/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.impl;

import java.util.Arrays;
import java.util.List;
import org.hpg.common.biz.service.abstr.IPrivilegeService;
import org.hpg.common.constant.MendelPrivilege;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Privilege service implementation
 *
 * @author trungpt
 */
public class SamplePrivilegeServiceImpl implements IPrivilegeService {

    @Override
    public List<MendelPrivilege> getUserGrantedPrivileges(long userId) throws MendelRuntimeException {
        // TODO Implement properly
        return Arrays.asList(MendelPrivilege.PRIV_LIST_DOCUMENT, MendelPrivilege.PRIV_MANAGE_USER);
    }

    @Override
    public boolean hasPrivilege(long userId, MendelPrivilege privilege) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int grantUserPrivileges(long userId, List<MendelPrivilege> privileges) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int revokePrivileges(long userId, List<MendelPrivilege> privileges) throws MendelRuntimeException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
