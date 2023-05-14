<?php

// Start of iconv v.8.2.6

/**
 * Returns the character count of string
 * @link http://www.php.net/manual/en/function.iconv-strlen.php
 * @param string $string The string.
 * @param mixed $encoding [optional] If encoding parameter is omitted or null,
 * string is assumed to be encoded in
 * iconv.internal_encoding.
 * @return mixed the character count of string, as an integer,
 * or false if an error occurs during the encoding.
 */
function iconv_strlen (string $string, $encoding = null): int|false {}

/**
 * Cut out part of a string
 * @link http://www.php.net/manual/en/function.iconv-substr.php
 * @param string $string The original string.
 * @param int $offset <p>
 * If offset is non-negative,
 * iconv_substr cuts the portion out of
 * string beginning at offset'th
 * character, counting from zero.
 * </p>
 * <p>
 * If offset is negative,
 * iconv_substr cuts out the portion beginning
 * at the position, offset characters
 * away from the end of string.
 * </p>
 * @param mixed $length [optional] <p>
 * If length is given and is positive, the return
 * value will contain at most length characters
 * of the portion that begins at offset
 * (depending on the length of string).
 * </p>
 * <p>
 * If negative length is passed,
 * iconv_substr cuts the portion out of
 * string from the offset'th
 * character up to the character that is
 * length characters away from the end of the string.
 * In case offset is also negative, the start position
 * is calculated beforehand according to the rule explained above.
 * </p>
 * @param mixed $encoding [optional] <p>
 * If encoding parameter is omitted or null,
 * string are assumed to be encoded in
 * iconv.internal_encoding.
 * </p>
 * <p>
 * Note that offset and length
 * parameters are always deemed to represent offsets that are
 * calculated on the basis of the character set determined by
 * encoding, whilst the counterpart
 * substr always takes these for byte offsets.
 * </p>
 * @return mixed the portion of string specified by the
 * offset and length parameters.
 * <p>
 * If string is shorter than offset
 * characters long, false will be returned.
 * If string is exactly offset
 * characters long, an empty string will be returned.
 * </p>
 */
function iconv_substr (string $string, int $offset, $length = null, $encoding = null): string|false {}

/**
 * Finds position of first occurrence of a needle within a haystack
 * @link http://www.php.net/manual/en/function.iconv-strpos.php
 * @param string $haystack The entire string.
 * @param string $needle The searched substring.
 * @param int $offset [optional] The optional offset parameter specifies
 * the position from which the search should be performed.
 * If the offset is negative, it is counted from the end of the string.
 * @param mixed $encoding [optional] If encoding parameter is omitted or null,
 * string are assumed to be encoded in
 * iconv.internal_encoding.
 * @return mixed the numeric position of the first occurrence of
 * needle in haystack.
 * <p>
 * If needle is not found,
 * iconv_strpos will return false.
 * </p>
 */
function iconv_strpos (string $haystack, string $needle, int $offset = null, $encoding = null): int|false {}

/**
 * Finds the last occurrence of a needle within a haystack
 * @link http://www.php.net/manual/en/function.iconv-strrpos.php
 * @param string $haystack The entire string.
 * @param string $needle The searched substring.
 * @param mixed $encoding [optional] If encoding parameter is omitted or null,
 * string are assumed to be encoded in
 * iconv.internal_encoding.
 * @return mixed the numeric position of the last occurrence of
 * needle in haystack.
 * <p>
 * If needle is not found,
 * iconv_strrpos will return false.
 * </p>
 */
function iconv_strrpos (string $haystack, string $needle, $encoding = null): int|false {}

