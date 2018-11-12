<?php

// Start of intl v.7.3.0

/**
 * Provides string comparison capability with support for appropriate
 * locale-sensitive sort orderings.
 * @link http://www.php.net/manual/en/class.collator.php
 */
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
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.collator.php#collator.props.name
	 */
	public $name;

	/**
	 * Create a collator
	 * @link http://www.php.net/manual/en/collator.construct.php
	 * @param mixed $arg1
	 */
	public function __construct ($arg1) {}

	/**
	 * Create a collator
	 * @link http://www.php.net/manual/en/collator.create.php
	 * @param string $locale The locale containing the required collation rules. Special values for
	 * locales can be passed in - if null is passed for the locale, the
	 * default locale collation rules will be used. If empty string ("") or
	 * "root" are passed, UCA rules will be used.
	 * @return Collator Return new instance of Collator object, or null
	 * on error.
	 */
	public static function create (string $locale) {}

	/**
	 * Compare two Unicode strings
	 * @link http://www.php.net/manual/en/collator.compare.php
	 * @param string $str1 The first string to compare.
	 * @param string $str2 The second string to compare.
	 * @return int Return comparison result:
	 * <p>
	 * <p>
	 * <br>
	 * <p>
	 * 1 if str1 is greater than 
	 * str2 ;
	 * </p>
	 * <br>
	 * <p>
	 * 0 if str1 is equal to 
	 * str2;
	 * </p>
	 * <br>
	 * <p>
	 * -1 if str1 is less than 
	 * str2 .
	 * </p>
	 * </p>
	 * On error
	 * boolean
	 * false
	 * is returned.
	 * </p>
	 */
	public function compare (string $str1, string $str2) {}

	/**
	 * Sort array using specified collator
	 * @link http://www.php.net/manual/en/collator.sort.php
	 * @param array $arr Array of strings to sort.
	 * @param int $sort_flag [optional] <p>
	 * Optional sorting type, one of the following:
	 * </p>
	 * <p>
	 * <p>
	 * <br>
	 * <p>
	 * Collator::SORT_REGULAR
	 * - compare items normally (don't change types)
	 * </p>
	 * <br>
	 * <p>
	 * Collator::SORT_NUMERIC
	 * - compare items numerically
	 * </p>
	 * <br>
	 * <p>
	 * Collator::SORT_STRING
	 * - compare items as strings
	 * </p>
	 * </p>
	 * Default sorting type is Collator::SORT_REGULAR.
	 * It is also used if an invalid sort_flag value has been specified.
	 * </p>
	 * @return bool true on success or false on failure
	 */
	public function sort (array &$arr, int $sort_flag = null) {}

	/**
	 * Sort array using specified collator and sort keys
	 * @link http://www.php.net/manual/en/collator.sortwithsortkeys.php
	 * @param array $arr Array of strings to sort
	 * @return bool true on success or false on failure
	 */
	public function sortWithSortKeys (array &$arr) {}

	/**
	 * Sort array maintaining index association
	 * @link http://www.php.net/manual/en/collator.asort.php
	 * @param array $arr Array of strings to sort.
	 * @param int $sort_flag [optional] <p>
	 * Optional sorting type, one of the following:
	 * <p>
	 * <br>
	 * <p>
	 * Collator::SORT_REGULAR
	 * - compare items normally (don't change types)
	 * </p>
	 * <br>
	 * <p>
	 * Collator::SORT_NUMERIC
	 * - compare items numerically
	 * </p>
	 * <br>
	 * <p>
	 * Collator::SORT_STRING
	 * - compare items as strings
	 * </p>
	 * </p>
	 * </p>
	 * <p>
	 * Default $sort_flag value is
	 * Collator::SORT_REGULAR.
	 * It is also used if an invalid $sort_flag value has been specified.
	 * </p>
	 * @return bool true on success or false on failure
	 */
	public function asort (array &$arr, int $sort_flag = null) {}

	/**
	 * Get collation attribute value
	 * @link http://www.php.net/manual/en/collator.getattribute.php
	 * @param int $attr Attribute to get value for.
	 * @return int Attribute value, or boolean false on error.
	 */
	public function getAttribute (int $attr) {}

	/**
	 * Set collation attribute
	 * @link http://www.php.net/manual/en/collator.setattribute.php
	 * @param int $attr Attribute.
	 * @param int $val Attribute value.
	 * @return bool true on success or false on failure
	 */
	public function setAttribute (int $attr, int $val) {}

	/**
	 * Get current collation strength
	 * @link http://www.php.net/manual/en/collator.getstrength.php
	 * @return int current collation strength, or boolean false on error.
	 */
	public function getStrength () {}

	/**
	 * Set collation strength
	 * @link http://www.php.net/manual/en/collator.setstrength.php
	 * @param int $strength <p>Strength to set.</p>
	 * <p>
	 * Possible values are:
	 * <p>
	 * <br>
	 * <p>
	 * Collator::PRIMARY
	 * </p>
	 * <br>
	 * <p>
	 * Collator::SECONDARY
	 * </p>
	 * <br>
	 * <p>
	 * Collator::TERTIARY
	 * </p>
	 * <br>
	 * <p>
	 * Collator::QUATERNARY
	 * </p>
	 * <br>
	 * <p>
	 * Collator::IDENTICAL
	 * </p>
	 * <br>
	 * <p>
	 * Collator::DEFAULT_STRENGTH
	 * </p>
	 * </p>
	 * </p>
	 * @return bool true on success or false on failure
	 */
	public function setStrength (int $strength) {}

	/**
	 * Get the locale name of the collator
	 * @link http://www.php.net/manual/en/collator.getlocale.php
	 * @param int $type You can choose between valid and actual locale (
	 * Locale::VALID_LOCALE and 
	 * Locale::ACTUAL_LOCALE,
	 * respectively).
	 * @return string Real locale name from which the collation data comes. If the collator was
	 * instantiated from rules or an error occurred, returns
	 * boolean false.
	 */
	public function getLocale (int $type) {}

	/**
	 * Get collator's last error code
	 * @link http://www.php.net/manual/en/collator.geterrorcode.php
	 * @return int Error code returned by the last Collator API function call.
	 */
	public function getErrorCode () {}

	/**
	 * Get text for collator's last error code
	 * @link http://www.php.net/manual/en/collator.geterrormessage.php
	 * @return string Description of an error occurred in the last Collator API function call.
	 */
	public function getErrorMessage () {}

	/**
	 * Get sorting key for a string
	 * @link http://www.php.net/manual/en/collator.getsortkey.php
	 * @param string $str The string to produce the key from.
	 * @return string the collation key for the string. Collation keys can be compared directly instead of strings.
	 */
	public function getSortKey (string $str) {}

}

/**
 * For currencies you can use currency format type to create a formatter that
 * returns a string with the formatted number and the appropriate currency
 * sign. Of course, the NumberFormatter class is unaware of exchange rates
 * so, the number output is the same regardless of the specified currency.
 * This means that the same number has different monetary values depending on
 * the currency locale. If the number is 9988776.65 the results will be:
 * <p>
 * 9 988 776,65 € in France
 * 9.988.776,65 € in Germany
 * $9,988,776.65 in the United States
 * </p>
 * @link http://www.php.net/manual/en/class.numberformatter.php
 */
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
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.numberformatter.php#numberformatter.props.name
	 */
	public $name;

	/**
	 * @param mixed $locale
	 * @param mixed $style
	 * @param mixed $pattern [optional]
	 */
	public function __construct ($locale, $style, $pattern = null) {}

	/**
	 * Create a number formatter
	 * @link http://www.php.net/manual/en/numberformatter.create.php
	 * @param string $locale Locale in which the number would be formatted (locale name, e.g. en_CA).
	 * @param int $style Style of the formatting, one of the
	 * format style constants. If
	 * NumberFormatter::PATTERN_DECIMAL
	 * or NumberFormatter::PATTERN_RULEBASED
	 * is passed then the number format is opened using the given pattern,
	 * which must conform to the syntax described in
	 * ICU DecimalFormat
	 * documentation or
	 * ICU RuleBasedNumberFormat
	 * documentation, respectively.
	 * @param string $pattern [optional] Pattern string if the chosen style requires a pattern.
	 * @return NumberFormatter NumberFormatter object or false on error.
	 */
	public static function create (string $locale, int $style, string $pattern = null) {}

	/**
	 * Format a number
	 * @link http://www.php.net/manual/en/numberformatter.format.php
	 * @param number $value The value to format. Can be integer or float,
	 * other values will be converted to a numeric value.
	 * @param int $type [optional] The
	 * formatting type to use.
	 * @return string the string containing formatted value, or false on error.
	 */
	public function format ($value, int $type = null) {}

	/**
	 * Parse a number
	 * @link http://www.php.net/manual/en/numberformatter.parse.php
	 * @param string $value 
	 * @param int $type [optional] The
	 * formatting type to use. By default,
	 * NumberFormatter::TYPE_DOUBLE is used.
	 * @param int $position [optional] Offset in the string at which to begin parsing. On return, this value
	 * will hold the offset at which parsing ended.
	 * @return mixed The value of the parsed number or false on error.
	 */
	public function parse (string $value, int $type = null, int &$position = null) {}

	/**
	 * Format a currency value
	 * @link http://www.php.net/manual/en/numberformatter.formatcurrency.php
	 * @param float $value The numeric currency value.
	 * @param string $currency The 3-letter ISO 4217 currency code indicating the currency to use.
	 * @return string String representing the formatted currency value.
	 */
	public function formatCurrency (float $value, string $currency) {}

	/**
	 * Parse a currency number
	 * @link http://www.php.net/manual/en/numberformatter.parsecurrency.php
	 * @param string $value 
	 * @param string $currency Parameter to receive the currency name (3-letter ISO 4217 currency
	 * code).
	 * @param int $position [optional] Offset in the string at which to begin parsing. On return, this value
	 * will hold the offset at which parsing ended.
	 * @return float The parsed numeric value or false on error.
	 */
	public function parseCurrency (string $value, string &$currency, int &$position = null) {}

	/**
	 * Set an attribute
	 * @link http://www.php.net/manual/en/numberformatter.setattribute.php
	 * @param int $attr Attribute specifier - one of the
	 * numeric attribute constants.
	 * @param int $value The attribute value.
	 * @return bool true on success or false on failure
	 */
	public function setAttribute (int $attr, int $value) {}

	/**
	 * Get an attribute
	 * @link http://www.php.net/manual/en/numberformatter.getattribute.php
	 * @param int $attr Attribute specifier - one of the
	 * numeric attribute constants.
	 * @return int Return attribute value on success, or false on error.
	 */
	public function getAttribute (int $attr) {}

	/**
	 * Set a text attribute
	 * @link http://www.php.net/manual/en/numberformatter.settextattribute.php
	 * @param int $attr Attribute specifier - one of the
	 * text attribute
	 * constants.
	 * @param string $value Text for the attribute value.
	 * @return bool true on success or false on failure
	 */
	public function setTextAttribute (int $attr, string $value) {}

	/**
	 * Get a text attribute
	 * @link http://www.php.net/manual/en/numberformatter.gettextattribute.php
	 * @param int $attr Attribute specifier - one of the
	 * text attribute constants.
	 * @return string Return attribute value on success, or false on error.
	 */
	public function getTextAttribute (int $attr) {}

	/**
	 * Set a symbol value
	 * @link http://www.php.net/manual/en/numberformatter.setsymbol.php
	 * @param int $attr Symbol specifier, one of the
	 * format symbol constants.
	 * @param string $value Text for the symbol.
	 * @return bool true on success or false on failure
	 */
	public function setSymbol (int $attr, string $value) {}

	/**
	 * Get a symbol value
	 * @link http://www.php.net/manual/en/numberformatter.getsymbol.php
	 * @param int $attr Symbol specifier, one of the
	 * format symbol constants.
	 * @return string The symbol string or false on error.
	 */
	public function getSymbol (int $attr) {}

	/**
	 * Set formatter pattern
	 * @link http://www.php.net/manual/en/numberformatter.setpattern.php
	 * @param string $pattern Pattern in syntax described in
	 * ICU DecimalFormat
	 * documentation.
	 * @return bool true on success or false on failure
	 */
	public function setPattern (string $pattern) {}

	/**
	 * Get formatter pattern
	 * @link http://www.php.net/manual/en/numberformatter.getpattern.php
	 * @return string Pattern string that is used by the formatter, or false if an error happens.
	 */
	public function getPattern () {}

	/**
	 * Get formatter locale
	 * @link http://www.php.net/manual/en/numberformatter.getlocale.php
	 * @param int $type [optional] You can choose between valid and actual locale (
	 * Locale::VALID_LOCALE,
	 * Locale::ACTUAL_LOCALE,
	 * respectively). The default is the actual locale.
	 * @return string The locale name used to create the formatter.
	 */
	public function getLocale (int $type = null) {}

	/**
	 * Get formatter's last error code
	 * @link http://www.php.net/manual/en/numberformatter.geterrorcode.php
	 * @return int error code from last formatter call.
	 */
	public function getErrorCode () {}

	/**
	 * Get formatter's last error message
	 * @link http://www.php.net/manual/en/numberformatter.geterrormessage.php
	 * @return string error message from last formatter call.
	 */
	public function getErrorMessage () {}

}

/**
 * The Unicode Consortium has defined a number of normalization forms
 * reflecting the various needs of applications:
 * <p>
 * Normalization Form D (NFD) - Canonical Decomposition
 * Normalization Form C (NFC) - Canonical Decomposition followed by
 * Canonical Composition
 * Normalization Form KD (NFKD) - Compatibility Decomposition
 * Normalization Form KC (NFKC) - Compatibility Decomposition followed by
 * Canonical Composition
 * </p>
 * The different forms are defined in terms of a set of transformations on
 * the text, transformations that are expressed by both an algorithm and a
 * set of data files.
 * @link http://www.php.net/manual/en/class.normalizer.php
 */
class Normalizer  {
	const NONE = 2;
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
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.normalizer.php#normalizer.props.name
	 */
	public $name;

	/**
	 * Normalizes the input provided and returns the normalized string
	 * @link http://www.php.net/manual/en/normalizer.normalize.php
	 * @param string $input The input string to normalize
	 * @param int $form [optional] One of the normalization forms.
	 * @return string The normalized string or false if an error occurred.
	 */
	public static function normalize (string $input, int $form = null) {}

	/**
	 * Checks if the provided string is already in the specified normalization
	 * form
	 * @link http://www.php.net/manual/en/normalizer.isnormalized.php
	 * @param string $input The input string to normalize
	 * @param int $form [optional] One of the normalization forms.
	 * @return bool true if normalized, false otherwise or if there an error
	 */
	public static function isNormalized (string $input, int $form = null) {}

	/**
	 * Gets the Decomposition_Mapping property for the given UTF-8 encoded code point
	 * @link http://www.php.net/manual/en/normalizer.getrawdecomposition.php
	 * @param string $input The input string, which should be a single, UTF-8 encoded, code point.
	 * @return string a string containing the Decomposition_Mapping property, if present in the UCD.
	 * <p>
	 * Returns null if there is no Decomposition_Mapping property for the character.
	 * </p>
	 */
	public static function getRawDecomposition (string $input) {}

}

/**
 * Examples of identifiers include:
 * <p>
 * en-US (English, United States)
 * zh-Hant-TW (Chinese, Traditional Script, Taiwan)
 * fr-CA, fr-FR (French for Canada and France respectively)
 * </p>
 * @link http://www.php.net/manual/en/class.locale.php
 */
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
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.locale.php#locale.props.name
	 */
	public $name;

	/**
	 * Gets the default locale value from the INTL global 'default_locale'
	 * @link http://www.php.net/manual/en/locale.getdefault.php
	 * @return string The current runtime locale
	 */
	public static function getDefault () {}

	/**
	 * Sets the default runtime locale
	 * @link http://www.php.net/manual/en/locale.setdefault.php
	 * @param string $locale Is a BCP 47 compliant language tag.
	 * @return bool true on success or false on failure
	 */
	public static function setDefault (string $locale) {}

	/**
	 * Gets the primary language for the input locale
	 * @link http://www.php.net/manual/en/locale.getprimarylanguage.php
	 * @param string $locale The locale to extract the primary language code from
	 * @return string The language code associated with the language or null in case of error.
	 */
	public static function getPrimaryLanguage (string $locale) {}

	/**
	 * Gets the script for the input locale
	 * @link http://www.php.net/manual/en/locale.getscript.php
	 * @param string $locale The locale to extract the script code from
	 * @return string The script subtag for the locale or null if not present
	 */
	public static function getScript (string $locale) {}

	/**
	 * Gets the region for the input locale
	 * @link http://www.php.net/manual/en/locale.getregion.php
	 * @param string $locale The locale to extract the region code from
	 * @return string The region subtag for the locale or null if not present
	 */
	public static function getRegion (string $locale) {}

	/**
	 * Gets the keywords for the input locale
	 * @link http://www.php.net/manual/en/locale.getkeywords.php
	 * @param string $locale The locale to extract the keywords from
	 * @return array Associative array containing the keyword-value pairs for this locale
	 */
	public static function getKeywords (string $locale) {}

	/**
	 * Returns an appropriately localized display name for script of the input locale
	 * @link http://www.php.net/manual/en/locale.getdisplayscript.php
	 * @param string $locale The locale to return a display script for
	 * @param string $in_locale [optional] Optional format locale to use to display the script name
	 * @return string Display name of the script for the $locale in the format appropriate for
	 * $in_locale.
	 */
	public static function getDisplayScript (string $locale, string $in_locale = null) {}

	/**
	 * Returns an appropriately localized display name for region of the input locale
	 * @link http://www.php.net/manual/en/locale.getdisplayregion.php
	 * @param string $locale The locale to return a display region for.
	 * @param string $in_locale [optional] Optional format locale to use to display the region name
	 * @return string display name of the region for the $locale in the format appropriate for
	 * $in_locale.
	 */
	public static function getDisplayRegion (string $locale, string $in_locale = null) {}

	/**
	 * Returns an appropriately localized display name for the input locale
	 * @link http://www.php.net/manual/en/locale.getdisplayname.php
	 * @param string $locale The locale to return a display name for.
	 * @param string $in_locale [optional] optional format locale
	 * @return string Display name of the locale in the format appropriate for $in_locale.
	 */
	public static function getDisplayName (string $locale, string $in_locale = null) {}

	/**
	 * Returns an appropriately localized display name for language of the inputlocale
	 * @link http://www.php.net/manual/en/locale.getdisplaylanguage.php
	 * @param string $locale The locale to return a display language for
	 * @param string $in_locale [optional] Optional format locale to use to display the language name
	 * @return string display name of the language for the $locale in the format appropriate for
	 * $in_locale.
	 */
	public static function getDisplayLanguage (string $locale, string $in_locale = null) {}

	/**
	 * Returns an appropriately localized display name for variants of the input locale
	 * @link http://www.php.net/manual/en/locale.getdisplayvariant.php
	 * @param string $locale The locale to return a display variant for
	 * @param string $in_locale [optional] Optional format locale to use to display the variant name
	 * @return string Display name of the variant for the $locale in the format appropriate for
	 * $in_locale.
	 */
	public static function getDisplayVariant (string $locale, string $in_locale = null) {}

	/**
	 * Returns a correctly ordered and delimited locale ID
	 * @link http://www.php.net/manual/en/locale.composelocale.php
	 * @param array $subtags <p>
	 * an array containing a list of key-value pairs, where the keys identify
	 * the particular locale ID subtags, and the values are the associated
	 * subtag values. 
	 * <p>
	 * The 'variant' and 'private' subtags can take maximum 15 values
	 * whereas 'extlang' can take maximum 3 values.e.g. Variants are allowed
	 * with the suffix ranging from 0-14. Hence the keys for the input array
	 * can be variant0, variant1, ...,variant14. In the returned locale id,
	 * the subtag is ordered by suffix resulting in variant0 followed by
	 * variant1 followed by variant2 and so on.
	 * </p>
	 * <p>
	 * The 'variant', 'private' and 'extlang' multiple values can be specified both
	 * as array under specific key (e.g. 'variant') and as multiple numbered keys
	 * (e.g. 'variant0', 'variant1', etc.).
	 * </p>
	 * </p>
	 * @return string The corresponding locale identifier.
	 */
	public static function composeLocale (array $subtags) {}

	/**
	 * Returns a key-value array of locale ID subtag elements
	 * @link http://www.php.net/manual/en/locale.parselocale.php
	 * @param string $locale The locale to extract the subtag array from. Note: The 'variant' and
	 * 'private' subtags can take maximum 15 values whereas 'extlang' can take
	 * maximum 3 values.
	 * @return array an array containing a list of key-value pairs, where the keys
	 * identify the particular locale ID subtags, and the values are the
	 * associated subtag values. The array will be ordered as the locale id
	 * subtags e.g. in the locale id if variants are '-varX-varY-varZ' then the
	 * returned array will have variant0=&gt;varX , variant1=&gt;varY ,
	 * variant2=&gt;varZ
	 * <p>
	 * Returns null when the length of locale exceeds
	 * INTL_MAX_LOCALE_LEN.
	 * </p>
	 */
	public static function parseLocale (string $locale) {}

	/**
	 * Gets the variants for the input locale
	 * @link http://www.php.net/manual/en/locale.getallvariants.php
	 * @param string $locale The locale to extract the variants from
	 * @return array The array containing the list of all variants subtag for the locale 
	 * or null if not present
	 */
	public static function getAllVariants (string $locale) {}

	/**
	 * Checks if a language tag filter matches with locale
	 * @link http://www.php.net/manual/en/locale.filtermatches.php
	 * @param string $langtag The language tag to check
	 * @param string $locale The language range to check against
	 * @param bool $canonicalize [optional] If true, the arguments will be converted to canonical form before
	 * matching.
	 * @return bool true if $locale matches $langtag false otherwise.
	 */
	public static function filterMatches (string $langtag, string $locale, bool $canonicalize = null) {}

	/**
	 * Searches the language tag list for the best match to the language
	 * @link http://www.php.net/manual/en/locale.lookup.php
	 * @param array $langtag <p>
	 * An <p>locale. Maximum 100 items allowed.
	 * </p>
	 * @param string $locale The locale to use as the language range when matching.
	 * @param bool $canonicalize [optional] If true, the arguments will be converted to canonical form before
	 * matching.
	 * @param string $default [optional] The locale to use if no match is found.
	 * @return string The closest matching language tag or default value.
	 */
	public static function lookup (array $langtag, string $locale, bool $canonicalize = null, string $default = null) {}

	/**
	 * Canonicalize the locale string
	 * @link http://www.php.net/manual/en/locale.canonicalize.php
	 * @param string $locale 
	 * @return string 
	 */
	public static function canonicalize (string $locale) {}

	/**
	 * Tries to find out best available locale based on HTTP "Accept-Language" header
	 * @link http://www.php.net/manual/en/locale.acceptfromhttp.php
	 * @param string $header The string containing the "Accept-Language" header according to format in RFC 2616.
	 * @return string The corresponding locale identifier.
	 */
	public static function acceptFromHttp (string $header) {}

}

