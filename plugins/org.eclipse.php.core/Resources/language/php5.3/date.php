<?php

// Start of date v.5.3.0beta1

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


	/**
	 * @param time[optional]
	 * @param object[optional]
	 */
	public function __construct ($time, $object) {}

	public function __wakeup () {}

	public static function __set_state () {}

	/**
	 * @param format
	 * @param time
	 * @param object[optional]
	 */
	public static function createFromFormat ($format, $time, $object) {}

	public static function getLastErrors () {}

	/**
	 * @param format
	 */
	public function format ($format) {}

	/**
	 * @param modify
	 */
	public function modify ($modify) {}

	/**
	 * @param interval
	 */
	public function add ($interval) {}

	/**
	 * @param interval
	 */
	public function sub ($interval) {}

	public function getTimezone () {}

	/**
	 * @param timezone
	 */
	public function setTimezone ($timezone) {}

	public function getOffset () {}

	/**
	 * @param hour
	 * @param minute
	 * @param second[optional]
	 */
	public function setTime ($hour, $minute, $second) {}

	/**
	 * @param year
	 * @param month
	 * @param day
	 */
	public function setDate ($year, $month, $day) {}

	/**
	 * @param object
	 * @param year
	 * @param week
	 * @param day[optional]
	 */
	public function setISODate ($object, $year, $week, $day) {}

	/**
	 * @param unixtimestamp
	 */
	public function setTimestamp ($unixtimestamp) {}

	public function getTimestamp () {}

	/**
	 * @param object
	 * @param absolute[optional]
	 */
	public function diff ($object, $absolute) {}

}

class DateTimeZone  {
	const AFRICA = 1;
	const AMERICA = 2;
	const ANTARCTICA = 4;
	const ARCTIC = 8;
	const ASIA = 16;
	const ATLANTIC = 32;
	const AUSTRALIA = 64;
	const EUROPE = 128;
	const INDIAN = 256;
	const PACIFIC = 512;
	const UTC = 1024;
	const ALL = 2047;
	const ALL_WITH_BC = 4095;
	const PER_COUNTRY = 4096;


	/**
	 * @param timezone
	 */
	public function __construct ($timezone) {}

	public function getName () {}

	/**
	 * @param datetime
	 */
	public function getOffset ($datetime) {}

	/**
	 * @param timestamp_begin
	 * @param timestamp_end
	 */
	public function getTransitions ($timestamp_begin, $timestamp_end) {}

	public function getLocation () {}

	public static function listAbbreviations () {}

	/**
	 * @param what[optional]
	 */
	public static function listIdentifiers ($what) {}

}

class DateInterval  {

	/**
	 * @param interval_spec[optional]
	 */
	public function __construct ($interval_spec) {}

	public function format () {}

	/**
	 * @param time
	 */
	public static function createFromDateString ($time) {}

}

class DatePeriod implements Traversable {
	const EXCLUDE_START_DATE = 1;


	/**
	 * @param start
	 * @param interval
	 * @param end
	 */
	public function __construct ($start, $interval, $end) {}

}

