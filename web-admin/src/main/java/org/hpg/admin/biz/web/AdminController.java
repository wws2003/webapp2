/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web;

import org.hpg.admin.constant.AdminUrls;
import org.hpg.common.biz.service.abstr.IUserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for admin pages
 *
 * @author trungpt
 */
@Controller
@RequestMapping(AdminUrls.ADMIN_ROOT_URL)
public class AdminController {

    // Test sample of Session
    @Autowired
    private IUserSession userSession;

    /**
     * Home page for admin
     *
     * @param model
     * @return
     */
    @GetMapping(AdminUrls.ADMIN_HOME)
    public String home(Model model) {
        model.addAttribute("userName", userSession.getCurrentLoginInfo().getUsername());
        return "admin/home";
    }

    /**
     * User management page
     *
     * @param model
     * @return
     */
    @GetMapping(AdminUrls.ADMIN_USER_MANAGEMENT)
    public String userManagement(Model model) {
        return "admin/userManagement";
    }

    /**
     * System management page
     *
     * @param model
     * @return
     */
    @GetMapping(AdminUrls.ADMIN_SYSTEM_MANAGEMENT)
    public String systemManagement(Model model) {
        return "admin/systemManagement";
    }
}
