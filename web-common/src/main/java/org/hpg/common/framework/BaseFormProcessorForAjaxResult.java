/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.framework;

import java.util.List;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.util.AjaxResultBuilder;

/**
 * Form processor for AJAX result
 *
 * @author wws2003
 * @param <FormType>
 */
public class BaseFormProcessorForAjaxResult<FormType> extends BaseFormProcessor<FormType, AjaxResult> {

    private BaseFormProcessorForAjaxResult() {
        // Initialize
        super();
        // Validation error result creator
        validationErrorMessagesProcessor = this::createAjaxResultForValidationError;
        // Fatal error result creator
        executeExceptionProcessor = this::createAjaxResultForFatalError;
    }

    /**
     * Instance creator
     *
     * @param <FT>
     * @return
     */
    public static <FT> BaseFormProcessorForAjaxResult<FT> instanceForAjaxResult() {
        return new BaseFormProcessorForAjaxResult();
    }

    /**
     * Create AJAX result for validation errors
     *
     * @param errorMessages
     * @return
     */
    private AjaxResult createAjaxResultForValidationError(List<String> errorMessages) {
        return AjaxResultBuilder.failedInstance(false).errorMessages(errorMessages).build();
    }

    /**
     * Create AJAX result for fatal errors
     *
     * @param e
     * @return
     */
    private AjaxResult createAjaxResultForFatalError(Exception e) {
        // TODO Implement properly based on e
        return AjaxResultBuilder.failedInstance(true).oneErrorMessage(e.getLocalizedMessage()).build();
    }
}
