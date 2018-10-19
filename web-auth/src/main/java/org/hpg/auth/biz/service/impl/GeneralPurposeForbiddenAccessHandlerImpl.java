/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hpg.auth.biz.service.abstr.IForbiddenAccessHandler;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.util.AjaxResultBuilder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

/**
 * Handler for both response body and forward/redirect
 *
 * @author trungpt
 */
public class GeneralPurposeForbiddenAccessHandlerImpl implements IForbiddenAccessHandler {

    private final String redirectUrl;

    /**
     * Construct with default redirect URL
     *
     * @param redirectUrl
     */
    public GeneralPurposeForbiddenAccessHandlerImpl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public void handleForbiddenAccess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (isToProduceAjaxResult(request)) {
            AjaxResult sessionErrorResult = createAjaxResultForSessionExpiredEvent();
            response.setContentType("application/json; charset=utf-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.append(serializeAjaxResult(sessionErrorResult));
            printWriter.flush();
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
        return AjaxResultBuilder.failedInstance(true)
                .oneErrorMessage("[MDL0099]Invalid session, due to expire or force logout")
                .resultObject(this.redirectUrl)
                .build();
    }

    /**
     * Get JSON format of Ajax result to send to the client
     *
     * @param ajaxResult
     * @return
     */
    private String serializeAjaxResult(AjaxResult ajaxResult) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(ajaxResult);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(DefaultSessionExpiredStrategyImpl.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
}
