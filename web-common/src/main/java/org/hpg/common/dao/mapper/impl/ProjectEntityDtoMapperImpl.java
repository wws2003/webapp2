/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.mapper.impl;

import org.hpg.common.constant.MendelProjectStatus;
import org.hpg.common.constant.MendelReferScope;
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
            "displayedName",
            "description",
            "cDate",
            "mDate"
        },
        dtoToEntityMappings = {
            "id",
            "code",
            "displayedName",
            "description",
            "cDate",
            "mDate"
        }
)
public class ProjectEntityDtoMapperImpl extends BaseEntityDtoMapper<ProjectEntity, MendelProject> {

    @Override
    protected void finalizeEntityFromDto(MendelProject dto, ProjectEntity entity) {
        entity.setReferScope(dto.getReferScope().getCode());
        entity.setStatus(dto.getStatus().getCode());
    }

    @Override
    protected void finalizeDtoFromEntity(ProjectEntity entity, MendelProject dto) {
        dto.setReferScope(MendelReferScope.getProjectReferScopeByCode(entity.getReferScope()));
        dto.setStatus(MendelProjectStatus.getProjectStatusByCode(entity.getStatus()));
    }
}
