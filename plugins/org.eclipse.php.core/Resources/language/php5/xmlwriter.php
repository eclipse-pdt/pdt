<?php

// Start of xmlwriter v.0.1

/**
 * @link http://php.net/manual/en/ref.xmlwriter.php
 */
class XMLWriter  {

	/**
	 * Create new xmlwriter using source uri for output
	 * @link http://php.net/manual/en/function.xmlwriter-open-uri.php
	 * @param uri string
	 * @return bool 
	 */
	public function openUri ($uri) {}

	/**
	 * Create new xmlwriter using memory for string output
	 * @link http://php.net/manual/en/function.xmlwriter-open-memory.php
	 * @return bool 
	 */
	public function openMemory () {}

	/**
	 * Toggle indentation on/off
	 * @link http://php.net/manual/en/function.xmlwriter-set-indent.php
	 * @param indent bool
	 * @return bool 
	 */
	public function setIndent ($indent) {}

	/**
	 * Set string used for indenting
	 * @link http://php.net/manual/en/function.xmlwriter-set-indent-string.php
	 * @param indentString string
	 * @return bool 
	 */
	public function setIndentString ($indentString) {}

	/**
	 * Create start comment
	 * @link http://php.net/manual/en/function.xmlwriter-start-comment.php
	 * @return bool 
	 */
	public function startComment () {}

	/**
	 * Create end comment
	 * @link http://php.net/manual/en/function.xmlwriter-end-comment.php
	 * @return bool 
	 */
	public function endComment () {}

	/**
	 * Create start attribute
	 * @link http://php.net/manual/en/function.xmlwriter-start-attribute.php
	 * @param name string
	 * @return bool 
	 */
	public function startAttribute ($name) {}

	/**
	 * End attribute
	 * @link http://php.net/manual/en/function.xmlwriter-end-attribute.php
	 * @return bool 
	 */
	public function endAttribute () {}

	/**
	 * Write full attribute
	 * @link http://php.net/manual/en/function.xmlwriter-write-attribute.php
	 * @param name string
	 * @param value string
	 * @return bool 
	 */
	public function writeAttribute ($name, $value) {}

	/**
	 * Create start namespaced attribute
	 * @link http://php.net/manual/en/function.xmlwriter-start-attribute-ns.php
	 * @param prefix string
	 * @param name string
	 * @param uri string
	 * @return bool 
	 */
	public function startAttributeNs ($prefix, $name, $uri) {}

	/**
	 * Write full namespaced attribute
	 * @link http://php.net/manual/en/function.xmlwriter-write-attribute-ns.php
	 * @param prefix string
	 * @param name string
	 * @param uri string
	 * @param content string
	 * @return bool 
	 */
	public function writeAttributeNs ($prefix, $name, $uri, $content) {}

	/**
	 * Create start element tag
	 * @link http://php.net/manual/en/function.xmlwriter-start-element.php
	 * @param name string
	 * @return bool 
	 */
	public function startElement ($name) {}

	/**
	 * End current element
	 * @link http://php.net/manual/en/function.xmlwriter-end-element.php
	 * @return bool 
	 */
	public function endElement () {}

	/**
	 * End current element
	 * @link http://php.net/manual/en/function.xmlwriter-full-end-element.php
	 * @return bool 
	 */
	public function fullEndElement () {}

	/**
	 * Create start namespaced element tag
	 * @link http://php.net/manual/en/function.xmlwriter-start-element-ns.php
	 * @param prefix string
	 * @param name string
	 * @param uri string
	 * @return bool 
	 */
	public function startElementNs ($prefix, $name, $uri) {}

	/**
	 * Write full element tag
	 * @link http://php.net/manual/en/function.xmlwriter-write-element.php
	 * @param name string
	 * @param content string[optional]
	 * @return bool 
	 */
	public function writeElement ($name, $content = null) {}

