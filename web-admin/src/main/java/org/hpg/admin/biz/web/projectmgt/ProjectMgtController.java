/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.projectmgt;

import org.hpg.admin.biz.web.projectmgt.form.ProjectsIndexForm;
import org.hpg.admin.biz.web.projectmgt.form.UserSearchForm;
import org.hpg.admin.biz.web.projectmgt.service.IProjectMgtScrnService;
import org.hpg.admin.constant.AdminUrls;
import org.hpg.common.biz.service.abstr.IScreenService;
import org.hpg.common.constant.MendelTransactionalLevel;
import org.hpg.common.model.dto.web.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for project management actions
 *
 * @author trungpt
 */
@Controller
@RequestMapping(AdminUrls.ADMIN_PROJECT_MANAGEMENT_ROOT_URL)
public class ProjectMgtController {

    @Autowired
    private IScreenService actionFlowService;

    @Autowired
    private IProjectMgtScrnService projectMgtScrnService;

    /**
     * Indexing
     *
     * @param form
     * @return
     */
    @PostMapping(AdminUrls.ADMIN_PROJECT_MANAGEMENT_INDEX)
    @ResponseBody
    public AjaxResult indexUsers(@RequestBody ProjectsIndexForm form) {
        return actionFlowService.executeSyncForAjaxResult(form,
                MendelTransactionalLevel.DEFAULT_READONLY,
                projectMgtScrnService::index);
    }

    /**
     * Get all privileges can be set to user
     *
     * @param form
     * @return
     */
    @PostMapping(AdminUrls.ADMIN_PROJECT_MANAGEMENT_SEARCH_USER)
    @ResponseBody
    public AjaxResult searchForUsers(UserSearchForm form) {
        return actionFlowService.executeSyncForAjaxResult(form,
                MendelTransactionalLevel.DEFAULT_READONLY,
                projectMgtScrnService::searchForUsers);
    }
}
