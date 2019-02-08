/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.biz.service.abstr;

import java.util.List;
import org.hpg.common.model.dto.document.Document;
import org.hpg.common.model.dto.document.MendelPage;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.project.model.dto.DocumentSearchRequest;
import org.hpg.project.model.dto.DocumentSearchResult;

/**
 * Service for searching documents
 *
 * @author trungpt
 */
public interface IDocumentSearchService {

    /**
     * Index a single page (attached to a single document)
     *
     * @param page
     * @throws MendelRuntimeException
     */
    public void indexPage(MendelPage page) throws MendelRuntimeException;

    /**
     * Indexing document for full-text search
     *
     * @param document
     * @param pages
     * @throws MendelRuntimeException When indexing failed
     */
    public void indexDocument(Document document, List<MendelPage> pages) throws MendelRuntimeException;

    /**
     * Search for documents
     *
     * @param request
     * @return
     * @throws MendelRuntimeException When searching failed
     */
    public DocumentSearchResult searchDocuments(DocumentSearchRequest request) throws MendelRuntimeException;
}
