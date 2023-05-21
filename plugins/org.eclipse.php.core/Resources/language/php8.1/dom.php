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
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = "", int $code = null, ?Throwable $previous = null): string {}

	public function __wakeup () {}

	/**
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return int Returns the exception code as int in
	 * Exception but possibly as other type in
	 * Exception descendants (for example as
	 * string in PDOException).
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Returns the filename in which the exception was created.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Returns the line number where the exception was created.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString (): string {}

}

/**
 * @link http://www.php.net/manual/en/class.domparentnode.php
 */
interface DOMParentNode  {

	/**
	 * Appends nodes after the last child node
	 * @link http://www.php.net/manual/en/domparentnode.append.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	abstract public function append (DOMNode|string ...$nodes): void

	/**
	 * Prepends nodes before the first child node
	 * @link http://www.php.net/manual/en/domparentnode.prepend.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	abstract public function prepend (DOMNode|string ...$nodes): void

}

/**
 * @link http://www.php.net/manual/en/class.domchildnode.php
 */
interface DOMChildNode  {

	/**
	 * Removes the node
	 * @link http://www.php.net/manual/en/domchildnode.remove.php
	 * @return void No value is returned.
	 */
	abstract public function remove (): void

	/**
	 * Adds nodes before the node
	 * @link http://www.php.net/manual/en/domchildnode.before.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	abstract public function before (DOMNode|string ...$nodes): void

	/**
	 * Adds nodes after the node
	 * @link http://www.php.net/manual/en/domchildnode.after.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	abstract public function after (DOMNode|string ...$nodes): void

	/**
	 * Replaces the node with new nodes
	 * @link http://www.php.net/manual/en/domchildnode.replacewith.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	abstract public function replaceWith (DOMNode|string ...$nodes): void

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
	public function getFeature (string $feature, string $version) {}

	/**
	 * Test if the DOM implementation implements a specific feature
	 * @link http://www.php.net/manual/en/domimplementation.hasfeature.php
	 * @param string $feature 
	 * @param string $version 
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasFeature (string $feature, string $version): bool {}

	/**
	 * Creates an empty DOMDocumentType object
	 * @link http://www.php.net/manual/en/domimplementation.createdocumenttype.php
	 * @param string $qualifiedName 
	 * @param string $publicId [optional] 
	 * @param string $systemId [optional] 
	 * @return DOMDocumentType|bool A new DOMDocumentType node with its 
	 * ownerDocument set to null or false on error.
	 */
	public function createDocumentType (string $qualifiedName, string $publicId = "", string $systemId = ""): DOMDocumentType|bool {}

