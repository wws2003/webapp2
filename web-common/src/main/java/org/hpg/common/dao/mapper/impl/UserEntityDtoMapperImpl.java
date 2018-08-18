/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.mapper.impl;

import org.hpg.common.constant.MendelRole;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.entity.RoleEntity;
import org.hpg.common.model.entity.UserEntity;
import org.hpg.common.model.mapping.EntityDtoMap;

/**
 * User entity-DTO mapper
 *
 * @author wws2003
 */
@EntityDtoMap(
        entityClass = UserEntity.class,
        dtoClass = MendelUser.class,
        entityToDtoMappings = {
            "id",
            "name",
            "encodedPassword",
            "encodedPassword,password",
            "displayedName,dispName"
        }
)
public class UserEntityDtoMapperImpl extends BaseEntityDtoMapper<UserEntity, MendelUser> {

    @Override
    protected void finalizeEntityFromDto(MendelUser dto, UserEntity entity) {
        // Role
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(dto.getRole().getCode());
        roleEntity.setName(dto.getRole().getName());
        entity.setRole(roleEntity);
    }

    @Override
    protected void finalizeDtoFromEntity(UserEntity entity, MendelUser dto) {
        // Role
        dto.setRole(MendelRole.getRoleFromCode(entity.getRole().getId()));
    }

}
