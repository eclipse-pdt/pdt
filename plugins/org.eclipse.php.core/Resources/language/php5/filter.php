<?php

// Start of filter v.0.11.0

/**
 * Gets a specific external variable by name and optionally filters it
 * @link http://php.net/manual/en/function.filter-input.php
 * @param type int
 * @param variable_name string
 * @param filter int[optional]
 * @param options mixed[optional]
 * @return mixed 
 */
function filter_input ($type, $variable_name, $filter = null, $options = null) {}

/**
 * Filters a variable with a specified filter
 * @link http://php.net/manual/en/function.filter-var.php
 * @param variable mixed
 * @param filter int[optional]
 * @param options mixed[optional]
 * @return mixed the filtered data, or false if the filter fails.
 */
function filter_var ($variable, $filter = null, $options = null) {}

/**
 * Gets external variables and optionally filters them
 * @link http://php.net/manual/en/function.filter-input-array.php
 * @param type int
 * @param definition mixed[optional]
 * @return mixed 
 */
function filter_input_array ($type, $definition = null) {}

/**
 * Gets multiple variables and optionally filters them
 * @link http://php.net/manual/en/function.filter-var-array.php
 * @param data array
 * @param definition mixed[optional]
 * @return mixed 
 */
function filter_var_array (array $data, $definition = null) {}

/**
 * Returns a list of all supported filters
 * @link http://php.net/manual/en/function.filter-list.php
 * @return array an array of names of all supported filters, empty array if there
 */
function filter_list () {}

/**
 * Checks if variable of specified type exists
 * @link http://php.net/manual/en/function.filter-has-var.php
 * @param type int
 * @param variable_name string
 * @return bool 
 */
function filter_has_var ($type, $variable_name) {}

/**
 * Returns the filter ID belonging to a named filter
 * @link http://php.net/manual/en/function.filter-id.php
 * @param filtername string
 * @return int 
 */
function filter_id ($filtername) {}


/**
 * POST variables.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('INPUT_POST', 0);

/**
 * GET variables.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('INPUT_GET', 1);

/**
 * COOKIE variables.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('INPUT_COOKIE', 2);

/**
 * ENV variables.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('INPUT_ENV', 4);

/**
 * SERVER variables.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('INPUT_SERVER', 5);

/**
 * SESSION variables.
 * (not implemented yet)
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('INPUT_SESSION', 6);

/**
 * REQUEST variables.
 * (not implemented yet)
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('INPUT_REQUEST', 99);

/**
 * No flags.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_NONE', 0);

/**
 * Flag used to require scalar as input
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_REQUIRE_SCALAR', 33554432);

/**
 * Require an array as input.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_REQUIRE_ARRAY', 16777216);

/**
 * Always returns an array.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FORCE_ARRAY', 67108864);

/**
 * Use NULL instead of FALSE on failure.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_NULL_ON_FAILURE', 134217728);

/**
 * ID of "int" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_VALIDATE_INT', 257);

/**
 * ID of "boolean" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_VALIDATE_BOOLEAN', 258);

/**
 * ID of "float" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_VALIDATE_FLOAT', 259);

/**
 * ID of "validate_regexp" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_VALIDATE_REGEXP', 272);

/**
 * ID of "validate_url" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_VALIDATE_URL', 273);

/**
 * ID of "validate_email" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_VALIDATE_EMAIL', 274);

/**
 * ID of "validate_ip" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_VALIDATE_IP', 275);

/**
 * ID of default ("string") filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_DEFAULT', 516);

/**
 * ID of "unsafe_raw" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_UNSAFE_RAW', 516);

/**
 * ID of "string" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_SANITIZE_STRING', 513);

/**
 * ID of "stripped" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_SANITIZE_STRIPPED', 513);

/**
 * ID of "encoded" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_SANITIZE_ENCODED', 514);

/**
 * ID of "special_chars" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_SANITIZE_SPECIAL_CHARS', 515);

/**
 * ID of "email" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_SANITIZE_EMAIL', 517);

/**
 * ID of "url" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_SANITIZE_URL', 518);

/**
 * ID of "number_int" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_SANITIZE_NUMBER_INT', 519);

/**
 * ID of "number_float" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_SANITIZE_NUMBER_FLOAT', 520);

/**
 * ID of "magic_quotes" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_SANITIZE_MAGIC_QUOTES', 521);

/**
 * ID of "callback" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_CALLBACK', 1024);

/**
 * Allow octal notation (0[0-7]+) in "int" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_ALLOW_OCTAL', 1);

/**
 * Allow hex notation (0x[0-9a-fA-F]+) in "int" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_ALLOW_HEX', 2);

/**
 * Strip characters with ASCII value less than 32.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_STRIP_LOW', 4);

/**
 * Strip characters with ASCII value greater than 127.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_STRIP_HIGH', 8);

/**
 * Encode characters with ASCII value less than 32.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_ENCODE_LOW', 16);

/**
 * Encode characters with ASCII value greater than 127.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_ENCODE_HIGH', 32);

/**
 * Encode &amp;.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_ENCODE_AMP', 64);

/**
 * Don't encode ' and ".
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_NO_ENCODE_QUOTES', 128);

/**
 * (No use for now.)
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_EMPTY_STRING_NULL', 256);

/**
 * Allow fractional part in "number_float" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_ALLOW_FRACTION', 4096);

/**
 * Allow thousand separator (,) in "number_float" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_ALLOW_THOUSAND', 8192);

/**
 * Allow scientific notation (e, E) in
 * "number_float" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_ALLOW_SCIENTIFIC', 16384);

/**
 * Require scheme in "validate_url" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_SCHEME_REQUIRED', 65536);

/**
 * Require host in "validate_url" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_HOST_REQUIRED', 131072);

/**
 * Require path in "validate_url" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_PATH_REQUIRED', 262144);

/**
 * Require query in "validate_url" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_QUERY_REQUIRED', 524288);

/**
 * Allow only IPv4 address in "validate_ip" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_IPV4', 1048576);

/**
 * Allow only IPv6 address in "validate_ip" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_IPV6', 2097152);

/**
 * Deny reserved addresses in "validate_ip" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_NO_RES_RANGE', 4194304);

/**
 * Deny private addresses in "validate_ip" filter.
 * @link http://php.net/manual/en/filter.constants.php
 */
define ('FILTER_FLAG_NO_PRIV_RANGE', 8388608);

// End of filter v.0.11.0
?>
