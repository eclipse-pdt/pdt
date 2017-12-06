<?php

// Start of bz2 v.7.1.1

/**
 * Opens a bzip2 compressed file
 * @link http://www.php.net/manual/en/function.bzopen.php
 * @param mixed $file The name of the file to open, or an existing stream resource.
 * @param string $mode The modes 'r' (read), and 'w' (write) are supported.
 * Everything else will cause bzopen to return false.
 * @return resource If the open fails, bzopen returns false, otherwise
 * it returns a pointer to the newly opened file.
 */
function bzopen ($file, string $mode) {}

/**
 * Binary safe bzip2 file read
 * @link http://www.php.net/manual/en/function.bzread.php
 * @param resource $bz The file pointer. It must be valid and must point to a file 
 * successfully opened by bzopen.
 * @param int $length [optional] If not specified, bzread will read 1024 
 * (uncompressed) bytes at a time. A maximum of 8192
 * uncompressed bytes will be read at a time.
 * @return string the uncompressed data, or false on error.
 */
function bzread ($bz, int $length = null) {}

/**
 * Binary safe bzip2 file write
 * @link http://www.php.net/manual/en/function.bzwrite.php
 * @param resource $bz The file pointer. It must be valid and must point to a file 
 * successfully opened by bzopen.
 * @param string $data The written data.
 * @param int $length [optional] If supplied, writing will stop after length 
 * (uncompressed) bytes have been written or the end of 
 * data is reached, whichever comes first.
 * @return int the number of bytes written, or false on error.
 */
function bzwrite ($bz, string $data, int $length = null) {}

/**
 * Force a write of all buffered data
 * @link http://www.php.net/manual/en/function.bzflush.php
 * @param resource $bz The file pointer. It must be valid and must point to a file 
 * successfully opened by bzopen.
 * @return bool true on success or false on failure
 */
function bzflush ($bz) {}

/**
 * Close a bzip2 file
 * @link http://www.php.net/manual/en/function.bzclose.php
 * @param resource $bz The file pointer. It must be valid and must point to a file 
 * successfully opened by bzopen.
 * @return int true on success or false on failure
 */
function bzclose ($bz) {}

/**
 * Returns a bzip2 error number
 * @link http://www.php.net/manual/en/function.bzerrno.php
 * @param resource $bz The file pointer. It must be valid and must point to a file 
 * successfully opened by bzopen.
 * @return int the error number as an integer.
 */
function bzerrno ($bz) {}

/**
 * Returns a bzip2 error string
 * @link http://www.php.net/manual/en/function.bzerrstr.php
 * @param resource $bz The file pointer. It must be valid and must point to a file 
 * successfully opened by bzopen.
 * @return string a string containing the error message.
 */
function bzerrstr ($bz) {}

/**
 * Returns the bzip2 error number and error string in an array
 * @link http://www.php.net/manual/en/function.bzerror.php
 * @param resource $bz The file pointer. It must be valid and must point to a file 
 * successfully opened by bzopen.
 * @return array an associative array, with the error code in the 
 * errno entry, and the error message in the
 * errstr entry.
 */
function bzerror ($bz) {}

/**
 * Compress a string into bzip2 encoded data
 * @link http://www.php.net/manual/en/function.bzcompress.php
 * @param string $source The string to compress.
 * @param int $blocksize [optional] Specifies the blocksize used during compression and should be a number 
 * from 1 to 9 with 9 giving the best compression, but using more 
 * resources to do so.
 * @param int $workfactor [optional] <p>
 * Controls how the compression phase behaves when presented with worst
 * case, highly repetitive, input data. The value can be between 0 and
 * 250 with 0 being a special case. 
 * </p>
 * <p>
 * Regardless of the workfactor, the generated 
 * output is the same.
 * </p>
 * @return mixed The compressed string, or an error number if an error occurred.
 */
function bzcompress (string $source, int $blocksize = null, int $workfactor = null) {}

/**
 * Decompresses bzip2 encoded data
 * @link http://www.php.net/manual/en/function.bzdecompress.php
 * @param string $source The string to decompress.
 * @param int $small [optional] <p>
 * If true, an alternative decompression algorithm will be used which
 * uses less memory (the maximum memory requirement drops to around 2300K) 
 * but works at roughly half the speed.
 * </p>
 * <p>
 * See the bzip2 documentation for more 
 * information about this feature.
 * </p>
 * @return mixed The decompressed string, or an error number if an error occurred.
 */
function bzdecompress (string $source, int $small = null) {}

// End of bz2 v.7.1.1
