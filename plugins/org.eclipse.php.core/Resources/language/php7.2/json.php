<?php

// Start of json v.1.5.0

/**
 * Objects implementing JsonSerializable
 * can customize their JSON representation when encoded with
 * json_encode.
 * @link http://www.php.net/manual/en/class.jsonserializable.php
 */
interface JsonSerializable  {

	/**
	 * Specify data which should be serialized to JSON
	 * @link http://www.php.net/manual/en/jsonserializable.jsonserialize.php
	 * @return mixed data which can be serialized by json_encode,
	 * which is a value of any type other than a resource.
	 */
	abstract public function jsonSerialize ();

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
 * json.implementation.superset
 * @param int $options [optional] Bitmask consisting of JSON_HEX_QUOT,
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
 * @param int $depth [optional] Set the maximum depth. Must be greater than zero.
 * @return string a JSON encoded string on success or false on failure.
 */
function json_encode ($value, int $options = null, int $depth = null) {}

/**
 * Decodes a JSON string
 * @link http://www.php.net/manual/en/function.json-decode.php
 * @param string $json <p>
 * The json string being decoded.
 * </p>
 * <p>
 * This function only works with UTF-8 encoded strings.
 * </p>
 * json.implementation.superset
 * @param bool $assoc [optional] When true, returned objects will be converted into
 * associative arrays.
 * @param int $depth [optional] User specified recursion depth.
 * @param int $options [optional] Bitmask of JSON decode options. Currently there are two supported
 * options. The first is JSON_BIGINT_AS_STRING that
 * allows casting big integers to string instead of floats which is the
 * default. The second option is JSON_OBJECT_AS_ARRAY
 * that has the same effect as setting assoc to
 * true.
 * @return mixed the value encoded in json in appropriate
 * PHP type. Values true, false and
 * null are returned as true, false and null
 * respectively. null is returned if the json cannot
 * be decoded or if the encoded data is deeper than the recursion limit.
 */
function json_decode (string $json, bool $assoc = null, int $depth = null, int $options = null) {}

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
 * Encode multibyte Unicode characters literally (default is to escape as
 * \uXXXX).
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
 * Ensures that float values are always encoded as a float
 * value.
 * Available since PHP 5.6.6.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_PRESERVE_ZERO_FRACTION', 1024);

/**
 * The line terminators are kept unescaped when
 * JSON_UNESCAPED_UNICODE is supplied. It uses the same
 * behaviour as it was before PHP 7.1 without this constant.
 * Available since PHP 7.1.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_UNESCAPED_LINE_TERMINATORS', 2048);

/**
 * Decodes JSON objects as PHP array. This option can be added automatically
 * by calling json_decode with the second parameter
 * equal to true.
 * Available since PHP 5.4.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_OBJECT_AS_ARRAY', 1);

/**
 * Decodes large integers as their original string value.
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
 * Malformed UTF-8 characters, possibly incorrectly encoded.
 * Available since PHP 5.3.3.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_UTF8', 5);

/**
 * The object or array passed to json_encode include
 * recursive references and cannot be encoded.
 * If the JSON_PARTIAL_OUTPUT_ON_ERROR option was
 * given, null will be encoded in the place of the recursive reference.
 * Available since PHP 5.5.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_RECURSION', 6);

/**
 * The value passed to json_encode includes either
 * NAN
 * or INF.
 * If the JSON_PARTIAL_OUTPUT_ON_ERROR option was
 * given, 0 will be encoded in the place of these
 * special numbers.
 * Available since PHP 5.5.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_INF_OR_NAN', 7);

/**
 * A value of an unsupported type was given to
 * json_encode, such as a resource.
 * If the JSON_PARTIAL_OUTPUT_ON_ERROR option was
 * given, null will be encoded in the place of the unsupported value.
 * Available since PHP 5.5.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_UNSUPPORTED_TYPE', 8);

/**
 * A key starting with \u0000 character was in the string passed to
 * json_decode when decoding a JSON object into a PHP
 * object.
 * Available since PHP 7.0.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_INVALID_PROPERTY_NAME', 9);

/**
 * Single unpaired UTF-16 surrogate in unicode escape contained in the
 * JSON string passed to json_encode.
 * Available since PHP 7.0.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_UTF16', 10);

// End of json v.1.5.0
