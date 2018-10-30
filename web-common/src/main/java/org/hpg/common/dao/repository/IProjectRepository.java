/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.repository;

import org.hpg.common.model.entity.ProjectEntity;
import org.springframework.stereotype.Repository;

/**
 * Repository for user (do create instance at runtime)
 *
 * @author trungpt
 */
@Repository
public interface IProjectRepository extends IPagingAndSortingRepository<ProjectEntity, Long> {

}
