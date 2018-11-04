/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity for document reference record
 *
 * @author wws2003
 */
@Entity
@Table(name = "TBL_DOCUMENT_REFERENCE")
public class DocumentReferenceEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doc1_id")
    private Long documentId1;

    @Column(name = "doc2_id")
    private Long documentId2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocumentId1() {
        return documentId1;
    }

    public void setDocumentId1(Long documentId1) {
        this.documentId1 = documentId1;
    }

    public Long getDocumentId2() {
        return documentId2;
    }

    public void setDocumentId2(Long documentId2) {
        this.documentId2 = documentId2;
    }
}
