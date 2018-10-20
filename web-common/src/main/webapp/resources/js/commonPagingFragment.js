/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global MendelDialog, MendelWebs */

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
     * Function to decorate rendered table
     *
     * @param {JQuery} tableEle
     * @returns {undefined}
     */
    this._renderedTableDecorateFunc = function (tableEle) {
        // Default do nothing
    };
    /**
     * Options for record number per page
     */
    this._recordCountOptions = [10, 20, 50, 100];
    /**
     * Records-control area elements
     */
    this._recordCtrlAreaEles = [];
    /**
     * Table addition CSS-classes
     */
    this._tableClasses = '';
    /**
     * Page request subject
     */
    this._pageRequestSubject = null;
    /**
     * Page request observables
     */
    this._pageRequestObservables = [];
    // Streamed observable for page request
    /**
     * Subscription for page error observable
     */
    this._pagingErrorSubscription = null;
    /**
     * Subscription for page request
     */
    this._pageRequestSubscription = null;
    /**
     * Subscription for page data response
     */
    this._pageResponseSubscription = null;
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
 * Set function to decorate table after rendered
 * @param {Function} renderedTableDecorateFunc
 * @returns {CommonPagingFragmentRender.prototype}
 */
CommonPagingFragmentRender.prototype.renderedTableDecorateFunc = function (renderedTableDecorateFunc) {
    this._renderedTableDecorateFunc = renderedTableDecorateFunc;
    return this;
};


/**
 * Set page request subject
 * @param {Subject} pageRequestSubject Should be a subject for data promise (such as result of switchMap())
 * @returns {CommonPagingFragmentRender.prototype}
 */
CommonPagingFragmentRender.prototype.pageRequestSubject = function (pageRequestSubject) {
    this._pageRequestSubject = pageRequestSubject;
    return this;
};

/**
 * Add observable for reload subject (must be called after pageRequestSubject?)
 * @param {Observable} observable
 * @returns {undefined}
 */
CommonPagingFragmentRender.prototype.pageRequestObservable = function (observable) {
    // For outside observables, currently do not unsubscribe
    this._pageRequestObservables.push(observable);
    observable.subscribe(this._pageRequestSubject);
    return this;
};

/*---------------------------------------Methods to effectively render. Ideally outside instance should only care these methods------------------------------------------*/

/**
 * Apply this instance attributes, properties into records control area
 * @param {JQuery} frgPagingEle
 * @returns {undefined}
 */
CommonPagingFragmentRender.prototype.buildRecordsCtrlArea = function (frgPagingEle) {
    // Default records count per page options
    let recordCountSelect = frgPagingEle.find('#sltPagingRecordPerPage');
    Rx.Observable.from(this._recordCountOptions)
            .map(rec => '<option val=' + rec + '>' + rec + '</option>')
            .scan((accOpts, opt) => accOpts + opt, '')
            .defaultIfEmpty('')
            .subscribe(accOpts => recordCountSelect.html(accOpts));

    // Records control area
    let recordsCtrlArea = frgPagingEle.find('#dvRecordCtrlArea');
    recordsCtrlArea.html(this._recordCtrlAreaEles.reduce((acc, cur) => acc + cur, ''));
};

/**
 * Apply this instance attributes, properties into specified area
 * @param {JQuery} frgPagingEle
 * @returns {undefined}
 */
CommonPagingFragmentRender.prototype.applyPageRequestSubject = function (frgPagingEle) {
    // Page request subject
    // Observer for response inside common paging fragment area
    let observerBuilder = MendelWebs.getDefaultAjaxResponseObserverBuilder();

    this._pageResponseSubscription && this._pageResponseSubscription.unsubscribe();
    this._pageResponseSubscription = this._pageRequestSubject.subscribe(observerBuilder.createAjaxResponseObserver(this.createObserverForPageResponse(frgPagingEle)));

    return this;
};

