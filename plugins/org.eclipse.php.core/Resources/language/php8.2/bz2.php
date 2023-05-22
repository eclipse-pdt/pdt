<?php

// Start of bz2 v.8.2.6

/**
 * Opens a bzip2 compressed file
 * @link http://www.php.net/manual/en/function.bzopen.php
 * @param string|resource $file 
 * @param string $mode 
 * @return resource|false If the open fails, bzopen returns false, otherwise
 * it returns a pointer to the newly opened file.
 */
function bzopen ($file, string $mode) {}

/**
 * Binary safe bzip2 file read
 * @link http://www.php.net/manual/en/function.bzread.php
 * @param resource $bz 
 * @param int $length [optional] 
 * @return string|false Returns the uncompressed data, or false on error.
 */
function bzread ($bz, int $length = 1024): string|false {}

/**
 * Binary safe bzip2 file write
 * @link http://www.php.net/manual/en/function.bzwrite.php
 * @param resource $bz 
 * @param string $data 
 * @param int|null $length [optional] 
 * @return int|false Returns the number of bytes written, or false on error.
 */
function bzwrite ($bz, string $data, ?int $length = null): int|false {}

/**
 * Do nothing
 * @link http://www.php.net/manual/en/function.bzflush.php
 * @param resource $bz 
 * @return bool Returns true on success or false on failure.
 */
function bzflush ($bz): bool {}

/**
 * Close a bzip2 file
 * @link http://www.php.net/manual/en/function.bzclose.php
 * @param resource $bz 
 * @return bool Returns true on success or false on failure.
 */
function bzclose ($bz): bool {}

/**
 * Returns a bzip2 error number
 * @link http://www.php.net/manual/en/function.bzerrno.php
 * @param resource $bz 
 * @return int Returns the error number as an integer.
 */
function bzerrno ($bz): int {}

/**
 * Returns a bzip2 error string
 * @link http://www.php.net/manual/en/function.bzerrstr.php
 * @param resource $bz 
 * @return string Returns a string containing the error message.
 */
function bzerrstr ($bz): string {}

/**
 * Returns the bzip2 error number and error string in an array
 * @link http://www.php.net/manual/en/function.bzerror.php
 * @param resource $bz 
 * @return array Returns an associative array, with the error code in the 
 * errno entry, and the error message in the
 * errstr entry.
 */
function bzerror ($bz): array {}

/**
 * Compress a string into bzip2 encoded data
 * @link http://www.php.net/manual/en/function.bzcompress.php
 * @param string $data 
 * @param int $block_size [optional] 
 * @param int $work_factor [optional] 
 * @return string|int The compressed string, or an error number if an error occurred.
 */
function bzcompress (string $data, int $block_size = 4, int $work_factor = null): string|int {}

/**
 * Decompresses bzip2 encoded data
 * @link http://www.php.net/manual/en/function.bzdecompress.php
 * @param string $data 
 * @param bool $use_less_memory [optional] 
 * @return string|int|false The decompressed string, or false or an error number if an error occurred.
 */
function bzdecompress (string $data, bool $use_less_memory = false): string|int|false {}

// End of bz2 v.8.2.6
