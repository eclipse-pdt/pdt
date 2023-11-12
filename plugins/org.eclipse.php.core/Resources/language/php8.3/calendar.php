<?php

// Start of calendar v.8.2.6

/**
 * Return the number of days in a month for a given year and calendar
 * @link http://www.php.net/manual/en/function.cal-days-in-month.php
 * @param int $calendar 
 * @param int $month 
 * @param int $year 
 * @return int The length in days of the selected month in the given calendar
 */
function cal_days_in_month (int $calendar, int $month, int $year): int {}

/**
 * Converts from Julian Day Count to a supported calendar
 * @link http://www.php.net/manual/en/function.cal-from-jd.php
 * @param int $julian_day 
 * @param int $calendar 
 * @return array Returns an array containing calendar information like month, day, year,
 * day of week (dow), abbreviated and full names of weekday and month and the
 * date in string form "month/day/year".
 * The day of week ranges from 0 (Sunday) to
 * 6 (Saturday).
 */
function cal_from_jd (int $julian_day, int $calendar): array {}

/**
 * Returns information about a particular calendar
 * @link http://www.php.net/manual/en/function.cal-info.php
 * @param int $calendar [optional] 
 * @return array 
 */
function cal_info (int $calendar = -1): array {}

/**
 * Converts from a supported calendar to Julian Day Count
 * @link http://www.php.net/manual/en/function.cal-to-jd.php
 * @param int $calendar 
 * @param int $month 
 * @param int $day 
 * @param int $year 
 * @return int A Julian Day number.
 */
function cal_to_jd (int $calendar, int $month, int $day, int $year): int {}

/**
 * Get Unix timestamp for midnight on Easter of a given year
 * @link http://www.php.net/manual/en/function.easter-date.php
 * @param int|null $year [optional] 
 * @param int $mode [optional] 
 * @return int The easter date as a unix timestamp.
 */
function easter_date (?int $year = null, int $mode = CAL_EASTER_DEFAULT): int {}

/**
 * Get number of days after March 21 on which Easter falls for a given year
 * @link http://www.php.net/manual/en/function.easter-days.php
 * @param int|null $year [optional] 
 * @param int $mode [optional] 
 * @return int The number of days after March 21st that the Easter Sunday
 * is in the given year.
 */
function easter_days (?int $year = null, int $mode = CAL_EASTER_DEFAULT): int {}

/**
 * Converts a date from the French Republican Calendar to a Julian Day Count
 * @link http://www.php.net/manual/en/function.frenchtojd.php
 * @param int $month 
 * @param int $day 
 * @param int $year 
 * @return int The julian day for the given french revolution date as an integer.
 */
function frenchtojd (int $month, int $day, int $year): int {}

/**
 * Converts a Gregorian date to Julian Day Count
 * @link http://www.php.net/manual/en/function.gregoriantojd.php
 * @param int $month 
 * @param int $day 
 * @param int $year 
 * @return int The julian day for the given gregorian date as an integer.
 * Dates outside the valid range return 0.
 */
function gregoriantojd (int $month, int $day, int $year): int {}

/**
 * Returns the day of the week
 * @link http://www.php.net/manual/en/function.jddayofweek.php
 * @param int $julian_day 
 * @param int $mode [optional] 
 * @return int|string The gregorian weekday as either an integer or string.
 */
function jddayofweek (int $julian_day, int $mode = CAL_DOW_DAYNO): int|string {}

/**
 * Returns a month name
 * @link http://www.php.net/manual/en/function.jdmonthname.php
 * @param int $julian_day 
 * @param int $mode 
 * @return string The month name for the given Julian Day and mode.
 */
function jdmonthname (int $julian_day, int $mode): string {}

/**
 * Converts a Julian Day Count to the French Republican Calendar
 * @link http://www.php.net/manual/en/function.jdtofrench.php
 * @param int $julian_day 
 * @return string The french revolution date as a string in the form "month/day/year"
 */
function jdtofrench (int $julian_day): string {}

/**
 * Converts Julian Day Count to Gregorian date
 * @link http://www.php.net/manual/en/function.jdtogregorian.php
 * @param int $julian_day 
 * @return string The gregorian date as a string in the form "month/day/year"
 */
function jdtogregorian (int $julian_day): string {}

/**
 * Converts a Julian day count to a Jewish calendar date
 * @link http://www.php.net/manual/en/function.jdtojewish.php
 * @param int $julian_day 
 * @param bool $hebrew [optional] 
 * @param int $flags [optional] 
 * @return string The Jewish date as a string in the form "month/day/year", or an ISO-8859-8
 * encoded Hebrew date string, according to the hebrew
 * parameter.
 */
