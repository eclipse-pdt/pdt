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
	/**
	 * Atom (example: 2005-08-15T15:52:01+00:00)
	const ATOM = "Y-m-d\TH:i:sP";
	/**
	 * HTTP Cookies (example: Monday, 15-Aug-2005 15:52:01 UTC)
	const COOKIE = "l, d-M-Y H:i:s T";
	/**
	 * ISO-8601 (example: 2005-08-15T15:52:01+0000)
	 * This format is not compatible with ISO-8601, but is left this way for
	 * backward compatibility reasons. Use
	 * DateTimeInterface::ISO8601_EXPANDED,
	 * DateTimeInterface::ATOM for compatibility with ISO-8601
	 * instead. (ref ISO8601:2004 section 4.3.3 clause d)
	const ISO8601 = "Y-m-d\TH:i:sO";
	/**
	 * ISO-8601 Expanded (example: +10191-07-26T08:59:52+01:00)
	 * This format allows for year ranges outside of ISO-8601's normal range
	 * of 0000-9999 by always
	 * including a sign character. It also addresses that that timezone part
	 * (+01:00) is compatible with ISO-8601.
	const ISO8601_EXPANDED = "X-m-d\TH:i:sP";
	/**
	 * RFC 822 (example: Mon, 15 Aug 05 15:52:01 +0000)
	const RFC822 = "D, d M y H:i:s O";
	/**
	 * RFC 850 (example: Monday, 15-Aug-05 15:52:01 UTC)
	const RFC850 = "l, d-M-y H:i:s T";
	/**
	 * RFC 1036 (example: Mon, 15 Aug 05 15:52:01 +0000)
	const RFC1036 = "D, d M y H:i:s O";
	/**
	 * RFC 1123 (example: Mon, 15 Aug 2005 15:52:01 +0000)
	const RFC1123 = "D, d M Y H:i:s O";
	/**
	 * RFC 7231 (since PHP 7.0.19 and 7.1.5) (example: Sat, 30 Apr 2016 17:52:13 GMT)
	const RFC7231 = "D, d M Y H:i:s \G\M\T";
	/**
	 * RFC 2822 (example: Mon, 15 Aug 2005 15:52:01 +0000)
	const RFC2822 = "D, d M Y H:i:s O";
	/**
	 * Same as DATE_ATOM
	const RFC3339 = "Y-m-d\TH:i:sP";
	/**
	 * RFC 3339 EXTENDED format (example: 2005-08-15T15:52:01.000+00:00)
	const RFC3339_EXTENDED = "Y-m-d\TH:i:s.vP";
	/**
	 * RSS (example: Mon, 15 Aug 2005 15:52:01 +0000)
	const RSS = "D, d M Y H:i:s O";
	/**
	 * World Wide Web Consortium (example: 2005-08-15T15:52:01+00:00)
	const W3C = "Y-m-d\TH:i:sP";


	/**
	 * Returns date formatted according to given format
	 * @link http://www.php.net/manual/en/datetime.format.php
	 * @param string $format The format of the outputted date string. See the formatting
	 * options below. There are also several
	 * predefined date constants
	 * that may be used instead, so for example DATE_RSS
	 * contains the format string 'D, d M Y H:i:s'.
	 * <p><table>
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
	 * </table></p>
	 * <p>Unrecognized characters in the format string will be printed
	 * as-is. The Z format will always return
	 * 0 when using gmdate.</p>
	 * <p>Since this function only accepts int timestamps the
	 * u format character is only useful when using the
	 * date_format function with user based timestamps
	 * created with date_create.</p>
	 * @return string Returns the formatted date string on success.
	 */
	abstract public function format (string $format): string;

	/**
	 * Return time zone relative to given DateTime
	 * @link http://www.php.net/manual/en/datetime.gettimezone.php
	 * @return DateTimeZone|false Returns a DateTimeZone object on success
	 * or false on failure.
	 */
	abstract public function getTimezone (): DateTimeZone|false;

	/**
	 * Returns the timezone offset
	 * @link http://www.php.net/manual/en/datetime.getoffset.php
	 * @return int Returns the timezone offset in seconds from UTC on success.
	 */
	abstract public function getOffset (): int;

	/**
	 * Gets the Unix timestamp
	 * @link http://www.php.net/manual/en/datetime.gettimestamp.php
	 * @return int Returns the Unix timestamp representing the date.
	 */
	abstract public function getTimestamp (): int;

	/**
	 * Returns the difference between two DateTime objects
	 * @link http://www.php.net/manual/en/datetime.diff.php
	 * @param DateTimeInterface $targetObject 
	 * @param bool $absolute [optional] Should the interval be forced to be positive?
	 * @return DateInterval The DateInterval object represents the
	 * difference between the two dates.
	 * <p>The return value more specifically represents the clock-time interval to
	 * apply to the original object ($this or
	 * $originObject) to arrive at the
	 * $targetObject. This process is not always
	 * reversible.</p>
	 * <p>The method is aware of DST changeovers, and hence can return an interval of
	 * 24 hours and 30 minutes, as per one of the examples. If
	 * you want to calculate with absolute time, you need to convert both the
	 * $this/$baseObject, and
	 * $targetObject to UTC first.</p>
	 */
	abstract public function diff (DateTimeInterface $targetObject, bool $absolute = false): DateInterval;

	/**
	 * The __wakeup handler
	 * @link http://www.php.net/manual/en/datetime.wakeup.php
	 * @return void Initializes a DateTime object.
	 */
	abstract public function __wakeup (): void;

	/**
	 * {@inheritdoc}
	 */
	abstract public function __serialize (): array;

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	abstract public function __unserialize (array $data): void;

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
	 * @param string $datetime [optional] A date/time string. Valid formats are explained in Date and Time Formats.
	 * <p>Enter "now" here to obtain the current time when using
	 * the $timezone parameter.</p>
	 * @param DateTimeZone|null $timezone [optional] A DateTimeZone object representing the
	 * timezone of $datetime.
	 * <p>If $timezone is omitted or null,
	 * the current timezone will be used.</p>
	 * <p>The $timezone parameter
	 * and the current timezone are ignored when the
	 * $datetime parameter either
	 * is a UNIX timestamp (e.g. @946684800)
	 * or specifies a timezone
	 * (e.g. 2010-01-28T15:00:00+02:00).</p>
	 * @return string Returns a new DateTime instance.
	 * Procedural style returns false on failure.
	 */
	public function __construct (string $datetime = '"now"', ?DateTimeZone $timezone = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data): void {}

	/**
	 * The __wakeup handler
	 * @link http://www.php.net/manual/en/datetime.wakeup.php
	 * @return void Initializes a DateTime object.
	 */
	public function __wakeup (): void {}

	/**
	 * The __set_state handler
	 * @link http://www.php.net/manual/en/datetime.set-state.php
	 * @param array $array Initialization array.
	 * @return DateTime Returns a new instance of a DateTime object.
	 */
	public static function __set_state (array $array): DateTime {}

	/**
	 * Returns new DateTime instance encapsulating the given DateTimeImmutable object
	 * @link http://www.php.net/manual/en/datetime.createfromimmutable.php
	 * @param DateTimeImmutable $object 
	 * @return static Returns a new DateTime instance.
	 */
	public static function createFromImmutable (DateTimeImmutable $object): static {}

	/**
	 * Returns new DateTime object encapsulating the given DateTimeInterface object
	 * @link http://www.php.net/manual/en/datetime.createfrominterface.php
	 * @param DateTimeInterface $object 
	 * @return DateTime Returns a new DateTime instance.
	 */
	public static function createFromInterface (DateTimeInterface $object): DateTime {}

	/**
	 * Parses a time string according to a specified format
	 * @link http://www.php.net/manual/en/datetime.createfromformat.php
	 * @param string $format 
	 * @param string $datetime 
	 * @param DateTimeZone|null $timezone [optional] 
	 * @return DateTime|false Returns a new DateTime instance or false on failure.
	 */
	public static function createFromFormat (string $format, string $datetime, ?DateTimeZone $timezone = null): DateTime|false {}

	/**
	 * {@inheritdoc}
	 */
	public static function getLastErrors () {}

	/**
	 * Returns date formatted according to given format
	 * @link http://www.php.net/manual/en/datetime.format.php
	 * @param string $format The format of the outputted date string. See the formatting
	 * options below. There are also several
	 * predefined date constants
	 * that may be used instead, so for example DATE_RSS
	 * contains the format string 'D, d M Y H:i:s'.
	 * <p><table>
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
	 * </table></p>
	 * <p>Unrecognized characters in the format string will be printed
	 * as-is. The Z format will always return
	 * 0 when using gmdate.</p>
	 * <p>Since this function only accepts int timestamps the
	 * u format character is only useful when using the
	 * date_format function with user based timestamps
	 * created with date_create.</p>
	 * @return string Returns the formatted date string on success.
	 */
	public function format (string $format): string {}

	/**
	 * Alters the timestamp
	 * @link http://www.php.net/manual/en/datetime.modify.php
	 * @param string $modifier A date/time string. Valid formats are explained in Date and Time Formats.
	 * @return DateTime|false Returns the modified DateTime object for method chaining or false on failure.
	 */
	public function modify (string $modifier): DateTime|false {}

	/**
	 * Modifies a DateTime object, with added amount of days, months, years, hours, minutes and seconds
	 * @link http://www.php.net/manual/en/datetime.add.php
	 * @param DateInterval $interval A DateInterval object
	 * @return DateTime Returns the modified DateTime object for method chaining.
	 */
	public function add (DateInterval $interval): DateTime {}

	/**
	 * Subtracts an amount of days, months, years, hours, minutes and seconds from
	 * a DateTime object
	 * @link http://www.php.net/manual/en/datetime.sub.php
	 * @param DateInterval $interval A DateInterval object
	 * @return DateTime Returns the modified DateTime object for method chaining.
	 */
	public function sub (DateInterval $interval): DateTime {}

	/**
	 * Return time zone relative to given DateTime
	 * @link http://www.php.net/manual/en/datetime.gettimezone.php
	 * @return DateTimeZone|false Returns a DateTimeZone object on success
	 * or false on failure.
	 */
	public function getTimezone (): DateTimeZone|false {}

	/**
	 * Sets the time zone for the DateTime object
	 * @link http://www.php.net/manual/en/datetime.settimezone.php
	 * @param DateTimeZone $timezone A DateTimeZone object representing the
	 * desired time zone.
	 * @return DateTime Returns the DateTime object for method chaining. The
	 * underlaying point-in-time is not changed when calling this method.
	 */
	public function setTimezone (DateTimeZone $timezone): DateTime {}

	/**
	 * Returns the timezone offset
	 * @link http://www.php.net/manual/en/datetime.getoffset.php
	 * @return int Returns the timezone offset in seconds from UTC on success.
	 */
	public function getOffset (): int {}

	/**
	 * Sets the time
	 * @link http://www.php.net/manual/en/datetime.settime.php
	 * @param int $hour Hour of the time.
	 * @param int $minute Minute of the time.
	 * @param int $second [optional] Second of the time.
	 * @param int $microsecond [optional] Microsecond of the time.
	 * @return DateTime Returns the modified DateTime object for method chaining.
	 */
	public function setTime (int $hour, int $minute, int $second = null, int $microsecond = null): DateTime {}

	/**
	 * Sets the date
	 * @link http://www.php.net/manual/en/datetime.setdate.php
	 * @param int $year Year of the date.
	 * @param int $month Month of the date.
	 * @param int $day Day of the date.
	 * @return DateTime Returns the modified DateTime object for method chaining.
	 */
	public function setDate (int $year, int $month, int $day): DateTime {}

	/**
	 * Sets the ISO date
	 * @link http://www.php.net/manual/en/datetime.setisodate.php
	 * @param int $year Year of the date.
	 * @param int $week Week of the date.
	 * @param int $dayOfWeek [optional] Offset from the first day of the week.
	 * @return DateTime Returns the modified DateTime object for method chaining.
	 */
	public function setISODate (int $year, int $week, int $dayOfWeek = 1): DateTime {}

	/**
	 * Sets the date and time based on an Unix timestamp
	 * @link http://www.php.net/manual/en/datetime.settimestamp.php
	 * @param int $timestamp Unix timestamp representing the date.
	 * Setting timestamps outside the range of int is possible by using
	 * DateTimeImmutable::modify with the @ format.
	 * @return DateTime Returns the modified DateTime object for method chaining.
	 */
	public function setTimestamp (int $timestamp): DateTime {}

	/**
	 * Gets the Unix timestamp
	 * @link http://www.php.net/manual/en/datetime.gettimestamp.php
	 * @return int Returns the Unix timestamp representing the date.
	 */
	public function getTimestamp (): int {}

	/**
	 * Returns the difference between two DateTime objects
	 * @link http://www.php.net/manual/en/datetime.diff.php
	 * @param DateTimeInterface $targetObject 
	 * @param bool $absolute [optional] Should the interval be forced to be positive?
	 * @return DateInterval The DateInterval object represents the
	 * difference between the two dates.
	 * <p>The return value more specifically represents the clock-time interval to
	 * apply to the original object ($this or
	 * $originObject) to arrive at the
	 * $targetObject. This process is not always
	 * reversible.</p>
	 * <p>The method is aware of DST changeovers, and hence can return an interval of
	 * 24 hours and 30 minutes, as per one of the examples. If
	 * you want to calculate with absolute time, you need to convert both the
	 * $this/$baseObject, and
	 * $targetObject to UTC first.</p>
	 */
	public function diff (DateTimeInterface $targetObject, bool $absolute = false): DateInterval {}

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
	 * @param string $datetime [optional] A date/time string. Valid formats are explained in Date and Time Formats.
	 * <p>Enter "now" here to obtain the current time when using
	 * the $timezone parameter.</p>
	 * @param DateTimeZone|null $timezone [optional] A DateTimeZone object representing the
	 * timezone of $datetime.
	 * <p>If $timezone is omitted or null,
	 * the current timezone will be used.</p>
	 * <p>The $timezone parameter
	 * and the current timezone are ignored when the
	 * $datetime parameter either
	 * is a UNIX timestamp (e.g. @946684800)
	 * or specifies a timezone
	 * (e.g. 2010-01-28T15:00:00+02:00, or
	 * 2010-07-05T06:00:00Z).</p>
	 * @return DateTimeImmutable|false Returns a new DateTimeImmutable instance.
	 * Procedural style returns false on failure.
	 */
	public function __construct (string $datetime = '"now"', ?DateTimeZone $timezone = null): DateTimeImmutable|false {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data): void {}

	/**
	 * The __wakeup handler
	 * @link http://www.php.net/manual/en/datetime.wakeup.php
	 * @return void Initializes a DateTime object.
	 */
	public function __wakeup (): void {}

	/**
	 * The __set_state handler
	 * @link http://www.php.net/manual/en/datetimeimmutable.set-state.php
	 * @param array $array Initialization array.
	 * @return DateTimeImmutable Returns a new instance of a DateTimeImmutable object.
	 */
	public static function __set_state (array $array): DateTimeImmutable {}

	/**
	 * Parses a time string according to a specified format
	 * @link http://www.php.net/manual/en/datetimeimmutable.createfromformat.php
	 * @param string $format The format that the passed in string should be in. See the
	 * formatting options below. In most cases, the same letters as for the
	 * date can be used.
	 * <p>All fields are initialised with the current date/time. In most cases you
	 * would want to reset these to "zero" (the Unix epoch, 1970-01-01
	 * 00:00:00 UTC). You do that by including the
	 * ! character as first character in your
	 * format, or | as your last.
	 * Please see the documentation for each character below for more
	 * information.</p>
	 * <p>The format is parsed from left to right, which means that in some
	 * situations the order in which the format characters are present affects
	 * the result. In the case of z (the day of the year),
	 * it is required that a year has already been parsed,
	 * for example through the Y or y
	 * characters.</p>
	 * <p>Letters that are used for parsing numbers allow a wide range of values,
	 * outside of what the logical range would be. For example, the
	 * d (day of the month) accepts values in the range from
	 * 00 to 99. The only constraint is
	 * on the amount of digits. The date/time parser's overflow mechanism is
	 * used when out-of-range values are given. The examples below show some of
	 * this behaviour.</p>
	 * <p>This also means that the data parsed for a format letter is greedy, and
	 * will read up to the amount of digits its format allows for. That can
	 * then also mean that there are no
	 * longer enough characters in the datetime string
	 * for following format characters. An example on this page also
	 * illustrates this issue.</p>
	 * <p><table>
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
	 * 1 to 31. (2 digit numbers
	 * higher than the number of days in the month are accepted, in which
	 * case they will make the month overflow. For example using 33 with
	 * January, means February 2nd)
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>D and l</td>
	 * <td>A textual representation of a day</td>
	 * <td>
	 * Mon through Sun or
	 * Sunday through Saturday. If
	 * the day name given is different then the day name belonging to a
	 * parsed (or default) date is different, then an overflow occurs to
	 * the next date with the given day name. See the
	 * examples below for an explanation.
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
	 * <td>
	 * The day of the year (starting from 0);
	 * must be preceded by Y or y.
	 * </td>
	 * <td>
	 * 0 through 365. (3 digit
	 * numbers higher than the numbers in a year are accepted, in which
	 * case they will make the year overflow. For example using 366 with
	 * 2022, means January 2nd, 2023)
	 * </td>
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
	 * 1 through 12.
	 * (2 digit numbers higher than 12 are accepted, in which case they
	 * will make the year overflow. For example using 13 means January in
	 * the next year)
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>Year</td>
	 * <td>---</td>
	 * <td>---</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>X and x</td>
	 * <td>A full numeric representation of a year, up to 19 digits,
	 * optionally prefixed by + or
	 * -</td>
	 * <td>Examples: 0055, 787,
	 * 1999, -2003,
	 * +10191</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>Y</td>
	 * <td>A full numeric representation of a year, up to 4 digits</td>
	 * <td>Examples: 0055, 787,
	 * 1999, 2003</td>
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
	 * 01 through 12 (2 digit
	 * numbers higher than 12 are accepted, in which case they will make
	 * the day overflow. For example using 14 means
	 * 02 in the next AM/PM period)
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>G and H</td>
	 * <td>24-hour format of an hour with or without leading zeros</td>
	 * <td>
	 * 0 through 23 or
	 * 00 through 23 (2 digit
	 * numbers higher than 24 are accepted, in which case they will make
	 * the day overflow. For example using 26 means
	 * 02:00 the next day)
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>i</td>
	 * <td>Minutes with leading zeros</td>
	 * <td>
	 * 00 to 59. (2 digit
	 * numbers higher than 59 are accepted, in which case they will make
	 * the hour overflow. For example using 66 means
	 * :06 the next hour)
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>s</td>
	 * <td>Seconds, with leading zeros</td>
	 * <td>
	 * 00 through 59 (2 digit
	 * numbers higher than 59 are accepted, in which case they will make
	 * the minute overflow. For example using 90 means
	 * :30 the next minute)
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>v</td>
	 * <td>Fraction in milliseconds (up to three digits)</td>
	 * <td>Example: 12 (0.12
	 * seconds), 345 (0.345 seconds)</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>u</td>
	 * <td>Fraction in microseconds (up to six digits)</td>
	 * <td>Example: 45 (0.45
	 * seconds), 654321 (0.654321
	 * seconds)</td>
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
	 * fraction and timezone information) to zero-like values (
	 * 0 for hour, minute, second and fraction,
	 * 1 for month and day, 1970
	 * for year and UTC for timezone information)</td>
	 * <td>Without !, all fields will be set to the
	 * current date and time.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>|</td>
	 * <td>Resets all fields (year, month, day, hour, minute, second,
	 * fraction and timezone information) to zero-like values if they have
	 * not been parsed yet</td>
	 * <td>Y-m-d| will set the year, month and day
	 * to the information found in the string to parse, and sets the hour,
	 * minute and second to 0.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>+</td>
	 * <td>If this format specifier is present, trailing data in the
	 * string will not cause an error, but a warning instead</td>
	 * <td>Use DateTimeImmutable::getLastErrors to find out
	 * whether trailing data was present.</td>
	 * </tr>
	 * </table>
	 * </table></p>
	 * <p>Unrecognized characters in the format string will cause the
	 * parsing to fail and an error message is appended to the returned
	 * structure. You can query error messages with
	 * DateTimeImmutable::getLastErrors.</p>
	 * <p>To include literal characters in format, you have
	 * to escape them with a backslash (\).</p>
	 * <p>If format does not contain the character
	 * ! then portions of the generated date/time which are not
	 * specified in format will be set to the current
	 * system time.</p>
	 * <p>If format contains the
	 * character !, then portions of the generated
	 * date/time not provided in format, as well as
	 * values to the left-hand side of the !, will
	 * be set to corresponding values from the Unix epoch.</p>
	 * <p>If any time character is parsed, then all other time-related fields are
	 * set to "0", unless also parsed.</p>
	 * <p>The Unix epoch is 1970-01-01 00:00:00 UTC.</p>
	 * @param string $datetime String representing the time.
	 * @param DateTimeZone|null $timezone [optional] A DateTimeZone object representing the
	 * desired time zone.
	 * <p>If timezone is omitted or null and
	 * datetime contains no timezone,
	 * the current timezone will be used.</p>
	 * <p>The timezone parameter
	 * and the current timezone are ignored when the
	 * datetime parameter either
	 * contains a UNIX timestamp (e.g. 946684800)
	 * or specifies a timezone
	 * (e.g. 2010-01-28T15:00:00+02:00).</p>
	 * @return DateTimeImmutable|false Returns a new DateTimeImmutable instance or false on failure.
	 */
	public static function createFromFormat (string $format, string $datetime, ?DateTimeZone $timezone = null): DateTimeImmutable|false {}

	/**
	 * Returns the warnings and errors
	 * @link http://www.php.net/manual/en/datetimeimmutable.getlasterrors.php
	 * @return array|false Returns array containing info about warnings and errors, or false if there
	 * are neither warnings nor errors.
	 */
	public static function getLastErrors (): array|false {}

	/**
	 * Returns date formatted according to given format
	 * @link http://www.php.net/manual/en/datetime.format.php
	 * @param string $format The format of the outputted date string. See the formatting
	 * options below. There are also several
	 * predefined date constants
	 * that may be used instead, so for example DATE_RSS
	 * contains the format string 'D, d M Y H:i:s'.
	 * <p><table>
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
	 * </table></p>
	 * <p>Unrecognized characters in the format string will be printed
	 * as-is. The Z format will always return
	 * 0 when using gmdate.</p>
	 * <p>Since this function only accepts int timestamps the
	 * u format character is only useful when using the
	 * date_format function with user based timestamps
	 * created with date_create.</p>
	 * @return string Returns the formatted date string on success.
	 */
	public function format (string $format): string {}

	/**
	 * Return time zone relative to given DateTime
	 * @link http://www.php.net/manual/en/datetime.gettimezone.php
	 * @return DateTimeZone|false Returns a DateTimeZone object on success
	 * or false on failure.
	 */
	public function getTimezone (): DateTimeZone|false {}

	/**
	 * Returns the timezone offset
	 * @link http://www.php.net/manual/en/datetime.getoffset.php
	 * @return int Returns the timezone offset in seconds from UTC on success.
	 */
	public function getOffset (): int {}

	/**
	 * Gets the Unix timestamp
	 * @link http://www.php.net/manual/en/datetime.gettimestamp.php
	 * @return int Returns the Unix timestamp representing the date.
	 */
	public function getTimestamp (): int {}

	/**
	 * Returns the difference between two DateTime objects
	 * @link http://www.php.net/manual/en/datetime.diff.php
	 * @param DateTimeInterface $targetObject 
	 * @param bool $absolute [optional] Should the interval be forced to be positive?
	 * @return DateInterval The DateInterval object represents the
	 * difference between the two dates.
	 * <p>The return value more specifically represents the clock-time interval to
	 * apply to the original object ($this or
	 * $originObject) to arrive at the
	 * $targetObject. This process is not always
	 * reversible.</p>
	 * <p>The method is aware of DST changeovers, and hence can return an interval of
	 * 24 hours and 30 minutes, as per one of the examples. If
	 * you want to calculate with absolute time, you need to convert both the
	 * $this/$baseObject, and
	 * $targetObject to UTC first.</p>
	 */
	public function diff (DateTimeInterface $targetObject, bool $absolute = false): DateInterval {}

	/**
	 * Creates a new object with modified timestamp
	 * @link http://www.php.net/manual/en/datetimeimmutable.modify.php
	 * @param string $modifier A date/time string. Valid formats are explained in Date and Time Formats.
	 * @return DateTimeImmutable|false Returns a new modified DateTimeImmutable object or false on failure.
	 */
	public function modify (string $modifier): DateTimeImmutable|false {}

	/**
	 * Returns a new object, with added amount of days, months, years, hours, minutes and seconds
	 * @link http://www.php.net/manual/en/datetimeimmutable.add.php
	 * @param DateInterval $interval A DateInterval object
	 * @return DateTimeImmutable Returns a new DateTimeImmutable object with the modified data.
	 */
	public function add (DateInterval $interval): DateTimeImmutable {}

	/**
	 * Subtracts an amount of days, months, years, hours, minutes and seconds
	 * @link http://www.php.net/manual/en/datetimeimmutable.sub.php
	 * @param DateInterval $interval A DateInterval object
	 * @return DateTimeImmutable Returns a new DateTimeImmutable object with the modified data.
	 */
	public function sub (DateInterval $interval): DateTimeImmutable {}

	/**
	 * Sets the time zone
	 * @link http://www.php.net/manual/en/datetimeimmutable.settimezone.php
	 * @param DateTimeZone $timezone A DateTimeZone object representing the
	 * desired time zone.
	 * @return DateTimeImmutable Returns a new modified DateTimeImmutable object for
	 * method chaining. The underlaying point-in-time is not changed when calling
	 * this method.
	 */
	public function setTimezone (DateTimeZone $timezone): DateTimeImmutable {}

	/**
	 * Sets the time
	 * @link http://www.php.net/manual/en/datetimeimmutable.settime.php
	 * @param int $hour Hour of the time.
	 * @param int $minute Minute of the time.
	 * @param int $second [optional] Second of the time.
	 * @param int $microsecond [optional] Microsecond of the time.
	 * @return DateTimeImmutable Returns a new DateTimeImmutable object with the modified data.
	 */
	public function setTime (int $hour, int $minute, int $second = null, int $microsecond = null): DateTimeImmutable {}

	/**
	 * Sets the date
	 * @link http://www.php.net/manual/en/datetimeimmutable.setdate.php
	 * @param int $year Year of the date.
	 * @param int $month Month of the date.
	 * @param int $day Day of the date.
	 * @return DateTimeImmutable Returns a new DateTimeImmutable object with the modified data.
	 */
	public function setDate (int $year, int $month, int $day): DateTimeImmutable {}

	/**
	 * Sets the ISO date
	 * @link http://www.php.net/manual/en/datetimeimmutable.setisodate.php
	 * @param int $year Year of the date.
	 * @param int $week Week of the date.
	 * @param int $dayOfWeek [optional] Offset from the first day of the week.
	 * @return DateTimeImmutable Returns a new DateTimeImmutable object with the modified data.
	 */
	public function setISODate (int $year, int $week, int $dayOfWeek = 1): DateTimeImmutable {}

	/**
	 * Sets the date and time based on a Unix timestamp
	 * @link http://www.php.net/manual/en/datetimeimmutable.settimestamp.php
	 * @param int $timestamp Unix timestamp representing the date.
	 * Setting timestamps outside the range of int is possible by using
	 * DateTimeImmutable::modify with the @ format.
	 * @return DateTimeImmutable Returns a new DateTimeImmutable object with the modified data.
	 */
	public function setTimestamp (int $timestamp): DateTimeImmutable {}

	/**
	 * Returns new DateTimeImmutable instance encapsulating the given DateTime object
	 * @link http://www.php.net/manual/en/datetimeimmutable.createfrommutable.php
	 * @param DateTime $object 
	 * @return static Returns a new DateTimeImmutable instance.
	 */
	public static function createFromMutable (DateTime $object): static {}

	/**
	 * Returns new DateTimeImmutable object encapsulating the given DateTimeInterface object
	 * @link http://www.php.net/manual/en/datetimeimmutable.createfrominterface.php
	 * @param DateTimeInterface $object 
	 * @return DateTimeImmutable Returns a new DateTimeImmutable instance.
	 */
	public static function createFromInterface (DateTimeInterface $object): DateTimeImmutable {}

}

