<?php

// Start of xml v.

/**
 * Create an XML parser
 * @link http://php.net/manual/en/function.xml-parser-create.php
 * @param encoding string[optional]
 * @return resource a resource handle for the new XML parser.
 */
function xml_parser_create ($encoding = null) {}

/**
 * Create an XML parser with namespace support
 * @link http://php.net/manual/en/function.xml-parser-create-ns.php
 * @param encoding string[optional]
 * @param separator string[optional]
 * @return resource a resource handle for the new XML parser.
 */
function xml_parser_create_ns ($encoding = null, $separator = null) {}

/**
 * Use XML Parser within an object
 * @link http://php.net/manual/en/function.xml-set-object.php
 * @param parser resource
 * @param object object
 * @return bool 
 */
function xml_set_object ($parser, &$object) {}

/**
 * Set up start and end element handlers
 * @link http://php.net/manual/en/function.xml-set-element-handler.php
 * @param parser resource
 * @param start_element_handler callback
 * @param end_element_handler callback
 * @return bool 
 */
function xml_set_element_handler ($parser, $start_element_handler, $end_element_handler) {}

/**
 * Set up character data handler
 * @link http://php.net/manual/en/function.xml-set-character-data-handler.php
 * @param parser resource
 * @param handler callback
 * @return bool 
 */
function xml_set_character_data_handler ($parser, $handler) {}

/**
 * Set up processing instruction (PI) handler
 * @link http://php.net/manual/en/function.xml-set-processing-instruction-handler.php
 * @param parser resource
 * @param handler callback
 * @return bool 
 */
function xml_set_processing_instruction_handler ($parser, $handler) {}

/**
 * Set up default handler
 * @link http://php.net/manual/en/function.xml-set-default-handler.php
 * @param parser resource
 * @param handler callback
 * @return bool 
 */
function xml_set_default_handler ($parser, $handler) {}

/**
 * Set up unparsed entity declaration handler
 * @link http://php.net/manual/en/function.xml-set-unparsed-entity-decl-handler.php
 * @param parser resource
 * @param handler callback
 * @return bool 
 */
function xml_set_unparsed_entity_decl_handler ($parser, $handler) {}

/**
 * Set up notation declaration handler
 * @link http://php.net/manual/en/function.xml-set-notation-decl-handler.php
 * @param parser resource
 * @param handler callback
 * @return bool 
 */
function xml_set_notation_decl_handler ($parser, $handler) {}

/**
 * Set up external entity reference handler
 * @link http://php.net/manual/en/function.xml-set-external-entity-ref-handler.php
 * @param parser resource
 * @param handler callback
 * @return bool 
 */
function xml_set_external_entity_ref_handler ($parser, $handler) {}

/**
 * Set up start namespace declaration handler
 * @link http://php.net/manual/en/function.xml-set-start-namespace-decl-handler.php
 * @param parser resource
 * @param handler callback
 * @return bool 
 */
function xml_set_start_namespace_decl_handler ($parser, $handler) {}

/**
 * Set up end namespace declaration handler
 * @link http://php.net/manual/en/function.xml-set-end-namespace-decl-handler.php
 * @param parser resource
 * @param handler callback
 * @return bool 
 */
function xml_set_end_namespace_decl_handler ($parser, $handler) {}

/**
 * Start parsing an XML document
 * @link http://php.net/manual/en/function.xml-parse.php
 * @param parser resource
 * @param data string
 * @param is_final bool[optional]
 * @return int 1 on success or 0 on failure.
 */
function xml_parse ($parser, $data, $is_final = null) {}

/**
 * Parse XML data into an array structure
 * @link http://php.net/manual/en/function.xml-parse-into-struct.php
 * @param parser resource
 * @param data string
 * @param values array
 * @param index array[optional]
 * @return int 
 */
function xml_parse_into_struct ($parser, $data, array &$values, array &$index = null) {}

/**
 * Get XML parser error code
 * @link http://php.net/manual/en/function.xml-get-error-code.php
 * @param parser resource
 * @return int 
 */
