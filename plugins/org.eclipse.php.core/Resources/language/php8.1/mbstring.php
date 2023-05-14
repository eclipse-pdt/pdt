<?php

// Start of mbstring v.8.1.19

/**
 * Set/Get current language
 * @link http://www.php.net/manual/en/function.mb-language.php
 * @param mixed $language [optional] <p>
 * Used for encoding e-mail messages.
 * The valid languages are listed in the following table.
 * mb_send_mail uses this setting to encode e-mail.
 * </p>
 * <table>
 * <tr valign="top">
 * <td>Language</td>
 * <td>Charset</td>
 * <td>Encoding</td>
 * <td>Alias</td>
 * </tr>
 * <tr valign="top">
 * <td>German/de</td>
 * <td>ISO-8859-15</td>
 * <td>Quoted-Printable</td>
 * <td>Deutsch</td>
 * </tr>
 * <tr valign="top">
 * <td>English/en</td>
 * <td>ISO-8859-1</td>
 * <td>Quoted-Printable</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>Armenian/hy</td>
 * <td>ArmSCII-8</td>
 * <td>Quoted-Printable</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>Japanese/ja</td>
 * <td>ISO-2022-JP</td>
 * <td>BASE64</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>Korean/ko</td>
 * <td>ISO-2022-KR</td>
 * <td>BASE64</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>neutral</td>
 * <td>UTF-8</td>
 * <td>BASE64</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>Russian/ru</td>
 * <td>KOI8-R</td>
 * <td>Quoted-Printable</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>Turkish/tr</td>
 * <td>ISO-8859-9</td>
 * <td>Quoted-Printable</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>Ukrainian/ua</td>
 * <td>KOI8-U</td>
 * <td>Quoted-Printable</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>uni</td>
 * <td>UTF-8</td>
 * <td>BASE64</td>
 * <td>universal</td>
 * </tr>
 * <tr valign="top">
 * <td>Simplified Chinese/zh-cn</td>
 * <td>HZ</td>
 * <td>BASE64</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>Traditional Chinese/zh-tw</td>
 * <td>BIG-5</td>
 * <td>BASE64</td>
 * <td></td>
 * </tr>
 * </table>
 * @return mixed If language is set and
 * language is valid, it returns
 * true. Otherwise, it returns false. 
 * When language is omitted or null, it returns the language
 * name as a string.
 */
function mb_language ($language = null): string|bool {}

/**
 * Set/Get internal character encoding
 * @link http://www.php.net/manual/en/function.mb-internal-encoding.php
 * @param mixed $encoding [optional] encoding is the character encoding name 
 * used for the HTTP input character encoding conversion, HTTP output 
 * character encoding conversion, and the default character encoding 
 * for string functions defined by the mbstring module.
 * You should notice that the internal encoding is totally different from the one for multibyte regex.
 * @return mixed If encoding is set, then 
 * Returns true on success or false on failure
 * In this case, the character encoding for multibyte regex is NOT changed.
 * If encoding is omitted, then 
 * the current character encoding name is returned.
 */
function mb_internal_encoding ($encoding = null): string|bool {}

/**
 * Detect HTTP input character encoding
 * @link http://www.php.net/manual/en/function.mb-http-input.php
 * @param mixed $type [optional] <p>
 * Input string specifies the input type. 
 * "G" for GET, "P" for POST, "C" for COOKIE, "S" for string, "L" for list, and
 * <p>array). 
 * If type is omitted, it returns the last input type processed. 
 * </p>
 * @return mixed The character encoding name, as per the type,
 * or an array of character encoding names, if type is "I".
 * If mb_http_input does not process specified
 * HTTP input, it returns false.
 */
function mb_http_input ($type = null): array|string|false {}

/**
 * Set/Get HTTP output character encoding
 * @link http://www.php.net/manual/en/function.mb-http-output.php
 * @param mixed $encoding [optional] <p>
 * If encoding is set,
 * mb_http_output sets the HTTP output character
 * encoding to encoding.
 * </p>
 * <p>
 * If encoding is omitted,
 * mb_http_output returns the current HTTP output
 * character encoding.
 * </p>
 * @return mixed If encoding is omitted,
 * mb_http_output returns the current HTTP output
 * character encoding. Otherwise, 
 * Returns true on success or false on failure
 */
function mb_http_output ($encoding = null): string|bool {}

/**
 * Set/Get character encoding detection order
 * @link http://www.php.net/manual/en/function.mb-detect-order.php
 * @param mixed $encoding [optional] <p>
 * encoding is an array or 
 * comma separated list of character encoding. See supported encodings.
 * </p>
 * <p>
 * If encoding is omitted or null, it returns
 * the current character encoding detection order as array.
 * </p>
 * <p>
 * This setting affects mb_detect_encoding and
 * mb_send_mail.
 * </p>
 * <p>
 * mbstring currently implements the following
 * encoding detection filters. If there is an invalid byte sequence
 * for the following encodings, encoding detection will fail.
 * </p>
 * UTF-8, UTF-7,
 * ASCII,
 * EUC-JP,SJIS,
 * eucJP-win, SJIS-win,
 * JIS, ISO-2022-JP 
 * <p>
 * For ISO-8859-&#42;, mbstring
 * always detects as ISO-8859-&#42;.
 * </p>
 * <p>
 * For UTF-16, UTF-32,
 * UCS2 and UCS4, encoding
 * detection will fail always.
 * </p>
 * @return mixed When setting the encoding detection order, true is returned on success or false on failure.
 * <p>
 * When getting the encoding detection order, an ordered array of the encodings is returned.
 * </p>
 */
function mb_detect_order ($encoding = null): array|bool {}

