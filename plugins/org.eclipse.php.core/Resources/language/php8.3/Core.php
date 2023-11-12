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

	/**
	 * Retrieve an external iterator
	 * @link http://www.php.net/manual/en/iteratoraggregate.getiterator.php
	 * @return Traversable An instance of an object implementing Iterator or
	 * Traversable
	 */
	abstract public function getIterator (): Traversable;

}

/**
 * Interface for external iterators or objects that can be iterated
 * themselves internally.
 * @link http://www.php.net/manual/en/class.iterator.php
 */
interface Iterator extends Traversable {

	/**
	 * Return the current element
	 * @link http://www.php.net/manual/en/iterator.current.php
	 * @return mixed Can return any type.
	 */
	abstract public function current (): mixed;

	/**
	 * Move forward to next element
	 * @link http://www.php.net/manual/en/iterator.next.php
	 * @return void Any returned value is ignored.
	 */
	abstract public function next (): void;

	/**
	 * Return the key of the current element
	 * @link http://www.php.net/manual/en/iterator.key.php
	 * @return mixed Returns scalar on success, or null on failure.
	 */
	abstract public function key (): mixed;

	/**
	 * Checks if current position is valid
	 * @link http://www.php.net/manual/en/iterator.valid.php
	 * @return bool The return value will be casted to bool and then evaluated.
	 * Returns true on success or false on failure.
	 */
	abstract public function valid (): bool;

	/**
	 * Rewind the Iterator to the first element
	 * @link http://www.php.net/manual/en/iterator.rewind.php
	 * @return void Any returned value is ignored.
	 */
	abstract public function rewind (): void;

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

	/**
	 * String representation of object
	 * @link http://www.php.net/manual/en/serializable.serialize.php
	 * @return string|null Returns the string representation of the object or null
	 */
	abstract public function serialize (): ?string;

	/**
	 * Constructs the object
	 * @link http://www.php.net/manual/en/serializable.unserialize.php
	 * @param string $data 
	 * @return void The return value from this method is ignored.
	 */
	abstract public function unserialize (string $data): void;

}

/**
 * Interface to provide accessing objects as arrays.
 * @link http://www.php.net/manual/en/class.arrayaccess.php
 */
interface ArrayAccess  {

	/**
	 * Whether an offset exists
	 * @link http://www.php.net/manual/en/arrayaccess.offsetexists.php
	 * @param mixed $offset 
	 * @return bool Returns true on success or false on failure.
	 * <p>The return value will be casted to bool if non-boolean was returned.</p>
	 */
	abstract public function offsetExists (mixed $offset): bool;

	/**
	 * Offset to retrieve
	 * @link http://www.php.net/manual/en/arrayaccess.offsetget.php
	 * @param mixed $offset 
	 * @return mixed Can return all value types.
	 */
	abstract public function offsetGet (mixed $offset): mixed;

	/**
	 * Assign a value to the specified offset
	 * @link http://www.php.net/manual/en/arrayaccess.offsetset.php
	 * @param mixed $offset 
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	abstract public function offsetSet (mixed $offset, mixed $value): void;

	/**
	 * Unset an offset
	 * @link http://www.php.net/manual/en/arrayaccess.offsetunset.php
	 * @param mixed $offset 
	 * @return void No value is returned.
	 */
	abstract public function offsetUnset (mixed $offset): void;

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
	 * <p>The return value is cast to an int.</p>
	 */
	abstract public function count (): int;

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

	/**
	 * Gets a string representation of the object
	 * @link http://www.php.net/manual/en/stringable.tostring.php
	 * @return string Returns the string representation of the object.
	 */
	abstract public function __toString (): string;

}

/**
 * Class to ease implementing IteratorAggregate
 * for internal classes.
 * @link http://www.php.net/manual/en/class.internaliterator.php
 */
final class InternalIterator implements Iterator, Traversable {

	/**
	 * Private constructor to disallow direct instantiation
	 * @link http://www.php.net/manual/en/internaliterator.construct.php
	 */
	private function __construct () {}

	/**
	 * Return the current element
	 * @link http://www.php.net/manual/en/internaliterator.current.php
	 * @return mixed Returns the current element.
	 */
	public function current (): mixed {}

	/**
	 * Return the key of the current element
	 * @link http://www.php.net/manual/en/internaliterator.key.php
	 * @return mixed Returns the key of the current element.
	 */
	public function key (): mixed {}

	/**
	 * Move forward to next element
	 * @link http://www.php.net/manual/en/internaliterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Check if current position is valid
	 * @link http://www.php.net/manual/en/internaliterator.valid.php
	 * @return bool Returns whether the current position is valid.
	 */
	public function valid (): bool {}

	/**
	 * Rewind the Iterator to the first element
	 * @link http://www.php.net/manual/en/internaliterator.rewind.php
	 * @return void No value is returned.
	 */
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

	/**
	 * Gets the message
	 * @link http://www.php.net/manual/en/throwable.getmessage.php
	 * @return string Returns the message associated with the thrown object.
	 */
	abstract public function getMessage (): string;

	/**
	 * Gets the exception code
	 * @link http://www.php.net/manual/en/throwable.getcode.php
	 * @return int Returns the exception code as int in
	 * Exception but possibly as other type in
	 * Exception descendants (for example as
	 * string in PDOException).
	 */
	abstract public function getCode (): int;

	/**
	 * Gets the file in which the object was created
	 * @link http://www.php.net/manual/en/throwable.getfile.php
	 * @return string Returns the filename in which the thrown object was created.
	 */
	abstract public function getFile (): string;

	/**
	 * Gets the line on which the object was instantiated
	 * @link http://www.php.net/manual/en/throwable.getline.php
	 * @return int Returns the line number where the thrown object was instantiated.
	 */
	abstract public function getLine (): int;

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/throwable.gettrace.php
	 * @return array Returns the stack trace as an array in the same format as
	 * debug_backtrace.
	 */
	abstract public function getTrace (): array;

	/**
	 * Returns the previous Throwable
	 * @link http://www.php.net/manual/en/throwable.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available, or
	 * null otherwise.
	 */
	abstract public function getPrevious (): ?Throwable;

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/throwable.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	abstract public function getTraceAsString (): string;

	/**
	 * Gets a string representation of the object
	 * @link http://www.php.net/manual/en/stringable.tostring.php
	 * @return string Returns the string representation of the object.
	 */
	abstract public function __toString (): string;

}

/**
 * Exception is the base class for
 * all user exceptions.
 * @link http://www.php.net/manual/en/class.exception.php
 */
class Exception implements Stringable, Throwable {

	/**
	 * The exception message
	 * @var string
	 * @link http://www.php.net/manual/en/class.exception.php#exception.props.message
	 */
	protected string $message;

	/**
	 * The exception code
	 * @var int
	 * @link http://www.php.net/manual/en/class.exception.php#exception.props.code
	 */
	protected int $code;

	/**
	 * The filename where the exception was created
	 * @var string
	 * @link http://www.php.net/manual/en/class.exception.php#exception.props.file
	 */
	protected string $file;

	/**
	 * The line where the exception was created
	 * @var int
	 * @link http://www.php.net/manual/en/class.exception.php#exception.props.line
	 */
	protected int $line;

	/**
	 * Clone the exception
	 * @link http://www.php.net/manual/en/exception.clone.php
	 * @return void No value is returned.
	 */
	private function __clone (): void {}

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
 * An Error Exception.
 * @link http://www.php.net/manual/en/class.errorexception.php
 */
class ErrorException extends Exception implements Throwable, Stringable {

	/**
	 * The severity of the exception
	 * @var int
	 * @link http://www.php.net/manual/en/class.errorexception.php#errorexception.props.severity
	 */
	protected int $severity;

