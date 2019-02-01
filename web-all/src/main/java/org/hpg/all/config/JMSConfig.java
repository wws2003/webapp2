/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.all.config;

import javax.jms.ConnectionFactory;
import javax.naming.NamingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.jndi.JndiObjectFactoryBean;

/**
 * Configuration for JMS. In web-all, just provide fundamental common components
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
