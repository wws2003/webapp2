/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hpg.auth.constant.AuthUrls;
import org.hpg.auth.constant.AuthenticationErrorType;
import org.hpg.common.constant.MendelRole;
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
     * Map from role to authentication error URL
     */
    private final Map<Integer, String> mAuthErrorUrlMap = new HashMap<Integer, String>() {
        {
            put(MendelRole.ADMIN.getCode(), AuthUrls.ADMIN_LOGIN_ERROR);
            put(MendelRole.USER.getCode(), AuthUrls.USER_LOGIN_ERROR);
        }
    };

    /**
     * The role attempted to login with
     */
    private final MendelRole mAttemptedLoginRole;

    /**
     * Default constructor
     *
     * @param attemptedLoginRole
     */
    public DefaultAuthenticationFailureHandlerImpl(MendelRole attemptedLoginRole) {
        super();
        mAttemptedLoginRole = attemptedLoginRole;
        this.setDefaultFailureUrl(getAuthenticationFailureUrl(AuthenticationErrorType.UNKNOWN_ERROR));
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest hsr, HttpServletResponse hsr1, AuthenticationException ae) throws IOException, ServletException {
        // TODO Implement properly
        // TODO Detect role trying to login for proper redirect URL
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
                Optional.ofNullable(mAuthErrorUrlMap.get(mAttemptedLoginRole.getCode())).orElse(AuthUrls.ADMIN_LOGIN_ERROR),
                "?error=",
                String.valueOf(authenticationErrorType.getCode()));
    }
}
