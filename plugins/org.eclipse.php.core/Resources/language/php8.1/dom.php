<?php

// Start of dom v.20031129

/**
 * DOM operations raise exceptions under particular circumstances, i.e.,
 * when an operation is impossible to perform for logical reasons.
 * <p>See also .</p>
 * @link http://www.php.net/manual/en/class.domexception.php
 */
final class DOMException extends Exception implements Throwable, Stringable {
	protected $message;
	protected $file;
	protected $line;
	public $code;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param ?Throwable|null $previous [optional]
	 */
	public function __construct (string $message = ''int , $code = 0?Throwable|null , $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ??Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

/**
 * @link http://www.php.net/manual/en/class.domparentnode.php
 */
interface DOMParentNode  {

	/**
	 * Appends nodes after the last child node
	 * @link http://www.php.net/manual/en/domparentnode.append.php
	 * @param mixed $nodes The nodes to append.
	 * @return void 
	 */
	abstract public function append ($nodes): void

	/**
	 * Prepends nodes before the first child node
	 * @link http://www.php.net/manual/en/domparentnode.prepend.php
	 * @param mixed $nodes The nodes to prepend.
	 * @return void 
	 */
	abstract public function prepend ($nodes): void

}

/**
 * @link http://www.php.net/manual/en/class.domchildnode.php
 */
interface DOMChildNode  {

	/**
	 * Removes the node
	 * @link http://www.php.net/manual/en/domchildnode.remove.php
	 * @return void 
	 */
	abstract public function remove (): void

	/**
	 * Adds nodes before the node
	 * @link http://www.php.net/manual/en/domchildnode.before.php
	 * @param mixed $nodes Nodes to be added before the node.
	 * @return void 
	 */
	abstract public function before ($nodes): void

	/**
	 * Adds nodes after the node
	 * @link http://www.php.net/manual/en/domchildnode.after.php
	 * @param mixed $nodes Nodes to be added after the node.
	 * @return void 
	 */
	abstract public function after ($nodes): void

	/**
	 * Replaces the node with new nodes
	 * @link http://www.php.net/manual/en/domchildnode.replacewith.php
	 * @param mixed $nodes The replacement nodes.
	 * @return void 
	 */
	abstract public function replaceWith ($nodes): void

}

/**
 * The DOMImplementation class provides a number
 * of methods for performing operations that are independent of any 
 * particular instance of the document object model.
 * @link http://www.php.net/manual/en/class.domimplementation.php
 */
class DOMImplementation  {

	/**
	 * @param string $feature
	 * @param string $version
	 */
	public function getFeature (string $featurestring , $version) {}

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
	 * @param string $qualifiedName The qualified name of the document type to create.
	 * @param string $publicId [optional] The external subset public identifier.
	 * @param string $systemId [optional] The external subset system identifier.
	 * @return mixed A new DOMDocumentType node with its 
	 * ownerDocument set to null or false on error.
	 */
	public function createDocumentType (string $qualifiedName, string $publicId = null, string $systemId = null) {}

	/**
	 * Creates a DOMDocument object of the specified type with its document element
	 * @link http://www.php.net/manual/en/domimplementation.createdocument.php
	 * @param mixed $namespace [optional] The namespace URI of the document element to create.
	 * @param string $qualifiedName [optional] The qualified name of the document element to create.
	 * @param mixed $doctype [optional] The type of document to create or null.
	 * @return mixed A new DOMDocument object or false on error. If
	 * namespace, qualifiedName,
	 * and doctype are null, the returned
	 * DOMDocument is empty with no document element
	 */
	public function createDocument ($namespace = null, string $qualifiedName = null, $doctype = null) {}

}

/**
 * @link http://www.php.net/manual/en/class.domnode.php
 */
class DOMNode  {
	public $nodeName;
	public $nodeValue;
	public $nodeType;
	public $parentNode;
	public $childNodes;
	public $firstChild;
	public $lastChild;
	public $previousSibling;
	public $nextSibling;
	public $attributes;
	public $ownerDocument;
	public $namespaceURI;
	public $prefix;
	public $localName;
	public $baseURI;
	public $textContent;


	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node The appended child.
	 * @return mixed The node added or false on error.
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return mixed The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return mixed a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node The new node.
	 * @param mixed $child [optional] The reference node. If not supplied, node is
	 * appended to the children.
	 * @return mixed The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, $child = null) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace The namespace URI to look for.
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $otherNode) {}

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
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace The namespace URI.
	 * @return mixed The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child The removed child.
	 * @return mixed If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument-&gt;createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $child The old node.
	 * @return mixed The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

}

class DOMNameSpaceNode  {
	public $nodeName;
	public $nodeValue;
	public $nodeType;
	public $prefix;
	public $localName;
	public $namespaceURI;
	public $ownerDocument;
	public $parentNode;

}

/**
 * @link http://www.php.net/manual/en/class.domdocumentfragment.php
 */
class DOMDocumentFragment extends DOMNode implements DOMParentNode {
	public $nodeName;
	public $nodeValue;
	public $nodeType;
	public $parentNode;
	public $childNodes;
	public $firstChild;
	public $lastChild;
	public $previousSibling;
	public $nextSibling;
	public $attributes;
	public $ownerDocument;
	public $namespaceURI;
	public $prefix;
	public $localName;
	public $baseURI;
	public $textContent;
	public $firstElementChild;
	public $lastElementChild;
	public $childElementCount;


	/**
	 * Constructs a DOMDocumentFragment object
	 * @link http://www.php.net/manual/en/domdocumentfragment.construct.php
	 */
	public function __construct () {}

