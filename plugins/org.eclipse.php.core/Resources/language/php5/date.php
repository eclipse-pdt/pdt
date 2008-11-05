<?php

// Start of date v.5.2.6

class DateTime  {
	const ATOM = "Y-m-d\TH:i:sP";
	const COOKIE = "l, d-M-y H:i:s T";
	const ISO8601 = "Y-m-d\TH:i:sO";
	const RFC822 = "D, d M y H:i:s O";
	const RFC850 = "l, d-M-y H:i:s T";
	const RFC1036 = "D, d M y H:i:s O";
	const RFC1123 = "D, d M Y H:i:s O";
	const RFC2822 = "D, d M Y H:i:s O";
	const RFC3339 = "Y-m-d\TH:i:sP";
	const RSS = "D, d M Y H:i:s O";
	const W3C = "Y-m-d\TH:i:sP";


	public function __construct () {}

	public function format () {}

	public function modify () {}

	public function getTimezone () {}

	public function setTimezone () {}

	public function getOffset () {}

	public function setTime () {}

	public function setDate () {}

	public function setISODate () {}

}

class DateTimeZone  {

	public function __construct () {}

	public function getName () {}

	public function getOffset () {}

	public function getTransitions () {}

	public static function listAbbreviations () {}

	public static function listIdentifiers () {}

}

/**
 * Parse about any English textual datetime description into a Unix timestamp
 * @link http://php.net/manual/en/function.strtotime.php
 * @param time string <p>
 * The string to parse, according to the GNU Date Input Formats
 * syntax. Before PHP 5.0.0, microseconds weren't allowed in the time, since
 * PHP 5.0.0 they are allowed but ignored.
 * </p>
 * @param now int[optional] <p>
 * The timestamp used to calculate the returned value.
 * </p>
 * @return int a timestamp on success, false otherwise. Previous to PHP 5.1.0,
 * this function would return -1 on failure.
 */
function strtotime ($time, $now = null) {}