/**
 * Set/Get substitution character
 * @link http://www.php.net/manual/en/function.mb-substitute-character.php
 * @param mixed $substitute_character [optional] <p>
 * Specify the Unicode value as an int, 
 * or as one of the following strings:
 * <p>
 * <br>
 * "none": no output
 * <br>
 * "long": Output character code value (Example:
 * U+3000, JIS+7E7E)
 * <br>
 * "entity": Output character entity (Example:
 * &amp;#x200;)
 * </p>
 * </p>
 * @return mixed If substitute_character is set, it returns true for success,
 * otherwise returns false. 
 * If substitute_character is not set, it returns the current
 * setting.
 */
function mb_substitute_character ($substitute_character = null): string|int|bool {}

/**
 * Get MIME charset string
 * @link http://www.php.net/manual/en/function.mb-preferred-mime-name.php
 * @param string $encoding The encoding being checked.
 * @return mixed The MIME charset string for character encoding
 * encoding,
 * or false if no charset is preferred for the given encoding.
 */
function mb_preferred_mime_name (string $encoding): string|false {}

/**
 * Parse GET/POST/COOKIE data and set global variable
 * @link http://www.php.net/manual/en/function.mb-parse-str.php
 * @param string $string The URL encoded data.
 * @param array $result An array containing decoded and character encoded converted values.
 * @return bool true on success or false on failure
 */
function mb_parse_str (string $string, array &$result): bool {}

/**
 * Callback function converts character encoding in output buffer
 * @link http://www.php.net/manual/en/function.mb-output-handler.php
 * @param string $string The contents of the output buffer.
 * @param int $status The status of the output buffer.
 * @return string The converted string.
 */
function mb_output_handler (string $string, int $status): string {}

/**
 * Given a multibyte string, return an array of its characters
 * @link http://www.php.net/manual/en/function.mb-str-split.php
 * @param string $string The string to split into characters or chunks.
 * @param int $length [optional] If specified, each element of the returned array will be composed of multiple characters instead of a single character.
 * @param mixed $encoding [optional] mbstring.encoding.parameter
 * <p>
 * A string specifying one of the supported encodings.
 * </p>
 * @return array mb_str_split returns an array of strings.
 */
function mb_str_split (string $string, int $length = null, $encoding = null): array {}

/**
 * Get string length
 * @link http://www.php.net/manual/en/function.mb-strlen.php
 * @param string $string The string being checked for length.
 * @param mixed $encoding [optional] mbstring.encoding.parameter
 * @return int the number of characters in
 * string string having character encoding
 * encoding. A multi-byte character is
 * counted as 1.
 */
function mb_strlen (string $string, $encoding = null): int {}

/**
 * Find position of first occurrence of string in a string
 * @link http://www.php.net/manual/en/function.mb-strpos.php
 * @param string $haystack The string being checked.
 * @param string $needle The string to find in haystack. In contrast
 * with strpos, numeric values are not applied
 * as the ordinal value of a character.
 * @param int $offset [optional] The search offset. If it is not specified, 0 is used.
 * A negative offset counts from the end of the string.
 * @param mixed $encoding [optional] mbstring.encoding.parameter
 * @return mixed the numeric position of
 * the first occurrence of needle in the
 * haystack string. If
 * needle is not found, it returns false.
 */
function mb_strpos (string $haystack, string $needle, int $offset = null, $encoding = null): int|false {}

/**
 * Find position of last occurrence of a string in a string
 * @link http://www.php.net/manual/en/function.mb-strrpos.php
 * @param string $haystack The string being checked, for the last occurrence
 * of needle
 * @param string $needle The string to find in haystack.
 * @param int $offset [optional] May be specified to begin searching an arbitrary number of characters into
 * the string. Negative values will stop searching at an arbitrary point
 * prior to the end of the string.
 * @param mixed $encoding [optional] mbstring.encoding.parameter
 * @return mixed the numeric position of
 * the last occurrence of needle in the
 * haystack string. If
 * needle is not found, it returns false.
 */
function mb_strrpos (string $haystack, string $needle, int $offset = null, $encoding = null): int|false {}

/**
 * Finds position of first occurrence of a string within another, case insensitive
 * @link http://www.php.net/manual/en/function.mb-stripos.php
 * @param string $haystack The string from which to get the position of the first occurrence
 * of needle
 * @param string $needle The string to find in haystack
 * @param int $offset [optional] The position in haystack
 * to start searching.
 * A negative offset counts from the end of the string.
 * @param mixed $encoding [optional] Character encoding name to use.
 * If it is omitted, internal character encoding is used.
 * @return mixed Return the numeric position of the first occurrence of
 * needle in the haystack
 * string, or false if needle is not found.
 */
function mb_stripos (string $haystack, string $needle, int $offset = null, $encoding = null): int|false {}

/**
 * Finds position of last occurrence of a string within another, case insensitive
 * @link http://www.php.net/manual/en/function.mb-strripos.php
 * @param string $haystack The string from which to get the position of the last occurrence
 * of needle
 * @param string $needle The string to find in haystack
 * @param int $offset [optional] The position in haystack
 * to start searching
 * @param mixed $encoding [optional] Character encoding name to use.
 * If it is omitted, internal character encoding is used.
 * @return mixed Return the numeric position of
 * the last occurrence of needle in the
 * haystack string, or false
 * if needle is not found.
 */
function mb_strripos (string $haystack, string $needle, int $offset = null, $encoding = null): int|false {}

