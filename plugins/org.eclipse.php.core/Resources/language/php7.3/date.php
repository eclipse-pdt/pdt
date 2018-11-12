<?php

// Start of date v.7.3.0

/**
 * DateTimeInterface is meant so that both DateTime and DateTimeImmutable can
 * be type hinted for. It is not possible to implement this interface with
 * userland classes.
 * @link http://www.php.net/manual/en/class.datetimeinterface.php
 */
interface DateTimeInterface  {
	const ATOM = "Y-m-d\TH:i:sP";
	const COOKIE = "l, d-M-Y H:i:s T";
	const ISO8601 = "Y-m-d\TH:i:sO";
	const RFC822 = "D, d M y H:i:s O";
	const RFC850 = "l, d-M-y H:i:s T";
	const RFC1036 = "D, d M y H:i:s O";
	const RFC1123 = "D, d M Y H:i:s O";
	const RFC7231 = "D, d M Y H:i:s \G\M\T";
	const RFC2822 = "D, d M Y H:i:s O";
	const RFC3339 = "Y-m-d\TH:i:sP";
	const RFC3339_EXTENDED = "Y-m-d\TH:i:s.vP";
	const RSS = "D, d M Y H:i:s O";
	const W3C = "Y-m-d\TH:i:sP";


	/**
	 * @param mixed $format
	 */
	abstract public function format ($format);

	abstract public function getTimezone ();

	abstract public function getOffset ();

	abstract public function getTimestamp ();

	/**
	 * @param mixed $object
	 * @param mixed $absolute [optional]
	 */
	abstract public function diff ($object, $absolute = null);

	abstract public function __wakeup ();

}

/**
 * Representation of date and time.
 * @link http://www.php.net/manual/en/class.datetime.php
 */
class DateTime implements DateTimeInterface {
	const ATOM = "Y-m-d\TH:i:sP";
	const COOKIE = "l, d-M-Y H:i:s T";
	const ISO8601 = "Y-m-d\TH:i:sO";
	const RFC822 = "D, d M y H:i:s O";
	const RFC850 = "l, d-M-y H:i:s T";
	const RFC1036 = "D, d M y H:i:s O";
	const RFC1123 = "D, d M Y H:i:s O";
	const RFC7231 = "D, d M Y H:i:s \G\M\T";
	const RFC2822 = "D, d M Y H:i:s O";
	const RFC3339 = "Y-m-d\TH:i:sP";
	const RFC3339_EXTENDED = "Y-m-d\TH:i:s.vP";
	const RSS = "D, d M Y H:i:s O";
	const W3C = "Y-m-d\TH:i:sP";


	/**
	 * Returns new DateTime object
	 * @link http://www.php.net/manual/en/datetime.construct.php
	 * @param string $time [optional] <p>A date/time string. date.formats</p>
	 * <p>
	 * Enter "now" here to obtain the current time when using
	 * the $timezone parameter.
	 * </p>
	 * @param DateTimeZone $timezone [optional] <p>
	 * A DateTimeZone object representing the
	 * timezone of $time.
	 * </p>
	 * <p>
	 * If $timezone is omitted,
	 * the current timezone will be used.
	 * </p>
	 * <p>
	 * The $timezone parameter
	 * and the current timezone are ignored when the
	 * $time parameter either
	 * is a UNIX timestamp (e.g. @946684800)
	 * or specifies a timezone
	 * (e.g. 2010-01-28T15:00:00+02:00).
	 * </p>
	 * @return DateTime a new DateTime instance.
	 * style.procedural returns false on failure.
	 */
	public function __construct (string $time = null, DateTimeZone $timezone = null) {}

	/**
	 * The __wakeup handler
	 * @link http://www.php.net/manual/en/datetime.wakeup.php
	 */
	public function __wakeup () {}

	/**
	 * The __set_state handler
	 * @link http://www.php.net/manual/en/datetime.set-state.php
	 * @param array $array Initialization array.
	 * @return DateTime a new instance of a DateTime object.
	 */
	public static function __set_state (array $array) {}

	/**
	 * @param mixed $DateTimeImmutable
	 */
	public static function createFromImmutable ($DateTimeImmutable) {}

	/**
	 * Parses a time string according to a specified format
	 * @link http://www.php.net/manual/en/datetime.createfromformat.php
	 * @param string $format <p>
	 * The format that the passed in string should be in. See the
	 * formatting options below. In most cases, the same letters as for the
	 * date can be used.
	 * </p>
	 * <p>
	 * <table>
	 * The following characters are recognized in the
	 * format parameter string
	 * <table>
	 * <tr valign="top">
	 * <td>format character</td>
	 * <td>Description</td>
	 * <td>Example parsable values</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>Day</td>
	 * <td>---</td>
	 * <td>---</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>d and j</td>
	 * <td>Day of the month, 2 digits with or without leading zeros</td>
	 * <td>
	 * 01 to 31 or
	 * 1 to 31
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>D and l</td>
	 * <td>A textual representation of a day</td>
	 * <td>
	 * Mon through Sun or
	 * Sunday through Saturday
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>S</td>
	 * <td>English ordinal suffix for the day of the month, 2
	 * characters. It's ignored while processing.</td>
	 * <td>
	 * st, nd, rd or
	 * th.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>z</td>
	 * <td>The day of the year (starting from 0)</td>
	 * <td>0 through 365</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>Month</td>
	 * <td>---</td>
	 * <td>---</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>F and M</td>
	 * <td>A textual representation of a month, such as January or Sept</td>
	 * <td>
	 * January through December or
	 * Jan through Dec
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>m and n</td>
	 * <td>Numeric representation of a month, with or without leading zeros</td>
	 * <td>
	 * 01 through 12 or
	 * 1 through 12
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>Year</td>
	 * <td>---</td>
	 * <td>---</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>Y</td>
	 * <td>A full numeric representation of a year, 4 digits</td>
	 * <td>Examples: 1999 or 2003</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>y</td>
	 * <td>
	 * A two digit representation of a year (which is assumed to be in the
	 * range 1970-2069, inclusive)
	 * </td>
	 * <td>
	 * Examples:
	 * 99 or 03
	 * (which will be interpreted as 1999 and
	 * 2003, respectively)
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>Time</td>
	 * <td>---</td>
	 * <td>---</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>a and A</td>
	 * <td>Ante meridiem and Post meridiem</td>
	 * <td>am or pm</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>g and h</td>
	 * <td>12-hour format of an hour with or without leading zero</td>
	 * <td>
	 * 1 through 12 or
	 * 01 through 12
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>G and H</td>
	 * <td>24-hour format of an hour with or without leading zeros</td>
	 * <td>
	 * 0 through 23 or
	 * 00 through 23
	 * </td>
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
	 * <td>Microseconds (up to six digits)</td>
	 * <td>Example: 45, 654321</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>Timezone</td>
	 * <td>---</td>
	 * <td>---</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>
	 * e, O,
	 * P and T
	 * </td>
	 * <td>Timezone identifier, or difference to UTC in hours, or
	 * difference to UTC with colon between hours and minutes, or timezone
	 * abbreviation</td>
	 * <td>Examples: UTC, GMT,
	 * Atlantic/Azores or 
	 * +0200 or +02:00 or
	 * EST, MDT
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>Full Date/Time</td>
	 * <td>---</td>
	 * <td>---</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>U</td>
	 * <td>Seconds since the Unix Epoch (January 1 1970 00:00:00 GMT)</td>
	 * <td>Example: 1292177455</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>Whitespace and Separators</td>
	 * <td>---</td>
	 * <td>---</td>
	 * </tr>
	 * <tr valign="top">
	 * <td> (space)</td>
	 * <td>One space or one tab</td>
	 * <td>Example: </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>#</td>
	 * <td>
	 * One of the following separation symbol: ;,
	 * :, /, .,
	 * ,, -, ( or
	 * )
	 * </td>
	 * <td>Example: /</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>
	 * ;,
	 * :, /, .,
	 * ,, -, ( or
	 * )
	 * </td>
	 * <td>The specified character.</td>
	 * <td>Example: -</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>?</td>
	 * <td>A random byte</td>
	 * <td>Example: ^ (Be aware that for UTF-8
	 * characters you might need more than one ?.
	 * In this case, using &#42; is probably what you want
	 * instead)</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>&#42;</td>
	 * <td>Random bytes until the next separator or digit</td>
	 * <td>Example: &#42; in Y-&#42;-d with
	 * the string 2009-aWord-08 will match
	 * aWord</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>!</td>
	 * <td>Resets all fields (year, month, day, hour, minute, second,
	 * fraction and timezone information) to the Unix Epoch</td>
	 * <td>Without !, all fields will be set to the
	 * current date and time.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>|</td>
	 * <td>Resets all fields (year, month, day, hour, minute, second,
	 * fraction and timezone information) to the Unix Epoch if they have
	 * not been parsed yet</td>
	 * <td>Y-m-d| will set the year, month and day
	 * to the information found in the string to parse, and sets the hour,
	 * minute and second to 0.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>+</td>
	 * <td>If this format specifier is present, trailing data in the
	 * string will not cause an error, but a warning instead</td>
	 * <td>Use DateTime::getLastErrors to find out
	 * whether trailing data was present.</td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * <p>
	 * Unrecognized characters in the format string will cause the
	 * parsing to fail and an error message is appended to the returned
	 * structure. You can query error messages with
	 * DateTime::getLastErrors.
	 * </p> 
	 * <p>
	 * To include literal characters in format, you have
	 * to escape them with a backslash (\).
	 * </p>
	 * <p>
	 * If format does not contain the character
	 * ! then portions of the generated time which are not
	 * specified in format will be set to the current
	 * system time.
	 * </p>
	 * <p>
	 * If format contains the
	 * character !, then portions of the generated
	 * time not provided in format, as well as
	 * values to the left-hand side of the !, will
	 * be set to corresponding values from the Unix epoch.
	 * </p>
	 * <p>
	 * The Unix epoch is 1970-01-01 00:00:00 UTC.
	 * </p>
	 * @param string $time String representing the time.
	 * @param DateTimeZone $timezone [optional] <p>
	 * A DateTimeZone object representing the
	 * desired time zone.
	 * </p>
	 * <p>
	 * If timezone is omitted and
	 * time contains no timezone,
	 * the current timezone will be used.
	 * </p>
	 * <p>
	 * The timezone parameter
	 * and the current timezone are ignored when the
	 * time parameter either
	 * contains a UNIX timestamp (e.g. 946684800)
	 * or specifies a timezone
	 * (e.g. 2010-01-28T15:00:00+02:00).
	 * </p>
	 * @return DateTime a new DateTime instance or false on failure.
	 */
	public static function createFromFormat (string $format, string $time, DateTimeZone $timezone = null) {}

