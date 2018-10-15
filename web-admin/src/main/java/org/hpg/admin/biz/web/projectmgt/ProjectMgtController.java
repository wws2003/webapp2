/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.projectmgt;

import org.hpg.admin.biz.web.projectmgt.service.IProjectMgtScrnService;
import org.hpg.admin.constant.AdminUrls;
import org.hpg.common.biz.service.abstr.IScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private IProjectMgtScrnService userMgtScrnService;
}
