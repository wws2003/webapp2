/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    private final Map<Integer, List<MendelPrivilege>> rolePrivilegesMap = new HashMap();

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

        // Usable privileges for each role
        rolePrivilegesMap.put(MendelRole.ADMIN.getCode(), Arrays.asList(MendelPrivilege.PRIV_MANAGE_USER, MendelPrivilege.PRIV_MANAGE_SYSTEM));
        rolePrivilegesMap.put(MendelRole.USER.getCode(), Arrays.asList(MendelPrivilege.PRIV_CREATE_DOCUMENT,
                MendelPrivilege.PRIV_SHARE_DOCUMENT,
                MendelPrivilege.PRIV_UPDATE_DOCUMENT,
                MendelPrivilege.PRIV_LIST_DOCUMENT,
                MendelPrivilege.PRIV_SEARCH_DOCUMENT));
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
        privileges.stream().forEach(priv -> {
            UserPrivEntity entity = new UserPrivEntity();
            entity.setPrivilegeId(priv.getId());
            entity.setUserId(userId);
            userPrivRepository.save(entity);
        });
    }

    @Override
    public int deleteUsers(List<Long> userIds) throws MendelRuntimeException {
        List<UserEntity> deletedRecords = userRepository.deleteByIdIn(userIds);
        return deletedRecords.size();
    }

    @Override
    public List<MendelPrivilege> findPrivilegesForRole(MendelRole role) throws MendelRuntimeException {
        return Optional.ofNullable(rolePrivilegesMap.get(role.getCode())).orElse(new ArrayList());
    }
}
