/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global MendelDialog */
var CommonDetailDlg = CommonDetailDlg || {};

/**
 * Base class of observers for CRUD actions
 * @param {CommonDetailDlg} detailDlg
 * @param {FunctionMaps} saveSuccessSubject
 * @param {FunctionMaps} deleteSuccessSubject
 * @param {FunctionMaps} saveFailedSubject
 * @param {FunctionMaps} deleteFailedSubject
 * @returns {CommonCRUDServerResponseObserver}
 */
function CommonCRUDServerResponseObserver(detailDlg, saveSuccessSubject, deleteSuccessSubject, saveFailedSubject, deleteFailedSubject) {
    // View elements
    this._detailDlg = detailDlg;
    // Subject for save, delete actions
    this._saveSuccessSubject = saveSuccessSubject;
    this._deleteSuccessSubject = deleteSuccessSubject;
    this._saveFailedSubject = saveFailedSubject;
    this._deleteFailedSubject = deleteFailedSubject;
}

/**
 * Subject for get details action
 * @type Map
 */
CommonCRUDServerResponseObserver.prototype.getRetrieveDetailsResponseObserver = function () {
    let successResponseFunc = CommonDetailDlg.prototype.showForUpdate.bind(this._detailDlg);
    return {
        // TODO Handle error
        next: (response) => successResponseFunc(response.resultObject)
    };
};

/**
 * Subject for save action
 * @type Map
 */
CommonCRUDServerResponseObserver.prototype.getSaveResponseObserver = function () {
    // TODO Implement properly, handle messages and error
    let saveSuccessSubject = this.getSubjectOrElseDoNothing(this._saveSuccessSubject);
    let saveFailedSubject = this.getSubjectOrElseDoNothing(this._saveFailedSubject);
    let detailDlg = this._detailDlg;
    return  {
        next: (response) => {
            detailDlg.hide();
            if (response.success) {
                // Show message dialog after hiding detail dialog, then notify subject for save success
                MendelDialog.info('Message', response.successMessages[0], () => saveSuccessSubject.next());
            } else {
                // Show error message
                MendelDialog.error('Message', response.errorMessages[0], () => saveFailedSubject.next());
            }
        }
    };
};

/**
 * Subject for delete action
 * @type Map
 */
CommonCRUDServerResponseObserver.prototype.getDeleteResponseObserver = function () {
    // TODO Implement properly, handle messages and error
    let deleteSuccessSubject = this.getSubjectOrElseDoNothing(this._deleteSuccessSubject);
    let deleteFailedSubject = this.getSubjectOrElseDoNothing(this._deleteFailedSubject);
    return  {
        next: (response) => {
            // Show dialog after hide dialog
            if (response.success) {
                MendelDialog.info('Message', response.successMessages[0], () => deleteSuccessSubject.next());
            } else {
                MendelDialog.error('Message', response.errorMessages[0], () => deleteFailedSubject.next());
            }
        }
    };
};

/*----------------------------Private methods---------------------------*/

/**
 * Get the next() function or a dummy function doing nothing if next() does not exists
 * @param {FunctionMap} subject
 * @returns {undefined}
 */
CommonCRUDServerResponseObserver.prototype.getSubjectOrElseDoNothing = function (subject) {
    if (!subject || !subject.next) {
        return {
            next: () => {
                // Do nothing if nothing set
            }
        };
    }
    return subject;
};