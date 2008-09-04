<?php

// Start of mbstring v.

/**
 * Perform case folding on a string
 * @link http://php.net/manual/en/function.mb-convert-case.php
 * @param str string
 * @param mode int
 * @param encoding string[optional]
 * @return string 
 */
function mb_convert_case ($str, $mode, $encoding = null) {}

/**
 * Make a string uppercase
 * @link http://php.net/manual/en/function.mb-strtoupper.php
 * @param str string
 * @param encoding string[optional]
 * @return string 
 */
function mb_strtoupper ($str, $encoding = null) {}

/**
 * Make a string lowercase
 * @link http://php.net/manual/en/function.mb-strtolower.php
 * @param str string
 * @param encoding string[optional]
 * @return string 
 */
function mb_strtolower ($str, $encoding = null) {}

/**
 * Set/Get current language
 * @link http://php.net/manual/en/function.mb-language.php
 * @param language string[optional]
 * @return mixed 
 */
function mb_language ($language = null) {}

/**
 * Set/Get internal character encoding
 * @link http://php.net/manual/en/function.mb-internal-encoding.php
 * @param encoding string[optional]
 * @return mixed 
 */
function mb_internal_encoding ($encoding = null) {}

/**
 * Detect HTTP input character encoding
 * @link http://php.net/manual/en/function.mb-http-input.php
 * @param type string[optional]
 * @return mixed 
 */
function mb_http_input ($type = null) {}

/**
 * Set/Get HTTP output character encoding
 * @link http://php.net/manual/en/function.mb-http-output.php
 * @param encoding string[optional]
 * @return mixed 
 */
function mb_http_output ($encoding = null) {}

/**
 * Set/Get character encoding detection order
 * @link http://php.net/manual/en/function.mb-detect-order.php
 * @param encoding_list mixed[optional]
 * @return mixed 
 */
function mb_detect_order ($encoding_list = null) {}

/**
 * Set/Get substitution character
 * @link http://php.net/manual/en/function.mb-substitute-character.php
 * @param substrchar mixed[optional]
 * @return mixed 
 */
function mb_substitute_character ($substrchar = null) {}

/**
 * Parse GET/POST/COOKIE data and set global variable
 * @link http://php.net/manual/en/function.mb-parse-str.php
 * @param encoded_string string
 * @param result array[optional]
 * @return bool 
 */
function mb_parse_str ($encoded_string, array &$result = null) {}

/**
 * Callback function converts character encoding in output buffer
 * @link http://php.net/manual/en/function.mb-output-handler.php
 * @param contents string
 * @param status int
 * @return string 
 */
function mb_output_handler ($contents, $status) {}

/**
 * Get MIME charset string
 * @link http://php.net/manual/en/function.mb-preferred-mime-name.php
 * @param encoding string
 * @return string 
 */
function mb_preferred_mime_name ($encoding) {}

/**
 * Get string length
 * @link http://php.net/manual/en/function.mb-strlen.php
 * @param str string
 * @param encoding string[optional]
 * @return int the number of characters in
 */
function mb_strlen ($str, $encoding = null) {}

/**
 * Find position of first occurrence of string in a string
 * @link http://php.net/manual/en/function.mb-strpos.php
 * @param haystack string
 * @param needle string
 * @param offset int[optional]
 * @param encoding string[optional]
 * @return int the numeric position of
 */
function mb_strpos ($haystack, $needle, $offset = null, $encoding = null) {}

/**
 * Find position of last occurrence of a string in a string
 * @link http://php.net/manual/en/function.mb-strrpos.php
 * @param haystack string
 * @param needle string
 * @param offset int[optional]
 * @param encoding string[optional]
 * @return int the numeric position of
 */
function mb_strrpos ($haystack, $needle, $offset = null, $encoding = null) {}

/**
 * Finds position of first occurrence of a string within another, case insensitive
 * @link http://php.net/manual/en/function.mb-stripos.php
 * @param haystack string
 * @param needle string
 * @param offset int[optional]
 * @param encoding string[optional]
 * @return int 
 */
