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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jndi.JndiObjectFactoryBean;

/**
 * Config for JMS
 *
 * @author trungpt
 */
@Configuration
@EnableJms
public class AdminJMSConfig {

    /**
     * Topic for authentication stuffs
     */
    private static final String JNDI_AUTH_TOPIC = "jms/mendelAuthTopic";

    /**
     * Container for message listener
     *
     * @param connectionFactory
     * @param messageListener
     * @return
     */
    @Bean
    public MessageListenerContainer getMessageListenerContainer(ConnectionFactory connectionFactory, MessageListener messageListener) {
        try {
            JndiObjectFactoryBean jndiFactory = new JndiObjectFactoryBean();
            jndiFactory.setJndiName(JNDI_AUTH_TOPIC);
            jndiFactory.setResourceRef(true);  //adds java:comp/env/ prefix
            jndiFactory.afterPropertiesSet();  //very important, actually causes the object to be loaded
            Destination dest = (Destination) jndiFactory.getObject();

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
     * Message listener
     *
     * @return
     */
    @Bean
    public MessageListener getMessageListenerForLoginLogoutAction() {
        return new LoginLogoutMessageReceiverImpl();
    }
}
