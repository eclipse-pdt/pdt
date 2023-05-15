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
 * @param mixed $encoding [optional] The input
 * encoding is automatically detected, so that the
 * encoding parameter specifies only the output
 * encoding. If empty string is passed, the parser attempts to identify
 * which encoding the document is encoded in by looking at the heading 3 or
 * 4 bytes. The default output charset is UTF-8. The supported
 * encodings are ISO-8859-1, UTF-8 and
 * US-ASCII.
 * @return XMLParser a new XMLParser instance.
 */
function xml_parser_create ($encoding = null): XMLParser {}

/**
 * Create an XML parser with namespace support
 * @link http://www.php.net/manual/en/function.xml-parser-create-ns.php
 * @param mixed $encoding [optional] The input encoding is automatically detected, so that the
 * encoding parameter specifies only the output
 * encoding. The default output charset is UTF-8. The supported
 * encodings are ISO-8859-1, UTF-8 and
 * US-ASCII.
 * @param string $separator [optional] With a namespace aware parser tag parameters passed to the various
 * handler functions will consist of namespace and tag name separated by
 * the string specified in separator.
 * @return XMLParser a new XMLParser instance.
 */
function xml_parser_create_ns ($encoding = null, string $separator = null): XMLParser {}

/**
 * Use XML Parser within an object
 * @link http://www.php.net/manual/en/function.xml-set-object.php
 * @param XMLParser $parser A reference to the XML parser to use inside the object.
 * @param object $object The object where to use the XML parser.
 * @return true Always returns true.
 */
function xml_set_object (XMLParser $parser, $object): true {}

/**
 * Set up start and end element handlers
 * @link http://www.php.net/manual/en/function.xml-set-element-handler.php
 * @param XMLParser $parser A reference to the XML parser to set up start and end element handler functions.
 * @param callable $start_handler <p>
 * The function named by start_handler
 * must accept three parameters:
 * start_element_handler
 * XMLParserparser
 * stringname
 * arrayattribs
 * <p>
 * parser 
 * <br>
 * The first parameter, parser, is a
 * reference to the XML parser calling the handler.
 * name
 * <br>
 * The second parameter, name, contains the name
 * of the element for which this handler is called.If case-folding is in effect for this
 * parser, the element name will be in uppercase letters.
 * attribs
 * <br>
 * The third parameter, attribs, contains an
 * associative array with the element's attributes (if any).The keys
 * of this array are the attribute names, the values are the attribute
 * values.Attribute names are case-folded on the same criteria as
 * element names.Attribute values are not
 * case-folded.
 * The original order of the attributes can be retrieved by walking
 * through attribs the normal way, using
 * each.The first key in the array was the first
 * attribute, and so on.
 * </p>
 * </p>
 * note.func-callback
 * @param callable $end_handler <p>
 * The function named by end_handler
 * must accept two parameters:
 * end_element_handler
 * XMLParserparser
 * stringname
 * <p>
 * parser 
 * <br>
 * The first parameter, parser, is a
 * reference to the XML parser calling the handler.
 * name
 * <br>
 * The second parameter, name, contains the name
 * of the element for which this handler is called.If case-folding is in effect for this
 * parser, the element name will be in uppercase letters.
 * </p>
 * </p>
 * <p>
 * If a handler function is set to an empty string, or false, the handler
 * in question is disabled.
 * </p>
 * @return true Always returns true.
 */
function xml_set_element_handler (XMLParser $parser, callable $start_handler, callable $end_handler): true {}

/**
 * Set up character data handler
 * @link http://www.php.net/manual/en/function.xml-set-character-data-handler.php
 * @param XMLParser $parser A reference to the XML parser to set up character data handler function.
 * @param callable $handler <p>
 * handler is a string containing the name of a
 * function that must exist when xml_parse is called
 * for parser.
 * </p>
 * <p>
 * The function named by handler must accept
 * two parameters:
 * handler
 * XMLParserparser
 * stringdata
 * <p>
 * parser
 * <br>
 * The first parameter, parser, is a
 * reference to the XML parser calling the handler.
 * data
 * <br>
 * The second parameter, data, contains
 * the character data as a string.
 * </p>
 * </p>
 * <p>
 * Character data handler is called for every piece of a text in the XML
 * document. It can be called multiple times inside each fragment (e.g.
 * for non-ASCII strings).
 * </p>
 * <p>
 * If a handler function is set to an empty string, or false, the handler
 * in question is disabled.
 * </p>
 * note.func-callback
 * @return true Always returns true.
 */
