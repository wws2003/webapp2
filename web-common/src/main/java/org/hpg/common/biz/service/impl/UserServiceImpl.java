/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.constant.MendelPrivilege;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.dao.mapper.abstr.IEntityDtoMapper;
import org.hpg.common.dao.repository.IUserPrivRepository;
import org.hpg.common.dao.repository.IUserRepository;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.entity.UserEntity;
import org.hpg.common.model.entity.UserPrivEntity;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Actual implementation of user service
 *
 * @author trungpt
 */
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    private final IUserPrivRepository userPrivRepository;

    private final IEntityDtoMapper<UserEntity, MendelUser> entityDtoMapper;

    /**
     * Constructor
     *
     * @param userRepository
     * @param userPrivRepository
     * @param entityDtoMapper
     */
    public UserServiceImpl(IUserRepository userRepository, IUserPrivRepository userPrivRepository, IEntityDtoMapper<UserEntity, MendelUser> entityDtoMapper) {
        // Dependencies
        this.userRepository = userRepository;
        this.userPrivRepository = userPrivRepository;
        this.entityDtoMapper = entityDtoMapper;

    }

    @Override
    public Optional<MendelUser> findUserById(long userId) throws MendelRuntimeException {
        return userRepository.findById(userId)
                .map(entityDtoMapper::getDtoFromEntity);
    }

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
        // TODO Need detached the returned entity ?
        UserEntity savedUser = userRepository.save(entityDtoMapper.getEntityFromDto(user));
        return Optional.ofNullable(savedUser).map(entityDtoMapper::getDtoFromEntity).orElse(null);
    }

    @Override
    public void grantUserWithPrivileges(MendelUser user, List<MendelPrivilege> privileges) throws MendelRuntimeException {
        final long userId = user.getId();
        // Revoke
        userPrivRepository.deleteByUserId(userId);
        // Grant
        privileges.stream().forEach(priv -> {
            UserPrivEntity entity = new UserPrivEntity();
            entity.setPrivilegeId(priv.getId());
            entity.setUserId(userId);
            userPrivRepository.save(entity);
        });
    }

    @Override
    public List<MendelPrivilege> getUserGrantedPrivileges(long userId) throws MendelRuntimeException {
        return userPrivRepository.findByUserId(userId)
                .stream()
                .map(userPrivEnt -> userPrivEnt.getPrivilegeId())
                .map(MendelPrivilege::getPrivilegeFromId)
                .collect(Collectors.toList());
    }

    @Override
    public int deleteUsers(List<Long> userIds) throws MendelRuntimeException {
        List<UserEntity> deletedRecords = userRepository.deleteByIdIn(userIds);
        return deletedRecords.size();
    }

    @Override
    public List<MendelUser> findUsers(String text) throws MendelRuntimeException {
        // Only get users with normal role
        return userRepository.findByUserNameOrDisplayName(text, MendelRole.USER.getCode())
                .stream()
                .map(entityDtoMapper::getDtoFromEntity)
                .collect(Collectors.toList());
    }
}
