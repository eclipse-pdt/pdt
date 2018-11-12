<?php

// Start of xmlwriter v.7.3.0

class XMLWriter  {

	/**
	 * Create new xmlwriter using source uri for output
	 * @link http://www.php.net/manual/en/function.xmlwriter-open-uri.php
	 * @param string $uri The URI of the resource for the output.
	 * @return bool Object oriented style: Returns true on success or false on failure
	 * <p>
	 * Procedural style: Returns a new xmlwriter resource for later use with the
	 * xmlwriter functions on success, false on error.
	 * </p>
	 */
	public function openUri (string $uri) {}

	/**
	 * Create new xmlwriter using memory for string output
	 * @link http://www.php.net/manual/en/function.xmlwriter-open-memory.php
	 * @return bool Object oriented style: Returns true on success or false on failure
	 * <p>
	 * Procedural style: Returns a new xmlwriter resource for later use with the
	 * xmlwriter functions on success, false on error.
	 * </p>
	 */
	public function openMemory () {}

	/**
	 * Toggle indentation on/off
	 * @link http://www.php.net/manual/en/function.xmlwriter-set-indent.php
	 * @param bool $indent Whether indentation is enabled.
	 * @return bool true on success or false on failure
	 */
	public function setIndent (bool $indent) {}

	/**
	 * Set string used for indenting
	 * @link http://www.php.net/manual/en/function.xmlwriter-set-indent-string.php
	 * @param string $indentString The indentation string.
	 * @return bool true on success or false on failure
	 */
	public function setIndentString (string $indentString) {}

	/**
	 * Create start comment
	 * @link http://www.php.net/manual/en/function.xmlwriter-start-comment.php
	 * @return bool true on success or false on failure
	 */
	public function startComment () {}

	/**
	 * Create end comment
	 * @link http://www.php.net/manual/en/function.xmlwriter-end-comment.php
	 * @return bool true on success or false on failure
	 */
	public function endComment () {}

	/**
	 * Create start attribute
	 * @link http://www.php.net/manual/en/function.xmlwriter-start-attribute.php
	 * @param string $name The attribute name.
	 * @return bool true on success or false on failure
	 */
	public function startAttribute (string $name) {}

	/**
	 * End attribute
	 * @link http://www.php.net/manual/en/function.xmlwriter-end-attribute.php
	 * @return bool true on success or false on failure
	 */
	public function endAttribute () {}

	/**
	 * Write full attribute
	 * @link http://www.php.net/manual/en/function.xmlwriter-write-attribute.php
	 * @param string $name The name of the attribute.
	 * @param string $value The value of the attribute.
	 * @return bool true on success or false on failure
	 */
	public function writeAttribute (string $name, string $value) {}

	/**
	 * Create start namespaced attribute
	 * @link http://www.php.net/manual/en/function.xmlwriter-start-attribute-ns.php
	 * @param string $prefix The namespace prefix.
	 * @param string $name The attribute name.
	 * @param string $uri The namespace URI.
	 * @return bool true on success or false on failure
	 */
	public function startAttributeNs (string $prefix, string $name, string $uri) {}

	/**
	 * Write full namespaced attribute
	 * @link http://www.php.net/manual/en/function.xmlwriter-write-attribute-ns.php
	 * @param string $prefix The namespace prefix.
	 * @param string $name The attribute name.
	 * @param string $uri The namespace URI.
	 * @param string $content The attribute value.
	 * @return bool true on success or false on failure
	 */
	public function writeAttributeNs (string $prefix, string $name, string $uri, string $content) {}

	/**
	 * Create start element tag
	 * @link http://www.php.net/manual/en/function.xmlwriter-start-element.php
	 * @param string $name The element name.
	 * @return bool true on success or false on failure
	 */
	public function startElement (string $name) {}

	/**
	 * End current element
	 * @link http://www.php.net/manual/en/function.xmlwriter-end-element.php
	 * @return bool true on success or false on failure
	 */
	public function endElement () {}

	/**
	 * End current element
	 * @link http://www.php.net/manual/en/function.xmlwriter-full-end-element.php
	 * @return bool true on success or false on failure
	 */
	public function fullEndElement () {}

	/**
	 * Create start namespaced element tag
	 * @link http://www.php.net/manual/en/function.xmlwriter-start-element-ns.php
	 * @param string $prefix The namespace prefix.
	 * @param string $name The element name.
	 * @param string $uri The namespace URI.
	 * @return bool true on success or false on failure
	 */
	public function startElementNs (string $prefix, string $name, string $uri) {}

