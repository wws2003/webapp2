/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.biz.service.impl;

import java.util.List;
import org.hpg.common.model.dto.document.Document;
import org.hpg.common.model.dto.document.Page;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.project.biz.service.abstr.IDocumentSearchService;
import org.hpg.project.model.dto.DocumentSearchRequest;
import org.hpg.project.model.dto.DocumentSearchResult;

/**
 *
 * @author wws2003
 */
public class DocumentSearchServiceElasticSearchImpl implements IDocumentSearchService {

    @Override
    public void indexDocument(Document document, List<Page> pages) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DocumentSearchResult searchDocuments(DocumentSearchRequest request) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
