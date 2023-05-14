<?php

// Start of xmlwriter v.8.2.6

/**
 * @link http://www.php.net/manual/en/class.xmlwriter.php
 */
class XMLWriter  {

	/**
	 * Create new xmlwriter using source uri for output
	 * @link http://www.php.net/manual/en/xmlwriter.openuri.php
	 * @param string $uri The URI of the resource for the output.
	 * @return bool Object-oriented style: Returns true on success or false on failure
	 * <p>
	 * Procedural style: Returns a new XMLWriter instance for later use with the
	 * xmlwriter functions on success, or false on failure.
	 * </p>
	 */
	public function openUri (string $uri) {}

	/**
	 * Create new xmlwriter using memory for string output
	 * @link http://www.php.net/manual/en/xmlwriter.openmemory.php
	 * @return bool Object-oriented style: Returns true on success or false on failure
	 * <p>
	 * Procedural style: Returns a new XMLWriter for later use with the
	 * xmlwriter functions on success, or false on failure.
	 * </p>
	 */
	public function openMemory () {}

	/**
	 * Toggle indentation on/off
	 * @link http://www.php.net/manual/en/xmlwriter.setindent.php
	 * @param bool $enable Whether indentation is enabled.
	 * @return bool true on success or false on failure
	 */
	public function setIndent (bool $enable) {}

	/**
	 * Set string used for indenting
	 * @link http://www.php.net/manual/en/xmlwriter.setindentstring.php
	 * @param string $indentation The indentation string.
	 * @return bool true on success or false on failure
	 */
	public function setIndentString (string $indentation) {}

	/**
	 * Create start comment
	 * @link http://www.php.net/manual/en/xmlwriter.startcomment.php
	 * @return bool true on success or false on failure
	 */
	public function startComment () {}

	/**
	 * Create end comment
	 * @link http://www.php.net/manual/en/xmlwriter.endcomment.php
	 * @return bool true on success or false on failure
	 */
	public function endComment () {}

	/**
	 * Create start attribute
	 * @link http://www.php.net/manual/en/xmlwriter.startattribute.php
	 * @param string $name The attribute name.
	 * @return bool true on success or false on failure
	 */
	public function startAttribute (string $name) {}

	/**
	 * End attribute
	 * @link http://www.php.net/manual/en/xmlwriter.endattribute.php
	 * @return bool true on success or false on failure
	 */
	public function endAttribute () {}

	/**
	 * Write full attribute
	 * @link http://www.php.net/manual/en/xmlwriter.writeattribute.php
	 * @param string $name The name of the attribute.
	 * @param string $value The value of the attribute.
	 * @return bool true on success or false on failure
	 */
	public function writeAttribute (string $name, string $value) {}

	/**
	 * Create start namespaced attribute
	 * @link http://www.php.net/manual/en/xmlwriter.startattributens.php
	 * @param mixed $prefix The namespace prefix.
	 * @param string $name The attribute name.
	 * @param mixed $namespace The namespace URI.
	 * If namespace is null, the namespace declaration will be
	 * omitted.
	 * @return bool true on success or false on failure
	 */
	public function startAttributeNs ($prefix, string $name, $namespace) {}

	/**
	 * Write full namespaced attribute
	 * @link http://www.php.net/manual/en/xmlwriter.writeattributens.php
	 * @param mixed $prefix The namespace prefix.
	 * If prefix is null, the namespace will be omitted.
	 * @param string $name The attribute name.
	 * @param mixed $namespace The namespace URI.
	 * If namespace is null, the namespace declaration will be
	 * omitted.
	 * @param string $value The attribute value.
	 * @return bool true on success or false on failure
	 */
	public function writeAttributeNs ($prefix, string $name, $namespace, string $value) {}

	/**
	 * Create start element tag
	 * @link http://www.php.net/manual/en/xmlwriter.startelement.php
	 * @param string $name The element name.
	 * @return bool true on success or false on failure
	 */
	public function startElement (string $name) {}

	/**
	 * End current element
	 * @link http://www.php.net/manual/en/xmlwriter.endelement.php
	 * @return bool true on success or false on failure
	 */
	public function endElement () {}

	/**
	 * End current element
	 * @link http://www.php.net/manual/en/xmlwriter.fullendelement.php
	 * @return bool true on success or false on failure
	 */
	public function fullEndElement () {}

