<?php

// Start of zlib v.7.1.1

/**
 * Output a gz-file
 * @link http://www.php.net/manual/en/function.readgzfile.php
 * @param string $filename <p>
 * The file name. This file will be opened from the filesystem and its
 * contents written to standard output.
 * </p>
 * @param int $use_include_path [optional] <p>
 * You can set this optional parameter to 1, if you
 * want to search for the file in the include_path too.
 * </p>
 * @return int the number of (uncompressed) bytes read from the file. If
 * an error occurs, false is returned and unless the function was
 * called as @readgzfile, an error message is
 * printed.
 */
function readgzfile (string $filename, int $use_include_path = null) {}

/**
 * Rewind the position of a gz-file pointer
 * @link http://www.php.net/manual/en/function.gzrewind.php
 * @param resource $zp <p>
 * The gz-file pointer. It must be valid, and must point to a file
 * successfully opened by gzopen.
 * </p>
 * @return bool true on success or false on failure
 */
function gzrewind ($zp) {}

/**
 * Close an open gz-file pointer
 * @link http://www.php.net/manual/en/function.gzclose.php
 * @param resource $zp <p>
 * The gz-file pointer. It must be valid, and must point to a file
 * successfully opened by gzopen.
 * </p>
 * @return bool true on success or false on failure
 */
function gzclose ($zp) {}

/**
 * Test for EOF on a gz-file pointer
 * @link http://www.php.net/manual/en/function.gzeof.php
 * @param resource $zp <p>
 * The gz-file pointer. It must be valid, and must point to a file
 * successfully opened by gzopen.
 * </p>
 * @return int true if the gz-file pointer is at EOF or an error occurs;
 * otherwise returns false.
 */
function gzeof ($zp) {}

/**
 * Get character from gz-file pointer
 * @link http://www.php.net/manual/en/function.gzgetc.php
 * @param resource $zp <p>
 * The gz-file pointer. It must be valid, and must point to a file
 * successfully opened by gzopen.
 * </p>
 * @return string The uncompressed character or false on EOF (unlike gzeof).
 */
function gzgetc ($zp) {}

/**
 * Get line from file pointer
 * @link http://www.php.net/manual/en/function.gzgets.php
 * @param resource $zp <p>
 * The gz-file pointer. It must be valid, and must point to a file
 * successfully opened by gzopen.
 * </p>
 * @param int $length [optional] <p>
 * The length of data to get.
 * </p>
 * @return string The uncompressed string, or false on error.
 */
function gzgets ($zp, int $length = null) {}

/**
 * Get line from gz-file pointer and strip HTML tags
 * @link http://www.php.net/manual/en/function.gzgetss.php
 * @param resource $zp <p>
 * The gz-file pointer. It must be valid, and must point to a file
 * successfully opened by gzopen.
 * </p>
 * @param int $length <p>
 * The length of data to get.
 * </p>
 * @param string $allowable_tags [optional] <p>
 * You can use this optional parameter to specify tags which should not 
 * be stripped.
 * </p>
 * @return string The uncompressed and stripped string, or false on error.
 */
function gzgetss ($zp, int $length, string $allowable_tags = null) {}

/**
 * Binary-safe gz-file read
 * @link http://www.php.net/manual/en/function.gzread.php
 * @param resource $zp <p>
 * The gz-file pointer. It must be valid, and must point to a file
 * successfully opened by gzopen.
 * </p>
 * @param int $length <p>
 * The number of bytes to read.
 * </p>
 * @return string The data that have been read.
 */
function gzread ($zp, int $length) {}

/**
 * Open gz-file
 * @link http://www.php.net/manual/en/function.gzopen.php
 * @param string $filename <p>
 * The file name.
 * </p>
 * @param string $mode <p>
 * As in fopen (rb or 
 * wb) but can also include a compression level 
 * (wb9) or a strategy: f for
 * filtered data as in wb6f, h for
 * Huffman only compression as in wb1h.
 * (See the description of deflateInit2
 * in zlib.h for 
 * more information about the strategy parameter.)
 * </p>
 * @param int $use_include_path [optional] <p>
 * You can set this optional parameter to 1, if you
 * want to search for the file in the include_path too.
 * </p>
 * @return resource a file pointer to the file opened, after that, everything you read
 * from this file descriptor will be transparently decompressed and what you 
 * write gets compressed.
 * <p>
 * If the open fails, the function returns false.
 * </p>
 */
function gzopen (string $filename, string $mode, int $use_include_path = null) {}

/**
 * Output all remaining data on a gz-file pointer
 * @link http://www.php.net/manual/en/function.gzpassthru.php
 * @param resource $zp <p>
 * The gz-file pointer. It must be valid, and must point to a file
 * successfully opened by gzopen.
 * </p>
 * @return int The number of uncompressed characters read from gz
 * and passed through to the input, or false on error.
 */
