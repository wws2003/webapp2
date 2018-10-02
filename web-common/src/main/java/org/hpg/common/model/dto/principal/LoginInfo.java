/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.dto.principal;

import java.io.Serializable;
import java.util.Date;
import org.hpg.common.model.dto.user.MendelUser;

/**
 * Login info model (to act as principal)
 *
 * @author trungpt
 */
public class LoginInfo implements Serializable {

    /**
     * Login user
     */
    private final MendelUser mLoginUser;

    /**
     * Login timestamp. TODO Better data type
     */
    private final Date mLoginTimeStamp;

    /**
     * Flag to detect authenticated to detect if guest account (probably change
     * later by using class instead of primitive type)
     */
    private final boolean mIsAuthenticated;

    // TODO Think of moving to MendelUser class
    /**
     * Account non-expired flag
     */
    private final boolean mAccountNonExpired;

    /**
     * Account non-locked flag
     */
    private final boolean mAccountNonLocked;

    /**
     * Credentials non-expired flag
     */
    private final boolean mCredentialsNonExpired;

    /**
     * Enabled flag
     */
    private final boolean mEnabled;

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
    private LoginInfo(MendelUser loginUser, Date loginTimeStamp, boolean isAuthenticated, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        mLoginUser = loginUser;
        mLoginTimeStamp = loginTimeStamp;
        mIsAuthenticated = isAuthenticated;
        mAccountNonExpired = accountNonExpired;
        mAccountNonLocked = accountNonLocked;
        mCredentialsNonExpired = credentialsNonExpired;
        mEnabled = enabled;
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
     * Get login timestamp
     *
     * @return
     */
    public Date getLoginTimeStamp() {
        return mLoginTimeStamp;
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

    /**
     * @return the mAccountNonExpired
     */
    public boolean isAccountNonExpired() {
        return mAccountNonExpired;
    }

    /**
     * @return the mAccountNonLocked
     */
    public boolean isAccountNonLocked() {
        return mAccountNonLocked;
    }

    /**
     * @return the mCredentialsNonExpired
     */
    public boolean isCredentialsNonExpired() {
        return mCredentialsNonExpired;
    }

    /**
     * @return the mEnabled
     */
    public boolean isEnabled() {
        return mEnabled;
    }

    /**
     * Builder class
     */
    public static class Builder {

        // The instance to build
        private MendelUser mLoginUser;
        private Date mLoginTimeStamp = null;
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

        public Builder loginAt(Date loginTimeStamp) {
            mLoginTimeStamp = loginTimeStamp;
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
            return new LoginInfo(mLoginUser, mLoginTimeStamp, isAuthenticated, !accountExpired, !accountLocked, !credentialsExpired, !disabled);
        }
    }
}
