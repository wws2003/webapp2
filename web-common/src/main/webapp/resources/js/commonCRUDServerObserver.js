/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global MendelDialog */

/**
 * Base class of observers CRUD actions
 * @returns {CommonCRUDServerObserver}
 */
function CommonCRUDServerObserver() {

}

/**
 * Subject for get details action
 * @type Map
 */
CommonCRUDServerObserver.prototype.getRetrieveUserDetailsResponseObserver = function () {
    let successResponseFunc = UserDetailDlg.prototype.showForUpdate.bind(this._userDetailDlg);
    return {
        // TODO Handle error
        next: (response) => successResponseFunc(response.resultObject)
    };
};

/**
 * Subject for user save action
 * @type Map
 */
CommonCRUDServerObserver.prototype.getSaveResponseObserver = function () {
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
};

/**
 * Subject for user delete action
 * @type Map
 */
CommonCRUDServerObserver.prototype.getDeleteResponseObserver = function () {
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
};
