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

    @Test
    public void testSave1() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(null);
        userEntity.setName("Name1");
        userEntity.setDisplayedName("DispName1");
        userEntity.setEncodedPassword("nononono");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(MendelRole.USER.getCode());
        roleEntity.setName(MendelRole.USER.getName());
        userEntity.setRole(roleEntity);

        UserEntity saveRet = userRepository.save(userEntity);
        assert (saveRet.getId() > 0);
    }
}