/**
 * Finds first occurrence of a string within another
 * @link http://www.php.net/manual/en/function.mb-strstr.php
 * @param string $haystack The string from which to get the first occurrence
 * of needle
 * @param string $needle The string to find in haystack
 * @param bool $before_needle [optional] Determines which portion of haystack
 * this function returns. 
 * If set to true, it returns all of haystack
 * from the beginning to the first occurrence of needle (excluding needle).
 * If set to false, it returns all of haystack
 * from the first occurrence of needle to the end (including needle).
 * @param mixed $encoding [optional] Character encoding name to use.
 * If it is omitted, internal character encoding is used.
 * @return mixed the portion of haystack,
 * or false if needle is not found.
 */
function mb_strstr (string $haystack, string $needle, bool $before_needle = null, $encoding = null): string|false {}

/**
 * Finds the last occurrence of a character in a string within another
 * @link http://www.php.net/manual/en/function.mb-strrchr.php
 * @param string $haystack The string from which to get the last occurrence
 * of needle
 * @param string $needle The string to find in haystack
 * @param bool $before_needle [optional] Determines which portion of haystack
 * this function returns. 
 * If set to true, it returns all of haystack
 * from the beginning to the last occurrence of needle.
 * If set to false, it returns all of haystack
 * from the last occurrence of needle to the end,
 * @param mixed $encoding [optional] Character encoding name to use.
 * If it is omitted, internal character encoding is used.
 * @return mixed the portion of haystack.
 * or false if needle is not found.
 */
function mb_strrchr (string $haystack, string $needle, bool $before_needle = null, $encoding = null): string|false {}

/**
 * Finds first occurrence of a string within another, case insensitive
 * @link http://www.php.net/manual/en/function.mb-stristr.php
 * @param string $haystack The string from which to get the first occurrence
 * of needle
 * @param string $needle The string to find in haystack
 * @param bool $before_needle [optional] Determines which portion of haystack
 * this function returns. 
 * If set to true, it returns all of haystack
 * from the beginning to the first occurrence of needle (excluding needle).
 * If set to false, it returns all of haystack
 * from the first occurrence of needle to the end (including needle).
 * @param mixed $encoding [optional] Character encoding name to use.
 * If it is omitted, internal character encoding is used.
 * @return mixed the portion of haystack,
 * or false if needle is not found.
 */
function mb_stristr (string $haystack, string $needle, bool $before_needle = null, $encoding = null): string|false {}

/**
 * Finds the last occurrence of a character in a string within another, case insensitive
 * @link http://www.php.net/manual/en/function.mb-strrichr.php
 * @param string $haystack The string from which to get the last occurrence
 * of needle
 * @param string $needle The string to find in haystack
 * @param bool $before_needle [optional] Determines which portion of haystack
 * this function returns. 
 * If set to true, it returns all of haystack
 * from the beginning to the last occurrence of needle.
 * If set to false, it returns all of haystack
 * from the last occurrence of needle to the end,
 * @param mixed $encoding [optional] Character encoding name to use.
 * If it is omitted, internal character encoding is used.
 * @return mixed the portion of haystack.
 * or false if needle is not found.
 */
function mb_strrichr (string $haystack, string $needle, bool $before_needle = null, $encoding = null): string|false {}

/**
 * Count the number of substring occurrences
 * @link http://www.php.net/manual/en/function.mb-substr-count.php
 * @param string $haystack The string being checked.
 * @param string $needle The string being found.
 * @param mixed $encoding [optional] mbstring.encoding.parameter
 * @return int The number of times the
 * needle substring occurs in the
 * haystack string.
 */
function mb_substr_count (string $haystack, string $needle, $encoding = null): int {}

/**
 * Get part of string
 * @link http://www.php.net/manual/en/function.mb-substr.php
 * @param string $string The string to extract the substring from.
 * @param int $start <p>
 * If start is non-negative, the returned string
 * will start at the start'th position in
 * string, counting from zero. For instance,
 * in the string 'abcdef', the character at
 * position 0 is 'a', the
 * character at position 2 is
 * 'c', and so forth.
 * </p>
 * <p>
 * If start is negative, the returned string
 * will start at the start'th character
 * from the end of string.
 * </p>
 * @param mixed $length [optional] Maximum number of characters to use from string. If
 * omitted or NULL is passed, extract all characters to
 * the end of the string.
 * @param mixed $encoding [optional] mbstring.encoding.parameter
 * @return string mb_substr returns the portion of
 * string specified by the
 * start and
 * length parameters.
 */
function mb_substr (string $string, int $start, $length = null, $encoding = null): string {}

/**
 * Get part of string
 * @link http://www.php.net/manual/en/function.mb-strcut.php
 * @param string $string The string being cut.
 * @param int $start <p>
 * If start is non-negative, the returned string
 * will start at the start'th byte position in
 * string, counting from zero. For instance,
 * in the string 'abcdef', the byte at
 * position 0 is 'a', the
 * byte at position 2 is
 * 'c', and so forth.
 * </p>
 * <p>
 * If start is negative, the returned string
 * will start at the start'th byte
 * counting back from the end of string. However, if the
 * magnitude of a negative start is greater than the
 * length of the string, the returned portion will start from the beginning of
 * string.
 * </p>
 * @param mixed $length [optional] <p>
 * Length in bytes. If omitted or NULL
 * is passed, extract all bytes to the end of the string.
 * </p>
 * <p>
 * If length is negative, the returned string will
 * end at the length'th byte counting back from the
 * end of string. However, if the magnitude of a negative
 * length is greater than the number of characters
 * after the start position, an empty string will
 * be returned.
 * </p>
 * @param mixed $encoding [optional] mbstring.encoding.parameter
 * @return string mb_strcut returns the portion of
 * string specified by the
 * start and
 * length parameters.
 */
function mb_strcut (string $string, int $start, $length = null, $encoding = null): string {}

/**
 * Return width of string
 * @link http://www.php.net/manual/en/function.mb-strwidth.php
 * @param string $string The string being decoded.
 * @param mixed $encoding [optional] mbstring.encoding.parameter
 * @return int The width of string string.
 */