/**
 * Representation of time zone.
 * @link http://www.php.net/manual/en/class.datetimezone.php
 */
class DateTimeZone  {
	/**
	 * Africa time zones.
	const AFRICA = 1;
	/**
	 * America time zones.
	const AMERICA = 2;
	/**
	 * Antarctica time zones.
	const ANTARCTICA = 4;
	/**
	 * Arctic time zones.
	const ARCTIC = 8;
	/**
	 * Asia time zones.
	const ASIA = 16;
	/**
	 * Atlantic time zones.
	const ATLANTIC = 32;
	/**
	 * Australia time zones.
	const AUSTRALIA = 64;
	/**
	 * Europe time zones.
	const EUROPE = 128;
	/**
	 * Indian time zones.
	const INDIAN = 256;
	/**
	 * Pacific time zones.
	const PACIFIC = 512;
	/**
	 * UTC time zones.
	const UTC = 1024;
	/**
	 * All time zones.
	const ALL = 2047;
	/**
	 * All time zones including backwards compatible.
	const ALL_WITH_BC = 4095;
	/**
	 * Time zones per country.
	const PER_COUNTRY = 4096;


	/**
	 * Creates new DateTimeZone object
	 * @link http://www.php.net/manual/en/datetimezone.construct.php
	 * @param string $timezone 
	 * @return DateTimeZone|false Returns DateTimeZone on success.
	 * Procedural style returns false on failure.
	 */
	public function __construct (string $timezone): DateTimeZone|false {}

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
	public function getName (): string {}