	/**
	 * Constructs the exception
	 * @link http://www.php.net/manual/en/errorexception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param int $severity [optional] 
	 * @param string|null $filename [optional] 
	 * @param int|null $line [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, int $severity = E_ERROR, ?string $filename = null, ?int $line = null, ?Throwable $previous = null): string {}

	/**
	 * Gets the exception severity
	 * @link http://www.php.net/manual/en/errorexception.getseverity.php
	 * @return int Returns the severity level of the exception.
	 */
	final public function getSeverity (): int {}

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
 * Error is the base class for all
 * internal PHP errors.
 * @link http://www.php.net/manual/en/class.error.php
 */
class Error implements Stringable, Throwable {

	/**
	 * The error message
	 * @var string
	 * @link http://www.php.net/manual/en/class.error.php#error.props.message
	 */
	protected string $message;

	/**
	 * The error code
	 * @var int
	 * @link http://www.php.net/manual/en/class.error.php#error.props.code
	 */
	protected int $code;

	/**
	 * The filename where the error happened
	 * @var string
	 * @link http://www.php.net/manual/en/class.error.php#error.props.file
	 */
	protected string $file;

	/**
	 * The line where the error happened
	 * @var int
	 * @link http://www.php.net/manual/en/class.error.php#error.props.line
	 */
	protected int $line;

	/**
	 * Clone the error
	 * @link http://www.php.net/manual/en/error.clone.php
	 * @return void No value is returned.
	 */
	private function __clone (): void {}

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
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
	 * Gets the error message
	 * @link http://www.php.net/manual/en/error.getmessage.php
	 * @return string Returns the error message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the error code
	 * @link http://www.php.net/manual/en/error.getcode.php
	 * @return int Returns the error code as int
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the error occurred
	 * @link http://www.php.net/manual/en/error.getfile.php
	 * @return string Returns the filename in which the error occurred.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the error occurred
	 * @link http://www.php.net/manual/en/error.getline.php
	 * @return int Returns the line number where the error occurred.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/error.gettrace.php
	 * @return array Returns the stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/error.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/error.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the error
	 * @link http://www.php.net/manual/en/error.tostring.php
	 * @return string Returns the string representation of the error.
	 */
	public function __toString (): string {}

}

/**
 * CompileError is thrown for some
 * compilation errors, which formerly issued a fatal error.
 * @link http://www.php.net/manual/en/class.compileerror.php
 */
class CompileError extends Error implements Throwable, Stringable {

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
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
	 * Gets the error message
	 * @link http://www.php.net/manual/en/error.getmessage.php
	 * @return string Returns the error message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the error code
	 * @link http://www.php.net/manual/en/error.getcode.php
	 * @return int Returns the error code as int
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the error occurred
	 * @link http://www.php.net/manual/en/error.getfile.php
	 * @return string Returns the filename in which the error occurred.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the error occurred
	 * @link http://www.php.net/manual/en/error.getline.php
	 * @return int Returns the line number where the error occurred.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/error.gettrace.php
	 * @return array Returns the stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/error.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/error.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the error
	 * @link http://www.php.net/manual/en/error.tostring.php
	 * @return string Returns the string representation of the error.
	 */
	public function __toString (): string {}

}

/**
 * ParseError is thrown when an
 * error occurs while parsing PHP code, such as when
 * eval is called.
 * @link http://www.php.net/manual/en/class.parseerror.php
 */
class ParseError extends CompileError implements Stringable, Throwable {

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
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
	 * Gets the error message
	 * @link http://www.php.net/manual/en/error.getmessage.php
	 * @return string Returns the error message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the error code
	 * @link http://www.php.net/manual/en/error.getcode.php
	 * @return int Returns the error code as int
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the error occurred
	 * @link http://www.php.net/manual/en/error.getfile.php
	 * @return string Returns the filename in which the error occurred.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the error occurred
	 * @link http://www.php.net/manual/en/error.getline.php
	 * @return int Returns the line number where the error occurred.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/error.gettrace.php
	 * @return array Returns the stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/error.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/error.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the error
	 * @link http://www.php.net/manual/en/error.tostring.php
	 * @return string Returns the string representation of the error.
	 */
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

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
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
	 * Gets the error message
	 * @link http://www.php.net/manual/en/error.getmessage.php
	 * @return string Returns the error message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the error code
	 * @link http://www.php.net/manual/en/error.getcode.php
	 * @return int Returns the error code as int
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the error occurred
	 * @link http://www.php.net/manual/en/error.getfile.php
	 * @return string Returns the filename in which the error occurred.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the error occurred
	 * @link http://www.php.net/manual/en/error.getline.php
	 * @return int Returns the line number where the error occurred.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/error.gettrace.php
	 * @return array Returns the stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/error.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/error.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the error
	 * @link http://www.php.net/manual/en/error.tostring.php
	 * @return string Returns the string representation of the error.
	 */
	public function __toString (): string {}

}

/**
 * ArgumentCountError is thrown
 * when too few arguments are passed to a user-defined function or method.
 * @link http://www.php.net/manual/en/class.argumentcounterror.php
 */
class ArgumentCountError extends TypeError implements Stringable, Throwable {

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
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
	 * Gets the error message
	 * @link http://www.php.net/manual/en/error.getmessage.php
	 * @return string Returns the error message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the error code
	 * @link http://www.php.net/manual/en/error.getcode.php
	 * @return int Returns the error code as int
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the error occurred
	 * @link http://www.php.net/manual/en/error.getfile.php
	 * @return string Returns the filename in which the error occurred.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the error occurred
	 * @link http://www.php.net/manual/en/error.getline.php
	 * @return int Returns the line number where the error occurred.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/error.gettrace.php
	 * @return array Returns the stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/error.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/error.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the error
	 * @link http://www.php.net/manual/en/error.tostring.php
	 * @return string Returns the string representation of the error.
	 */
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

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
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
	 * Gets the error message
	 * @link http://www.php.net/manual/en/error.getmessage.php
	 * @return string Returns the error message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the error code
	 * @link http://www.php.net/manual/en/error.getcode.php
	 * @return int Returns the error code as int
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the error occurred
	 * @link http://www.php.net/manual/en/error.getfile.php
	 * @return string Returns the filename in which the error occurred.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the error occurred
	 * @link http://www.php.net/manual/en/error.getline.php
	 * @return int Returns the line number where the error occurred.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/error.gettrace.php
	 * @return array Returns the stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/error.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/error.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the error
	 * @link http://www.php.net/manual/en/error.tostring.php
	 * @return string Returns the string representation of the error.
	 */
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

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
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
	 * Gets the error message
	 * @link http://www.php.net/manual/en/error.getmessage.php
	 * @return string Returns the error message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the error code
	 * @link http://www.php.net/manual/en/error.getcode.php
	 * @return int Returns the error code as int
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the error occurred
	 * @link http://www.php.net/manual/en/error.getfile.php
	 * @return string Returns the filename in which the error occurred.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the error occurred
	 * @link http://www.php.net/manual/en/error.getline.php
	 * @return int Returns the line number where the error occurred.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/error.gettrace.php
	 * @return array Returns the stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/error.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/error.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the error
	 * @link http://www.php.net/manual/en/error.tostring.php
	 * @return string Returns the string representation of the error.
	 */
	public function __toString (): string {}

}

/**
 * DivisionByZeroError is thrown
 * when an attempt is made to divide a number by zero.
 * @link http://www.php.net/manual/en/class.divisionbyzeroerror.php
 */
class DivisionByZeroError extends ArithmeticError implements Stringable, Throwable {

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
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
	 * Gets the error message
	 * @link http://www.php.net/manual/en/error.getmessage.php
	 * @return string Returns the error message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the error code
	 * @link http://www.php.net/manual/en/error.getcode.php
	 * @return int Returns the error code as int
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the error occurred
	 * @link http://www.php.net/manual/en/error.getfile.php
	 * @return string Returns the filename in which the error occurred.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the error occurred
	 * @link http://www.php.net/manual/en/error.getline.php
	 * @return int Returns the line number where the error occurred.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/error.gettrace.php
	 * @return array Returns the stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/error.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/error.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the error
	 * @link http://www.php.net/manual/en/error.tostring.php
	 * @return string Returns the string representation of the error.
	 */
	public function __toString (): string {}

}

/**
 * An UnhandledMatchError is thrown
 * when the subject passed to a match expression is not handled by any arm
 * of the match expression.
 * @link http://www.php.net/manual/en/class.unhandledmatcherror.php
 */
class UnhandledMatchError extends Error implements Throwable, Stringable {

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
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
	 * Gets the error message
	 * @link http://www.php.net/manual/en/error.getmessage.php
	 * @return string Returns the error message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the error code
	 * @link http://www.php.net/manual/en/error.getcode.php
	 * @return int Returns the error code as int
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the error occurred
	 * @link http://www.php.net/manual/en/error.getfile.php
	 * @return string Returns the filename in which the error occurred.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the error occurred
	 * @link http://www.php.net/manual/en/error.getline.php
	 * @return int Returns the line number where the error occurred.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/error.gettrace.php
	 * @return array Returns the stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/error.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/error.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the error
	 * @link http://www.php.net/manual/en/error.tostring.php
	 * @return string Returns the string representation of the error.
	 */
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

