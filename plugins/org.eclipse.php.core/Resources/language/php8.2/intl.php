<?php

// Start of intl v.8.2.6

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
	/**
	 * Sort strings with different accents from the back of the string. This
	 * attribute is automatically set to
	 * On
	 * for the French locales and a few others. Users normally would not need
	 * to explicitly set this attribute. There is a string comparison
	 * performance cost when it is set On,
	 * but sort key length is unaffected. Possible values are:
	 * <p>
	 * Collator::ON
	 * Collator::OFF(default)
	 * Collator::DEFAULT_VALUE
	 * </p>
	 * <p>FRENCH_COLLATION rules
	 * <p>
	 * <p>
	 * F=OFF cote &lt; coté &lt; côte &lt; côté 
	 * F=ON cote &lt; côte &lt; coté &lt; côté
	 * </p>
	 * </p></p>
	 * <p>F=OFF cote &lt; coté &lt; côte &lt; côté 
	 * F=ON cote &lt; côte &lt; coté &lt; côté</p>
	const FRENCH_COLLATION = 0;
	/**
	 * The Alternate attribute is used to control the handling of the so called
	 * variable characters in the UCA: whitespace, punctuation and symbols. If
	 * Alternate is set to NonIgnorable
	 * (N), then differences among these characters are of the same importance
	 * as differences among letters. If Alternate is set to
	 * Shifted
	 * (S), then these characters are of only minor importance. The
	 * Shifted value is often used in combination with
	 * Strength
	 * set to Quaternary. In such a case, whitespace, punctuation, and symbols
	 * are considered when comparing strings, but only if all other aspects of
	 * the strings (base letters, accents, and case) are identical. If
	 * Alternate is not set to Shifted, then there is no difference between a
	 * Strength of 3 and a Strength of 4. For more information and examples,
	 * see Variable_Weighting in the
	 * UCA.
	 * The reason the Alternate values are not simply
	 * On and Off
	 * is that additional Alternate values may be added in the future. The UCA
	 * option Blanked is expressed with Strength set to 3, and Alternate set to
	 * Shifted. The default for most locales is NonIgnorable. If Shifted is
	 * selected, it may be slower if there are many strings that are the same
	 * except for punctuation; sort key length will not be affected unless the
	 * strength level is also increased.
	 * <p>Possible values are:
	 * <p>
	 * Collator::NON_IGNORABLE(default)
	 * Collator::SHIFTED
	 * Collator::DEFAULT_VALUE
	 * </p></p>
	 * <p>ALTERNATE_HANDLING rules
	 * <p>
	 * <p>
	 * S=3, A=N di Silva &lt; Di Silva &lt; diSilva &lt; U.S.A. &lt; USA
	 * S=3, A=S di Silva = diSilva &lt; Di Silva &lt; U.S.A. = USA
	 * S=4, A=S di Silva &lt; diSilva &lt; Di Silva &lt; U.S.A. &lt; USA
	 * </p>
	 * </p></p>
	 * <p>S=3, A=N di Silva &lt; Di Silva &lt; diSilva &lt; U.S.A. &lt; USA
	 * S=3, A=S di Silva = diSilva &lt; Di Silva &lt; U.S.A. = USA
	 * S=4, A=S di Silva &lt; diSilva &lt; Di Silva &lt; U.S.A. &lt; USA</p>
	const ALTERNATE_HANDLING = 1;
	/**
	 * The Case_First attribute is used to control whether uppercase letters
	 * come before lowercase letters or vice versa, in the absence of other
	 * differences in the strings. The possible values are
	 * Uppercase_First
	 * (U) and Lowercase_First
	 * (L), plus the standard Default
	 * and Off.
	 * There is almost no difference between the Off and Lowercase_First
	 * options in terms of results, so typically users will not use
	 * Lowercase_First: only Off or Uppercase_First. (People interested in the
	 * detailed differences between X and L should consult the Collation
	 * Customization). Specifying either L or U won't affect string comparison
	 * performance, but will affect the sort key length.
	 * <p>Possible values are:
	 * <p>
	 * Collator::OFF(default)
	 * Collator::LOWER_FIRST
	 * Collator::UPPER_FIRST
	 * Collator:DEFAULT
	 * </p></p>
	 * <p>CASE_FIRST rules
	 * <p>
	 * <p>
	 * C=X or C=L "china" &lt; "China" &lt; "denmark" &lt; "Denmark" 
	 * C=U "China" &lt; "china" &lt; "Denmark" &lt; "denmark"
	 * </p>
	 * </p></p>
	 * <p>C=X or C=L "china" &lt; "China" &lt; "denmark" &lt; "Denmark" 
	 * C=U "China" &lt; "china" &lt; "Denmark" &lt; "denmark"</p>
	const CASE_FIRST = 2;
	/**
	 * The Case_Level attribute is used when ignoring accents but not case. In
	 * such a situation, set Strength to be Primary,
	 * and Case_Level to be On.
	 * In most locales, this setting is Off by default. There is a small
	 * string comparison performance and sort key impact if this attribute is
	 * set to be On.
	 * <p>Possible values are:
	 * <p>
	 * Collator::OFF(default)
	 * Collator::ON
	 * Collator::DEFAULT_VALUE
	 * </p></p>
	 * <p>CASE_LEVEL rules
	 * <p>
	 * <p>
	 * S=1, E=X role = Role = rôle 
	 * S=1, E=O role = rôle &lt; Role
	 * </p>
	 * </p></p>
	 * <p>S=1, E=X role = Role = rôle 
	 * S=1, E=O role = rôle &lt; Role</p>
	const CASE_LEVEL = 3;
	/**
	 * The Normalization setting determines whether text is thoroughly
	 * normalized or not in comparison. Even if the setting is off (which is
	 * the default for many locales), text as represented in common usage will
	 * compare correctly (for details, see UTN #5). Only if the accent marks
	 * are in noncanonical order will there be a problem. If the setting is
	 * On,
	 * then the best results are guaranteed for all possible text input.
	 * There is a medium string comparison performance cost if this attribute
	 * is On,
	 * depending on the frequency of sequences that require normalization.
	 * There is no significant effect on sort key length. If the input text is
	 * known to be in NFD or NFKD normalization forms, there is no need to
	 * enable this Normalization option.
	 * <p>Possible values are:
	 * <p>
	 * Collator::OFF(default)
	 * Collator::ON
	 * Collator::DEFAULT_VALUE
	 * </p></p>
	const NORMALIZATION_MODE = 4;
	/**
	 * The ICU Collation Service supports many levels of comparison (named
	 * "Levels", but also known as "Strengths"). Having these categories
	 * enables ICU to sort strings precisely according to local conventions.
	 * However, by allowing the levels to be selectively employed, searching
	 * for a string in text can be performed with various matching conditions.
	 * For more detailed information, see
	 * collator_set_strength chapter.
	 * <p>Possible values are:
	 * <p>
	 * Collator::PRIMARY
	 * Collator::SECONDARY
	 * Collator::TERTIARY(default)
	 * Collator::QUATERNARY
	 * Collator::IDENTICAL
	 * Collator::DEFAULT_VALUE
	 * </p></p>
	const STRENGTH = 5;
	/**
	 * Compatibility with JIS x 4061 requires the introduction of an additional
	 * level to distinguish Hiragana and Katakana characters. If compatibility
	 * with that standard is required, then this attribute should be set
	 * On,
	 * and the strength set to Quaternary. This will affect sort key length
	 * and string comparison string comparison performance.
	 * <p>Possible values are:
	 * <p>
	 * Collator::OFF(default)
	 * Collator::ON
	 * Collator::DEFAULT_VALUE
	 * </p></p>
	const HIRAGANA_QUATERNARY_MODE = 6;
	/**
	 * When turned on, this attribute generates a collation key for the numeric
	 * value of substrings of digits. This is a way to get '100' to sort AFTER
	 * '2'.
	 * <p>Possible values are:
	 * <p>
	 * Collator::OFF(default)
	 * Collator::ON
	 * Collator::DEFAULT_VALUE
	 * </p></p>
	const NUMERIC_COLLATION = 7;
	const SORT_REGULAR = 0;
	const SORT_STRING = 1;
	const SORT_NUMERIC = 2;


	/**
	 * Create a collator
	 * @link http://www.php.net/manual/en/collator.construct.php
	 * @param string $locale 
	 * @return string 
	 */
	public function __construct (string $locale): string {}

	/**
	 * Create a collator
	 * @link http://www.php.net/manual/en/collator.create.php
	 * @param string $locale 
	 * @return Collator|null Return new instance of Collator object, or null
	 * on error.
	 */
	public static function create (string $locale): ?Collator {}

	/**
	 * Compare two Unicode strings
	 * @link http://www.php.net/manual/en/collator.compare.php
	 * @param string $string1 
	 * @param string $string2 
	 * @return int|false Return comparison result:
	 * <p><p>
	 * <br>
	 * <p>
	 * 1 if string1 is greater than 
	 * string2 ;
	 * </p>
	 * <br>
	 * <p>
	 * 0 if string1 is equal to 
	 * string2;
	 * </p>
	 * <br>
	 * <p>
	 * -1 if string1 is less than 
	 * string2 .
	 * </p>
	 * </p>
	 * Returns false on failure.</p>
	 * <p>1 if string1 is greater than 
	 * string2 ;</p>
	 * <p>0 if string1 is equal to 
	 * string2;</p>
	 * <p>-1 if string1 is less than 
	 * string2 .</p>
	 */
	public function compare (string $string1, string $string2): int|false {}

	/**
	 * Sort array using specified collator
	 * @link http://www.php.net/manual/en/collator.sort.php
	 * @param array $array 
	 * @param int $flags [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function sort (array &$array, int $flags = \Collator::SORT_REGULAR): bool {}

	/**
	 * Sort array using specified collator and sort keys
	 * @link http://www.php.net/manual/en/collator.sortwithsortkeys.php
	 * @param array $array 
	 * @return bool Returns true on success or false on failure.
	 */
	public function sortWithSortKeys (array &$array): bool {}

	/**
	 * Sort array maintaining index association
	 * @link http://www.php.net/manual/en/collator.asort.php
	 * @param array $array 
	 * @param int $flags [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function asort (array &$array, int $flags = \Collator::SORT_REGULAR): bool {}

	/**
	 * Get collation attribute value
	 * @link http://www.php.net/manual/en/collator.getattribute.php
	 * @param int $attribute 
	 * @return int|false Attribute value, or false on failure.
	 */
	public function getAttribute (int $attribute): int|false {}

	/**
	 * Set collation attribute
	 * @link http://www.php.net/manual/en/collator.setattribute.php
	 * @param int $attribute 
	 * @param int $value 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setAttribute (int $attribute, int $value): bool {}

	/**
	 * Get current collation strength
	 * @link http://www.php.net/manual/en/collator.getstrength.php
	 * @return int Returns current collation strength, or false on failure.
	 */
	public function getStrength (): int {}

	/**
	 * Set collation strength
	 * @link http://www.php.net/manual/en/collator.setstrength.php
	 * @param int $strength 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setStrength (int $strength): bool {}

	/**
	 * Get the locale name of the collator
	 * @link http://www.php.net/manual/en/collator.getlocale.php
	 * @param int $type 
	 * @return string|false Real locale name from which the collation data comes. If the collator was
	 * instantiated from rules or an error occurred, returns false.
	 */
	public function getLocale (int $type): string|false {}

	/**
	 * Get collator's last error code
	 * @link http://www.php.net/manual/en/collator.geterrorcode.php
	 * @return int|false Error code returned by the last Collator API function call,
	 * or false on failure.
	 */
	public function getErrorCode (): int|false {}

	/**
	 * Get text for collator's last error code
	 * @link http://www.php.net/manual/en/collator.geterrormessage.php
	 * @return string|false Description of an error occurred in the last Collator API function call,
	 * or false on failure.
	 */
	public function getErrorMessage (): string|false {}

	/**
	 * Get sorting key for a string
	 * @link http://www.php.net/manual/en/collator.getsortkey.php
	 * @param string $string 
	 * @return string|false Returns the collation key for the string, or false on failure.
	 */
	public function getSortKey (string $string): string|false {}

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
	/**
	 * Decimal format defined by pattern
	const PATTERN_DECIMAL = 0;
	/**
	 * Decimal format
	const DECIMAL = 1;
	/**
	 * Currency format
	const CURRENCY = 2;
	/**
	 * Percent format
	const PERCENT = 3;
	/**
	 * Scientific format
	const SCIENTIFIC = 4;
	/**
	 * Spellout rule-based format
	const SPELLOUT = 5;
	/**
	 * Ordinal rule-based format
	const ORDINAL = 6;
	/**
	 * Duration rule-based format
	const DURATION = 7;
	/**
	 * Rule-based format defined by pattern
	const PATTERN_RULEBASED = 9;
	/**
	 * Alias for PATTERN_DECIMAL
	const IGNORE = 0;
	/**
	 * Currency format for accounting, e.g., ($3.00) for negative currency amount
	 * instead of -$3.00. Available as of PHP 7.4.1 and ICU 53.
	const CURRENCY_ACCOUNTING = 12;
	/**
	 * Default format for the locale
	const DEFAULT_STYLE = 1;
	/**
	 * Rounding mode to round towards positive infinity.
	const ROUND_CEILING = 0;
	/**
	 * Rounding mode to round towards negative infinity.
	const ROUND_FLOOR = 1;
	/**
	 * Rounding mode to round towards zero.
	const ROUND_DOWN = 2;
	/**
	 * Rounding mode to round away from zero.
	const ROUND_UP = 3;
	/**
	 * Rounding mode to round towards the "nearest neighbor" unless both
	 * neighbors are equidistant, in which case, round towards the even
	 * neighbor.
	const ROUND_HALFEVEN = 4;
	/**
	 * Rounding mode to round towards "nearest neighbor" unless both neighbors
	 * are equidistant, in which case round down.
	const ROUND_HALFDOWN = 5;
	/**
	 * Rounding mode to round towards "nearest neighbor" unless both neighbors
	 * are equidistant, in which case round up.
	const ROUND_HALFUP = 6;
	/**
	 * Pad characters inserted before the prefix.
	const PAD_BEFORE_PREFIX = 0;
	/**
	 * Pad characters inserted after the prefix.
	const PAD_AFTER_PREFIX = 1;
	/**
	 * Pad characters inserted before the suffix.
	const PAD_BEFORE_SUFFIX = 2;
	/**
	 * Pad characters inserted after the suffix.
	const PAD_AFTER_SUFFIX = 3;
	/**
	 * Parse integers only.
	const PARSE_INT_ONLY = 0;
	/**
	 * Use grouping separator.
	const GROUPING_USED = 1;
	/**
	 * Always show decimal point.
	const DECIMAL_ALWAYS_SHOWN = 2;
	/**
	 * Maximum integer digits.
	const MAX_INTEGER_DIGITS = 3;
	/**
	 * Minimum integer digits.
	const MIN_INTEGER_DIGITS = 4;
	/**
	 * Integer digits.
	const INTEGER_DIGITS = 5;
	/**
	 * Maximum fraction digits.
	const MAX_FRACTION_DIGITS = 6;
	/**
	 * Minimum fraction digits.
	const MIN_FRACTION_DIGITS = 7;
	/**
	 * Fraction digits.
	const FRACTION_DIGITS = 8;
	/**
	 * Multiplier.
	const MULTIPLIER = 9;
	/**
	 * Grouping size.
	const GROUPING_SIZE = 10;
	/**
	 * Rounding Mode.
	const ROUNDING_MODE = 11;
	/**
	 * Rounding increment.
	const ROUNDING_INCREMENT = 12;
	/**
	 * The width to which the output of format() is padded.
	const FORMAT_WIDTH = 13;
	/**
	 * The position at which padding will take place. See pad position
	 * constants for possible argument values.
	const PADDING_POSITION = 14;
	/**
	 * Secondary grouping size.
	const SECONDARY_GROUPING_SIZE = 15;
	/**
	 * Use significant digits.
	const SIGNIFICANT_DIGITS_USED = 16;
	/**
	 * Minimum significant digits.
	const MIN_SIGNIFICANT_DIGITS = 17;
	/**
	 * Maximum significant digits.
	const MAX_SIGNIFICANT_DIGITS = 18;
	/**
	 * Lenient parse mode used by rule-based formats.
	const LENIENT_PARSE = 19;
	/**
	 * Positive prefix.
	const POSITIVE_PREFIX = 0;
	/**
	 * Positive suffix.
	const POSITIVE_SUFFIX = 1;
	/**
	 * Negative prefix.
	const NEGATIVE_PREFIX = 2;
	/**
	 * Negative suffix.
	const NEGATIVE_SUFFIX = 3;
	/**
	 * The character used to pad to the format width.
	const PADDING_CHARACTER = 4;
	/**
	 * The ISO currency code.
	const CURRENCY_CODE = 5;
	/**
	 * The default rule set. This is only available with rule-based
	 * formatters.
	const DEFAULT_RULESET = 6;
	/**
	 * The public rule sets. This is only available with rule-based
	 * formatters. This is a read-only attribute. The public rulesets are
	 * returned as a single string, with each ruleset name delimited by ';'
	 * (semicolon).
	const PUBLIC_RULESETS = 7;
	/**
	 * The decimal separator.
	const DECIMAL_SEPARATOR_SYMBOL = 0;
	/**
	 * The grouping separator.
	const GROUPING_SEPARATOR_SYMBOL = 1;
	/**
	 * The pattern separator.
	const PATTERN_SEPARATOR_SYMBOL = 2;
	/**
	 * The percent sign.
	const PERCENT_SYMBOL = 3;
	/**
	 * Zero.
	const ZERO_DIGIT_SYMBOL = 4;
	/**
	 * Character representing a digit in the pattern.
	const DIGIT_SYMBOL = 5;
	/**
	 * The minus sign.
	const MINUS_SIGN_SYMBOL = 6;
	/**
	 * The plus sign.
	const PLUS_SIGN_SYMBOL = 7;
	/**
	 * The currency symbol.
	const CURRENCY_SYMBOL = 8;
	/**
	 * The international currency symbol.
	const INTL_CURRENCY_SYMBOL = 9;
	/**
	 * The monetary separator.
	const MONETARY_SEPARATOR_SYMBOL = 10;
	/**
	 * The exponential symbol.
	const EXPONENTIAL_SYMBOL = 11;
	/**
	 * Per mill symbol.
	const PERMILL_SYMBOL = 12;
	/**
	 * Escape padding character.
	const PAD_ESCAPE_SYMBOL = 13;
	/**
	 * Infinity symbol.
	const INFINITY_SYMBOL = 14;
	/**
	 * Not-a-number symbol.
	const NAN_SYMBOL = 15;
	/**
	 * Significant digit symbol.
	const SIGNIFICANT_DIGIT_SYMBOL = 16;
	/**
	 * The monetary grouping separator.
	const MONETARY_GROUPING_SEPARATOR_SYMBOL = 17;
	/**
	 * Derive the type from variable type
	const TYPE_DEFAULT = 0;
	/**
	 * Format/parse as 32-bit integer
	const TYPE_INT32 = 1;
	/**
	 * Format/parse as 64-bit integer
	const TYPE_INT64 = 2;
	/**
	 * Format/parse as floating point value
	const TYPE_DOUBLE = 3;
	/**
	 * Format/parse as currency value
	const TYPE_CURRENCY = 4;


	/**
	 * Create a number formatter
	 * @link http://www.php.net/manual/en/numberformatter.create.php
	 * @param string $locale 
	 * @param int $style 
	 * @param string|null $pattern [optional] 
	 * @return NumberFormatter|null Returns NumberFormatter object or null on error.
	 */
	public function __construct (string $locale, int $style, ?string $pattern = null): ?NumberFormatter {}

	/**
	 * Create a number formatter
	 * @link http://www.php.net/manual/en/numberformatter.create.php
	 * @param string $locale 
	 * @param int $style 
	 * @param string|null $pattern [optional] 
	 * @return NumberFormatter|null Returns NumberFormatter object or null on error.
	 */
	public static function create (string $locale, int $style, ?string $pattern = null): ?NumberFormatter {}

	/**
	 * Format a number
	 * @link http://www.php.net/manual/en/numberformatter.format.php
	 * @param int|float $num 
	 * @param int $type [optional] 
	 * @return string|false Returns the string containing formatted value, or false on error.
	 */
	public function format (int|float $num, int $type = \NumberFormatter::TYPE_DEFAULT): string|false {}

	/**
	 * Parse a number
	 * @link http://www.php.net/manual/en/numberformatter.parse.php
	 * @param string $string 
	 * @param int $type [optional] 
	 * @param int $offset [optional] 
	 * @return int|float|false The value of the parsed number or false on error.
	 */
	public function parse (string $string, int $type = \NumberFormatter::TYPE_DOUBLE, int &$offset = null): int|float|false {}

	/**
	 * Format a currency value
	 * @link http://www.php.net/manual/en/numberformatter.formatcurrency.php
	 * @param float $amount 
	 * @param string $currency 
	 * @return string|false String representing the formatted currency value, or false on failure.
	 */
	public function formatCurrency (float $amount, string $currency): string|false {}

	/**
	 * Parse a currency number
	 * @link http://www.php.net/manual/en/numberformatter.parsecurrency.php
	 * @param string $string 
	 * @param string $currency 
	 * @param int $offset [optional] 
	 * @return float|false The parsed numeric value or false on error.
	 */
	public function parseCurrency (string $string, string &$currency, int &$offset = null): float|false {}

	/**
	 * Set an attribute
	 * @link http://www.php.net/manual/en/numberformatter.setattribute.php
	 * @param int $attribute 
	 * @param int|float $value 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setAttribute (int $attribute, int|float $value): bool {}

	/**
	 * Get an attribute
	 * @link http://www.php.net/manual/en/numberformatter.getattribute.php
	 * @param int $attribute 
	 * @return int|float|false Return attribute value on success, or false on error.
	 */
	public function getAttribute (int $attribute): int|float|false {}

	/**
	 * Set a text attribute
	 * @link http://www.php.net/manual/en/numberformatter.settextattribute.php
	 * @param int $attribute 
	 * @param string $value 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setTextAttribute (int $attribute, string $value): bool {}

	/**
	 * Get a text attribute
	 * @link http://www.php.net/manual/en/numberformatter.gettextattribute.php
	 * @param int $attribute 
	 * @return string|false Return attribute value on success, or false on error.
	 */
	public function getTextAttribute (int $attribute): string|false {}

	/**
	 * Set a symbol value
	 * @link http://www.php.net/manual/en/numberformatter.setsymbol.php
	 * @param int $symbol 
	 * @param string $value 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setSymbol (int $symbol, string $value): bool {}

	/**
	 * Get a symbol value
	 * @link http://www.php.net/manual/en/numberformatter.getsymbol.php
	 * @param int $symbol 
	 * @return string|false The symbol string or false on error.
	 */
	public function getSymbol (int $symbol): string|false {}

	/**
	 * Set formatter pattern
	 * @link http://www.php.net/manual/en/numberformatter.setpattern.php
	 * @param string $pattern 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setPattern (string $pattern): bool {}

	/**
	 * Get formatter pattern
	 * @link http://www.php.net/manual/en/numberformatter.getpattern.php
	 * @return string|false Pattern string that is used by the formatter, or false if an error happens.
	 */
	public function getPattern (): string|false {}

	/**
	 * Get formatter locale
	 * @link http://www.php.net/manual/en/numberformatter.getlocale.php
	 * @param int $type [optional] 
	 * @return string|false The locale name used to create the formatter, or false on failure.
	 */
	public function getLocale (int $type = ULOC_ACTUAL_LOCALE): string|false {}

	/**
	 * Get formatter's last error code
	 * @link http://www.php.net/manual/en/numberformatter.geterrorcode.php
	 * @return int Returns error code from last formatter call.
	 */
	public function getErrorCode (): int {}

	/**
	 * Get formatter's last error message
	 * @link http://www.php.net/manual/en/numberformatter.geterrormessage.php
	 * @return string Returns error message from last formatter call.
	 */
	public function getErrorMessage (): string {}

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
	/**
	 * Normalization Form D (NFD) - Canonical Decomposition
	const FORM_D = 4;
	const NFD = 4;
	/**
	 * Normalization Form KD (NFKD) - Compatibility Decomposition
	const FORM_KD = 8;
	const NFKD = 8;
	/**
	 * Normalization Form C (NFC) - Canonical Decomposition followed by
	 * Canonical Composition
	const FORM_C = 16;
	const NFC = 16;
	/**
	 * Normalization Form KC (NFKC) - Compatibility Decomposition, followed by
	 * Canonical Composition
	const FORM_KC = 32;
	const NFKC = 32;
	const FORM_KC_CF = 48;
	const NFKC_CF = 48;


	/**
	 * Normalizes the input provided and returns the normalized string
	 * @link http://www.php.net/manual/en/normalizer.normalize.php
	 * @param string $string 
	 * @param int $form [optional] 
	 * @return string|false The normalized string or false if an error occurred.
	 */
	public static function normalize (string $string, int $form = \Normalizer::FORM_C): string|false {}

	/**
	 * Checks if the provided string is already in the specified normalization
	 * form
	 * @link http://www.php.net/manual/en/normalizer.isnormalized.php
	 * @param string $string 
	 * @param int $form [optional] 
	 * @return bool true if normalized, false otherwise or if there an error
	 */
	public static function isNormalized (string $string, int $form = \Normalizer::FORM_C): bool {}

	/**
	 * Gets the Decomposition_Mapping property for the given UTF-8 encoded code point
	 * @link http://www.php.net/manual/en/normalizer.getrawdecomposition.php
	 * @param string $string The input string, which should be a single, UTF-8 encoded, code point.
	 * @param int $form [optional] 
	 * @return string|null Returns a string containing the Decomposition_Mapping property, if present in the UCD.
	 * <p>Returns null if there is no Decomposition_Mapping property for the character.</p>
	 */
	public static function getRawDecomposition (string $string, int $form = \Normalizer::FORM_C): ?string {}

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
	/**
	 * This is locale the data actually comes from.
	const ACTUAL_LOCALE = 0;
	/**
	 * This is the most specific locale supported by ICU.
	const VALID_LOCALE = 1;
	/**
	 * Used as locale parameter with the methods of the various locale affected classes,
	 * such as NumberFormatter. This constant would make the methods to use default
	 * locale.
	const DEFAULT_LOCALE = null;
	/**
	 * Language subtag
	const LANG_TAG = "language";
	/**
	 * Extended language subtag
	const EXTLANG_TAG = "extlang";
	/**
	 * Script subtag
	const SCRIPT_TAG = "script";
	/**
	 * Region subtag
	const REGION_TAG = "region";
	/**
	 * Variant subtag
	const VARIANT_TAG = "variant";
	/**
	 * Grandfathered Language subtag
	const GRANDFATHERED_LANG_TAG = "grandfathered";
	/**
	 * Private subtag
	const PRIVATE_TAG = "private";


	/**
	 * Gets the default locale value from the INTL global 'default_locale'
	 * @link http://www.php.net/manual/en/locale.getdefault.php
	 * @return string The current runtime locale
	 */
	public static function getDefault (): string {}

	/**
	 * Sets the default runtime locale
	 * @link http://www.php.net/manual/en/locale.setdefault.php
	 * @param string $locale 
	 * @return bool Returns true.
	 */
	public static function setDefault (string $locale): bool {}

	/**
	 * Gets the primary language for the input locale
	 * @link http://www.php.net/manual/en/locale.getprimarylanguage.php
	 * @param string $locale 
	 * @return string|null The language code associated with the language.
	 * <p>>Returns null when the length of locale exceeds INTL_MAX_LOCALE_LEN.</p>
	 */
	public static function getPrimaryLanguage (string $locale): ?string {}

	/**
	 * Gets the script for the input locale
	 * @link http://www.php.net/manual/en/locale.getscript.php
	 * @param string $locale 
	 * @return string|null The script subtag for the locale or null if not present
	 */
	public static function getScript (string $locale): ?string {}

	/**
	 * Gets the region for the input locale
	 * @link http://www.php.net/manual/en/locale.getregion.php
	 * @param string $locale 
	 * @return string|null The region subtag for the locale or null if not present
	 * <p>>Returns null when the length of locale exceeds INTL_MAX_LOCALE_LEN.</p>
	 */
	public static function getRegion (string $locale): ?string {}

	/**
	 * Gets the keywords for the input locale
	 * @link http://www.php.net/manual/en/locale.getkeywords.php
	 * @param string $locale 
	 * @return array|false|null Associative array containing the keyword-value pairs for this locale
	 * <p>>Returns null when the length of locale exceeds INTL_MAX_LOCALE_LEN.</p>
	 */
	public static function getKeywords (string $locale): array|false|null {}

	/**
	 * Returns an appropriately localized display name for script of the input locale
	 * @link http://www.php.net/manual/en/locale.getdisplayscript.php
	 * @param string $locale 
	 * @param string|null $displayLocale [optional] 
	 * @return string|false Display name of the script for the locale in the format appropriate for
	 * displayLocale, or false on failure.
	 */
	public static function getDisplayScript (string $locale, ?string $displayLocale = null): string|false {}

	/**
	 * Returns an appropriately localized display name for region of the input locale
	 * @link http://www.php.net/manual/en/locale.getdisplayregion.php
	 * @param string $locale 
	 * @param string|null $displayLocale [optional] 
	 * @return string|false Display name of the region for the locale in the format appropriate for
	 * displayLocale, or false on failure.
	 */
	public static function getDisplayRegion (string $locale, ?string $displayLocale = null): string|false {}

	/**
	 * Returns an appropriately localized display name for the input locale
	 * @link http://www.php.net/manual/en/locale.getdisplayname.php
	 * @param string $locale 
	 * @param string|null $displayLocale [optional] 
	 * @return string|false Display name of the locale in the format appropriate for displayLocale, or false on failure.
	 */
	public static function getDisplayName (string $locale, ?string $displayLocale = null): string|false {}

	/**
	 * Returns an appropriately localized display name for language of the inputlocale
	 * @link http://www.php.net/manual/en/locale.getdisplaylanguage.php
	 * @param string $locale 
	 * @param string|null $displayLocale [optional] 
	 * @return string|false Display name of the language for the locale in the format appropriate for
	 * displayLocale, or false on failure.
	 */
	public static function getDisplayLanguage (string $locale, ?string $displayLocale = null): string|false {}

	/**
	 * Returns an appropriately localized display name for variants of the input locale
	 * @link http://www.php.net/manual/en/locale.getdisplayvariant.php
	 * @param string $locale 
	 * @param string|null $displayLocale [optional] 
	 * @return string|false Display name of the variant for the locale in the format appropriate for
	 * displayLocale, or false on failure.
	 */
	public static function getDisplayVariant (string $locale, ?string $displayLocale = null): string|false {}

	/**
	 * Returns a correctly ordered and delimited locale ID
	 * @link http://www.php.net/manual/en/locale.composelocale.php
	 * @param array $subtags 
	 * @return string|false The corresponding locale identifier, or false when subtags is empty.
	 */
	public static function composeLocale (array $subtags): string|false {}

	/**
	 * Returns a key-value array of locale ID subtag elements
	 * @link http://www.php.net/manual/en/locale.parselocale.php
	 * @param string $locale 
	 * @return array|null Returns an array containing a list of key-value pairs, where the keys
	 * identify the particular locale ID subtags, and the values are the
	 * associated subtag values. The array will be ordered as the locale id
	 * subtags e.g. in the locale id if variants are '-varX-varY-varZ' then the
	 * returned array will have variant0=&gt;varX , variant1=&gt;varY ,
	 * variant2=&gt;varZ
	 * <p>Returns null when the length of locale exceeds
	 * INTL_MAX_LOCALE_LEN.</p>
	 */
	public static function parseLocale (string $locale): ?array {}

	/**
	 * Gets the variants for the input locale
	 * @link http://www.php.net/manual/en/locale.getallvariants.php
	 * @param string $locale 
	 * @return array|null The <p>null if not present
	 * <p>>Returns null when the length of locale exceeds INTL_MAX_LOCALE_LEN.</p>
	 */
	public static function getAllVariants (string $locale): ?array {}

	/**
	 * Checks if a language tag filter matches with locale
	 * @link http://www.php.net/manual/en/locale.filtermatches.php
	 * @param string $languageTag 
	 * @param string $locale 
	 * @param bool $canonicalize [optional] 
	 * @return bool|null true if locale matches languageTag false otherwise.
	 * <p>>Returns null when the length of locale exceeds INTL_MAX_LOCALE_LEN.</p>
	 */
	public static function filterMatches (string $languageTag, string $locale, bool $canonicalize = false): ?bool {}

	/**
	 * Searches the language tag list for the best match to the language
	 * @link http://www.php.net/manual/en/locale.lookup.php
	 * @param array $languageTag 
	 * @param string $locale 
	 * @param bool $canonicalize [optional] 
	 * @param string|null $defaultLocale [optional] 
	 * @return string|null The closest matching language tag or default value.
	 * <p>>Returns null when the length of locale exceeds INTL_MAX_LOCALE_LEN.</p>
	 */
	public static function lookup (array $languageTag, string $locale, bool $canonicalize = false, ?string $defaultLocale = null): ?string {}

	/**
	 * Canonicalize the locale string
	 * @link http://www.php.net/manual/en/locale.canonicalize.php
	 * @param string $locale 
	 * @return string|null Canonicalized locale string.
	 * <p>>Returns null when the length of locale exceeds INTL_MAX_LOCALE_LEN.</p>
	 */
	public static function canonicalize (string $locale): ?string {}

	/**
	 * Tries to find out best available locale based on HTTP "Accept-Language" header
	 * @link http://www.php.net/manual/en/locale.acceptfromhttp.php
	 * @param string $header 
	 * @return string|false The corresponding locale identifier.
	 * <p>Returns false when the length of header exceeds
	 * INTL_MAX_LOCALE_LEN.</p>
	 */
	public static function acceptFromHttp (string $header): string|false {}

}

