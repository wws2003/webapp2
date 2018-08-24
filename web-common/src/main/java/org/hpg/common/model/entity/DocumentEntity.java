/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.entity;

import java.io.Serializable;
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
    private Boolean publicDoc;
}