function xml_set_character_data_handler (XMLParser $parser, callable $handler): true {}

/**
 * Set up processing instruction (PI) handler
 * @link http://www.php.net/manual/en/function.xml-set-processing-instruction-handler.php
 * @param XMLParser $parser A reference to the XML parser to set up processing instruction (PI) handler function.
 * @param callable $handler <p>
 * handler is a string containing the name of a
 * function that must exist when xml_parse is called
 * for parser.
 * </p>
 * <p>
 * The function named by handler must accept
 * three parameters:
 * handler
 * XMLParserparser
 * stringtarget
 * stringdata
 * <p>
 * parser
 * <br>
 * The first parameter, parser, is a
 * reference to the XML parser calling the handler.
 * target
 * <br>
 * The second parameter, target, contains the PI
 * target.
 * data
 * <br>
 * The third parameter, data, contains the PI
 * data.
 * </p>
 * </p>
 * <p>
 * If a handler function is set to an empty string, or false, the handler
 * in question is disabled.
 * </p>
 * note.func-callback
 * @return true Always returns true.
 */
function xml_set_processing_instruction_handler (XMLParser $parser, callable $handler): true {}

/**
 * Set up default handler
 * @link http://www.php.net/manual/en/function.xml-set-default-handler.php
 * @param XMLParser $parser A reference to the XML parser to set up default handler function.
 * @param callable $handler <p>
 * handler is a string containing the name of a
 * function that must exist when xml_parse is called
 * for parser.
 * </p>
 * <p>
 * The function named by handler must accept
 * two parameters:
 * handler
 * XMLParserparser
 * stringdata
 * <p>
 * parser
 * <br>
 * The first parameter, parser, is a
 * reference to the XML parser calling the handler.
 * data
 * <br>
 * The second parameter, data, contains
 * the character data.This may be the XML declaration,
 * document type declaration, entities or other data for which
 * no other handler exists.
 * </p>
 * </p>
 * <p>
 * If a handler function is set to an empty string, or false, the handler
 * in question is disabled.
 * </p>
 * note.func-callback
 * @return true Always returns true.
 */
function xml_set_default_handler (XMLParser $parser, callable $handler): true {}

/**
 * Set up unparsed entity declaration handler
 * @link http://www.php.net/manual/en/function.xml-set-unparsed-entity-decl-handler.php
 * @param XMLParser $parser A reference to the XML parser to set up unparsed entity declaration handler function.
 * @param callable $handler <p>
 * handler is a string containing the name of a
 * function that must exist when xml_parse is called
 * for parser.
 * </p>
 * <p>
 * The function named by handler must accept six
 * parameters:
 * handler
 * XMLParserparser
 * stringentity_name
 * stringbase
 * stringsystem_id
 * stringpublic_id
 * stringnotation_name
 * <p>
 * parser
 * <br>
 * The first parameter, parser, is a
 * reference to the XML parser calling the
 * handler.
 * entity_name
 * <br>
 * The name of the entity that is about to be defined.
 * base
 * <br>
 * This is the base for resolving the system identifier
 * (systemId) of the external entity.Currently
 * this parameter will always be set to an empty string.
 * system_id
 * <br>
 * System identifier for the external entity.
 * public_id
 * <br>
 * Public identifier for the external entity.
 * notation_name
 * <br>
 * Name of the notation of this entity (see
 * xml_set_notation_decl_handler).
 * </p>
 * </p>
 * <p>
 * If a handler function is set to an empty string, or false, the handler
 * in question is disabled.
 * </p>
 * note.func-callback
 * @return true Always returns true.
 */
function xml_set_unparsed_entity_decl_handler (XMLParser $parser, callable $handler): true {}