function mb_strwidth (string $string, $encoding = null): int {}

/**
 * Get truncated string with specified width
 * @link http://www.php.net/manual/en/function.mb-strimwidth.php
 * @param string $string The string being decoded.
 * @param int $start The start position offset. Number of
 * characters from the beginning of string (first character is 0),
 * or if start is negative, number of characters from the end of the string.
 * @param int $width The width of the desired trim. Negative widths count from the end of the string.
 * @param string $trim_marker [optional] A string that is added to the end of string 
 * when string is truncated.
 * @param mixed $encoding [optional] mbstring.encoding.parameter
 * @return string The truncated string. If trim_marker is set,
 * trim_marker replaces the last chars to match the width.
 */
function mb_strimwidth (string $string, int $start, int $width, string $trim_marker = null, $encoding = null): string {}

/**
 * Convert a string from one character encoding to another
 * @link http://www.php.net/manual/en/function.mb-convert-encoding.php
 * @param mixed $string The string or array to be converted.
 * @param string $to_encoding The desired encoding of the result.
 * @param mixed $from_encoding [optional] <p>
 * The current encoding used to interpret string.
 * Multiple encodings may be specified as an array or comma separated
 * list, in which case the correct encoding will be guessed using the
 * same algorithm as mb_detect_encoding.
 * </p>
 * <p>
 * If from_encoding is null or not specified, the
 * mbstring.internal_encoding setting
 * will be used if set, otherwise the default_charset setting.
 * </p>
 * <p>
 * See supported encodings
 * for valid values of to_encoding
 * and from_encoding.
 * </p>
 * @return mixed The encoded string or array on success, or false on failure.
 */
function mb_convert_encoding ($string, string $to_encoding, $from_encoding = null): array|string|false {}

/**
 * Perform case folding on a string
 * @link http://www.php.net/manual/en/function.mb-convert-case.php
 * @param string $string The string being converted.
 * @param int $mode The mode of the conversion. It can be one of 
 * MB_CASE_UPPER, 
 * MB_CASE_LOWER, 
 * MB_CASE_TITLE,
 * MB_CASE_FOLD,
 * MB_CASE_UPPER_SIMPLE,
 * MB_CASE_LOWER_SIMPLE,
 * MB_CASE_TITLE_SIMPLE,
 * MB_CASE_FOLD_SIMPLE.
 * @param mixed $encoding [optional] mbstring.encoding.parameter
 * @return string A case folded version of string converted in the
 * way specified by mode.
 */
function mb_convert_case (string $string, int $mode, $encoding = null): string {}

/**
 * Make a string uppercase
 * @link http://www.php.net/manual/en/function.mb-strtoupper.php
 * @param string $string The string being uppercased.
 * @param mixed $encoding [optional] mbstring.encoding.parameter
 * @return string string with all alphabetic characters converted to uppercase.
 */
function mb_strtoupper (string $string, $encoding = null): string {}

/**
 * Make a string lowercase
 * @link http://www.php.net/manual/en/function.mb-strtolower.php
 * @param string $string The string being lowercased.
 * @param mixed $encoding [optional] mbstring.encoding.parameter
 * @return string string with all alphabetic characters converted to lowercase.
 */
function mb_strtolower (string $string, $encoding = null): string {}

/**
 * Detect character encoding
 * @link http://www.php.net/manual/en/function.mb-detect-encoding.php
 * @param string $string The string being inspected.
 * @param mixed $encodings [optional] <p>
 * A list of character encodings to try, in order. The list may be specified as
 * an array of strings, or a single string separated by commas.
 * </p>
 * <p>
 * If encodings is omitted or null,
 * the current detect_order (set with the 
 * mbstring.detect_order configuration option, or mb_detect_order
 * function) will be used.
 * </p>
 * @param bool $strict [optional] <p>
 * Controls the behaviour when string
 * is not valid in any of the listed encodings.
 * If strict is set to false, the closest matching
 * encoding will be returned; if strict is set to true,
 * false will be returned.
 * </p>
 * <p>
 * The default value for strict can be set
 * with the 
 * mbstring.strict_detection configuration option.
 * </p>
 * @return mixed The detected character encoding, or false if the string is not valid
 * in any of the listed encodings.
 */
function mb_detect_encoding (string $string, $encodings = null, bool $strict = null): string|false {}

/**
 * Returns an array of all supported encodings
 * @link http://www.php.net/manual/en/function.mb-list-encodings.php
 * @return array a numerically indexed array.
 */
function mb_list_encodings (): array {}

/**
 * Get aliases of a known encoding type
 * @link http://www.php.net/manual/en/function.mb-encoding-aliases.php
 * @param string $encoding The encoding type being checked, for aliases.
 * @return array a numerically indexed array of encoding aliases.
 */
function mb_encoding_aliases (string $encoding): array {}

/**
 * Encode string for MIME header
 * @link http://www.php.net/manual/en/function.mb-encode-mimeheader.php
 * @param string $string The string being encoded.
 * Its encoding should be same as mb_internal_encoding.
 * @param mixed $charset [optional] charset specifies the name of the character set
 * in which string is represented in. The default value
 * is determined by the current NLS setting (mbstring.language).
 * @param mixed $transfer_encoding [optional] transfer_encoding specifies the scheme of MIME
 * encoding. It should be either "B" (Base64) or
 * "Q" (Quoted-Printable). Falls back to
 * "B" if not given.
 * @param string $newline [optional] newline specifies the EOL (end-of-line) marker
 * with which mb_encode_mimeheader performs
 * line-folding (a RFC term,
 * the act of breaking a line longer than a certain length into multiple
 * lines. The length is currently hard-coded to 74 characters).
 * Falls back to "\r\n" (CRLF) if not given.
 * @param int $indent [optional] Indentation of the first line (number of characters in the header
 * before string).
 * @return string A converted version of the string represented in ASCII.
 */
