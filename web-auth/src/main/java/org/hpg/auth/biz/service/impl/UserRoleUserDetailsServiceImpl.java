/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.config.CommonQualifierConstant;
import org.hpg.common.constant.MendelRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring-provided UserDetailsService implementation class for normal user role
 *
 * @author trungpt
 */
public class UserRoleUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    @Qualifier(CommonQualifierConstant.USER_SERVICE_FOR_USER)
    private IUserService mUserService;

    @Autowired
    private PasswordEncoder mPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // TODO Implement properly
        // Test debug

        System.out.println("-----------------------Loading user for user role " + userName + "------------------");

        return mUserService.findUserByName(userName, MendelRole.USER)
                .map(mendelUser -> {
                    return User.withUsername(mendelUser.getName())
                            .password(mPasswordEncoder.encode(mendelUser.getPassword()))
                            .roles(MendelRole.USER.getName())
                            .build();
                })
                .orElse(null);
    }
}
