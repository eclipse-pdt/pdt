<?php

// Start of standard v.5.2.5

class __PHP_Incomplete_Class  {
}

class php_user_filter  {
	public $filtername;
	public $params;


	/**
	 * @param in
	 * @param out
	 * @param consumed
	 * @param closing
	 */
	public function filter ($in, $out, &$consumed, $closing) {}

	public function onCreate () {}

	public function onClose () {}

}

class Directory  {

	public function close () {}

	public function rewind () {}

	public function read () {}

}

/**
 * Returns the value of a constant
 * @link http://php.net/manual/en/function.constant.php
 * @param name string
 * @return mixed the value of the constant, or &null; if the constant is not
 */
function constant ($name) {}

/**
 * Convert binary data into hexadecimal representation
 * @link http://php.net/manual/en/function.bin2hex.php
 * @param str string
 * @return string the hexadecimal representation of the given string.
 */
function bin2hex ($str) {}

/**
 * Delay execution
 * @link http://php.net/manual/en/function.sleep.php
 * @param seconds int
 * @return int zero on success, or false on errors.
 */
function sleep ($seconds) {}

/**
 * Delay execution in microseconds
 * @link http://php.net/manual/en/function.usleep.php
 * @param micro_seconds int
 * @return void 
 */
function usleep ($micro_seconds) {}

/**
 * Delay for a number of seconds and nanoseconds
 * @link http://php.net/manual/en/function.time-nanosleep.php
 * @param seconds int
 * @param nanoseconds int
 * @return mixed 
 */
function time_nanosleep ($seconds, $nanoseconds) {}

/**
 * Make the script sleep until the specified time
 * @link http://php.net/manual/en/function.time-sleep-until.php
 * @param timestamp float
 * @return bool 
 */
function time_sleep_until ($timestamp) {}

/**
 * Parse a time/date generated with <function>strftime</function>
 * @link http://php.net/manual/en/function.strptime.php
 * @param date string
 * @param format string
 * @return array an array, or false on failure.
 */
function strptime ($date, $format) {}

/**
 * Flush the output buffer
 * @link http://php.net/manual/en/function.flush.php
 * @return void 
 */
function flush () {}

/**
 * Wraps a string to a given number of characters
 * @link http://php.net/manual/en/function.wordwrap.php
 * @param str string
 * @param width int[optional]
 * @param break string[optional]
 * @param cut bool[optional]
 * @return string the given string wrapped at the specified column.
 */
function wordwrap ($str, $width = null, $break = null, $cut = null) {}

/**
 * Convert special characters to HTML entities
 * @link http://php.net/manual/en/function.htmlspecialchars.php
 * @param string string
 * @param quote_style int[optional]
 * @param charset string[optional]
 * @param double_encode bool[optional]
 * @return string 
 */
function htmlspecialchars ($string, $quote_style = null, $charset = null, $double_encode = null) {}

/**
 * Convert all applicable characters to HTML entities
 * @link http://php.net/manual/en/function.htmlentities.php
 * @param string string
 * @param quote_style int[optional]
 * @param charset string[optional]
 * @param double_encode bool[optional]
 * @return string the encoded string.
 */
function htmlentities ($string, $quote_style = null, $charset = null, $double_encode = null) {}

/**
 * Convert all HTML entities to their applicable characters
 * @link http://php.net/manual/en/function.html-entity-decode.php
 * @param string string
 * @param quote_style int[optional]
 * @param charset string[optional]
 * @return string the decoded string.
 */
function html_entity_decode ($string, $quote_style = null, $charset = null) {}

/**
 * Convert special HTML entities back to characters
 * @link http://php.net/manual/en/function.htmlspecialchars-decode.php
 * @param string string
 * @param quote_style int[optional]
 * @return string the decoded string.
 */
function htmlspecialchars_decode ($string, $quote_style = null) {}

/**
 * Returns the translation table used by <function>htmlspecialchars</function> and <function>htmlentities</function>
 * @link http://php.net/manual/en/function.get-html-translation-table.php
 * @param table int[optional]
 * @param quote_style int[optional]
 * @return array the translation table as an array.
 */
function get_html_translation_table ($table = null, $quote_style = null) {}

/**
 * Calculate the sha1 hash of a string
 * @link http://php.net/manual/en/function.sha1.php
 * @param str string
 * @param raw_output bool[optional]
 * @return string the sha1 hash as a string.
 */
function sha1 ($str, $raw_output = null) {}

/**
 * Calculate the sha1 hash of a file
 * @link http://php.net/manual/en/function.sha1-file.php
 * @param filename string
 * @param raw_output bool[optional]
 * @return string a string on success, false otherwise.
 */
function sha1_file ($filename, $raw_output = null) {}

/**
 * Calculate the md5 hash of a string
 * @link http://php.net/manual/en/function.md5.php
 * @param str string
 * @param raw_output bool[optional]
 * @return string the hash as a 32-character hexadecimal number.
 */
function md5 ($str, $raw_output = null) {}

/**
 * Calculates the md5 hash of a given file
 * @link http://php.net/manual/en/function.md5-file.php
 * @param filename string
 * @param raw_output bool[optional]
 * @return string a string on success, false otherwise.
 */
function md5_file ($filename, $raw_output = null) {}

/**
 * Calculates the crc32 polynomial of a string
 * @link http://php.net/manual/en/function.crc32.php
 * @param str string
 * @return int the crc32 checksum of str as an integer.
 */
function crc32 ($str) {}

/**
 * Parse a binary IPTC block into single tags.
 * @link http://php.net/manual/en/function.iptcparse.php
 * @param iptcblock string
 * @return array an array using the tagmarker as an index and the value as the
 */
function iptcparse ($iptcblock) {}

/**
 * Embed binary IPTC data into a JPEG image
 * @link http://php.net/manual/en/function.iptcembed.php
 * @param iptcdata string
 * @param jpeg_file_name string
 * @param spool int[optional]
 * @return mixed 
 */
function iptcembed ($iptcdata, $jpeg_file_name, $spool = null) {}

/**
 * Get the size of an image
 * @link http://php.net/manual/en/function.getimagesize.php
 * @param filename string
 * @param imageinfo array[optional]
 * @return array an array with 5 elements.
 */
function getimagesize ($filename, array &$imageinfo = null) {}

/**
 * Get Mime-Type for image-type returned by getimagesize,
   exif_read_data, exif_thumbnail, exif_imagetype
 * @link http://php.net/manual/en/function.image-type-to-mime-type.php
 * @param imagetype int
 * @return string 
 */
function image_type_to_mime_type ($imagetype) {}

/**
 * Get file extension for image type
 * @link http://php.net/manual/en/function.image-type-to-extension.php
 * @param imagetype int
 * @param include_dot bool[optional]
 * @return string 
 */
function image_type_to_extension ($imagetype, $include_dot = null) {}

/**
 * Outputs lots of PHP information
 * @link http://php.net/manual/en/function.phpinfo.php
 * @param what int[optional]
 * @return bool 
 */
function phpinfo ($what = null) {}

/**
 * Gets the current PHP version
 * @link http://php.net/manual/en/function.phpversion.php
 * @param extension string[optional]
 * @return string 
 */
function phpversion ($extension = null) {}

/**
 * Prints out the credits for PHP
 * @link http://php.net/manual/en/function.phpcredits.php
 * @param flag int[optional]
 * @return bool 
 */
function phpcredits ($flag = null) {}

/**
 * Gets the logo guid
 * @link http://php.net/manual/en/function.php-logo-guid.php
 * @return string PHPE9568F34-D428-11d2-A769-00AA001ACF42.
 */
function php_logo_guid () {}

function php_real_logo_guid () {}

function php_egg_logo_guid () {}

/**
 * Gets the Zend guid
 * @link http://php.net/manual/en/function.zend-logo-guid.php
 * @return string PHPE9568F35-D428-11d2-A769-00AA001ACF42.
 */
function zend_logo_guid () {}

/**
 * Returns the type of interface between web server and PHP
 * @link http://php.net/manual/en/function.php-sapi-name.php
 * @return string the interface type, as a lowercase string.
 */
function php_sapi_name () {}

/**
 * Returns information about the operating system PHP is running on
 * @link http://php.net/manual/en/function.php-uname.php
 * @param mode string[optional]
 * @return string the description, as a string.
 */
function php_uname ($mode = null) {}

/**
 * Return a list of .ini files parsed from the additional ini dir
 * @link http://php.net/manual/en/function.php-ini-scanned-files.php
 * @return string a comma-separated string of .ini files on success. Each comma is
 */
function php_ini_scanned_files () {}

/**
 * Retrieve a path to the loaded php.ini file
 * @link http://php.net/manual/en/function.php-ini-loaded-file.php
 * @return string 
 */
function php_ini_loaded_file () {}

/**
 * String comparisons using a "natural order" algorithm
 * @link http://php.net/manual/en/function.strnatcmp.php
 * @param str1 string
 * @param str2 string
 * @return int 
 */
function strnatcmp ($str1, $str2) {}

/**
 * Case insensitive string comparisons using a "natural order" algorithm
 * @link http://php.net/manual/en/function.strnatcasecmp.php
 * @param str1 string
 * @param str2 string
 * @return int 
 */
function strnatcasecmp ($str1, $str2) {}

/**
 * Count the number of substring occurrences
 * @link http://php.net/manual/en/function.substr-count.php
 * @param haystack string
 * @param needle string
 * @param offset int[optional]
 * @param length int[optional]
 * @return int 
 */
function substr_count ($haystack, $needle, $offset = null, $length = null) {}

/**
 * Find length of initial segment matching mask
 * @link http://php.net/manual/en/function.strspn.php
 * @param str1 string
 * @param str2 string
 * @param start int[optional]
 * @param length int[optional]
 * @return int the length of the initial segment of str1
 */
function strspn ($str1, $str2, $start = null, $length = null) {}

/**
 * Find length of initial segment not matching mask
 * @link http://php.net/manual/en/function.strcspn.php
 * @param str1 string
 * @param str2 string
 * @param start int[optional]
 * @param length int[optional]
 * @return int the length of the segment as an integer.
 */
function strcspn ($str1, $str2, $start = null, $length = null) {}

/**
 * Tokenize string
 * @link http://php.net/manual/en/function.strtok.php
 * @param str string
 * @param token string
 * @return string 
 */
function strtok ($str, $token) {}

/**
 * Make a string uppercase
 * @link http://php.net/manual/en/function.strtoupper.php
 * @param string string
 * @return string the uppercased string.
 */
function strtoupper ($string) {}

/**
 * Make a string lowercase
 * @link http://php.net/manual/en/function.strtolower.php
 * @param str string
 * @return string the lowercased string.
 */
function strtolower ($str) {}

/**
 * Find position of first occurrence of a string
 * @link http://php.net/manual/en/function.strpos.php
 * @param haystack string
 * @param needle mixed
 * @param offset int[optional]
 * @return int the position as an integer. If needle is
 */
function strpos ($haystack, $needle, $offset = null) {}

/**
 * Find position of first occurrence of a case-insensitive string
 * @link http://php.net/manual/en/function.stripos.php
 * @param haystack string
 * @param needle string
 * @param offset int[optional]
 * @return int 
 */
function stripos ($haystack, $needle, $offset = null) {}

/**
 * Find position of last occurrence of a char in a string
 * @link http://php.net/manual/en/function.strrpos.php
 * @param haystack string
 * @param needle string
 * @param offset int[optional]
 * @return int 
 */
function strrpos ($haystack, $needle, $offset = null) {}

/**
 * Find position of last occurrence of a case-insensitive string in a string
 * @link http://php.net/manual/en/function.strripos.php
 * @param haystack string
 * @param needle string
 * @param offset int[optional]
 * @return int the numerical position of the last occurence of
 */
function strripos ($haystack, $needle, $offset = null) {}

/**
 * Reverse a string
 * @link http://php.net/manual/en/function.strrev.php
 * @param string string
 * @return string the reversed string.
 */
function strrev ($string) {}

/**
 * Convert logical Hebrew text to visual text
 * @link http://php.net/manual/en/function.hebrev.php
 * @param hebrew_text string
 * @param max_chars_per_line int[optional]
 * @return string the visual string.
 */
function hebrev ($hebrew_text, $max_chars_per_line = null) {}

/**
 * Convert logical Hebrew text to visual text with newline conversion
 * @link http://php.net/manual/en/function.hebrevc.php
 * @param hebrew_text string
 * @param max_chars_per_line int[optional]
 * @return string the visual string.
 */
function hebrevc ($hebrew_text, $max_chars_per_line = null) {}

/**
 * Inserts HTML line breaks before all newlines in a string
 * @link http://php.net/manual/en/function.nl2br.php
 * @param string string
 * @return string the altered string.
 */
function nl2br ($string) {}

/**
 * Returns filename component of path
 * @link http://php.net/manual/en/function.basename.php
 * @param path string
 * @param suffix string[optional]
 * @return string the base name of the given path.
 */
function basename ($path, $suffix = null) {}

/**
 * Returns directory name component of path
 * @link http://php.net/manual/en/function.dirname.php
 * @param path string
 * @return string the name of the directory. If there are no slashes in
 */
function dirname ($path) {}

/**
 * Returns information about a file path
 * @link http://php.net/manual/en/function.pathinfo.php
 * @param path string
 * @param options int[optional]
 * @return mixed 
 */
function pathinfo ($path, $options = null) {}

/**
 * Un-quote string quoted with <function>addslashes</function>
 * @link http://php.net/manual/en/function.stripslashes.php
 * @param str string
 * @return string a string with backslashes stripped off.
 */
function stripslashes ($str) {}

/**
 * Un-quote string quoted with <function>addcslashes</function>
 * @link http://php.net/manual/en/function.stripcslashes.php
 * @param str string
 * @return string the unescaped string.
 */
function stripcslashes ($str) {}

/**
 * Find first occurrence of a string
 * @link http://php.net/manual/en/function.strstr.php
 * @param haystack string
 * @param needle mixed
 * @param before_needle bool[optional]
 * @return string the portion of string, or false if needle
 */
function strstr ($haystack, $needle, $before_needle = null) {}

/**
 * Case-insensitive <function>strstr</function>
 * @link http://php.net/manual/en/function.stristr.php
 * @param haystack string
 * @param needle mixed
 * @param before_needle bool[optional]
 * @return string the matched substring. If needle is not
 */
function stristr ($haystack, $needle, $before_needle = null) {}

/**
 * Find the last occurrence of a character in a string
 * @link http://php.net/manual/en/function.strrchr.php
 * @param haystack string
 * @param needle mixed
 * @return string 
 */
function strrchr ($haystack, $needle) {}

/**
 * Randomly shuffles a string
 * @link http://php.net/manual/en/function.str-shuffle.php
 * @param str string
 * @return string the shuffled string.
 */
function str_shuffle ($str) {}

/**
 * Return information about words used in a string
 * @link http://php.net/manual/en/function.str-word-count.php
 * @param string string
 * @param format int[optional]
 * @param charlist string[optional]
 * @return mixed an array or an integer, depending on the
 */
function str_word_count ($string, $format = null, $charlist = null) {}

/**
 * Convert a string to an array
 * @link http://php.net/manual/en/function.str-split.php
 * @param string string
 * @param split_length int[optional]
 * @return array 
 */
function str_split ($string, $split_length = null) {}

/**
 * Search a string for any of a set of characters
 * @link http://php.net/manual/en/function.strpbrk.php
 * @param haystack string
 * @param char_list string
 * @return string a string starting from the character found, or false if it is
 */
function strpbrk ($haystack, $char_list) {}

/**
 * Binary safe comparison of 2 strings from an offset, up to length characters
 * @link http://php.net/manual/en/function.substr-compare.php
 * @param main_str string
 * @param str string
 * @param offset int
 * @param length int[optional]
 * @param case_insensitivity bool[optional]
 * @return int &lt; 0 if main_str from position
 */
function substr_compare ($main_str, $str, $offset, $length = null, $case_insensitivity = null) {}

/**
 * Locale based string comparison
 * @link http://php.net/manual/en/function.strcoll.php
 * @param str1 string
 * @param str2 string
 * @return int &lt; 0 if str1 is less than
 */
function strcoll ($str1, $str2) {}

/**
 * Formats a number as a currency string
 * @link http://php.net/manual/en/function.money-format.php
 * @param format string
 * @param number float
 * @return string the formatted string. Characters before and after the formatting
 */
function money_format ($format, $number) {}

/**
 * Return part of a string
 * @link http://php.net/manual/en/function.substr.php
 * @param string string
 * @param start int
 * @param length int[optional]
 * @return string the extracted part of string.
 */
function substr ($string, $start, $length = null) {}

/**
 * Replace text within a portion of a string
 * @link http://php.net/manual/en/function.substr-replace.php
 * @param string mixed
 * @param replacement string
 * @param start int
 * @param length int[optional]
 * @return mixed 
 */
function substr_replace ($string, $replacement, $start, $length = null) {}

/**
 * Quote meta characters
 * @link http://php.net/manual/en/function.quotemeta.php
 * @param str string
 * @return string the string with meta characters quoted.
 */
function quotemeta ($str) {}

/**
 * Make a string's first character uppercase
 * @link http://php.net/manual/en/function.ucfirst.php
 * @param str string
 * @return string the resulting string.
 */
function ucfirst ($str) {}

/**
 * Uppercase the first character of each word in a string
 * @link http://php.net/manual/en/function.ucwords.php
 * @param str string
 * @return string the modified string.
 */
function ucwords ($str) {}

