/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.search.biz.service;

import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.search.model.dto.DocumentSearchRequest;
import org.hpg.search.model.dto.DocumentSearchResult;

/**
 * Service for searching documents
 *
 * @author trungpt
 */
public interface IDocumentSearchService {

    /**
     * Search for documents
     *
     * @param request
     * @return
     * @throws MendelRuntimeException When searching failed
     */
    public DocumentSearchResult searchDocuments(DocumentSearchRequest request) throws MendelRuntimeException;
}
