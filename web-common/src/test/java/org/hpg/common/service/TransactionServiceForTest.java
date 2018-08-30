/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.service;

import java.util.function.Function;
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

    public void saveThenException(UserEntity entity) {
        // The very test

        // This kind of thing does not work. Possibly newly created instance is not managed properly by Spring transaction manager
        XXXExecutor ex = new XXXExecutor(entity);
        ex.saveEntity(ent -> {
            UserEntity ent1 = userRepository.save(entity);
            if (true) {
                throw new MendelRuntimeException();
            }
            return ent1;
        });
        throw new MendelRuntimeException();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private class XXXExecutor {

        private final UserEntity entity;

        public XXXExecutor(UserEntity entity) {
            this.entity = entity;
        }

        public UserEntity saveEntity(Function<UserEntity, UserEntity> persistFunc) {
            return persistFunc.apply(entity);
        }
    }
}
