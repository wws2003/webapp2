/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.biz.web;

import org.hpg.project.constant.ProjectUrls;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for search
 *
 * @author trungpt
 */
@Controller
@RequestMapping(ProjectUrls.PROJECT_ROOT_URL)
public class ProjectController {

    @GetMapping(ProjectUrls.PROJECT_INDEX_URL)
    public String home() {
        return "project/home";
    }
}
