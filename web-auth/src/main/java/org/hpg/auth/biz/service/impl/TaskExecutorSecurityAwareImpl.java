/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.hpg.common.biz.service.abstr.ITaskExecutor;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

/**
 * Implementation for security context aware task executor
 *
 * Created on : Feb 9, 2019, 8:08:59 PM
 *
 * @author wws2003
 */
public class TaskExecutorSecurityAwareImpl implements ITaskExecutor {

    private final Map<String, CompletableFuture> taskMap = new ConcurrentHashMap();

    private final ThreadPoolTaskExecutor delegateExecutor = new ThreadPoolTaskExecutor();

    private final AtomicBoolean started = new AtomicBoolean(false);

    @Override
    public boolean start() throws MendelRuntimeException {
        if (started.compareAndSet(false, true)) {
            // A sample of waiting time out
            delegateExecutor.setAwaitTerminationSeconds(20);
            delegateExecutor.setWaitForTasksToCompleteOnShutdown(false);
            delegateExecutor.initialize();
        }
        return true;
    }

    @Override
    public void submit(String taskKey, Runnable runnable) throws MendelRuntimeException {
        // Yet a very simple solution
        DelegatingSecurityContextExecutor executor = new DelegatingSecurityContextAsyncTaskExecutor(delegateExecutor,
                SecurityContextHolder.getContext());

        // No need of explicit call to execute()
        CompletableFuture completableFuture = CompletableFuture.runAsync(runnable, executor);

        // Add to map
        synchronized (this) {
            this.taskMap.put(taskKey, completableFuture);
        }
    }

    @Override
    public boolean cancelTask(String taskKey) throws MendelRuntimeException {
        // Now just a dummy implementation
        // But looks like does not work with threads blocking, e.g.the case of Thread.sleep()
        return Optional.ofNullable(taskMap.get(taskKey))
                .map(completableFuture -> completableFuture.cancel(true))
                .orElse(false);
    }

    @Override
    public void stop() throws MendelRuntimeException {
        if (started.compareAndSet(true, false)) {
            delegateExecutor.shutdown();
        }
    }
}