	/**
	 * Returns the warnings and errors
	 * @link http://www.php.net/manual/en/datetime.getlasterrors.php
	 * @return array array containing info about warnings and errors.
	 */
	public static function getLastErrors () {}

	/**
	 * Returns date formatted according to given format
	 * @link http://www.php.net/manual/en/datetime.format.php
	 * @param string $format Format accepted by date.
	 * @return string the formatted date string on success or false on failure.
	 */
	public function format (string $format) {}

	/**
	 * Alters the timestamp
	 * @link http://www.php.net/manual/en/datetime.modify.php
	 * @param string $modify A date/time string. date.formats
	 * @return DateTime the DateTime object for method chainingreturn.falseforfailure.
	 */
	public function modify (string $modify) {}

	/**
	 * Adds an amount of days, months, years, hours, minutes and seconds to a
	 * DateTime object
	 * @link http://www.php.net/manual/en/datetime.add.php
	 * @param DateInterval $interval A DateInterval object
	 * @return DateTime the DateTime object for method chainingreturn.falseforfailure.
	 */
	public function add (DateInterval $interval) {}

	/**
	 * Subtracts an amount of days, months, years, hours, minutes and seconds from
	 * a DateTime object
	 * @link http://www.php.net/manual/en/datetime.sub.php
	 * @param DateInterval $interval A DateInterval object
	 * @return DateTime the DateTime object for method chainingreturn.falseforfailure.
	 */
	public function sub (DateInterval $interval) {}

	/**
	 * Return time zone relative to given DateTime
	 * @link http://www.php.net/manual/en/datetime.gettimezone.php
	 * @return DateTimeZone a DateTimeZone object on success
	 * or false on failure.
	 */
	public function getTimezone () {}

	/**
	 * Sets the time zone for the DateTime object
	 * @link http://www.php.net/manual/en/datetime.settimezone.php
	 * @param DateTimeZone $timezone A DateTimeZone object representing the
	 * desired time zone.
	 * @return DateTime the DateTime object for method chainingreturn.falseforfailure.
	 */
	public function setTimezone (DateTimeZone $timezone) {}

	/**
	 * Returns the timezone offset
	 * @link http://www.php.net/manual/en/datetime.getoffset.php
	 * @return int the timezone offset in seconds from UTC on success
	 * or false on failure.
	 */
	public function getOffset () {}

	/**
	 * Sets the time
	 * @link http://www.php.net/manual/en/datetime.settime.php
	 * @param int $hour Hour of the time.
	 * @param int $minute Minute of the time.
	 * @param int $second [optional] Second of the time.
	 * @param int $microseconds [optional] Microsecond of the time.
	 * @return DateTime the DateTime object for method chainingreturn.falseforfailure.
	 */
	public function setTime (int $hour, int $minute, int $second = null, int $microseconds = null) {}

	/**
	 * Sets the date
	 * @link http://www.php.net/manual/en/datetime.setdate.php
	 * @param int $year Year of the date.
	 * @param int $month Month of the date.
	 * @param int $day Day of the date.
	 * @return DateTime the DateTime object for method chainingreturn.falseforfailure.
	 */
	public function setDate (int $year, int $month, int $day) {}

	/**
	 * Sets the ISO date
	 * @link http://www.php.net/manual/en/datetime.setisodate.php
	 * @param int $year Year of the date.
	 * @param int $week Week of the date.
	 * @param int $day [optional] Offset from the first day of the week.
	 * @return DateTime the DateTime object for method chainingreturn.falseforfailure.
	 */
	public function setISODate (int $year, int $week, int $day = null) {}

	/**
	 * Sets the date and time based on an Unix timestamp
	 * @link http://www.php.net/manual/en/datetime.settimestamp.php
	 * @param int $unixtimestamp Unix timestamp representing the date.
	 * @return DateTime the DateTime object for method chainingreturn.falseforfailure.
	 */
	public function setTimestamp (int $unixtimestamp) {}

	/**
	 * Gets the Unix timestamp
	 * @link http://www.php.net/manual/en/datetime.gettimestamp.php
	 * @return int the Unix timestamp representing the date.
	 */
	public function getTimestamp () {}

	/**
	 * Returns the difference between two DateTime objects
	 * @link http://www.php.net/manual/en/datetime.diff.php
	 * @param DateTimeInterface $datetime2 
	 * @param bool $absolute [optional] Should the interval be forced to be positive?
	 * @return DateInterval The DateInterval object representing the
	 * difference between the two dates or false on failure.
	 */
	public function diff ($datetime2, bool $absolute = null) {}

}

/**
 * This class behaves the same as DateTime except it
 * never modifies itself but returns a new object instead.
 * @link http://www.php.net/manual/en/class.datetimeimmutable.php
 */
