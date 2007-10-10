<?php

// Start of libxml v.

/**
 * @link http://php.net/manual/en/ref.libxml.php
 */
class LibXMLError  {
}

/**
 * Set the streams context for the next libxml document load or write
 * @link http://php.net/manual/en/function.libxml-set-streams-context.php
 * @param streams_context resource
 * @return void
 */
function libxml_set_streams_context ($streams_context) {}

/**
 * Disable libxml errors and allow user to fetch error information as needed
 * @link http://php.net/manual/en/function.libxml-use-internal-errors.php
 * @param use_errors bool[optional]
 * @return bool
 */
function libxml_use_internal_errors ($use_errors = null) {}

/**
 * Retrieve last error from libxml
 * @link http://php.net/manual/en/function.libxml-get-last-error.php
 * @return LibXMLError a LibXMLError object if there is any error in the
 */
function libxml_get_last_error () {}

/**
 * Clear libxml error buffer
 * @link http://php.net/manual/en/function.libxml-clear-errors.php
 * @return void
 */
function libxml_clear_errors () {}

/**
 * Retrieve array of errors
 * @link http://php.net/manual/en/function.libxml-get-errors.php
 * @return array an array with LibXMLError objects if there are any
 */
function libxml_get_errors () {}

define ('LIBXML_VERSION', 20627);
define ('LIBXML_DOTTED_VERSION', '2.6.27');
define ('LIBXML_NOENT', 2);
define ('LIBXML_DTDLOAD', 4);
define ('LIBXML_DTDATTR', 8);
define ('LIBXML_DTDVALID', 16);
define ('LIBXML_NOERROR', 32);
define ('LIBXML_NOWARNING', 64);
define ('LIBXML_NOBLANKS', 256);
define ('LIBXML_XINCLUDE', 1024);
define ('LIBXML_NSCLEAN', 8192);
define ('LIBXML_NOCDATA', 16384);
define ('LIBXML_NONET', 2048);
define ('LIBXML_COMPACT', 65536);
define ('LIBXML_NOXMLDECL', 2);
define ('LIBXML_NOEMPTYTAG', 4);
define ('LIBXML_ERR_NONE', 0);
define ('LIBXML_ERR_WARNING', 1);
define ('LIBXML_ERR_ERROR', 2);
define ('LIBXML_ERR_FATAL', 3);

// End of libxml v.

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
define ('XML_SAX_IMPL', 'libxml');

// End of xml v.

// Start of wddx v.

/**
 * Serialize a single value into a WDDX packet
 * @link http://php.net/manual/en/function.wddx-serialize-value.php
 * @param var mixed
 * @param comment string[optional]
 * @return string the WDDX packet, or false on error.
 */
function wddx_serialize_value ($var, $comment = null) {}

/**
 * Serialize variables into a WDDX packet
 * @link http://php.net/manual/en/function.wddx-serialize-vars.php
 * @param var_name mixed
 * @param ... mixed[optional]
 * @return string the WDDX packet, or false on error.
 */
function wddx_serialize_vars ($var_name) {}

/**
 * Starts a new WDDX packet with structure inside it
 * @link http://php.net/manual/en/function.wddx-packet-start.php
 * @param comment string[optional]
 * @return resource a packet ID for use in later functions, or false on error.
 */
function wddx_packet_start ($comment = null) {}

/**
 * Ends a WDDX packet with the specified ID
 * @link http://php.net/manual/en/function.wddx-packet-end.php
 * @param packet_id resource
 * @return string the string containing the WDDX packet.
 */
function wddx_packet_end ($packet_id) {}

/**
 * Add variables to a WDDX packet with the specified ID
 * @link http://php.net/manual/en/function.wddx-add-vars.php
 * @param packet_id resource
 * @param var_name mixed
 * @param ... mixed[optional]
 * @return bool
 */
function wddx_add_vars ($packet_id, $var_name) {}

/**
 * &Alias; <function>wddx_unserialize</function>
 * @link http://php.net/manual/en/function.wddx-deserialize.php
 */
function wddx_deserialize () {}

// End of wddx v.

// Start of session v.

/**
 * Get and/or set the current session name
 * @link http://php.net/manual/en/function.session-name.php
 * @param name string[optional]
 * @return string the name of the current session.
 */
function session_name ($name = null) {}

/**
 * Get and/or set the current session module
 * @link http://php.net/manual/en/function.session-module-name.php
 * @param module string[optional]
 * @return string the name of the current session module.
 */
function session_module_name ($module = null) {}

/**
 * Get and/or set the current session save path
 * @link http://php.net/manual/en/function.session-save-path.php
 * @param path string[optional]
 * @return string the path of the current directory used for data storage.
 */
function session_save_path ($path = null) {}

/**
 * Get and/or set the current session id
 * @link http://php.net/manual/en/function.session-id.php
 * @param id string[optional]
 * @return string
 */
function session_id ($id = null) {}

/**
 * Update the current session id with a newly generated one
 * @link http://php.net/manual/en/function.session-regenerate-id.php
 * @param delete_old_session bool[optional]
 * @return bool
 */
function session_regenerate_id ($delete_old_session = null) {}

/**
 * Decodes session data from a string
 * @link http://php.net/manual/en/function.session-decode.php
 * @param data string
 * @return bool
 */
function session_decode ($data) {}

/**
 * Register one or more global variables with the current session
 * @link http://php.net/manual/en/function.session-register.php
 * @param name mixed
 * @param ... mixed[optional]
 * @return bool
 */
function session_register ($name) {}

/**
 * Unregister a global variable from the current session
 * @link http://php.net/manual/en/function.session-unregister.php
 * @param name string
 * @return bool
 */
function session_unregister ($name) {}

/**
 * Find out whether a global variable is registered in a session
 * @link http://php.net/manual/en/function.session-is-registered.php
 * @param name string
 * @return bool
 */
function session_is_registered ($name) {}

/**
 * Encodes the current session data as a string
 * @link http://php.net/manual/en/function.session-encode.php
 * @return string the contents of the current session encoded.
 */
function session_encode () {}

/**
 * Initialize session data
 * @link http://php.net/manual/en/function.session-start.php
 * @return bool
 */
function session_start () {}

/**
 * Destroys all data registered to a session
 * @link http://php.net/manual/en/function.session-destroy.php
 * @return bool
 */
function session_destroy () {}

/**
 * Free all session variables
 * @link http://php.net/manual/en/function.session-unset.php
 * @return void
 */
function session_unset () {}

/**
 * Sets user-level session storage functions
 * @link http://php.net/manual/en/function.session-set-save-handler.php
 * @param open callback
 * @param close callback
 * @param read callback
 * @param write callback
 * @param destroy callback
 * @param gc callback
 * @return bool
 */
function session_set_save_handler ($open, $close, $read, $write, $destroy, $gc) {}

/**
 * Get and/or set the current cache limiter
 * @link http://php.net/manual/en/function.session-cache-limiter.php
 * @param cache_limiter string[optional]
 * @return string the name of the current cache limiter. 
 */
function session_cache_limiter ($cache_limiter = null) {}

/**
 * Return current cache expire
 * @link http://php.net/manual/en/function.session-cache-expire.php
 * @param new_cache_expire int[optional]
 * @return int the current setting of session.cache_expire.
 */
function session_cache_expire ($new_cache_expire = null) {}

/**
 * Set the session cookie parameters
 * @link http://php.net/manual/en/function.session-set-cookie-params.php
 * @param lifetime int
 * @param path string[optional]
 * @param domain string[optional]
 * @param secure bool[optional]
 * @param httponly bool[optional]
 * @return void
 */
function session_set_cookie_params ($lifetime, $path = null, $domain = null, $secure = null, $httponly = null) {}

/**
 * Get the session cookie parameters
 * @link http://php.net/manual/en/function.session-get-cookie-params.php
 * @return array an array with the current session cookie information, the array
 */
function session_get_cookie_params () {}

/**
 * Write session data and end session
 * @link http://php.net/manual/en/function.session-write-close.php
 * @return void
 */
function session_write_close () {}

/**
 * &Alias; <function>session_write_close</function>
 * @link http://php.net/manual/en/function.session-commit.php
 */
function session_commit () {}

// End of session v.

// Start of pcre v.

/**
 * Perform a regular expression match
 * @link http://php.net/manual/en/function.preg-match.php
 * @param pattern string
 * @param subject string
 * @param matches array[optional]
 * @param flags int[optional]
 * @param offset int[optional]
 * @return int
 */
function preg_match ($pattern, $subject, array &$matches = null, $flags = null, $offset = null) {}

/**
 * Perform a global regular expression match
 * @link http://php.net/manual/en/function.preg-match-all.php
 * @param pattern string
 * @param subject string
 * @param matches array
 * @param flags int[optional]
 * @param offset int[optional]
 * @return int the number of full pattern matches (which might be zero),
 */
function preg_match_all ($pattern, $subject, array &$matches, $flags = null, $offset = null) {}

/**
 * Perform a regular expression search and replace
 * @link http://php.net/manual/en/function.preg-replace.php
 * @param pattern mixed
 * @param replacement mixed
 * @param subject mixed
 * @param limit int[optional]
 * @param count int[optional]
 * @return mixed
 */
function preg_replace ($pattern, $replacement, $subject, $limit = null, &$count = null) {}

/**
 * Perform a regular expression search and replace using a callback
 * @link http://php.net/manual/en/function.preg-replace-callback.php
 * @param pattern mixed
 * @param callback callback
 * @param subject mixed
 * @param limit int[optional]
 * @param count int[optional]
 * @return mixed
 */
function preg_replace_callback ($pattern, $callback, $subject, $limit = null, &$count = null) {}

/**
 * Split string by a regular expression
 * @link http://php.net/manual/en/function.preg-split.php
 * @param pattern string
 * @param subject string
 * @param limit int[optional]
 * @param flags int[optional]
 * @return array an array containing substrings of subject
 */
function preg_split ($pattern, $subject, $limit = null, $flags = null) {}

/**
 * Quote regular expression characters
 * @link http://php.net/manual/en/function.preg-quote.php
 * @param str string
 * @param delimiter string[optional]
 * @return string the quoted string.
 */
function preg_quote ($str, $delimiter = null) {}

/**
 * Return array entries that match the pattern
 * @link http://php.net/manual/en/function.preg-grep.php
 * @param pattern string
 * @param input array
 * @param flags int[optional]
 * @return array an array indexed using the keys from the
 */
function preg_grep ($pattern, array $input, $flags = null) {}

/**
 * Returns the error code of the last PCRE regex execution
 * @link http://php.net/manual/en/function.preg-last-error.php
 * @return int one of the following constants (
 */
function preg_last_error () {}

define ('PREG_PATTERN_ORDER', 1);
define ('PREG_SET_ORDER', 2);
define ('PREG_OFFSET_CAPTURE', 256);
define ('PREG_SPLIT_NO_EMPTY', 1);
define ('PREG_SPLIT_DELIM_CAPTURE', 2);
define ('PREG_SPLIT_OFFSET_CAPTURE', 4);
define ('PREG_GREP_INVERT', 1);
define ('PREG_NO_ERROR', 0);
define ('PREG_INTERNAL_ERROR', 1);
define ('PREG_BACKTRACK_LIMIT_ERROR', 2);
define ('PREG_RECURSION_LIMIT_ERROR', 3);
define ('PREG_BAD_UTF8_ERROR', 4);
define ('PCRE_VERSION', '7.2 2007-06-19');

// End of pcre v.

// Start of SimpleXML v.0.1

class SimpleXMLElement implements Traversable {

	/**
	 * Creates a new SimpleXMLElement object
	 * @link http://php.net/manual/en/function.simplexml-element-construct.php
	 */
	final public function __construct () {}

	/**
	 * Return a well-formed XML string based on SimpleXML element
	 * @link http://php.net/manual/en/function.simplexml-element-asXML.php
	 * @param filename string[optional]
	 * @return mixed
	 */
	public function asXML ($filename = null) {}

	public function saveXML () {}

	/**
	 * Runs XPath query on XML data
	 * @link http://php.net/manual/en/function.simplexml-element-xpath.php
	 * @param path string
	 * @return array an array of SimpleXMLElement objects or false in
	 */
	public function xpath ($path) {}

	/**
	 * Creates a prefix/ns context for the next XPath query
	 * @link http://php.net/manual/en/function.simplexml-element-registerXPathNamespace.php
	 * @param prefix string
	 * @param ns string
	 * @return bool
	 */
	public function registerXPathNamespace ($prefix, $ns) {}

	/**
	 * Identifies an element's attributes
	 * @link http://php.net/manual/en/function.simplexml-element-attributes.php
	 * @param ns string[optional]
	 * @param is_prefix bool[optional]
	 * @return SimpleXMLElement
	 */
	public function attributes ($ns = null, $is_prefix = null) {}

	/**
	 * Finds children of given node
	 * @link http://php.net/manual/en/function.simplexml-element-children.php
	 * @param ns string[optional]
	 * @param is_prefix bool[optional]
	 * @return SimpleXMLElement
	 */
	public function children ($ns = null, $is_prefix = null) {}

	/**
	 * Returns namespaces used in document
	 * @link http://php.net/manual/en/function.simplexml-element-getNamespaces.php
	 * @param recursive bool[optional]
	 * @return array
	 */
	public function getNamespaces ($recursive = null) {}

	/**
	 * Returns namespaces declared in document
	 * @link http://php.net/manual/en/function.simplexml-element-getDocNamespaces.php
	 * @param recursive bool[optional]
	 * @return array
	 */
	public function getDocNamespaces ($recursive = null) {}

	/**
	 * Gets the name of the XML element
	 * @link http://php.net/manual/en/function.simplexml-element-getName.php
	 * @return string
	 */
	public function getName () {}

	/**
	 * Adds a child element to the XML node
	 * @link http://php.net/manual/en/function.simplexml-element-addChild.php
	 * @param name string
	 * @param value string[optional]
	 * @param namespace string[optional]
	 * @return SimpleXMLElement
	 */
	public function addChild ($name, $value = null, $namespace = null) {}

	/**
	 * Adds an attribute to the SimpleXML element
	 * @link http://php.net/manual/en/function.simplexml-element-addAttribute.php
	 * @param name string
	 * @param value string
	 * @param namespace string[optional]
	 * @return void
	 */
	public function addAttribute ($name, $value, $namespace = null) {}

}

/**
 * Interprets an XML file into an object
 * @link http://php.net/manual/en/function.simplexml-load-file.php
 * @param filename string
 * @param class_name string[optional]
 * @param options int[optional]
 * @param ns string[optional]
 * @param is_prefix bool[optional]
 * @return object an object of class SimpleXMLElement with
 */
function simplexml_load_file ($filename, $class_name = null, $options = null, $ns = null, $is_prefix = null) {}

/**
 * Interprets a string of XML into an object
 * @link http://php.net/manual/en/function.simplexml-load-string.php
 * @param data string
 * @param class_name string[optional]
 * @param options int[optional]
 * @param ns string[optional]
 * @param is_prefix bool[optional]
 * @return object an object of class SimpleXMLElement with
 */
function simplexml_load_string ($data, $class_name = null, $options = null, $ns = null, $is_prefix = null) {}

/**
 * Get a <literal>SimpleXMLElement</literal> object from a DOM node.
 * @link http://php.net/manual/en/function.simplexml-import-dom.php
 * @param node DOMNode
 * @param class_name string[optional]
 * @return SimpleXMLElement a SimpleXMLElement or false on failure.
 */
function simplexml_import_dom (DOMNode $node, $class_name = null) {}

// End of SimpleXML v.0.1

// Start of SPL v.0.2

interface RecursiveIterator implements Iterator, Traversable {

	abstract public function hasChildren () {}

	abstract public function getChildren () {}

	abstract public function current () {}

	abstract public function next () {}

	abstract public function key () {}

	abstract public function valid () {}

	abstract public function rewind () {}

}

class RecursiveIteratorIterator implements Iterator, Traversable, OuterIterator {
	const LEAVES_ONLY = 0;
	const SELF_FIRST = 1;
	const CHILD_FIRST = 2;
	const CATCH_GET_CHILD = 16;


	/**
	 * @param iterator Traversable
	 * @param mode[optional]
	 * @param flags[optional]
	 */
	public function __construct (Traversable $iterator, $mode, $flags) {}

	/**
	 * Rewind the iterator to the first element of the top level inner iterator
	 * @link http://php.net/manual/en/function.RecursiveIteratorIterator-rewind.php
	 */
	public function rewind () {}

	/**
	 * Check whether the current position is valid
	 * @link http://php.net/manual/en/function.RecursiveIteratorIterator-valid.php
	 */
	public function valid () {}

	/**
	 * Access the current key
	 * @link http://php.net/manual/en/function.RecursiveIteratorIterator-key.php
	 */
	public function key () {}

	/**
	 * Access the current element value
	 * @link http://php.net/manual/en/function.RecursiveIteratorIterator-current.php
	 */
	public function current () {}

	/**
	 * Move forward to the next element
	 * @link http://php.net/manual/en/function.RecursiveIteratorIterator-next.php
	 */
	public function next () {}

	/**
	 * Get the current depth of the recursive iteration
	 * @link http://php.net/manual/en/function.RecursiveIteratorIterator-getDepth.php
	 */
	public function getDepth () {}

	/**
	 * The current active sub iterator
	 * @link http://php.net/manual/en/function.RecursiveIteratorIterator-getSubIterator.php
	 * @param level[optional]
	 */
	public function getSubIterator ($level) {}

	public function getInnerIterator () {}

	public function beginIteration () {}

	public function endIteration () {}

	public function callHasChildren () {}

	public function callGetChildren () {}

	public function beginChildren () {}

	public function endChildren () {}

	public function nextElement () {}

	/**
	 * @param max_depth[optional]
	 */
	public function setMaxDepth ($max_depth) {}

	public function getMaxDepth () {}

}

interface OuterIterator implements Iterator, Traversable {

	abstract public function getInnerIterator () {}

	abstract public function current () {}

	abstract public function next () {}

	abstract public function key () {}

	abstract public function valid () {}

	abstract public function rewind () {}

}

class IteratorIterator implements Iterator, Traversable, OuterIterator {

	/**
	 * @param iterator Traversable
	 */
	public function __construct (Traversable $iterator) {}

	public function rewind () {}

	public function valid () {}

	public function key () {}

	public function current () {}

	public function next () {}

	public function getInnerIterator () {}

}

abstract class FilterIterator extends IteratorIterator implements OuterIterator, Traversable, Iterator {

	/**
	 * @param iterator Iterator
	 */
	public function __construct (Iterator $iterator) {}

	/**
	 * Rewind the iterator
	 * @link http://php.net/manual/en/function.FilterIterator-rewind.php
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://php.net/manual/en/function.FilterIterator-valid.php
	 */
	public function valid () {}

	/**
	 * Get the current key
	 * @link http://php.net/manual/en/function.FilterIterator-key.php
	 */
	public function key () {}

	/**
	 * Get the current element value
	 * @link http://php.net/manual/en/function.FilterIterator-current.php
	 */
	public function current () {}

	/**
	 * Move the iterator forward
	 * @link http://php.net/manual/en/function.FilterIterator-next.php
	 */
	public function next () {}

	/**
	 * Get the inner iterator
	 * @link http://php.net/manual/en/function.FilterIterator-getInnerIterator.php
	 */
	public function getInnerIterator () {}

	abstract public function accept () {}

}

abstract class RecursiveFilterIterator extends FilterIterator implements Iterator, Traversable, OuterIterator, RecursiveIterator {

	/**
	 * @param iterator RecursiveIterator
	 */
	public function __construct (RecursiveIterator $iterator) {}

	public function hasChildren () {}

	public function getChildren () {}

	/**
	 * Rewind the iterator
	 * @link http://php.net/manual/en/function.FilterIterator-rewind.php
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://php.net/manual/en/function.FilterIterator-valid.php
	 */
	public function valid () {}

	/**
	 * Get the current key
	 * @link http://php.net/manual/en/function.FilterIterator-key.php
	 */
	public function key () {}

	/**
	 * Get the current element value
	 * @link http://php.net/manual/en/function.FilterIterator-current.php
	 */
	public function current () {}

	/**
	 * Move the iterator forward
	 * @link http://php.net/manual/en/function.FilterIterator-next.php
	 */
	public function next () {}

	/**
	 * Get the inner iterator
	 * @link http://php.net/manual/en/function.FilterIterator-getInnerIterator.php
	 */
	public function getInnerIterator () {}

	abstract public function accept () {}

}

class ParentIterator extends RecursiveFilterIterator implements RecursiveIterator, OuterIterator, Traversable, Iterator {

	/**
	 * @param iterator RecursiveIterator
	 */
	public function __construct (RecursiveIterator $iterator) {}

	public function accept () {}

	public function hasChildren () {}

	public function getChildren () {}

	/**
	 * Rewind the iterator
	 * @link http://php.net/manual/en/function.FilterIterator-rewind.php
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://php.net/manual/en/function.FilterIterator-valid.php
	 */
	public function valid () {}

	/**
	 * Get the current key
	 * @link http://php.net/manual/en/function.FilterIterator-key.php
	 */
	public function key () {}

	/**
	 * Get the current element value
	 * @link http://php.net/manual/en/function.FilterIterator-current.php
	 */
	public function current () {}

	/**
	 * Move the iterator forward
	 * @link http://php.net/manual/en/function.FilterIterator-next.php
	 */
	public function next () {}

	/**
	 * Get the inner iterator
	 * @link http://php.net/manual/en/function.FilterIterator-getInnerIterator.php
	 */
	public function getInnerIterator () {}

}

interface Countable  {

	abstract public function count () {}

}

interface SeekableIterator implements Iterator, Traversable {

	/**
	 * @param position
	 */
	abstract public function seek ($position) {}

	abstract public function current () {}

	abstract public function next () {}

	abstract public function key () {}

	abstract public function valid () {}

	abstract public function rewind () {}

}

class LimitIterator extends IteratorIterator implements OuterIterator, Traversable, Iterator {

	/**
	 * @param iterator Iterator
	 * @param offset[optional]
	 * @param count[optional]
	 */
	public function __construct (Iterator $iterator, $offset, $count) {}

	/**
	 * Rewind the iterator to the specified starting offset
	 * @link http://php.net/manual/en/function.LimitIterator-rewind.php
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://php.net/manual/en/function.LimitIterator-valid.php
	 */
	public function valid () {}

	public function key () {}

	public function current () {}

	/**
	 * Move the iterator forward
	 * @link http://php.net/manual/en/function.LimitIterator-next.php
	 */
	public function next () {}

	/**
	 * Seek to the given position
	 * @link http://php.net/manual/en/function.LimitIterator-seek.php
	 * @param position
	 */
	public function seek ($position) {}

	/**
	 * Return the current position
	 * @link http://php.net/manual/en/function.LimitIterator-getPosition.php
	 */
	public function getPosition () {}

	public function getInnerIterator () {}

}

class CachingIterator extends IteratorIterator implements OuterIterator, Traversable, Iterator, ArrayAccess, Countable {
	const CALL_TOSTRING = 1;
	const CATCH_GET_CHILD = 16;
	const TOSTRING_USE_KEY = 2;
	const TOSTRING_USE_CURRENT = 4;
	const TOSTRING_USE_INNER = 8;
	const FULL_CACHE = 256;


	/**
	 * @param iterator Iterator
	 * @param flags[optional]
	 */
	public function __construct (Iterator $iterator, $flags) {}

	/**
	 * Rewind the iterator
	 * @link http://php.net/manual/en/function.CachingIterator-rewind.php
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://php.net/manual/en/function.CachingIterator-valid.php
	 */
	public function valid () {}

	public function key () {}

	public function current () {}

	/**
	 * Move the iterator forward
	 * @link http://php.net/manual/en/function.CachingIterator-next.php
	 */
	public function next () {}

	/**
	 * Check whether the inner iterator has a valid next element
	 * @link http://php.net/manual/en/function.CachingIterator-hasNext.php
	 */
	public function hasNext () {}

	/**
	 * Return the string representation of the current element
	 * @link http://php.net/manual/en/function.CachingIterator-toString.php
	 */
	public function __toString () {}

	public function getInnerIterator () {}

	public function getFlags () {}

	/**
	 * @param flags
	 */
	public function setFlags ($flags) {}

	/**
	 * @param index
	 */
	public function offsetGet ($index) {}

	/**
	 * @param index
	 * @param newval
	 */
	public function offsetSet ($index, $newval) {}

	/**
	 * @param index
	 */
	public function offsetUnset ($index) {}

	/**
	 * @param index
	 */
	public function offsetExists ($index) {}

	public function getCache () {}

	public function count () {}

}

class RecursiveCachingIterator extends CachingIterator implements Countable, ArrayAccess, Iterator, Traversable, OuterIterator, RecursiveIterator {
	const CALL_TOSTRING = 1;
	const CATCH_GET_CHILD = 16;
	const TOSTRING_USE_KEY = 2;
	const TOSTRING_USE_CURRENT = 4;
	const TOSTRING_USE_INNER = 8;
	const FULL_CACHE = 256;


	/**
	 * @param iterator Iterator
	 * @param flags[optional]
	 */
	public function __construct (Iterator $iterator, $flags) {}

	public function hasChildren () {}

	public function getChildren () {}

	/**
	 * Rewind the iterator
	 * @link http://php.net/manual/en/function.CachingIterator-rewind.php
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://php.net/manual/en/function.CachingIterator-valid.php
	 */
	public function valid () {}

	public function key () {}

	public function current () {}

	/**
	 * Move the iterator forward
	 * @link http://php.net/manual/en/function.CachingIterator-next.php
	 */
	public function next () {}

	/**
	 * Check whether the inner iterator has a valid next element
	 * @link http://php.net/manual/en/function.CachingIterator-hasNext.php
	 */
	public function hasNext () {}

	/**
	 * Return the string representation of the current element
	 * @link http://php.net/manual/en/function.CachingIterator-toString.php
	 */
	public function __toString () {}

	public function getInnerIterator () {}

	public function getFlags () {}

	/**
	 * @param flags
	 */
	public function setFlags ($flags) {}

	/**
	 * @param index
	 */
	public function offsetGet ($index) {}

	/**
	 * @param index
	 * @param newval
	 */
	public function offsetSet ($index, $newval) {}

	/**
	 * @param index
	 */
	public function offsetUnset ($index) {}

	/**
	 * @param index
	 */
	public function offsetExists ($index) {}

	public function getCache () {}

	public function count () {}

}

class NoRewindIterator extends IteratorIterator implements OuterIterator, Traversable, Iterator {

	/**
	 * @param iterator Iterator
	 */
	public function __construct (Iterator $iterator) {}

	public function rewind () {}

	public function valid () {}

	public function key () {}

	public function current () {}

	public function next () {}

	public function getInnerIterator () {}

}

class AppendIterator extends IteratorIterator implements OuterIterator, Traversable, Iterator {

	public function __construct () {}

	/**
	 * @param iterator Iterator
	 */
	public function append (Iterator $iterator) {}

	public function rewind () {}

	public function valid () {}

	public function key () {}

	public function current () {}

	public function next () {}

	public function getInnerIterator () {}

	public function getIteratorIndex () {}

	public function getArrayIterator () {}

}

class InfiniteIterator extends IteratorIterator implements OuterIterator, Traversable, Iterator {

	/**
	 * @param iterator Iterator
	 */
	public function __construct (Iterator $iterator) {}

	public function next () {}

	public function rewind () {}

	public function valid () {}

	public function key () {}

	public function current () {}

	public function getInnerIterator () {}

}

class RegexIterator extends FilterIterator implements Iterator, Traversable, OuterIterator {
	const USE_KEY = 1;
	const MATCH = 0;
	const GET_MATCH = 1;
	const ALL_MATCHES = 2;
	const SPLIT = 3;
	const REPLACE = 4;

	public $replacement;


	/**
	 * @param iterator Iterator
	 * @param regex
	 * @param mode[optional]
	 * @param flags[optional]
	 * @param preg_flags[optional]
	 */
	public function __construct (Iterator $iterator, $regex, $mode, $flags, $preg_flags) {}

	public function accept () {}

	public function getMode () {}

	/**
	 * @param mode
	 */
	public function setMode ($mode) {}

	public function getFlags () {}

	/**
	 * @param flags
	 */
	public function setFlags ($flags) {}

	public function getPregFlags () {}

	/**
	 * @param preg_flags
	 */
	public function setPregFlags ($preg_flags) {}

	/**
	 * Rewind the iterator
	 * @link http://php.net/manual/en/function.FilterIterator-rewind.php
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://php.net/manual/en/function.FilterIterator-valid.php
	 */
	public function valid () {}

	/**
	 * Get the current key
	 * @link http://php.net/manual/en/function.FilterIterator-key.php
	 */
	public function key () {}

	/**
	 * Get the current element value
	 * @link http://php.net/manual/en/function.FilterIterator-current.php
	 */
	public function current () {}

	/**
	 * Move the iterator forward
	 * @link http://php.net/manual/en/function.FilterIterator-next.php
	 */
	public function next () {}

	/**
	 * Get the inner iterator
	 * @link http://php.net/manual/en/function.FilterIterator-getInnerIterator.php
	 */
	public function getInnerIterator () {}

}

class RecursiveRegexIterator extends RegexIterator implements OuterIterator, Traversable, Iterator, RecursiveIterator {
	const USE_KEY = 1;
	const MATCH = 0;
	const GET_MATCH = 1;
	const ALL_MATCHES = 2;
	const SPLIT = 3;
	const REPLACE = 4;

	public $replacement;


	/**
	 * @param iterator RecursiveIterator
	 * @param regex
	 * @param mode[optional]
	 * @param flags[optional]
	 * @param preg_flags[optional]
	 */
	public function __construct (RecursiveIterator $iterator, $regex, $mode, $flags, $preg_flags) {}

	public function hasChildren () {}

	public function getChildren () {}

	public function accept () {}

	public function getMode () {}

	/**
	 * @param mode
	 */
	public function setMode ($mode) {}

	public function getFlags () {}

	/**
	 * @param flags
	 */
	public function setFlags ($flags) {}

	public function getPregFlags () {}

	/**
	 * @param preg_flags
	 */
	public function setPregFlags ($preg_flags) {}

	/**
	 * Rewind the iterator
	 * @link http://php.net/manual/en/function.FilterIterator-rewind.php
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://php.net/manual/en/function.FilterIterator-valid.php
	 */
	public function valid () {}

	/**
	 * Get the current key
	 * @link http://php.net/manual/en/function.FilterIterator-key.php
	 */
	public function key () {}

	/**
	 * Get the current element value
	 * @link http://php.net/manual/en/function.FilterIterator-current.php
	 */
	public function current () {}

	/**
	 * Move the iterator forward
	 * @link http://php.net/manual/en/function.FilterIterator-next.php
	 */
	public function next () {}

	/**
	 * Get the inner iterator
	 * @link http://php.net/manual/en/function.FilterIterator-getInnerIterator.php
	 */
	public function getInnerIterator () {}

}

class EmptyIterator implements Iterator, Traversable {

	public function rewind () {}

	public function valid () {}

	public function key () {}

	public function current () {}

	public function next () {}

}

class ArrayObject implements IteratorAggregate, Traversable, ArrayAccess, Countable {
	const STD_PROP_LIST = 1;
	const ARRAY_AS_PROPS = 2;


	/**
	 * Construct a new array object
	 * @link http://php.net/manual/en/function.ArrayObject-construct.php
	 * @param array
	 */
	public function __construct ($array) {}

	/**
	 * Returns whether the requested $index exists
	 * @link http://php.net/manual/en/function.ArrayObject-offsetExists.php
	 * @param index
	 */
	public function offsetExists ($index) {}

	/**
	 * Returns the value at the specified $index
	 * @link http://php.net/manual/en/function.ArrayObject-offsetGet.php
	 * @param index
	 */
	public function offsetGet ($index) {}

	/**
	 * Sets the value at the specified $index to $newval
	 * @link http://php.net/manual/en/function.ArrayObject-offsetSet.php
	 * @param index
	 * @param newval
	 */
	public function offsetSet ($index, $newval) {}

	/**
	 * Unsets the value at the specified $index
	 * @link http://php.net/manual/en/function.ArrayObject-offsetUnset.php
	 * @param index
	 */
	public function offsetUnset ($index) {}

	/**
	 * Appends the value
	 * @link http://php.net/manual/en/function.ArrayObject-append.php
	 * @param value
	 */
	public function append ($value) {}

	public function getArrayCopy () {}

	/**
	 * Return the number of elements in the Iterator
	 * @link http://php.net/manual/en/function.ArrayObject-count.php
	 */
	public function count () {}

	public function getFlags () {}

	/**
	 * @param flags
	 */
	public function setFlags ($flags) {}

	public function asort () {}

	public function ksort () {}

	/**
	 * @param cmp_function
	 */
	public function uasort ($cmp_function) {}

	/**
	 * @param cmp_function
	 */
	public function uksort ($cmp_function) {}

	public function natsort () {}

	public function natcasesort () {}

	/**
	 * Create a new iterator from an ArrayObject instance
	 * @link http://php.net/manual/en/function.ArrayObject-getIterator.php
	 */
	public function getIterator () {}

	/**
	 * @param array
	 */
	public function exchangeArray ($array) {}

	/**
	 * @param iteratorClass
	 */
	public function setIteratorClass ($iteratorClass) {}

	public function getIteratorClass () {}

}

class ArrayIterator implements Iterator, Traversable, ArrayAccess, SeekableIterator, Countable {
	const STD_PROP_LIST = 1;
	const ARRAY_AS_PROPS = 2;


	/**
	 * @param array
	 */
	public function __construct ($array) {}

	/**
	 * @param index
	 */
	public function offsetExists ($index) {}

	/**
	 * @param index
	 */
	public function offsetGet ($index) {}

	/**
	 * @param index
	 * @param newval
	 */
	public function offsetSet ($index, $newval) {}

	/**
	 * @param index
	 */
	public function offsetUnset ($index) {}

	/**
	 * @param value
	 */
	public function append ($value) {}

	public function getArrayCopy () {}

	public function count () {}

	public function getFlags () {}

	/**
	 * @param flags
	 */
	public function setFlags ($flags) {}

	public function asort () {}

	public function ksort () {}

	/**
	 * @param cmp_function
	 */
	public function uasort ($cmp_function) {}

	/**
	 * @param cmp_function
	 */
	public function uksort ($cmp_function) {}

	public function natsort () {}

	public function natcasesort () {}

	/**
	 * Rewind array back to the start
	 * @link http://php.net/manual/en/function.ArrayIterator-rewind.php
	 */
	public function rewind () {}

	/**
	 * Return current array entry
	 * @link http://php.net/manual/en/function.ArrayIterator-current.php
	 */
	public function current () {}

	/**
	 * Return current array key
	 * @link http://php.net/manual/en/function.ArrayIterator-key.php
	 */
	public function key () {}

	/**
	 * Move to next entry
	 * @link http://php.net/manual/en/function.ArrayIterator-next.php
	 */
	public function next () {}

	/**
	 * Check whether array contains more entries
	 * @link http://php.net/manual/en/function.ArrayIterator-valid.php
	 */
	public function valid () {}

	/**
	 * Seek to position
	 * @link http://php.net/manual/en/function.ArrayIterator-seek.php
	 * @param position
	 */
	public function seek ($position) {}

}

class RecursiveArrayIterator extends ArrayIterator implements SeekableIterator, ArrayAccess, Traversable, Iterator, RecursiveIterator {

	public function hasChildren () {}

	public function getChildren () {}

	/**
	 * @param array
	 */
	public function __construct ($array) {}

	/**
	 * @param index
	 */
	public function offsetExists ($index) {}

	/**
	 * @param index
	 */
	public function offsetGet ($index) {}

	/**
	 * @param index
	 * @param newval
	 */
	public function offsetSet ($index, $newval) {}

	/**
	 * @param index
	 */
	public function offsetUnset ($index) {}

	/**
	 * @param value
	 */
	public function append ($value) {}

	public function getArrayCopy () {}

	public function count () {}

	public function getFlags () {}

	/**
	 * @param flags
	 */
	public function setFlags ($flags) {}

	public function asort () {}

	public function ksort () {}

	/**
	 * @param cmp_function
	 */
	public function uasort ($cmp_function) {}

	/**
	 * @param cmp_function
	 */
	public function uksort ($cmp_function) {}

	public function natsort () {}

	public function natcasesort () {}

	/**
	 * Rewind array back to the start
	 * @link http://php.net/manual/en/function.ArrayIterator-rewind.php
	 */
	public function rewind () {}

	/**
	 * Return current array entry
	 * @link http://php.net/manual/en/function.ArrayIterator-current.php
	 */
	public function current () {}

	/**
	 * Return current array key
	 * @link http://php.net/manual/en/function.ArrayIterator-key.php
	 */
	public function key () {}

	/**
	 * Move to next entry
	 * @link http://php.net/manual/en/function.ArrayIterator-next.php
	 */
	public function next () {}

	/**
	 * Check whether array contains more entries
	 * @link http://php.net/manual/en/function.ArrayIterator-valid.php
	 */
	public function valid () {}

	/**
	 * Seek to position
	 * @link http://php.net/manual/en/function.ArrayIterator-seek.php
	 * @param position
	 */
	public function seek ($position) {}

}

class SplFileInfo  {

	/**
	 * @param file_name
	 */
	public function __construct ($file_name) {}

	public function getPath () {}

	public function getFilename () {}

	/**
	 * @param suffix[optional]
	 */
	public function getBasename ($suffix) {}

	public function getPathname () {}

	public function getPerms () {}

	public function getInode () {}

	public function getSize () {}

	public function getOwner () {}

	public function getGroup () {}

	public function getATime () {}

	public function getMTime () {}

	public function getCTime () {}

	public function getType () {}

	public function isWritable () {}

	public function isReadable () {}

	public function isExecutable () {}

	public function isFile () {}

	public function isDir () {}

	public function isLink () {}

	public function getLinkTarget () {}

	public function getRealPath () {}

	/**
	 * @param class_name[optional]
	 */
	public function getFileInfo ($class_name) {}

	/**
	 * @param class_name[optional]
	 */
	public function getPathInfo ($class_name) {}

	/**
	 * @param open_mode[optional]
	 * @param use_include_path[optional]
	 * @param context[optional]
	 */
	public function openFile ($open_mode, $use_include_path, $context) {}

	/**
	 * @param class_name[optional]
	 */
	public function setFileClass ($class_name) {}

	/**
	 * @param class_name[optional]
	 */
	public function setInfoClass ($class_name) {}

	public function __toString () {}

}

class DirectoryIterator extends SplFileInfo implements Iterator, Traversable {

	/**
	 * Constructs a new dir iterator from a path
	 * @link http://php.net/manual/en/function.DirectoryIterator-construct.php
	 * @param path
	 */
	public function __construct ($path) {}

	/**
	 * Return filename of current dir entry
	 * @link http://php.net/manual/en/function.DirectoryIterator-getFilename.php
	 */
	public function getFilename () {}

	/**
	 * @param suffix[optional]
	 */
	public function getBasename ($suffix) {}

	/**
	 * Returns true if current entry is '.' or '..'
	 * @link http://php.net/manual/en/function.DirectoryIterator-isDot.php
	 */
	public function isDot () {}

	/**
	 * Rewind dir back to the start
	 * @link http://php.net/manual/en/function.DirectoryIterator-rewind.php
	 */
	public function rewind () {}

	/**
	 * Check whether dir contains more entries
	 * @link http://php.net/manual/en/function.DirectoryIterator-valid.php
	 */
	public function valid () {}

	/**
	 * Return current dir entry
	 * @link http://php.net/manual/en/function.DirectoryIterator-key.php
	 */
	public function key () {}

	/**
	 * Return this (needed for Iterator interface)
	 * @link http://php.net/manual/en/function.DirectoryIterator-current.php
	 */
	public function current () {}

	/**
	 * Move to next entry
	 * @link http://php.net/manual/en/function.DirectoryIterator-next.php
	 */
	public function next () {}

	public function __toString () {}

	public function getPath () {}

	public function getPathname () {}

	public function getPerms () {}

	public function getInode () {}

	public function getSize () {}

	public function getOwner () {}

	public function getGroup () {}

	public function getATime () {}

	public function getMTime () {}

	public function getCTime () {}

	public function getType () {}

	public function isWritable () {}

	public function isReadable () {}

	public function isExecutable () {}

	public function isFile () {}

	public function isDir () {}

	public function isLink () {}

	public function getLinkTarget () {}

	public function getRealPath () {}

	/**
	 * @param class_name[optional]
	 */
	public function getFileInfo ($class_name) {}

	/**
	 * @param class_name[optional]
	 */
	public function getPathInfo ($class_name) {}

	/**
	 * @param open_mode[optional]
	 * @param use_include_path[optional]
	 * @param context[optional]
	 */
	public function openFile ($open_mode, $use_include_path, $context) {}

	/**
	 * @param class_name[optional]
	 */
	public function setFileClass ($class_name) {}

	/**
	 * @param class_name[optional]
	 */
	public function setInfoClass ($class_name) {}

}

class RecursiveDirectoryIterator extends DirectoryIterator implements Traversable, Iterator, RecursiveIterator {
	const CURRENT_MODE_MASK = 240;
	const CURRENT_AS_PATHNAME = 32;
	const CURRENT_AS_FILEINFO = 16;
	const CURRENT_AS_SELF = 0;
	const KEY_MODE_MASK = 3840;
	const KEY_AS_PATHNAME = 0;
	const KEY_AS_FILENAME = 256;
	const NEW_CURRENT_AND_KEY = 272;


	/**
	 * @param path
	 * @param flags[optional]
	 */
	public function __construct ($path, $flags) {}

	/**
	 * Rewind dir back to the start
	 * @link http://php.net/manual/en/function.RecursiveDirectoryIterator-rewind.php
	 */
	public function rewind () {}

	/**
	 * Move to next entry
	 * @link http://php.net/manual/en/function.RecursiveDirectoryIterator-next.php
	 */
	public function next () {}

	/**
	 * Return path and filename of current dir entry
	 * @link http://php.net/manual/en/function.RecursiveDirectoryIterator-key.php
	 */
	public function key () {}

	public function current () {}

	/**
	 * Returns whether current entry is a directory and not '.' or '..'
	 * @link http://php.net/manual/en/function.RecursiveDirectoryIterator-hasChildren.php
	 * @param allow_links[optional]
	 */
	public function hasChildren ($allow_links) {}

	/**
	 * Returns an iterator for the current entry if it is a directory
	 * @link http://php.net/manual/en/function.RecursiveDirectoryIterator-getChildren.php
	 */
	public function getChildren () {}

	public function getSubPath () {}

	public function getSubPathname () {}

	/**
	 * Return filename of current dir entry
	 * @link http://php.net/manual/en/function.DirectoryIterator-getFilename.php
	 */
	public function getFilename () {}

	/**
	 * @param suffix[optional]
	 */
	public function getBasename ($suffix) {}

	/**
	 * Returns true if current entry is '.' or '..'
	 * @link http://php.net/manual/en/function.DirectoryIterator-isDot.php
	 */
	public function isDot () {}

	/**
	 * Check whether dir contains more entries
	 * @link http://php.net/manual/en/function.DirectoryIterator-valid.php
	 */
	public function valid () {}

	public function __toString () {}

	public function getPath () {}

	public function getPathname () {}

	public function getPerms () {}

	public function getInode () {}

	public function getSize () {}

	public function getOwner () {}

	public function getGroup () {}

	public function getATime () {}

	public function getMTime () {}

	public function getCTime () {}

	public function getType () {}

	public function isWritable () {}

	public function isReadable () {}

	public function isExecutable () {}

	public function isFile () {}

	public function isDir () {}

	public function isLink () {}

	public function getLinkTarget () {}

	public function getRealPath () {}

	/**
	 * @param class_name[optional]
	 */
	public function getFileInfo ($class_name) {}

	/**
	 * @param class_name[optional]
	 */
	public function getPathInfo ($class_name) {}

	/**
	 * @param open_mode[optional]
	 * @param use_include_path[optional]
	 * @param context[optional]
	 */
	public function openFile ($open_mode, $use_include_path, $context) {}

	/**
	 * @param class_name[optional]
	 */
	public function setFileClass ($class_name) {}

	/**
	 * @param class_name[optional]
	 */
	public function setInfoClass ($class_name) {}

}

class SplFileObject extends SplFileInfo implements RecursiveIterator, Traversable, Iterator, SeekableIterator {
	const DROP_NEW_LINE = 1;
	const READ_AHEAD = 2;
	const SKIP_EMPTY = 6;
	const READ_CSV = 8;


	/**
	 * @param file_name
	 * @param open_mode[optional]
	 * @param use_include_path[optional]
	 * @param context[optional]
	 */
	public function __construct ($file_name, $open_mode, $use_include_path, $context) {}

	public function rewind () {}

	public function eof () {}

	public function valid () {}

	public function fgets () {}

	/**
	 * @param delimiter[optional]
	 * @param enclosure[optional]
	 */
	public function fgetcsv ($delimiter, $enclosure) {}

	/**
	 * @param delimiter[optional]
	 * @param enclosure[optional]
	 */
	public function setCsvControl ($delimiter, $enclosure) {}

	public function getCsvControl () {}

	/**
	 * @param operation
	 * @param wouldblock[optional]
	 */
	public function flock ($operation, &$wouldblock) {}

	public function fflush () {}

	public function ftell () {}

	/**
	 * @param pos
	 * @param whence[optional]
	 */
	public function fseek ($pos, $whence) {}

	public function fgetc () {}

	public function fpassthru () {}

	/**
	 * @param allowable_tags[optional]
	 */
	public function fgetss ($allowable_tags) {}

	/**
	 * @param format
	 */
	public function fscanf ($format) {}

	/**
	 * @param str
	 * @param length[optional]
	 */
	public function fwrite ($str, $length) {}

	public function fstat () {}

	/**
	 * @param size
	 */
	public function ftruncate ($size) {}

	public function current () {}

	public function key () {}

	public function next () {}

	/**
	 * @param flags
	 */
	public function setFlags ($flags) {}

	public function getFlags () {}

	/**
	 * @param max_len
	 */
	public function setMaxLineLen ($max_len) {}

	public function getMaxLineLen () {}

	public function hasChildren () {}

	public function getChildren () {}

	/**
	 * @param line_pos
	 */
	public function seek ($line_pos) {}

	public function getCurrentLine () {}

	public function __toString () {}

	public function getPath () {}

	public function getFilename () {}

	/**
	 * @param suffix[optional]
	 */
	public function getBasename ($suffix) {}

	public function getPathname () {}

	public function getPerms () {}

	public function getInode () {}

	public function getSize () {}

	public function getOwner () {}

	public function getGroup () {}

	public function getATime () {}

	public function getMTime () {}

	public function getCTime () {}

	public function getType () {}

	public function isWritable () {}

	public function isReadable () {}

	public function isExecutable () {}

	public function isFile () {}

	public function isDir () {}

	public function isLink () {}

	public function getLinkTarget () {}

	public function getRealPath () {}

	/**
	 * @param class_name[optional]
	 */
	public function getFileInfo ($class_name) {}

	/**
	 * @param class_name[optional]
	 */
	public function getPathInfo ($class_name) {}

	/**
	 * @param open_mode[optional]
	 * @param use_include_path[optional]
	 * @param context[optional]
	 */
	public function openFile ($open_mode, $use_include_path, $context) {}

	/**
	 * @param class_name[optional]
	 */
	public function setFileClass ($class_name) {}

	/**
	 * @param class_name[optional]
	 */
	public function setInfoClass ($class_name) {}

}

class SplTempFileObject extends SplFileObject implements SeekableIterator, Iterator, Traversable, RecursiveIterator {
	const DROP_NEW_LINE = 1;
	const READ_AHEAD = 2;
	const SKIP_EMPTY = 6;
	const READ_CSV = 8;


	/**
	 * @param max_memory[optional]
	 */
	public function __construct ($max_memory) {}

	public function rewind () {}

	public function eof () {}

	public function valid () {}

	public function fgets () {}

	/**
	 * @param delimiter[optional]
	 * @param enclosure[optional]
	 */
	public function fgetcsv ($delimiter, $enclosure) {}

	/**
	 * @param delimiter[optional]
	 * @param enclosure[optional]
	 */
	public function setCsvControl ($delimiter, $enclosure) {}

	public function getCsvControl () {}

	/**
	 * @param operation
	 * @param wouldblock[optional]
	 */
	public function flock ($operation, &$wouldblock) {}

	public function fflush () {}

	public function ftell () {}

	/**
	 * @param pos
	 * @param whence[optional]
	 */
	public function fseek ($pos, $whence) {}

	public function fgetc () {}

	public function fpassthru () {}

	/**
	 * @param allowable_tags[optional]
	 */
	public function fgetss ($allowable_tags) {}

	/**
	 * @param format
	 */
	public function fscanf ($format) {}

	/**
	 * @param str
	 * @param length[optional]
	 */
	public function fwrite ($str, $length) {}

	public function fstat () {}

	/**
	 * @param size
	 */
	public function ftruncate ($size) {}

	public function current () {}

	public function key () {}

	public function next () {}

	/**
	 * @param flags
	 */
	public function setFlags ($flags) {}

	public function getFlags () {}

	/**
	 * @param max_len
	 */
	public function setMaxLineLen ($max_len) {}

	public function getMaxLineLen () {}

	public function hasChildren () {}

	public function getChildren () {}

	/**
	 * @param line_pos
	 */
	public function seek ($line_pos) {}

	public function getCurrentLine () {}

	public function __toString () {}

	public function getPath () {}

	public function getFilename () {}

	/**
	 * @param suffix[optional]
	 */
	public function getBasename ($suffix) {}

	public function getPathname () {}

	public function getPerms () {}

	public function getInode () {}

	public function getSize () {}

	public function getOwner () {}

	public function getGroup () {}

	public function getATime () {}

	public function getMTime () {}

	public function getCTime () {}

	public function getType () {}

	public function isWritable () {}

	public function isReadable () {}

	public function isExecutable () {}

	public function isFile () {}

	public function isDir () {}

	public function isLink () {}

	public function getLinkTarget () {}

	public function getRealPath () {}

	/**
	 * @param class_name[optional]
	 */
	public function getFileInfo ($class_name) {}

	/**
	 * @param class_name[optional]
	 */
	public function getPathInfo ($class_name) {}

	/**
	 * @param open_mode[optional]
	 * @param use_include_path[optional]
	 * @param context[optional]
	 */
	public function openFile ($open_mode, $use_include_path, $context) {}

	/**
	 * @param class_name[optional]
	 */
	public function setFileClass ($class_name) {}

	/**
	 * @param class_name[optional]
	 */
	public function setInfoClass ($class_name) {}

}

class SimpleXMLIterator extends SimpleXMLElement implements Traversable, RecursiveIterator, Iterator, Countable {

	/**
	 * Rewind SimpleXML back to the start
	 * @link http://php.net/manual/en/function.SimpleXMLIterator-rewind.php
	 */
	public function rewind () {}

	/**
	 * Check whether SimpleXML contains more entries
	 * @link http://php.net/manual/en/function.SimpleXMLIterator-valid.php
	 */
	public function valid () {}

	/**
	 * Return current SimpleXML entry
	 * @link http://php.net/manual/en/function.SimpleXMLIterator-current.php
	 */
	public function current () {}

	/**
	 * Return current SimpleXML key
	 * @link http://php.net/manual/en/function.SimpleXMLIterator-key.php
	 */
	public function key () {}

	/**
	 * Move to next entry
	 * @link http://php.net/manual/en/function.SimpleXMLIterator-next.php
	 */
	public function next () {}

	/**
	 * Returns whether current entry is a SimpleXML object
	 * @link http://php.net/manual/en/function.SimpleXMLIterator-hasChildren.php
	 */
	public function hasChildren () {}

	/**
	 * Returns an iterator for the current entry if it is a SimpleXML object
	 * @link http://php.net/manual/en/function.SimpleXMLIterator-getChildren.php
	 */
	public function getChildren () {}

	public function count () {}

	/**
	 * Creates a new SimpleXMLElement object
	 * @link http://php.net/manual/en/function.simplexml-element-construct.php
	 */
	final public function __construct () {}

	/**
	 * Return a well-formed XML string based on SimpleXML element
	 * @link http://php.net/manual/en/function.simplexml-element-asXML.php
	 * @param filename string[optional]
	 * @return mixed
	 */
	public function asXML ($filename = null) {}

	public function saveXML () {}

	/**
	 * Runs XPath query on XML data
	 * @link http://php.net/manual/en/function.simplexml-element-xpath.php
	 * @param path string
	 * @return array an array of SimpleXMLElement objects or false in
	 */
	public function xpath ($path) {}

	/**
	 * Creates a prefix/ns context for the next XPath query
	 * @link http://php.net/manual/en/function.simplexml-element-registerXPathNamespace.php
	 * @param prefix string
	 * @param ns string
	 * @return bool
	 */
	public function registerXPathNamespace ($prefix, $ns) {}

	/**
	 * Identifies an element's attributes
	 * @link http://php.net/manual/en/function.simplexml-element-attributes.php
	 * @param ns string[optional]
	 * @param is_prefix bool[optional]
	 * @return SimpleXMLElement
	 */
	public function attributes ($ns = null, $is_prefix = null) {}

	/**
	 * Finds children of given node
	 * @link http://php.net/manual/en/function.simplexml-element-children.php
	 * @param ns string[optional]
	 * @param is_prefix bool[optional]
	 * @return SimpleXMLElement
	 */
	public function children ($ns = null, $is_prefix = null) {}

	/**
	 * Returns namespaces used in document
	 * @link http://php.net/manual/en/function.simplexml-element-getNamespaces.php
	 * @param recursive bool[optional]
	 * @return array
	 */
	public function getNamespaces ($recursive = null) {}

	/**
	 * Returns namespaces declared in document
	 * @link http://php.net/manual/en/function.simplexml-element-getDocNamespaces.php
	 * @param recursive bool[optional]
	 * @return array
	 */
	public function getDocNamespaces ($recursive = null) {}

	/**
	 * Gets the name of the XML element
	 * @link http://php.net/manual/en/function.simplexml-element-getName.php
	 * @return string
	 */
	public function getName () {}

	/**
	 * Adds a child element to the XML node
	 * @link http://php.net/manual/en/function.simplexml-element-addChild.php
	 * @param name string
	 * @param value string[optional]
	 * @param namespace string[optional]
	 * @return SimpleXMLElement
	 */
	public function addChild ($name, $value = null, $namespace = null) {}

	/**
	 * Adds an attribute to the SimpleXML element
	 * @link http://php.net/manual/en/function.simplexml-element-addAttribute.php
	 * @param name string
	 * @param value string
	 * @param namespace string[optional]
	 * @return void
	 */
	public function addAttribute ($name, $value, $namespace = null) {}

}

class LogicException extends Exception  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class BadFunctionCallException extends LogicException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class BadMethodCallException extends BadFunctionCallException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class DomainException extends LogicException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class InvalidArgumentException extends LogicException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class LengthException extends LogicException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class OutOfRangeException extends LogicException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class RuntimeException extends Exception  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class OutOfBoundsException extends RuntimeException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class OverflowException extends RuntimeException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class RangeException extends RuntimeException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class UnderflowException extends RuntimeException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class UnexpectedValueException extends RuntimeException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

interface SplObserver  {

	/**
	 * @param SplSubject SplSubject
	 */
	abstract public function update (SplSubject $SplSubject) {}

}

interface SplSubject  {

	/**
	 * @param SplObserver SplObserver
	 */
	abstract public function attach (SplObserver $SplObserver) {}

	/**
	 * @param SplObserver SplObserver
	 */
	abstract public function detach (SplObserver $SplObserver) {}

	abstract public function notify () {}

}

class SplObjectStorage implements Countable, Iterator, Traversable, Serializable {

	/**
	 * @param object
	 */
	public function attach ($object) {}

	/**
	 * @param object
	 */
	public function detach ($object) {}

	/**
	 * @param object
	 */
	public function contains ($object) {}

	public function count () {}

	public function rewind () {}

	public function valid () {}

	public function key () {}

	public function current () {}

	public function next () {}

	/**
	 * @param serialized
	 */
	public function unserialize ($serialized) {}

	public function serialize () {}

}

/**
 * Return available SPL classes
 * @link http://php.net/manual/en/function.spl-classes.php
 */
function spl_classes () {}

/**
 * Default implementation for __autoload()
 * @link http://php.net/manual/en/function.spl-autoload.php
 */
function spl_autoload () {}

/**
 * Register and return default file extensions for spl_autoload
 * @link http://php.net/manual/en/function.spl-autoload-extensions.php
 */
function spl_autoload_extensions () {}

/**
 * Register given function as __autoload() implementation
 * @link http://php.net/manual/en/function.spl-autoload-register.php
 */
function spl_autoload_register () {}

/**
 * Unregister given function as __autoload() implementation
 * @link http://php.net/manual/en/function.spl-autoload-unregister.php
 */
function spl_autoload_unregister () {}

/**
 * Return all registered __autoload() functions
 * @link http://php.net/manual/en/function.spl-autoload-functions.php
 */
function spl_autoload_functions () {}

/**
 * Try all registered __autoload() function to load the requested class
 * @link http://php.net/manual/en/function.spl-autoload-call.php
 */
function spl_autoload_call () {}

/**
 * Return the parent classes of the given class
 * @link http://php.net/manual/en/function.class-parents.php
 * @param class mixed
 * @param autoload bool[optional]
 * @return array an array or false on error.
 */
function class_parents ($class, $autoload = null) {}

/**
 * Return the interfaces which are implemented by the given class
 * @link http://php.net/manual/en/function.class-implements.php
 * @param class mixed
 * @param autoload bool[optional]
 * @return array an array or false on error.
 */
function class_implements ($class, $autoload = null) {}

/**
 * Return hash id for given object
 * @link http://php.net/manual/en/function.spl-object-hash.php
 */
function spl_object_hash () {}

/**
 * Copy the iterator into an array
 * @link http://php.net/manual/en/function.iterator-to-array.php
 * @param iterator Traversable
 * @param use_keys[optional]
 */
function iterator_to_array (Traversable $iterator, $use_keys) {}

/**
 * Count the elements in an iterator
 * @link http://php.net/manual/en/function.iterator-count.php
 * @param iterator Traversable
 */
function iterator_count (Traversable $iterator) {}

/**
 * @param iterator Traversable
 * @param function
 * @param args[optional]
 */
function iterator_apply (Traversable $iterator, $functionarray , $args = null) {}

// End of SPL v.0.2

// Start of standard v.5.2.4

class __PHP_Incomplete_Class  {
}

class php_user_filter  {
	public $filtername;
	public $params;


	/**
	 * @param in
	 * @param out
	 * @param consumed
	 * @param closing
	 */
	public function filter ($in, $out, &$consumed, $closing) {}

	public function onCreate () {}

	public function onClose () {}

}

class Directory  {

	public function close () {}

	public function rewind () {}

	public function read () {}

}

/**
 * Returns the value of a constant
 * @link http://php.net/manual/en/function.constant.php
 * @param name string
 * @return mixed the value of the constant, or &null; if the constant is not
 */
function constant ($name) {}

/**
 * Convert binary data into hexadecimal representation
 * @link http://php.net/manual/en/function.bin2hex.php
 * @param str string
 * @return string the hexadecimal representation of the given string.
 */
function bin2hex ($str) {}

/**
 * Delay execution
 * @link http://php.net/manual/en/function.sleep.php
 * @param seconds int
 * @return int zero on success, or false on errors.
 */
function sleep ($seconds) {}

/**
 * Delay execution in microseconds
 * @link http://php.net/manual/en/function.usleep.php
 * @param micro_seconds int
 * @return void
 */
function usleep ($micro_seconds) {}

/**
 * Delay for a number of seconds and nanoseconds
 * @link http://php.net/manual/en/function.time-nanosleep.php
 * @param seconds int
 * @param nanoseconds int
 * @return mixed
 */
function time_nanosleep ($seconds, $nanoseconds) {}

/**
 * Make the script sleep until the specified time
 * @link http://php.net/manual/en/function.time-sleep-until.php
 * @param timestamp float
 * @return bool
 */
function time_sleep_until ($timestamp) {}

/**
 * Parse a time/date generated with <function>strftime</function>
 * @link http://php.net/manual/en/function.strptime.php
 * @param date string
 * @param format string
 * @return array an array, or false on failure.
 */
function strptime ($date, $format) {}

/**
 * Flush the output buffer
 * @link http://php.net/manual/en/function.flush.php
 * @return void
 */
function flush () {}

/**
 * Wraps a string to a given number of characters
 * @link http://php.net/manual/en/function.wordwrap.php
 * @param str string
 * @param width int[optional]
 * @param break string[optional]
 * @param cut bool[optional]
 * @return string the given string wrapped at the specified column.
 */
function wordwrap ($str, $width = null, $break = null, $cut = null) {}

/**
 * Convert special characters to HTML entities
 * @link http://php.net/manual/en/function.htmlspecialchars.php
 * @param string
 * @param quote_style[optional]
 * @param charset[optional]
 * @param double_encode[optional]
 */
function htmlspecialchars ($string, $quote_style, $charset, $double_encode) {}

/**
 * Convert all applicable characters to HTML entities
 * @link http://php.net/manual/en/function.htmlentities.php
 * @param string string
 * @param quote_style int[optional]
 * @param charset string[optional]
 * @param double_encode bool[optional]
 * @return string the encoded string.
 */
function htmlentities ($string, $quote_style = null, $charset = null, $double_encode = null) {}

/**
 * Convert all HTML entities to their applicable characters
 * @link http://php.net/manual/en/function.html-entity-decode.php
 * @param string string
 * @param quote_style int[optional]
 * @param charset string[optional]
 * @return string the decoded string.
 */
function html_entity_decode ($string, $quote_style = null, $charset = null) {}

/**
 * Convert special HTML entities back to characters
 * @link http://php.net/manual/en/function.htmlspecialchars-decode.php
 * @param string string
 * @param quote_style int[optional]
 * @return string the decoded string.
 */
function htmlspecialchars_decode ($string, $quote_style = null) {}

/**
 * Returns the translation table used by <function>htmlspecialchars</function> and <function>htmlentities</function>
 * @link http://php.net/manual/en/function.get-html-translation-table.php
 * @param table int[optional]
 * @param quote_style int[optional]
 * @return array the translation table as an array.
 */
function get_html_translation_table ($table = null, $quote_style = null) {}

/**
 * Calculate the sha1 hash of a string
 * @link http://php.net/manual/en/function.sha1.php
 * @param str string
 * @param raw_output bool[optional]
 * @return string the sha1 hash as a string.
 */
function sha1 ($str, $raw_output = null) {}

/**
 * Calculate the sha1 hash of a file
 * @link http://php.net/manual/en/function.sha1-file.php
 * @param filename string
 * @param raw_output bool[optional]
 * @return string a string on success, false otherwise.
 */
function sha1_file ($filename, $raw_output = null) {}

/**
 * Calculate the md5 hash of a string
 * @link http://php.net/manual/en/function.md5.php
 * @param str string
 * @param raw_output bool[optional]
 * @return string the hash as a 32-character hexadecimal number. 
 */
function md5 ($str, $raw_output = null) {}

/**
 * Calculates the md5 hash of a given file
 * @link http://php.net/manual/en/function.md5-file.php
 * @param filename string
 * @param raw_output bool[optional]
 * @return string a string on success, false otherwise.
 */
function md5_file ($filename, $raw_output = null) {}

/**
 * Calculates the crc32 polynomial of a string
 * @link http://php.net/manual/en/function.crc32.php
 * @param str string
 * @return int
 */
function crc32 ($str) {}

/**
 * Parse a binary IPTC block into single tags.
 * @link http://php.net/manual/en/function.iptcparse.php
 * @param iptcblock string
 * @return array an array using the tagmarker as an index and the value as the
 */
function iptcparse ($iptcblock) {}

/**
 * Embed binary IPTC data into a JPEG image
 * @link http://php.net/manual/en/function.iptcembed.php
 * @param iptcdata string
 * @param jpeg_file_name string
 * @param spool int[optional]
 * @return mixed
 */
function iptcembed ($iptcdata, $jpeg_file_name, $spool = null) {}

/**
 * Get the size of an image
 * @link http://php.net/manual/en/function.getimagesize.php
 * @param filename string
 * @param imageinfo array[optional]
 * @return array an array with 5 elements.
 */
function getimagesize ($filename, array &$imageinfo = null) {}

/**
 * Get Mime-Type for image-type returned by getimagesize,
   exif_read_data, exif_thumbnail, exif_imagetype
 * @link http://php.net/manual/en/function.image-type-to-mime-type.php
 * @param imagetype int
 * @return string
 */
function image_type_to_mime_type ($imagetype) {}

/**
 * Get file extension for image type
 * @link http://php.net/manual/en/function.image-type-to-extension.php
 * @param imagetype int
 * @param include_dot bool[optional]
 * @return string
 */
function image_type_to_extension ($imagetype, $include_dot = null) {}

/**
 * Outputs lots of PHP information
 * @link http://php.net/manual/en/function.phpinfo.php
 * @param what int[optional]
 * @return bool
 */
function phpinfo ($what = null) {}

/**
 * Gets the current PHP version
 * @link http://php.net/manual/en/function.phpversion.php
 * @param extension string[optional]
 * @return string
 */
function phpversion ($extension = null) {}

/**
 * Prints out the credits for PHP
 * @link http://php.net/manual/en/function.phpcredits.php
 * @param flag int[optional]
 * @return bool
 */
function phpcredits ($flag = null) {}

/**
 * Gets the logo guid
 * @link http://php.net/manual/en/function.php-logo-guid.php
 * @return string PHPE9568F34-D428-11d2-A769-00AA001ACF42.
 */
function php_logo_guid () {}

function php_real_logo_guid () {}

function php_egg_logo_guid () {}

/**
 * Gets the Zend guid
 * @link http://php.net/manual/en/function.zend-logo-guid.php
 * @return string PHPE9568F35-D428-11d2-A769-00AA001ACF42.
 */
function zend_logo_guid () {}

/**
 * Returns the type of interface between web server and PHP
 * @link http://php.net/manual/en/function.php-sapi-name.php
 * @return string the interface type, as a lowercase string.
 */
function php_sapi_name () {}

/**
 * Returns information about the operating system PHP is running on
 * @link http://php.net/manual/en/function.php-uname.php
 * @param mode string[optional]
 * @return string the description, as a string.
 */
function php_uname ($mode = null) {}

/**
 * Return a list of .ini files parsed from the additional ini dir
 * @link http://php.net/manual/en/function.php-ini-scanned-files.php
 * @return string a comma-separated string of .ini files on success. Each comma is
 */
function php_ini_scanned_files () {}

function php_ini_loaded_file () {}

/**
 * String comparisons using a "natural order" algorithm
 * @link http://php.net/manual/en/function.strnatcmp.php
 * @param str1 string
 * @param str2 string
 * @return int
 */
function strnatcmp ($str1, $str2) {}

/**
 * Case insensitive string comparisons using a "natural order" algorithm
 * @link http://php.net/manual/en/function.strnatcasecmp.php
 * @param str1 string
 * @param str2 string
 * @return int
 */
function strnatcasecmp ($str1, $str2) {}

/**
 * Count the number of substring occurrences
 * @link http://php.net/manual/en/function.substr-count.php
 * @param haystack string
 * @param needle string
 * @param offset int[optional]
 * @param length int[optional]
 * @return int
 */
function substr_count ($haystack, $needle, $offset = null, $length = null) {}

/**
 * Find length of initial segment matching mask
 * @link http://php.net/manual/en/function.strspn.php
 * @param str1 string
 * @param str2 string
 * @param start int[optional]
 * @param length int[optional]
 * @return int the length of the initial segment of str1
 */
function strspn ($str1, $str2, $start = null, $length = null) {}

/**
 * Find length of initial segment not matching mask
 * @link http://php.net/manual/en/function.strcspn.php
 * @param str1 string
 * @param str2 string
 * @param start int[optional]
 * @param length int[optional]
 * @return int the length of the segment as an integer.
 */
function strcspn ($str1, $str2, $start = null, $length = null) {}

/**
 * Tokenize string
 * @link http://php.net/manual/en/function.strtok.php
 * @param str
 * @param token
 */
function strtok ($str, $token) {}

/**
 * Make a string uppercase
 * @link http://php.net/manual/en/function.strtoupper.php
 * @param string string
 * @return string the uppercased string.
 */
function strtoupper ($string) {}

/**
 * Make a string lowercase
 * @link http://php.net/manual/en/function.strtolower.php
 * @param str string
 * @return string the lowercased string.
 */
function strtolower ($str) {}

/**
 * Find position of first occurrence of a string
 * @link http://php.net/manual/en/function.strpos.php
 * @param haystack string
 * @param needle mixed
 * @param offset int[optional]
 * @return int the position as an integer. If needle is
 */
function strpos ($haystack, $needle, $offset = null) {}

/**
 * Find position of first occurrence of a case-insensitive string
 * @link http://php.net/manual/en/function.stripos.php
 * @param haystack string
 * @param needle string
 * @param offset int[optional]
 * @return int
 */
function stripos ($haystack, $needle, $offset = null) {}

/**
 * Find position of last occurrence of a char in a string
 * @link http://php.net/manual/en/function.strrpos.php
 * @param haystack string
 * @param needle string
 * @param offset int[optional]
 * @return int
 */
function strrpos ($haystack, $needle, $offset = null) {}

/**
 * Find position of last occurrence of a case-insensitive string in a string
 * @link http://php.net/manual/en/function.strripos.php
 * @param haystack string
 * @param needle string
 * @param offset int[optional]
 * @return int the numerical position of the last occurence of
 */
function strripos ($haystack, $needle, $offset = null) {}

/**
 * Reverse a string
 * @link http://php.net/manual/en/function.strrev.php
 * @param string string
 * @return string the reversed string.
 */
function strrev ($string) {}

/**
 * Convert logical Hebrew text to visual text
 * @link http://php.net/manual/en/function.hebrev.php
 * @param hebrew_text string
 * @param max_chars_per_line int[optional]
 * @return string the visual string.
 */
function hebrev ($hebrew_text, $max_chars_per_line = null) {}

/**
 * Convert logical Hebrew text to visual text with newline conversion
 * @link http://php.net/manual/en/function.hebrevc.php
 * @param hebrew_text string
 * @param max_chars_per_line int[optional]
 * @return string the visual string.
 */
function hebrevc ($hebrew_text, $max_chars_per_line = null) {}

/**
 * Inserts HTML line breaks before all newlines in a string
 * @link http://php.net/manual/en/function.nl2br.php
 * @param string string
 * @return string the altered string.
 */
function nl2br ($string) {}

/**
 * Returns filename component of path
 * @link http://php.net/manual/en/function.basename.php
 * @param path string
 * @param suffix string[optional]
 * @return string the base name of the given path.
 */
function basename ($path, $suffix = null) {}

/**
 * Returns directory name component of path
 * @link http://php.net/manual/en/function.dirname.php
 * @param path string
 * @return string the name of the directory. If there are no slashes in
 */
function dirname ($path) {}

/**
 * Returns information about a file path
 * @link http://php.net/manual/en/function.pathinfo.php
 * @param path string
 * @param options int[optional]
 * @return mixed
 */
function pathinfo ($path, $options = null) {}

/**
 * Un-quote string quoted with <function>addslashes</function>
 * @link http://php.net/manual/en/function.stripslashes.php
 * @param str string
 * @return string a string with backslashes stripped off.
 */
function stripslashes ($str) {}

/**
 * Un-quote string quoted with <function>addcslashes</function>
 * @link http://php.net/manual/en/function.stripcslashes.php
 * @param str string
 * @return string the unescaped string.
 */
function stripcslashes ($str) {}

/**
 * Find first occurrence of a string
 * @link http://php.net/manual/en/function.strstr.php
 * @param haystack string
 * @param needle string
 * @param before_needle bool
 * @return string the portion of string, or false if needle
 */
function strstr ($haystack, $needle, $before_needle) {}

/**
 * Case-insensitive <function>strstr</function>
 * @link http://php.net/manual/en/function.stristr.php
 * @param haystack string
 * @param needle string
 * @param before_needle bool
 * @return string the matched substring. If needle is not
 */
function stristr ($haystack, $needle, $before_needle) {}

/**
 * Find the last occurrence of a character in a string
 * @link http://php.net/manual/en/function.strrchr.php
 * @param haystack string
 * @param needle string
 * @return string
 */
function strrchr ($haystack, $needle) {}

/**
 * Randomly shuffles a string
 * @link http://php.net/manual/en/function.str-shuffle.php
 * @param str string
 * @return string the shuffled string.
 */
function str_shuffle ($str) {}

/**
 * Return information about words used in a string
 * @link http://php.net/manual/en/function.str-word-count.php
 * @param string string
 * @param format int[optional]
 * @param charlist string[optional]
 * @return mixed an array or an integer, depending on the
 */
function str_word_count ($string, $format = null, $charlist = null) {}

/**
 * Convert a string to an array
 * @link http://php.net/manual/en/function.str-split.php
 * @param string string
 * @param split_length int[optional]
 * @return array
 */
function str_split ($string, $split_length = null) {}

/**
 * Search a string for any of a set of characters
 * @link http://php.net/manual/en/function.strpbrk.php
 * @param haystack string
 * @param char_list string
 * @return string a string starting from the character found, or false if it is
 */
function strpbrk ($haystack, $char_list) {}

/**
 * Binary safe comparison of 2 strings from an offset, up to length characters
 * @link http://php.net/manual/en/function.substr-compare.php
 * @param main_str string
 * @param str string
 * @param offset int
 * @param length int[optional]
 * @param case_insensitivity bool[optional]
 * @return int &lt; 0 if main_str from position
 */
function substr_compare ($main_str, $str, $offset, $length = null, $case_insensitivity = null) {}

/**
 * Locale based string comparison
 * @link http://php.net/manual/en/function.strcoll.php
 * @param str1 string
 * @param str2 string
 * @return int &lt; 0 if str1 is less than
 */
function strcoll ($str1, $str2) {}

/**
 * Formats a number as a currency string
 * @link http://php.net/manual/en/function.money-format.php
 * @param format string
 * @param number float
 * @return string the formatted string. Characters before and after the formatting
 */
function money_format ($format, $number) {}

/**
 * Return part of a string
 * @link http://php.net/manual/en/function.substr.php
 * @param string string
 * @param start int
 * @param length int[optional]
 * @return string the extracted part of string.
 */
function substr ($string, $start, $length = null) {}

/**
 * Replace text within a portion of a string
 * @link http://php.net/manual/en/function.substr-replace.php
 * @param string mixed
 * @param replacement string
 * @param start int
 * @param length int[optional]
 * @return mixed
 */
function substr_replace ($string, $replacement, $start, $length = null) {}

/**
 * Quote meta characters
 * @link http://php.net/manual/en/function.quotemeta.php
 * @param str string
 * @return string the string with meta characters quoted.
 */
function quotemeta ($str) {}

/**
 * Make a string's first character uppercase
 * @link http://php.net/manual/en/function.ucfirst.php
 * @param str string
 * @return string the resulting string.
 */
function ucfirst ($str) {}

/**
 * Uppercase the first character of each word in a string
 * @link http://php.net/manual/en/function.ucwords.php
 * @param str string
 * @return string the modified string.
 */
function ucwords ($str) {}

/**
 * Translate certain characters
 * @link http://php.net/manual/en/function.strtr.php
 * @param str
 * @param from
 * @param to[optional]
 */
function strtr ($str, $from, $to) {}

/**
 * Quote string with slashes
 * @link http://php.net/manual/en/function.addslashes.php
 * @param str string
 * @return string the escaped string.
 */
function addslashes ($str) {}

/**
 * Quote string with slashes in a C style
 * @link http://php.net/manual/en/function.addcslashes.php
 * @param str string
 * @param charlist string
 * @return string the escaped string.
 */
function addcslashes ($str, $charlist) {}

/**
 * Strip whitespace (or other characters) from the end of a string
 * @link http://php.net/manual/en/function.rtrim.php
 * @param str string
 * @param charlist string[optional]
 * @return string the modified string.
 */
function rtrim ($str, $charlist = null) {}

/**
 * Replace all occurrences of the search string with the replacement string
 * @link http://php.net/manual/en/function.str-replace.php
 * @param search mixed
 * @param replace mixed
 * @param subject mixed
 * @param count int[optional]
 * @return mixed
 */
function str_replace ($search, $replace, $subject, &$count = null) {}

/**
 * Case-insensitive version of <function>str_replace</function>.
 * @link http://php.net/manual/en/function.str-ireplace.php
 * @param search mixed
 * @param replace mixed
 * @param subject mixed
 * @param count int[optional]
 * @return mixed a string or an array of replacements.
 */
function str_ireplace ($search, $replace, $subject, &$count = null) {}

/**
 * Repeat a string
 * @link http://php.net/manual/en/function.str-repeat.php
 * @param input string
 * @param multiplier int
 * @return string the repeated string.
 */
function str_repeat ($input, $multiplier) {}

/**
 * Return information about characters used in a string
 * @link http://php.net/manual/en/function.count-chars.php
 * @param string string
 * @param mode int[optional]
 * @return mixed
 */
function count_chars ($string, $mode = null) {}

/**
 * Split a string into smaller chunks
 * @link http://php.net/manual/en/function.chunk-split.php
 * @param body string
 * @param chunklen int[optional]
 * @param end string[optional]
 * @return string the chunked string.
 */
function chunk_split ($body, $chunklen = null, $end = null) {}

/**
 * Strip whitespace (or other characters) from the beginning and end of a string
 * @link http://php.net/manual/en/function.trim.php
 * @param str string
 * @param charlist string[optional]
 * @return string
 */
function trim ($str, $charlist = null) {}

/**
 * Strip whitespace (or other characters) from the beginning of a string
 * @link http://php.net/manual/en/function.ltrim.php
 * @param str string
 * @param charlist string[optional]
 * @return string
 */
function ltrim ($str, $charlist = null) {}

/**
 * Strip HTML and PHP tags from a string
 * @link http://php.net/manual/en/function.strip-tags.php
 * @param str string
 * @param allowable_tags string[optional]
 * @return string the stripped string.
 */
function strip_tags ($str, $allowable_tags = null) {}

/**
 * Calculate the similarity between two strings
 * @link http://php.net/manual/en/function.similar-text.php
 * @param first string
 * @param second string
 * @param percent float[optional]
 * @return int the number of matching chars in both strings.
 */
function similar_text ($first, $second, &$percent = null) {}

/**
 * Split a string by string
 * @link http://php.net/manual/en/function.explode.php
 * @param delimiter string
 * @param string string
 * @param limit int[optional]
 * @return array
 */
function explode ($delimiter, $string, $limit = null) {}

/**
 * Join array elements with a string
 * @link http://php.net/manual/en/function.implode.php
 * @param glue string
 * @param pieces array
 * @return string a string containing a string representation of all the array
 */
function implode ($glue, array $pieces) {}

/**
 * Set locale information
 * @link http://php.net/manual/en/function.setlocale.php
 * @param category int
 * @param locale string
 * @param ... string[optional]
 * @return string the new current locale, or false if the locale functionality is
 */
function setlocale ($category, $locale) {}

/**
 * Get numeric formatting information
 * @link http://php.net/manual/en/function.localeconv.php
 * @return array
 */
function localeconv () {}

/**
 * Query language and locale information
 * @link http://php.net/manual/en/function.nl-langinfo.php
 * @param item int
 * @return string the element as a string, or false if item
 */
function nl_langinfo ($item) {}

/**
 * Calculate the soundex key of a string
 * @link http://php.net/manual/en/function.soundex.php
 * @param str string
 * @return string the soundex key as a string.
 */
function soundex ($str) {}

/**
 * Calculate Levenshtein distance between two strings
 * @link http://php.net/manual/en/function.levenshtein.php
 * @param str1
 * @param str2
 * @param cost_ins
 * @param cost_rep
 * @param cost_del
 */
function levenshtein ($str1, $str2, $cost_ins, $cost_rep, $cost_del) {}

/**
 * Return a specific character
 * @link http://php.net/manual/en/function.chr.php
 * @param ascii int
 * @return string the specified character.
 */
function chr ($ascii) {}

/**
 * Return ASCII value of character
 * @link http://php.net/manual/en/function.ord.php
 * @param string string
 * @return int the ASCII value as an integer.
 */
function ord ($string) {}

/**
 * Parses the string into variables
 * @link http://php.net/manual/en/function.parse-str.php
 * @param str string
 * @param arr array[optional]
 * @return void
 */
function parse_str ($str, array &$arr = null) {}

/**
 * Pad a string to a certain length with another string
 * @link http://php.net/manual/en/function.str-pad.php
 * @param input string
 * @param pad_length int
 * @param pad_string string[optional]
 * @param pad_type int[optional]
 * @return string the padded string.
 */
function str_pad ($input, $pad_length, $pad_string = null, $pad_type = null) {}

/**
 * &Alias; <function>rtrim</function>
 * @link http://php.net/manual/en/function.chop.php
 * @param str
 * @param character_mask[optional]
 */
function chop ($str, $character_mask) {}

/**
 * &Alias; <function>strstr</function>
 * @link http://php.net/manual/en/function.strchr.php
 * @param haystack
 * @param needle
 */
function strchr ($haystack, $needle) {}

/**
 * Return a formatted string
 * @link http://php.net/manual/en/function.sprintf.php
 * @param format string
 * @param args mixed[optional]
 * @param ... mixed[optional]
 * @return string a string produced according to the formatting string
 */
function sprintf ($format, $args = null) {}

/**
 * Output a formatted string
 * @link http://php.net/manual/en/function.printf.php
 * @param format string
 * @param args mixed[optional]
 * @param ... mixed[optional]
 * @return int the length of the outputted string.
 */
function printf ($format, $args = null) {}

/**
 * Output a formatted string
 * @link http://php.net/manual/en/function.vprintf.php
 * @param format string
 * @param args array
 * @return int the length of the outputted string.
 */
function vprintf ($format, array $args) {}

/**
 * Return a formatted string
 * @link http://php.net/manual/en/function.vsprintf.php
 * @param format string
 * @param args array
 * @return string
 */
function vsprintf ($format, array $args) {}

/**
 * Write a formatted string to a stream
 * @link http://php.net/manual/en/function.fprintf.php
 * @param handle resource
 * @param format string
 * @param args mixed[optional]
 * @param ... mixed[optional]
 * @return int the length of the outputted string.
 */
function fprintf ($handle, $format, $args = null) {}

/**
 * Write a formatted string to a stream
 * @link http://php.net/manual/en/function.vfprintf.php
 * @param handle resource
 * @param format string
 * @param args array
 * @return int the length of the outputted string.
 */
function vfprintf ($handle, $format, array $args) {}

/**
 * Parses input from a string according to a format
 * @link http://php.net/manual/en/function.sscanf.php
 * @param str
 * @param format
 * @param ...[optional]
 */
function sscanf ($str, $format) {}

/**
 * Parses input from a file according to a format
 * @link http://php.net/manual/en/function.fscanf.php
 * @param handle resource
 * @param format string
 * @param ... mixed[optional]
 * @return mixed
 */
function fscanf ($handle, $format) {}

/**
 * Parse a URL and return its components
 * @link http://php.net/manual/en/function.parse-url.php
 * @param url string
 * @param component int[optional]
 * @return mixed
 */
function parse_url ($url, $component = null) {}

/**
 * URL-encodes string
 * @link http://php.net/manual/en/function.urlencode.php
 * @param str string
 * @return string a string in which all non-alphanumeric characters except
 */
function urlencode ($str) {}

/**
 * Decodes URL-encoded string
 * @link http://php.net/manual/en/function.urldecode.php
 * @param str string
 * @return string the decoded string.
 */
function urldecode ($str) {}

/**
 * URL-encode according to RFC 1738
 * @link http://php.net/manual/en/function.rawurlencode.php
 * @param str string
 * @return string a string in which all non-alphanumeric characters except
 */
function rawurlencode ($str) {}

/**
 * Decode URL-encoded strings
 * @link http://php.net/manual/en/function.rawurldecode.php
 * @param str string
 * @return string the decoded URL, as a string.
 */
function rawurldecode ($str) {}

/**
 * Generate URL-encoded query string
 * @link http://php.net/manual/en/function.http-build-query.php
 * @param formdata array
 * @param numeric_prefix string[optional]
 * @param arg_separator string[optional]
 * @return string a URL-encoded string.
 */
function http_build_query (array $formdata, $numeric_prefix = null, $arg_separator = null) {}

/**
 * Returns the target of a symbolic link
 * @link http://php.net/manual/en/function.readlink.php
 * @param path string
 * @return string the contents of the symbolic link path or false on error.
 */
function readlink ($path) {}

/**
 * Gets information about a link
 * @link http://php.net/manual/en/function.linkinfo.php
 * @param path string
 * @return int
 */
function linkinfo ($path) {}

/**
 * Creates a symbolic link
 * @link http://php.net/manual/en/function.symlink.php
 * @param target string
 * @param link string
 * @return bool
 */
function symlink ($target, $link) {}

/**
 * Create a hard link
 * @link http://php.net/manual/en/function.link.php
 * @param target string
 * @param link string
 * @return bool
 */
function link ($target, $link) {}

/**
 * Deletes a file
 * @link http://php.net/manual/en/function.unlink.php
 * @param filename string
 * @param context resource[optional]
 * @return bool
 */
function unlink ($filename, $context = null) {}

/**
 * Execute an external program
 * @link http://php.net/manual/en/function.exec.php
 * @param command string
 * @param output array[optional]
 * @param return_var int[optional]
 * @return string
 */
function exec ($command, array &$output = null, &$return_var = null) {}

/**
 * Execute an external program and display the output
 * @link http://php.net/manual/en/function.system.php
 * @param command string
 * @param return_var int[optional]
 * @return string the last line of the command output on success, and false
 */
function system ($command, &$return_var = null) {}

/**
 * Escape shell metacharacters
 * @link http://php.net/manual/en/function.escapeshellcmd.php
 * @param command string
 * @return string
 */
function escapeshellcmd ($command) {}

/**
 * Escape a string to be used as a shell argument
 * @link http://php.net/manual/en/function.escapeshellarg.php
 * @param arg string
 * @return string
 */
function escapeshellarg ($arg) {}

/**
 * Execute an external program and display raw output
 * @link http://php.net/manual/en/function.passthru.php
 * @param command string
 * @param return_var int[optional]
 * @return void
 */
function passthru ($command, &$return_var = null) {}

/**
 * Execute command via shell and return the complete output as a string
 * @link http://php.net/manual/en/function.shell-exec.php
 * @param cmd string
 * @return string
 */
function shell_exec ($cmd) {}

/**
 * Execute a command and open file pointers for input/output
 * @link http://php.net/manual/en/function.proc-open.php
 * @param cmd string
 * @param descriptorspec array
 * @param pipes array
 * @param cwd string[optional]
 * @param env array[optional]
 * @param other_options array[optional]
 * @return resource a resource representing the process, which should be freed using
 */
function proc_open ($cmd, array $descriptorspec, array &$pipes, $cwd = null, array $env = null, array $other_options = null) {}

/**
 * Close a process opened by <function>proc_open</function> and return the exit code of that process.
 * @link http://php.net/manual/en/function.proc-close.php
 * @param process resource
 * @return int the termination status of the process that was run.
 */
function proc_close ($process) {}

/**
 * Kills a process opened by proc_open
 * @link http://php.net/manual/en/function.proc-terminate.php
 * @param process resource
 * @param signal int[optional]
 * @return bool the termination status of the process that was run.
 */
function proc_terminate ($process, $signal = null) {}

/**
 * Get information about a process opened by <function>proc_open</function>
 * @link http://php.net/manual/en/function.proc-get-status.php
 * @param process resource
 * @return array
 */
function proc_get_status ($process) {}

/**
 * Change the priority of the current process
 * @link http://php.net/manual/en/function.proc-nice.php
 * @param increment int
 * @return bool
 */
function proc_nice ($increment) {}

/**
 * Generate a random integer
 * @link http://php.net/manual/en/function.rand.php
 * @param min int[optional]
 * @param max int
 * @return int
 */
function rand ($min = null, $max) {}

/**
 * Seed the random number generator
 * @link http://php.net/manual/en/function.srand.php
 * @param seed int[optional]
 * @return void
 */
function srand ($seed = null) {}

/**
 * Show largest possible random value
 * @link http://php.net/manual/en/function.getrandmax.php
 * @return int
 */
function getrandmax () {}

/**
 * Generate a better random value
 * @link http://php.net/manual/en/function.mt-rand.php
 * @param min int[optional]
 * @param max int
 * @return int
 */
function mt_rand ($min = null, $max) {}

/**
 * Seed the better random number generator
 * @link http://php.net/manual/en/function.mt-srand.php
 * @param seed int[optional]
 * @return void
 */
function mt_srand ($seed = null) {}

/**
 * Show largest possible random value
 * @link http://php.net/manual/en/function.mt-getrandmax.php
 * @return int the maximum random value returned by mt_rand
 */
function mt_getrandmax () {}

/**
 * Get port number associated with an Internet service and protocol
 * @link http://php.net/manual/en/function.getservbyname.php
 * @param service string
 * @param protocol string
 * @return int the port number, or false if service or
 */
function getservbyname ($service, $protocol) {}

/**
 * Get Internet service which corresponds to port and protocol
 * @link http://php.net/manual/en/function.getservbyport.php
 * @param port int
 * @param protocol string
 * @return string the Internet service name as a string.
 */
function getservbyport ($port, $protocol) {}

/**
 * Get protocol number associated with protocol name
 * @link http://php.net/manual/en/function.getprotobyname.php
 * @param name string
 * @return int the protocol number or -1 if the protocol is not found.
 */
function getprotobyname ($name) {}

/**
 * Get protocol name associated with protocol number
 * @link http://php.net/manual/en/function.getprotobynumber.php
 * @param number int
 * @return string the protocol name as a string.
 */
function getprotobynumber ($number) {}

/**
 * Gets PHP script owner's UID
 * @link http://php.net/manual/en/function.getmyuid.php
 * @return int the user ID of the current script, or false on error.
 */
function getmyuid () {}

/**
 * Get PHP script owner's GID
 * @link http://php.net/manual/en/function.getmygid.php
 * @return int the group ID of the current script, or false on error.
 */
function getmygid () {}

/**
 * Gets PHP's process ID
 * @link http://php.net/manual/en/function.getmypid.php
 * @return int the current PHP process ID, or false on error.
 */
function getmypid () {}

/**
 * Gets the inode of the current script
 * @link http://php.net/manual/en/function.getmyinode.php
 * @return int the current script's inode as a string, or false on error.
 */
function getmyinode () {}

/**
 * Gets time of last page modification
 * @link http://php.net/manual/en/function.getlastmod.php
 * @return int the time of the last modification of the current
 */
function getlastmod () {}

/**
 * Decodes data encoded with MIME base64
 * @link http://php.net/manual/en/function.base64-decode.php
 * @param data string
 * @param strict bool[optional]
 * @return string the original data or false on failure. The returned data may be
 */
function base64_decode ($data, $strict = null) {}

/**
 * Encodes data with MIME base64
 * @link http://php.net/manual/en/function.base64-encode.php
 * @param data string
 * @return string
 */
function base64_encode ($data) {}

/**
 * Uuencode a string
 * @link http://php.net/manual/en/function.convert-uuencode.php
 * @param data string
 * @return string the uuencoded data.
 */
function convert_uuencode ($data) {}

/**
 * Decode a uuencoded string
 * @link http://php.net/manual/en/function.convert-uudecode.php
 * @param data string
 * @return string the decoded data as a string.
 */
function convert_uudecode ($data) {}

/**
 * Absolute value
 * @link http://php.net/manual/en/function.abs.php
 * @param number mixed
 * @return number
 */
function abs ($number) {}

/**
 * Round fractions up
 * @link http://php.net/manual/en/function.ceil.php
 * @param value float
 * @return float
 */
function ceil ($value) {}

/**
 * Round fractions down
 * @link http://php.net/manual/en/function.floor.php
 * @param value float
 * @return float
 */
function floor ($value) {}

/**
 * Rounds a float
 * @link http://php.net/manual/en/function.round.php
 * @param val float
 * @param precision int[optional]
 * @return float
 */
function round ($val, $precision = null) {}

/**
 * Sine
 * @link http://php.net/manual/en/function.sin.php
 * @param arg float
 * @return float
 */
function sin ($arg) {}

/**
 * Cosine
 * @link http://php.net/manual/en/function.cos.php
 * @param arg float
 * @return float
 */
function cos ($arg) {}

/**
 * Tangent
 * @link http://php.net/manual/en/function.tan.php
 * @param arg float
 * @return float
 */
function tan ($arg) {}

/**
 * Arc sine
 * @link http://php.net/manual/en/function.asin.php
 * @param arg float
 * @return float
 */
function asin ($arg) {}

/**
 * Arc cosine
 * @link http://php.net/manual/en/function.acos.php
 * @param arg float
 * @return float
 */
function acos ($arg) {}

/**
 * Arc tangent
 * @link http://php.net/manual/en/function.atan.php
 * @param arg float
 * @return float
 */
function atan ($arg) {}

/**
 * Arc tangent of two variables
 * @link http://php.net/manual/en/function.atan2.php
 * @param y float
 * @param x float
 * @return float
 */
function atan2 ($y, $x) {}

/**
 * Hyperbolic sine
 * @link http://php.net/manual/en/function.sinh.php
 * @param arg float
 * @return float
 */
function sinh ($arg) {}

/**
 * Hyperbolic cosine
 * @link http://php.net/manual/en/function.cosh.php
 * @param arg float
 * @return float
 */
function cosh ($arg) {}

/**
 * Hyperbolic tangent
 * @link http://php.net/manual/en/function.tanh.php
 * @param arg float
 * @return float
 */
function tanh ($arg) {}

/**
 * Inverse hyperbolic sine
 * @link http://php.net/manual/en/function.asinh.php
 * @param arg float
 * @return float
 */
function asinh ($arg) {}

/**
 * Inverse hyperbolic cosine
 * @link http://php.net/manual/en/function.acosh.php
 * @param arg float
 * @return float
 */
function acosh ($arg) {}

/**
 * Inverse hyperbolic tangent
 * @link http://php.net/manual/en/function.atanh.php
 * @param arg float
 * @return float
 */
function atanh ($arg) {}

/**
 * Returns exp(number) - 1, computed in a way that is accurate even
   when the value of number is close to zero
 * @link http://php.net/manual/en/function.expm1.php
 * @param arg float
 * @return float
 */
function expm1 ($arg) {}

/**
 * Returns log(1 + number), computed in a way that is accurate even when
   the value of number is close to zero
 * @link http://php.net/manual/en/function.log1p.php
 * @param number float
 * @return float
 */
function log1p ($number) {}

/**
 * Get value of pi
 * @link http://php.net/manual/en/function.pi.php
 * @return float
 */
function pi () {}

/**
 * Finds whether a value is a legal finite number
 * @link http://php.net/manual/en/function.is-finite.php
 * @param val float
 * @return bool
 */
function is_finite ($val) {}

/**
 * Finds whether a value is not a number
 * @link http://php.net/manual/en/function.is-nan.php
 * @param val float
 * @return bool true if val is 'not a number',
 */
function is_nan ($val) {}

/**
 * Finds whether a value is infinite
 * @link http://php.net/manual/en/function.is-infinite.php
 * @param val float
 * @return bool
 */
function is_infinite ($val) {}

/**
 * Exponential expression
 * @link http://php.net/manual/en/function.pow.php
 * @param base number
 * @param exp number
 * @return number
 */
function pow ($base, $exp) {}

/**
 * Calculates the exponent of <constant>e</constant>
 * @link http://php.net/manual/en/function.exp.php
 * @param arg float
 * @return float
 */
function exp ($arg) {}

/**
 * Natural logarithm
 * @link http://php.net/manual/en/function.log.php
 * @param arg float
 * @param base float[optional]
 * @return float
 */
function log ($arg, $base = null) {}

/**
 * Base-10 logarithm
 * @link http://php.net/manual/en/function.log10.php
 * @param arg float
 * @return float
 */
function log10 ($arg) {}

/**
 * Square root
 * @link http://php.net/manual/en/function.sqrt.php
 * @param arg float
 * @return float
 */
function sqrt ($arg) {}

/**
 * Calculate the length of the hypotenuse of a right-angle triangle
 * @link http://php.net/manual/en/function.hypot.php
 * @param x float
 * @param y float
 * @return float
 */
function hypot ($x, $y) {}

/**
 * Converts the number in degrees to the radian equivalent
 * @link http://php.net/manual/en/function.deg2rad.php
 * @param number float
 * @return float
 */
function deg2rad ($number) {}

/**
 * Converts the radian number to the equivalent number in degrees
 * @link http://php.net/manual/en/function.rad2deg.php
 * @param number float
 * @return float
 */
function rad2deg ($number) {}

/**
 * Binary to decimal
 * @link http://php.net/manual/en/function.bindec.php
 * @param binary_string string
 * @return number
 */
function bindec ($binary_string) {}

/**
 * Hexadecimal to decimal
 * @link http://php.net/manual/en/function.hexdec.php
 * @param hex_string string
 * @return number
 */
function hexdec ($hex_string) {}

/**
 * Octal to decimal
 * @link http://php.net/manual/en/function.octdec.php
 * @param octal_string string
 * @return number
 */
function octdec ($octal_string) {}

/**
 * Decimal to binary
 * @link http://php.net/manual/en/function.decbin.php
 * @param number int
 * @return string
 */
function decbin ($number) {}

/**
 * Decimal to octal
 * @link http://php.net/manual/en/function.decoct.php
 * @param number int
 * @return string
 */
function decoct ($number) {}

/**
 * Decimal to hexadecimal
 * @link http://php.net/manual/en/function.dechex.php
 * @param number int
 * @return string
 */
function dechex ($number) {}

/**
 * Convert a number between arbitrary bases
 * @link http://php.net/manual/en/function.base-convert.php
 * @param number string
 * @param frombase int
 * @param tobase int
 * @return string
 */
function base_convert ($number, $frombase, $tobase) {}

/**
 * Format a number with grouped thousands
 * @link http://php.net/manual/en/function.number-format.php
 * @param number
 * @param num_decimal_places[optional]
 * @param dec_seperator[optional]
 * @param thousands_seperator[optional]
 */
function number_format ($number, $num_decimal_places, $dec_seperator, $thousands_seperator) {}

/**
 * Returns the floating point remainder (modulo) of the division
  of the arguments
 * @link http://php.net/manual/en/function.fmod.php
 * @param x float
 * @param y float
 * @return float
 */
function fmod ($x, $y) {}

/**
 * Converts a packed internet address to a human readable representation
 * @link http://php.net/manual/en/function.inet-ntop.php
 * @param in_addr string
 * @return string a string representation of the address or false on failure.
 */
function inet_ntop ($in_addr) {}

/**
 * Converts a human readable IP address to its packed in_addr representation
 * @link http://php.net/manual/en/function.inet-pton.php
 * @param address string
 * @return string the in_addr representation of the given
 */
function inet_pton ($address) {}

/**
 * Converts a string containing an (IPv4) Internet Protocol dotted address into a proper address
 * @link http://php.net/manual/en/function.ip2long.php
 * @param ip_address string
 * @return int the IPv4 address or false if ip_address
 */
function ip2long ($ip_address) {}

/**
 * Converts an (IPv4) Internet network address into a string in Internet standard dotted format
 * @link http://php.net/manual/en/function.long2ip.php
 * @param proper_address int
 * @return string the Internet IP address as a string.
 */
function long2ip ($proper_address) {}

/**
 * Gets the value of an environment variable
 * @link http://php.net/manual/en/function.getenv.php
 * @param varname string
 * @return string the value of the environment variable
 */
function getenv ($varname) {}

/**
 * Sets the value of an environment variable
 * @link http://php.net/manual/en/function.putenv.php
 * @param setting string
 * @return bool
 */
function putenv ($setting) {}

/**
 * Gets options from the command line argument list
 * @link http://php.net/manual/en/function.getopt.php
 * @param options string
 * @param longopts array[optional]
 * @return array
 */
function getopt ($options, array $longopts = null) {}

/**
 * Gets system load average
 * @link http://php.net/manual/en/function.sys-getloadavg.php
 * @return array an array with three samples (last 1, 5 and 15
 */
function sys_getloadavg () {}

/**
 * Return current Unix timestamp with microseconds
 * @link http://php.net/manual/en/function.microtime.php
 * @param get_as_float bool[optional]
 * @return mixed
 */
function microtime ($get_as_float = null) {}

/**
 * Get current time
 * @link http://php.net/manual/en/function.gettimeofday.php
 * @param return_float bool[optional]
 * @return mixed
 */
function gettimeofday ($return_float = null) {}

/**
 * Gets the current resource usages
 * @link http://php.net/manual/en/function.getrusage.php
 * @param who int[optional]
 * @return array an associative array containing the data returned from the system
 */
function getrusage ($who = null) {}

/**
 * Generate a unique ID
 * @link http://php.net/manual/en/function.uniqid.php
 * @param prefix string[optional]
 * @param more_entropy bool[optional]
 * @return string the unique identifier, as a string.
 */
function uniqid ($prefix = null, $more_entropy = null) {}

/**
 * Convert a quoted-printable string to an 8 bit string
 * @link http://php.net/manual/en/function.quoted-printable-decode.php
 * @param str string
 * @return string the 8-bit binary string.
 */
function quoted_printable_decode ($str) {}

/**
 * Convert from one Cyrillic character set to another
 * @link http://php.net/manual/en/function.convert-cyr-string.php
 * @param str string
 * @param from string
 * @param to string
 * @return string the converted string.
 */
function convert_cyr_string ($str, $from, $to) {}

/**
 * Gets the name of the owner of the current PHP script
 * @link http://php.net/manual/en/function.get-current-user.php
 * @return string the username as a string.
 */
function get_current_user () {}

/**
 * Limits the maximum execution time
 * @link http://php.net/manual/en/function.set-time-limit.php
 * @param seconds int
 * @return void
 */
function set_time_limit ($seconds) {}

/**
 * Gets the value of a PHP configuration option
 * @link http://php.net/manual/en/function.get-cfg-var.php
 * @param option string
 * @return string the current value of the PHP configuration variable specified by
 */
function get_cfg_var ($option) {}

function magic_quotes_runtime () {}

/**
 * Sets the current active configuration setting of magic_quotes_runtime
 * @link http://php.net/manual/en/function.set-magic-quotes-runtime.php
 * @param new_setting int
 * @return bool
 */
function set_magic_quotes_runtime ($new_setting) {}

/**
 * Gets the current configuration setting of magic quotes gpc
 * @link http://php.net/manual/en/function.get-magic-quotes-gpc.php
 * @return int 0 if magic quotes gpc are off, 1 otherwise.
 */
function get_magic_quotes_gpc () {}

/**
 * Gets the current active configuration setting of magic_quotes_runtime
 * @link http://php.net/manual/en/function.get-magic-quotes-runtime.php
 * @return int 0 if magic quotes runtime is off, 1 otherwise.
 */
function get_magic_quotes_runtime () {}

/**
 * Import GET/POST/Cookie variables into the global scope
 * @link http://php.net/manual/en/function.import-request-variables.php
 * @param types string
 * @param prefix string[optional]
 * @return bool
 */
function import_request_variables ($types, $prefix = null) {}

/**
 * Send an error message somewhere
 * @link http://php.net/manual/en/function.error-log.php
 * @param message string
 * @param message_type int[optional]
 * @param destination string[optional]
 * @param extra_headers string[optional]
 * @return bool
 */
function error_log ($message, $message_type = null, $destination = null, $extra_headers = null) {}

/**
 * Get the last occurred error
 * @link http://php.net/manual/en/function.error-get-last.php
 * @return array an associative array describing the last error with keys "type",
 */
function error_get_last () {}

/**
 * Call a user function given by the first parameter
 * @link http://php.net/manual/en/function.call-user-func.php
 * @param function callback
 * @param parameter mixed[optional]
 * @param ... mixed[optional]
 * @return mixed the function result, or false on error.
 */
function call_user_func ($function, $parameter = null) {}

/**
 * Call a user function given with an array of parameters
 * @link http://php.net/manual/en/function.call-user-func-array.php
 * @param function callback
 * @param param_arr array
 * @return mixed the function result, or false on error.
 */
function call_user_func_array ($function, array $param_arr) {}

/**
 * Call a user method on an specific object [deprecated]
 * @link http://php.net/manual/en/function.call-user-method.php
 * @param method_name string
 * @param obj object
 * @param parameter mixed[optional]
 * @param ... mixed[optional]
 * @return mixed
 */
function call_user_method ($method_name, &$obj, $parameter = null) {}

/**
 * Call a user method given with an array of parameters [deprecated]
 * @link http://php.net/manual/en/function.call-user-method-array.php
 * @param method_name string
 * @param obj object
 * @param paramarr array
 * @return mixed
 */
function call_user_method_array ($method_name, &$obj, array $paramarr) {}

/**
 * Generates a storable representation of a value
 * @link http://php.net/manual/en/function.serialize.php
 * @param value mixed
 * @return string a string containing a byte-stream representation of 
 */
function serialize ($value) {}

/**
 * Creates a PHP value from a stored representation
 * @link http://php.net/manual/en/function.unserialize.php
 * @param str string
 * @return mixed
 */
function unserialize ($str) {}

/**
 * Dumps information about a variable
 * @link http://php.net/manual/en/function.var-dump.php
 * @param expression mixed
 * @param expression mixed[optional]
 * @return void
 */
function var_dump ($expression, $expression = null) {}

/**
 * Outputs or returns a parsable string representation of a variable
 * @link http://php.net/manual/en/function.var-export.php
 * @param expression mixed
 * @param return bool[optional]
 * @return mixed the variable representation when the return 
 */
function var_export ($expression, $return = null) {}

/**
 * Dumps a string representation of an internal zend value to output
 * @link http://php.net/manual/en/function.debug-zval-dump.php
 * @param variable mixed
 * @return void
 */
function debug_zval_dump ($variable) {}

/**
 * Prints human-readable information about a variable
 * @link http://php.net/manual/en/function.print-r.php
 * @param expression mixed
 * @param return bool[optional]
 * @return mixed
 */
function print_r ($expression, $return = null) {}

/**
 * Returns the amount of memory allocated to PHP
 * @link http://php.net/manual/en/function.memory-get-usage.php
 * @param real_usage bool[optional]
 * @return int the memory amount in bytes.
 */
function memory_get_usage ($real_usage = null) {}

/**
 * Returns the peak of memory allocated by PHP
 * @link http://php.net/manual/en/function.memory-get-peak-usage.php
 * @param real_usage bool[optional]
 * @return int the memory peak in bytes.
 */
function memory_get_peak_usage ($real_usage = null) {}

/**
 * Register a function for execution on shutdown
 * @link http://php.net/manual/en/function.register-shutdown-function.php
 * @param function callback
 * @param parameter mixed[optional]
 * @param ... mixed[optional]
 * @return void
 */
function register_shutdown_function ($function, $parameter = null) {}

/**
 * Register a function for execution on each tick
 * @link http://php.net/manual/en/function.register-tick-function.php
 * @param function callback
 * @param arg mixed[optional]
 * @param ... mixed[optional]
 * @return bool
 */
function register_tick_function ($function, $arg = null) {}

/**
 * De-register a function for execution on each tick
 * @link http://php.net/manual/en/function.unregister-tick-function.php
 * @param function_name string
 * @return void
 */
function unregister_tick_function ($function_name) {}

/**
 * Syntax highlighting of a file
 * @link http://php.net/manual/en/function.highlight-file.php
 * @param filename string
 * @param return bool[optional]
 * @return mixed
 */
function highlight_file ($filename, $return = null) {}

/**
 * &Alias; <function>highlight_file</function>
 * @link http://php.net/manual/en/function.show-source.php
 * @param file_name
 * @param return[optional]
 */
function show_source ($file_name, $return) {}

/**
 * Syntax highlighting of a string
 * @link http://php.net/manual/en/function.highlight-string.php
 * @param str string
 * @param return bool[optional]
 * @return mixed
 */
function highlight_string ($str, $return = null) {}

/**
 * Return source with stripped comments and whitespace
 * @link http://php.net/manual/en/function.php-strip-whitespace.php
 * @param filename string
 * @return string
 */
function php_strip_whitespace ($filename) {}

/**
 * Gets the value of a configuration option
 * @link http://php.net/manual/en/function.ini-get.php
 * @param varname string
 * @return string the value of the configuration option as a string on success, or
 */
function ini_get ($varname) {}

/**
 * Gets all configuration options
 * @link http://php.net/manual/en/function.ini-get-all.php
 * @param extension string[optional]
 * @return array an associative array uses the directive name as the array key, 
 */
function ini_get_all ($extension = null) {}

/**
 * Sets the value of a configuration option
 * @link http://php.net/manual/en/function.ini-set.php
 * @param varname string
 * @param newvalue string
 * @return string the old value on success, false on failure.
 */
function ini_set ($varname, $newvalue) {}

/**
 * &Alias; <function>ini_set</function>
 * @link http://php.net/manual/en/function.ini-alter.php
 * @param varname
 * @param newvalue
 */
function ini_alter ($varname, $newvalue) {}

/**
 * Restores the value of a configuration option
 * @link http://php.net/manual/en/function.ini-restore.php
 * @param varname string
 * @return void
 */
function ini_restore ($varname) {}

/**
 * Gets the current include_path configuration option
 * @link http://php.net/manual/en/function.get-include-path.php
 * @return string the path, as a string.
 */
function get_include_path () {}

/**
 * Sets the include_path configuration option
 * @link http://php.net/manual/en/function.set-include-path.php
 * @param new_include_path string
 * @return string the old include_path on
 */
function set_include_path ($new_include_path) {}

/**
 * Restores the value of the include_path configuration option
 * @link http://php.net/manual/en/function.restore-include-path.php
 * @return void
 */
function restore_include_path () {}

/**
 * Send a cookie
 * @link http://php.net/manual/en/function.setcookie.php
 * @param name string
 * @param value string[optional]
 * @param expire int[optional]
 * @param path string[optional]
 * @param domain string[optional]
 * @param secure bool[optional]
 * @param httponly bool[optional]
 * @return bool
 */
function setcookie ($name, $value = null, $expire = null, $path = null, $domain = null, $secure = null, $httponly = null) {}

/**
 * Send a cookie without urlencoding the cookie value
 * @link http://php.net/manual/en/function.setrawcookie.php
 * @param name string
 * @param value string[optional]
 * @param expire int[optional]
 * @param path string[optional]
 * @param domain string[optional]
 * @param secure bool[optional]
 * @param httponly bool[optional]
 * @return bool
 */
function setrawcookie ($name, $value = null, $expire = null, $path = null, $domain = null, $secure = null, $httponly = null) {}

/**
 * Send a raw HTTP header
 * @link http://php.net/manual/en/function.header.php
 * @param string string
 * @param replace bool[optional]
 * @param http_response_code int[optional]
 * @return void
 */
function header ($string, $replace = null, $http_response_code = null) {}

/**
 * Checks if or where headers have been sent
 * @link http://php.net/manual/en/function.headers-sent.php
 * @param file string[optional]
 * @param line int[optional]
 * @return bool
 */
function headers_sent (&$file = null, &$line = null) {}

/**
 * Returns a list of response headers sent (or ready to send)
 * @link http://php.net/manual/en/function.headers-list.php
 * @return array a numerically indexed array of headers.
 */
function headers_list () {}

/**
 * Check whether client disconnected
 * @link http://php.net/manual/en/function.connection-aborted.php
 * @return int 1 if client disconnected, 0 otherwise.
 */
function connection_aborted () {}

/**
 * Returns connection status bitfield
 * @link http://php.net/manual/en/function.connection-status.php
 * @return int the connection status bitfield, which can be used against the
 */
function connection_status () {}

/**
 * Set whether a client disconnect should abort script execution
 * @link http://php.net/manual/en/function.ignore-user-abort.php
 * @param setting bool[optional]
 * @return int the previous setting, as a boolean.
 */
function ignore_user_abort ($setting = null) {}

/**
 * Parse a configuration file
 * @link http://php.net/manual/en/function.parse-ini-file.php
 * @param filename string
 * @param process_sections bool[optional]
 * @return array
 */
function parse_ini_file ($filename, $process_sections = null) {}

/**
 * Tells whether the file was uploaded via HTTP POST
 * @link http://php.net/manual/en/function.is-uploaded-file.php
 * @param filename string
 * @return bool
 */
function is_uploaded_file ($filename) {}

/**
 * Moves an uploaded file to a new location
 * @link http://php.net/manual/en/function.move-uploaded-file.php
 * @param filename string
 * @param destination string
 * @return bool
 */
function move_uploaded_file ($filename, $destination) {}

/**
 * Get the Internet host name corresponding to a given IP address
 * @link http://php.net/manual/en/function.gethostbyaddr.php
 * @param ip_address string
 * @return string the host name or the unmodified ip_address
 */
function gethostbyaddr ($ip_address) {}

/**
 * Get the IP address corresponding to a given Internet host name
 * @link http://php.net/manual/en/function.gethostbyname.php
 * @param hostname string
 * @return string the IP address or a string containing the unmodified
 */
function gethostbyname ($hostname) {}

/**
 * Get a list of IP addresses corresponding to a given Internet host
   name
 * @link http://php.net/manual/en/function.gethostbynamel.php
 * @param hostname string
 * @return array an array of IP addresses or false if
 */
function gethostbynamel ($hostname) {}

/**
 * &Alias; <function>checkdnsrr</function>
 * @link http://php.net/manual/en/function.dns-check-record.php
 * @param host
 * @param type[optional]
 */
function dns_check_record ($host, $type) {}

/**
 * Check DNS records corresponding to a given Internet host name or IP address
 * @link http://php.net/manual/en/function.checkdnsrr.php
 * @param host string
 * @param type string[optional]
 * @return int true if any records are found; returns false if no records
 */
function checkdnsrr ($host, $type = null) {}

/**
 * &Alias; <function>getmxrr</function>
 * @link http://php.net/manual/en/function.dns-get-mx.php
 * @param hostname
 * @param mxhosts
 * @param weight[optional]
 */
function dns_get_mx ($hostname, &$mxhosts, &$weight) {}

/**
 * Get MX records corresponding to a given Internet host name
 * @link http://php.net/manual/en/function.getmxrr.php
 * @param hostname string
 * @param mxhosts array
 * @param weight array[optional]
 * @return bool true if any records are found; returns false if no records
 */
function getmxrr ($hostname, array &$mxhosts, array &$weight = null) {}

/**
 * Fetch DNS Resource Records associated with a hostname
 * @link http://php.net/manual/en/function.dns-get-record.php
 * @param hostname string
 * @param type int[optional]
 * @param authns array[optional]
 * @param addtl array
 * @return array
 */
function dns_get_record ($hostname, $type = null, array &$authns = null, array &$addtl) {}

/**
 * Get the integer value of a variable
 * @link http://php.net/manual/en/function.intval.php
 * @param var mixed
 * @param base int[optional]
 * @return int
 */
function intval ($var, $base = null) {}

/**
 * Get float value of a variable
 * @link http://php.net/manual/en/function.floatval.php
 * @param var mixed
 * @return float
 */
function floatval ($var) {}

/**
 * &Alias; <function>floatval</function>
 * @link http://php.net/manual/en/function.doubleval.php
 * @param var
 */
function doubleval ($var) {}

/**
 * Get string value of a variable
 * @link http://php.net/manual/en/function.strval.php
 * @param var mixed
 * @return string
 */
function strval ($var) {}

/**
 * Get the type of a variable
 * @link http://php.net/manual/en/function.gettype.php
 * @param var mixed
 * @return string
 */
function gettype ($var) {}

/**
 * Set the type of a variable
 * @link http://php.net/manual/en/function.settype.php
 * @param var mixed
 * @param type string
 * @return bool
 */
function settype (&$var, $type) {}

/**
 * Finds whether a variable is &null;
 * @link http://php.net/manual/en/function.is-null.php
 * @param var mixed
 * @return bool true if var is null, false
 */
function is_null ($var) {}

/**
 * Finds whether a variable is a resource
 * @link http://php.net/manual/en/function.is-resource.php
 * @param var mixed
 * @return bool true if var is a resource,
 */
function is_resource ($var) {}

/**
 * Finds out whether a variable is a boolean
 * @link http://php.net/manual/en/function.is-bool.php
 * @param var mixed
 * @return bool true if var is a boolean,
 */
function is_bool ($var) {}

/**
 * &Alias; <function>is_int</function>
 * @link http://php.net/manual/en/function.is-long.php
 * @param var
 */
function is_long ($var) {}

/**
 * Finds whether a variable is a float
 * @link http://php.net/manual/en/function.is-float.php
 * @param var mixed
 * @return bool true if var is a float, 
 */
function is_float ($var) {}

/**
 * Find whether the type of a variable is integer
 * @link http://php.net/manual/en/function.is-int.php
 * @param var mixed
 * @return bool true if var is an integer, 
 */
function is_int ($var) {}

/**
 * &Alias; <function>is_int</function>
 * @link http://php.net/manual/en/function.is-integer.php
 * @param var
 */
function is_integer ($var) {}

/**
 * &Alias; <function>is_float</function>
 * @link http://php.net/manual/en/function.is-double.php
 * @param var
 */
function is_double ($var) {}

/**
 * &Alias; <function>is_float</function>
 * @link http://php.net/manual/en/function.is-real.php
 * @param var
 */
function is_real ($var) {}

/**
 * Finds whether a variable is a number or a numeric string
 * @link http://php.net/manual/en/function.is-numeric.php
 * @param var mixed
 * @return bool true if var is a number or a numeric
 */
function is_numeric ($var) {}

/**
 * Finds whether a variable is a string
 * @link http://php.net/manual/en/function.is-string.php
 * @param var mixed
 * @return bool true if var is a string,
 */
function is_string ($var) {}

/**
 * Finds whether a variable is an array
 * @link http://php.net/manual/en/function.is-array.php
 * @param var mixed
 * @return bool true if var is an array, 
 */
function is_array ($var) {}

/**
 * Finds whether a variable is an object
 * @link http://php.net/manual/en/function.is-object.php
 * @param var mixed
 * @return bool true if var is an object, 
 */
function is_object ($var) {}

/**
 * Finds whether a variable is a scalar
 * @link http://php.net/manual/en/function.is-scalar.php
 * @param var mixed
 * @return bool true if var is a scalar false
 */
function is_scalar ($var) {}

/**
 * Verify that the contents of a variable can be called as a function
 * @link http://php.net/manual/en/function.is-callable.php
 * @param var mixed
 * @param syntax_only bool[optional]
 * @param callable_name string[optional]
 * @return bool true if var is callable, false 
 */
function is_callable ($var, $syntax_only = null, &$callable_name = null) {}

/**
 * Regular expression match
 * @link http://php.net/manual/en/function.ereg.php
 * @param pattern string
 * @param string string
 * @param regs array[optional]
 * @return int the length of the matched string if a match for
 */
function ereg ($pattern, $string, array &$regs = null) {}

/**
 * Replace regular expression
 * @link http://php.net/manual/en/function.ereg-replace.php
 * @param pattern string
 * @param replacement string
 * @param string string
 * @return string
 */
function ereg_replace ($pattern, $replacement, $string) {}

/**
 * Case insensitive regular expression match
 * @link http://php.net/manual/en/function.eregi.php
 * @param pattern string
 * @param string string
 * @param regs array[optional]
 * @return int the length of the matched string if a match for
 */
function eregi ($pattern, $string, array &$regs = null) {}

/**
 * Replace regular expression case insensitive
 * @link http://php.net/manual/en/function.eregi-replace.php
 * @param pattern string
 * @param replacement string
 * @param string string
 * @return string
 */
function eregi_replace ($pattern, $replacement, $string) {}

/**
 * Split string into array by regular expression
 * @link http://php.net/manual/en/function.split.php
 * @param pattern string
 * @param string string
 * @param limit int[optional]
 * @return array an array of strings, each of which is a substring of
 */
function split ($pattern, $string, $limit = null) {}

/**
 * Split string into array by regular expression case insensitive
 * @link http://php.net/manual/en/function.spliti.php
 * @param pattern string
 * @param string string
 * @param limit int[optional]
 * @return array an array of strings, each of which is a substring of
 */
function spliti ($pattern, $string, $limit = null) {}

/**
 * &Alias; <function>implode</function>
 * @link http://php.net/manual/en/function.join.php
 * @param glue
 * @param pieces
 */
function join ($glue, $pieces) {}

/**
 * Make regular expression for case insensitive match
 * @link http://php.net/manual/en/function.sql-regcase.php
 * @param string string
 * @return string a valid regular expression which will match
 */
function sql_regcase ($string) {}

/**
 * Loads a PHP extension at runtime
 * @link http://php.net/manual/en/function.dl.php
 * @param library string
 * @return int
 */
function dl ($library) {}

/**
 * Closes process file pointer
 * @link http://php.net/manual/en/function.pclose.php
 * @param handle resource
 * @return int the termination status of the process that was run.
 */
function pclose ($handle) {}

/**
 * Opens process file pointer
 * @link http://php.net/manual/en/function.popen.php
 * @param command string
 * @param mode string
 * @return resource a file pointer identical to that returned by
 */
function popen ($command, $mode) {}

/**
 * Outputs a file
 * @link http://php.net/manual/en/function.readfile.php
 * @param filename string
 * @param use_include_path bool[optional]
 * @param context resource[optional]
 * @return int the number of bytes read from the file. If an error
 */
function readfile ($filename, $use_include_path = null, $context = null) {}

/**
 * Rewind the position of a file pointer
 * @link http://php.net/manual/en/function.rewind.php
 * @param handle resource
 * @return bool
 */
function rewind ($handle) {}

/**
 * Removes directory
 * @link http://php.net/manual/en/function.rmdir.php
 * @param dirname string
 * @param context resource[optional]
 * @return bool
 */
function rmdir ($dirname, $context = null) {}

/**
 * Changes the current umask
 * @link http://php.net/manual/en/function.umask.php
 * @param mask int[optional]
 * @return int
 */
function umask ($mask = null) {}

/**
 * Closes an open file pointer
 * @link http://php.net/manual/en/function.fclose.php
 * @param handle resource
 * @return bool
 */
function fclose ($handle) {}

/**
 * Tests for end-of-file on a file pointer
 * @link http://php.net/manual/en/function.feof.php
 * @param handle resource
 * @return bool true if the file pointer is at EOF or an error occurs
 */
function feof ($handle) {}

/**
 * Gets character from file pointer
 * @link http://php.net/manual/en/function.fgetc.php
 * @param handle resource
 * @return string a string containing a single character read from the file pointed
 */
function fgetc ($handle) {}

/**
 * Gets line from file pointer
 * @link http://php.net/manual/en/function.fgets.php
 * @param handle resource
 * @param length int[optional]
 * @return string a string of up to length - 1 bytes read from
 */
function fgets ($handle, $length = null) {}

/**
 * Gets line from file pointer and strip HTML tags
 * @link http://php.net/manual/en/function.fgetss.php
 * @param handle resource
 * @param length int[optional]
 * @param allowable_tags string[optional]
 * @return string a string of up to length - 1 bytes read from
 */
function fgetss ($handle, $length = null, $allowable_tags = null) {}

/**
 * Binary-safe file read
 * @link http://php.net/manual/en/function.fread.php
 * @param handle resource
 * @param length int
 * @return string the read string or false in case of error.
 */
function fread ($handle, $length) {}

/**
 * Opens file or URL
 * @link http://php.net/manual/en/function.fopen.php
 * @param filename string
 * @param mode string
 * @param use_include_path bool[optional]
 * @param context resource[optional]
 * @return resource a file pointer resource on success, or false on error.
 */
function fopen ($filename, $mode, $use_include_path = null, $context = null) {}

/**
 * Output all remaining data on a file pointer
 * @link http://php.net/manual/en/function.fpassthru.php
 * @param handle resource
 * @return int
 */
function fpassthru ($handle) {}

/**
 * Truncates a file to a given length
 * @link http://php.net/manual/en/function.ftruncate.php
 * @param handle resource
 * @param size int
 * @return bool
 */
function ftruncate ($handle, $size) {}

/**
 * Gets information about a file using an open file pointer
 * @link http://php.net/manual/en/function.fstat.php
 * @param handle resource
 * @return array an array with the statistics of the file; the format of the array
 */
function fstat ($handle) {}

/**
 * Seeks on a file pointer
 * @link http://php.net/manual/en/function.fseek.php
 * @param handle resource
 * @param offset int
 * @param whence int[optional]
 * @return int
 */
function fseek ($handle, $offset, $whence = null) {}

/**
 * Tells file pointer read/write position
 * @link http://php.net/manual/en/function.ftell.php
 * @param handle resource
 * @return int the position of the file pointer referenced by
 */
function ftell ($handle) {}

/**
 * Flushes the output to a file
 * @link http://php.net/manual/en/function.fflush.php
 * @param handle resource
 * @return bool
 */
function fflush ($handle) {}

/**
 * Binary-safe file write
 * @link http://php.net/manual/en/function.fwrite.php
 * @param handle resource
 * @param string string
 * @param length int[optional]
 * @return int
 */
function fwrite ($handle, $string, $length = null) {}

/**
 * &Alias; <function>fwrite</function>
 * @link http://php.net/manual/en/function.fputs.php
 * @param fp
 * @param str
 * @param length[optional]
 */
function fputs ($fp, $str, $length) {}

/**
 * Makes directory
 * @link http://php.net/manual/en/function.mkdir.php
 * @param pathname string
 * @param mode int[optional]
 * @param recursive bool[optional]
 * @param context resource[optional]
 * @return bool
 */
function mkdir ($pathname, $mode = null, $recursive = null, $context = null) {}

/**
 * Renames a file or directory
 * @link http://php.net/manual/en/function.rename.php
 * @param oldname string
 * @param newname string
 * @param context resource[optional]
 * @return bool
 */
function rename ($oldname, $newname, $context = null) {}

/**
 * Copies file
 * @link http://php.net/manual/en/function.copy.php
 * @param source string
 * @param dest string
 * @return bool
 */
function copy ($source, $dest) {}

/**
 * Create file with unique file name
 * @link http://php.net/manual/en/function.tempnam.php
 * @param dir string
 * @param prefix string
 * @return string the new temporary filename, or false on
 */
function tempnam ($dir, $prefix) {}

/**
 * Creates a temporary file
 * @link http://php.net/manual/en/function.tmpfile.php
 * @return resource a file handle, similar to the one returned by
 */
function tmpfile () {}

/**
 * Reads entire file into an array
 * @link http://php.net/manual/en/function.file.php
 * @param filename string
 * @param flags int[optional]
 * @param context resource[optional]
 * @return array the file in an array. Each element of the array corresponds to a
 */
function file ($filename, $flags = null, $context = null) {}

/**
 * Reads entire file into a string
 * @link http://php.net/manual/en/function.file-get-contents.php
 * @param filename string
 * @param flags int[optional]
 * @param context resource[optional]
 * @param offset int[optional]
 * @param maxlen int[optional]
 * @return string
 */
function file_get_contents ($filename, $flags = null, $context = null, $offset = null, $maxlen = null) {}

/**
 * Write a string to a file
 * @link http://php.net/manual/en/function.file-put-contents.php
 * @param filename string
 * @param data mixed
 * @param flags int[optional]
 * @param context resource[optional]
 * @return int
 */
function file_put_contents ($filename, $data, $flags = null, $context = null) {}

/**
 * Runs the equivalent of the select() system call on the given 
     arrays of streams with a timeout specified by tv_sec and tv_usec
 * @link http://php.net/manual/en/function.stream-select.php
 * @param read_streams
 * @param write_streams
 * @param except_streams
 * @param tv_sec
 * @param tv_usec[optional]
 */
function stream_select (&$read_streams, &$write_streams, &$except_streams, $tv_sec, $tv_usec) {}

/**
 * Create a streams context
 * @link http://php.net/manual/en/function.stream-context-create.php
 * @param options[optional]
 */
function stream_context_create ($options) {}

/**
 * Set parameters for a stream/wrapper/context
 * @link http://php.net/manual/en/function.stream-context-set-params.php
 * @param stream_or_context
 * @param options
 */
function stream_context_set_params ($stream_or_context, $options) {}

/**
 * Sets an option for a stream/wrapper/context
 * @link http://php.net/manual/en/function.stream-context-set-option.php
 * @param stream_or_context
 * @param wrappername
 * @param optionname
 * @param value
 */
function stream_context_set_option ($stream_or_context, $wrappername, $optionname, $value) {}

/**
 * Retrieve options for a stream/wrapper/context
 * @link http://php.net/manual/en/function.stream-context-get-options.php
 * @param stream_or_context
 */
function stream_context_get_options ($stream_or_context) {}

/**
 * Retreive the default streams context
 * @link http://php.net/manual/en/function.stream-context-get-default.php
 * @param options[optional]
 */
function stream_context_get_default ($options) {}

/**
 * Attach a filter to a stream
 * @link http://php.net/manual/en/function.stream-filter-prepend.php
 * @param stream
 * @param filtername
 * @param read_write[optional]
 * @param filterparams[optional]
 */
function stream_filter_prepend ($stream, $filtername, $read_write, $filterparams) {}

/**
 * Attach a filter to a stream
 * @link http://php.net/manual/en/function.stream-filter-append.php
 * @param stream
 * @param filtername
 * @param read_write[optional]
 * @param filterparams[optional]
 */
function stream_filter_append ($stream, $filtername, $read_write, $filterparams) {}

/**
 * Remove a filter from a stream
 * @link http://php.net/manual/en/function.stream-filter-remove.php
 * @param stream_filter
 */
function stream_filter_remove ($stream_filter) {}

/**
 * Open Internet or Unix domain socket connection
 * @link http://php.net/manual/en/function.stream-socket-client.php
 * @param remoteaddress
 * @param errcode[optional]
 * @param errstring[optional]
 * @param timeout[optional]
 * @param flags[optional]
 * @param context[optional]
 */
function stream_socket_client ($remoteaddress, &$errcode, &$errstring, $timeout, $flags, $context) {}

/**
 * Create an Internet or Unix domain server socket
 * @link http://php.net/manual/en/function.stream-socket-server.php
 * @param localaddress
 * @param errcode[optional]
 * @param errstring[optional]
 * @param flags[optional]
 * @param context[optional]
 */
function stream_socket_server ($localaddress, &$errcode, &$errstring, $flags, $context) {}

/**
 * Accept a connection on a socket created by <function>stream_socket_server</function>
 * @link http://php.net/manual/en/function.stream-socket-accept.php
 * @param serverstream
 * @param timeout[optional]
 * @param peername[optional]
 */
function stream_socket_accept ($serverstream, $timeout, &$peername) {}

/**
 * Retrieve the name of the local or remote sockets
 * @link http://php.net/manual/en/function.stream-socket-get-name.php
 * @param stream
 * @param want_peer
 */
function stream_socket_get_name ($stream, $want_peer) {}

/**
 * Receives data from a socket, connected or not
 * @link http://php.net/manual/en/function.stream-socket-recvfrom.php
 * @param stream
 * @param amount
 * @param flags[optional]
 * @param remote_addr[optional]
 */
function stream_socket_recvfrom ($stream, $amount, $flags, &$remote_addr) {}

/**
 * Sends a message to a socket, whether it is connected or not
 * @link http://php.net/manual/en/function.stream-socket-sendto.php
 * @param stream
 * @param data
 * @param flags[optional]
 * @param target_addr[optional]
 */
function stream_socket_sendto ($stream, $data, $flags, $target_addr) {}

/**
 * Turns encryption on/off on an already connected socket
 * @link http://php.net/manual/en/function.stream-socket-enable-crypto.php
 * @param stream
 * @param enable
 * @param cryptokind[optional]
 * @param sessionstream[optional]
 */
function stream_socket_enable_crypto ($stream, $enable, $cryptokind, $sessionstream) {}

/**
 * Shutdown a full-duplex connection
 * @link http://php.net/manual/en/function.stream-socket-shutdown.php
 * @param stream resource
 * @param how int
 * @return bool
 */
function stream_socket_shutdown ($stream, $how) {}

/**
 * Creates a pair of connected, indistinguishable socket streams
 * @link http://php.net/manual/en/function.stream-socket-pair.php
 * @param domain int
 * @param type int
 * @param protocol int
 * @return array an array with the two socket resources on success, or
 */
function stream_socket_pair ($domain, $type, $protocol) {}

/**
 * Copies data from one stream to another
 * @link http://php.net/manual/en/function.stream-copy-to-stream.php
 * @param source resource
 * @param dest resource
 * @param maxlength int[optional]
 * @param offset int[optional]
 * @return int the total count of bytes copied.
 */
function stream_copy_to_stream ($source, $dest, $maxlength = null, $offset = null) {}

/**
 * Reads remainder of a stream into a string
 * @link http://php.net/manual/en/function.stream-get-contents.php
 * @param source
 * @param maxlen[optional]
 * @param offset[optional]
 */
function stream_get_contents ($source, $maxlen, $offset) {}

/**
 * Gets line from file pointer and parse for CSV fields
 * @link http://php.net/manual/en/function.fgetcsv.php
 * @param handle resource
 * @param length int[optional]
 * @param delimiter string[optional]
 * @param enclosure string[optional]
 * @return array an indexed array containing the fields read.
 */
function fgetcsv ($handle, $length = null, $delimiter = null, $enclosure = null) {}

/**
 * Format line as CSV and write to file pointer
 * @link http://php.net/manual/en/function.fputcsv.php
 * @param handle resource
 * @param fields array
 * @param delimiter string[optional]
 * @param enclosure string[optional]
 * @return int the length of the written string, or false on failure.
 */
function fputcsv ($handle, array $fields, $delimiter = null, $enclosure = null) {}

/**
 * Portable advisory file locking
 * @link http://php.net/manual/en/function.flock.php
 * @param handle resource
 * @param operation int
 * @param wouldblock int[optional]
 * @return bool
 */
function flock ($handle, $operation, &$wouldblock = null) {}

/**
 * Extracts all meta tag content attributes from a file and returns an array
 * @link http://php.net/manual/en/function.get-meta-tags.php
 * @param filename string
 * @param use_include_path bool[optional]
 * @return array an array with all the parsed meta tags.
 */
function get_meta_tags ($filename, $use_include_path = null) {}

/**
 * Sets file buffering on the given stream
 * @link http://php.net/manual/en/function.stream-set-write-buffer.php
 * @param fp
 * @param buffer
 */
function stream_set_write_buffer ($fp, $buffer) {}

/**
 * &Alias; <function>stream_set_write_buffer</function>
 * @link http://php.net/manual/en/function.set-file-buffer.php
 * @param fp
 * @param buffer
 */
function set_file_buffer ($fp, $buffer) {}

/**
 * @param socket
 * @param mode
 */
function set_socket_blocking ($socket, $mode) {}

/**
 * Set blocking/non-blocking mode on a stream
 * @link http://php.net/manual/en/function.stream-set-blocking.php
 * @param socket
 * @param mode
 */
function stream_set_blocking ($socket, $mode) {}

/**
 * &Alias; <function>stream_set_blocking</function>
 * @link http://php.net/manual/en/function.socket-set-blocking.php
 * @param socket
 * @param mode
 */
function socket_set_blocking ($socket, $mode) {}

/**
 * Retrieves header/meta data from streams/file pointers
 * @link http://php.net/manual/en/function.stream-get-meta-data.php
 * @param fp
 */
function stream_get_meta_data ($fp) {}

/**
 * Gets line from stream resource up to a given delimiter
 * @link http://php.net/manual/en/function.stream-get-line.php
 * @param stream
 * @param maxlen
 * @param ending[optional]
 */
function stream_get_line ($stream, $maxlen, $ending) {}

/**
 * Register a URL wrapper implemented as a PHP class
 * @link http://php.net/manual/en/function.stream-wrapper-register.php
 * @param protocol
 * @param classname
 * @param flags[optional]
 */
function stream_wrapper_register ($protocol, $classname, $flags) {}

/**
 * Alias of <function>stream_wrapper_register</function>
 * @link http://php.net/manual/en/function.stream-register-wrapper.php
 * @param protocol
 * @param classname
 * @param flags[optional]
 */
function stream_register_wrapper ($protocol, $classname, $flags) {}

/**
 * Unregister a URL wrapper
 * @link http://php.net/manual/en/function.stream-wrapper-unregister.php
 * @param protocol
 */
function stream_wrapper_unregister ($protocol) {}

/**
 * Restores a previously unregistered built-in wrapper
 * @link http://php.net/manual/en/function.stream-wrapper-restore.php
 * @param protocol
 */
function stream_wrapper_restore ($protocol) {}

/**
 * Retrieve list of registered streams
 * @link http://php.net/manual/en/function.stream-get-wrappers.php
 */
function stream_get_wrappers () {}

/**
 * Retrieve list of registered socket transports
 * @link http://php.net/manual/en/function.stream-get-transports.php
 */
function stream_get_transports () {}

/**
 * @param stream
 */
function stream_is_local ($stream) {}

/**
 * Fetches all the headers sent by the server in response to a HTTP request
 * @link http://php.net/manual/en/function.get-headers.php
 * @param url string
 * @param format int[optional]
 * @return array an indexed or associative array with the headers, or false on
 */
function get_headers ($url, $format = null) {}

/**
 * Set timeout period on a stream
 * @link http://php.net/manual/en/function.stream-set-timeout.php
 * @param stream
 * @param seconds
 * @param microseconds
 */
function stream_set_timeout ($stream, $seconds, $microseconds) {}

/**
 * &Alias; <function>stream_set_timeout</function>
 * @link http://php.net/manual/en/function.socket-set-timeout.php
 * @param stream
 * @param seconds
 * @param microseconds
 */
function socket_set_timeout ($stream, $seconds, $microseconds) {}

/**
 * &Alias; <function>stream_get_meta_data</function>
 * @link http://php.net/manual/en/function.socket-get-status.php
 * @param fp
 */
function socket_get_status ($fp) {}

/**
 * Returns canonicalized absolute pathname
 * @link http://php.net/manual/en/function.realpath.php
 * @param path string
 * @return string the canonicalized absolute pathname on success. The resulting path 
 */
function realpath ($path) {}

/**
 * Match filename against a pattern
 * @link http://php.net/manual/en/function.fnmatch.php
 * @param pattern string
 * @param string string
 * @param flags int[optional]
 * @return bool true if there is a match, false otherwise.
 */
function fnmatch ($pattern, $string, $flags = null) {}

/**
 * Open Internet or Unix domain socket connection
 * @link http://php.net/manual/en/function.fsockopen.php
 * @param hostname string
 * @param port int[optional]
 * @param errno int[optional]
 * @param errstr string[optional]
 * @param timeout float[optional]
 * @return resource
 */
function fsockopen ($hostname, $port = null, &$errno = null, &$errstr = null, $timeout = null) {}

/**
 * Open persistent Internet or Unix domain socket connection
 * @link http://php.net/manual/en/function.pfsockopen.php
 * @param hostname string
 * @param port int[optional]
 * @param errno int[optional]
 * @param errstr string[optional]
 * @param timeout float[optional]
 * @return resource
 */
function pfsockopen ($hostname, $port = null, &$errno = null, &$errstr = null, $timeout = null) {}

/**
 * Pack data into binary string
 * @link http://php.net/manual/en/function.pack.php
 * @param format string
 * @param args mixed[optional]
 * @param ... mixed[optional]
 * @return string a binary string containing data.
 */
function pack ($format, $args = null) {}

/**
 * Unpack data from binary string
 * @link http://php.net/manual/en/function.unpack.php
 * @param format string
 * @param data string
 * @return array an associative array containing unpacked elements of binary
 */
function unpack ($format, $data) {}

/**
 * Tells what the user's browser is capable of
 * @link http://php.net/manual/en/function.get-browser.php
 * @param user_agent string[optional]
 * @param return_array bool[optional]
 * @return mixed
 */
function get_browser ($user_agent = null, $return_array = null) {}

/**
 * One-way string encryption (hashing)
 * @link http://php.net/manual/en/function.crypt.php
 * @param str string
 * @param salt string[optional]
 * @return string the encrypted string.
 */
function crypt ($str, $salt = null) {}

/**
 * Open directory handle
 * @link http://php.net/manual/en/function.opendir.php
 * @param path string
 * @param context resource[optional]
 * @return resource a directory handle resource on success, or
 */
function opendir ($path, $context = null) {}

/**
 * Close directory handle
 * @link http://php.net/manual/en/function.closedir.php
 * @param dir_handle resource
 * @return void
 */
function closedir ($dir_handle) {}

/**
 * Change directory
 * @link http://php.net/manual/en/function.chdir.php
 * @param directory string
 * @return bool
 */
function chdir ($directory) {}

/**
 * Change the root directory
 * @link http://php.net/manual/en/function.chroot.php
 * @param directory string
 * @return bool
 */
function chroot ($directory) {}

/**
 * Gets the current working directory
 * @link http://php.net/manual/en/function.getcwd.php
 * @return string the current working directory on success, or false on
 */
function getcwd () {}

/**
 * Rewind directory handle
 * @link http://php.net/manual/en/function.rewinddir.php
 * @param dir_handle resource
 * @return void
 */
function rewinddir ($dir_handle) {}

/**
 * Read entry from directory handle
 * @link http://php.net/manual/en/function.readdir.php
 * @param dir_handle resource
 * @return string the filename on success, or false on failure.
 */
function readdir ($dir_handle) {}

/**
 * Return an instance of the Directory class
 * @link http://php.net/manual/en/class.dir.php
 * @param directory
 * @param context[optional]
 * @return string
 */
function dir ($directory, $context) {}

/**
 * List files and directories inside the specified path
 * @link http://php.net/manual/en/function.scandir.php
 * @param directory string
 * @param sorting_order int[optional]
 * @param context resource[optional]
 * @return array an array of filenames on success, or false on 
 */
function scandir ($directory, $sorting_order = null, $context = null) {}

/**
 * Find pathnames matching a pattern
 * @link http://php.net/manual/en/function.glob.php
 * @param pattern string
 * @param flags int[optional]
 * @return array an array containing the matched files/directories, an empty array
 */
function glob ($pattern, $flags = null) {}

/**
 * Gets last access time of file
 * @link http://php.net/manual/en/function.fileatime.php
 * @param filename string
 * @return int the time the file was last accessed, or false in case of
 */
function fileatime ($filename) {}

/**
 * Gets inode change time of file
 * @link http://php.net/manual/en/function.filectime.php
 * @param filename string
 * @return int the time the file was last changed, or false in case of
 */
function filectime ($filename) {}

/**
 * Gets file group
 * @link http://php.net/manual/en/function.filegroup.php
 * @param filename string
 * @return int the group ID of the file, or false in case
 */
function filegroup ($filename) {}

/**
 * Gets file inode
 * @link http://php.net/manual/en/function.fileinode.php
 * @param filename string
 * @return int the inode number of the file, or false in case of an error.
 */
function fileinode ($filename) {}

/**
 * Gets file modification time
 * @link http://php.net/manual/en/function.filemtime.php
 * @param filename string
 * @return int the time the file was last modified, or false in case of
 */
function filemtime ($filename) {}

/**
 * Gets file owner
 * @link http://php.net/manual/en/function.fileowner.php
 * @param filename string
 * @return int the user ID of the owner of the file, or false in case of
 */
function fileowner ($filename) {}

/**
 * Gets file permissions
 * @link http://php.net/manual/en/function.fileperms.php
 * @param filename string
 * @return int the permissions on the file, or false in case of an error.
 */
function fileperms ($filename) {}

/**
 * Gets file size
 * @link http://php.net/manual/en/function.filesize.php
 * @param filename string
 * @return int the size of the file in bytes, or false (and generates an error
 */
function filesize ($filename) {}

/**
 * Gets file type
 * @link http://php.net/manual/en/function.filetype.php
 * @param filename string
 * @return string the type of the file. Possible values are fifo, char,
 */
function filetype ($filename) {}

/**
 * Checks whether a file or directory exists
 * @link http://php.net/manual/en/function.file-exists.php
 * @param filename string
 * @return bool true if the file or directory specified by
 */
function file_exists ($filename) {}

/**
 * Tells whether the filename is writable
 * @link http://php.net/manual/en/function.is-writable.php
 * @param filename string
 * @return bool true if the filename exists and is
 */
function is_writable ($filename) {}

/**
 * &Alias; <function>is_writable</function>
 * @link http://php.net/manual/en/function.is-writeable.php
 * @param filename
 */
function is_writeable ($filename) {}

/**
 * Tells whether the filename is readable
 * @link http://php.net/manual/en/function.is-readable.php
 * @param filename string
 * @return bool true if the file or directory specified by
 */
function is_readable ($filename) {}

/**
 * Tells whether the filename is executable
 * @link http://php.net/manual/en/function.is-executable.php
 * @param filename string
 * @return bool true if the filename exists and is executable, or false on
 */
function is_executable ($filename) {}

/**
 * Tells whether the filename is a regular file
 * @link http://php.net/manual/en/function.is-file.php
 * @param filename string
 * @return bool true if the filename exists and is a regular file, false
 */
function is_file ($filename) {}

/**
 * Tells whether the filename is a directory
 * @link http://php.net/manual/en/function.is-dir.php
 * @param filename string
 * @return bool true if the filename exists and is a directory, false
 */
function is_dir ($filename) {}

/**
 * Tells whether the filename is a symbolic link
 * @link http://php.net/manual/en/function.is-link.php
 * @param filename string
 * @return bool true if the filename exists and is a symbolic link, false
 */
function is_link ($filename) {}

/**
 * Gives information about a file
 * @link http://php.net/manual/en/function.stat.php
 * @param filename string
 * @return array
 */
function stat ($filename) {}

/**
 * Gives information about a file or symbolic link
 * @link http://php.net/manual/en/function.lstat.php
 * @param filename string
 * @return array
 */
function lstat ($filename) {}

/**
 * Changes file owner
 * @link http://php.net/manual/en/function.chown.php
 * @param filename string
 * @param user mixed
 * @return bool
 */
function chown ($filename, $user) {}

/**
 * Changes file group
 * @link http://php.net/manual/en/function.chgrp.php
 * @param filename string
 * @param group mixed
 * @return bool
 */
function chgrp ($filename, $group) {}

/**
 * Changes user ownership of symlink
 * @link http://php.net/manual/en/function.lchown.php
 * @param filename string
 * @param user mixed
 * @return bool
 */
function lchown ($filename, $user) {}

/**
 * Changes group ownership of symlink
 * @link http://php.net/manual/en/function.lchgrp.php
 * @param filename string
 * @param group mixed
 * @return bool
 */
function lchgrp ($filename, $group) {}

/**
 * Changes file mode
 * @link http://php.net/manual/en/function.chmod.php
 * @param filename string
 * @param mode int
 * @return bool
 */
function chmod ($filename, $mode) {}

/**
 * Sets access and modification time of file
 * @link http://php.net/manual/en/function.touch.php
 * @param filename string
 * @param time int[optional]
 * @param atime int[optional]
 * @return bool
 */
function touch ($filename, $time = null, $atime = null) {}

/**
 * Clears file status cache
 * @link http://php.net/manual/en/function.clearstatcache.php
 * @return void
 */
function clearstatcache () {}

/**
 * Returns the total size of a directory
 * @link http://php.net/manual/en/function.disk-total-space.php
 * @param directory string
 * @return float the total number of bytes as a float. 
 */
function disk_total_space ($directory) {}

/**
 * Returns available space in directory
 * @link http://php.net/manual/en/function.disk-free-space.php
 * @param directory string
 * @return float the number of available bytes as a float. 
 */
function disk_free_space ($directory) {}

/**
 * &Alias; <function>disk_free_space</function>
 * @link http://php.net/manual/en/function.diskfreespace.php
 * @param path
 */
function diskfreespace ($path) {}

/**
 * Send mail
 * @link http://php.net/manual/en/function.mail.php
 * @param to string
 * @param subject string
 * @param message string
 * @param additional_headers string[optional]
 * @param additional_parameters string[optional]
 * @return bool true if the mail was successfully accepted for delivery, false otherwise.
 */
function mail ($to, $subject, $message, $additional_headers = null, $additional_parameters = null) {}

/**
 * Calculate the hash value needed by EZMLM
 * @link http://php.net/manual/en/function.ezmlm-hash.php
 * @param addr string
 * @return int
 */
function ezmlm_hash ($addr) {}

/**
 * Open connection to system logger
 * @link http://php.net/manual/en/function.openlog.php
 * @param ident string
 * @param option int
 * @param facility int
 * @return bool
 */
function openlog ($ident, $option, $facility) {}

/**
 * Generate a system log message
 * @link http://php.net/manual/en/function.syslog.php
 * @param priority int
 * @param message string
 * @return bool
 */
function syslog ($priority, $message) {}

/**
 * Close connection to system logger
 * @link http://php.net/manual/en/function.closelog.php
 * @return bool
 */
function closelog () {}

/**
 * Initializes all syslog related constants
 * @link http://php.net/manual/en/function.define-syslog-variables.php
 * @return void
 */
function define_syslog_variables () {}

/**
 * Combined linear congruential generator
 * @link http://php.net/manual/en/function.lcg-value.php
 * @return float
 */
function lcg_value () {}

/**
 * Calculate the metaphone key of a string
 * @link http://php.net/manual/en/function.metaphone.php
 * @param str string
 * @param phones int[optional]
 * @return string the metaphone key as a string.
 */
function metaphone ($str, $phones = null) {}

/**
 * Turn on output buffering
 * @link http://php.net/manual/en/function.ob-start.php
 * @param output_callback callback[optional]
 * @param chunk_size int[optional]
 * @param erase bool[optional]
 * @return bool
 */
function ob_start ($output_callback = null, $chunk_size = null, $erase = null) {}

/**
 * Flush (send) the output buffer
 * @link http://php.net/manual/en/function.ob-flush.php
 * @return void
 */
function ob_flush () {}

/**
 * Clean (erase) the output buffer
 * @link http://php.net/manual/en/function.ob-clean.php
 * @return void
 */
function ob_clean () {}

/**
 * Flush (send) the output buffer and turn off output buffering
 * @link http://php.net/manual/en/function.ob-end-flush.php
 * @return bool
 */
function ob_end_flush () {}

/**
 * Clean (erase) the output buffer and turn off output buffering
 * @link http://php.net/manual/en/function.ob-end-clean.php
 * @return bool
 */
function ob_end_clean () {}

/**
 * Flush the output buffer, return it as a string and turn off output buffering
 * @link http://php.net/manual/en/function.ob-get-flush.php
 * @return string the output buffer or false if no buffering is active.
 */
function ob_get_flush () {}

/**
 * Get current buffer contents and delete current output buffer
 * @link http://php.net/manual/en/function.ob-get-clean.php
 * @return string the contents of the output buffer and end output buffering.
 */
function ob_get_clean () {}

/**
 * Return the length of the output buffer
 * @link http://php.net/manual/en/function.ob-get-length.php
 * @return int the length of the output buffer contents or false if no
 */
function ob_get_length () {}

/**
 * Return the nesting level of the output buffering mechanism
 * @link http://php.net/manual/en/function.ob-get-level.php
 * @return int the level of nested output buffering handlers or zero if output
 */
function ob_get_level () {}

/**
 * Get status of output buffers
 * @link http://php.net/manual/en/function.ob-get-status.php
 * @param full_status bool[optional]
 * @return array
 */
function ob_get_status ($full_status = null) {}

/**
 * Return the contents of the output buffer
 * @link http://php.net/manual/en/function.ob-get-contents.php
 * @return string
 */
function ob_get_contents () {}

/**
 * Turn implicit flush on/off
 * @link http://php.net/manual/en/function.ob-implicit-flush.php
 * @param flag int[optional]
 * @return void
 */
function ob_implicit_flush ($flag = null) {}

/**
 * List all output handlers in use
 * @link http://php.net/manual/en/function.ob-list-handlers.php
 * @return array
 */
function ob_list_handlers () {}

/**
 * Sort an array by key
 * @link http://php.net/manual/en/function.ksort.php
 * @param arg
 * @param sort_flags[optional]
 */
function ksort (&$arg, $sort_flags) {}

/**
 * Sort an array by key in reverse order
 * @link http://php.net/manual/en/function.krsort.php
 * @param arg
 * @param sort_flags[optional]
 */
function krsort (&$arg, $sort_flags) {}

/**
 * Sort an array using a "natural order" algorithm
 * @link http://php.net/manual/en/function.natsort.php
 * @param arg
 */
function natsort (&$arg) {}

/**
 * Sort an array using a case insensitive "natural order" algorithm
 * @link http://php.net/manual/en/function.natcasesort.php
 * @param arg
 */
function natcasesort (&$arg) {}

/**
 * Sort an array and maintain index association
 * @link http://php.net/manual/en/function.asort.php
 * @param arg
 * @param sort_flags[optional]
 */
function asort (&$arg, $sort_flags) {}

/**
 * Sort an array in reverse order and maintain index association
 * @link http://php.net/manual/en/function.arsort.php
 * @param arg
 * @param sort_flags[optional]
 */
function arsort (&$arg, $sort_flags) {}

/**
 * Sort an array
 * @link http://php.net/manual/en/function.sort.php
 * @param arg
 * @param sort_flags[optional]
 */
function sort (&$arg, $sort_flags) {}

/**
 * Sort an array in reverse order
 * @link http://php.net/manual/en/function.rsort.php
 * @param arg
 * @param sort_flags[optional]
 */
function rsort (&$arg, $sort_flags) {}

/**
 * Sort an array by values using a user-defined comparison function
 * @link http://php.net/manual/en/function.usort.php
 * @param arg
 * @param cmp_function
 */
function usort (&$arg, $cmp_function) {}

/**
 * Sort an array with a user-defined comparison function and maintain index association
 * @link http://php.net/manual/en/function.uasort.php
 * @param arg
 * @param cmp_function
 */
function uasort (&$arg, $cmp_function) {}

/**
 * Sort an array by keys using a user-defined comparison function
 * @link http://php.net/manual/en/function.uksort.php
 * @param arg
 * @param cmp_function
 */
function uksort (&$arg, $cmp_function) {}

/**
 * Shuffle an array
 * @link http://php.net/manual/en/function.shuffle.php
 * @param arg
 */
function shuffle (&$arg) {}

/**
 * Apply a user function to every member of an array
 * @link http://php.net/manual/en/function.array-walk.php
 * @param input
 * @param funcname
 * @param userdata[optional]
 */
function array_walk (&$input, $funcname, $userdata) {}

/**
 * Apply a user function recursively to every member of an array
 * @link http://php.net/manual/en/function.array-walk-recursive.php
 * @param input
 * @param funcname
 * @param userdata[optional]
 */
function array_walk_recursive (&$input, $funcname, $userdata) {}

/**
 * Count elements in an array, or properties in an object
 * @link http://php.net/manual/en/function.count.php
 * @param var
 * @param mode[optional]
 */
function count ($var, $mode) {}

/**
 * Set the internal pointer of an array to its last element
 * @link http://php.net/manual/en/function.end.php
 * @param arg
 */
function end (&$arg) {}

/**
 * Rewind the internal array pointer
 * @link http://php.net/manual/en/function.prev.php
 * @param arg
 */
function prev (&$arg) {}

/**
 * Advance the internal array pointer of an array
 * @link http://php.net/manual/en/function.next.php
 * @param arg
 */
function next (&$arg) {}

/**
 * Set the internal pointer of an array to its first element
 * @link http://php.net/manual/en/function.reset.php
 * @param arg
 */
function reset (&$arg) {}

/**
 * Return the current element in an array
 * @link http://php.net/manual/en/function.current.php
 * @param arg
 */
function current (&$arg) {}

/**
 * Fetch a key from an associative array
 * @link http://php.net/manual/en/function.key.php
 * @param arg
 */
function key (&$arg) {}

/**
 * Find lowest value
 * @link http://php.net/manual/en/function.min.php
 * @param values array
 * @return mixed
 */
function min (array $values) {}

/**
 * Find highest value
 * @link http://php.net/manual/en/function.max.php
 * @param values array
 * @return mixed
 */
function max (array $values) {}

/**
 * Checks if a value exists in an array
 * @link http://php.net/manual/en/function.in-array.php
 * @param needle
 * @param haystack
 * @param strict[optional]
 */
function in_array ($needle, $haystack, $strict) {}

/**
 * Searches the array for a given value and returns the corresponding key if successful
 * @link http://php.net/manual/en/function.array-search.php
 * @param needle
 * @param haystack
 * @param strict[optional]
 */
function array_search ($needle, $haystack, $strict) {}

/**
 * Import variables into the current symbol table from an array
 * @link http://php.net/manual/en/function.extract.php
 * @param arg
 * @param extract_type[optional]
 * @param prefix[optional]
 */
function extract ($arg, $extract_type, $prefix) {}

/**
 * Create array containing variables and their values
 * @link http://php.net/manual/en/function.compact.php
 * @param var_names
 * @param ...[optional]
 */
function compact ($var_names) {}

/**
 * Fill an array with values
 * @link http://php.net/manual/en/function.array-fill.php
 * @param start_index int
 * @param num int
 * @param value mixed
 * @return array the filled array
 */
function array_fill ($start_index, $num, $value) {}

/**
 * Fill an array with values, specifying keys
 * @link http://php.net/manual/en/function.array-fill-keys.php
 * @param keys array
 * @param value mixed
 * @return array the filled array
 */
function array_fill_keys (array $keys, $value) {}

/**
 * Create an array containing a range of elements
 * @link http://php.net/manual/en/function.range.php
 * @param low
 * @param high
 * @param step[optional]
 */
function range ($low, $high, $step) {}

/**
 * Sort multiple or multi-dimensional arrays
 * @link http://php.net/manual/en/function.array-multisort.php
 * @param arr1
 * @param SORT_ASC_or_SORT_DESC[optional]
 * @param SORT_REGULAR_or_SORT_NUMERIC_or_SORT_STRING[optional]
 * @param arr2[optional]
 * @param SORT_ASC_or_SORT_DESC[optional]
 * @param SORT_REGULAR_or_SORT_NUMERIC_or_SORT_STRING[optional]
 */
function array_multisort (&$arr1, &$SORT_ASC_or_SORT_DESC, &$SORT_REGULAR_or_SORT_NUMERIC_or_SORT_STRING, &$arr2, &$SORT_ASC_or_SORT_DESC, &$SORT_REGULAR_or_SORT_NUMERIC_or_SORT_STRING) {}

/**
 * Push one or more elements onto the end of array
 * @link http://php.net/manual/en/function.array-push.php
 * @param stack
 * @param var
 * @param ...[optional]
 */
function array_push (&$stack, $var) {}

/**
 * Pop the element off the end of array
 * @link http://php.net/manual/en/function.array-pop.php
 * @param array array
 * @return mixed the last value of array.
 */
function array_pop (array &$array) {}

/**
 * Shift an element off the beginning of array
 * @link http://php.net/manual/en/function.array-shift.php
 * @param stack
 */
function array_shift (&$stack) {}

/**
 * Prepend one or more elements to the beginning of an array
 * @link http://php.net/manual/en/function.array-unshift.php
 * @param stack
 * @param var
 * @param ...[optional]
 */
function array_unshift (&$stack, $var) {}

/**
 * Remove a portion of the array and replace it with something else
 * @link http://php.net/manual/en/function.array-splice.php
 * @param arg
 * @param offset
 * @param length[optional]
 * @param replacement[optional]
 */
function array_splice (&$arg, $offset, $length, $replacement) {}

/**
 * Extract a slice of the array
 * @link http://php.net/manual/en/function.array-slice.php
 * @param arg
 * @param offset
 * @param length[optional]
 * @param preserve_keys[optional]
 */
function array_slice ($arg, $offset, $length, $preserve_keys) {}

/**
 * Merge one or more arrays
 * @link http://php.net/manual/en/function.array-merge.php
 * @param arr1
 * @param arr2
 * @param ...[optional]
 */
function array_merge ($arr1, $arr2) {}

/**
 * Merge two or more arrays recursively
 * @link http://php.net/manual/en/function.array-merge-recursive.php
 * @param array1 array
 * @param ... array[optional]
 * @return array
 */
function array_merge_recursive (array $array1) {}

/**
 * Return all the keys of an array
 * @link http://php.net/manual/en/function.array-keys.php
 * @param input array
 * @param search_value mixed[optional]
 * @param strict bool[optional]
 * @return array an array of all the keys in input.
 */
function array_keys (array $input, $search_value = null, $strict = null) {}

/**
 * Return all the values of an array
 * @link http://php.net/manual/en/function.array-values.php
 * @param arg
 */
function array_values ($arg) {}

/**
 * Counts all the values of an array
 * @link http://php.net/manual/en/function.array-count-values.php
 * @param input array
 * @return array an assosiative array of values from input as
 */
function array_count_values (array $input) {}

/**
 * Return an array with elements in reverse order
 * @link http://php.net/manual/en/function.array-reverse.php
 * @param input
 * @param preserve_keys[optional]
 */
function array_reverse ($input, $preserve_keys) {}

/**
 * Iteratively reduce the array to a single value using a callback function
 * @link http://php.net/manual/en/function.array-reduce.php
 * @param arg
 * @param callback
 * @param initial[optional]
 */
function array_reduce ($arg, $callback, $initial) {}

/**
 * Pad array to the specified length with a value
 * @link http://php.net/manual/en/function.array-pad.php
 * @param input array
 * @param pad_size int
 * @param pad_value mixed
 * @return array a copy of the input padded to size specified
 */
function array_pad (array $input, $pad_size, $pad_value) {}

/**
 * Exchanges all keys with their associated values in an array
 * @link http://php.net/manual/en/function.array-flip.php
 * @param trans array
 * @return array the flipped array on success and false on failure.
 */
function array_flip (array $trans) {}

/**
 * Changes all keys in an array
 * @link http://php.net/manual/en/function.array-change-key-case.php
 * @param input array
 * @param case int[optional]
 * @return array an array with its keys lower or uppercased, or false if
 */
function array_change_key_case (array $input, $case = null) {}

/**
 * Pick one or more random entries out of an array
 * @link http://php.net/manual/en/function.array-rand.php
 * @param arg
 * @param num_req[optional]
 */
function array_rand ($arg, $num_req) {}

/**
 * Removes duplicate values from an array
 * @link http://php.net/manual/en/function.array-unique.php
 * @param arg
 */
function array_unique ($arg) {}

/**
 * Computes the intersection of arrays
 * @link http://php.net/manual/en/function.array-intersect.php
 * @param array1 array
 * @param array2 array
 * @param ... array[optional]
 * @return array an array containing all of the values in 
 */
function array_intersect (array $array1, array $array2) {}

/**
 * Computes the intersection of arrays using keys for comparison
 * @link http://php.net/manual/en/function.array-intersect-key.php
 * @param array1 array
 * @param array2 array
 * @param ... array[optional]
 * @return array an associative array containing all the values of 
 */
function array_intersect_key (array $array1, array $array2) {}

/**
 * Computes the intersection of arrays using a callback function on the keys for comparison
 * @link http://php.net/manual/en/function.array-intersect-ukey.php
 * @param array1 array
 * @param array2 array
 * @param ... array[optional]
 * @param key_compare_func callback
 * @return array the values of array1 whose keys exist
 */
function array_intersect_ukey (array $array1, array $array2, $key_compare_func) {}

/**
 * Computes the intersection of arrays, compares data by a callback function
 * @link http://php.net/manual/en/function.array-uintersect.php
 * @param arr1
 * @param arr2
 * @param callback_data_compare_func
 */
function array_uintersect ($arr1, $arr2, $callback_data_compare_func) {}

/**
 * Computes the intersection of arrays with additional index check
 * @link http://php.net/manual/en/function.array-intersect-assoc.php
 * @param array1 array
 * @param array2 array
 * @param ... array[optional]
 * @return array an associative array containing all the values in 
 */
function array_intersect_assoc (array $array1, array $array2) {}

/**
 * Computes the intersection of arrays with additional index check, compares data by a callback function
 * @link http://php.net/manual/en/function.array-uintersect-assoc.php
 * @param arr1
 * @param arr2
 * @param callback_data_compare_func
 */
function array_uintersect_assoc ($arr1, $arr2, $callback_data_compare_func) {}

/**
 * Computes the intersection of arrays with additional index check, compares indexes by a callback function
 * @link http://php.net/manual/en/function.array-intersect-uassoc.php
 * @param array1 array
 * @param array2 array
 * @param ... array[optional]
 * @param key_compare_func callback
 * @return array the values of array1 whose values exist
 */
function array_intersect_uassoc (array $array1, array $array2, $key_compare_func) {}

/**
 * Computes the intersection of arrays with additional index check, compares data and indexes by a callback functions
 * @link http://php.net/manual/en/function.array-uintersect-uassoc.php
 * @param arr1
 * @param arr2
 * @param callback_data_compare_func
 * @param callback_key_compare_func
 */
function array_uintersect_uassoc ($arr1, $arr2, $callback_data_compare_func, $callback_key_compare_func) {}

/**
 * Computes the difference of arrays
 * @link http://php.net/manual/en/function.array-diff.php
 * @param array1 array
 * @param array2 array
 * @param ... array[optional]
 * @return array
 */
function array_diff (array $array1, array $array2) {}

/**
 * Computes the difference of arrays using keys for comparison
 * @link http://php.net/manual/en/function.array-diff-key.php
 * @param array1 array
 * @param array2 array
 * @param ... array[optional]
 * @return array an array containing all the entries from
 */
function array_diff_key (array $array1, array $array2) {}

/**
 * Computes the difference of arrays using a callback function on the keys for comparison
 * @link http://php.net/manual/en/function.array-diff-ukey.php
 * @param array1 array
 * @param array2 array
 * @param ... array[optional]
 * @param key_compare_func callback
 * @return array an array containing all the entries from
 */
function array_diff_ukey (array $array1, array $array2, $key_compare_func) {}

/**
 * Computes the difference of arrays by using a callback function for data comparison
 * @link http://php.net/manual/en/function.array-udiff.php
 * @param arr1
 * @param arr2
 * @param callback_data_comp_func
 */
function array_udiff ($arr1, $arr2, $callback_data_comp_func) {}

/**
 * Computes the difference of arrays with additional index check
 * @link http://php.net/manual/en/function.array-diff-assoc.php
 * @param array1 array
 * @param array2 array
 * @param ... array[optional]
 * @return array an array containing all the values from
 */
function array_diff_assoc (array $array1, array $array2) {}

/**
 * Computes the difference of arrays with additional index check, compares data by a callback function
 * @link http://php.net/manual/en/function.array-udiff-assoc.php
 * @param arr1
 * @param arr2
 * @param callback_key_comp_func
 */
function array_udiff_assoc ($arr1, $arr2, $callback_key_comp_func) {}

/**
 * Computes the difference of arrays with additional index check which is performed by a user supplied callback function
 * @link http://php.net/manual/en/function.array-diff-uassoc.php
 * @param array1 array
 * @param array2 array
 * @param ... array[optional]
 * @param key_compare_func callback
 * @return array an array containing all the entries from
 */
function array_diff_uassoc (array $array1, array $array2, $key_compare_func) {}

/**
 * Computes the difference of arrays with additional index check, compares data and indexes by a callback function
 * @link http://php.net/manual/en/function.array-udiff-uassoc.php
 * @param arr1
 * @param arr2
 * @param callback_data_comp_func
 * @param callback_key_comp_func
 */
function array_udiff_uassoc ($arr1, $arr2, $callback_data_comp_func, $callback_key_comp_func) {}

/**
 * Calculate the sum of values in an array
 * @link http://php.net/manual/en/function.array-sum.php
 * @param arg
 */
function array_sum ($arg) {}

/**
 * Calculate the product of values in an array
 * @link http://php.net/manual/en/function.array-product.php
 * @param arg
 */
function array_product ($arg) {}

/**
 * Filters elements of an array using a callback function
 * @link http://php.net/manual/en/function.array-filter.php
 * @param input array
 * @param callback callback[optional]
 * @return array the filtered array.
 */
function array_filter (array $input, $callback = null) {}

/**
 * Applies the callback to the elements of the given arrays
 * @link http://php.net/manual/en/function.array-map.php
 * @param callback callback
 * @param arr1 array
 * @param ... array[optional]
 * @return array an array containing all the elements of arr1
 */
function array_map ($callback, array $arr1) {}

/**
 * Split an array into chunks
 * @link http://php.net/manual/en/function.array-chunk.php
 * @param input array
 * @param size int
 * @param preserve_keys bool[optional]
 * @return array a multidimensional numerically indexed array, starting with zero,
 */
function array_chunk (array $input, $size, $preserve_keys = null) {}

/**
 * Creates an array by using one array for keys and another for its values
 * @link http://php.net/manual/en/function.array-combine.php
 * @param keys array
 * @param values array
 * @return array the combined array, false if the number of elements
 */
function array_combine (array $keys, array $values) {}

/**
 * Checks if the given key or index exists in the array
 * @link http://php.net/manual/en/function.array-key-exists.php
 * @param key mixed
 * @param search array
 * @return bool
 */
function array_key_exists ($key, array $search) {}

/**
 * &Alias; <function>current</function>
 * @link http://php.net/manual/en/function.pos.php
 * @param arg
 */
function pos (&$arg) {}

/**
 * &Alias; <function>count</function>
 * @link http://php.net/manual/en/function.sizeof.php
 * @param var
 * @param mode[optional]
 */
function sizeof ($var, $mode) {}

/**
 * @param key
 * @param search
 */
function key_exists ($key, $search) {}

/**
 * Checks if assertion is &false;
 * @link http://php.net/manual/en/function.assert.php
 * @param assertion mixed
 * @return bool
 */
function assert ($assertion) {}

/**
 * Set/get the various assert flags
 * @link http://php.net/manual/en/function.assert-options.php
 * @param what int
 * @param value mixed[optional]
 * @return mixed the original setting of any option or false on errors.
 */
function assert_options ($what, $value = null) {}

/**
 * Compares two "PHP-standardized" version number strings
 * @link http://php.net/manual/en/function.version-compare.php
 * @param version1 string
 * @param version2 string
 * @param operator string[optional]
 * @return mixed
 */
function version_compare ($version1, $version2, $operator = null) {}

/**
 * Convert a pathname and a project identifier to a System V IPC key
 * @link http://php.net/manual/en/function.ftok.php
 * @param pathname string
 * @param proj string
 * @return int
 */
function ftok ($pathname, $proj) {}

/**
 * Perform the rot13 transform on a string
 * @link http://php.net/manual/en/function.str-rot13.php
 * @param str string
 * @return string the ROT13 version of the given string.
 */
function str_rot13 ($str) {}

/**
 * Retrieve list of registered filters
 * @link http://php.net/manual/en/function.stream-get-filters.php
 */
function stream_get_filters () {}

/**
 * Register a stream filter implemented as a PHP class 
     derived from <literal>php_user_filter</literal>
 * @link http://php.net/manual/en/function.stream-filter-register.php
 * @param filtername
 * @param classname
 */
function stream_filter_register ($filtername, $classname) {}

/**
 * Return a bucket object from the brigade for operating on
 * @link http://php.net/manual/en/function.stream-bucket-make-writeable.php
 * @param brigade resource
 * @return object
 */
function stream_bucket_make_writeable ($brigade) {}

/**
 * Prepend bucket to brigade
 * @link http://php.net/manual/en/function.stream-bucket-prepend.php
 * @param brigade resource
 * @param bucket resource
 * @return void
 */
function stream_bucket_prepend ($brigade, $bucket) {}

/**
 * Append bucket to brigade
 * @link http://php.net/manual/en/function.stream-bucket-append.php
 * @param brigade resource
 * @param bucket resource
 * @return void
 */
function stream_bucket_append ($brigade, $bucket) {}

/**
 * Create a new bucket for use on the current stream
 * @link http://php.net/manual/en/function.stream-bucket-new.php
 * @param stream resource
 * @param buffer string
 * @return object
 */
function stream_bucket_new ($stream, $buffer) {}

/**
 * Add URL rewriter values
 * @link http://php.net/manual/en/function.output-add-rewrite-var.php
 * @param name string
 * @param value string
 * @return bool
 */
function output_add_rewrite_var ($name, $value) {}

/**
 * Reset URL rewriter values
 * @link http://php.net/manual/en/function.output-reset-rewrite-vars.php
 * @return bool
 */
function output_reset_rewrite_vars () {}

/**
 * Returns directory path used for temporary files
 * @link http://php.net/manual/en/function.sys-get-temp-dir.php
 * @return string the path of the temporary directory.
 */
function sys_get_temp_dir () {}

define ('CONNECTION_ABORTED', 1);
define ('CONNECTION_NORMAL', 0);
define ('CONNECTION_TIMEOUT', 2);
define ('INI_USER', 1);
define ('INI_PERDIR', 2);
define ('INI_SYSTEM', 4);
define ('INI_ALL', 7);
define ('PHP_URL_SCHEME', 0);
define ('PHP_URL_HOST', 1);
define ('PHP_URL_PORT', 2);
define ('PHP_URL_USER', 3);
define ('PHP_URL_PASS', 4);
define ('PHP_URL_PATH', 5);
define ('PHP_URL_QUERY', 6);
define ('PHP_URL_FRAGMENT', 7);
define ('M_E', 2.718281828459);
define ('M_LOG2E', 1.442695040889);
define ('M_LOG10E', 0.43429448190325);
define ('M_LN2', 0.69314718055995);
define ('M_LN10', 2.302585092994);
define ('M_PI', 3.1415926535898);
define ('M_PI_2', 1.5707963267949);
define ('M_PI_4', 0.78539816339745);
define ('M_1_PI', 0.31830988618379);
define ('M_2_PI', 0.63661977236758);
define ('M_SQRTPI', 1.7724538509055);
define ('M_2_SQRTPI', 1.1283791670955);
define ('M_LNPI', 1.1447298858494);
define ('M_EULER', 0.57721566490153);
define ('M_SQRT2', 1.4142135623731);
define ('M_SQRT1_2', 0.70710678118655);
define ('M_SQRT3', 1.7320508075689);
define ('INF', INF);
define ('NAN', NAN);
define ('INFO_GENERAL', 1);
define ('INFO_CREDITS', 2);
define ('INFO_CONFIGURATION', 4);
define ('INFO_MODULES', 8);
define ('INFO_ENVIRONMENT', 16);
define ('INFO_VARIABLES', 32);
define ('INFO_LICENSE', 64);
define ('INFO_ALL', 4294967295);
define ('CREDITS_GROUP', 1);
define ('CREDITS_GENERAL', 2);
define ('CREDITS_SAPI', 4);
define ('CREDITS_MODULES', 8);
define ('CREDITS_DOCS', 16);
define ('CREDITS_FULLPAGE', 32);
define ('CREDITS_QA', 64);
define ('CREDITS_ALL', 4294967295);
define ('HTML_SPECIALCHARS', 0);
define ('HTML_ENTITIES', 1);
define ('ENT_COMPAT', 2);
define ('ENT_QUOTES', 3);
define ('ENT_NOQUOTES', 0);
define ('STR_PAD_LEFT', 0);
define ('STR_PAD_RIGHT', 1);
define ('STR_PAD_BOTH', 2);
define ('PATHINFO_DIRNAME', 1);
define ('PATHINFO_BASENAME', 2);
define ('PATHINFO_EXTENSION', 4);
define ('PATHINFO_FILENAME', 8);
define ('CHAR_MAX', 127);
define ('LC_CTYPE', 0);
define ('LC_NUMERIC', 1);
define ('LC_TIME', 2);
define ('LC_COLLATE', 3);
define ('LC_MONETARY', 4);
define ('LC_ALL', 6);
define ('LC_MESSAGES', 5);
define ('SEEK_SET', 0);
define ('SEEK_CUR', 1);
define ('SEEK_END', 2);
define ('LOCK_SH', 1);
define ('LOCK_EX', 2);
define ('LOCK_UN', 3);
define ('LOCK_NB', 4);
define ('STREAM_NOTIFY_CONNECT', 2);
define ('STREAM_NOTIFY_AUTH_REQUIRED', 3);
define ('STREAM_NOTIFY_AUTH_RESULT', 10);
define ('STREAM_NOTIFY_MIME_TYPE_IS', 4);
define ('STREAM_NOTIFY_FILE_SIZE_IS', 5);
define ('STREAM_NOTIFY_REDIRECTED', 6);
define ('STREAM_NOTIFY_PROGRESS', 7);
define ('STREAM_NOTIFY_FAILURE', 9);
define ('STREAM_NOTIFY_COMPLETED', 8);
define ('STREAM_NOTIFY_RESOLVE', 1);
define ('STREAM_NOTIFY_SEVERITY_INFO', 0);
define ('STREAM_NOTIFY_SEVERITY_WARN', 1);
define ('STREAM_NOTIFY_SEVERITY_ERR', 2);
define ('STREAM_FILTER_READ', 1);
define ('STREAM_FILTER_WRITE', 2);
define ('STREAM_FILTER_ALL', 3);
define ('STREAM_CLIENT_PERSISTENT', 1);
define ('STREAM_CLIENT_ASYNC_CONNECT', 2);
define ('STREAM_CLIENT_CONNECT', 4);
define ('STREAM_CRYPTO_METHOD_SSLv2_CLIENT', 0);
define ('STREAM_CRYPTO_METHOD_SSLv3_CLIENT', 1);
define ('STREAM_CRYPTO_METHOD_SSLv23_CLIENT', 2);
define ('STREAM_CRYPTO_METHOD_TLS_CLIENT', 3);
define ('STREAM_CRYPTO_METHOD_SSLv2_SERVER', 4);
define ('STREAM_CRYPTO_METHOD_SSLv3_SERVER', 5);
define ('STREAM_CRYPTO_METHOD_SSLv23_SERVER', 6);
define ('STREAM_CRYPTO_METHOD_TLS_SERVER', 7);
define ('STREAM_SHUT_RD', 0);
define ('STREAM_SHUT_WR', 1);
define ('STREAM_SHUT_RDWR', 2);
define ('STREAM_PF_INET', 2);
define ('STREAM_PF_INET6', 10);
define ('STREAM_PF_UNIX', 1);
define ('STREAM_IPPROTO_IP', 0);
define ('STREAM_IPPROTO_TCP', 6);
define ('STREAM_IPPROTO_UDP', 17);
define ('STREAM_IPPROTO_ICMP', 1);
define ('STREAM_IPPROTO_RAW', 255);
define ('STREAM_SOCK_STREAM', 1);
define ('STREAM_SOCK_DGRAM', 2);
define ('STREAM_SOCK_RAW', 3);
define ('STREAM_SOCK_SEQPACKET', 5);
define ('STREAM_SOCK_RDM', 4);
define ('STREAM_PEEK', 2);
define ('STREAM_OOB', 1);
define ('STREAM_SERVER_BIND', 4);
define ('STREAM_SERVER_LISTEN', 8);
define ('FILE_USE_INCLUDE_PATH', 1);
define ('FILE_IGNORE_NEW_LINES', 2);
define ('FILE_SKIP_EMPTY_LINES', 4);
define ('FILE_APPEND', 8);
define ('FILE_NO_DEFAULT_CONTEXT', 16);
define ('FNM_NOESCAPE', 2);
define ('FNM_PATHNAME', 1);
define ('FNM_PERIOD', 4);
define ('FNM_CASEFOLD', 16);
define ('PSFS_PASS_ON', 2);
define ('PSFS_FEED_ME', 1);
define ('PSFS_ERR_FATAL', 0);
define ('PSFS_FLAG_NORMAL', 0);
define ('PSFS_FLAG_FLUSH_INC', 1);
define ('PSFS_FLAG_FLUSH_CLOSE', 2);
define ('ABDAY_1', 131072);
define ('ABDAY_2', 131073);
define ('ABDAY_3', 131074);
define ('ABDAY_4', 131075);
define ('ABDAY_5', 131076);
define ('ABDAY_6', 131077);
define ('ABDAY_7', 131078);
define ('DAY_1', 131079);
define ('DAY_2', 131080);
define ('DAY_3', 131081);
define ('DAY_4', 131082);
define ('DAY_5', 131083);
define ('DAY_6', 131084);
define ('DAY_7', 131085);
define ('ABMON_1', 131086);
define ('ABMON_2', 131087);
define ('ABMON_3', 131088);
define ('ABMON_4', 131089);
define ('ABMON_5', 131090);
define ('ABMON_6', 131091);
define ('ABMON_7', 131092);
define ('ABMON_8', 131093);
define ('ABMON_9', 131094);
define ('ABMON_10', 131095);
define ('ABMON_11', 131096);
define ('ABMON_12', 131097);
define ('MON_1', 131098);
define ('MON_2', 131099);
define ('MON_3', 131100);
define ('MON_4', 131101);
define ('MON_5', 131102);
define ('MON_6', 131103);
define ('MON_7', 131104);
define ('MON_8', 131105);
define ('MON_9', 131106);
define ('MON_10', 131107);
define ('MON_11', 131108);
define ('MON_12', 131109);
define ('AM_STR', 131110);
define ('PM_STR', 131111);
define ('D_T_FMT', 131112);
define ('D_FMT', 131113);
define ('T_FMT', 131114);
define ('T_FMT_AMPM', 131115);
define ('ERA', 131116);
define ('ERA_D_T_FMT', 131120);
define ('ERA_D_FMT', 131118);
define ('ERA_T_FMT', 131121);
define ('ALT_DIGITS', 131119);
define ('CRNCYSTR', 262159);
define ('RADIXCHAR', 65536);
define ('THOUSEP', 65537);
define ('YESEXPR', 327680);
define ('NOEXPR', 327681);
define ('CODESET', 14);
define ('CRYPT_SALT_LENGTH', 60);
define ('CRYPT_STD_DES', 1);
define ('CRYPT_EXT_DES', 0);
define ('CRYPT_MD5', 1);
define ('CRYPT_BLOWFISH', 1);
define ('DIRECTORY_SEPARATOR', '/');
define ('PATH_SEPARATOR', ':');
define ('GLOB_BRACE', 1024);
define ('GLOB_MARK', 2);
define ('GLOB_NOSORT', 4);
define ('GLOB_NOCHECK', 16);
define ('GLOB_NOESCAPE', 64);
define ('GLOB_ERR', 1);
define ('GLOB_ONLYDIR', 8192);
define ('GLOB_AVAILABLE_FLAGS', 9303);
define ('LOG_EMERG', 0);
define ('LOG_ALERT', 1);
define ('LOG_CRIT', 2);
define ('LOG_ERR', 3);
define ('LOG_WARNING', 4);
define ('LOG_NOTICE', 5);
define ('LOG_INFO', 6);
define ('LOG_DEBUG', 7);
define ('LOG_KERN', 0);
define ('LOG_USER', 8);
define ('LOG_MAIL', 16);
define ('LOG_DAEMON', 24);
define ('LOG_AUTH', 32);
define ('LOG_SYSLOG', 40);
define ('LOG_LPR', 48);
define ('LOG_NEWS', 56);
define ('LOG_UUCP', 64);
define ('LOG_CRON', 72);
define ('LOG_AUTHPRIV', 80);
define ('LOG_LOCAL0', 128);
define ('LOG_LOCAL1', 136);
define ('LOG_LOCAL2', 144);
define ('LOG_LOCAL3', 152);
define ('LOG_LOCAL4', 160);
define ('LOG_LOCAL5', 168);
define ('LOG_LOCAL6', 176);
define ('LOG_LOCAL7', 184);
define ('LOG_PID', 1);
define ('LOG_CONS', 2);
define ('LOG_ODELAY', 4);
define ('LOG_NDELAY', 8);
define ('LOG_NOWAIT', 16);
define ('LOG_PERROR', 32);
define ('EXTR_OVERWRITE', 0);
define ('EXTR_SKIP', 1);
define ('EXTR_PREFIX_SAME', 2);
define ('EXTR_PREFIX_ALL', 3);
define ('EXTR_PREFIX_INVALID', 4);
define ('EXTR_PREFIX_IF_EXISTS', 5);
define ('EXTR_IF_EXISTS', 6);
define ('EXTR_REFS', 256);
define ('SORT_ASC', 4);
define ('SORT_DESC', 3);
define ('SORT_REGULAR', 0);
define ('SORT_NUMERIC', 1);
define ('SORT_STRING', 2);
define ('SORT_LOCALE_STRING', 5);
define ('CASE_LOWER', 0);
define ('CASE_UPPER', 1);
define ('COUNT_NORMAL', 0);
define ('COUNT_RECURSIVE', 1);
define ('ASSERT_ACTIVE', 1);
define ('ASSERT_CALLBACK', 2);
define ('ASSERT_BAIL', 3);
define ('ASSERT_WARNING', 4);
define ('ASSERT_QUIET_EVAL', 5);
define ('STREAM_USE_PATH', 1);
define ('STREAM_IGNORE_URL', 2);
define ('STREAM_ENFORCE_SAFE_MODE', 4);
define ('STREAM_REPORT_ERRORS', 8);
define ('STREAM_MUST_SEEK', 16);
define ('STREAM_URL_STAT_LINK', 1);
define ('STREAM_URL_STAT_QUIET', 2);
define ('STREAM_MKDIR_RECURSIVE', 1);
define ('STREAM_IS_URL', 1);
define ('IMAGETYPE_GIF', 1);
define ('IMAGETYPE_JPEG', 2);
define ('IMAGETYPE_PNG', 3);
define ('IMAGETYPE_SWF', 4);
define ('IMAGETYPE_PSD', 5);
define ('IMAGETYPE_BMP', 6);
define ('IMAGETYPE_TIFF_II', 7);
define ('IMAGETYPE_TIFF_MM', 8);
define ('IMAGETYPE_JPC', 9);
define ('IMAGETYPE_JP2', 10);
define ('IMAGETYPE_JPX', 11);
define ('IMAGETYPE_JB2', 12);
define ('IMAGETYPE_SWC', 13);
define ('IMAGETYPE_IFF', 14);
define ('IMAGETYPE_WBMP', 15);
define ('IMAGETYPE_JPEG2000', 9);
define ('IMAGETYPE_XBM', 16);
define ('DNS_A', 1);
define ('DNS_NS', 2);
define ('DNS_CNAME', 16);
define ('DNS_SOA', 32);
define ('DNS_PTR', 2048);
define ('DNS_HINFO', 4096);
define ('DNS_MX', 16384);
define ('DNS_TXT', 32768);
define ('DNS_SRV', 33554432);
define ('DNS_NAPTR', 67108864);
define ('DNS_AAAA', 134217728);
define ('DNS_A6', 16777216);
define ('DNS_ANY', 268435456);
define ('DNS_ALL', 251713587);

// End of standard v.5.2.4

// Start of Reflection v.0.1

class ReflectionException extends Exception  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class Reflection  {

	/**
	 * @param modifiers
	 */
	public static function getModifierNames ($modifiers) {}

	/**
	 * @param reflector Reflector
	 * @param return[optional]
	 */
	public static function export (Reflector $reflector, $return) {}

}

interface Reflector  {

	abstract public static function export () {}

	abstract public function __toString () {}

}

abstract class ReflectionFunctionAbstract implements Reflector {
	abstract public $name;


	final private function __clone () {}

	abstract public function __toString () {}

	public function isInternal () {}

	public function isUserDefined () {}

	public function getName () {}

	public function getFileName () {}

	public function getStartLine () {}

	public function getEndLine () {}

	public function getDocComment () {}

	public function getStaticVariables () {}

	public function returnsReference () {}

	public function getParameters () {}

	public function getNumberOfParameters () {}

	public function getNumberOfRequiredParameters () {}

	public function getExtension () {}

	public function getExtensionName () {}

	public function isDeprecated () {}

}

class ReflectionFunction extends ReflectionFunctionAbstract implements Reflector {
	const IS_DEPRECATED = 262144;

	public $name;


	/**
	 * @param name
	 */
	public function __construct ($name) {}

	public function __toString () {}

	/**
	 * @param name
	 * @param return[optional]
	 */
	public static function export ($name, $return) {}

	public function isDisabled () {}

	/**
	 * @param args
	 */
	public function invoke ($args) {}

	/**
	 * @param args
	 */
	public function invokeArgs (array $args) {}

	final private function __clone () {}

	public function isInternal () {}

	public function isUserDefined () {}

	public function getName () {}

	public function getFileName () {}

	public function getStartLine () {}

	public function getEndLine () {}

	public function getDocComment () {}

	public function getStaticVariables () {}

	public function returnsReference () {}

	public function getParameters () {}

	public function getNumberOfParameters () {}

	public function getNumberOfRequiredParameters () {}

	public function getExtension () {}

	public function getExtensionName () {}

	public function isDeprecated () {}

}

class ReflectionParameter implements Reflector {
	public $name;


	final private function __clone () {}

	/**
	 * @param function
	 * @param parameter
	 * @param return[optional]
	 */
	public static function export ($function, $parameter, $return) {}

	/**
	 * @param function
	 * @param parameter
	 */
	public function __construct ($function, $parameter) {}

	public function __toString () {}

	public function getName () {}

	public function isPassedByReference () {}

	public function getDeclaringFunction () {}

	public function getDeclaringClass () {}

	public function getClass () {}

	public function isArray () {}

	public function allowsNull () {}

	public function getPosition () {}

	public function isOptional () {}

	public function isDefaultValueAvailable () {}

	public function getDefaultValue () {}

}

class ReflectionMethod extends ReflectionFunctionAbstract implements Reflector {
	const IS_STATIC = 1;
	const IS_PUBLIC = 256;
	const IS_PROTECTED = 512;
	const IS_PRIVATE = 1024;
	const IS_ABSTRACT = 2;
	const IS_FINAL = 4;

	public $name;
	public $class;


	/**
	 * @param class
	 * @param name
	 * @param return[optional]
	 */
	public static function export ($class, $name, $return) {}

	/**
	 * @param class_or_method
	 * @param name[optional]
	 */
	public function __construct ($class_or_method, $name) {}

	public function __toString () {}

	public function isPublic () {}

	public function isPrivate () {}

	public function isProtected () {}

	public function isAbstract () {}

	public function isFinal () {}

	public function isStatic () {}

	public function isConstructor () {}

	public function isDestructor () {}

	public function getModifiers () {}

	/**
	 * @param object
	 * @param args
	 */
	public function invoke ($object, $args) {}

	/**
	 * @param object
	 * @param args
	 */
	public function invokeArgs ($objectarray , $args) {}

	public function getDeclaringClass () {}

	public function getPrototype () {}

	final private function __clone () {}

	public function isInternal () {}

	public function isUserDefined () {}

	public function getName () {}

	public function getFileName () {}

	public function getStartLine () {}

	public function getEndLine () {}

	public function getDocComment () {}

	public function getStaticVariables () {}

	public function returnsReference () {}

	public function getParameters () {}

	public function getNumberOfParameters () {}

	public function getNumberOfRequiredParameters () {}

	public function getExtension () {}

	public function getExtensionName () {}

	public function isDeprecated () {}

}

class ReflectionClass implements Reflector {
	const IS_IMPLICIT_ABSTRACT = 16;
	const IS_EXPLICIT_ABSTRACT = 32;
	const IS_FINAL = 64;

	public $name;


	final private function __clone () {}

	/**
	 * @param argument
	 * @param return[optional]
	 */
	public static function export ($argument, $return) {}

	/**
	 * @param argument
	 */
	public function __construct ($argument) {}

	public function __toString () {}

	public function getName () {}

	public function isInternal () {}

	public function isUserDefined () {}

	public function isInstantiable () {}

	public function getFileName () {}

	public function getStartLine () {}

	public function getEndLine () {}

	public function getDocComment () {}

	public function getConstructor () {}

	/**
	 * @param name
	 */
	public function hasMethod ($name) {}

	/**
	 * @param name
	 */
	public function getMethod ($name) {}

	/**
	 * @param filter[optional]
	 */
	public function getMethods ($filter) {}

	/**
	 * @param name
	 */
	public function hasProperty ($name) {}

	/**
	 * @param name
	 */
	public function getProperty ($name) {}

	/**
	 * @param filter[optional]
	 */
	public function getProperties ($filter) {}

	/**
	 * @param name
	 */
	public function hasConstant ($name) {}

	public function getConstants () {}

	/**
	 * @param name
	 */
	public function getConstant ($name) {}

	public function getInterfaces () {}

	public function getInterfaceNames () {}

	public function isInterface () {}

	public function isAbstract () {}

	public function isFinal () {}

	public function getModifiers () {}

	/**
	 * @param object
	 */
	public function isInstance ($object) {}

	/**
	 * @param args
	 */
	public function newInstance ($args) {}

	/**
	 * @param args[optional]
	 */
	public function newInstanceArgs (array $args) {}

	public function getParentClass () {}

	/**
	 * @param class
	 */
	public function isSubclassOf ($class) {}

	public function getStaticProperties () {}

	/**
	 * @param name
	 * @param default[optional]
	 */
	public function getStaticPropertyValue ($name, $default) {}

	/**
	 * @param name
	 * @param value
	 */
	public function setStaticPropertyValue ($name, $value) {}

	public function getDefaultProperties () {}

	public function isIterateable () {}

	/**
	 * @param interface
	 */
	public function implementsInterface ($interface) {}

	public function getExtension () {}

	public function getExtensionName () {}

}

class ReflectionObject extends ReflectionClass implements Reflector {
	const IS_IMPLICIT_ABSTRACT = 16;
	const IS_EXPLICIT_ABSTRACT = 32;
	const IS_FINAL = 64;

	public $name;


	/**
	 * @param argument
	 * @param return[optional]
	 */
	public static function export ($argument, $return) {}

	/**
	 * @param argument
	 */
	public function __construct ($argument) {}

	final private function __clone () {}

	public function __toString () {}

	public function getName () {}

	public function isInternal () {}

	public function isUserDefined () {}

	public function isInstantiable () {}

	public function getFileName () {}

	public function getStartLine () {}

	public function getEndLine () {}

	public function getDocComment () {}

	public function getConstructor () {}

	/**
	 * @param name
	 */
	public function hasMethod ($name) {}

	/**
	 * @param name
	 */
	public function getMethod ($name) {}

	/**
	 * @param filter[optional]
	 */
	public function getMethods ($filter) {}

	/**
	 * @param name
	 */
	public function hasProperty ($name) {}

	/**
	 * @param name
	 */
	public function getProperty ($name) {}

	/**
	 * @param filter[optional]
	 */
	public function getProperties ($filter) {}

	/**
	 * @param name
	 */
	public function hasConstant ($name) {}

	public function getConstants () {}

	/**
	 * @param name
	 */
	public function getConstant ($name) {}

	public function getInterfaces () {}

	public function getInterfaceNames () {}

	public function isInterface () {}

	public function isAbstract () {}

	public function isFinal () {}

	public function getModifiers () {}

	/**
	 * @param object
	 */
	public function isInstance ($object) {}

	/**
	 * @param args
	 */
	public function newInstance ($args) {}

	/**
	 * @param args[optional]
	 */
	public function newInstanceArgs (array $args) {}

	public function getParentClass () {}

	/**
	 * @param class
	 */
	public function isSubclassOf ($class) {}

	public function getStaticProperties () {}

	/**
	 * @param name
	 * @param default[optional]
	 */
	public function getStaticPropertyValue ($name, $default) {}

	/**
	 * @param name
	 * @param value
	 */
	public function setStaticPropertyValue ($name, $value) {}

	public function getDefaultProperties () {}

	public function isIterateable () {}

	/**
	 * @param interface
	 */
	public function implementsInterface ($interface) {}

	public function getExtension () {}

	public function getExtensionName () {}

}

class ReflectionProperty implements Reflector {
	const IS_STATIC = 1;
	const IS_PUBLIC = 256;
	const IS_PROTECTED = 512;
	const IS_PRIVATE = 1024;

	public $name;
	public $class;


	final private function __clone () {}

	/**
	 * @param argument
	 * @param return[optional]
	 */
	public static function export ($argument, $return) {}

	/**
	 * @param argument
	 */
	public function __construct ($argument) {}

	public function __toString () {}

	public function getName () {}

	/**
	 * @param object[optional]
	 */
	public function getValue ($object) {}

	/**
	 * @param object
	 * @param value
	 */
	public function setValue ($object, $value) {}

	public function isPublic () {}

	public function isPrivate () {}

	public function isProtected () {}

	public function isStatic () {}

	public function isDefault () {}

	public function getModifiers () {}

	public function getDeclaringClass () {}

	public function getDocComment () {}

}

class ReflectionExtension implements Reflector {
	public $name;


	final private function __clone () {}

	/**
	 * @param name
	 * @param return[optional]
	 */
	public static function export ($name, $return) {}

	/**
	 * @param name
	 */
	public function __construct ($name) {}

	public function __toString () {}

	public function getName () {}

	public function getVersion () {}

	public function getFunctions () {}

	public function getConstants () {}

	public function getINIEntries () {}

	public function getClasses () {}

	public function getClassNames () {}

	public function getDependencies () {}

	public function info () {}

}
// End of Reflection v.0.1

// Start of json v.1.2.1

/**
 * Returns the JSON representation of a value
 * @link http://php.net/manual/en/function.json-encode.php
 * @param value mixed
 * @return string a JSON encoded string on success.
 */
function json_encode ($value) {}

/**
 * Decodes a JSON string
 * @link http://php.net/manual/en/function.json-decode.php
 * @param json string
 * @param assoc bool[optional]
 * @return mixed an object or if the optional
 */
function json_decode ($json, $assoc = null) {}

// End of json v.1.2.1

// Start of hash v.1.0

/**
 * Generate a hash value (message digest)
 * @link http://php.net/manual/en/function.hash.php
 * @param algo string
 * @param data string
 * @param raw_output bool[optional]
 * @return string a string containing the calculated message digest as lowercase hexits
 */
function hash ($algo, $data, $raw_output = null) {}

/**
 * Generate a hash value using the contents of a given file
 * @link http://php.net/manual/en/function.hash-file.php
 * @param algo string
 * @param filename string
 * @param raw_output bool[optional]
 * @return string a string containing the calculated message digest as lowercase hexits
 */
function hash_file ($algo, $filename, $raw_output = null) {}

/**
 * Generate a keyed hash value using the HMAC method
 * @link http://php.net/manual/en/function.hash-hmac.php
 * @param algo string
 * @param data string
 * @param key string
 * @param raw_output bool[optional]
 * @return string a string containing the calculated message digest as lowercase hexits
 */
function hash_hmac ($algo, $data, $key, $raw_output = null) {}

/**
 * Generate a keyed hash value using the HMAC method and the contents of a given file
 * @link http://php.net/manual/en/function.hash-hmac-file.php
 * @param algo string
 * @param filename string
 * @param key string
 * @param raw_output bool[optional]
 * @return string a string containing the calculated message digest as lowercase hexits
 */
function hash_hmac_file ($algo, $filename, $key, $raw_output = null) {}

/**
 * Initialize an incremental hashing context
 * @link http://php.net/manual/en/function.hash-init.php
 * @param algo string
 * @param options int[optional]
 * @param key string
 * @return resource a Hashing Context resource for use with hash_update,
 */
function hash_init ($algo, $options = null, $key) {}

/**
 * Pump data into an active hashing context
 * @link http://php.net/manual/en/function.hash-update.php
 * @param context resource
 * @param data string
 * @return bool true.
 */
function hash_update ($context, $data) {}

/**
 * Pump data into an active hashing context from an open stream
 * @link http://php.net/manual/en/function.hash-update-stream.php
 * @param context resource
 * @param handle resource
 * @param length int[optional]
 * @return int
 */
function hash_update_stream ($context, $handle, $length = null) {}

/**
 * Pump data into an active hashing context from a file
 * @link http://php.net/manual/en/function.hash-update-file.php
 * @param context resource
 * @param filename string
 * @param context resource[optional]
 * @return bool
 */
function hash_update_file ($context, $filename, $context = null) {}

/**
 * Finalize an incremental hash and return resulting digest
 * @link http://php.net/manual/en/function.hash-final.php
 * @param context resource
 * @param raw_output bool[optional]
 * @return string a string containing the calculated message digest as lowercase hexits
 */
function hash_final ($context, $raw_output = null) {}

/**
 * Return a list of registered hashing algorithms
 * @link http://php.net/manual/en/function.hash-algos.php
 * @return array a numerically indexed array containing the list of supported
 */
function hash_algos () {}

define ('HASH_HMAC', 1);

// End of hash v.1.0

// Start of filter v.0.11.0

/**
 * Gets variable from outside PHP and optionally filters it
 * @link http://php.net/manual/en/function.filter-input.php
 * @param type int
 * @param variable_name string
 * @param filter int[optional]
 * @param options mixed[optional]
 * @return mixed
 */
function filter_input ($type, $variable_name, $filter = null, $options = null) {}

/**
 * Filters a variable with a specified filter
 * @link http://php.net/manual/en/function.filter-var.php
 * @param variable mixed
 * @param filter int[optional]
 * @param options mixed[optional]
 * @return mixed the filtered data, or false if the filter fails.
 */
function filter_var ($variable, $filter = null, $options = null) {}

/**
 * Gets multiple variables from outside PHP and optionally filters them
 * @link http://php.net/manual/en/function.filter-input-array.php
 * @param type int
 * @param definition mixed[optional]
 * @return mixed
 */
function filter_input_array ($type, $definition = null) {}

/**
 * Gets multiple variables and optionally filters them
 * @link http://php.net/manual/en/function.filter-var-array.php
 * @param data array
 * @param definition mixed[optional]
 * @return mixed
 */
function filter_var_array (array $data, $definition = null) {}

/**
 * Returns a list of all supported filters
 * @link http://php.net/manual/en/function.filter-list.php
 * @return array an array of names of all supported filters, empty array if there
 */
function filter_list () {}

/**
 * Checks if variable of specified type exists
 * @link http://php.net/manual/en/function.filter-has-var.php
 * @param type int
 * @param variable_name string
 * @return bool
 */
function filter_has_var ($type, $variable_name) {}

/**
 * Returns the filter ID belonging to a named filter
 * @link http://php.net/manual/en/function.filter-id.php
 * @param filtername string
 * @return int
 */
function filter_id ($filtername) {}

define ('INPUT_POST', 0);
define ('INPUT_GET', 1);
define ('INPUT_COOKIE', 2);
define ('INPUT_ENV', 4);
define ('INPUT_SERVER', 5);
define ('INPUT_SESSION', 6);
define ('INPUT_REQUEST', 99);
define ('FILTER_FLAG_NONE', 0);
define ('FILTER_REQUIRE_SCALAR', 33554432);
define ('FILTER_REQUIRE_ARRAY', 16777216);
define ('FILTER_FORCE_ARRAY', 67108864);
define ('FILTER_NULL_ON_FAILURE', 134217728);
define ('FILTER_VALIDATE_INT', 257);
define ('FILTER_VALIDATE_BOOLEAN', 258);
define ('FILTER_VALIDATE_FLOAT', 259);
define ('FILTER_VALIDATE_REGEXP', 272);
define ('FILTER_VALIDATE_URL', 273);
define ('FILTER_VALIDATE_EMAIL', 274);
define ('FILTER_VALIDATE_IP', 275);
define ('FILTER_DEFAULT', 516);
define ('FILTER_UNSAFE_RAW', 516);
define ('FILTER_SANITIZE_STRING', 513);
define ('FILTER_SANITIZE_STRIPPED', 513);
define ('FILTER_SANITIZE_ENCODED', 514);
define ('FILTER_SANITIZE_SPECIAL_CHARS', 515);
define ('FILTER_SANITIZE_EMAIL', 517);
define ('FILTER_SANITIZE_URL', 518);
define ('FILTER_SANITIZE_NUMBER_INT', 519);
define ('FILTER_SANITIZE_NUMBER_FLOAT', 520);
define ('FILTER_SANITIZE_MAGIC_QUOTES', 521);
define ('FILTER_CALLBACK', 1024);
define ('FILTER_FLAG_ALLOW_OCTAL', 1);
define ('FILTER_FLAG_ALLOW_HEX', 2);
define ('FILTER_FLAG_STRIP_LOW', 4);
define ('FILTER_FLAG_STRIP_HIGH', 8);
define ('FILTER_FLAG_ENCODE_LOW', 16);
define ('FILTER_FLAG_ENCODE_HIGH', 32);
define ('FILTER_FLAG_ENCODE_AMP', 64);
define ('FILTER_FLAG_NO_ENCODE_QUOTES', 128);
define ('FILTER_FLAG_EMPTY_STRING_NULL', 256);
define ('FILTER_FLAG_ALLOW_FRACTION', 4096);
define ('FILTER_FLAG_ALLOW_THOUSAND', 8192);
define ('FILTER_FLAG_ALLOW_SCIENTIFIC', 16384);
define ('FILTER_FLAG_SCHEME_REQUIRED', 65536);
define ('FILTER_FLAG_HOST_REQUIRED', 131072);
define ('FILTER_FLAG_PATH_REQUIRED', 262144);
define ('FILTER_FLAG_QUERY_REQUIRED', 524288);
define ('FILTER_FLAG_IPV4', 1048576);
define ('FILTER_FLAG_IPV6', 2097152);
define ('FILTER_FLAG_NO_RES_RANGE', 4194304);
define ('FILTER_FLAG_NO_PRIV_RANGE', 8388608);

// End of filter v.0.11.0

// Start of dom v.20031129

/**
 * DOM operations raise exceptions under particular circumstances, i.e.,
 *       when an operation is impossible to perform for logical reasons.
 * @link http://php.net/manual/en/ref.dom.php
 */
final class DOMException extends Exception  {
	protected $message;
	public $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class DOMStringList  {

	public function item () {}

}

/**
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMNameList  {

	public function getName () {}

	public function getNamespaceURI () {}

}

class DOMImplementationList  {

	public function item () {}

}

class DOMImplementationSource  {

	public function getDomimplementation () {}

	public function getDomimplementations () {}

}

/**
 * The DOMImplementation interface provides a number
 *       of methods for performing operations that are independent of any 
 *       particular instance of the document object model.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMImplementation  {

	public function getFeature () {}

	/**
	 * Test if the DOM implementation implements a specific feature
	 * @link http://php.net/manual/en/function.dom-domimplementation-hasfeature.php
	 * @param feature string
	 * @param version string
	 * @return bool
	 */
	public function hasFeature ($feature, $version) {}

	/**
	 * Creates an empty DOMDocumentType object
	 * @link http://php.net/manual/en/function.dom-domimplementation-createdocumenttype.php
	 * @param qualifiedName string[optional]
	 * @param publicId string[optional]
	 * @param systemId string[optional]
	 * @return DOMDocumentType
	 */
	public function createDocumentType ($qualifiedName = null, $publicId = null, $systemId = null) {}

	/**
	 * Creates a DOMDocument object of the specified type with its document element
	 * @link http://php.net/manual/en/function.dom-domimplementation-createdocument.php
	 * @param namespaceURI string[optional]
	 * @param qualifiedName string[optional]
	 * @param doctype DOMDocumentType[optional]
	 * @return DOMDocument
	 */
	public function createDocument ($namespaceURI = null, $qualifiedName = null, DOMDocumentType $doctype = null) {}

}

/**
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMNode  {

	/**
	 * Adds a new child before a reference node
	 * @link http://php.net/manual/en/function.dom-domnode-insertbefore.php
	 * @param newnode DOMNode
	 * @param refnode DOMNode[optional]
	 * @return DOMNode
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://php.net/manual/en/function.dom-domnode-replacechild.php
	 * @param newnode DOMNode
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://php.net/manual/en/function.dom-domnode-removechild.php
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://php.net/manual/en/function.dom-domnode-appendchild.php
	 * @param newnode DOMNode
	 * @return DOMNode
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://php.net/manual/en/function.dom-domnode-haschildnodes.php
	 * @return bool
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://php.net/manual/en/function.dom-domnode-clonenode.php
	 * @param deep bool[optional]
	 * @return DOMNode
	 */
	public function cloneNode ($deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://php.net/manual/en/function.dom-domnode-normalize.php
	 * @return void
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://php.net/manual/en/function.dom-domnode-issupported.php
	 * @param feature string
	 * @param version string
	 * @return bool
	 */
	public function isSupported ($feature, $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://php.net/manual/en/function.dom-domnode-hasattributes.php
	 * @return bool
	 */
	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://php.net/manual/en/function.dom-domnode-issamenode.php
	 * @param node DOMNode
	 * @return bool
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://php.net/manual/en/function.dom-domnode-lookupprefix.php
	 * @param namespaceURI string
	 * @return string
	 */
	public function lookupPrefix ($namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://php.net/manual/en/function.dom-domnode-isdefaultnamespace.php
	 * @param namespaceURI string
	 * @return bool
	 */
	public function isDefaultNamespace ($namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://php.net/manual/en/function.dom-domnode-lookupnamespaceuri.php
	 * @param prefix string
	 * @return string
	 */
	public function lookupNamespaceUri ($prefix) {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

class DOMNameSpaceNode  {
}

/**
 * Extends DOMNode.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMDocumentFragment extends DOMNode  {

	public function __construct () {}

	/**
	 * Append raw XML data
	 * @link http://php.net/manual/en/function.dom-domdocumentfragment-appendxml.php
	 * @param data string
	 * @return bool
	 */
	public function appendXML ($data) {}

	/**
	 * Adds a new child before a reference node
	 * @link http://php.net/manual/en/function.dom-domnode-insertbefore.php
	 * @param newnode DOMNode
	 * @param refnode DOMNode[optional]
	 * @return DOMNode
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://php.net/manual/en/function.dom-domnode-replacechild.php
	 * @param newnode DOMNode
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://php.net/manual/en/function.dom-domnode-removechild.php
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://php.net/manual/en/function.dom-domnode-appendchild.php
	 * @param newnode DOMNode
	 * @return DOMNode
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://php.net/manual/en/function.dom-domnode-haschildnodes.php
	 * @return bool
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://php.net/manual/en/function.dom-domnode-clonenode.php
	 * @param deep bool[optional]
	 * @return DOMNode
	 */
	public function cloneNode ($deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://php.net/manual/en/function.dom-domnode-normalize.php
	 * @return void
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://php.net/manual/en/function.dom-domnode-issupported.php
	 * @param feature string
	 * @param version string
	 * @return bool
	 */
	public function isSupported ($feature, $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://php.net/manual/en/function.dom-domnode-hasattributes.php
	 * @return bool
	 */
	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://php.net/manual/en/function.dom-domnode-issamenode.php
	 * @param node DOMNode
	 * @return bool
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://php.net/manual/en/function.dom-domnode-lookupprefix.php
	 * @param namespaceURI string
	 * @return string
	 */
	public function lookupPrefix ($namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://php.net/manual/en/function.dom-domnode-isdefaultnamespace.php
	 * @param namespaceURI string
	 * @return bool
	 */
	public function isDefaultNamespace ($namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://php.net/manual/en/function.dom-domnode-lookupnamespaceuri.php
	 * @param prefix string
	 * @return string
	 */
	public function lookupNamespaceUri ($prefix) {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * Extends DOMNode.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMDocument extends DOMNode  {

	/**
	 * Create new element node
	 * @link http://php.net/manual/en/function.dom-domdocument-createelement.php
	 * @param name string
	 * @param value string[optional]
	 * @return DOMElement a new instance of class DOMElement or false
	 */
	public function createElement ($name, $value = null) {}

	/**
	 * Create new document fragment
	 * @link http://php.net/manual/en/function.dom-domdocument-createdocumentfragment.php
	 * @return DOMDocumentFragment
	 */
	public function createDocumentFragment () {}

	/**
	 * Create new text node
	 * @link http://php.net/manual/en/function.dom-domdocument-createtextnode.php
	 * @param content string
	 * @return DOMText
	 */
	public function createTextNode ($content) {}

	/**
	 * Create new comment node
	 * @link http://php.net/manual/en/function.dom-domdocument-createcomment.php
	 * @param data string
	 * @return DOMComment
	 */
	public function createComment ($data) {}

	/**
	 * Create new cdata node
	 * @link http://php.net/manual/en/function.dom-domdocument-createcdatasection.php
	 * @param data string
	 * @return DOMCDATASection
	 */
	public function createCDATASection ($data) {}

	/**
	 * Creates new PI node
	 * @link http://php.net/manual/en/function.dom-domdocument-createprocessinginstruction.php
	 * @param target string
	 * @param data string[optional]
	 * @return DOMProcessingInstruction
	 */
	public function createProcessingInstruction ($target, $data = null) {}

	/**
	 * Create new attribute
	 * @link http://php.net/manual/en/function.dom-domdocument-createattribute.php
	 * @param name string
	 * @return DOMAttr
	 */
	public function createAttribute ($name) {}

	/**
	 * Create new entity reference node
	 * @link http://php.net/manual/en/function.dom-domdocument-createentityreference.php
	 * @param name string
	 * @return DOMEntityReference
	 */
	public function createEntityReference ($name) {}

	/**
	 * Searches for all elements with given tag name
	 * @link http://php.net/manual/en/function.dom-domdocument-getelementsbytagname.php
	 * @param name string
	 * @return DOMNodeList
	 */
	public function getElementsByTagName ($name) {}

	/**
	 * Import node into current document
	 * @link http://php.net/manual/en/function.dom-domdocument-importnode.php
	 * @param importedNode DOMNode
	 * @param deep bool[optional]
	 * @return DOMNode
	 */
	public function importNode (DOMNode $importedNode, $deep = null) {}

	/**
	 * Create new element node with an associated namespace
	 * @link http://php.net/manual/en/function.dom-domdocument-createelementns.php
	 * @param namespaceURI string
	 * @param qualifiedName string
	 * @param value string[optional]
	 * @return DOMElement
	 */
	public function createElementNS ($namespaceURI, $qualifiedName, $value = null) {}

	/**
	 * Create new attribute node with an associated namespace
	 * @link http://php.net/manual/en/function.dom-domdocument-createattributens.php
	 * @param namespaceURI string
	 * @param qualifiedName string
	 * @return DOMAttr
	 */
	public function createAttributeNS ($namespaceURI, $qualifiedName) {}

	/**
	 * Searches for all elements with given tag name in specified namespace
	 * @link http://php.net/manual/en/function.dom-domdocument-getelementsbytagnamens.php
	 * @param namespaceURI string
	 * @param localName string
	 * @return DOMNodeList
	 */
	public function getElementsByTagNameNS ($namespaceURI, $localName) {}

	/**
	 * Searches for an element with a certain id
	 * @link http://php.net/manual/en/function.dom-domdocument-getelementbyid.php
	 * @param elementId string
	 * @return DOMElement the DOMElement or &null; if the element is
	 */
	public function getElementById ($elementId) {}

	public function adoptNode () {}

	/**
	 * Normalizes the document
	 * @link http://php.net/manual/en/function.dom-domdocument-normalizedocument.php
	 * @return void
	 */
	public function normalizeDocument () {}

	public function renameNode () {}

	/**
	 * Load XML from a file
	 * @link http://php.net/manual/en/function.dom-domdocument-load.php
	 * @param filename string
	 * @param options int[optional]
	 * @return mixed
	 */
	public function load ($filename, $options = null) {}

	/**
	 * Dumps the internal XML tree back into a file
	 * @link http://php.net/manual/en/function.dom-domdocument-save.php
	 * @param filename string
	 * @param options int[optional]
	 * @return mixed the number of bytes written or false if an error occurred.
	 */
	public function save ($filename, $options = null) {}

	/**
	 * Load XML from a string
	 * @link http://php.net/manual/en/function.dom-domdocument-loadxml.php
	 * @param source string
	 * @param options int[optional]
	 * @return mixed
	 */
	public function loadXML ($source, $options = null) {}

	/**
	 * Dumps the internal XML tree back into a string
	 * @link http://php.net/manual/en/function.dom-domdocument-savexml.php
	 * @param node DOMNode[optional]
	 * @param options int[optional]
	 * @return string the XML, or false if an error occurred.
	 */
	public function saveXML (DOMNode $node = null, $options = null) {}

	/**
	 * Creates a new DOMDocument object
	 * @link http://php.net/manual/en/function.dom-domdocument-construct.php
	 */
	public function __construct () {}

	/**
	 * Validates the document based on its DTD
	 * @link http://php.net/manual/en/function.dom-domdocument-validate.php
	 * @return bool
	 */
	public function validate () {}

	/**
	 * Substitutes XIncludes in a DomDocument Object
	 * @link http://php.net/manual/en/function.domdocument-xinclude.php
	 * @param options int[optional]
	 * @return int the number of XIncludes in the document.
	 */
	public function xinclude ($options = null) {}

	/**
	 * Load HTML from a string
	 * @link http://php.net/manual/en/function.dom-domdocument-loadhtml.php
	 * @param source string
	 * @return bool
	 */
	public function loadHTML ($source) {}

	/**
	 * Load HTML from a file
	 * @link http://php.net/manual/en/function.dom-domdocument-loadhtmlfile.php
	 * @param filename string
	 * @return bool
	 */
	public function loadHTMLFile ($filename) {}

	/**
	 * Dumps the internal document into a string using HTML formatting
	 * @link http://php.net/manual/en/function.dom-domdocument-savehtml.php
	 * @return string the HTML, or false if an error occurred.
	 */
	public function saveHTML () {}

	/**
	 * Dumps the internal document into a file using HTML formatting
	 * @link http://php.net/manual/en/function.dom-domdocument-savehtmlfile.php
	 * @param filename string
	 * @return int the number of bytes written or false if an error occurred.
	 */
	public function saveHTMLFile ($filename) {}

	/**
	 * Validates a document based on a schema
	 * @link http://php.net/manual/en/function.dom-domdocument-schemavalidate.php
	 * @param filename string
	 * @return bool
	 */
	public function schemaValidate ($filename) {}

	/**
	 * Validates a document based on a schema
	 * @link http://php.net/manual/en/function.dom-domdocument-schemavalidatesource.php
	 * @param source string
	 * @return bool
	 */
	public function schemaValidateSource ($source) {}

	/**
	 * Performs relaxNG validation on the document
	 * @link http://php.net/manual/en/function.dom-domdocument-relaxngvalidate.php
	 * @param filename string
	 * @return bool
	 */
	public function relaxNGValidate ($filename) {}

	/**
	 * Performs relaxNG validation on the document
	 * @link http://php.net/manual/en/function.dom-domdocument-relaxngvalidatesource.php
	 * @param source string
	 * @return bool
	 */
	public function relaxNGValidateSource ($source) {}

	/**
	 * Register extended class used to create base node type
	 * @link http://php.net/manual/en/function.dom-domdocument-registernodeclass.php
	 * @param baseclass string
	 * @param extendedclass string
	 * @return bool
	 */
	public function registerNodeClass ($baseclass, $extendedclass) {}

	/**
	 * Adds a new child before a reference node
	 * @link http://php.net/manual/en/function.dom-domnode-insertbefore.php
	 * @param newnode DOMNode
	 * @param refnode DOMNode[optional]
	 * @return DOMNode
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://php.net/manual/en/function.dom-domnode-replacechild.php
	 * @param newnode DOMNode
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://php.net/manual/en/function.dom-domnode-removechild.php
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://php.net/manual/en/function.dom-domnode-appendchild.php
	 * @param newnode DOMNode
	 * @return DOMNode
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://php.net/manual/en/function.dom-domnode-haschildnodes.php
	 * @return bool
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://php.net/manual/en/function.dom-domnode-clonenode.php
	 * @param deep bool[optional]
	 * @return DOMNode
	 */
	public function cloneNode ($deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://php.net/manual/en/function.dom-domnode-normalize.php
	 * @return void
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://php.net/manual/en/function.dom-domnode-issupported.php
	 * @param feature string
	 * @param version string
	 * @return bool
	 */
	public function isSupported ($feature, $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://php.net/manual/en/function.dom-domnode-hasattributes.php
	 * @return bool
	 */
	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://php.net/manual/en/function.dom-domnode-issamenode.php
	 * @param node DOMNode
	 * @return bool
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://php.net/manual/en/function.dom-domnode-lookupprefix.php
	 * @param namespaceURI string
	 * @return string
	 */
	public function lookupPrefix ($namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://php.net/manual/en/function.dom-domnode-isdefaultnamespace.php
	 * @param namespaceURI string
	 * @return bool
	 */
	public function isDefaultNamespace ($namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://php.net/manual/en/function.dom-domnode-lookupnamespaceuri.php
	 * @param prefix string
	 * @return string
	 */
	public function lookupNamespaceUri ($prefix) {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMNodeList  {

	/**
	 * Retrieves a node specified by index
	 * @link http://php.net/manual/en/function.dom-domnodelist-item.php
	 * @param index int
	 * @return DOMNode
	 */
	public function item ($index) {}

}

/**
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMNamedNodeMap  {

	/**
	 * Retrieves a node specified by name
	 * @link http://php.net/manual/en/function.dom-domnamednodemap-getnameditem.php
	 * @param name string
	 * @return DOMNode
	 */
	public function getNamedItem ($name) {}

	public function setNamedItem () {}

	public function removeNamedItem () {}

	/**
	 * Retrieves a node specified by index
	 * @link http://php.net/manual/en/function.dom-domnamednodemap-item.php
	 * @param index int
	 * @return DOMNode
	 */
	public function item ($index) {}

	/**
	 * Retrieves a node specified by local name and namespace URI
	 * @link http://php.net/manual/en/function.dom-domnamednodemap-getnameditemns.php
	 * @param namespaceURI string
	 * @param localName string
	 * @return DOMNode
	 */
	public function getNamedItemNS ($namespaceURI, $localName) {}

	public function setNamedItemNS () {}

	public function removeNamedItemNS () {}

}

/**
 * Extends DOMNode.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMCharacterData extends DOMNode  {

	/**
	 * Extracts a range of data from the node
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-substringdata.php
	 * @param offset int
	 * @param count int
	 * @return string
	 */
	public function substringData ($offset, $count) {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-appenddata.php
	 * @param data string
	 * @return void
	 */
	public function appendData ($data) {}

	/**
	 * Insert a string at the specified 16-bit unit offset
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-insertdata.php
	 * @param offset int
	 * @param data string
	 * @return void
	 */
	public function insertData ($offset, $data) {}

	/**
	 * Remove a range of characters from the node
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-deletedata.php
	 * @param offset int
	 * @param count int
	 * @return void
	 */
	public function deleteData ($offset, $count) {}

	/**
	 * Replace a substring within the DOMCharacterData node
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-replacedata.php
	 * @param offset int
	 * @param count int
	 * @param data string
	 * @return void
	 */
	public function replaceData ($offset, $count, $data) {}

	/**
	 * Adds a new child before a reference node
	 * @link http://php.net/manual/en/function.dom-domnode-insertbefore.php
	 * @param newnode DOMNode
	 * @param refnode DOMNode[optional]
	 * @return DOMNode
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://php.net/manual/en/function.dom-domnode-replacechild.php
	 * @param newnode DOMNode
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://php.net/manual/en/function.dom-domnode-removechild.php
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://php.net/manual/en/function.dom-domnode-appendchild.php
	 * @param newnode DOMNode
	 * @return DOMNode
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://php.net/manual/en/function.dom-domnode-haschildnodes.php
	 * @return bool
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://php.net/manual/en/function.dom-domnode-clonenode.php
	 * @param deep bool[optional]
	 * @return DOMNode
	 */
	public function cloneNode ($deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://php.net/manual/en/function.dom-domnode-normalize.php
	 * @return void
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://php.net/manual/en/function.dom-domnode-issupported.php
	 * @param feature string
	 * @param version string
	 * @return bool
	 */
	public function isSupported ($feature, $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://php.net/manual/en/function.dom-domnode-hasattributes.php
	 * @return bool
	 */
	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://php.net/manual/en/function.dom-domnode-issamenode.php
	 * @param node DOMNode
	 * @return bool
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://php.net/manual/en/function.dom-domnode-lookupprefix.php
	 * @param namespaceURI string
	 * @return string
	 */
	public function lookupPrefix ($namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://php.net/manual/en/function.dom-domnode-isdefaultnamespace.php
	 * @param namespaceURI string
	 * @return bool
	 */
	public function isDefaultNamespace ($namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://php.net/manual/en/function.dom-domnode-lookupnamespaceuri.php
	 * @param prefix string
	 * @return string
	 */
	public function lookupNamespaceUri ($prefix) {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * Extends DOMNode. The DOMAttr
 *       interface represents an attribute in an DOMElement object.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMAttr extends DOMNode  {

	/**
	 * Checks if attribute is a defined ID
	 * @link http://php.net/manual/en/function.dom-domattr-isid.php
	 * @return bool
	 */
	public function isId () {}

	/**
	 * Creates a new DOMAttr object
	 * @link http://php.net/manual/en/function.dom-domattr-construct.php
	 */
	public function __construct () {}

	/**
	 * Adds a new child before a reference node
	 * @link http://php.net/manual/en/function.dom-domnode-insertbefore.php
	 * @param newnode DOMNode
	 * @param refnode DOMNode[optional]
	 * @return DOMNode
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://php.net/manual/en/function.dom-domnode-replacechild.php
	 * @param newnode DOMNode
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://php.net/manual/en/function.dom-domnode-removechild.php
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://php.net/manual/en/function.dom-domnode-appendchild.php
	 * @param newnode DOMNode
	 * @return DOMNode
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://php.net/manual/en/function.dom-domnode-haschildnodes.php
	 * @return bool
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://php.net/manual/en/function.dom-domnode-clonenode.php
	 * @param deep bool[optional]
	 * @return DOMNode
	 */
	public function cloneNode ($deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://php.net/manual/en/function.dom-domnode-normalize.php
	 * @return void
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://php.net/manual/en/function.dom-domnode-issupported.php
	 * @param feature string
	 * @param version string
	 * @return bool
	 */
	public function isSupported ($feature, $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://php.net/manual/en/function.dom-domnode-hasattributes.php
	 * @return bool
	 */
	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://php.net/manual/en/function.dom-domnode-issamenode.php
	 * @param node DOMNode
	 * @return bool
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://php.net/manual/en/function.dom-domnode-lookupprefix.php
	 * @param namespaceURI string
	 * @return string
	 */
	public function lookupPrefix ($namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://php.net/manual/en/function.dom-domnode-isdefaultnamespace.php
	 * @param namespaceURI string
	 * @return bool
	 */
	public function isDefaultNamespace ($namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://php.net/manual/en/function.dom-domnode-lookupnamespaceuri.php
	 * @param prefix string
	 * @return string
	 */
	public function lookupNamespaceUri ($prefix) {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * Extends DOMNode.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMElement extends DOMNode  {

	/**
	 * Returns value of attribute
	 * @link http://php.net/manual/en/function.dom-domelement-getattribute.php
	 * @param name string
	 * @return string
	 */
	public function getAttribute ($name) {}

	/**
	 * Adds new attribute
	 * @link http://php.net/manual/en/function.dom-domelement-setattribute.php
	 * @param name string
	 * @param value string
	 * @return bool
	 */
	public function setAttribute ($name, $value) {}

	/**
	 * Removes attribute
	 * @link http://php.net/manual/en/function.dom-domelement-removeattribute.php
	 * @param name string
	 * @return bool
	 */
	public function removeAttribute ($name) {}

	/**
	 * Returns attribute node
	 * @link http://php.net/manual/en/function.dom-domelement-getattributenode.php
	 * @param name string
	 * @return DOMAttr
	 */
	public function getAttributeNode ($name) {}

	/**
	 * Adds new attribute node to element
	 * @link http://php.net/manual/en/function.dom-domelement-setattributenode.php
	 * @param attr DOMAttr
	 * @return DOMAttr old node if the attribute has been replaced or &null;.
	 */
	public function setAttributeNode (DOMAttr $attr) {}

	/**
	 * Removes attribute
	 * @link http://php.net/manual/en/function.dom-domelement-removeattributenode.php
	 * @param oldnode DOMAttr
	 * @return bool
	 */
	public function removeAttributeNode (DOMAttr $oldnode) {}

	/**
	 * Gets elements by tagname
	 * @link http://php.net/manual/en/function.dom-domelement-getelementsbytagname.php
	 * @param name string
	 * @return DOMNodeList
	 */
	public function getElementsByTagName ($name) {}

	/**
	 * Returns value of attribute
	 * @link http://php.net/manual/en/function.dom-domelement-getattributens.php
	 * @param namespaceURI string
	 * @param localName string
	 * @return string
	 */
	public function getAttributeNS ($namespaceURI, $localName) {}

	/**
	 * Adds new attribute
	 * @link http://php.net/manual/en/function.dom-domelement-setattributens.php
	 * @param namespaceURI string
	 * @param qualifiedName string
	 * @param value string
	 * @return void
	 */
	public function setAttributeNS ($namespaceURI, $qualifiedName, $value) {}

	/**
	 * Removes attribute
	 * @link http://php.net/manual/en/function.dom-domelement-removeattributens.php
	 * @param namespaceURI string
	 * @param localName string
	 * @return bool
	 */
	public function removeAttributeNS ($namespaceURI, $localName) {}

	/**
	 * Returns attribute node
	 * @link http://php.net/manual/en/function.dom-domelement-getattributenodens.php
	 * @param namespaceURI string
	 * @param localName string
	 * @return DOMAttr
	 */
	public function getAttributeNodeNS ($namespaceURI, $localName) {}

	/**
	 * Adds new attribute node to element
	 * @link http://php.net/manual/en/function.dom-domelement-setattributenodens.php
	 * @param attr DOMAttr
	 * @return DOMAttr the old node if the attribute has been replaced.
	 */
	public function setAttributeNodeNS (DOMAttr $attr) {}

	/**
	 * Get elements by namespaceURI and localName
	 * @link http://php.net/manual/en/function.dom-domelement-getelementsbytagnamens.php
	 * @param namespaceURI string
	 * @param localName string
	 * @return DOMNodeList
	 */
	public function getElementsByTagNameNS ($namespaceURI, $localName) {}

	/**
	 * Checks to see if attribute exists
	 * @link http://php.net/manual/en/function.dom-domelement-hasattribute.php
	 * @param name string
	 * @return bool
	 */
	public function hasAttribute ($name) {}

	/**
	 * Checks to see if attribute exists
	 * @link http://php.net/manual/en/function.dom-domelement-hasattributens.php
	 * @param namespaceURI string
	 * @param localName string
	 * @return bool
	 */
	public function hasAttributeNS ($namespaceURI, $localName) {}

	/**
	 * Declares the attribute specified by name to be of type ID
	 * @link http://php.net/manual/en/function.dom-domelement-setidattribute.php
	 * @param name string
	 * @param isId bool
	 * @return void
	 */
	public function setIdAttribute ($name, $isId) {}

	/**
	 * Declares the attribute specified by local name and namespace URI to be of type ID
	 * @link http://php.net/manual/en/function.dom-domelement-setidattributens.php
	 * @param namespaceURI string
	 * @param localName string
	 * @param isId bool
	 * @return void
	 */
	public function setIdAttributeNS ($namespaceURI, $localName, $isId) {}

	/**
	 * Declares the attribute specified by node to be of type ID
	 * @link http://php.net/manual/en/function.dom-domelement-setidattributenode.php
	 * @param attr DOMAttr
	 * @param isId bool
	 * @return void
	 */
	public function setIdAttributeNode (DOMAttr $attr, $isId) {}

	/**
	 * Creates a new DOMElement object
	 * @link http://php.net/manual/en/function.dom-domelement-construct.php
	 */
	public function __construct () {}

	/**
	 * Adds a new child before a reference node
	 * @link http://php.net/manual/en/function.dom-domnode-insertbefore.php
	 * @param newnode DOMNode
	 * @param refnode DOMNode[optional]
	 * @return DOMNode
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://php.net/manual/en/function.dom-domnode-replacechild.php
	 * @param newnode DOMNode
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://php.net/manual/en/function.dom-domnode-removechild.php
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://php.net/manual/en/function.dom-domnode-appendchild.php
	 * @param newnode DOMNode
	 * @return DOMNode
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://php.net/manual/en/function.dom-domnode-haschildnodes.php
	 * @return bool
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://php.net/manual/en/function.dom-domnode-clonenode.php
	 * @param deep bool[optional]
	 * @return DOMNode
	 */
	public function cloneNode ($deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://php.net/manual/en/function.dom-domnode-normalize.php
	 * @return void
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://php.net/manual/en/function.dom-domnode-issupported.php
	 * @param feature string
	 * @param version string
	 * @return bool
	 */
	public function isSupported ($feature, $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://php.net/manual/en/function.dom-domnode-hasattributes.php
	 * @return bool
	 */
	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://php.net/manual/en/function.dom-domnode-issamenode.php
	 * @param node DOMNode
	 * @return bool
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://php.net/manual/en/function.dom-domnode-lookupprefix.php
	 * @param namespaceURI string
	 * @return string
	 */
	public function lookupPrefix ($namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://php.net/manual/en/function.dom-domnode-isdefaultnamespace.php
	 * @param namespaceURI string
	 * @return bool
	 */
	public function isDefaultNamespace ($namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://php.net/manual/en/function.dom-domnode-lookupnamespaceuri.php
	 * @param prefix string
	 * @return string
	 */
	public function lookupNamespaceUri ($prefix) {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * Extends DOMCharacterData.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMText extends DOMCharacterData  {

	/**
	 * Breaks this node into two nodes at the specified offset
	 * @link http://php.net/manual/en/function.dom-domtext-splittext.php
	 * @param offset int
	 * @return DOMText
	 */
	public function splitText ($offset) {}

	/**
	 * Indicates whether this text node contains whitespace
	 * @link http://php.net/manual/en/function.dom-domtext-iswhitespaceinelementcontent.php
	 * @return bool
	 */
	public function isWhitespaceInElementContent () {}

	public function isElementContentWhitespace () {}

	public function replaceWholeText () {}

	/**
	 * Creates a new DOMText object
	 * @link http://php.net/manual/en/function.dom-domtext-construct.php
	 */
	public function __construct () {}

	/**
	 * Extracts a range of data from the node
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-substringdata.php
	 * @param offset int
	 * @param count int
	 * @return string
	 */
	public function substringData ($offset, $count) {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-appenddata.php
	 * @param data string
	 * @return void
	 */
	public function appendData ($data) {}

	/**
	 * Insert a string at the specified 16-bit unit offset
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-insertdata.php
	 * @param offset int
	 * @param data string
	 * @return void
	 */
	public function insertData ($offset, $data) {}

	/**
	 * Remove a range of characters from the node
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-deletedata.php
	 * @param offset int
	 * @param count int
	 * @return void
	 */
	public function deleteData ($offset, $count) {}

	/**
	 * Replace a substring within the DOMCharacterData node
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-replacedata.php
	 * @param offset int
	 * @param count int
	 * @param data string
	 * @return void
	 */
	public function replaceData ($offset, $count, $data) {}

	/**
	 * Adds a new child before a reference node
	 * @link http://php.net/manual/en/function.dom-domnode-insertbefore.php
	 * @param newnode DOMNode
	 * @param refnode DOMNode[optional]
	 * @return DOMNode
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://php.net/manual/en/function.dom-domnode-replacechild.php
	 * @param newnode DOMNode
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://php.net/manual/en/function.dom-domnode-removechild.php
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://php.net/manual/en/function.dom-domnode-appendchild.php
	 * @param newnode DOMNode
	 * @return DOMNode
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://php.net/manual/en/function.dom-domnode-haschildnodes.php
	 * @return bool
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://php.net/manual/en/function.dom-domnode-clonenode.php
	 * @param deep bool[optional]
	 * @return DOMNode
	 */
	public function cloneNode ($deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://php.net/manual/en/function.dom-domnode-normalize.php
	 * @return void
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://php.net/manual/en/function.dom-domnode-issupported.php
	 * @param feature string
	 * @param version string
	 * @return bool
	 */
	public function isSupported ($feature, $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://php.net/manual/en/function.dom-domnode-hasattributes.php
	 * @return bool
	 */
	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://php.net/manual/en/function.dom-domnode-issamenode.php
	 * @param node DOMNode
	 * @return bool
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://php.net/manual/en/function.dom-domnode-lookupprefix.php
	 * @param namespaceURI string
	 * @return string
	 */
	public function lookupPrefix ($namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://php.net/manual/en/function.dom-domnode-isdefaultnamespace.php
	 * @param namespaceURI string
	 * @return bool
	 */
	public function isDefaultNamespace ($namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://php.net/manual/en/function.dom-domnode-lookupnamespaceuri.php
	 * @param prefix string
	 * @return string
	 */
	public function lookupNamespaceUri ($prefix) {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * Extends DOMCharacterData.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMComment extends DOMCharacterData  {

	/**
	 * Creates a new DOMComment object
	 * @link http://php.net/manual/en/function.dom-domcomment-construct.php
	 */
	public function __construct () {}

	/**
	 * Extracts a range of data from the node
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-substringdata.php
	 * @param offset int
	 * @param count int
	 * @return string
	 */
	public function substringData ($offset, $count) {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-appenddata.php
	 * @param data string
	 * @return void
	 */
	public function appendData ($data) {}

	/**
	 * Insert a string at the specified 16-bit unit offset
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-insertdata.php
	 * @param offset int
	 * @param data string
	 * @return void
	 */
	public function insertData ($offset, $data) {}

	/**
	 * Remove a range of characters from the node
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-deletedata.php
	 * @param offset int
	 * @param count int
	 * @return void
	 */
	public function deleteData ($offset, $count) {}

	/**
	 * Replace a substring within the DOMCharacterData node
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-replacedata.php
	 * @param offset int
	 * @param count int
	 * @param data string
	 * @return void
	 */
	public function replaceData ($offset, $count, $data) {}

	/**
	 * Adds a new child before a reference node
	 * @link http://php.net/manual/en/function.dom-domnode-insertbefore.php
	 * @param newnode DOMNode
	 * @param refnode DOMNode[optional]
	 * @return DOMNode
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://php.net/manual/en/function.dom-domnode-replacechild.php
	 * @param newnode DOMNode
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://php.net/manual/en/function.dom-domnode-removechild.php
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://php.net/manual/en/function.dom-domnode-appendchild.php
	 * @param newnode DOMNode
	 * @return DOMNode
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://php.net/manual/en/function.dom-domnode-haschildnodes.php
	 * @return bool
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://php.net/manual/en/function.dom-domnode-clonenode.php
	 * @param deep bool[optional]
	 * @return DOMNode
	 */
	public function cloneNode ($deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://php.net/manual/en/function.dom-domnode-normalize.php
	 * @return void
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://php.net/manual/en/function.dom-domnode-issupported.php
	 * @param feature string
	 * @param version string
	 * @return bool
	 */
	public function isSupported ($feature, $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://php.net/manual/en/function.dom-domnode-hasattributes.php
	 * @return bool
	 */
	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://php.net/manual/en/function.dom-domnode-issamenode.php
	 * @param node DOMNode
	 * @return bool
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://php.net/manual/en/function.dom-domnode-lookupprefix.php
	 * @param namespaceURI string
	 * @return string
	 */
	public function lookupPrefix ($namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://php.net/manual/en/function.dom-domnode-isdefaultnamespace.php
	 * @param namespaceURI string
	 * @return bool
	 */
	public function isDefaultNamespace ($namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://php.net/manual/en/function.dom-domnode-lookupnamespaceuri.php
	 * @param prefix string
	 * @return string
	 */
	public function lookupNamespaceUri ($prefix) {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

class DOMTypeinfo  {
}

class DOMUserDataHandler  {

	public function handle () {}

}

class DOMDomError  {
}

class DOMErrorHandler  {

	public function handleError () {}

}

class DOMLocator  {
}

class DOMConfiguration  {

	public function setParameter () {}

	public function getParameter () {}

	public function canSetParameter () {}

}

class DOMCdataSection extends DOMText  {

	public function __construct () {}

	/**
	 * Breaks this node into two nodes at the specified offset
	 * @link http://php.net/manual/en/function.dom-domtext-splittext.php
	 * @param offset int
	 * @return DOMText
	 */
	public function splitText ($offset) {}

	/**
	 * Indicates whether this text node contains whitespace
	 * @link http://php.net/manual/en/function.dom-domtext-iswhitespaceinelementcontent.php
	 * @return bool
	 */
	public function isWhitespaceInElementContent () {}

	public function isElementContentWhitespace () {}

	public function replaceWholeText () {}

	/**
	 * Extracts a range of data from the node
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-substringdata.php
	 * @param offset int
	 * @param count int
	 * @return string
	 */
	public function substringData ($offset, $count) {}

	/**
	 * Append the string to the end of the character data of the node
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-appenddata.php
	 * @param data string
	 * @return void
	 */
	public function appendData ($data) {}

	/**
	 * Insert a string at the specified 16-bit unit offset
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-insertdata.php
	 * @param offset int
	 * @param data string
	 * @return void
	 */
	public function insertData ($offset, $data) {}

	/**
	 * Remove a range of characters from the node
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-deletedata.php
	 * @param offset int
	 * @param count int
	 * @return void
	 */
	public function deleteData ($offset, $count) {}

	/**
	 * Replace a substring within the DOMCharacterData node
	 * @link http://php.net/manual/en/function.dom-domcharacterdata-replacedata.php
	 * @param offset int
	 * @param count int
	 * @param data string
	 * @return void
	 */
	public function replaceData ($offset, $count, $data) {}

	/**
	 * Adds a new child before a reference node
	 * @link http://php.net/manual/en/function.dom-domnode-insertbefore.php
	 * @param newnode DOMNode
	 * @param refnode DOMNode[optional]
	 * @return DOMNode
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://php.net/manual/en/function.dom-domnode-replacechild.php
	 * @param newnode DOMNode
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://php.net/manual/en/function.dom-domnode-removechild.php
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://php.net/manual/en/function.dom-domnode-appendchild.php
	 * @param newnode DOMNode
	 * @return DOMNode
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://php.net/manual/en/function.dom-domnode-haschildnodes.php
	 * @return bool
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://php.net/manual/en/function.dom-domnode-clonenode.php
	 * @param deep bool[optional]
	 * @return DOMNode
	 */
	public function cloneNode ($deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://php.net/manual/en/function.dom-domnode-normalize.php
	 * @return void
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://php.net/manual/en/function.dom-domnode-issupported.php
	 * @param feature string
	 * @param version string
	 * @return bool
	 */
	public function isSupported ($feature, $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://php.net/manual/en/function.dom-domnode-hasattributes.php
	 * @return bool
	 */
	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://php.net/manual/en/function.dom-domnode-issamenode.php
	 * @param node DOMNode
	 * @return bool
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://php.net/manual/en/function.dom-domnode-lookupprefix.php
	 * @param namespaceURI string
	 * @return string
	 */
	public function lookupPrefix ($namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://php.net/manual/en/function.dom-domnode-isdefaultnamespace.php
	 * @param namespaceURI string
	 * @return bool
	 */
	public function isDefaultNamespace ($namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://php.net/manual/en/function.dom-domnode-lookupnamespaceuri.php
	 * @param prefix string
	 * @return string
	 */
	public function lookupNamespaceUri ($prefix) {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * Extends DOMNode
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMDocumentType extends DOMNode  {

	/**
	 * Adds a new child before a reference node
	 * @link http://php.net/manual/en/function.dom-domnode-insertbefore.php
	 * @param newnode DOMNode
	 * @param refnode DOMNode[optional]
	 * @return DOMNode
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://php.net/manual/en/function.dom-domnode-replacechild.php
	 * @param newnode DOMNode
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://php.net/manual/en/function.dom-domnode-removechild.php
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://php.net/manual/en/function.dom-domnode-appendchild.php
	 * @param newnode DOMNode
	 * @return DOMNode
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://php.net/manual/en/function.dom-domnode-haschildnodes.php
	 * @return bool
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://php.net/manual/en/function.dom-domnode-clonenode.php
	 * @param deep bool[optional]
	 * @return DOMNode
	 */
	public function cloneNode ($deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://php.net/manual/en/function.dom-domnode-normalize.php
	 * @return void
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://php.net/manual/en/function.dom-domnode-issupported.php
	 * @param feature string
	 * @param version string
	 * @return bool
	 */
	public function isSupported ($feature, $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://php.net/manual/en/function.dom-domnode-hasattributes.php
	 * @return bool
	 */
	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://php.net/manual/en/function.dom-domnode-issamenode.php
	 * @param node DOMNode
	 * @return bool
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://php.net/manual/en/function.dom-domnode-lookupprefix.php
	 * @param namespaceURI string
	 * @return string
	 */
	public function lookupPrefix ($namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://php.net/manual/en/function.dom-domnode-isdefaultnamespace.php
	 * @param namespaceURI string
	 * @return bool
	 */
	public function isDefaultNamespace ($namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://php.net/manual/en/function.dom-domnode-lookupnamespaceuri.php
	 * @param prefix string
	 * @return string
	 */
	public function lookupNamespaceUri ($prefix) {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * Extends DOMNode
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMNotation  {
}

/**
 * Extends DOMNode
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMEntity extends DOMNode  {

	/**
	 * Adds a new child before a reference node
	 * @link http://php.net/manual/en/function.dom-domnode-insertbefore.php
	 * @param newnode DOMNode
	 * @param refnode DOMNode[optional]
	 * @return DOMNode
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://php.net/manual/en/function.dom-domnode-replacechild.php
	 * @param newnode DOMNode
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://php.net/manual/en/function.dom-domnode-removechild.php
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://php.net/manual/en/function.dom-domnode-appendchild.php
	 * @param newnode DOMNode
	 * @return DOMNode
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://php.net/manual/en/function.dom-domnode-haschildnodes.php
	 * @return bool
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://php.net/manual/en/function.dom-domnode-clonenode.php
	 * @param deep bool[optional]
	 * @return DOMNode
	 */
	public function cloneNode ($deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://php.net/manual/en/function.dom-domnode-normalize.php
	 * @return void
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://php.net/manual/en/function.dom-domnode-issupported.php
	 * @param feature string
	 * @param version string
	 * @return bool
	 */
	public function isSupported ($feature, $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://php.net/manual/en/function.dom-domnode-hasattributes.php
	 * @return bool
	 */
	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://php.net/manual/en/function.dom-domnode-issamenode.php
	 * @param node DOMNode
	 * @return bool
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://php.net/manual/en/function.dom-domnode-lookupprefix.php
	 * @param namespaceURI string
	 * @return string
	 */
	public function lookupPrefix ($namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://php.net/manual/en/function.dom-domnode-isdefaultnamespace.php
	 * @param namespaceURI string
	 * @return bool
	 */
	public function isDefaultNamespace ($namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://php.net/manual/en/function.dom-domnode-lookupnamespaceuri.php
	 * @param prefix string
	 * @return string
	 */
	public function lookupNamespaceUri ($prefix) {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * Extends DOMNode.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMEntityReference extends DOMNode  {

	/**
	 * Creates a new DOMEntityReference object
	 * @link http://php.net/manual/en/function.dom-domentityreference-construct.php
	 */
	public function __construct () {}

	/**
	 * Adds a new child before a reference node
	 * @link http://php.net/manual/en/function.dom-domnode-insertbefore.php
	 * @param newnode DOMNode
	 * @param refnode DOMNode[optional]
	 * @return DOMNode
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://php.net/manual/en/function.dom-domnode-replacechild.php
	 * @param newnode DOMNode
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://php.net/manual/en/function.dom-domnode-removechild.php
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://php.net/manual/en/function.dom-domnode-appendchild.php
	 * @param newnode DOMNode
	 * @return DOMNode
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://php.net/manual/en/function.dom-domnode-haschildnodes.php
	 * @return bool
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://php.net/manual/en/function.dom-domnode-clonenode.php
	 * @param deep bool[optional]
	 * @return DOMNode
	 */
	public function cloneNode ($deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://php.net/manual/en/function.dom-domnode-normalize.php
	 * @return void
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://php.net/manual/en/function.dom-domnode-issupported.php
	 * @param feature string
	 * @param version string
	 * @return bool
	 */
	public function isSupported ($feature, $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://php.net/manual/en/function.dom-domnode-hasattributes.php
	 * @return bool
	 */
	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://php.net/manual/en/function.dom-domnode-issamenode.php
	 * @param node DOMNode
	 * @return bool
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://php.net/manual/en/function.dom-domnode-lookupprefix.php
	 * @param namespaceURI string
	 * @return string
	 */
	public function lookupPrefix ($namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://php.net/manual/en/function.dom-domnode-isdefaultnamespace.php
	 * @param namespaceURI string
	 * @return bool
	 */
	public function isDefaultNamespace ($namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://php.net/manual/en/function.dom-domnode-lookupnamespaceuri.php
	 * @param prefix string
	 * @return string
	 */
	public function lookupNamespaceUri ($prefix) {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

/**
 * Extends DOMNode.
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMProcessingInstruction extends DOMNode  {

	/**
	 * Creates a new DOMProcessingInstruction object
	 * @link http://php.net/manual/en/function.dom-domprocessinginstruction-construct.php
	 */
	public function __construct () {}

	/**
	 * Adds a new child before a reference node
	 * @link http://php.net/manual/en/function.dom-domnode-insertbefore.php
	 * @param newnode DOMNode
	 * @param refnode DOMNode[optional]
	 * @return DOMNode
	 */
	public function insertBefore (DOMNode $newnode, DOMNode $refnode = null) {}

	/**
	 * Replaces a child
	 * @link http://php.net/manual/en/function.dom-domnode-replacechild.php
	 * @param newnode DOMNode
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function replaceChild (DOMNode $newnode, DOMNode $oldnode) {}

	/**
	 * Removes child from list of children
	 * @link http://php.net/manual/en/function.dom-domnode-removechild.php
	 * @param oldnode DOMNode
	 * @return DOMNode
	 */
	public function removeChild (DOMNode $oldnode) {}

	/**
	 * Adds new child at the end of the children
	 * @link http://php.net/manual/en/function.dom-domnode-appendchild.php
	 * @param newnode DOMNode
	 * @return DOMNode
	 */
	public function appendChild (DOMNode $newnode) {}

	/**
	 * Checks if node has children
	 * @link http://php.net/manual/en/function.dom-domnode-haschildnodes.php
	 * @return bool
	 */
	public function hasChildNodes () {}

	/**
	 * Clones a node
	 * @link http://php.net/manual/en/function.dom-domnode-clonenode.php
	 * @param deep bool[optional]
	 * @return DOMNode
	 */
	public function cloneNode ($deep = null) {}

	/**
	 * Normalizes the node
	 * @link http://php.net/manual/en/function.dom-domnode-normalize.php
	 * @return void
	 */
	public function normalize () {}

	/**
	 * Checks if feature is supported for specified version
	 * @link http://php.net/manual/en/function.dom-domnode-issupported.php
	 * @param feature string
	 * @param version string
	 * @return bool
	 */
	public function isSupported ($feature, $version) {}

	/**
	 * Checks if node has attributes
	 * @link http://php.net/manual/en/function.dom-domnode-hasattributes.php
	 * @return bool
	 */
	public function hasAttributes () {}

	public function compareDocumentPosition () {}

	/**
	 * Indicates if two nodes are the same node
	 * @link http://php.net/manual/en/function.dom-domnode-issamenode.php
	 * @param node DOMNode
	 * @return bool
	 */
	public function isSameNode (DOMNode $node) {}

	/**
	 * Gets the namespace prefix of the node based on the namespace URI
	 * @link http://php.net/manual/en/function.dom-domnode-lookupprefix.php
	 * @param namespaceURI string
	 * @return string
	 */
	public function lookupPrefix ($namespaceURI) {}

	/**
	 * Checks if the specified namespaceURI is the default namespace or not
	 * @link http://php.net/manual/en/function.dom-domnode-isdefaultnamespace.php
	 * @param namespaceURI string
	 * @return bool
	 */
	public function isDefaultNamespace ($namespaceURI) {}

	/**
	 * Gets the namespace URI of the node based on the prefix
	 * @link http://php.net/manual/en/function.dom-domnode-lookupnamespaceuri.php
	 * @param prefix string
	 * @return string
	 */
	public function lookupNamespaceUri ($prefix) {}

	public function isEqualNode () {}

	public function getFeature () {}

	public function setUserData () {}

	public function getUserData () {}

	public function getNodePath () {}

	public function C14N () {}

	public function C14NFile () {}

}

class DOMStringExtend  {

	public function findOffset16 () {}

	public function findOffset32 () {}

}

/**
 * @link http://php.net/manual/en/ref.dom.php
 */
class DOMXPath  {

	/**
	 * Creates a new DOMXPath object
	 * @link http://php.net/manual/en/function.dom-domxpath-construct.php
	 */
	public function __construct () {}

	/**
	 * Registers the namespace with the DOMXPath object
	 * @link http://php.net/manual/en/function.dom-domxpath-registernamespace.php
	 * @param prefix string
	 * @param namespaceURI string
	 * @return bool
	 */
	public function registerNamespace ($prefix, $namespaceURI) {}

	/**
	 * Evaluates the given XPath expression
	 * @link http://php.net/manual/en/function.dom-domxpath-query.php
	 * @param expression string
	 * @param contextnode DOMNode[optional]
	 * @return DOMNodeList a DOMNodeList containing all nodes matching 
	 */
	public function query ($expression, DOMNode $contextnode = null) {}

	/**
	 * Evaluates the given XPath expression and returns a typed result if possible.
	 * @link http://php.net/manual/en/function.dom-domxpath-evaluate.php
	 * @param expression string
	 * @param contextnode DOMNode[optional]
	 * @return mixed a typed result if possible or a DOMNodeList 
	 */
	public function evaluate ($expression, DOMNode $contextnode = null) {}

}

/**
 * Gets a DOMElement object from a SimpleXMLElement object
 * @link http://php.net/manual/en/function.dom-import-simplexml.php
 * @param node SimpleXMLElement
 * @return DOMElement
 */
function dom_import_simplexml (SimpleXMLElement $node) {}

define ('XML_ELEMENT_NODE', 1);
define ('XML_ATTRIBUTE_NODE', 2);
define ('XML_TEXT_NODE', 3);
define ('XML_CDATA_SECTION_NODE', 4);
define ('XML_ENTITY_REF_NODE', 5);
define ('XML_ENTITY_NODE', 6);
define ('XML_PI_NODE', 7);
define ('XML_COMMENT_NODE', 8);
define ('XML_DOCUMENT_NODE', 9);
define ('XML_DOCUMENT_TYPE_NODE', 10);
define ('XML_DOCUMENT_FRAG_NODE', 11);
define ('XML_NOTATION_NODE', 12);
define ('XML_HTML_DOCUMENT_NODE', 13);
define ('XML_DTD_NODE', 14);
define ('XML_ELEMENT_DECL_NODE', 15);
define ('XML_ATTRIBUTE_DECL_NODE', 16);
define ('XML_ENTITY_DECL_NODE', 17);
define ('XML_NAMESPACE_DECL_NODE', 18);
define ('XML_LOCAL_NAMESPACE', 18);
define ('XML_ATTRIBUTE_CDATA', 1);
define ('XML_ATTRIBUTE_ID', 2);
define ('XML_ATTRIBUTE_IDREF', 3);
define ('XML_ATTRIBUTE_IDREFS', 4);
define ('XML_ATTRIBUTE_ENTITY', 6);
define ('XML_ATTRIBUTE_NMTOKEN', 7);
define ('XML_ATTRIBUTE_NMTOKENS', 8);
define ('XML_ATTRIBUTE_ENUMERATION', 9);
define ('XML_ATTRIBUTE_NOTATION', 10);
define ('DOM_PHP_ERR', 0);
define ('DOM_INDEX_SIZE_ERR', 1);
define ('DOMSTRING_SIZE_ERR', 2);
define ('DOM_HIERARCHY_REQUEST_ERR', 3);
define ('DOM_WRONG_DOCUMENT_ERR', 4);
define ('DOM_INVALID_CHARACTER_ERR', 5);
define ('DOM_NO_DATA_ALLOWED_ERR', 6);
define ('DOM_NO_MODIFICATION_ALLOWED_ERR', 7);
define ('DOM_NOT_FOUND_ERR', 8);
define ('DOM_NOT_SUPPORTED_ERR', 9);
define ('DOM_INUSE_ATTRIBUTE_ERR', 10);
define ('DOM_INVALID_STATE_ERR', 11);
define ('DOM_SYNTAX_ERR', 12);
define ('DOM_INVALID_MODIFICATION_ERR', 13);
define ('DOM_NAMESPACE_ERR', 14);
define ('DOM_INVALID_ACCESS_ERR', 15);
define ('DOM_VALIDATION_ERR', 16);

// End of dom v.20031129

// Start of date v.5.2.4

class DateTime  {
	const ATOM = 'Y-m-d\TH:i:sP';
	const COOKIE = 'l, d-M-y H:i:s T';
	const ISO8601 = 'Y-m-d\TH:i:sO';
	const RFC822 = 'D, d M y H:i:s O';
	const RFC850 = 'l, d-M-y H:i:s T';
	const RFC1036 = 'D, d M y H:i:s O';
	const RFC1123 = 'D, d M Y H:i:s O';
	const RFC2822 = 'D, d M Y H:i:s O';
	const RFC3339 = 'Y-m-d\TH:i:sP';
	const RSS = 'D, d M Y H:i:s O';
	const W3C = 'Y-m-d\TH:i:sP';


	public function __construct () {}

	public function format () {}

	public function modify () {}

	public function getTimezone () {}

	public function setTimezone () {}

	public function getOffset () {}

	public function setTime () {}

	public function setDate () {}

	public function setISODate () {}

}

class DateTimeZone  {

	public function __construct () {}

	public function getName () {}

	public function getOffset () {}

	public function getTransitions () {}

	public static function listAbbreviations () {}

	public static function listIdentifiers () {}

}

/**
 * Parse about any English textual datetime description into a Unix timestamp
 * @link http://php.net/manual/en/function.strtotime.php
 * @param time string
 * @param now int[optional]
 * @return int a timestamp on success, false otherwise. Previous to PHP 5.1.0,
 */
function strtotime ($time, $now = null) {}

/**
 * Format a local time/date
 * @link http://php.net/manual/en/function.date.php
 * @param format string
 * @param timestamp int[optional]
 * @return string a formatted date string. If a non-numeric value is used for 
 */
function date ($format, $timestamp = null) {}

/**
 * Format a local time/date as integer
 * @link http://php.net/manual/en/function.idate.php
 * @param format string
 * @param timestamp int[optional]
 * @return int an integer.
 */
function idate ($format, $timestamp = null) {}

/**
 * Format a GMT/UTC date/time
 * @link http://php.net/manual/en/function.gmdate.php
 * @param format string
 * @param timestamp int[optional]
 * @return string a formatted date string. If a non-numeric value is used for 
 */
function gmdate ($format, $timestamp = null) {}

/**
 * Get Unix timestamp for a date
 * @link http://php.net/manual/en/function.mktime.php
 * @param hour int[optional]
 * @param minute int[optional]
 * @param second int[optional]
 * @param month int[optional]
 * @param day int[optional]
 * @param year int[optional]
 * @param is_dst int[optional]
 * @return int
 */
function mktime ($hour = null, $minute = null, $second = null, $month = null, $day = null, $year = null, $is_dst = null) {}

/**
 * Get Unix timestamp for a GMT date
 * @link http://php.net/manual/en/function.gmmktime.php
 * @param hour int[optional]
 * @param minute int[optional]
 * @param second int[optional]
 * @param month int[optional]
 * @param day int[optional]
 * @param year int[optional]
 * @param is_dst int[optional]
 * @return int a integer Unix timestamp.
 */
function gmmktime ($hour = null, $minute = null, $second = null, $month = null, $day = null, $year = null, $is_dst = null) {}

/**
 * Validate a Gregorian date
 * @link http://php.net/manual/en/function.checkdate.php
 * @param month int
 * @param day int
 * @param year int
 * @return bool true if the date given is valid; otherwise returns false.
 */
function checkdate ($month, $day, $year) {}

/**
 * Format a local time/date according to locale settings
 * @link http://php.net/manual/en/function.strftime.php
 * @param format string
 * @param timestamp int[optional]
 * @return string
 */
function strftime ($format, $timestamp = null) {}

/**
 * Format a GMT/UTC time/date according to locale settings
 * @link http://php.net/manual/en/function.gmstrftime.php
 * @param format string
 * @param timestamp int[optional]
 * @return string
 */
function gmstrftime ($format, $timestamp = null) {}

/**
 * Return current Unix timestamp
 * @link http://php.net/manual/en/function.time.php
 * @return int
 */
function time () {}

/**
 * Get the local time
 * @link http://php.net/manual/en/function.localtime.php
 * @param timestamp int[optional]
 * @param is_associative bool[optional]
 * @return array
 */
function localtime ($timestamp = null, $is_associative = null) {}

/**
 * Get date/time information
 * @link http://php.net/manual/en/function.getdate.php
 * @param timestamp int[optional]
 * @return array an associative array of information related to
 */
function getdate ($timestamp = null) {}

/**
 * Returns new DateTime object
 * @link http://php.net/manual/en/function.date-create.php
 * @param time string[optional]
 * @param timezone DateTimeZone[optional]
 * @return DateTime DateTime object on success or false on failure.
 */
function date_create ($time = null, DateTimeZone $timezone = null) {}

/**
 * Returns associative array with detailed info about given date
 * @link http://php.net/manual/en/function.date-parse.php
 * @param date string
 * @return array array on success or false on failure.
 */
function date_parse ($date) {}

/**
 * Returns date formatted according to given format
 * @link http://php.net/manual/en/function.date-format.php
 * @param object DateTime
 * @param format string
 * @return string formatted date on success or false on failure.
 */
function date_format (DateTime $object, $format) {}

/**
 * Alters the timestamp
 * @link http://php.net/manual/en/function.date-modify.php
 * @param object DateTime
 * @param modify string
 * @return void &null; on success or false on failure.
 */
function date_modify (DateTime $object, $modify) {}

/**
 * Return time zone relative to given DateTime
 * @link http://php.net/manual/en/function.date-timezone-get.php
 * @param object DateTime
 * @return DateTimeZone DateTimeZone object on success or false on failure.
 */
function date_timezone_get (DateTime $object) {}

/**
 * Sets the time zone for the DateTime object
 * @link http://php.net/manual/en/function.date-timezone-set.php
 * @param object DateTime
 * @param timezone DateTimeZone
 * @return void &null; on success or false on failure.
 */
function date_timezone_set (DateTime $object, DateTimeZone $timezone) {}

/**
 * Returns the daylight saving time offset
 * @link http://php.net/manual/en/function.date-offset-get.php
 * @param object DateTime
 * @return int DST offset in seconds on success or false on failure.
 */
function date_offset_get (DateTime $object) {}

/**
 * Sets the time
 * @link http://php.net/manual/en/function.date-time-set.php
 * @param object DateTime
 * @param hour int
 * @param minute int
 * @param second int[optional]
 * @return void &null; on success or false on failure.
 */
function date_time_set (DateTime $object, $hour, $minute, $second = null) {}

/**
 * Sets the date
 * @link http://php.net/manual/en/function.date-date-set.php
 * @param object DateTime
 * @param year int
 * @param month int
 * @param day int
 * @return void &null; on success or false on failure.
 */
function date_date_set (DateTime $object, $year, $month, $day) {}

/**
 * Sets the ISO date
 * @link http://php.net/manual/en/function.date-isodate-set.php
 * @param object DateTime
 * @param year int
 * @param week int
 * @param day int[optional]
 * @return void &null; on success or false on failure.
 */
function date_isodate_set (DateTime $object, $year, $week, $day = null) {}

/**
 * Returns new DateTimeZone object
 * @link http://php.net/manual/en/function.timezone-open.php
 * @param timezone string
 * @return DateTimeZone DateTimeZone object on success or false on failure.
 */
function timezone_open ($timezone) {}

/**
 * Returns the name of the timezone
 * @link http://php.net/manual/en/function.timezone-name-get.php
 * @param object DateTimeZone
 * @return string time zone name on success or false on failure.
 */
function timezone_name_get (DateTimeZone $object) {}

/**
 * Returns the timezone name from abbrevation
 * @link http://php.net/manual/en/function.timezone-name-from-abbr.php
 * @param abbr string
 * @param gmtOffset int[optional]
 * @param isdst int[optional]
 * @return string time zone name on success or false on failure.
 */
function timezone_name_from_abbr ($abbr, $gmtOffset = null, $isdst = null) {}

/**
 * Returns the timezone offset from GMT
 * @link http://php.net/manual/en/function.timezone-offset-get.php
 * @param object DateTimeZone
 * @param datetime DateTime
 * @return int time zone offset in seconds on success or false on failure.
 */
function timezone_offset_get (DateTimeZone $object, DateTime $datetime) {}

/**
 * Returns all transitions for the timezone
 * @link http://php.net/manual/en/function.timezone-transitions-get.php
 * @param object DateTimeZone
 * @return array numerically indexed array containing associative array with all
 */
function timezone_transitions_get (DateTimeZone $object) {}

/**
 * Returns numerically index array with all timezone identifiers
 * @link http://php.net/manual/en/function.timezone-identifiers-list.php
 * @return array array on success or false on failure.
 */
function timezone_identifiers_list () {}

/**
 * Returns associative array containing dst, offset and the timezone name
 * @link http://php.net/manual/en/function.timezone-abbreviations-list.php
 * @return array array on success or false on failure.
 */
function timezone_abbreviations_list () {}

/**
 * Sets the default timezone used by all date/time functions in a script
 * @link http://php.net/manual/en/function.date-default-timezone-set.php
 * @param timezone_identifier string
 * @return bool
 */
function date_default_timezone_set ($timezone_identifier) {}

/**
 * Gets the default timezone used by all date/time functions in a script
 * @link http://php.net/manual/en/function.date-default-timezone-get.php
 * @return string a string.
 */
function date_default_timezone_get () {}

/**
 * Returns time of sunrise for a given day and location
 * @link http://php.net/manual/en/function.date-sunrise.php
 * @param timestamp int
 * @param format int[optional]
 * @param latitude float[optional]
 * @param longitude float[optional]
 * @param zenith float[optional]
 * @param gmt_offset float[optional]
 * @return mixed the sunrise time in a specified format on
 */
function date_sunrise ($timestamp, $format = null, $latitude = null, $longitude = null, $zenith = null, $gmt_offset = null) {}

/**
 * Returns time of sunset for a given day and location
 * @link http://php.net/manual/en/function.date-sunset.php
 * @param timestamp int
 * @param format int[optional]
 * @param latitude float[optional]
 * @param longitude float[optional]
 * @param zenith float[optional]
 * @param gmt_offset float[optional]
 * @return mixed the sunset time in a specified format on
 */
function date_sunset ($timestamp, $format = null, $latitude = null, $longitude = null, $zenith = null, $gmt_offset = null) {}

/**
 * Returns an array with information about sunset/sunrise and twilight begin/end
 * @link http://php.net/manual/en/function.date-sun-info.php
 * @param time int
 * @param latitude float
 * @param longitude float
 * @return array array on success or false on failure.
 */
function date_sun_info ($time, $latitude, $longitude) {}

define ('DATE_ATOM', 'Y-m-d\TH:i:sP');
define ('DATE_COOKIE', 'l, d-M-y H:i:s T');
define ('DATE_ISO8601', 'Y-m-d\TH:i:sO');
define ('DATE_RFC822', 'D, d M y H:i:s O');
define ('DATE_RFC850', 'l, d-M-y H:i:s T');
define ('DATE_RFC1036', 'D, d M y H:i:s O');
define ('DATE_RFC1123', 'D, d M Y H:i:s O');
define ('DATE_RFC2822', 'D, d M Y H:i:s O');
define ('DATE_RFC3339', 'Y-m-d\TH:i:sP');
define ('DATE_RSS', 'D, d M Y H:i:s O');
define ('DATE_W3C', 'Y-m-d\TH:i:sP');
define ('SUNFUNCS_RET_TIMESTAMP', 0);
define ('SUNFUNCS_RET_STRING', 1);
define ('SUNFUNCS_RET_DOUBLE', 2);

// End of date v.5.2.4

// Start of ctype v.

/**
 * Check for alphanumeric character(s)
 * @link http://php.net/manual/en/function.ctype-alnum.php
 * @param text string
 * @return bool true if every character in text is either
 */
function ctype_alnum ($text) {}

/**
 * Check for alphabetic character(s)
 * @link http://php.net/manual/en/function.ctype-alpha.php
 * @param text string
 * @return bool true if every character in text is 
 */
function ctype_alpha ($text) {}

/**
 * Check for control character(s)
 * @link http://php.net/manual/en/function.ctype-cntrl.php
 * @param text string
 * @return bool true if every character in text is 
 */
function ctype_cntrl ($text) {}

/**
 * Check for numeric character(s)
 * @link http://php.net/manual/en/function.ctype-digit.php
 * @param text string
 * @return bool true if every character in text is 
 */
function ctype_digit ($text) {}

/**
 * Check for lowercase character(s)
 * @link http://php.net/manual/en/function.ctype-lower.php
 * @param text string
 * @return bool true if every character in text is 
 */
function ctype_lower ($text) {}

/**
 * Check for any printable character(s) except space
 * @link http://php.net/manual/en/function.ctype-graph.php
 * @param text string
 * @return bool true if every character in text is 
 */
function ctype_graph ($text) {}

/**
 * Check for printable character(s)
 * @link http://php.net/manual/en/function.ctype-print.php
 * @param text string
 * @return bool true if every character in text 
 */
function ctype_print ($text) {}

/**
 * Check for any printable character which is not whitespace or an
   alphanumeric character
 * @link http://php.net/manual/en/function.ctype-punct.php
 * @param text string
 * @return bool true if every character in text 
 */
function ctype_punct ($text) {}

/**
 * Check for whitespace character(s)
 * @link http://php.net/manual/en/function.ctype-space.php
 * @param text string
 * @return bool true if every character in text 
 */
function ctype_space ($text) {}

/**
 * Check for uppercase character(s)
 * @link http://php.net/manual/en/function.ctype-upper.php
 * @param text string
 * @return bool true if every character in text is 
 */
function ctype_upper ($text) {}

/**
 * Check for character(s) representing a hexadecimal digit
 * @link http://php.net/manual/en/function.ctype-xdigit.php
 * @param text string
 * @return bool true if every character in text is 
 */
function ctype_xdigit ($text) {}

// End of ctype v.

// Start of Zend Core v.2.5.0

function zend_core_version () {}

function zend_core_restart () {}

// End of Zend Core v.2.5.0

// Start of zlib v.1.1

/**
 * Output a gz-file
 * @link http://php.net/manual/en/function.readgzfile.php
 * @param filename string
 * @param use_include_path int[optional]
 * @return int the number of (uncompressed) bytes read from the file. If
 */
function readgzfile ($filename, $use_include_path = null) {}

/**
 * Rewind the position of a gz-file pointer
 * @link http://php.net/manual/en/function.gzrewind.php
 * @param zp resource
 * @return bool
 */
function gzrewind ($zp) {}

/**
 * Close an open gz-file pointer
 * @link http://php.net/manual/en/function.gzclose.php
 * @param zp resource
 * @return bool
 */
function gzclose ($zp) {}

/**
 * Test for end-of-file on a gz-file pointer
 * @link http://php.net/manual/en/function.gzeof.php
 * @param zp resource
 * @return int true if the gz-file pointer is at EOF or an error occurs;
 */
function gzeof ($zp) {}

/**
 * Get character from gz-file pointer
 * @link http://php.net/manual/en/function.gzgetc.php
 * @param zp resource
 * @return string
 */
function gzgetc ($zp) {}

/**
 * Get line from file pointer
 * @link http://php.net/manual/en/function.gzgets.php
 * @param zp resource
 * @param length int
 * @return string
 */
function gzgets ($zp, $length) {}

/**
 * Get line from gz-file pointer and strip HTML tags
 * @link http://php.net/manual/en/function.gzgetss.php
 * @param zp resource
 * @param length int
 * @param allowable_tags string[optional]
 * @return string
 */
function gzgetss ($zp, $length, $allowable_tags = null) {}

/**
 * Binary-safe gz-file read
 * @link http://php.net/manual/en/function.gzread.php
 * @param zp resource
 * @param length int
 * @return string
 */
function gzread ($zp, $length) {}

/**
 * Open gz-file
 * @link http://php.net/manual/en/function.gzopen.php
 * @param filename string
 * @param mode string
 * @param use_include_path int[optional]
 * @return resource a file pointer to the file opened, after that, everything you read
 */
function gzopen ($filename, $mode, $use_include_path = null) {}

/**
 * Output all remaining data on a gz-file pointer
 * @link http://php.net/manual/en/function.gzpassthru.php
 * @param zp resource
 * @return int
 */
function gzpassthru ($zp) {}

/**
 * Seek on a gz-file pointer
 * @link http://php.net/manual/en/function.gzseek.php
 * @param zp resource
 * @param offset int
 * @return int
 */
function gzseek ($zp, $offset) {}

/**
 * Tell gz-file pointer read/write position
 * @link http://php.net/manual/en/function.gztell.php
 * @param zp resource
 * @return int
 */
function gztell ($zp) {}

/**
 * Binary-safe gz-file write
 * @link http://php.net/manual/en/function.gzwrite.php
 * @param zp resource
 * @param string string
 * @param length int[optional]
 * @return int the number of (uncompressed) bytes written to the given gz-file 
 */
function gzwrite ($zp, $string, $length = null) {}

/**
 * &Alias; <function>gzwrite</function>
 * @link http://php.net/manual/en/function.gzputs.php
 */
function gzputs () {}

/**
 * Read entire gz-file into an array
 * @link http://php.net/manual/en/function.gzfile.php
 * @param filename string
 * @param use_include_path int[optional]
 * @return array
 */
function gzfile ($filename, $use_include_path = null) {}

/**
 * Compress a string
 * @link http://php.net/manual/en/function.gzcompress.php
 * @param data string
 * @param level int[optional]
 * @return string
 */
function gzcompress ($data, $level = null) {}

/**
 * Uncompress a compressed string
 * @link http://php.net/manual/en/function.gzuncompress.php
 * @param data string
 * @param length int[optional]
 * @return string
 */
function gzuncompress ($data, $length = null) {}

/**
 * Deflate a string
 * @link http://php.net/manual/en/function.gzdeflate.php
 * @param data string
 * @param level int[optional]
 * @return string
 */
function gzdeflate ($data, $level = null) {}

/**
 * Inflate a deflated string
 * @link http://php.net/manual/en/function.gzinflate.php
 * @param data string
 * @param length int[optional]
 * @return string
 */
function gzinflate ($data, $length = null) {}

/**
 * Create a gzip compressed string
 * @link http://php.net/manual/en/function.gzencode.php
 * @param data string
 * @param level int[optional]
 * @param encoding_mode int[optional]
 * @return string
 */
function gzencode ($data, $level = null, $encoding_mode = null) {}

/**
 * ob_start callback function to gzip output buffer
 * @link http://php.net/manual/en/function.ob-gzhandler.php
 * @param buffer string
 * @param mode int
 * @return string
 */
function ob_gzhandler ($buffer, $mode) {}

/**
 * Returns the coding type used for output compression
 * @link http://php.net/manual/en/function.zlib-get-coding-type.php
 * @return string
 */
function zlib_get_coding_type () {}

define ('FORCE_GZIP', 1);
define ('FORCE_DEFLATE', 2);

// End of zlib v.1.1

// Start of openssl v.

/**
 * Frees a private key
 * @link http://php.net/manual/en/function.openssl-pkey-free.php
 * @param key resource
 * @return void
 */
function openssl_pkey_free ($key) {}

/**
 * Generates a new private key
 * @link http://php.net/manual/en/function.openssl-pkey-new.php
 * @param configargs array[optional]
 * @return resource a resource identifier for the pkey on success, or false on
 */
function openssl_pkey_new (array $configargs = null) {}

/**
 * Gets an exportable representation of a key into a string
 * @link http://php.net/manual/en/function.openssl-pkey-export.php
 * @param key mixed
 * @param out string
 * @param passphrase string[optional]
 * @param configargs array[optional]
 * @return bool
 */
function openssl_pkey_export ($key, &$out, $passphrase = null, array $configargs = null) {}

/**
 * Gets an exportable representation of a key into a file
 * @link http://php.net/manual/en/function.openssl-pkey-export-to-file.php
 * @param key mixed
 * @param outfilename string
 * @param passphrase string[optional]
 * @param configargs array[optional]
 * @return bool
 */
function openssl_pkey_export_to_file ($key, $outfilename, $passphrase = null, array $configargs = null) {}

/**
 * Get a private key
 * @link http://php.net/manual/en/function.openssl-pkey-get-private.php
 * @param key mixed
 * @param passphrase string[optional]
 * @return resource a positive key resource identifier on success, or false on error.
 */
function openssl_pkey_get_private ($key, $passphrase = null) {}

/**
 * Extract public key from certificate and prepare it for use
 * @link http://php.net/manual/en/function.openssl-pkey-get-public.php
 * @param certificate mixed
 * @return resource a positive key resource identifier on success, or false on error.
 */
function openssl_pkey_get_public ($certificate) {}

/**
 * Returns an array with the key details (bits, pkey, type)
 * @link http://php.net/manual/en/function.openssl-pkey-get-details.php
 * @param key resource
 * @return array
 */
function openssl_pkey_get_details ($key) {}

/**
 * Free key resource
 * @link http://php.net/manual/en/function.openssl-free-key.php
 * @param key_identifier resource
 * @return void
 */
function openssl_free_key ($key_identifier) {}

/**
 * &Alias; <function>openssl_pkey_get_private</function>
 * @link http://php.net/manual/en/function.openssl-get-privatekey.php
 */
function openssl_get_privatekey () {}

/**
 * &Alias; <function>openssl_pkey_get_public</function>
 * @link http://php.net/manual/en/function.openssl-get-publickey.php
 */
function openssl_get_publickey () {}

/**
 * Parse an X.509 certificate and return a resource identifier for
  it
 * @link http://php.net/manual/en/function.openssl-x509-read.php
 * @param x509certdata mixed
 * @return resource a resource identifier on success, or false on failure.
 */
function openssl_x509_read ($x509certdata) {}

/**
 * Free certificate resource
 * @link http://php.net/manual/en/function.openssl-x509-free.php
 * @param x509cert resource
 * @return void
 */
function openssl_x509_free ($x509cert) {}

/**
 * Parse an X509 certificate and return the information as an array
 * @link http://php.net/manual/en/function.openssl-x509-parse.php
 * @param x509cert mixed
 * @param shortnames bool[optional]
 * @return array
 */
function openssl_x509_parse ($x509cert, $shortnames = null) {}

/**
 * Verifies if a certificate can be used for a particular purpose
 * @link http://php.net/manual/en/function.openssl-x509-checkpurpose.php
 * @param x509cert mixed
 * @param purpose int
 * @param cainfo array[optional]
 * @param untrustedfile string[optional]
 * @return int true if the certificate can be used for the intended purpose,
 */
function openssl_x509_checkpurpose ($x509cert, $purpose, array $cainfo = null, $untrustedfile = null) {}

/**
 * Checks if a private key corresponds to a certificate
 * @link http://php.net/manual/en/function.openssl-x509-check-private-key.php
 * @param cert mixed
 * @param key mixed
 * @return bool true if key is the private key that
 */
function openssl_x509_check_private_key ($cert, $key) {}

/**
 * Exports a certificate as a string
 * @link http://php.net/manual/en/function.openssl-x509-export.php
 * @param x509 mixed
 * @param output string
 * @param notext bool[optional]
 * @return bool
 */
function openssl_x509_export ($x509, &$output, $notext = null) {}

/**
 * Exports a certificate to file
 * @link http://php.net/manual/en/function.openssl-x509-export-to-file.php
 * @param x509 mixed
 * @param outfilename string
 * @param notext bool[optional]
 * @return bool
 */
function openssl_x509_export_to_file ($x509, $outfilename, $notext = null) {}

/**
 * @param var1
 * @param var2
 */
function openssl_pkcs12_export ($var1, &$var2) {}

function openssl_pkcs12_export_to_file () {}

/**
 * @param var1
 * @param var2
 */
function openssl_pkcs12_read ($var1, &$var2) {}

/**
 * Generates a CSR
 * @link http://php.net/manual/en/function.openssl-csr-new.php
 * @param dn array
 * @param privkey resource
 * @param configargs array[optional]
 * @param extraattribs array[optional]
 * @return mixed the CSR.
 */
function openssl_csr_new (array $dn, &$privkey, array $configargs = null, array $extraattribs = null) {}

/**
 * Exports a CSR as a string
 * @link http://php.net/manual/en/function.openssl-csr-export.php
 * @param csr resource
 * @param out string
 * @param notext bool[optional]
 * @return bool
 */
function openssl_csr_export ($csr, &$out, $notext = null) {}

/**
 * Exports a CSR to a file
 * @link http://php.net/manual/en/function.openssl-csr-export-to-file.php
 * @param csr resource
 * @param outfilename string
 * @param notext bool[optional]
 * @return bool
 */
function openssl_csr_export_to_file ($csr, $outfilename, $notext = null) {}

/**
 * Sign a CSR with another certificate (or itself) and generate a certificate
 * @link http://php.net/manual/en/function.openssl-csr-sign.php
 * @param csr mixed
 * @param cacert mixed
 * @param priv_key mixed
 * @param days int
 * @param configargs array[optional]
 * @param serial int[optional]
 * @return resource an x509 certificate resource on success, false on failure.
 */
function openssl_csr_sign ($csr, $cacert, $priv_key, $days, array $configargs = null, $serial = null) {}

/**
 * Returns the subject of a CERT
 * @link http://php.net/manual/en/function.openssl-csr-get-subject.php
 * @param csr mixed
 * @param use_shortnames bool[optional]
 * @return array
 */
function openssl_csr_get_subject ($csr, $use_shortnames = null) {}

/**
 * Returns the public key of a CERT
 * @link http://php.net/manual/en/function.openssl-csr-get-public-key.php
 * @param csr mixed
 * @param use_shortnames bool[optional]
 * @return resource
 */
function openssl_csr_get_public_key ($csr, $use_shortnames = null) {}

/**
 * Generate signature
 * @link http://php.net/manual/en/function.openssl-sign.php
 * @param data string
 * @param signature string
 * @param priv_key_id mixed
 * @param signature_alg int[optional]
 * @return bool
 */
function openssl_sign ($data, &$signature, $priv_key_id, $signature_alg = null) {}

/**
 * Verify signature
 * @link http://php.net/manual/en/function.openssl-verify.php
 * @param data string
 * @param signature string
 * @param pub_key_id mixed
 * @param signature_alg int[optional]
 * @return int 1 if the signature is correct, 0 if it is incorrect, and
 */
function openssl_verify ($data, $signature, $pub_key_id, $signature_alg = null) {}

/**
 * Seal (encrypt) data
 * @link http://php.net/manual/en/function.openssl-seal.php
 * @param data string
 * @param sealed_data string
 * @param env_keys array
 * @param pub_key_ids array
 * @return int the length of the sealed data on success, or false on error.
 */
function openssl_seal ($data, &$sealed_data, array &$env_keys, array $pub_key_ids) {}

/**
 * Open sealed data
 * @link http://php.net/manual/en/function.openssl-open.php
 * @param sealed_data string
 * @param open_data string
 * @param env_key string
 * @param priv_key_id mixed
 * @return bool
 */
function openssl_open ($sealed_data, &$open_data, $env_key, $priv_key_id) {}

/**
 * Verifies the signature of an S/MIME signed message
 * @link http://php.net/manual/en/function.openssl-pkcs7-verify.php
 * @param filename string
 * @param flags int
 * @param outfilename string[optional]
 * @param cainfo array[optional]
 * @param extracerts string[optional]
 * @param content string[optional]
 * @return mixed true if the signature is verified, false if it is not correct
 */
function openssl_pkcs7_verify ($filename, $flags, $outfilename = null, array $cainfo = null, $extracerts = null, $content = null) {}

/**
 * Decrypts an S/MIME encrypted message
 * @link http://php.net/manual/en/function.openssl-pkcs7-decrypt.php
 * @param infilename string
 * @param outfilename string
 * @param recipcert mixed
 * @param recipkey mixed[optional]
 * @return bool
 */
function openssl_pkcs7_decrypt ($infilename, $outfilename, $recipcert, $recipkey = null) {}

/**
 * Sign an S/MIME message
 * @link http://php.net/manual/en/function.openssl-pkcs7-sign.php
 * @param infilename string
 * @param outfilename string
 * @param signcert mixed
 * @param privkey mixed
 * @param headers array
 * @param flags int[optional]
 * @param extracerts string[optional]
 * @return bool
 */
function openssl_pkcs7_sign ($infilename, $outfilename, $signcert, $privkey, array $headers, $flags = null, $extracerts = null) {}

/**
 * Encrypt an S/MIME message
 * @link http://php.net/manual/en/function.openssl-pkcs7-encrypt.php
 * @param infile string
 * @param outfile string
 * @param recipcerts mixed
 * @param headers array
 * @param flags int[optional]
 * @param cipherid int[optional]
 * @return bool
 */
function openssl_pkcs7_encrypt ($infile, $outfile, $recipcerts, array $headers, $flags = null, $cipherid = null) {}

/**
 * Encrypts data with private key
 * @link http://php.net/manual/en/function.openssl-private-encrypt.php
 * @param data string
 * @param crypted string
 * @param key mixed
 * @param padding int[optional]
 * @return bool
 */
function openssl_private_encrypt ($data, &$crypted, $key, $padding = null) {}

/**
 * Decrypts data with private key
 * @link http://php.net/manual/en/function.openssl-private-decrypt.php
 * @param data string
 * @param decrypted string
 * @param key mixed
 * @param padding int[optional]
 * @return bool
 */
function openssl_private_decrypt ($data, &$decrypted, $key, $padding = null) {}

/**
 * Encrypts data with public key
 * @link http://php.net/manual/en/function.openssl-public-encrypt.php
 * @param data string
 * @param crypted string
 * @param key mixed
 * @param padding int[optional]
 * @return bool
 */
function openssl_public_encrypt ($data, &$crypted, $key, $padding = null) {}

/**
 * Decrypts data with public key
 * @link http://php.net/manual/en/function.openssl-public-decrypt.php
 * @param data string
 * @param decrypted string
 * @param key mixed
 * @param padding int[optional]
 * @return bool
 */
function openssl_public_decrypt ($data, &$decrypted, $key, $padding = null) {}

/**
 * Return openSSL error message
 * @link http://php.net/manual/en/function.openssl-error-string.php
 * @return string an error message string, or false if there are no more error
 */
function openssl_error_string () {}

define ('OPENSSL_VERSION_TEXT', 'OpenSSL 0.9.8d 28 Sep 2006');
define ('OPENSSL_VERSION_NUMBER', 9470031);
define ('X509_PURPOSE_SSL_CLIENT', 1);
define ('X509_PURPOSE_SSL_SERVER', 2);
define ('X509_PURPOSE_NS_SSL_SERVER', 3);
define ('X509_PURPOSE_SMIME_SIGN', 4);
define ('X509_PURPOSE_SMIME_ENCRYPT', 5);
define ('X509_PURPOSE_CRL_SIGN', 6);
define ('X509_PURPOSE_ANY', 7);
define ('OPENSSL_ALGO_SHA1', 1);
define ('OPENSSL_ALGO_MD5', 2);
define ('OPENSSL_ALGO_MD4', 3);
define ('OPENSSL_ALGO_MD2', 4);
define ('PKCS7_DETACHED', 64);
define ('PKCS7_TEXT', 1);
define ('PKCS7_NOINTERN', 16);
define ('PKCS7_NOVERIFY', 32);
define ('PKCS7_NOCHAIN', 8);
define ('PKCS7_NOCERTS', 2);
define ('PKCS7_NOATTR', 256);
define ('PKCS7_BINARY', 128);
define ('PKCS7_NOSIGS', 4);
define ('OPENSSL_PKCS1_PADDING', 1);
define ('OPENSSL_SSLV23_PADDING', 2);
define ('OPENSSL_NO_PADDING', 3);
define ('OPENSSL_PKCS1_OAEP_PADDING', 4);
define ('OPENSSL_CIPHER_RC2_40', 0);
define ('OPENSSL_CIPHER_RC2_128', 1);
define ('OPENSSL_CIPHER_RC2_64', 2);
define ('OPENSSL_CIPHER_DES', 3);
define ('OPENSSL_CIPHER_3DES', 4);
define ('OPENSSL_KEYTYPE_RSA', 0);
define ('OPENSSL_KEYTYPE_DSA', 1);
define ('OPENSSL_KEYTYPE_DH', 2);
define ('OPENSSL_KEYTYPE_EC', 3);

// End of openssl v.

// Start of bcmath v.

/**
 * Add two arbitrary precision numbers
 * @link http://php.net/manual/en/function.bcadd.php
 * @param left_operand string
 * @param right_operand string
 * @param scale int[optional]
 * @return string
 */
function bcadd ($left_operand, $right_operand, $scale = null) {}

/**
 * Subtract one arbitrary precision number from another
 * @link http://php.net/manual/en/function.bcsub.php
 * @param left_operand string
 * @param right_operand string
 * @param scale int[optional]
 * @return string
 */
function bcsub ($left_operand, $right_operand, $scale = null) {}

/**
 * Multiply two arbitrary precision number
 * @link http://php.net/manual/en/function.bcmul.php
 * @param left_operand string
 * @param right_operand string
 * @param scale int[optional]
 * @return string the result as a string.
 */
function bcmul ($left_operand, $right_operand, $scale = null) {}

/**
 * Divide two arbitrary precision numbers
 * @link http://php.net/manual/en/function.bcdiv.php
 * @param left_operand string
 * @param right_operand string
 * @param scale int[optional]
 * @return string the result of the division as a string, or &null; if 
 */
function bcdiv ($left_operand, $right_operand, $scale = null) {}

/**
 * Get modulus of an arbitrary precision number
 * @link http://php.net/manual/en/function.bcmod.php
 * @param left_operand string
 * @param modulus string
 * @return string the modulus as a string, or &null; if 
 */
function bcmod ($left_operand, $modulus) {}

/**
 * Raise an arbitrary precision number to another
 * @link http://php.net/manual/en/function.bcpow.php
 * @param left_operand string
 * @param right_operand string
 * @param scale int[optional]
 * @return string the result as a string.
 */
function bcpow ($left_operand, $right_operand, $scale = null) {}

/**
 * Get the square root of an arbitrary precision number
 * @link http://php.net/manual/en/function.bcsqrt.php
 * @param operand string
 * @param scale int[optional]
 * @return string the square root as a string, or &null; if 
 */
function bcsqrt ($operand, $scale = null) {}

/**
 * Set default scale parameter for all bc math functions
 * @link http://php.net/manual/en/function.bcscale.php
 * @param scale int
 * @return bool
 */
function bcscale ($scale) {}

/**
 * Compare two arbitrary precision numbers
 * @link http://php.net/manual/en/function.bccomp.php
 * @param left_operand string
 * @param right_operand string
 * @param scale int[optional]
 * @return int 0 if the two operands are equal, 1 if the
 */
function bccomp ($left_operand, $right_operand, $scale = null) {}

/**
 * Raise an arbitrary precision number to another, reduced by a specified modulus
 * @link http://php.net/manual/en/function.bcpowmod.php
 * @param left_operand string
 * @param right_operand string
 * @param modulus string
 * @param scale int[optional]
 * @return string the result as a string, or &null; if modulus
 */
function bcpowmod ($left_operand, $right_operand, $modulus, $scale = null) {}

// End of bcmath v.

// Start of curl v.

/**
 * Initialize a cURL session
 * @link http://php.net/manual/en/function.curl-init.php
 * @param url string[optional]
 * @return resource a cURL handle on success, false on errors.
 */
function curl_init ($url = null) {}

/**
 * Copy a cURL handle along with all of its preferences
 * @link http://php.net/manual/en/function.curl-copy-handle.php
 * @param ch resource
 * @return resource a new cURL handle.
 */
function curl_copy_handle ($ch) {}

/**
 * Gets cURL version information
 * @link http://php.net/manual/en/function.curl-version.php
 * @param age int[optional]
 * @return array an associative array with the following elements: 
 */
function curl_version ($age = null) {}

/**
 * Set an option for a cURL transfer
 * @link http://php.net/manual/en/function.curl-setopt.php
 * @param ch resource
 * @param option int
 * @param value mixed
 * @return bool
 */
function curl_setopt ($ch, $option, $value) {}

/**
 * Set multiple options for a cURL transfer
 * @link http://php.net/manual/en/function.curl-setopt-array.php
 * @param ch resource
 * @param options array
 * @return bool true if all options were successfully set. If an option could
 */
function curl_setopt_array ($ch, array $options) {}

/**
 * Perform a cURL session
 * @link http://php.net/manual/en/function.curl-exec.php
 * @param ch resource
 * @return mixed
 */
function curl_exec ($ch) {}

/**
 * Get information regarding a specific transfer
 * @link http://php.net/manual/en/function.curl-getinfo.php
 * @param ch resource
 * @param opt int[optional]
 * @return mixed
 */
function curl_getinfo ($ch, $opt = null) {}

/**
 * Return a string containing the last error for the current session
 * @link http://php.net/manual/en/function.curl-error.php
 * @param ch resource
 * @return string the error number or '' (the empty string) if no
 */
function curl_error ($ch) {}

/**
 * Return the last error number
 * @link http://php.net/manual/en/function.curl-errno.php
 * @param ch resource
 * @return int the error number or 0 (zero) if no error
 */
function curl_errno ($ch) {}

/**
 * Close a cURL session
 * @link http://php.net/manual/en/function.curl-close.php
 * @param ch resource
 * @return void
 */
function curl_close ($ch) {}

/**
 * Returns a new cURL multi handle
 * @link http://php.net/manual/en/function.curl-multi-init.php
 * @return resource a cURL on handle on success, false on failure.
 */
function curl_multi_init () {}

/**
 * Add a normal cURL handle to a cURL multi handle
 * @link http://php.net/manual/en/function.curl-multi-add-handle.php
 * @param mh resource
 * @param ch resource
 * @return int 0 on success, or one of the CURLM_XXX errors
 */
function curl_multi_add_handle ($mh, $ch) {}

/**
 * Remove a multi handle from a set of cURL handles
 * @link http://php.net/manual/en/function.curl-multi-remove-handle.php
 * @param mh resource
 * @param ch resource
 * @return int
 */
function curl_multi_remove_handle ($mh, $ch) {}

/**
 * Get all the sockets associated with the cURL extension, which can then be "selected"
 * @link http://php.net/manual/en/function.curl-multi-select.php
 * @param mh resource
 * @param timeout float[optional]
 * @return int
 */
function curl_multi_select ($mh, $timeout = null) {}

/**
 * Run the sub-connections of the current cURL handle
 * @link http://php.net/manual/en/function.curl-multi-exec.php
 * @param mh resource
 * @param still_running int
 * @return int
 */
function curl_multi_exec ($mh, &$still_running) {}

/**
 * Return the content of a cURL handle if <constant>CURLOPT_RETURNTRANSFER</constant> is set
 * @link http://php.net/manual/en/function.curl-multi-getcontent.php
 * @param ch resource
 * @return string
 */
function curl_multi_getcontent ($ch) {}

/**
 * Get information about the current transfers
 * @link http://php.net/manual/en/function.curl-multi-info-read.php
 * @param mh resource
 * @param msgs_in_queue int[optional]
 * @return array
 */
function curl_multi_info_read ($mh, $msgs_in_queue = null) {}

/**
 * Close a set of cURL handles
 * @link http://php.net/manual/en/function.curl-multi-close.php
 * @param mh resource
 * @return void
 */
function curl_multi_close ($mh) {}

define ('CURLOPT_DNS_USE_GLOBAL_CACHE', 91);
define ('CURLOPT_DNS_CACHE_TIMEOUT', 92);
define ('CURLOPT_PORT', 3);
define ('CURLOPT_FILE', 10001);
define ('CURLOPT_READDATA', 10009);
define ('CURLOPT_INFILE', 10009);
define ('CURLOPT_INFILESIZE', 14);
define ('CURLOPT_URL', 10002);
define ('CURLOPT_PROXY', 10004);
define ('CURLOPT_VERBOSE', 41);
define ('CURLOPT_HEADER', 42);
define ('CURLOPT_HTTPHEADER', 10023);
define ('CURLOPT_NOPROGRESS', 43);
define ('CURLOPT_NOBODY', 44);
define ('CURLOPT_FAILONERROR', 45);
define ('CURLOPT_UPLOAD', 46);
define ('CURLOPT_POST', 47);
define ('CURLOPT_FTPLISTONLY', 48);
define ('CURLOPT_FTPAPPEND', 50);
define ('CURLOPT_NETRC', 51);
define ('CURLOPT_FOLLOWLOCATION', 52);
define ('CURLOPT_PUT', 54);
define ('CURLOPT_USERPWD', 10005);
define ('CURLOPT_PROXYUSERPWD', 10006);
define ('CURLOPT_RANGE', 10007);
define ('CURLOPT_TIMEOUT', 13);
define ('CURLOPT_POSTFIELDS', 10015);
define ('CURLOPT_REFERER', 10016);
define ('CURLOPT_USERAGENT', 10018);
define ('CURLOPT_FTPPORT', 10017);
define ('CURLOPT_FTP_USE_EPSV', 85);
define ('CURLOPT_LOW_SPEED_LIMIT', 19);
define ('CURLOPT_LOW_SPEED_TIME', 20);
define ('CURLOPT_RESUME_FROM', 21);
define ('CURLOPT_COOKIE', 10022);
define ('CURLOPT_COOKIESESSION', 96);
define ('CURLOPT_AUTOREFERER', 58);
define ('CURLOPT_SSLCERT', 10025);
define ('CURLOPT_SSLCERTPASSWD', 10026);
define ('CURLOPT_WRITEHEADER', 10029);
define ('CURLOPT_SSL_VERIFYHOST', 81);
define ('CURLOPT_COOKIEFILE', 10031);
define ('CURLOPT_SSLVERSION', 32);
define ('CURLOPT_TIMECONDITION', 33);
define ('CURLOPT_TIMEVALUE', 34);
define ('CURLOPT_CUSTOMREQUEST', 10036);
define ('CURLOPT_STDERR', 10037);
define ('CURLOPT_TRANSFERTEXT', 53);
define ('CURLOPT_RETURNTRANSFER', 19913);
define ('CURLOPT_QUOTE', 10028);
define ('CURLOPT_POSTQUOTE', 10039);
define ('CURLOPT_INTERFACE', 10062);
define ('CURLOPT_KRB4LEVEL', 10063);
define ('CURLOPT_HTTPPROXYTUNNEL', 61);
define ('CURLOPT_FILETIME', 69);
define ('CURLOPT_WRITEFUNCTION', 20011);
define ('CURLOPT_READFUNCTION', 20012);
define ('CURLOPT_HEADERFUNCTION', 20079);
define ('CURLOPT_MAXREDIRS', 68);
define ('CURLOPT_MAXCONNECTS', 71);
define ('CURLOPT_CLOSEPOLICY', 72);
define ('CURLOPT_FRESH_CONNECT', 74);
define ('CURLOPT_FORBID_REUSE', 75);
define ('CURLOPT_RANDOM_FILE', 10076);
define ('CURLOPT_EGDSOCKET', 10077);
define ('CURLOPT_CONNECTTIMEOUT', 78);
define ('CURLOPT_SSL_VERIFYPEER', 64);
define ('CURLOPT_CAINFO', 10065);
define ('CURLOPT_CAPATH', 10097);
define ('CURLOPT_COOKIEJAR', 10082);
define ('CURLOPT_SSL_CIPHER_LIST', 10083);
define ('CURLOPT_BINARYTRANSFER', 19914);
define ('CURLOPT_NOSIGNAL', 99);
define ('CURLOPT_PROXYTYPE', 101);
define ('CURLOPT_BUFFERSIZE', 98);
define ('CURLOPT_HTTPGET', 80);
define ('CURLOPT_HTTP_VERSION', 84);
define ('CURLOPT_SSLKEY', 10087);
define ('CURLOPT_SSLKEYTYPE', 10088);
define ('CURLOPT_SSLKEYPASSWD', 10026);
define ('CURLOPT_SSLENGINE', 10089);
define ('CURLOPT_SSLENGINE_DEFAULT', 90);
define ('CURLOPT_SSLCERTTYPE', 10086);
define ('CURLOPT_CRLF', 27);
define ('CURLOPT_ENCODING', 10102);
define ('CURLOPT_PROXYPORT', 59);
define ('CURLOPT_UNRESTRICTED_AUTH', 105);
define ('CURLOPT_FTP_USE_EPRT', 106);
define ('CURLOPT_TCP_NODELAY', 121);
define ('CURLOPT_HTTP200ALIASES', 10104);
define ('CURL_TIMECOND_IFMODSINCE', 1);
define ('CURL_TIMECOND_IFUNMODSINCE', 2);
define ('CURL_TIMECOND_LASTMOD', 3);
define ('CURLOPT_HTTPAUTH', 107);
define ('CURLAUTH_BASIC', 1);
define ('CURLAUTH_DIGEST', 2);
define ('CURLAUTH_GSSNEGOTIATE', 4);
define ('CURLAUTH_NTLM', 8);
define ('CURLAUTH_ANY', -1);
define ('CURLAUTH_ANYSAFE', -2);
define ('CURLOPT_PROXYAUTH', 111);
define ('CURLOPT_FTP_CREATE_MISSING_DIRS', 110);
define ('CURLOPT_PRIVATE', 10103);
define ('CURLCLOSEPOLICY_LEAST_RECENTLY_USED', 2);
define ('CURLCLOSEPOLICY_LEAST_TRAFFIC', 3);
define ('CURLCLOSEPOLICY_SLOWEST', 4);
define ('CURLCLOSEPOLICY_CALLBACK', 5);
define ('CURLCLOSEPOLICY_OLDEST', 1);
define ('CURLINFO_EFFECTIVE_URL', 1048577);
define ('CURLINFO_HTTP_CODE', 2097154);
define ('CURLINFO_HEADER_SIZE', 2097163);
define ('CURLINFO_REQUEST_SIZE', 2097164);
define ('CURLINFO_TOTAL_TIME', 3145731);
define ('CURLINFO_NAMELOOKUP_TIME', 3145732);
define ('CURLINFO_CONNECT_TIME', 3145733);
define ('CURLINFO_PRETRANSFER_TIME', 3145734);
define ('CURLINFO_SIZE_UPLOAD', 3145735);
define ('CURLINFO_SIZE_DOWNLOAD', 3145736);
define ('CURLINFO_SPEED_DOWNLOAD', 3145737);
define ('CURLINFO_SPEED_UPLOAD', 3145738);
define ('CURLINFO_FILETIME', 2097166);
define ('CURLINFO_SSL_VERIFYRESULT', 2097165);
define ('CURLINFO_CONTENT_LENGTH_DOWNLOAD', 3145743);
define ('CURLINFO_CONTENT_LENGTH_UPLOAD', 3145744);
define ('CURLINFO_STARTTRANSFER_TIME', 3145745);
define ('CURLINFO_CONTENT_TYPE', 1048594);
define ('CURLINFO_REDIRECT_TIME', 3145747);
define ('CURLINFO_REDIRECT_COUNT', 2097172);
define ('CURLINFO_HEADER_OUT', 2);
define ('CURLINFO_PRIVATE', 1048597);
define ('CURL_VERSION_IPV6', 1);
define ('CURL_VERSION_KERBEROS4', 2);
define ('CURL_VERSION_SSL', 4);
define ('CURL_VERSION_LIBZ', 8);
define ('CURLVERSION_NOW', 2);
define ('CURLE_OK', 0);
define ('CURLE_UNSUPPORTED_PROTOCOL', 1);
define ('CURLE_FAILED_INIT', 2);
define ('CURLE_URL_MALFORMAT', 3);
define ('CURLE_URL_MALFORMAT_USER', 4);
define ('CURLE_COULDNT_RESOLVE_PROXY', 5);
define ('CURLE_COULDNT_RESOLVE_HOST', 6);
define ('CURLE_COULDNT_CONNECT', 7);
define ('CURLE_FTP_WEIRD_SERVER_REPLY', 8);
define ('CURLE_FTP_ACCESS_DENIED', 9);
define ('CURLE_FTP_USER_PASSWORD_INCORRECT', 10);
define ('CURLE_FTP_WEIRD_PASS_REPLY', 11);
define ('CURLE_FTP_WEIRD_USER_REPLY', 12);
define ('CURLE_FTP_WEIRD_PASV_REPLY', 13);
define ('CURLE_FTP_WEIRD_227_FORMAT', 14);
define ('CURLE_FTP_CANT_GET_HOST', 15);
define ('CURLE_FTP_CANT_RECONNECT', 16);
define ('CURLE_FTP_COULDNT_SET_BINARY', 17);
define ('CURLE_PARTIAL_FILE', 18);
define ('CURLE_FTP_COULDNT_RETR_FILE', 19);
define ('CURLE_FTP_WRITE_ERROR', 20);
define ('CURLE_FTP_QUOTE_ERROR', 21);
define ('CURLE_HTTP_NOT_FOUND', 22);
define ('CURLE_WRITE_ERROR', 23);
define ('CURLE_MALFORMAT_USER', 24);
define ('CURLE_FTP_COULDNT_STOR_FILE', 25);
define ('CURLE_READ_ERROR', 26);
define ('CURLE_OUT_OF_MEMORY', 27);
define ('CURLE_OPERATION_TIMEOUTED', 28);
define ('CURLE_FTP_COULDNT_SET_ASCII', 29);
define ('CURLE_FTP_PORT_FAILED', 30);
define ('CURLE_FTP_COULDNT_USE_REST', 31);
define ('CURLE_FTP_COULDNT_GET_SIZE', 32);
define ('CURLE_HTTP_RANGE_ERROR', 33);
define ('CURLE_HTTP_POST_ERROR', 34);
define ('CURLE_SSL_CONNECT_ERROR', 35);
define ('CURLE_FTP_BAD_DOWNLOAD_RESUME', 36);
define ('CURLE_FILE_COULDNT_READ_FILE', 37);
define ('CURLE_LDAP_CANNOT_BIND', 38);
define ('CURLE_LDAP_SEARCH_FAILED', 39);
define ('CURLE_LIBRARY_NOT_FOUND', 40);
define ('CURLE_FUNCTION_NOT_FOUND', 41);
define ('CURLE_ABORTED_BY_CALLBACK', 42);
define ('CURLE_BAD_FUNCTION_ARGUMENT', 43);
define ('CURLE_BAD_CALLING_ORDER', 44);
define ('CURLE_HTTP_PORT_FAILED', 45);
define ('CURLE_BAD_PASSWORD_ENTERED', 46);
define ('CURLE_TOO_MANY_REDIRECTS', 47);
define ('CURLE_UNKNOWN_TELNET_OPTION', 48);
define ('CURLE_TELNET_OPTION_SYNTAX', 49);
define ('CURLE_OBSOLETE', 50);
define ('CURLE_SSL_PEER_CERTIFICATE', 51);
define ('CURLE_GOT_NOTHING', 52);
define ('CURLE_SSL_ENGINE_NOTFOUND', 53);
define ('CURLE_SSL_ENGINE_SETFAILED', 54);
define ('CURLE_SEND_ERROR', 55);
define ('CURLE_RECV_ERROR', 56);
define ('CURLE_SHARE_IN_USE', 57);
define ('CURLE_SSL_CERTPROBLEM', 58);
define ('CURLE_SSL_CIPHER', 59);
define ('CURLE_SSL_CACERT', 60);
define ('CURLE_BAD_CONTENT_ENCODING', 61);
define ('CURLE_LDAP_INVALID_URL', 62);
define ('CURLE_FILESIZE_EXCEEDED', 63);
define ('CURLE_FTP_SSL_FAILED', 64);
define ('CURLPROXY_HTTP', 0);
define ('CURLPROXY_SOCKS5', 5);
define ('CURL_NETRC_OPTIONAL', 1);
define ('CURL_NETRC_IGNORED', 0);
define ('CURL_NETRC_REQUIRED', 2);
define ('CURL_HTTP_VERSION_NONE', 0);
define ('CURL_HTTP_VERSION_1_0', 1);
define ('CURL_HTTP_VERSION_1_1', 2);
define ('CURLM_CALL_MULTI_PERFORM', -1);
define ('CURLM_OK', 0);
define ('CURLM_BAD_HANDLE', 1);
define ('CURLM_BAD_EASY_HANDLE', 2);
define ('CURLM_OUT_OF_MEMORY', 3);
define ('CURLM_INTERNAL_ERROR', 4);
define ('CURLMSG_DONE', 1);
define ('CURLOPT_FTPSSLAUTH', 129);
define ('CURLFTPAUTH_DEFAULT', 0);
define ('CURLFTPAUTH_SSL', 1);
define ('CURLFTPAUTH_TLS', 2);
define ('CURLOPT_FTP_SSL', 119);
define ('CURLFTPSSL_NONE', 0);
define ('CURLFTPSSL_TRY', 1);
define ('CURLFTPSSL_CONTROL', 2);
define ('CURLFTPSSL_ALL', 3);

// End of curl v.

// Start of ftp v.

/**
 * Opens an FTP connection
 * @link http://php.net/manual/en/function.ftp-connect.php
 * @param host string
 * @param port int[optional]
 * @param timeout int[optional]
 * @return resource a FTP stream on success or false on error.
 */
function ftp_connect ($host, $port = null, $timeout = null) {}

/**
 * Opens an Secure SSL-FTP connection
 * @link http://php.net/manual/en/function.ftp-ssl-connect.php
 * @param host string
 * @param port int[optional]
 * @param timeout int[optional]
 * @return resource a SSL-FTP stream on success or false on error.
 */
function ftp_ssl_connect ($host, $port = null, $timeout = null) {}

/**
 * Logs in to an FTP connection
 * @link http://php.net/manual/en/function.ftp-login.php
 * @param ftp_stream resource
 * @param username string
 * @param password string
 * @return bool
 */
function ftp_login ($ftp_stream, $username, $password) {}

/**
 * Returns the current directory name
 * @link http://php.net/manual/en/function.ftp-pwd.php
 * @param ftp_stream resource
 * @return string the current directory name or false on error.
 */
function ftp_pwd ($ftp_stream) {}

/**
 * Changes to the parent directory
 * @link http://php.net/manual/en/function.ftp-cdup.php
 * @param ftp_stream resource
 * @return bool
 */
function ftp_cdup ($ftp_stream) {}

/**
 * Changes the current directory on a FTP server
 * @link http://php.net/manual/en/function.ftp-chdir.php
 * @param ftp_stream resource
 * @param directory string
 * @return bool
 */
function ftp_chdir ($ftp_stream, $directory) {}

/**
 * Requests execution of a command on the FTP server
 * @link http://php.net/manual/en/function.ftp-exec.php
 * @param ftp_stream resource
 * @param command string
 * @return bool true if the command was successful (server sent response code:
 */
function ftp_exec ($ftp_stream, $command) {}

/**
 * Sends an arbitrary command to an FTP server
 * @link http://php.net/manual/en/function.ftp-raw.php
 * @param ftp_stream resource
 * @param command string
 * @return array the server's response as an array of strings.
 */
function ftp_raw ($ftp_stream, $command) {}

/**
 * Creates a directory
 * @link http://php.net/manual/en/function.ftp-mkdir.php
 * @param ftp_stream resource
 * @param directory string
 * @return string the newly created directory name on success or false on error.
 */
function ftp_mkdir ($ftp_stream, $directory) {}

/**
 * Removes a directory
 * @link http://php.net/manual/en/function.ftp-rmdir.php
 * @param ftp_stream resource
 * @param directory string
 * @return bool
 */
function ftp_rmdir ($ftp_stream, $directory) {}

/**
 * Set permissions on a file via FTP
 * @link http://php.net/manual/en/function.ftp-chmod.php
 * @param ftp_stream resource
 * @param mode int
 * @param filename string
 * @return int the new file permissions on success or false on error.
 */
function ftp_chmod ($ftp_stream, $mode, $filename) {}

/**
 * Allocates space for a file to be uploaded
 * @link http://php.net/manual/en/function.ftp-alloc.php
 * @param ftp_stream resource
 * @param filesize int
 * @param result string[optional]
 * @return bool
 */
function ftp_alloc ($ftp_stream, $filesize, &$result = null) {}

/**
 * Returns a list of files in the given directory
 * @link http://php.net/manual/en/function.ftp-nlist.php
 * @param ftp_stream resource
 * @param directory string
 * @return array an array of filenames from the specified directory on success or
 */
function ftp_nlist ($ftp_stream, $directory) {}

/**
 * Returns a detailed list of files in the given directory
 * @link http://php.net/manual/en/function.ftp-rawlist.php
 * @param ftp_stream resource
 * @param directory string
 * @param recursive bool[optional]
 * @return array an array where each element corresponds to one line of text.
 */
function ftp_rawlist ($ftp_stream, $directory, $recursive = null) {}

/**
 * Returns the system type identifier of the remote FTP server
 * @link http://php.net/manual/en/function.ftp-systype.php
 * @param ftp_stream resource
 * @return string the remote system type, or false on error.
 */
function ftp_systype ($ftp_stream) {}

/**
 * Turns passive mode on or off
 * @link http://php.net/manual/en/function.ftp-pasv.php
 * @param ftp_stream resource
 * @param pasv bool
 * @return bool
 */
function ftp_pasv ($ftp_stream, $pasv) {}

/**
 * Downloads a file from the FTP server
 * @link http://php.net/manual/en/function.ftp-get.php
 * @param ftp_stream resource
 * @param local_file string
 * @param remote_file string
 * @param mode int
 * @param resumepos int[optional]
 * @return bool
 */
function ftp_get ($ftp_stream, $local_file, $remote_file, $mode, $resumepos = null) {}

/**
 * Downloads a file from the FTP server and saves to an open file
 * @link http://php.net/manual/en/function.ftp-fget.php
 * @param ftp_stream resource
 * @param handle resource
 * @param remote_file string
 * @param mode int
 * @param resumepos int[optional]
 * @return bool
 */
function ftp_fget ($ftp_stream, $handle, $remote_file, $mode, $resumepos = null) {}

/**
 * Uploads a file to the FTP server
 * @link http://php.net/manual/en/function.ftp-put.php
 * @param ftp_stream resource
 * @param remote_file string
 * @param local_file string
 * @param mode int
 * @param startpos int[optional]
 * @return bool
 */
function ftp_put ($ftp_stream, $remote_file, $local_file, $mode, $startpos = null) {}

/**
 * Uploads from an open file to the FTP server
 * @link http://php.net/manual/en/function.ftp-fput.php
 * @param ftp_stream resource
 * @param remote_file string
 * @param handle resource
 * @param mode int
 * @param startpos int[optional]
 * @return bool
 */
function ftp_fput ($ftp_stream, $remote_file, $handle, $mode, $startpos = null) {}

/**
 * Returns the size of the given file
 * @link http://php.net/manual/en/function.ftp-size.php
 * @param ftp_stream resource
 * @param remote_file string
 * @return int the file size on success, or -1 on error.
 */
function ftp_size ($ftp_stream, $remote_file) {}

/**
 * Returns the last modified time of the given file
 * @link http://php.net/manual/en/function.ftp-mdtm.php
 * @param ftp_stream resource
 * @param remote_file string
 * @return int the last modified time as a Unix timestamp on success, or -1 on 
 */
function ftp_mdtm ($ftp_stream, $remote_file) {}

/**
 * Renames a file or a directory on the FTP server
 * @link http://php.net/manual/en/function.ftp-rename.php
 * @param ftp_stream resource
 * @param oldname string
 * @param newname string
 * @return bool
 */
function ftp_rename ($ftp_stream, $oldname, $newname) {}

/**
 * Deletes a file on the FTP server
 * @link http://php.net/manual/en/function.ftp-delete.php
 * @param ftp_stream resource
 * @param path string
 * @return bool
 */
function ftp_delete ($ftp_stream, $path) {}

/**
 * Sends a SITE command to the server
 * @link http://php.net/manual/en/function.ftp-site.php
 * @param ftp_stream resource
 * @param command string
 * @return bool
 */
function ftp_site ($ftp_stream, $command) {}

/**
 * Closes an FTP connection
 * @link http://php.net/manual/en/function.ftp-close.php
 * @param ftp_stream resource
 * @return bool
 */
function ftp_close ($ftp_stream) {}

/**
 * Set miscellaneous runtime FTP options
 * @link http://php.net/manual/en/function.ftp-set-option.php
 * @param ftp_stream resource
 * @param option int
 * @param value mixed
 * @return bool true if the option could be set; false if not. A warning
 */
function ftp_set_option ($ftp_stream, $option, $value) {}

/**
 * Retrieves various runtime behaviours of the current FTP stream
 * @link http://php.net/manual/en/function.ftp-get-option.php
 * @param ftp_stream resource
 * @param option int
 * @return mixed the value on success or false if the given 
 */
function ftp_get_option ($ftp_stream, $option) {}

/**
 * Retrieves a file from the FTP server and writes it to an open file (non-blocking)
 * @link http://php.net/manual/en/function.ftp-nb-fget.php
 * @param ftp_stream resource
 * @param handle resource
 * @param remote_file string
 * @param mode int
 * @param resumepos int[optional]
 * @return int FTP_FAILED or FTP_FINISHED
 */
function ftp_nb_fget ($ftp_stream, $handle, $remote_file, $mode, $resumepos = null) {}

/**
 * Retrieves a file from the FTP server and writes it to a local file (non-blocking)
 * @link http://php.net/manual/en/function.ftp-nb-get.php
 * @param ftp_stream resource
 * @param local_file string
 * @param remote_file string
 * @param mode int
 * @param resumepos int[optional]
 * @return int FTP_FAILED or FTP_FINISHED
 */
function ftp_nb_get ($ftp_stream, $local_file, $remote_file, $mode, $resumepos = null) {}

/**
 * Continues retrieving/sending a file (non-blocking)
 * @link http://php.net/manual/en/function.ftp-nb-continue.php
 * @param ftp_stream resource
 * @return int FTP_FAILED or FTP_FINISHED
 */
function ftp_nb_continue ($ftp_stream) {}

/**
 * Stores a file on the FTP server (non-blocking)
 * @link http://php.net/manual/en/function.ftp-nb-put.php
 * @param ftp_stream resource
 * @param remote_file string
 * @param local_file string
 * @param mode int
 * @param startpos int[optional]
 * @return int FTP_FAILED or FTP_FINISHED
 */
function ftp_nb_put ($ftp_stream, $remote_file, $local_file, $mode, $startpos = null) {}

/**
 * Stores a file from an open file to the FTP server (non-blocking)
 * @link http://php.net/manual/en/function.ftp-nb-fput.php
 * @param ftp_stream resource
 * @param remote_file string
 * @param handle resource
 * @param mode int
 * @param startpos int[optional]
 * @return int FTP_FAILED or FTP_FINISHED
 */
function ftp_nb_fput ($ftp_stream, $remote_file, $handle, $mode, $startpos = null) {}

/**
 * &Alias; <function>ftp_close</function>
 * @link http://php.net/manual/en/function.ftp-quit.php
 * @param ftp
 */
function ftp_quit ($ftp) {}

define ('FTP_ASCII', 1);
define ('FTP_TEXT', 1);
define ('FTP_BINARY', 2);
define ('FTP_IMAGE', 2);
define ('FTP_AUTORESUME', -1);
define ('FTP_TIMEOUT_SEC', 0);
define ('FTP_AUTOSEEK', 1);
define ('FTP_FAILED', 0);
define ('FTP_FINISHED', 1);
define ('FTP_MOREDATA', 2);

// End of ftp v.

// Start of ldap v.

/**
 * Connect to an LDAP server
 * @link http://php.net/manual/en/function.ldap-connect.php
 * @param hostname string[optional]
 * @param port int[optional]
 * @return resource a positive LDAP link identifier on success, or false on error.
 */
function ldap_connect ($hostname = null, $port = null) {}

/**
 * &Alias; <function>ldap_unbind</function>
 * @link http://php.net/manual/en/function.ldap-close.php
 */
function ldap_close () {}

/**
 * Bind to LDAP directory
 * @link http://php.net/manual/en/function.ldap-bind.php
 * @param link_identifier resource
 * @param bind_rdn string[optional]
 * @param bind_password string[optional]
 * @return bool
 */
function ldap_bind ($link_identifier, $bind_rdn = null, $bind_password = null) {}

/**
 * Unbind from LDAP directory
 * @link http://php.net/manual/en/function.ldap-unbind.php
 * @param link_identifier resource
 * @return bool
 */
function ldap_unbind ($link_identifier) {}

/**
 * Read an entry
 * @link http://php.net/manual/en/function.ldap-read.php
 * @param link_identifier resource
 * @param base_dn string
 * @param filter string
 * @param attributes array[optional]
 * @param attrsonly int[optional]
 * @param sizelimit int[optional]
 * @param timelimit int[optional]
 * @param deref int[optional]
 * @return resource a search result identifier or false on error.
 */
function ldap_read ($link_identifier, $base_dn, $filter, array $attributes = null, $attrsonly = null, $sizelimit = null, $timelimit = null, $deref = null) {}

/**
 * Single-level search
 * @link http://php.net/manual/en/function.ldap-list.php
 * @param link_identifier resource
 * @param base_dn string
 * @param filter string
 * @param attributes array[optional]
 * @param attrsonly int[optional]
 * @param sizelimit int[optional]
 * @param timelimit int[optional]
 * @param deref int[optional]
 * @return resource a search result identifier or false on error.
 */
function ldap_list ($link_identifier, $base_dn, $filter, array $attributes = null, $attrsonly = null, $sizelimit = null, $timelimit = null, $deref = null) {}

/**
 * Search LDAP tree
 * @link http://php.net/manual/en/function.ldap-search.php
 * @param link_identifier resource
 * @param base_dn string
 * @param filter string
 * @param attributes array[optional]
 * @param attrsonly int[optional]
 * @param sizelimit int[optional]
 * @param timelimit int[optional]
 * @param deref int[optional]
 * @return resource a search result identifier or false on error.
 */
function ldap_search ($link_identifier, $base_dn, $filter, array $attributes = null, $attrsonly = null, $sizelimit = null, $timelimit = null, $deref = null) {}

/**
 * Free result memory
 * @link http://php.net/manual/en/function.ldap-free-result.php
 * @param result_identifier resource
 * @return bool
 */
function ldap_free_result ($result_identifier) {}

/**
 * Count the number of entries in a search
 * @link http://php.net/manual/en/function.ldap-count-entries.php
 * @param link_identifier resource
 * @param result_identifier resource
 * @return int number of entries in the result or false on error.
 */
function ldap_count_entries ($link_identifier, $result_identifier) {}

/**
 * Return first result id
 * @link http://php.net/manual/en/function.ldap-first-entry.php
 * @param link_identifier resource
 * @param result_identifier resource
 * @return resource the result entry identifier for the first entry on success and
 */
function ldap_first_entry ($link_identifier, $result_identifier) {}

/**
 * Get next result entry
 * @link http://php.net/manual/en/function.ldap-next-entry.php
 * @param link_identifier resource
 * @param result_entry_identifier resource
 * @return resource entry identifier for the next entry in the result whose entries
 */
function ldap_next_entry ($link_identifier, $result_entry_identifier) {}

/**
 * Get all result entries
 * @link http://php.net/manual/en/function.ldap-get-entries.php
 * @param link_identifier resource
 * @param result_identifier resource
 * @return array a complete result information in a multi-dimensional array on
 */
function ldap_get_entries ($link_identifier, $result_identifier) {}

/**
 * Return first attribute
 * @link http://php.net/manual/en/function.ldap-first-attribute.php
 * @param link_identifier resource
 * @param result_entry_identifier resource
 * @param ber_identifier int
 * @return string the first attribute in the entry on success and false on
 */
function ldap_first_attribute ($link_identifier, $result_entry_identifier, &$ber_identifier) {}

/**
 * Get the next attribute in result
 * @link http://php.net/manual/en/function.ldap-next-attribute.php
 * @param link_identifier resource
 * @param result_entry_identifier resource
 * @param ber_identifier resource
 * @return string the next attribute in an entry on success and false on
 */
function ldap_next_attribute ($link_identifier, $result_entry_identifier, &$ber_identifier) {}

/**
 * Get attributes from a search result entry
 * @link http://php.net/manual/en/function.ldap-get-attributes.php
 * @param link_identifier resource
 * @param result_entry_identifier resource
 * @return array a complete entry information in a multi-dimensional array
 */
function ldap_get_attributes ($link_identifier, $result_entry_identifier) {}

/**
 * Get all values from a result entry
 * @link http://php.net/manual/en/function.ldap-get-values.php
 * @param link_identifier resource
 * @param result_entry_identifier resource
 * @param attribute string
 * @return array an array of values for the attribute on success and false on
 */
function ldap_get_values ($link_identifier, $result_entry_identifier, $attribute) {}

/**
 * Get all binary values from a result entry
 * @link http://php.net/manual/en/function.ldap-get-values-len.php
 * @param link_identifier resource
 * @param result_entry_identifier resource
 * @param attribute string
 * @return array an array of values for the attribute on success and false on
 */
function ldap_get_values_len ($link_identifier, $result_entry_identifier, $attribute) {}

/**
 * Get the DN of a result entry
 * @link http://php.net/manual/en/function.ldap-get-dn.php
 * @param link_identifier resource
 * @param result_entry_identifier resource
 * @return string the DN of the result entry and false on error.
 */
function ldap_get_dn ($link_identifier, $result_entry_identifier) {}

/**
 * Splits DN into its component parts
 * @link http://php.net/manual/en/function.ldap-explode-dn.php
 * @param dn string
 * @param with_attrib int
 * @return array an array of all DN components.
 */
function ldap_explode_dn ($dn, $with_attrib) {}

/**
 * Convert DN to User Friendly Naming format
 * @link http://php.net/manual/en/function.ldap-dn2ufn.php
 * @param dn string
 * @return string the user friendly name.
 */
function ldap_dn2ufn ($dn) {}

/**
 * Add entries to LDAP directory
 * @link http://php.net/manual/en/function.ldap-add.php
 * @param link_identifier resource
 * @param dn string
 * @param entry array
 * @return bool
 */
function ldap_add ($link_identifier, $dn, array $entry) {}

/**
 * Delete an entry from a directory
 * @link http://php.net/manual/en/function.ldap-delete.php
 * @param link_identifier resource
 * @param dn string
 * @return bool
 */
function ldap_delete ($link_identifier, $dn) {}

/**
 * Modify an LDAP entry
 * @link http://php.net/manual/en/function.ldap-modify.php
 * @param link_identifier resource
 * @param dn string
 * @param entry array
 * @return bool
 */
function ldap_modify ($link_identifier, $dn, array $entry) {}

/**
 * Add attribute values to current attributes
 * @link http://php.net/manual/en/function.ldap-mod-add.php
 * @param link_identifier resource
 * @param dn string
 * @param entry array
 * @return bool
 */
function ldap_mod_add ($link_identifier, $dn, array $entry) {}

/**
 * Replace attribute values with new ones
 * @link http://php.net/manual/en/function.ldap-mod-replace.php
 * @param link_identifier resource
 * @param dn string
 * @param entry array
 * @return bool
 */
function ldap_mod_replace ($link_identifier, $dn, array $entry) {}

/**
 * Delete attribute values from current attributes
 * @link http://php.net/manual/en/function.ldap-mod-del.php
 * @param link_identifier resource
 * @param dn string
 * @param entry array
 * @return bool
 */
function ldap_mod_del ($link_identifier, $dn, array $entry) {}

/**
 * Return the LDAP error number of the last LDAP command
 * @link http://php.net/manual/en/function.ldap-errno.php
 * @param link_identifier resource
 * @return int
 */
function ldap_errno ($link_identifier) {}

/**
 * Convert LDAP error number into string error message
 * @link http://php.net/manual/en/function.ldap-err2str.php
 * @param errno int
 * @return string the error message, as a string.
 */
function ldap_err2str ($errno) {}

/**
 * Return the LDAP error message of the last LDAP command
 * @link http://php.net/manual/en/function.ldap-error.php
 * @param link_identifier resource
 * @return string string error message.
 */
function ldap_error ($link_identifier) {}

/**
 * Compare value of attribute found in entry specified with DN
 * @link http://php.net/manual/en/function.ldap-compare.php
 * @param link_identifier resource
 * @param dn string
 * @param attribute string
 * @param value string
 * @return mixed true if value matches otherwise returns
 */
function ldap_compare ($link_identifier, $dn, $attribute, $value) {}

/**
 * Sort LDAP result entries
 * @link http://php.net/manual/en/function.ldap-sort.php
 * @param link resource
 * @param result resource
 * @param sortfilter string
 * @return bool
 */
function ldap_sort ($link, $result, $sortfilter) {}

/**
 * Modify the name of an entry
 * @link http://php.net/manual/en/function.ldap-rename.php
 * @param link_identifier resource
 * @param dn string
 * @param newrdn string
 * @param newparent string
 * @param deleteoldrdn bool
 * @return bool
 */
function ldap_rename ($link_identifier, $dn, $newrdn, $newparent, $deleteoldrdn) {}

/**
 * Get the current value for given option
 * @link http://php.net/manual/en/function.ldap-get-option.php
 * @param link_identifier resource
 * @param option int
 * @param retval mixed
 * @return bool
 */
function ldap_get_option ($link_identifier, $option, &$retval) {}

/**
 * Set the value of the given option
 * @link http://php.net/manual/en/function.ldap-set-option.php
 * @param link_identifier resource
 * @param option int
 * @param newval mixed
 * @return bool
 */
function ldap_set_option ($link_identifier, $option, $newval) {}

/**
 * Return first reference
 * @link http://php.net/manual/en/function.ldap-first-reference.php
 * @param link resource
 * @param result resource
 * @return resource
 */
function ldap_first_reference ($link, $result) {}

/**
 * Get next reference
 * @link http://php.net/manual/en/function.ldap-next-reference.php
 * @param link resource
 * @param entry resource
 * @return resource
 */
function ldap_next_reference ($link, $entry) {}

/**
 * Extract information from reference entry
 * @link http://php.net/manual/en/function.ldap-parse-reference.php
 * @param link resource
 * @param entry resource
 * @param referrals array
 * @return bool
 */
function ldap_parse_reference ($link, $entry, array &$referrals) {}

/**
 * Extract information from result
 * @link http://php.net/manual/en/function.ldap-parse-result.php
 * @param link resource
 * @param result resource
 * @param errcode int
 * @param matcheddn string[optional]
 * @param errmsg string[optional]
 * @param referrals array[optional]
 * @return bool
 */
function ldap_parse_result ($link, $result, &$errcode, &$matcheddn = null, &$errmsg = null, array &$referrals = null) {}

/**
 * Start TLS
 * @link http://php.net/manual/en/function.ldap-start-tls.php
 * @param link resource
 * @return bool
 */
function ldap_start_tls ($link) {}

/**
 * Set a callback function to do re-binds on referral chasing
 * @link http://php.net/manual/en/function.ldap-set-rebind-proc.php
 * @param link resource
 * @param callback callback
 * @return bool
 */
function ldap_set_rebind_proc ($link, $callback) {}

define ('LDAP_DEREF_NEVER', 0);
define ('LDAP_DEREF_SEARCHING', 1);
define ('LDAP_DEREF_FINDING', 2);
define ('LDAP_DEREF_ALWAYS', 3);
define ('LDAP_OPT_DEREF', 2);
define ('LDAP_OPT_SIZELIMIT', 3);
define ('LDAP_OPT_TIMELIMIT', 4);
define ('LDAP_OPT_PROTOCOL_VERSION', 17);
define ('LDAP_OPT_ERROR_NUMBER', 49);
define ('LDAP_OPT_REFERRALS', 8);
define ('LDAP_OPT_RESTART', 9);
define ('LDAP_OPT_HOST_NAME', 48);
define ('LDAP_OPT_ERROR_STRING', 50);
define ('LDAP_OPT_MATCHED_DN', 51);
define ('LDAP_OPT_SERVER_CONTROLS', 18);
define ('LDAP_OPT_CLIENT_CONTROLS', 19);
define ('LDAP_OPT_DEBUG_LEVEL', 20481);

// End of ldap v.

// Start of mhash v.

/**
 * Get the block size of the specified hash
 * @link http://php.net/manual/en/function.mhash-get-block-size.php
 * @param hash int
 * @return int the size in bytes or false, if the hash
 */
function mhash_get_block_size ($hash) {}

/**
 * Get the name of the specified hash
 * @link http://php.net/manual/en/function.mhash-get-hash-name.php
 * @param hash int
 * @return string the name of the hash or false, if the hash does not exist.
 */
function mhash_get_hash_name ($hash) {}

/**
 * Generates a key
 * @link http://php.net/manual/en/function.mhash-keygen-s2k.php
 * @param hash int
 * @param password string
 * @param salt string
 * @param bytes int
 * @return string the generated key as a string, or false on error.
 */
function mhash_keygen_s2k ($hash, $password, $salt, $bytes) {}

/**
 * Get the highest available hash id
 * @link http://php.net/manual/en/function.mhash-count.php
 * @return int the highest available hash id. Hashes are numbered from 0 to this
 */
function mhash_count () {}

/**
 * Compute hash
 * @link http://php.net/manual/en/function.mhash.php
 * @param hash int
 * @param data string
 * @param key string[optional]
 * @return string the resulting hash (also called digest) or HMAC as a string, or
 */
function mhash ($hash, $data, $key = null) {}

define ('MHASH_CRC32', 0);
define ('MHASH_MD5', 1);
define ('MHASH_SHA1', 2);
define ('MHASH_HAVAL256', 3);
define ('MHASH_RIPEMD160', 5);
define ('MHASH_TIGER', 7);
define ('MHASH_GOST', 8);
define ('MHASH_CRC32B', 9);
define ('MHASH_HAVAL224', 10);
define ('MHASH_HAVAL192', 11);
define ('MHASH_HAVAL160', 12);
define ('MHASH_HAVAL128', 13);
define ('MHASH_TIGER128', 14);
define ('MHASH_TIGER160', 15);
define ('MHASH_MD4', 16);
define ('MHASH_SHA256', 17);
define ('MHASH_ADLER32', 18);
define ('MHASH_SHA224', 19);
define ('MHASH_SHA512', 20);
define ('MHASH_SHA384', 21);
define ('MHASH_WHIRLPOOL', 22);
define ('MHASH_RIPEMD128', 23);
define ('MHASH_RIPEMD256', 24);
define ('MHASH_RIPEMD320', 25);
define ('MHASH_SNEFRU128', 26);
define ('MHASH_SNEFRU256', 27);
define ('MHASH_MD2', 28);

// End of mhash v.

// Start of mysql v.1.0

/**
 * Open a connection to a MySQL Server
 * @link http://php.net/manual/en/function.mysql-connect.php
 * @param server string[optional]
 * @param username string[optional]
 * @param password string[optional]
 * @param new_link bool[optional]
 * @param client_flags int[optional]
 * @return resource a MySQL link identifier on success, or false on failure.
 */
function mysql_connect ($server = null, $username = null, $password = null, $new_link = null, $client_flags = null) {}

/**
 * Open a persistent connection to a MySQL server
 * @link http://php.net/manual/en/function.mysql-pconnect.php
 * @param server string[optional]
 * @param username string[optional]
 * @param password string[optional]
 * @param client_flags int[optional]
 * @return resource a MySQL persistent link identifier on success, or false on 
 */
function mysql_pconnect ($server = null, $username = null, $password = null, $client_flags = null) {}

/**
 * Close MySQL connection
 * @link http://php.net/manual/en/function.mysql-close.php
 * @param link_identifier resource[optional]
 * @return bool
 */
function mysql_close ($link_identifier = null) {}

/**
 * Select a MySQL database
 * @link http://php.net/manual/en/function.mysql-select-db.php
 * @param database_name string
 * @param link_identifier resource[optional]
 * @return bool
 */
function mysql_select_db ($database_name, $link_identifier = null) {}

/**
 * Send a MySQL query
 * @link http://php.net/manual/en/function.mysql-query.php
 * @param query string
 * @param link_identifier resource[optional]
 * @return resource
 */
function mysql_query ($query, $link_identifier = null) {}

/**
 * Send an SQL query to MySQL, without fetching and buffering the result rows
 * @link http://php.net/manual/en/function.mysql-unbuffered-query.php
 * @param query string
 * @param link_identifier resource[optional]
 * @return resource
 */
function mysql_unbuffered_query ($query, $link_identifier = null) {}

/**
 * Send a MySQL query
 * @link http://php.net/manual/en/function.mysql-db-query.php
 * @param database string
 * @param query string
 * @param link_identifier resource[optional]
 * @return resource a positive MySQL result resource to the query result,
 */
function mysql_db_query ($database, $query, $link_identifier = null) {}

/**
 * List databases available on a MySQL server
 * @link http://php.net/manual/en/function.mysql-list-dbs.php
 * @param link_identifier resource[optional]
 * @return resource a result pointer resource on success, or false on
 */
function mysql_list_dbs ($link_identifier = null) {}

/**
 * List tables in a MySQL database
 * @link http://php.net/manual/en/function.mysql-list-tables.php
 * @param database string
 * @param link_identifier resource[optional]
 * @return resource
 */
function mysql_list_tables ($database, $link_identifier = null) {}

/**
 * List MySQL table fields
 * @link http://php.net/manual/en/function.mysql-list-fields.php
 * @param database_name string
 * @param table_name string
 * @param link_identifier resource[optional]
 * @return resource
 */
function mysql_list_fields ($database_name, $table_name, $link_identifier = null) {}

/**
 * List MySQL processes
 * @link http://php.net/manual/en/function.mysql-list-processes.php
 * @param link_identifier resource[optional]
 * @return resource
 */
function mysql_list_processes ($link_identifier = null) {}

/**
 * Returns the text of the error message from previous MySQL operation
 * @link http://php.net/manual/en/function.mysql-error.php
 * @param link_identifier resource[optional]
 * @return string the error text from the last MySQL function, or
 */
function mysql_error ($link_identifier = null) {}

/**
 * Returns the numerical value of the error message from previous MySQL operation
 * @link http://php.net/manual/en/function.mysql-errno.php
 * @param link_identifier resource[optional]
 * @return int the error number from the last MySQL function, or
 */
function mysql_errno ($link_identifier = null) {}

/**
 * Get number of affected rows in previous MySQL operation
 * @link http://php.net/manual/en/function.mysql-affected-rows.php
 * @param link_identifier resource[optional]
 * @return int the number of affected rows on success, and -1 if the last query
 */
function mysql_affected_rows ($link_identifier = null) {}

/**
 * Get the ID generated from the previous INSERT operation
 * @link http://php.net/manual/en/function.mysql-insert-id.php
 * @param link_identifier resource[optional]
 * @return int
 */
function mysql_insert_id ($link_identifier = null) {}

/**
 * Get result data
 * @link http://php.net/manual/en/function.mysql-result.php
 * @param result resource
 * @param row int
 * @param field mixed[optional]
 * @return string
 */
function mysql_result ($result, $row, $field = null) {}

/**
 * Get number of rows in result
 * @link http://php.net/manual/en/function.mysql-num-rows.php
 * @param result resource
 * @return int
 */
function mysql_num_rows ($result) {}

/**
 * Get number of fields in result
 * @link http://php.net/manual/en/function.mysql-num-fields.php
 * @param result resource
 * @return int the number of fields in the result set resource on
 */
function mysql_num_fields ($result) {}

/**
 * Get a result row as an enumerated array
 * @link http://php.net/manual/en/function.mysql-fetch-row.php
 * @param result resource
 * @return array an numerical array of strings that corresponds to the fetched row, or 
 */
function mysql_fetch_row ($result) {}

/**
 * Fetch a result row as an associative array, a numeric array, or both
 * @link http://php.net/manual/en/function.mysql-fetch-array.php
 * @param result resource
 * @param result_type int[optional]
 * @return array an array of strings that corresponds to the fetched row, or false
 */
function mysql_fetch_array ($result, $result_type = null) {}

/**
 * Fetch a result row as an associative array
 * @link http://php.net/manual/en/function.mysql-fetch-assoc.php
 * @param result resource
 * @return array an associative array of strings that corresponds to the fetched row, or 
 */
function mysql_fetch_assoc ($result) {}

/**
 * Fetch a result row as an object
 * @link http://php.net/manual/en/function.mysql-fetch-object.php
 * @param result resource
 * @param class_name string[optional]
 * @param params array[optional]
 * @return object an object with string properties that correspond to the
 */
function mysql_fetch_object ($result, $class_name = null, array $params = null) {}

/**
 * Move internal result pointer
 * @link http://php.net/manual/en/function.mysql-data-seek.php
 * @param result resource
 * @param row_number int
 * @return bool
 */
function mysql_data_seek ($result, $row_number) {}

/**
 * Get the length of each output in a result
 * @link http://php.net/manual/en/function.mysql-fetch-lengths.php
 * @param result resource
 * @return array
 */
function mysql_fetch_lengths ($result) {}

/**
 * Get column information from a result and return as an object
 * @link http://php.net/manual/en/function.mysql-fetch-field.php
 * @param result resource
 * @param field_offset int[optional]
 * @return object an object containing field information. The properties 
 */
function mysql_fetch_field ($result, $field_offset = null) {}

/**
 * Set result pointer to a specified field offset
 * @link http://php.net/manual/en/function.mysql-field-seek.php
 * @param result resource
 * @param field_offset int
 * @return bool
 */
function mysql_field_seek ($result, $field_offset) {}

/**
 * Free result memory
 * @link http://php.net/manual/en/function.mysql-free-result.php
 * @param result resource
 * @return bool
 */
function mysql_free_result ($result) {}

/**
 * Get the name of the specified field in a result
 * @link http://php.net/manual/en/function.mysql-field-name.php
 * @param result resource
 * @param field_offset int
 * @return string
 */
function mysql_field_name ($result, $field_offset) {}

/**
 * Get name of the table the specified field is in
 * @link http://php.net/manual/en/function.mysql-field-table.php
 * @param result resource
 * @param field_offset int
 * @return string
 */
function mysql_field_table ($result, $field_offset) {}

/**
 * Returns the length of the specified field
 * @link http://php.net/manual/en/function.mysql-field-len.php
 * @param result resource
 * @param field_offset int
 * @return int
 */
function mysql_field_len ($result, $field_offset) {}

/**
 * Get the type of the specified field in a result
 * @link http://php.net/manual/en/function.mysql-field-type.php
 * @param result resource
 * @param field_offset int
 * @return string
 */
function mysql_field_type ($result, $field_offset) {}

/**
 * Get the flags associated with the specified field in a result
 * @link http://php.net/manual/en/function.mysql-field-flags.php
 * @param result resource
 * @param field_offset int
 * @return string a string of flags associated with the result, or false on failure.
 */
function mysql_field_flags ($result, $field_offset) {}

/**
 * Escapes a string for use in a mysql_query
 * @link http://php.net/manual/en/function.mysql-escape-string.php
 * @param unescaped_string string
 * @return string the escaped string.
 */
function mysql_escape_string ($unescaped_string) {}

/**
 * Escapes special characters in a string for use in a SQL statement
 * @link http://php.net/manual/en/function.mysql-real-escape-string.php
 * @param unescaped_string string
 * @param link_identifier resource[optional]
 * @return string the escaped string, or false on error.
 */
function mysql_real_escape_string ($unescaped_string, $link_identifier = null) {}

/**
 * Get current system status
 * @link http://php.net/manual/en/function.mysql-stat.php
 * @param link_identifier resource[optional]
 * @return string a string with the status for uptime, threads, queries, open tables, 
 */
function mysql_stat ($link_identifier = null) {}

/**
 * Return the current thread ID
 * @link http://php.net/manual/en/function.mysql-thread-id.php
 * @param link_identifier resource[optional]
 * @return int
 */
function mysql_thread_id ($link_identifier = null) {}

/**
 * Returns the name of the character set
 * @link http://php.net/manual/en/function.mysql-client-encoding.php
 * @param link_identifier resource[optional]
 * @return string the default character set name for the current connection.
 */
function mysql_client_encoding ($link_identifier = null) {}

/**
 * Ping a server connection or reconnect if there is no connection
 * @link http://php.net/manual/en/function.mysql-ping.php
 * @param link_identifier resource[optional]
 * @return bool true if the connection to the server MySQL server is working, 
 */
function mysql_ping ($link_identifier = null) {}

/**
 * Get MySQL client info
 * @link http://php.net/manual/en/function.mysql-get-client-info.php
 * @return string
 */
function mysql_get_client_info () {}

/**
 * Get MySQL host info
 * @link http://php.net/manual/en/function.mysql-get-host-info.php
 * @param link_identifier resource[optional]
 * @return string a string describing the type of MySQL connection in use for the 
 */
function mysql_get_host_info ($link_identifier = null) {}

/**
 * Get MySQL protocol info
 * @link http://php.net/manual/en/function.mysql-get-proto-info.php
 * @param link_identifier resource[optional]
 * @return int the MySQL protocol on success, or false on failure.
 */
function mysql_get_proto_info ($link_identifier = null) {}

/**
 * Get MySQL server info
 * @link http://php.net/manual/en/function.mysql-get-server-info.php
 * @param link_identifier resource[optional]
 * @return string the MySQL server version on success, or false on failure.
 */
function mysql_get_server_info ($link_identifier = null) {}

/**
 * Get information about the most recent query
 * @link http://php.net/manual/en/function.mysql-info.php
 * @param link_identifier resource[optional]
 * @return string information about the statement on success, or false on
 */
function mysql_info ($link_identifier = null) {}

/**
 * Sets the client character set
 * @link http://php.net/manual/en/function.mysql-set-charset.php
 * @param charset string
 * @param link_identifier resource[optional]
 * @return bool
 */
function mysql_set_charset ($charset, $link_identifier = null) {}

function mysql () {}

function mysql_fieldname () {}

function mysql_fieldtable () {}

function mysql_fieldlen () {}

function mysql_fieldtype () {}

function mysql_fieldflags () {}

function mysql_selectdb () {}

function mysql_freeresult () {}

function mysql_numfields () {}

function mysql_numrows () {}

function mysql_listdbs () {}

function mysql_listtables () {}

function mysql_listfields () {}

/**
 * Get result data
 * @link http://php.net/manual/en/function.mysql-db-name.php
 * @param result resource
 * @param row int
 * @param field mixed[optional]
 * @return string the database name on success, and false on failure. If false
 */
function mysql_db_name ($result, $row, $field = null) {}

function mysql_dbname () {}

/**
 * Get table name of field
 * @link http://php.net/manual/en/function.mysql-tablename.php
 * @param result resource
 * @param i int
 * @return string
 */
function mysql_tablename ($result, $i) {}

function mysql_table_name () {}

define ('MYSQL_ASSOC', 1);
define ('MYSQL_NUM', 2);
define ('MYSQL_BOTH', 3);
define ('MYSQL_CLIENT_COMPRESS', 32);
define ('MYSQL_CLIENT_SSL', 2048);
define ('MYSQL_CLIENT_INTERACTIVE', 1024);
define ('MYSQL_CLIENT_IGNORE_SPACE', 256);

// End of mysql v.1.0

// Start of PDO v.1.0.4dev

/**
 * Represents an error raised by PDO.  You should not throw a
 *       PDOException from your own code.
 *       See Exceptions for more
 *       information about Exceptions in PHP.
 * @link http://php.net/manual/en/ref.pdo.php
 */
class PDOException extends RuntimeException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	public $errorInfo;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * Represents a connection between PHP and a database server.
 * @link http://php.net/manual/en/ref.pdo.php
 */
class PDO  {
	const PARAM_BOOL = 5;
	const PARAM_NULL = 0;
	const PARAM_INT = 1;
	const PARAM_STR = 2;
	const PARAM_LOB = 3;
	const PARAM_STMT = 4;
	const PARAM_INPUT_OUTPUT = 2147483648;
	const PARAM_EVT_ALLOC = 0;
	const PARAM_EVT_FREE = 1;
	const PARAM_EVT_EXEC_PRE = 2;
	const PARAM_EVT_EXEC_POST = 3;
	const PARAM_EVT_FETCH_PRE = 4;
	const PARAM_EVT_FETCH_POST = 5;
	const PARAM_EVT_NORMALIZE = 6;
	const FETCH_LAZY = 1;
	const FETCH_ASSOC = 2;
	const FETCH_NUM = 3;
	const FETCH_BOTH = 4;
	const FETCH_OBJ = 5;
	const FETCH_BOUND = 6;
	const FETCH_COLUMN = 7;
	const FETCH_CLASS = 8;
	const FETCH_INTO = 9;
	const FETCH_FUNC = 10;
	const FETCH_GROUP = 65536;
	const FETCH_UNIQUE = 196608;
	const FETCH_KEY_PAIR = 12;
	const FETCH_CLASSTYPE = 262144;
	const FETCH_SERIALIZE = 524288;
	const FETCH_PROPS_LATE = 1048576;
	const FETCH_NAMED = 11;
	const ATTR_AUTOCOMMIT = 0;
	const ATTR_PREFETCH = 1;
	const ATTR_TIMEOUT = 2;
	const ATTR_ERRMODE = 3;
	const ATTR_SERVER_VERSION = 4;
	const ATTR_CLIENT_VERSION = 5;
	const ATTR_SERVER_INFO = 6;
	const ATTR_CONNECTION_STATUS = 7;
	const ATTR_CASE = 8;
	const ATTR_CURSOR_NAME = 9;
	const ATTR_CURSOR = 10;
	const ATTR_ORACLE_NULLS = 11;
	const ATTR_PERSISTENT = 12;
	const ATTR_STATEMENT_CLASS = 13;
	const ATTR_FETCH_TABLE_NAMES = 14;
	const ATTR_FETCH_CATALOG_NAMES = 15;
	const ATTR_DRIVER_NAME = 16;
	const ATTR_STRINGIFY_FETCHES = 17;
	const ATTR_MAX_COLUMN_LEN = 18;
	const ATTR_EMULATE_PREPARES = 20;
	const ATTR_DEFAULT_FETCH_MODE = 19;
	const ERRMODE_SILENT = 0;
	const ERRMODE_WARNING = 1;
	const ERRMODE_EXCEPTION = 2;
	const CASE_NATURAL = 0;
	const CASE_LOWER = 2;
	const CASE_UPPER = 1;
	const NULL_NATURAL = 0;
	const NULL_EMPTY_STRING = 1;
	const NULL_TO_STRING = 2;
	const ERR_NONE = 00000;
	const FETCH_ORI_NEXT = 0;
	const FETCH_ORI_PRIOR = 1;
	const FETCH_ORI_FIRST = 2;
	const FETCH_ORI_LAST = 3;
	const FETCH_ORI_ABS = 4;
	const FETCH_ORI_REL = 5;
	const CURSOR_FWDONLY = 0;
	const CURSOR_SCROLL = 1;
	const MYSQL_ATTR_USE_BUFFERED_QUERY = 1000;
	const MYSQL_ATTR_LOCAL_INFILE = 1001;
	const MYSQL_ATTR_INIT_COMMAND = 1002;
	const MYSQL_ATTR_READ_DEFAULT_FILE = 1003;
	const MYSQL_ATTR_READ_DEFAULT_GROUP = 1004;
	const MYSQL_ATTR_MAX_BUFFER_SIZE = 1005;
	const MYSQL_ATTR_DIRECT_QUERY = 1006;
	const PGSQL_ATTR_DISABLE_NATIVE_PREPARED_STATEMENT = 1000;


	/**
	 * Creates a PDO instance representing a connection to a database
	 * @link http://php.net/manual/en/function.PDO-construct.php
	 * @param dsn string
	 * @param username string[optional]
	 * @param password string[optional]
	 * @param driver_options array[optional]
	 * @return PDO a PDO object on success.
	 */
	public function __construct ($dsn, $username = null, $password = null, array $driver_options = null) {}

	/**
	 * Prepares a statement for execution and returns a statement object
	 * @link http://php.net/manual/en/function.PDO-prepare.php
	 * @param statement string
	 * @param driver_options array[optional]
	 * @return PDOStatement
	 */
	public function prepare ($statement, array $driver_options = null) {}

	/**
	 * Initiates a transaction
	 * @link http://php.net/manual/en/function.PDO-beginTransaction.php
	 * @return bool
	 */
	public function beginTransaction () {}

	/**
	 * Commits a transaction
	 * @link http://php.net/manual/en/function.PDO-commit.php
	 * @return bool
	 */
	public function commit () {}

	/**
	 * Rolls back a transaction
	 * @link http://php.net/manual/en/function.PDO-rollBack.php
	 * @return bool
	 */
	public function rollBack () {}

	/**
	 * Set an attribute
	 * @link http://php.net/manual/en/function.PDO-setAttribute.php
	 * @param attribute int
	 * @param value mixed
	 * @return bool
	 */
	public function setAttribute ($attribute, $value) {}

	/**
	 * Execute an SQL statement and return the number of affected rows
	 * @link http://php.net/manual/en/function.PDO-exec.php
	 * @param statement string
	 * @return int
	 */
	public function exec ($statement) {}

	/**
	 * Executes an SQL statement, returning a result set as a PDOStatement object
	 * @link http://php.net/manual/en/function.PDO-query.php
	 * @param statement string
	 * @param PDO::FETCH_INTO int
	 * @param object object
	 * @return PDOStatement
	 */
	public function query ($statement, $PDO::FETCH_INTO, $object) {}

	/**
	 * Returns the ID of the last inserted row or sequence value
	 * @link http://php.net/manual/en/function.PDO-lastInsertId.php
	 * @param name string[optional]
	 * @return string
	 */
	public function lastInsertId ($name = null) {}

	/**
	 * Fetch the SQLSTATE associated with the last operation on the database handle
	 * @link http://php.net/manual/en/function.PDO-errorCode.php
	 * @return string a SQLSTATE, a five-character alphanumeric identifier defined in
	 */
	public function errorCode () {}

	/**
	 * Fetch extended error information associated with the last operation on the database handle
	 * @link http://php.net/manual/en/function.PDO-errorInfo.php
	 * @return array
	 */
	public function errorInfo () {}

	/**
	 * Retrieve a database connection attribute
	 * @link http://php.net/manual/en/function.PDO-getAttribute.php
	 * @param attribute int
	 * @return mixed
	 */
	public function getAttribute ($attribute) {}

	/**
	 * Quotes a string for use in a query.
	 * @link http://php.net/manual/en/function.PDO-quote.php
	 * @param string string
	 * @param parameter_type int[optional]
	 * @return string a quoted string that is theoretically safe to pass into an
	 */
	public function quote ($string, $parameter_type = null) {}

	final public function __wakeup () {}

	final public function __sleep () {}

	/**
	 * Return an array of available PDO drivers
	 * @link http://php.net/manual/en/function.PDO-getAvailableDrivers.php
	 * @return array
	 */
	public static function getAvailableDrivers () {}

}

/**
 * Represents a prepared statement and, after the statement is executed, an 
 *       associated result set.
 * @link http://php.net/manual/en/ref.pdo.php
 */
class PDOStatement implements Traversable {
	public $queryString;


	/**
	 * Executes a prepared statement
	 * @link http://php.net/manual/en/function.PDOStatement-execute.php
	 * @param input_parameters array[optional]
	 * @return bool
	 */
	public function execute (array $input_parameters = null) {}

	/**
	 * Fetches the next row from a result set
	 * @link http://php.net/manual/en/function.PDOStatement-fetch.php
	 * @param fetch_style int[optional]
	 * @param cursor_orientation int[optional]
	 * @param cursor_offset int[optional]
	 * @return mixed
	 */
	public function fetch ($fetch_style = null, $cursor_orientation = null, $cursor_offset = null) {}

	/**
	 * Binds a parameter to the specified variable name
	 * @link http://php.net/manual/en/function.PDOStatement-bindParam.php
	 * @param parameter mixed
	 * @param variable mixed
	 * @param data_type int[optional]
	 * @param length int[optional]
	 * @param driver_options mixed[optional]
	 * @return bool
	 */
	public function bindParam ($parameter, &$variable, $data_type = null, $length = null, $driver_options = null) {}

	/**
	 * Bind a column to a PHP variable
	 * @link http://php.net/manual/en/function.PDOStatement-bindColumn.php
	 * @param column mixed
	 * @param param mixed
	 * @param type int[optional]
	 * @return bool
	 */
	public function bindColumn ($column, &$param, $type = null) {}

	/**
	 * Binds a value to a parameter
	 * @link http://php.net/manual/en/function.PDOStatement-bindValue.php
	 * @param parameter mixed
	 * @param value mixed
	 * @param data_type int[optional]
	 * @return bool
	 */
	public function bindValue ($parameter, $value, $data_type = null) {}

	/**
	 * Returns the number of rows affected by the last SQL statement
	 * @link http://php.net/manual/en/function.PDOStatement-rowCount.php
	 * @return int the number of rows.
	 */
	public function rowCount () {}

	/**
	 * Returns a single column from the next row of a result set
	 * @link http://php.net/manual/en/function.PDOStatement-fetchColumn.php
	 * @param column_number int[optional]
	 * @return string
	 */
	public function fetchColumn ($column_number = null) {}

	/**
	 * Returns an array containing all of the result set rows
	 * @link http://php.net/manual/en/function.PDOStatement-fetchAll.php
	 * @param fetch_style int[optional]
	 * @param column_index int[optional]
	 * @param ctor_args array[optional]
	 * @return array
	 */
	public function fetchAll ($fetch_style = null, $column_index = null, array $ctor_args = null) {}

	/**
	 * Fetches the next row and returns it as an object.
	 * @link http://php.net/manual/en/function.PDOStatement-fetchObject.php
	 * @param class_name string[optional]
	 * @param ctor_args array[optional]
	 * @return mixed an instance of the required class with property names that
	 */
	public function fetchObject ($class_name = null, array $ctor_args = null) {}

	/**
	 * Fetch the SQLSTATE associated with the last operation on the statement handle
	 * @link http://php.net/manual/en/function.PDOStatement-errorCode.php
	 * @return string
	 */
	public function errorCode () {}

	/**
	 * Fetch extended error information associated with the last operation on the statement handle
	 * @link http://php.net/manual/en/function.PDOStatement-errorInfo.php
	 * @return array
	 */
	public function errorInfo () {}

	/**
	 * Set a statement attribute
	 * @link http://php.net/manual/en/function.PDOStatement-setAttribute.php
	 * @param attribute int
	 * @param value mixed
	 * @return bool
	 */
	public function setAttribute ($attribute, $value) {}

	/**
	 * Retrieve a statement attribute
	 * @link http://php.net/manual/en/function.PDOStatement-getAttribute.php
	 * @param attribute int
	 * @return mixed the attribute value.
	 */
	public function getAttribute ($attribute) {}

	/**
	 * Returns the number of columns in the result set
	 * @link http://php.net/manual/en/function.PDOStatement-columnCount.php
	 * @return int the number of columns in the result set represented by the
	 */
	public function columnCount () {}

	/**
	 * Returns metadata for a column in a result set
	 * @link http://php.net/manual/en/function.PDOStatement-getColumnMeta.php
	 * @param column int
	 * @return array an associative array containing the following values representing
	 */
	public function getColumnMeta ($column) {}

	/**
	 * Set the default fetch mode for this statement
	 * @link http://php.net/manual/en/function.PDOStatement-setFetchMode.php
	 * @param PDO::FETCH_INTO int
	 * @param object object
	 * @return bool 1 on success or false on failure.
	 */
	public function setFetchMode ($PDO::FETCH_INTO, $object) {}

	/**
	 * Advances to the next rowset in a multi-rowset statement handle
	 * @link http://php.net/manual/en/function.PDOStatement-nextRowset.php
	 * @return bool
	 */
	public function nextRowset () {}

	/**
	 * Closes the cursor, enabling the statement to be executed again.
	 * @link http://php.net/manual/en/function.PDOStatement-closeCursor.php
	 * @return bool
	 */
	public function closeCursor () {}

	public function debugDumpParams () {}

	final public function __wakeup () {}

	final public function __sleep () {}

}

final class PDORow  {
}

function pdo_drivers () {}

// End of PDO v.1.0.4dev

// Start of posix v.

/**
 * Send a signal to a process
 * @link http://php.net/manual/en/function.posix-kill.php
 * @param pid int
 * @param sig int
 * @return bool
 */
function posix_kill ($pid, $sig) {}

/**
 * Return the current process identifier
 * @link http://php.net/manual/en/function.posix-getpid.php
 * @return int the identifier, as an integer.
 */
function posix_getpid () {}

/**
 * Return the parent process identifier
 * @link http://php.net/manual/en/function.posix-getppid.php
 * @return int the identifier, as an integer.
 */
function posix_getppid () {}

/**
 * Return the real user ID of the current process
 * @link http://php.net/manual/en/function.posix-getuid.php
 * @return int the user id, as an integer
 */
function posix_getuid () {}

/**
 * Set the UID of the current process
 * @link http://php.net/manual/en/function.posix-setuid.php
 * @param uid int
 * @return bool
 */
function posix_setuid ($uid) {}

/**
 * Return the effective user ID of the current process
 * @link http://php.net/manual/en/function.posix-geteuid.php
 * @return int the user id, as an integer
 */
function posix_geteuid () {}

/**
 * Set the effective UID of the current process
 * @link http://php.net/manual/en/function.posix-seteuid.php
 * @param uid int
 * @return bool
 */
function posix_seteuid ($uid) {}

/**
 * Return the real group ID of the current process
 * @link http://php.net/manual/en/function.posix-getgid.php
 * @return int the real group id, as an integer.
 */
function posix_getgid () {}

/**
 * Set the GID of the current process
 * @link http://php.net/manual/en/function.posix-setgid.php
 * @param gid int
 * @return bool
 */
function posix_setgid ($gid) {}

/**
 * Return the effective group ID of the current process
 * @link http://php.net/manual/en/function.posix-getegid.php
 * @return int an integer of the effective group ID.
 */
function posix_getegid () {}

/**
 * Set the effective GID of the current process
 * @link http://php.net/manual/en/function.posix-setegid.php
 * @param gid int
 * @return bool
 */
function posix_setegid ($gid) {}

/**
 * Return the group set of the current process
 * @link http://php.net/manual/en/function.posix-getgroups.php
 * @return array an array of integers containing the numeric group ids of the group
 */
function posix_getgroups () {}

/**
 * Return login name
 * @link http://php.net/manual/en/function.posix-getlogin.php
 * @return string the login name of the user, as a string.
 */
function posix_getlogin () {}

/**
 * Return the current process group identifier
 * @link http://php.net/manual/en/function.posix-getpgrp.php
 * @return int the identifier, as an integer.
 */
function posix_getpgrp () {}

/**
 * Make the current process a session leader
 * @link http://php.net/manual/en/function.posix-setsid.php
 * @return int the session id, or -1 on errors.
 */
function posix_setsid () {}

/**
 * Set process group id for job control
 * @link http://php.net/manual/en/function.posix-setpgid.php
 * @param pid int
 * @param pgid int
 * @return bool
 */
function posix_setpgid ($pid, $pgid) {}

/**
 * Get process group id for job control
 * @link http://php.net/manual/en/function.posix-getpgid.php
 * @param pid int
 * @return int the identifier, as an integer.
 */
function posix_getpgid ($pid) {}

/**
 * Get the current sid of the process
 * @link http://php.net/manual/en/function.posix-getsid.php
 * @param pid int
 * @return int the identifier, as an integer.
 */
function posix_getsid ($pid) {}

/**
 * Get system name
 * @link http://php.net/manual/en/function.posix-uname.php
 * @return array a hash of strings with information about the
 */
function posix_uname () {}

/**
 * Get process times
 * @link http://php.net/manual/en/function.posix-times.php
 * @return array a hash of strings with information about the current
 */
function posix_times () {}

/**
 * Get path name of controlling terminal
 * @link http://php.net/manual/en/function.posix-ctermid.php
 * @return string
 */
function posix_ctermid () {}

/**
 * Determine terminal device name
 * @link http://php.net/manual/en/function.posix-ttyname.php
 * @param fd int
 * @return string
 */
function posix_ttyname ($fd) {}

/**
 * Determine if a file descriptor is an interactive terminal
 * @link http://php.net/manual/en/function.posix-isatty.php
 * @param fd int
 * @return bool true if fd is an open descriptor connected
 */
function posix_isatty ($fd) {}

/**
 * Pathname of current directory
 * @link http://php.net/manual/en/function.posix-getcwd.php
 * @return string a string of the absolute pathname on success.  
 */
function posix_getcwd () {}

/**
 * Create a fifo special file (a named pipe)
 * @link http://php.net/manual/en/function.posix-mkfifo.php
 * @param pathname string
 * @param mode int
 * @return bool
 */
function posix_mkfifo ($pathname, $mode) {}

/**
 * Create a special or ordinary file (POSIX.1)
 * @link http://php.net/manual/en/function.posix-mknod.php
 * @param pathname string
 * @param mode int
 * @param major int[optional]
 * @param minor int[optional]
 * @return bool
 */
function posix_mknod ($pathname, $mode, $major = null, $minor = null) {}

/**
 * Determine accessibility of a file
 * @link http://php.net/manual/en/function.posix-access.php
 * @param file string
 * @param mode int[optional]
 * @return bool
 */
function posix_access ($file, $mode = null) {}

/**
 * Return info about a group by name
 * @link http://php.net/manual/en/function.posix-getgrnam.php
 * @param name string
 * @return array
 */
function posix_getgrnam ($name) {}

/**
 * Return info about a group by group id
 * @link http://php.net/manual/en/function.posix-getgrgid.php
 * @param gid int
 * @return array
 */
function posix_getgrgid ($gid) {}

/**
 * Return info about a user by username
 * @link http://php.net/manual/en/function.posix-getpwnam.php
 * @param username string
 * @return array
 */
function posix_getpwnam ($username) {}

/**
 * Return info about a user by user id
 * @link http://php.net/manual/en/function.posix-getpwuid.php
 * @param uid int
 * @return array an associative array with the following elements:
 */
function posix_getpwuid ($uid) {}

/**
 * Return info about system resource limits
 * @link http://php.net/manual/en/function.posix-getrlimit.php
 * @return array an associative array of elements for each
 */
function posix_getrlimit () {}

/**
 * Retrieve the error number set by the last posix function that failed
 * @link http://php.net/manual/en/function.posix-get-last-error.php
 * @return int the errno (error number) set by the last posix function that
 */
function posix_get_last_error () {}

function posix_errno () {}

/**
 * Retrieve the system error message associated with the given errno
 * @link http://php.net/manual/en/function.posix-strerror.php
 * @param errno int
 * @return string the error message, as a string.
 */
function posix_strerror ($errno) {}

/**
 * Calculate the group access list
 * @link http://php.net/manual/en/function.posix-initgroups.php
 * @param name string
 * @param base_group_id int
 * @return bool
 */
function posix_initgroups ($name, $base_group_id) {}

define ('POSIX_F_OK', 0);
define ('POSIX_X_OK', 1);
define ('POSIX_W_OK', 2);
define ('POSIX_R_OK', 4);
define ('POSIX_S_IFREG', 32768);
define ('POSIX_S_IFCHR', 8192);
define ('POSIX_S_IFBLK', 24576);
define ('POSIX_S_IFIFO', 4096);
define ('POSIX_S_IFSOCK', 49152);

// End of posix v.

// Start of sockets v.

/**
 * Runs the select() system call on the given arrays of sockets with a specified timeout
 * @link http://php.net/manual/en/function.socket-select.php
 * @param read array
 * @param write array
 * @param except array
 * @param tv_sec int
 * @param tv_usec int[optional]
 * @return int
 */
function socket_select (array &$read, array &$write, array &$except, $tv_sec, $tv_usec = null) {}

/**
 * Create a socket (endpoint for communication)
 * @link http://php.net/manual/en/function.socket-create.php
 * @param domain int
 * @param type int
 * @param protocol int
 * @return resource
 */
function socket_create ($domain, $type, $protocol) {}

/**
 * Opens a socket on port to accept connections
 * @link http://php.net/manual/en/function.socket-create-listen.php
 * @param port int
 * @param backlog int[optional]
 * @return resource
 */
function socket_create_listen ($port, $backlog = null) {}

/**
 * Creates a pair of indistinguishable sockets and stores them in an array
 * @link http://php.net/manual/en/function.socket-create-pair.php
 * @param domain int
 * @param type int
 * @param protocol int
 * @param fd array
 * @return bool
 */
function socket_create_pair ($domain, $type, $protocol, array &$fd) {}

/**
 * Accepts a connection on a socket
 * @link http://php.net/manual/en/function.socket-accept.php
 * @param socket resource
 * @return resource a new socket resource on success, or false on error. The actual
 */
function socket_accept ($socket) {}

/**
 * Sets nonblocking mode for file descriptor fd
 * @link http://php.net/manual/en/function.socket-set-nonblock.php
 * @param socket resource
 * @return bool
 */
function socket_set_nonblock ($socket) {}

/**
 * Sets blocking mode on a socket resource
 * @link http://php.net/manual/en/function.socket-set-block.php
 * @param socket resource
 * @return bool
 */
function socket_set_block ($socket) {}

/**
 * Listens for a connection on a socket
 * @link http://php.net/manual/en/function.socket-listen.php
 * @param socket resource
 * @param backlog int[optional]
 * @return bool
 */
function socket_listen ($socket, $backlog = null) {}

/**
 * Closes a socket resource
 * @link http://php.net/manual/en/function.socket-close.php
 * @param socket resource
 * @return void
 */
function socket_close ($socket) {}

/**
 * Write to a socket
 * @link http://php.net/manual/en/function.socket-write.php
 * @param socket resource
 * @param buffer string
 * @param length int[optional]
 * @return int the number of bytes successfully written to the socket or false
 */
function socket_write ($socket, $buffer, $length = null) {}

/**
 * Reads a maximum of length bytes from a socket
 * @link http://php.net/manual/en/function.socket-read.php
 * @param socket resource
 * @param length int
 * @param type int[optional]
 * @return string
 */
function socket_read ($socket, $length, $type = null) {}

/**
 * Queries the local side of the given socket which may either result in host/port or in a Unix filesystem path, dependent on its type
 * @link http://php.net/manual/en/function.socket-getsockname.php
 * @param socket resource
 * @param addr string
 * @param port int[optional]
 * @return bool
 */
function socket_getsockname ($socket, &$addr, &$port = null) {}

/**
 * Queries the remote side of the given socket which may either result in host/port or in a Unix filesystem path, dependent on its type
 * @link http://php.net/manual/en/function.socket-getpeername.php
 * @param socket resource
 * @param address string
 * @param port int[optional]
 * @return bool
 */
function socket_getpeername ($socket, &$address, &$port = null) {}

/**
 * Initiates a connection on a socket
 * @link http://php.net/manual/en/function.socket-connect.php
 * @param socket resource
 * @param address string
 * @param port int[optional]
 * @return bool
 */
function socket_connect ($socket, $address, $port = null) {}

/**
 * Return a string describing a socket error
 * @link http://php.net/manual/en/function.socket-strerror.php
 * @param errno int
 * @return string the error message associated with the errno
 */
function socket_strerror ($errno) {}

/**
 * Binds a name to a socket
 * @link http://php.net/manual/en/function.socket-bind.php
 * @param socket resource
 * @param address string
 * @param port int[optional]
 * @return bool
 */
function socket_bind ($socket, $address, $port = null) {}

/**
 * Receives data from a connected socket
 * @link http://php.net/manual/en/function.socket-recv.php
 * @param socket resource
 * @param buf string
 * @param len int
 * @param flags int
 * @return int
 */
function socket_recv ($socket, &$buf, $len, $flags) {}

/**
 * Sends data to a connected socket
 * @link http://php.net/manual/en/function.socket-send.php
 * @param socket resource
 * @param buf string
 * @param len int
 * @param flags int
 * @return int
 */
function socket_send ($socket, $buf, $len, $flags) {}

/**
 * Receives data from a socket whether or not it is connection-oriented
 * @link http://php.net/manual/en/function.socket-recvfrom.php
 * @param socket resource
 * @param buf string
 * @param len int
 * @param flags int
 * @param name string
 * @param port int[optional]
 * @return int
 */
function socket_recvfrom ($socket, &$buf, $len, $flags, &$name, &$port = null) {}

/**
 * Sends a message to a socket, whether it is connected or not
 * @link http://php.net/manual/en/function.socket-sendto.php
 * @param socket resource
 * @param buf string
 * @param len int
 * @param flags int
 * @param addr string
 * @param port int[optional]
 * @return int
 */
function socket_sendto ($socket, $buf, $len, $flags, $addr, $port = null) {}

/**
 * Gets socket options for the socket
 * @link http://php.net/manual/en/function.socket-get-option.php
 * @param socket resource
 * @param level int
 * @param optname int
 * @return mixed the value of the given option, or false on errors.
 */
function socket_get_option ($socket, $level, $optname) {}

/**
 * Sets socket options for the socket
 * @link http://php.net/manual/en/function.socket-set-option.php
 * @param socket resource
 * @param level int
 * @param optname int
 * @param optval mixed
 * @return bool
 */
function socket_set_option ($socket, $level, $optname, $optval) {}

/**
 * Shuts down a socket for receiving, sending, or both
 * @link http://php.net/manual/en/function.socket-shutdown.php
 * @param socket resource
 * @param how int[optional]
 * @return bool
 */
function socket_shutdown ($socket, $how = null) {}

/**
 * Returns the last error on the socket
 * @link http://php.net/manual/en/function.socket-last-error.php
 * @param socket resource[optional]
 * @return int
 */
function socket_last_error ($socket = null) {}

/**
 * Clears the error on the socket or the last error code
 * @link http://php.net/manual/en/function.socket-clear-error.php
 * @param socket resource[optional]
 * @return void
 */
function socket_clear_error ($socket = null) {}

function socket_getopt () {}

function socket_setopt () {}

define ('AF_UNIX', 1);
define ('AF_INET', 2);
define ('AF_INET6', 10);
define ('SOCK_STREAM', 1);
define ('SOCK_DGRAM', 2);
define ('SOCK_RAW', 3);
define ('SOCK_SEQPACKET', 5);
define ('SOCK_RDM', 4);
define ('MSG_OOB', 1);
define ('MSG_WAITALL', 256);
define ('MSG_PEEK', 2);
define ('MSG_DONTROUTE', 4);
define ('MSG_EOR', 128);
define ('MSG_EOF', 512);
define ('SO_DEBUG', 1);
define ('SO_REUSEADDR', 2);
define ('SO_KEEPALIVE', 9);
define ('SO_DONTROUTE', 5);
define ('SO_LINGER', 13);
define ('SO_BROADCAST', 6);
define ('SO_OOBINLINE', 10);
define ('SO_SNDBUF', 7);
define ('SO_RCVBUF', 8);
define ('SO_SNDLOWAT', 19);
define ('SO_RCVLOWAT', 18);
define ('SO_SNDTIMEO', 21);
define ('SO_RCVTIMEO', 20);
define ('SO_TYPE', 3);
define ('SO_ERROR', 4);
define ('SOL_SOCKET', 1);
define ('SOMAXCONN', 128);
define ('PHP_NORMAL_READ', 1);
define ('PHP_BINARY_READ', 2);
define ('SOCKET_EPERM', 1);
define ('SOCKET_ENOENT', 2);
define ('SOCKET_EINTR', 4);
define ('SOCKET_EIO', 5);
define ('SOCKET_ENXIO', 6);
define ('SOCKET_E2BIG', 7);
define ('SOCKET_EBADF', 9);
define ('SOCKET_EAGAIN', 11);
define ('SOCKET_ENOMEM', 12);
define ('SOCKET_EACCES', 13);
define ('SOCKET_EFAULT', 14);
define ('SOCKET_ENOTBLK', 15);
define ('SOCKET_EBUSY', 16);
define ('SOCKET_EEXIST', 17);
define ('SOCKET_EXDEV', 18);
define ('SOCKET_ENODEV', 19);
define ('SOCKET_ENOTDIR', 20);
define ('SOCKET_EISDIR', 21);
define ('SOCKET_EINVAL', 22);
define ('SOCKET_ENFILE', 23);
define ('SOCKET_EMFILE', 24);
define ('SOCKET_ENOTTY', 25);
define ('SOCKET_ENOSPC', 28);
define ('SOCKET_ESPIPE', 29);
define ('SOCKET_EROFS', 30);
define ('SOCKET_EMLINK', 31);
define ('SOCKET_EPIPE', 32);
define ('SOCKET_ENAMETOOLONG', 36);
define ('SOCKET_ENOLCK', 37);
define ('SOCKET_ENOSYS', 38);
define ('SOCKET_ENOTEMPTY', 39);
define ('SOCKET_ELOOP', 40);
define ('SOCKET_EWOULDBLOCK', 11);
define ('SOCKET_ENOMSG', 42);
define ('SOCKET_EIDRM', 43);
define ('SOCKET_ECHRNG', 44);
define ('SOCKET_EL2NSYNC', 45);
define ('SOCKET_EL3HLT', 46);
define ('SOCKET_EL3RST', 47);
define ('SOCKET_ELNRNG', 48);
define ('SOCKET_EUNATCH', 49);
define ('SOCKET_ENOCSI', 50);
define ('SOCKET_EL2HLT', 51);
define ('SOCKET_EBADE', 52);
define ('SOCKET_EBADR', 53);
define ('SOCKET_EXFULL', 54);
define ('SOCKET_ENOANO', 55);
define ('SOCKET_EBADRQC', 56);
define ('SOCKET_EBADSLT', 57);
define ('SOCKET_ENOSTR', 60);
define ('SOCKET_ENODATA', 61);
define ('SOCKET_ETIME', 62);
define ('SOCKET_ENOSR', 63);
define ('SOCKET_ENONET', 64);
define ('SOCKET_EREMOTE', 66);
define ('SOCKET_ENOLINK', 67);
define ('SOCKET_EADV', 68);
define ('SOCKET_ESRMNT', 69);
define ('SOCKET_ECOMM', 70);
define ('SOCKET_EPROTO', 71);
define ('SOCKET_EMULTIHOP', 72);
define ('SOCKET_EBADMSG', 74);
define ('SOCKET_ENOTUNIQ', 76);
define ('SOCKET_EBADFD', 77);
define ('SOCKET_EREMCHG', 78);
define ('SOCKET_ERESTART', 85);
define ('SOCKET_ESTRPIPE', 86);
define ('SOCKET_EUSERS', 87);
define ('SOCKET_ENOTSOCK', 88);
define ('SOCKET_EDESTADDRREQ', 89);
define ('SOCKET_EMSGSIZE', 90);
define ('SOCKET_EPROTOTYPE', 91);
define ('SOCKET_ENOPROTOOPT', 92);
define ('SOCKET_EPROTONOSUPPORT', 93);
define ('SOCKET_ESOCKTNOSUPPORT', 94);
define ('SOCKET_EOPNOTSUPP', 95);
define ('SOCKET_EPFNOSUPPORT', 96);
define ('SOCKET_EAFNOSUPPORT', 97);
define ('SOCKET_EADDRINUSE', 98);
define ('SOCKET_EADDRNOTAVAIL', 99);
define ('SOCKET_ENETDOWN', 100);
define ('SOCKET_ENETUNREACH', 101);
define ('SOCKET_ENETRESET', 102);
define ('SOCKET_ECONNABORTED', 103);
define ('SOCKET_ECONNRESET', 104);
define ('SOCKET_ENOBUFS', 105);
define ('SOCKET_EISCONN', 106);
define ('SOCKET_ENOTCONN', 107);
define ('SOCKET_ESHUTDOWN', 108);
define ('SOCKET_ETOOMANYREFS', 109);
define ('SOCKET_ETIMEDOUT', 110);
define ('SOCKET_ECONNREFUSED', 111);
define ('SOCKET_EHOSTDOWN', 112);
define ('SOCKET_EHOSTUNREACH', 113);
define ('SOCKET_EALREADY', 114);
define ('SOCKET_EINPROGRESS', 115);
define ('SOCKET_EISNAM', 120);
define ('SOCKET_EREMOTEIO', 121);
define ('SOCKET_EDQUOT', 122);
define ('SOCKET_ENOMEDIUM', 123);
define ('SOCKET_EMEDIUMTYPE', 124);
define ('SOL_TCP', 6);
define ('SOL_UDP', 17);

// End of sockets v.

// Start of sysvsem v.

/**
 * Get a semaphore id
 * @link http://php.net/manual/en/function.sem-get.php
 * @param key int
 * @param max_acquire int[optional]
 * @param perm int[optional]
 * @param auto_release int[optional]
 * @return resource a positive semaphore identifier on success, or false on
 */
function sem_get ($key, $max_acquire = null, $perm = null, $auto_release = null) {}

/**
 * Acquire a semaphore
 * @link http://php.net/manual/en/function.sem-acquire.php
 * @param sem_identifier resource
 * @return bool
 */
function sem_acquire ($sem_identifier) {}

/**
 * Release a semaphore
 * @link http://php.net/manual/en/function.sem-release.php
 * @param sem_identifier resource
 * @return bool
 */
function sem_release ($sem_identifier) {}

/**
 * Remove a semaphore
 * @link http://php.net/manual/en/function.sem-remove.php
 * @param sem_identifier resource
 * @return bool
 */
function sem_remove ($sem_identifier) {}

// End of sysvsem v.

// Start of tokenizer v.0.1

/**
 * Split given source into PHP tokens
 * @link http://php.net/manual/en/function.token-get-all.php
 * @param source string
 * @return array
 */
function token_get_all ($source) {}

/**
 * Get the symbolic name of a given PHP token
 * @link http://php.net/manual/en/function.token-name.php
 * @param token int
 * @return string
 */
function token_name ($token) {}

define ('T_REQUIRE_ONCE', 258);
define ('T_REQUIRE', 259);
define ('T_EVAL', 260);
define ('T_INCLUDE_ONCE', 261);
define ('T_INCLUDE', 262);
define ('T_LOGICAL_OR', 263);
define ('T_LOGICAL_XOR', 264);
define ('T_LOGICAL_AND', 265);
define ('T_PRINT', 266);
define ('T_SR_EQUAL', 267);
define ('T_SL_EQUAL', 268);
define ('T_XOR_EQUAL', 269);
define ('T_OR_EQUAL', 270);
define ('T_AND_EQUAL', 271);
define ('T_MOD_EQUAL', 272);
define ('T_CONCAT_EQUAL', 273);
define ('T_DIV_EQUAL', 274);
define ('T_MUL_EQUAL', 275);
define ('T_MINUS_EQUAL', 276);
define ('T_PLUS_EQUAL', 277);
define ('T_BOOLEAN_OR', 278);
define ('T_BOOLEAN_AND', 279);
define ('T_IS_NOT_IDENTICAL', 280);
define ('T_IS_IDENTICAL', 281);
define ('T_IS_NOT_EQUAL', 282);
define ('T_IS_EQUAL', 283);
define ('T_IS_GREATER_OR_EQUAL', 284);
define ('T_IS_SMALLER_OR_EQUAL', 285);
define ('T_SR', 286);
define ('T_SL', 287);
define ('T_INSTANCEOF', 288);
define ('T_UNSET_CAST', 289);
define ('T_BOOL_CAST', 290);
define ('T_OBJECT_CAST', 291);
define ('T_ARRAY_CAST', 292);
define ('T_STRING_CAST', 293);
define ('T_DOUBLE_CAST', 294);
define ('T_INT_CAST', 295);
define ('T_DEC', 296);
define ('T_INC', 297);
define ('T_CLONE', 298);
define ('T_NEW', 299);
define ('T_EXIT', 300);
define ('T_IF', 301);
define ('T_ELSEIF', 302);
define ('T_ELSE', 303);
define ('T_ENDIF', 304);
define ('T_LNUMBER', 305);
define ('T_DNUMBER', 306);
define ('T_STRING', 307);
define ('T_STRING_VARNAME', 308);
define ('T_VARIABLE', 309);
define ('T_NUM_STRING', 310);
define ('T_INLINE_HTML', 311);
define ('T_CHARACTER', 312);
define ('T_BAD_CHARACTER', 313);
define ('T_ENCAPSED_AND_WHITESPACE', 314);
define ('T_CONSTANT_ENCAPSED_STRING', 315);
define ('T_ECHO', 316);
define ('T_DO', 317);
define ('T_WHILE', 318);
define ('T_ENDWHILE', 319);
define ('T_FOR', 320);
define ('T_ENDFOR', 321);
define ('T_FOREACH', 322);
define ('T_ENDFOREACH', 323);
define ('T_DECLARE', 324);
define ('T_ENDDECLARE', 325);
define ('T_AS', 326);
define ('T_SWITCH', 327);
define ('T_ENDSWITCH', 328);
define ('T_CASE', 329);
define ('T_DEFAULT', 330);
define ('T_BREAK', 331);
define ('T_CONTINUE', 332);
define ('T_FUNCTION', 333);
define ('T_CONST', 334);
define ('T_RETURN', 335);
define ('T_TRY', 336);
define ('T_CATCH', 337);
define ('T_THROW', 338);
define ('T_USE', 339);
define ('T_GLOBAL', 340);
define ('T_PUBLIC', 341);
define ('T_PROTECTED', 342);
define ('T_PRIVATE', 343);
define ('T_FINAL', 344);
define ('T_ABSTRACT', 345);
define ('T_STATIC', 346);
define ('T_VAR', 347);
define ('T_UNSET', 348);
define ('T_ISSET', 349);
define ('T_EMPTY', 350);
define ('T_HALT_COMPILER', 351);
define ('T_CLASS', 352);
define ('T_INTERFACE', 353);
define ('T_EXTENDS', 354);
define ('T_IMPLEMENTS', 355);
define ('T_OBJECT_OPERATOR', 356);
define ('T_DOUBLE_ARROW', 357);
define ('T_LIST', 358);
define ('T_ARRAY', 359);
define ('T_CLASS_C', 360);
define ('T_METHOD_C', 361);
define ('T_FUNC_C', 362);
define ('T_LINE', 363);
define ('T_FILE', 364);
define ('T_COMMENT', 365);
define ('T_DOC_COMMENT', 366);
define ('T_OPEN_TAG', 367);
define ('T_OPEN_TAG_WITH_ECHO', 368);
define ('T_CLOSE_TAG', 369);
define ('T_WHITESPACE', 370);
define ('T_START_HEREDOC', 371);
define ('T_END_HEREDOC', 372);
define ('T_DOLLAR_OPEN_CURLY_BRACES', 373);
define ('T_CURLY_OPEN', 374);
define ('T_PAAMAYIM_NEKUDOTAYIM', 375);
define ('T_DOUBLE_COLON', 375);

// End of tokenizer v.0.1

// Start of xsl v.0.1

/**
 * @link http://php.net/manual/en/ref.xsl.php
 */
class XSLTProcessor  {

	/**
	 * Import stylesheet
	 * @link http://php.net/manual/en/function.xsl-xsltprocessor-import-stylesheet.php
	 * @param stylesheet DOMDocument
	 * @return void
	 */
	public function importStylesheet (DOMDocument $stylesheet) {}

	/**
	 * Transform to a DOMDocument
	 * @link http://php.net/manual/en/function.xsl-xsltprocessor-transform-to-doc.php
	 * @param doc DOMNode
	 * @return DOMDocument
	 */
	public function transformToDoc (DOMNode $doc) {}

	/**
	 * Transform to URI
	 * @link http://php.net/manual/en/function.xsl-xsltprocessor-transform-to-uri.php
	 * @param doc DOMDocument
	 * @param uri string
	 * @return int the number of bytes written or false if an error occurred.
	 */
	public function transformToUri (DOMDocument $doc, $uri) {}

	/**
	 * Transform to XML
	 * @link http://php.net/manual/en/function.xsl-xsltprocessor-transform-to-xml.php
	 * @param doc DOMDocument
	 * @return string
	 */
	public function transformToXml (DOMDocument $doc) {}

	/**
	 * Set value for a parameter
	 * @link http://php.net/manual/en/function.xsl-xsltprocessor-set-parameter.php
	 * @param namespace string
	 * @param options array
	 * @return bool
	 */
	public function setParameter ($namespace, array $options) {}

	/**
	 * Get value of a parameter
	 * @link http://php.net/manual/en/function.xsl-xsltprocessor-get-parameter.php
	 * @param namespaceURI string
	 * @param localName string
	 * @return string
	 */
	public function getParameter ($namespaceURI, $localName) {}

	/**
	 * Remove parameter
	 * @link http://php.net/manual/en/function.xsl-xsltprocessor-remove-parameter.php
	 * @param namespaceURI string
	 * @param localName string
	 * @return bool
	 */
	public function removeParameter ($namespaceURI, $localName) {}

	/**
	 * Determine if PHP has EXSLT support
	 * @link http://php.net/manual/en/function.xsl-xsltprocessor-has-exslt-support.php
	 * @return bool
	 */
	public function hasExsltSupport () {}

	/**
	 * Enables the ability to use PHP functions as XSLT functions
	 * @link http://php.net/manual/en/function.xsl-xsltprocessor-register-php-functions.php
	 * @param restrict mixed[optional]
	 * @return void
	 */
	public function registerPHPFunctions ($restrict = null) {}

}
define ('XSL_CLONE_AUTO', 0);
define ('XSL_CLONE_NEVER', -1);
define ('XSL_CLONE_ALWAYS', 1);
define ('LIBXSLT_VERSION', 10117);
define ('LIBXSLT_DOTTED_VERSION', '1.1.17');
define ('LIBEXSLT_VERSION', 813);
define ('LIBEXSLT_DOTTED_VERSION', '1.1.17');

// End of xsl v.0.1

// Start of bz2 v.

/**
 * Opens a bzip2 compressed file
 * @link http://php.net/manual/en/function.bzopen.php
 * @param filename string
 * @param mode string
 * @return resource
 */
function bzopen ($filename, $mode) {}

/**
 * Binary safe bzip2 file read
 * @link http://php.net/manual/en/function.bzread.php
 * @param bz resource
 * @param length int[optional]
 * @return string the uncompressed data, or false on error.
 */
function bzread ($bz, $length = null) {}

/**
 * Binary safe bzip2 file write
 * @link http://php.net/manual/en/function.bzwrite.php
 * @param bz resource
 * @param data string
 * @param length int[optional]
 * @return int the number of bytes written, or false on error.
 */
function bzwrite ($bz, $data, $length = null) {}

/**
 * Force a write of all buffered data
 * @link http://php.net/manual/en/function.bzflush.php
 * @param bz resource
 * @return int
 */
function bzflush ($bz) {}

/**
 * Close a bzip2 file
 * @link http://php.net/manual/en/function.bzclose.php
 * @param bz resource
 * @return int
 */
function bzclose ($bz) {}

/**
 * Returns a bzip2 error number
 * @link http://php.net/manual/en/function.bzerrno.php
 * @param bz resource
 * @return int the error number as an integer.
 */
function bzerrno ($bz) {}

/**
 * Returns a bzip2 error string
 * @link http://php.net/manual/en/function.bzerrstr.php
 * @param bz resource
 * @return string a string containing the error message.
 */
function bzerrstr ($bz) {}

/**
 * Returns the bzip2 error number and error string in an array
 * @link http://php.net/manual/en/function.bzerror.php
 * @param bz resource
 * @return array an associative array, with the error code in the 
 */
function bzerror ($bz) {}

/**
 * Compress a string into bzip2 encoded data
 * @link http://php.net/manual/en/function.bzcompress.php
 * @param source string
 * @param blocksize int[optional]
 * @param workfactor int[optional]
 * @return mixed
 */
function bzcompress ($source, $blocksize = null, $workfactor = null) {}

/**
 * Decompresses bzip2 encoded data
 * @link http://php.net/manual/en/function.bzdecompress.php
 * @param source string
 * @param small int[optional]
 * @return mixed
 */
function bzdecompress ($source, $small = null) {}

// End of bz2 v.

// Start of gd v.

/**
 * Retrieve information about the currently installed GD library
 * @link http://php.net/manual/en/function.gd-info.php
 * @return array an associative array.
 */
function gd_info () {}

/**
 * Draws an arc
 * @link http://php.net/manual/en/function.imagearc.php
 * @param image resource
 * @param cx int
 * @param cy int
 * @param width int
 * @param height int
 * @param start int
 * @param end int
 * @param color int
 * @return bool
 */
function imagearc ($image, $cx, $cy, $width, $height, $start, $end, $color) {}

/**
 * Draw an ellipse
 * @link http://php.net/manual/en/function.imageellipse.php
 * @param image resource
 * @param cx int
 * @param cy int
 * @param width int
 * @param height int
 * @param color int
 * @return bool
 */
function imageellipse ($image, $cx, $cy, $width, $height, $color) {}

/**
 * Draw a character horizontally
 * @link http://php.net/manual/en/function.imagechar.php
 * @param image resource
 * @param font int
 * @param x int
 * @param y int
 * @param c string
 * @param color int
 * @return bool
 */
function imagechar ($image, $font, $x, $y, $c, $color) {}

/**
 * Draw a character vertically
 * @link http://php.net/manual/en/function.imagecharup.php
 * @param image resource
 * @param font int
 * @param x int
 * @param y int
 * @param c string
 * @param color int
 * @return bool
 */
function imagecharup ($image, $font, $x, $y, $c, $color) {}

/**
 * Get the index of the color of a pixel
 * @link http://php.net/manual/en/function.imagecolorat.php
 * @param image resource
 * @param x int
 * @param y int
 * @return int the index of the color.
 */
function imagecolorat ($image, $x, $y) {}

/**
 * Allocate a color for an image
 * @link http://php.net/manual/en/function.imagecolorallocate.php
 * @param image resource
 * @param red int
 * @param green int
 * @param blue int
 * @return int
 */
function imagecolorallocate ($image, $red, $green, $blue) {}

/**
 * Copy the palette from one image to another
 * @link http://php.net/manual/en/function.imagepalettecopy.php
 * @param destination resource
 * @param source resource
 * @return void
 */
function imagepalettecopy ($destination, $source) {}

/**
 * Create a new image from the image stream in the string
 * @link http://php.net/manual/en/function.imagecreatefromstring.php
 * @param data string
 * @return resource
 */
function imagecreatefromstring ($data) {}

/**
 * Get the index of the closest color to the specified color
 * @link http://php.net/manual/en/function.imagecolorclosest.php
 * @param image resource
 * @param red int
 * @param green int
 * @param blue int
 * @return int the index of the closest color, in the palette of the image, to
 */
function imagecolorclosest ($image, $red, $green, $blue) {}

/**
 * Get the index of the color which has the hue, white and blackness nearest to the given color
 * @link http://php.net/manual/en/function.imagecolorclosesthwb.php
 * @param image resource
 * @param red int
 * @param green int
 * @param blue int
 * @return int
 */
function imagecolorclosesthwb ($image, $red, $green, $blue) {}

/**
 * De-allocate a color for an image
 * @link http://php.net/manual/en/function.imagecolordeallocate.php
 * @param image resource
 * @param color int
 * @return bool
 */
function imagecolordeallocate ($image, $color) {}

/**
 * Get the index of the specified color or its closest possible alternative
 * @link http://php.net/manual/en/function.imagecolorresolve.php
 * @param image resource
 * @param red int
 * @param green int
 * @param blue int
 * @return int a color index.
 */
function imagecolorresolve ($image, $red, $green, $blue) {}

/**
 * Get the index of the specified color
 * @link http://php.net/manual/en/function.imagecolorexact.php
 * @param image resource
 * @param red int
 * @param green int
 * @param blue int
 * @return int the index of the specified color in the palette, or -1 if the
 */
function imagecolorexact ($image, $red, $green, $blue) {}

/**
 * Set the color for the specified palette index
 * @link http://php.net/manual/en/function.imagecolorset.php
 * @param image resource
 * @param index int
 * @param red int
 * @param green int
 * @param blue int
 * @return void
 */
function imagecolorset ($image, $index, $red, $green, $blue) {}

/**
 * Define a color as transparent
 * @link http://php.net/manual/en/function.imagecolortransparent.php
 * @param image resource
 * @param color int[optional]
 * @return int
 */
function imagecolortransparent ($image, $color = null) {}

/**
 * Find out the number of colors in an image's palette
 * @link http://php.net/manual/en/function.imagecolorstotal.php
 * @param image resource
 * @return int the number of colors in the specified image's palette or 0 for
 */
function imagecolorstotal ($image) {}

/**
 * Get the colors for an index
 * @link http://php.net/manual/en/function.imagecolorsforindex.php
 * @param image resource
 * @param index int
 * @return array an associative array with red, green, blue and alpha keys that
 */
function imagecolorsforindex ($image, $index) {}

/**
 * Copy part of an image
 * @link http://php.net/manual/en/function.imagecopy.php
 * @param dst_im resource
 * @param src_im resource
 * @param dst_x int
 * @param dst_y int
 * @param src_x int
 * @param src_y int
 * @param src_w int
 * @param src_h int
 * @return bool
 */
function imagecopy ($dst_im, $src_im, $dst_x, $dst_y, $src_x, $src_y, $src_w, $src_h) {}

/**
 * Copy and merge part of an image
 * @link http://php.net/manual/en/function.imagecopymerge.php
 * @param dst_im resource
 * @param src_im resource
 * @param dst_x int
 * @param dst_y int
 * @param src_x int
 * @param src_y int
 * @param src_w int
 * @param src_h int
 * @param pct int
 * @return bool
 */
function imagecopymerge ($dst_im, $src_im, $dst_x, $dst_y, $src_x, $src_y, $src_w, $src_h, $pct) {}

/**
 * Copy and merge part of an image with gray scale
 * @link http://php.net/manual/en/function.imagecopymergegray.php
 * @param dst_im resource
 * @param src_im resource
 * @param dst_x int
 * @param dst_y int
 * @param src_x int
 * @param src_y int
 * @param src_w int
 * @param src_h int
 * @param pct int
 * @return bool
 */
function imagecopymergegray ($dst_im, $src_im, $dst_x, $dst_y, $src_x, $src_y, $src_w, $src_h, $pct) {}

/**
 * Copy and resize part of an image
 * @link http://php.net/manual/en/function.imagecopyresized.php
 * @param dst_image resource
 * @param src_image resource
 * @param dst_x int
 * @param dst_y int
 * @param src_x int
 * @param src_y int
 * @param dst_w int
 * @param dst_h int
 * @param src_w int
 * @param src_h int
 * @return bool
 */
function imagecopyresized ($dst_image, $src_image, $dst_x, $dst_y, $src_x, $src_y, $dst_w, $dst_h, $src_w, $src_h) {}

/**
 * Create a new palette based image
 * @link http://php.net/manual/en/function.imagecreate.php
 * @param width int
 * @param height int
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreate ($width, $height) {}

/**
 * Create a new true color image
 * @link http://php.net/manual/en/function.imagecreatetruecolor.php
 * @param width int
 * @param height int
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatetruecolor ($width, $height) {}

/**
 * Finds whether an image is a truecolor image
 * @link http://php.net/manual/en/function.imageistruecolor.php
 * @param image resource
 * @return bool true if the image is truecolor, false
 */
function imageistruecolor ($image) {}

/**
 * Convert a true color image to a palette image
 * @link http://php.net/manual/en/function.imagetruecolortopalette.php
 * @param image resource
 * @param dither bool
 * @param ncolors int
 * @return bool
 */
function imagetruecolortopalette ($image, $dither, $ncolors) {}

/**
 * Set the thickness for line drawing
 * @link http://php.net/manual/en/function.imagesetthickness.php
 * @param image resource
 * @param thickness int
 * @return bool
 */
function imagesetthickness ($image, $thickness) {}

/**
 * Draw a partial ellipse and fill it
 * @link http://php.net/manual/en/function.imagefilledarc.php
 * @param image resource
 * @param cx int
 * @param cy int
 * @param width int
 * @param height int
 * @param start int
 * @param end int
 * @param color int
 * @param style int
 * @return bool
 */
function imagefilledarc ($image, $cx, $cy, $width, $height, $start, $end, $color, $style) {}

/**
 * Draw a filled ellipse
 * @link http://php.net/manual/en/function.imagefilledellipse.php
 * @param image resource
 * @param cx int
 * @param cy int
 * @param width int
 * @param height int
 * @param color int
 * @return bool
 */
function imagefilledellipse ($image, $cx, $cy, $width, $height, $color) {}

/**
 * Set the blending mode for an image
 * @link http://php.net/manual/en/function.imagealphablending.php
 * @param image resource
 * @param blendmode bool
 * @return bool
 */
function imagealphablending ($image, $blendmode) {}

/**
 * Set the flag to save full alpha channel information (as opposed to single-color transparency) when saving PNG images
 * @link http://php.net/manual/en/function.imagesavealpha.php
 * @param image resource
 * @param saveflag bool
 * @return bool
 */
function imagesavealpha ($image, $saveflag) {}

/**
 * Allocate a color for an image
 * @link http://php.net/manual/en/function.imagecolorallocatealpha.php
 * @param image resource
 * @param red int
 * @param green int
 * @param blue int
 * @param alpha int
 * @return int
 */
function imagecolorallocatealpha ($image, $red, $green, $blue, $alpha) {}

/**
 * Get the index of the specified color + alpha or its closest possible alternative
 * @link http://php.net/manual/en/function.imagecolorresolvealpha.php
 * @param image resource
 * @param red int
 * @param green int
 * @param blue int
 * @param alpha int
 * @return int a color index.
 */
function imagecolorresolvealpha ($image, $red, $green, $blue, $alpha) {}

/**
 * Get the index of the closest color to the specified color + alpha
 * @link http://php.net/manual/en/function.imagecolorclosestalpha.php
 * @param image resource
 * @param red int
 * @param green int
 * @param blue int
 * @param alpha int
 * @return int the index of the closest color in the palette.
 */
function imagecolorclosestalpha ($image, $red, $green, $blue, $alpha) {}

/**
 * Get the index of the specified color + alpha
 * @link http://php.net/manual/en/function.imagecolorexactalpha.php
 * @param image resource
 * @param red int
 * @param green int
 * @param blue int
 * @param alpha int
 * @return int the index of the specified color+alpha in the palette of the
 */
function imagecolorexactalpha ($image, $red, $green, $blue, $alpha) {}

/**
 * Copy and resize part of an image with resampling
 * @link http://php.net/manual/en/function.imagecopyresampled.php
 * @param dst_image resource
 * @param src_image resource
 * @param dst_x int
 * @param dst_y int
 * @param src_x int
 * @param src_y int
 * @param dst_w int
 * @param dst_h int
 * @param src_w int
 * @param src_h int
 * @return bool
 */
function imagecopyresampled ($dst_image, $src_image, $dst_x, $dst_y, $src_x, $src_y, $dst_w, $dst_h, $src_w, $src_h) {}

/**
 * Rotate an image with a given angle
 * @link http://php.net/manual/en/function.imagerotate.php
 * @param source_image resource
 * @param angle float
 * @param bgd_color int
 * @param ignore_transparent int[optional]
 * @return resource
 */
function imagerotate ($source_image, $angle, $bgd_color, $ignore_transparent = null) {}

/**
 * Should antialias functions be used or not
 * @link http://php.net/manual/en/function.imageantialias.php
 * @param image resource
 * @param on bool
 * @return bool
 */
function imageantialias ($image, $on) {}

/**
 * Set the tile image for filling
 * @link http://php.net/manual/en/function.imagesettile.php
 * @param image resource
 * @param tile resource
 * @return bool
 */
function imagesettile ($image, $tile) {}

/**
 * Set the brush image for line drawing
 * @link http://php.net/manual/en/function.imagesetbrush.php
 * @param image resource
 * @param brush resource
 * @return bool
 */
function imagesetbrush ($image, $brush) {}

/**
 * Set the style for line drawing
 * @link http://php.net/manual/en/function.imagesetstyle.php
 * @param image resource
 * @param style array
 * @return bool
 */
function imagesetstyle ($image, array $style) {}

/**
 * Create a new image from file or URL
 * @link http://php.net/manual/en/function.imagecreatefrompng.php
 * @param filename string
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefrompng ($filename) {}

/**
 * Create a new image from file or URL
 * @link http://php.net/manual/en/function.imagecreatefromgif.php
 * @param filename string
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromgif ($filename) {}

/**
 * Create a new image from file or URL
 * @link http://php.net/manual/en/function.imagecreatefromjpeg.php
 * @param filename string
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromjpeg ($filename) {}

/**
 * Create a new image from file or URL
 * @link http://php.net/manual/en/function.imagecreatefromwbmp.php
 * @param filename string
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromwbmp ($filename) {}

/**
 * Create a new image from file or URL
 * @link http://php.net/manual/en/function.imagecreatefromxbm.php
 * @param filename string
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromxbm ($filename) {}

/**
 * Create a new image from GD file or URL
 * @link http://php.net/manual/en/function.imagecreatefromgd.php
 * @param filename string
 * @return resource
 */
function imagecreatefromgd ($filename) {}

/**
 * Create a new image from GD2 file or URL
 * @link http://php.net/manual/en/function.imagecreatefromgd2.php
 * @param filename string
 * @return resource
 */
function imagecreatefromgd2 ($filename) {}

/**
 * Create a new image from a given part of GD2 file or URL
 * @link http://php.net/manual/en/function.imagecreatefromgd2part.php
 * @param filename string
 * @param srcX int
 * @param srcY int
 * @param width int
 * @param height int
 * @return resource
 */
function imagecreatefromgd2part ($filename, $srcX, $srcY, $width, $height) {}

/**
 * Output a PNG image to either the browser or a file
 * @link http://php.net/manual/en/function.imagepng.php
 * @param image resource
 * @param filename string[optional]
 * @param quality int[optional]
 * @param filters int[optional]
 * @return bool
 */
function imagepng ($image, $filename = null, $quality = null, $filters = null) {}

/**
 * Output image to browser or file
 * @link http://php.net/manual/en/function.imagegif.php
 * @param image resource
 * @param filename string[optional]
 * @return bool
 */
function imagegif ($image, $filename = null) {}

/**
 * Output image to browser or file
 * @link http://php.net/manual/en/function.imagejpeg.php
 * @param image resource
 * @param filename string[optional]
 * @param quality int[optional]
 * @return bool
 */
function imagejpeg ($image, $filename = null, $quality = null) {}

/**
 * Output image to browser or file
 * @link http://php.net/manual/en/function.imagewbmp.php
 * @param image resource
 * @param filename string[optional]
 * @param foreground int[optional]
 * @return bool
 */
function imagewbmp ($image, $filename = null, $foreground = null) {}

/**
 * Output GD image to browser or file
 * @link http://php.net/manual/en/function.imagegd.php
 * @param image resource
 * @param filename string[optional]
 * @return bool
 */
function imagegd ($image, $filename = null) {}

/**
 * Output GD2 image to browser or file
 * @link http://php.net/manual/en/function.imagegd2.php
 * @param image resource
 * @param filename string[optional]
 * @param chunk_size int[optional]
 * @param type int[optional]
 * @return bool
 */
function imagegd2 ($image, $filename = null, $chunk_size = null, $type = null) {}

/**
 * Destroy an image
 * @link http://php.net/manual/en/function.imagedestroy.php
 * @param image resource
 * @return bool
 */
function imagedestroy ($image) {}

/**
 * Apply a gamma correction to a GD image
 * @link http://php.net/manual/en/function.imagegammacorrect.php
 * @param image resource
 * @param inputgamma float
 * @param outputgamma float
 * @return bool
 */
function imagegammacorrect ($image, $inputgamma, $outputgamma) {}

/**
 * Flood fill
 * @link http://php.net/manual/en/function.imagefill.php
 * @param image resource
 * @param x int
 * @param y int
 * @param color int
 * @return bool
 */
function imagefill ($image, $x, $y, $color) {}

/**
 * Draw a filled polygon
 * @link http://php.net/manual/en/function.imagefilledpolygon.php
 * @param image resource
 * @param points array
 * @param num_points int
 * @param color int
 * @return bool
 */
function imagefilledpolygon ($image, array $points, $num_points, $color) {}

/**
 * Draw a filled rectangle
 * @link http://php.net/manual/en/function.imagefilledrectangle.php
 * @param image resource
 * @param x1 int
 * @param y1 int
 * @param x2 int
 * @param y2 int
 * @param color int
 * @return bool
 */
function imagefilledrectangle ($image, $x1, $y1, $x2, $y2, $color) {}

/**
 * Flood fill to specific color
 * @link http://php.net/manual/en/function.imagefilltoborder.php
 * @param image resource
 * @param x int
 * @param y int
 * @param border int
 * @param color int
 * @return bool
 */
function imagefilltoborder ($image, $x, $y, $border, $color) {}

/**
 * Get font width
 * @link http://php.net/manual/en/function.imagefontwidth.php
 * @param font int
 * @return int the width of the pixel
 */
function imagefontwidth ($font) {}

/**
 * Get font height
 * @link http://php.net/manual/en/function.imagefontheight.php
 * @param font int
 * @return int the height of the pixel.
 */
function imagefontheight ($font) {}

/**
 * Enable or disable interlace
 * @link http://php.net/manual/en/function.imageinterlace.php
 * @param image resource
 * @param interlace int[optional]
 * @return int 1 if the interlace bit is set for the image, 0 otherwise.
 */
function imageinterlace ($image, $interlace = null) {}

/**
 * Draw a line
 * @link http://php.net/manual/en/function.imageline.php
 * @param image resource
 * @param x1 int
 * @param y1 int
 * @param x2 int
 * @param y2 int
 * @param color int
 * @return bool
 */
function imageline ($image, $x1, $y1, $x2, $y2, $color) {}

/**
 * Load a new font
 * @link http://php.net/manual/en/function.imageloadfont.php
 * @param file string
 * @return int
 */
function imageloadfont ($file) {}

/**
 * Draws a polygon
 * @link http://php.net/manual/en/function.imagepolygon.php
 * @param image resource
 * @param points array
 * @param num_points int
 * @param color int
 * @return bool
 */
function imagepolygon ($image, array $points, $num_points, $color) {}

/**
 * Draw a rectangle
 * @link http://php.net/manual/en/function.imagerectangle.php
 * @param image resource
 * @param x1 int
 * @param y1 int
 * @param x2 int
 * @param y2 int
 * @param color int
 * @return bool
 */
function imagerectangle ($image, $x1, $y1, $x2, $y2, $color) {}

/**
 * Set a single pixel
 * @link http://php.net/manual/en/function.imagesetpixel.php
 * @param image resource
 * @param x int
 * @param y int
 * @param color int
 * @return bool
 */
function imagesetpixel ($image, $x, $y, $color) {}

/**
 * Draw a string horizontally
 * @link http://php.net/manual/en/function.imagestring.php
 * @param image resource
 * @param font int
 * @param x int
 * @param y int
 * @param string string
 * @param color int
 * @return bool
 */
function imagestring ($image, $font, $x, $y, $string, $color) {}

/**
 * Draw a string vertically
 * @link http://php.net/manual/en/function.imagestringup.php
 * @param image resource
 * @param font int
 * @param x int
 * @param y int
 * @param string string
 * @param color int
 * @return bool
 */
function imagestringup ($image, $font, $x, $y, $string, $color) {}

/**
 * Get image width
 * @link http://php.net/manual/en/function.imagesx.php
 * @param image resource
 * @return int
 */
function imagesx ($image) {}

/**
 * Get image height
 * @link http://php.net/manual/en/function.imagesy.php
 * @param image resource
 * @return int
 */
function imagesy ($image) {}

/**
 * Draw a dashed line
 * @link http://php.net/manual/en/function.imagedashedline.php
 * @param image resource
 * @param x1 int
 * @param y1 int
 * @param x2 int
 * @param y2 int
 * @param color int
 * @return bool
 */
function imagedashedline ($image, $x1, $y1, $x2, $y2, $color) {}

/**
 * Give the bounding box of a text using TrueType fonts
 * @link http://php.net/manual/en/function.imagettfbbox.php
 * @param size float
 * @param angle float
 * @param fontfile string
 * @param text string
 * @return array
 */
function imagettfbbox ($size, $angle, $fontfile, $text) {}

/**
 * Write text to the image using TrueType fonts
 * @link http://php.net/manual/en/function.imagettftext.php
 * @param image resource
 * @param size float
 * @param angle float
 * @param x int
 * @param y int
 * @param color int
 * @param fontfile string
 * @param text string
 * @return array an array with 8 elements representing four points making the
 */
function imagettftext ($image, $size, $angle, $x, $y, $color, $fontfile, $text) {}

/**
 * Give the bounding box of a text using fonts via freetype2
 * @link http://php.net/manual/en/function.imageftbbox.php
 * @param size float
 * @param angle float
 * @param font_file string
 * @param text string
 * @param extrainfo array[optional]
 * @return array
 */
function imageftbbox ($size, $angle, $font_file, $text, array $extrainfo = null) {}

/**
 * Write text to the image using fonts using FreeType 2
 * @link http://php.net/manual/en/function.imagefttext.php
 * @param image resource
 * @param size float
 * @param angle float
 * @param x int
 * @param y int
 * @param col int
 * @param font_file string
 * @param text string
 * @param extrainfo array[optional]
 * @return array
 */
function imagefttext ($image, $size, $angle, $x, $y, $col, $font_file, $text, array $extrainfo = null) {}

/**
 * Load a PostScript Type 1 font from file
 * @link http://php.net/manual/en/function.imagepsloadfont.php
 * @param filename string
 * @return resource
 */
function imagepsloadfont ($filename) {}

/**
 * Free memory used by a PostScript Type 1 font
 * @link http://php.net/manual/en/function.imagepsfreefont.php
 * @param fontindex resource
 * @return bool
 */
function imagepsfreefont ($fontindex) {}

/**
 * Change the character encoding vector of a font
 * @link http://php.net/manual/en/function.imagepsencodefont.php
 * @param font_index resource
 * @param encodingfile string
 * @return bool
 */
function imagepsencodefont ($font_index, $encodingfile) {}

/**
 * Extend or condense a font
 * @link http://php.net/manual/en/function.imagepsextendfont.php
 * @param font_index int
 * @param extend float
 * @return bool
 */
function imagepsextendfont ($font_index, $extend) {}

/**
 * Slant a font
 * @link http://php.net/manual/en/function.imagepsslantfont.php
 * @param font_index resource
 * @param slant float
 * @return bool
 */
function imagepsslantfont ($font_index, $slant) {}

/**
 * Draws a text over an image using PostScript Type1 fonts
 * @link http://php.net/manual/en/function.imagepstext.php
 * @param image resource
 * @param text string
 * @param font resource
 * @param size int
 * @param foreground int
 * @param background int
 * @param x int
 * @param y int
 * @param space int[optional]
 * @param tightness int[optional]
 * @param angle float[optional]
 * @param antialias_steps int[optional]
 * @return array
 */
function imagepstext ($image, $text, $font, $size, $foreground, $background, $x, $y, $space = null, $tightness = null, $angle = null, $antialias_steps = null) {}

/**
 * Give the bounding box of a text rectangle using PostScript Type1 fonts
 * @link http://php.net/manual/en/function.imagepsbbox.php
 * @param text string
 * @param font int
 * @param size int
 * @param space int[optional]
 * @param tightness int
 * @param angle float
 * @return array an array containing the following elements:
 */
function imagepsbbox ($text, $font, $size, $space = null, $tightness, $angle) {}

/**
 * Return the image types supported by this PHP build
 * @link http://php.net/manual/en/function.imagetypes.php
 * @return int a bit-field corresponding to the image formats supported by the
 */
function imagetypes () {}

/**
 * Convert JPEG image file to WBMP image file
 * @link http://php.net/manual/en/function.jpeg2wbmp.php
 * @param jpegname string
 * @param wbmpname string
 * @param dest_height int
 * @param dest_width int
 * @param threshold int
 * @return bool
 */
function jpeg2wbmp ($jpegname, $wbmpname, $dest_height, $dest_width, $threshold) {}

/**
 * Convert PNG image file to WBMP image file
 * @link http://php.net/manual/en/function.png2wbmp.php
 * @param pngname string
 * @param wbmpname string
 * @param dest_height int
 * @param dest_width int
 * @param threshold int
 * @return bool
 */
function png2wbmp ($pngname, $wbmpname, $dest_height, $dest_width, $threshold) {}

/**
 * Output image to browser or file
 * @link http://php.net/manual/en/function.image2wbmp.php
 * @param image resource
 * @param filename string[optional]
 * @param threshold int[optional]
 * @return bool
 */
function image2wbmp ($image, $filename = null, $threshold = null) {}

/**
 * Set the alpha blending flag to use the bundled libgd layering effects
 * @link http://php.net/manual/en/function.imagelayereffect.php
 * @param image resource
 * @param effect int
 * @return bool
 */
function imagelayereffect ($image, $effect) {}

/**
 * Makes the colors of the palette version of an image more closely match the true color version
 * @link http://php.net/manual/en/function.imagecolormatch.php
 * @param image1 resource
 * @param image2 resource
 * @return bool
 */
function imagecolormatch ($image1, $image2) {}

/**
 * Output XBM image to browser or file
 * @link http://php.net/manual/en/function.imagexbm.php
 * @param image resource
 * @param filename string
 * @param foreground int[optional]
 * @return bool
 */
function imagexbm ($image, $filename, $foreground = null) {}

/**
 * Applies a filter to an image
 * @link http://php.net/manual/en/function.imagefilter.php
 * @param image resource
 * @param filtertype int
 * @param arg1 int[optional]
 * @param arg2 int[optional]
 * @param arg3 int[optional]
 * @return bool
 */
function imagefilter ($image, $filtertype, $arg1 = null, $arg2 = null, $arg3 = null) {}

/**
 * Apply a 3x3 convolution matrix, using coefficient and offset
 * @link http://php.net/manual/en/function.imageconvolution.php
 * @param image resource
 * @param matrix array
 * @param div float
 * @param offset float
 * @return bool
 */
function imageconvolution ($image, array $matrix, $div, $offset) {}

define ('IMG_GIF', 1);
define ('IMG_JPG', 2);
define ('IMG_JPEG', 2);
define ('IMG_PNG', 4);
define ('IMG_WBMP', 8);
define ('IMG_XPM', 16);
define ('IMG_COLOR_TILED', -5);
define ('IMG_COLOR_STYLED', -2);
define ('IMG_COLOR_BRUSHED', -3);
define ('IMG_COLOR_STYLEDBRUSHED', -4);
define ('IMG_COLOR_TRANSPARENT', -6);
define ('IMG_ARC_ROUNDED', 0);
define ('IMG_ARC_PIE', 0);
define ('IMG_ARC_CHORD', 1);
define ('IMG_ARC_NOFILL', 2);
define ('IMG_ARC_EDGED', 4);
define ('IMG_GD2_RAW', 1);
define ('IMG_GD2_COMPRESSED', 2);
define ('IMG_EFFECT_REPLACE', 0);
define ('IMG_EFFECT_ALPHABLEND', 1);
define ('IMG_EFFECT_NORMAL', 2);
define ('IMG_EFFECT_OVERLAY', 3);
define ('GD_BUNDLED', 1);
define ('IMG_FILTER_NEGATE', 0);
define ('IMG_FILTER_GRAYSCALE', 1);
define ('IMG_FILTER_BRIGHTNESS', 2);
define ('IMG_FILTER_CONTRAST', 3);
define ('IMG_FILTER_COLORIZE', 4);
define ('IMG_FILTER_EDGEDETECT', 5);
define ('IMG_FILTER_GAUSSIAN_BLUR', 7);
define ('IMG_FILTER_SELECTIVE_BLUR', 8);
define ('IMG_FILTER_EMBOSS', 6);
define ('IMG_FILTER_MEAN_REMOVAL', 9);
define ('IMG_FILTER_SMOOTH', 10);
define ('GD_VERSION', '2.0.35');
define ('GD_MAJOR_VERSION', 2);
define ('GD_MINOR_VERSION', 0);
define ('GD_RELEASE_VERSION', 35);
define ('GD_EXTRA_VERSION', '');
define ('PNG_NO_FILTER', 0);
define ('PNG_FILTER_NONE', 8);
define ('PNG_FILTER_SUB', 16);
define ('PNG_FILTER_UP', 32);
define ('PNG_FILTER_AVG', 64);
define ('PNG_FILTER_PAETH', 128);
define ('PNG_ALL_FILTERS', 248);

// End of gd v.

// Start of iconv v.

/**
 * Convert string to requested character encoding
 * @link http://php.net/manual/en/function.iconv.php
 * @param in_charset string
 * @param out_charset string
 * @param str string
 * @return string the converted string or false on failure.
 */
function iconv ($in_charset, $out_charset, $str) {}

/**
 * Convert character encoding as output buffer handler
 * @link http://php.net/manual/en/function.ob-iconv-handler.php
 * @param contents string
 * @param status int
 * @return string
 */
function ob_iconv_handler ($contents, $status) {}

/**
 * Retrieve internal configuration variables of iconv extension
 * @link http://php.net/manual/en/function.iconv-get-encoding.php
 * @param type string[optional]
 * @return mixed the current value of the internal configuration variable if
 */
function iconv_get_encoding ($type = null) {}

/**
 * Set current setting for character encoding conversion
 * @link http://php.net/manual/en/function.iconv-set-encoding.php
 * @param type string
 * @param charset string
 * @return bool
 */
function iconv_set_encoding ($type, $charset) {}

/**
 * Returns the character count of string
 * @link http://php.net/manual/en/function.iconv-strlen.php
 * @param str string
 * @param charset string[optional]
 * @return int the character count of str, as an integer.
 */
function iconv_strlen ($str, $charset = null) {}

/**
 * Cut out part of a string
 * @link http://php.net/manual/en/function.iconv-substr.php
 * @param str string
 * @param offset int
 * @param length int[optional]
 * @param charset string[optional]
 * @return string the portion of str specified by the
 */
function iconv_substr ($str, $offset, $length = null, $charset = null) {}

/**
 * Finds position of first occurrence of a needle within a haystack
 * @link http://php.net/manual/en/function.iconv-strpos.php
 * @param haystack string
 * @param needle string
 * @param offset int[optional]
 * @param charset string[optional]
 * @return int the numeric position of the first occurrence of
 */
function iconv_strpos ($haystack, $needle, $offset = null, $charset = null) {}

/**
 * Finds the last occurrence of a needle within a haystack
 * @link http://php.net/manual/en/function.iconv-strrpos.php
 * @param haystack string
 * @param needle string
 * @param charset string[optional]
 * @return int the numeric position of the last occurrence of
 */
function iconv_strrpos ($haystack, $needle, $charset = null) {}

/**
 * Composes a <literal>MIME</literal> header field
 * @link http://php.net/manual/en/function.iconv-mime-encode.php
 * @param field_name string
 * @param field_value string
 * @param preferences array[optional]
 * @return string an encoded MIME field on success,
 */
function iconv_mime_encode ($field_name, $field_value, array $preferences = null) {}

/**
 * Decodes a <literal>MIME</literal> header field
 * @link http://php.net/manual/en/function.iconv-mime-decode.php
 * @param encoded_header string
 * @param mode int[optional]
 * @param charset string[optional]
 * @return string a decoded MIME field on success,
 */
function iconv_mime_decode ($encoded_header, $mode = null, $charset = null) {}

/**
 * Decodes multiple <literal>MIME</literal> header fields at once
 * @link http://php.net/manual/en/function.iconv-mime-decode-headers.php
 * @param encoded_headers string
 * @param mode int[optional]
 * @param charset string[optional]
 * @return array
 */
function iconv_mime_decode_headers ($encoded_headers, $mode = null, $charset = null) {}

define ('ICONV_IMPL', 'glibc');
define ('ICONV_VERSION', 1.9);
define ('ICONV_MIME_DECODE_STRICT', 1);
define ('ICONV_MIME_DECODE_CONTINUE_ON_ERROR', 2);

// End of iconv v.

// Start of mbstring v.

/**
 * Perform case folding on a string
 * @link http://php.net/manual/en/function.mb-convert-case.php
 */
function mb_convert_case () {}

/**
 * Make a string uppercase
 * @link http://php.net/manual/en/function.mb-strtoupper.php
 */
function mb_strtoupper () {}

/**
 * Make a string lowercase
 * @link http://php.net/manual/en/function.mb-strtolower.php
 */
function mb_strtolower () {}

/**
 * Set/Get current language
 * @link http://php.net/manual/en/function.mb-language.php
 */
function mb_language () {}

/**
 * Set/Get internal character encoding
 * @link http://php.net/manual/en/function.mb-internal-encoding.php
 */
function mb_internal_encoding () {}

/**
 * Detect HTTP input character encoding
 * @link http://php.net/manual/en/function.mb-http-input.php
 */
function mb_http_input () {}

/**
 * Set/Get HTTP output character encoding
 * @link http://php.net/manual/en/function.mb-http-output.php
 */
function mb_http_output () {}

/**
 * Set/Get character encoding detection order
 * @link http://php.net/manual/en/function.mb-detect-order.php
 */
function mb_detect_order () {}

/**
 * Set/Get substitution character
 * @link http://php.net/manual/en/function.mb-substitute-character.php
 */
function mb_substitute_character () {}

/**
 * Parse GET/POST/COOKIE data and set global variable
 * @link http://php.net/manual/en/function.mb-parse-str.php
 * @param var1
 * @param var2
 */
function mb_parse_str ($var1, &$var2) {}

/**
 * Callback function converts character encoding in output buffer
 * @link http://php.net/manual/en/function.mb-output-handler.php
 */
function mb_output_handler () {}

/**
 * Get MIME charset string
 * @link http://php.net/manual/en/function.mb-preferred-mime-name.php
 */
function mb_preferred_mime_name () {}

/**
 * Get string length
 * @link http://php.net/manual/en/function.mb-strlen.php
 */
function mb_strlen () {}

/**
 * Find position of first occurrence of string in a string
 * @link http://php.net/manual/en/function.mb-strpos.php
 */
function mb_strpos () {}

/**
 * Find position of last occurrence of a string in a string
 * @link http://php.net/manual/en/function.mb-strrpos.php
 * @param haystack string
 * @param needle string
 * @param offset int[optional]
 * @param encoding string[optional]
 * @return int
 */
function mb_strrpos ($haystack, $needle, $offset = null, $encoding = null) {}

/**
 * Finds position of first occurrence of a string within another, case insensitive
 * @link http://php.net/manual/en/function.mb-stripos.php
 * @param haystack string
 * @param needle string
 * @param offset int[optional]
 * @param encoding string[optional]
 * @return int
 */
function mb_stripos ($haystack, $needle, $offset = null, $encoding = null) {}

/**
 * Finds position of last occurrence of a string within another, case insensitive
 * @link http://php.net/manual/en/function.mb-strripos.php
 * @param haystack string
 * @param needle string
 * @param offset int[optional]
 * @param encoding string[optional]
 * @return int
 */
function mb_strripos ($haystack, $needle, $offset = null, $encoding = null) {}

/**
 * Finds first occurrence of a string within another
 * @link http://php.net/manual/en/function.mb-strstr.php
 * @param haystack string
 * @param needle string
 * @param part bool[optional]
 * @param encoding string[optional]
 * @return string the portion of haystack,
 */
function mb_strstr ($haystack, $needle, $part = null, $encoding = null) {}

/**
 * Finds the last occurrence of a character in a string within another
 * @link http://php.net/manual/en/function.mb-strrchr.php
 * @param haystack string
 * @param needle string
 * @param part bool[optional]
 * @param encoding string[optional]
 * @return string the portion of haystack.
 */
function mb_strrchr ($haystack, $needle, $part = null, $encoding = null) {}

/**
 * Finds first occurrence of a string within another, case insensitive
 * @link http://php.net/manual/en/function.mb-stristr.php
 * @param haystack string
 * @param needle string
 * @param part bool[optional]
 * @param encoding string[optional]
 * @return string the portion of haystack,
 */
function mb_stristr ($haystack, $needle, $part = null, $encoding = null) {}

/**
 * Finds the last occurrence of a character in a string within another, case insensitive
 * @link http://php.net/manual/en/function.mb-strrichr.php
 * @param haystack string
 * @param needle string
 * @param part bool[optional]
 * @param encoding string[optional]
 * @return string the portion of haystack.
 */
function mb_strrichr ($haystack, $needle, $part = null, $encoding = null) {}

/**
 * Count the number of substring occurrences
 * @link http://php.net/manual/en/function.mb-substr-count.php
 */
function mb_substr_count () {}

/**
 * Get part of string
 * @link http://php.net/manual/en/function.mb-substr.php
 */
function mb_substr () {}

/**
 * Get part of string
 * @link http://php.net/manual/en/function.mb-strcut.php
 */
function mb_strcut () {}

/**
 * Return width of string
 * @link http://php.net/manual/en/function.mb-strwidth.php
 */
function mb_strwidth () {}

/**
 * Get truncated string with specified width
 * @link http://php.net/manual/en/function.mb-strimwidth.php
 */
function mb_strimwidth () {}

/**
 * Convert character encoding
 * @link http://php.net/manual/en/function.mb-convert-encoding.php
 */
function mb_convert_encoding () {}

/**
 * Detect character encoding
 * @link http://php.net/manual/en/function.mb-detect-encoding.php
 */
function mb_detect_encoding () {}

function mb_list_encodings () {}

/**
 * Convert "kana" one from another ("zen-kaku", "han-kaku" and more)
 * @link http://php.net/manual/en/function.mb-convert-kana.php
 */
function mb_convert_kana () {}

/**
 * Encode string for MIME header
 * @link http://php.net/manual/en/function.mb-encode-mimeheader.php
 */
function mb_encode_mimeheader () {}

/**
 * Decode string in MIME header field
 * @link http://php.net/manual/en/function.mb-decode-mimeheader.php
 */
function mb_decode_mimeheader () {}

/**
 * Convert character code in variable(s)
 * @link http://php.net/manual/en/function.mb-convert-variables.php
 * @param var1
 * @param var2
 */
function mb_convert_variables ($var1, $var2) {}

/**
 * Encode character to HTML numeric string reference
 * @link http://php.net/manual/en/function.mb-encode-numericentity.php
 */
function mb_encode_numericentity () {}

/**
 * Decode HTML numeric string reference to character
 * @link http://php.net/manual/en/function.mb-decode-numericentity.php
 */
function mb_decode_numericentity () {}

/**
 * Send encoded mail
 * @link http://php.net/manual/en/function.mb-send-mail.php
 */
function mb_send_mail () {}

/**
 * Get internal settings of mbstring
 * @link http://php.net/manual/en/function.mb-get-info.php
 */
function mb_get_info () {}

/**
 * Check if the string is valid for the specified encoding
 * @link http://php.net/manual/en/function.mb-check-encoding.php
 * @param var string[optional]
 * @param encoding string[optional]
 * @return bool
 */
function mb_check_encoding ($var = null, $encoding = null) {}

/**
 * Returns current encoding for multibyte regex as string
 * @link http://php.net/manual/en/function.mb-regex-encoding.php
 */
function mb_regex_encoding () {}

/**
 * Set/Get the default options for mbregex functions
 * @link http://php.net/manual/en/function.mb-regex-set-options.php
 */
function mb_regex_set_options () {}

/**
 * Regular expression match with multibyte support
 * @link http://php.net/manual/en/function.mb-ereg.php
 * @param var1
 * @param var2
 * @param var3
 */
function mb_ereg ($var1, $var2, &$var3) {}

/**
 * Regular expression match ignoring case with multibyte support
 * @link http://php.net/manual/en/function.mb-eregi.php
 * @param var1
 * @param var2
 * @param var3
 */
function mb_eregi ($var1, $var2, &$var3) {}

/**
 * Replace regular expression with multibyte support
 * @link http://php.net/manual/en/function.mb-ereg-replace.php
 */
function mb_ereg_replace () {}

/**
 * Replace regular expression with multibyte support
     ignoring case
 * @link http://php.net/manual/en/function.mb-eregi-replace.php
 */
function mb_eregi_replace () {}

/**
 * Split multibyte string using regular expression
 * @link http://php.net/manual/en/function.mb-split.php
 */
function mb_split () {}

/**
 * Regular expression match for multibyte string
 * @link http://php.net/manual/en/function.mb-ereg-match.php
 */
function mb_ereg_match () {}

/**
 * Multibyte regular expression match for predefined multibyte string
 * @link http://php.net/manual/en/function.mb-ereg-search.php
 */
function mb_ereg_search () {}

/**
 * Return position and length of matched part of multibyte regular
     expression for predefined multibyte string
 * @link http://php.net/manual/en/function.mb-ereg-search-pos.php
 */
function mb_ereg_search_pos () {}

/**
 * Returns the matched part of multibyte regular expression
 * @link http://php.net/manual/en/function.mb-ereg-search-regs.php
 */
function mb_ereg_search_regs () {}

/**
 * Setup string and regular expression for multibyte regular
     expression match
 * @link http://php.net/manual/en/function.mb-ereg-search-init.php
 */
function mb_ereg_search_init () {}

/**
 * Retrieve the result from the last multibyte regular expression
     match
 * @link http://php.net/manual/en/function.mb-ereg-search-getregs.php
 */
function mb_ereg_search_getregs () {}

/**
 * Returns start point for next regular expression match
 * @link http://php.net/manual/en/function.mb-ereg-search-getpos.php
 */
function mb_ereg_search_getpos () {}

/**
 * Set start point of next regular expression match
 * @link http://php.net/manual/en/function.mb-ereg-search-setpos.php
 */
function mb_ereg_search_setpos () {}

function mbregex_encoding () {}

function mbereg () {}

function mberegi () {}

function mbereg_replace () {}

function mberegi_replace () {}

function mbsplit () {}

function mbereg_match () {}

function mbereg_search () {}

function mbereg_search_pos () {}

function mbereg_search_regs () {}

function mbereg_search_init () {}

function mbereg_search_getregs () {}

function mbereg_search_getpos () {}

function mbereg_search_setpos () {}

define ('MB_OVERLOAD_MAIL', 1);
define ('MB_OVERLOAD_STRING', 2);
define ('MB_OVERLOAD_REGEX', 4);
define ('MB_CASE_UPPER', 0);
define ('MB_CASE_LOWER', 1);
define ('MB_CASE_TITLE', 2);

// End of mbstring v.

// Start of ming v.

class SWFShape  {

	public function __construct () {}

	public function setLine () {}

	public function addFill () {}

	public function setLeftFill () {}

	public function setRightFill () {}

	public function movePenTo () {}

	public function movePen () {}

	public function drawLineTo () {}

	public function drawLine () {}

	public function drawCurveTo () {}

	public function drawCurve () {}

	public function drawGlyph () {}

	public function drawCircle () {}

	public function drawArc () {}

	public function drawCubic () {}

	public function drawCubicTo () {}

}

class SWFFill  {

	public function __construct () {}

	public function moveTo () {}

	public function scaleTo () {}

	public function rotateTo () {}

	public function skewXTo () {}

	public function skewYTo () {}

}

class SWFGradient  {

	public function __construct () {}

	public function addEntry () {}

}

class SWFBitmap  {

	public function __construct () {}

	public function getWidth () {}

	public function getHeight () {}

}

class SWFText  {

	public function __construct () {}

	public function setFont () {}

	public function setHeight () {}

	public function setSpacing () {}

	public function setColor () {}

	public function moveTo () {}

	public function addString () {}

	public function addUTF8String () {}

	public function getWidth () {}

	public function getUTF8Width () {}

	public function getAscent () {}

	public function getDescent () {}

	public function getLeading () {}

}

class SWFTextField  {

	public function __construct () {}

	public function setFont () {}

	public function setBounds () {}

	public function align () {}

	public function setHeight () {}

	public function setLeftMargin () {}

	public function setRightMargin () {}

	public function setMargins () {}

	public function setIndentation () {}

	public function setLineSpacing () {}

	public function setColor () {}

	public function setName () {}

	public function addString () {}

	public function setPadding () {}

	public function addChars () {}

}

class SWFFont  {

	public function __construct () {}

	public function getWidth () {}

	public function getUTF8Width () {}

	public function getAscent () {}

	public function getDescent () {}

	public function getLeading () {}

	public function getShape () {}

}

class SWFDisplayItem  {

	public function moveTo () {}

	public function move () {}

	public function scaleTo () {}

	public function scale () {}

	public function rotateTo () {}

	public function rotate () {}

	public function skewXTo () {}

	public function skewX () {}

	public function skewYTo () {}

	public function skewY () {}

	public function setMatrix () {}

	public function setDepth () {}

	public function setRatio () {}

	public function addColor () {}

	public function multColor () {}

	public function setName () {}

	public function addAction () {}

	public function remove () {}

	public function setMaskLevel () {}

	public function endMask () {}

	public function getX () {}

	public function getY () {}

	public function getXScale () {}

	public function getYScale () {}

	public function getXSkew () {}

	public function getYSkew () {}

	public function getRot () {}

}

class SWFMovie  {

	public function __construct () {}

	public function nextFrame () {}

	public function labelFrame () {}

	public function add () {}

	public function remove () {}

	public function output () {}

	public function save () {}

	public function saveToFile () {}

	public function setBackground () {}

	public function setRate () {}

	public function setDimension () {}

	public function setFrames () {}

	public function streamMP3 () {}

	public function addExport () {}

	public function writeExports () {}

	public function startSound () {}

	public function stopSound () {}

	public function importChar () {}

	public function importFont () {}

	public function addFont () {}

	public function protect () {}

	public function namedAnchor () {}

}

class SWFButton  {

	public function __construct () {}

	public function setHit () {}

	public function setOver () {}

	public function setUp () {}

	public function setDown () {}

	public function setAction () {}

	public function addShape () {}

	public function setMenu () {}

	public function addAction () {}

	public function addSound () {}

}

class SWFAction  {

	public function __construct () {}

}

class SWFMorph  {

	public function __construct () {}

	public function getShape1 () {}

	public function getShape2 () {}

}

class SWFSprite  {

	public function __construct () {}

	public function add () {}

	public function remove () {}

	public function nextFrame () {}

	public function labelFrame () {}

	public function setFrames () {}

	public function startSound () {}

	public function stopSound () {}

}

class SWFSound  {

	public function __construct () {}

}

class SWFFontChar  {

	public function addChars () {}

	public function addUTF8Chars () {}

}

class SWFSoundInstance  {

	public function noMultiple () {}

	public function loopInPoint () {}

	public function loopOutPoint () {}

	public function loopCount () {}

}

class SWFVideoStream  {

	public function __construct () {}

	public function setdimension () {}

	public function getnumframes () {}

}

/**
 * Set cubic threshold
 * @link http://php.net/manual/en/function.ming-setcubicthreshold.php
 * @param threshold int
 * @return void
 */
function ming_setcubicthreshold ($threshold) {}

/**
 * Set scale
 * @link http://php.net/manual/en/function.ming-setscale.php
 * @param scale int
 * @return void
 */
function ming_setscale ($scale) {}

/**
 * Sets the SWF version
 * @link http://php.net/manual/en/function.ming-useswfversion.php
 * @param version int
 * @return void
 */
function ming_useswfversion ($version) {}

/**
 * Returns the action flag for keyPress(char)
 * @link http://php.net/manual/en/function.ming-keypress.php
 * @param char string
 * @return int
 */
function ming_keypress ($char) {}

/**
 * Use constant pool
 * @link http://php.net/manual/en/function.ming-useconstants.php
 * @param use int
 * @return void
 */
function ming_useconstants ($use) {}

/**
 * Sets the SWF output compression
 * @link http://php.net/manual/en/function.ming-setswfcompression.php
 * @param level int
 * @return void
 */
function ming_setswfcompression ($level) {}

define ('MING_NEW', 1);
define ('MING_ZLIB', 1);
define ('SWFBUTTON_HIT', 8);
define ('SWFBUTTON_DOWN', 4);
define ('SWFBUTTON_OVER', 2);
define ('SWFBUTTON_UP', 1);
define ('SWFBUTTON_MOUSEUPOUTSIDE', 64);
define ('SWFBUTTON_DRAGOVER', 160);
define ('SWFBUTTON_DRAGOUT', 272);
define ('SWFBUTTON_MOUSEUP', 8);
define ('SWFBUTTON_MOUSEDOWN', 4);
define ('SWFBUTTON_MOUSEOUT', 2);
define ('SWFBUTTON_MOUSEOVER', 1);
define ('SWFFILL_RADIAL_GRADIENT', 18);
define ('SWFFILL_LINEAR_GRADIENT', 16);
define ('SWFFILL_TILED_BITMAP', 64);
define ('SWFFILL_CLIPPED_BITMAP', 65);
define ('SWFTEXTFIELD_HASLENGTH', 2);
define ('SWFTEXTFIELD_NOEDIT', 8);
define ('SWFTEXTFIELD_PASSWORD', 16);
define ('SWFTEXTFIELD_MULTILINE', 32);
define ('SWFTEXTFIELD_WORDWRAP', 64);
define ('SWFTEXTFIELD_DRAWBOX', 2048);
define ('SWFTEXTFIELD_NOSELECT', 4096);
define ('SWFTEXTFIELD_HTML', 512);
define ('SWFTEXTFIELD_USEFONT', 256);
define ('SWFTEXTFIELD_AUTOSIZE', 16384);
define ('SWFTEXTFIELD_ALIGN_LEFT', 0);
define ('SWFTEXTFIELD_ALIGN_RIGHT', 1);
define ('SWFTEXTFIELD_ALIGN_CENTER', 2);
define ('SWFTEXTFIELD_ALIGN_JUSTIFY', 3);
define ('SWFACTION_ONLOAD', 1);
define ('SWFACTION_ENTERFRAME', 2);
define ('SWFACTION_UNLOAD', 4);
define ('SWFACTION_MOUSEMOVE', 8);
define ('SWFACTION_MOUSEDOWN', 16);
define ('SWFACTION_MOUSEUP', 32);
define ('SWFACTION_KEYDOWN', 64);
define ('SWFACTION_KEYUP', 128);
define ('SWFACTION_DATA', 256);
define ('SWF_SOUND_NOT_COMPRESSED', 0);
define ('SWF_SOUND_ADPCM_COMPRESSED', 16);
define ('SWF_SOUND_MP3_COMPRESSED', 32);
define ('SWF_SOUND_NOT_COMPRESSED_LE', 48);
define ('SWF_SOUND_NELLY_COMPRESSED', 96);
define ('SWF_SOUND_5KHZ', 0);
define ('SWF_SOUND_11KHZ', 4);
define ('SWF_SOUND_22KHZ', 8);
define ('SWF_SOUND_44KHZ', 12);
define ('SWF_SOUND_8BITS', 0);
define ('SWF_SOUND_16BITS', 2);
define ('SWF_SOUND_MONO', 0);
define ('SWF_SOUND_STEREO', 1);

// End of ming v.

// Start of pdo_mysql v.1.0.2
// End of pdo_mysql v.1.0.2

// Start of pdo_pgsql v.1.0.2
// End of pdo_pgsql v.1.0.2

// Start of shmop v.

/**
 * Create or open shared memory block
 * @link http://php.net/manual/en/function.shmop-open.php
 * @param key int
 * @param flags string
 * @param mode int
 * @param size int
 * @return int
 */
function shmop_open ($key, $flags, $mode, $size) {}

/**
 * Read data from shared memory block
 * @link http://php.net/manual/en/function.shmop-read.php
 * @param shmid int
 * @param start int
 * @param count int
 * @return string the data or false on failure.
 */
function shmop_read ($shmid, $start, $count) {}

/**
 * Close shared memory block
 * @link http://php.net/manual/en/function.shmop-close.php
 * @param shmid int
 * @return void
 */
function shmop_close ($shmid) {}

/**
 * Get size of shared memory block
 * @link http://php.net/manual/en/function.shmop-size.php
 * @param shmid int
 * @return int an int, which represents the number of bytes the shared memory
 */
function shmop_size ($shmid) {}

/**
 * Write data into shared memory block
 * @link http://php.net/manual/en/function.shmop-write.php
 * @param shmid int
 * @param data string
 * @param offset int
 * @return int
 */
function shmop_write ($shmid, $data, $offset) {}

/**
 * Delete shared memory block
 * @link http://php.net/manual/en/function.shmop-delete.php
 * @param shmid int
 * @return bool
 */
function shmop_delete ($shmid) {}

// End of shmop v.

// Start of SQLite v.2.0-dev

/**
 * Represents an opened SQLite database.
 * @link http://php.net/manual/en/ref.sqlite.php
 */
class SQLiteDatabase  {

	/**
	 * @param var1
	 * @param var2
	 * @param var3
	 */
	final public function __construct ($var1, $var2, &$var3) {}

	/**
	 * @param var1
	 * @param var2
	 * @param var3
	 */
	public function query ($var1, $var2, &$var3) {}

	/**
	 * @param var1
	 * @param var2
	 */
	public function queryExec ($var1, &$var2) {}

	public function arrayQuery () {}

	public function singleQuery () {}

	/**
	 * @param var1
	 * @param var2
	 * @param var3
	 */
	public function unbufferedQuery ($var1, $var2, &$var3) {}

	public function lastInsertRowid () {}

	public function changes () {}

	public function createAggregate () {}

	public function createFunction () {}

	public function busyTimeout () {}

	public function lastError () {}

	public function fetchColumnTypes () {}

}

/**
 * Represents a buffered SQLite result set.
 * @link http://php.net/manual/en/ref.sqlite.php
 */
final class SQLiteResult implements Iterator, Traversable, Countable {

	public function fetch () {}

	public function fetchObject () {}

	public function fetchSingle () {}

	public function fetchAll () {}

	public function column () {}

	public function numFields () {}

	public function fieldName () {}

	public function current () {}

	public function key () {}

	public function next () {}

	public function valid () {}

	public function rewind () {}

	public function count () {}

	public function prev () {}

	public function hasPrev () {}

	public function numRows () {}

	public function seek () {}

}

/**
 * Represents an unbuffered SQLite result set.  Unbuffered results sets are sequential, forward-seeking only.
 * @link http://php.net/manual/en/ref.sqlite.php
 */
final class SQLiteUnbuffered  {

	public function fetch () {}

	public function fetchObject () {}

	public function fetchSingle () {}

	public function fetchAll () {}

	public function column () {}

	public function numFields () {}

	public function fieldName () {}

	public function current () {}

	public function next () {}

	public function valid () {}

}

final class SQLiteException extends RuntimeException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * Opens a SQLite database and create the database if it does not exist
 * @link http://php.net/manual/en/function.sqlite-open.php
 * @param filename string
 * @param mode int[optional]
 * @param error_message string[optional]
 * @return resource a resource (database handle) on success, false on error.
 */
function sqlite_open ($filename, $mode = null, &$error_message = null) {}

/**
 * Opens a persistent handle to an SQLite database and create the database if it does not exist
 * @link http://php.net/manual/en/function.sqlite-popen.php
 * @param filename string
 * @param mode int[optional]
 * @param error_message string[optional]
 * @return resource a resource (database handle) on success, false on error.
 */
function sqlite_popen ($filename, $mode = null, &$error_message = null) {}

/**
 * Closes an open SQLite database
 * @link http://php.net/manual/en/function.sqlite-close.php
 * @param dbhandle resource
 * @return void
 */
function sqlite_close ($dbhandle) {}

/**
 * Executes a query against a given database and returns a result handle
 * @link http://php.net/manual/en/function.sqlite-query.php
 * @param query string
 * @param result_type int[optional]
 * @param error_msg string[optional]
 * @return SQLiteResult
 */
function sqlite_query ($query, $result_type = null, &$error_msg = null) {}

/**
 * Executes a result-less query against a given database
 * @link http://php.net/manual/en/function.sqlite-exec.php
 * @param query string
 * @param error_msg string[optional]
 * @return bool
 */
function sqlite_exec ($query, &$error_msg = null) {}

/**
 * Execute a query against a given database and returns an array
 * @link http://php.net/manual/en/function.sqlite-array-query.php
 * @param query string
 * @param result_type int[optional]
 * @param decode_binary bool[optional]
 * @return array an array of the entire result set; false otherwise.
 */
function sqlite_array_query ($query, $result_type = null, $decode_binary = null) {}

/**
 * Executes a query and returns either an array for one single column or the value of the first row
 * @link http://php.net/manual/en/function.sqlite-single-query.php
 * @param query string
 * @param first_row_only bool[optional]
 * @param decode_binary bool[optional]
 * @return array
 */
function sqlite_single_query ($query, $first_row_only = null, $decode_binary = null) {}

/**
 * Fetches the next row from a result set as an array
 * @link http://php.net/manual/en/function.sqlite-fetch-array.php
 * @param result_type int[optional]
 * @param decode_binary bool[optional]
 * @return array an array of the next row from a result set; false if the
 */
function sqlite_fetch_array ($result_type = null, $decode_binary = null) {}

/**
 * Fetches the next row from a result set as an object
 * @link http://php.net/manual/en/function.sqlite-fetch-object.php
 * @param class_name string[optional]
 * @param ctor_params array[optional]
 * @param decode_binary bool[optional]
 * @return object
 */
function sqlite_fetch_object ($class_name = null, array $ctor_params = null, $decode_binary = null) {}

/**
 * Fetches the first column of a result set as a string
 * @link http://php.net/manual/en/function.sqlite-fetch-single.php
 * @param decode_binary bool[optional]
 * @return string
 */
function sqlite_fetch_single ($decode_binary = null) {}

/**
 * &Alias; <function>sqlite_fetch_single</function>
 * @link http://php.net/manual/en/function.sqlite-fetch-string.php
 */
function sqlite_fetch_string () {}

/**
 * Fetches all rows from a result set as an array of arrays
 * @link http://php.net/manual/en/function.sqlite-fetch-all.php
 * @param result_type int[optional]
 * @param decode_binary bool[optional]
 * @return array an array of the remaining rows in a result set. If called right
 */
function sqlite_fetch_all ($result_type = null, $decode_binary = null) {}

/**
 * Fetches the current row from a result set as an array
 * @link http://php.net/manual/en/function.sqlite-current.php
 * @param result_type int[optional]
 * @param decode_binary bool[optional]
 * @return array an array of the current row from a result set; false if the
 */
function sqlite_current ($result_type = null, $decode_binary = null) {}

/**
 * Fetches a column from the current row of a result set
 * @link http://php.net/manual/en/function.sqlite-column.php
 * @param index_or_name mixed
 * @param decode_binary bool[optional]
 * @return mixed
 */
function sqlite_column ($index_or_name, $decode_binary = null) {}

/**
 * Returns the version of the linked SQLite library
 * @link http://php.net/manual/en/function.sqlite-libversion.php
 * @return string
 */
function sqlite_libversion () {}

/**
 * Returns the encoding of the linked SQLite library
 * @link http://php.net/manual/en/function.sqlite-libencoding.php
 * @return string
 */
function sqlite_libencoding () {}

/**
 * Returns the number of rows that were changed by the most
   recent SQL statement
 * @link http://php.net/manual/en/function.sqlite-changes.php
 * @return int
 */
function sqlite_changes () {}

/**
 * Returns the rowid of the most recently inserted row
 * @link http://php.net/manual/en/function.sqlite-last-insert-rowid.php
 * @return int
 */
function sqlite_last_insert_rowid () {}

/**
 * Returns the number of rows in a buffered result set
 * @link http://php.net/manual/en/function.sqlite-num-rows.php
 * @return int
 */
function sqlite_num_rows () {}

/**
 * Returns the number of fields in a result set
 * @link http://php.net/manual/en/function.sqlite-num-fields.php
 * @return int
 */
function sqlite_num_fields () {}

/**
 * Returns the name of a particular field
 * @link http://php.net/manual/en/function.sqlite-field-name.php
 * @param field_index int
 * @return string the name of a field in an SQLite result set, given the ordinal
 */
function sqlite_field_name ($field_index) {}

/**
 * Seek to a particular row number of a buffered result set
 * @link http://php.net/manual/en/function.sqlite-seek.php
 * @param rownum int
 * @return bool false if the row does not exist, true otherwise.
 */
function sqlite_seek ($rownum) {}

/**
 * Seek to the first row number
 * @link http://php.net/manual/en/function.sqlite-rewind.php
 * @return bool false if there are no rows in the result set, true otherwise.
 */
function sqlite_rewind () {}

/**
 * Seek to the next row number
 * @link http://php.net/manual/en/function.sqlite-next.php
 * @return bool true on success, or false if there are no more rows.
 */
function sqlite_next () {}

/**
 * Seek to the previous row number of a result set
 * @link http://php.net/manual/en/function.sqlite-prev.php
 * @return bool true on success, or false if there are no more previous rows.
 */
function sqlite_prev () {}

/**
 * Returns whether more rows are available
 * @link http://php.net/manual/en/function.sqlite-valid.php
 * @return bool true if there are more rows available from the
 */
function sqlite_valid () {}

/**
 * Finds whether or not more rows are available
 * @link http://php.net/manual/en/function.sqlite-has-more.php
 * @param result resource
 * @return bool true if there are more rows available from the
 */
function sqlite_has_more ($result) {}

/**
 * Returns whether or not a previous row is available
 * @link http://php.net/manual/en/function.sqlite-has-prev.php
 * @return bool true if there are more previous rows available from the
 */
function sqlite_has_prev () {}

/**
 * Escapes a string for use as a query parameter
 * @link http://php.net/manual/en/function.sqlite-escape-string.php
 * @param item string
 * @return string
 */
function sqlite_escape_string ($item) {}

/**
 * Set busy timeout duration, or disable busy handlers
 * @link http://php.net/manual/en/function.sqlite-busy-timeout.php
 * @param milliseconds int
 * @return void
 */
function sqlite_busy_timeout ($milliseconds) {}

/**
 * Returns the error code of the last error for a database
 * @link http://php.net/manual/en/function.sqlite-last-error.php
 * @return int
 */
function sqlite_last_error () {}

/**
 * Returns the textual description of an error code
 * @link http://php.net/manual/en/function.sqlite-error-string.php
 * @param error_code int
 * @return string
 */
function sqlite_error_string ($error_code) {}

/**
 * Execute a query that does not prefetch and buffer all data
 * @link http://php.net/manual/en/function.sqlite-unbuffered-query.php
 * @param query string
 * @param result_type int[optional]
 * @param error_msg string[optional]
 * @return SQLiteUnbuffered a result handle or false on failure.
 */
function sqlite_unbuffered_query ($query, $result_type = null, &$error_msg = null) {}

/**
 * Register an aggregating UDF for use in SQL statements
 * @link http://php.net/manual/en/function.sqlite-create-aggregate.php
 * @param function_name string
 * @param step_func callback
 * @param finalize_func callback
 * @param num_args int[optional]
 * @return void
 */
function sqlite_create_aggregate ($function_name, $step_func, $finalize_func, $num_args = null) {}

/**
 * Registers a "regular" User Defined Function for use in SQL statements
 * @link http://php.net/manual/en/function.sqlite-create-function.php
 * @param function_name string
 * @param callback callback
 * @param num_args int[optional]
 * @return void
 */
function sqlite_create_function ($function_name, $callback, $num_args = null) {}

/**
 * Opens a SQLite database and returns a SQLiteDatabase object
 * @link http://php.net/manual/en/function.sqlite-factory.php
 * @param filename string
 * @param mode int[optional]
 * @param error_message string[optional]
 * @return SQLiteDatabase a SQLiteDatabase object on success, &null; on error.
 */
function sqlite_factory ($filename, $mode = null, &$error_message = null) {}

/**
 * Encode binary data before returning it from an UDF
 * @link http://php.net/manual/en/function.sqlite-udf-encode-binary.php
 * @param data string
 * @return string
 */
function sqlite_udf_encode_binary ($data) {}

/**
 * Decode binary data passed as parameters to an UDF
 * @link http://php.net/manual/en/function.sqlite-udf-decode-binary.php
 * @param data string
 * @return string
 */
function sqlite_udf_decode_binary ($data) {}

/**
 * Return an array of column types from a particular table
 * @link http://php.net/manual/en/function.sqlite-fetch-column-types.php
 * @param table_name string
 * @param result_type int[optional]
 * @return array an array of column data types; false on error.
 */
function sqlite_fetch_column_types ($table_name, $result_type = null) {}

define ('SQLITE_BOTH', 3);
define ('SQLITE_NUM', 2);
define ('SQLITE_ASSOC', 1);
define ('SQLITE_OK', 0);
define ('SQLITE_ERROR', 1);
define ('SQLITE_INTERNAL', 2);
define ('SQLITE_PERM', 3);
define ('SQLITE_ABORT', 4);
define ('SQLITE_BUSY', 5);
define ('SQLITE_LOCKED', 6);
define ('SQLITE_NOMEM', 7);
define ('SQLITE_READONLY', 8);
define ('SQLITE_INTERRUPT', 9);
define ('SQLITE_IOERR', 10);
define ('SQLITE_CORRUPT', 11);
define ('SQLITE_NOTFOUND', 12);
define ('SQLITE_FULL', 13);
define ('SQLITE_CANTOPEN', 14);
define ('SQLITE_PROTOCOL', 15);
define ('SQLITE_EMPTY', 16);
define ('SQLITE_SCHEMA', 17);
define ('SQLITE_TOOBIG', 18);
define ('SQLITE_CONSTRAINT', 19);
define ('SQLITE_MISMATCH', 20);
define ('SQLITE_MISUSE', 21);
define ('SQLITE_NOLFS', 22);
define ('SQLITE_AUTH', 23);
define ('SQLITE_NOTADB', 26);
define ('SQLITE_FORMAT', 24);
define ('SQLITE_ROW', 100);
define ('SQLITE_DONE', 101);

// End of SQLite v.2.0-dev

// Start of sysvshm v.

/**
 * Creates or open a shared memory segment
 * @link http://php.net/manual/en/function.shm-attach.php
 * @param key int
 * @param memsize int[optional]
 * @param perm int[optional]
 * @return int a shared memory segment identifier.
 */
function shm_attach ($key, $memsize = null, $perm = null) {}

/**
 * Removes shared memory from Unix systems
 * @link http://php.net/manual/en/function.shm-remove.php
 * @param shm_identifier int
 * @return bool
 */
function shm_remove ($shm_identifier) {}

/**
 * Disconnects from shared memory segment
 * @link http://php.net/manual/en/function.shm-detach.php
 * @param shm_identifier int
 * @return bool
 */
function shm_detach ($shm_identifier) {}

/**
 * Inserts or updates a variable in shared memory
 * @link http://php.net/manual/en/function.shm-put-var.php
 * @param shm_identifier int
 * @param variable_key int
 * @param variable mixed
 * @return bool
 */
function shm_put_var ($shm_identifier, $variable_key, $variable) {}

/**
 * Returns a variable from shared memory
 * @link http://php.net/manual/en/function.shm-get-var.php
 * @param shm_identifier int
 * @param variable_key int
 * @return mixed the variable with the given key.
 */
function shm_get_var ($shm_identifier, $variable_key) {}

/**
 * Removes a variable from shared memory
 * @link http://php.net/manual/en/function.shm-remove-var.php
 * @param shm_identifier int
 * @param variable_key int
 * @return bool
 */
function shm_remove_var ($shm_identifier, $variable_key) {}

// End of sysvshm v.

// Start of xmlreader v.0.1

/**
 * @link http://php.net/manual/en/ref.xmlreader.php
 */
class XMLReader  {
	const NONE = 0;
	const ELEMENT = 1;
	const ATTRIBUTE = 2;
	const TEXT = 3;
	const CDATA = 4;
	const ENTITY_REF = 5;
	const ENTITY = 6;
	const PI = 7;
	const COMMENT = 8;
	const DOC = 9;
	const DOC_TYPE = 10;
	const DOC_FRAGMENT = 11;
	const NOTATION = 12;
	const WHITESPACE = 13;
	const SIGNIFICANT_WHITESPACE = 14;
	const END_ELEMENT = 15;
	const END_ENTITY = 16;
	const XML_DECLARATION = 17;
	const LOADDTD = 1;
	const DEFAULTATTRS = 2;
	const VALIDATE = 3;
	const SUBST_ENTITIES = 4;


	/**
	 * Close the XMLReader input
	 * @link http://php.net/manual/en/function.xmlreader-close.php
	 * @return bool
	 */
	public function close () {}

	/**
	 * Get the value of a named attribute
	 * @link http://php.net/manual/en/function.xmlreader-getattribute.php
	 * @param name string
	 * @return string
	 */
	public function getAttribute ($name) {}

	/**
	 * Get the value of an attribute by index
	 * @link http://php.net/manual/en/function.xmlreader-getattributeno.php
	 * @param index int
	 * @return string
	 */
	public function getAttributeNo ($index) {}

	/**
	 * Get the value of an attribute by localname and URI
	 * @link http://php.net/manual/en/function.xmlreader-getattributens.php
	 * @param localName string
	 * @param namespaceURI string
	 * @return string
	 */
	public function getAttributeNs ($localName, $namespaceURI) {}

	/**
	 * Indicates if specified property has been set
	 * @link http://php.net/manual/en/function.xmlreader-getparserproperty.php
	 * @param property int
	 * @return bool
	 */
	public function getParserProperty ($property) {}

	/**
	 * Indicates if the parsed document is valid
	 * @link http://php.net/manual/en/function.xmlreader-isvalid.php
	 * @return bool
	 */
	public function isValid () {}

	/**
	 * Lookup namespace for a prefix
	 * @link http://php.net/manual/en/function.xmlreader-lookupnamespace.php
	 * @param prefix string
	 * @return bool
	 */
	public function lookupNamespace ($prefix) {}

	/**
	 * Move cursor to an attribute by index
	 * @link http://php.net/manual/en/function.xmlreader-movetoattributeno.php
	 * @param index int
	 * @return bool
	 */
	public function moveToAttributeNo ($index) {}

	/**
	 * Move cursor to a named attribute
	 * @link http://php.net/manual/en/function.xmlreader-movetoattribute.php
	 * @param name string
	 * @return bool
	 */
	public function moveToAttribute ($name) {}

	/**
	 * Move cursor to a named attribute
	 * @link http://php.net/manual/en/function.xmlreader-movetoattributens.php
	 * @param localName string
	 * @param namespaceURI string
	 * @return bool
	 */
	public function moveToAttributeNs ($localName, $namespaceURI) {}

	/**
	 * Position cursor on the parent Element of current Attribute
	 * @link http://php.net/manual/en/function.xmlreader-movetoelement.php
	 * @return bool
	 */
	public function moveToElement () {}

	/**
	 * Position cursor on the first Attribute
	 * @link http://php.net/manual/en/function.xmlreader-movetofirstattribute.php
	 * @return bool
	 */
	public function moveToFirstAttribute () {}

	/**
	 * Position cursor on the next Attribute
	 * @link http://php.net/manual/en/function.xmlreader-movetonextattribute.php
	 * @return bool
	 */
	public function moveToNextAttribute () {}

	/**
	 * Set the URI containing the XML to parse
	 * @link http://php.net/manual/en/function.xmlreader-open.php
	 * @param URI string
	 * @param encoding string[optional]
	 * @param options int[optional]
	 * @return bool
	 */
	public function open ($URI, $encoding = null, $options = null) {}

	/**
	 * Move to next node in document
	 * @link http://php.net/manual/en/function.xmlreader-read.php
	 * @return bool
	 */
	public function read () {}

	/**
	 * Move cursor to next node skipping all subtrees
	 * @link http://php.net/manual/en/function.xmlreader-next.php
	 * @param localname string[optional]
	 * @return bool
	 */
	public function next ($localname = null) {}

	public function readInnerXml () {}

	public function readOuterXml () {}

	public function readString () {}

	/**
	 * @param filename
	 */
	public function setSchema ($filename) {}

	/**
	 * Set or Unset parser options
	 * @link http://php.net/manual/en/function.xmlreader-setparserproperty.php
	 * @param property int
	 * @param value bool
	 * @return bool
	 */
	public function setParserProperty ($property, $value) {}

	/**
	 * Set the filename or URI for a RelaxNG Schema
	 * @link http://php.net/manual/en/function.xmlreader-setrelaxngschema.php
	 * @param filename string
	 * @return bool
	 */
	public function setRelaxNGSchema ($filename) {}

	/**
	 * Set the data containing a RelaxNG Schema
	 * @link http://php.net/manual/en/function.xmlreader-setrelaxngschemasource.php
	 * @param source string
	 * @return bool
	 */
	public function setRelaxNGSchemaSource ($source) {}

	/**
	 * Set the data containing the XML to parse
	 * @link http://php.net/manual/en/function.xmlreader-xml.php
	 * @param source string
	 * @param encoding string[optional]
	 * @param options int[optional]
	 * @return bool
	 */
	public function XML ($source, $encoding = null, $options = null) {}

	/**
	 * Returns a copy of the current node as a DOM object
	 * @link http://php.net/manual/en/function.xmlreader-expand.php
	 * @return DOMNode
	 */
	public function expand () {}

}
// End of xmlreader v.0.1

// Start of zip v.1.4.0

class ZipArchive  {
	const CREATE = 1;
	const EXCL = 2;
	const CHECKCONS = 4;
	const OVERWRITE = 8;
	const FL_NOCASE = 1;
	const FL_NODIR = 2;
	const FL_COMPRESSED = 4;
	const FL_UNCHANGED = 8;
	const CM_DEFAULT = -1;
	const CM_STORE = 0;
	const CM_SHRINK = 1;
	const CM_REDUCE_1 = 2;
	const CM_REDUCE_2 = 3;
	const CM_REDUCE_3 = 4;
	const CM_REDUCE_4 = 5;
	const CM_IMPLODE = 6;
	const CM_DEFLATE = 8;
	const CM_DEFLATE64 = 9;
	const CM_PKWARE_IMPLODE = 10;
	const ER_OK = 0;
	const ER_MULTIDISK = 1;
	const ER_RENAME = 2;
	const ER_CLOSE = 3;
	const ER_SEEK = 4;
	const ER_READ = 5;
	const ER_WRITE = 6;
	const ER_CRC = 7;
	const ER_ZIPCLOSED = 8;
	const ER_NOENT = 9;
	const ER_EXISTS = 10;
	const ER_OPEN = 11;
	const ER_TMPOPEN = 12;
	const ER_ZLIB = 13;
	const ER_MEMORY = 14;
	const ER_CHANGED = 15;
	const ER_COMPNOTSUPP = 16;
	const ER_EOF = 17;
	const ER_INVAL = 18;
	const ER_NOZIP = 19;
	const ER_INTERNAL = 20;
	const ER_INCONS = 21;
	const ER_REMOVE = 22;
	const ER_DELETED = 23;


	/**
	 * Open a ZIP file archive
	 * @link http://php.net/manual/en/function.ziparchive-open.php
	 * @param filename string
	 * @param flags int[optional]
	 * @return mixed true on success or the error code.
	 */
	public function open ($filename, $flags = null) {}

	/**
	 * Close the active archive (opened or newly created)
	 * @link http://php.net/manual/en/function.ziparchive-close.php
	 * @return bool
	 */
	public function close () {}

	/**
	 * Add a new directory
	 * @link http://php.net/manual/en/function.ziparchive-addemptydir.php
	 * @param dirname string
	 * @return bool
	 */
	public function addEmptyDir ($dirname) {}

	/**
	 * Add a file to a ZIP archive using its contents
	 * @link http://php.net/manual/en/function.ziparchive-addfromstring.php
	 * @param localname string
	 * @param contents string
	 * @return bool
	 */
	public function addFromString ($localname, $contents) {}

	/**
	 * Adds a file to a ZIP archive from the given path
	 * @link http://php.net/manual/en/function.ziparchive-addfile.php
	 * @param filename string
	 * @param localname string[optional]
	 * @return bool
	 */
	public function addFile ($filename, $localname = null) {}

	/**
	 * Renames an entry defined by its index
	 * @link http://php.net/manual/en/function.ziparchive-renameindex.php
	 * @param index int
	 * @param newname string
	 * @return bool
	 */
	public function renameIndex ($index, $newname) {}

	/**
	 * Renames an entry defined by its name
	 * @link http://php.net/manual/en/function.ziparchive-renamename.php
	 * @param name string
	 * @param newname string
	 * @return bool
	 */
	public function renameName ($name, $newname) {}

	/**
	 * Set the comment of a ZIP archive
	 * @link http://php.net/manual/en/function.ziparchive-setarchivecomment.php
	 * @param comment string
	 * @return mixed
	 */
	public function setArchiveComment ($comment) {}

	/**
	 * Returns the Zip archive comment
	 * @link http://php.net/manual/en/function.ziparchive-getarchivecomment.php
	 * @return string the Zip archive comment or false on failure.
	 */
	public function getArchiveComment () {}

	/**
	 * Set the comment of an entry defined by its index
	 * @link http://php.net/manual/en/function.ziparchive-setcommentindex.php
	 * @param index int
	 * @param comment string
	 * @return mixed
	 */
	public function setCommentIndex ($index, $comment) {}

	/**
	 * Set the comment of an entry defined by its name
	 * @link http://php.net/manual/en/function.ziparchive-setCommentName.php
	 * @param name string
	 * @param comment string
	 * @return mixed
	 */
	public function setCommentName ($name, $comment) {}

	/**
	 * Returns the comment of an entry using the entry index
	 * @link http://php.net/manual/en/function.ziparchive-getcommentindex.php
	 * @param index int
	 * @param flags int[optional]
	 * @return string the comment on success or false on failure.
	 */
	public function getCommentIndex ($index, $flags = null) {}

	/**
	 * Returns the comment of an entry using the entry name
	 * @link http://php.net/manual/en/function.ziparchive-getcommentname.php
	 * @param name string
	 * @param flags int[optional]
	 * @return string the comment on success or false on failure.
	 */
	public function getCommentName ($name, $flags = null) {}

	/**
	 * delete an entry in the archive using its index
	 * @link http://php.net/manual/en/function.ziparchive-deleteindex.php
	 * @param index int
	 * @return bool
	 */
	public function deleteIndex ($index) {}

	/**
	 * delete an entry in the archive using its name
	 * @link http://php.net/manual/en/function.ziparchive-deletename.php
	 * @param name string
	 * @return bool
	 */
	public function deleteName ($name) {}

	/**
	 * Get the details of an entry defined by its name.
	 * @link http://php.net/manual/en/function.ziparchive-statname.php
	 * @param name name
	 * @param flags int[optional]
	 * @return mixed an array containing the entry details or false on failure.
	 */
	public function statName ($name, $flags = null) {}

	/**
	 * Get the details of an entry defined by its index.
	 * @link http://php.net/manual/en/function.ziparchive-statindex.php
	 * @param index int
	 * @param flags int[optional]
	 * @return mixed an array containing the entry details or false on failure.
	 */
	public function statIndex ($index, $flags = null) {}

	/**
	 * Returns the index of the entry in the archive
	 * @link http://php.net/manual/en/function.ziparchive-locatename.php
	 * @param name string
	 * @param flags int[optional]
	 * @return mixed the index of the entry on success or false on failure.
	 */
	public function locateName ($name, $flags = null) {}

	/**
	 * Returns the name of an entry using its index
	 * @link http://php.net/manual/en/function.ziparchive-getnameindex.php
	 * @param index int
	 * @return string the name on success or false on failure.
	 */
	public function getNameIndex ($index) {}

	/**
	 * Revert all global changes done in the archive.
	 * @link http://php.net/manual/en/function.ziparchive-unchangearchive.php
	 * @return mixed
	 */
	public function unchangeArchive () {}

	/**
	 * Undo all changes done in the archive.
	 * @link http://php.net/manual/en/function.ziparchive-unchangeall.php
	 * @return mixed
	 */
	public function unchangeAll () {}

	/**
	 * Revert all changes done to an entry at the given index.
	 * @link http://php.net/manual/en/function.ziparchive-unchangeindex.php
	 * @param index int
	 * @return mixed
	 */
	public function unchangeIndex ($index) {}

	/**
	 * Revert all changes done to an entry with the given name.
	 * @link http://php.net/manual/en/function.ziparchive-unchangename.php
	 * @param name string
	 * @return mixed
	 */
	public function unchangeName ($name) {}

	/**
	 * Extract the archive contents
	 * @link http://php.net/manual/en/function.ziparchive-extractto.php
	 * @param destination string
	 * @param entries mixed[optional]
	 * @return mixed
	 */
	public function extractTo ($destination, $entries = null) {}

	/**
	 * Returns the entry contents using its name.
	 * @link http://php.net/manual/en/function.ziparchive-getfromname.php
	 * @param name string
	 * @param flags int[optional]
	 * @return mixed the contents of the entry on success or false on failure.
	 */
	public function getFromName ($name, $flags = null) {}

	/**
	 * Returns the entry contents using its index.
	 * @link http://php.net/manual/en/function.ziparchive-getfromindex.php
	 * @param index int
	 * @param flags int[optional]
	 * @return mixed the contents of the entry on success or false on failure.
	 */
	public function getFromIndex ($index, $flags = null) {}

	/**
	 * Get a file handler to the entry defined by its name (read only).
	 * @link http://php.net/manual/en/function.ziparchive-getstream.php
	 * @param name string
	 * @return resource a file pointer (resource) on success or false on failure.
	 */
	public function getStream ($name) {}

}

/**
 * Open a ZIP file archive
 * @link http://php.net/manual/en/function.zip-open.php
 * @param filename string
 * @return mixed a resource handle for later use with
 */
function zip_open ($filename) {}

/**
 * Close a ZIP file archive
 * @link http://php.net/manual/en/function.zip-close.php
 * @param zip resource
 * @return void
 */
function zip_close ($zip) {}

/**
 * Read next entry in a ZIP file archive
 * @link http://php.net/manual/en/function.zip-read.php
 * @param zip resource
 * @return mixed a directory entry resource for later use with the
 */
function zip_read ($zip) {}

/**
 * Open a directory entry for reading
 * @link http://php.net/manual/en/function.zip-entry-open.php
 * @param zip resource
 * @param zip_entry resource
 * @param mode string[optional]
 * @return bool
 */
function zip_entry_open ($zip, $zip_entry, $mode = null) {}

/**
 * Close a directory entry
 * @link http://php.net/manual/en/function.zip-entry-close.php
 * @param zip_entry resource
 * @return bool
 */
function zip_entry_close ($zip_entry) {}

/**
 * Read from an open directory entry
 * @link http://php.net/manual/en/function.zip-entry-read.php
 * @param zip_entry resource
 * @param length int[optional]
 * @return string the data read, or false if the end of the file is
 */
function zip_entry_read ($zip_entry, $length = null) {}

/**
 * Retrieve the actual file size of a directory entry
 * @link http://php.net/manual/en/function.zip-entry-filesize.php
 * @param zip_entry resource
 * @return int
 */
function zip_entry_filesize ($zip_entry) {}

/**
 * Retrieve the name of a directory entry
 * @link http://php.net/manual/en/function.zip-entry-name.php
 * @param zip_entry resource
 * @return string
 */
function zip_entry_name ($zip_entry) {}

/**
 * Retrieve the compressed size of a directory entry
 * @link http://php.net/manual/en/function.zip-entry-compressedsize.php
 * @param zip_entry resource
 * @return int
 */
function zip_entry_compressedsize ($zip_entry) {}

/**
 * Retrieve the compression method of a directory entry
 * @link http://php.net/manual/en/function.zip-entry-compressionmethod.php
 * @param zip_entry resource
 * @return string
 */
function zip_entry_compressionmethod ($zip_entry) {}

// End of zip v.1.4.0

// Start of calendar v.

/**
 * Converts Julian Day Count to Gregorian date
 * @link http://php.net/manual/en/function.jdtogregorian.php
 * @param julianday int
 * @return string
 */
function jdtogregorian ($julianday) {}

/**
 * Converts a Gregorian date to Julian Day Count
 * @link http://php.net/manual/en/function.gregoriantojd.php
 * @param month int
 * @param day int
 * @param year int
 * @return int
 */
function gregoriantojd ($month, $day, $year) {}

/**
 * Converts a Julian Day Count to a Julian Calendar Date
 * @link http://php.net/manual/en/function.jdtojulian.php
 * @param julianday int
 * @return string
 */
function jdtojulian ($julianday) {}

/**
 * Converts a Julian Calendar date to Julian Day Count
 * @link http://php.net/manual/en/function.juliantojd.php
 * @param month int
 * @param day int
 * @param year int
 * @return int
 */
function juliantojd ($month, $day, $year) {}

/**
 * Converts a Julian day count to a Jewish calendar date
 * @link http://php.net/manual/en/function.jdtojewish.php
 * @param juliandaycount int
 * @param hebrew bool[optional]
 * @param fl int[optional]
 * @return string
 */
function jdtojewish ($juliandaycount, $hebrew = null, $fl = null) {}

/**
 * Converts a date in the Jewish Calendar to Julian Day Count
 * @link http://php.net/manual/en/function.jewishtojd.php
 * @param month int
 * @param day int
 * @param year int
 * @return int
 */
function jewishtojd ($month, $day, $year) {}

/**
 * Converts a Julian Day Count to the French Republican Calendar
 * @link http://php.net/manual/en/function.jdtofrench.php
 * @param juliandaycount int
 * @return string
 */
function jdtofrench ($juliandaycount) {}

/**
 * Converts a date from the French Republican Calendar to a Julian Day Count
 * @link http://php.net/manual/en/function.frenchtojd.php
 * @param month int
 * @param day int
 * @param year int
 * @return int
 */
function frenchtojd ($month, $day, $year) {}

/**
 * Returns the day of the week
 * @link http://php.net/manual/en/function.jddayofweek.php
 * @param julianday int
 * @param mode int[optional]
 * @return mixed
 */
function jddayofweek ($julianday, $mode = null) {}

/**
 * Returns a month name
 * @link http://php.net/manual/en/function.jdmonthname.php
 * @param julianday int
 * @param mode int
 * @return string
 */
function jdmonthname ($julianday, $mode) {}

/**
 * Get Unix timestamp for midnight on Easter of a given year
 * @link http://php.net/manual/en/function.easter-date.php
 * @param year int[optional]
 * @return int
 */
function easter_date ($year = null) {}

/**
 * Get number of days after March 21 on which Easter falls for a given year
 * @link http://php.net/manual/en/function.easter-days.php
 * @param year int[optional]
 * @param method int[optional]
 * @return int
 */
function easter_days ($year = null, $method = null) {}

/**
 * Convert Unix timestamp to Julian Day
 * @link http://php.net/manual/en/function.unixtojd.php
 * @param timestamp int[optional]
 * @return int
 */
function unixtojd ($timestamp = null) {}

/**
 * Convert Julian Day to Unix timestamp
 * @link http://php.net/manual/en/function.jdtounix.php
 * @param jday int
 * @return int
 */
function jdtounix ($jday) {}

/**
 * Converts from a supported calendar to Julian Day Count
 * @link http://php.net/manual/en/function.cal-to-jd.php
 * @param calendar int
 * @param month int
 * @param day int
 * @param year int
 * @return int
 */
function cal_to_jd ($calendar, $month, $day, $year) {}

/**
 * Converts from Julian Day Count to a supported calendar
 * @link http://php.net/manual/en/function.cal-from-jd.php
 * @param jd int
 * @param calendar int
 * @return array an array containing calendar information like month, day, year,
 */
function cal_from_jd ($jd, $calendar) {}

/**
 * Return the number of days in a month for a given year and calendar
 * @link http://php.net/manual/en/function.cal-days-in-month.php
 * @param calendar int
 * @param month int
 * @param year int
 * @return int
 */
function cal_days_in_month ($calendar, $month, $year) {}

/**
 * Returns information about a particular calendar
 * @link http://php.net/manual/en/function.cal-info.php
 * @param calendar int[optional]
 * @return array
 */
function cal_info ($calendar = null) {}

define ('CAL_GREGORIAN', 0);
define ('CAL_JULIAN', 1);
define ('CAL_JEWISH', 2);
define ('CAL_FRENCH', 3);
define ('CAL_NUM_CALS', 4);
define ('CAL_DOW_DAYNO', 0);
define ('CAL_DOW_SHORT', 1);
define ('CAL_DOW_LONG', 2);
define ('CAL_MONTH_GREGORIAN_SHORT', 0);
define ('CAL_MONTH_GREGORIAN_LONG', 1);
define ('CAL_MONTH_JULIAN_SHORT', 2);
define ('CAL_MONTH_JULIAN_LONG', 3);
define ('CAL_MONTH_JEWISH', 4);
define ('CAL_MONTH_FRENCH', 5);
define ('CAL_EASTER_DEFAULT', 0);
define ('CAL_EASTER_ROMAN', 1);
define ('CAL_EASTER_ALWAYS_GREGORIAN', 2);
define ('CAL_EASTER_ALWAYS_JULIAN', 3);
define ('CAL_JEWISH_ADD_ALAFIM_GERESH', 2);
define ('CAL_JEWISH_ADD_ALAFIM', 4);
define ('CAL_JEWISH_ADD_GERESHAYIM', 8);

// End of calendar v.

// Start of exif v.1.4 $Id: phpFunctions5.php,v 1.17 2007/10/10 17:02:30 mspector Exp $

/**
 * Reads the <acronym>EXIF</acronym> headers from <acronym>JPEG</acronym> or <acronym>TIFF</acronym>
 * @link http://php.net/manual/en/function.exif-read-data.php
 * @param filename string
 * @param sections string[optional]
 * @param arrays bool[optional]
 * @param thumbnail bool[optional]
 * @return array
 */
function exif_read_data ($filename, $sections = null, $arrays = null, $thumbnail = null) {}

/**
 * &Alias; <function>exif_read_data</function>
 * @link http://php.net/manual/en/function.read-exif-data.php
 * @param filename
 * @param sections_needed[optional]
 * @param sub_arrays[optional]
 * @param read_thumbnail[optional]
 */
function read_exif_data ($filename, $sections_needed, $sub_arrays, $read_thumbnail) {}

/**
 * Get the header name for an index
 * @link http://php.net/manual/en/function.exif-tagname.php
 * @param index string
 * @return string the header name, or false if index is
 */
function exif_tagname ($index) {}

/**
 * Retrieve the embedded thumbnail of a TIFF or JPEG image
 * @link http://php.net/manual/en/function.exif-thumbnail.php
 * @param filename string
 * @param width int[optional]
 * @param height int[optional]
 * @param imagetype int[optional]
 * @return string the embedded thumbnail, or false if the image contains no 
 */
function exif_thumbnail ($filename, &$width = null, &$height = null, &$imagetype = null) {}

/**
 * Determine the type of an image
 * @link http://php.net/manual/en/function.exif-imagetype.php
 * @param filename string
 * @return int
 */
function exif_imagetype ($filename) {}

define ('EXIF_USE_MBSTRING', 0);

// End of exif v.1.4 $Id: phpFunctions5.php,v 1.17 2007/10/10 17:02:30 mspector Exp $

// Start of gmp v.

/**
 * Create GMP number
 * @link http://php.net/manual/en/function.gmp-init.php
 * @param number mixed
 * @param base int[optional]
 * @return resource
 */
function gmp_init ($number, $base = null) {}

/**
 * Convert GMP number to integer
 * @link http://php.net/manual/en/function.gmp-intval.php
 * @param gmpnumber resource
 * @return int
 */
function gmp_intval ($gmpnumber) {}

/**
 * Convert GMP number to string
 * @link http://php.net/manual/en/function.gmp-strval.php
 * @param gmpnumber resource
 * @param base int[optional]
 * @return string
 */
function gmp_strval ($gmpnumber, $base = null) {}

/**
 * Add numbers
 * @link http://php.net/manual/en/function.gmp-add.php
 * @param a resource
 * @param b resource
 * @return resource
 */
function gmp_add ($a, $b) {}

/**
 * Subtract numbers
 * @link http://php.net/manual/en/function.gmp-sub.php
 * @param a resource
 * @param b resource
 * @return resource
 */
function gmp_sub ($a, $b) {}

/**
 * Multiply numbers
 * @link http://php.net/manual/en/function.gmp-mul.php
 * @param a resource
 * @param b resource
 * @return resource
 */
function gmp_mul ($a, $b) {}

/**
 * Divide numbers and get quotient and remainder
 * @link http://php.net/manual/en/function.gmp-div-qr.php
 * @param n resource
 * @param d resource
 * @param round int[optional]
 * @return array an array, with the first
 */
function gmp_div_qr ($n, $d, $round = null) {}

/**
 * Divide numbers
 * @link http://php.net/manual/en/function.gmp-div-q.php
 * @param a resource
 * @param b resource
 * @param round int[optional]
 * @return resource
 */
function gmp_div_q ($a, $b, $round = null) {}

/**
 * Remainder of the division of numbers
 * @link http://php.net/manual/en/function.gmp-div-r.php
 * @param n resource
 * @param d resource
 * @param round int[optional]
 * @return resource
 */
function gmp_div_r ($n, $d, $round = null) {}

/**
 * &Alias; <function>gmp_div_q</function>
 * @link http://php.net/manual/en/function.gmp-div.php
 * @param a
 * @param b
 * @param round[optional]
 */
function gmp_div ($a, $b, $round) {}

/**
 * Modulo operation
 * @link http://php.net/manual/en/function.gmp-mod.php
 * @param n resource
 * @param d resource
 * @return resource
 */
function gmp_mod ($n, $d) {}

/**
 * Exact division of numbers
 * @link http://php.net/manual/en/function.gmp-divexact.php
 * @param n resource
 * @param d resource
 * @return resource
 */
function gmp_divexact ($n, $d) {}

/**
 * Negate number
 * @link http://php.net/manual/en/function.gmp-neg.php
 * @param a resource
 * @return resource -a, as a GMP number.
 */
function gmp_neg ($a) {}

/**
 * Absolute value
 * @link http://php.net/manual/en/function.gmp-abs.php
 * @param a resource
 * @return resource the absolute value of a, as a GMP number.
 */
function gmp_abs ($a) {}

/**
 * Factorial
 * @link http://php.net/manual/en/function.gmp-fact.php
 * @param a int
 * @return resource
 */
function gmp_fact ($a) {}

/**
 * Calculate square root
 * @link http://php.net/manual/en/function.gmp-sqrt.php
 * @param a resource
 * @return resource
 */
function gmp_sqrt ($a) {}

/**
 * Square root with remainder
 * @link http://php.net/manual/en/function.gmp-sqrtrem.php
 * @param a resource
 * @return array array where first element is the integer square root of
 */
function gmp_sqrtrem ($a) {}

/**
 * Raise number into power
 * @link http://php.net/manual/en/function.gmp-pow.php
 * @param base resource
 * @param exp int
 * @return resource
 */
function gmp_pow ($base, $exp) {}

/**
 * Raise number into power with modulo
 * @link http://php.net/manual/en/function.gmp-powm.php
 * @param base resource
 * @param exp resource
 * @param mod resource
 * @return resource
 */
function gmp_powm ($base, $exp, $mod) {}

/**
 * Perfect square check
 * @link http://php.net/manual/en/function.gmp-perfect-square.php
 * @param a resource
 * @return bool true if a is a perfect square,
 */
function gmp_perfect_square ($a) {}

/**
 * Check if number is "probably prime"
 * @link http://php.net/manual/en/function.gmp-prob-prime.php
 * @param a resource
 * @param reps int[optional]
 * @return int
 */
function gmp_prob_prime ($a, $reps = null) {}

/**
 * Calculate GCD
 * @link http://php.net/manual/en/function.gmp-gcd.php
 * @param a resource
 * @param b resource
 * @return resource
 */
function gmp_gcd ($a, $b) {}

/**
 * Calculate GCD and multipliers
 * @link http://php.net/manual/en/function.gmp-gcdext.php
 * @param a resource
 * @param b resource
 * @return array
 */
function gmp_gcdext ($a, $b) {}

/**
 * Inverse by modulo
 * @link http://php.net/manual/en/function.gmp-invert.php
 * @param a resource
 * @param b resource
 * @return resource
 */
function gmp_invert ($a, $b) {}

/**
 * Jacobi symbol
 * @link http://php.net/manual/en/function.gmp-jacobi.php
 * @param a resource
 * @param p resource
 * @return int
 */
function gmp_jacobi ($a, $p) {}

/**
 * Legendre symbol
 * @link http://php.net/manual/en/function.gmp-legendre.php
 * @param a resource
 * @param p resource
 * @return int
 */
function gmp_legendre ($a, $p) {}

/**
 * Compare numbers
 * @link http://php.net/manual/en/function.gmp-cmp.php
 * @param a resource
 * @param b resource
 * @return int a positive value if a &gt; b, zero if
 */
function gmp_cmp ($a, $b) {}

/**
 * Sign of number
 * @link http://php.net/manual/en/function.gmp-sign.php
 * @param a resource
 * @return int 1 if a is positive,
 */
function gmp_sign ($a) {}

/**
 * Random number
 * @link http://php.net/manual/en/function.gmp-random.php
 * @param limiter int
 * @return resource
 */
function gmp_random ($limiter) {}

/**
 * Logical AND
 * @link http://php.net/manual/en/function.gmp-and.php
 * @param a resource
 * @param b resource
 * @return resource
 */
function gmp_and ($a, $b) {}

/**
 * Logical OR
 * @link http://php.net/manual/en/function.gmp-or.php
 * @param a resource
 * @param b resource
 * @return resource
 */
function gmp_or ($a, $b) {}

/**
 * Calculates one's complement
 * @link http://php.net/manual/en/function.gmp-com.php
 * @param a resource
 * @return resource the one's complement of a, as a GMP number.
 */
function gmp_com ($a) {}

/**
 * Logical XOR
 * @link http://php.net/manual/en/function.gmp-xor.php
 * @param a resource
 * @param b resource
 * @return resource
 */
function gmp_xor ($a, $b) {}

/**
 * Set bit
 * @link http://php.net/manual/en/function.gmp-setbit.php
 * @param a resource
 * @param index int
 * @param set_clear bool[optional]
 * @return void
 */
function gmp_setbit (&$a, $index, $set_clear = null) {}

/**
 * Clear bit
 * @link http://php.net/manual/en/function.gmp-clrbit.php
 * @param a resource
 * @param index int
 * @return void
 */
function gmp_clrbit (&$a, $index) {}

/**
 * Scan for 0
 * @link http://php.net/manual/en/function.gmp-scan0.php
 * @param a resource
 * @param start int
 * @return int the index of the found bit, as an integer. The
 */
function gmp_scan0 ($a, $start) {}

/**
 * Scan for 1
 * @link http://php.net/manual/en/function.gmp-scan1.php
 * @param a resource
 * @param start int
 * @return int the index of the found bit, as an integer.
 */
function gmp_scan1 ($a, $start) {}

/**
 * Population count
 * @link http://php.net/manual/en/function.gmp-popcount.php
 * @param a resource
 * @return int
 */
function gmp_popcount ($a) {}

/**
 * Hamming distance
 * @link http://php.net/manual/en/function.gmp-hamdist.php
 * @param a resource
 * @param b resource
 * @return int
 */
function gmp_hamdist ($a, $b) {}

/**
 * Find next prime number
 * @link http://php.net/manual/en/function.gmp-nextprime.php
 * @param a int
 * @return resource
 */
function gmp_nextprime ($a) {}

define ('GMP_ROUND_ZERO', 0);
define ('GMP_ROUND_PLUSINF', 1);
define ('GMP_ROUND_MINUSINF', 2);
define ('GMP_VERSION', '4.2.1');

// End of gmp v.

// Start of imap v.

/**
 * Open an IMAP stream to a mailbox
 * @link http://php.net/manual/en/function.imap-open.php
 * @param mailbox string
 * @param username string
 * @param password string
 * @param options int[optional]
 * @param n_retries int[optional]
 * @return resource an IMAP stream on success or false on error.
 */
function imap_open ($mailbox, $username, $password, $options = null, $n_retries = null) {}

/**
 * Reopen IMAP stream to new mailbox
 * @link http://php.net/manual/en/function.imap-reopen.php
 * @param imap_stream resource
 * @param mailbox string
 * @param options int[optional]
 * @param n_retries int[optional]
 * @return bool
 */
function imap_reopen ($imap_stream, $mailbox, $options = null, $n_retries = null) {}

/**
 * Close an IMAP stream
 * @link http://php.net/manual/en/function.imap-close.php
 * @param imap_stream resource
 * @param flag int[optional]
 * @return bool
 */
function imap_close ($imap_stream, $flag = null) {}

/**
 * Gets the number of messages in the current mailbox
 * @link http://php.net/manual/en/function.imap-num-msg.php
 * @param imap_stream resource
 * @return int
 */
function imap_num_msg ($imap_stream) {}

/**
 * Gets the number of recent messages in current mailbox
 * @link http://php.net/manual/en/function.imap-num-recent.php
 * @param imap_stream resource
 * @return int the number of recent messages in the current mailbox, as an
 */
function imap_num_recent ($imap_stream) {}

/**
 * Returns headers for all messages in a mailbox
 * @link http://php.net/manual/en/function.imap-headers.php
 * @param imap_stream resource
 * @return array an array of string formatted with header info. One
 */
function imap_headers ($imap_stream) {}

/**
 * Read the header of the message
 * @link http://php.net/manual/en/function.imap-headerinfo.php
 * @param imap_stream resource
 * @param msg_number int
 * @param fromlength int[optional]
 * @param subjectlength int[optional]
 * @param defaulthost string[optional]
 * @return object the information in an object with following properties:
 */
function imap_headerinfo ($imap_stream, $msg_number, $fromlength = null, $subjectlength = null, $defaulthost = null) {}

/**
 * Parse mail headers from a string
 * @link http://php.net/manual/en/function.imap-rfc822-parse-headers.php
 * @param headers string
 * @param defaulthost string[optional]
 * @return object an object similar to the one returned by
 */
function imap_rfc822_parse_headers ($headers, $defaulthost = null) {}

/**
 * Returns a properly formatted email address given the mailbox, host, and personal info
 * @link http://php.net/manual/en/function.imap-rfc822-write-address.php
 * @param mailbox string
 * @param host string
 * @param personal string
 * @return string a string properly formatted email address as defined in 
 */
function imap_rfc822_write_address ($mailbox, $host, $personal) {}

/**
 * Parses an address string
 * @link http://php.net/manual/en/function.imap-rfc822-parse-adrlist.php
 * @param address string
 * @param default_host string
 * @return array an array of objects. The objects properties are:
 */
function imap_rfc822_parse_adrlist ($address, $default_host) {}

/**
 * Read the message body
 * @link http://php.net/manual/en/function.imap-body.php
 * @param imap_stream resource
 * @param msg_number int
 * @param options int[optional]
 * @return string the body of the specified message, as a string.
 */
function imap_body ($imap_stream, $msg_number, $options = null) {}

/**
 * Read the structure of a specified body section of a specific message
 * @link http://php.net/manual/en/function.imap-bodystruct.php
 * @param imap_stream resource
 * @param msg_number int
 * @param section string
 * @return object the information in an object, for a detailed description
 */
function imap_bodystruct ($imap_stream, $msg_number, $section) {}

/**
 * Fetch a particular section of the body of the message
 * @link http://php.net/manual/en/function.imap-fetchbody.php
 * @param imap_stream resource
 * @param msg_number int
 * @param part_number string
 * @param options int[optional]
 * @return string a particular section of the body of the specified messages as a
 */
function imap_fetchbody ($imap_stream, $msg_number, $part_number, $options = null) {}

/**
 * Save a specific body section to a file
 * @link http://php.net/manual/en/function.imap-savebody.php
 * @param imap_stream resource
 * @param file mixed
 * @param msg_number int
 * @param part_number string[optional]
 * @param options int[optional]
 * @return bool
 */
function imap_savebody ($imap_stream, $file, $msg_number, $part_number = null, $options = null) {}

/**
 * Returns header for a message
 * @link http://php.net/manual/en/function.imap-fetchheader.php
 * @param imap_stream resource
 * @param msg_number int
 * @param options int[optional]
 * @return string the header of the specified message as a text string.
 */
function imap_fetchheader ($imap_stream, $msg_number, $options = null) {}

/**
 * Read the structure of a particular message
 * @link http://php.net/manual/en/function.imap-fetchstructure.php
 * @param imap_stream resource
 * @param msg_number int
 * @param options int[optional]
 * @return object an object includes the envelope, internal date, size, flags and
 */
function imap_fetchstructure ($imap_stream, $msg_number, $options = null) {}

/**
 * Delete all messages marked for deletion
 * @link http://php.net/manual/en/function.imap-expunge.php
 * @param imap_stream resource
 * @return bool true.
 */
function imap_expunge ($imap_stream) {}

/**
 * Mark a message for deletion from current mailbox
 * @link http://php.net/manual/en/function.imap-delete.php
 * @param imap_stream int
 * @param msg_number int
 * @param options int[optional]
 * @return bool true.
 */
function imap_delete ($imap_stream, $msg_number, $options = null) {}

/**
 * Unmark the message which is marked deleted
 * @link http://php.net/manual/en/function.imap-undelete.php
 * @param imap_stream resource
 * @param msg_number int
 * @param flags int[optional]
 * @return bool
 */
function imap_undelete ($imap_stream, $msg_number, $flags = null) {}

/**
 * Check current mailbox
 * @link http://php.net/manual/en/function.imap-check.php
 * @param imap_stream resource
 * @return object the information in an object with following properties:
 */
function imap_check ($imap_stream) {}

/**
 * Copy specified messages to a mailbox
 * @link http://php.net/manual/en/function.imap-mail-copy.php
 * @param imap_stream resource
 * @param msglist string
 * @param mailbox string
 * @param options int[optional]
 * @return bool
 */
function imap_mail_copy ($imap_stream, $msglist, $mailbox, $options = null) {}

/**
 * Move specified messages to a mailbox
 * @link http://php.net/manual/en/function.imap-mail-move.php
 * @param imap_stream resource
 * @param msglist string
 * @param mailbox string
 * @param options int[optional]
 * @return bool
 */
function imap_mail_move ($imap_stream, $msglist, $mailbox, $options = null) {}

/**
 * Create a MIME message based on given envelope and body sections
 * @link http://php.net/manual/en/function.imap-mail-compose.php
 * @param envelope array
 * @param body array
 * @return string the MIME message.
 */
function imap_mail_compose (array $envelope, array $body) {}

/**
 * Create a new mailbox
 * @link http://php.net/manual/en/function.imap-createmailbox.php
 * @param imap_stream resource
 * @param mailbox string
 * @return bool
 */
function imap_createmailbox ($imap_stream, $mailbox) {}

/**
 * Rename an old mailbox to new mailbox
 * @link http://php.net/manual/en/function.imap-renamemailbox.php
 * @param imap_stream resource
 * @param old_mbox string
 * @param new_mbox string
 * @return bool
 */
function imap_renamemailbox ($imap_stream, $old_mbox, $new_mbox) {}

/**
 * Delete a mailbox
 * @link http://php.net/manual/en/function.imap-deletemailbox.php
 * @param imap_stream resource
 * @param mailbox string
 * @return bool
 */
function imap_deletemailbox ($imap_stream, $mailbox) {}

/**
 * Subscribe to a mailbox
 * @link http://php.net/manual/en/function.imap-subscribe.php
 * @param imap_stream resource
 * @param mailbox string
 * @return bool
 */
function imap_subscribe ($imap_stream, $mailbox) {}

/**
 * Unsubscribe from a mailbox
 * @link http://php.net/manual/en/function.imap-unsubscribe.php
 * @param imap_stream string
 * @param mailbox string
 * @return bool
 */
function imap_unsubscribe ($imap_stream, $mailbox) {}

/**
 * Append a string message to a specified mailbox
 * @link http://php.net/manual/en/function.imap-append.php
 * @param imap_stream resource
 * @param mailbox string
 * @param message string
 * @param options string[optional]
 * @return bool
 */
function imap_append ($imap_stream, $mailbox, $message, $options = null) {}

/**
 * Check if the IMAP stream is still active
 * @link http://php.net/manual/en/function.imap-ping.php
 * @param imap_stream resource
 * @return bool true if the stream is still alive, false otherwise.
 */
function imap_ping ($imap_stream) {}

/**
 * Decode BASE64 encoded text
 * @link http://php.net/manual/en/function.imap-base64.php
 * @param text string
 * @return string the decoded message as a string.
 */
function imap_base64 ($text) {}

/**
 * Convert a quoted-printable string to an 8 bit string
 * @link http://php.net/manual/en/function.imap-qprint.php
 * @param string string
 * @return string an 8 bits string.
 */
function imap_qprint ($string) {}

/**
 * Convert an 8bit string to a quoted-printable string
 * @link http://php.net/manual/en/function.imap-8bit.php
 * @param string string
 * @return string a quoted-printable string.
 */
function imap_8bit ($string) {}

/**
 * Convert an 8bit string to a base64 string
 * @link http://php.net/manual/en/function.imap-binary.php
 * @param string string
 * @return string a base64 encoded string.
 */
function imap_binary ($string) {}

/**
 * Converts MIME-encoded text to UTF-8
 * @link http://php.net/manual/en/function.imap-utf8.php
 * @param mime_encoded_text string
 * @return string an UTF-8 encoded string.
 */
function imap_utf8 ($mime_encoded_text) {}

/**
 * Returns status information on a mailbox
 * @link http://php.net/manual/en/function.imap-status.php
 * @param imap_stream resource
 * @param mailbox string
 * @param options int
 * @return object
 */
function imap_status ($imap_stream, $mailbox, $options) {}

/**
 * Get information about the current mailbox
 * @link http://php.net/manual/en/function.imap-mailboxmsginfo.php
 * @param imap_stream resource
 * @return object the information in an object with following properties:
 */
function imap_mailboxmsginfo ($imap_stream) {}

/**
 * Sets flags on messages
 * @link http://php.net/manual/en/function.imap-setflag-full.php
 * @param imap_stream resource
 * @param sequence string
 * @param flag string
 * @param options int[optional]
 * @return bool
 */
function imap_setflag_full ($imap_stream, $sequence, $flag, $options = null) {}

/**
 * Clears flags on messages
 * @link http://php.net/manual/en/function.imap-clearflag-full.php
 * @param imap_stream resource
 * @param sequence string
 * @param flag string
 * @param options string[optional]
 * @return bool
 */
function imap_clearflag_full ($imap_stream, $sequence, $flag, $options = null) {}

/**
 * Gets and sort messages
 * @link http://php.net/manual/en/function.imap-sort.php
 * @param imap_stream resource
 * @param criteria int
 * @param reverse int
 * @param options int[optional]
 * @param search_criteria string[optional]
 * @param charset string[optional]
 * @return array an array of message numbers sorted by the given
 */
function imap_sort ($imap_stream, $criteria, $reverse, $options = null, $search_criteria = null, $charset = null) {}

/**
 * This function returns the UID for the given message sequence number
 * @link http://php.net/manual/en/function.imap-uid.php
 * @param imap_stream resource
 * @param msg_number int
 * @return int
 */
function imap_uid ($imap_stream, $msg_number) {}

/**
 * Gets the message sequence number for the given UID
 * @link http://php.net/manual/en/function.imap-msgno.php
 * @param imap_stream resource
 * @param uid int
 * @return int the message sequence number for the given 
 */
function imap_msgno ($imap_stream, $uid) {}

/**
 * Read the list of mailboxes
 * @link http://php.net/manual/en/function.imap-list.php
 * @param imap_stream resource
 * @param ref string
 * @param pattern string
 * @return array an array containing the names of the mailboxes.
 */
function imap_list ($imap_stream, $ref, $pattern) {}

/**
 * List all the subscribed mailboxes
 * @link http://php.net/manual/en/function.imap-lsub.php
 * @param imap_stream resource
 * @param ref string
 * @param pattern string
 * @return array an array of all the subscribed mailboxes.
 */
function imap_lsub ($imap_stream, $ref, $pattern) {}

/**
 * Read an overview of the information in the headers of the given message
 * @link http://php.net/manual/en/function.imap-fetch-overview.php
 * @param imap_stream resource
 * @param sequence string
 * @param options int[optional]
 * @return array an array of objects describing one message header each.
 */
function imap_fetch_overview ($imap_stream, $sequence, $options = null) {}

/**
 * Returns all IMAP alert messages that have occurred
 * @link http://php.net/manual/en/function.imap-alerts.php
 * @return array an array of all of the IMAP alert messages generated or false if
 */
function imap_alerts () {}

/**
 * Returns all of the IMAP errors that have occured
 * @link http://php.net/manual/en/function.imap-errors.php
 * @return array
 */
function imap_errors () {}

/**
 * Gets the last IMAP error that occurred during this page request
 * @link http://php.net/manual/en/function.imap-last-error.php
 * @return string the full text of the last IMAP error message that occurred on the
 */
function imap_last_error () {}

/**
 * This function returns an array of messages matching the given search criteria
 * @link http://php.net/manual/en/function.imap-search.php
 * @param imap_stream resource
 * @param criteria string
 * @param options int[optional]
 * @param charset string[optional]
 * @return array an array of message numbers or UIDs.
 */
function imap_search ($imap_stream, $criteria, $options = null, $charset = null) {}

/**
 * Decodes a modified UTF-7 encoded string
 * @link http://php.net/manual/en/function.imap-utf7-decode.php
 * @param text string
 * @return string a string that is encoded in ISO-8859-1 and consists of the same
 */
function imap_utf7_decode ($text) {}

/**
 * Converts ISO-8859-1 string to modified UTF-7 text
 * @link http://php.net/manual/en/function.imap-utf7-encode.php
 * @param data string
 * @return string data encoded with the modified UTF-7
 */
function imap_utf7_encode ($data) {}

/**
 * Decode MIME header elements
 * @link http://php.net/manual/en/function.imap-mime-header-decode.php
 * @param text string
 * @return array
 */
function imap_mime_header_decode ($text) {}

/**
 * Returns a tree of threaded message
 * @link http://php.net/manual/en/function.imap-thread.php
 * @param imap_stream resource
 * @param options int[optional]
 * @return array
 */
function imap_thread ($imap_stream, $options = null) {}

/**
 * Set or fetch imap timeout
 * @link http://php.net/manual/en/function.imap-timeout.php
 * @param timeout_type int
 * @param timeout int[optional]
 * @return mixed
 */
function imap_timeout ($timeout_type, $timeout = null) {}

/**
 * Retrieve the quota level settings, and usage statics per mailbox
 * @link http://php.net/manual/en/function.imap-get-quota.php
 * @param imap_stream resource
 * @param quota_root string
 * @return array an array with integer values limit and usage for the given
 */
function imap_get_quota ($imap_stream, $quota_root) {}

/**
 * Retrieve the quota settings per user
 * @link http://php.net/manual/en/function.imap-get-quotaroot.php
 * @param imap_stream resource
 * @param quota_root string
 * @return array an array of integer values pertaining to the specified user
 */
function imap_get_quotaroot ($imap_stream, $quota_root) {}

/**
 * Sets a quota for a given mailbox
 * @link http://php.net/manual/en/function.imap-set-quota.php
 * @param imap_stream resource
 * @param quota_root string
 * @param quota_limit int
 * @return bool
 */
function imap_set_quota ($imap_stream, $quota_root, $quota_limit) {}

/**
 * Sets the ACL for a giving mailbox
 * @link http://php.net/manual/en/function.imap-setacl.php
 * @param imap_stream resource
 * @param mailbox string
 * @param id string
 * @param rights string
 * @return bool
 */
function imap_setacl ($imap_stream, $mailbox, $id, $rights) {}

/**
 * Gets the ACL for a given mailbox
 * @link http://php.net/manual/en/function.imap-getacl.php
 * @param imap_stream resource
 * @param mailbox string
 * @return array
 */
function imap_getacl ($imap_stream, $mailbox) {}

/**
 * Send an email message
 * @link http://php.net/manual/en/function.imap-mail.php
 * @param to string
 * @param subject string
 * @param message string
 * @param additional_headers string[optional]
 * @param cc string[optional]
 * @param bcc string[optional]
 * @param rpath string[optional]
 * @return bool
 */
function imap_mail ($to, $subject, $message, $additional_headers = null, $cc = null, $bcc = null, $rpath = null) {}

/**
 * &Alias; <function>imap_headerinfo</function>
 * @link http://php.net/manual/en/function.imap-header.php
 */
function imap_header () {}

/**
 * &Alias; <function>imap_list</function>
 * @link http://php.net/manual/en/function.imap-listmailbox.php
 */
function imap_listmailbox () {}

/**
 * Read the list of mailboxes, returning detailed information on each one
 * @link http://php.net/manual/en/function.imap-getmailboxes.php
 * @param imap_stream resource
 * @param ref string
 * @param pattern string
 * @return array an array of objects containing mailbox information. Each
 */
function imap_getmailboxes ($imap_stream, $ref, $pattern) {}

/**
 * &Alias; <function>imap_listscan</function>
 * @link http://php.net/manual/en/function.imap-scanmailbox.php
 */
function imap_scanmailbox () {}

/**
 * &Alias; <function>imap_lsub</function>
 * @link http://php.net/manual/en/function.imap-listsubscribed.php
 */
function imap_listsubscribed () {}

/**
 * List all the subscribed mailboxes
 * @link http://php.net/manual/en/function.imap-getsubscribed.php
 * @param imap_stream resource
 * @param ref string
 * @param pattern string
 * @return array an array of objects containing mailbox information. Each
 */
function imap_getsubscribed ($imap_stream, $ref, $pattern) {}

function imap_fetchtext () {}

function imap_scan () {}

function imap_create () {}

function imap_rename () {}

define ('NIL', 0);
define ('IMAP_OPENTIMEOUT', 1);
define ('IMAP_READTIMEOUT', 2);
define ('IMAP_WRITETIMEOUT', 3);
define ('IMAP_CLOSETIMEOUT', 4);
define ('OP_DEBUG', 1);
define ('OP_READONLY', 2);
define ('OP_ANONYMOUS', 4);
define ('OP_SHORTCACHE', 8);
define ('OP_SILENT', 16);
define ('OP_PROTOTYPE', 32);
define ('OP_HALFOPEN', 64);
define ('OP_EXPUNGE', 128);
define ('OP_SECURE', 256);
define ('CL_EXPUNGE', 32768);
define ('FT_UID', 1);
define ('FT_PEEK', 2);
define ('FT_NOT', 4);
define ('FT_INTERNAL', 8);
define ('FT_PREFETCHTEXT', 32);
define ('ST_UID', 1);
define ('ST_SILENT', 2);
define ('ST_SET', 4);
define ('CP_UID', 1);
define ('CP_MOVE', 2);
define ('SE_UID', 1);
define ('SE_FREE', 2);
define ('SE_NOPREFETCH', 4);
define ('SO_FREE', 8);
define ('SO_NOSERVER', 16);
define ('SA_MESSAGES', 1);
define ('SA_RECENT', 2);
define ('SA_UNSEEN', 4);
define ('SA_UIDNEXT', 8);
define ('SA_UIDVALIDITY', 16);
define ('SA_ALL', 31);
define ('LATT_NOINFERIORS', 1);
define ('LATT_NOSELECT', 2);
define ('LATT_MARKED', 4);
define ('LATT_UNMARKED', 8);
define ('LATT_REFERRAL', 16);
define ('LATT_HASCHILDREN', 32);
define ('LATT_HASNOCHILDREN', 64);
define ('SORTDATE', 0);
define ('SORTARRIVAL', 1);
define ('SORTFROM', 2);
define ('SORTSUBJECT', 3);
define ('SORTTO', 4);
define ('SORTCC', 5);
define ('SORTSIZE', 6);
define ('TYPETEXT', 0);
define ('TYPEMULTIPART', 1);
define ('TYPEMESSAGE', 2);
define ('TYPEAPPLICATION', 3);
define ('TYPEAUDIO', 4);
define ('TYPEIMAGE', 5);
define ('TYPEVIDEO', 6);
define ('TYPEMODEL', 7);
define ('TYPEOTHER', 8);
define ('ENC7BIT', 0);
define ('ENC8BIT', 1);
define ('ENCBINARY', 2);
define ('ENCBASE64', 3);
define ('ENCQUOTEDPRINTABLE', 4);
define ('ENCOTHER', 5);

// End of imap v.

// Start of mcrypt v.

/**
 * Deprecated: Encrypt/decrypt data in ECB mode
 * @link http://php.net/manual/en/function.mcrypt-ecb.php
 */
function mcrypt_ecb () {}

/**
 * Encrypt/decrypt data in CBC mode
 * @link http://php.net/manual/en/function.mcrypt-cbc.php
 */
function mcrypt_cbc () {}

/**
 * Encrypt/decrypt data in CFB mode
 * @link http://php.net/manual/en/function.mcrypt-cfb.php
 */
function mcrypt_cfb () {}

/**
 * Encrypt/decrypt data in OFB mode
 * @link http://php.net/manual/en/function.mcrypt-ofb.php
 */
function mcrypt_ofb () {}

/**
 * Get the key size of the specified cipher
 * @link http://php.net/manual/en/function.mcrypt-get-key-size.php
 */
function mcrypt_get_key_size () {}

/**
 * Get the block size of the specified cipher
 * @link http://php.net/manual/en/function.mcrypt-get-block-size.php
 */
function mcrypt_get_block_size () {}

/**
 * Get the name of the specified cipher
 * @link http://php.net/manual/en/function.mcrypt-get-cipher-name.php
 */
function mcrypt_get_cipher_name () {}

/**
 * Create an initialization vector (IV) from a random source
 * @link http://php.net/manual/en/function.mcrypt-create-iv.php
 */
function mcrypt_create_iv () {}

/**
 * Get an array of all supported ciphers
 * @link http://php.net/manual/en/function.mcrypt-list-algorithms.php
 */
function mcrypt_list_algorithms () {}

/**
 * Get an array of all supported modes
 * @link http://php.net/manual/en/function.mcrypt-list-modes.php
 */
function mcrypt_list_modes () {}

/**
 * Returns the size of the IV belonging to a specific cipher/mode combination
 * @link http://php.net/manual/en/function.mcrypt-get-iv-size.php
 */
function mcrypt_get_iv_size () {}

/**
 * Encrypts plaintext with given parameters
 * @link http://php.net/manual/en/function.mcrypt-encrypt.php
 */
function mcrypt_encrypt () {}

/**
 * Decrypts crypttext with given parameters
 * @link http://php.net/manual/en/function.mcrypt-decrypt.php
 */
function mcrypt_decrypt () {}

/**
 * Opens the module of the algorithm and the mode to be used
 * @link http://php.net/manual/en/function.mcrypt-module-open.php
 */
function mcrypt_module_open () {}

/**
 * This function initializes all buffers needed for encryption
 * @link http://php.net/manual/en/function.mcrypt-generic-init.php
 */
function mcrypt_generic_init () {}

/**
 * This function encrypts data
 * @link http://php.net/manual/en/function.mcrypt-generic.php
 */
function mcrypt_generic () {}

/**
 * Decrypt data
 * @link http://php.net/manual/en/function.mdecrypt-generic.php
 */
function mdecrypt_generic () {}

/**
 * This function terminates encryption
 * @link http://php.net/manual/en/function.mcrypt-generic-end.php
 */
function mcrypt_generic_end () {}

/**
 * This function deinitializes an encryption module
 * @link http://php.net/manual/en/function.mcrypt-generic-deinit.php
 */
function mcrypt_generic_deinit () {}

/**
 * This function runs a self test on the opened module
 * @link http://php.net/manual/en/function.mcrypt-enc-self-test.php
 */
function mcrypt_enc_self_test () {}

/**
 * Checks whether the encryption of the opened mode works on blocks
 * @link http://php.net/manual/en/function.mcrypt-enc-is-block-algorithm-mode.php
 */
function mcrypt_enc_is_block_algorithm_mode () {}

/**
 * Checks whether the algorithm of the opened mode is a block algorithm
 * @link http://php.net/manual/en/function.mcrypt-enc-is-block-algorithm.php
 */
function mcrypt_enc_is_block_algorithm () {}

/**
 * Checks whether the opened mode outputs blocks
 * @link http://php.net/manual/en/function.mcrypt-enc-is-block-mode.php
 */
function mcrypt_enc_is_block_mode () {}

/**
 * Returns the blocksize of the opened algorithm
 * @link http://php.net/manual/en/function.mcrypt-enc-get-block-size.php
 */
function mcrypt_enc_get_block_size () {}

/**
 * Returns the maximum supported keysize of the opened mode
 * @link http://php.net/manual/en/function.mcrypt-enc-get-key-size.php
 */
function mcrypt_enc_get_key_size () {}

/**
 * Returns an array with the supported keysizes of the opened algorithm
 * @link http://php.net/manual/en/function.mcrypt-enc-get-supported-key-sizes.php
 */
function mcrypt_enc_get_supported_key_sizes () {}

/**
 * Returns the size of the IV of the opened algorithm
 * @link http://php.net/manual/en/function.mcrypt-enc-get-iv-size.php
 */
function mcrypt_enc_get_iv_size () {}

/**
 * Returns the name of the opened algorithm
 * @link http://php.net/manual/en/function.mcrypt-enc-get-algorithms-name.php
 */
function mcrypt_enc_get_algorithms_name () {}

/**
 * Returns the name of the opened mode
 * @link http://php.net/manual/en/function.mcrypt-enc-get-modes-name.php
 */
function mcrypt_enc_get_modes_name () {}

/**
 * This function runs a self test on the specified module
 * @link http://php.net/manual/en/function.mcrypt-module-self-test.php
 */
function mcrypt_module_self_test () {}

/**
 * Returns if the specified module is a block algorithm or not
 * @link http://php.net/manual/en/function.mcrypt-module-is-block-algorithm-mode.php
 */
function mcrypt_module_is_block_algorithm_mode () {}

/**
 * This function checks whether the specified algorithm is a block algorithm
 * @link http://php.net/manual/en/function.mcrypt-module-is-block-algorithm.php
 */
function mcrypt_module_is_block_algorithm () {}

/**
 * Returns if the specified mode outputs blocks or not
 * @link http://php.net/manual/en/function.mcrypt-module-is-block-mode.php
 */
function mcrypt_module_is_block_mode () {}

/**
 * Returns the blocksize of the specified algorithm
 * @link http://php.net/manual/en/function.mcrypt-module-get-algo-block-size.php
 */
function mcrypt_module_get_algo_block_size () {}

/**
 * Returns the maximum supported keysize of the opened mode
 * @link http://php.net/manual/en/function.mcrypt-module-get-algo-key-size.php
 */
function mcrypt_module_get_algo_key_size () {}

/**
 * Returns an array with the supported keysizes of the opened algorithm
 * @link http://php.net/manual/en/function.mcrypt-module-get-supported-key-sizes.php
 */
function mcrypt_module_get_supported_key_sizes () {}

/**
 * Close the mcrypt module
 * @link http://php.net/manual/en/function.mcrypt-module-close.php
 */
function mcrypt_module_close () {}

define ('MCRYPT_ENCRYPT', 0);
define ('MCRYPT_DECRYPT', 1);
define ('MCRYPT_DEV_RANDOM', 0);
define ('MCRYPT_DEV_URANDOM', 1);
define ('MCRYPT_RAND', 2);
define ('MCRYPT_3DES', 'tripledes');
define ('MCRYPT_ARCFOUR_IV', 'arcfour-iv');
define ('MCRYPT_ARCFOUR', 'arcfour');
define ('MCRYPT_BLOWFISH', 'blowfish');
define ('MCRYPT_BLOWFISH_COMPAT', 'blowfish-compat');
define ('MCRYPT_CAST_128', 'cast-128');
define ('MCRYPT_CAST_256', 'cast-256');
define ('MCRYPT_CRYPT', 'crypt');
define ('MCRYPT_DES', 'des');
define ('MCRYPT_ENIGNA', 'crypt');
define ('MCRYPT_GOST', 'gost');
define ('MCRYPT_LOKI97', 'loki97');
define ('MCRYPT_PANAMA', 'panama');
define ('MCRYPT_RC2', 'rc2');
define ('MCRYPT_RIJNDAEL_128', 'rijndael-128');
define ('MCRYPT_RIJNDAEL_192', 'rijndael-192');
define ('MCRYPT_RIJNDAEL_256', 'rijndael-256');
define ('MCRYPT_SAFER64', 'safer-sk64');
define ('MCRYPT_SAFER128', 'safer-sk128');
define ('MCRYPT_SAFERPLUS', 'saferplus');
define ('MCRYPT_SERPENT', 'serpent');
define ('MCRYPT_THREEWAY', 'threeway');
define ('MCRYPT_TRIPLEDES', 'tripledes');
define ('MCRYPT_TWOFISH', 'twofish');
define ('MCRYPT_WAKE', 'wake');
define ('MCRYPT_XTEA', 'xtea');
define ('MCRYPT_IDEA', 'idea');
define ('MCRYPT_MARS', 'mars');
define ('MCRYPT_RC6', 'rc6');
define ('MCRYPT_SKIPJACK', 'skipjack');
define ('MCRYPT_MODE_CBC', 'cbc');
define ('MCRYPT_MODE_CFB', 'cfb');
define ('MCRYPT_MODE_ECB', 'ecb');
define ('MCRYPT_MODE_NOFB', 'nofb');
define ('MCRYPT_MODE_OFB', 'ofb');
define ('MCRYPT_MODE_STREAM', 'stream');

// End of mcrypt v.

// Start of mysqli v.0.1

final class mysqli_sql_exception extends RuntimeException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $sqlstate;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

final class mysqli_driver  {

	public function embedded_server_start () {}

	public function embedded_server_end () {}

}

/**
 * Represents a connection between PHP and a MySQL database.
 * @link http://php.net/manual/en/ref.mysqli.php
 */
class mysqli  {

	public function autocommit () {}

	public function change_user () {}

	public function character_set_name () {}

	public function client_encoding () {}

	public function close () {}

	public function commit () {}

	public function connect () {}

	public function debug () {}

	public function disable_reads_from_master () {}

	public function disable_rpl_parse () {}

	public function dump_debug_info () {}

	public function enable_reads_from_master () {}

	public function enable_rpl_parse () {}

	public function get_charset () {}

	public function get_client_info () {}

	public function get_server_info () {}

	public function get_warnings () {}

	public function init () {}

	public function kill () {}

	public function set_local_infile_default () {}

	public function set_local_infile_handler () {}

	public function master_query () {}

	public function multi_query () {}

	public function mysqli () {}

	public function more_results () {}

	public function next_result () {}

	public function options () {}

	public function ping () {}

	public function prepare () {}

	public function query () {}

	public function real_connect () {}

	public function real_escape_string () {}

	public function escape_string () {}

	public function real_query () {}

	public function rollback () {}

	public function rpl_parse_enabled () {}

	public function rpl_probe () {}

	public function rpl_query_type () {}

	public function select_db () {}

	public function set_charset () {}

	public function set_opt () {}

	public function slave_query () {}

	public function ssl_set () {}

	public function stat () {}

	public function stmt_init () {}

	public function store_result () {}

	public function thread_safe () {}

	public function use_result () {}

}

final protected class mysqli_warning  {

	protected function __construct () {}

	public function next () {}

}

/**
 * Represents the result set obtained from a query against the database.
 * @link http://php.net/manual/en/ref.mysqli.php
 */
class mysqli_result  {

	public function mysqli_result () {}

	public function close () {}

	public function free () {}

	public function data_seek () {}

	public function fetch_field () {}

	public function fetch_fields () {}

	public function fetch_field_direct () {}

	public function fetch_array () {}

	public function fetch_assoc () {}

	public function fetch_object () {}

	public function fetch_row () {}

	public function field_count () {}

	public function field_seek () {}

	public function free_result () {}

}

/**
 * Represents a prepared statement.
 * @link http://php.net/manual/en/ref.mysqli.php
 */
class mysqli_stmt  {

	public function mysqli_stmt () {}

	public function attr_get () {}

	public function attr_set () {}

	/**
	 * @param var1
	 */
	public function bind_param ($var1) {}

	public function bind_result () {}

	public function close () {}

	public function data_seek () {}

	public function execute () {}

	public function fetch () {}

	public function get_warnings () {}

	public function result_metadata () {}

	public function num_rows () {}

	public function send_long_data () {}

	public function stmt () {}

	public function free_result () {}

	public function reset () {}

	public function prepare () {}

	public function store_result () {}

}

/**
 * Gets the number of affected rows in a previous MySQL operation
 * @link http://php.net/manual/en/function.mysqli-affected-rows.php
 * @param link mysqli
 * @return int
 */
function mysqli_affected_rows (mysqli $link) {}

/**
 * Turns on or off auto-commiting database modifications
 * @link http://php.net/manual/en/function.mysqli-autocommit.php
 * @param mode bool
 * @return bool
 */
function mysqli_autocommit ($mode) {}

/**
 * Changes the user of the specified database connection
 * @link http://php.net/manual/en/function.mysqli-change-user.php
 * @param user string
 * @param password string
 * @param database string
 * @return bool
 */
function mysqli_change_user ($user, $password, $database) {}

/**
 * Returns the default character set for the database connection
 * @link http://php.net/manual/en/function.mysqli-character-set-name.php
 * @return string
 */
function mysqli_character_set_name () {}

/**
 * Closes a previously opened database connection
 * @link http://php.net/manual/en/function.mysqli-close.php
 * @return bool
 */
function mysqli_close () {}

/**
 * Commits the current transaction
 * @link http://php.net/manual/en/function.mysqli-commit.php
 * @return bool
 */
function mysqli_commit () {}

/**
 * Open a new connection to the MySQL server
 * @link http://php.net/manual/en/function.mysqli-connect.php
 * @param host string[optional]
 * @param username string[optional]
 * @param passwd string[optional]
 * @param dbname string[optional]
 * @param port int[optional]
 * @param socket string[optional]
 * @return mysqli a object which represents the connection to a MySQL Server or
 */
function mysqli_connect ($host = null, $username = null, $passwd = null, $dbname = null, $port = null, $socket = null) {}

/**
 * Returns the error code from last connect call
 * @link http://php.net/manual/en/function.mysqli-connect-errno.php
 * @return int
 */
function mysqli_connect_errno () {}

/**
 * Returns a string description of the last connect error
 * @link http://php.net/manual/en/function.mysqli-connect-error.php
 * @return string
 */
function mysqli_connect_error () {}

/**
 * Adjusts the result pointer to an arbitary row in the result
 * @link http://php.net/manual/en/function.mysqli-data-seek.php
 * @param offset int
 * @return bool
 */
function mysqli_data_seek ($offset) {}

/**
 * Performs debugging operations
 * @link http://php.net/manual/en/function.mysqli-debug.php
 * @param message string
 * @return bool true.
 */
function mysqli_debug ($message) {}

/**
 * Disable reads from master
 * @link http://php.net/manual/en/function.mysqli-disable-reads-from-master.php
 * @return void
 */
function mysqli_disable_reads_from_master () {}

/**
 * Disable RPL parse
 * @link http://php.net/manual/en/function.mysqli-disable-rpl-parse.php
 * @param link mysqli
 * @return bool
 */
function mysqli_disable_rpl_parse (mysqli $link) {}

/**
 * Dump debugging information into the log
 * @link http://php.net/manual/en/function.mysqli-dump-debug-info.php
 * @return bool
 */
function mysqli_dump_debug_info () {}

/**
 * Enable reads from master
 * @link http://php.net/manual/en/function.mysqli-enable-reads-from-master.php
 * @param link mysqli
 * @return bool
 */
function mysqli_enable_reads_from_master (mysqli $link) {}

/**
 * Enable RPL parse
 * @link http://php.net/manual/en/function.mysqli-enable-rpl-parse.php
 * @param link mysqli
 * @return bool
 */
function mysqli_enable_rpl_parse (mysqli $link) {}

/**
 * @link http://php.net/manual/en/function.mysqli-embedded-server-end.php
 * @return void
 */
function mysqli_embedded_server_end () {}

/**
 * @link http://php.net/manual/en/function.mysqli-embedded-server-start.php
 * @param start bool
 * @param arguments array
 * @param groups array
 * @return bool
 */
function mysqli_embedded_server_start ($start, array $arguments, array $groups) {}

/**
 * Returns the error code for the most recent function call
 * @link http://php.net/manual/en/function.mysqli-errno.php
 * @param link mysqli
 * @return int
 */
function mysqli_errno (mysqli $link) {}

/**
 * Returns a string description of the last error
 * @link http://php.net/manual/en/function.mysqli-error.php
 * @param link mysqli
 * @return string
 */
function mysqli_error (mysqli $link) {}

/**
 * Executes a prepared Query
 * @link http://php.net/manual/en/function.mysqli-stmt-execute.php
 * @return bool
 */
function mysqli_stmt_execute () {}

/**
 * Alias for <function>mysqli_stmt_execute</function>
 * @link http://php.net/manual/en/function.mysqli-execute.php
 */
function mysqli_execute () {}

/**
 * Returns the next field in the result set
 * @link http://php.net/manual/en/function.mysqli-fetch-field.php
 * @return object an object which contains field definition information or false
 */
function mysqli_fetch_field () {}

/**
 * Returns an array of objects representing the fields in a result set
 * @link http://php.net/manual/en/function.mysqli-fetch-fields.php
 * @return array an array of objects which contains field definition information or
 */
function mysqli_fetch_fields () {}

/**
 * Fetch meta-data for a single field
 * @link http://php.net/manual/en/function.mysqli-fetch-field-direct.php
 * @param fieldnr int
 * @return object an object which contains field definition information or false
 */
function mysqli_fetch_field_direct ($fieldnr) {}

/**
 * Returns the lengths of the columns of the current row in the result set
 * @link http://php.net/manual/en/function.mysqli-fetch-lengths.php
 * @param result mysqli_result
 * @return array
 */
function mysqli_fetch_lengths (mysqli_result $result) {}

/**
 * Fetch a result row as an associative, a numeric array, or both
 * @link http://php.net/manual/en/function.mysqli-fetch-array.php
 * @param resulttype int[optional]
 * @return mixed an array of strings that corresponds to the fetched row or &null; if there
 */
function mysqli_fetch_array ($resulttype = null) {}

/**
 * Fetch a result row as an associative array
 * @link http://php.net/manual/en/function.mysqli-fetch-assoc.php
 * @return array an associative array of strings representing the fetched row in the result
 */
function mysqli_fetch_assoc () {}

/**
 * Returns the current row of a result set as an object
 * @link http://php.net/manual/en/function.mysqli-fetch-object.php
 * @param class_name string[optional]
 * @param params array[optional]
 * @return object an object with string properties that corresponds to the fetched
 */
function mysqli_fetch_object ($class_name = null, array $params = null) {}

/**
 * Get a result row as an enumerated array
 * @link http://php.net/manual/en/function.mysqli-fetch-row.php
 * @return mixed
 */
function mysqli_fetch_row () {}

/**
 * Returns the number of columns for the most recent query
 * @link http://php.net/manual/en/function.mysqli-field-count.php
 * @return int
 */
function mysqli_field_count () {}

/**
 * Set result pointer to a specified field offset
 * @link http://php.net/manual/en/function.mysqli-field-seek.php
 * @param fieldnr int
 * @return bool the previous value of field cursor.
 */
function mysqli_field_seek ($fieldnr) {}

/**
 * Get current field offset of a result pointer
 * @link http://php.net/manual/en/function.mysqli-field-tell.php
 * @param result mysqli_result
 * @return int current offset of field cursor.
 */
function mysqli_field_tell (mysqli_result $result) {}

/**
 * Frees the memory associated with a result
 * @link http://php.net/manual/en/function.mysqli-free-result.php
 * @return void
 */
function mysqli_free_result () {}

/**
 * Returns a character set object
 * @link http://php.net/manual/en/function.mysqli-get-charset.php
 * @param link mysqli
 * @return object
 */
function mysqli_get_charset (mysqli $link) {}

/**
 * Returns the MySQL client version as a string
 * @link http://php.net/manual/en/function.mysqli-get-client-info.php
 * @return string
 */
function mysqli_get_client_info () {}

/**
 * Get MySQL client info
 * @link http://php.net/manual/en/function.mysqli-get-client-version.php
 * @return int
 */
function mysqli_get_client_version () {}

/**
 * Returns a string representing the type of connection used
 * @link http://php.net/manual/en/function.mysqli-get-host-info.php
 * @param link mysqli
 * @return string
 */
function mysqli_get_host_info (mysqli $link) {}

/**
 * Returns the version of the MySQL protocol used
 * @link http://php.net/manual/en/function.mysqli-get-proto-info.php
 * @param link mysqli
 * @return int an integer representing the protocol version.
 */
function mysqli_get_proto_info (mysqli $link) {}

/**
 * Returns the version of the MySQL server
 * @link http://php.net/manual/en/function.mysqli-get-server-info.php
 * @param link mysqli
 * @return string
 */
function mysqli_get_server_info (mysqli $link) {}

/**
 * Returns the version of the MySQL server as an integer
 * @link http://php.net/manual/en/function.mysqli-get-server-version.php
 * @param link mysqli
 * @return int
 */
function mysqli_get_server_version (mysqli $link) {}

/**
 * @link http://php.net/manual/en/function.mysqli-get-warnings.php
 * @param link mysqli
 * @return object
 */
function mysqli_get_warnings (mysqli $link) {}

/**
 * Initializes MySQLi and returns a resource for use with mysqli_real_connect()
 * @link http://php.net/manual/en/function.mysqli-init.php
 * @return mysqli an object.
 */
function mysqli_init () {}

/**
 * Retrieves information about the most recently executed query
 * @link http://php.net/manual/en/function.mysqli-info.php
 * @param link mysqli
 * @return string
 */
function mysqli_info (mysqli $link) {}

/**
 * Returns the auto generated id used in the last query
 * @link http://php.net/manual/en/function.mysqli-insert-id.php
 * @param link mysqli
 * @return int
 */
function mysqli_insert_id (mysqli $link) {}

/**
 * Asks the server to kill a MySQL thread
 * @link http://php.net/manual/en/function.mysqli-kill.php
 * @param processid int
 * @return bool
 */
function mysqli_kill ($processid) {}

/**
 * Unsets user defined handler for load local infile command
 * @link http://php.net/manual/en/function.mysqli-set-local-infile-default.php
 * @param link mysqli
 * @return void
 */
function mysqli_set_local_infile_default (mysqli $link) {}

/**
 * Set callback functions for LOAD DATA LOCAL INFILE command
 * @link http://php.net/manual/en/function.mysqli-set-local-infile-handler.php
 * @param link mysqli
 * @param read_func callback
 * @return bool
 */
function mysqli_set_local_infile_handler (mysqli $link, $read_func) {}

/**
 * Enforce execution of a query on the master in a master/slave setup
 * @link http://php.net/manual/en/function.mysqli-master-query.php
 * @param link mysqli
 * @param query string
 * @return bool
 */
function mysqli_master_query (mysqli $link, $query) {}

/**
 * Check if there are any more query results from a multi query
 * @link http://php.net/manual/en/function.mysqli-more-results.php
 * @param link mysqli
 * @return bool
 */
function mysqli_more_results (mysqli $link) {}

/**
 * Performs a query on the database
 * @link http://php.net/manual/en/function.mysqli-multi-query.php
 * @param query string
 * @return bool false if the first statement failed.
 */
function mysqli_multi_query ($query) {}

/**
 * Prepare next result from multi_query
 * @link http://php.net/manual/en/function.mysqli-next-result.php
 * @param link mysqli
 * @return bool
 */
function mysqli_next_result (mysqli $link) {}

/**
 * Get the number of fields in a result
 * @link http://php.net/manual/en/function.mysqli-num-fields.php
 * @param result mysqli_result
 * @return int
 */
function mysqli_num_fields (mysqli_result $result) {}

/**
 * Gets the number of rows in a result
 * @link http://php.net/manual/en/function.mysqli-num-rows.php
 * @param result mysqli_result
 * @return int number of rows in the result set.
 */
function mysqli_num_rows (mysqli_result $result) {}

/**
 * Set options
 * @link http://php.net/manual/en/function.mysqli-options.php
 * @param option int
 * @param value mixed
 * @return bool
 */
function mysqli_options ($option, $value) {}

/**
 * Pings a server connection, or tries to reconnect if the connection has gone down
 * @link http://php.net/manual/en/function.mysqli-ping.php
 * @return bool
 */
function mysqli_ping () {}

/**
 * Prepare a SQL statement for execution
 * @link http://php.net/manual/en/function.mysqli-prepare.php
 * @param query string
 * @return mysqli_stmt
 */
function mysqli_prepare ($query) {}

/**
 * Enables or disables internal report functions
 * @link http://php.net/manual/en/function.mysqli-report.php
 * @param flags int
 * @return bool
 */
function mysqli_report ($flags) {}

/**
 * Performs a query on the database
 * @link http://php.net/manual/en/function.mysqli-query.php
 * @param query string
 * @param resultmode int[optional]
 * @return mixed
 */
function mysqli_query ($query, $resultmode = null) {}

/**
 * Opens a connection to a mysql server
 * @link http://php.net/manual/en/function.mysqli-real-connect.php
 * @param host string[optional]
 * @param username string[optional]
 * @param passwd string[optional]
 * @param dbname string[optional]
 * @param port int[optional]
 * @param socket string[optional]
 * @param flags int[optional]
 * @return bool
 */
function mysqli_real_connect ($host = null, $username = null, $passwd = null, $dbname = null, $port = null, $socket = null, $flags = null) {}

/**
 * Escapes special characters in a string for use in a SQL statement, taking into account the current charset of the connection
 * @link http://php.net/manual/en/function.mysqli-real-escape-string.php
 * @param escapestr string
 * @return string an escaped string.
 */
function mysqli_real_escape_string ($escapestr) {}

/**
 * Execute an SQL query
 * @link http://php.net/manual/en/function.mysqli-real-query.php
 * @param query string
 * @return bool
 */
function mysqli_real_query ($query) {}

/**
 * Rolls back current transaction
 * @link http://php.net/manual/en/function.mysqli-rollback.php
 * @return bool
 */
function mysqli_rollback () {}

/**
 * Check if RPL parse is enabled
 * @link http://php.net/manual/en/function.mysqli-rpl-parse-enabled.php
 * @param link mysqli
 * @return int
 */
function mysqli_rpl_parse_enabled (mysqli $link) {}

/**
 * RPL probe
 * @link http://php.net/manual/en/function.mysqli-rpl-probe.php
 * @param link mysqli
 * @return bool
 */
function mysqli_rpl_probe (mysqli $link) {}

/**
 * Returns RPL query type
 * @link http://php.net/manual/en/function.mysqli-rpl-query-type.php
 * @param query string
 * @return int
 */
function mysqli_rpl_query_type ($query) {}

/**
 * Selects the default database for database queries
 * @link http://php.net/manual/en/function.mysqli-select-db.php
 * @param dbname string
 * @return bool
 */
function mysqli_select_db ($dbname) {}

/**
 * Sets the default client character set
 * @link http://php.net/manual/en/function.mysqli-set-charset.php
 * @param charset string
 * @return bool
 */
function mysqli_set_charset ($charset) {}

/**
 * @link http://php.net/manual/en/function.mysqli-stmt-attr-get.php
 * @param stmt mysqli_stmt
 * @param attr int
 * @return int
 */
function mysqli_stmt_attr_get (mysqli_stmt $stmt, $attr) {}

/**
 * @link http://php.net/manual/en/function.mysqli-stmt-attr-set.php
 * @param stmt mysqli_stmt
 * @param attr int
 * @param mode int
 * @return bool
 */
function mysqli_stmt_attr_set (mysqli_stmt $stmt, $attr, $mode) {}

/**
 * Returns the number of field in the given statement
 * @link http://php.net/manual/en/function.mysqli-stmt-field-count.php
 * @param stmt mysqli_stmt
 * @return int
 */
function mysqli_stmt_field_count (mysqli_stmt $stmt) {}

/**
 * Initializes a statement and returns an object for use with mysqli_stmt_prepare
 * @link http://php.net/manual/en/function.mysqli-stmt-init.php
 * @return mysqli_stmt an object.
 */
function mysqli_stmt_init () {}

/**
 * Prepare a SQL statement for execution
 * @link http://php.net/manual/en/function.mysqli-stmt-prepare.php
 * @param query string
 * @return mixed
 */
function mysqli_stmt_prepare ($query) {}

/**
 * Returns result set metadata from a prepared statement
 * @link http://php.net/manual/en/function.mysqli-stmt-result-metadata.php
 * @return mysqli_result a result object or false if an error occured.
 */
function mysqli_stmt_result_metadata () {}

/**
 * Send data in blocks
 * @link http://php.net/manual/en/function.mysqli-stmt-send-long-data.php
 * @param param_nr int
 * @param data string
 * @return bool
 */
function mysqli_stmt_send_long_data ($param_nr, $data) {}

/**
 * Binds variables to a prepared statement as parameters
 * @link http://php.net/manual/en/function.mysqli-stmt-bind-param.php
 * @param types string
 * @param var1 mixed
 * @param ... mixed[optional]
 * @return bool
 */
function mysqli_stmt_bind_param ($types, &$var1) {}

/**
 * Binds variables to a prepared statement for result storage
 * @link http://php.net/manual/en/function.mysqli-stmt-bind-result.php
 * @param var1 mixed
 * @param ... mixed[optional]
 * @return bool
 */
function mysqli_stmt_bind_result (&$var1) {}

/**
 * Fetch results from a prepared statement into the bound variables
 * @link http://php.net/manual/en/function.mysqli-stmt-fetch.php
 * @return bool
 */
function mysqli_stmt_fetch () {}

/**
 * Frees stored result memory for the given statement handle
 * @link http://php.net/manual/en/function.mysqli-stmt-free-result.php
 * @return void
 */
function mysqli_stmt_free_result () {}

/**
 * @link http://php.net/manual/en/function.mysqli-stmt-get-warnings.php
 * @param stmt mysqli_stmt
 * @return object
 */
function mysqli_stmt_get_warnings (mysqli_stmt $stmt) {}

/**
 * Get the ID generated from the previous INSERT operation
 * @link http://php.net/manual/en/function.mysqli-stmt-insert-id.php
 * @param stmt mysqli_stmt
 * @return mixed
 */
function mysqli_stmt_insert_id (mysqli_stmt $stmt) {}

/**
 * Resets a prepared statement
 * @link http://php.net/manual/en/function.mysqli-stmt-reset.php
 * @return bool
 */
function mysqli_stmt_reset () {}

/**
 * Returns the number of parameter for the given statement
 * @link http://php.net/manual/en/function.mysqli-stmt-param-count.php
 * @param stmt mysqli_stmt
 * @return int an integer representing the number of parameters.
 */
function mysqli_stmt_param_count (mysqli_stmt $stmt) {}

/**
 * Send the query and return
 * @link http://php.net/manual/en/function.mysqli-send-query.php
 * @param query string
 * @return bool
 */
function mysqli_send_query ($query) {}

/**
 * Force execution of a query on a slave in a master/slave setup
 * @link http://php.net/manual/en/function.mysqli-slave-query.php
 * @param link mysqli
 * @param query string
 * @return bool
 */
function mysqli_slave_query (mysqli $link, $query) {}

/**
 * Returns the SQLSTATE error from previous MySQL operation
 * @link http://php.net/manual/en/function.mysqli-sqlstate.php
 * @param link mysqli
 * @return string a string containing the SQLSTATE error code for the last error.
 */
function mysqli_sqlstate (mysqli $link) {}

/**
 * Used for establishing secure connections using SSL
 * @link http://php.net/manual/en/function.mysqli-ssl-set.php
 * @param key string
 * @param cert string
 * @param ca string
 * @param capath string
 * @param cipher string
 * @return bool
 */
function mysqli_ssl_set ($key, $cert, $ca, $capath, $cipher) {}

/**
 * Gets the current system status
 * @link http://php.net/manual/en/function.mysqli-stat.php
 * @return string
 */
function mysqli_stat () {}

/**
 * Returns the total number of rows changed, deleted, or
  inserted by the last executed statement
 * @link http://php.net/manual/en/function.mysqli-stmt-affected-rows.php
 * @param stmt mysqli_stmt
 * @return int
 */
function mysqli_stmt_affected_rows (mysqli_stmt $stmt) {}

/**
 * Closes a prepared statement
 * @link http://php.net/manual/en/function.mysqli-stmt-close.php
 * @return bool
 */
function mysqli_stmt_close () {}

/**
 * Seeks to an arbitray row in statement result set
 * @link http://php.net/manual/en/function.mysqli-stmt-data-seek.php
 * @param offset int
 * @return void
 */
function mysqli_stmt_data_seek ($offset) {}

/**
 * Returns the error code for the most recent statement call
 * @link http://php.net/manual/en/function.mysqli-stmt-errno.php
 * @param stmt mysqli_stmt
 * @return int
 */
function mysqli_stmt_errno (mysqli_stmt $stmt) {}

/**
 * Returns a string description for last statement error
 * @link http://php.net/manual/en/function.mysqli-stmt-error.php
 * @param stmt mysqli_stmt
 * @return string
 */
function mysqli_stmt_error (mysqli_stmt $stmt) {}

/**
 * Return the number of rows in statements result set
 * @link http://php.net/manual/en/function.mysqli-stmt-num-rows.php
 * @param stmt mysqli_stmt
 * @return int
 */
function mysqli_stmt_num_rows (mysqli_stmt $stmt) {}

/**
 * Returns SQLSTATE error from previous statement operation
 * @link http://php.net/manual/en/function.mysqli-stmt-sqlstate.php
 * @param stmt mysqli_stmt
 * @return string a string containing the SQLSTATE error code for the last error.
 */
function mysqli_stmt_sqlstate (mysqli_stmt $stmt) {}

/**
 * Transfers a result set from the last query
 * @link http://php.net/manual/en/function.mysqli-store-result.php
 * @return mysqli_result a buffered result object or false if an error occurred.
 */
function mysqli_store_result () {}

/**
 * Transfers a result set from a prepared statement
 * @link http://php.net/manual/en/function.mysqli-stmt-store-result.php
 * @return bool
 */
function mysqli_stmt_store_result () {}

/**
 * Returns the thread ID for the current connection
 * @link http://php.net/manual/en/function.mysqli-thread-id.php
 * @param link mysqli
 * @return int the Thread ID for the current connection.
 */
function mysqli_thread_id (mysqli $link) {}

/**
 * Returns whether thread safety is given or not
 * @link http://php.net/manual/en/function.mysqli-thread-safe.php
 * @return bool
 */
function mysqli_thread_safe () {}

/**
 * Initiate a result set retrieval
 * @link http://php.net/manual/en/function.mysqli-use-result.php
 * @return mysqli_result an unbuffered result object or false if an error occurred.
 */
function mysqli_use_result () {}

/**
 * Returns the number of warnings from the last query for the given link
 * @link http://php.net/manual/en/function.mysqli-warning-count.php
 * @param link mysqli
 * @return int
 */
function mysqli_warning_count (mysqli $link) {}

/**
 * Alias for <function>mysqli_stmt_bind_param</function>
 * @link http://php.net/manual/en/function.mysqli-bind-param.php
 * @param var1
 * @param var2
 */
function mysqli_bind_param ($var1, $var2) {}

/**
 * Alias for <function>mysqli_stmt_bind_result</function>
 * @link http://php.net/manual/en/function.mysqli-bind-result.php
 * @param var1
 */
function mysqli_bind_result ($var1) {}

/**
 * Alias of <function>mysqli_character_set_name</function>
 * @link http://php.net/manual/en/function.mysqli-client-encoding.php
 */
function mysqli_client_encoding () {}

/**
 * Alias of <function>mysqli_real_escape_string</function>
 * @link http://php.net/manual/en/function.mysqli-escape-string.php
 */
function mysqli_escape_string () {}

/**
 * Alias for <function>mysqli_stmt_fetch</function>
 * @link http://php.net/manual/en/function.mysqli-fetch.php
 */
function mysqli_fetch () {}

/**
 * Alias for <function>mysqli_stmt_param_count</function>
 * @link http://php.net/manual/en/function.mysqli-param-count.php
 */
function mysqli_param_count () {}

/**
 * Alias for <function>mysqli_stmt_result_metadata</function>
 * @link http://php.net/manual/en/function.mysqli-get-metadata.php
 */
function mysqli_get_metadata () {}

/**
 * Alias for <function>mysqli_stmt_send_long_data</function>
 * @link http://php.net/manual/en/function.mysqli-send-long-data.php
 */
function mysqli_send_long_data () {}

/**
 * Alias of <function>mysqli_options</function>
 * @link http://php.net/manual/en/function.mysqli-set-opt.php
 */
function mysqli_set_opt () {}

define ('MYSQLI_READ_DEFAULT_GROUP', 5);
define ('MYSQLI_READ_DEFAULT_FILE', 4);
define ('MYSQLI_OPT_CONNECT_TIMEOUT', 0);
define ('MYSQLI_OPT_LOCAL_INFILE', 8);
define ('MYSQLI_INIT_COMMAND', 3);
define ('MYSQLI_CLIENT_SSL', 2048);
define ('MYSQLI_CLIENT_COMPRESS', 32);
define ('MYSQLI_CLIENT_INTERACTIVE', 1024);
define ('MYSQLI_CLIENT_IGNORE_SPACE', 256);
define ('MYSQLI_CLIENT_NO_SCHEMA', 16);
define ('MYSQLI_CLIENT_FOUND_ROWS', 2);
define ('MYSQLI_STORE_RESULT', 0);
define ('MYSQLI_USE_RESULT', 1);
define ('MYSQLI_ASSOC', 1);
define ('MYSQLI_NUM', 2);
define ('MYSQLI_BOTH', 3);
define ('MYSQLI_STMT_ATTR_UPDATE_MAX_LENGTH', 0);
define ('MYSQLI_STMT_ATTR_CURSOR_TYPE', 1);
define ('MYSQLI_CURSOR_TYPE_NO_CURSOR', 0);
define ('MYSQLI_CURSOR_TYPE_READ_ONLY', 1);
define ('MYSQLI_CURSOR_TYPE_FOR_UPDATE', 2);
define ('MYSQLI_CURSOR_TYPE_SCROLLABLE', 4);
define ('MYSQLI_STMT_ATTR_PREFETCH_ROWS', 2);
define ('MYSQLI_NOT_NULL_FLAG', 1);
define ('MYSQLI_PRI_KEY_FLAG', 2);
define ('MYSQLI_UNIQUE_KEY_FLAG', 4);
define ('MYSQLI_MULTIPLE_KEY_FLAG', 8);
define ('MYSQLI_BLOB_FLAG', 16);
define ('MYSQLI_UNSIGNED_FLAG', 32);
define ('MYSQLI_ZEROFILL_FLAG', 64);
define ('MYSQLI_AUTO_INCREMENT_FLAG', 512);
define ('MYSQLI_TIMESTAMP_FLAG', 1024);
define ('MYSQLI_SET_FLAG', 2048);
define ('MYSQLI_NUM_FLAG', 32768);
define ('MYSQLI_PART_KEY_FLAG', 16384);
define ('MYSQLI_GROUP_FLAG', 32768);
define ('MYSQLI_TYPE_DECIMAL', 0);
define ('MYSQLI_TYPE_TINY', 1);
define ('MYSQLI_TYPE_SHORT', 2);
define ('MYSQLI_TYPE_LONG', 3);
define ('MYSQLI_TYPE_FLOAT', 4);
define ('MYSQLI_TYPE_DOUBLE', 5);
define ('MYSQLI_TYPE_NULL', 6);
define ('MYSQLI_TYPE_TIMESTAMP', 7);
define ('MYSQLI_TYPE_LONGLONG', 8);
define ('MYSQLI_TYPE_INT24', 9);
define ('MYSQLI_TYPE_DATE', 10);
define ('MYSQLI_TYPE_TIME', 11);
define ('MYSQLI_TYPE_DATETIME', 12);
define ('MYSQLI_TYPE_YEAR', 13);
define ('MYSQLI_TYPE_NEWDATE', 14);
define ('MYSQLI_TYPE_ENUM', 247);
define ('MYSQLI_TYPE_SET', 248);
define ('MYSQLI_TYPE_TINY_BLOB', 249);
define ('MYSQLI_TYPE_MEDIUM_BLOB', 250);
define ('MYSQLI_TYPE_LONG_BLOB', 251);
define ('MYSQLI_TYPE_BLOB', 252);
define ('MYSQLI_TYPE_VAR_STRING', 253);
define ('MYSQLI_TYPE_STRING', 254);
define ('MYSQLI_TYPE_CHAR', 1);
define ('MYSQLI_TYPE_INTERVAL', 247);
define ('MYSQLI_TYPE_GEOMETRY', 255);
define ('MYSQLI_TYPE_NEWDECIMAL', 246);
define ('MYSQLI_TYPE_BIT', 16);
define ('MYSQLI_RPL_MASTER', 0);
define ('MYSQLI_RPL_SLAVE', 1);
define ('MYSQLI_RPL_ADMIN', 2);
define ('MYSQLI_NO_DATA', 100);
define ('MYSQLI_DATA_TRUNCATED', 101);
define ('MYSQLI_REPORT_INDEX', 4);
define ('MYSQLI_REPORT_ERROR', 1);
define ('MYSQLI_REPORT_STRICT', 2);
define ('MYSQLI_REPORT_ALL', 255);
define ('MYSQLI_REPORT_OFF', 0);

// End of mysqli v.0.1

// Start of pcntl v.

/**
 * Forks the currently running process
 * @link http://php.net/manual/en/function.pcntl-fork.php
 * @return int
 */
function pcntl_fork () {}

/**
 * Waits on or returns the status of a forked child
 * @link http://php.net/manual/en/function.pcntl-waitpid.php
 * @param pid int
 * @param status int
 * @param options int[optional]
 * @return int
 */
function pcntl_waitpid ($pid, &$status, $options = null) {}

/**
 * Waits on or returns the status of a forked child
 * @link http://php.net/manual/en/function.pcntl-wait.php
 * @param status int
 * @param options int[optional]
 * @return int
 */
function pcntl_wait (&$status, $options = null) {}

/**
 * Installs a signal handler
 * @link http://php.net/manual/en/function.pcntl-signal.php
 * @param signo int
 * @param handler callback
 * @param restart_syscalls bool[optional]
 * @return bool
 */
function pcntl_signal ($signo, $handler, $restart_syscalls = null) {}

/**
 * Checks if status code represents a normal exit
 * @link http://php.net/manual/en/function.pcntl-wifexited.php
 * @param status int
 * @return bool true if the child status code represents a normal exit, false
 */
function pcntl_wifexited ($status) {}

/**
 * Checks whether the child process is currently stopped
 * @link http://php.net/manual/en/function.pcntl-wifstopped.php
 * @param status int
 * @return bool true if the child process which caused the return is
 */
function pcntl_wifstopped ($status) {}

/**
 * Checks whether the status code represents a termination due to a signal
 * @link http://php.net/manual/en/function.pcntl-wifsignaled.php
 * @param status int
 * @return bool true if the child process exited because of a signal which was
 */
function pcntl_wifsignaled ($status) {}

/**
 * Returns the return code of a terminated child
 * @link http://php.net/manual/en/function.pcntl-wexitstatus.php
 * @param status int
 * @return int the return code, as an integer.
 */
function pcntl_wexitstatus ($status) {}

/**
 * Returns the signal which caused the child to terminate
 * @link http://php.net/manual/en/function.pcntl-wtermsig.php
 * @param status int
 * @return int the signal number, as an integer.
 */
function pcntl_wtermsig ($status) {}

/**
 * Returns the signal which caused the child to stop
 * @link http://php.net/manual/en/function.pcntl-wstopsig.php
 * @param status int
 * @return int the signal number.
 */
function pcntl_wstopsig ($status) {}

/**
 * Executes specified program in current process space
 * @link http://php.net/manual/en/function.pcntl-exec.php
 * @param path string
 * @param args array[optional]
 * @param envs array[optional]
 * @return void false on error and does not return on success.
 */
function pcntl_exec ($path, array $args = null, array $envs = null) {}

/**
 * Set an alarm clock for delivery of a signal
 * @link http://php.net/manual/en/function.pcntl-alarm.php
 * @param seconds int
 * @return int the time in seconds that any previously scheduled alarm had
 */
function pcntl_alarm ($seconds) {}

/**
 * Get the priority of any process
 * @link http://php.net/manual/en/function.pcntl-getpriority.php
 * @param pid int[optional]
 * @param process_identifier int[optional]
 * @return int
 */
function pcntl_getpriority ($pid = null, $process_identifier = null) {}

/**
 * Change the priority of any process
 * @link http://php.net/manual/en/function.pcntl-setpriority.php
 * @param priority int
 * @param pid int[optional]
 * @param process_identifier int[optional]
 * @return bool
 */
function pcntl_setpriority ($priority, $pid = null, $process_identifier = null) {}

define ('WNOHANG', 1);
define ('WUNTRACED', 2);
define ('SIG_IGN', 1);
define ('SIG_DFL', 0);
define ('SIG_ERR', -1);
define ('SIGHUP', 1);
define ('SIGINT', 2);
define ('SIGQUIT', 3);
define ('SIGILL', 4);
define ('SIGTRAP', 5);
define ('SIGABRT', 6);
define ('SIGIOT', 6);
define ('SIGBUS', 7);
define ('SIGFPE', 8);
define ('SIGKILL', 9);
define ('SIGUSR1', 10);
define ('SIGSEGV', 11);
define ('SIGUSR2', 12);
define ('SIGPIPE', 13);
define ('SIGALRM', 14);
define ('SIGTERM', 15);
define ('SIGSTKFLT', 16);
define ('SIGCLD', 17);
define ('SIGCHLD', 17);
define ('SIGCONT', 18);
define ('SIGSTOP', 19);
define ('SIGTSTP', 20);
define ('SIGTTIN', 21);
define ('SIGTTOU', 22);
define ('SIGURG', 23);
define ('SIGXCPU', 24);
define ('SIGXFSZ', 25);
define ('SIGVTALRM', 26);
define ('SIGPROF', 27);
define ('SIGWINCH', 28);
define ('SIGPOLL', 29);
define ('SIGIO', 29);
define ('SIGPWR', 30);
define ('SIGSYS', 31);
define ('SIGBABY', 31);
define ('PRIO_PGRP', 1);
define ('PRIO_USER', 2);
define ('PRIO_PROCESS', 0);

// End of pcntl v.

// Start of pgsql v.

/**
 * Open a PostgreSQL connection
 * @link http://php.net/manual/en/function.pg-connect.php
 * @param connection_string string
 * @param connect_type int[optional]
 * @return resource
 */
function pg_connect ($connection_string, $connect_type = null) {}

/**
 * Open a persistent PostgreSQL connection
 * @link http://php.net/manual/en/function.pg-pconnect.php
 * @param connection_string string
 * @param connect_type int[optional]
 * @return resource
 */
function pg_pconnect ($connection_string, $connect_type = null) {}

/**
 * Closes a PostgreSQL connection
 * @link http://php.net/manual/en/function.pg-close.php
 * @param connection resource[optional]
 * @return bool
 */
function pg_close ($connection = null) {}

/**
 * Get connection status
 * @link http://php.net/manual/en/function.pg-connection-status.php
 * @param connection resource
 * @return int
 */
function pg_connection_status ($connection) {}

/**
 * Get connection is busy or not
 * @link http://php.net/manual/en/function.pg-connection-busy.php
 * @param connection resource
 * @return bool true if the connection is busy, false otherwise.
 */
function pg_connection_busy ($connection) {}

/**
 * Reset connection (reconnect)
 * @link http://php.net/manual/en/function.pg-connection-reset.php
 * @param connection resource
 * @return bool
 */
function pg_connection_reset ($connection) {}

/**
 * Returns the host name associated with the connection
 * @link http://php.net/manual/en/function.pg-host.php
 * @param connection resource[optional]
 * @return string
 */
function pg_host ($connection = null) {}

/**
 * Get the database name
 * @link http://php.net/manual/en/function.pg-dbname.php
 * @param connection resource[optional]
 * @return string
 */
function pg_dbname ($connection = null) {}

/**
 * Return the port number associated with the connection
 * @link http://php.net/manual/en/function.pg-port.php
 * @param connection resource[optional]
 * @return int
 */
function pg_port ($connection = null) {}

/**
 * Return the TTY name associated with the connection
 * @link http://php.net/manual/en/function.pg-tty.php
 * @param connection resource[optional]
 * @return string
 */
function pg_tty ($connection = null) {}

/**
 * Get the options associated with the connection
 * @link http://php.net/manual/en/function.pg-options.php
 * @param connection resource[optional]
 * @return string
 */
function pg_options ($connection = null) {}

/**
 * Returns an array with client, protocol and server version (when available)
 * @link http://php.net/manual/en/function.pg-version.php
 * @param connection resource[optional]
 * @return array an array with client, protocol 
 */
function pg_version ($connection = null) {}

/**
 * Ping database connection
 * @link http://php.net/manual/en/function.pg-ping.php
 * @param connection resource[optional]
 * @return bool
 */
function pg_ping ($connection = null) {}

/**
 * Looks up a current parameter setting of the server.
 * @link http://php.net/manual/en/function.pg-parameter-status.php
 * @param connection resource
 * @param param_name string
 * @return string
 */
function pg_parameter_status ($connection, $param_name) {}

/**
 * Returns the current in-transaction status of the server.
 * @link http://php.net/manual/en/function.pg-transaction-status.php
 * @param connection resource
 * @return int
 */
function pg_transaction_status ($connection) {}

/**
 * Execute a query
 * @link http://php.net/manual/en/function.pg-query.php
 * @param query string
 * @return resource
 */
function pg_query ($query) {}

/**
 * Submits a command to the server and waits for the result, with the ability to pass parameters separately from the SQL command text.
 * @link http://php.net/manual/en/function.pg-query-params.php
 * @param connection resource
 * @param query string
 * @param params array
 * @return resource
 */
function pg_query_params ($connection, $query, array $params) {}

/**
 * Submits a request to create a prepared statement with the 
  given parameters, and waits for completion.
 * @link http://php.net/manual/en/function.pg-prepare.php
 * @param connection resource
 * @param stmtname string
 * @param query string
 * @return resource
 */
function pg_prepare ($connection, $stmtname, $query) {}

/**
 * Sends a request to execute a prepared statement with given parameters, and waits for the result.
 * @link http://php.net/manual/en/function.pg-execute.php
 * @param connection resource
 * @param stmtname string
 * @param params array
 * @return resource
 */
function pg_execute ($connection, $stmtname, array $params) {}

/**
 * Sends asynchronous query
 * @link http://php.net/manual/en/function.pg-send-query.php
 * @param connection resource
 * @param query string
 * @return bool
 */
function pg_send_query ($connection, $query) {}

/**
 * Submits a command and separate parameters to the server without waiting for the result(s).
 * @link http://php.net/manual/en/function.pg-send-query-params.php
 * @param connection resource
 * @param query string
 * @param params array
 * @return bool
 */
function pg_send_query_params ($connection, $query, array $params) {}

/**
 * Sends a request to create a prepared statement with the given parameters, without waiting for completion.
 * @link http://php.net/manual/en/function.pg-send-prepare.php
 * @param connection resource
 * @param stmtname string
 * @param query string
 * @return bool true on success, false on failure.  Use pg_get_result
 */
function pg_send_prepare ($connection, $stmtname, $query) {}

/**
 * Sends a request to execute a prepared statement with given parameters, without waiting for the result(s).
 * @link http://php.net/manual/en/function.pg-send-execute.php
 * @param connection resource
 * @param stmtname string
 * @param params array
 * @return bool true on success, false on failure.  Use pg_get_result
 */
function pg_send_execute ($connection, $stmtname, array $params) {}

/**
 * Cancel an asynchronous query
 * @link http://php.net/manual/en/function.pg-cancel-query.php
 * @param connection resource
 * @return bool
 */
function pg_cancel_query ($connection) {}

/**
 * Returns values from a result resource
 * @link http://php.net/manual/en/function.pg-fetch-result.php
 * @param result resource
 * @param row int
 * @param field mixed
 * @return string
 */
function pg_fetch_result ($result, $row, $field) {}

/**
 * Get a row as an enumerated array
 * @link http://php.net/manual/en/function.pg-fetch-row.php
 * @param result resource
 * @param row int[optional]
 * @param result_type int[optional]
 * @return array
 */
function pg_fetch_row ($result, $row = null, $result_type = null) {}

/**
 * Fetch a row as an associative array
 * @link http://php.net/manual/en/function.pg-fetch-assoc.php
 * @param result resource
 * @param row int[optional]
 * @return array
 */
function pg_fetch_assoc ($result, $row = null) {}

/**
 * Fetch a row as an array
 * @link http://php.net/manual/en/function.pg-fetch-array.php
 * @param result resource
 * @param row int[optional]
 * @param result_type int[optional]
 * @return array
 */
function pg_fetch_array ($result, $row = null, $result_type = null) {}

/**
 * Fetch a row as an object
 * @link http://php.net/manual/en/function.pg-fetch-object.php
 * @param result resource
 * @param row int[optional]
 * @param result_type int[optional]
 * @return object
 */
function pg_fetch_object ($result, $row = null, $result_type = null) {}

/**
 * Fetches all rows from a result as an array
 * @link http://php.net/manual/en/function.pg-fetch-all.php
 * @param result resource
 * @return array
 */
function pg_fetch_all ($result) {}

/**
 * Fetches all rows in a particular result column as an array
 * @link http://php.net/manual/en/function.pg-fetch-all-columns.php
 * @param result resource
 * @param column int[optional]
 * @return array
 */
function pg_fetch_all_columns ($result, $column = null) {}

/**
 * Returns number of affected records (tuples)
 * @link http://php.net/manual/en/function.pg-affected-rows.php
 * @param result resource
 * @return int
 */
function pg_affected_rows ($result) {}

/**
 * Get asynchronous query result
 * @link http://php.net/manual/en/function.pg-get-result.php
 * @param connection resource[optional]
 * @return resource
 */
function pg_get_result ($connection = null) {}

/**
 * Set internal row offset in result resource
 * @link http://php.net/manual/en/function.pg-result-seek.php
 * @param result resource
 * @param offset int
 * @return bool
 */
function pg_result_seek ($result, $offset) {}

/**
 * Get status of query result
 * @link http://php.net/manual/en/function.pg-result-status.php
 * @param result resource
 * @param type int[optional]
 * @return mixed
 */
function pg_result_status ($result, $type = null) {}

/**
 * Free result memory
 * @link http://php.net/manual/en/function.pg-free-result.php
 * @param result resource
 * @return bool
 */
function pg_free_result ($result) {}

/**
 * Returns the last row's OID
 * @link http://php.net/manual/en/function.pg-last-oid.php
 * @param result resource
 * @return string
 */
function pg_last_oid ($result) {}

/**
 * Returns the number of rows in a result
 * @link http://php.net/manual/en/function.pg-num-rows.php
 * @param result resource
 * @return int
 */
function pg_num_rows ($result) {}

/**
 * Returns the number of fields in a result
 * @link http://php.net/manual/en/function.pg-num-fields.php
 * @param result resource
 * @return int
 */
function pg_num_fields ($result) {}

/**
 * Returns the name of a field
 * @link http://php.net/manual/en/function.pg-field-name.php
 * @param result resource
 * @param field_number int
 * @return string
 */
function pg_field_name ($result, $field_number) {}

/**
 * Returns the field number of the named field
 * @link http://php.net/manual/en/function.pg-field-num.php
 * @param result resource
 * @param field_name string
 * @return int
 */
function pg_field_num ($result, $field_name) {}

/**
 * Returns the internal storage size of the named field
 * @link http://php.net/manual/en/function.pg-field-size.php
 * @param result resource
 * @param field_number int
 * @return int
 */
function pg_field_size ($result, $field_number) {}

/**
 * Returns the type name for the corresponding field number
 * @link http://php.net/manual/en/function.pg-field-type.php
 * @param result resource
 * @param field_number int
 * @return string
 */
function pg_field_type ($result, $field_number) {}

/**
 * Returns the type ID (OID) for the corresponding field number
 * @link http://php.net/manual/en/function.pg-field-type-oid.php
 * @param result resource
 * @param field_number int
 * @return int
 */
function pg_field_type_oid ($result, $field_number) {}

/**
 * Returns the printed length
 * @link http://php.net/manual/en/function.pg-field-prtlen.php
 * @param result resource
 * @param row_number int
 * @param field_name_or_number mixed
 * @return int
 */
function pg_field_prtlen ($result, $row_number, $field_name_or_number) {}

/**
 * Test if a field is SQL <literal>NULL</literal>
 * @link http://php.net/manual/en/function.pg-field-is-null.php
 * @param result resource
 * @param row int
 * @param field mixed
 * @return int 1 if the field in the given row is SQL NULL, 0
 */
function pg_field_is_null ($result, $row, $field) {}

/**
 * Returns the name or oid of the tables field
 * @link http://php.net/manual/en/function.pg-field-table.php
 * @param result resource
 * @param field_number int
 * @param oid_only bool[optional]
 * @return mixed
 */
function pg_field_table ($result, $field_number, $oid_only = null) {}

/**
 * Gets SQL NOTIFY message
 * @link http://php.net/manual/en/function.pg-get-notify.php
 * @param connection resource
 * @param result_type int[optional]
 * @return array
 */
function pg_get_notify ($connection, $result_type = null) {}

/**
 * Gets the backend's process ID
 * @link http://php.net/manual/en/function.pg-get-pid.php
 * @param connection resource
 * @return int
 */
function pg_get_pid ($connection) {}

/**
 * Get error message associated with result
 * @link http://php.net/manual/en/function.pg-result-error.php
 * @param result resource
 * @return string a string if there is an error associated with the
 */
function pg_result_error ($result) {}

/**
 * Returns an individual field of an error report.
 * @link http://php.net/manual/en/function.pg-result-error-field.php
 * @param result resource
 * @param fieldcode int
 * @return string
 */
function pg_result_error_field ($result, $fieldcode) {}

/**
 * Get the last error message string of a connection
 * @link http://php.net/manual/en/function.pg-last-error.php
 * @param connection resource[optional]
 * @return string
 */
function pg_last_error ($connection = null) {}

/**
 * Returns the last notice message from PostgreSQL server
 * @link http://php.net/manual/en/function.pg-last-notice.php
 * @param connection resource
 * @return string
 */
function pg_last_notice ($connection) {}

/**
 * Send a NULL-terminated string to PostgreSQL backend
 * @link http://php.net/manual/en/function.pg-put-line.php
 * @param data string
 * @return bool
 */
function pg_put_line ($data) {}

/**
 * Sync with PostgreSQL backend
 * @link http://php.net/manual/en/function.pg-end-copy.php
 * @param connection resource[optional]
 * @return bool
 */
function pg_end_copy ($connection = null) {}

/**
 * Copy a table to an array
 * @link http://php.net/manual/en/function.pg-copy-to.php
 * @param connection resource
 * @param table_name string
 * @param delimiter string[optional]
 * @param null_as string[optional]
 * @return array
 */
function pg_copy_to ($connection, $table_name, $delimiter = null, $null_as = null) {}

/**
 * Insert records into a table from an array
 * @link http://php.net/manual/en/function.pg-copy-from.php
 * @param connection resource
 * @param table_name string
 * @param rows array
 * @param delimiter string[optional]
 * @param null_as string[optional]
 * @return bool
 */
function pg_copy_from ($connection, $table_name, array $rows, $delimiter = null, $null_as = null) {}

/**
 * Enable tracing a PostgreSQL connection
 * @link http://php.net/manual/en/function.pg-trace.php
 * @param pathname string
 * @param mode string[optional]
 * @param connection resource[optional]
 * @return bool
 */
function pg_trace ($pathname, $mode = null, $connection = null) {}

/**
 * Disable tracing of a PostgreSQL connection
 * @link http://php.net/manual/en/function.pg-untrace.php
 * @param connection resource[optional]
 * @return bool
 */
function pg_untrace ($connection = null) {}

/**
 * Create a large object
 * @link http://php.net/manual/en/function.pg-lo-create.php
 * @param connection resource[optional]
 * @return int
 */
function pg_lo_create ($connection = null) {}

/**
 * Delete a large object
 * @link http://php.net/manual/en/function.pg-lo-unlink.php
 * @param connection resource
 * @param oid int
 * @return bool
 */
function pg_lo_unlink ($connection, $oid) {}

/**
 * Open a large object
 * @link http://php.net/manual/en/function.pg-lo-open.php
 * @param connection resource
 * @param oid int
 * @param mode string
 * @return resource
 */
function pg_lo_open ($connection, $oid, $mode) {}

/**
 * Close a large object
 * @link http://php.net/manual/en/function.pg-lo-close.php
 * @param large_object resource
 * @return bool
 */
function pg_lo_close ($large_object) {}

/**
 * Read a large object
 * @link http://php.net/manual/en/function.pg-lo-read.php
 * @param large_object resource
 * @param len int[optional]
 * @return string
 */
function pg_lo_read ($large_object, $len = null) {}

/**
 * Write to a large object
 * @link http://php.net/manual/en/function.pg-lo-write.php
 * @param large_object resource
 * @param data string
 * @param len int[optional]
 * @return int
 */
function pg_lo_write ($large_object, $data, $len = null) {}

/**
 * Reads an entire large object and send straight to browser
 * @link http://php.net/manual/en/function.pg-lo-read-all.php
 * @param large_object resource
 * @return int
 */
function pg_lo_read_all ($large_object) {}

/**
 * Import a large object from file
 * @link http://php.net/manual/en/function.pg-lo-import.php
 * @param connection resource
 * @param pathname string
 * @return int
 */
function pg_lo_import ($connection, $pathname) {}

/**
 * Export a large object to file
 * @link http://php.net/manual/en/function.pg-lo-export.php
 * @param connection resource
 * @param oid int
 * @param pathname string
 * @return bool
 */
function pg_lo_export ($connection, $oid, $pathname) {}

/**
 * Seeks position within a large object
 * @link http://php.net/manual/en/function.pg-lo-seek.php
 * @param large_object resource
 * @param offset int
 * @param whence int[optional]
 * @return bool
 */
function pg_lo_seek ($large_object, $offset, $whence = null) {}

/**
 * Returns current seek position a of large object
 * @link http://php.net/manual/en/function.pg-lo-tell.php
 * @param large_object resource
 * @return int
 */
function pg_lo_tell ($large_object) {}

/**
 * Escape a string for insertion into a text field
 * @link http://php.net/manual/en/function.pg-escape-string.php
 * @param connection resource[optional]
 * @param data string
 * @return string
 */
function pg_escape_string ($connection = null, $data) {}

/**
 * Escape a string for insertion into a bytea field
 * @link http://php.net/manual/en/function.pg-escape-bytea.php
 * @param connection resource[optional]
 * @param data string
 * @return string
 */
function pg_escape_bytea ($connection = null, $data) {}

/**
 * Unescape binary for bytea type
 * @link http://php.net/manual/en/function.pg-unescape-bytea.php
 * @param data string
 * @return string
 */
function pg_unescape_bytea ($data) {}

/**
 * Determines the verbosity of messages returned by <function>pg_last_error</function> 
   and <function>pg_result_error</function>.
 * @link http://php.net/manual/en/function.pg-set-error-verbosity.php
 * @param connection resource
 * @param verbosity int
 * @return int
 */
function pg_set_error_verbosity ($connection, $verbosity) {}

/**
 * Gets the client encoding
 * @link http://php.net/manual/en/function.pg-client-encoding.php
 * @param connection resource[optional]
 * @return string
 */
function pg_client_encoding ($connection = null) {}

/**
 * Set the client encoding
 * @link http://php.net/manual/en/function.pg-set-client-encoding.php
 * @param encoding string
 * @return int 0 on success or -1 on error.
 */
function pg_set_client_encoding ($encoding) {}

/**
 * Get meta data for table
 * @link http://php.net/manual/en/function.pg-meta-data.php
 * @param connection resource
 * @param table_name string
 * @return array
 */
function pg_meta_data ($connection, $table_name) {}

/**
 * Convert associative array values into suitable for SQL statement
 * @link http://php.net/manual/en/function.pg-convert.php
 * @param connection resource
 * @param table_name string
 * @param assoc_array array
 * @param options int[optional]
 * @return array
 */
function pg_convert ($connection, $table_name, array $assoc_array, $options = null) {}

/**
 * Insert array into table
 * @link http://php.net/manual/en/function.pg-insert.php
 * @param connection resource
 * @param table_name string
 * @param assoc_array array
 * @param options int[optional]
 * @return mixed
 */
function pg_insert ($connection, $table_name, array $assoc_array, $options = null) {}

/**
 * Update table
 * @link http://php.net/manual/en/function.pg-update.php
 * @param connection resource
 * @param table_name string
 * @param data array
 * @param condition array
 * @param options int[optional]
 * @return mixed
 */
function pg_update ($connection, $table_name, array $data, array $condition, $options = null) {}

/**
 * Deletes records
 * @link http://php.net/manual/en/function.pg-delete.php
 * @param connection resource
 * @param table_name string
 * @param assoc_array array
 * @param options int[optional]
 * @return mixed
 */
function pg_delete ($connection, $table_name, array $assoc_array, $options = null) {}

/**
 * Select records
 * @link http://php.net/manual/en/function.pg-select.php
 * @param connection resource
 * @param table_name string
 * @param assoc_array array
 * @param options int[optional]
 * @return mixed
 */
function pg_select ($connection, $table_name, array $assoc_array, $options = null) {}

function pg_exec () {}

function pg_getlastoid () {}

function pg_cmdtuples () {}

function pg_errormessage () {}

function pg_numrows () {}

function pg_numfields () {}

function pg_fieldname () {}

function pg_fieldsize () {}

function pg_fieldtype () {}

function pg_fieldnum () {}

function pg_fieldprtlen () {}

function pg_fieldisnull () {}

function pg_freeresult () {}

function pg_result () {}

function pg_loreadall () {}

function pg_locreate () {}

function pg_lounlink () {}

function pg_loopen () {}

function pg_loclose () {}

function pg_loread () {}

function pg_lowrite () {}

function pg_loimport () {}

function pg_loexport () {}

function pg_clientencoding () {}

function pg_setclientencoding () {}

define ('PGSQL_CONNECT_FORCE_NEW', 2);
define ('PGSQL_ASSOC', 1);
define ('PGSQL_NUM', 2);
define ('PGSQL_BOTH', 3);
define ('PGSQL_CONNECTION_BAD', 1);
define ('PGSQL_CONNECTION_OK', 0);
define ('PGSQL_TRANSACTION_IDLE', 0);
define ('PGSQL_TRANSACTION_ACTIVE', 1);
define ('PGSQL_TRANSACTION_INTRANS', 2);
define ('PGSQL_TRANSACTION_INERROR', 3);
define ('PGSQL_TRANSACTION_UNKNOWN', 4);
define ('PGSQL_ERRORS_TERSE', 0);
define ('PGSQL_ERRORS_DEFAULT', 1);
define ('PGSQL_ERRORS_VERBOSE', 2);
define ('PGSQL_SEEK_SET', 0);
define ('PGSQL_SEEK_CUR', 1);
define ('PGSQL_SEEK_END', 2);
define ('PGSQL_STATUS_LONG', 1);
define ('PGSQL_STATUS_STRING', 2);
define ('PGSQL_EMPTY_QUERY', 0);
define ('PGSQL_COMMAND_OK', 1);
define ('PGSQL_TUPLES_OK', 2);
define ('PGSQL_COPY_OUT', 3);
define ('PGSQL_COPY_IN', 4);
define ('PGSQL_BAD_RESPONSE', 5);
define ('PGSQL_NONFATAL_ERROR', 6);
define ('PGSQL_FATAL_ERROR', 7);
define ('PGSQL_DIAG_SEVERITY', 83);
define ('PGSQL_DIAG_SQLSTATE', 67);
define ('PGSQL_DIAG_MESSAGE_PRIMARY', 77);
define ('PGSQL_DIAG_MESSAGE_DETAIL', 68);
define ('PGSQL_DIAG_MESSAGE_HINT', 72);
define ('PGSQL_DIAG_STATEMENT_POSITION', 80);
define ('PGSQL_DIAG_INTERNAL_POSITION', 112);
define ('PGSQL_DIAG_INTERNAL_QUERY', 113);
define ('PGSQL_DIAG_CONTEXT', 87);
define ('PGSQL_DIAG_SOURCE_FILE', 70);
define ('PGSQL_DIAG_SOURCE_LINE', 76);
define ('PGSQL_DIAG_SOURCE_FUNCTION', 82);
define ('PGSQL_CONV_IGNORE_DEFAULT', 2);
define ('PGSQL_CONV_FORCE_NULL', 4);
define ('PGSQL_CONV_IGNORE_NOT_NULL', 8);
define ('PGSQL_DML_NO_CONV', 256);
define ('PGSQL_DML_EXEC', 512);
define ('PGSQL_DML_ASYNC', 1024);
define ('PGSQL_DML_STRING', 2048);

// End of pgsql v.

// Start of soap v.

/**
 * @link http://php.net/manual/en/ref.soap.php
 */
class SoapClient  {

	public function SoapClient () {}

	/**
	 * Calls a SOAP function (deprecated)
	 * @link http://php.net/manual/en/function.soap-soapclient-call.php
	 * @param function_name string
	 * @param arguments array
	 * @param options array[optional]
	 * @param input_headers array[optional]
	 * @param output_headers array[optional]
	 * @return mixed
	 */
	public function __call ($function_name, array $arguments, array $options = null, array $input_headers = null, array $output_headers = null) {}

	/**
	 * Calls a SOAP function
	 * @link http://php.net/manual/en/function.soap-soapclient-soapcall.php
	 * @param function_name string
	 * @param arguments array
	 * @param options array[optional]
	 * @param input_headers mixed[optional]
	 * @param output_headers array[optional]
	 * @return mixed
	 */
	public function __soapCall ($function_name, array $arguments, array $options = null, $input_headers = null, array &$output_headers = null) {}

	/**
	 * Returns last SOAP request
	 * @link http://php.net/manual/en/function.soap-soapclient-getlastrequest.php
	 * @return string
	 */
	public function __getLastRequest () {}

	/**
	 * Returns last SOAP response.
	 * @link http://php.net/manual/en/function.soap-soapclient-getlastresponse.php
	 * @return string
	 */
	public function __getLastResponse () {}

	/**
	 * Returns last SOAP request headers
	 * @link http://php.net/manual/en/function.soap-soapclient-getlastrequestheaders.php
	 * @return string
	 */
	public function __getLastRequestHeaders () {}

	/**
	 * Returns last SOAP response headers.
	 * @link http://php.net/manual/en/function.soap-soapclient-getlastresponseheaders.php
	 * @return string
	 */
	public function __getLastResponseHeaders () {}

	/**
	 * Returns list of SOAP functions
	 * @link http://php.net/manual/en/function.soap-soapclient-getfunctions.php
	 * @return array
	 */
	public function __getFunctions () {}

	/**
	 * Returns list of SOAP types
	 * @link http://php.net/manual/en/function.soap-soapclient-gettypes.php
	 * @return array
	 */
	public function __getTypes () {}

	/**
	 * Performs a SOAP request
	 * @link http://php.net/manual/en/function.soap-soapclient-dorequest.php
	 * @param request string
	 * @param location string
	 * @param action string
	 * @param version int
	 * @param one_way int[optional]
	 * @return string
	 */
	public function __doRequest ($request, $location, $action, $version, $one_way = null) {}

	/**
	 * Sets the cookie that will be sent with the SOAP request
	 * @link http://php.net/manual/en/function.soap-soapclient-setcookie.php
	 * @param name string
	 * @param value string[optional]
	 * @return void
	 */
	public function __setCookie ($name, $value = null) {}

	public function __setLocation () {}

	public function __setSoapHeaders () {}

}

/**
 * SoapVar is a special low-level class for encoding
 *      parameters and returning values in non-WSDL mode. It's
 *      just a data holder and does not have any special methods except the constructor. 
 *      It's useful when you want to set the type property in SOAP request or response.
 * @link http://php.net/manual/en/ref.soap.php
 */
class SoapVar  {

	public function SoapVar () {}

}

/**
 * @link http://php.net/manual/en/ref.soap.php
 */
class SoapServer  {

	public function SoapServer () {}

	/**
	 * Sets persistence mode of SoapServer
	 * @link http://php.net/manual/en/function.soap-soapserver-setpersistence.php
	 * @param mode int
	 * @return void
	 */
	public function setPersistence ($mode) {}

	/**
	 * Sets class which will handle SOAP requests
	 * @link http://php.net/manual/en/function.soap-soapserver-setclass.php
	 * @param class_name string
	 * @param args mixed[optional]
	 * @param ... mixed[optional]
	 * @return void
	 */
	public function setClass ($class_name, $args = null) {}

	public function setObject () {}

	/**
	 * Adds one or several functions those will handle SOAP requests
	 * @link http://php.net/manual/en/function.soap-soapserver-addfunction.php
	 * @param functions mixed
	 * @return void
	 */
	public function addFunction ($functions) {}

	/**
	 * Returns list of defined functions
	 * @link http://php.net/manual/en/function.soap-soapserver-getfunctions.php
	 * @return array
	 */
	public function getFunctions () {}

	/**
	 * Handles a SOAP request
	 * @link http://php.net/manual/en/function.soap-soapserver-handle.php
	 * @param soap_request string[optional]
	 * @return void
	 */
	public function handle ($soap_request = null) {}

	/**
	 * Issue SoapServer fault indicating an error
	 * @link http://php.net/manual/en/function.soap-soapserver-fault.php
	 * @param code string
	 * @param string string
	 * @param actor string[optional]
	 * @param details mixed[optional]
	 * @param name string[optional]
	 * @return void
	 */
	public function fault ($code, $string, $actor = null, $details = null, $name = null) {}

	public function addSoapHeader () {}

}

/**
 * @link http://php.net/manual/en/ref.soap.php
 */
class SoapFault extends Exception  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function SoapFault () {}

	public function __toString () {}

	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

}

/**
 * SoapParam is a special low-level class for naming 
 *      parameters and returning values in non-WSDL mode. 
 *      It's just a data holder and it does not have any special methods except 
 *      its constructor.
 * @link http://php.net/manual/en/ref.soap.php
 */
class SoapParam  {

	public function SoapParam () {}

}

/**
 * SoapHeader is a special low-level class for passing 
 *      or returning SOAP headers. It's just a data holder and it does not have any 
 *      special methods except its constructor. It can be used in the  method to pass a SOAP header or 
 *      in a SOAP header handler to return the header in a SOAP response.
 * @link http://php.net/manual/en/ref.soap.php
 */
class SoapHeader  {

	public function SoapHeader () {}

}

/**
 * Set whether to use the SOAP error handler and return the former value
 * @link http://php.net/manual/en/function.use-soap-error-handler.php
 * @param handler bool[optional]
 * @return bool
 */
function use_soap_error_handler ($handler = null) {}

/**
 * Checks if SOAP call was failed
 * @link http://php.net/manual/en/function.is-soap-fault.php
 * @param obj mixed
 * @return bool
 */
function is_soap_fault ($obj) {}

define ('SOAP_1_1', 1);
define ('SOAP_1_2', 2);
define ('SOAP_PERSISTENCE_SESSION', 1);
define ('SOAP_PERSISTENCE_REQUEST', 2);
define ('SOAP_FUNCTIONS_ALL', 999);
define ('SOAP_ENCODED', 1);
define ('SOAP_LITERAL', 2);
define ('SOAP_RPC', 1);
define ('SOAP_DOCUMENT', 2);
define ('SOAP_ACTOR_NEXT', 1);
define ('SOAP_ACTOR_NONE', 2);
define ('SOAP_ACTOR_UNLIMATERECEIVER', 3);
define ('SOAP_COMPRESSION_ACCEPT', 32);
define ('SOAP_COMPRESSION_GZIP', 0);
define ('SOAP_COMPRESSION_DEFLATE', 16);
define ('SOAP_AUTHENTICATION_BASIC', 0);
define ('SOAP_AUTHENTICATION_DIGEST', 1);
define ('UNKNOWN_TYPE', 999998);
define ('XSD_STRING', 101);
define ('XSD_BOOLEAN', 102);
define ('XSD_DECIMAL', 103);
define ('XSD_FLOAT', 104);
define ('XSD_DOUBLE', 105);
define ('XSD_DURATION', 106);
define ('XSD_DATETIME', 107);
define ('XSD_TIME', 108);
define ('XSD_DATE', 109);
define ('XSD_GYEARMONTH', 110);
define ('XSD_GYEAR', 111);
define ('XSD_GMONTHDAY', 112);
define ('XSD_GDAY', 113);
define ('XSD_GMONTH', 114);
define ('XSD_HEXBINARY', 115);
define ('XSD_BASE64BINARY', 116);
define ('XSD_ANYURI', 117);
define ('XSD_QNAME', 118);
define ('XSD_NOTATION', 119);
define ('XSD_NORMALIZEDSTRING', 120);
define ('XSD_TOKEN', 121);
define ('XSD_LANGUAGE', 122);
define ('XSD_NMTOKEN', 123);
define ('XSD_NAME', 124);
define ('XSD_NCNAME', 125);
define ('XSD_ID', 126);
define ('XSD_IDREF', 127);
define ('XSD_IDREFS', 128);
define ('XSD_ENTITY', 129);
define ('XSD_ENTITIES', 130);
define ('XSD_INTEGER', 131);
define ('XSD_NONPOSITIVEINTEGER', 132);
define ('XSD_NEGATIVEINTEGER', 133);
define ('XSD_LONG', 134);
define ('XSD_INT', 135);
define ('XSD_SHORT', 136);
define ('XSD_BYTE', 137);
define ('XSD_NONNEGATIVEINTEGER', 138);
define ('XSD_UNSIGNEDLONG', 139);
define ('XSD_UNSIGNEDINT', 140);
define ('XSD_UNSIGNEDSHORT', 141);
define ('XSD_UNSIGNEDBYTE', 142);
define ('XSD_POSITIVEINTEGER', 143);
define ('XSD_NMTOKENS', 144);
define ('XSD_ANYTYPE', 145);
define ('XSD_ANYXML', 147);
define ('APACHE_MAP', 200);
define ('SOAP_ENC_OBJECT', 301);
define ('SOAP_ENC_ARRAY', 300);
define ('XSD_1999_TIMEINSTANT', 401);
define ('XSD_NAMESPACE', 'http://www.w3.org/2001/XMLSchema');
define ('XSD_1999_NAMESPACE', 'http://www.w3.org/1999/XMLSchema');
define ('SOAP_SINGLE_ELEMENT_ARRAYS', 1);
define ('SOAP_WAIT_ONE_WAY_CALLS', 2);
define ('SOAP_USE_XSI_ARRAY_TYPE', 4);
define ('WSDL_CACHE_NONE', 0);
define ('WSDL_CACHE_DISK', 1);
define ('WSDL_CACHE_MEMORY', 2);
define ('WSDL_CACHE_BOTH', 3);

// End of soap v.

// Start of sysvmsg v.

/**
 * Create or attach to a message queue
 * @link http://php.net/manual/en/function.msg-get-queue.php
 * @param key int
 * @param perms int[optional]
 * @return resource an id that can be used to access the System V message queue.
 */
function msg_get_queue ($key, $perms = null) {}

/**
 * Send a message to a message queue
 * @link http://php.net/manual/en/function.msg-send.php
 * @param queue resource
 * @param msgtype int
 * @param message mixed
 * @param serialize bool[optional]
 * @param blocking bool[optional]
 * @param errorcode int[optional]
 * @return bool
 */
function msg_send ($queue, $msgtype, $message, $serialize = null, $blocking = null, &$errorcode = null) {}

/**
 * Receive a message from a message queue
 * @link http://php.net/manual/en/function.msg-receive.php
 * @param queue resource
 * @param desiredmsgtype int
 * @param msgtype int
 * @param maxsize int
 * @param message mixed
 * @param unserialize bool[optional]
 * @param flags int[optional]
 * @param errorcode int[optional]
 * @return bool
 */
function msg_receive ($queue, $desiredmsgtype, &$msgtype, $maxsize, &$message, $unserialize = null, $flags = null, &$errorcode = null) {}

/**
 * Destroy a message queue
 * @link http://php.net/manual/en/function.msg-remove-queue.php
 * @param queue resource
 * @return bool
 */
function msg_remove_queue ($queue) {}

/**
 * Returns information from the message queue data structure
 * @link http://php.net/manual/en/function.msg-stat-queue.php
 * @param queue resource
 * @return array
 */
function msg_stat_queue ($queue) {}

/**
 * Set information in the message queue data structure
 * @link http://php.net/manual/en/function.msg-set-queue.php
 * @param queue resource
 * @param data array
 * @return bool
 */
function msg_set_queue ($queue, array $data) {}

define ('MSG_IPC_NOWAIT', 1);
define ('MSG_EAGAIN', 11);
define ('MSG_ENOMSG', 42);
define ('MSG_NOERROR', 2);
define ('MSG_EXCEPT', 4);

// End of sysvmsg v.

// Start of tidy v.2.0

class tidy  {

	public function getOpt () {}

	public function cleanRepair () {}

	public function parseFile () {}

	public function parseString () {}

	public function repairString () {}

	public function repairFile () {}

	public function diagnose () {}

	public function getRelease () {}

	public function getConfig () {}

	public function getStatus () {}

	public function getHtmlVer () {}

	public function isXhtml () {}

	public function isXml () {}

	public function root () {}

	public function head () {}

	public function html () {}

	public function body () {}

	/**
	 * Constructs a new tidy object
	 * @link http://php.net/manual/en/function.tidy-construct.php
	 */
	public function __construct () {}

}

/**
 * @link http://php.net/manual/en/ref.tidy.php
 */
final class tidyNode  {

	/**
	 * Returns true if this node has children
	 * @link http://php.net/manual/en/function.tidyNode-hasChildren.php
	 */
	public function hasChildren () {}

	/**
	 * Returns true if this node has siblings
	 * @link http://php.net/manual/en/function.tidyNode-hasSiblings.php
	 */
	public function hasSiblings () {}

	/**
	 * Returns true if this node represents a comment
	 * @link http://php.net/manual/en/function.tidyNode-isComment.php
	 */
	public function isComment () {}

	/**
	 * Returns true if this node is part of a HTML document
	 * @link http://php.net/manual/en/function.tidyNode-isHtml.php
	 */
	public function isHtml () {}

	/**
	 * Returns true if this node represents text (no markup)
	 * @link http://php.net/manual/en/function.tidyNode-isText.php
	 */
	public function isText () {}

	/**
	 * Returns true if this node is JSTE
	 * @link http://php.net/manual/en/function.tidyNode-isJste.php
	 */
	public function isJste () {}

	/**
	 * Returns true if this node is ASP
	 * @link http://php.net/manual/en/function.tidyNode-isAsp.php
	 */
	public function isAsp () {}

	/**
	 * Returns true if this node is PHP
	 * @link http://php.net/manual/en/function.tidyNode-isPhp.php
	 */
	public function isPhp () {}

	/**
	 * returns the parent node of the current node
	 * @link http://php.net/manual/en/function.tidynode-getparent.php
	 * @return tidyNode a tidyNode if the node has a parent, or &null;
	 */
	public function getParent () {}

}

/**
 * Returns the value of the specified configuration option for the tidy document
 * @link http://php.net/manual/en/function.tidy-getopt.php
 */
function tidy_getopt () {}

/**
 * Parse a document stored in a string
 * @link http://php.net/manual/en/function.tidy-parse-string.php
 */
function tidy_parse_string () {}

/**
 * Parse markup in file or URI
 * @link http://php.net/manual/en/function.tidy-parse-file.php
 */
function tidy_parse_file () {}

/**
 * Return a string representing the parsed tidy markup
 * @link http://php.net/manual/en/function.tidy-get-output.php
 */
function tidy_get_output () {}

/**
 * Return warnings and errors which occurred parsing the specified document
 * @link http://php.net/manual/en/function.tidy-get-error-buffer.php
 */
function tidy_get_error_buffer () {}

/**
 * Execute configured cleanup and repair operations on parsed markup
 * @link http://php.net/manual/en/function.tidy-clean-repair.php
 */
function tidy_clean_repair () {}

/**
 * Repair a string using an optionally provided configuration file
 * @link http://php.net/manual/en/function.tidy-repair-string.php
 */
function tidy_repair_string () {}

/**
 * Repair a file and return it as a string
 * @link http://php.net/manual/en/function.tidy-repair-file.php
 */
function tidy_repair_file () {}

/**
 * Run configured diagnostics on parsed and repaired markup
 * @link http://php.net/manual/en/function.tidy-diagnose.php
 */
function tidy_diagnose () {}

/**
 * Get release date (version) for Tidy library
 * @link http://php.net/manual/en/function.tidy-get-release.php
 */
function tidy_get_release () {}

/**
 * Get current Tidy configuration
 * @link http://php.net/manual/en/function.tidy-get-config.php
 */
function tidy_get_config () {}

/**
 * Get status of specified document
 * @link http://php.net/manual/en/function.tidy-get-status.php
 */
function tidy_get_status () {}

/**
 * Get the Detected HTML version for the specified document
 * @link http://php.net/manual/en/function.tidy-get-html-ver.php
 */
function tidy_get_html_ver () {}

/**
 * Indicates if the document is a XHTML document
 * @link http://php.net/manual/en/function.tidy-is-xhtml.php
 */
function tidy_is_xhtml () {}

/**
 * Indicates if the document is a generic (non HTML/XHTML) XML document
 * @link http://php.net/manual/en/function.tidy-is-xml.php
 */
function tidy_is_xml () {}

/**
 * Returns the Number of Tidy errors encountered for specified document
 * @link http://php.net/manual/en/function.tidy-error-count.php
 */
function tidy_error_count () {}

/**
 * Returns the Number of Tidy warnings encountered for specified document
 * @link http://php.net/manual/en/function.tidy-warning-count.php
 */
function tidy_warning_count () {}

/**
 * Returns the Number of Tidy accessibility warnings encountered for specified document
 * @link http://php.net/manual/en/function.tidy-access-count.php
 */
function tidy_access_count () {}

/**
 * Returns the Number of Tidy configuration errors encountered for specified document
 * @link http://php.net/manual/en/function.tidy-config-count.php
 */
function tidy_config_count () {}

/**
 * Returns a tidyNode object representing the root of the tidy parse tree
 * @link http://php.net/manual/en/function.tidy-get-root.php
 */
function tidy_get_root () {}

/**
 * Returns a tidyNode Object starting from the &lt;head&gt; tag of the tidy parse tree
 * @link http://php.net/manual/en/function.tidy-get-head.php
 */
function tidy_get_head () {}

/**
 * Returns a tidyNode Object starting from the &lt;html&gt; tag of the tidy parse tree
 * @link http://php.net/manual/en/function.tidy-get-html.php
 */
function tidy_get_html () {}

/**
 * Returns a tidyNode Object starting from the &lt;body&gt; tag of the tidy parse tree
 * @link http://php.net/manual/en/function.tidy-get-body.php
 */
function tidy_get_body () {}

/**
 * ob_start callback function to repair the buffer
 * @link http://php.net/manual/en/function.ob-tidyhandler.php
 */
function ob_tidyhandler () {}

define ('TIDY_TAG_UNKNOWN', 0);
define ('TIDY_TAG_A', 1);
define ('TIDY_TAG_ABBR', 2);
define ('TIDY_TAG_ACRONYM', 3);
define ('TIDY_TAG_ADDRESS', 4);
define ('TIDY_TAG_ALIGN', 5);
define ('TIDY_TAG_APPLET', 6);
define ('TIDY_TAG_AREA', 7);
define ('TIDY_TAG_B', 8);
define ('TIDY_TAG_BASE', 9);
define ('TIDY_TAG_BASEFONT', 10);
define ('TIDY_TAG_BDO', 11);
define ('TIDY_TAG_BGSOUND', 12);
define ('TIDY_TAG_BIG', 13);
define ('TIDY_TAG_BLINK', 14);
define ('TIDY_TAG_BLOCKQUOTE', 15);
define ('TIDY_TAG_BODY', 16);
define ('TIDY_TAG_BR', 17);
define ('TIDY_TAG_BUTTON', 18);
define ('TIDY_TAG_CAPTION', 19);
define ('TIDY_TAG_CENTER', 20);
define ('TIDY_TAG_CITE', 21);
define ('TIDY_TAG_CODE', 22);
define ('TIDY_TAG_COL', 23);
define ('TIDY_TAG_COLGROUP', 24);
define ('TIDY_TAG_COMMENT', 25);
define ('TIDY_TAG_DD', 26);
define ('TIDY_TAG_DEL', 27);
define ('TIDY_TAG_DFN', 28);
define ('TIDY_TAG_DIR', 29);
define ('TIDY_TAG_DIV', 30);
define ('TIDY_TAG_DL', 31);
define ('TIDY_TAG_DT', 32);
define ('TIDY_TAG_EM', 33);
define ('TIDY_TAG_EMBED', 34);
define ('TIDY_TAG_FIELDSET', 35);
define ('TIDY_TAG_FONT', 36);
define ('TIDY_TAG_FORM', 37);
define ('TIDY_TAG_FRAME', 38);
define ('TIDY_TAG_FRAMESET', 39);
define ('TIDY_TAG_H1', 40);
define ('TIDY_TAG_H2', 41);
define ('TIDY_TAG_H3', 42);
define ('TIDY_TAG_H4', 43);
define ('TIDY_TAG_H5', 44);
define ('TIDY_TAG_H6', 45);
define ('TIDY_TAG_HEAD', 46);
define ('TIDY_TAG_HR', 47);
define ('TIDY_TAG_HTML', 48);
define ('TIDY_TAG_I', 49);
define ('TIDY_TAG_IFRAME', 50);
define ('TIDY_TAG_ILAYER', 51);
define ('TIDY_TAG_IMG', 52);
define ('TIDY_TAG_INPUT', 53);
define ('TIDY_TAG_INS', 54);
define ('TIDY_TAG_ISINDEX', 55);
define ('TIDY_TAG_KBD', 56);
define ('TIDY_TAG_KEYGEN', 57);
define ('TIDY_TAG_LABEL', 58);
define ('TIDY_TAG_LAYER', 59);
define ('TIDY_TAG_LEGEND', 60);
define ('TIDY_TAG_LI', 61);
define ('TIDY_TAG_LINK', 62);
define ('TIDY_TAG_LISTING', 63);
define ('TIDY_TAG_MAP', 64);
define ('TIDY_TAG_MARQUEE', 65);
define ('TIDY_TAG_MENU', 66);
define ('TIDY_TAG_META', 67);
define ('TIDY_TAG_MULTICOL', 68);
define ('TIDY_TAG_NOBR', 69);
define ('TIDY_TAG_NOEMBED', 70);
define ('TIDY_TAG_NOFRAMES', 71);
define ('TIDY_TAG_NOLAYER', 72);
define ('TIDY_TAG_NOSAVE', 73);
define ('TIDY_TAG_NOSCRIPT', 74);
define ('TIDY_TAG_OBJECT', 75);
define ('TIDY_TAG_OL', 76);
define ('TIDY_TAG_OPTGROUP', 77);
define ('TIDY_TAG_OPTION', 78);
define ('TIDY_TAG_P', 79);
define ('TIDY_TAG_PARAM', 80);
define ('TIDY_TAG_PLAINTEXT', 81);
define ('TIDY_TAG_PRE', 82);
define ('TIDY_TAG_Q', 83);
define ('TIDY_TAG_RB', 84);
define ('TIDY_TAG_RBC', 85);
define ('TIDY_TAG_RP', 86);
define ('TIDY_TAG_RT', 87);
define ('TIDY_TAG_RTC', 88);
define ('TIDY_TAG_RUBY', 89);
define ('TIDY_TAG_S', 90);
define ('TIDY_TAG_SAMP', 91);
define ('TIDY_TAG_SCRIPT', 92);
define ('TIDY_TAG_SELECT', 93);
define ('TIDY_TAG_SERVER', 94);
define ('TIDY_TAG_SERVLET', 95);
define ('TIDY_TAG_SMALL', 96);
define ('TIDY_TAG_SPACER', 97);
define ('TIDY_TAG_SPAN', 98);
define ('TIDY_TAG_STRIKE', 99);
define ('TIDY_TAG_STRONG', 100);
define ('TIDY_TAG_STYLE', 101);
define ('TIDY_TAG_SUB', 102);
define ('TIDY_TAG_SUP', 103);
define ('TIDY_TAG_TABLE', 104);
define ('TIDY_TAG_TBODY', 105);
define ('TIDY_TAG_TD', 106);
define ('TIDY_TAG_TEXTAREA', 107);
define ('TIDY_TAG_TFOOT', 108);
define ('TIDY_TAG_TH', 109);
define ('TIDY_TAG_THEAD', 110);
define ('TIDY_TAG_TITLE', 111);
define ('TIDY_TAG_TR', 112);
define ('TIDY_TAG_TT', 113);
define ('TIDY_TAG_U', 114);
define ('TIDY_TAG_UL', 115);
define ('TIDY_TAG_VAR', 116);
define ('TIDY_TAG_WBR', 117);
define ('TIDY_TAG_XMP', 118);
define ('TIDY_NODETYPE_ROOT', 0);
define ('TIDY_NODETYPE_DOCTYPE', 1);
define ('TIDY_NODETYPE_COMMENT', 2);
define ('TIDY_NODETYPE_PROCINS', 3);
define ('TIDY_NODETYPE_TEXT', 4);
define ('TIDY_NODETYPE_START', 5);
define ('TIDY_NODETYPE_END', 6);
define ('TIDY_NODETYPE_STARTEND', 7);
define ('TIDY_NODETYPE_CDATA', 8);
define ('TIDY_NODETYPE_SECTION', 9);
define ('TIDY_NODETYPE_ASP', 10);
define ('TIDY_NODETYPE_JSTE', 11);
define ('TIDY_NODETYPE_PHP', 12);
define ('TIDY_NODETYPE_XMLDECL', 13);

// End of tidy v.2.0

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

/**
 * Gets the version of the current Zend engine
 * @link http://php.net/manual/en/function.zend-version.php
 * @return string the Zend Engine version number, as a string.
 */
function zend_version () {}

/**
 * Returns the number of arguments passed to the function
 * @link http://php.net/manual/en/function.func-num-args.php
 * @return int the number of arguments passed into the current user-defined
 */
function func_num_args () {}

/**
 * Return an item from the argument list
 * @link http://php.net/manual/en/function.func-get-arg.php
 * @param arg_num int
 * @return mixed the specified argument, or false on error.
 */
function func_get_arg ($arg_num) {}

/**
 * Returns an array comprising a function's argument list
 * @link http://php.net/manual/en/function.func-get-args.php
 * @return array an array in which each element is a copy of the corresponding
 */
function func_get_args () {}

/**
 * Get string length
 * @link http://php.net/manual/en/function.strlen.php
 * @param string string
 * @return int
 */
function strlen ($string) {}

/**
 * Binary safe string comparison
 * @link http://php.net/manual/en/function.strcmp.php
 * @param str1 string
 * @param str2 string
 * @return int &lt; 0 if str1 is less than
 */
function strcmp ($str1, $str2) {}

/**
 * Binary safe string comparison of the first n characters
 * @link http://php.net/manual/en/function.strncmp.php
 * @param str1 string
 * @param str2 string
 * @param len int
 * @return int &lt; 0 if str1 is less than
 */
function strncmp ($str1, $str2, $len) {}

/**
 * Binary safe case-insensitive string comparison
 * @link http://php.net/manual/en/function.strcasecmp.php
 * @param str1 string
 * @param str2 string
 * @return int &lt; 0 if str1 is less than
 */
function strcasecmp ($str1, $str2) {}

/**
 * Binary safe case-insensitive string comparison of the first n characters
 * @link http://php.net/manual/en/function.strncasecmp.php
 * @param str1 string
 * @param str2 string
 * @param len int
 * @return int &lt; 0 if str1 is less than
 */
function strncasecmp ($str1, $str2, $len) {}

/**
 * Return the current key and value pair from an array and advance the array cursor
 * @link http://php.net/manual/en/function.each.php
 * @param var1
 */
function each (&$var1) {}

/**
 * Sets which PHP errors are reported
 * @link http://php.net/manual/en/function.error-reporting.php
 * @param level int[optional]
 * @return int the old error_reporting
 */
function error_reporting ($level = null) {}

/**
 * Defines a named constant
 * @link http://php.net/manual/en/function.define.php
 * @param name string
 * @param value mixed
 * @param case_insensitive bool[optional]
 * @return bool
 */
function define ($name, $value, $case_insensitive = null) {}

/**
 * Checks whether a given named constant exists
 * @link http://php.net/manual/en/function.defined.php
 * @param name string
 * @return bool true if the named constant given by name
 */
function defined ($name) {}

/**
 * Returns the name of the class of an object
 * @link http://php.net/manual/en/function.get-class.php
 * @param object object[optional]
 * @return string the name of the class of which object is an
 */
function get_class ($object = null) {}

/**
 * Retrieves the parent class name for object or class
 * @link http://php.net/manual/en/function.get-parent-class.php
 * @param object mixed[optional]
 * @return string the name of the parent class of the class of which
 */
function get_parent_class ($object = null) {}

/**
 * Checks if the class method exists
 * @link http://php.net/manual/en/function.method-exists.php
 * @param object object
 * @param method_name string
 * @return bool true if the method given by method_name
 */
function method_exists ($object, $method_name) {}

/**
 * Checks if the object or class has a property
 * @link http://php.net/manual/en/function.property-exists.php
 * @param class mixed
 * @param property string
 * @return bool true if the property exists, false if it doesn't exist or
 */
function property_exists ($class, $property) {}

/**
 * Checks if the class has been defined
 * @link http://php.net/manual/en/function.class-exists.php
 * @param class_name string
 * @param autoload bool[optional]
 * @return bool true if class_name is a defined class,
 */
function class_exists ($class_name, $autoload = null) {}

/**
 * Checks if the interface has been defined
 * @link http://php.net/manual/en/function.interface-exists.php
 * @param interface_name string
 * @param autoload bool[optional]
 * @return bool true if the interface given by 
 */
function interface_exists ($interface_name, $autoload = null) {}

/**
 * Return &true; if the given function has been defined
 * @link http://php.net/manual/en/function.function-exists.php
 * @param function_name string
 * @return bool true if function_name exists and is a
 */
function function_exists ($function_name) {}

/**
 * Returns an array with the names of included or required files
 * @link http://php.net/manual/en/function.get-included-files.php
 * @return array an array of the names of all files.
 */
function get_included_files () {}

/**
 * &Alias; <function>get_included_files</function>
 * @link http://php.net/manual/en/function.get-required-files.php
 */
function get_required_files () {}

/**
 * Checks if the object has this class as one of its parents
 * @link http://php.net/manual/en/function.is-subclass-of.php
 * @param object mixed
 * @param class_name string
 * @return bool
 */
function is_subclass_of ($object, $class_name) {}

/**
 * Checks if the object is of this class or has this class as one of its parents
 * @link http://php.net/manual/en/function.is-a.php
 * @param object object
 * @param class_name string
 * @return bool true if the object is of this class or has this class as one of
 */
function is_a ($object, $class_name) {}

/**
 * Get the default properties of the class
 * @link http://php.net/manual/en/function.get-class-vars.php
 * @param class_name string
 * @return array an associative array of default public properties of the class.
 */
function get_class_vars ($class_name) {}

/**
 * Gets the properties of the given object
 * @link http://php.net/manual/en/function.get-object-vars.php
 * @param object object
 * @return array an associative array of defined object properties for the
 */
function get_object_vars ($object) {}

/**
 * Gets the class methods' names
 * @link http://php.net/manual/en/function.get-class-methods.php
 * @param class_name mixed
 * @return array an array of method names defined for the class specified by
 */
function get_class_methods ($class_name) {}

/**
 * Generates a user-level error/warning/notice message
 * @link http://php.net/manual/en/function.trigger-error.php
 * @param error_msg string
 * @param error_type int[optional]
 * @return bool
 */
function trigger_error ($error_msg, $error_type = null) {}

/**
 * Alias of <function>trigger_error</function>
 * @link http://php.net/manual/en/function.user-error.php
 */
function user_error () {}

/**
 * Sets a user-defined error handler function
 * @link http://php.net/manual/en/function.set-error-handler.php
 * @param error_handler callback
 * @param error_types int[optional]
 * @return mixed a string containing the previously defined
 */
function set_error_handler ($error_handler, $error_types = null) {}

/**
 * Restores the previous error handler function
 * @link http://php.net/manual/en/function.restore-error-handler.php
 * @return bool
 */
function restore_error_handler () {}

/**
 * Sets a user-defined exception handler function
 * @link http://php.net/manual/en/function.set-exception-handler.php
 * @param exception_handler callback
 * @return string the name of the previously defined exception handler, or &null; on error. If
 */
function set_exception_handler ($exception_handler) {}

/**
 * Restores the previously defined exception handler function
 * @link http://php.net/manual/en/function.restore-exception-handler.php
 * @return bool
 */
function restore_exception_handler () {}

/**
 * Returns an array with the name of the defined classes
 * @link http://php.net/manual/en/function.get-declared-classes.php
 * @return array an array of the names of the declared classes in the current
 */
function get_declared_classes () {}

/**
 * Returns an array of all declared interfaces
 * @link http://php.net/manual/en/function.get-declared-interfaces.php
 * @return array an array of the names of the declared interfaces in the current
 */
function get_declared_interfaces () {}

/**
 * Returns an array of all defined functions
 * @link http://php.net/manual/en/function.get-defined-functions.php
 * @return array an multidimensional array containing a list of all defined
 */
function get_defined_functions () {}

/**
 * Returns an array of all defined variables
 * @link http://php.net/manual/en/function.get-defined-vars.php
 * @return array
 */
function get_defined_vars () {}

/**
 * Create an anonymous (lambda-style) function
 * @link http://php.net/manual/en/function.create-function.php
 * @param args string
 * @param code string
 * @return string a unique function name as a string, or false on error.
 */
function create_function ($args, $code) {}

/**
 * Returns the resource type
 * @link http://php.net/manual/en/function.get-resource-type.php
 * @param handle resource
 * @return string
 */
function get_resource_type ($handle) {}

/**
 * Returns an array with the names of all modules compiled and loaded
 * @link http://php.net/manual/en/function.get-loaded-extensions.php
 * @return array an indexed array of all the modules names.
 */
function get_loaded_extensions () {}

/**
 * Find out whether an extension is loaded
 * @link http://php.net/manual/en/function.extension-loaded.php
 * @param name string
 * @return bool true if the extension identified by name
 */
function extension_loaded ($name) {}

/**
 * Returns an array with the names of the functions of a module
 * @link http://php.net/manual/en/function.get-extension-funcs.php
 * @param module_name string
 * @return array an array with all the functions, or false if 
 */
function get_extension_funcs ($module_name) {}

/**
 * Returns an associative array with the names of all the constants and their values
 * @link http://php.net/manual/en/function.get-defined-constants.php
 * @param categorize mixed[optional]
 * @return array
 */
function get_defined_constants ($categorize = null) {}

/**
 * Generates a backtrace
 * @link http://php.net/manual/en/function.debug-backtrace.php
 * @return array an associative array. The possible returned elements
 */
function debug_backtrace () {}

/**
 * Prints a backtrace
 * @link http://php.net/manual/en/function.debug-print-backtrace.php
 * @return void
 */
function debug_print_backtrace () {}

class stdClass  {
}

class Exception  {
	protected $message;
	private $string;
	protected $code;
	protected $file;
	protected $line;
	private $trace;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class ErrorException extends Exception  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $severity;


	/**
	 * @param message[optional]
	 * @param code[optional]
	 * @param severity[optional]
	 * @param filename[optional]
	 * @param lineno[optional]
	 */
	public function __construct ($message, $code, $severity, $filename, $lineno) {}

	final public function getSeverity () {}

	final private function __clone () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}


// Start of Zend Extensions

// Constants for jobs status
define('JOB_QUEUE_STATUS_SUCCESS', 1);             // Job was processed and succeeded
define('JOB_QUEUE_STATUS_WAITING', 2);             // Job is waiting for being processed (was not scheduled)
define('JOB_QUEUE_STATUS_SUSPENDED', 3);           // Job was suspended
define('JOB_QUEUE_STATUS_SCHEDULED', 4);           // Job is scheduled and waiting in queue
define('JOB_QUEUE_STATUS_WAITING_PREDECESSOR', 5); // Job is waiting for it's predecessor to be completed
define('JOB_QUEUE_STATUS_IN_PROCESS', 6);          // Job is in process in Queue
define('JOB_QUEUE_STATUS_EXECUTION_FAILED', 7);    // Job execution failed in the ZendEnabler
define('JOB_QUEUE_STATUS_LOGICALLY_FAILED', 8);    // Job was processed and failed logically either
                                                   // because of job_fail command or script parse or
                                                   // fatal error

// Constants for different priorities of jobs
define('JOB_QUEUE_PRIORITY_LOW', 0);
define('JOB_QUEUE_PRIORITY_NORMAL', 1);
define('JOB_QUEUE_PRIORITY_HIGH', 2);
define('JOB_QUEUE_PRIORITY_URGENT', 3);

// Constants for saving global variables bit mask
define('JOB_QUEUE_SAVE_POST', 1);
define('JOB_QUEUE_SAVE_GET', 2);
define('JOB_QUEUE_SAVE_COOKIE', 4);
define('JOB_QUEUE_SAVE_SESSION', 8);
define('JOB_QUEUE_SAVE_RAW_POST', 16);
define('JOB_QUEUE_SAVE_SERVER', 32);
define('JOB_QUEUE_SAVE_FILES', 64);
define('JOB_QUEUE_SAVE_ENV', 128);


/**
 * causes a job to fail logically
 * can be used to indicate an error in the script logic (e.g. database connection problem)
 * @param string $error_string the error string to display 
 */
set_job_failed( $error_string );



/**
 * returns array containing following fields:
 * "license_ok" - whether license allows use of JobQueue
 * "expires" - license expiration date 
 */
jobqueue_license_info();


class ZendAPI_Queue {
    var $_jobqueue_url;
    
    /**
     * Constructor for a job queue connection
     *
     * @param string $jobqueue_url Full address where the queue is in the form host:port
     * @return zendapi_queue object
     */
    function zendapi_queue($queue_url) {}
    
    /**
     * Open a connection to a job queue
     *
     * @param string $password For authentication, password must be specified to connect to a queue
     * @param int $application_id Optional, if set, all subsequent calls to job related methods will use this application id (unless explicitly specified otherwise). I.e. When adding new job, 
        unless this job already set an application id, the job will be assigned the queue application id
     * @return bool Success
     */
    function login($password, $application_id=null) {}
    
    
    /**
     * Insert a new job to the queue, the Job is passed by reference because 
        its new job ID and status will be set in the Job object
         * If the returned job id is 0 it means the job could be added to the queue
         *
     * @param Job $job The Job we want to insert to the queue (by ref.)
     * @return int The inserted job id
     */
    function addJob(&$job) {}
    

    /**
     * Return a Job object that describing a job in the queue
         *
     * @param int $job_id The job id
     * @return Job Object describing a job in the queue
     */
    function getJob($job_id) {}

    /**
     * Update an existing job in the queue with it's new properties. If job doesn't exists, 
        a new job will be added. Job is passed by reference and it's updated from the queue.
     *
     * @param Job $job The Job object, the ID of the given job is the id of the job we try to update.
        If the given Job doesn't have an assigned ID, a new job will be added
     * @return int The id of the updated job
     */
    function updateJob(&$job) {}
    
    /**
     * Remove a job from the queue
     *
     * @param int|array $job_id The job id or array of job ids we want to remove from the queue
     * @return bool Success/Failure
     */
    function removeJob($job_id) {}

    
    /**
     * Suspend a job in the queue (without removing it)
     *
     * @param int|array $job_id The job id or array of job ids we want to suspend
     * @return bool Success/Failure
     */
    function suspendJob($job_id) {}


    /**
     * Resume a suspended job in the queue
     *
     * @param int|array $job_id The job id or array of job ids we want to resume
     * @return bool Success/Failure (if the job wasn't suspended, the function will return false)
     */
    function resumeJob($job_id) {}


    /**
     * Requeue failed job back to the queue.
     *
     * @param job $job  job object to re-query
     * @return bool - true or false.
     */
    function requeueJob($job) {}


    /**
     * returns job statistics
         * @return array with the following:
                         "total_complete_jobs"
                         "total_incomplete_jobs"
                         "average_time_in_queue"  [msec]
                         "average_waiting_time"   [sec]
                         "added_jobs_in_window"
                         "activated_jobs_in_window"
                         "completed_jobs_in_window"
         * moving window size can be set through ini file
         */
    function getStatistics() {}


    /**
     * Returns whether a script exists in the document root
     * @param string $path relative script path
     * @return bool - TRUE if script exists in the document root FALSE otherwise
     */
    function isScriptExists($path) {}


    /**
     * Returns whether the queue is suspended
     * @return bool - TRUE if job is suspended FALSE otherwise
     */
    function isSuspend() {}


    /**
     * Return a list of jobs in the queue according to the options given in the filter_options parameter, doesn't return jobs in "final states" (failed, complete)
     * If application id is set for this queue, only jobs with this application id will be returned
     *
     * @param array $filter_options Array of optional filter options to filter the jobs we want to get 
        from the queue. If not set, all jobs will be returned.<br>
     *     Options can be: priority, application_id, name, status, recurring.
     * @param int max_jobs  Maximum jobs to retrive. Default is -1, getting all jobs available.
     * @param bool with_globals_and_output. Whether gets the global variables dataand job output.
     *     Default is false.
     * @return array. Jobs that satisfies filter_options.
     */
    function getJobsInQueue($filter_options=null, $max_jobs=-1, $with_globals_and_output=false) {}
    

    /**
     * Return the number of jobs in the queue according to the options given in the filter_options parameter
     * @param array $filter_options Array of optional filter options to filter the jobs we want to get from the queue. If not set, all jobs will be counted.<br>
     *     Options can be: priority, application_id, host, name, status, recurring.
     * @return int. Number of jobs that satisfy filter_options.
     */
    function getNumOfJobsInQueue($filter_options=null) {}


    /**
     * Return all the hosts that jobs were submitted from.
     * @return array. 
     */
    function getAllhosts() {}


    /**
     * Return all the application ids exists in queue.
     * @return array.
     */
    function getAllApplicationIDs() {}



    /**
     * Return finished jobs (either failed or successed) between time range allowing paging.
     * Jobs are sorted by job id descending.
     *
     * @param int $status. Filter to jobs by status, 1-success, 0-failed either logical or execution.
     * @param UNIX timestamp $start_time. Get only jobs finished after $start_time.
     * @param UNIX timestamp $end_time. Get only jobs finished before $end_time.
     * @param int $index. Get jobs starting from the $index-th place.
     * @param int $count. Get only $count jobs.
     * @param int $total. Pass by reference. Return the total number of jobs statisifed the query criteria. 
     *
     * @return array of jobs.
     */
    function getHistoricJobs($status, $start_time, $end_time, $index, $count, &$total) {}


    /**
     * Suspends queue operation
     * @return bool - TRUE if successful FALSE otherwise
     */
    function suspendQueue() {}


    /**
     * Resumes queue operation
     * @return bool - TRUE if successful FALSE otherwise
     */
    function resumeQueue() {}


    /**
     * Return description of the last error occured in the queue object. After every
     *    method invoked an error string describing the error is store in the queue object.
     * @return string.
     */
    function getLastError() {}


    /**
     * Sets a new maximum time for keeping historic jobs
     * @return bool - TRUE if successful FALSE otherwise
     */
    function setMaxHistoryTime() {}
}

/**
 * Describing a job in a queue
 * In order to add/modify a job in the queue, a Job class must be created/retrieved and than saved in a queue
 *
 * For simplicity, a job can be added directly to a queue and without creating an instant of a Queue object
 */
class ZendAPI_Job {
    
    /**
     * Unique id of the Job in the job queue
     *
     * @var int
     */
    var $_id;
    
    /**
     * Full path of the script that this job calls when it's processed
     *
     * @var string
     */
    var $_script;
    
    /**
     * The host that the job was submit from
     *
     * @var string
     */
    var $_host;

    /**
     * A short string describing the job
     *
     * @var string
     */
    var $_name;


    /**
     * The job output after executing
     *
     * @var string
     */
    var $_output;

    /**
     * The status of the job
     * By default, the job status is waiting to being execute. 
     * The status is determent by the queue and can not be modify by the user.
     *
     * @var int
     */
    var $_status = JOB_QUEUE_STATUS_WAITING;

    /**
     * The application id of the job
     * If the application id is not set, this job may get an application id automatically from the queue 
     * (if the queue was assigned one). By default it is null (which indicates no application id is assigned)
     *
     * @var string
     */
    var $_application_id = null;
    
    /**
     * The priority of the job, options are the priority constants
     * By default the priority is set to normal (JOB_QUEUE_PRIORITY_NORMAL)
     *
     * @var int
     */
    var $_priority = JOB_QUEUE_PRIORITY_NORMAL;
    
    /**
     * Array holding all the variables that the user wants the job's script to have when it's called
     * The structure is variable_name => variable_value
        i.e. if the user_variables array is array('my_var' => 8), when the script is called,
        a global variable called $my_var will have the int value of 8
     * By default there are no variables that we want to add to the job's script
     *
     * @var array
     */
    var $_user_variables = array();
    
    /**
     * Bit mask holding the global variables that the user want the job's script to have when it's called
     * Options are prefixed with "JOB_QUEUE_SAVE_" and may be:
        POST|GET|COOKIE|SESSION|RAW_POST|SERVER|FILES|ENV
     * By default there are no global variables we want to add to the job's script
     * i.e. In order to save the current GET and COOKIE global variables,
        this property should be JOB_QUEUE_SAVE_GET|JOB_QUEUE_SAVE_COOKIE (or the integer 6)
        In that case (of GET and COOKIE), when the job is added, the current $_GET and 
        $_COOKIE variables  should be saved, and when the job's script is called,
        those global variables should be populated
     *
     * @var int
     */
    var $_global_variables = 0;
    
    /**
     * The job may have a dependency (another job that must be performed before this job)
     * This property hold the id of the job that must be performed. if this variable is an array of integers,
        it means that there are several jobs that must be performed before this job 
     * By default there are no dependencies
     *
     * @var mixed (int|array)
     */
    var $_predecessor = null;
    
    /**
     * The time that this job should be performed, this variables is the UNIX timestamp.
     * If set to 0, it means that the job should be performed now (or at least as soon as possible)
     * By default there is no scheduled time, which means we want to perform the job as soon as possible
     *
     * @var int
     */
    var $_scheduled_time = 0;
    
    /**
     * The job running frequency in seconds. The job should run every _internal seconds
     * This property applys only to recurrent job. 
     * By default, its value is 0 e.g. run it only once.
     *
     * @var int
     */
         var $_interval = 0;

    /**
     * UNIX timestamp that it's the last time this job should occurs. If _interval was set, and _end_time
     * was not, then this job will run forever.
     * By default there is no end_time, so recurrent job will run forever. If the job is not recurrent
     * (occurs only once) then the job will run at most once. If end_time has reached and the job was not
     * execute yet, it will not run.
     * 
     * @var int
     */
     var $_end_time = null;


    /**
     * A bit that determine whether job can be deleted from history. When set, removeJob will not
     * delete the job from history.
     *
     * @var int
     */
     var $_preserved = 0;

    
    /**
     * Instantiate a Job object, describe all the information and properties of a job
     *
     * @param script $script relative path (relative to document root supplied in ini file) of the script this job should call when it's executing
     * @return Job
     */
    function ZendAPI_Job($script) {}
    

    /**
     * Add the job the the specified queue (without instantiating a JobQueue object)
     * This function should be used for extreme simplicity of the user when adding a single job,
            when the user want to insert more than one job and/or manipulating other jobs (or job tasks) 
            he should create and use the JobQueue object
     * Actually what this function do is to create a new JobQueue, login to it (with the given parameters), 
            add this job to it and logout
     * 
     * @param string $jobqueue_url Full address of the queue we want to connect to
     * @param string $password For authentication, the queue password
     * @return int The added job id or false on failure
     */
    function addJobToQueue($jobqueue_url, $password) {}


    /**
     * Set a new priority to the job
     *
     * @param int $priority Priority options are constants with the "JOB_QUEUE_PRIORITY_" prefix
     */
    function setJobPriority($priority) {}
    
    // All properties SET functions
    function setJobName($name) {}
    function setScript($script) {}
    function setApplicationID($app_id) {}
    function setUserVariables($vars) {}
    function setGlobalVariables($vars) {}
    function setJobDependency($job_id) {}
    function setScheduledTime($timestamp) {}
    function setRecurrenceData($interval, $end_time=null) {}
    function setPreserved($preserved)
    
    /**
     * Get the job properties
     *
     * @return array The same format of job options array as in the Job constructor
     */
    function getProperties() {}

    /**
     * Get the job output
     *
     * @return An HTML representing the job output
     */
    function getOutput() {}
    
    // All properties GET functions
    function getID() {}
    function getHost() {}
    function getScript() {}
    function getJobPriority() {}
    function getJobName() {}
    function getApplicationID() {}
    function getUserVariables() {}
    function getGlobalVariables() {}
    function getJobDependency() {}
    function getScheduledTime() {}
    function getInterval() {}
    function getEndTime() {}
    function getPreserved() {}

    /**
     * Get the current status of the job
     * If this job was created and not returned from a queue (using the JobQueue::GetJob() function), 
     *  the function will return false
     * The status is one of the constants with the "JOB_QUEUE_STATUS_" prefix. 
     * E.g. job was performed and failed, job is waiting etc.
     *
     * @return int
     */
    function getJobStatus() {}

    /**
     * Get how much seconds there are until the next time the job will run. 
     * If the job is not recurrence or it past its end time, then return 0.
     *
     * @return int
     */
     function getTimeToNextRepeat() {}

    /**
     * For recurring job get the status of the last execution. For simple job,
     * getLastPerformedStatus is equivalent to getJobStatus.
     * jobs that haven't been executed yet will return STATUS_WAITING
     * @return int
     */
     function getLastPerformedStatus() {}

}


/**
 * Disable/enable the Code Acceleration functionality at run time.
 * @param status bool If false, Acceleration is disabled, if true - enabled
 * @return void
 */ 
function accelerator_set_status($status) {}

/**
 * Disables output caching for currently running scripts.
 * @return void
 */
function output_cache_disable() {}

/**
 * Does not allow the cache to perform compression on the output of the current page.
 * This output will not be compressed, even if the global set tings would normally allow
 * compression on files of this type.
 * @return void
 */
function output_cache_disable_compression() {}

/**
 * Gets the code’s return value from the cache if it is there, if not - run function and cache the value.
 * @param key string cache key
 * @param function string PHP expression
 * @param lifetime int data lifetime in cache (seconds)
 * @return string function's return
 */
function output_cache_fetch($key, $function, $lifetime) {}

/**
 * If they cache for the key exists, output it, otherwise capture expression output, cache and pass it out.
 * @param key string cache key
 * @param function string PHP expression
 * @param lifetime int data lifetime in cache (seconds)
 * @return expression output
 */
function output_cache_output($key, $function, $lifetime) {}

/**
 * Removes all the cache data for the given filename.
 * @param filename string full script path on local filesystem
 * @return bool true if OK, false if something went wrong
 */
function output_cache_remove($filename) {}

/**
 * Remove cache data for the script with given URL (all dependent data is removed)
 * @param url string the local url for the script
 * @return bool true if OK
 */
function output_cache_remove_url($url) {}

/**
 * Remove item from PHP API cache by key
 * @param key string cache key as given to output_cache_get/output_cache_put
 * @return bool true if OK
 */
function output_cache_remove_key($key) {}

/**
 * Puts data in cache according to the assigned key.
 * @param key string cache key
 * @param data mixed cached data (must not contain objects or resources)
 * @return bool true if OK
 */
function output_cache_put($key, $data) {}

/**
 * Gets cached data according to the assigned key.
 * @param key string cache key
 * @param lifetime int cache validity time (seconds)
 * @return mixed cached data if cache exists, false otherwise
 */
function output_cache_get($key, $lifetime) {}

/**
 * If data for assigned key exists, this function outputs it and returns a value of true.
 * If not, it starts capturing the output. To be used in pair with output_cache_stop.
 * @param key string cache key
 * @param lifetime int cache validity time (seconds)
 * @return bool true if cached data exists
 */
function output_cache_exists($key, $lifetime) {}

/**
 * If output was captured by output_cache_exists, this function stops the output capture and stores
 * the data under the key that was given to output_cache_exists().
 * @return void
 */
function output_cache_stop() {}


/**
 * Should be called from a custom error handler to pass the error to the monitor.
 * The user function needs to accept two parameters: the error code, and a string describing the error.
 * Then there are two optional parameters that may be supplied: the filename in which the error occurred
 * and the line number  in which the error occurred.
 * @param errno int
 * @param errstr string
 * @param errfile string
 * @param errline integer
 * @return void
 */
function monitor_pass_error($errno, $errstr, $errfile, $errline) {}

/**
 * Limited in the database to 255 chars
 * @param hint string
 * @return void
 */
function monitor_set_aggregation_hint($hint) {}

/**
 * Creates a custom event with class $class, text $text and possibly severity and other user data
 * @param class string
 * @param text string
 * @param severe int[optional]
 * @param user_data mixed[optional]
 * @return void
 */
function monitor_custom_event($class, $text, $severe = null, $user_data = null) {}

/**
 * Create an HTTPERROR event
 * @param error_code int the http error code to be associated with this event
 * @param url string the URL to be associated with this event
 * @param severe int[optional] the severety of the event: 0 - not severe, 1 - severe
 * @return void
 */
function monitor_httperror_event($error_code, $url, $severe = null) {}

/**
 * Returns an array containing information about
 * <li>module loading status (and cause of error if module failed to load)
 * <li>module license status (and cause of error if license not valid)
 * @return array 
 */
function monitor_license_info() {}

/**
 * Allow you to register a user function as an event handler.When a monitor event is triggerd
 * all the user event handler are called and the return value from the handler is saved in
 * an array keyed by the name the event handler was registered under. The event handlers
 * results array is saved in the event_extra_data table.
 * @param event_handler_func string The callback function that will be call when the event is triggered, object methods may also be invoked statically using t
his function by passing array($objectname, $methodname) to the function parameter
 * @param handler_register_name string[optional] The name this function is registered under - if none is supplied, the function will be registerd under it's own name
 * @param event_type_mask int The mask of event types that the handler should be called on by default it's set to MONITOR_EVENT_ALL.
 * @return bool TRUE on sucess and FALSE if an error occurs.
 */
function register_event_handler($event_handler_func, $handler_register_name, $event_type_mask) {}

/**
 * Allow you to unregister an event handler.
 * @param handler_name string the name you registered with the handler you now wish to unregister.
 * @return bool TRUE on sucess and FALSE if no handler we registered under the given name.
 */
function unregister_event_handler($handler_name) {}

/**
 * Send a file using ZDS
 * @param filename string path to the file
 * @param mime_type string[optional] MIME type of the file, if omitted, taken from configured MIME types file
 * @param custom_headers string[optional] user defined headers that will be send instead of regular ZDS headers. few basic essential headers will be send anyway
 * @return bool FALSE if sending file failed, does not return otherwise
 */
function zend_send_file($filename, $mime_type, $custom_headers) {}

/**
 * Send a buffer using ZDS
 * @param buffer string the content that will be send
 * @param mime_type string[optional] MIME type of the buffer, if omitted, taken from configured MIME types file
 * @param custom_headers string[optional] user defined headers that will be send instead of regular ZDS headers. few basic essential headers will be send anyway
 * @return bool FALSE if sending file failed, does not return otherwise
 */
function zend_send_buffer($buffer, $mime_type, $custom_headers) {}


class java {
    /**
     * Create Java object
     *
     * @return java
     * @param  classname string
     * @vararg ...
     */
    function java($classname) {}

};

class JavaException {
    /**
     * Get Java exception that led to this exception
     *
     * @return object
     */
    function getCause() {}

};


/**
 * Create Java object
 *
 * @return object
 * @param  class string
 * @vararg ...
 */
function java($class) {}


/**
 * Return Java exception object for last exception
 * @return object Java Exception object, if there was an exception, false otherwise
 */
function java_last_exception_get() {}

/**
 * Clear last Java exception object record.
 * @return void
 */
function java_last_exception_clear() {}

/**
 * Set case sensitivity for Java calls.
 * @param ignore bool if set, Java attribute and method names would be resolved disregarding case. NOTE: this does not make any Java functions case insensi
tive, just things like $foo->bar and $foo->bar() would match Bar too.
 * @return void
 */
function java_set_ignore_case($ignore) {}

/**
 * Set encoding for strings received by Java from PHP. Default is UTF-8.
 * @param encoding string
 * @return array
 */
function java_set_encoding($encoding) {}

/**
 * Control if exceptions are thrown on Java exception. Only for PHP5.
 * @param throw bool If true, PHP exception is thrown when Java exception happens. If set to false, use java_last_exception_get() to check for exception.
 * @return void
 */
function java_throw_exceptions($throw) {}

/**
 * Reload Jar's that were dynamically loaded
 *
 * @return array
 * @param  new_jarpath string
 */
function java_reload($new_jarpath) {}

/**
 * Add to Java's classpath in runtime
 *
 * @return array
 * @param  new_classpath string
 */
function java_require($new_classpath) {}


/**
 * Shown if loader is enabled
 * @return bool
 */
function zend_loader_enabled() {}

/**
 * Returns true if the current file is a Zend-encoded file.
 * @return bool
 */
function zend_loader_file_encoded() {}

/**
 * Returns license (array with fields) if the current file has a valid license and is encoded, otherwise it returns false.
 * @return array 
 */
function zend_loader_file_licensed() {}

/**
 * Returns the name of the file currently being executed.
 * @return string
 */
function zend_loader_current_file() {}

/**
 * Dynamically loads a license for applications encoded with Zend SafeGuard. The Override controls if it will override old licenses for the same product.
 * @param license_file string
 * @param override bool[optional]
 * @return bool
 */
function zend_loader_install_license($license_file, $override) {}

/**
 * Obfuscate and return the given function name with the internal obfuscation function.
 * @param function_name string
 * @return string
 */
function zend_obfuscate_function_name($function_name) {}

/**
 * Obfuscate and return the given class name with the internal obfuscation function.
 * @param class_name string
 * @return string
 */
function zend_obfuscate_class_name($class_name) {}

/**
 * Returns the current obfuscation level support (set by zend_optimizer.obfuscation_level_support)
 * @return int
 */
function zend_current_obfuscation_level() {}

/**
 * Start runtime-obfuscation support that allows limited mixing of obfuscated and un-obfuscated code.
 * @return void
 */
function zend_runtime_obfuscate() {}

/**
 * Returns array of the host ids. If all_ids is true, then all IDs are returned, otehrwise only IDs considered "primary" are returned.
 * @param all_ids bool[optional]
 * @return array
 */
function zend_get_id($all_ids = false) {}

/**
 * Returns Optimizer version. Alias: zend_loader_version()
 * @return string
 */
function zend_optimizer_version() {}


// End of Zend Extensions

?>
