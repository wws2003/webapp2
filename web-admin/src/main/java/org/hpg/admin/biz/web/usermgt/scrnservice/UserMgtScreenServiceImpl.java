/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.usermgt.scrnservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.hpg.admin.biz.web.common.form.PageRequestForm;
import org.hpg.admin.biz.web.common.form.SimpleDeleteByIDForm;
import org.hpg.admin.biz.web.common.form.SimpleRequestByIDForm;
import org.hpg.admin.biz.web.usermgt.form.UserAddUpdateForm;
import org.hpg.admin.biz.web.usermgt.form.UserForceLogoutForm;
import org.hpg.admin.biz.web.usermgt.scrnmodel.ScrnUserDetail;
import org.hpg.admin.biz.web.usermgt.scrnmodel.ScrnUserRecord;
import org.hpg.common.biz.service.abstr.ILoginUserService;
import org.hpg.common.biz.service.abstr.IPagingService;
import org.hpg.common.biz.service.abstr.IPasswordService;
import org.hpg.common.biz.service.abstr.IPrivilegeService;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.constant.MendelPrivilege;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.common.util.AjaxResultBuilder;
import org.hpg.libcommon.CH;
import org.hpg.libcommon.DateFormatConst;
import org.hpg.libcommon.DateUtil;
import org.hpg.libcommon.Tuple;
import org.hpg.libcommon.Tuple3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
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
    private IPrivilegeService privilegeService;

    @Autowired
    private IPasswordService passwordService;

    @Autowired
    private ILoginUserService loginUserService;

    @Override
    public AjaxResult indexUsers(PageRequestForm form) throws MendelRuntimeException {
        List<Order> orders = Arrays.asList(Order.asc("role.id"), Order.asc("name").ignoreCase());
        Sort sort = Sort.by(orders);
        Pageable pageRequest = PageRequest.of(form.getPageNumber() - 1,
                form.getRecordCountPerPage(),
                sort);

        // Get data
        Page<ScrnUserRecord> currentPage = userPagingService.getPage(pageRequest)
                .map(
                        usr -> new ScrnUserRecord(
                                usr,
                                loginUserService.getLoginInfo(usr.getId())
                                        .map(usrInfo -> usrInfo.getLoginTimeStamp())
                                        .map(tmsp -> DateUtil.dateTime2String(tmsp, DateFormatConst.FULL_SEC_A)).orElse("")
                        )
                );

        return AjaxResultBuilder.successInstance()
                .resultObject(currentPage)
                .build();
    }

    @Override
    public AjaxResult getUserDetailInfo(SimpleRequestByIDForm form) throws MendelRuntimeException {
        List<MendelPrivilege> allGrantablePrivs = privilegeService.findPrivilegesForRole(MendelRole.USER);
        ScrnUserDetail model = new ScrnUserDetail();

        // Get user by id
        MendelUser user = userService.findUserById(form.getElementId()).orElseThrow(() -> new MendelRuntimeException("Can not find user to update with id " + form.getElementId()));
        // Set attributes to model
        model.setId(user.getId());
        model.setName(user.getName());
        model.setDispName(user.getDispName());
        // Get privs
        List<MendelPrivilege> grantedPrivs = userService.getUserGrantedPrivileges(user.getId());
        List<MendelPrivilege> notGrantedPrivs = allGrantablePrivs.stream()
                .filter(priv -> !(grantedPrivs.stream().anyMatch(prv -> prv.getId() == priv.getId())))
                .collect(Collectors.toList());
        // Set privileges into final result
        model.setGrantedPrivileges(this.serializeMendelPrivileges(grantedPrivs));
        model.setRemainingGrantablePrivileges(this.serializeMendelPrivileges(notGrantedPrivs));

        // Return sucess result. TODO Set message properly
        return AjaxResultBuilder.successInstance()
                .resultObject(model)
                .build();
    }

    @Override
    public AjaxResult getAllPrivileges(MendelRole role) throws MendelRuntimeException {
        // Return sucess result. TODO Set message properly
        // Map ENUM to tuple here instead of making enum depends on JSON by annotation
        return AjaxResultBuilder.successInstance()
                .resultObject(this.serializeMendelPrivileges(privilegeService.findPrivilegesForRole(role)))
                .build();
    }

    @Override
    public AjaxResult addUpdateUser(UserAddUpdateForm form) throws MendelRuntimeException {
        MendelUser userToCreateOrUpdate = parseUserDtoFromForm(form);
        // Save
        MendelUser savedUser = form.getIdForm().getElementId() < 0 ? userService.createUser(userToCreateOrUpdate) : userService.updateUser(userToCreateOrUpdate);
        // TODO Grant privileges
        List<MendelPrivilege> grantedPrivileges = parseGrantedPrivilegesFromForm(form);
        userService.grantUserWithPrivileges(savedUser, grantedPrivileges);
        // userService.grantUserWithPrivileges(savedUser, grantedPrivileges);
        // Return sucess result. TODO Set message properly
        return AjaxResultBuilder.successInstance()
                .oneSuccessMessage("User has been successfully saved")
                .build();
    }

    @Override
    public AjaxResult deleteUsers(SimpleDeleteByIDForm form) throws MendelRuntimeException {
        userService.deleteUsers(form.getElementIds());
        // Return sucess result. TODO Set message properly
        return AjaxResultBuilder.successInstance()
                .oneSuccessMessage("Users have been successfully deleted")
                .build();

    }

    @Override
    public AjaxResult forceLogoutUsers(UserForceLogoutForm form) throws MendelRuntimeException {
        loginUserService.forceLogout(form.getUserIdsToForceLogout());
        // Return sucess result. TODO Set message properly
        return AjaxResultBuilder.successInstance()
                .oneSuccessMessage("Users have been successfully forced to logout")
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
        user.setId(form.getIdForm().getElementId());
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

    /**
     * Parse privileges into JSON serializable format
     *
     * @param privs
     * @return
     */
    private List<Tuple3<Integer, String, String>> serializeMendelPrivileges(List<MendelPrivilege> privs) {
        return privs.stream()
                .map(priv -> Tuple.newTuple(priv.getId(), priv.getCode(), priv.getDispName()))
                .collect(Collectors.toList());
    }
}
