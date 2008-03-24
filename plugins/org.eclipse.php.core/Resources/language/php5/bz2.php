<?php

// Start of bz2 v.

/**
 * Opens a bzip2 compressed file
 * @link http://php.net/manual/en/function.bzopen.php
 * @param filename string
 * @param mode string
 * @return resource 
 */
function bzopen ($filename, $mode) {}

/**
 * Binary safe bzip2 file read
 * @link http://php.net/manual/en/function.bzread.php
 * @param bz resource
 * @param length int[optional]
 * @return string the uncompressed data, or false on error.
 */
function bzread ($bz, $length = null) {}

/**
 * Binary safe bzip2 file write
 * @link http://php.net/manual/en/function.bzwrite.php
 * @param bz resource
 * @param data string
 * @param length int[optional]
 * @return int the number of bytes written, or false on error.
 */
function bzwrite ($bz, $data, $length = null) {}

/**
 * Force a write of all buffered data
 * @link http://php.net/manual/en/function.bzflush.php
 * @param bz resource
 * @return int 
 */
function bzflush ($bz) {}

/**
 * Close a bzip2 file
 * @link http://php.net/manual/en/function.bzclose.php
 * @param bz resource
 * @return int 
 */
function bzclose ($bz) {}

/**
 * Returns a bzip2 error number
 * @link http://php.net/manual/en/function.bzerrno.php
 * @param bz resource
 * @return int the error number as an integer.
 */
function bzerrno ($bz) {}

/**
 * Returns a bzip2 error string
 * @link http://php.net/manual/en/function.bzerrstr.php
 * @param bz resource
 * @return string a string containing the error message.
 */
function bzerrstr ($bz) {}

/**
 * Returns the bzip2 error number and error string in an array
 * @link http://php.net/manual/en/function.bzerror.php
 * @param bz resource
 * @return array an associative array, with the error code in the
 */
function bzerror ($bz) {}

/**
 * Compress a string into bzip2 encoded data
 * @link http://php.net/manual/en/function.bzcompress.php
 * @param source string
 * @param blocksize int[optional]
 * @param workfactor int[optional]
 * @return mixed 
 */
function bzcompress ($source, $blocksize = null, $workfactor = null) {}

/**
 * Decompresses bzip2 encoded data
 * @link http://php.net/manual/en/function.bzdecompress.php
 * @param source string
 * @param small int[optional]
 * @return mixed 
 */
function bzdecompress ($source, $small = null) {}

// End of bz2 v.
?>
