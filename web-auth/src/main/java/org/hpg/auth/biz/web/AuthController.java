/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.web;

import org.hpg.common.constant.MendelRole;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Authentication controller
 *
 * @author trungpt
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    /**
     * Login page for admin
     *
     * @return
     */
    @GetMapping("/userLogin")
    public String userLogin() {
        // Test reference to WEB-COMMON: Still does not work
        return "auth/userLogin";
    }

    /**
     * Login page for user
     *
     * @return
     */
    @GetMapping("/adminLogin")
    public String adminLogin() {
        // Test import
        MendelRole e = MendelRole.ADMIN;
        return "auth/adminLogin";
    }
}
