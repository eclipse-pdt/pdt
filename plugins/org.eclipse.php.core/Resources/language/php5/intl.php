<?php

// Start of intl v.1.0.0

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
	 * @param arg1
	 */
	public function __construct ($arg1) {}

	/**
	 * @param arg1
	 */
	public static function create ($arg1) {}

	/**
	 * @param arg1
	 * @param arg2
	 */
	public function compare ($arg1, $arg2) {}

	/**
	 * @param arr
	 * @param flags[optional]
	 */
	public function sort (array &$arr, $flags) {}

	/**
	 * @param arr
	 * @param flags[optional]
	 */
	public function sortWithSortKeys (array &$arr, $flags) {}

	/**
	 * @param arr
	 * @param flags[optional]
	 */
	public function asort (array &$arr, $flags) {}

	/**
	 * @param arg1
	 */
	public function getAttribute ($arg1) {}

	/**
	 * @param arg1
	 * @param arg2
	 */
	public function setAttribute ($arg1, $arg2) {}

	public function getStrength () {}

	/**
	 * @param arg1
	 */
	public function setStrength ($arg1) {}

	/**
	 * @param arg1
	 */
	public function getLocale ($arg1) {}

	public function getErrorCode () {}

	public function getErrorMessage () {}

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
	const PATTERN_RULEBASED = 8;
	const IGNORE = 0;
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
	 * @param locale
	 * @param style
	 * @param pattern[optional]
	 */
	public function __construct ($locale, $style, $pattern) {}

	/**
	 * @param locale
	 * @param style
	 * @param pattern[optional]
	 */
	public static function create ($locale, $style, $pattern) {}

	/**
	 * @param num
	 * @param type[optional]
	 */
	public function format ($num, $type) {}

	/**
	 * @param string
	 * @param type[optional]
	 * @param position[optional]
	 */
	public function parse ($string, $type, &$position) {}

	/**
	 * @param num
	 * @param currency
	 */
	public function formatCurrency ($num, $currency) {}

	/**
	 * @param string
	 * @param currency
	 * @param position[optional]
	 */
	public function parseCurrency ($string, &$currency, &$position) {}

	/**
	 * @param attr
	 * @param value
	 */
	public function setAttribute ($attr, $value) {}

	/**
	 * @param attr
	 */
	public function getAttribute ($attr) {}

	/**
	 * @param attr
	 * @param value
	 */
	public function setTextAttribute ($attr, $value) {}

	/**
	 * @param attr
	 */
	public function getTextAttribute ($attr) {}

	/**
	 * @param attr
	 * @param symbol
	 */
	public function setSymbol ($attr, $symbol) {}

	/**
	 * @param attr
	 */
	public function getSymbol ($attr) {}

	/**
	 * @param pattern
	 */
	public function setPattern ($pattern) {}

	public function getPattern () {}

	/**
	 * @param type[optional]
	 */
	public function getLocale ($type) {}

	public function getErrorCode () {}

	public function getErrorMessage () {}

}

class Normalizer  {
	const NONE = 1;
	const FORM_D = 2;
	const NFD = 2;
	const FORM_KD = 3;
	const NFKD = 3;
	const FORM_C = 4;
	const NFC = 4;
	const FORM_KC = 5;
	const NFKC = 5;


	/**
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public static function normalize ($arg1, $arg2, $arg3) {}

	/**
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public static function isNormalized ($arg1, $arg2, $arg3) {}

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


	public static function getDefault () {}

	/**
	 * @param arg1
	 */
	public static function setDefault ($arg1) {}

	/**
	 * @param arg1
	 */
	public static function getPrimaryLanguage ($arg1) {}

	/**
	 * @param arg1
	 */
	public static function getScript ($arg1) {}

	/**
	 * @param arg1
	 */
	public static function getRegion ($arg1) {}

	/**
	 * @param arg1
	 */
	public static function getKeywords ($arg1) {}

	/**
	 * @param arg1
	 * @param arg2
	 */
	public static function getDisplayScript ($arg1, $arg2) {}

	/**
	 * @param arg1
	 * @param arg2
	 */
	public static function getDisplayRegion ($arg1, $arg2) {}

	/**
	 * @param arg1
	 * @param arg2
	 */
	public static function getDisplayName ($arg1, $arg2) {}

	/**
	 * @param arg1
	 * @param arg2
	 */
	public static function getDisplayLanguage ($arg1, $arg2) {}

