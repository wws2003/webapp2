/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.message;

import java.io.Serializable;
import java.util.List;

/**
 * Message contents info of recently login/logout user
 *
 * @author trungpt
 */
public class RecentLoginStatusMessage implements Serializable {

    /**
     * Most recent login user ids
     */
    private List<Long> loggedInUserIds;

    /**
     * Most recent logout user ids
     */
    private List<Long> loggedOutUserIds;

    public List<Long> getLoggedInUserIds() {
        return loggedInUserIds;
    }

    public void setLoggedInUserIds(List<Long> loggedInUserIds) {
        this.loggedInUserIds = loggedInUserIds;
    }

    public List<Long> getLoggedOutUserIds() {
        return loggedOutUserIds;
    }

    public void setLoggedOutUserIds(List<Long> loggedOutUserIds) {
        this.loggedOutUserIds = loggedOutUserIds;
    }

    @Override
    public String toString() {
        return "LoginMessage{" + "loggedInUserIds=" + loggedInUserIds + ", loggedOutUserIds=" + loggedOutUserIds + '}';
    }
}
