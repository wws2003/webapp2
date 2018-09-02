/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.usermgt.scrnmodel;

import java.io.Serializable;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.model.dto.user.MendelUser;

/**
 * Record model for one user (to display in index screen)
 *
 * @author trungpt
 */
public class ScrnUserRecord implements Serializable {

    private final long id;

    private final String name;

    private final String displayedName;

    private final MendelRole role;

    /**
     * Flag to determine login status
     */
    private final boolean loggingIn;

    /**
     * Constructor. Almost info retrieved from the base model
     *
     * @param user
     * @param loggingIn
     */
    public ScrnUserRecord(MendelUser user, boolean loggingIn) {
        this.id = user.getId();
        this.name = user.getName();
        this.displayedName = user.getDispName();
        this.role = user.getRole();
        this.loggingIn = loggingIn;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public boolean isLoggingIn() {
        return loggingIn;
    }

    public MendelRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "ScrnUserRecord{" + "id=" + id + ", name=" + name + ", displayedName=" + displayedName + ", role=" + role + ", loggingIn=" + loggingIn + '}';
    }
}
