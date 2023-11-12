<?php

// Start of xmlwriter v.8.2.6

/**
 * @link http://www.php.net/manual/en/class.xmlwriter.php
 */
class XMLWriter  {

	/**
	 * Create new xmlwriter using source uri for output
	 * @link http://www.php.net/manual/en/xmlwriter.openuri.php
	 * @param string $uri 
	 * @return bool Object-oriented style: Returns true on success or false on failure.
	 * <p>Procedural style: Returns a new XMLWriter instance for later use with the
	 * xmlwriter functions on success, or false on failure.</p>
	 */
	public function openUri (string $uri): bool {}

	/**
	 * Create new xmlwriter using memory for string output
	 * @link http://www.php.net/manual/en/xmlwriter.openmemory.php
	 * @return bool Object-oriented style: Returns true on success or false on failure.
	 * <p>Procedural style: Returns a new XMLWriter for later use with the
	 * xmlwriter functions on success, or false on failure.</p>
	 */
	public function openMemory (): bool {}

	/**
	 * Toggle indentation on/off
	 * @link http://www.php.net/manual/en/xmlwriter.setindent.php
	 * @param bool $enable 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setIndent (bool $enable): bool {}

	/**
	 * Set string used for indenting
	 * @link http://www.php.net/manual/en/xmlwriter.setindentstring.php
	 * @param string $indentation 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setIndentString (string $indentation): bool {}

	/**
	 * Create start comment
	 * @link http://www.php.net/manual/en/xmlwriter.startcomment.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function startComment (): bool {}

	/**
	 * Create end comment
	 * @link http://www.php.net/manual/en/xmlwriter.endcomment.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function endComment (): bool {}

	/**
	 * Create start attribute
	 * @link http://www.php.net/manual/en/xmlwriter.startattribute.php
	 * @param string $name 
	 * @return bool Returns true on success or false on failure.
	 */
	public function startAttribute (string $name): bool {}

	/**
	 * End attribute
	 * @link http://www.php.net/manual/en/xmlwriter.endattribute.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function endAttribute (): bool {}

	/**
	 * Write full attribute
	 * @link http://www.php.net/manual/en/xmlwriter.writeattribute.php
	 * @param string $name 
	 * @param string $value 
	 * @return bool Returns true on success or false on failure.
	 */
	public function writeAttribute (string $name, string $value): bool {}

	/**
	 * Create start namespaced attribute
	 * @link http://www.php.net/manual/en/xmlwriter.startattributens.php
	 * @param string|null $prefix 
	 * @param string $name 
	 * @param string|null $namespace 
	 * @return bool Returns true on success or false on failure.
	 */
	public function startAttributeNs (?string $prefix, string $name, ?string $namespace): bool {}

	/**
	 * Write full namespaced attribute
	 * @link http://www.php.net/manual/en/xmlwriter.writeattributens.php
	 * @param string|null $prefix 
	 * @param string $name 
	 * @param string|null $namespace 
	 * @param string $value 
	 * @return bool Returns true on success or false on failure.
	 */
	public function writeAttributeNs (?string $prefix, string $name, ?string $namespace, string $value): bool {}

	/**
	 * Create start element tag
	 * @link http://www.php.net/manual/en/xmlwriter.startelement.php
	 * @param string $name 
	 * @return bool Returns true on success or false on failure.
	 */
	public function startElement (string $name): bool {}

	/**
	 * End current element
	 * @link http://www.php.net/manual/en/xmlwriter.endelement.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function endElement (): bool {}

	/**
	 * End current element
	 * @link http://www.php.net/manual/en/xmlwriter.fullendelement.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function fullEndElement (): bool {}

	/**
	 * Create start namespaced element tag
	 * @link http://www.php.net/manual/en/xmlwriter.startelementns.php
	 * @param string|null $prefix 
	 * @param string $name 
	 * @param string|null $namespace 
	 * @return bool Returns true on success or false on failure.
	 */
	public function startElementNs (?string $prefix, string $name, ?string $namespace): bool {}

	/**
	 * Write full element tag
	 * @link http://www.php.net/manual/en/xmlwriter.writeelement.php
	 * @param string $name 
	 * @param string|null $content [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function writeElement (string $name, ?string $content = null): bool {}

	/**
	 * Write full namespaced element tag
	 * @link http://www.php.net/manual/en/xmlwriter.writeelementns.php
	 * @param string|null $prefix 
	 * @param string $name 
	 * @param string|null $namespace 
	 * @param string|null $content [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function writeElementNs (?string $prefix, string $name, ?string $namespace, ?string $content = null): bool {}

	/**
	 * Create start PI tag
	 * @link http://www.php.net/manual/en/xmlwriter.startpi.php
	 * @param string $target 
	 * @return bool Returns true on success or false on failure.
	 */
	public function startPi (string $target): bool {}

