<?php

// Start of SimpleXML v.0.1

class SimpleXMLElement implements Traversable {

	/**
	 * Creates a new SimpleXMLElement object
	 * @link http://php.net/manual/en/function.simplexml-element-construct.php
	 */
	final public function __construct () {}

	/**
	 * Return a well-formed XML string based on SimpleXML element
	 * @link http://php.net/manual/en/function.simplexml-element-asXML.php
	 * @param filename string[optional]
	 * @return mixed 
	 */
	public function asXML ($filename = null) {}

	public function saveXML () {}

	/**
	 * Runs XPath query on XML data
	 * @link http://php.net/manual/en/function.simplexml-element-xpath.php
	 * @param path string
	 * @return array an array of SimpleXMLElement objects or false in
	 */
	public function xpath ($path) {}

	/**
	 * Creates a prefix/ns context for the next XPath query
	 * @link http://php.net/manual/en/function.simplexml-element-registerXPathNamespace.php
	 * @param prefix string
	 * @param ns string
	 * @return bool 
	 */
	public function registerXPathNamespace ($prefix, $ns) {}

	/**
	 * Identifies an element's attributes
	 * @link http://php.net/manual/en/function.simplexml-element-attributes.php
	 * @param ns string[optional]
	 * @param is_prefix bool[optional]
	 * @return SimpleXMLElement 
	 */
	public function attributes ($ns = null, $is_prefix = null) {}

	/**
	 * Finds children of given node
	 * @link http://php.net/manual/en/function.simplexml-element-children.php
	 * @param ns string[optional]
	 * @param is_prefix bool[optional]
	 * @return SimpleXMLElement 
	 */
	public function children ($ns = null, $is_prefix = null) {}

	/**
	 * Returns namespaces used in document
	 * @link http://php.net/manual/en/function.simplexml-element-getNamespaces.php
	 * @param recursive bool[optional]
	 * @return array 
	 */
	public function getNamespaces ($recursive = null) {}

	/**
	 * Returns namespaces declared in document
	 * @link http://php.net/manual/en/function.simplexml-element-getDocNamespaces.php
	 * @param recursive bool[optional]
	 * @return array 
	 */
	public function getDocNamespaces ($recursive = null) {}

	/**
	 * Gets the name of the XML element
	 * @link http://php.net/manual/en/function.simplexml-element-getName.php
	 * @return string 
	 */
	public function getName () {}

	/**
	 * Adds a child element to the XML node
	 * @link http://php.net/manual/en/function.simplexml-element-addChild.php
	 * @param name string
	 * @param value string[optional]
	 * @param namespace string[optional]
	 * @return SimpleXMLElement 
	 */
	public function addChild ($name, $value = null, $namespace = null) {}

	/**
	 * Adds an attribute to the SimpleXML element
	 * @link http://php.net/manual/en/function.simplexml-element-addAttribute.php
	 * @param name string
	 * @param value string
	 * @param namespace string[optional]
	 * @return void 
	 */
	public function addAttribute ($name, $value, $namespace = null) {}

}

/**
 * Interprets an XML file into an object
 * @link http://php.net/manual/en/function.simplexml-load-file.php
 * @param filename string
 * @param class_name string[optional]
 * @param options int[optional]
 * @param ns string[optional]
 * @param is_prefix bool[optional]
 * @return object an object of class SimpleXMLElement with
 */
function simplexml_load_file ($filename, $class_name = null, $options = null, $ns = null, $is_prefix = null) {}

/**
 * Interprets a string of XML into an object
 * @link http://php.net/manual/en/function.simplexml-load-string.php
 * @param data string
 * @param class_name string[optional]
 * @param options int[optional]
 * @param ns string[optional]
 * @param is_prefix bool[optional]
 * @return object an object of class SimpleXMLElement with
 */
function simplexml_load_string ($data, $class_name = null, $options = null, $ns = null, $is_prefix = null) {}

/**
 * Get a <literal>SimpleXMLElement</literal> object from a DOM node.
 * @link http://php.net/manual/en/function.simplexml-import-dom.php
 * @param node DOMNode
 * @param class_name string[optional]
 * @return SimpleXMLElement a SimpleXMLElement or false on failure.
 */
function simplexml_import_dom (DOMNode $node, $class_name = null) {}

// End of SimpleXML v.0.1
?>
