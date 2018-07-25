/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.model;

import java.io.Serializable;
import java.util.List;
import org.hpg.auth.util.AuthUtil;
import org.hpg.common.constant.MendelPrivilege;
import org.hpg.common.model.dto.principal.LoginInfo;
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
     * @param privileges
     */
    public MendelUserDetails(LoginInfo loginInfo, List<MendelPrivilege> privileges) {
        super(loginInfo.getLoginUser().getName(),
                loginInfo.getLoginUser().getPassword(),
                AuthUtil.getGrantedAuthoritiesFromRoleAndPrivileges(loginInfo.getLoginUser().getRole(), privileges));

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
