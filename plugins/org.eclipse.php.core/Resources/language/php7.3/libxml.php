<?php

// Start of libxml v.7.3.0

/**
 * Contains various information about errors thrown by libxml. The error codes
 * are described within the official 
 * xmlError API documentation.
 * @link http://www.php.net/manual/en/class.libxmlerror.php
 */
class LibXMLError  {

	/**
	 * the severity of the error (one of the following constants:
	 * LIBXML_ERR_WARNING,
	 * LIBXML_ERR_ERROR or
	 * LIBXML_ERR_FATAL)
	 * @var int
	 * @link http://www.php.net/manual/en/class.libxmlerror.php#libxmlerror.props.level
	 */
	public $level;

	/**
	 * The error's code.
	 * @var int
	 * @link http://www.php.net/manual/en/class.libxmlerror.php#libxmlerror.props.code
	 */
	public $code;

	/**
	 * The column where the error occurred.
	 * @var int
	 * @link http://www.php.net/manual/en/class.libxmlerror.php#libxmlerror.props.column
	 */
	public $column;

	/**
	 * The error message, if any.
	 * @var string
	 * @link http://www.php.net/manual/en/class.libxmlerror.php#libxmlerror.props.message
	 */
	public $message;

	/**
	 * The filename, or empty if the XML was loaded from a string.
	 * @var string
	 * @link http://www.php.net/manual/en/class.libxmlerror.php#libxmlerror.props.file
	 */
	public $file;

	/**
	 * The line where the error occurred.
	 * @var int
	 * @link http://www.php.net/manual/en/class.libxmlerror.php#libxmlerror.props.line
	 */
	public $line;
}

/**
 * Set the streams context for the next libxml document load or write
 * @link http://www.php.net/manual/en/function.libxml-set-streams-context.php
 * @param resource $streams_context The stream context resource (created with
 * stream_context_create)
 * @return void 
 */
function libxml_set_streams_context ($streams_context) {}

/**
 * Disable libxml errors and allow user to fetch error information as needed
 * @link http://www.php.net/manual/en/function.libxml-use-internal-errors.php
 * @param bool $use_errors [optional] Enable (true) user error handling or disable (false) user error handling. Disabling will also clear any existing libxml errors.
 * @return bool This function returns the previous value of
 * use_errors.
 */
function libxml_use_internal_errors (bool $use_errors = null) {}

/**
 * Retrieve last error from libxml
 * @link http://www.php.net/manual/en/function.libxml-get-last-error.php
 * @return LibXMLError a LibXMLError object if there is any error in the
 * buffer, false otherwise.
 */
function libxml_get_last_error () {}

/**
 * Clear libxml error buffer
 * @link http://www.php.net/manual/en/function.libxml-clear-errors.php
 * @return void 
 */
function libxml_clear_errors () {}

/**
 * Retrieve array of errors
 * @link http://www.php.net/manual/en/function.libxml-get-errors.php
 * @return array an array with LibXMLError objects if there are any
 * errors in the buffer, or an empty array otherwise.
 */
function libxml_get_errors () {}

/**
 * Disable the ability to load external entities
 * @link http://www.php.net/manual/en/function.libxml-disable-entity-loader.php
 * @param bool $disable [optional] Disable (true) or enable (false) libxml extensions (such as
 * , 
 * and ) to load external entities.
 * @return bool the previous value.
 */
function libxml_disable_entity_loader (bool $disable = null) {}

/**
 * Changes the default external entity loader
 * @link http://www.php.net/manual/en/function.libxml-set-external-entity-loader.php
 * @param callable $resolver_function A callable that takes three arguments. Two strings, a public id
 * and system id, and a context (an array with four keys) as the third argument.
 * This callback should return a resource, a string from which a resource can be
 * opened, or null.
 * @return bool true on success or false on failure
 */
function libxml_set_external_entity_loader (callable $resolver_function) {}


/**
 * libxml version like 20605 or 20617
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_VERSION', 20908);

/**
 * libxml version like 2.6.5 or 2.6.17
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_DOTTED_VERSION', "2.9.8");
define ('LIBXML_LOADED_VERSION', 20908);

/**
 * Substitute entities
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_NOENT', 2);

/**
 * Load the external subset
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_DTDLOAD', 4);

/**
 * Default DTD attributes
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_DTDATTR', 8);

/**
 * Validate with the DTD
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_DTDVALID', 16);

/**
 * Suppress error reports
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_NOERROR', 32);

/**
 * Suppress warning reports
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_NOWARNING', 64);

/**
 * Remove blank nodes
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_NOBLANKS', 256);

/**
 * Implement XInclude substitution
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_XINCLUDE', 1024);

/**
 * Remove redundant namespace declarations
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_NSCLEAN', 8192);

/**
 * Merge CDATA as text nodes
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_NOCDATA', 16384);

/**
 * Disable network access when loading documents
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_NONET', 2048);

/**
 * Sets XML_PARSE_PEDANTIC flag, which enables pedantic error reporting.
 * <p>
 * Available as of PHP &gt;= 5.4.0
 * </p>
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_PEDANTIC', 128);

/**
 * Activate small nodes allocation optimization. This may speed up your
 * application without needing to change the code.
 * <p>
 * Only available in Libxml &gt;= 2.6.21
 * </p>
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_COMPACT', 65536);

/**
 * Drop the XML declaration when saving a document
 * <p>
 * Only available in Libxml &gt;= 2.6.21
 * </p>
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_NOXMLDECL', 2);

/**
 * Sets XML_PARSE_HUGE flag, which relaxes any hardcoded limit from the parser. This affects 
 * limits like maximum depth of a document or the entity recursion, as well as limits of the 
 * size of text nodes.
 * <p>
 * Only available in Libxml &gt;= 2.7.0 (as of PHP &gt;= 5.3.2 and PHP &gt;= 5.2.12)
 * </p>
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_PARSEHUGE', 524288);

/**
 * Allows line numbers greater than 65535 to be reported correctly.
 * <p>
 * Only available as of PHP 7.0.0 with Libxml &gt;= 2.9.0
 * </p>
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_BIGLINES', 4194304);

/**
 * Expand empty tags (e.g. &lt;br/&gt; to
 * &lt;br&gt;&lt;/br&gt;)
 * <p>
 * This option is currently just available in the
 * and
 * functions.
 * </p>
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_NOEMPTYTAG', 4);

/**
 * Create default/fixed value nodes during XSD schema validation
 * <p>
 * Only available in Libxml &gt;= 2.6.14 (as of PHP &gt;= 5.5.2)
 * </p>
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_SCHEMA_CREATE', 1);

/**
 * Sets HTML_PARSE_NOIMPLIED flag, which turns off the
 * automatic adding of implied html/body... elements.
 * <p>
 * Only available in Libxml &gt;= 2.7.7 (as of PHP &gt;= 5.4.0)
 * </p>
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_HTML_NOIMPLIED', 8192);

/**
 * Sets HTML_PARSE_NODEFDTD flag, which prevents a default doctype
 * being added when one is not found.
 * <p>
 * Only available in Libxml &gt;= 2.7.8 (as of PHP &gt;= 5.4.0)
 * </p>
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_HTML_NODEFDTD', 4);

/**
 * No errors
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_ERR_NONE', 0);

/**
 * A simple warning
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_ERR_WARNING', 1);

/**
 * A recoverable error
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_ERR_ERROR', 2);

/**
 * A fatal error
 * @link http://www.php.net/manual/en/libxml.constants.php
 */
define ('LIBXML_ERR_FATAL', 3);

// End of libxml v.7.3.0