	/**
	 * Append raw XML data
	 * @link http://www.php.net/manual/en/domdocumentfragment.appendxml.php
	 * @param string $data XML to append.
	 * @return bool true on success or false on failure
	 */
	public function appendXML (string $data) {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function append (...$nodes): void {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function prepend (...$nodes): void {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node The appended child.
	 * @return mixed The node added or false on error.
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return mixed The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return mixed a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node The new node.
	 * @param mixed $child [optional] The reference node. If not supplied, node is
	 * appended to the children.
	 * @return mixed The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, $child = null) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace The namespace URI to look for.
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $otherNode) {}

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
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace The namespace URI.
	 * @return mixed The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child The removed child.
	 * @return mixed If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument-&gt;createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $child The old node.
	 * @return mixed The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

}

/**
 * Represents an entire HTML or XML document; serves as the root of the
 * document tree.
 * @link http://www.php.net/manual/en/class.domdocument.php
 */
class DOMDocument extends DOMNode implements DOMParentNode {
	public $nodeName;
	public $nodeValue;
	public $nodeType;
	public $parentNode;
	public $childNodes;
	public $firstChild;
	public $lastChild;
	public $previousSibling;
	public $nextSibling;
	public $attributes;
	public $ownerDocument;
	public $namespaceURI;
	public $prefix;
	public $localName;
	public $baseURI;
	public $textContent;
	public $doctype;
	public $implementation;
	public $documentElement;
	public $actualEncoding;
	public $encoding;
	public $xmlEncoding;
	public $standalone;
	public $xmlStandalone;
	public $version;
	public $xmlVersion;
	public $strictErrorChecking;
	public $documentURI;
	public $config;
	public $formatOutput;
	public $validateOnParse;
	public $resolveExternals;
	public $preserveWhiteSpace;
	public $recover;
	public $substituteEntities;
	public $firstElementChild;
	public $lastElementChild;
	public $childElementCount;


	/**
	 * Creates a new DOMDocument object
	 * @link http://www.php.net/manual/en/domdocument.construct.php
	 * @param string $version [optional]
	 * @param string $encoding [optional]
	 */
	public function __construct (string $version = 1.0string , $encoding = '') {}

	/**
	 * Create new attribute
	 * @link http://www.php.net/manual/en/domdocument.createattribute.php
	 * @param string $localName The name of the attribute.
	 * @return mixed The new DOMAttr or false if an error occurred.
	 */
	public function createAttribute (string $localName) {}

	/**
	 * Create new attribute node with an associated namespace
	 * @link http://www.php.net/manual/en/domdocument.createattributens.php
	 * @param mixed $namespace The URI of the namespace.
	 * @param string $qualifiedName The tag name and prefix of the attribute, as prefix:tagname.
	 * @return mixed The new DOMAttr or false if an error occurred.
	 */
	public function createAttributeNS ($namespace, string $qualifiedName) {}

	/**
	 * Create new cdata node
	 * @link http://www.php.net/manual/en/domdocument.createcdatasection.php
	 * @param string $data The content of the cdata.
	 * @return mixed The new DOMCDATASection or false if an error occurred.
	 */
	public function createCDATASection (string $data) {}

	/**
	 * Create new comment node
	 * @link http://www.php.net/manual/en/domdocument.createcomment.php
	 * @param string $data The content of the comment.
	 * @return DOMComment The new DOMComment.
	 */
	public function createComment (string $data) {}

	/**
	 * Create new document fragment
	 * @link http://www.php.net/manual/en/domdocument.createdocumentfragment.php
	 * @return DOMDocumentFragment The new DOMDocumentFragment.
	 */
	public function createDocumentFragment () {}

	/**
	 * Create new element node
	 * @link http://www.php.net/manual/en/domdocument.createelement.php
	 * @param string $localName The tag name of the element.
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
	 * @return mixed a new instance of class DOMElement or false
	 * if an error occurred.
	 */
	public function createElement (string $localName, string $value = null) {}

	/**
	 * Create new element node with an associated namespace
	 * @link http://www.php.net/manual/en/domdocument.createelementns.php
	 * @param mixed $namespace The URI of the namespace.
	 * @param string $qualifiedName The qualified name of the element, as prefix:tagname.
	 * @param string $value [optional] The value of the element. By default, an empty element will be created.
	 * You can also set the value later with DOMElement::$nodeValue.
	 * @return mixed The new DOMElement or false if an error occurred.
	 */
	public function createElementNS ($namespace, string $qualifiedName, string $value = null) {}

	/**
	 * Create new entity reference node
	 * @link http://www.php.net/manual/en/domdocument.createentityreference.php
	 * @param string $name The content of the entity reference, e.g. the entity reference minus
	 * the leading &amp; and the trailing
	 * ; characters.
	 * @return mixed The new DOMEntityReference or false if an error
	 * occurred.
	 */
	public function createEntityReference (string $name) {}

	/**
	 * Creates new PI node
	 * @link http://www.php.net/manual/en/domdocument.createprocessinginstruction.php
	 * @param string $target The target of the processing instruction.
	 * @param string $data [optional] The content of the processing instruction.
	 * @return mixed The new DOMProcessingInstruction or false if an error occurred.
	 */
	public function createProcessingInstruction (string $target, string $data = null) {}

	/**
	 * Create new text node
	 * @link http://www.php.net/manual/en/domdocument.createtextnode.php
	 * @param string $data The content of the text.
	 * @return DOMText The new DOMText.
	 */
	public function createTextNode (string $data) {}

	/**
	 * Searches for an element with a certain id
	 * @link http://www.php.net/manual/en/domdocument.getelementbyid.php
	 * @param string $elementId The unique id value for an element.
	 * @return mixed the DOMElement or null if the element is
	 * not found.
	 */
	public function getElementById (string $elementId) {}

	/**
	 * Searches for all elements with given local tag name
	 * @link http://www.php.net/manual/en/domdocument.getelementsbytagname.php
	 * @param string $qualifiedName The local name (without namespace) of the tag to match on. The special value &#42;
	 * matches all tags.
	 * @return DOMNodeList A new DOMNodeList object containing all the matched 
	 * elements.
	 */
	public function getElementsByTagName (string $qualifiedName) {}

	/**
	 * Searches for all elements with given tag name in specified namespace
	 * @link http://www.php.net/manual/en/domdocument.getelementsbytagnamens.php
	 * @param mixed $namespace The namespace URI of the elements to match on. 
	 * The special value &#42; matches all namespaces.
	 * @param string $localName The local name of the elements to match on. 
	 * The special value &#42; matches all local names.
	 * @return DOMNodeList A new DOMNodeList object containing all the matched 
	 * elements.
	 */
	public function getElementsByTagNameNS ($namespace, string $localName) {}

	/**
	 * Import node into current document
	 * @link http://www.php.net/manual/en/domdocument.importnode.php
	 * @param DOMNode $node The node to import.
	 * @param bool $deep [optional] <p>
	 * If set to true, this method will recursively import the subtree under
	 * the node.
	 * </p>
	 * <p>
	 * To copy the nodes attributes deep needs to be set to true
	 * </p>
	 * @return mixed The copied node or false, if it cannot be copied.
	 */
	public function importNode (DOMNode $node, bool $deep = null) {}

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
	 * Normalizes the document
	 * @link http://www.php.net/manual/en/domdocument.normalizedocument.php
	 * @return void 
	 */
	public function normalizeDocument () {}