function xml_get_error_code ($parser) {}

/**
 * Get XML parser error string
 * @link http://php.net/manual/en/function.xml-error-string.php
 * @param code int
 * @return string a string with a textual description of the error
 */
function xml_error_string ($code) {}

/**
 * Get current line number for an XML parser
 * @link http://php.net/manual/en/function.xml-get-current-line-number.php
 * @param parser resource
 * @return int 
 */
function xml_get_current_line_number ($parser) {}

/**
 * Get current column number for an XML parser
 * @link http://php.net/manual/en/function.xml-get-current-column-number.php
 * @param parser resource
 * @return int 
 */
function xml_get_current_column_number ($parser) {}

/**
 * Get current byte index for an XML parser
 * @link http://php.net/manual/en/function.xml-get-current-byte-index.php
 * @param parser resource
 * @return int 
 */
function xml_get_current_byte_index ($parser) {}

/**
 * Free an XML parser
 * @link http://php.net/manual/en/function.xml-parser-free.php
 * @param parser resource
 * @return bool 
 */
function xml_parser_free ($parser) {}

/**
 * Set options in an XML parser
 * @link http://php.net/manual/en/function.xml-parser-set-option.php
 * @param parser resource
 * @param option int
 * @param value mixed
 * @return bool 
 */
function xml_parser_set_option ($parser, $option, $value) {}

/**
 * Get options from an XML parser
 * @link http://php.net/manual/en/function.xml-parser-get-option.php
 * @param parser resource
 * @param option int
 * @return mixed 
 */
function xml_parser_get_option ($parser, $option) {}

/**
 * Encodes an ISO-8859-1 string to UTF-8
 * @link http://php.net/manual/en/function.utf8-encode.php
 * @param data string
 * @return string the UTF-8 translation of data.
 */
function utf8_encode ($data) {}

/**
 * Converts a string with ISO-8859-1 characters encoded with UTF-8
   to single-byte ISO-8859-1
 * @link http://php.net/manual/en/function.utf8-decode.php
 * @param data string
 * @return string the ISO-8859-1 translation of data.
 */
function utf8_decode ($data) {}

define ('XML_ERROR_NONE', 0);
define ('XML_ERROR_NO_MEMORY', 1);
define ('XML_ERROR_SYNTAX', 2);
define ('XML_ERROR_NO_ELEMENTS', 3);
define ('XML_ERROR_INVALID_TOKEN', 4);
define ('XML_ERROR_UNCLOSED_TOKEN', 5);
define ('XML_ERROR_PARTIAL_CHAR', 6);
define ('XML_ERROR_TAG_MISMATCH', 7);
define ('XML_ERROR_DUPLICATE_ATTRIBUTE', 8);
define ('XML_ERROR_JUNK_AFTER_DOC_ELEMENT', 9);
define ('XML_ERROR_PARAM_ENTITY_REF', 10);
define ('XML_ERROR_UNDEFINED_ENTITY', 11);
define ('XML_ERROR_RECURSIVE_ENTITY_REF', 12);
define ('XML_ERROR_ASYNC_ENTITY', 13);
define ('XML_ERROR_BAD_CHAR_REF', 14);
define ('XML_ERROR_BINARY_ENTITY_REF', 15);
define ('XML_ERROR_ATTRIBUTE_EXTERNAL_ENTITY_REF', 16);
define ('XML_ERROR_MISPLACED_XML_PI', 17);
define ('XML_ERROR_UNKNOWN_ENCODING', 18);
define ('XML_ERROR_INCORRECT_ENCODING', 19);
define ('XML_ERROR_UNCLOSED_CDATA_SECTION', 20);
define ('XML_ERROR_EXTERNAL_ENTITY_HANDLING', 21);
define ('XML_OPTION_CASE_FOLDING', 1);
define ('XML_OPTION_TARGET_ENCODING', 2);
define ('XML_OPTION_SKIP_TAGSTART', 3);
define ('XML_OPTION_SKIP_WHITE', 4);
define ('XML_SAX_IMPL', "libxml");

// End of xml v.
?>
