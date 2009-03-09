<?php

// Start of json v.1.2.1

/**
 * Returns the JSON representation of a value
 * @link http://php.net/manual/en/function.json-encode.php
 * @param value mixed <p>
 * The value being encoded. Can be any type except
 * a resource.
 * </p>
 * <p>
 * This function only works with UTF-8 encoded data.
 * </p>
 * @param options int[optional] <p>
 * Bitmask consisting of PHP_JSON_HEX_QUOT,
 * PHP_JSON_HEX_TAG,
 * PHP_JSON_HEX_AMP,
 * PHP_JSON_HEX_APOS. Defaults to 0.
 * </p>
 * @return string a JSON encoded string on success.
 */
function json_encode ($value, $options = null) {}

/**
 * Decodes a JSON string
 * @link http://php.net/manual/en/function.json-decode.php
 * @param json string <p>
 * The json string being decoded.
 * </p>
 * @param assoc bool[optional] <p>
 * When true, returned objects will be converted into
 * associative arrays.
 * </p>
 * @param depth int[optional] <p>
 * </p>
 * @return mixed an object or if the optional
 * assoc parameter is true, an associative 
 * array is instead returned.
 */
function json_decode ($json, $assoc = null, $depth = null) {}

/**
 * Returns the last error occured
 * @link http://php.net/manual/en/function.json-last-error.php
 * @return int an integer, the value can be one of the following 
 * constants:
 */
function json_last_error () {}

define ('JSON_HEX_TAG', 1);
define ('JSON_HEX_AMP', 2);
define ('JSON_HEX_APOS', 4);
define ('JSON_HEX_QUOT', 8);

/**
 * No error has occurred.
 * @link http://php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_NONE', 0);

/**
 * The maximum stack depth has been exceeded.
 * @link http://php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_DEPTH', 1);
define ('JSON_ERROR_STATE_MISMATCH', 2);

/**
 * Control character error, possibly incorrectly encoded.
 * @link http://php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_CTRL_CHAR', 3);

/**
 * Syntax error.
 * @link http://php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_SYNTAX', 4);

// End of json v.1.2.1
?>
