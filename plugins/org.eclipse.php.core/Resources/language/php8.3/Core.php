<?php

// Start of Core v.8.3.0

interface Traversable  {
}

interface IteratorAggregate extends Traversable {

	/**
	 * {@inheritdoc}
	 */
	abstract public function getIterator ();

}

interface Iterator extends Traversable {

	/**
	 * {@inheritdoc}
	 */
	abstract public function current ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function next ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function key ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function valid ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function rewind ();

}

interface Serializable  {

	/**
	 * {@inheritdoc}
	 */
	abstract public function serialize ();

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	abstract public function unserialize (string $data);

}

interface ArrayAccess  {

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	abstract public function offsetExists (mixed $offset = null);

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	abstract public function offsetGet (mixed $offset = null);

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 * @param mixed $value
	 */
	abstract public function offsetSet (mixed $offset = null, mixed $value = null);

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	abstract public function offsetUnset (mixed $offset = null);

}

interface Countable  {

	/**
	 * {@inheritdoc}
	 */
	abstract public function count ();

}

interface Stringable  {

	/**
	 * {@inheritdoc}
	 */
	abstract public function __toString (): string;

}

final class InternalIterator implements Iterator, Traversable {

	/**
	 * {@inheritdoc}
	 */
	private function __construct () {}

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
	public function valid (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind (): void {}

}

interface Throwable extends Stringable {

	/**
	 * {@inheritdoc}
	 */
	abstract public function getMessage (): string;

	/**
	 * {@inheritdoc}
	 */
	abstract public function getCode ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function getFile (): string;

	/**
	 * {@inheritdoc}
	 */
	abstract public function getLine (): int;

	/**
	 * {@inheritdoc}
	 */
	abstract public function getTrace (): array;

	/**
	 * {@inheritdoc}
	 */
	abstract public function getPrevious (): ?Throwable;

	/**
	 * {@inheritdoc}
	 */
	abstract public function getTraceAsString (): string;

	/**
	 * {@inheritdoc}
	 */
	abstract public function __toString (): string;

}

class Exception implements Stringable, Throwable {

	protected $message;

	protected $code;

	protected string $file;

	protected int $line;

	/**
	 * {@inheritdoc}
	 */
	private function __clone (): void {}

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

class ErrorException extends Exception implements Throwable, Stringable {