/**
 * Parse about any English textual datetime description into a Unix timestamp
 * @link http://php.net/manual/en/function.strtotime.php
 * @param time string <p>
 * The string to parse. Before PHP 5.0.0, microseconds weren't allowed in
 * the time, since PHP 5.0.0 they are allowed but ignored.
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
 * <table>
 * The following characters are recognized in the
 * format parameter string
 * <tr valign="top">
 * <td>format</td>
 * <td>Description</td>
 * <td>Example returned values</td>
 * </tr>
 * <tr valign="top">
 * Day</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>%a</td>
 * <td>An abbreviated textual representation of the day</td>
 * <td>Sun through Sat</td>
 * </tr>
 * <tr valign="top">
 * <td>%A</td>
 * <td>A full textual representation of the day</td>
 * <td>Sunday through Saturday</td>
 * </tr>
 * <tr valign="top">
 * <td>%d</td>
 * <td>Two-digit day of the month (with leading zeros)</td>
 * <td>01 to 31</td>
 * </tr>
 * <tr valign="top">
 * <td>%e</td>
 * <td>Day of the month, with a space preceeding single digits</td>
 * <td> 1 to 31</td>
 * </tr>
 * <tr valign="top">
 * <td>%j</td>
 * <td>Day of the year, 3 digits with leading zeros</td>
 * <td>001 to 366</td>
 * </tr>
 * <tr valign="top">
 * <td>%u</td>
 * <td>ISO-8601 numeric representation of the day of the week</td>
 * <td>1 (for Monday) though 7 (for Sunday)</td>
 * </tr>
 * <tr valign="top">
 * <td>%w</td>
 * <td>Numeric representation of the day of the week</td>
 * <td>0 (for Sunday) through 6 (for Saturday)</td>
 * </tr>
 * <tr valign="top">
 * Week</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>%U</td>
 * <td>Week number of the given year, starting with the the first
 * 	 Sunday as the first week</td>
 * <td>13 (for the 13th full week of the year)</td>
 * </tr>
 * <tr valign="top">
 * <td>%V</td>
 * <td>ISO-8601:1988 week number of the given year, starting with
 * 	 the first week of the year with at least 4 weekdays, with Monday
 * 	 being the start of the week</td>
 * <td>01 through 53 (where 53
 * 	 accounts for an overlapping week)</td>
 * </tr>
 * <tr valign="top">
 * <td>%W</td>
 * <td>A numeric representation of the week of the year, starting
 * 	 with the first Monday as the first week</td>
 * <td>46 (for the 46th week of the year beginning
 * 	 with a Monday)</td>
 * </tr>
 * <tr valign="top">
 * Month</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>%b</td>
 * <td>Abbreviated month name, based on the locale</td>
 * <td>Jan through Dec</td>
 * </tr>
 * <tr valign="top">
 * <td>%B</td>
 * <td>Full month name, based on the locale</td>
 * <td>January through December</td>
 * </tr>
 * <tr valign="top">
 * <td>%h</td>
 * <td>Abbreviated month name, based on the locale (an alias of %b)</td>
 * <td>Jan through Dec</td>
 * </tr>
 * <tr valign="top">
 * <td>%m</td>
 * <td>Two digit representation of the month</td>
 * <td>01 (for January) through 12 (for December)</td>
 * </tr>
 * <tr valign="top">
 * Year</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>%C</td>
 * <td>Two digit representation of the century (year divided by 100, truncated to an integer)</td>
 * <td>19 for the 20th Century</td>
 * </tr>
 * <tr valign="top">
 * <td>%g</td>
 * <td>Two digit representation of the year going by ISO-8601:1988 standards (see %V)</td>
 * <td>Example: 09 for the week of January 6, 2009</td>
 * </tr>
 * <tr valign="top">
 * <td>%G</td>
 * <td>The full four-digit version of %g</td>
 * <td>Example: 2008 for the week of January 3, 2009</td>
 * </tr>
 * <tr valign="top">
 * <td>%y</td>
 * <td>Two digit representation of the year</td>
 * <td>Example: 09 for 2009, 79 for 1979</td>
 * </tr>
 * <tr valign="top">
 * <td>%Y</td>
 * <td>Four digit representation for the year</td>
 * <td>Example: 2038</td>
 * </tr>
 * <tr valign="top">
 * Time</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>%H</td>
 * <td>Two digit representation of the hour in 24-hour format</td>
 * <td>00 through 23</td>
 * </tr>
 * <tr valign="top">
 * <td>%I</td>
 * <td>Two digit representation of the hour in 12-hour format</td>
 * <td>01 through 12</td>
 * </tr>
 * <tr valign="top">
 * <td>%l (lower-case 'L')</td>
 * <td>Hour in 12-hour format, with a space preceeding single digits</td>
 * <td> 1 through 12</td>
 * </tr>
 * <tr valign="top">
 * <td>%M</td>
 * <td>Two digit representation of the minute</td>
 * <td>00 through 59</td>
 * </tr>
 * <tr valign="top">
 * <td>%p</td>
 * <td>UPPER-CASE 'AM' or 'PM' based on the given time</td>
 * <td>Example: AM for 00:31, PM for 22:23</td>
 * </tr>
 * <tr valign="top">
 * <td>%P</td>
 * <td>lower-case 'am' or 'pm' based on the given time</td>
 * <td>Example: am for 00:31, pm for 22:23</td>
 * </tr>
 * <tr valign="top">
 * <td>%r</td>
 * <td>Same as "%I:%M:%S %p"</td>
 * <td>Example: 09:34:17 PM for 21:34:17</td>
 * </tr>
 * <tr valign="top">
 * <td>%R</td>
 * <td>Same as "%H:%M"</td>
 * <td>Example: 00:35 for 12:35 AM, 16:44 for 4:44 PM</td>
 * </tr>
 * <tr valign="top">
 * <td>%S</td>
 * <td>Two digit representation of the second</td>
 * <td>00 through 59</td>
 * </tr>
 * <tr valign="top">
 * <td>%T</td>
 * <td>Same as "%H:%M:%S"</td>
 * <td>Example: 21:34:17 for 09:34:17 PM</td>
 * </tr>
 * <tr valign="top">
 * <td>%X</td>
 * <td>Preferred time representation based on locale, without the date</td>
 * <td>Example: 03:59:16 or 15:59:16</td>
 * </tr>
 * <tr valign="top">
 * <td>%z</td>
 * <td>Either the time zone offset from UTC or the abbreviation (depends
 * 	 on operating system)</td>
 * <td>Example: -0500 or EST for Eastern Time</td>
 * </tr>
 * <tr valign="top">
 * <td>%Z</td>
 * <td>The time zone offset/abbreviation option NOT given by %z (depends
 * 	 on operating system)</td>
 * <td>Example: -0500 or EST for Eastern Time</td>
 * </tr>
 * <tr valign="top">
 * Time and Date Stamps</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>%c</td>
 * <td>Preferred date and time stamp based on local</td>
 * <td>Example: Tue Feb 5 00:45:10 2009 for
 * February 4, 2009 at 12:45:10 AM</td>
 * </tr>
 * <tr valign="top">
 * <td>%D</td>
 * <td>Same as "%m/%d/%y"</td>
 * <td>Example: 02/05/09 for February 5, 2009</td>
 * </tr>
 * <tr valign="top">
 * <td>%F</td>
 * <td>Same as "%y-%m-%d" (commonly used in database datestamps)</td>
 * <td>Example: 2009-02-05 for February 5, 2009</td>
 * </tr>
 * <tr valign="top">
 * <td>%s</td>
 * <td>Unix Epoch Time timestamp (same as the time
 * 	 function)</td>
 * <td>Example: 305815200 for September 10, 1979 08:40:00 AM</td>
 * </tr>
 * <tr valign="top">
 * <td>%x</td>
 * <td>Preferred date representation based on locale, without the time</td>
 * <td>Example: 02/05/09 for February 5, 2009</td>
 * </tr>
 * 	 <tr valign="top">
 * Miscellaneous</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>%n</td>
 * <td>A newline character ("\n")</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>%t</td>
 * <td>A Tab character ("\t")</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>%%</td>
 * <td>A literal percentage character ("%")</td>
 * <td>---</td>
 * </tr>
 * 	 
 * 	
 * </table>
 * </p>
 * <p>
 * Maximum length of this parameter is 1023 characters.
 * </p>
 * Contrary to ISO-9889:1999, Sun Solaris starts with Sunday as 1.
 * As a result, %u may not function as described in this manual.
 * @param timestamp int[optional] 
 * @return string a string formatted according format
 * using the given timestamp or the current
 * local time if no timestamp is given. Month and weekday names and
 * other language-dependent strings respect the current locale set
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
 * &Alias; <methodname>DateTime::createFromFormat</methodname>
 * @link http://php.net/manual/en/function.date-create-from-format.php
 * @param format
 * @param time
 * @param object[optional]
 */