function gzpassthru ($zp) {}

/**
 * Seek on a gz-file pointer
 * @link http://www.php.net/manual/en/function.gzseek.php
 * @param resource $zp <p>
 * The gz-file pointer. It must be valid, and must point to a file
 * successfully opened by gzopen.
 * </p>
 * @param int $offset <p>
 * The seeked offset.
 * </p>
 * @param int $whence [optional] <p>
 * whence values are:
 * SEEK_SET - Set position equal to offset bytes.
 * SEEK_CUR - Set position to current location plus offset.
 * </p>
 * <p>
 * If whence is not specified, it is assumed to be
 * SEEK_SET.
 * </p>
 * @return int Upon success, returns 0; otherwise, returns -1. Note that seeking
 * past EOF is not considered an error.
 */
function gzseek ($zp, int $offset, int $whence = null) {}

/**
 * Tell gz-file pointer read/write position
 * @link http://www.php.net/manual/en/function.gztell.php
 * @param resource $zp <p>
 * The gz-file pointer. It must be valid, and must point to a file
 * successfully opened by gzopen.
 * </p>
 * @return int The position of the file pointer or false if an error occurs.
 */
function gztell ($zp) {}

/**
 * Binary-safe gz-file write
 * @link http://www.php.net/manual/en/function.gzwrite.php
 * @param resource $zp <p>
 * The gz-file pointer. It must be valid, and must point to a file
 * successfully opened by gzopen.
 * </p>
 * @param string $string <p>
 * The string to write.
 * </p>
 * @param int $length [optional] <p>
 * The number of uncompressed bytes to write. If supplied, writing will 
 * stop after length (uncompressed) bytes have been
 * written or the end of string is reached,
 * whichever comes first.
 * </p>
 * <p>
 * Note that if the length argument is given,
 * then the magic_quotes_runtime
 * configuration option will be ignored and no slashes will be
 * stripped from string.
 * </p>
 * @return int the number of (uncompressed) bytes written to the given gz-file 
 * stream.
 */
function gzwrite ($zp, string $string, int $length = null) {}

/**
 * Alias: gzwrite
 * @link http://www.php.net/manual/en/function.gzputs.php
 * @param $fp
 * @param $str
 * @param $length [optional]
 */
function gzputs ($fp, $str, $length = null) {}

/**
 * Read entire gz-file into an array
 * @link http://www.php.net/manual/en/function.gzfile.php
 * @param string $filename <p>
 * The file name.
 * </p>
 * @param int $use_include_path [optional] <p>
 * You can set this optional parameter to 1, if you
 * want to search for the file in the include_path too.
 * </p>
 * @return array An array containing the file, one line per cell, empty lines included, and with newlines still attached.
 */
function gzfile (string $filename, int $use_include_path = null) {}

/**
 * Compress a string
 * @link http://www.php.net/manual/en/function.gzcompress.php
 * @param string $data <p>
 * The data to compress.
 * </p>
 * @param int $level [optional] <p>
 * The level of compression. Can be given as 0 for no compression up to 9
 * for maximum compression.
 * </p>
 * <p>
 * If -1 is used, the default compression of the zlib library is used which is 6.
 * </p>
 * @param int $encoding [optional] <p>
 * One of ZLIB_ENCODING_&#42; constants.
 * </p>
 * @return string The compressed string or false if an error occurred.
 */
function gzcompress (string $data, int $level = null, int $encoding = null) {}

/**
 * Uncompress a compressed string
 * @link http://www.php.net/manual/en/function.gzuncompress.php
 * @param string $data <p>
 * The data compressed by gzcompress.
 * </p>
 * @param int $length [optional] <p>
 * The maximum length of data to decode.
 * </p>
 * @return string The original uncompressed data or false on error.
 * <p>
 * The function will return an error if the uncompressed data is more than
 * 32768 times the length of the compressed input data 
 * or more than the optional parameter length.
 * </p>
 */
function gzuncompress (string $data, int $length = null) {}

/**
 * Deflate a string
 * @link http://www.php.net/manual/en/function.gzdeflate.php
 * @param string $data <p>
 * The data to deflate.
 * </p>
 * @param int $level [optional] <p>
 * The level of compression. Can be given as 0 for no compression up to 9
 * for maximum compression. If not given, the default compression level will
 * be the default compression level of the zlib library.
 * </p>
 * @param int $encoding [optional] <p>
 * One of ZLIB_ENCODING_&#42; constants.
 * </p>
 * @return string The deflated string or false if an error occurred.
 */
function gzdeflate (string $data, int $level = null, int $encoding = null) {}

