<?php

// Start of xml v.8.3.0

final class XMLParser  {
}

/**
 * {@inheritdoc}
 * @param string|null $encoding [optional]
 */
function xml_parser_create (?string $encoding = NULL): XMLParser {}

/**
 * {@inheritdoc}
 * @param string|null $encoding [optional]
 * @param string $separator [optional]
 */
function xml_parser_create_ns (?string $encoding = NULL, string $separator = ':'): XMLParser {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 * @param object $object
 */
function xml_set_object (XMLParser $parser, object $object): true {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 * @param mixed $start_handler
 * @param mixed $end_handler
 */
function xml_set_element_handler (XMLParser $parser, $start_handler = null, $end_handler = null): true {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 * @param mixed $handler
 */
function xml_set_character_data_handler (XMLParser $parser, $handler = null): true {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 * @param mixed $handler
 */
function xml_set_processing_instruction_handler (XMLParser $parser, $handler = null): true {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 * @param mixed $handler
 */
function xml_set_default_handler (XMLParser $parser, $handler = null): true {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 * @param mixed $handler
 */
function xml_set_unparsed_entity_decl_handler (XMLParser $parser, $handler = null): true {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 * @param mixed $handler
 */
function xml_set_notation_decl_handler (XMLParser $parser, $handler = null): true {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 * @param mixed $handler
 */
function xml_set_external_entity_ref_handler (XMLParser $parser, $handler = null): true {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 * @param mixed $handler
 */
function xml_set_start_namespace_decl_handler (XMLParser $parser, $handler = null): true {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 * @param mixed $handler
 */
function xml_set_end_namespace_decl_handler (XMLParser $parser, $handler = null): true {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 * @param string $data
 * @param bool $is_final [optional]
 */
function xml_parse (XMLParser $parser, string $data, bool $is_final = false): int {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 * @param string $data
 * @param mixed $values
 * @param mixed $index [optional]
 */
function xml_parse_into_struct (XMLParser $parser, string $data, &$values = null, &$index = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 */
function xml_get_error_code (XMLParser $parser): int {}

/**
 * {@inheritdoc}
 * @param int $error_code
 */
function xml_error_string (int $error_code): ?string {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 */
function xml_get_current_line_number (XMLParser $parser): int {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 */
function xml_get_current_column_number (XMLParser $parser): int {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 */
function xml_get_current_byte_index (XMLParser $parser): int {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 */
function xml_parser_free (XMLParser $parser): bool {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 * @param int $option
 * @param mixed $value
 */
function xml_parser_set_option (XMLParser $parser, int $option, $value = null): bool {}

/**
 * {@inheritdoc}
 * @param XMLParser $parser
 * @param int $option
 */
function xml_parser_get_option (XMLParser $parser, int $option): string|int|bool {}

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

// End of xml v.8.3.0
