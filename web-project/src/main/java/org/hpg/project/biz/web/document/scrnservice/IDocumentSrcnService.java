/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.biz.web.document.scrnservice;

import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.project.biz.web.document.form.DocumentCreateForm;
import org.hpg.project.biz.web.document.form.DocumentPageContentForm;

/**
 * Interface for document screen service
 *
 * @author wws2003
 */
public interface IDocumentSrcnService {

    /**
     * Create new document
     *
     * @param form
     * @return
     * @throws MendelRuntimeException When creation failed
     */
    public AjaxResult createDocument(DocumentCreateForm form) throws MendelRuntimeException;

    /**
     * Add a new page to persisted document
     *
     * @param form
     * @return
     * @throws MendelRuntimeException When adding failed
     */
    public AjaxResult addPage(DocumentPageContentForm form) throws MendelRuntimeException;
}
