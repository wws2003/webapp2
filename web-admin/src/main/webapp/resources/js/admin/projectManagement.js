/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global Tagger, MendelApp */

var Rx = Rx || {};

/*----------------------------------------------------Constants----------------------------------------------------*/
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
     * @returns {undefined}
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
        this._projectUpdateSubject = undefined;
    },

    /**
     * Set subjects for user-interactions
     * @param {Subject} addObserver
     * @param {Subject} updateObserver
     * @param {Subject} deleteObserver
     * @returns {undefined}
     */
    setUserActionSubject: function (addObserver, updateObserver, deleteObserver) {
        // Add
        Rx.Observable.fromEvent(this._btnAdd, 'click')
                .subscribe(addObserver);

        // Delete
        this.subscribeOneActionForCheckedProjects(Rx.Observable.fromEvent(this._btnDelete, 'click'),
                '.mo_chkDelete:checked',
                deleteObserver);

        // TODO Implement other actions
    },

    /**
     * Set page request subject. TODO Better implementation
     * @param {Subject} pageRequestSubject
     * @returns {undefined}
     */
    setPageRequestSubject: function (pageRequestSubject) {

    },

    /**
     * Setup observables for page request
     * @param {Observable} screenIntializedObservable
     * @param {Observable} addSuccessObservable
     * @param {Observable} deleteSuccessObservable
     * @returns {undefined}
     */
    setObservablesForPageRequest: function (screenIntializedObservable, addSuccessObservable, deleteSuccessObservable) {

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
                .th('Document count').withClass('col-xs-4')
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
                .td(projectRecord.documentCount).withClass('col-xs-4')
                .then()
                .td().withClasses('col-xs-1 mo_checkbox_wrapper').innerTag('input').autoClose().withAttr('type', 'checkbox').withClass('mo_chkDelete').id('chkDelete_' + projectRecord.id).then()
                .then()
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
                .subscribe(this._projectUpdateSubject);
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

/**
 * Map for project details
 * @type Map
 */
var ProjectDetailDlg = {
    /**
     * Initialize
     * @param {JQuery} mdlProjectAddUpdate
     * @returns {undefined}
     */
    init: function (mdlProjectAddUpdate) {
        this._mdlProjectAddUpdate = mdlProjectAddUpdate;
    },

    /**
     * Set subjects for user-interactions
     * @param {Subject} saveSubject
     * @param {Subject} searchSubject
     * @returns {undefined}
     */
    setUserActionSubject: function (saveSubject, searchSubject) {

    },

    /**
     * Show modal for adding new project
     * @returns {undefined}
     */
    showForAdd: function () {
        let dlg = this._mdlProjectAddUpdate;
        // TODO Implement
        dlg.modal('show');
    },

    /**
     * Show modal with project detail
     * @param {Map} projectDetail
     * @returns {undefined}
     */
    showForUpdate: function (projectDetail) {
        let dlg = this._mdlProjectAddUpdate;
        // TODO Implement
        dlg.modal('show');
    },

    /**
     * Hide
     * @returns {undefined}
     */
    hide: function () {
        let dlg = this._mdlProjectAddUpdate;
        // TODO Implement
        dlg.modal('hide');
    },

    /**
     * Render error (including disabling elements)
     * @param {Array} errorMessages
     * @returns {undefined}
     */
    renderErrors: function (errorMessages) {
        // TODO Implement
    }
};

/*----------------------------------------------------Observable, Subjects, Observers----------------------------------------------------*/
var UserActionSubjects = {
    /**
     * Initialize with web service
     * @param {Map} projectMgtWebService
     * @returns {undefined}
     */
    init: function (projectMgtWebService) {
        // Service wiring
        this._projectMgtWebService = projectMgtWebService;
        // Subjects
        this._addProjectSubject = new Rx.Subject();
        this._updateProjectSubject = new Rx.Subject();
        this._deleteProjectsSubject = new Rx.Subject();
        this._getProjectDetailsSubject = new Rx.Subject();
    }
};

/**
 * Observers for server response
 * @type Map
 */
var ServerResponseObservers = {
    // TODO Implement
};

/*--------------------------------------------------Service------------------------------------------------*/
/**
 * Instance to handle WEB requests
 * @type
 */
var ProjectMgtWebService = {

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
    // TODO Implement
    let webService = ProjectMgtWebService;

    // 2. User action observable
    let userActionSubjects = UserActionSubjects;
    userActionSubjects.init(webService);

    // 3. View internal observables (paging)
    // 4. View internal subject (paging)
    // 5. Views
    let projectRecordsPageFragment = ProjectRecordsPageFragment;
    let projectDetailDialog = ProjectDetailDlg;
    projectRecordsPageFragment.init($('#frgPaging'));
    projectDetailDialog.init($('#mdlProjectAddUpdate'));
    projectRecordsPageFragment.setUserActionSubject(userActionSubjects._addProjectSubject, userActionSubjects._updateProjectSubject, userActionSubjects._deleteProjectsSubject);
}

/**
 * Load initial data
 * @returns {undefined}
 */
function loadInitialData() {
    // TODO Implement
}