/**
 * Format a local time/date
 * @link http://php.net/manual/en/function.date.php
 * @param format string <p>
 * The format of the outputted date string. See the formatting
 * options below.
 * </p>
 * <p>
 * <table>
 * The following characters are recognized in the
 * format parameter string
 * <tr valign="top">
 * <td>format character</td>
 * <td>Description</td>
 * <td>Example returned values</td>
 * </tr>
 * <tr valign="top">
 * Day</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>d</td>
 * <td>Day of the month, 2 digits with leading zeros</td>
 * <td>01 to 31</td>
 * </tr>
 * <tr valign="top">
 * <td>D</td>
 * <td>A textual representation of a day, three letters</td>
 * <td>Mon through Sun</td>
 * </tr>
 * <tr valign="top">
 * <td>j</td>
 * <td>Day of the month without leading zeros</td>
 * <td>1 to 31</td>
 * </tr>
 * <tr valign="top">
 * <td>l (lowercase 'L')</td>
 * <td>A full textual representation of the day of the week</td>
 * <td>Sunday through Saturday</td>
 * </tr>
 * <tr valign="top">
 * <td>N</td>
 * <td>ISO-8601 numeric representation of the day of the week (added in
 * PHP 5.1.0)</td>
 * <td>1 (for Monday) through 7 (for Sunday)</td>
 * </tr>
 * <tr valign="top">
 * <td>S</td>
 * <td>English ordinal suffix for the day of the month, 2 characters</td>
 * <td>
 * st, nd, rd or
 * th. Works well with j
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>w</td>
 * <td>Numeric representation of the day of the week</td>
 * <td>0 (for Sunday) through 6 (for Saturday)</td>
 * </tr>
 * <tr valign="top">
 * <td>z</td>
 * <td>The day of the year (starting from 0)</td>
 * <td>0 through 365</td>
 * </tr>
 * <tr valign="top">
 * Week</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>W</td>
 * <td>ISO-8601 week number of year, weeks starting on Monday (added in PHP 4.1.0)</td>
 * <td>Example: 42 (the 42nd week in the year)</td>
 * </tr>
 * <tr valign="top">
 * Month</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>F</td>
 * <td>A full textual representation of a month, such as January or March</td>
 * <td>January through December</td>
 * </tr>
 * <tr valign="top">
 * <td>m</td>
 * <td>Numeric representation of a month, with leading zeros</td>
 * <td>01 through 12</td>
 * </tr>
 * <tr valign="top">
 * <td>M</td>
 * <td>A short textual representation of a month, three letters</td>
 * <td>Jan through Dec</td>
 * </tr>
 * <tr valign="top">
 * <td>n</td>
 * <td>Numeric representation of a month, without leading zeros</td>
 * <td>1 through 12</td>
 * </tr>
 * <tr valign="top">
 * <td>t</td>
 * <td>Number of days in the given month</td>
 * <td>28 through 31</td>
 * </tr>
 * <tr valign="top">
 * Year</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>L</td>
 * <td>Whether it's a leap year</td>
 * <td>1 if it is a leap year, 0 otherwise.</td>
 * </tr>
 * <tr valign="top">
 * <td>o</td>
 * <td>ISO-8601 year number. This has the same value as
 * Y, except that if the ISO week number
 * (W) belongs to the previous or next year, that year
 * is used instead. (added in PHP 5.1.0)</td>
 * <td>Examples: 1999 or 2003</td>
 * </tr>
 * <tr valign="top">
 * <td>Y</td>
 * <td>A full numeric representation of a year, 4 digits</td>
 * <td>Examples: 1999 or 2003</td>
 * </tr>
 * <tr valign="top">
 * <td>y</td>
 * <td>A two digit representation of a year</td>
 * <td>Examples: 99 or 03</td>
 * </tr>
 * <tr valign="top">
 * Time</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>a</td>
 * <td>Lowercase Ante meridiem and Post meridiem</td>
 * <td>am or pm</td>
 * </tr>
 * <tr valign="top">
 * <td>A</td>
 * <td>Uppercase Ante meridiem and Post meridiem</td>
 * <td>AM or PM</td>
 * </tr>
 * <tr valign="top">
 * <td>B</td>
 * <td>Swatch Internet time</td>
 * <td>000 through 999</td>
 * </tr>
 * <tr valign="top">
 * <td>g</td>
 * <td>12-hour format of an hour without leading zeros</td>
 * <td>1 through 12</td>
 * </tr>
 * <tr valign="top">
 * <td>G</td>
 * <td>24-hour format of an hour without leading zeros</td>
 * <td>0 through 23</td>
 * </tr>
 * <tr valign="top">
 * <td>h</td>
 * <td>12-hour format of an hour with leading zeros</td>
 * <td>01 through 12</td>
 * </tr>
 * <tr valign="top">
 * <td>H</td>
 * <td>24-hour format of an hour with leading zeros</td>
 * <td>00 through 23</td>
 * </tr>
 * <tr valign="top">
 * <td>i</td>
 * <td>Minutes with leading zeros</td>
 * <td>00 to 59</td>
 * </tr>
 * <tr valign="top">
 * <td>s</td>
 * <td>Seconds, with leading zeros</td>
 * <td>00 through 59</td>
 * </tr>
 * <tr valign="top">
 * <td>u</td>
 * <td>Microseconds (added in PHP 5.2.2)</td>
 * <td>Example: 54321</td>
 * </tr>
 * <tr valign="top">
 * Timezone</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>e</td>
 * <td>Timezone identifier (added in PHP 5.1.0)</td>
 * <td>Examples: UTC, GMT, Atlantic/Azores</td>
 * </tr>
 * <tr valign="top">
 * <td>I (capital i)</td>
 * <td>Whether or not the date is in daylight saving time</td>
 * <td>1 if Daylight Saving Time, 0 otherwise.</td>
 * </tr>
 * <tr valign="top">
 * <td>O</td>
 * <td>Difference to Greenwich time (GMT) in hours</td>
 * <td>Example: +0200</td>
 * </tr>
 * <tr valign="top">
 * <td>P</td>
 * <td>Difference to Greenwich time (GMT) with colon between hours and minutes (added in PHP 5.1.3)</td>
 * <td>Example: +02:00</td>
 * </tr>
 * <tr valign="top">
 * <td>T</td>
 * <td>Timezone abbreviation</td>
 * <td>Examples: EST, MDT ...</td>
 * </tr>
 * <tr valign="top">
 * <td>Z</td>
 * <td>Timezone offset in seconds. The offset for timezones west of UTC is always
 * negative, and for those east of UTC is always positive.</td>
 * <td>-43200 through 50400</td>
 * </tr>
 * <tr valign="top">
 * Full Date/Time</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>c</td>
 * <td>ISO 8601 date (added in PHP 5)</td>
 * <td>2004-02-12T15:19:21+00:00</td>
 * </tr>
 * <tr valign="top">
 * <td>r</td>
 * <td>RFC 2822 formatted date</td>
 * <td>Example: Thu, 21 Dec 2000 16:01:07 +0200</td>
 * </tr>
 * <tr valign="top">
 * <td>U</td>
 * <td>Seconds since the Unix Epoch (January 1 1970 00:00:00 GMT)</td>
 * <td>See also time</td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * Unrecognized characters in the format string will be printed
 * as-is. The Z format will always return
 * 0 when using gmdate.
 * </p> 
 * <p>
 * Since this function only accepts integer timestamps the
 * u format character is only useful when using the
 * date_format function with user based timestamps
 * created with date_create.
 * </p>
 * @param timestamp int[optional] 
 * @return string a formatted date string. If a non-numeric value is used for 
 * timestamp, false is returned and an 
 * E_WARNING level error is emitted.
 */
