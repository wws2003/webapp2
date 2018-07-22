/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.user.biz.web;

import org.hpg.common.model.dto.principal.LoginInfo;
import org.hpg.user.constant.UserUrls;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for user pages
 *
 * @author trungpt
 */
@Controller
@RequestMapping(UserUrls.USER_ROOT_URL)
public class UserController {

    /**
     * Home page for user
     *
     * @param model
     * @return
     */
    @GetMapping(UserUrls.USER_HOME)
    public String admin(Model model) {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        LoginInfo user = (LoginInfo) auth.getPrincipal();
        model.addAttribute("userName", user.getUsername());
        return "user/home";
    }
}
