/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.biz.service.impl;

import java.util.List;
import org.hpg.common.biz.service.abstr.IDocumentService;
import org.hpg.common.model.dto.document.Document;
import org.hpg.common.model.dto.document.MendelPage;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.project.biz.service.abstr.IDocumentSearchService;
import org.hpg.project.dao.repository.es.IEsDocumentRepository;
import org.hpg.project.model.dto.DocumentSearchRequest;
import org.hpg.project.model.dto.DocumentSearchResult;
import org.hpg.project.model.entity.es.EsDocument;

/**
 *
 * @author wws2003
 */
public class DocumentSearchServiceElasticSearchImpl implements IDocumentSearchService {

    private final IEsDocumentRepository documentRepository;

    private final IDocumentService documentService;

    /**
     * Constructor
     *
     * @param documentRepository
     * @param documentService
     */
    public DocumentSearchServiceElasticSearchImpl(IEsDocumentRepository documentRepository, IDocumentService documentService) {
        this.documentRepository = documentRepository;
        this.documentService = documentService;
    }

    @Override
    public void indexPage(MendelPage page) throws MendelRuntimeException {
        documentRepository.save(createESDocumentFromPage(page));
    }

    @Override
    public void indexDocument(Document document, List<MendelPage> pages) throws MendelRuntimeException {
        pages.stream()
                .map(this::createESDocumentFromPage)
                .forEachOrdered(esDocument -> {
                    documentRepository.save(esDocument);
                });
    }

    @Override
    public DocumentSearchResult searchDocuments(DocumentSearchRequest request) throws MendelRuntimeException {
        // TODO Implement
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Create Elastic search document instance for each page
     *
     * @param page
     * @return
     */
    private EsDocument createESDocumentFromPage(MendelPage page) {
        EsDocument esDocument = new EsDocument();
        esDocument.setContent(page.getContent());
        esDocument.setDocId(page.getDocumentId());
        return esDocument;
    }
}
