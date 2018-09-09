/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* Values is initialized by let and therefore can not be redeclared here */
/* global MendelApp, Tagger  */

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

/*--------------------------Views--------------------------*/
/**
 * Instance to handler UI stuffs (act basically as a view)
 * @type type
 */
var UserRecordsPageFragment = {
    /**
     * Initialize view with related buttons
     * @param {JQuery} userRecordsPagingFragment
     * @param {JQuery} btnReload
     * @param {JQuery} btnAdd
     * @param {JQuery} btnDelete
     * @param {JQuery} btnForceLogout
     * @returns {undefined}
     */
    init: function (userRecordsPagingFragment, btnReload, btnAdd, btnDelete, btnForceLogout) {
        // TODO Implement properly
        // Assign view components
        this._userRecordsPagingFragment = userRecordsPagingFragment;
        this._btnReload = btnReload;
        this._btnAdd = btnAdd;
        this._btnDelete = btnDelete;
        this._btnForceLogout = btnForceLogout;

        // Create view builders
        this._pageRender = (new CommonPagingFragmentRender())
                .headerGenFunc(UserRecordsPageFragment.userTblHeadGenFunc)
                .rowGenFunc(UserRecordsPageFragment.userTblRowGenFunc);

        // Subjects (for events fired in this view)
        this._userUpdateSubject = undefined;
    },

    /**
     * Set subjects for user-interactions
     * @param {Subject} reloadSubject
     * @param {Subject} addSubject
     * @param {Subject} updateSubject
     * @param {Subject} deleteSubject
     * @param {Subject} forceLogoutSubject
     * @returns {undefined}
     */
    setSubject: function (reloadSubject, addSubject, updateSubject, deleteSubject, forceLogoutSubject) {
        // Reload
        Rx.Observable.fromEvent(this._btnReload, 'click').subscribe(reloadSubject);

        // Add
        Rx.Observable.fromEvent(this._btnAdd, 'click')
                .subscribe(addSubject);

        // Delete
        this.subscribeOneActionForCheckedUsers(Rx.Observable.fromEvent(this._btnDelete, 'click'),
                '.mo_chkDelete:checked',
                deleteSubject);

        // Force logout
        this.subscribeOneActionForCheckedUsers(Rx.Observable.fromEvent(this._btnForceLogout, 'click'),
                '.mo_chkForceLogout:checked',
                forceLogoutSubject);

        // Update (for later use)
        this._userUpdateSubject = updateSubject;
    },

    /**
     * Render page
     * @param {Map} page
     * @returns {undefined}
     */
    renderUsersPage: function (page) {
        // Render to view
        this._pageRender.render(this._userRecordsPagingFragment, page);
        // Events on the page
        // TODO Unsubsribe old events ?
        Rx.Observable.fromEvent(this._userRecordsPagingFragment.find('button.mo_btnUpdate'), 'click')
                .map(e => $(e.target).attr('id').split('_')[1])
                .map(idStr => parseInt(idStr))
                .subscribe(this._userUpdateSubject);
    },

    renderErrors: function (errorMessages) {
        // TODO Implement
    },

    /**
     * Disable interactions
     * @returns {undefined}
     */
    disable: function () {
        [this._btnReload, this._btnAdd, this._btnDelete, this._btnForceLogout].eleDisable();
    },

    /*--------------------Private methods-------------------*/
    userTblHeadGenFunc: function (userPage) {
        return Tagger.tr()
                .withTh('Name')
                .withTh('Display name')
                .withTh('Role')
                .withTh('Login status')
                .withTh('Force logout')
                .withTh('Delete')
                .withTh('Update')
                .build();
    },

    userTblRowGenFunc: function (userRecord) {
        return Tagger.tr()
                .withTd(userRecord.name)
                .withTd(userRecord.displayedName)
                .withTd(userRecord.role)
                .withTd(userRecord.loggingIn)
                .td().innerTag('input').autoClose().withAttr('type', 'checkbox').withClass('mo_chkForceLogout').id('chkForceLogout_' + userRecord.id).then()
                .then()
                .td().innerTag('input').autoClose().withAttr('type', 'checkbox').withClass('mo_chkDelete').id('chkDelete_' + userRecord.id).then()
                .then()
                .td().innerTag('button').innerText('Update').withClass('mo_btnUpdate').id('btnAddUpdate_' + userRecord.id).then()
                .then()
                .build();
    },

    /**
     * Subscribe to the subject with params as checked user ids
     * @param {Observable} actionObservable
     * @param {String} checkedSelector
     * ¥@param {Subject} subject
     * @returns {undefined}
     */
    subscribeOneActionForCheckedUsers: function (actionObservable, checkedSelector, subject) {
        let pageFragment = this._userRecordsPagingFragment;
        actionObservable.switchMap(e => Rx.Observable.from(pageFragment.find(checkedSelector).get()))
                .map(e => $(e).attr('id').split('_')[1])
                .map(idStr => parseInt(idStr))
                .scan((acc, one) => acc.concat(one), []) // Re-collect to array again. Do NOT use toArray since the stream has not been completed
                .subscribe(subject);
    }
};

