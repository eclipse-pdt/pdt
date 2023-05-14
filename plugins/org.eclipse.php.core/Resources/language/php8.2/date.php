<?php

// Start of date v.8.2.6

/**
 * DateTimeInterface was created
 * so that parameter, return, or property type declarations may accept either 
 * DateTimeImmutable or DateTime
 * as a value. It is not possible to
 * implement this interface with userland classes.
 * <p>Common constants that allow for formatting
 * DateTimeImmutable or
 * DateTime objects through
 * DateTimeImmutable::format and
 * DateTime::format are also defined on this
 * interface.</p>
 * @link http://www.php.net/manual/en/class.datetimeinterface.php
 */
interface DateTimeInterface  {
	const ATOM = "Y-m-d\TH:i:sP";
	const COOKIE = "l, d-M-Y H:i:s T";
	const ISO8601 = "Y-m-d\TH:i:sO";
	const ISO8601_EXPANDED = "X-m-d\TH:i:sP";
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
	 * Returns date formatted according to given format
	 * @link http://www.php.net/manual/en/datetime.format.php
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
	 * <td>ISO 8601 numeric representation of the day of the week</td>
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
	 * <td>ISO 8601 week number of year, weeks starting on Monday</td>
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
	 * <td>ISO 8601 week-numbering year. This has the same value as
	 * Y, except that if the ISO week number
	 * (W) belongs to the previous or next year, that year
	 * is used instead.</td>
	 * <td>Examples: 1999 or 2003</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>X</td>
	 * <td>An expanded full numeric representation of a year, at least 4 digits,
	 * with - for years BCE, and +
	 * for years CE.</td>
	 * <td>Examples: -0055, +0787,
	 * +1999, +10191</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>x</td>
	 * <td>An expanded full numeric representation if requried, or a
	 * standard full numeral representation if possible (like
	 * Y). At least four digits. Years BCE are prefixed
	 * with a -. Years beyond (and including)
	 * 10000 are prefixed by a
	 * +.</td>
	 * <td>Examples: -0055, 0787,
	 * 1999, +10191</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>Y</td>
	 * <td>A full numeric representation of a year, at least 4 digits,
	 * with - for years BCE.</td>
	 * <td>Examples: -0055, 0787,
	 * 1999, 2003,
	 * 10191</td>
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
	 * <td>Seconds with leading zeros</td>
	 * <td>00 through 59</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>u</td>
	 * <td>
	 * Microseconds. Note that
	 * date will always generate
	 * 000000 since it takes an int
	 * parameter, whereas DateTime::format does
	 * support microseconds if DateTime was
	 * created with microseconds.
	 * </td>
	 * <td>Example: 654321</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>v</td>
	 * <td>
	 * Milliseconds. Same note applies as for
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
	 * <td>Timezone identifier</td>
	 * <td>Examples: UTC, GMT, Atlantic/Azores</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>I (capital i)</td>
	 * <td>Whether or not the date is in daylight saving time</td>
	 * <td>1 if Daylight Saving Time, 0 otherwise.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>O</td>
	 * <td>Difference to Greenwich time (GMT) without colon between hours and minutes</td>
	 * <td>Example: +0200</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>P</td>
	 * <td>Difference to Greenwich time (GMT) with colon between hours and minutes</td>
	 * <td>Example: +02:00</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>p</td>
	 * <td>
	 * The same as P, but returns Z instead of +00:00
	 * (available as of PHP 8.0.0)
	 * </td>
	 * <td>Examples: Z or +02:00</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>T</td>
	 * <td>Timezone abbreviation, if known; otherwise the GMT offset.</td>
	 * <td>Examples: EST, MDT, +05</td>
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
	 * <td>ISO 8601 date</td>
	 * <td>2004-02-12T15:19:21+00:00</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>r</td>
	 * <td>RFC 2822/RFC 5322 formatted date</td>
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
	 * Since this function only accepts int timestamps the
	 * u format character is only useful when using the
	 * date_format function with user based timestamps
	 * created with date_create.
	 * </p>
	 * @return string the formatted date string on success.
	 */
	abstract public function format (string $format)

	/**
	 * Return time zone relative to given DateTime
	 * @link http://www.php.net/manual/en/datetime.gettimezone.php
	 * @return mixed a DateTimeZone object on success
	 * or false on failure.
	 */
	abstract public function getTimezone ()

	/**
	 * Returns the timezone offset
	 * @link http://www.php.net/manual/en/datetime.getoffset.php
	 * @return int the timezone offset in seconds from UTC on success.
	 */
	abstract public function getOffset ()

	/**
	 * Gets the Unix timestamp
	 * @link http://www.php.net/manual/en/datetime.gettimestamp.php
	 * @return int the Unix timestamp representing the date.
	 */
	abstract public function getTimestamp ()

	/**
	 * Returns the difference between two DateTime objects
	 * @link http://www.php.net/manual/en/datetime.diff.php
	 * @param DateTimeInterface $targetObject 
	 * @param bool $absolute [optional] Should the interval be forced to be positive?
	 * @return DateInterval The DateInterval object represents the
	 * difference between the two dates.
	 * <p>
	 * The return value more specifically represents the clock-time interval to
	 * apply to the original object ($this or
	 * $originObject) to arrive at the
	 * $targetObject. This process is not always
	 * reversible.
	 * </p>
	 * <p>
	 * The method is aware of DST changeovers, and hence can return an interval of
	 * 24 hours and 30 minutes, as per one of the examples. If
	 * you want to calculate with absolute time, you need to convert both the
	 * $this/$baseObject, and
	 * $targetObject to UTC first.
	 * </p>
	 */
	abstract public function diff ($targetObject, bool $absolute = null)

	abstract public function __wakeup ()

	abstract public function __serialize (): array

	/**
	 * @param array[] $data
	 */
	abstract public function __unserialize (array $data): void

}

/**
 * Representation of date and time.
 * <p>This class behaves the same as DateTimeImmutable
 * except objects are modified itself when modification methods such as
 * DateTime::modify are called.</p>
 * <p>Calling methods on objects of the class DateTime
 * will change the information encapsulated in these objects, if you want to
 * prevent that you will have to use clone operator to
 * create a new object. Use DateTimeImmutable
 * instead of DateTime to obtain this recommended
 * behaviour by default.</p>
 * @link http://www.php.net/manual/en/class.datetime.php
 */
class DateTime implements DateTimeInterface {
	const ATOM = "Y-m-d\TH:i:sP";
	const COOKIE = "l, d-M-Y H:i:s T";
	const ISO8601 = "Y-m-d\TH:i:sO";
	const ISO8601_EXPANDED = "X-m-d\TH:i:sP";
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
	 * @param string $datetime [optional]
	 * @param ?DateTimeZone|null $timezone [optional]
	 */
	public function __construct (string $datetime = 'now'?DateTimeZone|null , $timezone = null) {}

