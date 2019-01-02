/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.repository;

import org.hpg.common.WebCommonTestBase;
import org.hpg.common.model.entity.ProjectEntity;
import org.hpg.common.service.IProjectTransactionServiceForTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test for project repository
 *
 * @author wws2003
 */
@RunWith(SpringRunner.class)
public class IProjectRepositoryTest extends WebCommonTestBase {

    @Autowired
    private IProjectRepository projectRepository;

    @Autowired
    private IProjectUserRepository projectUserRepository;

    @Autowired
    private IProjectTransactionServiceForTest srv;

    public IProjectRepositoryTest() {
    }

    @Test
    public void testUpdate() {
        // Currently no problem
        // Create
        ProjectEntity savedEntity = projectRepository.save(createSampleEntity());
        assert (savedEntity.getId() > 0);
        // Update
        savedEntity.setCode("Code2");
        savedEntity = projectRepository.save(savedEntity);
        assert ("Code2".equals(savedEntity.getCode()));
    }

    @Test
    public void testUpdateInTransaction() {
        // Create
        ProjectEntity originEntity = projectRepository.save(createSampleEntity());
        assert (originEntity.getId() > 0);
        // Update
        ProjectEntity entityForUpdate = createSampleEntity();
        entityForUpdate.setCode("Code2");
        entityForUpdate.setId(originEntity.getId());
        final ProjectEntity savedEntity = srv.executeSave(
                ent -> {
                    projectUserRepository.deleteByProjectId(ent.getId());
                    return projectRepository.save(ent);
                },
                entityForUpdate);
        assert ("Code2".equals(savedEntity.getCode()));
    }

    private ProjectEntity createSampleEntity() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setCode("XCD");
        projectEntity.setDescription("Desc");
        projectEntity.setDisplayedName("DispName");
        projectEntity.setReferScope((short) 1);
        projectEntity.setStatus((short) 1);
        return projectEntity;
    }
}
