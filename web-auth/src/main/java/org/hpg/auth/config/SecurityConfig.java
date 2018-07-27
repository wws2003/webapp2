/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.config;

import org.hpg.auth.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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
        @Qualifier("userDetailsServiceForUser")
        private UserDetailsService mUserUserDetailsService;

        @Autowired
        private PasswordEncoder mPasswordEncoder;

        @Autowired
        @Qualifier("authenticationSuccessHandlerForUserRole")
        private AuthenticationSuccessHandler authenticationSuccessHandler;

        @Autowired
        @Qualifier("defaultAuthenticationFailureHandlerForUserRole")
        private AuthenticationFailureHandler authenticationFailureHandler;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(mUserUserDetailsService)
                    .passwordEncoder(mPasswordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            HttpSecurityRolePrivsConfigurer.instance(http)
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
                    .permitAll()
                    .logoutSuccessUrl("/auth/userLogin");
        }
    }

    @Configuration
    @Order(2)
    public static class SecurityAdminRoleConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier("userDetailsServiceForAdmin")
        private UserDetailsService mAdminUserDetailsService;

        @Autowired
        private PasswordEncoder mPasswordEncoder;

        @Autowired
        @Qualifier("authenticationSuccessHandlerForAdminRole")
        private AuthenticationSuccessHandler authenticationSuccessHandler;

        @Autowired
        @Qualifier("defaultAuthenticationFailureHandlerForAdminRole")
        private AuthenticationFailureHandler authenticationFailureHandler;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(mAdminUserDetailsService)
                    .passwordEncoder(mPasswordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            HttpSecurityRolePrivsConfigurer.instance(http)
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
                    .permitAll()
                    .logoutSuccessUrl("/auth/adminLogin");
        }
    }
}