function mb_stripos ($haystack, $needle, $offset = null, $encoding = null) {}

/**
 * Finds position of last occurrence of a string within another, case insensitive
 * @link http://php.net/manual/en/function.mb-strripos.php
 * @param haystack string
 * @param needle string
 * @param offset int[optional]
 * @param encoding string[optional]
 * @return int 
 */
function mb_strripos ($haystack, $needle, $offset = null, $encoding = null) {}

/**
 * Finds first occurrence of a string within another
 * @link http://php.net/manual/en/function.mb-strstr.php
 * @param haystack string
 * @param needle string
 * @param part bool[optional]
 * @param encoding string[optional]
 * @return string the portion of haystack,
 */
function mb_strstr ($haystack, $needle, $part = null, $encoding = null) {}

/**
 * Finds the last occurrence of a character in a string within another
 * @link http://php.net/manual/en/function.mb-strrchr.php
 * @param haystack string
 * @param needle string
 * @param part bool[optional]
 * @param encoding string[optional]
 * @return string the portion of haystack.
 */
function mb_strrchr ($haystack, $needle, $part = null, $encoding = null) {}

/**
 * Finds first occurrence of a string within another, case insensitive
 * @link http://php.net/manual/en/function.mb-stristr.php
 * @param haystack string
 * @param needle string
 * @param part bool[optional]
 * @param encoding string[optional]
 * @return string the portion of haystack,
 */
function mb_stristr ($haystack, $needle, $part = null, $encoding = null) {}

/**
 * Finds the last occurrence of a character in a string within another, case insensitive
 * @link http://php.net/manual/en/function.mb-strrichr.php
 * @param haystack string
 * @param needle string
 * @param part bool[optional]
 * @param encoding string[optional]
 * @return string the portion of haystack.
 */
function mb_strrichr ($haystack, $needle, $part = null, $encoding = null) {}

/**
 * Count the number of substring occurrences
 * @link http://php.net/manual/en/function.mb-substr-count.php
 * @param haystack string
 * @param needle string
 * @param encoding string[optional]
 * @return int 
 */
function mb_substr_count ($haystack, $needle, $encoding = null) {}

/**
 * Get part of string
 * @link http://php.net/manual/en/function.mb-substr.php
 * @param str string
 * @param start int
 * @param length int[optional]
 * @param encoding string[optional]
 * @return string 
 */
function mb_substr ($str, $start, $length = null, $encoding = null) {}

/**
 * Get part of string
 * @link http://php.net/manual/en/function.mb-strcut.php
 * @param str string
 * @param start int
 * @param length int[optional]
 * @param encoding string[optional]
 * @return string 
 */
function mb_strcut ($str, $start, $length = null, $encoding = null) {}

/**
 * Return width of string
 * @link http://php.net/manual/en/function.mb-strwidth.php
 * @param str string
 * @param encoding string[optional]
 * @return int 
 */
function mb_strwidth ($str, $encoding = null) {}

/**
 * Get truncated string with specified width
 * @link http://php.net/manual/en/function.mb-strimwidth.php
 * @param str string
 * @param start int
 * @param width int
 * @param trimmarker string[optional]
 * @param encoding string[optional]
 * @return string 
 */
function mb_strimwidth ($str, $start, $width, $trimmarker = null, $encoding = null) {}

/**
 * Convert character encoding
 * @link http://php.net/manual/en/function.mb-convert-encoding.php
 * @param str string
 * @param to_encoding string
 * @param from_encoding mixed[optional]
 * @return string 
 */
function mb_convert_encoding ($str, $to_encoding, $from_encoding = null) {}

/**
 * Detect character encoding
 * @link http://php.net/manual/en/function.mb-detect-encoding.php
 * @param str string
 * @param encoding_list mixed[optional]
 * @param strict bool[optional]
 * @return string 
 */
