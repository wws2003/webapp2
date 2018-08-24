/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.usermgt.form;

import java.io.Serializable;
import java.util.List;

/**
 * Users to delete
 *
 * @author trungpt
 */
public class UserDeleteForm implements Serializable {

    /**
     * IDs of users to delete
     */
    private List<Long> userIdsToDelete;

    public List<Long> getUserIdsToDelete() {
        return userIdsToDelete;
    }

    public void setUserIdsToDelete(List<Long> userIdsToDelete) {
        this.userIdsToDelete = userIdsToDelete;
    }

    @Override
    public String toString() {
        return "UserDeleteForm{" + "userIdsToDelete=" + userIdsToDelete + '}';
    }
}