	/**
	 * End current PI
	 * @link http://www.php.net/manual/en/xmlwriter.endpi.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function endPi (): bool {}

	/**
	 * Writes a PI
	 * @link http://www.php.net/manual/en/xmlwriter.writepi.php
	 * @param string $target 
	 * @param string $content 
	 * @return bool Returns true on success or false on failure.
	 */
	public function writePi (string $target, string $content): bool {}

	/**
	 * Create start CDATA tag
	 * @link http://www.php.net/manual/en/xmlwriter.startcdata.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function startCdata (): bool {}

	/**
	 * End current CDATA
	 * @link http://www.php.net/manual/en/xmlwriter.endcdata.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function endCdata (): bool {}

	/**
	 * Write full CDATA tag
	 * @link http://www.php.net/manual/en/xmlwriter.writecdata.php
	 * @param string $content 
	 * @return bool Returns true on success or false on failure.
	 */
	public function writeCdata (string $content): bool {}

	/**
	 * Write text
	 * @link http://www.php.net/manual/en/xmlwriter.text.php
	 * @param string $content 
	 * @return bool Returns true on success or false on failure.
	 */
	public function text (string $content): bool {}

	/**
	 * Write a raw XML text
	 * @link http://www.php.net/manual/en/xmlwriter.writeraw.php
	 * @param string $content 
	 * @return bool Returns true on success or false on failure.
	 */
	public function writeRaw (string $content): bool {}

	/**
	 * Create document tag
	 * @link http://www.php.net/manual/en/xmlwriter.startdocument.php
	 * @param string|null $version [optional] 
	 * @param string|null $encoding [optional] 
	 * @param string|null $standalone [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function startDocument (?string $version = '"1.0"', ?string $encoding = null, ?string $standalone = null): bool {}

	/**
	 * End current document
	 * @link http://www.php.net/manual/en/xmlwriter.enddocument.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function endDocument (): bool {}

	/**
	 * Write full comment tag
	 * @link http://www.php.net/manual/en/xmlwriter.writecomment.php
	 * @param string $content 
	 * @return bool Returns true on success or false on failure.
	 */
	public function writeComment (string $content): bool {}

	/**
	 * Create start DTD tag
	 * @link http://www.php.net/manual/en/xmlwriter.startdtd.php
	 * @param string $qualifiedName 
	 * @param string|null $publicId [optional] 
	 * @param string|null $systemId [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function startDtd (string $qualifiedName, ?string $publicId = null, ?string $systemId = null): bool {}

	/**
	 * End current DTD
	 * @link http://www.php.net/manual/en/xmlwriter.enddtd.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function endDtd (): bool {}

	/**
	 * Write full DTD tag
	 * @link http://www.php.net/manual/en/xmlwriter.writedtd.php
	 * @param string $name 
	 * @param string|null $publicId [optional] 
	 * @param string|null $systemId [optional] 
	 * @param string|null $content [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function writeDtd (string $name, ?string $publicId = null, ?string $systemId = null, ?string $content = null): bool {}

	/**
	 * Create start DTD element
	 * @link http://www.php.net/manual/en/xmlwriter.startdtdelement.php
	 * @param string $qualifiedName 
	 * @return bool Returns true on success or false on failure.
	 */
	public function startDtdElement (string $qualifiedName): bool {}

	/**
	 * End current DTD element
	 * @link http://www.php.net/manual/en/xmlwriter.enddtdelement.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function endDtdElement (): bool {}

	/**
	 * Write full DTD element tag
	 * @link http://www.php.net/manual/en/xmlwriter.writedtdelement.php
	 * @param string $name 
	 * @param string $content 
	 * @return bool Returns true on success or false on failure.
	 */
	public function writeDtdElement (string $name, string $content): bool {}

	/**
	 * Create start DTD AttList
	 * @link http://www.php.net/manual/en/xmlwriter.startdtdattlist.php
	 * @param string $name 
	 * @return bool Returns true on success or false on failure.
	 */
	public function startDtdAttlist (string $name): bool {}

	/**
	 * End current DTD AttList
	 * @link http://www.php.net/manual/en/xmlwriter.enddtdattlist.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function endDtdAttlist (): bool {}

	/**
	 * Write full DTD AttList tag
	 * @link http://www.php.net/manual/en/xmlwriter.writedtdattlist.php
	 * @param string $name 
	 * @param string $content 
	 * @return bool Returns true on success or false on failure.
	 */
	public function writeDtdAttlist (string $name, string $content): bool {}

