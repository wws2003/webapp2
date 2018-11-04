/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.constant;

/**
 * URLs for authentication actions
 *
 * @author wws2003
 */
public class AuthUrls {

    // TODO Add more URLs
    // TODO Colaborate with SecurityConfig
    /**
     * Root URL for actions in auth module
     */
    public static final String AUTH_ROOT_URL = "/auth";

    /**
     * Login action URL for admin role
     */
    public static final String ADMIN_LOGIN = "/adminLogin";

    /**
     * Login action URL for user role
     */
    public static final String USER_LOGIN = "/userLogin";

    /**
     * Admin login failed action URL
     */
    public static final String ADMIN_LOGIN_ERROR = "/adminLogin";

    /**
     * User login failed action URL
     */
    public static final String USER_LOGIN_ERROR = "/userLogin";

    /**
     * 403 error action URL
     */
    public static final String FORBIDDEN_ACTION = "/forbiddenAction";

    /**
     * 403 error redirect user page
     */
    public static final String FORBIDDEN_PAGE = "/forbiddenPage";

    /**
     * Session error for ADMIN
     */
    public static final String ADMIN_SESSION_AUTH_FAILURE_URL = "/adminSessionFailure";

    /**
     * Session error for user
     */
    public static final String USER_SESSION_AUTH_FAILURE_URL = "/userSessionFailure";

    /**
     * URLs for the role ADMIN
     */
    public static final class AdminRole {

        /**
         * Prefix
         */
        public static final String PREFIX = "/admin";

        /**
         * Login process
         */
        public static final String LOGIN_PROCESS = "/login";

        /**
         * Logout process
         */
        public static final String LOGOUT_PROCESS = "/logout";

    }

    /**
     * URLs for the role USEr
     */
    public static final class UserRole {

        /**
         * Prefix for management pages (setting, profile management...)
         */
        public static final String MGT_PREFIX = "/user";

        /**
         * Prefix for project pages
         */
        public static final String PRJ_PREFIX = "/project";

        /**
         * Login process
         */
        public static final String LOGIN_PROCESS = "/login";

        /**
         * Logout process
         */
        public static final String LOGOUT_PROCESS = "/logout";
    }
}