function date ($format, $timestamp = null) {}

/**
 * Format a local time/date as integer
 * @link http://php.net/manual/en/function.idate.php
 * @param format string <p>
 * <table>
 * The following characters are recognized in the
 * format parameter string
 * <tr valign="top">
 * <td>format character</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>B</td>
 * <td>Swatch Beat/Internet Time</td>
 * </tr>
 * <tr valign="top">
 * <td>d</td>
 * <td>Day of the month</td>
 * </tr>
 * <tr valign="top">
 * <td>h</td>
 * <td>Hour (12 hour format)</td>
 * </tr>
 * <tr valign="top">
 * <td>H</td>
 * <td>Hour (24 hour format)</td>
 * </tr>
 * <tr valign="top">
 * <td>i</td>
 * <td>Minutes</td>
 * </tr>
 * <tr valign="top">
 * <td>I (uppercase i)</td>
 * <td>returns 1 if DST is activated,
 * 0 otherwise</td>
 * </tr>
 * <tr valign="top">
 * <td>L (uppercase l)</td>
 * <td>returns 1 for leap year,
 * 0 otherwise</td>
 * </tr>
 * <tr valign="top">
 * <td>m</td>
 * <td>Month number</td>
 * </tr>
 * <tr valign="top">
 * <td>s</td>
 * <td>Seconds</td>
 * </tr>
 * <tr valign="top">
 * <td>t</td>
 * <td>Days in current month</td>
 * </tr>
 * <tr valign="top">
 * <td>U</td>
 * <td>Seconds since the Unix Epoch - January 1 1970 00:00:00 UTC -
 * this is the same as time</td>
 * </tr>
 * <tr valign="top">
 * <td>w</td>
 * <td>Day of the week (0 on Sunday)</td>
 * </tr>
 * <tr valign="top">
 * <td>W</td>
 * <td>ISO-8601 week number of year, weeks starting on
 * Monday</td>
 * </tr>
 * <tr valign="top">
 * <td>y</td>
 * <td>Year (1 or 2 digits - check note below)</td>
 * </tr>
 * <tr valign="top">
 * <td>Y</td>
 * <td>Year (4 digits)</td>
 * </tr>
 * <tr valign="top">
 * <td>z</td>
 * <td>Day of the year</td>
 * </tr>
 * <tr valign="top">
 * <td>Z</td>
 * <td>Timezone offset in seconds</td>
 * </tr>
 * </table>
 * </p>
 * @param timestamp int[optional] 
 * @return int an integer.
 * </p>
 * <p>
 * As idate always returns an integer and
 * as they can't start with a "0", idate may return
 * fewer digits than you would expect. See the example below.
 */
function idate ($format, $timestamp = null) {}

/**
 * Format a GMT/UTC date/time
 * @link http://php.net/manual/en/function.gmdate.php
 * @param format string <p>
 * The format of the outputted date string. See the formatting
 * options for the date function.
 * </p>
 * @param timestamp int[optional] 
 * @return string a formatted date string. If a non-numeric value is used for 
 * timestamp, false is returned and an 
 * E_WARNING level error is emitted.
 */
function gmdate ($format, $timestamp = null) {}

