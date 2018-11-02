/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.constant;

import java.util.Arrays;

/**
 * Project status
 *
 * @author trungpt
 */
public enum MendelProjectStatus {
    INVALID((short) -1, "INVALID"),
    ACTIVE((short) 1, "ACTIVE"),
    PENDING((short) 2, "PENDING"),
    CLOSE((short) 3, "CLOSE");

    /**
     * Authority code
     */
    private final short code;

    /**
     * Authority name
     */
    private final String name;

    private MendelProjectStatus(short code, String name) {
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
    public static MendelProjectStatus getProjectStatusByCode(short codeId) {
        return Arrays.asList(MendelProjectStatus.values())
                .stream()
                .filter(role -> role.getCode() == codeId)
                .findFirst()
                .orElse(INVALID);
    }
}
