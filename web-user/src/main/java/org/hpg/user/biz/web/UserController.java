/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.user.biz.web;

import org.hpg.common.biz.service.abstr.IUserSession;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.util.AjaxResultBuilder;
import org.hpg.user.constant.UserUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * TEST method (for the case session expired)
     *
     * @return
     */
    @PostMapping(UserUrls.USER_HOME + "/testajax")
    @ResponseBody
    public AjaxResult testAjax() {
        return AjaxResultBuilder.successInstance().resultObject("12345").build();
    }
}
