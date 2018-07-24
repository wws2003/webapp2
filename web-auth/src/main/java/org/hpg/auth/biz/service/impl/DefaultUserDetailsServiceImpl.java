/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import org.hpg.auth.model.MendelUserDetails;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.model.dto.principal.LoginInfo;
import org.hpg.common.model.dto.user.MendelUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring-sec provided UserDetailsService implementation class
 *
 * @author trungpt
 */
public class DefaultUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService mUserService;

    @Autowired
    private PasswordEncoder mPasswordEncoder;

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
                .map(
                        // Sample: Manually set password
                        mendelUser -> {
                            MendelUser user = new MendelUser();
                            user.setId(mendelUser.getId());
                            user.setName(mendelUser.getName());
                            user.setEncodedPassword(mendelUser.getEncodedPassword());
                            user.setPassword(mPasswordEncoder.encode(mendelUser.getPassword()));
                            user.setRole(mendelUser.getRole());
                            return user;
                        }
                )
                .map(mendelUser -> {
                    return new MendelUserDetails(LoginInfo.withUser(mendelUser).build());
                })
                .orElseThrow(() -> new UsernameNotFoundException(userName));
    }
}
