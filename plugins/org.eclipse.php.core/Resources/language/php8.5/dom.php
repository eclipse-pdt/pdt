<?php

// Start of dom v.20031129

namespace Dom {

enum AdjacentPosition implements \BackedEnum, \UnitEnum {
	const BeforeBegin = ;
	const AfterBegin = ;
	const BeforeEnd = ;
	const AfterEnd = ;


	public readonly string $name;

	public readonly string $value;

	/**
	 * {@inheritdoc}
	 */
	public static function cases (): array {}

	/**
	 * {@inheritdoc}
	 * @param string|int $value
	 */
	public static function from (string|int $value): static {}

	/**
	 * {@inheritdoc}
	 * @param string|int $value
	 */
	public static function tryFrom (string|int $value): ?static {}

}


}


namespace {

final class DOMException extends Exception implements Throwable, Stringable {

	public $code;

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

	/**
	 * {@inheritdoc}
	 */
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

final class DOMException extends Exception implements Throwable, Stringable {

	public $code;

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

	/**
	 * {@inheritdoc}
	 */
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

interface DOMParentNode  {

	/**
	 * Appends nodes after the last child node
	 * @link http://www.php.net/manual/en/domparentnode.append.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	abstract public function append (DOMNode|string ...$nodes): void;

	/**
	 * Prepends nodes before the first child node
	 * @link http://www.php.net/manual/en/domparentnode.prepend.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	abstract public function prepend (DOMNode|string ...$nodes): void;

	/**
	 * Replace children in node
	 * @link http://www.php.net/manual/en/domparentnode.replacechildren.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	abstract public function replaceChildren (DOMNode|string ...$nodes): void;

}


}


namespace Dom {

interface ParentNode  {

	/**
	 * Appends nodes after the last child node
	 * @link http://www.php.net/manual/en/dom-parentnode.append.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	abstract public function append (\Dom\Node|string ...$nodes): void;

	/**
	 * Prepends nodes before the first child node
	 * @link http://www.php.net/manual/en/dom-parentnode.prepend.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	abstract public function prepend (\Dom\Node|string ...$nodes): void;

	/**
	 * Replace children in node
	 * @link http://www.php.net/manual/en/dom-parentnode.replacechildren.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	abstract public function replaceChildren (\Dom\Node|string ...$nodes): void;

	/**
	 * Returns the first element that matches the CSS selectors
	 * @link http://www.php.net/manual/en/dom-parentnode.queryselector.php
	 * @param string $selectors A string containing one or more CSS selectors.
	 * @return \Dom\Element|null Returns the first Dom\Element that matches
	 * selectors. Returns null if no element matches.
	 */
	abstract public function querySelector (string $selectors): ?\Dom\Element;

	/**
	 * Returns a collection of elements that match the CSS selectors
	 * @link http://www.php.net/manual/en/dom-parentnode.queryselectorall.php
	 * @param string $selectors A string containing one or more CSS selectors.
	 * @return \Dom\NodeList Returns a static collection of elements that match the CSS selectors
	 * specified in selectors.
	 */
	abstract public function querySelectorAll (string $selectors): \Dom\NodeList;

}


}


namespace {

interface DOMChildNode  {

	/**
	 * Removes the node
	 * @link http://www.php.net/manual/en/domchildnode.remove.php
	 * @return void No value is returned.
	 */
	abstract public function remove (): void;

	/**
	 * Adds nodes before the node
	 * @link http://www.php.net/manual/en/domchildnode.before.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	abstract public function before (DOMNode|string ...$nodes): void;

	/**
	 * Adds nodes after the node
	 * @link http://www.php.net/manual/en/domchildnode.after.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	abstract public function after (DOMNode|string ...$nodes): void;

	/**
	 * Replaces the node with new nodes
	 * @link http://www.php.net/manual/en/domchildnode.replacewith.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	abstract public function replaceWith (DOMNode|string ...$nodes): void;

}


}


namespace Dom {

interface ChildNode  {

	/**
	 * Removes the node
	 * @link http://www.php.net/manual/en/dom-childnode.remove.php
	 * @return void No value is returned.
	 */
	abstract public function remove (): void;

	/**
	 * Adds nodes before the node
	 * @link http://www.php.net/manual/en/dom-childnode.before.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	abstract public function before (\Dom\Node|string ...$nodes): void;

	/**
	 * Adds nodes after the node
	 * @link http://www.php.net/manual/en/dom-childnode.after.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	abstract public function after (\Dom\Node|string ...$nodes): void;

	/**
	 * Replaces the node with new nodes
	 * @link http://www.php.net/manual/en/dom-childnode.replacewith.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	abstract public function replaceWith (\Dom\Node|string ...$nodes): void;

}


}


namespace {

class DOMImplementation  {

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
	 * @return DOMDocumentType|false A new DOMDocumentType node with its 
	 * ownerDocument set to null or false on error.
	 */
	public function createDocumentType (string $qualifiedName, string $publicId = '""', string $systemId = '""'): DOMDocumentType|false {}

	/**
	 * Creates a DOMDocument object of the specified type with its document element
	 * @link http://www.php.net/manual/en/domimplementation.createdocument.php
	 * @param string|null $namespace [optional] 
	 * @param string $qualifiedName [optional] 
	 * @param DOMDocumentType|null $doctype [optional] 
	 * @return DOMDocument A new DOMDocument object. If
	 * namespace, qualifiedName,
	 * and doctype are null, the returned
	 * DOMDocument is empty with no document element.
	 */
	public function createDocument (?string $namespace = null, string $qualifiedName = '""', ?DOMDocumentType $doctype = null): DOMDocument {}

}


}


namespace Dom {

class Implementation  {

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 * @param string $publicId
	 * @param string $systemId
	 */
	public function createDocumentType (string $qualifiedName, string $publicId, string $systemId): \Dom\DocumentType {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $qualifiedName
	 * @param \Dom\DocumentType|null $doctype [optional]
	 */
	public function createDocument (?string $namespace = null, string $qualifiedName, ?\Dom\DocumentType $doctype = NULL): \Dom\XMLDocument {}

	/**
	 * {@inheritdoc}
	 * @param string|null $title [optional]
	 */
	public function createHTMLDocument (?string $title = NULL): \Dom\HTMLDocument {}

}


}


namespace {

class DOMNode  {
	/**
	 * Set when the other node and reference node are not in the same tree.
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	/**
	 * Set when the other node precedes the reference node.
	const DOCUMENT_POSITION_PRECEDING = 2;
	/**
	 * Set when the other node follows the reference node.
	const DOCUMENT_POSITION_FOLLOWING = 4;
	/**
	 * Set when the other node is an ancestor of the reference node.
	const DOCUMENT_POSITION_CONTAINS = 8;
	/**
	 * Set when the other node is a descendant of the reference node.
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	/**
	 * Set when the result depends on implementation-specific behaviour and
	 * may not be portable.
	 * This may happen with disconnected nodes or with attribute nodes.
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public string $nodeName;

	public ?string $nodeValue;

	public int $nodeType;

	public ?DOMNode $parentNode;

	public ?DOMElement $parentElement;

	public DOMNodeList $childNodes;

	public ?DOMNode $firstChild;

	public ?DOMNode $lastChild;

	public ?DOMNode $previousSibling;

	public ?DOMNode $nextSibling;

	public ?DOMNamedNodeMap $attributes;

	public bool $isConnected;

	public ?DOMDocument $ownerDocument;

	public ?string $namespaceURI;

	public string $prefix;

	public ?string $localName;

	public ?string $baseURI;

	public string $textContent;

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node 
	 * @return DOMNode|false The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|false {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|false Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|false {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|false Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|false {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|false The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|false {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Returns the line number where the node was defined at parse time.
	 * If the node was created manually, the return value will be 0.
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
	 * @return DOMNode|false The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|false {}

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
	 * Checks that both nodes are equal
	 * @link http://www.php.net/manual/en/domnode.isequalnode.php
	 * @param DOMNode|null $otherNode 
	 * @return bool Returns true if both nodes are equal, false otherwise.
	 */
	public function isEqualNode (?DOMNode $otherNode): bool {}

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
	 * @param string|null $prefix 
	 * @return string|null Returns the associated namespace URI or null if none is found.
	 */
	public function lookupNamespaceURI (?string $prefix): ?string {}

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
	 * @return DOMNode|false If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|false {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|false The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|false {}

	/**
	 * Checks if node contains other node
	 * @link http://www.php.net/manual/en/domnode.contains.php
	 * @param DOMNode|DOMNameSpaceNode|null $other 
	 * @return bool Returns true if node contains other node, false otherwise.
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other): bool {}

	/**
	 * Get root node
	 * @link http://www.php.net/manual/en/domnode.getrootnode.php
	 * @param array|null $options [optional] 
	 * @return DOMNode Returns the root node.
	 */
	public function getRootNode (?array $options = null): DOMNode {}

	/**
	 * Compares the position of two nodes
	 * @link http://www.php.net/manual/en/domnode.comparedocumentposition.php
	 * @param DOMNode $other The node for which the position should be compared for, relative to this node.
	 * @return int A bitmask of the DOMNode::DOCUMENT_POSITION_&#42;
	 * constants.
	 */
	public function compareDocumentPosition (DOMNode $other): int {}

	/**
	 * Forbids serialization unless serialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.sleep.php
	 * @return array The method always throws an exception.
	 */
	public function __sleep (): array {}

	/**
	 * Forbids unserialization unless unserialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.wakeup.php
	 * @return void The method always throws an exception.
	 */
	public function __wakeup (): void {}

}


}


namespace Dom {

class Node  {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public int $nodeType;

	public string $nodeName;

	public string $baseURI;

	public bool $isConnected;

	public ?\Dom\Document $ownerDocument;

	public ?\Dom\Node $parentNode;

	public ?\Dom\Element $parentElement;

	public \Dom\NodeList $childNodes;

	public ?\Dom\Node $firstChild;

	public ?\Dom\Node $lastChild;

	public ?\Dom\Node $previousSibling;

	public ?\Dom\Node $nextSibling;

	public ?string $nodeValue;

