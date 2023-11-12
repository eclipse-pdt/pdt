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
	 * @param string $expression 
	 * @return array|null|false Returns an array of SimpleXMLElement objects on success; or null or false in
	 * case of an error.
	 */
	public function xpath (string $expression): array|null|false {}

	/**
	 * Creates a prefix/ns context for the next XPath query
	 * @link http://www.php.net/manual/en/simplexmlelement.registerxpathnamespace.php
	 * @param string $prefix 
	 * @param string $namespace 
	 * @return bool Returns true on success or false on failure.
	 */
	public function registerXPathNamespace (string $prefix, string $namespace): bool {}

	/**
	 * Return a well-formed XML string based on SimpleXML element
	 * @link http://www.php.net/manual/en/simplexmlelement.asxml.php
	 * @param string|null $filename [optional] 
	 * @return string|bool If the filename isn't specified, this function
	 * returns a string on success and false on error. If the
	 * parameter is specified, it returns true if the file was written
	 * successfully and false otherwise.
	 */
	public function asXML (?string $filename = null): string|bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $filename [optional]
	 */
	public function saveXML (?string $filename = NULL) {}

	/**
	 * Returns namespaces used in document
	 * @link http://www.php.net/manual/en/simplexmlelement.getnamespaces.php
	 * @param bool $recursive [optional] 
	 * @return array The getNamespaces method returns an array of 
	 * namespace names with their associated URIs.
	 */
	public function getNamespaces (bool $recursive = false): array {}

	/**
	 * Returns namespaces declared in document
	 * @link http://www.php.net/manual/en/simplexmlelement.getdocnamespaces.php
	 * @param bool $recursive [optional] 
	 * @param bool $fromRoot [optional] 
	 * @return array|false The getDocNamespaces method returns an array 
	 * of namespace names with their associated URIs.
	 */
	public function getDocNamespaces (bool $recursive = false, bool $fromRoot = true): array|false {}

	/**
	 * Finds children of given node
	 * @link http://www.php.net/manual/en/simplexmlelement.children.php
	 * @param string|null $namespaceOrPrefix [optional] 
	 * @param bool $isPrefix [optional] 
	 * @return SimpleXMLElement|null Returns a SimpleXMLElement element, whether the node 
	 * has children or not, unless the node represents an attribute, in which case
	 * null is returned.
	 */
	public function children (?string $namespaceOrPrefix = null, bool $isPrefix = false): ?SimpleXMLElement {}

	/**
	 * Identifies an element's attributes
	 * @link http://www.php.net/manual/en/simplexmlelement.attributes.php
	 * @param string|null $namespaceOrPrefix [optional] 
	 * @param bool $isPrefix [optional] 
	 * @return SimpleXMLElement|null Returns a SimpleXMLElement object that can be
	 * iterated over to loop through the attributes on the tag.
	 * <p>Returns null if called on a SimpleXMLElement
	 * object that already represents an attribute and not a tag.</p>
	 */
	public function attributes (?string $namespaceOrPrefix = null, bool $isPrefix = false): ?SimpleXMLElement {}

	/**
	 * Creates a new SimpleXMLElement object
	 * @link http://www.php.net/manual/en/simplexmlelement.construct.php
	 * @param string $data 
	 * @param int $options [optional] 
	 * @param bool $dataIsURL [optional] 
	 * @param string $namespaceOrPrefix [optional] 
	 * @param bool $isPrefix [optional] 
	 * @return string 
	 */
	public function __construct (string $data, int $options = null, bool $dataIsURL = false, string $namespaceOrPrefix = '""', bool $isPrefix = false): string {}

	/**
	 * Adds a child element to the XML node
	 * @link http://www.php.net/manual/en/simplexmlelement.addchild.php
	 * @param string $qualifiedName 
	 * @param string|null $value [optional] 
	 * @param string|null $namespace [optional] 
	 * @return SimpleXMLElement|null The addChild method returns a SimpleXMLElement
	 * object representing the child added to the XML node on success; null on failure.
	 */
	public function addChild (string $qualifiedName, ?string $value = null, ?string $namespace = null): ?SimpleXMLElement {}

	/**
	 * Adds an attribute to the SimpleXML element
	 * @link http://www.php.net/manual/en/simplexmlelement.addattribute.php
	 * @param string $qualifiedName 
	 * @param string $value 
	 * @param string|null $namespace [optional] 
	 * @return void No value is returned.
	 */
	public function addAttribute (string $qualifiedName, string $value, ?string $namespace = null): void {}

	/**
	 * Gets the name of the XML element
	 * @link http://www.php.net/manual/en/simplexmlelement.getname.php
	 * @return string The getName method returns as a string the 
	 * name of the XML tag referenced by the SimpleXMLElement object.
	 */
	public function getName (): string {}

	/**
	 * Returns the string content
	 * @link http://www.php.net/manual/en/simplexmlelement.tostring.php
	 * @return string Returns the string content on success or an empty string on failure.
	 */
	public function __toString (): string {}

	/**
	 * Counts the children of an element
	 * @link http://www.php.net/manual/en/simplexmlelement.count.php
	 * @return int Returns the number of elements of an element.
	 */
	public function count (): int {}

	/**
	 * Rewind to the first element
	 * @link http://www.php.net/manual/en/simplexmlelement.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Check whether the current element is valid
	 * @link http://www.php.net/manual/en/simplexmlelement.valid.php
	 * @return bool Returns true if the current element is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Returns the current element
	 * @link http://www.php.net/manual/en/simplexmlelement.current.php
	 * @return SimpleXMLElement Returns the current element as a SimpleXMLElement object.
	 */
	public function current (): SimpleXMLElement {}

	/**
	 * Return current key
	 * @link http://www.php.net/manual/en/simplexmlelement.key.php
	 * @return string Returns the XML tag name of the element referenced by the current SimpleXMLElement object.
	 */
	public function key (): string {}

	/**
	 * Move to next element
	 * @link http://www.php.net/manual/en/simplexmlelement.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Checks whether the current element has sub elements
	 * @link http://www.php.net/manual/en/simplexmlelement.haschildren.php
	 * @return bool true if the current element has sub-elements, otherwise false
	 */
	public function hasChildren (): bool {}

	/**
	 * Returns the sub-elements of the current element
	 * @link http://www.php.net/manual/en/simplexmlelement.getchildren.php
	 * @return SimpleXMLElement|null Returns a SimpleXMLElement object containing
	 * the sub-elements of the current element.
	 */
	public function getChildren (): ?SimpleXMLElement {}

}

