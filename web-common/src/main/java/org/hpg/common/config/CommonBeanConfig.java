/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.config;

import org.hpg.common.biz.service.abstr.IPrivilegeService;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.biz.service.impl.SamplePrivilegeServiceImpl;
import org.hpg.common.biz.service.impl.SampleUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

/**
 * Configuration (mostly in term of producer) for beans provided by common
 * module
 *
 * @author trungpt
 */
@Configuration
public class CommonBeanConfig {

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public IUserService getUserServiceForAdmin() {
        // TODO Implement properly
        return new SampleUserServiceImpl();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public IPrivilegeService getPrivilegeService() {
        // TODO Implement properly
        return new SamplePrivilegeServiceImpl();
    }
}
