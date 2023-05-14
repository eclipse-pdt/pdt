<?php

// Start of SimpleXML v.8.2.6

/**
 * Represents an element in an XML document.
 * @link http://www.php.net/manual/en/class.simplexmlelement.php
 */
class SimpleXMLElement implements Stringable, Countable, RecursiveIterator, Traversable, Iterator {

	/**
	 * Runs XPath query on XML data
	 * @link http://www.php.net/manual/en/simplexmlelement.xpath.php
	 * @param string $expression An XPath path
	 * @return mixed an array of SimpleXMLElement objects on success; or null or false in
	 * case of an error.
	 */
	public function xpath (string $expression) {}

	/**
	 * Creates a prefix/ns context for the next XPath query
	 * @link http://www.php.net/manual/en/simplexmlelement.registerxpathnamespace.php
	 * @param string $prefix The namespace prefix to use in the XPath query for the namespace given in 
	 * namespace.
	 * @param string $namespace The namespace to use for the XPath query. This must match a namespace in
	 * use by the XML document or the XPath query using 
	 * prefix will not return any results.
	 * @return bool true on success or false on failure
	 */
	public function registerXPathNamespace (string $prefix, string $namespace) {}

	/**
	 * Return a well-formed XML string based on SimpleXML element
	 * @link http://www.php.net/manual/en/simplexmlelement.asxml.php
	 * @param mixed $filename [optional] If a string value is provided, the function writes the data to the file rather than
	 * returning it.
	 * @return mixed If the filename isn't specified, this function
	 * returns a string on success and false on error. If the
	 * parameter is specified, it returns true if the file was written
	 * successfully and false otherwise.
	 */
	public function asXML ($filename = null) {}

	/**
	 * Alias: SimpleXMLElement::asXML
	 * @link http://www.php.net/manual/en/simplexmlelement.savexml.php
	 * @param ?string|null $filename [optional]
	 */
	public function saveXML (?string|null $filename = null) {}

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
	 * @param bool $fromRoot [optional] Allows you to recursively check namespaces under a child node instead of
	 * from the root of the XML doc.
	 * @return mixed The getDocNamespaces method returns an array 
	 * of namespace names with their associated URIs.
	 */
	public function getDocNamespaces (bool $recursive = null, bool $fromRoot = null) {}

	/**
	 * Finds children of given node
	 * @link http://www.php.net/manual/en/simplexmlelement.children.php
	 * @param mixed $namespaceOrPrefix [optional] An XML namespace.
	 * @param bool $isPrefix [optional] If isPrefix is true,
	 * namespaceOrPrefix will be regarded as a prefix. If false,
	 * namespaceOrPrefix will be regarded as a namespace
	 * URL.
	 * @return mixed a SimpleXMLElement element, whether the node 
	 * has children or not, unless the node represents an attribute, in which case
	 * null is returned.
	 */
	public function children ($namespaceOrPrefix = null, bool $isPrefix = null) {}

	/**
	 * Identifies an element's attributes
	 * @link http://www.php.net/manual/en/simplexmlelement.attributes.php
	 * @param mixed $namespaceOrPrefix [optional] An optional namespace for the retrieved attributes
	 * @param bool $isPrefix [optional] Default to false
	 * @return mixed a SimpleXMLElement object that can be
	 * iterated over to loop through the attributes on the tag.
	 * <p>
	 * Returns null if called on a SimpleXMLElement
	 * object that already represents an attribute and not a tag.
	 * </p>
	 */
	public function attributes ($namespaceOrPrefix = null, bool $isPrefix = null) {}

	/**
	 * Creates a new SimpleXMLElement object
	 * @link http://www.php.net/manual/en/simplexmlelement.construct.php
	 * @param string $data
	 * @param int $options [optional]
	 * @param bool $dataIsURL [optional]
	 * @param string $namespaceOrPrefix [optional]
	 * @param bool $isPrefix [optional]
	 */
	public function __construct (string $dataint , $options = 0bool , $dataIsURL = ''string , $namespaceOrPrefix = ''bool , $isPrefix = '') {}