/**
 * Translate certain characters
 * @link http://php.net/manual/en/function.strtr.php
 * @param str string
 * @param from string
 * @param to string
 * @return string 
 */
function strtr ($str, $from, $to) {}

/**
 * Quote string with slashes
 * @link http://php.net/manual/en/function.addslashes.php
 * @param str string
 * @return string the escaped string.
 */
function addslashes ($str) {}

/**
 * Quote string with slashes in a C style
 * @link http://php.net/manual/en/function.addcslashes.php
 * @param str string
 * @param charlist string
 * @return string the escaped string.
 */
function addcslashes ($str, $charlist) {}

/**
 * Strip whitespace (or other characters) from the end of a string
 * @link http://php.net/manual/en/function.rtrim.php
 * @param str string
 * @param charlist string[optional]
 * @return string the modified string.
 */
function rtrim ($str, $charlist = null) {}

/**
 * Replace all occurrences of the search string with the replacement string
 * @link http://php.net/manual/en/function.str-replace.php
 * @param search mixed
 * @param replace mixed
 * @param subject mixed
 * @param count int[optional]
 * @return mixed 
 */
function str_replace ($search, $replace, $subject, &$count = null) {}

/**
 * Case-insensitive version of <function>str_replace</function>.
 * @link http://php.net/manual/en/function.str-ireplace.php
 * @param search mixed
 * @param replace mixed
 * @param subject mixed
 * @param count int[optional]
 * @return mixed a string or an array of replacements.
 */
function str_ireplace ($search, $replace, $subject, &$count = null) {}

/**
 * Repeat a string
 * @link http://php.net/manual/en/function.str-repeat.php
 * @param input string
 * @param multiplier int
 * @return string the repeated string.
 */
function str_repeat ($input, $multiplier) {}

/**
 * Return information about characters used in a string
 * @link http://php.net/manual/en/function.count-chars.php
 * @param string string
 * @param mode int[optional]
 * @return mixed 
 */
function count_chars ($string, $mode = null) {}

/**
 * Split a string into smaller chunks
 * @link http://php.net/manual/en/function.chunk-split.php
 * @param body string
 * @param chunklen int[optional]
 * @param end string[optional]
 * @return string the chunked string.
 */
function chunk_split ($body, $chunklen = null, $end = null) {}

/**
 * Strip whitespace (or other characters) from the beginning and end of a string
 * @link http://php.net/manual/en/function.trim.php
 * @param str string
 * @param charlist string[optional]
 * @return string 
 */
function trim ($str, $charlist = null) {}

/**
 * Strip whitespace (or other characters) from the beginning of a string
 * @link http://php.net/manual/en/function.ltrim.php
 * @param str string
 * @param charlist string[optional]
 * @return string 
 */
function ltrim ($str, $charlist = null) {}

/**
 * Strip HTML and PHP tags from a string
 * @link http://php.net/manual/en/function.strip-tags.php
 * @param str string
 * @param allowable_tags string[optional]
 * @return string the stripped string.
 */
function strip_tags ($str, $allowable_tags = null) {}

/**
 * Calculate the similarity between two strings
 * @link http://php.net/manual/en/function.similar-text.php
 * @param first string
 * @param second string
 * @param percent float[optional]
 * @return int the number of matching chars in both strings.
 */
function similar_text ($first, $second, &$percent = null) {}

/**
 * Split a string by string
 * @link http://php.net/manual/en/function.explode.php
 * @param delimiter string
 * @param string string
 * @param limit int[optional]
 * @return array 
 */
function explode ($delimiter, $string, $limit = null) {}

/**
 * Join array elements with a string
 * @link http://php.net/manual/en/function.implode.php
 * @param glue string
 * @param pieces array
 * @return string a string containing a string representation of all the array
 */
function implode ($glue, array $pieces) {}

/**
 * Set locale information
 * @link http://php.net/manual/en/function.setlocale.php
 * @param category int
 * @param locale string
 * @param _ string[optional]
 * @return string the new current locale, or false if the locale functionality is
 */
function setlocale ($category, $locale, $_ = null) {}

/**
 * Get numeric formatting information
 * @link http://php.net/manual/en/function.localeconv.php
 * @return array 
 */
function localeconv () {}

/**
 * Query language and locale information
 * @link http://php.net/manual/en/function.nl-langinfo.php
 * @param item int
 * @return string the element as a string, or false if item
 */
function nl_langinfo ($item) {}

/**
 * Calculate the soundex key of a string
 * @link http://php.net/manual/en/function.soundex.php
 * @param str string
 * @return string the soundex key as a string.
 */
function soundex ($str) {}

/**
 * Calculate Levenshtein distance between two strings
 * @link http://php.net/manual/en/function.levenshtein.php
 * @param str1 string
 * @param str2 string
 * @param cost_ins int[optional]
 * @param cost_rep int
 * @param cost_del int
 * @return int 
 */
function levenshtein ($str1, $str2, $cost_ins = null, $cost_rep, $cost_del) {}

/**
 * Return a specific character
 * @link http://php.net/manual/en/function.chr.php
 * @param ascii int
 * @return string the specified character.
 */
function chr ($ascii) {}

/**
 * Return ASCII value of character
 * @link http://php.net/manual/en/function.ord.php
 * @param string string
 * @return int the ASCII value as an integer.
 */
function ord ($string) {}

/**
 * Parses the string into variables
 * @link http://php.net/manual/en/function.parse-str.php
 * @param str string
 * @param arr array[optional]
 * @return void 
 */
function parse_str ($str, array &$arr = null) {}

/**
 * Pad a string to a certain length with another string
 * @link http://php.net/manual/en/function.str-pad.php
 * @param input string
 * @param pad_length int
 * @param pad_string string[optional]
 * @param pad_type int[optional]
 * @return string the padded string.
 */
function str_pad ($input, $pad_length, $pad_string = null, $pad_type = null) {}

/**
 * &Alias; <function>rtrim</function>
 * @link http://php.net/manual/en/function.chop.php
 * @param str
 * @param character_mask[optional]
 */
function chop ($str, $character_mask) {}

/**
 * &Alias; <function>strstr</function>
 * @link http://php.net/manual/en/function.strchr.php
 * @param haystack
 * @param needle
 */
function strchr ($haystack, $needle) {}

/**
 * Return a formatted string
 * @link http://php.net/manual/en/function.sprintf.php
 * @param format string
 * @param args mixed[optional]
 * @param _ mixed[optional]
 * @return string a string produced according to the formatting string
 */
function sprintf ($format, $args = null, $_ = null) {}

/**
 * Output a formatted string
 * @link http://php.net/manual/en/function.printf.php
 * @param format string
 * @param args mixed[optional]
 * @param _ mixed[optional]
 * @return int the length of the outputted string.
 */
function printf ($format, $args = null, $_ = null) {}

/**
 * Output a formatted string
 * @link http://php.net/manual/en/function.vprintf.php
 * @param format string
 * @param args array
 * @return int the length of the outputted string.
 */
function vprintf ($format, array $args) {}

/**
 * Return a formatted string
 * @link http://php.net/manual/en/function.vsprintf.php
 * @param format string
 * @param args array
 * @return string 
 */
function vsprintf ($format, array $args) {}

/**
 * Write a formatted string to a stream
 * @link http://php.net/manual/en/function.fprintf.php
 * @param handle resource
 * @param format string
 * @param args mixed[optional]
 * @param _ mixed[optional]
 * @return int the length of the string written.
 */
function fprintf ($handle, $format, $args = null, $_ = null) {}

/**
 * Write a formatted string to a stream
 * @link http://php.net/manual/en/function.vfprintf.php
 * @param handle resource
 * @param format string
 * @param args array
 * @return int the length of the outputted string.
 */
function vfprintf ($handle, $format, array $args) {}

/**
 * Parses input from a string according to a format
 * @link http://php.net/manual/en/function.sscanf.php
 * @param str string
 * @param format string
 * @param _ mixed[optional]
 * @return mixed 
 */
function sscanf ($str, $format, &$_ = null) {}

/**
 * Parses input from a file according to a format
 * @link http://php.net/manual/en/function.fscanf.php
 * @param handle resource
 * @param format string
 * @param _ mixed[optional]
 * @return mixed 
 */
function fscanf ($handle, $format, &$_ = null) {}

/**
 * Parse a URL and return its components
 * @link http://php.net/manual/en/function.parse-url.php
 * @param url string
 * @param component int[optional]
 * @return mixed 
 */
function parse_url ($url, $component = null) {}

/**
 * URL-encodes string
 * @link http://php.net/manual/en/function.urlencode.php
 * @param str string
 * @return string a string in which all non-alphanumeric characters except
 */
function urlencode ($str) {}

/**
 * Decodes URL-encoded string
 * @link http://php.net/manual/en/function.urldecode.php
 * @param str string
 * @return string the decoded string.
 */
function urldecode ($str) {}

/**
 * URL-encode according to RFC 1738
 * @link http://php.net/manual/en/function.rawurlencode.php
 * @param str string
 * @return string a string in which all non-alphanumeric characters except
 */
function rawurlencode ($str) {}

/**
 * Decode URL-encoded strings
 * @link http://php.net/manual/en/function.rawurldecode.php
 * @param str string
 * @return string the decoded URL, as a string.
 */
function rawurldecode ($str) {}

/**
 * Generate URL-encoded query string
 * @link http://php.net/manual/en/function.http-build-query.php
 * @param formdata array
 * @param numeric_prefix string[optional]
 * @param arg_separator string[optional]
 * @return string a URL-encoded string.
 */
function http_build_query (array $formdata, $numeric_prefix = null, $arg_separator = null) {}

/**
 * Returns the target of a symbolic link
 * @link http://php.net/manual/en/function.readlink.php
 * @param path string
 * @return string the contents of the symbolic link path or false on error.
 */
function readlink ($path) {}

/**
 * Gets information about a link
 * @link http://php.net/manual/en/function.linkinfo.php
 * @param path string
 * @return int 
 */
function linkinfo ($path) {}

/**
 * Creates a symbolic link
 * @link http://php.net/manual/en/function.symlink.php
 * @param target string
 * @param link string
 * @return bool 
 */
function symlink ($target, $link) {}

/**
 * Create a hard link
 * @link http://php.net/manual/en/function.link.php
 * @param target string
 * @param link string
 * @return bool 
 */
function link ($target, $link) {}

/**
 * Deletes a file
 * @link http://php.net/manual/en/function.unlink.php
 * @param filename string
 * @param context resource[optional]
 * @return bool 
 */
function unlink ($filename, $context = null) {}

/**
 * Execute an external program
 * @link http://php.net/manual/en/function.exec.php
 * @param command string
 * @param output array[optional]
 * @param return_var int[optional]
 * @return string 
 */
function exec ($command, array &$output = null, &$return_var = null) {}

/**
 * Execute an external program and display the output
 * @link http://php.net/manual/en/function.system.php
 * @param command string
 * @param return_var int[optional]
 * @return string the last line of the command output on success, and false
 */
function system ($command, &$return_var = null) {}

/**
 * Escape shell metacharacters
 * @link http://php.net/manual/en/function.escapeshellcmd.php
 * @param command string
 * @return string 
 */
function escapeshellcmd ($command) {}

/**
 * Escape a string to be used as a shell argument
 * @link http://php.net/manual/en/function.escapeshellarg.php
 * @param arg string
 * @return string 
 */
function escapeshellarg ($arg) {}

/**
 * Execute an external program and display raw output
 * @link http://php.net/manual/en/function.passthru.php
 * @param command string
 * @param return_var int[optional]
 * @return void 
 */
function passthru ($command, &$return_var = null) {}

/**
 * Execute command via shell and return the complete output as a string
 * @link http://php.net/manual/en/function.shell-exec.php
 * @param cmd string
 * @return string 
 */
function shell_exec ($cmd) {}

/**
 * Execute a command and open file pointers for input/output
 * @link http://php.net/manual/en/function.proc-open.php
 * @param cmd string
 * @param descriptorspec array
 * @param pipes array
 * @param cwd string[optional]
 * @param env array[optional]
 * @param other_options array[optional]
 * @return resource a resource representing the process, which should be freed using
 */
function proc_open ($cmd, array $descriptorspec, array &$pipes, $cwd = null, array $env = null, array $other_options = null) {}

/**
 * Close a process opened by <function>proc_open</function> and return the exit code of that process.
 * @link http://php.net/manual/en/function.proc-close.php
 * @param process resource
 * @return int the termination status of the process that was run.
 */
function proc_close ($process) {}

/**
 * Kills a process opened by proc_open
 * @link http://php.net/manual/en/function.proc-terminate.php
 * @param process resource
 * @param signal int[optional]
 * @return bool the termination status of the process that was run.
 */
function proc_terminate ($process, $signal = null) {}

/**
 * Get information about a process opened by <function>proc_open</function>
 * @link http://php.net/manual/en/function.proc-get-status.php
 * @param process resource
 * @return array 
 */
function proc_get_status ($process) {}

/**
 * Change the priority of the current process
 * @link http://php.net/manual/en/function.proc-nice.php
 * @param increment int
 * @return bool 
 */
function proc_nice ($increment) {}

/**
 * Generate a random integer
 * @link http://php.net/manual/en/function.rand.php
 * @param min int[optional]
 * @param max int
 * @return int 
 */
function rand ($min = null, $max) {}

/**
 * Seed the random number generator
 * @link http://php.net/manual/en/function.srand.php
 * @param seed int[optional]
 * @return void 
 */
function srand ($seed = null) {}

/**
 * Show largest possible random value
 * @link http://php.net/manual/en/function.getrandmax.php
 * @return int 
 */
function getrandmax () {}

/**
 * Generate a better random value
 * @link http://php.net/manual/en/function.mt-rand.php
 * @param min int[optional]
 * @param max int
 * @return int 
 */
function mt_rand ($min = null, $max) {}

/**
 * Seed the better random number generator
 * @link http://php.net/manual/en/function.mt-srand.php
 * @param seed int[optional]
 * @return void 
 */
function mt_srand ($seed = null) {}

/**
 * Show largest possible random value
 * @link http://php.net/manual/en/function.mt-getrandmax.php
 * @return int the maximum random value returned by mt_rand
 */
function mt_getrandmax () {}

/**
 * Get port number associated with an Internet service and protocol
 * @link http://php.net/manual/en/function.getservbyname.php
 * @param service string
 * @param protocol string
 * @return int the port number, or false if service or
 */
function getservbyname ($service, $protocol) {}

/**
 * Get Internet service which corresponds to port and protocol
 * @link http://php.net/manual/en/function.getservbyport.php
 * @param port int
 * @param protocol string
 * @return string the Internet service name as a string.
 */
function getservbyport ($port, $protocol) {}

/**
 * Get protocol number associated with protocol name
 * @link http://php.net/manual/en/function.getprotobyname.php
 * @param name string
 * @return int the protocol number or -1 if the protocol is not found.
 */
function getprotobyname ($name) {}

/**
 * Get protocol name associated with protocol number
 * @link http://php.net/manual/en/function.getprotobynumber.php
 * @param number int
 * @return string the protocol name as a string.
 */
function getprotobynumber ($number) {}

/**
 * Gets PHP script owner's UID
 * @link http://php.net/manual/en/function.getmyuid.php
 * @return int the user ID of the current script, or false on error.
 */
function getmyuid () {}

/**
 * Get PHP script owner's GID
 * @link http://php.net/manual/en/function.getmygid.php
 * @return int the group ID of the current script, or false on error.
 */
function getmygid () {}

/**
 * Gets PHP's process ID
 * @link http://php.net/manual/en/function.getmypid.php
 * @return int the current PHP process ID, or false on error.
 */
function getmypid () {}

/**
 * Gets the inode of the current script
 * @link http://php.net/manual/en/function.getmyinode.php
 * @return int the current script's inode as an integer, or false on error.
 */
function getmyinode () {}

/**
 * Gets time of last page modification
 * @link http://php.net/manual/en/function.getlastmod.php
 * @return int the time of the last modification of the current
 */
function getlastmod () {}

/**
 * Decodes data encoded with MIME base64
 * @link http://php.net/manual/en/function.base64-decode.php
 * @param data string
 * @param strict bool[optional]
 * @return string the original data or false on failure. The returned data may be
 */
function base64_decode ($data, $strict = null) {}

/**
 * Encodes data with MIME base64
 * @link http://php.net/manual/en/function.base64-encode.php
 * @param data string
 * @return string 
 */
function base64_encode ($data) {}

/**
 * Uuencode a string
 * @link http://php.net/manual/en/function.convert-uuencode.php
 * @param data string
 * @return string the uuencoded data.
 */
function convert_uuencode ($data) {}

/**
 * Decode a uuencoded string
 * @link http://php.net/manual/en/function.convert-uudecode.php
 * @param data string
 * @return string the decoded data as a string.
 */
function convert_uudecode ($data) {}

/**
 * Absolute value
 * @link http://php.net/manual/en/function.abs.php
 * @param number mixed
 * @return number 
 */
function abs ($number) {}

/**
 * Round fractions up
 * @link http://php.net/manual/en/function.ceil.php
 * @param value float
 * @return float 
 */
function ceil ($value) {}

/**
 * Round fractions down
 * @link http://php.net/manual/en/function.floor.php
 * @param value float
 * @return float 
 */
function floor ($value) {}

/**
 * Rounds a float
 * @link http://php.net/manual/en/function.round.php
 * @param val float
 * @param precision int[optional]
 * @return float 
 */
function round ($val, $precision = null) {}