	/**
	 * @param arg1
	 * @param arg2
	 */
	public static function getDisplayVariant ($arg1, $arg2) {}

	/**
	 * @param arg1
	 */
	public static function composeLocale ($arg1) {}

	/**
	 * @param arg1
	 */
	public static function parseLocale ($arg1) {}

	/**
	 * @param arg1
	 */
	public static function getAllVariants ($arg1) {}

	/**
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public static function filterMatches ($arg1, $arg2, $arg3) {}

	/**
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg4
	 */
	public static function lookup ($arg1, $arg2, $arg3, $arg4) {}

	/**
	 * @param arg1
	 */
	public static function canonicalize ($arg1) {}

	/**
	 * @param arg1
	 */
	public static function acceptFromHttp ($arg1) {}

}

class MessageFormatter  {

	/**
	 * @param locale
	 * @param pattern
	 */
	public function __construct ($locale, $pattern) {}

	/**
	 * @param locale
	 * @param pattern
	 */
	public static function create ($locale, $pattern) {}

	/**
	 * @param args
	 */
	public function format ($args) {}

	/**
	 * @param locale
	 * @param pattern
	 * @param args
	 */
	public static function formatMessage ($locale, $pattern, $args) {}

	/**
	 * @param source
	 */
	public function parse ($source) {}

	/**
	 * @param locale
	 * @param pattern
	 * @param args
	 */
	public static function parseMessage ($locale, $pattern, $args) {}

	/**
	 * @param pattern
	 */
	public function setPattern ($pattern) {}

	public function getPattern () {}

	public function getLocale () {}

	public function getErrorCode () {}

	public function getErrorMessage () {}

}

class IntlDateFormatter  {
	const FULL = 0;
	const LONG = 1;
	const MEDIUM = 2;
	const SHORT = 3;
	const NONE = -1;
	const GREGORIAN = 1;
	const TRADITIONAL = 0;


	/**
	 * @param locale
	 * @param datetype
	 * @param timetype
	 * @param timezone[optional]
	 * @param calendar[optional]
	 * @param pattern[optional]
	 */
	public function __construct ($locale, $datetype, $timetype, $timezone, $calendar, $pattern) {}

	/**
	 * @param locale
	 * @param datetype
	 * @param timetype
	 * @param timezone[optional]
	 * @param calendar[optional]
	 * @param pattern[optional]
	 */
	public static function create ($locale, $datetype, $timetype, $timezone, $calendar, $pattern) {}

	public function getDateType () {}

	public function getTimeType () {}

	public function getCalendar () {}

	/**
	 * @param which
	 */
	public function setCalendar ($which) {}

	public function getTimeZoneId () {}

	/**
	 * @param zone
	 */
	public function setTimeZoneId ($zone) {}

	/**
	 * @param pattern
	 */
	public function setPattern ($pattern) {}

	public function getPattern () {}

	public function getLocale () {}

	/**
	 * @param lenient
	 */
	public function setLenient ($lenient) {}

	public function isLenient () {}

	/**
	 * @param args[optional]
	 * @param array[optional]
	 */
	public function format ($args, $array) {}

	/**
	 * @param string
	 * @param position[optional]
	 */
	public function parse ($string, &$position) {}

	/**
	 * @param string
	 * @param position[optional]
	 */
	public function localtime ($string, &$position) {}

	public function getErrorCode () {}

	public function getErrorMessage () {}

}

/**
 * @param arg1
 */
function collator_create ($arg1) {}

/**
 * @param object Collator
 * @param arg1
 * @param arg2
 */
function collator_compare (Collator $object, $arg1, $arg2) {}

/**
 * @param object Collator
 * @param arg1
 */
function collator_get_attribute (Collator $object, $arg1) {}

/**
 * @param object Collator
 * @param arg1
 * @param arg2
 */
function collator_set_attribute (Collator $object, $arg1, $arg2) {}

/**
 * @param object Collator
 */
function collator_get_strength (Collator $object) {}

/**
 * @param object Collator
 * @param arg1
 */
function collator_set_strength (Collator $object, $arg1) {}

/**
 * @param object Collator
 * @param arr
 * @param sort_flags[optional]
 */
function collator_sort (Collator $objectarray , &$arr, $sort_flags) {}

/**
 * @param object Collator
 * @param arr
 * @param sort_flags[optional]
 */
function collator_sort_with_sort_keys (Collator $objectarray , &$arr, $sort_flags) {}

