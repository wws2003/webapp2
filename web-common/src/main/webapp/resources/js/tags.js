/*
 * The file contents common HTML tag creation util methods
 */

let Tagger = {
    tr: function () {
        return new Tr();
    }
};

/*------------------------------------------Base class for HTML tags-------------------------------------*/
function Tag() {
    this._id = '';
    this._cssClasses = [];
    this._attrMap = {};
}

/**
 * Set ID
 * @param {String} id
 * @returns {undefined}
 */
Tag.prototype.id = function (id) {
    this._id = id;
    return this;
};

/**
 * Add new CSS class
 * @param {String} cssClass
 * @returns {undefined}
 */
Tag.prototype.withClass = function (cssClass) {
    this._cssClasses.concat(cssClass);
    return this;
};

/**
 * Add new attribute
 * @param {String} attrName
 * @param {String} attrVal
 * @returns {undefined}
 */
Tag.prototype.withAttr = function (attrName, attrVal) {
    this._attrMap[attrName] = attrVal;
    return this;
};

/**
 * Clear all values have been set
 * @returns {undefined}
 */
Tag.prototype.clear = function () {
    this._cssClasses = [];
    this._attrMap = {};
};

/*------------------------------------------Class for HTML TR tag-------------------------------------*/
Tr.prototype = Object.create(Tag.prototype);
Tr.prototype.constructor = Tr;

function Tr() {
    Tag.call(this);
    this.tds = [];
}

/**
 * Add new td element with text
 * @param {String} text
 * @returns {undefined}
 */
Tr.prototype.td = function (text) {

};

/**
 * Build current tr tag
 * @returns {String}
 */
Tr.prototype.build = function () {
    // TODO Implement
    return '';
};

/*------------------------------------------Class for HTML TD tag-------------------------------------*/
Td.prototype = Object.create(Tag.prototype);
Td.prototype.constructor = Td;

/**
 * Td constructor with text
 * @param {String} text
 * @returns {Td}
 */
function Td(text) {

}

/**
 * Build current td tag
 * @returns {String}
 */
Td.prototype.build = function () {
    // TODO Implement
    return '';
};


