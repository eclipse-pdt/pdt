<?php

// Start of Core v.8.2.6

/**
 * Interface to detect if a class is traversable using foreach.
 * <p>Abstract base interface that cannot be implemented alone. Instead it must
 * be implemented by either IteratorAggregate or
 * Iterator.</p>
 * <p>Internal (built-in) classes that implement this interface can be used in
 * a foreach construct and do not need to implement
 * IteratorAggregate or
 * Iterator.</p>
 * <p>This is an internal engine interface which cannot be implemented in PHP
 * scripts. Either IteratorAggregate or
 * Iterator must be used instead.
 * When implementing an interface which extends Traversable, make sure to
 * list IteratorAggregate or
 * Iterator before its name in the implements
 * clause.</p>
 * @link http://www.php.net/manual/en/class.traversable.php
 */
interface Traversable  {
}

/**
 * Interface to create an external Iterator.
 * @link http://www.php.net/manual/en/class.iteratoraggregate.php
 */
interface IteratorAggregate extends Traversable {

	abstract public function getIterator ()

}

/**
 * Interface for external iterators or objects that can be iterated
 * themselves internally.
 * @link http://www.php.net/manual/en/class.iterator.php
 */
interface Iterator extends Traversable {

	abstract public function current ()

	abstract public function next ()

	abstract public function key ()

	abstract public function valid ()

	abstract public function rewind ()

}

/**
 * Interface for customized serializing.
 * <p>Classes that implement this interface no longer support
 * __sleep() and
 * __wakeup(). The method serialize is
 * called whenever an instance needs to be serialized. This does not invoke __destruct()
 * or have any other side effect unless programmed inside the method. When the data is
 * unserialized the class is known and the appropriate unserialize() method is called as
 * a constructor instead of calling __construct(). If you need to execute the standard
 * constructor you may do so in the method.</p>
 * <p>As of PHP 8.1.0, a class which implements Serializable without also implementing __serialize() and __unserialize() will generate a deprecation warning.</p>
 * @link http://www.php.net/manual/en/class.serializable.php
 */
interface Serializable  {

	abstract public function serialize ()

	/**
	 * @param string $data
	 */
	abstract public function unserialize (string $data)

}

/**
 * Interface to provide accessing objects as arrays.
 * @link http://www.php.net/manual/en/class.arrayaccess.php
 */
interface ArrayAccess  {

	/**
	 * @param mixed $offset
	 */
	abstract public function offsetExists (mixed $offset = null)

	/**
	 * @param mixed $offset
	 */
	abstract public function offsetGet (mixed $offset = null)

	/**
	 * @param mixed $offset
	 * @param mixed $value
	 */
	abstract public function offsetSet (mixed $offset = null, mixed $value = null)

	/**
	 * @param mixed $offset
	 */
	abstract public function offsetUnset (mixed $offset = null)

}

/**
 * Classes implementing Countable can be used with the
 * count function.
 * @link http://www.php.net/manual/en/class.countable.php
 */
interface Countable  {

	/**
	 * Count elements of an object
	 * @link http://www.php.net/manual/en/countable.count.php
	 * @return int The custom count as an int.
	 * <p>
	 * The return value is cast to an int.
	 * </p>
	 */
	abstract public function count ()

}

/**
 * The Stringable interface denotes a class as
 * having a __toString() method. Unlike most interfaces,
 * Stringable is implicitly present on any class that
 * has the magic __toString() method defined, although it
 * can and should be declared explicitly.
 * <p>Its primary value is to allow functions to type check against the union
 * type string|Stringable to accept either a string primitive
 * or an object that can be cast to a string.</p>
 * @link http://www.php.net/manual/en/class.stringable.php
 */
interface Stringable  {

	abstract public function __toString (): string

}

/**
 * Class to ease implementing IteratorAggregate
 * for internal classes.
 * @link http://www.php.net/manual/en/class.internaliterator.php
 */
final class InternalIterator implements Iterator, Traversable {

	private function __construct () {}

	public function current (): mixed {}

	public function key (): mixed {}

	public function next (): void {}

	public function valid (): bool {}

	public function rewind (): void {}

}

/**
 * Throwable is the base interface for any object that
 * can be thrown via a throw statement, including
 * Error and Exception.
 * <p>PHP classes cannot implement the Throwable
 * interface directly, and must instead extend
 * Exception.</p>
 * @link http://www.php.net/manual/en/class.throwable.php
 */
interface Throwable extends Stringable {

	abstract public function getMessage (): string

	abstract public function getCode ()

	abstract public function getFile (): string

	abstract public function getLine (): int

	abstract public function getTrace (): array

	abstract public function getPrevious (): ?Throwable

	abstract public function getTraceAsString (): string

	abstract public function __toString (): string

}

/**
 * Exception is the base class for
 * all user exceptions.
 * @link http://www.php.net/manual/en/class.exception.php
 */
class Exception implements Stringable, Throwable {
	protected $message;
	private $string;
	protected $code;
	protected $file;
	protected $line;
	private $trace;
	private $previous;


	private function __clone (): void {}

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
 * An Error Exception.
 * @link http://www.php.net/manual/en/class.errorexception.php
 */
class ErrorException extends Exception implements Throwable, Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $severity;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param int $severity [optional]
	 * @param string|null $filename [optional]
	 * @param int|null $line [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, int $severity = 1, string|null $filename = null, int|null $line = null, Throwable|null $previous = null) {}

	final public function getSeverity (): int {}

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
 * Error is the base class for all
 * internal PHP errors.
 * @link http://www.php.net/manual/en/class.error.php
 */
class Error implements Stringable, Throwable {
	protected $message;
	private $string;
	protected $code;
	protected $file;
	protected $line;
	private $trace;
	private $previous;


