/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.impl;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import org.hpg.common.biz.service.abstr.ITaskExecutor;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Sample implementation of task executor
 *
 * Created on : Feb 17, 2019, 4:37:03 PM
 *
 * @author wws2003
 */
public class BaseTaskExecutor implements ITaskExecutor {

    private static final boolean DONT_CARE = false;

    private final Map<String, Future> taskMap = new ConcurrentHashMap();

    private final ThreadPoolTaskExecutor delegateExecutor = new ThreadPoolTaskExecutor();

    private final AtomicBoolean started = new AtomicBoolean(false);

    private final boolean toWaitForTasksToCompleteOnShutdown;

    private final int awaitTerminationSeconds;

    public BaseTaskExecutor() {
        this.toWaitForTasksToCompleteOnShutdown = false;
        this.awaitTerminationSeconds = 20;
    }

    /**
     * Constructor
     *
     * @param toWaitForTasksToCompleteOnShutdown
     * @param awaitTerminationSeconds
     */
    public BaseTaskExecutor(boolean toWaitForTasksToCompleteOnShutdown, int awaitTerminationSeconds) {
        this.toWaitForTasksToCompleteOnShutdown = toWaitForTasksToCompleteOnShutdown;
        this.awaitTerminationSeconds = awaitTerminationSeconds;
    }

    @Override
    public boolean start() throws MendelRuntimeException {
        if (started.compareAndSet(false, true)) {
            // A sample of waiting time out
            delegateExecutor.setWaitForTasksToCompleteOnShutdown(toWaitForTasksToCompleteOnShutdown);
            delegateExecutor.setAwaitTerminationSeconds(awaitTerminationSeconds);
            delegateExecutor.initialize();
        }
        return true;
    }

    @Override
    public String getNewTaskKey() throws MendelRuntimeException {
        int tryCnt = 10;
        int i = 0;
        while (i++ < tryCnt) {
            String key = UUID.randomUUID().toString();
            synchronized (this.taskMap) {
                if (!this.taskMap.containsKey(key)) {
                    return key;
                }
            }
        }
        throw new MendelRuntimeException("TaskKeyGen failed");
    }

    @Override
    public void submit(String taskKey, Runnable runnable) throws MendelRuntimeException {
        // Yet a very simple solution
        // No need of explicit call to execute()
        Future future = getInterruptibleFuture(delegateExecutor, () -> {
            this.taskMap.remove(taskKey);
            // One chance to check to cancel soon
            if (!Thread.interrupted()) {
                runnable.run();
            }
        });

        // Add to map
        synchronized (this) {
            this.taskMap.put(taskKey, future);
        }
    }

    @Override
    public void submitUninterruptibleTask(String taskKey, Runnable interruptibleStage, Runnable uninterruptibleTask) throws MendelRuntimeException {
        // Yet a very simple solution
        CompletableFuture cancelableFuture = getInterruptibleFutureStage(delegateExecutor, () -> {
            this.taskMap.remove(taskKey);
            interruptibleStage.run();
        });

        // When the main runnable is started (i.e. task is executed), noway to cancel/interrupt
        cancelableFuture.thenRun(uninterruptibleTask);

        // Add to map
        synchronized (this) {
            this.taskMap.put(taskKey, cancelableFuture);
        }
    }

    @Override
    public boolean cancelTask(String taskKey) throws MendelRuntimeException {
        // Now just a dummy implementation
        // But looks like does not work with threads blocking, e.g.the case of Thread.sleep()
        return Optional.ofNullable(taskMap.remove(taskKey))
                .map(completableFuture -> {
                    if (!completableFuture.isDone()) {
                        return completableFuture.cancel(DONT_CARE);
                    }
                    return false;
                })
                .orElse(false);
    }

    @Override
    public void stop() throws MendelRuntimeException {
        if (started.compareAndSet(true, false)) {
            taskMap.clear();
            delegateExecutor.shutdown();
        }
    }

    /**
     * Get the future for the given task. Possibly be overridden by subclasses
     *
     * @param executor
     * @param runnable
     * @return
     */
    protected Future getInterruptibleFuture(SchedulingTaskExecutor executor, Runnable runnable) {
        return executor.submit(runnable);
    }

    /**
     * Get the future for uninterruptible task. Possibly be overridden by
     * subclasses
     *
     * @param executor
     * @param runnable
     * @return
     */
    protected CompletableFuture getInterruptibleFutureStage(SchedulingTaskExecutor executor, Runnable runnable) {
        return CompletableFuture.runAsync(runnable, delegateExecutor);
    }
}