	/**
	 * Register extended class used to create base node type
	 * @link http://www.php.net/manual/en/domdocument.registernodeclass.php
	 * @param string $baseClass The DOM class that you want to extend. You can find a list of these 
	 * classes in the chapter introduction.
	 * @param mixed $extendedClass Your extended class name. If null is provided, any previously 
	 * registered class extending baseClass will
	 * be removed.
	 * @return bool true on success or false on failure
	 */
	public function registerNodeClass (string $baseClass, $extendedClass) {}

	/**
	 * Dumps the internal XML tree back into a file
	 * @link http://www.php.net/manual/en/domdocument.save.php
	 * @param string $filename The path to the saved XML document.
	 * @param int $options [optional] Additional Options. Currently only LIBXML_NOEMPTYTAG is supported.
	 * @return mixed the number of bytes written or false if an error occurred.
	 */
	public function save (string $filename, int $options = null) {}

	/**
	 * Load HTML from a string
	 * @link http://www.php.net/manual/en/domdocument.loadhtml.php
	 * @param string $source The HTML string.
	 * @param int $options [optional] Since Libxml 2.6.0, you may also use the
	 * options parameter to specify additional Libxml parameters.
	 * @return mixed true on success or false on failure If called statically, returns a
	 * DOMDocument or false on failure.
	 */
	public function loadHTML (string $source, int $options = null) {}

	/**
	 * Load HTML from a file
	 * @link http://www.php.net/manual/en/domdocument.loadhtmlfile.php
	 * @param string $filename The path to the HTML file.
	 * @param int $options [optional] Since Libxml 2.6.0, you may also use the
	 * options parameter to specify additional Libxml parameters.
	 * @return mixed true on success or false on failure If called statically, returns a
	 * DOMDocument or false on failure.
	 */
	public function loadHTMLFile (string $filename, int $options = null) {}

	/**
	 * Dumps the internal document into a string using HTML formatting
	 * @link http://www.php.net/manual/en/domdocument.savehtml.php
	 * @param mixed $node [optional] Optional parameter to output a subset of the document.
	 * @return mixed the HTML, or false if an error occurred.
	 */
	public function saveHTML ($node = null) {}

	/**
	 * Dumps the internal document into a file using HTML formatting
	 * @link http://www.php.net/manual/en/domdocument.savehtmlfile.php
	 * @param string $filename The path to the saved HTML document.
	 * @return mixed the number of bytes written or false if an error occurred.
	 */
	public function saveHTMLFile (string $filename) {}

	/**
	 * Dumps the internal XML tree back into a string
	 * @link http://www.php.net/manual/en/domdocument.savexml.php
	 * @param mixed $node [optional] Use this parameter to output only a specific node without XML declaration
	 * rather than the entire document.
	 * @param int $options [optional] Additional Options. Currently only LIBXML_NOEMPTYTAG is supported.
	 * @return mixed the XML, or false if an error occurred.
	 */
	public function saveXML ($node = null, int $options = null) {}

	/**
	 * Validates a document based on a schema. Only XML Schema 1.0 is supported.
	 * @link http://www.php.net/manual/en/domdocument.schemavalidate.php
	 * @param string $filename The path to the schema.
	 * @param int $flags [optional] A bitmask of Libxml schema validation flags. Currently the only supported value is LIBXML_SCHEMA_CREATE. Available since Libxml 2.6.14.
	 * @return bool true on success or false on failure
	 */
	public function schemaValidate (string $filename, int $flags = null) {}

	/**
	 * Validates a document based on a schema
	 * @link http://www.php.net/manual/en/domdocument.schemavalidatesource.php
	 * @param string $source A string containing the schema.
	 * @param int $flags [optional] A bitmask of Libxml schema validation flags. Currently the only supported value is LIBXML_SCHEMA_CREATE. Available since Libxml 2.6.14.
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
	 * Validates the document based on its DTD
	 * @link http://www.php.net/manual/en/domdocument.validate.php
	 * @return bool true on success or false on failure
	 * If the document has no DTD attached, this method will return false.
	 */
	public function validate () {}

	/**
	 * Substitutes XIncludes in a DOMDocument Object
	 * @link http://www.php.net/manual/en/domdocument.xinclude.php
	 * @param int $options [optional] libxml parameters. Available
	 * since Libxml 2.6.7.
	 * @return mixed the number of XIncludes in the document, -1 if some processing failed,
	 * or false if there were no substitutions.
	 */
	public function xinclude (int $options = null) {}

	/**
	 * @param DOMNode $node
	 */
	public function adoptNode (DOMNode $node) {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function append (...$nodes): void {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function prepend (...$nodes): void {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node The appended child.
	 * @return mixed The node added or false on error.
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return mixed The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return mixed a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node The new node.
	 * @param mixed $child [optional] The reference node. If not supplied, node is
	 * appended to the children.
	 * @return mixed The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, $child = null) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace The namespace URI to look for.
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $otherNode) {}

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
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace The namespace URI.
	 * @return mixed The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child The removed child.
	 * @return mixed If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument-&gt;createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $child The old node.
	 * @return mixed The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

}

/**
 * @link http://www.php.net/manual/en/class.domnodelist.php
 */
class DOMNodeList implements IteratorAggregate, Traversable, Countable {
	public $length;


	/**
	 * Get number of nodes in the list
	 * @link http://www.php.net/manual/en/domnodelist.count.php
	 * @return int the number of nodes in the list, which is identical to the
	 * length property.
	 */
	public function count () {}

	public function getIterator (): Iterator {}

	/**
	 * Retrieves a node specified by index
	 * @link http://www.php.net/manual/en/domnodelist.item.php
	 * @param int $index Index of the node into the collection.
	 * @return mixed The node at the indexth position in the 
	 * DOMNodeList, or null if that is not a valid
	 * index.
	 */
	public function item (int $index) {}

}

/**
 * @link http://www.php.net/manual/en/class.domnamednodemap.php
 */
class DOMNamedNodeMap implements IteratorAggregate, Traversable, Countable {
	public $length;


	/**
	 * Retrieves a node specified by name
	 * @link http://www.php.net/manual/en/domnamednodemap.getnameditem.php
	 * @param string $qualifiedName The nodeName of the node to retrieve.
	 * @return mixed A node (of any type) with the specified nodeName, or 
	 * null if no node is found.
	 */
	public function getNamedItem (string $qualifiedName) {}

