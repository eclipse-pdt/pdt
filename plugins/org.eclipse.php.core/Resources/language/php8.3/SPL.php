<?php

// Start of SPL v.8.3.0

class LogicException extends Exception implements Throwable, Stringable {

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

class BadFunctionCallException extends LogicException implements Stringable, Throwable {

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

class BadMethodCallException extends BadFunctionCallException implements Throwable, Stringable {

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

class DomainException extends LogicException implements Stringable, Throwable {

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

class InvalidArgumentException extends LogicException implements Stringable, Throwable {

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

class LengthException extends LogicException implements Stringable, Throwable {

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

class OutOfRangeException extends LogicException implements Stringable, Throwable {

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

class RuntimeException extends Exception implements Throwable, Stringable {

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

class OutOfBoundsException extends RuntimeException implements Stringable, Throwable {

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

class OverflowException extends RuntimeException implements Stringable, Throwable {

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

class RangeException extends RuntimeException implements Stringable, Throwable {

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

class UnderflowException extends RuntimeException implements Stringable, Throwable {

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

class UnexpectedValueException extends RuntimeException implements Stringable, Throwable {

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

interface RecursiveIterator extends Iterator, Traversable {

	/**
	 * {@inheritdoc}
	 */
	abstract public function hasChildren ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function getChildren ();

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

interface OuterIterator extends Iterator, Traversable {

	/**
	 * {@inheritdoc}
	 */
	abstract public function getInnerIterator ();

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

class RecursiveIteratorIterator implements OuterIterator, Traversable, Iterator {
	const LEAVES_ONLY = 0;
	const SELF_FIRST = 1;
	const CHILD_FIRST = 2;
	const CATCH_GET_CHILD = 16;