	/**
	 * Create start DTD Entity
	 * @link http://www.php.net/manual/en/xmlwriter.startdtdentity.php
	 * @param string $name 
	 * @param bool $isParam 
	 * @return bool Returns true on success or false on failure.
	 */
	public function startDtdEntity (string $name, bool $isParam): bool {}

	/**
	 * End current DTD Entity
	 * @link http://www.php.net/manual/en/xmlwriter.enddtdentity.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function endDtdEntity (): bool {}

	/**
	 * Write full DTD Entity tag
	 * @link http://www.php.net/manual/en/xmlwriter.writedtdentity.php
	 * @param string $name 
	 * @param string $content 
	 * @param bool $isParam [optional] 
	 * @param string|null $publicId [optional] 
	 * @param string|null $systemId [optional] 
	 * @param string|null $notationData [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function writeDtdEntity (string $name, string $content, bool $isParam = false, ?string $publicId = null, ?string $systemId = null, ?string $notationData = null): bool {}

	/**
	 * Returns current buffer
	 * @link http://www.php.net/manual/en/xmlwriter.outputmemory.php
	 * @param bool $flush [optional] 
	 * @return string Returns the current buffer as a string.
	 */
	public function outputMemory (bool $flush = true): string {}

	/**
	 * Flush current buffer
	 * @link http://www.php.net/manual/en/xmlwriter.flush.php
	 * @param bool $empty [optional] 
	 * @return string|int If you opened the writer in memory, this function returns the generated XML buffer,
	 * Else, if using URI, this function will write the buffer and return the number of 
	 * written bytes.
	 */
	public function flush (bool $empty = true): string|int {}

}

/**
 * Create new xmlwriter using source uri for output
 * @link http://www.php.net/manual/en/xmlwriter.openuri.php
 * @param string $uri 
 * @return XMLWriter|false Object-oriented style: Returns true on success or false on failure.
 * <p>Procedural style: Returns a new XMLWriter instance for later use with the
 * xmlwriter functions on success, or false on failure.</p>
 */
function xmlwriter_open_uri (string $uri): XMLWriter|false {}

/**
 * Create new xmlwriter using memory for string output
 * @link http://www.php.net/manual/en/xmlwriter.openmemory.php
 * @return XMLWriter|false Object-oriented style: Returns true on success or false on failure.
 * <p>Procedural style: Returns a new XMLWriter for later use with the
 * xmlwriter functions on success, or false on failure.</p>
 */
function xmlwriter_open_memory (): XMLWriter|false {}

