/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.abstr;

import java.util.List;
import org.hpg.common.model.dto.document.MendelPage;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Service for page operations
 *
 * @author wws2003
 */
public interface IPageService {

    /**
     * Create a new page
     *
     * @param page
     * @throws MendelRuntimeException When page creation failed
     */
    public void createPage(MendelPage page) throws MendelRuntimeException;

    /**
     * Update page content
     *
     * @param page
     * @throws MendelRuntimeException When update failed
     */
    public void updatePage(MendelPage page) throws MendelRuntimeException;

    /**
     * Delete page
     *
     * @param page
     * @throws MendelRuntimeException When delete failed
     */
    public void deletePage(MendelPage page) throws MendelRuntimeException;

    /**
     * Delete all pages of the specified document
     *
     * @param documentId
     * @throws MendelRuntimeException When delete failed
     */
    public void deletePages(long documentId) throws MendelRuntimeException;

    /**
     * Create pages of one document
     *
     * @param documentId
     * @return
     * @throws MendelRuntimeException When retrieval failed
     */
    public List<MendelPage> getDocumentPages(long documentId) throws MendelRuntimeException;

    /**
     * Get pages of one documents with page numbers specified
     *
     * @param documentId
     * @param pageNos
     * @return
     * @throws MendelRuntimeException When retrieval failed
     */
    public List<MendelPage> getDocumentPages(long documentId, List<Long> pageNos) throws MendelRuntimeException;
}