/**
 * Set up notation declaration handler
 * @link http://www.php.net/manual/en/function.xml-set-notation-decl-handler.php
 * @param XMLParser $parser A reference to the XML parser to set up notation declaration handler function.
 * @param callable $handler <p>
 * handler is a string containing the name of a
 * function that must exist when xml_parse is called
 * for parser.
 * </p>
 * <p>
 * The function named by handler must accept
 * five parameters:
 * handler
 * XMLParserparser
 * stringnotation_name
 * stringbase
 * stringsystem_id
 * stringpublic_id
 * <p>
 * parser
 * <br>
 * The first parameter, parser, is a
 * reference to the XML parser calling the handler.
 * notation_name
 * <br>
 * This is the notation's name, as per
 * the notation format described above.
 * base
 * <br>
 * This is the base for resolving the system identifier
 * (system_id) of the notation declaration.
 * Currently this parameter will always be set to an empty string.
 * system_id
 * <br>
 * System identifier of the external notation declaration.
 * public_id
 * <br>
 * Public identifier of the external notation declaration.
 * </p>
 * </p>
 * <p>
 * If a handler function is set to an empty string, or false, the handler
 * in question is disabled.
 * </p>
 * note.func-callback
 * @return true Always returns true.
 */
function xml_set_notation_decl_handler (XMLParser $parser, callable $handler): true {}

/**
 * Set up external entity reference handler
 * @link http://www.php.net/manual/en/function.xml-set-external-entity-ref-handler.php
 * @param XMLParser $parser A reference to the XML parser to set up external entity reference handler function.
 * @param callable $handler <p>
 * handler is a string containing the name of a
 * function that must exist when xml_parse is called
 * for parser.
 * </p>
 * <p>
 * The function named by handler must accept
 * five parameters, and should return an integer value.If the
 * value returned from the handler is false (which it will be if no
 * value is returned), the XML parser will stop parsing and
 * xml_get_error_code will return
 * XML_ERROR_EXTERNAL_ENTITY_HANDLING.
 * handler
 * XMLParserparser
 * stringopen_entity_names
 * stringbase
 * stringsystem_id
 * stringpublic_id
 * <p>
 * parser
 * <br>
 * The first parameter, parser, is a
 * reference to the XML parser calling the handler.
 * open_entity_names
 * <br>
 * The second parameter, open_entity_names, is a
 * space-separated list of the names of the entities that are open for
 * the parse of this entity (including the name of the referenced
 * entity).
 * base
 * <br>
 * This is the base for resolving the system identifier
 * (system_id) of the external entity.Currently
 * this parameter will always be set to an empty string.
 * system_id
 * <br>
 * The fourth parameter, system_id, is the
 * system identifier as specified in the entity declaration.
 * public_id
 * <br>
 * The fifth parameter, public_id, is the
 * public identifier as specified in the entity declaration, or
 * an empty string if none was specified; the whitespace in the
 * public identifier will have been normalized as required by
 * the XML spec.
 * </p>
 * </p>
 * <p>
 * If a handler function is set to an empty string, or false, the handler
 * in question is disabled.
 * </p>
 * note.func-callback
 * @return true Always returns true.
 */
function xml_set_external_entity_ref_handler (XMLParser $parser, callable $handler): true {}

/**
 * Set up start namespace declaration handler
 * @link http://www.php.net/manual/en/function.xml-set-start-namespace-decl-handler.php
 * @param XMLParser $parser A reference to the XML parser.
 * @param callable $handler <p>
 * handler is a string containing the name of a
 * function that must exist when xml_parse is called
 * for parser.
 * </p>
 * <p>
 * The function named by handler must accept
 * three parameters, and should return an integer value. If the
 * value returned from the handler is false (which it will be if no
 * value is returned), the XML parser will stop parsing and
 * xml_get_error_code will return
 * XML_ERROR_EXTERNAL_ENTITY_HANDLING.
 * handler
 * XMLParserparser
 * stringprefix
 * stringuri
 * <p>
 * parser
 * <br>
 * The first parameter, parser, is a
 * reference to the XML parser calling the handler.
 * prefix
 * <br>
 * The prefix is a string used to reference the namespace within an XML object.
 * uri
 * <br>
 * Uniform Resource Identifier (URI) of namespace.
 * </p>
 * </p>
 * <p>
 * If a handler function is set to an empty string, or false, the handler
 * in question is disabled.
 * </p>
 * note.func-callback
 * @return true Always returns true.
 */
function xml_set_start_namespace_decl_handler (XMLParser $parser, callable $handler): true {}