/**
 * Sine
 * @link http://php.net/manual/en/function.sin.php
 * @param arg float
 * @return float 
 */
function sin ($arg) {}

/**
 * Cosine
 * @link http://php.net/manual/en/function.cos.php
 * @param arg float
 * @return float 
 */
function cos ($arg) {}

/**
 * Tangent
 * @link http://php.net/manual/en/function.tan.php
 * @param arg float
 * @return float 
 */
function tan ($arg) {}

/**
 * Arc sine
 * @link http://php.net/manual/en/function.asin.php
 * @param arg float
 * @return float 
 */
function asin ($arg) {}

/**
 * Arc cosine
 * @link http://php.net/manual/en/function.acos.php
 * @param arg float
 * @return float 
 */
function acos ($arg) {}

/**
 * Arc tangent
 * @link http://php.net/manual/en/function.atan.php
 * @param arg float
 * @return float 
 */
function atan ($arg) {}

/**
 * Arc tangent of two variables
 * @link http://php.net/manual/en/function.atan2.php
 * @param y float
 * @param x float
 * @return float 
 */
function atan2 ($y, $x) {}

/**
 * Hyperbolic sine
 * @link http://php.net/manual/en/function.sinh.php
 * @param arg float
 * @return float 
 */
function sinh ($arg) {}

/**
 * Hyperbolic cosine
 * @link http://php.net/manual/en/function.cosh.php
 * @param arg float
 * @return float 
 */
function cosh ($arg) {}

/**
 * Hyperbolic tangent
 * @link http://php.net/manual/en/function.tanh.php
 * @param arg float
 * @return float 
 */
function tanh ($arg) {}

/**
 * Inverse hyperbolic sine
 * @link http://php.net/manual/en/function.asinh.php
 * @param arg float
 * @return float 
 */
function asinh ($arg) {}

/**
 * Inverse hyperbolic cosine
 * @link http://php.net/manual/en/function.acosh.php
 * @param arg float
 * @return float 
 */
function acosh ($arg) {}

/**
 * Inverse hyperbolic tangent
 * @link http://php.net/manual/en/function.atanh.php
 * @param arg float
 * @return float 
 */
function atanh ($arg) {}

/**
 * Returns exp(number) - 1, computed in a way that is accurate even
   when the value of number is close to zero
 * @link http://php.net/manual/en/function.expm1.php
 * @param arg float
 * @return float 
 */
function expm1 ($arg) {}

/**
 * Returns log(1 + number), computed in a way that is accurate even when
   the value of number is close to zero
 * @link http://php.net/manual/en/function.log1p.php
 * @param number float
 * @return float 
 */
function log1p ($number) {}

/**
 * Get value of pi
 * @link http://php.net/manual/en/function.pi.php
 * @return float 
 */
function pi () {}

/**
 * Finds whether a value is a legal finite number
 * @link http://php.net/manual/en/function.is-finite.php
 * @param val float
 * @return bool 
 */
function is_finite ($val) {}

/**
 * Finds whether a value is not a number
 * @link http://php.net/manual/en/function.is-nan.php
 * @param val float
 * @return bool true if val is 'not a number',
 */
function is_nan ($val) {}

/**
 * Finds whether a value is infinite
 * @link http://php.net/manual/en/function.is-infinite.php
 * @param val float
 * @return bool 
 */
function is_infinite ($val) {}

/**
 * Exponential expression
 * @link http://php.net/manual/en/function.pow.php
 * @param base number
 * @param exp number
 * @return number 
 */
function pow ($base, $exp) {}

/**
 * Calculates the exponent of <constant>e</constant>
 * @link http://php.net/manual/en/function.exp.php
 * @param arg float
 * @return float 
 */
function exp ($arg) {}

/**
 * Natural logarithm
 * @link http://php.net/manual/en/function.log.php
 * @param arg float
 * @param base float[optional]
 * @return float 
 */
function log ($arg, $base = null) {}

/**
 * Base-10 logarithm
 * @link http://php.net/manual/en/function.log10.php
 * @param arg float
 * @return float 
 */
function log10 ($arg) {}

/**
 * Square root
 * @link http://php.net/manual/en/function.sqrt.php
 * @param arg float
 * @return float 
 */
function sqrt ($arg) {}

/**
 * Calculate the length of the hypotenuse of a right-angle triangle
 * @link http://php.net/manual/en/function.hypot.php
 * @param x float
 * @param y float
 * @return float 
 */
function hypot ($x, $y) {}

/**
 * Converts the number in degrees to the radian equivalent
 * @link http://php.net/manual/en/function.deg2rad.php
 * @param number float
 * @return float 
 */
function deg2rad ($number) {}

/**
 * Converts the radian number to the equivalent number in degrees
 * @link http://php.net/manual/en/function.rad2deg.php
 * @param number float
 * @return float 
 */
function rad2deg ($number) {}

/**
 * Binary to decimal
 * @link http://php.net/manual/en/function.bindec.php
 * @param binary_string string
 * @return number 
 */
function bindec ($binary_string) {}

/**
 * Hexadecimal to decimal
 * @link http://php.net/manual/en/function.hexdec.php
 * @param hex_string string
 * @return number 
 */
function hexdec ($hex_string) {}

/**
 * Octal to decimal
 * @link http://php.net/manual/en/function.octdec.php
 * @param octal_string string
 * @return number 
 */
function octdec ($octal_string) {}

/**
 * Decimal to binary
 * @link http://php.net/manual/en/function.decbin.php
 * @param number int
 * @return string 
 */
function decbin ($number) {}

/**
 * Decimal to octal
 * @link http://php.net/manual/en/function.decoct.php
 * @param number int
 * @return string 
 */
function decoct ($number) {}

/**
 * Decimal to hexadecimal
 * @link http://php.net/manual/en/function.dechex.php
 * @param number int
 * @return string 
 */
function dechex ($number) {}

/**
 * Convert a number between arbitrary bases
 * @link http://php.net/manual/en/function.base-convert.php
 * @param number string
 * @param frombase int
 * @param tobase int
 * @return string 
 */
function base_convert ($number, $frombase, $tobase) {}

/**
 * Format a number with grouped thousands
 * @link http://php.net/manual/en/function.number-format.php
 * @param number float
 * @param decimals int[optional]
 * @return string 
 */
function number_format ($number, $decimals = null) {}

/**
 * Returns the floating point remainder (modulo) of the division
  of the arguments
 * @link http://php.net/manual/en/function.fmod.php
 * @param x float
 * @param y float
 * @return float 
 */
function fmod ($x, $y) {}

/**
 * Converts a packed internet address to a human readable representation
 * @link http://php.net/manual/en/function.inet-ntop.php
 * @param in_addr string
 * @return string a string representation of the address or false on failure.
 */
function inet_ntop ($in_addr) {}

/**
 * Converts a human readable IP address to its packed in_addr representation
 * @link http://php.net/manual/en/function.inet-pton.php
 * @param address string
 * @return string the in_addr representation of the given
 */
function inet_pton ($address) {}

/**
 * Converts a string containing an (IPv4) Internet Protocol dotted address into a proper address
 * @link http://php.net/manual/en/function.ip2long.php
 * @param ip_address string
 * @return int the IPv4 address or false if ip_address
 */
function ip2long ($ip_address) {}

/**
 * Converts an (IPv4) Internet network address into a string in Internet standard dotted format
 * @link http://php.net/manual/en/function.long2ip.php
 * @param proper_address int
 * @return string the Internet IP address as a string.
 */
function long2ip ($proper_address) {}

/**
 * Gets the value of an environment variable
 * @link http://php.net/manual/en/function.getenv.php
 * @param varname string
 * @return string the value of the environment variable
 */
function getenv ($varname) {}

/**
 * Sets the value of an environment variable
 * @link http://php.net/manual/en/function.putenv.php
 * @param setting string
 * @return bool 
 */
function putenv ($setting) {}

/**
 * Gets options from the command line argument list
 * @link http://php.net/manual/en/function.getopt.php
 * @param options string
 * @param longopts array[optional]
 * @return array 
 */
function getopt ($options, array $longopts = null) {}

/**
 * Gets system load average
 * @link http://php.net/manual/en/function.sys-getloadavg.php
 * @return array an array with three samples (last 1, 5 and 15
 */
function sys_getloadavg () {}

/**
 * Return current Unix timestamp with microseconds
 * @link http://php.net/manual/en/function.microtime.php
 * @param get_as_float bool[optional]
 * @return mixed 
 */
function microtime ($get_as_float = null) {}

/**
 * Get current time
 * @link http://php.net/manual/en/function.gettimeofday.php
 * @param return_float bool[optional]
 * @return mixed 
 */
function gettimeofday ($return_float = null) {}

/**
 * Gets the current resource usages
 * @link http://php.net/manual/en/function.getrusage.php
 * @param who int[optional]
 * @return array an associative array containing the data returned from the system
 */
function getrusage ($who = null) {}

/**
 * Generate a unique ID
 * @link http://php.net/manual/en/function.uniqid.php
 * @param prefix string[optional]
 * @param more_entropy bool[optional]
 * @return string the unique identifier, as a string.
 */
function uniqid ($prefix = null, $more_entropy = null) {}

/**
 * Convert a quoted-printable string to an 8 bit string
 * @link http://php.net/manual/en/function.quoted-printable-decode.php
 * @param str string
 * @return string the 8-bit binary string.
 */
function quoted_printable_decode ($str) {}

/**
 * Convert from one Cyrillic character set to another
 * @link http://php.net/manual/en/function.convert-cyr-string.php
 * @param str string
 * @param from string
 * @param to string
 * @return string the converted string.
 */
function convert_cyr_string ($str, $from, $to) {}

/**
 * Gets the name of the owner of the current PHP script
 * @link http://php.net/manual/en/function.get-current-user.php
 * @return string the username as a string.
 */
function get_current_user () {}

/**
 * Limits the maximum execution time
 * @link http://php.net/manual/en/function.set-time-limit.php
 * @param seconds int
 * @return void 
 */
function set_time_limit ($seconds) {}

/**
 * Gets the value of a PHP configuration option
 * @link http://php.net/manual/en/function.get-cfg-var.php
 * @param option string
 * @return string the current value of the PHP configuration variable specified by
 */
function get_cfg_var ($option) {}

function magic_quotes_runtime () {}

/**
 * Sets the current active configuration setting of magic_quotes_runtime
 * @link http://php.net/manual/en/function.set-magic-quotes-runtime.php
 * @param new_setting int
 * @return bool 
 */
function set_magic_quotes_runtime ($new_setting) {}

/**
 * Gets the current configuration setting of magic quotes gpc
 * @link http://php.net/manual/en/function.get-magic-quotes-gpc.php
 * @return int 0 if magic quotes gpc are off, 1 otherwise.
 */
function get_magic_quotes_gpc () {}

/**
 * Gets the current active configuration setting of magic_quotes_runtime
 * @link http://php.net/manual/en/function.get-magic-quotes-runtime.php
 * @return int 0 if magic quotes runtime is off, 1 otherwise.
 */
function get_magic_quotes_runtime () {}

/**
 * Import GET/POST/Cookie variables into the global scope
 * @link http://php.net/manual/en/function.import-request-variables.php
 * @param types string
 * @param prefix string[optional]
 * @return bool 
 */
function import_request_variables ($types, $prefix = null) {}

/**
 * Send an error message somewhere
 * @link http://php.net/manual/en/function.error-log.php
 * @param message string
 * @param message_type int[optional]
 * @param destination string[optional]
 * @param extra_headers string[optional]
 * @return bool 
 */
function error_log ($message, $message_type = null, $destination = null, $extra_headers = null) {}

/**
 * Get the last occurred error
 * @link http://php.net/manual/en/function.error-get-last.php
 * @return array an associative array describing the last error with keys "type",
 */
function error_get_last () {}

/**
 * Call a user function given by the first parameter
 * @link http://php.net/manual/en/function.call-user-func.php
 * @param function callback
 * @param parameter mixed[optional]
 * @param _ mixed[optional]
 * @return mixed the function result, or false on error.
 */
function call_user_func ($function, $parameter = null, $_ = null) {}

/**
 * Call a user function given with an array of parameters
 * @link http://php.net/manual/en/function.call-user-func-array.php
 * @param function callback
 * @param param_arr array
 * @return mixed the function result, or false on error.
 */
function call_user_func_array ($function, array $param_arr) {}

/**
 * Call a user method on an specific object [deprecated]
 * @link http://php.net/manual/en/function.call-user-method.php
 * @param method_name string
 * @param obj object
 * @param parameter mixed[optional]
 * @param _ mixed[optional]
 * @return mixed 
 */
function call_user_method ($method_name, &$obj, $parameter = null, $_ = null) {}

/**
 * Call a user method given with an array of parameters [deprecated]
 * @link http://php.net/manual/en/function.call-user-method-array.php
 * @param method_name string
 * @param obj object
 * @param params array
 * @return mixed 
 */
function call_user_method_array ($method_name, &$obj, array $params) {}

/**
 * Generates a storable representation of a value
 * @link http://php.net/manual/en/function.serialize.php
 * @param value mixed
 * @return string a string containing a byte-stream representation of
 */
function serialize ($value) {}

/**
 * Creates a PHP value from a stored representation
 * @link http://php.net/manual/en/function.unserialize.php
 * @param str string
 * @return mixed 
 */
function unserialize ($str) {}

/**
 * Dumps information about a variable
 * @link http://php.net/manual/en/function.var-dump.php
 * @param expression mixed
 * @param expression mixed[optional]
 * @return void 
 */
function var_dump ($expression, $expression = null) {}

/**
 * Outputs or returns a parsable string representation of a variable
 * @link http://php.net/manual/en/function.var-export.php
 * @param expression mixed
 * @param return bool[optional]
 * @return mixed the variable representation when the return
 */
function var_export ($expression, $return = null) {}

/**
 * Dumps a string representation of an internal zend value to output
 * @link http://php.net/manual/en/function.debug-zval-dump.php
 * @param variable mixed
 * @return void 
 */
function debug_zval_dump ($variable) {}

/**
 * Prints human-readable information about a variable
 * @link http://php.net/manual/en/function.print-r.php
 * @param expression mixed
 * @param return bool[optional]
 * @return mixed 
 */
function print_r ($expression, $return = null) {}

/**
 * Returns the amount of memory allocated to PHP
 * @link http://php.net/manual/en/function.memory-get-usage.php
 * @param real_usage bool[optional]
 * @return int the memory amount in bytes.
 */
function memory_get_usage ($real_usage = null) {}

/**
 * Returns the peak of memory allocated by PHP
 * @link http://php.net/manual/en/function.memory-get-peak-usage.php
 * @param real_usage bool[optional]
 * @return int the memory peak in bytes.
 */
function memory_get_peak_usage ($real_usage = null) {}

/**
 * Register a function for execution on shutdown
 * @link http://php.net/manual/en/function.register-shutdown-function.php
 * @param function callback
 * @param parameter mixed[optional]
 * @param _ mixed[optional]
 * @return void 
 */
function register_shutdown_function ($function, $parameter = null, $_ = null) {}

/**
 * Register a function for execution on each tick
 * @link http://php.net/manual/en/function.register-tick-function.php
 * @param function callback
 * @param arg mixed[optional]
 * @param _ mixed[optional]
 * @return bool 
 */
function register_tick_function ($function, $arg = null, $_ = null) {}

/**
 * De-register a function for execution on each tick
 * @link http://php.net/manual/en/function.unregister-tick-function.php
 * @param function_name string
 * @return void 
 */
function unregister_tick_function ($function_name) {}

/**
 * Syntax highlighting of a file
 * @link http://php.net/manual/en/function.highlight-file.php
 * @param filename string
 * @param return bool[optional]
 * @return mixed 
 */
function highlight_file ($filename, $return = null) {}

/**
 * &Alias; <function>highlight_file</function>
 * @link http://php.net/manual/en/function.show-source.php
 * @param file_name
 * @param return[optional]
 */
function show_source ($file_name, $return) {}

/**
 * Syntax highlighting of a string
 * @link http://php.net/manual/en/function.highlight-string.php
 * @param str string
 * @param return bool[optional]
 * @return mixed 
 */
function highlight_string ($str, $return = null) {}

/**
 * Return source with stripped comments and whitespace
 * @link http://php.net/manual/en/function.php-strip-whitespace.php
 * @param filename string
 * @return string 
 */
function php_strip_whitespace ($filename) {}

/**
 * Gets the value of a configuration option
 * @link http://php.net/manual/en/function.ini-get.php
 * @param varname string
 * @return string the value of the configuration option as a string on success, or
 */
function ini_get ($varname) {}

/**
 * Gets all configuration options
 * @link http://php.net/manual/en/function.ini-get-all.php
 * @param extension string[optional]
 * @return array an associative array uses the directive name as the array key,
 */
function ini_get_all ($extension = null) {}

/**
 * Sets the value of a configuration option
 * @link http://php.net/manual/en/function.ini-set.php
 * @param varname string
 * @param newvalue string
 * @return string the old value on success, false on failure.
 */
function ini_set ($varname, $newvalue) {}

/**
 * &Alias; <function>ini_set</function>
 * @link http://php.net/manual/en/function.ini-alter.php
 * @param varname
 * @param newvalue
 */
function ini_alter ($varname, $newvalue) {}

/**
 * Restores the value of a configuration option
 * @link http://php.net/manual/en/function.ini-restore.php
 * @param varname string
 * @return void 
 */
function ini_restore ($varname) {}