	/**
	 * Write full namesapced element tag
	 * @link http://php.net/manual/en/function.xmlwriter-write-element-ns.php
	 * @param prefix string
	 * @param name string
	 * @param uri string
	 * @param content string[optional]
	 * @return bool 
	 */
	public function writeElementNs ($prefix, $name, $uri, $content = null) {}

	/**
	 * Create start PI tag
	 * @link http://php.net/manual/en/function.xmlwriter-start-pi.php
	 * @param target string
	 * @return bool 
	 */
	public function startPi ($target) {}

	/**
	 * End current PI
	 * @link http://php.net/manual/en/function.xmlwriter-end-pi.php
	 * @return bool 
	 */
	public function endPi () {}

	/**
	 * Writes a PI
	 * @link http://php.net/manual/en/function.xmlwriter-write-pi.php
	 * @param target string
	 * @param content string
	 * @return bool 
	 */
	public function writePi ($target, $content) {}

	/**
	 * Create start CDATA tag
	 * @link http://php.net/manual/en/function.xmlwriter-start-cdata.php
	 * @return bool 
	 */
	public function startCdata () {}

	/**
	 * End current CDATA
	 * @link http://php.net/manual/en/function.xmlwriter-end-cdata.php
	 * @return bool 
	 */
	public function endCdata () {}

	/**
	 * Write full CDATA tag
	 * @link http://php.net/manual/en/function.xmlwriter-write-cdata.php
	 * @param content string
	 * @return bool 
	 */
	public function writeCdata ($content) {}

	/**
	 * Write text
	 * @link http://php.net/manual/en/function.xmlwriter-text.php
	 * @param content string
	 * @return bool 
	 */
	public function text ($content) {}

	/**
	 * Write a raw XML text
	 * @link http://php.net/manual/en/function.xmlwriter-write-raw.php
	 * @param content string
	 * @return bool 
	 */
	public function writeRaw ($content) {}

	/**
	 * Create document tag
	 * @link http://php.net/manual/en/function.xmlwriter-start-document.php
	 * @param version string[optional]
	 * @param encoding string[optional]
	 * @param standalone string[optional]
	 * @return bool 
	 */
	public function startDocument ($version = null, $encoding = null, $standalone = null) {}

	/**
	 * End current document
	 * @link http://php.net/manual/en/function.xmlwriter-end-document.php
	 * @return bool 
	 */
	public function endDocument () {}

	/**
	 * Write full comment tag
	 * @link http://php.net/manual/en/function.xmlwriter-write-comment.php
	 * @param content string
	 * @return bool 
	 */
	public function writeComment ($content) {}

	/**
	 * Create start DTD tag
	 * @link http://php.net/manual/en/function.xmlwriter-start-dtd.php
	 * @param qualifiedName string
	 * @param publicId string[optional]
	 * @param systemId string[optional]
	 * @return bool 
	 */
	public function startDtd ($qualifiedName, $publicId = null, $systemId = null) {}

	/**
	 * End current DTD
	 * @link http://php.net/manual/en/function.xmlwriter-end-dtd.php
	 * @return bool 
	 */
	public function endDtd () {}

	/**
	 * Write full DTD tag
	 * @link http://php.net/manual/en/function.xmlwriter-write-dtd.php
	 * @param name string
	 * @param publicId string[optional]
	 * @param systemId string[optional]
	 * @param subset string[optional]
	 * @return bool 
	 */
	public function writeDtd ($name, $publicId = null, $systemId = null, $subset = null) {}

	/**
	 * Create start DTD element
	 * @link http://php.net/manual/en/function.xmlwriter-start-dtd-element.php
	 * @param qualifiedName string
	 * @return bool 
	 */
	public function startDtdElement ($qualifiedName) {}

	/**
	 * End current DTD element
	 * @link http://php.net/manual/en/function.xmlwriter-end-dtd-element.php
	 * @return bool 
	 */
	public function endDtdElement () {}

