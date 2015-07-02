<?php

// Start of fileinfo v.1.0.5

class finfo  {

	/**
	 * @param options[optional]
	 * @param arg[optional]
	 */
	public function finfo ($options = null, $arg = null) {}

	/**
	 * @param options
	 */
	public function set_flags ($options) {}

	/**
	 * @param filename
	 * @param options[optional]
	 * @param context[optional]
	 */
	public function file ($filename, $options = null, $context = null) {}

	/**
	 * @param string
	 * @param options[optional]
	 * @param context[optional]
	 */
	public function buffer ($string, $options = null, $context = null) {}

}

/**
 * Create a new fileinfo resource
 * @link http://www.php.net/manual/en/function.finfo-open.php
 * @param options[optional]
 * @param arg[optional]
 */
function finfo_open ($options = null, $arg = null) {}

/**
 * Close fileinfo resource
 * @link http://www.php.net/manual/en/function.finfo-close.php
 * @param finfo
 */
function finfo_close ($finfo) {}

/**
 * Set libmagic configuration options
 * @link http://www.php.net/manual/en/function.finfo-set-flags.php
 * @param finfo
 * @param options
 */
function finfo_set_flags ($finfo, $options) {}

/**
 * Return information about a file
 * @link http://www.php.net/manual/en/function.finfo-file.php
 * @param finfo
 * @param filename
 * @param options[optional]
 * @param context[optional]
 */
function finfo_file ($finfo, $filename, $options = null, $context = null) {}

/**
 * Return information about a string buffer
 * @link http://www.php.net/manual/en/function.finfo-buffer.php
 * @param finfo
 * @param string
 * @param options[optional]
 * @param context[optional]
 */
function finfo_buffer ($finfo, $string, $options = null, $context = null) {}

/**
 * Detect MIME Content-type for a file (deprecated)
 * @link http://www.php.net/manual/en/function.mime-content-type.php
 * @param filename string <p>
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
