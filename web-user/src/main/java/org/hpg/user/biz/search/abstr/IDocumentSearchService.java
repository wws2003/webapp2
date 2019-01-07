/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.user.biz.search.abstr;

import org.hpg.common.model.dto.document.Document;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Search service for document (including indexing)
 *
 * @author trungpt
 */
public interface IDocumentSearchService {

    /**
     * Indexing document with Elastic search service
     *
     * @param document
     * @throws MendelRuntimeException
     */
    public void indexDocument(Document document) throws MendelRuntimeException;
}
