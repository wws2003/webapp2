/*
 * The file contents common interactions with server side
 */

/*-----------------------------AJAX executor------------------------------------*/

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