	/**
	 * Retrieves a node specified by local name and namespace URI
	 * @link http://www.php.net/manual/en/domnamednodemap.getnameditemns.php
	 * @param mixed $namespace The namespace URI of the node to retrieve.
	 * @param string $localName The local name of the node to retrieve.
	 * @return mixed A node (of any type) with the specified local name and namespace URI, or 
	 * null if no node is found.
	 */
	public function getNamedItemNS ($namespace, string $localName) {}

	/**
	 * Retrieves a node specified by index
	 * @link http://www.php.net/manual/en/domnamednodemap.item.php
	 * @param int $index Index into this map.
	 * @return mixed The node at the indexth position in the map, or null
	 * if that is not a valid index (greater than or equal to the number of nodes 
	 * in this map).
	 */
	public function item (int $index) {}

	/**
	 * Get number of nodes in the map
	 * @link http://www.php.net/manual/en/domnamednodemap.count.php
	 * @return int the number of nodes in the map, which is identical to the
	 * length property.
	 */
	public function count () {}

	public function getIterator (): Iterator {}

}

/**
 * Represents nodes with character data. No nodes directly correspond to
 * this class, but other nodes do inherit from it.
 * @link http://www.php.net/manual/en/class.domcharacterdata.php
 */
class DOMCharacterData extends DOMNode implements DOMChildNode {
	public $nodeName;
	public $nodeValue;
	public $nodeType;
	public $parentNode;
	public $childNodes;
	public $firstChild;
	public $lastChild;
	public $previousSibling;
	public $nextSibling;
	public $attributes;
	public $ownerDocument;
	public $namespaceURI;
	public $prefix;
	public $localName;
	public $baseURI;
	public $textContent;
	public $data;
	public $length;
	public $previousElementSibling;
	public $nextElementSibling;


	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/domcharacterdata.appenddata.php
	 * @param string $data The string to append.
	 * @return true Always returns true.
	 */
	public function appendData (string $data) {}

	/**
	 * Extracts a range of data from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.substringdata.php
	 * @param int $offset Start offset of substring to extract.
	 * @param int $count The number of characters to extract.
	 * @return mixed The specified substring. If the sum of offset 
	 * and count exceeds the length, then all 16-bit units 
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count) {}

	/**
	 * Insert a string at the specified 16-bit unit offset
	 * @link http://www.php.net/manual/en/domcharacterdata.insertdata.php
	 * @param int $offset The character offset at which to insert.
	 * @param string $data The string to insert.
	 * @return bool true on success or false on failure
	 */
	public function insertData (int $offset, string $data) {}

	/**
	 * Remove a range of characters from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.deletedata.php
	 * @param int $offset The offset from which to start removing.
	 * @param int $count The number of characters to delete. If the sum of
	 * offset and count exceeds
	 * the length, then all characters to the end of the data are deleted.
	 * @return bool true on success or false on failure
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
	 * @return bool true on success or false on failure
	 */
	public function replaceData (int $offset, int $count, string $data) {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function replaceWith (...$nodes): void {}

	public function remove (): void {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function before (...$nodes): void {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function after (...$nodes): void {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node The appended child.
	 * @return mixed The node added or false on error.
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return mixed The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return mixed a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node The new node.
	 * @param mixed $child [optional] The reference node. If not supplied, node is
	 * appended to the children.
	 * @return mixed The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, $child = null) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace The namespace URI to look for.
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $otherNode) {}

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
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace The namespace URI.
	 * @return mixed The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child The removed child.
	 * @return mixed If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument-&gt;createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $child The old node.
	 * @return mixed The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

}

/**
 * DOMAttr represents an attribute in the 
 * DOMElement object.
 * @link http://www.php.net/manual/en/class.domattr.php
 */
class DOMAttr extends DOMNode  {
	public $nodeName;
	public $nodeValue;
	public $nodeType;
	public $parentNode;
	public $childNodes;
	public $firstChild;
	public $lastChild;
	public $previousSibling;
	public $nextSibling;
	public $attributes;
	public $ownerDocument;
	public $namespaceURI;
	public $prefix;
	public $localName;
	public $baseURI;
	public $textContent;
	public $name;
	public $specified;
	public $value;
	public $ownerElement;
	public $schemaTypeInfo;


	/**
	 * Creates a new DOMAttr object
	 * @link http://www.php.net/manual/en/domattr.construct.php
	 * @param string $name
	 * @param string $value [optional]
	 */
	public function __construct (string $namestring , $value = '') {}

	/**
	 * Checks if attribute is a defined ID
	 * @link http://www.php.net/manual/en/domattr.isid.php
	 * @return bool true on success or false on failure
	 */
	public function isId () {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node The appended child.
	 * @return mixed The node added or false on error.
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return mixed The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return mixed a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node The new node.
	 * @param mixed $child [optional] The reference node. If not supplied, node is
	 * appended to the children.
	 * @return mixed The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, $child = null) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace The namespace URI to look for.
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $otherNode) {}

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
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace The namespace URI.
	 * @return mixed The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child The removed child.
	 * @return mixed If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument-&gt;createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $child The old node.
	 * @return mixed The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

}

/**
 * @link http://www.php.net/manual/en/class.domelement.php
 */
class DOMElement extends DOMNode implements DOMParentNode, DOMChildNode {
	public $nodeName;
	public $nodeValue;
	public $nodeType;
	public $parentNode;
	public $childNodes;
	public $firstChild;
	public $lastChild;
	public $previousSibling;
	public $nextSibling;
	public $attributes;
	public $ownerDocument;
	public $namespaceURI;
	public $prefix;
	public $localName;
	public $baseURI;
	public $textContent;
	public $tagName;
	public $schemaTypeInfo;
	public $firstElementChild;
	public $lastElementChild;
	public $childElementCount;
	public $previousElementSibling;
	public $nextElementSibling;


	/**
	 * Creates a new DOMElement object
	 * @link http://www.php.net/manual/en/domelement.construct.php
	 * @param string $qualifiedName
	 * @param ?string|null $value [optional]
	 * @param string $namespace [optional]
	 */
	public function __construct (string $qualifiedName?string|null , $value = nullstring , $namespace = '') {}

	/**
	 * Returns value of attribute
	 * @link http://www.php.net/manual/en/domelement.getattribute.php
	 * @param string $qualifiedName The name of the attribute.
	 * @return string The value of the attribute, or an empty string if no attribute with the
	 * given qualifiedName is found.
	 */
	public function getAttribute (string $qualifiedName) {}

