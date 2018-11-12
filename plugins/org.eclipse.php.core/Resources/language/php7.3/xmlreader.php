<?php

// Start of xmlreader v.7.3.0

/**
 * The XMLReader extension is an XML Pull parser. The reader acts as a 
 * cursor going forward on the document stream and stopping at each node 
 * on the way.
 * @link http://www.php.net/manual/en/class.xmlreader.php
 */
class XMLReader  {
	const NONE = 0;
	const ELEMENT = 1;
	const ATTRIBUTE = 2;
	const TEXT = 3;
	const CDATA = 4;
	const ENTITY_REF = 5;
	const ENTITY = 6;
	const PI = 7;
	const COMMENT = 8;
	const DOC = 9;
	const DOC_TYPE = 10;
	const DOC_FRAGMENT = 11;
	const NOTATION = 12;
	const WHITESPACE = 13;
	const SIGNIFICANT_WHITESPACE = 14;
	const END_ELEMENT = 15;
	const END_ENTITY = 16;
	const XML_DECLARATION = 17;
	const LOADDTD = 1;
	const DEFAULTATTRS = 2;
	const VALIDATE = 3;
	const SUBST_ENTITIES = 4;


	/**
	 * The number of attributes on the node
	 * @var int
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.attributecount
	 */
	public $attributeCount;

	/**
	 * The base URI of the node
	 * @var string
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.baseuri
	 */
	public $baseURI;

	/**
	 * Depth of the node in the tree, starting at 0
	 * @var int
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.depth
	 */
	public $depth;

	/**
	 * Indicates if node has attributes
	 * @var bool
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.hasattributes
	 */
	public $hasAttributes;

	/**
	 * Indicates if node has a text value
	 * @var bool
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.hasvalue
	 */
	public $hasValue;

	/**
	 * Indicates if attribute is defaulted from DTD
	 * @var bool
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.isdefault
	 */
	public $isDefault;

	/**
	 * Indicates if node is an empty element tag
	 * @var bool
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.isemptyelement
	 */
	public $isEmptyElement;

	/**
	 * The local name of the node
	 * @var string
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.localname
	 */
	public $localName;

	/**
	 * The qualified name of the node
	 * @var string
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.name
	 */
	public $name;

	/**
	 * The URI of the namespace associated with the node
	 * @var string
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.namespaceuri
	 */
	public $namespaceURI;

	/**
	 * The node type for the node
	 * @var int
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.nodetype
	 */
	public $nodeType;

	/**
	 * The prefix of the namespace associated with the node
	 * @var string
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.prefix
	 */
	public $prefix;

	/**
	 * The text value of the node
	 * @var string
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.value
	 */
	public $value;

	/**
	 * The xml:lang scope which the node resides
	 * @var string
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.xmllang
	 */
	public $xmlLang;

	/**
	 * Close the XMLReader input
	 * @link http://www.php.net/manual/en/xmlreader.close.php
	 * @return bool true on success or false on failure
	 */
	public function close () {}

	/**
	 * Get the value of a named attribute
	 * @link http://www.php.net/manual/en/xmlreader.getattribute.php
	 * @param string $name The name of the attribute.
	 * @return string The value of the attribute, or null if no attribute with the given
	 * name is found or not positioned on an element node.
	 */
	public function getAttribute (string $name) {}

	/**
	 * Get the value of an attribute by index
	 * @link http://www.php.net/manual/en/xmlreader.getattributeno.php
	 * @param int $index The position of the attribute.
	 * @return string The value of the attribute, or an empty string (before PHP 5.6) or null
	 * (from PHP 5.6 onwards) if no attribute exists at
	 * index or is not positioned on the element.
	 */
	public function getAttributeNo (int $index) {}

	/**
	 * Get the value of an attribute by localname and URI
	 * @link http://www.php.net/manual/en/xmlreader.getattributens.php
	 * @param string $localName The local name.
	 * @param string $namespaceURI The namespace URI.
	 * @return string The value of the attribute, or an empty string (before PHP 5.6) or null
	 * (from PHP 5.6 onwards) if no attribute with the given
	 * localName and namespaceURI is
	 * found or not positioned of element.
	 */
	public function getAttributeNs (string $localName, string $namespaceURI) {}

	/**
	 * Indicates if specified property has been set
	 * @link http://www.php.net/manual/en/xmlreader.getparserproperty.php
	 * @param int $property One of the parser option 
	 * constants.
	 * @return bool true on success or false on failure
	 */
	public function getParserProperty (int $property) {}

	/**
	 * Indicates if the parsed document is valid
	 * @link http://www.php.net/manual/en/xmlreader.isvalid.php
	 * @return bool true on success or false on failure
	 */
	public function isValid () {}

	/**
	 * Lookup namespace for a prefix
	 * @link http://www.php.net/manual/en/xmlreader.lookupnamespace.php
	 * @param string $prefix String containing the prefix.
	 * @return string true on success or false on failure
	 */
	public function lookupNamespace (string $prefix) {}

	/**
	 * Move cursor to an attribute by index
	 * @link http://www.php.net/manual/en/xmlreader.movetoattributeno.php
	 * @param int $index The position of the attribute.
	 * @return bool true on success or false on failure
	 */
	public function moveToAttributeNo (int $index) {}

