/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.biz.service.impl;

import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.config.QualifierConstant;
import org.hpg.common.constant.MendelRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Spring-provided UserDetailsService implementation class for Admin role
 *
 * @author trungpt
 */
public class AdminRolelUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    @Qualifier(QualifierConstant.USER_SERVICE_FOR_ADMIN)
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Implement properly
        return userService.findUserByName(username, MendelRole.ADMIN)
                .map(mendelUser -> {
                    return User.withUsername(mendelUser.getName())
                            .password("password")
                            .build();
                })
                .orElse(null);
    }
}
