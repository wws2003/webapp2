/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.home;

import org.hpg.admin.constant.AdminUrls;
import org.hpg.common.model.dto.principal.LoginInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for admin page
 *
 * @author trungpt
 */
@Controller
@RequestMapping(AdminUrls.ADMIN_ROOT_URL)
public class AdminController {

    /**
     * Home page for admin
     *
     * @param model
     * @return
     */
    @GetMapping(AdminUrls.ADMIN_HOME)
    public String admin(Model model) {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        LoginInfo user = (LoginInfo) auth.getPrincipal();
        model.addAttribute("userName", user.getUsername());
        return "admin/admin";
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
