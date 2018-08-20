/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
let MendelCommon = {

    /**
     * Open url as modal
     * @param {String} url
     * @param {Map} paramMap (optional)
     * @returns {Number}
     */
    openModalFromUrl: function (url, paramMap) {
        // TODO Implement
        return 1;
    },

    /**
     * Redirect to url page
     * @param {String} url
     * @param {Map} paramMap (optional)
     * @returns {Undefined}
     */
    goToUrl: function (url, paramMap) {
        window.location = this.createGetUrlFromParam(url, paramMap);
    },

    /**
     * Conduct Ajax action
     * @param {String} url
     * @param {Map} options (optional: Map of params, method, async flag...)
     * @returns {undefined}
     */
    ajaxAction: function (url, options) {
        // TODO Implement
        return 1;
    },

    /*--------------------------------Private methods--------------------------------*/

    /**
     * Create GET URL with params
     * @param {String} rootUrl
     * @param {Map} paramMap
     * @returns {String}
     */
    createGetUrlFromParam: function (rootUrl, paramMap) {
        let paramPart = $
                .map(paramMap, function (k, v) {
                    return k + '=' + v;
                })
                .join('&');
        return rootUrl + (paramPart ? ('?' + paramPart) : '');
    }
};

function MendelAjaxExecutor() {
    // TODO Implement
}

MendelAjaxExecutor.prototype.url = function (url) {
    this.url = url;
    return this;
};

MendelAjaxExecutor.prototype.formData = function (form) {
    // TODO Implement
    return this;
};