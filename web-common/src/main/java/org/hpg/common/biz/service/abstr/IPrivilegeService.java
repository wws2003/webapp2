/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.abstr;

import java.util.List;
import org.hpg.common.constant.MendelPrivilege;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Service to manage user-privilege
 *
 * @author trungpt
 */
public interface IPrivilegeService {

    /**
     * Get privileges granted to the specified user
     *
     * @param userId
     * @return
     * @throws MendelRuntimeException
     */
    List<MendelPrivilege> getUserGrantedPrivileges(long userId) throws MendelRuntimeException;

    /**
     * Check if the specified privilege has been granted to the specified user
     *
     * @param userId
     * @param privilege
     * @return
     * @throws MendelRuntimeException
     */
    boolean hasPrivilege(long userId, MendelPrivilege privilege) throws MendelRuntimeException;

    /**
     * Grant the specified user with the specified privileges
     *
     * @param userId
     * @param privileges
     * @return Number of privileges succesfully granted
     * @throws MendelRuntimeException
     */
    int grantUserPrivileges(long userId, List<MendelPrivilege> privileges) throws MendelRuntimeException;

    /**
     * Revoke the specified user with the specified privileges
     *
     * @param userId
     * @param privileges
     * @return Number of privileges succesfully revoked (if any privilege not
     * granted to the user yet, do nothing with it)
     * @throws MendelRuntimeException
     */
    int revokePrivileges(long userId, List<MendelPrivilege> privileges) throws MendelRuntimeException;
}