	/**
	 * Returns value of attribute
	 * @link http://www.php.net/manual/en/domelement.getattributens.php
	 * @param mixed $namespace The namespace URI.
	 * @param string $localName The local name.
	 * @return string The value of the attribute, or an empty string if no attribute with the
	 * given localName and namespace 
	 * is found.
	 */
	public function getAttributeNS ($namespace, string $localName) {}

	/**
	 * Returns attribute node
	 * @link http://www.php.net/manual/en/domelement.getattributenode.php
	 * @param string $qualifiedName The name of the attribute.
	 * @return mixed The attribute node. Note that for XML namespace declarations
	 * (xmlns and xmlns:&#42; attributes) an 
	 * instance of DOMNameSpaceNode is returned instead of a
	 * DOMAttr.
	 */
	public function getAttributeNode (string $qualifiedName) {}

	/**
	 * Returns attribute node
	 * @link http://www.php.net/manual/en/domelement.getattributenodens.php
	 * @param mixed $namespace The namespace URI.
	 * @param string $localName The local name.
	 * @return mixed The attribute node. Note that for XML namespace declarations
	 * (xmlns and xmlns:&#42; attributes) an 
	 * instance of DOMNameSpaceNode is returned instead of a
	 * DOMAttr object.
	 */
	public function getAttributeNodeNS ($namespace, string $localName) {}

	/**
	 * Gets elements by tagname
	 * @link http://www.php.net/manual/en/domelement.getelementsbytagname.php
	 * @param string $qualifiedName The tag name. Use &#42; to return all elements within 
	 * the element tree.
	 * @return DOMNodeList This function returns a new instance of the class
	 * DOMNodeList of all matched elements.
	 */
	public function getElementsByTagName (string $qualifiedName) {}

	/**
	 * Get elements by namespaceURI and localName
	 * @link http://www.php.net/manual/en/domelement.getelementsbytagnamens.php
	 * @param mixed $namespace The namespace URI.
	 * @param string $localName The local name. Use &#42; to return all elements within 
	 * the element tree.
	 * @return DOMNodeList This function returns a new instance of the class
	 * DOMNodeList of all matched elements in the order in
	 * which they are encountered in a preorder traversal of this element tree.
	 */
	public function getElementsByTagNameNS ($namespace, string $localName) {}

	/**
	 * Checks to see if attribute exists
	 * @link http://www.php.net/manual/en/domelement.hasattribute.php
	 * @param string $qualifiedName The attribute name.
	 * @return bool true on success or false on failure
	 */
	public function hasAttribute (string $qualifiedName) {}

	/**
	 * Checks to see if attribute exists
	 * @link http://www.php.net/manual/en/domelement.hasattributens.php
	 * @param mixed $namespace The namespace URI.
	 * @param string $localName The local name.
	 * @return bool true on success or false on failure
	 */
	public function hasAttributeNS ($namespace, string $localName) {}

	/**
	 * Removes attribute
	 * @link http://www.php.net/manual/en/domelement.removeattribute.php
	 * @param string $qualifiedName The name of the attribute.
	 * @return bool true on success or false on failure
	 */
	public function removeAttribute (string $qualifiedName) {}

	/**
	 * Removes attribute
	 * @link http://www.php.net/manual/en/domelement.removeattributens.php
	 * @param mixed $namespace The namespace URI.
	 * @param string $localName The local name.
	 * @return void 
	 */
	public function removeAttributeNS ($namespace, string $localName) {}

	/**
	 * Removes attribute
	 * @link http://www.php.net/manual/en/domelement.removeattributenode.php
	 * @param DOMAttr $attr The attribute node.
	 * @return mixed true on success or false on failure
	 */
	public function removeAttributeNode (DOMAttr $attr) {}

	/**
	 * Adds new or modifies existing attribute
	 * @link http://www.php.net/manual/en/domelement.setattribute.php
	 * @param string $qualifiedName The name of the attribute.
	 * @param string $value The value of the attribute.
	 * @return mixed The created or modified DOMAttr or false if an error occurred.
	 */
	public function setAttribute (string $qualifiedName, string $value) {}

	/**
	 * Adds new attribute
	 * @link http://www.php.net/manual/en/domelement.setattributens.php
	 * @param mixed $namespace The namespace URI.
	 * @param string $qualifiedName The qualified name of the attribute, as prefix:tagname.
	 * @param string $value The value of the attribute.
	 * @return void 
	 */
	public function setAttributeNS ($namespace, string $qualifiedName, string $value) {}

	/**
	 * Adds new attribute node to element
	 * @link http://www.php.net/manual/en/domelement.setattributenode.php
	 * @param DOMAttr $attr The attribute node.
	 * @return mixed old node if the attribute has been replaced or null.
	 */
	public function setAttributeNode (DOMAttr $attr) {}

	/**
	 * Adds new attribute node to element
	 * @link http://www.php.net/manual/en/domelement.setattributenodens.php
	 * @param DOMAttr $attr The attribute node.
	 * @return mixed the old node if the attribute has been replaced.
	 */
	public function setAttributeNodeNS (DOMAttr $attr) {}

	/**
	 * Declares the attribute specified by name to be of type ID
	 * @link http://www.php.net/manual/en/domelement.setidattribute.php
	 * @param string $qualifiedName The name of the attribute.
	 * @param bool $isId Set it to true if you want qualifiedName to be of type
	 * ID, false otherwise.
	 * @return void 
	 */
	public function setIdAttribute (string $qualifiedName, bool $isId) {}

	/**
	 * Declares the attribute specified by local name and namespace URI to be of type ID
	 * @link http://www.php.net/manual/en/domelement.setidattributens.php
	 * @param string $namespace The namespace URI of the attribute.
	 * @param string $qualifiedName The local name of the attribute, as prefix:tagname.
	 * @param bool $isId Set it to true if you want name to be of type
	 * ID, false otherwise.
	 * @return void 
	 */
	public function setIdAttributeNS (string $namespace, string $qualifiedName, bool $isId) {}

	/**
	 * Declares the attribute specified by node to be of type ID
	 * @link http://www.php.net/manual/en/domelement.setidattributenode.php
	 * @param DOMAttr $attr The attribute node.
	 * @param bool $isId Set it to true if you want name to be of type
	 * ID, false otherwise.
	 * @return void 
	 */
	public function setIdAttributeNode (DOMAttr $attr, bool $isId) {}

