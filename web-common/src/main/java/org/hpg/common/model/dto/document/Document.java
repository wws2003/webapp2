/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.dto.document;

import java.io.Serializable;
import java.util.Date;
import org.hpg.common.constant.MendelDocumentType;
import org.hpg.common.model.dto.project.MendelProject;
import org.hpg.common.model.dto.user.MendelUser;

/**
 * Class for document model
 *
 * @author trungpt
 */
public class Document implements Serializable {

    /**
     * Document ID
     */
    private long id;

    /**
     * Author
     */
    private MendelUser author;

    /**
     * Project
     */
    private MendelProject project;

    /**
     * Document name
     */
    private String name;

    /**
     * Document description
     */
    private String description;

    /**
     * Type
     */
    private MendelDocumentType type;

    /**
     * Create date
     */
    private Date cDate;

    /**
     * Update date
     */
    private Date mDate;

    /**
     * Get document id
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Set document id
     *
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get containing project
     *
     * @return
     */
    public MendelProject getProject() {
        return project;
    }

    /**
     * Set the containing project
     *
     * @param project
     */
    public void setProject(MendelProject project) {
        this.project = project;
    }

    /**
     * Get document name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set document name
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get author
     *
     * @return
     */
    public MendelUser getAuthor() {
        return author;
    }

    /**
     * Set author
     *
     * @param author
     */
    public void setAuthor(MendelUser author) {
        this.author = author;
    }

    /**
     * Get description
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get create date
     *
     * @return the cDate
     */
    public Date getcDate() {
        return cDate;
    }

    /**
     * Set create date
     *
     * @param cDate the cDate to set
     */
    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }

    /**
     * Get update date
     *
     * @return the mDate
     */
    public Date getmDate() {
        return mDate;
    }

    /**
     * Set update date
     *
     * @param mDate the mDate to set
     */
    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    /**
     * @return the type
     */
    public MendelDocumentType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(MendelDocumentType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Document{" + "id=" + id + ", author=" + author + ", project=" + project + ", name=" + name + ", description=" + description + ", type=" + type + ", cDate=" + cDate + ", mDate=" + mDate + '}';
    }
}
