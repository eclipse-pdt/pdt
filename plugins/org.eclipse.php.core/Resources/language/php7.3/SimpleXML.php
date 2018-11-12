<?php

// Start of SimpleXML v.7.3.0

/**
 * Represents an element in an XML document.
 * @link http://www.php.net/manual/en/class.simplexmlelement.php
 */
class SimpleXMLElement implements Traversable {

	/**
	 * Creates a new SimpleXMLElement object
	 * @link http://www.php.net/manual/en/simplexmlelement.construct.php
	 * @param mixed $data
	 * @param mixed $options [optional]
	 * @param mixed $data_is_url [optional]
	 * @param mixed $ns [optional]
	 * @param mixed $is_prefix [optional]
	 */
	final public function __construct ($data, $options = null, $data_is_url = null, $ns = null, $is_prefix = null) {}

	/**
	 * Return a well-formed XML string based on SimpleXML element
	 * @link http://www.php.net/manual/en/simplexmlelement.asxml.php
	 * @param string $filename [optional] If specified, the function writes the data to the file rather than
	 * returning it.
	 * @return mixed If the filename isn't specified, this function
	 * returns a string on success and false on error. If the
	 * parameter is specified, it returns true if the file was written
	 * successfully and false otherwise.
	 */
	public function asXML (string $filename = null) {}

	/**
	 * Alias: SimpleXMLElement::asXML
	 * @link http://www.php.net/manual/en/simplexmlelement.savexml.php
	 * @param mixed $filename [optional]
	 */
	public function saveXML ($filename = null) {}

	/**
	 * Runs XPath query on XML data
	 * @link http://www.php.net/manual/en/simplexmlelement.xpath.php
	 * @param string $path An XPath path
	 * @return array an array of SimpleXMLElement objects or false in
	 * case of an error.
	 */
	public function xpath (string $path) {}

	/**
	 * Creates a prefix/ns context for the next XPath query
	 * @link http://www.php.net/manual/en/simplexmlelement.registerxpathnamespace.php
	 * @param string $prefix The namespace prefix to use in the XPath query for the namespace given in 
	 * ns.
	 * @param string $ns The namespace to use for the XPath query. This must match a namespace in
	 * use by the XML document or the XPath query using 
	 * prefix will not return any results.
	 * @return bool true on success or false on failure
	 */
	public function registerXPathNamespace (string $prefix, string $ns) {}

	/**
	 * Identifies an element's attributes
	 * @link http://www.php.net/manual/en/simplexmlelement.attributes.php
	 * @param string $ns [optional] An optional namespace for the retrieved attributes
	 * @param bool $is_prefix [optional] Default to false
	 * @return SimpleXMLElement a SimpleXMLElement object that can be
	 * iterated over to loop through the attributes on the tag.
	 * <p>
	 * Returns null if called on a SimpleXMLElement
	 * object that already represents an attribute and not a tag.
	 * </p>
	 */
	public function attributes (string $ns = null, bool $is_prefix = null) {}

	/**
	 * Finds children of given node
	 * @link http://www.php.net/manual/en/simplexmlelement.children.php
	 * @param string $ns [optional] An XML namespace.
	 * @param bool $is_prefix [optional] If is_prefix is true,
	 * ns will be regarded as a prefix. If false,
	 * ns will be regarded as a namespace
	 * URL.
	 * @return SimpleXMLElement a SimpleXMLElement element, whether the node 
	 * has children or not.
	 */
	public function children (string $ns = null, bool $is_prefix = null) {}

	/**
	 * Returns namespaces used in document
	 * @link http://www.php.net/manual/en/simplexmlelement.getnamespaces.php
	 * @param bool $recursive [optional] If specified, returns all namespaces used in parent and child nodes. 
	 * Otherwise, returns only namespaces used in root node.
	 * @return array The getNamespaces method returns an array of 
	 * namespace names with their associated URIs.
	 */
	public function getNamespaces (bool $recursive = null) {}