/**
 * @link http://www.php.net/manual/en/class.messageformatter.php
 */
class MessageFormatter  {

	/**
	 * Constructs a new Message Formatter
	 * @link http://www.php.net/manual/en/messageformatter.create.php
	 * @param string $locale 
	 * @param string $pattern 
	 * @return MessageFormatter|null The formatter object, or null on failure.
	 */
	public function __construct (string $locale, string $pattern): ?MessageFormatter {}

	/**
	 * Constructs a new Message Formatter
	 * @link http://www.php.net/manual/en/messageformatter.create.php
	 * @param string $locale 
	 * @param string $pattern 
	 * @return MessageFormatter|null The formatter object, or null on failure.
	 */
	public static function create (string $locale, string $pattern): ?MessageFormatter {}

	/**
	 * Format the message
	 * @link http://www.php.net/manual/en/messageformatter.format.php
	 * @param array $values 
	 * @return string|false The formatted string, or false if an error occurred
	 */
	public function format (array $values): string|false {}

	/**
	 * Quick format message
	 * @link http://www.php.net/manual/en/messageformatter.formatmessage.php
	 * @param string $locale 
	 * @param string $pattern 
	 * @param array $values 
	 * @return string|false The formatted pattern string or false if an error occurred
	 */
	public static function formatMessage (string $locale, string $pattern, array $values): string|false {}

	/**
	 * Parse input string according to pattern
	 * @link http://www.php.net/manual/en/messageformatter.parse.php
	 * @param string $string 
	 * @return array|false An array containing the items extracted, or false on error
	 */
	public function parse (string $string): array|false {}

	/**
	 * Quick parse input string
	 * @link http://www.php.net/manual/en/messageformatter.parsemessage.php
	 * @param string $locale 
	 * @param string $pattern 
	 * @param string $message 
	 * @return array|false An array containing items extracted, or false on error
	 */
	public static function parseMessage (string $locale, string $pattern, string $message): array|false {}

	/**
	 * Set the pattern used by the formatter
	 * @link http://www.php.net/manual/en/messageformatter.setpattern.php
	 * @param string $pattern 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setPattern (string $pattern): bool {}

	/**
	 * Get the pattern used by the formatter
	 * @link http://www.php.net/manual/en/messageformatter.getpattern.php
	 * @return string|false The pattern string for this message formatter, or false on failure.
	 */
	public function getPattern (): string|false {}

	/**
	 * Get the locale for which the formatter was created
	 * @link http://www.php.net/manual/en/messageformatter.getlocale.php
	 * @return string The locale name
	 */
	public function getLocale (): string {}

	/**
	 * Get the error code from last operation
	 * @link http://www.php.net/manual/en/messageformatter.geterrorcode.php
	 * @return int The error code, one of UErrorCode values. Initial value is U_ZERO_ERROR.
	 */
	public function getErrorCode (): int {}

	/**
	 * Get the error text from the last operation
	 * @link http://www.php.net/manual/en/messageformatter.geterrormessage.php
	 * @return string Description of the last error.
	 */
	public function getErrorMessage (): string {}

}

/**
 * @link http://www.php.net/manual/en/class.intldateformatter.php
 */
class IntlDateFormatter  {
	/**
	 * Completely specified style (Tuesday, April 12, 1952 AD or 3:30:42pm PST)
	const FULL = 0;
	/**
	 * Long style (January 12, 1952 or 3:30:32pm)
	const LONG = 1;
	/**
	 * Medium style (Jan 12, 1952)
	const MEDIUM = 2;
	/**
	 * Most abbreviated style, only essential data (12/13/52 or 3:30pm)
	const SHORT = 3;
	/**
	 * Do not include this element
	const NONE = -1;
	/**
	 * The same as IntlDateFormatter::FULL, but yesterday, today, and tomorrow
	 * show as yesterday, today, and tomorrow,
	 * respectively. Available as of PHP 8.0.0, for dateType only.
	const RELATIVE_FULL = 128;
	/**
	 * The same as IntlDateFormatter::LONG, but yesterday, today, and tomorrow
	 * show as yesterday, today, and tomorrow,
	 * respectively. Available as of PHP 8.0.0, for dateType only.
	const RELATIVE_LONG = 129;
	/**
	 * The same as IntlDateFormatter::MEDIUM, but yesterday, today, and tomorrow
	 * show as yesterday, today, and tomorrow,
	 * respectively. Available as of PHP 8.0.0, for dateType only.
	const RELATIVE_MEDIUM = 130;
	/**
	 * The same as IntlDateFormatter::SHORT, but yesterday, today, and tomorrow
	 * show as yesterday, today, and tomorrow,
	 * respectively. Available as of PHP 8.0.0, for dateType only.
	const RELATIVE_SHORT = 131;
	/**
	 * Gregorian Calendar
	const GREGORIAN = 1;
	/**
	 * Non-Gregorian Calendar
	const TRADITIONAL = 0;


	/**
	 * Create a date formatter
	 * @link http://www.php.net/manual/en/intldateformatter.create.php
	 * @param string|null $locale 
	 * @param int $dateType [optional] 
	 * @param int $timeType [optional] 
	 * @param IntlTimeZone|DateTimeZone|string|null $timezone [optional] 
	 * @param IntlCalendar|int|null $calendar [optional] 
	 * @param string|null $pattern [optional] 
	 * @return IntlDateFormatter|null The created IntlDateFormatter or null in case of
	 * failure.
	 */
	public function __construct (?string $locale, int $dateType = \IntlDateFormatter::FULL, int $timeType = \IntlDateFormatter::FULL, IntlTimeZone|DateTimeZone|string|null $timezone = null, IntlCalendar|int|null $calendar = null, ?string $pattern = null): ?IntlDateFormatter {}

	/**
	 * Create a date formatter
	 * @link http://www.php.net/manual/en/intldateformatter.create.php
	 * @param string|null $locale 
	 * @param int $dateType [optional] 
	 * @param int $timeType [optional] 
	 * @param IntlTimeZone|DateTimeZone|string|null $timezone [optional] 
	 * @param IntlCalendar|int|null $calendar [optional] 
	 * @param string|null $pattern [optional] 
	 * @return IntlDateFormatter|null The created IntlDateFormatter or null in case of
	 * failure.
	 */
	public static function create (?string $locale, int $dateType = \IntlDateFormatter::FULL, int $timeType = \IntlDateFormatter::FULL, IntlTimeZone|DateTimeZone|string|null $timezone = null, IntlCalendar|int|null $calendar = null, ?string $pattern = null): ?IntlDateFormatter {}

	/**
	 * Get the datetype used for the IntlDateFormatter
	 * @link http://www.php.net/manual/en/intldateformatter.getdatetype.php
	 * @return int|false The current date type value of the formatter,
	 * or false on failure.
	 */
	public function getDateType (): int|false {}

	/**
	 * Get the timetype used for the IntlDateFormatter
	 * @link http://www.php.net/manual/en/intldateformatter.gettimetype.php
	 * @return int|false The current date type value of the formatter,
	 * or false on failure.
	 */
	public function getTimeType (): int|false {}

	/**
	 * Get the calendar type used for the IntlDateFormatter
	 * @link http://www.php.net/manual/en/intldateformatter.getcalendar.php
	 * @return int|false The calendar
	 * type being used by the formatter. Either
	 * IntlDateFormatter::TRADITIONAL or
	 * IntlDateFormatter::GREGORIAN.
	 * Returns false on failure.
	 */
	public function getCalendar (): int|false {}

	/**
	 * Sets the calendar type used by the formatter
	 * @link http://www.php.net/manual/en/intldateformatter.setcalendar.php
	 * @param IntlCalendar|int|null $calendar 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setCalendar (IntlCalendar|int|null $calendar): bool {}

	/**
	 * Get the timezone-id used for the IntlDateFormatter
	 * @link http://www.php.net/manual/en/intldateformatter.gettimezoneid.php
	 * @return string|false ID string for the time zone used by this formatter, or false on failure.
	 */
	public function getTimeZoneId (): string|false {}

	/**
	 * Get copy of formatterʼs calendar object
	 * @link http://www.php.net/manual/en/intldateformatter.getcalendarobject.php
	 * @return IntlCalendar|false|null A copy of the internal calendar object used by this formatter,
	 * or null if none has been set, or false on failure.
	 */
	public function getCalendarObject (): IntlCalendar|false|null {}

	/**
	 * Get formatterʼs timezone
	 * @link http://www.php.net/manual/en/intldateformatter.gettimezone.php
	 * @return IntlTimeZone|false The associated IntlTimeZone
	 * object or false on failure.
	 */
	public function getTimeZone (): IntlTimeZone|false {}

	/**
	 * Sets formatterʼs timezone
	 * @link http://www.php.net/manual/en/intldateformatter.settimezone.php
	 * @param IntlTimeZone|DateTimeZone|string|null $timezone The timezone to use for this formatter. This can be specified in the
	 * following forms:
	 * <p>null, in which case the default timezone will be used, as specified in
	 * the ini setting date.timezone or
	 * through the function date_default_timezone_set and as
	 * returned by date_default_timezone_get.</p>
	 * <p>An IntlTimeZone, which will be used directly.</p>
	 * <p>A DateTimeZone. Its identifier will be extracted
	 * and an ICU timezone object will be created; the timezone will be backed
	 * by ICUʼs database, not PHPʼs.</p>
	 * <p>A string, which should be a valid ICU timezone identifier.
	 * See IntlTimeZone::createTimeZoneIDEnumeration. Raw
	 * offsets such as "GMT+08:30" are also accepted.</p>
	 * @return bool|null Returns null on success and false on failure.
	 */
	public function setTimeZone (IntlTimeZone|DateTimeZone|string|null $timezone): ?bool {}

	/**
	 * Set the pattern used for the IntlDateFormatter
	 * @link http://www.php.net/manual/en/intldateformatter.setpattern.php
	 * @param string $pattern 
	 * @return bool Returns true on success or false on failure.
	 * Bad formatstrings are usually the cause of the failure.
	 */
	public function setPattern (string $pattern): bool {}

	/**
	 * Get the pattern used for the IntlDateFormatter
	 * @link http://www.php.net/manual/en/intldateformatter.getpattern.php
	 * @return string|false The pattern string being used to format/parse, or false on failure.
	 */
	public function getPattern (): string|false {}

	/**
	 * Get the locale used by formatter
	 * @link http://www.php.net/manual/en/intldateformatter.getlocale.php
	 * @param int $type [optional] 
	 * @return string|false The locale of this formatter, or false on failure.
	 */
	public function getLocale (int $type = ULOC_ACTUAL_LOCALE): string|false {}

	/**
	 * Set the leniency of the parser
	 * @link http://www.php.net/manual/en/intldateformatter.setlenient.php
	 * @param bool $lenient 
	 * @return void Returns true on success or false on failure.
	 */
	public function setLenient (bool $lenient): void {}

	/**
	 * Get the lenient used for the IntlDateFormatter
	 * @link http://www.php.net/manual/en/intldateformatter.islenient.php
	 * @return bool true if parser is lenient, false if parser is strict. By default the parser is lenient.
	 */
	public function isLenient (): bool {}

	/**
	 * Format the date/time value as a string
	 * @link http://www.php.net/manual/en/intldateformatter.format.php
	 * @param IntlCalendar|DateTimeInterface|array|string|int|float $datetime 
	 * @return string|false The formatted string or, if an error occurred, false.
	 */
	public function format (IntlCalendar|DateTimeInterface|array|string|int|float $datetime): string|false {}

	/**
	 * Formats an object
	 * @link http://www.php.net/manual/en/intldateformatter.formatobject.php
	 * @param IntlCalendar|DateTimeInterface $datetime An object of type IntlCalendar or
	 * DateTime. The timezone information in the object
	 * will be used.
	 * @param array|int|string|null $format [optional] How to format the date/time. This can either be an array with
	 * two elements (first the date style, then the time style, these being one
	 * of the constants IntlDateFormatter::NONE,
	 * IntlDateFormatter::SHORT,
	 * IntlDateFormatter::MEDIUM,
	 * IntlDateFormatter::LONG,
	 * IntlDateFormatter::FULL), an int with
	 * the value of one of these constants (in which case it will be used both
	 * for the time and the date) or a string with the format
	 * described in the ICU
	 * documentation. If null, the default style will be used.
	 * @param string|null $locale [optional] The locale to use, or null to use the default one.
	 * @return string|false A string with result or false on failure.
	 */
	public static function formatObject (IntlCalendar|DateTimeInterface $datetime, array|int|string|null $format = null, ?string $locale = null): string|false {}

	/**
	 * Parse string to a timestamp value
	 * @link http://www.php.net/manual/en/intldateformatter.parse.php
	 * @param string $string 
	 * @param int $offset [optional] 
	 * @return int|float|false Timestamp of parsed value, or false if value cannot be parsed.
	 */
	public function parse (string $string, int &$offset = null): int|float|false {}

	/**
	 * Parse string to a field-based time value
	 * @link http://www.php.net/manual/en/intldateformatter.localtime.php
	 * @param string $string 
	 * @param int $offset [optional] 
	 * @return array|false Localtime compatible array of integers : contains 24 hour clock value in tm_hour field,
	 * or false on failure.
	 */
	public function localtime (string $string, int &$offset = null): array|false {}

	/**
	 * Get the error code from last operation
	 * @link http://www.php.net/manual/en/intldateformatter.geterrorcode.php
	 * @return int The error code, one of UErrorCode values. Initial value is U_ZERO_ERROR.
	 */
	public function getErrorCode (): int {}

	/**
	 * Get the error text from the last operation
	 * @link http://www.php.net/manual/en/intldateformatter.geterrormessage.php
	 * @return string Description of the last error.
	 */
	public function getErrorMessage (): string {}

}

/**
 * @link http://www.php.net/manual/en/class.intldatepatterngenerator.php
 */
class IntlDatePatternGenerator  {

	/**
	 * Creates a new IntlDatePatternGenerator instance
	 * @link http://www.php.net/manual/en/intldatepatterngenerator.create.php
	 * @param string|null $locale [optional] The locale.
	 * If null is passed, uses the ini setting intl.default_locale.
	 * @return IntlDatePatternGenerator|null Returns an IntlDatePatternGenerator instance on success, or null on failure.
	 */
	public function __construct (?string $locale = null): ?IntlDatePatternGenerator {}

	/**
	 * Creates a new IntlDatePatternGenerator instance
	 * @link http://www.php.net/manual/en/intldatepatterngenerator.create.php
	 * @param string|null $locale [optional] The locale.
	 * If null is passed, uses the ini setting intl.default_locale.
	 * @return IntlDatePatternGenerator|null Returns an IntlDatePatternGenerator instance on success, or null on failure.
	 */
	public static function create (?string $locale = null): ?IntlDatePatternGenerator {}

	/**
	 * Determines the most suitable date/time format
	 * @link http://www.php.net/manual/en/intldatepatterngenerator.getbestpattern.php
	 * @param string $skeleton The skeleton.
	 * @return string|false Returns a format, accepted by DateTimeInterface::format on success, or false on failure.
	 */
	public function getBestPattern (string $skeleton): string|false {}

}

/**
 * @link http://www.php.net/manual/en/class.resourcebundle.php
 */
class ResourceBundle implements IteratorAggregate, Traversable, Countable {

	/**
	 * Create a resource bundle
	 * @link http://www.php.net/manual/en/resourcebundle.create.php
	 * @param string|null $locale 
	 * @param string|null $bundle 
	 * @param bool $fallback [optional] 
	 * @return ResourceBundle|null Returns ResourceBundle object or null on error.
	 */
	public function __construct (?string $locale, ?string $bundle, bool $fallback = true): ?ResourceBundle {}

	/**
	 * Create a resource bundle
	 * @link http://www.php.net/manual/en/resourcebundle.create.php
	 * @param string|null $locale 
	 * @param string|null $bundle 
	 * @param bool $fallback [optional] 
	 * @return ResourceBundle|null Returns ResourceBundle object or null on error.
	 */
	public static function create (?string $locale, ?string $bundle, bool $fallback = true): ?ResourceBundle {}

	/**
	 * Get data from the bundle
	 * @link http://www.php.net/manual/en/resourcebundle.get.php
	 * @param string|int $index 
	 * @param bool $fallback [optional] 
	 * @return mixed Returns the data located at the index or null on error. Strings, integers and binary data strings
	 * are returned as corresponding PHP types, integer array is returned as PHP array. Complex types are
	 * returned as ResourceBundle object.
	 */
	public function get (string|int $index, bool $fallback = true): mixed {}

	/**
	 * Get number of elements in the bundle
	 * @link http://www.php.net/manual/en/resourcebundle.count.php
	 * @return int Returns number of elements in the bundle.
	 */
	public function count (): int {}

	/**
	 * Get supported locales
	 * @link http://www.php.net/manual/en/resourcebundle.locales.php
	 * @param string $bundle 
	 * @return array|false Returns the list of locales supported by the bundle, or false on failure.
	 */
	public static function getLocales (string $bundle): array|false {}

	/**
	 * Get bundle's last error code
	 * @link http://www.php.net/manual/en/resourcebundle.geterrorcode.php
	 * @return int Returns error code from last bundle object call.
	 */
	public function getErrorCode (): int {}

	/**
	 * Get bundle's last error message
	 * @link http://www.php.net/manual/en/resourcebundle.geterrormessage.php
	 * @return string Returns error message from last bundle object's call.
	 */
	public function getErrorMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): Iterator {}

}

/**
 * @link http://www.php.net/manual/en/class.transliterator.php
 */
class Transliterator  {
	const FORWARD = 0;
	const REVERSE = 1;


	public readonly string $id;

	/**
	 * Private constructor to deny instantiation
	 * @link http://www.php.net/manual/en/transliterator.construct.php
	 */
	final private function __construct () {}

	/**
	 * Create a transliterator
	 * @link http://www.php.net/manual/en/transliterator.create.php
	 * @param string $id The ID. A list of all registered transliterator IDs can be retrieved by using
	 * Transliterator::listIDs.
	 * @param int $direction [optional] The direction, defaults to 
	 * Transliterator::FORWARD.
	 * May also be set to
	 * Transliterator::REVERSE.
	 * @return Transliterator|null Returns a Transliterator object on success,
	 * or null on failure.
	 */
	public static function create (string $id, int $direction = \Transliterator::FORWARD): ?Transliterator {}

	/**
	 * Create transliterator from rules
	 * @link http://www.php.net/manual/en/transliterator.createfromrules.php
	 * @param string $rules The rules as defined in Transform Rules Syntax of UTS #35: Unicode LDML.
	 * @param int $direction [optional] The direction, defaults to 
	 * Transliterator::FORWARD.
	 * May also be set to
	 * Transliterator::REVERSE.
	 * @return Transliterator|null Returns a Transliterator object on success,
	 * or null on failure.
	 */
	public static function createFromRules (string $rules, int $direction = \Transliterator::FORWARD): ?Transliterator {}

	/**
	 * Create an inverse transliterator
	 * @link http://www.php.net/manual/en/transliterator.createinverse.php
	 * @return Transliterator|null Returns a Transliterator object on success,
	 * or null on failure
	 */
	public function createInverse (): ?Transliterator {}

	/**
	 * Get transliterator IDs
	 * @link http://www.php.net/manual/en/transliterator.listids.php
	 * @return array|false An array of registered transliterator IDs on success,
	 * or false on failure.
	 */
	public static function listIDs (): array|false {}

