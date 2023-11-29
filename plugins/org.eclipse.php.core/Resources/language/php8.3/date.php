<?php

// Start of date v.8.3.0

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
	 * {@inheritdoc}
	 * @param string $format
	 */
	abstract public function format (string $format);

	/**
	 * {@inheritdoc}
	 */
	abstract public function getTimezone ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function getOffset ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function getTimestamp ();

	/**
	 * {@inheritdoc}
	 * @param DateTimeInterface $targetObject
	 * @param bool $absolute [optional]
	 */
	abstract public function diff (DateTimeInterface $targetObject, bool $absolute = false);

	/**
	 * {@inheritdoc}
	 */
	abstract public function __wakeup ();

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
	 * {@inheritdoc}
	 * @param string $datetime [optional]
	 * @param DateTimeZone|null $timezone [optional]
	 */
	public function __construct (string $datetime = 'now', ?DateTimeZone $timezone = NULL) {}

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
	 * @param DateTimeImmutable $object
	 */
	public static function createFromImmutable (DateTimeImmutable $object) {}

	/**
	 * {@inheritdoc}
	 * @param DateTimeInterface $object
	 */
	public static function createFromInterface (DateTimeInterface $object): DateTime {}

	/**
	 * {@inheritdoc}
	 * @param string $format
	 * @param string $datetime
	 * @param DateTimeZone|null $timezone [optional]
	 */
	public static function createFromFormat (string $format, string $datetime, ?DateTimeZone $timezone = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public static function getLastErrors () {}

	/**
	 * {@inheritdoc}
	 * @param string $format
	 */
	public function format (string $format) {}

	/**
	 * {@inheritdoc}
	 * @param string $modifier
	 */
	public function modify (string $modifier) {}

	/**
	 * {@inheritdoc}
	 * @param DateInterval $interval
	 */
	public function add (DateInterval $interval) {}

	/**
	 * {@inheritdoc}
	 * @param DateInterval $interval
	 */
	public function sub (DateInterval $interval) {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimezone () {}

	/**
	 * {@inheritdoc}
	 * @param DateTimeZone $timezone
	 */
	public function setTimezone (DateTimeZone $timezone) {}

	/**
	 * {@inheritdoc}
	 */
	public function getOffset () {}

	/**
	 * {@inheritdoc}
	 * @param int $hour
	 * @param int $minute
	 * @param int $second [optional]
	 * @param int $microsecond [optional]
	 */
	public function setTime (int $hour, int $minute, int $second = 0, int $microsecond = 0) {}

	/**
	 * {@inheritdoc}
	 * @param int $year
	 * @param int $month
	 * @param int $day
	 */
	public function setDate (int $year, int $month, int $day) {}

	/**
	 * {@inheritdoc}
	 * @param int $year
	 * @param int $week
	 * @param int $dayOfWeek [optional]
	 */
	public function setISODate (int $year, int $week, int $dayOfWeek = 1) {}

	/**
	 * {@inheritdoc}
	 * @param int $timestamp
	 */
	public function setTimestamp (int $timestamp) {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimestamp () {}

	/**
	 * {@inheritdoc}
	 * @param DateTimeInterface $targetObject
	 * @param bool $absolute [optional]
	 */
	public function diff (DateTimeInterface $targetObject, bool $absolute = false) {}

}

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
	 * {@inheritdoc}
	 * @param string $datetime [optional]
	 * @param DateTimeZone|null $timezone [optional]
	 */
	public function __construct (string $datetime = 'now', ?DateTimeZone $timezone = NULL) {}

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
	 * @param string $format
	 * @param string $datetime
	 * @param DateTimeZone|null $timezone [optional]
	 */
	public static function createFromFormat (string $format, string $datetime, ?DateTimeZone $timezone = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public static function getLastErrors () {}

	/**
	 * {@inheritdoc}
	 * @param string $format
	 */
	public function format (string $format) {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimezone () {}

	/**
	 * {@inheritdoc}
	 */
	public function getOffset () {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimestamp () {}

	/**
	 * {@inheritdoc}
	 * @param DateTimeInterface $targetObject
	 * @param bool $absolute [optional]
	 */
	public function diff (DateTimeInterface $targetObject, bool $absolute = false) {}

	/**
	 * {@inheritdoc}
	 * @param string $modifier
	 */
	public function modify (string $modifier) {}

	/**
	 * {@inheritdoc}
	 * @param DateInterval $interval
	 */
	public function add (DateInterval $interval) {}

	/**
	 * {@inheritdoc}
	 * @param DateInterval $interval
	 */
	public function sub (DateInterval $interval) {}

	/**
	 * {@inheritdoc}
	 * @param DateTimeZone $timezone
	 */
	public function setTimezone (DateTimeZone $timezone) {}

	/**
	 * {@inheritdoc}
	 * @param int $hour
	 * @param int $minute
	 * @param int $second [optional]
	 * @param int $microsecond [optional]
	 */
	public function setTime (int $hour, int $minute, int $second = 0, int $microsecond = 0) {}

	/**
	 * {@inheritdoc}
	 * @param int $year
	 * @param int $month
	 * @param int $day
	 */
	public function setDate (int $year, int $month, int $day) {}

	/**
	 * {@inheritdoc}
	 * @param int $year
	 * @param int $week
	 * @param int $dayOfWeek [optional]
	 */
	public function setISODate (int $year, int $week, int $dayOfWeek = 1) {}

	/**
	 * {@inheritdoc}
	 * @param int $timestamp
	 */
	public function setTimestamp (int $timestamp) {}

	/**
	 * {@inheritdoc}
	 * @param DateTime $object
	 */
	public static function createFromMutable (DateTime $object) {}

	/**
	 * {@inheritdoc}
	 * @param DateTimeInterface $object
	 */
	public static function createFromInterface (DateTimeInterface $object): DateTimeImmutable {}

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
	 * {@inheritdoc}
	 * @param string $timezone
	 */
	public function __construct (string $timezone) {}

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 * @param DateTimeInterface $datetime
	 */
	public function getOffset (DateTimeInterface $datetime) {}

	/**
	 * {@inheritdoc}
	 * @param int $timestampBegin [optional]
	 * @param int $timestampEnd [optional]
	 */
	public function getTransitions (int $timestampBegin = -9223372036854775807-1, int $timestampEnd = 9223372036854775807) {}

	/**
	 * {@inheritdoc}
	 */
	public function getLocation () {}

	/**
	 * {@inheritdoc}
	 */
	public static function listAbbreviations () {}

	/**
	 * {@inheritdoc}
	 * @param int $timezoneGroup [optional]
	 * @param string|null $countryCode [optional]
	 */
	public static function listIdentifiers (int $timezoneGroup = 2047, ?string $countryCode = NULL) {}

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

class DateInterval  {

	/**
	 * {@inheritdoc}
	 * @param string $duration
	 */
	public function __construct (string $duration) {}

	/**
	 * {@inheritdoc}
	 * @param string $datetime
	 */
	public static function createFromDateString (string $datetime) {}

	/**
	 * {@inheritdoc}
	 * @param string $format
	 */
	public function format (string $format) {}

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

class DatePeriod implements IteratorAggregate, Traversable {
	const EXCLUDE_START_DATE = 1;
	const INCLUDE_END_DATE = 2;


	public ?DateTimeInterface $start;

	public ?DateTimeInterface $current;

	public ?DateTimeInterface $end;

	public ?DateInterval $interval;

	public int $recurrences;

	public bool $include_start_date;

	public bool $include_end_date;

	/**
	 * {@inheritdoc}
	 * @param string $specification
	 * @param int $options [optional]
	 */
	public static function createFromISO8601String (string $specification, int $options = 0): static {}

	/**
	 * {@inheritdoc}
	 * @param mixed $start
	 * @param mixed $interval [optional]
	 * @param mixed $end [optional]
	 * @param mixed $options [optional]
	 */
	public function __construct ($start = null, $interval = NULL, $end = NULL, $options = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getStartDate () {}

	/**
	 * {@inheritdoc}
	 */
	public function getEndDate () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDateInterval () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRecurrences () {}

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

class DateError extends Error implements Throwable, Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class DateObjectError extends DateError implements Stringable, Throwable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class DateRangeError extends DateError implements Stringable, Throwable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class DateException extends Exception implements Throwable, Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class DateInvalidTimeZoneException extends DateException implements Stringable, Throwable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class DateInvalidOperationException extends DateException implements Stringable, Throwable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class DateMalformedStringException extends DateException implements Stringable, Throwable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class DateMalformedIntervalStringException extends DateException implements Stringable, Throwable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class DateMalformedPeriodStringException extends DateException implements Stringable, Throwable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

/**
 * {@inheritdoc}
 * @param string $datetime
 * @param int|null $baseTimestamp [optional]
 */
function strtotime (string $datetime, ?int $baseTimestamp = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param string $format
 * @param int|null $timestamp [optional]
 */
function date (string $format, ?int $timestamp = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $format
 * @param int|null $timestamp [optional]
 */
function idate (string $format, ?int $timestamp = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param string $format
 * @param int|null $timestamp [optional]
 */
function gmdate (string $format, ?int $timestamp = NULL): string {}

/**
 * {@inheritdoc}
 * @param int $hour
 * @param int|null $minute [optional]
 * @param int|null $second [optional]
 * @param int|null $month [optional]
 * @param int|null $day [optional]
 * @param int|null $year [optional]
 */
function mktime (int $hour, ?int $minute = NULL, ?int $second = NULL, ?int $month = NULL, ?int $day = NULL, ?int $year = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param int $hour
 * @param int|null $minute [optional]
 * @param int|null $second [optional]
 * @param int|null $month [optional]
 * @param int|null $day [optional]
 * @param int|null $year [optional]
 */
function gmmktime (int $hour, ?int $minute = NULL, ?int $second = NULL, ?int $month = NULL, ?int $day = NULL, ?int $year = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param int $month
 * @param int $day
 * @param int $year
 */
function checkdate (int $month, int $day, int $year): bool {}

/**
 * {@inheritdoc}
 * @param string $format
 * @param int|null $timestamp [optional]
 * @deprecated 
 */
function strftime (string $format, ?int $timestamp = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $format
 * @param int|null $timestamp [optional]
 * @deprecated 
 */
function gmstrftime (string $format, ?int $timestamp = NULL): string|false {}

/**
 * {@inheritdoc}
 */
function time (): int {}

/**
 * {@inheritdoc}
 * @param int|null $timestamp [optional]
 * @param bool $associative [optional]
 */
function localtime (?int $timestamp = NULL, bool $associative = false): array {}

/**
 * {@inheritdoc}
 * @param int|null $timestamp [optional]
 */
function getdate (?int $timestamp = NULL): array {}

/**
 * {@inheritdoc}
 * @param string $datetime [optional]
 * @param DateTimeZone|null $timezone [optional]
 */
function date_create (string $datetime = 'now', ?DateTimeZone $timezone = NULL): DateTime|false {}

/**
 * {@inheritdoc}
 * @param string $datetime [optional]
 * @param DateTimeZone|null $timezone [optional]
 */
function date_create_immutable (string $datetime = 'now', ?DateTimeZone $timezone = NULL): DateTimeImmutable|false {}

/**
 * {@inheritdoc}
 * @param string $format
 * @param string $datetime
 * @param DateTimeZone|null $timezone [optional]
 */
function date_create_from_format (string $format, string $datetime, ?DateTimeZone $timezone = NULL): DateTime|false {}

/**
 * {@inheritdoc}
 * @param string $format
 * @param string $datetime
 * @param DateTimeZone|null $timezone [optional]
 */
function date_create_immutable_from_format (string $format, string $datetime, ?DateTimeZone $timezone = NULL): DateTimeImmutable|false {}

/**
 * {@inheritdoc}
 * @param string $datetime
 */
function date_parse (string $datetime): array {}

/**
 * {@inheritdoc}
 * @param string $format
 * @param string $datetime
 */
function date_parse_from_format (string $format, string $datetime): array {}

/**
 * {@inheritdoc}
 */
function date_get_last_errors (): array|false {}

/**
 * {@inheritdoc}
 * @param DateTimeInterface $object
 * @param string $format
 */
function date_format (DateTimeInterface $object, string $format): string {}

/**
 * {@inheritdoc}
 * @param DateTime $object
 * @param string $modifier
 */
function date_modify (DateTime $object, string $modifier): DateTime|false {}

/**
 * {@inheritdoc}
 * @param DateTime $object
 * @param DateInterval $interval
 */
function date_add (DateTime $object, DateInterval $interval): DateTime {}

/**
 * {@inheritdoc}
 * @param DateTime $object
 * @param DateInterval $interval
 */
function date_sub (DateTime $object, DateInterval $interval): DateTime {}

/**
 * {@inheritdoc}
 * @param DateTimeInterface $object
 */
function date_timezone_get (DateTimeInterface $object): DateTimeZone|false {}

/**
 * {@inheritdoc}
 * @param DateTime $object
 * @param DateTimeZone $timezone
 */
function date_timezone_set (DateTime $object, DateTimeZone $timezone): DateTime {}

/**
 * {@inheritdoc}
 * @param DateTimeInterface $object
 */
function date_offset_get (DateTimeInterface $object): int {}

/**
 * {@inheritdoc}
 * @param DateTimeInterface $baseObject
 * @param DateTimeInterface $targetObject
 * @param bool $absolute [optional]
 */
function date_diff (DateTimeInterface $baseObject, DateTimeInterface $targetObject, bool $absolute = false): DateInterval {}

/**
 * {@inheritdoc}
 * @param DateTime $object
 * @param int $hour
 * @param int $minute
 * @param int $second [optional]
 * @param int $microsecond [optional]
 */
function date_time_set (DateTime $object, int $hour, int $minute, int $second = 0, int $microsecond = 0): DateTime {}

/**
 * {@inheritdoc}
 * @param DateTime $object
 * @param int $year
 * @param int $month
 * @param int $day
 */
function date_date_set (DateTime $object, int $year, int $month, int $day): DateTime {}

/**
 * {@inheritdoc}
 * @param DateTime $object
 * @param int $year
 * @param int $week
 * @param int $dayOfWeek [optional]
 */
function date_isodate_set (DateTime $object, int $year, int $week, int $dayOfWeek = 1): DateTime {}

/**
 * {@inheritdoc}
 * @param DateTime $object
 * @param int $timestamp
 */
function date_timestamp_set (DateTime $object, int $timestamp): DateTime {}

/**
 * {@inheritdoc}
 * @param DateTimeInterface $object
 */
function date_timestamp_get (DateTimeInterface $object): int {}

/**
 * {@inheritdoc}
 * @param string $timezone
 */
function timezone_open (string $timezone): DateTimeZone|false {}

/**
 * {@inheritdoc}
 * @param DateTimeZone $object
 */
function timezone_name_get (DateTimeZone $object): string {}

/**
 * {@inheritdoc}
 * @param string $abbr
 * @param int $utcOffset [optional]
 * @param int $isDST [optional]
 */
function timezone_name_from_abbr (string $abbr, int $utcOffset = -1, int $isDST = -1): string|false {}

/**
 * {@inheritdoc}
 * @param DateTimeZone $object
 * @param DateTimeInterface $datetime
 */
function timezone_offset_get (DateTimeZone $object, DateTimeInterface $datetime): int {}

/**
 * {@inheritdoc}
 * @param DateTimeZone $object
 * @param int $timestampBegin [optional]
 * @param int $timestampEnd [optional]
 */
function timezone_transitions_get (DateTimeZone $object, int $timestampBegin = -9223372036854775807-1, int $timestampEnd = 9223372036854775807): array|false {}

/**
 * {@inheritdoc}
 * @param DateTimeZone $object
 */
function timezone_location_get (DateTimeZone $object): array|false {}

/**
 * {@inheritdoc}
 * @param int $timezoneGroup [optional]
 * @param string|null $countryCode [optional]
 */
function timezone_identifiers_list (int $timezoneGroup = 2047, ?string $countryCode = NULL): array {}

/**
 * {@inheritdoc}
 */
function timezone_abbreviations_list (): array {}

/**
 * {@inheritdoc}
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
 * {@inheritdoc}
 * @param string $timezoneId
 */
function date_default_timezone_set (string $timezoneId): bool {}

/**
 * {@inheritdoc}
 */
function date_default_timezone_get (): string {}

/**
 * {@inheritdoc}
 * @param int $timestamp
 * @param int $returnFormat [optional]
 * @param float|null $latitude [optional]
 * @param float|null $longitude [optional]
 * @param float|null $zenith [optional]
 * @param float|null $utcOffset [optional]
 * @deprecated 
 */
function date_sunrise (int $timestamp, int $returnFormat = 1, ?float $latitude = NULL, ?float $longitude = NULL, ?float $zenith = NULL, ?float $utcOffset = NULL): string|int|float|false {}

/**
 * {@inheritdoc}
 * @param int $timestamp
 * @param int $returnFormat [optional]
 * @param float|null $latitude [optional]
 * @param float|null $longitude [optional]
 * @param float|null $zenith [optional]
 * @param float|null $utcOffset [optional]
 * @deprecated 
 */
function date_sunset (int $timestamp, int $returnFormat = 1, ?float $latitude = NULL, ?float $longitude = NULL, ?float $zenith = NULL, ?float $utcOffset = NULL): string|int|float|false {}

/**
 * {@inheritdoc}
 * @param int $timestamp
 * @param float $latitude
 * @param float $longitude
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
define ('SUNFUNCS_RET_TIMESTAMP', 0);
define ('SUNFUNCS_RET_STRING', 1);
define ('SUNFUNCS_RET_DOUBLE', 2);

// End of date v.8.3.0
