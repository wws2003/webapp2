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
        let row = [];
        row.push('<tr>',
                '<th>Name</th>',
                '<th>Display name</th>',
                '<th>Role</th>',
                '<th>Login status</th>',
                '<th>Force logout</th>', // Force logout
                '<th>Delete</th>', // Delete
                '<th></th>', // Update
                '</tr>');
        return row.join('');
    },

    userTblRowGenFunc: function (userRecord) {
        let row = [];
        row.push('<tr>',
                '<td>' + userRecord.name + '</td>',
                '<td>' + userRecord.displayedName + '</td>',
                '<td>' + userRecord.role + '</td>',
                '<td>' + userRecord.loggingIn + '</td>',
                '<td><input type="checkbox"></td>',
                '<td><input type="checkbox"></td>',
                '<td><button>Update</button></td>',
                '</tr>');
        return row.join('');
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
        // TODO Implement properly
        this._isAdd = true;
        this._currentUserSelectedId = 0;
        this._pageRender = (new CommonPagingFragmentRender())
                .headerGenFunc(UserMgtView.userTblHeadGenFunc)
                .rowGenFunc(UserMgtView.userTblRowGenFunc);
    },

    indexUsers: function () {
        // TODO Implement. First try to load first page of users
        let indexForm = {
            pageNumber: 1,
            recordCountPerPage: 50
        };
        // Create
        let indexUrl = Urls.USER_MGT_BASE_URL + '/' + Urls.USER_MGT_INDEX_ACTION;
        let promise = this.getPostActionPromise(indexUrl, indexForm);
        let observable = Rx.Observable.fromPromise(promise);
        // Subscribe. TODO Implement better for failed event and standard for response
        observable.subscribe(response => this._pageRender.render($('#frgPaging'), response.resultObject));
    },

    showDetailDlg: function (isAdding) {
        // TODO Implement
        let detailForm = {
            userId: isAdding ? 0 : this._currentUserSelectedId
        };
        let detailUrl = Urls.USER_MGT_BASE_URL + '/' + Urls.USER_MGT_GETDETAIL_ACTION;
        let promise = this.getPostActionPromise(detailUrl, detailForm);
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
        let promise = this.getPostActionPromise(getDetailUrl, detailForm);
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
        let promise = this.getPostActionPromise(saveUrl, saveForm);
        let observable = Rx.Observable.fromPromise(promise);
        // Subscribe. TODO Implement better for failed event
        observable.subscribe(response => console.log(response));
    },

    deleteUser: function () {
        // TODO Implement
    },

    /*---Private methods---*/
    /**
     * Shortcut for get AJAX promise
     * @param {String} url
     * @param {Map} form
     * @returns {undefined}
     */
    getPostActionPromise: function (url, form) {
        return (new MendelAjaxExecutor())
                .url(url)
                .formData(form)
                .getPromise();
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
    UserMgtController.init();
}

/**
 * Setup interactions
 * @returns {undefined}
 */
function setupEvents() {
    // TODO Implement properly
    // Observer define
    let loadFunc = UserMgtController.indexUsers.bind(UserMgtController);
    let saveFunc = UserMgtController.saveUser.bind(UserMgtController);
    let showDetailDlgFunc = UserMgtController.showDetailDlg.bind(UserMgtController);
    let getUserDetails = UserMgtController.getUserDetails.bind(UserMgtController);

    // Reload
    Rx.Observable.fromEvent($('#btnReload'), 'click')
            .subscribe(loadFunc);

    // Add/Update
    Rx.Observable.fromEvent($('#btnUserAddUpdateDone'), 'click')
            .subscribe(saveFunc);

    // To show modal
    // From add button
    Rx.Observable.fromEvent($('#btnAddUser'), 'click')
            .map(e => true)
            .merge(Rx.Observable
                    .fromEvent($('.btnUpdate'), 'click')
                    .map(e => false))
            .subscribe(showDetailDlgFunc);

    // After modal shown
    Rx.Observable.fromEvent($('#mdlUserAddUpdate'), 'shown.bs.modal')
            .subscribe(getUserDetails);
}

/**
 * Load initial data
 * @returns {undefined}
 */
function loadInitialData() {
    UserMgtController.indexUsers();
}


