/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.mapping;

import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.entity.UserEntity;

/**
 * Details of mapping between Entity and DTO classes
 *
 * @author wws2003
 */
public class EntityDtoMappings {

    @EntityDtoMap(
            entityClass = UserEntity.class,
            dtoClass = MendelUser.class,
            entityToDtoMappings = {
                "name",
                "encodedPassword",
                "displayedName,dispName"
            }
    )
    public static class User {
    }
}
