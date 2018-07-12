/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.dto.document;

import java.io.Serializable;

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
     * Document name
     */
    private String name;

    /**
     * Comment for document
     */
    private String comment;

    /**
     * Constructor
     *
     * @param id
     * @param name
     * @param comment
     */
    public Document(long id, String name, String comment) {
        this.id = id;
        this.name = name;
        this.comment = comment;
    }

    // TODO Add owner info
    @Override
    public String toString() {
        return "Document{" + "id=" + getId() + ", name=" + getName() + ", comment=" + getComment() + '}';
    }

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
     * Get document comment
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set document comment
     *
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