/**
 * Gets the current include_path configuration option
 * @link http://php.net/manual/en/function.get-include-path.php
 * @return string the path, as a string.
 */
function get_include_path () {}

/**
 * Sets the include_path configuration option
 * @link http://php.net/manual/en/function.set-include-path.php
 * @param new_include_path string
 * @return string the old include_path on
 */
function set_include_path ($new_include_path) {}

/**
 * Restores the value of the include_path configuration option
 * @link http://php.net/manual/en/function.restore-include-path.php
 * @return void 
 */
function restore_include_path () {}

/**
 * Send a cookie
 * @link http://php.net/manual/en/function.setcookie.php
 * @param name string
 * @param value string[optional]
 * @param expire int[optional]
 * @param path string[optional]
 * @param domain string[optional]
 * @param secure bool[optional]
 * @param httponly bool[optional]
 * @return bool 
 */
function setcookie ($name, $value = null, $expire = null, $path = null, $domain = null, $secure = null, $httponly = null) {}

/**
 * Send a cookie without urlencoding the cookie value
 * @link http://php.net/manual/en/function.setrawcookie.php
 * @param name string
 * @param value string[optional]
 * @param expire int[optional]
 * @param path string[optional]
 * @param domain string[optional]
 * @param secure bool[optional]
 * @param httponly bool[optional]
 * @return bool 
 */
function setrawcookie ($name, $value = null, $expire = null, $path = null, $domain = null, $secure = null, $httponly = null) {}

/**
 * Send a raw HTTP header
 * @link http://php.net/manual/en/function.header.php
 * @param string string
 * @param replace bool[optional]
 * @param http_response_code int[optional]
 * @return void 
 */
function header ($string, $replace = null, $http_response_code = null) {}

/**
 * Checks if or where headers have been sent
 * @link http://php.net/manual/en/function.headers-sent.php
 * @param file string[optional]
 * @param line int[optional]
 * @return bool 
 */
function headers_sent (&$file = null, &$line = null) {}

/**
 * Returns a list of response headers sent (or ready to send)
 * @link http://php.net/manual/en/function.headers-list.php
 * @return array a numerically indexed array of headers.
 */
function headers_list () {}

/**
 * Check whether client disconnected
 * @link http://php.net/manual/en/function.connection-aborted.php
 * @return int 1 if client disconnected, 0 otherwise.
 */
function connection_aborted () {}

/**
 * Returns connection status bitfield
 * @link http://php.net/manual/en/function.connection-status.php
 * @return int the connection status bitfield, which can be used against the
 */
function connection_status () {}

/**
 * Set whether a client disconnect should abort script execution
 * @link http://php.net/manual/en/function.ignore-user-abort.php
 * @param setting bool[optional]
 * @return int the previous setting, as a boolean.
 */
function ignore_user_abort ($setting = null) {}

/**
 * Parse a configuration file
 * @link http://php.net/manual/en/function.parse-ini-file.php
 * @param filename string
 * @param process_sections bool[optional]
 * @return array 
 */
function parse_ini_file ($filename, $process_sections = null) {}

/**
 * Tells whether the file was uploaded via HTTP POST
 * @link http://php.net/manual/en/function.is-uploaded-file.php
 * @param filename string
 * @return bool 
 */
function is_uploaded_file ($filename) {}

/**
 * Moves an uploaded file to a new location
 * @link http://php.net/manual/en/function.move-uploaded-file.php
 * @param filename string
 * @param destination string
 * @return bool 
 */
function move_uploaded_file ($filename, $destination) {}

/**
 * Get the Internet host name corresponding to a given IP address
 * @link http://php.net/manual/en/function.gethostbyaddr.php
 * @param ip_address string
 * @return string the host name or the unmodified ip_address
 */
function gethostbyaddr ($ip_address) {}

/**
 * Get the IP address corresponding to a given Internet host name
 * @link http://php.net/manual/en/function.gethostbyname.php
 * @param hostname string
 * @return string the IP address or a string containing the unmodified
 */
function gethostbyname ($hostname) {}

/**
 * Get a list of IP addresses corresponding to a given Internet host
   name
 * @link http://php.net/manual/en/function.gethostbynamel.php
 * @param hostname string
 * @return array an array of IP addresses or false if
 */
function gethostbynamel ($hostname) {}

/**
 * &Alias; <function>checkdnsrr</function>
 * @link http://php.net/manual/en/function.dns-check-record.php
 * @param host
 * @param type[optional]
 */
function dns_check_record ($host, $type) {}

/**
 * Check DNS records corresponding to a given Internet host name or IP address
 * @link http://php.net/manual/en/function.checkdnsrr.php
 * @param host string
 * @param type string[optional]
 * @return int true if any records are found; returns false if no records
 */
function checkdnsrr ($host, $type = null) {}

/**
 * &Alias; <function>getmxrr</function>
 * @link http://php.net/manual/en/function.dns-get-mx.php
 * @param hostname
 * @param mxhosts
 * @param weight[optional]
 */
function dns_get_mx ($hostname, &$mxhosts, &$weight) {}

/**
 * Get MX records corresponding to a given Internet host name
 * @link http://php.net/manual/en/function.getmxrr.php
 * @param hostname string
 * @param mxhosts array
 * @param weight array[optional]
 * @return bool true if any records are found; returns false if no records
 */
function getmxrr ($hostname, array &$mxhosts, array &$weight = null) {}

/**
 * Fetch DNS Resource Records associated with a hostname
 * @link http://php.net/manual/en/function.dns-get-record.php
 * @param hostname string
 * @param type int[optional]
 * @param authns array[optional]
 * @param addtl array
 * @return array 
 */
function dns_get_record ($hostname, $type = null, array &$authns = null, array &$addtl) {}

/**
 * Get the integer value of a variable
 * @link http://php.net/manual/en/function.intval.php
 * @param var mixed
 * @param base int[optional]
 * @return int 
 */
function intval ($var, $base = null) {}

/**
 * Get float value of a variable
 * @link http://php.net/manual/en/function.floatval.php
 * @param var mixed
 * @return float 
 */
function floatval ($var) {}

/**
 * &Alias; <function>floatval</function>
 * @link http://php.net/manual/en/function.doubleval.php
 * @param var
 */
function doubleval ($var) {}

/**
 * Get string value of a variable
 * @link http://php.net/manual/en/function.strval.php
 * @param var mixed
 * @return string 
 */
function strval ($var) {}

/**
 * Get the type of a variable
 * @link http://php.net/manual/en/function.gettype.php
 * @param var mixed
 * @return string 
 */
function gettype ($var) {}

/**
 * Set the type of a variable
 * @link http://php.net/manual/en/function.settype.php
 * @param var mixed
 * @param type string
 * @return bool 
 */
function settype (&$var, $type) {}

/**
 * Finds whether a variable is &null;
 * @link http://php.net/manual/en/function.is-null.php
 * @param var mixed
 * @return bool true if var is null, false
 */
function is_null ($var) {}

/**
 * Finds whether a variable is a resource
 * @link http://php.net/manual/en/function.is-resource.php
 * @param var mixed
 * @return bool true if var is a resource,
 */
function is_resource ($var) {}

/**
 * Finds out whether a variable is a boolean
 * @link http://php.net/manual/en/function.is-bool.php
 * @param var mixed
 * @return bool true if var is a boolean,
 */
function is_bool ($var) {}

/**
 * &Alias; <function>is_int</function>
 * @link http://php.net/manual/en/function.is-long.php
 * @param var
 */
function is_long ($var) {}

/**
 * Finds whether the type of a variable is float
 * @link http://php.net/manual/en/function.is-float.php
 * @param var mixed
 * @return bool true if var is a float,
 */
function is_float ($var) {}

/**
 * Find whether the type of a variable is integer
 * @link http://php.net/manual/en/function.is-int.php
 * @param var mixed
 * @return bool true if var is an integer,
 */
function is_int ($var) {}

/**
 * &Alias; <function>is_int</function>
 * @link http://php.net/manual/en/function.is-integer.php
 * @param var
 */
function is_integer ($var) {}

/**
 * &Alias; <function>is_float</function>
 * @link http://php.net/manual/en/function.is-double.php
 * @param var
 */
function is_double ($var) {}

/**
 * &Alias; <function>is_float</function>
 * @link http://php.net/manual/en/function.is-real.php
 * @param var
 */
function is_real ($var) {}

/**
 * Finds whether a variable is a number or a numeric string
 * @link http://php.net/manual/en/function.is-numeric.php
 * @param var mixed
 * @return bool true if var is a number or a numeric
 */
function is_numeric ($var) {}

/**
 * Find whether the type of a variable is string
 * @link http://php.net/manual/en/function.is-string.php
 * @param var mixed
 * @return bool true if var is of type string,
 */
function is_string ($var) {}

/**
 * Finds whether a variable is an array
 * @link http://php.net/manual/en/function.is-array.php
 * @param var mixed
 * @return bool true if var is an array,
 */
function is_array ($var) {}

/**
 * Finds whether a variable is an object
 * @link http://php.net/manual/en/function.is-object.php
 * @param var mixed
 * @return bool true if var is an object,
 */
function is_object ($var) {}

/**
 * Finds whether a variable is a scalar
 * @link http://php.net/manual/en/function.is-scalar.php
 * @param var mixed
 * @return bool true if var is a scalar false
 */
function is_scalar ($var) {}

/**
 * Verify that the contents of a variable can be called as a function
 * @link http://php.net/manual/en/function.is-callable.php
 * @param var mixed
 * @param syntax_only bool[optional]
 * @param callable_name string[optional]
 * @return bool true if var is callable, false
 */
function is_callable ($var, $syntax_only = null, &$callable_name = null) {}

/**
 * Regular expression match
 * @link http://php.net/manual/en/function.ereg.php
 * @param pattern string
 * @param string string
 * @param regs array[optional]
 * @return int the length of the matched string if a match for
 */
function ereg ($pattern, $string, array &$regs = null) {}

/**
 * Replace regular expression
 * @link http://php.net/manual/en/function.ereg-replace.php
 * @param pattern string
 * @param replacement string
 * @param string string
 * @return string 
 */
function ereg_replace ($pattern, $replacement, $string) {}

/**
 * Case insensitive regular expression match
 * @link http://php.net/manual/en/function.eregi.php
 * @param pattern string
 * @param string string
 * @param regs array[optional]
 * @return int the length of the matched string if a match for
 */
function eregi ($pattern, $string, array &$regs = null) {}

/**
 * Replace regular expression case insensitive
 * @link http://php.net/manual/en/function.eregi-replace.php
 * @param pattern string
 * @param replacement string
 * @param string string
 * @return string 
 */
function eregi_replace ($pattern, $replacement, $string) {}

/**
 * Split string into array by regular expression
 * @link http://php.net/manual/en/function.split.php
 * @param pattern string
 * @param string string
 * @param limit int[optional]
 * @return array an array of strings, each of which is a substring of
 */
function split ($pattern, $string, $limit = null) {}

/**
 * Split string into array by regular expression case insensitive
 * @link http://php.net/manual/en/function.spliti.php
 * @param pattern string
 * @param string string
 * @param limit int[optional]
 * @return array an array of strings, each of which is a substring of
 */
function spliti ($pattern, $string, $limit = null) {}

/**
 * &Alias; <function>implode</function>
 * @link http://php.net/manual/en/function.join.php
 * @param glue
 * @param pieces
 */
function join ($glue, $pieces) {}

/**
 * Make regular expression for case insensitive match
 * @link http://php.net/manual/en/function.sql-regcase.php
 * @param string string
 * @return string a valid regular expression which will match
 */
function sql_regcase ($string) {}

/**
 * Loads a PHP extension at runtime
 * @link http://php.net/manual/en/function.dl.php
 * @param library string
 * @return int 
 */
function dl ($library) {}

/**
 * Closes process file pointer
 * @link http://php.net/manual/en/function.pclose.php
 * @param handle resource
 * @return int the termination status of the process that was run.
 */
function pclose ($handle) {}

/**
 * Opens process file pointer
 * @link http://php.net/manual/en/function.popen.php
 * @param command string
 * @param mode string
 * @return resource a file pointer identical to that returned by
 */
function popen ($command, $mode) {}

/**
 * Outputs a file
 * @link http://php.net/manual/en/function.readfile.php
 * @param filename string
 * @param use_include_path bool[optional]
 * @param context resource[optional]
 * @return int the number of bytes read from the file. If an error
 */
function readfile ($filename, $use_include_path = null, $context = null) {}

/**
 * Rewind the position of a file pointer
 * @link http://php.net/manual/en/function.rewind.php
 * @param handle resource
 * @return bool 
 */
function rewind ($handle) {}

/**
 * Removes directory
 * @link http://php.net/manual/en/function.rmdir.php
 * @param dirname string
 * @param context resource[optional]
 * @return bool 
 */
function rmdir ($dirname, $context = null) {}

/**
 * Changes the current umask
 * @link http://php.net/manual/en/function.umask.php
 * @param mask int[optional]
 * @return int 
 */
function umask ($mask = null) {}

/**
 * Closes an open file pointer
 * @link http://php.net/manual/en/function.fclose.php
 * @param handle resource
 * @return bool 
 */
function fclose ($handle) {}

/**
 * Tests for end-of-file on a file pointer
 * @link http://php.net/manual/en/function.feof.php
 * @param handle resource
 * @return bool true if the file pointer is at EOF or an error occurs
 */
function feof ($handle) {}

/**
 * Gets character from file pointer
 * @link http://php.net/manual/en/function.fgetc.php
 * @param handle resource
 * @return string a string containing a single character read from the file pointed
 */
function fgetc ($handle) {}

/**
 * Gets line from file pointer
 * @link http://php.net/manual/en/function.fgets.php
 * @param handle resource
 * @param length int[optional]
 * @return string a string of up to length - 1 bytes read from
 */
function fgets ($handle, $length = null) {}

/**
 * Gets line from file pointer and strip HTML tags
 * @link http://php.net/manual/en/function.fgetss.php
 * @param handle resource
 * @param length int[optional]
 * @param allowable_tags string[optional]
 * @return string a string of up to length - 1 bytes read from
 */
function fgetss ($handle, $length = null, $allowable_tags = null) {}

/**
 * Binary-safe file read
 * @link http://php.net/manual/en/function.fread.php
 * @param handle resource
 * @param length int
 * @return string the read string or false in case of error.
 */
function fread ($handle, $length) {}

/**
 * Opens file or URL
 * @link http://php.net/manual/en/function.fopen.php
 * @param filename string
 * @param mode string
 * @param use_include_path bool[optional]
 * @param context resource[optional]
 * @return resource a file pointer resource on success, or false on error.
 */
function fopen ($filename, $mode, $use_include_path = null, $context = null) {}

/**
 * Output all remaining data on a file pointer
 * @link http://php.net/manual/en/function.fpassthru.php
 * @param handle resource
 * @return int 
 */
function fpassthru ($handle) {}

/**
 * Truncates a file to a given length
 * @link http://php.net/manual/en/function.ftruncate.php
 * @param handle resource
 * @param size int
 * @return bool 
 */
function ftruncate ($handle, $size) {}

/**
 * Gets information about a file using an open file pointer
 * @link http://php.net/manual/en/function.fstat.php
 * @param handle resource
 * @return array an array with the statistics of the file; the format of the array
 */
function fstat ($handle) {}

/**
 * Seeks on a file pointer
 * @link http://php.net/manual/en/function.fseek.php
 * @param handle resource
 * @param offset int
 * @param whence int[optional]
 * @return int 
 */
function fseek ($handle, $offset, $whence = null) {}

/**
 * Tells file pointer read/write position
 * @link http://php.net/manual/en/function.ftell.php
 * @param handle resource
 * @return int the position of the file pointer referenced by
 */
function ftell ($handle) {}

/**
 * Flushes the output to a file
 * @link http://php.net/manual/en/function.fflush.php
 * @param handle resource
 * @return bool 
 */
function fflush ($handle) {}

/**
 * Binary-safe file write
 * @link http://php.net/manual/en/function.fwrite.php
 * @param handle resource
 * @param string string
 * @param length int[optional]
 * @return int 
 */
function fwrite ($handle, $string, $length = null) {}

/**
 * &Alias; <function>fwrite</function>
 * @link http://php.net/manual/en/function.fputs.php
 * @param fp
 * @param str
 * @param length[optional]
 */
function fputs ($fp, $str, $length) {}

/**
 * Makes directory
 * @link http://php.net/manual/en/function.mkdir.php
 * @param pathname string
 * @param mode int[optional]
 * @param recursive bool[optional]
 * @param context resource[optional]
 * @return bool 
 */
function mkdir ($pathname, $mode = null, $recursive = null, $context = null) {}

/**
 * Renames a file or directory
 * @link http://php.net/manual/en/function.rename.php
 * @param oldname string
 * @param newname string
 * @param context resource[optional]
 * @return bool 
 */
function rename ($oldname, $newname, $context = null) {}

/**
 * Copies file
 * @link http://php.net/manual/en/function.copy.php
 * @param source string
 * @param dest string
 * @param context resource[optional]
 * @return bool 
 */
function copy ($source, $dest, $context = null) {}

/**
 * Create file with unique file name
 * @link http://php.net/manual/en/function.tempnam.php
 * @param dir string
 * @param prefix string
 * @return string the new temporary filename, or false on
 */
function tempnam ($dir, $prefix) {}

/**
 * Creates a temporary file
 * @link http://php.net/manual/en/function.tmpfile.php
 * @return resource a file handle, similar to the one returned by
 */
