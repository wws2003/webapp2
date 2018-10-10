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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * Handler for session authentication failure
 *
 * @author wwws2003
 */
public class SessionAuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (isToProduceAjaxResult(request, exception)) {
            response.getOutputStream().print("XXXXXXXXXXXX");
        } else {
            response.sendRedirect("");
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

        return true;
    }
}
