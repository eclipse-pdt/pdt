<?php

// Start of zlib v.8.2.6

/**
 * A fully opaque class which replaces zlib.inflate resources as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/class.inflatecontext.php
 */
final class InflateContext  {
}

/**
 * A fully opaque class which replaces zlib.deflate resources as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/class.deflatecontext.php
 */
final class DeflateContext  {
}

/**
 * ob_start callback function to gzip output buffer
 * @link http://www.php.net/manual/en/function.ob-gzhandler.php
 * @param string $data 
 * @param int $flags 
 * @return string|false 
 */
function ob_gzhandler (string $data, int $flags): string|false {}

/**
 * Returns the coding type used for output compression
 * @link http://www.php.net/manual/en/function.zlib-get-coding-type.php
 * @return string|false Possible return values are gzip, deflate,
 * or false.
 */
function zlib_get_coding_type (): string|false {}

/**
 * Read entire gz-file into an array
 * @link http://www.php.net/manual/en/function.gzfile.php
 * @param string $filename 
 * @param int $use_include_path [optional] 
 * @return array|false An array containing the file, one line per cell, empty lines included, and with newlines still attached,
 * or false on failure.
 */
function gzfile (string $filename, int $use_include_path = null): array|false {}

/**
 * Open gz-file
 * @link http://www.php.net/manual/en/function.gzopen.php
 * @param string $filename 
 * @param string $mode 
 * @param int $use_include_path [optional] 
 * @return resource|false Returns a file pointer to the file opened, after that, everything you read
 * from this file descriptor will be transparently decompressed and what you 
 * write gets compressed.
 * <p>If the open fails, the function returns false.</p>
 */
function gzopen (string $filename, string $mode, int $use_include_path = null) {}

/**
 * Output a gz-file
 * @link http://www.php.net/manual/en/function.readgzfile.php
 * @param string $filename 
 * @param int $use_include_path [optional] 
 * @return int|false Returns the number of (uncompressed) bytes read from the file on success,
 * or false on failure
 */
function readgzfile (string $filename, int $use_include_path = null): int|false {}

/**
 * Compress data with the specified encoding
 * @link http://www.php.net/manual/en/function.zlib-encode.php
 * @param string $data The data to compress.
 * @param int $encoding The compression algorithm. Either ZLIB_ENCODING_RAW,
 * ZLIB_ENCODING_DEFLATE or
 * ZLIB_ENCODING_GZIP.
 * @param int $level [optional] 
 * @return string|false 
 */
function zlib_encode (string $data, int $encoding, int $level = -1): string|false {}

/**
 * Uncompress any raw/gzip/zlib encoded data
 * @link http://www.php.net/manual/en/function.zlib-decode.php
 * @param string $data 
 * @param int $max_length [optional] 
 * @return string|false Returns the uncompressed data, or false on failure.
 */
function zlib_decode (string $data, int $max_length = null): string|false {}

/**
 * Deflate a string
 * @link http://www.php.net/manual/en/function.gzdeflate.php
 * @param string $data 
 * @param int $level [optional] 
 * @param int $encoding [optional] 
 * @return string|false The deflated string or false if an error occurred.
 */
function gzdeflate (string $data, int $level = -1, int $encoding = ZLIB_ENCODING_RAW): string|false {}

/**
 * Create a gzip compressed string
 * @link http://www.php.net/manual/en/function.gzencode.php
 * @param string $data 
 * @param int $level [optional] 
 * @param int $encoding [optional] 
 * @return string|false The encoded string, or false if an error occurred.
 */
function gzencode (string $data, int $level = -1, int $encoding = ZLIB_ENCODING_GZIP): string|false {}

/**
 * Compress a string
 * @link http://www.php.net/manual/en/function.gzcompress.php
 * @param string $data 
 * @param int $level [optional] 
 * @param int $encoding [optional] 
 * @return string|false The compressed string or false if an error occurred.
 */
function gzcompress (string $data, int $level = -1, int $encoding = ZLIB_ENCODING_DEFLATE): string|false {}

/**
 * Inflate a deflated string
 * @link http://www.php.net/manual/en/function.gzinflate.php
 * @param string $data 
 * @param int $max_length [optional] 
 * @return string|false The original uncompressed data or false on error.
 * <p>The function will return an error if the uncompressed data is more than
 * 32768 times the length of the compressed input data 
 * or, unless max_length is 0, more than the optional parameter max_length.</p>
 */
function gzinflate (string $data, int $max_length = null): string|false {}

