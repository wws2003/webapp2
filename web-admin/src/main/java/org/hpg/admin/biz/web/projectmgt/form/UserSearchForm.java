/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.projectmgt.form;

import java.io.Serializable;

/**
 * Form to search for user to assign to project
 *
 * @author wws2003
 */
public class UserSearchForm implements Serializable {

    /**
     * Text in user name/display name
     */
    private String userText;

    public String getUserText() {
        return userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    @Override
    public String toString() {
        return "UserSearchForm{" + "userText=" + userText + '}';
    }
}
