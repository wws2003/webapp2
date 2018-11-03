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
 * Repository for user (do create instance at runtime)
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
     * Delete users by ID and return deleted records
     *
     * @param userIds
     * @return
     * @throws MendelRuntimeException When delete operation failed
     */
    List<UserEntity> deleteByIdIn(List<Long> userIds) throws MendelRuntimeException;

    /**
     * Delete in-batch users by ID and return deleted records. Currently does
     * not work (due to some Hibernate error ?)
     *
     * @param userIds
     * @return
     * @throws MendelRuntimeException When delete operation failed
     */
    @Modifying
    @Query("delete from UserEntity u where u.id in ?1")
    List<UserEntity> deleteBatchByIdIn(List<Long> userIds) throws MendelRuntimeException;

    /**
     * Find users having name or display name containg given text (ignore case)
     *
     * @param text
     * @param roleId
     * @return
     * @throws MendelRuntimeException
     */
    @Query("select u from UserEntity u where (UPPER(u.name) like UPPER(:text) or UPPER(u.displayedName) like UPPER(:text)) and (u.role.id = :roleId) order by u.id")
    List<UserEntity> findByUserNameOrDisplayName(@Param("text") String text, @Param("roleId") int roleId) throws MendelRuntimeException;

    /**
     * Find users assigned to the project
     *
     * @param projectId
     * @return
     * @throws MendelRuntimeException
     */
    @Query("select u from UserEntity u where u.id in (select pu.userId from ProjectUserEntity pu where pu.projectId = :projectId)")
    List<UserEntity> findUsersInProject(@Param("projectId") long projectId) throws MendelRuntimeException;
}
