/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.dao.mapper.impl;

import org.hpg.common.WebCommonTestBase;
import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.biz.service.impl.SimpleLoggerImpl;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.constant.MendelTransactionalLevel;
import org.hpg.common.framework.BaseFormProcessor;
import org.hpg.common.model.dto.user.MendelUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test class for user service
 *
 * @author trungpt
 */
@RunWith(SpringRunner.class)
public class UserServiceTest extends WebCommonTestBase {

    @Autowired
    private IUserService userService;

    @Test
    public void testSave1() {
        MendelUser dto = new MendelUser();
        dto.setId(0L);
        dto.setDispName("disname");
        dto.setName("name");
        dto.setPassword("pass");
        dto.setEncodedPassword("pass"); //Must be the same as password for now
        dto.setRole(MendelRole.USER);

        MendelUser createdRet = userService.createUser(dto);
        assert (createdRet.getId() > 0);
    }

    @Test
    public void testSaveWithTransaction() {
        MendelUser dto = new MendelUser();
        dto.setId(0L);
        dto.setDispName("disname");
        dto.setName("name");
        dto.setPassword("pass1");
        dto.setEncodedPassword("pass1"); //Must be the same as password for now
        dto.setRole(MendelRole.USER);
        // Executor
        BaseFormProcessor<MendelUser, MendelUser> processor = BaseFormProcessor.<MendelUser, MendelUser>instance(dto);
        MendelUser createdRet = processor.logger(new SimpleLoggerImpl(12))
                .transactional(MendelTransactionalLevel.DEFAULT)
                .formProcessor(usr -> userService.createUser(usr))
                .execute();
        assert (createdRet.getId() > 0);
    }
}
