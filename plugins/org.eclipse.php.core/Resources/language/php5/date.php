<?php

// Start of date v.5.2.5

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
 * @param time string
 * @param now int[optional]
 * @return int a timestamp on success, false otherwise. Previous to PHP 5.1.0,
 */
function strtotime ($time, $now = null) {}

/**
 * Format a local time/date
 * @link http://php.net/manual/en/function.date.php
 * @param format string
 * @param timestamp int[optional]
 * @return string a formatted date string. If a non-numeric value is used for
 */
function date ($format, $timestamp = null) {}

/**
 * Format a local time/date as integer
 * @link http://php.net/manual/en/function.idate.php
 * @param format string
 * @param timestamp int[optional]
 * @return int an integer.
 */
function idate ($format, $timestamp = null) {}

/**
 * Format a GMT/UTC date/time
 * @link http://php.net/manual/en/function.gmdate.php
 * @param format string
 * @param timestamp int[optional]
 * @return string a formatted date string. If a non-numeric value is used for
 */
function gmdate ($format, $timestamp = null) {}

/**
 * Get Unix timestamp for a date
 * @link http://php.net/manual/en/function.mktime.php
 * @param hour int[optional]
 * @param minute int[optional]
 * @param second int[optional]
 * @param month int[optional]
 * @param day int[optional]
 * @param year int[optional]
 * @param is_dst int[optional]
 * @return int 
 */
function mktime ($hour = null, $minute = null, $second = null, $month = null, $day = null, $year = null, $is_dst = null) {}

/**
 * Get Unix timestamp for a GMT date
 * @link http://php.net/manual/en/function.gmmktime.php
 * @param hour int[optional]
 * @param minute int[optional]
 * @param second int[optional]
 * @param month int[optional]
 * @param day int[optional]
 * @param year int[optional]
 * @param is_dst int[optional]
 * @return int a integer Unix timestamp.
 */
function gmmktime ($hour = null, $minute = null, $second = null, $month = null, $day = null, $year = null, $is_dst = null) {}

/**
 * Validate a Gregorian date
 * @link http://php.net/manual/en/function.checkdate.php
 * @param month int
 * @param day int
 * @param year int
 * @return bool true if the date given is valid; otherwise returns false.
 */
function checkdate ($month, $day, $year) {}

/**
 * Format a local time/date according to locale settings
 * @link http://php.net/manual/en/function.strftime.php
 * @param format string
 * @param timestamp int[optional]
 * @return string a string formatted according to the given format string
 */
function strftime ($format, $timestamp = null) {}

/**
 * Format a GMT/UTC time/date according to locale settings
 * @link http://php.net/manual/en/function.gmstrftime.php
 * @param format string
 * @param timestamp int[optional]
 * @return string a string formatted according to the given format string
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
 * @param is_associative bool[optional]
 * @return array 
 */
function localtime ($timestamp = null, $is_associative = null) {}

/**
 * Get date/time information
 * @link http://php.net/manual/en/function.getdate.php
 * @param timestamp int[optional]
 * @return array an associative array of information related to
 */
function getdate ($timestamp = null) {}

/**
 * Returns new DateTime object
 * @link http://php.net/manual/en/function.date-create.php
 * @param time string[optional]
 * @param timezone DateTimeZone[optional]
 * @return DateTime DateTime object on success or false on failure.
 */
function date_create ($time = null, DateTimeZone $timezone = null) {}

/**
 * Returns associative array with detailed info about given date
 * @link http://php.net/manual/en/function.date-parse.php
 * @param date string
 * @return array array with information about the parsed date
 */
function date_parse ($date) {}

/**
 * Returns date formatted according to given format
 * @link http://php.net/manual/en/function.date-format.php
 * @param object DateTime
 * @param format string
 * @return string formatted date on success or false on failure.
 */
function date_format (DateTime $object, $format) {}

/**
 * Alters the timestamp
 * @link http://php.net/manual/en/function.date-modify.php
 * @param object DateTime
 * @param modify string
 * @return void &null; on success or false on failure.
 */
function date_modify (DateTime $object, $modify) {}

/**
 * Return time zone relative to given DateTime
 * @link http://php.net/manual/en/function.date-timezone-get.php
 * @param object DateTime
 * @return DateTimeZone DateTimeZone object on success or false on failure.
 */