/**
 * Get Unix timestamp for a date
 * @link http://php.net/manual/en/function.mktime.php
 * @param hour int[optional] <p>
 * The number of the hour.
 * </p>
 * @param minute int[optional] <p>
 * The number of the minute.
 * </p>
 * @param second int[optional] <p>
 * The number of seconds past the minute.
 * </p>
 * @param month int[optional] <p>
 * The number of the month.
 * </p>
 * @param day int[optional] <p>
 * The number of the day.
 * </p>
 * @param year int[optional] <p>
 * The number of the year, may be a two or four digit value,
 * with values between 0-69 mapping to 2000-2069 and 70-100 to
 * 1970-2000. On systems where time_t is a 32bit signed integer, as
 * most common today, the valid range for year 
 * is somewhere between 1901 and 2038. However, before PHP 5.1.0 this
 * range was limited from 1970 to 2038 on some systems (e.g. Windows).
 * </p>
 * @param is_dst int[optional] <p>
 * This parameter can be set to 1 if the time is during daylight savings time (DST), 
 * 0 if it is not, or -1 (the default) if it is unknown whether the time is within 
 * daylight savings time or not. If it's unknown, PHP tries to figure it out itself.
 * This can cause unexpected (but not incorrect) results.
 * Some times are invalid if DST is enabled on the system PHP is running on or 
 * is_dst is set to 1. If DST is enabled in e.g. 2:00, all times 
 * between 2:00 and 3:00 are invalid and mktime returns an undefined 
 * (usually negative) value. 
 * Some systems (e.g. Solaris 8) enable DST at midnight so time 0:30 of the day when DST 
 * is enabled is evaluated as 23:30 of the previous day.
 * </p>
 * <p>
 * As of PHP 5.1.0, this parameter became deprecated. As a result, the
 * new timezone handling features should be used instead.
 * </p>
 * @return int mktime returns the Unix timestamp of the arguments
 * given.
 * If the arguments are invalid, the function returns false (before PHP 5.1
 * it returned -1).
 */
function mktime ($hour = null, $minute = null, $second = null, $month = null, $day = null, $year = null, $is_dst = null) {}

/**
 * Get Unix timestamp for a GMT date
 * @link http://php.net/manual/en/function.gmmktime.php
 * @param hour int[optional] <p>
 * The hour
 * </p>
 * @param minute int[optional] <p>
 * The minute
 * </p>
 * @param second int[optional] <p>
 * The second
 * </p>
 * @param month int[optional] <p>
 * The month
 * </p>
 * @param day int[optional] <p>
 * The day
 * </p>
 * @param year int[optional] <p>
 * The year
 * </p>
 * @param is_dst int[optional] <p>
 * Parameters always represent a GMT date so is_dst
 * doesn't influence the result.
 * </p>
 * @return int a integer Unix timestamp.
 */
function gmmktime ($hour = null, $minute = null, $second = null, $month = null, $day = null, $year = null, $is_dst = null) {}

/**
 * Validate a Gregorian date
 * @link http://php.net/manual/en/function.checkdate.php
 * @param month int <p>
 * The month is between 1 and 12 inclusive.
 * </p>
 * @param day int <p>
 * The day is within the allowed number of days for the given 
 * month. Leap years 
 * are taken into consideration.
 * </p>
 * @param year int <p>
 * The year is between 1 and 32767 inclusive.
 * </p>
 * @return bool true if the date given is valid; otherwise returns false.
 */
function checkdate ($month, $day, $year) {}

/**
 * Format a local time/date according to locale settings
 * @link http://php.net/manual/en/function.strftime.php
 * @param format string <p>
 * The following conversion specifiers are recognized in the format
 * string:
 * %a - abbreviated weekday name according to the current locale
 * @param timestamp int[optional] 
 * @return string a string formatted according to the given format string
 * using the given timestamp or the current
 * local time if no timestamp is given. Month and weekday names and
 * other language dependent strings respect the current locale set
 * with setlocale.
 */
function strftime ($format, $timestamp = null) {}

/**
 * Format a GMT/UTC time/date according to locale settings
 * @link http://php.net/manual/en/function.gmstrftime.php
 * @param format string <p>
 * See description in strftime.
 * </p>
 * @param timestamp int[optional] 
 * @return string a string formatted according to the given format string
 * using the given timestamp or the current
 * local time if no timestamp is given. Month and weekday names and
 * other language dependent strings respect the current locale set
 * with setlocale.
 */
function gmstrftime ($format, $timestamp = null) {}