	/**
	 * Constructor that disallows instantiation
	 * @link http://www.php.net/manual/en/closure.construct.php
	 */
	private function __construct () {}

	/**
	 * Duplicates a closure with a specific bound object and class scope
	 * @link http://www.php.net/manual/en/closure.bind.php
	 * @param Closure $closure The anonymous functions to bind.
	 * @param object|null $newThis The object to which the given anonymous function should be bound, or
	 * null for the closure to be unbound.
	 * @param object|string|null $newScope [optional] The class scope to which the closure is to be associated, or
	 * 'static' to keep the current one. If an object is given, the type of the
	 * object will be used instead. This determines the visibility of protected
	 * and private methods of the bound object.
	 * It is not allowed to pass (an object of) an internal class as this parameter.
	 * @return Closure|null Returns a new Closure object, or null on failure.
	 */
	public static function bind (Closure $closure, ?object $newThis, object|string|null $newScope = '"static"'): ?Closure {}

	/**
	 * Duplicates the closure with a new bound object and class scope
	 * @link http://www.php.net/manual/en/closure.bindto.php
	 * @param object|null $newThis The object to which the given anonymous function should be bound, or
	 * null for the closure to be unbound.
	 * @param object|string|null $newScope [optional] The class scope to which the closure is to be associated, or
	 * 'static' to keep the current one. If an object is given, the type of the
	 * object will be used instead. This determines the visibility of protected
	 * and private methods of the bound object.
	 * It is not allowed to pass (an object of) an internal class as this parameter.
	 * @return Closure|null Returns the newly created Closure object
	 * or null on failure.
	 */
	public function bindTo (?object $newThis, object|string|null $newScope = '"static"'): ?Closure {}

	/**
	 * Binds and calls the closure
	 * @link http://www.php.net/manual/en/closure.call.php
	 * @param object $newThis The object to bind the closure to for the duration of the
	 * call.
	 * @param mixed $args Zero or more parameters, which will be given as parameters to the
	 * closure.
	 * @return mixed Returns the return value of the closure.
	 */
	public function call (object $newThis, mixed ...$args): mixed {}

	/**
	 * Converts a callable into a closure
	 * @link http://www.php.net/manual/en/closure.fromcallable.php
	 * @param callable $callback The callable to convert.
	 * @return Closure Returns the newly created Closure or throws a
	 * TypeError if the callback is
	 * not callable in the current scope.
	 */
	public static function fromCallable (callable $callback): Closure {}

	/**
	 * {@inheritdoc}
	 */
	public function __invoke () {}

}

/**
 * Generator objects are returned from generators.
 * <p>Generator objects cannot be instantiated via
 * new.</p>
 * @link http://www.php.net/manual/en/class.generator.php
 */
final class Generator implements Iterator, Traversable {

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/generator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Check if the iterator has been closed
	 * @link http://www.php.net/manual/en/generator.valid.php
	 * @return bool Returns false if the iterator has been closed. Otherwise returns true.
	 */
	public function valid (): bool {}

	/**
	 * Get the yielded value
	 * @link http://www.php.net/manual/en/generator.current.php
	 * @return mixed Returns the yielded value.
	 */
	public function current (): mixed {}

	/**
	 * Get the yielded key
	 * @link http://www.php.net/manual/en/generator.key.php
	 * @return mixed Returns the yielded key.
	 */
	public function key (): mixed {}

	/**
	 * Resume execution of the generator
	 * @link http://www.php.net/manual/en/generator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Send a value to the generator
	 * @link http://www.php.net/manual/en/generator.send.php
	 * @param mixed $value Value to send into the generator. This value will be the return value of the
	 * yield expression the generator is currently at.
	 * @return mixed Returns the yielded value.
	 */
	public function send (mixed $value): mixed {}

	/**
	 * Throw an exception into the generator
	 * @link http://www.php.net/manual/en/generator.throw.php
	 * @param Throwable $exception Exception to throw into the generator.
	 * @return mixed Returns the yielded value.
	 */
	public function throw (Throwable $exception): mixed {}

	/**
	 * Get the return value of a generator
	 * @link http://www.php.net/manual/en/generator.getreturn.php
	 * @return mixed Returns the generator's return value once it has finished executing.
	 */
	public function getReturn (): mixed {}

}

class ClosedGeneratorException extends Exception implements Throwable, Stringable {

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
 * Weak references allow the programmer to retain a reference to an object which does not prevent
 * the object from being destroyed. They are useful for implementing cache like structures.
 * <p>WeakReferences cannot be serialized.</p>
 * @link http://www.php.net/manual/en/class.weakreference.php
 */
final class WeakReference  {

	/**
	 * Constructor that disallows instantiation
	 * @link http://www.php.net/manual/en/weakreference.construct.php
	 */
	public function __construct () {}

	/**
	 * Create a new weak reference
	 * @link http://www.php.net/manual/en/weakreference.create.php
	 * @param object $object The object to be weakly referenced.
	 * @return WeakReference Returns the freshly instantiated object.
	 */
	public static function create (object $object): WeakReference {}

	/**
	 * Get a weakly referenced Object
	 * @link http://www.php.net/manual/en/weakreference.get.php
	 * @return object|null Returns the referenced object, or null if the object has been destroyed.
	 */
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
	 * Returns the value pointed to by a certain object
	 * @link http://www.php.net/manual/en/weakmap.offsetget.php
	 * @param object $object Some object contained as key in the map.
	 * @return mixed Returns the value associated to the object passed as argument, null
	 * otherwise.
	 */
	public function offsetGet (object $object): mixed {}

	/**
	 * Updates the map with a new key-value pair
	 * @link http://www.php.net/manual/en/weakmap.offsetset.php
	 * @param object $object The object serving as key of the key-value pair.
	 * @param mixed $value The arbitrary data serving as value of the key-value pair.
	 * @return void No value is returned.
	 */
	public function offsetSet (object $object, mixed $value): void {}

	/**
	 * Checks whether a certain object is in the map
	 * @link http://www.php.net/manual/en/weakmap.offsetexists.php
	 * @param object $object Object to check for.
	 * @return bool Returns true if the object is contained in the map, false otherwise.
	 */
	public function offsetExists (object $object): bool {}

	/**
	 * Removes an entry from the map
	 * @link http://www.php.net/manual/en/weakmap.offsetunset.php
	 * @param object $object The key object to remove from the map.
	 * @return void No value is returned.
	 */
	public function offsetUnset (object $object): void {}

