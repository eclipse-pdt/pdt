<?php

// Start of v8js v.2.1.2

/**
 * This is the core class for V8Js extension. Each instance created from
 * this class has own context in which all JavaScript is compiled and
 * executed.
 * <p>See V8Js::__construct for more information.</p>
 * @link http://www.php.net/manual/en/class.v8js.php
 */
class V8Js  {
	const V8_VERSION = "11.0.226.13";
	const FLAG_NONE = 1;
	const FLAG_FORCE_ARRAY = 2;
	const FLAG_PROPAGATE_PHP_EXCEPTIONS = 4;


	/**
	 * Construct a new V8Js object
	 * @link http://www.php.net/manual/en/v8js.construct.php
	 * @param mixed $object_name [optional]
	 * @param mixed $variables [optional]
	 * @param mixed $snapshot_blob [optional]
	 */
	public function __construct ($object_name = null, $variables = null, $snapshot_blob = null) {}

	final public function __sleep () {}

	final public function __wakeup () {}

	/**
	 * Execute a string as Javascript code
	 * @link http://www.php.net/manual/en/v8js.executestring.php
	 * @param string $script The code string to be executed.
	 * @param string $identifier [optional] Identifier string for the executed code. Used for debugging.
	 * @param int $flags [optional] <p>
	 * Execution flags. This value must be one of the
	 * V8Js::FLAG_&#42; constants, defaulting to
	 * V8Js::FLAG_NONE.
	 * <p>
	 * <br>
	 * <p>
	 * V8Js::FLAG_NONE: no flags
	 * </p>
	 * <br>
	 * <p>
	 * V8Js::FLAG_FORCE_ARRAY: forces all Javascript
	 * objects passed to PHP to be associative arrays
	 * </p>
	 * </p>
	 * </p>
	 * @return mixed the last variable instantiated in the Javascript code converted to matching PHP variable type.
	 */
	public function executeString (string $script, string $identifier = null, int $flags = null) {}

	/**
	 * @param mixed $script
	 * @param mixed $identifier [optional]
	 */
	public function compileString ($script = null, $identifier = null) {}

	/**
	 * @param mixed $script
	 * @param mixed $flags [optional]
	 * @param mixed $time_limit [optional]
	 * @param mixed $memory_limit [optional]
	 */
	public function executeScript ($script = null, $flags = null, $time_limit = null, $memory_limit = null) {}

	/**
	 * @param mixed $base
	 * @param mixed $module_id
	 */
	public function setModuleNormaliser ($base = null, $module_id = null) {}

	/**
	 * @param mixed $callable
	 */
	public function setModuleLoader ($callable = null) {}

	/**
	 * @param mixed $callable
	 */
	public function setExceptionFilter ($callable = null) {}

	/**
	 * @param mixed $time_limit
	 */
	public function setTimeLimit ($time_limit = null) {}

	/**
	 * @param mixed $memory_limit
	 */
	public function setMemoryLimit ($memory_limit = null) {}

	/**
	 * @param mixed $average_object_size
	 */
	public function setAverageObjectSize ($average_object_size = null) {}

	/**
	 * @param mixed $script
	 */
	public static function createSnapshot ($script = null) {}

}

/**
 * @link http://www.php.net/manual/en/class.v8jsexception.php
 */
class V8JsException extends RuntimeException implements Stringable, Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	protected $JsFileName;

	protected $JsLineNumber;

	protected $JsSourceLine;

	protected $JsTrace;

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

final class V8JsScriptException extends V8JsException implements Throwable, Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $JsFileName;
	protected $JsLineNumber;
	protected $JsStartColumn;
	protected $JsEndColumn;
	protected $JsSourceLine;
	protected $JsTrace;


	final public function getJsFileName () {}

	final public function getJsLineNumber () {}

	final public function getJsStartColumn () {}

	final public function getJsEndColumn () {}

	final public function getJsSourceLine () {}

	final public function getJsTrace () {}

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

final class V8JsTimeLimitException extends V8JsException implements Throwable, Stringable {
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

final class V8JsMemoryLimitException extends V8JsException implements Throwable, Stringable {
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

final class V8Object  {

	public function __construct () {}

	final public function __sleep () {}

	final public function __wakeup () {}

}

final class V8Function  {

	public function __construct () {}

	final public function __sleep () {}

	final public function __wakeup () {}

}

final class V8Generator implements Iterator, Traversable {

	public function __construct () {}

	final public function __sleep () {}

	final public function __wakeup () {}

	public function current (): mixed {}

	public function key (): mixed {}

	public function next (): void {}

	public function rewind (): void {}

	public function valid (): bool {}

}
// End of v8js v.2.1.2