	public function __serialize (): array {}

	/**
	 * @param array[] $data
	 */
	public function __unserialize (array $data): void {}

	/**
	 * The __wakeup handler
	 * @link http://www.php.net/manual/en/datetime.wakeup.php
	 * @return void Initializes a DateTime object.
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
	 * Returns new DateTime instance encapsulating the given DateTimeImmutable object
	 * @link http://www.php.net/manual/en/datetime.createfromimmutable.php
	 * @param DateTimeImmutable $object The immutable DateTimeImmutable object that needs
	 * to be converted to a mutable version. This object is not modified, but
	 * instead a new DateTime instance is
	 * created containing the same date, time, and timezone information.
	 * @return static a new DateTime instance.
	 */
	public static function createFromImmutable (DateTimeImmutable $object) {}

	/**
	 * Returns new DateTime object encapsulating the given DateTimeInterface object
	 * @link http://www.php.net/manual/en/datetime.createfrominterface.php
	 * @param DateTimeInterface $object The DateTimeInterface object that needs
	 * to be converted to a mutable version. This object is not modified, but
	 * instead a new DateTime object is
	 * created containing the same date, time, and timezone information.
	 * @return DateTime a new DateTime instance.
	 */
	public static function createFromInterface ($object): DateTime {}

	/**
	 * Parses a time string according to a specified format
	 * @link http://www.php.net/manual/en/datetime.createfromformat.php
	 * @param string $format 
	 * @param string $datetime 
	 * @param mixed $timezone [optional] 
	 * @return mixed a new DateTime instance or false on failure.
	 */
	public static function createFromFormat (string $format, string $datetime, $timezone = null) {}

	/**
	 * Alias: DateTimeImmutable::getLastErrors
	 * @link http://www.php.net/manual/en/datetime.getlasterrors.php
	 */
	public static function getLastErrors () {}

	/**
	 * @param string $format
	 */
	public function format (string $format) {}

	/**
	 * Alters the timestamp
	 * @link http://www.php.net/manual/en/datetime.modify.php
	 * @param string $modifier A date/time string. date.formats
	 * @return mixed the modified DateTime object for method chainingreturn.falseforfailure.
	 */
	public function modify (string $modifier) {}

	/**
	 * Modifies a DateTime object, with added amount of days, months, years, hours, minutes and seconds
	 * @link http://www.php.net/manual/en/datetime.add.php
	 * @param DateInterval $interval A DateInterval object
	 * @return DateTime the modified DateTime object for method chaining.
	 */
	public function add (DateInterval $interval) {}

	/**
	 * Subtracts an amount of days, months, years, hours, minutes and seconds from
	 * a DateTime object
	 * @link http://www.php.net/manual/en/datetime.sub.php
	 * @param DateInterval $interval A DateInterval object
	 * @return DateTime the modified DateTime object for method chaining.
	 */
	public function sub (DateInterval $interval) {}

	public function getTimezone () {}

	/**
	 * Sets the time zone for the DateTime object
	 * @link http://www.php.net/manual/en/datetime.settimezone.php
	 * @param DateTimeZone $timezone A DateTimeZone object representing the
	 * desired time zone.
	 * @return DateTime the DateTime object for method chaining. The
	 * underlaying point-in-time is not changed when calling this method.
	 */
	public function setTimezone (DateTimeZone $timezone) {}

	public function getOffset () {}

	/**
	 * Sets the time
	 * @link http://www.php.net/manual/en/datetime.settime.php
	 * @param int $hour Hour of the time.
	 * @param int $minute Minute of the time.
	 * @param int $second [optional] Second of the time.
	 * @param int $microsecond [optional] Microsecond of the time.
	 * @return DateTime the modified DateTime object for method chaining.
	 */
	public function setTime (int $hour, int $minute, int $second = null, int $microsecond = null) {}

	/**
	 * Sets the date
	 * @link http://www.php.net/manual/en/datetime.setdate.php
	 * @param int $year Year of the date.
	 * @param int $month Month of the date.
	 * @param int $day Day of the date.
	 * @return DateTime the modified DateTime object for method chaining.
	 */
	public function setDate (int $year, int $month, int $day) {}

	/**
	 * Sets the ISO date
	 * @link http://www.php.net/manual/en/datetime.setisodate.php
	 * @param int $year Year of the date.
	 * @param int $week Week of the date.
	 * @param int $dayOfWeek [optional] Offset from the first day of the week.
	 * @return DateTime the modified DateTime object for method chaining.
	 */
	public function setISODate (int $year, int $week, int $dayOfWeek = null) {}

	/**
	 * Sets the date and time based on an Unix timestamp
	 * @link http://www.php.net/manual/en/datetime.settimestamp.php
	 * @param int $timestamp Unix timestamp representing the date.
	 * Setting timestamps outside the range of integer is possible by using
	 * DateTimeImmutable::modify with the @ format.
	 * @return DateTime the modified DateTime object for method chaining.
	 */
	public function setTimestamp (int $timestamp) {}

	public function getTimestamp () {}

	/**
	 * @param DateTimeInterface $targetObject
	 * @param bool $absolute [optional]
	 */
	public function diff (DateTimeInterface $targetObjectbool , $absolute = '') {}

}

/**
 * Representation of date and time.
 * <p>This class behaves the same as DateTime
 * except new objects are returned when modification methods such as
 * DateTime::modify are called.</p>
 * @link http://www.php.net/manual/en/class.datetimeimmutable.php
 */
class DateTimeImmutable implements DateTimeInterface {
	const ATOM = "Y-m-d\TH:i:sP";
	const COOKIE = "l, d-M-Y H:i:s T";
	const ISO8601 = "Y-m-d\TH:i:sO";
	const ISO8601_EXPANDED = "X-m-d\TH:i:sP";
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
	 * @param string $datetime [optional] <p>A date/time string. date.formats</p>
	 * <p>
	 * Enter "now" here to obtain the current time when using
	 * the $timezone parameter.
	 * </p>
	 * @param mixed $timezone [optional] <p>
	 * A DateTimeZone object representing the
	 * timezone of $datetime.
	 * </p>
	 * <p>
	 * If $timezone is omitted or null,
	 * the current timezone will be used.
	 * </p>
	 * <p>
	 * The $timezone parameter
	 * and the current timezone are ignored when the
	 * $datetime parameter either
	 * is a UNIX timestamp (e.g. @946684800)
	 * or specifies a timezone
	 * (e.g. 2010-01-28T15:00:00+02:00, or
	 * 2010-07-05T06:00:00Z).
	 * </p>
	 * @return mixed a new DateTimeImmutable instance.
	 * style.procedural returns false on failure.
	 */
	public function __construct (string $datetime = null, $timezone = null) {}