/**
 * Decodes a gzip compressed string
 * @link http://www.php.net/manual/en/function.gzdecode.php
 * @param string $data 
 * @param int $max_length [optional] 
 * @return string|false The decoded string, or or false on failure.
 */
function gzdecode (string $data, int $max_length = null): string|false {}

/**
 * Uncompress a compressed string
 * @link http://www.php.net/manual/en/function.gzuncompress.php
 * @param string $data 
 * @param int $max_length [optional] 
 * @return string|false The original uncompressed data or false on error.
 * <p>The function will return an error if the uncompressed data is more than
 * 32768 times the length of the compressed input data 
 * or more than the optional parameter max_length.</p>
 */
function gzuncompress (string $data, int $max_length = null): string|false {}

/**
 * Binary-safe gz-file write
 * @link http://www.php.net/manual/en/function.gzwrite.php
 * @param resource $stream 
 * @param string $data 
 * @param int|null $length [optional] 
 * @return int|false Returns the number of (uncompressed) bytes written to the given gz-file 
 * stream, or false on failure.
 */
function gzwrite ($stream, string $data, ?int $length = null): int|false {}

/**
 * Alias of gzwrite
 * @link http://www.php.net/manual/en/function.gzputs.php
 * @param resource $stream 
 * @param string $data 
 * @param int|null $length [optional] 
 * @return int|false Returns the number of (uncompressed) bytes written to the given gz-file 
 * stream, or false on failure.
 */
function gzputs ($stream, string $data, ?int $length = null): int|false {}

/**
 * Rewind the position of a gz-file pointer
 * @link http://www.php.net/manual/en/function.gzrewind.php
 * @param resource $stream 
 * @return bool Returns true on success or false on failure.
 */
function gzrewind ($stream): bool {}

/**
 * Close an open gz-file pointer
 * @link http://www.php.net/manual/en/function.gzclose.php
 * @param resource $stream 
 * @return bool Returns true on success or false on failure.
 */
function gzclose ($stream): bool {}

/**
 * Test for EOF on a gz-file pointer
 * @link http://www.php.net/manual/en/function.gzeof.php
 * @param resource $stream 
 * @return bool Returns true if the gz-file pointer is at EOF or an error occurs;
 * otherwise returns false.
 */
function gzeof ($stream): bool {}

/**
 * Get character from gz-file pointer
 * @link http://www.php.net/manual/en/function.gzgetc.php
 * @param resource $stream 
 * @return string|false The uncompressed character or false on EOF (unlike gzeof).
 */
function gzgetc ($stream): string|false {}

/**
 * Output all remaining data on a gz-file pointer
 * @link http://www.php.net/manual/en/function.gzpassthru.php
 * @param resource $stream 
 * @return int The number of uncompressed characters read from gz
 * and passed through to the input.
 */
function gzpassthru ($stream): int {}

/**
 * Seek on a gz-file pointer
 * @link http://www.php.net/manual/en/function.gzseek.php
 * @param resource $stream 
 * @param int $offset 
 * @param int $whence [optional] 
 * @return int Upon success, returns 0; otherwise, returns -1. Note that seeking
 * past EOF is not considered an error.
 */
function gzseek ($stream, int $offset, int $whence = SEEK_SET): int {}

/**
 * Tell gz-file pointer read/write position
 * @link http://www.php.net/manual/en/function.gztell.php
 * @param resource $stream 
 * @return int|false The position of the file pointer or false if an error occurs.
 */
function gztell ($stream): int|false {}

/**
 * Binary-safe gz-file read
 * @link http://www.php.net/manual/en/function.gzread.php
 * @param resource $stream 
 * @param int $length 
 * @return string|false The data that have been read, or false on failure.
 */
function gzread ($stream, int $length): string|false {}

/**
 * Get line from file pointer
 * @link http://www.php.net/manual/en/function.gzgets.php
 * @param resource $stream 
 * @param int|null $length [optional] 
 * @return string|false The uncompressed string, or false on error.
 */
function gzgets ($stream, ?int $length = null): string|false {}