	/**
	 * Write full element tag
	 * @link http://www.php.net/manual/en/function.xmlwriter-write-element.php
	 * @param string $name The element name.
	 * @param string $content [optional] The element contents.
	 * @return bool true on success or false on failure
	 */
	public function writeElement (string $name, string $content = null) {}

	/**
	 * Write full namespaced element tag
	 * @link http://www.php.net/manual/en/function.xmlwriter-write-element-ns.php
	 * @param string $prefix The namespace prefix.
	 * @param string $name The element name.
	 * @param string $uri The namespace URI.
	 * @param string $content [optional] The element contents.
	 * @return bool true on success or false on failure
	 */
	public function writeElementNs (string $prefix, string $name, string $uri, string $content = null) {}

	/**
	 * Create start PI tag
	 * @link http://www.php.net/manual/en/function.xmlwriter-start-pi.php
	 * @param string $target The target of the processing instruction.
	 * @return bool true on success or false on failure
	 */
	public function startPi (string $target) {}

	/**
	 * End current PI
	 * @link http://www.php.net/manual/en/function.xmlwriter-end-pi.php
	 * @return bool true on success or false on failure
	 */
	public function endPi () {}

	/**
	 * Writes a PI
	 * @link http://www.php.net/manual/en/function.xmlwriter-write-pi.php
	 * @param string $target The target of the processing instruction.
	 * @param string $content The content of the processing instruction.
	 * @return bool true on success or false on failure
	 */
	public function writePi (string $target, string $content) {}

	/**
	 * Create start CDATA tag
	 * @link http://www.php.net/manual/en/function.xmlwriter-start-cdata.php
	 * @return bool true on success or false on failure
	 */
	public function startCdata () {}

	/**
	 * End current CDATA
	 * @link http://www.php.net/manual/en/function.xmlwriter-end-cdata.php
	 * @return bool true on success or false on failure
	 */
	public function endCdata () {}

	/**
	 * Write full CDATA tag
	 * @link http://www.php.net/manual/en/function.xmlwriter-write-cdata.php
	 * @param string $content The contents of the CDATA.
	 * @return bool true on success or false on failure
	 */
	public function writeCdata (string $content) {}

	/**
	 * Write text
	 * @link http://www.php.net/manual/en/function.xmlwriter-text.php
	 * @param string $content The contents of the text.
	 * @return bool true on success or false on failure
	 */
	public function text (string $content) {}

	/**
	 * Write a raw XML text
	 * @link http://www.php.net/manual/en/function.xmlwriter-write-raw.php
	 * @param string $content The text string to write.
	 * @return bool true on success or false on failure
	 */
	public function writeRaw (string $content) {}

	/**
	 * Create document tag
	 * @link http://www.php.net/manual/en/function.xmlwriter-start-document.php
	 * @param string $version [optional] The version number of the document as part of the XML declaration.
	 * @param string $encoding [optional] The encoding of the document as part of the XML declaration.
	 * @param string $standalone [optional] yes or no.
	 * @return bool true on success or false on failure
	 */
	public function startDocument (string $version = null, string $encoding = null, string $standalone = null) {}

	/**
	 * End current document
	 * @link http://www.php.net/manual/en/function.xmlwriter-end-document.php
	 * @return bool true on success or false on failure
	 */
	public function endDocument () {}

	/**
	 * Write full comment tag
	 * @link http://www.php.net/manual/en/function.xmlwriter-write-comment.php
	 * @param string $content The contents of the comment.
	 * @return bool true on success or false on failure
	 */
	public function writeComment (string $content) {}

	/**
	 * Create start DTD tag
	 * @link http://www.php.net/manual/en/function.xmlwriter-start-dtd.php
	 * @param string $qualifiedName The qualified name of the document type to create.
	 * @param string $publicId [optional] The external subset public identifier.
	 * @param string $systemId [optional] The external subset system identifier.
	 * @return bool true on success or false on failure
	 */
	public function startDtd (string $qualifiedName, string $publicId = null, string $systemId = null) {}

	/**
	 * End current DTD
	 * @link http://www.php.net/manual/en/function.xmlwriter-end-dtd.php
	 * @return bool true on success or false on failure
	 */
	public function endDtd () {}

