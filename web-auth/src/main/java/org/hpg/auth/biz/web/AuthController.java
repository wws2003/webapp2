/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.web;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hpg.auth.constant.AuthUrls;
import org.hpg.common.biz.service.abstr.IUserSession;
import org.hpg.libcommon.MapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.hpg.auth.biz.service.abstr.IAccessErrorHandler;

/**
 * Authentication controller
 *
 * @author trungpt
 */
@Controller
@RequestMapping(AuthUrls.AUTH_ROOT_URL)
public class AuthController {

    @Autowired
    private IUserSession session;

    @Autowired
    private IAccessErrorHandler forbiddenAccessHandler;

    /**
     * Login page for user
     *
     * @return
     */
    @GetMapping(AuthUrls.USER_LOGIN)
    public String userLogin() {
        // Auto logout
        session.invalidate();
        return "auth/userLogin";
    }

    /**
     * Login page for admin
     *
     * @return
     */
    @GetMapping(AuthUrls.ADMIN_LOGIN)
    public String adminLogin() {
        // Auto logout
        session.invalidate();
        return "auth/adminLogin";
    }

    /**
     * 403-error page for POST/GET request
     *
     * @param request
     * @param response
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    @RequestMapping(AuthUrls.FORBIDDEN_ACTION)
    public void forbidenAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        forbiddenAccessHandler.handleForbiddenAccess(request, response);
    }

    @RequestMapping(AuthUrls.FORBIDDEN_PAGE)
    public String forbiddenPage() {
        // Return HTML page
        return "auth/forbidden";
    }

    /**
     * Session error
     *
     * @return
     */
    @GetMapping(AuthUrls.USER_SESSION_AUTH_FAILURE_URL)
    public ModelAndView userSessionAuthFailure() {
        return new ModelAndView("auth/sessionAuthFail", MapBuilder.instance(new HashMap<String, String>()).add("reloginUrl", "auth/userLogin").build());
    }

    /**
     * Session error
     *
     * @return
     */
    @GetMapping(AuthUrls.ADMIN_SESSION_AUTH_FAILURE_URL)
    public ModelAndView adminSessionAuthFailure() {
        return new ModelAndView("auth/sessionAuthFail", MapBuilder.instance(new HashMap<String, String>()).add("reloginUrl", "auth/adminLogin").build());
    }
}
