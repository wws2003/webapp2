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
import org.hpg.common.model.dto.principal.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

/**
 * Logout success handler
 *
 * @author trungpt
 */
public class DefaultLogoutHandlerImpl extends SimpleUrlLogoutSuccessHandler {

    @Autowired
    private ILoginLogoutMessageService messageService;

    public DefaultLogoutHandlerImpl(String defaultTargetUrl) {
        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Send message
        // (Authentication instance is still usable, though credential is set to null already)
        LoginInfo authenticatedLoginInfo = ((MendelUserDetails) authentication.getPrincipal()).getLoginInfo();
        messageService.notifyUserLogout(authenticatedLoginInfo.getLoginUser().getId());

        super.handle(request, response, authentication);
    }

    @Override
    public final void setAlwaysUseDefaultTargetUrl(boolean alwaysUseDefaultTargetUrl) {
        // Make this method final just to make warning in constructor disappear
        super.setAlwaysUseDefaultTargetUrl(alwaysUseDefaultTargetUrl);
    }

    @Override
    public final void setDefaultTargetUrl(String defaultTargetUrl) {
        // Make this method final just to make warning in constructor disappear
        super.setDefaultTargetUrl(defaultTargetUrl);
    }
}