function mb_detect_encoding ($str, $encoding_list = null, $strict = null) {}

/**
 * Returns an array of all supported encodings
 * @link http://php.net/manual/en/function.mb-list-encodings.php
 * @return array a numerically indexed array.
 */
function mb_list_encodings () {}

/**
 * Convert "kana" one from another ("zen-kaku", "han-kaku" and more)
 * @link http://php.net/manual/en/function.mb-convert-kana.php
 * @param str string
 * @param option string[optional]
 * @param encoding string[optional]
 * @return string 
 */
function mb_convert_kana ($str, $option = null, $encoding = null) {}

/**
 * Encode string for MIME header
 * @link http://php.net/manual/en/function.mb-encode-mimeheader.php
 * @param str string
 * @param charset string[optional]
 * @param transfer_encoding string[optional]
 * @param linefeed string[optional]
 * @param indent int[optional]
 * @return string 
 */
function mb_encode_mimeheader ($str, $charset = null, $transfer_encoding = null, $linefeed = null, $indent = null) {}

/**
 * Decode string in MIME header field
 * @link http://php.net/manual/en/function.mb-decode-mimeheader.php
 * @param str string
 * @return string 
 */
function mb_decode_mimeheader ($str) {}

/**
 * Convert character code in variable(s)
 * @link http://php.net/manual/en/function.mb-convert-variables.php
 * @param to_encoding string
 * @param from_encoding mixed
 * @param vars mixed
 * @param _ mixed[optional]
 * @return string 
 */
function mb_convert_variables ($to_encoding, $from_encoding, &$vars, &$_ = null) {}

/**
 * Encode character to HTML numeric string reference
 * @link http://php.net/manual/en/function.mb-encode-numericentity.php
 * @param str string
 * @param convmap array
 * @param encoding string[optional]
 * @return string 
 */
function mb_encode_numericentity ($str, array $convmap, $encoding = null) {}

/**
 * Decode HTML numeric string reference to character
 * @link http://php.net/manual/en/function.mb-decode-numericentity.php
 * @param str string
 * @param convmap array
 * @param encoding string[optional]
 * @return string 
 */
function mb_decode_numericentity ($str, array $convmap, $encoding = null) {}

/**
 * Send encoded mail
 * @link http://php.net/manual/en/function.mb-send-mail.php
 * @param to string
 * @param subject string
 * @param message string
 * @param additional_headers string[optional]
 * @param additional_parameter string[optional]
 * @return bool 
 */
function mb_send_mail ($to, $subject, $message, $additional_headers = null, $additional_parameter = null) {}

/**
 * Get internal settings of mbstring
 * @link http://php.net/manual/en/function.mb-get-info.php
 * @param type string[optional]
 * @return mixed 
 */
function mb_get_info ($type = null) {}

/**
 * Check if the string is valid for the specified encoding
 * @link http://php.net/manual/en/function.mb-check-encoding.php
 * @param var string[optional]
 * @param encoding string[optional]
 * @return bool 
 */
function mb_check_encoding ($var = null, $encoding = null) {}

/**
 * Returns current encoding for multibyte regex as string
 * @link http://php.net/manual/en/function.mb-regex-encoding.php
 * @param encoding string[optional]
 * @return mixed 
 */
function mb_regex_encoding ($encoding = null) {}

/**
 * Set/Get the default options for mbregex functions
 * @link http://php.net/manual/en/function.mb-regex-set-options.php
 * @param options string[optional]
 * @return string 
 */
function mb_regex_set_options ($options = null) {}

/**
 * Regular expression match with multibyte support
 * @link http://php.net/manual/en/function.mb-ereg.php
 * @param pattern string
 * @param string string
 * @param regs array[optional]
 * @return int 
 */
function mb_ereg ($pattern, $string, array $regs = null) {}

/**
 * Regular expression match ignoring case with multibyte support
 * @link http://php.net/manual/en/function.mb-eregi.php
 * @param pattern string
 * @param string string
 * @param regs array[optional]
 * @return int 
 */
