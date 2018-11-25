/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.projectmgt.form;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hpg.admin.biz.web.common.form.SimpleRequestByIDForm;

/**
 * Project add/update form
 *
 * @author wws2003
 */
public class ProjectAddUpdateForm implements Serializable {
    // TODO Add validation properly

    /**
     * ID form. If ID &lt 0 consider add form, otherwise consider update form
     */
    @NotNull
    private SimpleRequestByIDForm idForm;

    private String code;

    private String displayedName;

    private String description;

    private short referScopeCode;

    private short statusCode;

    /**
     * IDs of users to assign
     */
    private List<Long> userIds;

    public SimpleRequestByIDForm getIdForm() {
        return idForm;
    }

    public void setIdForm(SimpleRequestByIDForm idForm) {
        this.idForm = idForm;
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
        return "ProjectAddUpdateForm{" + "idForm=" + idForm + ", code=" + code + ", displayedName=" + displayedName + ", description=" + description + ", referScopeCode=" + referScopeCode + ", statusCode=" + statusCode + ", userIds=" + userIds + '}';
    }
}
