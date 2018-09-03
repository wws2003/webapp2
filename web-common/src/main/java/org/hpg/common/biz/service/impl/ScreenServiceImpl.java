/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.impl;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.hpg.common.biz.service.abstr.IFormValidator;
import org.hpg.common.biz.service.abstr.ILogger;
import org.hpg.common.biz.service.abstr.IScreenService;
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
