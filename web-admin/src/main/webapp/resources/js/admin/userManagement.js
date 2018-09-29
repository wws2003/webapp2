/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* Values is initialized by let and therefore can not be redeclared here */
/* global MendelApp, Tagger, MendelDialog, Stomp  */

var Rx = Rx || {};
/*----------------------------------------------------Constansts----------------------------------------------------*/
/**
 * Based urls
 * @type Map
 */
let Urls = {
    USER_MGT_BASE_URL: MendelApp.BASE_URL + location.pathname,
    USER_MGT_LOGIN_CHECK_TOPIC: '/' + MendelApp.APP_NAME + '/public/ws-ep',
    USER_MGT_INDEX_ACTION: 'index',
    USER_MGT_ADDUPDATE_ACTION: 'addUpdate',
    USER_MGT_GETDETAIL_ACTION: 'detail',
    USER_MGT_GETALLUSERPRIVS_ACTION: 'userPrivs',
    USER_MGT_DELETE_ACTION: 'delete'
};

let Topics = {
    RECENT_LOGIN_STATUS: '/topic/loginCheck'
};

let RecordsCtrlArea = {
    BTN_RELOAD: {
        id: 'btnReload',
        text: 'Reload',
        clz: 'btn btn-primary lo_records_ctrl_button'
    },
    BTN_ADD: {
        id: 'btnAddUser',
        text: 'Add user',
        clz: 'btn btn-primary lo_records_ctrl_button'
    },
    BTN_DELETE: {
        id: 'btnDeleteUser',
        text: 'Delete users',
        clz: 'btn btn-primary lo_records_ctrl_button'
    },
    BTN_FORECELOGOUT: {
        id: 'btnForceLogout',
        text: 'Force logout',
        clz: 'btn btn-primary lo_records_ctrl_button'
    }
};

/*----------------------------------------------------Views----------------------------------------------------*/
/**
 * Instance to handler UI stuffs (act basically as a view)
 * @type type
 */