	public function remove (): void {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function before (...$nodes): void {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function after (...$nodes): void {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function replaceWith (...$nodes): void {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function append (...$nodes): void {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function prepend (...$nodes): void {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node The appended child.
	 * @return mixed The node added or false on error.
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return mixed The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return mixed a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node The new node.
	 * @param mixed $child [optional] The reference node. If not supplied, node is
	 * appended to the children.
	 * @return mixed The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, $child = null) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace The namespace URI to look for.
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $otherNode) {}

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
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace The namespace URI.
	 * @return mixed The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child The removed child.
	 * @return mixed If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument-&gt;createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $child The old node.
	 * @return mixed The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

}

/**
 * The DOMText class inherits from 
 * DOMCharacterData and represents the textual
 * content of a DOMElement or 
 * DOMAttr.
 * @link http://www.php.net/manual/en/class.domtext.php
 */
class DOMText extends DOMCharacterData implements DOMChildNode {
	public $nodeName;
	public $nodeValue;
	public $nodeType;
	public $parentNode;
	public $childNodes;
	public $firstChild;
	public $lastChild;
	public $previousSibling;
	public $nextSibling;
	public $attributes;
	public $ownerDocument;
	public $namespaceURI;
	public $prefix;
	public $localName;
	public $baseURI;
	public $textContent;
	public $data;
	public $length;
	public $previousElementSibling;
	public $nextElementSibling;
	public $wholeText;


	/**
	 * Creates a new DOMText object
	 * @link http://www.php.net/manual/en/domtext.construct.php
	 * @param string $data [optional]
	 */
	public function __construct (string $data = '') {}

	/**
	 * Indicates whether this text node contains whitespace
	 * @link http://www.php.net/manual/en/domtext.iswhitespaceinelementcontent.php
	 * @return bool true if node contains zero or more whitespace characters and
	 * nothing else. Returns false otherwise.
	 */
	public function isWhitespaceInElementContent () {}

	/**
	 * Returns whether this text node contains whitespace in element content
	 * @link http://www.php.net/manual/en/domtext.iselementcontentwhitespace.php
	 * @return bool true on success or false on failure
	 */
	public function isElementContentWhitespace () {}

	/**
	 * Breaks this node into two nodes at the specified offset
	 * @link http://www.php.net/manual/en/domtext.splittext.php
	 * @param int $offset The offset at which to split, starting from 0.
	 * @return mixed The new node of the same type, which contains all the content at and after the 
	 * offset.
	 */
	public function splitText (int $offset) {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/domcharacterdata.appenddata.php
	 * @param string $data The string to append.
	 * @return true Always returns true.
	 */
	public function appendData (string $data) {}

	/**
	 * Extracts a range of data from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.substringdata.php
	 * @param int $offset Start offset of substring to extract.
	 * @param int $count The number of characters to extract.
	 * @return mixed The specified substring. If the sum of offset 
	 * and count exceeds the length, then all 16-bit units 
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count) {}

	/**
	 * Insert a string at the specified 16-bit unit offset
	 * @link http://www.php.net/manual/en/domcharacterdata.insertdata.php
	 * @param int $offset The character offset at which to insert.
	 * @param string $data The string to insert.
	 * @return bool true on success or false on failure
	 */
	public function insertData (int $offset, string $data) {}

	/**
	 * Remove a range of characters from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.deletedata.php
	 * @param int $offset The offset from which to start removing.
	 * @param int $count The number of characters to delete. If the sum of
	 * offset and count exceeds
	 * the length, then all characters to the end of the data are deleted.
	 * @return bool true on success or false on failure
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
	 * @return bool true on success or false on failure
	 */
	public function replaceData (int $offset, int $count, string $data) {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function replaceWith (...$nodes): void {}

	public function remove (): void {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function before (...$nodes): void {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function after (...$nodes): void {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node The appended child.
	 * @return mixed The node added or false on error.
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return mixed The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return mixed a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node The new node.
	 * @param mixed $child [optional] The reference node. If not supplied, node is
	 * appended to the children.
	 * @return mixed The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, $child = null) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace The namespace URI to look for.
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $otherNode) {}

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
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace The namespace URI.
	 * @return mixed The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child The removed child.
	 * @return mixed If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument-&gt;createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $child The old node.
	 * @return mixed The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

}

/**
 * Represents comment nodes, characters delimited by &lt;!--
 * and --&gt;.
 * @link http://www.php.net/manual/en/class.domcomment.php
 */
class DOMComment extends DOMCharacterData implements DOMChildNode {
	public $nodeName;
	public $nodeValue;
	public $nodeType;
	public $parentNode;
	public $childNodes;
	public $firstChild;
	public $lastChild;
	public $previousSibling;
	public $nextSibling;
	public $attributes;
	public $ownerDocument;
	public $namespaceURI;
	public $prefix;
	public $localName;
	public $baseURI;
	public $textContent;
	public $data;
	public $length;
	public $previousElementSibling;
	public $nextElementSibling;


	/**
	 * Creates a new DOMComment object
	 * @link http://www.php.net/manual/en/domcomment.construct.php
	 * @param string $data [optional]
	 */
	public function __construct (string $data = '') {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/domcharacterdata.appenddata.php
	 * @param string $data The string to append.
	 * @return true Always returns true.
	 */
	public function appendData (string $data) {}

	/**
	 * Extracts a range of data from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.substringdata.php
	 * @param int $offset Start offset of substring to extract.
	 * @param int $count The number of characters to extract.
	 * @return mixed The specified substring. If the sum of offset 
	 * and count exceeds the length, then all 16-bit units 
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count) {}

	/**
	 * Insert a string at the specified 16-bit unit offset
	 * @link http://www.php.net/manual/en/domcharacterdata.insertdata.php
	 * @param int $offset The character offset at which to insert.
	 * @param string $data The string to insert.
	 * @return bool true on success or false on failure
	 */
	public function insertData (int $offset, string $data) {}

	/**
	 * Remove a range of characters from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.deletedata.php
	 * @param int $offset The offset from which to start removing.
	 * @param int $count The number of characters to delete. If the sum of
	 * offset and count exceeds
	 * the length, then all characters to the end of the data are deleted.
	 * @return bool true on success or false on failure
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
	 * @return bool true on success or false on failure
	 */
	public function replaceData (int $offset, int $count, string $data) {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function replaceWith (...$nodes): void {}

	public function remove (): void {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function before (...$nodes): void {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function after (...$nodes): void {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node The appended child.
	 * @return mixed The node added or false on error.
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return mixed The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return mixed a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node The new node.
	 * @param mixed $child [optional] The reference node. If not supplied, node is
	 * appended to the children.
	 * @return mixed The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, $child = null) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace The namespace URI to look for.
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $otherNode) {}

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
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace The namespace URI.
	 * @return mixed The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child The removed child.
	 * @return mixed If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument-&gt;createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $child The old node.
	 * @return mixed The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

}

/**
 * The DOMCdataSection inherits from 
 * DOMText for textural representation 
 * of CData constructs.
 * @link http://www.php.net/manual/en/class.domcdatasection.php
 */
class DOMCdataSection extends DOMText implements DOMChildNode {
	public $nodeName;
	public $nodeValue;
	public $nodeType;
	public $parentNode;
	public $childNodes;
	public $firstChild;
	public $lastChild;
	public $previousSibling;
	public $nextSibling;
	public $attributes;
	public $ownerDocument;
	public $namespaceURI;
	public $prefix;
	public $localName;
	public $baseURI;
	public $textContent;
	public $data;
	public $length;
	public $previousElementSibling;
	public $nextElementSibling;
	public $wholeText;


	/**
	 * Constructs a new DOMCdataSection object
	 * @link http://www.php.net/manual/en/domcdatasection.construct.php
	 * @param string $data
	 */
	public function __construct (string $data) {}

	/**
	 * Indicates whether this text node contains whitespace
	 * @link http://www.php.net/manual/en/domtext.iswhitespaceinelementcontent.php
	 * @return bool true if node contains zero or more whitespace characters and
	 * nothing else. Returns false otherwise.
	 */
	public function isWhitespaceInElementContent () {}

	/**
	 * Returns whether this text node contains whitespace in element content
	 * @link http://www.php.net/manual/en/domtext.iselementcontentwhitespace.php
	 * @return bool true on success or false on failure
	 */
	public function isElementContentWhitespace () {}

	/**
	 * Breaks this node into two nodes at the specified offset
	 * @link http://www.php.net/manual/en/domtext.splittext.php
	 * @param int $offset The offset at which to split, starting from 0.
	 * @return mixed The new node of the same type, which contains all the content at and after the 
	 * offset.
	 */
	public function splitText (int $offset) {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/domcharacterdata.appenddata.php
	 * @param string $data The string to append.
	 * @return true Always returns true.
	 */
	public function appendData (string $data) {}

	/**
	 * Extracts a range of data from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.substringdata.php
	 * @param int $offset Start offset of substring to extract.
	 * @param int $count The number of characters to extract.
	 * @return mixed The specified substring. If the sum of offset 
	 * and count exceeds the length, then all 16-bit units 
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count) {}

	/**
	 * Insert a string at the specified 16-bit unit offset
	 * @link http://www.php.net/manual/en/domcharacterdata.insertdata.php
	 * @param int $offset The character offset at which to insert.
	 * @param string $data The string to insert.
	 * @return bool true on success or false on failure
	 */
	public function insertData (int $offset, string $data) {}

	/**
	 * Remove a range of characters from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.deletedata.php
	 * @param int $offset The offset from which to start removing.
	 * @param int $count The number of characters to delete. If the sum of
	 * offset and count exceeds
	 * the length, then all characters to the end of the data are deleted.
	 * @return bool true on success or false on failure
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
	 * @return bool true on success or false on failure
	 */
	public function replaceData (int $offset, int $count, string $data) {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function replaceWith (...$nodes): void {}

	public function remove (): void {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function before (...$nodes): void {}

	/**
	 * @param mixed $nodes [optional]
	 */
	public function after (...$nodes): void {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node The appended child.
	 * @return mixed The node added or false on error.
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return mixed The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return mixed a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node The new node.
	 * @param mixed $child [optional] The reference node. If not supplied, node is
	 * appended to the children.
	 * @return mixed The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, $child = null) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace The namespace URI to look for.
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $otherNode) {}

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
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace The namespace URI.
	 * @return mixed The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child The removed child.
	 * @return mixed If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument-&gt;createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $child The old node.
	 * @return mixed The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

}

/**
 * Each DOMDocument has a doctype
 * attribute whose value is either null or a DOMDocumentType object.
 * @link http://www.php.net/manual/en/class.domdocumenttype.php
 */
class DOMDocumentType extends DOMNode  {
	public $nodeName;
	public $nodeValue;
	public $nodeType;
	public $parentNode;
	public $childNodes;
	public $firstChild;
	public $lastChild;
	public $previousSibling;
	public $nextSibling;
	public $attributes;
	public $ownerDocument;
	public $namespaceURI;
	public $prefix;
	public $localName;
	public $baseURI;
	public $textContent;
	public $name;
	public $entities;
	public $notations;
	public $publicId;
	public $systemId;
	public $internalSubset;


	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node The appended child.
	 * @return mixed The node added or false on error.
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return mixed The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return mixed a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node The new node.
	 * @param mixed $child [optional] The reference node. If not supplied, node is
	 * appended to the children.
	 * @return mixed The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, $child = null) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace The namespace URI to look for.
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $otherNode) {}

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
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace The namespace URI.
	 * @return mixed The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child The removed child.
	 * @return mixed If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument-&gt;createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $child The old node.
	 * @return mixed The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

}

/**
 * @link http://www.php.net/manual/en/class.domnotation.php
 */
class DOMNotation extends DOMNode  {
	public $nodeName;
	public $nodeValue;
	public $nodeType;
	public $parentNode;
	public $childNodes;
	public $firstChild;
	public $lastChild;
	public $previousSibling;
	public $nextSibling;
	public $attributes;
	public $ownerDocument;
	public $namespaceURI;
	public $prefix;
	public $localName;
	public $baseURI;
	public $textContent;
	public $publicId;
	public $systemId;


	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node The appended child.
	 * @return mixed The node added or false on error.
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return mixed The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return mixed a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node The new node.
	 * @param mixed $child [optional] The reference node. If not supplied, node is
	 * appended to the children.
	 * @return mixed The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, $child = null) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace The namespace URI to look for.
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $otherNode) {}

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
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace The namespace URI.
	 * @return mixed The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child The removed child.
	 * @return mixed If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument-&gt;createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $child The old node.
	 * @return mixed The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

}

/**
 * This interface represents a known entity, either parsed or unparsed, in an XML document.
 * @link http://www.php.net/manual/en/class.domentity.php
 */
class DOMEntity extends DOMNode  {
	public $nodeName;
	public $nodeValue;
	public $nodeType;
	public $parentNode;
	public $childNodes;
	public $firstChild;
	public $lastChild;
	public $previousSibling;
	public $nextSibling;
	public $attributes;
	public $ownerDocument;
	public $namespaceURI;
	public $prefix;
	public $localName;
	public $baseURI;
	public $textContent;
	public $publicId;
	public $systemId;
	public $notationName;
	public $actualEncoding;
	public $encoding;
	public $version;


	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node The appended child.
	 * @return mixed The node added or false on error.
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return mixed The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return mixed a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node The new node.
	 * @param mixed $child [optional] The reference node. If not supplied, node is
	 * appended to the children.
	 * @return mixed The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, $child = null) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace The namespace URI to look for.
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $otherNode) {}

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
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace The namespace URI.
	 * @return mixed The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child The removed child.
	 * @return mixed If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument-&gt;createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $child The old node.
	 * @return mixed The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

}

/**
 * @link http://www.php.net/manual/en/class.domentityreference.php
 */
class DOMEntityReference extends DOMNode  {
	public $nodeName;
	public $nodeValue;
	public $nodeType;
	public $parentNode;
	public $childNodes;
	public $firstChild;
	public $lastChild;
	public $previousSibling;
	public $nextSibling;
	public $attributes;
	public $ownerDocument;
	public $namespaceURI;
	public $prefix;
	public $localName;
	public $baseURI;
	public $textContent;


	/**
	 * Creates a new DOMEntityReference object
	 * @link http://www.php.net/manual/en/domentityreference.construct.php
	 * @param string $name
	 */
	public function __construct (string $name) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node The appended child.
	 * @return mixed The node added or false on error.
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return mixed The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return mixed a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node The new node.
	 * @param mixed $child [optional] The reference node. If not supplied, node is
	 * appended to the children.
	 * @return mixed The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, $child = null) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace The namespace URI to look for.
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $otherNode) {}

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
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace The namespace URI.
	 * @return mixed The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child The removed child.
	 * @return mixed If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument-&gt;createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $child The old node.
	 * @return mixed The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

}

/**
 * @link http://www.php.net/manual/en/class.domprocessinginstruction.php
 */
class DOMProcessingInstruction extends DOMNode  {
	public $nodeName;
	public $nodeValue;
	public $nodeType;
	public $parentNode;
	public $childNodes;
	public $firstChild;
	public $lastChild;
	public $previousSibling;
	public $nextSibling;
	public $attributes;
	public $ownerDocument;
	public $namespaceURI;
	public $prefix;
	public $localName;
	public $baseURI;
	public $textContent;
	public $target;
	public $data;


	/**
	 * Creates a new DOMProcessingInstruction object
	 * @link http://www.php.net/manual/en/domprocessinginstruction.construct.php
	 * @param string $name
	 * @param string $value [optional]
	 */
	public function __construct (string $namestring , $value = '') {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node The appended child.
	 * @return mixed The node added or false on error.
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param mixed $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param mixed $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return mixed Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = null, bool $withComments = null, $xpath = null, $nsPrefixes = null) {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] Indicates whether to copy all descendant nodes. This parameter is 
	 * defaulted to false.
	 * @return mixed The cloned node.
	 */
	public function cloneNode (bool $deep = null) {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo () {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return mixed a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath () {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool true on success or false on failure
	 */
	public function hasAttributes () {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool true on success or false on failure
	 */
	public function hasChildNodes () {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node The new node.
	 * @param mixed $child [optional] The reference node. If not supplied, node is
	 * appended to the children.
	 * @return mixed The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, $child = null) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace The namespace URI to look for.
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode The compared node.
	 * @return bool true on success or false on failure
	 */
	public function isSameNode (DOMNode $otherNode) {}

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
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix The prefix of the namespace.
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace The namespace URI.
	 * @return mixed The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void 
	 */
	public function normalize () {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child The removed child.
	 * @return mixed If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node The new node. It must be a member of the target document, i.e.
	 * created by one of the DOMDocument-&gt;createXXX() methods or imported in
	 * the document by .
	 * @param DOMNode $child The old node.
	 * @return mixed The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

}

/**
 * Supports XPath 1.0
 * @link http://www.php.net/manual/en/class.domxpath.php
 */
class DOMXPath  {
	public $document;
	public $registerNodeNamespaces;


	/**
	 * Creates a new DOMXPath object
	 * @link http://www.php.net/manual/en/domxpath.construct.php
	 * @param DOMDocument $document
	 * @param bool $registerNodeNS [optional]
	 */
	public function __construct (DOMDocument $documentbool , $registerNodeNS = 1) {}

	/**
	 * Evaluates the given XPath expression and returns a typed result if possible
	 * @link http://www.php.net/manual/en/domxpath.evaluate.php
	 * @param string $expression The XPath expression to execute.
	 * @param mixed $contextNode [optional] The optional contextNode can be specified for
	 * doing relative XPath queries. By default, the queries are relative to 
	 * the root element.
	 * @param bool $registerNodeNS [optional] The optional registerNodeNS can be specified to 
	 * disable automatic registration of the context node.
	 * @return mixed a typed result if possible or a DOMNodeList 
	 * containing all nodes matching the given XPath expression. 
	 * <p>
	 * If the expression is malformed or the
	 * contextNode is invalid,
	 * DOMXPath::evaluate returns false.
	 * </p>
	 */
	public function evaluate (string $expression, $contextNode = null, bool $registerNodeNS = null) {}

	/**
	 * Evaluates the given XPath expression
	 * @link http://www.php.net/manual/en/domxpath.query.php
	 * @param string $expression The XPath expression to execute.
	 * @param mixed $contextNode [optional] The optional contextNode can be specified for
	 * doing relative XPath queries. By default, the queries are relative to 
	 * the root element.
	 * @param bool $registerNodeNS [optional] The optional registerNodeNS can be specified to 
	 * disable automatic registration of the context node.
	 * @return mixed a DOMNodeList containing all nodes matching
	 * the given XPath expression. Any expression which
	 * does not return nodes will return an empty
	 * DOMNodeList.
	 * <p>
	 * If the expression is malformed or the
	 * contextNode is invalid,
	 * DOMXPath::query returns false.
	 * </p>
	 */
	public function query (string $expression, $contextNode = null, bool $registerNodeNS = null) {}

	/**
	 * Registers the namespace with the DOMXPath object
	 * @link http://www.php.net/manual/en/domxpath.registernamespace.php
	 * @param string $prefix The prefix.
	 * @param string $namespace The URI of the namespace.
	 * @return bool true on success or false on failure
	 */
	public function registerNamespace (string $prefix, string $namespace) {}

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
 * @param object $node The SimpleXMLElement node.
 * @return DOMElement The DOMElement node added.
 */
function dom_import_simplexml ($node): DOMElement {}


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
