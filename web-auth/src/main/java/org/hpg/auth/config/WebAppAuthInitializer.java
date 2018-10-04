/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.WebApplicationInitializer;

/**
 * Secondary Spring web application initializer (for Spring-sec related stuffs)
 *
 * @author trungpt
 */
@Order(2)
public class WebAppAuthInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Aware of HttpSession expiration
        servletContext.addListener(new HttpSessionEventPublisher());
    }
}
