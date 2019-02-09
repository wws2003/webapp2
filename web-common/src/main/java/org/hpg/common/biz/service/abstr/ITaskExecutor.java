/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.abstr;

import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Simple executor for some task
 *
 * Created on : Feb 9, 2019, 7:48:46 PM
 *
 * @author wws2003
 */
public interface ITaskExecutor {

    /**
     * Start the executor
     *
     * @return TRUE if the executor started successfully, false otherwise
     * @throws MendelRuntimeException
     */
    public boolean start() throws MendelRuntimeException;

    /**
     * Submit the task to process on a task (possibly to set result to a
     * deferred result)
     *
     * @param taskKey
     * @param runnable
     * @throws MendelRuntimeException When submit failed or duplicated key
     */
    public void submit(String taskKey, Runnable runnable) throws MendelRuntimeException;

    /**
     * Cancel the submitted task
     *
     * @param taskKey
     * @return
     * @throws MendelRuntimeException
     */
    public boolean cancelTask(String taskKey) throws MendelRuntimeException;

    /**
     * Stop the executor
     *
     * @throws MendelRuntimeException
     */
    public void stop() throws MendelRuntimeException;
}
