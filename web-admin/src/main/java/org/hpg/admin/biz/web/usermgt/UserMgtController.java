/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.usermgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.hpg.admin.biz.web.usermgt.form.UserAddUpdateForm;
import org.hpg.admin.biz.web.usermgt.form.UserDeleteForm;
import org.hpg.admin.biz.web.usermgt.form.UsersIndexForm;
import org.hpg.admin.constant.AdminUrls;
import org.hpg.common.biz.service.abstr.IFormValidator;
import org.hpg.common.biz.service.abstr.IPasswordService;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.constant.MendelPrivilege;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.framework.BaseFormProcessorForAjaxResult;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.util.AjaxResultBuilder;
import org.hpg.libcommon.CH;
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

    @Autowired
    private IFormValidator formValidator;

    @Autowired
    private IPasswordService passwordService;

    /**
     * Indexing
     *
     * @param form
     * @return
     */
    @PostMapping(AdminUrls.ADMIN_USER_MANAGEMENT_INDEX)
    @ResponseBody
    public AjaxResult indexUsers(UsersIndexForm form) {
        return BaseFormProcessorForAjaxResult.<UsersIndexForm>instanceForAjaxResult()
                .formValidator(formValidator)
                .formProcessor(fm -> {
                    // Return sucess result. TODO Set message properly
                    return AjaxResultBuilder.successInstance()
                            .resultObject(null)
                            .build();
                })
                .execute(form);
    }

    /**
     * Add/Update user action
     *
     * @param form
     * @return
     */
    @PostMapping(AdminUrls.ADMIN_USER_MANAGEMENT_ADD_UPDATE)
    @ResponseBody
    public AjaxResult addUpdateUser(UserAddUpdateForm form) {

        return BaseFormProcessorForAjaxResult.<UserAddUpdateForm>instanceForAjaxResult()
                .formValidator(formValidator)
                .transactional(false)
                .formProcessor(fm -> {
                    MendelUser userToCreateOrUpdate = parseUserDtoFromForm(fm);
                    // Save
                    MendelUser savedUser = fm.getToCreateUser() ? userService.createUser(userToCreateOrUpdate) : userService.updateUser(userToCreateOrUpdate);
                    // Grant privileges
                    List<MendelPrivilege> grantedPrivileges = parseGrantedPrivilegesFromForm(fm);
                    userService.grantUserWithPrivileges(savedUser, grantedPrivileges);
                    // Return sucess result. TODO Set message properly
                    return AjaxResultBuilder.successInstance()
                            .oneSuccessMessage("User has been successfully saved")
                            .build();
                })
                .execute(form);
    }

    /**
     * Delete user action
     *
     * @param form
     * @return
     */
    @PostMapping(AdminUrls.ADMIN_USER_MANAGEMENT_DELETE)
    @ResponseBody
    public AjaxResult deleteUser(UserDeleteForm form) {
        // TODO Implememt
        return BaseFormProcessorForAjaxResult.<UserDeleteForm>instanceForAjaxResult()
                .formValidator(formValidator)
                .transactional(false)
                .formProcessor(fm -> {
                    userService.deleteUsers(fm.getUserIdsToDelete());
                    // Return sucess result. TODO Set message properly
                    return AjaxResultBuilder.successInstance()
                            .oneSuccessMessage("Users have been successfully deleted")
                            .build();
                })
                .execute(form);
    }

    /**
     * Parse from add/update form the DTO instance
     *
     * @param form
     * @return
     */
    private MendelUser parseUserDtoFromForm(UserAddUpdateForm form) {
        // TODO Implement properly based on annotations
        MendelUser user = new MendelUser();
        user.setDispName(form.getUserDipsName());
        user.setId(form.getUserId());
        user.setName(form.getUserName());
        user.setEncodedPassword(Optional.ofNullable(passwordService).map(srv -> srv.getEncodedPassword(form.getRawPassword())).orElse(form.getRawPassword()));
        user.setPassword(user.getEncodedPassword());
        user.setRole(MendelRole.USER);
        return user;
    }

    /**
     * Parse granted privileges to the user
     *
     * @param form
     * @return
     */
    private List<MendelPrivilege> parseGrantedPrivilegesFromForm(UserAddUpdateForm form) {
        if (CH.isEmpty(form.getGrantedPrivilegeIds())) {
            return new ArrayList();
        }
        return form.getGrantedPrivilegeIds().stream().map(MendelPrivilege::getPrivilegeFromId).collect(Collectors.toList());
    }
}