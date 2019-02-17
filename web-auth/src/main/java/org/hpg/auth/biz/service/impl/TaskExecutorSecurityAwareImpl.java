/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.hpg.common.biz.service.impl.BaseTaskExecutor;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.scheduling.DelegatingSecurityContextSchedulingTaskExecutor;

/**
 * Implementation for security context aware task executor
 *
 * Created on : Feb 9, 2019, 8:08:59 PM
 *
 * @author wws2003
 */
public class TaskExecutorSecurityAwareImpl extends BaseTaskExecutor {

    @Override
    protected CompletableFuture getInterruptibleFutureStage(SchedulingTaskExecutor delegateExecutor, Runnable runnable) {
        DelegatingSecurityContextSchedulingTaskExecutor executor = getSecurityExecutor(delegateExecutor);

        // The completable future created is not interruptible, only be able to cancel it by cancelling the
        // interruptible stage so that it can not be executed at all
        return CompletableFuture.runAsync(runnable, executor);
    }

    @Override
    protected Future getInterruptibleFuture(SchedulingTaskExecutor delegateExecutor, Runnable runnable) {
        DelegatingSecurityContextSchedulingTaskExecutor executor = getSecurityExecutor(delegateExecutor);

        return executor.submit(runnable);
    }

    /**
     * Get executor for spring security context
     *
     * @param delegateExecutor
     * @return
     */
    private DelegatingSecurityContextSchedulingTaskExecutor getSecurityExecutor(SchedulingTaskExecutor delegateExecutor) {
        return new DelegatingSecurityContextSchedulingTaskExecutor(
                delegateExecutor,
                SecurityContextHolder.getContext());
    }
}