/**
 * The SimpleXMLIterator provides recursive iteration over all nodes of a SimpleXMLElement object.
 * @link http://www.php.net/manual/en/class.simplexmliterator.php
 */
class SimpleXMLIterator extends SimpleXMLElement implements Iterator, Traversable, RecursiveIterator, Countable, Stringable {

	/**
	 * Runs XPath query on XML data
	 * @link http://www.php.net/manual/en/simplexmlelement.xpath.php
	 * @param string $expression 
	 * @return array|null|false Returns an array of SimpleXMLElement objects on success; or null or false in
	 * case of an error.
	 */
	public function xpath (string $expression): array|null|false {}

	/**
	 * Creates a prefix/ns context for the next XPath query
	 * @link http://www.php.net/manual/en/simplexmlelement.registerxpathnamespace.php
	 * @param string $prefix 
	 * @param string $namespace 
	 * @return bool Returns true on success or false on failure.
	 */
	public function registerXPathNamespace (string $prefix, string $namespace): bool {}

	/**
	 * Return a well-formed XML string based on SimpleXML element
	 * @link http://www.php.net/manual/en/simplexmlelement.asxml.php
	 * @param string|null $filename [optional] 
	 * @return string|bool If the filename isn't specified, this function
	 * returns a string on success and false on error. If the
	 * parameter is specified, it returns true if the file was written
	 * successfully and false otherwise.
	 */
	public function asXML (?string $filename = null): string|bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $filename [optional]
	 */
	public function saveXML (?string $filename = NULL) {}

	/**
	 * Returns namespaces used in document
	 * @link http://www.php.net/manual/en/simplexmlelement.getnamespaces.php
	 * @param bool $recursive [optional] 
	 * @return array The getNamespaces method returns an array of 
	 * namespace names with their associated URIs.
	 */
	public function getNamespaces (bool $recursive = false): array {}

	/**
	 * Returns namespaces declared in document
	 * @link http://www.php.net/manual/en/simplexmlelement.getdocnamespaces.php
	 * @param bool $recursive [optional] 
	 * @param bool $fromRoot [optional] 
	 * @return array|false The getDocNamespaces method returns an array 
	 * of namespace names with their associated URIs.
	 */
	public function getDocNamespaces (bool $recursive = false, bool $fromRoot = true): array|false {}

	/**
	 * Finds children of given node
	 * @link http://www.php.net/manual/en/simplexmlelement.children.php
	 * @param string|null $namespaceOrPrefix [optional] 
	 * @param bool $isPrefix [optional] 
	 * @return SimpleXMLElement|null Returns a SimpleXMLElement element, whether the node 
	 * has children or not, unless the node represents an attribute, in which case
	 * null is returned.
	 */
	public function children (?string $namespaceOrPrefix = null, bool $isPrefix = false): ?SimpleXMLElement {}

	/**
	 * Identifies an element's attributes
	 * @link http://www.php.net/manual/en/simplexmlelement.attributes.php
	 * @param string|null $namespaceOrPrefix [optional] 
	 * @param bool $isPrefix [optional] 
	 * @return SimpleXMLElement|null Returns a SimpleXMLElement object that can be
	 * iterated over to loop through the attributes on the tag.
	 * <p>Returns null if called on a SimpleXMLElement
	 * object that already represents an attribute and not a tag.</p>
	 */
	public function attributes (?string $namespaceOrPrefix = null, bool $isPrefix = false): ?SimpleXMLElement {}

