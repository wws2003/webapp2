/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.service;

import java.util.List;
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
@Transactional(propagation = Propagation.REQUIRED)
public class TransactionServiceForTest implements ITransactionServiceForTest {

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

    @Override
    public void saveThenException(MendelUser dto) {
        userService.createUser(dto);
        throw new MendelRuntimeException();
    }

    @Override
    public void saveThenException(UserEntity entity) {
        userRepository.save(entity);
        throw new MendelRuntimeException();
    }

    @Override
    public void executeSave(Function<UserEntity, UserEntity> saveFunc, UserEntity userEntity) {
        saveFunc.apply(userEntity);
    }

    @Override
    public List<UserEntity> executeDelete(Function<List<Long>, List<UserEntity>> deleteFunc, List<Long> idListToDelete) {
        return deleteFunc.apply(idListToDelete);
    }
}