	/**
	 * Returns the timezone offset from GMT
	 * @link http://www.php.net/manual/en/datetimezone.getoffset.php
	 * @param DateTimeInterface $datetime 
	 * @return int Returns time zone offset in seconds.
	 */
	public function getOffset (DateTimeInterface $datetime): int {}

	/**
	 * Returns all transitions for the timezone
	 * @link http://www.php.net/manual/en/datetimezone.gettransitions.php
	 * @param int $timestampBegin [optional] 
	 * @param int $timestampEnd [optional] 
	 * @return array|false Returns a numerically indexed array of
	 * transition arrays on success, or false on failure. DateTimeZone
	 * objects wrapping type 1 (UTC offsets) and type 2 (abbreviations) do not
	 * contain any transitions, and calling this method on them will return
	 * false.
	 * <p>If timestampBegin is given, the first entry in the
	 * returned array will contain a transition element at the time of
	 * timestampBegin.</p>
	 * <p><table>
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
	 * </table></p>
	 */
	public function getTransitions (int $timestampBegin = PHP_INT_MIN, int $timestampEnd = PHP_INT_MAX): array|false {}

	/**
	 * Returns location information for a timezone
	 * @link http://www.php.net/manual/en/datetimezone.getlocation.php
	 * @return array|false Array containing location information about timezone or false on failure.
	 */
	public function getLocation (): array|false {}

