/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var Rx = Rx || {};

/**
 * Construct dialog element
 * @param {JQuery} dlg
 * @param {String} btnSaveSelector
 * @returns {CommonDetailDlg}
 */
function CommonDetailDlg(dlg, btnSaveSelector) {
    // Dialog element
    this._mdlAddUpdate = dlg;
    // Observers and subscriptions
    let self = this;
    this._infoFormObservable = Rx.Observable.fromEvent(dlg.find(btnSaveSelector), 'click')
            .map(ev => {
                return self.createSaveData(dlg);
            });
    this._infoFormSubscription = undefined;
    this._saveSubject = undefined;
}

/**
 * Set save subject (for both add and update)
 * @param {Subject} saveSubject
 * @returns {undefined}
 */
CommonDetailDlg.prototype.setSaveSubject = function (saveSubject) {
    this._saveSubject = saveSubject;
};

/**
 * Show modal for add action
 * @param {Map} dataForAdd Any data needed for add action
 * @returns {undefined}
 */
CommonDetailDlg.prototype.showForAdd = function (dataForAdd) {
    let mdlAddUpdate = this._mdlAddUpdate;
    // Render
    this.renderDataForAdd(this._mdlAddUpdate, dataForAdd);
    // Re-construct subsription
    this.modifySubscription(this.createExtInfo());
    // Show up
    mdlAddUpdate.modal('show');
};

/**
 * Show modal for update/edit action
 * @param {Map} recordDetails Detailed information of the record
 * @param {Map} dataForUpdate Any data needed for update action apart from the record detail
 * @returns {undefined}
 */
CommonDetailDlg.prototype.showForUpdate = function (recordDetails, dataForUpdate) {
    let mdlAddUpdate = this._mdlAddUpdate;
    // Render
    this.renderRecordDetailForUpdate(this._mdlAddUpdate, recordDetails);
    // Re-construct subsription
    this.modifySubscription(this.createExtInfo(recordDetails));
    // Show up
    mdlAddUpdate.modal('show');
};

/**
 * Hide the modal
 * @returns {undefined}
 */
CommonDetailDlg.prototype.hide = function () {
    this._mdlAddUpdate.modal('hide');
};

/**
 * Render errors
 * @param {Array} errorMessages
 * @returns {undefined}
 */
CommonDetailDlg.prototype.renderErrors = function (errorMessages) {
    // TODO Implement
};

/**
 * Change the subscription for each time add/update
 * @param {Map} extInfo
 * @returns {undefined}
 */
CommonDetailDlg.prototype.modifySubscription = function (extInfo) {
    this._infoFormSubscription && this._infoFormSubscription.unsubscribe();
    this._infoFormSubscription = this._infoFormObservable
            .map((inforForm) => {
                return $.extend({}, inforForm, extInfo);
            })
            .subscribe(this._saveSubject);
};

/*--------------------------Methods to be implemented in subclass-----------------------*/
/**
 * Create data for save form from the dialog. To be implemented by subclass
 * @param {JQuery} dlg
 * @returns {Map}
 */
CommonDetailDlg.prototype.createSaveData = function (dlg) {
    return {};
};

/**
 * Render data for add action to the dialog
 * @param {JQuery} dlg
 * @param {Map} dataForAdd
 * @returns {undefined}
 */
CommonDetailDlg.prototype.renderDataForAdd = function (dlg, dataForAdd) {
    // Default do nothing
};

/**
 * Render to view the record with detailed information. To be implemented by subclass
 * @param {JQuery} dlg
 * @param {Map} recordDetails
 * @returns {undefined}
 */
CommonDetailDlg.prototype.renderRecordDetailForUpdate = function (dlg, recordDetails) {
    // Default do nothing
};

/**
 * Create extention information rather than from input fields
 * @param {Map} recordDetails
 * @returns {undefined}
 */
CommonDetailDlg.prototype.createExtInfo = function (recordDetails) {
    // Default return empty map
    return {};
};