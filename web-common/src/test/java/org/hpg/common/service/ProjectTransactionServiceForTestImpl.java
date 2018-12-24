/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.service;

import java.util.function.Function;
import org.hpg.common.model.entity.ProjectEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wws2003
 */
@Transactional(propagation = Propagation.REQUIRED)
public class ProjectTransactionServiceForTestImpl implements IProjectTransactionServiceForTest {

    @Override
    public ProjectEntity executeSave(Function<ProjectEntity, ProjectEntity> saveFunc, ProjectEntity projectEntity) {
        return saveFunc.apply(projectEntity);
    }
}