/**
 * Toggle indentation on/off
 * @link http://www.php.net/manual/en/xmlwriter.setindent.php
 * @param XMLWriter $writer 
 * @param bool $enable 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_set_indent (XMLWriter $writer, bool $enable): bool {}

/**
 * Set string used for indenting
 * @link http://www.php.net/manual/en/xmlwriter.setindentstring.php
 * @param XMLWriter $writer 
 * @param string $indentation 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_set_indent_string (XMLWriter $writer, string $indentation): bool {}

/**
 * Create start comment
 * @link http://www.php.net/manual/en/xmlwriter.startcomment.php
 * @param XMLWriter $writer 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_start_comment (XMLWriter $writer): bool {}

/**
 * Create end comment
 * @link http://www.php.net/manual/en/xmlwriter.endcomment.php
 * @param XMLWriter $writer 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_end_comment (XMLWriter $writer): bool {}

/**
 * Create start attribute
 * @link http://www.php.net/manual/en/xmlwriter.startattribute.php
 * @param XMLWriter $writer 
 * @param string $name 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_start_attribute (XMLWriter $writer, string $name): bool {}

/**
 * End attribute
 * @link http://www.php.net/manual/en/xmlwriter.endattribute.php
 * @param XMLWriter $writer 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_end_attribute (XMLWriter $writer): bool {}

/**
 * Write full attribute
 * @link http://www.php.net/manual/en/xmlwriter.writeattribute.php
 * @param XMLWriter $writer 
 * @param string $name 
 * @param string $value 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_write_attribute (XMLWriter $writer, string $name, string $value): bool {}

/**
 * Create start namespaced attribute
 * @link http://www.php.net/manual/en/xmlwriter.startattributens.php
 * @param XMLWriter $writer 
 * @param string|null $prefix 
 * @param string $name 
 * @param string|null $namespace 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_start_attribute_ns (XMLWriter $writer, ?string $prefix, string $name, ?string $namespace): bool {}

/**
 * Write full namespaced attribute
 * @link http://www.php.net/manual/en/xmlwriter.writeattributens.php
 * @param XMLWriter $writer 
 * @param string|null $prefix 
 * @param string $name 
 * @param string|null $namespace 
 * @param string $value 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_write_attribute_ns (XMLWriter $writer, ?string $prefix, string $name, ?string $namespace, string $value): bool {}

/**
 * Create start element tag
 * @link http://www.php.net/manual/en/xmlwriter.startelement.php
 * @param XMLWriter $writer 
 * @param string $name 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_start_element (XMLWriter $writer, string $name): bool {}

/**
 * End current element
 * @link http://www.php.net/manual/en/xmlwriter.endelement.php
 * @param XMLWriter $writer 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_end_element (XMLWriter $writer): bool {}

/**
 * End current element
 * @link http://www.php.net/manual/en/xmlwriter.fullendelement.php
 * @param XMLWriter $writer 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_full_end_element (XMLWriter $writer): bool {}

/**
 * Create start namespaced element tag
 * @link http://www.php.net/manual/en/xmlwriter.startelementns.php
 * @param XMLWriter $writer 
 * @param string|null $prefix 
 * @param string $name 
 * @param string|null $namespace 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_start_element_ns (XMLWriter $writer, ?string $prefix, string $name, ?string $namespace): bool {}

/**
 * Write full element tag
 * @link http://www.php.net/manual/en/xmlwriter.writeelement.php
 * @param XMLWriter $writer 
 * @param string $name 
 * @param string|null $content [optional] 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_write_element (XMLWriter $writer, string $name, ?string $content = null): bool {}

/**
 * Write full namespaced element tag
 * @link http://www.php.net/manual/en/xmlwriter.writeelementns.php
 * @param XMLWriter $writer 
 * @param string|null $prefix 
 * @param string $name 
 * @param string|null $namespace 
 * @param string|null $content [optional] 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_write_element_ns (XMLWriter $writer, ?string $prefix, string $name, ?string $namespace, ?string $content = null): bool {}

/**
 * Create start PI tag
 * @link http://www.php.net/manual/en/xmlwriter.startpi.php
 * @param XMLWriter $writer 
 * @param string $target 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_start_pi (XMLWriter $writer, string $target): bool {}

/**
 * End current PI
 * @link http://www.php.net/manual/en/xmlwriter.endpi.php
 * @param XMLWriter $writer 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_end_pi (XMLWriter $writer): bool {}

/**
 * Writes a PI
 * @link http://www.php.net/manual/en/xmlwriter.writepi.php
 * @param XMLWriter $writer 
 * @param string $target 
 * @param string $content 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_write_pi (XMLWriter $writer, string $target, string $content): bool {}

/**
 * Create start CDATA tag
 * @link http://www.php.net/manual/en/xmlwriter.startcdata.php
 * @param XMLWriter $writer 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_start_cdata (XMLWriter $writer): bool {}

/**
 * End current CDATA
 * @link http://www.php.net/manual/en/xmlwriter.endcdata.php
 * @param XMLWriter $writer 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_end_cdata (XMLWriter $writer): bool {}

/**
 * Write full CDATA tag
 * @link http://www.php.net/manual/en/xmlwriter.writecdata.php
 * @param XMLWriter $writer 
 * @param string $content 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_write_cdata (XMLWriter $writer, string $content): bool {}

/**
 * Write text
 * @link http://www.php.net/manual/en/xmlwriter.text.php
 * @param XMLWriter $writer 
 * @param string $content 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_text (XMLWriter $writer, string $content): bool {}

/**
 * Write a raw XML text
 * @link http://www.php.net/manual/en/xmlwriter.writeraw.php
 * @param XMLWriter $writer 
 * @param string $content 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_write_raw (XMLWriter $writer, string $content): bool {}

/**
 * Create document tag
 * @link http://www.php.net/manual/en/xmlwriter.startdocument.php
 * @param XMLWriter $writer 
 * @param string|null $version [optional] 
 * @param string|null $encoding [optional] 
 * @param string|null $standalone [optional] 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_start_document (XMLWriter $writer, ?string $version = '"1.0"', ?string $encoding = null, ?string $standalone = null): bool {}

/**
 * End current document
 * @link http://www.php.net/manual/en/xmlwriter.enddocument.php
 * @param XMLWriter $writer 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_end_document (XMLWriter $writer): bool {}

/**
 * Write full comment tag
 * @link http://www.php.net/manual/en/xmlwriter.writecomment.php
 * @param XMLWriter $writer 
 * @param string $content 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_write_comment (XMLWriter $writer, string $content): bool {}

/**
 * Create start DTD tag
 * @link http://www.php.net/manual/en/xmlwriter.startdtd.php
 * @param XMLWriter $writer 
 * @param string $qualifiedName 
 * @param string|null $publicId [optional] 
 * @param string|null $systemId [optional] 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_start_dtd (XMLWriter $writer, string $qualifiedName, ?string $publicId = null, ?string $systemId = null): bool {}

/**
 * End current DTD
 * @link http://www.php.net/manual/en/xmlwriter.enddtd.php
 * @param XMLWriter $writer 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_end_dtd (XMLWriter $writer): bool {}

/**
 * Write full DTD tag
 * @link http://www.php.net/manual/en/xmlwriter.writedtd.php
 * @param XMLWriter $writer 
 * @param string $name 
 * @param string|null $publicId [optional] 
 * @param string|null $systemId [optional] 
 * @param string|null $content [optional] 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_write_dtd (XMLWriter $writer, string $name, ?string $publicId = null, ?string $systemId = null, ?string $content = null): bool {}

/**
 * Create start DTD element
 * @link http://www.php.net/manual/en/xmlwriter.startdtdelement.php
 * @param XMLWriter $writer 
 * @param string $qualifiedName 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_start_dtd_element (XMLWriter $writer, string $qualifiedName): bool {}

/**
 * End current DTD element
 * @link http://www.php.net/manual/en/xmlwriter.enddtdelement.php
 * @param XMLWriter $writer 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_end_dtd_element (XMLWriter $writer): bool {}

/**
 * Write full DTD element tag
 * @link http://www.php.net/manual/en/xmlwriter.writedtdelement.php
 * @param XMLWriter $writer 
 * @param string $name 
 * @param string $content 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_write_dtd_element (XMLWriter $writer, string $name, string $content): bool {}

/**
 * Create start DTD AttList
 * @link http://www.php.net/manual/en/xmlwriter.startdtdattlist.php
 * @param XMLWriter $writer 
 * @param string $name 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_start_dtd_attlist (XMLWriter $writer, string $name): bool {}

/**
 * End current DTD AttList
 * @link http://www.php.net/manual/en/xmlwriter.enddtdattlist.php
 * @param XMLWriter $writer 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_end_dtd_attlist (XMLWriter $writer): bool {}

/**
 * Write full DTD AttList tag
 * @link http://www.php.net/manual/en/xmlwriter.writedtdattlist.php
 * @param XMLWriter $writer 
 * @param string $name 
 * @param string $content 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_write_dtd_attlist (XMLWriter $writer, string $name, string $content): bool {}

/**
 * Create start DTD Entity
 * @link http://www.php.net/manual/en/xmlwriter.startdtdentity.php
 * @param XMLWriter $writer 
 * @param string $name 
 * @param bool $isParam 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_start_dtd_entity (XMLWriter $writer, string $name, bool $isParam): bool {}

/**
 * End current DTD Entity
 * @link http://www.php.net/manual/en/xmlwriter.enddtdentity.php
 * @param XMLWriter $writer 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_end_dtd_entity (XMLWriter $writer): bool {}

/**
 * Write full DTD Entity tag
 * @link http://www.php.net/manual/en/xmlwriter.writedtdentity.php
 * @param XMLWriter $writer 
 * @param string $name 
 * @param string $content 
 * @param bool $isParam [optional] 
 * @param string|null $publicId [optional] 
 * @param string|null $systemId [optional] 
 * @param string|null $notationData [optional] 
 * @return bool Returns true on success or false on failure.
 */
function xmlwriter_write_dtd_entity (XMLWriter $writer, string $name, string $content, bool $isParam = false, ?string $publicId = null, ?string $systemId = null, ?string $notationData = null): bool {}

/**
 * Returns current buffer
 * @link http://www.php.net/manual/en/xmlwriter.outputmemory.php
 * @param XMLWriter $writer 
 * @param bool $flush [optional] 
 * @return string Returns the current buffer as a string.
 */
function xmlwriter_output_memory (XMLWriter $writer, bool $flush = true): string {}

/**
 * Flush current buffer
 * @link http://www.php.net/manual/en/xmlwriter.flush.php
 * @param XMLWriter $writer 
 * @param bool $empty [optional] 
 * @return string|int If you opened the writer in memory, this function returns the generated XML buffer,
 * Else, if using URI, this function will write the buffer and return the number of 
 * written bytes.
 */
function xmlwriter_flush (XMLWriter $writer, bool $empty = true): string|int {}

// End of xmlwriter v.8.2.6