	/**
	 * Move cursor to a named attribute
	 * @link http://www.php.net/manual/en/xmlreader.movetoattribute.php
	 * @param string $name The name of the attribute.
	 * @return bool true on success or false on failure
	 */
	public function moveToAttribute (string $name) {}

	/**
	 * Move cursor to a named attribute
	 * @link http://www.php.net/manual/en/xmlreader.movetoattributens.php
	 * @param string $localName The local name.
	 * @param string $namespaceURI The namespace URI.
	 * @return bool true on success or false on failure
	 */
	public function moveToAttributeNs (string $localName, string $namespaceURI) {}

	/**
	 * Position cursor on the parent Element of current Attribute
	 * @link http://www.php.net/manual/en/xmlreader.movetoelement.php
	 * @return bool true if successful and false if it fails or not positioned on 
	 * Attribute when this method is called.
	 */
	public function moveToElement () {}

	/**
	 * Position cursor on the first Attribute
	 * @link http://www.php.net/manual/en/xmlreader.movetofirstattribute.php
	 * @return bool true on success or false on failure
	 */
	public function moveToFirstAttribute () {}

	/**
	 * Position cursor on the next Attribute
	 * @link http://www.php.net/manual/en/xmlreader.movetonextattribute.php
	 * @return bool true on success or false on failure
	 */
	public function moveToNextAttribute () {}

	/**
	 * Set the URI containing the XML to parse
	 * @link http://www.php.net/manual/en/xmlreader.open.php
	 * @param string $URI URI pointing to the document.
	 * @param string $encoding [optional] The document encoding or null.
	 * @param int $options [optional] A bitmask of the LIBXML_&#42; 
	 * constants.
	 * @return bool true on success or false on failure If called statically, returns an
	 * XMLReader or false on failure.
	 */
	public function open (string $URI, string $encoding = null, int $options = null) {}

	/**
	 * Move to next node in document
	 * @link http://www.php.net/manual/en/xmlreader.read.php
	 * @return bool true on success or false on failure
	 */
	public function read () {}

	/**
	 * Move cursor to next node skipping all subtrees
	 * @link http://www.php.net/manual/en/xmlreader.next.php
	 * @param string $localname [optional] The name of the next node to move to.
	 * @return bool true on success or false on failure
	 */
	public function next (string $localname = null) {}

	/**
	 * Retrieve XML from current node
	 * @link http://www.php.net/manual/en/xmlreader.readinnerxml.php
	 * @return string the contents of the current node as a string. Empty string on failure.
	 */
	public function readInnerXml () {}

	/**
	 * Retrieve XML from current node, including itself
	 * @link http://www.php.net/manual/en/xmlreader.readouterxml.php
	 * @return string the contents of current node, including itself, as a string. Empty string on failure.
	 */
	public function readOuterXml () {}

	/**
	 * Reads the contents of the current node as a string
	 * @link http://www.php.net/manual/en/xmlreader.readstring.php
	 * @return string the content of the current node as a string. Empty string on
	 * failure.
	 */
	public function readString () {}

	/**
	 * Validate document against XSD
	 * @link http://www.php.net/manual/en/xmlreader.setschema.php
	 * @param string $filename The filename of the XSD schema.
	 * @return bool true on success or false on failure
	 */
	public function setSchema (string $filename) {}

	/**
	 * Set parser options
	 * @link http://www.php.net/manual/en/xmlreader.setparserproperty.php
	 * @param int $property One of the parser option 
	 * constants.
	 * @param bool $value If set to true the option will be enabled otherwise will 
	 * be disabled.
	 * @return bool true on success or false on failure
	 */
	public function setParserProperty (int $property, bool $value) {}

	/**
	 * Set the filename or URI for a RelaxNG Schema
	 * @link http://www.php.net/manual/en/xmlreader.setrelaxngschema.php
	 * @param string $filename filename or URI pointing to a RelaxNG Schema.
	 * @return bool true on success or false on failure
	 */
	public function setRelaxNGSchema (string $filename) {}

	/**
	 * Set the data containing a RelaxNG Schema
	 * @link http://www.php.net/manual/en/xmlreader.setrelaxngschemasource.php
	 * @param string $source String containing the RelaxNG Schema.
	 * @return bool true on success or false on failure
	 */
	public function setRelaxNGSchemaSource (string $source) {}

	/**
	 * Set the data containing the XML to parse
	 * @link http://www.php.net/manual/en/xmlreader.xml.php
	 * @param string $source String containing the XML to be parsed.
	 * @param string $encoding [optional] The document encoding or null.
	 * @param int $options [optional] A bitmask of the LIBXML_&#42; 
	 * constants.
	 * @return bool true on success or false on failure If called statically, returns an
	 * XMLReader or false on failure.
	 */
	public function XML (string $source, string $encoding = null, int $options = null) {}

	/**
	 * Returns a copy of the current node as a DOM object
	 * @link http://www.php.net/manual/en/xmlreader.expand.php
	 * @param DOMNode $basenode [optional] A DOMNode defining the target DOMDocument for the created DOM object.
	 * @return DOMNode The resulting DOMNode or false on error.
	 */
	public function expand (DOMNode $basenode = null) {}

}
// End of xmlreader v.7.3.0
