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
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.messaging.core.MessageSendingOperations;

/**
 * Receiver for messages from login/logout action
 *
 * @author trungpt
 */
public class LoginLogoutMessageReceiverImpl implements MessageListener {

    private final MessageConverter messageConverter;

    private final MessageSendingOperations<String> messagingTemplate;

    public LoginLogoutMessageReceiverImpl(MessageConverter messageConverter, MessageSendingOperations<String> messagingTemplate) {
        this.messageConverter = messageConverter;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void onMessage(Message msg) {
        try {
            // TODO Implement properly
            RecentLoginStatusMessage response = (RecentLoginStatusMessage) messageConverter.fromMessage(msg);
            System.out.println(response);
            // [EXPERIMENT] Try to sent to client via websocket
            messagingTemplate.convertAndSend("/topic/loginCheck", response);
        } catch (JMSException | MessageConversionException ex) {
            Logger.getLogger(LoginLogoutMessageReceiverImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
