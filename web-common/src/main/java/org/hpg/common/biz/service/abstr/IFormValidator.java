/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.abstr;

import java.util.List;

/**
 * Form validator
 *
 * @author trungpt
 */
public interface IFormValidator {

    /**
     * Validate the given form instance
     *
     * @param <T>
     * @param form
     * @return List of validation error messages. Empty in the case of no error
     */
    public <T> List<String> validate(T form);
}
