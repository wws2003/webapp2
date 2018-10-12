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
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.util.AjaxResultBuilder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * Handler for session authentication failure
 *
 * @author wwws2003
 */
public class DefaultSessionExpiredStrategyImpl implements SessionInformationExpiredStrategy {

    /**
     * URL to redirect
     */
    private final String redirectUrl;

    public DefaultSessionExpiredStrategyImpl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletResponse response = event.getResponse();
        HttpServletRequest request = event.getRequest();
        if (isToProduceAjaxResult(request)) {
            response.getOutputStream().print(createAjaxResultForSessionExpiredEvent().toString());
        } else {
            RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            redirectStrategy.sendRedirect(request, response, redirectUrl);
        }
    }

    /**
     * Detect if request is to retrieve AJAX result
     *
     * @param request
     * @param exception
     * @return
     */
    private boolean isToProduceAjaxResult(HttpServletRequest request) {
        // Ref. https://doanduyhai.wordpress.com/2012/04/21/spring-security-part-vi-session-timeout-handling-for-ajax-calls/
        String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");
        return ("XMLHttpRequest".equals(ajaxHeader));
    }

    /**
     * Create AjaxResult
     *
     * @return
     */
    private AjaxResult createAjaxResultForSessionExpiredEvent() {
        return AjaxResultBuilder.failedInstance(true).oneErrorMessage("[MDL0099]Invalid session, due to expire or force logout").build();
    }
}
