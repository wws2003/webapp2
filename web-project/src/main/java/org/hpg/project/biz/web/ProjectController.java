/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.biz.web;

import org.hpg.common.biz.service.abstr.IUserSession;
import org.hpg.project.constant.ProjectUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for project page
 *
 * @author trungpt
 */
@Controller
@RequestMapping(ProjectUrls.PROJECT_ROOT_URL)
public class ProjectController {

    @Autowired
    private IUserSession userSession;

    @GetMapping(ProjectUrls.PROJECT_HOME_URL)
    public String home(Model model) {
        model.addAttribute("userName", userSession.getCurrentLoginInfo().getLoginUser().getName());
        return "project/home";
    }
}
