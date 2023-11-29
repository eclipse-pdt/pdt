<?php

// Start of dom v.20031129

final class DOMException extends Exception implements Throwable, Stringable {

	public $code;

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

interface DOMParentNode  {

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	abstract public function append (...$nodes): void;

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	abstract public function prepend (...$nodes): void;

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	abstract public function replaceChildren (...$nodes): void;

}

interface DOMChildNode  {

	/**
	 * {@inheritdoc}
	 */
	abstract public function remove (): void;

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	abstract public function before (...$nodes): void;

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	abstract public function after (...$nodes): void;

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	abstract public function replaceWith (...$nodes): void;

}

class DOMImplementation  {

	/**
	 * {@inheritdoc}
	 * @param string $feature
	 * @param string $version
	 */
	public function getFeature (string $feature, string $version) {}

	/**
	 * {@inheritdoc}
	 * @param string $feature
	 * @param string $version
	 */
	public function hasFeature (string $feature, string $version) {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 * @param string $publicId [optional]
	 * @param string $systemId [optional]
	 */
	public function createDocumentType (string $qualifiedName, string $publicId = '', string $systemId = '') {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace [optional]
	 * @param string $qualifiedName [optional]
	 * @param DOMDocumentType|null $doctype [optional]
	 */
	public function createDocument (?string $namespace = NULL, string $qualifiedName = '', ?DOMDocumentType $doctype = NULL) {}

}

class DOMNode  {

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
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasAttributes () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode|null $child [optional]
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $otherNode
	 */
	public function isSameNode (DOMNode $otherNode) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|null $otherNode
	 */
	public function isEqualNode (?DOMNode $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $feature
	 * @param string $version
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $child
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode $child
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|DOMNameSpaceNode|null $other
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	public function getRootNode (?array $options = NULL): DOMNode {}

}

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
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

}

class DOMDocumentFragment extends DOMNode implements DOMParentNode {

	public ?DOMElement $firstElementChild;

	public ?DOMElement $lastElementChild;

	public int $childElementCount;

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function appendXML (string $data) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function append (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function prepend (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function replaceChildren (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasAttributes () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode|null $child [optional]
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $otherNode
	 */
	public function isSameNode (DOMNode $otherNode) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|null $otherNode
	 */
	public function isEqualNode (?DOMNode $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $feature
	 * @param string $version
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $child
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode $child
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|DOMNameSpaceNode|null $other
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	public function getRootNode (?array $options = NULL): DOMNode {}

}

class DOMDocument extends DOMNode implements DOMParentNode {

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
	 * {@inheritdoc}
	 * @param string $version [optional]
	 * @param string $encoding [optional]
	 */
	public function __construct (string $version = '1.0', string $encoding = '') {}

	/**
	 * {@inheritdoc}
	 * @param string $localName
	 */
	public function createAttribute (string $localName) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $qualifiedName
	 */
	public function createAttributeNS (?string $namespace = null, string $qualifiedName) {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function createCDATASection (string $data) {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function createComment (string $data) {}

	/**
	 * {@inheritdoc}
	 */
	public function createDocumentFragment () {}

	/**
	 * {@inheritdoc}
	 * @param string $localName
	 * @param string $value [optional]
	 */
	public function createElement (string $localName, string $value = '') {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $qualifiedName
	 * @param string $value [optional]
	 */
	public function createElementNS (?string $namespace = null, string $qualifiedName, string $value = '') {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function createEntityReference (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param string $target
	 * @param string $data [optional]
	 */
	public function createProcessingInstruction (string $target, string $data = '') {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function createTextNode (string $data) {}

	/**
	 * {@inheritdoc}
	 * @param string $elementId
	 */
	public function getElementById (string $elementId) {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function getElementsByTagName (string $qualifiedName) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function getElementsByTagNameNS (?string $namespace = null, string $localName) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param bool $deep [optional]
	 */
	public function importNode (DOMNode $node, bool $deep = false) {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param int $options [optional]
	 */
	public function load (string $filename, int $options = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $source
	 * @param int $options [optional]
	 */
	public function loadXML (string $source, int $options = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function normalizeDocument () {}

	/**
	 * {@inheritdoc}
	 * @param string $baseClass
	 * @param string|null $extendedClass
	 */
	public function registerNodeClass (string $baseClass, ?string $extendedClass = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param int $options [optional]
	 */
	public function save (string $filename, int $options = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $source
	 * @param int $options [optional]
	 */
	public function loadHTML (string $source, int $options = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param int $options [optional]
	 */
	public function loadHTMLFile (string $filename, int $options = 0) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|null $node [optional]
	 */
	public function saveHTML (?DOMNode $node = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 */
	public function saveHTMLFile (string $filename) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|null $node [optional]
	 * @param int $options [optional]
	 */
	public function saveXML (?DOMNode $node = NULL, int $options = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param int $flags [optional]
	 */
	public function schemaValidate (string $filename, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $source
	 * @param int $flags [optional]
	 */
	public function schemaValidateSource (string $source, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 */
	public function relaxNGValidate (string $filename) {}

	/**
	 * {@inheritdoc}
	 * @param string $source
	 */
	public function relaxNGValidateSource (string $source) {}

	/**
	 * {@inheritdoc}
	 */
	public function validate () {}

	/**
	 * {@inheritdoc}
	 * @param int $options [optional]
	 */
	public function xinclude (int $options = 0) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 */
	public function adoptNode (DOMNode $node) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function append (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function prepend (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function replaceChildren (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasAttributes () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode|null $child [optional]
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $otherNode
	 */
	public function isSameNode (DOMNode $otherNode) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|null $otherNode
	 */
	public function isEqualNode (?DOMNode $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $feature
	 * @param string $version
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $child
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode $child
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|DOMNameSpaceNode|null $other
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	public function getRootNode (?array $options = NULL): DOMNode {}

}

class DOMNodeList implements IteratorAggregate, Traversable, Countable {

	public int $length;

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): Iterator {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function item (int $index) {}

}

class DOMNamedNodeMap implements IteratorAggregate, Traversable, Countable {

	public int $length;

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function getNamedItem (string $qualifiedName) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function getNamedItemNS (?string $namespace = null, string $localName) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function item (int $index) {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): Iterator {}

}

class DOMCharacterData extends DOMNode implements DOMChildNode {

	public string $data;

	public int $length;

	public ?DOMElement $previousElementSibling;

	public ?DOMElement $nextElementSibling;

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function appendData (string $data) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 * @param int $count
	 */
	public function substringData (int $offset, int $count) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 * @param string $data
	 */
	public function insertData (int $offset, string $data) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 * @param int $count
	 */
	public function deleteData (int $offset, int $count) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 * @param int $count
	 * @param string $data
	 */
	public function replaceData (int $offset, int $count, string $data) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function replaceWith (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 */
	public function remove (): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function before (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function after (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasAttributes () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode|null $child [optional]
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $otherNode
	 */
	public function isSameNode (DOMNode $otherNode) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|null $otherNode
	 */
	public function isEqualNode (?DOMNode $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $feature
	 * @param string $version
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $child
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode $child
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|DOMNameSpaceNode|null $other
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	public function getRootNode (?array $options = NULL): DOMNode {}

}

class DOMAttr extends DOMNode  {

	public string $name;

	public bool $specified;

	public string $value;

	public ?DOMElement $ownerElement;

	public mixed $schemaTypeInfo;

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param string $value [optional]
	 */
	public function __construct (string $name, string $value = '') {}

	/**
	 * {@inheritdoc}
	 */
	public function isId () {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasAttributes () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode|null $child [optional]
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $otherNode
	 */
	public function isSameNode (DOMNode $otherNode) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|null $otherNode
	 */
	public function isEqualNode (?DOMNode $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $feature
	 * @param string $version
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $child
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode $child
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|DOMNameSpaceNode|null $other
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	public function getRootNode (?array $options = NULL): DOMNode {}

}

class DOMElement extends DOMNode implements DOMParentNode, DOMChildNode {

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
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 * @param string|null $value [optional]
	 * @param string $namespace [optional]
	 */
	public function __construct (string $qualifiedName, ?string $value = NULL, string $namespace = '') {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function getAttribute (string $qualifiedName) {}

	/**
	 * {@inheritdoc}
	 */
	public function getAttributeNames (): array {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function getAttributeNS (?string $namespace = null, string $localName) {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function getAttributeNode (string $qualifiedName) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function getAttributeNodeNS (?string $namespace = null, string $localName) {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function getElementsByTagName (string $qualifiedName) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function getElementsByTagNameNS (?string $namespace = null, string $localName) {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function hasAttribute (string $qualifiedName) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function hasAttributeNS (?string $namespace = null, string $localName) {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function removeAttribute (string $qualifiedName) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $localName
	 */
	public function removeAttributeNS (?string $namespace = null, string $localName) {}

	/**
	 * {@inheritdoc}
	 * @param DOMAttr $attr
	 */
	public function removeAttributeNode (DOMAttr $attr) {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 * @param string $value
	 */
	public function setAttribute (string $qualifiedName, string $value) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespace
	 * @param string $qualifiedName
	 * @param string $value
	 */
	public function setAttributeNS (?string $namespace = null, string $qualifiedName, string $value) {}

	/**
	 * {@inheritdoc}
	 * @param DOMAttr $attr
	 */
	public function setAttributeNode (DOMAttr $attr) {}

	/**
	 * {@inheritdoc}
	 * @param DOMAttr $attr
	 */
	public function setAttributeNodeNS (DOMAttr $attr) {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 * @param bool $isId
	 */
	public function setIdAttribute (string $qualifiedName, bool $isId) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 * @param string $qualifiedName
	 * @param bool $isId
	 */
	public function setIdAttributeNS (string $namespace, string $qualifiedName, bool $isId) {}

	/**
	 * {@inheritdoc}
	 * @param DOMAttr $attr
	 * @param bool $isId
	 */
	public function setIdAttributeNode (DOMAttr $attr, bool $isId) {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 * @param bool|null $force [optional]
	 */
	public function toggleAttribute (string $qualifiedName, ?bool $force = NULL): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function remove (): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function before (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function after (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function replaceWith (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function append (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function prepend (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function replaceChildren (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param string $where
	 * @param DOMElement $element
	 */
	public function insertAdjacentElement (string $where, DOMElement $element): ?DOMElement {}

	/**
	 * {@inheritdoc}
	 * @param string $where
	 * @param string $data
	 */
	public function insertAdjacentText (string $where, string $data): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasAttributes () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode|null $child [optional]
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $otherNode
	 */
	public function isSameNode (DOMNode $otherNode) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|null $otherNode
	 */
	public function isEqualNode (?DOMNode $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $feature
	 * @param string $version
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $child
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode $child
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|DOMNameSpaceNode|null $other
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	public function getRootNode (?array $options = NULL): DOMNode {}

}

class DOMText extends DOMCharacterData implements DOMChildNode {

	public string $wholeText;

	/**
	 * {@inheritdoc}
	 * @param string $data [optional]
	 */
	public function __construct (string $data = '') {}

	/**
	 * {@inheritdoc}
	 */
	public function isWhitespaceInElementContent () {}

	/**
	 * {@inheritdoc}
	 */
	public function isElementContentWhitespace () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function splitText (int $offset) {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function appendData (string $data) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 * @param int $count
	 */
	public function substringData (int $offset, int $count) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 * @param string $data
	 */
	public function insertData (int $offset, string $data) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 * @param int $count
	 */
	public function deleteData (int $offset, int $count) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 * @param int $count
	 * @param string $data
	 */
	public function replaceData (int $offset, int $count, string $data) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function replaceWith (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 */
	public function remove (): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function before (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function after (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasAttributes () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode|null $child [optional]
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $otherNode
	 */
	public function isSameNode (DOMNode $otherNode) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|null $otherNode
	 */
	public function isEqualNode (?DOMNode $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $feature
	 * @param string $version
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $child
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode $child
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|DOMNameSpaceNode|null $other
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	public function getRootNode (?array $options = NULL): DOMNode {}

}

class DOMComment extends DOMCharacterData implements DOMChildNode {

	/**
	 * {@inheritdoc}
	 * @param string $data [optional]
	 */
	public function __construct (string $data = '') {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function appendData (string $data) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 * @param int $count
	 */
	public function substringData (int $offset, int $count) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 * @param string $data
	 */
	public function insertData (int $offset, string $data) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 * @param int $count
	 */
	public function deleteData (int $offset, int $count) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 * @param int $count
	 * @param string $data
	 */
	public function replaceData (int $offset, int $count, string $data) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function replaceWith (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 */
	public function remove (): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function before (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function after (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasAttributes () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode|null $child [optional]
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $otherNode
	 */
	public function isSameNode (DOMNode $otherNode) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|null $otherNode
	 */
	public function isEqualNode (?DOMNode $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $feature
	 * @param string $version
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $child
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode $child
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|DOMNameSpaceNode|null $other
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	public function getRootNode (?array $options = NULL): DOMNode {}

}

class DOMCdataSection extends DOMText implements DOMChildNode {

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function __construct (string $data) {}

	/**
	 * {@inheritdoc}
	 */
	public function isWhitespaceInElementContent () {}

	/**
	 * {@inheritdoc}
	 */
	public function isElementContentWhitespace () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function splitText (int $offset) {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function appendData (string $data) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 * @param int $count
	 */
	public function substringData (int $offset, int $count) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 * @param string $data
	 */
	public function insertData (int $offset, string $data) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 * @param int $count
	 */
	public function deleteData (int $offset, int $count) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 * @param int $count
	 * @param string $data
	 */
	public function replaceData (int $offset, int $count, string $data) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function replaceWith (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 */
	public function remove (): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function before (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $nodes [optional]
	 */
	public function after (...$nodes): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasAttributes () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode|null $child [optional]
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $otherNode
	 */
	public function isSameNode (DOMNode $otherNode) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|null $otherNode
	 */
	public function isEqualNode (?DOMNode $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $feature
	 * @param string $version
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $child
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode $child
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|DOMNameSpaceNode|null $other
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	public function getRootNode (?array $options = NULL): DOMNode {}

}

class DOMDocumentType extends DOMNode  {

	public string $name;

	public DOMNamedNodeMap $entities;

	public DOMNamedNodeMap $notations;

	public string $publicId;

	public string $systemId;

	public ?string $internalSubset;

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasAttributes () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode|null $child [optional]
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $otherNode
	 */
	public function isSameNode (DOMNode $otherNode) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|null $otherNode
	 */
	public function isEqualNode (?DOMNode $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $feature
	 * @param string $version
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $child
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode $child
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|DOMNameSpaceNode|null $other
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	public function getRootNode (?array $options = NULL): DOMNode {}

}

class DOMNotation extends DOMNode  {

	public string $publicId;

	public string $systemId;

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasAttributes () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode|null $child [optional]
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $otherNode
	 */
	public function isSameNode (DOMNode $otherNode) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|null $otherNode
	 */
	public function isEqualNode (?DOMNode $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $feature
	 * @param string $version
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $child
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode $child
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|DOMNameSpaceNode|null $other
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	public function getRootNode (?array $options = NULL): DOMNode {}

}

class DOMEntity extends DOMNode  {

	public ?string $publicId;

	public ?string $systemId;

	public ?string $notationName;

	public ?string $actualEncoding;

	public ?string $encoding;

	public ?string $version;

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasAttributes () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode|null $child [optional]
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $otherNode
	 */
	public function isSameNode (DOMNode $otherNode) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|null $otherNode
	 */
	public function isEqualNode (?DOMNode $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $feature
	 * @param string $version
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $child
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode $child
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|DOMNameSpaceNode|null $other
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	public function getRootNode (?array $options = NULL): DOMNode {}

}

class DOMEntityReference extends DOMNode  {

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function __construct (string $name) {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasAttributes () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode|null $child [optional]
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $otherNode
	 */
	public function isSameNode (DOMNode $otherNode) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|null $otherNode
	 */
	public function isEqualNode (?DOMNode $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $feature
	 * @param string $version
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $child
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode $child
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|DOMNameSpaceNode|null $other
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	public function getRootNode (?array $options = NULL): DOMNode {}

}

class DOMProcessingInstruction extends DOMNode  {

	public string $target;

	public string $data;

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param string $value [optional]
	 */
	public function __construct (string $name, string $value = '') {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup (): void {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 */
	public function appendChild (DOMNode $node) {}

	/**
	 * {@inheritdoc}
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14N (bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param bool $exclusive [optional]
	 * @param bool $withComments [optional]
	 * @param array|null $xpath [optional]
	 * @param array|null $nsPrefixes [optional]
	 */
	public function C14NFile (string $uri, bool $exclusive = false, bool $withComments = false, ?array $xpath = NULL, ?array $nsPrefixes = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $deep [optional]
	 */
	public function cloneNode (bool $deep = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function getLineNo () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNodePath () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasAttributes () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildNodes () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode|null $child [optional]
	 */
	public function insertBefore (DOMNode $node, ?DOMNode $child = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function isDefaultNamespace (string $namespace) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $otherNode
	 */
	public function isSameNode (DOMNode $otherNode) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|null $otherNode
	 */
	public function isEqualNode (?DOMNode $otherNode = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $feature
	 * @param string $version
	 */
	public function isSupported (string $feature, string $version) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 */
	public function lookupNamespaceURI (?string $prefix = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 */
	public function lookupPrefix (string $namespace) {}

	/**
	 * {@inheritdoc}
	 */
	public function normalize () {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $child
	 */
	public function removeChild (DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode $node
	 * @param DOMNode $child
	 */
	public function replaceChild (DOMNode $node, DOMNode $child) {}

	/**
	 * {@inheritdoc}
	 * @param DOMNode|DOMNameSpaceNode|null $other
	 */
	public function contains (DOMNode|DOMNameSpaceNode|null $other = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	public function getRootNode (?array $options = NULL): DOMNode {}

}

class DOMXPath  {

	public DOMDocument $document;

	public bool $registerNodeNamespaces;

	/**
	 * {@inheritdoc}
	 * @param DOMDocument $document
	 * @param bool $registerNodeNS [optional]
	 */
	public function __construct (DOMDocument $document, bool $registerNodeNS = true) {}

	/**
	 * {@inheritdoc}
	 * @param string $expression
	 * @param DOMNode|null $contextNode [optional]
	 * @param bool $registerNodeNS [optional]
	 */
	public function evaluate (string $expression, ?DOMNode $contextNode = NULL, bool $registerNodeNS = true) {}

	/**
	 * {@inheritdoc}
	 * @param string $expression
	 * @param DOMNode|null $contextNode [optional]
	 * @param bool $registerNodeNS [optional]
	 */
	public function query (string $expression, ?DOMNode $contextNode = NULL, bool $registerNodeNS = true) {}

	/**
	 * {@inheritdoc}
	 * @param string $prefix
	 * @param string $namespace
	 */
	public function registerNamespace (string $prefix, string $namespace) {}

	/**
	 * {@inheritdoc}
	 * @param array|string|null $restrict [optional]
	 */
	public function registerPhpFunctions (array|string|null $restrict = NULL) {}

}

/**
 * {@inheritdoc}
 * @param object $node
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
