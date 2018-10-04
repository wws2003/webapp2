/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.usermgt;

import org.hpg.admin.biz.web.usermgt.form.UserAddUpdateForm;
import org.hpg.admin.biz.web.usermgt.form.UserDeleteForm;
import org.hpg.admin.biz.web.usermgt.form.UserDetailForm;
import org.hpg.admin.biz.web.usermgt.form.UsersIndexForm;
import org.hpg.admin.biz.web.usermgt.scrnservice.IUserMgtScrnService;
import org.hpg.admin.constant.AdminUrls;
import org.hpg.common.biz.service.abstr.IScreenService;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.constant.MendelTransactionalLevel;
import org.hpg.common.model.dto.web.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private IScreenService actionFlowService;

    @Autowired
    private IUserMgtScrnService userMgtScrnService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Indexing
     *
     * @param form
     * @return
     */
    @PostMapping(AdminUrls.ADMIN_USER_MANAGEMENT_INDEX)
    @ResponseBody
    public AjaxResult indexUsers(@RequestBody UsersIndexForm form) {
        return actionFlowService.executeSyncForAjaxResult(form,
                MendelTransactionalLevel.DEFAULT_READONLY,
                userMgtScrnService::indexUsers);
    }

    /**
     * Get user detail info
     *
     * @param form
     * @return
     */
    @PostMapping(AdminUrls.ADMIN_USER_MANAGEMENT_USER_DETAILS)
    @ResponseBody
    public AjaxResult getUserDetailInfo(@RequestBody UserDetailForm form) {
        return actionFlowService.executeSyncForAjaxResult(form,
                MendelTransactionalLevel.DEFAULT_READONLY,
                userMgtScrnService::getUserDetailInfo);
    }

    /**
     * Get all privileges can be set to user
     *
     * @return
     */
    @GetMapping(AdminUrls.ADMIN_USER_MANAGEMENT_ALL_USER_PRIVS)
    @ResponseBody
    public AjaxResult getAllUserPrivileges() {
        return actionFlowService.executeSyncForAjaxResult(MendelRole.USER,
                MendelTransactionalLevel.DEFAULT_READONLY,
                userMgtScrnService::getAllPrivileges);
    }

    /**
     * Get all privileges can be set to user
     *
     * @param form
     * @return
     */
    @PostMapping(AdminUrls.ADMIN_USER_MANAGEMENT_ADD_UPDATE)
    @ResponseBody
    public AjaxResult addUpdateUser(@RequestBody UserAddUpdateForm form) {
        return actionFlowService.executeSyncForAjaxResult(form,
                MendelTransactionalLevel.DEFAULT,
                userMgtScrnService::addUpdateUser,
                (fm, ret) -> String.format("User has been successfully saved {%s} for {%s}", fm, ret));
    }

    /**
     * Delete user action
     *
     * @param form
     * @return
     */
    @PostMapping(AdminUrls.ADMIN_USER_MANAGEMENT_DELETE)
    @ResponseBody
    public AjaxResult deleteUser(@RequestBody UserDeleteForm form) {
        return actionFlowService.executeSyncForAjaxResult(form,
                MendelTransactionalLevel.DEFAULT,
                userMgtScrnService::deleteUser,
                (fm, ret) -> String.format("Users have been successfully deleted {%s} for {%s}", fm, ret));
    }
}
