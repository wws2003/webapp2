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
import org.hpg.common.dao.repository.IUserRepository;
import org.hpg.common.framework.BaseFormProcessor;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.junit.Assert;
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

    @Autowired
    private IUserRepository userRepository;

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
        dto.setName("name9999");
        dto.setPassword("pass1");
        dto.setEncodedPassword("pass1"); //Must be the same as password for now
        dto.setRole(MendelRole.USER);

        // First count
        long userCnt = userRepository.count();
        System.out.println("First count = " + userCnt);

        // Executor
        try {
            BaseFormProcessor.<MendelUser, MendelUser>instance(dto)
                    .logger(new SimpleLoggerImpl(12))
                    .transactional(MendelTransactionalLevel.DEFAULT)
                    .formProcessor(usr -> {
                        MendelUser ret = userService.createUser(usr);
                        if (true) {
                            throw new MendelRuntimeException();
                        }
                        return ret;
                    })
                    .execute();
        } catch (Exception e) {
            // Do nothing
            if (e instanceof MendelRuntimeException) {
                System.out.println("Encountered an expected exception");
            }
        }

        // Count again after rollback
        long userCnt2 = userRepository.count();
        System.out.println("Second count = " + userCnt2);

        Assert.assertTrue(userCnt == userCnt2);
    }
}
