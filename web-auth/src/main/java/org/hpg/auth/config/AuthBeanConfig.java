/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.config;

import org.hpg.auth.biz.service.abstr.IAccessErrorHandler;
import org.hpg.auth.biz.service.abstr.ILoginLogoutMessageService;
import org.hpg.auth.biz.service.impl.AuthenticatedUserSessionImpl;
import org.hpg.auth.biz.service.impl.DefaultAuthenticationFailureHandlerImpl;
import org.hpg.auth.biz.service.impl.DefaultAuthenticationSuccessHandlerImpl;
import org.hpg.auth.biz.service.impl.DefaultLogoutHandlerImpl;
import org.hpg.auth.biz.service.impl.DefaultPasswordEncoderImpl;
import org.hpg.auth.biz.service.impl.DefaultSessionExpiredStrategyImpl;
import org.hpg.auth.biz.service.impl.DefaultUserDetailsServiceImpl;
import org.hpg.auth.biz.service.impl.GeneralPurposeAccessErrorHandlerImpl;
import org.hpg.auth.biz.service.impl.LoginLogoutJMSImpl;
import org.hpg.auth.biz.service.impl.SessionRegistryLoginUserServiceImpl;
import org.hpg.auth.biz.service.impl.TaskExecutorSecurityAwareImpl;
import org.hpg.auth.constant.AuthBeanConstant;
import org.hpg.auth.constant.AuthUrls;
import org.hpg.auth.util.AuthUtil;
import org.hpg.common.biz.annotation.SecurityContextAware;
import org.hpg.common.biz.service.abstr.ILoginUserService;
import org.hpg.common.biz.service.abstr.IPasswordService;
import org.hpg.common.biz.service.abstr.ITaskExecutor;
import org.hpg.common.biz.service.abstr.IUserSession;
import org.hpg.common.constant.MendelRole;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
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
    @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_USER_DETAILS_SERVICE_FOR_ADMINROLE)
    public UserDetailsService getUserDetailsServiceForAdmin() {
        // TODO Confirm: Is it possible to wire dependency via new operator ?
        return new DefaultUserDetailsServiceImpl(MendelRole.ADMIN);
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_USER_DETAILS_SERVICE_FOR_USERROLE)
    public UserDetailsService getUserDetailsServiceForUser() {
        // TODO Confirm: Is it possible to wire dependency via new operator ?
        return new DefaultUserDetailsServiceImpl(MendelRole.USER);
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public PasswordEncoder getDefaultPasswordEncoder() {
        // TODO Confirm: Is it possible to wire dependency via new operator ?
        return new DefaultPasswordEncoderImpl();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_AUTH_FAILURE_HANDLER_FOR_ADMINROLE)
    public AuthenticationFailureHandler getDefaultAuthenticationFailureHandlerForAdminRole() {
        // TODO Confirm: Is it possible to wire dependency via new operator ?
        return new DefaultAuthenticationFailureHandlerImpl(MendelRole.ADMIN);
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_AUTH_FAILURE_HANDLER_FOR_USERROLE)
    public AuthenticationFailureHandler getDefaultAuthenticationFailureHandlerForUserRole() {
        // TODO Confirm: Is it possible to wire dependency via new operator ?
        return new DefaultAuthenticationFailureHandlerImpl(MendelRole.USER);
    }

    /**
     * Create user session concrete instance for the whole application
     *
     * @return
     */
    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    @Primary
    public IUserSession getAuthenticatedUserSession() {
        // TODO Probably move to common module
        return new AuthenticatedUserSessionImpl();
    }

    @Bean(destroyMethod = "stop", initMethod = "start")
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    @SecurityContextAware(true)
    public ITaskExecutor getSecurityAwareTaskExecutor() {
        return new TaskExecutorSecurityAwareImpl();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_AUTH_SUCCESS_HANDLER_FOR_ADMINROLE)
    public AuthenticationSuccessHandler getAuthenticationSuccessHandlerForAdminRole() {
        // TODO Confirm: Is it possible to wire dependency via new operator ?
        return new DefaultAuthenticationSuccessHandlerImpl("/admin/home");
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_AUTH_SUCCESS_HANDLER_FOR_USERROLE)
    public AuthenticationSuccessHandler getAuthenticationSuccessHandlerForUserRole() {
        // TODO Confirm: Is it possible to wire dependency via new operator ?
        return new DefaultAuthenticationSuccessHandlerImpl("/user/home");
    }

    /**
     * Create concrete password service for the whole application
     *
     * @return
     */
    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public IPasswordService getPasswordService() {
        return new IPasswordService() {
            private final PasswordEncoder passwordEncoder = new DefaultPasswordEncoderImpl();

            @Override
            public String getEncodedPassword(String rawPassword) {
                return passwordEncoder.encode(rawPassword);
            }
        };
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public ILoginLogoutMessageService getLoginLogoutMessageService(@Qualifier(AuthBeanConstant.Qualifier.AUTH_TOPIC_JMS_TEMPLATE) JmsTemplate template) {
        return new LoginLogoutJMSImpl(template);
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_LOGOUT_SUCCESS_HANDLER_FOR_ADMINROLE)
    public LogoutSuccessHandler getLogoutSuccessHandlerForAdminRole() {
        return new DefaultLogoutHandlerImpl(AuthUtil.getUrlInAuthDomain(AuthUrls.ADMIN_LOGIN));
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_LOGOUT_SUCCESS_HANDLER_FOR_USERROLE)
    public LogoutSuccessHandler getLogoutSuccessHandlerForUserRole() {
        return new DefaultLogoutHandlerImpl(AuthUtil.getUrlInAuthDomain(AuthUrls.USER_LOGIN));
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_SESSION_EXPIRED_STRATRGY_FOR_ADMINROLE)
    public SessionInformationExpiredStrategy getSessionAuthFailureHandlerForAdminRole() {
        return new DefaultSessionExpiredStrategyImpl(
                new GeneralPurposeAccessErrorHandlerImpl(AuthUtil.getUrlInAuthDomain(AuthUrls.ADMIN_SESSION_AUTH_FAILURE_URL))
        );
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    @Qualifier(AuthBeanConstant.Qualifier.DEFAULT_SESSION_EXPIRED_STRATRGY_FOR_USERROLE)
    public SessionInformationExpiredStrategy getSessionAuthFailureHandlerForUserRole() {
        return new DefaultSessionExpiredStrategyImpl(
                new GeneralPurposeAccessErrorHandlerImpl(AuthUtil.getUrlInAuthDomain(AuthUrls.USER_SESSION_AUTH_FAILURE_URL))
        );
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public ILoginUserService getLoginUserService() {
        return new SessionRegistryLoginUserServiceImpl();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public IAccessErrorHandler getForbiddenAccessHandler() {
        return new GeneralPurposeAccessErrorHandlerImpl(AuthUtil.getUrlInAuthDomain(AuthUrls.FORBIDDEN_PAGE));
    }

    @Bean
    public SessionRegistry getSessionRegistry() {
        // TODO Better implementation ?
        return new SessionRegistryImpl();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ITaskExecutor getTaskExecutor() {
        return new TaskExecutorSecurityAwareImpl();
    }
}
