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
import org.hpg.common.biz.service.abstr.ITaskExecutor;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
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

    @Override
    public boolean start() throws MendelRuntimeException {
        // TODO Implement (probably in other class)
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void submit(String taskKey, Runnable runnable) throws MendelRuntimeException {
        // Yet a very simple solution
        SimpleAsyncTaskExecutor delegateExecutor = new SimpleAsyncTaskExecutor();

        DelegatingSecurityContextExecutor executor = new DelegatingSecurityContextAsyncTaskExecutor(delegateExecutor,
                SecurityContextHolder.getContext());

        // This only create new thread then execute the task without thread pool
        executor.execute(runnable);
    }

    @Override
    public boolean cancelTask(String taskKey) throws MendelRuntimeException {
        // Now just a dummy implementation
        return Optional.ofNullable(taskMap.get(taskKey))
                .map(completableFuture -> completableFuture.cancel(true))
                .orElse(false);
    }

    @Override
    public void stop() throws MendelRuntimeException {
        // TODO Implement (probably in other class)
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
