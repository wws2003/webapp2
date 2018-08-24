/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.framework;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.hpg.common.biz.service.abstr.IFormValidator;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.libcommon.CH;
import org.springframework.transaction.annotation.Transactional;

/**
 * Executor for operations processing form for result (possibly transactional)
 *
 * @author wws2003
 * @param <FormType>
 * @param <ResponseType>
 */
public class BaseFormProcessor<FormType, ResponseType> {

    /**
     * Flag to determine to execute in transaction
     */
    private boolean transactional = false;

    /**
     * Flag to determine a read only transaction
     */
    private boolean readOnlyTransaction = false;

    /**
     * Form validate checker (check the form and return validation error
     * messages)
     */
    protected IFormValidator formValidator = null;

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

    protected BaseFormProcessor() {
    }

    public static <FT, RT> BaseFormProcessor<FT, RT> instance() {
        return new BaseFormProcessor();
    }

    /**
     * Set to execute in transactional context
     *
     * @param readOnly TRUE: Read-only transaction, FALSE: Normal transaction
     * @return
     */
    public BaseFormProcessor<FormType, ResponseType> transactional(boolean readOnly) {
        this.transactional = true;
        this.readOnlyTransaction = readOnly;
        return this;
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
     * @param form
     * @return
     */
    public ResponseType execute(FormType form) {
        BaseExecuteWrapper wrapper = transactional
                ? (readOnlyTransaction ? new ReadOnlyTransactionalExecuteWrapper() : new TransactionalExecuteWrapper())
                : new BaseExecuteWrapper();

        try {
            // Execute. Auto rollback and commit
            return wrapper.execute(this, form);
        } catch (Exception e) {
            // TODO Logging
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
    private ResponseType internalExecute(FormType form) {
        // TODO Implement
        // Validate
        List<String> validationErrorMessages = Optional.ofNullable(formValidator)
                .map(validator -> validator.validate(form))
                .orElse(null);

        if (!CH.isEmpty(validationErrorMessages) && validationErrorMessagesProcessor != null) {
            return validationErrorMessagesProcessor.apply(validationErrorMessages);
        }

        // Execute
        ResponseType res = Optional.ofNullable(formProcessor)
                .map(processor -> processor.apply(form))
                .orElse(null);

        return res;
    }

    /*---------------------------------Execution wrapper classes--------------------------------*/
    private class BaseExecuteWrapper {

        /**
         * Execute the processor in a non-transactional context
         *
         * @param processor
         * @return
         */
        public ResponseType execute(BaseFormProcessor<FormType, ResponseType> processor, FormType form) {
            return processor.internalExecute(form);
        }
    }

    /**
     * Class to execute the processor in transactional context
     */
    @Transactional(rollbackFor = Exception.class)
    private class TransactionalExecuteWrapper extends BaseExecuteWrapper {
    }

    /**
     * Class to execute the processor in readonly transactional context
     */
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    private class ReadOnlyTransactionalExecuteWrapper extends BaseExecuteWrapper {
    }
}
