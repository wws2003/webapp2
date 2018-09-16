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
    },
    /**
     * Create Button tag
     * @returns {MendelHTMLTr}
     */
    button: function () {
        return new MendelHTMLTag('button');
    },
    /**
     * Create Option tag
     * @returns {MendelHTMLTag}
     */
    option: function () {
        return new MendelHTMLTag('option');
    },
    /**
     * Create Select tag
     * @returns {MendelHTMLTag}
     */
    select: function () {
        return new MendelHTMLTag('select');
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
    this._cssClasses.push(cssClass);
    return this;
};

/**
 * Add new CSS classes
 * @param {String} cssClasses (Space-Separated value)
 * @returns {undefined}
 */
MendelHTMLTag.prototype.withClasses = function (cssClasses) {
    var _cssClasses = this._cssClasses;
    $.each(cssClasses.split(' '), function (i, clz) {
        _cssClasses.push(clz);
    });
    return this;
};

/**
 * Add new CSS class if predicate true
 * @param {Function} predicator
 * @param {String} cssClass
 * @returns {undefined}
 */
MendelHTMLTag.prototype.withClassIf = function (predicator, cssClass) {
    if (!predicator()) {
        return this;
    }
    return this.withClass(cssClass);
};

/**
 * Add new CSS classes if predicate true
 * @param {Function} predicator
 * @param {String} cssClasses (Space-Separated value)
 * @returns {undefined}
 */
MendelHTMLTag.prototype.withClassesIf = function (predicator, cssClasses) {
    if (!predicator()) {
        return this;
    }
    return this.withClasses(cssClasses);
};

/**
 * Add new attribute
 * @param {String} attrName
 * @param {String} attrVal
 * @returns {undefined}
 */
MendelHTMLTag.prototype.withAttr = function (attrName, attrVal) {
    // If do not specifiy attrVal, consider attrVal the same as attrName (e.g. disabled, selected)
    this._attrMap[attrName] = attrVal !== undefined ? attrVal : attrName;
    return this;
};

/**
 * Add new attribute if predicate true
 * @param {Function} predicator
 * @param {String} attrName
 * @param {String} attrVal
 * @returns {undefined}
 */
MendelHTMLTag.prototype.withAttrIf = function (predicator, attrName, attrVal) {
    if (!predicator()) {
        return this;
    }
    return this.withAttr(attrName, attrVal);
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
 * Create new inner tag and return it (if predicate true, also append the new tag into current tag)
 * @param {Function} predicator
 * @param {String} tagName
 * @param {Number} contentOrder
 * @returns {MendelHTMLTag}
 */
MendelHTMLTag.prototype.innerTagIf = function (predicator, tagName, contentOrder) {
    // Almost simlar as innerTag, except that do not append to current tag
    let innerTag = new MendelHTMLTag(tagName, contentOrder);
    innerTag._parentTag = this;
    if (predicator()) {
        this._innerTags[this._innerTags.length] = (innerTag);
    }
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