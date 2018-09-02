/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.usermgt.form;

import java.io.Serializable;

/**
 * Form for indexing users
 *
 * @author trungpt
 */
public class UsersIndexForm implements Serializable {
    // TODO Add atttributes

    /**
     * Number of current page (one-based)
     */
    private int pageNumber;

    /**
     * Record count per page
     */
    private int recordCountPerPage;

    // TODO Add sort info
    //private int sortOrder;
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getRecordCountPerPage() {
        return recordCountPerPage;
    }

    public void setRecordCountPerPage(int recordCountPerPage) {
        this.recordCountPerPage = recordCountPerPage;
    }

    @Override
    public String toString() {
        return "UsersIndexForm{" + "pageNumber=" + pageNumber + ", recordCountPerPage=" + recordCountPerPage + '}';
    }
}
