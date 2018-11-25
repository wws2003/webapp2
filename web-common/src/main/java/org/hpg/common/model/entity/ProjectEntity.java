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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

    @Column(name = "cdate", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date cDate;

    @Column(name = "mdate", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date mDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public short getReferScope() {
        return referScope;
    }

    public void setReferScope(short referScope) {
        this.referScope = referScope;
    }

    public Date getcDate() {
        return cDate;
    }

    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }
}