/**
 * @param object Collator
 * @param arr
 * @param sort_flags[optional]
 */
function collator_asort (Collator $objectarray , &$arr, $sort_flags) {}

/**
 * @param object Collator
 * @param arg1
 */
function collator_get_locale (Collator $object, $arg1) {}

/**
 * @param object Collator
 */
function collator_get_error_code (Collator $object) {}

/**
 * @param object Collator
 */
function collator_get_error_message (Collator $object) {}

/**
 * @param locale
 * @param style
 * @param pattern[optional]
 */
function numfmt_create ($locale, $style, $pattern) {}

/**
 * @param nf
 * @param num
 * @param type[optional]
 */
function numfmt_format ($nf, $num, $type) {}

/**
 * @param formatter
 * @param string
 * @param type[optional]
 * @param position[optional]
 */
function numfmt_parse ($formatter, $string, $type, &$position) {}

/**
 * @param nf
 * @param num
 * @param currency
 */
function numfmt_format_currency ($nf, $num, $currency) {}

/**
 * @param formatter
 * @param string
 * @param currency
 * @param position[optional]
 */
function numfmt_parse_currency ($formatter, $string, &$currency, &$position) {}

/**
 * @param nf
 * @param attr
 * @param value
 */
function numfmt_set_attribute ($nf, $attr, $value) {}

/**
 * @param nf
 * @param attr
 */
function numfmt_get_attribute ($nf, $attr) {}

/**
 * @param nf
 * @param attr
 * @param value
 */
function numfmt_set_text_attribute ($nf, $attr, $value) {}

/**
 * @param nf
 * @param attr
 */
function numfmt_get_text_attribute ($nf, $attr) {}

/**
 * @param nf
 * @param attr
 * @param symbol
 */
function numfmt_set_symbol ($nf, $attr, $symbol) {}

/**
 * @param nf
 * @param attr
 */
function numfmt_get_symbol ($nf, $attr) {}

/**
 * @param nf
 * @param pattern
 */
function numfmt_set_pattern ($nf, $pattern) {}

/**
 * @param nf
 */
function numfmt_get_pattern ($nf) {}

/**
 * @param nf
 * @param type[optional]
 */
function numfmt_get_locale ($nf, $type) {}

/**
 * @param nf
 */
function numfmt_get_error_code ($nf) {}

/**
 * @param nf
 */
function numfmt_get_error_message ($nf) {}

/**
 * @param input
 * @param form[optional]
 */
function normalizer_normalize ($input, $form) {}

/**
 * @param input
 * @param form[optional]
 */
function normalizer_is_normalized ($input, $form) {}

/**
 * Get the default Locale
 * @link http://php.net/manual/en/function.locale-get-default.php
 * @return string a string with the current Locale.
 */
function locale_get_default () {}

/**
 * Set the default Locale
 * @link http://php.net/manual/en/function.locale-set-default.php
 * @param name string <p>
 * The new Locale name. A comprehensive list of the supported locales is
 * available at .
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function locale_set_default ($name) {}

/**
 * @param arg1
 */
function locale_get_primary_language ($arg1) {}

/**
 * @param arg1
 */
function locale_get_script ($arg1) {}

/**
 * @param arg1
 */
function locale_get_region ($arg1) {}

/**
 * @param arg1
 */
function locale_get_keywords ($arg1) {}

/**
 * @param arg1
 * @param arg2
 */
function locale_get_display_script ($arg1, $arg2) {}

/**
 * @param arg1
 * @param arg2
 */
function locale_get_display_region ($arg1, $arg2) {}

/**
 * @param arg1
 * @param arg2
 */
function locale_get_display_name ($arg1, $arg2) {}

/**
 * @param arg1
 * @param arg2
 */
function locale_get_display_language ($arg1, $arg2) {}

/**
 * @param arg1
 * @param arg2
 */
function locale_get_display_variant ($arg1, $arg2) {}

/**
 * @param arg1
 */
function locale_compose ($arg1) {}

/**
 * @param arg1
 */
function locale_parse ($arg1) {}

/**
 * @param arg1
 */
function locale_get_all_variants ($arg1) {}

/**
 * @param arg1
 * @param arg2
 * @param arg3
 */
function locale_filter_matches ($arg1, $arg2, $arg3) {}

/**
 * @param arg1
 */
function locale_canonicalize ($arg1) {}

/**
 * @param arg1
 * @param arg2
 * @param arg3
 * @param arg4
 */
