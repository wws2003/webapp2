/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.projectmgt.form;

import java.io.Serializable;
import java.util.List;

/**
 * Project add/update form
 *
 * @author wws2003
 */
public class ProjectAddUpdateForm implements Serializable {
    // TODO Add validation properly

    /**
     * Flag to determine if update/create
     */
    private boolean toCreateProject;

    /**
     * Project id. In the case of create, has the value -1
     */
    private long projectId;

    private String code;

    private String displayedName;

    private String description;

    private short referScopeCode;

    private short statusCode;

    /**
     * IDs of users to assign
     */
    private List<Long> userIds;

    public boolean isToCreateProject() {
        return toCreateProject;
    }

    public void setToCreateProject(boolean toCreateProject) {
        this.toCreateProject = toCreateProject;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
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

    public short getReferScopeCode() {
        return referScopeCode;
    }

    public void setReferScopeCode(short referScopeCode) {
        this.referScopeCode = referScopeCode;
    }

    public short getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(short statusCode) {
        this.statusCode = statusCode;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    @Override
    public String toString() {
        return "ProjectAddUpdateForm{" + "toCreateProject=" + toCreateProject + ", projectId=" + projectId + ", code=" + code + ", displayedName=" + displayedName + ", description=" + description + ", referScopeCode=" + referScopeCode + ", statusCode=" + statusCode + ", userIds=" + userIds + '}';
    }
}