/**
 * Set up end namespace declaration handler
 * @link http://www.php.net/manual/en/function.xml-set-end-namespace-decl-handler.php
 * @param XMLParser $parser A reference to the XML parser.
 * @param callable $handler <p>
 * handler is a string containing the name of a
 * function that must exist when xml_parse is called
 * for parser.
 * </p>
 * <p>
 * The function named by handler must accept
 * two parameters, and should return an integer value. If the
 * value returned from the handler is false (which it will be if no
 * value is returned), the XML parser will stop parsing and
 * xml_get_error_code will return
 * XML_ERROR_EXTERNAL_ENTITY_HANDLING.
 * handler
 * XMLParserparser
 * stringprefix
 * <p>
 * parser
 * <br>
 * The first parameter, parser, is a
 * reference to the XML parser calling the handler.
 * prefix
 * <br>
 * The prefix is a string used to reference the namespace within an XML object.
 * </p>
 * </p>
 * <p>
 * If a handler function is set to an empty string, or false, the handler
 * in question is disabled.
 * </p>
 * note.func-callback
 * @return true Always returns true.
 */
function xml_set_end_namespace_decl_handler (XMLParser $parser, callable $handler): true {}

/**
 * Start parsing an XML document
 * @link http://www.php.net/manual/en/function.xml-parse.php
 * @param XMLParser $parser A reference to the XML parser to use.
 * @param string $data Chunk of data to parse. A document may be parsed piece-wise by
 * calling xml_parse several times with new data,
 * as long as the is_final parameter is set and
 * true when the last data is parsed.
 * @param bool $is_final [optional] If set and true, data is the last piece of
 * data sent in this parse.
 * @return int 1 on success or 0 on failure.
 * <p>
 * For unsuccessful parses, error information can be retrieved with
 * xml_get_error_code,
 * xml_error_string,
 * xml_get_current_line_number,
 * xml_get_current_column_number and
 * xml_get_current_byte_index.
 * </p>
 * <p>
 * Some errors (such as entity errors) are reported at the end of the data, thus only if
 * is_final is set and true.
 * </p>
 */
function xml_parse (XMLParser $parser, string $data, bool $is_final = null): int {}

/**
 * Parse XML data into an array structure
 * @link http://www.php.net/manual/en/function.xml-parse-into-struct.php
 * @param XMLParser $parser A reference to the XML parser.
 * @param string $data A string containing the XML data.
 * @param array $values An array containing the values of the XML data
 * @param array $index [optional] An array containing pointers to the location of the appropriate values in the $values.
 * @return int xml_parse_into_struct returns 0 for failure and 1 for
 * success. This is not the same as false and true, be careful with
 * operators such as ===.
 */
function xml_parse_into_struct (XMLParser $parser, string $data, array &$values, array &$index = null): int {}

/**
 * Get XML parser error code
 * @link http://www.php.net/manual/en/function.xml-get-error-code.php
 * @param XMLParser $parser A reference to the XML parser to get error code from.
 * @return int one of the error
 * codes listed in the error codes
 * section.
 */
function xml_get_error_code (XMLParser $parser): int {}

/**
 * Get XML parser error string
 * @link http://www.php.net/manual/en/function.xml-error-string.php
 * @param int $error_code An error code from xml_get_error_code.
 * @return mixed a string with a textual description of the error
 * error_code, or null if no description was found.
 */
function xml_error_string (int $error_code): ?string {}

/**
 * Get current line number for an XML parser
 * @link http://www.php.net/manual/en/function.xml-get-current-line-number.php
 * @param XMLParser $parser A reference to the XML parser to get line number from.
 * @return int which line the
 * parser is currently at in its data buffer.
 */
function xml_get_current_line_number (XMLParser $parser): int {}

/**
 * Get current column number for an XML parser
 * @link http://www.php.net/manual/en/function.xml-get-current-column-number.php
 * @param XMLParser $parser A reference to the XML parser to get column number from.
 * @return int which column on
 * the current line (as given by
 * xml_get_current_line_number) the parser is
 * currently at.
 */
function xml_get_current_column_number (XMLParser $parser): int {}

/**
 * Get current byte index for an XML parser
 * @link http://www.php.net/manual/en/function.xml-get-current-byte-index.php
 * @param XMLParser $parser A reference to the XML parser to get byte index from.
 * @return int which byte index
 * the parser is currently at in its data buffer (starting at 0).
 */
