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
public class MendelPage implements Serializable {

    private long id;

    private long pageNo;

    private long documentId;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MendelPage{" + "id=" + id + ", pageNo=" + pageNo + ", documentId=" + documentId + ", content=" + content + '}';
    }
}
