/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.model.entity.es;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 *
 * Model for document. Currently with minimum specification
 *
 * @author wws2003
 */
@Document(indexName = "mendel", createIndex = true, replicas = 1, shards = 1, type = "esdocument")
public class EsDocument {

    /**
     * ES-generated ID (string type). This would be better for performance ?
     * http://blog.mikemccandless.com/2014/05/choosing-fast-unique-identifier-uuid.html
     */
    @Id
    private String id;

    /**
     * PageID in the RDBMS
     */
    @Field(type = FieldType.Long)
    private long pageId;

    /**
     * Content (as in the RDBMS)
     */
    @Field(type = FieldType.Text)
    private String content;

    /**
     * DocumentID in the RDBMS
     */
    private long docId;

    /**
     * Project ID in the RDBMS. Used for searching
     */
    @Field(type = FieldType.Long)
    private long projectId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPageId() {
        return pageId;
    }

    public void setPageId(long pageId) {
        this.pageId = pageId;
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

    public long getDocId() {
        return docId;
    }

    public void setDocId(long docId) {
        this.docId = docId;
    }

    @Override
    public String toString() {
        return "EsDocument{" + "id=" + id + ", pageId=" + pageId + ", content=" + content + ", docId=" + docId + ", projectId=" + projectId + '}';
    }
}
