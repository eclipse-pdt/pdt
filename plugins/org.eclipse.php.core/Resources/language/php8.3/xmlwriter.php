<?php

// Start of xmlwriter v.8.3.0

class XMLWriter  {

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 */
	public function openUri (string $uri) {}

	/**
	 * {@inheritdoc}
	 */
	public function openMemory () {}

	/**
	 * {@inheritdoc}
	 * @param bool $enable
	 */
	public function setIndent (bool $enable) {}

	/**
	 * {@inheritdoc}
	 * @param string $indentation
	 */
	public function setIndentString (string $indentation) {}

	/**
	 * {@inheritdoc}
	 */
	public function startComment () {}

	/**
	 * {@inheritdoc}
	 */
	public function endComment () {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function startAttribute (string $name) {}

	/**
	 * {@inheritdoc}
	 */
	public function endAttribute () {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param string $value
	 */
	public function writeAttribute (string $name, string $value) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 * @param string $name
	 * @param string|null $namespace
	 */
	public function startAttributeNs (?string $prefix = null, string $name, ?string $namespace = null) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 * @param string $name
	 * @param string|null $namespace
	 * @param string $value
	 */
	public function writeAttributeNs (?string $prefix = null, string $name, ?string $namespace = null, string $value) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function startElement (string $name) {}

	/**
	 * {@inheritdoc}
	 */
	public function endElement () {}

	/**
	 * {@inheritdoc}
	 */
	public function fullEndElement () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 * @param string $name
	 * @param string|null $namespace
	 */
	public function startElementNs (?string $prefix = null, string $name, ?string $namespace = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param string|null $content [optional]
	 */
	public function writeElement (string $name, ?string $content = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $prefix
	 * @param string $name
	 * @param string|null $namespace
	 * @param string|null $content [optional]
	 */
	public function writeElementNs (?string $prefix = null, string $name, ?string $namespace = null, ?string $content = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $target
	 */
	public function startPi (string $target) {}

	/**
	 * {@inheritdoc}
	 */
	public function endPi () {}

	/**
	 * {@inheritdoc}
	 * @param string $target
	 * @param string $content
	 */
	public function writePi (string $target, string $content) {}

	/**
	 * {@inheritdoc}
	 */
	public function startCdata () {}

	/**
	 * {@inheritdoc}
	 */
	public function endCdata () {}

	/**
	 * {@inheritdoc}
	 * @param string $content
	 */
	public function writeCdata (string $content) {}

	/**
	 * {@inheritdoc}
	 * @param string $content
	 */
	public function text (string $content) {}

	/**
	 * {@inheritdoc}
	 * @param string $content
	 */
	public function writeRaw (string $content) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $version [optional]
	 * @param string|null $encoding [optional]
	 * @param string|null $standalone [optional]
	 */
	public function startDocument (?string $version = '1.0', ?string $encoding = NULL, ?string $standalone = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function endDocument () {}

	/**
	 * {@inheritdoc}
	 * @param string $content
	 */
	public function writeComment (string $content) {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 * @param string|null $publicId [optional]
	 * @param string|null $systemId [optional]
	 */
	public function startDtd (string $qualifiedName, ?string $publicId = NULL, ?string $systemId = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function endDtd () {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param string|null $publicId [optional]
	 * @param string|null $systemId [optional]
	 * @param string|null $content [optional]
	 */
	public function writeDtd (string $name, ?string $publicId = NULL, ?string $systemId = NULL, ?string $content = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $qualifiedName
	 */
	public function startDtdElement (string $qualifiedName) {}

	/**
	 * {@inheritdoc}
	 */
	public function endDtdElement () {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param string $content
	 */
	public function writeDtdElement (string $name, string $content) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function startDtdAttlist (string $name) {}

	/**
	 * {@inheritdoc}
	 */
	public function endDtdAttlist () {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param string $content
	 */
	public function writeDtdAttlist (string $name, string $content) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param bool $isParam
	 */
	public function startDtdEntity (string $name, bool $isParam) {}

	/**
	 * {@inheritdoc}
	 */
	public function endDtdEntity () {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param string $content
	 * @param bool $isParam [optional]
	 * @param string|null $publicId [optional]
	 * @param string|null $systemId [optional]
	 * @param string|null $notationData [optional]
	 */
	public function writeDtdEntity (string $name, string $content, bool $isParam = false, ?string $publicId = NULL, ?string $systemId = NULL, ?string $notationData = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $flush [optional]
	 */
	public function outputMemory (bool $flush = true) {}

	/**
	 * {@inheritdoc}
	 * @param bool $empty [optional]
	 */
	public function flush (bool $empty = true) {}

}

/**
 * {@inheritdoc}
 * @param string $uri
 */
function xmlwriter_open_uri (string $uri): XMLWriter|false {}

/**
 * {@inheritdoc}
 */
function xmlwriter_open_memory (): XMLWriter|false {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param bool $enable
 */
function xmlwriter_set_indent (XMLWriter $writer, bool $enable): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $indentation
 */
function xmlwriter_set_indent_string (XMLWriter $writer, string $indentation): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 */
function xmlwriter_start_comment (XMLWriter $writer): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 */
function xmlwriter_end_comment (XMLWriter $writer): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $name
 */
function xmlwriter_start_attribute (XMLWriter $writer, string $name): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 */
function xmlwriter_end_attribute (XMLWriter $writer): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $name
 * @param string $value
 */
function xmlwriter_write_attribute (XMLWriter $writer, string $name, string $value): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string|null $prefix
 * @param string $name
 * @param string|null $namespace
 */
function xmlwriter_start_attribute_ns (XMLWriter $writer, ?string $prefix = null, string $name, ?string $namespace = null): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string|null $prefix
 * @param string $name
 * @param string|null $namespace
 * @param string $value
 */
function xmlwriter_write_attribute_ns (XMLWriter $writer, ?string $prefix = null, string $name, ?string $namespace = null, string $value): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $name
 */
function xmlwriter_start_element (XMLWriter $writer, string $name): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 */
function xmlwriter_end_element (XMLWriter $writer): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 */
function xmlwriter_full_end_element (XMLWriter $writer): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string|null $prefix
 * @param string $name
 * @param string|null $namespace
 */
function xmlwriter_start_element_ns (XMLWriter $writer, ?string $prefix = null, string $name, ?string $namespace = null): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $name
 * @param string|null $content [optional]
 */
function xmlwriter_write_element (XMLWriter $writer, string $name, ?string $content = NULL): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string|null $prefix
 * @param string $name
 * @param string|null $namespace
 * @param string|null $content [optional]
 */
function xmlwriter_write_element_ns (XMLWriter $writer, ?string $prefix = null, string $name, ?string $namespace = null, ?string $content = NULL): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $target
 */
function xmlwriter_start_pi (XMLWriter $writer, string $target): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 */
function xmlwriter_end_pi (XMLWriter $writer): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $target
 * @param string $content
 */
function xmlwriter_write_pi (XMLWriter $writer, string $target, string $content): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 */
function xmlwriter_start_cdata (XMLWriter $writer): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 */
function xmlwriter_end_cdata (XMLWriter $writer): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $content
 */
function xmlwriter_write_cdata (XMLWriter $writer, string $content): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $content
 */
function xmlwriter_text (XMLWriter $writer, string $content): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $content
 */
function xmlwriter_write_raw (XMLWriter $writer, string $content): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string|null $version [optional]
 * @param string|null $encoding [optional]
 * @param string|null $standalone [optional]
 */
function xmlwriter_start_document (XMLWriter $writer, ?string $version = '1.0', ?string $encoding = NULL, ?string $standalone = NULL): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 */
function xmlwriter_end_document (XMLWriter $writer): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $content
 */
function xmlwriter_write_comment (XMLWriter $writer, string $content): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $qualifiedName
 * @param string|null $publicId [optional]
 * @param string|null $systemId [optional]
 */
function xmlwriter_start_dtd (XMLWriter $writer, string $qualifiedName, ?string $publicId = NULL, ?string $systemId = NULL): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 */
function xmlwriter_end_dtd (XMLWriter $writer): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $name
 * @param string|null $publicId [optional]
 * @param string|null $systemId [optional]
 * @param string|null $content [optional]
 */
function xmlwriter_write_dtd (XMLWriter $writer, string $name, ?string $publicId = NULL, ?string $systemId = NULL, ?string $content = NULL): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $qualifiedName
 */
function xmlwriter_start_dtd_element (XMLWriter $writer, string $qualifiedName): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 */
function xmlwriter_end_dtd_element (XMLWriter $writer): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $name
 * @param string $content
 */
function xmlwriter_write_dtd_element (XMLWriter $writer, string $name, string $content): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $name
 */
function xmlwriter_start_dtd_attlist (XMLWriter $writer, string $name): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 */
function xmlwriter_end_dtd_attlist (XMLWriter $writer): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $name
 * @param string $content
 */
function xmlwriter_write_dtd_attlist (XMLWriter $writer, string $name, string $content): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $name
 * @param bool $isParam
 */
function xmlwriter_start_dtd_entity (XMLWriter $writer, string $name, bool $isParam): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 */
function xmlwriter_end_dtd_entity (XMLWriter $writer): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param string $name
 * @param string $content
 * @param bool $isParam [optional]
 * @param string|null $publicId [optional]
 * @param string|null $systemId [optional]
 * @param string|null $notationData [optional]
 */
function xmlwriter_write_dtd_entity (XMLWriter $writer, string $name, string $content, bool $isParam = false, ?string $publicId = NULL, ?string $systemId = NULL, ?string $notationData = NULL): bool {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param bool $flush [optional]
 */
function xmlwriter_output_memory (XMLWriter $writer, bool $flush = true): string {}

/**
 * {@inheritdoc}
 * @param XMLWriter $writer
 * @param bool $empty [optional]
 */
function xmlwriter_flush (XMLWriter $writer, bool $empty = true): string|int {}

// End of xmlwriter v.8.3.0