function mb_encode_mimeheader (string $string, $charset = null, $transfer_encoding = null, string $newline = null, int $indent = null): string {}

/**
 * Decode string in MIME header field
 * @link http://www.php.net/manual/en/function.mb-decode-mimeheader.php
 * @param string $string The string being decoded.
 * @return string The decoded string in internal character encoding.
 */
function mb_decode_mimeheader (string $string): string {}

/**
 * Convert "kana" one from another ("zen-kaku", "han-kaku" and more)
 * @link http://www.php.net/manual/en/function.mb-convert-kana.php
 * @param string $string The string being converted.
 * @param string $mode [optional] <p>
 * The conversion option.
 * </p>
 * <p>
 * Specify with a combination of following options.
 * <table>
 * Applicable Conversion Options
 * <table>
 * <tr valign="top">
 * <td>Option</td>
 * <td>Meaning</td>
 * </tr>
 * <tr valign="top">
 * <td>r</td>
 * <td>
 * Convert "zen-kaku" alphabets to "han-kaku"
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>R</td>
 * <td>
 * Convert "han-kaku" alphabets to "zen-kaku"
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>n</td>
 * <td>
 * Convert "zen-kaku" numbers to "han-kaku"
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>N</td>
 * <td>
 * Convert "han-kaku" numbers to "zen-kaku"
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>a</td>
 * <td>
 * Convert "zen-kaku" alphabets and numbers to "han-kaku"
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>A</td>
 * <td>
 * Convert "han-kaku" alphabets and numbers to "zen-kaku"
 * (Characters included in "a", "A" options are
 * U+0021 - U+007E excluding U+0022, U+0027, U+005C, U+007E)
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>s</td>
 * <td>
 * Convert "zen-kaku" space to "han-kaku" (U+3000 -> U+0020)
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>S</td>
 * <td>
 * Convert "han-kaku" space to "zen-kaku" (U+0020 -> U+3000)
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>k</td>
 * <td>
 * Convert "zen-kaku kata-kana" to "han-kaku kata-kana"
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>K</td>
 * <td>
 * Convert "han-kaku kata-kana" to "zen-kaku kata-kana"
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>h</td>
 * <td>
 * Convert "zen-kaku hira-gana" to "han-kaku kata-kana"
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>H</td>
 * <td>
 * Convert "han-kaku kata-kana" to "zen-kaku hira-gana"
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>c</td>
 * <td>
 * Convert "zen-kaku kata-kana" to "zen-kaku hira-gana"
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>C</td>
 * <td>
 * Convert "zen-kaku hira-gana" to "zen-kaku kata-kana"
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>V</td>
 * <td>
 * Collapse voiced sound notation and convert them into a character. Use with "K","H"
 * </td>
 * </tr>
 * </table>
 * </table>
 * </p>
 * @param mixed $encoding [optional] mbstring.encoding.parameter
 * @return string The converted string.
 */
function mb_convert_kana (string $string, string $mode = null, $encoding = null): string {}

/**
 * Convert character code in variable(s)
 * @link http://www.php.net/manual/en/function.mb-convert-variables.php
 * @param string $to_encoding The encoding that the string is being converted to.
 * @param mixed $from_encoding from_encoding is specified as an array 
 * or comma separated string, it tries to detect encoding from
 * from-coding. When from_encoding 
 * is omitted, detect_order is used.
 * @param mixed $var var is the reference to the
 * variable being converted. String, Array and Object are accepted.
 * mb_convert_variables assumes all parameters
 * have the same encoding.
 * @param mixed $vars Additional vars.
 * @return mixed The character encoding before conversion for success, 
 * or false for failure.
 */
function mb_convert_variables (string $to_encoding, $from_encoding, &$var, &$vars): string|false {}

/**
 * Encode character to HTML numeric string reference
 * @link http://www.php.net/manual/en/function.mb-encode-numericentity.php
 * @param string $string The string being encoded.
 * @param array $map map is array specifies code area to
 * convert.
 * @param mixed $encoding [optional] mbstring.encoding.parameter
 * @param bool $hex [optional] Whether the returned entity reference should be in hexadecimal notation
 * (otherwise it is in decimal notation).
 * @return string The converted string.
 */
function mb_encode_numericentity (string $string, array $map, $encoding = null, bool $hex = null): string {}

/**
 * Decode HTML numeric string reference to character
 * @link http://www.php.net/manual/en/function.mb-decode-numericentity.php
 * @param string $string The string being decoded.
 * @param array $map map is an array that specifies 
 * the code area to convert.
 * @param mixed $encoding [optional] mbstring.encoding.parameter
 * @return string The converted string.
 */
function mb_decode_numericentity (string $string, array $map, $encoding = null): string {}

