/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.user.biz.web;

import org.hpg.common.biz.service.abstr.IUserSession;
import org.hpg.user.constant.UserUrls;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Test sample of Session
    @Autowired
    private IUserSession userSession;

    /**
     * Home page for user
     *
     * @param model
     * @return
     */
    @GetMapping(UserUrls.USER_HOME)
    public String home(Model model) {
        model.addAttribute("userName", userSession.getCurrentLoginInfo().getLoginUser().getName());
        return "user/home";
    }

    /**
     * Page for profile management
     *
     * @return
     */
    @GetMapping(UserUrls.USER_PROFILE_MANAGEMENT)
    public String profileManagement() {
        return "user/profileManagement";
    }
}