	/**
	 * Creates a DOMDocument object of the specified type with its document element
	 * @link http://www.php.net/manual/en/domimplementation.createdocument.php
	 * @param string|null $namespace [optional] 
	 * @param string $qualifiedName [optional] 
	 * @param DOMDocumentType|null $doctype [optional] 
	 * @return DOMDocument|bool A new DOMDocument object or false on error. If
	 * namespace, qualifiedName,
	 * and doctype are null, the returned
	 * DOMDocument is empty with no document element
	 */
	public function createDocument (?string $namespace = null, string $qualifiedName = "", ?DOMDocumentType $doctype = null): DOMDocument|bool {}

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
	 * @param DOMNode $node 
	 * @return DOMNode|bool The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|bool {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|bool Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|bool {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|bool Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|bool {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|bool The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|bool {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo (): int {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string|null Returns a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath (): ?string {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasAttributes (): bool {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasChildNodes (): bool {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node 
	 * @param DOMNode|null $child [optional] 
	 * @return DOMNode|bool The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|bool {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace 
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace): bool {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSameNode (DOMNode $otherNode): bool {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature 
	 * @param string $version 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSupported (string $feature, string $version): bool {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix 
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix): string {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace 
	 * @return string|null The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace): ?string {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void No value is returned.
	 */
	public function normalize (): void {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child 
	 * @return DOMNode|bool If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|bool {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|bool The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|bool {}

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
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function appendXML (string $data): bool {}

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
	 * @param DOMNode $node 
	 * @return DOMNode|bool The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|bool {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|bool Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|bool {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|bool Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|bool {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|bool The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|bool {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo (): int {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string|null Returns a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath (): ?string {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasAttributes (): bool {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasChildNodes (): bool {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node 
	 * @param DOMNode|null $child [optional] 
	 * @return DOMNode|bool The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|bool {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace 
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace): bool {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSameNode (DOMNode $otherNode): bool {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature 
	 * @param string $version 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSupported (string $feature, string $version): bool {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix 
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix): string {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace 
	 * @return string|null The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace): ?string {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void No value is returned.
	 */
	public function normalize (): void {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child 
	 * @return DOMNode|bool If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|bool {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|bool The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|bool {}

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
	 * @return string 
	 */
	public function __construct (string $version = "1.0", string $encoding = ""): string {}

	/**
	 * Create new attribute
	 * @link http://www.php.net/manual/en/domdocument.createattribute.php
	 * @param string $localName 
	 * @return DOMAttr|bool The new DOMAttr or false if an error occurred.
	 */
	public function createAttribute (string $localName): DOMAttr|bool {}

	/**
	 * Create new attribute node with an associated namespace
	 * @link http://www.php.net/manual/en/domdocument.createattributens.php
	 * @param string|null $namespace 
	 * @param string $qualifiedName 
	 * @return DOMAttr|bool The new DOMAttr or false if an error occurred.
	 */
	public function createAttributeNS (?string $namespace, string $qualifiedName): DOMAttr|bool {}

	/**
	 * Create new cdata node
	 * @link http://www.php.net/manual/en/domdocument.createcdatasection.php
	 * @param string $data 
	 * @return DOMCdataSection|bool The new DOMCDATASection or false if an error occurred.
	 */
	public function createCDATASection (string $data): DOMCdataSection|bool {}

	/**
	 * Create new comment node
	 * @link http://www.php.net/manual/en/domdocument.createcomment.php
	 * @param string $data 
	 * @return DOMComment The new DOMComment.
	 */
	public function createComment (string $data): DOMComment {}

	/**
	 * Create new document fragment
	 * @link http://www.php.net/manual/en/domdocument.createdocumentfragment.php
	 * @return DOMDocumentFragment The new DOMDocumentFragment.
	 */
	public function createDocumentFragment (): DOMDocumentFragment {}

	/**
	 * Create new element node
	 * @link http://www.php.net/manual/en/domdocument.createelement.php
	 * @param string $localName 
	 * @param string $value [optional] 
	 * @return DOMElement|bool Returns a new instance of class DOMElement or false
	 * if an error occurred.
	 */
	public function createElement (string $localName, string $value = ""): DOMElement|bool {}

	/**
	 * Create new element node with an associated namespace
	 * @link http://www.php.net/manual/en/domdocument.createelementns.php
	 * @param string|null $namespace 
	 * @param string $qualifiedName 
	 * @param string $value [optional] 
	 * @return DOMElement|bool The new DOMElement or false if an error occurred.
	 */
	public function createElementNS (?string $namespace, string $qualifiedName, string $value = ""): DOMElement|bool {}

	/**
	 * Create new entity reference node
	 * @link http://www.php.net/manual/en/domdocument.createentityreference.php
	 * @param string $name 
	 * @return DOMEntityReference|bool The new DOMEntityReference or false if an error
	 * occurred.
	 */
	public function createEntityReference (string $name): DOMEntityReference|bool {}

	/**
	 * Creates new PI node
	 * @link http://www.php.net/manual/en/domdocument.createprocessinginstruction.php
	 * @param string $target 
	 * @param string $data [optional] 
	 * @return DOMProcessingInstruction|bool The new DOMProcessingInstruction or false if an error occurred.
	 */
	public function createProcessingInstruction (string $target, string $data = ""): DOMProcessingInstruction|bool {}

	/**
	 * Create new text node
	 * @link http://www.php.net/manual/en/domdocument.createtextnode.php
	 * @param string $data 
	 * @return DOMText The new DOMText.
	 */
	public function createTextNode (string $data): DOMText {}

	/**
	 * Searches for an element with a certain id
	 * @link http://www.php.net/manual/en/domdocument.getelementbyid.php
	 * @param string $elementId 
	 * @return DOMElement|null Returns the DOMElement or null if the element is
	 * not found.
	 */
	public function getElementById (string $elementId): ?DOMElement {}

	/**
	 * Searches for all elements with given local tag name
	 * @link http://www.php.net/manual/en/domdocument.getelementsbytagname.php
	 * @param string $qualifiedName 
	 * @return DOMNodeList A new DOMNodeList object containing all the matched 
	 * elements.
	 */
	public function getElementsByTagName (string $qualifiedName): DOMNodeList {}

	/**
	 * Searches for all elements with given tag name in specified namespace
	 * @link http://www.php.net/manual/en/domdocument.getelementsbytagnamens.php
	 * @param string|null $namespace 
	 * @param string $localName 
	 * @return DOMNodeList A new DOMNodeList object containing all the matched 
	 * elements.
	 */
	public function getElementsByTagNameNS (?string $namespace, string $localName): DOMNodeList {}

	/**
	 * Import node into current document
	 * @link http://www.php.net/manual/en/domdocument.importnode.php
	 * @param DOMNode $node 
	 * @param bool $deep [optional] 
	 * @return DOMNode|bool The copied node or false, if it cannot be copied.
	 */
	public function importNode (DOMNode $node, bool $deep = false): DOMNode|bool {}

	/**
	 * Load XML from a file
	 * @link http://www.php.net/manual/en/domdocument.load.php
	 * @param string $filename 
	 * @param int $options [optional] 
	 * @return DOMDocument|bool Returns true on success or false on failure. If called statically, returns a
	 * DOMDocument or false on failure.
	 */
	public function load (string $filename, int $options = null): DOMDocument|bool {}

	/**
	 * Load XML from a string
	 * @link http://www.php.net/manual/en/domdocument.loadxml.php
	 * @param string $source 
	 * @param int $options [optional] 
	 * @return DOMDocument|bool Returns true on success or false on failure. If called statically, returns a
	 * DOMDocument or false on failure.
	 */
	public function loadXML (string $source, int $options = null): DOMDocument|bool {}

	/**
	 * Normalizes the document
	 * @link http://www.php.net/manual/en/domdocument.normalizedocument.php
	 * @return void No value is returned.
	 */
	public function normalizeDocument (): void {}

	/**
	 * Register extended class used to create base node type
	 * @link http://www.php.net/manual/en/domdocument.registernodeclass.php
	 * @param string $baseClass 
	 * @param string|null $extendedClass 
	 * @return bool Returns true on success or false on failure.
	 */
	public function registerNodeClass (string $baseClass, ?string $extendedClass): bool {}

	/**
	 * Dumps the internal XML tree back into a file
	 * @link http://www.php.net/manual/en/domdocument.save.php
	 * @param string $filename 
	 * @param int $options [optional] 
	 * @return int|bool Returns the number of bytes written or false if an error occurred.
	 */
	public function save (string $filename, int $options = null): int|bool {}

	/**
	 * Load HTML from a string
	 * @link http://www.php.net/manual/en/domdocument.loadhtml.php
	 * @param string $source 
	 * @param int $options [optional] 
	 * @return DOMDocument|bool Returns true on success or false on failure. If called statically, returns a
	 * DOMDocument or false on failure.
	 */
	public function loadHTML (string $source, int $options = null): DOMDocument|bool {}

	/**
	 * Load HTML from a file
	 * @link http://www.php.net/manual/en/domdocument.loadhtmlfile.php
	 * @param string $filename 
	 * @param int $options [optional] 
	 * @return DOMDocument|bool Returns true on success or false on failure. If called statically, returns a
	 * DOMDocument or false on failure.
	 */
	public function loadHTMLFile (string $filename, int $options = null): DOMDocument|bool {}

	/**
	 * Dumps the internal document into a string using HTML formatting
	 * @link http://www.php.net/manual/en/domdocument.savehtml.php
	 * @param DOMNode|null $node [optional] 
	 * @return string|bool Returns the HTML, or false if an error occurred.
	 */
	public function saveHTML (?DOMNode $node = null): string|bool {}

	/**
	 * Dumps the internal document into a file using HTML formatting
	 * @link http://www.php.net/manual/en/domdocument.savehtmlfile.php
	 * @param string $filename 
	 * @return int|bool Returns the number of bytes written or false if an error occurred.
	 */
	public function saveHTMLFile (string $filename): int|bool {}

	/**
	 * Dumps the internal XML tree back into a string
	 * @link http://www.php.net/manual/en/domdocument.savexml.php
	 * @param DOMNode|null $node [optional] 
	 * @param int $options [optional] 
	 * @return string|bool Returns the XML, or false if an error occurred.
	 */
	public function saveXML (?DOMNode $node = null, int $options = null): string|bool {}

	/**
	 * Validates a document based on a schema. Only XML Schema 1.0 is supported.
	 * @link http://www.php.net/manual/en/domdocument.schemavalidate.php
	 * @param string $filename 
	 * @param int $flags [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function schemaValidate (string $filename, int $flags = null): bool {}

	/**
	 * Validates a document based on a schema
	 * @link http://www.php.net/manual/en/domdocument.schemavalidatesource.php
	 * @param string $source 
	 * @param int $flags [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function schemaValidateSource (string $source, int $flags = null): bool {}

	/**
	 * Performs relaxNG validation on the document
	 * @link http://www.php.net/manual/en/domdocument.relaxngvalidate.php
	 * @param string $filename 
	 * @return bool Returns true on success or false on failure.
	 */
	public function relaxNGValidate (string $filename): bool {}

	/**
	 * Performs relaxNG validation on the document
	 * @link http://www.php.net/manual/en/domdocument.relaxngvalidatesource.php
	 * @param string $source 
	 * @return bool Returns true on success or false on failure.
	 */
	public function relaxNGValidateSource (string $source): bool {}

	/**
	 * Validates the document based on its DTD
	 * @link http://www.php.net/manual/en/domdocument.validate.php
	 * @return bool Returns true on success or false on failure.
	 * If the document has no DTD attached, this method will return false.
	 */
	public function validate (): bool {}

	/**
	 * Substitutes XIncludes in a DOMDocument Object
	 * @link http://www.php.net/manual/en/domdocument.xinclude.php
	 * @param int $options [optional] 
	 * @return int|bool Returns the number of XIncludes in the document, -1 if some processing failed,
	 * or false if there were no substitutions.
	 */
	public function xinclude (int $options = null): int|bool {}

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
	 * @param DOMNode $node 
	 * @return DOMNode|bool The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|bool {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|bool Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|bool {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|bool Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|bool {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|bool The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|bool {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo (): int {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string|null Returns a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath (): ?string {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasAttributes (): bool {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasChildNodes (): bool {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node 
	 * @param DOMNode|null $child [optional] 
	 * @return DOMNode|bool The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|bool {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace 
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace): bool {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSameNode (DOMNode $otherNode): bool {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature 
	 * @param string $version 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSupported (string $feature, string $version): bool {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix 
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix): string {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace 
	 * @return string|null The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace): ?string {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void No value is returned.
	 */
	public function normalize (): void {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child 
	 * @return DOMNode|bool If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|bool {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|bool The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|bool {}

}

/**
 * @link http://www.php.net/manual/en/class.domnodelist.php
 */
class DOMNodeList implements IteratorAggregate, Traversable, Countable {
	public $length;


	/**
	 * Get number of nodes in the list
	 * @link http://www.php.net/manual/en/domnodelist.count.php
	 * @return int Returns the number of nodes in the list, which is identical to the
	 * length property.
	 */
	public function count (): int {}

	public function getIterator (): Iterator {}

	/**
	 * Retrieves a node specified by index
	 * @link http://www.php.net/manual/en/domnodelist.item.php
	 * @param int $index 
	 * @return DOMNode|DOMNameSpaceNode|null The node at the indexth position in the 
	 * DOMNodeList, or null if that is not a valid
	 * index.
	 */
	public function item (int $index): DOMNode|DOMNameSpaceNode|null {}

}

/**
 * @link http://www.php.net/manual/en/class.domnamednodemap.php
 */
class DOMNamedNodeMap implements IteratorAggregate, Traversable, Countable {
	public $length;


	/**
	 * Retrieves a node specified by name
	 * @link http://www.php.net/manual/en/domnamednodemap.getnameditem.php
	 * @param string $qualifiedName 
	 * @return DOMNode|null A node (of any type) with the specified nodeName, or 
	 * null if no node is found.
	 */
	public function getNamedItem (string $qualifiedName): ?DOMNode {}

	/**
	 * Retrieves a node specified by local name and namespace URI
	 * @link http://www.php.net/manual/en/domnamednodemap.getnameditemns.php
	 * @param string|null $namespace 
	 * @param string $localName 
	 * @return DOMNode|null A node (of any type) with the specified local name and namespace URI, or 
	 * null if no node is found.
	 */
	public function getNamedItemNS (?string $namespace, string $localName): ?DOMNode {}

	/**
	 * Retrieves a node specified by index
	 * @link http://www.php.net/manual/en/domnamednodemap.item.php
	 * @param int $index 
	 * @return DOMNode|null The node at the indexth position in the map, or null
	 * if that is not a valid index (greater than or equal to the number of nodes 
	 * in this map).
	 */
	public function item (int $index): ?DOMNode {}

	/**
	 * Get number of nodes in the map
	 * @link http://www.php.net/manual/en/domnamednodemap.count.php
	 * @return int Returns the number of nodes in the map, which is identical to the
	 * length property.
	 */
	public function count (): int {}

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
	 * @param string $data 
	 * @return bool Always returns true.
	 */
	public function appendData (string $data): bool {}

	/**
	 * Extracts a range of data from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.substringdata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return string|bool The specified substring. If the sum of offset 
	 * and count exceeds the length, then all 16-bit units 
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count): string|bool {}

	/**
	 * Insert a string at the specified 16-bit unit offset
	 * @link http://www.php.net/manual/en/domcharacterdata.insertdata.php
	 * @param int $offset 
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function insertData (int $offset, string $data): bool {}

	/**
	 * Remove a range of characters from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.deletedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return bool Returns true on success or false on failure.
	 */
	public function deleteData (int $offset, int $count): bool {}

	/**
	 * Replace a substring within the DOMCharacterData node
	 * @link http://www.php.net/manual/en/domcharacterdata.replacedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function replaceData (int $offset, int $count, string $data): bool {}

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
	 * @param DOMNode $node 
	 * @return DOMNode|bool The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|bool {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|bool Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|bool {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|bool Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|bool {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|bool The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|bool {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo (): int {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string|null Returns a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath (): ?string {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasAttributes (): bool {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasChildNodes (): bool {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node 
	 * @param DOMNode|null $child [optional] 
	 * @return DOMNode|bool The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|bool {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace 
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace): bool {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSameNode (DOMNode $otherNode): bool {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature 
	 * @param string $version 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSupported (string $feature, string $version): bool {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix 
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix): string {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace 
	 * @return string|null The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace): ?string {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void No value is returned.
	 */
	public function normalize (): void {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child 
	 * @return DOMNode|bool If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|bool {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|bool The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|bool {}

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
	 * @return string 
	 */
	public function __construct (string $name, string $value = ""): string {}

	/**
	 * Checks if attribute is a defined ID
	 * @link http://www.php.net/manual/en/domattr.isid.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function isId (): bool {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node 
	 * @return DOMNode|bool The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|bool {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|bool Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|bool {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|bool Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|bool {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|bool The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|bool {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo (): int {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string|null Returns a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath (): ?string {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasAttributes (): bool {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasChildNodes (): bool {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node 
	 * @param DOMNode|null $child [optional] 
	 * @return DOMNode|bool The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|bool {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace 
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace): bool {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSameNode (DOMNode $otherNode): bool {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature 
	 * @param string $version 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSupported (string $feature, string $version): bool {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix 
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix): string {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace 
	 * @return string|null The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace): ?string {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void No value is returned.
	 */
	public function normalize (): void {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child 
	 * @return DOMNode|bool If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|bool {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|bool The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|bool {}

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
	 * @param string|null $value [optional] 
	 * @param string $namespace [optional] 
	 * @return string 
	 */
	public function __construct (string $qualifiedName, ?string $value = null, string $namespace = ""): string {}

	/**
	 * Returns value of attribute
	 * @link http://www.php.net/manual/en/domelement.getattribute.php
	 * @param string $qualifiedName 
	 * @return string The value of the attribute, or an empty string if no attribute with the
	 * given qualifiedName is found.
	 */
	public function getAttribute (string $qualifiedName): string {}

	/**
	 * Returns value of attribute
	 * @link http://www.php.net/manual/en/domelement.getattributens.php
	 * @param string|null $namespace 
	 * @param string $localName 
	 * @return string The value of the attribute, or an empty string if no attribute with the
	 * given localName and namespace 
	 * is found.
	 */
	public function getAttributeNS (?string $namespace, string $localName): string {}

	/**
	 * Returns attribute node
	 * @link http://www.php.net/manual/en/domelement.getattributenode.php
	 * @param string $qualifiedName 
	 * @return DOMAttr|DOMNameSpaceNode|bool The attribute node. Note that for XML namespace declarations
	 * (xmlns and xmlns:&#42; attributes) an 
	 * instance of DOMNameSpaceNode is returned instead of a
	 * DOMAttr.
	 */
	public function getAttributeNode (string $qualifiedName): DOMAttr|DOMNameSpaceNode|bool {}

	/**
	 * Returns attribute node
	 * @link http://www.php.net/manual/en/domelement.getattributenodens.php
	 * @param string|null $namespace 
	 * @param string $localName 
	 * @return DOMAttr|DOMNameSpaceNode|null The attribute node. Note that for XML namespace declarations
	 * (xmlns and xmlns:&#42; attributes) an 
	 * instance of DOMNameSpaceNode is returned instead of a
	 * DOMAttr object.
	 */
	public function getAttributeNodeNS (?string $namespace, string $localName): DOMAttr|DOMNameSpaceNode|null {}

	/**
	 * Gets elements by tagname
	 * @link http://www.php.net/manual/en/domelement.getelementsbytagname.php
	 * @param string $qualifiedName 
	 * @return DOMNodeList This function returns a new instance of the class
	 * DOMNodeList of all matched elements.
	 */
	public function getElementsByTagName (string $qualifiedName): DOMNodeList {}

	/**
	 * Get elements by namespaceURI and localName
	 * @link http://www.php.net/manual/en/domelement.getelementsbytagnamens.php
	 * @param string|null $namespace 
	 * @param string $localName 
	 * @return DOMNodeList This function returns a new instance of the class
	 * DOMNodeList of all matched elements in the order in
	 * which they are encountered in a preorder traversal of this element tree.
	 */
	public function getElementsByTagNameNS (?string $namespace, string $localName): DOMNodeList {}

	/**
	 * Checks to see if attribute exists
	 * @link http://www.php.net/manual/en/domelement.hasattribute.php
	 * @param string $qualifiedName 
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasAttribute (string $qualifiedName): bool {}

	/**
	 * Checks to see if attribute exists
	 * @link http://www.php.net/manual/en/domelement.hasattributens.php
	 * @param string|null $namespace 
	 * @param string $localName 
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasAttributeNS (?string $namespace, string $localName): bool {}

	/**
	 * Removes attribute
	 * @link http://www.php.net/manual/en/domelement.removeattribute.php
	 * @param string $qualifiedName 
	 * @return bool Returns true on success or false on failure.
	 */
	public function removeAttribute (string $qualifiedName): bool {}

	/**
	 * Removes attribute
	 * @link http://www.php.net/manual/en/domelement.removeattributens.php
	 * @param string|null $namespace 
	 * @param string $localName 
	 * @return void No value is returned.
	 */
	public function removeAttributeNS (?string $namespace, string $localName): void {}

	/**
	 * Removes attribute
	 * @link http://www.php.net/manual/en/domelement.removeattributenode.php
	 * @param DOMAttr $attr 
	 * @return DOMAttr|bool Returns true on success or false on failure.
	 */
	public function removeAttributeNode (DOMAttr $attr): DOMAttr|bool {}

	/**
	 * Adds new or modifies existing attribute
	 * @link http://www.php.net/manual/en/domelement.setattribute.php
	 * @param string $qualifiedName 
	 * @param string $value 
	 * @return DOMAttr|bool The created or modified DOMAttr or false if an error occurred.
	 */
	public function setAttribute (string $qualifiedName, string $value): DOMAttr|bool {}

	/**
	 * Adds new attribute
	 * @link http://www.php.net/manual/en/domelement.setattributens.php
	 * @param string|null $namespace 
	 * @param string $qualifiedName 
	 * @param string $value 
	 * @return void No value is returned.
	 */
	public function setAttributeNS (?string $namespace, string $qualifiedName, string $value): void {}

	/**
	 * Adds new attribute node to element
	 * @link http://www.php.net/manual/en/domelement.setattributenode.php
	 * @param DOMAttr $attr 
	 * @return DOMAttr|null|bool Returns old node if the attribute has been replaced or null.
	 */
	public function setAttributeNode (DOMAttr $attr): DOMAttr|null|bool {}

	/**
	 * Adds new attribute node to element
	 * @link http://www.php.net/manual/en/domelement.setattributenodens.php
	 * @param DOMAttr $attr 
	 * @return DOMAttr|null|bool Returns the old node if the attribute has been replaced.
	 */
	public function setAttributeNodeNS (DOMAttr $attr): DOMAttr|null|bool {}

	/**
	 * Declares the attribute specified by name to be of type ID
	 * @link http://www.php.net/manual/en/domelement.setidattribute.php
	 * @param string $qualifiedName 
	 * @param bool $isId 
	 * @return void No value is returned.
	 */
	public function setIdAttribute (string $qualifiedName, bool $isId): void {}

	/**
	 * Declares the attribute specified by local name and namespace URI to be of type ID
	 * @link http://www.php.net/manual/en/domelement.setidattributens.php
	 * @param string $namespace 
	 * @param string $qualifiedName 
	 * @param bool $isId 
	 * @return void No value is returned.
	 */
	public function setIdAttributeNS (string $namespace, string $qualifiedName, bool $isId): void {}

	/**
	 * Declares the attribute specified by node to be of type ID
	 * @link http://www.php.net/manual/en/domelement.setidattributenode.php
	 * @param DOMAttr $attr 
	 * @param bool $isId 
	 * @return void No value is returned.
	 */
	public function setIdAttributeNode (DOMAttr $attr, bool $isId): void {}

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
	 * @param DOMNode $node 
	 * @return DOMNode|bool The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|bool {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|bool Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|bool {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|bool Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|bool {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|bool The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|bool {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo (): int {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string|null Returns a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath (): ?string {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasAttributes (): bool {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasChildNodes (): bool {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node 
	 * @param DOMNode|null $child [optional] 
	 * @return DOMNode|bool The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|bool {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace 
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace): bool {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSameNode (DOMNode $otherNode): bool {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature 
	 * @param string $version 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSupported (string $feature, string $version): bool {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix 
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix): string {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace 
	 * @return string|null The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace): ?string {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void No value is returned.
	 */
	public function normalize (): void {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child 
	 * @return DOMNode|bool If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|bool {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|bool The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|bool {}

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
	 * @return string 
	 */
	public function __construct (string $data = ""): string {}

	/**
	 * Indicates whether this text node contains whitespace
	 * @link http://www.php.net/manual/en/domtext.iswhitespaceinelementcontent.php
	 * @return bool Returns true if node contains zero or more whitespace characters and
	 * nothing else. Returns false otherwise.
	 */
	public function isWhitespaceInElementContent (): bool {}

	/**
	 * Returns whether this text node contains whitespace in element content
	 * @link http://www.php.net/manual/en/domtext.iselementcontentwhitespace.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function isElementContentWhitespace (): bool {}

	/**
	 * Breaks this node into two nodes at the specified offset
	 * @link http://www.php.net/manual/en/domtext.splittext.php
	 * @param int $offset 
	 * @return DOMText|bool The new node of the same type, which contains all the content at and after the 
	 * offset.
	 */
	public function splitText (int $offset): DOMText|bool {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/domcharacterdata.appenddata.php
	 * @param string $data 
	 * @return bool Always returns true.
	 */
	public function appendData (string $data): bool {}

	/**
	 * Extracts a range of data from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.substringdata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return string|bool The specified substring. If the sum of offset 
	 * and count exceeds the length, then all 16-bit units 
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count): string|bool {}

	/**
	 * Insert a string at the specified 16-bit unit offset
	 * @link http://www.php.net/manual/en/domcharacterdata.insertdata.php
	 * @param int $offset 
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function insertData (int $offset, string $data): bool {}

	/**
	 * Remove a range of characters from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.deletedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return bool Returns true on success or false on failure.
	 */
	public function deleteData (int $offset, int $count): bool {}

	/**
	 * Replace a substring within the DOMCharacterData node
	 * @link http://www.php.net/manual/en/domcharacterdata.replacedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function replaceData (int $offset, int $count, string $data): bool {}

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
	 * @param DOMNode $node 
	 * @return DOMNode|bool The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|bool {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|bool Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|bool {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|bool Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|bool {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|bool The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|bool {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo (): int {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string|null Returns a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath (): ?string {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasAttributes (): bool {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasChildNodes (): bool {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node 
	 * @param DOMNode|null $child [optional] 
	 * @return DOMNode|bool The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|bool {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace 
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace): bool {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSameNode (DOMNode $otherNode): bool {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature 
	 * @param string $version 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSupported (string $feature, string $version): bool {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix 
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix): string {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace 
	 * @return string|null The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace): ?string {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void No value is returned.
	 */
	public function normalize (): void {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child 
	 * @return DOMNode|bool If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|bool {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|bool The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|bool {}

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
	 * @return string 
	 */
	public function __construct (string $data = ""): string {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/domcharacterdata.appenddata.php
	 * @param string $data 
	 * @return bool Always returns true.
	 */
	public function appendData (string $data): bool {}

	/**
	 * Extracts a range of data from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.substringdata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return string|bool The specified substring. If the sum of offset 
	 * and count exceeds the length, then all 16-bit units 
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count): string|bool {}

	/**
	 * Insert a string at the specified 16-bit unit offset
	 * @link http://www.php.net/manual/en/domcharacterdata.insertdata.php
	 * @param int $offset 
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function insertData (int $offset, string $data): bool {}

	/**
	 * Remove a range of characters from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.deletedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return bool Returns true on success or false on failure.
	 */
	public function deleteData (int $offset, int $count): bool {}

	/**
	 * Replace a substring within the DOMCharacterData node
	 * @link http://www.php.net/manual/en/domcharacterdata.replacedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function replaceData (int $offset, int $count, string $data): bool {}

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
	 * @param DOMNode $node 
	 * @return DOMNode|bool The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|bool {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|bool Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|bool {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|bool Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|bool {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|bool The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|bool {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo (): int {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string|null Returns a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath (): ?string {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasAttributes (): bool {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasChildNodes (): bool {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node 
	 * @param DOMNode|null $child [optional] 
	 * @return DOMNode|bool The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|bool {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace 
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace): bool {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSameNode (DOMNode $otherNode): bool {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature 
	 * @param string $version 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSupported (string $feature, string $version): bool {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix 
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix): string {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace 
	 * @return string|null The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace): ?string {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void No value is returned.
	 */
	public function normalize (): void {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child 
	 * @return DOMNode|bool If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|bool {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|bool The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|bool {}

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
	 * @param string $data The value of the CDATA node. If not supplied, an empty CDATA node is created.
	 * @return string 
	 */
	public function __construct (string $data): string {}

	/**
	 * Indicates whether this text node contains whitespace
	 * @link http://www.php.net/manual/en/domtext.iswhitespaceinelementcontent.php
	 * @return bool Returns true if node contains zero or more whitespace characters and
	 * nothing else. Returns false otherwise.
	 */
	public function isWhitespaceInElementContent (): bool {}

	/**
	 * Returns whether this text node contains whitespace in element content
	 * @link http://www.php.net/manual/en/domtext.iselementcontentwhitespace.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function isElementContentWhitespace (): bool {}

	/**
	 * Breaks this node into two nodes at the specified offset
	 * @link http://www.php.net/manual/en/domtext.splittext.php
	 * @param int $offset 
	 * @return DOMText|bool The new node of the same type, which contains all the content at and after the 
	 * offset.
	 */
	public function splitText (int $offset): DOMText|bool {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/domcharacterdata.appenddata.php
	 * @param string $data 
	 * @return bool Always returns true.
	 */
	public function appendData (string $data): bool {}

	/**
	 * Extracts a range of data from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.substringdata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return string|bool The specified substring. If the sum of offset 
	 * and count exceeds the length, then all 16-bit units 
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count): string|bool {}

	/**
	 * Insert a string at the specified 16-bit unit offset
	 * @link http://www.php.net/manual/en/domcharacterdata.insertdata.php
	 * @param int $offset 
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function insertData (int $offset, string $data): bool {}

	/**
	 * Remove a range of characters from the node
	 * @link http://www.php.net/manual/en/domcharacterdata.deletedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return bool Returns true on success or false on failure.
	 */
	public function deleteData (int $offset, int $count): bool {}

	/**
	 * Replace a substring within the DOMCharacterData node
	 * @link http://www.php.net/manual/en/domcharacterdata.replacedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function replaceData (int $offset, int $count, string $data): bool {}

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
	 * @param DOMNode $node 
	 * @return DOMNode|bool The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|bool {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|bool Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|bool {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|bool Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|bool {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|bool The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|bool {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo (): int {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string|null Returns a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath (): ?string {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasAttributes (): bool {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasChildNodes (): bool {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node 
	 * @param DOMNode|null $child [optional] 
	 * @return DOMNode|bool The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|bool {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace 
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace): bool {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSameNode (DOMNode $otherNode): bool {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature 
	 * @param string $version 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSupported (string $feature, string $version): bool {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix 
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix): string {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace 
	 * @return string|null The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace): ?string {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void No value is returned.
	 */
	public function normalize (): void {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child 
	 * @return DOMNode|bool If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|bool {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|bool The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|bool {}

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
	 * @param DOMNode $node 
	 * @return DOMNode|bool The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|bool {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|bool Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|bool {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|bool Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|bool {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|bool The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|bool {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo (): int {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string|null Returns a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath (): ?string {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasAttributes (): bool {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasChildNodes (): bool {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node 
	 * @param DOMNode|null $child [optional] 
	 * @return DOMNode|bool The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|bool {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace 
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace): bool {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSameNode (DOMNode $otherNode): bool {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature 
	 * @param string $version 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSupported (string $feature, string $version): bool {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix 
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix): string {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace 
	 * @return string|null The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace): ?string {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void No value is returned.
	 */
	public function normalize (): void {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child 
	 * @return DOMNode|bool If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|bool {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|bool The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|bool {}

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
	 * @param DOMNode $node 
	 * @return DOMNode|bool The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|bool {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|bool Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|bool {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|bool Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|bool {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|bool The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|bool {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo (): int {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string|null Returns a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath (): ?string {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasAttributes (): bool {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasChildNodes (): bool {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node 
	 * @param DOMNode|null $child [optional] 
	 * @return DOMNode|bool The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|bool {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace 
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace): bool {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSameNode (DOMNode $otherNode): bool {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature 
	 * @param string $version 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSupported (string $feature, string $version): bool {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix 
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix): string {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace 
	 * @return string|null The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace): ?string {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void No value is returned.
	 */
	public function normalize (): void {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child 
	 * @return DOMNode|bool If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|bool {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|bool The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|bool {}

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
	 * @param DOMNode $node 
	 * @return DOMNode|bool The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|bool {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|bool Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|bool {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|bool Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|bool {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|bool The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|bool {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo (): int {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string|null Returns a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath (): ?string {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasAttributes (): bool {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasChildNodes (): bool {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node 
	 * @param DOMNode|null $child [optional] 
	 * @return DOMNode|bool The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|bool {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace 
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace): bool {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSameNode (DOMNode $otherNode): bool {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature 
	 * @param string $version 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSupported (string $feature, string $version): bool {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix 
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix): string {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace 
	 * @return string|null The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace): ?string {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void No value is returned.
	 */
	public function normalize (): void {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child 
	 * @return DOMNode|bool If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|bool {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|bool The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|bool {}

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
	 * @return string 
	 */
	public function __construct (string $name): string {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node 
	 * @return DOMNode|bool The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|bool {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|bool Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|bool {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|bool Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|bool {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|bool The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|bool {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo (): int {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string|null Returns a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath (): ?string {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasAttributes (): bool {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasChildNodes (): bool {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node 
	 * @param DOMNode|null $child [optional] 
	 * @return DOMNode|bool The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|bool {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace 
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace): bool {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSameNode (DOMNode $otherNode): bool {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature 
	 * @param string $version 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSupported (string $feature, string $version): bool {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix 
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix): string {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace 
	 * @return string|null The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace): ?string {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void No value is returned.
	 */
	public function normalize (): void {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child 
	 * @return DOMNode|bool If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|bool {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|bool The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|bool {}

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
	 * @return string 
	 */
	public function __construct (string $name, string $value = ""): string {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node 
	 * @return DOMNode|bool The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|bool {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|bool Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|bool {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of xpaths to filter the nodes by.
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|bool Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|bool {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|bool The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|bool {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Always returns the line number where the node was defined in.
	 */
	public function getLineNo (): int {}

	/**
	 * Get an XPath for a node
	 * @link http://www.php.net/manual/en/domnode.getnodepath.php
	 * @return string|null Returns a string containing the XPath, or null in case of an error.
	 */
	public function getNodePath (): ?string {}

	/**
	 * Checks if node has attributes
	 * @link http://www.php.net/manual/en/domnode.hasattributes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasAttributes (): bool {}

	/**
	 * Checks if node has children
	 * @link http://www.php.net/manual/en/domnode.haschildnodes.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasChildNodes (): bool {}

	/**
	 * Adds a new child before a reference node
	 * @link http://www.php.net/manual/en/domnode.insertbefore.php
	 * @param DOMNode $node 
	 * @param DOMNode|null $child [optional] 
	 * @return DOMNode|bool The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|bool {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://www.php.net/manual/en/domnode.isdefaultnamespace.php
	 * @param string $namespace 
	 * @return bool Return true if namespace is the default
	 * namespace, false otherwise.
	 */
	public function isDefaultNamespace (string $namespace): bool {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://www.php.net/manual/en/domnode.issamenode.php
	 * @param DOMNode $otherNode 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSameNode (DOMNode $otherNode): bool {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://www.php.net/manual/en/domnode.issupported.php
	 * @param string $feature 
	 * @param string $version 
	 * @return bool Returns true on success or false on failure.
	 */
	public function isSupported (string $feature, string $version): bool {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://www.php.net/manual/en/domnode.lookupnamespaceuri.php
	 * @param string $prefix 
	 * @return string The namespace URI of the node.
	 */
	public function lookupNamespaceURI (string $prefix): string {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://www.php.net/manual/en/domnode.lookupprefix.php
	 * @param string $namespace 
	 * @return string|null The prefix of the namespace or null on error.
	 */
	public function lookupPrefix (string $namespace): ?string {}

	/**
	 * Normalizes the node
	 * @link http://www.php.net/manual/en/domnode.normalize.php
	 * @return void No value is returned.
	 */
	public function normalize (): void {}

	/**
	 * Removes child from list of children
	 * @link http://www.php.net/manual/en/domnode.removechild.php
	 * @param DOMNode $child 
	 * @return DOMNode|bool If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|bool {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|bool The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|bool {}

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
	 * @return DOMDocument 
	 */
	public function __construct (DOMDocument $document, bool $registerNodeNS = true): DOMDocument {}

	/**
	 * Evaluates the given XPath expression and returns a typed result if possible
	 * @link http://www.php.net/manual/en/domxpath.evaluate.php
	 * @param string $expression 
	 * @param DOMNode|null $contextNode [optional] 
	 * @param bool $registerNodeNS [optional] 
	 * @return mixed Returns a typed result if possible or a DOMNodeList 
	 * containing all nodes matching the given XPath expression.
	 * <p>If the expression is malformed or the
	 * contextNode is invalid,
	 * DOMXPath::evaluate returns false.</p>
	 */
	public function evaluate (string $expression, ?DOMNode $contextNode = null, bool $registerNodeNS = true): mixed {}

	/**
	 * Evaluates the given XPath expression
	 * @link http://www.php.net/manual/en/domxpath.query.php
	 * @param string $expression 
	 * @param DOMNode|null $contextNode [optional] 
	 * @param bool $registerNodeNS [optional] 
	 * @return mixed Returns a DOMNodeList containing all nodes matching
	 * the given XPath expression. Any expression which
	 * does not return nodes will return an empty
	 * DOMNodeList.
	 * <p>If the expression is malformed or the
	 * contextNode is invalid,
	 * DOMXPath::query returns false.</p>
	 */
	public function query (string $expression, ?DOMNode $contextNode = null, bool $registerNodeNS = true): mixed {}

	/**
	 * Registers the namespace with the DOMXPath object
	 * @link http://www.php.net/manual/en/domxpath.registernamespace.php
	 * @param string $prefix 
	 * @param string $namespace 
	 * @return bool Returns true on success or false on failure.
	 */
	public function registerNamespace (string $prefix, string $namespace): bool {}

	/**
	 * Register PHP functions as XPath functions
	 * @link http://www.php.net/manual/en/domxpath.registerphpfunctions.php
	 * @param string|array|null $restrict [optional] 
	 * @return void No value is returned.
	 */
	public function registerPhpFunctions (string|array|null $restrict = null): void {}

}

/**
 * Gets a DOMElement object from a
 * SimpleXMLElement object
 * @link http://www.php.net/manual/en/function.dom-import-simplexml.php
 * @param object $node 
 * @return DOMElement The DOMElement node added.
 */
function dom_import_simplexml (object $node): DOMElement {}

define ('XML_ELEMENT_NODE', 1);
define ('XML_ATTRIBUTE_NODE', 2);
define ('XML_TEXT_NODE', 3);
define ('XML_CDATA_SECTION_NODE', 4);
define ('XML_ENTITY_REF_NODE', 5);
define ('XML_ENTITY_NODE', 6);
define ('XML_PI_NODE', 7);
define ('XML_COMMENT_NODE', 8);
define ('XML_DOCUMENT_NODE', 9);
define ('XML_DOCUMENT_TYPE_NODE', 10);
define ('XML_DOCUMENT_FRAG_NODE', 11);
define ('XML_NOTATION_NODE', 12);
define ('XML_HTML_DOCUMENT_NODE', 13);
define ('XML_DTD_NODE', 14);
define ('XML_ELEMENT_DECL_NODE', 15);
define ('XML_ATTRIBUTE_DECL_NODE', 16);
define ('XML_ENTITY_DECL_NODE', 17);
define ('XML_NAMESPACE_DECL_NODE', 18);
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
define ('DOM_INDEX_SIZE_ERR', 1);
define ('DOMSTRING_SIZE_ERR', 2);
define ('DOM_HIERARCHY_REQUEST_ERR', 3);
define ('DOM_WRONG_DOCUMENT_ERR', 4);
define ('DOM_INVALID_CHARACTER_ERR', 5);
define ('DOM_NO_DATA_ALLOWED_ERR', 6);
define ('DOM_NO_MODIFICATION_ALLOWED_ERR', 7);
define ('DOM_NOT_FOUND_ERR', 8);
define ('DOM_NOT_SUPPORTED_ERR', 9);
define ('DOM_INUSE_ATTRIBUTE_ERR', 10);
define ('DOM_INVALID_STATE_ERR', 11);
define ('DOM_SYNTAX_ERR', 12);
define ('DOM_INVALID_MODIFICATION_ERR', 13);
define ('DOM_NAMESPACE_ERR', 14);
define ('DOM_INVALID_ACCESS_ERR', 15);
define ('DOM_VALIDATION_ERR', 16);

// End of dom v.20031129