/**
 * Send encoded mail
 * @link http://www.php.net/manual/en/function.mb-send-mail.php
 * @param string $to The mail addresses being sent to. Multiple
 * recipients may be specified by putting a comma between each
 * address in to. 
 * This parameter is not automatically encoded.
 * @param string $subject The subject of the mail.
 * @param string $message The message of the mail.
 * @param mixed $additional_headers [optional] <p>
 * String or array to be inserted at the end of the email header.
 * </p>
 * <p>
 * This is typically used to add extra headers (From, Cc, and Bcc).
 * Multiple extra headers should be separated with a CRLF (\r\n).
 * Validate parameter not to be injected unwanted headers by attackers.
 * </p>
 * <p>
 * If an array is passed, its keys are the header names and its
 * values are the respective header values.
 * </p>
 * <p>
 * When sending mail, the mail must contain
 * a From header. This can be set with the 
 * additional_headers parameter, or a default
 * can be set in php.ini.
 * </p>
 * <p>
 * Failing to do this will result in an error
 * message similar to Warning: mail(): "sendmail_from" not
 * set in php.ini or custom "From:" header missing.
 * The From header sets also
 * Return-Path under Windows.
 * </p>
 * <p>
 * If messages are not received, try using a LF (\n) only.
 * Some Unix mail transfer agents (most notably
 * qmail) replace LF by CRLF
 * automatically (which leads to doubling CR if CRLF is used).
 * This should be a last resort, as it does not comply with
 * RFC 2822.
 * </p>
 * @param mixed $additional_params [optional] <p>
 * additional_params is a MTA command line
 * parameter. It is useful when setting the correct Return-Path
 * header when using sendmail.
 * </p>
 * <p>
 * This parameter is escaped by escapeshellcmd internally
 * to prevent command execution. escapeshellcmd prevents
 * command execution, but allows to add additional parameters. For security reason,
 * this parameter should be validated.
 * </p>
 * <p>
 * Since escapeshellcmd is applied automatically, some characters
 * that are allowed as email addresses by internet RFCs cannot be used. Programs
 * that are required to use these characters mail cannot be used.
 * </p>
 * <p>
 * The user that the webserver runs as should be added as a trusted user to the
 * sendmail configuration to prevent a 'X-Warning' header from being added
 * to the message when the envelope sender (-f) is set using this method.
 * For sendmail users, this file is /etc/mail/trusted-users.
 * </p>
 * @return bool true on success or false on failure
 */
function mb_send_mail (string $to, string $subject, string $message, $additional_headers = null, $additional_params = null): bool {}

/**
 * Get internal settings of mbstring
 * @link http://www.php.net/manual/en/function.mb-get-info.php
 * @param string $type [optional] <p>
 * If type is not specified or is specified as "all",
 * "internal_encoding", "http_input",
 * "http_output", "http_output_conv_mimetypes",
 * "mail_charset", "mail_header_encoding",
 * "mail_body_encoding", "illegal_chars",
 * "encoding_translation", "language",
 * "detect_order", "substitute_character"
 * and "strict_detection"
 * will be returned. 
 * </p>
 * <p>
 * If type is specified as
 * "internal_encoding", "http_input",
 * "http_output", "http_output_conv_mimetypes",
 * "mail_charset", "mail_header_encoding",
 * "mail_body_encoding", "illegal_chars",
 * "encoding_translation", "language",
 * "detect_order", "substitute_character"
 * or "strict_detection"
 * the specified setting parameter will be returned.
 * </p>
 * @return mixed An array of type information if type 
 * is not specified, otherwise a specific type,
 * or false on failure.
 */
function mb_get_info (string $type = null): array|string|int|false {}

/**
 * Check if strings are valid for the specified encoding
 * @link http://www.php.net/manual/en/function.mb-check-encoding.php
 * @param mixed $value [optional] <p>
 * The byte stream or array to check. If it is omitted, this function checks
 * all the input from the beginning of the request.
 * </p>
 * <p>
 * As of PHP 8.1.0, omitting this parameter or passing null is deprecated.
 * </p>
 * @param mixed $encoding [optional] The expected encoding.
 * @return bool true on success or false on failure
 */