function xml_get_current_byte_index (XMLParser $parser): int {}

/**
 * Free an XML parser
 * @link http://www.php.net/manual/en/function.xml-parser-free.php
 * @param XMLParser $parser A reference to the XML parser to free.
 * @return bool true on success or false on failure
 */
function xml_parser_free (XMLParser $parser): bool {}

/**
 * Set options in an XML parser
 * @link http://www.php.net/manual/en/function.xml-parser-set-option.php
 * @param XMLParser $parser A reference to the XML parser to set an option in.
 * @param int $option <p>
 * Which option to set. See below.
 * </p>
 * <p>
 * The following options are available:
 * <table>
 * XML parser options
 * <table>
 * <tr valign="top">
 * <td>Option constant</td>
 * <td>Data type</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>XML_OPTION_CASE_FOLDING</td>
 * <td>integer</td>
 * <td>
 * Controls whether case-folding is enabled for this
 * XML parser. Enabled by default.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>XML_OPTION_SKIP_TAGSTART</td>
 * <td>integer</td> 
 * <td>
 * Specify how many characters should be skipped in the beginning of a
 * tag name.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>XML_OPTION_SKIP_WHITE</td>
 * <td>integer</td> 
 * <td>
 * Whether to skip values consisting of whitespace characters.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>XML_OPTION_TARGET_ENCODING</td>
 * <td>string</td> 
 * <td>
 * Sets which target encoding to
 * use in this XML parser.By default, it is set to the same as the
 * source encoding used by xml_parser_create.
 * Supported target encodings are ISO-8859-1,
 * US-ASCII and UTF-8.
 * </td>
 * </tr>
 * </table>
 * </table>
 * </p>
 * @param mixed $value The option's new value.
 * @return bool This function returns false if parser does not
 * refer to a valid parser, or if the option could not be set. Else the
 * option is set and true is returned.
 */
function xml_parser_set_option (XMLParser $parser, int $option, $value): bool {}

/**
 * Get options from an XML parser
 * @link http://www.php.net/manual/en/function.xml-parser-get-option.php
 * @param XMLParser $parser A reference to the XML parser to get an option from.
 * @param int $option Which option to fetch. XML_OPTION_CASE_FOLDING,
 * XML_OPTION_SKIP_TAGSTART, XML_OPTION_SKIP_WHITE
 * and XML_OPTION_TARGET_ENCODING are available.
 * See xml_parser_set_option for their description.
 * @return mixed This function returns false if parser does
 * not refer to a valid parser or if option isn't
 * valid (generates also a E_WARNING).
 * Else the option's value is returned.
 */
function xml_parser_get_option (XMLParser $parser, int $option): string|int {}


/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_NONE', 0);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_NO_MEMORY', 1);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_SYNTAX', 2);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_NO_ELEMENTS', 3);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_INVALID_TOKEN', 4);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_UNCLOSED_TOKEN', 5);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_PARTIAL_CHAR', 6);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_TAG_MISMATCH', 7);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_DUPLICATE_ATTRIBUTE', 8);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_JUNK_AFTER_DOC_ELEMENT', 9);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_PARAM_ENTITY_REF', 10);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_UNDEFINED_ENTITY', 11);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_RECURSIVE_ENTITY_REF', 12);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_ASYNC_ENTITY', 13);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_BAD_CHAR_REF', 14);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_BINARY_ENTITY_REF', 15);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_ATTRIBUTE_EXTERNAL_ENTITY_REF', 16);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_MISPLACED_XML_PI', 17);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_UNKNOWN_ENCODING', 18);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_INCORRECT_ENCODING', 19);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_UNCLOSED_CDATA_SECTION', 20);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_ERROR_EXTERNAL_ENTITY_HANDLING', 21);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_OPTION_CASE_FOLDING', 1);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_OPTION_TARGET_ENCODING', 2);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_OPTION_SKIP_TAGSTART', 3);

/**
 * 
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_OPTION_SKIP_WHITE', 4);

/**
 * Holds the SAX implementation method.
 * Can be libxml or expat.
 * @link http://www.php.net/manual/en/xml.constants.php
 */
define ('XML_SAX_IMPL', "libxml");

// End of xml v.8.2.6