	/**
	 * Creates a new SimpleXMLElement object
	 * @link http://www.php.net/manual/en/simplexmlelement.construct.php
	 * @param string $data 
	 * @param int $options [optional] 
	 * @param bool $dataIsURL [optional] 
	 * @param string $namespaceOrPrefix [optional] 
	 * @param bool $isPrefix [optional] 
	 * @return string 
	 */
	public function __construct (string $data, int $options = null, bool $dataIsURL = false, string $namespaceOrPrefix = '""', bool $isPrefix = false): string {}

	/**
	 * Adds a child element to the XML node
	 * @link http://www.php.net/manual/en/simplexmlelement.addchild.php
	 * @param string $qualifiedName 
	 * @param string|null $value [optional] 
	 * @param string|null $namespace [optional] 
	 * @return SimpleXMLElement|null The addChild method returns a SimpleXMLElement
	 * object representing the child added to the XML node on success; null on failure.
	 */
	public function addChild (string $qualifiedName, ?string $value = null, ?string $namespace = null): ?SimpleXMLElement {}

	/**
	 * Adds an attribute to the SimpleXML element
	 * @link http://www.php.net/manual/en/simplexmlelement.addattribute.php
	 * @param string $qualifiedName 
	 * @param string $value 
	 * @param string|null $namespace [optional] 
	 * @return void No value is returned.
	 */
	public function addAttribute (string $qualifiedName, string $value, ?string $namespace = null): void {}

	/**
	 * Gets the name of the XML element
	 * @link http://www.php.net/manual/en/simplexmlelement.getname.php
	 * @return string The getName method returns as a string the 
	 * name of the XML tag referenced by the SimpleXMLElement object.
	 */
	public function getName (): string {}

	/**
	 * Returns the string content
	 * @link http://www.php.net/manual/en/simplexmlelement.tostring.php
	 * @return string Returns the string content on success or an empty string on failure.
	 */
	public function __toString (): string {}

	/**
	 * Counts the children of an element
	 * @link http://www.php.net/manual/en/simplexmlelement.count.php
	 * @return int Returns the number of elements of an element.
	 */
	public function count (): int {}

	/**
	 * Rewind to the first element
	 * @link http://www.php.net/manual/en/simplexmlelement.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Check whether the current element is valid
	 * @link http://www.php.net/manual/en/simplexmlelement.valid.php
	 * @return bool Returns true if the current element is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Returns the current element
	 * @link http://www.php.net/manual/en/simplexmlelement.current.php
	 * @return SimpleXMLElement Returns the current element as a SimpleXMLElement object.
	 */
	public function current (): SimpleXMLElement {}

	/**
	 * Return current key
	 * @link http://www.php.net/manual/en/simplexmlelement.key.php
	 * @return string Returns the XML tag name of the element referenced by the current SimpleXMLElement object.
	 */
	public function key (): string {}

	/**
	 * Move to next element
	 * @link http://www.php.net/manual/en/simplexmlelement.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Checks whether the current element has sub elements
	 * @link http://www.php.net/manual/en/simplexmlelement.haschildren.php
	 * @return bool true if the current element has sub-elements, otherwise false
	 */
	public function hasChildren (): bool {}

	/**
	 * Returns the sub-elements of the current element
	 * @link http://www.php.net/manual/en/simplexmlelement.getchildren.php
	 * @return SimpleXMLElement|null Returns a SimpleXMLElement object containing
	 * the sub-elements of the current element.
	 */
	public function getChildren (): ?SimpleXMLElement {}

}

/**
 * Interprets an XML file into an object
 * @link http://www.php.net/manual/en/function.simplexml-load-file.php
 * @param string $filename 
 * @param string|null $class_name [optional] 
 * @param int $options [optional] 
 * @param string $namespace_or_prefix [optional] 
 * @param bool $is_prefix [optional] 
 * @return SimpleXMLElement|false Returns an object of class SimpleXMLElement with
 * properties containing the data held within the XML document, or false on failure.
 */
function simplexml_load_file (string $filename, ?string $class_name = 'SimpleXMLElement::class', int $options = null, string $namespace_or_prefix = '""', bool $is_prefix = false): SimpleXMLElement|false {}

/**
 * Interprets a string of XML into an object
 * @link http://www.php.net/manual/en/function.simplexml-load-string.php
 * @param string $data 
 * @param string|null $class_name [optional] 
 * @param int $options [optional] 
 * @param string $namespace_or_prefix [optional] 
 * @param bool $is_prefix [optional] 
 * @return SimpleXMLElement|false Returns an object of class SimpleXMLElement with
 * properties containing the data held within the xml document, or false on failure.
 */
function simplexml_load_string (string $data, ?string $class_name = 'SimpleXMLElement::class', int $options = null, string $namespace_or_prefix = '""', bool $is_prefix = false): SimpleXMLElement|false {}

/**
 * Get a SimpleXMLElement object from a DOM node
 * @link http://www.php.net/manual/en/function.simplexml-import-dom.php
 * @param SimpleXMLElement|DOMNode $node 
 * @param string|null $class_name [optional] 
 * @return SimpleXMLElement|null Returns a SimpleXMLElement or null on failure.
 */
function simplexml_import_dom (SimpleXMLElement|DOMNode $node, ?string $class_name = 'SimpleXMLElement::class'): ?SimpleXMLElement {}

// End of SimpleXML v.8.2.6
