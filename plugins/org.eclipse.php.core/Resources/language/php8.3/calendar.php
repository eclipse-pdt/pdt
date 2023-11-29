<?php

// Start of calendar v.8.3.0

/**
 * {@inheritdoc}
 * @param int $calendar
 * @param int $month
 * @param int $year
 */
function cal_days_in_month (int $calendar, int $month, int $year): int {}

/**
 * {@inheritdoc}
 * @param int $julian_day
 * @param int $calendar
 */
function cal_from_jd (int $julian_day, int $calendar): array {}

/**
 * {@inheritdoc}
 * @param int $calendar [optional]
 */
function cal_info (int $calendar = -1): array {}

/**
 * {@inheritdoc}
 * @param int $calendar
 * @param int $month
 * @param int $day
 * @param int $year
 */
function cal_to_jd (int $calendar, int $month, int $day, int $year): int {}

/**
 * {@inheritdoc}
 * @param int|null $year [optional]
 * @param int $mode [optional]
 */
function easter_date (?int $year = NULL, int $mode = 0): int {}

/**
 * {@inheritdoc}
 * @param int|null $year [optional]
 * @param int $mode [optional]
 */
function easter_days (?int $year = NULL, int $mode = 0): int {}

/**
 * {@inheritdoc}
 * @param int $month
 * @param int $day
 * @param int $year
 */
function frenchtojd (int $month, int $day, int $year): int {}

/**
 * {@inheritdoc}
 * @param int $month
 * @param int $day
 * @param int $year
 */
function gregoriantojd (int $month, int $day, int $year): int {}

/**
 * {@inheritdoc}
 * @param int $julian_day
 * @param int $mode [optional]
 */
function jddayofweek (int $julian_day, int $mode = 0): string|int {}

/**
 * {@inheritdoc}
 * @param int $julian_day
 * @param int $mode
 */
function jdmonthname (int $julian_day, int $mode): string {}

/**
 * {@inheritdoc}
 * @param int $julian_day
 */
function jdtofrench (int $julian_day): string {}

/**
 * {@inheritdoc}
 * @param int $julian_day
 */
function jdtogregorian (int $julian_day): string {}

/**
 * {@inheritdoc}
 * @param int $julian_day
 * @param bool $hebrew [optional]
 * @param int $flags [optional]
 */
function jdtojewish (int $julian_day, bool $hebrew = false, int $flags = 0): string {}

/**
 * {@inheritdoc}
 * @param int $julian_day
 */
function jdtojulian (int $julian_day): string {}

/**
 * {@inheritdoc}
 * @param int $julian_day
 */
function jdtounix (int $julian_day): int {}

/**
 * {@inheritdoc}
 * @param int $month
 * @param int $day
 * @param int $year
 */
function jewishtojd (int $month, int $day, int $year): int {}

/**
 * {@inheritdoc}
 * @param int $month
 * @param int $day
 * @param int $year
 */
function juliantojd (int $month, int $day, int $year): int {}

/**
 * {@inheritdoc}
 * @param int|null $timestamp [optional]
 */
function unixtojd (?int $timestamp = NULL): int|false {}

define ('CAL_GREGORIAN', 0);
define ('CAL_JULIAN', 1);
define ('CAL_JEWISH', 2);
define ('CAL_FRENCH', 3);
define ('CAL_NUM_CALS', 4);
define ('CAL_DOW_DAYNO', 0);
define ('CAL_DOW_SHORT', 2);
define ('CAL_DOW_LONG', 1);
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

// End of calendar v.8.3.0