	public function __serialize (): array {}

	/**
	 * @param array[] $data
	 */
	public function __unserialize (array $data): void {}

	public function __wakeup () {}

	/**
	 * The __set_state handler
	 * @link http://www.php.net/manual/en/datetimeimmutable.set-state.php
	 * @param array $array Initialization array.
	 * @return DateTimeImmutable a new instance of a DateTimeImmutable object.
	 */
	public static function __set_state (array $array) {}

	/**
	 * Parses a time string according to a specified format
	 * @link http://www.php.net/manual/en/datetimeimmutable.createfromformat.php
	 * @param string $format 
	 * @param string $datetime 
	 * @param mixed $timezone [optional] 
	 * @return mixed a new DateTimeImmutable instance or false on failure.
	 */
	public static function createFromFormat (string $format, string $datetime, $timezone = null) {}

	/**
	 * Returns the warnings and errors
	 * @link http://www.php.net/manual/en/datetimeimmutable.getlasterrors.php
	 * @return mixed array containing info about warnings and errors, or false if there
	 * are neither warnings nor errors.
	 */
	public static function getLastErrors () {}

	/**
	 * @param string $format
	 */
	public function format (string $format) {}

	public function getTimezone () {}

	public function getOffset () {}

	public function getTimestamp () {}

	/**
	 * @param DateTimeInterface $targetObject
	 * @param bool $absolute [optional]
	 */
	public function diff (DateTimeInterface $targetObjectbool , $absolute = '') {}

	/**
	 * Creates a new object with modified timestamp
	 * @link http://www.php.net/manual/en/datetimeimmutable.modify.php
	 * @param string $modifier A date/time string. date.formats
	 * @return mixed a new modified DateTimeImmutable object or false on failure.
	 */
	public function modify (string $modifier) {}

	/**
	 * Returns a new object, with added amount of days, months, years, hours, minutes and seconds
	 * @link http://www.php.net/manual/en/datetimeimmutable.add.php
	 * @param DateInterval $interval A DateInterval object
	 * @return DateTimeImmutable a new DateTimeImmutable object with the modified data.
	 */
	public function add (DateInterval $interval) {}

	/**
	 * Subtracts an amount of days, months, years, hours, minutes and seconds
	 * @link http://www.php.net/manual/en/datetimeimmutable.sub.php
	 * @param DateInterval $interval A DateInterval object
	 * @return DateTimeImmutable a new DateTimeImmutable object with the modified data.
	 */
	public function sub (DateInterval $interval) {}

	/**
	 * Sets the time zone
	 * @link http://www.php.net/manual/en/datetimeimmutable.settimezone.php
	 * @param DateTimeZone $timezone A DateTimeZone object representing the
	 * desired time zone.
	 * @return DateTimeImmutable a new modified DateTimeImmutable object for
	 * method chaining. The underlaying point-in-time is not changed when calling
	 * this method.
	 */
	public function setTimezone (DateTimeZone $timezone) {}

	/**
	 * Sets the time
	 * @link http://www.php.net/manual/en/datetimeimmutable.settime.php
	 * @param int $hour Hour of the time.
	 * @param int $minute Minute of the time.
	 * @param int $second [optional] Second of the time.
	 * @param int $microsecond [optional] Microsecond of the time.
	 * @return DateTimeImmutable a new DateTimeImmutable object with the modified data.
	 */
	public function setTime (int $hour, int $minute, int $second = null, int $microsecond = null) {}

	/**
	 * Sets the date
	 * @link http://www.php.net/manual/en/datetimeimmutable.setdate.php
	 * @param int $year Year of the date.
	 * @param int $month Month of the date.
	 * @param int $day Day of the date.
	 * @return DateTimeImmutable a new DateTimeImmutable object with the modified data.
	 */
	public function setDate (int $year, int $month, int $day) {}

	/**
	 * Sets the ISO date
	 * @link http://www.php.net/manual/en/datetimeimmutable.setisodate.php
	 * @param int $year Year of the date.
	 * @param int $week Week of the date.
	 * @param int $dayOfWeek [optional] Offset from the first day of the week.
	 * @return DateTimeImmutable a new DateTimeImmutable object with the modified data.
	 */
	public function setISODate (int $year, int $week, int $dayOfWeek = null) {}

	/**
	 * Sets the date and time based on a Unix timestamp
	 * @link http://www.php.net/manual/en/datetimeimmutable.settimestamp.php
	 * @param int $timestamp Unix timestamp representing the date.
	 * Setting timestamps outside the range of integer is possible by using
	 * DateTimeImmutable::modify with the @ format.
	 * @return DateTimeImmutable a new DateTimeImmutable object with the modified data.
	 */
	public function setTimestamp (int $timestamp) {}

	/**
	 * Returns new DateTimeImmutable instance encapsulating the given DateTime object
	 * @link http://www.php.net/manual/en/datetimeimmutable.createfrommutable.php
	 * @param DateTime $object The mutable DateTime object that you want to
	 * convert to an immutable version. This object is not modified, but
	 * instead a new DateTimeImmutable instance is
	 * created containing the same date time and timezone information.
	 * @return static a new DateTimeImmutable instance.
	 */
	public static function createFromMutable (DateTime $object) {}

	/**
	 * Returns new DateTimeImmutable object encapsulating the given DateTimeInterface object
	 * @link http://www.php.net/manual/en/datetimeimmutable.createfrominterface.php
	 * @param DateTimeInterface $object The DateTimeInterface object that needs
	 * to be converted to an immutable version. This object is not modified, but
	 * instead a new DateTimeImmutable object is
	 * created containing the same date, time, and timezone information.
	 * @return DateTimeImmutable a new DateTimeImmutable instance.
	 */
	public static function createFromInterface ($object): DateTimeImmutable {}

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
	 * @param string $timezone One of the supported timezone names,
	 * an offset value (+0200), or a timezone abbreviation (BST).
	 * @return mixed DateTimeZone on success.
	 * style.procedural returns false on failure.
	 */
	public function __construct (string $timezone) {}

	/**
	 * Returns the name of the timezone
	 * @link http://www.php.net/manual/en/datetimezone.getname.php
	 * @return string Depending on zone type, UTC offset (type 1), timezone abbreviation (type
	 * 2), and timezone identifiers as published in the IANA timezone database
	 * (type 3), the descriptor string to create a new
	 * DateTimeZone object with the same offset and/or
	 * rules. For example 02:00, CEST, or
	 * one of the timezone names in the <p>.
	 */
	public function getName () {}

