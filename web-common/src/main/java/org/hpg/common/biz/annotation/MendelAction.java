/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.annotation;

import org.hpg.common.constant.MendelPrivilege;
import org.springframework.transaction.annotation.Transactional;

/**
 * Annotation for any web action in this system
 *
 * @author wws2003
 */
@Transactional
public @interface MendelAction {

    /**
     * Required privileges to use the action
     *
     * @return
     */
    MendelPrivilege[] privileges() default {};
}
