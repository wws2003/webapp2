/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.mapper.impl;

import org.hpg.common.model.dto.document.MendelPage;
import org.hpg.common.model.entity.PageEntity;
import org.hpg.common.model.mapping.EntityDtoMap;

/**
 * Mapping Entity-DTO for page
 *
 * @author wws2003
 */
@EntityDtoMap(
        entityClass = PageEntity.class,
        dtoClass = MendelPage.class,
        entityToDtoMappings = {
            "id",
            "content",
            "pageNo",
            "docId,documentId"
        },
        dtoToEntityMappings = {
            "id",
            "content",
            "pageNo",
            "documentId,docId"
        }
)
public class PageEntityDtoMapperImpl extends BaseEntityDtoMapper<PageEntity, MendelPage> {

}
