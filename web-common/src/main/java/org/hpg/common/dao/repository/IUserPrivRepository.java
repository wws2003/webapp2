/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.repository;

import java.util.List;
import org.hpg.common.model.entity.UserPrivEntity;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for user-privilege mapping
 *
 * @author trungpt
 */
public interface IUserPrivRepository extends ICRUDRepository<UserPrivEntity, Long> {

    /**
     * Find by user id
     *
     * @param userId
     * @return
     * @throws MendelRuntimeException When find failed
     */
    List<UserPrivEntity> findByUserId(long userId) throws MendelRuntimeException;

    /**
     * Delete by user id (delete by default method does not take effect
     * probably, so have to use this solution)
     *
     * @param userId
     * @return Number of deleted records
     * @throws MendelRuntimeException When delete failed
     */
    @Modifying
    @Query("delete from UserPrivEntity u where u.userId = ?1")
    int deleteByUserId(long userId) throws MendelRuntimeException;
}
