/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * Sample authentication failure handler (currently just logging)
 *
 * @author trungpt
 */
public class DefaultAuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

    /**
     * Default constructor
     *
     * @param defaultFailureUrl
     */
    public DefaultAuthenticationFailureHandlerImpl(String defaultFailureUrl) {
        super(defaultFailureUrl);
    }

    /**
     * Default constructor
     */
    public DefaultAuthenticationFailureHandlerImpl() {
        super("/auth/adminLogin?error=2");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest hsr, HttpServletResponse hsr1, AuthenticationException ae) throws IOException, ServletException {
        // Firstly just logging to see what is inside AuthenticationException: How to know what kind of error: Expired, incorrect password ...?
        /**
         * [#|2018-07-19T09:07:21.621+0700|SEVERE|glassfish
         * 4.1||_ThreadID=31;_ThreadName=Thread-9;_TimeMillis=1531966041621;_LevelValue=1000;|
         * org.springframework.security.authentication.BadCredentialsException:
         * Bad credentials at
         * org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider.authenticate(AbstractUserDetailsAuthenticationProvider.java:151)
         * at
         * org.springframework.security.authentication.ProviderManager.authenticate(ProviderManager.java:174)
         * at
         * org.springframework.security.authentication.ProviderManager.authenticate(ProviderManager.java:199)
         * at
         * org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.attemptAuthentication(UsernamePasswordAuthenticationFilter.java:94)
         * at
         * org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:212)
         * at
         * org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)
         * at
         * org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:116)
         * at
         * org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)
         * at
         * org.springframework.security.web.csrf.CsrfFilter.doFilterInternal(CsrfFilter.java:124)
         * at
         * org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
         * at
         * org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)
         * at
         * org.springframework.security.web.header.HeaderWriterFilter.doFilterInternal(HeaderWriterFilter.java:66)
         * at
         * org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
         * at
         * org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)
         * at
         * org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:105)
         * at
         * org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)
         * at
         * org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.doFilterInternal(WebAsyncManagerIntegrationFilter.java:56)
         * at
         * org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
         * at
         * org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)
         * at
         * org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:215)
         * at
         * org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:178)
         * at
         * org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:357)
         * at
         * org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:270)
         * at
         * org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:256)
         * at
         * org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:214)
         * at
         * org.glassfish.tyrus.servlet.TyrusServletFilter.doFilter(TyrusServletFilter.java:305)
         */
        ae.printStackTrace();
        super.onAuthenticationFailure(hsr, hsr1, ae);
    }
}
