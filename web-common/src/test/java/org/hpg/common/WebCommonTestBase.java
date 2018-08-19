/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common;

import org.hpg.common.dao.mapper.abstr.IEntityDtoMapper;
import org.hpg.common.dao.mapper.impl.UserEntityDtoMapperImpl;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.entity.UserEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.WebApplicationContext;

/**
 * Base class for tests in web-common module
 *
 * @author wws2003
 */
@Configuration
@ContextConfiguration
public class WebCommonTestBase {

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_APPLICATION)
    public IEntityDtoMapper<UserEntity, MendelUser> getEntityDtoMapperForTest() {
        return new UserEntityDtoMapperImpl();
    }
}
