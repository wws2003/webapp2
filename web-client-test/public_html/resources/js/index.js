/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global Tagger, MendelApp, MendelDialog, MendelAjaxObservableBuilder, MendelWebs, MendelAjaxResponseObserverBuilder */

var Rx = Rx || {};
var CommonDetailDlg = CommonDetailDlg || {};

/*--------------------------------------------------Main actions------------------------------------------------*/
// Entry point
$(document).ready(function () {
    var searchSubject = new Rx.Subject();

    var userSearchBox = (new CommonSearchBoxFragmentRender())
            .searchInputPlaceHolder('User name')
            .searchSubject(searchSubject)
            .searchResultOptionEleGenerator((usr) => Tagger.option()
                        .id(usr.id)
                        .innerText(usr.name)
                        .build()
            )
            .build($('#frgSearchBox'));

    // Mocked result
    var ret = {
        success: true,
        successMessages: [],
        resultObject: [
            {id: 1, name: 'name1', dispName: 'dispname1'},
            {id: 2, name: 'name2', dispName: 'dispname2'},
            {id: 3, name: 'name3', dispName: 'dispname3'}
        ]
    };
    var resultSource = Rx.Observable.from([ret]);

    resultSource.subscribe(searchSubject);
});