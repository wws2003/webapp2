/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.mapper.impl;

import org.hpg.common.WebCommonTestBase;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.dao.repository.IUserRepository;
import org.hpg.common.model.entity.RoleEntity;
import org.hpg.common.model.entity.UserEntity;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.common.service.TransactionServiceForTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test class for user dao
 *
 * @author trungpt
 */
@RunWith(SpringRunner.class)
public class UserDaoTest extends WebCommonTestBase {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private TransactionServiceForTest srv;

    @Test
    public void testSave1() {
        UserEntity saveRet = userRepository.save(getSampleEntity());
        assert (saveRet.getId() > 0);
    }

    @Test
    public void testSaveWithException() {
        // First count
        long userCnt = userRepository.count();
        System.out.println("First count = " + userCnt);

        // Executor
        try {
            srv.saveThenException(getSampleEntity());
        } catch (MendelRuntimeException e) {
            System.out.println("Encountered an expected exception");
        }

        // Count again after rollback
        long userCnt2 = userRepository.count();
        System.out.println("Second count = " + userCnt2);

        Assert.assertTrue(userCnt == userCnt2);
    }

    private UserEntity getSampleEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(null);
        userEntity.setName("Name12414");
        userEntity.setDisplayedName("DispName1");
        userEntity.setEncodedPassword("nononono");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(MendelRole.USER.getCode());
        roleEntity.setName(MendelRole.USER.getName());
        userEntity.setRole(roleEntity);

        return userEntity;
    }
}
