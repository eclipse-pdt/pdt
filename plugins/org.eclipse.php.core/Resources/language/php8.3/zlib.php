<?php

// Start of zlib v.8.3.0

final class InflateContext  {
}

final class DeflateContext  {
}

/**
 * {@inheritdoc}
 * @param string $data
 * @param int $flags
 */
function ob_gzhandler (string $data, int $flags): string|false {}

/**
 * {@inheritdoc}
 */
function zlib_get_coding_type (): string|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param int $use_include_path [optional]
 */
function gzfile (string $filename, int $use_include_path = 0): array|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param string $mode
 * @param int $use_include_path [optional]
 */
function gzopen (string $filename, string $mode, int $use_include_path = 0) {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param int $use_include_path [optional]
 */
function readgzfile (string $filename, int $use_include_path = 0): int|false {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param int $encoding
 * @param int $level [optional]
 */
function zlib_encode (string $data, int $encoding, int $level = -1): string|false {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param int $max_length [optional]
 */
function zlib_decode (string $data, int $max_length = 0): string|false {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param int $level [optional]
 * @param int $encoding [optional]
 */
function gzdeflate (string $data, int $level = -1, int $encoding = -15): string|false {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param int $level [optional]
 * @param int $encoding [optional]
 */
function gzencode (string $data, int $level = -1, int $encoding = 31): string|false {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param int $level [optional]
 * @param int $encoding [optional]
 */
function gzcompress (string $data, int $level = -1, int $encoding = 15): string|false {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param int $max_length [optional]
 */
function gzinflate (string $data, int $max_length = 0): string|false {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param int $max_length [optional]
 */
function gzdecode (string $data, int $max_length = 0): string|false {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param int $max_length [optional]
 */
function gzuncompress (string $data, int $max_length = 0): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param string $data
 * @param int|null $length [optional]
 */
function gzwrite ($stream = null, string $data, ?int $length = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param string $data
 * @param int|null $length [optional]
 */
function gzputs ($stream = null, string $data, ?int $length = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function gzrewind ($stream = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function gzclose ($stream = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function gzeof ($stream = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function gzgetc ($stream = null): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function gzpassthru ($stream = null): int {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param int $offset
 * @param int $whence [optional]
 */
function gzseek ($stream = null, int $offset, int $whence = 0): int {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function gztell ($stream = null): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param int $length
 */
function gzread ($stream = null, int $length): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param int|null $length [optional]
 */
function gzgets ($stream = null, ?int $length = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param int $encoding
 * @param array $options [optional]
 */
function deflate_init (int $encoding, array $options = array (
)): DeflateContext|false {}

/**
 * {@inheritdoc}
 * @param DeflateContext $context
 * @param string $data
 * @param int $flush_mode [optional]
 */
function deflate_add (DeflateContext $context, string $data, int $flush_mode = 2): string|false {}

/**
 * {@inheritdoc}
 * @param int $encoding
 * @param array $options [optional]
 */
function inflate_init (int $encoding, array $options = array (
)): InflateContext|false {}

/**
 * {@inheritdoc}
 * @param InflateContext $context
 * @param string $data
 * @param int $flush_mode [optional]
 */
function inflate_add (InflateContext $context, string $data, int $flush_mode = 2): string|false {}

/**
 * {@inheritdoc}
 * @param InflateContext $context
 */
function inflate_get_status (InflateContext $context): int {}

/**
 * {@inheritdoc}
 * @param InflateContext $context
 */
function inflate_get_read_len (InflateContext $context): int {}

define ('FORCE_GZIP', 31);
define ('FORCE_DEFLATE', 15);
define ('ZLIB_ENCODING_RAW', -15);
define ('ZLIB_ENCODING_GZIP', 31);
define ('ZLIB_ENCODING_DEFLATE', 15);
define ('ZLIB_NO_FLUSH', 0);
define ('ZLIB_PARTIAL_FLUSH', 1);
define ('ZLIB_SYNC_FLUSH', 2);
define ('ZLIB_FULL_FLUSH', 3);
define ('ZLIB_BLOCK', 5);
define ('ZLIB_FINISH', 4);
define ('ZLIB_FILTERED', 1);
define ('ZLIB_HUFFMAN_ONLY', 2);
define ('ZLIB_RLE', 3);
define ('ZLIB_FIXED', 4);
define ('ZLIB_DEFAULT_STRATEGY', 0);
define ('ZLIB_VERSION', "1.2.12");
define ('ZLIB_VERNUM', 4800);
define ('ZLIB_OK', 0);
define ('ZLIB_STREAM_END', 1);
define ('ZLIB_NEED_DICT', 2);
define ('ZLIB_ERRNO', -1);
define ('ZLIB_STREAM_ERROR', -2);
define ('ZLIB_DATA_ERROR', -3);
define ('ZLIB_MEM_ERROR', -4);
define ('ZLIB_BUF_ERROR', -5);
define ('ZLIB_VERSION_ERROR', -6);

// End of zlib v.8.3.0
