/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global MendelWebs */

var Rx = Rx || {};

// Imagination
// render.searchInputPlaceHolder('User')
//      .searchOnTyping()
//      .searchSubject(new Subject().switchMap('SearchUrl'))
//      .searchResultOptionEleGenerator((e) => return '<option>e</option>')
//      .selectedSearchResultEleGenerator((e) => return '<tag>e</tag>')
//

function CommonSearchBoxFragmentRender() {
    this._searchTextBoxPlaceHolder = 'Search..';
    this._searchOnTyping = false;
    this._searchObserver = null;
    this._searchResultOptionGenerator = null;
    this._selectedSearchResultEleGenerator = null;
}

/**
 * Set search query textbox place holder
 * @param {String} placeHolder
 * @returns {CommonSearchBoxFragmentRender.prototype}
 */
CommonSearchBoxFragmentRender.prototype.searchInputPlaceHolder = function (placeHolder) {
    this._searchTextBoxPlaceHolder = placeHolder;
    return this;
};

/**
 * Enable search on typing
 * @returns {undefined}
 */
CommonSearchBoxFragmentRender.prototype.searchOnTyping = function () {
    this._searchOnTyping = true;
    return this;
};

/**
 * Set the function to process search text box
 * @param {Observer} searchObserver
 * @returns {undefined}
 */
CommonSearchBoxFragmentRender.prototype.searchObserver = function (searchObserver) {
    this._searchObserver = searchObserver;
    return this;
};

/**
 * Set the function to generate search result item DOM
 * @param {Function} searchResultOptionEleGenerator Function to generate item for one search result
 * @returns {undefined}
 */
CommonSearchBoxFragmentRender.prototype.searchResultOptionEleGenerator = function (searchResultOptionEleGenerator) {
    this._searchResultOptionGenerator = searchResultOptionEleGenerator;
};

/**
 * Set the function to generate selected search result element
 * @param {Function} selectedSearchResultEleGeneratingFunc
 * @returns {undefined}
 */
CommonSearchBoxFragmentRender.prototype.selectedSearchResultEleGenerator = function (selectedSearchResultEleGeneratingFunc) {
    this._selectedSearchResultEleGeneratingFunc = selectedSearchResultEleGeneratingFunc;
    return this;
};

/**
 * Build search box
 * @param {JQuery} frgSearchBox
 * @returns {undefined}
 */
CommonSearchBoxFragmentRender.prototype.build = function (frgSearchBox) {
    // TODO Implement
    // Content
    frgSearchBox.find('#txtSearchBoxQuery').attr('placeholder', this._searchTextBoxPlaceHolder);

    // Search process
    if (this._searchObserver) {
        let txtSearchBox = frgSearchBox.find('#txtSearchBoxQuery');
        let btnSearch = frgSearchBox.find('#btnSearch');
        let searchBtnClickObservable = Rx.Observable.fromEvent(btnSearch, 'click')
                .map(() => txtSearchBox.val());

        // Observable for search button and possibly typing on search field
        let searchObservable = !this._searchOnTyping ? searchBtnClickObservable : Rx.Observable.merge(searchBtnClickObservable);
        searchObservable.subscribe(this._searchObserver);

        // Search observer (actually search subject ?) subscribe the generator ?
        let observerBuilder = MendelWebs.getDefaultAjaxResponseObserverBuilder();
        this._searchSubscription = this._searchObserver.subscribe(observerBuilder.createAjaxResponseObserver(this.createObserverForSearchResponse(frgSearchBox.find('#sltSearchResultList'))));
    }

    // Select search result
    if (this._selectedSearchResultEleGeneratingFunc) {
        let searchResultEle = frgSearchBox.find('#sltSearchResultList option');
        let _selectedSearchResultEleGenerator = this._selectedSearchResultEleGenerator;
        let searchResultEleClickObservable = Rx.Observable.fromEvent(searchResultEle, 'click')
                .map(() => txtSearchBox.val())
                .subscribe((e) => console.log(e));
    }
};

/*-------------------------------------------------Private methods-------------------------------------------------*/

/**
 * Create observer object for search request action
 * @param {JQuery} searchResultSelectEle
 * @returns {Observer}
 */
CommonSearchBoxFragmentRender.prototype.createObserverForSearchResponse = function (searchResultSelectEle) {
    let renderFunc = CommonSearchBoxFragmentRender.prototype.renderResult.bind(this, searchResultSelectEle);
    let emptyFunc = CommonSearchBoxFragmentRender.prototype.renderEmpty.bind(this, searchResultSelectEle);
    let errorFunc = CommonSearchBoxFragmentRender.prototype.renderError.bind(this, searchResultSelectEle);
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

CommonSearchBoxFragmentRender.prototype.renderResult = function () {

};

CommonSearchBoxFragmentRender.prototype.renderEmpty = function () {

};

CommonSearchBoxFragmentRender.prototype.renderError = function () {

};