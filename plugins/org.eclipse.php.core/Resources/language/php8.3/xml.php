<?php

// Start of xml v.8.2.6

/**
 * A fully opaque class which replaces xml resources as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/class.xmlparser.php
 */
final class XMLParser  {
}

/**
 * Create an XML parser
 * @link http://www.php.net/manual/en/function.xml-parser-create.php
 * @param string|null $encoding [optional] 
 * @return XMLParser Returns a new XMLParser instance.
 */
function xml_parser_create (?string $encoding = null): XMLParser {}

/**
 * Create an XML parser with namespace support
 * @link http://www.php.net/manual/en/function.xml-parser-create-ns.php
 * @param string|null $encoding [optional] 
 * @param string $separator [optional] 
 * @return XMLParser Returns a new XMLParser instance.
 */
function xml_parser_create_ns (?string $encoding = null, string $separator = '":"'): XMLParser {}

/**
 * Use XML Parser within an object
 * @link http://www.php.net/manual/en/function.xml-set-object.php
 * @param XMLParser $parser 
 * @param object $object 
 * @return true Always returns true.
 */
function xml_set_object (XMLParser $parser, object $object): true {}

/**
 * Set up start and end element handlers
 * @link http://www.php.net/manual/en/function.xml-set-element-handler.php
 * @param XMLParser $parser 
 * @param callable $start_handler 
 * @param callable $end_handler 
 * @return true Always returns true.
 */
function xml_set_element_handler (XMLParser $parser, callable $start_handler, callable $end_handler): true {}

/**
 * Set up character data handler
 * @link http://www.php.net/manual/en/function.xml-set-character-data-handler.php
 * @param XMLParser $parser 
 * @param callable $handler 
 * @return true Always returns true.
 */
function xml_set_character_data_handler (XMLParser $parser, callable $handler): true {}

/**
 * Set up processing instruction (PI) handler
 * @link http://www.php.net/manual/en/function.xml-set-processing-instruction-handler.php
 * @param XMLParser $parser 
 * @param callable $handler 
 * @return true Always returns true.
 */
function xml_set_processing_instruction_handler (XMLParser $parser, callable $handler): true {}

/**
 * Set up default handler
 * @link http://www.php.net/manual/en/function.xml-set-default-handler.php
 * @param XMLParser $parser 
 * @param callable $handler 
 * @return true Always returns true.
 */
function xml_set_default_handler (XMLParser $parser, callable $handler): true {}

/**
 * Set up unparsed entity declaration handler
 * @link http://www.php.net/manual/en/function.xml-set-unparsed-entity-decl-handler.php
 * @param XMLParser $parser 
 * @param callable $handler 
 * @return true Always returns true.
 */
function xml_set_unparsed_entity_decl_handler (XMLParser $parser, callable $handler): true {}

/**
 * Set up notation declaration handler
 * @link http://www.php.net/manual/en/function.xml-set-notation-decl-handler.php
 * @param XMLParser $parser 
 * @param callable $handler 
 * @return true Always returns true.
 */
function xml_set_notation_decl_handler (XMLParser $parser, callable $handler): true {}

/**
 * Set up external entity reference handler
 * @link http://www.php.net/manual/en/function.xml-set-external-entity-ref-handler.php
 * @param XMLParser $parser 
 * @param callable $handler 
 * @return true Always returns true.
 */
function xml_set_external_entity_ref_handler (XMLParser $parser, callable $handler): true {}

/**
 * Set up start namespace declaration handler
 * @link http://www.php.net/manual/en/function.xml-set-start-namespace-decl-handler.php
 * @param XMLParser $parser 
 * @param callable $handler 
 * @return true Always returns true.
 */
function xml_set_start_namespace_decl_handler (XMLParser $parser, callable $handler): true {}

/**
 * Set up end namespace declaration handler
 * @link http://www.php.net/manual/en/function.xml-set-end-namespace-decl-handler.php
 * @param XMLParser $parser 
 * @param callable $handler 
 * @return true Always returns true.
 */
function xml_set_end_namespace_decl_handler (XMLParser $parser, callable $handler): true {}

/**
 * Start parsing an XML document
 * @link http://www.php.net/manual/en/function.xml-parse.php
 * @param XMLParser $parser 
 * @param string $data 
 * @param bool $is_final [optional] 
 * @return int Returns 1 on success or 0 on failure.
 * <p>For unsuccessful parses, error information can be retrieved with
 * xml_get_error_code,
 * xml_error_string,
 * xml_get_current_line_number,
 * xml_get_current_column_number and
 * xml_get_current_byte_index.</p>
 * <p>Some errors (such as entity errors) are reported at the end of the data, thus only if
 * is_final is set and true.</p>
 */