/**
 * @link http://www.php.net/manual/en/class.messageformatter.php
 */
class MessageFormatter  {

	/**
	 * @param mixed $locale
	 * @param mixed $pattern
	 */
	public function __construct ($locale, $pattern) {}

	/**
	 * Constructs a new Message Formatter
	 * @link http://www.php.net/manual/en/messageformatter.create.php
	 * @param string $locale The locale to use when formatting arguments
	 * @param string $pattern The pattern string to stick arguments into. 
	 * The pattern uses an 'apostrophe-friendly' syntax; it is run through
	 * umsg_autoQuoteApostrophe 
	 * before being interpreted.
	 * @return MessageFormatter The formatter object
	 */
	public static function create (string $locale, string $pattern) {}

	/**
	 * Format the message
	 * @link http://www.php.net/manual/en/messageformatter.format.php
	 * @param array $args Arguments to insert into the format string
	 * @return string The formatted string, or false if an error occurred
	 */
	public function format (array $args) {}

	/**
	 * Quick format message
	 * @link http://www.php.net/manual/en/messageformatter.formatmessage.php
	 * @param string $locale The locale to use for formatting locale-dependent parts
	 * @param string $pattern The pattern string to insert things into.
	 * The pattern uses an 'apostrophe-friendly' syntax; it is run through
	 * umsg_autoQuoteApostrophe 
	 * before being interpreted.
	 * @param array $args The array of values to insert into the format string
	 * @return string The formatted pattern string or false if an error occurred
	 */
	public static function formatMessage (string $locale, string $pattern, array $args) {}

	/**
	 * Parse input string according to pattern
	 * @link http://www.php.net/manual/en/messageformatter.parse.php
	 * @param string $value The string to parse
	 * @return array An array containing the items extracted, or false on error
	 */
	public function parse (string $value) {}

	/**
	 * Quick parse input string
	 * @link http://www.php.net/manual/en/messageformatter.parsemessage.php
	 * @param string $locale The locale to use for parsing locale-dependent parts
	 * @param string $pattern The pattern with which to parse the value.
	 * @param string $source The string to parse, conforming to the pattern.
	 * @return array An array containing items extracted, or false on error
	 */
	public static function parseMessage (string $locale, string $pattern, string $source) {}

	/**
	 * Set the pattern used by the formatter
	 * @link http://www.php.net/manual/en/messageformatter.setpattern.php
	 * @param string $pattern The pattern string to use in this message formatter.
	 * The pattern uses an 'apostrophe-friendly' syntax; it is run through
	 * umsg_autoQuoteApostrophe 
	 * before being interpreted.
	 * @return bool true on success or false on failure
	 */
	public function setPattern (string $pattern) {}

	/**
	 * Get the pattern used by the formatter
	 * @link http://www.php.net/manual/en/messageformatter.getpattern.php
	 * @return string The pattern string for this message formatter
	 */
	public function getPattern () {}

	/**
	 * Get the locale for which the formatter was created
	 * @link http://www.php.net/manual/en/messageformatter.getlocale.php
	 * @return string The locale name
	 */
	public function getLocale () {}

	/**
	 * Get the error code from last operation
	 * @link http://www.php.net/manual/en/messageformatter.geterrorcode.php
	 * @return int The error code, one of UErrorCode values. Initial value is U_ZERO_ERROR.
	 */
	public function getErrorCode () {}

	/**
	 * Get the error text from the last operation
	 * @link http://www.php.net/manual/en/messageformatter.geterrormessage.php
	 * @return string Description of the last error.
	 */
	public function getErrorMessage () {}

}

/**
 * @link http://www.php.net/manual/en/class.intldateformatter.php
 */
class IntlDateFormatter  {
	const FULL = 0;
	const LONG = 1;
	const MEDIUM = 2;
	const SHORT = 3;
	const NONE = -1;
	const GREGORIAN = 1;
	const TRADITIONAL = 0;


	/**
	 * @param mixed $locale
	 * @param mixed $datetype
	 * @param mixed $timetype
	 * @param mixed $timezone [optional]
	 * @param mixed $calendar [optional]
	 * @param mixed $pattern [optional]
	 */
	public function __construct ($locale, $datetype, $timetype, $timezone = null, $calendar = null, $pattern = null) {}

	/**
	 * Create a date formatter
	 * @link http://www.php.net/manual/en/intldateformatter.create.php
	 * @param string $locale Locale to use when formatting or parsing or null to use the value
	 * specified in the ini setting intl.default_locale.
	 * @param int $datetype Date type to use (none, short,
	 * medium, long,
	 * full). This is one of the IntlDateFormatter
	 * constants. It can also be null, in which case ICUʼs default
	 * date type will be used.
	 * @param int $timetype Time type to use (none, short,
	 * medium, long,
	 * full). This is one of the IntlDateFormatter
	 * constants. It can also be null, in which case ICUʼs default
	 * time type will be used.
	 * @param mixed $timezone [optional] <p>
	 * Time zone ID. The default (and the one used if null is given) is the
	 * one returned by date_default_timezone_get or, if
	 * applicable, that of the IntlCalendar object passed
	 * for the calendar parameter. This ID must be a
	 * valid identifier on ICUʼs database or an ID representing an
	 * explicit offset, such as GMT-05:30.
	 * </p>
	 * <p>
	 * This can also be an IntlTimeZone or a
	 * DateTimeZone object.
	 * </p>
	 * @param mixed $calendar [optional] Calendar to use for formatting or parsing. The default value is null,
	 * which corresponds to IntlDateFormatter::GREGORIAN.
	 * This can either be one of the 
	 * IntlDateFormatter
	 * calendar constants or an IntlCalendar. Any
	 * IntlCalendar object passed will be clone; it will
	 * not be changed by the IntlDateFormatter. This will
	 * determine the calendar type used (gregorian, islamic, persian, etc.) and,
	 * if null is given for the timezone parameter,
	 * also the timezone used.
	 * @param string $pattern [optional] Optional pattern to use when formatting or parsing.
	 * Possible patterns are documented at url.icu.datepattern.
	 * @return IntlDateFormatter The created IntlDateFormatter or false in case of
	 * failure.
	 */
	public static function create (string $locale, int $datetype, int $timetype, $timezone = null, $calendar = null, string $pattern = null) {}

	/**
	 * Get the datetype used for the IntlDateFormatter
	 * @link http://www.php.net/manual/en/intldateformatter.getdatetype.php
	 * @return int The current date type value of the formatter.
	 */
	public function getDateType () {}

	/**
	 * Get the timetype used for the IntlDateFormatter
	 * @link http://www.php.net/manual/en/intldateformatter.gettimetype.php
	 * @return int The current date type value of the formatter.
	 */
	public function getTimeType () {}

	/**
	 * Get the calendar type used for the IntlDateFormatter
	 * @link http://www.php.net/manual/en/intldateformatter.getcalendar.php
	 * @return int The calendar
	 * type being used by the formatter. Either
	 * IntlDateFormatter::TRADITIONAL or
	 * IntlDateFormatter::GREGORIAN.
	 */
	public function getCalendar () {}

	/**
	 * Get copy of formatterʼs calendar object
	 * @link http://www.php.net/manual/en/intldateformatter.getcalendarobject.php
	 * @return IntlCalendar A copy of the internal calendar object used by this formatter.
	 */
	public function getCalendarObject () {}

	/**
	 * Sets the calendar type used by the formatter
	 * @link http://www.php.net/manual/en/intldateformatter.setcalendar.php
	 * @param mixed $which <p>
	 * This can either be: the calendar
	 * type to use (default is
	 * IntlDateFormatter::GREGORIAN, which is also used if
	 * null is specified) or an
	 * IntlCalendar object.
	 * </p>
	 * <p>
	 * Any IntlCalendar object passed in will be cloned;
	 * no modifications will be made to the argument object.
	 * </p>
	 * <p>
	 * The timezone of the formatter will only be kept if an
	 * IntlCalendar object is not passed, otherwise the
	 * new timezone will be that of the passed object.
	 * </p>
	 * @return bool true on success or false on failure
	 */
	public function setCalendar ($which) {}

	/**
	 * Get the timezone-id used for the IntlDateFormatter
	 * @link http://www.php.net/manual/en/intldateformatter.gettimezoneid.php
	 * @return string ID string for the time zone used by this formatter.
	 */
	public function getTimeZoneId () {}

	/**
	 * Get formatterʼs timezone
	 * @link http://www.php.net/manual/en/intldateformatter.gettimezone.php
	 * @return IntlTimeZone The associated IntlTimeZone
	 * object or false on failure.
	 */
	public function getTimeZone () {}

	/**
	 * Sets formatterʼs timezone
	 * @link http://www.php.net/manual/en/intldateformatter.settimezone.php
	 * @param mixed $zone <p>
	 * The timezone to use for this formatter. This can be specified in the
	 * following forms:
	 * </p>
	 * reference.intl.inctimezoneparam
	 * @return bool true on success and false on failure.
	 */
	public function setTimeZone ($zone) {}

	/**
	 * Set the pattern used for the IntlDateFormatter
	 * @link http://www.php.net/manual/en/intldateformatter.setpattern.php
	 * @param string $pattern New pattern string to use.
	 * Possible patterns are documented at url.icu.datepattern.
	 * @return bool true on success or false on failure
	 * Bad formatstrings are usually the cause of the failure.
	 */
	public function setPattern (string $pattern) {}

	/**
	 * Get the pattern used for the IntlDateFormatter
	 * @link http://www.php.net/manual/en/intldateformatter.getpattern.php
	 * @return string The pattern string being used to format/parse.
	 */
	public function getPattern () {}

	/**
	 * Get the locale used by formatter
	 * @link http://www.php.net/manual/en/intldateformatter.getlocale.php
	 * @param int $which [optional] 
	 * @return string the locale of this formatter or 'false' if error
	 */
	public function getLocale (int $which = null) {}

	/**
	 * Set the leniency of the parser
	 * @link http://www.php.net/manual/en/intldateformatter.setlenient.php
	 * @param bool $lenient Sets whether the parser is lenient or not, default is true (lenient).
	 * @return bool true on success or false on failure
	 */
	public function setLenient (bool $lenient) {}

	/**
	 * Get the lenient used for the IntlDateFormatter
	 * @link http://www.php.net/manual/en/intldateformatter.islenient.php
	 * @return bool true if parser is lenient, false if parser is strict. By default the parser is lenient.
	 */
	public function isLenient () {}

	/**
	 * Format the date/time value as a string
	 * @link http://www.php.net/manual/en/intldateformatter.format.php
	 * @param mixed $value <p>
	 * Value to format. This may be a DateTimeInterface object, an
	 * IntlCalendar object, a numeric type
	 * representing a (possibly fractional) number of seconds since epoch or an
	 * array in the format output by
	 * localtime.
	 * </p>
	 * <p>
	 * If a DateTime or an
	 * IntlCalendar object is passed, its timezone is not
	 * considered. The object will be formatted using the formaterʼs configured
	 * timezone. If one wants to use the timezone of the object to be formatted,
	 * IntlDateFormatter::setTimeZone must be called before
	 * with the objectʼs timezone. Alternatively, the static function
	 * IntlDateFormatter::formatObject may be used instead.
	 * </p>
	 * @return string The formatted string or, if an error occurred, false.
	 */
	public function format ($value) {}

	/**
	 * Formats an object
	 * @link http://www.php.net/manual/en/intldateformatter.formatobject.php
	 * @param object $object An object of type IntlCalendar or
	 * DateTime. The timezone information in the object
	 * will be used.
	 * @param mixed $format [optional] How to format the date/time. This can either be an array with
	 * two elements (first the date style, then the time style, these being one
	 * of the constants IntlDateFormatter::NONE,
	 * IntlDateFormatter::SHORT,
	 * IntlDateFormatter::MEDIUM,
	 * IntlDateFormatter::LONG,
	 * IntlDateFormatter::FULL), an integer with
	 * the value of one of these constants (in which case it will be used both
	 * for the time and the date) or a string with the format
	 * described in the ICU
	 * documentation. If null, the default style will be used.
	 * @param string $locale [optional] The locale to use, or null to use the default one.
	 * @return string A string with result or false on failure.
	 */
	public static function formatObject ($object, $format = null, string $locale = null) {}

	/**
	 * Parse string to a timestamp value
	 * @link http://www.php.net/manual/en/intldateformatter.parse.php
	 * @param string $value string to convert to a time
	 * @param int $position [optional] Position at which to start the parsing in $value (zero-based).
	 * If no error occurs before $value is consumed, $parse_pos will contain -1
	 * otherwise it will contain the position at which parsing ended (and the error occurred).
	 * This variable will contain the end position if the parse fails.
	 * If $parse_pos > strlen($value), the parse fails immediately.
	 * @return int timestamp parsed value, or false if value can't be parsed.
	 */
	public function parse (string $value, int &$position = null) {}

	/**
	 * Parse string to a field-based time value
	 * @link http://www.php.net/manual/en/intldateformatter.localtime.php
	 * @param string $value string to convert to a time
	 * @param int $position [optional] Position at which to start the parsing in $value (zero-based).
	 * If no error occurs before $value is consumed, $parse_pos will contain -1
	 * otherwise it will contain the position at which parsing ended .
	 * If $parse_pos > strlen($value), the parse fails immediately.
	 * @return array Localtime compatible array of integers : contains 24 hour clock value in tm_hour field
	 */
	public function localtime (string $value, int &$position = null) {}

	/**
	 * Get the error code from last operation
	 * @link http://www.php.net/manual/en/intldateformatter.geterrorcode.php
	 * @return int The error code, one of UErrorCode values. Initial value is U_ZERO_ERROR.
	 */
	public function getErrorCode () {}

	/**
	 * Get the error text from the last operation
	 * @link http://www.php.net/manual/en/intldateformatter.geterrormessage.php
	 * @return string Description of the last error.
	 */
	public function getErrorMessage () {}

}

/**
 * @link http://www.php.net/manual/en/class.resourcebundle.php
 */
class ResourceBundle implements Traversable {

	/**
	 * @param mixed $locale
	 * @param mixed $bundlename
	 * @param mixed $fallback [optional]
	 */
	public function __construct ($locale, $bundlename, $fallback = null) {}

	/**
	 * Create a resource bundle
	 * @link http://www.php.net/manual/en/resourcebundle.create.php
	 * @param string $locale Locale for which the resources should be loaded (locale name, e.g. en_CA).
	 * @param string $bundlename The directory where the data is stored or the name of the .dat file.
	 * @param bool $fallback [optional] Whether locale should match exactly or fallback to parent locale is allowed.
	 * @return ResourceBundle ResourceBundle object or null on error.
	 */
	public static function create (string $locale, string $bundlename, bool $fallback = null) {}

	/**
	 * Get data from the bundle
	 * @link http://www.php.net/manual/en/resourcebundle.get.php
	 * @param string|int $index Data index, must be string or integer.
	 * @param bool $fallback [optional] Whether locale should match exactly or fallback to parent locale is allowed.
	 * @return mixed the data located at the index or null on error. Strings, integers and binary data strings
	 * are returned as corresponding PHP types, integer array is returned as PHP array. Complex types are
	 * returned as ResourceBundle object.
	 */
	public function get ($index, bool $fallback = null) {}

	/**
	 * Get number of elements in the bundle
	 * @link http://www.php.net/manual/en/resourcebundle.count.php
	 * @return int number of elements in the bundle.
	 */
	public function count () {}

	/**
	 * Get supported locales
	 * @link http://www.php.net/manual/en/resourcebundle.locales.php
	 * @param string $bundlename Path of ResourceBundle for which to get available locales, or
	 * empty string for default locales list.
	 * @return array the list of locales supported by the bundle.
	 */
	public static function getLocales (string $bundlename) {}

	/**
	 * Get bundle's last error code
	 * @link http://www.php.net/manual/en/resourcebundle.geterrorcode.php
	 * @return int error code from last bundle object call.
	 */
	public function getErrorCode () {}

	/**
	 * Get bundle's last error message
	 * @link http://www.php.net/manual/en/resourcebundle.geterrormessage.php
	 * @return string error message from last bundle object's call.
	 */
	public function getErrorMessage () {}

}

/**
 * @link http://www.php.net/manual/en/class.transliterator.php
 */
class Transliterator  {
	const FORWARD = 0;
	const REVERSE = 1;

	public $id;


	/**
	 * Private constructor to deny instantiation
	 * @link http://www.php.net/manual/en/transliterator.construct.php
	 */
	final private function __construct () {}

	/**
	 * Create a transliterator
	 * @link http://www.php.net/manual/en/transliterator.create.php
	 * @param string $id The id.
	 * @param int $direction [optional] The direction, defaults to 
	 * >Transliterator::FORWARD.
	 * May also be set to
	 * Transliterator::REVERSE.
	 * @return Transliterator a Transliterator object on success,
	 * or null on failure.
	 */
	public static function create (string $id, int $direction = null) {}

	/**
	 * Create transliterator from rules
	 * @link http://www.php.net/manual/en/transliterator.createfromrules.php
	 * @param string $rules The rules.
	 * @param string $direction [optional] The direction, defaults to 
	 * >Transliterator::FORWARD.
	 * May also be set to
	 * Transliterator::REVERSE.
	 * @return Transliterator a Transliterator object on success,
	 * or null on failure.
	 */
	public static function createFromRules (string $rules, string $direction = null) {}

	/**
	 * Create an inverse transliterator
	 * @link http://www.php.net/manual/en/transliterator.createinverse.php
	 * @return Transliterator a Transliterator object on success,
	 * or null on failure
	 */
	public function createInverse () {}

	/**
	 * Get transliterator IDs
	 * @link http://www.php.net/manual/en/transliterator.listids.php
	 * @return array An array of registered transliterator IDs on success,
	 * or false on failure.
	 */
	public static function listIDs () {}

	/**
	 * Transliterate a string
	 * @link http://www.php.net/manual/en/transliterator.transliterate.php
	 * @param string $subject The string to be transformed.
	 * @param int $start [optional] The start index (in UTF-16 code units) from which the string will start
	 * to be transformed, inclusive. Indexing starts at 0. The text before will
	 * be left as is.
	 * @param int $end [optional] The end index (in UTF-16 code units) until which the string will be
	 * transformed, exclusive. Indexing starts at 0. The text after will be
	 * left as is.
	 * @return string The transfomed string on success, or false on failure.
	 */
	public function transliterate (string $subject, int $start = null, int $end = null) {}

	/**
	 * Get last error code
	 * @link http://www.php.net/manual/en/transliterator.geterrorcode.php
	 * @return int The error code on success,
	 * or false if none exists, or on failure.
	 */
	public function getErrorCode () {}

	/**
	 * Get last error message
	 * @link http://www.php.net/manual/en/transliterator.geterrormessage.php
	 * @return string The error message on success,
	 * or false if none exists, or on failure.
	 */
	public function getErrorMessage () {}

}

