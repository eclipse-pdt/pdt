<?php

// Start of mbstring v.8.2.6

/**
 * Set/Get current language
 * @link http://www.php.net/manual/en/function.mb-language.php
 * @param string|null $language [optional] 
 * @return string|bool If language is set and
 * language is valid, it returns
 * true. Otherwise, it returns false. 
 * When language is omitted or null, it returns the language
 * name as a string.
 */
function mb_language (?string $language = null): string|bool {}

/**
 * Set/Get internal character encoding
 * @link http://www.php.net/manual/en/function.mb-internal-encoding.php
 * @param string|null $encoding [optional] 
 * @return string|bool If encoding is set, then 
 * Returns true on success or false on failure.
 * In this case, the character encoding for multibyte regex is NOT changed.
 * If encoding is omitted, then 
 * the current character encoding name is returned.
 */
function mb_internal_encoding (?string $encoding = null): string|bool {}

/**
 * Detect HTTP input character encoding
 * @link http://www.php.net/manual/en/function.mb-http-input.php
 * @param string|null $type [optional] 
 * @return array|string|false The character encoding name, as per the type,
 * or an array of character encoding names, if type is "I".
 * If mb_http_input does not process specified
 * HTTP input, it returns false.
 */
function mb_http_input (?string $type = null): array|string|false {}

/**
 * Set/Get HTTP output character encoding
 * @link http://www.php.net/manual/en/function.mb-http-output.php
 * @param string|null $encoding [optional] 
 * @return string|bool If encoding is omitted,
 * mb_http_output returns the current HTTP output
 * character encoding. Otherwise, 
 * Returns true on success or false on failure.
 */
function mb_http_output (?string $encoding = null): string|bool {}

/**
 * Set/Get character encoding detection order
 * @link http://www.php.net/manual/en/function.mb-detect-order.php
 * @param array|string|null $encoding [optional] 
 * @return array|bool When setting the encoding detection order, true is returned on success or false on failure.
 * <p>When getting the encoding detection order, an ordered array of the encodings is returned.</p>
 */
function mb_detect_order (array|string|null $encoding = null): array|bool {}

/**
 * Set/Get substitution character
 * @link http://www.php.net/manual/en/function.mb-substitute-character.php
 * @param string|int|null $substitute_character [optional] 
 * @return string|int|bool If substitute_character is set, it returns true for success,
 * otherwise returns false. 
 * If substitute_character is not set, it returns the current
 * setting.
 */
function mb_substitute_character (string|int|null $substitute_character = null): string|int|bool {}

/**
 * Get MIME charset string
 * @link http://www.php.net/manual/en/function.mb-preferred-mime-name.php
 * @param string $encoding 
 * @return string|false The MIME charset string for character encoding
 * encoding,
 * or false if no charset is preferred for the given encoding.
 */
function mb_preferred_mime_name (string $encoding): string|false {}

/**
 * Parse GET/POST/COOKIE data and set global variable
 * @link http://www.php.net/manual/en/function.mb-parse-str.php
 * @param string $string 
 * @param array $result 
 * @return bool Returns true on success or false on failure.
 */
function mb_parse_str (string $string, array &$result): bool {}

/**
 * Callback function converts character encoding in output buffer
 * @link http://www.php.net/manual/en/function.mb-output-handler.php
 * @param string $string 
 * @param int $status 
 * @return string The converted string.
 */
function mb_output_handler (string $string, int $status): string {}

/**
 * Given a multibyte string, return an array of its characters
 * @link http://www.php.net/manual/en/function.mb-str-split.php
 * @param string $string 
 * @param int $length [optional] 
 * @param string|null $encoding [optional] 
 * @return array mb_str_split returns an array of strings.
 */
function mb_str_split (string $string, int $length = 1, ?string $encoding = null): array {}

/**
 * Get string length
 * @link http://www.php.net/manual/en/function.mb-strlen.php
 * @param string $string 
 * @param string|null $encoding [optional] 
 * @return int Returns the number of characters in
 * string string having character encoding
 * encoding. A multi-byte character is
 * counted as 1.
 */
function mb_strlen (string $string, ?string $encoding = null): int {}

/**
 * Find position of first occurrence of string in a string
 * @link http://www.php.net/manual/en/function.mb-strpos.php
 * @param string $haystack 
 * @param string $needle 
 * @param int $offset [optional] 
 * @param string|null $encoding [optional] 
 * @return int|false Returns the numeric position of
 * the first occurrence of needle in the
 * haystack string. If
 * needle is not found, it returns false.
 */