	/**
	 * Counts the number of live entries in the map
	 * @link http://www.php.net/manual/en/weakmap.count.php
	 * @return int Returns the number of live entries in the map.
	 */
	public function count (): int {}

	/**
	 * Retrieve an external iterator
	 * @link http://www.php.net/manual/en/weakmap.getiterator.php
	 * @return Iterator An instance of an object implementing Iterator or
	 * Traversable
	 */
	public function getIterator (): Iterator {}

}

/**
 * Attributes offer the ability to add structured, machine-readable metadata
 * information on declarations in code: Classes, methods, functions,
 * parameters, properties and class constants can be the target of an attribute.
 * The metadata defined by attributes can then be inspected at runtime using the
 * Reflection APIs.
 * Attributes could therefore be thought of as a configuration language
 * embedded directly into code.
 * @link http://www.php.net/manual/en/class.attribute.php
 */
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
	 * Construct a new Attribute instance
	 * @link http://www.php.net/manual/en/attribute.construct.php
	 * @param int $flags [optional] 
	 * @return int 
	 */
	public function __construct (int $flags = \Attribute::TARGET_ALL): int {}

}

/**
 * Most non-final internal methods now require overriding methods to declare
 * a compatible return type, otherwise a deprecated notice is emitted during
 * inheritance validation.
 * In case the return type cannot be declared for an overriding method due to
 * PHP cross-version compatibility concerns,
 * a #[\ReturnTypeWillChange] attribute can be added to silence
 * the deprecation notice.
 * @link http://www.php.net/manual/en/class.returntypewillchange.php
 */
#[Attribute(4, )]
final class ReturnTypeWillChange  {

	/**
	 * Construct a new ReturnTypeWillChange attribute instance
	 * @link http://www.php.net/manual/en/returntypewillchange.construct.php
	 */
	public function __construct () {}

}

/**
 * This attribute is used to mark classes that allow
 * dynamic properties.
 * @link http://www.php.net/manual/en/class.allowdynamicproperties.php
 */
#[Attribute(1, )]
final class AllowDynamicProperties  {

	/**
	 * Construct a new AllowDynamicProperties attribute instance
	 * @link http://www.php.net/manual/en/allowdynamicproperties.construct.php
	 */
	public function __construct () {}

}

/**
 * This attribute is used to mark a parameter that is sensitive and should
 * have its value redacted if present in a stack trace.
 * @link http://www.php.net/manual/en/class.sensitiveparameter.php
 */
#[Attribute(32, )]
final class SensitiveParameter  {

	/**
	 * Construct a new SensitiveParameter attribute instance
	 * @link http://www.php.net/manual/en/sensitiveparameter.construct.php
	 */
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

	readonly mixed $value;

	/**
	 * Constructs a new SensitiveParameterValue object
	 * @link http://www.php.net/manual/en/sensitiveparametervalue.construct.php
	 * @param mixed $value An arbitrary value that should be stored inside the SensitiveParameterValue object.
	 * @return mixed 
	 */
	public function __construct (mixed $value): mixed {}

	/**
	 * Returns the sensitive value
	 * @link http://www.php.net/manual/en/sensitiveparametervalue.getvalue.php
	 * @return mixed The sensitive value.
	 */
	public function getValue (): mixed {}

	/**
	 * Protects the sensitive value against accidental exposure
	 * @link http://www.php.net/manual/en/sensitiveparametervalue.debuginfo.php
	 * @return array An empty array.
	 */
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

	/**
	 * Generates a list of cases on an enum
	 * @link http://www.php.net/manual/en/unitenum.cases.php
	 * @return array An array of all defined cases of this enumeration, in order of declaration.
	 */
	abstract public static function cases (): array;

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
	 * Maps a scalar to an enum instance
	 * @link http://www.php.net/manual/en/backedenum.from.php
	 * @param int|string $value The scalar value to map to an enum case.
	 * @return static A case instance of this enumeration.
	 */
	abstract public static function from (int|string $value): static;

	/**
	 * Maps a scalar to an enum instance or null
	 * @link http://www.php.net/manual/en/backedenum.tryfrom.php
	 * @param int|string $value The scalar value to map to an enum case.
	 * @return static|null A case instance of this enumeration, or null if not found.
	 */
	abstract public static function tryFrom (int|string $value): ?static;

	/**
	 * Generates a list of cases on an enum
	 * @link http://www.php.net/manual/en/unitenum.cases.php
	 * @return array An array of all defined cases of this enumeration, in order of declaration.
	 */
	abstract public static function cases (): array;

}

/**
 * Fibers represent full-stack, interruptible functions. Fibers may be suspended from anywhere in the call-stack,
 * pausing execution within the fiber until the fiber is resumed at a later time.
 * @link http://www.php.net/manual/en/class.fiber.php
 */
final class Fiber  {

	/**
	 * Creates a new Fiber instance
	 * @link http://www.php.net/manual/en/fiber.construct.php
	 * @param callable $callback The callable to invoke when starting the fiber.
	 * Arguments given to Fiber::start will be
	 * provided as arguments to the given callable.
	 * @return callable 
	 */
	public function __construct (callable $callback): callable {}

	/**
	 * Start execution of the fiber
	 * @link http://www.php.net/manual/en/fiber.start.php
	 * @param mixed $args The arguments to use when invoking the callable given to the fiber constructor.
	 * @return mixed The value provided to the first call to Fiber::suspend or null if the fiber returns.
	 * If the fiber throws an exception before suspending, it will be thrown from the call to this method.
	 */
	public function start (mixed ...$args): mixed {}

	/**
	 * Resumes execution of the fiber with a value
	 * @link http://www.php.net/manual/en/fiber.resume.php
	 * @param mixed $value [optional] The value to resume the fiber. This value will be the return value of the current
	 * Fiber::suspend call.
	 * @return mixed The value provided to the next call to Fiber::suspend or null if the fiber returns.
	 * If the fiber throws an exception before suspending, it will be thrown from the call to this method.
	 */
	public function resume (mixed $value = null): mixed {}

	/**
	 * Resumes execution of the fiber with an exception
	 * @link http://www.php.net/manual/en/fiber.throw.php
	 * @param Throwable $exception The exception to throw into the fiber from the current Fiber::suspend call.
	 * @return mixed The value provided to the next call to Fiber::suspend or null if the fiber returns.
	 * If the fiber throws an exception before suspending, it will be thrown from the call to this method.
	 */
	public function throw (Throwable $exception): mixed {}

	/**
	 * Determines if the fiber has started
	 * @link http://www.php.net/manual/en/fiber.isstarted.php
	 * @return bool Returns true only after the fiber has been started; otherwise false is returned.
	 */
	public function isStarted (): bool {}

	/**
	 * Determines if the fiber is suspended
	 * @link http://www.php.net/manual/en/fiber.issuspended.php
	 * @return bool Returns true if the fiber is currently suspended; otherwise false is returned.
	 */
	public function isSuspended (): bool {}

	/**
	 * Determines if the fiber is running
	 * @link http://www.php.net/manual/en/fiber.isrunning.php
	 * @return bool Returns true only if the fiber is running. A fiber is considered running after a call to
	 * Fiber::start, Fiber::resume, or
	 * Fiber::throw that has not yet returned.
	 * Return false if the fiber is not running.
	 */
	public function isRunning (): bool {}

	/**
	 * Determines if the fiber has terminated
	 * @link http://www.php.net/manual/en/fiber.isterminated.php
	 * @return bool Returns true only after the fiber has terminated, either by returning or throwing an exception;
	 * otherwise false is returned.
	 */
	public function isTerminated (): bool {}

	/**
	 * Gets the value returned by the Fiber
	 * @link http://www.php.net/manual/en/fiber.getreturn.php
	 * @return mixed Returns the value returned by the callable provided to Fiber::__construct.
	 * If the fiber has not returned a value, either because it has not been started, has not terminated, or threw an
	 * exception, a FiberError will be thrown.
	 */
	public function getReturn (): mixed {}