function tmpfile () {}

/**
 * Reads entire file into an array
 * @link http://php.net/manual/en/function.file.php
 * @param filename string
 * @param flags int[optional]
 * @param context resource[optional]
 * @return array the file in an array. Each element of the array corresponds to a
 */
function file ($filename, $flags = null, $context = null) {}

/**
 * Reads entire file into a string
 * @link http://php.net/manual/en/function.file-get-contents.php
 * @param filename string
 * @param flags int[optional]
 * @param context resource[optional]
 * @param offset int[optional]
 * @param maxlen int[optional]
 * @return string 
 */
function file_get_contents ($filename, $flags = null, $context = null, $offset = null, $maxlen = null) {}

/**
 * Write a string to a file
 * @link http://php.net/manual/en/function.file-put-contents.php
 * @param filename string
 * @param data mixed
 * @param flags int[optional]
 * @param context resource[optional]
 * @return int 
 */
function file_put_contents ($filename, $data, $flags = null, $context = null) {}

/**
 * Runs the equivalent of the select() system call on the given
   arrays of streams with a timeout specified by tv_sec and tv_usec
 * @link http://php.net/manual/en/function.stream-select.php
 * @param read_streams
 * @param write_streams
 * @param except_streams
 * @param tv_sec
 * @param tv_usec[optional]
 */
function stream_select (&$read_streams, &$write_streams, &$except_streams, $tv_sec, $tv_usec) {}

/**
 * Create a streams context
 * @link http://php.net/manual/en/function.stream-context-create.php
 * @param options[optional]
 */
function stream_context_create ($options) {}

/**
 * Set parameters for a stream/wrapper/context
 * @link http://php.net/manual/en/function.stream-context-set-params.php
 * @param stream_or_context
 * @param options
 */
function stream_context_set_params ($stream_or_context, $options) {}

/**
 * Sets an option for a stream/wrapper/context
 * @link http://php.net/manual/en/function.stream-context-set-option.php
 * @param stream_or_context
 * @param wrappername
 * @param optionname
 * @param value
 */
function stream_context_set_option ($stream_or_context, $wrappername, $optionname, $value) {}

/**
 * Retrieve options for a stream/wrapper/context
 * @link http://php.net/manual/en/function.stream-context-get-options.php
 * @param stream_or_context
 */
function stream_context_get_options ($stream_or_context) {}

/**
 * Retreive the default streams context
 * @link http://php.net/manual/en/function.stream-context-get-default.php
 * @param options[optional]
 */
function stream_context_get_default ($options) {}

/**
 * Attach a filter to a stream
 * @link http://php.net/manual/en/function.stream-filter-prepend.php
 * @param stream
 * @param filtername
 * @param read_write[optional]
 * @param filterparams[optional]
 */
function stream_filter_prepend ($stream, $filtername, $read_write, $filterparams) {}

/**
 * Attach a filter to a stream
 * @link http://php.net/manual/en/function.stream-filter-append.php
 * @param stream
 * @param filtername
 * @param read_write[optional]
 * @param filterparams[optional]
 */
function stream_filter_append ($stream, $filtername, $read_write, $filterparams) {}

/**
 * Remove a filter from a stream
 * @link http://php.net/manual/en/function.stream-filter-remove.php
 * @param stream_filter
 */
function stream_filter_remove ($stream_filter) {}

/**
 * Open Internet or Unix domain socket connection
 * @link http://php.net/manual/en/function.stream-socket-client.php
 * @param remoteaddress
 * @param errcode[optional]
 * @param errstring[optional]
 * @param timeout[optional]
 * @param flags[optional]
 * @param context[optional]
 */
function stream_socket_client ($remoteaddress, &$errcode, &$errstring, $timeout, $flags, $context) {}

/**
 * Create an Internet or Unix domain server socket
 * @link http://php.net/manual/en/function.stream-socket-server.php
 * @param localaddress
 * @param errcode[optional]
 * @param errstring[optional]
 * @param flags[optional]
 * @param context[optional]
 */
function stream_socket_server ($localaddress, &$errcode, &$errstring, $flags, $context) {}

/**
 * Accept a connection on a socket created by <function>stream_socket_server</function>
 * @link http://php.net/manual/en/function.stream-socket-accept.php
 * @param serverstream
 * @param timeout[optional]
 * @param peername[optional]
 */
function stream_socket_accept ($serverstream, $timeout, &$peername) {}

/**
 * Retrieve the name of the local or remote sockets
 * @link http://php.net/manual/en/function.stream-socket-get-name.php
 * @param stream
 * @param want_peer
 */
function stream_socket_get_name ($stream, $want_peer) {}

/**
 * Receives data from a socket, connected or not
 * @link http://php.net/manual/en/function.stream-socket-recvfrom.php
 * @param stream
 * @param amount
 * @param flags[optional]
 * @param remote_addr[optional]
 */
function stream_socket_recvfrom ($stream, $amount, $flags, &$remote_addr) {}

/**
 * Sends a message to a socket, whether it is connected or not
 * @link http://php.net/manual/en/function.stream-socket-sendto.php
 * @param stream
 * @param data
 * @param flags[optional]
 * @param target_addr[optional]
 */
function stream_socket_sendto ($stream, $data, $flags, $target_addr) {}

/**
 * Turns encryption on/off on an already connected socket
 * @link http://php.net/manual/en/function.stream-socket-enable-crypto.php
 * @param stream
 * @param enable
 * @param cryptokind[optional]
 * @param sessionstream[optional]
 */
function stream_socket_enable_crypto ($stream, $enable, $cryptokind, $sessionstream) {}

/**
 * Shutdown a full-duplex connection
 * @link http://php.net/manual/en/function.stream-socket-shutdown.php
 * @param stream resource
 * @param how int
 * @return bool 
 */
function stream_socket_shutdown ($stream, $how) {}

/**
 * Creates a pair of connected, indistinguishable socket streams
 * @link http://php.net/manual/en/function.stream-socket-pair.php
 * @param domain int
 * @param type int
 * @param protocol int
 * @return array an array with the two socket resources on success, or
 */
function stream_socket_pair ($domain, $type, $protocol) {}

/**
 * Copies data from one stream to another
 * @link http://php.net/manual/en/function.stream-copy-to-stream.php
 * @param source resource
 * @param dest resource
 * @param maxlength int[optional]
 * @param offset int[optional]
 * @return int the total count of bytes copied.
 */
function stream_copy_to_stream ($source, $dest, $maxlength = null, $offset = null) {}

/**
 * Reads remainder of a stream into a string
 * @link http://php.net/manual/en/function.stream-get-contents.php
 * @param source
 * @param maxlen[optional]
 * @param offset[optional]
 */
function stream_get_contents ($source, $maxlen, $offset) {}

/**
 * Gets line from file pointer and parse for CSV fields
 * @link http://php.net/manual/en/function.fgetcsv.php
 * @param handle resource
 * @param length int[optional]
 * @param delimiter string[optional]
 * @param enclosure string[optional]
 * @param escape string[optional]
 * @return array an indexed array containing the fields read.
 */
function fgetcsv ($handle, $length = null, $delimiter = null, $enclosure = null, $escape = null) {}

/**
 * Format line as CSV and write to file pointer
 * @link http://php.net/manual/en/function.fputcsv.php
 * @param handle resource
 * @param fields array
 * @param delimiter string[optional]
 * @param enclosure string[optional]
 * @return int the length of the written string, or false on failure.
 */
function fputcsv ($handle, array $fields, $delimiter = null, $enclosure = null) {}

/**
 * Portable advisory file locking
 * @link http://php.net/manual/en/function.flock.php
 * @param handle resource
 * @param operation int
 * @param wouldblock int[optional]
 * @return bool 
 */
function flock ($handle, $operation, &$wouldblock = null) {}

/**
 * Extracts all meta tag content attributes from a file and returns an array
 * @link http://php.net/manual/en/function.get-meta-tags.php
 * @param filename string
 * @param use_include_path bool[optional]
 * @return array an array with all the parsed meta tags.
 */
function get_meta_tags ($filename, $use_include_path = null) {}

/**
 * Sets file buffering on the given stream
 * @link http://php.net/manual/en/function.stream-set-write-buffer.php
 * @param fp
 * @param buffer
 */
function stream_set_write_buffer ($fp, $buffer) {}

/**
 * &Alias; <function>stream_set_write_buffer</function>
 * @link http://php.net/manual/en/function.set-file-buffer.php
 * @param fp
 * @param buffer
 */
function set_file_buffer ($fp, $buffer) {}

/**
 * @param socket
 * @param mode
 */
function set_socket_blocking ($socket, $mode) {}

/**
 * Set blocking/non-blocking mode on a stream
 * @link http://php.net/manual/en/function.stream-set-blocking.php
 * @param socket
 * @param mode
 */
function stream_set_blocking ($socket, $mode) {}

/**
 * &Alias; <function>stream_set_blocking</function>
 * @link http://php.net/manual/en/function.socket-set-blocking.php
 * @param socket
 * @param mode
 */
function socket_set_blocking ($socket, $mode) {}

/**
 * Retrieves header/meta data from streams/file pointers
 * @link http://php.net/manual/en/function.stream-get-meta-data.php
 * @param fp
 */
function stream_get_meta_data ($fp) {}

/**
 * Gets line from stream resource up to a given delimiter
 * @link http://php.net/manual/en/function.stream-get-line.php
 * @param stream
 * @param maxlen
 * @param ending[optional]
 */
function stream_get_line ($stream, $maxlen, $ending) {}

/**
 * Register a URL wrapper implemented as a PHP class
 * @link http://php.net/manual/en/function.stream-wrapper-register.php
 * @param protocol
 * @param classname
 * @param flags[optional]
 */
function stream_wrapper_register ($protocol, $classname, $flags) {}

/**
 * &Alias; <function>stream_wrapper_register</function>
 * @link http://php.net/manual/en/function.stream-register-wrapper.php
 * @param protocol
 * @param classname
 * @param flags[optional]
 */
function stream_register_wrapper ($protocol, $classname, $flags) {}

/**
 * Unregister a URL wrapper
 * @link http://php.net/manual/en/function.stream-wrapper-unregister.php
 * @param protocol string
 * @return bool 
 */
function stream_wrapper_unregister ($protocol) {}

/**
 * Restores a previously unregistered built-in wrapper
 * @link http://php.net/manual/en/function.stream-wrapper-restore.php
 * @param protocol string
 * @return bool 
 */
function stream_wrapper_restore ($protocol) {}

/**
 * Retrieve list of registered streams
 * @link http://php.net/manual/en/function.stream-get-wrappers.php
 */
function stream_get_wrappers () {}

/**
 * Retrieve list of registered socket transports
 * @link http://php.net/manual/en/function.stream-get-transports.php
 */
function stream_get_transports () {}

/**
 * @param stream
 */
function stream_is_local ($stream) {}

/**
 * Fetches all the headers sent by the server in response to a HTTP request
 * @link http://php.net/manual/en/function.get-headers.php
 * @param url string
 * @param format int[optional]
 * @return array an indexed or associative array with the headers, or false on
 */
function get_headers ($url, $format = null) {}

/**
 * Set timeout period on a stream
 * @link http://php.net/manual/en/function.stream-set-timeout.php
 * @param stream
 * @param seconds
 * @param microseconds
 */
function stream_set_timeout ($stream, $seconds, $microseconds) {}

/**
 * &Alias; <function>stream_set_timeout</function>
 * @link http://php.net/manual/en/function.socket-set-timeout.php
 * @param stream
 * @param seconds
 * @param microseconds
 */
function socket_set_timeout ($stream, $seconds, $microseconds) {}

/**
 * &Alias; <function>stream_get_meta_data</function>
 * @link http://php.net/manual/en/function.socket-get-status.php
 * @param fp
 */
function socket_get_status ($fp) {}

/**
 * Returns canonicalized absolute pathname
 * @link http://php.net/manual/en/function.realpath.php
 * @param path string
 * @return string the canonicalized absolute pathname on success. The resulting path
 */
function realpath ($path) {}

/**
 * Match filename against a pattern
 * @link http://php.net/manual/en/function.fnmatch.php
 * @param pattern string
 * @param string string
 * @param flags int[optional]
 * @return bool true if there is a match, false otherwise.
 */
function fnmatch ($pattern, $string, $flags = null) {}

/**
 * Open Internet or Unix domain socket connection
 * @link http://php.net/manual/en/function.fsockopen.php
 * @param hostname string
 * @param port int[optional]
 * @param errno int[optional]
 * @param errstr string[optional]
 * @param timeout float[optional]
 * @return resource 
 */
function fsockopen ($hostname, $port = null, &$errno = null, &$errstr = null, $timeout = null) {}

/**
 * Open persistent Internet or Unix domain socket connection
 * @link http://php.net/manual/en/function.pfsockopen.php
 * @param hostname string
 * @param port int[optional]
 * @param errno int[optional]
 * @param errstr string[optional]
 * @param timeout float[optional]
 * @return resource 
 */
function pfsockopen ($hostname, $port = null, &$errno = null, &$errstr = null, $timeout = null) {}

/**
 * Pack data into binary string
 * @link http://php.net/manual/en/function.pack.php
 * @param format string
 * @param args mixed[optional]
 * @param _ mixed[optional]
 * @return string a binary string containing data.
 */
function pack ($format, $args = null, $_ = null) {}

/**
 * Unpack data from binary string
 * @link http://php.net/manual/en/function.unpack.php
 * @param format string
 * @param data string
 * @return array an associative array containing unpacked elements of binary
 */
function unpack ($format, $data) {}

/**
 * Tells what the user's browser is capable of
 * @link http://php.net/manual/en/function.get-browser.php
 * @param user_agent string[optional]
 * @param return_array bool[optional]
 * @return mixed 
 */
function get_browser ($user_agent = null, $return_array = null) {}

/**
 * One-way string encryption (hashing)
 * @link http://php.net/manual/en/function.crypt.php
 * @param str string
 * @param salt string[optional]
 * @return string the encrypted string.
 */
function crypt ($str, $salt = null) {}

/**
 * Open directory handle
 * @link http://php.net/manual/en/function.opendir.php
 * @param path string
 * @param context resource[optional]
 * @return resource a directory handle resource on success, or
 */
function opendir ($path, $context = null) {}

/**
 * Close directory handle
 * @link http://php.net/manual/en/function.closedir.php
 * @param dir_handle resource
 * @return void 
 */
function closedir ($dir_handle) {}

/**
 * Change directory
 * @link http://php.net/manual/en/function.chdir.php
 * @param directory string
 * @return bool 
 */
function chdir ($directory) {}

/**
 * Gets the current working directory
 * @link http://php.net/manual/en/function.getcwd.php
 * @return string the current working directory on success, or false on
 */
function getcwd () {}

/**
 * Rewind directory handle
 * @link http://php.net/manual/en/function.rewinddir.php
 * @param dir_handle resource
 * @return void 
 */
function rewinddir ($dir_handle) {}

/**
 * Read entry from directory handle
 * @link http://php.net/manual/en/function.readdir.php
 * @param dir_handle resource
 * @return string the filename on success, or false on failure.
 */
function readdir ($dir_handle) {}

/**
 * Return an instance of the Directory class
 * @link http://php.net/manual/en/class.dir.php
 * @param directory
 * @param context[optional]
 * @return string 
 */
function dir ($directory, $context) {}

/**
 * List files and directories inside the specified path
 * @link http://php.net/manual/en/function.scandir.php
 * @param directory string
 * @param sorting_order int[optional]
 * @param context resource[optional]
 * @return array an array of filenames on success, or false on
 */
function scandir ($directory, $sorting_order = null, $context = null) {}

/**
 * Find pathnames matching a pattern
 * @link http://php.net/manual/en/function.glob.php
 * @param pattern string
 * @param flags int[optional]
 * @return array an array containing the matched files/directories, an empty array
 */
function glob ($pattern, $flags = null) {}

/**
 * Gets last access time of file
 * @link http://php.net/manual/en/function.fileatime.php
 * @param filename string
 * @return int the time the file was last accessed, or false in case of
 */
function fileatime ($filename) {}

/**
 * Gets inode change time of file
 * @link http://php.net/manual/en/function.filectime.php
 * @param filename string
 * @return int the time the file was last changed, or false in case of
 */
function filectime ($filename) {}

/**
 * Gets file group
 * @link http://php.net/manual/en/function.filegroup.php
 * @param filename string
 * @return int the group ID of the file, or false in case
 */
function filegroup ($filename) {}

/**
 * Gets file inode
 * @link http://php.net/manual/en/function.fileinode.php
 * @param filename string
 * @return int the inode number of the file, or false in case of an error.
 */
function fileinode ($filename) {}

/**
 * Gets file modification time
 * @link http://php.net/manual/en/function.filemtime.php
 * @param filename string
 * @return int the time the file was last modified, or false in case of
 */
function filemtime ($filename) {}

/**
 * Gets file owner
 * @link http://php.net/manual/en/function.fileowner.php
 * @param filename string
 * @return int the user ID of the owner of the file, or false in case of
 */
function fileowner ($filename) {}

/**
 * Gets file permissions
 * @link http://php.net/manual/en/function.fileperms.php
 * @param filename string
 * @return int the permissions on the file, or false in case of an error.
 */
function fileperms ($filename) {}

/**
 * Gets file size
 * @link http://php.net/manual/en/function.filesize.php
 * @param filename string
 * @return int the size of the file in bytes, or false (and generates an error
 */
