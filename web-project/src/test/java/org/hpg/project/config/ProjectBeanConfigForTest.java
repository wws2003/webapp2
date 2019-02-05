/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.config;

import org.hpg.common.biz.service.abstr.IUserSession;
import org.hpg.common.model.dto.principal.LoginInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

/**
 * Provider of some beans required to have to test application context loaded
 *
 * @author wws2003
 */
@Configuration
public class ProjectBeanConfigForTest {

    /**
     * Create user session concrete instance for the whole application
     *
     * @return
     */
    @Bean
    @Primary
    @Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public IUserSession getAuthenticatedUserSession() {
        // TODO Probably move to common module
        return new IUserSession() {
            @Override
            public LoginInfo getCurrentLoginInfo() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setCurrentLoginInfo(LoginInfo loginInfo) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getSessionId() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void invalidate() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }
}