	/**
	 * Returns the timezone offset from GMT
	 * @link http://www.php.net/manual/en/datetimezone.getoffset.php
	 * @param DateTimeInterface $datetime DateTime that contains the date/time to compute the offset from.
	 * @return int time zone offset in seconds.
	 */
	public function getOffset ($datetime) {}

	/**
	 * Returns all transitions for the timezone
	 * @link http://www.php.net/manual/en/datetimezone.gettransitions.php
	 * @param int $timestampBegin [optional] Begin timestamp.
	 * @param int $timestampEnd [optional] End timestamp.
	 * @return mixed a numerically indexed array of
	 * transition arrays on success, or false on failure. DateTimeZone
	 * objects wrapping type 1 (UTC offsets) and type 2 (abbreviations) do not
	 * contain any transitions, and calling this method on them will return
	 * false.
	 * <p>
	 * If timestampBegin is given, the first entry in the
	 * returned array will contain a transition element at the time of
	 * timestampBegin.
	 * </p>
	 * <p>
	 * <table>
	 * Transition Array Structure
	 * <table>
	 * <tr valign="top">
	 * <td>Key</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>ts</td>
	 * <td>int</td>
	 * <td>Unix timestamp</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>time</td>
	 * <td>string</td>
	 * <td>DateTimeInterface::ISO8601_EXPANDED (PHP
	 * 8.2 and later), or DateTimeInterface::ISO8601 (PHP
	 * 8.1 and lower) time string</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>offset</td>
	 * <td>int</td>
	 * <td>Offset to UTC in seconds</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>isdst</td>
	 * <td>bool</td>
	 * <td>Whether daylight saving time is active</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>abbr</td>
	 * <td>string</td>
	 * <td>Timezone abbreviation</td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 */
	public function getTransitions (int $timestampBegin = null, int $timestampEnd = null) {}

	/**
	 * Returns location information for a timezone
	 * @link http://www.php.net/manual/en/datetimezone.getlocation.php
	 * @return mixed Array containing location information about timezone or false on failure.
	 */
	public function getLocation () {}

	/**
	 * Returns associative array containing dst, offset and the timezone name
	 * @link http://www.php.net/manual/en/datetimezone.listabbreviations.php
	 * @return array the array of timezone abbreviations.
	 */
	public static function listAbbreviations () {}

	/**
	 * Returns a numerically indexed array containing all defined timezone identifiers
	 * @link http://www.php.net/manual/en/datetimezone.listidentifiers.php
	 * @param int $timezoneGroup [optional] One of the DateTimeZone class constants (or a combination).
	 * @param mixed $countryCode [optional] <p>
	 * A two-letter (uppercase) ISO 3166-1 compatible country code.
	 * </p>
	 * This option is only used when timezoneGroup is set to
	 * DateTimeZone::PER_COUNTRY.
	 * @return array the array of timezone identifiers. Only non-outdated items are
	 * returned. To get all, including outdated timezone identifiers, use the
	 * DateTimeZone::ALL_WITH_BC as value for
	 * timezoneGroup.
	 */
	public static function listIdentifiers (int $timezoneGroup = null, $countryCode = null) {}

	public function __serialize (): array {}

	/**
	 * @param array[] $data
	 */
	public function __unserialize (array $data): void {}

	public function __wakeup () {}

	/**
	 * @param array[] $array
	 */
	public static function __set_state (array $array) {}

}

/**
 * Represents a date interval.
 * <p>A date interval stores either a fixed amount of time (in years, months,
 * days, hours etc) or a relative time string in the format that
 * DateTimeImmutable's and
 * DateTime's constructors support.</p>
 * <p>More specifically, the information in an object of the
 * DateInterval class is an instruction to get from
 * one date/time to another date/time. This process is not always reversible.</p>
 * <p>A common way to create a DateInterval object
 * is by calculating the difference between two date/time objects through
 * DateTimeInterface::diff.</p>
 * <p>Since there is no well defined way to compare date intervals,
 * DateInterval instances are
 * incomparable.</p>
 * @link http://www.php.net/manual/en/class.dateinterval.php
 */
class DateInterval  {

	/**
	 * Number of years.
	 * @var int
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.y
	 */
	public $y;

	/**
	 * Number of months.
	 * @var int
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.m
	 */
	public $m;

	/**
	 * Number of days.
	 * @var int
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.d
	 */
	public $d;

	/**
	 * Number of hours.
	 * @var int
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.h
	 */
	public $h;

	/**
	 * Number of minutes.
	 * @var int
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.i
	 */
	public $i;

	/**
	 * Number of seconds.
	 * @var int
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
	 * @var int
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.invert
	 */
	public $invert;

	/**
	 * If the DateInterval object was created by
	 * DateTimeImmutable::diff or
	 * DateTime::diff, then this is the
	 * total number of full days between the start and end dates. Otherwise,
	 * days will be false.
	 * @var mixed
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.days
	 */
	public $days;

	/**
	 * If the DateInterval object was created by
	 * DateInterval::createFromDateString, then
	 * this property's value will be true, and the
	 * date_string property will be populated. Otherwise,
	 * the value will be false, and the y to
	 * f, invert, and
	 * days properties will be populated.
	 * @var bool
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.from_string
	 */
	public $from_string;

	/**
	 * The string used as argument to
	 * DateInterval::createFromDateString.
	 * @var string
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.date_string
	 */
	public $date_string;

	/**
	 * Creates a new DateInterval object
	 * @link http://www.php.net/manual/en/dateinterval.construct.php
	 * @param string $duration
	 */
	public function __construct (string $duration) {}

	/**
	 * Sets up a DateInterval from the relative parts of the string
	 * @link http://www.php.net/manual/en/dateinterval.createfromdatestring.php
	 * @param string $datetime <p>
	 * A date with relative parts. Specifically, the relative formats supported
	 * by the parser used for DateTimeImmutable,
	 * DateTime, and strtotime
	 * will be used to construct the DateInterval.
	 * </p>
	 * <p>
	 * To use an ISO-8601 format string like P7D, you must
	 * use the constructor.
	 * </p>
	 * @return mixed a new DateInterval instance on success, or false on failure.
	 */
	public static function createFromDateString (string $datetime) {}

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

	public function __serialize (): array {}

	/**
	 * @param array[] $data
	 */
	public function __unserialize (array $data): void {}

	public function __wakeup () {}

	/**
	 * @param array[] $array
	 */
	public static function __set_state (array $array) {}

}

/**
 * Represents a date period.
 * <p>A date period allows iteration over a set of dates and times, recurring at
 * regular intervals, over a given period.</p>
 * @link http://www.php.net/manual/en/class.dateperiod.php
 */
class DatePeriod implements IteratorAggregate, Traversable {
	const EXCLUDE_START_DATE = 1;
	const INCLUDE_END_DATE = 2;

