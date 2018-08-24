/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.abstr;

/**
 * Services for password. Concrete instance provided by security implementation
 * module. TODO: Think over if this class should be in common module
 *
 * @author trungpt
 */
public interface IPasswordService {

    /**
     * Encode raw password. TODO Add salt...
     *
     * @param rawPassword
     * @return The encoded password
     */
    public String getEncodedPassword(String rawPassword);
}