function jdtojewish (int $julian_day, bool $hebrew = false, int $flags = null): string {}

/**
 * Converts a Julian Day Count to a Julian Calendar Date
 * @link http://www.php.net/manual/en/function.jdtojulian.php
 * @param int $julian_day 
 * @return string The julian date as a string in the form "month/day/year"
 */
function jdtojulian (int $julian_day): string {}

/**
 * Convert Julian Day to Unix timestamp
 * @link http://www.php.net/manual/en/function.jdtounix.php
 * @param int $julian_day 
 * @return int The unix timestamp for the start (midnight, not noon) of the given Julian day
 */
function jdtounix (int $julian_day): int {}

/**
 * Converts a date in the Jewish Calendar to Julian Day Count
 * @link http://www.php.net/manual/en/function.jewishtojd.php
 * @param int $month 
 * @param int $day 
 * @param int $year 
 * @return int The julian day for the given jewish date as an integer.
 */
function jewishtojd (int $month, int $day, int $year): int {}

/**
 * Converts a Julian Calendar date to Julian Day Count
 * @link http://www.php.net/manual/en/function.juliantojd.php
 * @param int $month 
 * @param int $day 
 * @param int $year 
 * @return int The julian day for the given julian date as an integer.
 */
function juliantojd (int $month, int $day, int $year): int {}

/**
 * Convert Unix timestamp to Julian Day
 * @link http://www.php.net/manual/en/function.unixtojd.php
 * @param int|null $timestamp [optional] 
 * @return int|false A julian day number as integer, or false on failure.
 */
function unixtojd (?int $timestamp = null): int|false {}


/**
 * For cal_days_in_month,
 * cal_from_jd, cal_info and
 * cal_to_jd: use the proleptic Gregorian calendar.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_GREGORIAN', 0);

/**
 * For cal_days_in_month,
 * cal_from_jd, cal_info and
 * cal_to_jd: use the Julian calendar.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_JULIAN', 1);

/**
 * For cal_days_in_month,
 * cal_from_jd, cal_info and
 * cal_to_jd: use the Jewish calendar.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_JEWISH', 2);

/**
 * For cal_days_in_month,
 * cal_from_jd, cal_info and
 * cal_to_jd: use the French Repuclican calendar.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_FRENCH', 3);

/**
 * The number of available calendars.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_NUM_CALS', 4);

/**
 * For jddayofweek: the day of the week as
 * int, where 0 means Sunday and
 * 6 means Saturday.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_DOW_DAYNO', 0);

/**
 * For jddayofweek: the abbreviated English name of the
 * day of the week.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_DOW_SHORT', 2);

/**
 * For jddayofweek: the English name of the day of the
 * week.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_DOW_LONG', 1);

/**
 * For jdmonthname: the abbreviated Gregorian month name.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_MONTH_GREGORIAN_SHORT', 0);

/**
 * For jdmonthname: the Gregorian month name.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_MONTH_GREGORIAN_LONG', 1);

/**
 * For jdmonthname: the abbreviated Julian month name.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_MONTH_JULIAN_SHORT', 2);

/**
 * For jdmonthname: the Julian month name.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_MONTH_JULIAN_LONG', 3);

/**
 * For jdmonthname: the Jewish month name.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_MONTH_JEWISH', 4);

/**
 * For jdmonthname: the French Republican month name.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_MONTH_FRENCH', 5);

/**
 * For easter_days: calculate Easter for years before
 * 1753 according to the Julian calendar, and for later years according to the
 * Gregorian calendar.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_EASTER_DEFAULT', 0);

/**
 * For easter_days: calculate Easter for years before
 * 1583 according to the Julian calendar, and for later years according to the
 * Gregorian calendar.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_EASTER_ROMAN', 1);

/**
 * For easter_days: calculate Easter according to the
 * proleptic Gregorian calendar.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_EASTER_ALWAYS_GREGORIAN', 2);

/**
 * For easter_days: calculate Easter according to the
 * Julian calendar.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_EASTER_ALWAYS_JULIAN', 3);

/**
 * For jdtojewish: adds a geresh symbol (which resembles
 * a single-quote mark) as thousands separator to the year number.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_JEWISH_ADD_ALAFIM_GERESH', 2);

/**
 * For jdtojewish: adds the word alafim as thousands
 * separator to the year number.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_JEWISH_ADD_ALAFIM', 4);

/**
 * For jdtojewish: add a gershayim symbol (which
 * resembles a double-quote mark) before the final letter of the day and year numbers.
 * @link http://www.php.net/manual/en/calendar.constants.php
 * @var int
 */
define ('CAL_JEWISH_ADD_GERESHAYIM', 8);

// End of calendar v.8.2.6