/**
 * Instance to handle UI stuffs for user detail modal
 * @type type
 */
var UserDetailDlg = {
    init: function (mdlUserAddUpdate) {
        // Dialog element
        this._mdlUserAddUpdate = mdlUserAddUpdate;
        // Observers and subscriptions
        this._userInfoFormObservable = Rx.Observable.fromEvent(mdlUserAddUpdate.find('#btnUserAddUpdateDone'), 'click')
                .map(function (ev) {
                    return {
                        userName: mdlUserAddUpdate.find('#txtUserName').val(),
                        userDispName: mdlUserAddUpdate.find('#txtDispName').val(),
                        rawPassword: mdlUserAddUpdate.find('#txtPassword').val(),
                        confirmedRawPassword: mdlUserAddUpdate.find('#txtPasswordConfirm').val(),
                        grantedPrivilegeIds: []
                    };
                });
        this._userInfoFormSubscription = undefined;
        this._saveSubject = undefined;
    },

    /**
     * Set subjects for user-interactions
     * @param {Subject} saveSubject
     * @returns {undefined}
     */
    setSubject: function (saveSubject) {
        this._saveSubject = saveSubject;
    },

    /**
     * Show user detailed info for update
     * @param {Map} userDetails
     * @returns {undefined}
     */
    showForUpdate: function (userDetails) {
        // TODO Implement properly
        console.log(userDetails);
        let mdlUserAddUpdate = this._mdlUserAddUpdate;
        // Render
        mdlUserAddUpdate.find('#lblUserDialogTitle').text('Update user info');
        mdlUserAddUpdate.find('#txtUserName').val(userDetails.name);
        mdlUserAddUpdate.find('#txtDispName').val(userDetails.dispName);
        mdlUserAddUpdate.find('#txtPassword').val('somepass');
        mdlUserAddUpdate.find('#txtPasswordConfirm').val('somepass');
        // Re-construct subsription
        this.modifySubscription({
            toCreateUser: false,
            userId: userDetails.id
        });
        // Show up
        mdlUserAddUpdate.modal('show');
    },

    /**
     * Show user detailed info for add
     * @returns {undefined}
     */
    showForAdd: function () {
        let mdlUserAddUpdate = this._mdlUserAddUpdate;
        // Render
        mdlUserAddUpdate.find('#lblUserDialogTitle').text('Input new user info');
        mdlUserAddUpdate.find('#txtUserName').val('');
        mdlUserAddUpdate.find('#txtDispName').val('');
        mdlUserAddUpdate.find('#txtPassword').val('');
        mdlUserAddUpdate.find('#txtPasswordConfirm').val('');
        // Re-construct subsription
        this.modifySubscription({
            toCreateUser: true,
            userId: 0
        });
        // Show up
        mdlUserAddUpdate.modal('show');
    },

    hide: function () {
        this._mdlUserAddUpdate.modal('hide');
    },

    renderErrors: function (errorMessages) {
        // TODO Implement
    },

    /**
     * Modify subscription of button Save 's click event
     * @param {Map} extInfo
     * @returns {undefined}
     */
    modifySubscription: function (extInfo) {
        this._userInfoFormSubscription && this._userInfoFormSubscription.unsubscribe();
        this._userInfoFormSubscription = this._userInfoFormObservable
                .map((userInfoForm) => {
                    return $.extend({}, userInfoForm, extInfo);
                })
                .subscribe(this._saveSubject);
    }
};