	/**
	 * Create start namespaced element tag
	 * @link http://www.php.net/manual/en/xmlwriter.startelementns.php
	 * @param mixed $prefix The namespace prefix.
	 * If prefix is null, the namespace will be omitted.
	 * @param string $name The element name.
	 * @param mixed $namespace The namespace URI.
	 * If namespace is null, the namespace declaration will be
	 * omitted.
	 * @return bool true on success or false on failure
	 */
	public function startElementNs ($prefix, string $name, $namespace) {}

	/**
	 * Write full element tag
	 * @link http://www.php.net/manual/en/xmlwriter.writeelement.php
	 * @param string $name The element name.
	 * @param mixed $content [optional] The element contents.
	 * @return bool true on success or false on failure
	 */
	public function writeElement (string $name, $content = null) {}

	/**
	 * Write full namespaced element tag
	 * @link http://www.php.net/manual/en/xmlwriter.writeelementns.php
	 * @param mixed $prefix The namespace prefix.
	 * If prefix is null, the namespace will be omitted.
	 * @param string $name The element name.
	 * @param mixed $namespace The namespace URI.
	 * If namespace is null, the namespace declaration will be
	 * omitted.
	 * @param mixed $content [optional] The element contents.
	 * @return bool true on success or false on failure
	 */
	public function writeElementNs ($prefix, string $name, $namespace, $content = null) {}

	/**
	 * Create start PI tag
	 * @link http://www.php.net/manual/en/xmlwriter.startpi.php
	 * @param string $target The target of the processing instruction.
	 * @return bool true on success or false on failure
	 */
	public function startPi (string $target) {}

	/**
	 * End current PI
	 * @link http://www.php.net/manual/en/xmlwriter.endpi.php
	 * @return bool true on success or false on failure
	 */
	public function endPi () {}

	/**
	 * Writes a PI
	 * @link http://www.php.net/manual/en/xmlwriter.writepi.php
	 * @param string $target The target of the processing instruction.
	 * @param string $content The content of the processing instruction.
	 * @return bool true on success or false on failure
	 */
	public function writePi (string $target, string $content) {}

	/**
	 * Create start CDATA tag
	 * @link http://www.php.net/manual/en/xmlwriter.startcdata.php
	 * @return bool true on success or false on failure
	 */
	public function startCdata () {}

	/**
	 * End current CDATA
	 * @link http://www.php.net/manual/en/xmlwriter.endcdata.php
	 * @return bool true on success or false on failure
	 */
	public function endCdata () {}

	/**
	 * Write full CDATA tag
	 * @link http://www.php.net/manual/en/xmlwriter.writecdata.php
	 * @param string $content The contents of the CDATA.
	 * @return bool true on success or false on failure
	 */
	public function writeCdata (string $content) {}

	/**
	 * Write text
	 * @link http://www.php.net/manual/en/xmlwriter.text.php
	 * @param string $content The contents of the text.
	 * The characters &lt;, &gt;,
	 * &amp; and " are written as
	 * entity references (i.e. &amp;lt;, &amp;gt;,
	 * &amp;amp; and &amp;quot;, respectively).
	 * All other characters including apos are written literally.
	 * To write the special XML characters literally, or to write literal entity references,
	 * xmlwriter_write_raw has to be used.
	 * @return bool true on success or false on failure
	 */
	public function text (string $content) {}

	/**
	 * Write a raw XML text
	 * @link http://www.php.net/manual/en/xmlwriter.writeraw.php
	 * @param string $content The text string to write.
	 * @return bool true on success or false on failure
	 */
	public function writeRaw (string $content) {}

	/**
	 * Create document tag
	 * @link http://www.php.net/manual/en/xmlwriter.startdocument.php
	 * @param mixed $version [optional] The version number of the document as part of the XML declaration.
	 * @param mixed $encoding [optional] The encoding of the document as part of the XML declaration.
	 * @param mixed $standalone [optional] yes or no.
	 * @return bool true on success or false on failure
	 */
	public function startDocument ($version = null, $encoding = null, $standalone = null) {}

	/**
	 * End current document
	 * @link http://www.php.net/manual/en/xmlwriter.enddocument.php
	 * @return bool true on success or false on failure
	 */
	public function endDocument () {}

