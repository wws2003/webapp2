/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.repository;

import java.util.Random;
import org.hpg.common.WebCommonTestBase;
import org.hpg.common.constant.MendelDocumentType;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.model.entity.DocumentEntity;
import org.hpg.common.model.entity.ProjectEntity;
import org.hpg.common.model.entity.RoleEntity;
import org.hpg.common.model.entity.UserEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test for document repository
 *
 * @author trungpt
 */
@RunWith(SpringRunner.class)
public class IDocumentRespositoryTest extends WebCommonTestBase {

    @Autowired
    private IProjectRepository projectRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IDocumentRespository documentRepository;

    public IDocumentRespositoryTest() {
    }

    /**
     * Use @Rollback to rollback the transaction. But looks like there are some
     * problem (can not detect invalid entity ?)
     */
    @Test
    //@Transactional(propagation = Propagation.REQUIRED)
    //@Rollback
    public void testSave() {
        ProjectEntity sampleProjectEntity = createSampleProjectEntity();
        UserEntity sampleUserEntity = getSampleUserEntity();

        // Firstly need to save project and user
        ProjectEntity savedProject = projectRepository.save(sampleProjectEntity);
        assert (savedProject.getId() > 0);
        UserEntity savedUser = userRepository.save(sampleUserEntity);
        assert (savedUser.getId() > 0);

        // Setback ID to make sure detached entity work
        sampleProjectEntity.setId(savedProject.getId());
        sampleUserEntity.setId(savedUser.getId());

        // Save document
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setDescription("Desc");
        documentEntity.setName("Desc");
        documentEntity.setType(MendelDocumentType.TEXT_ORIGIN.getCode());
        documentEntity.setAuthor(sampleUserEntity);
        documentEntity.setProject(sampleProjectEntity);

        DocumentEntity savedDocumentEntity = documentRepository.save(documentEntity);
        assert (savedDocumentEntity.getId() > 0);

        // Actually these assertion would be false since nothing update except for ID
        Assert.assertNotNull(savedDocumentEntity.getcDate());
        Assert.assertNotNull(savedDocumentEntity.getmDate());
        // But what about the result of find ?
        DocumentEntity reFindDocumentEntity = documentRepository.findById(savedDocumentEntity.getId()).get();
        Assert.assertNotNull(reFindDocumentEntity);
//        Assert.assertNotNull(reFindDocumentEntity.getcDate());
//        Assert.assertNotNull(reFindDocumentEntity.getmDate());
    }

    private ProjectEntity createSampleProjectEntity() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setCode("XCD");
        projectEntity.setDescription("Desc");
        projectEntity.setDisplayedName("DispName");
        projectEntity.setReferScope((short) 1);
        projectEntity.setStatus((short) 1);
        return projectEntity;
    }

    private UserEntity getSampleUserEntity() {
        Random rand = new Random(13L);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(null);
        userEntity.setName("VCCC" + rand.nextInt());
        userEntity.setDisplayedName("DispName1");
        userEntity.setEncodedPassword("nononono");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(MendelRole.USER.getCode());
        roleEntity.setName(MendelRole.USER.getName());
        userEntity.setRole(roleEntity);

        return userEntity;
    }
}
