/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.dto.principal;

import java.util.Collection;
import java.util.function.Function;
import org.hpg.common.model.dto.user.MendelUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Login info model (to act as principal)
 *
 * @author trungpt
 */
public class LoginInfo extends User {

    /**
     * Login user
     */
    private final MendelUser mLoginUser;

    /**
     * Constructor
     *
     * @param loginUser
     * @param authorities
     */
    public LoginInfo(MendelUser loginUser, Collection<? extends GrantedAuthority> authorities) {
        super(loginUser.getName(), loginUser.getPassword(), authorities);
        mLoginUser = loginUser;
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
    public LoginInfo(MendelUser loginUser, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(loginUser.getName(), loginUser.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        mLoginUser = loginUser;
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
     * Instantiate builder
     *
     * @param loginUser
     * @return
     */
    public static Builder withUser(MendelUser loginUser) {
        return new Builder().withUser(loginUser);
    }

    public static class Builder {

        private LoginInfo mLoginInfo;

        public Builder withUser(MendelUser loginUser) {
            mLoginInfo = new LoginInfo(loginUser, null);
            return this;
        }

        public Builder passwordEncoder(Function<String, String> passwordEncoder) {
            // TODO Implement
            return this;
        }

        // TODO Add methods
        public LoginInfo build() {
            return mLoginInfo;
        }
    }
}
