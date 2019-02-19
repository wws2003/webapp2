/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.mapper.impl;

import org.hpg.common.constant.MendelDocumentType;
import org.hpg.common.dao.mapper.abstr.IEntityDtoMapper;
import org.hpg.common.model.dto.document.Document;
import org.hpg.common.model.dto.project.MendelProject;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.entity.DocumentEntity;
import org.hpg.common.model.entity.ProjectEntity;
import org.hpg.common.model.entity.UserEntity;
import org.hpg.common.model.mapping.EntityDtoMap;

/**
 * Mapping Entity-DTO for document
 *
 * @author wws2003
 */
@EntityDtoMap(
        entityClass = DocumentEntity.class,
        dtoClass = Document.class,
        entityToDtoMappings = {
            "id",
            "name",
            "description"
        },
        dtoToEntityMappings = {
            "id",
            "name",
            "description"
        }
)
public class DocumentEntityDtoMapperImpl extends BaseEntityDtoMapper<DocumentEntity, Document> {

    private final IEntityDtoMapper<UserEntity, MendelUser> userEntityDtoMapper;

    private final IEntityDtoMapper<ProjectEntity, MendelProject> projectEntityDtoMapper;

    /**
     * Constructor
     *
     * @param userEntityDtoMapper
     * @param projectEntityDtoMapper
     */
    public DocumentEntityDtoMapperImpl(IEntityDtoMapper<UserEntity, MendelUser> userEntityDtoMapper, IEntityDtoMapper<ProjectEntity, MendelProject> projectEntityDtoMapper) {
        this.userEntityDtoMapper = userEntityDtoMapper;
        this.projectEntityDtoMapper = projectEntityDtoMapper;
    }

    @Override
    protected void finalizeEntityFromDto(Document dto, DocumentEntity entity) {
        super.finalizeEntityFromDto(dto, entity);
        entity.setType(dto.getType().getCode());
        // cDate and mDate must be add manually since they do not start with upper case character !
        entity.setcDate(dto.getcDate());
        entity.setmDate(dto.getmDate());
    }

    @Override
    protected void finalizeDtoFromEntity(DocumentEntity entity, Document dto) {
        super.finalizeDtoFromEntity(entity, dto);
        dto.setType(MendelDocumentType.getDocumentTypeByCode((short) entity.getType()));
        // cDate and mDate must be add manually since they do not start with upper case character !
        dto.setcDate(entity.getcDate());
        dto.setmDate(entity.getmDate());
    }
}