	/**
	 * Returns namespaces declared in document
	 * @link http://www.php.net/manual/en/simplexmlelement.getdocnamespaces.php
	 * @param bool $recursive [optional] If specified, returns all namespaces declared in parent and child nodes. 
	 * Otherwise, returns only namespaces declared in root node.
	 * @param bool $from_root [optional] Allows you to recursively check namespaces under a child node instead of
	 * from the root of the XML doc.
	 * @return array The getDocNamespaces method returns an array 
	 * of namespace names with their associated URIs.
	 */
	public function getDocNamespaces (bool $recursive = null, bool $from_root = null) {}

	/**
	 * Gets the name of the XML element
	 * @link http://www.php.net/manual/en/simplexmlelement.getname.php
	 * @return string The getName method returns as a string the 
	 * name of the XML tag referenced by the SimpleXMLElement object.
	 */
	public function getName () {}

	/**
	 * Adds a child element to the XML node
	 * @link http://www.php.net/manual/en/simplexmlelement.addchild.php
	 * @param string $name The name of the child element to add.
	 * @param string $value [optional] If specified, the value of the child element.
	 * @param string $namespace [optional] If specified, the namespace to which the child element belongs.
	 * @return SimpleXMLElement The addChild method returns a SimpleXMLElement
	 * object representing the child added to the XML node.
	 */
	public function addChild (string $name, string $value = null, string $namespace = null) {}

	/**
	 * Adds an attribute to the SimpleXML element
	 * @link http://www.php.net/manual/en/simplexmlelement.addattribute.php
	 * @param string $name The name of the attribute to add.
	 * @param string $value [optional] The value of the attribute.
	 * @param string $namespace [optional] If specified, the namespace to which the attribute belongs.
	 * @return void 
	 */
	public function addAttribute (string $name, string $value = null, string $namespace = null) {}

	/**
	 * Returns the string content
	 * @link http://www.php.net/manual/en/simplexmlelement.tostring.php
	 * @return string the string content on success or an empty string on failure.
	 */
	public function __toString () {}

	/**
	 * Counts the children of an element
	 * @link http://www.php.net/manual/en/simplexmlelement.count.php
	 * @return int the number of elements of an element.
	 */
	public function count () {}

}

/**
 * The SimpleXMLIterator provides recursive iteration over all nodes of a SimpleXMLElement object.
 * @link http://www.php.net/manual/en/class.simplexmliterator.php
 */
class SimpleXMLIterator extends SimpleXMLElement implements Traversable, RecursiveIterator, Iterator, Countable {

	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.simplexmliterator.php#simplexmliterator.props.name
	 */
	public $name;

	/**
	 * Rewind to the first element
	 * @link http://www.php.net/manual/en/simplexmliterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://www.php.net/manual/en/simplexmliterator.valid.php
	 * @return bool true if the current element is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Returns the current element
	 * @link http://www.php.net/manual/en/simplexmliterator.current.php
	 * @return mixed the current element as a SimpleXMLIterator object or null on failure.
	 */
	public function current () {}

	/**
	 * Return current key
	 * @link http://www.php.net/manual/en/simplexmliterator.key.php
	 * @return mixed the XML tag name of the element referenced by the current SimpleXMLIterator object or false
	 */
	public function key () {}

	/**
	 * Move to next element
	 * @link http://www.php.net/manual/en/simplexmliterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Checks whether the current element has sub elements
	 * @link http://www.php.net/manual/en/simplexmliterator.haschildren.php
	 * @return bool true if the current element has sub-elements, otherwise false
	 */
	public function hasChildren () {}

	/**
	 * Returns the sub-elements of the current element
	 * @link http://www.php.net/manual/en/simplexmliterator.getchildren.php
	 * @return SimpleXMLIterator a SimpleXMLIterator object containing
	 * the sub-elements of the current element.
	 */
	public function getChildren () {}

	/**
	 * Creates a new SimpleXMLElement object
	 * @link http://www.php.net/manual/en/simplexmlelement.construct.php
	 * @param mixed $data
	 * @param mixed $options [optional]
	 * @param mixed $data_is_url [optional]
	 * @param mixed $ns [optional]
	 * @param mixed $is_prefix [optional]
	 */
	final public function __construct ($data, $options = null, $data_is_url = null, $ns = null, $is_prefix = null) {}