/**
 * Composes a MIME header field
 * @link http://www.php.net/manual/en/function.iconv-mime-encode.php
 * @param string $field_name The field name.
 * @param string $field_value The field value.
 * @param array $options [optional] You can control the behaviour of iconv_mime_encode
 * by specifying an associative array that contains configuration items
 * to the optional third parameter options.
 * The items supported by iconv_mime_encode are
 * listed below. Note that item names are treated case-sensitive.
 * <table>
 * Configuration items supported by iconv_mime_encode
 * <table>
 * <tr valign="top">
 * <td>Item</td>
 * <td>Type</td>
 * <td>Description</td>
 * <td>Default value</td>
 * <td>Example</td>
 * </tr>
 * <tr valign="top">
 * <td>scheme</td>
 * <td>string</td>
 * <td>
 * Specifies the method to encode a field value by. The value of
 * this item may be either "B" or "Q", where "B" stands for
 * base64 encoding scheme and "Q" stands for
 * quoted-printable encoding scheme.
 * </td>
 * <td>B</td>
 * <td>B</td>
 * </tr>
 * <tr valign="top">
 * <td>input-charset</td>
 * <td>string</td>
 * <td>
 * Specifies the character set in which the first parameter
 * field_name and the second parameter
 * field_value are presented. If not given,
 * iconv_mime_encode assumes those parameters
 * are presented to it in the
 * iconv.internal_encoding
 * ini setting.
 * </td>
 * <td>
 * iconv.internal_encoding
 * </td>
 * <td>ISO-8859-1</td>
 * </tr>
 * <tr valign="top">
 * <td>output-charset</td>
 * <td>string</td>
 * <td>
 * Specifies the character set to use to compose the
 * MIME header.
 * </td>
 * <td>
 * iconv.internal_encoding
 * </td>
 * <td>UTF-8</td>
 * </tr>
 * <tr valign="top">
 * <td>line-length</td>
 * <td>int</td>
 * <td>
 * Specifies the maximum length of the header lines. The resulting
 * header is "folded" to a set of multiple lines in case
 * the resulting header field would be longer than the value of this
 * parameter, according to
 * RFC2822 - Internet Message Format.
 * If not given, the length will be limited to 76 characters.
 * </td>
 * <td>76</td>
 * <td>996</td>
 * </tr>
 * <tr valign="top">
 * <td>line-break-chars</td>
 * <td>string</td>
 * <td>
 * Specifies the sequence of characters to append to each line
 * as an end-of-line sign when "folding" is performed on a long header
 * field. If not given, this defaults to "\r\n"
 * (CR LF). Note that
 * this parameter is always treated as an ASCII string regardless
 * of the value of input-charset.
 * </td>
 * <td>\r\n</td>
 * <td>\n</td>
 * </tr>
 * </table>
 * </table>
 * @return mixed an encoded MIME field on success,
 * or false if an error occurs during the encoding.
 */
function iconv_mime_encode (string $field_name, string $field_value, array $options = null): string|false {}

/**
 * Decodes a MIME header field
 * @link http://www.php.net/manual/en/function.iconv-mime-decode.php
 * @param string $string The encoded header, as a string.
 * @param int $mode [optional] mode determines the behaviour in the event
 * iconv_mime_decode encounters a malformed
 * MIME header field. You can specify any combination
 * of the following bitmasks.
 * <table>
 * Bitmasks acceptable to iconv_mime_decode
 * <table>
 * <tr valign="top">
 * <td>Value</td>
 * <td>Constant</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>1</td>
 * <td>ICONV_MIME_DECODE_STRICT</td>
 * <td>
 * If set, the given header is decoded in full conformance with the
 * standards defined in RFC2047.
 * This option is disabled by default because there are a lot of
 * broken mail user agents that don't follow the specification and don't
 * produce correct MIME headers.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>2</td>
 * <td>ICONV_MIME_DECODE_CONTINUE_ON_ERROR</td>
 * <td>
 * If set, iconv_mime_decode_headers
 * attempts to ignore any grammatical errors and continue to process
 * a given header.
 * </td>
 * </tr>
 * </table>
 * </table>
 * @param mixed $encoding [optional] The optional encoding parameter specifies the
 * character set to represent the result by. If omitted or null,
 * iconv.internal_encoding
 * will be used.
 * @return mixed a decoded MIME field on success,
 * or false if an error occurs during the decoding.
 */
function iconv_mime_decode (string $string, int $mode = null, $encoding = null): string|false {}

