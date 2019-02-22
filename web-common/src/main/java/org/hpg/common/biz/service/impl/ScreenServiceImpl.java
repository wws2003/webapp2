/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hpg.common.biz.annotation.SecurityContextAware;
import org.hpg.common.biz.service.abstr.IFormValidator;
import org.hpg.common.biz.service.abstr.ILogger;
import org.hpg.common.biz.service.abstr.IScreenService;
import org.hpg.common.biz.service.abstr.ITaskExecutor;
import org.hpg.common.constant.MendelTransactionalLevel;
import org.hpg.common.framework.BaseFormProcessorForAjaxResult;
import org.hpg.common.framework.BaseFormProcessorForModelAndView;
import org.hpg.common.framework.transaction.ITransactionExecutor;
import org.hpg.common.framework.transaction.NoTransactionExecutorImpl;
import org.hpg.common.model.dto.web.AjaxResult;
import org.springframework.web.servlet.ModelAndView;

/**
 * Base class for normal controllers
 *
 * @author wws2003
 */
public class ScreenServiceImpl implements IScreenService {

    private final IFormValidator formValidator;

    private final ILogger logger;

    private final Map<Integer, ITransactionExecutor> transactionExecutorMap;

    // TODO Wire this
    @SecurityContextAware(true)
    private ITaskExecutor taskExecutor;

    /**
     * Constructor
     *
     * @param formValidator
     * @param logger
     * @param transactionExecutorMap
     */
    public ScreenServiceImpl(IFormValidator formValidator, ILogger logger, Map<Integer, ITransactionExecutor> transactionExecutorMap) {
        this.formValidator = formValidator;
        this.logger = logger;
        this.transactionExecutorMap = transactionExecutorMap;
    }

    @Override
    public <FormType> AjaxResult executeSyncForAjaxResult(FormType form,
            MendelTransactionalLevel transactionLevel,
            Function<FormType, AjaxResult> func,
            BiFunction<FormType, AjaxResult, String> infoMessageFunc) {

        // TODO Implement properly by specifying transaction executor properly
        return BaseFormProcessorForAjaxResult.<FormType>instanceForAjaxResult(form)
                .formValidator(formValidator)
                .logger(logger)
                .transactionExecutor(getTransactionExecutor(transactionLevel))
                .infoMessageFunc(infoMessageFunc)
                .formProcessor(func)
                .execute();
    }

    @Override
    public <FormType> ModelAndView executeSyncForModelAndView(FormType form,
            MendelTransactionalLevel transactionLevel,
            Function<FormType, ModelAndView> func,
            String errorPage,
            String fatalPage,
            BiFunction<FormType, ModelAndView, String> infoMessageFunc) {

        return BaseFormProcessorForModelAndView.instanceForModelAndView(form)
                .validationErrorPage(errorPage)
                .fatalPage(fatalPage)
                .formValidator(formValidator)
                .logger(logger)
                .transactionExecutor(getTransactionExecutor(transactionLevel))
                .formProcessor(func)
                .infoMessageFunc(infoMessageFunc)
                .execute();
    }

    public <FormType> AjaxResult executeAsyncForAjaxResult(
            HttpServletRequest request,
            HttpServletResponse response,
            FormType form,
            MendelTransactionalLevel transactionLevel,
            Function<FormType, AjaxResult> func,
            BiFunction<FormType, AjaxResult, String> infoMessageFunc) {

        // Create context for an async search
        AsyncContext asyncContext = request.startAsync(request, response);

        taskExecutor.submit(taskExecutor.getNewTaskKey(),
                () -> {
                    ServletResponse servResponse = asyncContext.getResponse();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    // Execute for result
                    AjaxResult ajaxResult = executeSyncForAjaxResult(form, transactionLevel, func, infoMessageFunc);
                    ObjectMapper objMapper = new ObjectMapper();
                    try (PrintWriter writer = servResponse.getWriter()) {
                        writer.write(objMapper.writeValueAsString(ajaxResult));
                        writer.flush();
                    } catch (Exception ex) {
                        logger.error(ex);
                    }
                });

        // TODO Return submitted result
        return null;
    }

    /**
     * Get the proper transaction executor based on level
     *
     * @param transactionalLevel
     * @return
     */
    private ITransactionExecutor getTransactionExecutor(MendelTransactionalLevel transactionalLevel) {
        return Optional.ofNullable(transactionExecutorMap.get(transactionalLevel.getCode()))
                .orElse(new NoTransactionExecutorImpl());
    }
}
