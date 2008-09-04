<?php

// Start of fileinfo v.0.1

class finfo  {

	public function finfo () {}

	public function set_flags () {}

	public function file () {}

	public function buffer () {}

}

/**
 * Create a new fileinfo resource
 * @link http://php.net/manual/en/function.finfo-open.php
 * @param options int[optional]
 * @param arg string[optional]
 * @return resource a magic database resource on success or false on failure.
 */
function finfo_open ($options = null, $arg = null) {}

/**
 * Close fileinfo resource
 * @link http://php.net/manual/en/function.finfo-close.php
 * @param finfo resource
 * @return bool 
 */
function finfo_close ($finfo) {}

/**
 * Set libmagic configuration options
 * @link http://php.net/manual/en/function.finfo-set-flags.php
 * @param options int
 * @return bool 
 */
function finfo_set_flags ($options) {}

/**
 * Return information about a file
 * @link http://php.net/manual/en/function.finfo-file.php
 * @param file_name string
 * @param options int[optional]
 * @param context resource[optional]
 * @return string a textual description of the contents of the
 */
function finfo_file ($file_name, $options = null, $context = null) {}

/**
 * Return information about a string buffer
 * @link http://php.net/manual/en/function.finfo-buffer.php
 * @param string string
 * @param options int[optional]
 * @param context resource[optional]
 * @return string a textual description of the string
 */
function finfo_buffer ($string, $options = null, $context = null) {}


/**
 * No special handling.
 * @link http://php.net/manual/en/fileinfo.constants.php
 */
define ('FILEINFO_NONE', 0);

/**
 * Follow symlinks.
 * @link http://php.net/manual/en/fileinfo.constants.php
 */
define ('FILEINFO_SYMLINK', 2);

/**
 * Return a mime string, instead of a textual description.
 * @link http://php.net/manual/en/fileinfo.constants.php
 */
define ('FILEINFO_MIME', 1040);

/**
 * Decompress compressed files.
 * @link http://php.net/manual/en/fileinfo.constants.php
 */
define ('FILEINFO_COMPRESS', 4);

/**
 * Look at the contents of blocks or character special devices.
 * @link http://php.net/manual/en/fileinfo.constants.php
 */
define ('FILEINFO_DEVICES', 8);

/**
 * Return all matches, not just the first.
 * @link http://php.net/manual/en/fileinfo.constants.php
 */
define ('FILEINFO_CONTINUE', 32);

/**
 * If possible preserve the original access time.
 * @link http://php.net/manual/en/fileinfo.constants.php
 */
define ('FILEINFO_PRESERVE_ATIME', 128);

/**
 * Don't translate unprintable characters to a \ooo octal
 * representation.
 * @link http://php.net/manual/en/fileinfo.constants.php
 */
define ('FILEINFO_RAW', 256);

// End of fileinfo v.0.1
?>
