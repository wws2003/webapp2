/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.config;

import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.biz.service.impl.SampleUserServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier(QualifierConstant.USER_SERVICE_FOR_ADMIN)
    public IUserService getUserServiceForAdmin() {
        return new SampleUserServiceImpl();
    }
}
