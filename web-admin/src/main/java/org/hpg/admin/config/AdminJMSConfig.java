/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.config;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageListener;
import javax.naming.NamingException;
import org.hpg.admin.biz.message.impl.LoginLogoutMessageReceiverImpl;
import org.hpg.admin.constant.AdminBeanConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * Config for JMS for Admin module
 *
 * @author trungpt
 */
@Configuration
public class AdminJMSConfig {

    // TODO Make clear about the internal message channel
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private Environment environment;

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

    /**
     * Container for message listener
     *
     * @param connectionFactory
     * @param messageListener
     * @return
     */
    @Bean
    @Qualifier("authTopicJMSListenerContainer")
    public MessageListenerContainer getMessageListenerContainerForAuthTopic(ConnectionFactory connectionFactory, @Qualifier(AdminBeanConstant.Qualifier.AUTH_TOPIC_JMS_MESSAGE_LISTENER) MessageListener messageListener) {
        try {
            Destination dest = getJMSDestination(environment.getProperty("jms.jndi.topic.auth"));

            DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
            container.setConnectionFactory(connectionFactory);
            container.setDestination(dest);
            container.setMessageListener(messageListener);

            return container;
        } catch (IllegalArgumentException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get JMS destination
     *
     * @param jndiName
     * @return
     */
    private Destination getJMSDestination(String jndiName) throws IllegalArgumentException, NamingException {
        JndiObjectFactoryBean jndiFactory = new JndiObjectFactoryBean();
        jndiFactory.setJndiName(jndiName);
        jndiFactory.setResourceRef(true);  //adds java:comp/env/ prefix
        jndiFactory.afterPropertiesSet();  //very important, actually causes the object to be loaded
        return (Destination) jndiFactory.getObject();
    }
}