	/**
	 * Adds a child element to the XML node
	 * @link http://www.php.net/manual/en/simplexmlelement.addchild.php
	 * @param string $qualifiedName The name of the child element to add.
	 * @param mixed $value [optional] If specified, the value of the child element.
	 * @param mixed $namespace [optional] If specified, the namespace to which the child element belongs.
	 * @return mixed The addChild method returns a SimpleXMLElement
	 * object representing the child added to the XML node on success; null on failure.
	 */
	public function addChild (string $qualifiedName, $value = null, $namespace = null) {}

	/**
	 * Adds an attribute to the SimpleXML element
	 * @link http://www.php.net/manual/en/simplexmlelement.addattribute.php
	 * @param string $qualifiedName The name of the attribute to add.
	 * @param string $value The value of the attribute.
	 * @param mixed $namespace [optional] If specified, the namespace to which the attribute belongs.
	 * @return void 
	 */
	public function addAttribute (string $qualifiedName, string $value, $namespace = null) {}

	/**
	 * Gets the name of the XML element
	 * @link http://www.php.net/manual/en/simplexmlelement.getname.php
	 * @return string The getName method returns as a string the 
	 * name of the XML tag referenced by the SimpleXMLElement object.
	 */
	public function getName () {}

	/**
	 * Returns the string content
	 * @link http://www.php.net/manual/en/simplexmlelement.tostring.php
	 * @return string the string content on success or an empty string on failure.
	 */
	public function __toString (): string {}

	/**
	 * Counts the children of an element
	 * @link http://www.php.net/manual/en/simplexmlelement.count.php
	 * @return int the number of elements of an element.
	 */
	public function count () {}

	/**
	 * Rewind to the first element
	 * @link http://www.php.net/manual/en/simplexmlelement.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://www.php.net/manual/en/simplexmlelement.valid.php
	 * @return bool true if the current element is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Returns the current element
	 * @link http://www.php.net/manual/en/simplexmlelement.current.php
	 * @return SimpleXMLElement the current element as a SimpleXMLElement object.
	 */
	public function current () {}

	/**
	 * Return current key
	 * @link http://www.php.net/manual/en/simplexmlelement.key.php
	 * @return string the XML tag name of the element referenced by the current SimpleXMLElement object.
	 */
	public function key () {}

	/**
	 * Move to next element
	 * @link http://www.php.net/manual/en/simplexmlelement.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Checks whether the current element has sub elements
	 * @link http://www.php.net/manual/en/simplexmlelement.haschildren.php
	 * @return bool true if the current element has sub-elements, otherwise false
	 */
	public function hasChildren () {}

	/**
	 * Returns the sub-elements of the current element
	 * @link http://www.php.net/manual/en/simplexmlelement.getchildren.php
	 * @return mixed a SimpleXMLElement object containing
	 * the sub-elements of the current element.
	 */
	public function getChildren () {}

}

/**
 * The SimpleXMLIterator provides recursive iteration over all nodes of a SimpleXMLElement object.
 * @link http://www.php.net/manual/en/class.simplexmliterator.php
 */
class SimpleXMLIterator extends SimpleXMLElement implements Iterator, Traversable, RecursiveIterator, Countable, Stringable {

	/**
	 * Runs XPath query on XML data
	 * @link http://www.php.net/manual/en/simplexmlelement.xpath.php
	 * @param string $expression An XPath path
	 * @return mixed an array of SimpleXMLElement objects on success; or null or false in
	 * case of an error.
	 */
	public function xpath (string $expression) {}

	/**
	 * Creates a prefix/ns context for the next XPath query
	 * @link http://www.php.net/manual/en/simplexmlelement.registerxpathnamespace.php
	 * @param string $prefix The namespace prefix to use in the XPath query for the namespace given in 
	 * namespace.
	 * @param string $namespace The namespace to use for the XPath query. This must match a namespace in
	 * use by the XML document or the XPath query using 
	 * prefix will not return any results.
	 * @return bool true on success or false on failure
	 */
	public function registerXPathNamespace (string $prefix, string $namespace) {}