function date_create_from_format ($format, $time, $object) {}

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
 * Get info about given date
 * @link http://php.net/manual/en/function.date-parse-from-format.php
 * @param format string <p>
 * Format accepted by date with some extras.
 * </p>
 * @param date string <p>
 * String representing the date.
 * </p>
 * @return array associative array with detailed info about given date.
 */
function date_parse_from_format ($format, $date) {}

/**
 * &Alias; <methodname>DateTime::getLastErrors</methodname>
 * @link http://php.net/manual/en/function.date-get-last-errors.php
 */
function date_get_last_errors () {}

/**
 * &Alias; <methodname>DateTime::format</methodname>
 * @link http://php.net/manual/en/function.date-format.php
 * @param object
 * @param format
 */
function date_format ($object, $format) {}

/**
 * &Alias; <methodname>DateTime::modify</methodname>
 * @link http://php.net/manual/en/function.date-modify.php
 * @param object
 * @param modify
 */
function date_modify ($object, $modify) {}

/**
 * &Alias; <methodname>DateTime::add</methodname>
 * @link http://php.net/manual/en/function.date-add.php
 * @param object
 * @param interval
 */
function date_add ($object, $interval) {}

/**
 * &Alias; <methodname>DateTime::sub</methodname>
 * @link http://php.net/manual/en/function.date-sub.php
 * @param object
 * @param interval
 */
function date_sub ($object, $interval) {}

/**
 * &Alias; <methodname>DateTime::getTimezone</methodname>
 * @link http://php.net/manual/en/function.date-timezone-get.php
 * @param object
 */
function date_timezone_get ($object) {}

/**
 * &Alias; <methodname>DateTime::setTimezone</methodname>
 * @link http://php.net/manual/en/function.date-timezone-set.php
 * @param object
 * @param timezone
 */
