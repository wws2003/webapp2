/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.constant;

/**
 * Project status
 *
 * @author trungpt
 */
public enum MendelProjectStatus {
    INVALID(-1, "INVALID"),
    ACTIVE(1, "ACTIVE"),
    PENDING(2, "PENDING"),
    CLOSE(3, "CLOSE");

    /**
     * Authority code
     */
    private final int code;

    /**
     * Authority name
     */
    private final String name;

    private MendelProjectStatus(int code, String name) {
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
