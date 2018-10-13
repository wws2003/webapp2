/*
 * The file contents common interactions with server side
 */
/*-----------------------------Const------------------------------------*/
let MendelAjaxCommonErrorCode = {
    SESSION_AUTH_FAILURE: '[MDL0099]'
};

/*-----------------------------AJAX executor------------------------------------*/

/* global MendelDialog, MendelCommon, MendelApp */

/**
 * Default (empty) constructor
 * @returns {MendelAjaxExecutor}
 */
function MendelAjaxExecutor() {
    this._url = '';
    this._postMethod = false;
    this._async = true;
    this._postForm = {};
}

/**
 * Set URL
 * @param {String} url
 * @returns {MendelAjaxExecutor.prototype}
 */
MendelAjaxExecutor.prototype.url = function (url) {
    this._url = url;
    return this;
};

/**
 * Set submit form (via POST)
 * @param {Map} form
 * @returns {MendelAjaxExecutor.prototype}
 */
MendelAjaxExecutor.prototype.formData = function (form) {
    this._form = form;
    this._postMethod = true;
    return this;
};

/**
 * Set async flag
 * @param {Boolean} async
 * @returns {undefined}
 */
MendelAjaxExecutor.prototype.async = function (async) {
    this._async = async;
    return this;
};

/**
 * Execute server call
 * @returns {undefined}
 */
MendelAjaxExecutor.prototype.getPromise = function () {
    // Currently only support GET and POST
    let ajaxObj = {
        url: this._url,
        type: this._postMethod ? 'POST' : 'GET',
        dataType: 'json',
        contentType: 'application/json',
        connection: 'Keep-Alive'
    };
    if (this._postMethod) {
        ajaxObj.data = JSON.stringify(this._form);
    }

    return $.ajax(ajaxObj);
};

/*-----------------------------AJAX response observer builder------------------------------------*/

/**
 * Construct MendelAjaxResponseObserverBuilder with no error filter
 * @returns {MendelAjaxResponseObserverBuilder}
 */
function MendelAjaxResponseObserverBuilder() {
    this._errorFilters = [];
}

/**
 * Add new error filter for ajax result
 * @param {AjaxResponseFilter} errorFilter
 * @returns {MendelAjaxResponseObserverBuilder}
 */
MendelAjaxResponseObserverBuilder.prototype.addErrorFilter = function (errorFilter) {
    this._errorFilters.push(errorFilter);
    return this;
};

/**
 * Create observer for ajax response, considering internal error filters
 * @param {Observer} defaultObserver Final observer to be applied after all internal error filter passed through
 * @returns {undefined}
 */
MendelAjaxResponseObserverBuilder.prototype.createAjaxResponseObserver = function (defaultObserver) {
    let nextFunc = defaultObserver.next;
    let errorFunc = defaultObserver.error;
    let completeFunc = defaultObserver.complete;
    let errorFilters = this._errorFilters;
    return {
        next: (response) => {
            if (response.success) {
                nextFunc(response);
                return true;
            }
            for (var i = 0; i < errorFilters.length; i++) {
                if (errorFilters[i].doFilter(response)) {
                    // Return immediately if one filter triggered
                    return false;
                }
            }
            nextFunc(response);
            return true;
        },
        error: errorFunc,
        complete: completeFunc
    };
};

/**
 * Default builder: Only check session failure error
 * @type MendelAjaxResponseObserverBuilder
 */
let MendelDefaultAjaxResponseObserverBuilder = new MendelAjaxResponseObserverBuilder()
        .addErrorFilter(new AjaxResponseSessionErrorFilter());

/*-----------------------------AJAX response error filter------------------------------------*/

function AjaxResponseSessionErrorFilter() {
}

AjaxResponseSessionErrorFilter.prototype.doFilter = function (ajaxResponse) {
    if (ajaxResponse.fatalError &&
            ajaxResponse.errorMessages.length > 0 &&
            ajaxResponse.errorMessages[0].substring(0, 9) === MendelAjaxCommonErrorCode.SESSION_AUTH_FAILURE) {

        let errorMsg = ajaxResponse.errorMessages[0];
        let redirectUrl = MendelApp.BASE_URL + '/' + MendelApp.APP_NAME + ajaxResponse.resultObject;
        MendelDialog.error('Fatal error', errorMsg, MendelCommon.goToUrl.bind(null, redirectUrl));
        return true;
    }
    return false;
};