	private function __clone (): void {}

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
 * CompileError is thrown for some
 * compilation errors, which formerly issued a fatal error.
 * @link http://www.php.net/manual/en/class.compileerror.php
 */
class CompileError extends Error implements Throwable, Stringable {
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
 * ParseError is thrown when an
 * error occurs while parsing PHP code, such as when
 * eval is called.
 * @link http://www.php.net/manual/en/class.parseerror.php
 */
class ParseError extends CompileError implements Stringable, Throwable {
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
 * A TypeError may be thrown when:
 * <p>
 * The value being set for a class property does not match
 * the property's corresponding declared type.
 * The argument type being passed to a function does not match
 * its corresponding declared parameter type.
 * A value being returned from a function does not match the
 * declared function return type.
 * </p>
 * @link http://www.php.net/manual/en/class.typeerror.php
 */
class TypeError extends Error implements Throwable, Stringable {
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
 * ArgumentCountError is thrown
 * when too few arguments are passed to a user-defined function or method.
 * @link http://www.php.net/manual/en/class.argumentcounterror.php
 */
class ArgumentCountError extends TypeError implements Stringable, Throwable {
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
 * A ValueError is thrown when the
 * type of an argument is correct but the value of it is incorrect.
 * For example, passing a negative integer when the function expects a
 * positive one, or passing an empty string/array when the function expects
 * it to not be empty.
 * @link http://www.php.net/manual/en/class.valueerror.php
 */
class ValueError extends Error implements Throwable, Stringable {
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
 * ArithmeticError is thrown when
 * an error occurs while performing mathematical operations.
 * These errors include attempting to perform a bitshift by a negative
 * amount, and any call to intdiv that would result in a
 * value outside the possible bounds of an int.
 * @link http://www.php.net/manual/en/class.arithmeticerror.php
 */
class ArithmeticError extends Error implements Throwable, Stringable {
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
 * DivisionByZeroError is thrown
 * when an attempt is made to divide a number by zero.
 * @link http://www.php.net/manual/en/class.divisionbyzeroerror.php
 */
class DivisionByZeroError extends ArithmeticError implements Stringable, Throwable {
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
 * An UnhandledMatchError is thrown
 * when the subject passed to a match expression is not handled by any arm
 * of the match expression.
 * @link http://www.php.net/manual/en/class.unhandledmatcherror.php
 */
class UnhandledMatchError extends Error implements Throwable, Stringable {
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
 * Class used to represent anonymous
 * functions.
 * <p>Anonymous functions yield objects of this type.
 * This class has methods that allow
 * further control of the anonymous function after it has been created.</p>
 * <p>Besides the methods listed here, this class also has an
 * __invoke method. This is for consistency with other
 * classes that implement calling
 * magic, as this method is not used for calling the function.</p>
 * @link http://www.php.net/manual/en/class.closure.php
 */
final class Closure  {

	private function __construct () {}

	/**
	 * @param Closure $closure
	 * @param object|null $newThis
	 * @param object|string|null $newScope [optional]
	 */
	public static function bind (Closure $closure, object|null $newThis = null, object|string|null $newScope = null): ?Closure {}

	/**
	 * @param object|null $newThis
	 * @param object|string|null $newScope [optional]
	 */
	public function bindTo (object|null $newThis = null, object|string|null $newScope = null): ?Closure {}

	/**
	 * @param object $newThis
	 * @param mixed $args [optional]
	 */
	public function call (object $newThis, mixed ...$args): mixed {}

	/**
	 * @param callable $callback
	 */
	public static function fromCallable (callable $callback): Closure {}

	public function __invoke () {}

}

/**
 * Generator objects are returned from generators.
 * <p>Generator objects cannot be instantiated via
 * new.</p>
 * @link http://www.php.net/manual/en/class.generator.php
 */
final class Generator implements Iterator, Traversable {

	public function rewind (): void {}

	public function valid (): bool {}

	public function current (): mixed {}

	public function key (): mixed {}

	public function next (): void {}

	/**
	 * @param mixed $value
	 */
	public function send (mixed $value = null): mixed {}

	/**
	 * @param Throwable $exception
	 */
	public function throw (Throwable $exception): mixed {}

	public function getReturn (): mixed {}

}

class ClosedGeneratorException extends Exception implements Throwable, Stringable {
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
 * Weak references allow the programmer to retain a reference to an object which does not prevent
 * the object from being destroyed. They are useful for implementing cache like structures.
 * <p>WeakReferences cannot be serialized.</p>
 * @link http://www.php.net/manual/en/class.weakreference.php
 */
final class WeakReference  {

	public function __construct () {}

	/**
	 * @param object $object
	 */
	public static function create (object $object): WeakReference {}

	public function get (): ?object {}

}

/**
 * A WeakMap is map (or dictionary) that accepts objects as keys. However, unlike the
 * otherwise similar SplObjectStorage, an object in a key of WeakMap
 * does not contribute toward the object's reference count. That is, if at any point the only remaining reference
 * to an object is the key of a WeakMap, the object will be garbage collected and removed
 * from the WeakMap. Its primary use case is for building caches of data derived from
 * an object that do not need to live longer than the object.
 * <p>WeakMap implements ArrayAccess,
 * Iterator, and Countable,
 * so in most cases it can be used in the same fashion as an associative array.</p>
 * @link http://www.php.net/manual/en/class.weakmap.php
 */
final class WeakMap implements ArrayAccess, Countable, IteratorAggregate, Traversable {

	/**
	 * @param mixed $object
	 */
	public function offsetGet ($object = null): mixed {}

	/**
	 * @param mixed $object
	 * @param mixed $value
	 */
	public function offsetSet ($object = null, mixed $value = null): void {}

	/**
	 * @param mixed $object
	 */
	public function offsetExists ($object = null): bool {}

	/**
	 * @param mixed $object
	 */
	public function offsetUnset ($object = null): void {}

	public function count (): int {}

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

	public $flags;


	/**
	 * @param int $flags [optional]
	 */
	public function __construct (int $flags = 63) {}

}

#[Attribute(4, )]
final class ReturnTypeWillChange  {

	public function __construct () {}

}

#[Attribute(1, )]
final class AllowDynamicProperties  {

	public function __construct () {}

}

#[Attribute(32, )]
final class SensitiveParameter  {

	public function __construct () {}

}

/**
 * The SensitiveParameterValue class allows wrapping sensitive
 * values to protect them against accidental exposure.
 * <p>Values of parameters having the SensitiveParameter attribute
 * will automatically be wrapped inside of a SensitiveParameterValue
 * object within stack traces.</p>
 * @link http://www.php.net/manual/en/class.sensitiveparametervalue.php
 */
final class SensitiveParameterValue  {
	private readonly $value;


