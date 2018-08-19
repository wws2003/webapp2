/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.mapper.impl;

import org.hpg.common.constant.MendelRole;
import org.hpg.common.dao.mapper.abstr.IEntityDtoMapper;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.entity.RoleEntity;
import org.hpg.common.model.entity.UserEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test for user entity-DTO mapper
 *
 * @author wws2003
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class UserEntityDtoMapperImplTest {

    @Autowired
    private IEntityDtoMapper<UserEntity, MendelUser> entityDtoMapper;

    public UserEntityDtoMapperImplTest() {
    }

    @Test
    public void testEntityDtoMapper() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(MendelRole.ADMIN.getCode());
        roleEntity.setName(MendelRole.ADMIN.getName());

        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setDisplayedName("Dispname");
        entity.setEncodedPassword("password");
        entity.setRole(roleEntity);
        entity.setName("Name");

        MendelUser dto = entityDtoMapper.getDtoFromEntity(entity);

        Assert.assertTrue(entity.getId() == dto.getId());
        Assert.assertTrue(entity.getName().equals(dto.getName()));
        Assert.assertTrue(entity.getDisplayedName().equals(dto.getDispName()));
        Assert.assertTrue(entity.getEncodedPassword().equals(dto.getPassword()));
        Assert.assertTrue(entity.getEncodedPassword().equals(dto.getEncodedPassword()));
        Assert.assertTrue(entity.getRole().getId() == (dto.getRole().getCode()));
    }

    @Test
    public void testDtoEntityMapper() {
        MendelUser dto = new MendelUser();
        dto.setId(1L);
        dto.setDispName("disname");
        dto.setName("name");
        dto.setPassword("pass");
        dto.setEncodedPassword("pass"); //Must be the same as password
        dto.setRole(MendelRole.USER);

        UserEntity entity = entityDtoMapper.getEntityFromDto(dto);

        Assert.assertTrue(entity.getId() == dto.getId());
        Assert.assertTrue(entity.getName().equals(dto.getName()));
        Assert.assertTrue(entity.getDisplayedName().equals(dto.getDispName()));
        Assert.assertTrue(entity.getEncodedPassword().equals(dto.getPassword()));
        Assert.assertTrue(entity.getEncodedPassword().equals(dto.getEncodedPassword()));
        Assert.assertTrue(entity.getRole().getId() == (dto.getRole().getCode()));
    }
}