/**
 * Inflate a deflated string
 * @link http://www.php.net/manual/en/function.gzinflate.php
 * @param string $data <p>
 * The data compressed by gzdeflate.
 * </p>
 * @param int $length [optional] <p>
 * The maximum length of data to decode.
 * </p>
 * @return string The original uncompressed data or false on error.
 * <p>
 * The function will return an error if the uncompressed data is more than
 * 32768 times the length of the compressed input data 
 * or more than the optional parameter length.
 * </p>
 */
function gzinflate (string $data, int $length = null) {}

/**
 * Create a gzip compressed string
 * @link http://www.php.net/manual/en/function.gzencode.php
 * @param string $data <p>
 * The data to encode.
 * </p>
 * @param int $level [optional] <p>
 * The level of compression. Can be given as 0 for no compression up to 9
 * for maximum compression. If not given, the default compression level will
 * be the default compression level of the zlib library.
 * </p>
 * @param int $encoding_mode [optional] <p>
 * The encoding mode. Can be FORCE_GZIP (the default)
 * or FORCE_DEFLATE. 
 * </p>
 * <p>
 * Prior to PHP 5.4.0, using FORCE_DEFLATE results in
 * a standard zlib deflated string (inclusive zlib headers) after a gzip
 * file header but without the trailing crc32 checksum.
 * </p>
 * <p>
 * In PHP 5.4.0 and later, FORCE_DEFLATE generates
 * RFC 1950 compliant output, consisting of a zlib header, the deflated
 * data, and an Adler checksum.
 * </p>
 * @return string The encoded string, or false if an error occurred.
 */
function gzencode (string $data, int $level = null, int $encoding_mode = null) {}

/**
 * Decodes a gzip compressed string
 * @link http://www.php.net/manual/en/function.gzdecode.php
 * @param string $data <p>
 * The data to decode, encoded by gzencode.
 * </p>
 * @param int $length [optional] <p>
 * The maximum length of data to decode.
 * </p>
 * @return string The decoded string, or false if an error occurred.
 */
function gzdecode (string $data, int $length = null) {}

/**
 * Compress data with the specified encoding
 * @link http://www.php.net/manual/en/function.zlib-encode.php
 * @param string $data <p>
 * The data to compress.
 * </p>
 * @param string $encoding <p>
 * The compression algorithm. Either ZLIB_ENCODING_RAW,
 * ZLIB_ENCODING_DEFLATE or
 * ZLIB_ENCODING_GZIP.
 * </p>
 * @param string $level [optional] <p>
 * </p>
 * @return string 
 */
function zlib_encode (string $data, string $encoding, string $level = null) {}

/**
 * Uncompress any raw/gzip/zlib encoded data
 * @link http://www.php.net/manual/en/function.zlib-decode.php
 * @param string $data <p>
 * </p>
 * @param string $max_decoded_len [optional] <p>
 * </p>
 * @return string the uncompressed data, or false on failure.
 */
function zlib_decode (string $data, string $max_decoded_len = null) {}

/**
 * Returns the coding type used for output compression
 * @link http://www.php.net/manual/en/function.zlib-get-coding-type.php
 * @return string Possible return values are gzip, deflate,
 * or false.
 */
function zlib_get_coding_type () {}

/**
 * Initialize an incremental deflate context
 * @link http://www.php.net/manual/en/function.deflate-init.php
 * @param int $encoding <p>
 * One of the ZLIB_ENCODING_&#42; constants.
 * </p>
 * @param array $options [optional] <p>
 * An associative array which may contain the following elements:
 * level
 * <p>
 * The compression level in range -1..9; defaults to -1.
 * </p>
 * @return resource a deflate context resource (zlib.deflate) on
 * success, or false on failure.
 */
function deflate_init (int $encoding, array $options = null) {}

/**
 * Incrementally deflate data
 * @link http://www.php.net/manual/en/function.deflate-add.php
 * @param resource $context <p>
 * A context created with deflate_init.
 * </p>
 * @param string $data <p>
 * A chunk of data to compress.
 * </p>
 * @param int $flush_mode [optional] <p>
 * One of ZLIB_BLOCK,
 * ZLIB_NO_FLUSH,
 * ZLIB_PARTIAL_FLUSH,
 * ZLIB_SYNC_FLUSH (default),
 * ZLIB_FULL_FLUSH, ZLIB_FINISH.
 * Normally you will want to set ZLIB_NO_FLUSH to
 * maximize compression, and ZLIB_FINISH to terminate
 * with the last chunk of data. See the zlib manual for a
 * detailed description of these constants.
 * </p>
 * @return string a chunk of compressed data, or false on failure.
 */
function deflate_add ($context, string $data, int $flush_mode = null) {}