class DateTimeImmutable implements DateTimeInterface {
	const ATOM = "Y-m-d\TH:i:sP";
	const COOKIE = "l, d-M-Y H:i:s T";
	const ISO8601 = "Y-m-d\TH:i:sO";
	const RFC822 = "D, d M y H:i:s O";
	const RFC850 = "l, d-M-y H:i:s T";
	const RFC1036 = "D, d M y H:i:s O";
	const RFC1123 = "D, d M Y H:i:s O";
	const RFC7231 = "D, d M Y H:i:s \G\M\T";
	const RFC2822 = "D, d M Y H:i:s O";
	const RFC3339 = "Y-m-d\TH:i:sP";
	const RFC3339_EXTENDED = "Y-m-d\TH:i:s.vP";
	const RSS = "D, d M Y H:i:s O";
	const W3C = "Y-m-d\TH:i:sP";


	/**
	 * Returns new DateTimeImmutable object
	 * @link http://www.php.net/manual/en/datetimeimmutable.construct.php
	 * @param string $time [optional] 
	 * @param DateTimeZone $timezone [optional] 
	 * @return DateTimeImmutable 
	 */
	public function __construct (string $time = null, DateTimeZone $timezone = null) {}

	public function __wakeup () {}

	/**
	 * The __set_state handler
	 * @link http://www.php.net/manual/en/datetimeimmutable.set-state.php
	 * @param array $array 
	 * @return DateTimeImmutable 
	 */
	public static function __set_state (array $array) {}

	/**
	 * Parses a time string according to a specified format
	 * @link http://www.php.net/manual/en/datetimeimmutable.createfromformat.php
	 * @param string $format 
	 * @param string $time 
	 * @param DateTimeZone $timezone [optional] 
	 * @return DateTimeImmutable 
	 */
	public static function createFromFormat (string $format, string $time, DateTimeZone $timezone = null) {}

	/**
	 * Returns the warnings and errors
	 * @link http://www.php.net/manual/en/datetimeimmutable.getlasterrors.php
	 * @return array 
	 */
	public static function getLastErrors () {}

	/**
	 * @param mixed $format
	 */
	public function format ($format) {}

	public function getTimezone () {}

	public function getOffset () {}

	public function getTimestamp () {}

	/**
	 * @param mixed $object
	 * @param mixed $absolute [optional]
	 */
	public function diff ($object, $absolute = null) {}

	/**
	 * Creates a new object with modified timestamp
	 * @link http://www.php.net/manual/en/datetimeimmutable.modify.php
	 * @param string $modify A date/time string. date.formats
	 * @return DateTimeImmutable the newly created object or false on failure.
	 */
	public function modify (string $modify) {}

	/**
	 * Adds an amount of days, months, years, hours, minutes and seconds
	 * @link http://www.php.net/manual/en/datetimeimmutable.add.php
	 * @param DateInterval $interval 
	 * @return DateTimeImmutable 
	 */
	public function add (DateInterval $interval) {}

	/**
	 * Subtracts an amount of days, months, years, hours, minutes and seconds
	 * @link http://www.php.net/manual/en/datetimeimmutable.sub.php
	 * @param DateInterval $interval 
	 * @return DateTimeImmutable 
	 */
	public function sub (DateInterval $interval) {}

	/**
	 * Sets the time zone
	 * @link http://www.php.net/manual/en/datetimeimmutable.settimezone.php
	 * @param DateTimeZone $timezone 
	 * @return DateTimeImmutable 
	 */
	public function setTimezone (DateTimeZone $timezone) {}

	/**
	 * Sets the time
	 * @link http://www.php.net/manual/en/datetimeimmutable.settime.php
	 * @param int $hour 
	 * @param int $minute 
	 * @param int $second [optional] 
	 * @param int $microseconds [optional] 
	 * @return DateTimeImmutable 
	 */
	public function setTime (int $hour, int $minute, int $second = null, int $microseconds = null) {}

	/**
	 * Sets the date
	 * @link http://www.php.net/manual/en/datetimeimmutable.setdate.php
	 * @param int $year 
	 * @param int $month 
	 * @param int $day 
	 * @return DateTimeImmutable 
	 */
	public function setDate (int $year, int $month, int $day) {}

	/**
	 * Sets the ISO date
	 * @link http://www.php.net/manual/en/datetimeimmutable.setisodate.php
	 * @param int $year 
	 * @param int $week 
	 * @param int $day [optional] 
	 * @return DateTimeImmutable 
	 */
	public function setISODate (int $year, int $week, int $day = null) {}

	/**
	 * Sets the date and time based on a Unix timestamp
	 * @link http://www.php.net/manual/en/datetimeimmutable.settimestamp.php
	 * @param int $unixtimestamp 
	 * @return DateTimeImmutable 
	 */
	public function setTimestamp (int $unixtimestamp) {}

	/**
	 * Returns new DateTimeImmutable object encapsulating the given DateTime object
	 * @link http://www.php.net/manual/en/datetimeimmutable.createfrommutable.php
	 * @param DateTime $datetime The mutable DateTime object that you want to
	 * convert to an immutable version. This object is not modified, but
	 * instead a new DateTimeImmutable object is
	 * created containing the same date time and timezone information.
	 * @return DateTimeImmutable a new DateTimeImmutable instance.
	 */
	public static function createFromMutable (DateTime $datetime) {}

}

/**
 * Representation of time zone.
 * @link http://www.php.net/manual/en/class.datetimezone.php
 */
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
	 * Creates new DateTimeZone object
	 * @link http://www.php.net/manual/en/datetimezone.construct.php
	 * @param string $timezone One of the supported timezone names
	 * or an offset value (+0200).
	 * @return DateTimeZone DateTimeZone on success.
	 * style.procedural returns false on failure.
	 */
	public function __construct (string $timezone) {}

	public function __wakeup () {}

	/**
	 * @param mixed $array
	 */
	public static function __set_state (array $array) {}

	/**
	 * Returns the name of the timezone
	 * @link http://www.php.net/manual/en/datetimezone.getname.php
	 * @return string One of the timezone names in the
	 * <p>.
	 */
	public function getName () {}

	/**
	 * Returns the timezone offset from GMT
	 * @link http://www.php.net/manual/en/datetimezone.getoffset.php
	 * @param DateTime $datetime DateTime that contains the date/time to compute the offset from.
	 * @return int time zone offset in seconds on success or false on failure.
	 */
	public function getOffset (DateTime $datetime) {}

	/**
	 * Returns all transitions for the timezone
	 * @link http://www.php.net/manual/en/datetimezone.gettransitions.php
	 * @param int $timestamp_begin [optional] Begin timestamp.
	 * @param int $timestamp_end [optional] End timestamp.
	 * @return array numerically indexed array containing associative array with all
	 * transitions on success or false on failure.
	 */
	public function getTransitions (int $timestamp_begin = null, int $timestamp_end = null) {}

	/**
	 * Returns location information for a timezone
	 * @link http://www.php.net/manual/en/datetimezone.getlocation.php
	 * @return array Array containing location information about timezone or false on failure.
	 */
	public function getLocation () {}

	/**
	 * Returns associative array containing dst, offset and the timezone name
	 * @link http://www.php.net/manual/en/datetimezone.listabbreviations.php
	 * @return array array on success or false on failure.
	 */
	public static function listAbbreviations () {}

	/**
	 * Returns a numerically indexed array containing all defined timezone identifiers
	 * @link http://www.php.net/manual/en/datetimezone.listidentifiers.php
	 * @param int $what [optional] One of DateTimeZone class constants.
	 * @param string $country [optional] <p>
	 * A two-letter ISO 3166-1 compatible country code.
	 * </p>
	 * This option is only used when what is set to
	 * DateTimeZone::PER_COUNTRY.
	 * @return array array on success or false on failure.
	 */
	public static function listIdentifiers (int $what = null, string $country = null) {}

}