	/**
	 * Transliterate a string
	 * @link http://www.php.net/manual/en/transliterator.transliterate.php
	 * @param string $string The string to be transformed.
	 * @param int $start [optional] The start index (in UTF-16 code units) from which the string will start
	 * to be transformed, inclusive. Indexing starts at 0. The text before will
	 * be left as is.
	 * @param int $end [optional] The end index (in UTF-16 code units) until which the string will be
	 * transformed, exclusive. Indexing starts at 0. The text after will be
	 * left as is.
	 * @return string|false The transformed string on success, or false on failure.
	 */
	public function transliterate (string $string, int $start = null, int $end = -1): string|false {}

	/**
	 * Get last error code
	 * @link http://www.php.net/manual/en/transliterator.geterrorcode.php
	 * @return int|false The error code on success,
	 * or false if none exists, or on failure.
	 */
	public function getErrorCode (): int|false {}

	/**
	 * Get last error message
	 * @link http://www.php.net/manual/en/transliterator.geterrormessage.php
	 * @return string|false The error message on success,
	 * or false if none exists, or on failure.
	 */
	public function getErrorMessage (): string|false {}

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


	/**
	 * Private constructor to disallow direct instantiation
	 * @link http://www.php.net/manual/en/intltimezone.construct.php
	 */
	private function __construct () {}

	/**
	 * Get the number of IDs in the equivalency group that includes the given ID
	 * @link http://www.php.net/manual/en/intltimezone.countequivalentids.php
	 * @param string $timezoneId 
	 * @return int|false 
	 */
	public static function countEquivalentIDs (string $timezoneId): int|false {}

	/**
	 * Create a new copy of the default timezone for this host
	 * @link http://www.php.net/manual/en/intltimezone.createdefault.php
	 * @return IntlTimeZone 
	 */
	public static function createDefault (): IntlTimeZone {}

	/**
	 * Get an enumeration over time zone IDs associated with the
	 * given country or offset
	 * @link http://www.php.net/manual/en/intltimezone.createenumeration.php
	 * @param IntlTimeZone|string|int|float|null $countryOrRawOffset [optional] 
	 * @return IntlIterator|false 
	 */
	public static function createEnumeration (IntlTimeZone|string|int|float|null $countryOrRawOffset = null): IntlIterator|false {}

	/**
	 * Create a timezone object for the given ID
	 * @link http://www.php.net/manual/en/intltimezone.createtimezone.php
	 * @param string $timezoneId 
	 * @return IntlTimeZone|null 
	 */
	public static function createTimeZone (string $timezoneId): ?IntlTimeZone {}

	/**
	 * Get an enumeration over system time zone IDs with the given filter conditions
	 * @link http://www.php.net/manual/en/intltimezone.createtimezoneidenumeration.php
	 * @param int $type 
	 * @param string|null $region [optional] 
	 * @param int|null $rawOffset [optional] 
	 * @return IntlIterator|false Returns IntlIterator or false on failure.
	 */
	public static function createTimeZoneIDEnumeration (int $type, ?string $region = null, ?int $rawOffset = null): IntlIterator|false {}

	/**
	 * Create a timezone object from DateTimeZone
	 * @link http://www.php.net/manual/en/intltimezone.fromdatetimezone.php
	 * @param DateTimeZone $timezone 
	 * @return IntlTimeZone|null 
	 */
	public static function fromDateTimeZone (DateTimeZone $timezone): ?IntlTimeZone {}

	/**
	 * Get the canonical system timezone ID or the normalized custom time zone ID for the given time zone ID
	 * @link http://www.php.net/manual/en/intltimezone.getcanonicalid.php
	 * @param string $timezoneId 
	 * @param bool $isSystemId [optional] 
	 * @return string|false 
	 */
	public static function getCanonicalID (string $timezoneId, bool &$isSystemId = null): string|false {}

	/**
	 * Get a name of this time zone suitable for presentation to the user
	 * @link http://www.php.net/manual/en/intltimezone.getdisplayname.php
	 * @param bool $dst [optional] 
	 * @param int $style [optional] 
	 * @param string|null $locale [optional] 
	 * @return string|false 
	 */
	public function getDisplayName (bool $dst = false, int $style = \IntlTimeZone::DISPLAY_LONG, ?string $locale = null): string|false {}

	/**
	 * Get the amount of time to be added to local standard time to get local wall clock time
	 * @link http://www.php.net/manual/en/intltimezone.getdstsavings.php
	 * @return int 
	 */
	public function getDSTSavings (): int {}

	/**
	 * Get an ID in the equivalency group that includes the given ID
	 * @link http://www.php.net/manual/en/intltimezone.getequivalentid.php
	 * @param string $timezoneId 
	 * @param int $offset 
	 * @return string|false 
	 */
	public static function getEquivalentID (string $timezoneId, int $offset): string|false {}

	/**
	 * Get last error code on the object
	 * @link http://www.php.net/manual/en/intltimezone.geterrorcode.php
	 * @return int|false 
	 */
	public function getErrorCode (): int|false {}

	/**
	 * Get last error message on the object
	 * @link http://www.php.net/manual/en/intltimezone.geterrormessage.php
	 * @return string|false 
	 */
	public function getErrorMessage (): string|false {}

	/**
	 * Create GMT (UTC) timezone
	 * @link http://www.php.net/manual/en/intltimezone.getgmt.php
	 * @return IntlTimeZone 
	 */
	public static function getGMT (): IntlTimeZone {}

	/**
	 * Get timezone ID
	 * @link http://www.php.net/manual/en/intltimezone.getid.php
	 * @return string|false 
	 */
	public function getID (): string|false {}

	/**
	 * Get the time zone raw and GMT offset for the given moment in time
	 * @link http://www.php.net/manual/en/intltimezone.getoffset.php
	 * @param float $timestamp 
	 * @param bool $local 
	 * @param int $rawOffset 
	 * @param int $dstOffset 
	 * @return bool 
	 */
	public function getOffset (float $timestamp, bool $local, int &$rawOffset, int &$dstOffset): bool {}

	/**
	 * Get the raw GMT offset (before taking daylight savings time into account
	 * @link http://www.php.net/manual/en/intltimezone.getrawoffset.php
	 * @return int 
	 */
	public function getRawOffset (): int {}

	/**
	 * Get the region code associated with the given system time zone ID
	 * @link http://www.php.net/manual/en/intltimezone.getregion.php
	 * @param string $timezoneId 
	 * @return string|false Return region or false on failure.
	 */
	public static function getRegion (string $timezoneId): string|false {}

	/**
	 * Get the timezone data version currently used by ICU
	 * @link http://www.php.net/manual/en/intltimezone.gettzdataversion.php
	 * @return string|false 
	 */
	public static function getTZDataVersion (): string|false {}

	/**
	 * Get the "unknown" time zone
	 * @link http://www.php.net/manual/en/intltimezone.getunknown.php
	 * @return IntlTimeZone Returns IntlTimeZone or null on failure.
	 */
	public static function getUnknown (): IntlTimeZone {}

	/**
	 * Translate a system timezone into a Windows timezone
	 * @link http://www.php.net/manual/en/intltimezone.getwindowsid.php
	 * @param string $timezoneId 
	 * @return string|false Returns the Windows timezone or false on failure.
	 */
	public static function getWindowsID (string $timezoneId): string|false {}

	/**
	 * Translate a Windows timezone into a system timezone
	 * @link http://www.php.net/manual/en/intltimezone.getidforwindowsid.php
	 * @param string $timezoneId 
	 * @param string|null $region [optional] 
	 * @return string|false Returns the system timezone or false on failure.
	 */
	public static function getIDForWindowsID (string $timezoneId, ?string $region = null): string|false {}

	/**
	 * Check if this zone has the same rules and offset as another zone
	 * @link http://www.php.net/manual/en/intltimezone.hassamerules.php
	 * @param IntlTimeZone $other 
	 * @return bool 
	 */
	public function hasSameRules (IntlTimeZone $other): bool {}

	/**
	 * Convert to DateTimeZone object
	 * @link http://www.php.net/manual/en/intltimezone.todatetimezone.php
	 * @return DateTimeZone|false 
	 */
	public function toDateTimeZone (): DateTimeZone|false {}

	/**
	 * Check if this time zone uses daylight savings time
	 * @link http://www.php.net/manual/en/intltimezone.usedaylighttime.php
	 * @return bool 
	 */
	public function useDaylightTime (): bool {}

}

/**
 * @link http://www.php.net/manual/en/class.intlcalendar.php
 */
class IntlCalendar  {
	/**
	 * Calendar field numerically representing an era, for instance
	 * 1 for AD and 0 for BC in the
	 * Gregorian/Julian calendars and 235 for the Heisei
	 * (平成) era in the Japanese calendar. Not all calendars have more than
	 * one era.
	const FIELD_ERA = 0;
	/**
	 * Calendar field for the year. This is not unique across eras. If the
	 * calendar type has more than one era, generally the minimum value for
	 * this field will be 1.
	const FIELD_YEAR = 1;
	/**
	 * Calendar field for the month. The month sequence is zero-based, so
	 * January (here used to signify the first month of the calendar; this
	 * may be called another name, such as Muharram in the Islamic calendar)
	 * is represented by 0, February by
	 * 1, …, December by 11 and, for
	 * calendars that have it, the 13th or leap month by
	 * 12.
	const FIELD_MONTH = 2;
	/**
	 * Calendar field for the number of the week of the year. This depends on
	 * which day of the week is deemed to start the
	 * week and the minimal number of days
	 * in a week.
	const FIELD_WEEK_OF_YEAR = 3;
	/**
	 * Calendar field for the number of the week of the month. This depends on
	 * which day of the week is deemed to start the
	 * week and the minimal number of days
	 * in a week.
	const FIELD_WEEK_OF_MONTH = 4;
	/**
	 * Calendar field for the day of the month. The same as
	 * IntlCalendar::FIELD_DAY_OF_MONTH, which has a
	 * clearer name.
	const FIELD_DATE = 5;
	/**
	 * Calendar field for the day of the year. For the Gregorian calendar,
	 * starts with 1 and ends with
	 * 365 or 366.
	const FIELD_DAY_OF_YEAR = 6;
	/**
	 * Calendar field for the day of the week. Its values start with
	 * 1 (Sunday, see IntlCalendar::DOW_SUNDAY
	 * and subsequent constants) and the last valid value is 7 (Saturday).
	const FIELD_DAY_OF_WEEK = 7;
	/**
	 * Given a day of the week (Sunday, Monday, …), this calendar
	 * field assigns an ordinal to such a day of the week in a specific month.
	 * Thus, if the value of this field is 1 and the value of the day of the
	 * week is 2 (Monday), then the set day of the month is the 1st Monday of the
	 * month; the maximum value is 5.
	 * <p>Additionally, the value 0 and negative values are
	 * also allowed. The value 0 encompasses the seven days
	 * that occur immediately before the first seven days of a month (which
	 * therefore have a ‘day of week in month’ with value
	 * 1). Negative values starts counting from the end of
	 * the month – -1 points to the last occurrence of a
	 * day of the week in a month, -2 to the second last,
	 * and so on.</p>
	 * <p>Unlike IntlCalendar::FIELD_WEEK_OF_MONTH
	 * and IntlCalendar::FIELD_WEEK_OF_YEAR,
	 * this value does not depend on
	 * IntlCalendar::getFirstDayOfWeek or on
	 * IntlCalendar::getMinimalDaysInFirstWeek. The first
	 * Monday is the first Monday, even if it occurs in a week that belongs to
	 * the previous month.</p>
	const FIELD_DAY_OF_WEEK_IN_MONTH = 8;
	/**
	 * Calendar field indicating whether a time is before noon (value
	 * 0, AM) or after (1). Midnight is
	 * AM, noon is PM.
	const FIELD_AM_PM = 9;
	/**
	 * Calendar field for the hour, without specifying whether itʼs in the
	 * morning or in the afternoon. Valid values are 0 to
	 * 11.
	const FIELD_HOUR = 10;
	/**
	 * Calendar field for the full (24h) hour of the day. Valid values are
	 * 0 to 23.
	const FIELD_HOUR_OF_DAY = 11;
	/**
	 * Calendar field for the minutes component of the time.
	const FIELD_MINUTE = 12;
	/**
	 * Calendar field for the seconds component of the time.
	const FIELD_SECOND = 13;
	/**
	 * Calendar field the milliseconds component of the time.
	const FIELD_MILLISECOND = 14;
	/**
	 * Calendar field indicating the raw offset of the timezone, in
	 * milliseconds. The raw offset is the timezone offset, excluding any
	 * offset due to daylight saving time.
	const FIELD_ZONE_OFFSET = 15;
	/**
	 * Calendar field for the daylight saving time offset of the calendarʼs
	 * timezone, in milliseconds, if active for calendarʼs time.
	const FIELD_DST_OFFSET = 16;
	/**
	 * Calendar field representing the year for week of year
	 * purposes.
	const FIELD_YEAR_WOY = 17;
	/**
	 * Calendar field for the localized day of the week. This is a value
	 * between 1 and 7,
	 * 1 being used for the day of the week that matches
	 * the value returned by
	 * IntlCalendar::getFirstDayOfWeek.
	const FIELD_DOW_LOCAL = 18;
	/**
	 * Calendar field for a year number representation that is continuous
	 * across eras. For the Gregorian calendar, the value of this field
	 * matches that of IntlCalendar::FIELD_YEAR for AD
	 * years; a BC year y is represented by -y +
	 * 1.
	const FIELD_EXTENDED_YEAR = 19;
	/**
	 * Calendar field for a modified Julian day number. It is different from a
	 * conventional Julian day number in that its transitions occur at local
	 * zone midnight rather than at noon UTC. It uniquely identifies a date.
	const FIELD_JULIAN_DAY = 20;
	/**
	 * Calendar field encompassing the information in
	 * IntlCalendar::FIELD_HOUR_OF_DAY,
	 * IntlCalendar::FIELD_MINUTE,
	 * IntlCalendar::FIELD_SECOND and
	 * IntlCalendar::FIELD_MILLISECOND. Range is from the
	 * 0 to 24 &#42; 3600 &#42; 1000 - 1. It is
	 * not the amount of milliseconds elapsed in the day since on DST
	 * transitions it will have discontinuities analog to those of the wall
	 * time.
	const FIELD_MILLISECONDS_IN_DAY = 21;
	/**
	 * Calendar field whose value is 1 for indicating a
	 * leap month and 0 otherwise.
	const FIELD_IS_LEAP_MONTH = 22;
	/**
	 * The total number of fields.
	const FIELD_FIELD_COUNT = 23;
	/**
	 * Alias for IntlCalendar::FIELD_DATE.
	const FIELD_DAY_OF_MONTH = 5;
	/**
	 * Sunday.
	const DOW_SUNDAY = 1;
	/**
	 * Monday.
	const DOW_MONDAY = 2;
	/**
	 * Tuesday.
	const DOW_TUESDAY = 3;
	/**
	 * Wednesday.
	const DOW_WEDNESDAY = 4;
	/**
	 * Thursday.
	const DOW_THURSDAY = 5;
	/**
	 * Friday.
	const DOW_FRIDAY = 6;
	/**
	 * Saturday.
	const DOW_SATURDAY = 7;
	/**
	 * Output of IntlCalendar::getDayOfWeekType
	 * indicating a day of week is a weekday.
	const DOW_TYPE_WEEKDAY = 0;
	/**
	 * Output of IntlCalendar::getDayOfWeekType
	 * indicating a day of week belongs to the weekend.
	const DOW_TYPE_WEEKEND = 1;
	/**
	 * Output of IntlCalendar::getDayOfWeekType
	 * indicating the weekend begins during the given day of week.
	const DOW_TYPE_WEEKEND_OFFSET = 2;
	/**
	 * Output of IntlCalendar::getDayOfWeekType
	 * indicating the weekend ends during the given day of week.
	const DOW_TYPE_WEEKEND_CEASE = 3;
	/**
	 * Output of IntlCalendar::getSkippedWallTimeOption
	 * indicating that wall times in the skipped range should refer to the
	 * same instant as wall times with one hour less and of
	 * IntlCalendar::getRepeatedWallTimeOption
	 * indicating the wall times in the repeated range should refer to the
	 * instant of the first occurrence of such wall time.
	const WALLTIME_FIRST = 1;
	/**
	 * Output of IntlCalendar::getSkippedWallTimeOption
	 * indicating that wall times in the skipped range should refer to the
	 * same instant as wall times with one hour after and of
	 * IntlCalendar::getRepeatedWallTimeOption
	 * indicating the wall times in the repeated range should refer to the
	 * instant of the second occurrence of such wall time.
	const WALLTIME_LAST = 0;
	/**
	 * Output of IntlCalendar::getSkippedWallTimeOption
	 * indicating that wall times in the skipped range should refer to the
	 * instant when the daylight saving time transition occurs (begins).
	const WALLTIME_NEXT_VALID = 2;


	/**
	 * Private constructor for disallowing instantiation
	 * @link http://www.php.net/manual/en/intlcalendar.construct.php
	 */
	private function __construct () {}

	/**
	 * Create a new IntlCalendar
	 * @link http://www.php.net/manual/en/intlcalendar.createinstance.php
	 * @param IntlTimeZone|DateTimeZone|string|null $timezone [optional] The timezone to use.
	 * <p>null, in which case the default timezone will be used, as specified in
	 * the ini setting date.timezone or
	 * through the function date_default_timezone_set and as
	 * returned by date_default_timezone_get.</p>
	 * <p>An IntlTimeZone, which will be used directly.</p>
	 * <p>A DateTimeZone. Its identifier will be extracted
	 * and an ICU timezone object will be created; the timezone will be backed
	 * by ICUʼs database, not PHPʼs.</p>
	 * <p>A string, which should be a valid ICU timezone identifier.
	 * See IntlTimeZone::createTimeZoneIDEnumeration. Raw
	 * offsets such as "GMT+08:30" are also accepted.</p>
	 * @param string|null $locale [optional] A locale to use or null to use the default locale.
	 * @return IntlCalendar|null The created IntlCalendar instance or null on
	 * failure.
	 */
	public static function createInstance (IntlTimeZone|DateTimeZone|string|null $timezone = null, ?string $locale = null): ?IntlCalendar {}

	/**
	 * Compare time of two IntlCalendar objects for equality
	 * @link http://www.php.net/manual/en/intlcalendar.equals.php
	 * @param IntlCalendar $other The calendar to compare with the primary object.
	 * @return bool Returns true if the current time of both this and the passed in
	 * IntlCalendar object are the same, or false
	 * otherwise.
	 * <p>>On failure false is also returned. To detect error conditions use intl_get_error_code, or set up Intl to throw exceptions.</p>
	 */
	public function equals (IntlCalendar $other): bool {}

	/**
	 * Calculate difference between given time and this objectʼs time
	 * @link http://www.php.net/manual/en/intlcalendar.fielddifference.php
	 * @param float $timestamp The time against which to compare the quantity represented by the
	 * field. For the result to be positive, the time
	 * given for this parameter must be ahead of the time of the object the
	 * method is being invoked on.
	 * @param int $field The field that represents the quantity being compared.
	 * <p>>
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.</p>
	 * @return int|false Returns a (signed) difference of time in the unit associated with the
	 * specified field or false on failure.
	 */
	public function fieldDifference (float $timestamp, int $field): int|false {}

	/**
	 * Add a (signed) amount of time to a field
	 * @link http://www.php.net/manual/en/intlcalendar.add.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @param int $value The signed amount to add to the current field. If the amount is positive,
	 * the instant will be moved forward; if it is negative, the instant will be
	 * moved into the past. The unit is implicit to the field type. For instance,
	 * hours for IntlCalendar::FIELD_HOUR_OF_DAY.
	 * @return bool Returns true on success or false on failure.
	 */
	public function add (int $field, int $value): bool {}

	/**
	 * Whether this objectʼs time is after that of the passed object
	 * @link http://www.php.net/manual/en/intlcalendar.after.php
	 * @param IntlCalendar $other The calendar whose time will be checked against the primary objectʼs time.
	 * @return bool Returns true if this objectʼs current time is after that of the
	 * calendar argumentʼs time. Returns false otherwise.
	 * <p>>On failure false is also returned. To detect error conditions use intl_get_error_code, or set up Intl to throw exceptions.</p>
	 */
	public function after (IntlCalendar $other): bool {}

	/**
	 * Whether this objectʼs time is before that of the passed object
	 * @link http://www.php.net/manual/en/intlcalendar.before.php
	 * @param IntlCalendar $other The calendar whose time will be checked against the primary objectʼs time.
	 * @return bool Returns true if this objectʼs current time is before that of the
	 * calendar argumentʼs time. Returns false otherwise.
	 * <p>>On failure false is also returned. To detect error conditions use intl_get_error_code, or set up Intl to throw exceptions.</p>
	 */
	public function before (IntlCalendar $other): bool {}

	/**
	 * Clear a field or all fields
	 * @link http://www.php.net/manual/en/intlcalendar.clear.php
	 * @param int|null $field [optional] >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @return true Always returns true.
	 */
	public function clear (?int $field = null): true {}

	/**
	 * Create an IntlCalendar from a DateTime object or string
	 * @link http://www.php.net/manual/en/intlcalendar.fromdatetime.php
	 * @param DateTime|string $datetime A DateTime object or a string that
	 * can be passed to DateTime::__construct.
	 * @param string|null $locale [optional] 
	 * @return IntlCalendar|null The created IntlCalendar object or null in case of
	 * failure. If a string is passed, any exception that occurs
	 * inside the DateTime constructor is propagated.
	 */
	public static function fromDateTime (DateTime|string $datetime, ?string $locale = null): ?IntlCalendar {}

	/**
	 * Get the value for a field
	 * @link http://www.php.net/manual/en/intlcalendar.get.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @return int|false An integer with the value of the time field.
	 */
	public function get (int $field): int|false {}

	/**
	 * The maximum value for a field, considering the objectʼs current time
	 * @link http://www.php.net/manual/en/intlcalendar.getactualmaximum.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @return int|false An int representing the maximum value in the units associated
	 * with the given field or false on failure.
	 */
	public function getActualMaximum (int $field): int|false {}

	/**
	 * The minimum value for a field, considering the objectʼs current time
	 * @link http://www.php.net/manual/en/intlcalendar.getactualminimum.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @return int|false An int representing the minimum value in the fieldʼs
	 * unit or false on failure.
	 */
	public function getActualMinimum (int $field): int|false {}

	/**
	 * Get array of locales for which there is data
	 * @link http://www.php.net/manual/en/intlcalendar.getavailablelocales.php
	 * @return array An array of strings, one for which locale.
	 */
	public static function getAvailableLocales (): array {}

	/**
	 * Tell whether a day is a weekday, weekend or a day that has a transition between the two
	 * @link http://www.php.net/manual/en/intlcalendar.getdayofweektype.php
	 * @param int $dayOfWeek One of the constants IntlCalendar::DOW_SUNDAY,
	 * IntlCalendar::DOW_MONDAY, …,
	 * IntlCalendar::DOW_SATURDAY.
	 * @return int|false Returns one of the constants 
	 * IntlCalendar::DOW_TYPE_WEEKDAY,
	 * IntlCalendar::DOW_TYPE_WEEKEND,
	 * IntlCalendar::DOW_TYPE_WEEKEND_OFFSET or
	 * IntlCalendar::DOW_TYPE_WEEKEND_CEASE or false on failure.
	 */
	public function getDayOfWeekType (int $dayOfWeek): int|false {}

	/**
	 * Get last error code on the object
	 * @link http://www.php.net/manual/en/intlcalendar.geterrorcode.php
	 * @return int|false An ICU error code indicating either success, failure or a warning.
	 * Returns false on failure.
	 */
	public function getErrorCode (): int|false {}

	/**
	 * Get last error message on the object
	 * @link http://www.php.net/manual/en/intlcalendar.geterrormessage.php
	 * @return string|false The error message associated with last error that occurred in a function call
	 * on this object, or a string indicating the non-existence of an error.
	 * Returns false on failure.
	 */
	public function getErrorMessage (): string|false {}

	/**
	 * Get the first day of the week for the calendarʼs locale
	 * @link http://www.php.net/manual/en/intlcalendar.getfirstdayofweek.php
	 * @return int|false One of the constants IntlCalendar::DOW_SUNDAY,
	 * IntlCalendar::DOW_MONDAY, …,
	 * IntlCalendar::DOW_SATURDAY or false on failure.
	 */
	public function getFirstDayOfWeek (): int|false {}

	/**
	 * Get the largest local minimum value for a field
	 * @link http://www.php.net/manual/en/intlcalendar.getgreatestminimum.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @return int|false An int representing a field value, in the fieldʼs
	 * unit, or false on failure.
	 */
	public function getGreatestMinimum (int $field): int|false {}

	/**
	 * Get set of locale keyword values
	 * @link http://www.php.net/manual/en/intlcalendar.getkeywordvaluesforlocale.php
	 * @param string $keyword The locale keyword for which relevant values are to be queried. Only
	 * 'calendar' is supported.
	 * @param string $locale The locale onto which the keyword/value pair are to be appended.
	 * @param bool $onlyCommon Whether to show only the values commonly used for the specified locale.
	 * @return IntlIterator|false An iterator that yields strings with the locale keyword
	 * values or false on failure.
	 */
	public static function getKeywordValuesForLocale (string $keyword, string $locale, bool $onlyCommon): IntlIterator|false {}

	/**
	 * Get the smallest local maximum for a field
	 * @link http://www.php.net/manual/en/intlcalendar.getleastmaximum.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @return int|false An int representing a field value in the fieldʼs
	 * unit or false on failure.
	 */
	public function getLeastMaximum (int $field): int|false {}

	/**
	 * Get the locale associated with the object
	 * @link http://www.php.net/manual/en/intlcalendar.getlocale.php
	 * @param int $type Whether to fetch the actual locale (the locale from which the calendar
	 * data originates, with Locale::ACTUAL_LOCALE) or the
	 * valid locale, i.e., the most specific locale supported by ICU relatively
	 * to the requested locale – see Locale::VALID_LOCALE.
	 * From the most general to the most specific, the locales are ordered in
	 * this fashion – actual locale, valid locale, requested locale.
	 * @return string|false A locale string or false on failure.
	 */
	public function getLocale (int $type): string|false {}

	/**
	 * Get the global maximum value for a field
	 * @link http://www.php.net/manual/en/intlcalendar.getmaximum.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @return int|false An int representing a field value in the fieldʼs
	 * unit or false on failure.
	 */
	public function getMaximum (int $field): int|false {}

	/**
	 * Get minimal number of days the first week in a year or month can have
	 * @link http://www.php.net/manual/en/intlcalendar.getminimaldaysinfirstweek.php
	 * @return int|false An int representing a number of days or false on failure.
	 */
	public function getMinimalDaysInFirstWeek (): int|false {}

