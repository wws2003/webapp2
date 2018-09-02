/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.usermgt.scrnmodel;

import java.io.Serializable;
import java.util.List;
import org.hpg.common.constant.MendelPrivilege;

/**
 * Model class for user details to display on screen
 *
 * @author trungpt
 */
public class ScrnUserDetail implements Serializable {

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

    /**
     * Granted privileges
     */
    private List<MendelPrivilege> grantedPrivileges;

    /**
     * Not granted privileges
     */
    private List<MendelPrivilege> remainingGrantablePrivileges;

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

    public List<MendelPrivilege> getGrantedPrivileges() {
        return grantedPrivileges;
    }

    public void setGrantedPrivileges(List<MendelPrivilege> grantedPrivileges) {
        this.grantedPrivileges = grantedPrivileges;
    }

    public List<MendelPrivilege> getRemainingGrantablePrivileges() {
        return remainingGrantablePrivileges;
    }

    public void setRemainingGrantablePrivileges(List<MendelPrivilege> remainingGrantablePrivileges) {
        this.remainingGrantablePrivileges = remainingGrantablePrivileges;
    }

    @Override
    public String toString() {
        return "ScrnUserDetail{" + "id=" + id + ", name=" + name + ", dispName=" + dispName + ", grantedPrivileges=" + grantedPrivileges + ", remainingGrantablePrivileges=" + remainingGrantablePrivileges + '}';
    }
}