	/**
	 * Write full comment tag
	 * @link http://www.php.net/manual/en/xmlwriter.writecomment.php
	 * @param string $content The contents of the comment.
	 * @return bool true on success or false on failure
	 */
	public function writeComment (string $content) {}

	/**
	 * Create start DTD tag
	 * @link http://www.php.net/manual/en/xmlwriter.startdtd.php
	 * @param string $qualifiedName The qualified name of the document type to create.
	 * @param mixed $publicId [optional] The external subset public identifier.
	 * @param mixed $systemId [optional] The external subset system identifier.
	 * @return bool true on success or false on failure
	 */
	public function startDtd (string $qualifiedName, $publicId = null, $systemId = null) {}

	/**
	 * End current DTD
	 * @link http://www.php.net/manual/en/xmlwriter.enddtd.php
	 * @return bool true on success or false on failure
	 */
	public function endDtd () {}

	/**
	 * Write full DTD tag
	 * @link http://www.php.net/manual/en/xmlwriter.writedtd.php
	 * @param string $name The DTD name.
	 * @param mixed $publicId [optional] The external subset public identifier.
	 * @param mixed $systemId [optional] The external subset system identifier.
	 * @param mixed $content [optional] The content of the DTD.
	 * @return bool true on success or false on failure
	 */
	public function writeDtd (string $name, $publicId = null, $systemId = null, $content = null) {}

	/**
	 * Create start DTD element
	 * @link http://www.php.net/manual/en/xmlwriter.startdtdelement.php
	 * @param string $qualifiedName The qualified name of the document type to create.
	 * @return bool true on success or false on failure
	 */
	public function startDtdElement (string $qualifiedName) {}

	/**
	 * End current DTD element
	 * @link http://www.php.net/manual/en/xmlwriter.enddtdelement.php
	 * @return bool true on success or false on failure
	 */
	public function endDtdElement () {}

	/**
	 * Write full DTD element tag
	 * @link http://www.php.net/manual/en/xmlwriter.writedtdelement.php
	 * @param string $name The name of the DTD element.
	 * @param string $content The content of the element.
	 * @return bool true on success or false on failure
	 */
	public function writeDtdElement (string $name, string $content) {}

	/**
	 * Create start DTD AttList
	 * @link http://www.php.net/manual/en/xmlwriter.startdtdattlist.php
	 * @param string $name The attribute list name.
	 * @return bool true on success or false on failure
	 */
	public function startDtdAttlist (string $name) {}

	/**
	 * End current DTD AttList
	 * @link http://www.php.net/manual/en/xmlwriter.enddtdattlist.php
	 * @return bool true on success or false on failure
	 */
	public function endDtdAttlist () {}

	/**
	 * Write full DTD AttList tag
	 * @link http://www.php.net/manual/en/xmlwriter.writedtdattlist.php
	 * @param string $name The name of the DTD attribute list.
	 * @param string $content The content of the DTD attribute list.
	 * @return bool true on success or false on failure
	 */
	public function writeDtdAttlist (string $name, string $content) {}

	/**
	 * Create start DTD Entity
	 * @link http://www.php.net/manual/en/xmlwriter.startdtdentity.php
	 * @param string $name The name of the entity.
	 * @param bool $isParam 
	 * @return bool true on success or false on failure
	 */
	public function startDtdEntity (string $name, bool $isParam) {}

	/**
	 * End current DTD Entity
	 * @link http://www.php.net/manual/en/xmlwriter.enddtdentity.php
	 * @return bool true on success or false on failure
	 */
	public function endDtdEntity () {}

	/**
	 * Write full DTD Entity tag
	 * @link http://www.php.net/manual/en/xmlwriter.writedtdentity.php
	 * @param string $name The name of the entity.
	 * @param string $content The content of the entity.
	 * @param bool $isParam [optional] 
	 * @param mixed $publicId [optional] 
	 * @param mixed $systemId [optional] 
	 * @param mixed $notationData [optional] 
	 * @return bool true on success or false on failure
	 */
	public function writeDtdEntity (string $name, string $content, bool $isParam = null, $publicId = null, $systemId = null, $notationData = null) {}

