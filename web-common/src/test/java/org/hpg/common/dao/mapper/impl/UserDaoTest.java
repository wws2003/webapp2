/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.mapper.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.hpg.common.WebCommonTestBase;
import org.hpg.common.config.CommonQualifierConstant;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.dao.repository.IUserRepository;
import org.hpg.common.framework.transaction.ITransactionExecutor;
import org.hpg.common.model.entity.RoleEntity;
import org.hpg.common.model.entity.UserEntity;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.hpg.common.service.ITransactionServiceForTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test class for user DAO
 *
 * @author trungpt
 */
@RunWith(SpringRunner.class)
public class UserDaoTest extends WebCommonTestBase {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    @Qualifier(CommonQualifierConstant.TransactionExecutor.NEW)
    private ITransactionExecutor transactionExecutor;

    @Autowired
    private ITransactionServiceForTest srv;

    @Test
    public void testSave1() {
        UserEntity saveRet = userRepository.save(getSampleEntity());
        assert (saveRet.getId() > 0);
    }

    @Test
    public void testUpdate1() {
        UserEntity ent = getSampleEntity();
        UserEntity saveRet = userRepository.save(ent);
        assert (saveRet.getId() > 0);
        saveRet.setDisplayedName("New displayed name");
        UserEntity saveRet2 = userRepository.save(saveRet);
        assert (saveRet2.getId() > 0);
        assert (saveRet2.getDisplayedName().equals(saveRet.getDisplayedName()));
    }

    @Test
    public void testDelete() {
        List<UserEntity> entities = IntStream.range(0, 10)
                .boxed()
                .map(i -> "Namexxx" + String.valueOf(i))
                .map(this::getSampleEntity)
                .collect(Collectors.toList());

        List<UserEntity> savedEntities = entities.stream()
                .map(ent -> userRepository.save(ent))
                .collect(Collectors.toList());

        List<Long> savedIds = savedEntities.stream()
                .map(UserEntity::getId)
                .collect(Collectors.toList());

        List<UserEntity> deletedEntities = srv.executeDelete(userRepository::deleteByIdIn, savedIds);

        assert (deletedEntities.size() == entities.size());
    }

    /**
     * Test with bulk delete
     */
    @Test
    public void testDelete2() {
        List<UserEntity> entities = IntStream.range(0, 10)
                .boxed()
                .map(i -> "Namexxx" + String.valueOf(i))
                .map(this::getSampleEntity)
                .collect(Collectors.toList());

        List<UserEntity> savedEntities = entities.stream()
                .map(ent -> userRepository.save(ent))
                .collect(Collectors.toList());

        List<Long> savedIds = savedEntities.stream()
                .map(UserEntity::getId)
                .collect(Collectors.toList());

        List<UserEntity> deletedEntities = srv.executeDelete(userRepository::deleteBatchByIdIn, savedIds);

        assert (deletedEntities.size() == entities.size());

        // Currently not even executable due to some Hibernate exception org.hibernate.hql.internal.QueryExecutionRequestException: Not supported for DML operations
        // Assert if deleted records are really deleted ?
        long remainingRecordCnt = deletedEntities.stream()
                .filter(ent -> userRepository.existsById(ent.getId()))
                .count();

        assert (remainingRecordCnt == 0);
    }

    @Test
    public void testSaveWithException() {
        // First count
        long userCnt = userRepository.count();
        System.out.println("First count = " + userCnt);

        // Executor
        try {
            // Using map does not work
            // ITransactionExecutor exe = transactionExecutorMap.get(MendelTransactionalLevel.DEFAULT.getCode());

            // This does not work neither, surprisingly
            /*transactionExecutor.execute(
                    ent -> {
                        userRepository.save(ent);
                        throw new MendelRuntimeException("");
                    },
                    getSampleEntity());*/
            // This works
            // srv.saveThenException(getSampleEntity());
            // What about this. Does not. Looks like due to using default implementing method from the interface ?
            srv.executeSave(
                    ent -> {
                        userRepository.save(ent);
                        throw new MendelRuntimeException("");
                    },
                    getSampleEntity());
        } catch (MendelRuntimeException e) {
            System.out.println("Encountered an expected exception");
        }

        // Count again after rollback
        long userCnt2 = userRepository.count();
        System.out.println("Second count = " + userCnt2);

        Assert.assertTrue(userCnt == userCnt2);
    }

    private UserEntity getSampleEntity() {
        return getSampleEntity("Name12414");
    }

    private UserEntity getSampleEntity(String name) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(null);
        userEntity.setName(name);
        userEntity.setDisplayedName("DispName1");
        userEntity.setEncodedPassword("nononono");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(MendelRole.USER.getCode());
        roleEntity.setName(MendelRole.USER.getName());
        userEntity.setRole(roleEntity);

        return userEntity;
    }
}
