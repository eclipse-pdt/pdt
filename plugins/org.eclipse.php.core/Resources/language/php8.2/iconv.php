<?php

// Start of iconv v.8.2.6

/**
 * Returns the character count of string
 * @link http://www.php.net/manual/en/function.iconv-strlen.php
 * @param string $string 
 * @param string|null $encoding [optional] 
 * @return int|false Returns the character count of string, as an integer,
 * or false if an error occurs during the encoding.
 */
function iconv_strlen (string $string, ?string $encoding = null): int|false {}

/**
 * Cut out part of a string
 * @link http://www.php.net/manual/en/function.iconv-substr.php
 * @param string $string 
 * @param int $offset 
 * @param int|null $length [optional] 
 * @param string|null $encoding [optional] 
 * @return string|false Returns the portion of string specified by the
 * offset and length parameters.
 * <p>If string is shorter than offset
 * characters long, false will be returned.
 * If string is exactly offset
 * characters long, an empty string will be returned.</p>
 */
function iconv_substr (string $string, int $offset, ?int $length = null, ?string $encoding = null): string|false {}

/**
 * Finds position of first occurrence of a needle within a haystack
 * @link http://www.php.net/manual/en/function.iconv-strpos.php
 * @param string $haystack 
 * @param string $needle 
 * @param int $offset [optional] 
 * @param string|null $encoding [optional] 
 * @return int|false Returns the numeric position of the first occurrence of
 * needle in haystack.
 * <p>If needle is not found,
 * iconv_strpos will return false.</p>
 */
function iconv_strpos (string $haystack, string $needle, int $offset = null, ?string $encoding = null): int|false {}

/**
 * Finds the last occurrence of a needle within a haystack
 * @link http://www.php.net/manual/en/function.iconv-strrpos.php
 * @param string $haystack 
 * @param string $needle 
 * @param string|null $encoding [optional] 
 * @return int|false Returns the numeric position of the last occurrence of
 * needle in haystack.
 * <p>If needle is not found,
 * iconv_strrpos will return false.</p>
 */
function iconv_strrpos (string $haystack, string $needle, ?string $encoding = null): int|false {}

/**
 * Composes a MIME header field
 * @link http://www.php.net/manual/en/function.iconv-mime-encode.php
 * @param string $field_name 
 * @param string $field_value 
 * @param array $options [optional] 
 * @return string|false Returns an encoded MIME field on success,
 * or false if an error occurs during the encoding.
 */
function iconv_mime_encode (string $field_name, string $field_value, array $options = '[]'): string|false {}

/**
 * Decodes a MIME header field
 * @link http://www.php.net/manual/en/function.iconv-mime-decode.php
 * @param string $string 
 * @param int $mode [optional] 
 * @param string|null $encoding [optional] 
 * @return string|false Returns a decoded MIME field on success,
 * or false if an error occurs during the decoding.
 */
function iconv_mime_decode (string $string, int $mode = null, ?string $encoding = null): string|false {}

/**
 * Decodes multiple MIME header fields at once
 * @link http://www.php.net/manual/en/function.iconv-mime-decode-headers.php
 * @param string $headers 
 * @param int $mode [optional] 
 * @param string|null $encoding [optional] 
 * @return array|false Returns an associative array that holds a whole set of
 * MIME header fields specified by
 * headers on success, or false
 * if an error occurs during the decoding.
 * <p>Each key of the return value represents an individual
 * field name and the corresponding element represents a field value.
 * If more than one field of the same name are present,
 * iconv_mime_decode_headers automatically incorporates
 * them into a numerically indexed array in the order of occurrence.
 * Note that header names are not case-insensitive.</p>
 */
function iconv_mime_decode_headers (string $headers, int $mode = null, ?string $encoding = null): array|false {}

/**
 * Convert a string from one character encoding to another
 * @link http://www.php.net/manual/en/function.iconv.php
 * @param string $from_encoding 
 * @param string $to_encoding 
 * @param string $string 
 * @return string|false Returns the converted string, or false on failure.
 */
function iconv (string $from_encoding, string $to_encoding, string $string): string|false {}

/**
 * Set current setting for character encoding conversion
 * @link http://www.php.net/manual/en/function.iconv-set-encoding.php
 * @param string $type 
 * @param string $encoding 
 * @return bool Returns true on success or false on failure.
 */
function iconv_set_encoding (string $type, string $encoding): bool {}

/**
 * Retrieve internal configuration variables of iconv extension
 * @link http://www.php.net/manual/en/function.iconv-get-encoding.php
 * @param string $type [optional] 
 * @return array|string|false Returns the current value of the internal configuration variable if
 * successful or false on failure.
 * <p>If type is omitted or set to "all",
 * iconv_get_encoding returns an array that
 * stores all these variables.</p>
 */
function iconv_get_encoding (string $type = '"all"'): array|string|false {}

define ('ICONV_IMPL', "unknown");
define ('ICONV_VERSION', "unknown");
define ('ICONV_MIME_DECODE_STRICT', 1);
define ('ICONV_MIME_DECODE_CONTINUE_ON_ERROR', 2);

// End of iconv v.8.2.6
