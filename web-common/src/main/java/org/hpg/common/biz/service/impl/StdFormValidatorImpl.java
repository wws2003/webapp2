/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.hpg.common.biz.service.abstr.IFormValidator;

/**
 * Implementation for form validator
 *
 * @author trungpt
 */
public class StdFormValidatorImpl implements IFormValidator {

    /**
     * Use Javax standard validator
     */
    private final Validator baseValidator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public <T> List<String> validate(T form) {
        if (form == null) {
            return new ArrayList();
        }
        Set<ConstraintViolation<T>> emptyViolations = baseValidator.validate(form);

        // TODO Implement properly
        return emptyViolations.stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.toList());
    }
}
