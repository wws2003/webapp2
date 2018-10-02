/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import java.util.Optional;
import org.hpg.auth.model.MendelUserDetails;
import org.hpg.common.biz.service.abstr.ILoginUserService;
import org.hpg.common.model.dto.principal.LoginInfo;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;

/**
 * Implementation of login user service based on session registry
 *
 * @author wws2003
 */
public class SessionRegistryLoginUserServiceImpl implements ILoginUserService {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public Optional<LoginInfo> getLoginInfo(long userId) throws MendelRuntimeException {
        return sessionRegistry.getAllPrincipals()
                .stream()
                .map(principal -> ((MendelUserDetails) principal).getLoginInfo())
                .filter(loginInfo -> loginInfo.getLoginUser().getId() == userId)
                .findFirst();
    }
}