function mb_strpos (string $haystack, string $needle, int $offset = null, ?string $encoding = null): int|false {}

/**
 * Find position of last occurrence of a string in a string
 * @link http://www.php.net/manual/en/function.mb-strrpos.php
 * @param string $haystack 
 * @param string $needle 
 * @param int $offset [optional] 
 * @param string|null $encoding [optional] 
 * @return int|false Returns the numeric position of
 * the last occurrence of needle in the
 * haystack string. If
 * needle is not found, it returns false.
 */
function mb_strrpos (string $haystack, string $needle, int $offset = null, ?string $encoding = null): int|false {}

/**
 * Finds position of first occurrence of a string within another, case insensitive
 * @link http://www.php.net/manual/en/function.mb-stripos.php
 * @param string $haystack 
 * @param string $needle 
 * @param int $offset [optional] 
 * @param string|null $encoding [optional] 
 * @return int|false Return the numeric position of the first occurrence of
 * needle in the haystack
 * string, or false if needle is not found.
 */
function mb_stripos (string $haystack, string $needle, int $offset = null, ?string $encoding = null): int|false {}

/**
 * Finds position of last occurrence of a string within another, case insensitive
 * @link http://www.php.net/manual/en/function.mb-strripos.php
 * @param string $haystack 
 * @param string $needle 
 * @param int $offset [optional] 
 * @param string|null $encoding [optional] 
 * @return int|false Return the numeric position of
 * the last occurrence of needle in the
 * haystack string, or false
 * if needle is not found.
 */
function mb_strripos (string $haystack, string $needle, int $offset = null, ?string $encoding = null): int|false {}

/**
 * Finds first occurrence of a string within another
 * @link http://www.php.net/manual/en/function.mb-strstr.php
 * @param string $haystack 
 * @param string $needle 
 * @param bool $before_needle [optional] 
 * @param string|null $encoding [optional] 
 * @return string|false Returns the portion of haystack,
 * or false if needle is not found.
 */
function mb_strstr (string $haystack, string $needle, bool $before_needle = false, ?string $encoding = null): string|false {}

/**
 * Finds the last occurrence of a character in a string within another
 * @link http://www.php.net/manual/en/function.mb-strrchr.php
 * @param string $haystack 
 * @param string $needle 
 * @param bool $before_needle [optional] 
 * @param string|null $encoding [optional] 
 * @return string|false Returns the portion of haystack.
 * or false if needle is not found.
 */
function mb_strrchr (string $haystack, string $needle, bool $before_needle = false, ?string $encoding = null): string|false {}

/**
 * Finds first occurrence of a string within another, case insensitive
 * @link http://www.php.net/manual/en/function.mb-stristr.php
 * @param string $haystack 
 * @param string $needle 
 * @param bool $before_needle [optional] 
 * @param string|null $encoding [optional] 
 * @return string|false Returns the portion of haystack,
 * or false if needle is not found.
 */
function mb_stristr (string $haystack, string $needle, bool $before_needle = false, ?string $encoding = null): string|false {}

/**
 * Finds the last occurrence of a character in a string within another, case insensitive
 * @link http://www.php.net/manual/en/function.mb-strrichr.php
 * @param string $haystack 
 * @param string $needle 
 * @param bool $before_needle [optional] 
 * @param string|null $encoding [optional] 
 * @return string|false Returns the portion of haystack.
 * or false if needle is not found.
 */
function mb_strrichr (string $haystack, string $needle, bool $before_needle = false, ?string $encoding = null): string|false {}

/**
 * Count the number of substring occurrences
 * @link http://www.php.net/manual/en/function.mb-substr-count.php
 * @param string $haystack 
 * @param string $needle 
 * @param string|null $encoding [optional] 
 * @return int The number of times the
 * needle substring occurs in the
 * haystack string.
 */
function mb_substr_count (string $haystack, string $needle, ?string $encoding = null): int {}

/**
 * Get part of string
 * @link http://www.php.net/manual/en/function.mb-substr.php
 * @param string $string 
 * @param int $start 
 * @param int|null $length [optional] 
 * @param string|null $encoding [optional] 
 * @return string mb_substr returns the portion of
 * string specified by the
 * start and
 * length parameters.
 */
function mb_substr (string $string, int $start, ?int $length = null, ?string $encoding = null): string {}