/**
 * Represents a date interval.
 * <p>A date interval stores either a fixed amount of time (in years, months,
 * days, hours etc) or a relative time string in the format that
 * DateTime's constructor supports.</p>
 * @link http://www.php.net/manual/en/class.dateinterval.php
 */
class DateInterval  {

	/**
	 * Number of years.
	 * @var integer
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.y
	 */
	public $y;

	/**
	 * Number of months.
	 * @var integer
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.m
	 */
	public $m;

	/**
	 * Number of days.
	 * @var integer
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.d
	 */
	public $d;

	/**
	 * Number of hours.
	 * @var integer
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.h
	 */
	public $h;

	/**
	 * Number of minutes.
	 * @var integer
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.i
	 */
	public $i;

	/**
	 * Number of seconds.
	 * @var integer
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.s
	 */
	public $s;

	/**
	 * Number of microseconds, as a fraction of a second.
	 * @var float
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.f
	 */
	public $f;

	/**
	 * Is 1 if the interval
	 * represents a negative time period and
	 * 0 otherwise.
	 * See DateInterval::format.
	 * @var integer
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.invert
	 */
	public $invert;

	/**
	 * If the DateInterval object was created by
	 * DateTime::diff, then this is the total number of
	 * days between the start and end dates. Otherwise,
	 * days will be false.
	 * @var mixed
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.days
	 */
	public $days;

	/**
	 * Creates a new DateInterval object
	 * @link http://www.php.net/manual/en/dateinterval.construct.php
	 * @param mixed $interval_spec
	 */
	public function __construct ($interval_spec) {}

	public function __wakeup () {}

	/**
	 * @param mixed $array
	 */
	public static function __set_state (array $array) {}

	/**
	 * Formats the interval
	 * @link http://www.php.net/manual/en/dateinterval.format.php
	 * @param string $format <table>
	 * The following characters are recognized in the
	 * format parameter string.
	 * Each format character must be prefixed by a percent sign
	 * (%).
	 * <table>
	 * <tr valign="top">
	 * <td>format character</td>
	 * <td>Description</td>
	 * <td>Example values</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>%</td>
	 * <td>Literal %</td>
	 * <td>%</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>Y</td>
	 * <td>Years, numeric, at least 2 digits with leading 0</td>
	 * <td>01, 03</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>y</td>
	 * <td>Years, numeric</td>
	 * <td>1, 3</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>M</td>
	 * <td>Months, numeric, at least 2 digits with leading 0</td>
	 * <td>01, 03, 12</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>m</td>
	 * <td>Months, numeric</td>
	 * <td>1, 3, 12</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>D</td>
	 * <td>Days, numeric, at least 2 digits with leading 0</td>
	 * <td>01, 03, 31</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>d</td>
	 * <td>Days, numeric</td>
	 * <td>1, 3, 31</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>a</td>
	 * <td>Total number of days as a result of a DateTime::diff or (unknown) otherwise</td>
	 * <td>4, 18, 8123</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>H</td>
	 * <td>Hours, numeric, at least 2 digits with leading 0</td>
	 * <td>01, 03, 23</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>h</td>
	 * <td>Hours, numeric</td>
	 * <td>1, 3, 23</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>I</td>
	 * <td>Minutes, numeric, at least 2 digits with leading 0</td>
	 * <td>01, 03, 59</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>i</td>
	 * <td>Minutes, numeric</td>
	 * <td>1, 3, 59</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>S</td>
	 * <td>Seconds, numeric, at least 2 digits with leading 0</td>
	 * <td>01, 03, 57</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>s</td>
	 * <td>Seconds, numeric</td>
	 * <td>1, 3, 57</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>F</td>
	 * <td>Microseconds, numeric, at least 6 digits with leading
	 * 0</td>
	 * <td>007701, 052738, 428291</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>f</td>
	 * <td>Microseconds, numeric</td>
	 * <td>7701, 52738, 428291</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>R</td>
	 * <td>Sign "-" when negative, "+" when positive</td>
	 * <td>-, +</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>r</td>
	 * <td>Sign "-" when negative, empty when positive</td>
	 * <td>-, </td>
	 * </tr>
	 * </table>
	 * </table>
	 * @return string the formatted interval.
	 */
	public function format (string $format) {}

	/**
	 * Sets up a DateInterval from the relative parts of the string
	 * @link http://www.php.net/manual/en/dateinterval.createfromdatestring.php
	 * @param string $time A date with relative parts. Specifically, the
	 * relative formats
	 * supported by the parser used for strtotime and
	 * DateTime will be used to construct the
	 * DateInterval.
	 * @return DateInterval a new DateInterval instance.
	 */
	public static function createFromDateString (string $time) {}

}

/**
 * Represents a date period.
 * <p>A date period allows iteration over a set of dates and times, recurring at
 * regular intervals, over a given period.</p>
 * @link http://www.php.net/manual/en/class.dateperiod.php
 */
class DatePeriod implements Traversable {
	const EXCLUDE_START_DATE = 1;


	/**
	 * The number of recurrences.
	 * @var integer
	 * @link http://www.php.net/manual/en/class.dateperiod.php#dateperiod.props.recurrences
	 */
	public $recurrences;

	/**
	 * Whether to include the start date in the set of recurring dates or not.
	 * @var boolean
	 * @link http://www.php.net/manual/en/class.dateperiod.php#dateperiod.props.include_start_date
	 */
	public $include_start_date;

	/**
	 * The start date of the period.
	 * @var DateTimeInterface
	 * @link http://www.php.net/manual/en/class.dateperiod.php#dateperiod.props.start
	 */
	public $start;

	/**
	 * During iteration this will contain the current date within the period.
	 * @var DateTimeInterface
	 * @link http://www.php.net/manual/en/class.dateperiod.php#dateperiod.props.current
	 */
	public $current;

	/**
	 * The end date of the period.
	 * @var DateTimeInterface
	 * @link http://www.php.net/manual/en/class.dateperiod.php#dateperiod.props.end
	 */
	public $end;

	/**
	 * An ISO 8601 repeating interval specification.
	 * @var DateInterval
	 * @link http://www.php.net/manual/en/class.dateperiod.php#dateperiod.props.interval
	 */
	public $interval;

	/**
	 * Creates a new DatePeriod object
	 * @link http://www.php.net/manual/en/dateperiod.construct.php
	 * @param mixed $start
	 * @param mixed $interval
	 * @param mixed $end
	 */
	public function __construct ($start, $interval, $end) {}

	public function __wakeup () {}

	/**
	 * @param mixed $array
	 */
	public static function __set_state (array $array) {}

	/**
	 * Gets the start date
	 * @link http://www.php.net/manual/en/dateperiod.getstartdate.php
	 * @return DateTimeInterface a DateTimeImmutable object
	 * when the DatePeriod is initialized with a
	 * DateTimeImmutable object
	 * as the start parameter.
	 * <p>
	 * Returns a DateTime object
	 * otherwise.
	 * </p>
	 */
	public function getStartDate () {}

	/**
	 * Gets the end date
	 * @link http://www.php.net/manual/en/dateperiod.getenddate.php
	 * @return DateTimeInterface null if the DatePeriod does
	 * not have an end date. For example, when initialized with the
	 * recurrences parameter, or the
	 * isostr parameter without an
	 * end date.
	 * <p>
	 * Returns a DateTimeImmutable object
	 * when the DatePeriod is initialized with a
	 * DateTimeImmutable object
	 * as the end parameter.
	 * </p>
	 * <p>
	 * Returns a DateTime object
	 * otherwise.
	 * </p>
	 */
	public function getEndDate () {}

