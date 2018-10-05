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
     * Login timestamp in YYYY/MM/DD hh:mm:ss format. Empty if user not
     * login<BR>
     * TODO Use serializable Optional
     */
    private final String loginTimeStamp;

    /**
     * Constructor. Almost info retrieved from the base model
     *
     * @param user
     * @param loginTimeStamp
     */
    public ScrnUserRecord(MendelUser user, String loginTimeStamp) {
        this.id = user.getId();
        this.name = user.getName();
        this.displayedName = user.getDispName();
        this.role = user.getRole();
        this.loginTimeStamp = loginTimeStamp;
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

    public String getLoginTimeStamp() {
        return loginTimeStamp;
    }

    public MendelRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "ScrnUserRecord{" + "id=" + id + ", name=" + name + ", displayedName=" + displayedName + ", role=" + role + ", loginTimeStamp=" + loginTimeStamp + '}';
    }
}
