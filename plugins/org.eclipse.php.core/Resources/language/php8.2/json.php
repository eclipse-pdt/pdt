<?php

// Start of json v.8.2.6

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
	abstract public function jsonSerialize ()

}

/**
 * Exception thrown if JSON_THROW_ON_ERROR option is
 * set for json_encode or
 * json_decode. code contains the error
 * type, for possible values see json_last_error.
 * @link http://www.php.net/manual/en/class.jsonexception.php
 */
class JsonException extends Exception implements Throwable, Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

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
 * @param int $flags [optional] Bitmask consisting of
 * JSON_FORCE_OBJECT,
 * JSON_HEX_QUOT,
 * JSON_HEX_TAG,
 * JSON_HEX_AMP,
 * JSON_HEX_APOS,
 * JSON_INVALID_UTF8_IGNORE,
 * JSON_INVALID_UTF8_SUBSTITUTE,
 * JSON_NUMERIC_CHECK,
 * JSON_PARTIAL_OUTPUT_ON_ERROR,
 * JSON_PRESERVE_ZERO_FRACTION,
 * JSON_PRETTY_PRINT,
 * JSON_UNESCAPED_LINE_TERMINATORS,
 * JSON_UNESCAPED_SLASHES,
 * JSON_UNESCAPED_UNICODE,
 * JSON_THROW_ON_ERROR.
 * The behaviour of these constants is described on the
 * JSON constants page.
 * @param int $depth [optional] Set the maximum depth. Must be greater than zero.
 * @return mixed a JSON encoded string on success or false on failure.
 */
function json_encode ($value, int $flags = null, int $depth = null): string|false {}

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
 * @param mixed $associative [optional] When true, JSON objects will be returned as
 * associative arrays; when false, JSON objects will be returned as objects.
 * When null, JSON objects will be returned as associative arrays or
 * objects depending on whether JSON_OBJECT_AS_ARRAY
 * is set in the flags.
 * @param int $depth [optional] Maximum nesting depth of the structure being decoded.
 * The value must be greater than 0,
 * and less than or equal to 2147483647.
 * @param int $flags [optional] Bitmask of
 * JSON_BIGINT_AS_STRING,
 * JSON_INVALID_UTF8_IGNORE,
 * JSON_INVALID_UTF8_SUBSTITUTE,
 * JSON_OBJECT_AS_ARRAY,
 * JSON_THROW_ON_ERROR.
 * The behaviour of these constants is described on the
 * JSON constants page.
 * @return mixed the value encoded in json in appropriate
 * PHP type. Values true, false and
 * null are returned as true, false and null
 * respectively. null is returned if the json cannot
 * be decoded or if the encoded data is deeper than the nesting limit.
 */
function json_decode (string $json, $associative = null, int $depth = null, int $flags = null): mixed {}

/**
 * Returns the last error occurred
 * @link http://www.php.net/manual/en/function.json-last-error.php
 * @return int an integer, the value can be one of the following 
 * constants:
 */
function json_last_error (): int {}

/**
 * Returns the error string of the last json_encode() or json_decode() call
 * @link http://www.php.net/manual/en/function.json-last-error-msg.php
 * @return string the error message on success, or "No error" if no
 * error has occurred.
 */
function json_last_error_msg (): string {}


/**
 * All &lt; and &gt; are converted to \u003C and \u003E.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_HEX_TAG', 1);

/**
 * All &amp; are converted to \u0026.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_HEX_AMP', 2);

/**
 * All ' are converted to \u0027.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_HEX_APOS', 4);

/**
 * All " are converted to \u0022.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_HEX_QUOT', 8);

/**
 * Outputs an object rather than an array when a non-associative array is
 * used. Especially useful when the recipient of the output is expecting
 * an object and the array is empty.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_FORCE_OBJECT', 16);

/**
 * Encodes numeric strings as numbers.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_NUMERIC_CHECK', 32);

/**
 * Don't escape /.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_UNESCAPED_SLASHES', 64);

/**
 * Use whitespace in returned data to format it.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_PRETTY_PRINT', 128);

/**
 * Encode multibyte Unicode characters literally (default is to escape as
 * \uXXXX).
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_UNESCAPED_UNICODE', 256);

/**
 * Substitute some unencodable values instead of failing.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_PARTIAL_OUTPUT_ON_ERROR', 512);

/**
 * Ensures that float values are always encoded as a float value.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_PRESERVE_ZERO_FRACTION', 1024);

/**
 * The line terminators are kept unescaped when
 * JSON_UNESCAPED_UNICODE is supplied. It uses the same
 * behaviour as it was before PHP 7.1 without this constant.
 * Available as of PHP 7.1.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_UNESCAPED_LINE_TERMINATORS', 2048);

/**
 * Decodes JSON objects as PHP array. This option can be added automatically
 * by calling json_decode with the second parameter
 * equal to true.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_OBJECT_AS_ARRAY', 1);

/**
 * Decodes large integers as their original string value.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_BIGINT_AS_STRING', 2);

/**
 * Ignore invalid UTF-8 characters.
 * Available as of PHP 7.2.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_INVALID_UTF8_IGNORE', 1048576);

/**
 * Convert invalid UTF-8 characters to \0xfffd
 * (Unicode Character 'REPLACEMENT CHARACTER')
 * Available as of PHP 7.2.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_INVALID_UTF8_SUBSTITUTE', 2097152);

/**
 * Throws JsonException if an error occurs instead 
 * of setting the global error state that is retrieved with
 * json_last_error and json_last_error_msg. 
 * JSON_PARTIAL_OUTPUT_ON_ERROR takes precedence over
 * JSON_THROW_ON_ERROR.
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_THROW_ON_ERROR', 4194304);

/**
 * No error has occurred.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_NONE', 0);

/**
 * The maximum stack depth has been exceeded.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_DEPTH', 1);

/**
 * Occurs with underflow or with the modes mismatch.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_STATE_MISMATCH', 2);

/**
 * Control character error, possibly incorrectly encoded.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_CTRL_CHAR', 3);

/**
 * Syntax error.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_SYNTAX', 4);

/**
 * Malformed UTF-8 characters, possibly incorrectly encoded.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_UTF8', 5);

/**
 * The object or array passed to json_encode include
 * recursive references and cannot be encoded.
 * If the JSON_PARTIAL_OUTPUT_ON_ERROR option was
 * given, null will be encoded in the place of the recursive reference.
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
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_INF_OR_NAN', 7);

/**
 * A value of an unsupported type was given to
 * json_encode, such as a resource.
 * If the JSON_PARTIAL_OUTPUT_ON_ERROR option was
 * given, null will be encoded in the place of the unsupported value.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_UNSUPPORTED_TYPE', 8);

/**
 * A key starting with \u0000 character was in the string passed to
 * json_decode when decoding a JSON object into a PHP
 * object.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_INVALID_PROPERTY_NAME', 9);

/**
 * Single unpaired UTF-16 surrogate in unicode escape contained in the
 * JSON string passed to json_decode.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_UTF16', 10);
define ('JSON_ERROR_NON_BACKED_ENUM', 11);

// End of json v.8.2.6
