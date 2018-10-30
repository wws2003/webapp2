/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    this._searchFunc = null;
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
 * @param {Function} searchFunc
 * @returns {undefined}
 */
CommonSearchBoxFragmentRender.prototype.searchFunc = function (searchFunc) {
    this._searchFunc = searchFunc;
    return this;
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
    if (this._searchFunc) {
        let txtSearchBox = frgSearchBox.find('#txtSearchBoxQuery');
        let btnSearch = frgSearchBox.find('#btnSearch');
        let searchBtnClickObservable = Rx.Observable.fromEvent(btnSearch, 'click')
                .map(() => txtSearchBox.val());

        // TODO Implement search on typing
        let searchObservable = !this._searchOnTyping ? searchBtnClickObservable : Rx.Observable.merge(searchBtnClickObservable);

        searchObservable.subscribe(this._searchFunc);
    }

    // Select search result
    if (this._selectedSearchResultEleGeneratingFunc) {
        let searchResultEle = frgSearchBox.find('#ulSearchResultList li');
        let _selectedSearchResultEleGenerator = this._selectedSearchResultEleGenerator;
        let searchResultEleClickObservable = Rx.Observable.fromEvent(searchResultEle, 'click')
                .map(() => txtSearchBox.val());
    }
};
