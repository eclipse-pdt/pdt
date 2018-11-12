<?php

// Start of fileinfo v.7.3.0

/**
 * This class provides an object oriented interface into the fileinfo
 * functions.
 * @link http://www.php.net/manual/en/class.finfo.php
 */
class finfo  {

	/**
	 * @param mixed $options [optional]
	 * @param mixed $arg [optional]
	 */
	public function finfo ($options = null, $arg = null) {}

	/**
	 * Alias: finfo_set_flags()
	 * @link http://www.php.net/manual/en/finfo.set-flags.php
	 * @param int $options 
	 * @return bool 
	 */
	public function set_flags (int $options) {}

	/**
	 * Alias: finfo_file()
	 * @link http://www.php.net/manual/en/finfo.file.php
	 * @param string $file_name 
	 * @param int $options [optional] 
	 * @param resource $context [optional] 
	 * @return string 
	 */
	public function file (string $file_name, int $options = null, $context = null) {}

	/**
	 * Alias: finfo_buffer()
	 * @link http://www.php.net/manual/en/finfo.buffer.php
	 * @param string $string 
	 * @param int $options [optional] 
	 * @param resource $context [optional] 
	 * @return string 
	 */
	public function buffer (string $string, int $options = null, $context = null) {}

}

/**
 * Create a new fileinfo resource
 * @link http://www.php.net/manual/en/function.finfo-open.php
 * @param int $options [optional] One or disjunction of more Fileinfo
 * constants.
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
function finfo_open (int $options = null, string $magic_file = null) {}

/**
 * Close fileinfo resource
 * @link http://www.php.net/manual/en/function.finfo-close.php
 * @param resource $finfo Fileinfo resource returned by finfo_open.
 * @return bool true on success or false on failure
 */
function finfo_close ($finfo) {}

/**
 * Set libmagic configuration options
 * @link http://www.php.net/manual/en/function.finfo-set-flags.php
 * @param resource $finfo Fileinfo resource returned by finfo_open.
 * @param int $options One or disjunction of more Fileinfo
 * constants.
 * @return bool true on success or false on failure
 */
function finfo_set_flags ($finfo, int $options) {}

/**
 * Return information about a file
 * @link http://www.php.net/manual/en/function.finfo-file.php
 * @param resource $finfo Fileinfo resource returned by finfo_open.
 * @param string $file_name Name of a file to be checked.
 * @param int $options [optional] One or disjunction of more Fileinfo
 * constants.
 * @param resource $context [optional] For a description of contexts, refer to .
 * @return string a textual description of the contents of the
 * file_name argument, or false if an error occurred.
 */
function finfo_file ($finfo, string $file_name, int $options = null, $context = null) {}

/**
 * Return information about a string buffer
 * @link http://www.php.net/manual/en/function.finfo-buffer.php
 * @param resource $finfo Fileinfo resource returned by finfo_open.
 * @param string $string Content of a file to be checked.
 * @param int $options [optional] One or disjunction of more Fileinfo
 * constants.
 * @param resource $context [optional] 
 * @return string a textual description of the string
 * argument, or false if an error occurred.
 */
function finfo_buffer ($finfo, string $string, int $options = null, $context = null) {}

/**
 * Detect MIME Content-type for a file
 * @link http://www.php.net/manual/en/function.mime-content-type.php
 * @param string $filename Path to the tested file.
 * @return string the content type in MIME format, like 
 * text/plain or application/octet-stream,
 * or false on failure.
 */
function mime_content_type (string $filename) {}


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

/**
 * Returns the file extension appropiate for a the MIME type detected in 
 * the file.
 * For types that commonly have multiple file extensions, such as JPEG 
 * images, then the return value is multiple extensions speparated by a forward slash e.g.: 
 * "jpeg/jpg/jpe/jfif". For unknown types not available in the 
 * magic.mime database, then return value is "???".
 * Available since PHP 7.2.0.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 */
define ('FILEINFO_EXTENSION', 16777216);

// End of fileinfo v.7.3.0