function locale_lookup ($arg1, $arg2, $arg3, $arg4) {}

/**
 * @param arg1
 */
function locale_accept_from_http ($arg1) {}

/**
 * @param locale
 * @param pattern
 */
function msgfmt_create ($locale, $pattern) {}

/**
 * @param nf
 * @param args
 */
function msgfmt_format ($nf, $args) {}

/**
 * @param locale
 * @param pattern
 * @param args
 */
function msgfmt_format_message ($locale, $pattern, $args) {}

/**
 * @param nf
 * @param source
 */
function msgfmt_parse ($nf, $source) {}

/**
 * @param locale
 * @param pattern
 * @param source
 */
function msgfmt_parse_message ($locale, $pattern, $source) {}

/**
 * @param mf
 * @param pattern
 */
function msgfmt_set_pattern ($mf, $pattern) {}

/**
 * @param mf
 */
function msgfmt_get_pattern ($mf) {}

/**
 * @param mf
 */
function msgfmt_get_locale ($mf) {}

/**
 * @param nf
 */
function msgfmt_get_error_code ($nf) {}

/**
 * @param coll
 */
function msgfmt_get_error_message ($coll) {}

/**
 * @param locale
 * @param date_type
 * @param time_type
 * @param timezone_str[optional]
 * @param calendar[optional]
 * @param pattern[optional]
 */
function datefmt_create ($locale, $date_type, $time_type, $timezone_str, $calendar, $pattern) {}

/**
 * @param mf
 */
function datefmt_get_datetype ($mf) {}

/**
 * @param mf
 */
function datefmt_get_timetype ($mf) {}

/**
 * @param mf
 */
function datefmt_get_calendar ($mf) {}

/**
 * @param mf
 * @param calendar
 */
function datefmt_set_calendar ($mf, $calendar) {}

/**
 * @param mf
 */
function datefmt_get_locale ($mf) {}

/**
 * @param mf
 */
function datefmt_get_timezone_id ($mf) {}

/**
 * @param mf
 */
function datefmt_set_timezone_id ($mf) {}

/**
 * @param mf
 */
function datefmt_get_pattern ($mf) {}

/**
 * @param mf
 * @param pattern
 */
function datefmt_set_pattern ($mf, $pattern) {}

/**
 * @param mf
 */
function datefmt_is_lenient ($mf) {}

/**
 * @param mf
 */
function datefmt_set_lenient ($mf) {}

/**
 * @param args[optional]
 * @param array[optional]
 */
function datefmt_format ($args, $array) {}

/**
 * @param formatter
 * @param string
 * @param position[optional]
 */
function datefmt_parse ($formatter, $string, &$position) {}

/**
 * @param formatter
 * @param string
 * @param position[optional]
 */
function datefmt_localtime ($formatter, $string, &$position) {}

/**
 * @param nf
 */
function datefmt_get_error_code ($nf) {}

/**
 * @param coll
 */
function datefmt_get_error_message ($coll) {}

/**
 * Get string length in grapheme units
 * @link http://php.net/manual/en/function.grapheme-strlen.php
 * @param input string <p>
 * The string being measured for length. It must be a valid UTF-8 string.
 * </p>
 * @return int The length of the string on success, and 0 if the string is empty.
 */
function grapheme_strlen ($input) {}

/**
 * Find position (in grapheme units) of first occurrence of a string
 * @link http://php.net/manual/en/function.grapheme-strpos.php
 * @param haystack string <p>
 * The string to look in. Must be valid UTF-8.
 * </p>
 * @param needle string <p>
 * The string to look for. Must be valid UTF-8.
 * </p>
 * @param offset int[optional] <p>
 * The optional $offset parameter allows you to specify where in $haystack to
 * start searching as an offset in grapheme units (not bytes or characters). If not given, the default is zero. 
 * The position returned is still relative to the beginning of haystack
 * regardless of the value of $offset.
 * </p>
 * @return int the position as an integer. If needle is not found, strpos() will return boolean FALSE.
 */
function grapheme_strpos ($haystack, $needle, $offset = null) {}