/**
 * Return current Unix timestamp
 * @link http://php.net/manual/en/function.time.php
 * @return int 
 */
function time () {}

/**
 * Get the local time
 * @link http://php.net/manual/en/function.localtime.php
 * @param timestamp int[optional] 
 * @param is_associative bool[optional] <p>
 * If set to false or not supplied then the array is returned as a regular, 
 * numerically indexed array. If the argument is set to true then
 * localtime returns an associative array containing
 * all the different elements of the structure returned by the C
 * function call to localtime. The names of the different keys of
 * the associative array are as follows:
 * </p>
 * <p>
 * "tm_sec" - seconds
 * @return array 
 */
function localtime ($timestamp = null, $is_associative = null) {}

/**
 * Get date/time information
 * @link http://php.net/manual/en/function.getdate.php
 * @param timestamp int[optional] 
 * @return array an associative array of information related to
 * the timestamp. Elements from the returned 
 * associative array are as follows:
 * </p>
 * <p>
 * <table>
 * Key elements of the returned associative array
 * <tr valign="top">
 * <td>Key</td>
 * <td>Description</td>
 * <td>Example returned values</td>
 * </tr>
 * <tr valign="top">
 * <td>"seconds"</td>
 * <td>Numeric representation of seconds</td>
 * <td>0 to 59</td>
 * </tr>
 * <tr valign="top">
 * <td>"minutes"</td>
 * <td>Numeric representation of minutes</td>
 * <td>0 to 59</td>
 * </tr>
 * <tr valign="top">
 * <td>"hours"</td>
 * <td>Numeric representation of hours</td>
 * <td>0 to 23</td>
 * </tr>
 * <tr valign="top">
 * <td>"mday"</td>
 * <td>Numeric representation of the day of the month</td>
 * <td>1 to 31</td>
 * </tr>
 * <tr valign="top">
 * <td>"wday"</td>
 * <td>Numeric representation of the day of the week</td>
 * <td>0 (for Sunday) through 6 (for Saturday)</td>
 * </tr>
 * <tr valign="top">
 * <td>"mon"</td>
 * <td>Numeric representation of a month</td>
 * <td>1 through 12</td>
 * </tr>
 * <tr valign="top">
 * <td>"year"</td>
 * <td>A full numeric representation of a year, 4 digits</td>
 * <td>Examples: 1999 or 2003</td>
 * </tr>
 * <tr valign="top">
 * <td>"yday"</td>
 * <td>Numeric representation of the day of the year</td>
 * <td>0 through 365</td>
 * </tr>
 * <tr valign="top">
 * <td>"weekday"</td>
 * <td>A full textual representation of the day of the week</td>
 * <td>Sunday through Saturday</td>
 * </tr>
 * <tr valign="top">
 * <td>"month"</td>
 * <td>A full textual representation of a month, such as January or March</td>
 * <td>January through December</td>
 * </tr>
 * <tr valign="top">
 * <td>0</td>
 * <td>
 * Seconds since the Unix Epoch, similar to the values returned by
 * time and used by date.
 * </td>
 * <td>
 * System Dependent, typically -2147483648 through
 * 2147483647.
 * </td>
 * </tr>
 * </table>
 */
function getdate ($timestamp = null) {}

/**
 * Returns new DateTime object
 * @link http://php.net/manual/en/function.date-create.php
 * @param time string[optional] <p>
 * String in a format accepted by strtotime, defaults
 * to "now".
 * </p>
 * @param timezone DateTimeZone[optional] <p>
 * Time zone of the time.
 * </p>
 * @return DateTime DateTime object on success or false on failure.
 */
function date_create ($time = null, DateTimeZone $timezone = null) {}

/**
 * Returns associative array with detailed info about given date
 * @link http://php.net/manual/en/function.date-parse.php
 * @param date string <p>
 * Date in format accepted by strtotime.
 * </p>
 * @return array array with information about the parsed date
 * on success, or false on failure.
 */
function date_parse ($date) {}

/**
 * Returns date formatted according to given format
 * @link http://php.net/manual/en/function.date-format.php
 * @param object DateTime <p>
 * DateTime object.
 * </p>
 * @param format string <p>
 * Format accepted by date.
 * </p>
 * @return string formatted date on success or false on failure.
 */
function date_format (DateTime $object, $format) {}

