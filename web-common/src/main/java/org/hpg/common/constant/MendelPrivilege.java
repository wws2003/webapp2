/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.constant;

/**
 * Privilege for Mendel system
 *
 * @author wws2003
 */
public enum MendelPrivilege {
    INVALID(-1, "NONE", "Invalid"),
    // Admin-related
    PRIV_MANAGE_USER(1, "M001", "Manage users"),
    PRIV_MANAGE_SYSTEM(2, "M002", "Manage system"),
    // Document-related
    PRIV_CREATE_DOCUMENT(5, "M005", "Create document"),
    PRIV_SHARE_DOCUMENT(6, "M006", "Index document (for fulltext search)"),
    PRIV_UPDATE_DOCUMENT(7, "M007", "Comment public document"),
    PRIV_LIST_DOCUMENT(8, "M008", "List public document"),
    PRIV_SEARCH_DOCUMENT(9, "M009", "Search public document");

    /**
     * Privilege id
     */
    private final int id;

    /**
     * Privilege code
     */
    private final String code;

    /**
     * Authority dispName
     */
    private final String dispName;

    /**
     * Constructor
     *
     * @param id
     * @param code
     * @param name
     */
    private MendelPrivilege(int id, String code, String name) {
        this.id = id;
        this.code = code;
        this.dispName = name;
    }

    /**
     * Get the privilege id
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Get the authority dispName
     *
     * @return
     */
    public String getDispName() {
        return dispName;
    }

}
