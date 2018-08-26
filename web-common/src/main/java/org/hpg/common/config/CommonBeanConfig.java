/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.config;

import org.hpg.common.biz.service.abstr.IFormValidator;
import org.hpg.common.biz.service.abstr.ILogger;
import org.hpg.common.biz.service.abstr.IPrivilegeService;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.biz.service.impl.SamplePrivilegeServiceImpl;
import org.hpg.common.biz.service.impl.SimpleLoggerImpl;
import org.hpg.common.biz.service.impl.StdFormValidatorImpl;
import org.hpg.common.biz.service.impl.UserServiceImpl;
import org.hpg.common.dao.mapper.abstr.IEntityDtoMapper;
import org.hpg.common.dao.mapper.impl.UserEntityDtoMapperImpl;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.web.context.WebApplicationContext;

/**
 * Configuration (mostly in term of producer) for beans provided by common
 * module
 *
 * @author trungpt
 */
@Configuration
@PropertySources({
    @PropertySource("classpath:application.properties")
})
public class CommonBeanConfig {

    @Autowired
    private Environment environment;

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public IEntityDtoMapper<UserEntity, MendelUser> getEntityDtoMapper() {
        return new UserEntityDtoMapperImpl();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public IUserService getUserService() {
        return new UserServiceImpl();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public IPrivilegeService getPrivilegeService() {
        // TODO Implement properly
        return new SamplePrivilegeServiceImpl();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public IFormValidator getFormValidator() {
        return new StdFormValidatorImpl();
    }

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public ILogger getLogger() {
        int maxTraceLevel = environment.getProperty("logger.max-trace-level", Integer.class);
        return new SimpleLoggerImpl(maxTraceLevel);
    }
}
