/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.impl;

import java.util.List;
import java.util.Optional;
import org.hpg.common.biz.service.abstr.IDocumentService;
import org.hpg.common.dao.mapper.abstr.IEntityDtoMapper;
import org.hpg.common.dao.repository.IDocumentRespository;
import org.hpg.common.model.dto.document.Document;
import org.hpg.common.model.entity.DocumentEntity;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.libcommon.Tuple2;

/**
 * Created on : Feb 8, 2019, 10:24:06 PM
 *
 * @author wws2003
 */
public class DocumentServiceImpl implements IDocumentService {

    private final IDocumentRespository documentRespository;

    private final IEntityDtoMapper<DocumentEntity, Document> documentEntityDtoMapper;

    /**
     * Constructor
     *
     * @param documentRespository
     * @param documentEntityDtoMapper
     */
    public DocumentServiceImpl(IDocumentRespository documentRespository, IEntityDtoMapper<DocumentEntity, Document> documentEntityDtoMapper) {
        this.documentRespository = documentRespository;
        this.documentEntityDtoMapper = documentEntityDtoMapper;
    }

    @Override
    public long countByAuthorId(long authorId) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<Document> findDocumentById(long documentId) throws MendelRuntimeException {
        return documentRespository.findById(documentId).map(documentEntityDtoMapper::getDtoFromEntity);
    }

    @Override
    public List<Document> findDocumentsByAuthorId(long authorId) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Document createDocument(Document document) throws MendelRuntimeException {
        DocumentEntity savedEntity = documentRespository.save(documentEntityDtoMapper.getEntityFromDto(document));
        return Optional.ofNullable(savedEntity).map(documentEntityDtoMapper::getDtoFromEntity).orElse(null);
    }

    @Override
    public Document updateDocument(Document document) throws MendelRuntimeException {
        DocumentEntity savedEntity = documentRespository.save(documentEntityDtoMapper.getEntityFromDto(document));
        return Optional.ofNullable(savedEntity).map(documentEntityDtoMapper::getDtoFromEntity).orElse(null);
    }

    @Override
    public void updateDocumentForPageChange(long documentId) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteDocuments(List<Long> documentIds) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Document> findReferringDocuments(long documentId) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Document> findReferredDocuments(long documentId) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int createReferences(List<Tuple2<Long, Long>> documentsReferences) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteReferences(List<Tuple2<Long, Long>> documentsReferences) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
