<?php

// Start of filter v.8.5.0-dev

namespace Filter {

class FilterException extends \Exception implements \Throwable, \Stringable {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param \Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?\Throwable $previous = null): string {}

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
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

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

class FilterFailedException extends \Filter\FilterException implements \Stringable, \Throwable {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param \Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?\Throwable $previous = null): string {}

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
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

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


}


namespace {

/**
 * Checks if a variable of the specified type exists
 * @link http://www.php.net/manual/en/function.filter-has-var.php
 * @param int $input_type 
 * @param string $var_name 
 * @return bool Returns true on success or false on failure.
 */
function filter_has_var (int $input_type, string $var_name): bool {}

/**
 * Gets a specific external variable by name and optionally filters it
 * @link http://www.php.net/manual/en/function.filter-input.php
 * @param int $type One of the INPUT_&#42; constants.
 * The content of the superglobal that is being filtered is the original
 * "raw" content provided by the SAPI,
 * prior to any user modification to the superglobal.
 * To filter a modified superglobal use
 * filter_var instead.
 * @param string $var_name Name of a variable to filter inside the corresponding
 * type superglobal.
 * @param int $filter [optional] The filter to apply.
 * Can be a validation filter by using one of the
 * FILTER_VALIDATE_&#42;
 * constants, a sanitization filter by using one of the
 * FILTER_SANITIZE_&#42;
 * or FILTER_UNSAFE_RAW, or a custom filter by using
 * FILTER_CALLBACK.
 * The default is FILTER_DEFAULT,
 * which is an alias of FILTER_UNSAFE_RAW.
 * This will result in no filtering taking place by default.
 * @param array|int $options [optional] Either an associative array of options,
 * or a bitmask of filter flag constants
 * FILTER_FLAG_&#42;.
 * If the filter accepts options,
 * flags can be provided by using the "flags" field of array.
 * @return mixed On success returns the filtered variable.
 * If the variable is not set false is returned.
 * On failure false is returned,
 * unless the FILTER_NULL_ON_FAILURE flag is used,
 * in which case null is returned.
 */
function filter_input (int $type, string $var_name, int $filter = FILTER_DEFAULT, array|int $options = null): mixed {}

/**
 * Filters a variable with a specified filter
 * @link http://www.php.net/manual/en/function.filter-var.php
 * @param mixed $value Value to filter.
 * Scalar values are
 * converted to string
 * internally before they are filtered.
 * @param int $filter [optional] The filter to apply.
 * Can be a validation filter by using one of the
 * FILTER_VALIDATE_&#42;
 * constants, a sanitization filter by using one of the
 * FILTER_SANITIZE_&#42;
 * or FILTER_UNSAFE_RAW, or a custom filter by using
 * FILTER_CALLBACK.
 * The default is FILTER_DEFAULT,
 * which is an alias of FILTER_UNSAFE_RAW.
 * This will result in no filtering taking place by default.
 * @param array|int $options [optional] Either an associative array of options,
 * or a bitmask of filter flag constants
 * FILTER_FLAG_&#42;.
 * If the filter accepts options,
 * flags can be provided by using the "flags" field of array.
 * @return mixed On success returns the filtered data.
 * On failure false is returned,
 * unless the FILTER_NULL_ON_FAILURE flag is used,
 * in which case null is returned.
 */
function filter_var (mixed $value, int $filter = FILTER_DEFAULT, array|int $options = null): mixed {}

/**
 * Gets external variables and optionally filters them
 * @link http://www.php.net/manual/en/function.filter-input-array.php
 * @param int $type One of the INPUT_&#42; constants.
 * The content of the superglobal that is being filtered is the original
 * "raw" content provided by the SAPI,
 * prior to any user modification to the superglobal.
 * To filter a modified superglobal use
 * filter_var_array instead.
 * @param array|int $options [optional] Either an associative array of options,
 * or the filter to apply to each entry,
 * which can either be a validation filter by using one of the
 * FILTER_VALIDATE_&#42;
 * constants, or a sanitization filter by using one of the
 * FILTER_SANITIZE_&#42;
 * constants.
 * The option array is an associative array where the key corresponds
 * to a key in the data array and the associated
 * value is either the filter to apply to this entry,
 * or an associative array describing how and which filter should be
 * applied to this entry.
 * The associative array describing how a filter should be applied
 * must contain the 'filter' key whose associated
 * value is the filter to apply, which can be one of the
 * FILTER_VALIDATE_&#42;,
 * FILTER_SANITIZE_&#42;,
 * FILTER_UNSAFE_RAW, or
 * FILTER_CALLBACK constants.
 * It can optionally contain the 'flags' key
 * which specifies and flags that apply to the filter,
 * and the 'options' key which specifies any options
 * that apply to the filter.
 * @param bool $add_empty [optional] Add missing keys as null to the return value.
 * @return array|false|null On success, an array containing the values of the requested variables.
 * On failure, false is returned.
 * Except if the failure is that the input array designated by
 * type is not populated where null is returned
 * if the FILTER_NULL_ON_FAILURE flag is used.
 * Missing entries from the input array will be populated into the returned
 * array if add_empty is true.
 * In which case, missing entries will be set to null,
 * unless the FILTER_NULL_ON_FAILURE flag is used,
 * in which case it will be false.
 * An entry of the returned array will be false if the filter fails,
 * unless the FILTER_NULL_ON_FAILURE flag is used,
 * in which case it will be null.
 */
function filter_input_array (int $type, array|int $options = FILTER_DEFAULT, bool $add_empty = true): array|false|null {}

/**
 * Gets multiple variables and optionally filters them
 * @link http://www.php.net/manual/en/function.filter-var-array.php
 * @param array $array An associative array containing the data to filter.
 * @param array|int $options [optional] Either an associative array of options,
 * or the filter to apply to each entry,
 * which can either be a validation filter by using one of the
 * FILTER_VALIDATE_&#42;
 * constants, or a sanitization filter by using one of the
 * FILTER_SANITIZE_&#42;
 * constants.
 * The option array is an associative array where the key corresponds
 * to a key in the data array and the associated
 * value is either the filter to apply to this entry,
 * or an associative array describing how and which filter should be
 * applied to this entry.
 * The associative array describing how a filter should be applied
 * must contain the 'filter' key whose associated
 * value is the filter to apply, which can be one of the
 * FILTER_VALIDATE_&#42;,
 * FILTER_SANITIZE_&#42;,
 * FILTER_UNSAFE_RAW, or
 * FILTER_CALLBACK constants.
 * It can optionally contain the 'flags' key
 * which specifies and flags that apply to the filter,
 * and the 'options' key which specifies any options
 * that apply to the filter.
 * @param bool $add_empty [optional] Add missing keys as null to the return value.
 * @return array|false|null An array containing the values of the requested variables on success, or false 
 * on failure. An array value will be false if the filter fails, or null if 
 * the variable is not set.
 */
function filter_var_array (array $array, array|int $options = FILTER_DEFAULT, bool $add_empty = true): array|false|null {}

/**
 * Returns a list of all supported filters
 * @link http://www.php.net/manual/en/function.filter-list.php
 * @return array Returns an array of names of all supported filters, empty array if there
 * are no such filters. Indexes of this array are not filter IDs, they can be
 * obtained with filter_id from a name instead.
 */
function filter_list (): array {}

/**
 * Returns the filter ID belonging to a named filter
 * @link http://www.php.net/manual/en/function.filter-id.php
 * @param string $name 
 * @return int|false ID of a filter on success or false if filter doesn't exist.
 */
function filter_id (string $name): int|false {}


/**
 * POST variables.
 * @link http://www.php.net/manual/en/reserved.variables.post.php
 * @var int
 */
define ('INPUT_POST', 0);

/**
 * GET variables.
 * @link http://www.php.net/manual/en/reserved.variables.get.php
 * @var int
 */
define ('INPUT_GET', 1);

/**
 * COOKIE variables.
 * @link http://www.php.net/manual/en/reserved.variables.cookies.php
 * @var int
 */
define ('INPUT_COOKIE', 2);

/**
 * ENV variables.
 * @link http://www.php.net/manual/en/reserved.variables.environment.php
 * @var int
 */
define ('INPUT_ENV', 4);

/**
 * SERVER variables.
 * @link http://www.php.net/manual/en/reserved.variables.server.php
 * @var int
 */
define ('INPUT_SERVER', 5);

/**
 * No flags.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_NONE', 0);

/**
 * Flag used to require the input of the filter to be a scalar.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_REQUIRE_SCALAR', 33554432);

/**
 * Flag used to require the input of the filter to be an array.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_REQUIRE_ARRAY', 16777216);

/**
 * This flags wraps scalar inputs into a one element array
 * for filters which operate on arrays.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FORCE_ARRAY', 67108864);

/**
 * Use null instead of false on failure.
 * Usable with any validation
 * FILTER_VALIDATE_&#42;
 * filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_NULL_ON_FAILURE', 134217728);
define ('FILTER_THROW_ON_FAILURE', 268435456);

/**
 * Validates whether the value is an integer,
 * on success it is converted to type int.
 * String values are trimmed using trim
 * before validation.
 * Value to return in case the filter fails.
 * Value is only valid if it is greater than or equal to the provided value.
 * Value is only valid if it is less than or equal to the provided value.
 * Allow integers in octal notation
 * (0[0-7]+).
 * Allow integers in hexadecimal notation
 * (0x[0-9a-fA-F]+).
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_VALIDATE_INT', 257);

/**
 * Alias of FILTER_VALIDATE_BOOL.
 * The alias was available prior to the introduction of its canonical
 * name in PHP 8.0.0.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_VALIDATE_BOOLEAN', 258);

/**
 * Returns true for "1",
 * "true",
 * "on",
 * and "yes".
 * Returns false for "0",
 * "false",
 * "off",
 * "no", and
 * "".
 * The return value for non-boolean values depends on the
 * FILTER_NULL_ON_FAILURE.
 * If it is set, null is returned, otherwise false is returned.
 * Value to return in case the filter fails.
 * Available as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_VALIDATE_BOOL', 258);

/**
 * Validates whether the value is a float,
 * on success it is converted to type float.
 * String values are trimmed using trim
 * before validation.
 * Value to return in case the filter fails.
 * Value is only valid if it is greater than or equal to the provided value.
 * Available as of PHP 7.4.0.
 * Value is only valid if it is less than or equal to the provided value.
 * Available as of PHP 7.4.0.
 * Accept commas (,),
 * which usually represent the thousand separator.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_VALIDATE_FLOAT', 259);

/**
 * Validates value against the regular expression provided by the
 * regexp option.
 * Value to return in case the filter fails.
 * Perl-compatible regular expression.
 * @link http://www.php.net/manual/en/book.pcre.php
 * @var int
 */
define ('FILTER_VALIDATE_REGEXP', 272);

/**
 * Validates whether the domain name is valid according to
 * RFC 952,
 * RFC 1034,
 * RFC 1035,
 * RFC 1123,
 * RFC 2732,
 * and
 * RFC 2181.
 * Value to return in case the filter fails.
 * Require hostnames to start with an alphanumeric character and contain
 * only alphanumerics or hyphens.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_VALIDATE_DOMAIN', 277);

/**
 * Validates whether the URL name is valid according to
 * RFC 2396.
 * Value to return in case the filter fails.
 * Requires the URL to contain a scheme part.
 * DEPRECATED as of PHP 7.3.0 and
 * REMOVED as of PHP 8.0.0.
 * This is because it is always implied by the
 * FILTER_VALIDATE_URL filter.
 * Requires the URL to contain a host part.
 * DEPRECATED as of PHP 7.3.0 and
 * REMOVED as of PHP 8.0.0.
 * This is because it is always implied by the
 * FILTER_VALIDATE_URL filter.
 * Requires the URL to contain a path part.
 * Requires the URL to contain a query part.
 * A valid URL may not specify the
 * HTTP protocol (http://).
 * Therefore, further validation may be required to determine if the
 * URL uses an expected protocol,
 * e.g. ssh:// or mailto:.
 * This filter only works on ASCII URLs.
 * This means that Internationalized Domain Names (IDN) will always be rejected.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_VALIDATE_URL', 273);

/**
 * Validates whether the value is a "valid" e-mail address.
 * The validation is performed against the addr-spec
 * syntax in
 * RFC 822.
 * However, comments, whitespace folding, and dotless domain names
 * are not supported, and thus will be rejected.
 * Value to return in case the filter fails.
 * Accepts Unicode characters in the local part.
 * Available as of PHP 7.1.0.
 * Email validation is complex and the only true way to confirm an email
 * is valid and exists is to send an email to the address.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_VALIDATE_EMAIL', 274);

/**
 * Validates value as IP address.
 * <p>Which for IPv4 corresponds to the following ranges:
 * <p>
 * 0.0.0.0/8
 * 169.254.0.0/16
 * 127.0.0.0/8
 * 240.0.0.0/4
 * </p>.</p>
 * <p>And for IPv6 corresponds to the following ranges:
 * <p>
 * ::1/128
 * ::/128
 * ::FFFF:0:0/96
 * FE80::/10
 * </p>.</p>
 * <p>These are IPv4 addresses which are in the following ranges:
 * <p>
 * 10.0.0.0/8
 * 172.16.0.0/12
 * 192.168.0.0/16
 * </p>.</p>
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_VALIDATE_IP', 275);

/**
 * Validates whether the value is a MAC address.
 * Value to return in case the filter fails.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_VALIDATE_MAC', 276);

/**
 * Alias of FILTER_UNSAFE_RAW.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_DEFAULT', 516);

/**
 * This filter does nothing.
 * However, it can strip or encode special characters if used together with
 * the FILTER_FLAG_STRIP_&#42;
 * and FILTER_FLAG_ENCODE_&#42;
 * filter sanitization flags.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_UNSAFE_RAW', 516);

/**
 * This filter strips tags and HTML-encodes double and single quotes.
 * Optionally it can strip or encode specified characters if used together with
 * the FILTER_FLAG_STRIP_&#42;
 * and FILTER_FLAG_ENCODE_&#42;
 * filter sanitization flags.
 * The behaviour of encoding quotes can be disabled by using the
 * FILTER_FLAG_NO_ENCODE_QUOTES filter flag.
 * Deprecated as of PHP 8.1.0,
 * use htmlspecialchars instead.
 * The way this filter strips tags is not equivalent to
 * strip_tags.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_SANITIZE_STRING', 513);

/**
 * Alias of FILTER_SANITIZE_STRING.
 * Deprecated as of PHP 8.1.0,
 * use htmlspecialchars instead.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_SANITIZE_STRIPPED', 513);

/**
 * This filter URL-encodes a string.
 * Optionally it can strip or encode specified characters if used together with
 * the FILTER_FLAG_STRIP_&#42;
 * and FILTER_FLAG_ENCODE_&#42;
 * filter sanitization flags.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_SANITIZE_ENCODED', 514);

/**
 * This filter HTML-encodes
 * <p>
 * '
 * "
 * &lt;
 * &gt;
 * &amp;
 * </p>
 * and characters with an ASCII value less than 32.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_SANITIZE_SPECIAL_CHARS', 515);

/**
 * This filter is equivalent to calling htmlspecialchars
 * with ENT_QUOTES set.
 * The behaviour of encoding quotes can be disabled by using the
 * FILTER_FLAG_NO_ENCODE_QUOTES filter flag.
 * Like htmlspecialchars, this filter is aware of the
 * default_charset INI setting.
 * If a sequence of bytes is detected that makes up an invalid character
 * in the current character set then the entire string is rejected
 * resulting in a empty string being returned.
 * @link http://www.php.net/manual/en/ini.default-charset.php
 * @var int
 */
define ('FILTER_SANITIZE_FULL_SPECIAL_CHARS', 522);

/**
 * Sanitize the string by removing all characters except
 * latin letters ([a-zA-Z]),
 * digits ([0-9]),
 * and the special characters
 * !#$%&amp;'&#42;+-=?^_`{|}~@.[].
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_SANITIZE_EMAIL', 517);

/**
 * Sanitize the string by removing all characters except
 * latin letters ([a-zA-Z]),
 * digits ([0-9]),
 * and the special characters
 * $-_.+!&#42;'(),{}|\\^~[]`&lt;&gt;#%";/?:@&amp;=.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_SANITIZE_URL', 518);

/**
 * Sanitize the string by removing all characters except digits
 * ([0-9]), plus sign (+),
 * and minus sign (-).
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_SANITIZE_NUMBER_INT', 519);

/**
 * >The above example will output:
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_SANITIZE_NUMBER_FLOAT', 520);

/**
 * Apply addslashes to the input.
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_SANITIZE_ADD_SLASHES', 523);

/**
 * The callback should have the following signature:
 * mixedcallback
 * stringvalue
 * <p>
 * value
 * <br>
 * The value that is being filtered.
 * </p>
 * <p>>The above example will output:</p>
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_CALLBACK', 1024);

/**
 * Allow integers in octal notation
 * (0[0-7]+).
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_ALLOW_OCTAL', 1);

/**
 * Allow integers in hexadecimal notation
 * (0x[0-9a-fA-F]+).
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_ALLOW_HEX', 2);

/**
 * Strip characters with ASCII value less than 32.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_STRIP_LOW', 4);

/**
 * Strip characters with ASCII value greater than 127.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_STRIP_HIGH', 8);

/**
 * Strips backtick (`) characters.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_STRIP_BACKTICK', 512);

/**
 * Encode characters with ASCII value less than 32.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_ENCODE_LOW', 16);

/**
 * Encode characters with ASCII value greater than 127.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_ENCODE_HIGH', 32);

/**
 * Encode &amp;.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_ENCODE_AMP', 64);

/**
 * Singe and double quotes (' and ")
 * will not be encoded.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_NO_ENCODE_QUOTES', 128);

/**
 * If sanitizing a string results in an empty string,
 * convert the value to null
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_EMPTY_STRING_NULL', 256);

/**
 * Accept dot (.) character,
 * which usually represents the separator between the integer and
 * fractional parts.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_ALLOW_FRACTION', 4096);

/**
 * Accept commas (,) character,
 * which usually represents the thousand separator.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_ALLOW_THOUSAND', 8192);

/**
 * Accept numbers in scientific notation by allowing the
 * e and E characters.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_ALLOW_SCIENTIFIC', 16384);

/**
 * Requires the URL to contain a path part.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_PATH_REQUIRED', 262144);

/**
 * Requires the URL to contain a query part.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_QUERY_REQUIRED', 524288);

/**
 * Allow IPv4 address.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_IPV4', 1048576);

/**
 * Allow IPv6 address.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_IPV6', 2097152);

/**
 * Which for IPv4 corresponds to the following ranges:
 * <p>
 * 0.0.0.0/8
 * 169.254.0.0/16
 * 127.0.0.0/8
 * 240.0.0.0/4
 * </p>.
 * <p>And for IPv6 corresponds to the following ranges:
 * <p>
 * ::1/128
 * ::/128
 * ::FFFF:0:0/96
 * FE80::/10
 * </p>.</p>
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_NO_RES_RANGE', 4194304);

/**
 * These are IPv4 addresses which are in the following ranges:
 * <p>
 * 10.0.0.0/8
 * 172.16.0.0/12
 * 192.168.0.0/16
 * </p>.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_NO_PRIV_RANGE', 8388608);

/**
 * Only allow global addresses.
 * These can be found in
 * RFC 6890
 * where the Global attribute is True.
 * Available as of PHP 8.2.0.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_GLOBAL_RANGE', 536870912);

/**
 * Require hostnames to start with an alphanumeric character and contain
 * only alphanumerics or hyphens.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_HOSTNAME', 1048576);

/**
 * Accepts Unicode characters in the local part.
 * Available as of PHP 7.1.0.
 * @link http://www.php.net/manual/en/filter.constants.php
 * @var int
 */
define ('FILTER_FLAG_EMAIL_UNICODE', 1048576);


}

// End of filter v.8.5.0-dev