/**
 * Alters the timestamp
 * @link http://php.net/manual/en/function.date-modify.php
 * @param object DateTime <p>
 * DateTime object.
 * </p>
 * @param modify string <p>
 * String in a relative format accepted by strtotime.
 * </p>
 * @return void &null; on success or false on failure.
 */
function date_modify (DateTime $object, $modify) {}

/**
 * Return time zone relative to given DateTime
 * @link http://php.net/manual/en/function.date-timezone-get.php
 * @param object DateTime <p>
 * DateTime object.
 * </p>
 * @return DateTimeZone DateTimeZone object on success or false on failure.
 */
function date_timezone_get (DateTime $object) {}

/**
 * Sets the time zone for the DateTime object
 * @link http://php.net/manual/en/function.date-timezone-set.php
 * @param object DateTime <p>
 * DateTime object.
 * </p>
 * @param timezone DateTimeZone <p>
 * Desired time zone.
 * </p>
 * @return void &null; on success or false on failure.
 */
function date_timezone_set (DateTime $object, DateTimeZone $timezone) {}

/**
 * Returns the daylight saving time offset
 * @link http://php.net/manual/en/function.date-offset-get.php
 * @param object DateTime <p>
 * DateTime object.
 * </p>
 * @return int DST offset in seconds on success or false on failure.
 */
function date_offset_get (DateTime $object) {}

/**
 * Sets the time
 * @link http://php.net/manual/en/function.date-time-set.php
 * @param object DateTime <p>
 * DateTime object.
 * </p>
 * @param hour int <p>
 * Hour of the time.
 * </p>
 * @param minute int <p>
 * Minute of the time.
 * </p>
 * @param second int[optional] <p>
 * Second of the time.
 * </p>
 * @return void &null; on success or false on failure.
 */
function date_time_set (DateTime $object, $hour, $minute, $second = null) {}

/**
 * Sets the date
 * @link http://php.net/manual/en/function.date-date-set.php
 * @param object DateTime <p>
 * DateTime object.
 * </p>
 * @param year int <p>
 * Year of the date.
 * </p>
 * @param month int <p>
 * Month of the date.
 * </p>
 * @param day int <p>
 * Day of the date.
 * </p>
 * @return void &null; on success or false on failure.
 */
function date_date_set (DateTime $object, $year, $month, $day) {}

/**
 * Sets the ISO date
 * @link http://php.net/manual/en/function.date-isodate-set.php
 * @param object DateTime <p>
 * DateTime object.
 * </p>
 * @param year int <p>
 * Year of the date.
 * </p>
 * @param week int <p>
 * Week of the date.
 * </p>
 * @param day int[optional] <p>
 * Day of the date.
 * </p>
 * @return void &null; on success or false on failure.
 */
function date_isodate_set (DateTime $object, $year, $week, $day = null) {}

/**
 * Returns new DateTimeZone object
 * @link http://php.net/manual/en/function.timezone-open.php
 * @param timezone string <p>
 * Time zone identifier as full name (e.g. Europe/Prague) or abbreviation
 * (e.g. CET).
 * </p>
 * @return DateTimeZone DateTimeZone object on success or false on failure.
 */
function timezone_open ($timezone) {}

/**
 * Returns the name of the timezone
 * @link http://php.net/manual/en/function.timezone-name-get.php
 * @param object DateTimeZone <p>
 * DateTimeZone object.
 * </p>
 * @return string time zone name on success or false on failure.
 */
function timezone_name_get (DateTimeZone $object) {}

/**
 * Returns the timezone name from abbrevation
 * @link http://php.net/manual/en/function.timezone-name-from-abbr.php
 * @param abbr string <p>
 * Time zone abbreviation.
 * </p>
 * @param gmtOffset int[optional] <p>
 * Offset from GMT in seconds. Defaults to -1 which means that first found
 * time zone corresponding to abbr is returned.
 * Otherwise exact offset is searched and only if not found then the first
 * time zone with any offset is returned.
 * </p>
 * @param isdst int[optional] <p>
 * Daylight saving time indicator. If abbr doesn't
 * exist then the time zone is searched solely by
 * offset and isdst.
 * </p>
 * @return string time zone name on success or false on failure.
 */
function timezone_name_from_abbr ($abbr, $gmtOffset = null, $isdst = null) {}

