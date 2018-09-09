/*
 * The file contents common HTML tag creation util methods
 */

let Tagger = {
    /**
     * Create TR tag
     * @returns {MendelHTMLTr}
     */
    tr: function () {
        return new MendelHTMLTr();
    },
    /**
     * Create DIV tag
     * @returns {MendelHTMLTr}
     */
    div: function () {
        return new MendelHTMLTag('div', 1);
    }
    // TODO Add methods like select, form...
};

/*------------------------------------------Base class for HTML tags-------------------------------------*/

/**
 * Init a tag
 * @param {String} tagName
 * @param {Number} contentOrder 1: Inner text then inner tags, Otherwise: Inner tags then inner text
 * @returns {MendelHTMLTag}
 */
function MendelHTMLTag(tagName, contentOrder) {
    this._tagName = tagName;
    this._contentOrder = contentOrder;
    this._parentTag = null;
    this._innerText = '';
    this._innerTags = [];
    this._id = '';
    this._autoClose = false; // True for tags like <input xxx/>
    this._cssClasses = [];
    this._attrMap = {};
}

/**
 * Set ID
 * @param {String} id
 * @returns {undefined}
 */
MendelHTMLTag.prototype.id = function (id) {
    this._id = id;
    return this;
};

/**
 * Set inner text
 * @param {String} text
 * @returns {undefined}
 */
MendelHTMLTag.prototype.innerText = function (text) {
    this._innerText = (text !== null && text !== undefined) ? text : '';
    return this;
};

/**
 * Set autoclose
 * @returns {undefined}
 */
MendelHTMLTag.prototype.autoClose = function () {
    this._autoClose = true;
    return this;
};

/**
 * Add new CSS class
 * @param {String} cssClass
 * @returns {undefined}
 */
MendelHTMLTag.prototype.withClass = function (cssClass) {
    this._cssClasses[this._cssClasses.length] = cssClass;
    return this;
};

/**
 * Add new attribute
 * @param {String} attrName
 * @param {String} attrVal
 * @returns {undefined}
 */
MendelHTMLTag.prototype.withAttr = function (attrName, attrVal) {
    this._attrMap[attrName] = attrVal;
    return this;
};

/**
 * Clear all values have been set
 * @returns {undefined}
 */
MendelHTMLTag.prototype.clear = function () {
    this._cssClasses = [];
    this._attrMap = {};
};

/**
 * Create new inner tag and return it
 * @param {String} tagName
 * @param {Number} contentOrder
 * @returns {MendelHTMLTag}
 */
MendelHTMLTag.prototype.innerTag = function (tagName, contentOrder) {
    let innerTag = new MendelHTMLTag(tagName, contentOrder);
    innerTag._parentTag = this;
    this._innerTags[this._innerTags.length] = (innerTag);
    return innerTag;
};

/**
 * Back to parent tag
 * @returns {MendelHTMLTag._parentTag}
 */
MendelHTMLTag.prototype.then = function () {
    return this._parentTag;
};


/**
 * Build HTML string
 * @returns {String}
 */
MendelHTMLTag.prototype.build = function () {
    return this._autoClose ? this.buildAsAutoCloseTag() : this.buildOpenAndCloseTag();
};

/**
 * Build HTML string for tag without closing mark
 * @returns {String}
 */
MendelHTMLTag.prototype.buildAsAutoCloseTag = function () {
    return '<' + this.buildOpenTagContent() + '/>';
};

/**
 * Build HTML string for open and close tag, like &lt;tr id='1'&gt and &lt;/tr id='1'&gt;
 * @returns {String}
 */
MendelHTMLTag.prototype.buildOpenAndCloseTag = function () {
    let openTag = '<' + this.buildOpenTagContent() + '>';
    let innerText = this._innerText;
    let innerTagHTML = this._innerTags.reduce(
            (accInnerHTML, innerTag) => accInnerHTML + innerTag.build(),
            '');
    let innerContent = (this._contentOrder === 1) ? (innerText + innerTagHTML) : (innerTagHTML + innerText);
    let closeTag = '</' + this._tagName + '>';
    // Create tags
    return openTag + innerContent + closeTag;
};


/**
 * Build HTML string for open tag, like &lt;tr id='1'&gt and &lt;/tr id='1'&gt;
 * @returns {String}
 */
MendelHTMLTag.prototype.buildOpenTagContent = function () {
    let id = this.ifNotEmpty(this._id, id => 'id="' + id + '"');
    let attrMap = this._attrMap;
    let attrs = Object.keys(attrMap)
            .map(attrKey => attrKey + '=' + attrMap[attrKey])
            .join(' ');
    let css = this.ifNotEmpty(this._cssClasses.join(' '), cssClasses => 'class="' + cssClasses + '"');
    // Create tags
    return [this._tagName, id, attrs, css].filter(e => (e !== '')).join(' ');
};

/**
 * Apply func to target and return the result if target not empty, return empty otherwise
 * @param {String} target
 * @param {Function} func
 * @returns {String}
 */
MendelHTMLTag.prototype.ifNotEmpty = function (target, func) {
    return (target !== '') ? func(target) : '';
};

/*------------------------------------------Shortcut class for HTML TR tag-------------------------------------*/
MendelHTMLTr.prototype = Object.create(MendelHTMLTag.prototype);
MendelHTMLTr.prototype.constructor = MendelHTMLTr;

function MendelHTMLTr() {
    MendelHTMLTag.call(this, 'tr');
}

/**
 * Add new td element with text and return the newly created td tag
 * @param {String} text
 * @returns {undefined}
 */
MendelHTMLTr.prototype.td = function (text) {
    return this.innerTag('td').innerText(text);
};

/**
 * Add new td element with text and return this
 * @param {String} text
 * @returns {undefined}
 */
MendelHTMLTr.prototype.withTd = function (text) {
    return this.innerTag('td').innerText(text).then();
};

/**
 * Add new th element with text and return the newly created th tag
 * @param {String} text
 * @returns {undefined}
 */
MendelHTMLTr.prototype.th = function (text) {
    return this.innerTag('th').innerText(text);
};

/**
 * Add new th element with text and return this
 * @param {String} text
 * @returns {undefined}
 */
MendelHTMLTr.prototype.withTh = function (text) {
    return this.innerTag('th').innerText(text).then();
};