	public $start;
	public $current;
	public $end;
	public $interval;
	public $recurrences;
	public $include_start_date;
	public $include_end_date;


	/**
	 * Creates a new DatePeriod object
	 * @link http://www.php.net/manual/en/dateperiod.construct.php
	 * @param mixed $start
	 * @param mixed $interval [optional]
	 * @param mixed $end [optional]
	 * @param mixed $options [optional]
	 */
	public function __construct ($start = null, $interval = null, $end = null, $options = null) {}

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
	 * @return mixed null if the DatePeriod does
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
	 * Returns a cloned DateTime object
	 * representing the end date otherwise.
	 * </p>
	 */
	public function getEndDate () {}

	/**
	 * Gets the interval
	 * @link http://www.php.net/manual/en/dateperiod.getdateinterval.php
	 * @return DateInterval a DateInterval object
	 */
	public function getDateInterval () {}

	/**
	 * Gets the number of recurrences
	 * @link http://www.php.net/manual/en/dateperiod.getrecurrences.php
	 * @return mixed The number of recurrences as set by explicitly passing the
	 * $recurrences to the contructor of the
	 * DatePeriod class, or null otherwise.
	 */
	public function getRecurrences () {}

	public function __serialize (): array {}

	/**
	 * @param array[] $data
	 */
	public function __unserialize (array $data): void {}

	public function __wakeup () {}

	/**
	 * @param array[] $array
	 */
	public static function __set_state (array $array) {}

	public function getIterator (): Iterator {}

}

/**
 * Parse about any English textual datetime description into a Unix timestamp
 * @link http://www.php.net/manual/en/function.strtotime.php
 * @param string $datetime A date/time string. date.formats
 * @param mixed $baseTimestamp [optional] The timestamp which is used as a base for the calculation of relative
 * dates.
 * @return mixed a timestamp on success, false otherwise.
 */
function strtotime (string $datetime, $baseTimestamp = null): int|false {}

/**
 * Format a Unix timestamp
 * @link http://www.php.net/manual/en/function.date.php
 * @param string $format <p>
 * Format accepted by DateTimeInterface::format.
 * </p>
 * date will always generate
 * 000000 as microseconds since it takes an int
 * parameter, whereas DateTime::format does
 * support microseconds if DateTime was
 * created with microseconds.
 * @param mixed $timestamp [optional] 
 * @return string a formatted date string.
 */
function date (string $format, $timestamp = null): string {}

/**
 * Format a local time/date part as integer
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
 * <td>N</td>
 * <td>ISO-8601 day of the week (1 for Monday
 * through 7 for Sunday)</td>
 * </tr>
 * <tr valign="top">
 * <td>o</td>
 * <td>ISO-8601 year (4 digits)</td>
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
 * @param mixed $timestamp [optional] 
 * @return mixed an int on success, or false on failure.
 * <p>
 * As idate always returns an int and
 * as they can't start with a "0", idate may return
 * fewer digits than you would expect. See the example below.
 * </p>
 */
function idate (string $format, $timestamp = null): int|false {}

/**
 * Format a GMT/UTC date/time
 * @link http://www.php.net/manual/en/function.gmdate.php
 * @param string $format The format of the outputted date string. See the formatting
 * options for the date function.
 * @param mixed $timestamp [optional] 
 * @return string a formatted date string.
 */
function gmdate (string $format, $timestamp = null): string {}

/**
 * Get Unix timestamp for a date
 * @link http://www.php.net/manual/en/function.mktime.php
 * @param int $hour The number of the hour relative to the start of the day determined by
 * month, day and year.
 * Negative values reference the hour before midnight of the day in question.
 * Values greater than 23 reference the appropriate hour in the following day(s).
 * @param mixed $minute [optional] The number of the minute relative to the start of the hour.
 * Negative values reference the minute in the previous hour.
 * Values greater than 59 reference the appropriate minute in the following hour(s).
 * @param mixed $second [optional] The number of seconds relative to the start of the minute.
 * Negative values reference the second in the previous minute.
 * Values greater than 59 reference the appropriate second in the following minute(s).
 * @param mixed $month [optional] The number of the month relative to the end of the previous year.
 * Values 1 to 12 reference the normal calendar months of the year in question.
 * Values less than 1 (including negative values) reference the months in the previous year in reverse order, so 0 is December, -1 is November, etc.
 * Values greater than 12 reference the appropriate month in the following year(s).
 * @param mixed $day [optional] The number of the day relative to the end of the previous month.
 * Values 1 to 28, 29, 30 or 31 (depending upon the month) reference the normal days in the relevant month.
 * Values less than 1 (including negative values) reference the days in the previous month, so 0 is the last day of the previous month, -1 is the day before that, etc.
 * Values greater than the number of days in the relevant month reference the appropriate day in the following month(s).
 * @param mixed $year [optional] The number of the year, may be a two or four digit value,
 * with values between 0-69 mapping to 2000-2069 and 70-100 to
 * 1970-2000. On systems where time_t is a 32bit signed integer, as
 * most common today, the valid range for year 
 * is somewhere between 1901 and 2038.
 * @return mixed mktime returns the Unix timestamp of the arguments
 * given.
 */
function mktime (int $hour, $minute = null, $second = null, $month = null, $day = null, $year = null): int|false {}

/**
 * Get Unix timestamp for a GMT date
 * @link http://www.php.net/manual/en/function.gmmktime.php
 * @param int $hour The number of the hour relative to the start of the day determined by
 * month, day and year.
 * Negative values reference the hour before midnight of the day in question.
 * Values greater than 23 reference the appropriate hour in the following day(s).
 * @param mixed $minute [optional] The number of the minute relative to the start of the hour.
 * Negative values reference the minute in the previous hour.
 * Values greater than 59 reference the appropriate minute in the following hour(s).
 * @param mixed $second [optional] The number of seconds relative to the start of the minute.
 * Negative values reference the second in the previous minute.
 * Values greater than 59 reference the appropriate second in the following minute(s).
 * @param mixed $month [optional] The number of the month relative to the end of the previous year.
 * Values 1 to 12 reference the normal calendar months of the year in question.
 * Values less than 1 (including negative values) reference the months in the previous year in reverse order, so 0 is December, -1 is November, etc.
 * Values greater than 12 reference the appropriate month in the following year(s).
 * @param mixed $day [optional] The number of the day relative to the end of the previous month.
 * Values 1 to 28, 29, 30 or 31 (depending upon the month) reference the normal days in the relevant month.
 * Values less than 1 (including negative values) reference the days in the previous month, so 0 is the last day of the previous month, -1 is the day before that, etc.
 * Values greater than the number of days in the relevant month reference the appropriate day in the following month(s).
 * @param mixed $year [optional] The year
 * @return mixed a int Unix timestamp on success, or false on failure.
 */
