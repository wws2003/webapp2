/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.dto.paging;

import java.io.Serializable;
import java.util.List;

/**
 * Index for paging models
 *
 * @author trungpt
 * @param <T>
 */
public class PagingIndex<T extends Serializable> implements Serializable {

    /**
     * Current page number (starting from 1)
     */
    private int currentPage;

    /**
     * All page count
     */
    private int allPageCount;

    /**
     * Records per page
     */
    private int recordCountPerPage;

    /**
     * Models for records in the current page
     */
    private List<T> currentPageModels;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getAllPageCount() {
        return allPageCount;
    }

    public void setAllPageCount(int allPageCount) {
        this.allPageCount = allPageCount;
    }

    public int getRecordCountPerPage() {
        return recordCountPerPage;
    }

    public void setRecordCountPerPage(int recordCountPerPage) {
        this.recordCountPerPage = recordCountPerPage;
    }

    public List<T> getCurrentPageModels() {
        return currentPageModels;
    }

    public void setCurrentPageModels(List<T> currentPageModels) {
        this.currentPageModels = currentPageModels;
    }

    @Override
    public String toString() {
        return "PagingIndex{" + "currentPage=" + currentPage + ", allPageCount=" + allPageCount + ", recordCountPerPage=" + recordCountPerPage + ", currentPageModels=" + currentPageModels + '}';
    }
}