/*--------------------------Subjects--------------------------*/
var UserActionSubjects = {

    /**
     * Initialize subjects from user interacting events
     * @param {Map} userDetailDlg
     */
    init: function (userDetailDlg) {
        // Subjects
        this._indexSubject = new Rx.Subject();
        this._getUserDetailsSubject = new Rx.Subject();
        this._addUserSubject = new Rx.Subject();
        this._updateUserSubject = new Rx.Subject();
        this._deleteUsersSubject = new Rx.Subject();
        this._forceLogoutUsersSubject = new Rx.Subject();

        // Views
        this._userDetailDlg = userDetailDlg;
    },

    /**
     * Init by providing controller instance
     * @param {Map} userMgtWebService
     * @param {Map} serverResponseSubjects
     * @returns {undefined}
     */
    setupSubjects: function (userMgtWebService, serverResponseSubjects) {
        // Subscribe subjects to actions. Subjects now act as observable
        // Index. TODO Move indexForm to view
        this._indexSubject
                .map(e => {
                    return {
                        pageNumber: 1,
                        recordCountPerPage: 20
                    };
                })
                .switchMap(indexForm => userMgtWebService.getIndexAJAXObservable(indexForm))
                .subscribe(serverResponseSubjects.getIndexResponseObserver());

        // Get details
        this._getUserDetailsSubject
                .switchMap(userId => userMgtWebService.getUserDetailsRetrieveAJAXObservable(userId))
                .subscribe(serverResponseSubjects.getRetrieveUserDetailsResponseObserver());

        // Add
        let userDetailDlg = this._userDetailDlg;
        this._addUserSubject.subscribe(() => userDetailDlg.showForAdd());

        // Update
        this._updateUserSubject
                .switchMap(saveForm => userMgtWebService.getSaveAJAXObservable(saveForm))
                .subscribe(serverResponseSubjects.getSaveUserResponseObserver());

        // Delete
        this._deleteUsersSubject
                .switchMap(userIdsToDelete => userMgtWebService.getDeleteAJAXObservable(userIdsToDelete))
                .subscribe(serverResponseSubjects.getDeleteUserResponseObserver());

        // Force logout TODO Implement
    }
};

/**
 * Subjects for server response
 * @type Map
 */
var ServerResponseSubjects = {
    /**
     * Initialize by views and one user interaction subject (for index action)
     * @param {Map} userRecordsPageFragment
     * @param {Map} userDetailDlg
     * @param {Subject} indexActionSubject
     * @returns {undefined}
     */
    init: function (userRecordsPageFragment, userDetailDlg, indexActionSubject) {
        this._userRecordsPageFragment = userRecordsPageFragment;
        this._userDetailDlg = userDetailDlg;
        this._indexActionSubject = indexActionSubject;
    },

    /**
     * Get observer for index response
     * @type Map
     */
    getIndexResponseObserver: function () {
        let successResponseFunc = UserRecordsPageFragment.renderUsersPage.bind(this._userRecordsPageFragment);
        return  {
            // TODO Handle error
            next: (response) => successResponseFunc(response.resultObject)
        };
    },

    /**
     * Subject for get details action
     * @type Map
     */
    getRetrieveUserDetailsResponseObserver: function () {
        let successResponseFunc = UserDetailDlg.showForUpdate.bind(this._userDetailDlg);
        return {
            // TODO Handle error
            next: (response) => successResponseFunc(response.resultObject)
        };
    },

    /**
     * Subject for user save action
     * @type Map
     */
    getSaveUserResponseObserver: function () {
        // TODO Implement properly, handle messages and error
        let indexSubject = this._indexActionSubject;
        let userDetailDlg = this._userDetailDlg;
        return  {
            next: (response) => {
                userDetailDlg.hide();
                // Reload
                indexSubject.next();
            }
        };
    },

    /**
     * Subject for user delete action
     * @type Map
     */
    getDeleteUserResponseObserver: function () {
        // TODO Implement properly, handle messages and error
        let indexSubject = this._indexActionSubject;
        let userDetailDlg = this._userDetailDlg;
        return  {
            next: (response) => {
                userDetailDlg.hide();
                // Reload
                indexSubject.next();
            }
        };
    },

    /**
     *Subject for user force logout action
     * @returns {Map}
     */
    getForceLogoutResponseObserver: function () {
        // TODO Implement
        return  {
            next: (response) => {
            }
        };
    }
};