/**
 * @link http://www.php.net/manual/en/class.intltimezone.php
 */
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


	private function __construct () {}

	/**
	 * Create a timezone object for the given ID
	 * @link http://www.php.net/manual/en/intltimezone.createtimezone.php
	 * @param string $zoneId 
	 * @return IntlTimeZone 
	 */
	public static function createTimeZone (string $zoneId) {}

	/**
	 * Create a timezone object from DateTimeZone
	 * @link http://www.php.net/manual/en/intltimezone.fromdatetimezone.php
	 * @param DateTimeZone $zoneId 
	 * @return IntlTimeZone 
	 */
	public static function fromDateTimeZone (DateTimeZone $zoneId) {}

	/**
	 * Create a new copy of the default timezone for this host
	 * @link http://www.php.net/manual/en/intltimezone.createdefault.php
	 * @return IntlTimeZone 
	 */
	public static function createDefault () {}

	/**
	 * Create GMT (UTC) timezone
	 * @link http://www.php.net/manual/en/intltimezone.getgmt.php
	 * @return IntlTimeZone 
	 */
	public static function getGMT () {}

	/**
	 * Get the "unknown" time zone
	 * @link http://www.php.net/manual/en/intltimezone.getunknown.php
	 * @return IntlTimeZone IntlTimeZone or null on failure.
	 */
	public static function getUnknown () {}

	/**
	 * Get an enumeration over time zone IDs associated with the
	 * given country or offset
	 * @link http://www.php.net/manual/en/intltimezone.createenumeration.php
	 * @param mixed $countryOrRawOffset [optional] 
	 * @return IntlIterator 
	 */
	public static function createEnumeration ($countryOrRawOffset = null) {}

	/**
	 * Get the number of IDs in the equivalency group that includes the given ID
	 * @link http://www.php.net/manual/en/intltimezone.countequivalentids.php
	 * @param string $zoneId 
	 * @return int 
	 */
	public static function countEquivalentIDs (string $zoneId) {}

	/**
	 * Get an enumeration over system time zone IDs with the given filter conditions
	 * @link http://www.php.net/manual/en/intltimezone.createtimezoneidenumeration.php
	 * @param int $zoneType 
	 * @param string $region [optional] 
	 * @param int $rawOffset [optional] 
	 * @return IntlIterator IntlIterator or false on failure.
	 */
	public static function createTimeZoneIDEnumeration (int $zoneType, string $region = null, int $rawOffset = null) {}

	/**
	 * Get the canonical system timezone ID or the normalized custom time zone ID for the given time zone ID
	 * @link http://www.php.net/manual/en/intltimezone.getcanonicalid.php
	 * @param string $zoneId 
	 * @param bool $isSystemID [optional] 
	 * @return string 
	 */
	public static function getCanonicalID (string $zoneId, bool &$isSystemID = null) {}

	/**
	 * Get the region code associated with the given system time zone ID
	 * @link http://www.php.net/manual/en/intltimezone.getregion.php
	 * @param string $zoneId 
	 * @return string Return region or false on failure.
	 */
	public static function getRegion (string $zoneId) {}

	/**
	 * Get the timezone data version currently used by ICU
	 * @link http://www.php.net/manual/en/intltimezone.gettzdataversion.php
	 * @return string 
	 */
	public static function getTZDataVersion () {}

	/**
	 * Get an ID in the equivalency group that includes the given ID
	 * @link http://www.php.net/manual/en/intltimezone.getequivalentid.php
	 * @param string $zoneId 
	 * @param int $index 
	 * @return string 
	 */
	public static function getEquivalentID (string $zoneId, int $index) {}

	/**
	 * Get timezone ID
	 * @link http://www.php.net/manual/en/intltimezone.getid.php
	 * @return string 
	 */
	public function getID () {}

	/**
	 * Check if this time zone uses daylight savings time
	 * @link http://www.php.net/manual/en/intltimezone.usedaylighttime.php
	 * @return bool 
	 */
	public function useDaylightTime () {}

	/**
	 * Get the time zone raw and GMT offset for the given moment in time
	 * @link http://www.php.net/manual/en/intltimezone.getoffset.php
	 * @param float $date 
	 * @param bool $local 
	 * @param int $rawOffset 
	 * @param int $dstOffset 
	 * @return int 
	 */
	public function getOffset (float $date, bool $local, int &$rawOffset, int &$dstOffset) {}

	/**
	 * Get the raw GMT offset (before taking daylight savings time into account
	 * @link http://www.php.net/manual/en/intltimezone.getrawoffset.php
	 * @return int 
	 */
	public function getRawOffset () {}

	/**
	 * Check if this zone has the same rules and offset as another zone
	 * @link http://www.php.net/manual/en/intltimezone.hassamerules.php
	 * @param IntlTimeZone $otherTimeZone 
	 * @return bool 
	 */
	public function hasSameRules (IntlTimeZone $otherTimeZone) {}

	/**
	 * Get a name of this time zone suitable for presentation to the user
	 * @link http://www.php.net/manual/en/intltimezone.getdisplayname.php
	 * @param bool $isDaylight [optional] 
	 * @param int $style [optional] 
	 * @param string $locale [optional] 
	 * @return string 
	 */
	public function getDisplayName (bool $isDaylight = null, int $style = null, string $locale = null) {}

	/**
	 * Get the amount of time to be added to local standard time to get local wall clock time
	 * @link http://www.php.net/manual/en/intltimezone.getdstsavings.php
	 * @return int 
	 */
	public function getDSTSavings () {}

	/**
	 * Convert to DateTimeZone object
	 * @link http://www.php.net/manual/en/intltimezone.todatetimezone.php
	 * @return DateTimeZone 
	 */
	public function toDateTimeZone () {}

	/**
	 * Get last error code on the object
	 * @link http://www.php.net/manual/en/intltimezone.geterrorcode.php
	 * @return int 
	 */
	public function getErrorCode () {}

	/**
	 * Get last error message on the object
	 * @link http://www.php.net/manual/en/intltimezone.geterrormessage.php
	 * @return string 
	 */
	public function getErrorMessage () {}

	/**
	 * Translate a system timezone into a Windows timezone
	 * @link http://www.php.net/manual/en/intltimezone.getwindowsid.php
	 * @param string $timezone 
	 * @return string the Windows timezone or false on failure.
	 */
	public static function getWindowsID (string $timezone) {}

	/**
	 * Translate a Windows timezone into a system timezone
	 * @link http://www.php.net/manual/en/intltimezone.getidforwindowsid.php
	 * @param string $timezone 
	 * @param string $region [optional] 
	 * @return string the system timezone or false on failure.
	 */
	public static function getIDForWindowsID (string $timezone, string $region = null) {}

}

/**
 * @link http://www.php.net/manual/en/class.intlcalendar.php
 */
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
	const FIELD_FIELD_COUNT = 23;
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
	 * Private constructor for disallowing instantiation
	 * @link http://www.php.net/manual/en/intlcalendar.construct.php
	 */
	private function __construct () {}

	/**
	 * Create a new IntlCalendar
	 * @link http://www.php.net/manual/en/intlcalendar.createinstance.php
	 * @param mixed $timeZone [optional] <p>
	 * The timezone to use.
	 * </p>
	 * reference.intl.inctimezoneparam
	 * @param string $locale [optional] A locale to use or null to use the default locale.
	 * @return IntlCalendar The created IntlCalendar instance or null on
	 * failure.
	 */
	public static function createInstance ($timeZone = null, string $locale = null) {}

	/**
	 * Get set of locale keyword values
	 * @link http://www.php.net/manual/en/intlcalendar.getkeywordvaluesforlocale.php
	 * @param string $key The locale keyword for which relevant values are to be queried. Only
	 * 'calendar' is supported.
	 * @param string $locale The locale onto which the keyword/value pair are to be appended.
	 * @param bool $commonlyUsed Whether to show only the values commonly used for the specified locale.
	 * @return Iterator An iterator that yields strings with the locale keyword
	 * values or false on failure.
	 */
	public static function getKeywordValuesForLocale (string $key, string $locale, bool $commonlyUsed) {}

	/**
	 * Get number representing the current time
	 * @link http://www.php.net/manual/en/intlcalendar.getnow.php
	 * @return float A float representing a number of milliseconds since the epoch,
	 * not counting leap seconds.
	 */
	public static function getNow () {}

	/**
	 * Get array of locales for which there is data
	 * @link http://www.php.net/manual/en/intlcalendar.getavailablelocales.php
	 * @return array An array of strings, one for which locale.
	 */
	public static function getAvailableLocales () {}

	/**
	 * Get the value for a field
	 * @link http://www.php.net/manual/en/intlcalendar.get.php
	 * @param int $field reference.intl.incfieldparam
	 * @return int An integer with the value of the time field.
	 */
	public function get (int $field) {}

	/**
	 * Get time currently represented by the object
	 * @link http://www.php.net/manual/en/intlcalendar.gettime.php
	 * @return float A float representing the number of milliseconds elapsed since the
	 * reference time (1 Jan 1970 00:00:00 UTC).
	 */
	public function getTime () {}

	/**
	 * Set the calendar time in milliseconds since the epoch
	 * @link http://www.php.net/manual/en/intlcalendar.settime.php
	 * @param float $date An instant represented by the number of number of milliseconds between
	 * such instant and the epoch, ignoring leap seconds.
	 * @return bool true on success and false on failure.
	 */
	public function setTime (float $date) {}

	/**
	 * Add a (signed) amount of time to a field
	 * @link http://www.php.net/manual/en/intlcalendar.add.php
	 * @param int $field reference.intl.incfieldparam
	 * @param int $amount The signed amount to add to the current field. If the amount is positive,
	 * the instant will be moved forward; if it is negative, the instant wil be
	 * moved into the past. The unit is implicit to the field type. For instance,
	 * hours for IntlCalendar::FIELD_HOUR_OF_DAY.
	 * @return bool true on success or false on failure.
	 */
	public function add (int $field, int $amount) {}

	/**
	 * Set the timezone used by this calendar
	 * @link http://www.php.net/manual/en/intlcalendar.settimezone.php
	 * @param mixed $timeZone The new timezone to be used by this calendar. It can be specified in the
	 * following ways:
	 * reference.intl.inctimezoneparam
	 * @return bool true on success and false on failure.
	 */
	public function setTimeZone ($timeZone) {}

	/**
	 * Whether this objectʼs time is after that of the passed object
	 * @link http://www.php.net/manual/en/intlcalendar.after.php
	 * @param IntlCalendar $other The calendar whose time will be checked against the primary objectʼs time.
	 * @return bool true if this objectʼs current time is after that of the
	 * calendar argumentʼs time. Returns false otherwise.
	 * Also returns false on failure. You can use exceptions or
	 * intl_get_error_code to detect error conditions.
	 */
	public function after (IntlCalendar $other) {}

	/**
	 * Whether this objectʼs time is before that of the passed object
	 * @link http://www.php.net/manual/en/intlcalendar.before.php
	 * @param IntlCalendar $other The calendar whose time will be checked against the primary objectʼs time.
	 * @return bool true if this objectʼs current time is before that of the
	 * calendar argumentʼs time. Returns false otherwise.
	 * Also returns false on failure. You can use exceptions or
	 * intl_get_error_code to detect error conditions.
	 */
	public function before (IntlCalendar $other) {}

	/**
	 * Set a time field or several common fields at once
	 * @link http://www.php.net/manual/en/intlcalendar.set.php
	 * @param int $field reference.intl.incfieldparam
	 * @param int $value The new value of the given field.
	 * @return bool true on success and false on failure.
	 */
	public function set (int $field, int $value) {}

	/**
	 * Add value to field without carrying into more significant fields
	 * @link http://www.php.net/manual/en/intlcalendar.roll.php
	 * @param int $field reference.intl.incfieldparam
	 * @param mixed $amountOrUpOrDown The (signed) amount to add to the field, true for rolling up (adding
	 * 1), or false for rolling down (subtracting
	 * 1).
	 * @return bool true on success or false on failure.
	 */
	public function roll (int $field, $amountOrUpOrDown) {}

	/**
	 * Clear a field or all fields
	 * @link http://www.php.net/manual/en/intlcalendar.clear.php
	 * @param int $field [optional] reference.intl.incfieldparam
	 * @return bool true on success or false on failure. Failure can only occur is
	 * invalid arguments are provided.
	 */
	public function clear (int $field = null) {}

	/**
	 * Calculate difference between given time and this objectʼs time
	 * @link http://www.php.net/manual/en/intlcalendar.fielddifference.php
	 * @param float $when The time against which to compare the quantity represented by the
	 * field. For the result to be positive, the time
	 * given for this parameter must be ahead of the time of the object the
	 * method is being invoked on.
	 * @param int $field <p>
	 * The field that represents the quantity being compared.
	 * </p>
	 * reference.intl.incfieldparam
	 * @return int a (signed) difference of time in the unit associated with the
	 * specified field or false on failure.
	 */
	public function fieldDifference (float $when, int $field) {}

	/**
	 * The maximum value for a field, considering the objectʼs current time
	 * @link http://www.php.net/manual/en/intlcalendar.getactualmaximum.php
	 * @param int $field reference.intl.incfieldparam
	 * @return int An int representing the maximum value in the units associated
	 * with the given field or false on failure.
	 */
	public function getActualMaximum (int $field) {}

	/**
	 * The minimum value for a field, considering the objectʼs current time
	 * @link http://www.php.net/manual/en/intlcalendar.getactualminimum.php
	 * @param int $field reference.intl.incfieldparam
	 * @return int An int representing the minimum value in the fieldʼs
	 * unit or false on failure.
	 */
	public function getActualMinimum (int $field) {}

	/**
	 * Tell whether a day is a weekday, weekend or a day that has a transition between the two
	 * @link http://www.php.net/manual/en/intlcalendar.getdayofweektype.php
	 * @param int $dayOfWeek One of the constants IntlCalendar::DOW_SUNDAY,
	 * IntlCalendar::DOW_MONDAY, …,
	 * IntlCalendar::DOW_SATURDAY.
	 * @return int one of the constants 
	 * IntlCalendar::DOW_TYPE_WEEKDAY,
	 * IntlCalendar::DOW_TYPE_WEEKEND,
	 * IntlCalendar::DOW_TYPE_WEEKEND_OFFSET or
	 * IntlCalendar::DOW_TYPE_WEEKEND_CEASE or false on failure.
	 */
	public function getDayOfWeekType (int $dayOfWeek) {}

	/**
	 * Get the first day of the week for the calendarʼs locale
	 * @link http://www.php.net/manual/en/intlcalendar.getfirstdayofweek.php
	 * @return int One of the constants IntlCalendar::DOW_SUNDAY,
	 * IntlCalendar::DOW_MONDAY, …,
	 * IntlCalendar::DOW_SATURDAY or false on failure.
	 */
	public function getFirstDayOfWeek () {}

	/**
	 * Get the largest local minimum value for a field
	 * @link http://www.php.net/manual/en/intlcalendar.getgreatestminimum.php
	 * @param int $field reference.intl.incfieldparam
	 * @return int An int representing a field value, in the fieldʼs
	 * unit, or false on failure.
	 */
	public function getGreatestMinimum (int $field) {}

	/**
	 * Get the smallest local maximum for a field
	 * @link http://www.php.net/manual/en/intlcalendar.getleastmaximum.php
	 * @param int $field reference.intl.incfieldparam
	 * @return int An int representing a field value in the fieldʼs
	 * unit or false on failure.
	 */
	public function getLeastMaximum (int $field) {}

	/**
	 * Get the locale associated with the object
	 * @link http://www.php.net/manual/en/intlcalendar.getlocale.php
	 * @param int $localeType Whether to fetch the actual locale (the locale from which the calendar
	 * data originates, with Locale::ACTUAL_LOCALE) or the
	 * valid locale, i.e., the most specific locale supported by ICU relatively
	 * to the requested locale – see Locale::VALID_LOCALE.
	 * From the most general to the most specific, the locales are ordered in
	 * this fashion – actual locale, valid locale, requested locale.
	 * @return string A locale string or false on failure.
	 */
	public function getLocale (int $localeType) {}

	/**
	 * Get the global maximum value for a field
	 * @link http://www.php.net/manual/en/intlcalendar.getmaximum.php
	 * @param int $field reference.intl.incfieldparam
	 * @return int An int representing a field value in the fieldʼs
	 * unit or false on failure.
	 */
	public function getMaximum (int $field) {}

	/**
	 * Get minimal number of days the first week in a year or month can have
	 * @link http://www.php.net/manual/en/intlcalendar.getminimaldaysinfirstweek.php
	 * @return int An int representing a number of days or false on failure.
	 */
	public function getMinimalDaysInFirstWeek () {}

	/**
	 * Get the global minimum value for a field
	 * @link http://www.php.net/manual/en/intlcalendar.getminimum.php
	 * @param int $field reference.intl.incfieldparam
	 * @return int An int representing a value for the given
	 * field in the fieldʼs unit or false on failure.
	 */
	public function getMinimum (int $field) {}

	/**
	 * Get the objectʼs timezone
	 * @link http://www.php.net/manual/en/intlcalendar.gettimezone.php
	 * @return IntlTimeZone An IntlTimeZone object corresponding to the one used
	 * internally in this object.
	 */
	public function getTimeZone () {}

	/**
	 * Get the calendar type
	 * @link http://www.php.net/manual/en/intlcalendar.gettype.php
	 * @return string A string representing the calendar type, such as
	 * 'gregorian', 'islamic', etc.
	 */
	public function getType () {}

	/**
	 * Get time of the day at which weekend begins or ends
	 * @link http://www.php.net/manual/en/intlcalendar.getweekendtransition.php
	 * @param string $dayOfWeek One of the constants IntlCalendar::DOW_SUNDAY,
	 * IntlCalendar::DOW_MONDAY, …,
	 * IntlCalendar::DOW_SATURDAY.
	 * @return int The number of milliseconds into the day at which the weekend begins or
	 * ends or false on failure.
	 */
	public function getWeekendTransition (string $dayOfWeek) {}

	/**
	 * Whether the objectʼs time is in Daylight Savings Time
	 * @link http://www.php.net/manual/en/intlcalendar.indaylighttime.php
	 * @return bool true if the date is in Daylight Savings Time, false otherwise.
	 * The value false may also be returned on failure, for instance after
	 * specifying invalid field values on non-lenient mode; use exceptions or query
	 * intl_get_error_code to disambiguate.
	 */
	public function inDaylightTime () {}

	/**
	 * Whether another calendar is equal but for a different time
	 * @link http://www.php.net/manual/en/intlcalendar.isequivalentto.php
	 * @param IntlCalendar $other The other calendar against which the comparison is to be made.
	 * @return bool Assuming there are no argument errors, returns true iif the calendars are
	 * equivalent except possibly for their set time.
	 */
	public function isEquivalentTo (IntlCalendar $other) {}

	/**
	 * Whether date/time interpretation is in lenient mode
	 * @link http://www.php.net/manual/en/intlcalendar.islenient.php
	 * @return bool A bool representing whether the calendar is set to lenient mode.
	 */
	public function isLenient () {}

	/**
	 * Whether a field is set
	 * @link http://www.php.net/manual/en/intlcalendar.isset.php
	 * @param int $field reference.intl.incfieldparam
	 * @return bool Assuming there are no argument errors, returns true iif the field is set.
	 */
	public function isSet (int $field) {}

	/**
	 * Whether a certain date/time is in the weekend
	 * @link http://www.php.net/manual/en/intlcalendar.isweekend.php
	 * @param float $date [optional] An optional timestamp representing the number of milliseconds since the
	 * epoch, excluding leap seconds. If null, this objectʼs current time is
	 * used instead.
	 * @return bool A bool indicating whether the given or this objectʼs time occurs
	 * in a weekend.
	 * <p>
	 * The value false may also be returned on failure, for instance after giving
	 * a date out of bounds on non-lenient mode; use exceptions or query
	 * intl_get_error_code to disambiguate.
	 * </p>
	 */
	public function isWeekend (float $date = null) {}

	/**
	 * Set the day on which the week is deemed to start
	 * @link http://www.php.net/manual/en/intlcalendar.setfirstdayofweek.php
	 * @param int $dayOfWeek One of the constants IntlCalendar::DOW_SUNDAY,
	 * IntlCalendar::DOW_MONDAY, …,
	 * IntlCalendar::DOW_SATURDAY.
	 * @return bool true on success. Failure can only happen due to invalid parameters.
	 */
	public function setFirstDayOfWeek (int $dayOfWeek) {}

	/**
	 * Set whether date/time interpretation is to be lenient
	 * @link http://www.php.net/manual/en/intlcalendar.setlenient.php
	 * @param bool $isLenient Use true to activate the lenient mode; false otherwise.
	 * @return bool true on success. Failure can only happen due to invalid parameters.
	 */
	public function setLenient (bool $isLenient) {}

	/**
	 * Set minimal number of days the first week in a year or month can have
	 * @link http://www.php.net/manual/en/intlcalendar.setminimaldaysinfirstweek.php
	 * @param int $minimalDays The number of minimal days to set.
	 * @return bool true on success, false on failure.
	 */
	public function setMinimalDaysInFirstWeek (int $minimalDays) {}

	/**
	 * Compare time of two IntlCalendar objects for equality
	 * @link http://www.php.net/manual/en/intlcalendar.equals.php
	 * @param IntlCalendar $other The calendar to compare with the primary object.
	 * @return bool true if the current time of both this and the passed in
	 * IntlCalendar object are the same, or false
	 * otherwise. The value false can also be returned on failure. This can only
	 * happen if bad arguments are passed in. In any case, the two cases can be
	 * distinguished by calling intl_get_error_code.
	 */
	public function equals (IntlCalendar $other) {}

	/**
	 * Get behavior for handling repeating wall time
	 * @link http://www.php.net/manual/en/intlcalendar.getrepeatedwalltimeoption.php
	 * @return int One of the constants IntlCalendar::WALLTIME_FIRST or
	 * IntlCalendar::WALLTIME_LAST.
	 */
	public function getRepeatedWallTimeOption () {}

	/**
	 * Get behavior for handling skipped wall time
	 * @link http://www.php.net/manual/en/intlcalendar.getskippedwalltimeoption.php
	 * @return int One of the constants IntlCalendar::WALLTIME_FIRST,
	 * IntlCalendar::WALLTIME_LAST or
	 * IntlCalendar::WALLTIME_NEXT_VALID.
	 */
	public function getSkippedWallTimeOption () {}

	/**
	 * Set behavior for handling repeating wall times at negative timezone offset transitions
	 * @link http://www.php.net/manual/en/intlcalendar.setrepeatedwalltimeoption.php
	 * @param int $wallTimeOption One of the constants IntlCalendar::WALLTIME_FIRST or
	 * IntlCalendar::WALLTIME_LAST.
	 * @return bool true on success. Failure can only happen due to invalid parameters.
	 */
	public function setRepeatedWallTimeOption (int $wallTimeOption) {}

	/**
	 * Set behavior for handling skipped wall times at positive timezone offset transitions
	 * @link http://www.php.net/manual/en/intlcalendar.setskippedwalltimeoption.php
	 * @param int $wallTimeOption One of the constants IntlCalendar::WALLTIME_FIRST,
	 * IntlCalendar::WALLTIME_LAST or
	 * IntlCalendar::WALLTIME_NEXT_VALID.
	 * @return bool true on success. Failure can only happen due to invalid parameters.
	 */
	public function setSkippedWallTimeOption (int $wallTimeOption) {}

	/**
	 * Create an IntlCalendar from a DateTime object or string
	 * @link http://www.php.net/manual/en/intlcalendar.fromdatetime.php
	 * @param mixed $dateTime A DateTime object or a string that
	 * can be passed to DateTime::__construct.
	 * @return IntlCalendar The created IntlCalendar object or null in case of
	 * failure. If a string is passed, any exception that occurs
	 * inside the DateTime constructor is propagated.
	 */
	public static function fromDateTime ($dateTime) {}

	/**
	 * Convert an IntlCalendar into a DateTime object
	 * @link http://www.php.net/manual/en/intlcalendar.todatetime.php
	 * @return DateTime A DateTime object with the same timezone as this
	 * object (though using PHPʼs database instead of ICUʼs) and the same time,
	 * except for the smaller precision (second precision instead of millisecond).
	 * Returns false on failure.
	 */
	public function toDateTime () {}

	/**
	 * Get last error code on the object
	 * @link http://www.php.net/manual/en/intlcalendar.geterrorcode.php
	 * @return int An ICU error code indicating either success, failure or a warning.
	 */
	public function getErrorCode () {}

	/**
	 * Get last error message on the object
	 * @link http://www.php.net/manual/en/intlcalendar.geterrormessage.php
	 * @return string The error message associated with last error that occurred in a function call
	 * on this object, or a string indicating the non-existance of an error.
	 */
	public function getErrorMessage () {}

}

