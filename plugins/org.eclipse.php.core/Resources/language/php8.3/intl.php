<?php

// Start of intl v.8.3.0

class Collator  {
	const DEFAULT_VALUE = -1;
	const PRIMARY = 0;
	const SECONDARY = 1;
	const TERTIARY = 2;
	const DEFAULT_STRENGTH = 2;
	const QUATERNARY = 3;
	const IDENTICAL = 15;
	const OFF = 16;
	const ON = 17;
	const SHIFTED = 20;
	const NON_IGNORABLE = 21;
	const LOWER_FIRST = 24;
	const UPPER_FIRST = 25;
	const FRENCH_COLLATION = 0;
	const ALTERNATE_HANDLING = 1;
	const CASE_FIRST = 2;
	const CASE_LEVEL = 3;
	const NORMALIZATION_MODE = 4;
	const STRENGTH = 5;
	const HIRAGANA_QUATERNARY_MODE = 6;
	const NUMERIC_COLLATION = 7;
	const SORT_REGULAR = 0;
	const SORT_STRING = 1;
	const SORT_NUMERIC = 2;


	/**
	 * {@inheritdoc}
	 * @param string $locale
	 */
	public function __construct (string $locale) {}

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 */
	public static function create (string $locale) {}

	/**
	 * {@inheritdoc}
	 * @param string $string1
	 * @param string $string2
	 */
	public function compare (string $string1, string $string2) {}

