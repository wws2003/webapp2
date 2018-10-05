/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.message;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Message contents info of recently login/logout user
 *
 * @author trungpt
 */
public class RecentLoginStatusMessage implements Serializable {

    /**
     * Most recent login user ids (with logintimestamp info)
     */
    private Map<Long, String> loggedInUserMap;

    /**
     * Most recent logout user ids
     */
    private List<Long> loggedOutUserIds;

    public Map<Long, String> getLoggedInUserMap() {
        return loggedInUserMap;
    }

    public void setLoggedInUserMap(Map<Long, String> loggedInUserMap) {
        this.loggedInUserMap = loggedInUserMap;
    }

    public List<Long> getLoggedOutUserIds() {
        return loggedOutUserIds;
    }

    public void setLoggedOutUserIds(List<Long> loggedOutUserIds) {
        this.loggedOutUserIds = loggedOutUserIds;
    }

    @Override
    public String toString() {
        return "LoginMessage{" + "loggedInUserIds=" + loggedInUserMap + ", loggedOutUserIds=" + loggedOutUserIds + '}';
    }
}