	protected int $severity;

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param int $severity [optional]
	 * @param string|null $filename [optional]
	 * @param int|null $line [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, int $severity = 1, ?string $filename = NULL, ?int $line = NULL, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	final public function getSeverity (): int {}

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

class Error implements Stringable, Throwable {

	protected $message;

	protected $code;

	protected string $file;

	protected int $line;

	/**
	 * {@inheritdoc}
	 */
	private function __clone (): void {}

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

class CompileError extends Error implements Throwable, Stringable {

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

class ParseError extends CompileError implements Stringable, Throwable {

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

class TypeError extends Error implements Throwable, Stringable {

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

class ArgumentCountError extends TypeError implements Stringable, Throwable {

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

class ValueError extends Error implements Throwable, Stringable {

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

class ArithmeticError extends Error implements Throwable, Stringable {

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

class DivisionByZeroError extends ArithmeticError implements Stringable, Throwable {

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

class UnhandledMatchError extends Error implements Throwable, Stringable {

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

final class Closure  {

	/**
	 * {@inheritdoc}
	 */
	private function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param Closure $closure
	 * @param object|null $newThis
	 * @param object|string|null $newScope [optional]
	 */
	public static function bind (Closure $closure, ?object $newThis = null, object|string|null $newScope = 'static'): ?Closure {}

	/**
	 * {@inheritdoc}
	 * @param object|null $newThis
	 * @param object|string|null $newScope [optional]
	 */
	public function bindTo (?object $newThis = null, object|string|null $newScope = 'static'): ?Closure {}

	/**
	 * {@inheritdoc}
	 * @param object $newThis
	 * @param mixed $args [optional]
	 */
	public function call (object $newThis, mixed ...$args): mixed {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public static function fromCallable (callable $callback): Closure {}

	/**
	 * {@inheritdoc}
	 */
	public function __invoke () {}

}

final class Generator implements Iterator, Traversable {

	/**
	 * {@inheritdoc}
	 */
	public function rewind (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function valid (): bool {}

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
	 * @param mixed $value
	 */
	public function send (mixed $value = null): mixed {}

	/**
	 * {@inheritdoc}
	 * @param Throwable $exception
	 */
	public function throw (Throwable $exception): mixed {}

	/**
	 * {@inheritdoc}
	 */
	public function getReturn (): mixed {}

}

class ClosedGeneratorException extends Exception implements Throwable, Stringable {

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

final class WeakReference  {

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param object $object
	 */
	public static function create (object $object): WeakReference {}

	/**
	 * {@inheritdoc}
	 */
	public function get (): ?object {}

}

final class WeakMap implements ArrayAccess, Countable, IteratorAggregate, Traversable {

	/**
	 * {@inheritdoc}
	 * @param mixed $object
	 */
	public function offsetGet ($object = null): mixed {}

	/**
	 * {@inheritdoc}
	 * @param mixed $object
	 * @param mixed $value
	 */
	public function offsetSet ($object = null, mixed $value = null): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $object
	 */
	public function offsetExists ($object = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $object
	 */
	public function offsetUnset ($object = null): void {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): Iterator {}

}

#[Attribute(1, )]
final class Attribute  {
	const TARGET_CLASS = 1;
	const TARGET_FUNCTION = 2;
	const TARGET_METHOD = 4;
	const TARGET_PROPERTY = 8;
	const TARGET_CLASS_CONSTANT = 16;
	const TARGET_PARAMETER = 32;
	const TARGET_ALL = 63;
	const IS_REPEATABLE = 64;


	public int $flags;

	/**
	 * {@inheritdoc}
	 * @param int $flags [optional]
	 */
	public function __construct (int $flags = 63) {}

}

#[Attribute(4, )]
final class ReturnTypeWillChange  {

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

}

#[Attribute(1, )]
final class AllowDynamicProperties  {

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

}

#[Attribute(32, )]
final class SensitiveParameter  {

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

}

final class SensitiveParameterValue  {

	readonly mixed $value;

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function __construct (mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getValue (): mixed {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo (): array {}

}

#[Attribute(4, )]
final class Override  {

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

}

interface UnitEnum  {

	/**
	 * {@inheritdoc}
	 */
	abstract public static function cases (): array;

}

interface BackedEnum extends UnitEnum {

	/**
	 * {@inheritdoc}
	 * @param string|int $value
	 */
	abstract public static function from (string|int $value): static;

	/**
	 * {@inheritdoc}
	 * @param string|int $value
	 */
	abstract public static function tryFrom (string|int $value): ?static;

	/**
	 * {@inheritdoc}
	 */
	abstract public static function cases (): array;

}

final class Fiber  {

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public function __construct (callable $callback) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $args [optional]
	 */
	public function start (mixed ...$args): mixed {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value [optional]
	 */
	public function resume (mixed $value = NULL): mixed {}

	/**
	 * {@inheritdoc}
	 * @param Throwable $exception
	 */
	public function throw (Throwable $exception): mixed {}

	/**
	 * {@inheritdoc}
	 */
	public function isStarted (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function isSuspended (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function isRunning (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function isTerminated (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getReturn (): mixed {}

	/**
	 * {@inheritdoc}
	 */
	public static function getCurrent (): ?Fiber {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value [optional]
	 */
	public static function suspend (mixed $value = NULL): mixed {}

}

final class FiberError extends Error implements Throwable, Stringable {

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

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

#[AllowDynamicProperties]
class stdClass  {
}

/**
 * {@inheritdoc}
 */
function zend_version (): string {}

/**
 * {@inheritdoc}
 */
function func_num_args (): int {}

/**
 * {@inheritdoc}
 * @param int $position
 */
function func_get_arg (int $position): mixed {}

/**
 * {@inheritdoc}
 */
function func_get_args (): array {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function strlen (string $string): int {}

/**
 * {@inheritdoc}
 * @param string $string1
 * @param string $string2
 */
function strcmp (string $string1, string $string2): int {}

/**
 * {@inheritdoc}
 * @param string $string1
 * @param string $string2
 * @param int $length
 */
function strncmp (string $string1, string $string2, int $length): int {}

/**
 * {@inheritdoc}
 * @param string $string1
 * @param string $string2
 */
function strcasecmp (string $string1, string $string2): int {}

/**
 * {@inheritdoc}
 * @param string $string1
 * @param string $string2
 * @param int $length
 */
function strncasecmp (string $string1, string $string2, int $length): int {}

/**
 * {@inheritdoc}
 * @param int|null $error_level [optional]
 */
function error_reporting (?int $error_level = NULL): int {}

/**
 * {@inheritdoc}
 * @param string $constant_name
 * @param mixed $value
 * @param bool $case_insensitive [optional]
 */
function define (string $constant_name, mixed $value = null, bool $case_insensitive = false): bool {}

/**
 * {@inheritdoc}
 * @param string $constant_name
 */
function defined (string $constant_name): bool {}

/**
 * {@inheritdoc}
 * @param object $object [optional]
 */
function get_class (object $object = NULL): string {}

/**
 * {@inheritdoc}
 */
function get_called_class (): string {}

/**
 * {@inheritdoc}
 * @param object|string $object_or_class [optional]
 */
function get_parent_class (object|string $object_or_class = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $object_or_class
 * @param string $class
 * @param bool $allow_string [optional]
 */
function is_subclass_of (mixed $object_or_class = null, string $class, bool $allow_string = true): bool {}

/**
 * {@inheritdoc}
 * @param mixed $object_or_class
 * @param string $class
 * @param bool $allow_string [optional]
 */
function is_a (mixed $object_or_class = null, string $class, bool $allow_string = false): bool {}

/**
 * {@inheritdoc}
 * @param string $class
 */
function get_class_vars (string $class): array {}

/**
 * {@inheritdoc}
 * @param object $object
 */
function get_object_vars (object $object): array {}

/**
 * {@inheritdoc}
 * @param object $object
 */
function get_mangled_object_vars (object $object): array {}

/**
 * {@inheritdoc}
 * @param object|string $object_or_class
 */
function get_class_methods (object|string $object_or_class): array {}

/**
 * {@inheritdoc}
 * @param mixed $object_or_class
 * @param string $method
 */
function method_exists ($object_or_class = null, string $method): bool {}

/**
 * {@inheritdoc}
 * @param mixed $object_or_class
 * @param string $property
 */
function property_exists ($object_or_class = null, string $property): bool {}

/**
 * {@inheritdoc}
 * @param string $class
 * @param bool $autoload [optional]
 */
function class_exists (string $class, bool $autoload = true): bool {}

/**
 * {@inheritdoc}
 * @param string $interface
 * @param bool $autoload [optional]
 */
function interface_exists (string $interface, bool $autoload = true): bool {}

/**
 * {@inheritdoc}
 * @param string $trait
 * @param bool $autoload [optional]
 */
function trait_exists (string $trait, bool $autoload = true): bool {}

/**
 * {@inheritdoc}
 * @param string $enum
 * @param bool $autoload [optional]
 */
function enum_exists (string $enum, bool $autoload = true): bool {}

/**
 * {@inheritdoc}
 * @param string $function
 */
function function_exists (string $function): bool {}

/**
 * {@inheritdoc}
 * @param string $class
 * @param string $alias
 * @param bool $autoload [optional]
 */
function class_alias (string $class, string $alias, bool $autoload = true): bool {}

/**
 * {@inheritdoc}
 */
function get_included_files (): array {}

/**
 * {@inheritdoc}
 */
function get_required_files (): array {}

/**
 * {@inheritdoc}
 * @param string $message
 * @param int $error_level [optional]
 */
function trigger_error (string $message, int $error_level = 1024): bool {}

/**
 * {@inheritdoc}
 * @param string $message
 * @param int $error_level [optional]
 */
function user_error (string $message, int $error_level = 1024): bool {}

/**
 * {@inheritdoc}
 * @param callable|null $callback
 * @param int $error_levels [optional]
 */
function set_error_handler (?callable $callback = null, int $error_levels = 32767) {}

/**
 * {@inheritdoc}
 */
function restore_error_handler (): true {}

/**
 * {@inheritdoc}
 * @param callable|null $callback
 */
function set_exception_handler (?callable $callback = null) {}

/**
 * {@inheritdoc}
 */
function restore_exception_handler (): true {}

/**
 * {@inheritdoc}
 */
function get_declared_classes (): array {}

/**
 * {@inheritdoc}
 */
function get_declared_traits (): array {}

/**
 * {@inheritdoc}
 */
function get_declared_interfaces (): array {}

/**
 * {@inheritdoc}
 * @param bool $exclude_disabled [optional]
 */
function get_defined_functions (bool $exclude_disabled = true): array {}

/**
 * {@inheritdoc}
 */
function get_defined_vars (): array {}

/**
 * {@inheritdoc}
 * @param mixed $resource
 */
function get_resource_type ($resource = null): string {}

/**
 * {@inheritdoc}
 * @param mixed $resource
 */
function get_resource_id ($resource = null): int {}

/**
 * {@inheritdoc}
 * @param string|null $type [optional]
 */
function get_resources (?string $type = NULL): array {}

/**
 * {@inheritdoc}
 * @param bool $zend_extensions [optional]
 */
function get_loaded_extensions (bool $zend_extensions = false): array {}

/**
 * {@inheritdoc}
 * @param bool $categorize [optional]
 */
function get_defined_constants (bool $categorize = false): array {}

/**
 * {@inheritdoc}
 * @param int $options [optional]
 * @param int $limit [optional]
 */
function debug_backtrace (int $options = 1, int $limit = 0): array {}

/**
 * {@inheritdoc}
 * @param int $options [optional]
 * @param int $limit [optional]
 */
function debug_print_backtrace (int $options = 0, int $limit = 0): void {}

/**
 * {@inheritdoc}
 * @param string $extension
 */
function extension_loaded (string $extension): bool {}

/**
 * {@inheritdoc}
 * @param string $extension
 */
function get_extension_funcs (string $extension): array|false {}

/**
 * {@inheritdoc}
 */
function gc_mem_caches (): int {}

/**
 * {@inheritdoc}
 */
function gc_collect_cycles (): int {}

/**
 * {@inheritdoc}
 */
function gc_enabled (): bool {}

/**
 * {@inheritdoc}
 */
function gc_enable (): void {}

/**
 * {@inheritdoc}
 */
function gc_disable (): void {}

/**
 * {@inheritdoc}
 */
function gc_status (): array {}

define ('E_ERROR', 1);
define ('E_WARNING', 2);
define ('E_PARSE', 4);
define ('E_NOTICE', 8);
define ('E_CORE_ERROR', 16);
define ('E_CORE_WARNING', 32);
define ('E_COMPILE_ERROR', 64);
define ('E_COMPILE_WARNING', 128);
define ('E_USER_ERROR', 256);
define ('E_USER_WARNING', 512);
define ('E_USER_NOTICE', 1024);
define ('E_STRICT', 2048);
define ('E_RECOVERABLE_ERROR', 4096);
define ('E_DEPRECATED', 8192);
define ('E_USER_DEPRECATED', 16384);
define ('E_ALL', 32767);
define ('DEBUG_BACKTRACE_PROVIDE_OBJECT', 1);
define ('DEBUG_BACKTRACE_IGNORE_ARGS', 2);
define ('ZEND_THREAD_SAFE', false);
define ('ZEND_DEBUG_BUILD', false);
define ('TRUE', true);
define ('FALSE', false);
define ('NULL', null);
define ('PHP_VERSION', "8.3.0");
define ('PHP_MAJOR_VERSION', 8);
define ('PHP_MINOR_VERSION', 3);
define ('PHP_RELEASE_VERSION', 0);
define ('PHP_EXTRA_VERSION', "");
define ('PHP_VERSION_ID', 80300);
define ('PHP_ZTS', 0);
define ('PHP_DEBUG', 0);
define ('PHP_OS', "Darwin");
define ('PHP_OS_FAMILY', "Darwin");
define ('DEFAULT_INCLUDE_PATH', ".:/opt/homebrew/Cellar/php/8.3.0/share/php/pear");
define ('PEAR_INSTALL_DIR', "/opt/homebrew/Cellar/php/8.3.0/share/php/pear");
define ('PEAR_EXTENSION_DIR', "/opt/homebrew/Cellar/php/8.3.0/lib/php/20230831");
define ('PHP_EXTENSION_DIR', "/opt/homebrew/Cellar/php/8.3.0/lib/php/20230831");
define ('PHP_PREFIX', "/opt/homebrew/Cellar/php/8.3.0");
define ('PHP_BINDIR', "/opt/homebrew/Cellar/php/8.3.0/bin");
define ('PHP_MANDIR', "/opt/homebrew/Cellar/php/8.3.0/share/man");
define ('PHP_LIBDIR', "/opt/homebrew/Cellar/php/8.3.0/lib/php");
define ('PHP_DATADIR', "/opt/homebrew/Cellar/php/8.3.0/share/php");
define ('PHP_SYSCONFDIR', "/opt/homebrew/etc/php/8.3");
define ('PHP_LOCALSTATEDIR', "/opt/homebrew/var");
define ('PHP_CONFIG_FILE_PATH', "/opt/homebrew/etc/php/8.3");
define ('PHP_CONFIG_FILE_SCAN_DIR', "/opt/homebrew/etc/php/8.3/conf.d");
define ('PHP_SHLIB_SUFFIX', "so");
define ('PHP_EOL', "\n");
define ('PHP_MAXPATHLEN', 1024);
define ('PHP_INT_MAX', 9223372036854775807);
define ('PHP_INT_MIN', -9223372036854775808);
define ('PHP_INT_SIZE', 8);
define ('PHP_FD_SETSIZE', 1024);
define ('PHP_FLOAT_DIG', 15);
define ('PHP_FLOAT_EPSILON', 2.2204460492503E-16);
define ('PHP_FLOAT_MAX', 1.7976931348623E+308);
define ('PHP_FLOAT_MIN', 2.2250738585072E-308);
define ('PHP_OUTPUT_HANDLER_START', 1);
define ('PHP_OUTPUT_HANDLER_WRITE', 0);
define ('PHP_OUTPUT_HANDLER_FLUSH', 4);
define ('PHP_OUTPUT_HANDLER_CLEAN', 2);
define ('PHP_OUTPUT_HANDLER_FINAL', 8);
define ('PHP_OUTPUT_HANDLER_CONT', 0);
define ('PHP_OUTPUT_HANDLER_END', 8);
define ('PHP_OUTPUT_HANDLER_CLEANABLE', 16);
define ('PHP_OUTPUT_HANDLER_FLUSHABLE', 32);
define ('PHP_OUTPUT_HANDLER_REMOVABLE', 64);
define ('PHP_OUTPUT_HANDLER_STDFLAGS', 112);
define ('PHP_OUTPUT_HANDLER_STARTED', 4096);
define ('PHP_OUTPUT_HANDLER_DISABLED', 8192);
define ('UPLOAD_ERR_OK', 0);
define ('UPLOAD_ERR_INI_SIZE', 1);
define ('UPLOAD_ERR_FORM_SIZE', 2);
define ('UPLOAD_ERR_PARTIAL', 3);
define ('UPLOAD_ERR_NO_FILE', 4);
define ('UPLOAD_ERR_NO_TMP_DIR', 6);
define ('UPLOAD_ERR_CANT_WRITE', 7);
define ('UPLOAD_ERR_EXTENSION', 8);
define ('PHP_SAPI', "cli");
define ('PHP_BINARY', "/opt/homebrew/Cellar/php/8.3.0/bin/php");
define ('PHP_CLI_PROCESS_TITLE', true);
define ('STDIN', "Resource id #1");
define ('STDOUT', "Resource id #2");
define ('STDERR', "Resource id #3");

// End of Core v.8.3.0