	/**
	 * Write full DTD element tag
	 * @link http://php.net/manual/en/function.xmlwriter-write-dtd-element.php
	 * @param name string
	 * @param content string
	 * @return bool 
	 */
	public function writeDtdElement ($name, $content) {}

	/**
	 * Create start DTD AttList
	 * @link http://php.net/manual/en/function.xmlwriter-start-dtd-attlist.php
	 * @param name string
	 * @return bool 
	 */
	public function startDtdAttlist ($name) {}

	/**
	 * End current DTD AttList
	 * @link http://php.net/manual/en/function.xmlwriter-end-dtd-attlist.php
	 * @return bool 
	 */
	public function endDtdAttlist () {}

	/**
	 * Write full DTD AttList tag
	 * @link http://php.net/manual/en/function.xmlwriter-write-dtd-attlist.php
	 * @param name string
	 * @param content string
	 * @return bool 
	 */
	public function writeDtdAttlist ($name, $content) {}

	/**
	 * Create start DTD Entity
	 * @link http://php.net/manual/en/function.xmlwriter-start-dtd-entity.php
	 * @param name string
	 * @param isparam bool
	 * @return bool 
	 */
	public function startDtdEntity ($name, $isparam) {}

	/**
	 * End current DTD Entity
	 * @link http://php.net/manual/en/function.xmlwriter-end-dtd-entity.php
	 * @return bool 
	 */
	public function endDtdEntity () {}

	/**
	 * Write full DTD Entity tag
	 * @link http://php.net/manual/en/function.xmlwriter-write-dtd-entity.php
	 * @param name string
	 * @param content string
	 * @return bool 
	 */
	public function writeDtdEntity ($name, $content) {}

	/**
	 * Returns current buffer
	 * @link http://php.net/manual/en/function.xmlwriter-output-memory.php
	 * @param flush bool[optional]
	 * @return bool the current buffer as a string.
	 */
	public function outputMemory ($flush = null) {}

	/**
	 * Flush current buffer
	 * @link http://php.net/manual/en/function.xmlwriter-flush.php
	 * @param empty bool[optional]
	 * @return mixed 
	 */
	public function flush ($empty = null) {}

}

/**
 * Create new xmlwriter using source uri for output
 * @link http://php.net/manual/en/function.xmlwriter-open-uri.php
 * @param uri string
 * @return bool 
 */
function xmlwriter_open_uri ($uri) {}

/**
 * Create new xmlwriter using memory for string output
 * @link http://php.net/manual/en/function.xmlwriter-open-memory.php
 * @return bool 
 */
function xmlwriter_open_memory () {}

/**
 * Toggle indentation on/off
 * @link http://php.net/manual/en/function.xmlwriter-set-indent.php
 * @param indent bool
 * @return bool 
 */
function xmlwriter_set_indent ($indent) {}

/**
 * Set string used for indenting
 * @link http://php.net/manual/en/function.xmlwriter-set-indent-string.php
 * @param indentString string
 * @return bool 
 */
function xmlwriter_set_indent_string ($indentString) {}

/**
 * Create start comment
 * @link http://php.net/manual/en/function.xmlwriter-start-comment.php
 * @return bool 
 */
function xmlwriter_start_comment () {}

/**
 * Create end comment
 * @link http://php.net/manual/en/function.xmlwriter-end-comment.php
 * @return bool 
 */
function xmlwriter_end_comment () {}

/**
 * Create start attribute
 * @link http://php.net/manual/en/function.xmlwriter-start-attribute.php
 * @param name string
 * @return bool 
 */
function xmlwriter_start_attribute ($name) {}

/**
 * End attribute
 * @link http://php.net/manual/en/function.xmlwriter-end-attribute.php
 * @return bool 
 */
function xmlwriter_end_attribute () {}

/**
 * Write full attribute
 * @link http://php.net/manual/en/function.xmlwriter-write-attribute.php
 * @param name string
 * @param value string
 * @return bool 
 */
