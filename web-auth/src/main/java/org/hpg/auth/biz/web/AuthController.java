/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.web;

import org.hpg.auth.constant.AuthUrls;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Authentication controller
 *
 * @author trungpt
 */
@Controller
@RequestMapping(AuthUrls.AUTH_ROOT_URL)
public class AuthController {

    /**
     * Login page for user
     *
     * @return
     */
    @GetMapping(AuthUrls.USER_LOGIN)
    public String userLogin() {
        // TODO Auto logout
        return "auth/userLogin";
    }

    /**
     * Login page for admin
     *
     * @return
     */
    @GetMapping(AuthUrls.ADMIN_LOGIN)
    public String adminLogin() {
        // TODO Auto logout
        return "auth/adminLogin";
    }

    /**
     * 403-error page
     *
     * @return
     */
    @GetMapping(AuthUrls.FORBIDDEN)
    public String forbiden() {
        return "auth/forbidden";
    }
}
