<?php

// Start of uuid v.1.0.3

/**
 * @param uuid_type[optional]
 */
function uuid_create ($uuid_type) {}

/**
 * @param uuid
 */
function uuid_is_valid ($uuid) {}

/**
 * @param uuid1
 * @param uuid2
 */
function uuid_compare ($uuid1, $uuid2) {}

/**
 * @param uuid
 */
function uuid_is_null ($uuid) {}

/**
 * @param uuid
 */
function uuid_parse ($uuid) {}

/**
 * @param uuid
 */
function uuid_unparse ($uuid) {}

define ('UUID_TYPE_DEFAULT', 0);
define ('UUID_TYPE_TIME', 1);
define ('UUID_TYPE_DCE', 4);
define ('UUID_TYPE_NAME', 1);
define ('UUID_TYPE_RANDOM', 4);
define ('UUID_TYPE_NULL', -1);
define ('UUID_TYPE_INVALID', -42);

// End of uuid v.1.0.3
