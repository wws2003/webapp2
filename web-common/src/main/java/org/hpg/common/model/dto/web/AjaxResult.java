/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.dto.web;

import java.io.Serializable;
import java.util.List;

/**
 * Results to be returned as Ajax result
 *
 * @author wws2003
 * @param <T>
 */
public class AjaxResult<T> implements Serializable {

    /**
     * Request is successful or not
     */
    private boolean success;

    /**
     * Result object
     */
    private T resultObject;

    /**
     * Success messages
     */
    private List<String> successMessages;

    /**
     * Error messages
     */
    private List<String> errorMessages;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResultObject() {
        return resultObject;
    }

    public void setResultObject(T resultObject) {
        this.resultObject = resultObject;
    }

    public List<String> getSuccessMessages() {
        return successMessages;
    }

    public void setSuccessMessages(List<String> successMessages) {
        this.successMessages = successMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

}
