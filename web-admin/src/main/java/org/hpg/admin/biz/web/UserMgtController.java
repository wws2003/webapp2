/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web;

import java.util.ArrayList;
import java.util.List;
import org.hpg.admin.biz.web.form.UserAddUpdateForm;
import org.hpg.admin.constant.AdminUrls;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.constant.MendelPrivilege;
import org.hpg.common.framework.BaseFormProcessorForAjaxResult;
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

        BaseFormProcessorForAjaxResult<UserAddUpdateForm> processor = BaseFormProcessorForAjaxResult.<UserAddUpdateForm>instanceForAjaxResult();

        return (AjaxResult) processor.consume(form)
                .transactional(false)
                .formProcessor(fm -> {
                    MendelUser userToCreateOrUpdate = parseUserDtoFromForm(fm);
                    boolean isToCreateUser = form.getToCreateUser();
                    // Save
                    MendelUser savedUser = isToCreateUser ? userService.createUser(userToCreateOrUpdate) : userService.updateUser(userToCreateOrUpdate);
                    // Grant privileges
                    List<MendelPrivilege> grantedPrivileges = parseGrantedPrivilegesFromForm(fm);
                    userService.grantUserWithPrivileges(savedUser, grantedPrivileges);
                    // Return sucess result
                    return AjaxResultBuilder.successInstance()
                            .oneSuccessMessage("User has been successfully created")
                            .build();
                })
                .execute();
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

    /**
     * Parse granted privileges to the user
     *
     * @param form
     * @return
     */
    private List<MendelPrivilege> parseGrantedPrivilegesFromForm(UserAddUpdateForm form) {
        // TODO Implement
        return new ArrayList();
    }
}