/*---------------------------------------Private methods------------------------------------------*/
/**
 * Clear data in paging fragment area
 * @param {JQuery} frgPagingEle
 * @returns {undefined}
 */
CommonPagingFragmentRender.prototype.renderEmpty = function (frgPagingEle) {
    // Nav bar
    let navBarEle = frgPagingEle.find('#dvPagingNavBar');
    navBarEle.find('input').eleDisable();
    navBarEle.find('button').eleDisable();

    // Content table body
    let tableEle = frgPagingEle.find('#tblPagingContent');
    tableEle.find('tbody').html('');
};

/**
 * Actual do the rendering for error message
 * @param {JQuery} frgPagingEle
 * @param {String} errorMessageHTML
 * @returns {undefined}
 */
CommonPagingFragmentRender.prototype.renderError = function (frgPagingEle, errorMessageHTML) {
    // TODO Any disable process ?
    // Show messages
    frgPagingEle.find('#dvPagingMessage').html(errorMessageHTML);
    return true;
};

/**
 * Actual do the rendering for page
 * @param {JQuery} frgPagingEle
 * @param {Map} page
 * @returns {CommonPagingFragmentRender.prototype}
 */
CommonPagingFragmentRender.prototype.renderPage = function (frgPagingEle, page) {
    // Initialize
    let self = this;
    let navBarEle = frgPagingEle.find('#dvPagingNavBar');
    let lblPagingPageCount = frgPagingEle.find('#lblPagingPageCount');
    let tableEle = frgPagingEle.find('#tblPagingContent');

    // Nav bar
    lblPagingPageCount.text(page.totalPages); // From zero-based to one-based
    let txtPageNo = frgPagingEle.find('#txtPagingCurrent');
    txtPageNo.val(page.number + 1);
    this.setEnableState(navBarEle.find('#btnPagingFirst'), !(page.first));
    this.setEnableState(navBarEle.find('#btnPagingPrev'), (page.number > 0));
    this.setEnableState(navBarEle.find('#btnPagingNext'), (page.number < page.totalPages - 1));
    this.setEnableState(navBarEle.find('#btnPagingLast'), !(page.last));

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
    this.setupPagingEvents(frgPagingEle, page);

    // Call decorate function on the records table
    this._renderedTableDecorateFunc(tableEle);

    return true;
};

/**
 * Setup paging events (next/prev, first/last page, change records count per page, change page number)
 * @param {JQuery} frgPagingEle
 * @param {Map} page
 * @returns {undefined}
 */
CommonPagingFragmentRender.prototype.setupPagingEvents = function (frgPagingEle, page) {
    // Initialize
    let navBarEle = frgPagingEle.find('#dvPagingNavBar');
    let txtPageNo = frgPagingEle.find('#txtPagingCurrent');
    let recordCountSelect = frgPagingEle.find('#sltPagingRecordPerPage');
    let btnFirstPage = navBarEle.find('#btnPagingFirst');
    let btnPrevPage = navBarEle.find('#btnPagingPrev');
    let btnNextPage = navBarEle.find('#btnPagingNext');
    let btnLastPage = navBarEle.find('#btnPagingLast');
    let currentPage = page.number + 1; // 1-based
    let lastPage = page.totalPages; // 1-based

    // Go to page
    let pageTransitionObservable = Rx.Observable.merge(
            Rx.Observable.fromEvent(btnFirstPage, 'click').map(e => 1),
            Rx.Observable.fromEvent(btnPrevPage, 'click').map(e => currentPage - 1),
            Rx.Observable.fromEvent(btnNextPage, 'click').map(e => currentPage + 1),
            Rx.Observable.fromEvent(btnLastPage, 'click').map(e => lastPage),
            )
            .map(targetPageNo => {
                return {
                    pageNumber: targetPageNo,
                    recordCountPerPage: parseInt(recordCountSelect.val())
                };
            });

    // {PageNo, records count per page}
    let pagingInfoChangeObservable = Rx.Observable
            .fromEvent(recordCountSelect, 'change')
            .merge(Rx.Observable.fromEvent(txtPageNo, 'blur'))
            .map(e => [txtPageNo.val(), recordCountSelect.val()])
            .map(pair => {
                return {
                    pageNumber: parseInt(pair[0]),
                    recordCountPerPage: parseInt(pair[1])
                };
            });

    // Merged observable (store previous value to rollback in the case of error)
    let mergedPageRequestObservable = Rx.Observable
            .merge(pageTransitionObservable, pagingInfoChangeObservable);

    // Setup paging actions
    this.setupPagingObservable(frgPagingEle, lastPage, mergedPageRequestObservable);
};