	/**
	 * Gets the currently executing Fiber instance
	 * @link http://www.php.net/manual/en/fiber.getcurrent.php
	 * @return Fiber|null Returns the currently executing Fiber instance or null if this method is called from
	 * outside a fiber.
	 */
	public static function getCurrent (): ?Fiber {}

	/**
	 * Suspends execution of the current fiber
	 * @link http://www.php.net/manual/en/fiber.suspend.php
	 * @param mixed $value [optional] The value to return from the call to Fiber::start,
	 * Fiber::resume, or Fiber::throw that switched execution into
	 * the current fiber.
	 * @return mixed The value provided to Fiber::resume.
	 */
	public static function suspend (mixed $value = null): mixed {}

}

/**
 * FiberError is thrown
 * when an invalid operation is performed on a Fiber.
 * @link http://www.php.net/manual/en/class.fibererror.php
 */
final class FiberError extends Error implements Throwable, Stringable {

	/**
	 * Constructor to disallow direct instantiation
	 * @link http://www.php.net/manual/en/fibererror.construct.php
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * Gets the error message
	 * @link http://www.php.net/manual/en/error.getmessage.php
	 * @return string Returns the error message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the error code
	 * @link http://www.php.net/manual/en/error.getcode.php
	 * @return int Returns the error code as int
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the error occurred
	 * @link http://www.php.net/manual/en/error.getfile.php
	 * @return string Returns the filename in which the error occurred.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the error occurred
	 * @link http://www.php.net/manual/en/error.getline.php
	 * @return int Returns the line number where the error occurred.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/error.gettrace.php
	 * @return array Returns the stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/error.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/error.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the error
	 * @link http://www.php.net/manual/en/error.tostring.php
	 * @return string Returns the string representation of the error.
	 */
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
 * @return string Returns the Zend Engine version number, as a string.
 */
function zend_version (): string {}

/**
 * Returns the number of arguments passed to the function
 * @link http://www.php.net/manual/en/function.func-num-args.php
 * @return int Returns the number of arguments passed into the current user-defined
 * function.
 */
function func_num_args (): int {}

/**
 * Return an item from the argument list
 * @link http://www.php.net/manual/en/function.func-get-arg.php
 * @param int $position 
 * @return mixed Returns the specified argument, or false on error.
 */
function func_get_arg (int $position): mixed {}

/**
 * Returns an array comprising a function's argument list
 * @link http://www.php.net/manual/en/function.func-get-args.php
 * @return array Returns an array in which each element is a copy of the corresponding
 * member of the current user-defined function's argument list.
 */
function func_get_args (): array {}

/**
 * Get string length
 * @link http://www.php.net/manual/en/function.strlen.php
 * @param string $string 
 * @return int The length of the string in bytes.
 */
function strlen (string $string): int {}

/**
 * Binary safe string comparison
 * @link http://www.php.net/manual/en/function.strcmp.php
 * @param string $string1 
 * @param string $string2 
 * @return int Returns -1 if string1 is less than
 * string2; 1 if string1
 * is greater than string2, and 0 if they are
 * equal.
 */
function strcmp (string $string1, string $string2): int {}

/**
 * Binary safe string comparison of the first n characters
 * @link http://www.php.net/manual/en/function.strncmp.php
 * @param string $string1 
 * @param string $string2 
 * @param int $length 
 * @return int Returns -1 if string1 is less than
 * string2; 1 if string1
 * is greater than string2, and 0 if they are
 * equal.
 */
function strncmp (string $string1, string $string2, int $length): int {}

/**
 * Binary safe case-insensitive string comparison
 * @link http://www.php.net/manual/en/function.strcasecmp.php
 * @param string $string1 
 * @param string $string2 
 * @return int Returns -1 if string1 is less than
 * string2; 1 if string1
 * is greater than string2, and 0 if they are
 * equal.
 */
function strcasecmp (string $string1, string $string2): int {}

/**
 * Binary safe case-insensitive string comparison of the first n characters
 * @link http://www.php.net/manual/en/function.strncasecmp.php
 * @param string $string1 
 * @param string $string2 
 * @param int $length 
 * @return int Returns -1 if string1 is less than
 * string2; 1 if string1 is
 * greater than string2, and 0 if they are equal.
 */
function strncasecmp (string $string1, string $string2, int $length): int {}

/**
 * Sets which PHP errors are reported
 * @link http://www.php.net/manual/en/function.error-reporting.php
 * @param int|null $error_level [optional] 
 * @return int Returns the old error_reporting
 * level or the current level if no error_level parameter is
 * given.
 */
function error_reporting (?int $error_level = null): int {}

/**
 * Defines a named constant
 * @link http://www.php.net/manual/en/function.define.php
 * @param string $constant_name 
 * @param mixed $value 
 * @param bool $case_insensitive [optional] 
 * @return bool Returns true on success or false on failure.
 */
function define (string $constant_name, mixed $value, bool $case_insensitive = false): bool {}

/**
 * Checks whether a given named constant exists
 * @link http://www.php.net/manual/en/function.defined.php
 * @param string $constant_name 
 * @return bool Returns true if the named constant given by constant_name
 * has been defined, false otherwise.
 */
function defined (string $constant_name): bool {}

/**
 * Returns the name of the class of an object
 * @link http://www.php.net/manual/en/function.get-class.php
 * @param object $object [optional] 
 * @return string Returns the name of the class of which object is an
 * instance.
 * <p>If object is omitted when inside a class, the
 * name of that class is returned.</p>
 * <p>If the object is an instance of a class which exists 
 * in a namespace, the qualified namespaced name of that class is returned.</p>
 */
function get_class (object $object = null): string {}

/**
 * The "Late Static Binding" class name
 * @link http://www.php.net/manual/en/function.get-called-class.php
 * @return string Returns the class name. Returns false if called from outside a class.
 */
function get_called_class (): string {}

/**
 * Retrieves the parent class name for object or class
 * @link http://www.php.net/manual/en/function.get-parent-class.php
 * @param object|string $object_or_class [optional] 
 * @return string|false Returns the name of the parent class of the class of which
 * object_or_class is an instance or the name.
 * <p>If the object does not have a parent or the class given does not exist false will be returned.</p>
 * <p>If called without parameter outside object, this function returns false.</p>
 */
function get_parent_class (object|string $object_or_class = null): string|false {}

/**
 * Checks if the object has this class as one of its parents or implements it
 * @link http://www.php.net/manual/en/function.is-subclass-of.php
 * @param mixed $object_or_class 
 * @param string $class 
 * @param bool $allow_string [optional] 
 * @return bool This function returns true if the object object_or_class,
 * belongs to a class which is a subclass of
 * class, false otherwise.
 */
function is_subclass_of (mixed $object_or_class, string $class, bool $allow_string = true): bool {}

/**
 * Checks whether the object is of a given type or subtype
 * @link http://www.php.net/manual/en/function.is-a.php
 * @param mixed $object_or_class 
 * @param string $class 
 * @param bool $allow_string [optional] 
 * @return bool Returns true if the object is of this object type or has this object type as one of
 * its supertypes, false otherwise.
 */
function is_a (mixed $object_or_class, string $class, bool $allow_string = false): bool {}

/**
 * Get the default properties of the class
 * @link http://www.php.net/manual/en/function.get-class-vars.php
 * @param string $class 
 * @return array Returns an associative array of declared properties visible from the
 * current scope, with their default value.
 * The resulting array elements are in the form of 
 * varname =&gt; value.
 * In case of an error, it returns false.
 */
function get_class_vars (string $class): array {}

/**
 * Gets the properties of the given object
 * @link http://www.php.net/manual/en/function.get-object-vars.php
 * @param object $object 
 * @return array Returns an associative array of defined object accessible non-static properties 
 * for the specified object in scope.
 */
function get_object_vars (object $object): array {}

/**
 * Returns an array of mangled object properties
 * @link http://www.php.net/manual/en/function.get-mangled-object-vars.php
 * @param object $object 
 * @return array Returns an array containing all properties, regardless of visibility, of object.
 */
function get_mangled_object_vars (object $object): array {}

/**
 * Gets the class methods' names
 * @link http://www.php.net/manual/en/function.get-class-methods.php
 * @param object|string $object_or_class 
 * @return array Returns an array of method names defined for the class specified by
 * object_or_class.
 */
function get_class_methods (object|string $object_or_class): array {}

/**
 * Checks if the class method exists
 * @link http://www.php.net/manual/en/function.method-exists.php
 * @param object|string $object_or_class 
 * @param string $method 
 * @return bool Returns true if the method given by method
 * has been defined for the given object_or_class, false
 * otherwise.
 */
function method_exists (object|string $object_or_class, string $method): bool {}

/**
 * Checks if the object or class has a property
 * @link http://www.php.net/manual/en/function.property-exists.php
 * @param object|string $object_or_class 
 * @param string $property 
 * @return bool Returns true if the property exists, false if it doesn't exist or
 * null in case of an error.
 */
function property_exists (object|string $object_or_class, string $property): bool {}

/**
 * Checks if the class has been defined
 * @link http://www.php.net/manual/en/function.class-exists.php
 * @param string $class 
 * @param bool $autoload [optional] 
 * @return bool Returns true if class is a defined class,
 * false otherwise.
 */
function class_exists (string $class, bool $autoload = true): bool {}

/**
 * Checks if the interface has been defined
 * @link http://www.php.net/manual/en/function.interface-exists.php
 * @param string $interface 
 * @param bool $autoload [optional] 
 * @return bool Returns true if the interface given by 
 * interface has been defined, false otherwise.
 */
function interface_exists (string $interface, bool $autoload = true): bool {}

/**
 * Checks if the trait exists
 * @link http://www.php.net/manual/en/function.trait-exists.php
 * @param string $trait Name of the trait to check
 * @param bool $autoload [optional] Whether to autoload
 * if not already loaded.
 * @return bool Returns true if trait exists, and false otherwise.
 */
function trait_exists (string $trait, bool $autoload = true): bool {}

/**
 * Checks if the enum has been defined
 * @link http://www.php.net/manual/en/function.enum-exists.php
 * @param string $enum 
 * @param bool $autoload [optional] 
 * @return bool Returns true if enum is a defined enum,
 * false otherwise.
 */
function enum_exists (string $enum, bool $autoload = true): bool {}

/**
 * Return true if the given function has been defined
 * @link http://www.php.net/manual/en/function.function-exists.php
 * @param string $function 
 * @return bool Returns true if function exists and is a
 * function, false otherwise.
 * <p>This function will return false for constructs, such as 
 * include_once and echo.</p>
 */
function function_exists (string $function): bool {}

/**
 * Creates an alias for a class
 * @link http://www.php.net/manual/en/function.class-alias.php
 * @param string $class 
 * @param string $alias 
 * @param bool $autoload [optional] 
 * @return bool Returns true on success or false on failure.
 */
function class_alias (string $class, string $alias, bool $autoload = true): bool {}

/**
 * Returns an array with the names of included or required files
 * @link http://www.php.net/manual/en/function.get-included-files.php
 * @return array Returns an array of the names of all files.
 * <p>The script originally called is considered an "included file," so it will
 * be listed together with the files referenced by 
 * include and family.</p>
 * <p>Files that are included or required multiple times only show up once in
 * the returned array.</p>
 */
function get_included_files (): array {}

/**
 * Alias of get_included_files
 * @link http://www.php.net/manual/en/function.get-required-files.php
 * @return array Returns an array of the names of all files.
 * <p>The script originally called is considered an "included file," so it will
 * be listed together with the files referenced by 
 * include and family.</p>
 * <p>Files that are included or required multiple times only show up once in
 * the returned array.</p>
 */
function get_required_files (): array {}

/**
 * Generates a user-level error/warning/notice message
 * @link http://www.php.net/manual/en/function.trigger-error.php
 * @param string $message 
 * @param int $error_level [optional] 
 * @return bool This function returns false if wrong error_level is
 * specified, true otherwise.
 */
function trigger_error (string $message, int $error_level = E_USER_NOTICE): bool {}

/**
 * {@inheritdoc}
 * @param string $message
 * @param int $error_level [optional]
 */
function user_error (string $message, int $error_level = 1024): bool {}

/**
 * Sets a user-defined error handler function
 * @link http://www.php.net/manual/en/function.set-error-handler.php
 * @param callable|null $callback 
 * @param int $error_levels [optional] 
 * @return callable|null Returns the previously defined error handler (if any). If
 * the built-in error handler is used null is returned.
 * If the previous error handler
 * was a class method, this function will return an indexed array with the class
 * and the method name.
 */
function set_error_handler (?callable $callback, int $error_levels = E_ALL): ?callable {}

/**
 * Restores the previous error handler function
 * @link http://www.php.net/manual/en/function.restore-error-handler.php
 * @return true Always returns true.
 */
function restore_error_handler (): true {}

/**
 * Sets a user-defined exception handler function
 * @link http://www.php.net/manual/en/function.set-exception-handler.php
 * @param callable|null $callback 
 * @return callable|null Returns the previously defined exception handler, or null on error. If
 * no previous handler was defined, null is also returned.
 */
function set_exception_handler (?callable $callback): ?callable {}

/**
 * Restores the previously defined exception handler function
 * @link http://www.php.net/manual/en/function.restore-exception-handler.php
 * @return true Always returns true.
 */
function restore_exception_handler (): true {}

/**
 * Returns an array with the name of the defined classes
 * @link http://www.php.net/manual/en/function.get-declared-classes.php
 * @return array Returns an array of the names of the declared classes in the current
 * script.
 * <p>Note that depending on what extensions you have compiled or
 * loaded into PHP, additional classes could be present. This means that
 * you will not be able to define your own classes using these
 * names. There is a list of predefined classes in the Predefined Classes section of
 * the appendices.</p>
 */
function get_declared_classes (): array {}

/**
 * Returns an array of all declared traits
 * @link http://www.php.net/manual/en/function.get-declared-traits.php
 * @return array Returns an array with names of all declared traits in values.
 */
function get_declared_traits (): array {}

/**
 * Returns an array of all declared interfaces
 * @link http://www.php.net/manual/en/function.get-declared-interfaces.php
 * @return array Returns an array of the names of the declared interfaces in the current
 * script.
 */
function get_declared_interfaces (): array {}

/**
 * Returns an array of all defined functions
 * @link http://www.php.net/manual/en/function.get-defined-functions.php
 * @param bool $exclude_disabled [optional] Whether disabled functions should be excluded from the return value.
 * @return array Returns a multidimensional array containing a list of all defined
 * functions, both built-in (internal) and user-defined. The internal
 * functions will be accessible via $arr["internal"], and
 * the user defined ones using $arr["user"] (see example
 * below).
 */
function get_defined_functions (bool $exclude_disabled = true): array {}

/**
 * Returns an array of all defined variables
 * @link http://www.php.net/manual/en/function.get-defined-vars.php
 * @return array A multidimensional array with all the variables.
 */
function get_defined_vars (): array {}

/**
 * Returns the resource type
 * @link http://www.php.net/manual/en/function.get-resource-type.php
 * @param resource $resource 
 * @return string If the given resource is a resource, this function
 * will return a string representing its type. If the type is not identified
 * by this function, the return value will be the string 
 * Unknown.
 * <p>This function will return null and generate an error if 
 * resource is not a resource.</p>
 */
function get_resource_type ($resource): string {}

/**
 * Returns an integer identifier for the given resource
 * @link http://www.php.net/manual/en/function.get-resource-id.php
 * @param resource $resource 
 * @return int The int identifier for the given resource.
 * <p>This function is essentially an int cast of
 * resource to make it easier to retrieve the resource ID.</p>
 */
function get_resource_id ($resource): int {}

/**
 * Returns active resources
 * @link http://www.php.net/manual/en/function.get-resources.php
 * @param string|null $type [optional] 
 * @return array Returns an array of currently active resources, indexed by
 * resource number.
 */
function get_resources (?string $type = null): array {}

/**
 * Returns an array with the names of all modules compiled and loaded
 * @link http://www.php.net/manual/en/function.get-loaded-extensions.php
 * @param bool $zend_extensions [optional] 
 * @return array Returns an indexed array of all the modules names.
 */
function get_loaded_extensions (bool $zend_extensions = false): array {}

/**
 * Returns an associative array with the names of all the constants and their values
 * @link http://www.php.net/manual/en/function.get-defined-constants.php
 * @param bool $categorize [optional] 
 * @return array Returns an array of constant name =&gt; constant value array, optionally
 * groupped by extension name registering the constant.
 */
function get_defined_constants (bool $categorize = false): array {}

/**
 * Generates a backtrace
 * @link http://www.php.net/manual/en/function.debug-backtrace.php
 * @param int $options [optional] 
 * @param int $limit [optional] 
 * @return array Returns an array of associative arrays. The possible returned elements
 * are as follows:
 * <p><table>
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
 * </table></p>
 */
function debug_backtrace (int $options = DEBUG_BACKTRACE_PROVIDE_OBJECT, int $limit = null): array {}

/**
 * Prints a backtrace
 * @link http://www.php.net/manual/en/function.debug-print-backtrace.php
 * @param int $options [optional] 
 * @param int $limit [optional] 
 * @return void No value is returned.
 */
function debug_print_backtrace (int $options = null, int $limit = null): void {}

/**
 * Find out whether an extension is loaded
 * @link http://www.php.net/manual/en/function.extension-loaded.php
 * @param string $extension 
 * @return bool Returns true if the extension identified by extension
 * is loaded, false otherwise.
 */
function extension_loaded (string $extension): bool {}

/**
 * Returns an array with the names of the functions of a module
 * @link http://www.php.net/manual/en/function.get-extension-funcs.php
 * @param string $extension 
 * @return array|false Returns an array with all the functions, or false if 
 * extension is not a valid extension.
 */
function get_extension_funcs (string $extension): array|false {}

/**
 * Reclaims memory used by the Zend Engine memory manager
 * @link http://www.php.net/manual/en/function.gc-mem-caches.php
 * @return int Returns the number of bytes freed.
 */
function gc_mem_caches (): int {}

/**
 * Forces collection of any existing garbage cycles
 * @link http://www.php.net/manual/en/function.gc-collect-cycles.php
 * @return int Returns number of collected cycles.
 */
function gc_collect_cycles (): int {}

/**
 * Returns status of the circular reference collector
 * @link http://www.php.net/manual/en/function.gc-enabled.php
 * @return bool Returns true if the garbage collector is enabled, false otherwise.
 */
function gc_enabled (): bool {}

/**
 * Activates the circular reference collector
 * @link http://www.php.net/manual/en/function.gc-enable.php
 * @return void No value is returned.
 */
function gc_enable (): void {}

/**
 * Deactivates the circular reference collector
 * @link http://www.php.net/manual/en/function.gc-disable.php
 * @return void No value is returned.
 */
function gc_disable (): void {}

/**
 * Gets information about the garbage collector
 * @link http://www.php.net/manual/en/function.gc-status.php
 * @return array Returns an associative array with the following elements:
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
 * 
 * Fatal run-time errors. These indicate errors that can not be
 * recovered from, such as a memory allocation problem.
 * Execution of the script is halted.
 * 
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 * @var int
 */
define ('E_ERROR', 1);

/**
 * 
 * Run-time warnings (non-fatal errors). Execution of the script is not
 * halted.
 * 
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 * @var int
 */
define ('E_WARNING', 2);

/**
 * 
 * Compile-time parse errors. Parse errors should only be generated by
 * the parser.
 * 
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 * @var int
 */
define ('E_PARSE', 4);

/**
 * 
 * Run-time notices. Indicate that the script encountered something that
 * could indicate an error, but could also happen in the normal course of
 * running a script.
 * 
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 * @var int
 */
define ('E_NOTICE', 8);

/**
 * 
 * Fatal errors that occur during PHP's initial startup. This is like an
 * E_ERROR, except it is generated by the core of PHP.
 * 
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 * @var int
 */
define ('E_CORE_ERROR', 16);

/**
 * 
 * Warnings (non-fatal errors) that occur during PHP's initial startup.
 * This is like an E_WARNING, except it is generated
 * by the core of PHP.
 * 
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 * @var int
 */
define ('E_CORE_WARNING', 32);

/**
 * 
 * Fatal compile-time errors. This is like an E_ERROR,
 * except it is generated by the Zend Scripting Engine.
 * 
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 * @var int
 */
define ('E_COMPILE_ERROR', 64);

/**
 * 
 * Compile-time warnings (non-fatal errors). This is like an
 * E_WARNING, except it is generated by the Zend
 * Scripting Engine.
 * 
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 * @var int
 */
define ('E_COMPILE_WARNING', 128);

/**
 * 
 * User-generated error message. This is like an
 * E_ERROR, except it is generated in PHP code by
 * using the PHP function trigger_error.
 * 
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 * @var int
 */
define ('E_USER_ERROR', 256);

/**
 * 
 * User-generated warning message. This is like an
 * E_WARNING, except it is generated in PHP code by
 * using the PHP function trigger_error.
 * 
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 * @var int
 */
define ('E_USER_WARNING', 512);

/**
 * 
 * User-generated notice message. This is like an
 * E_NOTICE, except it is generated in PHP code by
 * using the PHP function trigger_error.
 * 
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 * @var int
 */
define ('E_USER_NOTICE', 1024);

/**
 * 
 * Enable to have PHP suggest changes
 * to your code which will ensure the best interoperability
 * and forward compatibility of your code.
 * 
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 * @var int
 */
define ('E_STRICT', 2048);

/**
 * 
 * Catchable fatal error. It indicates that a probably dangerous error
 * occurred, but did not leave the Engine in an unstable state. If the error
 * is not caught by a user defined handle (see also
 * set_error_handler), the application aborts as it
 * was an E_ERROR.
 * 
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 * @var int
 */
define ('E_RECOVERABLE_ERROR', 4096);

/**
 * 
 * Run-time notices. Enable this to receive warnings about code
 * that will not work in future versions.
 * 
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 * @var int
 */
define ('E_DEPRECATED', 8192);

/**
 * 
 * User-generated warning message. This is like an
 * E_DEPRECATED, except it is generated in PHP code by
 * using the PHP function trigger_error.
 * 
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 * @var int
 */
define ('E_USER_DEPRECATED', 16384);

/**
 * 
 * All errors, warnings, and notices.
 * 
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 * @var int
 */
define ('E_ALL', 32767);
define ('DEBUG_BACKTRACE_PROVIDE_OBJECT', 1);
define ('DEBUG_BACKTRACE_IGNORE_ARGS', 2);
define ('ZEND_THREAD_SAFE', false);
define ('ZEND_DEBUG_BUILD', false);
define ('TRUE', true);
define ('FALSE', false);
define ('NULL', null);

/**
 * The current PHP version as a string in 
 * "major.minor.release[extra]" notation.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PHP_VERSION', "8.2.6");

/**
 * The current PHP "major" version as an integer (e.g., int(5) 
 * from version "5.2.7-extra").
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var int
 */
define ('PHP_MAJOR_VERSION', 8);

/**
 * The current PHP "minor" version as an integer (e.g., int(2) 
 * from version "5.2.7-extra").
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var int
 */
define ('PHP_MINOR_VERSION', 2);

/**
 * The current PHP "release" version as an integer (e.g., int(7) 
 * from version "5.2.7-extra").
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var int
 */
define ('PHP_RELEASE_VERSION', 6);

/**
 * The current PHP "extra" version as a string (e.g., '-extra'
 * from version "5.2.7-extra"). Often used by distribution
 * vendors to indicate a package version.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PHP_EXTRA_VERSION', "");

/**
 * The current PHP version as an integer, useful for 
 * version comparisons (e.g., int(50207) from version "5.2.7-extra").
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var int
 */
define ('PHP_VERSION_ID', 80206);

/**
 * 
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var int
 */
define ('PHP_ZTS', 0);

/**
 * 
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var int
 */
define ('PHP_DEBUG', 0);

/**
 * The operating system PHP was built for.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PHP_OS', "Darwin");

/**
 * The operating system family PHP was built for. One of
 * 'Windows', 'BSD',
 * 'Darwin', 'Solaris',
 * 'Linux' or 'Unknown'.
 * Available as of PHP 7.2.0.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PHP_OS_FAMILY', "Darwin");

/**
 * The Server API for this build of PHP.
 * See also php_sapi_name.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PHP_SAPI', "cli");

/**
 * 
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('DEFAULT_INCLUDE_PATH', ".:/opt/homebrew/Cellar/php/8.2.6/share/php/pear");

/**
 * 
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PEAR_INSTALL_DIR', "/opt/homebrew/Cellar/php/8.2.6/share/php/pear");

/**
 * 
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PEAR_EXTENSION_DIR', "/opt/homebrew/Cellar/php/8.2.6/lib/php/20220829");

/**
 * The default directory where to look for dynamically loadable extensions
 * (unless overridden by extension_dir).
 * Defaults to PHP_PREFIX (or PHP_PREFIX . "\\ext" on Windows).
 * @link http://www.php.net/manual/en/ini.extension-dir.php
 * @var string
 */
define ('PHP_EXTENSION_DIR', "/opt/homebrew/Cellar/php/8.2.6/lib/php/20220829");

/**
 * The value --prefix was set to at configure.
 * On Windows, it is the value --with-prefix
 * was set to at configure.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PHP_PREFIX', "/opt/homebrew/Cellar/php/8.2.6");

/**
 * The value --bindir was set to at configure.
 * On Windows, it is the value --with-prefix
 * was set to at configure.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PHP_BINDIR', "/opt/homebrew/Cellar/php/8.2.6/bin");

/**
 * Specifies where the manpages were installed into.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PHP_MANDIR', "/opt/homebrew/Cellar/php/8.2.6/share/man");

/**
 * 
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PHP_LIBDIR', "/opt/homebrew/Cellar/php/8.2.6/lib/php");

/**
 * 
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PHP_DATADIR', "/opt/homebrew/Cellar/php/8.2.6/share/php");

/**
 * 
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PHP_SYSCONFDIR', "/opt/homebrew/etc/php/8.2");

/**
 * 
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PHP_LOCALSTATEDIR', "/opt/homebrew/var");

/**
 * 
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PHP_CONFIG_FILE_PATH', "/opt/homebrew/etc/php/8.2");

/**
 * 
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PHP_CONFIG_FILE_SCAN_DIR', "/opt/homebrew/etc/php/8.2/conf.d");

/**
 * The build-platform's shared library suffix, such as "so" (most Unixes)
 * or "dll" (Windows).
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PHP_SHLIB_SUFFIX', "so");

/**
 * The correct 'End Of Line' symbol for this platform.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PHP_EOL', "\n");

/**
 * The maximum length of filenames (including path) supported
 * by this build of PHP.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var int
 */
define ('PHP_MAXPATHLEN', 1024);

/**
 * The largest integer supported in this build of PHP. Usually int(2147483647)
 * in 32 bit systems and int(9223372036854775807) in 64 bit systems.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var int
 */
define ('PHP_INT_MAX', 9223372036854775807);

/**
 * The smallest integer supported in this build of PHP. Usually int(-2147483648) in 32 bit systems and
 * int(-9223372036854775808) in 64 bit systems.
 * Usually, PHP_INT_MIN === ~PHP_INT_MAX.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var int
 */
define ('PHP_INT_MIN', -9223372036854775808);

/**
 * The size of an integer in bytes in this build of PHP.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var int
 */
define ('PHP_INT_SIZE', 8);

/**
 * The maximum number of file descriptors for select system calls. Available
 * as of PHP 7.1.0.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PHP_FD_SETSIZE', 1024);

/**
 * Number of decimal digits that can be rounded into a float and back
 * without precision loss.
 * Available as of PHP 7.2.0.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var int
 */
define ('PHP_FLOAT_DIG', 15);

/**
 * Smallest representable positive number x, so that x + 1.0 !=
 * 1.0.
 * Available as of PHP 7.2.0.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var float
 */
define ('PHP_FLOAT_EPSILON', 2.2204460492503E-16);

/**
 * Largest representable floating point number.
 * Available as of PHP 7.2.0.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var float
 */
define ('PHP_FLOAT_MAX', 1.7976931348623E+308);

/**
 * Smallest representable positive floating point number.
 * If you need the smallest representable negative floating point number, use - PHP_FLOAT_MAX.
 * Available as of PHP 7.2.0.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var float
 */
define ('PHP_FLOAT_MIN', 2.2250738585072E-308);

/**
 * Specifies the PHP binary path during script execution.
 * @link http://www.php.net/manual/en/reserved.constants.php
 * @var string
 */
define ('PHP_BINARY', "/opt/homebrew/Cellar/php/8.2.6/bin/php");

/**
 * Indicates that output buffering has begun.
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 * @var int
 */
define ('PHP_OUTPUT_HANDLER_START', 1);

/**
 * Indicates that the output buffer is being flushed, and had data to output.
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 * @var int
 */
define ('PHP_OUTPUT_HANDLER_WRITE', 0);

/**
 * Indicates that the buffer has been flushed.
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 * @var int
 */
define ('PHP_OUTPUT_HANDLER_FLUSH', 4);

/**
 * Indicates that the output buffer has been cleaned.
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 * @var int
 */
define ('PHP_OUTPUT_HANDLER_CLEAN', 2);

/**
 * Indicates that this is the final output buffering operation.
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 * @var int
 */
define ('PHP_OUTPUT_HANDLER_FINAL', 8);

/**
 * Indicates that the buffer has been flushed, but output buffering will
 * continue.
 * <p>This is an alias for
 * PHP_OUTPUT_HANDLER_WRITE.</p>
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 * @var int
 */
define ('PHP_OUTPUT_HANDLER_CONT', 0);

/**
 * Indicates that output buffering has ended.
 * <p>This is an alias for
 * PHP_OUTPUT_HANDLER_FINAL.</p>
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 * @var int
 */
define ('PHP_OUTPUT_HANDLER_END', 8);

/**
 * Controls whether an output buffer created by
 * ob_start can be cleaned.
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 * @var int
 */
define ('PHP_OUTPUT_HANDLER_CLEANABLE', 16);

/**
 * Controls whether an output buffer created by
 * ob_start can be flushed.
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 * @var int
 */
define ('PHP_OUTPUT_HANDLER_FLUSHABLE', 32);

/**
 * Controls whether an output buffer created by
 * ob_start can be removed before the end of the script.
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 * @var int
 */
define ('PHP_OUTPUT_HANDLER_REMOVABLE', 64);

/**
 * The default set of output buffer flags; currently equivalent to
 * PHP_OUTPUT_HANDLER_CLEANABLE |
 * PHP_OUTPUT_HANDLER_FLUSHABLE |
 * PHP_OUTPUT_HANDLER_REMOVABLE.
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 * @var int
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
