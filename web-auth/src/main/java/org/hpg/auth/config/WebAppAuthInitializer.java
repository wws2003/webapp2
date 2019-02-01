/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.config;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.core.Conventions;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

/**
 * Secondary Spring web application initializer (for Spring-sec related stuffs)
 *
 * @author trungpt
 */
@Order(2)
public class WebAppAuthInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Add servler filter
        registerServletFilter(servletContext, new DelegatingFilterProxy("springSecurityFilterChain"));
        // Aware of HttpSession expiration
        servletContext.addListener(new HttpSessionEventPublisher());
    }

    /**
     * Copy from AbstractDispatcherServletInitializer
     *
     * @param servletContext
     * @param filter
     * @return
     */
    protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, Filter filter) {
        String filterName = Conventions.getVariableName(filter);
        FilterRegistration.Dynamic registration = servletContext.addFilter(filterName, filter);

        if (registration == null) {
            int counter = 0;
            while (registration == null) {
                if (counter == 100) {
                    throw new IllegalStateException("Failed to register filter with name '" + filterName + "'. "
                            + "Check if there is another filter registered under the same name.");
                }
                registration = servletContext.addFilter(filterName + "#" + counter, filter);
                counter++;
            }
        }

        registration.setAsyncSupported(true);
        registration.addMappingForServletNames(getDispatcherTypes(), false, getServletName());
        return registration;
    }

    /**
     * Copy from AbstractDispatcherServletInitializer
     *
     * @return
     */
    private EnumSet<DispatcherType> getDispatcherTypes() {
        return EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ASYNC);
    }

    /**
     * Copy from AbstractDispatcherServletInitializer
     *
     * @return
     * @see #registerDispatcherServlet(ServletContext)
     */
    protected String getServletName() {
        return AbstractDispatcherServletInitializer.DEFAULT_SERVLET_NAME;
    }
}
