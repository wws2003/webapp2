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
}
