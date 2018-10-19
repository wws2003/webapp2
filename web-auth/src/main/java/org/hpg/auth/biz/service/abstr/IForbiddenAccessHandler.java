/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.abstr;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handler for forbidden access (invalid session, invalid CSRF token...)
 *
 * @author trungpt
 */
public interface IForbiddenAccessHandler {

    /**
     * Handle request to forbidden resource. Default do nothing
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    default void handleForbiddenAccess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    }
}