function filesize ($filename) {}

/**
 * Gets file type
 * @link http://php.net/manual/en/function.filetype.php
 * @param filename string
 * @return string the type of the file. Possible values are fifo, char,
 */
function filetype ($filename) {}

/**
 * Checks whether a file or directory exists
 * @link http://php.net/manual/en/function.file-exists.php
 * @param filename string
 * @return bool true if the file or directory specified by
 */
function file_exists ($filename) {}

/**
 * Tells whether the filename is writable
 * @link http://php.net/manual/en/function.is-writable.php
 * @param filename string
 * @return bool true if the filename exists and is
 */
function is_writable ($filename) {}

/**
 * &Alias; <function>is_writable</function>
 * @link http://php.net/manual/en/function.is-writeable.php
 * @param filename
 */
function is_writeable ($filename) {}

/**
 * Tells whether the filename is readable
 * @link http://php.net/manual/en/function.is-readable.php
 * @param filename string
 * @return bool true if the file or directory specified by
 */
function is_readable ($filename) {}

/**
 * Tells whether the filename is executable
 * @link http://php.net/manual/en/function.is-executable.php
 * @param filename string
 * @return bool true if the filename exists and is executable, or false on
 */
function is_executable ($filename) {}

/**
 * Tells whether the filename is a regular file
 * @link http://php.net/manual/en/function.is-file.php
 * @param filename string
 * @return bool true if the filename exists and is a regular file, false
 */
function is_file ($filename) {}

/**
 * Tells whether the filename is a directory
 * @link http://php.net/manual/en/function.is-dir.php
 * @param filename string
 * @return bool true if the filename exists and is a directory, false
 */
function is_dir ($filename) {}

/**
 * Tells whether the filename is a symbolic link
 * @link http://php.net/manual/en/function.is-link.php
 * @param filename string
 * @return bool true if the filename exists and is a symbolic link, false
 */
function is_link ($filename) {}

/**
 * Gives information about a file
 * @link http://php.net/manual/en/function.stat.php
 * @param filename string
 * @return array 
 */
function stat ($filename) {}

/**
 * Gives information about a file or symbolic link
 * @link http://php.net/manual/en/function.lstat.php
 * @param filename string
 * @return array 
 */
function lstat ($filename) {}

/**
 * Changes file owner
 * @link http://php.net/manual/en/function.chown.php
 * @param filename string
 * @param user mixed
 * @return bool 
 */
function chown ($filename, $user) {}

/**
 * Changes file group
 * @link http://php.net/manual/en/function.chgrp.php
 * @param filename string
 * @param group mixed
 * @return bool 
 */
function chgrp ($filename, $group) {}

/**
 * Changes user ownership of symlink
 * @link http://php.net/manual/en/function.lchown.php
 * @param filename string
 * @param user mixed
 * @return bool 
 */
function lchown ($filename, $user) {}

/**
 * Changes group ownership of symlink
 * @link http://php.net/manual/en/function.lchgrp.php
 * @param filename string
 * @param group mixed
 * @return bool 
 */
function lchgrp ($filename, $group) {}

/**
 * Changes file mode
 * @link http://php.net/manual/en/function.chmod.php
 * @param filename string
 * @param mode int
 * @return bool 
 */
function chmod ($filename, $mode) {}

/**
 * Sets access and modification time of file
 * @link http://php.net/manual/en/function.touch.php
 * @param filename string
 * @param time int[optional]
 * @param atime int[optional]
 * @return bool 
 */
function touch ($filename, $time = null, $atime = null) {}

/**
 * Clears file status cache
 * @link http://php.net/manual/en/function.clearstatcache.php
 * @return void 
 */
function clearstatcache () {}

/**
 * Returns the total size of a directory
 * @link http://php.net/manual/en/function.disk-total-space.php
 * @param directory string
 * @return float the total number of bytes as a float.
 */
function disk_total_space ($directory) {}

/**
 * Returns available space in directory
 * @link http://php.net/manual/en/function.disk-free-space.php
 * @param directory string
 * @return float the number of available bytes as a float.
 */
function disk_free_space ($directory) {}

/**
 * &Alias; <function>disk_free_space</function>
 * @link http://php.net/manual/en/function.diskfreespace.php
 * @param path
 */
function diskfreespace ($path) {}

/**
 * Send mail
 * @link http://php.net/manual/en/function.mail.php
 * @param to string
 * @param subject string
 * @param message string
 * @param additional_headers string[optional]
 * @param additional_parameters string[optional]
 * @return bool true if the mail was successfully accepted for delivery, false otherwise.
 */
function mail ($to, $subject, $message, $additional_headers = null, $additional_parameters = null) {}

/**
 * Calculate the hash value needed by EZMLM
 * @link http://php.net/manual/en/function.ezmlm-hash.php
 * @param addr string
 * @return int 
 */
function ezmlm_hash ($addr) {}

/**
 * Open connection to system logger
 * @link http://php.net/manual/en/function.openlog.php
 * @param ident string
 * @param option int
 * @param facility int
 * @return bool 
 */
function openlog ($ident, $option, $facility) {}

/**
 * Generate a system log message
 * @link http://php.net/manual/en/function.syslog.php
 * @param priority int
 * @param message string
 * @return bool 
 */
function syslog ($priority, $message) {}

/**
 * Close connection to system logger
 * @link http://php.net/manual/en/function.closelog.php
 * @return bool 
 */
function closelog () {}

/**
 * Initializes all syslog related constants
 * @link http://php.net/manual/en/function.define-syslog-variables.php
 * @return void 
 */
function define_syslog_variables () {}

/**
 * Combined linear congruential generator
 * @link http://php.net/manual/en/function.lcg-value.php
 * @return float 
 */
function lcg_value () {}

/**
 * Calculate the metaphone key of a string
 * @link http://php.net/manual/en/function.metaphone.php
 * @param str string
 * @param phones int[optional]
 * @return string the metaphone key as a string.
 */
function metaphone ($str, $phones = null) {}

/**
 * Turn on output buffering
 * @link http://php.net/manual/en/function.ob-start.php
 * @param output_callback callback[optional]
 * @param chunk_size int[optional]
 * @param erase bool[optional]
 * @return bool 
 */
function ob_start ($output_callback = null, $chunk_size = null, $erase = null) {}

/**
 * Flush (send) the output buffer
 * @link http://php.net/manual/en/function.ob-flush.php
 * @return void 
 */
function ob_flush () {}

/**
 * Clean (erase) the output buffer
 * @link http://php.net/manual/en/function.ob-clean.php
 * @return void 
 */
function ob_clean () {}

/**
 * Flush (send) the output buffer and turn off output buffering
 * @link http://php.net/manual/en/function.ob-end-flush.php
 * @return bool 
 */
function ob_end_flush () {}

/**
 * Clean (erase) the output buffer and turn off output buffering
 * @link http://php.net/manual/en/function.ob-end-clean.php
 * @return bool 
 */
function ob_end_clean () {}

/**
 * Flush the output buffer, return it as a string and turn off output buffering
 * @link http://php.net/manual/en/function.ob-get-flush.php
 * @return string the output buffer or false if no buffering is active.
 */
function ob_get_flush () {}

/**
 * Get current buffer contents and delete current output buffer
 * @link http://php.net/manual/en/function.ob-get-clean.php
 * @return string the contents of the output buffer and end output buffering.
 */
function ob_get_clean () {}

/**
 * Return the length of the output buffer
 * @link http://php.net/manual/en/function.ob-get-length.php
 * @return int the length of the output buffer contents or false if no
 */
function ob_get_length () {}

/**
 * Return the nesting level of the output buffering mechanism
 * @link http://php.net/manual/en/function.ob-get-level.php
 * @return int the level of nested output buffering handlers or zero if output
 */
function ob_get_level () {}

/**
 * Get status of output buffers
 * @link http://php.net/manual/en/function.ob-get-status.php
 * @param full_status bool[optional]
 * @return array 
 */
function ob_get_status ($full_status = null) {}

/**
 * Return the contents of the output buffer
 * @link http://php.net/manual/en/function.ob-get-contents.php
 * @return string 
 */
function ob_get_contents () {}

/**
 * Turn implicit flush on/off
 * @link http://php.net/manual/en/function.ob-implicit-flush.php
 * @param flag int[optional]
 * @return void 
 */
function ob_implicit_flush ($flag = null) {}

/**
 * List all output handlers in use
 * @link http://php.net/manual/en/function.ob-list-handlers.php
 * @return array 
 */
function ob_list_handlers () {}

/**
 * Sort an array by key
 * @link http://php.net/manual/en/function.ksort.php
 * @param array array
 * @param sort_flags int[optional]
 * @return bool 
 */
function ksort (array &$array, $sort_flags = null) {}

/**
 * Sort an array by key in reverse order
 * @link http://php.net/manual/en/function.krsort.php
 * @param array array
 * @param sort_flags int[optional]
 * @return bool 
 */
function krsort (array &$array, $sort_flags = null) {}

/**
 * Sort an array using a "natural order" algorithm
 * @link http://php.net/manual/en/function.natsort.php
 * @param array array
 * @return bool 
 */
function natsort (array &$array) {}

/**
 * Sort an array using a case insensitive "natural order" algorithm
 * @link http://php.net/manual/en/function.natcasesort.php
 * @param array array
 * @return bool 
 */
function natcasesort (array &$array) {}

/**
 * Sort an array and maintain index association
 * @link http://php.net/manual/en/function.asort.php
 * @param array array
 * @param sort_flags int[optional]
 * @return bool 
 */
function asort (array &$array, $sort_flags = null) {}

/**
 * Sort an array in reverse order and maintain index association
 * @link http://php.net/manual/en/function.arsort.php
 * @param array array
 * @param sort_flags int[optional]
 * @return bool 
 */
function arsort (array &$array, $sort_flags = null) {}

/**
 * Sort an array
 * @link http://php.net/manual/en/function.sort.php
 * @param array array
 * @param sort_flags int[optional]
 * @return bool 
 */
function sort (array &$array, $sort_flags = null) {}

/**
 * Sort an array in reverse order
 * @link http://php.net/manual/en/function.rsort.php
 * @param array array
 * @param sort_flags int[optional]
 * @return bool 
 */
function rsort (array &$array, $sort_flags = null) {}

/**
 * Sort an array by values using a user-defined comparison function
 * @link http://php.net/manual/en/function.usort.php
 * @param array array
 * @param cmp_function callback
 * @return bool 
 */
function usort (array &$array, $cmp_function) {}

/**
 * Sort an array with a user-defined comparison function and maintain index association
 * @link http://php.net/manual/en/function.uasort.php
 * @param array array
 * @param cmp_function callback
 * @return bool 
 */
function uasort (array &$array, $cmp_function) {}

/**
 * Sort an array by keys using a user-defined comparison function
 * @link http://php.net/manual/en/function.uksort.php
 * @param array array
 * @param cmp_function callback
 * @return bool 
 */
function uksort (array &$array, $cmp_function) {}

/**
 * Shuffle an array
 * @link http://php.net/manual/en/function.shuffle.php
 * @param array array
 * @return bool 
 */
function shuffle (array &$array) {}

/**
 * Apply a user function to every member of an array
 * @link http://php.net/manual/en/function.array-walk.php
 * @param array array
 * @param funcname callback
 * @param userdata mixed[optional]
 * @return bool 
 */
function array_walk (array &$array, $funcname, $userdata = null) {}

/**
 * Apply a user function recursively to every member of an array
 * @link http://php.net/manual/en/function.array-walk-recursive.php
 * @param input array
 * @param funcname callback
 * @param userdata mixed[optional]
 * @return bool 
 */
function array_walk_recursive (array &$input, $funcname, $userdata = null) {}

/**
 * Count elements in an array, or properties in an object
 * @link http://php.net/manual/en/function.count.php
 * @param var mixed
 * @param mode int[optional]
 * @return int the number of elements in var, which is
 */
function count ($var, $mode = null) {}

/**
 * Set the internal pointer of an array to its last element
 * @link http://php.net/manual/en/function.end.php
 * @param array array
 * @return mixed the value of the last element.
 */
function end (array &$array) {}

/**
 * Rewind the internal array pointer
 * @link http://php.net/manual/en/function.prev.php
 * @param array array
 * @return mixed the array value in the previous place that's pointed to by
 */
function prev (array &$array) {}

/**
 * Advance the internal array pointer of an array
 * @link http://php.net/manual/en/function.next.php
 * @param array array
 * @return mixed the array value in the next place that's pointed to by the
 */
function next (array &$array) {}

/**
 * Set the internal pointer of an array to its first element
 * @link http://php.net/manual/en/function.reset.php
 * @param array array
 * @return mixed the value of the first array element, or false if the array is
 */
function reset (array &$array) {}

/**
 * Return the current element in an array
 * @link http://php.net/manual/en/function.current.php
 * @param array array
 * @return mixed 
 */
function current (array &$array) {}

/**
 * Fetch a key from an array
 * @link http://php.net/manual/en/function.key.php
 * @param array array
 * @return mixed the index.
 */
function key (array &$array) {}

/**
 * Find lowest value
 * @link http://php.net/manual/en/function.min.php
 * @param values array
 * @return mixed 
 */
function min (array $values) {}

/**
 * Find highest value
 * @link http://php.net/manual/en/function.max.php
 * @param values array
 * @return mixed 
 */
function max (array $values) {}

/**
 * Checks if a value exists in an array
 * @link http://php.net/manual/en/function.in-array.php
 * @param needle mixed
 * @param haystack array
 * @param strict bool[optional]
 * @return bool true if needle is found in the array,
 */
function in_array ($needle, array $haystack, $strict = null) {}

/**
 * Searches the array for a given value and returns the corresponding key if successful
 * @link http://php.net/manual/en/function.array-search.php
 * @param needle mixed
 * @param haystack array
 * @param strict bool[optional]
 * @return mixed the key for needle if it is found in the
 */
function array_search ($needle, array $haystack, $strict = null) {}

/**
 * Import variables into the current symbol table from an array
 * @link http://php.net/manual/en/function.extract.php
 * @param var_array array
 * @param extract_type int[optional]
 * @param prefix string[optional]
 * @return int the number of variables successfully imported into the symbol
 */
function extract (array $var_array, $extract_type = null, $prefix = null) {}

/**
 * Create array containing variables and their values
 * @link http://php.net/manual/en/function.compact.php
 * @param varname mixed
 * @param _ mixed[optional]
 * @return array the output array with all the variables added to it.
 */
function compact ($varname, $_ = null) {}

/**
 * Fill an array with values
 * @link http://php.net/manual/en/function.array-fill.php
 * @param start_index int
 * @param num int
 * @param value mixed
 * @return array the filled array
 */
function array_fill ($start_index, $num, $value) {}

/**
 * Fill an array with values, specifying keys
 * @link http://php.net/manual/en/function.array-fill-keys.php
 * @param keys array
 * @param value mixed
 * @return array the filled array
 */
function array_fill_keys (array $keys, $value) {}

/**
 * Create an array containing a range of elements
 * @link http://php.net/manual/en/function.range.php
 * @param low mixed
 * @param high mixed
 * @param step number[optional]
 * @return array an array of elements from low to
 */
function range ($low, $high, $step = null) {}

/**
 * Sort multiple or multi-dimensional arrays
 * @link http://php.net/manual/en/function.array-multisort.php
 * @param arr array
 * @param arg mixed[optional]
 * @param _ mixed[optional]
 * @return bool 
 */
function array_multisort (array $arr, $arg = null, $_ = null) {}

/**
 * Push one or more elements onto the end of array
 * @link http://php.net/manual/en/function.array-push.php
 * @param array array
 * @param var mixed
 * @param _ mixed[optional]
 * @return int the new number of elements in the array.
 */
function array_push (array &$array, $var, $_ = null) {}

/**
 * Pop the element off the end of array
 * @link http://php.net/manual/en/function.array-pop.php
 * @param array array
 * @return mixed the last value of array.
 */
function array_pop (array &$array) {}

/**
 * Shift an element off the beginning of array
 * @link http://php.net/manual/en/function.array-shift.php
 * @param array array
 * @return mixed the shifted value, or &null; if array is
 */
function array_shift (array &$array) {}

/**
 * Prepend one or more elements to the beginning of an array
 * @link http://php.net/manual/en/function.array-unshift.php
 * @param array array
 * @param var mixed
 * @param _ mixed[optional]
 * @return int the new number of elements in the array.
 */
function array_unshift (array &$array, $var, $_ = null) {}

/**
 * Remove a portion of the array and replace it with something else
 * @link http://php.net/manual/en/function.array-splice.php
 * @param input array
 * @param offset int
 * @param length int[optional]
 * @param replacement mixed[optional]
 * @return array the array consisting of the extracted elements.
 */
function array_splice (array &$input, $offset, $length = null, $replacement = null) {}

/**
 * Extract a slice of the array
 * @link http://php.net/manual/en/function.array-slice.php
 * @param array array
 * @param offset int
 * @param length int[optional]
 * @param preserve_keys bool[optional]
 * @return array the slice.
 */
function array_slice (array $array, $offset, $length = null, $preserve_keys = null) {}

/**
 * Merge one or more arrays
 * @link http://php.net/manual/en/function.array-merge.php
 * @param array1 array
 * @param array2 array[optional]
 * @param _ array[optional]
 * @return array the resulting array.
 */
function array_merge (array $array1, array $array2 = null, array $_ = null) {}

