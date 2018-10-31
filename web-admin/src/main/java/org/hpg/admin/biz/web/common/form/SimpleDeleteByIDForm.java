/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.common.form;

import java.io.Serializable;
import java.util.List;

/**
 * Simple form to delete elements by IDs
 *
 * @author trungpt
 */
public class SimpleDeleteByIDForm implements Serializable {

    /**
     * IDs
     */
    private List<Long> elementIds;

    public List<Long> getElementIds() {
        return elementIds;
    }

    public void setElementIds(List<Long> elementIds) {
        this.elementIds = elementIds;
    }

    @Override
    public String toString() {
        return "SimpleDeleteByIDForm{" + "elementIds=" + elementIds + '}';
    }
}
