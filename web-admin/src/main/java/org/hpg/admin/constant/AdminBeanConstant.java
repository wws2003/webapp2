/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.constant;

/**
 * Constants used by bean configuration
 *
 * @author trungpt
 */
public class AdminBeanConstant {

    public static class Qualifier {

        // TODO Get value from somewhere to avoid potential conflict (currently hard-coded from JMSConfig)
        public static final String AUTH_TOPIC_JMS_MESSAGE_LISTENER = "authTopicJMSMessageListener";
    }
}
