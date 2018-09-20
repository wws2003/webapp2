/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.service;

import java.util.Random;
import org.hpg.common.WebCommonTestBase;
import org.hpg.common.constant.MendelPrivilege;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.dao.repository.IUserPrivRepository;
import org.hpg.common.dao.repository.IUserRepository;
import org.hpg.common.model.entity.RoleEntity;
import org.hpg.common.model.entity.UserEntity;
import org.hpg.common.model.entity.UserPrivEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author trungpt
 */
@RunWith(SpringRunner.class)
public class UserPrivServiceTest extends WebCommonTestBase {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IUserPrivRepository userPrivRepository;

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void testDefaultDelete() {
        // Just to see log
        userPrivRepository.deleteByUserId(12);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void testDeleteThenSave() {
        // Insert user first
        UserEntity userEntity = getSampleUserEntity();
        userRepository.save(userEntity);

        // Insert user-priv
        UserPrivEntity userPrivEntity = new UserPrivEntity();
        userPrivEntity.setUserId(userEntity.getId());
        userPrivEntity.setPrivilegeId(MendelPrivilege.PRIV_CREATE_DOCUMENT.getId());
        userPrivRepository.save(userPrivEntity);
        long userPrivEntityId1 = userPrivEntity.getId();
        assert (userPrivEntityId1 > 0);

        // Delete user-priv by user id
        int deleteCnt = userPrivRepository.deleteByUserId(userEntity.getId());
        assert (deleteCnt == 1);

        // Re-insert and see if it work (need to use new instance as userPrivEntity is treated as deleted)
        UserPrivEntity userPrivEntity2 = new UserPrivEntity();
        userPrivEntity2.setUserId(userEntity.getId());
        userPrivEntity2.setPrivilegeId(MendelPrivilege.PRIV_CREATE_DOCUMENT.getId());
        userPrivRepository.save(userPrivEntity2);
        long userPrivEntityId2 = userPrivEntity2.getId();

        // Assert new record saved instead of the old one
        assert (userPrivEntityId2 != userPrivEntityId1);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void testSaveThenDelete() {
        // Insert user first
        UserEntity userEntity = getSampleUserEntity();
        userRepository.save(userEntity);

        long userId = userEntity.getId();
        assert (userId > 0);

        userRepository.delete(userEntity);

        assert (!userRepository.findById(userId).isPresent());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void testDeleteThenSave2() {
        // Insert user first
        UserEntity userEntity = getSampleUserEntity();
        userRepository.save(userEntity);

        // Insert user-priv
        UserPrivEntity userPrivEntity = new UserPrivEntity();
        userPrivEntity.setUserId(userEntity.getId());
        userPrivEntity.setPrivilegeId(MendelPrivilege.PRIV_CREATE_DOCUMENT.getId());
        userPrivRepository.save(userPrivEntity);
        long userPrivEntityId1 = userPrivEntity.getId();
        assert (userPrivEntityId1 > 0);

        // Delete user-priv by user id
        //List<UserPrivEntity> deletedUserPrivs = userPrivRepository.deleteByUserId(userEntity.getId());
        //assert (deletedUserPrivs.size() == 1);
        // Directly delete the entity
        userPrivRepository.delete(userPrivEntity);

        // Re-insert and see if it work (need to use new instance as userPrivEntity is treated as deleted)
        UserPrivEntity userPrivEntity2 = new UserPrivEntity();
        userPrivEntity2.setUserId(userEntity.getId());
        userPrivEntity2.setPrivilegeId(MendelPrivilege.PRIV_CREATE_DOCUMENT.getId());
        userPrivRepository.save(userPrivEntity2);
        long userPrivEntityId2 = userPrivEntity2.getId();

        // Assert new record saved instead of the old one
        assert (userPrivEntityId2 != userPrivEntityId1);
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