	/**
	 * Write full DTD tag
	 * @link http://www.php.net/manual/en/function.xmlwriter-write-dtd.php
	 * @param string $name The DTD name.
	 * @param string $publicId [optional] The external subset public identifier.
	 * @param string $systemId [optional] The external subset system identifier.
	 * @param string $subset [optional] The content of the DTD.
	 * @return bool true on success or false on failure
	 */
	public function writeDtd (string $name, string $publicId = null, string $systemId = null, string $subset = null) {}

	/**
	 * Create start DTD element
	 * @link http://www.php.net/manual/en/function.xmlwriter-start-dtd-element.php
	 * @param string $qualifiedName The qualified name of the document type to create.
	 * @return bool true on success or false on failure
	 */
	public function startDtdElement (string $qualifiedName) {}

	/**
	 * End current DTD element
	 * @link http://www.php.net/manual/en/function.xmlwriter-end-dtd-element.php
	 * @return bool true on success or false on failure
	 */
	public function endDtdElement () {}

	/**
	 * Write full DTD element tag
	 * @link http://www.php.net/manual/en/function.xmlwriter-write-dtd-element.php
	 * @param string $name The name of the DTD element.
	 * @param string $content The content of the element.
	 * @return bool true on success or false on failure
	 */
	public function writeDtdElement (string $name, string $content) {}

	/**
	 * Create start DTD AttList
	 * @link http://www.php.net/manual/en/function.xmlwriter-start-dtd-attlist.php
	 * @param string $name The attribute list name.
	 * @return bool true on success or false on failure
	 */
	public function startDtdAttlist (string $name) {}

	/**
	 * End current DTD AttList
	 * @link http://www.php.net/manual/en/function.xmlwriter-end-dtd-attlist.php
	 * @return bool true on success or false on failure
	 */
	public function endDtdAttlist () {}

	/**
	 * Write full DTD AttList tag
	 * @link http://www.php.net/manual/en/function.xmlwriter-write-dtd-attlist.php
	 * @param string $name The name of the DTD attribute list.
	 * @param string $content The content of the DTD attribute list.
	 * @return bool true on success or false on failure
	 */
	public function writeDtdAttlist (string $name, string $content) {}

	/**
	 * Create start DTD Entity
	 * @link http://www.php.net/manual/en/function.xmlwriter-start-dtd-entity.php
	 * @param string $name The name of the entity.
	 * @param bool $isparam 
	 * @return bool true on success or false on failure
	 */
	public function startDtdEntity (string $name, bool $isparam) {}

	/**
	 * End current DTD Entity
	 * @link http://www.php.net/manual/en/function.xmlwriter-end-dtd-entity.php
	 * @return bool true on success or false on failure
	 */
	public function endDtdEntity () {}

	/**
	 * Write full DTD Entity tag
	 * @link http://www.php.net/manual/en/function.xmlwriter-write-dtd-entity.php
	 * @param string $name The name of the entity.
	 * @param string $content The content of the entity.
	 * @param bool $pe 
	 * @param string $pubid 
	 * @param string $sysid 
	 * @param string $ndataid 
	 * @return bool true on success or false on failure
	 */
	public function writeDtdEntity (string $name, string $content, bool $pe, string $pubid, string $sysid, string $ndataid) {}

	/**
	 * Returns current buffer
	 * @link http://www.php.net/manual/en/function.xmlwriter-output-memory.php
	 * @param bool $flush [optional] Whether to flush the output buffer or not. Default is true.
	 * @return string the current buffer as a string.
	 */
	public function outputMemory (bool $flush = null) {}

	/**
	 * Flush current buffer
	 * @link http://www.php.net/manual/en/function.xmlwriter-flush.php
	 * @param bool $empty [optional] Whether to empty the buffer or not. Default is true.
	 * @return mixed If you opened the writer in memory, this function returns the generated XML buffer,
	 * Else, if using URI, this function will write the buffer and return the number of 
	 * written bytes.
	 */
	public function flush (bool $empty = null) {}

}

/**
 * @param mixed $uri
 */
function xmlwriter_open_uri ($uri) {}

function xmlwriter_open_memory () {}

/**
 * @param mixed $xmlwriter
 * @param mixed $indent
 */
function xmlwriter_set_indent ($xmlwriter, $indent) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $indentString
 */
function xmlwriter_set_indent_string ($xmlwriter, $indentString) {}

/**
 * @param mixed $xmlwriter
 */
function xmlwriter_start_comment ($xmlwriter) {}

/**
 * @param mixed $xmlwriter
 */
function xmlwriter_end_comment ($xmlwriter) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $name
 */
function xmlwriter_start_attribute ($xmlwriter, $name) {}

