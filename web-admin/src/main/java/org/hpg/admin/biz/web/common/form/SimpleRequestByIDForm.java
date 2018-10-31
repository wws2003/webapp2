/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.common.form;

import java.io.Serializable;

/**
 * Form to request element by id
 *
 * @author trungpt
 */
public class SimpleRequestByIDForm implements Serializable {

    /**
     * ID
     */
    private Long elementId;

    public Long getElementId() {
        return elementId;
    }

    public void setElementId(Long elementId) {
        this.elementId = elementId;
    }

    @Override
    public String toString() {
        return "SimpleRequestByIDForm{" + "elementId=" + elementId + '}';
    }
}
