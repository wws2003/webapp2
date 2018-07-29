/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hpg.common.constant.MendelPrivilege;
import org.springframework.transaction.annotation.Transactional;

/**
 * Annotation for any web action in this system
 *
 * @author wws2003
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD})
@Transactional
public @interface MendelAction {

    /**
     * Required all specified privileges flag
     *
     * @return TRUE: All specified required to execute the action. FALSE: One
     * specified required to execute the action
     */
    boolean requiredAllPrivileges() default true;

    /**
     * Required privileges to use the action
     *
     * @return
     */
    MendelPrivilege[] privileges() default {};
}