	public ?string $textContent;

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param array $options [optional]
	 */
	public function getRootNode (array $options = array (
)): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize (): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isEqualNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isSameNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $other
	 */
	public function compareDocumentPosition (\Dom\Node $other): int {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $other
	 */
	public function contains (?\Dom\Node $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function lookupPrefix (?string $namespace = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function isDefaultNamespace (?string $namespace = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node|null $child
	 */
	public function insertBefore (\Dom\Node $node, ?\Dom\Node $child = null): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function appendChild (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node $child
	 */
	public function replaceChild (\Dom\Node $node, \Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $child
	 */
	public function removeChild (\Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath (): string {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

}


}


namespace {

class DOMNameSpaceNode  {

	public string $nodeName;

	public ?string $nodeValue;

	public int $nodeType;

	public string $prefix;

	public ?string $localName;

	public ?string $namespaceURI;

	public bool $isConnected;

	public ?DOMDocument $ownerDocument;

	public ?DOMNode $parentNode;

	public ?DOMElement $parentElement;

	/**
	 * Forbids serialization unless serialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnamespacenode.sleep.php
	 * @return array The method always throws an exception.
	 */
	public function __sleep (): array {}

	/**
	 * Forbids unserialization unless unserialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnamespacenode.wakeup.php
	 * @return void The method always throws an exception.
	 */
	public function __wakeup (): void {}

}


}


namespace Dom {

final readonly class NamespaceInfo  {

	public readonly ?string $prefix;

	public readonly ?string $namespaceURI;

	public readonly \Dom\Element $element;

	/**
	 * {@inheritdoc}
	 */
	private function __construct () {}

}


}


namespace {

class DOMDocumentFragment extends DOMNode implements DOMParentNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public ?DOMElement $firstElementChild;

	public ?DOMElement $lastElementChild;

	public int $childElementCount;

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
	 * Appends nodes after the last child node
	 * @link http://www.php.net/manual/en/domdocumentfragment.append.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function append (DOMNode|string ...$nodes): void {}

	/**
	 * Prepends nodes before the first child node
	 * @link http://www.php.net/manual/en/domdocumentfragment.prepend.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function prepend (DOMNode|string ...$nodes): void {}

	/**
	 * Replace children in fragment
	 * @link http://www.php.net/manual/en/domdocumentfragment.replacechildren.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function replaceChildren (DOMNode|string ...$nodes): void {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node 
	 * @return DOMNode|false The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|false {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|false Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|false {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|false Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|false {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|false The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|false {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Returns the line number where the node was defined at parse time.
	 * If the node was created manually, the return value will be 0.
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
	 * @return DOMNode|false The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|false {}

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
	 * Checks that both nodes are equal
	 * @link http://www.php.net/manual/en/domnode.isequalnode.php
	 * @param DOMNode|null $otherNode 
	 * @return bool Returns true if both nodes are equal, false otherwise.
	 */
	public function isEqualNode (?DOMNode $otherNode): bool {}

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
	 * @param string|null $prefix 
	 * @return string|null Returns the associated namespace URI or null if none is found.
	 */
	public function lookupNamespaceURI (?string $prefix): ?string {}

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
	 * @return DOMNode|false If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|false {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|false The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|false {}

	/**
	 * Checks if node contains other node
	 * @link http://www.php.net/manual/en/domnode.contains.php
	 * @param DOMNode|DOMNameSpaceNode|null $other 
	 * @return bool Returns true if node contains other node, false otherwise.
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other): bool {}

	/**
	 * Get root node
	 * @link http://www.php.net/manual/en/domnode.getrootnode.php
	 * @param array|null $options [optional] 
	 * @return DOMNode Returns the root node.
	 */
	public function getRootNode (?array $options = null): DOMNode {}

	/**
	 * Compares the position of two nodes
	 * @link http://www.php.net/manual/en/domnode.comparedocumentposition.php
	 * @param DOMNode $other The node for which the position should be compared for, relative to this node.
	 * @return int A bitmask of the DOMNode::DOCUMENT_POSITION_&#42;
	 * constants.
	 */
	public function compareDocumentPosition (DOMNode $other): int {}

	/**
	 * Forbids serialization unless serialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.sleep.php
	 * @return array The method always throws an exception.
	 */
	public function __sleep (): array {}

	/**
	 * Forbids unserialization unless unserialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.wakeup.php
	 * @return void The method always throws an exception.
	 */
	public function __wakeup (): void {}

}


}


namespace Dom {

class DocumentFragment extends \Dom\Node implements \Dom\ParentNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public \Dom\HTMLCollection $children;

	public ?\Dom\Element $firstElementChild;

	public ?\Dom\Element $lastElementChild;

	public int $childElementCount;

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function appendXml (string $data): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function append (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function prepend (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function replaceChildren (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param string $selectors
	 */
	public function querySelector (string $selectors): ?\Dom\Element {}

	/**
	 * {@inheritdoc}
	 * @param string $selectors
	 */
	public function querySelectorAll (string $selectors): \Dom\NodeList {}

	/**
	 * {@inheritdoc}
	 * @param array $options [optional]
	 */
	public function getRootNode (array $options = array (
)): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize (): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isEqualNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isSameNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $other
	 */
	public function compareDocumentPosition (\Dom\Node $other): int {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $other
	 */
	public function contains (?\Dom\Node $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function lookupPrefix (?string $namespace = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function isDefaultNamespace (?string $namespace = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node|null $child
	 */
	public function insertBefore (\Dom\Node $node, ?\Dom\Node $child = null): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function appendChild (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node $child
	 */
	public function replaceChild (\Dom\Node $node, \Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $child
	 */
	public function removeChild (\Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath (): string {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

}

abstract class Document extends \Dom\Node implements \Dom\ParentNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public \Dom\HTMLCollection $children;

	public ?\Dom\Element $firstElementChild;

	public ?\Dom\Element $lastElementChild;

	public int $childElementCount;

	public \Dom\Implementation $implementation;

	public string $URL;

	public string $documentURI;

	public string $characterSet;

	public string $charset;

	public string $inputEncoding;

	public ?\Dom\DocumentType $doctype;

	public ?\Dom\Element $documentElement;

	public ?\Dom\HTMLElement $body;

	public ?\Dom\HTMLElement $head;

	public string $title;

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function getElementsByTagName (string $qualifiedName): \Dom\HTMLCollection {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function getElementsByTagNameNS (?string $namespace = null, string $localName): \Dom\HTMLCollection {}

	/**
	 * {@inheritdoc}
	 * @param string $classNames
	 */
	public function getElementsByClassName (string $classNames): \Dom\HTMLCollection {}

	/**
	 * {@inheritdoc}
	 * @param string $localName
	 */
	public function createElement (string $localName): \Dom\Element {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $qualifiedName
	 */
	public function createElementNS (?string $namespace = null, string $qualifiedName): \Dom\Element {}

	/**
	 * {@inheritdoc}
	 */
	public function createDocumentFragment (): \Dom\DocumentFragment {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function createTextNode (string $data): \Dom\Text {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function createCDATASection (string $data): \Dom\CDATASection {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function createComment (string $data): \Dom\Comment {}

	/**
	 * {@inheritdoc}
	 * @param string $target
	 * @param string $data
	 */
	public function createProcessingInstruction (string $target, string $data): \Dom\ProcessingInstruction {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $node
	 * @param bool $deep [optional]
	 */
	public function importNode (?\Dom\Node $node = null, bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function adoptNode (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param string $localName
	 */
	public function createAttribute (string $localName): \Dom\Attr {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $qualifiedName
	 */
	public function createAttributeNS (?string $namespace = null, string $qualifiedName): \Dom\Attr {}

	/**
	 * {@inheritdoc}
	 * @param string $elementId
	 */
	public function getElementById (string $elementId): ?\Dom\Element {}

	/**
	 * {@inheritdoc}
	 * @param string $baseClass
	 * @param string|null $extendedClass
	 */
	public function registerNodeClass (string $baseClass, ?string $extendedClass = null): void {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param int $flags [optional]
	 */
	public function schemaValidate (string $filename, int $flags = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $source
	 * @param int $flags [optional]
	 */
	public function schemaValidateSource (string $source, int $flags = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 */
	public function relaxNgValidate (string $filename): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $source
	 */
	public function relaxNgValidateSource (string $source): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function append (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function prepend (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function replaceChildren (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \DOMNode $node
	 * @param bool $deep [optional]
	 */
	public function importLegacyNode (\DOMNode $node, bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param string $selectors
	 */
	public function querySelector (string $selectors): ?\Dom\Element {}

	/**
	 * {@inheritdoc}
	 * @param string $selectors
	 */
	public function querySelectorAll (string $selectors): \Dom\NodeList {}

	/**
	 * {@inheritdoc}
	 * @param array $options [optional]
	 */
	public function getRootNode (array $options = array (
)): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize (): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isEqualNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isSameNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $other
	 */
	public function compareDocumentPosition (\Dom\Node $other): int {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $other
	 */
	public function contains (?\Dom\Node $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function lookupPrefix (?string $namespace = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function isDefaultNamespace (?string $namespace = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node|null $child
	 */
	public function insertBefore (\Dom\Node $node, ?\Dom\Node $child = null): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function appendChild (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node $child
	 */
	public function replaceChild (\Dom\Node $node, \Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $child
	 */
	public function removeChild (\Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath (): string {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

}


}


namespace {

class DOMDocument extends DOMNode implements DOMParentNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public ?DOMDocumentType $doctype;

	public DOMImplementation $implementation;

	public ?DOMElement $documentElement;

	public ?string $actualEncoding;

	public ?string $encoding;

	public ?string $xmlEncoding;

	public bool $standalone;

	public bool $xmlStandalone;

	public ?string $version;

	public ?string $xmlVersion;

	public bool $strictErrorChecking;

	public ?string $documentURI;

	public mixed $config;

	public bool $formatOutput;

	public bool $validateOnParse;

	public bool $resolveExternals;

	public bool $preserveWhiteSpace;

	public bool $recover;

	public bool $substituteEntities;

	public ?DOMElement $firstElementChild;

	public ?DOMElement $lastElementChild;

	public int $childElementCount;

	/**
	 * Creates a new DOMDocument object
	 * @link http://www.php.net/manual/en/domdocument.construct.php
	 * @param string $version [optional] 
	 * @param string $encoding [optional] 
	 * @return string 
	 */
	public function __construct (string $version = '"1.0"', string $encoding = '""'): string {}

	/**
	 * Create new attribute
	 * @link http://www.php.net/manual/en/domdocument.createattribute.php
	 * @param string $localName 
	 * @return DOMAttr|false The new DOMAttr or false if an error occurred.
	 */
	public function createAttribute (string $localName): DOMAttr|false {}

	/**
	 * Create new attribute node with an associated namespace
	 * @link http://www.php.net/manual/en/domdocument.createattributens.php
	 * @param string|null $namespace 
	 * @param string $qualifiedName 
	 * @return DOMAttr|false The new DOMAttr or false if an error occurred.
	 */
	public function createAttributeNS (?string $namespace, string $qualifiedName): DOMAttr|false {}

	/**
	 * Create new cdata node
	 * @link http://www.php.net/manual/en/domdocument.createcdatasection.php
	 * @param string $data 
	 * @return DOMCdataSection|false The new DOMCDATASection or false if an error occurred.
	 */
	public function createCDATASection (string $data): DOMCdataSection|false {}

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
	 * @return DOMElement|false Returns a new instance of class DOMElement or false
	 * if an error occurred.
	 */
	public function createElement (string $localName, string $value = '""'): DOMElement|false {}

	/**
	 * Create new element node with an associated namespace
	 * @link http://www.php.net/manual/en/domdocument.createelementns.php
	 * @param string|null $namespace 
	 * @param string $qualifiedName 
	 * @param string $value [optional] 
	 * @return DOMElement|false The new DOMElement or false if an error occurred.
	 */
	public function createElementNS (?string $namespace, string $qualifiedName, string $value = '""'): DOMElement|false {}

	/**
	 * Create new entity reference node
	 * @link http://www.php.net/manual/en/domdocument.createentityreference.php
	 * @param string $name 
	 * @return DOMEntityReference|false The new DOMEntityReference or false if an error
	 * occurred.
	 */
	public function createEntityReference (string $name): DOMEntityReference|false {}

	/**
	 * Creates new PI node
	 * @link http://www.php.net/manual/en/domdocument.createprocessinginstruction.php
	 * @param string $target 
	 * @param string $data [optional] 
	 * @return DOMProcessingInstruction|false The new DOMProcessingInstruction or false if an error occurred.
	 */
	public function createProcessingInstruction (string $target, string $data = '""'): DOMProcessingInstruction|false {}

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
	 * @return DOMNode|false The copied node or false, if it cannot be copied.
	 */
	public function importNode (DOMNode $node, bool $deep = false): DOMNode|false {}

	/**
	 * Load XML from a file
	 * @link http://www.php.net/manual/en/domdocument.load.php
	 * @param string $filename 
	 * @param int $options [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function load (string $filename, int $options = null): bool {}

	/**
	 * Load XML from a string
	 * @link http://www.php.net/manual/en/domdocument.loadxml.php
	 * @param string $source 
	 * @param int $options [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function loadXML (string $source, int $options = null): bool {}

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
	 * @return true Always returns true.
	 */
	public function registerNodeClass (string $baseClass, ?string $extendedClass): true {}

	/**
	 * Dumps the internal XML tree back into a file
	 * @link http://www.php.net/manual/en/domdocument.save.php
	 * @param string $filename 
	 * @param int $options [optional] 
	 * @return int|false Returns the number of bytes written or false if an error occurred.
	 */
	public function save (string $filename, int $options = null): int|false {}

	/**
	 * Load HTML from a string
	 * @link http://www.php.net/manual/en/domdocument.loadhtml.php
	 * @param string $source 
	 * @param int $options [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function loadHTML (string $source, int $options = null): bool {}

	/**
	 * Load HTML from a file
	 * @link http://www.php.net/manual/en/domdocument.loadhtmlfile.php
	 * @param string $filename 
	 * @param int $options [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function loadHTMLFile (string $filename, int $options = null): bool {}

	/**
	 * Dumps the internal document into a string using HTML formatting
	 * @link http://www.php.net/manual/en/domdocument.savehtml.php
	 * @param DOMNode|null $node [optional] 
	 * @return string|false Returns the HTML, or false if an error occurred.
	 */
	public function saveHTML (?DOMNode $node = null): string|false {}

	/**
	 * Dumps the internal document into a file using HTML formatting
	 * @link http://www.php.net/manual/en/domdocument.savehtmlfile.php
	 * @param string $filename 
	 * @return int|false Returns the number of bytes written or false if an error occurred.
	 */
	public function saveHTMLFile (string $filename): int|false {}

	/**
	 * Dumps the internal XML tree back into a string
	 * @link http://www.php.net/manual/en/domdocument.savexml.php
	 * @param DOMNode|null $node [optional] 
	 * @param int $options [optional] 
	 * @return string|false Returns the XML, or false if an error occurred.
	 */
	public function saveXML (?DOMNode $node = null, int $options = null): string|false {}

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
	 * @return int|false Returns the number of XIncludes in the document, -1 if some processing failed,
	 * or false if there were no substitutions.
	 */
	public function xinclude (int $options = null): int|false {}

	/**
	 * Transfer a node from another document
	 * @link http://www.php.net/manual/en/domdocument.adoptnode.php
	 * @param DOMNode $node 
	 * @return DOMNode|false The node that was transfered, or false on error.
	 */
	public function adoptNode (DOMNode $node): DOMNode|false {}

	/**
	 * Appends nodes after the last child node
	 * @link http://www.php.net/manual/en/domdocument.append.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function append (DOMNode|string ...$nodes): void {}

	/**
	 * Prepends nodes before the first child node
	 * @link http://www.php.net/manual/en/domdocument.prepend.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function prepend (DOMNode|string ...$nodes): void {}

	/**
	 * Replace children in document
	 * @link http://www.php.net/manual/en/domdocument.replacechildren.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function replaceChildren (DOMNode|string ...$nodes): void {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node 
	 * @return DOMNode|false The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|false {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|false Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|false {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|false Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|false {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|false The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|false {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Returns the line number where the node was defined at parse time.
	 * If the node was created manually, the return value will be 0.
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
	 * @return DOMNode|false The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|false {}

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
	 * Checks that both nodes are equal
	 * @link http://www.php.net/manual/en/domnode.isequalnode.php
	 * @param DOMNode|null $otherNode 
	 * @return bool Returns true if both nodes are equal, false otherwise.
	 */
	public function isEqualNode (?DOMNode $otherNode): bool {}

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
	 * @param string|null $prefix 
	 * @return string|null Returns the associated namespace URI or null if none is found.
	 */
	public function lookupNamespaceURI (?string $prefix): ?string {}

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
	 * @return DOMNode|false If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|false {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|false The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|false {}

	/**
	 * Checks if node contains other node
	 * @link http://www.php.net/manual/en/domnode.contains.php
	 * @param DOMNode|DOMNameSpaceNode|null $other 
	 * @return bool Returns true if node contains other node, false otherwise.
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other): bool {}

	/**
	 * Get root node
	 * @link http://www.php.net/manual/en/domnode.getrootnode.php
	 * @param array|null $options [optional] 
	 * @return DOMNode Returns the root node.
	 */
	public function getRootNode (?array $options = null): DOMNode {}

	/**
	 * Compares the position of two nodes
	 * @link http://www.php.net/manual/en/domnode.comparedocumentposition.php
	 * @param DOMNode $other The node for which the position should be compared for, relative to this node.
	 * @return int A bitmask of the DOMNode::DOCUMENT_POSITION_&#42;
	 * constants.
	 */
	public function compareDocumentPosition (DOMNode $other): int {}

	/**
	 * Forbids serialization unless serialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.sleep.php
	 * @return array The method always throws an exception.
	 */
	public function __sleep (): array {}

	/**
	 * Forbids unserialization unless unserialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.wakeup.php
	 * @return void The method always throws an exception.
	 */
	public function __wakeup (): void {}

}


}


namespace Dom {

final class HTMLDocument extends \Dom\Document implements \Dom\ParentNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	/**
	 * Creates an empty HTML document
	 * @link http://www.php.net/manual/en/dom-htmldocument.createempty.php
	 * @param string $encoding [optional] The character encoding of the document, used for serialization when
	 * calling the save methods.
	 * @return \Dom\HTMLDocument An empty HTML document.
	 */
	public static function createEmpty (string $encoding = '"UTF-8"'): \Dom\HTMLDocument {}

	/**
	 * Parses an HTML document from a file
	 * @link http://www.php.net/manual/en/dom-htmldocument.createfromfile.php
	 * @param string $path The path to the file to parse.
	 * @param int $options [optional] >
	 * Bitwise OR
	 * of the libxml option constants.
	 * @param string|null $overrideEncoding [optional] >
	 * The encoding that the document was created in.
	 * If not provided, it will attempt to determine the encoding that is most likely used.
	 * @return \Dom\HTMLDocument The parsed document as an Dom\HTMLDocument instance.
	 */
	public static function createFromFile (string $path, int $options = null, ?string $overrideEncoding = null): \Dom\HTMLDocument {}

	/**
	 * Parses an HTML document from a string
	 * @link http://www.php.net/manual/en/dom-htmldocument.createfromstring.php
	 * @param string $source The string containing the HTML to parse.
	 * @param int $options [optional] >
	 * Bitwise OR
	 * of the libxml option constants.
	 * @param string|null $overrideEncoding [optional] >
	 * The encoding that the document was created in.
	 * If not provided, it will attempt to determine the encoding that is most likely used.
	 * @return \Dom\HTMLDocument The parsed document as an Dom\HTMLDocument instance.
	 */
	public static function createFromString (string $source, int $options = null, ?string $overrideEncoding = null): \Dom\HTMLDocument {}

	/**
	 * Serializes the document as an XML string
	 * @link http://www.php.net/manual/en/dom-htmldocument.savexml.php
	 * @param \Dom\Node|null $node [optional] The node to serialize.
	 * If not provided, the entire document is serialized.
	 * @param int $options [optional] >
	 * Additional Options.
	 * The LIBXML_NOEMPTYTAG
	 * and LIBXML_NOXMLDECL options are supported.
	 * Prior to PHP 8.3.0, only the LIBXML_NOEMPTYTAG
	 * option is supported.
	 * @return string|false The serialized XML document string in the current
	 * document encoding, or false on failure.
	 */
	public function saveXml (?\Dom\Node $node = null, int $options = null): string|false {}

	/**
	 * Serializes the document as an XML file
	 * @link http://www.php.net/manual/en/dom-htmldocument.savexmlfile.php
	 * @param string $filename The path to the file to save to.
	 * @param int $options [optional] >
	 * Additional Options.
	 * The LIBXML_NOEMPTYTAG
	 * and LIBXML_NOXMLDECL options are supported.
	 * Prior to PHP 8.3.0, only the LIBXML_NOEMPTYTAG
	 * option is supported.
	 * @return int|false The number of bytes written on success, or false on failure.
	 */
	public function saveXmlFile (string $filename, int $options = null): int|false {}

	/**
	 * Serializes the document as an HTML string
	 * @link http://www.php.net/manual/en/dom-htmldocument.savehtml.php
	 * @param \Dom\Node|null $node [optional] The node to serialize.
	 * If not provided, the entire document is serialized.
	 * @return string The serialized HTML document string in the current
	 * document encoding.
	 */
	public function saveHtml (?\Dom\Node $node = null): string {}

	/**
	 * Serializes the document as an HTML file
	 * @link http://www.php.net/manual/en/dom-htmldocument.savehtmlfile.php
	 * @param string $filename The path to the file to save to.
	 * @return int|false The number of bytes written on success, or false on failure.
	 */
	public function saveHtmlFile (string $filename): int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function getElementsByTagName (string $qualifiedName): \Dom\HTMLCollection {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function getElementsByTagNameNS (?string $namespace = null, string $localName): \Dom\HTMLCollection {}

	/**
	 * {@inheritdoc}
	 * @param string $classNames
	 */
	public function getElementsByClassName (string $classNames): \Dom\HTMLCollection {}

	/**
	 * {@inheritdoc}
	 * @param string $localName
	 */
	public function createElement (string $localName): \Dom\Element {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $qualifiedName
	 */
	public function createElementNS (?string $namespace = null, string $qualifiedName): \Dom\Element {}

	/**
	 * {@inheritdoc}
	 */
	public function createDocumentFragment (): \Dom\DocumentFragment {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function createTextNode (string $data): \Dom\Text {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function createCDATASection (string $data): \Dom\CDATASection {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function createComment (string $data): \Dom\Comment {}

	/**
	 * {@inheritdoc}
	 * @param string $target
	 * @param string $data
	 */
	public function createProcessingInstruction (string $target, string $data): \Dom\ProcessingInstruction {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $node
	 * @param bool $deep [optional]
	 */
	public function importNode (?\Dom\Node $node = null, bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function adoptNode (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param string $localName
	 */
	public function createAttribute (string $localName): \Dom\Attr {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $qualifiedName
	 */
	public function createAttributeNS (?string $namespace = null, string $qualifiedName): \Dom\Attr {}

	/**
	 * {@inheritdoc}
	 * @param string $elementId
	 */
	public function getElementById (string $elementId): ?\Dom\Element {}

	/**
	 * {@inheritdoc}
	 * @param string $baseClass
	 * @param string|null $extendedClass
	 */
	public function registerNodeClass (string $baseClass, ?string $extendedClass = null): void {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param int $flags [optional]
	 */
	public function schemaValidate (string $filename, int $flags = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $source
	 * @param int $flags [optional]
	 */
	public function schemaValidateSource (string $source, int $flags = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 */
	public function relaxNgValidate (string $filename): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $source
	 */
	public function relaxNgValidateSource (string $source): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function append (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function prepend (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function replaceChildren (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \DOMNode $node
	 * @param bool $deep [optional]
	 */
	public function importLegacyNode (\DOMNode $node, bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param string $selectors
	 */
	public function querySelector (string $selectors): ?\Dom\Element {}

	/**
	 * {@inheritdoc}
	 * @param string $selectors
	 */
	public function querySelectorAll (string $selectors): \Dom\NodeList {}

	/**
	 * {@inheritdoc}
	 * @param array $options [optional]
	 */
	public function getRootNode (array $options = array (
)): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize (): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isEqualNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isSameNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $other
	 */
	public function compareDocumentPosition (\Dom\Node $other): int {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $other
	 */
	public function contains (?\Dom\Node $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function lookupPrefix (?string $namespace = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function isDefaultNamespace (?string $namespace = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node|null $child
	 */
	public function insertBefore (\Dom\Node $node, ?\Dom\Node $child = null): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function appendChild (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node $child
	 */
	public function replaceChild (\Dom\Node $node, \Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $child
	 */
	public function removeChild (\Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath (): string {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

}

final class XMLDocument extends \Dom\Document implements \Dom\ParentNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public string $xmlEncoding;

	public bool $xmlStandalone;

	public string $xmlVersion;

	public bool $formatOutput;

	/**
	 * {@inheritdoc}
	 * @param string $version [optional]
	 * @param string $encoding [optional]
	 */
	public static function createEmpty (string $version = '1.0', string $encoding = 'UTF-8'): \Dom\XMLDocument {}

	/**
	 * {@inheritdoc}
	 * @param string $path
	 * @param int $options [optional]
	 * @param string|null $overrideEncoding [optional]
	 */
	public static function createFromFile (string $path, int $options = 0, ?string $overrideEncoding = NULL): \Dom\XMLDocument {}

	/**
	 * {@inheritdoc}
	 * @param string $source
	 * @param int $options [optional]
	 * @param string|null $overrideEncoding [optional]
	 */
	public static function createFromString (string $source, int $options = 0, ?string $overrideEncoding = NULL): \Dom\XMLDocument {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function createEntityReference (string $name): \Dom\EntityReference {}

	/**
	 * {@inheritdoc}
	 */
	public function validate (): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $options [optional]
	 */
	public function xinclude (int $options = 0): int {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $node [optional]
	 * @param int $options [optional]
	 */
	public function saveXml (?\Dom\Node $node = NULL, int $options = 0): string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param int $options [optional]
	 */
	public function saveXmlFile (string $filename, int $options = 0): int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function getElementsByTagName (string $qualifiedName): \Dom\HTMLCollection {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function getElementsByTagNameNS (?string $namespace = null, string $localName): \Dom\HTMLCollection {}

	/**
	 * {@inheritdoc}
	 * @param string $classNames
	 */
	public function getElementsByClassName (string $classNames): \Dom\HTMLCollection {}

	/**
	 * {@inheritdoc}
	 * @param string $localName
	 */
	public function createElement (string $localName): \Dom\Element {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $qualifiedName
	 */
	public function createElementNS (?string $namespace = null, string $qualifiedName): \Dom\Element {}

	/**
	 * {@inheritdoc}
	 */
	public function createDocumentFragment (): \Dom\DocumentFragment {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function createTextNode (string $data): \Dom\Text {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function createCDATASection (string $data): \Dom\CDATASection {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function createComment (string $data): \Dom\Comment {}

	/**
	 * {@inheritdoc}
	 * @param string $target
	 * @param string $data
	 */
	public function createProcessingInstruction (string $target, string $data): \Dom\ProcessingInstruction {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $node
	 * @param bool $deep [optional]
	 */
	public function importNode (?\Dom\Node $node = null, bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function adoptNode (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param string $localName
	 */
	public function createAttribute (string $localName): \Dom\Attr {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $qualifiedName
	 */
	public function createAttributeNS (?string $namespace = null, string $qualifiedName): \Dom\Attr {}

	/**
	 * {@inheritdoc}
	 * @param string $elementId
	 */
	public function getElementById (string $elementId): ?\Dom\Element {}

	/**
	 * {@inheritdoc}
	 * @param string $baseClass
	 * @param string|null $extendedClass
	 */
	public function registerNodeClass (string $baseClass, ?string $extendedClass = null): void {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param int $flags [optional]
	 */
	public function schemaValidate (string $filename, int $flags = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $source
	 * @param int $flags [optional]
	 */
	public function schemaValidateSource (string $source, int $flags = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 */
	public function relaxNgValidate (string $filename): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $source
	 */
	public function relaxNgValidateSource (string $source): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function append (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function prepend (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function replaceChildren (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \DOMNode $node
	 * @param bool $deep [optional]
	 */
	public function importLegacyNode (\DOMNode $node, bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param string $selectors
	 */
	public function querySelector (string $selectors): ?\Dom\Element {}

	/**
	 * {@inheritdoc}
	 * @param string $selectors
	 */
	public function querySelectorAll (string $selectors): \Dom\NodeList {}

	/**
	 * {@inheritdoc}
	 * @param array $options [optional]
	 */
	public function getRootNode (array $options = array (
)): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize (): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isEqualNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isSameNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $other
	 */
	public function compareDocumentPosition (\Dom\Node $other): int {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $other
	 */
	public function contains (?\Dom\Node $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function lookupPrefix (?string $namespace = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function isDefaultNamespace (?string $namespace = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node|null $child
	 */
	public function insertBefore (\Dom\Node $node, ?\Dom\Node $child = null): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function appendChild (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node $child
	 */
	public function replaceChild (\Dom\Node $node, \Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $child
	 */
	public function removeChild (\Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath (): string {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

}


}


namespace {

class DOMNodeList implements IteratorAggregate, Traversable, Countable {

	public int $length;

	/**
	 * Get number of nodes in the list
	 * @link http://www.php.net/manual/en/domnodelist.count.php
	 * @return int Returns the number of nodes in the list, which is identical to the
	 * length property.
	 */
	public function count (): int {}

	/**
	 * Retrieve an external iterator
	 * @link http://www.php.net/manual/en/domnodelist.getiterator.php
	 * @return Iterator An instance of an object implementing Iterator or
	 * Traversable
	 */
	public function getIterator (): Iterator {}

	/**
	 * Retrieves a node specified by index
	 * @link http://www.php.net/manual/en/domnodelist.item.php
	 * @param int $index 
	 * @return DOMElement|DOMNode|DOMNameSpaceNode|null The node at the indexth position in the 
	 * DOMNodeList, or null if that is not a valid
	 * index.
	 */
	public function item (int $index): DOMElement|DOMNode|DOMNameSpaceNode|null {}

}


}


namespace Dom {

class NodeList implements \IteratorAggregate, \Traversable, \Countable {

	public int $length;

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): \Iterator {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function item (int $index): ?\Dom\Node {}

}


}


namespace {

class DOMNamedNodeMap implements IteratorAggregate, Traversable, Countable {

	public int $length;

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

	/**
	 * Retrieve an external iterator
	 * @link http://www.php.net/manual/en/domnamednodemap.getiterator.php
	 * @return Iterator An instance of an object implementing Iterator or
	 * Traversable
	 */
	public function getIterator (): Iterator {}

}


}


namespace Dom {

class NamedNodeMap implements \IteratorAggregate, \Traversable, \Countable {

	public int $length;

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function item (int $index): ?\Dom\Attr {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function getNamedItem (string $qualifiedName): ?\Dom\Attr {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function getNamedItemNS (?string $namespace = null, string $localName): ?\Dom\Attr {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): \Iterator {}

}

class DtdNamedNodeMap implements \IteratorAggregate, \Traversable, \Countable {

	public int $length;

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function item (int $index): \Dom\Entity|\Dom\Notation|null {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function getNamedItem (string $qualifiedName): \Dom\Entity|\Dom\Notation|null {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function getNamedItemNS (?string $namespace = null, string $localName): \Dom\Entity|\Dom\Notation|null {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): \Iterator {}

}

class HTMLCollection implements \IteratorAggregate, \Traversable, \Countable {

	public int $length;

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function item (int $index): ?\Dom\Element {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function namedItem (string $key): ?\Dom\Element {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): \Iterator {}

}


}


namespace {

class DOMCharacterData extends DOMNode implements DOMChildNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public string $data;

	public int $length;

	public ?DOMElement $previousElementSibling;

	public ?DOMElement $nextElementSibling;

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/domcharacterdata.appenddata.php
	 * @param string $data 
	 * @return true Always returns true.
	 */
	public function appendData (string $data): true {}

	/**
	 * Extracts a range of data from the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.substringdata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return string|false The specified substring. If the sum of offset 
	 * and count exceeds the length, then all UTF-8 codepoints
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count): string|false {}

	/**
	 * Insert a string at the specified UTF-8 codepoint offset
	 * @link http://www.php.net/manual/en/domcharacterdata.insertdata.php
	 * @param int $offset 
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function insertData (int $offset, string $data): bool {}

	/**
	 * Remove a range of characters from the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.deletedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return bool Returns true on success or false on failure.
	 */
	public function deleteData (int $offset, int $count): bool {}

	/**
	 * Replace a substring within the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.replacedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function replaceData (int $offset, int $count, string $data): bool {}

	/**
	 * Replaces the character data with new nodes
	 * @link http://www.php.net/manual/en/domcharacterdata.replacewith.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function replaceWith (DOMNode|string ...$nodes): void {}

	/**
	 * Removes the character data node
	 * @link http://www.php.net/manual/en/domcharacterdata.remove.php
	 * @return void No value is returned.
	 */
	public function remove (): void {}

	/**
	 * Adds nodes before the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.before.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function before (DOMNode|string ...$nodes): void {}

	/**
	 * Adds nodes after the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.after.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function after (DOMNode|string ...$nodes): void {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node 
	 * @return DOMNode|false The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|false {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|false Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|false {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|false Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|false {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|false The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|false {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Returns the line number where the node was defined at parse time.
	 * If the node was created manually, the return value will be 0.
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
	 * @return DOMNode|false The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|false {}

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
	 * Checks that both nodes are equal
	 * @link http://www.php.net/manual/en/domnode.isequalnode.php
	 * @param DOMNode|null $otherNode 
	 * @return bool Returns true if both nodes are equal, false otherwise.
	 */
	public function isEqualNode (?DOMNode $otherNode): bool {}

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
	 * @param string|null $prefix 
	 * @return string|null Returns the associated namespace URI or null if none is found.
	 */
	public function lookupNamespaceURI (?string $prefix): ?string {}

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
	 * @return DOMNode|false If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|false {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|false The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|false {}

	/**
	 * Checks if node contains other node
	 * @link http://www.php.net/manual/en/domnode.contains.php
	 * @param DOMNode|DOMNameSpaceNode|null $other 
	 * @return bool Returns true if node contains other node, false otherwise.
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other): bool {}

	/**
	 * Get root node
	 * @link http://www.php.net/manual/en/domnode.getrootnode.php
	 * @param array|null $options [optional] 
	 * @return DOMNode Returns the root node.
	 */
	public function getRootNode (?array $options = null): DOMNode {}

	/**
	 * Compares the position of two nodes
	 * @link http://www.php.net/manual/en/domnode.comparedocumentposition.php
	 * @param DOMNode $other The node for which the position should be compared for, relative to this node.
	 * @return int A bitmask of the DOMNode::DOCUMENT_POSITION_&#42;
	 * constants.
	 */
	public function compareDocumentPosition (DOMNode $other): int {}

	/**
	 * Forbids serialization unless serialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.sleep.php
	 * @return array The method always throws an exception.
	 */
	public function __sleep (): array {}

	/**
	 * Forbids unserialization unless unserialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.wakeup.php
	 * @return void The method always throws an exception.
	 */
	public function __wakeup (): void {}

}


}


namespace Dom {

class CharacterData extends \Dom\Node implements \Dom\ChildNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public ?\Dom\Element $previousElementSibling;

	public ?\Dom\Element $nextElementSibling;

	public string $data;

	public int $length;

	/**
	 * Extracts a range of data from the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.substringdata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return string The specified substring. If the sum of offset 
	 * and count exceeds the length, then all UTF-8 codepoints
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count): string {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/dom-characterdata.appenddata.php
	 * @param string $data 
	 * @return void Always returns true.
	 */
	public function appendData (string $data): void {}

	/**
	 * Insert a string at the specified UTF-8 codepoint offset
	 * @link http://www.php.net/manual/en/dom-characterdata.insertdata.php
	 * @param int $offset 
	 * @param string $data 
	 * @return void Returns true on success or false on failure.
	 */
	public function insertData (int $offset, string $data): void {}

	/**
	 * Remove a range of characters from the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.deletedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return void Returns true on success or false on failure.
	 */
	public function deleteData (int $offset, int $count): void {}

	/**
	 * Replace a substring within the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.replacedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @param string $data 
	 * @return void Returns true on success or false on failure.
	 */
	public function replaceData (int $offset, int $count, string $data): void {}

	/**
	 * Removes the character data node
	 * @link http://www.php.net/manual/en/dom-characterdata.remove.php
	 * @return void No value is returned.
	 */
	public function remove (): void {}

	/**
	 * Adds nodes before the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.before.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	public function before (\Dom\Node|string ...$nodes): void {}

	/**
	 * Adds nodes after the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.after.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	public function after (\Dom\Node|string ...$nodes): void {}

	/**
	 * Replaces the character data with new nodes
	 * @link http://www.php.net/manual/en/dom-characterdata.replacewith.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	public function replaceWith (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param array $options [optional]
	 */
	public function getRootNode (array $options = array (
)): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize (): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isEqualNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isSameNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $other
	 */
	public function compareDocumentPosition (\Dom\Node $other): int {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $other
	 */
	public function contains (?\Dom\Node $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function lookupPrefix (?string $namespace = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function isDefaultNamespace (?string $namespace = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node|null $child
	 */
	public function insertBefore (\Dom\Node $node, ?\Dom\Node $child = null): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function appendChild (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node $child
	 */
	public function replaceChild (\Dom\Node $node, \Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $child
	 */
	public function removeChild (\Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath (): string {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

}


}


namespace {

class DOMAttr extends DOMNode  {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public string $name;

	public bool $specified;

	public string $value;

	public ?DOMElement $ownerElement;

	public mixed $schemaTypeInfo;

	/**
	 * Creates a new DOMAttr object
	 * @link http://www.php.net/manual/en/domattr.construct.php
	 * @param string $name 
	 * @param string $value [optional] 
	 * @return string 
	 */
	public function __construct (string $name, string $value = '""'): string {}

	/**
	 * Checks if attribute is a defined ID
	 * @link http://www.php.net/manual/en/domattr.isid.php
	 * @return bool Returns true if this attribute is a defined ID, false otherwise.
	 */
	public function isId (): bool {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node 
	 * @return DOMNode|false The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|false {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|false Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|false {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|false Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|false {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|false The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|false {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Returns the line number where the node was defined at parse time.
	 * If the node was created manually, the return value will be 0.
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
	 * @return DOMNode|false The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|false {}

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
	 * Checks that both nodes are equal
	 * @link http://www.php.net/manual/en/domnode.isequalnode.php
	 * @param DOMNode|null $otherNode 
	 * @return bool Returns true if both nodes are equal, false otherwise.
	 */
	public function isEqualNode (?DOMNode $otherNode): bool {}

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
	 * @param string|null $prefix 
	 * @return string|null Returns the associated namespace URI or null if none is found.
	 */
	public function lookupNamespaceURI (?string $prefix): ?string {}

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
	 * @return DOMNode|false If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|false {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|false The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|false {}

	/**
	 * Checks if node contains other node
	 * @link http://www.php.net/manual/en/domnode.contains.php
	 * @param DOMNode|DOMNameSpaceNode|null $other 
	 * @return bool Returns true if node contains other node, false otherwise.
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other): bool {}

	/**
	 * Get root node
	 * @link http://www.php.net/manual/en/domnode.getrootnode.php
	 * @param array|null $options [optional] 
	 * @return DOMNode Returns the root node.
	 */
	public function getRootNode (?array $options = null): DOMNode {}

	/**
	 * Compares the position of two nodes
	 * @link http://www.php.net/manual/en/domnode.comparedocumentposition.php
	 * @param DOMNode $other The node for which the position should be compared for, relative to this node.
	 * @return int A bitmask of the DOMNode::DOCUMENT_POSITION_&#42;
	 * constants.
	 */
	public function compareDocumentPosition (DOMNode $other): int {}

	/**
	 * Forbids serialization unless serialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.sleep.php
	 * @return array The method always throws an exception.
	 */
	public function __sleep (): array {}

	/**
	 * Forbids unserialization unless unserialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.wakeup.php
	 * @return void The method always throws an exception.
	 */
	public function __wakeup (): void {}

}


}


namespace Dom {

class Attr extends \Dom\Node  {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public ?string $namespaceURI;

	public ?string $prefix;

	public string $localName;

	public string $name;

	public string $value;

	public ?\Dom\Element $ownerElement;

	public bool $specified;

	/**
	 * Checks if attribute is a defined ID
	 * @link http://www.php.net/manual/en/dom-attr.isid.php
	 * @return bool >
	 * Returns true if this attribute is a defined ID, false otherwise.
	 */
	public function isId (): bool {}

	/**
	 * Changes the qualified name or namespace of an attribute
	 * @link http://www.php.net/manual/en/dom-attr.rename.php
	 * @param string|null $namespaceURI The new namespace URI of the attribute.
	 * @param string $qualifiedName The new qualified name of the attribute.
	 * @return void No value is returned.
	 */
	public function rename (?string $namespaceURI, string $qualifiedName): void {}

	/**
	 * {@inheritdoc}
	 * @param array $options [optional]
	 */
	public function getRootNode (array $options = array (
)): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize (): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isEqualNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isSameNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $other
	 */
	public function compareDocumentPosition (\Dom\Node $other): int {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $other
	 */
	public function contains (?\Dom\Node $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function lookupPrefix (?string $namespace = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function isDefaultNamespace (?string $namespace = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node|null $child
	 */
	public function insertBefore (\Dom\Node $node, ?\Dom\Node $child = null): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function appendChild (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node $child
	 */
	public function replaceChild (\Dom\Node $node, \Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $child
	 */
	public function removeChild (\Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath (): string {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

}


}


namespace {

class DOMElement extends DOMNode implements DOMParentNode, DOMChildNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public string $tagName;

	public string $className;

	public string $id;

	public mixed $schemaTypeInfo;

	public ?DOMElement $firstElementChild;

	public ?DOMElement $lastElementChild;

	public int $childElementCount;

	public ?DOMElement $previousElementSibling;

	public ?DOMElement $nextElementSibling;

	/**
	 * Creates a new DOMElement object
	 * @link http://www.php.net/manual/en/domelement.construct.php
	 * @param string $qualifiedName 
	 * @param string|null $value [optional] 
	 * @param string $namespace [optional] 
	 * @return string 
	 */
	public function __construct (string $qualifiedName, ?string $value = null, string $namespace = '""'): string {}

	/**
	 * Returns value of attribute
	 * @link http://www.php.net/manual/en/domelement.getattribute.php
	 * @param string $qualifiedName 
	 * @return string The value of the attribute, or an empty string if no attribute with the
	 * given qualifiedName is found.
	 */
	public function getAttribute (string $qualifiedName): string {}

	/**
	 * Get attribute names
	 * @link http://www.php.net/manual/en/domelement.getattributenames.php
	 * @return array Return attribute names.
	 */
	public function getAttributeNames (): array {}

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
	 * @return DOMAttr|DOMNameSpaceNode|false The attribute node. Note that for XML namespace declarations
	 * (xmlns and xmlns:&#42; attributes) an 
	 * instance of DOMNameSpaceNode is returned instead of a
	 * DOMAttr.
	 */
	public function getAttributeNode (string $qualifiedName): DOMAttr|DOMNameSpaceNode|false {}

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
	 * @return DOMAttr|false Returns true on success or false on failure.
	 */
	public function removeAttributeNode (DOMAttr $attr): DOMAttr|false {}

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
	 * @return DOMAttr|null|false Returns the old attribute if it has been replaced or null if there was no old attribute.
	 * If a DOM_WRONG_DOCUMENT_ERR error is raised, and strictErrorChecking is false, false is returned.
	 */
	public function setAttributeNode (DOMAttr $attr): DOMAttr|null|false {}

	/**
	 * Adds new attribute node to element
	 * @link http://www.php.net/manual/en/domelement.setattributenodens.php
	 * @param DOMAttr $attr 
	 * @return DOMAttr|null|false Returns the old attribute if it has been replaced or null if there was no old attribute.
	 * If a DOM_WRONG_DOCUMENT_ERR error is raised, and strictErrorChecking is false, false is returned.
	 */
	public function setAttributeNodeNS (DOMAttr $attr): DOMAttr|null|false {}

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

	/**
	 * Toggle attribute
	 * @link http://www.php.net/manual/en/domelement.toggleattribute.php
	 * @param string $qualifiedName 
	 * @param bool|null $force [optional] 
	 * @return bool Returns true if the attribute is present after finishing the call, false otherwise.
	 */
	public function toggleAttribute (string $qualifiedName, ?bool $force = null): bool {}

	/**
	 * Removes the element
	 * @link http://www.php.net/manual/en/domelement.remove.php
	 * @return void No value is returned.
	 */
	public function remove (): void {}

	/**
	 * Adds nodes before the element
	 * @link http://www.php.net/manual/en/domelement.before.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function before (DOMNode|string ...$nodes): void {}

	/**
	 * Adds nodes after the element
	 * @link http://www.php.net/manual/en/domelement.after.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function after (DOMNode|string ...$nodes): void {}

	/**
	 * Replaces the element with new nodes
	 * @link http://www.php.net/manual/en/domelement.replacewith.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function replaceWith (DOMNode|string ...$nodes): void {}

	/**
	 * Appends nodes after the last child node
	 * @link http://www.php.net/manual/en/domelement.append.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function append (DOMNode|string ...$nodes): void {}

	/**
	 * Prepends nodes before the first child node
	 * @link http://www.php.net/manual/en/domelement.prepend.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function prepend (DOMNode|string ...$nodes): void {}

	/**
	 * Replace children in element
	 * @link http://www.php.net/manual/en/domelement.replacechildren.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function replaceChildren (DOMNode|string ...$nodes): void {}

	/**
	 * Insert adjacent element
	 * @link http://www.php.net/manual/en/domelement.insertadjacentelement.php
	 * @param string $where 
	 * @param DOMElement $element 
	 * @return DOMElement|null Return DOMElement or null on failure.
	 */
	public function insertAdjacentElement (string $where, DOMElement $element): ?DOMElement {}

	/**
	 * Insert adjacent text
	 * @link http://www.php.net/manual/en/domelement.insertadjacenttext.php
	 * @param string $where 
	 * @param string $data 
	 * @return void No value is returned.
	 */
	public function insertAdjacentText (string $where, string $data): void {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node 
	 * @return DOMNode|false The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|false {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|false Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|false {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|false Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|false {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|false The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|false {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Returns the line number where the node was defined at parse time.
	 * If the node was created manually, the return value will be 0.
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
	 * @return DOMNode|false The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|false {}

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
	 * Checks that both nodes are equal
	 * @link http://www.php.net/manual/en/domnode.isequalnode.php
	 * @param DOMNode|null $otherNode 
	 * @return bool Returns true if both nodes are equal, false otherwise.
	 */
	public function isEqualNode (?DOMNode $otherNode): bool {}

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
	 * @param string|null $prefix 
	 * @return string|null Returns the associated namespace URI or null if none is found.
	 */
	public function lookupNamespaceURI (?string $prefix): ?string {}

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
	 * @return DOMNode|false If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|false {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|false The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|false {}

	/**
	 * Checks if node contains other node
	 * @link http://www.php.net/manual/en/domnode.contains.php
	 * @param DOMNode|DOMNameSpaceNode|null $other 
	 * @return bool Returns true if node contains other node, false otherwise.
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other): bool {}

	/**
	 * Get root node
	 * @link http://www.php.net/manual/en/domnode.getrootnode.php
	 * @param array|null $options [optional] 
	 * @return DOMNode Returns the root node.
	 */
	public function getRootNode (?array $options = null): DOMNode {}

	/**
	 * Compares the position of two nodes
	 * @link http://www.php.net/manual/en/domnode.comparedocumentposition.php
	 * @param DOMNode $other The node for which the position should be compared for, relative to this node.
	 * @return int A bitmask of the DOMNode::DOCUMENT_POSITION_&#42;
	 * constants.
	 */
	public function compareDocumentPosition (DOMNode $other): int {}

	/**
	 * Forbids serialization unless serialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.sleep.php
	 * @return array The method always throws an exception.
	 */
	public function __sleep (): array {}

	/**
	 * Forbids unserialization unless unserialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.wakeup.php
	 * @return void The method always throws an exception.
	 */
	public function __wakeup (): void {}

}


}


namespace Dom {

class Element extends \Dom\Node implements \Dom\ParentNode, \Dom\ChildNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public ?string $namespaceURI;

	public ?string $prefix;

	public string $localName;

	public string $tagName;

	public \Dom\HTMLCollection $children;

	public ?\Dom\Element $firstElementChild;

	public ?\Dom\Element $lastElementChild;

	public int $childElementCount;

	public ?\Dom\Element $previousElementSibling;

	public ?\Dom\Element $nextElementSibling;

	public string $id;

	public string $className;

	public \Dom\TokenList $classList;

	public \Dom\NamedNodeMap $attributes;

	public string $innerHTML;

	public string $outerHTML;

	public string $substitutedNodeValue;

	/**
	 * {@inheritdoc}
	 */
	public function hasAttributes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getAttributeNames (): array {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function getAttribute (string $qualifiedName): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function getAttributeNS (?string $namespace = null, string $localName): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 * @param string $value
	 */
	public function setAttribute (string $qualifiedName, string $value): void {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $qualifiedName
	 * @param string $value
	 */
	public function setAttributeNS (?string $namespace = null, string $qualifiedName, string $value): void {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function removeAttribute (string $qualifiedName): void {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function removeAttributeNS (?string $namespace = null, string $localName): void {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 * @param bool|null $force [optional]
	 */
	public function toggleAttribute (string $qualifiedName, ?bool $force = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function hasAttribute (string $qualifiedName): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function hasAttributeNS (?string $namespace = null, string $localName): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function getAttributeNode (string $qualifiedName): ?\Dom\Attr {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function getAttributeNodeNS (?string $namespace = null, string $localName): ?\Dom\Attr {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Attr $attr
	 */
	public function setAttributeNode (\Dom\Attr $attr): ?\Dom\Attr {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Attr $attr
	 */
	public function setAttributeNodeNS (\Dom\Attr $attr): ?\Dom\Attr {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Attr $attr
	 */
	public function removeAttributeNode (\Dom\Attr $attr): \Dom\Attr {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function getElementsByTagName (string $qualifiedName): \Dom\HTMLCollection {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function getElementsByTagNameNS (?string $namespace = null, string $localName): \Dom\HTMLCollection {}

	/**
	 * {@inheritdoc}
	 * @param string $classNames
	 */
	public function getElementsByClassName (string $classNames): \Dom\HTMLCollection {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\AdjacentPosition $where
	 * @param \Dom\Element $element
	 */
	public function insertAdjacentElement (\Dom\AdjacentPosition $where, \Dom\Element $element): ?\Dom\Element {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\AdjacentPosition $where
	 * @param string $data
	 */
	public function insertAdjacentText (\Dom\AdjacentPosition $where, string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\AdjacentPosition $where
	 * @param string $string
	 */
	public function insertAdjacentHTML (\Dom\AdjacentPosition $where, string $string): void {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 * @param bool $isId
	 */
	public function setIdAttribute (string $qualifiedName, bool $isId): void {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $qualifiedName
	 * @param bool $isId
	 */
	public function setIdAttributeNS (?string $namespace = null, string $qualifiedName, bool $isId): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Attr $attr
	 * @param bool $isId
	 */
	public function setIdAttributeNode (\Dom\Attr $attr, bool $isId): void {}

	/**
	 * {@inheritdoc}
	 */
	public function remove (): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function before (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function after (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function replaceWith (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function append (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function prepend (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function replaceChildren (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param string $selectors
	 */
	public function querySelector (string $selectors): ?\Dom\Element {}

	/**
	 * {@inheritdoc}
	 * @param string $selectors
	 */
	public function querySelectorAll (string $selectors): \Dom\NodeList {}

	/**
	 * {@inheritdoc}
	 * @param string $selectors
	 */
	public function closest (string $selectors): ?\Dom\Element {}

	/**
	 * {@inheritdoc}
	 * @param string $selectors
	 */
	public function matches (string $selectors): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getInScopeNamespaces (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getDescendantNamespaces (): array {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespaceURI
	 * @param string $qualifiedName
	 */
	public function rename (?string $namespaceURI = null, string $qualifiedName): void {}

	/**
	 * {@inheritdoc}
	 * @param array $options [optional]
	 */
	public function getRootNode (array $options = array (
)): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize (): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isEqualNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isSameNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $other
	 */
	public function compareDocumentPosition (\Dom\Node $other): int {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $other
	 */
	public function contains (?\Dom\Node $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function lookupPrefix (?string $namespace = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function isDefaultNamespace (?string $namespace = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node|null $child
	 */
	public function insertBefore (\Dom\Node $node, ?\Dom\Node $child = null): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function appendChild (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node $child
	 */
	public function replaceChild (\Dom\Node $node, \Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $child
	 */
	public function removeChild (\Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath (): string {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

}

class HTMLElement extends \Dom\Element implements \Dom\ChildNode, \Dom\ParentNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	/**
	 * {@inheritdoc}
	 */
	public function hasAttributes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getAttributeNames (): array {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function getAttribute (string $qualifiedName): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function getAttributeNS (?string $namespace = null, string $localName): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 * @param string $value
	 */
	public function setAttribute (string $qualifiedName, string $value): void {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $qualifiedName
	 * @param string $value
	 */
	public function setAttributeNS (?string $namespace = null, string $qualifiedName, string $value): void {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function removeAttribute (string $qualifiedName): void {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function removeAttributeNS (?string $namespace = null, string $localName): void {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 * @param bool|null $force [optional]
	 */
	public function toggleAttribute (string $qualifiedName, ?bool $force = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function hasAttribute (string $qualifiedName): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function hasAttributeNS (?string $namespace = null, string $localName): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function getAttributeNode (string $qualifiedName): ?\Dom\Attr {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function getAttributeNodeNS (?string $namespace = null, string $localName): ?\Dom\Attr {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Attr $attr
	 */
	public function setAttributeNode (\Dom\Attr $attr): ?\Dom\Attr {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Attr $attr
	 */
	public function setAttributeNodeNS (\Dom\Attr $attr): ?\Dom\Attr {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Attr $attr
	 */
	public function removeAttributeNode (\Dom\Attr $attr): \Dom\Attr {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function getElementsByTagName (string $qualifiedName): \Dom\HTMLCollection {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function getElementsByTagNameNS (?string $namespace = null, string $localName): \Dom\HTMLCollection {}

	/**
	 * {@inheritdoc}
	 * @param string $classNames
	 */
	public function getElementsByClassName (string $classNames): \Dom\HTMLCollection {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\AdjacentPosition $where
	 * @param \Dom\Element $element
	 */
	public function insertAdjacentElement (\Dom\AdjacentPosition $where, \Dom\Element $element): ?\Dom\Element {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\AdjacentPosition $where
	 * @param string $data
	 */
	public function insertAdjacentText (\Dom\AdjacentPosition $where, string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\AdjacentPosition $where
	 * @param string $string
	 */
	public function insertAdjacentHTML (\Dom\AdjacentPosition $where, string $string): void {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 * @param bool $isId
	 */
	public function setIdAttribute (string $qualifiedName, bool $isId): void {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $qualifiedName
	 * @param bool $isId
	 */
	public function setIdAttributeNS (?string $namespace = null, string $qualifiedName, bool $isId): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Attr $attr
	 * @param bool $isId
	 */
	public function setIdAttributeNode (\Dom\Attr $attr, bool $isId): void {}

	/**
	 * {@inheritdoc}
	 */
	public function remove (): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function before (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function after (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function replaceWith (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function append (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function prepend (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function replaceChildren (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param string $selectors
	 */
	public function querySelector (string $selectors): ?\Dom\Element {}

	/**
	 * {@inheritdoc}
	 * @param string $selectors
	 */
	public function querySelectorAll (string $selectors): \Dom\NodeList {}

	/**
	 * {@inheritdoc}
	 * @param string $selectors
	 */
	public function closest (string $selectors): ?\Dom\Element {}

	/**
	 * {@inheritdoc}
	 * @param string $selectors
	 */
	public function matches (string $selectors): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getInScopeNamespaces (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getDescendantNamespaces (): array {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespaceURI
	 * @param string $qualifiedName
	 */
	public function rename (?string $namespaceURI = null, string $qualifiedName): void {}

	/**
	 * {@inheritdoc}
	 * @param array $options [optional]
	 */
	public function getRootNode (array $options = array (
)): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize (): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isEqualNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isSameNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $other
	 */
	public function compareDocumentPosition (\Dom\Node $other): int {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $other
	 */
	public function contains (?\Dom\Node $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function lookupPrefix (?string $namespace = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function isDefaultNamespace (?string $namespace = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node|null $child
	 */
	public function insertBefore (\Dom\Node $node, ?\Dom\Node $child = null): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function appendChild (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node $child
	 */
	public function replaceChild (\Dom\Node $node, \Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $child
	 */
	public function removeChild (\Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath (): string {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

}


}


namespace {

class DOMText extends DOMCharacterData implements DOMChildNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public string $wholeText;

	/**
	 * Creates a new DOMText object
	 * @link http://www.php.net/manual/en/domtext.construct.php
	 * @param string $data [optional] 
	 * @return string 
	 */
	public function __construct (string $data = '""'): string {}

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
	 * @return DOMText|false The new node of the same type, which contains all the content at and after the 
	 * offset.
	 */
	public function splitText (int $offset): DOMText|false {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/domcharacterdata.appenddata.php
	 * @param string $data 
	 * @return true Always returns true.
	 */
	public function appendData (string $data): true {}

	/**
	 * Extracts a range of data from the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.substringdata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return string|false The specified substring. If the sum of offset 
	 * and count exceeds the length, then all UTF-8 codepoints
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count): string|false {}

	/**
	 * Insert a string at the specified UTF-8 codepoint offset
	 * @link http://www.php.net/manual/en/domcharacterdata.insertdata.php
	 * @param int $offset 
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function insertData (int $offset, string $data): bool {}

	/**
	 * Remove a range of characters from the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.deletedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return bool Returns true on success or false on failure.
	 */
	public function deleteData (int $offset, int $count): bool {}

	/**
	 * Replace a substring within the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.replacedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function replaceData (int $offset, int $count, string $data): bool {}

	/**
	 * Replaces the character data with new nodes
	 * @link http://www.php.net/manual/en/domcharacterdata.replacewith.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function replaceWith (DOMNode|string ...$nodes): void {}

	/**
	 * Removes the character data node
	 * @link http://www.php.net/manual/en/domcharacterdata.remove.php
	 * @return void No value is returned.
	 */
	public function remove (): void {}

	/**
	 * Adds nodes before the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.before.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function before (DOMNode|string ...$nodes): void {}

	/**
	 * Adds nodes after the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.after.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function after (DOMNode|string ...$nodes): void {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node 
	 * @return DOMNode|false The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|false {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|false Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|false {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|false Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|false {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|false The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|false {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Returns the line number where the node was defined at parse time.
	 * If the node was created manually, the return value will be 0.
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
	 * @return DOMNode|false The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|false {}

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
	 * Checks that both nodes are equal
	 * @link http://www.php.net/manual/en/domnode.isequalnode.php
	 * @param DOMNode|null $otherNode 
	 * @return bool Returns true if both nodes are equal, false otherwise.
	 */
	public function isEqualNode (?DOMNode $otherNode): bool {}

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
	 * @param string|null $prefix 
	 * @return string|null Returns the associated namespace URI or null if none is found.
	 */
	public function lookupNamespaceURI (?string $prefix): ?string {}

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
	 * @return DOMNode|false If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|false {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|false The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|false {}

	/**
	 * Checks if node contains other node
	 * @link http://www.php.net/manual/en/domnode.contains.php
	 * @param DOMNode|DOMNameSpaceNode|null $other 
	 * @return bool Returns true if node contains other node, false otherwise.
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other): bool {}

	/**
	 * Get root node
	 * @link http://www.php.net/manual/en/domnode.getrootnode.php
	 * @param array|null $options [optional] 
	 * @return DOMNode Returns the root node.
	 */
	public function getRootNode (?array $options = null): DOMNode {}

	/**
	 * Compares the position of two nodes
	 * @link http://www.php.net/manual/en/domnode.comparedocumentposition.php
	 * @param DOMNode $other The node for which the position should be compared for, relative to this node.
	 * @return int A bitmask of the DOMNode::DOCUMENT_POSITION_&#42;
	 * constants.
	 */
	public function compareDocumentPosition (DOMNode $other): int {}

	/**
	 * Forbids serialization unless serialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.sleep.php
	 * @return array The method always throws an exception.
	 */
	public function __sleep (): array {}

	/**
	 * Forbids unserialization unless unserialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.wakeup.php
	 * @return void The method always throws an exception.
	 */
	public function __wakeup (): void {}

}


}


namespace Dom {

class Text extends \Dom\CharacterData implements \Dom\ChildNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public string $wholeText;

	/**
	 * Breaks this node into two nodes at the specified offset
	 * @link http://www.php.net/manual/en/dom-text.splittext.php
	 * @param int $offset 
	 * @return \Dom\Text The new node of the same type, which contains all the content at and after the 
	 * offset.
	 */
	public function splitText (int $offset): \Dom\Text {}

	/**
	 * Extracts a range of data from the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.substringdata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return string The specified substring. If the sum of offset 
	 * and count exceeds the length, then all UTF-8 codepoints
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count): string {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/dom-characterdata.appenddata.php
	 * @param string $data 
	 * @return void Always returns true.
	 */
	public function appendData (string $data): void {}

	/**
	 * Insert a string at the specified UTF-8 codepoint offset
	 * @link http://www.php.net/manual/en/dom-characterdata.insertdata.php
	 * @param int $offset 
	 * @param string $data 
	 * @return void Returns true on success or false on failure.
	 */
	public function insertData (int $offset, string $data): void {}

	/**
	 * Remove a range of characters from the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.deletedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return void Returns true on success or false on failure.
	 */
	public function deleteData (int $offset, int $count): void {}

	/**
	 * Replace a substring within the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.replacedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @param string $data 
	 * @return void Returns true on success or false on failure.
	 */
	public function replaceData (int $offset, int $count, string $data): void {}

	/**
	 * Removes the character data node
	 * @link http://www.php.net/manual/en/dom-characterdata.remove.php
	 * @return void No value is returned.
	 */
	public function remove (): void {}

	/**
	 * Adds nodes before the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.before.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	public function before (\Dom\Node|string ...$nodes): void {}

	/**
	 * Adds nodes after the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.after.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	public function after (\Dom\Node|string ...$nodes): void {}

	/**
	 * Replaces the character data with new nodes
	 * @link http://www.php.net/manual/en/dom-characterdata.replacewith.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	public function replaceWith (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param array $options [optional]
	 */
	public function getRootNode (array $options = array (
)): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize (): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isEqualNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isSameNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $other
	 */
	public function compareDocumentPosition (\Dom\Node $other): int {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $other
	 */
	public function contains (?\Dom\Node $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function lookupPrefix (?string $namespace = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function isDefaultNamespace (?string $namespace = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node|null $child
	 */
	public function insertBefore (\Dom\Node $node, ?\Dom\Node $child = null): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function appendChild (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node $child
	 */
	public function replaceChild (\Dom\Node $node, \Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $child
	 */
	public function removeChild (\Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath (): string {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

}


}


namespace {

class DOMComment extends DOMCharacterData implements DOMChildNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	/**
	 * Creates a new DOMComment object
	 * @link http://www.php.net/manual/en/domcomment.construct.php
	 * @param string $data [optional] 
	 * @return string 
	 */
	public function __construct (string $data = '""'): string {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/domcharacterdata.appenddata.php
	 * @param string $data 
	 * @return true Always returns true.
	 */
	public function appendData (string $data): true {}

	/**
	 * Extracts a range of data from the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.substringdata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return string|false The specified substring. If the sum of offset 
	 * and count exceeds the length, then all UTF-8 codepoints
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count): string|false {}

	/**
	 * Insert a string at the specified UTF-8 codepoint offset
	 * @link http://www.php.net/manual/en/domcharacterdata.insertdata.php
	 * @param int $offset 
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function insertData (int $offset, string $data): bool {}

	/**
	 * Remove a range of characters from the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.deletedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return bool Returns true on success or false on failure.
	 */
	public function deleteData (int $offset, int $count): bool {}

	/**
	 * Replace a substring within the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.replacedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function replaceData (int $offset, int $count, string $data): bool {}

	/**
	 * Replaces the character data with new nodes
	 * @link http://www.php.net/manual/en/domcharacterdata.replacewith.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function replaceWith (DOMNode|string ...$nodes): void {}

	/**
	 * Removes the character data node
	 * @link http://www.php.net/manual/en/domcharacterdata.remove.php
	 * @return void No value is returned.
	 */
	public function remove (): void {}

	/**
	 * Adds nodes before the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.before.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function before (DOMNode|string ...$nodes): void {}

	/**
	 * Adds nodes after the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.after.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function after (DOMNode|string ...$nodes): void {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node 
	 * @return DOMNode|false The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|false {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|false Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|false {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|false Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|false {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|false The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|false {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Returns the line number where the node was defined at parse time.
	 * If the node was created manually, the return value will be 0.
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
	 * @return DOMNode|false The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|false {}

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
	 * Checks that both nodes are equal
	 * @link http://www.php.net/manual/en/domnode.isequalnode.php
	 * @param DOMNode|null $otherNode 
	 * @return bool Returns true if both nodes are equal, false otherwise.
	 */
	public function isEqualNode (?DOMNode $otherNode): bool {}

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
	 * @param string|null $prefix 
	 * @return string|null Returns the associated namespace URI or null if none is found.
	 */
	public function lookupNamespaceURI (?string $prefix): ?string {}

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
	 * @return DOMNode|false If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|false {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|false The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|false {}

	/**
	 * Checks if node contains other node
	 * @link http://www.php.net/manual/en/domnode.contains.php
	 * @param DOMNode|DOMNameSpaceNode|null $other 
	 * @return bool Returns true if node contains other node, false otherwise.
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other): bool {}

	/**
	 * Get root node
	 * @link http://www.php.net/manual/en/domnode.getrootnode.php
	 * @param array|null $options [optional] 
	 * @return DOMNode Returns the root node.
	 */
	public function getRootNode (?array $options = null): DOMNode {}

	/**
	 * Compares the position of two nodes
	 * @link http://www.php.net/manual/en/domnode.comparedocumentposition.php
	 * @param DOMNode $other The node for which the position should be compared for, relative to this node.
	 * @return int A bitmask of the DOMNode::DOCUMENT_POSITION_&#42;
	 * constants.
	 */
	public function compareDocumentPosition (DOMNode $other): int {}

	/**
	 * Forbids serialization unless serialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.sleep.php
	 * @return array The method always throws an exception.
	 */
	public function __sleep (): array {}

	/**
	 * Forbids unserialization unless unserialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.wakeup.php
	 * @return void The method always throws an exception.
	 */
	public function __wakeup (): void {}

}


}


namespace Dom {

class Comment extends \Dom\CharacterData implements \Dom\ChildNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	/**
	 * Extracts a range of data from the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.substringdata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return string The specified substring. If the sum of offset 
	 * and count exceeds the length, then all UTF-8 codepoints
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count): string {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/dom-characterdata.appenddata.php
	 * @param string $data 
	 * @return void Always returns true.
	 */
	public function appendData (string $data): void {}

	/**
	 * Insert a string at the specified UTF-8 codepoint offset
	 * @link http://www.php.net/manual/en/dom-characterdata.insertdata.php
	 * @param int $offset 
	 * @param string $data 
	 * @return void Returns true on success or false on failure.
	 */
	public function insertData (int $offset, string $data): void {}

	/**
	 * Remove a range of characters from the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.deletedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return void Returns true on success or false on failure.
	 */
	public function deleteData (int $offset, int $count): void {}

	/**
	 * Replace a substring within the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.replacedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @param string $data 
	 * @return void Returns true on success or false on failure.
	 */
	public function replaceData (int $offset, int $count, string $data): void {}

	/**
	 * Removes the character data node
	 * @link http://www.php.net/manual/en/dom-characterdata.remove.php
	 * @return void No value is returned.
	 */
	public function remove (): void {}

	/**
	 * Adds nodes before the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.before.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	public function before (\Dom\Node|string ...$nodes): void {}

	/**
	 * Adds nodes after the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.after.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	public function after (\Dom\Node|string ...$nodes): void {}

	/**
	 * Replaces the character data with new nodes
	 * @link http://www.php.net/manual/en/dom-characterdata.replacewith.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	public function replaceWith (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param array $options [optional]
	 */
	public function getRootNode (array $options = array (
)): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize (): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isEqualNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isSameNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $other
	 */
	public function compareDocumentPosition (\Dom\Node $other): int {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $other
	 */
	public function contains (?\Dom\Node $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function lookupPrefix (?string $namespace = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function isDefaultNamespace (?string $namespace = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node|null $child
	 */
	public function insertBefore (\Dom\Node $node, ?\Dom\Node $child = null): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function appendChild (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node $child
	 */
	public function replaceChild (\Dom\Node $node, \Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $child
	 */
	public function removeChild (\Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath (): string {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

}


}


namespace {

class DOMCdataSection extends DOMText implements DOMChildNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


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
	 * @return DOMText|false The new node of the same type, which contains all the content at and after the 
	 * offset.
	 */
	public function splitText (int $offset): DOMText|false {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/domcharacterdata.appenddata.php
	 * @param string $data 
	 * @return true Always returns true.
	 */
	public function appendData (string $data): true {}

	/**
	 * Extracts a range of data from the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.substringdata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return string|false The specified substring. If the sum of offset 
	 * and count exceeds the length, then all UTF-8 codepoints
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count): string|false {}

	/**
	 * Insert a string at the specified UTF-8 codepoint offset
	 * @link http://www.php.net/manual/en/domcharacterdata.insertdata.php
	 * @param int $offset 
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function insertData (int $offset, string $data): bool {}

	/**
	 * Remove a range of characters from the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.deletedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return bool Returns true on success or false on failure.
	 */
	public function deleteData (int $offset, int $count): bool {}

	/**
	 * Replace a substring within the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.replacedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function replaceData (int $offset, int $count, string $data): bool {}

	/**
	 * Replaces the character data with new nodes
	 * @link http://www.php.net/manual/en/domcharacterdata.replacewith.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function replaceWith (DOMNode|string ...$nodes): void {}

	/**
	 * Removes the character data node
	 * @link http://www.php.net/manual/en/domcharacterdata.remove.php
	 * @return void No value is returned.
	 */
	public function remove (): void {}

	/**
	 * Adds nodes before the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.before.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function before (DOMNode|string ...$nodes): void {}

	/**
	 * Adds nodes after the character data
	 * @link http://www.php.net/manual/en/domcharacterdata.after.php
	 * @param DOMNode|string $nodes 
	 * @return void No value is returned.
	 */
	public function after (DOMNode|string ...$nodes): void {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node 
	 * @return DOMNode|false The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|false {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|false Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|false {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|false Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|false {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|false The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|false {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Returns the line number where the node was defined at parse time.
	 * If the node was created manually, the return value will be 0.
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
	 * @return DOMNode|false The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|false {}

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
	 * Checks that both nodes are equal
	 * @link http://www.php.net/manual/en/domnode.isequalnode.php
	 * @param DOMNode|null $otherNode 
	 * @return bool Returns true if both nodes are equal, false otherwise.
	 */
	public function isEqualNode (?DOMNode $otherNode): bool {}

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
	 * @param string|null $prefix 
	 * @return string|null Returns the associated namespace URI or null if none is found.
	 */
	public function lookupNamespaceURI (?string $prefix): ?string {}

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
	 * @return DOMNode|false If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|false {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|false The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|false {}

	/**
	 * Checks if node contains other node
	 * @link http://www.php.net/manual/en/domnode.contains.php
	 * @param DOMNode|DOMNameSpaceNode|null $other 
	 * @return bool Returns true if node contains other node, false otherwise.
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other): bool {}

	/**
	 * Get root node
	 * @link http://www.php.net/manual/en/domnode.getrootnode.php
	 * @param array|null $options [optional] 
	 * @return DOMNode Returns the root node.
	 */
	public function getRootNode (?array $options = null): DOMNode {}

	/**
	 * Compares the position of two nodes
	 * @link http://www.php.net/manual/en/domnode.comparedocumentposition.php
	 * @param DOMNode $other The node for which the position should be compared for, relative to this node.
	 * @return int A bitmask of the DOMNode::DOCUMENT_POSITION_&#42;
	 * constants.
	 */
	public function compareDocumentPosition (DOMNode $other): int {}

	/**
	 * Forbids serialization unless serialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.sleep.php
	 * @return array The method always throws an exception.
	 */
	public function __sleep (): array {}

	/**
	 * Forbids unserialization unless unserialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.wakeup.php
	 * @return void The method always throws an exception.
	 */
	public function __wakeup (): void {}

}


}


namespace Dom {

class CDATASection extends \Dom\Text implements \Dom\ChildNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	/**
	 * Breaks this node into two nodes at the specified offset
	 * @link http://www.php.net/manual/en/dom-text.splittext.php
	 * @param int $offset 
	 * @return \Dom\Text The new node of the same type, which contains all the content at and after the 
	 * offset.
	 */
	public function splitText (int $offset): \Dom\Text {}

	/**
	 * Extracts a range of data from the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.substringdata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return string The specified substring. If the sum of offset 
	 * and count exceeds the length, then all UTF-8 codepoints
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count): string {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/dom-characterdata.appenddata.php
	 * @param string $data 
	 * @return void Always returns true.
	 */
	public function appendData (string $data): void {}

	/**
	 * Insert a string at the specified UTF-8 codepoint offset
	 * @link http://www.php.net/manual/en/dom-characterdata.insertdata.php
	 * @param int $offset 
	 * @param string $data 
	 * @return void Returns true on success or false on failure.
	 */
	public function insertData (int $offset, string $data): void {}

	/**
	 * Remove a range of characters from the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.deletedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return void Returns true on success or false on failure.
	 */
	public function deleteData (int $offset, int $count): void {}

	/**
	 * Replace a substring within the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.replacedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @param string $data 
	 * @return void Returns true on success or false on failure.
	 */
	public function replaceData (int $offset, int $count, string $data): void {}

	/**
	 * Removes the character data node
	 * @link http://www.php.net/manual/en/dom-characterdata.remove.php
	 * @return void No value is returned.
	 */
	public function remove (): void {}

	/**
	 * Adds nodes before the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.before.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	public function before (\Dom\Node|string ...$nodes): void {}

	/**
	 * Adds nodes after the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.after.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	public function after (\Dom\Node|string ...$nodes): void {}

	/**
	 * Replaces the character data with new nodes
	 * @link http://www.php.net/manual/en/dom-characterdata.replacewith.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	public function replaceWith (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param array $options [optional]
	 */
	public function getRootNode (array $options = array (
)): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize (): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isEqualNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isSameNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $other
	 */
	public function compareDocumentPosition (\Dom\Node $other): int {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $other
	 */
	public function contains (?\Dom\Node $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function lookupPrefix (?string $namespace = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function isDefaultNamespace (?string $namespace = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node|null $child
	 */
	public function insertBefore (\Dom\Node $node, ?\Dom\Node $child = null): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function appendChild (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node $child
	 */
	public function replaceChild (\Dom\Node $node, \Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $child
	 */
	public function removeChild (\Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath (): string {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

}


}


namespace {

class DOMDocumentType extends DOMNode  {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public string $name;

	public DOMNamedNodeMap $entities;

	public DOMNamedNodeMap $notations;

	public string $publicId;

	public string $systemId;

	public ?string $internalSubset;

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node 
	 * @return DOMNode|false The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|false {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|false Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|false {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|false Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|false {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|false The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|false {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Returns the line number where the node was defined at parse time.
	 * If the node was created manually, the return value will be 0.
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
	 * @return DOMNode|false The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|false {}

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
	 * Checks that both nodes are equal
	 * @link http://www.php.net/manual/en/domnode.isequalnode.php
	 * @param DOMNode|null $otherNode 
	 * @return bool Returns true if both nodes are equal, false otherwise.
	 */
	public function isEqualNode (?DOMNode $otherNode): bool {}

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
	 * @param string|null $prefix 
	 * @return string|null Returns the associated namespace URI or null if none is found.
	 */
	public function lookupNamespaceURI (?string $prefix): ?string {}

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
	 * @return DOMNode|false If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|false {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|false The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|false {}

	/**
	 * Checks if node contains other node
	 * @link http://www.php.net/manual/en/domnode.contains.php
	 * @param DOMNode|DOMNameSpaceNode|null $other 
	 * @return bool Returns true if node contains other node, false otherwise.
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other): bool {}

	/**
	 * Get root node
	 * @link http://www.php.net/manual/en/domnode.getrootnode.php
	 * @param array|null $options [optional] 
	 * @return DOMNode Returns the root node.
	 */
	public function getRootNode (?array $options = null): DOMNode {}

	/**
	 * Compares the position of two nodes
	 * @link http://www.php.net/manual/en/domnode.comparedocumentposition.php
	 * @param DOMNode $other The node for which the position should be compared for, relative to this node.
	 * @return int A bitmask of the DOMNode::DOCUMENT_POSITION_&#42;
	 * constants.
	 */
	public function compareDocumentPosition (DOMNode $other): int {}

	/**
	 * Forbids serialization unless serialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.sleep.php
	 * @return array The method always throws an exception.
	 */
	public function __sleep (): array {}

	/**
	 * Forbids unserialization unless unserialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.wakeup.php
	 * @return void The method always throws an exception.
	 */
	public function __wakeup (): void {}

}


}


namespace Dom {

class DocumentType extends \Dom\Node implements \Dom\ChildNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public string $name;

	public \Dom\DtdNamedNodeMap $entities;

	public \Dom\DtdNamedNodeMap $notations;

	public string $publicId;

	public string $systemId;

	public ?string $internalSubset;

	/**
	 * {@inheritdoc}
	 */
	public function remove (): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function before (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function after (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|string $nodes [optional]
	 */
	public function replaceWith (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param array $options [optional]
	 */
	public function getRootNode (array $options = array (
)): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize (): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isEqualNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isSameNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $other
	 */
	public function compareDocumentPosition (\Dom\Node $other): int {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $other
	 */
	public function contains (?\Dom\Node $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function lookupPrefix (?string $namespace = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function isDefaultNamespace (?string $namespace = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node|null $child
	 */
	public function insertBefore (\Dom\Node $node, ?\Dom\Node $child = null): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function appendChild (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node $child
	 */
	public function replaceChild (\Dom\Node $node, \Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $child
	 */
	public function removeChild (\Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath (): string {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

}


}


namespace {

class DOMNotation extends DOMNode  {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public string $publicId;

	public string $systemId;

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node 
	 * @return DOMNode|false The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|false {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|false Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|false {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|false Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|false {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|false The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|false {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Returns the line number where the node was defined at parse time.
	 * If the node was created manually, the return value will be 0.
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
	 * @return DOMNode|false The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|false {}

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
	 * Checks that both nodes are equal
	 * @link http://www.php.net/manual/en/domnode.isequalnode.php
	 * @param DOMNode|null $otherNode 
	 * @return bool Returns true if both nodes are equal, false otherwise.
	 */
	public function isEqualNode (?DOMNode $otherNode): bool {}

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
	 * @param string|null $prefix 
	 * @return string|null Returns the associated namespace URI or null if none is found.
	 */
	public function lookupNamespaceURI (?string $prefix): ?string {}

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
	 * @return DOMNode|false If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|false {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|false The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|false {}

	/**
	 * Checks if node contains other node
	 * @link http://www.php.net/manual/en/domnode.contains.php
	 * @param DOMNode|DOMNameSpaceNode|null $other 
	 * @return bool Returns true if node contains other node, false otherwise.
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other): bool {}

	/**
	 * Get root node
	 * @link http://www.php.net/manual/en/domnode.getrootnode.php
	 * @param array|null $options [optional] 
	 * @return DOMNode Returns the root node.
	 */
	public function getRootNode (?array $options = null): DOMNode {}

	/**
	 * Compares the position of two nodes
	 * @link http://www.php.net/manual/en/domnode.comparedocumentposition.php
	 * @param DOMNode $other The node for which the position should be compared for, relative to this node.
	 * @return int A bitmask of the DOMNode::DOCUMENT_POSITION_&#42;
	 * constants.
	 */
	public function compareDocumentPosition (DOMNode $other): int {}

	/**
	 * Forbids serialization unless serialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.sleep.php
	 * @return array The method always throws an exception.
	 */
	public function __sleep (): array {}

	/**
	 * Forbids unserialization unless unserialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.wakeup.php
	 * @return void The method always throws an exception.
	 */
	public function __wakeup (): void {}

}


}


namespace Dom {

class Notation extends \Dom\Node  {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public string $publicId;

	public string $systemId;

	/**
	 * {@inheritdoc}
	 * @param array $options [optional]
	 */
	public function getRootNode (array $options = array (
)): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize (): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isEqualNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isSameNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $other
	 */
	public function compareDocumentPosition (\Dom\Node $other): int {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $other
	 */
	public function contains (?\Dom\Node $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function lookupPrefix (?string $namespace = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function isDefaultNamespace (?string $namespace = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node|null $child
	 */
	public function insertBefore (\Dom\Node $node, ?\Dom\Node $child = null): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function appendChild (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node $child
	 */
	public function replaceChild (\Dom\Node $node, \Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $child
	 */
	public function removeChild (\Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath (): string {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

}


}


namespace {

class DOMEntity extends DOMNode  {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public ?string $publicId;

	public ?string $systemId;

	public ?string $notationName;

	public ?string $actualEncoding;

	public ?string $encoding;

	public ?string $version;

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node 
	 * @return DOMNode|false The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|false {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|false Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|false {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|false Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|false {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|false The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|false {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Returns the line number where the node was defined at parse time.
	 * If the node was created manually, the return value will be 0.
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
	 * @return DOMNode|false The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|false {}

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
	 * Checks that both nodes are equal
	 * @link http://www.php.net/manual/en/domnode.isequalnode.php
	 * @param DOMNode|null $otherNode 
	 * @return bool Returns true if both nodes are equal, false otherwise.
	 */
	public function isEqualNode (?DOMNode $otherNode): bool {}

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
	 * @param string|null $prefix 
	 * @return string|null Returns the associated namespace URI or null if none is found.
	 */
	public function lookupNamespaceURI (?string $prefix): ?string {}

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
	 * @return DOMNode|false If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|false {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|false The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|false {}

	/**
	 * Checks if node contains other node
	 * @link http://www.php.net/manual/en/domnode.contains.php
	 * @param DOMNode|DOMNameSpaceNode|null $other 
	 * @return bool Returns true if node contains other node, false otherwise.
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other): bool {}

	/**
	 * Get root node
	 * @link http://www.php.net/manual/en/domnode.getrootnode.php
	 * @param array|null $options [optional] 
	 * @return DOMNode Returns the root node.
	 */
	public function getRootNode (?array $options = null): DOMNode {}

	/**
	 * Compares the position of two nodes
	 * @link http://www.php.net/manual/en/domnode.comparedocumentposition.php
	 * @param DOMNode $other The node for which the position should be compared for, relative to this node.
	 * @return int A bitmask of the DOMNode::DOCUMENT_POSITION_&#42;
	 * constants.
	 */
	public function compareDocumentPosition (DOMNode $other): int {}

	/**
	 * Forbids serialization unless serialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.sleep.php
	 * @return array The method always throws an exception.
	 */
	public function __sleep (): array {}

	/**
	 * Forbids unserialization unless unserialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.wakeup.php
	 * @return void The method always throws an exception.
	 */
	public function __wakeup (): void {}

}


}


namespace Dom {

class Entity extends \Dom\Node  {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public ?string $publicId;

	public ?string $systemId;

	public ?string $notationName;

	/**
	 * {@inheritdoc}
	 * @param array $options [optional]
	 */
	public function getRootNode (array $options = array (
)): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize (): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isEqualNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isSameNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $other
	 */
	public function compareDocumentPosition (\Dom\Node $other): int {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $other
	 */
	public function contains (?\Dom\Node $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function lookupPrefix (?string $namespace = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function isDefaultNamespace (?string $namespace = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node|null $child
	 */
	public function insertBefore (\Dom\Node $node, ?\Dom\Node $child = null): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function appendChild (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node $child
	 */
	public function replaceChild (\Dom\Node $node, \Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $child
	 */
	public function removeChild (\Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath (): string {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

}


}


namespace {

class DOMEntityReference extends DOMNode  {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


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
	 * @return DOMNode|false The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|false {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|false Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|false {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|false Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|false {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|false The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|false {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Returns the line number where the node was defined at parse time.
	 * If the node was created manually, the return value will be 0.
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
	 * @return DOMNode|false The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|false {}

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
	 * Checks that both nodes are equal
	 * @link http://www.php.net/manual/en/domnode.isequalnode.php
	 * @param DOMNode|null $otherNode 
	 * @return bool Returns true if both nodes are equal, false otherwise.
	 */
	public function isEqualNode (?DOMNode $otherNode): bool {}

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
	 * @param string|null $prefix 
	 * @return string|null Returns the associated namespace URI or null if none is found.
	 */
	public function lookupNamespaceURI (?string $prefix): ?string {}

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
	 * @return DOMNode|false If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|false {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|false The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|false {}

	/**
	 * Checks if node contains other node
	 * @link http://www.php.net/manual/en/domnode.contains.php
	 * @param DOMNode|DOMNameSpaceNode|null $other 
	 * @return bool Returns true if node contains other node, false otherwise.
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other): bool {}

	/**
	 * Get root node
	 * @link http://www.php.net/manual/en/domnode.getrootnode.php
	 * @param array|null $options [optional] 
	 * @return DOMNode Returns the root node.
	 */
	public function getRootNode (?array $options = null): DOMNode {}

	/**
	 * Compares the position of two nodes
	 * @link http://www.php.net/manual/en/domnode.comparedocumentposition.php
	 * @param DOMNode $other The node for which the position should be compared for, relative to this node.
	 * @return int A bitmask of the DOMNode::DOCUMENT_POSITION_&#42;
	 * constants.
	 */
	public function compareDocumentPosition (DOMNode $other): int {}

	/**
	 * Forbids serialization unless serialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.sleep.php
	 * @return array The method always throws an exception.
	 */
	public function __sleep (): array {}

	/**
	 * Forbids unserialization unless unserialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.wakeup.php
	 * @return void The method always throws an exception.
	 */
	public function __wakeup (): void {}

}


}


namespace Dom {

class EntityReference extends \Dom\Node  {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	/**
	 * {@inheritdoc}
	 * @param array $options [optional]
	 */
	public function getRootNode (array $options = array (
)): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize (): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isEqualNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isSameNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $other
	 */
	public function compareDocumentPosition (\Dom\Node $other): int {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $other
	 */
	public function contains (?\Dom\Node $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function lookupPrefix (?string $namespace = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function isDefaultNamespace (?string $namespace = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node|null $child
	 */
	public function insertBefore (\Dom\Node $node, ?\Dom\Node $child = null): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function appendChild (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node $child
	 */
	public function replaceChild (\Dom\Node $node, \Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $child
	 */
	public function removeChild (\Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath (): string {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

}


}


namespace {

class DOMProcessingInstruction extends DOMNode  {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public string $target;

	public string $data;

	/**
	 * Creates a new DOMProcessingInstruction object
	 * @link http://www.php.net/manual/en/domprocessinginstruction.construct.php
	 * @param string $name 
	 * @param string $value [optional] 
	 * @return string 
	 */
	public function __construct (string $name, string $value = '""'): string {}

	/**
	 * Adds new child at the end of the children
	 * @link http://www.php.net/manual/en/domnode.appendchild.php
	 * @param DOMNode $node 
	 * @return DOMNode|false The node added or false on error.
	 */
	public function appendChild (DOMNode $node): DOMNode|false {}

	/**
	 * Canonicalize nodes to a string
	 * @link http://www.php.net/manual/en/domnode.c14n.php
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return string|false Returns canonicalized nodes as a string or false on failure
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): string|false {}

	/**
	 * Canonicalize nodes to a file
	 * @link http://www.php.net/manual/en/domnode.c14nfile.php
	 * @param string $uri Path to write the output to.
	 * @param bool $exclusive [optional] Enable exclusive parsing of only the nodes matched by the provided
	 * xpath or namespace prefixes.
	 * @param bool $withComments [optional] Retain comments in output.
	 * @param array|null $xpath [optional] An array of XPaths to filter the nodes by.
	 * Each entry in this array is an associative array with:
	 * <p>
	 * <br>
	 * A required query key containing the XPath expression as a string.
	 * <br>
	 * An optional namespaces key containing an array that maps namespace prefixes (keys) to namespace URIs (values).
	 * </p>
	 * @param array|null $nsPrefixes [optional] An array of namespace prefixes to filter the nodes by.
	 * @return int|false Number of bytes written or false on failure
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = null, ?array $nsPrefixes = null): int|false {}

	/**
	 * Clones a node
	 * @link http://www.php.net/manual/en/domnode.clonenode.php
	 * @param bool $deep [optional] 
	 * @return DOMNode|false The cloned node.
	 */
	public function cloneNode (bool $deep = false): DOMNode|false {}

	/**
	 * Get line number for a node
	 * @link http://www.php.net/manual/en/domnode.getlineno.php
	 * @return int Returns the line number where the node was defined at parse time.
	 * If the node was created manually, the return value will be 0.
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
	 * @return DOMNode|false The inserted node or false on error.
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = null): DOMNode|false {}

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
	 * Checks that both nodes are equal
	 * @link http://www.php.net/manual/en/domnode.isequalnode.php
	 * @param DOMNode|null $otherNode 
	 * @return bool Returns true if both nodes are equal, false otherwise.
	 */
	public function isEqualNode (?DOMNode $otherNode): bool {}

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
	 * @param string|null $prefix 
	 * @return string|null Returns the associated namespace URI or null if none is found.
	 */
	public function lookupNamespaceURI (?string $prefix): ?string {}

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
	 * @return DOMNode|false If the child could be removed the function returns the old child or false on error.
	 */
	public function removeChild (DOMNode $child): DOMNode|false {}

	/**
	 * Replaces a child
	 * @link http://www.php.net/manual/en/domnode.replacechild.php
	 * @param DOMNode $node 
	 * @param DOMNode $child 
	 * @return DOMNode|false The old node or false if an error occur.
	 */
	public function replaceChild (DOMNode $node, DOMNode $child): DOMNode|false {}

	/**
	 * Checks if node contains other node
	 * @link http://www.php.net/manual/en/domnode.contains.php
	 * @param DOMNode|DOMNameSpaceNode|null $other 
	 * @return bool Returns true if node contains other node, false otherwise.
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other): bool {}

	/**
	 * Get root node
	 * @link http://www.php.net/manual/en/domnode.getrootnode.php
	 * @param array|null $options [optional] 
	 * @return DOMNode Returns the root node.
	 */
	public function getRootNode (?array $options = null): DOMNode {}

	/**
	 * Compares the position of two nodes
	 * @link http://www.php.net/manual/en/domnode.comparedocumentposition.php
	 * @param DOMNode $other The node for which the position should be compared for, relative to this node.
	 * @return int A bitmask of the DOMNode::DOCUMENT_POSITION_&#42;
	 * constants.
	 */
	public function compareDocumentPosition (DOMNode $other): int {}

	/**
	 * Forbids serialization unless serialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.sleep.php
	 * @return array The method always throws an exception.
	 */
	public function __sleep (): array {}

	/**
	 * Forbids unserialization unless unserialization methods are implemented in a subclass
	 * @link http://www.php.net/manual/en/domnode.wakeup.php
	 * @return void The method always throws an exception.
	 */
	public function __wakeup (): void {}

}


}


namespace Dom {

class ProcessingInstruction extends \Dom\CharacterData implements \Dom\ChildNode {
	const DOCUMENT_POSITION_DISCONNECTED = 1;
	const DOCUMENT_POSITION_PRECEDING = 2;
	const DOCUMENT_POSITION_FOLLOWING = 4;
	const DOCUMENT_POSITION_CONTAINS = 8;
	const DOCUMENT_POSITION_CONTAINED_BY = 16;
	const DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 32;


	public string $target;

	/**
	 * Extracts a range of data from the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.substringdata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return string The specified substring. If the sum of offset 
	 * and count exceeds the length, then all UTF-8 codepoints
	 * to the end of the data are returned.
	 */
	public function substringData (int $offset, int $count): string {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://www.php.net/manual/en/dom-characterdata.appenddata.php
	 * @param string $data 
	 * @return void Always returns true.
	 */
	public function appendData (string $data): void {}

	/**
	 * Insert a string at the specified UTF-8 codepoint offset
	 * @link http://www.php.net/manual/en/dom-characterdata.insertdata.php
	 * @param int $offset 
	 * @param string $data 
	 * @return void Returns true on success or false on failure.
	 */
	public function insertData (int $offset, string $data): void {}

	/**
	 * Remove a range of characters from the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.deletedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @return void Returns true on success or false on failure.
	 */
	public function deleteData (int $offset, int $count): void {}

	/**
	 * Replace a substring within the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.replacedata.php
	 * @param int $offset 
	 * @param int $count 
	 * @param string $data 
	 * @return void Returns true on success or false on failure.
	 */
	public function replaceData (int $offset, int $count, string $data): void {}

	/**
	 * Removes the character data node
	 * @link http://www.php.net/manual/en/dom-characterdata.remove.php
	 * @return void No value is returned.
	 */
	public function remove (): void {}

	/**
	 * Adds nodes before the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.before.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	public function before (\Dom\Node|string ...$nodes): void {}

	/**
	 * Adds nodes after the character data
	 * @link http://www.php.net/manual/en/dom-characterdata.after.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	public function after (\Dom\Node|string ...$nodes): void {}

	/**
	 * Replaces the character data with new nodes
	 * @link http://www.php.net/manual/en/dom-characterdata.replacewith.php
	 * @param \Dom\Node|string $nodes 
	 * @return void No value is returned.
	 */
	public function replaceWith (\Dom\Node|string ...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param array $options [optional]
	 */
	public function getRootNode (array $options = array (
)): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize (): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isEqualNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $otherNode
	 */
	public function isSameNode (?\Dom\Node $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $other
	 */
	public function compareDocumentPosition (\Dom\Node $other): int {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node|null $other
	 */
	public function contains (?\Dom\Node $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function lookupPrefix (?string $namespace = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 */
	public function isDefaultNamespace (?string $namespace = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node|null $child
	 */
	public function insertBefore (\Dom\Node $node, ?\Dom\Node $child = null): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 */
	public function appendChild (\Dom\Node $node): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $node
	 * @param \Dom\Node $child
	 */
	public function replaceChild (\Dom\Node $node, \Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 * @param \Dom\Node $child
	 */
	public function removeChild (\Dom\Node $child): \Dom\Node {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath (): string {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL): int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

}


}


namespace {

class DOMXPath  {

	public DOMDocument $document;

	public bool $registerNodeNamespaces;

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

	/**
	 * Register a PHP functions as namespaced XPath function
	 * @link http://www.php.net/manual/en/domxpath.registerphpfunctionns.php
	 * @param string $namespaceURI The URI of the namespace.
	 * @param string $name The local function name inside the namespace.
	 * @param callable $callable The PHP function to call when the XPath function gets called within the XPath expression.
	 * When a node list is passed as parameter to the callback,
	 * they are arrays containing the matched DOM nodes.
	 * @return void No value is returned.
	 */
	public function registerPhpFunctionNS (string $namespaceURI, string $name, callable $callable): void {}

	/**
	 * Quotes a string for use in an XPath expression
	 * @link http://www.php.net/manual/en/domxpath.quote.php
	 * @param string $str 
	 * @return string Returns a quoted string to be used in an XPath expression.
	 */
	public static function quote (string $str): string {}

}


}


namespace Dom {

final class XPath  {

	public \Dom\Document $document;

	public bool $registerNodeNamespaces;

	/**
	 * {@inheritdoc}
	 * @param \Dom\Document $document
	 * @param bool $registerNodeNS [optional]
	 */
	public function __construct (\Dom\Document $document, bool $registerNodeNS = true) {}

	/**
	 * {@inheritdoc}
	 * @param string $expression
	 * @param \Dom\Node|null $contextNode [optional]
	 * @param bool $registerNodeNS [optional]
	 */
	public function evaluate (string $expression, ?\Dom\Node $contextNode = NULL, bool $registerNodeNS = true): \Dom\NodeList|string|float|bool|null {}

	/**
	 * {@inheritdoc}
	 * @param string $expression
	 * @param \Dom\Node|null $contextNode [optional]
	 * @param bool $registerNodeNS [optional]
	 */
	public function query (string $expression, ?\Dom\Node $contextNode = NULL, bool $registerNodeNS = true): \Dom\NodeList {}

	/**
	 * {@inheritdoc}
	 * @param string $prefix
	 * @param string $namespace
	 */
	public function registerNamespace (string $prefix, string $namespace): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string|null $restrict [optional]
	 */
	public function registerPhpFunctions (array|string|null $restrict = NULL): void {}

	/**
	 * {@inheritdoc}
	 * @param string $namespaceURI
	 * @param string $name
	 * @param callable $callable
	 */
	public function registerPhpFunctionNS (string $namespaceURI, string $name, callable $callable): void {}

	/**
	 * {@inheritdoc}
	 * @param string $str
	 */
	public static function quote (string $str): string {}

}

final class TokenList implements \IteratorAggregate, \Traversable, \Countable {

	public int $length;

	public string $value;

	/**
	 * {@inheritdoc}
	 */
	private function __construct () {}

	/**
	 * Returns a token from the list
	 * @link http://www.php.net/manual/en/dom-tokenlist.item.php
	 * @param int $index The token index.
	 * @return string|null Returns the token at index or null when the index
	 * is out of bounds.
	 */
	public function item (int $index): ?string {}

	/**
	 * Returns whether the list contains a given token
	 * @link http://www.php.net/manual/en/dom-tokenlist.contains.php
	 * @param string $token The token.
	 * @return bool Returns <p>token,
	 * false otherwise.
	 */
	public function contains (string $token): bool {}

	/**
	 * Adds the given tokens to the list
	 * @link http://www.php.net/manual/en/dom-tokenlist.add.php
	 * @param string $tokens The tokens to add.
	 * @return void No value is returned.
	 */
	public function add (string ...$tokens): void {}

	/**
	 * Removes the given tokens from the list
	 * @link http://www.php.net/manual/en/dom-tokenlist.remove.php
	 * @param string $tokens The tokens to remove.
	 * @return void No value is returned.
	 */
	public function remove (string ...$tokens): void {}

	/**
	 * Toggles the presence of a token in the list
	 * @link http://www.php.net/manual/en/dom-tokenlist.toggle.php
	 * @param string $token The token to toggle.
	 * @param bool|null $force [optional] If force is provided, setting it to true will
	 * add the token, and setting it to false will remove the token.
	 * @return bool Returns <p>false otherwise.
	 */
	public function toggle (string $token, ?bool $force = null): bool {}

	/**
	 * Replaces a token in the list with another one
	 * @link http://www.php.net/manual/en/dom-tokenlist.replace.php
	 * @param string $token The token to replace.
	 * @param string $newToken The new token.
	 * @return bool Returns true if token was in the list,
	 * false otherwise.
	 */
	public function replace (string $token, string $newToken): bool {}

	/**
	 * Returns whether the given token is supported
	 * @link http://www.php.net/manual/en/dom-tokenlist.supports.php
	 * @param string $token The token.
	 * @return bool Returns true on success or false on failure.
	 */
	public function supports (string $token): bool {}

	/**
	 * Returns the number of tokens in the list
	 * @link http://www.php.net/manual/en/dom-tokenlist.count.php
	 * @return int The number of tokens in the list.
	 */
	public function count (): int {}

	/**
	 * Returns an iterator over the token list
	 * @link http://www.php.net/manual/en/dom-tokenlist.getiterator.php
	 * @return \Iterator An iterator over the token list.
	 */
	public function getIterator (): \Iterator {}

}


}


namespace {

/**
 * Gets a DOMAttr or DOMElement object from a
 * SimpleXMLElement object
 * @link http://www.php.net/manual/en/function.dom-import-simplexml.php
 * @param object $node 
 * @return DOMAttr|DOMElement The DOMAttr or DOMElement.
 */
function dom_import_simplexml (object $node): DOMAttr|DOMElement {}


}


namespace dom {

/**
 * Gets a Dom\Attr or Dom\Element object from a
 * SimpleXMLElement object
 * @link http://www.php.net/manual/en/function.dom-ns-import-simplexml.php
 * @param object $node 
 * @return \Dom\Attr|\Dom\Element The Dom\Attr or Dom\Element.
 */
function import_simplexml (object $node): \Dom\Attr|\Dom\Element {}


}


namespace {

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
define ('Dom\INDEX_SIZE_ERR', 1);
define ('Dom\STRING_SIZE_ERR', 2);
define ('Dom\HIERARCHY_REQUEST_ERR', 3);
define ('Dom\WRONG_DOCUMENT_ERR', 4);
define ('Dom\INVALID_CHARACTER_ERR', 5);
define ('Dom\NO_DATA_ALLOWED_ERR', 6);
define ('Dom\NO_MODIFICATION_ALLOWED_ERR', 7);
define ('Dom\NOT_FOUND_ERR', 8);
define ('Dom\NOT_SUPPORTED_ERR', 9);
define ('Dom\INUSE_ATTRIBUTE_ERR', 10);
define ('Dom\INVALID_STATE_ERR', 11);
define ('Dom\SYNTAX_ERR', 12);
define ('Dom\INVALID_MODIFICATION_ERR', 13);
define ('Dom\NAMESPACE_ERR', 14);
define ('Dom\VALIDATION_ERR', 16);
define ('Dom\HTML_NO_DEFAULT_NS', 2147483648);


}

// End of dom v.20031129
