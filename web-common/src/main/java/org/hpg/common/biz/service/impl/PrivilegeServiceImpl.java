/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.hpg.common.biz.service.abstr.IPrivilegeService;
import org.hpg.common.constant.MendelPrivilege;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Privilege service implementation
 *
 * @author trungpt
 */
public class PrivilegeServiceImpl implements IPrivilegeService {

    private final Map<Integer, List<MendelPrivilege>> rolePrivilegesMap = new HashMap();

    public PrivilegeServiceImpl() {
        // Usable privileges for each role
        rolePrivilegesMap.put(MendelRole.ADMIN.getCode(), Arrays.asList(MendelPrivilege.PRIV_MANAGE_USER, MendelPrivilege.PRIV_MANAGE_SYSTEM));
        rolePrivilegesMap.put(MendelRole.USER.getCode(), Arrays.asList(MendelPrivilege.PRIV_CREATE_DOCUMENT,
                MendelPrivilege.PRIV_SHARE_DOCUMENT,
                MendelPrivilege.PRIV_UPDATE_DOCUMENT,
                MendelPrivilege.PRIV_LIST_DOCUMENT,
                MendelPrivilege.PRIV_SEARCH_DOCUMENT));
    }

    @Override
    public List<MendelPrivilege> findPrivilegesForRole(MendelRole role) throws MendelRuntimeException {
        return Optional.ofNullable(rolePrivilegesMap.get(role.getCode())).orElse(new ArrayList());
    }

}
