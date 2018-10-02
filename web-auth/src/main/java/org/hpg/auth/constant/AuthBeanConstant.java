/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.constant;

/**
 * Constants used by bean configuration
 *
 * @author trungpt
 */
public class AuthBeanConstant {

    public static class Qualifier {

        public static final String DEFAULT_USER_DETAILS_SERVICE_FOR_ADMINROLE = "userDetailsServiceForAdmin";
        public static final String DEFAULT_USER_DETAILS_SERVICE_FOR_USERROLE = "userDetailsServiceForUser";
        public static final String DEFAULT_AUTH_FAILURE_HANDLER_FOR_ADMINROLE = "defaultAuthenticationFailureHandlerForAdminRole";
        public static final String DEFAULT_AUTH_FAILURE_HANDLER_FOR_USERROLE = "defaultAuthenticationFailureHandlerForUserRole";
        public static final String DEFAULT_AUTH_SUCCESS_HANDLER_FOR_ADMINROLE = "authenticationSuccessHandlerForAdminRole";
        public static final String DEFAULT_AUTH_SUCCESS_HANDLER_FOR_USERROLE = "authenticationSuccessHandlerForUserRole";
        public static final String DEFAULT_LOGOUT_SUCCESS_HANDLER_FOR_ADMINROLE = "logoutSuccessHandlerForAdminRole";
        public static final String DEFAULT_LOGOUT_SUCCESS_HANDLER_FOR_USERROLE = "logoutSuccessHandlerForUserRole";

        // TODO Get value from somewhere to avoid potential conflict (currently hard-coded from JMSConfig)
        public static final String AUTH_TOPIC_JMS_TEMPLATE = "authTopicJMSTemplate";
    }
}
