<?php

// Start of iconv v.

/**
 * Convert string to requested character encoding
 * @link http://php.net/manual/en/function.iconv.php
 * @param in_charset string
 * @param out_charset string
 * @param str string
 * @return string the converted string or false on failure.
 */
function iconv ($in_charset, $out_charset, $str) {}

/**
 * Convert character encoding as output buffer handler
 * @link http://php.net/manual/en/function.ob-iconv-handler.php
 * @param contents string
 * @param status int
 * @return string 
 */
function ob_iconv_handler ($contents, $status) {}

/**
 * Retrieve internal configuration variables of iconv extension
 * @link http://php.net/manual/en/function.iconv-get-encoding.php
 * @param type string[optional]
 * @return mixed the current value of the internal configuration variable if
 */
function iconv_get_encoding ($type = null) {}

/**
 * Set current setting for character encoding conversion
 * @link http://php.net/manual/en/function.iconv-set-encoding.php
 * @param type string
 * @param charset string
 * @return bool 
 */
function iconv_set_encoding ($type, $charset) {}

/**
 * Returns the character count of string
 * @link http://php.net/manual/en/function.iconv-strlen.php
 * @param str string
 * @param charset string[optional]
 * @return int the character count of str, as an integer.
 */
function iconv_strlen ($str, $charset = null) {}

/**
 * Cut out part of a string
 * @link http://php.net/manual/en/function.iconv-substr.php
 * @param str string
 * @param offset int
 * @return string the portion of str specified by the
 */
function iconv_substr ($str, $offset) {}

/**
 * Finds position of first occurrence of a needle within a haystack
 * @link http://php.net/manual/en/function.iconv-strpos.php
 * @param haystack string
 * @param needle string
 * @param offset int[optional]
 * @param charset string[optional]
 * @return int the numeric position of the first occurrence of
 */
function iconv_strpos ($haystack, $needle, $offset = null, $charset = null) {}

/**
 * Finds the last occurrence of a needle within a haystack
 * @link http://php.net/manual/en/function.iconv-strrpos.php
 * @param haystack string
 * @param needle string
 * @param charset string[optional]
 * @return int the numeric position of the last occurrence of
 */
function iconv_strrpos ($haystack, $needle, $charset = null) {}

/**
 * Composes a <literal>MIME</literal> header field
 * @link http://php.net/manual/en/function.iconv-mime-encode.php
 * @param field_name string
 * @param field_value string
 * @param preferences array[optional]
 * @return string an encoded MIME field on success,
 */
function iconv_mime_encode ($field_name, $field_value, array $preferences = null) {}

/**
 * Decodes a <literal>MIME</literal> header field
 * @link http://php.net/manual/en/function.iconv-mime-decode.php
 * @param encoded_header string
 * @param mode int[optional]
 * @param charset string[optional]
 * @return string a decoded MIME field on success,
 */
function iconv_mime_decode ($encoded_header, $mode = null, $charset = null) {}

/**
 * Decodes multiple <literal>MIME</literal> header fields at once
 * @link http://php.net/manual/en/function.iconv-mime-decode-headers.php
 * @param encoded_headers string
 * @param mode int[optional]
 * @param charset string[optional]
 * @return array 
 */
function iconv_mime_decode_headers ($encoded_headers, $mode = null, $charset = null) {}

define ('ICONV_IMPL', "glibc");
define ('ICONV_VERSION', 2.7);
define ('ICONV_MIME_DECODE_STRICT', 1);
define ('ICONV_MIME_DECODE_CONTINUE_ON_ERROR', 2);

// End of iconv v.
?>
