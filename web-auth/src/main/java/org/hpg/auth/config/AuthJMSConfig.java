/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.config;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.NamingException;
import org.hpg.auth.constant.AuthBeanConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jndi.JndiObjectFactoryBean;

/**
 * JMS config for auth module. Separated from web-all (is this just temporary
 * refactoring ?)
 *
 * @author trungpt
 */
@Configuration
@PropertySources({
    @PropertySource("classpath:jms.properties")
})
@EnableJms
public class AuthJMSConfig {

    @Autowired
    private Environment environment;

    @Autowired
    private ConnectionFactory connectionFactory;

    // Endpoint
    // Transaction manager?
    /**
     * For sending message
     *
     * @return
     */
    @Bean
    @Qualifier(AuthBeanConstant.Qualifier.AUTH_TOPIC_JMS_TEMPLATE)
    public JmsTemplate getJMSTemplate() {
        try {
            Destination dest = getJMSDestination(environment.getProperty("jms.jndi.topic.auth"));
            JmsTemplate template = new JmsTemplate(connectionFactory);
            template.setDefaultDestination(dest);
            template.setPubSubDomain(true);  //may be necessary if using topic
            return template;
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