	/**
	 * {@inheritdoc}
	 * @param Traversable $iterator
	 * @param int $mode [optional]
	 * @param int $flags [optional]
	 */
	public function __construct (Traversable $iterator, int $mode = 0, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDepth () {}

	/**
	 * {@inheritdoc}
	 * @param int|null $level [optional]
	 */
	public function getSubIterator (?int $level = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getInnerIterator () {}

	/**
	 * {@inheritdoc}
	 */
	public function beginIteration () {}

	/**
	 * {@inheritdoc}
	 */
	public function endIteration () {}

	/**
	 * {@inheritdoc}
	 */
	public function callHasChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function callGetChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function beginChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function endChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function nextElement () {}

	/**
	 * {@inheritdoc}
	 * @param int $maxDepth [optional]
	 */
	public function setMaxDepth (int $maxDepth = -1) {}

	/**
	 * {@inheritdoc}
	 */
	public function getMaxDepth () {}

}

class IteratorIterator implements OuterIterator, Traversable, Iterator {

	/**
	 * {@inheritdoc}
	 * @param Traversable $iterator
	 * @param string|null $class [optional]
	 */
	public function __construct (Traversable $iterator, ?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getInnerIterator () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

}

abstract class FilterIterator extends IteratorIterator implements Iterator, Traversable, OuterIterator {

	/**
	 * {@inheritdoc}
	 */
	abstract public function accept ();

	/**
	 * {@inheritdoc}
	 * @param Iterator $iterator
	 */
	public function __construct (Iterator $iterator) {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInnerIterator () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

}

abstract class RecursiveFilterIterator extends FilterIterator implements OuterIterator, Traversable, Iterator, RecursiveIterator {

	/**
	 * {@inheritdoc}
	 * @param RecursiveIterator $iterator
	 */
	public function __construct (RecursiveIterator $iterator) {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function getChildren () {}

	/**
	 * {@inheritdoc}
	 */
	abstract public function accept ();

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInnerIterator () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

}

class CallbackFilterIterator extends FilterIterator implements OuterIterator, Traversable, Iterator {

	/**
	 * {@inheritdoc}
	 * @param Iterator $iterator
	 * @param callable $callback
	 */
	public function __construct (Iterator $iterator, callable $callback) {}

	/**
	 * {@inheritdoc}
	 */
	public function accept () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInnerIterator () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

}

class RecursiveCallbackFilterIterator extends CallbackFilterIterator implements Iterator, Traversable, OuterIterator, RecursiveIterator {

	/**
	 * {@inheritdoc}
	 * @param RecursiveIterator $iterator
	 * @param callable $callback
	 */
	public function __construct (RecursiveIterator $iterator, callable $callback) {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function getChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function accept () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInnerIterator () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

}

class ParentIterator extends RecursiveFilterIterator implements RecursiveIterator, Iterator, Traversable, OuterIterator {

	/**
	 * {@inheritdoc}
	 * @param RecursiveIterator $iterator
	 */
	public function __construct (RecursiveIterator $iterator) {}

	/**
	 * {@inheritdoc}
	 */
	public function accept () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function getChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInnerIterator () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

}

interface SeekableIterator extends Iterator, Traversable {

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	abstract public function seek (int $offset);

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

class LimitIterator extends IteratorIterator implements Iterator, Traversable, OuterIterator {

	/**
	 * {@inheritdoc}
	 * @param Iterator $iterator
	 * @param int $offset [optional]
	 * @param int $limit [optional]
	 */
	public function __construct (Iterator $iterator, int $offset = 0, int $limit = -1) {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function seek (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function getPosition () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInnerIterator () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

}

class CachingIterator extends IteratorIterator implements Stringable, Iterator, Traversable, OuterIterator, ArrayAccess, Countable {
	const CALL_TOSTRING = 1;
	const CATCH_GET_CHILD = 16;
	const TOSTRING_USE_KEY = 2;
	const TOSTRING_USE_CURRENT = 4;
	const TOSTRING_USE_INNER = 8;
	const FULL_CACHE = 256;


	/**
	 * {@inheritdoc}
	 * @param Iterator $iterator
	 * @param int $flags [optional]
	 */
	public function __construct (Iterator $iterator, int $flags = 1) {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasNext () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags () {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function setFlags (int $flags) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetGet ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function offsetSet ($key = null, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetUnset ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetExists ($key = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getCache () {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInnerIterator () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

}

class RecursiveCachingIterator extends CachingIterator implements Countable, ArrayAccess, OuterIterator, Traversable, Iterator, Stringable, RecursiveIterator {
	const CALL_TOSTRING = 1;
	const CATCH_GET_CHILD = 16;
	const TOSTRING_USE_KEY = 2;
	const TOSTRING_USE_CURRENT = 4;
	const TOSTRING_USE_INNER = 8;
	const FULL_CACHE = 256;


	/**
	 * {@inheritdoc}
	 * @param Iterator $iterator
	 * @param int $flags [optional]
	 */
	public function __construct (Iterator $iterator, int $flags = 1) {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function getChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasNext () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags () {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function setFlags (int $flags) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetGet ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function offsetSet ($key = null, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetUnset ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetExists ($key = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getCache () {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInnerIterator () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

}

class NoRewindIterator extends IteratorIterator implements Iterator, Traversable, OuterIterator {

	/**
	 * {@inheritdoc}
	 * @param Iterator $iterator
	 */
	public function __construct (Iterator $iterator) {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInnerIterator () {}

}

class AppendIterator extends IteratorIterator implements Iterator, Traversable, OuterIterator {

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param Iterator $iterator
	 */
	public function append (Iterator $iterator) {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function getIteratorIndex () {}

	/**
	 * {@inheritdoc}
	 */
	public function getArrayIterator () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInnerIterator () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

}

class InfiniteIterator extends IteratorIterator implements Iterator, Traversable, OuterIterator {

	/**
	 * {@inheritdoc}
	 * @param Iterator $iterator
	 */
	public function __construct (Iterator $iterator) {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInnerIterator () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

}

class RegexIterator extends FilterIterator implements OuterIterator, Traversable, Iterator {
	const USE_KEY = 1;
	const INVERT_MATCH = 2;
	const MATCH = 0;
	const GET_MATCH = 1;
	const ALL_MATCHES = 2;
	const SPLIT = 3;
	const REPLACE = 4;


	public ?string $replacement;

	/**
	 * {@inheritdoc}
	 * @param Iterator $iterator
	 * @param string $pattern
	 * @param int $mode [optional]
	 * @param int $flags [optional]
	 * @param int $pregFlags [optional]
	 */
	public function __construct (Iterator $iterator, string $pattern, int $mode = 0, int $flags = 0, int $pregFlags = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function accept () {}

	/**
	 * {@inheritdoc}
	 */
	public function getMode () {}

	/**
	 * {@inheritdoc}
	 * @param int $mode
	 */
	public function setMode (int $mode) {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags () {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function setFlags (int $flags) {}

	/**
	 * {@inheritdoc}
	 */
	public function getRegex () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPregFlags () {}

	/**
	 * {@inheritdoc}
	 * @param int $pregFlags
	 */
	public function setPregFlags (int $pregFlags) {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInnerIterator () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

}

class RecursiveRegexIterator extends RegexIterator implements Iterator, Traversable, OuterIterator, RecursiveIterator {
	const USE_KEY = 1;
	const INVERT_MATCH = 2;
	const MATCH = 0;
	const GET_MATCH = 1;
	const ALL_MATCHES = 2;
	const SPLIT = 3;
	const REPLACE = 4;


	/**
	 * {@inheritdoc}
	 * @param RecursiveIterator $iterator
	 * @param string $pattern
	 * @param int $mode [optional]
	 * @param int $flags [optional]
	 * @param int $pregFlags [optional]
	 */
	public function __construct (RecursiveIterator $iterator, string $pattern, int $mode = 0, int $flags = 0, int $pregFlags = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function accept () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function getChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function getMode () {}

	/**
	 * {@inheritdoc}
	 * @param int $mode
	 */
	public function setMode (int $mode) {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags () {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function setFlags (int $flags) {}

	/**
	 * {@inheritdoc}
	 */
	public function getRegex () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPregFlags () {}

	/**
	 * {@inheritdoc}
	 * @param int $pregFlags
	 */
	public function setPregFlags (int $pregFlags) {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInnerIterator () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

}

class EmptyIterator implements Iterator, Traversable {

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

}

class RecursiveTreeIterator extends RecursiveIteratorIterator implements Iterator, Traversable, OuterIterator {
	const LEAVES_ONLY = 0;
	const SELF_FIRST = 1;
	const CHILD_FIRST = 2;
	const CATCH_GET_CHILD = 16;
	const BYPASS_CURRENT = 4;
	const BYPASS_KEY = 8;
	const PREFIX_LEFT = 0;
	const PREFIX_MID_HAS_NEXT = 1;
	const PREFIX_MID_LAST = 2;
	const PREFIX_END_HAS_NEXT = 3;
	const PREFIX_END_LAST = 4;
	const PREFIX_RIGHT = 5;


	/**
	 * {@inheritdoc}
	 * @param mixed $iterator
	 * @param int $flags [optional]
	 * @param int $cachingIteratorFlags [optional]
	 * @param int $mode [optional]
	 */
	public function __construct ($iterator = null, int $flags = 8, int $cachingIteratorFlags = 16, int $mode = 1) {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPrefix () {}

	/**
	 * {@inheritdoc}
	 * @param string $postfix
	 */
	public function setPostfix (string $postfix) {}

	/**
	 * {@inheritdoc}
	 * @param int $part
	 * @param string $value
	 */
	public function setPrefixPart (int $part, string $value) {}

	/**
	 * {@inheritdoc}
	 */
	public function getEntry () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPostfix () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDepth () {}

	/**
	 * {@inheritdoc}
	 * @param int|null $level [optional]
	 */
	public function getSubIterator (?int $level = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getInnerIterator () {}

	/**
	 * {@inheritdoc}
	 */
	public function beginIteration () {}

	/**
	 * {@inheritdoc}
	 */
	public function endIteration () {}

	/**
	 * {@inheritdoc}
	 */
	public function callHasChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function callGetChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function beginChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function endChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function nextElement () {}

	/**
	 * {@inheritdoc}
	 * @param int $maxDepth [optional]
	 */
	public function setMaxDepth (int $maxDepth = -1) {}

	/**
	 * {@inheritdoc}
	 */
	public function getMaxDepth () {}

}

class ArrayObject implements IteratorAggregate, Traversable, ArrayAccess, Serializable, Countable {
	const STD_PROP_LIST = 1;
	const ARRAY_AS_PROPS = 2;


	/**
	 * {@inheritdoc}
	 * @param object|array $array [optional]
	 * @param int $flags [optional]
	 * @param string $iteratorClass [optional]
	 */
	public function __construct (object|array $array = array (
), int $flags = 0, string $iteratorClass = 'ArrayIterator') {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetExists (mixed $key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetGet (mixed $key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function offsetSet (mixed $key = null, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetUnset (mixed $key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function append (mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getArrayCopy () {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags () {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function setFlags (int $flags) {}

	/**
	 * {@inheritdoc}
	 * @param int $flags [optional]
	 */
	public function asort (int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param int $flags [optional]
	 */
	public function ksort (int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public function uasort (callable $callback) {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public function uksort (callable $callback) {}

	/**
	 * {@inheritdoc}
	 */
	public function natsort () {}

	/**
	 * {@inheritdoc}
	 */
	public function natcasesort () {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function unserialize (string $data) {}

	/**
	 * {@inheritdoc}
	 */
	public function serialize () {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize () {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data) {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator () {}

	/**
	 * {@inheritdoc}
	 * @param object|array $array
	 */
	public function exchangeArray (object|array $array) {}

	/**
	 * {@inheritdoc}
	 * @param string $iteratorClass
	 */
	public function setIteratorClass (string $iteratorClass) {}

	/**
	 * {@inheritdoc}
	 */
	public function getIteratorClass () {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

}

class ArrayIterator implements SeekableIterator, Traversable, Iterator, ArrayAccess, Serializable, Countable {
	const STD_PROP_LIST = 1;
	const ARRAY_AS_PROPS = 2;


	/**
	 * {@inheritdoc}
	 * @param object|array $array [optional]
	 * @param int $flags [optional]
	 */
	public function __construct (object|array $array = array (
), int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetExists (mixed $key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetGet (mixed $key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function offsetSet (mixed $key = null, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetUnset (mixed $key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function append (mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getArrayCopy () {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags () {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function setFlags (int $flags) {}

	/**
	 * {@inheritdoc}
	 * @param int $flags [optional]
	 */
	public function asort (int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param int $flags [optional]
	 */
	public function ksort (int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public function uasort (callable $callback) {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public function uksort (callable $callback) {}

	/**
	 * {@inheritdoc}
	 */
	public function natsort () {}

	/**
	 * {@inheritdoc}
	 */
	public function natcasesort () {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function unserialize (string $data) {}

	/**
	 * {@inheritdoc}
	 */
	public function serialize () {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize () {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data) {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function seek (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

}

class RecursiveArrayIterator extends ArrayIterator implements Countable, Serializable, ArrayAccess, Iterator, Traversable, SeekableIterator, RecursiveIterator {
	const STD_PROP_LIST = 1;
	const ARRAY_AS_PROPS = 2;
	const CHILD_ARRAYS_ONLY = 4;


	/**
	 * {@inheritdoc}
	 */
	public function hasChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function getChildren () {}

	/**
	 * {@inheritdoc}
	 * @param object|array $array [optional]
	 * @param int $flags [optional]
	 */
	public function __construct (object|array $array = array (
), int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetExists (mixed $key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetGet (mixed $key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function offsetSet (mixed $key = null, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetUnset (mixed $key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function append (mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getArrayCopy () {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags () {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function setFlags (int $flags) {}

	/**
	 * {@inheritdoc}
	 * @param int $flags [optional]
	 */
	public function asort (int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param int $flags [optional]
	 */
	public function ksort (int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public function uasort (callable $callback) {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public function uksort (callable $callback) {}

	/**
	 * {@inheritdoc}
	 */
	public function natsort () {}

	/**
	 * {@inheritdoc}
	 */
	public function natcasesort () {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function unserialize (string $data) {}

	/**
	 * {@inheritdoc}
	 */
	public function serialize () {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize () {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data) {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function seek (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

}

class SplFileInfo implements Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 */
	public function __construct (string $filename) {}

	/**
	 * {@inheritdoc}
	 */
	public function getPath () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFilename () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtension () {}

	/**
	 * {@inheritdoc}
	 * @param string $suffix [optional]
	 */
	public function getBasename (string $suffix = '') {}

	/**
	 * {@inheritdoc}
	 */
	public function getPathname () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPerms () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSize () {}

	/**
	 * {@inheritdoc}
	 */
	public function getOwner () {}

	/**
	 * {@inheritdoc}
	 */
	public function getGroup () {}

	/**
	 * {@inheritdoc}
	 */
	public function getATime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getMTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getCTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 */
	public function isWritable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isReadable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isExecutable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isFile () {}

	/**
	 * {@inheritdoc}
	 */
	public function isDir () {}

	/**
	 * {@inheritdoc}
	 */
	public function isLink () {}

	/**
	 * {@inheritdoc}
	 */
	public function getLinkTarget () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRealPath () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getFileInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getPathInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $mode [optional]
	 * @param bool $useIncludePath [optional]
	 * @param mixed $context [optional]
	 */
	public function openFile (string $mode = 'r', bool $useIncludePath = false, $context = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setFileClass (string $class = 'SplFileObject') {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setInfoClass (string $class = 'SplFileInfo') {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

	/**
	 * {@inheritdoc}
	 * @deprecated 
	 */
	final public function _bad_state_ex () {}

}

class DirectoryIterator extends SplFileInfo implements Stringable, SeekableIterator, Traversable, Iterator {

	/**
	 * {@inheritdoc}
	 * @param string $directory
	 */
	public function __construct (string $directory) {}

	/**
	 * {@inheritdoc}
	 */
	public function getFilename () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtension () {}

	/**
	 * {@inheritdoc}
	 * @param string $suffix [optional]
	 */
	public function getBasename (string $suffix = '') {}

	/**
	 * {@inheritdoc}
	 */
	public function isDot () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function seek (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getPath () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPathname () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPerms () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSize () {}

	/**
	 * {@inheritdoc}
	 */
	public function getOwner () {}

	/**
	 * {@inheritdoc}
	 */
	public function getGroup () {}

	/**
	 * {@inheritdoc}
	 */
	public function getATime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getMTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getCTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 */
	public function isWritable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isReadable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isExecutable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isFile () {}

	/**
	 * {@inheritdoc}
	 */
	public function isDir () {}

	/**
	 * {@inheritdoc}
	 */
	public function isLink () {}

	/**
	 * {@inheritdoc}
	 */
	public function getLinkTarget () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRealPath () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getFileInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getPathInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $mode [optional]
	 * @param bool $useIncludePath [optional]
	 * @param mixed $context [optional]
	 */
	public function openFile (string $mode = 'r', bool $useIncludePath = false, $context = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setFileClass (string $class = 'SplFileObject') {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setInfoClass (string $class = 'SplFileInfo') {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

	/**
	 * {@inheritdoc}
	 * @deprecated 
	 */
	final public function _bad_state_ex () {}

}

class FilesystemIterator extends DirectoryIterator implements Iterator, Traversable, SeekableIterator, Stringable {
	const CURRENT_MODE_MASK = 240;
	const CURRENT_AS_PATHNAME = 32;
	const CURRENT_AS_FILEINFO = 0;
	const CURRENT_AS_SELF = 16;
	const KEY_MODE_MASK = 3840;
	const KEY_AS_PATHNAME = 0;
	const FOLLOW_SYMLINKS = 16384;
	const KEY_AS_FILENAME = 256;
	const NEW_CURRENT_AND_KEY = 256;
	const OTHER_MODE_MASK = 28672;
	const SKIP_DOTS = 4096;
	const UNIX_PATHS = 8192;


	/**
	 * {@inheritdoc}
	 * @param string $directory
	 * @param int $flags [optional]
	 */
	public function __construct (string $directory, int $flags = 4096) {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags () {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function setFlags (int $flags) {}

	/**
	 * {@inheritdoc}
	 */
	public function getFilename () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtension () {}

	/**
	 * {@inheritdoc}
	 * @param string $suffix [optional]
	 */
	public function getBasename (string $suffix = '') {}

	/**
	 * {@inheritdoc}
	 */
	public function isDot () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function seek (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getPath () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPathname () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPerms () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSize () {}

	/**
	 * {@inheritdoc}
	 */
	public function getOwner () {}

	/**
	 * {@inheritdoc}
	 */
	public function getGroup () {}

	/**
	 * {@inheritdoc}
	 */
	public function getATime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getMTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getCTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 */
	public function isWritable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isReadable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isExecutable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isFile () {}

	/**
	 * {@inheritdoc}
	 */
	public function isDir () {}

	/**
	 * {@inheritdoc}
	 */
	public function isLink () {}

	/**
	 * {@inheritdoc}
	 */
	public function getLinkTarget () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRealPath () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getFileInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getPathInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $mode [optional]
	 * @param bool $useIncludePath [optional]
	 * @param mixed $context [optional]
	 */
	public function openFile (string $mode = 'r', bool $useIncludePath = false, $context = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setFileClass (string $class = 'SplFileObject') {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setInfoClass (string $class = 'SplFileInfo') {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

	/**
	 * {@inheritdoc}
	 * @deprecated 
	 */
	final public function _bad_state_ex () {}

}

class RecursiveDirectoryIterator extends FilesystemIterator implements Stringable, SeekableIterator, Traversable, Iterator, RecursiveIterator {
	const CURRENT_MODE_MASK = 240;
	const CURRENT_AS_PATHNAME = 32;
	const CURRENT_AS_FILEINFO = 0;
	const CURRENT_AS_SELF = 16;
	const KEY_MODE_MASK = 3840;
	const KEY_AS_PATHNAME = 0;
	const FOLLOW_SYMLINKS = 16384;
	const KEY_AS_FILENAME = 256;
	const NEW_CURRENT_AND_KEY = 256;
	const OTHER_MODE_MASK = 28672;
	const SKIP_DOTS = 4096;
	const UNIX_PATHS = 8192;


	/**
	 * {@inheritdoc}
	 * @param string $directory
	 * @param int $flags [optional]
	 */
	public function __construct (string $directory, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param bool $allowLinks [optional]
	 */
	public function hasChildren (bool $allowLinks = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function getChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSubPath () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSubPathname () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags () {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function setFlags (int $flags) {}

	/**
	 * {@inheritdoc}
	 */
	public function getFilename () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtension () {}

	/**
	 * {@inheritdoc}
	 * @param string $suffix [optional]
	 */
	public function getBasename (string $suffix = '') {}

	/**
	 * {@inheritdoc}
	 */
	public function isDot () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function seek (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getPath () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPathname () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPerms () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSize () {}

	/**
	 * {@inheritdoc}
	 */
	public function getOwner () {}

	/**
	 * {@inheritdoc}
	 */
	public function getGroup () {}

	/**
	 * {@inheritdoc}
	 */
	public function getATime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getMTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getCTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 */
	public function isWritable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isReadable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isExecutable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isFile () {}

	/**
	 * {@inheritdoc}
	 */
	public function isDir () {}

	/**
	 * {@inheritdoc}
	 */
	public function isLink () {}

	/**
	 * {@inheritdoc}
	 */
	public function getLinkTarget () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRealPath () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getFileInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getPathInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $mode [optional]
	 * @param bool $useIncludePath [optional]
	 * @param mixed $context [optional]
	 */
	public function openFile (string $mode = 'r', bool $useIncludePath = false, $context = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setFileClass (string $class = 'SplFileObject') {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setInfoClass (string $class = 'SplFileInfo') {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

	/**
	 * {@inheritdoc}
	 * @deprecated 
	 */
	final public function _bad_state_ex () {}

}

class GlobIterator extends FilesystemIterator implements Stringable, SeekableIterator, Traversable, Iterator, Countable {
	const CURRENT_MODE_MASK = 240;
	const CURRENT_AS_PATHNAME = 32;
	const CURRENT_AS_FILEINFO = 0;
	const CURRENT_AS_SELF = 16;
	const KEY_MODE_MASK = 3840;
	const KEY_AS_PATHNAME = 0;
	const FOLLOW_SYMLINKS = 16384;
	const KEY_AS_FILENAME = 256;
	const NEW_CURRENT_AND_KEY = 256;
	const OTHER_MODE_MASK = 28672;
	const SKIP_DOTS = 4096;
	const UNIX_PATHS = 8192;


	/**
	 * {@inheritdoc}
	 * @param string $pattern
	 * @param int $flags [optional]
	 */
	public function __construct (string $pattern, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags () {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function setFlags (int $flags) {}

	/**
	 * {@inheritdoc}
	 */
	public function getFilename () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtension () {}

	/**
	 * {@inheritdoc}
	 * @param string $suffix [optional]
	 */
	public function getBasename (string $suffix = '') {}

	/**
	 * {@inheritdoc}
	 */
	public function isDot () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function seek (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getPath () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPathname () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPerms () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSize () {}

	/**
	 * {@inheritdoc}
	 */
	public function getOwner () {}

	/**
	 * {@inheritdoc}
	 */
	public function getGroup () {}

	/**
	 * {@inheritdoc}
	 */
	public function getATime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getMTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getCTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 */
	public function isWritable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isReadable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isExecutable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isFile () {}

	/**
	 * {@inheritdoc}
	 */
	public function isDir () {}

	/**
	 * {@inheritdoc}
	 */
	public function isLink () {}

	/**
	 * {@inheritdoc}
	 */
	public function getLinkTarget () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRealPath () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getFileInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getPathInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $mode [optional]
	 * @param bool $useIncludePath [optional]
	 * @param mixed $context [optional]
	 */
	public function openFile (string $mode = 'r', bool $useIncludePath = false, $context = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setFileClass (string $class = 'SplFileObject') {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setInfoClass (string $class = 'SplFileInfo') {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

	/**
	 * {@inheritdoc}
	 * @deprecated 
	 */
	final public function _bad_state_ex () {}

}

class SplFileObject extends SplFileInfo implements Stringable, RecursiveIterator, Traversable, Iterator, SeekableIterator {
	const DROP_NEW_LINE = 1;
	const READ_AHEAD = 2;
	const SKIP_EMPTY = 4;
	const READ_CSV = 8;


	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param string $mode [optional]
	 * @param bool $useIncludePath [optional]
	 * @param mixed $context [optional]
	 */
	public function __construct (string $filename, string $mode = 'r', bool $useIncludePath = false, $context = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function eof () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function fgets () {}

	/**
	 * {@inheritdoc}
	 * @param int $length
	 */
	public function fread (int $length) {}

	/**
	 * {@inheritdoc}
	 * @param string $separator [optional]
	 * @param string $enclosure [optional]
	 * @param string $escape [optional]
	 */
	public function fgetcsv (string $separator = ',', string $enclosure = '"', string $escape = '\\') {}

	/**
	 * {@inheritdoc}
	 * @param array $fields
	 * @param string $separator [optional]
	 * @param string $enclosure [optional]
	 * @param string $escape [optional]
	 * @param string $eol [optional]
	 */
	public function fputcsv (array $fields, string $separator = ',', string $enclosure = '"', string $escape = '\\', string $eol = '
') {}

	/**
	 * {@inheritdoc}
	 * @param string $separator [optional]
	 * @param string $enclosure [optional]
	 * @param string $escape [optional]
	 */
	public function setCsvControl (string $separator = ',', string $enclosure = '"', string $escape = '\\') {}

	/**
	 * {@inheritdoc}
	 */
	public function getCsvControl () {}

	/**
	 * {@inheritdoc}
	 * @param int $operation
	 * @param mixed $wouldBlock [optional]
	 */
	public function flock (int $operation, &$wouldBlock = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function fflush () {}

	/**
	 * {@inheritdoc}
	 */
	public function ftell () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 * @param int $whence [optional]
	 */
	public function fseek (int $offset, int $whence = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function fgetc () {}

	/**
	 * {@inheritdoc}
	 */
	public function fpassthru () {}

	/**
	 * {@inheritdoc}
	 * @param string $format
	 * @param mixed $vars [optional]
	 */
	public function fscanf (string $format, mixed &...$vars) {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 * @param int $length [optional]
	 */
	public function fwrite (string $data, int $length = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function fstat () {}

	/**
	 * {@inheritdoc}
	 * @param int $size
	 */
	public function ftruncate (int $size) {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function setFlags (int $flags) {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags () {}

	/**
	 * {@inheritdoc}
	 * @param int $maxLength
	 */
	public function setMaxLineLen (int $maxLength) {}

	/**
	 * {@inheritdoc}
	 */
	public function getMaxLineLen () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function getChildren () {}

	/**
	 * {@inheritdoc}
	 * @param int $line
	 */
	public function seek (int $line) {}

	/**
	 * {@inheritdoc}
	 */
	public function getCurrentLine () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getPath () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFilename () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtension () {}

	/**
	 * {@inheritdoc}
	 * @param string $suffix [optional]
	 */
	public function getBasename (string $suffix = '') {}

	/**
	 * {@inheritdoc}
	 */
	public function getPathname () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPerms () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSize () {}

	/**
	 * {@inheritdoc}
	 */
	public function getOwner () {}

	/**
	 * {@inheritdoc}
	 */
	public function getGroup () {}

	/**
	 * {@inheritdoc}
	 */
	public function getATime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getMTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getCTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 */
	public function isWritable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isReadable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isExecutable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isFile () {}

	/**
	 * {@inheritdoc}
	 */
	public function isDir () {}

	/**
	 * {@inheritdoc}
	 */
	public function isLink () {}

	/**
	 * {@inheritdoc}
	 */
	public function getLinkTarget () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRealPath () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getFileInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getPathInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $mode [optional]
	 * @param bool $useIncludePath [optional]
	 * @param mixed $context [optional]
	 */
	public function openFile (string $mode = 'r', bool $useIncludePath = false, $context = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setFileClass (string $class = 'SplFileObject') {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setInfoClass (string $class = 'SplFileInfo') {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

	/**
	 * {@inheritdoc}
	 * @deprecated 
	 */
	final public function _bad_state_ex () {}

}

class SplTempFileObject extends SplFileObject implements SeekableIterator, Iterator, Traversable, RecursiveIterator, Stringable {
	const DROP_NEW_LINE = 1;
	const READ_AHEAD = 2;
	const SKIP_EMPTY = 4;
	const READ_CSV = 8;


	/**
	 * {@inheritdoc}
	 * @param int $maxMemory [optional]
	 */
	public function __construct (int $maxMemory = 2097152) {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function eof () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function fgets () {}

	/**
	 * {@inheritdoc}
	 * @param int $length
	 */
	public function fread (int $length) {}

	/**
	 * {@inheritdoc}
	 * @param string $separator [optional]
	 * @param string $enclosure [optional]
	 * @param string $escape [optional]
	 */
	public function fgetcsv (string $separator = ',', string $enclosure = '"', string $escape = '\\') {}

	/**
	 * {@inheritdoc}
	 * @param array $fields
	 * @param string $separator [optional]
	 * @param string $enclosure [optional]
	 * @param string $escape [optional]
	 * @param string $eol [optional]
	 */
	public function fputcsv (array $fields, string $separator = ',', string $enclosure = '"', string $escape = '\\', string $eol = '
') {}

	/**
	 * {@inheritdoc}
	 * @param string $separator [optional]
	 * @param string $enclosure [optional]
	 * @param string $escape [optional]
	 */
	public function setCsvControl (string $separator = ',', string $enclosure = '"', string $escape = '\\') {}

	/**
	 * {@inheritdoc}
	 */
	public function getCsvControl () {}

	/**
	 * {@inheritdoc}
	 * @param int $operation
	 * @param mixed $wouldBlock [optional]
	 */
	public function flock (int $operation, &$wouldBlock = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function fflush () {}

	/**
	 * {@inheritdoc}
	 */
	public function ftell () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 * @param int $whence [optional]
	 */
	public function fseek (int $offset, int $whence = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function fgetc () {}

	/**
	 * {@inheritdoc}
	 */
	public function fpassthru () {}

	/**
	 * {@inheritdoc}
	 * @param string $format
	 * @param mixed $vars [optional]
	 */
	public function fscanf (string $format, mixed &...$vars) {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 * @param int $length [optional]
	 */
	public function fwrite (string $data, int $length = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function fstat () {}

	/**
	 * {@inheritdoc}
	 * @param int $size
	 */
	public function ftruncate (int $size) {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function setFlags (int $flags) {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags () {}

	/**
	 * {@inheritdoc}
	 * @param int $maxLength
	 */
	public function setMaxLineLen (int $maxLength) {}

	/**
	 * {@inheritdoc}
	 */
	public function getMaxLineLen () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function getChildren () {}

	/**
	 * {@inheritdoc}
	 * @param int $line
	 */
	public function seek (int $line) {}

	/**
	 * {@inheritdoc}
	 */
	public function getCurrentLine () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getPath () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFilename () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtension () {}

	/**
	 * {@inheritdoc}
	 * @param string $suffix [optional]
	 */
	public function getBasename (string $suffix = '') {}

	/**
	 * {@inheritdoc}
	 */
	public function getPathname () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPerms () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSize () {}

	/**
	 * {@inheritdoc}
	 */
	public function getOwner () {}

	/**
	 * {@inheritdoc}
	 */
	public function getGroup () {}

	/**
	 * {@inheritdoc}
	 */
	public function getATime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getMTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getCTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 */
	public function isWritable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isReadable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isExecutable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isFile () {}

	/**
	 * {@inheritdoc}
	 */
	public function isDir () {}

	/**
	 * {@inheritdoc}
	 */
	public function isLink () {}

	/**
	 * {@inheritdoc}
	 */
	public function getLinkTarget () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRealPath () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getFileInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getPathInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $mode [optional]
	 * @param bool $useIncludePath [optional]
	 * @param mixed $context [optional]
	 */
	public function openFile (string $mode = 'r', bool $useIncludePath = false, $context = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setFileClass (string $class = 'SplFileObject') {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setInfoClass (string $class = 'SplFileInfo') {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

	/**
	 * {@inheritdoc}
	 * @deprecated 
	 */
	final public function _bad_state_ex () {}

}

class SplDoublyLinkedList implements Iterator, Traversable, Countable, ArrayAccess, Serializable {
	const IT_MODE_LIFO = 2;
	const IT_MODE_FIFO = 0;
	const IT_MODE_DELETE = 1;
	const IT_MODE_KEEP = 0;


	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param mixed $value
	 */
	public function add (int $index, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function pop () {}

	/**
	 * {@inheritdoc}
	 */
	public function shift () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function push (mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function unshift (mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function top () {}

	/**
	 * {@inheritdoc}
	 */
	public function bottom () {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function isEmpty () {}

	/**
	 * {@inheritdoc}
	 * @param int $mode
	 */
	public function setIteratorMode (int $mode) {}

	/**
	 * {@inheritdoc}
	 */
	public function getIteratorMode () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 */
	public function offsetExists ($index = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 */
	public function offsetGet ($index = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 * @param mixed $value
	 */
	public function offsetSet ($index = null, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 */
	public function offsetUnset ($index = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function prev () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function unserialize (string $data) {}

	/**
	 * {@inheritdoc}
	 */
	public function serialize () {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize () {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data) {}

}

class SplQueue extends SplDoublyLinkedList implements Serializable, ArrayAccess, Countable, Traversable, Iterator {
	const IT_MODE_LIFO = 2;
	const IT_MODE_FIFO = 0;
	const IT_MODE_DELETE = 1;
	const IT_MODE_KEEP = 0;


	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function enqueue (mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function dequeue () {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param mixed $value
	 */
	public function add (int $index, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function pop () {}

	/**
	 * {@inheritdoc}
	 */
	public function shift () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function push (mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function unshift (mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function top () {}

	/**
	 * {@inheritdoc}
	 */
	public function bottom () {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function isEmpty () {}

	/**
	 * {@inheritdoc}
	 * @param int $mode
	 */
	public function setIteratorMode (int $mode) {}

	/**
	 * {@inheritdoc}
	 */
	public function getIteratorMode () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 */
	public function offsetExists ($index = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 */
	public function offsetGet ($index = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 * @param mixed $value
	 */
	public function offsetSet ($index = null, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 */
	public function offsetUnset ($index = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function prev () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function unserialize (string $data) {}

	/**
	 * {@inheritdoc}
	 */
	public function serialize () {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize () {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data) {}

}

class SplStack extends SplDoublyLinkedList implements Serializable, ArrayAccess, Countable, Traversable, Iterator {
	const IT_MODE_LIFO = 2;
	const IT_MODE_FIFO = 0;
	const IT_MODE_DELETE = 1;
	const IT_MODE_KEEP = 0;


	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param mixed $value
	 */
	public function add (int $index, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function pop () {}

	/**
	 * {@inheritdoc}
	 */
	public function shift () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function push (mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function unshift (mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function top () {}

	/**
	 * {@inheritdoc}
	 */
	public function bottom () {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function isEmpty () {}

	/**
	 * {@inheritdoc}
	 * @param int $mode
	 */
	public function setIteratorMode (int $mode) {}

	/**
	 * {@inheritdoc}
	 */
	public function getIteratorMode () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 */
	public function offsetExists ($index = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 */
	public function offsetGet ($index = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 * @param mixed $value
	 */
	public function offsetSet ($index = null, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 */
	public function offsetUnset ($index = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function prev () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function unserialize (string $data) {}

	/**
	 * {@inheritdoc}
	 */
	public function serialize () {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize () {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data) {}

}

abstract class SplHeap implements Iterator, Traversable, Countable {

	/**
	 * {@inheritdoc}
	 */
	public function extract () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function insert (mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function top () {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function isEmpty () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function recoverFromCorruption () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value1
	 * @param mixed $value2
	 */
	abstract protected function compare (mixed $value1 = null, mixed $value2 = null);

	/**
	 * {@inheritdoc}
	 */
	public function isCorrupted () {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

}

class SplMinHeap extends SplHeap implements Countable, Traversable, Iterator {

	/**
	 * {@inheritdoc}
	 * @param mixed $value1
	 * @param mixed $value2
	 */
	protected function compare (mixed $value1 = null, mixed $value2 = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function extract () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function insert (mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function top () {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function isEmpty () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function recoverFromCorruption () {}

	/**
	 * {@inheritdoc}
	 */
	public function isCorrupted () {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

}

class SplMaxHeap extends SplHeap implements Countable, Traversable, Iterator {

	/**
	 * {@inheritdoc}
	 * @param mixed $value1
	 * @param mixed $value2
	 */
	protected function compare (mixed $value1 = null, mixed $value2 = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function extract () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function insert (mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function top () {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function isEmpty () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function recoverFromCorruption () {}

	/**
	 * {@inheritdoc}
	 */
	public function isCorrupted () {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

}

class SplPriorityQueue implements Iterator, Traversable, Countable {
	const EXTR_BOTH = 3;
	const EXTR_PRIORITY = 2;
	const EXTR_DATA = 1;


	/**
	 * {@inheritdoc}
	 * @param mixed $priority1
	 * @param mixed $priority2
	 */
	public function compare (mixed $priority1 = null, mixed $priority2 = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 * @param mixed $priority
	 */
	public function insert (mixed $value = null, mixed $priority = null) {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function setExtractFlags (int $flags) {}

	/**
	 * {@inheritdoc}
	 */
	public function top () {}

	/**
	 * {@inheritdoc}
	 */
	public function extract () {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function isEmpty () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function recoverFromCorruption () {}

	/**
	 * {@inheritdoc}
	 */
	public function isCorrupted () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtractFlags () {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

}

class SplFixedArray implements IteratorAggregate, Traversable, ArrayAccess, Countable, JsonSerializable {

	/**
	 * {@inheritdoc}
	 * @param int $size [optional]
	 */
	public function __construct (int $size = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function toArray () {}

	/**
	 * {@inheritdoc}
	 * @param array $array
	 * @param bool $preserveKeys [optional]
	 */
	public static function fromArray (array $array, bool $preserveKeys = true) {}

	/**
	 * {@inheritdoc}
	 */
	public function getSize () {}

	/**
	 * {@inheritdoc}
	 * @param int $size
	 */
	public function setSize (int $size) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 */
	public function offsetExists ($index = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 */
	public function offsetGet ($index = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 * @param mixed $value
	 */
	public function offsetSet ($index = null, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 */
	public function offsetUnset ($index = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): Iterator {}

	/**
	 * {@inheritdoc}
	 */
	public function jsonSerialize (): array {}

}

interface SplObserver  {

	/**
	 * {@inheritdoc}
	 * @param SplSubject $subject
	 */
	abstract public function update (SplSubject $subject);

}

interface SplSubject  {

	/**
	 * {@inheritdoc}
	 * @param SplObserver $observer
	 */
	abstract public function attach (SplObserver $observer);

	/**
	 * {@inheritdoc}
	 * @param SplObserver $observer
	 */
	abstract public function detach (SplObserver $observer);

	/**
	 * {@inheritdoc}
	 */
	abstract public function notify ();

}

class SplObjectStorage implements Countable, Iterator, Traversable, Serializable, ArrayAccess {

	/**
	 * {@inheritdoc}
	 * @param object $object
	 * @param mixed $info [optional]
	 */
	public function attach (object $object, mixed $info = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param object $object
	 */
	public function detach (object $object) {}

	/**
	 * {@inheritdoc}
	 * @param object $object
	 */
	public function contains (object $object) {}

	/**
	 * {@inheritdoc}
	 * @param SplObjectStorage $storage
	 */
	public function addAll (SplObjectStorage $storage) {}

	/**
	 * {@inheritdoc}
	 * @param SplObjectStorage $storage
	 */
	public function removeAll (SplObjectStorage $storage) {}

	/**
	 * {@inheritdoc}
	 * @param SplObjectStorage $storage
	 */
	public function removeAllExcept (SplObjectStorage $storage) {}

	/**
	 * {@inheritdoc}
	 */
	public function getInfo () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $info
	 */
	public function setInfo (mixed $info = null) {}

	/**
	 * {@inheritdoc}
	 * @param int $mode [optional]
	 */
	public function count (int $mode = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function unserialize (string $data) {}

	/**
	 * {@inheritdoc}
	 */
	public function serialize () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $object
	 */
	public function offsetExists ($object = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $object
	 */
	public function offsetGet ($object = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $object
	 * @param mixed $info [optional]
	 */
	public function offsetSet ($object = null, mixed $info = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $object
	 */
	public function offsetUnset ($object = null) {}

	/**
	 * {@inheritdoc}
	 * @param object $object
	 */
	public function getHash (object $object) {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize () {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data) {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

}

class MultipleIterator implements Iterator, Traversable {
	const MIT_NEED_ANY = 0;
	const MIT_NEED_ALL = 1;
	const MIT_KEYS_NUMERIC = 0;
	const MIT_KEYS_ASSOC = 2;


	/**
	 * {@inheritdoc}
	 * @param int $flags [optional]
	 */
	public function __construct (int $flags = 1) {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags () {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function setFlags (int $flags) {}

	/**
	 * {@inheritdoc}
	 * @param Iterator $iterator
	 * @param string|int|null $info [optional]
	 */
	public function attachIterator (Iterator $iterator, string|int|null $info = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param Iterator $iterator
	 */
	public function detachIterator (Iterator $iterator) {}

	/**
	 * {@inheritdoc}
	 * @param Iterator $iterator
	 */
	public function containsIterator (Iterator $iterator) {}

	/**
	 * {@inheritdoc}
	 */
	public function countIterators () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

}

/**
 * {@inheritdoc}
 * @param mixed $object_or_class
 * @param bool $autoload [optional]
 */
function class_implements ($object_or_class = null, bool $autoload = true): array|false {}

/**
 * {@inheritdoc}
 * @param mixed $object_or_class
 * @param bool $autoload [optional]
 */
function class_parents ($object_or_class = null, bool $autoload = true): array|false {}

/**
 * {@inheritdoc}
 * @param mixed $object_or_class
 * @param bool $autoload [optional]
 */
function class_uses ($object_or_class = null, bool $autoload = true): array|false {}

/**
 * {@inheritdoc}
 * @param string $class
 * @param string|null $file_extensions [optional]
 */
function spl_autoload (string $class, ?string $file_extensions = NULL): void {}

/**
 * {@inheritdoc}
 * @param string $class
 */
function spl_autoload_call (string $class): void {}

/**
 * {@inheritdoc}
 * @param string|null $file_extensions [optional]
 */
function spl_autoload_extensions (?string $file_extensions = NULL): string {}

/**
 * {@inheritdoc}
 */
function spl_autoload_functions (): array {}

/**
 * {@inheritdoc}
 * @param callable|null $callback [optional]
 * @param bool $throw [optional]
 * @param bool $prepend [optional]
 */
function spl_autoload_register (?callable $callback = NULL, bool $throw = true, bool $prepend = false): bool {}

/**
 * {@inheritdoc}
 * @param callable $callback
 */
function spl_autoload_unregister (callable $callback): bool {}

/**
 * {@inheritdoc}
 */
function spl_classes (): array {}

/**
 * {@inheritdoc}
 * @param object $object
 */
function spl_object_hash (object $object): string {}

/**
 * {@inheritdoc}
 * @param object $object
 */
function spl_object_id (object $object): int {}

/**
 * {@inheritdoc}
 * @param Traversable $iterator
 * @param callable $callback
 * @param array|null $args [optional]
 */
function iterator_apply (Traversable $iterator, callable $callback, ?array $args = NULL): int {}

/**
 * {@inheritdoc}
 * @param Traversable|array $iterator
 */
function iterator_count (Traversable|array $iterator): int {}

/**
 * {@inheritdoc}
 * @param Traversable|array $iterator
 * @param bool $preserve_keys [optional]
 */
function iterator_to_array (Traversable|array $iterator, bool $preserve_keys = true): array {}

// End of SPL v.8.3.0
