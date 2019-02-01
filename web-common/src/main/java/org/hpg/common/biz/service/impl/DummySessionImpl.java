/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.impl;

import org.hpg.common.biz.service.abstr.IUserSession;
import org.hpg.common.model.dto.principal.LoginInfo;
import org.hpg.common.model.dto.user.MendelUser;

/**
 * Just a dummy class for session (used when module auth not deployed)
 *
 * @author trungpt
 */
public class DummySessionImpl implements IUserSession {

    @Override
    public LoginInfo getCurrentLoginInfo() {
        MendelUser user = new MendelUser();
        user.setDispName("Sample user");
        user.setId(1L);
        user.setName("SMPLUSR");
        user.setPassword("pass001");
        return LoginInfo.withUser(user)
                .accountLocked(false)
                .accountExpired(false)
                .build();
    }

    @Override
    public void setCurrentLoginInfo(LoginInfo loginInfo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSessionId() {
        // Dummy
        return "Dummy session id";
    }

    @Override
    public void invalidate() {
        // Do nothing
    }

}
