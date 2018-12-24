/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.service;

import java.util.function.Function;
import org.hpg.common.model.entity.ProjectEntity;

/**
 *
 * @author wws2003
 */
public interface IProjectTransactionServiceForTest {

    public ProjectEntity executeSave(Function<ProjectEntity, ProjectEntity> saveFunc, ProjectEntity projectEntity);

}
