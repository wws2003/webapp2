/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.repository;

import java.util.List;
import java.util.Optional;
import org.hpg.common.model.entity.UserEntity;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for user (do not create instance at runtime)
 *
 * @author trungpt
 */
@Repository
public interface IUserRepository extends IPagingAndSortingRepository<UserEntity, Long> {

    /**
     * Find user by user name and role
     *
     * @param userName
     * @param roleId
     * @return
     * @throws MendelRuntimeException When find operation failed
     */
    @Query("select u from UserEntity u where u.name = :userName and u.role.id = :roleId")
    Optional<UserEntity> findByUserNameAndRoleId(@Param("userName") String userName, @Param("roleId") int roleId) throws MendelRuntimeException;

    /**
     * Delete in bulk by ID
     *
     * @param userIds
     * @throws MendelRuntimeException When delete operation failed
     */
    @Modifying(flushAutomatically = true)
    @Query("delete from UserEntity u where u.id in ?1")
    void deleteUsersById(List<Long> userIds) throws MendelRuntimeException;
}