/**
 * Find position (in grapheme units) of first occurrence of a case-insensitive string
 * @link http://php.net/manual/en/function.grapheme-stripos.php
 * @param haystack string <p>
 *  The string to look in. Must be valid UTF-8.
 * </p>
 * @param needle string <p>
 *  The string to look for. Must be valid UTF-8. 
 * </p>
 * @param offset int[optional] <p>
 * The optional $offset parameter allows you to specify where in haystack to
 * start searching as an offset in grapheme units (not bytes or characters). If not given, the default is zero. 
 * The position returned is still relative to the beginning of haystack
 * regardless of the value of $offset.
 * </p>
 * @return int the position as an integer. If needle is not found, grapheme_stripos() will return boolean FALSE.
 */
function grapheme_stripos ($haystack, $needle, $offset = null) {}

/**
 * Find position (in grapheme units) of last occurrence of a string
 * @link http://php.net/manual/en/function.grapheme-strrpos.php
 * @param haystack string <p>
 * The string to look in. Must be valid UTF-8.
 * </p>
 * @param needle string <p>
 * The string to look for. Must be valid UTF-8.
 * </p>
 * @param offset int[optional] <p>
 * The optional $offset parameter allows you to specify where in $haystack to
 * start searching as an offset in grapheme units (not bytes or characters). If not given, the default is zero. 
 * The position returned is still relative to the beginning of haystack
 * regardless of the value of $offset.
 * </p>
 * @return int the position as an integer. If needle is not found, grapheme_strrpos() will return boolean FALSE.
 */
function grapheme_strrpos ($haystack, $needle, $offset = null) {}

/**
 * Find position (in grapheme units) of last occurrence of a case-insensitive string
 * @link http://php.net/manual/en/function.grapheme-strripos.php
 * @param haystack string <p>
 * The string to look in. Must be valid UTF-8.
 * </p>
 * @param needle string <p>
 * The string to look for. Must be valid UTF-8.
 * </p>
 * @param offset int[optional] <p>
 * The optional $offset parameter allows you to specify where in $haystack to
 * start searching as an offset in grapheme units (not bytes or characters). If not given, the default is zero. 
 * The position returned is still relative to the beginning of haystack
 * regardless of the value of $offset.
 * </p>
 * @return int the position as an integer. If needle is not found, grapheme_strripos() will return boolean FALSE.
 */
function grapheme_strripos ($haystack, $needle, $offset = null) {}

/**
 * Return part of a string
 * @link http://php.net/manual/en/function.grapheme-substr.php
 * @param string string <p>
 * The input string. Must be valid UTF-8. 
 * </p>
 * @param start int <p>
 * Start position in default grapheme units.
 * If $start is non-negative, the returned string will start at the	 
 * $start'th position in $string, counting from zero. If $start is negative,
 * the returned string will start at the $start'th grapheme unit from the 
 * end of string.
 * </p>
 * @param length int[optional] <p>
 * Length in grapheme units.
 * If $length is given and is positive, the string returned will contain	 						
 * at most $length grapheme units beginning from $start (depending on the 
 * length of string). If $string is less than or equal to $start grapheme
 * units long, FALSE will be returned. If $length is given and is negative, then
 * that many grapheme units will be omitted from the end of string (after the
 * start position has been calculated when a start is negative). If $start
 * denotes a position beyond this truncation, an empty string will be returned. 
 * </p>
 * @return int the extracted part of $string.
 */
function grapheme_substr ($string, $start, $length = null) {}

/**
 * Returns part of haystack string from the first occurrence of needle to the end of haystack.
 * @link http://php.net/manual/en/function.grapheme-strstr.php
 * @param haystack string <p>
 * The input string. Must be valid UTF-8. 
 * </p>
 * @param needle string <p>
 * The string to look for. Must be valid UTF-8. 
 * </p>
 * @param before_needle bool[optional] <p>
 * If TRUE (the default is FALSE), grapheme_strstr() returns the part of the	 
 * haystack before the first occurrence of the needle.
 * </p>
 * @return string the portion of string, or FALSE if needle is not found.
 */
function grapheme_strstr ($haystack, $needle, $before_needle = null) {}

/**
 * Returns part of haystack string from the first occurrence of case-insensitive needle to the end of haystack.
 * @link http://php.net/manual/en/function.grapheme-stristr.php
 * @param haystack string <p>
 * The input string. Must be valid UTF-8.
 * </p>
 * @param needle string <p>
 * The string to look for. Must be valid UTF-8.
 * </p>
 * @param before_needle bool[optional] <p>
 * If TRUE (the default is FALSE), grapheme_strstr() returns the part of the	 
 * haystack before the first occurrence of the needle.
 * </p>
 * @return string the portion of $haystack, or FALSE if $needle is not found.
 */