function xml_parse (XMLParser $parser, string $data, bool $is_final = false): int {}

/**
 * Parse XML data into an array structure
 * @link http://www.php.net/manual/en/function.xml-parse-into-struct.php
 * @param XMLParser $parser 
 * @param string $data 
 * @param array $values 
 * @param array $index [optional] 
 * @return int xml_parse_into_struct returns 0 for failure and 1 for
 * success. This is not the same as false and true, be careful with
 * operators such as ===.
 */
function xml_parse_into_struct (XMLParser $parser, string $data, array &$values, array &$index = null): int {}

/**
 * Get XML parser error code
 * @link http://www.php.net/manual/en/function.xml-get-error-code.php
 * @param XMLParser $parser 
 * @return int Returns one of the error
 * codes listed in the error codes
 * section.
 */
function xml_get_error_code (XMLParser $parser): int {}

/**
 * Get XML parser error string
 * @link http://www.php.net/manual/en/function.xml-error-string.php
 * @param int $error_code 
 * @return string|null Returns a string with a textual description of the error
 * error_code, or null if no description was found.
 */
function xml_error_string (int $error_code): ?string {}

/**
 * Get current line number for an XML parser
 * @link http://www.php.net/manual/en/function.xml-get-current-line-number.php
 * @param XMLParser $parser 
 * @return int Returns which line the
 * parser is currently at in its data buffer.
 */
function xml_get_current_line_number (XMLParser $parser): int {}

/**
 * Get current column number for an XML parser
 * @link http://www.php.net/manual/en/function.xml-get-current-column-number.php
 * @param XMLParser $parser 
 * @return int Returns which column on
 * the current line (as given by
 * xml_get_current_line_number) the parser is
 * currently at.
 */
function xml_get_current_column_number (XMLParser $parser): int {}

/**
 * Get current byte index for an XML parser
 * @link http://www.php.net/manual/en/function.xml-get-current-byte-index.php
 * @param XMLParser $parser 
 * @return int Returns which byte index
 * the parser is currently at in its data buffer (starting at 0).
 */
function xml_get_current_byte_index (XMLParser $parser): int {}

/**
 * Free an XML parser
 * @link http://www.php.net/manual/en/function.xml-parser-free.php
 * @param XMLParser $parser 
 * @return bool Returns true on success or false on failure.
 */
function xml_parser_free (XMLParser $parser): bool {}

/**
 * Set options in an XML parser
 * @link http://www.php.net/manual/en/function.xml-parser-set-option.php
 * @param XMLParser $parser 
 * @param int $option 
 * @param string|int $value 
 * @return bool This function returns false if parser does not
 * refer to a valid parser, or if the option could not be set. Else the
 * option is set and true is returned.
 */
function xml_parser_set_option (XMLParser $parser, int $option, string|int $value): bool {}

/**
 * Get options from an XML parser
 * @link http://www.php.net/manual/en/function.xml-parser-get-option.php
 * @param XMLParser $parser 
 * @param int $option 
 * @return string|int This function returns false if parser does
 * not refer to a valid parser or if option isn't
 * valid (generates also a E_WARNING).
 * Else the option's value is returned.
 */
function xml_parser_get_option (XMLParser $parser, int $option): string|int {}


/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_NONE', 0);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_NO_MEMORY', 1);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_SYNTAX', 2);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_NO_ELEMENTS', 3);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_INVALID_TOKEN', 4);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_UNCLOSED_TOKEN', 5);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_PARTIAL_CHAR', 6);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_TAG_MISMATCH', 7);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_DUPLICATE_ATTRIBUTE', 8);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_JUNK_AFTER_DOC_ELEMENT', 9);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_PARAM_ENTITY_REF', 10);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_UNDEFINED_ENTITY', 11);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_RECURSIVE_ENTITY_REF', 12);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_ASYNC_ENTITY', 13);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_BAD_CHAR_REF', 14);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_BINARY_ENTITY_REF', 15);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_ATTRIBUTE_EXTERNAL_ENTITY_REF', 16);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_MISPLACED_XML_PI', 17);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_UNKNOWN_ENCODING', 18);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_INCORRECT_ENCODING', 19);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_UNCLOSED_CDATA_SECTION', 20);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_ERROR_EXTERNAL_ENTITY_HANDLING', 21);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_OPTION_CASE_FOLDING', 1);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_OPTION_TARGET_ENCODING', 2);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_OPTION_SKIP_TAGSTART', 3);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var int
 */
define ('XML_OPTION_SKIP_WHITE', 4);

/**
 * Holds the SAX implementation method.
 * Can be libxml or expat.
 * @link http://www.php.net/manual/en/xml.constants.php
 * @var string
 */
define ('XML_SAX_IMPL', "libxml");

// End of xml v.8.2.6
