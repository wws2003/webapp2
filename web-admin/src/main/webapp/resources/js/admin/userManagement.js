/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Based urls
 * @type Map
 */
let Urls = {
    BASE_URL: '/userMgt'
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
        // TODO Implement: Ajax call then reload/show error
        let saveForm = {};
        let saveUrl = Urls.BASE_URL + '/addUpdate';
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