	/**
	 * @param mixed $value
	 */
	public function __construct (mixed $value = null) {}

	public function getValue (): mixed {}

	public function __debugInfo (): array {}

}

/**
 * The UnitEnum interface is automatically applied to all
 * enumerations by the engine. It may not be implemented by user-defined classes.
 * Enumerations may not override its methods, as default implementations are provided
 * by the engine. It is available only for type checks.
 * @link http://www.php.net/manual/en/class.unitenum.php
 */
interface UnitEnum  {

	abstract public static function cases (): array

}

/**
 * The BackedEnum interface is automatically applied to backed
 * enumerations by the engine. It may not be implemented by user-defined classes.
 * Enumerations may not override its methods, as default implementations are provided
 * by the engine. It is available only for type checks.
 * @link http://www.php.net/manual/en/class.backedenum.php
 */
interface BackedEnum extends UnitEnum {

	/**
	 * @param string|int $value
	 */
	abstract public static function from (string|int $value): static

	/**
	 * @param string|int $value
	 */
	abstract public static function tryFrom (string|int $value): ?static

	abstract public static function cases (): array

}

/**
 * Fibers represent full-stack, interruptible functions. Fibers may be suspended from anywhere in the call-stack,
 * pausing execution within the fiber until the fiber is resumed at a later time.
 * @link http://www.php.net/manual/en/class.fiber.php
 */
final class Fiber  {

	/**
	 * @param callable $callback
	 */
	public function __construct (callable $callback) {}

	/**
	 * @param mixed $args [optional]
	 */
	public function start (mixed ...$args): mixed {}

	/**
	 * @param mixed $value [optional]
	 */
	public function resume (mixed $value = null): mixed {}

	/**
	 * @param Throwable $exception
	 */
	public function throw (Throwable $exception): mixed {}

	public function isStarted (): bool {}

	public function isSuspended (): bool {}

	public function isRunning (): bool {}

	public function isTerminated (): bool {}

	public function getReturn (): mixed {}

	public static function getCurrent (): ?Fiber {}