	/**
	 * Gets the interval
	 * @link http://www.php.net/manual/en/dateperiod.getdateinterval.php
	 * @return DateInterval a DateInterval object
	 */
	public function getDateInterval () {}

}

/**
 * Parse about any English textual datetime description into a Unix timestamp
 * @link http://www.php.net/manual/en/function.strtotime.php
 * @param string $time A date/time string. date.formats
 * @param int $now [optional] The timestamp which is used as a base for the calculation of relative
 * dates.
 * @return int a timestamp on success, false otherwise. Previous to PHP 5.1.0,
 * this function would return -1 on failure.
 */
function strtotime (string $time, int $now = null) {}

/**
 * Format a local time/date
 * @link http://www.php.net/manual/en/function.date.php
 * @param string $format <p>
 * The format of the outputted date string. See the formatting
 * options below. There are also several
 * predefined date constants
 * that may be used instead, so for example DATE_RSS
 * contains the format string 'D, d M Y H:i:s'.
 * </p>
 * <p>
 * <table>
 * The following characters are recognized in the
 * format parameter string
 * <table>
 * <tr valign="top">
 * <td>format character</td>
 * <td>Description</td>
 * <td>Example returned values</td>
 * </tr>
 * <tr valign="top">
 * <td>Day</td>
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
 * <td>Week</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>W</td>
 * <td>ISO-8601 week number of year, weeks starting on Monday</td>
 * <td>Example: 42 (the 42nd week in the year)</td>
 * </tr>
 * <tr valign="top">
 * <td>Month</td>
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
 * <td>Year</td>
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
 * <td>ISO-8601 week-numbering year. This has the same value as
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
 * <td>Time</td>
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
 * <td>
 * Microseconds (added in PHP 5.2.2). Note that
 * date will always generate
 * 000000 since it takes an integer
 * parameter, whereas DateTime::format does
 * support microseconds if DateTime was
 * created with microseconds.
 * </td>
 * <td>Example: 654321</td>
 * </tr>
 * <tr valign="top">
 * <td>v</td>
 * <td>
 * Milliseconds (added in PHP 7.0.0). Same note applies as for
 * u.
 * </td>
 * <td>Example: 654</td>
 * </tr>
 * <tr valign="top">
 * <td>Timezone</td>
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
 * <td>Full Date/Time</td>
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
 * @param int $timestamp [optional] 
 * @return string a formatted date string. If a non-numeric value is used for 
 * timestamp, false is returned and an 
 * E_WARNING level error is emitted.
 */
function date (string $format, int $timestamp = null) {}

/**
 * Format a local time/date as integer
 * @link http://www.php.net/manual/en/function.idate.php
 * @param string $format <table>
 * The following characters are recognized in the
 * format parameter string
 * <table>
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
 * </table>
 * @param int $timestamp [optional] 
 * @return int an integer.
 * <p>
 * As idate always returns an integer and
 * as they can't start with a "0", idate may return
 * fewer digits than you would expect. See the example below.
 * </p>
 */
function idate (string $format, int $timestamp = null) {}

/**
 * Format a GMT/UTC date/time
 * @link http://www.php.net/manual/en/function.gmdate.php
 * @param string $format The format of the outputted date string. See the formatting
 * options for the date function.
 * @param int $timestamp [optional] 
 * @return string a formatted date string. If a non-numeric value is used for 
 * timestamp, false is returned and an 
 * E_WARNING level error is emitted.
 */
function gmdate (string $format, int $timestamp = null) {}

/**
 * Get Unix timestamp for a date
 * @link http://www.php.net/manual/en/function.mktime.php
 * @param int $hour [optional] The number of the hour relative to the start of the day determined by
 * month, day and year.
 * Negative values reference the hour before midnight of the day in question.
 * Values greater than 23 reference the appropriate hour in the following day(s).
 * @param int $minute [optional] The number of the minute relative to the start of the hour.
 * Negative values reference the minute in the previous hour.
 * Values greater than 59 reference the appropriate minute in the following hour(s).
 * @param int $second [optional] The number of seconds relative to the start of the minute.
 * Negative values reference the second in the previous minute.
 * Values greater than 59 reference the appropriate second in the following minute(s).
 * @param int $month [optional] The number of the month relative to the end of the previous year.
 * Values 1 to 12 reference the normal calendar months of the year in question.
 * Values less than 1 (including negative values) reference the months in the previous year in reverse order, so 0 is December, -1 is November, etc.
 * Values greater than 12 reference the appropriate month in the following year(s).
 * @param int $day [optional] The number of the day relative to the end of the previous month.
 * Values 1 to 28, 29, 30 or 31 (depending upon the month) reference the normal days in the relevant month.
 * Values less than 1 (including negative values) reference the days in the previous month, so 0 is the last day of the previous month, -1 is the day before that, etc.
 * Values greater than the number of days in the relevant month reference the appropriate day in the following month(s).
 * @param int $year [optional] The number of the year, may be a two or four digit value,
 * with values between 0-69 mapping to 2000-2069 and 70-100 to
 * 1970-2000. On systems where time_t is a 32bit signed integer, as
 * most common today, the valid range for year 
 * is somewhere between 1901 and 2038. However, before PHP 5.1.0 this
 * range was limited from 1970 to 2038 on some systems (e.g. Windows).
 * @param int $is_dst [optional] <p>
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
 * <p>
 * This parameter has been removed in PHP 7.0.0.
 * </p>
 * @return int mktime returns the Unix timestamp of the arguments
 * given.
 * If the arguments are invalid, the function returns false (before PHP 5.1
 * it returned -1).
 */
function mktime (int $hour = null, int $minute = null, int $second = null, int $month = null, int $day = null, int $year = null, int $is_dst = null) {}

/**
 * Get Unix timestamp for a GMT date
 * @link http://www.php.net/manual/en/function.gmmktime.php
 * @param int $hour [optional] The number of the hour relative to the start of the day determined by
 * month, day and year.
 * Negative values reference the hour before midnight of the day in question.
 * Values greater than 23 reference the appropriate hour in the following day(s).
 * @param int $minute [optional] The number of the minute relative to the start of the hour.
 * Negative values reference the minute in the previous hour.
 * Values greater than 59 reference the appropriate minute in the following hour(s).
 * @param int $second [optional] The number of seconds relative to the start of the minute.
 * Negative values reference the second in the previous minute.
 * Values greater than 59 reference the appropriate second in the following minute(s).
 * @param int $month [optional] The number of the month relative to the end of the previous year.
 * Values 1 to 12 reference the normal calendar months of the year in question.
 * Values less than 1 (including negative values) reference the months in the previous year in reverse order, so 0 is December, -1 is November, etc.
 * Values greater than 12 reference the appropriate month in the following year(s).
 * @param int $day [optional] The number of the day relative to the end of the previous month.
 * Values 1 to 28, 29, 30 or 31 (depending upon the month) reference the normal days in the relevant month.
 * Values less than 1 (including negative values) reference the days in the previous month, so 0 is the last day of the previous month, -1 is the day before that, etc.
 * Values greater than the number of days in the relevant month reference the appropriate day in the following month(s).
 * @param int $year [optional] The year
 * @param int $is_dst [optional] <p>
 * Parameters always represent a GMT date so is_dst
 * doesn't influence the result.
 * </p>
 * <p>
 * This parameter has been removed in PHP 7.0.0.
 * </p>
 * @return int a integer Unix timestamp.
 */
function gmmktime (int $hour = null, int $minute = null, int $second = null, int $month = null, int $day = null, int $year = null, int $is_dst = null) {}

/**
 * Validate a Gregorian date
 * @link http://www.php.net/manual/en/function.checkdate.php
 * @param int $month The month is between 1 and 12 inclusive.
 * @param int $day The day is within the allowed number of days for the given 
 * month. Leap years 
 * are taken into consideration.
 * @param int $year The year is between 1 and 32767 inclusive.
 * @return bool true if the date given is valid; otherwise returns false.
 */
