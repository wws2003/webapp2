/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.biz.service.abstr;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.hpg.common.constant.MendelTransactionalLevel;
import org.hpg.common.model.dto.web.AjaxResult;
import org.hpg.common.model.dto.web.WebPageModel;

/**
 * Interface for services bound to controller (i.e. WEB layer)
 *
 * @author wws2003
 */
public interface IScreenService {

    /**
     * Create AJAX result for submitted form synchronously
     *
     * @param <FormType>
     * @param func
     * @param transactionLevel
     * @param form
     * @param infoMessageFunc
     * @return
     */
    public <FormType> AjaxResult executeSyncForAjaxResult(FormType form,
            MendelTransactionalLevel transactionLevel,
            Function<FormType, AjaxResult> func,
            BiFunction<FormType, AjaxResult, String> infoMessageFunc);

    /**
     * Create WebPageModel result for submitted form synchronously
     *
     * @param <FormType>
     * @param func
     * @param transactionLevel
     * @param form
     * @param infoMessageFunc
     * @return
     */
    public <FormType> WebPageModel executeSyncForWebPageModel(FormType form,
            MendelTransactionalLevel transactionLevel,
            Function<FormType, WebPageModel> func,
            BiFunction<FormType, WebPageModel, String> infoMessageFunc);

    // TODO Add methods
}
