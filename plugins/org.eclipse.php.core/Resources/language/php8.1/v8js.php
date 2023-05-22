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
	/**
	 * The V8 Javascript Engine version.
	const V8_VERSION = "11.0.226.13";
	/**
	 * No flags.
	const FLAG_NONE = 1;
	/**
	 * Forces all JS objects to be associative arrays in PHP.
	const FLAG_FORCE_ARRAY = 2;
	const FLAG_PROPAGATE_PHP_EXCEPTIONS = 4;


	/**
	 * Construct a new V8Js object
	 * @link http://www.php.net/manual/en/v8js.construct.php
	 * @param string $object_name [optional] The name of the object passed to Javascript.
	 * @param array $variables [optional] Map of PHP variables that will be available in Javascript. Must be an associative array
	 * in format array("name-for-js" =&gt; "name-of-php-variable"). Defaults to empty array.
	 * @param array $extensions [optional] List of extensions registered using V8Js::registerExtension which should be available 
	 * in the Javascript context of the created V8Js object.
	 * <p>
	 * Extensions registered to be enabled automatically do not need to be listed in this array.
	 * Also if an extension has dependencies, those dependencies can be omitted as well. Defaults to empty array.
	 * </p>
	 * <p>Extensions registered to be enabled automatically do not need to be listed in this array.
	 * Also if an extension has dependencies, those dependencies can be omitted as well. Defaults to empty array.</p>
	 * @param bool $report_uncaught_exceptions [optional] Controls whether uncaught Javascript exceptions are reported immediately or not. Defaults to true. If set to false
	 * the uncaught exception can be accessed using V8Js::getPendingException.
	 * @return string 
	 */
	public function __construct (string $object_name = '"PHP"', array $variables = 'array()', array $extensions = 'array()', bool $report_uncaught_exceptions = true): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function __sleep () {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup () {}

	/**
	 * Execute a string as Javascript code
	 * @link http://www.php.net/manual/en/v8js.executestring.php
	 * @param string $script The code string to be executed.
	 * @param string $identifier [optional] Identifier string for the executed code. Used for debugging.
	 * @param int $flags [optional] Execution flags. This value must be one of the
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
	 * <p>V8Js::FLAG_NONE: no flags</p>
	 * <p>V8Js::FLAG_FORCE_ARRAY: forces all Javascript
	 * objects passed to PHP to be associative arrays</p>
	 * @return mixed Returns the last variable instantiated in the Javascript code converted to matching PHP variable type.
	 */
	public function executeString (string $script, string $identifier = '"V8Js::executeString()"', int $flags = \V8Js::FLAG_NONE): mixed {}

	/**
	 * {@inheritdoc}
	 * @param mixed $script
	 * @param mixed $identifier [optional]
	 */
	public function compileString ($script = null, $identifier = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $script
	 * @param mixed $flags [optional]
	 * @param mixed $time_limit [optional]
	 * @param mixed $memory_limit [optional]
	 */
	public function executeScript ($script = null, $flags = NULL, $time_limit = NULL, $memory_limit = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $base
	 * @param mixed $module_id
	 */
	public function setModuleNormaliser ($base = null, $module_id = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $callable
	 */
	public function setModuleLoader ($callable = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $callable
	 */
	public function setExceptionFilter ($callable = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $time_limit
	 */
	public function setTimeLimit ($time_limit = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $memory_limit
	 */
	public function setMemoryLimit ($memory_limit = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $average_object_size
	 */
	public function setAverageObjectSize ($average_object_size = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $script
	 */
	public static function createSnapshot ($script = null) {}

}

/**
 * @link http://www.php.net/manual/en/class.v8jsexception.php
 */
class V8JsException extends RuntimeException implements Stringable, Throwable {

	protected $JsFileName;

	protected $JsLineNumber;

	protected $JsSourceLine;

	protected $JsTrace;

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

final class V8JsScriptException extends V8JsException implements Throwable, Stringable {

	protected $JsFileName;

	protected $JsLineNumber;

	protected $JsStartColumn;

	protected $JsEndColumn;

	protected $JsSourceLine;

	protected $JsTrace;

	/**
	 * {@inheritdoc}
	 */
	final public function getJsFileName () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getJsLineNumber () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getJsStartColumn () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getJsEndColumn () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getJsSourceLine () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getJsTrace () {}

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

final class V8JsTimeLimitException extends V8JsException implements Throwable, Stringable {

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

final class V8JsMemoryLimitException extends V8JsException implements Throwable, Stringable {

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

final class V8Object  {

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function __sleep () {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup () {}

}

final class V8Function  {

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function __sleep () {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup () {}

}

final class V8Generator implements Iterator, Traversable {

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function __sleep () {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	public function current (): mixed {}

	/**
	 * {@inheritdoc}
	 */
	public function key (): mixed {}

	/**
	 * {@inheritdoc}
	 */
	public function next (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function valid (): bool {}

}
// End of v8js v.2.1.2
