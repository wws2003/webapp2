/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hpg.common.model.dto.principal.LoginInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * Implementation for authentication success handler
 *
 * @author trungpt
 */
public class DefaultAuthenticationSuccessHandlerImpl extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Default login info
     */
    private LoginInfo mCurrentLoginInfo = null;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest hsr, HttpServletResponse hsr1, Authentication a) throws IOException, ServletException {
        // Retrieve login info
        mCurrentLoginInfo = (LoginInfo) a;
        super.onAuthenticationSuccess(hsr, hsr1, a);
    }

}
