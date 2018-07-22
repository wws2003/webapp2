/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.abstr;

import org.hpg.common.model.dto.principal.LoginInfo;

/**
 * Session storage: To provide access to current login user info and related
 * info
 *
 * @author wws2003
 */
public interface IUserSession {

    /**
     * Get current login user info. Never return null
     *
     * @return
     */
    public LoginInfo getCurrentLoginInfo();

    // TODO Add more methods
}
