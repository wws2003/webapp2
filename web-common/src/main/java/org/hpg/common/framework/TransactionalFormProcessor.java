/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.framework;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.libcommon.CH;
import org.springframework.transaction.annotation.Transactional;

/**
 * Executor for transactional operations
 *
 * @author wws2003
 * @param <FormType>
 * @param <ResponseType>
 */
public class TransactionalFormProcessor<FormType, ResponseType> {

    /**
     * Flag to determine to execute in transaction
     */
    private boolean transactional = false;

    /**
     * Flag to determine a read only transaction
     */
    private boolean readOnlyTransaction = false;

    /**
     * User submission form (or any input)
     */
    private FormType form = null;

    /**
     * Form validate checker (check the form and return validation error
     * messages)
     */
    private Function<FormType, List<String>> formValidator = null;

    /**
     * Process validation error messages for final result
     */
    private Function<List<String>, ResponseType> validationErrorMessagesProcessor = null;

    /**
     * Processor for result
     */
    private Function<FormType, ResponseType> formProcessor = null;

    /**
     * Process for execution raised exception
     */
    private Function<Exception, ResponseType> executeExceptionProcessor = null;

    protected TransactionalFormProcessor() {
    }

    public static TransactionalFormProcessor instance() {
        return new TransactionalFormProcessor();
    }

    /**
     * Set to execute in transactional context
     *
     * @param readOnly TRUE: Read-only transaction, FALSE: Normal transaction
     * @return
     */
    public TransactionalFormProcessor transactional(boolean readOnly) {
        this.transactional = true;
        this.readOnlyTransaction = readOnly;
        return this;
    }

    /**
     * Set input (typically user submitted form)
     *
     * @param formType
     * @return
     */
    public TransactionalFormProcessor consume(FormType formType) {
        this.form = formType;
        return this;
    }

    /**
     * Set form validation checker
     *
     * @param validator
     * @return
     */
    public TransactionalFormProcessor formValidator(Function<FormType, List<String>> validator) {
        this.formValidator = validator;
        return this;
    }

    /**
     * Set validation error messages processor for final result
     *
     * @param validationErrorMessagesProcessor
     * @return
     */
    public TransactionalFormProcessor validationErrorMessagesProcessor(Function<List<String>, ResponseType> validationErrorMessagesProcessor) {
        this.validationErrorMessagesProcessor = validationErrorMessagesProcessor;
        return this;
    }

    /**
     * Set form checker
     *
     * @param processor
     * @return
     */
    public TransactionalFormProcessor formProcessor(Function<FormType, ResponseType> processor) {
        this.formProcessor = processor;
        return this;
    }

    /**
     * Execution exception process to return final result
     *
     * @param executeExceptionProcessor
     * @return
     */
    public TransactionalFormProcessor executeExceptionProcessor(Function<Exception, ResponseType> executeExceptionProcessor) {
        this.executeExceptionProcessor = executeExceptionProcessor;
        return this;
    }

    /**
     * Execute and return final result
     *
     * @return
     */
    public ResponseType execute() {
        BaseExecuteWrapper wrapper = transactional
                ? (readOnlyTransaction ? new ReadOnlyTransactionalExecuteWrapper() : new TransactionalExecuteWrapper())
                : new BaseExecuteWrapper();

        try {
            // Execute. Auto rollback and commit
            return wrapper.execute(this);
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
    private ResponseType internalExecute() {
        // TODO Implement
        // Validate
        List<String> validationErrorMessages = Optional.ofNullable(formValidator)
                .map(validator -> validator.apply(form))
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
        public ResponseType execute(TransactionalFormProcessor<FormType, ResponseType> processor) {
            return processor.internalExecute();
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