	/**
	 * Set minimal number of days the first week in a year or month can have
	 * @link http://www.php.net/manual/en/intlcalendar.setminimaldaysinfirstweek.php
	 * @param int $days The number of minimal days to set.
	 * @return bool true on success, false on failure.
	 */
	public function setMinimalDaysInFirstWeek (int $days): bool {}

	/**
	 * Get the global minimum value for a field
	 * @link http://www.php.net/manual/en/intlcalendar.getminimum.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @return int|false An int representing a value for the given
	 * field in the fieldʼs unit or false on failure.
	 */
	public function getMinimum (int $field): int|false {}

	/**
	 * Get number representing the current time
	 * @link http://www.php.net/manual/en/intlcalendar.getnow.php
	 * @return float A float representing a number of milliseconds since the epoch,
	 * not counting leap seconds.
	 */
	public static function getNow (): float {}

	/**
	 * Get behavior for handling repeating wall time
	 * @link http://www.php.net/manual/en/intlcalendar.getrepeatedwalltimeoption.php
	 * @return int One of the constants IntlCalendar::WALLTIME_FIRST or
	 * IntlCalendar::WALLTIME_LAST.
	 */
	public function getRepeatedWallTimeOption (): int {}

	/**
	 * Get behavior for handling skipped wall time
	 * @link http://www.php.net/manual/en/intlcalendar.getskippedwalltimeoption.php
	 * @return int One of the constants IntlCalendar::WALLTIME_FIRST,
	 * IntlCalendar::WALLTIME_LAST or
	 * IntlCalendar::WALLTIME_NEXT_VALID.
	 */
	public function getSkippedWallTimeOption (): int {}

	/**
	 * Get time currently represented by the object
	 * @link http://www.php.net/manual/en/intlcalendar.gettime.php
	 * @return float|false A float representing the number of milliseconds elapsed since the
	 * reference time (1 Jan 1970 00:00:00 UTC), or false on failure
	 */
	public function getTime (): float|false {}

	/**
	 * Get the objectʼs timezone
	 * @link http://www.php.net/manual/en/intlcalendar.gettimezone.php
	 * @return IntlTimeZone|false An IntlTimeZone object corresponding to the one used
	 * internally in this object. Returns false on failure.
	 */
	public function getTimeZone (): IntlTimeZone|false {}

	/**
	 * Get the calendar type
	 * @link http://www.php.net/manual/en/intlcalendar.gettype.php
	 * @return string A string representing the calendar type, such as
	 * 'gregorian', 'islamic', etc.
	 */
	public function getType (): string {}

	/**
	 * Get time of the day at which weekend begins or ends
	 * @link http://www.php.net/manual/en/intlcalendar.getweekendtransition.php
	 * @param int $dayOfWeek One of the constants IntlCalendar::DOW_SUNDAY,
	 * IntlCalendar::DOW_MONDAY, …,
	 * IntlCalendar::DOW_SATURDAY.
	 * @return int|false The number of milliseconds into the day at which the weekend begins or
	 * ends or false on failure.
	 */
	public function getWeekendTransition (int $dayOfWeek): int|false {}

	/**
	 * Whether the objectʼs time is in Daylight Savings Time
	 * @link http://www.php.net/manual/en/intlcalendar.indaylighttime.php
	 * @return bool Returns true if the date is in Daylight Savings Time, false otherwise.
	 * <p>>On failure false is also returned. To detect error conditions use intl_get_error_code, or set up Intl to throw exceptions.</p>
	 */
	public function inDaylightTime (): bool {}

	/**
	 * Whether another calendar is equal but for a different time
	 * @link http://www.php.net/manual/en/intlcalendar.isequivalentto.php
	 * @param IntlCalendar $other The other calendar against which the comparison is to be made.
	 * @return bool Assuming there are no argument errors, returns true if the calendars are
	 * equivalent except possibly for their set time.
	 */
	public function isEquivalentTo (IntlCalendar $other): bool {}

	/**
	 * Whether date/time interpretation is in lenient mode
	 * @link http://www.php.net/manual/en/intlcalendar.islenient.php
	 * @return bool A bool representing whether the calendar is set to lenient mode.
	 */
	public function isLenient (): bool {}

	/**
	 * Whether a certain date/time is in the weekend
	 * @link http://www.php.net/manual/en/intlcalendar.isweekend.php
	 * @param float|null $timestamp [optional] An optional timestamp representing the number of milliseconds since the
	 * epoch, excluding leap seconds. If null, this objectʼs current time is
	 * used instead.
	 * @return bool A bool indicating whether the given or this objectʼs time occurs
	 * in a weekend.
	 * <p>>On failure false is also returned. To detect error conditions use intl_get_error_code, or set up Intl to throw exceptions.</p>
	 */
	public function isWeekend (?float $timestamp = null): bool {}

	/**
	 * Add value to field without carrying into more significant fields
	 * @link http://www.php.net/manual/en/intlcalendar.roll.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @param int|bool $value The (signed) amount to add to the field, true for rolling up (adding
	 * 1), or false for rolling down (subtracting
	 * 1).
	 * @return bool Returns true on success or false on failure.
	 */
	public function roll (int $field, int|bool $value): bool {}

	/**
	 * Whether a field is set
	 * @link http://www.php.net/manual/en/intlcalendar.isset.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @return bool Assuming there are no argument errors, returns true if the field is set.
	 */
	public function isSet (int $field): bool {}

	/**
	 * Set a time field or several common fields at once
	 * @link http://www.php.net/manual/en/intlcalendar.set.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @param int $value The new value of the given field.
	 * @return true Always returns true.
	 */
	public function set (int $field, int $value): true {}

	/**
	 * Set the day on which the week is deemed to start
	 * @link http://www.php.net/manual/en/intlcalendar.setfirstdayofweek.php
	 * @param int $dayOfWeek One of the constants IntlCalendar::DOW_SUNDAY,
	 * IntlCalendar::DOW_MONDAY, …,
	 * IntlCalendar::DOW_SATURDAY.
	 * @return true Always returns true.
	 */
	public function setFirstDayOfWeek (int $dayOfWeek): true {}

	/**
	 * Set whether date/time interpretation is to be lenient
	 * @link http://www.php.net/manual/en/intlcalendar.setlenient.php
	 * @param bool $lenient Use true to activate the lenient mode; false otherwise.
	 * @return true Always returns true.
	 */
	public function setLenient (bool $lenient): true {}

	/**
	 * Set behavior for handling repeating wall times at negative timezone offset transitions
	 * @link http://www.php.net/manual/en/intlcalendar.setrepeatedwalltimeoption.php
	 * @param int $option One of the constants IntlCalendar::WALLTIME_FIRST or
	 * IntlCalendar::WALLTIME_LAST.
	 * @return true Always returns true.
	 */
	public function setRepeatedWallTimeOption (int $option): true {}

	/**
	 * Set behavior for handling skipped wall times at positive timezone offset transitions
	 * @link http://www.php.net/manual/en/intlcalendar.setskippedwalltimeoption.php
	 * @param int $option One of the constants IntlCalendar::WALLTIME_FIRST,
	 * IntlCalendar::WALLTIME_LAST or
	 * IntlCalendar::WALLTIME_NEXT_VALID.
	 * @return true Always returns true.
	 */
	public function setSkippedWallTimeOption (int $option): true {}

	/**
	 * Set the calendar time in milliseconds since the epoch
	 * @link http://www.php.net/manual/en/intlcalendar.settime.php
	 * @param float $timestamp An instant represented by the number of number of milliseconds between
	 * such instant and the epoch, ignoring leap seconds.
	 * @return bool Returns true on success and false on failure.
	 */
	public function setTime (float $timestamp): bool {}

	/**
	 * Set the timezone used by this calendar
	 * @link http://www.php.net/manual/en/intlcalendar.settimezone.php
	 * @param IntlTimeZone|DateTimeZone|string|null $timezone The new timezone to be used by this calendar. It can be specified in the
	 * following ways:
	 * <p>
	 * <br>
	 * <p>
	 * null, in which case the default timezone will be used, as specified in
	 * the ini setting date.timezone or
	 * through the function date_default_timezone_set and as
	 * returned by date_default_timezone_get.
	 * </p>
	 * <br>
	 * <p>
	 * An IntlTimeZone, which will be used directly.
	 * </p>
	 * <br>
	 * <p>
	 * A DateTimeZone. Its identifier will be extracted
	 * and an ICU timezone object will be created; the timezone will be backed
	 * by ICUʼs database, not PHPʼs.
	 * </p>
	 * <br>
	 * <p>
	 * A string, which should be a valid ICU timezone identifier.
	 * See IntlTimeZone::createTimeZoneIDEnumeration. Raw
	 * offsets such as "GMT+08:30" are also accepted.
	 * </p>
	 * </p>
	 * <p>null, in which case the default timezone will be used, as specified in
	 * the ini setting date.timezone or
	 * through the function date_default_timezone_set and as
	 * returned by date_default_timezone_get.</p>
	 * <p>An IntlTimeZone, which will be used directly.</p>
	 * <p>A DateTimeZone. Its identifier will be extracted
	 * and an ICU timezone object will be created; the timezone will be backed
	 * by ICUʼs database, not PHPʼs.</p>
	 * <p>A string, which should be a valid ICU timezone identifier.
	 * See IntlTimeZone::createTimeZoneIDEnumeration. Raw
	 * offsets such as "GMT+08:30" are also accepted.</p>
	 * @return bool Returns true on success and false on failure.
	 */
	public function setTimeZone (IntlTimeZone|DateTimeZone|string|null $timezone): bool {}

	/**
	 * Convert an IntlCalendar into a DateTime object
	 * @link http://www.php.net/manual/en/intlcalendar.todatetime.php
	 * @return DateTime|false A DateTime object with the same timezone as this
	 * object (though using PHPʼs database instead of ICUʼs) and the same time,
	 * except for the smaller precision (second precision instead of millisecond).
	 * Returns false on failure.
	 */
	public function toDateTime (): DateTime|false {}

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
	 * @param IntlTimeZone $tz [optional] 
	 * @param string $locale [optional] 
	 * @return IntlTimeZone 
	 */
	public function __construct (IntlTimeZone $tz = null, string $locale = null): IntlTimeZone {}

	/**
	 * Set the Gregorian Calendar the change date
	 * @link http://www.php.net/manual/en/intlgregoriancalendar.setgregorianchange.php
	 * @param float $timestamp 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setGregorianChange (float $timestamp): bool {}

	/**
	 * Get the Gregorian Calendar change date
	 * @link http://www.php.net/manual/en/intlgregoriancalendar.getgregorianchange.php
	 * @return float Returns the change date.
	 */
	public function getGregorianChange (): float {}

	/**
	 * Determine if the given year is a leap year
	 * @link http://www.php.net/manual/en/intlgregoriancalendar.isleapyear.php
	 * @param int $year 
	 * @return bool Returns true for leap years, false otherwise and on failure.
	 */
	public function isLeapYear (int $year): bool {}

	/**
	 * Create a new IntlCalendar
	 * @link http://www.php.net/manual/en/intlcalendar.createinstance.php
	 * @param IntlTimeZone|DateTimeZone|string|null $timezone [optional] The timezone to use.
	 * <p>null, in which case the default timezone will be used, as specified in
	 * the ini setting date.timezone or
	 * through the function date_default_timezone_set and as
	 * returned by date_default_timezone_get.</p>
	 * <p>An IntlTimeZone, which will be used directly.</p>
	 * <p>A DateTimeZone. Its identifier will be extracted
	 * and an ICU timezone object will be created; the timezone will be backed
	 * by ICUʼs database, not PHPʼs.</p>
	 * <p>A string, which should be a valid ICU timezone identifier.
	 * See IntlTimeZone::createTimeZoneIDEnumeration. Raw
	 * offsets such as "GMT+08:30" are also accepted.</p>
	 * @param string|null $locale [optional] A locale to use or null to use the default locale.
	 * @return IntlCalendar|null The created IntlCalendar instance or null on
	 * failure.
	 */
	public static function createInstance (IntlTimeZone|DateTimeZone|string|null $timezone = null, ?string $locale = null): ?IntlCalendar {}

	/**
	 * Compare time of two IntlCalendar objects for equality
	 * @link http://www.php.net/manual/en/intlcalendar.equals.php
	 * @param IntlCalendar $other The calendar to compare with the primary object.
	 * @return bool Returns true if the current time of both this and the passed in
	 * IntlCalendar object are the same, or false
	 * otherwise.
	 * <p>>On failure false is also returned. To detect error conditions use intl_get_error_code, or set up Intl to throw exceptions.</p>
	 */
	public function equals (IntlCalendar $other): bool {}

	/**
	 * Calculate difference between given time and this objectʼs time
	 * @link http://www.php.net/manual/en/intlcalendar.fielddifference.php
	 * @param float $timestamp The time against which to compare the quantity represented by the
	 * field. For the result to be positive, the time
	 * given for this parameter must be ahead of the time of the object the
	 * method is being invoked on.
	 * @param int $field The field that represents the quantity being compared.
	 * <p>>
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.</p>
	 * @return int|false Returns a (signed) difference of time in the unit associated with the
	 * specified field or false on failure.
	 */
	public function fieldDifference (float $timestamp, int $field): int|false {}

	/**
	 * Add a (signed) amount of time to a field
	 * @link http://www.php.net/manual/en/intlcalendar.add.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @param int $value The signed amount to add to the current field. If the amount is positive,
	 * the instant will be moved forward; if it is negative, the instant will be
	 * moved into the past. The unit is implicit to the field type. For instance,
	 * hours for IntlCalendar::FIELD_HOUR_OF_DAY.
	 * @return bool Returns true on success or false on failure.
	 */
	public function add (int $field, int $value): bool {}

	/**
	 * Whether this objectʼs time is after that of the passed object
	 * @link http://www.php.net/manual/en/intlcalendar.after.php
	 * @param IntlCalendar $other The calendar whose time will be checked against the primary objectʼs time.
	 * @return bool Returns true if this objectʼs current time is after that of the
	 * calendar argumentʼs time. Returns false otherwise.
	 * <p>>On failure false is also returned. To detect error conditions use intl_get_error_code, or set up Intl to throw exceptions.</p>
	 */
	public function after (IntlCalendar $other): bool {}

	/**
	 * Whether this objectʼs time is before that of the passed object
	 * @link http://www.php.net/manual/en/intlcalendar.before.php
	 * @param IntlCalendar $other The calendar whose time will be checked against the primary objectʼs time.
	 * @return bool Returns true if this objectʼs current time is before that of the
	 * calendar argumentʼs time. Returns false otherwise.
	 * <p>>On failure false is also returned. To detect error conditions use intl_get_error_code, or set up Intl to throw exceptions.</p>
	 */
	public function before (IntlCalendar $other): bool {}

	/**
	 * Clear a field or all fields
	 * @link http://www.php.net/manual/en/intlcalendar.clear.php
	 * @param int|null $field [optional] >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @return true Always returns true.
	 */
	public function clear (?int $field = null): true {}

	/**
	 * Create an IntlCalendar from a DateTime object or string
	 * @link http://www.php.net/manual/en/intlcalendar.fromdatetime.php
	 * @param DateTime|string $datetime A DateTime object or a string that
	 * can be passed to DateTime::__construct.
	 * @param string|null $locale [optional] 
	 * @return IntlCalendar|null The created IntlCalendar object or null in case of
	 * failure. If a string is passed, any exception that occurs
	 * inside the DateTime constructor is propagated.
	 */
	public static function fromDateTime (DateTime|string $datetime, ?string $locale = null): ?IntlCalendar {}

	/**
	 * Get the value for a field
	 * @link http://www.php.net/manual/en/intlcalendar.get.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @return int|false An integer with the value of the time field.
	 */
	public function get (int $field): int|false {}

	/**
	 * The maximum value for a field, considering the objectʼs current time
	 * @link http://www.php.net/manual/en/intlcalendar.getactualmaximum.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @return int|false An int representing the maximum value in the units associated
	 * with the given field or false on failure.
	 */
	public function getActualMaximum (int $field): int|false {}

	/**
	 * The minimum value for a field, considering the objectʼs current time
	 * @link http://www.php.net/manual/en/intlcalendar.getactualminimum.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @return int|false An int representing the minimum value in the fieldʼs
	 * unit or false on failure.
	 */
	public function getActualMinimum (int $field): int|false {}

	/**
	 * Get array of locales for which there is data
	 * @link http://www.php.net/manual/en/intlcalendar.getavailablelocales.php
	 * @return array An array of strings, one for which locale.
	 */
	public static function getAvailableLocales (): array {}

	/**
	 * Tell whether a day is a weekday, weekend or a day that has a transition between the two
	 * @link http://www.php.net/manual/en/intlcalendar.getdayofweektype.php
	 * @param int $dayOfWeek One of the constants IntlCalendar::DOW_SUNDAY,
	 * IntlCalendar::DOW_MONDAY, …,
	 * IntlCalendar::DOW_SATURDAY.
	 * @return int|false Returns one of the constants 
	 * IntlCalendar::DOW_TYPE_WEEKDAY,
	 * IntlCalendar::DOW_TYPE_WEEKEND,
	 * IntlCalendar::DOW_TYPE_WEEKEND_OFFSET or
	 * IntlCalendar::DOW_TYPE_WEEKEND_CEASE or false on failure.
	 */
	public function getDayOfWeekType (int $dayOfWeek): int|false {}

	/**
	 * Get last error code on the object
	 * @link http://www.php.net/manual/en/intlcalendar.geterrorcode.php
	 * @return int|false An ICU error code indicating either success, failure or a warning.
	 * Returns false on failure.
	 */
	public function getErrorCode (): int|false {}

	/**
	 * Get last error message on the object
	 * @link http://www.php.net/manual/en/intlcalendar.geterrormessage.php
	 * @return string|false The error message associated with last error that occurred in a function call
	 * on this object, or a string indicating the non-existence of an error.
	 * Returns false on failure.
	 */
	public function getErrorMessage (): string|false {}

	/**
	 * Get the first day of the week for the calendarʼs locale
	 * @link http://www.php.net/manual/en/intlcalendar.getfirstdayofweek.php
	 * @return int|false One of the constants IntlCalendar::DOW_SUNDAY,
	 * IntlCalendar::DOW_MONDAY, …,
	 * IntlCalendar::DOW_SATURDAY or false on failure.
	 */
	public function getFirstDayOfWeek (): int|false {}

	/**
	 * Get the largest local minimum value for a field
	 * @link http://www.php.net/manual/en/intlcalendar.getgreatestminimum.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @return int|false An int representing a field value, in the fieldʼs
	 * unit, or false on failure.
	 */
	public function getGreatestMinimum (int $field): int|false {}

	/**
	 * Get set of locale keyword values
	 * @link http://www.php.net/manual/en/intlcalendar.getkeywordvaluesforlocale.php
	 * @param string $keyword The locale keyword for which relevant values are to be queried. Only
	 * 'calendar' is supported.
	 * @param string $locale The locale onto which the keyword/value pair are to be appended.
	 * @param bool $onlyCommon Whether to show only the values commonly used for the specified locale.
	 * @return IntlIterator|false An iterator that yields strings with the locale keyword
	 * values or false on failure.
	 */
	public static function getKeywordValuesForLocale (string $keyword, string $locale, bool $onlyCommon): IntlIterator|false {}

	/**
	 * Get the smallest local maximum for a field
	 * @link http://www.php.net/manual/en/intlcalendar.getleastmaximum.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @return int|false An int representing a field value in the fieldʼs
	 * unit or false on failure.
	 */
	public function getLeastMaximum (int $field): int|false {}

	/**
	 * Get the locale associated with the object
	 * @link http://www.php.net/manual/en/intlcalendar.getlocale.php
	 * @param int $type Whether to fetch the actual locale (the locale from which the calendar
	 * data originates, with Locale::ACTUAL_LOCALE) or the
	 * valid locale, i.e., the most specific locale supported by ICU relatively
	 * to the requested locale – see Locale::VALID_LOCALE.
	 * From the most general to the most specific, the locales are ordered in
	 * this fashion – actual locale, valid locale, requested locale.
	 * @return string|false A locale string or false on failure.
	 */
	public function getLocale (int $type): string|false {}

	/**
	 * Get the global maximum value for a field
	 * @link http://www.php.net/manual/en/intlcalendar.getmaximum.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @return int|false An int representing a field value in the fieldʼs
	 * unit or false on failure.
	 */
	public function getMaximum (int $field): int|false {}

	/**
	 * Get minimal number of days the first week in a year or month can have
	 * @link http://www.php.net/manual/en/intlcalendar.getminimaldaysinfirstweek.php
	 * @return int|false An int representing a number of days or false on failure.
	 */
	public function getMinimalDaysInFirstWeek (): int|false {}

	/**
	 * Set minimal number of days the first week in a year or month can have
	 * @link http://www.php.net/manual/en/intlcalendar.setminimaldaysinfirstweek.php
	 * @param int $days The number of minimal days to set.
	 * @return bool true on success, false on failure.
	 */
	public function setMinimalDaysInFirstWeek (int $days): bool {}

	/**
	 * Get the global minimum value for a field
	 * @link http://www.php.net/manual/en/intlcalendar.getminimum.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @return int|false An int representing a value for the given
	 * field in the fieldʼs unit or false on failure.
	 */
	public function getMinimum (int $field): int|false {}

	/**
	 * Get number representing the current time
	 * @link http://www.php.net/manual/en/intlcalendar.getnow.php
	 * @return float A float representing a number of milliseconds since the epoch,
	 * not counting leap seconds.
	 */
	public static function getNow (): float {}

	/**
	 * Get behavior for handling repeating wall time
	 * @link http://www.php.net/manual/en/intlcalendar.getrepeatedwalltimeoption.php
	 * @return int One of the constants IntlCalendar::WALLTIME_FIRST or
	 * IntlCalendar::WALLTIME_LAST.
	 */
	public function getRepeatedWallTimeOption (): int {}

	/**
	 * Get behavior for handling skipped wall time
	 * @link http://www.php.net/manual/en/intlcalendar.getskippedwalltimeoption.php
	 * @return int One of the constants IntlCalendar::WALLTIME_FIRST,
	 * IntlCalendar::WALLTIME_LAST or
	 * IntlCalendar::WALLTIME_NEXT_VALID.
	 */
	public function getSkippedWallTimeOption (): int {}

	/**
	 * Get time currently represented by the object
	 * @link http://www.php.net/manual/en/intlcalendar.gettime.php
	 * @return float|false A float representing the number of milliseconds elapsed since the
	 * reference time (1 Jan 1970 00:00:00 UTC), or false on failure
	 */
	public function getTime (): float|false {}

	/**
	 * Get the objectʼs timezone
	 * @link http://www.php.net/manual/en/intlcalendar.gettimezone.php
	 * @return IntlTimeZone|false An IntlTimeZone object corresponding to the one used
	 * internally in this object. Returns false on failure.
	 */
	public function getTimeZone (): IntlTimeZone|false {}

	/**
	 * Get the calendar type
	 * @link http://www.php.net/manual/en/intlcalendar.gettype.php
	 * @return string A string representing the calendar type, such as
	 * 'gregorian', 'islamic', etc.
	 */
	public function getType (): string {}

	/**
	 * Get time of the day at which weekend begins or ends
	 * @link http://www.php.net/manual/en/intlcalendar.getweekendtransition.php
	 * @param int $dayOfWeek One of the constants IntlCalendar::DOW_SUNDAY,
	 * IntlCalendar::DOW_MONDAY, …,
	 * IntlCalendar::DOW_SATURDAY.
	 * @return int|false The number of milliseconds into the day at which the weekend begins or
	 * ends or false on failure.
	 */
	public function getWeekendTransition (int $dayOfWeek): int|false {}

	/**
	 * Whether the objectʼs time is in Daylight Savings Time
	 * @link http://www.php.net/manual/en/intlcalendar.indaylighttime.php
	 * @return bool Returns true if the date is in Daylight Savings Time, false otherwise.
	 * <p>>On failure false is also returned. To detect error conditions use intl_get_error_code, or set up Intl to throw exceptions.</p>
	 */
	public function inDaylightTime (): bool {}

	/**
	 * Whether another calendar is equal but for a different time
	 * @link http://www.php.net/manual/en/intlcalendar.isequivalentto.php
	 * @param IntlCalendar $other The other calendar against which the comparison is to be made.
	 * @return bool Assuming there are no argument errors, returns true if the calendars are
	 * equivalent except possibly for their set time.
	 */
	public function isEquivalentTo (IntlCalendar $other): bool {}

	/**
	 * Whether date/time interpretation is in lenient mode
	 * @link http://www.php.net/manual/en/intlcalendar.islenient.php
	 * @return bool A bool representing whether the calendar is set to lenient mode.
	 */
	public function isLenient (): bool {}

	/**
	 * Whether a certain date/time is in the weekend
	 * @link http://www.php.net/manual/en/intlcalendar.isweekend.php
	 * @param float|null $timestamp [optional] An optional timestamp representing the number of milliseconds since the
	 * epoch, excluding leap seconds. If null, this objectʼs current time is
	 * used instead.
	 * @return bool A bool indicating whether the given or this objectʼs time occurs
	 * in a weekend.
	 * <p>>On failure false is also returned. To detect error conditions use intl_get_error_code, or set up Intl to throw exceptions.</p>
	 */
	public function isWeekend (?float $timestamp = null): bool {}

	/**
	 * Add value to field without carrying into more significant fields
	 * @link http://www.php.net/manual/en/intlcalendar.roll.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @param int|bool $value The (signed) amount to add to the field, true for rolling up (adding
	 * 1), or false for rolling down (subtracting
	 * 1).
	 * @return bool Returns true on success or false on failure.
	 */
	public function roll (int $field, int|bool $value): bool {}

	/**
	 * Whether a field is set
	 * @link http://www.php.net/manual/en/intlcalendar.isset.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @return bool Assuming there are no argument errors, returns true if the field is set.
	 */
	public function isSet (int $field): bool {}

	/**
	 * Set a time field or several common fields at once
	 * @link http://www.php.net/manual/en/intlcalendar.set.php
	 * @param int $field >
	 * One of the IntlCalendar date/time field constants. These are integer
	 * values between 0 and
	 * IntlCalendar::FIELD_COUNT.
	 * @param int $value The new value of the given field.
	 * @return true Always returns true.
	 */
	public function set (int $field, int $value): true {}

	/**
	 * Set the day on which the week is deemed to start
	 * @link http://www.php.net/manual/en/intlcalendar.setfirstdayofweek.php
	 * @param int $dayOfWeek One of the constants IntlCalendar::DOW_SUNDAY,
	 * IntlCalendar::DOW_MONDAY, …,
	 * IntlCalendar::DOW_SATURDAY.
	 * @return true Always returns true.
	 */
	public function setFirstDayOfWeek (int $dayOfWeek): true {}

