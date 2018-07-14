/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.dto.user;

import org.hpg.common.constant.MendelRole;

/**
 * Model class for Mendel user
 *
 * @author trungpt
 */
public class MendelUser {

    /**
     * User unique ID
     */
    private long id;

    /**
     * User unique name
     */
    private String name;

    /**
     * User display name
     */
    private String dispName;

    /**
     * Password
     */
    private String password;

    /**
     * Encoded password
     */
    private String encodedPassword;

    /**
     * Role (in this system, no more one role for one user)
     */
    private MendelRole role;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the dispName
     */
    public String getDispName() {
        return dispName;
    }

    /**
     * @param dispName the dispName to set
     */
    public void setDispName(String dispName) {
        this.dispName = dispName;
    }

    /**
     * @return the role
     */
    public MendelRole getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(MendelRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "MendelUser{" + "id=" + id + ", name=" + name + ", dispName=" + dispName + ", role=" + role + '}';
    }

    /**
     * Get the password
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the encodedPassword
     */
    public String getEncodedPassword() {
        return encodedPassword;
    }

    /**
     * @param encodedPassword the encodedPassword to set
     */
    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }
}