/**
 * Merge two or more arrays recursively
 * @link http://php.net/manual/en/function.array-merge-recursive.php
 * @param array1 array
 * @param _ array[optional]
 * @return array 
 */
function array_merge_recursive (array $array1, array $_ = null) {}

/**
 * Return all the keys of an array
 * @link http://php.net/manual/en/function.array-keys.php
 * @param input array
 * @param search_value mixed[optional]
 * @param strict bool[optional]
 * @return array an array of all the keys in input.
 */
function array_keys (array $input, $search_value = null, $strict = null) {}

/**
 * Return all the values of an array
 * @link http://php.net/manual/en/function.array-values.php
 * @param input array
 * @return array an indexed array of values.
 */
function array_values (array $input) {}

/**
 * Counts all the values of an array
 * @link http://php.net/manual/en/function.array-count-values.php
 * @param input array
 * @return array an assosiative array of values from input as
 */
function array_count_values (array $input) {}

/**
 * Return an array with elements in reverse order
 * @link http://php.net/manual/en/function.array-reverse.php
 * @param array array
 * @param preserve_keys bool[optional]
 * @return array the reversed array.
 */
function array_reverse (array $array, $preserve_keys = null) {}

/**
 * Iteratively reduce the array to a single value using a callback function
 * @link http://php.net/manual/en/function.array-reduce.php
 * @param input array
 * @param function callback
 * @param initial int[optional]
 * @return mixed the resulting value.
 */
function array_reduce (array $input, $function, $initial = null) {}

/**
 * Pad array to the specified length with a value
 * @link http://php.net/manual/en/function.array-pad.php
 * @param input array
 * @param pad_size int
 * @param pad_value mixed
 * @return array a copy of the input padded to size specified
 */
function array_pad (array $input, $pad_size, $pad_value) {}

/**
 * Exchanges all keys with their associated values in an array
 * @link http://php.net/manual/en/function.array-flip.php
 * @param trans array
 * @return array the flipped array on success and false on failure.
 */
function array_flip (array $trans) {}

/**
 * Changes all keys in an array
 * @link http://php.net/manual/en/function.array-change-key-case.php
 * @param input array
 * @param case int[optional]
 * @return array an array with its keys lower or uppercased, or false if
 */
function array_change_key_case (array $input, $case = null) {}

/**
 * Pick one or more random entries out of an array
 * @link http://php.net/manual/en/function.array-rand.php
 * @param input array
 * @param num_req int[optional]
 * @return mixed 
 */
function array_rand (array $input, $num_req = null) {}

/**
 * Removes duplicate values from an array
 * @link http://php.net/manual/en/function.array-unique.php
 * @param array array
 * @return array the filtered array.
 */
function array_unique (array $array) {}

/**
 * Computes the intersection of arrays
 * @link http://php.net/manual/en/function.array-intersect.php
 * @param array1 array
 * @param array2 array
 * @param _ array[optional]
 * @return array an array containing all of the values in
 */
function array_intersect (array $array1, array $array2, array $_ = null) {}

/**
 * Computes the intersection of arrays using keys for comparison
 * @link http://php.net/manual/en/function.array-intersect-key.php
 * @param array1 array
 * @param array2 array
 * @param _ array[optional]
 * @return array an associative array containing all the values of
 */
function array_intersect_key (array $array1, array $array2, array $_ = null) {}

/**
 * Computes the intersection of arrays using a callback function on the keys for comparison
 * @link http://php.net/manual/en/function.array-intersect-ukey.php
 * @param array1 array
 * @param array2 array
 * @param _ array[optional]
 * @param key_compare_func callback
 * @return array the values of array1 whose keys exist
 */
function array_intersect_ukey (array $array1, array $array2, array $_ = null, $key_compare_func) {}

/**
 * Computes the intersection of arrays, compares data by a callback function
 * @link http://php.net/manual/en/function.array-uintersect.php
 * @param array1 array
 * @param array2 array
 * @param _ array[optional]
 * @param data_compare_func callback
 * @return array an array containing all the values of array1
 */
function array_uintersect (array $array1, array $array2, array $_ = null, $data_compare_func) {}

/**
 * Computes the intersection of arrays with additional index check
 * @link http://php.net/manual/en/function.array-intersect-assoc.php
 * @param array1 array
 * @param array2 array
 * @param _ array[optional]
 * @return array an associative array containing all the values in
 */
function array_intersect_assoc (array $array1, array $array2, array $_ = null) {}

/**
 * Computes the intersection of arrays with additional index check, compares data by a callback function
 * @link http://php.net/manual/en/function.array-uintersect-assoc.php
 * @param array1 array
 * @param array2 array
 * @param _ array[optional]
 * @param data_compare_func callback
 * @return array an array containing all the values of
 */
function array_uintersect_assoc (array $array1, array $array2, array $_ = null, $data_compare_func) {}

/**
 * Computes the intersection of arrays with additional index check, compares indexes by a callback function
 * @link http://php.net/manual/en/function.array-intersect-uassoc.php
 * @param array1 array
 * @param array2 array
 * @param _ array[optional]
 * @param key_compare_func callback
 * @return array the values of array1 whose values exist
 */
function array_intersect_uassoc (array $array1, array $array2, array $_ = null, $key_compare_func) {}

/**
 * Computes the intersection of arrays with additional index check, compares data and indexes by a callback functions
 * @link http://php.net/manual/en/function.array-uintersect-uassoc.php
 * @param array1 array
 * @param array2 array
 * @param _ array[optional]
 * @param data_compare_func callback
 * @param key_compare_func callback
 * @return array an array containing all the values of
 */
function array_uintersect_uassoc (array $array1, array $array2, array $_ = null, $data_compare_func, $key_compare_func) {}

/**
 * Computes the difference of arrays
 * @link http://php.net/manual/en/function.array-diff.php
 * @param array1 array
 * @param array2 array
 * @param _ array[optional]
 * @return array an array containing all the entries from
 */
function array_diff (array $array1, array $array2, array $_ = null) {}

/**
 * Computes the difference of arrays using keys for comparison
 * @link http://php.net/manual/en/function.array-diff-key.php
 * @param array1 array
 * @param array2 array
 * @param _ array[optional]
 * @return array an array containing all the entries from
 */
function array_diff_key (array $array1, array $array2, array $_ = null) {}

/**
 * Computes the difference of arrays using a callback function on the keys for comparison
 * @link http://php.net/manual/en/function.array-diff-ukey.php
 * @param array1 array
 * @param array2 array
 * @param _ array[optional]
 * @param key_compare_func callback
 * @return array an array containing all the entries from
 */
function array_diff_ukey (array $array1, array $array2, array $_ = null, $key_compare_func) {}

/**
 * Computes the difference of arrays by using a callback function for data comparison
 * @link http://php.net/manual/en/function.array-udiff.php
 * @param array1 array
 * @param array2 array
 * @param _ array[optional]
 * @param data_compare_func callback
 * @return array an array containing all the values of array1
 */
function array_udiff (array $array1, array $array2, array $_ = null, $data_compare_func) {}

/**
 * Computes the difference of arrays with additional index check
 * @link http://php.net/manual/en/function.array-diff-assoc.php
 * @param array1 array
 * @param array2 array
 * @param _ array[optional]
 * @return array an array containing all the values from
 */
function array_diff_assoc (array $array1, array $array2, array $_ = null) {}

/**
 * Computes the difference of arrays with additional index check, compares data by a callback function
 * @link http://php.net/manual/en/function.array-udiff-assoc.php
 * @param array1 array
 * @param array2 array
 * @param _ array[optional]
 * @param data_compare_func callback
 * @return array 
 */
function array_udiff_assoc (array $array1, array $array2, array $_ = null, $data_compare_func) {}

/**
 * Computes the difference of arrays with additional index check which is performed by a user supplied callback function
 * @link http://php.net/manual/en/function.array-diff-uassoc.php
 * @param array1 array
 * @param array2 array
 * @param _ array[optional]
 * @param key_compare_func callback
 * @return array an array containing all the entries from
 */
function array_diff_uassoc (array $array1, array $array2, array $_ = null, $key_compare_func) {}

/**
 * Computes the difference of arrays with additional index check, compares data and indexes by a callback function
 * @link http://php.net/manual/en/function.array-udiff-uassoc.php
 * @param array1 array
 * @param array2 array
 * @param _ array[optional]
 * @param data_compare_func callback
 * @param key_compare_func callback
 * @return array an array containing all the values from
 */
function array_udiff_uassoc (array $array1, array $array2, array $_ = null, $data_compare_func, $key_compare_func) {}

/**
 * Calculate the sum of values in an array
 * @link http://php.net/manual/en/function.array-sum.php
 * @param array array
 * @return number the sum of values as an integer or float.
 */
function array_sum (array $array) {}

/**
 * Calculate the product of values in an array
 * @link http://php.net/manual/en/function.array-product.php
 * @param array array
 * @return number the product as an integer or float.
 */
function array_product (array $array) {}

/**
 * Filters elements of an array using a callback function
 * @link http://php.net/manual/en/function.array-filter.php
 * @param input array
 * @param callback callback[optional]
 * @return array the filtered array.
 */
function array_filter (array $input, $callback = null) {}

/**
 * Applies the callback to the elements of the given arrays
 * @link http://php.net/manual/en/function.array-map.php
 * @param callback callback
 * @param arr1 array
 * @param _ array[optional]
 * @return array an array containing all the elements of arr1
 */
function array_map ($callback, array $arr1, array $_ = null) {}

/**
 * Split an array into chunks
 * @link http://php.net/manual/en/function.array-chunk.php
 * @param input array
 * @param size int
 * @param preserve_keys bool[optional]
 * @return array a multidimensional numerically indexed array, starting with zero,
 */
function array_chunk (array $input, $size, $preserve_keys = null) {}

/**
 * Creates an array by using one array for keys and another for its values
 * @link http://php.net/manual/en/function.array-combine.php
 * @param keys array
 * @param values array
 * @return array the combined array, false if the number of elements
 */
function array_combine (array $keys, array $values) {}

/**
 * Checks if the given key or index exists in the array
 * @link http://php.net/manual/en/function.array-key-exists.php
 * @param key mixed
 * @param search array
 * @return bool 
 */
function array_key_exists ($key, array $search) {}

/**
 * &Alias; <function>current</function>
 * @link http://php.net/manual/en/function.pos.php
 * @param arg
 */
function pos (&$arg) {}

/**
 * &Alias; <function>count</function>
 * @link http://php.net/manual/en/function.sizeof.php
 * @param var
 * @param mode[optional]
 */
function sizeof ($var, $mode) {}

/**
 * @param key
 * @param search
 */
function key_exists ($key, $search) {}

/**
 * Checks if assertion is &false;
 * @link http://php.net/manual/en/function.assert.php
 * @param assertion mixed
 * @return bool 
 */
function assert ($assertion) {}

/**
 * Set/get the various assert flags
 * @link http://php.net/manual/en/function.assert-options.php
 * @param what int
 * @param value mixed[optional]
 * @return mixed the original setting of any option or false on errors.
 */
function assert_options ($what, $value = null) {}

/**
 * Compares two "PHP-standardized" version number strings
 * @link http://php.net/manual/en/function.version-compare.php
 * @param version1 string
 * @param version2 string
 * @param operator string[optional]
 * @return mixed 
 */
function version_compare ($version1, $version2, $operator = null) {}

/**
 * Convert a pathname and a project identifier to a System V IPC key
 * @link http://php.net/manual/en/function.ftok.php
 * @param pathname string
 * @param proj string
 * @return int 
 */
function ftok ($pathname, $proj) {}

/**
 * Perform the rot13 transform on a string
 * @link http://php.net/manual/en/function.str-rot13.php
 * @param str string
 * @return string the ROT13 version of the given string.
 */
function str_rot13 ($str) {}

/**
 * Retrieve list of registered filters
 * @link http://php.net/manual/en/function.stream-get-filters.php
 */
function stream_get_filters () {}

/**
 * Register a stream filter implemented as a PHP class derived from <literal>php_user_filter</literal>
 * @link http://php.net/manual/en/function.stream-filter-register.php
 * @param filtername
 * @param classname
 */
function stream_filter_register ($filtername, $classname) {}

/**
 * Return a bucket object from the brigade for operating on
 * @link http://php.net/manual/en/function.stream-bucket-make-writeable.php
 * @param brigade resource
 * @return object 
 */
function stream_bucket_make_writeable ($brigade) {}

/**
 * Prepend bucket to brigade
 * @link http://php.net/manual/en/function.stream-bucket-prepend.php
 * @param brigade resource
 * @param bucket resource
 * @return void 
 */
function stream_bucket_prepend ($brigade, $bucket) {}

/**
 * Append bucket to brigade
 * @link http://php.net/manual/en/function.stream-bucket-append.php
 * @param brigade resource
 * @param bucket resource
 * @return void 
 */
function stream_bucket_append ($brigade, $bucket) {}

/**
 * Create a new bucket for use on the current stream
 * @link http://php.net/manual/en/function.stream-bucket-new.php
 * @param stream resource
 * @param buffer string
 * @return object 
 */
function stream_bucket_new ($stream, $buffer) {}

/**
 * Add URL rewriter values
 * @link http://php.net/manual/en/function.output-add-rewrite-var.php
 * @param name string
 * @param value string
 * @return bool 
 */
function output_add_rewrite_var ($name, $value) {}

/**
 * Reset URL rewriter values
 * @link http://php.net/manual/en/function.output-reset-rewrite-vars.php
 * @return bool 
 */
function output_reset_rewrite_vars () {}

/**
 * Returns directory path used for temporary files
 * @link http://php.net/manual/en/function.sys-get-temp-dir.php
 * @return string the path of the temporary directory.
 */
function sys_get_temp_dir () {}

define ('CONNECTION_ABORTED', 1);
define ('CONNECTION_NORMAL', 0);
define ('CONNECTION_TIMEOUT', 2);
define ('INI_USER', 1);
define ('INI_PERDIR', 2);
define ('INI_SYSTEM', 4);
define ('INI_ALL', 7);
define ('PHP_URL_SCHEME', 0);
define ('PHP_URL_HOST', 1);
define ('PHP_URL_PORT', 2);
define ('PHP_URL_USER', 3);
define ('PHP_URL_PASS', 4);
define ('PHP_URL_PATH', 5);
define ('PHP_URL_QUERY', 6);
define ('PHP_URL_FRAGMENT', 7);
define ('M_E', 2.71828182846);
define ('M_LOG2E', 1.44269504089);
define ('M_LOG10E', 0.434294481903);
define ('M_LN2', 0.69314718056);
define ('M_LN10', 2.30258509299);
define ('M_PI', 3.14159265359);
define ('M_PI_2', 1.57079632679);
define ('M_PI_4', 0.785398163397);
define ('M_1_PI', 0.318309886184);
define ('M_2_PI', 0.636619772368);
define ('M_SQRTPI', 1.77245385091);
define ('M_2_SQRTPI', 1.1283791671);
define ('M_LNPI', 1.14472988585);
define ('M_EULER', 0.577215664902);
define ('M_SQRT2', 1.41421356237);
define ('M_SQRT1_2', 0.707106781187);
define ('M_SQRT3', 1.73205080757);
define ('INF', INF);
define ('NAN', NAN);
define ('INFO_GENERAL', 1);
define ('INFO_CREDITS', 2);
define ('INFO_CONFIGURATION', 4);
define ('INFO_MODULES', 8);
define ('INFO_ENVIRONMENT', 16);
define ('INFO_VARIABLES', 32);
define ('INFO_LICENSE', 64);
define ('INFO_ALL', 4294967295);
define ('CREDITS_GROUP', 1);
define ('CREDITS_GENERAL', 2);
define ('CREDITS_SAPI', 4);
define ('CREDITS_MODULES', 8);
define ('CREDITS_DOCS', 16);
define ('CREDITS_FULLPAGE', 32);
define ('CREDITS_QA', 64);
define ('CREDITS_ALL', 4294967295);
define ('HTML_SPECIALCHARS', 0);
define ('HTML_ENTITIES', 1);
define ('ENT_COMPAT', 2);
define ('ENT_QUOTES', 3);
define ('ENT_NOQUOTES', 0);
define ('STR_PAD_LEFT', 0);
define ('STR_PAD_RIGHT', 1);
define ('STR_PAD_BOTH', 2);
define ('PATHINFO_DIRNAME', 1);
define ('PATHINFO_BASENAME', 2);
define ('PATHINFO_EXTENSION', 4);

/**
 * Since PHP 5.2.0.
 * @link http://php.net/manual/en/filesystem.constants.php
 */
define ('PATHINFO_FILENAME', 8);
define ('CHAR_MAX', 127);
define ('LC_CTYPE', 0);
define ('LC_NUMERIC', 1);
define ('LC_TIME', 2);
define ('LC_COLLATE', 3);
define ('LC_MONETARY', 4);
define ('LC_ALL', 6);
define ('LC_MESSAGES', 5);
define ('SEEK_SET', 0);
define ('SEEK_CUR', 1);
define ('SEEK_END', 2);
define ('LOCK_SH', 1);
define ('LOCK_EX', 2);
define ('LOCK_UN', 3);
define ('LOCK_NB', 4);

