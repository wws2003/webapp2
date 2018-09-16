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
        return '<tr><th></th></tr>';
    };
    /**
     * Function to generate body of record table
     * @param {Map} record
     * @returns {$}
     */
    this._rowGenFunc = function (record) {
        // Return empty row
        return '<tr><td></td></tr>';
    };
    /**
     * Options for record number per page
     */
    this._recordCountOptions = [20, 50, 100];
    /**
     * Records-control area elements
     */
    this._recordCtrlAreaEles = [];
    /**
     * Table addition CSS-classes
     */
    this._tableClasses = '';
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

/**
 * Set options for record per pages
 * @param {Array} recordCountOptions
 * @returns {CommonPagingFragmentRender.prototype}
 */
CommonPagingFragmentRender.prototype.recordCountOptions = function (recordCountOptions) {
    this._recordCountOptions = recordCountOptions;
    return this;
};

/**
 * Set CSS for table elements (apart from default ones). thead and tbody elements style should depend on these classes
 * @param {String} tableClasses (space-separated)
 * @returns {CommonPagingFragmentRender.prototype}
 */
CommonPagingFragmentRender.prototype.tableClass = function (tableClasses) {
    this._tableClasses = tableClasses;
    return this;
};

/**
 * Add one element into records control area
 * @param {String} eleHtml
 * @returns {undefined}
 */
CommonPagingFragmentRender.prototype.withRecordsCtrlElement = function (eleHtml) {
    this._recordCtrlAreaEles.push(eleHtml);
    return this;
};

/**
 * Set function to generate table thead
 * @param {Function} headerGenFunc
 * @returns {CommonPagingFragmentRender.prototype}
 */
CommonPagingFragmentRender.prototype.headerGenFunc = function (headerGenFunc) {
    this._headerGenFunc = headerGenFunc;
    return this;
};

/**
 * Set function to generate table row
 * @param {Function} rowGenFunc
 * @returns {CommonPagingFragmentRender.prototype}
 */
CommonPagingFragmentRender.prototype.rowGenFunc = function (rowGenFunc) {
    this._rowGenFunc = rowGenFunc;
    return this;
};

/**
 * Set function to request page data (after changing page number of records number per page)
 * @param {type} pageRequestFunc
 * @returns {CommonPagingFragmentRender.prototype}
 */
CommonPagingFragmentRender.prototype.pageRequestFunc = function (pageRequestFunc) {
    this._pageRequestFunc = pageRequestFunc;
    return this;
};

CommonPagingFragmentRender.prototype.pageRequestFuncAsFetchFromUrl = function (fetchUrl) {
    // TODO Implement
    return this;
};

/**
 * Show elements in records control area
 * @param {JQuery} frgPagingEle
 * @returns {undefined}
 */
CommonPagingFragmentRender.prototype.renderRecordCtrlArea = function (frgPagingEle) {
    let recordsCtrlArea = frgPagingEle.find('#dvRecordCtrlArea');

    // Records control area
    recordsCtrlArea.html(this._recordCtrlAreaEles.reduce((acc, cur) => acc + cur, ''));
};

/**
 * Actual do the rendering
 * @param {JQuery} frgPagingEle
 * @param {Map} page
 * @returns {CommonPagingFragmentRender.prototype}
 */
CommonPagingFragmentRender.prototype.renderPage = function (frgPagingEle, page) {
    // Initialize
    let self = this;
    let navBarEle = frgPagingEle.find('#dvPagingNavBar');
    let tableEle = frgPagingEle.find('#tblPagingContent');

    // Unsubscribe observer
    this._pageRequestSubscription && this._pageRequestSubscription.unsubscribe();

    // Nav bar
    $('#lblPagingPageCount').text(page.totalPages); // From zero-based to one-based
    let txtPageNo = frgPagingEle.find('#txtPagingCurrent');
    txtPageNo.val(page.number + 1);
    this.setEnableState(navBarEle.find('#btnPagingFirst'), !(page.first));
    this.setEnableState(navBarEle.find('#btnPagingPrev'), (page.hasPrevious));
    this.setEnableState(navBarEle.find('#btnPagingNext'), (page.hasNext));
    this.setEnableState(navBarEle.find('#btnPagingLast'), !(page.last));

    // Record count options
    let recordCountSelect = frgPagingEle.find('#sltPagingRecordPerPage');
    Rx.Observable.from(this._recordCountOptions)
            .map(rec => '<option val=' + rec + '>' + rec + '</option>')
            .scan((accOpts, opt) => accOpts + opt, '')
            .subscribe(accOpts => recordCountSelect.html(accOpts));

    // Set table styling classes
    tableEle.addClass(this._tableClasses);

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
            .distinctUntilChanged((pair1, pair2) => (pair1[0] === pair2[0] && pair1[1] === pair2[1])) // Do not fire event without any change
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

