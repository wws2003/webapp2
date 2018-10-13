/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global Tagger */

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
     * Application name. Should be mendel-all
     * @returns {undefined}
     */
    APP_NAME: location.pathname.split('/')[1],

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
        window.location = MendelCommon.createGetUrlFromParam(url, paramMap);
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

    /**
     * Enable element
     * @param {JQuery} ele
     * @returns {undefined}
     */
    enableEle: function (ele) {
        ele.eleEnable();
    },

    /**
     * Disable element
     * @param {JQuery} ele
     * @returns {undefined}
     */
    disableEle: function (ele) {
        ele.eleDisable();
    },

    /**
     * Enable/Disable element
     * @param {JQuery} ele
     * @param {Boolean} enableFlg
     * @returns {undefined}
     */
    setEleEnable: function (ele, enableFlg) {
        if (enableFlg) {
            ele.eleEnable();
        } else {
            ele.eleDisable();
        }
    },

    /*--------------------------------Private methods--------------------------------*/

    /**
     * Create GET URL with params
     * @param {String} rootUrl
     * @param {Map} paramMap
     * @returns {String}
     */
    createGetUrlFromParam: function (rootUrl, paramMap) {
        let prms = paramMap ? paramMap : [];
        let paramPart = $
                .map(prms, function (k, v) {
                    return k + '=' + v;
                })
                .join('&');
        return rootUrl + (paramPart ? ('?' + paramPart) : '');
    }
};

let MendelDialog = {
    /**
     * Show info dialog with callback called after user closes the dialog
     * @param {String} title
     * @param {String} infoMessage
     * @param {Function} callback
     * @returns {undefined}
     */
    info: function (title, infoMessage, callback) {
        return this.simpleDialog(1, title, infoMessage, callback);
    },

    /**
     * Show error dialog with callback called after user closes the dialog
     * @param {String} title
     * @param {String} errorMessage
     * @param {Function} callback
     * @returns {undefined}
     */
    error: function (title, errorMessage, callback) {
        return this.simpleDialog(2, title, errorMessage, callback);
    },

    /**
     * Show confirm dialog
     * @param {String} title
     * @param {String} confirmMessage
     * @param {Function} yesCallback
     * @param {Function} noCallback
     * @param {Function} cancelCallback (optional, if none specified then do nothing)
     * @returns {undefined}
     */
    confirm: function (title, confirmMessage, yesCallback, noCallback, cancelCallback) {
        let modalContent = '';
        return this.appendTemp(modalContent);
    },

    /*-----------------------------------------Private methods-------------------------------------------*/

    /**
     * Show simple dialog with callback called after user closes the dialog
     * @param {String} type 1: Info, 2: Error TODO Add more types
     * @param {String} title
     * @param {String} message
     * @param {Function} callback
     * @returns {undefined}
     */
    simpleDialog: function (type, title, message, callback) {
        let isInfoDlg = (type === 1);
        let isErrorDlg = (type === 2);

        let modalContent = Tagger.div().withClasses('modal fade').withAttr('role', 'dialog')
                .innerTag('div').withClass('modal-dialog')
                .innerTag('div').withClass('modal-content')
                .innerTag('div').withClass('modal-header')
                .innerTag('label').withClasses('modal-title mo_modal_title').innerText(title)
                .then()
                .innerTag('button').innerText('&times;').withClass('close').withAttr('data-dismiss', 'modal')
                .then()
                .then()
                .innerTag('div').withClass('modal-body')
                .innerTag('div').innerText(message)
                .innerTag('span').withClassesIf(() => isInfoDlg, 'glyphicon glyphicon-ok-sign mo_success_icon').withClassesIf(() => isErrorDlg, 'glyphicon glyphicon-remove-sign mo_error_icon')
                .then()
                .then()
                .then()
                .innerTag('div').withClass('modal-footer')
                .innerTag('button').innerText('OK').withClass('btn btn-default').withAttr('data-dismiss', 'modal')
                .then()
                .then()
                .then()
                .then()
                .build();
        return this.appendTemp(modalContent, callback);
    },

    /**
     * Temporary append the modal element and remove it after hide
     * @param {$} modalContent
     * @param {Function} hideCallback
     * @returns {undefined}
     */
    appendTemp: function (modalContent, hideCallback) {
        let modalEle = $($.parseHTML(modalContent));
        // Append to body
        $('body').append(modalEle.css("z-index", "3000"));
        // Remove after hide
        modalEle.on('hidden.bs.modal', function (event) {
            modalEle.remove();
            if (hideCallback) {
                hideCallback();
            }
        });
        // Temporary show
        modalEle.modal('show');
        // Return the modal itself
        return modalEle;
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
