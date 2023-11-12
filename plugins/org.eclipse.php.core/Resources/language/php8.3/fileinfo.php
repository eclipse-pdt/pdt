<?php

// Start of fileinfo v.8.2.6

/**
 * This class provides an object-oriented interface into the fileinfo
 * functions.
 * @link http://www.php.net/manual/en/class.finfo.php
 */
class finfo  {

	/**
	 * Alias of finfo_open
	 * @link http://www.php.net/manual/en/finfo.construct.php
	 * @param int $flags [optional] 
	 * @param string|null $magic_database [optional] 
	 * @return int 
	 */
	public function __construct (int $flags = FILEINFO_NONE, ?string $magic_database = null): int {}

	/**
	 * Alias of finfo_file()
	 * @link http://www.php.net/manual/en/finfo.file.php
	 * @param string $filename 
	 * @param int $flags [optional] 
	 * @param resource|null $context [optional] 
	 * @return string|false 
	 */
	public function file (string $filename, int $flags = FILEINFO_NONE, $context = null): string|false {}

	/**
	 * Alias of finfo_buffer()
	 * @link http://www.php.net/manual/en/finfo.buffer.php
	 * @param string $string 
	 * @param int $flags [optional] 
	 * @param resource|null $context [optional] 
	 * @return string|false 
	 */
	public function buffer (string $string, int $flags = FILEINFO_NONE, $context = null): string|false {}

	/**
	 * Alias of finfo_set_flags()
	 * @link http://www.php.net/manual/en/finfo.set-flags.php
	 * @param int $flags 
	 * @return bool 
	 */
	public function set_flags (int $flags): bool {}

}

/**
 * Create a new finfo instance
 * @link http://www.php.net/manual/en/function.finfo-open.php
 * @param int $flags [optional] 
 * @param string|null $magic_database [optional] 
 * @return finfo|false (Procedural style only)
 * Returns an finfo instance on success, or false on failure.
 */
function finfo_open (int $flags = FILEINFO_NONE, ?string $magic_database = null): finfo|false {}

/**
 * Close finfo instance
 * @link http://www.php.net/manual/en/function.finfo-close.php
 * @param finfo $finfo 
 * @return bool Returns true on success or false on failure.
 */
function finfo_close (finfo $finfo): bool {}

/**
 * Set libmagic configuration options
 * @link http://www.php.net/manual/en/function.finfo-set-flags.php
 * @param finfo $finfo 
 * @param int $flags 
 * @return bool Returns true on success or false on failure.
 */
function finfo_set_flags (finfo $finfo, int $flags): bool {}

/**
 * Return information about a file
 * @link http://www.php.net/manual/en/function.finfo-file.php
 * @param finfo $finfo 
 * @param string $filename 
 * @param int $flags [optional] 
 * @param resource|null $context [optional] 
 * @return string|false Returns a textual description of the contents of the
 * filename argument, or false if an error occurred.
 */
function finfo_file (finfo $finfo, string $filename, int $flags = FILEINFO_NONE, $context = null): string|false {}

/**
 * Return information about a string buffer
 * @link http://www.php.net/manual/en/function.finfo-buffer.php
 * @param finfo $finfo 
 * @param string $string 
 * @param int $flags [optional] 
 * @param resource|null $context [optional] 
 * @return string|false Returns a textual description of the string
 * argument, or false if an error occurred.
 */
function finfo_buffer (finfo $finfo, string $string, int $flags = FILEINFO_NONE, $context = null): string|false {}

/**
 * Detect MIME Content-type for a file
 * @link http://www.php.net/manual/en/function.mime-content-type.php
 * @param resource|string $filename 
 * @return string|false Returns the content type in MIME format, like 
 * text/plain or application/octet-stream,
 * or false on failure.
 */
function mime_content_type ($filename): string|false {}


/**
 * No special handling.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 * @var int
 */
define ('FILEINFO_NONE', 0);

/**
 * Follow symlinks.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 * @var int
 */
define ('FILEINFO_SYMLINK', 2);

/**
 * Return the mime type and mime encoding as defined by RFC 2045.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 * @var int
 */
define ('FILEINFO_MIME', 1040);

/**
 * Return the mime type.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 * @var int
 */
define ('FILEINFO_MIME_TYPE', 16);

/**
 * Return the mime encoding of the file.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 * @var int
 */
define ('FILEINFO_MIME_ENCODING', 1024);

/**
 * Look at the contents of blocks or character special devices.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 * @var int
 */
define ('FILEINFO_DEVICES', 8);

/**
 * Return all matches, not just the first.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 * @var int
 */
define ('FILEINFO_CONTINUE', 32);

/**
 * If possible preserve the original access time.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 * @var int
 */
define ('FILEINFO_PRESERVE_ATIME', 128);

/**
 * Don't translate unprintable characters to a \ooo octal
 * representation.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 * @var int
 */
define ('FILEINFO_RAW', 256);
define ('FILEINFO_APPLE', 2048);

/**
 * Returns the file extension appropriate for the MIME type detected in 
 * the file.
 * For types that commonly have multiple file extensions, such as JPEG 
 * images, then the return value is multiple extensions separated by a forward slash e.g.: 
 * "jpeg/jpg/jpe/jfif". For unknown types not available in the 
 * magic.mime database, then return value is "???".
 * Available since PHP 7.2.0.
 * @link http://www.php.net/manual/en/fileinfo.constants.php
 * @var int
 */
define ('FILEINFO_EXTENSION', 16777216);

// End of fileinfo v.8.2.6
