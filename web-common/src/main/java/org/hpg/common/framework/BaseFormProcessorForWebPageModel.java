/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.framework;

import org.hpg.common.model.dto.web.WebPageModel;

/**
 * Form processor for MVC Web page result
 *
 * @author wws2003
 * @param <FormType>
 */
public class BaseFormProcessorForWebPageModel<FormType> extends BaseFormProcessor<FormType, WebPageModel> {

    private BaseFormProcessorForWebPageModel(FormType form) {
        super(form);
    }

    /**
     * Instance creator
     *
     * @param <FT>
     * @param form
     * @return
     */
    public static <FT> BaseFormProcessorForWebPageModel<FT> instanceForWebPageModel(FT form) {
        return new BaseFormProcessorForWebPageModel(form);
    }
}