/**
 * Get part of string
 * @link http://www.php.net/manual/en/function.mb-strcut.php
 * @param string $string 
 * @param int $start 
 * @param int|null $length [optional] 
 * @param string|null $encoding [optional] 
 * @return string mb_strcut returns the portion of
 * string specified by the
 * start and
 * length parameters.
 */
function mb_strcut (string $string, int $start, ?int $length = null, ?string $encoding = null): string {}

/**
 * Return width of string
 * @link http://www.php.net/manual/en/function.mb-strwidth.php
 * @param string $string 
 * @param string|null $encoding [optional] 
 * @return int The width of string string.
 */
function mb_strwidth (string $string, ?string $encoding = null): int {}

/**
 * Get truncated string with specified width
 * @link http://www.php.net/manual/en/function.mb-strimwidth.php
 * @param string $string 
 * @param int $start 
 * @param int $width 
 * @param string $trim_marker [optional] 
 * @param string|null $encoding [optional] 
 * @return string The truncated string. If trim_marker is set,
 * trim_marker replaces the last chars to match the width.
 */
function mb_strimwidth (string $string, int $start, int $width, string $trim_marker = '""', ?string $encoding = null): string {}

/**
 * Convert a string from one character encoding to another
 * @link http://www.php.net/manual/en/function.mb-convert-encoding.php
 * @param array|string $string 
 * @param string $to_encoding 
 * @param array|string|null $from_encoding [optional] 
 * @return array|string|false The encoded string or array on success, or false on failure.
 */
function mb_convert_encoding (array|string $string, string $to_encoding, array|string|null $from_encoding = null): array|string|false {}

/**
 * Perform case folding on a string
 * @link http://www.php.net/manual/en/function.mb-convert-case.php
 * @param string $string 
 * @param int $mode 
 * @param string|null $encoding [optional] 
 * @return string A case folded version of string converted in the
 * way specified by mode.
 */
function mb_convert_case (string $string, int $mode, ?string $encoding = null): string {}

/**
 * Make a string uppercase
 * @link http://www.php.net/manual/en/function.mb-strtoupper.php
 * @param string $string 
 * @param string|null $encoding [optional] 
 * @return string string with all alphabetic characters converted to uppercase.
 */
function mb_strtoupper (string $string, ?string $encoding = null): string {}

/**
 * Make a string lowercase
 * @link http://www.php.net/manual/en/function.mb-strtolower.php
 * @param string $string 
 * @param string|null $encoding [optional] 
 * @return string string with all alphabetic characters converted to lowercase.
 */
function mb_strtolower (string $string, ?string $encoding = null): string {}

/**
 * Detect character encoding
 * @link http://www.php.net/manual/en/function.mb-detect-encoding.php
 * @param string $string 
 * @param array|string|null $encodings [optional] 
 * @param bool $strict [optional] 
 * @return string|false The detected character encoding, or false if the string is not valid
 * in any of the listed encodings.
 */
function mb_detect_encoding (string $string, array|string|null $encodings = null, bool $strict = false): string|false {}

/**
 * Returns an array of all supported encodings
 * @link http://www.php.net/manual/en/function.mb-list-encodings.php
 * @return array Returns a numerically indexed array.
 */
function mb_list_encodings (): array {}

/**
 * Get aliases of a known encoding type
 * @link http://www.php.net/manual/en/function.mb-encoding-aliases.php
 * @param string $encoding The encoding type being checked, for aliases.
 * @return array Returns a numerically indexed array of encoding aliases.
 */
function mb_encoding_aliases (string $encoding): array {}

/**
 * Encode string for MIME header
 * @link http://www.php.net/manual/en/function.mb-encode-mimeheader.php
 * @param string $string 
 * @param string|null $charset [optional] 
 * @param string|null $transfer_encoding [optional] 
 * @param string $newline [optional] 
 * @param int $indent [optional] 
 * @return string A converted version of the string represented in ASCII.
 */
function mb_encode_mimeheader (string $string, ?string $charset = null, ?string $transfer_encoding = null, string $newline = '"\\r\\n"', int $indent = null): string {}

/**
 * Decode string in MIME header field
 * @link http://www.php.net/manual/en/function.mb-decode-mimeheader.php
 * @param string $string 
 * @return string The decoded string in internal character encoding.
 */
function mb_decode_mimeheader (string $string): string {}

