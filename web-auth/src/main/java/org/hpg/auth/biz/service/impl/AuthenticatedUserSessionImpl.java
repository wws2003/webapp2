/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import java.io.Serializable;
import org.hpg.common.biz.service.abstr.IUserSession;
import org.hpg.common.model.dto.principal.LoginInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Implementation for UserSession
 *
 * @author wws2003
 */
public class AuthenticatedUserSessionImpl implements IUserSession, Serializable {

    public AuthenticatedUserSessionImpl() {
        // For investigation purpose
        System.out.println("-------------------------------------------org.hpg.auth.biz.service.impl.AuthenticatedUserSessionImpl.<init>()-------------------------------------------");
    }

    @Override
    public LoginInfo getCurrentLoginInfo() {
        // TODO Implement properly colaborating with AuthenticationSuccessHandler
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        return (LoginInfo) auth.getPrincipal();
    }
}
