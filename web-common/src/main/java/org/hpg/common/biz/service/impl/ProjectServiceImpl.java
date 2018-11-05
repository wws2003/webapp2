/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.impl;

import java.util.List;
import java.util.Optional;
import org.hpg.common.biz.service.abstr.IProjectService;
import org.hpg.common.dao.mapper.abstr.IEntityDtoMapper;
import org.hpg.common.dao.repository.IProjectRepository;
import org.hpg.common.dao.repository.IProjectUserRepository;
import org.hpg.common.model.dto.project.MendelProject;
import org.hpg.common.model.entity.ProjectEntity;
import org.hpg.common.model.entity.ProjectUserEntity;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Implementation of project biz service
 *
 * @author wws2003
 */
public class ProjectServiceImpl implements IProjectService {

    private final IProjectRepository projectRepository;

    private final IEntityDtoMapper<ProjectEntity, MendelProject> entityDtoMapper;

    private final IProjectUserRepository projectUserRepository;

    public ProjectServiceImpl(IProjectRepository projectRepository, IProjectUserRepository projectUserRepository, IEntityDtoMapper<ProjectEntity, MendelProject> entityDtoMapper) {
        this.projectRepository = projectRepository;
        this.projectUserRepository = projectUserRepository;
        this.entityDtoMapper = entityDtoMapper;
    }

    @Override
    public Optional<MendelProject> findProjectById(long projectId) throws MendelRuntimeException {
        return projectRepository.findById(projectId)
                .map(entityDtoMapper::getDtoFromEntity);
    }

    @Override
    public MendelProject createProject(MendelProject project) throws MendelRuntimeException {
        ProjectEntity savedEntity = projectRepository.save(entityDtoMapper.getEntityFromDto(project));
        return Optional.ofNullable(savedEntity).map(entityDtoMapper::getDtoFromEntity).orElse(null);
    }

    @Override
    public MendelProject updateProject(MendelProject project) throws MendelRuntimeException {
        ProjectEntity savedEntity = projectRepository.save(entityDtoMapper.getEntityFromDto(project));
        return Optional.ofNullable(savedEntity).map(entityDtoMapper::getDtoFromEntity).orElse(null);
    }

    @Override
    public int deleteProjects(List<Long> projectIds) throws MendelRuntimeException {
        List<ProjectEntity> deletedRecords = projectRepository.deleteByIdIn(projectIds);
        return deletedRecords.size();
    }

    @Override
    public void assignUsersToProject(MendelProject project, List<Long> userIds) throws MendelRuntimeException {
        final long projectId = project.getId();
        // 1. Delete all current assignment
        projectUserRepository.deleteByProjectId(projectId);
        // 2. Assign
        userIds.stream().forEach(userId -> {
            ProjectUserEntity entity = new ProjectUserEntity();
            entity.setProjectId(projectId);
            entity.setUserId(userId);
            projectUserRepository.save(entity);
        });
    }
}
