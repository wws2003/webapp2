/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.projectmgt.scrnmodel;

import java.io.Serializable;

/**
 * Info of user to be assigned to project (result of searching by text)
 *
 * @author wws2003
 */
public class ScrnUserTag implements Serializable {

    /**
     * User unique ID
     */
    private long id;

    /**
     * User unique name
     */
    private String name;

    /**
     * User display name
     */
    private String dispName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDispName() {
        return dispName;
    }

    public void setDispName(String dispName) {
        this.dispName = dispName;
    }

    @Override
    public String toString() {
        return "ScrnUserTag{" + "id=" + id + ", name=" + name + ", dispName=" + dispName + '}';
    }
}
