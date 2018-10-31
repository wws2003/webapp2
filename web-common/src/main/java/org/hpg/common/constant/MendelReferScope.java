/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.constant;

/**
 * Refer scope for one element (would be public/private)
 *
 * @author trungpt
 */
public enum MendelReferScope {
    INVALID(-1, "INVALID"),
    PUBLIC(1, "PUBLIC"),
    PRIVATE(2, "PRIVATE");

    /**
     * Authority code
     */
    private final int code;

    /**
     * Authority name
     */
    private final String name;

    private MendelReferScope(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