/**
 * Returns the timezone offset from GMT
 * @link http://php.net/manual/en/function.timezone-offset-get.php
 * @param object DateTimeZone <p>
 * DateTimeZone object.
 * </p>
 * @param datetime DateTime <p>
 * DateTime that contains the date/time to compute the offset from. 
 * </p>
 * @return int time zone offset in seconds on success or false on failure.
 */
function timezone_offset_get (DateTimeZone $object, DateTime $datetime) {}

/**
 * Returns all transitions for the timezone
 * @link http://php.net/manual/en/function.timezone-transitions-get.php
 * @param object DateTimeZone <p>
 * DateTimeZone object.
 * </p>
 * @return array numerically indexed array containing associative array with all
 * transitions on success or false on failure.
 */
function timezone_transitions_get (DateTimeZone $object) {}

/**
 * Returns numerically index array with all timezone identifiers
 * @link http://php.net/manual/en/function.timezone-identifiers-list.php
 * @return array array on success or false on failure.
 */
function timezone_identifiers_list () {}

/**
 * Returns associative array containing dst, offset and the timezone name
 * @link http://php.net/manual/en/function.timezone-abbreviations-list.php
 * @return array array on success or false on failure.
 */
function timezone_abbreviations_list () {}

/**
 * Sets the default timezone used by all date/time functions in a script
 * @link http://php.net/manual/en/function.date-default-timezone-set.php
 * @param timezone_identifier string <p>
 * The timezone identifier, like UTC or
 * Europe/Lisbon. The list of valid identifiers is
 * available in the .
 * </p>
 * @return bool This function returns false if the
 * timezone_identifier isn't valid, or true
 * otherwise.
 */
function date_default_timezone_set ($timezone_identifier) {}

/**
 * Gets the default timezone used by all date/time functions in a script
 * @link http://php.net/manual/en/function.date-default-timezone-get.php
 * @return string a string.
 */
function date_default_timezone_get () {}

/**
 * Returns time of sunrise for a given day and location
 * @link http://php.net/manual/en/function.date-sunrise.php
 * @param timestamp int <p>
 * The timestamp of the day from which the sunrise
 * time is taken.
 * </p>
 * @param format int[optional] <p>
 * <table>
 * format constants
 * <tr valign="top">
 * <td>constant</td>
 * <td>description</td>
 * <td>example</td>
 * </tr>
 * <tr valign="top">
 * <td>SUNFUNCS_RET_STRING</td>
 * <td>returns the result as string</td>
 * <td>16:46</td>
 * </tr>
 * <tr valign="top">
 * <td>SUNFUNCS_RET_DOUBLE</td>
 * <td>returns the result as float</td>
 * <td>16.78243132</td>
 * </tr>
 * <tr valign="top">
 * <td>SUNFUNCS_RET_TIMESTAMP</td>
 * <td>returns the result as integer (timestamp)</td>
 * <td>1095034606</td>
 * </tr>
 * </table>
 * </p>
 * @param latitude float[optional] <p>
 * Defaults to North, pass in a negative value for South.
 * See also: date.default_latitude
 * </p>
 * @param longitude float[optional] <p>
 * Defaults to East, pass in a negative value for West.
 * See also: date.default_longitude
 * </p>
 * @param zenith float[optional] <p>
 * Default: date.sunrise_zenith
 * </p>
 * @param gmt_offset float[optional] 
 * @return mixed the sunrise time in a specified format on
 * success, or false on failure.
 */
function date_sunrise ($timestamp, $format = null, $latitude = null, $longitude = null, $zenith = null, $gmt_offset = null) {}

/**
 * Returns time of sunset for a given day and location
 * @link http://php.net/manual/en/function.date-sunset.php
 * @param timestamp int <p>
 * The timestamp of the day from which the sunset
 * time is taken.
 * </p>
 * @param format int[optional] <p>
 * <table>
 * format constants
 * <tr valign="top">
 * <td>constant</td>
 * <td>description</td>
 * <td>example</td>
 * </tr>
 * <tr valign="top">
 * <td>SUNFUNCS_RET_STRING</td>
 * <td>returns the result as string</td>
 * <td>16:46</td>
 * </tr>
 * <tr valign="top">
 * <td>SUNFUNCS_RET_DOUBLE</td>
 * <td>returns the result as float</td>
 * <td>16.78243132</td>
 * </tr>
 * <tr valign="top">
 * <td>SUNFUNCS_RET_TIMESTAMP</td>
 * <td>returns the result as integer (timestamp)</td>
 * <td>1095034606</td>
 * </tr>
 * </table>
 * </p>
 * @param latitude float[optional] <p>
 * Defaults to North, pass in a negative value for South.
 * See also: date.default_latitude
 * </p>
 * @param longitude float[optional] <p>
 * Defaults to East, pass in a negative value for West.
 * See also: date.default_longitude
 * </p>
 * @param zenith float[optional] <p>
 * Default: date.sunrise_zenith
 * </p>
 * @param gmt_offset float[optional] 
 * @return mixed the sunset time in a specified format on
 * success, or false on failure.
 */
