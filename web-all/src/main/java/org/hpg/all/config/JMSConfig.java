/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.all.config;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageListener;
import javax.naming.NamingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.jndi.JndiObjectFactoryBean;

/**
 * Configuration for JMS
 *
 * @author trungpt
 */
@Configuration
@PropertySources({
    @PropertySource("classpath:jms.properties")
})
@EnableJms
public class JMSConfig {

    @Autowired
    private Environment environment;

    // Connection factory
    @Bean
    public ConnectionFactory getJMSConnectionFactory() {
        // Refs. https://stackoverflow.com/questions/19588419/change-jms-settings-with-glassfish-and-spring
        try {
            JndiObjectFactoryBean jndiFactory = new JndiObjectFactoryBean();
            jndiFactory.setJndiName(environment.getProperty("jms.jndi.connectionfactory"));
            jndiFactory.setResourceRef(true);  //adds java:comp/env/ prefix
            jndiFactory.afterPropertiesSet();  //very important, actually causes the object to be loaded

            return (ConnectionFactory) jndiFactory.getObject();

        } catch (IllegalArgumentException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

    // Endpoint
    // Transaction manager?
    /**
     * For sending message
     *
     * @return
     */
    @Bean
    @Qualifier("authTopicJMSTemplate")
    public JmsTemplate getJMSTemplate() {
        try {
            Destination dest = getJMSDestination(environment.getProperty("jms.jndi.topic.auth"));
            JmsTemplate template = new JmsTemplate(getJMSConnectionFactory());
            template.setDefaultDestination(dest);
            template.setPubSubDomain(true);  //may be necessary if using topic
            return template;
        } catch (IllegalArgumentException | NamingException e) {
            throw new RuntimeException(e);
        }
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
    public MessageListenerContainer getMessageListenerContainerForAuthTopic(ConnectionFactory connectionFactory, @Qualifier("authTopicJMSMessageListener") MessageListener messageListener) {
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
     * Message converter
     *
     * @return
     */
    @Bean
    public MessageConverter converter() {
        return new SimpleMessageConverter();
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