function xmlwriter_write_attribute ($name, $value) {}

/**
 * Create start namespaced attribute
 * @link http://php.net/manual/en/function.xmlwriter-start-attribute-ns.php
 * @param prefix string
 * @param name string
 * @param uri string
 * @return bool 
 */
function xmlwriter_start_attribute_ns ($prefix, $name, $uri) {}

/**
 * Write full namespaced attribute
 * @link http://php.net/manual/en/function.xmlwriter-write-attribute-ns.php
 * @param prefix string
 * @param name string
 * @param uri string
 * @param content string
 * @return bool 
 */
function xmlwriter_write_attribute_ns ($prefix, $name, $uri, $content) {}

/**
 * Create start element tag
 * @link http://php.net/manual/en/function.xmlwriter-start-element.php
 * @param name string
 * @return bool 
 */
function xmlwriter_start_element ($name) {}

/**
 * End current element
 * @link http://php.net/manual/en/function.xmlwriter-end-element.php
 * @return bool 
 */
function xmlwriter_end_element () {}

/**
 * End current element
 * @link http://php.net/manual/en/function.xmlwriter-full-end-element.php
 * @return bool 
 */
function xmlwriter_full_end_element () {}

/**
 * Create start namespaced element tag
 * @link http://php.net/manual/en/function.xmlwriter-start-element-ns.php
 * @param prefix string
 * @param name string
 * @param uri string
 * @return bool 
 */
function xmlwriter_start_element_ns ($prefix, $name, $uri) {}

/**
 * Write full element tag
 * @link http://php.net/manual/en/function.xmlwriter-write-element.php
 * @param name string
 * @param content string[optional]
 * @return bool 
 */
function xmlwriter_write_element ($name, $content = null) {}

/**
 * Write full namesapced element tag
 * @link http://php.net/manual/en/function.xmlwriter-write-element-ns.php
 * @param prefix string
 * @param name string
 * @param uri string
 * @param content string[optional]
 * @return bool 
 */
function xmlwriter_write_element_ns ($prefix, $name, $uri, $content = null) {}

/**
 * Create start PI tag
 * @link http://php.net/manual/en/function.xmlwriter-start-pi.php
 * @param target string
 * @return bool 
 */
function xmlwriter_start_pi ($target) {}

/**
 * End current PI
 * @link http://php.net/manual/en/function.xmlwriter-end-pi.php
 * @return bool 
 */
function xmlwriter_end_pi () {}

/**
 * Writes a PI
 * @link http://php.net/manual/en/function.xmlwriter-write-pi.php
 * @param target string
 * @param content string
 * @return bool 
 */
function xmlwriter_write_pi ($target, $content) {}

/**
 * Create start CDATA tag
 * @link http://php.net/manual/en/function.xmlwriter-start-cdata.php
 * @return bool 
 */
function xmlwriter_start_cdata () {}

/**
 * End current CDATA
 * @link http://php.net/manual/en/function.xmlwriter-end-cdata.php
 * @return bool 
 */
function xmlwriter_end_cdata () {}

/**
 * Write full CDATA tag
 * @link http://php.net/manual/en/function.xmlwriter-write-cdata.php
 * @param content string
 * @return bool 
 */
function xmlwriter_write_cdata ($content) {}

/**
 * Write text
 * @link http://php.net/manual/en/function.xmlwriter-text.php
 * @param content string
 * @return bool 
 */
function xmlwriter_text ($content) {}

/**
 * Write a raw XML text
 * @link http://php.net/manual/en/function.xmlwriter-write-raw.php
 * @param content string
 * @return bool 
 */
function xmlwriter_write_raw ($content) {}

/**
 * Create document tag
 * @link http://php.net/manual/en/function.xmlwriter-start-document.php
 * @param version string[optional]
 * @param encoding string[optional]
 * @param standalone string[optional]
 * @return bool 
 */
function xmlwriter_start_document ($version = null, $encoding = null, $standalone = null) {}

