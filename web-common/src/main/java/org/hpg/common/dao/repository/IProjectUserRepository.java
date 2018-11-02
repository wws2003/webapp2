/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.repository;

import org.hpg.common.model.entity.ProjectUserEntity;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for project-user relationship
 *
 * @author trungpt
 */
public interface IProjectUserRepository extends ICRUDRepository<ProjectUserEntity, Long> {

    /**
     * Delete by project id (delete by default method does not take effect
     * probably, so have to use this solution)
     *
     * @param projectId
     * @return Number of deleted records
     * @throws MendelRuntimeException When delete failed
     */
    @Modifying
    @Query("delete from ProjectUserEntity u where u.projectId = ?1")
    int deleteByProjectId(long projectId) throws MendelRuntimeException;
}
