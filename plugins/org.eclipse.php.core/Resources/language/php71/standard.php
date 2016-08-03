<?php

// Start of standard v.7.0.0-dev

class __PHP_Incomplete_Class  {
}

class php_user_filter  {
	public $filtername;
	public $params;


	/**
	 * Called when applying the filter
	 * @link http://www.php.net/manual/en/php-user-filter.filter.php
	 * @param resource $in <p>
	 * in is a resource pointing to a bucket brigade
	 * which contains one or more bucket objects containing data to be filtered.
	 * </p>
	 * @param resource $out <p>
	 * out is a resource pointing to a second bucket brigade
	 * into which your modified buckets should be placed.
	 * </p>
	 * @param int $consumed <p>
	 * consumed, which must always
	 * be declared by reference, should be incremented by the length of the data
	 * which your filter reads in and alters. In most cases this means you will
	 * increment consumed by $bucket->datalen
	 * for each $bucket.
	 * </p>
	 * @param bool $closing <p>
	 * If the stream is in the process of closing
	 * (and therefore this is the last pass through the filterchain),
	 * the closing parameter will be set to true.
	 * </p>
	 * @return int The filter method must return one of
	 * three values upon completion.
	 * <tr valign="top">
	 * <td>Return Value</td>
	 * <td>Meaning</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>PSFS_PASS_ON</td>
	 * <td>
	 * Filter processed successfully with data available in the
	 * out bucket brigade.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>PSFS_FEED_ME</td>
	 * <td>
	 * Filter processed successfully, however no data was available to
	 * return. More data is required from the stream or prior filter.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>PSFS_ERR_FATAL (default)</td>
	 * <td>
	 * The filter experienced an unrecoverable error and cannot continue.
	 * </td>
	 * </tr>
	 */
	public function filter ($in, $out, &$consumed, $closing) {}

	/**
	 * Called when creating the filter
	 * @link http://www.php.net/manual/en/php-user-filter.oncreate.php
	 * @return bool Your implementation of
	 * this method should return false on failure, or true on success.
	 */
	public function onCreate () {}

	/**
	 * Called when closing the filter
	 * @link http://www.php.net/manual/en/php-user-filter.onclose.php
	 * @return void Return value is ignored.
	 */
	public function onClose () {}

}

class Directory  {

	/**
	 * Close directory handle
	 * @link http://www.php.net/manual/en/directory.close.php
	 * @param resource $dir_handle [optional] 
	 * @return void 
	 */
	public function close ($dir_handle = null) {}

	/**
	 * Rewind directory handle
	 * @link http://www.php.net/manual/en/directory.rewind.php
	 * @param resource $dir_handle [optional] 
	 * @return void 
	 */
	public function rewind ($dir_handle = null) {}

	/**
	 * Read entry from directory handle
	 * @link http://www.php.net/manual/en/directory.read.php
	 * @param resource $dir_handle [optional] 
	 * @return string 
	 */
	public function read ($dir_handle = null) {}

}

/**
 * AssertionError is thrown when
 * an assertion made via assert fails.
 * @link http://www.php.net/manual/en/class.assertionerror.php
 */
class AssertionError extends Error implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param $message [optional]
	 * @param $code [optional]
	 * @param $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * Returns the value of a constant
 * @link http://www.php.net/manual/en/function.constant.php
 * @param string $name <p>
 * The constant name.
 * </p>
 * @return mixed the value of the constant, or &null; if the constant is not
 * defined.
 */
function constant ($name) {}

/**
 * Convert binary data into hexadecimal representation
 * @link http://www.php.net/manual/en/function.bin2hex.php
 * @param string $str <p>
 * A string.
 * </p>
 * @return string the hexadecimal representation of the given string.
 */
function bin2hex ($str) {}

/**
 * Decodes a hexadecimally encoded binary string
 * @link http://www.php.net/manual/en/function.hex2bin.php
 * @param string $data <p>
 * Hexadecimal representation of data.
 * </p>
 * @return string the binary representation of the given data or false on failure.
 */
function hex2bin ($data) {}

/**
 * Delay execution
 * @link http://www.php.net/manual/en/function.sleep.php
 * @param int $seconds <p>
 * Halt time in seconds.
 * </p>
 * @return int zero on success, or false on error.
 * </p>
 * <p>
 * If the call was interrupted by a signal, sleep returns
 * a non-zero value. On Windows, this value will always be
 * 192 (the value of the
 * WAIT_IO_COMPLETION constant within the Windows API).
 * On other platforms, the return value will be the number of seconds left to
 * sleep.
 */
function sleep ($seconds) {}

/**
 * Delay execution in microseconds
 * @link http://www.php.net/manual/en/function.usleep.php
 * @param int $micro_seconds <p>
 * Halt time in microseconds. A microsecond is one millionth of a
 * second.
 * </p>
 * @return void 
 */
function usleep ($micro_seconds) {}

/**
 * Delay for a number of seconds and nanoseconds
 * @link http://www.php.net/manual/en/function.time-nanosleep.php
 * @param int $seconds <p>
 * Must be a non-negative integer.
 * </p>
 * @param int $nanoseconds <p>
 * Must be a non-negative integer less than 1 billion.
 * </p>
 * @return mixed true on success or false on failure
 * </p>
 * <p>
 * If the delay was interrupted by a signal, an associative array will be
 * returned with the components:
 * seconds - number of seconds remaining in
 * the delay
 * nanoseconds - number of nanoseconds
 * remaining in the delay
 */
function time_nanosleep ($seconds, $nanoseconds) {}

/**
 * Make the script sleep until the specified time
 * @link http://www.php.net/manual/en/function.time-sleep-until.php
 * @param float $timestamp <p>
 * The timestamp when the script should wake.
 * </p>
 * @return bool true on success or false on failure
 */
function time_sleep_until ($timestamp) {}

/**
 * Parse a time/date generated with <function>strftime</function>
 * @link http://www.php.net/manual/en/function.strptime.php
 * @param string $date <p>
 * The string to parse (e.g. returned from strftime).
 * </p>
 * @param string $format <p>
 * The format used in date (e.g. the same as
 * used in strftime). Note that some of the format
 * options available to strftime may not have any
 * effect within strptime; the exact subset that are
 * supported will vary based on the operating system and C library in
 * use.
 * </p>
 * <p>
 * For more information about the format options, read the
 * strftime page.
 * </p>
 * @return array an array or false on failure.
 * </p>
 * <p>
 * <table>
 * The following parameters are returned in the array
 * <tr valign="top">
 * <td>parameters</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>"tm_sec"</td>
 * <td>Seconds after the minute (0-61)</td>
 * </tr>
 * <tr valign="top">
 * <td>"tm_min"</td>
 * <td>Minutes after the hour (0-59)</td>
 * </tr>
 * <tr valign="top">
 * <td>"tm_hour"</td>
 * <td>Hour since midnight (0-23)</td>
 * </tr>
 * <tr valign="top">
 * <td>"tm_mday"</td>
 * <td>Day of the month (1-31)</td>
 * </tr>
 * <tr valign="top">
 * <td>"tm_mon"</td>
 * <td>Months since January (0-11)</td>
 * </tr>
 * <tr valign="top">
 * <td>"tm_year"</td>
 * <td>Years since 1900</td>
 * </tr>
 * <tr valign="top">
 * <td>"tm_wday"</td>
 * <td>Days since Sunday (0-6)</td>
 * </tr>
 * <tr valign="top">
 * <td>"tm_yday"</td>
 * <td>Days since January 1 (0-365)</td>
 * </tr>
 * <tr valign="top">
 * <td>"unparsed"</td>
 * <td>the date part which was not
 * recognized using the specified format</td>
 * </tr>
 * </table>
 */
function strptime ($date, $format) {}

/**
 * Flush system output buffer
 * @link http://www.php.net/manual/en/function.flush.php
 * @return void 
 */
function flush () {}

/**
 * Wraps a string to a given number of characters
 * @link http://www.php.net/manual/en/function.wordwrap.php
 * @param string $str <p>
 * The input string.
 * </p>
 * @param int $width [optional] <p>
 * The number of characters at which the string will be wrapped.
 * </p>
 * @param string $break [optional] <p>
 * The line is broken using the optional
 * break parameter.
 * </p>
 * @param bool $cut [optional] <p>
 * If the cut is set to true, the string is
 * always wrapped at or before the specified width. So if you have
 * a word that is larger than the given width, it is broken apart.
 * (See second example). When false the function does not split the word
 * even if the width is smaller than the word width.
 * </p>
 * @return string the given string wrapped at the specified length.
 */
function wordwrap ($str, $width = null, $break = null, $cut = null) {}

/**
 * Convert special characters to HTML entities
 * @link http://www.php.net/manual/en/function.htmlspecialchars.php
 * @param string $string <p>
 * The string being converted.
 * </p>
 * @param int $flags [optional] <p>
 * A bitmask of one or more of the following flags, which specify how to handle quotes,
 * invalid code unit sequences and the used document type. The default is
 * ENT_COMPAT | ENT_HTML401.
 * <table>
 * Available flags constants
 * <tr valign="top">
 * <td>Constant Name</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_COMPAT</td>
 * <td>Will convert double-quotes and leave single-quotes alone.</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_QUOTES</td>
 * <td>Will convert both double and single quotes.</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_NOQUOTES</td>
 * <td>Will leave both double and single quotes unconverted.</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_IGNORE</td>
 * <td>
 * Silently discard invalid code unit sequences instead of returning
 * an empty string. Using this flag is discouraged as it
 * may have security implications.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_SUBSTITUTE</td>
 * <td>
 * Replace invalid code unit sequences with a Unicode Replacement Character
 * U+FFFD (UTF-8) or &amp;#FFFD; (otherwise) instead of returning an empty string.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_DISALLOWED</td>
 * <td>
 * Replace invalid code points for the given document type with a
 * Unicode Replacement Character U+FFFD (UTF-8) or &amp;#FFFD;
 * (otherwise) instead of leaving them as is. This may be useful, for
 * instance, to ensure the well-formedness of XML documents with
 * embedded external content.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_HTML401</td>
 * <td>
 * Handle code as HTML 4.01.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_XML1</td>
 * <td>
 * Handle code as XML 1.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_XHTML</td>
 * <td>
 * Handle code as XHTML.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_HTML5</td>
 * <td>
 * Handle code as HTML 5.
 * </td>
 * </tr>
 * </table>
 * </p>
 * @param string $encoding [optional] &strings.parameter.encoding;
 * <p>
 * For the purposes of this function, the encodings
 * ISO-8859-1, ISO-8859-15,
 * UTF-8, cp866,
 * cp1251, cp1252, and
 * KOI8-R are effectively equivalent, provided the
 * string itself is valid for the encoding, as
 * the characters affected by htmlspecialchars occupy
 * the same positions in all of these encodings.
 * </p>
 * &reference.strings.charsets;
 * @param bool $double_encode [optional] <p>
 * When double_encode is turned off PHP will not
 * encode existing html entities, the default is to convert everything.
 * </p>
 * @return string The converted string.
 * </p>
 * <p>
 * If the input string contains an invalid code unit
 * sequence within the given encoding an empty string
 * will be returned, unless either the ENT_IGNORE or
 * ENT_SUBSTITUTE flags are set.
 */
function htmlspecialchars ($string, $flags = null, $encoding = null, $double_encode = null) {}

/**
 * Convert all applicable characters to HTML entities
 * @link http://www.php.net/manual/en/function.htmlentities.php
 * @param string $string <p>
 * The input string.
 * </p>
 * @param int $flags [optional] <p>
 * A bitmask of one or more of the following flags, which specify how to handle quotes,
 * invalid code unit sequences and the used document type. The default is
 * ENT_COMPAT | ENT_HTML401.
 * <table>
 * Available flags constants
 * <tr valign="top">
 * <td>Constant Name</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_COMPAT</td>
 * <td>Will convert double-quotes and leave single-quotes alone.</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_QUOTES</td>
 * <td>Will convert both double and single quotes.</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_NOQUOTES</td>
 * <td>Will leave both double and single quotes unconverted.</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_IGNORE</td>
 * <td>
 * Silently discard invalid code unit sequences instead of returning
 * an empty string. Using this flag is discouraged as it
 * may have security implications.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_SUBSTITUTE</td>
 * <td>
 * Replace invalid code unit sequences with a Unicode Replacement Character
 * U+FFFD (UTF-8) or &amp;#FFFD; (otherwise) instead of returning an empty string.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_DISALLOWED</td>
 * <td>
 * Replace invalid code points for the given document type with a
 * Unicode Replacement Character U+FFFD (UTF-8) or &amp;#FFFD;
 * (otherwise) instead of leaving them as is. This may be useful, for
 * instance, to ensure the well-formedness of XML documents with
 * embedded external content.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_HTML401</td>
 * <td>
 * Handle code as HTML 4.01.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_XML1</td>
 * <td>
 * Handle code as XML 1.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_XHTML</td>
 * <td>
 * Handle code as XHTML.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_HTML5</td>
 * <td>
 * Handle code as HTML 5.
 * </td>
 * </tr>
 * </table>
 * </p>
 * @param string $encoding [optional] &strings.parameter.encoding;
 * &reference.strings.charsets;
 * @param bool $double_encode [optional] <p>
 * When double_encode is turned off PHP will not
 * encode existing html entities. The default is to convert everything.
 * </p>
 * @return string the encoded string.
 * </p>
 * <p>
 * If the input string contains an invalid code unit
 * sequence within the given encoding an empty string
 * will be returned, unless either the ENT_IGNORE or
 * ENT_SUBSTITUTE flags are set.
 */
function htmlentities ($string, $flags = null, $encoding = null, $double_encode = null) {}

/**
 * Convert all HTML entities to their applicable characters
 * @link http://www.php.net/manual/en/function.html-entity-decode.php
 * @param string $string <p>
 * The input string.
 * </p>
 * @param int $flags [optional] <p>
 * A bitmask of one or more of the following flags, which specify how to handle quotes and
 * which document type to use. The default is ENT_COMPAT | ENT_HTML401.
 * <table>
 * Available flags constants
 * <tr valign="top">
 * <td>Constant Name</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_COMPAT</td>
 * <td>Will convert double-quotes and leave single-quotes alone.</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_QUOTES</td>
 * <td>Will convert both double and single quotes.</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_NOQUOTES</td>
 * <td>Will leave both double and single quotes unconverted.</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_HTML401</td>
 * <td>
 * Handle code as HTML 4.01.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_XML1</td>
 * <td>
 * Handle code as XML 1.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_XHTML</td>
 * <td>
 * Handle code as XHTML.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_HTML5</td>
 * <td>
 * Handle code as HTML 5.
 * </td>
 * </tr>
 * </table>
 * </p>
 * @param string $encoding [optional] &strings.parameter.encoding;
 * &reference.strings.charsets;
 * @return string the decoded string.
 */
function html_entity_decode ($string, $flags = null, $encoding = null) {}

/**
 * Convert special HTML entities back to characters
 * @link http://www.php.net/manual/en/function.htmlspecialchars-decode.php
 * @param string $string <p>
 * The string to decode.
 * </p>
 * @param int $flags [optional] <p>
 * A bitmask of one or more of the following flags, which specify how to handle quotes and
 * which document type to use. The default is ENT_COMPAT | ENT_HTML401.
 * <table>
 * Available flags constants
 * <tr valign="top">
 * <td>Constant Name</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_COMPAT</td>
 * <td>Will convert double-quotes and leave single-quotes alone.</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_QUOTES</td>
 * <td>Will convert both double and single quotes.</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_NOQUOTES</td>
 * <td>Will leave both double and single quotes unconverted.</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_HTML401</td>
 * <td>
 * Handle code as HTML 4.01.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_XML1</td>
 * <td>
 * Handle code as XML 1.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_XHTML</td>
 * <td>
 * Handle code as XHTML.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_HTML5</td>
 * <td>
 * Handle code as HTML 5.
 * </td>
 * </tr>
 * </table>
 * </p>
 * @return string the decoded string.
 */
function htmlspecialchars_decode ($string, $flags = null) {}

/**
 * Returns the translation table used by <function>htmlspecialchars</function> and <function>htmlentities</function>
 * @link http://www.php.net/manual/en/function.get-html-translation-table.php
 * @param int $table [optional] <p>
 * Which table to return. Either HTML_ENTITIES or
 * HTML_SPECIALCHARS.
 * </p>
 * @param int $flags [optional] <p>
 * A bitmask of one or more of the following flags, which specify which quotes the
 * table will contain as well as which document type the table is for. The default is
 * ENT_COMPAT | ENT_HTML401.
 * <table>
 * Available flags constants
 * <tr valign="top">
 * <td>Constant Name</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_COMPAT</td>
 * <td>Table will contain entities for double-quotes, but not for single-quotes.</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_QUOTES</td>
 * <td>Table will contain entities for both double and single quotes.</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_NOQUOTES</td>
 * <td>Table will neither contain entities for single quotes nor for double quotes.</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_HTML401</td>
 * <td>Table for HTML 4.01.</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_XML1</td>
 * <td>Table for XML 1.</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_XHTML</td>
 * <td>Table for XHTML.</td>
 * </tr>
 * <tr valign="top">
 * <td>ENT_HTML5</td>
 * <td>Table for HTML 5.</td>
 * </tr>
 * </table>
 * </p>
 * @param string $encoding [optional] <p>
 * Encoding to use.
 * If omitted, the default value for this argument is ISO-8859-1 in
 * versions of PHP prior to 5.4.0, and UTF-8 from PHP 5.4.0 onwards.
 * </p>
 * &reference.strings.charsets;
 * @return array the translation table as an array, with the original characters
 * as keys and entities as values.
 */
function get_html_translation_table ($table = null, $flags = null, $encoding = null) {}

/**
 * Calculate the sha1 hash of a string
 * @link http://www.php.net/manual/en/function.sha1.php
 * @param string $str <p>
 * The input string.
 * </p>
 * @param bool $raw_output [optional] <p>
 * If the optional raw_output is set to true,
 * then the sha1 digest is instead returned in raw binary format with a
 * length of 20, otherwise the returned value is a 40-character
 * hexadecimal number.
 * </p>
 * @return string the sha1 hash as a string.
 */
function sha1 ($str, $raw_output = null) {}

/**
 * Calculate the sha1 hash of a file
 * @link http://www.php.net/manual/en/function.sha1-file.php
 * @param string $filename <p>
 * The filename of the file to hash.
 * </p>
 * @param bool $raw_output [optional] <p>
 * When true, returns the digest in raw binary format with a length of
 * 20.
 * </p>
 * @return string a string on success, false otherwise.
 */
function sha1_file ($filename, $raw_output = null) {}

/**
 * Calculate the md5 hash of a string
 * @link http://www.php.net/manual/en/function.md5.php
 * @param string $str <p>
 * The string.
 * </p>
 * @param bool $raw_output [optional] <p>
 * If the optional raw_output is set to true,
 * then the md5 digest is instead returned in raw binary format with a
 * length of 16.
 * </p>
 * @return string the hash as a 32-character hexadecimal number.
 */
function md5 ($str, $raw_output = null) {}

/**
 * Calculates the md5 hash of a given file
 * @link http://www.php.net/manual/en/function.md5-file.php
 * @param string $filename <p>
 * The filename
 * </p>
 * @param bool $raw_output [optional] <p>
 * When true, returns the digest in raw binary format with a length of
 * 16.
 * </p>
 * @return string a string on success, false otherwise.
 */
function md5_file ($filename, $raw_output = null) {}

/**
 * Calculates the crc32 polynomial of a string
 * @link http://www.php.net/manual/en/function.crc32.php
 * @param string $str <p>
 * The data.
 * </p>
 * @return int the crc32 checksum of str as an integer.
 */
function crc32 ($str) {}

/**
 * Parse a binary IPTC block into single tags.
 * @link http://www.php.net/manual/en/function.iptcparse.php
 * @param string $iptcblock <p>
 * A binary IPTC block.
 * </p>
 * @return array an array using the tagmarker as an index and the value as the
 * value. It returns false on error or if no IPTC data was found.
 */
function iptcparse ($iptcblock) {}

/**
 * Embeds binary IPTC data into a JPEG image
 * @link http://www.php.net/manual/en/function.iptcembed.php
 * @param string $iptcdata <p>
 * The data to be written.
 * </p>
 * @param string $jpeg_file_name <p>
 * Path to the JPEG image.
 * </p>
 * @param int $spool [optional] <p>
 * Spool flag. If the spool flag is less than 2 then the JPEG will be 
 * returned as a string. Otherwise the JPEG will be printed to STDOUT.
 * </p>
 * @return mixed If spool is less than 2, the JPEG will be returned,
 * or false on failure. Otherwise returns true on success
 * or false on failure.
 */
function iptcembed ($iptcdata, $jpeg_file_name, $spool = null) {}

/**
 * Get the size of an image
 * @link http://www.php.net/manual/en/function.getimagesize.php
 * @param string $filename <p>
 * This parameter specifies the file you wish to retrieve information
 * about. It can reference a local file or (configuration permitting) a
 * remote file using one of the supported streams. 
 * </p>
 * @param array $imageinfo [optional] <p>
 * This optional parameter allows you to extract some extended
 * information from the image file. Currently, this will return the
 * different JPG APP markers as an associative array.
 * Some programs use these APP markers to embed text information in 
 * images. A very common one is to embed 
 * IPTC information in the APP13 marker.
 * You can use the iptcparse function to parse the
 * binary APP13 marker into something readable.
 * </p>
 * @return array an array with up to 7 elements. Not all image types will include
 * the channels and bits elements.
 * </p>
 * <p>
 * Index 0 and 1 contains respectively the width and the height of the image.
 * </p>
 * <p>
 * Some formats may contain no image or may contain multiple images. In these
 * cases, getimagesize might not be able to properly
 * determine the image size. getimagesize will return
 * zero for width and height in these cases.
 * </p>
 * <p>
 * Index 2 is one of the IMAGETYPE_XXX constants indicating 
 * the type of the image.
 * </p>
 * <p>
 * Index 3 is a text string with the correct 
 * height="yyy" width="xxx" string that can be used
 * directly in an IMG tag.
 * </p>
 * <p>
 * mime is the correspondant MIME type of the image.
 * This information can be used to deliver images with the correct HTTP 
 * Content-type header:
 * getimagesize and MIME types
 * ]]>
 * </p>
 * <p>
 * channels will be 3 for RGB pictures and 4 for CMYK
 * pictures.
 * </p>
 * <p>
 * bits is the number of bits for each color.
 * </p>
 * <p>
 * For some image types, the presence of channels and
 * bits values can be a bit
 * confusing. As an example, GIF always uses 3 channels
 * per pixel, but the number of bits per pixel cannot be calculated for an
 * animated GIF with a global color table.
 * </p>
 * <p>
 * On failure, false is returned.
 */
function getimagesize ($filename, array &$imageinfo = null) {}

/**
 * Get the size of an image from a string
 * @link http://www.php.net/manual/en/function.getimagesizefromstring.php
 * @param string $imagedata <p>
 * The image data, as a string.
 * </p>
 * @param array $imageinfo [optional] <p>
 * See getimagesize.
 * </p>
 * @return array See getimagesize.
 */
function getimagesizefromstring ($imagedata, array &$imageinfo = null) {}

/**
 * Get Mime-Type for image-type returned by getimagesize,
   exif_read_data, exif_thumbnail, exif_imagetype
 * @link http://www.php.net/manual/en/function.image-type-to-mime-type.php
 * @param int $imagetype <p>
 * One of the IMAGETYPE_XXX constants.
 * </p>
 * @return string The returned values are as follows
 * <table>
 * Returned values Constants
 * <tr valign="top">
 * <td>imagetype</td>
 * <td>Returned value</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_GIF</td>
 * <td>image/gif</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_JPEG</td>
 * <td>image/jpeg</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_PNG</td>
 * <td>image/png</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_SWF</td>
 * <td>application/x-shockwave-flash</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_PSD</td>
 * <td>image/psd</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_BMP</td>
 * <td>image/bmp</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_TIFF_II (intel byte order)</td>
 * <td>image/tiff</td>
 * </tr>
 * <tr valign="top">
 * <td>
 * IMAGETYPE_TIFF_MM (motorola byte order)
 * </td>
 * <td>image/tiff</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_JPC</td>
 * <td>application/octet-stream</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_JP2</td>
 * <td>image/jp2</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_JPX</td>
 * <td>application/octet-stream</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_JB2</td>
 * <td>application/octet-stream</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_SWC</td>
 * <td>application/x-shockwave-flash</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_IFF</td>
 * <td>image/iff</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_WBMP</td>
 * <td>image/vnd.wap.wbmp</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_XBM</td>
 * <td>image/xbm</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_ICO</td>
 * <td>image/vnd.microsoft.icon</td>
 * </tr>
 * </table>
 */
function image_type_to_mime_type ($imagetype) {}

/**
 * Get file extension for image type
 * @link http://www.php.net/manual/en/function.image-type-to-extension.php
 * @param int $imagetype <p>
 * One of the IMAGETYPE_XXX constant.
 * </p>
 * @param bool $include_dot [optional] <p>
 * Whether to prepend a dot to the extension or not. Default to true.
 * </p>
 * @return string A string with the extension corresponding to the given image type.
 */
function image_type_to_extension ($imagetype, $include_dot = null) {}

/**
 * Outputs information about PHP's configuration
 * @link http://www.php.net/manual/en/function.phpinfo.php
 * @param int $what [optional] <p>
 * The output may be customized by passing one or more of the
 * following constants bitwise values summed
 * together in the optional what parameter.
 * One can also combine the respective constants or bitwise values
 * together with the or operator.
 * </p>
 * <p>
 * <table>
 * phpinfo options
 * <tr valign="top">
 * <td>Name (constant)</td>
 * <td>Value</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>INFO_GENERAL</td>
 * <td>1</td>
 * <td>
 * The configuration line, &php.ini; location, build date, Web
 * Server, System and more.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>INFO_CREDITS</td>
 * <td>2</td>
 * <td>
 * PHP Credits. See also phpcredits.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>INFO_CONFIGURATION</td>
 * <td>4</td>
 * <td>
 * Current Local and Master values for PHP directives. See
 * also ini_get.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>INFO_MODULES</td>
 * <td>8</td>
 * <td>
 * Loaded modules and their respective settings. See also
 * get_loaded_extensions.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>INFO_ENVIRONMENT</td>
 * <td>16</td>
 * <td>
 * Environment Variable information that's also available in
 * $_ENV.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>INFO_VARIABLES</td>
 * <td>32</td>
 * <td>
 * Shows all 
 * predefined variables from EGPCS (Environment, GET,
 * POST, Cookie, Server).
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>INFO_LICENSE</td>
 * <td>64</td>
 * <td>
 * PHP License information. See also the license FAQ.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>INFO_ALL</td>
 * <td>-1</td>
 * <td>
 * Shows all of the above.
 * </td>
 * </tr>
 * </table>
 * </p>
 * @return bool true on success or false on failure
 */
function phpinfo ($what = null) {}

/**
 * Gets the current PHP version
 * @link http://www.php.net/manual/en/function.phpversion.php
 * @param string $extension [optional] <p>
 * An optional extension name.
 * </p>
 * @return string If the optional extension parameter is
 * specified, phpversion returns the version of that
 * extension, or false if there is no version information associated or
 * the extension isn't enabled.
 */
function phpversion ($extension = null) {}

/**
 * Prints out the credits for PHP
 * @link http://www.php.net/manual/en/function.phpcredits.php
 * @param int $flag [optional] <p>
 * To generate a custom credits page, you may want to use the
 * flag parameter.
 * </p>
 * <p>
 * <table>
 * Pre-defined phpcredits flags
 * <tr valign="top">
 * <td>name</td>
 * <td>description</td>
 * </tr>
 * <tr valign="top">
 * <td>CREDITS_ALL</td>
 * <td>
 * All the credits, equivalent to using: CREDITS_DOCS +
 * CREDITS_GENERAL + CREDITS_GROUP +
 * CREDITS_MODULES + CREDITS_FULLPAGE.
 * It generates a complete stand-alone HTML page with the appropriate tags.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CREDITS_DOCS</td>
 * <td>The credits for the documentation team</td>
 * </tr>
 * <tr valign="top">
 * <td>CREDITS_FULLPAGE</td>
 * <td>
 * Usually used in combination with the other flags. Indicates
 * that a complete stand-alone HTML page needs to be
 * printed including the information indicated by the other
 * flags.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CREDITS_GENERAL</td>
 * <td>
 * General credits: Language design and concept, PHP authors 
 * and SAPI module.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CREDITS_GROUP</td>
 * <td>A list of the core developers</td>
 * </tr>
 * <tr valign="top">
 * <td>CREDITS_MODULES</td>
 * <td>
 * A list of the extension modules for PHP, and their authors
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CREDITS_SAPI</td>
 * <td>
 * A list of the server API modules for PHP, and their authors
 * </td>
 * </tr>
 * </table>
 * </p>
 * @return bool true on success or false on failure
 */
function phpcredits ($flag = null) {}

/**
 * Returns the type of interface between web server and PHP
 * @link http://www.php.net/manual/en/function.php-sapi-name.php
 * @return string the interface type, as a lowercase string.
 * </p>
 * <p>
 * Although not exhaustive, the possible return values include 
 * aolserver, apache, 
 * apache2filter, apache2handler, 
 * caudium, cgi (until PHP 5.3), 
 * cgi-fcgi, cli, cli-server,
 * continuity, embed, fpm-fcgi,
 * isapi, litespeed, 
 * milter, nsapi, 
 * phttpd, pi3web, roxen,
 * thttpd, tux, and webjames.
 */
function php_sapi_name () {}

/**
 * Returns information about the operating system PHP is running on
 * @link http://www.php.net/manual/en/function.php-uname.php
 * @param string $mode [optional] <p>
 * mode is a single character that defines what
 * information is returned:
 * 'a': This is the default. Contains all modes in
 * the sequence "s n r v m".
 * @return string the description, as a string.
 */
function php_uname ($mode = null) {}

/**
 * Return a list of .ini files parsed from the additional ini dir
 * @link http://www.php.net/manual/en/function.php-ini-scanned-files.php
 * @return string a comma-separated string of .ini files on success. Each comma is
 * followed by a newline. If the directive --with-config-file-scan-dir wasn't set,
 * false is returned. If it was set and the directory was empty, an
 * empty string is returned. If a file is unrecognizable, the file will
 * still make it into the returned string but a PHP error will also result.
 * This PHP error will be seen both at compile time and while using
 * php_ini_scanned_files.
 */
function php_ini_scanned_files () {}

/**
 * Retrieve a path to the loaded php.ini file
 * @link http://www.php.net/manual/en/function.php-ini-loaded-file.php
 * @return string The loaded &php.ini; path, or false if one is not loaded.
 */
function php_ini_loaded_file () {}

/**
 * String comparisons using a "natural order" algorithm
 * @link http://www.php.net/manual/en/function.strnatcmp.php
 * @param string $str1 <p>
 * The first string.
 * </p>
 * @param string $str2 <p>
 * The second string.
 * </p>
 * @return int Similar to other string comparison functions, this one returns &lt; 0 if
 * str1 is less than str2; &gt;
 * 0 if str1 is greater than
 * str2, and 0 if they are equal.
 */
function strnatcmp ($str1, $str2) {}

/**
 * Case insensitive string comparisons using a "natural order" algorithm
 * @link http://www.php.net/manual/en/function.strnatcasecmp.php
 * @param string $str1 <p>
 * The first string.
 * </p>
 * @param string $str2 <p>
 * The second string.
 * </p>
 * @return int Similar to other string comparison functions, this one returns &lt; 0 if
 * str1 is less than str2 &gt;
 * 0 if str1 is greater than
 * str2, and 0 if they are equal.
 */
function strnatcasecmp ($str1, $str2) {}

/**
 * Count the number of substring occurrences
 * @link http://www.php.net/manual/en/function.substr-count.php
 * @param string $haystack <p>
 * The string to search in
 * </p>
 * @param string $needle <p>
 * The substring to search for
 * </p>
 * @param int $offset [optional] <p>
 * The offset where to start counting
 * </p>
 * @param int $length [optional] <p>
 * The maximum length after the specified offset to search for the
 * substring. It outputs a warning if the offset plus the length is
 * greater than the haystack length.
 * </p>
 * @return int This function returns an integer.
 */
function substr_count ($haystack, $needle, $offset = null, $length = null) {}

/**
 * Finds the length of the initial segment of a string consisting
   entirely of characters contained within a given mask.
 * @link http://www.php.net/manual/en/function.strspn.php
 * @param string $subject <p>
 * The string to examine.
 * </p>
 * @param string $mask <p>
 * The list of allowable characters.
 * </p>
 * @param int $start [optional] <p>
 * The position in subject to
 * start searching.
 * </p>
 * <p>
 * If start is given and is non-negative,
 * then strspn will begin
 * examining subject at
 * the start'th position. For instance, in
 * the string 'abcdef', the character at
 * position 0 is 'a', the
 * character at position 2 is
 * 'c', and so forth.
 * </p>
 * <p>
 * If start is given and is negative,
 * then strspn will begin
 * examining subject at
 * the start'th position from the end
 * of subject.
 * </p>
 * @param int $length [optional] <p>
 * The length of the segment from subject
 * to examine. 
 * </p>
 * <p>
 * If length is given and is non-negative,
 * then subject will be examined
 * for length characters after the starting
 * position.
 * </p>
 * <p>
 * If length is given and is negative,
 * then subject will be examined from the
 * starting position up to length
 * characters from the end of subject.
 * </p>
 * @return int the length of the initial segment of subject
 * which consists entirely of characters in mask.
 * </p>
 * <p>
 * When a start parameter is set, the returned length
 * is counted starting from this position, not from the beginning of
 * subject.
 */
function strspn ($subject, $mask, $start = null, $length = null) {}

/**
 * Find length of initial segment not matching mask
 * @link http://www.php.net/manual/en/function.strcspn.php
 * @param string $subject <p>
 * The string to examine.
 * </p>
 * @param string $mask <p>
 * The string containing every disallowed character.
 * </p>
 * @param int $start [optional] <p>
 * The position in subject to
 * start searching.
 * </p>
 * <p>
 * If start is given and is non-negative,
 * then strcspn will begin
 * examining subject at
 * the start'th position. For instance, in
 * the string 'abcdef', the character at
 * position 0 is 'a', the
 * character at position 2 is
 * 'c', and so forth.
 * </p>
 * <p>
 * If start is given and is negative,
 * then strspn will begin
 * examining subject at
 * the start'th position from the end
 * of subject.
 * </p>
 * @param int $length [optional] <p>
 * The length of the segment from subject
 * to examine. 
 * </p>
 * <p>
 * If length is given and is non-negative,
 * then subject will be examined
 * for length characters after the starting
 * position.
 * </p>
 * <p>
 * If length is given and is negative,
 * then subject will be examined from the
 * starting position up to length
 * characters from the end of subject.
 * </p>
 * @return int the length of the initial segment of subject
 * which consists entirely of characters not in mask.
 * </p>
 * <p>
 * When a start parameter is set, the returned length
 * is counted starting from this position, not from the beginning of
 * subject.
 */
function strcspn ($subject, $mask, $start = null, $length = null) {}

/**
 * Tokenize string
 * @link http://www.php.net/manual/en/function.strtok.php
 * @param string $str <p>
 * The string being split up into smaller strings (tokens).
 * </p>
 * @param string $token <p>
 * The delimiter used when splitting up str.
 * </p>
 * @return string A string token.
 */
function strtok ($str, $token) {}

/**
 * Make a string uppercase
 * @link http://www.php.net/manual/en/function.strtoupper.php
 * @param string $string <p>
 * The input string.
 * </p>
 * @return string the uppercased string.
 */
function strtoupper ($string) {}

/**
 * Make a string lowercase
 * @link http://www.php.net/manual/en/function.strtolower.php
 * @param string $string <p>
 * The input string.
 * </p>
 * @return string the lowercased string.
 */
function strtolower ($string) {}

/**
 * Find the position of the first occurrence of a substring in a string
 * @link http://www.php.net/manual/en/function.strpos.php
 * @param string $haystack <p>
 * The string to search in.
 * </p>
 * @param mixed $needle <p>
 * If needle is not a string, it is converted
 * to an integer and applied as the ordinal value of a character.
 * </p>
 * @param int $offset [optional] <p>
 * If specified, search will start this number of characters counted from
 * the beginning of the string. Unlike strrpos and
 * strripos, the offset cannot be negative.
 * </p>
 * @return mixed the position of where the needle exists relative to the beginning of
 * the haystack string (independent of offset).
 * Also note that string positions start at 0, and not 1.
 * </p>
 * <p>
 * Returns false if the needle was not found.
 */
function strpos ($haystack, $needle, $offset = null) {}

/**
 * Find the position of the first occurrence of a case-insensitive substring in a string
 * @link http://www.php.net/manual/en/function.stripos.php
 * @param string $haystack <p>
 * The string to search in.
 * </p>
 * @param string $needle <p>
 * Note that the needle may be a string of one or
 * more characters.
 * </p>
 * <p>
 * If needle is not a string, it is converted to
 * an integer and applied as the ordinal value of a character.
 * </p>
 * @param int $offset [optional] <p>
 * If specified, search will start this number of characters counted from
 * the beginning of the string. Unlike strrpos and
 * strripos, the offset cannot be negative.
 * </p>
 * @return mixed the position of where the needle exists relative to the beginnning of
 * the haystack string (independent of offset).
 * Also note that string positions start at 0, and not 1.
 * </p>
 * <p>
 * Returns false if the needle was not found.
 */
function stripos ($haystack, $needle, $offset = null) {}

/**
 * Find the position of the last occurrence of a substring in a string
 * @link http://www.php.net/manual/en/function.strrpos.php
 * @param string $haystack <p>
 * The string to search in.
 * </p>
 * @param string $needle <p>
 * If needle is not a string, it is converted
 * to an integer and applied as the ordinal value of a character.
 * </p>
 * @param int $offset [optional] <p>
 * If specified, search will start this number of characters counted from the
 * beginning of the string. If the value is negative, search will instead start
 * from that many characters from the end of the string, searching backwards.
 * </p>
 * @return int the position where the needle exists relative to the beginnning of
 * the haystack string (independent of search direction
 * or offset).
 * Also note that string positions start at 0, and not 1.
 * </p>
 * <p>
 * Returns false if the needle was not found.
 */
function strrpos ($haystack, $needle, $offset = null) {}

/**
 * Find the position of the last occurrence of a case-insensitive substring in a string
 * @link http://www.php.net/manual/en/function.strripos.php
 * @param string $haystack <p>
 * The string to search in.
 * </p>
 * @param string $needle <p>
 * If needle is not a string, it is converted
 * to an integer and applied as the ordinal value of a character.
 * </p>
 * @param int $offset [optional] <p>
 * If specified, search will start this number of characters counted from the
 * beginning of the string. If the value is negative, search will instead start
 * from that many characters from the end of the string, searching backwards.
 * </p>
 * @return int the position where the needle exists relative to the beginnning of
 * the haystack string (independent of search direction
 * or offset).
 * Also note that string positions start at 0, and not 1.
 * </p>
 * <p>
 * Returns false if the needle was not found.
 */
function strripos ($haystack, $needle, $offset = null) {}

/**
 * Reverse a string
 * @link http://www.php.net/manual/en/function.strrev.php
 * @param string $string <p>
 * The string to be reversed.
 * </p>
 * @return string the reversed string.
 */
function strrev ($string) {}

/**
 * Convert logical Hebrew text to visual text
 * @link http://www.php.net/manual/en/function.hebrev.php
 * @param string $hebrew_text <p>
 * A Hebrew input string.
 * </p>
 * @param int $max_chars_per_line [optional] <p>
 * This optional parameter indicates maximum number of characters per
 * line that will be returned.
 * </p>
 * @return string the visual string.
 */
function hebrev ($hebrew_text, $max_chars_per_line = null) {}

/**
 * Convert logical Hebrew text to visual text with newline conversion
 * @link http://www.php.net/manual/en/function.hebrevc.php
 * @param string $hebrew_text <p>
 * A Hebrew input string.
 * </p>
 * @param int $max_chars_per_line [optional] <p>
 * This optional parameter indicates maximum number of characters per
 * line that will be returned.
 * </p>
 * @return string the visual string.
 */
function hebrevc ($hebrew_text, $max_chars_per_line = null) {}

/**
 * Inserts HTML line breaks before all newlines in a string
 * @link http://www.php.net/manual/en/function.nl2br.php
 * @param string $string <p>
 * The input string.
 * </p>
 * @param bool $is_xhtml [optional] <p>
 * Whether to use XHTML compatible line breaks or not.
 * </p>
 * @return string the altered string.
 */
function nl2br ($string, $is_xhtml = null) {}

/**
 * Returns trailing name component of path
 * @link http://www.php.net/manual/en/function.basename.php
 * @param string $path <p>
 * A path.
 * </p>
 * <p>
 * On Windows, both slash (/) and backslash
 * (\) are used as directory separator character. In
 * other environments, it is the forward slash (/).
 * </p>
 * @param string $suffix [optional] <p>
 * If the name component ends in suffix this will also
 * be cut off.
 * </p>
 * @return string the base name of the given path.
 */
function basename ($path, $suffix = null) {}

/**
 * Returns a parent directory's path
 * @link http://www.php.net/manual/en/function.dirname.php
 * @param string $path <p>
 * A path.
 * </p>
 * <p>
 * On Windows, both slash (/) and backslash
 * (\) are used as directory separator character. In
 * other environments, it is the forward slash (/).
 * </p>
 * @param int $levels [optional] <p>
 * The number of parent directories to go up.
 * </p>
 * <p>
 * This must be an integer greater than 0.
 * </p>
 * @return string the path of a parent directory. If there are no slashes in
 * path, a dot ('.') is returned,
 * indicating the current directory. Otherwise, the returned string is
 * path with any trailing
 * /component removed.
 */
function dirname ($path, $levels = null) {}

/**
 * Returns information about a file path
 * @link http://www.php.net/manual/en/function.pathinfo.php
 * @param string $path <p>
 * The path to be parsed.
 * </p>
 * @param int $options [optional] <p>
 * If present, specifies a specific element to be returned; one of
 * PATHINFO_DIRNAME,
 * PATHINFO_BASENAME,
 * PATHINFO_EXTENSION or
 * PATHINFO_FILENAME.
 * </p>
 * <p>If options is not specified, returns all
 * available elements.
 * </p>
 * @return mixed If the options parameter is not passed, an
 * associative array containing the following elements is
 * returned:
 * dirname, basename,
 * extension (if any), and filename.
 * </p>
 * <p>
 * If the path has more than one extension,
 * PATHINFO_EXTENSION returns only the last one and
 * PATHINFO_FILENAME only strips the last one.
 * (see first example below).
 * </p>
 * <p>
 * If the path does not have an extension, no
 * extension element will be returned
 * (see second example below).
 * </p>
 * <p>
 * If options is present, returns a
 * string containing the requested element.
 */
function pathinfo ($path, $options = null) {}

/**
 * Un-quotes a quoted string
 * @link http://www.php.net/manual/en/function.stripslashes.php
 * @param string $str <p>
 * The input string.
 * </p>
 * @return string a string with backslashes stripped off.
 * (\' becomes ' and so on.)
 * Double backslashes (\\) are made into a single
 * backslash (\).
 */
function stripslashes ($str) {}

/**
 * Un-quote string quoted with <function>addcslashes</function>
 * @link http://www.php.net/manual/en/function.stripcslashes.php
 * @param string $str <p>
 * The string to be unescaped.
 * </p>
 * @return string the unescaped string.
 */
function stripcslashes ($str) {}

/**
 * Find the first occurrence of a string
 * @link http://www.php.net/manual/en/function.strstr.php
 * @param string $haystack <p>
 * The input string.
 * </p>
 * @param mixed $needle <p>
 * If needle is not a string, it is converted to
 * an integer and applied as the ordinal value of a character.
 * </p>
 * @param bool $before_needle [optional] <p>
 * If true, strstr returns
 * the part of the haystack before the first
 * occurrence of the needle (excluding the needle).
 * </p>
 * @return string the portion of string, or false if needle
 * is not found.
 */
function strstr ($haystack, $needle, $before_needle = null) {}

/**
 * Case-insensitive <function>strstr</function>
 * @link http://www.php.net/manual/en/function.stristr.php
 * @param string $haystack <p>
 * The string to search in
 * </p>
 * @param mixed $needle <p>
 * If needle is not a string, it is converted to
 * an integer and applied as the ordinal value of a character.
 * </p>
 * @param bool $before_needle [optional] <p>
 * If true, stristr
 * returns the part of the haystack before the
 * first occurrence of the needle (excluding needle).
 * </p>
 * @return string the matched substring. If needle is not
 * found, returns false.
 */
function stristr ($haystack, $needle, $before_needle = null) {}

/**
 * Find the last occurrence of a character in a string
 * @link http://www.php.net/manual/en/function.strrchr.php
 * @param string $haystack <p>
 * The string to search in
 * </p>
 * @param mixed $needle <p>
 * If needle contains more than one character,
 * only the first is used. This behavior is different from that of
 * strstr.
 * </p>
 * <p>
 * If needle is not a string, it is converted to
 * an integer and applied as the ordinal value of a character.
 * </p>
 * @return string This function returns the portion of string, or false if
 * needle is not found.
 */
function strrchr ($haystack, $needle) {}

/**
 * Randomly shuffles a string
 * @link http://www.php.net/manual/en/function.str-shuffle.php
 * @param string $str <p>
 * The input string.
 * </p>
 * @return string the shuffled string.
 */
function str_shuffle ($str) {}

/**
 * Return information about words used in a string
 * @link http://www.php.net/manual/en/function.str-word-count.php
 * @param string $string <p>
 * The string
 * </p>
 * @param int $format [optional] <p>
 * Specify the return value of this function. The current supported values
 * are:
 * 0 - returns the number of words found
 * @param string $charlist [optional] <p>
 * A list of additional characters which will be considered as 'word'
 * </p>
 * @return mixed an array or an integer, depending on the
 * format chosen.
 */
function str_word_count ($string, $format = null, $charlist = null) {}

/**
 * Convert a string to an array
 * @link http://www.php.net/manual/en/function.str-split.php
 * @param string $string <p>
 * The input string.
 * </p>
 * @param int $split_length [optional] <p>
 * Maximum length of the chunk.
 * </p>
 * @return array If the optional split_length parameter is
 * specified, the returned array will be broken down into chunks with each
 * being split_length in length, otherwise each chunk
 * will be one character in length.
 * </p>
 * <p>
 * false is returned if split_length is less than 1.
 * If the split_length length exceeds the length of
 * string, the entire string is returned as the first
 * (and only) array element.
 */
function str_split ($string, $split_length = null) {}

/**
 * Search a string for any of a set of characters
 * @link http://www.php.net/manual/en/function.strpbrk.php
 * @param string $haystack <p>
 * The string where char_list is looked for.
 * </p>
 * @param string $char_list <p>
 * This parameter is case sensitive.
 * </p>
 * @return string a string starting from the character found, or false if it is
 * not found.
 */
function strpbrk ($haystack, $char_list) {}

/**
 * Binary safe comparison of two strings from an offset, up to length characters
 * @link http://www.php.net/manual/en/function.substr-compare.php
 * @param string $main_str <p>
 * The main string being compared.
 * </p>
 * @param string $str <p>
 * The secondary string being compared.
 * </p>
 * @param int $offset <p>
 * The start position for the comparison. If negative, it starts counting
 * from the end of the string.
 * </p>
 * @param int $length [optional] <p>
 * The length of the comparison. The default value is the largest of the
 * length of the str compared to the length of
 * main_str less the
 * offset.
 * </p>
 * @param bool $case_insensitivity [optional] <p>
 * If case_insensitivity is true, comparison is
 * case insensitive.
 * </p>
 * @return int &lt; 0 if main_str from position
 * offset is less than str, &gt;
 * 0 if it is greater than str, and 0 if they are equal.
 * If offset is equal to or greater than the length of
 * main_str, or the length is
 * set and is less than 1 (prior to PHP 5.6),
 * substr_compare prints a warning and returns
 * false.
 */
function substr_compare ($main_str, $str, $offset, $length = null, $case_insensitivity = null) {}

/**
 * Locale based string comparison
 * @link http://www.php.net/manual/en/function.strcoll.php
 * @param string $str1 <p>
 * The first string.
 * </p>
 * @param string $str2 <p>
 * The second string.
 * </p>
 * @return int &lt; 0 if str1 is less than
 * str2; &gt; 0 if
 * str1 is greater than
 * str2, and 0 if they are equal.
 */
function strcoll ($str1, $str2) {}

/**
 * Formats a number as a currency string
 * @link http://www.php.net/manual/en/function.money-format.php
 * @param string $format <p>
 * The format specification consists of the following sequence:
 * <p>a % character</p>
 * @param float $number <p>
 * The number to be formatted.
 * </p>
 * @return string the formatted string. Characters before and after the formatting
 * string will be returned unchanged.
 * Non-numeric number causes returning &null; and
 * emitting E_WARNING.
 */
function money_format ($format, $number) {}

/**
 * Return part of a string
 * @link http://www.php.net/manual/en/function.substr.php
 * @param string $string <p>
 * The input string. Must be one character or longer.
 * </p>
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
 * <p>
 * If string is less than
 * start characters long, false will be returned.
 * </p>
 * <p>
 * Using a negative start
 * ]]>
 * </p>
 * @param int $length [optional] <p>
 * If length is given and is positive, the string
 * returned will contain at most length characters
 * beginning from start (depending on the length of
 * string).
 * </p>
 * <p>
 * If length is given and is negative, then that many
 * characters will be omitted from the end of string
 * (after the start position has been calculated when a
 * start is negative). If
 * start denotes the position of this truncation or
 * beyond, false will be returned.
 * </p>
 * <p>
 * If length is given and is 0,
 * false or &null;, an empty string will be returned.
 * </p>
 * <p>
 * If length is omitted, the substring starting from
 * start until the end of the string will be
 * returned.
 * </p>
 * Using a negative length
 * ]]>
 * @return string the extracted part of string; or false on failure, or
 * an empty string.
 */
function substr ($string, $start, $length = null) {}

/**
 * Replace text within a portion of a string
 * @link http://www.php.net/manual/en/function.substr-replace.php
 * @param mixed $string <p>
 * The input string.
 * </p>
 * <p>
 * An array of strings can be provided, in which
 * case the replacements will occur on each string in turn. In this case,
 * the replacement, start
 * and length parameters may be provided either as
 * scalar values to be applied to each input string in turn, or as
 * arrays, in which case the corresponding array element will
 * be used for each input string.
 * </p>
 * @param mixed $replacement <p>
 * The replacement string.
 * </p>
 * @param mixed $start <p>
 * If start is positive, the replacing will
 * begin at the start'th offset into
 * string.
 * </p>
 * <p>
 * If start is negative, the replacing will
 * begin at the start'th character from the
 * end of string.
 * </p>
 * @param mixed $length [optional] <p>
 * If given and is positive, it represents the length of the portion of
 * string which is to be replaced. If it is
 * negative, it represents the number of characters from the end of
 * string at which to stop replacing. If it
 * is not given, then it will default to strlen(
 * string ); i.e. end the replacing at the
 * end of string. Of course, if
 * length is zero then this function will have the
 * effect of inserting replacement into
 * string at the given
 * start offset.
 * </p>
 * @return mixed The result string is returned. If string is an
 * array then array is returned.
 */
function substr_replace ($string, $replacement, $start, $length = null) {}

/**
 * Quote meta characters
 * @link http://www.php.net/manual/en/function.quotemeta.php
 * @param string $str <p>
 * The input string.
 * </p>
 * @return string the string with meta characters quoted, or false if an empty
 * string is given as str.
 */
function quotemeta ($str) {}

/**
 * Make a string's first character uppercase
 * @link http://www.php.net/manual/en/function.ucfirst.php
 * @param string $str <p>
 * The input string.
 * </p>
 * @return string the resulting string.
 */
function ucfirst ($str) {}

/**
 * Make a string's first character lowercase
 * @link http://www.php.net/manual/en/function.lcfirst.php
 * @param string $str <p>
 * The input string.
 * </p>
 * @return string the resulting string.
 */
function lcfirst ($str) {}

/**
 * Uppercase the first character of each word in a string
 * @link http://www.php.net/manual/en/function.ucwords.php
 * @param string $str <p>
 * The input string.
 * </p>
 * @param string $delimiters [optional] <p>
 * The optional delimiters contains the word separator characters.
 * </p>
 * @return string the modified string.
 */
function ucwords ($str, $delimiters = null) {}

/**
 * Translate characters or replace substrings
 * @link http://www.php.net/manual/en/function.strtr.php
 * @param string $str <p>
 * The string being translated.
 * </p>
 * @param string $from <p>
 * The string being translated to to.
 * </p>
 * @param string $to <p>
 * The string replacing from.
 * </p>
 * @return string the translated string.
 * </p>
 * <p>
 * If replace_pairs contains a key which
 * is an empty string (""),
 * false will be returned. If the str is not a scalar
 * then it is not typecasted into a string, instead a warning is raised and 
 * &null; is returned.
 */
function strtr ($str, $from, $to) {}

/**
 * Quote string with slashes
 * @link http://www.php.net/manual/en/function.addslashes.php
 * @param string $str <p>
 * The string to be escaped.
 * </p>
 * @return string the escaped string.
 */
function addslashes ($str) {}

/**
 * Quote string with slashes in a C style
 * @link http://www.php.net/manual/en/function.addcslashes.php
 * @param string $str <p>
 * The string to be escaped.
 * </p>
 * @param string $charlist <p>
 * A list of characters to be escaped. If
 * charlist contains characters
 * \n, \r etc., they are
 * converted in C-like style, while other non-alphanumeric characters
 * with ASCII codes lower than 32 and higher than 126 converted to
 * octal representation.
 * </p>
 * <p>
 * When you define a sequence of characters in the charlist argument
 * make sure that you know what characters come between the
 * characters that you set as the start and end of the range.
 * ]]>
 * Also, if the first character in a range has a higher ASCII value
 * than the second character in the range, no range will be
 * constructed. Only the start, end and period characters will be
 * escaped. Use the ord function to find the
 * ASCII value for a character.
 * ]]>
 * </p>
 * <p>
 * Be careful if you choose to escape characters 0, a, b, f, n, r, t and
 * v. They will be converted to \0, \a, \b, \f, \n, \r, \t and \v, all of
 * which are predefined escape sequences in C. Many of these sequences are
 * also defined in other C-derived languages, including PHP, meaning that
 * you may not get the desired result if you use the output of
 * addcslashes to generate code in those languages
 * with these characters defined in charlist.
 * </p>
 * @return string the escaped string.
 */
function addcslashes ($str, $charlist) {}

/**
 * Strip whitespace (or other characters) from the end of a string
 * @link http://www.php.net/manual/en/function.rtrim.php
 * @param string $str <p>
 * The input string.
 * </p>
 * @param string $character_mask [optional] <p>
 * You can also specify the characters you want to strip, by means
 * of the character_mask parameter.
 * Simply list all characters that you want to be stripped. With
 * .. you can specify a range of characters.
 * </p>
 * @return string the modified string.
 */
function rtrim ($str, $character_mask = null) {}

/**
 * Replace all occurrences of the search string with the replacement string
 * @link http://www.php.net/manual/en/function.str-replace.php
 * @param mixed $search <p>
 * The value being searched for, otherwise known as the needle.
 * An array may be used to designate multiple needles.
 * </p>
 * @param mixed $replace <p>
 * The replacement value that replaces found search
 * values. An array may be used to designate multiple replacements.
 * </p>
 * @param mixed $subject <p>
 * The string or array being searched and replaced on,
 * otherwise known as the haystack.
 * </p>
 * <p>
 * If subject is an array, then the search and
 * replace is performed with every entry of
 * subject, and the return value is an array as
 * well.
 * </p>
 * @param int $count [optional] <p>
 * If passed, this will be set to the number of replacements performed.
 * </p>
 * @return mixed This function returns a string or an array with the replaced values.
 */
function str_replace ($search, $replace, $subject, &$count = null) {}

/**
 * Case-insensitive version of <function>str_replace</function>.
 * @link http://www.php.net/manual/en/function.str-ireplace.php
 * @param mixed $search <p>
 * The value being searched for, otherwise known as the
 * needle. An array may be used to designate
 * multiple needles.
 * </p>
 * @param mixed $replace <p>
 * The replacement value that replaces found search
 * values. An array may be used to designate multiple replacements.
 * </p>
 * @param mixed $subject <p>
 * The string or array being searched and replaced on,
 * otherwise known as the haystack.
 * </p>
 * <p>
 * If subject is an array, then the search and
 * replace is performed with every entry of 
 * subject, and the return value is an array as
 * well.
 * </p>
 * @param int $count [optional] <p>
 * If passed, this will be set to the number of replacements performed.
 * </p>
 * @return mixed a string or an array of replacements.
 */
function str_ireplace ($search, $replace, $subject, &$count = null) {}

/**
 * Repeat a string
 * @link http://www.php.net/manual/en/function.str-repeat.php
 * @param string $input <p>
 * The string to be repeated.
 * </p>
 * @param int $multiplier <p>
 * Number of time the input string should be
 * repeated.
 * </p>
 * <p>
 * multiplier has to be greater than or equal to 0.
 * If the multiplier is set to 0, the function
 * will return an empty string.
 * </p>
 * @return string the repeated string.
 */
function str_repeat ($input, $multiplier) {}

/**
 * Return information about characters used in a string
 * @link http://www.php.net/manual/en/function.count-chars.php
 * @param string $string <p>
 * The examined string.
 * </p>
 * @param int $mode [optional] <p>
 * See return values.
 * </p>
 * @return mixed Depending on mode
 * count_chars returns one of the following:
 * 0 - an array with the byte-value as key and the frequency of
 * every byte as value.
 * 1 - same as 0 but only byte-values with a frequency greater
 * than zero are listed.
 * 2 - same as 0 but only byte-values with a frequency equal to
 * zero are listed.
 * 3 - a string containing all unique characters is returned.
 * 4 - a string containing all not used characters is returned.
 */
function count_chars ($string, $mode = null) {}

/**
 * Split a string into smaller chunks
 * @link http://www.php.net/manual/en/function.chunk-split.php
 * @param string $body <p>
 * The string to be chunked.
 * </p>
 * @param int $chunklen [optional] <p>
 * The chunk length.
 * </p>
 * @param string $end [optional] <p>
 * The line ending sequence.
 * </p>
 * @return string the chunked string.
 */
function chunk_split ($body, $chunklen = null, $end = null) {}

/**
 * Strip whitespace (or other characters) from the beginning and end of a string
 * @link http://www.php.net/manual/en/function.trim.php
 * @param string $str <p>
 * The string that will be trimmed.
 * </p>
 * @param string $character_mask [optional] <p>
 * Optionally, the stripped characters can also be specified using
 * the character_mask parameter.
 * Simply list all characters that you want to be stripped. With
 * .. you can specify a range of characters.
 * </p>
 * @return string The trimmed string.
 */
function trim ($str, $character_mask = null) {}

/**
 * Strip whitespace (or other characters) from the beginning of a string
 * @link http://www.php.net/manual/en/function.ltrim.php
 * @param string $str <p>
 * The input string.
 * </p>
 * @param string $character_mask [optional] <p>
 * You can also specify the characters you want to strip, by means of the
 * character_mask parameter.
 * Simply list all characters that you want to be stripped. With
 * .. you can specify a range of characters.
 * </p>
 * @return string This function returns a string with whitespace stripped from the
 * beginning of str.
 * Without the second parameter,
 * ltrim will strip these characters:
 * " " (ASCII 32
 * (0x20)), an ordinary space.
 * "\t" (ASCII 9
 * (0x09)), a tab.
 * "\n" (ASCII 10
 * (0x0A)), a new line (line feed).
 * "\r" (ASCII 13
 * (0x0D)), a carriage return.
 * "\0" (ASCII 0
 * (0x00)), the NUL-byte.
 * "\x0B" (ASCII 11
 * (0x0B)), a vertical tab.
 */
function ltrim ($str, $character_mask = null) {}

/**
 * Strip HTML and PHP tags from a string
 * @link http://www.php.net/manual/en/function.strip-tags.php
 * @param string $str <p>
 * The input string.
 * </p>
 * @param string $allowable_tags [optional] <p>
 * You can use the optional second parameter to specify tags which should
 * not be stripped.
 * </p>
 * <p>
 * HTML comments and PHP tags are also stripped. This is hardcoded and
 * can not be changed with allowable_tags.
 * </p>
 * <p>
 * This parameter should not contain whitespace.
 * strip_tags sees a tag as a case-insensitive
 * string between &lt; and the first whitespace or
 * &gt;.
 * </p>
 * <p>
 * In PHP 5.3.4 and later, you will also need to include the self-closing
 * XHTML tag to strip these from str. For example,
 * to strip both &lt;br&gt; and
 * &lt;br/&gt;, you should use:
 * </p>
 * ]]>
 * @return string the stripped string.
 */
function strip_tags ($str, $allowable_tags = null) {}

/**
 * Calculate the similarity between two strings
 * @link http://www.php.net/manual/en/function.similar-text.php
 * @param string $first <p>
 * The first string.
 * </p>
 * @param string $second <p>
 * The second string.
 * </p>
 * @param float $percent [optional] <p>
 * By passing a reference as third argument,
 * similar_text will calculate the similarity in
 * percent for you. 
 * </p>
 * @return int the number of matching chars in both strings.
 */
function similar_text ($first, $second, &$percent = null) {}

/**
 * Split a string by string
 * @link http://www.php.net/manual/en/function.explode.php
 * @param string $delimiter <p>
 * The boundary string.
 * </p>
 * @param string $string <p>
 * The input string.
 * </p>
 * @param int $limit [optional] <p>
 * If limit is set and positive, the returned array will contain
 * a maximum of limit elements with the last
 * element containing the rest of string.
 * </p>
 * <p>
 * If the limit parameter is negative, all components
 * except the last -limit are returned.
 * </p>
 * <p>
 * If the limit parameter is zero, then this is treated as 1.
 * </p>
 * @return array an array of strings
 * created by splitting the string parameter on
 * boundaries formed by the delimiter.
 * </p>
 * <p>
 * If delimiter is an empty string (""),
 * explode will return false.
 * If delimiter contains a value that is not
 * contained in string and a negative
 * limit is used, then an empty array will be
 * returned, otherwise an array containing
 * string will be returned.
 */
function explode ($delimiter, $string, $limit = null) {}

/**
 * Join array elements with a string
 * @link http://www.php.net/manual/en/function.implode.php
 * @param string $glue <p>
 * Defaults to an empty string.
 * </p>
 * @param array $pieces <p>
 * The array of strings to implode.
 * </p>
 * @return string a string containing a string representation of all the array
 * elements in the same order, with the glue string between each element.
 */
function implode ($glue, array $pieces) {}

/**
 * &Alias; <function>implode</function>
 * @link http://www.php.net/manual/en/function.join.php
 * @param $glue
 * @param $pieces
 */
function join ($glue, $pieces) {}

/**
 * Set locale information
 * @link http://www.php.net/manual/en/function.setlocale.php
 * @param int $category <p>
 * category is a named constant specifying the
 * category of the functions affected by the locale setting:
 * LC_ALL for all of the below
 * @param string $locale <p>
 * If locale is &null; or the empty string
 * "", the locale names will be set from the
 * values of environment variables with the same names as the above
 * categories, or from "LANG".
 * </p>
 * <p>
 * If locale is "0",
 * the locale setting is not affected, only the current setting is returned.
 * </p>
 * <p>
 * If locale is an array or followed by additional
 * parameters then each array element or parameter is tried to be set as
 * new locale until success. This is useful if a locale is known under
 * different names on different systems or for providing a fallback
 * for a possibly not available locale.
 * </p>
 * @param string $_ [optional] 
 * @return string the new current locale, or false if the locale functionality is
 * not implemented on your platform, the specified locale does not exist or
 * the category name is invalid.
 * </p>
 * <p>
 * An invalid category name also causes a warning message. Category/locale
 * names can be found in RFC 1766
 * and ISO 639.
 * Different systems have different naming schemes for locales.
 * </p>
 * <p>
 * The return value of setlocale depends
 * on the system that PHP is running. It returns exactly
 * what the system setlocale function returns.
 */
function setlocale ($category, $locale, $_ = null) {}

/**
 * Get numeric formatting information
 * @link http://www.php.net/manual/en/function.localeconv.php
 * @return array localeconv returns data based upon the current locale
 * as set by setlocale. The associative array that is
 * returned contains the following fields:
 * <tr valign="top">
 * <td>Array element</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>decimal_point</td>
 * <td>Decimal point character</td>
 * </tr>
 * <tr valign="top">
 * <td>thousands_sep</td>
 * <td>Thousands separator</td>
 * </tr>
 * <tr valign="top">
 * <td>grouping</td>
 * <td>Array containing numeric groupings</td>
 * </tr>
 * <tr valign="top">
 * <td>int_curr_symbol</td>
 * <td>International currency symbol (i.e. USD)</td>
 * </tr>
 * <tr valign="top">
 * <td>currency_symbol</td>
 * <td>Local currency symbol (i.e. $)</td>
 * </tr>
 * <tr valign="top">
 * <td>mon_decimal_point</td>
 * <td>Monetary decimal point character</td>
 * </tr>
 * <tr valign="top">
 * <td>mon_thousands_sep</td>
 * <td>Monetary thousands separator</td>
 * </tr>
 * <tr valign="top">
 * <td>mon_grouping</td>
 * <td>Array containing monetary groupings</td>
 * </tr>
 * <tr valign="top">
 * <td>positive_sign</td>
 * <td>Sign for positive values</td>
 * </tr>
 * <tr valign="top">
 * <td>negative_sign</td>
 * <td>Sign for negative values</td>
 * </tr>
 * <tr valign="top">
 * <td>int_frac_digits</td>
 * <td>International fractional digits</td>
 * </tr>
 * <tr valign="top">
 * <td>frac_digits</td>
 * <td>Local fractional digits</td>
 * </tr>
 * <tr valign="top">
 * <td>p_cs_precedes</td>
 * <td>
 * true if currency_symbol precedes a positive value, false
 * if it succeeds one
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>p_sep_by_space</td>
 * <td>
 * true if a space separates currency_symbol from a positive
 * value, false otherwise
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>n_cs_precedes</td>
 * <td>
 * true if currency_symbol precedes a negative value, false
 * if it succeeds one
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>n_sep_by_space</td>
 * <td>
 * true if a space separates currency_symbol from a negative
 * value, false otherwise
 * </td>
 * </tr>
 * <td>p_sign_posn</td>
 * <td>
 * 0 - Parentheses surround the quantity and currency_symbol
 * 1 - The sign string precedes the quantity and currency_symbol
 * 2 - The sign string succeeds the quantity and currency_symbol
 * 3 - The sign string immediately precedes the currency_symbol
 * 4 - The sign string immediately succeeds the currency_symbol
 * </td>
 * </tr>
 * <td>n_sign_posn</td>
 * <td>
 * 0 - Parentheses surround the quantity and currency_symbol
 * 1 - The sign string precedes the quantity and currency_symbol
 * 2 - The sign string succeeds the quantity and currency_symbol
 * 3 - The sign string immediately precedes the currency_symbol
 * 4 - The sign string immediately succeeds the currency_symbol
 * </td>
 * </tr>
 * </p>
 * <p>
 * The p_sign_posn, and n_sign_posn contain a string
 * of formatting options. Each number representing one of the above listed conditions.
 * </p>
 * <p>
 * The grouping fields contain arrays that define the way numbers should be
 * grouped. For example, the monetary grouping field for the nl_NL locale (in
 * UTF-8 mode with the euro sign), would contain a 2 item array with the
 * values 3 and 3. The higher the index in the array, the farther left the
 * grouping is. If an array element is equal to CHAR_MAX,
 * no further grouping is done. If an array element is equal to 0, the previous
 * element should be used.
 */
function localeconv () {}

/**
 * Query language and locale information
 * @link http://www.php.net/manual/en/function.nl-langinfo.php
 * @param int $item <p>
 * item may be an integer value of the element or the
 * constant name of the element. The following is a list of constant names
 * for item that may be used and their description.
 * Some of these constants may not be defined or hold no value for certain
 * locales.
 * <table>
 * nl_langinfo Constants
 * <tr valign="top">
 * <td>Constant</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * LC_TIME Category Constants</td>
 * </tr>
 * <tr valign="top">
 * <td>ABDAY_(1-7)</td>
 * <td>Abbreviated name of n-th day of the week.</td>
 * </tr>
 * <tr valign="top">
 * <td>DAY_(1-7)</td>
 * <td>Name of the n-th day of the week (DAY_1 = Sunday).</td>
 * </tr>
 * <tr valign="top">
 * <td>ABMON_(1-12)</td>
 * <td>Abbreviated name of the n-th month of the year.</td>
 * </tr>
 * <tr valign="top">
 * <td>MON_(1-12)</td>
 * <td>Name of the n-th month of the year.</td>
 * </tr>
 * <tr valign="top">
 * <td>AM_STR</td>
 * <td>String for Ante meridian.</td>
 * </tr>
 * <tr valign="top">
 * <td>PM_STR</td>
 * <td>String for Post meridian.</td>
 * </tr>
 * <tr valign="top">
 * <td>D_T_FMT</td>
 * <td>String that can be used as the format string for strftime to represent time and date.</td>
 * </tr>
 * <tr valign="top">
 * <td>D_FMT</td>
 * <td>String that can be used as the format string for strftime to represent date.</td>
 * </tr>
 * <tr valign="top">
 * <td>T_FMT</td>
 * <td>String that can be used as the format string for strftime to represent time.</td>
 * </tr>
 * <tr valign="top">
 * <td>T_FMT_AMPM</td>
 * <td>String that can be used as the format string for strftime to represent time in 12-hour format with ante/post meridian.</td>
 * </tr>
 * <tr valign="top">
 * <td>ERA</td>
 * <td>Alternate era.</td>
 * </tr>
 * <tr valign="top">
 * <td>ERA_YEAR</td>
 * <td>Year in alternate era format.</td>
 * </tr>
 * <tr valign="top">
 * <td>ERA_D_T_FMT</td>
 * <td>Date and time in alternate era format (string can be used in strftime).</td>
 * </tr>
 * <tr valign="top">
 * <td>ERA_D_FMT</td>
 * <td>Date in alternate era format (string can be used in strftime).</td>
 * </tr>
 * <tr valign="top">
 * <td>ERA_T_FMT</td>
 * <td>Time in alternate era format (string can be used in strftime).</td>
 * </tr>
 * <tr valign="top">
 * LC_MONETARY Category Constants</td>
 * </tr>
 * <tr valign="top">
 * <td>INT_CURR_SYMBOL</td>
 * <td>International currency symbol.</td>
 * </tr>
 * <tr valign="top">
 * <td>CURRENCY_SYMBOL</td>
 * <td>Local currency symbol.</td>
 * </tr>
 * <tr valign="top">
 * <td>CRNCYSTR</td>
 * <td>Same value as CURRENCY_SYMBOL.</td>
 * </tr>
 * <tr valign="top">
 * <td>MON_DECIMAL_POINT</td>
 * <td>Decimal point character.</td>
 * </tr>
 * <tr valign="top">
 * <td>MON_THOUSANDS_SEP</td>
 * <td>Thousands separator (groups of three digits).</td>
 * </tr>
 * <tr valign="top">
 * <td>MON_GROUPING</td>
 * <td>Like "grouping" element.</td>
 * </tr>
 * <tr valign="top">
 * <td>POSITIVE_SIGN</td>
 * <td>Sign for positive values.</td>
 * </tr>
 * <tr valign="top">
 * <td>NEGATIVE_SIGN</td>
 * <td>Sign for negative values.</td>
 * </tr>
 * <tr valign="top">
 * <td>INT_FRAC_DIGITS</td>
 * <td>International fractional digits.</td>
 * </tr>
 * <tr valign="top">
 * <td>FRAC_DIGITS</td>
 * <td>Local fractional digits.</td>
 * </tr>
 * <tr valign="top">
 * <td>P_CS_PRECEDES</td>
 * <td>Returns 1 if CURRENCY_SYMBOL precedes a positive value.</td>
 * </tr>
 * <tr valign="top">
 * <td>P_SEP_BY_SPACE</td>
 * <td>Returns 1 if a space separates CURRENCY_SYMBOL from a positive value.</td>
 * </tr>
 * <tr valign="top">
 * <td>N_CS_PRECEDES</td>
 * <td>Returns 1 if CURRENCY_SYMBOL precedes a negative value.</td>
 * </tr>
 * <tr valign="top">
 * <td>N_SEP_BY_SPACE</td>
 * <td>Returns 1 if a space separates CURRENCY_SYMBOL from a negative value.</td>
 * </tr>
 * <tr valign="top">
 * <td>P_SIGN_POSN</td>
 * Returns 0 if parentheses surround the quantity and CURRENCY_SYMBOL.
 * @return string the element as a string, or false if item
 * is not valid.
 */
function nl_langinfo ($item) {}

/**
 * Calculate the soundex key of a string
 * @link http://www.php.net/manual/en/function.soundex.php
 * @param string $str <p>
 * The input string.
 * </p>
 * @return string the soundex key as a string.
 */
function soundex ($str) {}

/**
 * Calculate Levenshtein distance between two strings
 * @link http://www.php.net/manual/en/function.levenshtein.php
 * @param string $str1 <p>
 * One of the strings being evaluated for Levenshtein distance.
 * </p>
 * @param string $str2 <p>
 * One of the strings being evaluated for Levenshtein distance.
 * </p>
 * @return int This function returns the Levenshtein-Distance between the
 * two argument strings or -1, if one of the argument strings
 * is longer than the limit of 255 characters.
 */
function levenshtein ($str1, $str2) {}

/**
 * Return a specific character
 * @link http://www.php.net/manual/en/function.chr.php
 * @param int $ascii <p>
 * The ascii code.
 * </p>
 * @return string the specified character.
 */
function chr ($ascii) {}

/**
 * Return ASCII value of character
 * @link http://www.php.net/manual/en/function.ord.php
 * @param string $string <p>
 * A character.
 * </p>
 * @return int the ASCII value as an integer.
 */
function ord ($string) {}

/**
 * Parses the string into variables
 * @link http://www.php.net/manual/en/function.parse-str.php
 * @param string $str <p>
 * The input string.
 * </p>
 * @param array $arr [optional] <p>
 * If the second parameter arr is present,
 * variables are stored in this variable as array elements instead.
 * </p>
 * @return void 
 */
function parse_str ($str, array &$arr = null) {}

/**
 * Parse a CSV string into an array
 * @link http://www.php.net/manual/en/function.str-getcsv.php
 * @param string $input <p>
 * The string to parse.
 * </p>
 * @param string $delimiter [optional] <p>
 * Set the field delimiter (one character only).
 * </p>
 * @param string $enclosure [optional] <p>
 * Set the field enclosure character (one character only).
 * </p>
 * @param string $escape [optional] <p>
 * Set the escape character (one character only). Defaults as a backslash
 * (\)
 * </p>
 * @return array an indexed array containing the fields read.
 */
function str_getcsv ($input, $delimiter = null, $enclosure = null, $escape = null) {}

/**
 * Pad a string to a certain length with another string
 * @link http://www.php.net/manual/en/function.str-pad.php
 * @param string $input <p>
 * The input string.
 * </p>
 * @param int $pad_length <p>
 * If the value of pad_length is negative,
 * less than, or equal to the length of the input string, no padding
 * takes place.
 * </p>
 * @param string $pad_string [optional] <p>
 * The pad_string may be truncated if the
 * required number of padding characters can't be evenly divided by the
 * pad_string's length.
 * </p>
 * @param int $pad_type [optional] <p>
 * Optional argument pad_type can be
 * STR_PAD_RIGHT, STR_PAD_LEFT,
 * or STR_PAD_BOTH. If
 * pad_type is not specified it is assumed to be
 * STR_PAD_RIGHT.
 * </p>
 * @return string the padded string.
 */
function str_pad ($input, $pad_length, $pad_string = null, $pad_type = null) {}

/**
 * &Alias; <function>rtrim</function>
 * @link http://www.php.net/manual/en/function.chop.php
 * @param $str
 * @param $character_mask [optional]
 */
function chop ($str, $character_mask = null) {}

/**
 * &Alias; <function>strstr</function>
 * @link http://www.php.net/manual/en/function.strchr.php
 * @param $haystack
 * @param $needle
 * @param $part [optional]
 */
function strchr ($haystack, $needle, $part = null) {}

/**
 * Return a formatted string
 * @link http://www.php.net/manual/en/function.sprintf.php
 * @param string $format <p>
 * The format string is composed of zero or more directives:
 * ordinary characters (excluding %) that are
 * copied directly to the result, and conversion
 * specifications, each of which results in fetching its
 * own parameter. This applies to both sprintf
 * and printf.
 * </p>
 * <p>
 * Each conversion specification consists of a percent sign
 * (%), followed by one or more of these
 * elements, in order:
 * An optional sign specifier that forces a sign
 * (- or +) to be used on a number. By default, only the - sign is used
 * on a number if it's negative. This specifier forces positive numbers
 * to have the + sign attached as well, and was added in PHP 4.3.0.
 * @param mixed $args [optional] <p>
 * </p>
 * @param mixed $_ [optional] 
 * @return string a string produced according to the formatting string
 * format.
 */
function sprintf ($format, $args = null, $_ = null) {}

/**
 * Output a formatted string
 * @link http://www.php.net/manual/en/function.printf.php
 * @param string $format <p>
 * See sprintf for a description of
 * format.
 * </p>
 * @param mixed $args [optional] <p>
 * </p>
 * @param mixed $_ [optional] 
 * @return int the length of the outputted string.
 */
function printf ($format, $args = null, $_ = null) {}

/**
 * Output a formatted string
 * @link http://www.php.net/manual/en/function.vprintf.php
 * @param string $format <p>
 * See sprintf for a description of
 * format.
 * </p>
 * @param array $args <p>
 * </p>
 * @return int the length of the outputted string.
 */
function vprintf ($format, array $args) {}

/**
 * Return a formatted string
 * @link http://www.php.net/manual/en/function.vsprintf.php
 * @param string $format <p>
 * See sprintf for a description of
 * format.
 * </p>
 * @param array $args <p>
 * </p>
 * @return string Return array values as a formatted string according to
 * format (which is described in the documentation
 * for sprintf).
 */
function vsprintf ($format, array $args) {}

/**
 * Write a formatted string to a stream
 * @link http://www.php.net/manual/en/function.fprintf.php
 * @param resource $handle &fs.file.pointer;
 * @param string $format <p>
 * See sprintf for a description of 
 * format.
 * </p>
 * @param mixed $args [optional] <p>
 * </p>
 * @param mixed $_ [optional] 
 * @return int the length of the string written.
 */
function fprintf ($handle, $format, $args = null, $_ = null) {}

/**
 * Write a formatted string to a stream
 * @link http://www.php.net/manual/en/function.vfprintf.php
 * @param resource $handle <p>
 * </p>
 * @param string $format <p>
 * See sprintf for a description of
 * format.
 * </p>
 * @param array $args <p>
 * </p>
 * @return int the length of the outputted string.
 */
function vfprintf ($handle, $format, array $args) {}

/**
 * Parses input from a string according to a format
 * @link http://www.php.net/manual/en/function.sscanf.php
 * @param string $str <p>
 * The input string being parsed.
 * </p>
 * @param string $format <p>
 * The interpreted format for str, which is
 * described in the documentation for sprintf with
 * following differences:
 * Function is not locale-aware.
 * F, g, G and
 * b are not supported.
 * D stands for decimal number.
 * i stands for integer with base detection.
 * n stands for number of characters processed so far.
 * </p>
 * @param mixed $_ [optional] 
 * @return mixed If only two parameters were passed to this function, the values parsed will
 * be returned as an array. Otherwise, if optional parameters are passed, the
 * function will return the number of assigned values. The optional parameters
 * must be passed by reference.
 * </p>
 * <p>
 * If there are more substrings expected in the format
 * than there are available within str,
 * -1 will be returned.
 */
function sscanf ($str, $format, &$_ = null) {}

/**
 * Parses input from a file according to a format
 * @link http://www.php.net/manual/en/function.fscanf.php
 * @param resource $handle &fs.file.pointer;
 * @param string $format <p>
 * The specified format as described in the 
 * sprintf documentation.
 * </p>
 * @param mixed $_ [optional] 
 * @return mixed If only two parameters were passed to this function, the values parsed will be
 * returned as an array. Otherwise, if optional parameters are passed, the
 * function will return the number of assigned values. The optional
 * parameters must be passed by reference.
 */
function fscanf ($handle, $format, &$_ = null) {}

/**
 * Parse a URL and return its components
 * @link http://www.php.net/manual/en/function.parse-url.php
 * @param string $url <p>
 * The URL to parse. Invalid characters are replaced by
 * _.
 * </p>
 * @param int $component [optional] <p>
 * Specify one of PHP_URL_SCHEME,
 * PHP_URL_HOST, PHP_URL_PORT,
 * PHP_URL_USER, PHP_URL_PASS,
 * PHP_URL_PATH, PHP_URL_QUERY
 * or PHP_URL_FRAGMENT to retrieve just a specific
 * URL component as a string (except when
 * PHP_URL_PORT is given, in which case the return
 * value will be an integer).
 * </p>
 * @return mixed On seriously malformed URLs, parse_url may return
 * false.
 * </p>
 * <p>
 * If the component parameter is omitted, an
 * associative array is returned. At least one element will be
 * present within the array. Potential keys within this array are:
 * scheme - e.g. http
 * host 
 * port
 * user
 * pass
 * path
 * query - after the question mark ?
 * fragment - after the hashmark #
 * </p>
 * <p>
 * If the component parameter is specified,
 * parse_url returns a string (or an
 * integer, in the case of PHP_URL_PORT)
 * instead of an array. If the requested component doesn't exist
 * within the given URL, &null; will be returned.
 */
function parse_url ($url, $component = null) {}

/**
 * URL-encodes string
 * @link http://www.php.net/manual/en/function.urlencode.php
 * @param string $str <p>
 * The string to be encoded.
 * </p>
 * @return string a string in which all non-alphanumeric characters except
 * -_. have been replaced with a percent
 * (%) sign followed by two hex digits and spaces encoded
 * as plus (+) signs. It is encoded the same way that the
 * posted data from a WWW form is encoded, that is the same way as in
 * application/x-www-form-urlencoded media type. This
 * differs from the RFC 3986 encoding (see
 * rawurlencode) in that for historical reasons, spaces
 * are encoded as plus (+) signs.
 */
function urlencode ($str) {}

/**
 * Decodes URL-encoded string
 * @link http://www.php.net/manual/en/function.urldecode.php
 * @param string $str <p>
 * The string to be decoded.
 * </p>
 * @return string the decoded string.
 */
function urldecode ($str) {}

/**
 * URL-encode according to RFC 3986
 * @link http://www.php.net/manual/en/function.rawurlencode.php
 * @param string $str <p>
 * The URL to be encoded.
 * </p>
 * @return string a string in which all non-alphanumeric characters except
 * -_.~ have been replaced with a percent
 * (%) sign followed by two hex digits. This is the
 * encoding described in RFC 3986 for
 * protecting literal characters from being interpreted as special URL
 * delimiters, and for protecting URLs from being mangled by transmission
 * media with character conversions (like some email systems).
 * <p>
 * Prior to PHP 5.3.0, rawurlencode encoded tildes (~) as per
 * RFC 1738.
 * </p>
 */
function rawurlencode ($str) {}

/**
 * Decode URL-encoded strings
 * @link http://www.php.net/manual/en/function.rawurldecode.php
 * @param string $str <p>
 * The URL to be decoded.
 * </p>
 * @return string the decoded URL, as a string.
 */
function rawurldecode ($str) {}

/**
 * Generate URL-encoded query string
 * @link http://www.php.net/manual/en/function.http-build-query.php
 * @param mixed $query_data <p>
 * May be an array or object containing properties.
 * </p>
 * <p>
 * If query_data is an array, it may be a simple
 * one-dimensional structure, or an array of arrays (which in
 * turn may contain other arrays).
 * </p>
 * <p>
 * If query_data is an object, then only public
 * properties will be incorporated into the result.
 * </p>
 * @param string $numeric_prefix [optional] <p>
 * If numeric indices are used in the base array and this parameter is
 * provided, it will be prepended to the numeric index for elements in
 * the base array only.
 * </p>
 * <p>
 * This is meant to allow for legal variable names when the data is
 * decoded by PHP or another CGI application later on.
 * </p>
 * @param string $arg_separator [optional] <p>
 * arg_separator.output
 * is used to separate arguments, unless this parameter is specified,
 * and is then used.
 * </p>
 * @param int $enc_type [optional] <p>
 * By default, PHP_QUERY_RFC1738.
 * </p>
 * <p>
 * If enc_type is
 * PHP_QUERY_RFC1738, then encoding is performed per
 * RFC 1738 and the
 * application/x-www-form-urlencoded media type, which
 * implies that spaces are encoded as plus (+) signs.
 * </p>
 * <p>
 * If enc_type is
 * PHP_QUERY_RFC3986, then encoding is performed
 * according to RFC 3986, and
 * spaces will be percent encoded (%20).
 * </p>
 * @return string a URL-encoded string.
 */
function http_build_query ($query_data, $numeric_prefix = null, $arg_separator = null, $enc_type = null) {}

/**
 * Returns the target of a symbolic link
 * @link http://www.php.net/manual/en/function.readlink.php
 * @param string $path <p>
 * The symbolic link path.
 * </p>
 * @return string the contents of the symbolic link path or false on error.
 */
function readlink ($path) {}

/**
 * Gets information about a link
 * @link http://www.php.net/manual/en/function.linkinfo.php
 * @param string $path <p>
 * Path to the link.
 * </p>
 * @return int linkinfo returns the st_dev field
 * of the Unix C stat structure returned by the lstat
 * system call. Returns 0 or false in case of error.
 */
function linkinfo ($path) {}

/**
 * Creates a symbolic link
 * @link http://www.php.net/manual/en/function.symlink.php
 * @param string $target <p>
 * Target of the link.
 * </p>
 * @param string $link <p>
 * The link name.
 * </p>
 * @return bool true on success or false on failure
 */
function symlink ($target, $link) {}

/**
 * Create a hard link
 * @link http://www.php.net/manual/en/function.link.php
 * @param string $target <p>
 * Target of the link.
 * </p>
 * @param string $link <p>
 * The link name.
 * </p>
 * @return bool true on success or false on failure
 */
function link ($target, $link) {}

/**
 * Deletes a file
 * @link http://www.php.net/manual/en/function.unlink.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @param resource $context [optional] &note.context-support;
 * @return bool true on success or false on failure
 */
function unlink ($filename, $context = null) {}

/**
 * Execute an external program
 * @link http://www.php.net/manual/en/function.exec.php
 * @param string $command <p>
 * The command that will be executed.
 * </p>
 * @param array $output [optional] <p>
 * If the output argument is present, then the
 * specified array will be filled with every line of output from the
 * command. Trailing whitespace, such as \n, is not
 * included in this array. Note that if the array already contains some
 * elements, exec will append to the end of the array.
 * If you do not want the function to append elements, call
 * unset on the array before passing it to
 * exec.
 * </p>
 * @param int $return_var [optional] <p>
 * If the return_var argument is present
 * along with the output argument, then the
 * return status of the executed command will be written to this
 * variable.
 * </p>
 * @return string The last line from the result of the command. If you need to execute a 
 * command and have all the data from the command passed directly back without 
 * any interference, use the passthru function.
 * </p>
 * <p>
 * To get the output of the executed command, be sure to set and use the
 * output parameter.
 */
function exec ($command, array &$output = null, &$return_var = null) {}

/**
 * Execute an external program and display the output
 * @link http://www.php.net/manual/en/function.system.php
 * @param string $command <p>
 * The command that will be executed.
 * </p>
 * @param int $return_var [optional] <p>
 * If the return_var argument is present, then the
 * return status of the executed command will be written to this
 * variable.
 * </p>
 * @return string the last line of the command output on success, and false
 * on failure.
 */
function system ($command, &$return_var = null) {}

/**
 * Escape shell metacharacters
 * @link http://www.php.net/manual/en/function.escapeshellcmd.php
 * @param string $command <p>
 * The command that will be escaped.
 * </p>
 * @return string The escaped string.
 */
function escapeshellcmd ($command) {}

/**
 * Escape a string to be used as a shell argument
 * @link http://www.php.net/manual/en/function.escapeshellarg.php
 * @param string $arg <p>
 * The argument that will be escaped.
 * </p>
 * @return string The escaped string.
 */
function escapeshellarg ($arg) {}

/**
 * Execute an external program and display raw output
 * @link http://www.php.net/manual/en/function.passthru.php
 * @param string $command <p>
 * The command that will be executed.
 * </p>
 * @param int $return_var [optional] <p>
 * If the return_var argument is present, the 
 * return status of the Unix command will be placed here.
 * </p>
 * @return void 
 */
function passthru ($command, &$return_var = null) {}

/**
 * Execute command via shell and return the complete output as a string
 * @link http://www.php.net/manual/en/function.shell-exec.php
 * @param string $cmd <p>
 * The command that will be executed.
 * </p>
 * @return string The output from the executed command or &null; if an error occurred or the
 * command produces no output.
 * </p>
 * <p>
 * This function can return &null; both when an error occurs or the program
 * produces no output. It is not possible to detect execution failures using
 * this function. exec should be used when access to the
 * program exit code is required.
 */
function shell_exec ($cmd) {}

/**
 * Execute a command and open file pointers for input/output
 * @link http://www.php.net/manual/en/function.proc-open.php
 * @param string $cmd <p>
 * The command to execute
 * </p>
 * @param array $descriptorspec <p>
 * An indexed array where the key represents the descriptor number and the
 * value represents how PHP will pass that descriptor to the child
 * process. 0 is stdin, 1 is stdout, while 2 is stderr.
 * </p>
 * <p>
 * Each element can be:
 * An array describing the pipe to pass to the process. The first
 * element is the descriptor type and the second element is an option for
 * the given type. Valid types are pipe (the second
 * element is either r to pass the read end of the pipe
 * to the process, or w to pass the write end) and
 * file (the second element is a filename).
 * A stream resource representing a real file descriptor (e.g. opened file,
 * a socket, STDIN).
 * </p>
 * <p>
 * The file descriptor numbers are not limited to 0, 1 and 2 - you may
 * specify any valid file descriptor number and it will be passed to the
 * child process. This allows your script to interoperate with other
 * scripts that run as "co-processes". In particular, this is useful for
 * passing passphrases to programs like PGP, GPG and openssl in a more
 * secure manner. It is also useful for reading status information
 * provided by those programs on auxiliary file descriptors.
 * </p>
 * @param array $pipes <p>
 * Will be set to an indexed array of file pointers that correspond to
 * PHP's end of any pipes that are created.
 * </p>
 * @param string $cwd [optional] <p>
 * The initial working dir for the command. This must be an
 * absolute directory path, or &null;
 * if you want to use the default value (the working dir of the current
 * PHP process)
 * </p>
 * @param array $env [optional] <p>
 * An array with the environment variables for the command that will be
 * run, or &null; to use the same environment as the current PHP process
 * </p>
 * @param array $other_options [optional] <p>
 * Allows you to specify additional options. Currently supported options
 * include:
 * suppress_errors (windows only): suppresses errors
 * generated by this function when it's set to true
 * bypass_shell (windows only): bypass
 * cmd.exe shell when set to true
 * </p>
 * @return resource a resource representing the process, which should be freed using
 * proc_close when you are finished with it. On failure
 * returns false.
 */
function proc_open ($cmd, array $descriptorspec, array &$pipes, $cwd = null, array $env = null, array $other_options = null) {}

/**
 * Close a process opened by <function>proc_open</function> and return the exit code of that process
 * @link http://www.php.net/manual/en/function.proc-close.php
 * @param resource $process <p>
 * The proc_open resource that will
 * be closed.
 * </p>
 * @return int the termination status of the process that was run. In case of 
 * an error then -1 is returned.
 */
function proc_close ($process) {}

/**
 * Kills a process opened by proc_open
 * @link http://www.php.net/manual/en/function.proc-terminate.php
 * @param resource $process <p>
 * The proc_open resource that will
 * be closed.
 * </p>
 * @param int $signal [optional] <p>
 * This optional parameter is only useful on POSIX
 * operating systems; you may specify a signal to send to the process
 * using the kill(2) system call. The default is
 * SIGTERM.
 * </p>
 * @return bool the termination status of the process that was run.
 */
function proc_terminate ($process, $signal = null) {}

/**
 * Get information about a process opened by <function>proc_open</function>
 * @link http://www.php.net/manual/en/function.proc-get-status.php
 * @param resource $process <p>
 * The proc_open resource that will
 * be evaluated.
 * </p>
 * @return array An array of collected information on success, and false
 * on failure. The returned array contains the following elements:
 * </p>
 * <p>
 * <tr valign="top"><td>element</td><td>type</td><td>description</td></tr>
 * <tr valign="top">
 * <td>command</td>
 * <td>string</td>
 * <td>
 * The command string that was passed to proc_open.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>pid</td>
 * <td>int</td>
 * <td>process id</td>
 * </tr>
 * <tr valign="top">
 * <td>running</td>
 * <td>bool</td>
 * <td>
 * true if the process is still running, false if it has
 * terminated.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>signaled</td>
 * <td>bool</td>
 * <td>
 * true if the child process has been terminated by
 * an uncaught signal. Always set to false on Windows.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>stopped</td>
 * <td>bool</td>
 * <td>
 * true if the child process has been stopped by a
 * signal. Always set to false on Windows.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>exitcode</td>
 * <td>int</td>
 * <td>
 * The exit code returned by the process (which is only
 * meaningful if running is false).
 * Only first call of this function return real value, next calls return
 * -1.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>termsig</td>
 * <td>int</td>
 * <td>
 * The number of the signal that caused the child process to terminate
 * its execution (only meaningful if signaled is true).
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>stopsig</td>
 * <td>int</td>
 * <td>
 * The number of the signal that caused the child process to stop its
 * execution (only meaningful if stopped is true).
 * </td>
 * </tr>
 */
function proc_get_status ($process) {}

/**
 * Change the priority of the current process
 * @link http://www.php.net/manual/en/function.proc-nice.php
 * @param int $increment <p>
 * The increment value of the priority change.
 * </p>
 * @return bool true on success or false on failure
 * If an error occurs, like the user lacks permission to change the priority, 
 * an error of level E_WARNING is also generated.
 */
function proc_nice ($increment) {}

/**
 * Generate a random integer
 * @link http://www.php.net/manual/en/function.rand.php
 * @param $min [optional]
 * @param $max [optional]
 * @return int A pseudo random value between min
 * (or 0) and max (or getrandmax, inclusive).
 */
function rand ($min = null, $max = null) {}

/**
 * Seed the random number generator
 * @link http://www.php.net/manual/en/function.srand.php
 * @param int $seed [optional] <p>
 * Optional seed value
 * </p>
 * @return void 
 */
function srand ($seed = null) {}

/**
 * Show largest possible random value
 * @link http://www.php.net/manual/en/function.getrandmax.php
 * @return int The largest possible random value returned by rand
 */
function getrandmax () {}

/**
 * Generate a better random value
 * @link http://www.php.net/manual/en/function.mt-rand.php
 * @param $min [optional]
 * @param $max [optional]
 * @return int A random integer value between min (or 0)
 * and max (or mt_getrandmax, inclusive),
 * or false if max is less than min.
 */
function mt_rand ($min = null, $max = null) {}

/**
 * Seed the better random number generator
 * @link http://www.php.net/manual/en/function.mt-srand.php
 * @param int $seed [optional] <p>
 * An optional seed value
 * </p>
 * @return void 
 */
function mt_srand ($seed = null) {}

/**
 * Show largest possible random value
 * @link http://www.php.net/manual/en/function.mt-getrandmax.php
 * @return int the maximum random value returned by a call to
 * mt_rand without arguments, which is the maximum value 
 * that can be used for its max parameter without the
 * result being scaled up (and therefore less random).
 */
function mt_getrandmax () {}

/**
 * Generates cryptographically secure pseudo-random bytes
 * @link http://www.php.net/manual/en/function.random-bytes.php
 * @param $length [optional]
 */
function random_bytes ($length = null) {}

/**
 * Generates cryptographically secure pseudo-random integers
 * @link http://www.php.net/manual/en/function.random-int.php
 * @param $min [optional]
 * @param $max [optional]
 */
function random_int ($min = null, $max = null) {}

/**
 * Get port number associated with an Internet service and protocol
 * @link http://www.php.net/manual/en/function.getservbyname.php
 * @param string $service <p>
 * The Internet service name, as a string.
 * </p>
 * @param string $protocol <p>
 * protocol is either "tcp"
 * or "udp" (in lowercase).
 * </p>
 * @return int the port number, or false if service or
 * protocol is not found.
 */
function getservbyname ($service, $protocol) {}

/**
 * Get Internet service which corresponds to port and protocol
 * @link http://www.php.net/manual/en/function.getservbyport.php
 * @param int $port <p>
 * The port number.
 * </p>
 * @param string $protocol <p>
 * protocol is either "tcp"
 * or "udp" (in lowercase).
 * </p>
 * @return string the Internet service name as a string.
 */
function getservbyport ($port, $protocol) {}

/**
 * Get protocol number associated with protocol name
 * @link http://www.php.net/manual/en/function.getprotobyname.php
 * @param string $name <p>
 * The protocol name.
 * </p>
 * @return int the protocol number, or false on failure.
 */
function getprotobyname ($name) {}

/**
 * Get protocol name associated with protocol number
 * @link http://www.php.net/manual/en/function.getprotobynumber.php
 * @param int $number <p>
 * The protocol number.
 * </p>
 * @return string the protocol name as a string, or false on failure.
 */
function getprotobynumber ($number) {}

/**
 * Gets PHP script owner's UID
 * @link http://www.php.net/manual/en/function.getmyuid.php
 * @return int the user ID of the current script, or false on error.
 */
function getmyuid () {}

/**
 * Get PHP script owner's GID
 * @link http://www.php.net/manual/en/function.getmygid.php
 * @return int the group ID of the current script, or false on error.
 */
function getmygid () {}

/**
 * Gets PHP's process ID
 * @link http://www.php.net/manual/en/function.getmypid.php
 * @return int the current PHP process ID, or false on error.
 */
function getmypid () {}

/**
 * Gets the inode of the current script
 * @link http://www.php.net/manual/en/function.getmyinode.php
 * @return int the current script's inode as an integer, or false on error.
 */
function getmyinode () {}

/**
 * Gets time of last page modification
 * @link http://www.php.net/manual/en/function.getlastmod.php
 * @return int the time of the last modification of the current
 * page. The value returned is a Unix timestamp, suitable for
 * feeding to date. Returns false on error.
 */
function getlastmod () {}

/**
 * Decodes data encoded with MIME base64
 * @link http://www.php.net/manual/en/function.base64-decode.php
 * @param string $data <p>
 * The encoded data.
 * </p>
 * @param bool $strict [optional] <p>
 * Returns false if input contains character from outside the base64
 * alphabet.
 * </p>
 * @return string the original data or false on failure. The returned data may be
 * binary.
 */
function base64_decode ($data, $strict = null) {}

/**
 * Encodes data with MIME base64
 * @link http://www.php.net/manual/en/function.base64-encode.php
 * @param string $data <p>
 * The data to encode.
 * </p>
 * @return string The encoded data, as a string or false on failure.
 */
function base64_encode ($data) {}

/**
 * Creates a password hash
 * @link http://www.php.net/manual/en/function.password-hash.php
 * @param string $password <p>
 * The user&apos;s password.
 * </p>
 * <p>
 * Using the PASSWORD_BCRYPT for the
 * algo parameter, will result
 * in the password parameter being truncated to a
 * maximum length of 72 characters.
 * </p>
 * @param integer $algo <p>
 * A password algorithm constant denoting the algorithm to use when hashing the password.
 * </p>
 * @param array $options [optional] <p>
 * An associative array containing options. See the password algorithm constants for documentation on the supported options for each algorithm.
 * </p>
 * <p>
 * If omitted, a random salt will be created and the default cost will be
 * used.
 * </p>
 * @return string the hashed password, or false on failure.
 * </p>
 * <p>
 * The used algorithm, cost and salt are returned as part of the hash. Therefore,
 * all information that's needed to verify the hash is included in it. This allows
 * the password_verify function to verify the hash without
 * needing separate storage for the salt or algorithm information.
 */
function password_hash ($password, $algo, array $options = null) {}

/**
 * Returns information about the given hash
 * @link http://www.php.net/manual/en/function.password-get-info.php
 * @param string $hash <p>
 * A hash created by password_hash.
 * </p>
 * @return array an associative array with three elements: 
 * algo, which will match a
 * password algorithm constant
 * algoName, which has the human readable name of the
 * algorithm
 * options, which includes the options
 * provided when calling password_hash
 */
function password_get_info ($hash) {}

/**
 * Checks if the given hash matches the given options
 * @link http://www.php.net/manual/en/function.password-needs-rehash.php
 * @param string $hash <p>
 * A hash created by password_hash.
 * </p>
 * @param integer $algo <p>
 * A password algorithm constant denoting the algorithm to use when hashing the password.
 * </p>
 * @param array $options [optional] <p>
 * An associative array containing options. See the password algorithm constants for documentation on the supported options for each algorithm.
 * </p>
 * @return boolean true if the hash should be rehashed to match the given
 * algo and options, or false
 * otherwise.
 */
function password_needs_rehash ($hash, $algo, array $options = null) {}

/**
 * Verifies that a password matches a hash
 * @link http://www.php.net/manual/en/function.password-verify.php
 * @param string $password <p>
 * The user&apos;s password.
 * </p>
 * @param string $hash <p>
 * A hash created by password_hash.
 * </p>
 * @return boolean true if the password and hash match, or false otherwise.
 */
function password_verify ($password, $hash) {}

/**
 * Uuencode a string
 * @link http://www.php.net/manual/en/function.convert-uuencode.php
 * @param string $data <p>
 * The data to be encoded.
 * </p>
 * @return string the uuencoded data or false on failure.
 */
function convert_uuencode ($data) {}

/**
 * Decode a uuencoded string
 * @link http://www.php.net/manual/en/function.convert-uudecode.php
 * @param string $data <p>
 * The uuencoded data.
 * </p>
 * @return string the decoded data as a string or false on failure.
 */
function convert_uudecode ($data) {}

/**
 * Absolute value
 * @link http://www.php.net/manual/en/function.abs.php
 * @param mixed $number <p>
 * The numeric value to process
 * </p>
 * @return number The absolute value of number. If the
 * argument number is
 * of type float, the return type is also float,
 * otherwise it is integer (as float usually has a
 * bigger value range than integer).
 */
function abs ($number) {}

/**
 * Round fractions up
 * @link http://www.php.net/manual/en/function.ceil.php
 * @param float $value <p>
 * The value to round
 * </p>
 * @return float value rounded up to the next highest
 * integer.
 * The return value of ceil is still of type
 * float as the value range of float is 
 * usually bigger than that of integer.
 */
function ceil ($value) {}

/**
 * Round fractions down
 * @link http://www.php.net/manual/en/function.floor.php
 * @param float $value <p>
 * The numeric value to round
 * </p>
 * @return float value rounded to the next lowest integer.
 * The return value of floor is still of type
 * float because the value range of float is 
 * usually bigger than that of integer.
 */
function floor ($value) {}

/**
 * Rounds a float
 * @link http://www.php.net/manual/en/function.round.php
 * @param float $val <p>
 * The value to round
 * </p>
 * @param int $precision [optional] <p>
 * The optional number of decimal digits to round to.
 * </p>
 * @param int $mode [optional] <p>
 * Use one of the following constants to specify the mode in which rounding occurs.
 * <tr valign="top">
 * <td>Constant</td>
 * <td>&Description;</td>
 * </tr>
 * <tr valign="top">
 * <td>PHP_ROUND_HALF_UP</td>
 * <td>
 * Round val up to precision decimal places
 * away from zero, when it is half way there. Making 1.5 into 2 and -1.5 into -2.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>PHP_ROUND_HALF_DOWN</td>
 * <td>
 * Round val down to precision decimal places
 * towards zero, when it is half way there. Making 1.5 into 1 and -1.5 into -1.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>PHP_ROUND_HALF_EVEN</td>
 * <td>
 * Round val to precision decimal places
 * towards the next even value.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>PHP_ROUND_HALF_ODD</td>
 * <td>
 * Round val to precision decimal places
 * towards the next odd value. 
 * </td>
 * </tr>
 * </p>
 * @return float The rounded value
 */
function round ($val, $precision = null, $mode = null) {}

/**
 * Sine
 * @link http://www.php.net/manual/en/function.sin.php
 * @param float $arg <p>
 * A value in radians
 * </p>
 * @return float The sine of arg
 */
function sin ($arg) {}

/**
 * Cosine
 * @link http://www.php.net/manual/en/function.cos.php
 * @param float $arg <p>
 * An angle in radians 
 * </p>
 * @return float The cosine of arg
 */
function cos ($arg) {}

/**
 * Tangent
 * @link http://www.php.net/manual/en/function.tan.php
 * @param float $arg <p>
 * The argument to process in radians 
 * </p>
 * @return float The tangent of arg
 */
function tan ($arg) {}

/**
 * Arc sine
 * @link http://www.php.net/manual/en/function.asin.php
 * @param float $arg <p>
 * The argument to process
 * </p>
 * @return float The arc sine of arg in radians
 */
function asin ($arg) {}

/**
 * Arc cosine
 * @link http://www.php.net/manual/en/function.acos.php
 * @param float $arg <p>
 * The argument to process
 * </p>
 * @return float The arc cosine of arg in radians.
 */
function acos ($arg) {}

/**
 * Arc tangent
 * @link http://www.php.net/manual/en/function.atan.php
 * @param float $arg <p>
 * The argument to process
 * </p>
 * @return float The arc tangent of arg in radians.
 */
function atan ($arg) {}

/**
 * Inverse hyperbolic tangent
 * @link http://www.php.net/manual/en/function.atanh.php
 * @param float $arg <p>
 * The argument to process
 * </p>
 * @return float Inverse hyperbolic tangent of arg
 */
function atanh ($arg) {}

/**
 * Arc tangent of two variables
 * @link http://www.php.net/manual/en/function.atan2.php
 * @param float $y <p>
 * Dividend parameter
 * </p>
 * @param float $x <p>
 * Divisor parameter
 * </p>
 * @return float The arc tangent of y/x 
 * in radians.
 */
function atan2 ($y, $x) {}

/**
 * Hyperbolic sine
 * @link http://www.php.net/manual/en/function.sinh.php
 * @param float $arg <p>
 * The argument to process
 * </p>
 * @return float The hyperbolic sine of arg
 */
function sinh ($arg) {}

/**
 * Hyperbolic cosine
 * @link http://www.php.net/manual/en/function.cosh.php
 * @param float $arg <p>
 * The argument to process
 * </p>
 * @return float The hyperbolic cosine of arg
 */
function cosh ($arg) {}

/**
 * Hyperbolic tangent
 * @link http://www.php.net/manual/en/function.tanh.php
 * @param float $arg <p>
 * The argument to process
 * </p>
 * @return float The hyperbolic tangent of arg
 */
function tanh ($arg) {}

/**
 * Inverse hyperbolic sine
 * @link http://www.php.net/manual/en/function.asinh.php
 * @param float $arg <p>
 * The argument to process
 * </p>
 * @return float The inverse hyperbolic sine of arg
 */
function asinh ($arg) {}

/**
 * Inverse hyperbolic cosine
 * @link http://www.php.net/manual/en/function.acosh.php
 * @param float $arg <p>
 * The value to process
 * </p>
 * @return float The inverse hyperbolic cosine of arg
 */
function acosh ($arg) {}

/**
 * Returns exp(number) - 1, computed in a way that is accurate even
   when the value of number is close to zero
 * @link http://www.php.net/manual/en/function.expm1.php
 * @param float $arg <p>
 * The argument to process
 * </p>
 * @return float 'e' to the power of arg minus one
 */
function expm1 ($arg) {}

/**
 * Returns log(1 + number), computed in a way that is accurate even when
   the value of number is close to zero
 * @link http://www.php.net/manual/en/function.log1p.php
 * @param float $number <p>
 * The argument to process
 * </p>
 * @return float log(1 + number)
 */
function log1p ($number) {}

/**
 * Get value of pi
 * @link http://www.php.net/manual/en/function.pi.php
 * @return float The value of pi as float.
 */
function pi () {}

/**
 * Finds whether a value is a legal finite number
 * @link http://www.php.net/manual/en/function.is-finite.php
 * @param float $val <p>
 * The value to check
 * </p>
 * @return bool true if val is a legal finite
 * number within the allowed range for a PHP float on this platform,
 * else false.
 */
function is_finite ($val) {}

/**
 * Finds whether a value is not a number
 * @link http://www.php.net/manual/en/function.is-nan.php
 * @param float $val <p>
 * The value to check
 * </p>
 * @return bool true if val is 'not a number',
 * else false.
 */
function is_nan ($val) {}

/**
 * Finds whether a value is infinite
 * @link http://www.php.net/manual/en/function.is-infinite.php
 * @param float $val <p>
 * The value to check
 * </p>
 * @return bool true if val is infinite, else false.
 */
function is_infinite ($val) {}

/**
 * Exponential expression
 * @link http://www.php.net/manual/en/function.pow.php
 * @param number $base <p>
 * The base to use
 * </p>
 * @param number $exp <p>
 * The exponent
 * </p>
 * @return number base raised to the power of exp.
 * If both arguments are non-negative integers and the result can be represented
 * as an integer, the result will be returned with integer type,
 * otherwise it will be returned as a float.
 */
function pow ($base, $exp) {}

/**
 * Calculates the exponent of <constant>e</constant>
 * @link http://www.php.net/manual/en/function.exp.php
 * @param float $arg <p>
 * The argument to process
 * </p>
 * @return float 'e' raised to the power of arg
 */
function exp ($arg) {}

/**
 * Natural logarithm
 * @link http://www.php.net/manual/en/function.log.php
 * @param float $arg <p>
 * The value to calculate the logarithm for
 * </p>
 * @param float $base [optional] <p>
 * The optional logarithmic base to use 
 * (defaults to 'e' and so to the natural logarithm).
 * </p>
 * @return float The logarithm of arg to 
 * base, if given, or the
 * natural logarithm.
 */
function log ($arg, $base = null) {}

/**
 * Base-10 logarithm
 * @link http://www.php.net/manual/en/function.log10.php
 * @param float $arg <p>
 * The argument to process
 * </p>
 * @return float The base-10 logarithm of arg
 */
function log10 ($arg) {}

/**
 * Square root
 * @link http://www.php.net/manual/en/function.sqrt.php
 * @param float $arg <p>
 * The argument to process
 * </p>
 * @return float The square root of arg
 * or the special value NAN for negative numbers.
 */
function sqrt ($arg) {}

/**
 * Calculate the length of the hypotenuse of a right-angle triangle
 * @link http://www.php.net/manual/en/function.hypot.php
 * @param float $x <p>
 * Length of first side
 * </p>
 * @param float $y <p>
 * Length of second side
 * </p>
 * @return float Calculated length of the hypotenuse
 */
function hypot ($x, $y) {}

/**
 * Converts the number in degrees to the radian equivalent
 * @link http://www.php.net/manual/en/function.deg2rad.php
 * @param float $number <p>
 * Angular value in degrees 
 * </p>
 * @return float The radian equivalent of number
 */
function deg2rad ($number) {}

/**
 * Converts the radian number to the equivalent number in degrees
 * @link http://www.php.net/manual/en/function.rad2deg.php
 * @param float $number <p>
 * A radian value
 * </p>
 * @return float The equivalent of number in degrees
 */
function rad2deg ($number) {}

/**
 * Binary to decimal
 * @link http://www.php.net/manual/en/function.bindec.php
 * @param string $binary_string <p>
 * The binary string to convert
 * </p>
 * @return number The decimal value of binary_string
 */
function bindec ($binary_string) {}

/**
 * Hexadecimal to decimal
 * @link http://www.php.net/manual/en/function.hexdec.php
 * @param string $hex_string <p>
 * The hexadecimal string to convert
 * </p>
 * @return number The decimal representation of hex_string
 */
function hexdec ($hex_string) {}

/**
 * Octal to decimal
 * @link http://www.php.net/manual/en/function.octdec.php
 * @param string $octal_string <p>
 * The octal string to convert
 * </p>
 * @return number The decimal representation of octal_string
 */
function octdec ($octal_string) {}

/**
 * Decimal to binary
 * @link http://www.php.net/manual/en/function.decbin.php
 * @param int $number <p>
 * Decimal value to convert
 * </p>
 * <table>
 * Range of inputs on 32-bit machines
 * <tr valign="top">
 * <td>positive number</td>
 * <td>negative number</td>
 * <td>return value</td>
 * </tr>
 * <tr valign="top">
 * <td>0</td>
 * <td></td>
 * <td>0</td>
 * </tr>
 * <tr valign="top">
 * <td>1</td>
 * <td></td>
 * <td>1</td>
 * </tr>
 * <tr valign="top">
 * <td>2</td>
 * <td></td>
 * <td>10</td>
 * </tr>
 * <tr valign="top">
 * ... normal progression ...</td>
 * </tr>
 * <tr valign="top">
 * <td>2147483646</td>
 * <td></td>
 * <td>1111111111111111111111111111110</td>
 * </tr>
 * <tr valign="top">
 * <td>2147483647 (largest signed integer)</td>
 * <td></td>
 * <td>1111111111111111111111111111111 (31 1's)</td>
 * </tr>
 * <tr valign="top">
 * <td>2147483648</td>
 * <td>-2147483648</td>
 * <td>10000000000000000000000000000000</td>
 * </tr>
 * <tr valign="top">
 * ... normal progression ...</td>
 * </tr>
 * <tr valign="top">
 * <td>4294967294</td>
 * <td>-2</td>
 * <td>11111111111111111111111111111110</td>
 * </tr>
 * <tr valign="top">
 * <td>4294967295 (largest unsigned integer)</td>
 * <td>-1</td>
 * <td>11111111111111111111111111111111 (32 1's)</td>
 * </tr>
 * </table>
 * <table>
 * Range of inputs on 64-bit machines
 * <tr valign="top">
 * <td>positive number</td>
 * <td>negative number</td>
 * <td>return value</td>
 * </tr>
 * <tr valign="top">
 * <td>0</td>
 * <td></td>
 * <td>0</td>
 * </tr>
 * <tr valign="top">
 * <td>1</td>
 * <td></td>
 * <td>1</td>
 * </tr>
 * <tr valign="top">
 * <td>2</td>
 * <td></td>
 * <td>10</td>
 * </tr>
 * <tr valign="top">
 * ... normal progression ...</td>
 * </tr>
 * <tr valign="top">
 * <td>9223372036854775806</td>
 * <td></td>
 * <td>111111111111111111111111111111111111111111111111111111111111110</td>
 * </tr>
 * <tr valign="top">
 * <td>9223372036854775807 (largest signed integer)</td>
 * <td></td>
 * <td>111111111111111111111111111111111111111111111111111111111111111 (63 1's)</td>
 * </tr>
 * <tr valign="top">
 * <td></td>
 * <td>-9223372036854775808</td>
 * <td>1000000000000000000000000000000000000000000000000000000000000000</td>
 * </tr>
 * <tr valign="top">
 * ... normal progression ...</td>
 * </tr>
 * <tr valign="top">
 * <td></td>
 * <td>-2</td>
 * <td>1111111111111111111111111111111111111111111111111111111111111110</td>
 * </tr>
 * <tr valign="top">
 * <td></td>
 * <td>-1</td>
 * <td>1111111111111111111111111111111111111111111111111111111111111111 (64 1's)</td>
 * </tr>
 * </table>
 * @return string Binary string representation of number
 */
function decbin ($number) {}

/**
 * Decimal to octal
 * @link http://www.php.net/manual/en/function.decoct.php
 * @param int $number <p>
 * Decimal value to convert
 * </p>
 * @return string Octal string representation of number
 */
function decoct ($number) {}

/**
 * Decimal to hexadecimal
 * @link http://www.php.net/manual/en/function.dechex.php
 * @param int $number <p>
 * The decimal value to convert.
 * </p>
 * <p>
 * As PHP's integer type is signed, but
 * dechex deals with unsigned integers, negative
 * integers will be treated as though they were unsigned.
 * </p>
 * @return string Hexadecimal string representation of number.
 */
function dechex ($number) {}

/**
 * Convert a number between arbitrary bases
 * @link http://www.php.net/manual/en/function.base-convert.php
 * @param string $number <p>
 * The number to convert
 * </p>
 * @param int $frombase <p>
 * The base number is in
 * </p>
 * @param int $tobase <p>
 * The base to convert number to
 * </p>
 * @return string number converted to base tobase
 */
function base_convert ($number, $frombase, $tobase) {}

/**
 * Format a number with grouped thousands
 * @link http://www.php.net/manual/en/function.number-format.php
 * @param float $number <p>
 * The number being formatted.
 * </p>
 * @param int $decimals [optional] <p>
 * Sets the number of decimal points.
 * </p>
 * @return string A formatted version of number.
 */
function number_format ($number, $decimals = null) {}

/**
 * Returns the floating point remainder (modulo) of the division
  of the arguments
 * @link http://www.php.net/manual/en/function.fmod.php
 * @param float $x <p>
 * The dividend
 * </p>
 * @param float $y <p>
 * The divisor
 * </p>
 * @return float The floating point remainder of 
 * x/y
 */
function fmod ($x, $y) {}

/**
 * Integer division
 * @link http://www.php.net/manual/en/function.intdiv.php
 * @param int $numerator <p>
 * Number to be divided.
 * </p>
 * @param int $divisor <p>
 * Number which divides the numerator.
 * </p>
 * @return int The integer division of numerator by divisor.
 */
function intdiv ($numerator, $divisor) {}

/**
 * Converts a packed internet address to a human readable representation
 * @link http://www.php.net/manual/en/function.inet-ntop.php
 * @param string $in_addr <p>
 * A 32bit IPv4, or 128bit IPv6 address.
 * </p>
 * @return string a string representation of the address or false on failure.
 */
function inet_ntop ($in_addr) {}

/**
 * Converts a human readable IP address to its packed in_addr representation
 * @link http://www.php.net/manual/en/function.inet-pton.php
 * @param string $address <p>
 * A human readable IPv4 or IPv6 address.
 * </p>
 * @return string the in_addr representation of the given
 * address, or false if a syntactically invalid
 * address is given (for example, an IPv4 address
 * without dots or an IPv6 address without colons).
 */
function inet_pton ($address) {}

/**
 * Converts a string containing an (IPv4) Internet Protocol dotted address into a proper address
 * @link http://www.php.net/manual/en/function.ip2long.php
 * @param string $ip_address <p>
 * A standard format address.
 * </p>
 * @return int the IPv4 address or false if ip_address
 * is invalid.
 */
function ip2long ($ip_address) {}

/**
 * Converts an (IPv4) Internet network address into a string in Internet standard dotted format
 * @link http://www.php.net/manual/en/function.long2ip.php
 * @param string $proper_address <p>
 * A proper address representation.
 * </p>
 * @return string the Internet IP address as a string.
 */
function long2ip ($proper_address) {}

/**
 * Gets the value of an environment variable
 * @link http://www.php.net/manual/en/function.getenv.php
 * @param string $varname <p>
 * The variable name.
 * </p>
 * @return string the value of the environment variable
 * varname, or false if the environment
 * variable varname does not exist.
 */
function getenv ($varname) {}

/**
 * Sets the value of an environment variable
 * @link http://www.php.net/manual/en/function.putenv.php
 * @param string $setting <p>
 * The setting, like "FOO=BAR"
 * </p>
 * @return bool true on success or false on failure
 */
function putenv ($setting) {}

/**
 * Gets options from the command line argument list
 * @link http://www.php.net/manual/en/function.getopt.php
 * @param string $options Each character in this string will be used as option characters and
 * matched against options passed to the script starting with a single
 * hyphen (-).
 * For example, an option string "x" recognizes an
 * option -x.
 * Only a-z, A-Z and 0-9 are allowed.
 * @param array $longopts [optional] An array of options. Each element in this array will be used as option
 * strings and matched against options passed to the script starting with
 * two hyphens (--).
 * For example, an longopts element "opt" recognizes an
 * option --opt.
 * @return array This function will return an array of option / argument pairs or false on
 * failure.
 * </p>
 * <p>
 * The parsing of options will end at the first non-option found, anything
 * that follows is discarded.
 */
function getopt ($options, array $longopts = null) {}

/**
 * Gets system load average
 * @link http://www.php.net/manual/en/function.sys-getloadavg.php
 * @return array an array with three samples (last 1, 5 and 15
 * minutes).
 */
function sys_getloadavg () {}

/**
 * Return current Unix timestamp with microseconds
 * @link http://www.php.net/manual/en/function.microtime.php
 * @param bool $get_as_float [optional] <p>
 * If used and set to true, microtime will return a
 * float instead of a string, as described in
 * the return values section below.
 * </p>
 * @return mixed By default, microtime returns a string in
 * the form "msec sec", where sec is the number of seconds 
 * since the Unix epoch (0:00:00 January 1,1970 GMT), and msec 
 * measures microseconds that have elapsed since sec 
 * and is also expressed in seconds.
 * </p>
 * <p>
 * If get_as_float is set to true, then
 * microtime returns a float, which
 * represents the current time in seconds since the Unix epoch accurate to the
 * nearest microsecond.
 */
function microtime ($get_as_float = null) {}

/**
 * Get current time
 * @link http://www.php.net/manual/en/function.gettimeofday.php
 * @param bool $return_float [optional] <p>
 * When set to true, a float instead of an array is returned.
 * </p>
 * @return mixed By default an array is returned. If return_float
 * is set, then a float is returned.
 * </p>
 * <p>
 * Array keys:
 * "sec" - seconds since the Unix Epoch
 * "usec" - microseconds
 * "minuteswest" - minutes west of Greenwich
 * "dsttime" - type of dst correction
 */
function gettimeofday ($return_float = null) {}

/**
 * Gets the current resource usages
 * @link http://www.php.net/manual/en/function.getrusage.php
 * @param int $who [optional] <p>
 * If who is 1, getrusage will be called with
 * RUSAGE_CHILDREN.
 * </p>
 * @return array an associative array containing the data returned from the system
 * call. All entries are accessible by using their documented field names.
 */
function getrusage ($who = null) {}

/**
 * Generate a unique ID
 * @link http://www.php.net/manual/en/function.uniqid.php
 * @param string $prefix [optional] <p>
 * Can be useful, for instance, if you generate identifiers
 * simultaneously on several hosts that might happen to generate the
 * identifier at the same microsecond.
 * </p>
 * <p>
 * With an empty prefix, the returned string will
 * be 13 characters long. If more_entropy is
 * true, it will be 23 characters.
 * </p>
 * @param bool $more_entropy [optional] <p>
 * If set to true, uniqid will add additional
 * entropy (using the combined linear congruential generator) at the end
 * of the return value, which increases the likelihood that the result
 * will be unique.
 * </p>
 * @return string the unique identifier, as a string.
 */
function uniqid ($prefix = null, $more_entropy = null) {}

/**
 * Convert a quoted-printable string to an 8 bit string
 * @link http://www.php.net/manual/en/function.quoted-printable-decode.php
 * @param string $str <p>
 * The input string.
 * </p>
 * @return string the 8-bit binary string.
 */
function quoted_printable_decode ($str) {}

/**
 * Convert a 8 bit string to a quoted-printable string
 * @link http://www.php.net/manual/en/function.quoted-printable-encode.php
 * @param string $str <p>
 * The input string.
 * </p>
 * @return string the encoded string.
 */
function quoted_printable_encode ($str) {}

/**
 * Convert from one Cyrillic character set to another
 * @link http://www.php.net/manual/en/function.convert-cyr-string.php
 * @param string $str <p>
 * The string to be converted.
 * </p>
 * @param string $from <p>
 * The source Cyrillic character set, as a single character.
 * </p>
 * @param string $to <p>
 * The target Cyrillic character set, as a single character.
 * </p>
 * @return string the converted string.
 */
function convert_cyr_string ($str, $from, $to) {}

/**
 * Gets the name of the owner of the current PHP script
 * @link http://www.php.net/manual/en/function.get-current-user.php
 * @return string the username as a string.
 */
function get_current_user () {}

/**
 * Limits the maximum execution time
 * @link http://www.php.net/manual/en/function.set-time-limit.php
 * @param int $seconds <p>
 * The maximum execution time, in seconds. If set to zero, no time limit
 * is imposed.
 * </p>
 * @return bool true on success, or false on failure.
 */
function set_time_limit ($seconds) {}

/**
 * Call a header function
 * @link http://www.php.net/manual/en/function.header-register-callback.php
 * @param callable $callback <p>
 * Function called just before the headers are sent. It gets no parameters
 * and the return value is ignored.
 * </p>
 * @return bool true on success or false on failure
 */
function header_register_callback ($callback) {}

/**
 * Gets the value of a PHP configuration option
 * @link http://www.php.net/manual/en/function.get-cfg-var.php
 * @param string $option <p>
 * The configuration option name.
 * </p>
 * @return string the current value of the PHP configuration variable specified by
 * option, or false if an error occurs.
 */
function get_cfg_var ($option) {}

/**
 * Gets the current configuration setting of magic_quotes_gpc
 * @link http://www.php.net/manual/en/function.get-magic-quotes-gpc.php
 * @return bool 0 if magic_quotes_gpc is off, 1 otherwise. 
 * Or always returns false as of PHP 5.4.0.
 */
function get_magic_quotes_gpc () {}

/**
 * Gets the current active configuration setting of magic_quotes_runtime
 * @link http://www.php.net/manual/en/function.get-magic-quotes-runtime.php
 * @return bool 0 if magic_quotes_runtime is off, 1 otherwise. 
 * Or always returns false as of PHP 5.4.0.
 */
function get_magic_quotes_runtime () {}

/**
 * Send an error message to the defined error handling routines
 * @link http://www.php.net/manual/en/function.error-log.php
 * @param string $message <p>
 * The error message that should be logged.
 * </p>
 * @param int $message_type [optional] <p>
 * Says where the error should go. The possible message types are as 
 * follows:
 * </p>
 * <p>
 * <table>
 * error_log log types
 * <tr valign="top">
 * <td>0</td>
 * <td>
 * message is sent to PHP's system logger, using
 * the Operating System's system logging mechanism or a file, depending
 * on what the error_log
 * configuration directive is set to. This is the default option.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>1</td>
 * <td>
 * message is sent by email to the address in
 * the destination parameter. This is the only
 * message type where the fourth parameter,
 * extra_headers is used.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>2</td>
 * <td>
 * No longer an option.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>3</td>
 * <td>
 * message is appended to the file
 * destination. A newline is not automatically 
 * added to the end of the message string.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>4</td>
 * <td>
 * message is sent directly to the SAPI logging
 * handler.
 * </td>
 * </tr>
 * </table>
 * </p>
 * @param string $destination [optional] <p>
 * The destination. Its meaning depends on the 
 * message_type parameter as described above.
 * </p>
 * @param string $extra_headers [optional] <p>
 * The extra headers. It's used when the message_type
 * parameter is set to 1.
 * This message type uses the same internal function as 
 * mail does.
 * </p>
 * @return bool true on success or false on failure
 */
function error_log ($message, $message_type = null, $destination = null, $extra_headers = null) {}

/**
 * Get the last occurred error
 * @link http://www.php.net/manual/en/function.error-get-last.php
 * @return array an associative array describing the last error with keys "type",
 * "message", "file" and "line". If the error has been caused by a PHP
 * internal function then the "message" begins with its name.
 * Returns &null; if there hasn't been an error yet.
 */
function error_get_last () {}

/**
 * Clear the last error occurred
 * @link http://www.php.net/manual/en/function.error-clear-last.php
 * @return void Clear the last error occurred
 */
function error_clear_last () {}

/**
 * Call the callback given by the first parameter
 * @link http://www.php.net/manual/en/function.call-user-func.php
 * @param callable $callback <p>
 * The callable to be called.
 * </p>
 * @param mixed $parameter [optional] <p>
 * Zero or more parameters to be passed to the callback.
 * </p>
 * <p>
 * Note that the parameters for call_user_func are
 * not passed by reference.
 * call_user_func example and references
 * ]]>
 * The above example will output:</p>
 * </p>
 * @param mixed $_ [optional] 
 * @return mixed the return value of the callback, or false on error.
 */
function call_user_func ($callback, $parameter = null, $_ = null) {}

/**
 * Call a callback with an array of parameters
 * @link http://www.php.net/manual/en/function.call-user-func-array.php
 * @param callable $callback <p>
 * The callable to be called.
 * </p>
 * @param array $param_arr <p>
 * The parameters to be passed to the callback, as an indexed array.
 * </p>
 * @return mixed the return value of the callback, or false on error.
 */
function call_user_func_array ($callback, array $param_arr) {}

/**
 * Call a static method
 * @link http://www.php.net/manual/en/function.forward-static-call.php
 * @param callable $function <p>
 * The function or method to be called. This parameter may be an array,
 * with the name of the class, and the method, or a string, with a function
 * name.
 * </p>
 * @param mixed $parameter [optional] <p>
 * Zero or more parameters to be passed to the function.
 * </p>
 * @param mixed $_ [optional] 
 * @return mixed the function result, or false on error.
 */
function forward_static_call ($function, $parameter = null, $_ = null) {}

/**
 * Call a static method and pass the arguments as array
 * @link http://www.php.net/manual/en/function.forward-static-call-array.php
 * @param callable $function <p>
 * The function or method to be called. This parameter may be an &array;,
 * with the name of the class, and the method, or a &string;, with a function
 * name.
 * </p>
 * @param array $parameters 
 * @return mixed the function result, or false on error.
 */
function forward_static_call_array ($function, array $parameters) {}

/**
 * Generates a storable representation of a value
 * @link http://www.php.net/manual/en/function.serialize.php
 * @param mixed $value <p>
 * The value to be serialized. serialize
 * handles all types, except the resource-type.
 * You can even serialize arrays that contain
 * references to itself. Circular references inside the array/object you 
 * are serializing will also be stored. Any other 
 * reference will be lost.
 * </p>
 * <p>
 * When serializing objects, PHP will attempt to call the member function
 * __sleep() prior to serialization. 
 * This is to allow the object to do any last minute clean-up, etc. prior 
 * to being serialized. Likewise, when the object is restored using 
 * unserialize the __wakeup() member function is called.
 * </p>
 * <p>
 * Object's private members have the class name prepended to the member
 * name; protected members have a '*' prepended to the member name.
 * These prepended values have null bytes on either side.
 * </p>
 * @return string a string containing a byte-stream representation of 
 * value that can be stored anywhere.
 * </p>
 * <p>
 * Note that this is a binary string which may include null bytes, and needs
 * to be stored and handled as such. For example,
 * serialize output should generally be stored in a BLOB
 * field in a database, rather than a CHAR or TEXT field.
 */
function serialize ($value) {}

/**
 * Creates a PHP value from a stored representation
 * @link http://www.php.net/manual/en/function.unserialize.php
 * @param string $str <p>
 * The serialized string.
 * </p>
 * <p>
 * If the variable being unserialized is an object, after successfully 
 * reconstructing the object PHP will automatically attempt to call the
 * __wakeup() member
 * function (if it exists).
 * </p>
 * <p>
 * unserialize_callback_func directive
 * <p>
 * It's possible to set a callback-function which will be called,
 * if an undefined class should be instantiated during unserializing.
 * (to prevent getting an incomplete object "__PHP_Incomplete_Class".)
 * Use your &php.ini;, ini_set or &htaccess; 
 * to define 'unserialize_callback_func'. Everytime an undefined class
 * should be instantiated, it'll be called. To disable this feature just
 * empty this setting.
 * </p>
 * </p>
 * @param array $options [optional] <p>
 * Any options to be provided to unserialize, as an
 * associative array.
 * </p>
 * <table>
 * Valid options
 * <tr valign="top">
 * <td>Name</td>
 * <td>Type</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>allowed_classes</td>
 * <td>mixed</td>
 * <td>
 * Either an array of class names which should be
 * accepted, false to accept no classes, or true to accept all
 * classes. If this option is defined and
 * unserialize encounters an object of a class
 * that isn't to be accepted, then the object will be instantiated as
 * __PHP_Incomplete_Class instead.
 * Omitting this option is the same as defining it as true: PHP
 * will attempt to instantiate objects of any class.
 * </td>
 * </tr>
 * </table>
 * @return mixed The converted value is returned, and can be a boolean,
 * integer, float, string,
 * array or object.
 * </p>
 * <p>
 * In case the passed string is not unserializeable, false is returned and
 * E_NOTICE is issued.
 */
function unserialize ($str, array $options = null) {}

/**
 * Dumps information about a variable
 * @link http://www.php.net/manual/en/function.var-dump.php
 * @param mixed $expression <p>
 * The variable you want to dump.
 * </p>
 * @param mixed $_ [optional] 
 * @return void 
 */
function var_dump ($expression, $_ = null) {}

/**
 * Outputs or returns a parsable string representation of a variable
 * @link http://www.php.net/manual/en/function.var-export.php
 * @param mixed $expression <p>
 * The variable you want to export.
 * </p>
 * @param bool $return [optional] <p>
 * If used and set to true, var_export will return
 * the variable representation instead of outputting it.
 * </p>
 * @return mixed the variable representation when the return 
 * parameter is used and evaluates to true. Otherwise, this function will
 * return &null;.
 */
function var_export ($expression, $return = null) {}

/**
 * Dumps a string representation of an internal zend value to output
 * @link http://www.php.net/manual/en/function.debug-zval-dump.php
 * @param mixed $variable <p>
 * The variable being evaluated.
 * </p>
 * @param mixed $_ [optional] 
 * @return void 
 */
function debug_zval_dump ($variable, $_ = null) {}

/**
 * Prints human-readable information about a variable
 * @link http://www.php.net/manual/en/function.print-r.php
 * @param mixed $expression <p>
 * The expression to be printed.
 * </p>
 * @param bool $return [optional] <p>
 * If you would like to capture the output of print_r, 
 * use the return parameter. When this parameter is set
 * to true, print_r will return the information rather than print it.
 * </p>
 * @return mixed If given a string, integer or float,
 * the value itself will be printed. If given an array, values
 * will be presented in a format that shows keys and elements. Similar
 * notation is used for objects.
 * </p>
 * <p>
 * When the return parameter is true, this function
 * will return a string. Otherwise, the return value is true.
 */
function print_r ($expression, $return = null) {}

/**
 * Returns the amount of memory allocated to PHP
 * @link http://www.php.net/manual/en/function.memory-get-usage.php
 * @param bool $real_usage [optional] <p>
 * Set this to true to get the real size of memory allocated from
 * system. If not set or false only the memory used by
 * emalloc() is reported.
 * </p>
 * @return int the memory amount in bytes.
 */
function memory_get_usage ($real_usage = null) {}

/**
 * Returns the peak of memory allocated by PHP
 * @link http://www.php.net/manual/en/function.memory-get-peak-usage.php
 * @param bool $real_usage [optional] <p>
 * Set this to true to get the real size of memory allocated from
 * system. If not set or false only the memory used by
 * emalloc() is reported.
 * </p>
 * @return int the memory peak in bytes.
 */
function memory_get_peak_usage ($real_usage = null) {}

/**
 * Register a function for execution on shutdown
 * @link http://www.php.net/manual/en/function.register-shutdown-function.php
 * @param callable $callback <p>
 * The shutdown callback to register.
 * </p>
 * <p>
 * The shutdown callbacks are executed as the part of the request, so
 * it's possible to send output from them and access output buffers.
 * </p>
 * @param mixed $parameter [optional] <p>
 * It is possible to pass parameters to the shutdown function by passing
 * additional parameters.
 * </p>
 * @param mixed $_ [optional] 
 * @return void 
 */
function register_shutdown_function ($callback, $parameter = null, $_ = null) {}

/**
 * Register a function for execution on each tick
 * @link http://www.php.net/manual/en/function.register-tick-function.php
 * @param callable $function <p>
 * The function name as a string, or an array consisting of an object and
 * a method.
 * </p>
 * @param mixed $arg [optional] <p>
 * </p>
 * @param mixed $_ [optional] 
 * @return bool true on success or false on failure
 */
function register_tick_function ($function, $arg = null, $_ = null) {}

/**
 * De-register a function for execution on each tick
 * @link http://www.php.net/manual/en/function.unregister-tick-function.php
 * @param string $function_name <p>
 * The function name, as a string.
 * </p>
 * @return void 
 */
function unregister_tick_function ($function_name) {}

/**
 * Syntax highlighting of a file
 * @link http://www.php.net/manual/en/function.highlight-file.php
 * @param string $filename <p>
 * Path to the PHP file to be highlighted.
 * </p>
 * @param bool $return [optional] <p>
 * Set this parameter to true to make this function return the
 * highlighted code.
 * </p>
 * @return mixed If return is set to true, returns the highlighted
 * code as a string instead of printing it out. Otherwise, it will return
 * true on success, false on failure.
 */
function highlight_file ($filename, $return = null) {}

/**
 * &Alias; <function>highlight_file</function>
 * @link http://www.php.net/manual/en/function.show-source.php
 * @param $file_name
 * @param $return [optional]
 */
function show_source ($file_name, $return = null) {}

/**
 * Syntax highlighting of a string
 * @link http://www.php.net/manual/en/function.highlight-string.php
 * @param string $str <p>
 * The PHP code to be highlighted. This should include the opening tag.
 * </p>
 * @param bool $return [optional] <p>
 * Set this parameter to true to make this function return the
 * highlighted code.
 * </p>
 * @return mixed If return is set to true, returns the highlighted
 * code as a string instead of printing it out. Otherwise, it will return
 * true on success, false on failure.
 */
function highlight_string ($str, $return = null) {}

/**
 * Return source with stripped comments and whitespace
 * @link http://www.php.net/manual/en/function.php-strip-whitespace.php
 * @param string $filename <p>
 * Path to the PHP file.
 * </p>
 * @return string The stripped source code will be returned on success, or an empty string
 * on failure.
 * </p>
 * <p>
 * This function works as described as of PHP 5.0.1. Before this it would
 * only return an empty string. For more information on this bug and its
 * prior behavior, see bug report
 * #29606.
 */
function php_strip_whitespace ($filename) {}

/**
 * Gets the value of a configuration option
 * @link http://www.php.net/manual/en/function.ini-get.php
 * @param string $varname <p>
 * The configuration option name.
 * </p>
 * @return string the value of the configuration option as a string on success, or an
 * empty string for null values. Returns false if the
 * configuration option doesn't exist.
 */
function ini_get ($varname) {}

/**
 * Gets all configuration options
 * @link http://www.php.net/manual/en/function.ini-get-all.php
 * @param string $extension [optional] <p>
 * An optional extension name. If set, the function return only options
 * specific for that extension.
 * </p>
 * @param bool $details [optional] <p>
 * Retrieve details settings or only the current value for each setting.
 * Default is true (retrieve details).
 * </p>
 * @return array an associative array with directive name as the array key.
 * </p>
 * <p>
 * When details is true (default) the array will
 * contain global_value (set in
 * &php.ini;), local_value (perhaps set with
 * ini_set or &htaccess;), and
 * access (the access level).
 * </p>
 * <p>
 * When details is false the value will be the
 * current value of the option.
 * </p>
 * <p>
 * See the manual section
 * for information on what access levels mean.
 * </p>
 * <p>
 * It's possible for a directive to have multiple access levels, which is
 * why access shows the appropriate bitmask values.
 */
function ini_get_all ($extension = null, $details = null) {}

/**
 * Sets the value of a configuration option
 * @link http://www.php.net/manual/en/function.ini-set.php
 * @param string $varname <p>
 * </p>
 * <p>
 * Not all the available options can be changed using
 * ini_set. There is a list of all available options
 * in the appendix.
 * </p>
 * @param string $newvalue <p>
 * The new value for the option.
 * </p>
 * @return string the old value on success, false on failure.
 */
function ini_set ($varname, $newvalue) {}

/**
 * &Alias; <function>ini_set</function>
 * @link http://www.php.net/manual/en/function.ini-alter.php
 * @param $varname
 * @param $newvalue
 */
function ini_alter ($varname, $newvalue) {}

/**
 * Restores the value of a configuration option
 * @link http://www.php.net/manual/en/function.ini-restore.php
 * @param string $varname <p>
 * The configuration option name.
 * </p>
 * @return void 
 */
function ini_restore ($varname) {}

/**
 * Gets the current include_path configuration option
 * @link http://www.php.net/manual/en/function.get-include-path.php
 * @return string the path, as a string.
 */
function get_include_path () {}

/**
 * Sets the include_path configuration option
 * @link http://www.php.net/manual/en/function.set-include-path.php
 * @param string $new_include_path <p>
 * The new value for the include_path
 * </p>
 * @return string the old include_path on
 * success or false on failure.
 */
function set_include_path ($new_include_path) {}

/**
 * Restores the value of the include_path configuration option
 * @link http://www.php.net/manual/en/function.restore-include-path.php
 * @return void 
 */
function restore_include_path () {}

/**
 * Send a cookie
 * @link http://www.php.net/manual/en/function.setcookie.php
 * @param string $name <p>
 * The name of the cookie.
 * </p>
 * @param string $value [optional] <p>
 * The value of the cookie. This value is stored on the clients computer;
 * do not store sensitive information. Assuming the
 * name is 'cookiename', this
 * value is retrieved through $_COOKIE['cookiename']
 * </p>
 * @param int $expire [optional] <p>
 * The time the cookie expires. This is a Unix timestamp so is
 * in number of seconds since the epoch. In other words, you'll
 * most likely set this with the time function
 * plus the number of seconds before you want it to expire. Or
 * you might use mktime.
 * time()+60*60*24*30 will set the cookie to
 * expire in 30 days. If set to 0, or omitted, the cookie will expire at
 * the end of the session (when the browser closes).
 * </p>
 * <p>
 * <p>
 * You may notice the expire parameter takes on a
 * Unix timestamp, as opposed to the date format Wdy, DD-Mon-YYYY
 * HH:MM:SS GMT, this is because PHP does this conversion
 * internally.
 * </p>
 * </p>
 * @param string $path [optional] <p>
 * The path on the server in which the cookie will be available on.
 * If set to '/', the cookie will be available
 * within the entire domain. If set to
 * '/foo/', the cookie will only be available
 * within the /foo/ directory and all
 * sub-directories such as /foo/bar/ of
 * domain. The default value is the
 * current directory that the cookie is being set in.
 * </p>
 * @param string $domain [optional] <p>
 * The domain that the cookie is available to. Setting the domain to
 * 'www.example.com' will make the cookie
 * available in the www subdomain and higher subdomains.
 * Cookies available to a lower domain, such as
 * 'example.com' will be available to higher subdomains,
 * such as 'www.example.com'.
 * Older browsers still implementing the deprecated
 * RFC 2109 may require a leading
 * . to match all subdomains.
 * </p>
 * @param bool $secure [optional] <p>
 * Indicates that the cookie should only be transmitted over a
 * secure HTTPS connection from the client. When set to true, the
 * cookie will only be set if a secure connection exists.
 * On the server-side, it's on the programmer to send this
 * kind of cookie only on secure connection (e.g. with respect to
 * $_SERVER["HTTPS"]).
 * </p>
 * @param bool $httponly [optional] <p>
 * When true the cookie will be made accessible only through the HTTP
 * protocol. This means that the cookie won't be accessible by
 * scripting languages, such as JavaScript. It has been suggested that
 * this setting can effectively help to reduce identity theft through
 * XSS attacks (although it is not supported by all browsers), but that
 * claim is often disputed. Added in PHP 5.2.0.
 * true or false
 * </p>
 * @return bool If output exists prior to calling this function,
 * setcookie will fail and return false. If
 * setcookie successfully runs, it will return true.
 * This does not indicate whether the user accepted the cookie.
 */
function setcookie ($name, $value = null, $expire = null, $path = null, $domain = null, $secure = null, $httponly = null) {}

/**
 * Send a cookie without urlencoding the cookie value
 * @link http://www.php.net/manual/en/function.setrawcookie.php
 * @param string $name 
 * @param string $value [optional] 
 * @param int $expire [optional] 
 * @param string $path [optional] 
 * @param string $domain [optional] 
 * @param bool $secure [optional] 
 * @param bool $httponly [optional] 
 * @return bool true on success or false on failure
 */
function setrawcookie ($name, $value = null, $expire = null, $path = null, $domain = null, $secure = null, $httponly = null) {}

/**
 * Send a raw HTTP header
 * @link http://www.php.net/manual/en/function.header.php
 * @param string $string <p>
 * The header string.
 * </p>
 * <p>
 * There are two special-case header calls. The first is a header
 * that starts with the string "HTTP/" (case is not
 * significant), which will be used to figure out the HTTP status
 * code to send. For example, if you have configured Apache to
 * use a PHP script to handle requests for missing files (using
 * the ErrorDocument directive), you may want to
 * make sure that your script generates the proper status code.
 * </p>
 * <p>
 * ]]>
 * </p>
 * <p>
 * The second special case is the "Location:" header. Not only does
 * it send this header back to the browser, but it also returns a
 * REDIRECT (302) status code to the browser
 * unless the 201 or
 * a 3xx status code has already been set.
 * </p>
 * <p>
 * ]]>
 * </p>
 * @param bool $replace [optional] <p>
 * The optional replace parameter indicates
 * whether the header should replace a previous similar header, or
 * add a second header of the same type. By default it will replace,
 * but if you pass in false as the second argument you can force
 * multiple headers of the same type. For example:
 * </p>
 * <p>
 * ]]>
 * </p>
 * @param int $http_response_code [optional] <p>
 * Forces the HTTP response code to the specified value. Note that this 
 * parameter only has an effect if the string is 
 * not empty.
 * </p>
 * @return void 
 */
function header ($string, $replace = null, $http_response_code = null) {}

/**
 * Remove previously set headers
 * @link http://www.php.net/manual/en/function.header-remove.php
 * @param string $name [optional] <p>
 * The header name to be removed.
 * </p>
 * This parameter is case-insensitive.
 * @return void 
 */
function header_remove ($name = null) {}

/**
 * Checks if or where headers have been sent
 * @link http://www.php.net/manual/en/function.headers-sent.php
 * @param string $file [optional] <p>
 * If the optional file and
 * line parameters are set, 
 * headers_sent will put the PHP source file name
 * and line number where output started in the file
 * and line variables.
 * </p>
 * @param int $line [optional] <p>
 * The line number where the output started.
 * </p>
 * @return bool headers_sent will return false if no HTTP headers
 * have already been sent or true otherwise.
 */
function headers_sent (&$file = null, &$line = null) {}

/**
 * Returns a list of response headers sent (or ready to send)
 * @link http://www.php.net/manual/en/function.headers-list.php
 * @return array a numerically indexed array of headers.
 */
function headers_list () {}

/**
 * Get or Set the HTTP response code
 * @link http://www.php.net/manual/en/function.http-response-code.php
 * @param int $response_code [optional] <p>
 * The optional response_code will set the response code.
 * </p>
 * <p>
 * ]]>
 * </p>
 * @return int The current response code. By default the return value is int(200).
 */
function http_response_code ($response_code = null) {}

/**
 * Check whether client disconnected
 * @link http://www.php.net/manual/en/function.connection-aborted.php
 * @return int 1 if client disconnected, 0 otherwise.
 */
function connection_aborted () {}

/**
 * Returns connection status bitfield
 * @link http://www.php.net/manual/en/function.connection-status.php
 * @return int the connection status bitfield, which can be used against the
 * CONNECTION_XXX constants to determine the connection
 * status.
 */
function connection_status () {}

/**
 * Set whether a client disconnect should abort script execution
 * @link http://www.php.net/manual/en/function.ignore-user-abort.php
 * @param string $value [optional] <p>
 * If set, this function will set the ignore_user_abort ini setting
 * to the given value. If not, this function will
 * only return the previous setting without changing it.
 * </p>
 * @return int the previous setting, as an integer.
 */
function ignore_user_abort ($value = null) {}

/**
 * Parse a configuration file
 * @link http://www.php.net/manual/en/function.parse-ini-file.php
 * @param string $filename <p>
 * The filename of the ini file being parsed.
 * </p>
 * @param bool $process_sections [optional] <p>
 * By setting the process_sections
 * parameter to true, you get a multidimensional array, with
 * the section names and settings included. The default
 * for process_sections is false 
 * </p>
 * @param int $scanner_mode [optional] <p>
 * Can either be INI_SCANNER_NORMAL (default) or 
 * INI_SCANNER_RAW. If INI_SCANNER_RAW 
 * is supplied, then option values will not be parsed.
 * </p>
 * &ini.scanner.typed;
 * @return array The settings are returned as an associative array on success,
 * and false on failure.
 */
function parse_ini_file ($filename, $process_sections = null, $scanner_mode = null) {}

/**
 * Parse a configuration string
 * @link http://www.php.net/manual/en/function.parse-ini-string.php
 * @param string $ini <p>
 * The contents of the ini file being parsed.
 * </p>
 * @param bool $process_sections [optional] <p>
 * By setting the process_sections
 * parameter to true, you get a multidimensional array, with
 * the section names and settings included. The default
 * for process_sections is false 
 * </p>
 * @param int $scanner_mode [optional] <p>
 * Can either be INI_SCANNER_NORMAL (default) or 
 * INI_SCANNER_RAW. If INI_SCANNER_RAW 
 * is supplied, then option values will not be parsed.
 * </p>
 * &ini.scanner.typed;
 * @return array The settings are returned as an associative array on success,
 * and false on failure.
 */
function parse_ini_string ($ini, $process_sections = null, $scanner_mode = null) {}

/**
 * Tells whether the file was uploaded via HTTP POST
 * @link http://www.php.net/manual/en/function.is-uploaded-file.php
 * @param string $filename <p>
 * The filename being checked.
 * </p>
 * @return bool true on success or false on failure
 */
function is_uploaded_file ($filename) {}

/**
 * Moves an uploaded file to a new location
 * @link http://www.php.net/manual/en/function.move-uploaded-file.php
 * @param string $filename <p>
 * The filename of the uploaded file.
 * </p>
 * @param string $destination <p>
 * The destination of the moved file.
 * </p>
 * @return bool true on success.
 * </p>
 * <p>
 * If filename is not a valid upload file,
 * then no action will occur, and
 * move_uploaded_file will return
 * false.
 * </p>
 * <p>
 * If filename is a valid upload file, but
 * cannot be moved for some reason, no action will occur, and
 * move_uploaded_file will return
 * false. Additionally, a warning will be issued.
 */
function move_uploaded_file ($filename, $destination) {}

/**
 * Get the Internet host name corresponding to a given IP address
 * @link http://www.php.net/manual/en/function.gethostbyaddr.php
 * @param string $ip_address <p>
 * The host IP address.
 * </p>
 * @return string the host name on success, the unmodified ip_address
 * on failure, or false on malformed input.
 */
function gethostbyaddr ($ip_address) {}

/**
 * Get the IPv4 address corresponding to a given Internet host name
 * @link http://www.php.net/manual/en/function.gethostbyname.php
 * @param string $hostname <p>
 * The host name.
 * </p>
 * @return string the IPv4 address or a string containing the unmodified
 * hostname on failure.
 */
function gethostbyname ($hostname) {}

/**
 * Get a list of IPv4 addresses corresponding to a given Internet host
   name
 * @link http://www.php.net/manual/en/function.gethostbynamel.php
 * @param string $hostname <p>
 * The host name.
 * </p>
 * @return array an array of IPv4 addresses or false if
 * hostname could not be resolved.
 */
function gethostbynamel ($hostname) {}

/**
 * Gets the host name
 * @link http://www.php.net/manual/en/function.gethostname.php
 * @return string a string with the hostname on success, otherwise false is 
 * returned.
 */
function gethostname () {}

/**
 * &Alias; <function>checkdnsrr</function>
 * @link http://www.php.net/manual/en/function.dns-check-record.php
 * @param $host
 * @param $type [optional]
 */
function dns_check_record ($host, $type = null) {}

/**
 * Check DNS records corresponding to a given Internet host name or IP address
 * @link http://www.php.net/manual/en/function.checkdnsrr.php
 * @param string $host <p>
 * host may either be the IP address in
 * dotted-quad notation or the host name.
 * </p>
 * @param string $type [optional] <p>
 * type may be any one of: A, MX, NS, SOA,
 * PTR, CNAME, AAAA, A6, SRV, NAPTR, TXT or ANY.
 * </p>
 * @return bool true if any records are found; returns false if no records
 * were found or if an error occurred.
 */
function checkdnsrr ($host, $type = null) {}

/**
 * &Alias; <function>getmxrr</function>
 * @link http://www.php.net/manual/en/function.dns-get-mx.php
 * @param $hostname
 * @param $mxhosts
 * @param $weight [optional]
 */
function dns_get_mx ($hostname, &$mxhosts, &$weight = null) {}

/**
 * Get MX records corresponding to a given Internet host name
 * @link http://www.php.net/manual/en/function.getmxrr.php
 * @param string $hostname <p>
 * The Internet host name.
 * </p>
 * @param array $mxhosts <p>
 * A list of the MX records found is placed into the array
 * mxhosts. 
 * </p>
 * @param array $weight [optional] <p>
 * If the weight array is given, it will be filled
 * with the weight information gathered.
 * </p>
 * @return bool true if any records are found; returns false if no records
 * were found or if an error occurred.
 */
function getmxrr ($hostname, array &$mxhosts, array &$weight = null) {}

/**
 * Fetch DNS Resource Records associated with a hostname
 * @link http://www.php.net/manual/en/function.dns-get-record.php
 * @param string $hostname <p>
 * hostname should be a valid DNS hostname such
 * as "www.example.com". Reverse lookups can be generated
 * using in-addr.arpa notation, but
 * gethostbyaddr is more suitable for
 * the majority of reverse lookups.
 * </p>
 * <p>
 * Per DNS standards, email addresses are given in user.host format (for
 * example: hostmaster.example.com as opposed to hostmaster@example.com),
 * be sure to check this value and modify if necessary before using it
 * with a functions such as mail.
 * </p>
 * @param int $type [optional] <p>
 * By default, dns_get_record will search for any
 * resource records associated with hostname. 
 * To limit the query, specify the optional type
 * parameter. May be any one of the following:
 * DNS_A, DNS_CNAME,
 * DNS_HINFO, DNS_MX,
 * DNS_NS, DNS_PTR,
 * DNS_SOA, DNS_TXT,
 * DNS_AAAA, DNS_SRV,
 * DNS_NAPTR, DNS_A6,
 * DNS_ALL or DNS_ANY.
 * </p>
 * <p>
 * Because of eccentricities in the performance of libresolv
 * between platforms, DNS_ANY will not
 * always return every record, the slower DNS_ALL
 * will collect all records more reliably.
 * </p>
 * @param array $authns [optional] <p>
 * Passed by reference and, if given, will be populated with Resource
 * Records for the Authoritative Name Servers.
 * </p>
 * @param array $addtl [optional] <p>
 * Passed by reference and, if given, will be populated with any
 * Additional Records.
 * </p>
 * @param bool $raw [optional] <p>
 * In case of raw mode, we query only the requestd type instead of looping
 * type by type before going with the additional info stuff.
 * </p>
 * @return array This function returns an array of associative arrays,
 * or false on failure. Each associative array contains
 * at minimum the following keys:
 * <table>
 * Basic DNS attributes
 * <tr valign="top">
 * <td>Attribute</td>
 * <td>Meaning</td>
 * </tr>
 * <tr valign="top">
 * <td>host</td>
 * <td>
 * The record in the DNS namespace to which the rest of the associated data refers.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>class</td>
 * <td>
 * dns_get_record only returns Internet class records and as
 * such this parameter will always return IN.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>type</td>
 * <td>
 * String containing the record type. Additional attributes will also be contained
 * in the resulting array dependant on the value of type. See table below.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ttl</td>
 * <td>
 * "Time To Live" remaining for this record. This will not equal
 * the record's original ttl, but will rather equal the original ttl minus whatever
 * length of time has passed since the authoritative name server was queried.
 * </td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * <table>
 * Other keys in associative arrays dependant on 'type'
 * <tr valign="top">
 * <td>Type</td>
 * <td>Extra Columns</td>
 * </tr>
 * <tr valign="top">
 * <td>A</td>
 * <td>
 * ip: An IPv4 addresses in dotted decimal notation.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>MX</td>
 * <td>
 * pri: Priority of mail exchanger.
 * Lower numbers indicate greater priority.
 * target: FQDN of the mail exchanger.
 * See also dns_get_mx.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CNAME</td>
 * <td>
 * target: FQDN of location in DNS namespace to which
 * the record is aliased.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>NS</td>
 * <td>
 * target: FQDN of the name server which is authoritative
 * for this hostname.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>PTR</td>
 * <td>
 * target: Location within the DNS namespace to which
 * this record points.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>TXT</td>
 * <td>
 * txt: Arbitrary string data associated with this record.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>HINFO</td>
 * <td>
 * cpu: IANA number designating the CPU of the machine
 * referenced by this record.
 * os: IANA number designating the Operating System on
 * the machine referenced by this record.
 * See IANA's Operating System
 * Names for the meaning of these values.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>SOA</td>
 * <td>
 * mname: FQDN of the machine from which the resource
 * records originated.
 * rname: Email address of the administrative contain
 * for this domain.
 * serial: Serial # of this revision of the requested
 * domain.
 * refresh: Refresh interval (seconds) secondary name
 * servers should use when updating remote copies of this domain.
 * retry: Length of time (seconds) to wait after a
 * failed refresh before making a second attempt.
 * expire: Maximum length of time (seconds) a secondary
 * DNS server should retain remote copies of the zone data without a
 * successful refresh before discarding.
 * minimum-ttl: Minimum length of time (seconds) a
 * client can continue to use a DNS resolution before it should request
 * a new resolution from the server. Can be overridden by individual
 * resource records.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>AAAA</td>
 * <td>
 * ipv6: IPv6 address
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>A6(PHP &gt;= 5.1.0)</td>
 * <td>
 * masklen: Length (in bits) to inherit from the target
 * specified by chain.
 * ipv6: Address for this specific record to merge with
 * chain.
 * chain: Parent record to merge with
 * ipv6 data.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>SRV</td>
 * <td>
 * pri: (Priority) lowest priorities should be used first.
 * weight: Ranking to weight which of commonly prioritized
 * targets should be chosen at random.
 * target and port: hostname and port
 * where the requested service can be found.
 * For additional information see: RFC 2782
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>NAPTR</td>
 * <td>
 * order and pref: Equivalent to
 * pri and weight above.
 * flags, services, regex,
 * and replacement: Parameters as defined by
 * RFC 2915.
 * </td>
 * </tr>
 * </table>
 */
function dns_get_record ($hostname, $type = null, array &$authns = null, array &$addtl = null, &$raw = null) {}

/**
 * Get the integer value of a variable
 * @link http://www.php.net/manual/en/function.intval.php
 * @param mixed $var <p>
 * The scalar value being converted to an integer
 * </p>
 * @param int $base [optional] <p>
 * The base for the conversion
 * </p>
 * <p>
 * If base is 0, the base used is determined
 * by the format of var:
 * if string includes a "0x" (or "0X") prefix, the base is taken
 * as 16 (hex); otherwise,
 * @return int The integer value of var on success, or 0 on
 * failure. Empty arrays return 0, non-empty arrays return 1.
 * </p>
 * <p>
 * The maximum value depends on the system. 32 bit systems have a 
 * maximum signed integer range of -2147483648 to 2147483647. So for example 
 * on such a system, intval('1000000000000') will return 
 * 2147483647. The maximum signed integer value for 64 bit systems is 
 * 9223372036854775807.
 * </p>
 * <p>
 * Strings will most likely return 0 although this depends on the 
 * leftmost characters of the string. The common rules of 
 * integer casting 
 * apply.
 */
function intval ($var, $base = null) {}

/**
 * Get float value of a variable
 * @link http://www.php.net/manual/en/function.floatval.php
 * @param mixed $var <p>
 * May be any scalar type. floatval should not be used
 * on objects, as doing so will emit an E_NOTICE level
 * error and return 1.
 * </p>
 * @return float The float value of the given variable. Empty arrays return 0, non-empty
 * arrays return 1.
 * </p>
 * <p>
 * Strings will most likely return 0 although this depends on the 
 * leftmost characters of the string. The common rules of 
 * float casting 
 * apply.
 */
function floatval ($var) {}

/**
 * &Alias; <function>floatval</function>
 * @link http://www.php.net/manual/en/function.doubleval.php
 * @param $var
 */
function doubleval ($var) {}

/**
 * Get string value of a variable
 * @link http://www.php.net/manual/en/function.strval.php
 * @param mixed $var <p>
 * The variable that is being converted to a string.
 * </p>
 * <p>
 * var may be any scalar type or an object that
 * implements the __toString()
 * method. You cannot use strval on arrays or on
 * objects that do not implement the
 * __toString() method.
 * </p>
 * @return string The string value of var.
 */
function strval ($var) {}

/**
 * Get the boolean value of a variable
 * @link http://www.php.net/manual/en/function.boolval.php
 * @param mixed $var <p>
 * The scalar value being converted to a boolean.
 * </p>
 * @return boolean The boolean value of var.
 */
function boolval ($var) {}

/**
 * Get the type of a variable
 * @link http://www.php.net/manual/en/function.gettype.php
 * @param mixed $var <p>
 * The variable being type checked.
 * </p>
 * @return string Possible values for the returned string are:
 * "boolean"
 * "integer"
 * "double" (for historical reasons "double" is
 * returned in case of a float, and not simply
 * "float")
 * "string"
 * "array"
 * "object"
 * "resource"
 * "NULL"
 * "unknown type"
 */
function gettype ($var) {}

/**
 * Set the type of a variable
 * @link http://www.php.net/manual/en/function.settype.php
 * @param mixed $var <p>
 * The variable being converted.
 * </p>
 * @param string $type <p>
 * Possibles values of type are:
 * "boolean" (or, since PHP 4.2.0, "bool")
 * @return bool true on success or false on failure
 */
function settype (&$var, $type) {}

/**
 * Finds whether a variable is &null;
 * @link http://www.php.net/manual/en/function.is-null.php
 * @param mixed $var <p>
 * The variable being evaluated.
 * </p>
 * @return bool true if var is null, false
 * otherwise.
 */
function is_null ($var) {}

/**
 * Finds whether a variable is a resource
 * @link http://www.php.net/manual/en/function.is-resource.php
 * @param mixed $var <p>
 * The variable being evaluated.
 * </p>
 * @return bool true if var is a resource,
 * false otherwise.
 */
function is_resource ($var) {}

/**
 * Finds out whether a variable is a boolean
 * @link http://www.php.net/manual/en/function.is-bool.php
 * @param mixed $var <p>
 * The variable being evaluated.
 * </p>
 * @return bool true if var is a boolean,
 * false otherwise.
 */
function is_bool ($var) {}

/**
 * Find whether the type of a variable is integer
 * @link http://www.php.net/manual/en/function.is-int.php
 * @param mixed $var <p>
 * The variable being evaluated.
 * </p>
 * @return bool true if var is an integer,
 * false otherwise.
 */
function is_int ($var) {}

/**
 * Finds whether the type of a variable is float
 * @link http://www.php.net/manual/en/function.is-float.php
 * @param mixed $var <p>
 * The variable being evaluated.
 * </p>
 * @return bool true if var is a float,
 * false otherwise.
 */
function is_float ($var) {}

/**
 * &Alias; <function>is_int</function>
 * @link http://www.php.net/manual/en/function.is-integer.php
 * @param $var
 */
function is_integer ($var) {}

/**
 * &Alias; <function>is_int</function>
 * @link http://www.php.net/manual/en/function.is-long.php
 * @param $var
 */
function is_long ($var) {}

/**
 * &Alias; <function>is_float</function>
 * @link http://www.php.net/manual/en/function.is-double.php
 * @param $var
 */
function is_double ($var) {}

/**
 * &Alias; <function>is_float</function>
 * @link http://www.php.net/manual/en/function.is-real.php
 * @param $var
 */
function is_real ($var) {}

/**
 * Finds whether a variable is a number or a numeric string
 * @link http://www.php.net/manual/en/function.is-numeric.php
 * @param mixed $var <p>
 * The variable being evaluated.
 * </p>
 * @return bool true if var is a number or a numeric
 * string, false otherwise.
 */
function is_numeric ($var) {}

/**
 * Find whether the type of a variable is string
 * @link http://www.php.net/manual/en/function.is-string.php
 * @param mixed $var <p>
 * The variable being evaluated.
 * </p>
 * @return bool true if var is of type string,
 * false otherwise.
 */
function is_string ($var) {}

/**
 * Finds whether a variable is an array
 * @link http://www.php.net/manual/en/function.is-array.php
 * @param mixed $var <p>
 * The variable being evaluated.
 * </p>
 * @return bool true if var is an array,
 * false otherwise.
 */
function is_array ($var) {}

/**
 * Finds whether a variable is an object
 * @link http://www.php.net/manual/en/function.is-object.php
 * @param mixed $var <p>
 * The variable being evaluated.
 * </p>
 * @return bool true if var is an object,
 * false otherwise.
 */
function is_object ($var) {}

/**
 * Finds whether a variable is a scalar
 * @link http://www.php.net/manual/en/function.is-scalar.php
 * @param mixed $var <p>
 * The variable being evaluated.
 * </p>
 * @return bool true if var is a scalar, false
 * otherwise.
 */
function is_scalar ($var) {}

/**
 * Verify that the contents of a variable can be called as a function
 * @link http://www.php.net/manual/en/function.is-callable.php
 * @param mixed $var <p>
 * The value to check
 * </p>
 * @param bool $syntax_only [optional] <p>
 * If set to true the function only verifies that
 * name might be a function or method. It will only
 * reject simple variables that are not strings, or an array that does
 * not have a valid structure to be used as a callback. The valid ones
 * are supposed to have only 2 entries, the first of which is an object
 * or a string, and the second a string.
 * </p>
 * @param string $callable_name [optional] <p>
 * Receives the "callable name". In the example below it is
 * "someClass::someMethod". Note, however, that despite the implication
 * that someClass::SomeMethod() is a callable static method, this is not
 * the case.
 * </p>
 * @return bool true if var is callable, false 
 * otherwise.
 */
function is_callable ($var, $syntax_only = null, &$callable_name = null) {}

/**
 * Closes process file pointer
 * @link http://www.php.net/manual/en/function.pclose.php
 * @param resource $handle <p>
 * The file pointer must be valid, and must have been returned by a
 * successful call to popen.
 * </p>
 * @return int the termination status of the process that was run. In case of 
 * an error then -1 is returned.
 */
function pclose ($handle) {}

/**
 * Opens process file pointer
 * @link http://www.php.net/manual/en/function.popen.php
 * @param string $command <p>
 * The command
 * </p>
 * @param string $mode <p>
 * The mode
 * </p>
 * @return resource a file pointer identical to that returned by
 * fopen, except that it is unidirectional (may
 * only be used for reading or writing) and must be closed with
 * pclose. This pointer may be used with
 * fgets, fgetss, and
 * fwrite. When the mode is 'r', the returned
 * file pointer equals to the STDOUT of the command, when the mode
 * is 'w', the returned file pointer equals to the STDIN of the
 * command.
 * </p>
 * <p>
 * If an error occurs, returns false.
 */
function popen ($command, $mode) {}

/**
 * Outputs a file
 * @link http://www.php.net/manual/en/function.readfile.php
 * @param string $filename <p>
 * The filename being read.
 * </p>
 * @param bool $use_include_path [optional] <p>
 * You can use the optional second parameter and set it to true, if
 * you want to search for the file in the include_path, too.
 * </p>
 * @param resource $context [optional] <p>
 * A context stream resource.
 * </p>
 * @return int the number of bytes read from the file. If an error
 * occurs, false is returned and unless the function was called as
 * @readfile, an error message is printed.
 */
function readfile ($filename, $use_include_path = null, $context = null) {}

/**
 * Rewind the position of a file pointer
 * @link http://www.php.net/manual/en/function.rewind.php
 * @param resource $handle <p>
 * The file pointer must be valid, and must point to a file
 * successfully opened by fopen.
 * </p>
 * @return bool true on success or false on failure
 */
function rewind ($handle) {}

/**
 * Removes directory
 * @link http://www.php.net/manual/en/function.rmdir.php
 * @param string $dirname <p>
 * Path to the directory.
 * </p>
 * @param resource $context [optional] &note.context-support;
 * @return bool true on success or false on failure
 */
function rmdir ($dirname, $context = null) {}

/**
 * Changes the current umask
 * @link http://www.php.net/manual/en/function.umask.php
 * @param int $mask [optional] <p>
 * The new umask.
 * </p>
 * @return int umask without arguments simply returns the
 * current umask otherwise the old umask is returned.
 */
function umask ($mask = null) {}

/**
 * Closes an open file pointer
 * @link http://www.php.net/manual/en/function.fclose.php
 * @param resource $handle <p>
 * The file pointer must be valid, and must point to a file successfully
 * opened by fopen or fsockopen.
 * </p>
 * @return bool true on success or false on failure
 */
function fclose ($handle) {}

/**
 * Tests for end-of-file on a file pointer
 * @link http://www.php.net/manual/en/function.feof.php
 * @param resource $handle &fs.validfp.all;
 * @return bool true if the file pointer is at EOF or an error occurs
 * (including socket timeout); otherwise returns false.
 */
function feof ($handle) {}

/**
 * Gets character from file pointer
 * @link http://www.php.net/manual/en/function.fgetc.php
 * @param resource $handle &fs.validfp.all;
 * @return string a string containing a single character read from the file pointed
 * to by handle. Returns false on EOF.
 */
function fgetc ($handle) {}

/**
 * Gets line from file pointer
 * @link http://www.php.net/manual/en/function.fgets.php
 * @param resource $handle &fs.validfp.all;
 * @param int $length [optional] <p>
 * Reading ends when length - 1 bytes have been
 * read, or a newline (which is included in the return value), or an EOF
 * (whichever comes first). If no length is specified, it will keep
 * reading from the stream until it reaches the end of the line.
 * </p>
 * <p>
 * Until PHP 4.3.0, omitting it would assume 1024 as the line length.
 * If the majority of the lines in the file are all larger than 8KB,
 * it is more resource efficient for your script to specify the maximum
 * line length.
 * </p>
 * @return string a string of up to length - 1 bytes read from
 * the file pointed to by handle. If there is no more data 
 * to read in the file pointer, then false is returned.
 * </p>
 * <p>
 * If an error occurs, false is returned.
 */
function fgets ($handle, $length = null) {}

/**
 * Gets line from file pointer and strip HTML tags
 * @link http://www.php.net/manual/en/function.fgetss.php
 * @param resource $handle &fs.validfp.all;
 * @param int $length [optional] <p>
 * Length of the data to be retrieved.
 * </p>
 * @param string $allowable_tags [optional] <p>
 * You can use the optional third parameter to specify tags which should
 * not be stripped.
 * </p>
 * @return string a string of up to length - 1 bytes read from
 * the file pointed to by handle, with all HTML and PHP
 * code stripped.
 * </p>
 * <p>
 * If an error occurs, returns false.
 */
function fgetss ($handle, $length = null, $allowable_tags = null) {}

/**
 * Binary-safe file read
 * @link http://www.php.net/manual/en/function.fread.php
 * @param resource $handle &fs.file.pointer;
 * @param int $length <p>
 * Up to length number of bytes read.
 * </p>
 * @return string the read string or false on failure.
 */
function fread ($handle, $length) {}

/**
 * Opens file or URL
 * @link http://www.php.net/manual/en/function.fopen.php
 * @param string $filename <p>
 * If filename is of the form "scheme://...", it
 * is assumed to be a URL and PHP will search for a protocol handler
 * (also known as a wrapper) for that scheme. If no wrappers for that
 * protocol are registered, PHP will emit a notice to help you track
 * potential problems in your script and then continue as though
 * filename specifies a regular file.
 * </p>
 * <p>
 * If PHP has decided that filename specifies
 * a local file, then it will try to open a stream on that file.
 * The file must be accessible to PHP, so you need to ensure that
 * the file access permissions allow this access.
 * If you have enabled safe mode
 * or open_basedir further
 * restrictions may apply.
 * </p>
 * <p>
 * If PHP has decided that filename specifies
 * a registered protocol, and that protocol is registered as a
 * network URL, PHP will check to make sure that
 * allow_url_fopen is
 * enabled. If it is switched off, PHP will emit a warning and
 * the fopen call will fail.
 * </p>
 * <p>
 * The list of supported protocols can be found in . Some protocols (also referred to as
 * wrappers) support context
 * and/or &php.ini; options. Refer to the specific page for the
 * protocol in use for a list of options which can be set. (e.g.
 * &php.ini; value user_agent used by the
 * http wrapper).
 * </p>
 * <p>
 * On the Windows platform, be careful to escape any backslashes
 * used in the path to the file, or use forward slashes.
 * ]]>
 * </p>
 * @param string $mode <p>
 * The mode parameter specifies the type of access
 * you require to the stream. It may be any of the following:
 * <table>
 * A list of possible modes for fopen
 * using mode
 * <tr valign="top">
 * <td>mode</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>'r'</td>
 * <td>
 * Open for reading only; place the file pointer at the
 * beginning of the file.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>'r+'</td>
 * <td>
 * Open for reading and writing; place the file pointer at
 * the beginning of the file.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>'w'</td>
 * <td>
 * Open for writing only; place the file pointer at the
 * beginning of the file and truncate the file to zero length.
 * If the file does not exist, attempt to create it.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>'w+'</td>
 * <td>
 * Open for reading and writing; place the file pointer at
 * the beginning of the file and truncate the file to zero
 * length. If the file does not exist, attempt to create it.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>'a'</td>
 * <td>
 * Open for writing only; place the file pointer at the end of
 * the file. If the file does not exist, attempt to create it.
 * In this mode, fseek only affects
 * the reading position, writes are always appended.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>'a+'</td>
 * <td>
 * Open for reading and writing; place the file pointer at
 * the end of the file. If the file does not exist, attempt to
 * create it. In this mode, fseek only affects
 * the reading position, writes are always appended.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>'x'</td>
 * <td>
 * Create and open for writing only; place the file pointer at the
 * beginning of the file. If the file already exists, the
 * fopen call will fail by returning false and
 * generating an error of level E_WARNING. If
 * the file does not exist, attempt to create it. This is equivalent
 * to specifying O_EXCL|O_CREAT flags for the
 * underlying open(2) system call. 
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>'x+'</td>
 * <td>
 * Create and open for reading and writing; otherwise it has the
 * same behavior as 'x'.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>'c'</td>
 * <td>
 * Open the file for writing only. If the file does not exist, it is
 * created. If it exists, it is neither truncated (as opposed to
 * 'w'), nor the call to this function fails (as is
 * the case with 'x'). The file pointer is
 * positioned on the beginning of the file. This may be useful if it's
 * desired to get an advisory lock (see flock)
 * before attempting to modify the file, as using
 * 'w' could truncate the file before the lock
 * was obtained (if truncation is desired,
 * ftruncate can be used after the lock is
 * requested).
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>'c+'</td>
 * <td>
 * Open the file for reading and writing; otherwise it has the same
 * behavior as 'c'.
 * </td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * Different operating system families have different line-ending
 * conventions. When you write a text file and want to insert a line
 * break, you need to use the correct line-ending character(s) for your
 * operating system. Unix based systems use \n as the
 * line ending character, Windows based systems use \r\n
 * as the line ending characters and Macintosh based systems use
 * \r as the line ending character.
 * </p>
 * <p>
 * If you use the wrong line ending characters when writing your files, you
 * might find that other applications that open those files will "look
 * funny".
 * </p>
 * <p>
 * Windows offers a text-mode translation flag ('t')
 * which will transparently translate \n to
 * \r\n when working with the file. In contrast, you
 * can also use 'b' to force binary mode, which will not
 * translate your data. To use these flags, specify either
 * 'b' or 't' as the last character
 * of the mode parameter.
 * </p>
 * <p>
 * The default translation mode depends on the SAPI and version of PHP that
 * you are using, so you are encouraged to always specify the appropriate
 * flag for portability reasons. You should use the 't'
 * mode if you are working with plain-text files and you use
 * \n to delimit your line endings in your script, but
 * expect your files to be readable with applications such as notepad. You
 * should use the 'b' in all other cases.
 * </p>
 * <p>
 * If you do not specify the 'b' flag when working with binary files, you
 * may experience strange problems with your data, including broken image
 * files and strange problems with \r\n characters.
 * </p>
 * <p>
 * For portability, it is strongly recommended that you always
 * use the 'b' flag when opening files with fopen.
 * </p>
 * <p>
 * Again, for portability, it is also strongly recommended that
 * you re-write code that uses or relies upon the 't'
 * mode so that it uses the correct line endings and
 * 'b' mode instead.
 * </p>
 * @param bool $use_include_path [optional] <p>
 * The optional third use_include_path parameter
 * can be set to '1' or true if you want to search for the file in the
 * include_path, too.
 * </p>
 * @param resource $context [optional] &note.context-support;
 * @return resource a file pointer resource on success, or false on error.
 */
function fopen ($filename, $mode, $use_include_path = null, $context = null) {}

/**
 * Output all remaining data on a file pointer
 * @link http://www.php.net/manual/en/function.fpassthru.php
 * @param resource $handle &fs.validfp.all;
 * @return int If an error occurs, fpassthru returns
 * false. Otherwise, fpassthru returns
 * the number of characters read from handle
 * and passed through to the output.
 */
function fpassthru ($handle) {}

/**
 * Truncates a file to a given length
 * @link http://www.php.net/manual/en/function.ftruncate.php
 * @param resource $handle <p>
 * The file pointer.
 * </p>
 * <p>
 * The handle must be open for writing.
 * </p>
 * @param int $size <p>
 * The size to truncate to.
 * </p>
 * <p>
 * If size is larger than the file then the file
 * is extended with null bytes.
 * </p>
 * <p>
 * If size is smaller than the file then the file
 * is truncated to that size.
 * </p>
 * @return bool true on success or false on failure
 */
function ftruncate ($handle, $size) {}

/**
 * Gets information about a file using an open file pointer
 * @link http://www.php.net/manual/en/function.fstat.php
 * @param resource $handle &fs.file.pointer;
 * @return array an array with the statistics of the file; the format of the array
 * is described in detail on the stat manual page.
 */
function fstat ($handle) {}

/**
 * Seeks on a file pointer
 * @link http://www.php.net/manual/en/function.fseek.php
 * @param resource $handle &fs.file.pointer;
 * @param int $offset <p>
 * The offset.
 * </p>
 * <p>
 * To move to a position before the end-of-file, you need to pass
 * a negative value in offset and
 * set whence
 * to SEEK_END.
 * </p>
 * @param int $whence [optional] <p>
 * whence values are:
 * SEEK_SET - Set position equal to offset bytes.
 * SEEK_CUR - Set position to current location plus offset.
 * SEEK_END - Set position to end-of-file plus offset.
 * </p>
 * @return int Upon success, returns 0; otherwise, returns -1.
 */
function fseek ($handle, $offset, $whence = null) {}

/**
 * Returns the current position of the file read/write pointer
 * @link http://www.php.net/manual/en/function.ftell.php
 * @param resource $handle <p>
 * The file pointer must be valid, and must point to a file successfully
 * opened by fopen or popen.
 * ftell gives undefined results for append-only streams
 * (opened with "a" flag).
 * </p>
 * @return int the position of the file pointer referenced by
 * handle as an integer; i.e., its offset into the file stream.
 * </p>
 * <p>
 * If an error occurs, returns false.
 */
function ftell ($handle) {}

/**
 * Flushes the output to a file
 * @link http://www.php.net/manual/en/function.fflush.php
 * @param resource $handle &fs.validfp.all;
 * @return bool true on success or false on failure
 */
function fflush ($handle) {}

/**
 * Binary-safe file write
 * @link http://www.php.net/manual/en/function.fwrite.php
 * @param resource $handle &fs.file.pointer;
 * @param string $string <p>
 * The string that is to be written.
 * </p>
 * @param int $length [optional] <p>
 * If the length argument is given, writing will
 * stop after length bytes have been written or
 * the end of string is reached, whichever comes
 * first.
 * </p>
 * <p>
 * Note that if the length argument is given,
 * then the magic_quotes_runtime
 * configuration option will be ignored and no slashes will be
 * stripped from string.
 * </p>
 * @return int 
 */
function fwrite ($handle, $string, $length = null) {}

/**
 * &Alias; <function>fwrite</function>
 * @link http://www.php.net/manual/en/function.fputs.php
 * @param $fp
 * @param $str
 * @param $length [optional]
 */
function fputs ($fp, $str, $length = null) {}

/**
 * Makes directory
 * @link http://www.php.net/manual/en/function.mkdir.php
 * @param string $pathname <p>
 * The directory path.
 * </p>
 * @param int $mode [optional] <p>
 * The mode is 0777 by default, which means the widest possible
 * access. For more information on modes, read the details
 * on the chmod page.
 * </p>
 * <p>
 * mode is ignored on Windows.
 * </p>
 * <p>
 * Note that you probably want to specify the mode as an octal number,
 * which means it should have a leading zero. The mode is also modified
 * by the current umask, which you can change using
 * umask.
 * </p>
 * @param bool $recursive [optional] <p>
 * Allows the creation of nested directories specified in the 
 * pathname.
 * </p>
 * @param resource $context [optional] &note.context-support;
 * @return bool true on success or false on failure
 */
function mkdir ($pathname, $mode = null, $recursive = null, $context = null) {}

/**
 * Renames a file or directory
 * @link http://www.php.net/manual/en/function.rename.php
 * @param string $oldname <p>
 * </p>
 * <p>
 * The old name. The wrapper used in oldname
 * must match the wrapper used in
 * newname.
 * </p>
 * @param string $newname <p>
 * The new name.
 * </p>
 * @param resource $context [optional] &note.context-support;
 * @return bool true on success or false on failure
 */
function rename ($oldname, $newname, $context = null) {}

/**
 * Copies file
 * @link http://www.php.net/manual/en/function.copy.php
 * @param string $source <p>
 * Path to the source file.
 * </p>
 * @param string $dest <p>
 * The destination path. If dest is a URL, the
 * copy operation may fail if the wrapper does not support overwriting of
 * existing files.
 * </p>
 * <p>
 * If the destination file already exists, it will be overwritten.
 * </p>
 * @param resource $context [optional] <p>
 * A valid context resource created with 
 * stream_context_create.
 * </p>
 * @return bool true on success or false on failure
 */
function copy ($source, $dest, $context = null) {}

/**
 * Create file with unique file name
 * @link http://www.php.net/manual/en/function.tempnam.php
 * @param string $dir <p>
 * The directory where the temporary filename will be created.
 * </p>
 * @param string $prefix <p>
 * The prefix of the generated temporary filename.
 * </p>
 * Windows uses only the first three characters of prefix.
 * @return string the new temporary filename (with path), or false on
 * failure.
 */
function tempnam ($dir, $prefix) {}

/**
 * Creates a temporary file
 * @link http://www.php.net/manual/en/function.tmpfile.php
 * @return resource a file handle, similar to the one returned by
 * fopen, for the new file or false on failure.
 */
function tmpfile () {}

/**
 * Reads entire file into an array
 * @link http://www.php.net/manual/en/function.file.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * &tip.fopen-wrapper;
 * @param int $flags [optional] <p>
 * The optional parameter flags can be one, or
 * more, of the following constants:
 * FILE_USE_INCLUDE_PATH
 * Search for the file in the include_path.
 * @param resource $context [optional] <p>
 * A context resource created with the 
 * stream_context_create function.
 * </p>
 * <p>
 * &note.context-support;
 * </p>
 * @return array the file in an array. Each element of the array corresponds to a
 * line in the file, with the newline still attached. Upon failure,
 * file returns false.
 * </p>
 * <p>
 * Each line in the resulting array will include the line ending, unless
 * FILE_IGNORE_NEW_LINES is used, so you still need to
 * use rtrim if you do not want the line ending
 * present.
 */
function file ($filename, $flags = null, $context = null) {}

/**
 * Reads entire file into a string
 * @link http://www.php.net/manual/en/function.file-get-contents.php
 * @param string $filename <p>
 * Name of the file to read.
 * </p>
 * @param bool $use_include_path [optional] <p>
 * As of PHP 5 the FILE_USE_INCLUDE_PATH constant can be used
 * to trigger include path
 * search.
 * </p>
 * @param resource $context [optional] <p>
 * A valid context resource created with 
 * stream_context_create. If you don't need to use a
 * custom context, you can skip this parameter by &null;.
 * </p>
 * @param int $offset [optional] <p>
 * The offset where the reading starts on the original stream.
 * </p>
 * <p>
 * Seeking (offset) is not supported with remote files.
 * Attempting to seek on non-local files may work with small offsets, but this
 * is unpredictable because it works on the buffered stream.
 * </p>
 * @param int $maxlen [optional] <p>
 * Maximum length of data read. The default is to read until end
 * of file is reached. Note that this parameter is applied to the 
 * stream processed by the filters.
 * </p>
 * @return string The function returns the read data or false on failure.
 */
function file_get_contents ($filename, $use_include_path = null, $context = null, $offset = null, $maxlen = null) {}

/**
 * Write a string to a file
 * @link http://www.php.net/manual/en/function.file-put-contents.php
 * @param string $filename <p>
 * Path to the file where to write the data.
 * </p>
 * @param mixed $data <p>
 * The data to write. Can be either a string, an
 * array or a stream resource.
 * </p>
 * <p>
 * If data is a stream resource, the
 * remaining buffer of that stream will be copied to the specified file.
 * This is similar with using stream_copy_to_stream.
 * </p>
 * <p>
 * You can also specify the data parameter as a single
 * dimension array. This is equivalent to
 * file_put_contents($filename, implode('', $array)).
 * </p>
 * @param int $flags [optional] <p>
 * The value of flags can be any combination of 
 * the following flags, joined with the binary OR (|)
 * operator.
 * </p>
 * <p>
 * <table>
 * Available flags
 * <tr valign="top">
 * <td>Flag</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>
 * FILE_USE_INCLUDE_PATH
 * </td>
 * <td>
 * Search for filename in the include directory.
 * See include_path for more
 * information.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>
 * FILE_APPEND
 * </td>
 * <td>
 * If file filename already exists, append 
 * the data to the file instead of overwriting it.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>
 * LOCK_EX
 * </td>
 * <td>
 * Acquire an exclusive lock on the file while proceeding to the 
 * writing. In other words, a flock call happens
 * between the fopen call and the 
 * fwrite call. This is not identical to an 
 * fopen call with mode "x".
 * </td>
 * </tr>
 * </table>
 * </p>
 * @param resource $context [optional] <p>
 * A valid context resource created with 
 * stream_context_create.
 * </p>
 * @return int This function returns the number of bytes that were written to the file, or
 * false on failure.
 */
function file_put_contents ($filename, $data, $flags = null, $context = null) {}

/**
 * Runs the equivalent of the select() system call on the given
   arrays of streams with a timeout specified by tv_sec and tv_usec
 * @link http://www.php.net/manual/en/function.stream-select.php
 * @param array $read <p>
 * The streams listed in the read array will be watched to
 * see if characters become available for reading (more precisely, to see if
 * a read will not block - in particular, a stream resource is also ready on
 * end-of-file, in which case an fread will return
 * a zero length string).
 * </p>
 * @param array $write <p>
 * The streams listed in the write array will be
 * watched to see if a write will not block.
 * </p>
 * @param array $except <p>
 * The streams listed in the except array will be
 * watched for high priority exceptional ("out-of-band") data arriving.
 * </p>
 * <p>
 * When stream_select returns, the arrays
 * read, write and
 * except are modified to indicate which stream
 * resource(s) actually changed status.
 * </p>
 * You do not need to pass every array to
 * stream_select. You can leave it out and use an
 * empty array or &null; instead. Also do not forget that those arrays are
 * passed by reference and will be modified after
 * stream_select returns.
 * @param int $tv_sec <p>
 * The tv_sec and tv_usec
 * together form the timeout parameter,
 * tv_sec specifies the number of seconds while
 * tv_usec the number of microseconds.
 * The timeout is an upper bound on the amount of time
 * that stream_select will wait before it returns.
 * If tv_sec and tv_usec are
 * both set to 0, stream_select will
 * not wait for data - instead it will return immediately, indicating the
 * current status of the streams.
 * </p>
 * <p>
 * If tv_sec is &null; stream_select
 * can block indefinitely, returning only when an event on one of the
 * watched streams occurs (or if a signal interrupts the system call).
 * </p>
 * <p>
 * Using a timeout value of 0 allows you to
 * instantaneously poll the status of the streams, however, it is NOT a
 * good idea to use a 0 timeout value in a loop as it
 * will cause your script to consume too much CPU time.
 * </p>
 * <p>
 * It is much better to specify a timeout value of a few seconds, although
 * if you need to be checking and running other code concurrently, using a
 * timeout value of at least 200000 microseconds will
 * help reduce the CPU usage of your script.
 * </p>
 * <p>
 * Remember that the timeout value is the maximum time that will elapse;
 * stream_select will return as soon as the
 * requested streams are ready for use.
 * </p>
 * @param int $tv_usec [optional] <p>
 * See tv_sec description.
 * </p>
 * @return int On success stream_select returns the number of
 * stream resources contained in the modified arrays, which may be zero if
 * the timeout expires before anything interesting happens. On error false
 * is returned and a warning raised (this can happen if the system call is
 * interrupted by an incoming signal).
 */
function stream_select (array &$read, array &$write, array &$except, $tv_sec, $tv_usec = null) {}

/**
 * Creates a stream context
 * @link http://www.php.net/manual/en/function.stream-context-create.php
 * @param array $options [optional] <p>
 * Must be an associative array of associative arrays in the format
 * $arr['wrapper']['option'] = $value.
 * </p>
 * <p>
 * Default to an empty array.
 * </p>
 * @param array $params [optional] <p>
 * Must be an associative array in the format
 * $arr['parameter'] = $value.
 * Refer to context parameters for
 * a listing of standard stream parameters.
 * </p>
 * @return resource A stream context resource.
 */
function stream_context_create (array $options = null, array $params = null) {}

/**
 * Set parameters for a stream/wrapper/context
 * @link http://www.php.net/manual/en/function.stream-context-set-params.php
 * @param resource $stream_or_context <p>
 * The stream or context to apply the parameters too.
 * </p>
 * @param array $params <p>
 * An array of parameters to set.
 * </p>
 * <p>
 * params should be an associative array of the structure:
 * $params['paramname'] = "paramvalue";.
 * </p>
 * @return bool true on success or false on failure
 */
function stream_context_set_params ($stream_or_context, array $params) {}

/**
 * Retrieves parameters from a context
 * @link http://www.php.net/manual/en/function.stream-context-get-params.php
 * @param resource $stream_or_context <p>
 * A stream resource or a
 * context resource
 * </p>
 * @return array an associate array containing all context options and parameters.
 */
function stream_context_get_params ($stream_or_context) {}

/**
 * Sets an option for a stream/wrapper/context
 * @link http://www.php.net/manual/en/function.stream-context-set-option.php
 * @param resource $stream_or_context <p>
 * The stream or context resource to apply the options to.
 * </p>
 * @param string $wrapper 
 * @param string $option 
 * @param mixed $value 
 * @return bool true on success or false on failure
 */
function stream_context_set_option ($stream_or_context, $wrapper, $option, $value) {}

/**
 * Retrieve options for a stream/wrapper/context
 * @link http://www.php.net/manual/en/function.stream-context-get-options.php
 * @param resource $stream_or_context <p>
 * The stream or context to get options from
 * </p>
 * @return array an associative array with the options.
 */
function stream_context_get_options ($stream_or_context) {}

/**
 * Retrieve the default stream context
 * @link http://www.php.net/manual/en/function.stream-context-get-default.php
 * @param array $options [optional] options must be an associative
 * array of associative arrays in the format
 * $arr['wrapper']['option'] = $value.
 * <p>
 * As of PHP 5.3.0, the stream_context_set_default function
 * can be used to set the default context.
 * </p>
 * @return resource A stream context resource.
 */
function stream_context_get_default (array $options = null) {}

/**
 * Set the default stream context
 * @link http://www.php.net/manual/en/function.stream-context-set-default.php
 * @param array $options <p>
 * The options to set for the default context.
 * </p>
 * <p>
 * options must be an associative
 * array of associative arrays in the format
 * $arr['wrapper']['option'] = $value.
 * </p>
 * @return resource the default stream context.
 */
function stream_context_set_default (array $options) {}

/**
 * Attach a filter to a stream
 * @link http://www.php.net/manual/en/function.stream-filter-prepend.php
 * @param resource $stream <p>
 * The target stream.
 * </p>
 * @param string $filtername <p>
 * The filter name.
 * </p>
 * @param int $read_write [optional] <p>
 * By default, stream_filter_prepend will
 * attach the filter to the read filter chain
 * if the file was opened for reading (i.e. File Mode:
 * r, and/or +). The filter
 * will also be attached to the write filter chain
 * if the file was opened for writing (i.e. File Mode:
 * w, a, and/or +).
 * STREAM_FILTER_READ,
 * STREAM_FILTER_WRITE, and/or
 * STREAM_FILTER_ALL can also be passed to the
 * read_write parameter to override this behavior.
 * See stream_filter_append for an example of
 * using this parameter.
 * </p>
 * @param mixed $params [optional] <p>
 * This filter will be added with the specified params
 * to the beginning of the list and will therefore be
 * called first during stream operations. To add a filter to the end of the
 * list, use stream_filter_append.
 * </p>
 * @return resource a resource which can be used to refer to this filter
 * instance during a call to stream_filter_remove.
 */
function stream_filter_prepend ($stream, $filtername, $read_write = null, $params = null) {}

/**
 * Attach a filter to a stream
 * @link http://www.php.net/manual/en/function.stream-filter-append.php
 * @param resource $stream <p>
 * The target stream.
 * </p>
 * @param string $filtername <p>
 * The filter name.
 * </p>
 * @param int $read_write [optional] <p>
 * By default, stream_filter_append will
 * attach the filter to the read filter chain
 * if the file was opened for reading (i.e. File Mode:
 * r, and/or +). The filter
 * will also be attached to the write filter chain
 * if the file was opened for writing (i.e. File Mode:
 * w, a, and/or +).
 * STREAM_FILTER_READ,
 * STREAM_FILTER_WRITE, and/or
 * STREAM_FILTER_ALL can also be passed to the
 * read_write parameter to override this behavior.
 * </p>
 * @param mixed $params [optional] <p>
 * This filter will be added with the specified 
 * params to the end of
 * the list and will therefore be called last during stream operations.
 * To add a filter to the beginning of the list, use
 * stream_filter_prepend.
 * </p>
 * @return resource a resource which can be used to refer to this filter
 * instance during a call to stream_filter_remove.
 */
function stream_filter_append ($stream, $filtername, $read_write = null, $params = null) {}

/**
 * Remove a filter from a stream
 * @link http://www.php.net/manual/en/function.stream-filter-remove.php
 * @param resource $stream_filter <p>
 * The stream filter to be removed.
 * </p>
 * @return bool true on success or false on failure
 */
function stream_filter_remove ($stream_filter) {}

/**
 * Open Internet or Unix domain socket connection
 * @link http://www.php.net/manual/en/function.stream-socket-client.php
 * @param string $remote_socket <p>
 * Address to the socket to connect to.
 * </p>
 * @param int $errno [optional] <p>
 * Will be set to the system level error number if connection fails.
 * </p>
 * @param string $errstr [optional] <p>
 * Will be set to the system level error message if the connection fails.
 * </p>
 * @param float $timeout [optional] <p>
 * Number of seconds until the connect() system call
 * should timeout.
 * This parameter only applies when not making asynchronous
 * connection attempts.
 * <p>
 * To set a timeout for reading/writing data over the socket, use the
 * stream_set_timeout, as the
 * timeout only applies while making connecting
 * the socket.
 * </p>
 * </p>
 * @param int $flags [optional] <p>
 * Bitmask field which may be set to any combination of connection flags.
 * Currently the select of connection flags is limited to
 * STREAM_CLIENT_CONNECT (default),
 * STREAM_CLIENT_ASYNC_CONNECT and
 * STREAM_CLIENT_PERSISTENT.
 * </p>
 * @param resource $context [optional] <p>
 * A valid context resource created with stream_context_create.
 * </p>
 * @return resource On success a stream resource is returned which may
 * be used together with the other file functions (such as
 * fgets, fgetss,
 * fwrite, fclose, and
 * feof), false on failure.
 */
function stream_socket_client ($remote_socket, &$errno = null, &$errstr = null, $timeout = null, $flags = null, $context = null) {}

/**
 * Create an Internet or Unix domain server socket
 * @link http://www.php.net/manual/en/function.stream-socket-server.php
 * @param string $local_socket <p>
 * The type of socket created is determined by the transport specified
 * using standard URL formatting: transport://target.
 * </p>
 * <p>
 * For Internet Domain sockets (AF_INET) such as TCP and UDP, the
 * target portion of the 
 * remote_socket parameter should consist of a
 * hostname or IP address followed by a colon and a port number. For
 * Unix domain sockets, the target portion should
 * point to the socket file on the filesystem.
 * </p>
 * <p>
 * Depending on the environment, Unix domain sockets may not be available.
 * A list of available transports can be retrieved using
 * stream_get_transports. See
 * for a list of bulitin transports.
 * </p>
 * @param int $errno [optional] <p>
 * If the optional errno and errstr
 * arguments are present they will be set to indicate the actual system
 * level error that occurred in the system-level socket(),
 * bind(), and listen() calls. If
 * the value returned in errno is 
 * 0 and the function returned false, it is an
 * indication that the error occurred before the bind()
 * call. This is most likely due to a problem initializing the socket. 
 * Note that the errno and
 * errstr arguments will always be passed by reference.
 * </p>
 * @param string $errstr [optional] <p>
 * See errno description.
 * </p>
 * @param int $flags [optional] <p>
 * A bitmask field which may be set to any combination of socket creation
 * flags.
 * </p>
 * <p>
 * For UDP sockets, you must use STREAM_SERVER_BIND as
 * the flags parameter.
 * </p>
 * @param resource $context [optional] <p>
 * </p>
 * @return resource the created stream, or false on error.
 */
function stream_socket_server ($local_socket, &$errno = null, &$errstr = null, $flags = null, $context = null) {}

/**
 * Accept a connection on a socket created by <function>stream_socket_server</function>
 * @link http://www.php.net/manual/en/function.stream-socket-accept.php
 * @param resource $server_socket <p>
 * The server socket to accept a connection from.
 * </p>
 * @param float $timeout [optional] <p>
 * Override the default socket accept timeout. Time should be given in
 * seconds.
 * </p>
 * @param string $peername [optional] <p>
 * Will be set to the name (address) of the client which connected, if
 * included and available from the selected transport.
 * </p>
 * <p>
 * Can also be determined later using
 * stream_socket_get_name.
 * </p>
 * @return resource a stream to the accepted socket connection or false on failure.
 */
function stream_socket_accept ($server_socket, $timeout = null, &$peername = null) {}

/**
 * Retrieve the name of the local or remote sockets
 * @link http://www.php.net/manual/en/function.stream-socket-get-name.php
 * @param resource $handle <p>
 * The socket to get the name of.
 * </p>
 * @param bool $want_peer <p>
 * If set to true the remote socket name will be returned, if set
 * to false the local socket name will be returned.
 * </p>
 * @return string The name of the socket.
 */
function stream_socket_get_name ($handle, $want_peer) {}

/**
 * Receives data from a socket, connected or not
 * @link http://www.php.net/manual/en/function.stream-socket-recvfrom.php
 * @param resource $socket <p>
 * The remote socket.
 * </p>
 * @param int $length <p>
 * The number of bytes to receive from the socket.
 * </p>
 * @param int $flags [optional] <p>
 * The value of flags can be any combination
 * of the following:
 * <table>
 * Possible values for flags
 * <tr valign="top">
 * <td>STREAM_OOB</td>
 * <td>
 * Process OOB (out-of-band) data.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>STREAM_PEEK</td>
 * <td>
 * Retrieve data from the socket, but do not consume the buffer.
 * Subsequent calls to fread or
 * stream_socket_recvfrom will see
 * the same data.
 * </td>
 * </tr>
 * </table>
 * </p>
 * @param string $address [optional] <p>
 * If address is provided it will be populated with
 * the address of the remote socket.
 * </p>
 * @return string the read data, as a string
 */
function stream_socket_recvfrom ($socket, $length, $flags = null, &$address = null) {}

/**
 * Sends a message to a socket, whether it is connected or not
 * @link http://www.php.net/manual/en/function.stream-socket-sendto.php
 * @param resource $socket <p>
 * The socket to send data to.
 * </p>
 * @param string $data <p>
 * The data to be sent.
 * </p>
 * @param int $flags [optional] <p>
 * The value of flags can be any combination
 * of the following:
 * <table>
 * possible values for flags
 * <tr valign="top">
 * <td>STREAM_OOB</td>
 * <td>
 * Process OOB (out-of-band) data.
 * </td>
 * </tr>
 * </table>
 * </p>
 * @param string $address [optional] <p>
 * The address specified when the socket stream was created will be used
 * unless an alternate address is specified in address.
 * </p>
 * <p>
 * If specified, it must be in dotted quad (or [ipv6]) format.
 * </p>
 * @return int a result code, as an integer.
 */
function stream_socket_sendto ($socket, $data, $flags = null, $address = null) {}

/**
 * Turns encryption on/off on an already connected socket
 * @link http://www.php.net/manual/en/function.stream-socket-enable-crypto.php
 * @param resource $stream <p>
 * The stream resource.
 * </p>
 * @param bool $enable <p>
 * Enable/disable cryptography on the stream.
 * </p>
 * @param int $crypto_type [optional] <p>
 * Setup encryption on the stream.
 * Valid methods are
 * STREAM_CRYPTO_METHOD_SSLv2_CLIENT
 * @param resource $session_stream [optional] <p>
 * Seed the stream with settings from session_stream.
 * </p>
 * @return mixed true on success, false if negotiation has failed or
 * 0 if there isn't enough data and you should try again
 * (only for non-blocking sockets).
 */
function stream_socket_enable_crypto ($stream, $enable, $crypto_type = null, $session_stream = null) {}

/**
 * Shutdown a full-duplex connection
 * @link http://www.php.net/manual/en/function.stream-socket-shutdown.php
 * @param resource $stream <p>
 * An open stream (opened with stream_socket_client,
 * for example)
 * </p>
 * @param int $how <p>
 * One of the following constants: STREAM_SHUT_RD
 * (disable further receptions), STREAM_SHUT_WR
 * (disable further transmissions) or
 * STREAM_SHUT_RDWR (disable further receptions and
 * transmissions).
 * </p>
 * @return bool true on success or false on failure
 */
function stream_socket_shutdown ($stream, $how) {}

/**
 * Creates a pair of connected, indistinguishable socket streams
 * @link http://www.php.net/manual/en/function.stream-socket-pair.php
 * @param int $domain <p>
 * The protocol family to be used: STREAM_PF_INET,
 * STREAM_PF_INET6 or
 * STREAM_PF_UNIX
 * </p>
 * @param int $type <p>
 * The type of communication to be used:
 * STREAM_SOCK_DGRAM,
 * STREAM_SOCK_RAW,
 * STREAM_SOCK_RDM,
 * STREAM_SOCK_SEQPACKET or
 * STREAM_SOCK_STREAM
 * </p>
 * @param int $protocol <p>
 * The protocol to be used: STREAM_IPPROTO_ICMP,
 * STREAM_IPPROTO_IP,
 * STREAM_IPPROTO_RAW,
 * STREAM_IPPROTO_TCP or
 * STREAM_IPPROTO_UDP 
 * </p>
 * @return array an array with the two socket resources on success, or
 * false on failure.
 */
function stream_socket_pair ($domain, $type, $protocol) {}

/**
 * Copies data from one stream to another
 * @link http://www.php.net/manual/en/function.stream-copy-to-stream.php
 * @param resource $source <p>
 * The source stream
 * </p>
 * @param resource $dest <p>
 * The destination stream
 * </p>
 * @param int $maxlength [optional] <p>
 * Maximum bytes to copy
 * </p>
 * @param int $offset [optional] <p>
 * The offset where to start to copy data
 * </p>
 * @return int the total count of bytes copied.
 */
function stream_copy_to_stream ($source, $dest, $maxlength = null, $offset = null) {}

/**
 * Reads remainder of a stream into a string
 * @link http://www.php.net/manual/en/function.stream-get-contents.php
 * @param resource $handle <p>
 * A stream resource (e.g. returned from fopen)
 * </p>
 * @param int $maxlength [optional] <p>
 * The maximum bytes to read. Defaults to -1 (read all the remaining
 * buffer).
 * </p>
 * @param int $offset [optional] <p>
 * Seek to the specified offset before reading. If this number is negative,
 * no seeking will occur and reading will start from the current position.
 * </p>
 * @return string a string or false on failure.
 */
function stream_get_contents ($handle, $maxlength = null, $offset = null) {}

/**
 * Tells whether the stream supports locking.
 * @link http://www.php.net/manual/en/function.stream-supports-lock.php
 * @param resource $stream <p>
 * The stream to check.
 * </p>
 * @return bool true on success or false on failure
 */
function stream_supports_lock ($stream) {}

/**
 * Gets line from file pointer and parse for CSV fields
 * @link http://www.php.net/manual/en/function.fgetcsv.php
 * @param resource $handle <p>
 * A valid file pointer to a file successfully opened by
 * fopen, popen, or
 * fsockopen.
 * </p>
 * @param int $length [optional] <p>
 * Must be greater than the longest line (in characters) to be found in
 * the CSV file (allowing for trailing line-end characters). It became
 * optional in PHP 5. Omitting this parameter (or setting it to 0 in PHP
 * 5.1.0 and later) the maximum line length is not limited, which is
 * slightly slower.
 * </p>
 * @param string $delimiter [optional] <p>
 * The optional delimiter parameter sets the field delimiter (one character only).
 * </p>
 * @param string $enclosure [optional] <p>
 * The optional enclosure parameter sets the field enclosure character (one character only).
 * </p>
 * @param string $escape [optional] <p>
 * The optional escape parameter sets the escape character (one character only).
 * </p>
 * @return array an indexed array containing the fields read.
 * </p>
 * <p>
 * A blank line in a CSV file will be returned as an array
 * comprising a single null field, and will not be treated
 * as an error.
 * </p>
 * &note.line-endings;
 * <p>
 * fgetcsv returns &null; if an invalid
 * handle is supplied or false on other errors,
 * including end of file.
 */
function fgetcsv ($handle, $length = null, $delimiter = null, $enclosure = null, $escape = null) {}

/**
 * Format line as CSV and write to file pointer
 * @link http://www.php.net/manual/en/function.fputcsv.php
 * @param resource $handle &fs.validfp.all;
 * @param array $fields <p>
 * An array of values.
 * </p>
 * @param string $delimiter [optional] <p>
 * The optional delimiter parameter sets the field
 * delimiter (one character only).
 * </p>
 * @param string $enclosure [optional] <p>
 * The optional enclosure parameter sets the field
 * enclosure (one character only).
 * </p>
 * @param string $escape_char [optional] <p>
 * The optional escape_char parameter sets the
 * escape character (one character only).
 * </p>
 * @return int the length of the written string or false on failure.
 */
function fputcsv ($handle, array $fields, $delimiter = null, $enclosure = null, $escape_char = null) {}

/**
 * Portable advisory file locking
 * @link http://www.php.net/manual/en/function.flock.php
 * @param resource $handle &fs.file.pointer;
 * @param int $operation <p>
 * operation is one of the following:
 * LOCK_SH to acquire a shared lock (reader).
 * @param int $wouldblock [optional] <p>
 * The optional third argument is set to 1 if the lock would block
 * (EWOULDBLOCK errno condition).
 * </p>
 * @return bool true on success or false on failure
 */
function flock ($handle, $operation, &$wouldblock = null) {}

/**
 * Extracts all meta tag content attributes from a file and returns an array
 * @link http://www.php.net/manual/en/function.get-meta-tags.php
 * @param string $filename <p>
 * The path to the HTML file, as a string. This can be a local file or an
 * URL.
 * </p>
 * <p>
 * What get_meta_tags parses
 * ]]>
 * (pay attention to line endings - PHP uses a native function to
 * parse the input, so a Mac file won't work on Unix).
 * </p>
 * @param bool $use_include_path [optional] <p>
 * Setting use_include_path to true will result
 * in PHP trying to open the file along the standard include path as per
 * the include_path directive.
 * This is used for local files, not URLs.
 * </p>
 * @return array an array with all the parsed meta tags.
 * </p>
 * <p>
 * The value of the name property becomes the key, the value of the content
 * property becomes the value of the returned array, so you can easily use
 * standard array functions to traverse it or access single values. 
 * Special characters in the value of the name property are substituted with
 * '_', the rest is converted to lower case. If two meta tags have the same
 * name, only the last one is returned.
 */
function get_meta_tags ($filename, $use_include_path = null) {}

/**
 * Set read file buffering on the given stream
 * @link http://www.php.net/manual/en/function.stream-set-read-buffer.php
 * @param resource $stream <p>
 * The file pointer.
 * </p>
 * @param int $buffer <p>
 * The number of bytes to buffer. If buffer
 * is 0 then read operations are unbuffered. This ensures that all reads
 * with fread are completed before other processes are
 * allowed to read from that input stream.
 * </p>
 * @return int 0 on success, or another value if the request
 * cannot be honored.
 */
function stream_set_read_buffer ($stream, $buffer) {}

/**
 * Sets write file buffering on the given stream
 * @link http://www.php.net/manual/en/function.stream-set-write-buffer.php
 * @param resource $stream <p>
 * The file pointer.
 * </p>
 * @param int $buffer <p>
 * The number of bytes to buffer. If buffer
 * is 0 then write operations are unbuffered. This ensures that all writes
 * with fwrite are completed before other processes are
 * allowed to write to that output stream.
 * </p>
 * @return int 0 on success, or another value if the request cannot be honored.
 */
function stream_set_write_buffer ($stream, $buffer) {}

/**
 * &Alias; <function>stream_set_write_buffer</function>
 * @link http://www.php.net/manual/en/function.set-file-buffer.php
 * @param $fp
 * @param $buffer
 */
function set_file_buffer ($fp, $buffer) {}

/**
 * Set the stream chunk size
 * @link http://www.php.net/manual/en/function.stream-set-chunk-size.php
 * @param resource $fp <p>
 * The target stream.
 * </p>
 * @param int $chunk_size <p>
 * The desired new chunk size.
 * </p>
 * @return int the previous chunk size on success.
 * </p>
 * <p>
 * Will return false if chunk_size is less than 1 or
 * greater than PHP_INT_MAX.
 */
function stream_set_chunk_size ($fp, $chunk_size) {}

/**
 * Set blocking/non-blocking mode on a stream
 * @link http://www.php.net/manual/en/function.stream-set-blocking.php
 * @param resource $stream <p>
 * The stream.
 * </p>
 * @param int $mode <p>
 * If mode is 0, the given stream
 * will be switched to non-blocking mode, and if 1, it
 * will be switched to blocking mode. This affects calls like
 * fgets and fread
 * that read from the stream. In non-blocking mode an
 * fgets call will always return right away
 * while in blocking mode it will wait for data to become available
 * on the stream.
 * </p>
 * @return bool true on success or false on failure
 */
function stream_set_blocking ($stream, $mode) {}

/**
 * &Alias; <function>stream_set_blocking</function>
 * @link http://www.php.net/manual/en/function.socket-set-blocking.php
 * @param $socket
 * @param $mode
 */
function socket_set_blocking ($socket, $mode) {}

/**
 * Retrieves header/meta data from streams/file pointers
 * @link http://www.php.net/manual/en/function.stream-get-meta-data.php
 * @param resource $stream <p>
 * The stream can be any stream created by fopen,
 * fsockopen and pfsockopen.
 * </p>
 * @return array The result array contains the following items:
 * </p>
 * <p>
 * timed_out (bool) - true if the stream
 * timed out while waiting for data on the last call to
 * fread or fgets.
 * </p>
 * <p>
 * blocked (bool) - true if the stream is
 * in blocking IO mode. See stream_set_blocking.
 * </p>
 * <p>
 * eof (bool) - true if the stream has reached
 * end-of-file. Note that for socket streams this member can be true
 * even when unread_bytes is non-zero. To
 * determine if there is more data to be read, use
 * feof instead of reading this item.
 * </p>
 * <p>
 * unread_bytes (int) - the number of bytes
 * currently contained in the PHP's own internal buffer.
 * </p>
 * You shouldn't use this value in a script.
 * <p>
 * stream_type (string) - a label describing
 * the underlying implementation of the stream.
 * </p>
 * <p>
 * wrapper_type (string) - a label describing
 * the protocol wrapper implementation layered over the stream.
 * See for more information about wrappers.
 * </p>
 * <p>
 * wrapper_data (mixed) - wrapper specific
 * data attached to this stream. See for
 * more information about wrappers and their wrapper data.
 * </p>
 * <p>
 * mode (string) - the type of access required for
 * this stream (see Table 1 of the fopen() reference)
 * </p>
 * <p>
 * seekable (bool) - whether the current stream can
 * be seeked.
 * </p>
 * <p>
 * uri (string) - the URI/filename associated with this
 * stream.
 */
function stream_get_meta_data ($stream) {}

/**
 * Gets line from stream resource up to a given delimiter
 * @link http://www.php.net/manual/en/function.stream-get-line.php
 * @param resource $handle <p>
 * A valid file handle.
 * </p>
 * @param int $length <p>
 * The number of bytes to read from the handle.
 * </p>
 * @param string $ending [optional] <p>
 * An optional string delimiter.
 * </p>
 * @return string a string of up to length bytes read from the file
 * pointed to by handle.
 * </p>
 * <p>
 * If an error occurs, returns false.
 */
function stream_get_line ($handle, $length, $ending = null) {}

/**
 * Register a URL wrapper implemented as a PHP class
 * @link http://www.php.net/manual/en/function.stream-wrapper-register.php
 * @param string $protocol <p>
 * The wrapper name to be registered.
 * </p>
 * @param string $classname <p>
 * The classname which implements the protocol.
 * </p>
 * @param int $flags [optional] <p>
 * Should be set to STREAM_IS_URL if
 * protocol is a URL protocol. Default is 0, local
 * stream.
 * </p>
 * @return bool true on success or false on failure
 * </p>
 * <p>
 * stream_wrapper_register will return false if the
 * protocol already has a handler.
 */
function stream_wrapper_register ($protocol, $classname, $flags = null) {}

/**
 * &Alias; <function>stream_wrapper_register</function>
 * @link http://www.php.net/manual/en/function.stream-register-wrapper.php
 * @param $protocol
 * @param $classname
 * @param $flags [optional]
 */
function stream_register_wrapper ($protocol, $classname, $flags = null) {}

/**
 * Unregister a URL wrapper
 * @link http://www.php.net/manual/en/function.stream-wrapper-unregister.php
 * @param string $protocol <p>
 * </p>
 * @return bool true on success or false on failure
 */
function stream_wrapper_unregister ($protocol) {}

/**
 * Restores a previously unregistered built-in wrapper
 * @link http://www.php.net/manual/en/function.stream-wrapper-restore.php
 * @param string $protocol <p>
 * </p>
 * @return bool true on success or false on failure
 */
function stream_wrapper_restore ($protocol) {}

/**
 * Retrieve list of registered streams
 * @link http://www.php.net/manual/en/function.stream-get-wrappers.php
 * @return array an indexed array containing the name of all stream wrappers
 * available on the running system.
 */
function stream_get_wrappers () {}

/**
 * Retrieve list of registered socket transports
 * @link http://www.php.net/manual/en/function.stream-get-transports.php
 * @return array an indexed array of socket transports names.
 */
function stream_get_transports () {}

/**
 * Resolve filename against the include path
 * @link http://www.php.net/manual/en/function.stream-resolve-include-path.php
 * @param string $filename <p>
 * The filename to resolve.
 * </p>
 * @param resource $context [optional] <p>
 * A valid context resource created with stream_context_create.
 * </p>
 * @return string a string containing the resolved absolute filename, or false on failure.
 */
function stream_resolve_include_path ($filename, $context = null) {}

/**
 * Checks if a stream is a local stream
 * @link http://www.php.net/manual/en/function.stream-is-local.php
 * @param mixed $stream_or_url <p>
 * The stream resource or URL to check.
 * </p>
 * @return bool true on success or false on failure
 */
function stream_is_local ($stream_or_url) {}

/**
 * Fetches all the headers sent by the server in response to a HTTP request
 * @link http://www.php.net/manual/en/function.get-headers.php
 * @param string $url <p>
 * The target URL.
 * </p>
 * @param int $format [optional] <p>
 * If the optional format parameter is set to non-zero,
 * get_headers parses the response and sets the 
 * array's keys.
 * </p>
 * @return array an indexed or associative array with the headers, or false on
 * failure.
 */
function get_headers ($url, $format = null) {}

/**
 * Set timeout period on a stream
 * @link http://www.php.net/manual/en/function.stream-set-timeout.php
 * @param resource $stream <p>
 * The target stream.
 * </p>
 * @param int $seconds <p>
 * The seconds part of the timeout to be set.
 * </p>
 * @param int $microseconds [optional] <p>
 * The microseconds part of the timeout to be set.
 * </p>
 * @return bool true on success or false on failure
 */
function stream_set_timeout ($stream, $seconds, $microseconds = null) {}

/**
 * &Alias; <function>stream_set_timeout</function>
 * @link http://www.php.net/manual/en/function.socket-set-timeout.php
 * @param $stream
 * @param $seconds
 * @param $microseconds [optional]
 */
function socket_set_timeout ($stream, $seconds, $microseconds = null) {}

/**
 * &Alias; <function>stream_get_meta_data</function>
 * @link http://www.php.net/manual/en/function.socket-get-status.php
 * @param $fp
 */
function socket_get_status ($fp) {}

/**
 * Returns canonicalized absolute pathname
 * @link http://www.php.net/manual/en/function.realpath.php
 * @param string $path <p>
 * The path being checked.
 * <p>
 * Whilst a path must be supplied, the value can be blank or &null;
 * In these cases, the value is interpreted as the current directory.
 * </p>
 * </p>
 * @return string the canonicalized absolute pathname on success. The resulting path 
 * will have no symbolic link, '/./' or '/../' components.
 * </p>
 * <p>
 * realpath returns false on failure, e.g. if
 * the file does not exist.
 * </p>
 * <p>
 * The running script must have executable permissions on all directories in
 * the hierarchy, otherwise realpath will return
 * false.
 * </p>
 * <p>
 * For case-insensitive filesystems realpath may or may
 * not normalize the character case.
 */
function realpath ($path) {}

/**
 * Match filename against a pattern
 * @link http://www.php.net/manual/en/function.fnmatch.php
 * @param string $pattern <p>
 * The shell wildcard pattern.
 * </p>
 * @param string $string <p>
 * The tested string. This function is especially useful for filenames,
 * but may also be used on regular strings.
 * </p>
 * <p>
 * The average user may be used to shell patterns or at least in their
 * simplest form to '?' and '*'
 * wildcards so using fnmatch instead of
 * preg_match for
 * frontend search expression input may be way more convenient for
 * non-programming users.
 * </p>
 * @param int $flags [optional] <p>
 * The value of flags can be any combination of 
 * the following flags, joined with the
 * binary OR (|) operator.
 * <table>
 * A list of possible flags for fnmatch
 * <tr valign="top">
 * <td>Flag</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>FNM_NOESCAPE</td>
 * <td>
 * Disable backslash escaping.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>FNM_PATHNAME</td>
 * <td>
 * Slash in string only matches slash in the given pattern.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>FNM_PERIOD</td>
 * <td>
 * Leading period in string must be exactly matched by period in the given pattern.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>FNM_CASEFOLD</td>
 * <td>
 * Caseless match. Part of the GNU extension.
 * </td>
 * </tr>
 * </table>
 * </p>
 * @return bool true if there is a match, false otherwise.
 */
function fnmatch ($pattern, $string, $flags = null) {}

/**
 * Open Internet or Unix domain socket connection
 * @link http://www.php.net/manual/en/function.fsockopen.php
 * @param string $hostname <p>
 * If OpenSSL support is
 * installed, you may prefix the hostname
 * with either ssl:// or tls:// to
 * use an SSL or TLS client connection over TCP/IP to connect to the
 * remote host.
 * </p>
 * @param int $port [optional] <p>
 * The port number. This can be omitted and skipped with
 * -1 for transports that do not use ports, such as
 * unix://.
 * </p>
 * @param int $errno [optional] <p>
 * If provided, holds the system level error number that occurred in the
 * system-level connect() call.
 * </p>
 * <p>
 * If the value returned in errno is
 * 0 and the function returned false, it is an
 * indication that the error occurred before the 
 * connect() call. This is most likely due to a
 * problem initializing the socket.
 * </p>
 * @param string $errstr [optional] <p>
 * The error message as a string.
 * </p>
 * @param float $timeout [optional] <p>
 * The connection timeout, in seconds.
 * </p>
 * <p>
 * If you need to set a timeout for reading/writing data over the
 * socket, use stream_set_timeout, as the 
 * timeout parameter to
 * fsockopen only applies while connecting the
 * socket.
 * </p>
 * @return resource fsockopen returns a file pointer which may be used
 * together with the other file functions (such as
 * fgets, fgetss,
 * fwrite, fclose, and
 * feof). If the call fails, it will return false
 */
function fsockopen ($hostname, $port = null, &$errno = null, &$errstr = null, $timeout = null) {}

/**
 * Open persistent Internet or Unix domain socket connection
 * @link http://www.php.net/manual/en/function.pfsockopen.php
 * @param string $hostname 
 * @param int $port [optional] 
 * @param int $errno [optional] 
 * @param string $errstr [optional] 
 * @param float $timeout [optional] 
 * @return resource 
 */
function pfsockopen ($hostname, $port = null, &$errno = null, &$errstr = null, $timeout = null) {}

/**
 * Pack data into binary string
 * @link http://www.php.net/manual/en/function.pack.php
 * @param string $format <p>
 * The format string consists of format codes
 * followed by an optional repeater argument. The repeater argument can
 * be either an integer value or * for repeating to
 * the end of the input data. For a, A, h, H the repeat count specifies
 * how many characters of one data argument are taken, for @ it is the
 * absolute position where to put the next data, for everything else the
 * repeat count specifies how many data arguments are consumed and packed
 * into the resulting binary string.
 * </p>
 * <p>
 * Currently implemented formats are:
 * <table>
 * pack format characters
 * <tr valign="top">
 * <td>Code</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>a</td>
 * <td>NUL-padded string</td>
 * </tr>
 * <tr valign="top">
 * <td>A</td>
 * <td>SPACE-padded string</td></tr>
 * <tr valign="top">
 * <td>h</td>
 * <td>Hex string, low nibble first</td></tr>
 * <tr valign="top">
 * <td>H</td>
 * <td>Hex string, high nibble first</td></tr>
 * <tr valign="top"><td>c</td><td>signed char</td></tr>
 * <tr valign="top">
 * <td>C</td>
 * <td>unsigned char</td></tr>
 * <tr valign="top">
 * <td>s</td>
 * <td>signed short (always 16 bit, machine byte order)</td>
 * </tr>
 * <tr valign="top">
 * <td>S</td>
 * <td>unsigned short (always 16 bit, machine byte order)</td>
 * </tr>
 * <tr valign="top">
 * <td>n</td>
 * <td>unsigned short (always 16 bit, big endian byte order)</td>
 * </tr>
 * <tr valign="top">
 * <td>v</td>
 * <td>unsigned short (always 16 bit, little endian byte order)</td>
 * </tr>
 * <tr valign="top">
 * <td>i</td>
 * <td>signed integer (machine dependent size and byte order)</td>
 * </tr>
 * <tr valign="top">
 * <td>I</td>
 * <td>unsigned integer (machine dependent size and byte order)</td>
 * </tr>
 * <tr valign="top">
 * <td>l</td>
 * <td>signed long (always 32 bit, machine byte order)</td>
 * </tr>
 * <tr valign="top">
 * <td>L</td>
 * <td>unsigned long (always 32 bit, machine byte order)</td>
 * </tr>
 * <tr valign="top">
 * <td>N</td>
 * <td>unsigned long (always 32 bit, big endian byte order)</td>
 * </tr>
 * <tr valign="top">
 * <td>V</td>
 * <td>unsigned long (always 32 bit, little endian byte order)</td>
 * </tr>
 * <tr valign="top">
 * <td>q</td>
 * <td>signed long long (always 64 bit, machine byte order)</td>
 * </tr>
 * <tr valign="top">
 * <td>Q</td>
 * <td>unsigned long long (always 64 bit, machine byte order)</td>
 * </tr>
 * <tr valign="top">
 * <td>J</td>
 * <td>unsigned long long (always 64 bit, big endian byte order)</td>
 * </tr>
 * <tr valign="top">
 * <td>P</td>
 * <td>unsigned long long (always 64 bit, little endian byte order)</td>
 * </tr>
 * <tr valign="top">
 * <td>f</td>
 * <td>float (machine dependent size and representation)</td>
 * </tr>
 * <tr valign="top">
 * <td>d</td>
 * <td>double (machine dependent size and representation)</td>
 * </tr>
 * <tr valign="top">
 * <td>x</td>
 * <td>NUL byte</td>
 * </tr>
 * <tr valign="top">
 * <td>X</td>
 * <td>Back up one byte</td>
 * </tr>
 * <tr valign="top">
 * <td>Z</td>
 * <td>NUL-padded string (new in PHP 5.5)</td>
 * </tr>
 * <tr valign="top">
 * <td>@</td>
 * <td>NUL-fill to absolute position</td>
 * </tr>
 * </table>
 * </p>
 * @param mixed $args [optional] <p>
 * </p>
 * @param mixed $_ [optional] 
 * @return string a binary string containing data.
 */
function pack ($format, $args = null, $_ = null) {}

/**
 * Unpack data from binary string
 * @link http://www.php.net/manual/en/function.unpack.php
 * @param string $format <p>
 * See pack for an explanation of the format codes.
 * </p>
 * @param string $data <p>
 * The packed data.
 * </p>
 * @return array an associative array containing unpacked elements of binary
 * string.
 */
function unpack ($format, $data) {}

/**
 * Tells what the user's browser is capable of
 * @link http://www.php.net/manual/en/function.get-browser.php
 * @param string $user_agent [optional] <p>
 * The User Agent to be analyzed. By default, the value of HTTP
 * User-Agent header is used; however, you can alter this (i.e., look up
 * another browser's info) by passing this parameter.
 * </p>
 * <p>
 * You can bypass this parameter with a &null; value.
 * </p>
 * @param bool $return_array [optional] <p>
 * If set to true, this function will return an array
 * instead of an object.
 * </p>
 * @return mixed The information is returned in an object or an array which will contain
 * various data elements representing, for instance, the browser's major and
 * minor version numbers and ID string; true/false values for features
 * such as frames, JavaScript, and cookies; and so forth.
 * </p>
 * <p>
 * The cookies value simply means that the browser
 * itself is capable of accepting cookies and does not mean the user has
 * enabled the browser to accept cookies or not. The only way to test if
 * cookies are accepted is to set one with setcookie,
 * reload, and check for the value.
 */
function get_browser ($user_agent = null, $return_array = null) {}

/**
 * One-way string hashing
 * @link http://www.php.net/manual/en/function.crypt.php
 * @param string $str <p>
 * The string to be hashed.
 * </p>
 * <p>
 * Using the CRYPT_BLOWFISH algorithm, will result
 * in the str parameter being truncated to a
 * maximum length of 72 characters.
 * </p>
 * @param string $salt [optional] <p>
 * An optional salt string to base the hashing on. If not provided, the
 * behaviour is defined by the algorithm implementation and can lead to
 * unexpected results.
 * </p>
 * @return string the hashed string or a string that is shorter than 13 characters
 * and is guaranteed to differ from the salt on failure.
 */
function crypt ($str, $salt = null) {}

/**
 * Open directory handle
 * @link http://www.php.net/manual/en/function.opendir.php
 * @param string $path <p>
 * The directory path that is to be opened
 * </p>
 * @param resource $context [optional] <p>
 * For a description of the context parameter, 
 * refer to the streams section of
 * the manual.
 * </p>
 * @return resource a directory handle resource on success, or
 * false on failure.
 * </p> 
 * <p>
 * If path is not a valid directory or the
 * directory can not be opened due to permission restrictions or
 * filesystem errors, opendir returns false and
 * generates a PHP error of level 
 * E_WARNING. You can suppress the error output of
 * opendir by prepending
 * '@' to the
 * front of the function name.
 */
function opendir ($path, $context = null) {}

/**
 * Close directory handle
 * @link http://www.php.net/manual/en/function.closedir.php
 * @param resource $dir_handle [optional] <p>
 * The directory handle resource previously opened
 * with opendir. If the directory handle is 
 * not specified, the last link opened by opendir 
 * is assumed.
 * </p>
 * @return void 
 */
function closedir ($dir_handle = null) {}

/**
 * Change directory
 * @link http://www.php.net/manual/en/function.chdir.php
 * @param string $directory <p>
 * The new current directory
 * </p>
 * @return bool true on success or false on failure
 */
function chdir ($directory) {}

/**
 * Gets the current working directory
 * @link http://www.php.net/manual/en/function.getcwd.php
 * @return string the current working directory on success, or false on
 * failure.
 * </p> 
 * <p>
 * On some Unix variants, getcwd will return
 * false if any one of the parent directories does not have the
 * readable or search mode set, even if the current directory
 * does. See chmod for more information on
 * modes and permissions.
 */
function getcwd () {}

/**
 * Rewind directory handle
 * @link http://www.php.net/manual/en/function.rewinddir.php
 * @param resource $dir_handle [optional] <p>
 * The directory handle resource previously opened
 * with opendir. If the directory handle is 
 * not specified, the last link opened by opendir 
 * is assumed.
 * </p>
 * @return void 
 */
function rewinddir ($dir_handle = null) {}

/**
 * Read entry from directory handle
 * @link http://www.php.net/manual/en/function.readdir.php
 * @param resource $dir_handle [optional] <p>
 * The directory handle resource previously opened
 * with opendir. If the directory handle is 
 * not specified, the last link opened by opendir 
 * is assumed.
 * </p>
 * @return string the entry name on success or false on failure.
 */
function readdir ($dir_handle = null) {}

/**
 * Return an instance of the Directory class
 * @link http://www.php.net/manual/en/function.dir.php
 * @param string $directory <p>
 * Directory to open
 * </p>
 * @param resource $context [optional] <p>
 * &note.context-support;
 * </p>
 * @return Directory an instance of Directory, or &null; with
 * wrong parameters, or false in case of another error.
 */
function dir ($directory, $context = null) {}

/**
 * List files and directories inside the specified path
 * @link http://www.php.net/manual/en/function.scandir.php
 * @param string $directory <p>
 * The directory that will be scanned.
 * </p>
 * @param int $sorting_order [optional] <p>
 * By default, the sorted order is alphabetical in ascending order. If
 * the optional sorting_order is set to
 * SCANDIR_SORT_DESCENDING, then the sort order is
 * alphabetical in descending order. If it is set to
 * SCANDIR_SORT_NONE then the result is unsorted.
 * </p>
 * @param resource $context [optional] <p>
 * For a description of the context parameter, 
 * refer to the streams section of
 * the manual.
 * </p>
 * @return array an array of filenames on success, or false on 
 * failure. If directory is not a directory, then 
 * boolean false is returned, and an error of level 
 * E_WARNING is generated.
 */
function scandir ($directory, $sorting_order = null, $context = null) {}

/**
 * Find pathnames matching a pattern
 * @link http://www.php.net/manual/en/function.glob.php
 * @param string $pattern <p>
 * The pattern. No tilde expansion or parameter substitution is done.
 * </p>
 * @param int $flags [optional] <p>
 * Valid flags:
 * GLOB_MARK - Adds a slash to each directory returned
 * @return array an array containing the matched files/directories, an empty array
 * if no file matched or false on error.
 * </p>
 * <p>
 * On some systems it is impossible to distinguish between empty match and an
 * error.
 */
function glob ($pattern, $flags = null) {}

/**
 * Gets last access time of file
 * @link http://www.php.net/manual/en/function.fileatime.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @return int the time the file was last accessed, or false on failure.
 * The time is returned as a Unix timestamp.
 */
function fileatime ($filename) {}

/**
 * Gets inode change time of file
 * @link http://www.php.net/manual/en/function.filectime.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @return int the time the file was last changed, or false on failure.
 * The time is returned as a Unix timestamp.
 */
function filectime ($filename) {}

/**
 * Gets file group
 * @link http://www.php.net/manual/en/function.filegroup.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @return int the group ID of the file, or false if
 * an error occurs. The group ID is returned in numerical format, use
 * posix_getgrgid to resolve it to a group name.
 * Upon failure, false is returned.
 */
function filegroup ($filename) {}

/**
 * Gets file inode
 * @link http://www.php.net/manual/en/function.fileinode.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @return int the inode number of the file, or false on failure.
 */
function fileinode ($filename) {}

/**
 * Gets file modification time
 * @link http://www.php.net/manual/en/function.filemtime.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @return int the time the file was last modified, or false on failure.
 * The time is returned as a Unix timestamp, which is
 * suitable for the date function.
 */
function filemtime ($filename) {}

/**
 * Gets file owner
 * @link http://www.php.net/manual/en/function.fileowner.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @return int the user ID of the owner of the file, or false on failure.
 * The user ID is returned in numerical format, use
 * posix_getpwuid to resolve it to a username.
 */
function fileowner ($filename) {}

/**
 * Gets file permissions
 * @link http://www.php.net/manual/en/function.fileperms.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @return int the file's permissions as a numeric mode. Lower bits of this mode
 * are the same as the permissions expected by chmod,
 * however on most platforms the return value will also include information on
 * the type of file given as filename. The examples
 * below demonstrate how to test the return value for specific permissions and
 * file types on POSIX systems, including Linux and Mac OS X.
 * </p>
 * <p>
 * For local files, the specific return value is that of the
 * st_mode member of the structure returned by the C
 * library's stat function. Exactly which bits are set
 * can vary from platform to platform, and looking up your specific platform's
 * documentation is recommended if parsing the non-permission bits of the
 * return value is required.
 */
function fileperms ($filename) {}

/**
 * Gets file size
 * @link http://www.php.net/manual/en/function.filesize.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @return int the size of the file in bytes, or false (and generates an error
 * of level E_WARNING) in case of an error.
 */
function filesize ($filename) {}

/**
 * Gets file type
 * @link http://www.php.net/manual/en/function.filetype.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @return string the type of the file. Possible values are fifo, char,
 * dir, block, link, file, socket and unknown.
 * </p>
 * <p>
 * Returns false if an error occurs. filetype will also
 * produce an E_NOTICE message if the stat call fails
 * or if the file type is unknown.
 */
function filetype ($filename) {}

/**
 * Checks whether a file or directory exists
 * @link http://www.php.net/manual/en/function.file-exists.php
 * @param string $filename <p>
 * Path to the file or directory.
 * </p>
 * <p>
 * On windows, use //computername/share/filename or
 * \\computername\share\filename to check files on
 * network shares.
 * </p>
 * @return bool true if the file or directory specified by
 * filename exists; false otherwise.
 * </p>
 * <p>
 * This function will return false for symlinks pointing to non-existing
 * files.
 * </p>
 * <p>
 * This function returns false for files inaccessible due to safe mode restrictions. However these
 * files still can be included if
 * they are located in safe_mode_include_dir.
 * </p>
 * <p>
 * The check is done using the real UID/GID instead of the effective one.
 */
function file_exists ($filename) {}

/**
 * Tells whether the filename is writable
 * @link http://www.php.net/manual/en/function.is-writable.php
 * @param string $filename <p>
 * The filename being checked.
 * </p>
 * @return bool true if the filename exists and is
 * writable.
 */
function is_writable ($filename) {}

/**
 * &Alias; <function>is_writable</function>
 * @link http://www.php.net/manual/en/function.is-writeable.php
 * @param $filename
 */
function is_writeable ($filename) {}

/**
 * Tells whether a file exists and is readable
 * @link http://www.php.net/manual/en/function.is-readable.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @return bool true if the file or directory specified by
 * filename exists and is readable, false otherwise.
 */
function is_readable ($filename) {}

/**
 * Tells whether the filename is executable
 * @link http://www.php.net/manual/en/function.is-executable.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @return bool true if the filename exists and is executable, or false on
 * error.
 */
function is_executable ($filename) {}

/**
 * Tells whether the filename is a regular file
 * @link http://www.php.net/manual/en/function.is-file.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @return bool true if the filename exists and is a regular file, false
 * otherwise.
 */
function is_file ($filename) {}

/**
 * Tells whether the filename is a directory
 * @link http://www.php.net/manual/en/function.is-dir.php
 * @param string $filename <p>
 * Path to the file. If filename is a relative
 * filename, it will be checked relative to the current working
 * directory. If filename is a symbolic or hard link 
 * then the link will be resolved and checked. If you have enabled safe mode,
 * or open_basedir further
 * restrictions may apply.
 * </p>
 * @return bool true if the filename exists and is a directory, false
 * otherwise.
 */
function is_dir ($filename) {}

/**
 * Tells whether the filename is a symbolic link
 * @link http://www.php.net/manual/en/function.is-link.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @return bool true if the filename exists and is a symbolic link, false
 * otherwise.
 */
function is_link ($filename) {}

/**
 * Gives information about a file
 * @link http://www.php.net/manual/en/function.stat.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @return array <table>
 * stat and fstat result
 * format
 * <tr valign="top">
 * <td>Numeric</td>
 * <td>Associative</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>0</td>
 * <td>dev</td>
 * <td>device number</td>
 * </tr>
 * <tr valign="top">
 * <td>1</td>
 * <td>ino</td>
 * <td>inode number *</td>
 * </tr>
 * <tr valign="top">
 * <td>2</td>
 * <td>mode</td>
 * <td>inode protection mode</td>
 * </tr>
 * <tr valign="top">
 * <td>3</td>
 * <td>nlink</td>
 * <td>number of links</td>
 * </tr>
 * <tr valign="top">
 * <td>4</td>
 * <td>uid</td>
 * <td>userid of owner *</td>
 * </tr>
 * <tr valign="top">
 * <td>5</td>
 * <td>gid</td>
 * <td>groupid of owner *</td>
 * </tr>
 * <tr valign="top">
 * <td>6</td>
 * <td>rdev</td>
 * <td>device type, if inode device</td>
 * </tr>
 * <tr valign="top">
 * <td>7</td>
 * <td>size</td>
 * <td>size in bytes</td>
 * </tr>
 * <tr valign="top">
 * <td>8</td>
 * <td>atime</td>
 * <td>time of last access (Unix timestamp)</td>
 * </tr>
 * <tr valign="top">
 * <td>9</td>
 * <td>mtime</td>
 * <td>time of last modification (Unix timestamp)</td>
 * </tr>
 * <tr valign="top">
 * <td>10</td>
 * <td>ctime</td>
 * <td>time of last inode change (Unix timestamp)</td>
 * </tr>
 * <tr valign="top">
 * <td>11</td>
 * <td>blksize</td>
 * <td>blocksize of filesystem IO **</td>
 * </tr>
 * <tr valign="top">
 * <td>12</td>
 * <td>blocks</td>
 * <td>number of 512-byte blocks allocated **</td>
 * </tr>
 * </table>
 * * On Windows this will always be 0.
 * </p>
 * <p>
 * ** Only valid on systems supporting the st_blksize type - other
 * systems (e.g. Windows) return -1.
 * </p>
 * <p>
 * In case of error, stat returns false.
 */
function stat ($filename) {}

/**
 * Gives information about a file or symbolic link
 * @link http://www.php.net/manual/en/function.lstat.php
 * @param string $filename <p>
 * Path to a file or a symbolic link.
 * </p>
 * @return array See the manual page for stat for information on
 * the structure of the array that lstat returns.
 * This function is identical to the stat function
 * except that if the filename parameter is a symbolic
 * link, the status of the symbolic link is returned, not the status of the
 * file pointed to by the symbolic link.
 */
function lstat ($filename) {}

/**
 * Changes file owner
 * @link http://www.php.net/manual/en/function.chown.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @param mixed $user <p>
 * A user name or number.
 * </p>
 * @return bool true on success or false on failure
 */
function chown ($filename, $user) {}

/**
 * Changes file group
 * @link http://www.php.net/manual/en/function.chgrp.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @param mixed $group <p>
 * A group name or number.
 * </p>
 * @return bool true on success or false on failure
 */
function chgrp ($filename, $group) {}

/**
 * Changes user ownership of symlink
 * @link http://www.php.net/manual/en/function.lchown.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @param mixed $user <p>
 * User name or number.
 * </p>
 * @return bool true on success or false on failure
 */
function lchown ($filename, $user) {}

/**
 * Changes group ownership of symlink
 * @link http://www.php.net/manual/en/function.lchgrp.php
 * @param string $filename <p>
 * Path to the symlink.
 * </p>
 * @param mixed $group <p>
 * The group specified by name or number.
 * </p>
 * @return bool true on success or false on failure
 */
function lchgrp ($filename, $group) {}

/**
 * Changes file mode
 * @link http://www.php.net/manual/en/function.chmod.php
 * @param string $filename <p>
 * Path to the file.
 * </p>
 * @param int $mode <p>
 * Note that mode is not automatically
 * assumed to be an octal value, so to ensure the expected operation,
 * you need to prefix mode with a zero (0). 
 * Strings such as "g+w" will not work properly.
 * </p>
 * <p>
 * ]]>
 * </p>
 * <p>
 * The mode parameter consists of three octal
 * number components specifying access restrictions for the owner,
 * the user group in which the owner is in, and to everybody else in
 * this order. One component can be computed by adding up the needed
 * permissions for that target user base. Number 1 means that you
 * grant execute rights, number 2 means that you make the file
 * writeable, number 4 means that you make the file readable. Add
 * up these numbers to specify needed rights. You can also read more
 * about modes on Unix systems with 'man 1 chmod'
 * and 'man 2 chmod'.
 * </p>
 * <p>
 * @return bool true on success or false on failure
 */
function chmod ($filename, $mode) {}

/**
 * Sets access and modification time of file
 * @link http://www.php.net/manual/en/function.touch.php
 * @param string $filename <p>
 * The name of the file being touched.
 * </p>
 * @param int $time [optional] <p>
 * The touch time. If time is not supplied, 
 * the current system time is used.
 * </p>
 * @param int $atime [optional] <p>
 * If present, the access time of the given filename is set to 
 * the value of atime. Otherwise, it is set to
 * the value passed to the time parameter.
 * If neither are present, the current system time is used.
 * </p>
 * @return bool true on success or false on failure
 */
function touch ($filename, $time = null, $atime = null) {}

/**
 * Clears file status cache
 * @link http://www.php.net/manual/en/function.clearstatcache.php
 * @param bool $clear_realpath_cache [optional] <p>
 * Whether to clear the realpath cache or not.
 * </p>
 * @param string $filename [optional] <p>
 * Clear the realpath and the stat cache for a specific filename only; only
 * used if clear_realpath_cache is true.
 * </p>
 * @return void 
 */
function clearstatcache ($clear_realpath_cache = null, $filename = null) {}

/**
 * Returns the total size of a filesystem or disk partition
 * @link http://www.php.net/manual/en/function.disk-total-space.php
 * @param string $directory <p>
 * A directory of the filesystem or disk partition.
 * </p>
 * @return float the total number of bytes as a float
 * or false on failure.
 */
function disk_total_space ($directory) {}

/**
 * Returns available space on filesystem or disk partition
 * @link http://www.php.net/manual/en/function.disk-free-space.php
 * @param string $directory <p>
 * A directory of the filesystem or disk partition.
 * </p>
 * <p>
 * Given a file name instead of a directory, the behaviour of the
 * function is unspecified and may differ between operating systems and
 * PHP versions.
 * </p>
 * @return float the number of available bytes as a float
 * or false on failure.
 */
function disk_free_space ($directory) {}

/**
 * &Alias; <function>disk_free_space</function>
 * @link http://www.php.net/manual/en/function.diskfreespace.php
 * @param $path
 */
function diskfreespace ($path) {}

/**
 * Get realpath cache size
 * @link http://www.php.net/manual/en/function.realpath-cache-size.php
 * @return int how much memory realpath cache is using.
 */
function realpath_cache_size () {}

/**
 * Get realpath cache entries
 * @link http://www.php.net/manual/en/function.realpath-cache-get.php
 * @return array an array of realpath cache entries. The keys are original path
 * entries, and the values are arrays of data items, containing the resolved
 * path, expiration date, and other options kept in the cache.
 */
function realpath_cache_get () {}

/**
 * Send mail
 * @link http://www.php.net/manual/en/function.mail.php
 * @param string $to <p>
 * Receiver, or receivers of the mail.
 * </p>
 * <p>
 * The formatting of this string must comply with
 * RFC 2822. Some examples are:
 * user@example.com
 * user@example.com, anotheruser@example.com
 * User &lt;user@example.com&gt;
 * User &lt;user@example.com&gt;, Another User &lt;anotheruser@example.com&gt;
 * </p>
 * @param string $subject <p>
 * Subject of the email to be sent.
 * </p>
 * <p>
 * Subject must satisfy RFC 2047.
 * </p>
 * @param string $message <p>
 * Message to be sent.
 * </p>
 * <p>
 * Each line should be separated with a CRLF (\r\n). Lines should not be
 * larger than 70 characters.
 * </p>
 * <p>
 * (Windows only) When PHP is talking to a SMTP server directly, if a full
 * stop is found on the start of a line, it is removed. To counter-act this,
 * replace these occurrences with a double dot.
 * ]]>
 * </p>
 * @param string $additional_headers [optional] <p>
 * String to be inserted at the end of the email header.
 * </p>
 * <p>
 * This is typically used to add extra headers (From, Cc, and Bcc).
 * Multiple extra headers should be separated with a CRLF (\r\n).
 * If outside data are used to compose this header, the data should be sanitized
 * so that no unwanted headers could be injected. 
 * </p>
 * <p>
 * additional_headers does not have mail header
 * injection protection. Therefore, users must make sure specified headers
 * are safe and contains headers only. i.e. Never start mail body by putting
 * multiple newlines.
 * </p>
 * <p>
 * When sending mail, the mail must contain
 * a From header. This can be set with the
 * additional_headers parameter, or a default
 * can be set in &php.ini;.
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
 * @param string $additional_parameters [optional] <p>
 * The additional_parameters parameter
 * can be used to pass additional flags as command line options to the
 * program configured to be used when sending mail, as defined by the
 * sendmail_path configuration setting. For example,
 * this can be used to set the envelope sender address when using
 * sendmail with the -f sendmail option.
 * </p>
 * <p>
 * This parameter is escaped by escapeshellcmd internally
 * to prevent command execution. escapeshellcmd prevents
 * command execution, but allows to add addtional parameters. For security reasons,
 * it is recommended for the user to sanitize this parameter to avoid adding unwanted
 * parameters to the shell command.
 * </p>
 * <p>
 * Since escapeshellcmd is applied automatically, some characters
 * that are allowed as email addresses by internet RFCs cannot be used. 
 * mail can not allow such characters, so in programs where the use of
 * such characters is required, alternative means of sending emails (such as using a framework
 * or a library) is recommended. 
 * </p>
 * <p>
 * The user that the webserver runs as should be added as a trusted user to the
 * sendmail configuration to prevent a 'X-Warning' header from being added
 * to the message when the envelope sender (-f) is set using this method.
 * For sendmail users, this file is /etc/mail/trusted-users.
 * </p>
 * @return bool true if the mail was successfully accepted for delivery, false otherwise.
 * </p>
 * <p>
 * It is important to note that just because the mail was accepted for delivery,
 * it does NOT mean the mail will actually reach the intended destination.
 */
function mail ($to, $subject, $message, $additional_headers = null, $additional_parameters = null) {}

/**
 * Calculate the hash value needed by EZMLM
 * @link http://www.php.net/manual/en/function.ezmlm-hash.php
 * @param string $addr <p>
 * The email address that's being hashed.
 * </p>
 * @return int The hash value of addr.
 */
function ezmlm_hash ($addr) {}

/**
 * Open connection to system logger
 * @link http://www.php.net/manual/en/function.openlog.php
 * @param string $ident <p>
 * The string ident is added to each message. 
 * </p>
 * @param int $option <p>
 * The option argument is used to indicate
 * what logging options will be used when generating a log message.
 * <table>
 * openlog Options
 * <tr valign="top">
 * <td>Constant</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_CONS</td>
 * <td>
 * if there is an error while sending data to the system logger,
 * write directly to the system console
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_NDELAY</td>
 * <td>
 * open the connection to the logger immediately
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_ODELAY</td>
 * <td>
 * (default) delay opening the connection until the first
 * message is logged
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_PERROR</td>
 * <td>print log message also to standard error</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_PID</td>
 * <td>include PID with each message</td>
 * </tr>
 * </table>
 * You can use one or more of this options. When using multiple options
 * you need to OR them, i.e. to open the connection
 * immediately, write to the console and include the PID in each message,
 * you will use: LOG_CONS | LOG_NDELAY | LOG_PID
 * </p>
 * @param int $facility <p>
 * The facility argument is used to specify what
 * type of program is logging the message. This allows you to specify
 * (in your machine's syslog configuration) how messages coming from
 * different facilities will be handled.
 * <table>
 * openlog Facilities
 * <tr valign="top">
 * <td>Constant</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_AUTH</td>
 * <td>
 * security/authorization messages (use 
 * LOG_AUTHPRIV instead
 * in systems where that constant is defined)
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_AUTHPRIV</td>
 * <td>security/authorization messages (private)</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_CRON</td>
 * <td>clock daemon (cron and at)</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_DAEMON</td>
 * <td>other system daemons</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_KERN</td>
 * <td>kernel messages</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_LOCAL0 ... LOG_LOCAL7</td>
 * <td>reserved for local use, these are not available in Windows</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_LPR</td>
 * <td>line printer subsystem</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_MAIL</td>
 * <td>mail subsystem</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_NEWS</td>
 * <td>USENET news subsystem</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_SYSLOG</td>
 * <td>messages generated internally by syslogd</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_USER</td>
 * <td>generic user-level messages</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_UUCP</td>
 * <td>UUCP subsystem</td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * LOG_USER is the only valid log type under Windows
 * operating systems
 * </p>
 * @return bool true on success or false on failure
 */
function openlog ($ident, $option, $facility) {}

/**
 * Generate a system log message
 * @link http://www.php.net/manual/en/function.syslog.php
 * @param int $priority <p>
 * priority is a combination of the facility and
 * the level. Possible values are:
 * <table>
 * syslog Priorities (in descending order)
 * <tr valign="top">
 * <td>Constant</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_EMERG</td>
 * <td>system is unusable</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_ALERT</td>
 * <td>action must be taken immediately</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_CRIT</td>
 * <td>critical conditions</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_ERR</td>
 * <td>error conditions</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_WARNING</td>
 * <td>warning conditions</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_NOTICE</td>
 * <td>normal, but significant, condition</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_INFO</td>
 * <td>informational message</td>
 * </tr>
 * <tr valign="top">
 * <td>LOG_DEBUG</td>
 * <td>debug-level message</td>
 * </tr>
 * </table>
 * </p>
 * @param string $message <p>
 * The message to send, except that the two characters
 * %m will be replaced by the error message string
 * (strerror) corresponding to the present value of
 * errno.
 * </p>
 * @return bool true on success or false on failure
 */
function syslog ($priority, $message) {}

/**
 * Close connection to system logger
 * @link http://www.php.net/manual/en/function.closelog.php
 * @return bool true on success or false on failure
 */
function closelog () {}

/**
 * Combined linear congruential generator
 * @link http://www.php.net/manual/en/function.lcg-value.php
 * @return float A pseudo random float value in the range of (0, 1)
 */
function lcg_value () {}

/**
 * Calculate the metaphone key of a string
 * @link http://www.php.net/manual/en/function.metaphone.php
 * @param string $str <p>
 * The input string.
 * </p>
 * @param int $phonemes [optional] <p>
 * This parameter restricts the returned metaphone key to 
 * phonemes characters in length.
 * The default value of 0 means no restriction.
 * </p>
 * @return string the metaphone key as a string, or false on failure.
 */
function metaphone ($str, $phonemes = null) {}

/**
 * Turn on output buffering
 * @link http://www.php.net/manual/en/function.ob-start.php
 * @param callable $output_callback [optional] <p>
 * An optional output_callback function may be
 * specified. This function takes a string as a parameter and should
 * return a string. The function will be called when
 * the output buffer is flushed (sent) or cleaned (with
 * ob_flush, ob_clean or similar
 * function) or when the output buffer
 * is flushed to the browser at the end of the request. When
 * output_callback is called, it will receive the
 * contents of the output buffer as its parameter and is expected to
 * return a new output buffer as a result, which will be sent to the
 * browser. If the output_callback is not a
 * callable function, this function will return false.
 * This is the callback signature:
 * </p>
 * <p>
 * stringhandler
 * stringbuffer
 * intphase
 * buffer
 * Contents of the output buffer.
 * @param int $chunk_size [optional] <p>
 * If the optional parameter chunk_size is passed, the
 * buffer will be flushed after any output call which causes the buffer's
 * length to equal or exceed chunk_size. The default
 * value 0 means that the output function will only be
 * called when the output buffer is closed.
 * </p>
 * <p>
 * Prior to PHP 5.4.0, the value 1 was a special case
 * value that set the chunk size to 4096 bytes.
 * </p>
 * @param int $flags [optional] <p>
 * The flags parameter is a bitmask that controls
 * the operations that can be performed on the output buffer. The default
 * is to allow output buffers to be cleaned, flushed and removed, which
 * can be set explicitly via
 * PHP_OUTPUT_HANDLER_CLEANABLE |
 * PHP_OUTPUT_HANDLER_FLUSHABLE |
 * PHP_OUTPUT_HANDLER_REMOVABLE, or
 * PHP_OUTPUT_HANDLER_STDFLAGS as shorthand.
 * </p>
 * <p>
 * Each flag controls access to a set of functions, as described below:
 * <tr valign="top">
 * <td>Constant</td>
 * <td>Functions</td>
 * </tr>
 * <tr valign="top">
 * <td>PHP_OUTPUT_HANDLER_CLEANABLE</td>
 * <td>
 * ob_clean,
 * ob_end_clean, and
 * ob_get_clean.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>PHP_OUTPUT_HANDLER_FLUSHABLE</td>
 * <td>
 * ob_end_flush,
 * ob_flush, and
 * ob_get_flush.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>PHP_OUTPUT_HANDLER_REMOVABLE</td>
 * <td>
 * ob_end_clean,
 * ob_end_flush, and
 * ob_get_flush.
 * </td>
 * </tr>
 * </p>
 * @return bool true on success or false on failure
 */
function ob_start ($output_callback = null, $chunk_size = null, $flags = null) {}

/**
 * Flush (send) the output buffer
 * @link http://www.php.net/manual/en/function.ob-flush.php
 * @return void 
 */
function ob_flush () {}

/**
 * Clean (erase) the output buffer
 * @link http://www.php.net/manual/en/function.ob-clean.php
 * @return void 
 */
function ob_clean () {}

/**
 * Flush (send) the output buffer and turn off output buffering
 * @link http://www.php.net/manual/en/function.ob-end-flush.php
 * @return bool true on success or false on failure Reasons for failure are first that you called the
 * function without an active buffer or that for some reason a buffer could
 * not be deleted (possible for special buffer).
 */
function ob_end_flush () {}

/**
 * Clean (erase) the output buffer and turn off output buffering
 * @link http://www.php.net/manual/en/function.ob-end-clean.php
 * @return bool true on success or false on failure Reasons for failure are first that you called the
 * function without an active buffer or that for some reason a buffer could
 * not be deleted (possible for special buffer).
 */
function ob_end_clean () {}

/**
 * Flush the output buffer, return it as a string and turn off output buffering
 * @link http://www.php.net/manual/en/function.ob-get-flush.php
 * @return string the output buffer or false if no buffering is active.
 */
function ob_get_flush () {}

/**
 * Get current buffer contents and delete current output buffer
 * @link http://www.php.net/manual/en/function.ob-get-clean.php
 * @return string the contents of the output buffer and end output buffering.
 * If output buffering isn't active then false is returned.
 */
function ob_get_clean () {}

/**
 * Return the length of the output buffer
 * @link http://www.php.net/manual/en/function.ob-get-length.php
 * @return int the length of the output buffer contents, in bytes, or false if no
 * buffering is active.
 */
function ob_get_length () {}

/**
 * Return the nesting level of the output buffering mechanism
 * @link http://www.php.net/manual/en/function.ob-get-level.php
 * @return int the level of nested output buffering handlers or zero if output
 * buffering is not active.
 */
function ob_get_level () {}

/**
 * Get status of output buffers
 * @link http://www.php.net/manual/en/function.ob-get-status.php
 * @param bool $full_status [optional] <p>
 * true to return all active output buffer levels. If false or not
 * set, only the top level output buffer is returned.
 * </p>
 * @return array If called without the full_status parameter
 * or with full_status = false a simple array
 * with the following elements is returned:
 * 2
 * [type] => 0
 * [status] => 0
 * [name] => URL-Rewriter
 * [del] => 1
 * )
 * ]]>
 * Simple ob_get_status results
 * KeyValue
 * levelOutput nesting level
 * typePHP_OUTPUT_HANDLER_INTERNAL (0) or PHP_OUTPUT_HANDLER_USER (1)
 * statusOne of PHP_OUTPUT_HANDLER_START (0), PHP_OUTPUT_HANDLER_CONT (1) or PHP_OUTPUT_HANDLER_END (2)
 * nameName of active output handler or ' default output handler' if none is set
 * delErase-flag as set by ob_start
 * </p>
 * <p>
 * If called with full_status = true an array
 * with one element for each active output buffer level is returned.
 * The output level is used as key of the top level array and each array
 * element itself is another array holding status information
 * on one active output level.
 * Array
 * (
 * [chunk_size] => 0
 * [size] => 40960
 * [block_size] => 10240
 * [type] => 1
 * [status] => 0
 * [name] => default output handler
 * [del] => 1
 * )
 * [1] => Array
 * (
 * [chunk_size] => 0
 * [size] => 40960
 * [block_size] => 10240
 * [type] => 0
 * [buffer_size] => 0
 * [status] => 0
 * [name] => URL-Rewriter
 * [del] => 1
 * )
 * )
 * ]]>
 * </p>
 * <p>
 * The full output contains these additional elements:
 * Full ob_get_status results
 * KeyValue
 * chunk_sizeChunk size as set by ob_start
 * size...
 * blocksize...
 */
function ob_get_status ($full_status = null) {}

/**
 * Return the contents of the output buffer
 * @link http://www.php.net/manual/en/function.ob-get-contents.php
 * @return string This will return the contents of the output buffer or false, if output
 * buffering isn't active.
 */
function ob_get_contents () {}

/**
 * Turn implicit flush on/off
 * @link http://www.php.net/manual/en/function.ob-implicit-flush.php
 * @param int $flag [optional] <p>
 * true to turn implicit flushing on, false otherwise.
 * </p>
 * @return void 
 */
function ob_implicit_flush ($flag = null) {}

/**
 * List all output handlers in use
 * @link http://www.php.net/manual/en/function.ob-list-handlers.php
 * @return array This will return an array with the output handlers in use (if any). If
 * output_buffering is enabled or
 * an anonymous function was used with ob_start,
 * ob_list_handlers will return "default output
 * handler".
 */
function ob_list_handlers () {}

/**
 * Sort an array by key
 * @link http://www.php.net/manual/en/function.ksort.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @param int $sort_flags [optional] <p>
 * You may modify the behavior of the sort using the optional
 * parameter sort_flags, for details
 * see sort.
 * </p>
 * @return bool true on success or false on failure
 */
function ksort (array &$array, $sort_flags = null) {}

/**
 * Sort an array by key in reverse order
 * @link http://www.php.net/manual/en/function.krsort.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @param int $sort_flags [optional] <p>
 * You may modify the behavior of the sort using the optional parameter
 * sort_flags, for details see
 * sort.
 * </p>
 * @return bool true on success or false on failure
 */
function krsort (array &$array, $sort_flags = null) {}

/**
 * Sort an array using a "natural order" algorithm
 * @link http://www.php.net/manual/en/function.natsort.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @return bool true on success or false on failure
 */
function natsort (array &$array) {}

/**
 * Sort an array using a case insensitive "natural order" algorithm
 * @link http://www.php.net/manual/en/function.natcasesort.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @return bool true on success or false on failure
 */
function natcasesort (array &$array) {}

/**
 * Sort an array and maintain index association
 * @link http://www.php.net/manual/en/function.asort.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @param int $sort_flags [optional] <p>
 * You may modify the behavior of the sort using the optional
 * parameter sort_flags, for details
 * see sort.
 * </p>
 * @return bool true on success or false on failure
 */
function asort (array &$array, $sort_flags = null) {}

/**
 * Sort an array in reverse order and maintain index association
 * @link http://www.php.net/manual/en/function.arsort.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @param int $sort_flags [optional] <p>
 * You may modify the behavior of the sort using the optional parameter
 * sort_flags, for details see
 * sort.
 * </p>
 * @return bool true on success or false on failure
 */
function arsort (array &$array, $sort_flags = null) {}

/**
 * Sort an array
 * @link http://www.php.net/manual/en/function.sort.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @param int $sort_flags [optional] <p>
 * The optional second parameter sort_flags
 * may be used to modify the sorting behavior using these values:
 * </p>
 * <p>
 * Sorting type flags:
 * SORT_REGULAR - compare items normally
 * (don't change types)
 * @return bool true on success or false on failure
 */
function sort (array &$array, $sort_flags = null) {}

/**
 * Sort an array in reverse order
 * @link http://www.php.net/manual/en/function.rsort.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @param int $sort_flags [optional] <p>
 * You may modify the behavior of the sort using the optional
 * parameter sort_flags, for details see
 * sort.
 * </p>
 * @return bool true on success or false on failure
 */
function rsort (array &$array, $sort_flags = null) {}

/**
 * Sort an array by values using a user-defined comparison function
 * @link http://www.php.net/manual/en/function.usort.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @param callable $value_compare_func <p>
 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
 * </p>
 * &callback.cmp;
 * <p>
 * Returning non-integer values from the comparison
 * function, such as float, will result in an internal cast to
 * integer of the callback's return value. So values such as
 * 0.99 and 0.1 will both be cast to an integer value of 0, which will
 * compare such values as equal.
 * </p>
 * @return bool true on success or false on failure
 */
function usort (array &$array, $value_compare_func) {}

/**
 * Sort an array with a user-defined comparison function and maintain index association
 * @link http://www.php.net/manual/en/function.uasort.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @param callable $value_compare_func <p>
 * See usort and uksort for
 * examples of user-defined comparison functions.
 * </p>
 * @return bool true on success or false on failure
 */
function uasort (array &$array, $value_compare_func) {}

/**
 * Sort an array by keys using a user-defined comparison function
 * @link http://www.php.net/manual/en/function.uksort.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @param callable $key_compare_func <p>
 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
 * </p>
 * &callback.cmp;
 * @return bool true on success or false on failure
 */
function uksort (array &$array, $key_compare_func) {}

/**
 * Shuffle an array
 * @link http://www.php.net/manual/en/function.shuffle.php
 * @param array $array <p>
 * The array.
 * </p>
 * @return bool true on success or false on failure
 */
function shuffle (array &$array) {}

/**
 * Apply a user supplied function to every member of an array
 * @link http://www.php.net/manual/en/function.array-walk.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @param callable $callback <p>
 * Typically, callback takes on two parameters.
 * The array parameter's value being the first, and
 * the key/index second.
 * </p>
 * <p>
 * If callback needs to be working with the
 * actual values of the array, specify the first parameter of
 * callback as a
 * reference. Then,
 * any changes made to those elements will be made in the
 * original array itself.
 * </p>
 * <p>
 * Many internal functions (for example strtolower)
 * will throw a warning if more than the expected number of argument
 * are passed in and are not usable directly as a
 * callback.
 * </p>
 * <p>
 * Only the values of the array may potentially be
 * changed; its structure cannot be altered, i.e., the programmer cannot
 * add, unset or reorder elements. If the callback does not respect this
 * requirement, the behavior of this function is undefined, and 
 * unpredictable.
 * </p>
 * @param mixed $userdata [optional] <p>
 * If the optional userdata parameter is supplied,
 * it will be passed as the third parameter to the
 * callback.
 * </p>
 * @return bool true on success or false on failure
 */
function array_walk (array &$array, $callback, $userdata = null) {}

/**
 * Apply a user function recursively to every member of an array
 * @link http://www.php.net/manual/en/function.array-walk-recursive.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @param callable $callback <p>
 * Typically, callback takes on two parameters.
 * The array parameter's value being the first, and
 * the key/index second.
 * </p>
 * <p>
 * If callback needs to be working with the
 * actual values of the array, specify the first parameter of
 * callback as a
 * reference. Then,
 * any changes made to those elements will be made in the
 * original array itself.
 * </p>
 * @param mixed $userdata [optional] <p>
 * If the optional userdata parameter is supplied,
 * it will be passed as the third parameter to the
 * callback.
 * </p>
 * @return bool true on success or false on failure
 */
function array_walk_recursive (array &$array, $callback, $userdata = null) {}

/**
 * Count all elements in an array, or something in an object
 * @link http://www.php.net/manual/en/function.count.php
 * @param mixed $array_or_countable <p>
 * An array or Countable object.
 * </p>
 * @param int $mode [optional] <p>
 * If the optional mode parameter is set to
 * COUNT_RECURSIVE (or 1), count
 * will recursively count the array. This is particularly useful for
 * counting all the elements of a multidimensional array.
 * </p>
 * <p>
 * count can detect recursion to avoid an infinite
 * loop, but will emit an E_WARNING every time it
 * does (in case the array contains itself more than once) and return a
 * count higher than may be expected.
 * </p>
 * @return int the number of elements in array_or_countable.
 * If the parameter is not an array or not an object with
 * implemented Countable interface,
 * 1 will be returned.
 * There is one exception, if array_or_countable is &null;,
 * 0 will be returned.
 * </p>
 * <p>
 * count may return 0 for a variable that isn't set,
 * but it may also return 0 for a variable that has been initialized with an
 * empty array. Use isset to test if a variable is set.
 */
function count ($array_or_countable, $mode = null) {}

/**
 * Set the internal pointer of an array to its last element
 * @link http://www.php.net/manual/en/function.end.php
 * @param array $array <p>
 * The array. This array is passed by reference because it is modified by
 * the function. This means you must pass it a real variable and not
 * a function returning an array because only actual variables may be
 * passed by reference.
 * </p>
 * @return mixed the value of the last element or false for empty array.
 */
function end (array &$array) {}

/**
 * Rewind the internal array pointer
 * @link http://www.php.net/manual/en/function.prev.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @return mixed the array value in the previous place that's pointed to by
 * the internal array pointer, or false if there are no more
 * elements.
 */
function prev (array &$array) {}

/**
 * Advance the internal array pointer of an array
 * @link http://www.php.net/manual/en/function.next.php
 * @param array $array <p>
 * The array being affected.
 * </p>
 * @return mixed the array value in the next place that's pointed to by the
 * internal array pointer, or false if there are no more elements.
 */
function next (array &$array) {}

/**
 * Set the internal pointer of an array to its first element
 * @link http://www.php.net/manual/en/function.reset.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @return mixed the value of the first array element, or false if the array is
 * empty.
 */
function reset (array &$array) {}

/**
 * Return the current element in an array
 * @link http://www.php.net/manual/en/function.current.php
 * @param array $array <p>
 * The array.
 * </p>
 * @return mixed The current function simply returns the
 * value of the array element that's currently being pointed to by the
 * internal pointer. It does not move the pointer in any way. If the
 * internal pointer points beyond the end of the elements list or the array is 
 * empty, current returns false.
 */
function current (array &$array) {}

/**
 * Fetch a key from an array
 * @link http://www.php.net/manual/en/function.key.php
 * @param array $array <p>
 * The array.
 * </p>
 * @return mixed The key function simply returns the
 * key of the array element that's currently being pointed to by the
 * internal pointer. It does not move the pointer in any way. If the
 * internal pointer points beyond the end of the elements list or the array is 
 * empty, key returns &null;.
 */
function key (array &$array) {}

/**
 * Find lowest value
 * @link http://www.php.net/manual/en/function.min.php
 * @param array $values <p>
 * An array containing the values.
 * </p>
 * @return mixed min returns the parameter value considered "lowest" according to standard
 * comparisons. If multiple values of different types evaluate as equal (e.g. 0
 * and 'abc') the first provided to the function will be returned.
 */
function min (array $values) {}

/**
 * Find highest value
 * @link http://www.php.net/manual/en/function.max.php
 * @param array $values <p>
 * An array containing the values.
 * </p>
 * @return mixed max returns the parameter value considered "highest" according to standard
 * comparisons. If multiple values of different types evaluate as equal (e.g. 0
 * and 'abc') the first provided to the function will be returned.
 */
function max (array $values) {}

/**
 * Checks if a value exists in an array
 * @link http://www.php.net/manual/en/function.in-array.php
 * @param mixed $needle <p>
 * The searched value.
 * </p>
 * <p>
 * If needle is a string, the comparison is done
 * in a case-sensitive manner.
 * </p>
 * @param array $haystack <p>
 * The array.
 * </p>
 * @param bool $strict [optional] <p>
 * If the third parameter strict is set to true
 * then the in_array function will also check the
 * types of the
 * needle in the haystack.
 * </p>
 * @return bool true if needle is found in the array,
 * false otherwise.
 */
function in_array ($needle, array $haystack, $strict = null) {}

/**
 * Searches the array for a given value and returns the corresponding key if successful
 * @link http://www.php.net/manual/en/function.array-search.php
 * @param mixed $needle <p>
 * The searched value.
 * </p>
 * <p>
 * If needle is a string, the comparison is done
 * in a case-sensitive manner.
 * </p>
 * @param array $haystack <p>
 * The array.
 * </p>
 * @param bool $strict [optional] <p>
 * If the third parameter strict is set to true
 * then the array_search function will search for
 * identical elements in the
 * haystack. This means it will also check the
 * types of the
 * needle in the haystack,
 * and objects must be the same instance.
 * </p>
 * @return mixed the key for needle if it is found in the
 * array, false otherwise.
 * </p>
 * <p>
 * If needle is found in haystack
 * more than once, the first matching key is returned. To return the keys for
 * all matching values, use array_keys with the optional
 * search_value parameter instead.
 */
function array_search ($needle, array $haystack, $strict = null) {}

/**
 * Import variables into the current symbol table from an array
 * @link http://www.php.net/manual/en/function.extract.php
 * @param array $array <p>
 * Note that prefix is only required if
 * flags is EXTR_PREFIX_SAME,
 * EXTR_PREFIX_ALL, EXTR_PREFIX_INVALID
 * or EXTR_PREFIX_IF_EXISTS. If
 * the prefixed result is not a valid variable name, it is not
 * imported into the symbol table. Prefixes are automatically separated from
 * the array key by an underscore character.
 * </p>
 * @param int $flags [optional] <p>
 * The way invalid/numeric keys and collisions are treated is determined
 * by the extraction flags. It can be one of the
 * following values:
 * EXTR_OVERWRITE
 * If there is a collision, overwrite the existing variable.
 * @param string $prefix [optional] Only overwrite the variable if it already exists in the
 * current symbol table, otherwise do nothing. This is useful
 * for defining a list of valid variables and then extracting
 * only those variables you have defined out of
 * $_REQUEST, for example.
 * @return int the number of variables successfully imported into the symbol
 * table.
 */
function extract (array &$array, $flags = null, $prefix = null) {}

/**
 * Create array containing variables and their values
 * @link http://www.php.net/manual/en/function.compact.php
 * @param mixed $varname1 <p>
 * compact takes a variable number of parameters.
 * Each parameter can be either a string containing the name of the
 * variable, or an array of variable names. The array can contain other
 * arrays of variable names inside it; compact
 * handles it recursively.
 * </p>
 * @param mixed $_ [optional] 
 * @return array the output array with all the variables added to it.
 */
function compact ($varname1, $_ = null) {}

/**
 * Fill an array with values
 * @link http://www.php.net/manual/en/function.array-fill.php
 * @param int $start_index <p>
 * The first index of the returned array.
 * </p>
 * <p>
 * If start_index is negative, 
 * the first index of the returned array will be 
 * start_index and the following 
 * indices will start from zero 
 * (see example).
 * </p>
 * @param int $num <p>
 * Number of elements to insert.
 * Must be greater than or equal to zero.
 * </p>
 * @param mixed $value <p>
 * Value to use for filling
 * </p>
 * @return array the filled array
 */
function array_fill ($start_index, $num, $value) {}

/**
 * Fill an array with values, specifying keys
 * @link http://www.php.net/manual/en/function.array-fill-keys.php
 * @param array $keys <p>
 * Array of values that will be used as keys. Illegal values
 * for key will be converted to string.
 * </p>
 * @param mixed $value <p>
 * Value to use for filling
 * </p>
 * @return array the filled array
 */
function array_fill_keys (array $keys, $value) {}

/**
 * Create an array containing a range of elements
 * @link http://www.php.net/manual/en/function.range.php
 * @param mixed $start <p>
 * First value of the sequence.
 * </p>
 * @param mixed $end <p>
 * The sequence is ended upon reaching the
 * end value.
 * </p>
 * @param number $step [optional] <p>
 * If a step value is given, it will be used as the
 * increment between elements in the sequence. step
 * should be given as a positive number. If not specified,
 * step will default to 1.
 * </p>
 * @return array an array of elements from start to
 * end, inclusive.
 */
function range ($start, $end, $step = null) {}

/**
 * Sort multiple or multi-dimensional arrays
 * @link http://www.php.net/manual/en/function.array-multisort.php
 * @param array $array1 <p>
 * An array being sorted.
 * </p>
 * @param mixed $array1_sort_order [optional] <p>
 * The order used to sort the previous array argument. Either
 * SORT_ASC to sort ascendingly or SORT_DESC
 * to sort descendingly.
 * </p>
 * <p>
 * This argument can be swapped with array1_sort_flags
 * or omitted entirely, in which case SORT_ASC is assumed.
 * </p>
 * @param mixed $array1_sort_flags [optional] <p>
 * Sort options for the previous array argument:
 * </p>
 * <p>
 * Sorting type flags:
 * SORT_REGULAR - compare items normally
 * (don't change types)
 * @param mixed $_ [optional] 
 * @return bool true on success or false on failure
 */
function array_multisort (array &$array1, $array1_sort_order = null, $array1_sort_flags = null, $_ = null) {}

/**
 * Push one or more elements onto the end of array
 * @link http://www.php.net/manual/en/function.array-push.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @param mixed $value1 <p>
 * The first value to push onto the end of the array.
 * </p>
 * @param mixed $_ [optional] 
 * @return int the new number of elements in the array.
 */
function array_push (array &$array, $value1, $_ = null) {}

/**
 * Pop the element off the end of array
 * @link http://www.php.net/manual/en/function.array-pop.php
 * @param array $array <p>
 * The array to get the value from.
 * </p>
 * @return mixed the last value of array.
 * If array is empty (or is not an array),
 * &null; will be returned.
 */
function array_pop (array &$array) {}

/**
 * Shift an element off the beginning of array
 * @link http://www.php.net/manual/en/function.array-shift.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @return mixed the shifted value, or &null; if array is
 * empty or is not an array.
 */
function array_shift (array &$array) {}

/**
 * Prepend one or more elements to the beginning of an array
 * @link http://www.php.net/manual/en/function.array-unshift.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @param mixed $value1 <p>
 * First value to prepend.
 * </p>
 * @param mixed $_ [optional] 
 * @return int the new number of elements in the array.
 */
function array_unshift (array &$array, $value1, $_ = null) {}

/**
 * Remove a portion of the array and replace it with something else
 * @link http://www.php.net/manual/en/function.array-splice.php
 * @param array $input <p>
 * The input array.
 * </p>
 * @param int $offset <p>
 * If offset is positive then the start of removed
 * portion is at that offset from the beginning of the
 * input array. If offset
 * is negative then it starts that far from the end of the
 * input array.
 * </p>
 * @param int $length [optional] <p>
 * If length is omitted, removes everything
 * from offset to the end of the array. If
 * length is specified and is positive, then
 * that many elements will be removed. If
 * length is specified and is negative then
 * the end of the removed portion will be that many elements from
 * the end of the array. If length is
 * specified and is zero, no elements will be removed.
 * Tip: to remove everything from
 * offset to the end of the array when
 * replacement is also specified, use
 * count($input) for
 * length.
 * </p>
 * @param mixed $replacement [optional] <p>
 * If replacement array is specified, then the
 * removed elements are replaced with elements from this array.
 * </p>
 * <p>
 * If offset and length
 * are such that nothing is removed, then the elements from the
 * replacement array are inserted in the place
 * specified by the offset. Note that keys in
 * replacement array are not preserved.
 * </p>
 * <p>
 * If replacement is just one element it is
 * not necessary to put array()
 * around it, unless the element is an array itself, an object or &null;.
 * </p>
 * @return array the array consisting of the extracted elements.
 */
function array_splice (array &$input, $offset, $length = null, $replacement = null) {}

/**
 * Extract a slice of the array
 * @link http://www.php.net/manual/en/function.array-slice.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @param int $offset <p>
 * If offset is non-negative, the sequence will
 * start at that offset in the array. If
 * offset is negative, the sequence will
 * start that far from the end of the array.
 * </p>
 * @param int $length [optional] <p>
 * If length is given and is positive, then
 * the sequence will have up to that many elements in it. If the array
 * is shorter than the length, then only the
 * available array elements will be present. If
 * length is given and is negative then the
 * sequence will stop that many elements from the end of the
 * array. If it is omitted, then the sequence will have everything
 * from offset up until the end of the
 * array.
 * </p>
 * @param bool $preserve_keys [optional] <p>
 * Note that array_slice will reorder and reset the
 * numeric array indices by default. You can change this behaviour by setting
 * preserve_keys to true.
 * </p>
 * @return array the slice.
 */
function array_slice (array $array, $offset, $length = null, $preserve_keys = null) {}

/**
 * Merge one or more arrays
 * @link http://www.php.net/manual/en/function.array-merge.php
 * @param array $array1 <p>
 * Initial array to merge.
 * </p>
 * @param array $_ [optional] 
 * @return array the resulting array.
 */
function array_merge (array $array1, array $_ = null) {}

/**
 * Merge two or more arrays recursively
 * @link http://www.php.net/manual/en/function.array-merge-recursive.php
 * @param array $array1 <p>
 * Initial array to merge.
 * </p>
 * @param array $_ [optional] 
 * @return array An array of values resulted from merging the arguments together.
 */
function array_merge_recursive (array $array1, array $_ = null) {}

/**
 * Replaces elements from passed arrays into the first array
 * @link http://www.php.net/manual/en/function.array-replace.php
 * @param array $array1 <p>
 * The array in which elements are replaced.
 * </p>
 * @param array $array2 <p>
 * The array from which elements will be extracted.
 * </p>
 * @param array $_ [optional] 
 * @return array an array, or &null; if an error occurs.
 */
function array_replace (array $array1, array $array2, array $_ = null) {}

/**
 * Replaces elements from passed arrays into the first array recursively
 * @link http://www.php.net/manual/en/function.array-replace-recursive.php
 * @param array $array1 <p>
 * The array in which elements are replaced.
 * </p>
 * @param array $array2 <p>
 * The array from which elements will be extracted.
 * </p>
 * @param array $_ [optional] 
 * @return array an array, or &null; if an error occurs.
 */
function array_replace_recursive (array $array1, array $array2, array $_ = null) {}

/**
 * Return all the keys or a subset of the keys of an array
 * @link http://www.php.net/manual/en/function.array-keys.php
 * @param array $array <p>
 * An array containing keys to return.
 * </p>
 * @param mixed $search_value [optional] <p>
 * If specified, then only keys containing these values are returned.
 * </p>
 * @param bool $strict [optional] <p>
 * Determines if strict comparison (===) should be used during the search.
 * </p>
 * @return array an array of all the keys in array.
 */
function array_keys (array $array, $search_value = null, $strict = null) {}

/**
 * Return all the values of an array
 * @link http://www.php.net/manual/en/function.array-values.php
 * @param array $array <p>
 * The array.
 * </p>
 * @return array an indexed array of values.
 */
function array_values (array $array) {}

/**
 * Counts all the values of an array
 * @link http://www.php.net/manual/en/function.array-count-values.php
 * @param array $array <p>
 * The array of values to count
 * </p>
 * @return array an associative array of values from array as
 * keys and their count as value.
 */
function array_count_values (array $array) {}

/**
 * Return the values from a single column in the input array
 * @link http://www.php.net/manual/en/function.array-column.php
 * @param array $array <p>
 * A multi-dimensional array (record set) from which to pull a column of
 * values.
 * </p>
 * @param mixed $column_key <p>
 * The column of values to return. This value may be the integer key of the
 * column you wish to retrieve, or it may be the string key name for an
 * associative array. It may also be &null; to return complete arrays
 * (useful together with index_key to reindex the
 * array).
 * </p>
 * @param mixed $index_key [optional] <p>
 * The column to use as the index/keys for the returned array. This value
 * may be the integer key of the column, or it may be the string key name.
 * </p>
 * @return array an array of values representing a single column from the input array.
 */
function array_column (array $array, $column_key, $index_key = null) {}

/**
 * Return an array with elements in reverse order
 * @link http://www.php.net/manual/en/function.array-reverse.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @param bool $preserve_keys [optional] <p>
 * If set to true numeric keys are preserved. 
 * Non-numeric keys are not affected by this setting and will always be preserved.
 * </p>
 * @return array the reversed array.
 */
function array_reverse (array $array, $preserve_keys = null) {}

/**
 * Iteratively reduce the array to a single value using a callback function
 * @link http://www.php.net/manual/en/function.array-reduce.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @param callable $callback mixedcallback
 * mixedcarry
 * mixeditem
 * carry
 * <p>
 * Holds the return value of the previous iteration; in the case of the
 * first iteration it instead holds the value of 
 * initial.
 * </p>
 * @param mixed $initial [optional] <p>
 * If the optional initial is available, it will
 * be used at the beginning of the process, or as a final result in case
 * the array is empty.
 * </p>
 * @return mixed the resulting value.
 * </p>
 * <p>
 * If the array is empty and initial is not passed,
 * array_reduce returns &null;.
 */
function array_reduce (array $array, $callback, $initial = null) {}

/**
 * Pad array to the specified length with a value
 * @link http://www.php.net/manual/en/function.array-pad.php
 * @param array $array <p>
 * Initial array of values to pad.
 * </p>
 * @param int $size <p>
 * New size of the array.
 * </p>
 * @param mixed $value <p>
 * Value to pad if array is less than
 * size.
 * </p>
 * @return array a copy of the array padded to size specified
 * by size with value 
 * value. If size is 
 * positive then the array is padded on the right, if it's negative then 
 * on the left. If the absolute value of size is less
 * than or equal to the length of the array then no
 * padding takes place.
 */
function array_pad (array $array, $size, $value) {}

/**
 * Exchanges all keys with their associated values in an array
 * @link http://www.php.net/manual/en/function.array-flip.php
 * @param array $array <p>
 * An array of key/value pairs to be flipped.
 * </p>
 * @return array the flipped array on success and &null; on failure.
 */
function array_flip (array $array) {}

/**
 * Changes the case of all keys in an array
 * @link http://www.php.net/manual/en/function.array-change-key-case.php
 * @param array $array <p>
 * The array to work on
 * </p>
 * @param int $case [optional] <p>
 * Either CASE_UPPER or
 * CASE_LOWER (default)
 * </p>
 * @return array an array with its keys lower or uppercased, or false if
 * array is not an array.
 */
function array_change_key_case (array $array, $case = null) {}

/**
 * Pick one or more random entries out of an array
 * @link http://www.php.net/manual/en/function.array-rand.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @param int $num [optional] <p>
 * Specifies how many entries should be picked.
 * </p>
 * @return mixed When picking only one entry, array_rand returns
 * the key for a random entry. Otherwise, an array of keys for the random
 * entries is returned. This is done so that random keys can be picked
 * from the array as well as random values. Trying to pick more elements
 * than there are in the array will result in an
 * E_WARNING level error, and NULL will be returned.
 */
function array_rand (array $array, $num = null) {}

/**
 * Removes duplicate values from an array
 * @link http://www.php.net/manual/en/function.array-unique.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @param int $sort_flags [optional] <p>
 * The optional second parameter sort_flags
 * may be used to modify the sorting behavior using these values:
 * </p>
 * <p>
 * Sorting type flags:
 * SORT_REGULAR - compare items normally
 * (don't change types)
 * @return array the filtered array.
 */
function array_unique (array $array, $sort_flags = null) {}

/**
 * Computes the intersection of arrays
 * @link http://www.php.net/manual/en/function.array-intersect.php
 * @param array $array1 <p>
 * The array with master values to check.
 * </p>
 * @param array $array2 <p>
 * An array to compare values against.
 * </p>
 * @param array $_ [optional] 
 * @return array an array containing all of the values in 
 * array1 whose values exist in all of the parameters.
 */
function array_intersect (array $array1, array $array2, array $_ = null) {}

/**
 * Computes the intersection of arrays using keys for comparison
 * @link http://www.php.net/manual/en/function.array-intersect-key.php
 * @param array $array1 <p>
 * The array with master keys to check.
 * </p>
 * @param array $array2 <p>
 * An array to compare keys against.
 * </p>
 * @param array $_ [optional] 
 * @return array an associative array containing all the entries of 
 * array1 which have keys that are present in all
 * arguments.
 */
function array_intersect_key (array $array1, array $array2, array $_ = null) {}

/**
 * Computes the intersection of arrays using a callback function on the keys for comparison
 * @link http://www.php.net/manual/en/function.array-intersect-ukey.php
 * @param array $array1 <p>
 * Initial array for comparison of the arrays.
 * </p>
 * @param array $array2 <p>
 * First array to compare keys against.
 * </p>
 * @param array $_ [optional] 
 * @param callable $key_compare_func <p>
 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
 * </p>
 * &callback.cmp;
 * @return array the values of array1 whose keys exist
 * in all the arguments.
 */
function array_intersect_ukey (array $array1, array $array2, array $_ = null, $key_compare_func) {}

/**
 * Computes the intersection of arrays, compares data by a callback function
 * @link http://www.php.net/manual/en/function.array-uintersect.php
 * @param array $array1 <p>
 * The first array.
 * </p>
 * @param array $array2 <p>
 * The second array.
 * </p>
 * @param array $_ [optional] 
 * @param callable $value_compare_func <p>
 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
 * </p>
 * &callback.cmp;
 * @return array an array containing all the values of array1
 * that are present in all the arguments.
 */
function array_uintersect (array $array1, array $array2, array $_ = null, $value_compare_func) {}

/**
 * Computes the intersection of arrays with additional index check
 * @link http://www.php.net/manual/en/function.array-intersect-assoc.php
 * @param array $array1 <p>
 * The array with master values to check.
 * </p>
 * @param array $array2 <p>
 * An array to compare values against.
 * </p>
 * @param array $_ [optional] 
 * @return array an associative array containing all the values in 
 * array1 that are present in all of the arguments.
 */
function array_intersect_assoc (array $array1, array $array2, array $_ = null) {}

/**
 * Computes the intersection of arrays with additional index check, compares data by a callback function
 * @link http://www.php.net/manual/en/function.array-uintersect-assoc.php
 * @param array $array1 <p>
 * The first array.
 * </p>
 * @param array $array2 <p>
 * The second array.
 * </p>
 * @param array $_ [optional] 
 * @param callable $value_compare_func <p>
 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
 * </p>
 * &callback.cmp;
 * @return array an array containing all the values of
 * array1 that are present in all the arguments.
 */
function array_uintersect_assoc (array $array1, array $array2, array $_ = null, $value_compare_func) {}

/**
 * Computes the intersection of arrays with additional index check, compares indexes by a callback function
 * @link http://www.php.net/manual/en/function.array-intersect-uassoc.php
 * @param array $array1 <p>
 * Initial array for comparison of the arrays.
 * </p>
 * @param array $array2 <p>
 * First array to compare keys against.
 * </p>
 * @param array $_ [optional] 
 * @param callable $key_compare_func <p>
 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
 * </p>
 * &callback.cmp;
 * @return array the values of array1 whose values exist
 * in all of the arguments.
 */
function array_intersect_uassoc (array $array1, array $array2, array $_ = null, $key_compare_func) {}

/**
 * Computes the intersection of arrays with additional index check, compares data and indexes by separate callback functions
 * @link http://www.php.net/manual/en/function.array-uintersect-uassoc.php
 * @param array $array1 <p>
 * The first array.
 * </p>
 * @param array $array2 <p>
 * The second array.
 * </p>
 * @param array $_ [optional] 
 * @param callable $value_compare_func <p>
 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
 * </p>
 * &callback.cmp;
 * @param callable $key_compare_func <p>
 * Key comparison callback function.
 * </p>
 * @return array an array containing all the values of
 * array1 that are present in all the arguments.
 */
function array_uintersect_uassoc (array $array1, array $array2, array $_ = null, $value_compare_func, $key_compare_func) {}

/**
 * Computes the difference of arrays
 * @link http://www.php.net/manual/en/function.array-diff.php
 * @param array $array1 <p>
 * The array to compare from
 * </p>
 * @param array $array2 <p>
 * An array to compare against
 * </p>
 * @param array $_ [optional] 
 * @return array an array containing all the entries from
 * array1 that are not present in any of the other arrays.
 */
function array_diff (array $array1, array $array2, array $_ = null) {}

/**
 * Computes the difference of arrays using keys for comparison
 * @link http://www.php.net/manual/en/function.array-diff-key.php
 * @param array $array1 <p>
 * The array to compare from
 * </p>
 * @param array $array2 <p>
 * An array to compare against
 * </p>
 * @param array $_ [optional] 
 * @return array an array containing all the entries from
 * array1 whose keys are not present in any of the
 * other arrays.
 */
function array_diff_key (array $array1, array $array2, array $_ = null) {}

/**
 * Computes the difference of arrays using a callback function on the keys for comparison
 * @link http://www.php.net/manual/en/function.array-diff-ukey.php
 * @param array $array1 <p>
 * The array to compare from
 * </p>
 * @param array $array2 <p>
 * An array to compare against
 * </p>
 * @param array $_ [optional] 
 * @param callable $key_compare_func <p>
 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
 * </p>
 * &callback.cmp;
 * @return array an array containing all the entries from
 * array1 that are not present in any of the other arrays.
 */
function array_diff_ukey (array $array1, array $array2, array $_ = null, $key_compare_func) {}

/**
 * Computes the difference of arrays by using a callback function for data comparison
 * @link http://www.php.net/manual/en/function.array-udiff.php
 * @param array $array1 <p>
 * The first array.
 * </p>
 * @param array $array2 <p>
 * The second array.
 * </p>
 * @param array $_ [optional] 
 * @param callable $value_compare_func <p>
 * The callback comparison function.
 * </p>
 * <p>
 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
 * </p>
 * &callback.cmp;
 * @return array an array containing all the values of array1
 * that are not present in any of the other arguments.
 */
function array_udiff (array $array1, array $array2, array $_ = null, $value_compare_func) {}

/**
 * Computes the difference of arrays with additional index check
 * @link http://www.php.net/manual/en/function.array-diff-assoc.php
 * @param array $array1 <p>
 * The array to compare from
 * </p>
 * @param array $array2 <p>
 * An array to compare against
 * </p>
 * @param array $_ [optional] 
 * @return array an array containing all the values from
 * array1 that are not present in any of the other arrays.
 */
function array_diff_assoc (array $array1, array $array2, array $_ = null) {}

/**
 * Computes the difference of arrays with additional index check, compares data by a callback function
 * @link http://www.php.net/manual/en/function.array-udiff-assoc.php
 * @param array $array1 <p>
 * The first array.
 * </p>
 * @param array $array2 <p>
 * The second array.
 * </p>
 * @param array $_ [optional] 
 * @param callable $value_compare_func <p>
 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
 * </p>
 * &callback.cmp;
 * @return array array_udiff_assoc returns an array
 * containing all the values from array1
 * that are not present in any of the other arguments.
 * Note that the keys are used in the comparison unlike
 * array_diff and array_udiff.
 * The comparison of arrays' data is performed by using an user-supplied
 * callback. In this aspect the behaviour is opposite to the behaviour of
 * array_diff_assoc which uses internal function for
 * comparison.
 */
function array_udiff_assoc (array $array1, array $array2, array $_ = null, $value_compare_func) {}

/**
 * Computes the difference of arrays with additional index check which is performed by a user supplied callback function
 * @link http://www.php.net/manual/en/function.array-diff-uassoc.php
 * @param array $array1 <p>
 * The array to compare from
 * </p>
 * @param array $array2 <p>
 * An array to compare against
 * </p>
 * @param array $_ [optional] 
 * @param callable $key_compare_func <p>
 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
 * </p>
 * &callback.cmp;
 * @return array an array containing all the entries from
 * array1 that are not present in any of the other arrays.
 */
function array_diff_uassoc (array $array1, array $array2, array $_ = null, $key_compare_func) {}

/**
 * Computes the difference of arrays with additional index check, compares data and indexes by a callback function
 * @link http://www.php.net/manual/en/function.array-udiff-uassoc.php
 * @param array $array1 <p>
 * The first array.
 * </p>
 * @param array $array2 <p>
 * The second array.
 * </p>
 * @param array $_ [optional] 
 * @param callable $value_compare_func <p>
 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
 * </p>
 * &callback.cmp;
 * @param callable $key_compare_func <p>
 * The comparison of keys (indices) is done also by the callback function
 * key_compare_func. This behaviour is unlike what
 * array_udiff_assoc does, since the latter compares
 * the indices by using an internal function.
 * </p>
 * @return array an array containing all the values from
 * array1 that are not present in any of the other
 * arguments.
 */
function array_udiff_uassoc (array $array1, array $array2, array $_ = null, $value_compare_func, $key_compare_func) {}

/**
 * Calculate the sum of values in an array
 * @link http://www.php.net/manual/en/function.array-sum.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @return number the sum of values as an integer or float.
 */
function array_sum (array $array) {}

/**
 * Calculate the product of values in an array
 * @link http://www.php.net/manual/en/function.array-product.php
 * @param array $array <p>
 * The array.
 * </p>
 * @return number the product as an integer or float.
 */
function array_product (array $array) {}

/**
 * Filters elements of an array using a callback function
 * @link http://www.php.net/manual/en/function.array-filter.php
 * @param array $array <p>
 * The array to iterate over
 * </p>
 * @param callable $callback [optional] <p>
 * The callback function to use
 * </p>
 * <p>
 * If no callback is supplied, all entries of
 * array equal to false (see
 * converting to
 * boolean) will be removed.
 * </p>
 * @param int $flag [optional] <p>
 * Flag determining what arguments are sent to callback:
 * ARRAY_FILTER_USE_KEY - pass key as the only argument
 * to callback instead of the value
 * @return array the filtered array.
 */
function array_filter (array $array, $callback = null, $flag = null) {}

/**
 * Applies the callback to the elements of the given arrays
 * @link http://www.php.net/manual/en/function.array-map.php
 * @param callable $callback <p>
 * Callback function to run for each element in each array.
 * </p>
 * @param array $array1 <p>
 * An array to run through the callback function.
 * </p>
 * @param array $_ [optional] 
 * @return array an array containing all the elements of array1
 * after applying the callback function to each one.
 */
function array_map ($callback, array $array1, array $_ = null) {}

/**
 * Split an array into chunks
 * @link http://www.php.net/manual/en/function.array-chunk.php
 * @param array $array <p>
 * The array to work on
 * </p>
 * @param int $size <p>
 * The size of each chunk
 * </p>
 * @param bool $preserve_keys [optional] <p>
 * When set to true keys will be preserved.
 * Default is false which will reindex the chunk numerically
 * </p>
 * @return array a multidimensional numerically indexed array, starting with zero,
 * with each dimension containing size elements.
 */
function array_chunk (array $array, $size, $preserve_keys = null) {}

/**
 * Creates an array by using one array for keys and another for its values
 * @link http://www.php.net/manual/en/function.array-combine.php
 * @param array $keys <p>
 * Array of keys to be used. Illegal values for key will be
 * converted to string.
 * </p>
 * @param array $values <p>
 * Array of values to be used
 * </p>
 * @return array the combined array, false if the number of elements
 * for each array isn't equal.
 */
function array_combine (array $keys, array $values) {}

/**
 * Checks if the given key or index exists in the array
 * @link http://www.php.net/manual/en/function.array-key-exists.php
 * @param mixed $key <p>
 * Value to check.
 * </p>
 * @param array $array <p>
 * An array with keys to check.
 * </p>
 * @return bool true on success or false on failure
 */
function array_key_exists ($key, array $array) {}

/**
 * &Alias; <function>current</function>
 * @link http://www.php.net/manual/en/function.pos.php
 * @param $arg
 */
function pos ($arg) {}

/**
 * &Alias; <function>count</function>
 * @link http://www.php.net/manual/en/function.sizeof.php
 * @param $var
 * @param $mode [optional]
 */
function sizeof ($var, $mode = null) {}

/**
 * &Alias; <function>array_key_exists</function>
 * @link http://www.php.net/manual/en/function.key-exists.php
 * @param $key
 * @param $search
 */
function key_exists ($key, $search) {}

/**
 * Checks if assertion is false
 * @link http://www.php.net/manual/en/function.assert.php
 * @param mixed $assertion <p>
 * The assertion.
 * </p>
 * @param string $description [optional] <p>
 * An optional description that will be included in the failure message if
 * the assertion fails.
 * </p>
 * @return bool false if the assertion is false, true otherwise.
 */
function assert ($assertion, $description = null) {}

/**
 * Set/get the various assert flags
 * @link http://www.php.net/manual/en/function.assert-options.php
 * @param int $what <p>
 * <table>
 * Assert Options
 * <tr valign="top">
 * <td>Option</td>
 * <td>INI Setting</td>
 * <td>Default value</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>ASSERT_ACTIVE</td>
 * <td>assert.active</td>
 * <td>1</td>
 * <td>enable assert evaluation</td>
 * </tr>
 * <tr valign="top">
 * <td>ASSERT_WARNING</td>
 * <td>assert.warning</td>
 * <td>1</td>
 * <td>issue a PHP warning for each failed assertion</td>
 * </tr>
 * <tr valign="top">
 * <td>ASSERT_BAIL</td>
 * <td>assert.bail</td>
 * <td>0</td>
 * <td>terminate execution on failed assertions</td>
 * </tr>
 * <tr valign="top">
 * <td>ASSERT_QUIET_EVAL</td>
 * <td>assert.quiet_eval</td>
 * <td>0</td>
 * <td>
 * disable error_reporting during assertion expression
 * evaluation
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ASSERT_CALLBACK</td>
 * <td>assert.callback</td>
 * <td)<&null;)</td>
 * <td>Callback to call on failed assertions</td>
 * </tr>
 * </table>
 * </p>
 * @param mixed $value [optional] <p>
 * An optional new value for the option.
 * </p>
 * @return mixed the original setting of any option or false on errors.
 */
function assert_options ($what, $value = null) {}

/**
 * Compares two "PHP-standardized" version number strings
 * @link http://www.php.net/manual/en/function.version-compare.php
 * @param string $version1 <p>
 * First version number.
 * </p>
 * @param string $version2 <p>
 * Second version number.
 * </p>
 * @param string $operator [optional] <p>
 * If the third optional operator argument is
 * specified, test for a particular relationship. The possible operators
 * are: &lt;, lt,
 * &lt;=, le, &gt;,
 * gt, &gt;=, ge,
 * ==, =, eq,
 * !=, &lt;&gt;, ne
 * respectively. 
 * </p>
 * <p>
 * This parameter is case-sensitive, values should be lowercase.
 * </p>
 * @return mixed By default, version_compare returns
 * -1 if the first version is lower than the second, 
 * 0 if they are equal, and
 * 1 if the second is lower. 
 * </p>
 * <p>
 * When using the optional operator argument, the
 * function will return true if the relationship is the one specified
 * by the operator, false otherwise.
 */
function version_compare ($version1, $version2, $operator = null) {}

/**
 * Convert a pathname and a project identifier to a System V IPC key
 * @link http://www.php.net/manual/en/function.ftok.php
 * @param string $pathname <p>
 * Path to an accessible file.
 * </p>
 * @param string $proj <p>
 * Project identifier. This must be a one character string.
 * </p>
 * @return int On success the return value will be the created key value, otherwise
 * -1 is returned.
 */
function ftok ($pathname, $proj) {}

/**
 * Perform the rot13 transform on a string
 * @link http://www.php.net/manual/en/function.str-rot13.php
 * @param string $str <p>
 * The input string.
 * </p>
 * @return string the ROT13 version of the given string.
 */
function str_rot13 ($str) {}

/**
 * Retrieve list of registered filters
 * @link http://www.php.net/manual/en/function.stream-get-filters.php
 * @return array an indexed array containing the name of all stream filters
 * available.
 */
function stream_get_filters () {}

/**
 * Register a user defined stream filter
 * @link http://www.php.net/manual/en/function.stream-filter-register.php
 * @param string $filtername <p>
 * The filter name to be registered.
 * </p>
 * @param string $classname <p>
 * To implement a filter, you need to define a class as an extension of
 * php_user_filter with a number of member
 * functions. When performing read/write operations on the stream
 * to which your filter is attached, PHP will pass the data through your
 * filter (and any other filters attached to that stream) so that the
 * data may be modified as desired. You must implement the methods
 * exactly as described in php_user_filter - doing
 * otherwise will lead to undefined behaviour.
 * </p>
 * @return bool true on success or false on failure
 * </p>
 * <p>
 * stream_filter_register will return false if the
 * filtername is already defined.
 */
function stream_filter_register ($filtername, $classname) {}

/**
 * Return a bucket object from the brigade for operating on
 * @link http://www.php.net/manual/en/function.stream-bucket-make-writeable.php
 * @param resource $brigade 
 * @return object 
 */
function stream_bucket_make_writeable ($brigade) {}

/**
 * Prepend bucket to brigade
 * @link http://www.php.net/manual/en/function.stream-bucket-prepend.php
 * @param resource $brigade <p>
 * brigade is a resource pointing to a bucket brigade
 * which contains one or more bucket objects.
 * </p>
 * @param object $bucket <p>
 * A bucket object.
 * </p>
 * @return void 
 */
function stream_bucket_prepend ($brigade, $bucket) {}

/**
 * Append bucket to brigade
 * @link http://www.php.net/manual/en/function.stream-bucket-append.php
 * @param resource $brigade 
 * @param object $bucket 
 * @return void 
 */
function stream_bucket_append ($brigade, $bucket) {}

/**
 * Create a new bucket for use on the current stream
 * @link http://www.php.net/manual/en/function.stream-bucket-new.php
 * @param resource $stream 
 * @param string $buffer 
 * @return object 
 */
function stream_bucket_new ($stream, $buffer) {}

/**
 * Add URL rewriter values
 * @link http://www.php.net/manual/en/function.output-add-rewrite-var.php
 * @param string $name <p>
 * The variable name.
 * </p>
 * @param string $value <p>
 * The variable value.
 * </p>
 * @return bool true on success or false on failure
 */
function output_add_rewrite_var ($name, $value) {}

/**
 * Reset URL rewriter values
 * @link http://www.php.net/manual/en/function.output-reset-rewrite-vars.php
 * @return bool true on success or false on failure
 */
function output_reset_rewrite_vars () {}

/**
 * Returns directory path used for temporary files
 * @link http://www.php.net/manual/en/function.sys-get-temp-dir.php
 * @return string the path of the temporary directory.
 */
function sys_get_temp_dir () {}

/**
 * Loads a PHP extension at runtime
 * @link http://www.php.net/manual/en/function.dl.php
 * @param string $library <p>
 * This parameter is only the filename of the
 * extension to load which also depends on your platform. For example,
 * the sockets extension (if compiled
 * as a shared module, not the default!) would be called 
 * sockets.so on Unix platforms whereas it is called
 * php_sockets.dll on the Windows platform.
 * </p>
 * <p>
 * The directory where the extension is loaded from depends on your
 * platform:
 * </p>
 * <p>
 * Windows - If not explicitly set in the &php.ini;, the extension is
 * loaded from C:\php4\extensions\ (PHP 4) or 
 * C:\php5\ (PHP 5) by default.
 * </p>
 * <p>
 * Unix - If not explicitly set in the &php.ini;, the default extension
 * directory depends on
 * whether PHP has been built with --enable-debug
 * or not
 * @return bool true on success or false on failure If the functionality of loading modules is not available
 * or has been disabled (either by setting
 * enable_dl off or by enabling safe mode
 * in &php.ini;) an E_ERROR is emitted
 * and execution is stopped. If dl fails because the
 * specified library couldn't be loaded, in addition to false an
 * E_WARNING message is emitted.
 */
function dl ($library) {}

/**
 * Sets the process title
 * @link http://www.php.net/manual/en/function.cli-set-process-title.php
 * @param string $title <p>
 * The new title.
 * </p>
 * @return bool true on success or false on failure
 */
function cli_set_process_title ($title) {}

/**
 * Returns the current process title
 * @link http://www.php.net/manual/en/function.cli-get-process-title.php
 * @return string Return a string with the current process title or &null; on error.
 */
function cli_get_process_title () {}

define ('CONNECTION_ABORTED', 1);
define ('CONNECTION_NORMAL', 0);
define ('CONNECTION_TIMEOUT', 2);
define ('INI_USER', 1);
define ('INI_PERDIR', 2);
define ('INI_SYSTEM', 4);
define ('INI_ALL', 7);
define ('INI_SCANNER_NORMAL', 0);
define ('INI_SCANNER_RAW', 1);
define ('INI_SCANNER_TYPED', 2);
define ('PHP_URL_SCHEME', 0);
define ('PHP_URL_HOST', 1);
define ('PHP_URL_PORT', 2);
define ('PHP_URL_USER', 3);
define ('PHP_URL_PASS', 4);
define ('PHP_URL_PATH', 5);
define ('PHP_URL_QUERY', 6);
define ('PHP_URL_FRAGMENT', 7);
define ('PHP_QUERY_RFC1738', 1);
define ('PHP_QUERY_RFC3986', 2);
define ('M_E', 2.718281828459);
define ('M_LOG2E', 1.442695040889);
define ('M_LOG10E', 0.43429448190325);
define ('M_LN2', 0.69314718055995);
define ('M_LN10', 2.302585092994);
define ('M_PI', 3.1415926535898);
define ('M_PI_2', 1.5707963267949);
define ('M_PI_4', 0.78539816339745);
define ('M_1_PI', 0.31830988618379);
define ('M_2_PI', 0.63661977236758);
define ('M_SQRTPI', 1.7724538509055);
define ('M_2_SQRTPI', 1.1283791670955);
define ('M_LNPI', 1.1447298858494);
define ('M_EULER', 0.57721566490153);
define ('M_SQRT2', 1.4142135623731);
define ('M_SQRT1_2', 0.70710678118655);
define ('M_SQRT3', 1.7320508075689);
define ('INF', INF);
define ('NAN', NAN);
define ('PHP_ROUND_HALF_UP', 1);
define ('PHP_ROUND_HALF_DOWN', 2);
define ('PHP_ROUND_HALF_EVEN', 3);
define ('PHP_ROUND_HALF_ODD', 4);
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
define ('ENT_IGNORE', 4);
define ('ENT_SUBSTITUTE', 8);
define ('ENT_DISALLOWED', 128);
define ('ENT_HTML401', 0);
define ('ENT_XML1', 16);
define ('ENT_XHTML', 32);
define ('ENT_HTML5', 48);
define ('STR_PAD_LEFT', 0);
define ('STR_PAD_RIGHT', 1);
define ('STR_PAD_BOTH', 2);
define ('PATHINFO_DIRNAME', 1);
define ('PATHINFO_BASENAME', 2);
define ('PATHINFO_EXTENSION', 4);
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
define ('STREAM_NOTIFY_CONNECT', 2);
define ('STREAM_NOTIFY_AUTH_REQUIRED', 3);
define ('STREAM_NOTIFY_AUTH_RESULT', 10);
define ('STREAM_NOTIFY_MIME_TYPE_IS', 4);
define ('STREAM_NOTIFY_FILE_SIZE_IS', 5);
define ('STREAM_NOTIFY_REDIRECTED', 6);
define ('STREAM_NOTIFY_PROGRESS', 7);
define ('STREAM_NOTIFY_FAILURE', 9);
define ('STREAM_NOTIFY_COMPLETED', 8);
define ('STREAM_NOTIFY_RESOLVE', 1);
define ('STREAM_NOTIFY_SEVERITY_INFO', 0);
define ('STREAM_NOTIFY_SEVERITY_WARN', 1);
define ('STREAM_NOTIFY_SEVERITY_ERR', 2);
define ('STREAM_FILTER_READ', 1);
define ('STREAM_FILTER_WRITE', 2);
define ('STREAM_FILTER_ALL', 3);
define ('STREAM_CLIENT_PERSISTENT', 1);
define ('STREAM_CLIENT_ASYNC_CONNECT', 2);
define ('STREAM_CLIENT_CONNECT', 4);
define ('STREAM_CRYPTO_METHOD_ANY_CLIENT', 63);
define ('STREAM_CRYPTO_METHOD_SSLv2_CLIENT', 3);
define ('STREAM_CRYPTO_METHOD_SSLv3_CLIENT', 5);
define ('STREAM_CRYPTO_METHOD_SSLv23_CLIENT', 57);
define ('STREAM_CRYPTO_METHOD_TLS_CLIENT', 9);
define ('STREAM_CRYPTO_METHOD_TLSv1_0_CLIENT', 9);
define ('STREAM_CRYPTO_METHOD_TLSv1_1_CLIENT', 17);
define ('STREAM_CRYPTO_METHOD_TLSv1_2_CLIENT', 33);
define ('STREAM_CRYPTO_METHOD_ANY_SERVER', 62);
define ('STREAM_CRYPTO_METHOD_SSLv2_SERVER', 2);
define ('STREAM_CRYPTO_METHOD_SSLv3_SERVER', 4);
define ('STREAM_CRYPTO_METHOD_SSLv23_SERVER', 56);
define ('STREAM_CRYPTO_METHOD_TLS_SERVER', 8);
define ('STREAM_CRYPTO_METHOD_TLSv1_0_SERVER', 8);
define ('STREAM_CRYPTO_METHOD_TLSv1_1_SERVER', 16);
define ('STREAM_CRYPTO_METHOD_TLSv1_2_SERVER', 32);
define ('STREAM_SHUT_RD', 0);
define ('STREAM_SHUT_WR', 1);
define ('STREAM_SHUT_RDWR', 2);
define ('STREAM_PF_INET', 2);
define ('STREAM_PF_INET6', 10);
define ('STREAM_PF_UNIX', 1);
define ('STREAM_IPPROTO_IP', 0);
define ('STREAM_IPPROTO_TCP', 6);
define ('STREAM_IPPROTO_UDP', 17);
define ('STREAM_IPPROTO_ICMP', 1);
define ('STREAM_IPPROTO_RAW', 255);
define ('STREAM_SOCK_STREAM', 1);
define ('STREAM_SOCK_DGRAM', 2);
define ('STREAM_SOCK_RAW', 3);
define ('STREAM_SOCK_SEQPACKET', 5);
define ('STREAM_SOCK_RDM', 4);
define ('STREAM_PEEK', 2);
define ('STREAM_OOB', 1);
define ('STREAM_SERVER_BIND', 4);
define ('STREAM_SERVER_LISTEN', 8);
define ('FILE_USE_INCLUDE_PATH', 1);
define ('FILE_IGNORE_NEW_LINES', 2);
define ('FILE_SKIP_EMPTY_LINES', 4);
define ('FILE_APPEND', 8);
define ('FILE_NO_DEFAULT_CONTEXT', 16);
define ('FILE_TEXT', 0);
define ('FILE_BINARY', 0);
define ('FNM_NOESCAPE', 2);
define ('FNM_PATHNAME', 1);
define ('FNM_PERIOD', 4);
define ('FNM_CASEFOLD', 16);
define ('PSFS_PASS_ON', 2);
define ('PSFS_FEED_ME', 1);
define ('PSFS_ERR_FATAL', 0);
define ('PSFS_FLAG_NORMAL', 0);
define ('PSFS_FLAG_FLUSH_INC', 1);
define ('PSFS_FLAG_FLUSH_CLOSE', 2);
define ('PASSWORD_DEFAULT', 1);
define ('PASSWORD_BCRYPT', 1);
define ('PASSWORD_BCRYPT_DEFAULT_COST', 10);
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
define ('CRYPT_SALT_LENGTH', 123);
define ('CRYPT_STD_DES', 1);
define ('CRYPT_EXT_DES', 1);
define ('CRYPT_MD5', 1);
define ('CRYPT_BLOWFISH', 1);
define ('CRYPT_SHA256', 1);
define ('CRYPT_SHA512', 1);
define ('DIRECTORY_SEPARATOR', "/");
define ('PATH_SEPARATOR', ":");
define ('SCANDIR_SORT_ASCENDING', 0);
define ('SCANDIR_SORT_DESCENDING', 1);
define ('SCANDIR_SORT_NONE', 2);
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
define ('SORT_ASC', 4);
define ('SORT_DESC', 3);
define ('SORT_REGULAR', 0);
define ('SORT_NUMERIC', 1);
define ('SORT_STRING', 2);
define ('SORT_LOCALE_STRING', 5);
define ('SORT_NATURAL', 6);
define ('SORT_FLAG_CASE', 8);
define ('CASE_LOWER', 0);
define ('CASE_UPPER', 1);
define ('COUNT_NORMAL', 0);
define ('COUNT_RECURSIVE', 1);
define ('ARRAY_FILTER_USE_BOTH', 1);
define ('ARRAY_FILTER_USE_KEY', 2);
define ('ASSERT_ACTIVE', 1);
define ('ASSERT_CALLBACK', 2);
define ('ASSERT_BAIL', 3);
define ('ASSERT_WARNING', 4);
define ('ASSERT_QUIET_EVAL', 5);
define ('ASSERT_EXCEPTION', 6);
define ('STREAM_USE_PATH', 1);
define ('STREAM_IGNORE_URL', 2);
define ('STREAM_REPORT_ERRORS', 8);
define ('STREAM_MUST_SEEK', 16);
define ('STREAM_URL_STAT_LINK', 1);
define ('STREAM_URL_STAT_QUIET', 2);
define ('STREAM_MKDIR_RECURSIVE', 1);
define ('STREAM_IS_URL', 1);
define ('STREAM_OPTION_BLOCKING', 1);
define ('STREAM_OPTION_READ_TIMEOUT', 4);
define ('STREAM_OPTION_READ_BUFFER', 2);
define ('STREAM_OPTION_WRITE_BUFFER', 3);
define ('STREAM_BUFFER_NONE', 0);
define ('STREAM_BUFFER_LINE', 1);
define ('STREAM_BUFFER_FULL', 2);
define ('STREAM_CAST_AS_STREAM', 0);
define ('STREAM_CAST_FOR_SELECT', 3);
define ('STREAM_META_TOUCH', 1);
define ('STREAM_META_OWNER', 3);
define ('STREAM_META_OWNER_NAME', 2);
define ('STREAM_META_GROUP', 5);
define ('STREAM_META_GROUP_NAME', 4);
define ('STREAM_META_ACCESS', 6);
define ('IMAGETYPE_GIF', 1);
define ('IMAGETYPE_JPEG', 2);
define ('IMAGETYPE_PNG', 3);
define ('IMAGETYPE_SWF', 4);
define ('IMAGETYPE_PSD', 5);
define ('IMAGETYPE_BMP', 6);
define ('IMAGETYPE_TIFF_II', 7);
define ('IMAGETYPE_TIFF_MM', 8);
define ('IMAGETYPE_JPC', 9);
define ('IMAGETYPE_JP2', 10);
define ('IMAGETYPE_JPX', 11);
define ('IMAGETYPE_JB2', 12);
define ('IMAGETYPE_SWC', 13);
define ('IMAGETYPE_IFF', 14);
define ('IMAGETYPE_WBMP', 15);
define ('IMAGETYPE_JPEG2000', 9);
define ('IMAGETYPE_XBM', 16);
define ('IMAGETYPE_ICO', 17);
define ('IMAGETYPE_UNKNOWN', 0);
define ('IMAGETYPE_COUNT', 18);
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

// End of standard v.7.0.0-dev
