/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.usermgt.form;

import java.io.Serializable;

/**
 * Form to request user detail info
 *
 * @author trungpt
 */
public class UserDetailForm implements Serializable {

    /**
     * ID of the user to request
     */
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserDetailForm{" + "userId=" + userId + '}';
    }
}