function mb_check_encoding ($value = null, $encoding = null): bool {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.mb-scrub.php
 * @param string $string 
 * @param mixed $encoding [optional] 
 * @return string 
 */
function mb_scrub (string $string, $encoding = null): string {}

/**
 * Get Unicode code point of character
 * @link http://www.php.net/manual/en/function.mb-ord.php
 * @param string $string A string
 * @param mixed $encoding [optional] mbstring.encoding.parameter
 * @return mixed The Unicode code point for the first character of string or false on failure.
 */
function mb_ord (string $string, $encoding = null): int|false {}

/**
 * Return character by Unicode code point value
 * @link http://www.php.net/manual/en/function.mb-chr.php
 * @param int $codepoint A Unicode codepoint value, e.g. 128024 for U+1F418 ELEPHANT
 * @param mixed $encoding [optional] mbstring.encoding.parameter
 * @return mixed A string containing the requested character, if it can be represented in the specified
 * encoding or false on failure.
 */
function mb_chr (int $codepoint, $encoding = null): string|false {}

/**
 * Set/Get character encoding for multibyte regex
 * @link http://www.php.net/manual/en/function.mb-regex-encoding.php
 * @param mixed $encoding [optional] mbstring.encoding.parameter
 * @return mixed 
 */
function mb_regex_encoding ($encoding = null): string|bool {}

/**
 * Regular expression match with multibyte support
 * @link http://www.php.net/manual/en/function.mb-ereg.php
 * @param string $pattern The search pattern.
 * @param string $string The search string.
 * @param array $matches [optional] <p>
 * If matches are found for parenthesized substrings of
 * pattern and the function is called with the
 * third argument matches, the matches will be stored
 * in the elements of the array matches.
 * If no matches are found, matches is set to an empty
 * array.
 * </p>
 * <p>
 * $matches[1] will contain the substring which starts at
 * the first left parenthesis; $matches[2] will contain
 * the substring starting at the second, and so on.
 * $matches[0] will contain a copy of the complete string
 * matched.
 * </p>
 * @return bool whether pattern matches string.
 */
function mb_ereg (string $pattern, string $string, array &$matches = null): bool {}

/**
 * Regular expression match ignoring case with multibyte support
 * @link http://www.php.net/manual/en/function.mb-eregi.php
 * @param string $pattern The regular expression pattern.
 * @param string $string The string being searched.
 * @param array $matches [optional] <p>
 * If matches are found for parenthesized substrings of
 * pattern and the function is called with the
 * third argument matches, the matches will be stored
 * in the elements of the array matches.
 * If no matches are found, matches is set to an empty
 * array.
 * </p>
 * <p>
 * $matches[1] will contain the substring which starts at
 * the first left parenthesis; $matches[2] will contain
 * the substring starting at the second, and so on.
 * $matches[0] will contain a copy of the complete string
 * matched.
 * </p>
 * @return bool whether pattern matches string.
 */
function mb_eregi (string $pattern, string $string, array &$matches = null): bool {}

/**
 * Replace regular expression with multibyte support
 * @link http://www.php.net/manual/en/function.mb-ereg-replace.php
 * @param string $pattern <p>
 * The regular expression pattern.
 * </p>
 * <p>
 * Multibyte characters may be used in pattern.
 * </p>
 * @param string $replacement The replacement text.
 * @param string $string The string being checked.
 * @param mixed $options [optional] The search option. See mb_regex_set_options for explanation.
 * @return mixed The resultant string on success, or false on error.
 * If string is not valid for the current encoding, null
 * is returned.
 */
function mb_ereg_replace (string $pattern, string $replacement, string $string, $options = null): ?string|false|null {}

/**
 * Replace regular expression with multibyte support ignoring case
 * @link http://www.php.net/manual/en/function.mb-eregi-replace.php
 * @param string $pattern The regular expression pattern. Multibyte characters may be used. The case will be ignored.
 * @param string $replacement The replacement text.
 * @param string $string The searched string.
 * @param mixed $options [optional] The search option. See mb_regex_set_options for explanation.
 * @return mixed The resultant string or false on error. 
 * If string is not valid for the current encoding, null
 * is returned.
 */
function mb_eregi_replace (string $pattern, string $replacement, string $string, $options = null): ?string|false|null {}

/**
 * Perform a regular expression search and replace with multibyte support using a callback
 * @link http://www.php.net/manual/en/function.mb-ereg-replace-callback.php
 * @param string $pattern <p>
 * The regular expression pattern.
 * </p>
 * <p>
 * Multibyte characters may be used in pattern.
 * </p>
 * @param callable $callback <p>
 * A callback that will be called and passed an array of matched elements
 * in the subject string. The callback should
 * return the replacement string.
 * </p>
 * <p>
 * You'll often need the callback function
 * for a mb_ereg_replace_callback in just one place.
 * In this case you can use an
 * anonymous function to
 * declare the callback within the call to
 * mb_ereg_replace_callback. By doing it this way
 * you have all information for the call in one place and do not
 * clutter the function namespace with a callback function's name
 * not used anywhere else. 
 * </p>
 * @param string $string The string being checked.
 * @param mixed $options [optional] The search option. See mb_regex_set_options for explanation.
 * @return mixed The resultant string on success, or false on error.
 * If string is not valid for the current encoding, null
 * is returned.
 */
function mb_ereg_replace_callback (string $pattern, callable $callback, string $string, $options = null): ?string|false|null {}

/**
 * Split multibyte string using regular expression
 * @link http://www.php.net/manual/en/function.mb-split.php
 * @param string $pattern The regular expression pattern.
 * @param string $string The string being split.
 * @param int $limit [optional] If optional parameter limit is specified, 
 * it will be split in limit elements as
 * maximum.
 * @return mixed The result as an array, or false on failure.
 */
function mb_split (string $pattern, string $string, int $limit = null): array|false {}

/**
 * Regular expression match for multibyte string
 * @link http://www.php.net/manual/en/function.mb-ereg-match.php
 * @param string $pattern The regular expression pattern.
 * @param string $string The string being evaluated.
 * @param mixed $options [optional] The search option. See mb_regex_set_options for explanation.
 * @return bool 
 */
function mb_ereg_match (string $pattern, string $string, $options = null): bool {}

/**
 * Multibyte regular expression match for predefined multibyte string
 * @link http://www.php.net/manual/en/function.mb-ereg-search.php
 * @param mixed $pattern [optional] The search pattern.
 * @param mixed $options [optional] The search option. See mb_regex_set_options for explanation.
 * @return bool 
 */
function mb_ereg_search ($pattern = null, $options = null): bool {}

/**
 * Returns position and length of a matched part of the multibyte regular expression for a predefined multibyte string
 * @link http://www.php.net/manual/en/function.mb-ereg-search-pos.php
 * @param mixed $pattern [optional] The search pattern.
 * @param mixed $options [optional] The search option. See mb_regex_set_options for explanation.
 * @return mixed 
 */
function mb_ereg_search_pos ($pattern = null, $options = null): array|false {}

/**
 * Returns the matched part of a multibyte regular expression
 * @link http://www.php.net/manual/en/function.mb-ereg-search-regs.php
 * @param mixed $pattern [optional] The search pattern.
 * @param mixed $options [optional] The search option. See mb_regex_set_options for explanation.
 * @return mixed 
 */
function mb_ereg_search_regs ($pattern = null, $options = null): array|false {}

/**
 * Setup string and regular expression for a multibyte regular expression match
 * @link http://www.php.net/manual/en/function.mb-ereg-search-init.php
 * @param string $string The search string.
 * @param mixed $pattern [optional] The search pattern.
 * @param mixed $options [optional] The search option. See mb_regex_set_options for explanation.
 * @return bool 
 */
function mb_ereg_search_init (string $string, $pattern = null, $options = null): bool {}

/**
 * Retrieve the result from the last multibyte regular expression match
 * @link http://www.php.net/manual/en/function.mb-ereg-search-getregs.php
 * @return mixed 
 */
function mb_ereg_search_getregs (): array|false {}

/**
 * Returns start point for next regular expression match
 * @link http://www.php.net/manual/en/function.mb-ereg-search-getpos.php
 * @return int 
 */
function mb_ereg_search_getpos (): int {}

/**
 * Set start point of next regular expression match
 * @link http://www.php.net/manual/en/function.mb-ereg-search-setpos.php
 * @param int $offset The position to set. If it is negative, it counts from the end of the string.
 * @return bool 
 */
function mb_ereg_search_setpos (int $offset): bool {}

/**
 * Set/Get the default options for mbregex functions
 * @link http://www.php.net/manual/en/function.mb-regex-set-options.php
 * @param mixed $options [optional] <p>
 * The options to set. This is a string where each 
 * character is an option. To set a mode, the mode 
 * character must be the last one set, however there 
 * can only be set one mode but multiple options.
 * </p>
 * <table>
 * Regex options
 * <table>
 * <tr valign="top">
 * <td>Option</td>
 * <td>Meaning</td>
 * </tr>
 * <tr valign="top">
 * <td>i</td>
 * <td>Ambiguity match on</td>
 * </tr>
 * <tr valign="top">
 * <td>x</td>
 * <td>Enables extended pattern form</td>
 * </tr>
 * <tr valign="top">
 * <td>m</td>
 * <td>'.' matches with newlines</td>
 * </tr>
 * <tr valign="top">
 * <td>s</td>
 * <td>'^' -> '\A', '$' -> '\Z'</td>
 * </tr>
 * <tr valign="top">
 * <td>p</td>
 * <td>Same as both the m and s options</td>
 * </tr>
 * <tr valign="top">
 * <td>l</td>
 * <td>Finds longest matches</td>
 * </tr>
 * <tr valign="top">
 * <td>n</td>
 * <td>Ignores empty matches</td>
 * </tr>
 * <tr valign="top">
 * <td>e</td>
 * <td>eval resulting code</td>
 * </tr>
 * </table>
 * </table>
 * <table>
 * Regex syntax modes
 * <table>
 * <tr valign="top">
 * <td>Mode</td>
 * <td>Meaning</td>
 * </tr>
 * <tr valign="top">
 * <td>j</td>
 * <td>Java (Sun java.util.regex)</td>
 * </tr>
 * <tr valign="top">
 * <td>u</td>
 * <td>GNU regex</td>
 * </tr>
 * <tr valign="top">
 * <td>g</td>
 * <td>grep</td>
 * </tr>
 * <tr valign="top">
 * <td>c</td>
 * <td>Emacs</td>
 * </tr>
 * <tr valign="top">
 * <td>r</td>
 * <td>Ruby</td>
 * </tr>
 * <tr valign="top">
 * <td>z</td>
 * <td>Perl</td>
 * </tr>
 * <tr valign="top">
 * <td>b</td>
 * <td>POSIX Basic regex</td>
 * </tr>
 * <tr valign="top">
 * <td>d</td>
 * <td>POSIX Extended regex</td>
 * </tr>
 * </table>
 * </table>
 * @return string The previous options. If options is omitted or null, 
 * it returns the string that describes the current options.
 */
function mb_regex_set_options ($options = null): string {}


/**
 * Performs a full upper-case folding.
 * This may change the length of the string.
 * This is the mode used by mb_strtoupper().
 * @link http://www.php.net/manual/en/mbstring.constants.php
 */
define ('MB_CASE_UPPER', 0);

/**
 * Performs a full lower-case folding.
 * This may change the length of the string.
 * This is the mode used by mb_strtolower().
 * @link http://www.php.net/manual/en/mbstring.constants.php
 */
define ('MB_CASE_LOWER', 1);

/**
 * Performs a full title-case conversion based on the Cased and CaseIgnorable
 * derived Unicode properties.
 * In particular this improves handling of quotes and apostrophes.
 * This may change the length of the string.
 * @link http://www.php.net/manual/en/mbstring.constants.php
 */
define ('MB_CASE_TITLE', 2);

/**
 * Performs a full case fold conversion which removes case distinctions
 * present in the string.
 * This is used for caseless matching.
 * This may change the length of the string.
 * Available since PHP 7.3.
 * @link http://www.php.net/manual/en/mbstring.constants.php
 */
define ('MB_CASE_FOLD', 3);

/**
 * Performs simple upper-case fold conversion.
 * This does not change the length of the string.
 * Available as of PHP 7.3.
 * @link http://www.php.net/manual/en/mbstring.constants.php
 */
define ('MB_CASE_UPPER_SIMPLE', 4);

/**
 * Performs a simple lower-case fold conversion.
 * This does not change the length of the string.
 * Available as of PHP 7.3.
 * @link http://www.php.net/manual/en/mbstring.constants.php
 */
define ('MB_CASE_LOWER_SIMPLE', 5);

/**
 * Performs simple title-case fold conversion.
 * This does not change the length of the string.
 * Available as of PHP 7.3.
 * @link http://www.php.net/manual/en/mbstring.constants.php
 */
define ('MB_CASE_TITLE_SIMPLE', 6);

/**
 * Performs a simple case fold conversion which removes case distinctions
 * present in the string.
 * This is used for caseless matching.
 * This does not change the length of the string.
 * Used by case-insensitive operations internally by the MBString extension.
 * Available as of PHP 7.3.
 * @link http://www.php.net/manual/en/mbstring.constants.php
 */
define ('MB_CASE_FOLD_SIMPLE', 7);

/**
 * The Oniguruma version, e.g. 6.9.4.
 * Available as of PHP 7.4.
 * @link http://www.php.net/manual/en/mbstring.constants.php
 */
define ('MB_ONIGURUMA_VERSION', "6.9.8");

// End of mbstring v.8.1.19
