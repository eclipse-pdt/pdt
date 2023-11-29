<?php

// Start of filter v.8.3.0

/**
 * {@inheritdoc}
 * @param int $input_type
 * @param string $var_name
 */
function filter_has_var (int $input_type, string $var_name): bool {}

/**
 * {@inheritdoc}
 * @param int $type
 * @param string $var_name
 * @param int $filter [optional]
 * @param array|int $options [optional]
 */
function filter_input (int $type, string $var_name, int $filter = 516, array|int $options = 0): mixed {}

/**
 * {@inheritdoc}
 * @param mixed $value
 * @param int $filter [optional]
 * @param array|int $options [optional]
 */
function filter_var (mixed $value = null, int $filter = 516, array|int $options = 0): mixed {}

/**
 * {@inheritdoc}
 * @param int $type
 * @param array|int $options [optional]
 * @param bool $add_empty [optional]
 */
function filter_input_array (int $type, array|int $options = 516, bool $add_empty = true): array|false|null {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param array|int $options [optional]
 * @param bool $add_empty [optional]
 */
function filter_var_array (array $array, array|int $options = 516, bool $add_empty = true): array|false|null {}

/**
 * {@inheritdoc}
 */
function filter_list (): array {}

/**
 * {@inheritdoc}
 * @param string $name
 */
function filter_id (string $name): int|false {}

define ('INPUT_POST', 0);
define ('INPUT_GET', 1);
define ('INPUT_COOKIE', 2);
define ('INPUT_ENV', 4);
define ('INPUT_SERVER', 5);
define ('FILTER_FLAG_NONE', 0);
define ('FILTER_REQUIRE_SCALAR', 33554432);
define ('FILTER_REQUIRE_ARRAY', 16777216);
define ('FILTER_FORCE_ARRAY', 67108864);
define ('FILTER_NULL_ON_FAILURE', 134217728);
define ('FILTER_VALIDATE_INT', 257);
define ('FILTER_VALIDATE_BOOLEAN', 258);
define ('FILTER_VALIDATE_BOOL', 258);
define ('FILTER_VALIDATE_FLOAT', 259);
define ('FILTER_VALIDATE_REGEXP', 272);
define ('FILTER_VALIDATE_DOMAIN', 277);
define ('FILTER_VALIDATE_URL', 273);
define ('FILTER_VALIDATE_EMAIL', 274);
define ('FILTER_VALIDATE_IP', 275);
define ('FILTER_VALIDATE_MAC', 276);
define ('FILTER_DEFAULT', 516);
define ('FILTER_UNSAFE_RAW', 516);
define ('FILTER_SANITIZE_STRING', 513);
define ('FILTER_SANITIZE_STRIPPED', 513);
define ('FILTER_SANITIZE_ENCODED', 514);
define ('FILTER_SANITIZE_SPECIAL_CHARS', 515);
define ('FILTER_SANITIZE_FULL_SPECIAL_CHARS', 522);
define ('FILTER_SANITIZE_EMAIL', 517);
define ('FILTER_SANITIZE_URL', 518);
define ('FILTER_SANITIZE_NUMBER_INT', 519);
define ('FILTER_SANITIZE_NUMBER_FLOAT', 520);
define ('FILTER_SANITIZE_ADD_SLASHES', 523);
define ('FILTER_CALLBACK', 1024);
define ('FILTER_FLAG_ALLOW_OCTAL', 1);
define ('FILTER_FLAG_ALLOW_HEX', 2);
define ('FILTER_FLAG_STRIP_LOW', 4);
define ('FILTER_FLAG_STRIP_HIGH', 8);
define ('FILTER_FLAG_STRIP_BACKTICK', 512);
define ('FILTER_FLAG_ENCODE_LOW', 16);
define ('FILTER_FLAG_ENCODE_HIGH', 32);
define ('FILTER_FLAG_ENCODE_AMP', 64);
define ('FILTER_FLAG_NO_ENCODE_QUOTES', 128);
define ('FILTER_FLAG_EMPTY_STRING_NULL', 256);
define ('FILTER_FLAG_ALLOW_FRACTION', 4096);
define ('FILTER_FLAG_ALLOW_THOUSAND', 8192);
define ('FILTER_FLAG_ALLOW_SCIENTIFIC', 16384);
define ('FILTER_FLAG_PATH_REQUIRED', 262144);
define ('FILTER_FLAG_QUERY_REQUIRED', 524288);
define ('FILTER_FLAG_IPV4', 1048576);
define ('FILTER_FLAG_IPV6', 2097152);
define ('FILTER_FLAG_NO_RES_RANGE', 4194304);
define ('FILTER_FLAG_NO_PRIV_RANGE', 8388608);
define ('FILTER_FLAG_GLOBAL_RANGE', 268435456);
define ('FILTER_FLAG_HOSTNAME', 1048576);
define ('FILTER_FLAG_EMAIL_UNICODE', 1048576);

// End of filter v.8.3.0
