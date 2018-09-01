/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.service;

import java.util.List;
import java.util.function.Function;
import org.hpg.common.model.dto.user.MendelUser;
import org.hpg.common.model.entity.UserEntity;

/**
 *
 * @author wws2003
 */
public interface ITransactionServiceForTest {

    public void saveThenException(MendelUser dto);

    public void saveThenException(UserEntity entity);

    public void executeSave(Function<UserEntity, UserEntity> saveFunc, UserEntity userEntity);

    public List<UserEntity> executeDelete(Function<List<Long>, List<UserEntity>> deleteFunc, List<Long> idListToDelete);
}