	/**
	 * @param mixed $value [optional]
	 */
	public static function suspend (mixed $value = null): mixed {}

}

/**
 * FiberError is thrown
 * when an invalid operation is performed on a Fiber.
 * @link http://www.php.net/manual/en/class.fibererror.php
 */
final class FiberError extends Error implements Throwable, Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function __construct () {}

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
 * A generic empty class with dynamic properties.
 * <p>Objects of this class can be instantiated with
 * new operator or created by
 * typecasting to object.
 * Several PHP functions also create instances of this class, e.g.
 * json_decode, mysqli_fetch_object
 * or PDOStatement::fetchObject.</p>
 * <p>Despite not implementing
 * __get()/__set()
 * magic methods, this class allows dynamic properties and does not require the
 * #[\AllowDynamicProperties] attribute.</p>
 * <p>This is not a base class as PHP does not have a concept of a universal base
 * class. However, it is possible to create a custom class that extends from
 * stdClass and as a result inherits the functionality
 * of dynamic properties.</p>
 * @link http://www.php.net/manual/en/class.stdclass.php
 */
#[AllowDynamicProperties]
class stdClass  {
}

/**
 * Gets the version of the current Zend engine
 * @link http://www.php.net/manual/en/function.zend-version.php
 * @return string the Zend Engine version number, as a string.
 */
function zend_version (): string {}

/**
 * Returns the number of arguments passed to the function
 * @link http://www.php.net/manual/en/function.func-num-args.php
 * @return int the number of arguments passed into the current user-defined
 * function.
 */
function func_num_args (): int {}

/**
 * Return an item from the argument list
 * @link http://www.php.net/manual/en/function.func-get-arg.php
 * @param int $position The argument offset. Function arguments are counted starting from
 * zero.
 * @return mixed the specified argument, or false on error.
 */
function func_get_arg (int $position): mixed {}

/**
 * Returns an array comprising a function's argument list
 * @link http://www.php.net/manual/en/function.func-get-args.php
 * @return array an array in which each element is a copy of the corresponding
 * member of the current user-defined function's argument list.
 */
function func_get_args (): array {}

/**
 * Get string length
 * @link http://www.php.net/manual/en/function.strlen.php
 * @param string $string The string being measured for length.
 * @return int The length of the string in bytes.
 */
function strlen (string $string): int {}

/**
 * Binary safe string comparison
 * @link http://www.php.net/manual/en/function.strcmp.php
 * @param string $string1 The first string.
 * @param string $string2 The second string.
 * @return int -1 if string1 is less than
 * string2; 1 if string1
 * is greater than string2, and 0 if they are
 * equal.
 */
function strcmp (string $string1, string $string2): int {}

/**
 * Binary safe string comparison of the first n characters
 * @link http://www.php.net/manual/en/function.strncmp.php
 * @param string $string1 The first string.
 * @param string $string2 The second string.
 * @param int $length Number of characters to use in the comparison.
 * @return int -1 if string1 is less than
 * string2; 1 if string1
 * is greater than string2, and 0 if they are
 * equal.
 */
function strncmp (string $string1, string $string2, int $length): int {}

/**
 * Binary safe case-insensitive string comparison
 * @link http://www.php.net/manual/en/function.strcasecmp.php
 * @param string $string1 The first string
 * @param string $string2 The second string
 * @return int -1 if string1 is less than
 * string2; 1 if string1
 * is greater than string2, and 0 if they are
 * equal.
 */
function strcasecmp (string $string1, string $string2): int {}

/**
 * Binary safe case-insensitive string comparison of the first n characters
 * @link http://www.php.net/manual/en/function.strncasecmp.php
 * @param string $string1 The first string.
 * @param string $string2 The second string.
 * @param int $length The length of strings to be used in the comparison.
 * @return int -1 if string1 is less than
 * string2; 1 if string1 is
 * greater than string2, and 0 if they are equal.
 */
function strncasecmp (string $string1, string $string2, int $length): int {}

/**
 * Sets which PHP errors are reported
 * @link http://www.php.net/manual/en/function.error-reporting.php
 * @param mixed $error_level [optional] <p>
 * The new error_reporting
 * level. It takes on either a bitmask, or named constants. Using named 
 * constants is strongly encouraged to ensure compatibility for future 
 * versions. As error levels are added, the range of integers increases, 
 * so older integer-based error levels will not always behave as expected.
 * </p>
 * <p>
 * The available error level constants and the actual
 * meanings of these error levels are described in the
 * predefined constants.
 * </p>
 * @return int the old error_reporting
 * level or the current level if no error_level parameter is
 * given.
 */
function error_reporting ($error_level = null): int {}

/**
 * Defines a named constant
 * @link http://www.php.net/manual/en/function.define.php
 * @param string $constant_name <p>
 * The name of the constant.
 * </p>
 * <p>
 * It is possible to define constants with reserved or
 * even invalid names, whose value can (only) be retrieved with
 * constant. However, doing so is not recommended.
 * </p>
 * @param mixed $value <p>
 * The value of the constant. In PHP 5, value must
 * be a scalar value (int,
 * float, string, bool, or
 * null). In PHP 7, array values are also accepted.
 * </p>
 * <p>
 * While it is possible to define resource constants, it is
 * not recommended and may cause unpredictable behavior.
 * </p>
 * @param bool $case_insensitive [optional] <p>
 * If set to true, the constant will be defined case-insensitive. 
 * The default behavior is case-sensitive; i.e. 
 * CONSTANT and Constant represent
 * different values.
 * </p>
 * Defining case-insensitive constants is deprecated as of PHP 7.3.0.
 * As of PHP 8.0.0, only false is an acceptable value, passing
 * true will produce a warning.
 * <p>
 * Case-insensitive constants are stored as lower-case.
 * </p>
 * @return bool true on success or false on failure
 */
function define (string $constant_name, $value, bool $case_insensitive = null): bool {}

/**
 * Checks whether a given named constant exists
 * @link http://www.php.net/manual/en/function.defined.php
 * @param string $constant_name The constant name.
 * @return bool true if the named constant given by constant_name
 * has been defined, false otherwise.
 */
function defined (string $constant_name): bool {}

/**
 * Returns the name of the class of an object
 * @link http://www.php.net/manual/en/function.get-class.php
 * @param object $object [optional] <p>
 * The tested object. This parameter may be omitted when inside a class.
 * </p>
 * Explicitly passing null as the object is no
 * longer allowed as of PHP 7.2.0 and emits an E_WARNING.
 * As of PHP 8.0.0, a TypeError is emitted when
 * null is used.
 * @return string the name of the class of which object is an
 * instance.
 * <p>
 * If object is omitted when inside a class, the
 * name of that class is returned.
 * </p>
 * <p>
 * If the object is an instance of a class which exists 
 * in a namespace, the qualified namespaced name of that class is returned.
 * </p>
 */
function get_class ($object = null): string {}

/**
 * The "Late Static Binding" class name
 * @link http://www.php.net/manual/en/function.get-called-class.php
 * @return string the class name. Returns false if called from outside a class.
 */
function get_called_class (): string {}

/**
 * Retrieves the parent class name for object or class
 * @link http://www.php.net/manual/en/function.get-parent-class.php
 * @param mixed $object_or_class [optional] The tested object or class name. This parameter is optional if called
 * from the object's method.
 * @return mixed the name of the parent class of the class of which
 * object_or_class is an instance or the name.
 * <p>
 * If the object does not have a parent or the class given does not exist false will be returned.
 * </p>
 * <p>
 * If called without parameter outside object, this function returns false.
 * </p>
 */
function get_parent_class ($object_or_class = null): string|false {}

/**
 * Checks if the object has this class as one of its parents or implements it
 * @link http://www.php.net/manual/en/function.is-subclass-of.php
 * @param mixed $object_or_class A class name or an object instance. No error is generated if the class does not exist.
 * @param string $class The class name
 * @param bool $allow_string [optional] If this parameter set to false, string class name as object_or_class
 * is not allowed. This also prevents from calling autoloader if the class doesn't exist.
 * @return bool This function returns true if the object object_or_class,
 * belongs to a class which is a subclass of
 * class, false otherwise.
 */
function is_subclass_of ($object_or_class, string $class, bool $allow_string = null): bool {}

/**
 * Checks whether the object is of a given type or subtype
 * @link http://www.php.net/manual/en/function.is-a.php
 * @param mixed $object_or_class A class name or an object instance.
 * @param string $class The class or interface name
 * @param bool $allow_string [optional] If this parameter set to false, string class name as object_or_class
 * is not allowed. This also prevents from calling autoloader if the class doesn't exist.
 * @return bool true if the object is of this object type or has this object type as one of
 * its supertypes, false otherwise.
 */
function is_a ($object_or_class, string $class, bool $allow_string = null): bool {}

/**
 * Get the default properties of the class
 * @link http://www.php.net/manual/en/function.get-class-vars.php
 * @param string $class The class name
 * @return array an associative array of declared properties visible from the
 * current scope, with their default value.
 * The resulting array elements are in the form of 
 * varname =&gt; value.
 * In case of an error, it returns false.
 */
function get_class_vars (string $class): array {}

/**
 * Gets the properties of the given object
 * @link http://www.php.net/manual/en/function.get-object-vars.php
 * @param object $object An object instance.
 * @return array an associative array of defined object accessible non-static properties 
 * for the specified object in scope.
 */
function get_object_vars ($object): array {}

/**
 * Returns an array of mangled object properties
 * @link http://www.php.net/manual/en/function.get-mangled-object-vars.php
 * @param object $object An object instance.
 * @return array an array containing all properties, regardless of visibility, of object.
 */
function get_mangled_object_vars ($object): array {}

/**
 * Gets the class methods' names
 * @link http://www.php.net/manual/en/function.get-class-methods.php
 * @param mixed $object_or_class The class name or an object instance
 * @return array an array of method names defined for the class specified by
 * object_or_class.
 */
function get_class_methods ($object_or_class): array {}

/**
 * Checks if the class method exists
 * @link http://www.php.net/manual/en/function.method-exists.php
 * @param mixed $object_or_class An object instance or a class name
 * @param string $method The method name
 * @return bool true if the method given by method
 * has been defined for the given object_or_class, false
 * otherwise.
 */
function method_exists ($object_or_class, string $method): bool {}

/**
 * Checks if the object or class has a property
 * @link http://www.php.net/manual/en/function.property-exists.php
 * @param mixed $object_or_class The class name or an object of the class to test for
 * @param string $property The name of the property
 * @return bool true if the property exists, false if it doesn't exist or
 * null in case of an error.
 */
function property_exists ($object_or_class, string $property): bool {}

/**
 * Checks if the class has been defined
 * @link http://www.php.net/manual/en/function.class-exists.php
 * @param string $class The class name. The name is matched in a case-insensitive manner.
 * @param bool $autoload [optional] Whether to autoload
 * if not already loaded.
 * @return bool true if class is a defined class,
 * false otherwise.
 */
function class_exists (string $class, bool $autoload = null): bool {}

/**
 * Checks if the interface has been defined
 * @link http://www.php.net/manual/en/function.interface-exists.php
 * @param string $interface The interface name
 * @param bool $autoload [optional] Whether to autoload
 * if not already loaded.
 * @return bool true if the interface given by 
 * interface has been defined, false otherwise.
 */
function interface_exists (string $interface, bool $autoload = null): bool {}

/**
 * Checks if the trait exists
 * @link http://www.php.net/manual/en/function.trait-exists.php
 * @param string $trait Name of the trait to check
 * @param bool $autoload [optional] Whether to autoload
 * if not already loaded.
 * @return bool true if trait exists, and false otherwise.
 */
function trait_exists (string $trait, bool $autoload = null): bool {}

/**
 * Checks if the enum has been defined
 * @link http://www.php.net/manual/en/function.enum-exists.php
 * @param string $enum The enum name. The name is matched in a case-insensitive manner.
 * @param bool $autoload [optional] Whether to autoload
 * if not already loaded.
 * @return bool true if enum is a defined enum,
 * false otherwise.
 */
function enum_exists (string $enum, bool $autoload = null): bool {}

/**
 * Return true if the given function has been defined
 * @link http://www.php.net/manual/en/function.function-exists.php
 * @param string $function The function name, as a string.
 * @return bool true if function exists and is a
 * function, false otherwise.
 * <p>
 * This function will return false for constructs, such as 
 * include_once and echo.
 * </p>
 */
function function_exists (string $function): bool {}

/**
 * Creates an alias for a class
 * @link http://www.php.net/manual/en/function.class-alias.php
 * @param string $class The original class.
 * @param string $alias The alias name for the class.
 * @param bool $autoload [optional] Whether to autoload
 * if the original class is not found.
 * @return bool true on success or false on failure
 */
function class_alias (string $class, string $alias, bool $autoload = null): bool {}

/**
 * Returns an array with the names of included or required files
 * @link http://www.php.net/manual/en/function.get-included-files.php
 * @return array an array of the names of all files.
 * <p>
 * The script originally called is considered an "included file," so it will
 * be listed together with the files referenced by 
 * include and family.
 * </p>
 * <p>
 * Files that are included or required multiple times only show up once in
 * the returned array.
 * </p>
 */
function get_included_files (): array {}

/**
 * Alias: get_included_files
 * @link http://www.php.net/manual/en/function.get-required-files.php
 */
function get_required_files (): array {}

/**
 * Generates a user-level error/warning/notice message
 * @link http://www.php.net/manual/en/function.trigger-error.php
 * @param string $message The designated error message for this error. It's limited to 1024 
 * bytes in length. Any additional characters beyond 1024 bytes will be 
 * truncated.
 * @param int $error_level [optional] The designated error type for this error. It only works with the E_USER
 * family of constants, and will default to E_USER_NOTICE.
 * @return bool This function returns false if wrong error_level is
 * specified, true otherwise.
 */
function trigger_error (string $message, int $error_level = null): bool {}

/**
 * Alias: trigger_error
 * @link http://www.php.net/manual/en/function.user-error.php
 * @param string $message
 * @param int $error_level [optional]
 */
function user_error (string $message, int $error_level = 1024): bool {}

/**
 * Sets a user-defined error handler function
 * @link http://www.php.net/manual/en/function.set-error-handler.php
 * @param mixed $callback <p>
 * If null is passed, the handler is reset to its default state.
 * Otherwise, the handler is a callback with the following signature:
 * </p>
 * <p>
 * boolhandler
 * interrno
 * stringerrstr
 * stringerrfile
 * interrline
 * arrayerrcontext
 * <p>
 * errno
 * <br>
 * The first parameter, errno, will be passed the
 * level of the error raised, as an integer.
 * errstr
 * <br>
 * The second parameter, errstr, will be passed the
 * error message, as a string.
 * errfile
 * <br>
 * If the callback accepts a third parameter, errfile,
 * it will be passed the filename that the error was raised in, as a string.
 * errline
 * <br>
 * If the callback accepts a fourth parameter, errline,
 * it will be passed the line number where the error was raised, as an integer.
 * errcontext
 * <br>
 * If the callback accepts a fifth parameter, errcontext,
 * it will be passed an array that points to the active symbol table at the
 * point the error occurred. In other words, errcontext
 * will contain an array of every variable that existed in the scope the
 * error was triggered in.
 * User error handlers must not modify the error context.
 * This parameter has been DEPRECATED as of PHP 7.2.0,
 * and REMOVED as of PHP 8.0.0. If the function defines
 * this parameter without a default, an error of "too few arguments" will be
 * raised when it is called.
 * </p>
 * </p>
 * <p>
 * If the function returns false then the normal error handler continues.
 * </p>
 * @param int $error_levels [optional] Can be used to mask the triggering of the
 * callback function just like the error_reporting ini setting 
 * controls which errors are shown. Without this mask set the
 * callback will be called for every error
 * regardless to the setting of the error_reporting setting.
 * @return mixed the previously defined error handler (if any). If
 * the built-in error handler is used null is returned.
 * If the previous error handler
 * was a class method, this function will return an indexed array with the class
 * and the method name.
 */
function set_error_handler ($callback, int $error_levels = null) {}

/**
 * Restores the previous error handler function
 * @link http://www.php.net/manual/en/function.restore-error-handler.php
 * @return true Always returns true.
 */
function restore_error_handler (): true {}

/**
 * Sets a user-defined exception handler function
 * @link http://www.php.net/manual/en/function.set-exception-handler.php
 * @param mixed $callback <p>
 * The function to be called when an uncaught exception occurs.
 * This handler function needs to accept one parameter,
 * which will be the Throwable object that was thrown.
 * Both Error and Exception
 * implement the Throwable interface.
 * This is the handler signature:
 * </p>
 * <p>
 * voidhandler
 * Throwableex
 * </p>
 * <p>
 * null may be passed instead, to reset this handler to its default state.
 * </p>
 * @return mixed the previously defined exception handler, or null on error. If
 * no previous handler was defined, null is also returned.
 */
function set_exception_handler ($callback) {}

/**
 * Restores the previously defined exception handler function
 * @link http://www.php.net/manual/en/function.restore-exception-handler.php
 * @return true Always returns true.
 */
function restore_exception_handler (): true {}

/**
 * Returns an array with the name of the defined classes
 * @link http://www.php.net/manual/en/function.get-declared-classes.php
 * @return array an array of the names of the declared classes in the current
 * script.
 * <p>
 * Note that depending on what extensions you have compiled or
 * loaded into PHP, additional classes could be present. This means that
 * you will not be able to define your own classes using these
 * names. There is a list of predefined classes in the Predefined Classes section of
 * the appendices.
 * </p>
 */
function get_declared_classes (): array {}

/**
 * Returns an array of all declared traits
 * @link http://www.php.net/manual/en/function.get-declared-traits.php
 * @return array an array with names of all declared traits in values.
 */
function get_declared_traits (): array {}

/**
 * Returns an array of all declared interfaces
 * @link http://www.php.net/manual/en/function.get-declared-interfaces.php
 * @return array an array of the names of the declared interfaces in the current
 * script.
 */
function get_declared_interfaces (): array {}

/**
 * Returns an array of all defined functions
 * @link http://www.php.net/manual/en/function.get-defined-functions.php
 * @param bool $exclude_disabled [optional] Whether disabled functions should be excluded from the return value.
 * @return array a multidimensional array containing a list of all defined
 * functions, both built-in (internal) and user-defined. The internal
 * functions will be accessible via $arr["internal"], and
 * the user defined ones using $arr["user"] (see example
 * below).
 */
function get_defined_functions (bool $exclude_disabled = null): array {}

/**
 * Returns an array of all defined variables
 * @link http://www.php.net/manual/en/function.get-defined-vars.php
 * @return array A multidimensional array with all the variables.
 */
function get_defined_vars (): array {}

/**
 * Returns the resource type
 * @link http://www.php.net/manual/en/function.get-resource-type.php
 * @param resource $resource The evaluated resource handle.
 * @return string If the given resource is a resource, this function
 * will return a string representing its type. If the type is not identified
 * by this function, the return value will be the string 
 * Unknown.
 * <p>
 * This function will return null and generate an error if 
 * resource is not a resource.
 * </p>
 */
function get_resource_type ($resource): string {}

/**
 * Returns an integer identifier for the given resource
 * @link http://www.php.net/manual/en/function.get-resource-id.php
 * @param resource $resource The evaluated resource handle.
 * @return int The int identifier for the given resource.
 * <p>
 * This function is essentially an int cast of
 * resource to make it easier to retrieve the resource ID.
 * </p>
 */
function get_resource_id ($resource): int {}

/**
 * Returns active resources
 * @link http://www.php.net/manual/en/function.get-resources.php
 * @param mixed $type [optional] <p>
 * If defined, this will cause get_resources to only
 * return resources of the given type.
 * <p>
 * </p>
 * <p>
 * If the string Unknown is provided as
 * the type, then only resources that are of an unknown type will be
 * returned.
 * </p>
 * <p>
 * If omitted, all resources will be returned.
 * </p>
 * @return array an array of currently active resources, indexed by
 * resource number.
 */
function get_resources ($type = null): array {}

/**
 * Returns an array with the names of all modules compiled and loaded
 * @link http://www.php.net/manual/en/function.get-loaded-extensions.php
 * @param bool $zend_extensions [optional] Only return Zend extensions, if not then regular extensions, like 
 * mysqli are listed. Defaults to false (return regular extensions).
 * @return array an indexed array of all the modules names.
 */
function get_loaded_extensions (bool $zend_extensions = null): array {}

/**
 * Returns an associative array with the names of all the constants and their values
 * @link http://www.php.net/manual/en/function.get-defined-constants.php
 * @param bool $categorize [optional] Causing this function to return a multi-dimensional
 * array with categories in the keys of the first dimension and constants
 * and their values in the second dimension.
 * <pre>
 * <code>&lt;?php
 * define(&quot;MY_CONSTANT&quot;, 1);
 * print_r(get_defined_constants(true));
 * ?&gt;</code>
 * </pre>
 * example.outputs.similar
 * <pre>
 * Array
 * (
 * [Core] => Array
 * (
 * [E_ERROR] => 1
 * [E_WARNING] => 2
 * [E_PARSE] => 4
 * [E_NOTICE] => 8
 * [E_CORE_ERROR] => 16
 * [E_CORE_WARNING] => 32
 * [E_COMPILE_ERROR] => 64
 * [E_COMPILE_WARNING] => 128
 * [E_USER_ERROR] => 256
 * [E_USER_WARNING] => 512
 * [E_USER_NOTICE] => 1024
 * [E_ALL] => 2047
 * [TRUE] => 1
 * )
 * [pcre] => Array
 * (
 * [PREG_PATTERN_ORDER] => 1
 * [PREG_SET_ORDER] => 2
 * [PREG_OFFSET_CAPTURE] => 256
 * [PREG_SPLIT_NO_EMPTY] => 1
 * [PREG_SPLIT_DELIM_CAPTURE] => 2
 * [PREG_SPLIT_OFFSET_CAPTURE] => 4
 * [PREG_GREP_INVERT] => 1
 * )
 * [user] => Array
 * (
 * [MY_CONSTANT] => 1
 * )
 * )
 * </pre>
 * @return array an array of constant name => constant value array, optionally
 * groupped by extension name registering the constant.
 */
function get_defined_constants (bool $categorize = null): array {}

/**
 * Generates a backtrace
 * @link http://www.php.net/manual/en/function.debug-backtrace.php
 * @param int $options [optional] This parameter is a bitmask for the following options:
 * <table>
 * debug_backtrace options
 * <table>
 * <tr valign="top">
 * <td>DEBUG_BACKTRACE_PROVIDE_OBJECT</td>
 * <td>
 * Whether or not to populate the "object" index.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>DEBUG_BACKTRACE_IGNORE_ARGS</td>
 * <td>
 * Whether or not to omit the "args" index, and thus all the function/method arguments,
 * to save memory.
 * </td>
 * </tr>
 * </table>
 * </table>
 * @param int $limit [optional] This parameter can be used to limit the number of stack frames returned.
 * By default (limit=0) it returns all stack frames.
 * @return array an array of associative arrays. The possible returned elements
 * are as follows:
 * <p>
 * <table>
 * Possible returned elements from debug_backtrace
 * <table>
 * <tr valign="top">
 * <td>Name</td>
 * <td>Type</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>function</td>
 * <td>string</td>
 * <td>
 * The current function name. See also
 * __FUNCTION__.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>line</td>
 * <td>int</td>
 * <td>
 * The current line number. See also
 * __LINE__.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>file</td>
 * <td>string</td>
 * <td>
 * The current file name. See also
 * __FILE__.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>class</td>
 * <td>string</td>
 * <td>
 * The current class name. See also
 * __CLASS__
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>object</td>
 * <td>object</td>
 * <td>
 * The current object.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>type</td>
 * <td>string</td>
 * <td>
 * The current call type. If a method call, "-&gt;" is returned. If a static
 * method call, "::" is returned. If a function call, nothing is returned.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>args</td>
 * <td>array</td>
 * <td>
 * If inside a function, this lists the functions arguments. If
 * inside an included file, this lists the included file name(s).
 * </td>
 * </tr>
 * </table>
 * </table>
 * </p>
 */
function debug_backtrace (int $options = null, int $limit = null): array {}

/**
 * Prints a backtrace
 * @link http://www.php.net/manual/en/function.debug-print-backtrace.php
 * @param int $options [optional] This parameter is a bitmask for the following options:
 * <table>
 * debug_print_backtrace options
 * <table>
 * <tr valign="top">
 * <td>DEBUG_BACKTRACE_IGNORE_ARGS</td>
 * <td>
 * Whether or not to omit the "args" index, and thus all the function/method arguments,
 * to save memory.
 * </td>
 * </tr>
 * </table>
 * </table>
 * @param int $limit [optional] This parameter can be used to limit the number of stack frames printed.
 * By default (limit=0) it prints all stack frames.
 * @return void 
 */
function debug_print_backtrace (int $options = null, int $limit = null): void {}

/**
 * Find out whether an extension is loaded
 * @link http://www.php.net/manual/en/function.extension-loaded.php
 * @param string $extension <p>
 * The extension name. This parameter is case-insensitive.
 * </p>
 * <p>
 * You can see the names of various extensions by using
 * phpinfo or if you're using the
 * CGI or CLI version of
 * PHP you can use the -m switch to
 * list all available extensions:
 * <pre>
 * $ php -m
 * [PHP Modules]
 * xml
 * tokenizer
 * standard
 * sockets
 * session
 * posix
 * pcre
 * overload
 * mysql
 * mbstring
 * ctype
 * [Zend Modules]
 * </pre>
 * </p>
 * @return bool true if the extension identified by extension
 * is loaded, false otherwise.
 */
function extension_loaded (string $extension): bool {}

/**
 * Returns an array with the names of the functions of a module
 * @link http://www.php.net/manual/en/function.get-extension-funcs.php
 * @param string $extension <p>
 * The module name.
 * </p>
 * <p>
 * This parameter must be in lowercase.
 * </p>
 * @return mixed an array with all the functions, or false if 
 * extension is not a valid extension.
 */
function get_extension_funcs (string $extension): array|false {}

/**
 * Reclaims memory used by the Zend Engine memory manager
 * @link http://www.php.net/manual/en/function.gc-mem-caches.php
 * @return int the number of bytes freed.
 */
function gc_mem_caches (): int {}

/**
 * Forces collection of any existing garbage cycles
 * @link http://www.php.net/manual/en/function.gc-collect-cycles.php
 * @return int number of collected cycles.
 */
function gc_collect_cycles (): int {}

/**
 * Returns status of the circular reference collector
 * @link http://www.php.net/manual/en/function.gc-enabled.php
 * @return bool true if the garbage collector is enabled, false otherwise.
 */
function gc_enabled (): bool {}

/**
 * Activates the circular reference collector
 * @link http://www.php.net/manual/en/function.gc-enable.php
 * @return void 
 */
function gc_enable (): void {}

/**
 * Deactivates the circular reference collector
 * @link http://www.php.net/manual/en/function.gc-disable.php
 * @return void 
 */
function gc_disable (): void {}

/**
 * Gets information about the garbage collector
 * @link http://www.php.net/manual/en/function.gc-status.php
 * @return array an associative array with the following elements:
 * <p>
 * <br>
 * "runs"
 * <br>
 * "collected"
 * <br>
 * "threshold"
 * <br>
 * "roots"
 * </p>
 */
function gc_status (): array {}


/**
 * Fatal run-time errors. These indicate errors that can not be
 * recovered from, such as a memory allocation problem.
 * Execution of the script is halted.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_ERROR', 1);

/**
 * Run-time warnings (non-fatal errors). Execution of the script is not
 * halted.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_WARNING', 2);

/**
 * Compile-time parse errors. Parse errors should only be generated by
 * the parser.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_PARSE', 4);

/**
 * Run-time notices. Indicate that the script encountered something that
 * could indicate an error, but could also happen in the normal course of
 * running a script.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_NOTICE', 8);

/**
 * Fatal errors that occur during PHP's initial startup. This is like an
 * E_ERROR, except it is generated by the core of PHP.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_CORE_ERROR', 16);

/**
 * Warnings (non-fatal errors) that occur during PHP's initial startup.
 * This is like an E_WARNING, except it is generated
 * by the core of PHP.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_CORE_WARNING', 32);

/**
 * Fatal compile-time errors. This is like an E_ERROR,
 * except it is generated by the Zend Scripting Engine.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_COMPILE_ERROR', 64);

/**
 * Compile-time warnings (non-fatal errors). This is like an
 * E_WARNING, except it is generated by the Zend
 * Scripting Engine.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_COMPILE_WARNING', 128);

/**
 * User-generated error message. This is like an
 * E_ERROR, except it is generated in PHP code by
 * using the PHP function trigger_error.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_USER_ERROR', 256);

/**
 * User-generated warning message. This is like an
 * E_WARNING, except it is generated in PHP code by
 * using the PHP function trigger_error.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_USER_WARNING', 512);

/**
 * User-generated notice message. This is like an
 * E_NOTICE, except it is generated in PHP code by
 * using the PHP function trigger_error.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_USER_NOTICE', 1024);

/**
 * Enable to have PHP suggest changes
 * to your code which will ensure the best interoperability
 * and forward compatibility of your code.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_STRICT', 2048);

/**
 * Catchable fatal error. It indicates that a probably dangerous error
 * occurred, but did not leave the Engine in an unstable state. If the error
 * is not caught by a user defined handle (see also
 * set_error_handler), the application aborts as it
 * was an E_ERROR.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_RECOVERABLE_ERROR', 4096);

/**
 * Run-time notices. Enable this to receive warnings about code
 * that will not work in future versions.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_DEPRECATED', 8192);

/**
 * User-generated warning message. This is like an
 * E_DEPRECATED, except it is generated in PHP code by
 * using the PHP function trigger_error.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_USER_DEPRECATED', 16384);

/**
 * All errors, warnings, and notices.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_ALL', 32767);
define ('DEBUG_BACKTRACE_PROVIDE_OBJECT', 1);
define ('DEBUG_BACKTRACE_IGNORE_ARGS', 2);
define ('ZEND_THREAD_SAFE', false);
define ('ZEND_DEBUG_BUILD', false);
define ('TRUE', true);
define ('FALSE', false);
define ('NULL', null);
define ('PHP_VERSION', "8.2.6");
define ('PHP_MAJOR_VERSION', 8);
define ('PHP_MINOR_VERSION', 2);
define ('PHP_RELEASE_VERSION', 6);
define ('PHP_EXTRA_VERSION', "");
define ('PHP_VERSION_ID', 80206);
define ('PHP_ZTS', 0);
define ('PHP_DEBUG', 0);
define ('PHP_OS', "Darwin");
define ('PHP_OS_FAMILY', "Darwin");
define ('PHP_SAPI', "cli");
define ('DEFAULT_INCLUDE_PATH', ".:/opt/homebrew/Cellar/php/8.2.6/share/php/pear");
define ('PEAR_INSTALL_DIR', "/opt/homebrew/Cellar/php/8.2.6/share/php/pear");
define ('PEAR_EXTENSION_DIR', "/opt/homebrew/Cellar/php/8.2.6/lib/php/20220829");
define ('PHP_EXTENSION_DIR', "/opt/homebrew/Cellar/php/8.2.6/lib/php/20220829");
define ('PHP_PREFIX', "/opt/homebrew/Cellar/php/8.2.6");
define ('PHP_BINDIR', "/opt/homebrew/Cellar/php/8.2.6/bin");
define ('PHP_MANDIR', "/opt/homebrew/Cellar/php/8.2.6/share/man");
define ('PHP_LIBDIR', "/opt/homebrew/Cellar/php/8.2.6/lib/php");
define ('PHP_DATADIR', "/opt/homebrew/Cellar/php/8.2.6/share/php");
define ('PHP_SYSCONFDIR', "/opt/homebrew/etc/php/8.2");
define ('PHP_LOCALSTATEDIR', "/opt/homebrew/var");
define ('PHP_CONFIG_FILE_PATH', "/opt/homebrew/etc/php/8.2");
define ('PHP_CONFIG_FILE_SCAN_DIR', "/opt/homebrew/etc/php/8.2/conf.d");
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
define ('PHP_BINARY', "/opt/homebrew/Cellar/php/8.2.6/bin/php");

/**
 * Indicates that output buffering has begun.
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_START', 1);

/**
 * Indicates that the output buffer is being flushed, and had data to output.
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_WRITE', 0);

/**
 * Indicates that the buffer has been flushed.
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_FLUSH', 4);

/**
 * Indicates that the output buffer has been cleaned.
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_CLEAN', 2);

/**
 * Indicates that this is the final output buffering operation.
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_FINAL', 8);

/**
 * <p>
 * Indicates that the buffer has been flushed, but output buffering will
 * continue.
 * </p>
 * <p>
 * This is an alias for
 * PHP_OUTPUT_HANDLER_WRITE.
 * </p>
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_CONT', 0);

/**
 * <p>
 * Indicates that output buffering has ended.
 * </p>
 * <p>
 * This is an alias for
 * PHP_OUTPUT_HANDLER_FINAL.
 * </p>
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_END', 8);

/**
 * Controls whether an output buffer created by
 * ob_start can be cleaned.
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_CLEANABLE', 16);

/**
 * Controls whether an output buffer created by
 * ob_start can be flushed.
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_FLUSHABLE', 32);

/**
 * Controls whether an output buffer created by
 * ob_start can be removed before the end of the script.
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_REMOVABLE', 64);

/**
 * The default set of output buffer flags; currently equivalent to
 * PHP_OUTPUT_HANDLER_CLEANABLE |
 * PHP_OUTPUT_HANDLER_FLUSHABLE |
 * PHP_OUTPUT_HANDLER_REMOVABLE.
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
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
define ('PHP_CLI_PROCESS_TITLE', false);
define ('STDIN', "Resource id #1");
define ('STDOUT', "Resource id #2");
define ('STDERR', "Resource id #3");

// End of Core v.8.2.6