	/**
	 * Returns associative array containing dst, offset and the timezone name
	 * @link http://www.php.net/manual/en/datetimezone.listabbreviations.php
	 * @return array Returns the array of timezone abbreviations.
	 */
	public static function listAbbreviations (): array {}

	/**
	 * Returns a numerically indexed array containing all defined timezone identifiers
	 * @link http://www.php.net/manual/en/datetimezone.listidentifiers.php
	 * @param int $timezoneGroup [optional] 
	 * @param string|null $countryCode [optional] 
	 * @return array Returns the array of timezone identifiers. Only non-outdated items are
	 * returned. To get all, including outdated timezone identifiers, use the
	 * DateTimeZone::ALL_WITH_BC as value for
	 * timezoneGroup.
	 */
	public static function listIdentifiers (int $timezoneGroup = \DateTimeZone::ALL, ?string $countryCode = null): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 * @param array $array
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
	public int $y;

	/**
	 * Number of months.
	 * @var int
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.m
	 */
	public int $m;

	/**
	 * Number of days.
	 * @var int
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.d
	 */
	public int $d;

	/**
	 * Number of hours.
	 * @var int
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.h
	 */
	public int $h;

	/**
	 * Number of minutes.
	 * @var int
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.i
	 */
	public int $i;

	/**
	 * Number of seconds.
	 * @var int
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.s
	 */
	public int $s;

	/**
	 * Number of microseconds, as a fraction of a second.
	 * @var float
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.f
	 */
	public float $f;

	/**
	 * Is 1 if the interval
	 * represents a negative time period and
	 * 0 otherwise.
	 * See DateInterval::format.
	 * @var int
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.invert
	 */
	public int $invert;

	/**
	 * If the DateInterval object was created by
	 * DateTimeImmutable::diff or
	 * DateTime::diff, then this is the
	 * total number of full days between the start and end dates. Otherwise,
	 * days will be false.
	 * @var mixed
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.days
	 */
	public mixed $days;

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
	public bool $from_string;

	/**
	 * The string used as argument to
	 * DateInterval::createFromDateString.
	 * @var string
	 * @link http://www.php.net/manual/en/class.dateinterval.php#dateinterval.props.date_string
	 */
	public string $date_string;

	/**
	 * Creates a new DateInterval object
	 * @link http://www.php.net/manual/en/dateinterval.construct.php
	 * @param string $duration 
	 * @return string 
	 */
	public function __construct (string $duration): string {}

	/**
	 * Sets up a DateInterval from the relative parts of the string
	 * @link http://www.php.net/manual/en/dateinterval.createfromdatestring.php
	 * @param string $datetime 
	 * @return DateInterval|false Returns a new DateInterval instance on success, or false on failure.
	 */
	public static function createFromDateString (string $datetime): DateInterval|false {}

	/**
	 * Formats the interval
	 * @link http://www.php.net/manual/en/dateinterval.format.php
	 * @param string $format 
	 * @return string Returns the formatted interval.
	 */
	public function format (string $format): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 * @param array $array
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
	/**
	 * Exclude start date, used in DatePeriod::__construct.
	const EXCLUDE_START_DATE = 1;
	/**
	 * Include end date, used in DatePeriod::__construct.
	const INCLUDE_END_DATE = 2;


	/**
	 * The start date of the period.
	 * @var DateTimeInterface|null
	 * @link http://www.php.net/manual/en/class.dateperiod.php#dateperiod.props.start
	 */
	public readonly ?DateTimeInterface $start;

	/**
	 * During iteration this will contain the current date within the period.
	 * @var DateTimeInterface|null
	 * @link http://www.php.net/manual/en/class.dateperiod.php#dateperiod.props.current
	 */
	public readonly ?DateTimeInterface $current;

	/**
	 * The end date of the period.
	 * @var DateTimeInterface|null
	 * @link http://www.php.net/manual/en/class.dateperiod.php#dateperiod.props.end
	 */
	public readonly ?DateTimeInterface $end;

	/**
	 * An ISO 8601 repeating interval specification.
	 * @var DateInterval|null
	 * @link http://www.php.net/manual/en/class.dateperiod.php#dateperiod.props.interval
	 */
	public readonly ?DateInterval $interval;

