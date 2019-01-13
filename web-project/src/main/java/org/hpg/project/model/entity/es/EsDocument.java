/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.model.entity.es;

import javax.persistence.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 *
 * Model for document. Currently with minimum specification
 *
 * @author wws2003
 */
@Document(indexName = "mendel", createIndex = true, replicas = 1, shards = 1)
public class EsDocument {

    @Id
    private long id;

    /**
     * PageID in the RDBMS
     */
    private long pageId;

    private String content;

    /**
     * Document ID in the RDBMS. Used for searching
     */
    private long documentId;

    /**
     * Project ID in the RDBMS. Used for searching
     */
    private long projectId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPageId() {
        return pageId;
    }

    public void setPageId(long pageId) {
        this.pageId = pageId;
    }

    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "EsDocument{" + "id=" + id + ", pageId=" + pageId + ", documentId=" + documentId + ", projectId=" + projectId + ", content=" + content + '}';
    }
}
