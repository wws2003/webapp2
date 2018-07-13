/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.config;

import org.hpg.auth.biz.service.impl.AdminRolelUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Configuration (mostly in term of producer) for beans provided by auth module
 *
 * @author trungpt
 */
@Configuration
public class AuthBeanConfig {
    // TODO Implement

    @Bean
    public UserDetailsService getUserDetailsServiceForAdmin() {
        // TODO Confirm: Is it possible to wire dependency via new operator ?
        return new AdminRolelUserDetailsServiceImpl();
    }
}
