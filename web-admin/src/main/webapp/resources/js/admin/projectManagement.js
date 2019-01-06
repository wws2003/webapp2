/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global Tagger, MendelApp, MendelDialog, MendelAjaxObservableBuilder, MendelWebs, MendelAjaxResponseObserverBuilder */

var Rx = Rx || {};
var CommonDetailDlg = CommonDetailDlg || {};

/*----------------------------------------------------Constants----------------------------------------------------*/

let ProjectReferScope = {
    PUBLIC: 1,
    PRIVATE: 2
};

let ProjectStatus = {
    ACTIVE: 1,
    PENDING: 2,
    CLOSE: 3
};

/**
 * Based urls
 * @type Map
 */
let Urls = {
    PROJECT_MGT_BASE_URL: MendelApp.BASE_URL + location.pathname,
    PROJECT_MGT_INDEX_ACTION: 'index',
    PROJECT_MGT_ADDUPDATE_ACTION: 'addUpdate',
    PROJECT_MGT_GETDETAIL_ACTION: 'detail',
    PROJECT_MGT_SEARCH_USER_ACTION: 'searchUser',
    PROJECT_MGT_DELETE_ACTION: 'delete'
};

let RecordsCtrlArea = {
    BTN_RELOAD: {
        id: 'btnReload',
        text: 'Reload',
        clz: 'btn btn-primary lo_records_ctrl_button'
    },
    BTN_ADD: {
        id: 'btnAddUser',
        text: 'Add project',
        clz: 'btn btn-primary lo_records_ctrl_button'
    },
    BTN_DELETE: {
        id: 'btnDeleteUser',
        text: 'Delete projects',
        clz: 'btn btn-primary lo_records_ctrl_button'
    }
};

/*----------------------------------------------------Views----------------------------------------------------*/
/**
 * Instance to handler UI stuffs (act basically as a view)
 * @type Map
 */
