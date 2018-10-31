/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.repository;

import java.util.List;
import org.hpg.common.model.entity.ProjectEntity;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.springframework.stereotype.Repository;

/**
 * Repository for user (do create instance at runtime)
 *
 * @author trungpt
 */
@Repository
public interface IProjectRepository extends IPagingAndSortingRepository<ProjectEntity, Long> {

    /**
     * Delete users by ID and return deleted records
     *
     * @param projectIds
     * @return
     * @throws MendelRuntimeException When delete operation failed
     */
    List<ProjectEntity> deleteByIdIn(List<Long> projectIds) throws MendelRuntimeException;
}
