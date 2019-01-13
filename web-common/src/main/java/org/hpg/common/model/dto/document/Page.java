/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.dto.document;

import java.io.Serializable;

/**
 * Model class for one single page
 *
 * @author trungpt
 */
public class Page implements Serializable {

    private long documentId;

    private long pageNo;

    private String content;

    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    public long getPageNo() {
        return pageNo;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Page{" + "documentId=" + documentId + ", pageNo=" + pageNo + ", content=" + content + '}';
    }
}