function gmmktime (int $hour, $minute = null, $second = null, $month = null, $day = null, $year = null): int|false {}

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
function checkdate (int $month, int $day, int $year): bool {}

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
 * <td>Example: AM for 00:31,
 * PM for 22:23. The exact result depends on the
 * Operating System, and they can also return lower-case variants, or
 * variants with dots (such as a.m.).</td>
 * </tr>
 * <tr valign="top">
 * <td>%P</td>
 * <td>lower-case 'am' or 'pm' based on the given time</td>
 * <td>Example: am for 00:31,
 * pm for 22:23. Not supported by all Operating
 * Systems.</td>
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
 * macOS and musl only: The %P modifier 
 * is not supported in the macOS implementation of this function.
 * @param mixed $timestamp [optional] 
 * @return mixed a string formatted according format
 * using the given timestamp or the current
 * local time if no timestamp is given. Month and weekday names and
 * other language-dependent strings respect the current locale set
 * with setlocale.
 * The function returns false if format is empty, contains unsupported
 * conversion specifiers, or if the length of the returned string would be greater than
 * 4095.
 * @deprecated 
 */
function strftime (string $format, $timestamp = null): string|false {}

/**
 * Format a GMT/UTC time/date according to locale settings
 * @link http://www.php.net/manual/en/function.gmstrftime.php
 * @param string $format See description in strftime.
 * @param mixed $timestamp [optional] 
 * @return mixed a string formatted according to the given format string
 * using the given timestamp or the current
 * local time if no timestamp is given. Month and weekday names and
 * other language dependent strings respect the current locale set
 * with setlocale.
 * On failure, false is returned.
 * @deprecated 
 */
function gmstrftime (string $format, $timestamp = null): string|false {}

/**
 * Return current Unix timestamp
 * @link http://www.php.net/manual/en/function.time.php
 * @return int the current timestamp.
 */
function time (): int {}

/**
 * Get the local time
 * @link http://www.php.net/manual/en/function.localtime.php
 * @param mixed $timestamp [optional] 
 * @param bool $associative [optional] Determines whether the function should return a regular, numerically indexed array, 
 * or an associative one.
 * @return array If associative is set to false or not supplied then 
 * the array is returned as a regular, numerically indexed array.
 * If associative is set to true then
 * localtime returns an associative array containing
 * the elements of the structure returned by the C
 * function call to localtime. 
 * The keys of the associative array are as follows:
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
 */
function localtime ($timestamp = null, bool $associative = null): array {}

/**
 * Get date/time information
 * @link http://www.php.net/manual/en/function.getdate.php
 * @param mixed $timestamp [optional] 
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
function getdate ($timestamp = null): array {}

/**
 * create a new DateTime object
 * @link http://www.php.net/manual/en/function.date-create.php
 * @param string $datetime [optional] 
 * @param mixed $timezone [optional] 
 * @return mixed a new DateTime instance.
 * style.procedural returns false on failure.
 */
function date_create (string $datetime = null, $timezone = null): DateTime|false {}

/**
 * create a new DateTimeImmutable object
 * @link http://www.php.net/manual/en/function.date-create-immutable.php
 * @param string $datetime [optional] 
 * @param mixed $timezone [optional] 
 * @return mixed a new DateTimeImmutable instance.
 * style.procedural returns false on failure.
 */
function date_create_immutable (string $datetime = null, $timezone = null): DateTimeImmutable|false {}

/**
 * Alias: DateTime::createFromFormat
 * @link http://www.php.net/manual/en/function.date-create-from-format.php
 * @param string $format
 * @param string $datetime
 * @param ?DateTimeZone|null $timezone [optional]
 */
function date_create_from_format (string $formatstring , $datetime?DateTimeZone|null , $timezone = null): DateTime|false {}

/**
 * Alias: DateTimeImmutable::createFromFormat
 * @link http://www.php.net/manual/en/function.date-create-immutable-from-format.php
 * @param string $format
 * @param string $datetime
 * @param ?DateTimeZone|null $timezone [optional]
 */
function date_create_immutable_from_format (string $formatstring , $datetime?DateTimeZone|null , $timezone = null): DateTimeImmutable|false {}

/**
 * Returns associative array with detailed info about given date/time
 * @link http://www.php.net/manual/en/function.date-parse.php
 * @param string $datetime Date/time in format accepted by
 * DateTimeImmutable::__construct.
 * @return array array with information about the parsed date/time.
 * <p>
 * The returned array has keys for year,
 * month, day, hour,
 * minute, second,
 * fraction, and is_localtime.
 * </p>
 * <p>
 * If is_localtime is present then
 * zone_type indicates the type of timezone. For type
 * 1 (UTC offset) the zone,
 * is_dst fields are added; for type 2
 * (abbreviation) the fields tz_abbr,
 * is_dst are added; and for type 3
 * (timezone identifier) the tz_abbr,
 * tz_id are added.
 * </p>
 * <p>
 * If relative time elements are present in the
 * datetime string such as +3 days,
 * the then returned array includes a nested array with the key
 * relative. This array then contains the keys
 * year, month, day,
 * hour, minute,
 * second, and if necessary weekday, and
 * weekdays, depending on the string that was passed in.
 * </p>
 * <p>
 * The array includes warning_count and
 * warnings fields. The first one indicate how many
 * warnings there were.
 * The keys of elements warnings array indicate the
 * position in the given datetime where the warning
 * occurred, with the string value describing the warning itself.
 * </p>
 * <p>
 * The array also contains error_count and
 * errors fields. The first one indicate how many errors
 * were found.
 * The keys of elements errors array indicate the
 * position in the given datetime where the error
 * occurred, with the string value describing the error itself.
 * </p>
 * <p>
 * The number of array elements in the warnings and
 * errors arrays might be less than
 * warning_count or error_count if they
 * occurred at the same position.
 * </p>
 */
function date_parse (string $datetime): array {}

/**
 * Get info about given date formatted according to the specified format
 * @link http://www.php.net/manual/en/function.date-parse-from-format.php
 * @param string $format Documentation on how the format is used, please
 * refer to the documentation of
 * DateTimeImmutable::createFromFormat. The same
 * rules apply.
 * @param string $datetime String representing the date/time.
 * @return array associative array with detailed info about given date/time.
 * <p>
 * The returned array has keys for year,
 * month, day, hour,
 * minute, second,
 * fraction, and is_localtime.
 * </p>
 * <p>
 * If is_localtime is present then
 * zone_type indicates the type of timezone. For type
 * 1 (UTC offset) the zone,
 * is_dst fields are added; for type 2
 * (abbreviation) the fields tz_abbr,
 * is_dst are added; and for type 3
 * (timezone identifier) the tz_abbr,
 * tz_id are added.
 * </p>
 * <p>
 * The array includes warning_count and
 * warnings fields. The first one indicate how many
 * warnings there were.
 * The keys of elements warnings array indicate the
 * position in the given datetime where the warning
 * occurred, with the string value describing the warning itself. An example
 * below shows such a warning.
 * </p>
 * <p>
 * The array also contains error_count and
 * errors fields. The first one indicate how many errors
 * were found.
 * The keys of elements errors array indicate the
 * position in the given datetime where the error
 * occurred, with the string value describing the error itself. An example
 * below shows such an error.
 * </p>
 * <p>
 * The number of array elements in the warnings and
 * errors arrays might be less than
 * warning_count or error_count if they
 * occurred at the same position.
 * </p>
 */
