/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.usermgt.scrnservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.hpg.admin.biz.web.usermgt.form.UserAddUpdateForm;
import org.hpg.admin.biz.web.usermgt.form.UserDeleteForm;
import org.hpg.admin.biz.web.usermgt.form.UserDetailForm;
import org.hpg.admin.biz.web.usermgt.form.UsersIndexForm;
import org.hpg.admin.biz.web.usermgt.scrnmodel.ScrnUserDetail;
import org.hpg.admin.biz.web.usermgt.scrnmodel.ScrnUserRecord;
import org.hpg.common.biz.service.abstr.IPagingService;
import org.hpg.common.biz.service.abstr.IPasswordService;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.constant.MendelPrivilege;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.common.util.AjaxResultBuilder;
import org.hpg.libcommon.CH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Implementation for service dedicated to the user management screen
 * (Convention: screen dedicated service is annotated with @Service instead of
 * being created explicitly by the bean configuration)
 *
 * @author wws2003
 */
@Service
public class UserMgtScreenServiceImpl implements IUserMgtScrnService {

    @Autowired
    private IPagingService<MendelUser> userPagingService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IPasswordService passwordService;

    @Override
    public AjaxResult indexUsers(UsersIndexForm form) throws MendelRuntimeException {
        Pageable pageRequest = PageRequest.of(form.getPageNumber() - 1,
                form.getRecordCountPerPage(),
                Sort.Direction.ASC,
                "name");

        // Get data
        // TODO Detect login status properly
        Page<ScrnUserRecord> currentPage = userPagingService.getPage(pageRequest)
                .map(usr -> new ScrnUserRecord(usr, false));

        return AjaxResultBuilder.successInstance()
                .resultObject(currentPage)
                .build();
    }

    @Override
    public AjaxResult getUserDetailInfo(UserDetailForm form) throws MendelRuntimeException {
        // TODO Get model properly
        // Separate update and delete
        boolean isAdding = form.getUserId() <= 0;
        List<MendelPrivilege> allGrantablePrivs = userService.findPrivilegesForRole(MendelRole.USER);
        ScrnUserDetail model = new ScrnUserDetail();

        if (!isAdding) {
            MendelUser user = userService.findUserById(form.getUserId()).orElseThrow(() -> new MendelRuntimeException("Can not find user to update"));
            model.setId(user.getId());
            model.setName(user.getName());
            model.setDispName(user.getDispName());
            // TODO Handle privs
        } else {
            model.setRemainingGrantablePrivileges(allGrantablePrivs);
        }

        // Return sucess result. TODO Set message properly
        return AjaxResultBuilder.successInstance()
                .resultObject(model)
                .build();
    }

    @Override
    public AjaxResult addUpdateUser(UserAddUpdateForm form) throws MendelRuntimeException {
        MendelUser userToCreateOrUpdate = parseUserDtoFromForm(form);
        // Save
        MendelUser savedUser = form.getToCreateUser() ? userService.createUser(userToCreateOrUpdate) : userService.updateUser(userToCreateOrUpdate);
        // Grant privileges
        List<MendelPrivilege> grantedPrivileges = parseGrantedPrivilegesFromForm(form);
        userService.grantUserWithPrivileges(savedUser, grantedPrivileges);
        // Return sucess result. TODO Set message properly
        return AjaxResultBuilder.successInstance()
                .oneSuccessMessage("User has been successfully saved")
                .build();
    }

    @Override
    public AjaxResult deleteUser(UserDeleteForm form) throws MendelRuntimeException {
        userService.deleteUsers(form.getUserIdsToDelete());
        // Return sucess result. TODO Set message properly
        return AjaxResultBuilder.successInstance()
                .oneSuccessMessage("Users have been successfully deleted")
                .build();

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
        user.setDispName(form.getUserDispName());
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
        return form.getGrantedPrivilegeIds()
                .stream()
                .map(MendelPrivilege::getPrivilegeFromId)
                .collect(Collectors.toList());
    }
}
