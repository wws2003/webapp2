/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.config;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.NamingException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.jndi.JndiObjectFactoryBean;

/**
 * Config for JMS
 *
 * @author trungpt
 */
@Configuration
@EnableJms
public class AuthJMSConfig {

    /**
     * Connection factory name
     */
    private static final String JNDI_GLASSFISH_JMS_CONNECTION_FACTORY = "jms/__defaultConnectionFactory";

    /**
     * Topic for authentication stuffs
     */
    private static final String JNDI_AUTH_TOPIC = "jms/mendelAuthTopic";

    // Connection factory
    @Bean
    public ConnectionFactory getJMSConnectionFactory() {
        // Refs. https://stackoverflow.com/questions/19588419/change-jms-settings-with-glassfish-and-spring
        try {
            JndiObjectFactoryBean jndiFactory = new JndiObjectFactoryBean();
            jndiFactory.setJndiName(JNDI_GLASSFISH_JMS_CONNECTION_FACTORY);
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
    public JmsTemplate getJMSTemplate() {
        try {
            JndiObjectFactoryBean jndiFactory = new JndiObjectFactoryBean();
            jndiFactory.setJndiName(JNDI_AUTH_TOPIC);
            jndiFactory.setResourceRef(true);  //adds java:comp/env/ prefix
            jndiFactory.afterPropertiesSet();  //very important, actually causes the object to be loaded
            Destination dest = (Destination) jndiFactory.getObject();

            JmsTemplate template = new JmsTemplate(getJMSConnectionFactory());
            template.setDefaultDestination(dest);
            template.setPubSubDomain(true);  //may be necessary if using topic

            return template;
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
}
