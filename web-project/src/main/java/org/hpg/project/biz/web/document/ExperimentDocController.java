/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.biz.web.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.PrintWriter;
import java.util.concurrent.Callable;
import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hpg.common.biz.service.abstr.ILogger;
import org.hpg.common.biz.service.abstr.ITaskExecutor;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.util.AjaxResultBuilder;
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

    @Autowired
    private ITaskExecutor taskExecutor;

    @Autowired
    private ILogger logger;

    @GetMapping("/testTest")
    @ResponseBody
    public String testTest() {
        return "1234";
    }

    @GetMapping("/testIndex1")
    public void testIndexPureServlet(HttpServletRequest request, HttpServletResponse response) {
        // Create context for an async search
        AsyncContext asyncContext = request.startAsync(request, response);

        taskExecutor.submit("12345678", () -> {
            try {
                Thread.sleep(1200);
                ServletResponse servResponse = asyncContext.getResponse();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                AjaxResult ajaxResult = AjaxResultBuilder.successInstance()
                        .resultObject("1234")
                        .oneSuccessMessage("Index success")
                        .build();

                ObjectMapper objMapper = new ObjectMapper();

                try (PrintWriter writer = servResponse.getWriter()) {
                    writer.write(objMapper.writeValueAsString(ajaxResult));
                    writer.flush();
                } catch (Exception ex) {
                    logger.error(ex);
                }
            } catch (InterruptedException e) {
                logger.error(e);
            }
        });

        // Return immediately
    }

    @GetMapping("/testIndex")
    @ResponseBody
    public DeferredResult<String> testIndex(HttpServletRequest request, HttpServletResponse response) {
        // Two minute timeout for the request
        DeferredResult<String> ret = new DeferredResult(2 * 60 * 1000L);
        taskExecutor.submit("12345678", () -> {
            try {
                Thread.sleep(1200);
                ret.setResultHandler((Object result) -> {

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    AjaxResult ajaxResult = AjaxResultBuilder.successInstance()
                            .resultObject(result)
                            .oneSuccessMessage("Index success")
                            .build();

                    ObjectMapper objMapper = new ObjectMapper();

                    try (PrintWriter writer = response.getWriter()) {
                        writer.write(objMapper.writeValueAsString(ajaxResult));
                        writer.flush();
                    } catch (Exception ex) {
                        logger.error(ex);
                    }
                });
                ret.setResult("1234");
            } catch (InterruptedException e) {
                logger.error(e);
                ret.setErrorResult(e);
            }
        });
        return ret;
    }

    @GetMapping("/testCancelIndex")
    @ResponseBody
    public Callable<String> testCancelIndex() {
        return () -> "1234";
    }
}
