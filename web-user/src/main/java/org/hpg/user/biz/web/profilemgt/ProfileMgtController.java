/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.user.biz.web.profilemgt;

import org.hpg.common.biz.service.abstr.IScreenService;
import org.hpg.common.constant.MendelTransactionalLevel;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.user.biz.web.profilemgt.scrnservice.IProfileMgtScrnService;
import org.hpg.user.constant.UserUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for user profile management actions (now only change password)
 *
 * @author wws2003
 */
@Controller
@RequestMapping(UserUrls.USER_PROFILE_MANAGEMENT)
public class ProfileMgtController {

    @Autowired
    private IScreenService actionFlowService;

    @Autowired
    private IProfileMgtScrnService profileMgtScrnService;

    /**
     * Save new password
     *
     * @param password
     * @return
     */
    @PostMapping(UserUrls.USER_SAVE_PASSWORD)
    @ResponseBody
    public AjaxResult savePassword(@RequestParam(name = "password") String password) {
        return actionFlowService.executeSyncForAjaxResult(password,
                MendelTransactionalLevel.DEFAULT,
                profileMgtScrnService::savePassword);
    }
}
