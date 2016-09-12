<?php

// Start of json v.1.5.0

interface JsonSerializable  {

	/**
	 * Specify data which should be serialized to JSON
	 * @link http://www.php.net/manual/en/jsonserializable.jsonserialize.php
	 * @return mixed data which can be serialized by json_encode,
	 * which is a value of any type other than a resource.
	 */
	abstract public function jsonSerialize () {}

}

/**
 * Returns the JSON representation of a value
 * @link http://www.php.net/manual/en/function.json-encode.php
 * @param mixed $value <p>
 * The value being encoded. Can be any type except
 * a resource.
 * </p>
 * <p>
 * All string data must be UTF-8 encoded.
 * </p>
 * &json.implementation.superset;
 * @param int $options [optional] <p>
 * Bitmask consisting of JSON_HEX_QUOT,
 * JSON_HEX_TAG,
 * JSON_HEX_AMP,
 * JSON_HEX_APOS,
 * JSON_NUMERIC_CHECK,
 * JSON_PRETTY_PRINT,
 * JSON_UNESCAPED_SLASHES,
 * JSON_FORCE_OBJECT,
 * JSON_PRESERVE_ZERO_FRACTION,
 * JSON_UNESCAPED_UNICODE,
 * JSON_PARTIAL_OUTPUT_ON_ERROR. The behaviour of these
 * constants is described on the
 * JSON constants page.
 * </p>
 * @param int $depth [optional] <p>
 * Set the maximum depth. Must be greater than zero.
 * </p>
 * @return string a JSON encoded string on success or false on failure.
 */
function json_encode ($value, $options = null, $depth = null) {}

/**
 * Decodes a JSON string
 * @link http://www.php.net/manual/en/function.json-decode.php
 * @param string $json <p>
 * The json string being decoded.
 * </p>
 * <p>
 * This function only works with UTF-8 encoded strings.
 * </p>
 * &json.implementation.superset;
 * @param bool $assoc [optional] <p>
 * When true, returned objects will be converted into
 * associative arrays.
 * </p>
 * @param int $depth [optional] <p>
 * User specified recursion depth.
 * </p>
 * @param int $options [optional] <p>
 * Bitmask of JSON decode options. Currently only
 * JSON_BIGINT_AS_STRING
 * is supported (default is to cast large integers as floats)
 * </p>
 * @return mixed the value encoded in json in appropriate
 * PHP type. Values true, false and
 * null are returned as true, false and &null;
 * respectively. &null; is returned if the json cannot
 * be decoded or if the encoded data is deeper than the recursion limit.
 */
function json_decode ($json, $assoc = null, $depth = null, $options = null) {}

/**
 * Returns the last error occurred
 * @link http://www.php.net/manual/en/function.json-last-error.php
 * @return int an integer, the value can be one of the following 
 * constants:
 */
function json_last_error () {}

/**
 * Returns the error string of the last json_encode() or json_decode() call
 * @link http://www.php.net/manual/en/function.json-last-error-msg.php
 * @return string the error message on success, "No Error" if no
 * error has occurred, or false on failure.
 */
function json_last_error_msg () {}


/**
 * All &lt; and &gt; are converted to \u003C and \u003E.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_HEX_TAG', 1);

/**
 * All &amp;s are converted to \u0026.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_HEX_AMP', 2);

/**
 * All ' are converted to \u0027.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_HEX_APOS', 4);

/**
 * All " are converted to \u0022.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_HEX_QUOT', 8);

/**
 * Outputs an object rather than an array when a non-associative array is
 * used. Especially useful when the recipient of the output is expecting
 * an object and the array is empty.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_FORCE_OBJECT', 16);

/**
 * Encodes numeric strings as numbers.
 * Available since PHP 5.3.3.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_NUMERIC_CHECK', 32);

/**
 * Don't escape /.
 * Available since PHP 5.4.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_UNESCAPED_SLASHES', 64);

/**
 * Use whitespace in returned data to format it.
 * Available since PHP 5.4.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_PRETTY_PRINT', 128);

/**
 * Encode multibyte Unicode characters literally (default is to escape as \uXXXX).
 * Available since PHP 5.4.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_UNESCAPED_UNICODE', 256);

/**
 * Substitute some unencodable values instead of failing.
 * Available since PHP 5.5.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_PARTIAL_OUTPUT_ON_ERROR', 512);

/**
 * Ensures that float values are always encoded as a float value.
 * Available since PHP 5.6.6.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_PRESERVE_ZERO_FRACTION', 1024);
define ('JSON_UNESCAPED_LINE_TERMINATORS', 2048);
define ('JSON_OBJECT_AS_ARRAY', 1);

/**
 * Encodes large integers as their original string value.
 * Available since PHP 5.4.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_BIGINT_AS_STRING', 2);

/**
 * No error has occurred.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_NONE', 0);

/**
 * The maximum stack depth has been exceeded.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_DEPTH', 1);

/**
 * Occurs with underflow or with the modes mismatch.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_STATE_MISMATCH', 2);

/**
 * Control character error, possibly incorrectly encoded.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_CTRL_CHAR', 3);

/**
 * Syntax error.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_SYNTAX', 4);

/**
 * Malformed UTF-8 characters, possibly incorrectly encoded. This 
 * constant is available as of PHP 5.3.3.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_UTF8', 5);

/**
 * <p>
 * The object or array passed to json_encode include
 * recursive references and cannot be encoded.
 * If the JSON_PARTIAL_OUTPUT_ON_ERROR option was
 * given, &null; will be encoded in the place of the recursive reference.
 * </p>
 * <p>
 * This constant is available as of PHP 5.5.0.
 * </p>
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_RECURSION', 6);

/**
 * <p>
 * The value passed to json_encode includes either
 * NAN
 * or INF.
 * If the JSON_PARTIAL_OUTPUT_ON_ERROR option was
 * given, 0 will be encoded in the place of these
 * special numbers.
 * </p>
 * <p>
 * This constant is available as of PHP 5.5.0.
 * </p>
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_INF_OR_NAN', 7);

/**
 * <p>
 * A value of an unsupported type was given to
 * json_encode, such as a resource.
 * If the JSON_PARTIAL_OUTPUT_ON_ERROR option was
 * given, &null; will be encoded in the place of the unsupported value.
 * </p>
 * <p>
 * This constant is available as of PHP 5.5.0.
 * </p>
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_UNSUPPORTED_TYPE', 8);
define ('JSON_ERROR_INVALID_PROPERTY_NAME', 9);
define ('JSON_ERROR_UTF16', 10);

// End of json v.1.5.0
