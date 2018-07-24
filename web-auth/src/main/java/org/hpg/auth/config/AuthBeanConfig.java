/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.config;

import org.hpg.auth.biz.service.impl.AdminRolelUserDetailsServiceImpl;
import org.hpg.auth.biz.service.impl.AuthenticatedUserSessionImpl;
import org.hpg.auth.biz.service.impl.DefaultAuthenticationFailureHandlerImpl;
import org.hpg.auth.biz.service.impl.DefaultAuthenticationSuccessHandlerImpl;
import org.hpg.auth.biz.service.impl.DefaultPasswordEncoderImpl;
import org.hpg.auth.biz.service.impl.UserRoleUserDetailsServiceImpl;
import org.hpg.common.biz.service.abstr.IUserSession;
import org.hpg.common.constant.MendelRole;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.context.WebApplicationContext;

/**
 * Configuration (mostly in term of producer) for beans provided by auth module
 *
 * @author trungpt
 */
@Configuration
public class AuthBeanConfig {
    // TODO Implement

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    @Qualifier("userDetailsServiceForAdmin")
    public UserDetailsService getUserDetailsServiceForAdmin() {
        // TODO Confirm: Is it possible to wire dependency via new operator ?
        return new AdminRolelUserDetailsServiceImpl();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    @Qualifier("userDetailsServiceForUser")
    public UserDetailsService getUserDetailsServiceForUser() {
        // TODO Confirm: Is it possible to wire dependency via new operator ?
        return new UserRoleUserDetailsServiceImpl();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public PasswordEncoder getDefaultPasswordEncoder() {
        // TODO Confirm: Is it possible to wire dependency via new operator ?
        return new DefaultPasswordEncoderImpl();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    @Qualifier("defaultAuthenticationFailureHandlerForAdminRole")
    public AuthenticationFailureHandler getDefaultAuthenticationFailureHandlerForAdminRole() {
        // TODO Confirm: Is it possible to wire dependency via new operator ?
        return new DefaultAuthenticationFailureHandlerImpl(MendelRole.ADMIN);
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    @Qualifier("defaultAuthenticationFailureHandlerForUserRole")
    public AuthenticationFailureHandler getDefaultAuthenticationFailureHandlerForUserRole() {
        // TODO Confirm: Is it possible to wire dependency via new operator ?
        return new DefaultAuthenticationFailureHandlerImpl(MendelRole.USER);
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public IUserSession getAuthenticatedUserSession() {
        // TODO Probably move to common module
        return new AuthenticatedUserSessionImpl();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
        // TODO Confirm: Is it possible to wire dependency via new operator ?
        return new DefaultAuthenticationSuccessHandlerImpl();
    }
}
