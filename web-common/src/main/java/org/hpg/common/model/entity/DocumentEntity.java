/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity for document record
 *
 * @author trungpt
 */
@Entity
@Table(name = "TBL_DOCUMENT")
public class DocumentEntity implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private Integer type;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private UserEntity role;

    @Column(name = "content")
    private String content;

    @Column(name = "public")
    private Boolean publishedDoc;

    // TODO Add proper annotations
    private List<DocumentEntity> referringDocuments;

    // TODO Add proper annotations
    private List<DocumentEntity> referredDocuments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public UserEntity getRole() {
        return role;
    }

    public void setRole(UserEntity role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getPublishedDoc() {
        return publishedDoc;
    }

    public void setPublishedDoc(Boolean publishedDoc) {
        this.publishedDoc = publishedDoc;
    }

    public List<DocumentEntity> getReferringDocuments() {
        return referringDocuments;
    }

    public void setReferringDocuments(List<DocumentEntity> referringDocuments) {
        this.referringDocuments = referringDocuments;
    }

    public List<DocumentEntity> getReferredDocuments() {
        return referredDocuments;
    }

    public void setReferredDocuments(List<DocumentEntity> referredDocuments) {
        this.referredDocuments = referredDocuments;
    }

}
