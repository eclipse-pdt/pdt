<?php

// Start of fileinfo v.8.3.0

class finfo  {

	/**
	 * {@inheritdoc}
	 * @param int $flags [optional]
	 * @param string|null $magic_database [optional]
	 */
	public function __construct (int $flags = 0, ?string $magic_database = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param int $flags [optional]
	 * @param mixed $context [optional]
	 */
	public function file (string $filename, int $flags = 0, $context = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 * @param int $flags [optional]
	 * @param mixed $context [optional]
	 */
	public function buffer (string $string, int $flags = 0, $context = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function set_flags (int $flags) {}

}

/**
 * {@inheritdoc}
 * @param int $flags [optional]
 * @param string|null $magic_database [optional]
 */
function finfo_open (int $flags = 0, ?string $magic_database = NULL): finfo|false {}

/**
 * {@inheritdoc}
 * @param finfo $finfo
 */
function finfo_close (finfo $finfo): bool {}

/**
 * {@inheritdoc}
 * @param finfo $finfo
 * @param int $flags
 */
function finfo_set_flags (finfo $finfo, int $flags): bool {}

/**
 * {@inheritdoc}
 * @param finfo $finfo
 * @param string $filename
 * @param int $flags [optional]
 * @param mixed $context [optional]
 */
function finfo_file (finfo $finfo, string $filename, int $flags = 0, $context = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param finfo $finfo
 * @param string $string
 * @param int $flags [optional]
 * @param mixed $context [optional]
 */
function finfo_buffer (finfo $finfo, string $string, int $flags = 0, $context = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $filename
 */
function mime_content_type ($filename = null): string|false {}

define ('FILEINFO_NONE', 0);
define ('FILEINFO_SYMLINK', 2);
define ('FILEINFO_MIME', 1040);
define ('FILEINFO_MIME_TYPE', 16);
define ('FILEINFO_MIME_ENCODING', 1024);
define ('FILEINFO_DEVICES', 8);
define ('FILEINFO_CONTINUE', 32);
define ('FILEINFO_PRESERVE_ATIME', 128);
define ('FILEINFO_RAW', 256);
define ('FILEINFO_APPLE', 2048);
define ('FILEINFO_EXTENSION', 16777216);

// End of fileinfo v.8.3.0
