<?php

// Start of dom v.20031129

final class DOMException extends Exception implements Throwable {
	protected $message;
	protected $file;
	protected $line;
	public $code;


	final private function __clone () {}

	/**
	 * @param $message [optional]
	 * @param $code [optional]
	 * @param $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class DOMStringList  {

	/**
	 * @param $index
	 */
	public function item ($index) {}

}

class DOMNameList  {

	/**
	 * @param $index
	 */
	public function getName ($index) {}

	/**
	 * @param $index
	 */
	public function getNamespaceURI ($index) {}

}

class DOMImplementationList  {

	/**
	 * @param $index
	 */
	public function item ($index) {}

}

class DOMImplementationSource  {

	/**
	 * @param $features
	 */
	public function getDomimplementation ($features) {}

	/**
	 * @param $features
	 */
	public function getDomimplementations ($features) {}

}

class DOMImplementation  {

	/**
	 * @param $feature
	 * @param $version
	 */
	public function getFeature ($feature, $version) {}

	/**
	 * Test if the DOM implementation implements a specific feature
	 * @link http://www.php.net/manual/en/domimplementation.hasfeature.php
	 * @param string $feature The feature to test.
	 * @param string $version The version number of the feature to test. In 
	 * level 2, this can be either 2.0 or
	 * 1.0.
	 * @return bool true on success or false on failure
	 */
	public function hasFeature (string $feature, string $version) {}

	/**
	 * Creates an empty DOMDocumentType object
	 * @link http://www.php.net/manual/en/domimplementation.createdocumenttype.php
	 * @param string $qualifiedName [optional] The qualified name of the document type to create.
	 * @param string $publicId [optional] The external subset public identifier.
	 * @param string $systemId [optional] The external subset system identifier.
	 * @return DOMDocumentType A new DOMDocumentType node with its 
	 * ownerDocument set to null.
	 */
	public function createDocumentType (string $qualifiedName = null, string $publicId = null, string $systemId = null) {}

	/**
	 * Creates a DOMDocument object of the specified type with its document element
	 * @link http://www.php.net/manual/en/domimplementation.createdocument.php
	 * @param string $namespaceURI [optional] The namespace URI of the document element to create.
	 * @param string $qualifiedName [optional] The qualified name of the document element to create.
	 * @param DOMDocumentType $doctype [optional] The type of document to create or null.
	 * @return DOMDocument A new DOMDocument object. If 
	 * namespaceURI, qualifiedName, 
	 * and doctype are null, the returned 
	 * DOMDocument is empty with no document element
	 */
	public function createDocument (string $namespaceURI = null, string $qualifiedName = null, DOMDocumentType $doctype = null) {}

}

