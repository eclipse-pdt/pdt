<?php

// Start of uuid v.1.2.0

/**
 * {@inheritdoc}
 * @param int $uuid_type [optional]
 */
function uuid_create (int $uuid_type = 0): string {}

/**
 * {@inheritdoc}
 * @param string $uuid
 */
function uuid_is_valid (string $uuid): bool {}

/**
 * {@inheritdoc}
 * @param string $uuid1
 * @param string $uuid2
 */
function uuid_compare (string $uuid1, string $uuid2): int {}

/**
 * {@inheritdoc}
 * @param string $uuid
 */
function uuid_is_null (string $uuid): bool {}

/**
 * {@inheritdoc}
 * @param string $uuid
 */
function uuid_type (string $uuid): int {}

/**
 * {@inheritdoc}
 * @param string $uuid
 */
function uuid_variant (string $uuid): int {}

/**
 * {@inheritdoc}
 * @param string $uuid
 */
function uuid_time (string $uuid): int {}

/**
 * {@inheritdoc}
 * @param string $uuid
 */
function uuid_mac (string $uuid): string {}

/**
 * {@inheritdoc}
 * @param string $uuid
 */
function uuid_parse (string $uuid): string {}

/**
 * {@inheritdoc}
 * @param string $uuid
 */
function uuid_unparse (string $uuid): string {}

define ('UUID_VARIANT_NCS', 0);
define ('UUID_VARIANT_DCE', 1);
define ('UUID_VARIANT_MICROSOFT', 2);
define ('UUID_VARIANT_OTHER', 3);
define ('UUID_TYPE_DEFAULT', 0);
define ('UUID_TYPE_DCE', 4);
define ('UUID_TYPE_NAME', 1);
define ('UUID_TYPE_TIME', 1);
define ('UUID_TYPE_RANDOM', 4);
define ('UUID_TYPE_NULL', -1);
define ('UUID_TYPE_INVALID', -42);

// End of uuid v.1.2.0
