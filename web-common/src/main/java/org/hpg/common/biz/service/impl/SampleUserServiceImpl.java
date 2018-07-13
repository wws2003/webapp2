/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.impl;

import java.util.List;
import java.util.Optional;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.constant.MendelPrivilege;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Implementation for user service
 *
 * @author trungpt
 */
public class SampleUserServiceImpl implements IUserService {

    @Override
    public Optional<MendelUser> findUserByName(String userName, MendelRole role) throws MendelRuntimeException {
        // TODO Implement properly
        MendelUser user = new MendelUser();
        user.setName(role == MendelRole.ADMIN ? "admin" : "user");
        user.setEncodedPassword("password");
        return Optional.of(user);
    }

    @Override
    public int createUser(MendelUser user) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteUsers(List<Long> userIds) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MendelPrivilege> findPrivilegesForRole(MendelRole role) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
