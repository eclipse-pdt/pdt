<?php

// Start of libxml v.

class LibXMLError  {
}

/**
 * Set the streams context for the next libxml document load or write
 * @link http://www.php.net/manual/en/function.libxml-set-streams-context.php
 * @param streams_context resource <p>
 * The stream context resource (created with
 * stream_context_create)
 * </p>
 * @return void 
 */
function libxml_set_streams_context ($streams_context) {}

/**
 * Disable libxml errors and allow user to fetch error information as needed
 * @link http://www.php.net/manual/en/function.libxml-use-internal-errors.php
 * @param use_errors bool[optional] <p>
 * Whether to enable user error handling.
 * </p>
 * @return bool This function returns the previous value of
 * use_errors.
 */
function libxml_use_internal_errors ($use_errors = null) {}

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

define ('LIBXML_VERSION', 20703);
define ('LIBXML_DOTTED_VERSION', "2.7.3");
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
?>