/**
 * Create observer object for page request action
 * @param {JQuery} frgPagingEle
 * @returns {Observer}
 */
CommonPagingFragmentRender.prototype.createObserverForPageResponse = function (frgPagingEle) {
    let renderFunc = CommonPagingFragmentRender.prototype.renderPage.bind(this, frgPagingEle);
    let emptyFunc = CommonPagingFragmentRender.prototype.renderEmpty.bind(this, frgPagingEle);
    let errorFunc = CommonPagingFragmentRender.prototype.renderError.bind(this, frgPagingEle);
    return {
        next: response => {
            let isSuccess = response.success;
            if (isSuccess) {
                let page = response.resultObject;
                renderFunc(page);
            } else {
                emptyFunc();
                let erroMsg = response.errorMessages.reduce((acc, cur) => acc + cur + '<br>', '');
                errorFunc(erroMsg);
            }
        },
        error: err => {
            // TODO Handle errors properly
            console.log(err);
            emptyFunc();
            errorFunc('Can not receive response properly');
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

/**
 * Setup paging function by subscribing paging observable
 * @param {JQuery} frgPagingEle
 * @param {Number} pageCount
 * @param {Observable} pagingObservable
 * @returns {undefined}
 */
CommonPagingFragmentRender.prototype.setupPagingObservable = function (frgPagingEle, pageCount, pagingObservable) {
    // Initial process
    this._pageRequestSubscription && this._pageRequestSubscription.unsubscribe();
    this._pagingErrorSubscription && this._pagingErrorSubscription.unsubscribe();
    let txtPageNo = frgPagingEle.find('#txtPagingCurrent');
    let recordCountSelect = frgPagingEle.find('#sltPagingRecordPerPage');

    // Branch into normal and error stream
    let branchedPagingObservables = pagingObservable
            .scan((prevcur, latest) => [prevcur[1], latest], [null, {pageNumber: parseInt(txtPageNo.val()), recordCountPerPage: parseInt(recordCountSelect.val())}])
            .partition(
                    prevcur => prevcur[1].pageNumber > 0 && prevcur[1].pageNumber <= pageCount
            );
    let normalPagingObservable = branchedPagingObservables[0];
    let errorPagingObservable = branchedPagingObservables[1];

    // Re-subscribe to page request subject
    // Filter distinct manually as distinctUntilChanged does not work since the subscription is reset after each load !
    this._pageRequestSubscription = normalPagingObservable
            .filter((prevcur) => !prevcur[0] || (prevcur[0].pageNumber !== prevcur[1].pageNumber || prevcur[0].recordCountPerPage !== prevcur[1].recordCountPerPage))
            .map(prevcur => prevcur[1])
            .subscribe(this._pageRequestSubject);

    // Error
    this._pagingErrorSubscription = errorPagingObservable.subscribe(e => {
        let prev = e[0];
        let cur = e[1];
        MendelDialog.error('Mendel pager', 'Invalid page number [' + cur.pageNumber + ', ' + cur.recordCountPerPage + ']', () => {
            txtPageNo.val(prev.pageNumber);
            // Try to trigger event.
            txtPageNo.focus();
            txtPageNo.blur();
        });
    });
};
