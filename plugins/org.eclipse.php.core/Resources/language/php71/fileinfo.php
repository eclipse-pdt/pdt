<?php

// Start of fileinfo v.1.0.5

class finfo  {

	/**
	 * @param $options [optional]
	 * @param $arg [optional]
	 */
	public function finfo ($options = null, $arg = null) {}

	/**
	 * Alias: finfo_set_flags()
	 * @link http://www.php.net/manual/en/finfo.set-flags.php
	 * @param int $options 
	 * @return bool 
	 */
	public function set_flags ($options) {}

	/**
	 * Alias: finfo_file()
	 * @link http://www.php.net/manual/en/finfo.file.php
	 * @param string $file_name 
	 * @param int $options [optional] 
	 * @param resource $context [optional] 
	 * @return string 
	 */
	public function file ($file_name, $options = null, $context = null) {}

	/**
	 * Alias: finfo_buffer()
	 * @link http://www.php.net/manual/en/finfo.buffer.php
	 * @param string $string 
	 * @param int $options [optional] 
	 * @param resource $context [optional] 
	 * @return string 
	 */
	public function buffer ($string, $options = null, $context = null) {}

}

/**
 * Create a new fileinfo resource
 * @link http://www.php.net/manual/en/function.finfo-open.php
 * @param int $options [optional] <p>
 * One or disjunction of more Fileinfo
 * constants.
 * </p>
 * @param string $magic_file [optional] <p>
 * Name of a magic database file, usually something like
 * /path/to/magic.mime. If not specified, the
 * MAGIC environment variable is used. If the
 * environment variable isn't set, then PHP's bundled magic database will
 * be used.
 * </p>
 * <p>
 * Passing null or an empty string will be equivalent to the default
 * value.
 * </p>
 * @return resource (Procedural style only)
 * Returns a magic database resource on success or false on failure.
 */
function finfo_open ($options = null, $magic_file = null) {}

/**
 * Close fileinfo resource
 * @link http://www.php.net/manual/en/function.finfo-close.php
 * @param resource $finfo <p>
 * Fileinfo resource returned by finfo_open.
 * </p>
 * @return bool true on success or false on failure
 */
function finfo_close ($finfo) {}

/**
 * Set libmagic configuration options
 * @link http://www.php.net/manual/en/function.finfo-set-flags.php
 * @param resource $finfo <p>
 * Fileinfo resource returned by finfo_open.
 * </p>
 * @param int $options <p>
 * One or disjunction of more Fileinfo
 * constants.
 * </p>
 * @return bool true on success or false on failure
 */
function finfo_set_flags ($finfo, $options) {}

/**
 * Return information about a file
 * @link http://www.php.net/manual/en/function.finfo-file.php
 * @param resource $finfo <p>
 * Fileinfo resource returned by finfo_open.
 * </p>
 * @param string $file_name <p>
 * Name of a file to be checked.
 * </p>
 * @param int $options [optional] <p>
 * One or disjunction of more Fileinfo
 * constants.
 * </p>
 * @param resource $context [optional] <p>
 * For a description of contexts, refer to .
 * </p>
 * @return string a textual description of the contents of the
 * file_name argument, or false if an error occurred.
 */
function finfo_file ($finfo, $file_name, $options = null, $context = null) {}

/**
 * Return information about a string buffer
 * @link http://www.php.net/manual/en/function.finfo-buffer.php
 * @param resource $finfo <p>
 * Fileinfo resource returned by finfo_open.
 * </p>
 * @param string $string <p>
 * Content of a file to be checked.
 * </p>
 * @param int $options [optional] <p>
 * One or disjunction of more Fileinfo
 * constants.
 * </p>
 * @param resource $context [optional] <p>
 * </p>
 * @return string a textual description of the string
 * argument, or false if an error occurred.
 */
function finfo_buffer ($finfo, $string, $options = null, $context = null) {}

/**
 * Detect MIME Content-type for a file
 * @link http://www.php.net/manual/en/function.mime-content-type.php
 * @param string $filename <p>
 * Path to the tested file.
 * </p>
 * @return string the content type in MIME format, like 
 * text/plain or application/octet-stream.
 */
function mime_content_type ($filename) {}


/**
 * No special handling.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 */
define ('FILEINFO_NONE', 0);

/**
 * Follow symlinks.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 */
define ('FILEINFO_SYMLINK', 2);

/**
 * Return the mime type and mime encoding as defined by RFC 2045.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 */
define ('FILEINFO_MIME', 1040);

/**
 * Return the mime type.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 */
define ('FILEINFO_MIME_TYPE', 16);

/**
 * Return the mime encoding of the file.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 */
define ('FILEINFO_MIME_ENCODING', 1024);

/**
 * Look at the contents of blocks or character special devices.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 */
define ('FILEINFO_DEVICES', 8);

/**
 * Return all matches, not just the first.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 */
define ('FILEINFO_CONTINUE', 32);

/**
 * If possible preserve the original access time.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 */
define ('FILEINFO_PRESERVE_ATIME', 128);

/**
 * Don't translate unprintable characters to a \ooo octal
 * representation.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 */
define ('FILEINFO_RAW', 256);

// End of fileinfo v.1.0.5
