/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.project.biz.web.document.form;

import java.io.Serializable;

/**
 * Form to create document (without pages)
 *
 * @author wws2003
 */
public class DocumentCreateForm implements Serializable {

    private long projectId;

    private long authorId;

    private String description;

    private int type;

    private String name;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DocumentCreateForm{" + "projectId=" + projectId + ", authorId=" + authorId + ", description=" + description + ", type=" + type + ", name=" + name + '}';
    }
}