function checkdate (int $month, int $day, int $year) {}

/**
 * Format a local time/date according to locale settings
 * @link http://www.php.net/manual/en/function.strftime.php
 * @param string $format <p>
 * <table>
 * The following characters are recognized in the
 * format parameter string
 * <table>
 * <tr valign="top">
 * <td>format</td>
 * <td>Description</td>
 * <td>Example returned values</td>
 * </tr>
 * <tr valign="top">
 * <td>Day</td>
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
 * <td>
 * Day of the month, with a space preceding single digits. Not 
 * implemented as described on Windows. See below for more information.
 * </td>
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
 * <td>1 (for Monday) through 7 (for Sunday)</td>
 * </tr>
 * <tr valign="top">
 * <td>%w</td>
 * <td>Numeric representation of the day of the week</td>
 * <td>0 (for Sunday) through 6 (for Saturday)</td>
 * </tr>
 * <tr valign="top">
 * <td>Week</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>%U</td>
 * <td>Week number of the given year, starting with the first
 * Sunday as the first week</td>
 * <td>13 (for the 13th full week of the year)</td>
 * </tr>
 * <tr valign="top">
 * <td>%V</td>
 * <td>ISO-8601:1988 week number of the given year, starting with
 * the first week of the year with at least 4 weekdays, with Monday
 * being the start of the week</td>
 * <td>01 through 53 (where 53
 * accounts for an overlapping week)</td>
 * </tr>
 * <tr valign="top">
 * <td>%W</td>
 * <td>A numeric representation of the week of the year, starting
 * with the first Monday as the first week</td>
 * <td>46 (for the 46th week of the year beginning
 * with a Monday)</td>
 * </tr>
 * <tr valign="top">
 * <td>Month</td>
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
 * <td>Year</td>
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
 * <td>Time</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>%H</td>
 * <td>Two digit representation of the hour in 24-hour format</td>
 * <td>00 through 23</td>
 * </tr>
 * <tr valign="top">
 * <td>%k</td>
 * <td>Hour in 24-hour format, with a space preceding single digits</td>
 * <td> 0 through 23</td>
 * </tr>
 * <tr valign="top">
 * <td>%I</td>
 * <td>Two digit representation of the hour in 12-hour format</td>
 * <td>01 through 12</td>
 * </tr>
 * <tr valign="top">
 * <td>%l (lower-case 'L')</td>
 * <td>Hour in 12-hour format, with a space preceding single digits</td>
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
 * <td>The time zone offset. Not implemented as described on
 * Windows. See below for more information.</td>
 * <td>Example: -0500 for US Eastern Time</td>
 * </tr>
 * <tr valign="top">
 * <td>%Z</td>
 * <td>The time zone abbreviation. Not implemented as described on
 * Windows. See below for more information.</td>
 * <td>Example: EST for Eastern Time</td>
 * </tr>
 * <tr valign="top">
 * <td>Time and Date Stamps</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>%c</td>
 * <td>Preferred date and time stamp based on locale</td>
 * <td>Example: Tue Feb 5 00:45:10 2009 for
 * February 5, 2009 at 12:45:10 AM</td>
 * </tr>
 * <tr valign="top">
 * <td>%D</td>
 * <td>Same as "%m/%d/%y"</td>
 * <td>Example: 02/05/09 for February 5, 2009</td>
 * </tr>
 * <tr valign="top">
 * <td>%F</td>
 * <td>Same as "%Y-%m-%d" (commonly used in database datestamps)</td>
 * <td>Example: 2009-02-05 for February 5, 2009</td>
 * </tr>
 * <tr valign="top">
 * <td>%s</td>
 * <td>Unix Epoch Time timestamp (same as the time
 * function)</td>
 * <td>Example: 305815200 for September 10, 1979 08:40:00 AM</td>
 * </tr>
 * <tr valign="top">
 * <td>%x</td>
 * <td>Preferred date representation based on locale, without the time</td>
 * <td>Example: 02/05/09 for February 5, 2009</td>
 * </tr>
 * <tr valign="top">
 * <td>Miscellaneous</td>
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
 * </table>
 * </table>
 * </p>
 * <p>
 * Maximum length of this parameter is 1023 characters.
 * </p>
 * Contrary to ISO-9899:1999, Sun Solaris starts with Sunday as 1. As a
 * result, %u may not function as described in this
 * manual.
 * <p>
 * Windows only:
 * </p>
 * <p>
 * The %e modifier is not supported in the Windows
 * implementation of this function. To achieve this value, the
 * %#d modifier can be used instead. The example below
 * illustrates how to write a cross platform compatible function.
 * </p>
 * <p>
 * The %z and %Z modifiers both
 * return the time zone name instead of the offset or abbreviation.
 * </p>
 * macOS only: The %P modifier 
 * is not supported in the macOS implementation of this function.
 * @param int $timestamp [optional] 
 * @return string a string formatted according format
 * using the given timestamp or the current
 * local time if no timestamp is given. Month and weekday names and
 * other language-dependent strings respect the current locale set
 * with setlocale.
 */
function strftime (string $format, int $timestamp = null) {}

/**
 * Format a GMT/UTC time/date according to locale settings
 * @link http://www.php.net/manual/en/function.gmstrftime.php
 * @param string $format See description in strftime.
 * @param int $timestamp [optional] 
 * @return string a string formatted according to the given format string
 * using the given timestamp or the current
 * local time if no timestamp is given. Month and weekday names and
 * other language dependent strings respect the current locale set
 * with setlocale.
 */
function gmstrftime (string $format, int $timestamp = null) {}

/**
 * Return current Unix timestamp
 * @link http://www.php.net/manual/en/function.time.php
 * @return int 
 */
function time () {}

/**
 * Get the local time
 * @link http://www.php.net/manual/en/function.localtime.php
 * @param int $timestamp [optional] 
 * @param bool $is_associative [optional] <p>
 * If set to false or not supplied then the array is returned as a regular, 
 * numerically indexed array. If the argument is set to true then
 * localtime returns an associative array containing
 * all the different elements of the structure returned by the C
 * function call to localtime. The names of the different keys of
 * the associative array are as follows:
 * </p>
 * <p>
 * <p>
 * <br>
 * "tm_sec" - seconds, 0 to 59
 * <br>
 * "tm_min" - minutes, 0 to 59
 * <br>
 * "tm_hour" - hours, 0 to 23
 * <br>
 * "tm_mday" - day of the month, 1 to 31
 * <br>
 * "tm_mon" - month of the year, 0 (Jan) to 11 (Dec)
 * <br>
 * "tm_year" - years since 1900
 * <br>
 * "tm_wday" - day of the week, 0 (Sun) to 6 (Sat)
 * <br>
 * "tm_yday" - day of the year, 0 to 365
 * <br>
 * "tm_isdst" - is daylight savings time in effect? 
 * Positive if yes, 0 if not, negative if unknown.
 * </p>
 * </p>
 * @return array 
 */
function localtime (int $timestamp = null, bool $is_associative = null) {}

/**
 * Get date/time information
 * @link http://www.php.net/manual/en/function.getdate.php
 * @param int $timestamp [optional] 
 * @return array an associative array of information related to
 * the timestamp. Elements from the returned 
 * associative array are as follows:
 * <p>
 * <table>
 * Key elements of the returned associative array
 * <table>
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
 * </table>
 * </p>
 */
function getdate (int $timestamp = null) {}

/**
 * Alias: DateTime::__construct
 * @link http://www.php.net/manual/en/function.date-create.php
 * @param mixed $time [optional]
 * @param mixed $timezone [optional]
 */
function date_create ($time = null, $timezone = null) {}

