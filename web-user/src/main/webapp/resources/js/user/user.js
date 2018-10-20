/* global MendelApp, Rx, MendelDialog, MendelWebs */

/**
 * Based urls
 * @type Map
 */
let Urls = {
    USER_BASE_URL: MendelApp.BASE_URL + location.pathname,
    TEST_URL: 'testajax'
};

// Entry point
$(document).ready(function () {
    // Currently just test ajax action when session expired
    $('#btnTest').on('click', function () {
        let promise = (new MendelAjaxExecutor())
                .url(Urls.USER_BASE_URL + '/' + Urls.TEST_URL)
                .formData({})
                .getPromise();

        let observable = Rx.Observable.fromPromise(promise);

        let defaultObserver = {
            next: (response) => {
                MendelDialog.info('Message', response.resultObject);
            },
            error: (response) => {
                console.error(response);
            },
            complete: (response) => {

            }
        };
        let observerBuilder = MendelWebs.getDefaultAjaxResponseObserverBuilder();
        observable.subscribe(observerBuilder.createAjaxResponseObserver(defaultObserver));
    });
});