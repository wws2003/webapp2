/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import java.util.Arrays;
import org.hpg.auth.biz.service.abstr.ILoginLogoutMessageService;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.common.model.message.RecentLoginStatusMessage;
import org.springframework.jms.core.JmsTemplate;

/**
 * Implementation of message service for login logout topic
 *
 * @author trungpt
 */
public class LoginLogoutJMSImpl implements ILoginLogoutMessageService {

    private final JmsTemplate jmsTemplate;

    public LoginLogoutJMSImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void notifyUserLogin(long loginUserId) throws MendelRuntimeException {
        RecentLoginStatusMessage message = new RecentLoginStatusMessage();
        message.setLoggedInUserIds(Arrays.asList(loginUserId));
        jmsTemplate.convertAndSend(message);
    }

    @Override
    public void notifyUserLogout(long logoutUserId) throws MendelRuntimeException {
        RecentLoginStatusMessage message = new RecentLoginStatusMessage();
        message.setLoggedOutUserIds(Arrays.asList(logoutUserId));
        jmsTemplate.convertAndSend(message);
    }
}
