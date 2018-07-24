/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import java.io.Serializable;
import org.hpg.common.biz.service.abstr.IUserSession;
import org.hpg.common.model.dto.principal.LoginInfo;

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
}
