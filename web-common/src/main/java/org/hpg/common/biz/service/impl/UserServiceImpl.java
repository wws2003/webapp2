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
import org.hpg.common.dao.mapper.abstr.IEntityDtoMapper;
import org.hpg.common.dao.repository.IUserRepository;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.entity.UserEntity;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Actual implementation of user service
 *
 * @author trungpt
 */
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IEntityDtoMapper<UserEntity, MendelUser> entityDtoMapper;

    @Override
    public Optional<MendelUser> findUserByName(String userName, MendelRole role) throws MendelRuntimeException {
        return userRepository.findByUserNameAndRoleId(userName, role.getCode())
                .map(entityDtoMapper::getDtoFromEntity);
    }

    @Override
    public MendelUser createUser(MendelUser user) throws MendelRuntimeException {
        // TODO Need detached the entity ?
        UserEntity savedUser = userRepository.save(entityDtoMapper.getEntityFromDto(user));
        return Optional.ofNullable(savedUser).map(entityDtoMapper::getDtoFromEntity).orElse(null);
    }

    @Override
    public MendelUser updateUser(MendelUser user) throws MendelRuntimeException {
        // TODO Need detached the entity ?
        UserEntity savedUser = userRepository.save(entityDtoMapper.getEntityFromDto(user));
        return Optional.ofNullable(savedUser).map(entityDtoMapper::getDtoFromEntity).orElse(null);
    }

    @Override
    public void grantUserWithPrivileges(MendelUser user, List<MendelPrivilege> privileges) throws MendelRuntimeException {
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