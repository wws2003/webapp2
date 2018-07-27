/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import java.io.Serializable;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.hpg.common.biz.service.abstr.IUserSession;
import org.hpg.common.model.dto.principal.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Implementation for UserSession
 *
 * @author wws2003
 */
public class AuthenticatedUserSessionImpl implements IUserSession, Serializable {

    /**
     * Current login info
     */
    private LoginInfo mCurrentLoginInfo;

    //EXPERIMENTAL
    @Autowired
    private HttpSession session;

    public AuthenticatedUserSessionImpl() {
        // For investigation purpose
        System.out.println("-------------------------------------------org.hpg.auth.biz.service.impl.AuthenticatedUserSessionImpl.<init>()-------------------------------------------");
    }

    @Override
    public LoginInfo getCurrentLoginInfo() {
        return mCurrentLoginInfo;
    }

    @Override
    public void setCurrentLoginInfo(LoginInfo loginInfo) {
        mCurrentLoginInfo = loginInfo;
    }

    @Override
    public String getSessionId() {
        // TODO Implement
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void invalidate() {
        // [App]
        this.mCurrentLoginInfo = null;
        // [HttpSession]Invalidate HttpSession should not put here
        //session.invalidate();
        // [Spring-sec]Clear authentication object in holder
        if (Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication()).map(Authentication::isAuthenticated).orElse(false)) {
            SecurityContextHolder.getContext().setAuthentication(null);
            SecurityContextHolder.clearContext();
        }
    }
}
