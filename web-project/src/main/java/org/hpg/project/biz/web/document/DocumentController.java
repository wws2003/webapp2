/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.biz.web.document;

import org.hpg.common.biz.service.abstr.IScreenService;
import org.hpg.common.constant.MendelTransactionalLevel;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.project.biz.web.document.form.DocumentCreateForm;
import org.hpg.project.biz.web.document.form.DocumentPageContentForm;
import org.hpg.project.biz.web.document.scrnservice.IDocumentSrcnService;
import org.hpg.project.constant.ProjectUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for document pages
 *
 * @author wws2003
 */
@Controller
@RequestMapping(ProjectUrls.PROJECT_ROOT_URL + ProjectUrls.PROJECT_DOCUMENT_ROOT_URL)
public class DocumentController {

    @Autowired
    private IScreenService actionFlowService;

    @Autowired
    private IDocumentSrcnService documentSrcnService;

    @GetMapping(ProjectUrls.PROJECT_CREATE_DOCUMENT)
    public String index() {
        return "project/document/createDocument";
    }

    /**
     * Create a document with basic info (i.e. without pages data)
     *
     * @param form
     * @return
     */
    @PostMapping(ProjectUrls.PROJECT_CREATE_DOCUMENT)
    @ResponseBody
    public AjaxResult createDocument(@RequestBody DocumentCreateForm form) {
        return actionFlowService.executeSyncForAjaxResult(form,
                MendelTransactionalLevel.DEFAULT,
                documentSrcnService::createDocument,
                (fm, ret) -> String.format("Document has been successfully saved {%s} for {%s}", fm, ret));
    }

    @PostMapping(ProjectUrls.PROJECT_ADD_PAGE)
    @ResponseBody
    public AjaxResult addPage(@RequestBody DocumentPageContentForm form) {
        return actionFlowService.executeSyncForAjaxResult(form,
                MendelTransactionalLevel.DEFAULT,
                documentSrcnService::addPage,
                (fm, ret) -> String.format("Page has been successfully added {%s} for {%s}", fm, ret));
    }

    public AjaxResult updatePageContent(DocumentPageContentForm form) {
        return null;
    }

    public AjaxResult uploadPage() {
        return null;
    }

    public AjaxResult cancelAddingPage() {
        return null;
    }
}
