/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.projectmgt.form;

import java.io.Serializable;

/**
 * Form to retrieve detail information for one project
 *
 * @author trungpt
 */
public class ProjectDetailForm implements Serializable {

    /**
     * ID of the project to request
     */
    private long projectId;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "ProjectDetailForm{" + "projectId=" + projectId + '}';
    }
}
