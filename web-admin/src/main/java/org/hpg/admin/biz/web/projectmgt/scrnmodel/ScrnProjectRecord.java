/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.projectmgt.scrnmodel;

import java.io.Serializable;

/**
 * Record for one project in the project management screen
 *
 * @author trungpt
 */
public class ScrnProjectRecord implements Serializable {

    private long id;

    private String code;

    private String displayedName;

    private int userCount;

    private int documentCount;

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

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getDocumentCount() {
        return documentCount;
    }

    public void setDocumentCount(int documentCount) {
        this.documentCount = documentCount;
    }

    @Override
    public String toString() {
        return "ScrnProjectRecord{" + "id=" + id + ", code=" + code + ", displayedName=" + displayedName + ", userCount=" + userCount + ", documentCount=" + documentCount + '}';
    }
}
