/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.biz.web.document;

import java.util.concurrent.Callable;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.project.constant.ProjectUrls;
import org.hpg.project.dao.repository.es.IEsDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * Sample controller for experimental purpose
 *
 * @author trungpt
 */
@Controller
@RequestMapping(ProjectUrls.PROJECT_ROOT_URL + ProjectUrls.PROJECT_DOCUMENT_ROOT_URL)
public class ExperimentDocController {

    @Autowired
    private IEsDocumentRepository documentRepository;

    @GetMapping("/testTest")
    @ResponseBody
    public String testTest() {
        return "1234";
    }

//    @Autowired
//    private ExecutorService executorService;
    @GetMapping("/testIndex")
    @ResponseBody
    public DeferredResult<String> testIndex() {
        DeferredResult<String> ret = new DeferredResult(100L);
        try {
            ret.setResult("1234");
        } catch (Exception e) {
            ret.setErrorResult(e);
        }
        return ret;
    }

    @GetMapping("/testCancelIndex")
    @ResponseBody
    public Callable<AjaxResult> testCancelIndex() {
        return null;
    }
}