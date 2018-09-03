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
import org.springframework.web.servlet.ModelAndView;

/**
 * Interface for services bound to controller (i.e. WEB layer)
 *
 * @author wws2003
 */
public interface IScreenService {

    public static final String FATAL_PAGE_VIEW = "common/pages/fatal";

    /**
     * Create AJAX result for submitted form synchronously without logging
     * (normally for read operation)
     *
     * @param <FormType>
     * @param func
     * @param transactionLevel
     * @param form
     * @return
     */
    default public <FormType> AjaxResult executeSyncForAjaxResult(FormType form,
            MendelTransactionalLevel transactionLevel,
            Function<FormType, AjaxResult> func) {
        return executeSyncForAjaxResult(form, transactionLevel, func, null);
    }

    /**
     * Create AJAX result for submitted form synchronously
     *
     * @param <FormType>
     * @param func
     * @param transactionLevel
     * @param form
     * @param infoMessageFunc Success logging message function
     * @return
     */
    public <FormType> AjaxResult executeSyncForAjaxResult(FormType form,
            MendelTransactionalLevel transactionLevel,
            Function<FormType, AjaxResult> func,
            BiFunction<FormType, AjaxResult, String> infoMessageFunc);

    /**
     * Create ModelAndView result for submitted form synchronously
     *
     * @param <FormType>
     * @param form
     * @param transactionLevel
     * @param func
     * @param errorPage
     * @param fatalPage
     * @param infoMessageFunc
     * @return
     */
    public <FormType> ModelAndView executeSyncForModelAndView(FormType form,
            MendelTransactionalLevel transactionLevel,
            Function<FormType, ModelAndView> func,
            String errorPage,
            String fatalPage,
            BiFunction<FormType, ModelAndView, String> infoMessageFunc);

    /**
     * Create ModelAndView result for submitted form synchronously
     *
     * @param <FormType>
     * @param form
     * @param transactionLevel
     * @param func
     * @param errorPage
     * @param fatalPage
     * @return
     */
    default public <FormType> ModelAndView executeSyncForModelAndView(FormType form,
            MendelTransactionalLevel transactionLevel,
            Function<FormType, ModelAndView> func,
            String errorPage,
            String fatalPage) {
        return executeSyncForModelAndView(form, transactionLevel, func, errorPage, fatalPage, null);
    }

    /**
     * Create ModelAndView result for submitted form synchronously (default
     * fatal page is used, no logging info message)
     *
     * @param <FormType>
     * @param form
     * @param transactionLevel
     * @param func
     * @param errorPage
     * @return
     */
    default public <FormType> ModelAndView executeSyncForModelAndView(FormType form,
            MendelTransactionalLevel transactionLevel,
            Function<FormType, ModelAndView> func,
            String errorPage) {
        return executeSyncForModelAndView(form, transactionLevel, func, errorPage, FATAL_PAGE_VIEW, null);
    }
}
