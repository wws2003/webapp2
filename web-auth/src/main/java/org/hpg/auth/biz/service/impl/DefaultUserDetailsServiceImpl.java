/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import java.util.Date;
import org.hpg.auth.model.MendelUserDetails;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.model.dto.principal.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Spring-sec provided UserDetailsService implementation class
 *
 * @author trungpt
 */
public class DefaultUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService mUserService;

    /**
     * Role
     */
    private final MendelRole mRole;

    public DefaultUserDetailsServiceImpl(MendelRole role) {
        this.mRole = role;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // TODO Implement properly
        // Test debug

        System.out.println("-----------------------Loading user for role " + userName + "------------------" + " for role " + mRole.getName());

        return mUserService.findUserByName(userName, this.mRole)
                .map(mendelUser -> {
                    return new MendelUserDetails(
                            LoginInfo.withUser(mendelUser)
                                    .loginAt(new Date())
                                    .build(),
                            mUserService.getUserGrantedPrivileges(mendelUser.getId())
                    );
                })
                .orElseThrow(() -> new UsernameNotFoundException(userName));
    }
}
