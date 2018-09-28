/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.message.impl;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.hpg.common.model.message.RecentLoginStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

/**
 * Receiver for messages from login/logout action
 *
 * @author trungpt
 */
public class LoginLogoutMessageReceiverImpl implements MessageListener {

    @Autowired
    private MessageConverter messageConverter;

    @Override
    public void onMessage(Message msg) {
        try {
            // TODO Implement properly
            RecentLoginStatusMessage response = (RecentLoginStatusMessage) messageConverter.fromMessage(msg);
            System.out.println(response);
        } catch (JMSException | MessageConversionException ex) {
            Logger.getLogger(LoginLogoutMessageReceiverImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}