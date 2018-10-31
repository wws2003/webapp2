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
import org.hpg.common.model.dto.project.MendelProject;
import org.hpg.common.model.entity.ProjectEntity;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Implementation of project biz service
 *
 * @author wws2003
 */
public class ProjectServiceImpl implements IProjectService {

    private final IProjectRepository projectRepository;

    private final IEntityDtoMapper<ProjectEntity, MendelProject> entityDtoMapper;

    public ProjectServiceImpl(IProjectRepository projectRepository, IEntityDtoMapper<ProjectEntity, MendelProject> entityDtoMapper) {
        this.projectRepository = projectRepository;
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
    public int deleteUsers(List<Long> projectIds) throws MendelRuntimeException {
        List<ProjectEntity> deletedRecords = projectRepository.deleteByIdIn(projectIds);
        return deletedRecords.size();
    }

    @Override
    public int assignUsersToProject(MendelProject project, List<Long> userIds) throws MendelRuntimeException {
        // TODO Implement
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
