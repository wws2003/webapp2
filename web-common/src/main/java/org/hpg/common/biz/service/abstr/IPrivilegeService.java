/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.abstr;

import java.util.List;
import org.hpg.common.constant.MendelPrivilege;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Service to manage user-privilege
 *
 * @author trungpt
 */
public interface IPrivilegeService {

    // TODO Add more methods
    /**
     * Find privileges assigned to the given role
     *
     * @param role
     * @return List of privileges assigned to the given role
     * @throws MendelRuntimeException When find operation failed
     */
    List<MendelPrivilege> findPrivilegesForRole(MendelRole role) throws MendelRuntimeException;
}
