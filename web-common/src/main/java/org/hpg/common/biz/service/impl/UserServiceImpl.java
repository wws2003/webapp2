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
import org.hpg.common.dao.repository.IUserRepository;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.entity.RoleEntity;
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

    @Override
    public Optional<MendelUser> findUserByName(String userName, MendelRole role) throws MendelRuntimeException {
        return userRepository.findByUserNameAndRoleId(userName, role.getCode())
                .map(this::getDtoFromEntity);
    }

    @Override
    public int createUser(MendelUser user) throws MendelRuntimeException {
        // TODO Implement properly
        UserEntity savedUser = userRepository.save(getEntityFromDto(user));
        return (savedUser != null) ? 1 : 0;
    }

    @Override
    public int deleteUsers(List<Long> userIds) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MendelPrivilege> findPrivilegesForRole(MendelRole role) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Convert entity to DTO (for business logic)
     *
     * @param entity
     * @return
     */
    private MendelUser getDtoFromEntity(UserEntity entity) {
        MendelUser dto = new MendelUser();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        // Currently set both passwords to be the same
        dto.setPassword(entity.getEncryptedPassword());
        dto.setEncodedPassword(entity.getEncryptedPassword());
        dto.setDispName(entity.getDisplayedName());
        dto.setRole(MendelRole.getRoleFromCode((entity.getRole().getId())));
        return dto;
    }

    /**
     * Get entity from DTO (for DAO)
     *
     * @param user
     * @return
     */
    private UserEntity getEntityFromDto(MendelUser user) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(user.getRole().getCode());
        roleEntity.setName(user.getRole().getName());

        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setRole(roleEntity);
        entity.setEncryptedPassword(user.getEncodedPassword());
        entity.setDisplayedName(user.getDispName());
        return entity;
    }
}
