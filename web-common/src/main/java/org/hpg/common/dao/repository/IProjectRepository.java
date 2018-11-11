/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.repository;

import java.util.List;
import org.hpg.common.model.entity.ProjectEntity;
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
public interface IProjectRepository extends IPagingAndSortingRepository<ProjectEntity, Long> {

    /**
     * Delete users by ID and return deleted records
     *
     * @param projectIds
     * @return
     * @throws MendelRuntimeException When delete operation failed
     */
    List<ProjectEntity> deleteByIdIn(List<Long> projectIds) throws MendelRuntimeException;

    /**
     * Update the project entity. Have to use this method rather than the save()
     * method for the shake of keeping mDate to be default value
     *
     * @param projectEntity
     * @return
     * @throws MendelRuntimeException When update operation failed
     */
    @Modifying
    @Query("update from Project p set p.code = prj.code, p.displayedName = prj.displayedName, p.description = prj.description, p.status = prj.status, p.referScope = prj.referScope, p.mDate = DEFAULT  where p.id = prj.id")
    int update(@Param("prj") ProjectEntity projectEntity) throws MendelRuntimeException;
}