	/**
	 * Returns current buffer
	 * @link http://www.php.net/manual/en/xmlwriter.outputmemory.php
	 * @param bool $flush [optional] Whether to flush the output buffer or not. Default is true.
	 * @return string the current buffer as a string.
	 */
	public function outputMemory (bool $flush = null) {}

	/**
	 * Flush current buffer
	 * @link http://www.php.net/manual/en/xmlwriter.flush.php
	 * @param bool $empty [optional] Whether to empty the buffer or not. Default is true.
	 * @return mixed If you opened the writer in memory, this function returns the generated XML buffer,
	 * Else, if using URI, this function will write the buffer and return the number of 
	 * written bytes.
	 */
	public function flush (bool $empty = null) {}

}

/**
 * @param string $uri
 */
function xmlwriter_open_uri (string $uri): XMLWriter|false {}

function xmlwriter_open_memory (): XMLWriter|false {}

/**
 * @param XMLWriter $writer
 * @param bool $enable
 */
function xmlwriter_set_indent (XMLWriter $writerbool , $enable): bool {}

/**
 * @param XMLWriter $writer
 * @param string $indentation
 */
function xmlwriter_set_indent_string (XMLWriter $writerstring , $indentation): bool {}

/**
 * @param XMLWriter $writer
 */
function xmlwriter_start_comment (XMLWriter $writer): bool {}

/**
 * @param XMLWriter $writer
 */
function xmlwriter_end_comment (XMLWriter $writer): bool {}

/**
 * @param XMLWriter $writer
 * @param string $name
 */
function xmlwriter_start_attribute (XMLWriter $writerstring , $name): bool {}

/**
 * @param XMLWriter $writer
 */
function xmlwriter_end_attribute (XMLWriter $writer): bool {}

/**
 * @param XMLWriter $writer
 * @param string $name
 * @param string $value
 */
function xmlwriter_write_attribute (XMLWriter $writerstring , $namestring , $value): bool {}

/**
 * @param XMLWriter $writer
 * @param ?string|null $prefix
 * @param string $name
 * @param ?string|null $namespace
 */
function xmlwriter_start_attribute_ns (XMLWriter $writer?string|null , $prefix = nullstring , $name?string|null , $namespace = null): bool {}

/**
 * @param XMLWriter $writer
 * @param ?string|null $prefix
 * @param string $name
 * @param ?string|null $namespace
 * @param string $value
 */
function xmlwriter_write_attribute_ns (XMLWriter $writer?string|null , $prefix = nullstring , $name?string|null , $namespace = nullstring , $value): bool {}

/**
 * @param XMLWriter $writer
 * @param string $name
 */
function xmlwriter_start_element (XMLWriter $writerstring , $name): bool {}

/**
 * @param XMLWriter $writer
 */
function xmlwriter_end_element (XMLWriter $writer): bool {}

/**
 * @param XMLWriter $writer
 */
function xmlwriter_full_end_element (XMLWriter $writer): bool {}

/**
 * @param XMLWriter $writer
 * @param ?string|null $prefix
 * @param string $name
 * @param ?string|null $namespace
 */
function xmlwriter_start_element_ns (XMLWriter $writer?string|null , $prefix = nullstring , $name?string|null , $namespace = null): bool {}

/**
 * @param XMLWriter $writer
 * @param string $name
 * @param ?string|null $content [optional]
 */
function xmlwriter_write_element (XMLWriter $writerstring , $name?string|null , $content = null): bool {}

/**
 * @param XMLWriter $writer
 * @param ?string|null $prefix
 * @param string $name
 * @param ?string|null $namespace
 * @param ?string|null $content [optional]
 */
function xmlwriter_write_element_ns (XMLWriter $writer?string|null , $prefix = nullstring , $name?string|null , $namespace = null?string|null , $content = null): bool {}

/**
 * @param XMLWriter $writer
 * @param string $target
 */
function xmlwriter_start_pi (XMLWriter $writerstring , $target): bool {}

/**
 * @param XMLWriter $writer
 */
function xmlwriter_end_pi (XMLWriter $writer): bool {}

/**
 * @param XMLWriter $writer
 * @param string $target
 * @param string $content
 */
function xmlwriter_write_pi (XMLWriter $writerstring , $targetstring , $content): bool {}

/**
 * @param XMLWriter $writer
 */
