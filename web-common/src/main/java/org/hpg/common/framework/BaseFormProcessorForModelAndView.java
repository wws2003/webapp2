/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.framework;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.servlet.ModelAndView;

/**
 * Form processor for MVC Web page result
 *
 * @author wws2003
 * @param <FormType>
 */
public class BaseFormProcessorForModelAndView<FormType> extends BaseFormProcessor<FormType, ModelAndView> {

    /**
     * Page for validation errors
     */
    private String validationErrorPage = "";

    /**
     * Page for fatal errors
     */
    private String fatalPage = "";

    /**
     * Key for model map used in page for validation errors
     */
    private static final String VALIDATION_ERR_KEY = "errorMessages";

    /**
     * Key for model map used in page for fatal errors
     */
    private static final String FATAL_KEY = "fatalErrorMessage";

    /**
     * Constructor default
     *
     * @param form
     */
    public BaseFormProcessorForModelAndView(FormType form) {
        super(form);
        // Validation error result creator
        validationErrorMessagesProcessor = this::createModelAndViewForValidationError;
        // Fatal error result creator
        executeExceptionProcessor = this::createModelAndViewForFatalError;
    }

    /**
     * Instance creator
     *
     * @param <FT>
     * @param form
     * @return
     */
    public static <FT> BaseFormProcessorForModelAndView<FT> instanceForModelAndView(FT form) {
        return new BaseFormProcessorForModelAndView(form);
    }

    public BaseFormProcessorForModelAndView<FormType> validationErrorPage(String validationErrorPage) {
        this.validationErrorPage = validationErrorPage;
        return this;
    }

    public BaseFormProcessorForModelAndView<FormType> fatalPage(String fatalPage) {
        this.fatalPage = fatalPage;
        return this;
    }

    /**
     * Create ModelAndView result for validation errors
     *
     * @param errorMessages
     * @return
     */
    private ModelAndView createModelAndViewForValidationError(List<String> errorMessages) {
        // TODO Implement more properly
        Map<String, List<String>> model = new HashMap();
        model.put(VALIDATION_ERR_KEY, errorMessages);
        return new ModelAndView(validationErrorPage, model);
    }

    /**
     * Create ModelAndView result for fatal errors
     *
     * @param e
     * @return
     */
    private ModelAndView createModelAndViewForFatalError(Exception e) {
        // TODO Implement properly based on e
        Map<String, String> model = new HashMap();
        model.put(FATAL_KEY, e.getMessage());
        return new ModelAndView(fatalPage, model);
    }
}
