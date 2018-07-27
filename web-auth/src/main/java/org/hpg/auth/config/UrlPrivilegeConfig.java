/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.config;

import org.hpg.common.biz.annotation.MendelAction;
import org.hpg.common.constant.MendelPrivilege;

/**
 * Privilege config for URLs. Possibly move to web-all module ?
 *
 * @author trungpt
 */
public class UrlPrivilegeConfig {

    public static class AdminRole {
        // TODO Add more URLs

        @MendelAction(privileges = {MendelPrivilege.PRIV_MANAGE_USER})
        public static final String ADMIN_USER_MGT = "/admin/userMgt";

        @MendelAction(privileges = {MendelPrivilege.PRIV_MANAGE_SYSTEM})
        public static final String ADMIN_SYSTEM_MGT = "/admin/systemMgt";
    }

    public static class UserRole {
        // TODO Add  URLs
    }

}
