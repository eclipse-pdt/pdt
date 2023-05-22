<?php

// Start of xmlreader v.8.2.6

/**
 * The XMLReader extension is an XML Pull parser. The reader acts as a 
 * cursor going forward on the document stream and stopping at each node 
 * on the way.
 * @link http://www.php.net/manual/en/class.xmlreader.php
 */
class XMLReader  {
	/**
	 * No node type
	const NONE = 0;
	/**
	 * Start element
	const ELEMENT = 1;
	/**
	 * Attribute node
	const ATTRIBUTE = 2;
	/**
	 * Text node
	const TEXT = 3;
	/**
	 * CDATA node
	const CDATA = 4;
	/**
	 * Entity Reference node
	const ENTITY_REF = 5;
	/**
	 * Entity Declaration node
	const ENTITY = 6;
	/**
	 * Processing Instruction node
	const PI = 7;
	/**
	 * Comment node
	const COMMENT = 8;
	/**
	 * Document node
	const DOC = 9;
	/**
	 * Document Type node
	const DOC_TYPE = 10;
	/**
	 * Document Fragment node
	const DOC_FRAGMENT = 11;
	/**
	 * Notation node
	const NOTATION = 12;
	/**
	 * Whitespace node
	const WHITESPACE = 13;
	/**
	 * Significant Whitespace node
	const SIGNIFICANT_WHITESPACE = 14;
	/**
	 * End Element
	const END_ELEMENT = 15;
	/**
	 * End Entity
	const END_ENTITY = 16;
	/**
	 * XML Declaration node
	const XML_DECLARATION = 17;
	/**
	 * Load DTD but do not validate
	const LOADDTD = 1;
	/**
	 * Load DTD and default attributes but do not validate
	const DEFAULTATTRS = 2;
	/**
	 * Load DTD and validate while parsing
	const VALIDATE = 3;
	/**
	 * Substitute entities and expand references
	const SUBST_ENTITIES = 4;


	/**
	 * The number of attributes on the node
	 * @var int
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.attributecount
	 */
	public int $attributeCount;

	/**
	 * The base URI of the node
	 * @var string
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.baseuri
	 */
	public string $baseURI;

	/**
	 * Depth of the node in the tree, starting at 0
	 * @var int
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.depth
	 */
	public int $depth;

	/**
	 * Indicates if node has attributes
	 * @var bool
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.hasattributes
	 */
	public bool $hasAttributes;

	/**
	 * Indicates if node has a text value
	 * @var bool
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.hasvalue
	 */
	public bool $hasValue;

	/**
	 * Indicates if attribute is defaulted from DTD
	 * @var bool
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.isdefault
	 */
	public bool $isDefault;

	/**
	 * Indicates if node is an empty element tag
	 * @var bool
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.isemptyelement
	 */
	public bool $isEmptyElement;

	/**
	 * The local name of the node
	 * @var string
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.localname
	 */
	public string $localName;

	/**
	 * The qualified name of the node
	 * @var string
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.name
	 */
	public string $name;

	/**
	 * The URI of the namespace associated with the node
	 * @var string
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.namespaceuri
	 */
	public string $namespaceURI;

	/**
	 * The node type for the node
	 * @var int
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.nodetype
	 */
	public int $nodeType;

	/**
	 * The prefix of the namespace associated with the node
	 * @var string
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.prefix
	 */
	public string $prefix;

	/**
	 * The text value of the node
	 * @var string
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.value
	 */
	public string $value;

	/**
	 * The xml:lang scope which the node resides
	 * @var string
	 * @link http://www.php.net/manual/en/class.xmlreader.php#xmlreader.props.xmllang
	 */
	public string $xmlLang;

	/**
	 * Close the XMLReader input
	 * @link http://www.php.net/manual/en/xmlreader.close.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function close (): bool {}

	/**
	 * Get the value of a named attribute
	 * @link http://www.php.net/manual/en/xmlreader.getattribute.php
	 * @param string $name 
	 * @return string|null The value of the attribute, or null if no attribute with the given
	 * name is found or not positioned on an element node.
	 */
	public function getAttribute (string $name): ?string {}

	/**
	 * Get the value of an attribute by index
	 * @link http://www.php.net/manual/en/xmlreader.getattributeno.php
	 * @param int $index 
	 * @return string|null The value of the attribute, or null if no attribute exists at
	 * index or is not positioned on the element.
	 */
	public function getAttributeNo (int $index): ?string {}

	/**
	 * Get the value of an attribute by localname and URI
	 * @link http://www.php.net/manual/en/xmlreader.getattributens.php
	 * @param string $name 
	 * @param string $namespace 
	 * @return string|null The value of the attribute, or null if no attribute with the given
	 * name and namespace is
	 * found or not positioned of element.
	 */
	public function getAttributeNs (string $name, string $namespace): ?string {}

	/**
	 * Indicates if specified property has been set
	 * @link http://www.php.net/manual/en/xmlreader.getparserproperty.php
	 * @param int $property 
	 * @return bool Returns true on success or false on failure.
	 */
	public function getParserProperty (int $property): bool {}