function date_parse_from_format (string $format, string $datetime): array {}

/**
 * Alias: DateTimeImmutable::getLastErrors
 * @link http://www.php.net/manual/en/function.date-get-last-errors.php
 */
function date_get_last_errors (): array|false {}

/**
 * Alias: DateTime::format
 * @link http://www.php.net/manual/en/function.date-format.php
 * @param DateTimeInterface $object
 * @param string $format
 */
function date_format (DateTimeInterface $objectstring , $format): string {}

/**
 * Alias: DateTime::modify
 * @link http://www.php.net/manual/en/function.date-modify.php
 * @param DateTime $object
 * @param string $modifier
 */
function date_modify (DateTime $objectstring , $modifier): DateTime|false {}

/**
 * Alias: DateTime::add
 * @link http://www.php.net/manual/en/function.date-add.php
 * @param DateTime $object
 * @param DateInterval $interval
 */
function date_add (DateTime $objectDateInterval , $interval): DateTime {}

/**
 * Alias: DateTime::sub
 * @link http://www.php.net/manual/en/function.date-sub.php
 * @param DateTime $object
 * @param DateInterval $interval
 */
function date_sub (DateTime $objectDateInterval , $interval): DateTime {}

/**
 * Alias: DateTime::getTimezone
 * @link http://www.php.net/manual/en/function.date-timezone-get.php
 * @param DateTimeInterface $object
 */
function date_timezone_get (DateTimeInterface $object): DateTimeZone|false {}

/**
 * Alias: DateTime::setTimezone
 * @link http://www.php.net/manual/en/function.date-timezone-set.php
 * @param DateTime $object
 * @param DateTimeZone $timezone
 */
function date_timezone_set (DateTime $objectDateTimeZone , $timezone): DateTime {}

/**
 * Alias: DateTime::getOffset
 * @link http://www.php.net/manual/en/function.date-offset-get.php
 * @param DateTimeInterface $object
 */
function date_offset_get (DateTimeInterface $object): int {}

/**
 * Alias: DateTime::diff
 * @link http://www.php.net/manual/en/function.date-diff.php
 * @param DateTimeInterface $baseObject
 * @param DateTimeInterface $targetObject
 * @param bool $absolute [optional]
 */
function date_diff (DateTimeInterface $baseObjectDateTimeInterface , $targetObjectbool , $absolute = ''): DateInterval {}

/**
 * Alias: DateTime::setTime
 * @link http://www.php.net/manual/en/function.date-time-set.php
 * @param DateTime $object
 * @param int $hour
 * @param int $minute
 * @param int $second [optional]
 * @param int $microsecond [optional]
 */
function date_time_set (DateTime $objectint , $hourint , $minuteint , $second = 0int , $microsecond = 0): DateTime {}

/**
 * Alias: DateTime::setDate
 * @link http://www.php.net/manual/en/function.date-date-set.php
 * @param DateTime $object
 * @param int $year
 * @param int $month
 * @param int $day
 */
function date_date_set (DateTime $objectint , $yearint , $monthint , $day): DateTime {}

/**
 * Alias: DateTime::setISODate
 * @link http://www.php.net/manual/en/function.date-isodate-set.php
 * @param DateTime $object
 * @param int $year
 * @param int $week
 * @param int $dayOfWeek [optional]
 */
function date_isodate_set (DateTime $objectint , $yearint , $weekint , $dayOfWeek = 1): DateTime {}

/**
 * Alias: DateTime::setTimestamp
 * @link http://www.php.net/manual/en/function.date-timestamp-set.php
 * @param DateTime $object
 * @param int $timestamp
 */
function date_timestamp_set (DateTime $objectint , $timestamp): DateTime {}

/**
 * Alias: DateTime::getTimestamp
 * @link http://www.php.net/manual/en/function.date-timestamp-get.php
 * @param DateTimeInterface $object
 */
function date_timestamp_get (DateTimeInterface $object): int {}

/**
 * Alias: DateTimeZone::__construct
 * @link http://www.php.net/manual/en/function.timezone-open.php
 * @param string $timezone
 */
function timezone_open (string $timezone): DateTimeZone|false {}

/**
 * Alias: DateTimeZone::getName
 * @link http://www.php.net/manual/en/function.timezone-name-get.php
 * @param DateTimeZone $object
 */
function timezone_name_get (DateTimeZone $object): string {}

/**
 * Returns a timezone name by guessing from abbreviation and UTC offset
 * @link http://www.php.net/manual/en/function.timezone-name-from-abbr.php
 * @param string $abbr Time zone abbreviation.
 * @param int $utcOffset [optional] Offset from GMT in seconds. Defaults to -1 which means that first found
 * time zone corresponding to abbr is returned.
 * Otherwise exact offset is searched and only if not found then the first
 * time zone with any offset is returned.
 * @param int $isDST [optional] Daylight saving time indicator. Defaults to -1, which means that
 * whether the time zone has daylight saving or not is not taken into
 * consideration when searching. If this is set to 1, then the
 * utcOffset is assumed to be an offset with
 * daylight saving in effect; if 0, then utcOffset
 * is assumed to be an offset without daylight saving in effect. If
 * abbr doesn't exist then the time zone is
 * searched solely by the utcOffset and
 * isDST.
 * @return mixed time zone name on success or false on failure.
 */
function timezone_name_from_abbr (string $abbr, int $utcOffset = null, int $isDST = null): string|false {}

/**
 * Alias: DateTimeZone::getOffset
 * @link http://www.php.net/manual/en/function.timezone-offset-get.php
 * @param DateTimeZone $object
 * @param DateTimeInterface $datetime
 */
function timezone_offset_get (DateTimeZone $objectDateTimeInterface , $datetime): int {}

/**
 * Alias: DateTimeZone::getTransitions
 * @link http://www.php.net/manual/en/function.timezone-transitions-get.php
 * @param DateTimeZone $object
 * @param int $timestampBegin [optional]
 * @param int $timestampEnd [optional]
 */
