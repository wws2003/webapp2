/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.constant;

/**
 * URLs for admin actions
 *
 * @author wws2003
 */
public class AdminUrls {

    /**
     * Root URL for actions in admin module
     */
    public static final String ADMIN_ROOT_URL = "admin";

    /**
     * Home URL
     */
    public static final String ADMIN_HOME = "/home";

    /**
     * User manager URL
     */
    public static final String ADMIN_USER_MANAGEMENT = "/userMgt";

    /**
     * User manager root URL
     */
    public static final String ADMIN_USER_MANAGEMENT_ROOT_URL = ADMIN_ROOT_URL + "/userMgt";

    /**
     * User manager: index (TODO including paging)
     */
    public static final String ADMIN_USER_MANAGEMENT_INDEX = "/index";

    /**
     * User manager: detail info of one user
     */
    public static final String ADMIN_USER_MANAGEMENT_USER_DETAILS = "/detail";

    /**
     * User manager: Get all privileges for user
     */
    public static final String ADMIN_USER_MANAGEMENT_ALL_USER_PRIVS = "/userPrivs";

    /**
     * User manager: Add/update
     */
    public static final String ADMIN_USER_MANAGEMENT_ADD_UPDATE = "/addUpdate";

    /**
     * User manager: Delete
     */
    public static final String ADMIN_USER_MANAGEMENT_DELETE = "/delete";

    /**
     * User manager: Force logout
     */
    public static final String ADMIN_USER_FORCE_LOGOUT = "/forceLogout";

    /**
     * System manager URL
     */
    public static final String ADMIN_SYSTEM_MANAGEMENT = "/systemMgt";

    /**
     * Project manager root URL
     */
    public static final String ADMIN_PROJECT_MANAGEMENT = "/projectMgt";

    /**
     * Project manager root URL
     */
    public static final String ADMIN_PROJECT_MANAGEMENT_ROOT_URL = ADMIN_ROOT_URL + "/projectMgt";

    /**
     * Project manager: Index (including paging)
     */
    public static final String ADMIN_PROJECT_MANAGEMENT_INDEX = "/index";

    /**
     * Project manager: URL to search users to assign to project
     */
    public static final String ADMIN_PROJECT_MANAGEMENT_SEARCH_USER = "/searchUser";

    /**
     * Project manager: Add/update
     */
    public static final String ADMIN_PROJECT_MANAGEMENT_ADD_UPDATE = "/addUpdate";

    /**
     * Project manager: Delete
     */
    public static final String ADMIN_PROJECT_MANAGEMENT_DELETE = "/delete";
}