function mb_eregi ($pattern, $string, array $regs = null) {}

/**
 * Replace regular expression with multibyte support
 * @link http://php.net/manual/en/function.mb-ereg-replace.php
 * @param pattern string
 * @param replacement string
 * @param string string
 * @param option string[optional]
 * @return string 
 */
function mb_ereg_replace ($pattern, $replacement, $string, $option = null) {}

/**
 * Replace regular expression with multibyte support ignoring case
 * @link http://php.net/manual/en/function.mb-eregi-replace.php
 * @param pattern string
 * @param replace string
 * @param string string
 * @param option string[optional]
 * @return string 
 */
function mb_eregi_replace ($pattern, $replace, $string, $option = null) {}

/**
 * Split multibyte string using regular expression
 * @link http://php.net/manual/en/function.mb-split.php
 * @param pattern string
 * @param string string
 * @param limit int[optional]
 * @return array 
 */
function mb_split ($pattern, $string, $limit = null) {}

/**
 * Regular expression match for multibyte string
 * @link http://php.net/manual/en/function.mb-ereg-match.php
 * @param pattern string
 * @param string string
 * @param option string[optional]
 * @return bool 
 */
function mb_ereg_match ($pattern, $string, $option = null) {}

/**
 * Multibyte regular expression match for predefined multibyte string
 * @link http://php.net/manual/en/function.mb-ereg-search.php
 * @param pattern string[optional]
 * @param option string[optional]
 * @return bool 
 */
function mb_ereg_search ($pattern = null, $option = null) {}

/**
 * Returns position and length of a matched part of the multibyte regular expression for a predefined multibyte string
 * @link http://php.net/manual/en/function.mb-ereg-search-pos.php
 * @param pattern string[optional]
 * @param option string[optional]
 * @return array 
 */
function mb_ereg_search_pos ($pattern = null, $option = null) {}

/**
 * Returns the matched part of a multibyte regular expression
 * @link http://php.net/manual/en/function.mb-ereg-search-regs.php
 * @param pattern string[optional]
 * @param option string[optional]
 * @return array 
 */
function mb_ereg_search_regs ($pattern = null, $option = null) {}

/**
 * Setup string and regular expression for a multibyte regular expression match
 * @link http://php.net/manual/en/function.mb-ereg-search-init.php
 * @param string string
 * @param pattern string[optional]
 * @param option string[optional]
 * @return bool 
 */
function mb_ereg_search_init ($string, $pattern = null, $option = null) {}

/**
 * Retrieve the result from the last multibyte regular expression match
 * @link http://php.net/manual/en/function.mb-ereg-search-getregs.php
 * @return array 
 */
function mb_ereg_search_getregs () {}

/**
 * Returns start point for next regular expression match
 * @link http://php.net/manual/en/function.mb-ereg-search-getpos.php
 * @return int 
 */
function mb_ereg_search_getpos () {}

/**
 * Set start point of next regular expression match
 * @link http://php.net/manual/en/function.mb-ereg-search-setpos.php
 * @param position int
 * @return bool 
 */
function mb_ereg_search_setpos ($position) {}

function mbregex_encoding () {}

function mbereg () {}

function mberegi () {}

function mbereg_replace () {}

function mberegi_replace () {}

function mbsplit () {}

function mbereg_match () {}

function mbereg_search () {}

function mbereg_search_pos () {}

function mbereg_search_regs () {}

function mbereg_search_init () {}

function mbereg_search_getregs () {}

function mbereg_search_getpos () {}

function mbereg_search_setpos () {}

define ('MB_OVERLOAD_MAIL', 1);
define ('MB_OVERLOAD_STRING', 2);
define ('MB_OVERLOAD_REGEX', 4);
define ('MB_CASE_UPPER', 0);
define ('MB_CASE_LOWER', 1);
define ('MB_CASE_TITLE', 2);

// End of mbstring v.
?>
