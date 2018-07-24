/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.dto.principal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hpg.common.model.dto.user.MendelUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Login info model (to act as principal)
 *
 * @author trungpt
 */
public class LoginInfo extends User implements Serializable {

    /**
     * Login user
     */
    private final MendelUser mLoginUser;

    /**
     * Flag to detect authenticated to detect if guest account (probably change
     * later by using class instead of primitive type)
     */
    private final boolean mIsAuthenticated;

    /**
     * Constructor for non-authenticated login
     *
     * @param loginUser
     */
    private LoginInfo(MendelUser loginUser, boolean isAuthenticated) {
        super(loginUser.getName(), loginUser.getPassword(), new ArrayList());
        mLoginUser = loginUser;
        mIsAuthenticated = isAuthenticated;
    }

    /**
     * Constructor
     *
     * @param loginUser
     * @param authorities
     */
    private LoginInfo(MendelUser loginUser, boolean isAuthenticated, Collection<? extends GrantedAuthority> authorities) {
        super(loginUser.getName(), loginUser.getPassword(), authorities);
        mLoginUser = loginUser;
        mIsAuthenticated = isAuthenticated;
    }

    /**
     * Constructor
     *
     * @param loginUser
     * @param enabled
     * @param accountNonExpired
     * @param credentialsNonExpired
     * @param accountNonLocked
     * @param authorities
     */
    private LoginInfo(MendelUser loginUser, boolean isAuthenticated, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(loginUser.getName(), loginUser.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        mLoginUser = loginUser;
        mIsAuthenticated = isAuthenticated;
    }

    /**
     * Get login user
     *
     * @return the mLoginUser
     */
    public MendelUser getLoginUser() {
        return mLoginUser;
    }

    /**
     * Get authenticated flag
     *
     * @return
     */
    public boolean isAuthenticated() {
        return mIsAuthenticated;
    }

    /**
     * Instantiate builder
     *
     * @param loginUser
     * @return
     */
    public static Builder withUser(MendelUser loginUser) {
        return new Builder().withUser(loginUser);
    }

    public static class Builder {

        // The instance to build
        private MendelUser mLoginUser;
        private boolean isAuthenticated = true;
        private boolean accountExpired = false;
        private boolean accountLocked = false;
        private boolean credentialsExpired = false;
        private boolean disabled = false;

        private Builder() {
            // Still callable from outer class
        }

        public Builder withUser(MendelUser loginUser) {
            mLoginUser = loginUser;
            return this;
        }

        public Builder accountExpired(boolean accountExpired) {
            this.accountExpired = accountExpired;
            return this;
        }

        public Builder accountLocked(boolean accountLocked) {
            this.accountLocked = accountLocked;
            return this;
        }

        public Builder credentialsExpired(boolean credentialsExpired) {
            this.credentialsExpired = credentialsExpired;
            return this;
        }

        public Builder disabled(boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        public Builder authenticated(boolean isAuthenticated) {
            this.isAuthenticated = isAuthenticated;
            return this;
        }

        /**
         * Create login info instance
         *
         * @return
         */
        public LoginInfo build() {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + mLoginUser.getRole().getName()));
            return new LoginInfo(mLoginUser, isAuthenticated, !disabled, !accountExpired, !credentialsExpired, !accountLocked, authorities);
        }
    }
}