	/**
	 * Set whether date/time interpretation is to be lenient
	 * @link http://www.php.net/manual/en/intlcalendar.setlenient.php
	 * @param bool $lenient Use true to activate the lenient mode; false otherwise.
	 * @return true Always returns true.
	 */
	public function setLenient (bool $lenient): true {}

	/**
	 * Set behavior for handling repeating wall times at negative timezone offset transitions
	 * @link http://www.php.net/manual/en/intlcalendar.setrepeatedwalltimeoption.php
	 * @param int $option One of the constants IntlCalendar::WALLTIME_FIRST or
	 * IntlCalendar::WALLTIME_LAST.
	 * @return true Always returns true.
	 */
	public function setRepeatedWallTimeOption (int $option): true {}

	/**
	 * Set behavior for handling skipped wall times at positive timezone offset transitions
	 * @link http://www.php.net/manual/en/intlcalendar.setskippedwalltimeoption.php
	 * @param int $option One of the constants IntlCalendar::WALLTIME_FIRST,
	 * IntlCalendar::WALLTIME_LAST or
	 * IntlCalendar::WALLTIME_NEXT_VALID.
	 * @return true Always returns true.
	 */
	public function setSkippedWallTimeOption (int $option): true {}

	/**
	 * Set the calendar time in milliseconds since the epoch
	 * @link http://www.php.net/manual/en/intlcalendar.settime.php
	 * @param float $timestamp An instant represented by the number of number of milliseconds between
	 * such instant and the epoch, ignoring leap seconds.
	 * @return bool Returns true on success and false on failure.
	 */
	public function setTime (float $timestamp): bool {}

	/**
	 * Set the timezone used by this calendar
	 * @link http://www.php.net/manual/en/intlcalendar.settimezone.php
	 * @param IntlTimeZone|DateTimeZone|string|null $timezone The new timezone to be used by this calendar. It can be specified in the
	 * following ways:
	 * <p>
	 * <br>
	 * <p>
	 * null, in which case the default timezone will be used, as specified in
	 * the ini setting date.timezone or
	 * through the function date_default_timezone_set and as
	 * returned by date_default_timezone_get.
	 * </p>
	 * <br>
	 * <p>
	 * An IntlTimeZone, which will be used directly.
	 * </p>
	 * <br>
	 * <p>
	 * A DateTimeZone. Its identifier will be extracted
	 * and an ICU timezone object will be created; the timezone will be backed
	 * by ICUʼs database, not PHPʼs.
	 * </p>
	 * <br>
	 * <p>
	 * A string, which should be a valid ICU timezone identifier.
	 * See IntlTimeZone::createTimeZoneIDEnumeration. Raw
	 * offsets such as "GMT+08:30" are also accepted.
	 * </p>
	 * </p>
	 * <p>null, in which case the default timezone will be used, as specified in
	 * the ini setting date.timezone or
	 * through the function date_default_timezone_set and as
	 * returned by date_default_timezone_get.</p>
	 * <p>An IntlTimeZone, which will be used directly.</p>
	 * <p>A DateTimeZone. Its identifier will be extracted
	 * and an ICU timezone object will be created; the timezone will be backed
	 * by ICUʼs database, not PHPʼs.</p>
	 * <p>A string, which should be a valid ICU timezone identifier.
	 * See IntlTimeZone::createTimeZoneIDEnumeration. Raw
	 * offsets such as "GMT+08:30" are also accepted.</p>
	 * @return bool Returns true on success and false on failure.
	 */
	public function setTimeZone (IntlTimeZone|DateTimeZone|string|null $timezone): bool {}

	/**
	 * Convert an IntlCalendar into a DateTime object
	 * @link http://www.php.net/manual/en/intlcalendar.todatetime.php
	 * @return DateTime|false A DateTime object with the same timezone as this
	 * object (though using PHPʼs database instead of ICUʼs) and the same time,
	 * except for the smaller precision (second precision instead of millisecond).
	 * Returns false on failure.
	 */
	public function toDateTime (): DateTime|false {}

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
	 * @param string $string String to test.
	 * @param int $errorCode [optional] This variable is set by-reference to int containing an error, if there
	 * was any.
	 * @return bool Returns true if there are suspicious characters, false otherwise.
	 */
	public function isSuspicious (string $string, int &$errorCode = null): bool {}

	/**
	 * Checks if given strings can be confused
	 * @link http://www.php.net/manual/en/spoofchecker.areconfusable.php
	 * @param string $string1 First string to check.
	 * @param string $string2 Second string to check.
	 * @param int $errorCode [optional] This variable is set by-reference to int containing an error, if there
	 * was any.
	 * @return bool Returns true if two given strings can be confused, false otherwise.
	 */
	public function areConfusable (string $string1, string $string2, int &$errorCode = null): bool {}

	/**
	 * Locales to use when running checks
	 * @link http://www.php.net/manual/en/spoofchecker.setallowedlocales.php
	 * @param string $locales 
	 * @return void 
	 */
	public function setAllowedLocales (string $locales): void {}

	/**
	 * Set the checks to run
	 * @link http://www.php.net/manual/en/spoofchecker.setchecks.php
	 * @param int $checks The checks that will be performed by SpoofChecker::isSuspicious.
	 * A bitmask of
	 * Spoofchecker::SINGLE_SCRIPT_CONFUSABLE,
	 * Spoofchecker::MIXED_SCRIPT_CONFUSABLE,
	 * Spoofchecker::WHOLE_SCRIPT_CONFUSABLE,
	 * Spoofchecker::ANY_CASE,
	 * Spoofchecker::SINGLE_SCRIPT,
	 * Spoofchecker::INVISIBLE, or
	 * Spoofchecker::CHAR_LIMIT.
	 * Defaults to all checks as of ICU 58; prior to that version,
	 * Spoofchecker::SINGLE_SCRIPT was excluded.
	 * @return void No value is returned.
	 */
	public function setChecks (int $checks): void {}

	/**
	 * {@inheritdoc}
	 * @param int $level
	 */
	public function setRestrictionLevel (int $level) {}

}

/**
 * This class is used for generating exceptions when errors occur inside intl
 * functions. Such exceptions are only generated when intl.use_exceptions is enabled.
 * @link http://www.php.net/manual/en/class.intlexception.php
 */
class IntlException extends Exception implements Throwable, Stringable {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return int Returns the exception code as int in
	 * Exception but possibly as other type in
	 * Exception descendants (for example as
	 * string in PDOException).
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Returns the filename in which the exception was created.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Returns the line number where the exception was created.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString (): string {}

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
	public function current (): mixed {}

	/**
	 * Get the current key
	 * @link http://www.php.net/manual/en/intliterator.key.php
	 * @return mixed 
	 */
	public function key (): mixed {}

	/**
	 * Move forward to the next element
	 * @link http://www.php.net/manual/en/intliterator.next.php
	 * @return void 
	 */
	public function next (): void {}

	/**
	 * Rewind the iterator to the first element
	 * @link http://www.php.net/manual/en/intliterator.rewind.php
	 * @return void 
	 */
	public function rewind (): void {}

	/**
	 * Check if current position is valid
	 * @link http://www.php.net/manual/en/intliterator.valid.php
	 * @return bool 
	 */
	public function valid (): bool {}

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
 * <p>This class implements IteratorAggregate. Traversing an
 * IntlBreakIterator yields non-negative integer
 * values representing the successive locations of the text boundaries,
 * expressed as UTF-8 code units (byte) counts, taken from the beginning of
 * the text (which has the location 0). The keys yielded
 * by the iterator simply form the sequence of natural numbers
 * {0, 1, 2, …}.</p>
 * @link http://www.php.net/manual/en/class.intlbreakiterator.php
 */
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
	 * Create break iterator for boundaries of combining character sequences
	 * @link http://www.php.net/manual/en/intlbreakiterator.createcharacterinstance.php
	 * @param string|null $locale [optional] 
	 * @return IntlBreakIterator|null 
	 */
	public static function createCharacterInstance (?string $locale = null): ?IntlBreakIterator {}

	/**
	 * Create break iterator for boundaries of code points
	 * @link http://www.php.net/manual/en/intlbreakiterator.createcodepointinstance.php
	 * @return IntlCodePointBreakIterator 
	 */
	public static function createCodePointInstance (): IntlCodePointBreakIterator {}

	/**
	 * Create break iterator for logically possible line breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createlineinstance.php
	 * @param string|null $locale [optional] 
	 * @return IntlBreakIterator|null 
	 */
	public static function createLineInstance (?string $locale = null): ?IntlBreakIterator {}

	/**
	 * Create break iterator for sentence breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createsentenceinstance.php
	 * @param string|null $locale [optional] 
	 * @return IntlBreakIterator|null 
	 */
	public static function createSentenceInstance (?string $locale = null): ?IntlBreakIterator {}

	/**
	 * Create break iterator for title-casing breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createtitleinstance.php
	 * @param string|null $locale [optional] 
	 * @return IntlBreakIterator|null 
	 */
	public static function createTitleInstance (?string $locale = null): ?IntlBreakIterator {}

	/**
	 * Create break iterator for word breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createwordinstance.php
	 * @param string|null $locale [optional] 
	 * @return IntlBreakIterator|null 
	 */
	public static function createWordInstance (?string $locale = null): ?IntlBreakIterator {}

	/**
	 * Private constructor for disallowing instantiation
	 * @link http://www.php.net/manual/en/intlbreakiterator.construct.php
	 */
	private function __construct () {}

	/**
	 * Get index of current position
	 * @link http://www.php.net/manual/en/intlbreakiterator.current.php
	 * @return int 
	 */
	public function current (): int {}

	/**
	 * Set position to the first character in the text
	 * @link http://www.php.net/manual/en/intlbreakiterator.first.php
	 * @return int 
	 */
	public function first (): int {}

	/**
	 * Advance the iterator to the first boundary following specified offset
	 * @link http://www.php.net/manual/en/intlbreakiterator.following.php
	 * @param int $offset 
	 * @return int 
	 */
	public function following (int $offset): int {}

	/**
	 * Get last error code on the object
	 * @link http://www.php.net/manual/en/intlbreakiterator.geterrorcode.php
	 * @return int 
	 */
	public function getErrorCode (): int {}

	/**
	 * Get last error message on the object
	 * @link http://www.php.net/manual/en/intlbreakiterator.geterrormessage.php
	 * @return string 
	 */
	public function getErrorMessage (): string {}

	/**
	 * Get the locale associated with the object
	 * @link http://www.php.net/manual/en/intlbreakiterator.getlocale.php
	 * @param int $type 
	 * @return string|false 
	 */
	public function getLocale (int $type): string|false {}

	/**
	 * Create iterator for navigating fragments between boundaries
	 * @link http://www.php.net/manual/en/intlbreakiterator.getpartsiterator.php
	 * @param string $type [optional] Optional key type. Possible values are:
	 * <p>
	 * IntlPartsIterator::KEY_SEQUENTIAL
	 * - The default. Sequentially increasing integers used as key.
	 * IntlPartsIterator::KEY_LEFT
	 * - Byte offset left of current part used as key.
	 * IntlPartsIterator::KEY_RIGHT
	 * - Byte offset right of current part used as key.
	 * </p>
	 * @return IntlPartsIterator 
	 */
	public function getPartsIterator (string $type = \IntlPartsIterator::KEY_SEQUENTIAL): IntlPartsIterator {}

	/**
	 * Get the text being scanned
	 * @link http://www.php.net/manual/en/intlbreakiterator.gettext.php
	 * @return string|null 
	 */
	public function getText (): ?string {}

	/**
	 * Tell whether an offset is a boundaryʼs offset
	 * @link http://www.php.net/manual/en/intlbreakiterator.isboundary.php
	 * @param int $offset 
	 * @return bool 
	 */
	public function isBoundary (int $offset): bool {}

	/**
	 * Set the iterator position to index beyond the last character
	 * @link http://www.php.net/manual/en/intlbreakiterator.last.php
	 * @return int 
	 */
	public function last (): int {}

	/**
	 * Advance the iterator the next boundary
	 * @link http://www.php.net/manual/en/intlbreakiterator.next.php
	 * @param int|null $offset [optional] 
	 * @return int 
	 */
	public function next (?int $offset = null): int {}

	/**
	 * Set the iterator position to the first boundary before an offset
	 * @link http://www.php.net/manual/en/intlbreakiterator.preceding.php
	 * @param int $offset 
	 * @return int 
	 */
	public function preceding (int $offset): int {}

	/**
	 * Set the iterator position to the boundary immediately before the current
	 * @link http://www.php.net/manual/en/intlbreakiterator.previous.php
	 * @return int 
	 */
	public function previous (): int {}

	/**
	 * Set the text being scanned
	 * @link http://www.php.net/manual/en/intlbreakiterator.settext.php
	 * @param string $text 
	 * @return bool|null 
	 */
	public function setText (string $text): ?bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): Iterator {}

}

/**
 * A subclass of IntlBreakIterator that encapsulates
 * ICU break iterators whose behavior is specified using a set of rules. This
 * is the most common kind of break iterators.
 * <p>These rules are described in the ICU Boundary Analysis
 * User Guide.</p>
 * @link http://www.php.net/manual/en/class.intlrulebasedbreakiterator.php
 */
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
	 * Create iterator from ruleset
	 * @link http://www.php.net/manual/en/intlrulebasedbreakiterator.construct.php
	 * @param string $rules 
	 * @param bool $compiled [optional] 
	 * @return string 
	 */
	public function __construct (string $rules, bool $compiled = false): string {}

	/**
	 * Get the binary form of compiled rules
	 * @link http://www.php.net/manual/en/intlrulebasedbreakiterator.getbinaryrules.php
	 * @return string|false 
	 */
	public function getBinaryRules (): string|false {}

	/**
	 * Get the rule set used to create this object
	 * @link http://www.php.net/manual/en/intlrulebasedbreakiterator.getrules.php
	 * @return string|false 
	 */
	public function getRules (): string|false {}

	/**
	 * Get the largest status value from the break rules that determined the current break position
	 * @link http://www.php.net/manual/en/intlrulebasedbreakiterator.getrulestatus.php
	 * @return int 
	 */
	public function getRuleStatus (): int {}

	/**
	 * Get the status values from the break rules that determined the current break position
	 * @link http://www.php.net/manual/en/intlrulebasedbreakiterator.getrulestatusvec.php
	 * @return array|false 
	 */
	public function getRuleStatusVec (): array|false {}

	/**
	 * Create break iterator for boundaries of combining character sequences
	 * @link http://www.php.net/manual/en/intlbreakiterator.createcharacterinstance.php
	 * @param string|null $locale [optional] 
	 * @return IntlBreakIterator|null 
	 */
	public static function createCharacterInstance (?string $locale = null): ?IntlBreakIterator {}

	/**
	 * Create break iterator for boundaries of code points
	 * @link http://www.php.net/manual/en/intlbreakiterator.createcodepointinstance.php
	 * @return IntlCodePointBreakIterator 
	 */
	public static function createCodePointInstance (): IntlCodePointBreakIterator {}

	/**
	 * Create break iterator for logically possible line breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createlineinstance.php
	 * @param string|null $locale [optional] 
	 * @return IntlBreakIterator|null 
	 */
	public static function createLineInstance (?string $locale = null): ?IntlBreakIterator {}

	/**
	 * Create break iterator for sentence breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createsentenceinstance.php
	 * @param string|null $locale [optional] 
	 * @return IntlBreakIterator|null 
	 */
	public static function createSentenceInstance (?string $locale = null): ?IntlBreakIterator {}

	/**
	 * Create break iterator for title-casing breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createtitleinstance.php
	 * @param string|null $locale [optional] 
	 * @return IntlBreakIterator|null 
	 */
	public static function createTitleInstance (?string $locale = null): ?IntlBreakIterator {}

	/**
	 * Create break iterator for word breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createwordinstance.php
	 * @param string|null $locale [optional] 
	 * @return IntlBreakIterator|null 
	 */
	public static function createWordInstance (?string $locale = null): ?IntlBreakIterator {}

	/**
	 * Get index of current position
	 * @link http://www.php.net/manual/en/intlbreakiterator.current.php
	 * @return int 
	 */
	public function current (): int {}

	/**
	 * Set position to the first character in the text
	 * @link http://www.php.net/manual/en/intlbreakiterator.first.php
	 * @return int 
	 */
	public function first (): int {}

	/**
	 * Advance the iterator to the first boundary following specified offset
	 * @link http://www.php.net/manual/en/intlbreakiterator.following.php
	 * @param int $offset 
	 * @return int 
	 */
	public function following (int $offset): int {}

	/**
	 * Get last error code on the object
	 * @link http://www.php.net/manual/en/intlbreakiterator.geterrorcode.php
	 * @return int 
	 */
	public function getErrorCode (): int {}

	/**
	 * Get last error message on the object
	 * @link http://www.php.net/manual/en/intlbreakiterator.geterrormessage.php
	 * @return string 
	 */
	public function getErrorMessage (): string {}

	/**
	 * Get the locale associated with the object
	 * @link http://www.php.net/manual/en/intlbreakiterator.getlocale.php
	 * @param int $type 
	 * @return string|false 
	 */
	public function getLocale (int $type): string|false {}

	/**
	 * Create iterator for navigating fragments between boundaries
	 * @link http://www.php.net/manual/en/intlbreakiterator.getpartsiterator.php
	 * @param string $type [optional] Optional key type. Possible values are:
	 * <p>
	 * IntlPartsIterator::KEY_SEQUENTIAL
	 * - The default. Sequentially increasing integers used as key.
	 * IntlPartsIterator::KEY_LEFT
	 * - Byte offset left of current part used as key.
	 * IntlPartsIterator::KEY_RIGHT
	 * - Byte offset right of current part used as key.
	 * </p>
	 * @return IntlPartsIterator 
	 */
	public function getPartsIterator (string $type = \IntlPartsIterator::KEY_SEQUENTIAL): IntlPartsIterator {}

	/**
	 * Get the text being scanned
	 * @link http://www.php.net/manual/en/intlbreakiterator.gettext.php
	 * @return string|null 
	 */
	public function getText (): ?string {}

	/**
	 * Tell whether an offset is a boundaryʼs offset
	 * @link http://www.php.net/manual/en/intlbreakiterator.isboundary.php
	 * @param int $offset 
	 * @return bool 
	 */
	public function isBoundary (int $offset): bool {}

	/**
	 * Set the iterator position to index beyond the last character
	 * @link http://www.php.net/manual/en/intlbreakiterator.last.php
	 * @return int 
	 */
	public function last (): int {}

	/**
	 * Advance the iterator the next boundary
	 * @link http://www.php.net/manual/en/intlbreakiterator.next.php
	 * @param int|null $offset [optional] 
	 * @return int 
	 */
	public function next (?int $offset = null): int {}

	/**
	 * Set the iterator position to the first boundary before an offset
	 * @link http://www.php.net/manual/en/intlbreakiterator.preceding.php
	 * @param int $offset 
	 * @return int 
	 */
	public function preceding (int $offset): int {}

	/**
	 * Set the iterator position to the boundary immediately before the current
	 * @link http://www.php.net/manual/en/intlbreakiterator.previous.php
	 * @return int 
	 */
	public function previous (): int {}

	/**
	 * Set the text being scanned
	 * @link http://www.php.net/manual/en/intlbreakiterator.settext.php
	 * @param string $text 
	 * @return bool|null 
	 */
	public function setText (string $text): ?bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): Iterator {}

}

/**
 * This break iterator
 * identifies the boundaries between UTF-8 code points.
 * @link http://www.php.net/manual/en/class.intlcodepointbreakiterator.php
 */
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
	 * Get last code point passed over after advancing or receding the iterator
	 * @link http://www.php.net/manual/en/intlcodepointbreakiterator.getlastcodepoint.php
	 * @return int 
	 */
	public function getLastCodePoint (): int {}

	/**
	 * Create break iterator for boundaries of combining character sequences
	 * @link http://www.php.net/manual/en/intlbreakiterator.createcharacterinstance.php
	 * @param string|null $locale [optional] 
	 * @return IntlBreakIterator|null 
	 */
	public static function createCharacterInstance (?string $locale = null): ?IntlBreakIterator {}

	/**
	 * Create break iterator for boundaries of code points
	 * @link http://www.php.net/manual/en/intlbreakiterator.createcodepointinstance.php
	 * @return IntlCodePointBreakIterator 
	 */
	public static function createCodePointInstance (): IntlCodePointBreakIterator {}

	/**
	 * Create break iterator for logically possible line breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createlineinstance.php
	 * @param string|null $locale [optional] 
	 * @return IntlBreakIterator|null 
	 */
	public static function createLineInstance (?string $locale = null): ?IntlBreakIterator {}

	/**
	 * Create break iterator for sentence breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createsentenceinstance.php
	 * @param string|null $locale [optional] 
	 * @return IntlBreakIterator|null 
	 */
	public static function createSentenceInstance (?string $locale = null): ?IntlBreakIterator {}

	/**
	 * Create break iterator for title-casing breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createtitleinstance.php
	 * @param string|null $locale [optional] 
	 * @return IntlBreakIterator|null 
	 */
	public static function createTitleInstance (?string $locale = null): ?IntlBreakIterator {}

	/**
	 * Create break iterator for word breaks
	 * @link http://www.php.net/manual/en/intlbreakiterator.createwordinstance.php
	 * @param string|null $locale [optional] 
	 * @return IntlBreakIterator|null 
	 */
	public static function createWordInstance (?string $locale = null): ?IntlBreakIterator {}

	/**
	 * Get index of current position
	 * @link http://www.php.net/manual/en/intlbreakiterator.current.php
	 * @return int 
	 */
	public function current (): int {}

	/**
	 * Set position to the first character in the text
	 * @link http://www.php.net/manual/en/intlbreakiterator.first.php
	 * @return int 
	 */
	public function first (): int {}

	/**
	 * Advance the iterator to the first boundary following specified offset
	 * @link http://www.php.net/manual/en/intlbreakiterator.following.php
	 * @param int $offset 
	 * @return int 
	 */
	public function following (int $offset): int {}

	/**
	 * Get last error code on the object
	 * @link http://www.php.net/manual/en/intlbreakiterator.geterrorcode.php
	 * @return int 
	 */
	public function getErrorCode (): int {}

	/**
	 * Get last error message on the object
	 * @link http://www.php.net/manual/en/intlbreakiterator.geterrormessage.php
	 * @return string 
	 */
	public function getErrorMessage (): string {}

	/**
	 * Get the locale associated with the object
	 * @link http://www.php.net/manual/en/intlbreakiterator.getlocale.php
	 * @param int $type 
	 * @return string|false 
	 */
	public function getLocale (int $type): string|false {}

	/**
	 * Create iterator for navigating fragments between boundaries
	 * @link http://www.php.net/manual/en/intlbreakiterator.getpartsiterator.php
	 * @param string $type [optional] Optional key type. Possible values are:
	 * <p>
	 * IntlPartsIterator::KEY_SEQUENTIAL
	 * - The default. Sequentially increasing integers used as key.
	 * IntlPartsIterator::KEY_LEFT
	 * - Byte offset left of current part used as key.
	 * IntlPartsIterator::KEY_RIGHT
	 * - Byte offset right of current part used as key.
	 * </p>
	 * @return IntlPartsIterator 
	 */
	public function getPartsIterator (string $type = \IntlPartsIterator::KEY_SEQUENTIAL): IntlPartsIterator {}

	/**
	 * Get the text being scanned
	 * @link http://www.php.net/manual/en/intlbreakiterator.gettext.php
	 * @return string|null 
	 */
	public function getText (): ?string {}

	/**
	 * Tell whether an offset is a boundaryʼs offset
	 * @link http://www.php.net/manual/en/intlbreakiterator.isboundary.php
	 * @param int $offset 
	 * @return bool 
	 */
	public function isBoundary (int $offset): bool {}

	/**
	 * Set the iterator position to index beyond the last character
	 * @link http://www.php.net/manual/en/intlbreakiterator.last.php
	 * @return int 
	 */
	public function last (): int {}

	/**
	 * Advance the iterator the next boundary
	 * @link http://www.php.net/manual/en/intlbreakiterator.next.php
	 * @param int|null $offset [optional] 
	 * @return int 
	 */
	public function next (?int $offset = null): int {}

	/**
	 * Set the iterator position to the first boundary before an offset
	 * @link http://www.php.net/manual/en/intlbreakiterator.preceding.php
	 * @param int $offset 
	 * @return int 
	 */
	public function preceding (int $offset): int {}

	/**
	 * Set the iterator position to the boundary immediately before the current
	 * @link http://www.php.net/manual/en/intlbreakiterator.previous.php
	 * @return int 
	 */
	public function previous (): int {}

	/**
	 * Set the text being scanned
	 * @link http://www.php.net/manual/en/intlbreakiterator.settext.php
	 * @param string $text 
	 * @return bool|null 
	 */
	public function setText (string $text): ?bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): Iterator {}

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
	public function getBreakIterator (): IntlBreakIterator {}

	/**
	 * {@inheritdoc}
	 */
	public function getRuleStatus () {}

	/**
	 * Get the current element
	 * @link http://www.php.net/manual/en/intliterator.current.php
	 * @return mixed 
	 */
	public function current (): mixed {}

	/**
	 * Get the current key
	 * @link http://www.php.net/manual/en/intliterator.key.php
	 * @return mixed 
	 */
	public function key (): mixed {}

	/**
	 * Move forward to the next element
	 * @link http://www.php.net/manual/en/intliterator.next.php
	 * @return void 
	 */
	public function next (): void {}

	/**
	 * Rewind the iterator to the first element
	 * @link http://www.php.net/manual/en/intliterator.rewind.php
	 * @return void 
	 */
	public function rewind (): void {}

	/**
	 * Check if current position is valid
	 * @link http://www.php.net/manual/en/intliterator.valid.php
	 * @return bool 
	 */
	public function valid (): bool {}

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
	 * @param string|null $destination_encoding [optional] 
	 * @param string|null $source_encoding [optional] 
	 * @return string|null 
	 */
	public function __construct (?string $destination_encoding = null, ?string $source_encoding = null): ?string {}

	/**
	 * Convert string from one charset to another
	 * @link http://www.php.net/manual/en/uconverter.convert.php
	 * @param string $str 
	 * @param bool $reverse [optional] 
	 * @return string|false 
	 */
	public function convert (string $str, bool $reverse = false): string|false {}

	/**
	 * Default "from" callback function
	 * @link http://www.php.net/manual/en/uconverter.fromucallback.php
	 * @param int $reason 
	 * @param array $source 
	 * @param int $codePoint 
	 * @param int $error 
	 * @return string|int|array|null 
	 */
	public function fromUCallback (int $reason, array $source, int $codePoint, int &$error): string|int|array|null {}

	/**
	 * Get the aliases of the given name
	 * @link http://www.php.net/manual/en/uconverter.getaliases.php
	 * @param string $name 
	 * @return array|false|null 
	 */
	public static function getAliases (string $name): array|false|null {}

	/**
	 * Get the available canonical converter names
	 * @link http://www.php.net/manual/en/uconverter.getavailable.php
	 * @return array 
	 */
	public static function getAvailable (): array {}

	/**
	 * Get the destination encoding
	 * @link http://www.php.net/manual/en/uconverter.getdestinationencoding.php
	 * @return string|false|null 
	 */
	public function getDestinationEncoding (): string|false|null {}

	/**
	 * Get the destination converter type
	 * @link http://www.php.net/manual/en/uconverter.getdestinationtype.php
	 * @return int|false|null 
	 */
	public function getDestinationType (): int|false|null {}

	/**
	 * Get last error code on the object
	 * @link http://www.php.net/manual/en/uconverter.geterrorcode.php
	 * @return int 
	 */
	public function getErrorCode (): int {}

	/**
	 * Get last error message on the object
	 * @link http://www.php.net/manual/en/uconverter.geterrormessage.php
	 * @return string|null 
	 */
	public function getErrorMessage (): ?string {}

	/**
	 * Get the source encoding
	 * @link http://www.php.net/manual/en/uconverter.getsourceencoding.php
	 * @return string|false|null 
	 */
	public function getSourceEncoding (): string|false|null {}

	/**
	 * Get the source converter type
	 * @link http://www.php.net/manual/en/uconverter.getsourcetype.php
	 * @return int|false|null 
	 */
	public function getSourceType (): int|false|null {}

	/**
	 * Get standards associated to converter names
	 * @link http://www.php.net/manual/en/uconverter.getstandards.php
	 * @return array|null 
	 */
	public static function getStandards (): ?array {}

	/**
	 * Get substitution chars
	 * @link http://www.php.net/manual/en/uconverter.getsubstchars.php
	 * @return string|false|null 
	 */
	public function getSubstChars (): string|false|null {}

	/**
	 * Get string representation of the callback reason
	 * @link http://www.php.net/manual/en/uconverter.reasontext.php
	 * @param int $reason 
	 * @return string 
	 */
	public static function reasonText (int $reason): string {}

	/**
	 * Set the destination encoding
	 * @link http://www.php.net/manual/en/uconverter.setdestinationencoding.php
	 * @param string $encoding 
	 * @return bool 
	 */
	public function setDestinationEncoding (string $encoding): bool {}

	/**
	 * Set the source encoding
	 * @link http://www.php.net/manual/en/uconverter.setsourceencoding.php
	 * @param string $encoding 
	 * @return bool 
	 */
	public function setSourceEncoding (string $encoding): bool {}

	/**
	 * Set the substitution chars
	 * @link http://www.php.net/manual/en/uconverter.setsubstchars.php
	 * @param string $chars 
	 * @return bool 
	 */
	public function setSubstChars (string $chars): bool {}

	/**
	 * Default "to" callback function
	 * @link http://www.php.net/manual/en/uconverter.toucallback.php
	 * @param int $reason 
	 * @param string $source 
	 * @param string $codeUnits 
	 * @param int $error 
	 * @return string|int|array|null 
	 */
	public function toUCallback (int $reason, string $source, string $codeUnits, int &$error): string|int|array|null {}

	/**
	 * Convert a string from one character encoding to another
	 * @link http://www.php.net/manual/en/uconverter.transcode.php
	 * @param string $str The string to be converted.
	 * @param string $toEncoding The desired encoding of the result.
	 * @param string $fromEncoding The current encoding used to interpret str.
	 * @param array|null $options [optional] An optional array, which may contain the following keys:
	 * <p>
	 * 'to_subst' - the substitution character to use
	 * in place of any character of str which cannot
	 * be encoded in toEncoding. If specified, it must
	 * represent a single character in the target encoding.
	 * </p>
	 * @return string|false Returns the converted string or false on failure.
	 */
	public static function transcode (string $str, string $toEncoding, string $fromEncoding, ?array $options = null): string|false {}

}

