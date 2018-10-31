/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.mapper.impl;

import org.hpg.common.model.dto.project.MendelProject;
import org.hpg.common.model.entity.ProjectEntity;
import org.hpg.common.model.mapping.EntityDtoMap;

/**
 * Mapping Entity-DTO for project
 *
 * @author trungpt
 */
@EntityDtoMap(
        entityClass = ProjectEntity.class,
        dtoClass = MendelProject.class,
        entityToDtoMappings = {
            "id",
            "code",
            "displayedName"
        },
        dtoToEntityMappings = {
            "id",
            "code",
            "displayedName",
            "encodedPassword"
        }
)
public class ProjectEntityDtoMapperImpl extends BaseEntityDtoMapper<ProjectEntity, MendelProject> {

}
