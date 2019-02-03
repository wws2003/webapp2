/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.impl;

import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.biz.service.abstr.IUserSession;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.model.dto.principal.LoginInfo;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Just a dummy class for session (used when module auth not deployed)
 *
 * @author trungpt
 */
public class SampleUserSessionImpl implements IUserSession {

    private final IUserService userService;

    /**
     * User instance. Wonder if this kind of storage work along with proxy
     * instance
     */
    private MendelUser sampleUser = null;

    /**
     * Constructor
     *
     * @param userService
     */
    public SampleUserSessionImpl(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public LoginInfo getCurrentLoginInfo() {
        if (this.sampleUser == null) {
            this.sampleUser = userService.findUserByName("USER_SMPL", MendelRole.USER)
                    .orElseThrow(() -> new MendelRuntimeException("No sample user available"));
        }
        return LoginInfo.withUser(this.sampleUser)
                .authenticated(true)
                .disabled(false)
                .credentialsExpired(false)
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
