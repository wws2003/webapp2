/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web;

import org.hpg.admin.biz.web.form.UserAddUpdateForm;
import org.hpg.admin.constant.AdminUrls;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.common.util.AjaxResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for user management actions
 *
 * @author trungpt
 */
@Controller
@RequestMapping(AdminUrls.ADMIN_USER_MANAGEMENT_ROOT_URL)
public class UserMgtController {

    @Autowired
    private IUserService userService;

    /**
     * Add/Update user action
     *
     * @param form
     * @return
     */
    @PostMapping(AdminUrls.ADMIN_USER_MANAGEMENT_ADD_UPDATE)
    @ResponseBody
    public AjaxResult addUpdateUser(UserAddUpdateForm form) {
        // TODO Implement properly
        try {
            validateAddUpdateForm(form);
            MendelUser userToCreateOrUpdate = parseUserDtoFromForm(form);
            boolean isToCreateUser = form.getToCreateUser();
            if (isToCreateUser) {
                userService.createUser(userToCreateOrUpdate);
            } else {
                userService.updateUser(userToCreateOrUpdate);
            }
        } catch (MendelRuntimeException ex) {
            return AjaxResultBuilder.failedInstance()
                    .oneErrorMessage(ex.getLocalizedMessage())
                    .build();
        }
        // TODO Implement properly
        return AjaxResultBuilder.successInstance()
                .oneSuccessMessage("User has been successfully created")
                .build();
    }

    /**
     * Validate the add/update form
     *
     * @param form
     * @throws MendelRuntimeException
     */
    private void validateAddUpdateForm(UserAddUpdateForm form) throws MendelRuntimeException {
        // TODO Implement
        boolean isValid = true;
        if (!isValid) {
        }
    }

    /**
     * Parse from add/update form the DTO instance
     *
     * @param form
     * @return
     */
    private MendelUser parseUserDtoFromForm(UserAddUpdateForm form) {
        // TODO Implement
        return null;
    }
}