var UserRecordsPageFragment = {
    /**
     * Initialize view with related buttons
     * @param {JQuery} userRecordsPagingFragment
     * @returns {undefined}
     */
    init: function (userRecordsPagingFragment) {
        // Create view builders onto paging fragment
        this._pageRender = (new CommonPagingFragmentRender())
                .tableClass('lo_fixed_header_table')
                .withRecordsCtrlElement(this.createRecordCtrlButtonHtml(RecordsCtrlArea.BTN_RELOAD))
                .withRecordsCtrlElement(this.createRecordCtrlButtonHtml(RecordsCtrlArea.BTN_ADD))
                .withRecordsCtrlElement(this.createRecordCtrlButtonHtml(RecordsCtrlArea.BTN_DELETE))
                .withRecordsCtrlElement(this.createRecordCtrlButtonHtml(RecordsCtrlArea.BTN_FORECELOGOUT))
                .headerGenFunc(UserRecordsPageFragment.userTblHeadGenFunc)
                .rowGenFunc(UserRecordsPageFragment.userTblRowGenFunc)
                .renderedTableDecorateFunc(UserRecordsPageFragment.userTblDecorateFunc.bind(this));

        // Build records control area first
        this._pageRender.buildRecordsCtrlArea(userRecordsPagingFragment);

        // Assign view components
        this._userRecordsPagingFragment = userRecordsPagingFragment;
        this._btnReload = userRecordsPagingFragment.find('#' + RecordsCtrlArea.BTN_RELOAD.id);
        this._btnAdd = userRecordsPagingFragment.find('#' + RecordsCtrlArea.BTN_ADD.id);
        this._btnDelete = userRecordsPagingFragment.find('#' + RecordsCtrlArea.BTN_DELETE.id);
        this._btnForceLogout = userRecordsPagingFragment.find('#' + RecordsCtrlArea.BTN_FORECELOGOUT.id);

        // Subjects (for events fired in this view)
        this._userUpdateSubject = undefined;
    },

    /**
     * Set subjects for user-interactions
     * @param {Subject} addSubject
     * @param {Subject} updateSubject
     * @param {Subject} deleteSubject
     * @param {Subject} forceLogoutSubject
     * @returns {undefined}
     */
    setUserActionSubjects: function (addSubject, updateSubject, deleteSubject, forceLogoutSubject) {
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
     * Set page request subject. TODO Better implementation
     * @param {Subject} pageRequestSubject
     * @returns {undefined}
     */
    setPageRequestSubject: function (pageRequestSubject) {
        // Paging action (same subject as reload)
        this._pageRender.pageRequestSubject(pageRequestSubject);
        // Build renderer onto current paging fragment area
        this._pageRender.applyPageRequestSubject(this._userRecordsPagingFragment);
    },

    /**
     * Setup observables for page request
     * @param {Observable} screenIntializedObservable
     * @param {Observable} addSuccessObservable
     * @param {Observable} deleteSuccessObservable
     * @param {Observable} forceLogoutSuccessObservable
     * @returns {undefined}
     */
    setObservablesForPageRequest: function (screenIntializedObservable, addSuccessObservable, deleteSuccessObservable, forceLogoutSuccessObservable) {
        // Apart from add and delete success, clicking on button Reload also included
        let userRecordsPagingFragment = this._userRecordsPagingFragment;
        let getRecordCountPerPageFunc = () => userRecordsPagingFragment.find('#sltPagingRecordPerPage').val();

        this._pageRender
                .pageRequestObservable(Rx.Observable.fromEvent(this._btnReload, 'click').map(() => {
                    // Providing paging subject with page request form
                    return {
                        pageNumber: parseInt(userRecordsPagingFragment.find('#txtPagingCurrent').val()),
                        recordCountPerPage: getRecordCountPerPageFunc()
                    };
                }))
                .pageRequestObservable(screenIntializedObservable.map(() => {
                    // Providing paging subject with page request form (starting from page 1)
                    return {
                        pageNumber: 1,
                        recordCountPerPage: getRecordCountPerPageFunc()
                    };
                }))
                .pageRequestObservable(addSuccessObservable.map(() => {
                    // Providing paging subject with page request form (starting from page 1)
                    return {
                        pageNumber: 1,
                        recordCountPerPage: getRecordCountPerPageFunc()
                    };
                }))
                .pageRequestObservable(deleteSuccessObservable.map(() => {
                    // Providing paging subject with page request form (starting from page 1)
                    return {
                        pageNumber: 1,
                        recordCountPerPage: getRecordCountPerPageFunc()
                    };
                }));
        // TODO Handle forceLogoutSuccessObservable
    },

    /**
     * Update login status of displaying user records
     * @param {Array} recentLoggedInUserIds
     * @param {Array} recentLoggedOutUserIds
     * @returns {undefined}
     */
    renderRecentLoginStatus: function (recentLoggedInUserIds, recentLoggedOutUserIds) {
        // TODO Implement properly
        let userTrs = this._userRecordsPagingFragment.find('#tblPagingContent tbody tr');
        Rx.Observable.merge(
                Rx.Observable.from(userTrs).filter(tr => recentLoggedInUserIds.indexOf(parseInt($(tr).attr('user_id'))) >= 0)
                .map(tr => {
                    return {
                        tableEle: tr,
                        loginStateHtml: 'True'
                    };
                }),
                Rx.Observable.from(userTrs).filter(tr => recentLoggedOutUserIds.indexOf(parseInt($(tr).attr('user_id'))) >= 0)
                .map(tr => {
                    return {
                        tableEle: tr,
                        loginStateHtml: 'False'
                    };
                })
                )
                .subscribe(trInfo => {
                    trInfo.tableEle.find('td:nth-child(4)').html(trInfo.loginStateHtml);
                });
    },

    /*--------------------Private methods-------------------*/

    /**
     * Create HTML for button element in the user record control area
     * @param {Map} btn
     * @returns {String}
     */
    createRecordCtrlButtonHtml: function (btn) {
        return Tagger.button()
                .id(btn.id)
                .innerText(btn.text)
                .withClasses(btn.clz)
                .build();
    },

    userTblHeadGenFunc: function (userPage) {
        return Tagger.tr()
                .th('Name').withClass('col-xs-2')
                .then()
                .th('Display name').withClass('col-xs-2')
                .then()
                .th('Role').withClass('col-xs-2')
                .then()
                .th('Login status').withClass('col-xs-2')
                .then()
                .th('Force logout').withClass('col-xs-2')
                .then()
                .th('Delete').withClass('col-xs-1')
                .then()
                .th('Update').withClass('col-xs-1')
                .then()
                .build();
    },

    userTblRowGenFunc: function (userRecord) {
        // TODO Better solution
        let isUserRolePred = () => (userRecord.role === 'USER');
        return Tagger.tr()
                .td(userRecord.name).withClass('col-xs-2')
                .then()
                .td(userRecord.displayedName).withClass('col-xs-2')
                .then()
                .td(userRecord.role).withClass('col-xs-2')
                .then()
                .td(userRecord.loggingIn).withClass('col-xs-2')
                .then()
                .td().withClasses('col-xs-2 mo_checkbox_wrapper').innerTagIf(isUserRolePred, 'input').autoClose().withAttr('type', 'checkbox').withClass('mo_chkForceLogout').id('chkForceLogout_' + userRecord.id).then()
                .then()
                .td().withClasses('col-xs-1 mo_checkbox_wrapper').innerTagIf(isUserRolePred, 'input').autoClose().withAttr('type', 'checkbox').withClass('mo_chkDelete').id('chkDelete_' + userRecord.id).then()
                .then()
                .td().withClass('col-xs-1').innerTagIf(isUserRolePred, 'button').innerText('Update').withClass('mo_btnUpdate').id('btnAddUpdate_' + userRecord.id).then()
                .then()
                .withAttrIfOrElse(isUserRolePred, 'user_id', userRecord.id, -1)
                .build();
    },

    /**
     * Function to decorate rendered user table, here is to set events for button update
     * @param {JQuery} tableEle
     * @returns {undefined}
     */
    userTblDecorateFunc: function (tableEle) {
        // Render to view is done automatically by _pageRender, so do nothing here
        // Events on the page
        // TODO Unsubsribe old events ?
        Rx.Observable.fromEvent(tableEle.find('button.mo_btnUpdate'), 'click')
                .map(e => $(e.target).attr('id').split('_')[1])
                .map(idStr => parseInt(idStr))
                .subscribe(this._userUpdateSubject);
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
                .map(ev => {
                    return {
                        userName: mdlUserAddUpdate.find('#txtUserName').val(),
                        userDispName: mdlUserAddUpdate.find('#txtDispName').val(),
                        rawPassword: mdlUserAddUpdate.find('#txtPassword').val(),
                        confirmedRawPassword: mdlUserAddUpdate.find('#txtPasswordConfirm').val(),
                        grantedPrivilegeIds: mdlUserAddUpdate.find('#sltGrantedPrivs option')
                                .get()
                                .map(optEle => parseInt(optEle.getAttribute('val')))
                    };
                });
        this._userInfoFormSubscription = undefined;
        this._saveSubject = undefined;
        // Observable of internal pure UI-stuffs
        this.setupGrantRevokeInternalActions();
    },

    /**
     * Set subjects for user-interactions
     * @param {Subject} saveSubject
     * @returns {undefined}
     */
    setUserActionSubject: function (saveSubject) {
        this._saveSubject = saveSubject;
    },

    /**
     * Show user detailed info for update
     * @param {Map} userDetails
     * @returns {undefined}
     */
    showForUpdate: function (userDetails) {
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
        // Privileges render
        this.setPrivsGrantRevokeOptions(userDetails.remainingGrantablePrivileges, userDetails.grantedPrivileges);
        // Show up
        mdlUserAddUpdate.modal('show');
    },

    /**
     * Show user detailed info for add
     * @param {Array} allUserPrivs
     * @returns {undefined}
     */
    showForAdd: function (allUserPrivs) {
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
        // Privileges
        this.setPrivsGrantRevokeOptions(allUserPrivs, []);
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
    },

    /**
     * Action for privileges grant/revoke buttons
     * @returns {undefined}
     */
    setupGrantRevokeInternalActions: function () {
        // Observable of internal UI-stuffs
        let btnGrant = this._mdlUserAddUpdate.find('#btnGrant');
        let btnRevoke = this._mdlUserAddUpdate.find('#btnRevoke');
        let sltNotGrantedPrivs = this._mdlUserAddUpdate.find('#sltNotGrantedPrivs');
        let sltGrantedPrivs = this._mdlUserAddUpdate.find('#sltGrantedPrivs');

        // -Move selected options between 2 select elements
        Rx.Observable.fromEvent(btnGrant, 'click')
                .map(() => [sltNotGrantedPrivs, sltGrantedPrivs])
                .merge(Rx.Observable
                        .fromEvent(btnRevoke, 'click')
                        .map(() => [sltGrantedPrivs, sltNotGrantedPrivs])
                        )
                .subscribe(eles => {
                    eles[0].find('option:selected').appendTo(eles[1]);
                });
    },

    /**
     * Set privileges into proper select elements
     * @param {Array} notGrantedPrivs
     * @param {Array} grantedPrivs
     * @returns {undefined}
     */
    setPrivsGrantRevokeOptions: function (notGrantedPrivs, grantedPrivs) {
        let sltNotGrantedPrivs = this._mdlUserAddUpdate.find('#sltNotGrantedPrivs');
        let sltGrantedPrivs = this._mdlUserAddUpdate.find('#sltGrantedPrivs');
        // Set data
        Rx.Observable.from(
                [
                    [sltNotGrantedPrivs, notGrantedPrivs],
                    [sltGrantedPrivs, grantedPrivs]
                ]
                ).map(e => [e[0], e[1].reduce(
                        (acc, cur) => acc + Tagger.option()
                            .innerText(cur.item3)
                            .withAttr('val', cur.item1)
                            .build(),
                        ''
                        )])
                .subscribe(selOpts => selOpts[0].html(selOpts[1]));
    }
};

/*----------------------------------------------------Observable, Subjects, Observers----------------------------------------------------*/
var UserActionSubjects = {

    /**
     * Initialize subjects from user interacting events
     * @param {Map} userMgtWebService
     */
    init: function (userMgtWebService) {
        // Service wiring
        this._userMgtWebService = userMgtWebService;
        // Subjects
        this._getUserDetailsSubject = new Rx.Subject();
        this._addUserSubject = new Rx.Subject();
        this._updateUserSubject = new Rx.Subject();
        this._deleteUsersSubject = new Rx.Subject();
        this._forceLogoutUsersSubject = new Rx.Subject();
    },

    /**
     * Init by providing controller instance
     * @param {Map} serverResponseOberservers
     * @returns {undefined}
     */
    setupObservers: function (serverResponseOberservers) {
        let userMgtWebService = this._userMgtWebService;
        // Get details
        this._getUserDetailsSubject
                .switchMap(userId => userMgtWebService.getUserDetailsRetrieveAJAXObservable(userId))
                .subscribe(serverResponseOberservers.getRetrieveUserDetailsResponseObserver());

        // Add
        this._addUserSubject
                .switchMap(userId => userMgtWebService.getAllUserPrivsRetrieveAJAXObservable(userId))
                .subscribe(serverResponseOberservers.getRetrieveAllUserPrivsResponseObserver());

        // Update
        this._updateUserSubject
                .switchMap(saveForm => userMgtWebService.getSaveAJAXObservable(saveForm))
                .subscribe(serverResponseOberservers.getSaveUserResponseObserver());

        // Delete
        this._deleteUsersSubject
                .switchMap(userIdsToDelete => userMgtWebService.getDeleteAJAXObservable(userIdsToDelete))
                .subscribe(serverResponseOberservers.getDeleteUserResponseObserver());

        // Force logout TODO Implement
    }
};

/**
 * Subject for page request
 * @type Map
 */
var PageRequestSubject = {
    /**
     * Initialize subjects from user interacting events
     * @param {Map} userMgtWebService
     */
    init: function (userMgtWebService) {
        // Subjects
        this._pageRequestSubject = (new Rx.Subject()).switchMap(indexForm => userMgtWebService.getIndexAJAXObservable(indexForm));
    }
};

/**
 * Observables for page request subject (i.e. firing page request for each value emitted)
 * @type Map
 */
var PageRequestObservables = {
    /**
     * Initialize
     * @returns {undefined}
     */
    init: function () {
        this._pageRequestAfterScreenInitialized = new Rx.Subject();
        this._pageRequestAfterAddSuccess = new Rx.Subject();
        this._pageRequestAfterDeleteSuccess = new Rx.Subject();
        this._pageRequestAfterForceLogoutSuccess = new Rx.Subject();
    }
};

/**
 * Observers for server response
 * @type Map
 */
var ServerResponseObservers = {
    /**
     * Initialize by views and one user interaction subject (for index action)
     * @param {Map} userRecordsPageFragment
     * @param {Map} userDetailDlg
     * @param {Subject} addSuccessSubject
     * @param {Subject} deleteSuccessSubject
     * @param {Subject} forceLogoutSuccessSubject
     * @returns {undefined}
     */
    init: function (userRecordsPageFragment, userDetailDlg, addSuccessSubject, deleteSuccessSubject, forceLogoutSuccessSubject) {
        this._userRecordsPageFragment = userRecordsPageFragment;
        this._userDetailDlg = userDetailDlg;
        this._addSuccessSubject = addSuccessSubject;
        this._deleteSuccessSubject = deleteSuccessSubject;
        this._forceLogoutSuccessSubject = forceLogoutSuccessSubject;
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
     * Subject for get all user privileges action
     * @type Map
     */
    getRetrieveAllUserPrivsResponseObserver: function () {
        let successResponseFunc = UserDetailDlg.showForAdd.bind(this._userDetailDlg);
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
        let addSuccessSubject = this._addSuccessSubject;
        let userDetailDlg = this._userDetailDlg;
        return  {
            next: (response) => {
                userDetailDlg.hide();
                if (response.success) {
                    // Show dialog after hide dialog, then reload
                    MendelDialog.info('Message', response.successMessages[0], () => addSuccessSubject.next());
                } else {
                    // Show error message
                    MendelDialog.error('Message', response.errorMessages[0]);
                }
            }
        };
    },

    /**
     * Subject for user delete action
     * @type Map
     */
    getDeleteUserResponseObserver: function () {
        // TODO Implement properly, handle messages and error
        let deleteSuccessSubject = this._deleteSuccessSubject;
        let userDetailDlg = this._userDetailDlg;
        return  {
            next: (response) => {
                userDetailDlg.hide();
                // Show dialog after hide dialog
                MendelDialog.info('Message', response.successMessages[0], () => deleteSuccessSubject.next());
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

/**
 * Observable for server message
 * @type Map
 */
var ServerMessageObservables = {

    /**
     * Initialize with observer ?
     * @param {Function} serverMessageObserver
     * @returns {undefined}
     */
    init: function (serverMessageObserver) {
        // Web socket very first experimental
        // In the case WebSocket is not supported, SockJS can emulate it by xhr-streaming, xhr-polling, etc.
        // Currently does not deal with Spring security
        let socket = new SockJS(Urls.USER_MGT_LOGIN_CHECK_TOPIC);
        // STOMP (a so-called Streaming text oriented message protocol) is trying to work on WebSocket API
        // STOMP: A lightweight protocol over TCP, can be conducted by WebSocket API
        let stompClient = Stomp.over(socket);
        stompClient.connect({}, frame => {
            // For debug
            console.log('CONNECTED');
            // After connected, start listening to server
            stompClient.subscribe(Topics.RECENT_LOGIN_STATUS, serverMessageObserver);
        });
    }
};

/**
 * Observers for server message (e.g. via websocket)
 * @type Map
 */
var ServerMessageObservers = {
    /**
     * Initialize by views and one user interaction subject (for index action)
     * @param {Map} userRecordsPageFragment
     * @returns {undefined}
     */
    init: function (userRecordsPageFragment) {
        this._userRecordsPageFragment = userRecordsPageFragment;
    },

    getRecentLoginStatusChangedObserver: function () {
        let successResponseFunc = UserRecordsPageFragment.renderRecentLoginStatus.bind(this._userRecordsPageFragment);
        return msg => {
            // TODO Implement
            let recentLoggedInUserIds = [];
            let recentLoggedOutUserIds = [];
            successResponseFunc(recentLoggedInUserIds, recentLoggedOutUserIds);
        };
    }
};

/*--------------------------------------------------Service------------------------------------------------*/
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

    getAllUserPrivsRetrieveAJAXObservable: function () {
        // Get all user privileges
        let getAllUserPrivs = Urls.USER_MGT_BASE_URL + '/' + Urls.USER_MGT_GETALLUSERPRIVS_ACTION;
        return this.getGetActionObservable(getAllUserPrivs);
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
     * Shortcut for Post AJAX promise
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
    },

    /**
     * Shortcut for Fet AJAX promise
     * @param {String} url
     * @returns {Observable}
     */
    getGetActionObservable: function (url) {
        let promise = (new MendelAjaxExecutor())
                .url(url)
                .getPromise();
        return Rx.Observable.fromPromise(promise);
    }
};

/*--------------------------------------------------Main actions------------------------------------------------*/
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
    UserRecordsPageFragment.init($('#frgPaging'));

    // Dialog
    UserDetailDlg.init($('#mdlUserAddUpdate'));
}

/**
 * Setup interactions
 * @returns {undefined}
 */
function setupEvents() {
    // Initialize subjects
    // Page request observables
    PageRequestObservables.init();
    // Page request subject
    PageRequestSubject.init(UserMgtWebService);
    // Server response subjects
    ServerResponseObservers.init(UserRecordsPageFragment,
            UserDetailDlg,
            PageRequestObservables._pageRequestAfterAddSuccess,
            PageRequestObservables._pageRequestAfterDeleteSuccess,
            PageRequestObservables._pageRequestAfterForceLogoutSuccess);
    // User action subjects
    UserActionSubjects.init(UserMgtWebService);
    // Server message
    ServerMessageObservers.init(UserRecordsPageFragment);
    ServerMessageObservables.init(ServerMessageObservers.getRecentLoginStatusChangedObserver());

    // Wire observable to observers
    // User actions
    // User detail dialog
    UserDetailDlg.setUserActionSubject(UserActionSubjects._updateUserSubject);
    UserRecordsPageFragment.setUserActionSubjects(UserActionSubjects._addUserSubject,
            UserActionSubjects._getUserDetailsSubject,
            UserActionSubjects._deleteUsersSubject,
            UserActionSubjects._forceLogoutUsersSubject);
    // Page-request related
    UserRecordsPageFragment.setPageRequestSubject(PageRequestSubject._pageRequestSubject);
    UserRecordsPageFragment.setObservablesForPageRequest(PageRequestObservables._pageRequestAfterScreenInitialized,
            PageRequestObservables._pageRequestAfterAddSuccess,
            PageRequestObservables._pageRequestAfterDeleteSuccess,
            PageRequestObservables._pageRequestAfterForceLogoutSuccess);
    UserActionSubjects.setupObservers(ServerResponseObservers);
}

/**
 * Load initial data
 * @returns {undefined}
 */
function loadInitialData() {
    // Here subject acts as an observer
    PageRequestObservables._pageRequestAfterScreenInitialized.next();
}
