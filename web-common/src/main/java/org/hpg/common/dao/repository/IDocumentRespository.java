/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.repository;

import org.hpg.common.model.entity.DocumentEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for documents
 *
 * @author trungpt
 */
public interface IDocumentRespository extends IPagingAndSortingRepository<DocumentEntity, Long> {

    /**
     * Update mDate column for the record. This implementation actually just
     * delegate for the trigger to do the trick !?
     *
     * @param documentEntity
     * @return
     */
    @Modifying
    @Query("update DocumentEntity de set de.id = de.id")
    public DocumentEntity updateModifiedDate(DocumentEntity documentEntity);
}
