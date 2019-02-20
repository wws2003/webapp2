/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.abstr;

import java.util.List;
import java.util.Optional;
import org.hpg.common.model.dto.document.Document;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.libcommon.Tuple2;

/**
 * Service for documents CRUD operations
 *
 * @author trungpt
 */
public interface IDocumentService {

    //TODO Declare proper methods
    /**
     * Count documents by author Id
     *
     * @param authorId
     * @return
     * @throws MendelRuntimeException When counting failed
     */
    public long countByAuthorId(long authorId) throws MendelRuntimeException;

    /**
     * Find document by ID
     *
     * @param documentId
     * @return
     * @throws MendelRuntimeException When finding failed
     */
    Optional<Document> findDocumentById(long documentId) throws MendelRuntimeException;

    /**
     * Find documents by author id. TODO Thinking of paging
     *
     * @param authorId
     * @return
     * @throws MendelRuntimeException When finding failed
     */
    public List<Document> findDocumentsByAuthorId(long authorId) throws MendelRuntimeException;

    /**
     * Save new document
     *
     * @param document
     * @return The saved document
     * @throws MendelRuntimeException When creating failed
     */
    public Document createDocument(Document document) throws MendelRuntimeException;

    /**
     * Save modified document
     *
     * @param document
     * @return The saved document
     * @throws MendelRuntimeException When updating failed
     */
    public Document updateDocument(Document document) throws MendelRuntimeException;

    /**
     * Update document to reflect changes in page (effectively just update the
     * mDate value to current timestamp)
     *
     * @param documentId
     * @throws MendelRuntimeException When updating failed
     */
    public void updateDocumentForPageChange(long documentId) throws MendelRuntimeException;

    /**
     * Delete documents
     *
     * @param documentIds
     * @return Number of document deleted
     * @throws MendelRuntimeException When delete failed
     */
    public int deleteDocuments(List<Long> documentIds) throws MendelRuntimeException;

    /**
     * Find documents to those the given document referring
     *
     * @param documentId
     * @return
     * @throws MendelRuntimeException
     */
    public List<Document> findReferringDocuments(long documentId) throws MendelRuntimeException;

    /**
     * Find documents from those the given document referred
     *
     * @param documentId
     * @return
     * @throws MendelRuntimeException When delete failed
     */
    public List<Document> findReferredDocuments(long documentId) throws MendelRuntimeException;

    /**
     * Create document references
     *
     * @param documentsReferences
     * @return
     * @throws MendelRuntimeException When creating failed
     */
    public int createReferences(List<Tuple2<Long, Long>> documentsReferences) throws MendelRuntimeException;

    /**
     * Delete document references
     *
     * @param documentsReferences
     * @return
     * @throws MendelRuntimeException When deleting failed
     */
    public int deleteReferences(List<Tuple2<Long, Long>> documentsReferences) throws MendelRuntimeException;
}
