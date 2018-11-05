<?php

// Start of filter v.7.3.0

/**
 * Gets a specific external variable by name and optionally filters it
 * @link http://www.php.net/manual/en/function.filter-input.php
 * @param int $type One of INPUT_GET, INPUT_POST,
 * INPUT_COOKIE, INPUT_SERVER, or
 * INPUT_ENV.
 * @param string $variable_name Name of a variable to get.
 * @param int $filter [optional] 
 * @param mixed $options [optional] Associative array of options or bitwise disjunction of flags. If filter
 * accepts options, flags can be provided in "flags" field of array.
 * @return mixed Value of the requested variable on success, false if the filter fails,
 * or null if the variable_name variable is not set.
 * If the flag FILTER_NULL_ON_FAILURE is used, it
 * returns false if the variable is not set and null if the filter fails.
 */
function filter_input (int $type, string $variable_name, int $filter = null, $options = null) {}

/**
 * Filters a variable with a specified filter
 * @link http://www.php.net/manual/en/function.filter-var.php
 * @param mixed $variable Value to filter. Note that scalar values are converted to
 * string internally before they are filtered.
 * @param int $filter [optional] 
 * @param mixed $options [optional] <p>
 * Associative array of options or bitwise disjunction of flags. If filter
 * accepts options, flags can be provided in "flags" field of array. For
 * the "callback" filter, callable type should be passed. The
 * callback must accept one argument, the value to be filtered, and return
 * the value after filtering/sanitizing it.
 * </p>
 * <p>
 * <pre>
 * <code>&lt;?php
 * &#47;&#47; for filters that accept options, use this format
 * $options = array(
 * 'options' =&gt; array(
 * 'default' =&gt; 3, &#47;&#47; value to return if the filter fails
 * &#47;&#47; other options here
 * 'min_range' =&gt; 0
 * ),
 * 'flags' =&gt; FILTER_FLAG_ALLOW_OCTAL,
 * );
 * $var = filter_var('0755', FILTER_VALIDATE_INT, $options);
 * &#47;&#47; for filter that only accept flags, you can pass them directly
 * $var = filter_var('oops', FILTER_VALIDATE_BOOLEAN, FILTER_NULL_ON_FAILURE);
 * &#47;&#47; for filter that only accept flags, you can also pass as an array
 * $var = filter_var('oops', FILTER_VALIDATE_BOOLEAN,
 * array('flags' =&gt; FILTER_NULL_ON_FAILURE));
 * &#47;&#47; callback validate filter
 * function foo($value)
 * {
 * &#47;&#47; Expected format: Surname, GivenNames
 * if (strpos($value, &quot;, &quot;) === false) return false;
 * list($surname, $givennames) = explode(&quot;, &quot;, $value, 2);
 * $empty = (empty($surname) || empty($givennames));
 * $notstrings = (!is_string($surname) || !is_string($givennames));
 * if ($empty || $notstrings) {
 * return false;
 * } else {
 * return $value;
 * }
 * }
 * $var = filter_var('Doe, Jane Sue', FILTER_CALLBACK, array('options' =&gt; 'foo'));
 * ?&gt;</code>
 * </pre>
 * </p>
 * @return mixed the filtered data, or false if the filter fails.
 */
function filter_var ($variable, int $filter = null, $options = null) {}

/**
 * Gets external variables and optionally filters them
 * @link http://www.php.net/manual/en/function.filter-input-array.php
 * @param int $type One of INPUT_GET, INPUT_POST,
 * INPUT_COOKIE, INPUT_SERVER, or
 * INPUT_ENV.
 * @param mixed $definition [optional] <p>
 * An array defining the arguments. A valid key is a string
 * containing a variable name and a valid value is either a filter type, or an array
 * optionally specifying the filter, flags and options. If the value is an
 * array, valid keys are filter which specifies the
 * filter type,
 * flags which specifies any flags that apply to the
 * filter, and options which specifies any options that
 * apply to the filter. See the example below for a better understanding.
 * </p>
 * <p>
 * This parameter can be also an integer holding a filter constant. Then all values in the
 * input array are filtered by this filter.
 * </p>
 * @param bool $add_empty [optional] Add missing keys as null to the return value.
 * @return mixed An array containing the values of the requested variables on success.
 * If the input array designated by type is not populated,
 * the function returns null if the FILTER_NULL_ON_FAILURE
 * flag is not given, or false otherwise. For other failures, false is returned.
 * <p>
 * An array value will be false if the filter fails, or null if 
 * the variable is not set. Or if the flag FILTER_NULL_ON_FAILURE
 * is used, it returns false if the variable is not set and null if the filter 
 * fails. If the add_empty parameter is false, no array
 * element will be added for unset variables.
 * </p>
 */
