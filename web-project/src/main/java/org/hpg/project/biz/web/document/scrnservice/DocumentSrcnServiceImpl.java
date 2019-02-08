/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.biz.web.document.scrnservice;

import org.hpg.common.biz.service.abstr.IDocumentService;
import org.hpg.common.biz.service.abstr.IPageService;
import org.hpg.common.model.dto.document.Document;
import org.hpg.common.model.dto.document.MendelPage;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.common.util.AjaxResultBuilder;
import org.hpg.project.biz.service.abstr.IDocumentSearchService;
import org.hpg.project.biz.web.document.form.DocumentCreateForm;
import org.hpg.project.biz.web.document.form.DocumentPageContentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for document screen service
 *
 * @author wws2003
 */
@Service
public class DocumentSrcnServiceImpl implements IDocumentSrcnService {

    @Autowired
    private IDocumentService documentService;

    @Autowired
    private IPageService pageService;

    @Autowired
    private IDocumentSearchService documentSearchService;

    @Override
    public AjaxResult createDocument(DocumentCreateForm form) throws MendelRuntimeException {
        Document document = createDocumentFromForm(form);
        documentService.createDocument(document);
        // Return sucess result. TODO Set message properly
        return AjaxResultBuilder.successInstance()
                .oneSuccessMessage("Document has been successfully saved")
                .build();
    }

    @Override
    public AjaxResult addPage(DocumentPageContentForm form) throws MendelRuntimeException {
        // 1. Create page first
        MendelPage savedPage = pageService.createPage(createPageFromForm(form));
        // 2. Then index if required
        if (form.isToIndex() && savedPage != null && savedPage.getId() > 0) {
            documentSearchService.indexPage(savedPage);
        }
        // Return sucess result. TODO Set message properly
        return AjaxResultBuilder.successInstance()
                .oneSuccessMessage("Page has been successfully saved")
                .build();
    }

    /**
     * Create DTO from form
     *
     * @param form
     * @return
     */
    private Document createDocumentFromForm(DocumentCreateForm form) {
        // TODO Implement
        Document document = new Document();
        document.setAuthor(null);
        document.setName(form.getName());
        // document.setProject(form.getProjectId());
        document.setDescription(form.getDescription());
        return document;
    }

    /**
     * Create page DTO from form
     *
     * @param form
     * @return
     */
    private MendelPage createPageFromForm(DocumentPageContentForm form) {
        MendelPage page = new MendelPage();
        page.setContent(form.getContent());
        page.setDocumentId(form.getDocumentId());
        page.setPageNo(form.getPageNo());
        return page;
    }
}
