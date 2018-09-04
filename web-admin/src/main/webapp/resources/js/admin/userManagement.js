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
    USER_MGT_ADDUPDATE_ACTION: 'addUpdate',
    USER_MGT_GETDETAIL_ACTION: 'detail',
    USER_MGT_DELETE_ACTION: 'delete'
};

/**
 * Instance to handler UI stuffs (act basically as a view)
 * @type type
 */
var UserMgtView = {
    init: function () {
        // TODO Implement
    },

    renderUsers: function (users) {
        // TODO Implement
    },

    renderErrors: function (errorMessages) {
        // TODO Implement
    },

    userTblHeadGenFunc: function (userPage) {
        return $('<tr><th>Name</th><th>Display name</th><th>Role</th><th>Login status</th></tr>');
    },

    userTblRowGenFunc: function (userRecord) {
        // TODO Implement properly
        return $('<tr><td>XXXX</td><td>XXXXXX</td><td>YYYYYYY</td><td>111111</td></tr>');
    }
};

/**
 * Instance to handle UI stuffs for user detail modal
 * @type type
 */
var UserDetailDlg = {
    init: function () {
        // TODO Implement
    },

    renderUserDetails: function (userDetails) {
        // TODO Implement
    }
};

/**
 * Instance to handle user operations (act basically as a controller)
 * @type
 */
var UserMgtController = {
    init: function () {
        // TODO Implement
        this._isAdd = true;
        this._currentUserSelectedId = 0;
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

    showDetailDlg: function (isAdding) {
        // TODO Implement
        let detailForm = {
            userId: isAdding ? 0 : this._currentUserSelectedId
        };
        let detailUrl = Urls.USER_MGT_BASE_URL + '/' + Urls.USER_MGT_GETDETAIL_ACTION;
        let promise = this.getMendelAjaxExecutor()
                .url(detailUrl)
                .formData(detailForm)
                .getPromise();
        let observable = Rx.Observable.fromPromise(promise);
        // Subscribe. TODO Implement better for failed event
        observable.subscribe(response => console.log(response));
    },

    getUserDetails: function () {
        // Get user details
        let getDetailUrl = Urls.USER_MGT_BASE_URL + '/' + Urls.USER_MGT_GETDETAIL_ACTION;
        let detailForm = {
            userId: this._currentUserSelectedId
        };
        let promise = this.getMendelAjaxExecutor()
                .url(getDetailUrl)
                .formData(detailForm)
                .getPromise();
        let observable = Rx.Observable.fromPromise(promise);
        // Subscribe. TODO Implement better for failed event
        observable.subscribe(response => console.log(response));
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

// Entry point
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
    // Rewritten in Rx-style
    Rx.Observable.fromEvent($('#btnUserAddUpdateDone'), 'click')
            .subscribe(UserMgtController.saveUser);

    // To show modal
    // From add button
    Rx.Observable.fromEvent($('#btnAddUser'), 'click')
            .map(e => true)
            .merge(Rx.Observable
                    .fromEvent($('.btnUpdate'), 'click')
                    .map(e => false))
            .subscribe(UserMgtController.showDetailDlg);

    // After modal shown
    Rx.Observable.fromEvent($('#mdlUserAddUpdate'), 'shown.bs.modal')
            .subscribe(UserMgtController.getUserDetails);

    //$('#btnUserAddUpdateDone').click(UserMgtController.saveUser);
}

/**
 * Load initial data
 * @returns {undefined}
 */
function loadInitialData() {
    UserMgtController.indexUsers();
}