/**
 * Initialize an incremental inflate context
 * @link http://www.php.net/manual/en/function.inflate-init.php
 * @param int $encoding <p>
 * One of the ZLIB_ENCODING_&#42; constants.
 * </p>
 * @param array $options [optional] <p>
 * An associative array which may contain the following elements:
 * level
 * <p>
 * The compression level in range -1..9; defaults to -1.
 * </p>
 * @return resource an inflate context resource (zlib.inflate) on
 * success, or false on failure.
 */
function inflate_init (int $encoding, array $options = null) {}

/**
 * Incrementally inflate encoded data
 * @link http://www.php.net/manual/en/function.inflate-add.php
 * @param resource $context <p>
 * A context created with inflate_init.
 * </p>
 * @param string $encoded_data <p>
 * A chunk of compressed data.
 * </p>
 * @param int $flush_mode [optional] <p>
 * One of ZLIB_BLOCK,
 * ZLIB_NO_FLUSH,
 * ZLIB_PARTIAL_FLUSH,
 * ZLIB_SYNC_FLUSH (default),
 * ZLIB_FULL_FLUSH, ZLIB_FINISH.
 * Normally you will want to set ZLIB_NO_FLUSH to
 * maximize compression, and ZLIB_FINISH to terminate
 * with the last chunk of data. See the zlib manual for a
 * detailed description of these constants.
 * </p>
 * @return string a chunk of uncompressed data, or false on failure.
 */
function inflate_add ($context, string $encoded_data, int $flush_mode = null) {}

/**
 * ob_start callback function to gzip output buffer
 * @link http://www.php.net/manual/en/function.ob-gzhandler.php
 * @param string $buffer <p>
 * </p>
 * @param int $mode <p>
 * </p>
 * @return string 
 */
function ob_gzhandler (string $buffer, int $mode) {}

define ('FORCE_GZIP', 31);
define ('FORCE_DEFLATE', 15);

/**
 * DEFLATE algorithm as per RFC 1951. Available as of PHP 7.0.0.
 * @link http://www.php.net/manual/en/zlib.constants.php
 */
define ('ZLIB_ENCODING_RAW', -15);

/**
 * GZIP algorithm as per RFC 1952. Available as of PHP 7.0.0.
 * @link http://www.php.net/manual/en/zlib.constants.php
 */
define ('ZLIB_ENCODING_GZIP', 31);

/**
 * ZLIB compression algorithm as per RFC 1950. Available as of PHP 7.0.0.
 * @link http://www.php.net/manual/en/zlib.constants.php
 */
define ('ZLIB_ENCODING_DEFLATE', 15);

/**
 * Available as of PHP 7.0.0.
 * @link http://www.php.net/manual/en/zlib.constants.php
 */
define ('ZLIB_NO_FLUSH', 0);

/**
 * Available as of PHP 7.0.0.
 * @link http://www.php.net/manual/en/zlib.constants.php
 */
define ('ZLIB_PARTIAL_FLUSH', 1);

/**
 * Available as of PHP 7.0.0.
 * @link http://www.php.net/manual/en/zlib.constants.php
 */
define ('ZLIB_SYNC_FLUSH', 2);

/**
 * Available as of PHP 7.0.0.
 * @link http://www.php.net/manual/en/zlib.constants.php
 */
define ('ZLIB_FULL_FLUSH', 3);

/**
 * Available as of PHP 7.0.0.
 * @link http://www.php.net/manual/en/zlib.constants.php
 */
define ('ZLIB_BLOCK', 5);

/**
 * Available as of PHP 7.0.0.
 * @link http://www.php.net/manual/en/zlib.constants.php
 */
define ('ZLIB_FINISH', 4);

/**
 * Available as of PHP 7.0.0.
 * @link http://www.php.net/manual/en/zlib.constants.php
 */
define ('ZLIB_FILTERED', 1);

/**
 * Available as of PHP 7.0.0.
 * @link http://www.php.net/manual/en/zlib.constants.php
 */
define ('ZLIB_HUFFMAN_ONLY', 2);

/**
 * Available as of PHP 7.0.0.
 * @link http://www.php.net/manual/en/zlib.constants.php
 */
define ('ZLIB_RLE', 3);

/**
 * Available as of PHP 7.0.0.
 * @link http://www.php.net/manual/en/zlib.constants.php
 */
define ('ZLIB_FIXED', 4);

/**
 * Available as of PHP 7.0.0.
 * @link http://www.php.net/manual/en/zlib.constants.php
 */
define ('ZLIB_DEFAULT_STRATEGY', 0);
define ('ZLIB_VERSION', "1.2.8");
define ('ZLIB_VERNUM', 4736);

// End of zlib v.7.1.1
