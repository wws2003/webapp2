/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.hpg.common.biz.service.abstr.IFormValidator;
import org.hpg.common.biz.service.abstr.ILogger;
import org.hpg.common.constant.MendelTransactionalLevel;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.libcommon.CH;
import org.springframework.transaction.annotation.Propagation;
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
     * User submission form (or any input)
     */
    private FormType form = null;

    /**
     * Transactional level
     */
    private MendelTransactionalLevel transactionalLevel = MendelTransactionalLevel.NONE;

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

    /**
     * Map to detect executor
     */
    private final Map<Integer, BaseExecuteWrapper> executorMap = new HashMap();

    protected BaseFormProcessor(FormType form) {
        this.form = form;
        // Executors map based on transactional level
        executorMap.put(MendelTransactionalLevel.DEFAULT.getCode(), new DefaultTransactionalExecuteWrapper());
        executorMap.put(MendelTransactionalLevel.DEFAULT_READONLY.getCode(), new DefaultReadOnlyTransactionalExecuteWrapper());
        executorMap.put(MendelTransactionalLevel.CURRENT.getCode(), new CurrentTransactionalExecuteWrapper());
        executorMap.put(MendelTransactionalLevel.CURRENT_READONLY.getCode(), new CurrentReadOnlyTransactionalExecuteWrapper());
        executorMap.put(MendelTransactionalLevel.NEW.getCode(), new NewTransactionalExecuteWrapper());
        executorMap.put(MendelTransactionalLevel.NEW_READONLY.getCode(), new NewReadOnlyTransactionalExecuteWrapper());
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
     * Set to execute in transactional context
     *
     * @param transactionalLevel
     * @return
     */
    public BaseFormProcessor<FormType, ResponseType> transactional(MendelTransactionalLevel transactionalLevel) {
        this.transactionalLevel = transactionalLevel;
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
        BaseExecuteWrapper wrapper = Optional.ofNullable(executorMap.get(transactionalLevel.getCode()))
                .orElse(new BaseExecuteWrapper());

        try {
            // Execute. Auto rollback and commit
            ResponseType ret = wrapper.execute(this, form);
            // Logging
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
    private class DefaultTransactionalExecuteWrapper extends BaseExecuteWrapper {
    }

    /**
     * Class to execute the processor in readonly transactional context
     */
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    private class DefaultReadOnlyTransactionalExecuteWrapper extends BaseExecuteWrapper {
    }

    /**
     * Class to execute the processor in readonly transactional context
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    private class NewTransactionalExecuteWrapper extends BaseExecuteWrapper {
    }

    /**
     * Class to execute the processor in readonly transactional context
     */
    @Transactional(rollbackFor = Exception.class, readOnly = true, propagation = Propagation.REQUIRES_NEW)
    private class NewReadOnlyTransactionalExecuteWrapper extends BaseExecuteWrapper {
    }

    /**
     * Class to execute the processor in transactional context
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    private class CurrentTransactionalExecuteWrapper extends BaseExecuteWrapper {
    }

    /**
     * Class to execute the processor in transactional context
     */
    @Transactional(rollbackFor = Exception.class, readOnly = true, propagation = Propagation.MANDATORY)
    private class CurrentReadOnlyTransactionalExecuteWrapper extends BaseExecuteWrapper {
    }
}
