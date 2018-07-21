/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.auth.constant;

/**
 * Authentication error types for this system
 *
 * @author wws2003
 */
public enum AuthenticationErrorType {
    // TODO Move messages to resource files (possibly by changing message field to message code field)
    // TODO Add more types of errors
    UNKNOWN_ERROR(-1, "Unknown error"),
    WRONG_USERNAME_PASSWORD(1, "Invalid username or password"),
    INVALID_ACCOUNT_STATUS(2, "Invalid account status");

    private final int code;

    private final String message;

    private AuthenticationErrorType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Get the error code
     *
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     * Get the error message
     *
     * @return
     */
    public String getMessage() {
        return message;
    }
}
