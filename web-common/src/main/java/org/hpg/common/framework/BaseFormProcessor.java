/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.hpg.common.biz.service.abstr.IFormValidator;
import org.hpg.common.biz.service.abstr.ILogger;
import org.hpg.common.framework.transaction.ITransactionExecutor;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.libcommon.CH;

/**
 * Executor for operations processing form for result (possibly transactional)
 *
 * @author wws2003
 * @param <FormType>
 * @param <ResponseType>
 */
public class BaseFormProcessor<FormType, ResponseType> {

    /**
     * User submission form (or any input)
     */
    private FormType form = null;

    /**
     * Transactional executor
     */
    private ITransactionExecutor transactionalExecutor = null;

    /**
     * Form validate checker (check the form and return validation error
     * messages)
     */
    protected IFormValidator formValidator = null;

    /**
     * Logger
     */
    protected ILogger logger = null;

    /**
     * Info message generating function
     */
    protected BiFunction<FormType, ResponseType, String> infoMessageFunc = null;

    /**
     * Process validation error messages for final result
     */
    protected Function<List<String>, ResponseType> validationErrorMessagesProcessor = null;

    /**
     * Processor for result
     */
    protected Function<FormType, ResponseType> formProcessor = null;

    /**
     * Process for execution raised exception
     */
    protected Function<Exception, ResponseType> executeExceptionProcessor = null;

    protected BaseFormProcessor(FormType form) {
        this.form = form;
    }

    public static <FT, RT> BaseFormProcessor<FT, RT> instance(FT form) {
        return new BaseFormProcessor(form);
    }

    /**
     * Set form validation checker
     *
     * @param validator
     * @return
     */
    public BaseFormProcessor<FormType, ResponseType> formValidator(IFormValidator validator) {
        this.formValidator = validator;
        return this;
    }

    /**
     * Set logger
     *
     * @param logger
     * @return
     */
    public BaseFormProcessor<FormType, ResponseType> logger(ILogger logger) {
        this.logger = logger;
        return this;
    }

    /**
     * Set transactional executor
     *
     * @param transactionalExecutor
     * @return
     */
    public BaseFormProcessor<FormType, ResponseType> transactionExecutor(ITransactionExecutor transactionalExecutor) {
        this.transactionalExecutor = transactionalExecutor;
        return this;
    }

    /**
     * Set info message generating function in the case of success
     *
     * @param infoMessageFunc
     * @return
     */
    public BaseFormProcessor<FormType, ResponseType> infoMessageFunc(BiFunction<FormType, ResponseType, String> infoMessageFunc) {
        this.infoMessageFunc = infoMessageFunc;
        return this;
    }

    /**
     * Set validation error messages processor for final result
     *
     * @param validationErrorMessagesProcessor
     * @return
     */
    public BaseFormProcessor<FormType, ResponseType> validationErrorMessagesProcessor(Function<List<String>, ResponseType> validationErrorMessagesProcessor) {
        this.validationErrorMessagesProcessor = validationErrorMessagesProcessor;
        return this;
    }

    /**
     * Set form checker
     *
     * @param processor
     * @return
     */
    public BaseFormProcessor<FormType, ResponseType> formProcessor(Function<FormType, ResponseType> processor) {
        this.formProcessor = processor;
        return this;
    }

    /**
     * Execution exception process to return final result
     *
     * @param executeExceptionProcessor
     * @return
     */
    public BaseFormProcessor<FormType, ResponseType> executeExceptionProcessor(Function<Exception, ResponseType> executeExceptionProcessor) {
        this.executeExceptionProcessor = executeExceptionProcessor;
        return this;
    }

    /**
     * Execute and return final result
     *
     * @return
     */
    public ResponseType execute() {
        try {
            // Execute (validation is also included in transaction if any). Auto rollback or commit
            ResponseType ret = transactionalExecutor.execute(this::internalExecute, form);
            // Logging (outside of transaction, if any)
            if (logger != null && infoMessageFunc != null) {
                logger.info(() -> infoMessageFunc.apply(form, ret));
            }
            return ret;
        } catch (Exception e) {
            // Logging
            Optional.ofNullable(logger)
                    .ifPresent(lg -> lg.error(e));
            // Return
            return Optional.ofNullable(executeExceptionProcessor)
                    .map(func -> func.apply(e))
                    .orElseThrow(() -> new MendelRuntimeException(e));
        }
    }

    /**
     * Execute following the standard flow
     *
     * @return
     */
    private ResponseType internalExecute(FormType frm) {
        // Validate
        List<String> validationErrorMessages = Optional.ofNullable(formValidator)
                .map(validator -> validator.validate(frm))
                .orElse(new ArrayList());

        if (!CH.isEmpty(validationErrorMessages) && validationErrorMessagesProcessor != null) {
            return validationErrorMessagesProcessor.apply(validationErrorMessages);
        }

        // Execute
        ResponseType res = Optional.ofNullable(formProcessor)
                .map(processor -> processor.apply(frm))
                .orElse(null);

        return res;
    }
}
