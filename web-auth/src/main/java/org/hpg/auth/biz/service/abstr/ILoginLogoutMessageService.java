/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.abstr;

import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Message service for login logout topic
 *
 * @author trungpt
 */
public interface ILoginLogoutMessageService {

    /**
     * Notify the system that a user has been successfully login
     *
     * @param loginUserId
     * @throws MendelRuntimeException
     */
    public void notifyUserLogin(long loginUserId) throws MendelRuntimeException;

    /**
     * Notify the system that a user has been successfully logout
     *
     * @param logoutUserId
     * @throws MendelRuntimeException
     */
    public void notifyUserLogout(long logoutUserId) throws MendelRuntimeException;
}
