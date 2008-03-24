<?php

// Start of dom v.20031129

/**
 * DOM operations raise exceptions under particular circumstances, i.e.,
 * when an operation is impossible to perform for logical reasons.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMException extends Exception  {
	protected $message;
	public $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class DOMStringList  {

	public function item () {}

}

/**
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMNameList  {

	public function getName () {}

	public function getNamespaceURI () {}

}

class DOMImplementationList  {

	public function item () {}

}

class DOMImplementationSource  {

	public function getDomimplementation () {}

	public function getDomimplementations () {}

}

/**
 * The DOMImplementation interface provides a number
 * of methods for performing operations that are independent of any 
 * particular instance of the document object model.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMImplementation  {

	public function getFeature () {}

	public function hasFeature () {}

	public function createDocumentType () {}

	public function createDocument () {}

}

/**
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMNode  {

	public function insertBefore () {}

	public function replaceChild () {}

	public function removeChild () {}

	public function appendChild () {}

	public function hasChildNodes () {}

	public function cloneNode () {}

	public function normalize () {}

	public function isSupported () {}

	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	public function isSameNode () {}

	public function lookupPrefix () {}

	public function isDefaultNamespace () {}

	public function lookupNamespaceUri () {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

class DOMNameSpaceNode  {
}

/**
 * Extends DOMNode.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMDocumentFragment extends DOMNode  {

	public function __construct () {}

	public function appendXML () {}

	public function insertBefore () {}

	public function replaceChild () {}

	public function removeChild () {}

	public function appendChild () {}

	public function hasChildNodes () {}

	public function cloneNode () {}

	public function normalize () {}

	public function isSupported () {}

	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	public function isSameNode () {}

	public function lookupPrefix () {}

	public function isDefaultNamespace () {}

	public function lookupNamespaceUri () {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * Extends DOMNode.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMDocument extends DOMNode  {

	public function createElement () {}

	public function createDocumentFragment () {}

	public function createTextNode () {}

	public function createComment () {}

	public function createCDATASection () {}

	public function createProcessingInstruction () {}

	public function createAttribute () {}

	public function createEntityReference () {}

	public function getElementsByTagName () {}

	public function importNode () {}

	public function createElementNS () {}

	public function createAttributeNS () {}

	public function getElementsByTagNameNS () {}

	public function getElementById () {}

	public function adoptNode () {}

	public function normalizeDocument () {}

	public function renameNode () {}

	public function load () {}

	public function save () {}

	public function loadXML () {}

	public function saveXML () {}

	public function __construct () {}

	public function validate () {}

	/**
	 * Substitutes XIncludes in a DomDocument Object
	 * @link http://php.net/manual/en/function.domdocument-xinclude.php
	 */
	public function xinclude () {}

	public function loadHTML () {}

	public function loadHTMLFile () {}

	public function saveHTML () {}

	public function saveHTMLFile () {}

	public function schemaValidate () {}

	public function schemaValidateSource () {}

	public function relaxNGValidate () {}

	public function relaxNGValidateSource () {}

	public function registerNodeClass () {}

	public function insertBefore () {}

	public function replaceChild () {}

	public function removeChild () {}

	public function appendChild () {}

	public function hasChildNodes () {}

	public function cloneNode () {}

	public function normalize () {}

	public function isSupported () {}

	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	public function isSameNode () {}

	public function lookupPrefix () {}

	public function isDefaultNamespace () {}

	public function lookupNamespaceUri () {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMNodeList  {

	public function item () {}

}

/**
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMNamedNodeMap  {

	public function getNamedItem () {}

	public function setNamedItem () {}

	public function removeNamedItem () {}

	public function item () {}

	public function getNamedItemNS () {}

	public function setNamedItemNS () {}

	public function removeNamedItemNS () {}

}

/**
 * Extends DOMNode.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMCharacterData extends DOMNode  {

	public function substringData () {}

	public function appendData () {}

	public function insertData () {}

	public function deleteData () {}

	public function replaceData () {}

	public function insertBefore () {}

	public function replaceChild () {}

	public function removeChild () {}

	public function appendChild () {}

	public function hasChildNodes () {}

	public function cloneNode () {}

	public function normalize () {}

	public function isSupported () {}

	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	public function isSameNode () {}

	public function lookupPrefix () {}

	public function isDefaultNamespace () {}

	public function lookupNamespaceUri () {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * Extends DOMNode. The DOMAttr
 * interface represents an attribute in an DOMElement object.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMAttr extends DOMNode  {

	public function isId () {}

	public function __construct () {}

	public function insertBefore () {}

	public function replaceChild () {}

	public function removeChild () {}

	public function appendChild () {}

	public function hasChildNodes () {}

	public function cloneNode () {}

	public function normalize () {}

	public function isSupported () {}

	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	public function isSameNode () {}

	public function lookupPrefix () {}

	public function isDefaultNamespace () {}

	public function lookupNamespaceUri () {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * Extends DOMNode.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMElement extends DOMNode  {

	public function getAttribute () {}

	public function setAttribute () {}

	public function removeAttribute () {}

	public function getAttributeNode () {}

	public function setAttributeNode () {}

	public function removeAttributeNode () {}

	public function getElementsByTagName () {}

	public function getAttributeNS () {}

	public function setAttributeNS () {}

	public function removeAttributeNS () {}

	public function getAttributeNodeNS () {}

	public function setAttributeNodeNS () {}

	public function getElementsByTagNameNS () {}

	public function hasAttribute () {}

	public function hasAttributeNS () {}

	public function setIdAttribute () {}

	public function setIdAttributeNS () {}

	public function setIdAttributeNode () {}

	public function __construct () {}

	public function insertBefore () {}

	public function replaceChild () {}

	public function removeChild () {}

	public function appendChild () {}

	public function hasChildNodes () {}

	public function cloneNode () {}

	public function normalize () {}

	public function isSupported () {}

	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	public function isSameNode () {}

	public function lookupPrefix () {}

	public function isDefaultNamespace () {}

	public function lookupNamespaceUri () {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * Extends DOMCharacterData.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMText extends DOMCharacterData  {

	public function splitText () {}

	public function isWhitespaceInElementContent () {}

	public function isElementContentWhitespace () {}

	public function replaceWholeText () {}

	public function __construct () {}

	public function substringData () {}

	public function appendData () {}

	public function insertData () {}

	public function deleteData () {}

	public function replaceData () {}

	public function insertBefore () {}

	public function replaceChild () {}

	public function removeChild () {}

	public function appendChild () {}

	public function hasChildNodes () {}

	public function cloneNode () {}

	public function normalize () {}

	public function isSupported () {}

	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	public function isSameNode () {}

	public function lookupPrefix () {}

	public function isDefaultNamespace () {}

	public function lookupNamespaceUri () {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * Extends DOMCharacterData.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMComment extends DOMCharacterData  {

	public function __construct () {}

	public function substringData () {}

	public function appendData () {}

	public function insertData () {}

	public function deleteData () {}

	public function replaceData () {}

	public function insertBefore () {}

	public function replaceChild () {}

	public function removeChild () {}

	public function appendChild () {}

	public function hasChildNodes () {}

	public function cloneNode () {}

	public function normalize () {}

	public function isSupported () {}

	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	public function isSameNode () {}

	public function lookupPrefix () {}

	public function isDefaultNamespace () {}

	public function lookupNamespaceUri () {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

class DOMTypeinfo  {
}

class DOMUserDataHandler  {

	public function handle () {}

}

class DOMDomError  {
}

class DOMErrorHandler  {

	public function handleError () {}

}

class DOMLocator  {
}

class DOMConfiguration  {

	public function setParameter () {}

	public function getParameter () {}

	public function canSetParameter () {}

}

class DOMCdataSection extends DOMText  {

	public function __construct () {}

	public function splitText () {}

	public function isWhitespaceInElementContent () {}

	public function isElementContentWhitespace () {}

	public function replaceWholeText () {}

	public function substringData () {}

	public function appendData () {}

	public function insertData () {}

	public function deleteData () {}

	public function replaceData () {}

	public function insertBefore () {}

	public function replaceChild () {}

	public function removeChild () {}

	public function appendChild () {}

	public function hasChildNodes () {}

	public function cloneNode () {}

	public function normalize () {}

	public function isSupported () {}

	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	public function isSameNode () {}

	public function lookupPrefix () {}

	public function isDefaultNamespace () {}

	public function lookupNamespaceUri () {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * Extends DOMNode
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMDocumentType extends DOMNode  {

	public function insertBefore () {}

	public function replaceChild () {}

	public function removeChild () {}

	public function appendChild () {}

	public function hasChildNodes () {}

	public function cloneNode () {}

	public function normalize () {}

	public function isSupported () {}

	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	public function isSameNode () {}

	public function lookupPrefix () {}

	public function isDefaultNamespace () {}

	public function lookupNamespaceUri () {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * Extends DOMNode
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMNotation  {
}

/**
 * Extends DOMNode
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMEntity extends DOMNode  {

	public function insertBefore () {}

	public function replaceChild () {}

	public function removeChild () {}

	public function appendChild () {}

	public function hasChildNodes () {}

	public function cloneNode () {}

	public function normalize () {}

	public function isSupported () {}

	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	public function isSameNode () {}

	public function lookupPrefix () {}

	public function isDefaultNamespace () {}

	public function lookupNamespaceUri () {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * Extends DOMNode.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMEntityReference extends DOMNode  {

	public function __construct () {}

	public function insertBefore () {}

	public function replaceChild () {}

	public function removeChild () {}

	public function appendChild () {}

	public function hasChildNodes () {}

	public function cloneNode () {}

	public function normalize () {}

	public function isSupported () {}

	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	public function isSameNode () {}

	public function lookupPrefix () {}

	public function isDefaultNamespace () {}

	public function lookupNamespaceUri () {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * Extends DOMNode.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMProcessingInstruction extends DOMNode  {

	public function __construct () {}

	public function insertBefore () {}

	public function replaceChild () {}

	public function removeChild () {}

	public function appendChild () {}

	public function hasChildNodes () {}

	public function cloneNode () {}

	public function normalize () {}

	public function isSupported () {}

	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	public function isSameNode () {}

	public function lookupPrefix () {}

	public function isDefaultNamespace () {}

	public function lookupNamespaceUri () {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

class DOMStringExtend  {

	public function findOffset16 () {}

	public function findOffset32 () {}

}

/**
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMXPath  {

	public function __construct () {}

	public function registerNamespace () {}

	public function query () {}

	public function evaluate () {}

}

/**
 * Gets a DOMElement object from a SimpleXMLElement object
 * @link http://php.net/manual/en/function.dom-import-simplexml.php
 * @param node SimpleXMLElement
 * @return DOMElement 
 */
function dom_import_simplexml (SimpleXMLElement $node) {}


/**
 * 1
 * @link http://php.net/manual/en/domxml.constants.php
 */
define ('XML_ELEMENT_NODE', 1);

/**
 * 2
 * @link http://php.net/manual/en/domxml.constants.php
 */
define ('XML_ATTRIBUTE_NODE', 2);

/**
 * 3
 * @link http://php.net/manual/en/domxml.constants.php
 */
define ('XML_TEXT_NODE', 3);

/**
 * 4
 * @link http://php.net/manual/en/domxml.constants.php
 */
define ('XML_CDATA_SECTION_NODE', 4);

/**
 * 5
 * @link http://php.net/manual/en/domxml.constants.php
 */
define ('XML_ENTITY_REF_NODE', 5);

/**
 * 6
 * @link http://php.net/manual/en/domxml.constants.php
 */
define ('XML_ENTITY_NODE', 6);

/**
 * 7
 * @link http://php.net/manual/en/domxml.constants.php
 */
define ('XML_PI_NODE', 7);

/**
 * 8
 * @link http://php.net/manual/en/domxml.constants.php
 */
define ('XML_COMMENT_NODE', 8);

/**
 * 9
 * @link http://php.net/manual/en/domxml.constants.php
 */
define ('XML_DOCUMENT_NODE', 9);

/**
 * 10
 * @link http://php.net/manual/en/domxml.constants.php
 */
define ('XML_DOCUMENT_TYPE_NODE', 10);

/**
 * 11
 * @link http://php.net/manual/en/domxml.constants.php
 */
define ('XML_DOCUMENT_FRAG_NODE', 11);

/**
 * 12
 * @link http://php.net/manual/en/domxml.constants.php
 */
define ('XML_NOTATION_NODE', 12);
define ('XML_HTML_DOCUMENT_NODE', 13);
define ('XML_DTD_NODE', 14);
define ('XML_ELEMENT_DECL_NODE', 15);
define ('XML_ATTRIBUTE_DECL_NODE', 16);
define ('XML_ENTITY_DECL_NODE', 17);
define ('XML_NAMESPACE_DECL_NODE', 18);

/**
 * 2
 * @link http://php.net/manual/en/domxml.constants.php
 */
define ('XML_LOCAL_NAMESPACE', 18);
define ('XML_ATTRIBUTE_CDATA', 1);
define ('XML_ATTRIBUTE_ID', 2);
define ('XML_ATTRIBUTE_IDREF', 3);
define ('XML_ATTRIBUTE_IDREFS', 4);
define ('XML_ATTRIBUTE_ENTITY', 6);
define ('XML_ATTRIBUTE_NMTOKEN', 7);
define ('XML_ATTRIBUTE_NMTOKENS', 8);
define ('XML_ATTRIBUTE_ENUMERATION', 9);
define ('XML_ATTRIBUTE_NOTATION', 10);
define ('DOM_PHP_ERR', 0);

/**
 * 1
 * @link http://php.net/manual/en/dom.constants.php
 */
define ('DOM_INDEX_SIZE_ERR', 1);

/**
 * 2
 * @link http://php.net/manual/en/dom.constants.php
 */
define ('DOMSTRING_SIZE_ERR', 2);

/**
 * 3
 * @link http://php.net/manual/en/dom.constants.php
 */
define ('DOM_HIERARCHY_REQUEST_ERR', 3);

/**
 * 4
 * @link http://php.net/manual/en/dom.constants.php
 */
define ('DOM_WRONG_DOCUMENT_ERR', 4);

/**
 * 5
 * @link http://php.net/manual/en/dom.constants.php
 */
define ('DOM_INVALID_CHARACTER_ERR', 5);

/**
 * 6
 * @link http://php.net/manual/en/dom.constants.php
 */
define ('DOM_NO_DATA_ALLOWED_ERR', 6);

/**
 * 7
 * @link http://php.net/manual/en/dom.constants.php
 */
define ('DOM_NO_MODIFICATION_ALLOWED_ERR', 7);

/**
 * 8
 * @link http://php.net/manual/en/dom.constants.php
 */
define ('DOM_NOT_FOUND_ERR', 8);

/**
 * 9
 * @link http://php.net/manual/en/dom.constants.php
 */
define ('DOM_NOT_SUPPORTED_ERR', 9);

/**
 * 10
 * @link http://php.net/manual/en/dom.constants.php
 */
define ('DOM_INUSE_ATTRIBUTE_ERR', 10);

/**
 * 11
 * @link http://php.net/manual/en/dom.constants.php
 */
define ('DOM_INVALID_STATE_ERR', 11);

/**
 * 12
 * @link http://php.net/manual/en/dom.constants.php
 */
define ('DOM_SYNTAX_ERR', 12);

/**
 * 13
 * @link http://php.net/manual/en/dom.constants.php
 */
define ('DOM_INVALID_MODIFICATION_ERR', 13);

/**
 * 14
 * @link http://php.net/manual/en/dom.constants.php
 */
define ('DOM_NAMESPACE_ERR', 14);

/**
 * 15
 * @link http://php.net/manual/en/dom.constants.php
 */
define ('DOM_INVALID_ACCESS_ERR', 15);

/**
 * 16
 * @link http://php.net/manual/en/dom.constants.php
 */
define ('DOM_VALIDATION_ERR', 16);

// End of dom v.20031129
?>