/*--------------------------Controller------------------------*/
/**
 * Instance to handle WEB requests
 * @type
 */
var UserMgtWebService = {
    /**
     * Get promise for the index action
     * @param {Map} indexForm
     * @returns {undefined}
     */
    getIndexAJAXObservable: function (indexForm) {
        // Create
        let indexUrl = Urls.USER_MGT_BASE_URL + '/' + Urls.USER_MGT_INDEX_ACTION;
        return this.getPostActionObservable(indexUrl, indexForm);
    },

    /**
     * Get promise for the action retrieving user detail
     * @param {Number} currentUserSelectedId
     * @returns {unresolved}
     */
    getUserDetailsRetrieveAJAXObservable: function (currentUserSelectedId) {
        // Get user details
        let getDetailUrl = Urls.USER_MGT_BASE_URL + '/' + Urls.USER_MGT_GETDETAIL_ACTION;
        let detailForm = {
            userId: currentUserSelectedId
        };
        return this.getPostActionObservable(getDetailUrl, detailForm);
    },

    getSaveAJAXObservable: function (saveForm) {
        // Create
        let saveUrl = Urls.USER_MGT_BASE_URL + '/' + Urls.USER_MGT_ADDUPDATE_ACTION;
        return this.getPostActionObservable(saveUrl, saveForm);
    },

    getDeleteAJAXObservable: function (userIdsToDelete) {
        let deleteForm = {
            userIdsToDelete: userIdsToDelete
        };
        // Create
        let deleteUrl = Urls.USER_MGT_BASE_URL + '/' + Urls.USER_MGT_DELETE_ACTION;
        return this.getPostActionObservable(deleteUrl, deleteForm);
    },

    /*--------------------Private methods--------------------*/
    /**
     * Shortcut for get AJAX promise
     * @param {String} url
     * @param {Map} form
     * @returns {Observable}
     */
    getPostActionObservable: function (url, form) {
        let promise = (new MendelAjaxExecutor())
                .url(url)
                .formData(form)
                .getPromise();
        return Rx.Observable.fromPromise(promise);
    }
};

/*--------------------------Main actions------------------------*/
// Entry point
$(document).ready(function () {
    initViews();
    setupEvents();
    loadInitialData();
});

/**
 * Initialize user manager instance
 * @returns {undefined}
 */
function initViews() {
    // Page fragment
    UserRecordsPageFragment.init($('#frgPaging'),
            $('#btnReload'),
            $('#btnAddUser'),
            $('#btnDeleteUser'),
            $('#btnForceUserLogout'));

    // Dialog
    UserDetailDlg.init($('#mdlUserAddUpdate'));

}

/**
 * Setup interactions
 * @returns {undefined}
 */
function setupEvents() {
    // Initialize subjects
    UserActionSubjects.init(UserDetailDlg);
    ServerResponseSubjects.init(UserRecordsPageFragment, UserDetailDlg, UserActionSubjects._indexSubject);
    UserActionSubjects.setupSubjects(UserMgtWebService, ServerResponseSubjects);

    // Wire subjects to views
    UserDetailDlg.setSubject(UserActionSubjects._updateUserSubject);
    UserRecordsPageFragment.setSubject(UserActionSubjects._indexSubject,
            UserActionSubjects._addUserSubject,
            UserActionSubjects._getUserDetailsSubject,
            UserActionSubjects._deleteUsersSubject,
            UserActionSubjects._forceLogoutUsersSubject);
}

/**
 * Load initial data
 * @returns {undefined}
 */
function loadInitialData() {
    // Here subject acts as an observer
    UserActionSubjects._indexSubject.next();
}
