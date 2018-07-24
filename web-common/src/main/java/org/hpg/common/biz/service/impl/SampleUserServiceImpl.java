/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.constant.MendelPrivilege;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.libcommon.Tuple;
import org.hpg.libcommon.Tuple2;

/**
 * Implementation for user service
 *
 * @author trungpt
 */
public class SampleUserServiceImpl implements IUserService {

    // Tempory storage
    private final Map<String, Tuple2<String, MendelRole>> tmpUserNamePasswordMap = new HashMap();

    public SampleUserServiceImpl() {
        tmpUserNamePasswordMap.put("admin", Tuple.newTuple("password", MendelRole.ADMIN));
        tmpUserNamePasswordMap.put("user", Tuple.newTuple("password", MendelRole.USER));
        tmpUserNamePasswordMap.put("guest", Tuple.newTuple("password", MendelRole.GUEST));
        tmpUserNamePasswordMap.put("noone", Tuple.newTuple("password", MendelRole.INVALID));
    }

    @Override
    public Optional<MendelUser> findUserByName(String userName, MendelRole role) throws MendelRuntimeException {
        // TODO Implement properly
        if (!tmpUserNamePasswordMap.containsKey(userName)) {
            return Optional.empty();
        }
        Tuple2<String, MendelRole> userData = tmpUserNamePasswordMap.get(userName);
        if (userData.getItem2() != role) {
            return Optional.empty();
        }
        MendelUser user = new MendelUser();
        user.setName(userName);
        user.setRole(userData.getItem2());
        user.setPassword(userData.getItem1());

        // Test debug
        System.out.println("-----------------------Trying to find user with name " + userName + "------------------");

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
