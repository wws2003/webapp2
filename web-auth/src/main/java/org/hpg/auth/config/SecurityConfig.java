/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.config;

import org.hpg.auth.constant.AuthBeanConstant;
import org.hpg.auth.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Implementation of Security policy using Spring-sec libraries
 *
 * @author wws2003
 */
@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    @Order(1)
    public static class SecurityUserRoleConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_USER_DETAILS_SERVICE_FOR_USERROLE)
        private UserDetailsService mUserUserDetailsService;

        @Autowired
        @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_AUTH_SUCCESS_HANDLER_FOR_USERROLE)
        private AuthenticationSuccessHandler authenticationSuccessHandler;

        @Autowired
        @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_AUTH_FAILURE_HANDLER_FOR_USERROLE)
        private AuthenticationFailureHandler authenticationFailureHandler;

        @Autowired
        @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_LOGOUT_SUCCESS_HANDLER_FOR_USERROLE)
        private LogoutSuccessHandler logoutSuccessHandlerForUserRole;

        @Autowired
        @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_SESSION_AUTH_FAILURE_HANDLER_FOR_USERROLE)
        private AuthenticationFailureHandler sessionAuthFailureHandler;

        @Autowired
        private PasswordEncoder mPasswordEncoder;

        @Autowired
        private SessionRegistry sessionRegistry;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(mUserUserDetailsService)
                    .passwordEncoder(mPasswordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            HttpSecurityRolePrivsConfigurer.instance(http)
                    .sessionManagement(1, sessionRegistry, sessionAuthFailureHandler) // No more one session for one user
                    .forUserRole()
                    .forUrlPrivileges(AuthUtil.getUrlPrivilesMap(UrlPrivilegeConfig.UserRole.class))
                    .buildInterceptUrlRegistry()
                    .and()
                    .formLogin()
                    .loginPage("/auth/userLogin")
                    .loginProcessingUrl("/user/login") // Prefix 'must' be /user
                    .failureHandler(authenticationFailureHandler)
                    .successHandler(authenticationSuccessHandler)
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/auth/forbidden")
                    .and()
                    .logout()
                    .logoutUrl("/user/logout") // Prefix 'must' be /user (?), default is clearing authentication and httpsession
                    .logoutSuccessHandler(logoutSuccessHandlerForUserRole)
                    .permitAll();
        }
    }

    @Configuration
    @Order(2)
    public static class SecurityAdminRoleConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_USER_DETAILS_SERVICE_FOR_ADMINROLE)
        private UserDetailsService mAdminUserDetailsService;

        @Autowired
        private PasswordEncoder mPasswordEncoder;

        @Autowired
        @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_AUTH_SUCCESS_HANDLER_FOR_ADMINROLE)
        private AuthenticationSuccessHandler authenticationSuccessHandler;

        @Autowired
        @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_AUTH_FAILURE_HANDLER_FOR_ADMINROLE)
        private AuthenticationFailureHandler authenticationFailureHandler;

        @Autowired
        @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_LOGOUT_SUCCESS_HANDLER_FOR_ADMINROLE)
        private LogoutSuccessHandler logoutSuccessHandlerForAdminRole;

        @Autowired
        @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_SESSION_AUTH_FAILURE_HANDLER_FOR_ADMINROLE)
        private AuthenticationFailureHandler sessionAuthFailureHandler;

        @Autowired
        private SessionRegistry sessionRegistry;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(mAdminUserDetailsService)
                    .passwordEncoder(mPasswordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            HttpSecurityRolePrivsConfigurer.instance(http)
                    .sessionManagement(1, sessionRegistry, sessionAuthFailureHandler)
                    .forAdminRole()
                    .forUrlPrivileges(AuthUtil.getUrlPrivilesMap(UrlPrivilegeConfig.AdminRole.class))
                    .buildInterceptUrlRegistry()
                    .and()
                    .formLogin()
                    .loginPage("/auth/adminLogin")
                    .loginProcessingUrl("/admin/login") // Prefix 'must' be /admin
                    .failureHandler(authenticationFailureHandler)
                    .successHandler(authenticationSuccessHandler)//.defaultSuccessUrl("/admin/home") // Call this cause specified authenticationSuccessHandler ignored
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/auth/forbidden")
                    .and()
                    .logout()
                    .logoutUrl("/admin/logout") // Prefix 'must' be /admin (?), default is clearing authentication and httpsession
                    .logoutSuccessHandler(logoutSuccessHandlerForAdminRole)
                    .permitAll();
        }
    }
}