	/**
	 * The minimum amount of instances as retured by the iterator.
	 * <p>If the number of recurrences has been explicitly passed through the
	 * recurrences parameter in the constructor of the
	 * DatePeriod instance, then this property contains
	 * this value, plus one if the start date has not been disabled
	 * through DatePeriod::EXCLUDE_START_DATE,
	 * plus one if the end date has been enabled through
	 * DatePeriod::INCLUDE_END_DATE.</p>
	 * <p>If the number of recurrences has not been explicitly passed, then this
	 * property contains the minimum number of returned instances. This would
	 * be 0, plus one if the start date
	 * has not been disabled through
	 * DatePeriod::EXCLUDE_START_DATE,
	 * plus one if the end date has been enabled through
	 * DatePeriod::INCLUDE_END_DATE.</p>
	 * <p><pre><code>&lt;?php
	 * $start = new DateTime(&&#35;039;2018-12-31 00:00:00&&#35;039;);
	 * $end = new DateTime(&&#35;039;2021-12-31 00:00:00&&#35;039;);
	 * $interval = new DateInterval(&&#35;039;P1M&&#35;039;);
	 * $recurrences = 5;
	 * &#47;&#47; recurrences explicitly set through the constructor
	 * $period = new DatePeriod($start, $interval, $recurrences, DatePeriod::EXCLUDE_START_DATE);
	 * echo $period-&gt;recurrences, &quot;\n&quot;;
	 * $period = new DatePeriod($start, $interval, $recurrences);
	 * echo $period-&gt;recurrences, &quot;\n&quot;;
	 * $period = new DatePeriod($start, $interval, $recurrences, DatePeriod::INCLUDE_END_DATE);
	 * echo $period-&gt;recurrences, &quot;\n&quot;;
	 * &#47;&#47; recurrences not set in the constructor
	 * $period = new DatePeriod($start, $interval, $end);
	 * echo $period-&gt;recurrences, &quot;\n&quot;;
	 * $period = new DatePeriod($start, $interval, $end, DatePeriod::EXCLUDE_START_DATE);
	 * echo $period-&gt;recurrences, &quot;\n&quot;;
	 * ?&gt;</code></pre>
	 * <p>The above example will output:</p>
	 * 5
	 * 6
	 * 7
	 * 1
	 * 0</p>
	 * <p>See also DatePeriod::getRecurrences.</p>
	 * @var int
	 * @link http://www.php.net/manual/en/class.dateperiod.php#dateperiod.props.recurrences
	 */
	public readonly int $recurrences;

	/**
	 * Whether to include the start date in the set of recurring dates or not.
	 * @var bool
	 * @link http://www.php.net/manual/en/class.dateperiod.php#dateperiod.props.include_start_date
	 */
	public readonly bool $include_start_date;

	/**
	 * Whether to include the end date in the set of recurring dates or not.
	 * @var bool
	 * @link http://www.php.net/manual/en/class.dateperiod.php#dateperiod.props.include_end_date
	 */
	public readonly bool $include_end_date;

	/**
	 * Creates a new DatePeriod object
	 * @link http://www.php.net/manual/en/dateperiod.construct.php
	 * @param DateTimeInterface $start 
	 * @param DateInterval $interval 
	 * @param int $recurrences 
	 * @param int $options [optional] 
	 * @return DateTimeInterface 
	 */
	public function __construct (DateTimeInterface $start, DateInterval $interval, int $recurrences, int $options = null): DateTimeInterface {}

	/**
	 * Gets the start date
	 * @link http://www.php.net/manual/en/dateperiod.getstartdate.php
	 * @return DateTimeInterface Returns a DateTimeImmutable object
	 * when the DatePeriod is initialized with a
	 * DateTimeImmutable object
	 * as the start parameter.
	 * <p>Returns a DateTime object
	 * otherwise.</p>
	 */
	public function getStartDate (): DateTimeInterface {}

	/**
	 * Gets the end date
	 * @link http://www.php.net/manual/en/dateperiod.getenddate.php
	 * @return DateTimeInterface|null Returns null if the DatePeriod does
	 * not have an end date. For example, when initialized with the
	 * recurrences parameter, or the
	 * isostr parameter without an
	 * end date.
	 * <p>Returns a DateTimeImmutable object
	 * when the DatePeriod is initialized with a
	 * DateTimeImmutable object
	 * as the end parameter.</p>
	 * <p>Returns a cloned DateTime object
	 * representing the end date otherwise.</p>
	 */
	public function getEndDate (): ?DateTimeInterface {}

	/**
	 * Gets the interval
	 * @link http://www.php.net/manual/en/dateperiod.getdateinterval.php
	 * @return DateInterval Returns a DateInterval object
	 */
	public function getDateInterval (): DateInterval {}

