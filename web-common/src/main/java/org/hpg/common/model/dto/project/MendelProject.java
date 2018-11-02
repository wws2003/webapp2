/*
 * To change this license header, choose License Headers in MendelProject Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.dto.project;

import java.io.Serializable;
import java.util.Date;
import org.hpg.common.constant.MendelProjectStatus;
import org.hpg.common.constant.MendelReferScope;

/**
 * DTO class for project
 *
 * @author trungpt
 */
public class MendelProject implements Serializable {

    /**
     * Project unique ID
     */
    private long id;

    /**
     * Project code
     */
    private String code;

    /**
     * Project displayedName
     */
    private String displayedName;

    /**
     * Project description
     */
    private String description;

    /**
     * Scope
     */
    private MendelReferScope referScope;

    /**
     * Status
     */
    private MendelProjectStatus status;

    /**
     * Create date
     */
    private Date cDate;

    /**
     * Update date
     */
    private Date mDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public MendelReferScope getReferScope() {
        return referScope;
    }

    public void setReferScope(MendelReferScope referScope) {
        this.referScope = referScope;
    }

    public MendelProjectStatus getStatus() {
        return status;
    }

    public void setStatus(MendelProjectStatus status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "MendelProject{" + "id=" + id + ", code=" + code + ", displayedName=" + displayedName + ", description=" + description + ", referScope=" + referScope + ", status=" + status + ", cDate=" + cDate + ", mDate=" + mDate + '}';
    }
}
