/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.abstr;

import java.util.Date;
import java.util.Optional;
import org.hpg.common.model.exception.MendelRuntimeException;

/**
 * Service to work with login users
 *
 * @author trungpt
 */
public interface ILoginUserService {
    // TODO Add methods

    /**
     * Get beginning timestamp of user session. <BR>
     * Return empty is user not logged in
     *
     * @param userId
     * @return
     * @throws MendelRuntimeException When retrieval failed
     */
    public Optional<Date> getLoginDate(long userId) throws MendelRuntimeException;
}
