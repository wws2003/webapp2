/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var Rx = Rx || {};

function CommonPagingFragmentRender() {
    /**
     * Function to generate head of record table
     * @param {Map} page
     * @returns {$}
     */
    this._headerGenFunc = function (page) {
        // Return empty head
        return $('<tr><th></th></tr>');
    };
    /**
     * Function to generate body of record table
     * @param {Map} record
     * @returns {$}
     */
    this._rowGenFunc = function (record) {
        // Return empty row
        return $('<tr><td></td></tr>');
    };
    /**
     * Options for record number per page
     */
    this._recordCountOptions = [20, 50, 100];
    /**
     * Function to request page
     */
    this._pageRequestFunc = null;
    // Streamed observable for page request
    /**
     * Subscription for page request
     */
    this._pageRequestSubscription = null;
}

CommonPagingFragmentRender.prototype.recordCountOptions = function (recordCountOptions) {
    this._recordCountOptions = recordCountOptions;
    return this;
};

CommonPagingFragmentRender.prototype.headerGenFunc = function (headerGenFunc) {
    this._headerGenFunc = headerGenFunc;
    return this;
};

CommonPagingFragmentRender.prototype.rowGenFunc = function (rowGenFunc) {
    this._rowGenFunc = rowGenFunc;
    return this;
};

CommonPagingFragmentRender.prototype.pageRequestFunc = function (pageRequestFunc) {
    this._pageRequestFunc = pageRequestFunc;
    return this;
};

/**
 * Actual do the rendering
 * @param {JQuery} frgPagingEle
 * @param {Map} page
 * @returns {CommonPagingFragmentRender.prototype}
 */
CommonPagingFragmentRender.prototype.render = function (frgPagingEle, page) {
    // Initialize
    let self = this;
    let navBarEle = frgPagingEle.find('#dvPagingNavBar');
    let tableEle = frgPagingEle.find('#tblPagingContent');
    if (this._pageRequestSubscription) {
        // Unsubscribe what observer ?
        this._pageRequestSubscription.unsubscribe();
    }

    // Nav bar
    $('#lblPagingPageCount').text(page.pageNumber + 1); // From zero-based to one-based
    let txtPageNo = frgPagingEle.find('#txtPagingCurrent');
    txtPageNo.val(page.currentPage);
    this.setEnableState(navBarEle.find('#btnPagingFirst'), !(page.first));
    this.setEnableState(navBarEle.find('#btnPagingPrev'), (page.pageNumber > 0));
    this.setEnableState(navBarEle.find('#btnPagingNext'), (page.pageNumber < page.totalPages - 1));
    this.setEnableState(navBarEle.find('#btnPagingLast'), !(page.last));

    // Record count options
    let recordCountSelect = frgPagingEle.find('#sltPagingRecordPerPage');
    Rx.Observable.from(this._recordCountOptions)
            .map(rec => '<option val=' + rec + '>' + rec + '</option>')
            .scan((accOpts, opt) => accOpts + opt, '')
            .subscribe(accOpts => recordCountSelect.html(accOpts));

    // Content table header
    tableEle.find('thead').html(this._headerGenFunc(page));

    // Content table body
    Rx.Observable.from(page.content)
            .map(rec => self._rowGenFunc(rec))
            .scan((accTrs, tr) => accTrs + tr, '')
            .subscribe(trs => tableEle.find('tbody').html(trs));

    // Events
    this._pageRequestSubscription = Rx.Observable
            .fromEvent(recordCountSelect, 'change')
            .merge(Rx.Observable.fromEvent(txtPageNo, 'blur'))
            .map(e => [txtPageNo.val(), recordCountSelect.val()])
            .subscribe(this.createObserverForPageRequest());

    return true;
};

/*---------------------------------------Private methods------------------------------------------*/

/**
 * Create observer object for page request action
 * @returns {Observer}
 */
CommonPagingFragmentRender.prototype.createObserverForPageRequest = function () {
    // TODO Implement
    return {
        next: e => {
            console.log(e);
        },
        error: err => {
            console.log(err);
        },
        complete: () => {

        }
    };
};

/**
 * Enable/Disable element
 * @param {JQuery} ele
 * @param {Boolean} isEnabled
 * @returns {undefined}
 */
CommonPagingFragmentRender.prototype.setEnableState = function (ele, isEnabled) {
    if (isEnabled) {
        ele.eleEnable();
    } else {
        ele.eleDisable();
    }
};

