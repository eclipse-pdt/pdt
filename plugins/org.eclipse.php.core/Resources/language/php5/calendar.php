<?php

// Start of calendar v.

/**
 * Converts Julian Day Count to Gregorian date
 * @link http://php.net/manual/en/function.jdtogregorian.php
 * @param julianday int
 * @return string 
 */
function jdtogregorian ($julianday) {}

/**
 * Converts a Gregorian date to Julian Day Count
 * @link http://php.net/manual/en/function.gregoriantojd.php
 * @param month int
 * @param day int
 * @param year int
 * @return int 
 */
function gregoriantojd ($month, $day, $year) {}

/**
 * Converts a Julian Day Count to a Julian Calendar Date
 * @link http://php.net/manual/en/function.jdtojulian.php
 * @param julianday int
 * @return string 
 */
function jdtojulian ($julianday) {}

/**
 * Converts a Julian Calendar date to Julian Day Count
 * @link http://php.net/manual/en/function.juliantojd.php
 * @param month int
 * @param day int
 * @param year int
 * @return int 
 */
function juliantojd ($month, $day, $year) {}

/**
 * Converts a Julian day count to a Jewish calendar date
 * @link http://php.net/manual/en/function.jdtojewish.php
 * @param juliandaycount int
 * @param hebrew bool[optional]
 * @param fl int[optional]
 * @return string 
 */
function jdtojewish ($juliandaycount, $hebrew = null, $fl = null) {}

/**
 * Converts a date in the Jewish Calendar to Julian Day Count
 * @link http://php.net/manual/en/function.jewishtojd.php
 * @param month int
 * @param day int
 * @param year int
 * @return int 
 */
function jewishtojd ($month, $day, $year) {}

/**
 * Converts a Julian Day Count to the French Republican Calendar
 * @link http://php.net/manual/en/function.jdtofrench.php
 * @param juliandaycount int
 * @return string 
 */
function jdtofrench ($juliandaycount) {}

/**
 * Converts a date from the French Republican Calendar to a Julian Day Count
 * @link http://php.net/manual/en/function.frenchtojd.php
 * @param month int
 * @param day int
 * @param year int
 * @return int 
 */
function frenchtojd ($month, $day, $year) {}

/**
 * Returns the day of the week
 * @link http://php.net/manual/en/function.jddayofweek.php
 * @param julianday int
 * @param mode int[optional]
 * @return mixed 
 */
function jddayofweek ($julianday, $mode = null) {}

/**
 * Returns a month name
 * @link http://php.net/manual/en/function.jdmonthname.php
 * @param julianday int
 * @param mode int
 * @return string 
 */
function jdmonthname ($julianday, $mode) {}

/**
 * Get Unix timestamp for midnight on Easter of a given year
 * @link http://php.net/manual/en/function.easter-date.php
 * @param year int[optional]
 * @return int 
 */
function easter_date ($year = null) {}

/**
 * Get number of days after March 21 on which Easter falls for a given year
 * @link http://php.net/manual/en/function.easter-days.php
 * @param year int[optional]
 * @param method int[optional]
 * @return int 
 */
function easter_days ($year = null, $method = null) {}

/**
 * Convert Unix timestamp to Julian Day
 * @link http://php.net/manual/en/function.unixtojd.php
 * @param timestamp int[optional]
 * @return int 
 */
function unixtojd ($timestamp = null) {}

/**
 * Convert Julian Day to Unix timestamp
 * @link http://php.net/manual/en/function.jdtounix.php
 * @param jday int
 * @return int 
 */
function jdtounix ($jday) {}

/**
 * Converts from a supported calendar to Julian Day Count
 * @link http://php.net/manual/en/function.cal-to-jd.php
 * @param calendar int
 * @param month int
 * @param day int
 * @param year int
 * @return int 
 */
function cal_to_jd ($calendar, $month, $day, $year) {}

/**
 * Converts from Julian Day Count to a supported calendar
 * @link http://php.net/manual/en/function.cal-from-jd.php
 * @param jd int
 * @param calendar int
 * @return array an array containing calendar information like month, day, year,
 */
function cal_from_jd ($jd, $calendar) {}

/**
 * Return the number of days in a month for a given year and calendar
 * @link http://php.net/manual/en/function.cal-days-in-month.php
 * @param calendar int
 * @param month int
 * @param year int
 * @return int 
 */
function cal_days_in_month ($calendar, $month, $year) {}

/**
 * Returns information about a particular calendar
 * @link http://php.net/manual/en/function.cal-info.php
 * @param calendar int[optional]
 * @return array 
 */
function cal_info ($calendar = null) {}

define ('CAL_GREGORIAN', 0);
define ('CAL_JULIAN', 1);
define ('CAL_JEWISH', 2);
define ('CAL_FRENCH', 3);
define ('CAL_NUM_CALS', 4);
define ('CAL_DOW_DAYNO', 0);
define ('CAL_DOW_SHORT', 1);
define ('CAL_DOW_LONG', 2);
define ('CAL_MONTH_GREGORIAN_SHORT', 0);
define ('CAL_MONTH_GREGORIAN_LONG', 1);
define ('CAL_MONTH_JULIAN_SHORT', 2);
define ('CAL_MONTH_JULIAN_LONG', 3);
define ('CAL_MONTH_JEWISH', 4);
define ('CAL_MONTH_FRENCH', 5);
define ('CAL_EASTER_DEFAULT', 0);
define ('CAL_EASTER_ROMAN', 1);
define ('CAL_EASTER_ALWAYS_GREGORIAN', 2);
define ('CAL_EASTER_ALWAYS_JULIAN', 3);
define ('CAL_JEWISH_ADD_ALAFIM_GERESH', 2);
define ('CAL_JEWISH_ADD_ALAFIM', 4);
define ('CAL_JEWISH_ADD_GERESHAYIM', 8);

// End of calendar v.
?>
