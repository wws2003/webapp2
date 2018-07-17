/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.home;

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
@RequestMapping("admin")
public class AdminController {

    /**
     * Home page for admin
     *
     * @param model
     * @return
     */
    @GetMapping("/home")
    public String admin(Model model) {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        LoginInfo user = (LoginInfo) auth.getPrincipal();
        model.addAttribute("userName", user.getUsername());
        return "admin/admin";
    }
}
