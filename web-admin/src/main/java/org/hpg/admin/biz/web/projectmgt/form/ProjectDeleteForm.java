/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package org.hpg.admin.biz.web.projectmgt.form;

import java.io.Serializable;
import java.util.List;

/**
 * Form to delete project(s)
 *
 * @author trungpt
 */
public class ProjectDeleteForm implements Serializable {

    /**
     * IDs of users to delete
     */
    private List<Long> projectIdsToDelete;

    public List<Long> getProjectIdsToDelete() {
        return projectIdsToDelete;
    }

    public void setProjectIdsToDelete(List<Long> projectIdsToDelete) {
        this.projectIdsToDelete = projectIdsToDelete;
    }

    @Override
    public String toString() {
        return "ProjectDeleteForm{" + "projectIdsToDelete=" + projectIdsToDelete + '}';
    }
}