/**
 * Initialize an incremental deflate context
 * @link http://www.php.net/manual/en/function.deflate-init.php
 * @param int $encoding One of the ZLIB_ENCODING_&#42; constants.
 * @param array $options [optional] An associative array which may contain the following elements:
 * <p>
 * level
 * <br>
 * <p>
 * The compression level in range -1..9; defaults to -1.
 * </p>
 * memory
 * <br>
 * <p>
 * The compression memory level in range 1..9; defaults to 8.
 * </p>
 * window
 * <br>
 * <p>
 * The zlib window size (logarithmic) in range 8..15;
 * defaults to 15.
 * zlib changes a window size of 8 to 9,
 * and as of zlib 1.2.8 fails with a warning, if a window size of 8
 * is requested for ZLIB_ENCODING_RAW or ZLIB_ENCODING_GZIP.
 * </p>
 * strategy
 * <br>
 * <p>
 * One of ZLIB_FILTERED,
 * ZLIB_HUFFMAN_ONLY, ZLIB_RLE,
 * ZLIB_FIXED or
 * ZLIB_DEFAULT_STRATEGY (the default).
 * </p>
 * dictionary
 * <br>
 * <p>
 * A string or an array of strings
 * of the preset dictionary (default: no preset dictionary).
 * </p>
 * </p>
 * <p>The compression level in range -1..9; defaults to -1.</p>
 * <p>The compression memory level in range 1..9; defaults to 8.</p>
 * <p>The zlib window size (logarithmic) in range 8..15;
 * defaults to 15.
 * zlib changes a window size of 8 to 9,
 * and as of zlib 1.2.8 fails with a warning, if a window size of 8
 * is requested for ZLIB_ENCODING_RAW or ZLIB_ENCODING_GZIP.</p>
 * <p>One of ZLIB_FILTERED,
 * ZLIB_HUFFMAN_ONLY, ZLIB_RLE,
 * ZLIB_FIXED or
 * ZLIB_DEFAULT_STRATEGY (the default).</p>
 * <p>A string or an array of strings
 * of the preset dictionary (default: no preset dictionary).</p>
 * @return DeflateContext|false Returns a deflate context resource (zlib.deflate) on
 * success, or false on failure.
 */
function deflate_init (int $encoding, array $options = '[]'): DeflateContext|false {}

/**
 * Incrementally deflate data
 * @link http://www.php.net/manual/en/function.deflate-add.php
 * @param DeflateContext $context A context created with deflate_init.
 * @param string $data A chunk of data to compress.
 * @param int $flush_mode [optional] One of ZLIB_BLOCK,
 * ZLIB_NO_FLUSH,
 * ZLIB_PARTIAL_FLUSH,
 * ZLIB_SYNC_FLUSH (default),
 * ZLIB_FULL_FLUSH, ZLIB_FINISH.
 * Normally you will want to set ZLIB_NO_FLUSH to
 * maximize compression, and ZLIB_FINISH to terminate
 * with the last chunk of data. See the zlib manual for a
 * detailed description of these constants.
 * @return string|false Returns a chunk of compressed data, or false on failure.
 */
function deflate_add (DeflateContext $context, string $data, int $flush_mode = ZLIB_SYNC_FLUSH): string|false {}

/**
 * Initialize an incremental inflate context
 * @link http://www.php.net/manual/en/function.inflate-init.php
 * @param int $encoding One of the ZLIB_ENCODING_&#42; constants.
 * @param array $options [optional] An associative array which may contain the following elements:
 * <p>
 * level
 * <br>
 * <p>
 * The compression level in range -1..9; defaults to -1.
 * </p>
 * memory
 * <br>
 * <p>
 * The compression memory level in range 1..9; defaults to 8.
 * </p>
 * window
 * <br>
 * <p>
 * The zlib window size (logarithmic) in range 8..15; defaults to 15.
 * </p>
 * strategy
 * <br>
 * <p>
 * One of ZLIB_FILTERED,
 * ZLIB_HUFFMAN_ONLY, ZLIB_RLE,
 * ZLIB_FIXED or
 * ZLIB_DEFAULT_STRATEGY (the default).
 * </p>
 * dictionary
 * <br>
 * <p>
 * A string or an array of strings
 * of the preset dictionary (default: no preset dictionary).
 * </p>
 * </p>
 * <p>The compression level in range -1..9; defaults to -1.</p>
 * <p>The compression memory level in range 1..9; defaults to 8.</p>
 * <p>The zlib window size (logarithmic) in range 8..15; defaults to 15.</p>
 * <p>One of ZLIB_FILTERED,
 * ZLIB_HUFFMAN_ONLY, ZLIB_RLE,
 * ZLIB_FIXED or
 * ZLIB_DEFAULT_STRATEGY (the default).</p>
 * <p>A string or an array of strings
 * of the preset dictionary (default: no preset dictionary).</p>
 * @return InflateContext|false Returns an inflate context resource (zlib.inflate) on
 * success, or false on failure.
 */
function inflate_init (int $encoding, array $options = '[]'): InflateContext|false {}