	/**
	 * Gets the number of recurrences
	 * @link http://www.php.net/manual/en/dateperiod.getrecurrences.php
	 * @return int|null The number of recurrences as set by explicitly passing the
	 * $recurrences to the contructor of the
	 * DatePeriod class, or null otherwise.
	 */
	public function getRecurrences (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 * @param array $array
	 */
	public static function __set_state (array $array) {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): Iterator {}

}

/**
 * Parse about any English textual datetime description into a Unix timestamp
 * @link http://www.php.net/manual/en/function.strtotime.php
 * @param string $datetime 
 * @param int|null $baseTimestamp [optional] 
 * @return int|false Returns a timestamp on success, false otherwise.
 */
function strtotime (string $datetime, ?int $baseTimestamp = null): int|false {}

/**
 * Format a Unix timestamp
 * @link http://www.php.net/manual/en/function.date.php
 * @param string $format Format accepted by DateTimeInterface::format.
 * @param int|null $timestamp [optional] The optional timestamp parameter is an
 * int Unix timestamp that defaults to the current
 * local time if timestamp is omitted or null. In other
 * words, it defaults to the value of time.
 * @return string Returns a formatted date string.
 */
function date (string $format, ?int $timestamp = null): string {}

/**
 * Format a local time/date part as integer
 * @link http://www.php.net/manual/en/function.idate.php
 * @param string $format 
 * @param int|null $timestamp [optional] 
 * @return int|false Returns an int on success, or false on failure.
 * <p>As idate always returns an int and
 * as they can't start with a "0", idate may return
 * fewer digits than you would expect. See the example below.</p>
 */
function idate (string $format, ?int $timestamp = null): int|false {}

/**
 * Format a GMT/UTC date/time
 * @link http://www.php.net/manual/en/function.gmdate.php
 * @param string $format 
 * @param int|null $timestamp [optional] 
 * @return string Returns a formatted date string.
 */
function gmdate (string $format, ?int $timestamp = null): string {}

/**
 * Get Unix timestamp for a date
 * @link http://www.php.net/manual/en/function.mktime.php
 * @param int $hour 
 * @param int|null $minute [optional] 
 * @param int|null $second [optional] 
 * @param int|null $month [optional] 
 * @param int|null $day [optional] 
 * @param int|null $year [optional] 
 * @return int|false mktime returns the Unix timestamp of the arguments
 * given.
 */
function mktime (int $hour, ?int $minute = null, ?int $second = null, ?int $month = null, ?int $day = null, ?int $year = null): int|false {}

/**
 * Get Unix timestamp for a GMT date
 * @link http://www.php.net/manual/en/function.gmmktime.php
 * @param int $hour 
 * @param int|null $minute [optional] 
 * @param int|null $second [optional] 
 * @param int|null $month [optional] 
 * @param int|null $day [optional] 
 * @param int|null $year [optional] 
 * @return int|false Returns a int Unix timestamp on success, or false on failure.
 */
function gmmktime (int $hour, ?int $minute = null, ?int $second = null, ?int $month = null, ?int $day = null, ?int $year = null): int|false {}

/**
 * Validate a Gregorian date
 * @link http://www.php.net/manual/en/function.checkdate.php
 * @param int $month 
 * @param int $day 
 * @param int $year 
 * @return bool Returns true if the date given is valid; otherwise returns false.
 */
function checkdate (int $month, int $day, int $year): bool {}

/**
 * Format a local time/date according to locale settings
 * @link http://www.php.net/manual/en/function.strftime.php
 * @param string $format 
 * @param int|null $timestamp [optional] 
 * @return string|false Returns a string formatted according format
 * using the given timestamp or the current
 * local time if no timestamp is given. Month and weekday names and
 * other language-dependent strings respect the current locale set
 * with setlocale.
 * The function returns false if format is empty, contains unsupported
 * conversion specifiers, or if the length of the returned string would be greater than
 * 4095.
 * @deprecated 1
 */
function strftime (string $format, ?int $timestamp = null): string|false {}

/**
 * Format a GMT/UTC time/date according to locale settings
 * @link http://www.php.net/manual/en/function.gmstrftime.php
 * @param string $format 
 * @param int|null $timestamp [optional] 
 * @return string|false Returns a string formatted according to the given format string
 * using the given timestamp or the current
 * local time if no timestamp is given. Month and weekday names and
 * other language dependent strings respect the current locale set
 * with setlocale.
 * On failure, false is returned.
 * @deprecated 1
 */
function gmstrftime (string $format, ?int $timestamp = null): string|false {}

/**
 * Return current Unix timestamp
 * @link http://www.php.net/manual/en/function.time.php
 * @return int Returns the current timestamp.
 */
function time (): int {}

/**
 * Get the local time
 * @link http://www.php.net/manual/en/function.localtime.php
 * @param int|null $timestamp [optional] 
 * @param bool $associative [optional] 
 * @return array If associative is set to false or not supplied then 
 * the array is returned as a regular, numerically indexed array.
 * If associative is set to true then
 * localtime returns an associative array containing
 * the elements of the structure returned by the C
 * function call to localtime. 
 * The keys of the associative array are as follows:
 * <p><br>
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
 * Positive if yes, 0 if not, negative if unknown.</p>
 */
function localtime (?int $timestamp = null, bool $associative = false): array {}

/**
 * Get date/time information
 * @link http://www.php.net/manual/en/function.getdate.php
 * @param int|null $timestamp [optional] 
 * @return array Returns an associative array of information related to
 * the timestamp. Elements from the returned 
 * associative array are as follows:
 * <p><table>
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
 * </table></p>
 */
function getdate (?int $timestamp = null): array {}

/**
 * create a new DateTime object
 * @link http://www.php.net/manual/en/function.date-create.php
 * @param string $datetime [optional] 
 * @param DateTimeZone|null $timezone [optional] 
 * @return DateTime|false Returns a new DateTime instance.
 * Procedural style returns false on failure.
 */
function date_create (string $datetime = '"now"', ?DateTimeZone $timezone = null): DateTime|false {}

/**
 * create a new DateTimeImmutable object
 * @link http://www.php.net/manual/en/function.date-create-immutable.php
 * @param string $datetime [optional] 
 * @param DateTimeZone|null $timezone [optional] 
 * @return DateTimeImmutable|false Returns a new DateTimeImmutable instance.
 * Procedural style returns false on failure.
 */
function date_create_immutable (string $datetime = '"now"', ?DateTimeZone $timezone = null): DateTimeImmutable|false {}

/**
 * Parses a time string according to a specified format
 * @link http://www.php.net/manual/en/datetime.createfromformat.php
 * @param string $format 
 * @param string $datetime 
 * @param DateTimeZone|null $timezone [optional] 
 * @return DateTime|false Returns a new DateTime instance or false on failure.
 */
function date_create_from_format (string $format, string $datetime, ?DateTimeZone $timezone = null): DateTime|false {}

/**
 * Parses a time string according to a specified format
 * @link http://www.php.net/manual/en/datetimeimmutable.createfromformat.php
 * @param string $format The format that the passed in string should be in. See the
 * formatting options below. In most cases, the same letters as for the
 * date can be used.
 * <p>All fields are initialised with the current date/time. In most cases you
 * would want to reset these to "zero" (the Unix epoch, 1970-01-01
 * 00:00:00 UTC). You do that by including the
 * ! character as first character in your
 * format, or | as your last.
 * Please see the documentation for each character below for more
 * information.</p>
 * <p>The format is parsed from left to right, which means that in some
 * situations the order in which the format characters are present affects
 * the result. In the case of z (the day of the year),
 * it is required that a year has already been parsed,
 * for example through the Y or y
 * characters.</p>
 * <p>Letters that are used for parsing numbers allow a wide range of values,
 * outside of what the logical range would be. For example, the
 * d (day of the month) accepts values in the range from
 * 00 to 99. The only constraint is
 * on the amount of digits. The date/time parser's overflow mechanism is
 * used when out-of-range values are given. The examples below show some of
 * this behaviour.</p>
 * <p>This also means that the data parsed for a format letter is greedy, and
 * will read up to the amount of digits its format allows for. That can
 * then also mean that there are no
 * longer enough characters in the datetime string
 * for following format characters. An example on this page also
 * illustrates this issue.</p>
 * <p><table>
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
 * 1 to 31. (2 digit numbers
 * higher than the number of days in the month are accepted, in which
 * case they will make the month overflow. For example using 33 with
 * January, means February 2nd)
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>D and l</td>
 * <td>A textual representation of a day</td>
 * <td>
 * Mon through Sun or
 * Sunday through Saturday. If
 * the day name given is different then the day name belonging to a
 * parsed (or default) date is different, then an overflow occurs to
 * the next date with the given day name. See the
 * examples below for an explanation.
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
 * <td>
 * The day of the year (starting from 0);
 * must be preceded by Y or y.
 * </td>
 * <td>
 * 0 through 365. (3 digit
 * numbers higher than the numbers in a year are accepted, in which
 * case they will make the year overflow. For example using 366 with
 * 2022, means January 2nd, 2023)
 * </td>
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
 * 1 through 12.
 * (2 digit numbers higher than 12 are accepted, in which case they
 * will make the year overflow. For example using 13 means January in
 * the next year)
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>Year</td>
 * <td>---</td>
 * <td>---</td>
 * </tr>
 * <tr valign="top">
 * <td>X and x</td>
 * <td>A full numeric representation of a year, up to 19 digits,
 * optionally prefixed by + or
 * -</td>
 * <td>Examples: 0055, 787,
 * 1999, -2003,
 * +10191</td>
 * </tr>
 * <tr valign="top">
 * <td>Y</td>
 * <td>A full numeric representation of a year, up to 4 digits</td>
 * <td>Examples: 0055, 787,
 * 1999, 2003</td>
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
 * 01 through 12 (2 digit
 * numbers higher than 12 are accepted, in which case they will make
 * the day overflow. For example using 14 means
 * 02 in the next AM/PM period)
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>G and H</td>
 * <td>24-hour format of an hour with or without leading zeros</td>
 * <td>
 * 0 through 23 or
 * 00 through 23 (2 digit
 * numbers higher than 24 are accepted, in which case they will make
 * the day overflow. For example using 26 means
 * 02:00 the next day)
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>i</td>
 * <td>Minutes with leading zeros</td>
 * <td>
 * 00 to 59. (2 digit
 * numbers higher than 59 are accepted, in which case they will make
 * the hour overflow. For example using 66 means
 * :06 the next hour)
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>s</td>
 * <td>Seconds, with leading zeros</td>
 * <td>
 * 00 through 59 (2 digit
 * numbers higher than 59 are accepted, in which case they will make
 * the minute overflow. For example using 90 means
 * :30 the next minute)
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>v</td>
 * <td>Fraction in milliseconds (up to three digits)</td>
 * <td>Example: 12 (0.12
 * seconds), 345 (0.345 seconds)</td>
 * </tr>
 * <tr valign="top">
 * <td>u</td>
 * <td>Fraction in microseconds (up to six digits)</td>
 * <td>Example: 45 (0.45
 * seconds), 654321 (0.654321
 * seconds)</td>
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
 * fraction and timezone information) to zero-like values (
 * 0 for hour, minute, second and fraction,
 * 1 for month and day, 1970
 * for year and UTC for timezone information)</td>
 * <td>Without !, all fields will be set to the
 * current date and time.</td>
 * </tr>
 * <tr valign="top">
 * <td>|</td>
 * <td>Resets all fields (year, month, day, hour, minute, second,
 * fraction and timezone information) to zero-like values if they have
 * not been parsed yet</td>
 * <td>Y-m-d| will set the year, month and day
 * to the information found in the string to parse, and sets the hour,
 * minute and second to 0.</td>
 * </tr>
 * <tr valign="top">
 * <td>+</td>
 * <td>If this format specifier is present, trailing data in the
 * string will not cause an error, but a warning instead</td>
 * <td>Use DateTimeImmutable::getLastErrors to find out
 * whether trailing data was present.</td>
 * </tr>
 * </table>
 * </table></p>
 * <p>Unrecognized characters in the format string will cause the
 * parsing to fail and an error message is appended to the returned
 * structure. You can query error messages with
 * DateTimeImmutable::getLastErrors.</p>
 * <p>To include literal characters in format, you have
 * to escape them with a backslash (\).</p>
 * <p>If format does not contain the character
 * ! then portions of the generated date/time which are not
 * specified in format will be set to the current
 * system time.</p>
 * <p>If format contains the
 * character !, then portions of the generated
 * date/time not provided in format, as well as
 * values to the left-hand side of the !, will
 * be set to corresponding values from the Unix epoch.</p>
 * <p>If any time character is parsed, then all other time-related fields are
 * set to "0", unless also parsed.</p>
 * <p>The Unix epoch is 1970-01-01 00:00:00 UTC.</p>
 * @param string $datetime String representing the time.
 * @param DateTimeZone|null $timezone [optional] A DateTimeZone object representing the
 * desired time zone.
 * <p>If timezone is omitted or null and
 * datetime contains no timezone,
 * the current timezone will be used.</p>
 * <p>The timezone parameter
 * and the current timezone are ignored when the
 * datetime parameter either
 * contains a UNIX timestamp (e.g. 946684800)
 * or specifies a timezone
 * (e.g. 2010-01-28T15:00:00+02:00).</p>
 * @return DateTimeImmutable|false Returns a new DateTimeImmutable instance or false on failure.
 */
function date_create_immutable_from_format (string $format, string $datetime, ?DateTimeZone $timezone = null): DateTimeImmutable|false {}

/**
 * Returns associative array with detailed info about given date/time
 * @link http://www.php.net/manual/en/function.date-parse.php
 * @param string $datetime 
 * @return array Returns array with information about the parsed date/time.
 * <p>The returned array has keys for year,
 * month, day, hour,
 * minute, second,
 * fraction, and is_localtime.</p>
 * <p>If is_localtime is present then
 * zone_type indicates the type of timezone. For type
 * 1 (UTC offset) the zone,
 * is_dst fields are added; for type 2
 * (abbreviation) the fields tz_abbr,
 * is_dst are added; and for type 3
 * (timezone identifier) the tz_abbr,
 * tz_id are added.</p>
 * <p>If relative time elements are present in the
 * datetime string such as +3 days,
 * the then returned array includes a nested array with the key
 * relative. This array then contains the keys
 * year, month, day,
 * hour, minute,
 * second, and if necessary weekday, and
 * weekdays, depending on the string that was passed in.</p>
 * <p>The array includes warning_count and
 * warnings fields. The first one indicate how many
 * warnings there were.
 * The keys of elements warnings array indicate the
 * position in the given datetime where the warning
 * occurred, with the string value describing the warning itself.</p>
 * <p>The array also contains error_count and
 * errors fields. The first one indicate how many errors
 * were found.
 * The keys of elements errors array indicate the
 * position in the given datetime where the error
 * occurred, with the string value describing the error itself.</p>
 * <p>The number of array elements in the warnings and
 * errors arrays might be less than
 * warning_count or error_count if they
 * occurred at the same position.</p>
 */
function date_parse (string $datetime): array {}

/**
 * Get info about given date formatted according to the specified format
 * @link http://www.php.net/manual/en/function.date-parse-from-format.php
 * @param string $format 
 * @param string $datetime 
 * @return array Returns associative array with detailed info about given date/time.
 * <p>The returned array has keys for year,
 * month, day, hour,
 * minute, second,
 * fraction, and is_localtime.</p>
 * <p>If is_localtime is present then
 * zone_type indicates the type of timezone. For type
 * 1 (UTC offset) the zone,
 * is_dst fields are added; for type 2
 * (abbreviation) the fields tz_abbr,
 * is_dst are added; and for type 3
 * (timezone identifier) the tz_abbr,
 * tz_id are added.</p>
 * <p>The array includes warning_count and
 * warnings fields. The first one indicate how many
 * warnings there were.
 * The keys of elements warnings array indicate the
 * position in the given datetime where the warning
 * occurred, with the string value describing the warning itself. An example
 * below shows such a warning.</p>
 * <p>The array also contains error_count and
 * errors fields. The first one indicate how many errors
 * were found.
 * The keys of elements errors array indicate the
 * position in the given datetime where the error
 * occurred, with the string value describing the error itself. An example
 * below shows such an error.</p>
 * <p>The number of array elements in the warnings and
 * errors arrays might be less than
 * warning_count or error_count if they
 * occurred at the same position.</p>
 */
function date_parse_from_format (string $format, string $datetime): array {}

/**
 * {@inheritdoc}
 */
function date_get_last_errors (): array|false {}

/**
 * Returns date formatted according to given format
 * @link http://www.php.net/manual/en/datetime.format.php
 * @param DateTimeInterface $object Procedural style only: A DateTime object
 * returned by date_create
 * @param string $format The format of the outputted date string. See the formatting
 * options below. There are also several
 * predefined date constants
 * that may be used instead, so for example DATE_RSS
 * contains the format string 'D, d M Y H:i:s'.
 * <p><table>
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
 * </table></p>
 * <p>Unrecognized characters in the format string will be printed
 * as-is. The Z format will always return
 * 0 when using gmdate.</p>
 * <p>Since this function only accepts int timestamps the
 * u format character is only useful when using the
 * date_format function with user based timestamps
 * created with date_create.</p>
 * @return string Returns the formatted date string on success.
 */
function date_format (DateTimeInterface $object, string $format): string {}

/**
 * Alters the timestamp
 * @link http://www.php.net/manual/en/datetime.modify.php
 * @param DateTime $object Procedural style only: A DateTime object
 * returned by date_create.
 * The function modifies this object.
 * @param string $modifier A date/time string. Valid formats are explained in Date and Time Formats.
 * @return DateTime|false Returns the modified DateTime object for method chaining or false on failure.
 */
function date_modify (DateTime $object, string $modifier): DateTime|false {}

/**
 * Modifies a DateTime object, with added amount of days, months, years, hours, minutes and seconds
 * @link http://www.php.net/manual/en/datetime.add.php
 * @param DateTime $object Procedural style only: A DateTime object
 * returned by date_create.
 * The function modifies this object.
 * @param DateInterval $interval A DateInterval object
 * @return DateTime Returns the modified DateTime object for method chaining.
 */
function date_add (DateTime $object, DateInterval $interval): DateTime {}

/**
 * Subtracts an amount of days, months, years, hours, minutes and seconds from
 * a DateTime object
 * @link http://www.php.net/manual/en/datetime.sub.php
 * @param DateTime $object Procedural style only: A DateTime object
 * returned by date_create.
 * The function modifies this object.
 * @param DateInterval $interval A DateInterval object
 * @return DateTime Returns the modified DateTime object for method chaining.
 */
function date_sub (DateTime $object, DateInterval $interval): DateTime {}

/**
 * Return time zone relative to given DateTime
 * @link http://www.php.net/manual/en/datetime.gettimezone.php
 * @param DateTimeInterface $object Procedural style only: A DateTime object
 * returned by date_create
 * @return DateTimeZone|false Returns a DateTimeZone object on success
 * or false on failure.
 */
function date_timezone_get (DateTimeInterface $object): DateTimeZone|false {}

/**
 * Sets the time zone for the DateTime object
 * @link http://www.php.net/manual/en/datetime.settimezone.php
 * @param DateTime $object Procedural style only: A DateTime object
 * returned by date_create.
 * The function modifies this object.
 * @param DateTimeZone $timezone A DateTimeZone object representing the
 * desired time zone.
 * @return DateTime Returns the DateTime object for method chaining. The
 * underlaying point-in-time is not changed when calling this method.
 */
function date_timezone_set (DateTime $object, DateTimeZone $timezone): DateTime {}

/**
 * Returns the timezone offset
 * @link http://www.php.net/manual/en/datetime.getoffset.php
 * @param DateTimeInterface $object Procedural style only: A DateTime object
 * returned by date_create
 * @return int Returns the timezone offset in seconds from UTC on success.
 */
function date_offset_get (DateTimeInterface $object): int {}

/**
 * Returns the difference between two DateTime objects
 * @link http://www.php.net/manual/en/datetime.diff.php
 * @param DateTimeInterface $baseObject 
 * @param DateTimeInterface $targetObject 
 * @param bool $absolute [optional] Should the interval be forced to be positive?
 * @return DateInterval The DateInterval object represents the
 * difference between the two dates.
 * <p>The return value more specifically represents the clock-time interval to
 * apply to the original object ($this or
 * $originObject) to arrive at the
 * $targetObject. This process is not always
 * reversible.</p>
 * <p>The method is aware of DST changeovers, and hence can return an interval of
 * 24 hours and 30 minutes, as per one of the examples. If
 * you want to calculate with absolute time, you need to convert both the
 * $this/$baseObject, and
 * $targetObject to UTC first.</p>
 */
function date_diff (DateTimeInterface $baseObject, DateTimeInterface $targetObject, bool $absolute = false): DateInterval {}

/**
 * Sets the time
 * @link http://www.php.net/manual/en/datetime.settime.php
 * @param DateTime $object Procedural style only: A DateTime object
 * returned by date_create.
 * The function modifies this object.
 * @param int $hour Hour of the time.
 * @param int $minute Minute of the time.
 * @param int $second [optional] Second of the time.
 * @param int $microsecond [optional] Microsecond of the time.
 * @return DateTime Returns the modified DateTime object for method chaining.
 */
function date_time_set (DateTime $object, int $hour, int $minute, int $second = null, int $microsecond = null): DateTime {}

/**
 * Sets the date
 * @link http://www.php.net/manual/en/datetime.setdate.php
 * @param DateTime $object Procedural style only: A DateTime object
 * returned by date_create.
 * The function modifies this object.
 * @param int $year Year of the date.
 * @param int $month Month of the date.
 * @param int $day Day of the date.
 * @return DateTime Returns the modified DateTime object for method chaining.
 */
function date_date_set (DateTime $object, int $year, int $month, int $day): DateTime {}

/**
 * Sets the ISO date
 * @link http://www.php.net/manual/en/datetime.setisodate.php
 * @param DateTime $object Procedural style only: A DateTime object
 * returned by date_create.
 * The function modifies this object.
 * @param int $year Year of the date.
 * @param int $week Week of the date.
 * @param int $dayOfWeek [optional] Offset from the first day of the week.
 * @return DateTime Returns the modified DateTime object for method chaining.
 */
function date_isodate_set (DateTime $object, int $year, int $week, int $dayOfWeek = 1): DateTime {}

/**
 * Sets the date and time based on an Unix timestamp
 * @link http://www.php.net/manual/en/datetime.settimestamp.php
 * @param DateTime $object Procedural style only: A DateTime object
 * returned by date_create.
 * The function modifies this object.
 * @param int $timestamp Unix timestamp representing the date.
 * Setting timestamps outside the range of int is possible by using
 * DateTimeImmutable::modify with the @ format.
 * @return DateTime Returns the modified DateTime object for method chaining.
 */
function date_timestamp_set (DateTime $object, int $timestamp): DateTime {}

/**
 * Gets the Unix timestamp
 * @link http://www.php.net/manual/en/datetime.gettimestamp.php
 * @param DateTimeInterface $object 
 * @return int Returns the Unix timestamp representing the date.
 */
function date_timestamp_get (DateTimeInterface $object): int {}

/**
 * Creates new DateTimeZone object
 * @link http://www.php.net/manual/en/datetimezone.construct.php
 * @param string $timezone 
 * @return DateTimeZone|false Returns DateTimeZone on success.
 * Procedural style returns false on failure.
 */
function timezone_open (string $timezone): DateTimeZone|false {}

/**
 * Returns the name of the timezone
 * @link http://www.php.net/manual/en/datetimezone.getname.php
 * @param DateTimeZone $object The DateTimeZone for which to get a name.
 * @return string Depending on zone type, UTC offset (type 1), timezone abbreviation (type
 * 2), and timezone identifiers as published in the IANA timezone database
 * (type 3), the descriptor string to create a new
 * DateTimeZone object with the same offset and/or
 * rules. For example 02:00, CEST, or
 * one of the timezone names in the <p>.
 */
function timezone_name_get (DateTimeZone $object): string {}

/**
 * Returns a timezone name by guessing from abbreviation and UTC offset
 * @link http://www.php.net/manual/en/function.timezone-name-from-abbr.php
 * @param string $abbr 
 * @param int $utcOffset [optional] 
 * @param int $isDST [optional] 
 * @return string|false Returns time zone name on success or false on failure.
 */
function timezone_name_from_abbr (string $abbr, int $utcOffset = -1, int $isDST = -1): string|false {}

/**
 * Returns the timezone offset from GMT
 * @link http://www.php.net/manual/en/datetimezone.getoffset.php
 * @param DateTimeZone $object 
 * @param DateTimeInterface $datetime 
 * @return int Returns time zone offset in seconds.
 */
function timezone_offset_get (DateTimeZone $object, DateTimeInterface $datetime): int {}

/**
 * Returns all transitions for the timezone
 * @link http://www.php.net/manual/en/datetimezone.gettransitions.php
 * @param DateTimeZone $object 
 * @param int $timestampBegin [optional] 
 * @param int $timestampEnd [optional] 
 * @return array|false Returns a numerically indexed array of
 * transition arrays on success, or false on failure. DateTimeZone
 * objects wrapping type 1 (UTC offsets) and type 2 (abbreviations) do not
 * contain any transitions, and calling this method on them will return
 * false.
 * <p>If timestampBegin is given, the first entry in the
 * returned array will contain a transition element at the time of
 * timestampBegin.</p>
 * <p><table>
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
 * </table></p>
 */
function timezone_transitions_get (DateTimeZone $object, int $timestampBegin = PHP_INT_MIN, int $timestampEnd = PHP_INT_MAX): array|false {}

/**
 * Returns location information for a timezone
 * @link http://www.php.net/manual/en/datetimezone.getlocation.php
 * @param DateTimeZone $object 
 * @return array|false Array containing location information about timezone or false on failure.
 */
function timezone_location_get (DateTimeZone $object): array|false {}

/**
 * Returns a numerically indexed array containing all defined timezone identifiers
 * @link http://www.php.net/manual/en/datetimezone.listidentifiers.php
 * @param int $timezoneGroup [optional] 
 * @param string|null $countryCode [optional] 
 * @return array Returns the array of timezone identifiers. Only non-outdated items are
 * returned. To get all, including outdated timezone identifiers, use the
 * DateTimeZone::ALL_WITH_BC as value for
 * timezoneGroup.
 */
function timezone_identifiers_list (int $timezoneGroup = \DateTimeZone::ALL, ?string $countryCode = null): array {}

/**
 * Returns associative array containing dst, offset and the timezone name
 * @link http://www.php.net/manual/en/datetimezone.listabbreviations.php
 * @return array Returns the array of timezone abbreviations.
 */
function timezone_abbreviations_list (): array {}

/**
 * Gets the version of the timezonedb
 * @link http://www.php.net/manual/en/function.timezone-version-get.php
 * @return string Returns a string in the format
 * YYYY.increment, such as 2022.2.
 * <p>If you have a timezone database version that is old (for example, it
 * doesn't show the current year), then you can update the timezone information
 * by either upgrading your PHP version, or installing the timezonedb PECL package.</p>
 * <p>Some Linux distributions patch PHP's date/time support to use an
 * alternative source for timezone information. In which case this function
 * will return 0.system. You are encouraged to install the
 * timezonedb PECL
 * package in that case as well.</p>
 */
function timezone_version_get (): string {}

/**
 * {@inheritdoc}
 * @param string $datetime
 */
function date_interval_create_from_date_string (string $datetime): DateInterval|false {}

/**
 * {@inheritdoc}
 * @param DateInterval $object
 * @param string $format
 */
function date_interval_format (DateInterval $object, string $format): string {}

/**
 * Sets the default timezone used by all date/time functions in a script
 * @link http://www.php.net/manual/en/function.date-default-timezone-set.php
 * @param string $timezoneId 
 * @return bool This function returns false if the
 * timezoneId isn't valid, or true
 * otherwise.
 */
function date_default_timezone_set (string $timezoneId): bool {}

/**
 * Gets the default timezone used by all date/time functions in a script
 * @link http://www.php.net/manual/en/function.date-default-timezone-get.php
 * @return string Returns a string.
 */
function date_default_timezone_get (): string {}

/**
 * Returns time of sunrise for a given day and location
 * @link http://www.php.net/manual/en/function.date-sunrise.php
 * @param int $timestamp 
 * @param int $returnFormat [optional] 
 * @param float|null $latitude [optional] 
 * @param float|null $longitude [optional] 
 * @param float|null $zenith [optional] 
 * @param float|null $utcOffset [optional] 
 * @return string|int|float|false Returns the sunrise time in a specified returnFormat on
 * success or false on failure. One potential reason for failure is that the
 * sun does not rise at all, which happens inside the polar circles for part of
 * the year.
 * @deprecated 1
 */
function date_sunrise (int $timestamp, int $returnFormat = SUNFUNCS_RET_STRING, ?float $latitude = null, ?float $longitude = null, ?float $zenith = null, ?float $utcOffset = null): string|int|float|false {}

/**
 * Returns time of sunset for a given day and location
 * @link http://www.php.net/manual/en/function.date-sunset.php
 * @param int $timestamp 
 * @param int $returnFormat [optional] 
 * @param float|null $latitude [optional] 
 * @param float|null $longitude [optional] 
 * @param float|null $zenith [optional] 
 * @param float|null $utcOffset [optional] 
 * @return string|int|float|false Returns the sunset time in a specified returnFormat on
 * success or false on failure. One potential reason for failure is that the
 * sun does not set at all, which happens inside the polar circles for part of
 * the year.
 * @deprecated 1
 */
function date_sunset (int $timestamp, int $returnFormat = SUNFUNCS_RET_STRING, ?float $latitude = null, ?float $longitude = null, ?float $zenith = null, ?float $utcOffset = null): string|int|float|false {}

/**
 * Returns an array with information about sunset/sunrise and twilight begin/end
 * @link http://www.php.net/manual/en/function.date-sun-info.php
 * @param int $timestamp 
 * @param float $latitude 
 * @param float $longitude 
 * @return array Returns array on success or false on failure.
 * The structure of the array is detailed in the following list:
 * <p>sunrise
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
 * nautical_twilight_end.</p>
 * <p>The values of the array elements are either UNIX timestamps, false if the
 * sun is below the respective zenith for the whole day, or true if the sun is
 * above the respective zenith for the whole day.</p>
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
 * @var int
 */
define ('SUNFUNCS_RET_TIMESTAMP', 0);

/**
 * Hours:minutes (example: 08:02)
 * @link http://www.php.net/manual/en/datetime.constants.php
 * @var int
 */
define ('SUNFUNCS_RET_STRING', 1);

/**
 * Hours as floating point number (example 8.75)
 * @link http://www.php.net/manual/en/datetime.constants.php
 * @var int
 */
define ('SUNFUNCS_RET_DOUBLE', 2);

// End of date v.8.2.6
