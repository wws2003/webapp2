/* global MendelApp */

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
        promise.done(ret => {
            console.log(ret);
        }).fail(ret => {
            console.log(ret);
        });
    });
});