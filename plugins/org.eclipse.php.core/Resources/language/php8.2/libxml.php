<?php

// Start of libxml v.8.2.6

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
	public int $level;

	/**
	 * The error's code.
	 * @var int
	 * @link http://www.php.net/manual/en/class.libxmlerror.php#libxmlerror.props.code
	 */
	public int $code;

	/**
	 * The column where the error occurred.
	 * @var int
	 * @link http://www.php.net/manual/en/class.libxmlerror.php#libxmlerror.props.column
	 */
	public int $column;

	/**
	 * The error message, if any.
	 * @var string
	 * @link http://www.php.net/manual/en/class.libxmlerror.php#libxmlerror.props.message
	 */
	public string $message;

	/**
	 * The filename, or empty if the XML was loaded from a string.
	 * @var string
	 * @link http://www.php.net/manual/en/class.libxmlerror.php#libxmlerror.props.file
	 */
	public string $file;

	/**
	 * The line where the error occurred.
	 * @var int
	 * @link http://www.php.net/manual/en/class.libxmlerror.php#libxmlerror.props.line
	 */
	public int $line;
}

/**
 * Set the streams context for the next libxml document load or write
 * @link http://www.php.net/manual/en/function.libxml-set-streams-context.php
 * @param resource $context 
 * @return void No value is returned.
 */
function libxml_set_streams_context ($context): void {}

/**
 * Disable libxml errors and allow user to fetch error information as needed
 * @link http://www.php.net/manual/en/function.libxml-use-internal-errors.php
 * @param bool|null $use_errors [optional] 
 * @return bool This function returns the previous value of
 * use_errors.
 */
function libxml_use_internal_errors (?bool $use_errors = null): bool {}

/**
 * Retrieve last error from libxml
 * @link http://www.php.net/manual/en/function.libxml-get-last-error.php
 * @return LibXMLError|false Returns a LibXMLError object if there is any error in the
 * buffer, false otherwise.
 */
function libxml_get_last_error (): LibXMLError|false {}

/**
 * Retrieve array of errors
 * @link http://www.php.net/manual/en/function.libxml-get-errors.php
 * @return array Returns an array with LibXMLError objects if there are any
 * errors in the buffer, or an empty array otherwise.
 */
function libxml_get_errors (): array {}

/**
 * Clear libxml error buffer
 * @link http://www.php.net/manual/en/function.libxml-clear-errors.php
 * @return void No value is returned.
 */
function libxml_clear_errors (): void {}

/**
 * Disable the ability to load external entities
 * @link http://www.php.net/manual/en/function.libxml-disable-entity-loader.php
 * @param bool $disable [optional] 
 * @return bool Returns the previous value.
 * @deprecated 1
 */
function libxml_disable_entity_loader (bool $disable = true): bool {}

/**
 * Changes the default external entity loader
 * @link http://www.php.net/manual/en/function.libxml-set-external-entity-loader.php
 * @param callable|null $resolver_function A callable with the following signature:
 * resourcestringnullresolver
 * stringpublic_id
 * stringsystem_id
 * arraycontext
 * <p>
 * public_id
 * <br>
 * The public ID.
 * system_id
 * <br>
 * The system ID.
 * context
 * <br>
 * An array with the four elements "directory", "intSubName",
 * "extSubURI" and "extSubSystem".
 * </p>
 * This callable should return a resource, a string from which a resource can be
 * opened. If null is returned, the entity reference resolution will fail.
 * @return bool Returns true on success or false on failure.
 */
function libxml_set_external_entity_loader (?callable $resolver_function): bool {}

/**
 * Get the current external entity loader
 * @link http://www.php.net/manual/en/function.libxml-get-external-entity-loader.php
 * @return callable|null The external entity loader previously installed by
 * libxml_set_external_entity_loader. If that function was
 * never called, or if it was called with null, null will be returned.
 */
function libxml_get_external_entity_loader (): ?callable {}


/**
 * libxml version like 20605 or 20617
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_VERSION', 20913);

/**
 * libxml version like 2.6.5 or 2.6.17
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var string
 */
define ('LIBXML_DOTTED_VERSION', "2.9.13");
define ('LIBXML_LOADED_VERSION', 20913);

/**
 * Substitute entities
 * Enabling entity substitution may facilitate XML External Entity (XXE) attacks.
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_NOENT', 2);

/**
 * Load the external subset
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_DTDLOAD', 4);

/**
 * Default DTD attributes
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_DTDATTR', 8);

/**
 * Validate with the DTD
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_DTDVALID', 16);

/**
 * Suppress error reports
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_NOERROR', 32);

/**
 * Suppress warning reports
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_NOWARNING', 64);

/**
 * Remove blank nodes
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_NOBLANKS', 256);

/**
 * Implement XInclude substitution
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_XINCLUDE', 1024);

/**
 * Remove redundant namespace declarations
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_NSCLEAN', 8192);

/**
 * Merge CDATA as text nodes
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_NOCDATA', 16384);

/**
 * Disable network access when loading documents
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_NONET', 2048);

/**
 * Available as of PHP &gt;= 5.4.0
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_PEDANTIC', 128);

/**
 * Only available in Libxml &gt;= 2.6.21
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_COMPACT', 65536);

/**
 * Only available in Libxml &gt;= 2.6.21
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_NOXMLDECL', 2);

/**
 * Only available in Libxml &gt;= 2.7.0 (as of PHP &gt;= 5.3.2 and PHP &gt;= 5.2.12)
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_PARSEHUGE', 524288);

/**
 * Only available as of PHP 7.0.0 with Libxml &gt;= 2.9.0
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_BIGLINES', 4194304);

/**
 * This option is currently just available in the
 * and
 * functions.
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_NOEMPTYTAG', 4);

/**
 * Only available in Libxml &gt;= 2.6.14 (as of PHP &gt;= 5.5.2)
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_SCHEMA_CREATE', 1);

/**
 * Only available in Libxml &gt;= 2.7.7 (as of PHP &gt;= 5.4.0)
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_HTML_NOIMPLIED', 8192);

/**
 * Only available in Libxml &gt;= 2.7.8 (as of PHP &gt;= 5.4.0)
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_HTML_NODEFDTD', 4);

/**
 * No errors
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_ERR_NONE', 0);

/**
 * A simple warning
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_ERR_WARNING', 1);

/**
 * A recoverable error
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_ERR_ERROR', 2);

/**
 * A fatal error
 * @link http://www.php.net/manual/en/libxml.constants.php
 * @var int
 */
define ('LIBXML_ERR_FATAL', 3);

// End of libxml v.8.2.6
