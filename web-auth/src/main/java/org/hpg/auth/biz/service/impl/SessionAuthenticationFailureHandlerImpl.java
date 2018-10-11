/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import java.io.IOException;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Handler for session authentication failure
 *
 * @author wwws2003
 */
public class SessionAuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    /**
     * URL to redirect
     */
    private final String redirectUrl;

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @PostConstruct
    public void init() {
        // EXPERIMENTAL
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = this.requestMappingHandlerMapping.getHandlerMethods();

        handlerMethods.entrySet().forEach((item) -> {
            RequestMappingInfo mapping = item.getKey();
            HandlerMethod method = item.getValue();

            mapping.getPatternsCondition().getPatterns().stream().map((urlPattern) -> {
                System.out.println(method.getBeanType().getName() + "#" + method.getMethod().getName() + " <-- " + urlPattern);
                //add to list of matching METHODS
                return urlPattern;
            }).filter((urlPattern) -> (urlPattern.equals("some specific url")));
        });
    }

    public SessionAuthenticationFailureHandlerImpl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (isToProduceAjaxResult(request, exception)) {
            response.getOutputStream().print("XXXXXXXXXXXX");
        } else {
            response.sendRedirect(redirectUrl);
        }
    }

    /**
     * Detect if request is to retrieve AJAX result
     *
     * @param request
     * @param exception
     * @return
     */
    private boolean isToProduceAjaxResult(HttpServletRequest request, AuthenticationException exception) {
        // TODO Implement
        String uri = request.getRequestURI();
        return false;
    }
}
