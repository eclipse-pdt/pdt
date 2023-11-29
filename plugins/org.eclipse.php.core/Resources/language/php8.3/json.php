<?php

// Start of json v.8.3.0

interface JsonSerializable  {

	/**
	 * {@inheritdoc}
	 */
	abstract public function jsonSerialize ();

}

class JsonException extends Exception implements Throwable, Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

/**
 * {@inheritdoc}
 * @param mixed $value
 * @param int $flags [optional]
 * @param int $depth [optional]
 */
function json_encode (mixed $value = null, int $flags = 0, int $depth = 512): string|false {}

/**
 * {@inheritdoc}
 * @param string $json
 * @param bool|null $associative [optional]
 * @param int $depth [optional]
 * @param int $flags [optional]
 */
function json_decode (string $json, ?bool $associative = NULL, int $depth = 512, int $flags = 0): mixed {}

/**
 * {@inheritdoc}
 * @param string $json
 * @param int $depth [optional]
 * @param int $flags [optional]
 */
function json_validate (string $json, int $depth = 512, int $flags = 0): bool {}

/**
 * {@inheritdoc}
 */
function json_last_error (): int {}

/**
 * {@inheritdoc}
 */
function json_last_error_msg (): string {}

define ('JSON_HEX_TAG', 1);
define ('JSON_HEX_AMP', 2);
define ('JSON_HEX_APOS', 4);
define ('JSON_HEX_QUOT', 8);
define ('JSON_FORCE_OBJECT', 16);
define ('JSON_NUMERIC_CHECK', 32);
define ('JSON_UNESCAPED_SLASHES', 64);
define ('JSON_PRETTY_PRINT', 128);
define ('JSON_UNESCAPED_UNICODE', 256);
define ('JSON_PARTIAL_OUTPUT_ON_ERROR', 512);
define ('JSON_PRESERVE_ZERO_FRACTION', 1024);
define ('JSON_UNESCAPED_LINE_TERMINATORS', 2048);
define ('JSON_OBJECT_AS_ARRAY', 1);
define ('JSON_BIGINT_AS_STRING', 2);
define ('JSON_INVALID_UTF8_IGNORE', 1048576);
define ('JSON_INVALID_UTF8_SUBSTITUTE', 2097152);
define ('JSON_THROW_ON_ERROR', 4194304);
define ('JSON_ERROR_NONE', 0);
define ('JSON_ERROR_DEPTH', 1);
define ('JSON_ERROR_STATE_MISMATCH', 2);
define ('JSON_ERROR_CTRL_CHAR', 3);
define ('JSON_ERROR_SYNTAX', 4);
define ('JSON_ERROR_UTF8', 5);
define ('JSON_ERROR_RECURSION', 6);
define ('JSON_ERROR_INF_OR_NAN', 7);
define ('JSON_ERROR_UNSUPPORTED_TYPE', 8);
define ('JSON_ERROR_INVALID_PROPERTY_NAME', 9);
define ('JSON_ERROR_UTF16', 10);
define ('JSON_ERROR_NON_BACKED_ENUM', 11);

// End of json v.8.3.0
