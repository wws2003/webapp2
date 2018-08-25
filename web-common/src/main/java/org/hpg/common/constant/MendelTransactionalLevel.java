/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.constant;

/**
 * Transactional levels
 *
 * @author wws2003
 */
public enum MendelTransactionalLevel {

    INVALID(-1, false),
    /**
     * No transaction required
     */
    NONE(0, false),
    /**
     * Use current transaction, create one if none available
     */
    DEFAULT(1, false),
    /**
     * Use current transaction, create one if none available. Should be read
     * only
     */
    DEFAULT_READONLY(1, true),
    /**
     * New read only transaction
     */
    NEW(2, false),
    /**
     * New read only transaction. Should be read only
     */
    NEW_READONLY(2, true),
    /**
     * Current transaction, do not create a new one even if none available. Not
     * read only
     */
    CURRENT(3, false),
    /**
     * Current transaction, do not create a new one even if none available.
     * Should be read only
     */
    CURRENT_READONLY(3, true);

    private final int val;

    private final boolean readOnly;

    private MendelTransactionalLevel(int val, boolean readOnly) {
        this.val = val;
        this.readOnly = readOnly;
    }

    /**
     * Get value
     *
     * @return
     */
    public int getVal() {
        return val;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public int getCode() {
        return val * 10 + (readOnly ? 1 : 0);
    }
}
