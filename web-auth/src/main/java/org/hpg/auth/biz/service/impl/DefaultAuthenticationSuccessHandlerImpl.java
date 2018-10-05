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
import org.hpg.auth.biz.service.abstr.ILoginLogoutMessageService;
import org.hpg.auth.model.MendelUserDetails;
import org.hpg.common.biz.service.abstr.IUserSession;
import org.hpg.common.model.dto.principal.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
     * Wiring a session scoped bean here is really possible ?
     */
    @Autowired
    private IUserSession userSession;

    @Autowired
    private ILoginLogoutMessageService messageService;

    /**
     * Constructor with default URL
     *
     * @param defaultTargetUrl
     */
    public DefaultAuthenticationSuccessHandlerImpl(String defaultTargetUrl) {
        super();
        this.setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest hsr, HttpServletResponse hsr1, Authentication a) throws IOException, ServletException {
        super.onAuthenticationSuccess(hsr, hsr1, a);
        // Retrieve login info
        LoginInfo authenticatedLoginInfo = ((MendelUserDetails) a.getPrincipal()).getLoginInfo();
        userSession.setCurrentLoginInfo(authenticatedLoginInfo);

        // Notify
        messageService.notifyUserLogin(authenticatedLoginInfo.getLoginUser().getId(), authenticatedLoginInfo.getLoginTimeStamp());
    }

    @Override
    public final void setDefaultTargetUrl(String defaultTargetUrl) {
        // Make this method final just to make warning in constructor disappear
        super.setDefaultTargetUrl(defaultTargetUrl);
    }
}
