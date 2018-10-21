/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global Tagger */

var Rx = Rx || {};

/*----------------------------------------------------Constants----------------------------------------------------*/
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
        this._userRecordsPagingFragment = projectRecordsPagingFragment;
        this._btnReload = projectRecordsPagingFragment.find('#' + RecordsCtrlArea.BTN_RELOAD.id);
        this._btnAdd = projectRecordsPagingFragment.find('#' + RecordsCtrlArea.BTN_ADD.id);
        this._btnDelete = projectRecordsPagingFragment.find('#' + RecordsCtrlArea.BTN_DELETE.id);

        // Subjects (for events fired in this view)
        this._userUpdateSubject = undefined;
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
    }
};

/**
 * Map for project details
 * @type Map
 */
var ProjectDetailDlg = {

};

/*----------------------------------------------------Observable, Subjects, Observers----------------------------------------------------*/


/*--------------------------------------------------Service------------------------------------------------*/


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
    // Views
    let projectRecordsPageFragment = ProjectRecordsPageFragment;
    projectRecordsPageFragment.init($('#frgPaging'));
}

/**
 * Load initial data
 * @returns {undefined}
 */
function loadInitialData() {
    // TODO Implement
}