/**
 * @link http://www.php.net/manual/en/class.intlgregoriancalendar.php
 */
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
	const FIELD_FIELD_COUNT = 23;
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
	 * Create the Gregorian Calendar class
	 * @link http://www.php.net/manual/en/intlgregoriancalendar.construct.php
	 * @param mixed $timeZoneOrYear [optional]
	 * @param mixed $localeOrMonth [optional]
	 * @param mixed $dayOfMonth [optional]
	 * @param mixed $hour [optional]
	 * @param mixed $minute [optional]
	 * @param mixed $second [optional]
	 */
	public function __construct ($timeZoneOrYear = null, $localeOrMonth = null, $dayOfMonth = null, $hour = null, $minute = null, $second = null) {}

	/**
	 * Set the Gregorian Calendar the change date
	 * @link http://www.php.net/manual/en/intlgregoriancalendar.setgregorianchange.php
	 * @param float $date 
	 * @return bool true on success or false on failure
	 */
	public function setGregorianChange (float $date) {}

	/**
	 * Get the Gregorian Calendar change date
	 * @link http://www.php.net/manual/en/intlgregoriancalendar.getgregorianchange.php
	 * @return float the change date or false on failure.
	 */
	public function getGregorianChange () {}

	/**
	 * Determine if the given year is a leap year
	 * @link http://www.php.net/manual/en/intlgregoriancalendar.isleapyear.php
	 * @param int $year 
	 * @return bool true for leap years, false otherwise and on failure.
	 */
	public function isLeapYear (int $year) {}

	/**
	 * Create a new IntlCalendar
	 * @link http://www.php.net/manual/en/intlcalendar.createinstance.php
	 * @param mixed $timeZone [optional] <p>
	 * The timezone to use.
	 * </p>
	 * reference.intl.inctimezoneparam
	 * @param string $locale [optional] A locale to use or null to use the default locale.
	 * @return IntlCalendar The created IntlCalendar instance or null on
	 * failure.
	 */
	public static function createInstance ($timeZone = null, string $locale = null) {}

	/**
	 * Get set of locale keyword values
	 * @link http://www.php.net/manual/en/intlcalendar.getkeywordvaluesforlocale.php
	 * @param string $key The locale keyword for which relevant values are to be queried. Only
	 * 'calendar' is supported.
	 * @param string $locale The locale onto which the keyword/value pair are to be appended.
	 * @param bool $commonlyUsed Whether to show only the values commonly used for the specified locale.
	 * @return Iterator An iterator that yields strings with the locale keyword
	 * values or false on failure.
	 */
	public static function getKeywordValuesForLocale (string $key, string $locale, bool $commonlyUsed) {}

	/**
	 * Get number representing the current time
	 * @link http://www.php.net/manual/en/intlcalendar.getnow.php
	 * @return float A float representing a number of milliseconds since the epoch,
	 * not counting leap seconds.
	 */
	public static function getNow () {}

	/**
	 * Get array of locales for which there is data
	 * @link http://www.php.net/manual/en/intlcalendar.getavailablelocales.php
	 * @return array An array of strings, one for which locale.
	 */
	public static function getAvailableLocales () {}

	/**
	 * Get the value for a field
	 * @link http://www.php.net/manual/en/intlcalendar.get.php
	 * @param int $field reference.intl.incfieldparam
	 * @return int An integer with the value of the time field.
	 */
	public function get (int $field) {}

	/**
	 * Get time currently represented by the object
	 * @link http://www.php.net/manual/en/intlcalendar.gettime.php
	 * @return float A float representing the number of milliseconds elapsed since the
	 * reference time (1 Jan 1970 00:00:00 UTC).
	 */
	public function getTime () {}

	/**
	 * Set the calendar time in milliseconds since the epoch
	 * @link http://www.php.net/manual/en/intlcalendar.settime.php
	 * @param float $date An instant represented by the number of number of milliseconds between
	 * such instant and the epoch, ignoring leap seconds.
	 * @return bool true on success and false on failure.
	 */
	public function setTime (float $date) {}

	/**
	 * Add a (signed) amount of time to a field
	 * @link http://www.php.net/manual/en/intlcalendar.add.php
	 * @param int $field reference.intl.incfieldparam
	 * @param int $amount The signed amount to add to the current field. If the amount is positive,
	 * the instant will be moved forward; if it is negative, the instant wil be
	 * moved into the past. The unit is implicit to the field type. For instance,
	 * hours for IntlCalendar::FIELD_HOUR_OF_DAY.
	 * @return bool true on success or false on failure.
	 */
	public function add (int $field, int $amount) {}

	/**
	 * Set the timezone used by this calendar
	 * @link http://www.php.net/manual/en/intlcalendar.settimezone.php
	 * @param mixed $timeZone The new timezone to be used by this calendar. It can be specified in the
	 * following ways:
	 * reference.intl.inctimezoneparam
	 * @return bool true on success and false on failure.
	 */
	public function setTimeZone ($timeZone) {}

	/**
	 * Whether this objectʼs time is after that of the passed object
	 * @link http://www.php.net/manual/en/intlcalendar.after.php
	 * @param IntlCalendar $other The calendar whose time will be checked against the primary objectʼs time.
	 * @return bool true if this objectʼs current time is after that of the
	 * calendar argumentʼs time. Returns false otherwise.
	 * Also returns false on failure. You can use exceptions or
	 * intl_get_error_code to detect error conditions.
	 */
	public function after (IntlCalendar $other) {}

	/**
	 * Whether this objectʼs time is before that of the passed object
	 * @link http://www.php.net/manual/en/intlcalendar.before.php
	 * @param IntlCalendar $other The calendar whose time will be checked against the primary objectʼs time.
	 * @return bool true if this objectʼs current time is before that of the
	 * calendar argumentʼs time. Returns false otherwise.
	 * Also returns false on failure. You can use exceptions or
	 * intl_get_error_code to detect error conditions.
	 */
	public function before (IntlCalendar $other) {}

	/**
	 * Set a time field or several common fields at once
	 * @link http://www.php.net/manual/en/intlcalendar.set.php
	 * @param int $field reference.intl.incfieldparam
	 * @param int $value The new value of the given field.
	 * @return bool true on success and false on failure.
	 */
	public function set (int $field, int $value) {}

	/**
	 * Add value to field without carrying into more significant fields
	 * @link http://www.php.net/manual/en/intlcalendar.roll.php
	 * @param int $field reference.intl.incfieldparam
	 * @param mixed $amountOrUpOrDown The (signed) amount to add to the field, true for rolling up (adding
	 * 1), or false for rolling down (subtracting
	 * 1).
	 * @return bool true on success or false on failure.
	 */
	public function roll (int $field, $amountOrUpOrDown) {}

	/**
	 * Clear a field or all fields
	 * @link http://www.php.net/manual/en/intlcalendar.clear.php
	 * @param int $field [optional] reference.intl.incfieldparam
	 * @return bool true on success or false on failure. Failure can only occur is
	 * invalid arguments are provided.
	 */
	public function clear (int $field = null) {}

	/**
	 * Calculate difference between given time and this objectʼs time
	 * @link http://www.php.net/manual/en/intlcalendar.fielddifference.php
	 * @param float $when The time against which to compare the quantity represented by the
	 * field. For the result to be positive, the time
	 * given for this parameter must be ahead of the time of the object the
	 * method is being invoked on.
	 * @param int $field <p>
	 * The field that represents the quantity being compared.
	 * </p>
	 * reference.intl.incfieldparam
	 * @return int a (signed) difference of time in the unit associated with the
	 * specified field or false on failure.
	 */
	public function fieldDifference (float $when, int $field) {}

	/**
	 * The maximum value for a field, considering the objectʼs current time
	 * @link http://www.php.net/manual/en/intlcalendar.getactualmaximum.php
	 * @param int $field reference.intl.incfieldparam
	 * @return int An int representing the maximum value in the units associated
	 * with the given field or false on failure.
	 */
	public function getActualMaximum (int $field) {}

	/**
	 * The minimum value for a field, considering the objectʼs current time
	 * @link http://www.php.net/manual/en/intlcalendar.getactualminimum.php
	 * @param int $field reference.intl.incfieldparam
	 * @return int An int representing the minimum value in the fieldʼs
	 * unit or false on failure.
	 */
	public function getActualMinimum (int $field) {}

	/**
	 * Tell whether a day is a weekday, weekend or a day that has a transition between the two
	 * @link http://www.php.net/manual/en/intlcalendar.getdayofweektype.php
	 * @param int $dayOfWeek One of the constants IntlCalendar::DOW_SUNDAY,
	 * IntlCalendar::DOW_MONDAY, …,
	 * IntlCalendar::DOW_SATURDAY.
	 * @return int one of the constants 
	 * IntlCalendar::DOW_TYPE_WEEKDAY,
	 * IntlCalendar::DOW_TYPE_WEEKEND,
	 * IntlCalendar::DOW_TYPE_WEEKEND_OFFSET or
	 * IntlCalendar::DOW_TYPE_WEEKEND_CEASE or false on failure.
	 */
	public function getDayOfWeekType (int $dayOfWeek) {}

	/**
	 * Get the first day of the week for the calendarʼs locale
	 * @link http://www.php.net/manual/en/intlcalendar.getfirstdayofweek.php
	 * @return int One of the constants IntlCalendar::DOW_SUNDAY,
	 * IntlCalendar::DOW_MONDAY, …,
	 * IntlCalendar::DOW_SATURDAY or false on failure.
	 */
	public function getFirstDayOfWeek () {}

	/**
	 * Get the largest local minimum value for a field
	 * @link http://www.php.net/manual/en/intlcalendar.getgreatestminimum.php
	 * @param int $field reference.intl.incfieldparam
	 * @return int An int representing a field value, in the fieldʼs
	 * unit, or false on failure.
	 */
	public function getGreatestMinimum (int $field) {}

	/**
	 * Get the smallest local maximum for a field
	 * @link http://www.php.net/manual/en/intlcalendar.getleastmaximum.php
	 * @param int $field reference.intl.incfieldparam
	 * @return int An int representing a field value in the fieldʼs
	 * unit or false on failure.
	 */
	public function getLeastMaximum (int $field) {}

	/**
	 * Get the locale associated with the object
	 * @link http://www.php.net/manual/en/intlcalendar.getlocale.php
	 * @param int $localeType Whether to fetch the actual locale (the locale from which the calendar
	 * data originates, with Locale::ACTUAL_LOCALE) or the
	 * valid locale, i.e., the most specific locale supported by ICU relatively
	 * to the requested locale – see Locale::VALID_LOCALE.
	 * From the most general to the most specific, the locales are ordered in
	 * this fashion – actual locale, valid locale, requested locale.
	 * @return string A locale string or false on failure.
	 */
	public function getLocale (int $localeType) {}

	/**
	 * Get the global maximum value for a field
	 * @link http://www.php.net/manual/en/intlcalendar.getmaximum.php
	 * @param int $field reference.intl.incfieldparam
	 * @return int An int representing a field value in the fieldʼs
	 * unit or false on failure.
	 */
	public function getMaximum (int $field) {}

	/**
	 * Get minimal number of days the first week in a year or month can have
	 * @link http://www.php.net/manual/en/intlcalendar.getminimaldaysinfirstweek.php
	 * @return int An int representing a number of days or false on failure.
	 */
	public function getMinimalDaysInFirstWeek () {}

	/**
	 * Get the global minimum value for a field
	 * @link http://www.php.net/manual/en/intlcalendar.getminimum.php
	 * @param int $field reference.intl.incfieldparam
	 * @return int An int representing a value for the given
	 * field in the fieldʼs unit or false on failure.
	 */
	public function getMinimum (int $field) {}

	/**
	 * Get the objectʼs timezone
	 * @link http://www.php.net/manual/en/intlcalendar.gettimezone.php
	 * @return IntlTimeZone An IntlTimeZone object corresponding to the one used
	 * internally in this object.
	 */
	public function getTimeZone () {}

	/**
	 * Get the calendar type
	 * @link http://www.php.net/manual/en/intlcalendar.gettype.php
	 * @return string A string representing the calendar type, such as
	 * 'gregorian', 'islamic', etc.
	 */
	public function getType () {}

	/**
	 * Get time of the day at which weekend begins or ends
	 * @link http://www.php.net/manual/en/intlcalendar.getweekendtransition.php
	 * @param string $dayOfWeek One of the constants IntlCalendar::DOW_SUNDAY,
	 * IntlCalendar::DOW_MONDAY, …,
	 * IntlCalendar::DOW_SATURDAY.
	 * @return int The number of milliseconds into the day at which the weekend begins or
	 * ends or false on failure.
	 */
	public function getWeekendTransition (string $dayOfWeek) {}

	/**
	 * Whether the objectʼs time is in Daylight Savings Time
	 * @link http://www.php.net/manual/en/intlcalendar.indaylighttime.php
	 * @return bool true if the date is in Daylight Savings Time, false otherwise.
	 * The value false may also be returned on failure, for instance after
	 * specifying invalid field values on non-lenient mode; use exceptions or query
	 * intl_get_error_code to disambiguate.
	 */
	public function inDaylightTime () {}

	/**
	 * Whether another calendar is equal but for a different time
	 * @link http://www.php.net/manual/en/intlcalendar.isequivalentto.php
	 * @param IntlCalendar $other The other calendar against which the comparison is to be made.
	 * @return bool Assuming there are no argument errors, returns true iif the calendars are
	 * equivalent except possibly for their set time.
	 */
	public function isEquivalentTo (IntlCalendar $other) {}

	/**
	 * Whether date/time interpretation is in lenient mode
	 * @link http://www.php.net/manual/en/intlcalendar.islenient.php
	 * @return bool A bool representing whether the calendar is set to lenient mode.
	 */
	public function isLenient () {}

	/**
	 * Whether a field is set
	 * @link http://www.php.net/manual/en/intlcalendar.isset.php
	 * @param int $field reference.intl.incfieldparam
	 * @return bool Assuming there are no argument errors, returns true iif the field is set.
	 */
	public function isSet (int $field) {}

	/**
	 * Whether a certain date/time is in the weekend
	 * @link http://www.php.net/manual/en/intlcalendar.isweekend.php
	 * @param float $date [optional] An optional timestamp representing the number of milliseconds since the
	 * epoch, excluding leap seconds. If null, this objectʼs current time is
	 * used instead.
	 * @return bool A bool indicating whether the given or this objectʼs time occurs
	 * in a weekend.
	 * <p>
	 * The value false may also be returned on failure, for instance after giving
	 * a date out of bounds on non-lenient mode; use exceptions or query
	 * intl_get_error_code to disambiguate.
	 * </p>
	 */
	public function isWeekend (float $date = null) {}

	/**
	 * Set the day on which the week is deemed to start
	 * @link http://www.php.net/manual/en/intlcalendar.setfirstdayofweek.php
	 * @param int $dayOfWeek One of the constants IntlCalendar::DOW_SUNDAY,
	 * IntlCalendar::DOW_MONDAY, …,
	 * IntlCalendar::DOW_SATURDAY.
	 * @return bool true on success. Failure can only happen due to invalid parameters.
	 */
	public function setFirstDayOfWeek (int $dayOfWeek) {}

	/**
	 * Set whether date/time interpretation is to be lenient
	 * @link http://www.php.net/manual/en/intlcalendar.setlenient.php
	 * @param bool $isLenient Use true to activate the lenient mode; false otherwise.
	 * @return bool true on success. Failure can only happen due to invalid parameters.
	 */
	public function setLenient (bool $isLenient) {}

	/**
	 * Set minimal number of days the first week in a year or month can have
	 * @link http://www.php.net/manual/en/intlcalendar.setminimaldaysinfirstweek.php
	 * @param int $minimalDays The number of minimal days to set.
	 * @return bool true on success, false on failure.
	 */
	public function setMinimalDaysInFirstWeek (int $minimalDays) {}

	/**
	 * Compare time of two IntlCalendar objects for equality
	 * @link http://www.php.net/manual/en/intlcalendar.equals.php
	 * @param IntlCalendar $other The calendar to compare with the primary object.
	 * @return bool true if the current time of both this and the passed in
	 * IntlCalendar object are the same, or false
	 * otherwise. The value false can also be returned on failure. This can only
	 * happen if bad arguments are passed in. In any case, the two cases can be
	 * distinguished by calling intl_get_error_code.
	 */
	public function equals (IntlCalendar $other) {}

	/**
	 * Get behavior for handling repeating wall time
	 * @link http://www.php.net/manual/en/intlcalendar.getrepeatedwalltimeoption.php
	 * @return int One of the constants IntlCalendar::WALLTIME_FIRST or
	 * IntlCalendar::WALLTIME_LAST.
	 */
	public function getRepeatedWallTimeOption () {}

	/**
	 * Get behavior for handling skipped wall time
	 * @link http://www.php.net/manual/en/intlcalendar.getskippedwalltimeoption.php
	 * @return int One of the constants IntlCalendar::WALLTIME_FIRST,
	 * IntlCalendar::WALLTIME_LAST or
	 * IntlCalendar::WALLTIME_NEXT_VALID.
	 */
	public function getSkippedWallTimeOption () {}

	/**
	 * Set behavior for handling repeating wall times at negative timezone offset transitions
	 * @link http://www.php.net/manual/en/intlcalendar.setrepeatedwalltimeoption.php
	 * @param int $wallTimeOption One of the constants IntlCalendar::WALLTIME_FIRST or
	 * IntlCalendar::WALLTIME_LAST.
	 * @return bool true on success. Failure can only happen due to invalid parameters.
	 */
	public function setRepeatedWallTimeOption (int $wallTimeOption) {}

	/**
	 * Set behavior for handling skipped wall times at positive timezone offset transitions
	 * @link http://www.php.net/manual/en/intlcalendar.setskippedwalltimeoption.php
	 * @param int $wallTimeOption One of the constants IntlCalendar::WALLTIME_FIRST,
	 * IntlCalendar::WALLTIME_LAST or
	 * IntlCalendar::WALLTIME_NEXT_VALID.
	 * @return bool true on success. Failure can only happen due to invalid parameters.
	 */
	public function setSkippedWallTimeOption (int $wallTimeOption) {}

	/**
	 * Create an IntlCalendar from a DateTime object or string
	 * @link http://www.php.net/manual/en/intlcalendar.fromdatetime.php
	 * @param mixed $dateTime A DateTime object or a string that
	 * can be passed to DateTime::__construct.
	 * @return IntlCalendar The created IntlCalendar object or null in case of
	 * failure. If a string is passed, any exception that occurs
	 * inside the DateTime constructor is propagated.
	 */
	public static function fromDateTime ($dateTime) {}

	/**
	 * Convert an IntlCalendar into a DateTime object
	 * @link http://www.php.net/manual/en/intlcalendar.todatetime.php
	 * @return DateTime A DateTime object with the same timezone as this
	 * object (though using PHPʼs database instead of ICUʼs) and the same time,
	 * except for the smaller precision (second precision instead of millisecond).
	 * Returns false on failure.
	 */
	public function toDateTime () {}

	/**
	 * Get last error code on the object
	 * @link http://www.php.net/manual/en/intlcalendar.geterrorcode.php
	 * @return int An ICU error code indicating either success, failure or a warning.
	 */
	public function getErrorCode () {}

	/**
	 * Get last error message on the object
	 * @link http://www.php.net/manual/en/intlcalendar.geterrormessage.php
	 * @return string The error message associated with last error that occurred in a function call
	 * on this object, or a string indicating the non-existance of an error.
	 */
	public function getErrorMessage () {}

}

/**
 * This class is provided because Unicode contains large number of characters
 * and incorporates the varied writing systems of the world and their incorrect
 * usage can expose programs or systems to possible security attacks using
 * characters similarity.
 * <p>Provided methods allow to check whether an individual string is likely an attempt
 * at confusing the reader (spoof detection), such as "pаypаl"
 * spelled with Cyrillic 'а' characters.</p>
 * @link http://www.php.net/manual/en/class.spoofchecker.php
 */
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


	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/spoofchecker.construct.php
	 */
	public function __construct () {}

	/**
	 * Checks if a given text contains any suspicious characters
	 * @link http://www.php.net/manual/en/spoofchecker.issuspicious.php
	 * @param string $text String to test.
	 * @param string $error [optional] This variable is set by-reference to string containing an error, if there
	 * were any.
	 * @return bool true if there are suspicious characters, false otherwise.
	 */
	public function isSuspicious (string $text, string &$error = null) {}

	/**
	 * Checks if given strings can be confused
	 * @link http://www.php.net/manual/en/spoofchecker.areconfusable.php
	 * @param string $str1 First string to check.
	 * @param string $str2 Second string to check.
	 * @param string $error [optional] This variable is set by-reference to string containing an error, if there
	 * were any.
	 * @return bool true if two given strings can be confused, false otherwise.
	 */
	public function areConfusable (string $str1, string $str2, string &$error = null) {}

	/**
	 * Locales to use when running checks
	 * @link http://www.php.net/manual/en/spoofchecker.setallowedlocales.php
	 * @param string $locale_list 
	 * @return void 
	 */
	public function setAllowedLocales (string $locale_list) {}

	/**
	 * Set the checks to run
	 * @link http://www.php.net/manual/en/spoofchecker.setchecks.php
	 * @param int $checks 
	 * @return void 
	 */
	public function setChecks (int $checks) {}

	/**
	 * @param mixed $level
	 */
	public function setRestrictionLevel ($level) {}

}