function date_sunset ($timestamp, $format = null, $latitude = null, $longitude = null, $zenith = null, $gmt_offset = null) {}

/**
 * Returns an array with information about sunset/sunrise and twilight begin/end
 * @link http://php.net/manual/en/function.date-sun-info.php
 * @param time int <p>
 * Timestamp.
 * </p>
 * @param latitude float <p>
 * Latitude in degrees.
 * </p>
 * @param longitude float <p>
 * Longitude in degrees.
 * </p>
 * @return array array on success or false on failure.
 */
function date_sun_info ($time, $latitude, $longitude) {}


/**
 * Atom (example: 2005-08-15T15:52:01+00:00)
 * @link http://php.net/manual/en/datetime.constants.php
 */
define ('DATE_ATOM', "Y-m-d\TH:i:sP");

/**
 * HTTP Cookies (example: Monday, 15-Aug-05 15:52:01 UTC)
 * @link http://php.net/manual/en/datetime.constants.php
 */
define ('DATE_COOKIE', "l, d-M-y H:i:s T");

/**
 * ISO-8601 (example: 2005-08-15T15:52:01+0000)
 * @link http://php.net/manual/en/datetime.constants.php
 */
define ('DATE_ISO8601', "Y-m-d\TH:i:sO");

/**
 * RFC 822 (example: Mon, 15 Aug 05 15:52:01 +0000)
 * @link http://php.net/manual/en/datetime.constants.php
 */
define ('DATE_RFC822', "D, d M y H:i:s O");

/**
 * RFC 850 (example: Monday, 15-Aug-05 15:52:01 UTC)
 * @link http://php.net/manual/en/datetime.constants.php
 */
define ('DATE_RFC850', "l, d-M-y H:i:s T");

/**
 * RFC 1036 (example: Mon, 15 Aug 05 15:52:01 +0000)
 * @link http://php.net/manual/en/datetime.constants.php
 */
define ('DATE_RFC1036', "D, d M y H:i:s O");

/**
 * RFC 1123 (example: Mon, 15 Aug 2005 15:52:01 +0000)
 * @link http://php.net/manual/en/datetime.constants.php
 */
define ('DATE_RFC1123', "D, d M Y H:i:s O");

/**
 * RFC 2822 (Mon, 15 Aug 2005 15:52:01 +0000)
 * @link http://php.net/manual/en/datetime.constants.php
 */
define ('DATE_RFC2822', "D, d M Y H:i:s O");

/**
 * Same as DATE_ATOM (since PHP 5.1.3)
 * @link http://php.net/manual/en/datetime.constants.php
 */
define ('DATE_RFC3339', "Y-m-d\TH:i:sP");

/**
 * RSS (Mon, 15 Aug 2005 15:52:01 +0000)
 * @link http://php.net/manual/en/datetime.constants.php
 */
define ('DATE_RSS', "D, d M Y H:i:s O");

/**
 * World Wide Web Consortium (example: 2005-08-15T15:52:01+00:00)
 * @link http://php.net/manual/en/datetime.constants.php
 */
define ('DATE_W3C', "Y-m-d\TH:i:sP");

/**
 * Timestamp
 * @link http://php.net/manual/en/datetime.constants.php
 */
define ('SUNFUNCS_RET_TIMESTAMP', 0);

/**
 * Hours:minutes (example: 08:02)
 * @link http://php.net/manual/en/datetime.constants.php
 */
define ('SUNFUNCS_RET_STRING', 1);

/**
 * Hours as floating point number (example 8.75)
 * @link http://php.net/manual/en/datetime.constants.php
 */
define ('SUNFUNCS_RET_DOUBLE', 2);

// End of date v.5.2.6
?>