function filter_input_array (int $type, $definition = null, bool $add_empty = null) {}

/**
 * Gets multiple variables and optionally filters them
 * @link http://www.php.net/manual/en/function.filter-var-array.php
 * @param array $data An array with string keys containing the data to filter.
 * @param mixed $definition [optional] <p>
 * An array defining the arguments. A valid key is a string
 * containing a variable name and a valid value is either a
 * filter type, or an
 * array optionally specifying the filter, flags and options.
 * If the value is an array, valid keys are filter
 * which specifies the filter type,
 * flags which specifies any flags that apply to the
 * filter, and options which specifies any options that
 * apply to the filter. See the example below for a better understanding.
 * </p>
 * <p>
 * This parameter can be also an integer holding a filter constant. Then all values in the
 * input array are filtered by this filter.
 * </p>
 * @param bool $add_empty [optional] Add missing keys as null to the return value.
 * @return mixed An array containing the values of the requested variables on success, or false 
 * on failure. An array value will be false if the filter fails, or null if 
 * the variable is not set.
 */
function filter_var_array (array $data, $definition = null, bool $add_empty = null) {}

/**
 * Returns a list of all supported filters
 * @link http://www.php.net/manual/en/function.filter-list.php
 * @return array an array of names of all supported filters, empty array if there
 * are no such filters. Indexes of this array are not filter IDs, they can be
 * obtained with filter_id from a name instead.
 */
function filter_list () {}

/**
 * Checks if variable of specified type exists
 * @link http://www.php.net/manual/en/function.filter-has-var.php
 * @param int $type One of INPUT_GET, INPUT_POST,
 * INPUT_COOKIE, INPUT_SERVER, or
 * INPUT_ENV.
 * @param string $variable_name Name of a variable to check.
 * @return bool true on success or false on failure
 */
function filter_has_var (int $type, string $variable_name) {}

/**
 * Returns the filter ID belonging to a named filter
 * @link http://www.php.net/manual/en/function.filter-id.php
 * @param string $filtername Name of a filter to get.
 * @return int ID of a filter on success or false if filter doesn't exist.
 */
function filter_id (string $filtername) {}


