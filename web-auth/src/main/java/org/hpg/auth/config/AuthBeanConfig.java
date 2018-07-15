/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.config;

import org.hpg.auth.biz.service.impl.AdminRolelUserDetailsServiceImpl;
import org.hpg.auth.biz.service.impl.DefaultPasswordEncoderImpl;
import org.hpg.auth.biz.service.impl.UserRoleUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
}
