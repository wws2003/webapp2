/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.user.biz.web.profilemgt.scrnservice;

import org.hpg.common.biz.service.abstr.IPasswordService;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.biz.service.abstr.IUserSession;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.common.util.AjaxResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for profile management screen service
 *
 * @author wws2003
 */
@Service
public class ProfileMgtScrnServiceImpl implements IProfileMgtScrnService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserSession userSession;

    @Autowired
    private IPasswordService passwordService;

    @Override
    public AjaxResult savePassword(String password) throws MendelRuntimeException {
        MendelUser currentUser = userSession.getCurrentLoginInfo().getLoginUser();
        if (currentUser == null) {
            throw new MendelRuntimeException("Can not find login user");
        }
        currentUser.setPassword(password);
        currentUser.setEncodedPassword(passwordService.getEncodedPassword(password));
        if (userService.updateUser(currentUser) != null) {
            return AjaxResultBuilder.successInstance()
                    .oneSuccessMessage("Users password have been successfully changed")
                    .build();
        } else {
            throw new MendelRuntimeException("Can not update user");
        }
    }
}