/**
 * @param mixed $xmlwriter
 */
function xmlwriter_end_attribute ($xmlwriter) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $name
 * @param mixed $value
 */
function xmlwriter_write_attribute ($xmlwriter, $name, $value) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $prefix
 * @param mixed $name
 * @param mixed $uri
 */
function xmlwriter_start_attribute_ns ($xmlwriter, $prefix, $name, $uri) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $prefix
 * @param mixed $name
 * @param mixed $uri
 * @param mixed $content
 */
function xmlwriter_write_attribute_ns ($xmlwriter, $prefix, $name, $uri, $content) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $name
 */
function xmlwriter_start_element ($xmlwriter, $name) {}

/**
 * @param mixed $xmlwriter
 */
function xmlwriter_end_element ($xmlwriter) {}

/**
 * @param mixed $xmlwriter
 */
function xmlwriter_full_end_element ($xmlwriter) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $prefix
 * @param mixed $name
 * @param mixed $uri
 */
function xmlwriter_start_element_ns ($xmlwriter, $prefix, $name, $uri) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $name
 * @param mixed $content [optional]
 */
function xmlwriter_write_element ($xmlwriter, $name, $content = null) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $prefix
 * @param mixed $name
 * @param mixed $uri
 * @param mixed $content [optional]
 */
function xmlwriter_write_element_ns ($xmlwriter, $prefix, $name, $uri, $content = null) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $target
 */
function xmlwriter_start_pi ($xmlwriter, $target) {}

/**
 * @param mixed $xmlwriter
 */
function xmlwriter_end_pi ($xmlwriter) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $target
 * @param mixed $content
 */
function xmlwriter_write_pi ($xmlwriter, $target, $content) {}

/**
 * @param mixed $xmlwriter
 */
function xmlwriter_start_cdata ($xmlwriter) {}

/**
 * @param mixed $xmlwriter
 */
function xmlwriter_end_cdata ($xmlwriter) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $content
 */
function xmlwriter_write_cdata ($xmlwriter, $content) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $content
 */
function xmlwriter_text ($xmlwriter, $content) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $content
 */
function xmlwriter_write_raw ($xmlwriter, $content) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $version [optional]
 * @param mixed $encoding [optional]
 * @param mixed $standalone [optional]
 */
function xmlwriter_start_document ($xmlwriter, $version = null, $encoding = null, $standalone = null) {}

/**
 * @param mixed $xmlwriter
 */
function xmlwriter_end_document ($xmlwriter) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $content
 */
function xmlwriter_write_comment ($xmlwriter, $content) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $qualifiedName
 * @param mixed $publicId [optional]
 * @param mixed $systemId [optional]
 */
function xmlwriter_start_dtd ($xmlwriter, $qualifiedName, $publicId = null, $systemId = null) {}

/**
 * @param mixed $xmlwriter
 */
function xmlwriter_end_dtd ($xmlwriter) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $name
 * @param mixed $publicId [optional]
 * @param mixed $systemId [optional]
 * @param mixed $subset [optional]
 */
function xmlwriter_write_dtd ($xmlwriter, $name, $publicId = null, $systemId = null, $subset = null) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $qualifiedName
 */
function xmlwriter_start_dtd_element ($xmlwriter, $qualifiedName) {}

/**
 * @param mixed $xmlwriter
 */
function xmlwriter_end_dtd_element ($xmlwriter) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $name
 * @param mixed $content
 */
function xmlwriter_write_dtd_element ($xmlwriter, $name, $content) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $name
 */
function xmlwriter_start_dtd_attlist ($xmlwriter, $name) {}

/**
 * @param mixed $xmlwriter
 */
function xmlwriter_end_dtd_attlist ($xmlwriter) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $name
 * @param mixed $content
 */
function xmlwriter_write_dtd_attlist ($xmlwriter, $name, $content) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $name
 * @param mixed $isparam
 */
function xmlwriter_start_dtd_entity ($xmlwriter, $name, $isparam) {}

/**
 * @param mixed $xmlwriter
 */
function xmlwriter_end_dtd_entity ($xmlwriter) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $name
 * @param mixed $content
 */
function xmlwriter_write_dtd_entity ($xmlwriter, $name, $content) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $flush [optional]
 */
function xmlwriter_output_memory ($xmlwriter, $flush = null) {}

/**
 * @param mixed $xmlwriter
 * @param mixed $empty [optional]
 */
function xmlwriter_flush ($xmlwriter, $empty = null) {}

// End of xmlwriter v.7.3.0
