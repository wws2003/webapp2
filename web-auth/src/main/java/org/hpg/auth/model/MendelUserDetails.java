/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import org.hpg.common.model.dto.principal.LoginInfo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Wrapper class for LoginInfo to implements Spring-sec depending UserDetails
 *
 * @author trungpt
 */
public class MendelUserDetails extends User implements Serializable {

    /**
     * Current login info
     */
    private final LoginInfo mLoginInfo;

    /**
     * Constructor
     *
     * @param loginInfo
     */
    public MendelUserDetails(LoginInfo loginInfo) {
        // TODO Implement properly to separate role and privileges
        super(loginInfo.getLoginUser().getName(),
                loginInfo.getLoginUser().getPassword(),
                new ArrayList(Arrays.asList(new SimpleGrantedAuthority("ROLE_" + loginInfo.getLoginUser().getRole().getName())))
        );
        this.mLoginInfo = loginInfo;
    }

    /**
     * Get current login info instance
     *
     * @return
     */
    public LoginInfo getLoginInfo() {
        return mLoginInfo;
    }
}
