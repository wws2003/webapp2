/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.dto.document;

import java.io.Serializable;
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
     * Document name
     */
    private String name;

    /**
     * Document content
     */
    private String content;

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
     * Get content
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * Set content
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Document{" + "id=" + id + ", author=" + author + ", name=" + name + ", content=" + content + '}';
    }
}
