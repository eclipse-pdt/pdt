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
	 * @return mixed Returns data which can be serialized by json_encode,
	 * which is a value of any type other than a resource.
	 */
	abstract public function jsonSerialize (): mixed;

}

/**
 * Exception thrown if JSON_THROW_ON_ERROR option is
 * set for json_encode or
 * json_decode. code contains the error
 * type, for possible values see json_last_error.
 * @link http://www.php.net/manual/en/class.jsonexception.php
 */
class JsonException extends Exception implements Throwable, Stringable {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return int Returns the exception code as int in
	 * Exception but possibly as other type in
	 * Exception descendants (for example as
	 * string in PDOException).
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Returns the filename in which the exception was created.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Returns the line number where the exception was created.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString (): string {}

}

/**
 * Returns the JSON representation of a value
 * @link http://www.php.net/manual/en/function.json-encode.php
 * @param mixed $value 
 * @param int $flags [optional] 
 * @param int $depth [optional] 
 * @return string|false Returns a JSON encoded string on success or false on failure.
 */
function json_encode (mixed $value, int $flags = null, int $depth = 512): string|false {}

/**
 * Decodes a JSON string
 * @link http://www.php.net/manual/en/function.json-decode.php
 * @param string $json 
 * @param bool|null $associative [optional] 
 * @param int $depth [optional] 
 * @param int $flags [optional] 
 * @return mixed Returns the value encoded in json in appropriate
 * PHP type. Values true, false and
 * null are returned as true, false and null
 * respectively. null is returned if the json cannot
 * be decoded or if the encoded data is deeper than the nesting limit.
 */
function json_decode (string $json, ?bool $associative = null, int $depth = 512, int $flags = null): mixed {}

/**
 * Returns the last error occurred
 * @link http://www.php.net/manual/en/function.json-last-error.php
 * @return int Returns an integer, the value can be one of the following 
 * constants:
 */
function json_last_error (): int {}

/**
 * Returns the error string of the last json_encode() or json_decode() call
 * @link http://www.php.net/manual/en/function.json-last-error-msg.php
 * @return string Returns the error message on success, or "No error" if no
 * error has occurred.
 */
function json_last_error_msg (): string {}


/**
 * All &lt; and &gt; are converted to \u003C and \u003E.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_HEX_TAG', 1);

/**
 * All &amp; are converted to \u0026.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_HEX_AMP', 2);

/**
 * All ' are converted to \u0027.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_HEX_APOS', 4);

/**
 * All " are converted to \u0022.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_HEX_QUOT', 8);

/**
 * Outputs an object rather than an array when a non-associative array is
 * used. Especially useful when the recipient of the output is expecting
 * an object and the array is empty.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_FORCE_OBJECT', 16);

/**
 * Encodes numeric strings as numbers.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_NUMERIC_CHECK', 32);

/**
 * Don't escape /.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_UNESCAPED_SLASHES', 64);

/**
 * Use whitespace in returned data to format it.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_PRETTY_PRINT', 128);

/**
 * Encode multibyte Unicode characters literally (default is to escape as
 * \uXXXX).
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_UNESCAPED_UNICODE', 256);

/**
 * Substitute some unencodable values instead of failing.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_PARTIAL_OUTPUT_ON_ERROR', 512);

/**
 * Ensures that float values are always encoded as a float value.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_PRESERVE_ZERO_FRACTION', 1024);

/**
 * The line terminators are kept unescaped when
 * JSON_UNESCAPED_UNICODE is supplied. It uses the same
 * behaviour as it was before PHP 7.1 without this constant.
 * Available as of PHP 7.1.0.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_UNESCAPED_LINE_TERMINATORS', 2048);

/**
 * Decodes JSON objects as PHP array. This option can be added automatically
 * by calling json_decode with the second parameter
 * equal to true.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_OBJECT_AS_ARRAY', 1);

/**
 * Decodes large integers as their original string value.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_BIGINT_AS_STRING', 2);

/**
 * Ignore invalid UTF-8 characters.
 * Available as of PHP 7.2.0.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_INVALID_UTF8_IGNORE', 1048576);

/**
 * Convert invalid UTF-8 characters to \0xfffd
 * (Unicode Character 'REPLACEMENT CHARACTER')
 * Available as of PHP 7.2.0.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
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
 * @var int
 */
define ('JSON_THROW_ON_ERROR', 4194304);

/**
 * No error has occurred.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_ERROR_NONE', 0);

/**
 * The maximum stack depth has been exceeded.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_ERROR_DEPTH', 1);

/**
 * Occurs with underflow or with the modes mismatch.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_ERROR_STATE_MISMATCH', 2);

/**
 * Control character error, possibly incorrectly encoded.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_ERROR_CTRL_CHAR', 3);

/**
 * Syntax error.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_ERROR_SYNTAX', 4);

/**
 * Malformed UTF-8 characters, possibly incorrectly encoded.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_ERROR_UTF8', 5);

/**
 * The object or array passed to json_encode include
 * recursive references and cannot be encoded.
 * If the JSON_PARTIAL_OUTPUT_ON_ERROR option was
 * given, null will be encoded in the place of the recursive reference.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_ERROR_RECURSION', 6);

/**
 * The value passed to json_encode includes either
 * NAN
 * or INF.
 * If the JSON_PARTIAL_OUTPUT_ON_ERROR option was
 * given, 0 will be encoded in the place of these
 * special numbers.
 * @link http://www.php.net/manual/en/language.types.float.nan.php
 * @var int
 */
define ('JSON_ERROR_INF_OR_NAN', 7);

/**
 * A value of an unsupported type was given to
 * json_encode, such as a resource.
 * If the JSON_PARTIAL_OUTPUT_ON_ERROR option was
 * given, null will be encoded in the place of the unsupported value.
 * @link http://www.php.net/manual/en/language.types.resource.php
 * @var int
 */
define ('JSON_ERROR_UNSUPPORTED_TYPE', 8);

/**
 * A key starting with \u0000 character was in the string passed to
 * json_decode when decoding a JSON object into a PHP
 * object.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_ERROR_INVALID_PROPERTY_NAME', 9);

/**
 * Single unpaired UTF-16 surrogate in unicode escape contained in the
 * JSON string passed to json_decode.
 * @link http://www.php.net/manual/en/json.constants.php
 * @var int
 */
define ('JSON_ERROR_UTF16', 10);
define ('JSON_ERROR_NON_BACKED_ENUM', 11);

// End of json v.8.2.6