/**
 * Convert "kana" one from another ("zen-kaku", "han-kaku" and more)
 * @link http://www.php.net/manual/en/function.mb-convert-kana.php
 * @param string $string 
 * @param string $mode [optional] 
 * @param string|null $encoding [optional] 
 * @return string The converted string.
 */
function mb_convert_kana (string $string, string $mode = '"KV"', ?string $encoding = null): string {}

/**
 * Convert character code in variable(s)
 * @link http://www.php.net/manual/en/function.mb-convert-variables.php
 * @param string $to_encoding 
 * @param array|string $from_encoding 
 * @param mixed $var 
 * @param mixed $vars 
 * @return string|false The character encoding before conversion for success, 
 * or false for failure.
 */
function mb_convert_variables (string $to_encoding, array|string $from_encoding, mixed &$var, mixed &...$vars): string|false {}

/**
 * Encode character to HTML numeric string reference
 * @link http://www.php.net/manual/en/function.mb-encode-numericentity.php
 * @param string $string 
 * @param array $map 
 * @param string|null $encoding [optional] 
 * @param bool $hex [optional] 
 * @return string The converted string.
 */
function mb_encode_numericentity (string $string, array $map, ?string $encoding = null, bool $hex = false): string {}

/**
 * Decode HTML numeric string reference to character
 * @link http://www.php.net/manual/en/function.mb-decode-numericentity.php
 * @param string $string 
 * @param array $map 
 * @param string|null $encoding [optional] 
 * @return string The converted string.
 */
function mb_decode_numericentity (string $string, array $map, ?string $encoding = null): string {}

/**
 * Send encoded mail
 * @link http://www.php.net/manual/en/function.mb-send-mail.php
 * @param string $to 
 * @param string $subject 
 * @param string $message 
 * @param array|string $additional_headers [optional] 
 * @param string|null $additional_params [optional] 
 * @return bool Returns true on success or false on failure.
 */
function mb_send_mail (string $to, string $subject, string $message, array|string $additional_headers = '[]', ?string $additional_params = null): bool {}

/**
 * Get internal settings of mbstring
 * @link http://www.php.net/manual/en/function.mb-get-info.php
 * @param string $type [optional] 
 * @return array|string|int|false An array of type information if type 
 * is not specified, otherwise a specific type,
 * or false on failure.
 */
function mb_get_info (string $type = '"all"'): array|string|int|false {}

/**
 * Check if strings are valid for the specified encoding
 * @link http://www.php.net/manual/en/function.mb-check-encoding.php
 * @param array|string|null $value [optional] 
 * @param string|null $encoding [optional] 
 * @return bool Returns true on success or false on failure.
 */