/**
 * POST variables.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('INPUT_POST', 0);

/**
 * GET variables.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('INPUT_GET', 1);

/**
 * COOKIE variables.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('INPUT_COOKIE', 2);

/**
 * ENV variables.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('INPUT_ENV', 4);

/**
 * SERVER variables.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('INPUT_SERVER', 5);

/**
 * SESSION variables.
 * (not implemented yet)
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('INPUT_SESSION', 6);

/**
 * REQUEST variables.
 * (not implemented yet)
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('INPUT_REQUEST', 99);

/**
 * No flags.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_NONE', 0);

/**
 * Flag used to require scalar as input
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_REQUIRE_SCALAR', 33554432);

/**
 * Require an array as input.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_REQUIRE_ARRAY', 16777216);

/**
 * Always returns an array.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FORCE_ARRAY', 67108864);

/**
 * Use NULL instead of FALSE on failure.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_NULL_ON_FAILURE', 134217728);

/**
 * ID of "int" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_VALIDATE_INT', 257);

/**
 * ID of "boolean" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_VALIDATE_BOOLEAN', 258);

/**
 * ID of "float" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_VALIDATE_FLOAT', 259);

/**
 * ID of "validate_regexp" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_VALIDATE_REGEXP', 272);
define ('FILTER_VALIDATE_DOMAIN', 277);

/**
 * ID of "validate_url" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_VALIDATE_URL', 273);

/**
 * ID of "validate_email" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_VALIDATE_EMAIL', 274);

/**
 * ID of "validate_ip" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_VALIDATE_IP', 275);

/**
 * ID of "validate_mac_address" filter.
 * (Available as of PHP 5.5.0)
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_VALIDATE_MAC', 276);

/**
 * ID of default ("unsafe_raw") filter. This is equivalent to
 * FILTER_UNSAFE_RAW.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_DEFAULT', 516);

/**
 * ID of "unsafe_raw" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_UNSAFE_RAW', 516);

/**
 * ID of "string" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_SANITIZE_STRING', 513);

/**
 * ID of "stripped" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_SANITIZE_STRIPPED', 513);

/**
 * ID of "encoded" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_SANITIZE_ENCODED', 514);

/**
 * ID of "special_chars" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_SANITIZE_SPECIAL_CHARS', 515);
define ('FILTER_SANITIZE_FULL_SPECIAL_CHARS', 522);

/**
 * ID of "email" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_SANITIZE_EMAIL', 517);

/**
 * ID of "url" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_SANITIZE_URL', 518);

/**
 * ID of "number_int" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_SANITIZE_NUMBER_INT', 519);

/**
 * ID of "number_float" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_SANITIZE_NUMBER_FLOAT', 520);

/**
 * ID of "magic_quotes" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_SANITIZE_MAGIC_QUOTES', 521);
define ('FILTER_SANITIZE_ADD_SLASHES', 523);

/**
 * ID of "callback" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_CALLBACK', 1024);

/**
 * Allow octal notation (0[0-7]+) in "int" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_ALLOW_OCTAL', 1);

/**
 * Allow hex notation (0x[0-9a-fA-F]+) in "int" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_ALLOW_HEX', 2);

/**
 * Strip characters with ASCII value less than 32.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_STRIP_LOW', 4);

/**
 * Strip characters with ASCII value greater than 127.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_STRIP_HIGH', 8);
define ('FILTER_FLAG_STRIP_BACKTICK', 512);

/**
 * Encode characters with ASCII value less than 32.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_ENCODE_LOW', 16);

/**
 * Encode characters with ASCII value greater than 127.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_ENCODE_HIGH', 32);

/**
 * Encode &amp;.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_ENCODE_AMP', 64);

/**
 * Don't encode ' and ".
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_NO_ENCODE_QUOTES', 128);

/**
 * (No use for now.)
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_EMPTY_STRING_NULL', 256);

/**
 * Allow fractional part in "number_float" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_ALLOW_FRACTION', 4096);

/**
 * Allow thousand separator (,) in "number_float" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_ALLOW_THOUSAND', 8192);

/**
 * Allow scientific notation (e, E) in
 * "number_float" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_ALLOW_SCIENTIFIC', 16384);
define ('FILTER_FLAG_SCHEME_REQUIRED', 65536);
define ('FILTER_FLAG_HOST_REQUIRED', 131072);

/**
 * Require path in "validate_url" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_PATH_REQUIRED', 262144);

/**
 * Require query in "validate_url" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_QUERY_REQUIRED', 524288);

/**
 * Allow only IPv4 address in "validate_ip" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_IPV4', 1048576);

/**
 * Allow only IPv6 address in "validate_ip" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_IPV6', 2097152);

/**
 * Deny reserved addresses in "validate_ip" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_NO_RES_RANGE', 4194304);

/**
 * Deny private addresses in "validate_ip" filter.
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_NO_PRIV_RANGE', 8388608);
define ('FILTER_FLAG_HOSTNAME', 1048576);

/**
 * Accepts Unicode characters in the local part in "validate_email" filter.
 * (Available as of PHP 7.1.0)
 * @link http://www.php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_EMAIL_UNICODE', 1048576);

// End of filter v.7.3.0