class DOMNode  {

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $newnode The new node.
	 * @param DOMNode $refnode [optional] The reference node. If not supplied, newnode is
	 * appended to the children.
	 * @return DOMNode The inserted node.
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $newnode The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument->createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $oldnode The old node.
	 * @return DOMNode The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $oldnode The removed child.
	 * @return DOMNode If the child could be removed the function returns the old child.
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $newnode The appended child.
	 * @return DOMNode The node added.
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return DOMNode The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature The feature to test. See the example of 
	 * DOMImplementation::hasFeature for a
	 * list of features.
	 * @param string $version The version number of the feature to test.
	 * @return bool true on success or false on failure
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * @param DOMNode $other
	 */
	public function compareDocumentPosition (DOMNode $other) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $node The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespaceURI The namespace URI.
	 * @return string The prefix of the namespace.
	 */
	public function lookupPrefix (string $namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespaceURI The namespace URI to look for.
	 * @return bool Return true if namespaceURI is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceUri (string $prefix) {}

	/**
	 * @param DOMNode $arg
	 */
	public function isEqualNode (DOMNode $arg) {}

	/**
	 * @param $feature
	 * @param $version
	 */
	public function getFeature ($feature, $version) {}

	/**
	 * @param $key
	 * @param $data
	 * @param $handler
	 */
	public function setUserData ($key, $data, $handler) {}

	/**
	 * @param $key
	 */
	public function getUserData ($key) {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

}

class DOMNameSpaceNode  {
}

class DOMDocumentFragment extends DOMNode  {

	public function __construct () {}

	/**
	 * Append raw XML data
	 * @link http://www.php.net/manual/en/domdocumentfragment.appendxml.php
	 * @param string $data XML to append.
	 * @return bool true on success or false on failure
	 */
	public function appendXML (string $data) {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $newnode The new node.
	 * @param DOMNode $refnode [optional] The reference node. If not supplied, newnode is
	 * appended to the children.
	 * @return DOMNode The inserted node.
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $newnode The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument->createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $oldnode The old node.
	 * @return DOMNode The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $oldnode The removed child.
	 * @return DOMNode If the child could be removed the function returns the old child.
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $newnode The appended child.
	 * @return DOMNode The node added.
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return DOMNode The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature The feature to test. See the example of 
	 * DOMImplementation::hasFeature for a
	 * list of features.
	 * @param string $version The version number of the feature to test.
	 * @return bool true on success or false on failure
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * @param DOMNode $other
	 */
	public function compareDocumentPosition (DOMNode $other) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $node The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespaceURI The namespace URI.
	 * @return string The prefix of the namespace.
	 */
	public function lookupPrefix (string $namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespaceURI The namespace URI to look for.
	 * @return bool Return true if namespaceURI is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceUri (string $prefix) {}

	/**
	 * @param DOMNode $arg
	 */
	public function isEqualNode (DOMNode $arg) {}

	/**
	 * @param $feature
	 * @param $version
	 */
	public function getFeature ($feature, $version) {}

	/**
	 * @param $key
	 * @param $data
	 * @param $handler
	 */
	public function setUserData ($key, $data, $handler) {}

	/**
	 * @param $key
	 */
	public function getUserData ($key) {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

}

class DOMDocument extends DOMNode  {

	/**
	 * Create new element node
	 * @link http://www.php.net/manual/en/domdocument.createelement.php
	 * @param string $name The tag name of the element.
	 * @param string $value [optional] <p>
	 * The value of the element. By default, an empty element will be created.
	 * The value can also be set later with DOMElement::$nodeValue.
	 * </p>
	 * <p>
	 * The value is used verbatim except that the &lt; and &gt; entity
	 * references will escaped. Note that &amp; has to be manually escaped;
	 * otherwise it is regarded as starting an entity reference. Also " won't be
	 * escaped.
	 * </p>
	 * @return DOMElement a new instance of class DOMElement or false
	 * if an error occurred.
	 */
	public function createElement (string $name, string $value = null) {}

	/**
	 * Create new document fragment
	 * @link http://www.php.net/manual/en/domdocument.createdocumentfragment.php
	 * @return DOMDocumentFragment The new DOMDocumentFragment or false if an error occurred.
	 */
	public function createDocumentFragment () {}

	/**
	 * Create new text node
	 * @link http://www.php.net/manual/en/domdocument.createtextnode.php
	 * @param string $content The content of the text.
	 * @return DOMText The new DOMText or false if an error occurred.
	 */
	public function createTextNode (string $content) {}

	/**
	 * Create new comment node
	 * @link http://www.php.net/manual/en/domdocument.createcomment.php
	 * @param string $data The content of the comment.
	 * @return DOMComment The new DOMComment or false if an error occurred.
	 */
	public function createComment (string $data) {}

	/**
	 * Create new cdata node
	 * @link http://www.php.net/manual/en/domdocument.createcdatasection.php
	 * @param string $data The content of the cdata.
	 * @return DOMCDATASection The new DOMCDATASection or false if an error occurred.
	 */
	public function createCDATASection (string $data) {}

	/**
	 * Creates new PI node
	 * @link http://www.php.net/manual/en/domdocument.createprocessinginstruction.php
	 * @param string $target The target of the processing instruction.
	 * @param string $data [optional] The content of the processing instruction.
	 * @return DOMProcessingInstruction The new DOMProcessingInstruction or false if an error occurred.
	 */
	public function createProcessingInstruction (string $target, string $data = null) {}

	/**
	 * Create new attribute
	 * @link http://www.php.net/manual/en/domdocument.createattribute.php
	 * @param string $name The name of the attribute.
	 * @return DOMAttr The new DOMAttr or false if an error occurred.
	 */
	public function createAttribute (string $name) {}

	/**
	 * Create new entity reference node
	 * @link http://www.php.net/manual/en/domdocument.createentityreference.php
	 * @param string $name The content of the entity reference, e.g. the entity reference minus
	 * the leading &amp; and the trailing
	 * ; characters.
	 * @return DOMEntityReference The new DOMEntityReference or false if an error
	 * occurred.
	 */
	public function createEntityReference (string $name) {}

	/**
	 * Searches for all elements with given local tag name
	 * @link http://www.php.net/manual/en/domdocument.getelementsbytagname.php
	 * @param string $name The local name (without namespace) of the tag to match on. The special value &#42;
	 * matches all tags.
	 * @return DOMNodeList A new DOMNodeList object containing all the matched 
	 * elements.
	 */
	public function getElementsByTagName (string $name) {}

	/**
	 * Import node into current document
	 * @link http://www.php.net/manual/en/domdocument.importnode.php
	 * @param DOMNode $importedNode The node to import.
	 * @param bool $deep [optional] <p>
	 * If set to true, this method will recursively import the subtree under
	 * the importedNode.
	 * </p>
	 * <p>
	 * To copy the nodes attributes deep needs to be set to true
	 * </p>
	 * @return DOMNode The copied node or false, if it cannot be copied.
	 */
	public function importNode (DOMNode $importedNode, bool $deep = null) {}

	/**
	 * Create new element node with an associated namespace
	 * @link http://www.php.net/manual/en/domdocument.createelementns.php
	 * @param string $namespaceURI The URI of the namespace.
	 * @param string $qualifiedName The qualified name of the element, as prefix:tagname.
	 * @param string $value [optional] The value of the element. By default, an empty element will be created.
	 * You can also set the value later with DOMElement::$nodeValue.
	 * @return DOMElement The new DOMElement or false if an error occurred.
	 */
	public function createElementNS (string $namespaceURI, string $qualifiedName, string $value = null) {}

	/**
	 * Create new attribute node with an associated namespace
	 * @link http://www.php.net/manual/en/domdocument.createattributens.php
	 * @param string $namespaceURI The URI of the namespace.
	 * @param string $qualifiedName The tag name and prefix of the attribute, as prefix:tagname.
	 * @return DOMAttr The new DOMAttr or false if an error occurred.
	 */
	public function createAttributeNS (string $namespaceURI, string $qualifiedName) {}

	/**
	 * Searches for all elements with given tag name in specified namespace
	 * @link http://www.php.net/manual/en/domdocument.getelementsbytagnamens.php
	 * @param string $namespaceURI The namespace URI of the elements to match on. 
	 * The special value &#42; matches all namespaces.
	 * @param string $localName The local name of the elements to match on. 
	 * The special value &#42; matches all local names.
	 * @return DOMNodeList A new DOMNodeList object containing all the matched 
	 * elements.
	 */
	public function getElementsByTagNameNS (string $namespaceURI, string $localName) {}

	/**
	 * Searches for an element with a certain id
	 * @link http://www.php.net/manual/en/domdocument.getelementbyid.php
	 * @param string $elementId The unique id value for an element.
	 * @return DOMElement the DOMElement or null if the element is
	 * not found.
	 */
	public function getElementById (string $elementId) {}

	/**
	 * @param DOMNode $source
	 */
	public function adoptNode (DOMNode $source) {}

	/**
	 * Normalizes the document
	 * @link http://www.php.net/manual/en/domdocument.normalizedocument.php
	 * @return void 
	 */
	public function normalizeDocument () {}

	/**
	 * @param DOMNode $node
	 * @param $namespaceURI
	 * @param $qualifiedName
	 */
	public function renameNode (DOMNode $node, $namespaceURI, $qualifiedName) {}

	/**
	 * Load XML from a file
	 * @link http://www.php.net/manual/en/domdocument.load.php
	 * @param string $filename The path to the XML document.
	 * @param int $options [optional] Bitwise OR
	 * of the libxml option constants.
	 * @return mixed true on success or false on failure If called statically, returns a
	 * DOMDocument or false on failure.
	 */
	public function load (string $filename, int $options = null) {}

	/**
	 * Dumps the internal XML tree back into a file
	 * @link http://www.php.net/manual/en/domdocument.save.php
	 * @param string $filename The path to the saved XML document.
	 * @param int $options [optional] Additional Options. Currently only LIBXML_NOEMPTYTAG is supported.
	 * @return int the number of bytes written or false if an error occurred.
	 */
	public function save (string $filename, int $options = null) {}

	/**
	 * Load XML from a string
	 * @link http://www.php.net/manual/en/domdocument.loadxml.php
	 * @param string $source The string containing the XML.
	 * @param int $options [optional] Bitwise OR
	 * of the libxml option constants.
	 * @return mixed true on success or false on failure If called statically, returns a
	 * DOMDocument or false on failure.
	 */
	public function loadXML (string $source, int $options = null) {}

	/**
	 * Dumps the internal XML tree back into a string
	 * @link http://www.php.net/manual/en/domdocument.savexml.php
	 * @param DOMNode $node [optional] Use this parameter to output only a specific node without XML declaration
	 * rather than the entire document.
	 * @param int $options [optional] Additional Options. Currently only LIBXML_NOEMPTYTAG is supported.
	 * @return string the XML, or false if an error occurred.
	 */
	public function saveXML (DOMNode $node = null, int $options = null) {}

	/**
	 * Creates a new DOMDocument object
	 * @link http://www.php.net/manual/en/domdocument.construct.php
	 * @param $version [optional]
	 * @param $encoding [optional]
	 */
	public function __construct ($version = null, $encoding = null) {}

	/**
	 * Validates the document based on its DTD
	 * @link http://www.php.net/manual/en/domdocument.validate.php
	 * @return bool true on success or false on failure
	 * If the document have no DTD attached, this method will return false.
	 */
	public function validate () {}

	/**
	 * Substitutes XIncludes in a DOMDocument Object
	 * @link http://www.php.net/manual/en/domdocument.xinclude.php
	 * @param int $options [optional] libxml parameters. Available
	 * since PHP 5.1.0 and Libxml 2.6.7.
	 * @return int the number of XIncludes in the document, -1 if some processing failed,
	 * or false if there were no substitutions.
	 */
	public function xinclude (int $options = null) {}

	/**
	 * Load HTML from a string
	 * @link http://www.php.net/manual/en/domdocument.loadhtml.php
	 * @param string $source The HTML string.
	 * @param int $options [optional] Since PHP 5.4.0 and Libxml 2.6.0, you may also use the
	 * options parameter to specify additional Libxml parameters.
	 * @return bool true on success or false on failure If called statically, returns a
	 * DOMDocument or false on failure.
	 */
	public function loadHTML (string $source, int $options = null) {}

	/**
	 * Load HTML from a file
	 * @link http://www.php.net/manual/en/domdocument.loadhtmlfile.php
	 * @param string $filename The path to the HTML file.
	 * @param int $options [optional] Since PHP 5.4.0 and Libxml 2.6.0, you may also use the
	 * options parameter to specify additional Libxml parameters.
	 * @return bool true on success or false on failure If called statically, returns a
	 * DOMDocument or false on failure.
	 */
	public function loadHTMLFile (string $filename, int $options = null) {}

	/**
	 * Dumps the internal document into a string using HTML formatting
	 * @link http://www.php.net/manual/en/domdocument.savehtml.php
	 * @param DOMNode $node [optional] Optional parameter to output a subset of the document.
	 * @return string the HTML, or false if an error occurred.
	 */
	public function saveHTML (DOMNode $node = null) {}

	/**
	 * Dumps the internal document into a file using HTML formatting
	 * @link http://www.php.net/manual/en/domdocument.savehtmlfile.php
	 * @param string $filename The path to the saved HTML document.
	 * @return int the number of bytes written or false if an error occurred.
	 */
	public function saveHTMLFile (string $filename) {}

	/**
	 * Validates a document based on a schema
	 * @link http://www.php.net/manual/en/domdocument.schemavalidate.php
	 * @param string $filename The path to the schema.
	 * @param int $flags [optional] A bitmask of Libxml schema validation flags. Currently the only supported value is LIBXML_SCHEMA_CREATE. Available since PHP 5.5.2 and Libxml 2.6.14.
	 * @return bool true on success or false on failure
	 */
	public function schemaValidate (string $filename, int $flags = null) {}

	/**
	 * Validates a document based on a schema
	 * @link http://www.php.net/manual/en/domdocument.schemavalidatesource.php
	 * @param string $source A string containing the schema.
	 * @param int $flags [optional] A bitmask of Libxml schema validation flags. Currently the only supported value is LIBXML_SCHEMA_CREATE. Available since PHP 5.5.2 and Libxml 2.6.14.
	 * @return bool true on success or false on failure
	 */
	public function schemaValidateSource (string $source, int $flags = null) {}

	/**
	 * Performs relaxNG validation on the document
	 * @link http://www.php.net/manual/en/domdocument.relaxngvalidate.php
	 * @param string $filename The RNG file.
	 * @return bool true on success or false on failure
	 */
	public function relaxNGValidate (string $filename) {}

	/**
	 * Performs relaxNG validation on the document
	 * @link http://www.php.net/manual/en/domdocument.relaxngvalidatesource.php
	 * @param string $source A string containing the RNG schema.
	 * @return bool true on success or false on failure
	 */
	public function relaxNGValidateSource (string $source) {}

	/**
	 * Register extended class used to create base node type
	 * @link http://www.php.net/manual/en/domdocument.registernodeclass.php
	 * @param string $baseclass The DOM class that you want to extend. You can find a list of these 
	 * classes in the chapter introduction.
	 * @param string $extendedclass Your extended class name. If null is provided, any previously 
	 * registered class extending baseclass will
	 * be removed.
	 * @return bool true on success or false on failure
	 */
	public function registerNodeClass (string $baseclass, string $extendedclass) {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $newnode The new node.
	 * @param DOMNode $refnode [optional] The reference node. If not supplied, newnode is
	 * appended to the children.
	 * @return DOMNode The inserted node.
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $newnode The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument->createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $oldnode The old node.
	 * @return DOMNode The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $oldnode The removed child.
	 * @return DOMNode If the child could be removed the function returns the old child.
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $newnode The appended child.
	 * @return DOMNode The node added.
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return DOMNode The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature The feature to test. See the example of 
	 * DOMImplementation::hasFeature for a
	 * list of features.
	 * @param string $version The version number of the feature to test.
	 * @return bool true on success or false on failure
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * @param DOMNode $other
	 */
	public function compareDocumentPosition (DOMNode $other) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $node The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespaceURI The namespace URI.
	 * @return string The prefix of the namespace.
	 */
	public function lookupPrefix (string $namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespaceURI The namespace URI to look for.
	 * @return bool Return true if namespaceURI is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceUri (string $prefix) {}

	/**
	 * @param DOMNode $arg
	 */
	public function isEqualNode (DOMNode $arg) {}

	/**
	 * @param $feature
	 * @param $version
	 */
	public function getFeature ($feature, $version) {}

	/**
	 * @param $key
	 * @param $data
	 * @param $handler
	 */
	public function setUserData ($key, $data, $handler) {}

	/**
	 * @param $key
	 */
	public function getUserData ($key) {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

}

class DOMNodeList implements Traversable {

	/**
	 * Retrieves a node specified by index
	 * @link http://www.php.net/manual/en/domnodelist.item.php
	 * @param int $index Index of the node into the collection.
	 * @return DOMNode The node at the indexth position in the 
	 * DOMNodeList, or null if that is not a valid
	 * index.
	 */
	public function item (int $index) {}

}

class DOMNamedNodeMap implements Traversable {

	/**
	 * Retrieves a node specified by name
	 * @link http://www.php.net/manual/en/domnamednodemap.getnameditem.php
	 * @param string $name The nodeName of the node to retrieve.
	 * @return DOMNode A node (of any type) with the specified nodeName, or 
	 * null if no node is found.
	 */
	public function getNamedItem (string $name) {}

	/**
	 * @param DOMNode $arg
	 */
	public function setNamedItem (DOMNode $arg) {}

	/**
	 * @param $name [optional]
	 */
	public function removeNamedItem ($name = null) {}

	/**
	 * Retrieves a node specified by index
	 * @link http://www.php.net/manual/en/domnamednodemap.item.php
	 * @param int $index Index into this map.
	 * @return DOMNode The node at the indexth position in the map, or null
	 * if that is not a valid index (greater than or equal to the number of nodes 
	 * in this map).
	 */
	public function item (int $index) {}

	/**
	 * Retrieves a node specified by local name and namespace URI
	 * @link http://www.php.net/manual/en/domnamednodemap.getnameditemns.php
	 * @param string $namespaceURI The namespace URI of the node to retrieve.
	 * @param string $localName The local name of the node to retrieve.
	 * @return DOMNode A node (of any type) with the specified local name and namespace URI, or 
	 * null if no node is found.
	 */
	public function getNamedItemNS (string $namespaceURI, string $localName) {}

	/**
	 * @param DOMNode $arg [optional]
	 */
	public function setNamedItemNS (DOMNode $arg = null) {}

	/**
	 * @param $namespaceURI [optional]
	 * @param $localName [optional]
	 */
	public function removeNamedItemNS ($namespaceURI = null, $localName = null) {}

}

class DOMCharacterData extends DOMNode  {

	/**
	 * Extracts a range of data from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.substringdata.php
	 * @param int $offset Start offset of substring to extract.
	 * @param int $count The number of characters to extract.
	 * @return string The specified substring. If the sum of offset 
	 * and count exceeds the length, then all 16-bit units 
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count) {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/domcharacterdata.appenddata.php
	 * @param string $data The string to append.
	 * @return void 
	 */
	public function appendData (string $data) {}

	/**
	 * Insert a string at the specified 16-bit unit offset
	 * @link http://www.php.net/manual/en/domcharacterdata.insertdata.php
	 * @param int $offset The character offset at which to insert.
	 * @param string $data The string to insert.
	 * @return void 
	 */
	public function insertData (int $offset, string $data) {}

	/**
	 * Remove a range of characters from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.deletedata.php
	 * @param int $offset The offset from which to start removing.
	 * @param int $count The number of characters to delete. If the sum of 
	 * offset and count exceeds
	 * the length, then all characters to the end of the data are deleted.
	 * @return void 
	 */
	public function deleteData (int $offset, int $count) {}

	/**
	 * Replace a substring within the DOMCharacterData node
	 * @link http://www.php.net/manual/en/domcharacterdata.replacedata.php
	 * @param int $offset The offset from which to start replacing.
	 * @param int $count The number of characters to replace. If the sum of 
	 * offset and count exceeds
	 * the length, then all characters to the end of the data are replaced.
	 * @param string $data The string with which the range must be replaced.
	 * @return void 
	 */
	public function replaceData (int $offset, int $count, string $data) {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $newnode The new node.
	 * @param DOMNode $refnode [optional] The reference node. If not supplied, newnode is
	 * appended to the children.
	 * @return DOMNode The inserted node.
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $newnode The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument->createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $oldnode The old node.
	 * @return DOMNode The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $oldnode The removed child.
	 * @return DOMNode If the child could be removed the function returns the old child.
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $newnode The appended child.
	 * @return DOMNode The node added.
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return DOMNode The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature The feature to test. See the example of 
	 * DOMImplementation::hasFeature for a
	 * list of features.
	 * @param string $version The version number of the feature to test.
	 * @return bool true on success or false on failure
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * @param DOMNode $other
	 */
	public function compareDocumentPosition (DOMNode $other) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $node The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespaceURI The namespace URI.
	 * @return string The prefix of the namespace.
	 */
	public function lookupPrefix (string $namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespaceURI The namespace URI to look for.
	 * @return bool Return true if namespaceURI is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceUri (string $prefix) {}

	/**
	 * @param DOMNode $arg
	 */
	public function isEqualNode (DOMNode $arg) {}

	/**
	 * @param $feature
	 * @param $version
	 */
	public function getFeature ($feature, $version) {}

	/**
	 * @param $key
	 * @param $data
	 * @param $handler
	 */
	public function setUserData ($key, $data, $handler) {}

	/**
	 * @param $key
	 */
	public function getUserData ($key) {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

}

class DOMAttr extends DOMNode  {

	/**
	 * Checks if attribute is a defined ID
	 * @link http://www.php.net/manual/en/domattr.isid.php
	 * @return bool true on success or false on failure
	 */
	public function isId () {}

	/**
	 * Creates a new DOMAttr object
	 * @link http://www.php.net/manual/en/domattr.construct.php
	 * @param $name
	 * @param $value [optional]
	 */
	public function __construct ($name, $value = null) {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $newnode The new node.
	 * @param DOMNode $refnode [optional] The reference node. If not supplied, newnode is
	 * appended to the children.
	 * @return DOMNode The inserted node.
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $newnode The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument->createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $oldnode The old node.
	 * @return DOMNode The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $oldnode The removed child.
	 * @return DOMNode If the child could be removed the function returns the old child.
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $newnode The appended child.
	 * @return DOMNode The node added.
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return DOMNode The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature The feature to test. See the example of 
	 * DOMImplementation::hasFeature for a
	 * list of features.
	 * @param string $version The version number of the feature to test.
	 * @return bool true on success or false on failure
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * @param DOMNode $other
	 */
	public function compareDocumentPosition (DOMNode $other) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $node The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespaceURI The namespace URI.
	 * @return string The prefix of the namespace.
	 */
	public function lookupPrefix (string $namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespaceURI The namespace URI to look for.
	 * @return bool Return true if namespaceURI is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceUri (string $prefix) {}

	/**
	 * @param DOMNode $arg
	 */
	public function isEqualNode (DOMNode $arg) {}

	/**
	 * @param $feature
	 * @param $version
	 */
	public function getFeature ($feature, $version) {}

	/**
	 * @param $key
	 * @param $data
	 * @param $handler
	 */
	public function setUserData ($key, $data, $handler) {}

	/**
	 * @param $key
	 */
	public function getUserData ($key) {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

}

class DOMElement extends DOMNode  {

	/**
	 * Returns value of attribute
	 * @link http://www.php.net/manual/en/domelement.getattribute.php
	 * @param string $name The name of the attribute.
	 * @return string The value of the attribute, or an empty string if no attribute with the
	 * given name is found.
	 */
	public function getAttribute (string $name) {}

	/**
	 * Adds new attribute
	 * @link http://www.php.net/manual/en/domelement.setattribute.php
	 * @param string $name The name of the attribute.
	 * @param string $value The value of the attribute.
	 * @return DOMAttr The new DOMAttr or false if an error occurred.
	 */
	public function setAttribute (string $name, string $value) {}

	/**
	 * Removes attribute
	 * @link http://www.php.net/manual/en/domelement.removeattribute.php
	 * @param string $name The name of the attribute.
	 * @return bool true on success or false on failure
	 */
	public function removeAttribute (string $name) {}

	/**
	 * Returns attribute node
	 * @link http://www.php.net/manual/en/domelement.getattributenode.php
	 * @param string $name The name of the attribute.
	 * @return DOMAttr The attribute node. Note that for XML namespace declarations
	 * (xmlns and xmlns:&#42; attributes) an 
	 * instance of DOMNameSpaceNode is returned instead of a
	 * DOMAttr.
	 */
	public function getAttributeNode (string $name) {}

	/**
	 * Adds new attribute node to element
	 * @link http://www.php.net/manual/en/domelement.setattributenode.php
	 * @param DOMAttr $attr The attribute node.
	 * @return DOMAttr old node if the attribute has been replaced or null.
	 */
	public function setAttributeNode (DOMAttr $attr) {}

	/**
	 * Removes attribute
	 * @link http://www.php.net/manual/en/domelement.removeattributenode.php
	 * @param DOMAttr $oldnode The attribute node.
	 * @return bool true on success or false on failure
	 */
	public function removeAttributeNode (DOMAttr $oldnode) {}

	/**
	 * Gets elements by tagname
	 * @link http://www.php.net/manual/en/domelement.getelementsbytagname.php
	 * @param string $name The tag name. Use &#42; to return all elements within 
	 * the element tree.
	 * @return DOMNodeList This function returns a new instance of the class
	 * DOMNodeList of all matched elements.
	 */
	public function getElementsByTagName (string $name) {}

	/**
	 * Returns value of attribute
	 * @link http://www.php.net/manual/en/domelement.getattributens.php
	 * @param string $namespaceURI The namespace URI.
	 * @param string $localName The local name.
	 * @return string The value of the attribute, or an empty string if no attribute with the
	 * given localName and namespaceURI 
	 * is found.
	 */
	public function getAttributeNS (string $namespaceURI, string $localName) {}

	/**
	 * Adds new attribute
	 * @link http://www.php.net/manual/en/domelement.setattributens.php
	 * @param string $namespaceURI The namespace URI.
	 * @param string $qualifiedName The qualified name of the attribute, as prefix:tagname.
	 * @param string $value The value of the attribute.
	 * @return void 
	 */
	public function setAttributeNS (string $namespaceURI, string $qualifiedName, string $value) {}

	/**
	 * Removes attribute
	 * @link http://www.php.net/manual/en/domelement.removeattributens.php
	 * @param string $namespaceURI The namespace URI.
	 * @param string $localName The local name.
	 * @return bool true on success or false on failure
	 */
	public function removeAttributeNS (string $namespaceURI, string $localName) {}

	/**
	 * Returns attribute node
	 * @link http://www.php.net/manual/en/domelement.getattributenodens.php
	 * @param string $namespaceURI The namespace URI.
	 * @param string $localName The local name.
	 * @return DOMAttr The attribute node. Note that for XML namespace declarations
	 * (xmlns and xmlns:&#42; attributes) an 
	 * instance of DOMNameSpaceNode is returned instead of a
	 * DOMAttr.
	 */
	public function getAttributeNodeNS (string $namespaceURI, string $localName) {}

	/**
	 * Adds new attribute node to element
	 * @link http://www.php.net/manual/en/domelement.setattributenodens.php
	 * @param DOMAttr $attr The attribute node.
	 * @return DOMAttr the old node if the attribute has been replaced.
	 */
	public function setAttributeNodeNS (DOMAttr $attr) {}

	/**
	 * Get elements by namespaceURI and localName
	 * @link http://www.php.net/manual/en/domelement.getelementsbytagnamens.php
	 * @param string $namespaceURI The namespace URI.
	 * @param string $localName The local name. Use &#42; to return all elements within 
	 * the element tree.
	 * @return DOMNodeList This function returns a new instance of the class
	 * DOMNodeList of all matched elements in the order in
	 * which they are encountered in a preorder traversal of this element tree.
	 */
	public function getElementsByTagNameNS (string $namespaceURI, string $localName) {}

	/**
	 * Checks to see if attribute exists
	 * @link http://www.php.net/manual/en/domelement.hasattribute.php
	 * @param string $name The attribute name.
	 * @return bool true on success or false on failure
	 */
	public function hasAttribute (string $name) {}

	/**
	 * Checks to see if attribute exists
	 * @link http://www.php.net/manual/en/domelement.hasattributens.php
	 * @param string $namespaceURI The namespace URI.
	 * @param string $localName The local name.
	 * @return bool true on success or false on failure
	 */
	public function hasAttributeNS (string $namespaceURI, string $localName) {}

	/**
	 * Declares the attribute specified by name to be of type ID
	 * @link http://www.php.net/manual/en/domelement.setidattribute.php
	 * @param string $name The name of the attribute.
	 * @param bool $isId Set it to true if you want name to be of type
	 * ID, false otherwise.
	 * @return void 
	 */
	public function setIdAttribute (string $name, bool $isId) {}

	/**
	 * Declares the attribute specified by local name and namespace URI to be of type ID
	 * @link http://www.php.net/manual/en/domelement.setidattributens.php
	 * @param string $namespaceURI The namespace URI of the attribute.
	 * @param string $localName The local name of the attribute, as prefix:tagname.
	 * @param bool $isId Set it to true if you want name to be of type
	 * ID, false otherwise.
	 * @return void 
	 */
	public function setIdAttributeNS (string $namespaceURI, string $localName, bool $isId) {}

	/**
	 * Declares the attribute specified by node to be of type ID
	 * @link http://www.php.net/manual/en/domelement.setidattributenode.php
	 * @param DOMAttr $attr The attribute node.
	 * @param bool $isId Set it to true if you want name to be of type
	 * ID, false otherwise.
	 * @return void 
	 */
	public function setIdAttributeNode (DOMAttr $attr, bool $isId) {}

	/**
	 * Creates a new DOMElement object
	 * @link http://www.php.net/manual/en/domelement.construct.php
	 * @param $name
	 * @param $value [optional]
	 * @param $uri [optional]
	 */
	public function __construct ($name, $value = null, $uri = null) {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $newnode The new node.
	 * @param DOMNode $refnode [optional] The reference node. If not supplied, newnode is
	 * appended to the children.
	 * @return DOMNode The inserted node.
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $newnode The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument->createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $oldnode The old node.
	 * @return DOMNode The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $oldnode The removed child.
	 * @return DOMNode If the child could be removed the function returns the old child.
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $newnode The appended child.
	 * @return DOMNode The node added.
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return DOMNode The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature The feature to test. See the example of 
	 * DOMImplementation::hasFeature for a
	 * list of features.
	 * @param string $version The version number of the feature to test.
	 * @return bool true on success or false on failure
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * @param DOMNode $other
	 */
	public function compareDocumentPosition (DOMNode $other) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $node The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespaceURI The namespace URI.
	 * @return string The prefix of the namespace.
	 */
	public function lookupPrefix (string $namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespaceURI The namespace URI to look for.
	 * @return bool Return true if namespaceURI is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceUri (string $prefix) {}

	/**
	 * @param DOMNode $arg
	 */
	public function isEqualNode (DOMNode $arg) {}

	/**
	 * @param $feature
	 * @param $version
	 */
	public function getFeature ($feature, $version) {}

	/**
	 * @param $key
	 * @param $data
	 * @param $handler
	 */
	public function setUserData ($key, $data, $handler) {}

	/**
	 * @param $key
	 */
	public function getUserData ($key) {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

}

class DOMText extends DOMCharacterData  {

	/**
	 * Breaks this node into two nodes at the specified offset
	 * @link http://www.php.net/manual/en/domtext.splittext.php
	 * @param int $offset The offset at which to split, starting from 0.
	 * @return DOMText The new node of the same type, which contains all the content at and after the 
	 * offset.
	 */
	public function splitText (int $offset) {}

	/**
	 * Indicates whether this text node contains whitespace
	 * @link http://www.php.net/manual/en/domtext.iswhitespaceinelementcontent.php
	 * @return bool true if node contains zero or more whitespace characters and
	 * nothing else. Returns false otherwise.
	 */
	public function isWhitespaceInElementContent () {}

	public function isElementContentWhitespace () {}

	/**
	 * @param $content
	 */
	public function replaceWholeText ($content) {}

	/**
	 * Creates a new DOMText object
	 * @link http://www.php.net/manual/en/domtext.construct.php
	 * @param $value [optional]
	 */
	public function __construct ($value = null) {}

	/**
	 * Extracts a range of data from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.substringdata.php
	 * @param int $offset Start offset of substring to extract.
	 * @param int $count The number of characters to extract.
	 * @return string The specified substring. If the sum of offset 
	 * and count exceeds the length, then all 16-bit units 
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count) {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/domcharacterdata.appenddata.php
	 * @param string $data The string to append.
	 * @return void 
	 */
	public function appendData (string $data) {}

	/**
	 * Insert a string at the specified 16-bit unit offset
	 * @link http://www.php.net/manual/en/domcharacterdata.insertdata.php
	 * @param int $offset The character offset at which to insert.
	 * @param string $data The string to insert.
	 * @return void 
	 */
	public function insertData (int $offset, string $data) {}

	/**
	 * Remove a range of characters from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.deletedata.php
	 * @param int $offset The offset from which to start removing.
	 * @param int $count The number of characters to delete. If the sum of 
	 * offset and count exceeds
	 * the length, then all characters to the end of the data are deleted.
	 * @return void 
	 */
	public function deleteData (int $offset, int $count) {}

	/**
	 * Replace a substring within the DOMCharacterData node
	 * @link http://www.php.net/manual/en/domcharacterdata.replacedata.php
	 * @param int $offset The offset from which to start replacing.
	 * @param int $count The number of characters to replace. If the sum of 
	 * offset and count exceeds
	 * the length, then all characters to the end of the data are replaced.
	 * @param string $data The string with which the range must be replaced.
	 * @return void 
	 */
	public function replaceData (int $offset, int $count, string $data) {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $newnode The new node.
	 * @param DOMNode $refnode [optional] The reference node. If not supplied, newnode is
	 * appended to the children.
	 * @return DOMNode The inserted node.
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $newnode The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument->createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $oldnode The old node.
	 * @return DOMNode The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $oldnode The removed child.
	 * @return DOMNode If the child could be removed the function returns the old child.
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $newnode The appended child.
	 * @return DOMNode The node added.
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return DOMNode The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature The feature to test. See the example of 
	 * DOMImplementation::hasFeature for a
	 * list of features.
	 * @param string $version The version number of the feature to test.
	 * @return bool true on success or false on failure
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * @param DOMNode $other
	 */
	public function compareDocumentPosition (DOMNode $other) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $node The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespaceURI The namespace URI.
	 * @return string The prefix of the namespace.
	 */
	public function lookupPrefix (string $namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespaceURI The namespace URI to look for.
	 * @return bool Return true if namespaceURI is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceUri (string $prefix) {}

	/**
	 * @param DOMNode $arg
	 */
	public function isEqualNode (DOMNode $arg) {}

	/**
	 * @param $feature
	 * @param $version
	 */
	public function getFeature ($feature, $version) {}

	/**
	 * @param $key
	 * @param $data
	 * @param $handler
	 */
	public function setUserData ($key, $data, $handler) {}

	/**
	 * @param $key
	 */
	public function getUserData ($key) {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

}

class DOMComment extends DOMCharacterData  {

	/**
	 * Creates a new DOMComment object
	 * @link http://www.php.net/manual/en/domcomment.construct.php
	 * @param $value [optional]
	 */
	public function __construct ($value = null) {}

	/**
	 * Extracts a range of data from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.substringdata.php
	 * @param int $offset Start offset of substring to extract.
	 * @param int $count The number of characters to extract.
	 * @return string The specified substring. If the sum of offset 
	 * and count exceeds the length, then all 16-bit units 
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count) {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/domcharacterdata.appenddata.php
	 * @param string $data The string to append.
	 * @return void 
	 */
	public function appendData (string $data) {}

	/**
	 * Insert a string at the specified 16-bit unit offset
	 * @link http://www.php.net/manual/en/domcharacterdata.insertdata.php
	 * @param int $offset The character offset at which to insert.
	 * @param string $data The string to insert.
	 * @return void 
	 */
	public function insertData (int $offset, string $data) {}

	/**
	 * Remove a range of characters from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.deletedata.php
	 * @param int $offset The offset from which to start removing.
	 * @param int $count The number of characters to delete. If the sum of 
	 * offset and count exceeds
	 * the length, then all characters to the end of the data are deleted.
	 * @return void 
	 */
	public function deleteData (int $offset, int $count) {}

	/**
	 * Replace a substring within the DOMCharacterData node
	 * @link http://www.php.net/manual/en/domcharacterdata.replacedata.php
	 * @param int $offset The offset from which to start replacing.
	 * @param int $count The number of characters to replace. If the sum of 
	 * offset and count exceeds
	 * the length, then all characters to the end of the data are replaced.
	 * @param string $data The string with which the range must be replaced.
	 * @return void 
	 */
	public function replaceData (int $offset, int $count, string $data) {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $newnode The new node.
	 * @param DOMNode $refnode [optional] The reference node. If not supplied, newnode is
	 * appended to the children.
	 * @return DOMNode The inserted node.
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $newnode The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument->createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $oldnode The old node.
	 * @return DOMNode The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $oldnode The removed child.
	 * @return DOMNode If the child could be removed the function returns the old child.
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $newnode The appended child.
	 * @return DOMNode The node added.
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return DOMNode The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature The feature to test. See the example of 
	 * DOMImplementation::hasFeature for a
	 * list of features.
	 * @param string $version The version number of the feature to test.
	 * @return bool true on success or false on failure
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * @param DOMNode $other
	 */
	public function compareDocumentPosition (DOMNode $other) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $node The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespaceURI The namespace URI.
	 * @return string The prefix of the namespace.
	 */
	public function lookupPrefix (string $namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespaceURI The namespace URI to look for.
	 * @return bool Return true if namespaceURI is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceUri (string $prefix) {}

	/**
	 * @param DOMNode $arg
	 */
	public function isEqualNode (DOMNode $arg) {}

	/**
	 * @param $feature
	 * @param $version
	 */
	public function getFeature ($feature, $version) {}

	/**
	 * @param $key
	 * @param $data
	 * @param $handler
	 */
	public function setUserData ($key, $data, $handler) {}

	/**
	 * @param $key
	 */
	public function getUserData ($key) {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

}

class DOMTypeinfo  {
}

class DOMUserDataHandler  {

	public function handle () {}

}

class DOMDomError  {
}

class DOMErrorHandler  {

	/**
	 * @param DOMDomError $error
	 */
	public function handleError (DOMDomError $error) {}

}

class DOMLocator  {
}

class DOMConfiguration  {

	/**
	 * @param $name
	 * @param $value
	 */
	public function setParameter ($name, $value) {}

	/**
	 * @param $name [optional]
	 */
	public function getParameter ($name = null) {}

	/**
	 * @param $name [optional]
	 * @param $value [optional]
	 */
	public function canSetParameter ($name = null, $value = null) {}

}

class DOMCdataSection extends DOMText  {

	/**
	 * Constructs a new DOMCdataSection object
	 * @link http://www.php.net/manual/en/domcdatasection.construct.php
	 * @param $value
	 */
	public function __construct ($value) {}

	/**
	 * Breaks this node into two nodes at the specified offset
	 * @link http://www.php.net/manual/en/domtext.splittext.php
	 * @param int $offset The offset at which to split, starting from 0.
	 * @return DOMText The new node of the same type, which contains all the content at and after the 
	 * offset.
	 */
	public function splitText (int $offset) {}

	/**
	 * Indicates whether this text node contains whitespace
	 * @link http://www.php.net/manual/en/domtext.iswhitespaceinelementcontent.php
	 * @return bool true if node contains zero or more whitespace characters and
	 * nothing else. Returns false otherwise.
	 */
	public function isWhitespaceInElementContent () {}

	public function isElementContentWhitespace () {}

	/**
	 * @param $content
	 */
	public function replaceWholeText ($content) {}

	/**
	 * Extracts a range of data from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.substringdata.php
	 * @param int $offset Start offset of substring to extract.
	 * @param int $count The number of characters to extract.
	 * @return string The specified substring. If the sum of offset 
	 * and count exceeds the length, then all 16-bit units 
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count) {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/domcharacterdata.appenddata.php
	 * @param string $data The string to append.
	 * @return void 
	 */
	public function appendData (string $data) {}

	/**
	 * Insert a string at the specified 16-bit unit offset
	 * @link http://www.php.net/manual/en/domcharacterdata.insertdata.php
	 * @param int $offset The character offset at which to insert.
	 * @param string $data The string to insert.
	 * @return void 
	 */
	public function insertData (int $offset, string $data) {}

	/**
	 * Remove a range of characters from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.deletedata.php
	 * @param int $offset The offset from which to start removing.
	 * @param int $count The number of characters to delete. If the sum of 
	 * offset and count exceeds
	 * the length, then all characters to the end of the data are deleted.
	 * @return void 
	 */
	public function deleteData (int $offset, int $count) {}

	/**
	 * Replace a substring within the DOMCharacterData node
	 * @link http://www.php.net/manual/en/domcharacterdata.replacedata.php
	 * @param int $offset The offset from which to start replacing.
	 * @param int $count The number of characters to replace. If the sum of 
	 * offset and count exceeds
	 * the length, then all characters to the end of the data are replaced.
	 * @param string $data The string with which the range must be replaced.
	 * @return void 
	 */
	public function replaceData (int $offset, int $count, string $data) {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $newnode The new node.
	 * @param DOMNode $refnode [optional] The reference node. If not supplied, newnode is
	 * appended to the children.
	 * @return DOMNode The inserted node.
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $newnode The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument->createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $oldnode The old node.
	 * @return DOMNode The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $oldnode The removed child.
	 * @return DOMNode If the child could be removed the function returns the old child.
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $newnode The appended child.
	 * @return DOMNode The node added.
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return DOMNode The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature The feature to test. See the example of 
	 * DOMImplementation::hasFeature for a
	 * list of features.
	 * @param string $version The version number of the feature to test.
	 * @return bool true on success or false on failure
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * @param DOMNode $other
	 */
	public function compareDocumentPosition (DOMNode $other) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $node The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespaceURI The namespace URI.
	 * @return string The prefix of the namespace.
	 */
	public function lookupPrefix (string $namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespaceURI The namespace URI to look for.
	 * @return bool Return true if namespaceURI is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceUri (string $prefix) {}

	/**
	 * @param DOMNode $arg
	 */
	public function isEqualNode (DOMNode $arg) {}

	/**
	 * @param $feature
	 * @param $version
	 */
	public function getFeature ($feature, $version) {}

	/**
	 * @param $key
	 * @param $data
	 * @param $handler
	 */
	public function setUserData ($key, $data, $handler) {}

	/**
	 * @param $key
	 */
	public function getUserData ($key) {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

}

class DOMDocumentType extends DOMNode  {

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $newnode The new node.
	 * @param DOMNode $refnode [optional] The reference node. If not supplied, newnode is
	 * appended to the children.
	 * @return DOMNode The inserted node.
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $newnode The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument->createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $oldnode The old node.
	 * @return DOMNode The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $oldnode The removed child.
	 * @return DOMNode If the child could be removed the function returns the old child.
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $newnode The appended child.
	 * @return DOMNode The node added.
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return DOMNode The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature The feature to test. See the example of 
	 * DOMImplementation::hasFeature for a
	 * list of features.
	 * @param string $version The version number of the feature to test.
	 * @return bool true on success or false on failure
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * @param DOMNode $other
	 */
	public function compareDocumentPosition (DOMNode $other) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $node The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespaceURI The namespace URI.
	 * @return string The prefix of the namespace.
	 */
	public function lookupPrefix (string $namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespaceURI The namespace URI to look for.
	 * @return bool Return true if namespaceURI is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceUri (string $prefix) {}

	/**
	 * @param DOMNode $arg
	 */
	public function isEqualNode (DOMNode $arg) {}

	/**
	 * @param $feature
	 * @param $version
	 */
	public function getFeature ($feature, $version) {}

	/**
	 * @param $key
	 * @param $data
	 * @param $handler
	 */
	public function setUserData ($key, $data, $handler) {}

	/**
	 * @param $key
	 */
	public function getUserData ($key) {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

}

class DOMNotation extends DOMNode  {

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $newnode The new node.
	 * @param DOMNode $refnode [optional] The reference node. If not supplied, newnode is
	 * appended to the children.
	 * @return DOMNode The inserted node.
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $newnode The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument->createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $oldnode The old node.
	 * @return DOMNode The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $oldnode The removed child.
	 * @return DOMNode If the child could be removed the function returns the old child.
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $newnode The appended child.
	 * @return DOMNode The node added.
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return DOMNode The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature The feature to test. See the example of 
	 * DOMImplementation::hasFeature for a
	 * list of features.
	 * @param string $version The version number of the feature to test.
	 * @return bool true on success or false on failure
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * @param DOMNode $other
	 */
	public function compareDocumentPosition (DOMNode $other) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $node The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespaceURI The namespace URI.
	 * @return string The prefix of the namespace.
	 */
	public function lookupPrefix (string $namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespaceURI The namespace URI to look for.
	 * @return bool Return true if namespaceURI is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceUri (string $prefix) {}

	/**
	 * @param DOMNode $arg
	 */
	public function isEqualNode (DOMNode $arg) {}

	/**
	 * @param $feature
	 * @param $version
	 */
	public function getFeature ($feature, $version) {}

	/**
	 * @param $key
	 * @param $data
	 * @param $handler
	 */
	public function setUserData ($key, $data, $handler) {}

	/**
	 * @param $key
	 */
	public function getUserData ($key) {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

}

class DOMEntity extends DOMNode  {

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $newnode The new node.
	 * @param DOMNode $refnode [optional] The reference node. If not supplied, newnode is
	 * appended to the children.
	 * @return DOMNode The inserted node.
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $newnode The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument->createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $oldnode The old node.
	 * @return DOMNode The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $oldnode The removed child.
	 * @return DOMNode If the child could be removed the function returns the old child.
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $newnode The appended child.
	 * @return DOMNode The node added.
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return DOMNode The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature The feature to test. See the example of 
	 * DOMImplementation::hasFeature for a
	 * list of features.
	 * @param string $version The version number of the feature to test.
	 * @return bool true on success or false on failure
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * @param DOMNode $other
	 */
	public function compareDocumentPosition (DOMNode $other) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $node The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespaceURI The namespace URI.
	 * @return string The prefix of the namespace.
	 */
	public function lookupPrefix (string $namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespaceURI The namespace URI to look for.
	 * @return bool Return true if namespaceURI is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceUri (string $prefix) {}

	/**
	 * @param DOMNode $arg
	 */
	public function isEqualNode (DOMNode $arg) {}

	/**
	 * @param $feature
	 * @param $version
	 */
	public function getFeature ($feature, $version) {}

	/**
	 * @param $key
	 * @param $data
	 * @param $handler
	 */
	public function setUserData ($key, $data, $handler) {}

	/**
	 * @param $key
	 */
	public function getUserData ($key) {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

}

class DOMEntityReference extends DOMNode  {

	/**
	 * Creates a new DOMEntityReference object
	 * @link http://www.php.net/manual/en/domentityreference.construct.php
	 * @param $name
	 */
	public function __construct ($name) {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $newnode The new node.
	 * @param DOMNode $refnode [optional] The reference node. If not supplied, newnode is
	 * appended to the children.
	 * @return DOMNode The inserted node.
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $newnode The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument->createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $oldnode The old node.
	 * @return DOMNode The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $oldnode The removed child.
	 * @return DOMNode If the child could be removed the function returns the old child.
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $newnode The appended child.
	 * @return DOMNode The node added.
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return DOMNode The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature The feature to test. See the example of 
	 * DOMImplementation::hasFeature for a
	 * list of features.
	 * @param string $version The version number of the feature to test.
	 * @return bool true on success or false on failure
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * @param DOMNode $other
	 */
	public function compareDocumentPosition (DOMNode $other) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $node The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespaceURI The namespace URI.
	 * @return string The prefix of the namespace.
	 */
	public function lookupPrefix (string $namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespaceURI The namespace URI to look for.
	 * @return bool Return true if namespaceURI is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceUri (string $prefix) {}

	/**
	 * @param DOMNode $arg
	 */
	public function isEqualNode (DOMNode $arg) {}

	/**
	 * @param $feature
	 * @param $version
	 */
	public function getFeature ($feature, $version) {}

	/**
	 * @param $key
	 * @param $data
	 * @param $handler
	 */
	public function setUserData ($key, $data, $handler) {}

	/**
	 * @param $key
	 */
	public function getUserData ($key) {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

}

class DOMProcessingInstruction extends DOMNode  {

	/**
	 * Creates a new DOMProcessingInstruction object
	 * @link http://www.php.net/manual/en/domprocessinginstruction.construct.php
	 * @param $name
	 * @param $value [optional]
	 */
	public function __construct ($name, $value = null) {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $newnode The new node.
	 * @param DOMNode $refnode [optional] The reference node. If not supplied, newnode is
	 * appended to the children.
	 * @return DOMNode The inserted node.
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $newnode The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument->createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $oldnode The old node.
	 * @return DOMNode The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $oldnode The removed child.
	 * @return DOMNode If the child could be removed the function returns the old child.
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $newnode The appended child.
	 * @return DOMNode The node added.
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return DOMNode The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature The feature to test. See the example of 
	 * DOMImplementation::hasFeature for a
	 * list of features.
	 * @param string $version The version number of the feature to test.
	 * @return bool true on success or false on failure
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * @param DOMNode $other
	 */
	public function compareDocumentPosition (DOMNode $other) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $node The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespaceURI The namespace URI.
	 * @return string The prefix of the namespace.
	 */
	public function lookupPrefix (string $namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespaceURI The namespace URI to look for.
	 * @return bool Return true if namespaceURI is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceUri (string $prefix) {}

	/**
	 * @param DOMNode $arg
	 */
	public function isEqualNode (DOMNode $arg) {}

	/**
	 * @param $feature
	 * @param $version
	 */
	public function getFeature ($feature, $version) {}

	/**
	 * @param $key
	 * @param $data
	 * @param $handler
	 */
	public function setUserData ($key, $data, $handler) {}

	/**
	 * @param $key
	 */
	public function getUserData ($key) {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $with_comments [optional] Retain comments in output.
	 * @param array $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array $ns_prefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $with_comments = null, array $xpath = null, array $ns_prefixes = null) {}

}

class DOMStringExtend  {

	/**
	 * @param $offset32
	 */
	public function findOffset16 ($offset32) {}

	/**
	 * @param $offset16
	 */
	public function findOffset32 ($offset16) {}

}

class DOMXPath  {

	/**
	 * Creates a new DOMXPath object
	 * @link http://www.php.net/manual/en/domxpath.construct.php
	 * @param DOMDocument $doc
	 */
	public function __construct (DOMDocument $doc) {}

	/**
	 * Registers the namespace with the DOMXPath object
	 * @link http://www.php.net/manual/en/domxpath.registernamespace.php
	 * @param string $prefix The prefix.
	 * @param string $namespaceURI The URI of the namespace.
	 * @return bool true on success or false on failure
	 */
	public function registerNamespace (string $prefix, string $namespaceURI) {}

	/**
	 * Evaluates the given XPath expression
	 * @link http://www.php.net/manual/en/domxpath.query.php
	 * @param string $expression The XPath expression to execute.
	 * @param DOMNode $contextnode [optional] The optional contextnode can be specified for
	 * doing relative XPath queries. By default, the queries are relative to 
	 * the root element.
	 * @param bool $registerNodeNS [optional] The optional registerNodeNS can be specified to 
	 * disable automatic registration of the context node.
	 * @return DOMNodeList a DOMNodeList containing all nodes matching
	 * the given XPath expression. Any expression which
	 * does not return nodes will return an empty
	 * DOMNodeList.
	 * <p>
	 * If the expression is malformed or the
	 * contextnode is invalid,
	 * DOMXPath::query returns false.
	 * </p>
	 */
	public function query (string $expression, DOMNode $contextnode = null, bool $registerNodeNS = null) {}

	/**
	 * Evaluates the given XPath expression and returns a typed result if possible
	 * @link http://www.php.net/manual/en/domxpath.evaluate.php
	 * @param string $expression The XPath expression to execute.
	 * @param DOMNode $contextnode [optional] The optional contextnode can be specified for
	 * doing relative XPath queries. By default, the queries are relative to 
	 * the root element.
	 * @param bool $registerNodeNS [optional] The optional registerNodeNS can be specified to 
	 * disable automatic registration of the context node.
	 * @return mixed a typed result if possible or a DOMNodeList 
	 * containing all nodes matching the given XPath expression. 
	 * <p>
	 * If the expression is malformed or the
	 * contextnode is invalid,
	 * DOMXPath::evaluate returns false.
	 * </p>
	 */
	public function evaluate (string $expression, DOMNode $contextnode = null, bool $registerNodeNS = null) {}

	/**
	 * Register PHP functions as XPath functions
	 * @link http://www.php.net/manual/en/domxpath.registerphpfunctions.php
	 * @param mixed $restrict [optional] <p>
	 * Use this parameter to only allow certain functions to be called from XPath.
	 * </p>
	 * <p>
	 * This parameter can be either a string (a function name) or 
	 * an array of function names.
	 * </p>
	 * @return void 
	 */
	public function registerPhpFunctions ($restrict = null) {}

}

/**
 * Gets a DOMElement object from a
 * SimpleXMLElement object
 * @link http://www.php.net/manual/en/function.dom-import-simplexml.php
 * @param SimpleXMLElement $node The SimpleXMLElement node.
 * @return DOMElement The DOMElement node added or false if any errors occur.
 */
function dom_import_simplexml (SimpleXMLElement $node) {}


/**
 * Node is a DOMElement
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_ELEMENT_NODE', 1);

/**
 * Node is a DOMAttr
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_ATTRIBUTE_NODE', 2);

/**
 * Node is a DOMText
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_TEXT_NODE', 3);

/**
 * Node is a DOMCharacterData
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_CDATA_SECTION_NODE', 4);

/**
 * Node is a DOMEntityReference
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_ENTITY_REF_NODE', 5);

/**
 * Node is a DOMEntity
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_ENTITY_NODE', 6);

/**
 * Node is a DOMProcessingInstruction
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_PI_NODE', 7);

/**
 * Node is a DOMComment
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_COMMENT_NODE', 8);

/**
 * Node is a DOMDocument
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_DOCUMENT_NODE', 9);

/**
 * Node is a DOMDocumentType
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_DOCUMENT_TYPE_NODE', 10);

/**
 * Node is a DOMDocumentFragment
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_DOCUMENT_FRAG_NODE', 11);

/**
 * Node is a DOMNotation
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_NOTATION_NODE', 12);

/**
 * 
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_HTML_DOCUMENT_NODE', 13);

/**
 * 
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_DTD_NODE', 14);

/**
 * 
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_ELEMENT_DECL_NODE', 15);

/**
 * 
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_ATTRIBUTE_DECL_NODE', 16);

/**
 * 
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_ENTITY_DECL_NODE', 17);

/**
 * 
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_NAMESPACE_DECL_NODE', 18);
define ('XML_LOCAL_NAMESPACE', 18);

/**
 * 
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_ATTRIBUTE_CDATA', 1);

/**
 * 
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_ATTRIBUTE_ID', 2);

/**
 * 
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_ATTRIBUTE_IDREF', 3);

/**
 * 
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_ATTRIBUTE_IDREFS', 4);

/**
 * 
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_ATTRIBUTE_ENTITY', 6);

/**
 * 
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_ATTRIBUTE_NMTOKEN', 7);

/**
 * 
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_ATTRIBUTE_NMTOKENS', 8);

/**
 * 
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_ATTRIBUTE_ENUMERATION', 9);

/**
 * 
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('XML_ATTRIBUTE_NOTATION', 10);

/**
 * Error code not part of the DOM specification. Meant for PHP errors.
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('DOM_PHP_ERR', 0);

/**
 * If index or size is negative, or greater than the allowed value.
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('DOM_INDEX_SIZE_ERR', 1);

/**
 * If the specified range of text does not fit into a 
 * DOMString.
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('DOMSTRING_SIZE_ERR', 2);

/**
 * If any node is inserted somewhere it doesn't belong
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('DOM_HIERARCHY_REQUEST_ERR', 3);

/**
 * If a node is used in a different document than the one that created it.
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('DOM_WRONG_DOCUMENT_ERR', 4);

/**
 * If an invalid or illegal character is specified, such as in a name.
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('DOM_INVALID_CHARACTER_ERR', 5);

/**
 * If data is specified for a node which does not support data.
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('DOM_NO_DATA_ALLOWED_ERR', 6);

/**
 * If an attempt is made to modify an object where modifications are not allowed.
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('DOM_NO_MODIFICATION_ALLOWED_ERR', 7);

/**
 * If an attempt is made to reference a node in a context where it does not exist.
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('DOM_NOT_FOUND_ERR', 8);

/**
 * If the implementation does not support the requested type of object or operation.
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('DOM_NOT_SUPPORTED_ERR', 9);

/**
 * If an attempt is made to add an attribute that is already in use elsewhere.
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('DOM_INUSE_ATTRIBUTE_ERR', 10);

/**
 * If an attempt is made to use an object that is not, or is no longer, usable.
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('DOM_INVALID_STATE_ERR', 11);

/**
 * If an invalid or illegal string is specified.
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('DOM_SYNTAX_ERR', 12);

/**
 * If an attempt is made to modify the type of the underlying object.
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('DOM_INVALID_MODIFICATION_ERR', 13);

/**
 * If an attempt is made to create or change an object in a way which is 
 * incorrect with regard to namespaces.
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('DOM_NAMESPACE_ERR', 14);

/**
 * If a parameter or an operation is not supported by the underlying object.
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('DOM_INVALID_ACCESS_ERR', 15);

/**
 * If a call to a method such as insertBefore or removeChild would make the Node
 * invalid with respect to "partial validity", this exception would be raised and 
 * the operation would not be done.
 * @link http://www.php.net/manual/en/dom.constants.php
 */
define ('DOM_VALIDATION_ERR', 16);

// End of dom v.20031129
