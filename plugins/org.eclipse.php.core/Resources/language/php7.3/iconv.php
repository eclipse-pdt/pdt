<?php

// Start of iconv v.7.3.0

/**
 * Convert string to requested character encoding
 * @link http://www.php.net/manual/en/function.iconv.php
 * @param string $in_charset The input charset.
 * @param string $out_charset <p>
 * The output charset.
 * </p>
 * <p>
 * If you append the string //TRANSLIT to
 * out_charset transliteration is activated. This
 * means that when a character can't be represented in the target charset,
 * it can be approximated through one or several similarly looking
 * characters. If you append the string //IGNORE,
 * characters that cannot be represented in the target charset are silently
 * discarded. Otherwise, E_NOTICE is generated and the function
 * will return false.
 * </p>
 * <p>
 * If and how //TRANSLIT works exactly depends on the
 * system's iconv() implementation (cf. ICONV_IMPL).
 * Some implementations are known to ignore //TRANSLIT,
 * so the conversion is likely to fail for characters which are illegal for
 * the out_charset.
 * </p>
 * @param string $str The string to be converted.
 * @return string the converted string or false on failure.
 */
function iconv (string $in_charset, string $out_charset, string $str) {}

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
function iconv_get_encoding (string $type = null) {}

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
 * @param string $charset The character set.
 * @return bool true on success or false on failure
 */
function iconv_set_encoding (string $type, string $charset) {}

/**
 * Returns the character count of string
 * @link http://www.php.net/manual/en/function.iconv-strlen.php
 * @param string $str The string.
 * @param string $charset [optional] If charset parameter is omitted,
 * str is assumed to be encoded in
 * iconv.internal_encoding.
 * @return int the character count of str, as an integer.
 */
function iconv_strlen (string $str, string $charset = null) {}

/**
 * Cut out part of a string
 * @link http://www.php.net/manual/en/function.iconv-substr.php
 * @param string $str The original string.
 * @param int $offset <p>
 * If offset is non-negative,
 * iconv_substr cuts the portion out of
 * str beginning at offset'th
 * character, counting from zero.
 * </p>
 * <p>
 * If offset is negative,
 * iconv_substr cuts out the portion beginning
 * at the position, offset characters
 * away from the end of str.
 * </p>
 * @param int $length [optional] <p>
 * If length is given and is positive, the return
 * value will contain at most length characters
 * of the portion that begins at offset
 * (depending on the length of string).
 * </p>
 * <p>
 * If negative length is passed,
 * iconv_substr cuts the portion out of
 * str from the offset'th
 * character up to the character that is
 * length characters away from the end of the string.
 * In case offset is also negative, the start position
 * is calculated beforehand according to the rule explained above.
 * </p>
 * @param string $charset [optional] <p>
 * If charset parameter is omitted,
 * string are assumed to be encoded in
 * iconv.internal_encoding.
 * </p>
 * <p>
 * Note that offset and length
 * parameters are always deemed to represent offsets that are
 * calculated on the basis of the character set determined by
 * charset, whilst the counterpart
 * substr always takes these for byte offsets.
 * </p>
 * @return string the portion of str specified by the
 * offset and length parameters.
 * <p>
 * If str is shorter than offset
 * characters long, false will be returned.
 * If str is exactly offset
 * characters long, an empty string will be returned.
 * </p>
 */
function iconv_substr (string $str, int $offset, int $length = null, string $charset = null) {}

/**
 * Finds position of first occurrence of a needle within a haystack
 * @link http://www.php.net/manual/en/function.iconv-strpos.php
 * @param string $haystack The entire string.
 * @param string $needle The searched substring.
 * @param int $offset [optional] The optional offset parameter specifies
 * the position from which the search should be performed.
 * If the offset is negative, it is counted from the end of the string.
 * @param string $charset [optional] If charset parameter is omitted,
 * string are assumed to be encoded in
 * iconv.internal_encoding.
 * @return int the numeric position of the first occurrence of
 * needle in haystack.
 * <p>
 * If needle is not found,
 * iconv_strpos will return false.
 * </p>
 */
function iconv_strpos (string $haystack, string $needle, int $offset = null, string $charset = null) {}

/**
 * Finds the last occurrence of a needle within a haystack
 * @link http://www.php.net/manual/en/function.iconv-strrpos.php
 * @param string $haystack The entire string.
 * @param string $needle The searched substring.
 * @param string $charset [optional] If charset parameter is omitted,
 * string are assumed to be encoded in
 * iconv.internal_encoding.
 * @return int the numeric position of the last occurrence of
 * needle in haystack.
 * <p>
 * If needle is not found,
 * iconv_strrpos will return false.
 * </p>
 */
function iconv_strrpos (string $haystack, string $needle, string $charset = null) {}

/**
 * Composes a MIME header field
 * @link http://www.php.net/manual/en/function.iconv-mime-encode.php
 * @param string $field_name The field name.
 * @param string $field_value The field value.
 * @param array $preferences [optional] You can control the behaviour of iconv_mime_encode
 * by specifying an associative array that contains configuration items
 * to the optional third parameter preferences.
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
 * <td>integer</td>
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
 * @return string an encoded MIME field on success,
 * or false if an error occurs during the encoding.
 */
function iconv_mime_encode (string $field_name, string $field_value, array $preferences = null) {}

/**
 * Decodes a MIME header field
 * @link http://www.php.net/manual/en/function.iconv-mime-decode.php
 * @param string $encoded_header The encoded header, as a string.
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
 * @param string $charset [optional] The optional charset parameter specifies the
 * character set to represent the result by. If omitted,
 * iconv.internal_encoding
 * will be used.
 * @return string a decoded MIME field on success,
 * or false if an error occurs during the decoding.
 */
function iconv_mime_decode (string $encoded_header, int $mode = null, string $charset = null) {}

/**
 * Decodes multiple MIME header fields at once
 * @link http://www.php.net/manual/en/function.iconv-mime-decode-headers.php
 * @param string $encoded_headers The encoded headers, as a string.
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
 * @param string $charset [optional] The optional charset parameter specifies the
 * character set to represent the result by. If omitted,
 * iconv.internal_encoding
 * will be used.
 * @return array an associative array that holds a whole set of
 * MIME header fields specified by
 * encoded_headers on success, or false
 * if an error occurs during the decoding.
 * <p>
 * Each key of the return value represents an individual
 * field name and the corresponding element represents a field value.
 * If more than one field of the same name are present,
 * iconv_mime_decode_headers automatically incorporates
 * them into a numerically indexed array in the order of occurrence.
 * </p>
 */
function iconv_mime_decode_headers (string $encoded_headers, int $mode = null, string $charset = null) {}


/**
 * string
 * @link http://www.php.net/manual/en/iconv.constants.php
 */
define ('ICONV_IMPL', "\"libiconv\"");

/**
 * string
 * @link http://www.php.net/manual/en/iconv.constants.php
 */
define ('ICONV_VERSION', 1.15);

/**
 * integer
 * @link http://www.php.net/manual/en/iconv.constants.php
 */
define ('ICONV_MIME_DECODE_STRICT', 1);

/**
 * integer
 * @link http://www.php.net/manual/en/iconv.constants.php
 */
define ('ICONV_MIME_DECODE_CONTINUE_ON_ERROR', 2);

// End of iconv v.7.3.0