	/**
	 * Return a well-formed XML string based on SimpleXML element
	 * @link http://www.php.net/manual/en/simplexmlelement.asxml.php
	 * @param string $filename [optional] If specified, the function writes the data to the file rather than
	 * returning it.
	 * @return mixed If the filename isn't specified, this function
	 * returns a string on success and false on error. If the
	 * parameter is specified, it returns true if the file was written
	 * successfully and false otherwise.
	 */
	public function asXML (string $filename = null) {}

	/**
	 * Alias: SimpleXMLElement::asXML
	 * @link http://www.php.net/manual/en/simplexmlelement.savexml.php
	 * @param mixed $filename [optional]
	 */
	public function saveXML ($filename = null) {}

	/**
	 * Runs XPath query on XML data
	 * @link http://www.php.net/manual/en/simplexmlelement.xpath.php
	 * @param string $path An XPath path
	 * @return array an array of SimpleXMLElement objects or false in
	 * case of an error.
	 */
	public function xpath (string $path) {}

	/**
	 * Creates a prefix/ns context for the next XPath query
	 * @link http://www.php.net/manual/en/simplexmlelement.registerxpathnamespace.php
	 * @param string $prefix The namespace prefix to use in the XPath query for the namespace given in 
	 * ns.
	 * @param string $ns The namespace to use for the XPath query. This must match a namespace in
	 * use by the XML document or the XPath query using 
	 * prefix will not return any results.
	 * @return bool true on success or false on failure
	 */
	public function registerXPathNamespace (string $prefix, string $ns) {}

	/**
	 * Identifies an element's attributes
	 * @link http://www.php.net/manual/en/simplexmlelement.attributes.php
	 * @param string $ns [optional] An optional namespace for the retrieved attributes
	 * @param bool $is_prefix [optional] Default to false
	 * @return SimpleXMLElement a SimpleXMLElement object that can be
	 * iterated over to loop through the attributes on the tag.
	 * <p>
	 * Returns null if called on a SimpleXMLElement
	 * object that already represents an attribute and not a tag.
	 * </p>
	 */
	public function attributes (string $ns = null, bool $is_prefix = null) {}

	/**
	 * Finds children of given node
	 * @link http://www.php.net/manual/en/simplexmlelement.children.php
	 * @param string $ns [optional] An XML namespace.
	 * @param bool $is_prefix [optional] If is_prefix is true,
	 * ns will be regarded as a prefix. If false,
	 * ns will be regarded as a namespace
	 * URL.
	 * @return SimpleXMLElement a SimpleXMLElement element, whether the node 
	 * has children or not.
	 */
	public function children (string $ns = null, bool $is_prefix = null) {}

	/**
	 * Returns namespaces used in document
	 * @link http://www.php.net/manual/en/simplexmlelement.getnamespaces.php
	 * @param bool $recursive [optional] If specified, returns all namespaces used in parent and child nodes. 
	 * Otherwise, returns only namespaces used in root node.
	 * @return array The getNamespaces method returns an array of 
	 * namespace names with their associated URIs.
	 */
	public function getNamespaces (bool $recursive = null) {}

	/**
	 * Returns namespaces declared in document
	 * @link http://www.php.net/manual/en/simplexmlelement.getdocnamespaces.php
	 * @param bool $recursive [optional] If specified, returns all namespaces declared in parent and child nodes. 
	 * Otherwise, returns only namespaces declared in root node.
	 * @param bool $from_root [optional] Allows you to recursively check namespaces under a child node instead of
	 * from the root of the XML doc.
	 * @return array The getDocNamespaces method returns an array 
	 * of namespace names with their associated URIs.
	 */
	public function getDocNamespaces (bool $recursive = null, bool $from_root = null) {}

	/**
	 * Gets the name of the XML element
	 * @link http://www.php.net/manual/en/simplexmlelement.getname.php
	 * @return string The getName method returns as a string the 
	 * name of the XML tag referenced by the SimpleXMLElement object.
	 */
	public function getName () {}

