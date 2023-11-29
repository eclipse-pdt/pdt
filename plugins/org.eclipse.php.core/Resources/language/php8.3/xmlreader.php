<?php

// Start of xmlreader v.8.3.0

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


	public int $attributeCount;

	public string $baseURI;

	public int $depth;

	public bool $hasAttributes;

	public bool $hasValue;

	public bool $isDefault;

	public bool $isEmptyElement;

	public string $localName;

	public string $name;

	public string $namespaceURI;

	public int $nodeType;

	public string $prefix;

	public string $value;

	public string $xmlLang;

	/**
	 * {@inheritdoc}
	 */
	public function close () {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getAttribute (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function getAttributeNo (int $index) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param string $namespace
	 */
	public function getAttributeNs (string $name, string $namespace) {}

	/**
	 * {@inheritdoc}
	 * @param int $property
	 */
	public function getParserProperty (int $property) {}

	/**
	 * {@inheritdoc}
	 */
	public function isValid () {}

	/**
	 * {@inheritdoc}
	 * @param string $prefix
	 */
	public function lookupNamespace (string $prefix) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function moveToAttribute (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function moveToAttributeNo (int $index) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param string $namespace
	 */
	public function moveToAttributeNs (string $name, string $namespace) {}

	/**
	 * {@inheritdoc}
	 */
	public function moveToElement () {}

	/**
	 * {@inheritdoc}
	 */
	public function moveToFirstAttribute () {}

	/**
	 * {@inheritdoc}
	 */
	public function moveToNextAttribute () {}

	/**
	 * {@inheritdoc}
	 */
	public function read () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $name [optional]
	 */
	public function next (?string $name = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param string|null $encoding [optional]
	 * @param int $flags [optional]
	 */
	public static function open (string $uri, ?string $encoding = NULL, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function readInnerXml () {}

	/**
	 * {@inheritdoc}
	 */
	public function readOuterXml () {}

	/**
	 * {@inheritdoc}
	 */
	public function readString () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $filename
	 */
	public function setSchema (?string $filename = null) {}

	/**
	 * {@inheritdoc}
	 * @param int $property
	 * @param bool $value
	 */
	public function setParserProperty (int $property, bool $value) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $filename
	 */
	public function setRelaxNGSchema (?string $filename = null) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $source
	 */
	public function setRelaxNGSchemaSource (?string $source = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $source
	 * @param string|null $encoding [optional]
	 * @param int $flags [optional]
	 */
	public static function XML (string $source, ?string $encoding = NULL, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|null $baseNode [optional]
	 */
	public function expand (?DOMNode $baseNode = NULL) {}

}
// End of xmlreader v.8.3.0
