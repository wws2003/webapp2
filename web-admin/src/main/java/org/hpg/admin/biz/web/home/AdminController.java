/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.home;

import org.springframework.stereotype.Controller;
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
     * @return
     */
    @GetMapping("/home")
    public String admin() {
        return "admin/admin";
    }
}