	/**
	 * Adds a child element to the XML node
	 * @link http://www.php.net/manual/en/simplexmlelement.addchild.php
	 * @param string $name The name of the child element to add.
	 * @param string $value [optional] If specified, the value of the child element.
	 * @param string $namespace [optional] If specified, the namespace to which the child element belongs.
	 * @return SimpleXMLElement The addChild method returns a SimpleXMLElement
	 * object representing the child added to the XML node.
	 */
	public function addChild (string $name, string $value = null, string $namespace = null) {}

	/**
	 * Adds an attribute to the SimpleXML element
	 * @link http://www.php.net/manual/en/simplexmlelement.addattribute.php
	 * @param string $name The name of the attribute to add.
	 * @param string $value [optional] The value of the attribute.
	 * @param string $namespace [optional] If specified, the namespace to which the attribute belongs.
	 * @return void 
	 */
	public function addAttribute (string $name, string $value = null, string $namespace = null) {}

	/**
	 * Returns the string content
	 * @link http://www.php.net/manual/en/simplexmlelement.tostring.php
	 * @return string the string content on success or an empty string on failure.
	 */
	public function __toString () {}

	/**
	 * Counts the children of an element
	 * @link http://www.php.net/manual/en/simplexmlelement.count.php
	 * @return int the number of elements of an element.
	 */
	public function count () {}

}

/**
 * Interprets an XML file into an object
 * @link http://www.php.net/manual/en/function.simplexml-load-file.php
 * @param string $filename <p>
 * Path to the XML file
 * </p>
 * <p>
 * Libxml 2 unescapes the URI, so if you want to pass e.g.
 * b&amp;c as the URI parameter a,
 * you have to call
 * simplexml_load_file(rawurlencode('http://example.com/?a=' .
 * urlencode('b&amp;c'))). Since PHP 5.1.0 you don't need to do
 * this because PHP will do it for you.
 * </p>
 * @param string $class_name [optional] You may use this optional parameter so that
 * simplexml_load_file will return an object of 
 * the specified class. That class should extend the 
 * SimpleXMLElement class.
 * @param int $options [optional] Since PHP 5.1.0 and Libxml 2.6.0, you may also use the
 * options parameter to specify additional Libxml parameters.
 * @param string $ns [optional] Namespace prefix or URI.
 * @param bool $is_prefix [optional] true if ns is a prefix, false if it's a URI;
 * defaults to false.
 * @return SimpleXMLElement an object of class SimpleXMLElement with
 * properties containing the data held within the XML document, or false on failure.
 */
function simplexml_load_file (string $filename, string $class_name = null, int $options = null, string $ns = null, bool $is_prefix = null) {}

/**
 * Interprets a string of XML into an object
 * @link http://www.php.net/manual/en/function.simplexml-load-string.php
 * @param string $data A well-formed XML string
 * @param string $class_name [optional] You may use this optional parameter so that
 * simplexml_load_string will return an object of 
 * the specified class. That class should extend the 
 * SimpleXMLElement class.
 * @param int $options [optional] Since PHP 5.1.0 and Libxml 2.6.0, you may also use the
 * options parameter to specify additional Libxml parameters.
 * @param string $ns [optional] Namespace prefix or URI.
 * @param bool $is_prefix [optional] true if ns is a prefix, false if it's a URI;
 * defaults to false.
 * @return SimpleXMLElement an object of class SimpleXMLElement with
 * properties containing the data held within the xml document, or false on failure.
 */
function simplexml_load_string (string $data, string $class_name = null, int $options = null, string $ns = null, bool $is_prefix = null) {}

/**
 * Get a SimpleXMLElement object from a DOM node
 * @link http://www.php.net/manual/en/function.simplexml-import-dom.php
 * @param DOMNode $node A DOM Element node
 * @param string $class_name [optional] You may use this optional parameter so that
 * simplexml_import_dom will return an object of 
 * the specified class. That class should extend the 
 * SimpleXMLElement class.
 * @return SimpleXMLElement a SimpleXMLElement or false on failure.
 */
function simplexml_import_dom (DOMNode $node, string $class_name = null) {}

// End of SimpleXML v.7.3.0