/**
 * End current document
 * @link http://php.net/manual/en/function.xmlwriter-end-document.php
 * @return bool 
 */
function xmlwriter_end_document () {}

/**
 * Write full comment tag
 * @link http://php.net/manual/en/function.xmlwriter-write-comment.php
 * @param content string
 * @return bool 
 */
function xmlwriter_write_comment ($content) {}

/**
 * Create start DTD tag
 * @link http://php.net/manual/en/function.xmlwriter-start-dtd.php
 * @param qualifiedName string
 * @param publicId string[optional]
 * @param systemId string[optional]
 * @return bool 
 */
function xmlwriter_start_dtd ($qualifiedName, $publicId = null, $systemId = null) {}

/**
 * End current DTD
 * @link http://php.net/manual/en/function.xmlwriter-end-dtd.php
 * @return bool 
 */
function xmlwriter_end_dtd () {}

/**
 * Write full DTD tag
 * @link http://php.net/manual/en/function.xmlwriter-write-dtd.php
 * @param name string
 * @param publicId string[optional]
 * @param systemId string[optional]
 * @param subset string[optional]
 * @return bool 
 */
function xmlwriter_write_dtd ($name, $publicId = null, $systemId = null, $subset = null) {}

/**
 * Create start DTD element
 * @link http://php.net/manual/en/function.xmlwriter-start-dtd-element.php
 * @param qualifiedName string
 * @return bool 
 */
function xmlwriter_start_dtd_element ($qualifiedName) {}

/**
 * End current DTD element
 * @link http://php.net/manual/en/function.xmlwriter-end-dtd-element.php
 * @return bool 
 */
function xmlwriter_end_dtd_element () {}

/**
 * Write full DTD element tag
 * @link http://php.net/manual/en/function.xmlwriter-write-dtd-element.php
 * @param name string
 * @param content string
 * @return bool 
 */
function xmlwriter_write_dtd_element ($name, $content) {}

/**
 * Create start DTD AttList
 * @link http://php.net/manual/en/function.xmlwriter-start-dtd-attlist.php
 * @param name string
 * @return bool 
 */
function xmlwriter_start_dtd_attlist ($name) {}

/**
 * End current DTD AttList
 * @link http://php.net/manual/en/function.xmlwriter-end-dtd-attlist.php
 * @return bool 
 */
function xmlwriter_end_dtd_attlist () {}

/**
 * Write full DTD AttList tag
 * @link http://php.net/manual/en/function.xmlwriter-write-dtd-attlist.php
 * @param name string
 * @param content string
 * @return bool 
 */
function xmlwriter_write_dtd_attlist ($name, $content) {}

/**
 * Create start DTD Entity
 * @link http://php.net/manual/en/function.xmlwriter-start-dtd-entity.php
 * @param name string
 * @param isparam bool
 * @return bool 
 */
function xmlwriter_start_dtd_entity ($name, $isparam) {}

/**
 * End current DTD Entity
 * @link http://php.net/manual/en/function.xmlwriter-end-dtd-entity.php
 * @return bool 
 */
function xmlwriter_end_dtd_entity () {}

/**
 * Write full DTD Entity tag
 * @link http://php.net/manual/en/function.xmlwriter-write-dtd-entity.php
 * @param name string
 * @param content string
 * @return bool 
 */
function xmlwriter_write_dtd_entity ($name, $content) {}

/**
 * Returns current buffer
 * @link http://php.net/manual/en/function.xmlwriter-output-memory.php
 * @param flush bool[optional]
 * @return bool the current buffer as a string.
 */
function xmlwriter_output_memory ($flush = null) {}

/**
 * Flush current buffer
 * @link http://php.net/manual/en/function.xmlwriter-flush.php
 * @param empty bool[optional]
 * @return mixed 
 */
function xmlwriter_flush ($empty = null) {}

// End of xmlwriter v.0.1
?>
