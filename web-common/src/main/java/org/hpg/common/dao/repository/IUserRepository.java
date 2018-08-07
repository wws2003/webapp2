/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.repository;

import java.util.Optional;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Repository for user
 *
 * @author trungpt
 */
public interface IUserRepository extends ICRUDRepository<MendelUser, Long> {

    /**
     * Find user by user name and role
     *
     * @param userName
     * @param role
     * @return
     * @throws MendelRuntimeException When find operation failed
     */
    Optional<MendelUser> findUserByName(String userName, MendelRole role) throws MendelRuntimeException;
}
