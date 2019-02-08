/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.constant;

import java.util.Arrays;

/**
 * Types for document
 *
 * @author wws2003
 */
public enum MendelDocumentType {
    INVALID((short) -1, "INVALID"),
    TEXT_ORIGIN((short) 1, "TEXT_ORIGIN"),
    IMAGE_ORIGIN((short) 2, "IMAGE_ORIGIN");

    /**
     * Authority code
     */
    private final short code;

    /**
     * Authority name
     */
    private final String name;

    private MendelDocumentType(short code, String name) {
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
    public static MendelDocumentType getDocumentTypeByCode(short codeId) {
        return Arrays.asList(MendelDocumentType.values())
                .stream()
                .filter(role -> role.getCode() == codeId)
                .findFirst()
                .orElse(INVALID);
    }
}
