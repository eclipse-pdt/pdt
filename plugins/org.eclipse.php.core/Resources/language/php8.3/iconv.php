<?php

// Start of iconv v.8.3.0

/**
 * {@inheritdoc}
 * @param string $string
 * @param string|null $encoding [optional]
 */
function iconv_strlen (string $string, ?string $encoding = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $offset
 * @param int|null $length [optional]
 * @param string|null $encoding [optional]
 */
function iconv_substr (string $string, int $offset, ?int $length = NULL, ?string $encoding = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param int $offset [optional]
 * @param string|null $encoding [optional]
 */
function iconv_strpos (string $haystack, string $needle, int $offset = 0, ?string $encoding = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param string|null $encoding [optional]
 */
function iconv_strrpos (string $haystack, string $needle, ?string $encoding = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param string $field_name
 * @param string $field_value
 * @param array $options [optional]
 */
function iconv_mime_encode (string $field_name, string $field_value, array $options = array (
)): string|false {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $mode [optional]
 * @param string|null $encoding [optional]
 */
function iconv_mime_decode (string $string, int $mode = 0, ?string $encoding = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $headers
 * @param int $mode [optional]
 * @param string|null $encoding [optional]
 */
function iconv_mime_decode_headers (string $headers, int $mode = 0, ?string $encoding = NULL): array|false {}

/**
 * {@inheritdoc}
 * @param string $from_encoding
 * @param string $to_encoding
 * @param string $string
 */
function iconv (string $from_encoding, string $to_encoding, string $string): string|false {}

/**
 * {@inheritdoc}
 * @param string $type
 * @param string $encoding
 */
function iconv_set_encoding (string $type, string $encoding): bool {}

/**
 * {@inheritdoc}
 * @param string $type [optional]
 */
function iconv_get_encoding (string $type = 'all'): array|string|false {}

define ('ICONV_IMPL', "libiconv");
define ('ICONV_VERSION', 1.11);
define ('ICONV_MIME_DECODE_STRICT', 1);
define ('ICONV_MIME_DECODE_CONTINUE_ON_ERROR', 2);

// End of iconv v.8.3.0
