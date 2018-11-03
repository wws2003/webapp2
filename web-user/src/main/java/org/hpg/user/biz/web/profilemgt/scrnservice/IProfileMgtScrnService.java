/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.user.biz.web.profilemgt.scrnservice;

import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Interface for service to manage profile in user role (currently only to
 * change password)
 *
 * @author wws2003
 */
public interface IProfileMgtScrnService {

    /**
     * Save password for current user
     *
     * @param password
     * @return
     * @throws MendelRuntimeException When save failed
     */
    public AjaxResult savePassword(String password) throws MendelRuntimeException;
}