var ProjectRecordsPageFragment = {
    /**
     * Initialize view with related buttons
     * @param {JQuery} projectRecordsPagingFragment
     * @returns {ProjectRecordsPageFragment}
     */
    init: function (projectRecordsPagingFragment) {
        // Create view builders onto paging fragment
        this._pageRender = (new CommonPagingFragmentRender())
                .tableClass('lo_fixed_header_table')
                .withRecordsCtrlElement(this.createRecordCtrlButtonHtml(RecordsCtrlArea.BTN_RELOAD))
                .withRecordsCtrlElement(this.createRecordCtrlButtonHtml(RecordsCtrlArea.BTN_ADD))
                .withRecordsCtrlElement(this.createRecordCtrlButtonHtml(RecordsCtrlArea.BTN_DELETE))
                .headerGenFunc(ProjectRecordsPageFragment.userTblHeadGenFunc)
                .rowGenFunc(ProjectRecordsPageFragment.userTblRowGenFunc)
                .renderedTableDecorateFunc(ProjectRecordsPageFragment.userTblDecorateFunc.bind(this));

        // Build records control area first
        this._pageRender.buildRecordsCtrlArea(projectRecordsPagingFragment);

        // Assign view components
        this._projectRecordsPagingFragment = projectRecordsPagingFragment;
        this._btnReload = projectRecordsPagingFragment.find('#' + RecordsCtrlArea.BTN_RELOAD.id);
        this._btnAdd = projectRecordsPagingFragment.find('#' + RecordsCtrlArea.BTN_ADD.id);
        this._btnDelete = projectRecordsPagingFragment.find('#' + RecordsCtrlArea.BTN_DELETE.id);

        // Subjects (for events fired in this view)
        this._projectUpdateObserver = undefined;

        // Return
        return this;
    },

    /**
     * Set subjects for user-interactions
     * @param {Subject} addObserver
     * @param {Subject} updateObserver
     * @param {Subject} deleteObserver
     * @returns {undefined}
     */
    userActionSubject: function (addObserver, updateObserver, deleteObserver) {
        // Add
        Rx.Observable.fromEvent(this._btnAdd, 'click')
                .subscribe(addObserver);

        // Delete
        this.subscribeOneActionForCheckedProjects(Rx.Observable.fromEvent(this._btnDelete, 'click'),
                '.mo_chkDelete:checked',
                deleteObserver);

        // Update (for later use)
        this._projectUpdateObserver = updateObserver;

        // Return
        return this;
    },

    /**
     * Set page request subject. TODO Better implementation
     * @param {Subject} pageRequestSubject
     * @returns {undefined}
     */
    pageRequestSubject: function (pageRequestSubject) {
        // Paging action (same subject as reload)
        this._pageRender.pageRequestSubject(pageRequestSubject);
        // Build renderer onto current paging fragment area
        this._pageRender.applyPageRequestSubject(this._projectRecordsPagingFragment);
        // Return
        return this;
    },

    /**
     * Setup observables for page request
     * @param {Observable} screenIntializedObservable
     * @param {Observable} saveSuccessObservable
     * @param {Observable} deleteSuccessObservable
     * @returns {undefined}
     */
    observablesForPageRequest: function (screenIntializedObservable, saveSuccessObservable, deleteSuccessObservable) {
        // Apart from add and delete success, clicking on button Reload also included
        let projectRecordsPagingFragment = this._projectRecordsPagingFragment;
        let getRecordCountPerPageFunc = () => projectRecordsPagingFragment.find('#sltPagingRecordPerPage').val();

        this._pageRender
                .pageRequestObservable(Rx.Observable.fromEvent(this._btnReload, 'click').map(() => {
                    // Providing paging subject with page request form
                    return {
                        pageNumber: parseInt(projectRecordsPagingFragment.find('#txtPagingCurrent').val()),
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
                .pageRequestObservable(saveSuccessObservable.map(() => {
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
        return this;
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

    userTblHeadGenFunc: function (page) {
        return Tagger.tr()
                .th('Code').withClass('col-xs-2')
                .then()
                .th('Display name').withClass('col-xs-4')
                .then()
                .th('Created at').withClass('col-xs-2')
                .then()
                .th('Updated at').withClass('col-xs-2')
                .then()
                .th('Delete').withClass('col-xs-1')
                .then()
                .th('Update').withClass('col-xs-1')
                .then()
                .build();
    },

    userTblRowGenFunc: function (projectRecord) {
        return Tagger.tr()
                .td(projectRecord.code).withClass('col-xs-2')
                .then()
                .td(projectRecord.displayedName).withClass('col-xs-4')
                .then()
                .td(projectRecord.cDateTimeStamp).withClass('col-xs-2')
                .then()
                .td(projectRecord.mDateTimeStamp).withClass('col-xs-2')
                .then()
                .td().withClasses('col-xs-1 mo_checkbox_wrapper').innerTag('input').autoClose().withAttr('type', 'checkbox').withClass('mo_chkDelete').id('chkDelete_' + projectRecord.id).then()
                .then()
                .td().withClass('col-xs-1').innerTag('button').innerText('Update').withClass('mo_btnUpdate').id('btnAddUpdate_' + projectRecord.id).then()
                .then()
                .withAttr('project_id', projectRecord.id)
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
                .subscribe(this._projectUpdateObserver);
    },

    /**
     * Subscribe to the subject with params as checked user ids
     * @param {Observable} actionObservable
     * @param {String} checkedSelector
     * ¥@param {Subject} subject
     * @returns {undefined}
     */
    subscribeOneActionForCheckedProjects: function (actionObservable, checkedSelector, subject) {
        let pageFragment = this._projectRecordsPagingFragment;
        actionObservable.switchMap(e => Rx.Observable.from(pageFragment.find(checkedSelector).get()))
                .map(e => $(e).attr('id').split('_')[1])
                .map(idStr => parseInt(idStr))
                .scan((acc, one) => acc.concat(one), []) // Re-collect to array again. Do NOT use toArray since the stream has not been completed
                .subscribe(subject);
    }
};

/*----------------Add/Update project dialog----------------------*/

ProjectDetailDlg.prototype = Object.create(CommonDetailDlg.prototype);
ProjectDetailDlg.prototype.constructor = ProjectDetailDlg;

/**
 * Construct
 * @param {JQuery} dlg
 * @returns {ProjectDetailDlg}
 *
 */
function ProjectDetailDlg(dlg) {
    CommonDetailDlg.call(this, dlg, '#btnProjectAddUpdateDone');
    this._searchSubject = undefined;
}

/**
 * Set subjects for user-interactions
 * @param {Subject} saveSubject
 * @param {Subject} searchSubject
 * @returns {undefined}
 */
ProjectDetailDlg.prototype.setUserActionSubject = function (saveSubject, searchSubject) {
    this.setSaveSubject(saveSubject);
    this._searchSubject = searchSubject;

    // Attempts for search action
    this._userSearchBox = (new CommonSearchBoxFragmentRender())
            .searchInputPlaceHolder('User name')
            .searchObserver(this._searchSubject)
            .searchResultOptionEleGenerator((usr) => Tagger.option()
                        .id(usr.id)
                        .innerText(usr.name)
                        .build()
            )
            .build($('#frgSearchBox'));
};

/**
 * Create data for save form from the dialog. To be implemented by subclass
 * @param {JQuery} mdlProjectAddUpdate
 * @returns {Map}
 */
ProjectDetailDlg.prototype.createSaveData = function (mdlProjectAddUpdate) {
    return {
        code: mdlProjectAddUpdate.find('#txtProjectCode').val(),
        displayedName: mdlProjectAddUpdate.find('#txtProjectDisplayedName').val(),
        description: mdlProjectAddUpdate.find('#txtProjectDesc').val(),
        referScopeCode: this.getCheckedRadioButtonValue(mdlProjectAddUpdate.find('input:radio[name="rd_scope"]')),
        statusCode: this.getCheckedRadioButtonValue(mdlProjectAddUpdate.find('input:radio[name="rd_status"]')),
        userIds: []
    };
};

/**
 * Render data for add action to the dialog
 * @param {JQuery} mdlProjectAddUpdate
 * @param {Map} dataForAdd
 * @returns {undefined}
 */
ProjectDetailDlg.prototype.renderDataForAdd = function (mdlProjectAddUpdate, dataForAdd) {
    // Reset
    mdlProjectAddUpdate.find('#lblProjectDialogTitle').text('Input new project info');
    mdlProjectAddUpdate.find('#txtProjectCode').val('');
    mdlProjectAddUpdate.find('#txtProjectDisplayedName').val('');
    mdlProjectAddUpdate.find('#txtProjectDesc').val('');
    this.checkRadioButtonByValue(mdlProjectAddUpdate.find('input:radio[name="rd_scope"]'), ProjectReferScope.PUBLIC);
    this.checkRadioButtonByValue(mdlProjectAddUpdate.find('input:radio[name="rd_status"]'), ProjectStatus.ACTIVE);
};

/**
 * Render to view the record with detailed information. To be implemented by subclass
 * @param {JQuery} mdlProjectAddUpdate
 * @param {Map} projectDetails
 * @returns {undefined}
 */
ProjectDetailDlg.prototype.renderRecordDetailForUpdate = function (mdlProjectAddUpdate, projectDetails) {
    // Render project info
    mdlProjectAddUpdate.find('#lblProjectDialogTitle').text('Update project info');
    mdlProjectAddUpdate.find('#txtProjectCode').val(projectDetails.code);
    mdlProjectAddUpdate.find('#txtProjectDisplayedName').val(projectDetails.displayedName);
    mdlProjectAddUpdate.find('#txtProjectDesc').val(projectDetails.description);
    this.checkRadioButtonByValue(mdlProjectAddUpdate.find('input:radio[name="rd_scope"]'), projectDetails.referScope);
    this.checkRadioButtonByValue(mdlProjectAddUpdate.find('input:radio[name="rd_status"]'), projectDetails.status);
};

/**
 * Find radio button by value then check
 * @param {JQuery} radioGroup
 * @param {Number} val
 * @returns {undefined}
 */
ProjectDetailDlg.prototype.checkRadioButtonByValue = function (radioGroup, val) {
    radioGroup.filter((i, ele) => parseInt($(ele).val()) === val).prop('checked', 'checked');
};

/**
 * Get checked value
 * @param {type} radioGroup
 * @returns {undefined}
 */
ProjectDetailDlg.prototype.getCheckedRadioButtonValue = function (radioGroup) {
    return parseInt(radioGroup.filter(':checked').val());
};

/*----------------------------------------------------Observable, Subjects, Observers----------------------------------------------------*/
var UserActionSubjects = {
    /**
     * Initialize with web service
     * @param {Map} projectDetailDlg
     * @returns {undefined}
     */
    init: function (projectDetailDlg) {
        // Components wiring
        this._projectDetailDlg = projectDetailDlg;
        // Subjects
        // 1. Add subject
        this._addProjectSubject = {
            // TODO Handle error
            next: ProjectDetailDlg.prototype.showForAdd.bind(projectDetailDlg)
        };

        // 2. Other subjects depends on server response
        this._saveProjectSubject = new Rx.Subject();
        this._deleteProjectsSubject = new Rx.Subject();
        this._getProjectDetailsSubject = new Rx.Subject();

        // For search action
        this._userSearchAction = new Rx.Subject();
    },

    /**
     * Init by providing controller instance
     * @param {Map} serverResponseOberservers
     * @returns {undefined}
     */
    setupObserversForServerResponses: function (serverResponseOberservers) {
        // TODO Implement
        let ajaxObservableBuilder = MendelAjaxObservableBuilder.baseCRUDUrl(Urls.PROJECT_MGT_BASE_URL)
                .indexActionUrl(Urls.PROJECT_MGT_INDEX_ACTION)
                .saveActionUrl(Urls.PROJECT_MGT_ADDUPDATE_ACTION)
                .detailActionUrl(Urls.PROJECT_MGT_GETDETAIL_ACTION)
                .deleteActionUrl(Urls.PROJECT_MGT_DELETE_ACTION);

        let observerBuilder = MendelWebs.getDefaultAjaxResponseObserverBuilder();
        let createAjaxResponseFunc = MendelAjaxResponseObserverBuilder.prototype.createAjaxResponseObserver.bind(observerBuilder);

        // Get detail (before showing update dialog)
        this._getProjectDetailsSubject
                .switchMap(projectId => ajaxObservableBuilder.getDetailsRetrieveAJAXObservable(projectId))
                .subscribe(createAjaxResponseFunc(serverResponseOberservers.getRetrieveDetailsResponseObserver()));

        // Save (for both create and update)
        this._saveProjectSubject
                .switchMap(saveForm => ajaxObservableBuilder.getSaveAJAXObservable(saveForm))
                .subscribe(createAjaxResponseFunc(serverResponseOberservers.getSaveResponseObserver()));

        // Delete
        this._deleteProjectsSubject
                .switchMap(projectIdsToDelete => ajaxObservableBuilder.getDeleteAJAXObservable(projectIdsToDelete))
                .subscribe(createAjaxResponseFunc(serverResponseOberservers.getDeleteResponseObserver()));

        // User search
        let searchUrl = Urls.PROJECT_MGT_BASE_URL + '/' + Urls.PROJECT_MGT_SEARCH_USER_ACTION;
        this._userSearchAction
                .switchMap(userText => MendelAjaxObservableBuilder.createPostActionObservable(searchUrl,
                            {
                                userText: userText
                            }));
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
        this._pageRequestSubject = (new Rx.Subject()).switchMap(
                indexForm => MendelAjaxObservableBuilder.baseCRUDUrl(Urls.PROJECT_MGT_BASE_URL)
                    .indexActionUrl(Urls.PROJECT_MGT_INDEX_ACTION)
                    .getIndexAJAXObservable(indexForm)
        );
        return this;
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
        this._pageRequestAfterSaveSuccess = new Rx.Subject();
        this._pageRequestAfterDeleteSuccess = new Rx.Subject();
        return this;
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
    // 1. User action observable
    let userActionSubjects = UserActionSubjects;

    // 2. View internal observables (paging)
    let pageRequestObservables = PageRequestObservables.init();

    // 4. View internal subject (paging)
    let pageRequestSubject = PageRequestSubject.init();

    // 4. Views
    let projectDetailDialog = new ProjectDetailDlg($('#mdlProjectAddUpdate'));
    userActionSubjects.init(projectDetailDialog);
    projectDetailDialog.setUserActionSubject(userActionSubjects._saveProjectSubject, userActionSubjects._userSearchAction);

    ProjectRecordsPageFragment.init($('#frgPaging'))
            .userActionSubject(
                    userActionSubjects._addProjectSubject,
                    userActionSubjects._getProjectDetailsSubject,
                    userActionSubjects._deleteProjectsSubject
                    )
            .pageRequestSubject(pageRequestSubject._pageRequestSubject)
            .observablesForPageRequest(
                    pageRequestObservables._pageRequestAfterScreenInitialized,
                    pageRequestObservables._pageRequestAfterSaveSuccess,
                    pageRequestObservables._pageRequestAfterDeleteSuccess
                    );

    // 5. Server observers
    // TODO Variable for add, update, delete success subject
    let serverResponseObservers = new CommonCRUDServerResponseObserver(projectDetailDialog,
            pageRequestObservables._pageRequestAfterSaveSuccess,
            pageRequestObservables._pageRequestAfterDeleteSuccess);
    userActionSubjects.setupObserversForServerResponses(serverResponseObservers);
}

/**
 * Load initial data
 * @returns {undefined}
 */
function loadInitialData() {
    // Here subject acts as an observer
    PageRequestObservables._pageRequestAfterScreenInitialized.next();
}