	/**
	 * Return a well-formed XML string based on SimpleXML element
	 * @link http://www.php.net/manual/en/simplexmlelement.asxml.php
	 * @param mixed $filename [optional] If a string value is provided, the function writes the data to the file rather than
	 * returning it.
	 * @return mixed If the filename isn't specified, this function
	 * returns a string on success and false on error. If the
	 * parameter is specified, it returns true if the file was written
	 * successfully and false otherwise.
	 */
	public function asXML ($filename = null) {}

	/**
	 * Alias: SimpleXMLElement::asXML
	 * @link http://www.php.net/manual/en/simplexmlelement.savexml.php
	 * @param ?string|null $filename [optional]
	 */
	public function saveXML (?string|null $filename = null) {}

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
	 * @param bool $fromRoot [optional] Allows you to recursively check namespaces under a child node instead of
	 * from the root of the XML doc.
	 * @return mixed The getDocNamespaces method returns an array 
	 * of namespace names with their associated URIs.
	 */
	public function getDocNamespaces (bool $recursive = null, bool $fromRoot = null) {}

	/**
	 * Finds children of given node
	 * @link http://www.php.net/manual/en/simplexmlelement.children.php
	 * @param mixed $namespaceOrPrefix [optional] An XML namespace.
	 * @param bool $isPrefix [optional] If isPrefix is true,
	 * namespaceOrPrefix will be regarded as a prefix. If false,
	 * namespaceOrPrefix will be regarded as a namespace
	 * URL.
	 * @return mixed a SimpleXMLElement element, whether the node 
	 * has children or not, unless the node represents an attribute, in which case
	 * null is returned.
	 */
	public function children ($namespaceOrPrefix = null, bool $isPrefix = null) {}

	/**
	 * Identifies an element's attributes
	 * @link http://www.php.net/manual/en/simplexmlelement.attributes.php
	 * @param mixed $namespaceOrPrefix [optional] An optional namespace for the retrieved attributes
	 * @param bool $isPrefix [optional] Default to false
	 * @return mixed a SimpleXMLElement object that can be
	 * iterated over to loop through the attributes on the tag.
	 * <p>
	 * Returns null if called on a SimpleXMLElement
	 * object that already represents an attribute and not a tag.
	 * </p>
	 */
	public function attributes ($namespaceOrPrefix = null, bool $isPrefix = null) {}

	/**
	 * Creates a new SimpleXMLElement object
	 * @link http://www.php.net/manual/en/simplexmlelement.construct.php
	 * @param string $data
	 * @param int $options [optional]
	 * @param bool $dataIsURL [optional]
	 * @param string $namespaceOrPrefix [optional]
	 * @param bool $isPrefix [optional]
	 */
	public function __construct (string $dataint , $options = 0bool , $dataIsURL = ''string , $namespaceOrPrefix = ''bool , $isPrefix = '') {}

	/**
	 * Adds a child element to the XML node
	 * @link http://www.php.net/manual/en/simplexmlelement.addchild.php
	 * @param string $qualifiedName The name of the child element to add.
	 * @param mixed $value [optional] If specified, the value of the child element.
	 * @param mixed $namespace [optional] If specified, the namespace to which the child element belongs.
	 * @return mixed The addChild method returns a SimpleXMLElement
	 * object representing the child added to the XML node on success; null on failure.
	 */
	public function addChild (string $qualifiedName, $value = null, $namespace = null) {}

	/**
	 * Adds an attribute to the SimpleXML element
	 * @link http://www.php.net/manual/en/simplexmlelement.addattribute.php
	 * @param string $qualifiedName The name of the attribute to add.
	 * @param string $value The value of the attribute.
	 * @param mixed $namespace [optional] If specified, the namespace to which the attribute belongs.
	 * @return void 
	 */
	public function addAttribute (string $qualifiedName, string $value, $namespace = null) {}

	/**
	 * Gets the name of the XML element
	 * @link http://www.php.net/manual/en/simplexmlelement.getname.php
	 * @return string The getName method returns as a string the 
	 * name of the XML tag referenced by the SimpleXMLElement object.
	 */
	public function getName () {}