/**
 * This class is used for generating exceptions when errors occur inside intl
 * functions. Such exceptions are only generated when intl.use_exceptions is enabled.
 * @link http://www.php.net/manual/en/class.intlexception.php
 */
class IntlException extends Exception implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param mixed $message [optional]
	 * @param mixed $code [optional]
	 * @param mixed $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * This class represents iterator objects throughout the intl extension
 * whenever the iterator cannot be identified with any other object provided
 * by the extension. The distinct iterator object used internally by the
 * foreach
 * construct can only be obtained (in the relevant part here) from
 * objects, so objects of this class serve the purpose of providing the hook
 * through which this internal object can be obtained. As a convenience, this
 * class also implements the Iterator interface,
 * allowing the collection of values to be navigated using the methods
 * defined in that interface. Both these methods and the internal iterator
 * objects provided to foreach are backed by the same
 * state (e.g. the position of the iterator and its current value).
 * <p>Subclasses may provide richer functionality.</p>
 * @link http://www.php.net/manual/en/class.intliterator.php
 */
class IntlIterator implements Iterator, Traversable {

	/**
	 * Get the current element
	 * @link http://www.php.net/manual/en/intliterator.current.php
	 * @return mixed 
	 */
	public function current () {}

	/**
	 * Get the current key
	 * @link http://www.php.net/manual/en/intliterator.key.php
	 * @return string 
	 */
	public function key () {}

	/**
	 * Move forward to the next element
	 * @link http://www.php.net/manual/en/intliterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Rewind the iterator to the first element
	 * @link http://www.php.net/manual/en/intliterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Check if current position is valid
	 * @link http://www.php.net/manual/en/intliterator.valid.php
	 * @return bool 
	 */
	public function valid () {}

}

/**
 * A “break iterator” is an ICU object that exposes methods for locating
 * boundaries in text (e.g. word or sentence boundaries).
 * The PHP IntlBreakIterator serves as the base class
 * for all types of ICU break iterators. Where extra functionality is
 * available, the intl extension may expose the ICU break iterator with
 * suitable subclasses, such as
 * IntlRuleBasedBreakIterator or
 * IntlCodePointBreakIterator.
 * <p>This class implements Traversable. Traversing an
 * IntlBreakIterator yields non-negative integer
 * values representing the successive locations of the text boundaries,
 * expressed as UTF-8 code units (byte) counts, taken from the beggining of
 * the text (which has the location 0). The keys yielded
 * by the iterator simply form the sequence of natural numbers
 * {0, 1, 2, …}.</p>
 * @link http://www.php.net/manual/en/class.intlbreakiterator.php
 */
class IntlBreakIterator implements Traversable {
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
	 * Private constructor for disallowing instantiation
	 * @link http://www.php.net/manual/en/intlbreakiterator.construct.php
	 */
	private function __construct () {}

	/**
	 * Create break iterator for word breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createwordinstance.php
	 * @param string $locale [optional] 
	 * @return IntlBreakIterator 
	 */
	public static function createWordInstance (string $locale = null) {}

	/**
	 * Create break iterator for logically possible line breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createlineinstance.php
	 * @param string $locale [optional] 
	 * @return IntlBreakIterator 
	 */
	public static function createLineInstance (string $locale = null) {}

	/**
	 * Create break iterator for boundaries of combining character sequences
	 * @link http://www.php.net/manual/en/intlbreakiterator.createcharacterinstance.php
	 * @param string $locale [optional] 
	 * @return IntlBreakIterator 
	 */
	public static function createCharacterInstance (string $locale = null) {}

	/**
	 * Create break iterator for sentence breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createsentenceinstance.php
	 * @param string $locale [optional] 
	 * @return IntlBreakIterator 
	 */
	public static function createSentenceInstance (string $locale = null) {}

	/**
	 * Create break iterator for title-casing breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createtitleinstance.php
	 * @param string $locale [optional] 
	 * @return IntlBreakIterator 
	 */
	public static function createTitleInstance (string $locale = null) {}

	/**
	 * Create break iterator for boundaries of code points
	 * @link http://www.php.net/manual/en/intlbreakiterator.createcodepointinstance.php
	 * @return IntlBreakIterator 
	 */
	public static function createCodePointInstance () {}

	/**
	 * Get the text being scanned
	 * @link http://www.php.net/manual/en/intlbreakiterator.gettext.php
	 * @return string 
	 */
	public function getText () {}

	/**
	 * Set the text being scanned
	 * @link http://www.php.net/manual/en/intlbreakiterator.settext.php
	 * @param string $text 
	 * @return bool 
	 */
	public function setText (string $text) {}

	/**
	 * Set position to the first character in the text
	 * @link http://www.php.net/manual/en/intlbreakiterator.first.php
	 * @return int 
	 */
	public function first () {}

	/**
	 * Set the iterator position to index beyond the last character
	 * @link http://www.php.net/manual/en/intlbreakiterator.last.php
	 * @return int 
	 */
	public function last () {}

	/**
	 * Set the iterator position to the boundary immediately before the current
	 * @link http://www.php.net/manual/en/intlbreakiterator.previous.php
	 * @return int 
	 */
	public function previous () {}

	/**
	 * Advance the iterator the next boundary
	 * @link http://www.php.net/manual/en/intlbreakiterator.next.php
	 * @param int $offset [optional] 
	 * @return int 
	 */
	public function next (int $offset = null) {}

	/**
	 * Get index of current position
	 * @link http://www.php.net/manual/en/intlbreakiterator.current.php
	 * @return int 
	 */
	public function current () {}

	/**
	 * Advance the iterator to the first boundary following specified offset
	 * @link http://www.php.net/manual/en/intlbreakiterator.following.php
	 * @param int $offset 
	 * @return int 
	 */
	public function following (int $offset) {}

	/**
	 * Set the iterator position to the first boundary before an offset
	 * @link http://www.php.net/manual/en/intlbreakiterator.preceding.php
	 * @param int $offset 
	 * @return int 
	 */
	public function preceding (int $offset) {}

	/**
	 * Tell whether an offset is a boundaryʼs offset
	 * @link http://www.php.net/manual/en/intlbreakiterator.isboundary.php
	 * @param int $offset 
	 * @return bool 
	 */
	public function isBoundary (int $offset) {}

	/**
	 * Get the locale associated with the object
	 * @link http://www.php.net/manual/en/intlbreakiterator.getlocale.php
	 * @param string $locale_type 
	 * @return string 
	 */
	public function getLocale (string $locale_type) {}

	/**
	 * Create iterator for navigating fragments between boundaries
	 * @link http://www.php.net/manual/en/intlbreakiterator.getpartsiterator.php
	 * @param int $key_type [optional] <p>
	 * Optional key type. Possible values are:
	 * <p>
	 * IntlPartsIterator::KEY_SEQUENTIAL
	 * - The default. Sequentially increasing integers used as key.
	 * IntlPartsIterator::KEY_LEFT
	 * - Byte offset left of current part used as key.
	 * IntlPartsIterator::KEY_RIGHT
	 * - Byte offset right of current part used as key.
	 * </p>
	 * </p>
	 * @return IntlPartsIterator 
	 */
	public function getPartsIterator (int $key_type = null) {}

	/**
	 * Get last error code on the object
	 * @link http://www.php.net/manual/en/intlbreakiterator.geterrorcode.php
	 * @return int 
	 */
	public function getErrorCode () {}

	/**
	 * Get last error message on the object
	 * @link http://www.php.net/manual/en/intlbreakiterator.geterrormessage.php
	 * @return string 
	 */
	public function getErrorMessage () {}

}

/**
 * A subclass of IntlBreakIterator that encapsulates
 * ICU break iterators whose behavior is specified using a set of rules. This
 * is the most common kind of break iterators.
 * <p>These rules are described in the ICU Boundary Analysis
 * User Guide.</p>
 * @link http://www.php.net/manual/en/class.intlrulebasedbreakiterator.php
 */
class IntlRuleBasedBreakIterator extends IntlBreakIterator implements Traversable {
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
	 * Create iterator from ruleset
	 * @link http://www.php.net/manual/en/intlrulebasedbreakiterator.construct.php
	 * @param mixed $rules
	 * @param mixed $areCompiled [optional]
	 */
	public function __construct ($rules, $areCompiled = null) {}

	/**
	 * Get the rule set used to create this object
	 * @link http://www.php.net/manual/en/intlrulebasedbreakiterator.getrules.php
	 * @return string 
	 */
	public function getRules () {}

	/**
	 * Get the largest status value from the break rules that determined the current break position
	 * @link http://www.php.net/manual/en/intlrulebasedbreakiterator.getrulestatus.php
	 * @return int 
	 */
	public function getRuleStatus () {}

	/**
	 * Get the status values from the break rules that determined the current break position
	 * @link http://www.php.net/manual/en/intlrulebasedbreakiterator.getrulestatusvec.php
	 * @return array 
	 */
	public function getRuleStatusVec () {}

	/**
	 * Get the binary form of compiled rules
	 * @link http://www.php.net/manual/en/intlrulebasedbreakiterator.getbinaryrules.php
	 * @return string 
	 */
	public function getBinaryRules () {}

	/**
	 * Create break iterator for word breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createwordinstance.php
	 * @param string $locale [optional] 
	 * @return IntlBreakIterator 
	 */
	public static function createWordInstance (string $locale = null) {}

	/**
	 * Create break iterator for logically possible line breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createlineinstance.php
	 * @param string $locale [optional] 
	 * @return IntlBreakIterator 
	 */
	public static function createLineInstance (string $locale = null) {}

	/**
	 * Create break iterator for boundaries of combining character sequences
	 * @link http://www.php.net/manual/en/intlbreakiterator.createcharacterinstance.php
	 * @param string $locale [optional] 
	 * @return IntlBreakIterator 
	 */
	public static function createCharacterInstance (string $locale = null) {}

	/**
	 * Create break iterator for sentence breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createsentenceinstance.php
	 * @param string $locale [optional] 
	 * @return IntlBreakIterator 
	 */
	public static function createSentenceInstance (string $locale = null) {}

	/**
	 * Create break iterator for title-casing breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createtitleinstance.php
	 * @param string $locale [optional] 
	 * @return IntlBreakIterator 
	 */
	public static function createTitleInstance (string $locale = null) {}

	/**
	 * Create break iterator for boundaries of code points
	 * @link http://www.php.net/manual/en/intlbreakiterator.createcodepointinstance.php
	 * @return IntlBreakIterator 
	 */
	public static function createCodePointInstance () {}

	/**
	 * Get the text being scanned
	 * @link http://www.php.net/manual/en/intlbreakiterator.gettext.php
	 * @return string 
	 */
	public function getText () {}

	/**
	 * Set the text being scanned
	 * @link http://www.php.net/manual/en/intlbreakiterator.settext.php
	 * @param string $text 
	 * @return bool 
	 */
	public function setText (string $text) {}

	/**
	 * Set position to the first character in the text
	 * @link http://www.php.net/manual/en/intlbreakiterator.first.php
	 * @return int 
	 */
	public function first () {}

	/**
	 * Set the iterator position to index beyond the last character
	 * @link http://www.php.net/manual/en/intlbreakiterator.last.php
	 * @return int 
	 */
	public function last () {}

	/**
	 * Set the iterator position to the boundary immediately before the current
	 * @link http://www.php.net/manual/en/intlbreakiterator.previous.php
	 * @return int 
	 */
	public function previous () {}

	/**
	 * Advance the iterator the next boundary
	 * @link http://www.php.net/manual/en/intlbreakiterator.next.php
	 * @param int $offset [optional] 
	 * @return int 
	 */
	public function next (int $offset = null) {}

	/**
	 * Get index of current position
	 * @link http://www.php.net/manual/en/intlbreakiterator.current.php
	 * @return int 
	 */
	public function current () {}

	/**
	 * Advance the iterator to the first boundary following specified offset
	 * @link http://www.php.net/manual/en/intlbreakiterator.following.php
	 * @param int $offset 
	 * @return int 
	 */
	public function following (int $offset) {}

	/**
	 * Set the iterator position to the first boundary before an offset
	 * @link http://www.php.net/manual/en/intlbreakiterator.preceding.php
	 * @param int $offset 
	 * @return int 
	 */
	public function preceding (int $offset) {}

	/**
	 * Tell whether an offset is a boundaryʼs offset
	 * @link http://www.php.net/manual/en/intlbreakiterator.isboundary.php
	 * @param int $offset 
	 * @return bool 
	 */
	public function isBoundary (int $offset) {}

	/**
	 * Get the locale associated with the object
	 * @link http://www.php.net/manual/en/intlbreakiterator.getlocale.php
	 * @param string $locale_type 
	 * @return string 
	 */
	public function getLocale (string $locale_type) {}

	/**
	 * Create iterator for navigating fragments between boundaries
	 * @link http://www.php.net/manual/en/intlbreakiterator.getpartsiterator.php
	 * @param int $key_type [optional] <p>
	 * Optional key type. Possible values are:
	 * <p>
	 * IntlPartsIterator::KEY_SEQUENTIAL
	 * - The default. Sequentially increasing integers used as key.
	 * IntlPartsIterator::KEY_LEFT
	 * - Byte offset left of current part used as key.
	 * IntlPartsIterator::KEY_RIGHT
	 * - Byte offset right of current part used as key.
	 * </p>
	 * </p>
	 * @return IntlPartsIterator 
	 */
	public function getPartsIterator (int $key_type = null) {}

	/**
	 * Get last error code on the object
	 * @link http://www.php.net/manual/en/intlbreakiterator.geterrorcode.php
	 * @return int 
	 */
	public function getErrorCode () {}

	/**
	 * Get last error message on the object
	 * @link http://www.php.net/manual/en/intlbreakiterator.geterrormessage.php
	 * @return string 
	 */
	public function getErrorMessage () {}

}

/**
 * This break iterator
 * identifies the boundaries between UTF-8 code points.
 * @link http://www.php.net/manual/en/class.intlcodepointbreakiterator.php
 */
class IntlCodePointBreakIterator extends IntlBreakIterator implements Traversable {
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
	 * Get last code point passed over after advancing or receding the iterator
	 * @link http://www.php.net/manual/en/intlcodepointbreakiterator.getlastcodepoint.php
	 * @return int 
	 */
	public function getLastCodePoint () {}

	/**
	 * Private constructor for disallowing instantiation
	 * @link http://www.php.net/manual/en/intlbreakiterator.construct.php
	 */
	private function __construct () {}

	/**
	 * Create break iterator for word breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createwordinstance.php
	 * @param string $locale [optional] 
	 * @return IntlBreakIterator 
	 */
	public static function createWordInstance (string $locale = null) {}

	/**
	 * Create break iterator for logically possible line breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createlineinstance.php
	 * @param string $locale [optional] 
	 * @return IntlBreakIterator 
	 */
	public static function createLineInstance (string $locale = null) {}

	/**
	 * Create break iterator for boundaries of combining character sequences
	 * @link http://www.php.net/manual/en/intlbreakiterator.createcharacterinstance.php
	 * @param string $locale [optional] 
	 * @return IntlBreakIterator 
	 */
	public static function createCharacterInstance (string $locale = null) {}

	/**
	 * Create break iterator for sentence breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createsentenceinstance.php
	 * @param string $locale [optional] 
	 * @return IntlBreakIterator 
	 */
	public static function createSentenceInstance (string $locale = null) {}

	/**
	 * Create break iterator for title-casing breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createtitleinstance.php
	 * @param string $locale [optional] 
	 * @return IntlBreakIterator 
	 */
	public static function createTitleInstance (string $locale = null) {}

	/**
	 * Create break iterator for boundaries of code points
	 * @link http://www.php.net/manual/en/intlbreakiterator.createcodepointinstance.php
	 * @return IntlBreakIterator 
	 */
	public static function createCodePointInstance () {}

	/**
	 * Get the text being scanned
	 * @link http://www.php.net/manual/en/intlbreakiterator.gettext.php
	 * @return string 
	 */
	public function getText () {}

	/**
	 * Set the text being scanned
	 * @link http://www.php.net/manual/en/intlbreakiterator.settext.php
	 * @param string $text 
	 * @return bool 
	 */
	public function setText (string $text) {}

	/**
	 * Set position to the first character in the text
	 * @link http://www.php.net/manual/en/intlbreakiterator.first.php
	 * @return int 
	 */
	public function first () {}

	/**
	 * Set the iterator position to index beyond the last character
	 * @link http://www.php.net/manual/en/intlbreakiterator.last.php
	 * @return int 
	 */
	public function last () {}

	/**
	 * Set the iterator position to the boundary immediately before the current
	 * @link http://www.php.net/manual/en/intlbreakiterator.previous.php
	 * @return int 
	 */
	public function previous () {}

	/**
	 * Advance the iterator the next boundary
	 * @link http://www.php.net/manual/en/intlbreakiterator.next.php
	 * @param int $offset [optional] 
	 * @return int 
	 */
	public function next (int $offset = null) {}

	/**
	 * Get index of current position
	 * @link http://www.php.net/manual/en/intlbreakiterator.current.php
	 * @return int 
	 */
	public function current () {}

	/**
	 * Advance the iterator to the first boundary following specified offset
	 * @link http://www.php.net/manual/en/intlbreakiterator.following.php
	 * @param int $offset 
	 * @return int 
	 */
	public function following (int $offset) {}

	/**
	 * Set the iterator position to the first boundary before an offset
	 * @link http://www.php.net/manual/en/intlbreakiterator.preceding.php
	 * @param int $offset 
	 * @return int 
	 */
	public function preceding (int $offset) {}

	/**
	 * Tell whether an offset is a boundaryʼs offset
	 * @link http://www.php.net/manual/en/intlbreakiterator.isboundary.php
	 * @param int $offset 
	 * @return bool 
	 */
	public function isBoundary (int $offset) {}

	/**
	 * Get the locale associated with the object
	 * @link http://www.php.net/manual/en/intlbreakiterator.getlocale.php
	 * @param string $locale_type 
	 * @return string 
	 */
	public function getLocale (string $locale_type) {}

	/**
	 * Create iterator for navigating fragments between boundaries
	 * @link http://www.php.net/manual/en/intlbreakiterator.getpartsiterator.php
	 * @param int $key_type [optional] <p>
	 * Optional key type. Possible values are:
	 * <p>
	 * IntlPartsIterator::KEY_SEQUENTIAL
	 * - The default. Sequentially increasing integers used as key.
	 * IntlPartsIterator::KEY_LEFT
	 * - Byte offset left of current part used as key.
	 * IntlPartsIterator::KEY_RIGHT
	 * - Byte offset right of current part used as key.
	 * </p>
	 * </p>
	 * @return IntlPartsIterator 
	 */
	public function getPartsIterator (int $key_type = null) {}

	/**
	 * Get last error code on the object
	 * @link http://www.php.net/manual/en/intlbreakiterator.geterrorcode.php
	 * @return int 
	 */
	public function getErrorCode () {}

	/**
	 * Get last error message on the object
	 * @link http://www.php.net/manual/en/intlbreakiterator.geterrormessage.php
	 * @return string 
	 */
	public function getErrorMessage () {}

}

/**
 * Objects of this class can be obtained from
 * IntlBreakIterator objects. While the break
 * iterators provide a sequence of boundary positions when iterated,
 * IntlPartsIterator objects provide, as a
 * convenience, the text fragments comprehended between two successive
 * boundaries.
 * <p>The keys may represent the offset of the left boundary, right boundary, or
 * they may just the sequence of non-negative integers. See
 * IntlBreakIterator::getPartsIterator.</p>
 * @link http://www.php.net/manual/en/class.intlpartsiterator.php
 */
class IntlPartsIterator extends IntlIterator implements Traversable, Iterator {
	const KEY_SEQUENTIAL = 0;
	const KEY_LEFT = 1;
	const KEY_RIGHT = 2;


	/**
	 * Get IntlBreakIterator backing this parts iterator
	 * @link http://www.php.net/manual/en/intlpartsiterator.getbreakiterator.php
	 * @return IntlBreakIterator 
	 */
	public function getBreakIterator () {}

	/**
	 * Get the current element
	 * @link http://www.php.net/manual/en/intliterator.current.php
	 * @return mixed 
	 */
	public function current () {}

	/**
	 * Get the current key
	 * @link http://www.php.net/manual/en/intliterator.key.php
	 * @return string 
	 */
	public function key () {}

	/**
	 * Move forward to the next element
	 * @link http://www.php.net/manual/en/intliterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Rewind the iterator to the first element
	 * @link http://www.php.net/manual/en/intliterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Check if current position is valid
	 * @link http://www.php.net/manual/en/intliterator.valid.php
	 * @return bool 
	 */
	public function valid () {}

}