/**
 * Alias: DateTimeImmutable::__construct
 * @link http://www.php.net/manual/en/function.date-create-immutable.php
 * @param mixed $time [optional]
 * @param mixed $timezone [optional]
 */
function date_create_immutable ($time = null, $timezone = null) {}

/**
 * Alias: DateTime::createFromFormat
 * @link http://www.php.net/manual/en/function.date-create-from-format.php
 * @param mixed $format
 * @param mixed $time
 * @param DateTimeZone $object [optional]
 */
function date_create_from_format ($format, $timeDateTimeZone , $object = null) {}

/**
 * Alias: DateTimeImmutable::createFromFormat
 * @link http://www.php.net/manual/en/function.date-create-immutable-from-format.php
 * @param mixed $format
 * @param mixed $time
 * @param DateTimeZone $object [optional]
 */
function date_create_immutable_from_format ($format, $timeDateTimeZone , $object = null) {}

/**
 * Returns associative array with detailed info about given date
 * @link http://www.php.net/manual/en/function.date-parse.php
 * @param string $date Date in format accepted by strtotime.
 * @return array array with information about the parsed date
 * on success or false on failure.
 */
function date_parse (string $date) {}

/**
 * Get info about given date formatted according to the specified format
 * @link http://www.php.net/manual/en/function.date-parse-from-format.php
 * @param string $format Format accepted by DateTime::createFromFormat.
 * @param string $date String representing the date.
 * @return array associative array with detailed info about given date.
 */
function date_parse_from_format (string $format, string $date) {}

/**
 * Alias: DateTime::getLastErrors
 * @link http://www.php.net/manual/en/function.date-get-last-errors.php
 */
function date_get_last_errors () {}

/**
 * Alias: DateTime::format
 * @link http://www.php.net/manual/en/function.date-format.php
 * @param mixed $object
 * @param mixed $format
 */
function date_format ($object, $format) {}

/**
 * Alias: DateTime::modify
 * @link http://www.php.net/manual/en/function.date-modify.php
 * @param mixed $object
 * @param mixed $modify
 */
function date_modify ($object, $modify) {}

/**
 * Alias: DateTime::add
 * @link http://www.php.net/manual/en/function.date-add.php
 * @param mixed $object
 * @param mixed $interval
 */
function date_add ($object, $interval) {}

/**
 * Alias: DateTime::sub
 * @link http://www.php.net/manual/en/function.date-sub.php
 * @param mixed $object
 * @param mixed $interval
 */
function date_sub ($object, $interval) {}

/**
 * Alias: DateTime::getTimezone
 * @link http://www.php.net/manual/en/function.date-timezone-get.php
 * @param mixed $object
 */
function date_timezone_get ($object) {}

/**
 * Alias: DateTime::setTimezone
 * @link http://www.php.net/manual/en/function.date-timezone-set.php
 * @param mixed $object
 * @param mixed $timezone
 */
function date_timezone_set ($object, $timezone) {}

/**
 * Alias: DateTime::getOffset
 * @link http://www.php.net/manual/en/function.date-offset-get.php
 * @param mixed $object
 */
function date_offset_get ($object) {}

/**
 * Alias: DateTime::diff
 * @link http://www.php.net/manual/en/function.date-diff.php
 * @param mixed $object
 * @param mixed $object2
 * @param mixed $absolute [optional]
 */
function date_diff ($object, $object2, $absolute = null) {}

/**
 * Alias: DateTime::setTime
 * @link http://www.php.net/manual/en/function.date-time-set.php
 * @param mixed $object
 * @param mixed $hour
 * @param mixed $minute
 * @param mixed $second [optional]
 * @param mixed $microseconds [optional]
 */
function date_time_set ($object, $hour, $minute, $second = null, $microseconds = null) {}

/**
 * Alias: DateTime::setDate
 * @link http://www.php.net/manual/en/function.date-date-set.php
 * @param mixed $object
 * @param mixed $year
 * @param mixed $month
 * @param mixed $day
 */
function date_date_set ($object, $year, $month, $day) {}

/**
 * Alias: DateTime::setISODate
 * @link http://www.php.net/manual/en/function.date-isodate-set.php
 * @param mixed $object
 * @param mixed $year
 * @param mixed $week
 * @param mixed $day [optional]
 */
function date_isodate_set ($object, $year, $week, $day = null) {}

/**
 * Alias: DateTime::setTimestamp
 * @link http://www.php.net/manual/en/function.date-timestamp-set.php
 * @param mixed $object
 * @param mixed $unixtimestamp
 */
function date_timestamp_set ($object, $unixtimestamp) {}

/**
 * Alias: DateTime::getTimestamp
 * @link http://www.php.net/manual/en/function.date-timestamp-get.php
 * @param mixed $object
 */
function date_timestamp_get ($object) {}

/**
 * Alias: DateTimeZone::__construct
 * @link http://www.php.net/manual/en/function.timezone-open.php
 * @param mixed $timezone
 */
function timezone_open ($timezone) {}

/**
 * Alias: DateTimeZone::getName
 * @link http://www.php.net/manual/en/function.timezone-name-get.php
 * @param mixed $object
 */
function timezone_name_get ($object) {}

/**
 * Returns the timezone name from abbreviation
 * @link http://www.php.net/manual/en/function.timezone-name-from-abbr.php
 * @param string $abbr Time zone abbreviation.
 * @param int $gmtOffset [optional] Offset from GMT in seconds. Defaults to -1 which means that first found
 * time zone corresponding to abbr is returned.
 * Otherwise exact offset is searched and only if not found then the first
 * time zone with any offset is returned.
 * @param int $isdst [optional] Daylight saving time indicator. Defaults to -1, which means that
 * whether the time zone has daylight saving or not is not taken into
 * consideration when searching. If this is set to 1, then the
 * gmtOffset is assumed to be an offset with
 * daylight saving in effect; if 0, then gmtOffset
 * is assumed to be an offset without daylight saving in effect. If
 * abbr doesn't exist then the time zone is
 * searched solely by the gmtOffset and
 * isdst.
 * @return string time zone name on success or false on failure.
 */
function timezone_name_from_abbr (string $abbr, int $gmtOffset = null, int $isdst = null) {}

/**
 * Alias: DateTimeZone::getOffset
 * @link http://www.php.net/manual/en/function.timezone-offset-get.php
 * @param DateTimeZone $object
 * @param DateTimeInterface $datetime
 */
function timezone_offset_get (DateTimeZone $objectDateTimeInterface , $datetime) {}

/**
 * Alias: DateTimeZone::getTransitions
 * @link http://www.php.net/manual/en/function.timezone-transitions-get.php
 * @param mixed $object
 * @param mixed $timestamp_begin [optional]
 * @param mixed $timestamp_end [optional]
 */
function timezone_transitions_get ($object, $timestamp_begin = null, $timestamp_end = null) {}

/**
 * Alias: DateTimeZone::getLocation
 * @link http://www.php.net/manual/en/function.timezone-location-get.php
 * @param mixed $object
 */
function timezone_location_get ($object) {}

/**
 * Alias: DateTimeZone::listIdentifiers
 * @link http://www.php.net/manual/en/function.timezone-identifiers-list.php
 * @param mixed $what [optional]
 * @param mixed $country [optional]
 */
function timezone_identifiers_list ($what = null, $country = null) {}

/**
 * Alias: DateTimeZone::listAbbreviations
 * @link http://www.php.net/manual/en/function.timezone-abbreviations-list.php
 */
function timezone_abbreviations_list () {}

/**
 * Gets the version of the timezonedb
 * @link http://www.php.net/manual/en/function.timezone-version-get.php
 * @return string a string.
 */
function timezone_version_get () {}

/**
 * Alias: DateInterval::createFromDateString
 * @link http://www.php.net/manual/en/function.date-interval-create-from-date-string.php
 * @param mixed $time
 */