	/**
	 * Returns the string content
	 * @link http://www.php.net/manual/en/simplexmlelement.tostring.php
	 * @return string the string content on success or an empty string on failure.
	 */
	public function __toString (): string {}

	/**
	 * Counts the children of an element
	 * @link http://www.php.net/manual/en/simplexmlelement.count.php
	 * @return int the number of elements of an element.
	 */
	public function count () {}

	/**
	 * Rewind to the first element
	 * @link http://www.php.net/manual/en/simplexmlelement.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://www.php.net/manual/en/simplexmlelement.valid.php
	 * @return bool true if the current element is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Returns the current element
	 * @link http://www.php.net/manual/en/simplexmlelement.current.php
	 * @return SimpleXMLElement the current element as a SimpleXMLElement object.
	 */
	public function current () {}

	/**
	 * Return current key
	 * @link http://www.php.net/manual/en/simplexmlelement.key.php
	 * @return string the XML tag name of the element referenced by the current SimpleXMLElement object.
	 */
	public function key () {}

	/**
	 * Move to next element
	 * @link http://www.php.net/manual/en/simplexmlelement.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Checks whether the current element has sub elements
	 * @link http://www.php.net/manual/en/simplexmlelement.haschildren.php
	 * @return bool true if the current element has sub-elements, otherwise false
	 */
	public function hasChildren () {}

	/**
	 * Returns the sub-elements of the current element
	 * @link http://www.php.net/manual/en/simplexmlelement.getchildren.php
	 * @return mixed a SimpleXMLElement object containing
	 * the sub-elements of the current element.
	 */
	public function getChildren () {}

}

/**
 * Interprets an XML file into an object
 * @link http://www.php.net/manual/en/function.simplexml-load-file.php
 * @param string $filename Path to the XML file
 * @param mixed $class_name [optional] You may use this optional parameter so that
 * simplexml_load_file will return an object of 
 * the specified class. That class should extend the 
 * SimpleXMLElement class.
 * @param int $options [optional] Since Libxml 2.6.0, you may also use the
 * options parameter to specify additional Libxml parameters.
 * @param string $namespace_or_prefix [optional] Namespace prefix or URI.
 * @param bool $is_prefix [optional] true if namespace_or_prefix is a prefix, false if it's a URI;
 * defaults to false.
 * @return SimpleXMLElement an object of class SimpleXMLElement with
 * properties containing the data held within the XML document, or false on failure.
 */
function simplexml_load_file (string $filename, $class_name = null, int $options = null, string $namespace_or_prefix = null, bool $is_prefix = null): SimpleXMLElement|false {}

/**
 * Interprets a string of XML into an object
 * @link http://www.php.net/manual/en/function.simplexml-load-string.php
 * @param string $data A well-formed XML string
 * @param mixed $class_name [optional] You may use this optional parameter so that
 * simplexml_load_string will return an object of 
 * the specified class. That class should extend the 
 * SimpleXMLElement class.
 * @param int $options [optional] Since Libxml 2.6.0, you may also use the
 * options parameter to specify additional Libxml parameters.
 * @param string $namespace_or_prefix [optional] Namespace prefix or URI.
 * @param bool $is_prefix [optional] true if namespace_or_prefix is a prefix, false if it's a URI;
 * defaults to false.
 * @return SimpleXMLElement an object of class SimpleXMLElement with
 * properties containing the data held within the xml document, or false on failure.
 */
function simplexml_load_string (string $data, $class_name = null, int $options = null, string $namespace_or_prefix = null, bool $is_prefix = null): SimpleXMLElement|false {}

/**
 * Get a SimpleXMLElement object from a DOM node
 * @link http://www.php.net/manual/en/function.simplexml-import-dom.php
 * @param mixed $node A DOM Element node
 * @param mixed $class_name [optional] You may use this optional parameter so that
 * simplexml_import_dom will return an object of 
 * the specified class. That class should extend the 
 * SimpleXMLElement class.
 * @return mixed a SimpleXMLElement or null on failure.
 */
function simplexml_import_dom ($node, $class_name = null): ??SimpleXMLElement {}

// End of SimpleXML v.8.2.6
