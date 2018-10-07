/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.usermgt.form;

import java.io.Serializable;
import java.util.List;

/**
 * Users to force logout (similar to delete form)
 *
 * @author wws2003
 */
public class UserForceLogoutForm implements Serializable {

    /**
     * IDs of users to delete
     */
    private List<Long> userIdsToForceLogout;

    public List<Long> getUserIdsToForceLogout() {
        return userIdsToForceLogout;
    }

    public void setUserIdsToForceLogout(List<Long> userIdsToForceLogout) {
        this.userIdsToForceLogout = userIdsToForceLogout;
    }

    @Override
    public String toString() {
        return "UserForceLogoutForm{" + "userIdsToForceLogout=" + userIdsToForceLogout + '}';
    }
}