function grapheme_stristr ($haystack, $needle, $before_needle = null) {}

/**
 * Function to extract a sequence of default grapheme clusters from a text buffer, which must be encoded in UTF-8.
 * @link http://php.net/manual/en/function.grapheme-extract.php
 * @param haystack string <p>
 * String to search.
 * </p>
 * @param size int <p>
 * Maximum number items - based on the $extract_type - to return.
 * </p>
 * @param extract_type int[optional] <p>
 * Defines the type of units referred to by the $size parameter:
 * </p>
 * <p>
 * GRAPHEME_EXTR_COUNT (default) - $size is the number of default
 * grapheme clusters to extract.
 * GRAPHEME_EXTR_MAXBYTES - $size is the maximum number of bytes
 * returned.
 * GRAPHEME_EXTR_MAXCHARS - $size is the maximum number of UTF-8
 * characters returned.
 * </p>
 * @param start int[optional] <p>
 * Starting position in $haystack in bytes - if given, it must be zero or a
 * positive value that is less than or equal to the length of $haystack in
 * bytes. The default is zero. If $start does not point to the first byte of a UTF-8
 * character, the start position is moved to the next character boundary.
 * </p>
 * @param next int[optional] <p>
 * Reference to a value that will be set to the next starting position.
 * When the call returns, this may point to the first byte position past the end of the string.
 * </p>
 * @return string A string starting at offset $start and ending on a default grapheme cluster
 * boundary that conforms to the $size and $extract_type specified.
 */
function grapheme_extract ($haystack, $size, $extract_type = null, $start = null, &$next = null) {}

/**
 * Get the last error code
 * @link http://php.net/manual/en/function.intl-get-error-code.php
 * @return int Error code returned by the last API function call.
 */
function intl_get_error_code () {}

/**
 * Get description of the last error
 * @link http://php.net/manual/en/function.intl-get-error-message.php
 * @return string Description of an error occurred in the last API function call.
 */
function intl_get_error_message () {}

/**
 * Check whether the given error code indicates failure
 * @link http://php.net/manual/en/function.intl-is-failure.php
 * @param error_code int <p>
 * is a value that returned by functions:
 * intl_get_error_code,
 * collator_get_error_code .
 * </p>
 * @return bool true if it the code indicates some failure, and false
 * in case of success or a warning.
 */
function intl_is_failure ($error_code) {}

/**
 * Get symbolic name for a given error code
 * @link http://php.net/manual/en/function.intl-error-name.php
 * @param error_code int <p>
 * ICU error code.
 * </p>
 * @return string The returned string will be the same as the name of the error code
 * constant.
 */
function intl_error_name ($error_code) {}


/**
 * Limit on locale length, set to 64 in PHP code. Locale names longer 
 * than this limit will not be accepted.
 * @link http://php.net/manual/en/intl.constants.php
 */
define ('INTL_MAX_LOCALE_LEN', 80);
define ('ULOC_ACTUAL_LOCALE', 0);
define ('ULOC_VALID_LOCALE', 1);
define ('GRAPHEME_EXTR_COUNT', 0);
define ('GRAPHEME_EXTR_MAXBYTES', 1);
define ('GRAPHEME_EXTR_MAXCHARS', 2);
define ('U_USING_FALLBACK_WARNING', -128);
define ('U_ERROR_WARNING_START', -128);
define ('U_USING_DEFAULT_WARNING', -127);
define ('U_SAFECLONE_ALLOCATED_WARNING', -126);
define ('U_STATE_OLD_WARNING', -125);
define ('U_STRING_NOT_TERMINATED_WARNING', -124);
define ('U_SORT_KEY_TOO_SHORT_WARNING', -123);
define ('U_AMBIGUOUS_ALIAS_WARNING', -122);
define ('U_DIFFERENT_UCA_VERSION', -121);
define ('U_ERROR_WARNING_LIMIT', -120);
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
define ('U_STANDARD_ERROR_LIMIT', 31);
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
define ('U_FMT_PARSE_ERROR_LIMIT', 65804);
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
define ('U_REGEX_ERROR_LIMIT', 66318);
define ('U_STRINGPREP_PROHIBITED_ERROR', 66560);
define ('U_STRINGPREP_UNASSIGNED_ERROR', 66561);
define ('U_STRINGPREP_CHECK_BIDI_ERROR', 66562);
define ('U_ERROR_LIMIT', 66568);

// End of intl v.1.0.0
?>
