/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.service;

import org.hpg.common.biz.service.abstr.IUserService;
import org.hpg.common.dao.repository.IUserRepository;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.entity.UserEntity;
import org.hpg.common.model.exception.MendelRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author trungpt
 */
public class TransactionServiceForTest {

    /**
     * User service
     */
    @Autowired
    private IUserService userService;

    /**
     * User repository
     */
    @Autowired
    private IUserRepository userRepository;

    @Transactional
    public void saveThenException(MendelUser dto) {
        userService.createUser(dto);
        throw new MendelRuntimeException();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveThenException(UserEntity entity) {
        userRepository.save(entity);
        throw new MendelRuntimeException();
    }
}
