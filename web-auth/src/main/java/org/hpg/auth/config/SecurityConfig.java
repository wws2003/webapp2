/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.config;

import org.hpg.auth.constant.AuthBeanConstant;
import org.hpg.auth.constant.AuthUrls;
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
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * Implementation of Security policy using Spring-sec libraries
 *
 * @author wws2003
 */
@EnableWebSecurity
public class SecurityConfig {

    public static abstract class AbstractSecurityUserRoleConfig extends WebSecurityConfigurerAdapter {

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
        private PasswordEncoder mPasswordEncoder;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(mUserUserDetailsService)
                    .passwordEncoder(mPasswordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            this.getHttpSecurityRolePrivsConfigurer(http)
                    .forUrlPrivileges(AuthUtil.getUrlPrivilesMap(UrlPrivilegeConfig.UserRole.class))
                    .buildInterceptUrlRegistry()
                    .and()
                    .formLogin()
                    .loginPage(AuthUtil.getUrlInAuthDomain(AuthUrls.USER_LOGIN))
                    .loginProcessingUrl(AuthUtil.getUrlInUserDomain(AuthUrls.UserRole.LOGIN_PROCESS)) // Prefix 'must' be /user
                    .failureHandler(authenticationFailureHandler)
                    .successHandler(authenticationSuccessHandler)
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage(AuthUtil.getUrlInAuthDomain(AuthUrls.FORBIDDEN_ACTION))
                    .and()
                    .logout()
                    .logoutUrl(AuthUtil.getUrlInUserDomain(AuthUrls.UserRole.LOGOUT_PROCESS)) // Prefix 'must' be /user (?), default is clearing authentication and httpsession
                    .logoutSuccessHandler(logoutSuccessHandlerForUserRole)
                    .permitAll();
        }

        protected abstract HttpSecurityRolePrivsConfigurer getHttpSecurityRolePrivsConfigurer(HttpSecurity httpSecurity) throws Exception;
    }

    @Configuration
    @Order(1)
    public static class SecurityUserRoleConfig extends AbstractSecurityUserRoleConfig {

        @Autowired
        @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_SESSION_EXPIRED_STRATRGY_FOR_USERROLE)
        private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

        @Autowired
        private SessionRegistry sessionRegistry;

        @Override
        protected HttpSecurityRolePrivsConfigurer getHttpSecurityRolePrivsConfigurer(HttpSecurity httpSecurity) throws Exception {
            return HttpSecurityRolePrivsConfigurer.instance(httpSecurity)
                    .sessionManagement(1, sessionRegistry, sessionInformationExpiredStrategy) // No more one session for one user
                    .forUserRoleToWorkWithProfile();
        }
    }

    @Configuration
    @Order(2)
    public static class SecurityUserRoleForProjectConfig extends AbstractSecurityUserRoleConfig {

        @Autowired
        @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_SESSION_EXPIRED_STRATRGY_FOR_USERROLE)
        private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

        @Autowired
        private SessionRegistry sessionRegistry;

        @Override
        protected HttpSecurityRolePrivsConfigurer getHttpSecurityRolePrivsConfigurer(HttpSecurity httpSecurity) throws Exception {
            return HttpSecurityRolePrivsConfigurer.instance(httpSecurity)
                    .sessionManagement(1, sessionRegistry, sessionInformationExpiredStrategy) // No more one session for one user
                    .forUserRoleToWorkWithProject();
        }
    }

    @Configuration
    @Order(3)
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
        @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_SESSION_EXPIRED_STRATRGY_FOR_ADMINROLE)
        private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

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
                    .sessionManagement(1, sessionRegistry, sessionInformationExpiredStrategy)
                    .forAdminRole()
                    .forUrlPrivileges(AuthUtil.getUrlPrivilesMap(UrlPrivilegeConfig.AdminRole.class))
                    .buildInterceptUrlRegistry()
                    .and()
                    .formLogin()
                    .loginPage(AuthUtil.getUrlInAuthDomain(AuthUrls.ADMIN_LOGIN))
                    .loginProcessingUrl(AuthUtil.getUrlInAdminDomain(AuthUrls.AdminRole.LOGIN_PROCESS)) // Prefix 'must' be /admin
                    .failureHandler(authenticationFailureHandler)
                    .successHandler(authenticationSuccessHandler)//.defaultSuccessUrl("/admin/home") // Call this cause specified authenticationSuccessHandler ignored
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage(AuthUtil.getUrlInAuthDomain(AuthUrls.FORBIDDEN_ACTION))
                    .and()
                    .logout()
                    .logoutUrl(AuthUtil.getUrlInAdminDomain(AuthUrls.AdminRole.LOGOUT_PROCESS)) // Prefix 'must' be /admin (?), default is clearing authentication and httpsession
                    .logoutSuccessHandler(logoutSuccessHandlerForAdminRole)
                    .permitAll();
        }
    }
}
