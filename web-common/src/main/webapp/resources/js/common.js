/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    // TODO Implement
    MendelApp.initialize();
});

let MendelApp = {

    /**
     * Base URLs for all actions in the app
     * @type String
     */
    BASE_URL: location.protocol + '//' + location.hostname + ':' + location.port,

    /**
     * Initial setup
     * @returns {undefined}
     */
    initialize: function () {
        // CSRF action for AJAX POST. TODO Think of moving to ajax stuffs
        $(document).ajaxSend(function (e, xhr, options) {
            // Get token from meta tag (called before ajax action)
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token);
        });
    }
};

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

/**
 * Function against one JQuery object to disable itself
 */
$.fn.eleDisable = function () {
    $.each(this, function (i, val) {
        $(val).attr("disabled", "disabled");
        $(val).addClass("md_mo_cs_disabled");
    });
};
/**
 *Function against one JQuery object to enable itself
 */
$.fn.eleEnable = function () {
    $.each(this, function (i, val) {
        $(val).removeAttr("disabled");
        $(val).removeClass("md_mo_cs_disabled");
    });
};