/**
 * IntlChar provides access to a number of utility
 * methods that can be used to access information about Unicode characters.
 * <p>The methods and constants adhere closely to the names and behavior used by the underlying ICU library.</p>
 * @link http://www.php.net/manual/en/class.intlchar.php
 */
class IntlChar  {
	const UNICODE_VERSION = 15.0;
	const CODEPOINT_MIN = 0;
	const CODEPOINT_MAX = 1114111;
	/**
	 * Special value that is returned by
	 * IntlChar::getNumericValue when no numeric value
	 * is defined for a code point.
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
	 * Check a binary Unicode property for a code point
	 * @link http://www.php.net/manual/en/intlchar.hasbinaryproperty.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @param int $property >The Unicode property to lookup (see the IntlChar::PROPERTY_&#42; constants).
	 * @return bool|null Returns true or false according to the binary Unicode property value for codepoint.
	 * Also false if property is out of bounds or if the Unicode version does not have data for
	 * the property at all, or not for this code point. Returns null on failure.
	 */
	public static function hasBinaryProperty (int|string $codepoint, int $property): ?bool {}

	/**
	 * Get the "age" of the code point
	 * @link http://www.php.net/manual/en/intlchar.charage.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return array|null The Unicode version number, as an array.
	 * For example, version 1.3.31.2 would be represented as [1, 3, 31, 2].
	 * Returns null on failure.
	 */
	public static function charAge (int|string $codepoint): ?array {}

	/**
	 * Get the decimal digit value of a decimal digit character
	 * @link http://www.php.net/manual/en/intlchar.chardigitvalue.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return int|null The decimal digit value of codepoint,
	 * or -1 if it is not a decimal digit character. Returns null on failure.
	 */
	public static function charDigitValue (int|string $codepoint): ?int {}

	/**
	 * Get bidirectional category value for a code point
	 * @link http://www.php.net/manual/en/intlchar.chardirection.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return int|null The bidirectional category value; one of the following constants:
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
	 * Returns null on failure.
	 */
	public static function charDirection (int|string $codepoint): ?int {}

	/**
	 * Find Unicode character by name and return its code point value
	 * @link http://www.php.net/manual/en/intlchar.charfromname.php
	 * @param string $name Full name of the Unicode character.
	 * @param int $type [optional] Which set of names to use for the lookup. Can be any of these constants:
	 * <p>
	 * IntlChar::UNICODE_CHAR_NAME (default)
	 * IntlChar::UNICODE_10_CHAR_NAME
	 * IntlChar::EXTENDED_CHAR_NAME
	 * IntlChar::CHAR_NAME_ALIAS
	 * IntlChar::CHAR_NAME_CHOICE_COUNT
	 * </p>
	 * @return int|null The Unicode value of the code point with the given name (as an int), or null if there is no such code point.
	 */
	public static function charFromName (string $name, int $type = \IntlChar::UNICODE_CHAR_NAME): ?int {}

	/**
	 * Get the "mirror-image" character for a code point
	 * @link http://www.php.net/manual/en/intlchar.charmirror.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return int|string|null Returns another Unicode code point that may serve as a mirror-image substitute, or codepoint
	 * itself if there is no such mapping or codepoint does not have the
	 * Bidi_Mirrored property.
	 * <p>>The return type is int unless the code point was passed as a UTF-8 string, in which case a string is returned. Returns null on failure.</p>
	 */
	public static function charMirror (int|string $codepoint): int|string|null {}

	/**
	 * Retrieve the name of a Unicode character
	 * @link http://www.php.net/manual/en/intlchar.charname.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @param int $type [optional] Which set of names to use for the lookup. Can be any of these constants:
	 * <p>
	 * IntlChar::UNICODE_CHAR_NAME (default)
	 * IntlChar::UNICODE_10_CHAR_NAME
	 * IntlChar::EXTENDED_CHAR_NAME
	 * IntlChar::CHAR_NAME_ALIAS
	 * IntlChar::CHAR_NAME_CHOICE_COUNT
	 * </p>
	 * @return string|null The corresponding name, or an empty string if there is no name for this character,
	 * or null if there is no such code point.
	 */
	public static function charName (int|string $codepoint, int $type = \IntlChar::UNICODE_CHAR_NAME): ?string {}

	/**
	 * Get the general category value for a code point
	 * @link http://www.php.net/manual/en/intlchar.chartype.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return int|null Returns the general category type, which may be one of the following constants:
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
	public static function charType (int|string $codepoint): ?int {}

	/**
	 * Return Unicode character by code point value
	 * @link http://www.php.net/manual/en/intlchar.chr.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return string|null A string containing the single character specified by the Unicode code point value, or null on failure.
	 */
	public static function chr (int|string $codepoint): ?string {}

	/**
	 * Get the decimal digit value of a code point for a given radix
	 * @link http://www.php.net/manual/en/intlchar.digit.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @param int $base [optional] The radix (defaults to 10).
	 * @return int|false|null Returns the numeric value represented by the character in the specified radix,
	 * or false if there is no value or if the value exceeds the radix. Returns null on failure.
	 */
	public static function digit (int|string $codepoint, int $base = 10): int|false|null {}

	/**
	 * Enumerate all assigned Unicode characters within a range
	 * @link http://www.php.net/manual/en/intlchar.enumcharnames.php
	 * @param int|string $start The first code point in the enumeration range.
	 * @param int|string $end One more than the last code point in the enumeration range (the first one after the range).
	 * @param callable $callback The function that is to be called for each character name. The following three arguments will be passed into it:
	 * <p>
	 * int $codepoint - The numeric code point value
	 * int $nameChoice - The same value as the type parameter below
	 * string $name - The name of the character
	 * </p>
	 * @param int $type [optional] Selector for which kind of names to enumerate. Can be any of these constants:
	 * <p>
	 * IntlChar::UNICODE_CHAR_NAME (default)
	 * IntlChar::UNICODE_10_CHAR_NAME
	 * IntlChar::EXTENDED_CHAR_NAME
	 * IntlChar::CHAR_NAME_ALIAS
	 * IntlChar::CHAR_NAME_CHOICE_COUNT
	 * </p>
	 * @return bool|null Returns null on success or false on failure.
	 */
	public static function enumCharNames (int|string $start, int|string $end, callable $callback, int $type = \IntlChar::UNICODE_CHAR_NAME): ?bool {}

	/**
	 * Enumerate all code points with their Unicode general categories
	 * @link http://www.php.net/manual/en/intlchar.enumchartypes.php
	 * @param callable $callback The function that is to be called for each contiguous range of code points with the same general category.
	 * The following three arguments will be passed into it:
	 * <p>
	 * int $start - The starting code point of the range
	 * int $end - The ending code point of the range
	 * int $name - The category type (one of the IntlChar::CHAR_CATEGORY_&#42; constants)
	 * </p>
	 * @return void No value is returned.
	 */
	public static function enumCharTypes (callable $callback): void {}

	/**
	 * Perform case folding on a code point
	 * @link http://www.php.net/manual/en/intlchar.foldcase.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @param int $options [optional] Either IntlChar::FOLD_CASE_DEFAULT (default)
	 * or IntlChar::FOLD_CASE_EXCLUDE_SPECIAL_I.
	 * @return int|string|null Returns the Simple_Case_Folding of the code point, if any; otherwise the code point itself on success,
	 * or null on failure.
	 */
	public static function foldCase (int|string $codepoint, int $options = \IntlChar::FOLD_CASE_DEFAULT): int|string|null {}

	/**
	 * Get character representation for a given digit and radix
	 * @link http://www.php.net/manual/en/intlchar.fordigit.php
	 * @param int $digit The number to convert to a character.
	 * @param int $base [optional] The radix (defaults to 10).
	 * @return int The character representation (as a string) of the specified digit in the specified radix.
	 */
	public static function forDigit (int $digit, int $base = 10): int {}

	/**
	 * Get the paired bracket character for a code point
	 * @link http://www.php.net/manual/en/intlchar.getbidipairedbracket.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return int|string|null Returns the paired bracket code point, or codepoint itself if there is no such mapping. Returns
	 * null on failure.
	 * <p>>The return type is int unless the code point was passed as a UTF-8 string, in which case a string is returned. Returns null on failure.</p>
	 */
	public static function getBidiPairedBracket (int|string $codepoint): int|string|null {}

	/**
	 * Get the Unicode allocation block containing a code point
	 * @link http://www.php.net/manual/en/intlchar.getblockcode.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return int|null Returns the block value for codepoint.
	 * See the IntlChar::BLOCK_CODE_&#42; constants for possible return values. Returns null on failure.
	 */
	public static function getBlockCode (int|string $codepoint): ?int {}

	/**
	 * Get the combining class of a code point
	 * @link http://www.php.net/manual/en/intlchar.getcombiningclass.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return int|null Returns the combining class of the character. Returns null on failure.
	 */
	public static function getCombiningClass (int|string $codepoint): ?int {}

	/**
	 * Get the FC_NFKC_Closure property for a code point
	 * @link http://www.php.net/manual/en/intlchar.getfc-nfkc-closure.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return string|false|null Returns the FC_NFKC_Closure property string for the codepoint, or an empty string if there is none.
	 * Returns null or false on failure.
	 */
	public static function getFC_NFKC_Closure (int|string $codepoint): string|false|null {}

	/**
	 * Get the max value for a Unicode property
	 * @link http://www.php.net/manual/en/intlchar.getintpropertymaxvalue.php
	 * @param int $property >The Unicode property to lookup (see the IntlChar::PROPERTY_&#42; constants).
	 * @return int The maximum value returned by IntlChar::getIntPropertyValue for a Unicode property.
	 * &lt;=0 if the property selector is out of range.
	 */
	public static function getIntPropertyMaxValue (int $property): int {}

	/**
	 * Get the min value for a Unicode property
	 * @link http://www.php.net/manual/en/intlchar.getintpropertyminvalue.php
	 * @param int $property >The Unicode property to lookup (see the IntlChar::PROPERTY_&#42; constants).
	 * @return int The minimum value returned by IntlChar::getIntPropertyValue for a Unicode property.
	 * 0 if the property selector is out of range.
	 */
	public static function getIntPropertyMinValue (int $property): int {}

	/**
	 * Get the value for a Unicode property for a code point
	 * @link http://www.php.net/manual/en/intlchar.getintpropertyvalue.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @param int $property >The Unicode property to lookup (see the IntlChar::PROPERTY_&#42; constants).
	 * @return int|null Returns the numeric value that is directly the property value or, for enumerated properties, corresponds to the
	 * numeric value of the enumerated constant of the respective property value enumeration type. Returns null on failure.
	 * <p>Returns 0 or 1 (for false/true) for binary Unicode properties.</p>
	 * <p>Returns a bit-mask for mask properties.</p>
	 * <p>Returns 0 if property is out of bounds or if the Unicode version does not
	 * have data for the property at all, or not for this code point.</p>
	 */
	public static function getIntPropertyValue (int|string $codepoint, int $property): ?int {}

	/**
	 * Get the numeric value for a Unicode code point
	 * @link http://www.php.net/manual/en/intlchar.getnumericvalue.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return float|null Numeric value of codepoint,
	 * or IntlChar::NO_NUMERIC_VALUE if none is defined. This
	 * constant was added in PHP 7.0.6, prior to this version the literal value
	 * (float)-123456789 may be used instead. Returns null on failure.
	 */
	public static function getNumericValue (int|string $codepoint): ?float {}

	/**
	 * Get the property constant value for a given property name
	 * @link http://www.php.net/manual/en/intlchar.getpropertyenum.php
	 * @param string $alias The property name to be matched. The name is compared using "loose matching" as described in PropertyAliases.txt.
	 * @return int Returns an IntlChar::PROPERTY_ constant value,
	 * or IntlChar::PROPERTY_INVALID_CODE if the given name does not match any property.
	 */
	public static function getPropertyEnum (string $alias): int {}

	/**
	 * Get the Unicode name for a property
	 * @link http://www.php.net/manual/en/intlchar.getpropertyname.php
	 * @param int $property >The Unicode property to lookup (see the IntlChar::PROPERTY_&#42; constants).
	 * <p>IntlChar::PROPERTY_INVALID_CODE should not be used.
	 * Also, if property is out of range, false is returned.</p>
	 * @param int $type [optional] Selector for which name to get. If out of range, false is returned.
	 * <p>All properties have a long name. Most have a short name, but some do not. Unicode allows for additional names;
	 * if present these will be returned by adding 1, 2, etc. to IntlChar::LONG_PROPERTY_NAME.</p>
	 * @return string|false Returns the name, or false if either the property or the type
	 * is out of range.
	 * <p>If a given type returns false, then all larger values of
	 * type will return false, with one exception: if false is returned for
	 * IntlChar::SHORT_PROPERTY_NAME, then IntlChar::LONG_PROPERTY_NAME
	 * (and higher) may still return a non-false value.</p>
	 */
	public static function getPropertyName (int $property, int $type = \IntlChar::LONG_PROPERTY_NAME): string|false {}

	/**
	 * Get the property value for a given value name
	 * @link http://www.php.net/manual/en/intlchar.getpropertyvalueenum.php
	 * @param int $property >The Unicode property to lookup (see the IntlChar::PROPERTY_&#42; constants).
	 * <p>If out of range, or this method doesn't work with the given value,
	 * IntlChar::PROPERTY_INVALID_CODE is returned.</p>
	 * @param string $name The value name to be matched. The name is compared using "loose matching" as described in PropertyValueAliases.txt.
	 * @return int Returns the corresponding value integer, or IntlChar::PROPERTY_INVALID_CODE if the given name
	 * does not match any value of the given property, or if the property is invalid.
	 */
	public static function getPropertyValueEnum (int $property, string $name): int {}

	/**
	 * Get the Unicode name for a property value
	 * @link http://www.php.net/manual/en/intlchar.getpropertyvaluename.php
	 * @param int $property >The Unicode property to lookup (see the IntlChar::PROPERTY_&#42; constants).
	 * <p>If out of range, or this method doesn't work with the given value, false is returned.</p>
	 * @param int $value Selector for a value for the given property. If out of range, false is returned.
	 * <p>In general, valid values range from 0 up to some maximum. There are a couple exceptions:
	 * <p>
	 * IntlChar::PROPERTY_BLOCK values begin at the non-zero value IntlChar::BLOCK_CODE_BASIC_LATIN
	 * IntlChar::PROPERTY_CANONICAL_COMBINING_CLASS values are not contiguous and range from 0..240.
	 * </p></p>
	 * @param int $type [optional] Selector for which name to get. If out of range, false is returned.
	 * <p>All values have a long name. Most have a short name, but some do not. Unicode allows for additional names;
	 * if present these will be returned by adding 1, 2, etc. to IntlChar::LONG_PROPERTY_NAME.</p>
	 * @return string|false Returns the name, or false if either the property or the type
	 * is out of range. Returns null on failure.
	 * <p>If a given type returns false, then all larger values of type
	 * will return false, with one exception: if false is returned for IntlChar::SHORT_PROPERTY_NAME,
	 * then IntlChar::LONG_PROPERTY_NAME (and higher) may still return a non-false value.</p>
	 */
	public static function getPropertyValueName (int $property, int $value, int $type = \IntlChar::LONG_PROPERTY_NAME): string|false {}

	/**
	 * Get the Unicode version
	 * @link http://www.php.net/manual/en/intlchar.getunicodeversion.php
	 * @return array An array containing the Unicode version number.
	 */
	public static function getUnicodeVersion (): array {}