/**
 * @link http://www.php.net/manual/en/class.uconverter.php
 */
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
	 * Create UConverter object
	 * @link http://www.php.net/manual/en/uconverter.construct.php
	 * @param mixed $destination_encoding [optional]
	 * @param mixed $source_encoding [optional]
	 */
	public function __construct ($destination_encoding = null, $source_encoding = null) {}

	/**
	 * Set the source encoding
	 * @link http://www.php.net/manual/en/uconverter.setsourceencoding.php
	 * @param string $encoding 
	 * @return void 
	 */
	public function setSourceEncoding (string $encoding) {}

	/**
	 * Set the destination encoding
	 * @link http://www.php.net/manual/en/uconverter.setdestinationencoding.php
	 * @param string $encoding 
	 * @return void 
	 */
	public function setDestinationEncoding (string $encoding) {}

	/**
	 * Get the source encoding
	 * @link http://www.php.net/manual/en/uconverter.getsourceencoding.php
	 * @return string 
	 */
	public function getSourceEncoding () {}

	/**
	 * Get the destination encoding
	 * @link http://www.php.net/manual/en/uconverter.getdestinationencoding.php
	 * @return string 
	 */
	public function getDestinationEncoding () {}

	/**
	 * Get the source convertor type
	 * @link http://www.php.net/manual/en/uconverter.getsourcetype.php
	 * @return int 
	 */
	public function getSourceType () {}

	/**
	 * Get the destination converter type
	 * @link http://www.php.net/manual/en/uconverter.getdestinationtype.php
	 * @return int 
	 */
	public function getDestinationType () {}

	/**
	 * Get substitution chars
	 * @link http://www.php.net/manual/en/uconverter.getsubstchars.php
	 * @return string 
	 */
	public function getSubstChars () {}

	/**
	 * Set the substitution chars
	 * @link http://www.php.net/manual/en/uconverter.setsubstchars.php
	 * @param string $chars 
	 * @return void 
	 */
	public function setSubstChars (string $chars) {}

	/**
	 * Default "to" callback function
	 * @link http://www.php.net/manual/en/uconverter.toucallback.php
	 * @param int $reason 
	 * @param string $source 
	 * @param string $codeUnits 
	 * @param int $error 
	 * @return mixed 
	 */
	public function toUCallback (int $reason, string $source, string $codeUnits, int &$error) {}

	/**
	 * Default "from" callback function
	 * @link http://www.php.net/manual/en/uconverter.fromucallback.php
	 * @param int $reason 
	 * @param string $source 
	 * @param string $codePoint 
	 * @param int $error 
	 * @return mixed 
	 */
	public function fromUCallback (int $reason, string $source, string $codePoint, int &$error) {}

	/**
	 * Convert string from one charset to another
	 * @link http://www.php.net/manual/en/uconverter.convert.php
	 * @param string $str 
	 * @param bool $reverse [optional] 
	 * @return string 
	 */
	public function convert (string $str, bool $reverse = null) {}

	/**
	 * Convert string from one charset to another
	 * @link http://www.php.net/manual/en/uconverter.transcode.php
	 * @param string $str 
	 * @param string $toEncoding 
	 * @param string $fromEncoding 
	 * @param array $options [optional] 
	 * @return string 
	 */
	public static function transcode (string $str, string $toEncoding, string $fromEncoding, array $options = null) {}

	/**
	 * Get last error code on the object
	 * @link http://www.php.net/manual/en/uconverter.geterrorcode.php
	 * @return int 
	 */
	public function getErrorCode () {}

	/**
	 * Get last error message on the object
	 * @link http://www.php.net/manual/en/uconverter.geterrormessage.php
	 * @return string 
	 */
	public function getErrorMessage () {}

	/**
	 * Get string representation of the callback reason
	 * @link http://www.php.net/manual/en/uconverter.reasontext.php
	 * @param int $reason [optional] 
	 * @return string 
	 */
	public static function reasonText (int $reason = null) {}

	/**
	 * Get the available canonical converter names
	 * @link http://www.php.net/manual/en/uconverter.getavailable.php
	 * @return array 
	 */
	public static function getAvailable () {}

	/**
	 * Get the aliases of the given name
	 * @link http://www.php.net/manual/en/uconverter.getaliases.php
	 * @param string $name 
	 * @return array 
	 */
	public static function getAliases (string $name) {}

	/**
	 * Get standards associated to converter names
	 * @link http://www.php.net/manual/en/uconverter.getstandards.php
	 * @return array 
	 */
	public static function getStandards () {}

}

/**
 * IntlChar provides access to a number of utility
 * methods that can be used to access information about Unicode characters.
 * <p>The methods and constants adhere closely to the names and behavior used by the underlying ICU library.</p>
 * @link http://www.php.net/manual/en/class.intlchar.php
 */
class IntlChar  {
	const UNICODE_VERSION = 11.0;
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
	const PROPERTY_BINARY_LIMIT = 65;
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
	const BLOCK_CODE_COUNT = 292;
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
	const JG_COUNT = 102;
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
	 * Return Unicode character by code point value
	 * @link http://www.php.net/manual/en/intlchar.chr.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return string A string containing the single character specified by the Unicode code point value.
	 */
	public static function chr ($codepoint) {}

	/**
	 * Return Unicode code point value of character
	 * @link http://www.php.net/manual/en/intlchar.ord.php
	 * @param mixed $character A Unicode character.
	 * @return int the Unicode code point value as an integer.
	 */
	public static function ord ($character) {}

	/**
	 * Check a binary Unicode property for a code point
	 * @link http://www.php.net/manual/en/intlchar.hasbinaryproperty.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @param int $property The Unicode property to lookup (see the IntlChar::PROPERTY_&#42; constants).
	 * @return bool true or false according to the binary Unicode property value for codepoint.
	 * Also false if property is out of bounds or if the Unicode version does not have data for
	 * the property at all, or not for this code point.
	 */
	public static function hasBinaryProperty ($codepoint, int $property) {}

	/**
	 * Check if code point has the Alphabetic Unicode property
	 * @link http://www.php.net/manual/en/intlchar.isualphabetic.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint has the Alphabetic Unicode property, false if not.
	 */
	public static function isUAlphabetic ($codepoint) {}

	/**
	 * Check if code point has the Lowercase Unicode property
	 * @link http://www.php.net/manual/en/intlchar.isulowercase.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint has the Lowercase Unicode property, false if not.
	 */
	public static function isULowercase ($codepoint) {}

	/**
	 * Check if code point has the Uppercase Unicode property
	 * @link http://www.php.net/manual/en/intlchar.isuuppercase.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint has the Uppercase Unicode property, false if not.
	 */
	public static function isUUppercase ($codepoint) {}

	/**
	 * Check if code point has the White_Space Unicode property
	 * @link http://www.php.net/manual/en/intlchar.isuwhitespace.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint has the White_Space Unicode property, false if not.
	 */
	public static function isUWhiteSpace ($codepoint) {}

	/**
	 * Get the value for a Unicode property for a code point
	 * @link http://www.php.net/manual/en/intlchar.getintpropertyvalue.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @param int $property The Unicode property to lookup (see the IntlChar::PROPERTY_&#42; constants).
	 * @return int the numeric value that is directly the property value or, for enumerated properties, corresponds to the
	 * numeric value of the enumerated constant of the respective property value enumeration type.
	 * <p>
	 * Returns 0 or 1 (for false/true) for binary Unicode properties.
	 * </p>
	 * <p>
	 * Returns a bit-mask for mask properties.
	 * </p>
	 * <p>
	 * Returns 0 if property is out of bounds or if the Unicode version does not
	 * have data for the property at all, or not for this code point.
	 * </p>
	 */
	public static function getIntPropertyValue ($codepoint, int $property) {}

	/**
	 * Get the min value for a Unicode property
	 * @link http://www.php.net/manual/en/intlchar.getintpropertyminvalue.php
	 * @param int $property The Unicode property to lookup (see the IntlChar::PROPERTY_&#42; constants).
	 * @return int The minimum value returned by IntlChar::getIntPropertyValue for a Unicode property.
	 * 0 if the property selector is out of range.
	 */
	public static function getIntPropertyMinValue (int $property) {}

	/**
	 * Get the max value for a Unicode property
	 * @link http://www.php.net/manual/en/intlchar.getintpropertymaxvalue.php
	 * @param int $property The Unicode property to lookup (see the IntlChar::PROPERTY_&#42; constants).
	 * @return int The maximum value returned by IntlChar::getIntPropertyValue for a Unicode property.
	 * &lt;=0 if the property selector is out of range.
	 */
	public static function getIntPropertyMaxValue (int $property) {}

	/**
	 * Get the numeric value for a Unicode code point
	 * @link http://www.php.net/manual/en/intlchar.getnumericvalue.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return float Numeric value of codepoint,
	 * or IntlChar::NO_NUMERIC_VALUE if none is defined. This
	 * constant was added in PHP 7.0.6, prior to this version the literal value
	 * (float)-123456789 may be used instead.
	 */
	public static function getNumericValue ($codepoint) {}

	/**
	 * Check if code point is a lowercase letter
	 * @link http://www.php.net/manual/en/intlchar.islower.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is an Ll lowercase letter, false if not.
	 */
	public static function islower ($codepoint) {}

	/**
	 * Check if code point has the general category "Lu" (uppercase letter)
	 * @link http://www.php.net/manual/en/intlchar.isupper.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is an Lu uppercase letter, false if not.
	 */
	public static function isupper ($codepoint) {}

	/**
	 * Check if code point is a titlecase letter
	 * @link http://www.php.net/manual/en/intlchar.istitle.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is a titlecase letter, false if not.
	 */
	public static function istitle ($codepoint) {}

	/**
	 * Check if code point is a digit character
	 * @link http://www.php.net/manual/en/intlchar.isdigit.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is a digit character, false if not.
	 */
	public static function isdigit ($codepoint) {}

	/**
	 * Check if code point is a letter character
	 * @link http://www.php.net/manual/en/intlchar.isalpha.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is a letter character, false if not.
	 */
	public static function isalpha ($codepoint) {}

	/**
	 * Check if code point is an alphanumeric character
	 * @link http://www.php.net/manual/en/intlchar.isalnum.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is an alphanumeric character, false if not.
	 */
	public static function isalnum ($codepoint) {}

	/**
	 * Check if code point is a hexadecimal digit
	 * @link http://www.php.net/manual/en/intlchar.isxdigit.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is a hexadecimal character, false if not.
	 */
	public static function isxdigit ($codepoint) {}

	/**
	 * Check if code point is punctuation character
	 * @link http://www.php.net/manual/en/intlchar.ispunct.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is a punctuation character, false if not.
	 */
	public static function ispunct ($codepoint) {}

	/**
	 * Check if code point is a graphic character
	 * @link http://www.php.net/manual/en/intlchar.isgraph.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is a "graphic" character, false if not.
	 */
	public static function isgraph ($codepoint) {}

	/**
	 * Check if code point is a "blank" or "horizontal space" character
	 * @link http://www.php.net/manual/en/intlchar.isblank.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is either a "blank" or "horizontal space" character, false if not.
	 */
	public static function isblank ($codepoint) {}

	/**
	 * Check whether the code point is defined
	 * @link http://www.php.net/manual/en/intlchar.isdefined.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is a defined character, false if not.
	 */
	public static function isdefined ($codepoint) {}

	/**
	 * Check if code point is a space character
	 * @link http://www.php.net/manual/en/intlchar.isspace.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is a space character, false if not.
	 */
	public static function isspace ($codepoint) {}

	/**
	 * Check if code point is a space character according to Java
	 * @link http://www.php.net/manual/en/intlchar.isjavaspacechar.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is a space character according to Java, false if not.
	 */
	public static function isJavaSpaceChar ($codepoint) {}

	/**
	 * Check if code point is a whitespace character according to ICU
	 * @link http://www.php.net/manual/en/intlchar.iswhitespace.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is a whitespace character according to ICU, false if not.
	 */
	public static function isWhitespace ($codepoint) {}

	/**
	 * Check if code point is a control character
	 * @link http://www.php.net/manual/en/intlchar.iscntrl.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is a control character, false if not.
	 */
	public static function iscntrl ($codepoint) {}

	/**
	 * Check if code point is an ISO control code
	 * @link http://www.php.net/manual/en/intlchar.isisocontrol.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is an ISO control code, false if not.
	 */
	public static function isISOControl ($codepoint) {}

	/**
	 * Check if code point is a printable character
	 * @link http://www.php.net/manual/en/intlchar.isprint.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is a printable character, false if not.
	 */
	public static function isprint ($codepoint) {}

	/**
	 * Check if code point is a base character
	 * @link http://www.php.net/manual/en/intlchar.isbase.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is a base character, false if not.
	 */
	public static function isbase ($codepoint) {}

	/**
	 * Get bidirectional category value for a code point
	 * @link http://www.php.net/manual/en/intlchar.chardirection.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return int The bidirectional category value; one of the following constants:
	 * <p>
	 * IntlChar::CHAR_DIRECTION_LEFT_TO_RIGHT
	 * IntlChar::CHAR_DIRECTION_RIGHT_TO_LEFT
	 * IntlChar::CHAR_DIRECTION_EUROPEAN_NUMBER
	 * IntlChar::CHAR_DIRECTION_EUROPEAN_NUMBER_SEPARATOR
	 * IntlChar::CHAR_DIRECTION_EUROPEAN_NUMBER_TERMINATOR
	 * IntlChar::CHAR_DIRECTION_ARABIC_NUMBER
	 * IntlChar::CHAR_DIRECTION_COMMON_NUMBER_SEPARATOR
	 * IntlChar::CHAR_DIRECTION_BLOCK_SEPARATOR
	 * IntlChar::CHAR_DIRECTION_SEGMENT_SEPARATOR
	 * IntlChar::CHAR_DIRECTION_WHITE_SPACE_NEUTRAL
	 * IntlChar::CHAR_DIRECTION_OTHER_NEUTRAL
	 * IntlChar::CHAR_DIRECTION_LEFT_TO_RIGHT_EMBEDDING
	 * IntlChar::CHAR_DIRECTION_LEFT_TO_RIGHT_OVERRIDE
	 * IntlChar::CHAR_DIRECTION_RIGHT_TO_LEFT_ARABIC
	 * IntlChar::CHAR_DIRECTION_RIGHT_TO_LEFT_EMBEDDING
	 * IntlChar::CHAR_DIRECTION_RIGHT_TO_LEFT_OVERRIDE
	 * IntlChar::CHAR_DIRECTION_POP_DIRECTIONAL_FORMAT
	 * IntlChar::CHAR_DIRECTION_DIR_NON_SPACING_MARK
	 * IntlChar::CHAR_DIRECTION_BOUNDARY_NEUTRAL
	 * IntlChar::CHAR_DIRECTION_FIRST_STRONG_ISOLATE
	 * IntlChar::CHAR_DIRECTION_LEFT_TO_RIGHT_ISOLATE
	 * IntlChar::CHAR_DIRECTION_RIGHT_TO_LEFT_ISOLATE
	 * IntlChar::CHAR_DIRECTION_POP_DIRECTIONAL_ISOLATE
	 * IntlChar::CHAR_DIRECTION_CHAR_DIRECTION_COUNT
	 * </p>
	 */
	public static function charDirection ($codepoint) {}

	/**
	 * Check if code point has the Bidi_Mirrored property
	 * @link http://www.php.net/manual/en/intlchar.ismirrored.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint has the Bidi_Mirrored property, false if not.
	 */
	public static function isMirrored ($codepoint) {}

	/**
	 * Get the "mirror-image" character for a code point
	 * @link http://www.php.net/manual/en/intlchar.charmirror.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return mixed another Unicode code point that may serve as a mirror-image substitute, or codepoint
	 * itself if there is no such mapping or codepoint does not have the
	 * Bidi_Mirrored property.
	 * <p>The return type will be integer unless the code point was passed as a UTF-8 string, in which case a string will be returned.</p>
	 */
	public static function charMirror ($codepoint) {}

	/**
	 * Get the paired bracket character for a code point
	 * @link http://www.php.net/manual/en/intlchar.getbidipairedbracket.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return mixed the paired bracket code point, or codepoint itself if there is no such mapping.
	 * <p>The return type will be integer unless the code point was passed as a UTF-8 string, in which case a string will be returned.</p>
	 */
	public static function getBidiPairedBracket ($codepoint) {}

	/**
	 * Get the general category value for a code point
	 * @link http://www.php.net/manual/en/intlchar.chartype.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return int the general category type, which may be one of the following constants:
	 * <p>
	 * IntlChar::CHAR_CATEGORY_UNASSIGNED
	 * IntlChar::CHAR_CATEGORY_GENERAL_OTHER_TYPES
	 * IntlChar::CHAR_CATEGORY_UPPERCASE_LETTER
	 * IntlChar::CHAR_CATEGORY_LOWERCASE_LETTER
	 * IntlChar::CHAR_CATEGORY_TITLECASE_LETTER
	 * IntlChar::CHAR_CATEGORY_MODIFIER_LETTER
	 * IntlChar::CHAR_CATEGORY_OTHER_LETTER
	 * IntlChar::CHAR_CATEGORY_NON_SPACING_MARK
	 * IntlChar::CHAR_CATEGORY_ENCLOSING_MARK
	 * IntlChar::CHAR_CATEGORY_COMBINING_SPACING_MARK
	 * IntlChar::CHAR_CATEGORY_DECIMAL_DIGIT_NUMBER
	 * IntlChar::CHAR_CATEGORY_LETTER_NUMBER
	 * IntlChar::CHAR_CATEGORY_OTHER_NUMBER
	 * IntlChar::CHAR_CATEGORY_SPACE_SEPARATOR
	 * IntlChar::CHAR_CATEGORY_LINE_SEPARATOR
	 * IntlChar::CHAR_CATEGORY_PARAGRAPH_SEPARATOR
	 * IntlChar::CHAR_CATEGORY_CONTROL_CHAR
	 * IntlChar::CHAR_CATEGORY_FORMAT_CHAR
	 * IntlChar::CHAR_CATEGORY_PRIVATE_USE_CHAR
	 * IntlChar::CHAR_CATEGORY_SURROGATE
	 * IntlChar::CHAR_CATEGORY_DASH_PUNCTUATION
	 * IntlChar::CHAR_CATEGORY_START_PUNCTUATION
	 * IntlChar::CHAR_CATEGORY_END_PUNCTUATION
	 * IntlChar::CHAR_CATEGORY_CONNECTOR_PUNCTUATION
	 * IntlChar::CHAR_CATEGORY_OTHER_PUNCTUATION
	 * IntlChar::CHAR_CATEGORY_MATH_SYMBOL
	 * IntlChar::CHAR_CATEGORY_CURRENCY_SYMBOL
	 * IntlChar::CHAR_CATEGORY_MODIFIER_SYMBOL
	 * IntlChar::CHAR_CATEGORY_OTHER_SYMBOL
	 * IntlChar::CHAR_CATEGORY_INITIAL_PUNCTUATION
	 * IntlChar::CHAR_CATEGORY_FINAL_PUNCTUATION
	 * IntlChar::CHAR_CATEGORY_CHAR_CATEGORY_COUNT
	 * </p>
	 */
	public static function charType ($codepoint) {}

	/**
	 * Enumerate all code points with their Unicode general categories
	 * @link http://www.php.net/manual/en/intlchar.enumchartypes.php
	 * @param callable $callback <p>
	 * The function that is to be called for each contiguous range of code points with the same general category.
	 * The following three arguments will be passed into it:
	 * <p>
	 * integer $start - The starting code point of the range
	 * integer $end - The ending code point of the range
	 * integer $name - The category type (one of the IntlChar::CHAR_CATEGORY_&#42; constants)
	 * </p>
	 * </p>
	 * @return void 
	 */
	public static function enumCharTypes (callable $callback) {}

	/**
	 * Get the combining class of a code point
	 * @link http://www.php.net/manual/en/intlchar.getcombiningclass.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return int the combining class of the character.
	 */
	public static function getCombiningClass ($codepoint) {}

	/**
	 * Get the decimal digit value of a decimal digit character
	 * @link http://www.php.net/manual/en/intlchar.chardigitvalue.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return int The decimal digit value of codepoint,
	 * or -1 if it is not a decimal digit character.
	 */
	public static function charDigitValue ($codepoint) {}

	/**
	 * Get the Unicode allocation block containing a code point
	 * @link http://www.php.net/manual/en/intlchar.getblockcode.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return int the block value for codepoint.
	 * See the IntlChar::BLOCK_CODE_&#42; constants for possible return values.
	 */
	public static function getBlockCode ($codepoint) {}

	/**
	 * Retrieve the name of a Unicode character
	 * @link http://www.php.net/manual/en/intlchar.charname.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @param int $nameChoice [optional] <p>
	 * Which set of names to use for the lookup. Can be any of these constants:
	 * <p>
	 * IntlChar::UNICODE_CHAR_NAME (default)
	 * IntlChar::UNICODE_10_CHAR_NAME
	 * IntlChar::EXTENDED_CHAR_NAME
	 * IntlChar::CHAR_NAME_ALIAS
	 * IntlChar::CHAR_NAME_CHOICE_COUNT
	 * </p>
	 * </p>
	 * @return string The corresponding name, or an empty string if there is no name for this character.
	 */
	public static function charName ($codepoint, int $nameChoice = null) {}

	/**
	 * Find Unicode character by name and return its code point value
	 * @link http://www.php.net/manual/en/intlchar.charfromname.php
	 * @param string $characterName Full name of the Unicode character.
	 * @param int $nameChoice [optional] <p>
	 * Which set of names to use for the lookup. Can be any of these constants:
	 * <p>
	 * IntlChar::UNICODE_CHAR_NAME (default)
	 * IntlChar::UNICODE_10_CHAR_NAME
	 * IntlChar::EXTENDED_CHAR_NAME
	 * IntlChar::CHAR_NAME_ALIAS
	 * IntlChar::CHAR_NAME_CHOICE_COUNT
	 * </p>
	 * </p>
	 * @return int The Unicode value of the code point with the given name (as an integer), or false if there is no such code point.
	 */
	public static function charFromName (string $characterName, int $nameChoice = null) {}