/**
 * Incrementally inflate encoded data
 * @link http://www.php.net/manual/en/function.inflate-add.php
 * @param InflateContext $context A context created with inflate_init.
 * @param string $data A chunk of compressed data.
 * @param int $flush_mode [optional] One of ZLIB_BLOCK,
 * ZLIB_NO_FLUSH,
 * ZLIB_PARTIAL_FLUSH,
 * ZLIB_SYNC_FLUSH (default),
 * ZLIB_FULL_FLUSH, ZLIB_FINISH.
 * Normally you will want to set ZLIB_NO_FLUSH to
 * maximize compression, and ZLIB_FINISH to terminate
 * with the last chunk of data. See the zlib manual for a
 * detailed description of these constants.
 * @return string|false Returns a chunk of uncompressed data, or false on failure.
 */
function inflate_add (InflateContext $context, string $data, int $flush_mode = ZLIB_SYNC_FLUSH): string|false {}

/**
 * Get decompression status
 * @link http://www.php.net/manual/en/function.inflate-get-status.php
 * @param InflateContext $context 
 * @return int Returns decompression status.
 */
function inflate_get_status (InflateContext $context): int {}

/**
 * Get number of bytes read so far
 * @link http://www.php.net/manual/en/function.inflate-get-read-len.php
 * @param InflateContext $context 
 * @return int Returns number of bytes read so far or false on failure.
 */
function inflate_get_read_len (InflateContext $context): int {}


/**
 * 
 * @link http://www.php.net/manual/en/zlib.constants.php
 * @var int
 */
define ('FORCE_GZIP', 31);

/**
 * 
 * @link http://www.php.net/manual/en/zlib.constants.php
 * @var int
 */
define ('FORCE_DEFLATE', 15);

/**
 * DEFLATE algorithm as per RFC 1951.
 * @link http://www.php.net/manual/en/zlib.constants.php
 * @var int
 */
define ('ZLIB_ENCODING_RAW', -15);

/**
 * GZIP algorithm as per RFC 1952.
 * @link http://www.php.net/manual/en/zlib.constants.php
 * @var int
 */
define ('ZLIB_ENCODING_GZIP', 31);

/**
 * ZLIB compression algorithm as per RFC 1950.
 * @link http://www.php.net/manual/en/zlib.constants.php
 * @var int
 */
define ('ZLIB_ENCODING_DEFLATE', 15);

/**
 * 
 * @link http://www.php.net/manual/en/zlib.constants.php
 * @var int
 */
define ('ZLIB_NO_FLUSH', 0);

/**
 * 
 * @link http://www.php.net/manual/en/zlib.constants.php
 * @var int
 */
define ('ZLIB_PARTIAL_FLUSH', 1);

/**
 * 
 * @link http://www.php.net/manual/en/zlib.constants.php
 * @var int
 */
define ('ZLIB_SYNC_FLUSH', 2);

/**
 * 
 * @link http://www.php.net/manual/en/zlib.constants.php
 * @var int
 */
define ('ZLIB_FULL_FLUSH', 3);

/**
 * 
 * @link http://www.php.net/manual/en/zlib.constants.php
 * @var int
 */
define ('ZLIB_BLOCK', 5);

/**
 * 
 * @link http://www.php.net/manual/en/zlib.constants.php
 * @var int
 */
define ('ZLIB_FINISH', 4);

/**
 * 
 * @link http://www.php.net/manual/en/zlib.constants.php
 * @var int
 */
define ('ZLIB_FILTERED', 1);

/**
 * 
 * @link http://www.php.net/manual/en/zlib.constants.php
 * @var int
 */
define ('ZLIB_HUFFMAN_ONLY', 2);

/**
 * 
 * @link http://www.php.net/manual/en/zlib.constants.php
 * @var int
 */
define ('ZLIB_RLE', 3);

/**
 * 
 * @link http://www.php.net/manual/en/zlib.constants.php
 * @var int
 */
define ('ZLIB_FIXED', 4);

/**
 * 
 * @link http://www.php.net/manual/en/zlib.constants.php
 * @var int
 */
define ('ZLIB_DEFAULT_STRATEGY', 0);
define ('ZLIB_VERSION', "1.2.11");
define ('ZLIB_VERNUM', 4784);
define ('ZLIB_OK', 0);
define ('ZLIB_STREAM_END', 1);
define ('ZLIB_NEED_DICT', 2);
define ('ZLIB_ERRNO', -1);
define ('ZLIB_STREAM_ERROR', -2);
define ('ZLIB_DATA_ERROR', -3);
define ('ZLIB_MEM_ERROR', -4);
define ('ZLIB_BUF_ERROR', -5);
define ('ZLIB_VERSION_ERROR', -6);

// End of zlib v.8.2.6
