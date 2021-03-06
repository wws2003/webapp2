/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.abstr;

import java.util.List;
import java.util.Optional;
import org.hpg.common.constant.MendelPrivilege;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Interface for user service
 *
 * @author trungpt
 */
public interface IUserService {

    /**
     * Find user by id
     *
     * @param userId
     * @return
     * @throws MendelRuntimeException When find operation failed
     */
    Optional<MendelUser> findUserById(long userId) throws MendelRuntimeException;

    /**
     * Find user by user name and role
     *
     * @param userName
     * @param role
     * @return
     * @throws MendelRuntimeException When find operation failed
     */
    Optional<MendelUser> findUserByName(String userName, MendelRole role) throws MendelRuntimeException;

    /**
     * Create new user
     *
     * @param user
     * @return Saved user instance (id set)
     * @throws MendelRuntimeException When create operation failed
     */
    MendelUser createUser(MendelUser user) throws MendelRuntimeException;

    /**
     * Update existing user
     *
     * @param user
     * @return Updated user instance
     * @throws MendelRuntimeException When update operation failed
     */
    MendelUser updateUser(MendelUser user) throws MendelRuntimeException;

    /**
     * Grant user with specified privileges (revoke all other privileges)
     *
     * @param user
     * @param privileges
     * @throws MendelRuntimeException
     */
    void grantUserWithPrivileges(MendelUser user, List<MendelPrivilege> privileges) throws MendelRuntimeException;

    /**
     * Get privileges granted to the specified user
     *
     * @param userId
     * @return
     * @throws MendelRuntimeException
     */
    List<MendelPrivilege> getUserGrantedPrivileges(long userId) throws MendelRuntimeException;

    /**
     * Delete list of users by id
     *
     * @param userIds
     * @return Number of deleted users
     * @throws MendelRuntimeException When delete operation failed
     */
    int deleteUsers(List<Long> userIds) throws MendelRuntimeException;

    /**
     * Find users with name or displayed name contains the given text (results
     * are ordered by ID)
     *
     * @param text
     * @return
     * @throws MendelRuntimeException When retrieval operation failed
     */
    List<MendelUser> findUsers(String text) throws MendelRuntimeException;
}
