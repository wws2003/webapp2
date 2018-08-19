/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.form;

import java.io.Serializable;
import java.util.List;

/**
 * Form to add/update user
 *
 * @author wws2003
 */
public class UserAddUpdateForm implements Serializable {

    /**
     * Flag to determine if update/create
     */
    private boolean toCreateUser;

    /**
     * User id. In the case of create, has the value -1
     */
    private long userId;

    /**
     * User name
     */
    private String userName;

    /**
     * Displayed user name
     */
    private String userDipsName;

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

    public boolean getToCreateUser() {
        return toCreateUser;
    }

    public void setToCreateUser(boolean toCreateUser) {
        this.toCreateUser = toCreateUser;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDipsName() {
        return userDipsName;
    }

    public void setUserDipsName(String userDipsName) {
        this.userDipsName = userDipsName;
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
        return "UserAddUpdateForm{" + "toUpdateUser=" + toCreateUser + ", userId=" + userId + ", userName=" + userName + ", userDipsName=" + userDipsName + ", rawPassword=" + rawPassword + ", confirmedRawPassword=" + confirmedRawPassword + ", grantedPrivilegeIds=" + grantedPrivilegeIds + '}';
    }
}
