/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.biz.web.document.form;

import java.io.Serializable;

/**
 * Content for one page
 *
 * @author wws2003
 */
public class DocumentPageContentForm implements Serializable {

    private long projectId;

    private long documentId;

    private long pageNo;

    private String content;

    private boolean toIndex;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

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

    public boolean isToIndex() {
        return toIndex;
    }

    public void setToIndex(boolean toIndex) {
        this.toIndex = toIndex;
    }

    @Override
    public String toString() {
        return "DocumentPageContentForm{" + "projectId=" + projectId + ", documentId=" + documentId + ", pageNo=" + pageNo + ", content=" + content + ", toIndex=" + toIndex + '}';
    }
}
