/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.home;

import java.util.HashMap;
import org.hpg.admin.constant.AdminUrls;
import org.hpg.common.biz.service.abstr.IScreenService;
import org.hpg.common.biz.service.abstr.IUserSession;
import org.hpg.common.constant.MendelTransactionalLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for admin pages
 *
 * @author trungpt
 */
@Controller
@RequestMapping(AdminUrls.ADMIN_ROOT_URL)
public class AdminController {

    @Autowired
    private IUserSession userSession;

    @Autowired
    private IScreenService screenService;

    /**
     * Home page for admin
     *
     * @param model
     * @return
     */
    @GetMapping(AdminUrls.ADMIN_HOME)
    public String home(Model model) {
        model.addAttribute("userName", userSession.getCurrentLoginInfo().getLoginUser().getName());
        return "admin/home";
    }

    /**
     * User management page
     *
     * @return
     */
    @GetMapping(AdminUrls.ADMIN_USER_MANAGEMENT)
    public ModelAndView userManagement() {
        String userMgtPage = "admin/userManagement";
        // Execute for result
        return screenService.executeSyncForModelAndView((Long) 0L,
                MendelTransactionalLevel.DEFAULT_READONLY,
                fm -> new ModelAndView(userMgtPage),
                userMgtPage);
    }

    /**
     * System management page
     *
     * @return
     */
    @GetMapping(AdminUrls.ADMIN_PROJECT_MANAGEMENT)
    public ModelAndView projectManagement() {
        String projectMgtPage = "admin/projectManagement";
        // Execute for result
        return screenService.executeSyncForModelAndView((Long) 0L,
                MendelTransactionalLevel.DEFAULT_READONLY,
                fm -> new ModelAndView(projectMgtPage),
                projectMgtPage);
    }

    /**
     * System management page
     *
     * @return
     */
    @GetMapping(AdminUrls.ADMIN_SYSTEM_MANAGEMENT)
    public ModelAndView systemManagement() {
        return new ModelAndView("admin/systemManagement", new HashMap());
    }
}
