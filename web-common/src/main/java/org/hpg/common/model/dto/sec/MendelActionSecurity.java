/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.dto.sec;

import java.io.Serializable;
import java.util.List;
import org.hpg.common.constant.MendelPrivilege;

/**
 * Class to present security required for one action
 *
 * @author wws2003
 */
public class MendelActionSecurity implements Serializable {

    // TODO Think of add role
    /**
     * Related privileges
     */
    private final List<MendelPrivilege> privileges;

    /**
     * Flag to decide all related privileges required<BR>
     * TRUE: All related privileges required. FALSE: Only related privilege is
     * enough
     */
    private final boolean allPrivilegesRequired;

    /**
     * Constructor
     *
     * @param privileges
     * @param allPrivilegesRequired
     */
    public MendelActionSecurity(List<MendelPrivilege> privileges, boolean allPrivilegesRequired) {
        this.privileges = privileges;
        this.allPrivilegesRequired = allPrivilegesRequired;
    }

    /**
     * Get all related privileges
     *
     * @return
     */
    public List<MendelPrivilege> getPrivileges() {
        return privileges;
    }

    /**
     * Get all flag
     *
     * @return
     */
    public boolean isAllPrivilegesRequired() {
        return allPrivilegesRequired;
    }
}