function xmlwriter_start_cdata (XMLWriter $writer): bool {}

/**
 * @param XMLWriter $writer
 */
function xmlwriter_end_cdata (XMLWriter $writer): bool {}

/**
 * @param XMLWriter $writer
 * @param string $content
 */
function xmlwriter_write_cdata (XMLWriter $writerstring , $content): bool {}

/**
 * @param XMLWriter $writer
 * @param string $content
 */
function xmlwriter_text (XMLWriter $writerstring , $content): bool {}

/**
 * @param XMLWriter $writer
 * @param string $content
 */
function xmlwriter_write_raw (XMLWriter $writerstring , $content): bool {}

/**
 * @param XMLWriter $writer
 * @param ?string|null $version [optional]
 * @param ?string|null $encoding [optional]
 * @param ?string|null $standalone [optional]
 */
function xmlwriter_start_document (XMLWriter $writer?string|null , $version = null?string|null , $encoding = null?string|null , $standalone = null): bool {}

/**
 * @param XMLWriter $writer
 */
function xmlwriter_end_document (XMLWriter $writer): bool {}

/**
 * @param XMLWriter $writer
 * @param string $content
 */
function xmlwriter_write_comment (XMLWriter $writerstring , $content): bool {}

/**
 * @param XMLWriter $writer
 * @param string $qualifiedName
 * @param ?string|null $publicId [optional]
 * @param ?string|null $systemId [optional]
 */
function xmlwriter_start_dtd (XMLWriter $writerstring , $qualifiedName?string|null , $publicId = null?string|null , $systemId = null): bool {}

/**
 * @param XMLWriter $writer
 */
function xmlwriter_end_dtd (XMLWriter $writer): bool {}

/**
 * @param XMLWriter $writer
 * @param string $name
 * @param ?string|null $publicId [optional]
 * @param ?string|null $systemId [optional]
 * @param ?string|null $content [optional]
 */
function xmlwriter_write_dtd (XMLWriter $writerstring , $name?string|null , $publicId = null?string|null , $systemId = null?string|null , $content = null): bool {}

/**
 * @param XMLWriter $writer
 * @param string $qualifiedName
 */
function xmlwriter_start_dtd_element (XMLWriter $writerstring , $qualifiedName): bool {}

/**
 * @param XMLWriter $writer
 */
function xmlwriter_end_dtd_element (XMLWriter $writer): bool {}

/**
 * @param XMLWriter $writer
 * @param string $name
 * @param string $content
 */
function xmlwriter_write_dtd_element (XMLWriter $writerstring , $namestring , $content): bool {}

/**
 * @param XMLWriter $writer
 * @param string $name
 */
function xmlwriter_start_dtd_attlist (XMLWriter $writerstring , $name): bool {}

/**
 * @param XMLWriter $writer
 */
function xmlwriter_end_dtd_attlist (XMLWriter $writer): bool {}

/**
 * @param XMLWriter $writer
 * @param string $name
 * @param string $content
 */
function xmlwriter_write_dtd_attlist (XMLWriter $writerstring , $namestring , $content): bool {}

/**
 * @param XMLWriter $writer
 * @param string $name
 * @param bool $isParam
 */
function xmlwriter_start_dtd_entity (XMLWriter $writerstring , $namebool , $isParam): bool {}

/**
 * @param XMLWriter $writer
 */
function xmlwriter_end_dtd_entity (XMLWriter $writer): bool {}

/**
 * @param XMLWriter $writer
 * @param string $name
 * @param string $content
 * @param bool $isParam [optional]
 * @param ?string|null $publicId [optional]
 * @param ?string|null $systemId [optional]
 * @param ?string|null $notationData [optional]
 */
function xmlwriter_write_dtd_entity (XMLWriter $writerstring , $namestring , $contentbool , $isParam = ''?string|null , $publicId = null?string|null , $systemId = null?string|null , $notationData = null): bool {}

/**
 * @param XMLWriter $writer
 * @param bool $flush [optional]
 */
function xmlwriter_output_memory (XMLWriter $writerbool , $flush = 1): string {}

/**
 * @param XMLWriter $writer
 * @param bool $empty [optional]
 */
function xmlwriter_flush (XMLWriter $writerbool , $empty = 1): string|int {}

// End of xmlwriter v.8.2.6
