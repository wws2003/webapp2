/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mapper class for user entity and DTO
 *
 * @author wws2003
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.TYPE})
public @interface EntityDtoMap {

    /**
     * Entity class
     *
     * @return the entity class
     */
    Class<?> entityClass();

    /**
     * DTO class
     *
     * @return the DTO class
     */
    Class<?> dtoClass();

    /**
     * Mappings from entity getters to DTO setter.<BR>
     * Format {["entityGetterName,dtoSetterName"]}
     *
     * @return
     */
    String[] entityToDtoMappings() default {};

    /**
     * Mappings from DTO getters to entity setter.<BR>
     * If empty, should be considered as reverse of entityToDtoMappings
     *
     * @return
     */
    String[] dtoToEntityMappings() default {};
}