/**
 * A connection with an external resource has been established.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_NOTIFY_CONNECT', 2);

/**
 * Additional authorization is required to access the specified resource.
 * Typical issued with severity level of
 * STREAM_NOTIFY_SEVERITY_ERR.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_NOTIFY_AUTH_REQUIRED', 3);

/**
 * Authorization has been completed (with or without success).
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_NOTIFY_AUTH_RESULT', 10);

/**
 * The mime-type of resource has been identified,
 * refer to message for a description of the
 * discovered type.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_NOTIFY_MIME_TYPE_IS', 4);

/**
 * The size of the resource has been discovered.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_NOTIFY_FILE_SIZE_IS', 5);

/**
 * The external resource has redirected the stream to an alternate
 * location. Refer to message.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_NOTIFY_REDIRECTED', 6);

/**
 * Indicates current progress of the stream transfer in
 * bytes_transferred and possibly
 * bytes_max as well.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_NOTIFY_PROGRESS', 7);

/**
 * A generic error occurred on the stream, consult
 * message and message_code
 * for details.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_NOTIFY_FAILURE', 9);

/**
 * There is no more data available on the stream.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_NOTIFY_COMPLETED', 8);

/**
 * A remote address required for this stream has been resolved, or the resolution
 * failed. See severity for an indication of which happened.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_NOTIFY_RESOLVE', 1);

/**
 * Normal, non-error related, notification.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_NOTIFY_SEVERITY_INFO', 0);

/**
 * Non critical error condition. Processing may continue.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_NOTIFY_SEVERITY_WARN', 1);

/**
 * A critical error occurred. Processing cannot continue.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_NOTIFY_SEVERITY_ERR', 2);

/**
 * Used with stream_filter_append and
 * stream_filter_prepend to indicate
 * that the specified filter should only be applied when
 * reading
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_FILTER_READ', 1);

/**
 * Used with stream_filter_append and
 * stream_filter_prepend to indicate
 * that the specified filter should only be applied when
 * writing
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_FILTER_WRITE', 2);

/**
 * This constant is equivalent to 
 * STREAM_FILTER_READ | STREAM_FILTER_WRITE
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_FILTER_ALL', 3);

/**
 * Client socket opened with stream_socket_client
 * should remain persistent between page loads.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_CLIENT_PERSISTENT', 1);

/**
 * Open client socket asynchronously. This option must be used
 * together with the STREAM_CLIENT_CONNECT flag.
 * Used with stream_socket_client.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_CLIENT_ASYNC_CONNECT', 2);

/**
 * Open client socket connection. Client sockets should always
 * include this flag. Used with stream_socket_client.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_CLIENT_CONNECT', 4);
define ('STREAM_CRYPTO_METHOD_SSLv2_CLIENT', 0);
define ('STREAM_CRYPTO_METHOD_SSLv3_CLIENT', 1);
define ('STREAM_CRYPTO_METHOD_SSLv23_CLIENT', 2);
define ('STREAM_CRYPTO_METHOD_TLS_CLIENT', 3);
define ('STREAM_CRYPTO_METHOD_SSLv2_SERVER', 4);
define ('STREAM_CRYPTO_METHOD_SSLv3_SERVER', 5);
define ('STREAM_CRYPTO_METHOD_SSLv23_SERVER', 6);
define ('STREAM_CRYPTO_METHOD_TLS_SERVER', 7);

/**
 * Used with stream_socket_shutdown to disable
 * further receptions. Added in PHP 5.2.1.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_SHUT_RD', 0);

/**
 * Used with stream_socket_shutdown to disable
 * further transmissions. Added in PHP 5.2.1.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_SHUT_WR', 1);

/**
 * Used with stream_socket_shutdown to disable
 * further receptions and transmissions. Added in PHP 5.2.1.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_SHUT_RDWR', 2);

/**
 * Internet Protocol Version 4 (IPv4).
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_PF_INET', 2);

/**
 * Internet Protocol Version 6 (IPv6).
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_PF_INET6', 10);

/**
 * Unix system internal protocols.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_PF_UNIX', 1);

/**
 * Provides a IP socket.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_IPPROTO_IP', 0);

/**
 * Provides a TCP socket.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_IPPROTO_TCP', 6);

/**
 * Provides a UDP socket.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_IPPROTO_UDP', 17);

/**
 * Provides a ICMP socket.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_IPPROTO_ICMP', 1);

/**
 * Provides a RAW socket.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_IPPROTO_RAW', 255);

/**
 * Provides sequenced, two-way byte streams with a transmission mechanism
 * for out-of-band data (TCP, for example).
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_SOCK_STREAM', 1);

/**
 * Provides datagrams, which are connectionless messages (UDP, for
 * example).
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_SOCK_DGRAM', 2);

/**
 * Provides a raw socket, which provides access to internal network
 * protocols and interfaces. Usually this type of socket is just available
 * to the root user.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_SOCK_RAW', 3);

/**
 * Provides a sequenced packet stream socket.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_SOCK_SEQPACKET', 5);

/**
 * Provides a RDM (Reliably-delivered messages) socket.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_SOCK_RDM', 4);
define ('STREAM_PEEK', 2);
define ('STREAM_OOB', 1);

/**
 * Tells a stream created with stream_socket_server
 * to bind to the specified target. Server sockets should always include this flag.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_SERVER_BIND', 4);

/**
 * Tells a stream created with stream_socket_server
 * and bound using the STREAM_SERVER_BIND flag to start
 * listening on the socket. Connection-orientated transports (such as TCP)
 * must use this flag, otherwise the server socket will not be enabled.
 * Using this flag for connect-less transports (such as UDP) is an error.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_SERVER_LISTEN', 8);

/**
 * Search for filename in
 * include_path (since PHP 5).
 * @link http://php.net/manual/en/filesystem.constants.php
 */
define ('FILE_USE_INCLUDE_PATH', 1);

/**
 * Strip EOL characters (since PHP 5).
 * @link http://php.net/manual/en/filesystem.constants.php
 */
define ('FILE_IGNORE_NEW_LINES', 2);

/**
 * Skip empty lines (since PHP 5).
 * @link http://php.net/manual/en/filesystem.constants.php
 */
define ('FILE_SKIP_EMPTY_LINES', 4);

/**
 * Append content to existing file.
 * @link http://php.net/manual/en/filesystem.constants.php
 */
define ('FILE_APPEND', 8);
define ('FILE_NO_DEFAULT_CONTEXT', 16);
define ('FNM_NOESCAPE', 2);
define ('FNM_PATHNAME', 1);
define ('FNM_PERIOD', 4);
define ('FNM_CASEFOLD', 16);

/**
 * Return Code indicating that the
 * userspace filter returned buckets in $out.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('PSFS_PASS_ON', 2);

/**
 * Return Code indicating that the
 * userspace filter did not return buckets in $out
 * (i.e. No data available).
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('PSFS_FEED_ME', 1);

/**
 * Return Code indicating that the
 * userspace filter encountered an unrecoverable error
 * (i.e. Invalid data received).
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('PSFS_ERR_FATAL', 0);
define ('PSFS_FLAG_NORMAL', 0);
define ('PSFS_FLAG_FLUSH_INC', 1);
define ('PSFS_FLAG_FLUSH_CLOSE', 2);
define ('ABDAY_1', 131072);
define ('ABDAY_2', 131073);
define ('ABDAY_3', 131074);
define ('ABDAY_4', 131075);
define ('ABDAY_5', 131076);
define ('ABDAY_6', 131077);
define ('ABDAY_7', 131078);
define ('DAY_1', 131079);
define ('DAY_2', 131080);
define ('DAY_3', 131081);
define ('DAY_4', 131082);
define ('DAY_5', 131083);
define ('DAY_6', 131084);
define ('DAY_7', 131085);
define ('ABMON_1', 131086);
define ('ABMON_2', 131087);
define ('ABMON_3', 131088);
define ('ABMON_4', 131089);
define ('ABMON_5', 131090);
define ('ABMON_6', 131091);
define ('ABMON_7', 131092);
define ('ABMON_8', 131093);
define ('ABMON_9', 131094);
define ('ABMON_10', 131095);
define ('ABMON_11', 131096);
define ('ABMON_12', 131097);
define ('MON_1', 131098);
define ('MON_2', 131099);
define ('MON_3', 131100);
define ('MON_4', 131101);
define ('MON_5', 131102);
define ('MON_6', 131103);
define ('MON_7', 131104);
define ('MON_8', 131105);
define ('MON_9', 131106);
define ('MON_10', 131107);
define ('MON_11', 131108);
define ('MON_12', 131109);
define ('AM_STR', 131110);
define ('PM_STR', 131111);
define ('D_T_FMT', 131112);
define ('D_FMT', 131113);
define ('T_FMT', 131114);
define ('T_FMT_AMPM', 131115);
define ('ERA', 131116);
define ('ERA_D_T_FMT', 131120);
define ('ERA_D_FMT', 131118);
define ('ERA_T_FMT', 131121);
define ('ALT_DIGITS', 131119);
define ('CRNCYSTR', 262159);
define ('RADIXCHAR', 65536);
define ('THOUSEP', 65537);
define ('YESEXPR', 327680);
define ('NOEXPR', 327681);
define ('CODESET', 14);
define ('CRYPT_SALT_LENGTH', 12);
define ('CRYPT_STD_DES', 1);
define ('CRYPT_EXT_DES', 0);
define ('CRYPT_MD5', 1);
define ('CRYPT_BLOWFISH', 0);
define ('DIRECTORY_SEPARATOR', "/");
define ('PATH_SEPARATOR', ":");
define ('GLOB_BRACE', 1024);
define ('GLOB_MARK', 2);
define ('GLOB_NOSORT', 4);
define ('GLOB_NOCHECK', 16);
define ('GLOB_NOESCAPE', 64);
define ('GLOB_ERR', 1);
define ('GLOB_ONLYDIR', 8192);
define ('GLOB_AVAILABLE_FLAGS', 9303);
define ('LOG_EMERG', 0);
define ('LOG_ALERT', 1);
define ('LOG_CRIT', 2);
define ('LOG_ERR', 3);
define ('LOG_WARNING', 4);
define ('LOG_NOTICE', 5);
define ('LOG_INFO', 6);
define ('LOG_DEBUG', 7);
define ('LOG_KERN', 0);
define ('LOG_USER', 8);
define ('LOG_MAIL', 16);
define ('LOG_DAEMON', 24);
define ('LOG_AUTH', 32);
define ('LOG_SYSLOG', 40);
define ('LOG_LPR', 48);
define ('LOG_NEWS', 56);
define ('LOG_UUCP', 64);
define ('LOG_CRON', 72);
define ('LOG_AUTHPRIV', 80);
define ('LOG_LOCAL0', 128);
define ('LOG_LOCAL1', 136);
define ('LOG_LOCAL2', 144);
define ('LOG_LOCAL3', 152);
define ('LOG_LOCAL4', 160);
define ('LOG_LOCAL5', 168);
define ('LOG_LOCAL6', 176);
define ('LOG_LOCAL7', 184);
define ('LOG_PID', 1);
define ('LOG_CONS', 2);
define ('LOG_ODELAY', 4);
define ('LOG_NDELAY', 8);
define ('LOG_NOWAIT', 16);
define ('LOG_PERROR', 32);
define ('EXTR_OVERWRITE', 0);
define ('EXTR_SKIP', 1);
define ('EXTR_PREFIX_SAME', 2);
define ('EXTR_PREFIX_ALL', 3);
define ('EXTR_PREFIX_INVALID', 4);
define ('EXTR_PREFIX_IF_EXISTS', 5);
define ('EXTR_IF_EXISTS', 6);
define ('EXTR_REFS', 256);

/**
 * SORT_ASC is used with
 * array_multisort to sort in ascending order.
 * @link http://php.net/manual/en/array.constants.php
 */
define ('SORT_ASC', 4);

/**
 * SORT_DESC is used with
 * array_multisort to sort in descending order.
 * @link http://php.net/manual/en/array.constants.php
 */
define ('SORT_DESC', 3);

/**
 * SORT_REGULAR is used to compare items normally.
 * @link http://php.net/manual/en/array.constants.php
 */
define ('SORT_REGULAR', 0);

/**
 * SORT_NUMERIC is used to compare items numerically.
 * @link http://php.net/manual/en/array.constants.php
 */
define ('SORT_NUMERIC', 1);

/**
 * SORT_STRING is used to compare items as strings.
 * @link http://php.net/manual/en/array.constants.php
 */
define ('SORT_STRING', 2);

/**
 * SORT_LOCALE_STRING is used to compare items as
 * strings, based on the current locale. Added in PHP 4.4.0 and 5.0.2.
 * @link http://php.net/manual/en/array.constants.php
 */
define ('SORT_LOCALE_STRING', 5);

/**
 * CASE_LOWER is used with
 * array_change_key_case and is used to convert array
 * keys to lower case. This is also the default case for
 * array_change_key_case.
 * @link http://php.net/manual/en/array.constants.php
 */
define ('CASE_LOWER', 0);

/**
 * CASE_UPPER is used with
 * array_change_key_case and is used to convert array
 * keys to upper case.
 * @link http://php.net/manual/en/array.constants.php
 */
define ('CASE_UPPER', 1);
define ('COUNT_NORMAL', 0);
define ('COUNT_RECURSIVE', 1);
define ('ASSERT_ACTIVE', 1);
define ('ASSERT_CALLBACK', 2);
define ('ASSERT_BAIL', 3);
define ('ASSERT_WARNING', 4);
define ('ASSERT_QUIET_EVAL', 5);

/**
 * Flag indicating if the stream
 * used the include path.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_USE_PATH', 1);
define ('STREAM_IGNORE_URL', 2);
define ('STREAM_ENFORCE_SAFE_MODE', 4);

/**
 * Flag indicating if the wrapper
 * is responsible for raising errors using trigger_error 
 * during opening of the stream. If this flag is not set, you
 * should not raise any errors.
 * @link http://php.net/manual/en/stream.constants.php
 */
define ('STREAM_REPORT_ERRORS', 8);

/**
 * This flag is useful when your extension really must be able to randomly
 * seek around in a stream. Some streams may not be seekable in their
 * native form, so this flag asks the streams API to check to see if the
 * stream does support seeking. If it does not, it will copy the stream
 * into temporary storage (which may be a temporary file or a memory
 * stream) which does support seeking.
 * Please note that this flag is not useful when you want to seek the
 * stream and write to it, because the stream you are accessing might
 * not be bound to the actual resource you requested.
 * If the requested resource is network based, this flag will cause the
 * opener to block until the whole contents have been downloaded.
 * @link http://php.net/manual/en/internals2.ze1.streams.constants.php
 */
define ('STREAM_MUST_SEEK', 16);
define ('STREAM_URL_STAT_LINK', 1);
define ('STREAM_URL_STAT_QUIET', 2);
define ('STREAM_MKDIR_RECURSIVE', 1);
define ('STREAM_IS_URL', 1);

/**
 * Image type constant used by the
 * image_type_to_mime_type and
 * image_type_to_extension functions.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMAGETYPE_GIF', 1);

/**
 * Image type constant used by the
 * image_type_to_mime_type and
 * image_type_to_extension functions.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMAGETYPE_JPEG', 2);

/**
 * Image type constant used by the
 * image_type_to_mime_type and
 * image_type_to_extension functions.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMAGETYPE_PNG', 3);

/**
 * Image type constant used by the
 * image_type_to_mime_type and
 * image_type_to_extension functions.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMAGETYPE_SWF', 4);

/**
 * Image type constant used by the
 * image_type_to_mime_type and
 * image_type_to_extension functions.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMAGETYPE_PSD', 5);

/**
 * Image type constant used by the
 * image_type_to_mime_type and
 * image_type_to_extension functions.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMAGETYPE_BMP', 6);

/**
 * Image type constant used by the
 * image_type_to_mime_type and
 * image_type_to_extension functions.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMAGETYPE_TIFF_II', 7);

/**
 * Image type constant used by the
 * image_type_to_mime_type and
 * image_type_to_extension functions.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMAGETYPE_TIFF_MM', 8);

/**
 * Image type constant used by the
 * image_type_to_mime_type and
 * image_type_to_extension functions.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMAGETYPE_JPC', 9);

/**
 * Image type constant used by the
 * image_type_to_mime_type and
 * image_type_to_extension functions.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMAGETYPE_JP2', 10);

/**
 * Image type constant used by the
 * image_type_to_mime_type and
 * image_type_to_extension functions.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMAGETYPE_JPX', 11);

/**
 * Image type constant used by the
 * image_type_to_mime_type and
 * image_type_to_extension functions.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMAGETYPE_JB2', 12);

/**
 * Image type constant used by the
 * image_type_to_mime_type and
 * image_type_to_extension functions.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMAGETYPE_SWC', 13);

/**
 * Image type constant used by the
 * image_type_to_mime_type and
 * image_type_to_extension functions.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMAGETYPE_IFF', 14);

/**
 * Image type constant used by the
 * image_type_to_mime_type and
 * image_type_to_extension functions.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMAGETYPE_WBMP', 15);
define ('IMAGETYPE_JPEG2000', 9);

/**
 * Image type constant used by the
 * image_type_to_mime_type and
 * image_type_to_extension functions.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMAGETYPE_XBM', 16);
define ('DNS_A', 1);
define ('DNS_NS', 2);
define ('DNS_CNAME', 16);
define ('DNS_SOA', 32);
define ('DNS_PTR', 2048);
define ('DNS_HINFO', 4096);
define ('DNS_MX', 16384);
define ('DNS_TXT', 32768);
define ('DNS_SRV', 33554432);
define ('DNS_NAPTR', 67108864);
define ('DNS_AAAA', 134217728);
define ('DNS_A6', 16777216);
define ('DNS_ANY', 268435456);
define ('DNS_ALL', 251713587);

// End of standard v.5.2.5
?>
