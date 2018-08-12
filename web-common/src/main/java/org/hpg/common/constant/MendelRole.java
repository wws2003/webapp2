/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.constant;

import java.util.Arrays;

/**
 * Roles in Mendel system
 *
 * @author wws2003
 */
public enum MendelRole {

    INVALID(-1, "Invalid"),
    ADMIN(1, "ADMIN"),
    USER(2, "USER"),
    GUEST(3, "GUEST"),
    DBA(4, "DBA");

    /**
     * Authority code
     */
    private final int code;

    /**
     * Authority name
     */
    private final String name;

    private MendelRole(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * Get the authority code
     *
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     * Get the authority name
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Get the enumeration value by specifying code
     *
     * @param roleCode
     * @return
     */
    public static MendelRole getRoleFromCode(int roleCode) {
        return Arrays.asList(MendelRole.values())
                .stream()
                .filter(role -> role.getCode() == roleCode)
                .findFirst()
                .orElse(INVALID);
    }
}
