/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.mapper.abstr;

/**
 * Entity-DTO mapper
 *
 * @author wws2003
 * @param <EntityClass>
 * @param <DtoClass>
 */
public interface IEntityDtoMapper<EntityClass, DtoClass> {

    /**
     * Get DTO from entity
     *
     * @param entity
     * @return
     */
    public DtoClass getDtoFromEntity(EntityClass entity);

    /**
     * Get entity from DTO
     *
     * @param dto
     * @return
     */
    public EntityClass getEntityFromDto(DtoClass dto);
}
