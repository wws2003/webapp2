/*
 * The file contents common interactions with server side
 */
/*-----------------------------Const------------------------------------*/
let MendelAjaxCommonErrorCode = {
    ACCESS_FORBIDDEN: '[MDL0098]',
    SESSION_AUTH_FAILURE: '[MDL0099]'
};

let MendelAjaxCommonError = {
    ACCESS_FORBIDDEN: 'Action is no longer permitted'
};

/*-----------------------------Common functions------------------------------------*/
/**
 * Provider of Web-related functions
 * @type Map
 */
let MendelWebs = {
    /**
     * Attach anti-CSRF stuffs to XMLHTTPREQUEST header
     * @returns {undefined}
     */
    enableCSRFPreventForAjax: function () {
        // CSRF action for AJAX POST
        $(document).ajaxSend(function (e, xhr, options) {
            // Get token from meta tag (called before ajax action)
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token);
        });
    },

    /**
     * Get the builder instance for AjaxResponseObserverBuilder
     * @returns {MendelAjaxResponseObserverBuilder}
     */
    getDefaultAjaxResponseObserverBuilder: function () {
        return new MendelAjaxResponseObserverBuilder()
                .addHttpErrorFilter(new AjaxAccessForbiddenResponseErrorFilter())
                .addErrorFilter(new AjaxResponseSessionErrorFilter())
                .addErrorFilter(new AjaxRedirectResponseErrorFilter());
    }
};

/*-----------------------------AJAX executor------------------------------------*/

/* global MendelDialog, MendelCommon, MendelApp */

var Rx = Rx || {};

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
 * Get promise for server POST request (not request data from server yet)
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

/**
 * Get Observable for server GET request (not request data from server yet)
 * @returns {Observable}
 */
MendelAjaxExecutor.prototype.getObservable = function () {
    Rx.Observable.fromPromise(this.getPromise());
};

/*-----------------------------AJAX observable builder------------------------------------*/

/**
 * Namespace for util functions
 * @type Map (namespace)
 */
let MendelAjaxObservableBuilder = {
    /**
     * Get CRUD observable builder
     * @param {MendelCRUDAjaxObservableBuilder} baseUrl
     * @returns {undefined}
     */
    baseCRUDUrl: function (baseUrl) {
        return new MendelCRUDAjaxObservableBuilder(baseUrl);
    },

    /**
     * Create AJAX observable for POST request
     * @param {String} url
     * @param {Map} form
     * @returns {Observable}
     */
    createPostActionObservable: function (url, form) {
        let promise = (new MendelAjaxExecutor())
                .url(url)
                .formData(form)
                .getPromise();
        return Rx.Observable.fromPromise(promise);
        // Below code does not work as .ajax call immediately executed !?
        //        return (new MendelAjaxExecutor())
        //                .url(url)
        //                .formData(form)
        //                .getObservable();
    },

    /**
     * Create AJAX observable for GET request
     * @param {String} url
     * @returns {Observable}
     */
    createGetActionObservable: function (url) {
        let promise = (new MendelAjaxExecutor())
                .url(url)
                .getPromise();
        return Rx.Observable.fromPromise(promise);

        // Below code does not work as .ajax call immediately executed !?
        //        return (new MendelAjaxExecutor())
        //                .url(url)
        //                .getObservable();
    }
};

/**
 * Initialize by base URL
 * @param {String} baseUrl
 * @returns {MendelCRUDAjaxObservableBuilder}
 */
function MendelCRUDAjaxObservableBuilder(baseUrl) {
    // Base URL
    this._baseUrl = baseUrl;
    // Index URL
    this._indexActionUrl = '';
    // Get detail URL
    this._getDetailActionUrl = '';
    // Save URL
    this._saveActionUrl = '';
    // Delete URL
    this._deleteActionUrl = '';
}

/**
 * Assign index url
 * @param {String} indexActionUrl
 * @returns {undefined}
 */
MendelCRUDAjaxObservableBuilder.prototype.indexActionUrl = function (indexActionUrl) {
    this._indexActionUrl = indexActionUrl;
    return this;
};

/**
 * Assign delete url
 * @param {String} deleteActionUrl
 * @returns {undefined}
 */
MendelCRUDAjaxObservableBuilder.prototype.deleteActionUrl = function (deleteActionUrl) {
    this._deleteActionUrl = deleteActionUrl;
    return this;
};

/**
 * Assign detail url
 * @param {String} detailActionUrl
 * @returns {undefined}
 */
MendelCRUDAjaxObservableBuilder.prototype.detailActionUrl = function (detailActionUrl) {
    this._getDetailActionUrl = detailActionUrl;
    return this;
};

/**
 * Assign save url
 * @param {String} saveActionUrl
 * @returns {undefined}
 */
MendelCRUDAjaxObservableBuilder.prototype.saveActionUrl = function (saveActionUrl) {
    this._saveActionUrl = saveActionUrl;
    return this;
};


/**
 * Get observable for the index action
 * @param {Map} indexForm
 * @param {String} indexActionUrl
 * @returns {undefined}
 */
MendelCRUDAjaxObservableBuilder.prototype.getIndexAJAXObservable = function (indexForm, indexActionUrl) {
    // Create
    let indexUrl = this._baseUrl + '/' + this.definedOrElse(indexActionUrl, this._indexActionUrl);
    return MendelAjaxObservableBuilder.createPostActionObservable(indexUrl, indexForm);
};

