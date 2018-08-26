/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
Rx = Rx || {};

/**
 * Based urls
 * @type Map
 */
let Urls = {
    BASE_URL: location.protocol + '//' + location.hostname + ':' + location.port + location.pathname
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
        // TODO Implement
    },

    getUserDetails: function () {

    },

    saveUser: function () {
        // TODO Implement properly. Below is just experimental code
        let saveForm = {
            toCreateUser: true,
            userName: $('#txtUserName').val(),
            userDispName: $('#txtDispName').val(),
            rawPassword: $('#txtPassword').val(),
            confirmedRawPassword: $('#txtPatxtPasswordConfirmssword').val(),
            grantedPrivilegeIds: []
        };
        let saveUrl = Urls.BASE_URL + '/addUpdate';
        let promise = $.ajax({
            url: saveUrl,
            type: "POST",
            dataType: "json",
            contentType: "text/xml; charset=\"utf-8\"",
            connection: "Keep-Alive",
            data: JSON.stringify({userAddUpdateForm: saveForm})
        });
        // Create
        let observable = Rx.Observable.fromPromise(promise);
        // Subscribe
        observable.subscribe(response => console.log(response));
    },

    deleteUser: function () {
        // TODO Implement
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
    // TODO Implement
}


