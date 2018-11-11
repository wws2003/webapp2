/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.projectmgt.scrnmodel;

import java.io.Serializable;
import java.util.List;
import org.hpg.libcommon.Tuple2;

/**
 * Detail information of one project
 *
 * @author trungpt
 */
public class ScrnProjectDetail implements Serializable {

    /**
     * Project ID
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
    private short referScope;

    /**
     * Status
     */
    private short status;

    /**
     * Login timestamp in YYYY/MM/DD format
     */
    private String cDateTimeStamp;

    /**
     * Login timestamp in YYYY/MM/DD format
     */
    private String mDateTimeStamp;

    /**
     * IDs-displayed displayedName of users to be assigned to the project
     */
    private List<Tuple2<Long, String>> members;

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

    public List<Tuple2<Long, String>> getMembers() {
        return members;
    }

    public void setMembers(List<Tuple2<Long, String>> members) {
        this.members = members;
    }

    public short getReferScope() {
        return referScope;
    }

    public void setReferScope(short referScope) {
        this.referScope = referScope;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getcDateTimeStamp() {
        return cDateTimeStamp;
    }

    public void setcDateTimeStamp(String cDateTimeStamp) {
        this.cDateTimeStamp = cDateTimeStamp;
    }

    public String getmDateTimeStamp() {
        return mDateTimeStamp;
    }

    public void setmDateTimeStamp(String mDateTimeStamp) {
        this.mDateTimeStamp = mDateTimeStamp;
    }

    @Override
    public String toString() {
        return "ScrnProjectDetail{" + "id=" + id + ", code=" + code + ", displayedName=" + displayedName + ", description=" + description + ", referScope=" + referScope + ", status=" + status + ", cDateTimeStamp=" + cDateTimeStamp + ", mDateTimeStamp=" + mDateTimeStamp + ", members=" + members + '}';
    }
}
