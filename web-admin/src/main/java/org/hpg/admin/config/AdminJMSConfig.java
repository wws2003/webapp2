/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.config;

import javax.jms.MessageListener;
import org.hpg.admin.biz.message.impl.LoginLogoutMessageReceiverImpl;
import org.hpg.admin.constant.AdminBeanConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * Config for JMS
 *
 * @author trungpt
 */
@Configuration
public class AdminJMSConfig {

    // TODO Make clear about the internal message channel
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Message listener
     *
     * @param messageConverter
     * @return
     */
    @Bean
    @Qualifier(AdminBeanConstant.Qualifier.AUTH_TOPIC_JMS_MESSAGE_LISTENER)
    public MessageListener getMessageListenerForLoginLogoutAction(MessageConverter messageConverter) {
        return new LoginLogoutMessageReceiverImpl(messageConverter, simpMessagingTemplate);
    }
}
