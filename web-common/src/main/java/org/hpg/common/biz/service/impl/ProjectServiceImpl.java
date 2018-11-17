/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.hpg.common.biz.service.abstr.IProjectService;
import org.hpg.common.dao.mapper.abstr.IEntityDtoMapper;
import org.hpg.common.dao.repository.IProjectRepository;
import org.hpg.common.dao.repository.IProjectUserRepository;
import org.hpg.common.model.dto.project.MendelProject;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.entity.ProjectEntity;
import org.hpg.common.model.entity.ProjectUserEntity;
import org.hpg.common.model.entity.UserEntity;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Implementation of project biz service
 *
 * @author wws2003
 */
public class ProjectServiceImpl implements IProjectService {

    private final IProjectRepository projectRepository;

    private final IProjectUserRepository projectUserRepository;

    private final IEntityDtoMapper<ProjectEntity, MendelProject> projectEntityDtoMapper;

    private final IEntityDtoMapper<UserEntity, MendelUser> userEntityDtoMapper;

    /**
     * Constructor
     *
     * @param projectRepository
     * @param projectUserRepository
     * @param projectEntityDtoMapper
     * @param userEntityDtoMapper
     */
    public ProjectServiceImpl(IProjectRepository projectRepository, IProjectUserRepository projectUserRepository, IEntityDtoMapper<ProjectEntity, MendelProject> projectEntityDtoMapper, IEntityDtoMapper<UserEntity, MendelUser> userEntityDtoMapper) {
        this.projectRepository = projectRepository;
        this.projectUserRepository = projectUserRepository;
        this.projectEntityDtoMapper = projectEntityDtoMapper;
        this.userEntityDtoMapper = userEntityDtoMapper;
    }

    @Override
    public Optional<MendelProject> findProjectById(long projectId) throws MendelRuntimeException {
        return projectRepository.findById(projectId)
                .map(projectEntityDtoMapper::getDtoFromEntity);
    }

    @Override
    public MendelProject createProject(MendelProject project) throws MendelRuntimeException {
        ProjectEntity savedEntity = projectRepository.save(projectEntityDtoMapper.getEntityFromDto(project));
        return Optional.ofNullable(savedEntity).map(projectEntityDtoMapper::getDtoFromEntity).orElse(null);
    }

    @Override
    public MendelProject updateProject(MendelProject project) throws MendelRuntimeException {
        // Updating mDate is delegated to trigger, so there is no difference in code to the createProject method !
        ProjectEntity savedEntity = projectRepository.save(projectEntityDtoMapper.getEntityFromDto(project));
        return Optional.ofNullable(savedEntity).map(projectEntityDtoMapper::getDtoFromEntity).orElse(null);
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

    @Override
    public List<MendelUser> findUsersAssignedToProject(MendelProject project) throws MendelRuntimeException {
        return projectUserRepository.findByProjectId(project.getId())
                .stream()
                .map(userEntityDtoMapper::getDtoFromEntity)
                .collect(Collectors.toList());
    }
}
