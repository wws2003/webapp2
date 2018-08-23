/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.dto.web;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Model for non-AJAX web page
 *
 * @author wws2003
 */
public class WebPageModel implements Serializable {

    /**
     * View name, e.g. user/create
     */
    private String viewName;

    /**
     * Models maps
     */
    private Map<String, ? extends Serializable> modelMap;

    /**
     * Flag to determine if page is for error rendering
     */
    private boolean errorPage;

    /**
     * Messages on screen
     */
    private List<String> messages;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, ? extends Serializable> getModelMap() {
        return modelMap;
    }

    public void setModelMap(Map<String, ? extends Serializable> modelMap) {
        this.modelMap = modelMap;
    }

    public boolean getErrorPage() {
        return errorPage;
    }

    public void setErrorPage(boolean errorPage) {
        this.errorPage = errorPage;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "WebPageModel{" + "viewName=" + viewName + ", modelMap=" + modelMap + ", errorPage=" + errorPage + ", messages=" + messages + '}';
    }
}
