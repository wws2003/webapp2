/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* MendelApp is initialized by let and therefore can not be redeclared here */
/* global MendelApp  */

var Rx = Rx || {};

/**
 * Based urls
 * @type Map
 */
let Urls = {
    USER_MGT_BASE_URL: MendelApp.BASE_URL + location.pathname,
    USER_MGT_INDEX_ACTION: 'index',
    USER_MGT_ADDUPDATE_ACTION: 'addUpdate'
};

/**
 * Instance to handler UI stuffs (act basically as a view)
 * @type type
 */
var UserMgtView = {

};

/**
 * Instance to handle user operations (act basically as a controller)
 * @type
 */
var UserMgtController = {
    init: function () {
        // TODO Implement
    },

    indexUsers: function () {
        // TODO Implement. First try to load first page of users
        let indexForm = {
            pageNumber: 1,
            recordCountPerPage: 50
        };
        // Create
        let indexUrl = Urls.USER_MGT_BASE_URL + '/' + Urls.USER_MGT_INDEX_ACTION;
        let promise = this.getMendelAjaxExecutor()
                .url(indexUrl)
                .formData(indexForm)
                .getPromise();
        let observable = Rx.Observable.fromPromise(promise);
        // Subscribe. TODO Implement better for failed event
        observable.subscribe(response => console.log(response));
    },

    getUserDetails: function () {

    },

    saveUser: function () {
        // EXPERIMENTAL CODE. TODO MOVE TO COMMON
        // TODO Implement properly. Below is just experimental code
        let saveForm = {
            toCreateUser: true,
            userName: $('#txtUserName').val(),
            userDispName: $('#txtDispName').val(),
            rawPassword: $('#txtPassword').val(),
            confirmedRawPassword: $('#txtPasswordConfirm').val(),
            grantedPrivilegeIds: []
        };
        // Create
        let saveUrl = Urls.USER_MGT_BASE_URL + '/' + Urls.USER_MGT_ADDUPDATE_ACTION;
        let promise = this.getMendelAjaxExecutor()
                .url(saveUrl)
                .formData(saveForm)
                .getPromise();
        let observable = Rx.Observable.fromPromise(promise);
        // Subscribe. TODO Implement better for failed event
        observable.subscribe(response => console.log(response));
    },

    deleteUser: function () {
        // TODO Implement
    },

    /*---Private methods---*/
    getMendelAjaxExecutor: function () {
        return new MendelAjaxExecutor();
    }
};

$(document).ready(function () {
    // TODO Implement
    initUserMgt();
    setupEvents();
    loadInitialData();
});

/**
 * Initialize user manager instance
 * @returns {undefined}
 */
function initUserMgt() {
    // TODO Implement
}

/**
 * Setup interactions
 * @returns {undefined}
 */
function setupEvents() {
    // TODO Implement properly
    $('#btnUserAddUpdateDone').click(UserMgtController.saveUser);
}

/**
 * Load initial data
 * @returns {undefined}
 */
function loadInitialData() {
    UserMgtController.indexUsers();
}


