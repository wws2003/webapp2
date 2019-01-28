/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.biz.web.document;

import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.project.biz.web.document.form.DocumentCreateForm;
import org.hpg.project.biz.web.document.form.DocumentPageContentForm;
import org.hpg.project.constant.ProjectUrls;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for document pages
 *
 * @author wws2003
 */
@Controller
@RequestMapping(ProjectUrls.PROJECT_ROOT_URL + ProjectUrls.PROJECT_DOCUMENT_ROOT_URL)
public class DocumentController {

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
    public AjaxResult createDocument(DocumentCreateForm form) {
        return null;
    }

    public AjaxResult addPage(DocumentPageContentForm form) {
        return null;
    }

    public AjaxResult uploadPage() {
        return null;
    }

    public AjaxResult cancelAddingPage() {
        return null;
    }
}
