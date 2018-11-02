/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.constant;

import java.util.Arrays;

/**
 * Refer scope for one element (would be public/private)
 *
 * @author trungpt
 */
public enum MendelReferScope {
    INVALID((short) -1, "INVALID"),
    PUBLIC((short) 1, "PUBLIC"),
    PRIVATE((short) 2, "PRIVATE");

    /**
     * Authority code
     */
    private final short code;

    /**
     * Authority name
     */
    private final String name;

    private MendelReferScope(short code, String name) {
        this.code = code;
        this.name = name;
    }

    public short getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * Get the enumeration value by specifying id
     *
     * @param codeId
     * @return
     */
    public static MendelReferScope getProjectReferScopeByCode(short codeId) {
        return Arrays.asList(MendelReferScope.values())
                .stream()
                .filter(role -> role.getCode() == codeId)
                .findFirst()
                .orElse(INVALID);
    }
}
