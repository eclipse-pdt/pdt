<?php

// Start of zlib v.1.1

/**
 * Output a gz-file
 * @link http://php.net/manual/en/function.readgzfile.php
 * @param filename string
 * @param use_include_path int[optional]
 * @return int the number of (uncompressed) bytes read from the file. If
 */
function readgzfile ($filename, $use_include_path = null) {}

/**
 * Rewind the position of a gz-file pointer
 * @link http://php.net/manual/en/function.gzrewind.php
 * @param zp resource
 * @return bool 
 */
function gzrewind ($zp) {}

/**
 * Close an open gz-file pointer
 * @link http://php.net/manual/en/function.gzclose.php
 * @param zp resource
 * @return bool 
 */
function gzclose ($zp) {}

/**
 * Test for end-of-file on a gz-file pointer
 * @link http://php.net/manual/en/function.gzeof.php
 * @param zp resource
 * @return int true if the gz-file pointer is at EOF or an error occurs;
 */
function gzeof ($zp) {}

/**
 * Get character from gz-file pointer
 * @link http://php.net/manual/en/function.gzgetc.php
 * @param zp resource
 * @return string 
 */
function gzgetc ($zp) {}

/**
 * Get line from file pointer
 * @link http://php.net/manual/en/function.gzgets.php
 * @param zp resource
 * @param length int
 * @return string 
 */
function gzgets ($zp, $length) {}

/**
 * Get line from gz-file pointer and strip HTML tags
 * @link http://php.net/manual/en/function.gzgetss.php
 * @param zp resource
 * @param length int
 * @param allowable_tags string[optional]
 * @return string 
 */
function gzgetss ($zp, $length, $allowable_tags = null) {}

/**
 * Binary-safe gz-file read
 * @link http://php.net/manual/en/function.gzread.php
 * @param zp resource
 * @param length int
 * @return string 
 */
function gzread ($zp, $length) {}

/**
 * Open gz-file
 * @link http://php.net/manual/en/function.gzopen.php
 * @param filename string
 * @param mode string
 * @param use_include_path int[optional]
 * @return resource a file pointer to the file opened, after that, everything you read
 */
function gzopen ($filename, $mode, $use_include_path = null) {}

/**
 * Output all remaining data on a gz-file pointer
 * @link http://php.net/manual/en/function.gzpassthru.php
 * @param zp resource
 * @return int 
 */
function gzpassthru ($zp) {}

/**
 * Seek on a gz-file pointer
 * @link http://php.net/manual/en/function.gzseek.php
 * @param zp resource
 * @param offset int
 * @return int 
 */
function gzseek ($zp, $offset) {}

/**
 * Tell gz-file pointer read/write position
 * @link http://php.net/manual/en/function.gztell.php
 * @param zp resource
 * @return int 
 */
function gztell ($zp) {}

/**
 * Binary-safe gz-file write
 * @link http://php.net/manual/en/function.gzwrite.php
 * @param zp resource
 * @param string string
 * @param length int[optional]
 * @return int the number of (uncompressed) bytes written to the given gz-file
 */
function gzwrite ($zp, $string, $length = null) {}

/**
 * &Alias; <function>gzwrite</function>
 * @link http://php.net/manual/en/function.gzputs.php
 */
function gzputs () {}

/**
 * Read entire gz-file into an array
 * @link http://php.net/manual/en/function.gzfile.php
 * @param filename string
 * @param use_include_path int[optional]
 * @return array 
 */
function gzfile ($filename, $use_include_path = null) {}

/**
 * Compress a string
 * @link http://php.net/manual/en/function.gzcompress.php
 * @param data string
 * @param level int[optional]
 * @return string 
 */
function gzcompress ($data, $level = null) {}

/**
 * Uncompress a compressed string
 * @link http://php.net/manual/en/function.gzuncompress.php
 * @param data string
 * @param length int[optional]
 * @return string 
 */
function gzuncompress ($data, $length = null) {}

/**
 * Deflate a string
 * @link http://php.net/manual/en/function.gzdeflate.php
 * @param data string
 * @param level int[optional]
 * @return string 
 */
function gzdeflate ($data, $level = null) {}

/**
 * Inflate a deflated string
 * @link http://php.net/manual/en/function.gzinflate.php
 * @param data string
 * @param length int[optional]
 * @return string 
 */
function gzinflate ($data, $length = null) {}

/**
 * Create a gzip compressed string
 * @link http://php.net/manual/en/function.gzencode.php
 * @param data string
 * @param level int[optional]
 * @param encoding_mode int[optional]
 * @return string 
 */
function gzencode ($data, $level = null, $encoding_mode = null) {}

/**
 * ob_start callback function to gzip output buffer
 * @link http://php.net/manual/en/function.ob-gzhandler.php
 * @param buffer string
 * @param mode int
 * @return string 
 */
function ob_gzhandler ($buffer, $mode) {}

/**
 * Returns the coding type used for output compression
 * @link http://php.net/manual/en/function.zlib-get-coding-type.php
 * @return string 
 */
function zlib_get_coding_type () {}

define ('FORCE_GZIP', 1);
define ('FORCE_DEFLATE', 2);

// End of zlib v.1.1
?>