function timezone_transitions_get (DateTimeZone $objectint , $timestampBegin = -9223372036854775808int , $timestampEnd = 9223372036854775807): array|false {}

/**
 * Alias: DateTimeZone::getLocation
 * @link http://www.php.net/manual/en/function.timezone-location-get.php
 * @param DateTimeZone $object
 */
function timezone_location_get (DateTimeZone $object): array|false {}

/**
 * Alias: DateTimeZone::listIdentifiers
 * @link http://www.php.net/manual/en/function.timezone-identifiers-list.php
 * @param int $timezoneGroup [optional]
 * @param ?string|null $countryCode [optional]
 */
function timezone_identifiers_list (int $timezoneGroup = 2047?string|null , $countryCode = null): array {}

/**
 * Alias: DateTimeZone::listAbbreviations
 * @link http://www.php.net/manual/en/function.timezone-abbreviations-list.php
 */
function timezone_abbreviations_list (): array {}

/**
 * Gets the version of the timezonedb
 * @link http://www.php.net/manual/en/function.timezone-version-get.php
 * @return string a string in the format
 * YYYY.increment, such as 2022.2.
 * <p>
 * If you have a timezone database version that is old (for example, it
 * doesn't show the current year), then you can update the timezone information
 * by either upgrading your PHP version, or installing the timezonedb PECL package.
 * </p>
 * <p>
 * Some Linux distributions patch PHP's date/time support to use an
 * alternative source for timezone information. In which case this function
 * will return 0.system. You are encouraged to install the
 * timezonedb PECL
 * package in that case as well.
 * </p>
 */
function timezone_version_get (): string {}

/**
 * Alias: DateInterval::createFromDateString
 * @link http://www.php.net/manual/en/function.date-interval-create-from-date-string.php
 * @param string $datetime
 */
function date_interval_create_from_date_string (string $datetime): DateInterval|false {}

/**
 * Alias: DateInterval::format
 * @link http://www.php.net/manual/en/function.date-interval-format.php
 * @param DateInterval $object
 * @param string $format
 */
function date_interval_format (DateInterval $objectstring , $format): string {}

/**
 * Sets the default timezone used by all date/time functions in a script
 * @link http://www.php.net/manual/en/function.date-default-timezone-set.php
 * @param string $timezoneId The timezone identifier, like UTC,
 * Africa/Lagos, Asia/Hong_Kong, or
 * Europe/Lisbon. The list of valid identifiers is
 * available in the .
 * @return bool This function returns false if the
 * timezoneId isn't valid, or true
 * otherwise.
 */
function date_default_timezone_set (string $timezoneId): bool {}

/**
 * Gets the default timezone used by all date/time functions in a script
 * @link http://www.php.net/manual/en/function.date-default-timezone-get.php
 * @return string a string.
 */
function date_default_timezone_get (): string {}

/**
 * Returns time of sunrise for a given day and location
 * @link http://www.php.net/manual/en/function.date-sunrise.php
 * @param int $timestamp The timestamp of the day from which the sunrise
 * time is taken.
 * @param int $returnFormat [optional] <table>
 * returnFormat constants
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
 * <td>returns the result as int (timestamp)</td>
 * <td>1095034606</td>
 * </tr>
 * </table>
 * </table>
 * @param mixed $latitude [optional] Defaults to North, pass in a negative value for South.
 * See also: date.default_latitude
 * @param mixed $longitude [optional] Defaults to East, pass in a negative value for West.
 * See also: date.default_longitude
 * @param mixed $zenith [optional] zenith is the angle between the center of the sun
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
 * @param mixed $utcOffset [optional] Specified in hours.
 * The utcOffset is ignored, if
 * returnFormat is
 * SUNFUNCS_RET_TIMESTAMP.
 * @return mixed the sunrise time in a specified returnFormat on
 * success or false on failure. One potential reason for failure is that the
 * sun does not rise at all, which happens inside the polar circles for part of
 * the year.
 * @deprecated 
 */
function date_sunrise (int $timestamp, int $returnFormat = null, $latitude = null, $longitude = null, $zenith = null, $utcOffset = null): string|int|float|false {}

/**
 * Returns time of sunset for a given day and location
 * @link http://www.php.net/manual/en/function.date-sunset.php
 * @param int $timestamp The timestamp of the day from which the sunset
 * time is taken.
 * @param int $returnFormat [optional] <table>
 * returnFormat constants
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
 * <td>returns the result as int (timestamp)</td>
 * <td>1095034606</td>
 * </tr>
 * </table>
 * </table>
 * @param mixed $latitude [optional] Defaults to North, pass in a negative value for South.
 * See also: date.default_latitude
 * @param mixed $longitude [optional] Defaults to East, pass in a negative value for West.
 * See also: date.default_longitude
 * @param mixed $zenith [optional] zenith is the angle between the center of the sun
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
 * @param mixed $utcOffset [optional] Specified in hours.
 * The utcOffset is ignored, if
 * returnFormat is
 * SUNFUNCS_RET_TIMESTAMP.
 * @return mixed the sunset time in a specified returnFormat on
 * success or false on failure. One potential reason for failure is that the
 * sun does not set at all, which happens inside the polar circles for part of
 * the year.
 * @deprecated 
 */
function date_sunset (int $timestamp, int $returnFormat = null, $latitude = null, $longitude = null, $zenith = null, $utcOffset = null): string|int|float|false {}

/**
 * Returns an array with information about sunset/sunrise and twilight begin/end
 * @link http://www.php.net/manual/en/function.date-sun-info.php
 * @param int $timestamp Unix timestamp.
 * @param float $latitude Latitude in degrees.
 * @param float $longitude Longitude in degrees.
 * @return array array on success or false on failure.
 * The structure of the array is detailed in the following list:
 * <p>
 * <p>
 * sunrise
 * <br>
 * The timestamp of the sunrise (zenith angle = 90°35').
 * sunset
 * <br>
 * The timestamp of the sunset (zenith angle = 90°35').
 * transit
 * <br>
 * The timestamp when the sun is at its zenith, i.e. has reached its topmost
 * point.
 * civil_twilight_begin
 * <br>
 * The start of the civil dawn (zenith angle = 96°). It ends at
 * sunrise.
 * civil_twilight_end
 * <br>
 * The end of the civil dusk (zenith angle = 96°). It starts at
 * sunset.
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
function date_sun_info (int $timestamp, float $latitude, float $longitude): array {}

define ('DATE_ATOM', "Y-m-d\TH:i:sP");
define ('DATE_COOKIE', "l, d-M-Y H:i:s T");
define ('DATE_ISO8601', "Y-m-d\TH:i:sO");
define ('DATE_ISO8601_EXPANDED', "X-m-d\TH:i:sP");
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

// End of date v.8.2.6