function date_timezone_get (DateTime $object) {}

/**
 * Sets the time zone for the DateTime object
 * @link http://php.net/manual/en/function.date-timezone-set.php
 * @param object DateTime
 * @param timezone DateTimeZone
 * @return void &null; on success or false on failure.
 */
function date_timezone_set (DateTime $object, DateTimeZone $timezone) {}

/**
 * Returns the daylight saving time offset
 * @link http://php.net/manual/en/function.date-offset-get.php
 * @param object DateTime
 * @return int DST offset in seconds on success or false on failure.
 */
function date_offset_get (DateTime $object) {}

/**
 * Sets the time
 * @link http://php.net/manual/en/function.date-time-set.php
 * @param object DateTime
 * @param hour int
 * @param minute int
 * @param second int[optional]
 * @return void &null; on success or false on failure.
 */
function date_time_set (DateTime $object, $hour, $minute, $second = null) {}

/**
 * Sets the date
 * @link http://php.net/manual/en/function.date-date-set.php
 * @param object DateTime
 * @param year int
 * @param month int
 * @param day int
 * @return void &null; on success or false on failure.
 */
function date_date_set (DateTime $object, $year, $month, $day) {}

/**
 * Sets the ISO date
 * @link http://php.net/manual/en/function.date-isodate-set.php
 * @param object DateTime
 * @param year int
 * @param week int
 * @param day int[optional]
 * @return void &null; on success or false on failure.
 */
function date_isodate_set (DateTime $object, $year, $week, $day = null) {}

/**
 * Returns new DateTimeZone object
 * @link http://php.net/manual/en/function.timezone-open.php
 * @param timezone string
 * @return DateTimeZone DateTimeZone object on success or false on failure.
 */
function timezone_open ($timezone) {}

/**
 * Returns the name of the timezone
 * @link http://php.net/manual/en/function.timezone-name-get.php
 * @param object DateTimeZone
 * @return string time zone name on success or false on failure.
 */
function timezone_name_get (DateTimeZone $object) {}

/**
 * Returns the timezone name from abbrevation
 * @link http://php.net/manual/en/function.timezone-name-from-abbr.php
 * @param abbr string
 * @param gmtOffset int[optional]
 * @param isdst int[optional]
 * @return string time zone name on success or false on failure.
 */
function timezone_name_from_abbr ($abbr, $gmtOffset = null, $isdst = null) {}

/**
 * Returns the timezone offset from GMT
 * @link http://php.net/manual/en/function.timezone-offset-get.php
 * @param object DateTimeZone
 * @param datetime DateTime
 * @return int time zone offset in seconds on success or false on failure.
 */
function timezone_offset_get (DateTimeZone $object, DateTime $datetime) {}

/**
 * Returns all transitions for the timezone
 * @link http://php.net/manual/en/function.timezone-transitions-get.php
 * @param object DateTimeZone
 * @return array numerically indexed array containing associative array with all
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
 * @param timezone_identifier string
 * @return bool 
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
 * @param timestamp int
 * @param format int[optional]
 * @param latitude float[optional]
 * @param longitude float[optional]
 * @param zenith float[optional]
 * @param gmt_offset float[optional]
 * @return mixed the sunrise time in a specified format on
 */
function date_sunrise ($timestamp, $format = null, $latitude = null, $longitude = null, $zenith = null, $gmt_offset = null) {}

/**
 * Returns time of sunset for a given day and location
 * @link http://php.net/manual/en/function.date-sunset.php
 * @param timestamp int
 * @param format int[optional]
 * @param latitude float[optional]
 * @param longitude float[optional]
 * @param zenith float[optional]
 * @param gmt_offset float[optional]
 * @return mixed the sunset time in a specified format on
 */
function date_sunset ($timestamp, $format = null, $latitude = null, $longitude = null, $zenith = null, $gmt_offset = null) {}

/**
 * Returns an array with information about sunset/sunrise and twilight begin/end
 * @link http://php.net/manual/en/function.date-sun-info.php
 * @param time int
 * @param latitude float
 * @param longitude float
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

// End of date v.5.2.5
?>
