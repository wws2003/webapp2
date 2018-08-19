/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.util;

import java.util.Arrays;
import java.util.List;
import org.hpg.common.model.dto.web.AjaxResult;

/**
 * Builder for AJAX result
 *
 * @author wws2003
 * @param <T>
 */
public class AjaxResultBuilder<T> {

    /**
     * Request is successful or not
     */
    private final boolean success;

    /**
     * Result object
     */
    private T resultObject = null;

    /**
     * Success messages
     */
    private List<String> successMessages = null;

    /**
     * Error messages
     */
    private List<String> errorMessages = null;

    /**
     * Private constructor
     *
     * @param success
     */
    private AjaxResultBuilder(boolean success) {
        this.success = success;
    }

    /**
     * Create new instance for success case
     *
     * @return
     */
    public static final AjaxResultBuilder successInstance() {
        return new AjaxResultBuilder(true);
    }

    /**
     * Create new instance for failed case
     *
     * @return
     */
    public static final AjaxResultBuilder failedInstance() {
        return new AjaxResultBuilder(false);
    }

    /**
     * Set result object
     *
     * @param obj
     * @return
     */
    public AjaxResultBuilder resultObject(T obj) {
        this.resultObject = obj;
        return this;
    }

    /**
     * Set success messages
     *
     * @param successMessages
     * @return
     */
    public AjaxResultBuilder successMessages(List<String> successMessages) {
        this.successMessages = successMessages;
        return this;
    }

    /**
     * Set success message (the only one)
     *
     * @param successMessage
     * @return
     */
    public AjaxResultBuilder oneSuccessMessage(String successMessage) {
        this.successMessages = Arrays.asList(successMessage);
        return this;
    }

    /**
     * Set error message (the only one)
     *
     * @param errorMessage
     * @return
     */
    public AjaxResultBuilder oneErrorMessage(String errorMessage) {
        this.errorMessages = Arrays.asList(errorMessage);
        return this;
    }

    /**
     * Set error messages
     *
     * @param errorMessages
     * @return
     */
    public AjaxResultBuilder errorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
        return this;
    }

    /**
     * Build final result
     *
     * @return
     */
    public AjaxResult<T> build() {
        AjaxResult<T> result = new AjaxResult();
        result.setErrorMessages(errorMessages);
        result.setResultObject(resultObject);
        result.setSuccess(success);
        result.setSuccessMessages(successMessages);
        return result;
    }
}
