/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity for project record
 *
 * @author trungpt
 */
@Entity
@Table(name = "TBL_PROJECT")
public class ProjectEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "displayed_name")
    private String displayedName;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private short status;

    @Column(name = "refer_scope")
    private short referScope;

    @Column(name = "cdate")
    private Date cDate;

    @Column(name = "mdate")
    private String mDate;
}
