<?php

// Start of json v.1.2.1

/**
 * Returns the JSON representation of a value
 * @link http://www.php.net/manual/en/function.json-encode.php
 * @param value mixed <p>
 * The value being encoded. Can be any type except
 * a resource.
 * </p>
 * <p>
 * This function only works with UTF-8 encoded data.
 * </p>
 * @param options int[optional] <p>
 * Bitmask consisting of JSON_HEX_QUOT,
 * JSON_HEX_TAG,
 * JSON_HEX_AMP,
 * JSON_HEX_APOS,
 * JSON_FORCE_OBJECT.
 * </p>
 * @return string a JSON encoded string on success.
 */
function json_encode ($value, $options = null) {}

/**
 * Decodes a JSON string
 * @link http://www.php.net/manual/en/function.json-decode.php
 * @param json string <p>
 * The json string being decoded.
 * </p>
 * @param assoc bool[optional] <p>
 * When true, returned objects will be converted into
 * associative arrays.
 * </p>
 * @param depth int[optional] <p>
 * User specified recursion depth.
 * </p>
 * @return mixed the value encoded in json in appropriate
 * PHP type. Values true, false and
 * null (case-insensitive) are returned as true, false
 * and &null; respectively. &null; is returned if the
 * json cannot be decoded or if the encoded
 * data is deeper than the recursion limit.
 */
function json_decode ($json, $assoc = null, $depth = null) {}

/**
 * Returns the last error occurred
 * @link http://www.php.net/manual/en/function.json-last-error.php
 * @return int an integer, the value can be one of the following 
 * constants:
 */
function json_last_error () {}

define ('JSON_HEX_TAG', 1);
define ('JSON_HEX_AMP', 2);
define ('JSON_HEX_APOS', 4);
define ('JSON_HEX_QUOT', 8);
define ('JSON_FORCE_OBJECT', 16);
define ('JSON_ERROR_NONE', 0);
define ('JSON_ERROR_DEPTH', 1);
define ('JSON_ERROR_STATE_MISMATCH', 2);
define ('JSON_ERROR_CTRL_CHAR', 3);
define ('JSON_ERROR_SYNTAX', 4);

// End of json v.1.2.1
?>
