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
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.hpg.auth.biz.service.abstr.IAccessErrorHandler;

/**
 * Handler for session authentication failure
 *
 * @author wwws2003
 */
public class DefaultSessionExpiredStrategyImpl implements SessionInformationExpiredStrategy {

    /**
     * URL to redirect
     */
    private final IAccessErrorHandler forbiddenAccessHandler;

    public DefaultSessionExpiredStrategyImpl(IAccessErrorHandler forbiddenAccessHandler) {
        this.forbiddenAccessHandler = forbiddenAccessHandler;
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletResponse response = event.getResponse();
        HttpServletRequest request = event.getRequest();
        forbiddenAccessHandler.handleForbiddenAccess(request, response);
    }
}