/**
 * Get observable for save action
 * @param {Map} saveForm
 * @param {String} saveActionUrl
 * @returns {Observable}
 */
MendelCRUDAjaxObservableBuilder.prototype.getSaveAJAXObservable = function (saveForm, saveActionUrl) {
    // Create
    let saveUrl = this._baseUrl + '/' + this.definedOrElse(saveActionUrl, this._saveActionUrl);
    return MendelAjaxObservableBuilder.createPostActionObservable(saveUrl, saveForm);
};

/**
 * Get observable for delete action
 * @param {Array} idsToDelete
 * @param {String} deleteActionUrl
 * @returns {Observable}
 */
MendelCRUDAjaxObservableBuilder.prototype.getDeleteAJAXObservable = function (idsToDelete, deleteActionUrl) {
    let deleteForm = {
        elementIds: idsToDelete
    };
    // Create
    let deleteUrl = this._baseUrl + '/' + this.definedOrElse(deleteActionUrl, this._deleteActionUrl);
    return MendelAjaxObservableBuilder.createPostActionObservable(deleteUrl, deleteForm);
};

/**
 * Get observable for the action retrieving detail
 * @param {Number} elementId
 * @param {String} getDetailActionUrl
 * @returns {unresolved}
 */
MendelCRUDAjaxObservableBuilder.prototype.getDetailsRetrieveAJAXObservable = function (elementId, getDetailActionUrl) {
    // Get user details
    let getDetailUrl = this._baseUrl + '/' + this.definedOrElse(getDetailActionUrl, this._getDetailActionUrl);
    let detailForm = {
        elementId: elementId
    };
    return MendelAjaxObservableBuilder.createPostActionObservable(getDetailUrl, detailForm);
};

/**
 * Return the value if it is not empty, or default value otherwise
 * @param {String} actionUrl
 * @param {String} defaultActionUrl
 * @returns {undefined}
 */
MendelCRUDAjaxObservableBuilder.prototype.definedOrElse = function (actionUrl, defaultActionUrl) {
    return actionUrl ? actionUrl : defaultActionUrl;
};

/*-----------------------------AJAX response observer builder------------------------------------*/

/**
 * Construct MendelAjaxResponseObserverBuilder with no error filter
 * @returns {MendelAjaxResponseObserverBuilder}
 */
function MendelAjaxResponseObserverBuilder() {
    // Error filters for HTTP 200 status (OK)
    this._errorFilters = [];
    // Error filters for HTTP error codes
    this._httpErrorFilters = [];
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
 * Add new HTTP error filter for ajax result
 * @param {AjaxResponseFilter} errorFilter
 * @returns {MendelAjaxResponseObserverBuilder}
 */
MendelAjaxResponseObserverBuilder.prototype.addHttpErrorFilter = function (errorFilter) {
    this._httpErrorFilters.push(errorFilter);
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
    let httpFilters = this._httpErrorFilters;

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
            if (nextFunc) {
                nextFunc(response);
            }
            return true;
        },
        error: (response) => {
            for (var i = 0; i < httpFilters.length; i++) {
                if (httpFilters[i].doFilter(response)) {
                    // Return immediately if one filter triggered
                    return false;
                }
            }
            if (errorFunc) {
                errorFunc(response);
            }
            return true;
        },
        complete: completeFunc
    };
};

/*-----------------------------AJAX response error filter------------------------------------*/

function AjaxResponseSessionErrorFilter() {
}

AjaxResponseSessionErrorFilter.prototype.doFilter = function (ajaxResponse) {
    if (ajaxResponse.fatalError &&
            ajaxResponse.errorMessages.length > 0 &&
            ajaxResponse.errorMessages[0].substring(0, 9) === MendelAjaxCommonErrorCode.SESSION_AUTH_FAILURE) {

        let errorMsg = ajaxResponse.errorMessages[0];
        // Do reload action
        MendelDialog.error('Fatal error', errorMsg, MendelCommon.goToUrl.bind(null, location.href));
        return true;
    }
    return false;
};

/*-----------------------------Access forbidden response error filter------------------------------------*/

function AjaxAccessForbiddenResponseErrorFilter() {
}

AjaxAccessForbiddenResponseErrorFilter.prototype.doFilter = function (ajaxResponse) {
    if (ajaxResponse.status === 403) {
        let errorMsg = MendelAjaxCommonErrorCode.ACCESS_FORBIDDEN + ' ' + MendelAjaxCommonError.ACCESS_FORBIDDEN;
        // Do reload action
        MendelDialog.error('Fatal error', errorMsg, MendelCommon.goToUrl.bind(null, location.href));
        return true;
    }
    return false;
};

/*-----------------------------Access forbidden response error filter------------------------------------*/

function AjaxRedirectResponseErrorFilter() {
}

AjaxRedirectResponseErrorFilter.prototype.doFilter = function (ajaxResponse) {
    // CURRENTLY DOES NOT WORK. A NEW REDIRECT CALL ALWAYS HAPPEN IN THE BACKGROUND
    if (ajaxResponse.status === 302) {
        let errorMsg = MendelAjaxCommonErrorCode.ACCESS_FORBIDDEN + ' ' + MendelAjaxCommonError.ACCESS_FORBIDDEN;
        // TODO Do redirect properly rather than reload
        MendelDialog.error('Fatal error', errorMsg, MendelCommon.goToUrl.bind(null, location.href));
        return true;
    }
    return false;
};