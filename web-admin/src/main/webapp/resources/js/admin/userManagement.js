/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* Values is initialized by let and therefore can not be redeclared here */
/* global MendelApp, Tagger, MendelDialog, Stomp, MendelCommon, MendelAjaxObservableBuilder, MendelAjaxResponseObserverBuilder, MendelWebs  */

var Rx = Rx || {};
var CommonDetailDlg = CommonDetailDlg || {};

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
    USER_MGT_DELETE_ACTION: 'delete',
    USER_MGT_DELETE_FORCE_LOGOUT: 'forceLogout'
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
                }))
                .pageRequestObservable(forceLogoutSuccessObservable.map(() => {
                    // Providing paging subject with page request form (starting from page 1)
                    return {
                        pageNumber: 1,
                        recordCountPerPage: getRecordCountPerPageFunc()
                    };
                }));
    },

    /**
     * Update login status of displaying user records
     * @param {Map} loggedInUserMap
     * @param {Array} recentLoggedOutUserIds
     * @returns {undefined}
     */
    renderRecentLoginStatus: function (loggedInUserMap, recentLoggedOutUserIds) {
        // TODO Implement properly
        let userTrs = this._userRecordsPagingFragment.find('#tblPagingContent tbody tr');
        Rx.Observable.merge(
                Rx.Observable.from(userTrs).filter(tr => parseInt($(tr).attr('user_id')) in loggedInUserMap)
                .map(tr => {
                    return {
                        tableEle: $(tr),
                        loginStateHtml: loggedInUserMap[parseInt($(tr).attr('user_id'))],
                        chckBoxDeleteFunc: MendelCommon.disableEle,
                        btnUpdateFunc: MendelCommon.disableEle
                    };
                }),
                Rx.Observable.from(userTrs).filter(tr => recentLoggedOutUserIds.indexOf(parseInt($(tr).attr('user_id'))) >= 0)
                .map(tr => {
                    return {
                        tableEle: $(tr),
                        loginStateHtml: '',
                        chckBoxDeleteFunc: MendelCommon.enableEle,
                        btnUpdateFunc: MendelCommon.enableEle
                    };
                })
                )
                .subscribe(trInfo => {
                    // Login since label
                    trInfo.tableEle.find('td:nth-child(4)').html(trInfo.loginStateHtml);
                    // Delete, Update button
                    trInfo.chckBoxDeleteFunc(trInfo.tableEle.find('td:nth-child(6) input[type="checkbox"]'));
                    trInfo.btnUpdateFunc(trInfo.tableEle.find('td:nth-child(7) button'));
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
                .th('Login since').withClass('col-xs-2')
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
        let isUserRolePred = () => (userRecord.role === 'USER');
        // Only update users not login
        let userCanNotUpdatePred = () => (!isUserRolePred() || userRecord.loginTimeStamp);
        return Tagger.tr()
                .td(userRecord.name).withClass('col-xs-2')
                .then()
                .td(userRecord.displayedName).withClass('col-xs-2')
                .then()
                .td(userRecord.role).withClass('col-xs-2')
                .then()
                .td(userRecord.loginTimeStamp).withClasses('col-xs-2 mo_logged_in')
                .then()
                .td().withClasses('col-xs-2 mo_checkbox_wrapper').innerTagIf(isUserRolePred, 'input').autoClose().withAttr('type', 'checkbox').withClass('mo_chkForceLogout').id('chkForceLogout_' + userRecord.id).then()
                .then()
                .td().withClasses('col-xs-1 mo_checkbox_wrapper').innerTagIf(isUserRolePred, 'input').withAttrIf(userCanNotUpdatePred, 'disabled', 'disabled').autoClose().withAttr('type', 'checkbox').withClass('mo_chkDelete').id('chkDelete_' + userRecord.id).then()
                .then()
                .td().withClass('col-xs-1').innerTagIf(isUserRolePred, 'button').withAttrIf(userCanNotUpdatePred, 'disabled', 'disabled').innerText('Update').withClass('mo_btnUpdate').id('btnAddUpdate_' + userRecord.id).then()
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

/*----------------Add/Update user dialog----------------------*/

UserDetailDlg.prototype = Object.create(CommonDetailDlg.prototype);
UserDetailDlg.prototype.constructor = UserDetailDlg;

/**
 * Construct
 * @param {JQuery} dlg
 * @type UserDetailDlg
 */
function UserDetailDlg(dlg) {
    CommonDetailDlg.call(this, dlg, '#btnUserAddUpdateDone');
    // Observable of internal pure UI-stuffs
    this.setupGrantRevokeInternalActions(dlg);
}

/**
 * Create data for save form. To be implemented by subclass
 * @returns {Map}
 */
UserDetailDlg.prototype.createSaveData = function (mdlUserAddUpdate) {
    return {
        userName: mdlUserAddUpdate.find('#txtUserName').val(),
        userDispName: mdlUserAddUpdate.find('#txtDispName').val(),
        rawPassword: mdlUserAddUpdate.find('#txtPassword').val(),
        confirmedRawPassword: mdlUserAddUpdate.find('#txtPasswordConfirm').val(),
        grantedPrivilegeIds: mdlUserAddUpdate.find('#sltGrantedPrivs option')
                .get()
                .map(optEle => parseInt(optEle.getAttribute('val')))
    };
};

/**
 * Render data for add action to the dialog
 * @param {JQuery} mdlUserAddUpdate
 * @param {Map} allUserPrivs
 * @returns {undefined}
 */
UserDetailDlg.prototype.renderDataForAdd = function (mdlUserAddUpdate, allUserPrivs) {
    // Basic info
    mdlUserAddUpdate.find('#lblUserDialogTitle').text('Input new user info');
    mdlUserAddUpdate.find('#txtUserName').val('');
    mdlUserAddUpdate.find('#txtDispName').val('');
    mdlUserAddUpdate.find('#txtPassword').val('');
    mdlUserAddUpdate.find('#txtPasswordConfirm').val('');
    // Privileges
    this.setPrivsGrantRevokeOptions(mdlUserAddUpdate, allUserPrivs, []);
};

/**
 * Render to view the record with detailed information. To be implemented by subclass
 * @param {JQuery} mdlUserAddUpdate
 * @param {Map} userDetails
 * @returns {undefined}
 */
UserDetailDlg.prototype.renderRecordDetailForUpdate = function (mdlUserAddUpdate, userDetails) {
    // Basic info
    mdlUserAddUpdate.find('#lblUserDialogTitle').text('Update user info');
    mdlUserAddUpdate.find('#txtUserName').val(userDetails.name);
    mdlUserAddUpdate.find('#txtDispName').val(userDetails.dispName);
    mdlUserAddUpdate.find('#txtPassword').val('pass001');
    mdlUserAddUpdate.find('#txtPasswordConfirm').val('pass001');
    // Privileges
    this.setPrivsGrantRevokeOptions(mdlUserAddUpdate, userDetails.remainingGrantablePrivileges, userDetails.grantedPrivileges);
};

/**
 * Action for privileges grant/revoke buttons
 * @returns {undefined}
 */
UserDetailDlg.prototype.setupGrantRevokeInternalActions = function (mdlUserAddUpdate) {
    // Observable of internal UI-stuffs
    let btnGrant = mdlUserAddUpdate.find('#btnGrant');
    let btnRevoke = mdlUserAddUpdate.find('#btnRevoke');
    let sltNotGrantedPrivs = mdlUserAddUpdate.find('#sltNotGrantedPrivs');
    let sltGrantedPrivs = mdlUserAddUpdate.find('#sltGrantedPrivs');

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
};

/**
 * Set privileges into proper select elements
 * @param {Array} notGrantedPrivs
 * @param {Array} grantedPrivs
 * @returns {undefined}
 */
UserDetailDlg.prototype.setPrivsGrantRevokeOptions = function (mdlUserAddUpdate, notGrantedPrivs, grantedPrivs) {
    let sltNotGrantedPrivs = mdlUserAddUpdate.find('#sltNotGrantedPrivs');
    let sltGrantedPrivs = mdlUserAddUpdate.find('#sltGrantedPrivs');
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
};

/*----------------------------------------------------Observable, Subjects, Observers----------------------------------------------------*/
var UserActionSubjects = {

    /**
     * Initialize subjects for user interacting events
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
        let ajaxObservableBuilder = MendelAjaxObservableBuilder.baseCRUDUrl(Urls.USER_MGT_BASE_URL)
                .indexActionUrl(Urls.USER_MGT_INDEX_ACTION)
                .saveActionUrl(Urls.USER_MGT_ADDUPDATE_ACTION)
                .detailActionUrl(Urls.USER_MGT_GETDETAIL_ACTION)
                .deleteActionUrl(Urls.USER_MGT_DELETE_ACTION);
        let observerBuilder = MendelWebs.getDefaultAjaxResponseObserverBuilder();
        let createAjaxResponseFunc = MendelAjaxResponseObserverBuilder.prototype.createAjaxResponseObserver.bind(observerBuilder);
        // Get details
        this._getUserDetailsSubject
                .switchMap(userId => ajaxObservableBuilder.getDetailsRetrieveAJAXObservable(userId))
                .subscribe(createAjaxResponseFunc(serverResponseOberservers.getRetrieveDetailsResponseObserver()));

        // Add
        this._addUserSubject
                .switchMap(userId => userMgtWebService.getAllUserPrivsRetrieveAJAXObservable(userId))
                .subscribe(createAjaxResponseFunc(serverResponseOberservers.getRetrieveAllUserPrivsResponseObserver()));

        // Update
        this._updateUserSubject
                .switchMap(saveForm => ajaxObservableBuilder.getSaveAJAXObservable(saveForm))
                .subscribe(createAjaxResponseFunc(serverResponseOberservers.getSaveResponseObserver()));

        // Delete
        this._deleteUsersSubject
                .switchMap(userIdsToDelete => ajaxObservableBuilder.getDeleteAJAXObservable(userIdsToDelete))
                .subscribe(createAjaxResponseFunc(serverResponseOberservers.getDeleteResponseObserver()));

        // Force logout
        this._forceLogoutUsersSubject
                .switchMap(userIdsToForceLogout => userMgtWebService.getForceLogoutAJAXObservable(userIdsToForceLogout))
                .subscribe(createAjaxResponseFunc(serverResponseOberservers.getForceLogoutUserResponseObserver()));
    }
};

/**
 * Subject for page request
 * @type Map
 */
var PageRequestSubject = {
    /**
     * Initialize subjects from user interacting events
     */
    init: function () {
        // Subjects
        this._pageRequestSubject = (new Rx.Subject()).switchMap(indexForm => MendelAjaxObservableBuilder.baseCRUDUrl(Urls.USER_MGT_BASE_URL)
                    .indexActionUrl(Urls.USER_MGT_INDEX_ACTION)
                    .getIndexAJAXObservable(indexForm));
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

// Observers for server response
ServerResponseObserver.prototype = Object.create(CommonCRUDServerResponseObserver.prototype);
ServerResponseObserver.prototype.constructor = ServerResponseObserver;

/**
 * Initialize by views and one user interaction subject (for index action)
 * @param {Map} userDetailDlg
 * @param {Subject} addSuccessSubject
 * @param {Subject} deleteSuccessSubject
 * @param {Subject} forceLogoutSuccessSubject
 * @returns {ServerResponseObserver}
 */
function ServerResponseObserver(userDetailDlg, addSuccessSubject, deleteSuccessSubject, forceLogoutSuccessSubject) {
    CommonCRUDServerResponseObserver.call(this, userDetailDlg, addSuccessSubject, deleteSuccessSubject);
    this._forceLogoutSuccessSubject = forceLogoutSuccessSubject;
}

/**
 * Subject for get all user privileges action
 * @type Map
 */
ServerResponseObserver.prototype.getRetrieveAllUserPrivsResponseObserver = function () {
    let successResponseFunc = UserDetailDlg.prototype.showForAdd.bind(this._detailDlg);
    return {
        // TODO Handle error
        next: (response) => successResponseFunc(response.resultObject)
    };
};

/**
 * Subject for user force logout action
 * @type Map
 */
ServerResponseObserver.prototype.getForceLogoutUserResponseObserver = function () {
    // TODO Implement properly, handle messages and error
    let forceLogoutSuccessSubject = this._forceLogoutSuccessSubject;
    let userDetailDlg = this._detailDlg;
    return  {
        next: (response) => {
            userDetailDlg.hide();
            // Show dialog after hide dialog
            MendelDialog.info('Message', response.successMessages[0], () => forceLogoutSuccessSubject.next());
        }
    };
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
            let msgObj = JSON.parse(msg.body);
            let recentLoggedInUserMap = msgObj.loggedInUserMap !== null ? msgObj.loggedInUserMap : {};
            let recentLoggedOutUserIds = msgObj.loggedOutUserIds !== null ? msgObj.loggedOutUserIds : [];
            successResponseFunc(recentLoggedInUserMap, recentLoggedOutUserIds);
        };
    }
};

/*--------------------------------------------------Service------------------------------------------------*/
/**
 * Instance to handle WEB requests
 * @type
 */
var UserMgtWebService = {
    getAllUserPrivsRetrieveAJAXObservable: function () {
        // Get all user privileges (Temporary still have to use POST request to avoid the problem with redirect !!)
        let getAllUserPrivs = Urls.USER_MGT_BASE_URL + '/' + Urls.USER_MGT_GETALLUSERPRIVS_ACTION;
        let form = {};
        return MendelAjaxObservableBuilder.createPostActionObservable(getAllUserPrivs, form);
    },

    getForceLogoutAJAXObservable: function (userIdsToForceLogout) {
        let forceLogoutForm = {
            userIdsToForceLogout: userIdsToForceLogout
        };
        // Create
        let forLogoutUrl = Urls.USER_MGT_BASE_URL + '/' + Urls.USER_MGT_DELETE_FORCE_LOGOUT;
        return MendelAjaxObservableBuilder.createPostActionObservable(forLogoutUrl, forceLogoutForm);
    }
};

/*--------------------------------------------------Main actions------------------------------------------------*/
// Entry point
$(document).ready(function () {
    setupEvents();
    loadInitialData();
});

/**
 * Setup interactions
 * @returns {undefined}
 */
function setupEvents() {
    // Try to apply traditional flow (old approach)
    // 1. Server request observable
    let webService = UserMgtWebService;

    // 2. User action observable
    let userActionSubjects = UserActionSubjects;
    userActionSubjects.init(webService);

    // 3. View internal observables (paging)
    let pageRequestObservables = PageRequestObservables;
    pageRequestObservables.init();

    // 4. View internal subject (paging)
    let pageRequestSubject = PageRequestSubject;
    pageRequestSubject.init();

    // 5. Views
    let userRecordsPageFragment = UserRecordsPageFragment;
    let userDetailDlg = new UserDetailDlg($('#mdlUserAddUpdate'));
    userRecordsPageFragment.init($('#frgPaging'));
    userRecordsPageFragment.setUserActionSubjects(userActionSubjects._addUserSubject,
            userActionSubjects._getUserDetailsSubject,
            userActionSubjects._deleteUsersSubject,
            userActionSubjects._forceLogoutUsersSubject);
    userRecordsPageFragment.setPageRequestSubject(pageRequestSubject._pageRequestSubject);
    userRecordsPageFragment.setObservablesForPageRequest(pageRequestObservables._pageRequestAfterScreenInitialized,
            pageRequestObservables._pageRequestAfterAddSuccess,
            pageRequestObservables._pageRequestAfterDeleteSuccess,
            pageRequestObservables._pageRequestAfterForceLogoutSuccess);
    userDetailDlg.setSaveSubject(userActionSubjects._updateUserSubject);

    // 6. Server observers
    let serverResponseObservers = new ServerResponseObserver(userDetailDlg,
            pageRequestObservables._pageRequestAfterAddSuccess,
            pageRequestObservables._pageRequestAfterDeleteSuccess,
            pageRequestObservables._pageRequestAfterForceLogoutSuccess);
    userActionSubjects.setupObservers(serverResponseObservers);

    // 7. WebSocket stuffs
    let serverMessageObservers = ServerMessageObservers;
    let serverMessageObservables = ServerMessageObservables;
    serverMessageObservers.init(userRecordsPageFragment);
    serverMessageObservables.init(serverMessageObservers.getRecentLoginStatusChangedObserver());
}

/**
 * Load initial data
 * @returns {undefined}
 */
function loadInitialData() {
    // Here subject acts as an observer
    PageRequestObservables._pageRequestAfterScreenInitialized.next();
}
