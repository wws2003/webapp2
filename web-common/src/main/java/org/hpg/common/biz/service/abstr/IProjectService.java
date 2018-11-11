/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.abstr;

import java.util.List;
import java.util.Optional;
import org.hpg.common.model.dto.project.MendelProject;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Service for project biz logics
 *
 * @author wws2003
 */
public interface IProjectService {

    /**
     * Find by ID
     *
     * @param projectId
     * @return
     * @throws MendelRuntimeException When find failed
     */
    public Optional<MendelProject> findProjectById(long projectId) throws MendelRuntimeException;

    /**
     * Create new
     *
     * @param project
     * @return Newly created (persisted) project
     * @throws MendelRuntimeException When creation failed
     */
    MendelProject createProject(MendelProject project) throws MendelRuntimeException;

    /**
     * Update existing
     *
     * @param project
     * @return Updated instance of project
     * @throws MendelRuntimeException When update failed
     */
    MendelProject updateProject(MendelProject project) throws MendelRuntimeException;

    /**
     * Delete list of projects by id
     *
     * @param projectIds
     * @return Number of deleted projects
     * @throws MendelRuntimeException When delete operation failed
     */
    int deleteProjects(List<Long> projectIds) throws MendelRuntimeException;

    /**
     * Assign users to project (un-assign all other users)
     *
     * @param project
     * @param userIds
     * @throws MendelRuntimeException When assign operation failed
     */
    void assignUsersToProject(MendelProject project, List<Long> userIds) throws MendelRuntimeException;

    /**
     * Find user assigned
     *
     * @param project
     * @return
     * @throws MendelRuntimeException when retrieval failed
     */
    List<MendelUser> findUsersAssignedToProject(MendelProject project) throws MendelRuntimeException;
}