function date_interval_create_from_date_string ($time) {}

/**
 * Alias: DateInterval::format
 * @link http://www.php.net/manual/en/function.date-interval-format.php
 * @param mixed $object
 * @param mixed $format
 */
function date_interval_format ($object, $format) {}

/**
 * Sets the default timezone used by all date/time functions in a script
 * @link http://www.php.net/manual/en/function.date-default-timezone-set.php
 * @param string $timezone_identifier The timezone identifier, like UTC or
 * Europe/Lisbon. The list of valid identifiers is
 * available in the .
 * @return bool This function returns false if the
 * timezone_identifier isn't valid, or true
 * otherwise.
 */
function date_default_timezone_set (string $timezone_identifier) {}

/**
 * Gets the default timezone used by all date/time functions in a script
 * @link http://www.php.net/manual/en/function.date-default-timezone-get.php
 * @return string a string.
 */
function date_default_timezone_get () {}

/**
 * Returns time of sunrise for a given day and location
 * @link http://www.php.net/manual/en/function.date-sunrise.php
 * @param int $timestamp The timestamp of the day from which the sunrise
 * time is taken.
 * @param int $format [optional] <table>
 * format constants
 * <table>
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
 * </table>
 * @param float $latitude [optional] Defaults to North, pass in a negative value for South.
 * See also: date.default_latitude
 * @param float $longitude [optional] Defaults to East, pass in a negative value for West.
 * See also: date.default_longitude
 * @param float $zenith [optional] zenith is the angle between the center of the sun
 * and a line perpendicular to earth's surface. It defaults to
 * date.sunrise_zenith
 * <table>
 * Common zenith angles
 * <table>
 * <tr valign="top">
 * <td>Angle</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>90°50'</td>
 * <td>Sunrise: the point where the sun becomes visible.</td>
 * </tr>
 * <tr valign="top">
 * <td>96°</td>
 * <td>Civil twilight: conventionally used to signify the start of dawn.</td>
 * </tr>
 * <tr valign="top">
 * <td>102°</td>
 * <td>Nautical twilight: the point at which the horizon starts being visible at sea.</td>
 * </tr>
 * <tr valign="top">
 * <td>108°</td>
 * <td>Astronomical twilight: the point at which the sun starts being the source of any illumination.</td>
 * </tr>
 * </table>
 * </table>
 * @param float $gmt_offset [optional] 
 * @return mixed the sunrise time in a specified format on
 * success or false on failure. One potential reason for failure is that the
 * sun does not rise at all, which happens inside the polar circles for part of
 * the year.
 */
function date_sunrise (int $timestamp, int $format = null, float $latitude = null, float $longitude = null, float $zenith = null, float $gmt_offset = null) {}

/**
 * Returns time of sunset for a given day and location
 * @link http://www.php.net/manual/en/function.date-sunset.php
 * @param int $timestamp The timestamp of the day from which the sunset
 * time is taken.
 * @param int $format [optional] <table>
 * format constants
 * <table>
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
 * </table>
 * @param float $latitude [optional] Defaults to North, pass in a negative value for South.
 * See also: date.default_latitude
 * @param float $longitude [optional] Defaults to East, pass in a negative value for West.
 * See also: date.default_longitude
 * @param float $zenith [optional] zenith is the angle between the center of the sun
 * and a line perpendicular to earth's surface. It defaults to
 * date.sunset_zenith
 * <table>
 * Common zenith angles
 * <table>
 * <tr valign="top">
 * <td>Angle</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>90°50'</td>
 * <td>Sunset: the point where the sun becomes invisible.</td>
 * </tr>
 * <tr valign="top">
 * <td>96°</td>
 * <td>Civil twilight: conventionally used to signify the end of dusk.</td>
 * </tr>
 * <tr valign="top">
 * <td>102°</td>
 * <td>Nautical twilight: the point at which the horizon ends being visible at sea.</td>
 * </tr>
 * <tr valign="top">
 * <td>108°</td>
 * <td>Astronomical twilight: the point at which the sun ends being the source of any illumination.</td>
 * </tr>
 * </table>
 * </table>
 * @param float $gmt_offset [optional] 
 * @return mixed the sunset time in a specified format on
 * success or false on failure. One potential reason for failure is that the
 * sun does not set at all, which happens inside the polar circles for part of
 * the year.
 */
function date_sunset (int $timestamp, int $format = null, float $latitude = null, float $longitude = null, float $zenith = null, float $gmt_offset = null) {}

/**
 * Returns an array with information about sunset/sunrise and twilight begin/end
 * @link http://www.php.net/manual/en/function.date-sun-info.php
 * @param int $time Timestamp.
 * @param float $latitude Latitude in degrees.
 * @param float $longitude Longitude in degrees.
 * @return array array on success or false on failure.
 * The structure of the array is detailed in the following list:
 * <p>
 * <p>
 * sunrise
 * <br>
 * The time of the sunrise (zenith angle = 90°35').
 * sunset
 * <br>
 * The time of the sunset (zenith angle = 90°35').
 * transit
 * <br>
 * The time when the sun is at its zenith, i.e. has reached its topmost
 * point.
 * civil_twilight_begin
 * <br>
 * The start of the civil dawn (zenith angle = 96°). It ends at sunrise.
 * civil_twilight_end
 * <br>
 * The end of the civil dusk (zenith angle = 96°). It starts at sunset.
 * nautical_twilight_begin
 * <br>
 * The start of the nautical dawn (zenith angle = 102°). It ends at
 * civil_twilight_begin.
 * nautical_twilight_end
 * <br>
 * The end of the nautical dusk (zenith angle = 102°). It starts at
 * civil_twilight_end.
 * astronomical_twilight_begin
 * <br>
 * The start of the astronomical dawn (zenith angle = 108°). It ends at
 * nautical_twilight_begin.
 * astronomical_twilight_end
 * <br>
 * The end of the astronomical dusk (zenith angle = 108°). It starts at
 * nautical_twilight_end.
 * </p>
 * </p>
 * <p> 
 * The values of the array elements are either UNIX timestamps, false if the
 * sun is below the respective zenith for the whole day, or true if the sun is
 * above the respective zenith for the whole day.
 * </p>
 */
function date_sun_info (int $time, float $latitude, float $longitude) {}

define ('DATE_ATOM', "Y-m-d\TH:i:sP");
define ('DATE_COOKIE', "l, d-M-Y H:i:s T");
define ('DATE_ISO8601', "Y-m-d\TH:i:sO");
define ('DATE_RFC822', "D, d M y H:i:s O");
define ('DATE_RFC850', "l, d-M-y H:i:s T");
define ('DATE_RFC1036', "D, d M y H:i:s O");
define ('DATE_RFC1123', "D, d M Y H:i:s O");
define ('DATE_RFC7231', "D, d M Y H:i:s \G\M\T");
define ('DATE_RFC2822', "D, d M Y H:i:s O");
define ('DATE_RFC3339', "Y-m-d\TH:i:sP");
define ('DATE_RFC3339_EXTENDED', "Y-m-d\TH:i:s.vP");
define ('DATE_RSS', "D, d M Y H:i:s O");
define ('DATE_W3C', "Y-m-d\TH:i:sP");

/**
 * Timestamp
 * @link http://www.php.net/manual/en/datetime.constants.php
 */
define ('SUNFUNCS_RET_TIMESTAMP', 0);

/**
 * Hours:minutes (example: 08:02)
 * @link http://www.php.net/manual/en/datetime.constants.php
 */
define ('SUNFUNCS_RET_STRING', 1);

/**
 * Hours as floating point number (example 8.75)
 * @link http://www.php.net/manual/en/datetime.constants.php
 */
define ('SUNFUNCS_RET_DOUBLE', 2);

// End of date v.7.3.0
