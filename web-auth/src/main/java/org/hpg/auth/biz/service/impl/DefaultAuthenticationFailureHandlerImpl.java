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
import org.hpg.auth.constant.AuthUrls;
import org.hpg.auth.constant.AuthenticationErrorType;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * Sample authentication failure handler (currently just logging)
 *
 * @author trungpt
 */
public class DefaultAuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

    /**
     * Default constructor
     *
     * @param defaultFailureUrl
     */
    public DefaultAuthenticationFailureHandlerImpl(String defaultFailureUrl) {
        super(defaultFailureUrl);
    }

    /**
     * Default constructor
     */
    public DefaultAuthenticationFailureHandlerImpl() {
        super();
        this.setDefaultFailureUrl(getAuthenticationFailureUrl(AuthenticationErrorType.UNKNOWN_ERROR));
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest hsr, HttpServletResponse hsr1, AuthenticationException ae) throws IOException, ServletException {
        // TODO Implement properly
        if (ae instanceof BadCredentialsException) {
            this.setDefaultFailureUrl(getAuthenticationFailureUrl(AuthenticationErrorType.WRONG_USERNAME_PASSWORD));
        }
        if (ae instanceof UsernameNotFoundException) {
            // Actually can not see this condition even if UserDetailsService threw UsernameNotFoundException !?
            this.setDefaultFailureUrl(getAuthenticationFailureUrl(AuthenticationErrorType.WRONG_USERNAME_PASSWORD));
        }
        if (ae instanceof AccountStatusException) {
            this.setDefaultFailureUrl(getAuthenticationFailureUrl(AuthenticationErrorType.INVALID_ACCOUNT_STATUS));
        }
        super.onAuthenticationFailure(hsr, hsr1, ae);
    }

    /**
     * Get authentication failure URL by error type
     *
     * @param authenticationErrorType
     * @return
     */
    private String getAuthenticationFailureUrl(AuthenticationErrorType authenticationErrorType) {
        return String.join("",
                AuthUrls.AUTH_ROOT_URL,
                AuthUrls.ADMIN_LOGIN_ERROR,
                "?error=",
                String.valueOf(authenticationErrorType.getCode()));
    }
}