function mb_check_encoding (array|string|null $value = null, ?string $encoding = null): bool {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.mb-scrub.php
 * @param string $string 
 * @param string|null $encoding [optional] 
 * @return string 
 */
function mb_scrub (string $string, ?string $encoding = null): string {}

/**
 * Get Unicode code point of character
 * @link http://www.php.net/manual/en/function.mb-ord.php
 * @param string $string A string
 * @param string|null $encoding [optional] >The encoding
 * parameter is the character encoding. If it is omitted or null, the internal character
 * encoding value will be used.
 * @return int|false The Unicode code point for the first character of string or false on failure.
 */
function mb_ord (string $string, ?string $encoding = null): int|false {}

/**
 * Return character by Unicode code point value
 * @link http://www.php.net/manual/en/function.mb-chr.php
 * @param int $codepoint A Unicode codepoint value, e.g. 128024 for U+1F418 ELEPHANT
 * @param string|null $encoding [optional] >The encoding
 * parameter is the character encoding. If it is omitted or null, the internal character
 * encoding value will be used.
 * @return string|false A string containing the requested character, if it can be represented in the specified
 * encoding or false on failure.
 */
function mb_chr (int $codepoint, ?string $encoding = null): string|false {}

/**
 * Set/Get character encoding for multibyte regex
 * @link http://www.php.net/manual/en/function.mb-regex-encoding.php
 * @param string|null $encoding [optional] 
 * @return string|bool If encoding is set, then 
 * Returns true on success or false on failure. 
 * In this case, the internal character encoding is NOT changed.
 * If encoding is omitted, then 
 * the current character encoding name for a multibyte regex is returned.
 */
function mb_regex_encoding (?string $encoding = null): string|bool {}

/**
 * Regular expression match with multibyte support
 * @link http://www.php.net/manual/en/function.mb-ereg.php
 * @param string $pattern 
 * @param string $string 
 * @param array $matches [optional] 
 * @return bool Returns whether pattern matches string.
 */
function mb_ereg (string $pattern, string $string, array &$matches = null): bool {}

/**
 * Regular expression match ignoring case with multibyte support
 * @link http://www.php.net/manual/en/function.mb-eregi.php
 * @param string $pattern 
 * @param string $string 
 * @param array $matches [optional] 
 * @return bool Returns whether pattern matches string.
 */
function mb_eregi (string $pattern, string $string, array &$matches = null): bool {}

/**
 * Replace regular expression with multibyte support
 * @link http://www.php.net/manual/en/function.mb-ereg-replace.php
 * @param string $pattern 
 * @param string $replacement 
 * @param string $string 
 * @param string|null $options [optional] 
 * @return string|false|null The resultant string on success, or false on error.
 * If string is not valid for the current encoding, null
 * is returned.
 */
function mb_ereg_replace (string $pattern, string $replacement, string $string, ?string $options = null): string|false|null {}

/**
 * Replace regular expression with multibyte support ignoring case
 * @link http://www.php.net/manual/en/function.mb-eregi-replace.php
 * @param string $pattern 
 * @param string $replacement 
 * @param string $string 
 * @param string|null $options [optional] 
 * @return string|false|null The resultant string or false on error. 
 * If string is not valid for the current encoding, null
 * is returned.
 */
function mb_eregi_replace (string $pattern, string $replacement, string $string, ?string $options = null): string|false|null {}

/**
 * Perform a regular expression search and replace with multibyte support using a callback
 * @link http://www.php.net/manual/en/function.mb-ereg-replace-callback.php
 * @param string $pattern 
 * @param callable $callback 
 * @param string $string 
 * @param string|null $options [optional] 
 * @return string|false|null The resultant string on success, or false on error.
 * If string is not valid for the current encoding, null
 * is returned.
 */
function mb_ereg_replace_callback (string $pattern, callable $callback, string $string, ?string $options = null): string|false|null {}

/**
 * Split multibyte string using regular expression
 * @link http://www.php.net/manual/en/function.mb-split.php
 * @param string $pattern 
 * @param string $string 
 * @param int $limit [optional] 
 * @return array|false The result as an array, or false on failure.
 */
function mb_split (string $pattern, string $string, int $limit = -1): array|false {}

/**
 * Regular expression match for multibyte string
 * @link http://www.php.net/manual/en/function.mb-ereg-match.php
 * @param string $pattern 
 * @param string $string 
 * @param string|null $options [optional] 
 * @return bool Returns true if
 * string matches the regular expression
 * pattern, false if not.
 */
function mb_ereg_match (string $pattern, string $string, ?string $options = null): bool {}

/**
 * Multibyte regular expression match for predefined multibyte string
 * @link http://www.php.net/manual/en/function.mb-ereg-search.php
 * @param string|null $pattern [optional] 
 * @param string|null $options [optional] 
 * @return bool mb_ereg_search returns true if the
 * multibyte string matches with the regular expression, or false
 * otherwise. The string for matching is set by 
 * mb_ereg_search_init. If
 * pattern is not specified, the previous one
 * is used.
 */
function mb_ereg_search (?string $pattern = null, ?string $options = null): bool {}

/**
 * Returns position and length of a matched part of the multibyte regular expression for a predefined multibyte string
 * @link http://www.php.net/manual/en/function.mb-ereg-search-pos.php
 * @param string|null $pattern [optional] 
 * @param string|null $options [optional] 
 * @return array|false An array containing two elements. The first element is the
 * offset, in bytes, where the match begins relative to the start of the
 * search string, and the second element is the length in bytes of the match.
 * If an error occurs, false is returned.
 */
function mb_ereg_search_pos (?string $pattern = null, ?string $options = null): array|false {}

/**
 * Returns the matched part of a multibyte regular expression
 * @link http://www.php.net/manual/en/function.mb-ereg-search-regs.php
 * @param string|null $pattern [optional] 
 * @param string|null $options [optional] 
 * @return array|false mb_ereg_search_regs executes the multibyte
 * regular expression match, and if there are some matched part, it
 * returns an array including substring of matched part as first
 * element, the first grouped part with brackets as second element,
 * the second grouped part as third element, and so on. 
 * It returns false on error.
 */
function mb_ereg_search_regs (?string $pattern = null, ?string $options = null): array|false {}

/**
 * Setup string and regular expression for a multibyte regular expression match
 * @link http://www.php.net/manual/en/function.mb-ereg-search-init.php
 * @param string $string 
 * @param string|null $pattern [optional] 
 * @param string|null $options [optional] 
 * @return bool Returns true on success or false on failure.
 */
function mb_ereg_search_init (string $string, ?string $pattern = null, ?string $options = null): bool {}

/**
 * Retrieve the result from the last multibyte regular expression match
 * @link http://www.php.net/manual/en/function.mb-ereg-search-getregs.php
 * @return array|false An array
 * including the sub-string of matched part by last
 * mb_ereg_search,
 * mb_ereg_search_pos,
 * mb_ereg_search_regs. If there are some
 * matches, the first element will have the matched sub-string, the
 * second element will have the first part grouped with brackets, 
 * the third element will have the second part grouped with
 * brackets, and so on. It returns false on error.
 */
function mb_ereg_search_getregs (): array|false {}

/**
 * Returns start point for next regular expression match
 * @link http://www.php.net/manual/en/function.mb-ereg-search-getpos.php
 * @return int mb_ereg_search_getpos returns
 * the point to start regular expression match for
 * mb_ereg_search,
 * mb_ereg_search_pos,
 * mb_ereg_search_regs. The position is
 * represented by bytes from the head of string.
 */
function mb_ereg_search_getpos (): int {}

/**
 * Set start point of next regular expression match
 * @link http://www.php.net/manual/en/function.mb-ereg-search-setpos.php
 * @param int $offset 
 * @return bool Returns true on success or false on failure.
 */
function mb_ereg_search_setpos (int $offset): bool {}

/**
 * Set/Get the default options for mbregex functions
 * @link http://www.php.net/manual/en/function.mb-regex-set-options.php
 * @param string|null $options [optional] 
 * @return string The previous options. If options is omitted or null, 
 * it returns the string that describes the current options.
 */
function mb_regex_set_options (?string $options = null): string {}


/**
 * The Oniguruma version, e.g. 6.9.4.
 * Available as of PHP 7.4.
 * @link http://www.php.net/manual/en/mbstring.constants.php
 * @var string
 */
define ('MB_ONIGURUMA_VERSION', "6.9.8");

/**
 * Performs a full upper-case folding.
 * This may change the length of the string.
 * This is the mode used by mb_strtoupper().
 * @link http://www.php.net/manual/en/mbstring.constants.php
 * @var int
 */
define ('MB_CASE_UPPER', 0);

/**
 * Performs a full lower-case folding.
 * This may change the length of the string.
 * This is the mode used by mb_strtolower().
 * @link http://www.php.net/manual/en/mbstring.constants.php
 * @var int
 */
define ('MB_CASE_LOWER', 1);

/**
 * Performs a full title-case conversion based on the Cased and CaseIgnorable
 * derived Unicode properties.
 * In particular this improves handling of quotes and apostrophes.
 * This may change the length of the string.
 * @link http://www.php.net/manual/en/mbstring.constants.php
 * @var int
 */
define ('MB_CASE_TITLE', 2);

/**
 * Performs a full case fold conversion which removes case distinctions
 * present in the string.
 * This is used for caseless matching.
 * This may change the length of the string.
 * Available since PHP 7.3.
 * @link http://www.php.net/manual/en/mbstring.constants.php
 * @var int
 */
define ('MB_CASE_FOLD', 3);

/**
 * Performs simple upper-case fold conversion.
 * This does not change the length of the string.
 * Available as of PHP 7.3.
 * @link http://www.php.net/manual/en/mbstring.constants.php
 * @var int
 */
define ('MB_CASE_UPPER_SIMPLE', 4);

/**
 * Performs a simple lower-case fold conversion.
 * This does not change the length of the string.
 * Available as of PHP 7.3.
 * @link http://www.php.net/manual/en/mbstring.constants.php
 * @var int
 */
define ('MB_CASE_LOWER_SIMPLE', 5);

/**
 * Performs simple title-case fold conversion.
 * This does not change the length of the string.
 * Available as of PHP 7.3.
 * @link http://www.php.net/manual/en/mbstring.constants.php
 * @var int
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
 * @var int
 */
define ('MB_CASE_FOLD_SIMPLE', 7);

// End of mbstring v.8.2.6