	/**
	 * {@inheritdoc}
	 * @param array $array
	 * @param int $flags [optional]
	 */
	public function sort (array &$array, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param array $array
	 */
	public function sortWithSortKeys (array &$array) {}

	/**
	 * {@inheritdoc}
	 * @param array $array
	 * @param int $flags [optional]
	 */
	public function asort (array &$array, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param int $attribute
	 */
	public function getAttribute (int $attribute) {}

	/**
	 * {@inheritdoc}
	 * @param int $attribute
	 * @param int $value
	 */
	public function setAttribute (int $attribute, int $value) {}

	/**
	 * {@inheritdoc}
	 */
	public function getStrength () {}

	/**
	 * {@inheritdoc}
	 * @param int $strength
	 */
	public function setStrength (int $strength) {}

	/**
	 * {@inheritdoc}
	 * @param int $type
	 */
	public function getLocale (int $type) {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorCode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorMessage () {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 */
	public function getSortKey (string $string) {}

}

class NumberFormatter  {
	const PATTERN_DECIMAL = 0;
	const DECIMAL = 1;
	const CURRENCY = 2;
	const PERCENT = 3;
	const SCIENTIFIC = 4;
	const SPELLOUT = 5;
	const ORDINAL = 6;
	const DURATION = 7;
	const PATTERN_RULEBASED = 9;
	const IGNORE = 0;
	const CURRENCY_ACCOUNTING = 12;
	const DEFAULT_STYLE = 1;
	const ROUND_CEILING = 0;
	const ROUND_FLOOR = 1;
	const ROUND_DOWN = 2;
	const ROUND_UP = 3;
	const ROUND_HALFEVEN = 4;
	const ROUND_HALFDOWN = 5;
	const ROUND_HALFUP = 6;
	const PAD_BEFORE_PREFIX = 0;
	const PAD_AFTER_PREFIX = 1;
	const PAD_BEFORE_SUFFIX = 2;
	const PAD_AFTER_SUFFIX = 3;
	const PARSE_INT_ONLY = 0;
	const GROUPING_USED = 1;
	const DECIMAL_ALWAYS_SHOWN = 2;
	const MAX_INTEGER_DIGITS = 3;
	const MIN_INTEGER_DIGITS = 4;
	const INTEGER_DIGITS = 5;
	const MAX_FRACTION_DIGITS = 6;
	const MIN_FRACTION_DIGITS = 7;
	const FRACTION_DIGITS = 8;
	const MULTIPLIER = 9;
	const GROUPING_SIZE = 10;
	const ROUNDING_MODE = 11;
	const ROUNDING_INCREMENT = 12;
	const FORMAT_WIDTH = 13;
	const PADDING_POSITION = 14;
	const SECONDARY_GROUPING_SIZE = 15;
	const SIGNIFICANT_DIGITS_USED = 16;
	const MIN_SIGNIFICANT_DIGITS = 17;
	const MAX_SIGNIFICANT_DIGITS = 18;
	const LENIENT_PARSE = 19;
	const POSITIVE_PREFIX = 0;
	const POSITIVE_SUFFIX = 1;
	const NEGATIVE_PREFIX = 2;
	const NEGATIVE_SUFFIX = 3;
	const PADDING_CHARACTER = 4;
	const CURRENCY_CODE = 5;
	const DEFAULT_RULESET = 6;
	const PUBLIC_RULESETS = 7;
	const DECIMAL_SEPARATOR_SYMBOL = 0;
	const GROUPING_SEPARATOR_SYMBOL = 1;
	const PATTERN_SEPARATOR_SYMBOL = 2;
	const PERCENT_SYMBOL = 3;
	const ZERO_DIGIT_SYMBOL = 4;
	const DIGIT_SYMBOL = 5;
	const MINUS_SIGN_SYMBOL = 6;
	const PLUS_SIGN_SYMBOL = 7;
	const CURRENCY_SYMBOL = 8;
	const INTL_CURRENCY_SYMBOL = 9;
	const MONETARY_SEPARATOR_SYMBOL = 10;
	const EXPONENTIAL_SYMBOL = 11;
	const PERMILL_SYMBOL = 12;
	const PAD_ESCAPE_SYMBOL = 13;
	const INFINITY_SYMBOL = 14;
	const NAN_SYMBOL = 15;
	const SIGNIFICANT_DIGIT_SYMBOL = 16;
	const MONETARY_GROUPING_SEPARATOR_SYMBOL = 17;
	const TYPE_DEFAULT = 0;
	const TYPE_INT32 = 1;
	const TYPE_INT64 = 2;
	const TYPE_DOUBLE = 3;
	const TYPE_CURRENCY = 4;


	/**
	 * {@inheritdoc}
	 * @param string $locale
	 * @param int $style
	 * @param string|null $pattern [optional]
	 */
	public function __construct (string $locale, int $style, ?string $pattern = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 * @param int $style
	 * @param string|null $pattern [optional]
	 */
	public static function create (string $locale, int $style, ?string $pattern = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int|float $num
	 * @param int $type [optional]
	 */
	public function format (int|float $num, int $type = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 * @param int $type [optional]
	 * @param mixed $offset [optional]
	 */
	public function parse (string $string, int $type = 3, &$offset = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param float $amount
	 * @param string $currency
	 */
	public function formatCurrency (float $amount, string $currency) {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 * @param mixed $currency
	 * @param mixed $offset [optional]
	 */
	public function parseCurrency (string $string, &$currency = null, &$offset = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $attribute
	 * @param int|float $value
	 */
	public function setAttribute (int $attribute, int|float $value) {}

	/**
	 * {@inheritdoc}
	 * @param int $attribute
	 */
	public function getAttribute (int $attribute) {}

	/**
	 * {@inheritdoc}
	 * @param int $attribute
	 * @param string $value
	 */
	public function setTextAttribute (int $attribute, string $value) {}

	/**
	 * {@inheritdoc}
	 * @param int $attribute
	 */
	public function getTextAttribute (int $attribute) {}

	/**
	 * {@inheritdoc}
	 * @param int $symbol
	 * @param string $value
	 */
	public function setSymbol (int $symbol, string $value) {}

	/**
	 * {@inheritdoc}
	 * @param int $symbol
	 */
	public function getSymbol (int $symbol) {}

	/**
	 * {@inheritdoc}
	 * @param string $pattern
	 */
	public function setPattern (string $pattern) {}

	/**
	 * {@inheritdoc}
	 */
	public function getPattern () {}

	/**
	 * {@inheritdoc}
	 * @param int $type [optional]
	 */
	public function getLocale (int $type = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorCode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorMessage () {}

}

class Normalizer  {
	const FORM_D = 4;
	const NFD = 4;
	const FORM_KD = 8;
	const NFKD = 8;
	const FORM_C = 16;
	const NFC = 16;
	const FORM_KC = 32;
	const NFKC = 32;
	const FORM_KC_CF = 48;
	const NFKC_CF = 48;


	/**
	 * {@inheritdoc}
	 * @param string $string
	 * @param int $form [optional]
	 */
	public static function normalize (string $string, int $form = 16) {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 * @param int $form [optional]
	 */
	public static function isNormalized (string $string, int $form = 16) {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 * @param int $form [optional]
	 */
	public static function getRawDecomposition (string $string, int $form = 16) {}

}

class Locale  {
	const ACTUAL_LOCALE = 0;
	const VALID_LOCALE = 1;
	const DEFAULT_LOCALE = null;
	const LANG_TAG = "language";
	const EXTLANG_TAG = "extlang";
	const SCRIPT_TAG = "script";
	const REGION_TAG = "region";
	const VARIANT_TAG = "variant";
	const GRANDFATHERED_LANG_TAG = "grandfathered";
	const PRIVATE_TAG = "private";


	/**
	 * {@inheritdoc}
	 */
	public static function getDefault () {}

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 */
	public static function setDefault (string $locale) {}

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 */
	public static function getPrimaryLanguage (string $locale) {}

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 */
	public static function getScript (string $locale) {}

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 */
	public static function getRegion (string $locale) {}

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 */
	public static function getKeywords (string $locale) {}

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 * @param string|null $displayLocale [optional]
	 */
	public static function getDisplayScript (string $locale, ?string $displayLocale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 * @param string|null $displayLocale [optional]
	 */
	public static function getDisplayRegion (string $locale, ?string $displayLocale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 * @param string|null $displayLocale [optional]
	 */
	public static function getDisplayName (string $locale, ?string $displayLocale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 * @param string|null $displayLocale [optional]
	 */
	public static function getDisplayLanguage (string $locale, ?string $displayLocale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 * @param string|null $displayLocale [optional]
	 */
	public static function getDisplayVariant (string $locale, ?string $displayLocale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param array $subtags
	 */
	public static function composeLocale (array $subtags) {}

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 */
	public static function parseLocale (string $locale) {}

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 */
	public static function getAllVariants (string $locale) {}

	/**
	 * {@inheritdoc}
	 * @param string $languageTag
	 * @param string $locale
	 * @param bool $canonicalize [optional]
	 */
	public static function filterMatches (string $languageTag, string $locale, bool $canonicalize = false) {}

	/**
	 * {@inheritdoc}
	 * @param array $languageTag
	 * @param string $locale
	 * @param bool $canonicalize [optional]
	 * @param string|null $defaultLocale [optional]
	 */
	public static function lookup (array $languageTag, string $locale, bool $canonicalize = false, ?string $defaultLocale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 */
	public static function canonicalize (string $locale) {}

	/**
	 * {@inheritdoc}
	 * @param string $header
	 */
	public static function acceptFromHttp (string $header) {}

}

class MessageFormatter  {

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 * @param string $pattern
	 */
	public function __construct (string $locale, string $pattern) {}

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 * @param string $pattern
	 */
	public static function create (string $locale, string $pattern) {}

	/**
	 * {@inheritdoc}
	 * @param array $values
	 */
	public function format (array $values) {}

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 * @param string $pattern
	 * @param array $values
	 */
	public static function formatMessage (string $locale, string $pattern, array $values) {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 */
	public function parse (string $string) {}

	/**
	 * {@inheritdoc}
	 * @param string $locale
	 * @param string $pattern
	 * @param string $message
	 */
	public static function parseMessage (string $locale, string $pattern, string $message) {}

	/**
	 * {@inheritdoc}
	 * @param string $pattern
	 */
	public function setPattern (string $pattern) {}

	/**
	 * {@inheritdoc}
	 */
	public function getPattern () {}

	/**
	 * {@inheritdoc}
	 */
	public function getLocale () {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorCode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorMessage () {}

}

class IntlDateFormatter  {
	const FULL = 0;
	const LONG = 1;
	const MEDIUM = 2;
	const SHORT = 3;
	const NONE = -1;
	const RELATIVE_FULL = 128;
	const RELATIVE_LONG = 129;
	const RELATIVE_MEDIUM = 130;
	const RELATIVE_SHORT = 131;
	const GREGORIAN = 1;
	const TRADITIONAL = 0;


	/**
	 * {@inheritdoc}
	 * @param string|null $locale
	 * @param int $dateType [optional]
	 * @param int $timeType [optional]
	 * @param mixed $timezone [optional]
	 * @param mixed $calendar [optional]
	 * @param string|null $pattern [optional]
	 */
	public function __construct (?string $locale = null, int $dateType = 0, int $timeType = 0, $timezone = NULL, $calendar = NULL, ?string $pattern = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $locale
	 * @param int $dateType [optional]
	 * @param int $timeType [optional]
	 * @param mixed $timezone [optional]
	 * @param IntlCalendar|int|null $calendar [optional]
	 * @param string|null $pattern [optional]
	 */
	public static function create (?string $locale = null, int $dateType = 0, int $timeType = 0, $timezone = NULL, IntlCalendar|int|null $calendar = NULL, ?string $pattern = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getDateType () {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimeType () {}

	/**
	 * {@inheritdoc}
	 */
	public function getCalendar () {}

	/**
	 * {@inheritdoc}
	 * @param IntlCalendar|int|null $calendar
	 */
	public function setCalendar (IntlCalendar|int|null $calendar = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimeZoneId () {}

	/**
	 * {@inheritdoc}
	 */
	public function getCalendarObject () {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimeZone () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $timezone
	 */
	public function setTimeZone ($timezone = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $pattern
	 */
	public function setPattern (string $pattern) {}

	/**
	 * {@inheritdoc}
	 */
	public function getPattern () {}

	/**
	 * {@inheritdoc}
	 * @param int $type [optional]
	 */
	public function getLocale (int $type = 0) {}

	/**
	 * {@inheritdoc}
	 * @param bool $lenient
	 */
	public function setLenient (bool $lenient) {}

	/**
	 * {@inheritdoc}
	 */
	public function isLenient () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $datetime
	 */
	public function format ($datetime = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $datetime
	 * @param mixed $format [optional]
	 * @param string|null $locale [optional]
	 */
	public static function formatObject ($datetime = null, $format = NULL, ?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 * @param mixed $offset [optional]
	 */
	public function parse (string $string, &$offset = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 * @param mixed $offset [optional]
	 */
	public function localtime (string $string, &$offset = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorCode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorMessage () {}

}

class IntlDatePatternGenerator  {

	/**
	 * {@inheritdoc}
	 * @param string|null $locale [optional]
	 */
	public function __construct (?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $locale [optional]
	 */
	public static function create (?string $locale = NULL): ?IntlDatePatternGenerator {}

	/**
	 * {@inheritdoc}
	 * @param string $skeleton
	 */
	public function getBestPattern (string $skeleton): string|false {}

}

class ResourceBundle implements IteratorAggregate, Traversable, Countable {

	/**
	 * {@inheritdoc}
	 * @param string|null $locale
	 * @param string|null $bundle
	 * @param bool $fallback [optional]
	 */
	public function __construct (?string $locale = null, ?string $bundle = null, bool $fallback = true) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $locale
	 * @param string|null $bundle
	 * @param bool $fallback [optional]
	 */
	public static function create (?string $locale = null, ?string $bundle = null, bool $fallback = true) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 * @param bool $fallback [optional]
	 */
	public function get ($index = null, bool $fallback = true) {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 * @param string $bundle
	 */
	public static function getLocales (string $bundle) {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorCode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorMessage () {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): Iterator {}

}

class Transliterator  {
	const FORWARD = 0;
	const REVERSE = 1;


	public readonly string $id;

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param string $id
	 * @param int $direction [optional]
	 */
	public static function create (string $id, int $direction = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $rules
	 * @param int $direction [optional]
	 */
	public static function createFromRules (string $rules, int $direction = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function createInverse () {}

	/**
	 * {@inheritdoc}
	 */
	public static function listIDs () {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 * @param int $start [optional]
	 * @param int $end [optional]
	 */
	public function transliterate (string $string, int $start = 0, int $end = -1) {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorCode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorMessage () {}

}

class IntlTimeZone  {
	const DISPLAY_SHORT = 1;
	const DISPLAY_LONG = 2;
	const DISPLAY_SHORT_GENERIC = 3;
	const DISPLAY_LONG_GENERIC = 4;
	const DISPLAY_SHORT_GMT = 5;
	const DISPLAY_LONG_GMT = 6;
	const DISPLAY_SHORT_COMMONLY_USED = 7;
	const DISPLAY_GENERIC_LOCATION = 8;
	const TYPE_ANY = 0;
	const TYPE_CANONICAL = 1;
	const TYPE_CANONICAL_LOCATION = 2;


	/**
	 * {@inheritdoc}
	 */
	private function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param string $timezoneId
	 */
	public static function countEquivalentIDs (string $timezoneId) {}

	/**
	 * {@inheritdoc}
	 */
	public static function createDefault () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $countryOrRawOffset [optional]
	 */
	public static function createEnumeration ($countryOrRawOffset = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $timezoneId
	 */
	public static function createTimeZone (string $timezoneId) {}

	/**
	 * {@inheritdoc}
	 * @param int $type
	 * @param string|null $region [optional]
	 * @param int|null $rawOffset [optional]
	 */
	public static function createTimeZoneIDEnumeration (int $type, ?string $region = NULL, ?int $rawOffset = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param DateTimeZone $timezone
	 */
	public static function fromDateTimeZone (DateTimeZone $timezone) {}

	/**
	 * {@inheritdoc}
	 * @param string $timezoneId
	 * @param mixed $isSystemId [optional]
	 */
	public static function getCanonicalID (string $timezoneId, &$isSystemId = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $dst [optional]
	 * @param int $style [optional]
	 * @param string|null $locale [optional]
	 */
	public function getDisplayName (bool $dst = false, int $style = 2, ?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getDSTSavings () {}

	/**
	 * {@inheritdoc}
	 * @param string $timezoneId
	 * @param int $offset
	 */
	public static function getEquivalentID (string $timezoneId, int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorCode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorMessage () {}

	/**
	 * {@inheritdoc}
	 */
	public static function getGMT () {}

	/**
	 * {@inheritdoc}
	 */
	public function getID () {}

	/**
	 * {@inheritdoc}
	 * @param float $timestamp
	 * @param bool $local
	 * @param mixed $rawOffset
	 * @param mixed $dstOffset
	 */
	public function getOffset (float $timestamp, bool $local, &$rawOffset = null, &$dstOffset = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getRawOffset () {}

	/**
	 * {@inheritdoc}
	 * @param string $timezoneId
	 */
	public static function getRegion (string $timezoneId) {}

	/**
	 * {@inheritdoc}
	 */
	public static function getTZDataVersion () {}

	/**
	 * {@inheritdoc}
	 */
	public static function getUnknown () {}

	/**
	 * {@inheritdoc}
	 * @param string $timezoneId
	 */
	public static function getWindowsID (string $timezoneId) {}

	/**
	 * {@inheritdoc}
	 * @param string $timezoneId
	 * @param string|null $region [optional]
	 */
	public static function getIDForWindowsID (string $timezoneId, ?string $region = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param IntlTimeZone $other
	 */
	public function hasSameRules (IntlTimeZone $other) {}

	/**
	 * {@inheritdoc}
	 */
	public function toDateTimeZone () {}

	/**
	 * {@inheritdoc}
	 */
	public function useDaylightTime () {}

}

class IntlCalendar  {
	const FIELD_ERA = 0;
	const FIELD_YEAR = 1;
	const FIELD_MONTH = 2;
	const FIELD_WEEK_OF_YEAR = 3;
	const FIELD_WEEK_OF_MONTH = 4;
	const FIELD_DATE = 5;
	const FIELD_DAY_OF_YEAR = 6;
	const FIELD_DAY_OF_WEEK = 7;
	const FIELD_DAY_OF_WEEK_IN_MONTH = 8;
	const FIELD_AM_PM = 9;
	const FIELD_HOUR = 10;
	const FIELD_HOUR_OF_DAY = 11;
	const FIELD_MINUTE = 12;
	const FIELD_SECOND = 13;
	const FIELD_MILLISECOND = 14;
	const FIELD_ZONE_OFFSET = 15;
	const FIELD_DST_OFFSET = 16;
	const FIELD_YEAR_WOY = 17;
	const FIELD_DOW_LOCAL = 18;
	const FIELD_EXTENDED_YEAR = 19;
	const FIELD_JULIAN_DAY = 20;
	const FIELD_MILLISECONDS_IN_DAY = 21;
	const FIELD_IS_LEAP_MONTH = 22;
	const FIELD_FIELD_COUNT = 24;
	const FIELD_DAY_OF_MONTH = 5;
	const DOW_SUNDAY = 1;
	const DOW_MONDAY = 2;
	const DOW_TUESDAY = 3;
	const DOW_WEDNESDAY = 4;
	const DOW_THURSDAY = 5;
	const DOW_FRIDAY = 6;
	const DOW_SATURDAY = 7;
	const DOW_TYPE_WEEKDAY = 0;
	const DOW_TYPE_WEEKEND = 1;
	const DOW_TYPE_WEEKEND_OFFSET = 2;
	const DOW_TYPE_WEEKEND_CEASE = 3;
	const WALLTIME_FIRST = 1;
	const WALLTIME_LAST = 0;
	const WALLTIME_NEXT_VALID = 2;


	/**
	 * {@inheritdoc}
	 */
	private function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $timezone [optional]
	 * @param string|null $locale [optional]
	 */
	public static function createInstance ($timezone = NULL, ?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param IntlCalendar $other
	 */
	public function equals (IntlCalendar $other) {}

	/**
	 * {@inheritdoc}
	 * @param float $timestamp
	 * @param int $field
	 */
	public function fieldDifference (float $timestamp, int $field) {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 * @param int $value
	 */
	public function add (int $field, int $value) {}

	/**
	 * {@inheritdoc}
	 * @param IntlCalendar $other
	 */
	public function after (IntlCalendar $other) {}

	/**
	 * {@inheritdoc}
	 * @param IntlCalendar $other
	 */
	public function before (IntlCalendar $other) {}

	/**
	 * {@inheritdoc}
	 * @param int|null $field [optional]
	 */
	public function clear (?int $field = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param DateTime|string $datetime
	 * @param string|null $locale [optional]
	 */
	public static function fromDateTime (DateTime|string $datetime, ?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 */
	public function get (int $field) {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 */
	public function getActualMaximum (int $field) {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 */
	public function getActualMinimum (int $field) {}

	/**
	 * {@inheritdoc}
	 */
	public static function getAvailableLocales () {}

	/**
	 * {@inheritdoc}
	 * @param int $dayOfWeek
	 */
	public function getDayOfWeekType (int $dayOfWeek) {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorCode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorMessage () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFirstDayOfWeek () {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 */
	public function getGreatestMinimum (int $field) {}

	/**
	 * {@inheritdoc}
	 * @param string $keyword
	 * @param string $locale
	 * @param bool $onlyCommon
	 */
	public static function getKeywordValuesForLocale (string $keyword, string $locale, bool $onlyCommon) {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 */
	public function getLeastMaximum (int $field) {}

	/**
	 * {@inheritdoc}
	 * @param int $type
	 */
	public function getLocale (int $type) {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 */
	public function getMaximum (int $field) {}

	/**
	 * {@inheritdoc}
	 */
	public function getMinimalDaysInFirstWeek () {}

	/**
	 * {@inheritdoc}
	 * @param int $days
	 */
	public function setMinimalDaysInFirstWeek (int $days) {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 */
	public function getMinimum (int $field) {}

	/**
	 * {@inheritdoc}
	 */
	public static function getNow () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRepeatedWallTimeOption () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSkippedWallTimeOption () {}

	/**
	 * {@inheritdoc}
	 */
	public function getTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimeZone () {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 * @param int $dayOfWeek
	 */
	public function getWeekendTransition (int $dayOfWeek) {}

	/**
	 * {@inheritdoc}
	 */
	public function inDaylightTime () {}

	/**
	 * {@inheritdoc}
	 * @param IntlCalendar $other
	 */
	public function isEquivalentTo (IntlCalendar $other) {}

	/**
	 * {@inheritdoc}
	 */
	public function isLenient () {}

	/**
	 * {@inheritdoc}
	 * @param float|null $timestamp [optional]
	 */
	public function isWeekend (?float $timestamp = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 * @param mixed $value
	 */
	public function roll (int $field, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 */
	public function isSet (int $field) {}

	/**
	 * {@inheritdoc}
	 * @param int $year
	 * @param int $month
	 * @param int $dayOfMonth [optional]
	 * @param int $hour [optional]
	 * @param int $minute [optional]
	 * @param int $second [optional]
	 */
	public function set (int $year, int $month, int $dayOfMonth = NULL, int $hour = NULL, int $minute = NULL, int $second = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $year
	 * @param int $month
	 * @param int $dayOfMonth
	 */
	public function setDate (int $year, int $month, int $dayOfMonth): void {}

	/**
	 * {@inheritdoc}
	 * @param int $year
	 * @param int $month
	 * @param int $dayOfMonth
	 * @param int $hour
	 * @param int $minute
	 * @param int|null $second [optional]
	 */
	public function setDateTime (int $year, int $month, int $dayOfMonth, int $hour, int $minute, ?int $second = NULL): void {}

	/**
	 * {@inheritdoc}
	 * @param int $dayOfWeek
	 */
	public function setFirstDayOfWeek (int $dayOfWeek) {}

	/**
	 * {@inheritdoc}
	 * @param bool $lenient
	 */
	public function setLenient (bool $lenient) {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 */
	public function setRepeatedWallTimeOption (int $option) {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 */
	public function setSkippedWallTimeOption (int $option) {}

	/**
	 * {@inheritdoc}
	 * @param float $timestamp
	 */
	public function setTime (float $timestamp) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $timezone
	 */
	public function setTimeZone ($timezone = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function toDateTime () {}

}

class IntlGregorianCalendar extends IntlCalendar  {
	const FIELD_ERA = 0;
	const FIELD_YEAR = 1;
	const FIELD_MONTH = 2;
	const FIELD_WEEK_OF_YEAR = 3;
	const FIELD_WEEK_OF_MONTH = 4;
	const FIELD_DATE = 5;
	const FIELD_DAY_OF_YEAR = 6;
	const FIELD_DAY_OF_WEEK = 7;
	const FIELD_DAY_OF_WEEK_IN_MONTH = 8;
	const FIELD_AM_PM = 9;
	const FIELD_HOUR = 10;
	const FIELD_HOUR_OF_DAY = 11;
	const FIELD_MINUTE = 12;
	const FIELD_SECOND = 13;
	const FIELD_MILLISECOND = 14;
	const FIELD_ZONE_OFFSET = 15;
	const FIELD_DST_OFFSET = 16;
	const FIELD_YEAR_WOY = 17;
	const FIELD_DOW_LOCAL = 18;
	const FIELD_EXTENDED_YEAR = 19;
	const FIELD_JULIAN_DAY = 20;
	const FIELD_MILLISECONDS_IN_DAY = 21;
	const FIELD_IS_LEAP_MONTH = 22;
	const FIELD_FIELD_COUNT = 24;
	const FIELD_DAY_OF_MONTH = 5;
	const DOW_SUNDAY = 1;
	const DOW_MONDAY = 2;
	const DOW_TUESDAY = 3;
	const DOW_WEDNESDAY = 4;
	const DOW_THURSDAY = 5;
	const DOW_FRIDAY = 6;
	const DOW_SATURDAY = 7;
	const DOW_TYPE_WEEKDAY = 0;
	const DOW_TYPE_WEEKEND = 1;
	const DOW_TYPE_WEEKEND_OFFSET = 2;
	const DOW_TYPE_WEEKEND_CEASE = 3;
	const WALLTIME_FIRST = 1;
	const WALLTIME_LAST = 0;
	const WALLTIME_NEXT_VALID = 2;


	/**
	 * {@inheritdoc}
	 * @param int $year
	 * @param int $month
	 * @param int $dayOfMonth
	 */
	public static function createFromDate (int $year, int $month, int $dayOfMonth): static {}

	/**
	 * {@inheritdoc}
	 * @param int $year
	 * @param int $month
	 * @param int $dayOfMonth
	 * @param int $hour
	 * @param int $minute
	 * @param int|null $second [optional]
	 */
	public static function createFromDateTime (int $year, int $month, int $dayOfMonth, int $hour, int $minute, ?int $second = NULL): static {}

	/**
	 * {@inheritdoc}
	 * @param mixed $timezoneOrYear [optional]
	 * @param mixed $localeOrMonth [optional]
	 * @param mixed $day [optional]
	 * @param mixed $hour [optional]
	 * @param mixed $minute [optional]
	 * @param mixed $second [optional]
	 */
	public function __construct ($timezoneOrYear = NULL, $localeOrMonth = NULL, $day = NULL, $hour = NULL, $minute = NULL, $second = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param float $timestamp
	 */
	public function setGregorianChange (float $timestamp) {}

	/**
	 * {@inheritdoc}
	 */
	public function getGregorianChange () {}

	/**
	 * {@inheritdoc}
	 * @param int $year
	 */
	public function isLeapYear (int $year) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $timezone [optional]
	 * @param string|null $locale [optional]
	 */
	public static function createInstance ($timezone = NULL, ?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param IntlCalendar $other
	 */
	public function equals (IntlCalendar $other) {}

	/**
	 * {@inheritdoc}
	 * @param float $timestamp
	 * @param int $field
	 */
	public function fieldDifference (float $timestamp, int $field) {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 * @param int $value
	 */
	public function add (int $field, int $value) {}

	/**
	 * {@inheritdoc}
	 * @param IntlCalendar $other
	 */
	public function after (IntlCalendar $other) {}

	/**
	 * {@inheritdoc}
	 * @param IntlCalendar $other
	 */
	public function before (IntlCalendar $other) {}

	/**
	 * {@inheritdoc}
	 * @param int|null $field [optional]
	 */
	public function clear (?int $field = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param DateTime|string $datetime
	 * @param string|null $locale [optional]
	 */
	public static function fromDateTime (DateTime|string $datetime, ?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 */
	public function get (int $field) {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 */
	public function getActualMaximum (int $field) {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 */
	public function getActualMinimum (int $field) {}

	/**
	 * {@inheritdoc}
	 */
	public static function getAvailableLocales () {}

	/**
	 * {@inheritdoc}
	 * @param int $dayOfWeek
	 */
	public function getDayOfWeekType (int $dayOfWeek) {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorCode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorMessage () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFirstDayOfWeek () {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 */
	public function getGreatestMinimum (int $field) {}

	/**
	 * {@inheritdoc}
	 * @param string $keyword
	 * @param string $locale
	 * @param bool $onlyCommon
	 */
	public static function getKeywordValuesForLocale (string $keyword, string $locale, bool $onlyCommon) {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 */
	public function getLeastMaximum (int $field) {}

	/**
	 * {@inheritdoc}
	 * @param int $type
	 */
	public function getLocale (int $type) {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 */
	public function getMaximum (int $field) {}

	/**
	 * {@inheritdoc}
	 */
	public function getMinimalDaysInFirstWeek () {}

	/**
	 * {@inheritdoc}
	 * @param int $days
	 */
	public function setMinimalDaysInFirstWeek (int $days) {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 */
	public function getMinimum (int $field) {}

	/**
	 * {@inheritdoc}
	 */
	public static function getNow () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRepeatedWallTimeOption () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSkippedWallTimeOption () {}

	/**
	 * {@inheritdoc}
	 */
	public function getTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimeZone () {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 * @param int $dayOfWeek
	 */
	public function getWeekendTransition (int $dayOfWeek) {}

	/**
	 * {@inheritdoc}
	 */
	public function inDaylightTime () {}

	/**
	 * {@inheritdoc}
	 * @param IntlCalendar $other
	 */
	public function isEquivalentTo (IntlCalendar $other) {}

	/**
	 * {@inheritdoc}
	 */
	public function isLenient () {}

	/**
	 * {@inheritdoc}
	 * @param float|null $timestamp [optional]
	 */
	public function isWeekend (?float $timestamp = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 * @param mixed $value
	 */
	public function roll (int $field, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param int $field
	 */
	public function isSet (int $field) {}

	/**
	 * {@inheritdoc}
	 * @param int $year
	 * @param int $month
	 * @param int $dayOfMonth [optional]
	 * @param int $hour [optional]
	 * @param int $minute [optional]
	 * @param int $second [optional]
	 */
	public function set (int $year, int $month, int $dayOfMonth = NULL, int $hour = NULL, int $minute = NULL, int $second = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $year
	 * @param int $month
	 * @param int $dayOfMonth
	 */
	public function setDate (int $year, int $month, int $dayOfMonth): void {}

	/**
	 * {@inheritdoc}
	 * @param int $year
	 * @param int $month
	 * @param int $dayOfMonth
	 * @param int $hour
	 * @param int $minute
	 * @param int|null $second [optional]
	 */
	public function setDateTime (int $year, int $month, int $dayOfMonth, int $hour, int $minute, ?int $second = NULL): void {}

	/**
	 * {@inheritdoc}
	 * @param int $dayOfWeek
	 */
	public function setFirstDayOfWeek (int $dayOfWeek) {}

	/**
	 * {@inheritdoc}
	 * @param bool $lenient
	 */
	public function setLenient (bool $lenient) {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 */
	public function setRepeatedWallTimeOption (int $option) {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 */
	public function setSkippedWallTimeOption (int $option) {}

	/**
	 * {@inheritdoc}
	 * @param float $timestamp
	 */
	public function setTime (float $timestamp) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $timezone
	 */
	public function setTimeZone ($timezone = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function toDateTime () {}

}

class Spoofchecker  {
	const SINGLE_SCRIPT_CONFUSABLE = 1;
	const MIXED_SCRIPT_CONFUSABLE = 2;
	const WHOLE_SCRIPT_CONFUSABLE = 4;
	const ANY_CASE = 8;
	const SINGLE_SCRIPT = 16;
	const INVISIBLE = 32;
	const CHAR_LIMIT = 64;
	const ASCII = 268435456;
	const HIGHLY_RESTRICTIVE = 805306368;
	const MODERATELY_RESTRICTIVE = 1073741824;
	const MINIMALLY_RESTRICTIVE = 1342177280;
	const UNRESTRICTIVE = 1610612736;
	const SINGLE_SCRIPT_RESTRICTIVE = 536870912;
	const MIXED_NUMBERS = 128;
	const HIDDEN_OVERLAY = 256;


	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 * @param mixed $errorCode [optional]
	 */
	public function isSuspicious (string $string, &$errorCode = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $string1
	 * @param string $string2
	 * @param mixed $errorCode [optional]
	 */
	public function areConfusable (string $string1, string $string2, &$errorCode = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $locales
	 */
	public function setAllowedLocales (string $locales) {}

	/**
	 * {@inheritdoc}
	 * @param int $checks
	 */
	public function setChecks (int $checks) {}

	/**
	 * {@inheritdoc}
	 * @param int $level
	 */
	public function setRestrictionLevel (int $level) {}

}

class IntlException extends Exception implements Throwable, Stringable {

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

class IntlIterator implements Iterator, Traversable {

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

}

class IntlBreakIterator implements IteratorAggregate, Traversable {
	const DONE = -1;
	const WORD_NONE = 0;
	const WORD_NONE_LIMIT = 100;
	const WORD_NUMBER = 100;
	const WORD_NUMBER_LIMIT = 200;
	const WORD_LETTER = 200;
	const WORD_LETTER_LIMIT = 300;
	const WORD_KANA = 300;
	const WORD_KANA_LIMIT = 400;
	const WORD_IDEO = 400;
	const WORD_IDEO_LIMIT = 500;
	const LINE_SOFT = 0;
	const LINE_SOFT_LIMIT = 100;
	const LINE_HARD = 100;
	const LINE_HARD_LIMIT = 200;
	const SENTENCE_TERM = 0;
	const SENTENCE_TERM_LIMIT = 100;
	const SENTENCE_SEP = 100;
	const SENTENCE_SEP_LIMIT = 200;


	/**
	 * {@inheritdoc}
	 * @param string|null $locale [optional]
	 */
	public static function createCharacterInstance (?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public static function createCodePointInstance () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $locale [optional]
	 */
	public static function createLineInstance (?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $locale [optional]
	 */
	public static function createSentenceInstance (?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $locale [optional]
	 */
	public static function createTitleInstance (?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $locale [optional]
	 */
	public static function createWordInstance (?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function first () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function following (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorCode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorMessage () {}

	/**
	 * {@inheritdoc}
	 * @param int $type
	 */
	public function getLocale (int $type) {}

	/**
	 * {@inheritdoc}
	 * @param string $type [optional]
	 */
	public function getPartsIterator (string $type = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function getText () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function isBoundary (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function last () {}

	/**
	 * {@inheritdoc}
	 * @param int|null $offset [optional]
	 */
	public function next (?int $offset = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function preceding (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function previous () {}

	/**
	 * {@inheritdoc}
	 * @param string $text
	 */
	public function setText (string $text) {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): Iterator {}

}

class IntlRuleBasedBreakIterator extends IntlBreakIterator implements Traversable, IteratorAggregate {
	const DONE = -1;
	const WORD_NONE = 0;
	const WORD_NONE_LIMIT = 100;
	const WORD_NUMBER = 100;
	const WORD_NUMBER_LIMIT = 200;
	const WORD_LETTER = 200;
	const WORD_LETTER_LIMIT = 300;
	const WORD_KANA = 300;
	const WORD_KANA_LIMIT = 400;
	const WORD_IDEO = 400;
	const WORD_IDEO_LIMIT = 500;
	const LINE_SOFT = 0;
	const LINE_SOFT_LIMIT = 100;
	const LINE_HARD = 100;
	const LINE_HARD_LIMIT = 200;
	const SENTENCE_TERM = 0;
	const SENTENCE_TERM_LIMIT = 100;
	const SENTENCE_SEP = 100;
	const SENTENCE_SEP_LIMIT = 200;


	/**
	 * {@inheritdoc}
	 * @param string $rules
	 * @param bool $compiled [optional]
	 */
	public function __construct (string $rules, bool $compiled = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function getBinaryRules () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRules () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRuleStatus () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRuleStatusVec () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $locale [optional]
	 */
	public static function createCharacterInstance (?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public static function createCodePointInstance () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $locale [optional]
	 */
	public static function createLineInstance (?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $locale [optional]
	 */
	public static function createSentenceInstance (?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $locale [optional]
	 */
	public static function createTitleInstance (?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $locale [optional]
	 */
	public static function createWordInstance (?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function first () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function following (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorCode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorMessage () {}

	/**
	 * {@inheritdoc}
	 * @param int $type
	 */
	public function getLocale (int $type) {}

	/**
	 * {@inheritdoc}
	 * @param string $type [optional]
	 */
	public function getPartsIterator (string $type = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function getText () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function isBoundary (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function last () {}

	/**
	 * {@inheritdoc}
	 * @param int|null $offset [optional]
	 */
	public function next (?int $offset = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function preceding (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function previous () {}

	/**
	 * {@inheritdoc}
	 * @param string $text
	 */
	public function setText (string $text) {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): Iterator {}

}

class IntlCodePointBreakIterator extends IntlBreakIterator implements Traversable, IteratorAggregate {
	const DONE = -1;
	const WORD_NONE = 0;
	const WORD_NONE_LIMIT = 100;
	const WORD_NUMBER = 100;
	const WORD_NUMBER_LIMIT = 200;
	const WORD_LETTER = 200;
	const WORD_LETTER_LIMIT = 300;
	const WORD_KANA = 300;
	const WORD_KANA_LIMIT = 400;
	const WORD_IDEO = 400;
	const WORD_IDEO_LIMIT = 500;
	const LINE_SOFT = 0;
	const LINE_SOFT_LIMIT = 100;
	const LINE_HARD = 100;
	const LINE_HARD_LIMIT = 200;
	const SENTENCE_TERM = 0;
	const SENTENCE_TERM_LIMIT = 100;
	const SENTENCE_SEP = 100;
	const SENTENCE_SEP_LIMIT = 200;


	/**
	 * {@inheritdoc}
	 */
	public function getLastCodePoint () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $locale [optional]
	 */
	public static function createCharacterInstance (?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public static function createCodePointInstance () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $locale [optional]
	 */
	public static function createLineInstance (?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $locale [optional]
	 */
	public static function createSentenceInstance (?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $locale [optional]
	 */
	public static function createTitleInstance (?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $locale [optional]
	 */
	public static function createWordInstance (?string $locale = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function first () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function following (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorCode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorMessage () {}

	/**
	 * {@inheritdoc}
	 * @param int $type
	 */
	public function getLocale (int $type) {}

	/**
	 * {@inheritdoc}
	 * @param string $type [optional]
	 */
	public function getPartsIterator (string $type = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function getText () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function isBoundary (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function last () {}

	/**
	 * {@inheritdoc}
	 * @param int|null $offset [optional]
	 */
	public function next (?int $offset = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function preceding (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function previous () {}

	/**
	 * {@inheritdoc}
	 * @param string $text
	 */
	public function setText (string $text) {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): Iterator {}

}

class IntlPartsIterator extends IntlIterator implements Traversable, Iterator {
	const KEY_SEQUENTIAL = 0;
	const KEY_LEFT = 1;
	const KEY_RIGHT = 2;


	/**
	 * {@inheritdoc}
	 */
	public function getBreakIterator () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRuleStatus () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

}

class UConverter  {
	const REASON_UNASSIGNED = 0;
	const REASON_ILLEGAL = 1;
	const REASON_IRREGULAR = 2;
	const REASON_RESET = 3;
	const REASON_CLOSE = 4;
	const REASON_CLONE = 5;
	const UNSUPPORTED_CONVERTER = -1;
	const SBCS = 0;
	const DBCS = 1;
	const MBCS = 2;
	const LATIN_1 = 3;
	const UTF8 = 4;
	const UTF16_BigEndian = 5;
	const UTF16_LittleEndian = 6;
	const UTF32_BigEndian = 7;
	const UTF32_LittleEndian = 8;
	const EBCDIC_STATEFUL = 9;
	const ISO_2022 = 10;
	const LMBCS_1 = 11;
	const LMBCS_2 = 12;
	const LMBCS_3 = 13;
	const LMBCS_4 = 14;
	const LMBCS_5 = 15;
	const LMBCS_6 = 16;
	const LMBCS_8 = 17;
	const LMBCS_11 = 18;
	const LMBCS_16 = 19;
	const LMBCS_17 = 20;
	const LMBCS_18 = 21;
	const LMBCS_19 = 22;
	const LMBCS_LAST = 22;
	const HZ = 23;
	const SCSU = 24;
	const ISCII = 25;
	const US_ASCII = 26;
	const UTF7 = 27;
	const BOCU1 = 28;
	const UTF16 = 29;
	const UTF32 = 30;
	const CESU8 = 31;
	const IMAP_MAILBOX = 32;


	/**
	 * {@inheritdoc}
	 * @param string|null $destination_encoding [optional]
	 * @param string|null $source_encoding [optional]
	 */
	public function __construct (?string $destination_encoding = NULL, ?string $source_encoding = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $str
	 * @param bool $reverse [optional]
	 */
	public function convert (string $str, bool $reverse = false) {}

	/**
	 * {@inheritdoc}
	 * @param int $reason
	 * @param array $source
	 * @param int $codePoint
	 * @param mixed $error
	 */
	public function fromUCallback (int $reason, array $source, int $codePoint, &$error = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public static function getAliases (string $name) {}

	/**
	 * {@inheritdoc}
	 */
	public static function getAvailable () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDestinationEncoding () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDestinationType () {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorCode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrorMessage () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSourceEncoding () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSourceType () {}

	/**
	 * {@inheritdoc}
	 */
	public static function getStandards () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSubstChars () {}

	/**
	 * {@inheritdoc}
	 * @param int $reason
	 */
	public static function reasonText (int $reason) {}

	/**
	 * {@inheritdoc}
	 * @param string $encoding
	 */
	public function setDestinationEncoding (string $encoding) {}

	/**
	 * {@inheritdoc}
	 * @param string $encoding
	 */
	public function setSourceEncoding (string $encoding) {}

	/**
	 * {@inheritdoc}
	 * @param string $chars
	 */
	public function setSubstChars (string $chars) {}

	/**
	 * {@inheritdoc}
	 * @param int $reason
	 * @param string $source
	 * @param string $codeUnits
	 * @param mixed $error
	 */
	public function toUCallback (int $reason, string $source, string $codeUnits, &$error = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $str
	 * @param string $toEncoding
	 * @param string $fromEncoding
	 * @param array|null $options [optional]
	 */
	public static function transcode (string $str, string $toEncoding, string $fromEncoding, ?array $options = NULL) {}

}

class IntlChar  {
	const UNICODE_VERSION = 15.0;
	const CODEPOINT_MIN = 0;
	const CODEPOINT_MAX = 1114111;
	const NO_NUMERIC_VALUE = -123456789;
	const PROPERTY_ALPHABETIC = 0;
	const PROPERTY_BINARY_START = 0;
	const PROPERTY_ASCII_HEX_DIGIT = 1;
	const PROPERTY_BIDI_CONTROL = 2;
	const PROPERTY_BIDI_MIRRORED = 3;
	const PROPERTY_DASH = 4;
	const PROPERTY_DEFAULT_IGNORABLE_CODE_POINT = 5;
	const PROPERTY_DEPRECATED = 6;
	const PROPERTY_DIACRITIC = 7;
	const PROPERTY_EXTENDER = 8;
	const PROPERTY_FULL_COMPOSITION_EXCLUSION = 9;
	const PROPERTY_GRAPHEME_BASE = 10;
	const PROPERTY_GRAPHEME_EXTEND = 11;
	const PROPERTY_GRAPHEME_LINK = 12;
	const PROPERTY_HEX_DIGIT = 13;
	const PROPERTY_HYPHEN = 14;
	const PROPERTY_ID_CONTINUE = 15;
	const PROPERTY_ID_START = 16;
	const PROPERTY_IDEOGRAPHIC = 17;
	const PROPERTY_IDS_BINARY_OPERATOR = 18;
	const PROPERTY_IDS_TRINARY_OPERATOR = 19;
	const PROPERTY_JOIN_CONTROL = 20;
	const PROPERTY_LOGICAL_ORDER_EXCEPTION = 21;
	const PROPERTY_LOWERCASE = 22;
	const PROPERTY_MATH = 23;
	const PROPERTY_NONCHARACTER_CODE_POINT = 24;
	const PROPERTY_QUOTATION_MARK = 25;
	const PROPERTY_RADICAL = 26;
	const PROPERTY_SOFT_DOTTED = 27;
	const PROPERTY_TERMINAL_PUNCTUATION = 28;
	const PROPERTY_UNIFIED_IDEOGRAPH = 29;
	const PROPERTY_UPPERCASE = 30;
	const PROPERTY_WHITE_SPACE = 31;
	const PROPERTY_XID_CONTINUE = 32;
	const PROPERTY_XID_START = 33;
	const PROPERTY_CASE_SENSITIVE = 34;
	const PROPERTY_S_TERM = 35;
	const PROPERTY_VARIATION_SELECTOR = 36;
	const PROPERTY_NFD_INERT = 37;
	const PROPERTY_NFKD_INERT = 38;
	const PROPERTY_NFC_INERT = 39;
	const PROPERTY_NFKC_INERT = 40;
	const PROPERTY_SEGMENT_STARTER = 41;
	const PROPERTY_PATTERN_SYNTAX = 42;
	const PROPERTY_PATTERN_WHITE_SPACE = 43;
	const PROPERTY_POSIX_ALNUM = 44;
	const PROPERTY_POSIX_BLANK = 45;
	const PROPERTY_POSIX_GRAPH = 46;
	const PROPERTY_POSIX_PRINT = 47;
	const PROPERTY_POSIX_XDIGIT = 48;
	const PROPERTY_CASED = 49;
	const PROPERTY_CASE_IGNORABLE = 50;
	const PROPERTY_CHANGES_WHEN_LOWERCASED = 51;
	const PROPERTY_CHANGES_WHEN_UPPERCASED = 52;
	const PROPERTY_CHANGES_WHEN_TITLECASED = 53;
	const PROPERTY_CHANGES_WHEN_CASEFOLDED = 54;
	const PROPERTY_CHANGES_WHEN_CASEMAPPED = 55;
	const PROPERTY_CHANGES_WHEN_NFKC_CASEFOLDED = 56;
	const PROPERTY_BINARY_LIMIT = 72;
	const PROPERTY_BIDI_CLASS = 4096;
	const PROPERTY_INT_START = 4096;
	const PROPERTY_BLOCK = 4097;
	const PROPERTY_CANONICAL_COMBINING_CLASS = 4098;
	const PROPERTY_DECOMPOSITION_TYPE = 4099;
	const PROPERTY_EAST_ASIAN_WIDTH = 4100;
	const PROPERTY_GENERAL_CATEGORY = 4101;
	const PROPERTY_JOINING_GROUP = 4102;
	const PROPERTY_JOINING_TYPE = 4103;
	const PROPERTY_LINE_BREAK = 4104;
	const PROPERTY_NUMERIC_TYPE = 4105;
	const PROPERTY_SCRIPT = 4106;
	const PROPERTY_HANGUL_SYLLABLE_TYPE = 4107;
	const PROPERTY_NFD_QUICK_CHECK = 4108;
	const PROPERTY_NFKD_QUICK_CHECK = 4109;
	const PROPERTY_NFC_QUICK_CHECK = 4110;
	const PROPERTY_NFKC_QUICK_CHECK = 4111;
	const PROPERTY_LEAD_CANONICAL_COMBINING_CLASS = 4112;
	const PROPERTY_TRAIL_CANONICAL_COMBINING_CLASS = 4113;
	const PROPERTY_GRAPHEME_CLUSTER_BREAK = 4114;
	const PROPERTY_SENTENCE_BREAK = 4115;
	const PROPERTY_WORD_BREAK = 4116;
	const PROPERTY_BIDI_PAIRED_BRACKET_TYPE = 4117;
	const PROPERTY_INT_LIMIT = 4121;
	const PROPERTY_GENERAL_CATEGORY_MASK = 8192;
	const PROPERTY_MASK_START = 8192;
	const PROPERTY_MASK_LIMIT = 8193;
	const PROPERTY_NUMERIC_VALUE = 12288;
	const PROPERTY_DOUBLE_START = 12288;
	const PROPERTY_DOUBLE_LIMIT = 12289;
	const PROPERTY_AGE = 16384;
	const PROPERTY_STRING_START = 16384;
	const PROPERTY_BIDI_MIRRORING_GLYPH = 16385;
	const PROPERTY_CASE_FOLDING = 16386;
	const PROPERTY_ISO_COMMENT = 16387;
	const PROPERTY_LOWERCASE_MAPPING = 16388;
	const PROPERTY_NAME = 16389;
	const PROPERTY_SIMPLE_CASE_FOLDING = 16390;
	const PROPERTY_SIMPLE_LOWERCASE_MAPPING = 16391;
	const PROPERTY_SIMPLE_TITLECASE_MAPPING = 16392;
	const PROPERTY_SIMPLE_UPPERCASE_MAPPING = 16393;
	const PROPERTY_TITLECASE_MAPPING = 16394;
	const PROPERTY_UNICODE_1_NAME = 16395;
	const PROPERTY_UPPERCASE_MAPPING = 16396;
	const PROPERTY_BIDI_PAIRED_BRACKET = 16397;
	const PROPERTY_STRING_LIMIT = 16398;
	const PROPERTY_SCRIPT_EXTENSIONS = 28672;
	const PROPERTY_OTHER_PROPERTY_START = 28672;
	const PROPERTY_OTHER_PROPERTY_LIMIT = 28673;
	const PROPERTY_INVALID_CODE = -1;
	const CHAR_CATEGORY_UNASSIGNED = 0;
	const CHAR_CATEGORY_GENERAL_OTHER_TYPES = 0;
	const CHAR_CATEGORY_UPPERCASE_LETTER = 1;
	const CHAR_CATEGORY_LOWERCASE_LETTER = 2;
	const CHAR_CATEGORY_TITLECASE_LETTER = 3;
	const CHAR_CATEGORY_MODIFIER_LETTER = 4;
	const CHAR_CATEGORY_OTHER_LETTER = 5;
	const CHAR_CATEGORY_NON_SPACING_MARK = 6;
	const CHAR_CATEGORY_ENCLOSING_MARK = 7;
	const CHAR_CATEGORY_COMBINING_SPACING_MARK = 8;
	const CHAR_CATEGORY_DECIMAL_DIGIT_NUMBER = 9;
	const CHAR_CATEGORY_LETTER_NUMBER = 10;
	const CHAR_CATEGORY_OTHER_NUMBER = 11;
	const CHAR_CATEGORY_SPACE_SEPARATOR = 12;
	const CHAR_CATEGORY_LINE_SEPARATOR = 13;
	const CHAR_CATEGORY_PARAGRAPH_SEPARATOR = 14;
	const CHAR_CATEGORY_CONTROL_CHAR = 15;
	const CHAR_CATEGORY_FORMAT_CHAR = 16;
	const CHAR_CATEGORY_PRIVATE_USE_CHAR = 17;
	const CHAR_CATEGORY_SURROGATE = 18;
	const CHAR_CATEGORY_DASH_PUNCTUATION = 19;
	const CHAR_CATEGORY_START_PUNCTUATION = 20;
	const CHAR_CATEGORY_END_PUNCTUATION = 21;
	const CHAR_CATEGORY_CONNECTOR_PUNCTUATION = 22;
	const CHAR_CATEGORY_OTHER_PUNCTUATION = 23;
	const CHAR_CATEGORY_MATH_SYMBOL = 24;
	const CHAR_CATEGORY_CURRENCY_SYMBOL = 25;
	const CHAR_CATEGORY_MODIFIER_SYMBOL = 26;
	const CHAR_CATEGORY_OTHER_SYMBOL = 27;
	const CHAR_CATEGORY_INITIAL_PUNCTUATION = 28;
	const CHAR_CATEGORY_FINAL_PUNCTUATION = 29;
	const CHAR_CATEGORY_CHAR_CATEGORY_COUNT = 30;
	const CHAR_DIRECTION_LEFT_TO_RIGHT = 0;
	const CHAR_DIRECTION_RIGHT_TO_LEFT = 1;
	const CHAR_DIRECTION_EUROPEAN_NUMBER = 2;
	const CHAR_DIRECTION_EUROPEAN_NUMBER_SEPARATOR = 3;
	const CHAR_DIRECTION_EUROPEAN_NUMBER_TERMINATOR = 4;
	const CHAR_DIRECTION_ARABIC_NUMBER = 5;
	const CHAR_DIRECTION_COMMON_NUMBER_SEPARATOR = 6;
	const CHAR_DIRECTION_BLOCK_SEPARATOR = 7;
	const CHAR_DIRECTION_SEGMENT_SEPARATOR = 8;
	const CHAR_DIRECTION_WHITE_SPACE_NEUTRAL = 9;
	const CHAR_DIRECTION_OTHER_NEUTRAL = 10;
	const CHAR_DIRECTION_LEFT_TO_RIGHT_EMBEDDING = 11;
	const CHAR_DIRECTION_LEFT_TO_RIGHT_OVERRIDE = 12;
	const CHAR_DIRECTION_RIGHT_TO_LEFT_ARABIC = 13;
	const CHAR_DIRECTION_RIGHT_TO_LEFT_EMBEDDING = 14;
	const CHAR_DIRECTION_RIGHT_TO_LEFT_OVERRIDE = 15;
	const CHAR_DIRECTION_POP_DIRECTIONAL_FORMAT = 16;
	const CHAR_DIRECTION_DIR_NON_SPACING_MARK = 17;
	const CHAR_DIRECTION_BOUNDARY_NEUTRAL = 18;
	const CHAR_DIRECTION_FIRST_STRONG_ISOLATE = 19;
	const CHAR_DIRECTION_LEFT_TO_RIGHT_ISOLATE = 20;
	const CHAR_DIRECTION_RIGHT_TO_LEFT_ISOLATE = 21;
	const CHAR_DIRECTION_POP_DIRECTIONAL_ISOLATE = 22;
	const CHAR_DIRECTION_CHAR_DIRECTION_COUNT = 23;
	const BLOCK_CODE_NO_BLOCK = 0;
	const BLOCK_CODE_BASIC_LATIN = 1;
	const BLOCK_CODE_LATIN_1_SUPPLEMENT = 2;
	const BLOCK_CODE_LATIN_EXTENDED_A = 3;
	const BLOCK_CODE_LATIN_EXTENDED_B = 4;
	const BLOCK_CODE_IPA_EXTENSIONS = 5;
	const BLOCK_CODE_SPACING_MODIFIER_LETTERS = 6;
	const BLOCK_CODE_COMBINING_DIACRITICAL_MARKS = 7;
	const BLOCK_CODE_GREEK = 8;
	const BLOCK_CODE_CYRILLIC = 9;
	const BLOCK_CODE_ARMENIAN = 10;
	const BLOCK_CODE_HEBREW = 11;
	const BLOCK_CODE_ARABIC = 12;
	const BLOCK_CODE_SYRIAC = 13;
	const BLOCK_CODE_THAANA = 14;
	const BLOCK_CODE_DEVANAGARI = 15;
	const BLOCK_CODE_BENGALI = 16;
	const BLOCK_CODE_GURMUKHI = 17;
	const BLOCK_CODE_GUJARATI = 18;
	const BLOCK_CODE_ORIYA = 19;
	const BLOCK_CODE_TAMIL = 20;
	const BLOCK_CODE_TELUGU = 21;
	const BLOCK_CODE_KANNADA = 22;
	const BLOCK_CODE_MALAYALAM = 23;
	const BLOCK_CODE_SINHALA = 24;
	const BLOCK_CODE_THAI = 25;
	const BLOCK_CODE_LAO = 26;
	const BLOCK_CODE_TIBETAN = 27;
	const BLOCK_CODE_MYANMAR = 28;
	const BLOCK_CODE_GEORGIAN = 29;
	const BLOCK_CODE_HANGUL_JAMO = 30;
	const BLOCK_CODE_ETHIOPIC = 31;
	const BLOCK_CODE_CHEROKEE = 32;
	const BLOCK_CODE_UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS = 33;
	const BLOCK_CODE_OGHAM = 34;
	const BLOCK_CODE_RUNIC = 35;
	const BLOCK_CODE_KHMER = 36;
	const BLOCK_CODE_MONGOLIAN = 37;
	const BLOCK_CODE_LATIN_EXTENDED_ADDITIONAL = 38;
	const BLOCK_CODE_GREEK_EXTENDED = 39;
	const BLOCK_CODE_GENERAL_PUNCTUATION = 40;
	const BLOCK_CODE_SUPERSCRIPTS_AND_SUBSCRIPTS = 41;
	const BLOCK_CODE_CURRENCY_SYMBOLS = 42;
	const BLOCK_CODE_COMBINING_MARKS_FOR_SYMBOLS = 43;
	const BLOCK_CODE_LETTERLIKE_SYMBOLS = 44;
	const BLOCK_CODE_NUMBER_FORMS = 45;
	const BLOCK_CODE_ARROWS = 46;
	const BLOCK_CODE_MATHEMATICAL_OPERATORS = 47;
	const BLOCK_CODE_MISCELLANEOUS_TECHNICAL = 48;
	const BLOCK_CODE_CONTROL_PICTURES = 49;
	const BLOCK_CODE_OPTICAL_CHARACTER_RECOGNITION = 50;
	const BLOCK_CODE_ENCLOSED_ALPHANUMERICS = 51;
	const BLOCK_CODE_BOX_DRAWING = 52;
	const BLOCK_CODE_BLOCK_ELEMENTS = 53;
	const BLOCK_CODE_GEOMETRIC_SHAPES = 54;
	const BLOCK_CODE_MISCELLANEOUS_SYMBOLS = 55;
	const BLOCK_CODE_DINGBATS = 56;
	const BLOCK_CODE_BRAILLE_PATTERNS = 57;
	const BLOCK_CODE_CJK_RADICALS_SUPPLEMENT = 58;
	const BLOCK_CODE_KANGXI_RADICALS = 59;
	const BLOCK_CODE_IDEOGRAPHIC_DESCRIPTION_CHARACTERS = 60;
	const BLOCK_CODE_CJK_SYMBOLS_AND_PUNCTUATION = 61;
	const BLOCK_CODE_HIRAGANA = 62;
	const BLOCK_CODE_KATAKANA = 63;
	const BLOCK_CODE_BOPOMOFO = 64;
	const BLOCK_CODE_HANGUL_COMPATIBILITY_JAMO = 65;
	const BLOCK_CODE_KANBUN = 66;
	const BLOCK_CODE_BOPOMOFO_EXTENDED = 67;
	const BLOCK_CODE_ENCLOSED_CJK_LETTERS_AND_MONTHS = 68;
	const BLOCK_CODE_CJK_COMPATIBILITY = 69;
	const BLOCK_CODE_CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A = 70;
	const BLOCK_CODE_CJK_UNIFIED_IDEOGRAPHS = 71;
	const BLOCK_CODE_YI_SYLLABLES = 72;
	const BLOCK_CODE_YI_RADICALS = 73;
	const BLOCK_CODE_HANGUL_SYLLABLES = 74;
	const BLOCK_CODE_HIGH_SURROGATES = 75;
	const BLOCK_CODE_HIGH_PRIVATE_USE_SURROGATES = 76;
	const BLOCK_CODE_LOW_SURROGATES = 77;
	const BLOCK_CODE_PRIVATE_USE_AREA = 78;
	const BLOCK_CODE_PRIVATE_USE = 78;
	const BLOCK_CODE_CJK_COMPATIBILITY_IDEOGRAPHS = 79;
	const BLOCK_CODE_ALPHABETIC_PRESENTATION_FORMS = 80;
	const BLOCK_CODE_ARABIC_PRESENTATION_FORMS_A = 81;
	const BLOCK_CODE_COMBINING_HALF_MARKS = 82;
	const BLOCK_CODE_CJK_COMPATIBILITY_FORMS = 83;
	const BLOCK_CODE_SMALL_FORM_VARIANTS = 84;
	const BLOCK_CODE_ARABIC_PRESENTATION_FORMS_B = 85;
	const BLOCK_CODE_SPECIALS = 86;
	const BLOCK_CODE_HALFWIDTH_AND_FULLWIDTH_FORMS = 87;
	const BLOCK_CODE_OLD_ITALIC = 88;
	const BLOCK_CODE_GOTHIC = 89;
	const BLOCK_CODE_DESERET = 90;
	const BLOCK_CODE_BYZANTINE_MUSICAL_SYMBOLS = 91;
	const BLOCK_CODE_MUSICAL_SYMBOLS = 92;
	const BLOCK_CODE_MATHEMATICAL_ALPHANUMERIC_SYMBOLS = 93;
	const BLOCK_CODE_CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B = 94;
	const BLOCK_CODE_CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT = 95;
	const BLOCK_CODE_TAGS = 96;
	const BLOCK_CODE_CYRILLIC_SUPPLEMENT = 97;
	const BLOCK_CODE_CYRILLIC_SUPPLEMENTARY = 97;
	const BLOCK_CODE_TAGALOG = 98;
	const BLOCK_CODE_HANUNOO = 99;
	const BLOCK_CODE_BUHID = 100;
	const BLOCK_CODE_TAGBANWA = 101;
	const BLOCK_CODE_MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A = 102;
	const BLOCK_CODE_SUPPLEMENTAL_ARROWS_A = 103;
	const BLOCK_CODE_SUPPLEMENTAL_ARROWS_B = 104;
	const BLOCK_CODE_MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B = 105;
	const BLOCK_CODE_SUPPLEMENTAL_MATHEMATICAL_OPERATORS = 106;
	const BLOCK_CODE_KATAKANA_PHONETIC_EXTENSIONS = 107;
	const BLOCK_CODE_VARIATION_SELECTORS = 108;
	const BLOCK_CODE_SUPPLEMENTARY_PRIVATE_USE_AREA_A = 109;
	const BLOCK_CODE_SUPPLEMENTARY_PRIVATE_USE_AREA_B = 110;
	const BLOCK_CODE_LIMBU = 111;
	const BLOCK_CODE_TAI_LE = 112;
	const BLOCK_CODE_KHMER_SYMBOLS = 113;
	const BLOCK_CODE_PHONETIC_EXTENSIONS = 114;
	const BLOCK_CODE_MISCELLANEOUS_SYMBOLS_AND_ARROWS = 115;
	const BLOCK_CODE_YIJING_HEXAGRAM_SYMBOLS = 116;
	const BLOCK_CODE_LINEAR_B_SYLLABARY = 117;
	const BLOCK_CODE_LINEAR_B_IDEOGRAMS = 118;
	const BLOCK_CODE_AEGEAN_NUMBERS = 119;
	const BLOCK_CODE_UGARITIC = 120;
	const BLOCK_CODE_SHAVIAN = 121;
	const BLOCK_CODE_OSMANYA = 122;
	const BLOCK_CODE_CYPRIOT_SYLLABARY = 123;
	const BLOCK_CODE_TAI_XUAN_JING_SYMBOLS = 124;
	const BLOCK_CODE_VARIATION_SELECTORS_SUPPLEMENT = 125;
	const BLOCK_CODE_ANCIENT_GREEK_MUSICAL_NOTATION = 126;
	const BLOCK_CODE_ANCIENT_GREEK_NUMBERS = 127;
	const BLOCK_CODE_ARABIC_SUPPLEMENT = 128;
	const BLOCK_CODE_BUGINESE = 129;
	const BLOCK_CODE_CJK_STROKES = 130;
	const BLOCK_CODE_COMBINING_DIACRITICAL_MARKS_SUPPLEMENT = 131;
	const BLOCK_CODE_COPTIC = 132;
	const BLOCK_CODE_ETHIOPIC_EXTENDED = 133;
	const BLOCK_CODE_ETHIOPIC_SUPPLEMENT = 134;
	const BLOCK_CODE_GEORGIAN_SUPPLEMENT = 135;
	const BLOCK_CODE_GLAGOLITIC = 136;
	const BLOCK_CODE_KHAROSHTHI = 137;
	const BLOCK_CODE_MODIFIER_TONE_LETTERS = 138;
	const BLOCK_CODE_NEW_TAI_LUE = 139;
	const BLOCK_CODE_OLD_PERSIAN = 140;
	const BLOCK_CODE_PHONETIC_EXTENSIONS_SUPPLEMENT = 141;
	const BLOCK_CODE_SUPPLEMENTAL_PUNCTUATION = 142;
	const BLOCK_CODE_SYLOTI_NAGRI = 143;
	const BLOCK_CODE_TIFINAGH = 144;
	const BLOCK_CODE_VERTICAL_FORMS = 145;
	const BLOCK_CODE_NKO = 146;
	const BLOCK_CODE_BALINESE = 147;
	const BLOCK_CODE_LATIN_EXTENDED_C = 148;
	const BLOCK_CODE_LATIN_EXTENDED_D = 149;
	const BLOCK_CODE_PHAGS_PA = 150;
	const BLOCK_CODE_PHOENICIAN = 151;
	const BLOCK_CODE_CUNEIFORM = 152;
	const BLOCK_CODE_CUNEIFORM_NUMBERS_AND_PUNCTUATION = 153;
	const BLOCK_CODE_COUNTING_ROD_NUMERALS = 154;
	const BLOCK_CODE_SUNDANESE = 155;
	const BLOCK_CODE_LEPCHA = 156;
	const BLOCK_CODE_OL_CHIKI = 157;
	const BLOCK_CODE_CYRILLIC_EXTENDED_A = 158;
	const BLOCK_CODE_VAI = 159;
	const BLOCK_CODE_CYRILLIC_EXTENDED_B = 160;
	const BLOCK_CODE_SAURASHTRA = 161;
	const BLOCK_CODE_KAYAH_LI = 162;
	const BLOCK_CODE_REJANG = 163;
	const BLOCK_CODE_CHAM = 164;
	const BLOCK_CODE_ANCIENT_SYMBOLS = 165;
	const BLOCK_CODE_PHAISTOS_DISC = 166;
	const BLOCK_CODE_LYCIAN = 167;
	const BLOCK_CODE_CARIAN = 168;
	const BLOCK_CODE_LYDIAN = 169;
	const BLOCK_CODE_MAHJONG_TILES = 170;
	const BLOCK_CODE_DOMINO_TILES = 171;
	const BLOCK_CODE_SAMARITAN = 172;
	const BLOCK_CODE_UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_EXTENDED = 173;
	const BLOCK_CODE_TAI_THAM = 174;
	const BLOCK_CODE_VEDIC_EXTENSIONS = 175;
	const BLOCK_CODE_LISU = 176;
	const BLOCK_CODE_BAMUM = 177;
	const BLOCK_CODE_COMMON_INDIC_NUMBER_FORMS = 178;
	const BLOCK_CODE_DEVANAGARI_EXTENDED = 179;
	const BLOCK_CODE_HANGUL_JAMO_EXTENDED_A = 180;
	const BLOCK_CODE_JAVANESE = 181;
	const BLOCK_CODE_MYANMAR_EXTENDED_A = 182;
	const BLOCK_CODE_TAI_VIET = 183;
	const BLOCK_CODE_MEETEI_MAYEK = 184;
	const BLOCK_CODE_HANGUL_JAMO_EXTENDED_B = 185;
	const BLOCK_CODE_IMPERIAL_ARAMAIC = 186;
	const BLOCK_CODE_OLD_SOUTH_ARABIAN = 187;
	const BLOCK_CODE_AVESTAN = 188;
	const BLOCK_CODE_INSCRIPTIONAL_PARTHIAN = 189;
	const BLOCK_CODE_INSCRIPTIONAL_PAHLAVI = 190;
	const BLOCK_CODE_OLD_TURKIC = 191;
	const BLOCK_CODE_RUMI_NUMERAL_SYMBOLS = 192;
	const BLOCK_CODE_KAITHI = 193;
	const BLOCK_CODE_EGYPTIAN_HIEROGLYPHS = 194;
	const BLOCK_CODE_ENCLOSED_ALPHANUMERIC_SUPPLEMENT = 195;
	const BLOCK_CODE_ENCLOSED_IDEOGRAPHIC_SUPPLEMENT = 196;
	const BLOCK_CODE_CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C = 197;
	const BLOCK_CODE_MANDAIC = 198;
	const BLOCK_CODE_BATAK = 199;
	const BLOCK_CODE_ETHIOPIC_EXTENDED_A = 200;
	const BLOCK_CODE_BRAHMI = 201;
	const BLOCK_CODE_BAMUM_SUPPLEMENT = 202;
	const BLOCK_CODE_KANA_SUPPLEMENT = 203;
	const BLOCK_CODE_PLAYING_CARDS = 204;
	const BLOCK_CODE_MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS = 205;
	const BLOCK_CODE_EMOTICONS = 206;
	const BLOCK_CODE_TRANSPORT_AND_MAP_SYMBOLS = 207;
	const BLOCK_CODE_ALCHEMICAL_SYMBOLS = 208;
	const BLOCK_CODE_CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D = 209;
	const BLOCK_CODE_ARABIC_EXTENDED_A = 210;
	const BLOCK_CODE_ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS = 211;
	const BLOCK_CODE_CHAKMA = 212;
	const BLOCK_CODE_MEETEI_MAYEK_EXTENSIONS = 213;
	const BLOCK_CODE_MEROITIC_CURSIVE = 214;
	const BLOCK_CODE_MEROITIC_HIEROGLYPHS = 215;
	const BLOCK_CODE_MIAO = 216;
	const BLOCK_CODE_SHARADA = 217;
	const BLOCK_CODE_SORA_SOMPENG = 218;
	const BLOCK_CODE_SUNDANESE_SUPPLEMENT = 219;
	const BLOCK_CODE_TAKRI = 220;
	const BLOCK_CODE_BASSA_VAH = 221;
	const BLOCK_CODE_CAUCASIAN_ALBANIAN = 222;
	const BLOCK_CODE_COPTIC_EPACT_NUMBERS = 223;
	const BLOCK_CODE_COMBINING_DIACRITICAL_MARKS_EXTENDED = 224;
	const BLOCK_CODE_DUPLOYAN = 225;
	const BLOCK_CODE_ELBASAN = 226;
	const BLOCK_CODE_GEOMETRIC_SHAPES_EXTENDED = 227;
	const BLOCK_CODE_GRANTHA = 228;
	const BLOCK_CODE_KHOJKI = 229;
	const BLOCK_CODE_KHUDAWADI = 230;
	const BLOCK_CODE_LATIN_EXTENDED_E = 231;
	const BLOCK_CODE_LINEAR_A = 232;
	const BLOCK_CODE_MAHAJANI = 233;
	const BLOCK_CODE_MANICHAEAN = 234;
	const BLOCK_CODE_MENDE_KIKAKUI = 235;
	const BLOCK_CODE_MODI = 236;
	const BLOCK_CODE_MRO = 237;
	const BLOCK_CODE_MYANMAR_EXTENDED_B = 238;
	const BLOCK_CODE_NABATAEAN = 239;
	const BLOCK_CODE_OLD_NORTH_ARABIAN = 240;
	const BLOCK_CODE_OLD_PERMIC = 241;
	const BLOCK_CODE_ORNAMENTAL_DINGBATS = 242;
	const BLOCK_CODE_PAHAWH_HMONG = 243;
	const BLOCK_CODE_PALMYRENE = 244;
	const BLOCK_CODE_PAU_CIN_HAU = 245;
	const BLOCK_CODE_PSALTER_PAHLAVI = 246;
	const BLOCK_CODE_SHORTHAND_FORMAT_CONTROLS = 247;
	const BLOCK_CODE_SIDDHAM = 248;
	const BLOCK_CODE_SINHALA_ARCHAIC_NUMBERS = 249;
	const BLOCK_CODE_SUPPLEMENTAL_ARROWS_C = 250;
	const BLOCK_CODE_TIRHUTA = 251;
	const BLOCK_CODE_WARANG_CITI = 252;
	const BLOCK_CODE_COUNT = 328;
	const BLOCK_CODE_INVALID_CODE = -1;
	const BPT_NONE = 0;
	const BPT_OPEN = 1;
	const BPT_CLOSE = 2;
	const BPT_COUNT = 3;
	const EA_NEUTRAL = 0;
	const EA_AMBIGUOUS = 1;
	const EA_HALFWIDTH = 2;
	const EA_FULLWIDTH = 3;
	const EA_NARROW = 4;
	const EA_WIDE = 5;
	const EA_COUNT = 6;
	const UNICODE_CHAR_NAME = 0;
	const UNICODE_10_CHAR_NAME = 1;
	const EXTENDED_CHAR_NAME = 2;
	const CHAR_NAME_ALIAS = 3;
	const CHAR_NAME_CHOICE_COUNT = 4;
	const SHORT_PROPERTY_NAME = 0;
	const LONG_PROPERTY_NAME = 1;
	const PROPERTY_NAME_CHOICE_COUNT = 2;
	const DT_NONE = 0;
	const DT_CANONICAL = 1;
	const DT_COMPAT = 2;
	const DT_CIRCLE = 3;
	const DT_FINAL = 4;
	const DT_FONT = 5;
	const DT_FRACTION = 6;
	const DT_INITIAL = 7;
	const DT_ISOLATED = 8;
	const DT_MEDIAL = 9;
	const DT_NARROW = 10;
	const DT_NOBREAK = 11;
	const DT_SMALL = 12;
	const DT_SQUARE = 13;
	const DT_SUB = 14;
	const DT_SUPER = 15;
	const DT_VERTICAL = 16;
	const DT_WIDE = 17;
	const DT_COUNT = 18;
	const JT_NON_JOINING = 0;
	const JT_JOIN_CAUSING = 1;
	const JT_DUAL_JOINING = 2;
	const JT_LEFT_JOINING = 3;
	const JT_RIGHT_JOINING = 4;
	const JT_TRANSPARENT = 5;
	const JT_COUNT = 6;
	const JG_NO_JOINING_GROUP = 0;
	const JG_AIN = 1;
	const JG_ALAPH = 2;
	const JG_ALEF = 3;
	const JG_BEH = 4;
	const JG_BETH = 5;
	const JG_DAL = 6;
	const JG_DALATH_RISH = 7;
	const JG_E = 8;
	const JG_FEH = 9;
	const JG_FINAL_SEMKATH = 10;
	const JG_GAF = 11;
	const JG_GAMAL = 12;
	const JG_HAH = 13;
	const JG_TEH_MARBUTA_GOAL = 14;
	const JG_HAMZA_ON_HEH_GOAL = 14;
	const JG_HE = 15;
	const JG_HEH = 16;
	const JG_HEH_GOAL = 17;
	const JG_HETH = 18;
	const JG_KAF = 19;
	const JG_KAPH = 20;
	const JG_KNOTTED_HEH = 21;
	const JG_LAM = 22;
	const JG_LAMADH = 23;
	const JG_MEEM = 24;
	const JG_MIM = 25;
	const JG_NOON = 26;
	const JG_NUN = 27;
	const JG_PE = 28;
	const JG_QAF = 29;
	const JG_QAPH = 30;
	const JG_REH = 31;
	const JG_REVERSED_PE = 32;
	const JG_SAD = 33;
	const JG_SADHE = 34;
	const JG_SEEN = 35;
	const JG_SEMKATH = 36;
	const JG_SHIN = 37;
	const JG_SWASH_KAF = 38;
	const JG_SYRIAC_WAW = 39;
	const JG_TAH = 40;
	const JG_TAW = 41;
	const JG_TEH_MARBUTA = 42;
	const JG_TETH = 43;
	const JG_WAW = 44;
	const JG_YEH = 45;
	const JG_YEH_BARREE = 46;
	const JG_YEH_WITH_TAIL = 47;
	const JG_YUDH = 48;
	const JG_YUDH_HE = 49;
	const JG_ZAIN = 50;
	const JG_FE = 51;
	const JG_KHAPH = 52;
	const JG_ZHAIN = 53;
	const JG_BURUSHASKI_YEH_BARREE = 54;
	const JG_FARSI_YEH = 55;
	const JG_NYA = 56;
	const JG_ROHINGYA_YEH = 57;
	const JG_MANICHAEAN_ALEPH = 58;
	const JG_MANICHAEAN_AYIN = 59;
	const JG_MANICHAEAN_BETH = 60;
	const JG_MANICHAEAN_DALETH = 61;
	const JG_MANICHAEAN_DHAMEDH = 62;
	const JG_MANICHAEAN_FIVE = 63;
	const JG_MANICHAEAN_GIMEL = 64;
	const JG_MANICHAEAN_HETH = 65;
	const JG_MANICHAEAN_HUNDRED = 66;
	const JG_MANICHAEAN_KAPH = 67;
	const JG_MANICHAEAN_LAMEDH = 68;
	const JG_MANICHAEAN_MEM = 69;
	const JG_MANICHAEAN_NUN = 70;
	const JG_MANICHAEAN_ONE = 71;
	const JG_MANICHAEAN_PE = 72;
	const JG_MANICHAEAN_QOPH = 73;
	const JG_MANICHAEAN_RESH = 74;
	const JG_MANICHAEAN_SADHE = 75;
	const JG_MANICHAEAN_SAMEKH = 76;
	const JG_MANICHAEAN_TAW = 77;
	const JG_MANICHAEAN_TEN = 78;
	const JG_MANICHAEAN_TETH = 79;
	const JG_MANICHAEAN_THAMEDH = 80;
	const JG_MANICHAEAN_TWENTY = 81;
	const JG_MANICHAEAN_WAW = 82;
	const JG_MANICHAEAN_YODH = 83;
	const JG_MANICHAEAN_ZAYIN = 84;
	const JG_STRAIGHT_WAW = 85;
	const JG_COUNT = 104;
	const GCB_OTHER = 0;
	const GCB_CONTROL = 1;
	const GCB_CR = 2;
	const GCB_EXTEND = 3;
	const GCB_L = 4;
	const GCB_LF = 5;
	const GCB_LV = 6;
	const GCB_LVT = 7;
	const GCB_T = 8;
	const GCB_V = 9;
	const GCB_SPACING_MARK = 10;
	const GCB_PREPEND = 11;
	const GCB_REGIONAL_INDICATOR = 12;
	const GCB_COUNT = 18;
	const WB_OTHER = 0;
	const WB_ALETTER = 1;
	const WB_FORMAT = 2;
	const WB_KATAKANA = 3;
	const WB_MIDLETTER = 4;
	const WB_MIDNUM = 5;
	const WB_NUMERIC = 6;
	const WB_EXTENDNUMLET = 7;
	const WB_CR = 8;
	const WB_EXTEND = 9;
	const WB_LF = 10;
	const WB_MIDNUMLET = 11;
	const WB_NEWLINE = 12;
	const WB_REGIONAL_INDICATOR = 13;
	const WB_HEBREW_LETTER = 14;
	const WB_SINGLE_QUOTE = 15;
	const WB_DOUBLE_QUOTE = 16;
	const WB_COUNT = 23;
	const SB_OTHER = 0;
	const SB_ATERM = 1;
	const SB_CLOSE = 2;
	const SB_FORMAT = 3;
	const SB_LOWER = 4;
	const SB_NUMERIC = 5;
	const SB_OLETTER = 6;
	const SB_SEP = 7;
	const SB_SP = 8;
	const SB_STERM = 9;
	const SB_UPPER = 10;
	const SB_CR = 11;
	const SB_EXTEND = 12;
	const SB_LF = 13;
	const SB_SCONTINUE = 14;
	const SB_COUNT = 15;
	const LB_UNKNOWN = 0;
	const LB_AMBIGUOUS = 1;
	const LB_ALPHABETIC = 2;
	const LB_BREAK_BOTH = 3;
	const LB_BREAK_AFTER = 4;
	const LB_BREAK_BEFORE = 5;
	const LB_MANDATORY_BREAK = 6;
	const LB_CONTINGENT_BREAK = 7;
	const LB_CLOSE_PUNCTUATION = 8;
	const LB_COMBINING_MARK = 9;
	const LB_CARRIAGE_RETURN = 10;
	const LB_EXCLAMATION = 11;
	const LB_GLUE = 12;
	const LB_HYPHEN = 13;
	const LB_IDEOGRAPHIC = 14;
	const LB_INSEPARABLE = 15;
	const LB_INSEPERABLE = 15;
	const LB_INFIX_NUMERIC = 16;
	const LB_LINE_FEED = 17;
	const LB_NONSTARTER = 18;
	const LB_NUMERIC = 19;
	const LB_OPEN_PUNCTUATION = 20;
	const LB_POSTFIX_NUMERIC = 21;
	const LB_PREFIX_NUMERIC = 22;
	const LB_QUOTATION = 23;
	const LB_COMPLEX_CONTEXT = 24;
	const LB_SURROGATE = 25;
	const LB_SPACE = 26;
	const LB_BREAK_SYMBOLS = 27;
	const LB_ZWSPACE = 28;
	const LB_NEXT_LINE = 29;
	const LB_WORD_JOINER = 30;
	const LB_H2 = 31;
	const LB_H3 = 32;
	const LB_JL = 33;
	const LB_JT = 34;
	const LB_JV = 35;
	const LB_CLOSE_PARENTHESIS = 36;
	const LB_CONDITIONAL_JAPANESE_STARTER = 37;
	const LB_HEBREW_LETTER = 38;
	const LB_REGIONAL_INDICATOR = 39;
	const LB_COUNT = 43;
	const NT_NONE = 0;
	const NT_DECIMAL = 1;
	const NT_DIGIT = 2;
	const NT_NUMERIC = 3;
	const NT_COUNT = 4;
	const HST_NOT_APPLICABLE = 0;
	const HST_LEADING_JAMO = 1;
	const HST_VOWEL_JAMO = 2;
	const HST_TRAILING_JAMO = 3;
	const HST_LV_SYLLABLE = 4;
	const HST_LVT_SYLLABLE = 5;
	const HST_COUNT = 6;
	const FOLD_CASE_DEFAULT = 0;
	const FOLD_CASE_EXCLUDE_SPECIAL_I = 1;


	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 * @param int $property
	 */
	public static function hasBinaryProperty (string|int $codepoint, int $property) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function charAge (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function charDigitValue (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function charDirection (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param int $type [optional]
	 */
	public static function charFromName (string $name, int $type = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function charMirror (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 * @param int $type [optional]
	 */
	public static function charName (string|int $codepoint, int $type = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function charType (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function chr (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 * @param int $base [optional]
	 */
	public static function digit (string|int $codepoint, int $base = 10) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $start
	 * @param string|int $end
	 * @param callable $callback
	 * @param int $type [optional]
	 */
	public static function enumCharNames (string|int $start, string|int $end, callable $callback, int $type = 0) {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public static function enumCharTypes (callable $callback) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 * @param int $options [optional]
	 */
	public static function foldCase (string|int $codepoint, int $options = 0) {}

	/**
	 * {@inheritdoc}
	 * @param int $digit
	 * @param int $base [optional]
	 */
	public static function forDigit (int $digit, int $base = 10) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function getBidiPairedBracket (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function getBlockCode (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function getCombiningClass (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function getFC_NFKC_Closure (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param int $property
	 */
	public static function getIntPropertyMaxValue (int $property) {}

	/**
	 * {@inheritdoc}
	 * @param int $property
	 */
	public static function getIntPropertyMinValue (int $property) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 * @param int $property
	 */
	public static function getIntPropertyValue (string|int $codepoint, int $property) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function getNumericValue (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string $alias
	 */
	public static function getPropertyEnum (string $alias) {}

	/**
	 * {@inheritdoc}
	 * @param int $property
	 * @param int $type [optional]
	 */
	public static function getPropertyName (int $property, int $type = 1) {}

	/**
	 * {@inheritdoc}
	 * @param int $property
	 * @param string $name
	 */
	public static function getPropertyValueEnum (int $property, string $name) {}

	/**
	 * {@inheritdoc}
	 * @param int $property
	 * @param int $value
	 * @param int $type [optional]
	 */
	public static function getPropertyValueName (int $property, int $value, int $type = 1) {}

	/**
	 * {@inheritdoc}
	 */
	public static function getUnicodeVersion () {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isalnum (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isalpha (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isbase (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isblank (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function iscntrl (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isdefined (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isdigit (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isgraph (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isIDIgnorable (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isIDPart (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isIDStart (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isISOControl (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isJavaIDPart (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isJavaIDStart (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isJavaSpaceChar (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function islower (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isMirrored (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isprint (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function ispunct (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isspace (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function istitle (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isUAlphabetic (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isULowercase (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isupper (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isUUppercase (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isUWhiteSpace (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isWhitespace (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function isxdigit (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $character
	 */
	public static function ord (string|int $character) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function tolower (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function totitle (string|int $codepoint) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $codepoint
	 */
	public static function toupper (string|int $codepoint) {}

}

/**
 * {@inheritdoc}
 * @param mixed $timezone [optional]
 * @param string|null $locale [optional]
 */
function intlcal_create_instance ($timezone = NULL, ?string $locale = NULL): ?IntlCalendar {}

/**
 * {@inheritdoc}
 * @param string $keyword
 * @param string $locale
 * @param bool $onlyCommon
 */
function intlcal_get_keyword_values_for_locale (string $keyword, string $locale, bool $onlyCommon): IntlIterator|false {}

/**
 * {@inheritdoc}
 */
function intlcal_get_now (): float {}

/**
 * {@inheritdoc}
 */
function intlcal_get_available_locales (): array {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $field
 */
function intlcal_get (IntlCalendar $calendar, int $field): int|false {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 */
function intlcal_get_time (IntlCalendar $calendar): float|false {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param float $timestamp
 */
function intlcal_set_time (IntlCalendar $calendar, float $timestamp): bool {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $field
 * @param int $value
 */
function intlcal_add (IntlCalendar $calendar, int $field, int $value): bool {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param mixed $timezone
 */
function intlcal_set_time_zone (IntlCalendar $calendar, $timezone = null): bool {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param IntlCalendar $other
 */
function intlcal_after (IntlCalendar $calendar, IntlCalendar $other): bool {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param IntlCalendar $other
 */
function intlcal_before (IntlCalendar $calendar, IntlCalendar $other): bool {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $year
 * @param int $month
 * @param int $dayOfMonth [optional]
 * @param int $hour [optional]
 * @param int $minute [optional]
 * @param int $second [optional]
 */
function intlcal_set (IntlCalendar $calendar, int $year, int $month, int $dayOfMonth = NULL, int $hour = NULL, int $minute = NULL, int $second = NULL): true {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $field
 * @param mixed $value
 */
function intlcal_roll (IntlCalendar $calendar, int $field, $value = null): bool {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int|null $field [optional]
 */
function intlcal_clear (IntlCalendar $calendar, ?int $field = NULL): true {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param float $timestamp
 * @param int $field
 */
function intlcal_field_difference (IntlCalendar $calendar, float $timestamp, int $field): int|false {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $field
 */
function intlcal_get_actual_maximum (IntlCalendar $calendar, int $field): int|false {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $field
 */
function intlcal_get_actual_minimum (IntlCalendar $calendar, int $field): int|false {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $dayOfWeek
 */
function intlcal_get_day_of_week_type (IntlCalendar $calendar, int $dayOfWeek): int|false {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 */
function intlcal_get_first_day_of_week (IntlCalendar $calendar): int|false {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $field
 */
function intlcal_get_least_maximum (IntlCalendar $calendar, int $field): int|false {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $field
 */
function intlcal_get_greatest_minimum (IntlCalendar $calendar, int $field): int|false {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $type
 */
function intlcal_get_locale (IntlCalendar $calendar, int $type): string|false {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $field
 */
function intlcal_get_maximum (IntlCalendar $calendar, int $field): int|false {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 */
function intlcal_get_minimal_days_in_first_week (IntlCalendar $calendar): int|false {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $days
 */
function intlcal_set_minimal_days_in_first_week (IntlCalendar $calendar, int $days): true {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $field
 */
function intlcal_get_minimum (IntlCalendar $calendar, int $field): int|false {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 */
function intlcal_get_time_zone (IntlCalendar $calendar): IntlTimeZone|false {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 */
function intlcal_get_type (IntlCalendar $calendar): string {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $dayOfWeek
 */
function intlcal_get_weekend_transition (IntlCalendar $calendar, int $dayOfWeek): int|false {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 */
function intlcal_in_daylight_time (IntlCalendar $calendar): bool {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 */
function intlcal_is_lenient (IntlCalendar $calendar): bool {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $field
 */
function intlcal_is_set (IntlCalendar $calendar, int $field): bool {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param IntlCalendar $other
 */
function intlcal_is_equivalent_to (IntlCalendar $calendar, IntlCalendar $other): bool {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param float|null $timestamp [optional]
 */
function intlcal_is_weekend (IntlCalendar $calendar, ?float $timestamp = NULL): bool {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $dayOfWeek
 */
function intlcal_set_first_day_of_week (IntlCalendar $calendar, int $dayOfWeek): true {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param bool $lenient
 */
function intlcal_set_lenient (IntlCalendar $calendar, bool $lenient): true {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 */
function intlcal_get_repeated_wall_time_option (IntlCalendar $calendar): int {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param IntlCalendar $other
 */
function intlcal_equals (IntlCalendar $calendar, IntlCalendar $other): bool {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 */
function intlcal_get_skipped_wall_time_option (IntlCalendar $calendar): int {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $option
 */
function intlcal_set_repeated_wall_time_option (IntlCalendar $calendar, int $option): true {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $option
 */
function intlcal_set_skipped_wall_time_option (IntlCalendar $calendar, int $option): true {}

/**
 * {@inheritdoc}
 * @param DateTime|string $datetime
 * @param string|null $locale [optional]
 */
function intlcal_from_date_time (DateTime|string $datetime, ?string $locale = NULL): ?IntlCalendar {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 */
function intlcal_to_date_time (IntlCalendar $calendar): DateTime|false {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 */
function intlcal_get_error_code (IntlCalendar $calendar): int|false {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 */
function intlcal_get_error_message (IntlCalendar $calendar): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $timezoneOrYear [optional]
 * @param mixed $localeOrMonth [optional]
 * @param mixed $day [optional]
 * @param mixed $hour [optional]
 * @param mixed $minute [optional]
 * @param mixed $second [optional]
 */
function intlgregcal_create_instance ($timezoneOrYear = NULL, $localeOrMonth = NULL, $day = NULL, $hour = NULL, $minute = NULL, $second = NULL): ?IntlGregorianCalendar {}

/**
 * {@inheritdoc}
 * @param IntlGregorianCalendar $calendar
 * @param float $timestamp
 */
function intlgregcal_set_gregorian_change (IntlGregorianCalendar $calendar, float $timestamp): bool {}

/**
 * {@inheritdoc}
 * @param IntlGregorianCalendar $calendar
 */
function intlgregcal_get_gregorian_change (IntlGregorianCalendar $calendar): float {}

/**
 * {@inheritdoc}
 * @param IntlGregorianCalendar $calendar
 * @param int $year
 */
function intlgregcal_is_leap_year (IntlGregorianCalendar $calendar, int $year): bool {}

/**
 * {@inheritdoc}
 * @param string $locale
 */
function collator_create (string $locale): ?Collator {}

/**
 * {@inheritdoc}
 * @param Collator $object
 * @param string $string1
 * @param string $string2
 */
function collator_compare (Collator $object, string $string1, string $string2): int|false {}

/**
 * {@inheritdoc}
 * @param Collator $object
 * @param int $attribute
 */
function collator_get_attribute (Collator $object, int $attribute): int|false {}

/**
 * {@inheritdoc}
 * @param Collator $object
 * @param int $attribute
 * @param int $value
 */
function collator_set_attribute (Collator $object, int $attribute, int $value): bool {}

/**
 * {@inheritdoc}
 * @param Collator $object
 */
function collator_get_strength (Collator $object): int {}

/**
 * {@inheritdoc}
 * @param Collator $object
 * @param int $strength
 */
function collator_set_strength (Collator $object, int $strength): bool {}

/**
 * {@inheritdoc}
 * @param Collator $object
 * @param array $array
 * @param int $flags [optional]
 */
function collator_sort (Collator $object, array &$array, int $flags = 0): bool {}

/**
 * {@inheritdoc}
 * @param Collator $object
 * @param array $array
 */
function collator_sort_with_sort_keys (Collator $object, array &$array): bool {}

/**
 * {@inheritdoc}
 * @param Collator $object
 * @param array $array
 * @param int $flags [optional]
 */
function collator_asort (Collator $object, array &$array, int $flags = 0): bool {}

/**
 * {@inheritdoc}
 * @param Collator $object
 * @param int $type
 */
function collator_get_locale (Collator $object, int $type): string|false {}

/**
 * {@inheritdoc}
 * @param Collator $object
 */
function collator_get_error_code (Collator $object): int|false {}

/**
 * {@inheritdoc}
 * @param Collator $object
 */
function collator_get_error_message (Collator $object): string|false {}

/**
 * {@inheritdoc}
 * @param Collator $object
 * @param string $string
 */
function collator_get_sort_key (Collator $object, string $string): string|false {}

/**
 * {@inheritdoc}
 */
function intl_get_error_code (): int {}

/**
 * {@inheritdoc}
 */
function intl_get_error_message (): string {}

/**
 * {@inheritdoc}
 * @param int $errorCode
 */
function intl_is_failure (int $errorCode): bool {}

/**
 * {@inheritdoc}
 * @param int $errorCode
 */
function intl_error_name (int $errorCode): string {}

/**
 * {@inheritdoc}
 * @param string|null $locale
 * @param int $dateType [optional]
 * @param int $timeType [optional]
 * @param mixed $timezone [optional]
 * @param IntlCalendar|int|null $calendar [optional]
 * @param string|null $pattern [optional]
 */
function datefmt_create (?string $locale = null, int $dateType = 0, int $timeType = 0, $timezone = NULL, IntlCalendar|int|null $calendar = NULL, ?string $pattern = NULL): ?IntlDateFormatter {}

/**
 * {@inheritdoc}
 * @param IntlDateFormatter $formatter
 */
function datefmt_get_datetype (IntlDateFormatter $formatter): int|false {}

/**
 * {@inheritdoc}
 * @param IntlDateFormatter $formatter
 */
function datefmt_get_timetype (IntlDateFormatter $formatter): int|false {}

/**
 * {@inheritdoc}
 * @param IntlDateFormatter $formatter
 */
function datefmt_get_calendar (IntlDateFormatter $formatter): int|false {}

/**
 * {@inheritdoc}
 * @param IntlDateFormatter $formatter
 * @param IntlCalendar|int|null $calendar
 */
function datefmt_set_calendar (IntlDateFormatter $formatter, IntlCalendar|int|null $calendar = null): bool {}

/**
 * {@inheritdoc}
 * @param IntlDateFormatter $formatter
 */
function datefmt_get_timezone_id (IntlDateFormatter $formatter): string|false {}

/**
 * {@inheritdoc}
 * @param IntlDateFormatter $formatter
 */
function datefmt_get_calendar_object (IntlDateFormatter $formatter): IntlCalendar|false|null {}

/**
 * {@inheritdoc}
 * @param IntlDateFormatter $formatter
 */
function datefmt_get_timezone (IntlDateFormatter $formatter): IntlTimeZone|false {}

/**
 * {@inheritdoc}
 * @param IntlDateFormatter $formatter
 * @param mixed $timezone
 */
function datefmt_set_timezone (IntlDateFormatter $formatter, $timezone = null): bool {}

/**
 * {@inheritdoc}
 * @param IntlDateFormatter $formatter
 * @param string $pattern
 */
function datefmt_set_pattern (IntlDateFormatter $formatter, string $pattern): bool {}

/**
 * {@inheritdoc}
 * @param IntlDateFormatter $formatter
 */
function datefmt_get_pattern (IntlDateFormatter $formatter): string|false {}

/**
 * {@inheritdoc}
 * @param IntlDateFormatter $formatter
 * @param int $type [optional]
 */
function datefmt_get_locale (IntlDateFormatter $formatter, int $type = 0): string|false {}

/**
 * {@inheritdoc}
 * @param IntlDateFormatter $formatter
 * @param bool $lenient
 */
function datefmt_set_lenient (IntlDateFormatter $formatter, bool $lenient): void {}

/**
 * {@inheritdoc}
 * @param IntlDateFormatter $formatter
 */
function datefmt_is_lenient (IntlDateFormatter $formatter): bool {}

/**
 * {@inheritdoc}
 * @param IntlDateFormatter $formatter
 * @param mixed $datetime
 */
function datefmt_format (IntlDateFormatter $formatter, $datetime = null): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $datetime
 * @param mixed $format [optional]
 * @param string|null $locale [optional]
 */
function datefmt_format_object ($datetime = null, $format = NULL, ?string $locale = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param IntlDateFormatter $formatter
 * @param string $string
 * @param mixed $offset [optional]
 */
function datefmt_parse (IntlDateFormatter $formatter, string $string, &$offset = NULL): int|float|false {}

/**
 * {@inheritdoc}
 * @param IntlDateFormatter $formatter
 * @param string $string
 * @param mixed $offset [optional]
 */
function datefmt_localtime (IntlDateFormatter $formatter, string $string, &$offset = NULL): array|false {}

/**
 * {@inheritdoc}
 * @param IntlDateFormatter $formatter
 */
function datefmt_get_error_code (IntlDateFormatter $formatter): int {}

/**
 * {@inheritdoc}
 * @param IntlDateFormatter $formatter
 */
function datefmt_get_error_message (IntlDateFormatter $formatter): string {}

/**
 * {@inheritdoc}
 * @param string $locale
 * @param int $style
 * @param string|null $pattern [optional]
 */
function numfmt_create (string $locale, int $style, ?string $pattern = NULL): ?NumberFormatter {}

/**
 * {@inheritdoc}
 * @param NumberFormatter $formatter
 * @param int|float $num
 * @param int $type [optional]
 */
function numfmt_format (NumberFormatter $formatter, int|float $num, int $type = 0): string|false {}

/**
 * {@inheritdoc}
 * @param NumberFormatter $formatter
 * @param string $string
 * @param int $type [optional]
 * @param mixed $offset [optional]
 */
function numfmt_parse (NumberFormatter $formatter, string $string, int $type = 3, &$offset = NULL): int|float|false {}

/**
 * {@inheritdoc}
 * @param NumberFormatter $formatter
 * @param float $amount
 * @param string $currency
 */
function numfmt_format_currency (NumberFormatter $formatter, float $amount, string $currency): string|false {}

/**
 * {@inheritdoc}
 * @param NumberFormatter $formatter
 * @param string $string
 * @param mixed $currency
 * @param mixed $offset [optional]
 */
function numfmt_parse_currency (NumberFormatter $formatter, string $string, &$currency = null, &$offset = NULL): float|false {}

/**
 * {@inheritdoc}
 * @param NumberFormatter $formatter
 * @param int $attribute
 * @param int|float $value
 */
function numfmt_set_attribute (NumberFormatter $formatter, int $attribute, int|float $value): bool {}

/**
 * {@inheritdoc}
 * @param NumberFormatter $formatter
 * @param int $attribute
 */
function numfmt_get_attribute (NumberFormatter $formatter, int $attribute): int|float|false {}

/**
 * {@inheritdoc}
 * @param NumberFormatter $formatter
 * @param int $attribute
 * @param string $value
 */
function numfmt_set_text_attribute (NumberFormatter $formatter, int $attribute, string $value): bool {}

/**
 * {@inheritdoc}
 * @param NumberFormatter $formatter
 * @param int $attribute
 */
function numfmt_get_text_attribute (NumberFormatter $formatter, int $attribute): string|false {}

/**
 * {@inheritdoc}
 * @param NumberFormatter $formatter
 * @param int $symbol
 * @param string $value
 */
function numfmt_set_symbol (NumberFormatter $formatter, int $symbol, string $value): bool {}

/**
 * {@inheritdoc}
 * @param NumberFormatter $formatter
 * @param int $symbol
 */
function numfmt_get_symbol (NumberFormatter $formatter, int $symbol): string|false {}

/**
 * {@inheritdoc}
 * @param NumberFormatter $formatter
 * @param string $pattern
 */
function numfmt_set_pattern (NumberFormatter $formatter, string $pattern): bool {}

/**
 * {@inheritdoc}
 * @param NumberFormatter $formatter
 */
function numfmt_get_pattern (NumberFormatter $formatter): string|false {}

/**
 * {@inheritdoc}
 * @param NumberFormatter $formatter
 * @param int $type [optional]
 */
function numfmt_get_locale (NumberFormatter $formatter, int $type = 0): string|false {}

/**
 * {@inheritdoc}
 * @param NumberFormatter $formatter
 */
function numfmt_get_error_code (NumberFormatter $formatter): int {}

/**
 * {@inheritdoc}
 * @param NumberFormatter $formatter
 */
function numfmt_get_error_message (NumberFormatter $formatter): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function grapheme_strlen (string $string): int|false|null {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param int $offset [optional]
 */
function grapheme_strpos (string $haystack, string $needle, int $offset = 0): int|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param int $offset [optional]
 */
function grapheme_stripos (string $haystack, string $needle, int $offset = 0): int|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param int $offset [optional]
 */
function grapheme_strrpos (string $haystack, string $needle, int $offset = 0): int|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param int $offset [optional]
 */
function grapheme_strripos (string $haystack, string $needle, int $offset = 0): int|false {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $offset
 * @param int|null $length [optional]
 */
function grapheme_substr (string $string, int $offset, ?int $length = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param bool $beforeNeedle [optional]
 */
function grapheme_strstr (string $haystack, string $needle, bool $beforeNeedle = false): string|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param bool $beforeNeedle [optional]
 */
function grapheme_stristr (string $haystack, string $needle, bool $beforeNeedle = false): string|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param int $size
 * @param int $type [optional]
 * @param int $offset [optional]
 * @param mixed $next [optional]
 */
function grapheme_extract (string $haystack, int $size, int $type = 0, int $offset = 0, &$next = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $domain
 * @param int $flags [optional]
 * @param int $variant [optional]
 * @param mixed $idna_info [optional]
 */
function idn_to_ascii (string $domain, int $flags = 0, int $variant = 1, &$idna_info = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $domain
 * @param int $flags [optional]
 * @param int $variant [optional]
 * @param mixed $idna_info [optional]
 */
function idn_to_utf8 (string $domain, int $flags = 0, int $variant = 1, &$idna_info = NULL): string|false {}

/**
 * {@inheritdoc}
 */
function locale_get_default (): string {}

/**
 * {@inheritdoc}
 * @param string $locale
 */
function locale_set_default (string $locale): bool {}

/**
 * {@inheritdoc}
 * @param string $locale
 */
function locale_get_primary_language (string $locale): ?string {}

/**
 * {@inheritdoc}
 * @param string $locale
 */
function locale_get_script (string $locale): ?string {}

/**
 * {@inheritdoc}
 * @param string $locale
 */
function locale_get_region (string $locale): ?string {}

/**
 * {@inheritdoc}
 * @param string $locale
 */
function locale_get_keywords (string $locale): array|false|null {}

/**
 * {@inheritdoc}
 * @param string $locale
 * @param string|null $displayLocale [optional]
 */
function locale_get_display_script (string $locale, ?string $displayLocale = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $locale
 * @param string|null $displayLocale [optional]
 */
function locale_get_display_region (string $locale, ?string $displayLocale = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $locale
 * @param string|null $displayLocale [optional]
 */
function locale_get_display_name (string $locale, ?string $displayLocale = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $locale
 * @param string|null $displayLocale [optional]
 */
function locale_get_display_language (string $locale, ?string $displayLocale = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $locale
 * @param string|null $displayLocale [optional]
 */
function locale_get_display_variant (string $locale, ?string $displayLocale = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param array $subtags
 */
function locale_compose (array $subtags): string|false {}

/**
 * {@inheritdoc}
 * @param string $locale
 */
function locale_parse (string $locale): ?array {}

/**
 * {@inheritdoc}
 * @param string $locale
 */
function locale_get_all_variants (string $locale): ?array {}

/**
 * {@inheritdoc}
 * @param string $languageTag
 * @param string $locale
 * @param bool $canonicalize [optional]
 */
function locale_filter_matches (string $languageTag, string $locale, bool $canonicalize = false): ?bool {}

/**
 * {@inheritdoc}
 * @param string $locale
 */
function locale_canonicalize (string $locale): ?string {}

/**
 * {@inheritdoc}
 * @param array $languageTag
 * @param string $locale
 * @param bool $canonicalize [optional]
 * @param string|null $defaultLocale [optional]
 */
function locale_lookup (array $languageTag, string $locale, bool $canonicalize = false, ?string $defaultLocale = NULL): ?string {}

/**
 * {@inheritdoc}
 * @param string $header
 */
function locale_accept_from_http (string $header): string|false {}

/**
 * {@inheritdoc}
 * @param string $locale
 * @param string $pattern
 */
function msgfmt_create (string $locale, string $pattern): ?MessageFormatter {}

/**
 * {@inheritdoc}
 * @param MessageFormatter $formatter
 * @param array $values
 */
function msgfmt_format (MessageFormatter $formatter, array $values): string|false {}

/**
 * {@inheritdoc}
 * @param string $locale
 * @param string $pattern
 * @param array $values
 */
function msgfmt_format_message (string $locale, string $pattern, array $values): string|false {}

/**
 * {@inheritdoc}
 * @param MessageFormatter $formatter
 * @param string $string
 */
function msgfmt_parse (MessageFormatter $formatter, string $string): array|false {}

/**
 * {@inheritdoc}
 * @param string $locale
 * @param string $pattern
 * @param string $message
 */
function msgfmt_parse_message (string $locale, string $pattern, string $message): array|false {}

/**
 * {@inheritdoc}
 * @param MessageFormatter $formatter
 * @param string $pattern
 */
function msgfmt_set_pattern (MessageFormatter $formatter, string $pattern): bool {}

/**
 * {@inheritdoc}
 * @param MessageFormatter $formatter
 */
function msgfmt_get_pattern (MessageFormatter $formatter): string|false {}

/**
 * {@inheritdoc}
 * @param MessageFormatter $formatter
 */
function msgfmt_get_locale (MessageFormatter $formatter): string {}

/**
 * {@inheritdoc}
 * @param MessageFormatter $formatter
 */
function msgfmt_get_error_code (MessageFormatter $formatter): int {}

/**
 * {@inheritdoc}
 * @param MessageFormatter $formatter
 */
function msgfmt_get_error_message (MessageFormatter $formatter): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $form [optional]
 */
function normalizer_normalize (string $string, int $form = 16): string|false {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $form [optional]
 */
function normalizer_is_normalized (string $string, int $form = 16): bool {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $form [optional]
 */
function normalizer_get_raw_decomposition (string $string, int $form = 16): ?string {}

/**
 * {@inheritdoc}
 * @param string|null $locale
 * @param string|null $bundle
 * @param bool $fallback [optional]
 */
function resourcebundle_create (?string $locale = null, ?string $bundle = null, bool $fallback = true): ?ResourceBundle {}

/**
 * {@inheritdoc}
 * @param ResourceBundle $bundle
 * @param mixed $index
 * @param bool $fallback [optional]
 */
function resourcebundle_get (ResourceBundle $bundle, $index = null, bool $fallback = true): mixed {}

/**
 * {@inheritdoc}
 * @param ResourceBundle $bundle
 */
function resourcebundle_count (ResourceBundle $bundle): int {}

/**
 * {@inheritdoc}
 * @param string $bundle
 */
function resourcebundle_locales (string $bundle): array|false {}

/**
 * {@inheritdoc}
 * @param ResourceBundle $bundle
 */
function resourcebundle_get_error_code (ResourceBundle $bundle): int {}

/**
 * {@inheritdoc}
 * @param ResourceBundle $bundle
 */
function resourcebundle_get_error_message (ResourceBundle $bundle): string {}

/**
 * {@inheritdoc}
 * @param string $timezoneId
 */
function intltz_count_equivalent_ids (string $timezoneId): int|false {}

/**
 * {@inheritdoc}
 */
function intltz_create_default (): IntlTimeZone {}

/**
 * {@inheritdoc}
 * @param mixed $countryOrRawOffset [optional]
 */
function intltz_create_enumeration ($countryOrRawOffset = NULL): IntlIterator|false {}

/**
 * {@inheritdoc}
 * @param string $timezoneId
 */
function intltz_create_time_zone (string $timezoneId): ?IntlTimeZone {}

/**
 * {@inheritdoc}
 * @param int $type
 * @param string|null $region [optional]
 * @param int|null $rawOffset [optional]
 */
function intltz_create_time_zone_id_enumeration (int $type, ?string $region = NULL, ?int $rawOffset = NULL): IntlIterator|false {}

/**
 * {@inheritdoc}
 * @param DateTimeZone $timezone
 */
function intltz_from_date_time_zone (DateTimeZone $timezone): ?IntlTimeZone {}

/**
 * {@inheritdoc}
 * @param string $timezoneId
 * @param mixed $isSystemId [optional]
 */
function intltz_get_canonical_id (string $timezoneId, &$isSystemId = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param IntlTimeZone $timezone
 * @param bool $dst [optional]
 * @param int $style [optional]
 * @param string|null $locale [optional]
 */
function intltz_get_display_name (IntlTimeZone $timezone, bool $dst = false, int $style = 2, ?string $locale = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param IntlTimeZone $timezone
 */
function intltz_get_dst_savings (IntlTimeZone $timezone): int {}

/**
 * {@inheritdoc}
 * @param string $timezoneId
 * @param int $offset
 */
function intltz_get_equivalent_id (string $timezoneId, int $offset): string|false {}

/**
 * {@inheritdoc}
 * @param IntlTimeZone $timezone
 */
function intltz_get_error_code (IntlTimeZone $timezone): int|false {}

/**
 * {@inheritdoc}
 * @param IntlTimeZone $timezone
 */
function intltz_get_error_message (IntlTimeZone $timezone): string|false {}

/**
 * {@inheritdoc}
 */
function intltz_get_gmt (): IntlTimeZone {}

/**
 * {@inheritdoc}
 * @param IntlTimeZone $timezone
 */
function intltz_get_id (IntlTimeZone $timezone): string|false {}

/**
 * {@inheritdoc}
 * @param IntlTimeZone $timezone
 * @param float $timestamp
 * @param bool $local
 * @param mixed $rawOffset
 * @param mixed $dstOffset
 */
function intltz_get_offset (IntlTimeZone $timezone, float $timestamp, bool $local, &$rawOffset = null, &$dstOffset = null): bool {}

/**
 * {@inheritdoc}
 * @param IntlTimeZone $timezone
 */
function intltz_get_raw_offset (IntlTimeZone $timezone): int {}

/**
 * {@inheritdoc}
 * @param string $timezoneId
 */
function intltz_get_region (string $timezoneId): string|false {}

/**
 * {@inheritdoc}
 */
function intltz_get_tz_data_version (): string|false {}

/**
 * {@inheritdoc}
 */
function intltz_get_unknown (): IntlTimeZone {}

/**
 * {@inheritdoc}
 * @param string $timezoneId
 */
function intltz_get_windows_id (string $timezoneId): string|false {}

/**
 * {@inheritdoc}
 * @param string $timezoneId
 * @param string|null $region [optional]
 */
function intltz_get_id_for_windows_id (string $timezoneId, ?string $region = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param IntlTimeZone $timezone
 * @param IntlTimeZone $other
 */
function intltz_has_same_rules (IntlTimeZone $timezone, IntlTimeZone $other): bool {}

/**
 * {@inheritdoc}
 * @param IntlTimeZone $timezone
 */
function intltz_to_date_time_zone (IntlTimeZone $timezone): DateTimeZone|false {}

/**
 * {@inheritdoc}
 * @param IntlTimeZone $timezone
 */
function intltz_use_daylight_time (IntlTimeZone $timezone): bool {}

/**
 * {@inheritdoc}
 * @param string $id
 * @param int $direction [optional]
 */
function transliterator_create (string $id, int $direction = 0): ?Transliterator {}

/**
 * {@inheritdoc}
 * @param string $rules
 * @param int $direction [optional]
 */
function transliterator_create_from_rules (string $rules, int $direction = 0): ?Transliterator {}

/**
 * {@inheritdoc}
 */
function transliterator_list_ids (): array|false {}

/**
 * {@inheritdoc}
 * @param Transliterator $transliterator
 */
function transliterator_create_inverse (Transliterator $transliterator): ?Transliterator {}

/**
 * {@inheritdoc}
 * @param Transliterator|string $transliterator
 * @param string $string
 * @param int $start [optional]
 * @param int $end [optional]
 */
function transliterator_transliterate (Transliterator|string $transliterator, string $string, int $start = 0, int $end = -1): string|false {}

/**
 * {@inheritdoc}
 * @param Transliterator $transliterator
 */
function transliterator_get_error_code (Transliterator $transliterator): int|false {}

/**
 * {@inheritdoc}
 * @param Transliterator $transliterator
 */
function transliterator_get_error_message (Transliterator $transliterator): string|false {}

define ('INTL_MAX_LOCALE_LEN', 156);
define ('INTL_ICU_VERSION', 73.2);
define ('INTL_ICU_DATA_VERSION', 73.2);
define ('GRAPHEME_EXTR_COUNT', 0);
define ('GRAPHEME_EXTR_MAXBYTES', 1);
define ('GRAPHEME_EXTR_MAXCHARS', 2);
define ('IDNA_DEFAULT', 0);
define ('IDNA_ALLOW_UNASSIGNED', 1);
define ('IDNA_USE_STD3_RULES', 2);
define ('IDNA_CHECK_BIDI', 4);
define ('IDNA_CHECK_CONTEXTJ', 8);
define ('IDNA_NONTRANSITIONAL_TO_ASCII', 16);
define ('IDNA_NONTRANSITIONAL_TO_UNICODE', 32);
define ('INTL_IDNA_VARIANT_UTS46', 1);
define ('IDNA_ERROR_EMPTY_LABEL', 1);
define ('IDNA_ERROR_LABEL_TOO_LONG', 2);
define ('IDNA_ERROR_DOMAIN_NAME_TOO_LONG', 4);
define ('IDNA_ERROR_LEADING_HYPHEN', 8);
define ('IDNA_ERROR_TRAILING_HYPHEN', 16);
define ('IDNA_ERROR_HYPHEN_3_4', 32);
define ('IDNA_ERROR_LEADING_COMBINING_MARK', 64);
define ('IDNA_ERROR_DISALLOWED', 128);
define ('IDNA_ERROR_PUNYCODE', 256);
define ('IDNA_ERROR_LABEL_HAS_DOT', 512);
define ('IDNA_ERROR_INVALID_ACE_LABEL', 1024);
define ('IDNA_ERROR_BIDI', 2048);
define ('IDNA_ERROR_CONTEXTJ', 4096);
define ('ULOC_ACTUAL_LOCALE', 0);
define ('ULOC_VALID_LOCALE', 1);
define ('U_USING_FALLBACK_WARNING', -128);
define ('U_ERROR_WARNING_START', -128);
define ('U_USING_DEFAULT_WARNING', -127);
define ('U_SAFECLONE_ALLOCATED_WARNING', -126);
define ('U_STATE_OLD_WARNING', -125);
define ('U_STRING_NOT_TERMINATED_WARNING', -124);
define ('U_SORT_KEY_TOO_SHORT_WARNING', -123);
define ('U_AMBIGUOUS_ALIAS_WARNING', -122);
define ('U_DIFFERENT_UCA_VERSION', -121);
define ('U_ERROR_WARNING_LIMIT', -119);
define ('U_ZERO_ERROR', 0);
define ('U_ILLEGAL_ARGUMENT_ERROR', 1);
define ('U_MISSING_RESOURCE_ERROR', 2);
define ('U_INVALID_FORMAT_ERROR', 3);
define ('U_FILE_ACCESS_ERROR', 4);
define ('U_INTERNAL_PROGRAM_ERROR', 5);
define ('U_MESSAGE_PARSE_ERROR', 6);
define ('U_MEMORY_ALLOCATION_ERROR', 7);
define ('U_INDEX_OUTOFBOUNDS_ERROR', 8);
define ('U_PARSE_ERROR', 9);
define ('U_INVALID_CHAR_FOUND', 10);
define ('U_TRUNCATED_CHAR_FOUND', 11);
define ('U_ILLEGAL_CHAR_FOUND', 12);
define ('U_INVALID_TABLE_FORMAT', 13);
define ('U_INVALID_TABLE_FILE', 14);
define ('U_BUFFER_OVERFLOW_ERROR', 15);
define ('U_UNSUPPORTED_ERROR', 16);
define ('U_RESOURCE_TYPE_MISMATCH', 17);
define ('U_ILLEGAL_ESCAPE_SEQUENCE', 18);
define ('U_UNSUPPORTED_ESCAPE_SEQUENCE', 19);
define ('U_NO_SPACE_AVAILABLE', 20);
define ('U_CE_NOT_FOUND_ERROR', 21);
define ('U_PRIMARY_TOO_LONG_ERROR', 22);
define ('U_STATE_TOO_OLD_ERROR', 23);
define ('U_TOO_MANY_ALIASES_ERROR', 24);
define ('U_ENUM_OUT_OF_SYNC_ERROR', 25);
define ('U_INVARIANT_CONVERSION_ERROR', 26);
define ('U_INVALID_STATE_ERROR', 27);
define ('U_COLLATOR_VERSION_MISMATCH', 28);
define ('U_USELESS_COLLATOR_ERROR', 29);
define ('U_NO_WRITE_PERMISSION', 30);
define ('U_STANDARD_ERROR_LIMIT', 32);
define ('U_BAD_VARIABLE_DEFINITION', 65536);
define ('U_PARSE_ERROR_START', 65536);
define ('U_MALFORMED_RULE', 65537);
define ('U_MALFORMED_SET', 65538);
define ('U_MALFORMED_SYMBOL_REFERENCE', 65539);
define ('U_MALFORMED_UNICODE_ESCAPE', 65540);
define ('U_MALFORMED_VARIABLE_DEFINITION', 65541);
define ('U_MALFORMED_VARIABLE_REFERENCE', 65542);
define ('U_MISMATCHED_SEGMENT_DELIMITERS', 65543);
define ('U_MISPLACED_ANCHOR_START', 65544);
define ('U_MISPLACED_CURSOR_OFFSET', 65545);
define ('U_MISPLACED_QUANTIFIER', 65546);
define ('U_MISSING_OPERATOR', 65547);
define ('U_MISSING_SEGMENT_CLOSE', 65548);
define ('U_MULTIPLE_ANTE_CONTEXTS', 65549);
define ('U_MULTIPLE_CURSORS', 65550);
define ('U_MULTIPLE_POST_CONTEXTS', 65551);
define ('U_TRAILING_BACKSLASH', 65552);
define ('U_UNDEFINED_SEGMENT_REFERENCE', 65553);
define ('U_UNDEFINED_VARIABLE', 65554);
define ('U_UNQUOTED_SPECIAL', 65555);
define ('U_UNTERMINATED_QUOTE', 65556);
define ('U_RULE_MASK_ERROR', 65557);
define ('U_MISPLACED_COMPOUND_FILTER', 65558);
define ('U_MULTIPLE_COMPOUND_FILTERS', 65559);
define ('U_INVALID_RBT_SYNTAX', 65560);
define ('U_INVALID_PROPERTY_PATTERN', 65561);
define ('U_MALFORMED_PRAGMA', 65562);
define ('U_UNCLOSED_SEGMENT', 65563);
define ('U_ILLEGAL_CHAR_IN_SEGMENT', 65564);
define ('U_VARIABLE_RANGE_EXHAUSTED', 65565);
define ('U_VARIABLE_RANGE_OVERLAP', 65566);
define ('U_ILLEGAL_CHARACTER', 65567);
define ('U_INTERNAL_TRANSLITERATOR_ERROR', 65568);
define ('U_INVALID_ID', 65569);
define ('U_INVALID_FUNCTION', 65570);
define ('U_PARSE_ERROR_LIMIT', 65571);
define ('U_UNEXPECTED_TOKEN', 65792);
define ('U_FMT_PARSE_ERROR_START', 65792);
define ('U_MULTIPLE_DECIMAL_SEPARATORS', 65793);
define ('U_MULTIPLE_DECIMAL_SEPERATORS', 65793);
define ('U_MULTIPLE_EXPONENTIAL_SYMBOLS', 65794);
define ('U_MALFORMED_EXPONENTIAL_PATTERN', 65795);
define ('U_MULTIPLE_PERCENT_SYMBOLS', 65796);
define ('U_MULTIPLE_PERMILL_SYMBOLS', 65797);
define ('U_MULTIPLE_PAD_SPECIFIERS', 65798);
define ('U_PATTERN_SYNTAX_ERROR', 65799);
define ('U_ILLEGAL_PAD_POSITION', 65800);
define ('U_UNMATCHED_BRACES', 65801);
define ('U_UNSUPPORTED_PROPERTY', 65802);
define ('U_UNSUPPORTED_ATTRIBUTE', 65803);
define ('U_FMT_PARSE_ERROR_LIMIT', 65812);
define ('U_BRK_INTERNAL_ERROR', 66048);
define ('U_BRK_ERROR_START', 66048);
define ('U_BRK_HEX_DIGITS_EXPECTED', 66049);
define ('U_BRK_SEMICOLON_EXPECTED', 66050);
define ('U_BRK_RULE_SYNTAX', 66051);
define ('U_BRK_UNCLOSED_SET', 66052);
define ('U_BRK_ASSIGN_ERROR', 66053);
define ('U_BRK_VARIABLE_REDFINITION', 66054);
define ('U_BRK_MISMATCHED_PAREN', 66055);
define ('U_BRK_NEW_LINE_IN_QUOTED_STRING', 66056);
define ('U_BRK_UNDEFINED_VARIABLE', 66057);
define ('U_BRK_INIT_ERROR', 66058);
define ('U_BRK_RULE_EMPTY_SET', 66059);
define ('U_BRK_UNRECOGNIZED_OPTION', 66060);
define ('U_BRK_MALFORMED_RULE_TAG', 66061);
define ('U_BRK_ERROR_LIMIT', 66062);
define ('U_REGEX_INTERNAL_ERROR', 66304);
define ('U_REGEX_ERROR_START', 66304);
define ('U_REGEX_RULE_SYNTAX', 66305);
define ('U_REGEX_INVALID_STATE', 66306);
define ('U_REGEX_BAD_ESCAPE_SEQUENCE', 66307);
define ('U_REGEX_PROPERTY_SYNTAX', 66308);
define ('U_REGEX_UNIMPLEMENTED', 66309);
define ('U_REGEX_MISMATCHED_PAREN', 66310);
define ('U_REGEX_NUMBER_TOO_BIG', 66311);
define ('U_REGEX_BAD_INTERVAL', 66312);
define ('U_REGEX_MAX_LT_MIN', 66313);
define ('U_REGEX_INVALID_BACK_REF', 66314);
define ('U_REGEX_INVALID_FLAG', 66315);
define ('U_REGEX_LOOK_BEHIND_LIMIT', 66316);
define ('U_REGEX_SET_CONTAINS_STRING', 66317);
define ('U_REGEX_ERROR_LIMIT', 66326);
define ('U_IDNA_PROHIBITED_ERROR', 66560);
define ('U_IDNA_ERROR_START', 66560);
define ('U_IDNA_UNASSIGNED_ERROR', 66561);
define ('U_IDNA_CHECK_BIDI_ERROR', 66562);
define ('U_IDNA_STD3_ASCII_RULES_ERROR', 66563);
define ('U_IDNA_ACE_PREFIX_ERROR', 66564);
define ('U_IDNA_VERIFICATION_ERROR', 66565);
define ('U_IDNA_LABEL_TOO_LONG_ERROR', 66566);
define ('U_IDNA_ZERO_LENGTH_LABEL_ERROR', 66567);
define ('U_IDNA_DOMAIN_NAME_TOO_LONG_ERROR', 66568);
define ('U_IDNA_ERROR_LIMIT', 66569);
define ('U_STRINGPREP_PROHIBITED_ERROR', 66560);
define ('U_STRINGPREP_UNASSIGNED_ERROR', 66561);
define ('U_STRINGPREP_CHECK_BIDI_ERROR', 66562);
define ('U_ERROR_LIMIT', 66818);

// End of intl v.8.3.0