/**
 * Decodes multiple MIME header fields at once
 * @link http://www.php.net/manual/en/function.iconv-mime-decode-headers.php
 * @param string $headers The encoded headers, as a string.
 * @param int $mode [optional] mode determines the behaviour in the event
 * iconv_mime_decode_headers encounters a malformed
 * MIME header field. You can specify any combination
 * of the following bitmasks.
 * <table>
 * Bitmasks acceptable to iconv_mime_decode_headers
 * <table>
 * <tr valign="top">
 * <td>Value</td>
 * <td>Constant</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>1</td>
 * <td>ICONV_MIME_DECODE_STRICT</td>
 * <td>
 * If set, the given header is decoded in full conformance with the
 * standards defined in RFC2047.
 * This option is disabled by default because there are a lot of
 * broken mail user agents that don't follow the specification and don't
 * produce correct MIME headers.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>2</td>
 * <td>ICONV_MIME_DECODE_CONTINUE_ON_ERROR</td>
 * <td>
 * If set, iconv_mime_decode_headers
 * attempts to ignore any grammatical errors and continue to process
 * a given header.
 * </td>
 * </tr>
 * </table>
 * </table>
 * @param mixed $encoding [optional] The optional encoding parameter specifies the
 * character set to represent the result by. If omitted or null,
 * iconv.internal_encoding
 * will be used.
 * @return mixed an associative array that holds a whole set of
 * MIME header fields specified by
 * headers on success, or false
 * if an error occurs during the decoding.
 * <p>
 * Each key of the return value represents an individual
 * field name and the corresponding element represents a field value.
 * If more than one field of the same name are present,
 * iconv_mime_decode_headers automatically incorporates
 * them into a numerically indexed array in the order of occurrence.
 * Note that header names are not case-insensitive.
 * </p>
 */
function iconv_mime_decode_headers (string $headers, int $mode = null, $encoding = null): array|false {}

/**
 * Convert a string from one character encoding to another
 * @link http://www.php.net/manual/en/function.iconv.php
 * @param string $from_encoding The current encoding used to interpret string.
 * @param string $to_encoding <p>
 * The desired encoding of the result.
 * </p>
 * <p>
 * If the string //TRANSLIT is appended to
 * to_encoding, then transliteration is activated. This
 * means that when a character can't be represented in the target charset,
 * it may be approximated through one or several similarly looking
 * characters. If the string //IGNORE is appended,
 * characters that cannot be represented in the target charset are silently
 * discarded. Otherwise, E_NOTICE is generated and the function
 * will return false.
 * </p>
 * <p>
 * If and how //TRANSLIT works exactly depends on the
 * system's iconv() implementation (cf. ICONV_IMPL).
 * Some implementations are known to ignore //TRANSLIT,
 * so the conversion is likely to fail for characters which are illegal for
 * the to_encoding.
 * </p>
 * @param string $string The string to be converted.
 * @return mixed the converted string, or false on failure.
 */
function iconv (string $from_encoding, string $to_encoding, string $string): string|false {}

/**
 * Set current setting for character encoding conversion
 * @link http://www.php.net/manual/en/function.iconv-set-encoding.php
 * @param string $type <p>
 * The value of type can be any one of these:
 * <p>
 * input_encoding
 * output_encoding
 * internal_encoding
 * </p>
 * </p>
 * @param string $encoding The character set.
 * @return bool true on success or false on failure
 */
function iconv_set_encoding (string $type, string $encoding): bool {}

/**
 * Retrieve internal configuration variables of iconv extension
 * @link http://www.php.net/manual/en/function.iconv-get-encoding.php
 * @param string $type [optional] <p>
 * The value of the optional type can be:
 * <p>
 * all
 * input_encoding
 * output_encoding
 * internal_encoding
 * </p>
 * </p>
 * @return mixed the current value of the internal configuration variable if
 * successful or false on failure.
 * <p>
 * If type is omitted or set to "all",
 * iconv_get_encoding returns an array that
 * stores all these variables.
 * </p>
 */
function iconv_get_encoding (string $type = null): array|string|false {}


/**
 * string
 * @link http://www.php.net/manual/en/iconv.constants.php
 */
define ('ICONV_IMPL', "unknown");

/**
 * string
 * @link http://www.php.net/manual/en/iconv.constants.php
 */
define ('ICONV_VERSION', "unknown");

/**
 * int
 * @link http://www.php.net/manual/en/iconv.constants.php
 */
define ('ICONV_MIME_DECODE_STRICT', 1);

/**
 * int
 * @link http://www.php.net/manual/en/iconv.constants.php
 */
define ('ICONV_MIME_DECODE_CONTINUE_ON_ERROR', 2);

// End of iconv v.8.2.6