	/**
	 * Enumerate all assigned Unicode characters within a range
	 * @link http://www.php.net/manual/en/intlchar.enumcharnames.php
	 * @param mixed $start The first code point in the enumeration range.
	 * @param mixed $limit One more than the last code point in the enumeration range (the first one after the range).
	 * @param callable $callback <p>
	 * The function that is to be called for each character name. The following three arguments will be passed into it:
	 * <p>
	 * integer $codepoint - The numeric code point value
	 * integer $nameChoice - The same value as the nameChoice parameter below
	 * string $name - The name of the character
	 * </p>
	 * </p>
	 * @param int $nameChoice [optional] <p>
	 * Selector for which kind of names to enumerate. Can be any of these constants:
	 * <p>
	 * IntlChar::UNICODE_CHAR_NAME (default)
	 * IntlChar::UNICODE_10_CHAR_NAME
	 * IntlChar::EXTENDED_CHAR_NAME
	 * IntlChar::CHAR_NAME_ALIAS
	 * IntlChar::CHAR_NAME_CHOICE_COUNT
	 * </p>
	 * </p>
	 * @return void 
	 */
	public static function enumCharNames ($start, $limit, callable $callback, int $nameChoice = null) {}

	/**
	 * Get the Unicode name for a property
	 * @link http://www.php.net/manual/en/intlchar.getpropertyname.php
	 * @param int $property <p>The Unicode property to lookup (see the IntlChar::PROPERTY_&#42; constants).</p>
	 * <p>
	 * IntlChar::PROPERTY_INVALID_CODE should not be used.
	 * Also, if property is out of range, false is returned.
	 * </p>
	 * @param int $nameChoice [optional] <p>
	 * Selector for which name to get. If out of range, false is returned.
	 * </p>
	 * <p>
	 * All properties have a long name. Most have a short name, but some do not. Unicode allows for additional names;
	 * if present these will be returned by adding 1, 2, etc. to IntlChar::LONG_PROPERTY_NAME.
	 * </p>
	 * @return string the name, or false if either the property or the nameChoice
	 * is out of range.
	 * <p>
	 * If a given nameChoice returns false, then all larger values of
	 * nameChoice will return false, with one exception: if false is returned for
	 * IntlChar::SHORT_PROPERTY_NAME, then IntlChar::LONG_PROPERTY_NAME
	 * (and higher) may still return a non-false value.
	 * </p>
	 */
	public static function getPropertyName (int $property, int $nameChoice = null) {}

	/**
	 * Get the property constant value for a given property name
	 * @link http://www.php.net/manual/en/intlchar.getpropertyenum.php
	 * @param string $alias The property name to be matched. The name is compared using "loose matching" as described in PropertyAliases.txt.
	 * @return int an IntlChar::PROPERTY_ constant value,
	 * or IntlChar::PROPERTY_INVALID_CODE if the given name does not match any property.
	 */
	public static function getPropertyEnum (string $alias) {}

	/**
	 * Get the Unicode name for a property value
	 * @link http://www.php.net/manual/en/intlchar.getpropertyvaluename.php
	 * @param int $property <p>The Unicode property to lookup (see the IntlChar::PROPERTY_&#42; constants).</p>
	 * <p>
	 * If out of range, or this method doesn't work with the given value, false is returned.
	 * </p>
	 * @param int $value <p>
	 * Selector for a value for the given property. If out of range, false is returned.
	 * </p>
	 * <p>
	 * In general, valid values range from 0 up to some maximum. There are a couple exceptions:
	 * <p>
	 * IntlChar::PROPERTY_BLOCK values begin at the non-zero value IntlChar::BLOCK_CODE_BASIC_LATIN
	 * IntlChar::PROPERTY_CANONICAL_COMBINING_CLASS values are not contiguous and range from 0..240.
	 * </p>
	 * </p>
	 * @param int $nameChoice [optional] <p>
	 * Selector for which name to get. If out of range, false is returned.
	 * </p>
	 * <p>
	 * All values have a long name. Most have a short name, but some do not. Unicode allows for additional names;
	 * if present these will be returned by adding 1, 2, etc. to IntlChar::LONG_PROPERTY_NAME.
	 * </p>
	 * @return string the name, or false if either the property or the nameChoice
	 * is out of range.
	 * <p>
	 * If a given nameChoice returns false, then all larger values of nameChoice
	 * will return false, with one exception: if false is returned for IntlChar::SHORT_PROPERTY_NAME,
	 * then IntlChar::LONG_PROPERTY_NAME (and higher) may still return a non-false value.
	 * </p>
	 */
	public static function getPropertyValueName (int $property, int $value, int $nameChoice = null) {}

	/**
	 * Get the property value for a given value name
	 * @link http://www.php.net/manual/en/intlchar.getpropertyvalueenum.php
	 * @param int $property <p>The Unicode property to lookup (see the IntlChar::PROPERTY_&#42; constants).</p>
	 * <p>
	 * If out of range, or this method doesn't work with the given value,
	 * IntlChar::PROPERTY_INVALID_CODE is returned.
	 * </p>
	 * @param string $name The value name to be matched. The name is compared using "loose matching" as described in PropertyValueAliases.txt.
	 * @return int the corresponding value integer, or IntlChar::PROPERTY_INVALID_CODE if the given name
	 * does not match any value of the given property, or if the property is invalid.
	 */
	public static function getPropertyValueEnum (int $property, string $name) {}

	/**
	 * Check if code point is permissible as the first character in an identifier
	 * @link http://www.php.net/manual/en/intlchar.isidstart.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint may start an identifier, false if not.
	 */
	public static function isIDStart ($codepoint) {}

	/**
	 * Check if code point is permissible in an identifier
	 * @link http://www.php.net/manual/en/intlchar.isidpart.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is the code point may occur in an identifier, false if not.
	 */
	public static function isIDPart ($codepoint) {}

	/**
	 * Check if code point is an ignorable character
	 * @link http://www.php.net/manual/en/intlchar.isidignorable.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint is ignorable in identifiers, false if not.
	 */
	public static function isIDIgnorable ($codepoint) {}

	/**
	 * Check if code point is permissible as the first character in a Java identifier
	 * @link http://www.php.net/manual/en/intlchar.isjavaidstart.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint may start a Java identifier, false if not.
	 */
	public static function isJavaIDStart ($codepoint) {}

	/**
	 * Check if code point is permissible in a Java identifier
	 * @link http://www.php.net/manual/en/intlchar.isjavaidpart.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool true if
	 * codepoint may occur in a Java identifier, false if not.
	 */
	public static function isJavaIDPart ($codepoint) {}

	/**
	 * Make Unicode character lowercase
	 * @link http://www.php.net/manual/en/intlchar.tolower.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return mixed the Simple_Lowercase_Mapping of the code point, if any;
	 * otherwise the code point itself.
	 * <p>The return type will be integer unless the code point was passed as a UTF-8 string, in which case a string will be returned.</p>
	 */
	public static function tolower ($codepoint) {}

	/**
	 * Make Unicode character uppercase
	 * @link http://www.php.net/manual/en/intlchar.toupper.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return mixed the Simple_Uppercase_Mapping of the code point, if any;
	 * otherwise the code point itself.
	 * <p>The return type will be integer unless the code point was passed as a UTF-8 string, in which case a string will be returned.</p>
	 */
	public static function toupper ($codepoint) {}

	/**
	 * Make Unicode character titlecase
	 * @link http://www.php.net/manual/en/intlchar.totitle.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return mixed the Simple_Titlecase_Mapping of the code point, if any;
	 * otherwise the code point itself.
	 * <p>The return type will be integer unless the code point was passed as a UTF-8 string, in which case a string will be returned.</p>
	 */
	public static function totitle ($codepoint) {}

	/**
	 * Perform case folding on a code point
	 * @link http://www.php.net/manual/en/intlchar.foldcase.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @param int $options [optional] Either IntlChar::FOLD_CASE_DEFAULT (default)
	 * or IntlChar::FOLD_CASE_EXCLUDE_SPECIAL_I.
	 * @return mixed the Simple_Case_Folding of the code point, if any; otherwise the code point itself.
	 */
	public static function foldCase ($codepoint, int $options = null) {}

	/**
	 * Get the decimal digit value of a code point for a given radix
	 * @link http://www.php.net/manual/en/intlchar.digit.php
	 * @param string $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @param int $radix [optional] The radix (defaults to 10).
	 * @return int the numeric value represented by the character in the specified radix,
	 * or false if there is no value or if the value exceeds the radix.
	 */
	public static function digit (string $codepoint, int $radix = null) {}

	/**
	 * Get character representation for a given digit and radix
	 * @link http://www.php.net/manual/en/intlchar.fordigit.php
	 * @param int $digit The number to convert to a character.
	 * @param int $radix [optional] The radix (defaults to 10).
	 * @return int The character representation (as a string) of the specified digit in the specified radix.
	 */
	public static function forDigit (int $digit, int $radix = null) {}

	/**
	 * Get the "age" of the code point
	 * @link http://www.php.net/manual/en/intlchar.charage.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return array The Unicode version number, as an array.
	 * For example, version 1.3.31.2 would be represented as [1, 3, 31, 2].
	 */
	public static function charAge ($codepoint) {}

	/**
	 * Get the Unicode version
	 * @link http://www.php.net/manual/en/intlchar.getunicodeversion.php
	 * @return array An array containing the Unicode version number.
	 */
	public static function getUnicodeVersion () {}

	/**
	 * Get the FC_NFKC_Closure property for a code point
	 * @link http://www.php.net/manual/en/intlchar.getfc-nfkc-closure.php
	 * @param mixed $codepoint The integer codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return string the FC_NFKC_Closure property string for the codepoint, or an empty string if there is none.
	 */
	public static function getFC_NFKC_Closure ($codepoint) {}

}

/**
 * @param mixed $arg1
 */
function collator_create ($arg1) {}

/**
 * @param Collator $object
 * @param mixed $arg1
 * @param mixed $arg2
 */
function collator_compare (Collator $object, $arg1, $arg2) {}

/**
 * @param Collator $object
 * @param mixed $arg1
 */
function collator_get_attribute (Collator $object, $arg1) {}

/**
 * @param Collator $object
 * @param mixed $arg1
 * @param mixed $arg2
 */
function collator_set_attribute (Collator $object, $arg1, $arg2) {}

/**
 * @param Collator $object
 */
function collator_get_strength (Collator $object) {}

/**
 * @param Collator $object
 * @param mixed $arg1
 */
function collator_set_strength (Collator $object, $arg1) {}

/**
 * @param Collator $object
 * @param mixed $arr
 * @param mixed $sort_flags [optional]
 */
function collator_sort (Collator $objectarray , &$arr, $sort_flags = null) {}

/**
 * @param Collator $coll
 * @param mixed $arr
 */
function collator_sort_with_sort_keys (Collator $collarray , &$arr) {}

/**
 * @param Collator $object
 * @param mixed $arr
 * @param mixed $sort_flags [optional]
 */
function collator_asort (Collator $objectarray , &$arr, $sort_flags = null) {}

/**
 * @param Collator $object
 * @param mixed $arg1
 */
function collator_get_locale (Collator $object, $arg1) {}

/**
 * @param Collator $object
 */
function collator_get_error_code (Collator $object) {}

/**
 * @param Collator $object
 */
function collator_get_error_message (Collator $object) {}

/**
 * @param Collator $object
 * @param mixed $arg1
 */
function collator_get_sort_key (Collator $object, $arg1) {}

/**
 * @param mixed $locale
 * @param mixed $style
 * @param mixed $pattern [optional]
 */
function numfmt_create ($locale, $style, $pattern = null) {}

/**
 * @param mixed $nf
 * @param mixed $num
 * @param mixed $type [optional]
 */
function numfmt_format ($nf, $num, $type = null) {}

/**
 * @param mixed $formatter
 * @param mixed $string
 * @param mixed $type [optional]
 * @param mixed $position [optional]
 */
function numfmt_parse ($formatter, $string, $type = null, &$position = null) {}

/**
 * @param mixed $nf
 * @param mixed $num
 * @param mixed $currency
 */
function numfmt_format_currency ($nf, $num, $currency) {}

/**
 * @param mixed $formatter
 * @param mixed $string
 * @param mixed $currency
 * @param mixed $position [optional]
 */
function numfmt_parse_currency ($formatter, $string, &$currency, &$position = null) {}

/**
 * @param mixed $nf
 * @param mixed $attr
 * @param mixed $value
 */
function numfmt_set_attribute ($nf, $attr, $value) {}

/**
 * @param mixed $nf
 * @param mixed $attr
 */
function numfmt_get_attribute ($nf, $attr) {}

/**
 * @param mixed $nf
 * @param mixed $attr
 * @param mixed $value
 */
function numfmt_set_text_attribute ($nf, $attr, $value) {}

/**
 * @param mixed $nf
 * @param mixed $attr
 */
function numfmt_get_text_attribute ($nf, $attr) {}

/**
 * @param mixed $nf
 * @param mixed $attr
 * @param mixed $symbol
 */
function numfmt_set_symbol ($nf, $attr, $symbol) {}

/**
 * @param mixed $nf
 * @param mixed $attr
 */
function numfmt_get_symbol ($nf, $attr) {}

/**
 * @param mixed $nf
 * @param mixed $pattern
 */
function numfmt_set_pattern ($nf, $pattern) {}

/**
 * @param mixed $nf
 */
function numfmt_get_pattern ($nf) {}

/**
 * @param mixed $nf
 * @param mixed $type [optional]
 */
function numfmt_get_locale ($nf, $type = null) {}

/**
 * @param mixed $nf
 */
function numfmt_get_error_code ($nf) {}

/**
 * @param mixed $nf
 */
function numfmt_get_error_message ($nf) {}

/**
 * @param mixed $input
 * @param mixed $form [optional]
 */
function normalizer_normalize ($input, $form = null) {}

/**
 * @param mixed $input
 * @param mixed $form [optional]
 */
function normalizer_is_normalized ($input, $form = null) {}

/**
 * @param mixed $input
 */
function normalizer_get_raw_decomposition ($input) {}

function locale_get_default () {}

/**
 * @param mixed $arg1
 */
function locale_set_default ($arg1) {}

/**
 * @param mixed $arg1
 */
function locale_get_primary_language ($arg1) {}

/**
 * @param mixed $arg1
 */
function locale_get_script ($arg1) {}

/**
 * @param mixed $arg1
 */
function locale_get_region ($arg1) {}

/**
 * @param mixed $arg1
 */
function locale_get_keywords ($arg1) {}

/**
 * @param mixed $locale
 * @param mixed $in_locale [optional]
 */
function locale_get_display_script ($locale, $in_locale = null) {}

/**
 * @param mixed $locale
 * @param mixed $in_locale [optional]
 */
function locale_get_display_region ($locale, $in_locale = null) {}

/**
 * @param mixed $locale
 * @param mixed $in_locale [optional]
 */
function locale_get_display_name ($locale, $in_locale = null) {}

/**
 * @param mixed $locale
 * @param mixed $in_locale [optional]
 */
function locale_get_display_language ($locale, $in_locale = null) {}

/**
 * @param mixed $locale
 * @param mixed $in_locale [optional]
 */
function locale_get_display_variant ($locale, $in_locale = null) {}

/**
 * @param mixed $arg1
 */
function locale_compose ($arg1) {}

/**
 * @param mixed $arg1
 */
function locale_parse ($arg1) {}

/**
 * @param mixed $arg1
 */
function locale_get_all_variants ($arg1) {}

/**
 * @param mixed $langtag
 * @param mixed $locale
 * @param mixed $canonicalize [optional]
 */
function locale_filter_matches ($langtag, $locale, $canonicalize = null) {}

/**
 * @param mixed $arg1
 */
function locale_canonicalize ($arg1) {}

/**
 * @param mixed $langtag
 * @param mixed $locale
 * @param mixed $canonicalize [optional]
 * @param mixed $def [optional]
 */
function locale_lookup ($langtag, $locale, $canonicalize = null, $def = null) {}

/**
 * @param mixed $arg1
 */
function locale_accept_from_http ($arg1) {}

/**
 * @param mixed $locale
 * @param mixed $pattern
 */
function msgfmt_create ($locale, $pattern) {}

/**
 * @param mixed $nf
 * @param mixed $args
 */
function msgfmt_format ($nf, $args) {}

/**
 * @param mixed $locale
 * @param mixed $pattern
 * @param mixed $args
 */
function msgfmt_format_message ($locale, $pattern, $args) {}

/**
 * @param mixed $nf
 * @param mixed $source
 */
function msgfmt_parse ($nf, $source) {}

/**
 * @param mixed $locale
 * @param mixed $pattern
 * @param mixed $source
 */
function msgfmt_parse_message ($locale, $pattern, $source) {}

/**
 * @param mixed $mf
 * @param mixed $pattern
 */
function msgfmt_set_pattern ($mf, $pattern) {}

/**
 * @param mixed $mf
 */
function msgfmt_get_pattern ($mf) {}

/**
 * @param mixed $mf
 */
function msgfmt_get_locale ($mf) {}

/**
 * @param mixed $nf
 */
function msgfmt_get_error_code ($nf) {}

/**
 * @param mixed $coll
 */
function msgfmt_get_error_message ($coll) {}

/**
 * @param mixed $locale
 * @param mixed $date_type
 * @param mixed $time_type
 * @param mixed $timezone_str [optional]
 * @param mixed $calendar [optional]
 * @param mixed $pattern [optional]
 */
function datefmt_create ($locale, $date_type, $time_type, $timezone_str = null, $calendar = null, $pattern = null) {}

/**
 * @param mixed $mf
 */
function datefmt_get_datetype ($mf) {}

/**
 * @param mixed $mf
 */
function datefmt_get_timetype ($mf) {}

/**
 * @param mixed $mf
 */
function datefmt_get_calendar ($mf) {}

/**
 * @param mixed $mf
 */
function datefmt_get_calendar_object ($mf) {}

/**
 * @param mixed $mf
 * @param mixed $calendar
 */
function datefmt_set_calendar ($mf, $calendar) {}

/**
 * @param mixed $mf
 */
function datefmt_get_locale ($mf) {}

/**
 * @param mixed $mf
 */
function datefmt_get_timezone_id ($mf) {}

/**
 * @param mixed $mf
 */
function datefmt_get_timezone ($mf) {}

/**
 * @param mixed $mf
 * @param mixed $timezone
 */
function datefmt_set_timezone ($mf, $timezone) {}

/**
 * @param mixed $mf
 */
function datefmt_get_pattern ($mf) {}

/**
 * @param mixed $mf
 * @param mixed $pattern
 */
function datefmt_set_pattern ($mf, $pattern) {}

/**
 * @param mixed $mf
 */
function datefmt_is_lenient ($mf) {}

/**
 * @param mixed $mf
 */
function datefmt_set_lenient ($mf) {}

/**
 * @param mixed $args [optional]
 * @param mixed $array [optional]
 */
function datefmt_format ($args = null, $array = null) {}

/**
 * @param mixed $object
 * @param mixed $format [optional]
 * @param mixed $locale [optional]
 */
function datefmt_format_object ($object, $format = null, $locale = null) {}

/**
 * @param mixed $formatter
 * @param mixed $string
 * @param mixed $position [optional]
 */
function datefmt_parse ($formatter, $string, &$position = null) {}

/**
 * @param mixed $formatter
 * @param mixed $string
 * @param mixed $position [optional]
 */
function datefmt_localtime ($formatter, $string, &$position = null) {}

/**
 * @param mixed $nf
 */
function datefmt_get_error_code ($nf) {}

/**
 * @param mixed $coll
 */
function datefmt_get_error_message ($coll) {}

/**
 * Get string length in grapheme units
 * @link http://www.php.net/manual/en/function.grapheme-strlen.php
 * @param string $input The string being measured for length. It must be a valid UTF-8 string.
 * @return int The length of the string on success, and 0 if the string is empty.
 */
function grapheme_strlen (string $input) {}

/**
 * Find position (in grapheme units) of first occurrence of a string
 * @link http://www.php.net/manual/en/function.grapheme-strpos.php
 * @param string $haystack The string to look in. Must be valid UTF-8.
 * @param string $needle The string to look for. Must be valid UTF-8.
 * @param int $offset [optional] The optional $offset parameter allows you to specify where in $haystack to
 * start searching as an offset in grapheme units (not bytes or characters).
 * If the offset is negative, it is treated relative to the end of the string.
 * The position returned is still relative to the beginning of haystack
 * regardless of the value of $offset.
 * @return int the position as an integer. If needle is not found, grapheme_strpos() will return boolean FALSE.
 */
function grapheme_strpos (string $haystack, string $needle, int $offset = null) {}

/**
 * Find position (in grapheme units) of first occurrence of a case-insensitive string
 * @link http://www.php.net/manual/en/function.grapheme-stripos.php
 * @param string $haystack The string to look in. Must be valid UTF-8.
 * @param string $needle The string to look for. Must be valid UTF-8.
 * @param int $offset [optional] The optional $offset parameter allows you to specify where in haystack to
 * start searching as an offset in grapheme units (not bytes or characters).
 * If the offset is negative, it is treated relative to the end of the string.
 * The position returned is still relative to the beginning of haystack
 * regardless of the value of $offset.
 * @return int the position as an integer. If needle is not found, grapheme_stripos() will return boolean FALSE.
 */
function grapheme_stripos (string $haystack, string $needle, int $offset = null) {}

/**
 * Find position (in grapheme units) of last occurrence of a string
 * @link http://www.php.net/manual/en/function.grapheme-strrpos.php
 * @param string $haystack The string to look in. Must be valid UTF-8.
 * @param string $needle The string to look for. Must be valid UTF-8.
 * @param int $offset [optional] The optional $offset parameter allows you to specify where in $haystack to
 * start searching as an offset in grapheme units (not bytes or characters).
 * The position returned is still relative to the beginning of haystack
 * regardless of the value of $offset.
 * @return int the position as an integer. If needle is not found, grapheme_strrpos() will return boolean FALSE.
 */
