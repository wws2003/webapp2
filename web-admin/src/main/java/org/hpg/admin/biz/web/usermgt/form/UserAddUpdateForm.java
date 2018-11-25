/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.usermgt.form;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hpg.admin.biz.web.common.form.SimpleRequestByIDForm;

/**
 * Form to add/update user
 *
 * @author wws2003
 */
public class UserAddUpdateForm implements Serializable {

    /**
     * ID form. If ID &lt 0 consider add form, otherwise consider update form
     */
    @NotNull
    private SimpleRequestByIDForm idForm;

    /**
     * User name
     */
    private String userName;

    /**
     * Displayed user name
     */
    private String userDispName;

    /**
     * Raw password
     */
    private String rawPassword;

    /**
     * Confirmed raw password
     */
    private String confirmedRawPassword;

    /**
     * Granted privileges
     */
    private List<Integer> grantedPrivilegeIds;

    public SimpleRequestByIDForm getIdForm() {
        return idForm;
    }

    public void setIdForm(SimpleRequestByIDForm idForm) {
        this.idForm = idForm;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDispName() {
        return userDispName;
    }

    public void setUserDispName(String userDispName) {
        this.userDispName = userDispName;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    public String getConfirmedRawPassword() {
        return confirmedRawPassword;
    }

    public void setConfirmedRawPassword(String confirmedRawPassword) {
        this.confirmedRawPassword = confirmedRawPassword;
    }

    public List<Integer> getGrantedPrivilegeIds() {
        return grantedPrivilegeIds;
    }

    public void setGrantedPrivilegeIds(List<Integer> grantedPrivilegeIds) {
        this.grantedPrivilegeIds = grantedPrivilegeIds;
    }

    @Override
    public String toString() {
        return "UserAddUpdateForm{" + "idForm=" + idForm + ", userName=" + userName + ", userDispName=" + userDispName + ", rawPassword=" + rawPassword + ", confirmedRawPassword=" + confirmedRawPassword + ", grantedPrivilegeIds=" + grantedPrivilegeIds + '}';
    }
}