function date_timezone_set ($object, $timezone) {}

/**
 * &Alias; <methodname>DateTime::getOffset</methodname>
 * @link http://php.net/manual/en/function.date-offset-get.php
 * @param object
 */
function date_offset_get ($object) {}

/**
 * &Alias; <methodname>DateTime::diff</methodname>
 * @link http://php.net/manual/en/function.date-diff.php
 * @param object
 * @param object2
 * @param absolute[optional]
 */
function date_diff ($object, $object2, $absolute) {}

/**
 * &Alias; <methodname>DateTime::setTime</methodname>
 * @link http://php.net/manual/en/function.date-time-set.php
 * @param object
 * @param hour
 * @param minute
 * @param second[optional]
 */
function date_time_set ($object, $hour, $minute, $second) {}

/**
 * &Alias; <methodname>DateTime::setDate</methodname>
 * @link http://php.net/manual/en/function.date-date-set.php
 * @param object
 * @param year
 * @param month
 * @param day
 */
function date_date_set ($object, $year, $month, $day) {}

/**
 * &Alias; <methodname>DateTime::setISODate</methodname>
 * @link http://php.net/manual/en/function.date-isodate-set.php
 * @param object
 * @param year
 * @param week
 * @param day[optional]
 */
function date_isodate_set ($object, $year, $week, $day) {}

/**
 * &Alias; <methodname>DateTime::setTimestamp</methodname>
 * @link http://php.net/manual/en/function.date-timestamp-set.php
 * @param object
 * @param unixtimestamp
 */
function date_timestamp_set ($object, $unixtimestamp) {}

/**
 * &Alias; <methodname>DateTime::getTimestamp</methodname>
 * @link http://php.net/manual/en/function.date-timestamp-get.php
 * @param object
 */
function date_timestamp_get ($object) {}

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
 * &Alias; <methodname>DateTimeZone::getName</methodname>
 * @link http://php.net/manual/en/function.timezone-name-get.php
 * @param object
 */
function timezone_name_get ($object) {}

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
 * &Alias; <methodname>DateTimeZone::getOffset</methodname>
 * @link http://php.net/manual/en/function.timezone-offset-get.php
 * @param object
 * @param datetime
 */
function timezone_offset_get ($object, $datetime) {}

/**
 * &Alias; <methodname>DateTimeZone::getTransitions</methodname>
 * @link http://php.net/manual/en/function.timezone-transitions-get.php
 * @param object
 * @param timestamp_begin[optional]
 * @param timestamp_end[optional]
 */
function timezone_transitions_get ($object, $timestamp_begin, $timestamp_end) {}

/**
 * &Alias; <methodname>DateTimeZone::getLocation</methodname>
 * @link http://php.net/manual/en/function.timezone-location-get.php
 * @param object
 */
function timezone_location_get ($object) {}

/**
 * &Alias; <methodname>DateTimeZone::listIdentifiers</methodname>
 * @link http://php.net/manual/en/function.timezone-identifiers-list.php
 * @param what[optional]
 */
function timezone_identifiers_list ($what) {}

/**
 * &Alias; <methodname>DateTimeZone::listAbbreviations</methodname>
 * @link http://php.net/manual/en/function.timezone-abbreviations-list.php
 */
function timezone_abbreviations_list () {}

/**
 * &Alias; <methodname>DateInterval::createFromDateString</methodname>
 * @link http://php.net/manual/en/function.date-interval-create-from-date-string.php
 * @param time
 */
function date_interval_create_from_date_string ($time) {}

/**
 * &Alias; <methodname>DateInterval::format</methodname>
 * @link http://php.net/manual/en/function.date-interval-format.php
 * @param object
 */
function date_interval_format ($object) {}

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
 * Default: date.sunset_zenith
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

define ('DATE_ATOM', "Y-m-d\TH:i:sP");
define ('DATE_COOKIE', "l, d-M-y H:i:s T");
define ('DATE_ISO8601', "Y-m-d\TH:i:sO");
define ('DATE_RFC822', "D, d M y H:i:s O");
define ('DATE_RFC850', "l, d-M-y H:i:s T");
define ('DATE_RFC1036', "D, d M y H:i:s O");
define ('DATE_RFC1123', "D, d M Y H:i:s O");
define ('DATE_RFC2822', "D, d M Y H:i:s O");
define ('DATE_RFC3339', "Y-m-d\TH:i:sP");
define ('DATE_RSS', "D, d M Y H:i:s O");
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

// End of date v.5.3.0beta1
?>