function grapheme_strrpos (string $haystack, string $needle, int $offset = null) {}

/**
 * Find position (in grapheme units) of last occurrence of a case-insensitive string
 * @link http://www.php.net/manual/en/function.grapheme-strripos.php
 * @param string $haystack The string to look in. Must be valid UTF-8.
 * @param string $needle The string to look for. Must be valid UTF-8.
 * @param int $offset [optional] The optional $offset parameter allows you to specify where in $haystack to
 * start searching as an offset in grapheme units (not bytes or characters).
 * The position returned is still relative to the beginning of haystack
 * regardless of the value of $offset.
 * @return int the position as an integer. If needle is not found, grapheme_strripos() will return boolean FALSE.
 */
function grapheme_strripos (string $haystack, string $needle, int $offset = null) {}

/**
 * Return part of a string
 * @link http://www.php.net/manual/en/function.grapheme-substr.php
 * @param string $string The input string. Must be valid UTF-8.
 * @param int $start Start position in default grapheme units.
 * If $start is non-negative, the returned string will start at the
 * $start'th position in $string, counting from zero. If $start is negative,
 * the returned string will start at the $start'th grapheme unit from the 
 * end of string.
 * @param int $length [optional] Length in grapheme units.
 * If $length is given and is positive, the string returned will contain
 * at most $length grapheme units beginning from $start (depending on the 
 * length of string). If $length is given and is negative, then
 * that many grapheme units will be omitted from the end of string (after the
 * start position has been calculated when a start is negative). If $start
 * denotes a position beyond this truncation, false will be returned.
 * @return string the extracted part of $string.
 */
function grapheme_substr (string $string, int $start, int $length = null) {}

/**
 * Returns part of haystack string from the first occurrence of needle to the end of haystack
 * @link http://www.php.net/manual/en/function.grapheme-strstr.php
 * @param string $haystack The input string. Must be valid UTF-8.
 * @param string $needle The string to look for. Must be valid UTF-8.
 * @param bool $before_needle [optional] If true, grapheme_strstr() returns the part of the
 * haystack before the first occurrence of the needle (excluding the needle).
 * @return string the portion of string, or FALSE if needle is not found.
 */
function grapheme_strstr (string $haystack, string $needle, bool $before_needle = null) {}

/**
 * Returns part of haystack string from the first occurrence of case-insensitive needle to the end of haystack
 * @link http://www.php.net/manual/en/function.grapheme-stristr.php
 * @param string $haystack The input string. Must be valid UTF-8.
 * @param string $needle The string to look for. Must be valid UTF-8.
 * @param bool $before_needle [optional] If true, grapheme_strstr() returns the part of the
 * haystack before the first occurrence of the needle (excluding needle).
 * @return string the portion of $haystack, or FALSE if $needle is not found.
 */
function grapheme_stristr (string $haystack, string $needle, bool $before_needle = null) {}

/**
 * Function to extract a sequence of default grapheme clusters from a text buffer, which must be encoded in UTF-8
 * @link http://www.php.net/manual/en/function.grapheme-extract.php
 * @param string $haystack String to search.
 * @param int $size Maximum number items - based on the $extract_type - to return.
 * @param int $extract_type [optional] <p>
 * Defines the type of units referred to by the $size parameter:
 * </p>
 * <p>
 * <p>
 * GRAPHEME_EXTR_COUNT (default) - $size is the number of default
 * grapheme clusters to extract.
 * GRAPHEME_EXTR_MAXBYTES - $size is the maximum number of bytes
 * returned.
 * GRAPHEME_EXTR_MAXCHARS - $size is the maximum number of UTF-8
 * characters returned.
 * </p>
 * </p>
 * @param int $start [optional] Starting position in $haystack in bytes - if given, it must be zero or a
 * positive value that is less than or equal to the length of $haystack in
 * bytes, or a negative value that counts from the end of $haystack.
 * If $start does not point to the first byte of a UTF-8
 * character, the start position is moved to the next character boundary.
 * @param int $next [optional] Reference to a value that will be set to the next starting position.
 * When the call returns, this may point to the first byte position past the end of the string.
 * @return string A string starting at offset $start and ending on a default grapheme cluster
 * boundary that conforms to the $size and $extract_type specified.
 */
function grapheme_extract (string $haystack, int $size, int $extract_type = null, int $start = null, int &$next = null) {}

/**
 * Convert domain name to IDNA ASCII form
 * @link http://www.php.net/manual/en/function.idn-to-ascii.php
 * @param string $domain The domain to convert, which must be UTF-8 encoded.
 * @param int $options [optional] Conversion options - combination of IDNA_&#42; constants
 * (except IDNA_ERROR_&#42; constants).
 * @param int $variant [optional] Either INTL_IDNA_VARIANT_2003 for IDNA 2003 or
 * INTL_IDNA_VARIANT_UTS46 for UTS #46.
 * @param array $idna_info [optional] This parameter can be used only if
 * INTL_IDNA_VARIANT_UTS46 was used for
 * variant. In that case, it will be filled with an
 * array with the keys 'result', the possibly illegal
 * result of the transformation,
 * 'isTransitionalDifferent', a boolean indicating
 * whether the usage of the transitional mechanisms of UTS #46 either has
 * or would have changed the result and 'errors',
 * which is an int representing a bitset of the error
 * constants IDNA_ERROR_&#42;.
 * @return string The domain name encoded in ASCII-compatible form, or false on failure
 */
function idn_to_ascii (string $domain, int $options = null, int $variant = null, array &$idna_info = null) {}

/**
 * Convert domain name from IDNA ASCII to Unicode
 * @link http://www.php.net/manual/en/function.idn-to-utf8.php
 * @param string $domain Domain to convert in an IDNA ASCII-compatible format.
 * @param int $options [optional] Conversion options - combination of IDNA_&#42; constants
 * (except IDNA_ERROR_&#42; constants).
 * @param int $variant [optional] Either INTL_IDNA_VARIANT_2003 for IDNA 2003 or
 * INTL_IDNA_VARIANT_UTS46 for UTS #46.
 * @param array $idna_info [optional] This parameter can be used only if
 * INTL_IDNA_VARIANT_UTS46 was used for
 * variant. In that case, it will be filled with an
 * array with the keys 'result', the possibly illegal
 * result of the transformation,
 * 'isTransitionalDifferent', a boolean indicating
 * whether the usage of the transitional mechanisms of UTS #46 either has
 * or would have changed the result and 'errors',
 * which is an int representing a bitset of the error
 * constants IDNA_ERROR_&#42;.
 * @return string The domain name in Unicode, encoded in UTF-8, or false on failure
 */
function idn_to_utf8 (string $domain, int $options = null, int $variant = null, array &$idna_info = null) {}

/**
 * @param mixed $locale
 * @param mixed $bundlename
 * @param mixed $fallback [optional]
 */
function resourcebundle_create ($locale, $bundlename, $fallback = null) {}

/**
 * @param mixed $bundle
 * @param mixed $index
 * @param mixed $fallback [optional]
 */
function resourcebundle_get ($bundle, $index, $fallback = null) {}

/**
 * @param mixed $bundle
 */
function resourcebundle_count ($bundle) {}

/**
 * @param mixed $bundlename
 */
function resourcebundle_locales ($bundlename) {}

/**
 * @param mixed $bundle
 */
function resourcebundle_get_error_code ($bundle) {}

/**
 * @param mixed $bundle
 */
function resourcebundle_get_error_message ($bundle) {}

/**
 * @param mixed $id
 * @param mixed $direction [optional]
 */
function transliterator_create ($id, $direction = null) {}

/**
 * @param mixed $rules
 * @param mixed $direction [optional]
 */
function transliterator_create_from_rules ($rules, $direction = null) {}

function transliterator_list_ids () {}

/**
 * @param Transliterator $orig_trans
 */
function transliterator_create_inverse (Transliterator $orig_trans) {}

/**
 * @param mixed $trans
 * @param mixed $subject
 * @param mixed $start [optional]
 * @param mixed $end [optional]
 */
function transliterator_transliterate ($trans, $subject, $start = null, $end = null) {}

/**
 * @param Transliterator $trans
 */
function transliterator_get_error_code (Transliterator $trans) {}

/**
 * @param Transliterator $trans
 */
function transliterator_get_error_message (Transliterator $trans) {}

/**
 * @param mixed $zoneId
 */
function intltz_create_time_zone ($zoneId) {}

/**
 * @param DateTimeZone $dateTimeZone
 */
function intltz_from_date_time_zone (DateTimeZone $dateTimeZone) {}

function intltz_create_default () {}

/**
 * @param IntlTimeZone $timeZone
 */
function intltz_get_id (IntlTimeZone $timeZone) {}

function intltz_get_gmt () {}

function intltz_get_unknown () {}

/**
 * @param mixed $countryOrRawOffset [optional]
 */
function intltz_create_enumeration ($countryOrRawOffset = null) {}

/**
 * @param mixed $zoneId
 */
function intltz_count_equivalent_ids ($zoneId) {}

/**
 * @param mixed $zoneType
 * @param mixed $region [optional]
 * @param mixed $rawOffset [optional]
 */
function intltz_create_time_zone_id_enumeration ($zoneType, $region = null, $rawOffset = null) {}

/**
 * @param mixed $zoneId
 * @param mixed $isSystemID [optional]
 */
function intltz_get_canonical_id ($zoneId, &$isSystemID = null) {}

/**
 * @param mixed $zoneId
 */
function intltz_get_region ($zoneId) {}

function intltz_get_tz_data_version () {}

/**
 * @param mixed $zoneId
 * @param mixed $index
 */
function intltz_get_equivalent_id ($zoneId, $index) {}

/**
 * @param IntlTimeZone $timeZone
 */
function intltz_use_daylight_time (IntlTimeZone $timeZone) {}

/**
 * @param IntlTimeZone $timeZone
 * @param mixed $date
 * @param mixed $local
 * @param mixed $rawOffset
 * @param mixed $dstOffset
 */
function intltz_get_offset (IntlTimeZone $timeZone, $date, $local, &$rawOffset, &$dstOffset) {}

/**
 * @param IntlTimeZone $timeZone
 */
function intltz_get_raw_offset (IntlTimeZone $timeZone) {}

/**
 * @param IntlTimeZone $timeZone
 * @param IntlTimeZone $otherTimeZone [optional]
 */
function intltz_has_same_rules (IntlTimeZone $timeZoneIntlTimeZone , $otherTimeZone = null) {}

/**
 * @param IntlTimeZone $timeZone
 * @param mixed $isDaylight [optional]
 * @param mixed $style [optional]
 * @param mixed $locale [optional]
 */
function intltz_get_display_name (IntlTimeZone $timeZone, $isDaylight = null, $style = null, $locale = null) {}

/**
 * @param IntlTimeZone $timeZone
 */
function intltz_get_dst_savings (IntlTimeZone $timeZone) {}

/**
 * @param IntlTimeZone $timeZone
 */
function intltz_to_date_time_zone (IntlTimeZone $timeZone) {}

/**
 * @param IntlTimeZone $timeZone
 */
function intltz_get_error_code (IntlTimeZone $timeZone) {}

/**
 * @param IntlTimeZone $timeZone
 */
function intltz_get_error_message (IntlTimeZone $timeZone) {}

/**
 * @param mixed $timeZone [optional]
 * @param mixed $locale [optional]
 */
function intlcal_create_instance ($timeZone = null, $locale = null) {}

/**
 * @param mixed $key
 * @param mixed $locale
 * @param mixed $commonlyUsed
 */
function intlcal_get_keyword_values_for_locale ($key, $locale, $commonlyUsed) {}

function intlcal_get_now () {}

function intlcal_get_available_locales () {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $field
 */
function intlcal_get (IntlCalendar $calendar, $field) {}

/**
 * @param IntlCalendar $calendar
 */
function intlcal_get_time (IntlCalendar $calendar) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $date
 */
function intlcal_set_time (IntlCalendar $calendar, $date) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $field
 * @param mixed $amount
 */
function intlcal_add (IntlCalendar $calendar, $field, $amount) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $timeZone
 */
function intlcal_set_time_zone (IntlCalendar $calendar, $timeZone) {}

/**
 * @param IntlCalendar $calendar
 * @param IntlCalendar $otherCalendar
 */
function intlcal_after (IntlCalendar $calendarIntlCalendar , $otherCalendar) {}

/**
 * @param IntlCalendar $calendar
 * @param IntlCalendar $otherCalendar
 */
function intlcal_before (IntlCalendar $calendarIntlCalendar , $otherCalendar) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $fieldOrYear
 * @param mixed $valueOrMonth
 * @param mixed $dayOfMonth [optional]
 * @param mixed $hour [optional]
 * @param mixed $minute [optional]
 * @param mixed $second [optional]
 */
function intlcal_set (IntlCalendar $calendar, $fieldOrYear, $valueOrMonth, $dayOfMonth = null, $hour = null, $minute = null, $second = null) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $field
 * @param mixed $amountOrUpOrDown [optional]
 */
function intlcal_roll (IntlCalendar $calendar, $field, $amountOrUpOrDown = null) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $field [optional]
 */
function intlcal_clear (IntlCalendar $calendar, $field = null) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $when
 * @param mixed $field
 */
function intlcal_field_difference (IntlCalendar $calendar, $when, $field) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $field
 */
function intlcal_get_actual_maximum (IntlCalendar $calendar, $field) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $field
 */
function intlcal_get_actual_minimum (IntlCalendar $calendar, $field) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $dayOfWeek
 */
function intlcal_get_day_of_week_type (IntlCalendar $calendar, $dayOfWeek) {}

/**
 * @param IntlCalendar $calendar
 */
function intlcal_get_first_day_of_week (IntlCalendar $calendar) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $field
 */
function intlcal_get_greatest_minimum (IntlCalendar $calendar, $field) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $field
 */
function intlcal_get_least_maximum (IntlCalendar $calendar, $field) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $localeType
 */
function intlcal_get_locale (IntlCalendar $calendar, $localeType) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $field
 */
function intlcal_get_maximum (IntlCalendar $calendar, $field) {}

/**
 * @param IntlCalendar $calendar
 */
function intlcal_get_minimal_days_in_first_week (IntlCalendar $calendar) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $field
 */
function intlcal_get_minimum (IntlCalendar $calendar, $field) {}

/**
 * @param IntlCalendar $calendar
 */
function intlcal_get_time_zone (IntlCalendar $calendar) {}

/**
 * @param IntlCalendar $calendar
 */
function intlcal_get_type (IntlCalendar $calendar) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $dayOfWeek
 */
function intlcal_get_weekend_transition (IntlCalendar $calendar, $dayOfWeek) {}

/**
 * @param IntlCalendar $calendar
 */
function intlcal_in_daylight_time (IntlCalendar $calendar) {}

/**
 * @param IntlCalendar $calendar
 * @param IntlCalendar $otherCalendar
 */
function intlcal_is_equivalent_to (IntlCalendar $calendarIntlCalendar , $otherCalendar) {}

/**
 * @param IntlCalendar $calendar
 */
function intlcal_is_lenient (IntlCalendar $calendar) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $field
 */
function intlcal_is_set (IntlCalendar $calendar, $field) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $date [optional]
 */
function intlcal_is_weekend (IntlCalendar $calendar, $date = null) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $dayOfWeek
 */
function intlcal_set_first_day_of_week (IntlCalendar $calendar, $dayOfWeek) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $isLenient
 */
function intlcal_set_lenient (IntlCalendar $calendar, $isLenient) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $numberOfDays
 */
function intlcal_set_minimal_days_in_first_week (IntlCalendar $calendar, $numberOfDays) {}

/**
 * @param IntlCalendar $calendar
 * @param IntlCalendar $otherCalendar
 */
function intlcal_equals (IntlCalendar $calendarIntlCalendar , $otherCalendar) {}

/**
 * @param mixed $dateTime
 */
function intlcal_from_date_time ($dateTime) {}

/**
 * @param IntlCalendar $calendar
 */
function intlcal_to_date_time (IntlCalendar $calendar) {}

/**
 * @param IntlCalendar $calendar
 */
function intlcal_get_repeated_wall_time_option (IntlCalendar $calendar) {}

/**
 * @param IntlCalendar $calendar
 */
function intlcal_get_skipped_wall_time_option (IntlCalendar $calendar) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $wallTimeOption
 */
function intlcal_set_repeated_wall_time_option (IntlCalendar $calendar, $wallTimeOption) {}

/**
 * @param IntlCalendar $calendar
 * @param mixed $wallTimeOption
 */
function intlcal_set_skipped_wall_time_option (IntlCalendar $calendar, $wallTimeOption) {}

/**
 * @param IntlCalendar $calendar
 */
function intlcal_get_error_code (IntlCalendar $calendar) {}

/**
 * @param IntlCalendar $calendar
 */
function intlcal_get_error_message (IntlCalendar $calendar) {}

/**
 * @param mixed $timeZoneOrYear [optional]
 * @param mixed $localeOrMonth [optional]
 * @param mixed $dayOfMonth [optional]
 * @param mixed $hour [optional]
 * @param mixed $minute [optional]
 * @param mixed $second [optional]
 */
function intlgregcal_create_instance ($timeZoneOrYear = null, $localeOrMonth = null, $dayOfMonth = null, $hour = null, $minute = null, $second = null) {}

/**
 * @param IntlGregorianCalendar $calendar
 * @param mixed $date
 */
function intlgregcal_set_gregorian_change (IntlGregorianCalendar $calendar, $date) {}

/**
 * @param IntlGregorianCalendar $calendar
 */
function intlgregcal_get_gregorian_change (IntlGregorianCalendar $calendar) {}

/**
 * @param IntlGregorianCalendar $calendar
 * @param mixed $year
 */
function intlgregcal_is_leap_year (IntlGregorianCalendar $calendar, $year) {}

/**
 * Get the last error code
 * @link http://www.php.net/manual/en/function.intl-get-error-code.php
 * @return int Error code returned by the last API function call.
 */
function intl_get_error_code () {}

/**
 * Get description of the last error
 * @link http://www.php.net/manual/en/function.intl-get-error-message.php
 * @return string Description of an error occurred in the last API function call.
 */
function intl_get_error_message () {}

/**
 * Check whether the given error code indicates failure
 * @link http://www.php.net/manual/en/function.intl-is-failure.php
 * @param int $error_code is a value that returned by functions:
 * intl_get_error_code,
 * collator_get_error_code .
 * @return bool true if it the code indicates some failure, and false
 * in case of success or a warning.
 */
function intl_is_failure (int $error_code) {}

/**
 * Get symbolic name for a given error code
 * @link http://www.php.net/manual/en/function.intl-error-name.php
 * @param int $error_code ICU error code.
 * @return string The returned string will be the same as the name of the error code
 * constant.
 */
function intl_error_name (int $error_code) {}


/**
 * Limit on locale length, set to 80 in PHP code. Locale names longer 
 * than this limit will not be accepted.
 * @link http://www.php.net/manual/en/intl.constants.php
 */
define ('INTL_MAX_LOCALE_LEN', 156);
define ('INTL_ICU_VERSION', 63.1);
define ('INTL_ICU_DATA_VERSION', 63.1);
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

/**
 * Prohibit processing of unassigned codepoints in the input for IDN
 * functions and do not check if the input conforms to domain name ASCII rules.
 * @link http://www.php.net/manual/en/intl.constants.php
 */
define ('IDNA_DEFAULT', 0);

/**
 * Allow processing of unassigned codepoints in the input for IDN functions.
 * @link http://www.php.net/manual/en/intl.constants.php
 */
define ('IDNA_ALLOW_UNASSIGNED', 1);

/**
 * Check if the input for IDN functions conforms to domain name ASCII rules.
 * @link http://www.php.net/manual/en/intl.constants.php
 */
define ('IDNA_USE_STD3_RULES', 2);

/**
 * Check whether the input conforms to the BiDi rules.
 * Ignored by the IDNA2003 implementation, which always performs this check.
 * @link http://www.php.net/manual/en/intl.constants.php
 */
define ('IDNA_CHECK_BIDI', 4);

/**
 * Check whether the input conforms to the CONTEXTJ rules.
 * Ignored by the IDNA2003 implementation, as this check is new in IDNA2008.
 * @link http://www.php.net/manual/en/intl.constants.php
 */
define ('IDNA_CHECK_CONTEXTJ', 8);

/**
 * Option for nontransitional processing in
 * idn_to_ascii. Transitional processing is activated
 * by default. This option is ignored by the IDNA2003 implementation.
 * @link http://www.php.net/manual/en/intl.constants.php
 */
define ('IDNA_NONTRANSITIONAL_TO_ASCII', 16);

/**
 * Option for nontransitional processing in
 * idn_to_utf8. Transitional processing is activated
 * by default. This option is ignored by the IDNA2003 implementation.
 * @link http://www.php.net/manual/en/intl.constants.php
 */
define ('IDNA_NONTRANSITIONAL_TO_UNICODE', 32);

/**
 * Use IDNA 2003 algorithm in idn_to_utf8 and
 * idn_to_ascii. This is the default.
 * This constant and using the default has been deprecated as of PHP 7.2.0.
 * @link http://www.php.net/manual/en/intl.constants.php
 */
define ('INTL_IDNA_VARIANT_2003', 0);

/**
 * Use UTS #46 algorithm in idn_to_utf8 and
 * idn_to_ascii.
 * Available as of ICU 4.6.
 * @link http://www.php.net/manual/en/intl.constants.php
 */
define ('INTL_IDNA_VARIANT_UTS46', 1);

/**
 * Errors reported in a bitset returned by the UTS #46 algorithm in
 * idn_to_utf8 and
 * idn_to_ascii.
 * @link http://www.php.net/manual/en/intl.constants.php
 */
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

// End of intl v.7.3.0
