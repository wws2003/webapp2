/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.usermgt.scrnservice;

import org.hpg.admin.biz.web.usermgt.form.UserAddUpdateForm;
import org.hpg.admin.biz.web.usermgt.form.UserDeleteForm;
import org.hpg.admin.biz.web.usermgt.form.UserDetailForm;
import org.hpg.admin.biz.web.usermgt.form.UserForceLogoutForm;
import org.hpg.admin.biz.web.usermgt.form.UsersIndexForm;
import org.hpg.common.constant.MendelRole;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Service dedicated to the user management screen
 *
 * @author wws2003
 */
public interface IUserMgtScrnService {

    /**
     * Index
     *
     * @param form
     * @return
     * @throws MendelRuntimeException When retrieval failed
     */
    public AjaxResult indexUsers(UsersIndexForm form) throws MendelRuntimeException;

    /**
     * Get detail info
     *
     * @param form
     * @return
     * @throws MendelRuntimeException When retrieval failed
     */
    public AjaxResult getUserDetailInfo(UserDetailForm form) throws MendelRuntimeException;

    /**
     * Get all user privileges
     *
     * @param role
     * @return @throws MendelRuntimeException When retrieval failed
     */
    public AjaxResult getAllPrivileges(MendelRole role) throws MendelRuntimeException;

    /**
     * Add/update
     *
     * @param form
     * @return
     * @throws MendelRuntimeException When add/update failed
     */
    public AjaxResult addUpdateUser(UserAddUpdateForm form) throws MendelRuntimeException;

    /**
     * Delete
     *
     * @param form
     * @return
     * @throws MendelRuntimeException When delete failed
     */
    public AjaxResult deleteUsers(UserDeleteForm form) throws MendelRuntimeException;

    /**
     * Start forcing logout. Just return OK status if staring logout has been
     * done properly
     *
     * @param form
     * @return
     * @throws MendelRuntimeException
     */
    public AjaxResult forceLogoutUsers(UserForceLogoutForm form) throws MendelRuntimeException;

}
