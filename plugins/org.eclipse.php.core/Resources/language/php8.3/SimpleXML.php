<?php

// Start of SimpleXML v.8.3.0

class SimpleXMLElement implements Stringable, Countable, RecursiveIterator, Traversable, Iterator {

	/**
	 * {@inheritdoc}
	 * @param string $expression
	 */
	public function xpath (string $expression) {}

	/**
	 * {@inheritdoc}
	 * @param string $prefix
	 * @param string $namespace
	 */
	public function registerXPathNamespace (string $prefix, string $namespace) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $filename [optional]
	 */
	public function asXML (?string $filename = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $filename [optional]
	 */
	public function saveXML (?string $filename = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $recursive [optional]
	 */
	public function getNamespaces (bool $recursive = false) {}

	/**
	 * {@inheritdoc}
	 * @param bool $recursive [optional]
	 * @param bool $fromRoot [optional]
	 */
	public function getDocNamespaces (bool $recursive = false, bool $fromRoot = true) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespaceOrPrefix [optional]
	 * @param bool $isPrefix [optional]
	 */
	public function children (?string $namespaceOrPrefix = NULL, bool $isPrefix = false) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespaceOrPrefix [optional]
	 * @param bool $isPrefix [optional]
	 */
	public function attributes (?string $namespaceOrPrefix = NULL, bool $isPrefix = false) {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 * @param int $options [optional]
	 * @param bool $dataIsURL [optional]
	 * @param string $namespaceOrPrefix [optional]
	 * @param bool $isPrefix [optional]
	 */
	public function __construct (string $data, int $options = 0, bool $dataIsURL = false, string $namespaceOrPrefix = '', bool $isPrefix = false) {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 * @param string|null $value [optional]
	 * @param string|null $namespace [optional]
	 */
	public function addChild (string $qualifiedName, ?string $value = NULL, ?string $namespace = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 * @param string $value
	 * @param string|null $namespace [optional]
	 */
	public function addAttribute (string $qualifiedName, string $value, ?string $namespace = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function getChildren () {}

}

class SimpleXMLIterator extends SimpleXMLElement implements Iterator, Traversable, RecursiveIterator, Countable, Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $expression
	 */
	public function xpath (string $expression) {}

	/**
	 * {@inheritdoc}
	 * @param string $prefix
	 * @param string $namespace
	 */
	public function registerXPathNamespace (string $prefix, string $namespace) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $filename [optional]
	 */
	public function asXML (?string $filename = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $filename [optional]
	 */
	public function saveXML (?string $filename = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $recursive [optional]
	 */
	public function getNamespaces (bool $recursive = false) {}

	/**
	 * {@inheritdoc}
	 * @param bool $recursive [optional]
	 * @param bool $fromRoot [optional]
	 */
	public function getDocNamespaces (bool $recursive = false, bool $fromRoot = true) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespaceOrPrefix [optional]
	 * @param bool $isPrefix [optional]
	 */
	public function children (?string $namespaceOrPrefix = NULL, bool $isPrefix = false) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $namespaceOrPrefix [optional]
	 * @param bool $isPrefix [optional]
	 */
	public function attributes (?string $namespaceOrPrefix = NULL, bool $isPrefix = false) {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 * @param int $options [optional]
	 * @param bool $dataIsURL [optional]
	 * @param string $namespaceOrPrefix [optional]
	 * @param bool $isPrefix [optional]
	 */
	public function __construct (string $data, int $options = 0, bool $dataIsURL = false, string $namespaceOrPrefix = '', bool $isPrefix = false) {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 * @param string|null $value [optional]
	 * @param string|null $namespace [optional]
	 */
	public function addChild (string $qualifiedName, ?string $value = NULL, ?string $namespace = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 * @param string $value
	 * @param string|null $namespace [optional]
	 */
	public function addAttribute (string $qualifiedName, string $value, ?string $namespace = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function getChildren () {}

}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param string|null $class_name [optional]
 * @param int $options [optional]
 * @param string $namespace_or_prefix [optional]
 * @param bool $is_prefix [optional]
 */
function simplexml_load_file (string $filename, ?string $class_name = 'SimpleXMLElement', int $options = 0, string $namespace_or_prefix = '', bool $is_prefix = false): SimpleXMLElement|false {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param string|null $class_name [optional]
 * @param int $options [optional]
 * @param string $namespace_or_prefix [optional]
 * @param bool $is_prefix [optional]
 */
function simplexml_load_string (string $data, ?string $class_name = 'SimpleXMLElement', int $options = 0, string $namespace_or_prefix = '', bool $is_prefix = false): SimpleXMLElement|false {}

/**
 * {@inheritdoc}
 * @param SimpleXMLElement|DOMNode $node
 * @param string|null $class_name [optional]
 */
function simplexml_import_dom (SimpleXMLElement|DOMNode $node, ?string $class_name = 'SimpleXMLElement'): ?SimpleXMLElement {}

// End of SimpleXML v.8.3.0