	/**
	 * Check if code point is an alphanumeric character
	 * @link http://www.php.net/manual/en/intlchar.isalnum.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is an alphanumeric character, false if not. Returns null on failure.
	 */
	public static function isalnum (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is a letter character
	 * @link http://www.php.net/manual/en/intlchar.isalpha.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is a letter character, false if not. Returns null on failure.
	 */
	public static function isalpha (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is a base character
	 * @link http://www.php.net/manual/en/intlchar.isbase.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is a base character, false if not. Returns null on failure.
	 */
	public static function isbase (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is a "blank" or "horizontal space" character
	 * @link http://www.php.net/manual/en/intlchar.isblank.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is either a "blank" or "horizontal space" character, false if not. Returns null on failure.
	 */
	public static function isblank (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is a control character
	 * @link http://www.php.net/manual/en/intlchar.iscntrl.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is a control character, false if not. Returns null on failure.
	 */
	public static function iscntrl (int|string $codepoint): ?bool {}

	/**
	 * Check whether the code point is defined
	 * @link http://www.php.net/manual/en/intlchar.isdefined.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is a defined character, false if not. Returns null on failure.
	 */
	public static function isdefined (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is a digit character
	 * @link http://www.php.net/manual/en/intlchar.isdigit.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is a digit character, false if not. Returns null on failure.
	 */
	public static function isdigit (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is a graphic character
	 * @link http://www.php.net/manual/en/intlchar.isgraph.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is a "graphic" character, false if not. Returns null on failure.
	 */
	public static function isgraph (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is an ignorable character
	 * @link http://www.php.net/manual/en/intlchar.isidignorable.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is ignorable in identifiers, false if not. Returns null on failure.
	 */
	public static function isIDIgnorable (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is permissible in an identifier
	 * @link http://www.php.net/manual/en/intlchar.isidpart.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is the code point may occur in an identifier, false if not. Returns null on failure.
	 */
	public static function isIDPart (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is permissible as the first character in an identifier
	 * @link http://www.php.net/manual/en/intlchar.isidstart.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint may start an identifier, false if not. Returns null on failure.
	 */
	public static function isIDStart (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is an ISO control code
	 * @link http://www.php.net/manual/en/intlchar.isisocontrol.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is an ISO control code, false if not. Returns null on failure.
	 */
	public static function isISOControl (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is permissible in a Java identifier
	 * @link http://www.php.net/manual/en/intlchar.isjavaidpart.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint may occur in a Java identifier, false if not. Returns null on failure.
	 */
	public static function isJavaIDPart (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is permissible as the first character in a Java identifier
	 * @link http://www.php.net/manual/en/intlchar.isjavaidstart.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint may start a Java identifier, false if not. Returns null on failure.
	 */
	public static function isJavaIDStart (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is a space character according to Java
	 * @link http://www.php.net/manual/en/intlchar.isjavaspacechar.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is a space character according to Java, false if not. Returns null on failure.
	 */
	public static function isJavaSpaceChar (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is a lowercase letter
	 * @link http://www.php.net/manual/en/intlchar.islower.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is an Ll lowercase letter, false if not. Returns null on failure.
	 */
	public static function islower (int|string $codepoint): ?bool {}

	/**
	 * Check if code point has the Bidi_Mirrored property
	 * @link http://www.php.net/manual/en/intlchar.ismirrored.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint has the Bidi_Mirrored property, false if not. Returns null on failure.
	 */
	public static function isMirrored (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is a printable character
	 * @link http://www.php.net/manual/en/intlchar.isprint.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is a printable character, false if not. Returns null on failure.
	 */
	public static function isprint (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is punctuation character
	 * @link http://www.php.net/manual/en/intlchar.ispunct.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is a punctuation character, false if not. Returns null on failure.
	 */
	public static function ispunct (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is a space character
	 * @link http://www.php.net/manual/en/intlchar.isspace.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is a space character, false if not. Returns null on failure.
	 */
	public static function isspace (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is a titlecase letter
	 * @link http://www.php.net/manual/en/intlchar.istitle.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is a titlecase letter, false if not. Returns null on failure.
	 */
	public static function istitle (int|string $codepoint): ?bool {}

	/**
	 * Check if code point has the Alphabetic Unicode property
	 * @link http://www.php.net/manual/en/intlchar.isualphabetic.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint has the Alphabetic Unicode property, false if not. Returns null on failure.
	 */
	public static function isUAlphabetic (int|string $codepoint): ?bool {}

	/**
	 * Check if code point has the Lowercase Unicode property
	 * @link http://www.php.net/manual/en/intlchar.isulowercase.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint has the Lowercase Unicode property, false if not. Returns null on failure.
	 */
	public static function isULowercase (int|string $codepoint): ?bool {}

	/**
	 * Check if code point has the general category "Lu" (uppercase letter)
	 * @link http://www.php.net/manual/en/intlchar.isupper.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is an Lu uppercase letter, false if not. Returns null on failure.
	 */
	public static function isupper (int|string $codepoint): ?bool {}

	/**
	 * Check if code point has the Uppercase Unicode property
	 * @link http://www.php.net/manual/en/intlchar.isuuppercase.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint has the Uppercase Unicode property, false if not. Returns null on failure.
	 */
	public static function isUUppercase (int|string $codepoint): ?bool {}

	/**
	 * Check if code point has the White_Space Unicode property
	 * @link http://www.php.net/manual/en/intlchar.isuwhitespace.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint has the White_Space Unicode property, false if not. Returns null on failure.
	 */
	public static function isUWhiteSpace (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is a whitespace character according to ICU
	 * @link http://www.php.net/manual/en/intlchar.iswhitespace.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is a whitespace character according to ICU, false if not. Returns null on failure.
	 */
	public static function isWhitespace (int|string $codepoint): ?bool {}

	/**
	 * Check if code point is a hexadecimal digit
	 * @link http://www.php.net/manual/en/intlchar.isxdigit.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return bool|null Returns true if
	 * codepoint is a hexadecimal character, false if not. Returns null on failure.
	 */
	public static function isxdigit (int|string $codepoint): ?bool {}

	/**
	 * Return Unicode code point value of character
	 * @link http://www.php.net/manual/en/intlchar.ord.php
	 * @param int|string $character >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return int|null Returns the Unicode code point value as an integer.
	 */
	public static function ord (int|string $character): ?int {}

	/**
	 * Make Unicode character lowercase
	 * @link http://www.php.net/manual/en/intlchar.tolower.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return int|string|null Returns the Simple_Lowercase_Mapping of the code point, if any;
	 * otherwise the code point itself. Returns null on failure.
	 * <p>>The return type is int unless the code point was passed as a UTF-8 string, in which case a string is returned. Returns null on failure.</p>
	 */
	public static function tolower (int|string $codepoint): int|string|null {}

	/**
	 * Make Unicode character titlecase
	 * @link http://www.php.net/manual/en/intlchar.totitle.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return int|string|null Returns the Simple_Titlecase_Mapping of the code point, if any;
	 * otherwise the code point itself. Returns null on failure.
	 * <p>>The return type is int unless the code point was passed as a UTF-8 string, in which case a string is returned. Returns null on failure.</p>
	 */
	public static function totitle (int|string $codepoint): int|string|null {}

	/**
	 * Make Unicode character uppercase
	 * @link http://www.php.net/manual/en/intlchar.toupper.php
	 * @param int|string $codepoint >The int codepoint value (e.g. 0x2603 for U+2603 SNOWMAN), or the character encoded as a UTF-8 string (e.g. "\u{2603}")
	 * @return int|string|null Returns the Simple_Uppercase_Mapping of the code point, if any;
	 * otherwise the code point itself.
	 * <p>>The return type is int unless the code point was passed as a UTF-8 string, in which case a string is returned. Returns null on failure.</p>
	 */
	public static function toupper (int|string $codepoint): int|string|null {}

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
function intlcal_set (IntlCalendar $calendar, int $year, int $month, int $dayOfMonth = NULL, int $hour = NULL, int $minute = NULL, int $second = NULL): bool {}

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
function intlcal_clear (IntlCalendar $calendar, ?int $field = NULL): bool {}

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
function intlcal_set_minimal_days_in_first_week (IntlCalendar $calendar, int $days): bool {}

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
function intlcal_set_first_day_of_week (IntlCalendar $calendar, int $dayOfWeek): bool {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param bool $lenient
 */
function intlcal_set_lenient (IntlCalendar $calendar, bool $lenient): bool {}

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
function intlcal_set_repeated_wall_time_option (IntlCalendar $calendar, int $option): bool {}

/**
 * {@inheritdoc}
 * @param IntlCalendar $calendar
 * @param int $option
 */
function intlcal_set_skipped_wall_time_option (IntlCalendar $calendar, int $option): bool {}

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
 * Get last error code on the object
 * @link http://www.php.net/manual/en/intlcalendar.geterrorcode.php
 * @param IntlCalendar $calendar The calendar object, on the procedural style interface.
 * @return int|false An ICU error code indicating either success, failure or a warning.
 * Returns false on failure.
 */
function intlcal_get_error_code (IntlCalendar $calendar): int|false {}

/**
 * Get last error message on the object
 * @link http://www.php.net/manual/en/intlcalendar.geterrormessage.php
 * @param IntlCalendar $calendar The calendar object, on the procedural style interface.
 * @return string|false The error message associated with last error that occurred in a function call
 * on this object, or a string indicating the non-existence of an error.
 * Returns false on failure.
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
 * Create a collator
 * @link http://www.php.net/manual/en/collator.create.php
 * @param string $locale 
 * @return Collator|null Return new instance of Collator object, or null
 * on error.
 */
function collator_create (string $locale): ?Collator {}

/**
 * Compare two Unicode strings
 * @link http://www.php.net/manual/en/collator.compare.php
 * @param Collator $object 
 * @param string $string1 
 * @param string $string2 
 * @return int|false Return comparison result:
 * <p><p>
 * <br>
 * <p>
 * 1 if string1 is greater than 
 * string2 ;
 * </p>
 * <br>
 * <p>
 * 0 if string1 is equal to 
 * string2;
 * </p>
 * <br>
 * <p>
 * -1 if string1 is less than 
 * string2 .
 * </p>
 * </p>
 * Returns false on failure.</p>
 * <p>1 if string1 is greater than 
 * string2 ;</p>
 * <p>0 if string1 is equal to 
 * string2;</p>
 * <p>-1 if string1 is less than 
 * string2 .</p>
 */
function collator_compare (Collator $object, string $string1, string $string2): int|false {}

/**
 * Get collation attribute value
 * @link http://www.php.net/manual/en/collator.getattribute.php
 * @param Collator $object 
 * @param int $attribute 
 * @return int|false Attribute value, or false on failure.
 */
function collator_get_attribute (Collator $object, int $attribute): int|false {}

/**
 * Set collation attribute
 * @link http://www.php.net/manual/en/collator.setattribute.php
 * @param Collator $object 
 * @param int $attribute 
 * @param int $value 
 * @return bool Returns true on success or false on failure.
 */
function collator_set_attribute (Collator $object, int $attribute, int $value): bool {}

/**
 * Get current collation strength
 * @link http://www.php.net/manual/en/collator.getstrength.php
 * @param Collator $object 
 * @return int Returns current collation strength, or false on failure.
 */
function collator_get_strength (Collator $object): int {}

/**
 * Set collation strength
 * @link http://www.php.net/manual/en/collator.setstrength.php
 * @param Collator $object 
 * @param int $strength 
 * @return bool Returns true on success or false on failure.
 */
function collator_set_strength (Collator $object, int $strength): bool {}

/**
 * Sort array using specified collator
 * @link http://www.php.net/manual/en/collator.sort.php
 * @param Collator $object 
 * @param array $array 
 * @param int $flags [optional] 
 * @return bool Returns true on success or false on failure.
 */
function collator_sort (Collator $object, array &$array, int $flags = \Collator::SORT_REGULAR): bool {}

/**
 * Sort array using specified collator and sort keys
 * @link http://www.php.net/manual/en/collator.sortwithsortkeys.php
 * @param Collator $object 
 * @param array $array 
 * @return bool Returns true on success or false on failure.
 */
function collator_sort_with_sort_keys (Collator $object, array &$array): bool {}

/**
 * Sort array maintaining index association
 * @link http://www.php.net/manual/en/collator.asort.php
 * @param Collator $object 
 * @param array $array 
 * @param int $flags [optional] 
 * @return bool Returns true on success or false on failure.
 */
function collator_asort (Collator $object, array &$array, int $flags = \Collator::SORT_REGULAR): bool {}

/**
 * Get the locale name of the collator
 * @link http://www.php.net/manual/en/collator.getlocale.php
 * @param Collator $object 
 * @param int $type 
 * @return string|false Real locale name from which the collation data comes. If the collator was
 * instantiated from rules or an error occurred, returns false.
 */
function collator_get_locale (Collator $object, int $type): string|false {}

/**
 * Get collator's last error code
 * @link http://www.php.net/manual/en/collator.geterrorcode.php
 * @param Collator $object 
 * @return int|false Error code returned by the last Collator API function call,
 * or false on failure.
 */
function collator_get_error_code (Collator $object): int|false {}

/**
 * Get text for collator's last error code
 * @link http://www.php.net/manual/en/collator.geterrormessage.php
 * @param Collator $object 
 * @return string|false Description of an error occurred in the last Collator API function call,
 * or false on failure.
 */
function collator_get_error_message (Collator $object): string|false {}

/**
 * Get sorting key for a string
 * @link http://www.php.net/manual/en/collator.getsortkey.php
 * @param Collator $object 
 * @param string $string 
 * @return string|false Returns the collation key for the string, or false on failure.
 */
function collator_get_sort_key (Collator $object, string $string): string|false {}

/**
 * Get the last error code
 * @link http://www.php.net/manual/en/function.intl-get-error-code.php
 * @return int Error code returned by the last API function call.
 */
function intl_get_error_code (): int {}

/**
 * Get description of the last error
 * @link http://www.php.net/manual/en/function.intl-get-error-message.php
 * @return string Description of an error occurred in the last API function call.
 */
function intl_get_error_message (): string {}

/**
 * Check whether the given error code indicates failure
 * @link http://www.php.net/manual/en/function.intl-is-failure.php
 * @param int $errorCode 
 * @return bool true if it the code indicates some failure, and false
 * in case of success or a warning.
 */
function intl_is_failure (int $errorCode): bool {}

/**
 * Get symbolic name for a given error code
 * @link http://www.php.net/manual/en/function.intl-error-name.php
 * @param int $errorCode 
 * @return string The returned string will be the same as the name of the error code
 * constant.
 */
function intl_error_name (int $errorCode): string {}

/**
 * Create a date formatter
 * @link http://www.php.net/manual/en/intldateformatter.create.php
 * @param string|null $locale 
 * @param int $dateType [optional] 
 * @param int $timeType [optional] 
 * @param IntlTimeZone|DateTimeZone|string|null $timezone [optional] 
 * @param IntlCalendar|int|null $calendar [optional] 
 * @param string|null $pattern [optional] 
 * @return IntlDateFormatter|null The created IntlDateFormatter or null in case of
 * failure.
 */
function datefmt_create (?string $locale, int $dateType = \IntlDateFormatter::FULL, int $timeType = \IntlDateFormatter::FULL, IntlTimeZone|DateTimeZone|string|null $timezone = null, IntlCalendar|int|null $calendar = null, ?string $pattern = null): ?IntlDateFormatter {}

/**
 * Get the datetype used for the IntlDateFormatter
 * @link http://www.php.net/manual/en/intldateformatter.getdatetype.php
 * @param IntlDateFormatter $formatter 
 * @return int|false The current date type value of the formatter,
 * or false on failure.
 */
function datefmt_get_datetype (IntlDateFormatter $formatter): int|false {}

/**
 * Get the timetype used for the IntlDateFormatter
 * @link http://www.php.net/manual/en/intldateformatter.gettimetype.php
 * @param IntlDateFormatter $formatter 
 * @return int|false The current date type value of the formatter,
 * or false on failure.
 */
function datefmt_get_timetype (IntlDateFormatter $formatter): int|false {}

/**
 * Get the calendar type used for the IntlDateFormatter
 * @link http://www.php.net/manual/en/intldateformatter.getcalendar.php
 * @param IntlDateFormatter $formatter 
 * @return int|false The calendar
 * type being used by the formatter. Either
 * IntlDateFormatter::TRADITIONAL or
 * IntlDateFormatter::GREGORIAN.
 * Returns false on failure.
 */
function datefmt_get_calendar (IntlDateFormatter $formatter): int|false {}

/**
 * Sets the calendar type used by the formatter
 * @link http://www.php.net/manual/en/intldateformatter.setcalendar.php
 * @param IntlDateFormatter $formatter 
 * @param IntlCalendar|int|null $calendar 
 * @return bool Returns true on success or false on failure.
 */
function datefmt_set_calendar (IntlDateFormatter $formatter, IntlCalendar|int|null $calendar): bool {}

/**
 * Get the timezone-id used for the IntlDateFormatter
 * @link http://www.php.net/manual/en/intldateformatter.gettimezoneid.php
 * @param IntlDateFormatter $formatter 
 * @return string|false ID string for the time zone used by this formatter, or false on failure.
 */
function datefmt_get_timezone_id (IntlDateFormatter $formatter): string|false {}

/**
 * Get copy of formatterʼs calendar object
 * @link http://www.php.net/manual/en/intldateformatter.getcalendarobject.php
 * @param IntlDateFormatter $formatter 
 * @return IntlCalendar|false|null A copy of the internal calendar object used by this formatter,
 * or null if none has been set, or false on failure.
 */
function datefmt_get_calendar_object (IntlDateFormatter $formatter): IntlCalendar|false|null {}

/**
 * Get formatterʼs timezone
 * @link http://www.php.net/manual/en/intldateformatter.gettimezone.php
 * @param IntlDateFormatter $formatter 
 * @return IntlTimeZone|false The associated IntlTimeZone
 * object or false on failure.
 */
function datefmt_get_timezone (IntlDateFormatter $formatter): IntlTimeZone|false {}

/**
 * Sets formatterʼs timezone
 * @link http://www.php.net/manual/en/intldateformatter.settimezone.php
 * @param IntlDateFormatter $formatter The formatter resource.
 * @param IntlTimeZone|DateTimeZone|string|null $timezone The timezone to use for this formatter. This can be specified in the
 * following forms:
 * <p>null, in which case the default timezone will be used, as specified in
 * the ini setting date.timezone or
 * through the function date_default_timezone_set and as
 * returned by date_default_timezone_get.</p>
 * <p>An IntlTimeZone, which will be used directly.</p>
 * <p>A DateTimeZone. Its identifier will be extracted
 * and an ICU timezone object will be created; the timezone will be backed
 * by ICUʼs database, not PHPʼs.</p>
 * <p>A string, which should be a valid ICU timezone identifier.
 * See IntlTimeZone::createTimeZoneIDEnumeration. Raw
 * offsets such as "GMT+08:30" are also accepted.</p>
 * @return bool|null Returns null on success and false on failure.
 */
function datefmt_set_timezone (IntlDateFormatter $formatter, IntlTimeZone|DateTimeZone|string|null $timezone): ?bool {}

/**
 * Set the pattern used for the IntlDateFormatter
 * @link http://www.php.net/manual/en/intldateformatter.setpattern.php
 * @param IntlDateFormatter $formatter 
 * @param string $pattern 
 * @return bool Returns true on success or false on failure.
 * Bad formatstrings are usually the cause of the failure.
 */
function datefmt_set_pattern (IntlDateFormatter $formatter, string $pattern): bool {}

/**
 * Get the pattern used for the IntlDateFormatter
 * @link http://www.php.net/manual/en/intldateformatter.getpattern.php
 * @param IntlDateFormatter $formatter 
 * @return string|false The pattern string being used to format/parse, or false on failure.
 */
function datefmt_get_pattern (IntlDateFormatter $formatter): string|false {}

/**
 * Get the locale used by formatter
 * @link http://www.php.net/manual/en/intldateformatter.getlocale.php
 * @param IntlDateFormatter $formatter 
 * @param int $type [optional] 
 * @return string|false The locale of this formatter, or false on failure.
 */
function datefmt_get_locale (IntlDateFormatter $formatter, int $type = ULOC_ACTUAL_LOCALE): string|false {}

/**
 * Set the leniency of the parser
 * @link http://www.php.net/manual/en/intldateformatter.setlenient.php
 * @param IntlDateFormatter $formatter 
 * @param bool $lenient 
 * @return void Returns true on success or false on failure.
 */
function datefmt_set_lenient (IntlDateFormatter $formatter, bool $lenient): void {}

/**
 * Get the lenient used for the IntlDateFormatter
 * @link http://www.php.net/manual/en/intldateformatter.islenient.php
 * @param IntlDateFormatter $formatter 
 * @return bool true if parser is lenient, false if parser is strict. By default the parser is lenient.
 */
function datefmt_is_lenient (IntlDateFormatter $formatter): bool {}

/**
 * Format the date/time value as a string
 * @link http://www.php.net/manual/en/intldateformatter.format.php
 * @param IntlDateFormatter $formatter 
 * @param IntlCalendar|DateTimeInterface|array|string|int|float $datetime 
 * @return string|false The formatted string or, if an error occurred, false.
 */
function datefmt_format (IntlDateFormatter $formatter, IntlCalendar|DateTimeInterface|array|string|int|float $datetime): string|false {}

/**
 * Formats an object
 * @link http://www.php.net/manual/en/intldateformatter.formatobject.php
 * @param IntlCalendar|DateTimeInterface $datetime An object of type IntlCalendar or
 * DateTime. The timezone information in the object
 * will be used.
 * @param array|int|string|null $format [optional] How to format the date/time. This can either be an array with
 * two elements (first the date style, then the time style, these being one
 * of the constants IntlDateFormatter::NONE,
 * IntlDateFormatter::SHORT,
 * IntlDateFormatter::MEDIUM,
 * IntlDateFormatter::LONG,
 * IntlDateFormatter::FULL), an int with
 * the value of one of these constants (in which case it will be used both
 * for the time and the date) or a string with the format
 * described in the ICU
 * documentation. If null, the default style will be used.
 * @param string|null $locale [optional] The locale to use, or null to use the default one.
 * @return string|false A string with result or false on failure.
 */
function datefmt_format_object (IntlCalendar|DateTimeInterface $datetime, array|int|string|null $format = null, ?string $locale = null): string|false {}

/**
 * Parse string to a timestamp value
 * @link http://www.php.net/manual/en/intldateformatter.parse.php
 * @param IntlDateFormatter $formatter 
 * @param string $string 
 * @param int $offset [optional] 
 * @return int|float|false Timestamp of parsed value, or false if value cannot be parsed.
 */
function datefmt_parse (IntlDateFormatter $formatter, string $string, int &$offset = null): int|float|false {}

/**
 * Parse string to a field-based time value
 * @link http://www.php.net/manual/en/intldateformatter.localtime.php
 * @param IntlDateFormatter $formatter 
 * @param string $string 
 * @param int $offset [optional] 
 * @return array|false Localtime compatible array of integers : contains 24 hour clock value in tm_hour field,
 * or false on failure.
 */
function datefmt_localtime (IntlDateFormatter $formatter, string $string, int &$offset = null): array|false {}

/**
 * Get the error code from last operation
 * @link http://www.php.net/manual/en/intldateformatter.geterrorcode.php
 * @param IntlDateFormatter $formatter 
 * @return int The error code, one of UErrorCode values. Initial value is U_ZERO_ERROR.
 */
function datefmt_get_error_code (IntlDateFormatter $formatter): int {}

/**
 * Get the error text from the last operation
 * @link http://www.php.net/manual/en/intldateformatter.geterrormessage.php
 * @param IntlDateFormatter $formatter 
 * @return string Description of the last error.
 */
function datefmt_get_error_message (IntlDateFormatter $formatter): string {}

/**
 * Create a number formatter
 * @link http://www.php.net/manual/en/numberformatter.create.php
 * @param string $locale 
 * @param int $style 
 * @param string|null $pattern [optional] 
 * @return NumberFormatter|null Returns NumberFormatter object or null on error.
 */
function numfmt_create (string $locale, int $style, ?string $pattern = null): ?NumberFormatter {}

/**
 * Format a number
 * @link http://www.php.net/manual/en/numberformatter.format.php
 * @param NumberFormatter $formatter 
 * @param int|float $num 
 * @param int $type [optional] 
 * @return string|false Returns the string containing formatted value, or false on error.
 */
function numfmt_format (NumberFormatter $formatter, int|float $num, int $type = \NumberFormatter::TYPE_DEFAULT): string|false {}

/**
 * Parse a number
 * @link http://www.php.net/manual/en/numberformatter.parse.php
 * @param NumberFormatter $formatter 
 * @param string $string 
 * @param int $type [optional] 
 * @param int $offset [optional] 
 * @return int|float|false The value of the parsed number or false on error.
 */
function numfmt_parse (NumberFormatter $formatter, string $string, int $type = \NumberFormatter::TYPE_DOUBLE, int &$offset = null): int|float|false {}

/**
 * Format a currency value
 * @link http://www.php.net/manual/en/numberformatter.formatcurrency.php
 * @param NumberFormatter $formatter 
 * @param float $amount 
 * @param string $currency 
 * @return string|false String representing the formatted currency value, or false on failure.
 */
function numfmt_format_currency (NumberFormatter $formatter, float $amount, string $currency): string|false {}

/**
 * Parse a currency number
 * @link http://www.php.net/manual/en/numberformatter.parsecurrency.php
 * @param NumberFormatter $formatter 
 * @param string $string 
 * @param string $currency 
 * @param int $offset [optional] 
 * @return float|false The parsed numeric value or false on error.
 */
function numfmt_parse_currency (NumberFormatter $formatter, string $string, string &$currency, int &$offset = null): float|false {}

/**
 * Set an attribute
 * @link http://www.php.net/manual/en/numberformatter.setattribute.php
 * @param NumberFormatter $formatter 
 * @param int $attribute 
 * @param int|float $value 
 * @return bool Returns true on success or false on failure.
 */
function numfmt_set_attribute (NumberFormatter $formatter, int $attribute, int|float $value): bool {}

/**
 * Get an attribute
 * @link http://www.php.net/manual/en/numberformatter.getattribute.php
 * @param NumberFormatter $formatter 
 * @param int $attribute 
 * @return int|float|false Return attribute value on success, or false on error.
 */
function numfmt_get_attribute (NumberFormatter $formatter, int $attribute): int|float|false {}

/**
 * Set a text attribute
 * @link http://www.php.net/manual/en/numberformatter.settextattribute.php
 * @param NumberFormatter $formatter 
 * @param int $attribute 
 * @param string $value 
 * @return bool Returns true on success or false on failure.
 */
function numfmt_set_text_attribute (NumberFormatter $formatter, int $attribute, string $value): bool {}

/**
 * Get a text attribute
 * @link http://www.php.net/manual/en/numberformatter.gettextattribute.php
 * @param NumberFormatter $formatter 
 * @param int $attribute 
 * @return string|false Return attribute value on success, or false on error.
 */
function numfmt_get_text_attribute (NumberFormatter $formatter, int $attribute): string|false {}

/**
 * Set a symbol value
 * @link http://www.php.net/manual/en/numberformatter.setsymbol.php
 * @param NumberFormatter $formatter 
 * @param int $symbol 
 * @param string $value 
 * @return bool Returns true on success or false on failure.
 */
function numfmt_set_symbol (NumberFormatter $formatter, int $symbol, string $value): bool {}

/**
 * Get a symbol value
 * @link http://www.php.net/manual/en/numberformatter.getsymbol.php
 * @param NumberFormatter $formatter 
 * @param int $symbol 
 * @return string|false The symbol string or false on error.
 */
function numfmt_get_symbol (NumberFormatter $formatter, int $symbol): string|false {}

/**
 * Set formatter pattern
 * @link http://www.php.net/manual/en/numberformatter.setpattern.php
 * @param NumberFormatter $formatter 
 * @param string $pattern 
 * @return bool Returns true on success or false on failure.
 */
function numfmt_set_pattern (NumberFormatter $formatter, string $pattern): bool {}

/**
 * Get formatter pattern
 * @link http://www.php.net/manual/en/numberformatter.getpattern.php
 * @param NumberFormatter $formatter 
 * @return string|false Pattern string that is used by the formatter, or false if an error happens.
 */
function numfmt_get_pattern (NumberFormatter $formatter): string|false {}

/**
 * Get formatter locale
 * @link http://www.php.net/manual/en/numberformatter.getlocale.php
 * @param NumberFormatter $formatter 
 * @param int $type [optional] 
 * @return string|false The locale name used to create the formatter, or false on failure.
 */
function numfmt_get_locale (NumberFormatter $formatter, int $type = ULOC_ACTUAL_LOCALE): string|false {}

/**
 * Get formatter's last error code
 * @link http://www.php.net/manual/en/numberformatter.geterrorcode.php
 * @param NumberFormatter $formatter 
 * @return int Returns error code from last formatter call.
 */
function numfmt_get_error_code (NumberFormatter $formatter): int {}

/**
 * Get formatter's last error message
 * @link http://www.php.net/manual/en/numberformatter.geterrormessage.php
 * @param NumberFormatter $formatter 
 * @return string Returns error message from last formatter call.
 */
function numfmt_get_error_message (NumberFormatter $formatter): string {}

/**
 * Get string length in grapheme units
 * @link http://www.php.net/manual/en/function.grapheme-strlen.php
 * @param string $string 
 * @return int|false|null The length of the string on success, or false on failure.
 */
function grapheme_strlen (string $string): int|false|null {}

/**
 * Find position (in grapheme units) of first occurrence of a string
 * @link http://www.php.net/manual/en/function.grapheme-strpos.php
 * @param string $haystack 
 * @param string $needle 
 * @param int $offset [optional] 
 * @return int|false Returns the position as an integer. If needle is not found, grapheme_strpos will return false.
 */
function grapheme_strpos (string $haystack, string $needle, int $offset = null): int|false {}

/**
 * Find position (in grapheme units) of first occurrence of a case-insensitive string
 * @link http://www.php.net/manual/en/function.grapheme-stripos.php
 * @param string $haystack 
 * @param string $needle 
 * @param int $offset [optional] 
 * @return int|false Returns the position as an integer. If needle is not found, grapheme_stripos will return false.
 */
function grapheme_stripos (string $haystack, string $needle, int $offset = null): int|false {}

/**
 * Find position (in grapheme units) of last occurrence of a string
 * @link http://www.php.net/manual/en/function.grapheme-strrpos.php
 * @param string $haystack 
 * @param string $needle 
 * @param int $offset [optional] 
 * @return int|false Returns the position as an integer. If needle is not found, grapheme_strrpos will return false.
 */
function grapheme_strrpos (string $haystack, string $needle, int $offset = null): int|false {}

/**
 * Find position (in grapheme units) of last occurrence of a case-insensitive string
 * @link http://www.php.net/manual/en/function.grapheme-strripos.php
 * @param string $haystack 
 * @param string $needle 
 * @param int $offset [optional] 
 * @return int|false Returns the position as an integer. If needle is not found, grapheme_strripos will return false.
 */
function grapheme_strripos (string $haystack, string $needle, int $offset = null): int|false {}

/**
 * Return part of a string
 * @link http://www.php.net/manual/en/function.grapheme-substr.php
 * @param string $string 
 * @param int $offset 
 * @param int|null $length [optional] 
 * @return string|false Returns the extracted part of string, or false on failure.
 */
function grapheme_substr (string $string, int $offset, ?int $length = null): string|false {}

/**
 * Returns part of haystack string from the first occurrence of needle to the end of haystack
 * @link http://www.php.net/manual/en/function.grapheme-strstr.php
 * @param string $haystack 
 * @param string $needle 
 * @param bool $beforeNeedle [optional] 
 * @return string|false Returns the portion of haystack, or false if needle is not found.
 */
function grapheme_strstr (string $haystack, string $needle, bool $beforeNeedle = false): string|false {}

/**
 * Returns part of haystack string from the first occurrence of case-insensitive needle to the end of haystack
 * @link http://www.php.net/manual/en/function.grapheme-stristr.php
 * @param string $haystack 
 * @param string $needle 
 * @param bool $beforeNeedle [optional] 
 * @return string|false Returns the portion of haystack, or false if needle is not found.
 */
function grapheme_stristr (string $haystack, string $needle, bool $beforeNeedle = false): string|false {}

/**
 * Function to extract a sequence of default grapheme clusters from a text buffer, which must be encoded in UTF-8
 * @link http://www.php.net/manual/en/function.grapheme-extract.php
 * @param string $haystack 
 * @param int $size 
 * @param int $type [optional] 
 * @param int $offset [optional] 
 * @param int $next [optional] 
 * @return string|false A string starting at offset offset and ending on a default grapheme cluster
 * boundary that conforms to the size and type specified,
 * or false on failure.
 */
function grapheme_extract (string $haystack, int $size, int $type = GRAPHEME_EXTR_COUNT, int $offset = null, int &$next = null): string|false {}

/**
 * Convert domain name to IDNA ASCII form
 * @link http://www.php.net/manual/en/function.idn-to-ascii.php
 * @param string $domain 
 * @param int $flags [optional] 
 * @param int $variant [optional] 
 * @param array $idna_info [optional] 
 * @return string|false The domain name encoded in ASCII-compatible form, or false on failure
 */
function idn_to_ascii (string $domain, int $flags = IDNA_DEFAULT, int $variant = INTL_IDNA_VARIANT_UTS46, array &$idna_info = null): string|false {}

/**
 * Convert domain name from IDNA ASCII to Unicode
 * @link http://www.php.net/manual/en/function.idn-to-utf8.php
 * @param string $domain 
 * @param int $flags [optional] 
 * @param int $variant [optional] 
 * @param array $idna_info [optional] 
 * @return string|false The domain name in Unicode, encoded in UTF-8, or false on failure
 */
function idn_to_utf8 (string $domain, int $flags = IDNA_DEFAULT, int $variant = INTL_IDNA_VARIANT_UTS46, array &$idna_info = null): string|false {}

/**
 * Gets the default locale value from the INTL global 'default_locale'
 * @link http://www.php.net/manual/en/locale.getdefault.php
 * @return string The current runtime locale
 */
function locale_get_default (): string {}

/**
 * Sets the default runtime locale
 * @link http://www.php.net/manual/en/locale.setdefault.php
 * @param string $locale 
 * @return bool Returns true.
 */
function locale_set_default (string $locale): bool {}

/**
 * Gets the primary language for the input locale
 * @link http://www.php.net/manual/en/locale.getprimarylanguage.php
 * @param string $locale 
 * @return string|null The language code associated with the language.
 * <p>>Returns null when the length of locale exceeds INTL_MAX_LOCALE_LEN.</p>
 */
function locale_get_primary_language (string $locale): ?string {}

/**
 * Gets the script for the input locale
 * @link http://www.php.net/manual/en/locale.getscript.php
 * @param string $locale 
 * @return string|null The script subtag for the locale or null if not present
 */
function locale_get_script (string $locale): ?string {}

/**
 * Gets the region for the input locale
 * @link http://www.php.net/manual/en/locale.getregion.php
 * @param string $locale 
 * @return string|null The region subtag for the locale or null if not present
 * <p>>Returns null when the length of locale exceeds INTL_MAX_LOCALE_LEN.</p>
 */
function locale_get_region (string $locale): ?string {}

/**
 * Gets the keywords for the input locale
 * @link http://www.php.net/manual/en/locale.getkeywords.php
 * @param string $locale 
 * @return array|false|null Associative array containing the keyword-value pairs for this locale
 * <p>>Returns null when the length of locale exceeds INTL_MAX_LOCALE_LEN.</p>
 */
function locale_get_keywords (string $locale): array|false|null {}

/**
 * Returns an appropriately localized display name for script of the input locale
 * @link http://www.php.net/manual/en/locale.getdisplayscript.php
 * @param string $locale 
 * @param string|null $displayLocale [optional] 
 * @return string|false Display name of the script for the locale in the format appropriate for
 * displayLocale, or false on failure.
 */
function locale_get_display_script (string $locale, ?string $displayLocale = null): string|false {}

/**
 * Returns an appropriately localized display name for region of the input locale
 * @link http://www.php.net/manual/en/locale.getdisplayregion.php
 * @param string $locale 
 * @param string|null $displayLocale [optional] 
 * @return string|false Display name of the region for the locale in the format appropriate for
 * displayLocale, or false on failure.
 */
function locale_get_display_region (string $locale, ?string $displayLocale = null): string|false {}

/**
 * Returns an appropriately localized display name for the input locale
 * @link http://www.php.net/manual/en/locale.getdisplayname.php
 * @param string $locale 
 * @param string|null $displayLocale [optional] 
 * @return string|false Display name of the locale in the format appropriate for displayLocale, or false on failure.
 */
function locale_get_display_name (string $locale, ?string $displayLocale = null): string|false {}

/**
 * Returns an appropriately localized display name for language of the inputlocale
 * @link http://www.php.net/manual/en/locale.getdisplaylanguage.php
 * @param string $locale 
 * @param string|null $displayLocale [optional] 
 * @return string|false Display name of the language for the locale in the format appropriate for
 * displayLocale, or false on failure.
 */
function locale_get_display_language (string $locale, ?string $displayLocale = null): string|false {}

/**
 * Returns an appropriately localized display name for variants of the input locale
 * @link http://www.php.net/manual/en/locale.getdisplayvariant.php
 * @param string $locale 
 * @param string|null $displayLocale [optional] 
 * @return string|false Display name of the variant for the locale in the format appropriate for
 * displayLocale, or false on failure.
 */
function locale_get_display_variant (string $locale, ?string $displayLocale = null): string|false {}

/**
 * Returns a correctly ordered and delimited locale ID
 * @link http://www.php.net/manual/en/locale.composelocale.php
 * @param array $subtags 
 * @return string|false The corresponding locale identifier, or false when subtags is empty.
 */
function locale_compose (array $subtags): string|false {}

/**
 * Returns a key-value array of locale ID subtag elements
 * @link http://www.php.net/manual/en/locale.parselocale.php
 * @param string $locale 
 * @return array|null Returns an array containing a list of key-value pairs, where the keys
 * identify the particular locale ID subtags, and the values are the
 * associated subtag values. The array will be ordered as the locale id
 * subtags e.g. in the locale id if variants are '-varX-varY-varZ' then the
 * returned array will have variant0=&gt;varX , variant1=&gt;varY ,
 * variant2=&gt;varZ
 * <p>Returns null when the length of locale exceeds
 * INTL_MAX_LOCALE_LEN.</p>
 */
function locale_parse (string $locale): ?array {}

/**
 * Gets the variants for the input locale
 * @link http://www.php.net/manual/en/locale.getallvariants.php
 * @param string $locale 
 * @return array|null The <p>null if not present
 * <p>>Returns null when the length of locale exceeds INTL_MAX_LOCALE_LEN.</p>
 */
function locale_get_all_variants (string $locale): ?array {}

/**
 * Checks if a language tag filter matches with locale
 * @link http://www.php.net/manual/en/locale.filtermatches.php
 * @param string $languageTag 
 * @param string $locale 
 * @param bool $canonicalize [optional] 
 * @return bool|null true if locale matches languageTag false otherwise.
 * <p>>Returns null when the length of locale exceeds INTL_MAX_LOCALE_LEN.</p>
 */
function locale_filter_matches (string $languageTag, string $locale, bool $canonicalize = false): ?bool {}

/**
 * Canonicalize the locale string
 * @link http://www.php.net/manual/en/locale.canonicalize.php
 * @param string $locale 
 * @return string|null Canonicalized locale string.
 * <p>>Returns null when the length of locale exceeds INTL_MAX_LOCALE_LEN.</p>
 */
function locale_canonicalize (string $locale): ?string {}

/**
 * Searches the language tag list for the best match to the language
 * @link http://www.php.net/manual/en/locale.lookup.php
 * @param array $languageTag 
 * @param string $locale 
 * @param bool $canonicalize [optional] 
 * @param string|null $defaultLocale [optional] 
 * @return string|null The closest matching language tag or default value.
 * <p>>Returns null when the length of locale exceeds INTL_MAX_LOCALE_LEN.</p>
 */
function locale_lookup (array $languageTag, string $locale, bool $canonicalize = false, ?string $defaultLocale = null): ?string {}

/**
 * Tries to find out best available locale based on HTTP "Accept-Language" header
 * @link http://www.php.net/manual/en/locale.acceptfromhttp.php
 * @param string $header 
 * @return string|false The corresponding locale identifier.
 * <p>Returns false when the length of header exceeds
 * INTL_MAX_LOCALE_LEN.</p>
 */
function locale_accept_from_http (string $header): string|false {}

/**
 * Constructs a new Message Formatter
 * @link http://www.php.net/manual/en/messageformatter.create.php
 * @param string $locale 
 * @param string $pattern 
 * @return MessageFormatter|null The formatter object, or null on failure.
 */
function msgfmt_create (string $locale, string $pattern): ?MessageFormatter {}

/**
 * Format the message
 * @link http://www.php.net/manual/en/messageformatter.format.php
 * @param MessageFormatter $formatter 
 * @param array $values 
 * @return string|false The formatted string, or false if an error occurred
 */
function msgfmt_format (MessageFormatter $formatter, array $values): string|false {}

/**
 * Quick format message
 * @link http://www.php.net/manual/en/messageformatter.formatmessage.php
 * @param string $locale 
 * @param string $pattern 
 * @param array $values 
 * @return string|false The formatted pattern string or false if an error occurred
 */
function msgfmt_format_message (string $locale, string $pattern, array $values): string|false {}

/**
 * Parse input string according to pattern
 * @link http://www.php.net/manual/en/messageformatter.parse.php
 * @param MessageFormatter $formatter 
 * @param string $string 
 * @return array|false An array containing the items extracted, or false on error
 */
function msgfmt_parse (MessageFormatter $formatter, string $string): array|false {}

/**
 * Quick parse input string
 * @link http://www.php.net/manual/en/messageformatter.parsemessage.php
 * @param string $locale 
 * @param string $pattern 
 * @param string $message 
 * @return array|false An array containing items extracted, or false on error
 */
function msgfmt_parse_message (string $locale, string $pattern, string $message): array|false {}

/**
 * Set the pattern used by the formatter
 * @link http://www.php.net/manual/en/messageformatter.setpattern.php
 * @param MessageFormatter $formatter 
 * @param string $pattern 
 * @return bool Returns true on success or false on failure.
 */
function msgfmt_set_pattern (MessageFormatter $formatter, string $pattern): bool {}

/**
 * Get the pattern used by the formatter
 * @link http://www.php.net/manual/en/messageformatter.getpattern.php
 * @param MessageFormatter $formatter 
 * @return string|false The pattern string for this message formatter, or false on failure.
 */
function msgfmt_get_pattern (MessageFormatter $formatter): string|false {}

/**
 * Get the locale for which the formatter was created
 * @link http://www.php.net/manual/en/messageformatter.getlocale.php
 * @param MessageFormatter $formatter 
 * @return string The locale name
 */
function msgfmt_get_locale (MessageFormatter $formatter): string {}

/**
 * Get the error code from last operation
 * @link http://www.php.net/manual/en/messageformatter.geterrorcode.php
 * @param MessageFormatter $formatter 
 * @return int The error code, one of UErrorCode values. Initial value is U_ZERO_ERROR.
 */
function msgfmt_get_error_code (MessageFormatter $formatter): int {}

/**
 * Get the error text from the last operation
 * @link http://www.php.net/manual/en/messageformatter.geterrormessage.php
 * @param MessageFormatter $formatter 
 * @return string Description of the last error.
 */
function msgfmt_get_error_message (MessageFormatter $formatter): string {}

/**
 * Normalizes the input provided and returns the normalized string
 * @link http://www.php.net/manual/en/normalizer.normalize.php
 * @param string $string 
 * @param int $form [optional] 
 * @return string|false The normalized string or false if an error occurred.
 */
function normalizer_normalize (string $string, int $form = \Normalizer::FORM_C): string|false {}

/**
 * Checks if the provided string is already in the specified normalization
 * form
 * @link http://www.php.net/manual/en/normalizer.isnormalized.php
 * @param string $string 
 * @param int $form [optional] 
 * @return bool true if normalized, false otherwise or if there an error
 */
function normalizer_is_normalized (string $string, int $form = \Normalizer::FORM_C): bool {}

/**
 * Gets the Decomposition_Mapping property for the given UTF-8 encoded code point
 * @link http://www.php.net/manual/en/normalizer.getrawdecomposition.php
 * @param string $string The input string, which should be a single, UTF-8 encoded, code point.
 * @param int $form [optional] 
 * @return string|null Returns a string containing the Decomposition_Mapping property, if present in the UCD.
 * <p>Returns null if there is no Decomposition_Mapping property for the character.</p>
 */
function normalizer_get_raw_decomposition (string $string, int $form = \Normalizer::FORM_C): ?string {}

/**
 * Create a resource bundle
 * @link http://www.php.net/manual/en/resourcebundle.create.php
 * @param string|null $locale 
 * @param string|null $bundle 
 * @param bool $fallback [optional] 
 * @return ResourceBundle|null Returns ResourceBundle object or null on error.
 */
function resourcebundle_create (?string $locale, ?string $bundle, bool $fallback = true): ?ResourceBundle {}

/**
 * Get data from the bundle
 * @link http://www.php.net/manual/en/resourcebundle.get.php
 * @param ResourceBundle $bundle 
 * @param string|int $index 
 * @param bool $fallback [optional] 
 * @return mixed Returns the data located at the index or null on error. Strings, integers and binary data strings
 * are returned as corresponding PHP types, integer array is returned as PHP array. Complex types are
 * returned as ResourceBundle object.
 */
function resourcebundle_get (ResourceBundle $bundle, string|int $index, bool $fallback = true): mixed {}

/**
 * Get number of elements in the bundle
 * @link http://www.php.net/manual/en/resourcebundle.count.php
 * @param ResourceBundle $bundle 
 * @return int Returns number of elements in the bundle.
 */
function resourcebundle_count (ResourceBundle $bundle): int {}

/**
 * Get supported locales
 * @link http://www.php.net/manual/en/resourcebundle.locales.php
 * @param string $bundle 
 * @return array|false Returns the list of locales supported by the bundle, or false on failure.
 */
function resourcebundle_locales (string $bundle): array|false {}

/**
 * Get bundle's last error code
 * @link http://www.php.net/manual/en/resourcebundle.geterrorcode.php
 * @param ResourceBundle $bundle 
 * @return int Returns error code from last bundle object call.
 */
function resourcebundle_get_error_code (ResourceBundle $bundle): int {}

/**
 * Get bundle's last error message
 * @link http://www.php.net/manual/en/resourcebundle.geterrormessage.php
 * @param ResourceBundle $bundle 
 * @return string Returns error message from last bundle object's call.
 */
function resourcebundle_get_error_message (ResourceBundle $bundle): string {}

/**
 * Get the number of IDs in the equivalency group that includes the given ID
 * @link http://www.php.net/manual/en/intltimezone.countequivalentids.php
 * @param string $timezoneId 
 * @return int|false 
 */
function intltz_count_equivalent_ids (string $timezoneId): int|false {}

/**
 * Create a new copy of the default timezone for this host
 * @link http://www.php.net/manual/en/intltimezone.createdefault.php
 * @return IntlTimeZone 
 */
function intltz_create_default (): IntlTimeZone {}

/**
 * Get an enumeration over time zone IDs associated with the
 * given country or offset
 * @link http://www.php.net/manual/en/intltimezone.createenumeration.php
 * @param IntlTimeZone|string|int|float|null $countryOrRawOffset [optional] 
 * @return IntlIterator|false 
 */
function intltz_create_enumeration (IntlTimeZone|string|int|float|null $countryOrRawOffset = null): IntlIterator|false {}

/**
 * Create a timezone object for the given ID
 * @link http://www.php.net/manual/en/intltimezone.createtimezone.php
 * @param string $timezoneId 
 * @return IntlTimeZone|null 
 */
function intltz_create_time_zone (string $timezoneId): ?IntlTimeZone {}

/**
 * Get an enumeration over system time zone IDs with the given filter conditions
 * @link http://www.php.net/manual/en/intltimezone.createtimezoneidenumeration.php
 * @param int $type 
 * @param string|null $region [optional] 
 * @param int|null $rawOffset [optional] 
 * @return IntlIterator|false Returns IntlIterator or false on failure.
 */
function intltz_create_time_zone_id_enumeration (int $type, ?string $region = null, ?int $rawOffset = null): IntlIterator|false {}

/**
 * Create a timezone object from DateTimeZone
 * @link http://www.php.net/manual/en/intltimezone.fromdatetimezone.php
 * @param DateTimeZone $timezone 
 * @return IntlTimeZone|null 
 */
function intltz_from_date_time_zone (DateTimeZone $timezone): ?IntlTimeZone {}

/**
 * Get the canonical system timezone ID or the normalized custom time zone ID for the given time zone ID
 * @link http://www.php.net/manual/en/intltimezone.getcanonicalid.php
 * @param string $timezoneId 
 * @param bool $isSystemId [optional] 
 * @return string|false 
 */
function intltz_get_canonical_id (string $timezoneId, bool &$isSystemId = null): string|false {}

/**
 * Get a name of this time zone suitable for presentation to the user
 * @link http://www.php.net/manual/en/intltimezone.getdisplayname.php
 * @param IntlTimeZone $timezone 
 * @param bool $dst [optional] 
 * @param int $style [optional] 
 * @param string|null $locale [optional] 
 * @return string|false 
 */
function intltz_get_display_name (IntlTimeZone $timezone, bool $dst = false, int $style = \IntlTimeZone::DISPLAY_LONG, ?string $locale = null): string|false {}

/**
 * Get the amount of time to be added to local standard time to get local wall clock time
 * @link http://www.php.net/manual/en/intltimezone.getdstsavings.php
 * @param IntlTimeZone $timezone 
 * @return int 
 */
function intltz_get_dst_savings (IntlTimeZone $timezone): int {}

/**
 * Get an ID in the equivalency group that includes the given ID
 * @link http://www.php.net/manual/en/intltimezone.getequivalentid.php
 * @param string $timezoneId 
 * @param int $offset 
 * @return string|false 
 */
function intltz_get_equivalent_id (string $timezoneId, int $offset): string|false {}

/**
 * Get last error code on the object
 * @link http://www.php.net/manual/en/intltimezone.geterrorcode.php
 * @param IntlTimeZone $timezone 
 * @return int|false 
 */
function intltz_get_error_code (IntlTimeZone $timezone): int|false {}

/**
 * Get last error message on the object
 * @link http://www.php.net/manual/en/intltimezone.geterrormessage.php
 * @param IntlTimeZone $timezone 
 * @return string|false 
 */
function intltz_get_error_message (IntlTimeZone $timezone): string|false {}

/**
 * Create GMT (UTC) timezone
 * @link http://www.php.net/manual/en/intltimezone.getgmt.php
 * @return IntlTimeZone 
 */
function intltz_get_gmt (): IntlTimeZone {}

/**
 * Get timezone ID
 * @link http://www.php.net/manual/en/intltimezone.getid.php
 * @param IntlTimeZone $timezone 
 * @return string|false 
 */
function intltz_get_id (IntlTimeZone $timezone): string|false {}

/**
 * Get the time zone raw and GMT offset for the given moment in time
 * @link http://www.php.net/manual/en/intltimezone.getoffset.php
 * @param IntlTimeZone $timezone 
 * @param float $timestamp 
 * @param bool $local 
 * @param int $rawOffset 
 * @param int $dstOffset 
 * @return bool 
 */
function intltz_get_offset (IntlTimeZone $timezone, float $timestamp, bool $local, int &$rawOffset, int &$dstOffset): bool {}

/**
 * Get the raw GMT offset (before taking daylight savings time into account
 * @link http://www.php.net/manual/en/intltimezone.getrawoffset.php
 * @param IntlTimeZone $timezone 
 * @return int 
 */
function intltz_get_raw_offset (IntlTimeZone $timezone): int {}

/**
 * Get the region code associated with the given system time zone ID
 * @link http://www.php.net/manual/en/intltimezone.getregion.php
 * @param string $timezoneId 
 * @return string|false Return region or false on failure.
 */
function intltz_get_region (string $timezoneId): string|false {}

/**
 * Get the timezone data version currently used by ICU
 * @link http://www.php.net/manual/en/intltimezone.gettzdataversion.php
 * @return string|false 
 */
function intltz_get_tz_data_version (): string|false {}

/**
 * Get the "unknown" time zone
 * @link http://www.php.net/manual/en/intltimezone.getunknown.php
 * @return IntlTimeZone Returns IntlTimeZone or null on failure.
 */
function intltz_get_unknown (): IntlTimeZone {}

/**
 * Translate a system timezone into a Windows timezone
 * @link http://www.php.net/manual/en/intltimezone.getwindowsid.php
 * @param string $timezoneId 
 * @return string|false Returns the Windows timezone or false on failure.
 */
function intltz_get_windows_id (string $timezoneId): string|false {}

/**
 * Translate a Windows timezone into a system timezone
 * @link http://www.php.net/manual/en/intltimezone.getidforwindowsid.php
 * @param string $timezoneId 
 * @param string|null $region [optional] 
 * @return string|false Returns the system timezone or false on failure.
 */
function intltz_get_id_for_windows_id (string $timezoneId, ?string $region = null): string|false {}

/**
 * Check if this zone has the same rules and offset as another zone
 * @link http://www.php.net/manual/en/intltimezone.hassamerules.php
 * @param IntlTimeZone $timezone 
 * @param IntlTimeZone $other 
 * @return bool 
 */
function intltz_has_same_rules (IntlTimeZone $timezone, IntlTimeZone $other): bool {}

/**
 * Convert to DateTimeZone object
 * @link http://www.php.net/manual/en/intltimezone.todatetimezone.php
 * @param IntlTimeZone $timezone 
 * @return DateTimeZone|false 
 */
function intltz_to_date_time_zone (IntlTimeZone $timezone): DateTimeZone|false {}

/**
 * Check if this time zone uses daylight savings time
 * @link http://www.php.net/manual/en/intltimezone.usedaylighttime.php
 * @param IntlTimeZone $timezone 
 * @return bool 
 */
function intltz_use_daylight_time (IntlTimeZone $timezone): bool {}

/**
 * Create a transliterator
 * @link http://www.php.net/manual/en/transliterator.create.php
 * @param string $id The ID. A list of all registered transliterator IDs can be retrieved by using
 * Transliterator::listIDs.
 * @param int $direction [optional] The direction, defaults to 
 * Transliterator::FORWARD.
 * May also be set to
 * Transliterator::REVERSE.
 * @return Transliterator|null Returns a Transliterator object on success,
 * or null on failure.
 */
function transliterator_create (string $id, int $direction = \Transliterator::FORWARD): ?Transliterator {}

/**
 * Create transliterator from rules
 * @link http://www.php.net/manual/en/transliterator.createfromrules.php
 * @param string $rules The rules as defined in Transform Rules Syntax of UTS #35: Unicode LDML.
 * @param int $direction [optional] The direction, defaults to 
 * Transliterator::FORWARD.
 * May also be set to
 * Transliterator::REVERSE.
 * @return Transliterator|null Returns a Transliterator object on success,
 * or null on failure.
 */
function transliterator_create_from_rules (string $rules, int $direction = \Transliterator::FORWARD): ?Transliterator {}

/**
 * Get transliterator IDs
 * @link http://www.php.net/manual/en/transliterator.listids.php
 * @return array|false An array of registered transliterator IDs on success,
 * or false on failure.
 */
function transliterator_list_ids (): array|false {}

/**
 * Create an inverse transliterator
 * @link http://www.php.net/manual/en/transliterator.createinverse.php
 * @param Transliterator $transliterator 
 * @return Transliterator|null Returns a Transliterator object on success,
 * or null on failure
 */
function transliterator_create_inverse (Transliterator $transliterator): ?Transliterator {}

/**
 * Transliterate a string
 * @link http://www.php.net/manual/en/transliterator.transliterate.php
 * @param Transliterator|string $transliterator In the procedural version, either a Transliterator
 * or a string from which a
 * Transliterator can be built.
 * @param string $string The string to be transformed.
 * @param int $start [optional] The start index (in UTF-16 code units) from which the string will start
 * to be transformed, inclusive. Indexing starts at 0. The text before will
 * be left as is.
 * @param int $end [optional] The end index (in UTF-16 code units) until which the string will be
 * transformed, exclusive. Indexing starts at 0. The text after will be
 * left as is.
 * @return string|false The transformed string on success, or false on failure.
 */
function transliterator_transliterate (Transliterator|string $transliterator, string $string, int $start = null, int $end = -1): string|false {}

/**
 * Get last error code
 * @link http://www.php.net/manual/en/transliterator.geterrorcode.php
 * @param Transliterator $transliterator 
 * @return int|false The error code on success,
 * or false if none exists, or on failure.
 */
function transliterator_get_error_code (Transliterator $transliterator): int|false {}

/**
 * Get last error message
 * @link http://www.php.net/manual/en/transliterator.geterrormessage.php
 * @param Transliterator $transliterator 
 * @return string|false The error message on success,
 * or false if none exists, or on failure.
 */
function transliterator_get_error_message (Transliterator $transliterator): string|false {}


/**
 * Limit on locale length, set to 80 in PHP code. Locale names longer 
 * than this limit will not be accepted.
 * @link http://www.php.net/manual/en/intl.constants.php
 * @var int
 */
define ('INTL_MAX_LOCALE_LEN', 156);

/**
 * The current ICU library version as a dotted-decimal string.
 * @link http://www.php.net/manual/en/intl.constants.php
 * @var string
 */
define ('INTL_ICU_VERSION', 72.1);
define ('INTL_ICU_DATA_VERSION', 72.1);
define ('GRAPHEME_EXTR_COUNT', 0);
define ('GRAPHEME_EXTR_MAXBYTES', 1);
define ('GRAPHEME_EXTR_MAXCHARS', 2);

/**
 * Prohibit processing of unassigned codepoints in the input for IDN
 * functions and do not check if the input conforms to domain name ASCII rules.
 * @link http://www.php.net/manual/en/intl.constants.php
 * @var int
 */
define ('IDNA_DEFAULT', 0);

/**
 * Allow processing of unassigned codepoints in the input for IDN functions.
 * @link http://www.php.net/manual/en/intl.constants.php
 * @var int
 */
define ('IDNA_ALLOW_UNASSIGNED', 1);

/**
 * Check if the input for IDN functions conforms to domain name ASCII rules.
 * @link http://www.php.net/manual/en/intl.constants.php
 * @var int
 */
define ('IDNA_USE_STD3_RULES', 2);

/**
 * Check whether the input conforms to the BiDi rules.
 * Ignored by the IDNA2003 implementation, which always performs this check.
 * @link http://www.php.net/manual/en/intl.constants.php
 * @var int
 */
define ('IDNA_CHECK_BIDI', 4);

/**
 * Check whether the input conforms to the CONTEXTJ rules.
 * Ignored by the IDNA2003 implementation, as this check is new in IDNA2008.
 * @link http://www.php.net/manual/en/intl.constants.php
 * @var int
 */
define ('IDNA_CHECK_CONTEXTJ', 8);

/**
 * Option for nontransitional processing in
 * idn_to_ascii. Transitional processing is activated
 * by default. This option is ignored by the IDNA2003 implementation.
 * @link http://www.php.net/manual/en/intl.constants.php
 * @var int
 */
define ('IDNA_NONTRANSITIONAL_TO_ASCII', 16);

/**
 * Option for nontransitional processing in
 * idn_to_utf8. Transitional processing is activated
 * by default. This option is ignored by the IDNA2003 implementation.
 * @link http://www.php.net/manual/en/intl.constants.php
 * @var int
 */
define ('IDNA_NONTRANSITIONAL_TO_UNICODE', 32);

/**
 * Use UTS #46 algorithm in idn_to_utf8 and
 * idn_to_ascii.
 * Available as of ICU 4.6.
 * @link http://www.php.net/manual/en/intl.constants.php
 * @var int
 */
define ('INTL_IDNA_VARIANT_UTS46', 1);

/**
 * Errors reported in a bitset returned by the UTS #46 algorithm in
 * idn_to_utf8 and
 * idn_to_ascii.
 * @link http://www.php.net/manual/en/intl.constants.php
 * @var int
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

// End of intl v.8.2.6