	/**
	 * Indicates if the parsed document is valid
	 * @link http://www.php.net/manual/en/xmlreader.isvalid.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function isValid (): bool {}

	/**
	 * Lookup namespace for a prefix
	 * @link http://www.php.net/manual/en/xmlreader.lookupnamespace.php
	 * @param string $prefix 
	 * @return string|null The value of the namespace, or null if no namespace exists.
	 */
	public function lookupNamespace (string $prefix): ?string {}

	/**
	 * Move cursor to a named attribute
	 * @link http://www.php.net/manual/en/xmlreader.movetoattribute.php
	 * @param string $name 
	 * @return bool Returns true on success or false on failure.
	 */
	public function moveToAttribute (string $name): bool {}

	/**
	 * Move cursor to an attribute by index
	 * @link http://www.php.net/manual/en/xmlreader.movetoattributeno.php
	 * @param int $index 
	 * @return bool Returns true on success or false on failure.
	 */
	public function moveToAttributeNo (int $index): bool {}

	/**
	 * Move cursor to a named attribute
	 * @link http://www.php.net/manual/en/xmlreader.movetoattributens.php
	 * @param string $name 
	 * @param string $namespace 
	 * @return bool Returns true on success or false on failure.
	 */
	public function moveToAttributeNs (string $name, string $namespace): bool {}

	/**
	 * Position cursor on the parent Element of current Attribute
	 * @link http://www.php.net/manual/en/xmlreader.movetoelement.php
	 * @return bool Returns true if successful and false if it fails or not positioned on 
	 * Attribute when this method is called.
	 */
	public function moveToElement (): bool {}

	/**
	 * Position cursor on the first Attribute
	 * @link http://www.php.net/manual/en/xmlreader.movetofirstattribute.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function moveToFirstAttribute (): bool {}

	/**
	 * Position cursor on the next Attribute
	 * @link http://www.php.net/manual/en/xmlreader.movetonextattribute.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function moveToNextAttribute (): bool {}

	/**
	 * Move to next node in document
	 * @link http://www.php.net/manual/en/xmlreader.read.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function read (): bool {}

	/**
	 * Move cursor to next node skipping all subtrees
	 * @link http://www.php.net/manual/en/xmlreader.next.php
	 * @param string|null $name [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function next (?string $name = null): bool {}

	/**
	 * Set the URI containing the XML to parse
	 * @link http://www.php.net/manual/en/xmlreader.open.php
	 * @param string $uri 
	 * @param string|null $encoding [optional] 
	 * @param int $flags [optional] 
	 * @return bool|XMLReader Returns true on success or false on failure. If called statically, returns an
	 * XMLReader or false on failure.
	 */
	public static function open (string $uri, ?string $encoding = null, int $flags = null): bool|XMLReader {}

	/**
	 * Retrieve XML from current node
	 * @link http://www.php.net/manual/en/xmlreader.readinnerxml.php
	 * @return string Returns the contents of the current node as a string. Empty string on failure.
	 */
	public function readInnerXml (): string {}

	/**
	 * Retrieve XML from current node, including itself
	 * @link http://www.php.net/manual/en/xmlreader.readouterxml.php
	 * @return string Returns the contents of current node, including itself, as a string. Empty string on failure.
	 */
	public function readOuterXml (): string {}

	/**
	 * Reads the contents of the current node as a string
	 * @link http://www.php.net/manual/en/xmlreader.readstring.php
	 * @return string Returns the content of the current node as a string. Empty string on
	 * failure.
	 */
	public function readString (): string {}

	/**
	 * Validate document against XSD
	 * @link http://www.php.net/manual/en/xmlreader.setschema.php
	 * @param string|null $filename 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setSchema (?string $filename): bool {}

	/**
	 * Set parser options
	 * @link http://www.php.net/manual/en/xmlreader.setparserproperty.php
	 * @param int $property 
	 * @param bool $value 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setParserProperty (int $property, bool $value): bool {}

	/**
	 * Set the filename or URI for a RelaxNG Schema
	 * @link http://www.php.net/manual/en/xmlreader.setrelaxngschema.php
	 * @param string|null $filename 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setRelaxNGSchema (?string $filename): bool {}

	/**
	 * Set the data containing a RelaxNG Schema
	 * @link http://www.php.net/manual/en/xmlreader.setrelaxngschemasource.php
	 * @param string|null $source 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setRelaxNGSchemaSource (?string $source): bool {}

	/**
	 * Set the data containing the XML to parse
	 * @link http://www.php.net/manual/en/xmlreader.xml.php
	 * @param string $source 
	 * @param string|null $encoding [optional] 
	 * @param int $flags [optional] 
	 * @return bool|XMLReader Returns true on success or false on failure. If called statically, returns an
	 * XMLReader or false on failure.
	 */
	public static function XML (string $source, ?string $encoding = null, int $flags = null): bool|XMLReader {}

	/**
	 * Returns a copy of the current node as a DOM object
	 * @link http://www.php.net/manual/en/xmlreader.expand.php
	 * @param DOMNode|null $baseNode [optional] 
	 * @return DOMNode|false The resulting DOMNode or false on error.
	 */
	public function expand (?DOMNode $baseNode = null): DOMNode|false {}

}
// End of xmlreader v.8.2.6
