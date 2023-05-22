<?php

// Start of SPL v.8.2.6

/**
 * Exception that represents error in the program logic. This kind of
 * exception should lead directly to a fix in your code.
 * @link http://www.php.net/manual/en/class.logicexception.php
 */
class LogicException extends Exception implements Throwable, Stringable {

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
 * Exception thrown if a callback refers to an undefined function or if some
 * arguments are missing.
 * @link http://www.php.net/manual/en/class.badfunctioncallexception.php
 */
class BadFunctionCallException extends LogicException implements Stringable, Throwable {

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
 * Exception thrown if a callback refers to an undefined method or if some
 * arguments are missing.
 * @link http://www.php.net/manual/en/class.badmethodcallexception.php
 */
class BadMethodCallException extends BadFunctionCallException implements Throwable, Stringable {

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
 * Exception thrown if a value does not adhere to a defined valid data domain.
 * @link http://www.php.net/manual/en/class.domainexception.php
 */
class DomainException extends LogicException implements Stringable, Throwable {

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
 * Exception thrown if an argument is not of the expected type.
 * @link http://www.php.net/manual/en/class.invalidargumentexception.php
 */
class InvalidArgumentException extends LogicException implements Stringable, Throwable {

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
 * Exception thrown if a length is invalid.
 * @link http://www.php.net/manual/en/class.lengthexception.php
 */
class LengthException extends LogicException implements Stringable, Throwable {

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
 * Exception thrown when an illegal index was requested. This represents
 * errors that should be detected at compile time.
 * @link http://www.php.net/manual/en/class.outofrangeexception.php
 */
class OutOfRangeException extends LogicException implements Stringable, Throwable {

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
 * Exception thrown if an error which can only be found on runtime occurs.
 * @link http://www.php.net/manual/en/class.runtimeexception.php
 */
class RuntimeException extends Exception implements Throwable, Stringable {

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
 * Exception thrown if a value is not a valid key. This represents errors
 * that cannot be detected at compile time.
 * @link http://www.php.net/manual/en/class.outofboundsexception.php
 */
class OutOfBoundsException extends RuntimeException implements Stringable, Throwable {

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
 * Exception thrown when adding an element to a full container.
 * @link http://www.php.net/manual/en/class.overflowexception.php
 */
class OverflowException extends RuntimeException implements Stringable, Throwable {

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
 * Exception thrown to indicate range errors during program execution.
 * Normally this means there was an arithmetic error other than
 * under/overflow. This is the runtime version of
 * DomainException.
 * @link http://www.php.net/manual/en/class.rangeexception.php
 */
class RangeException extends RuntimeException implements Stringable, Throwable {

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
 * Exception thrown when performing an invalid operation on an empty
 * container, such as removing an element.
 * @link http://www.php.net/manual/en/class.underflowexception.php
 */
class UnderflowException extends RuntimeException implements Stringable, Throwable {

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
 * Exception thrown if a value does not match with a set of values. Typically
 * this happens when a function calls another function and expects the return
 * value to be of a certain type or value not including arithmetic or buffer
 * related errors.
 * @link http://www.php.net/manual/en/class.unexpectedvalueexception.php
 */
class UnexpectedValueException extends RuntimeException implements Stringable, Throwable {

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
 * Classes implementing RecursiveIterator can be used to iterate
 * over iterators recursively.
 * @link http://www.php.net/manual/en/class.recursiveiterator.php
 */
interface RecursiveIterator extends Iterator, Traversable {

	/**
	 * Returns if an iterator can be created for the current entry
	 * @link http://www.php.net/manual/en/recursiveiterator.haschildren.php
	 * @return bool Returns true if the current entry can be iterated over, otherwise returns false.
	 */
	abstract public function hasChildren (): bool;

	/**
	 * Returns an iterator for the current entry
	 * @link http://www.php.net/manual/en/recursiveiterator.getchildren.php
	 * @return RecursiveIterator|null Returns an iterator for the current entry if it exists, or null otherwise.
	 */
	abstract public function getChildren (): ?RecursiveIterator;

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
 * Classes implementing OuterIterator can be used to iterate
 * over iterators.
 * @link http://www.php.net/manual/en/class.outeriterator.php
 */
interface OuterIterator extends Iterator, Traversable {

	/**
	 * Returns the inner iterator for the current entry
	 * @link http://www.php.net/manual/en/outeriterator.getinneriterator.php
	 * @return Iterator|null Returns the inner iterator for the current entry if it exists, or null otherwise.
	 */
	abstract public function getInnerIterator (): ?Iterator;

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
 * Can be used to iterate through recursive iterators.
 * @link http://www.php.net/manual/en/class.recursiveiteratoriterator.php
 */
class RecursiveIteratorIterator implements OuterIterator, Traversable, Iterator {
	const LEAVES_ONLY = 0;
	const SELF_FIRST = 1;
	const CHILD_FIRST = 2;
	const CATCH_GET_CHILD = 16;


	/**
	 * Construct a RecursiveIteratorIterator
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.construct.php
	 * @param Traversable $iterator 
	 * @param int $mode [optional] 
	 * @param int $flags [optional] 
	 * @return Traversable 
	 */
	public function __construct (Traversable $iterator, int $mode = \RecursiveIteratorIterator::LEAVES_ONLY, int $flags = null): Traversable {}

	/**
	 * Rewind the iterator to the first element of the top level inner iterator
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Check whether the current position is valid
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.valid.php
	 * @return bool true if the current position is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Access the current key
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.key.php
	 * @return mixed The current key.
	 */
	public function key (): mixed {}

	/**
	 * Access the current element value
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.current.php
	 * @return mixed The current elements value.
	 */
	public function current (): mixed {}

	/**
	 * Move forward to the next element
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Get the current depth of the recursive iteration
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.getdepth.php
	 * @return int The current depth of the recursive iteration.
	 */
	public function getDepth (): int {}

	/**
	 * The current active sub iterator
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.getsubiterator.php
	 * @param int|null $level [optional] 
	 * @return RecursiveIterator|null The current active sub iterator on success; null on failure.
	 */
	public function getSubIterator (?int $level = null): ?RecursiveIterator {}

	/**
	 * Get inner iterator
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.getinneriterator.php
	 * @return RecursiveIterator The current active sub iterator.
	 */
	public function getInnerIterator (): RecursiveIterator {}

	/**
	 * Begin Iteration
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.beginiteration.php
	 * @return void No value is returned.
	 */
	public function beginIteration (): void {}

	/**
	 * End Iteration
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.enditeration.php
	 * @return void No value is returned.
	 */
	public function endIteration (): void {}

	/**
	 * Has children
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.callhaschildren.php
	 * @return bool Returns whether the element has children.
	 */
	public function callHasChildren (): bool {}

	/**
	 * Get children
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.callgetchildren.php
	 * @return RecursiveIterator|null A RecursiveIterator on success, or null on failure.
	 */
	public function callGetChildren (): ?RecursiveIterator {}

	/**
	 * Begin children
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.beginchildren.php
	 * @return void No value is returned.
	 */
	public function beginChildren (): void {}

	/**
	 * End children
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.endchildren.php
	 * @return void No value is returned.
	 */
	public function endChildren (): void {}

	/**
	 * Next element
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.nextelement.php
	 * @return void No value is returned.
	 */
	public function nextElement (): void {}

	/**
	 * Set max depth
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.setmaxdepth.php
	 * @param int $maxDepth [optional] 
	 * @return void No value is returned.
	 */
	public function setMaxDepth (int $maxDepth = -1): void {}

	/**
	 * Get max depth
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.getmaxdepth.php
	 * @return int|false The maximum accepted depth, or false if any depth is allowed.
	 */
	public function getMaxDepth (): int|false {}

}

/**
 * This iterator wrapper allows the conversion of anything that is
 * Traversable into an Iterator.
 * It is important to understand that most classes that do not implement
 * Iterators have reasons as most likely they do not allow the full
 * Iterator feature set. If so, techniques should be provided to prevent
 * misuse, otherwise expect exceptions or fatal errors.
 * @link http://www.php.net/manual/en/class.iteratoriterator.php
 */
class IteratorIterator implements OuterIterator, Traversable, Iterator {

	/**
	 * Create an iterator from anything that is traversable
	 * @link http://www.php.net/manual/en/iteratoriterator.construct.php
	 * @param Traversable $iterator 
	 * @param string|null $class [optional] 
	 * @return Traversable 
	 */
	public function __construct (Traversable $iterator, ?string $class = null): Traversable {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/iteratoriterator.getinneriterator.php
	 * @return Iterator|null The inner iterator as passed to IteratorIterator::__construct, or null when there is no inner iterator.
	 */
	public function getInnerIterator (): ?Iterator {}

	/**
	 * Rewind to the first element
	 * @link http://www.php.net/manual/en/iteratoriterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Checks if the iterator is valid
	 * @link http://www.php.net/manual/en/iteratoriterator.valid.php
	 * @return bool Returns true if the iterator is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Get the key of the current element
	 * @link http://www.php.net/manual/en/iteratoriterator.key.php
	 * @return mixed The key of the current element.
	 */
	public function key (): mixed {}

	/**
	 * Get the current value
	 * @link http://www.php.net/manual/en/iteratoriterator.current.php
	 * @return mixed The value of the current element.
	 */
	public function current (): mixed {}

	/**
	 * Forward to the next element
	 * @link http://www.php.net/manual/en/iteratoriterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

}

/**
 * This abstract iterator filters out unwanted values. This class should be extended to
 * implement custom iterator filters. The FilterIterator::accept
 * must be implemented in the subclass.
 * @link http://www.php.net/manual/en/class.filteriterator.php
 */
abstract class FilterIterator extends IteratorIterator implements Iterator, Traversable, OuterIterator {

	/**
	 * Check whether the current element of the iterator is acceptable
	 * @link http://www.php.net/manual/en/filteriterator.accept.php
	 * @return bool true if the current element is acceptable, otherwise false.
	 */
	abstract public function accept (): bool;

	/**
	 * Construct a filterIterator
	 * @link http://www.php.net/manual/en/filteriterator.construct.php
	 * @param Iterator $iterator 
	 * @return Iterator 
	 */
	public function __construct (Iterator $iterator): Iterator {}

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/filteriterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/filteriterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/iteratoriterator.getinneriterator.php
	 * @return Iterator|null The inner iterator as passed to IteratorIterator::__construct, or null when there is no inner iterator.
	 */
	public function getInnerIterator (): ?Iterator {}

	/**
	 * Checks if the iterator is valid
	 * @link http://www.php.net/manual/en/iteratoriterator.valid.php
	 * @return bool Returns true if the iterator is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Get the key of the current element
	 * @link http://www.php.net/manual/en/iteratoriterator.key.php
	 * @return mixed The key of the current element.
	 */
	public function key (): mixed {}

	/**
	 * Get the current value
	 * @link http://www.php.net/manual/en/iteratoriterator.current.php
	 * @return mixed The value of the current element.
	 */
	public function current (): mixed {}

}

/**
 * This abstract iterator filters out unwanted values for a RecursiveIterator.
 * This class should be extended to implement custom filters. 
 * The RecursiveFilterIterator::accept must be implemented in the subclass.
 * @link http://www.php.net/manual/en/class.recursivefilteriterator.php
 */
abstract class RecursiveFilterIterator extends FilterIterator implements OuterIterator, Traversable, Iterator, RecursiveIterator {

	/**
	 * Create a RecursiveFilterIterator from a RecursiveIterator
	 * @link http://www.php.net/manual/en/recursivefilteriterator.construct.php
	 * @param RecursiveIterator $iterator 
	 * @return RecursiveIterator 
	 */
	public function __construct (RecursiveIterator $iterator): RecursiveIterator {}

	/**
	 * Check whether the inner iterator's current element has children
	 * @link http://www.php.net/manual/en/recursivefilteriterator.haschildren.php
	 * @return bool true if the inner iterator has children, otherwise false
	 */
	public function hasChildren (): bool {}

	/**
	 * Return the inner iterator's children contained in a RecursiveFilterIterator
	 * @link http://www.php.net/manual/en/recursivefilteriterator.getchildren.php
	 * @return RecursiveFilterIterator|null Returns a RecursiveFilterIterator containing the inner iterator's children.
	 */
	public function getChildren (): ?RecursiveFilterIterator {}

	/**
	 * Check whether the current element of the iterator is acceptable
	 * @link http://www.php.net/manual/en/filteriterator.accept.php
	 * @return bool true if the current element is acceptable, otherwise false.
	 */
	abstract public function accept (): bool;

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/filteriterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/filteriterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/iteratoriterator.getinneriterator.php
	 * @return Iterator|null The inner iterator as passed to IteratorIterator::__construct, or null when there is no inner iterator.
	 */
	public function getInnerIterator (): ?Iterator {}

	/**
	 * Checks if the iterator is valid
	 * @link http://www.php.net/manual/en/iteratoriterator.valid.php
	 * @return bool Returns true if the iterator is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Get the key of the current element
	 * @link http://www.php.net/manual/en/iteratoriterator.key.php
	 * @return mixed The key of the current element.
	 */
	public function key (): mixed {}

	/**
	 * Get the current value
	 * @link http://www.php.net/manual/en/iteratoriterator.current.php
	 * @return mixed The value of the current element.
	 */
	public function current (): mixed {}

}

/**
 * @link http://www.php.net/manual/en/class.callbackfilteriterator.php
 */
class CallbackFilterIterator extends FilterIterator implements OuterIterator, Traversable, Iterator {

	/**
	 * Create a filtered iterator from another iterator
	 * @link http://www.php.net/manual/en/callbackfilteriterator.construct.php
	 * @param Iterator $iterator The iterator to be filtered.
	 * @param callable $callback The callback, which should return true to accept the current item
	 * or false otherwise.
	 * See Examples.
	 * <p>May be any valid callable value.</p>
	 * @return Iterator 
	 */
	public function __construct (Iterator $iterator, callable $callback): Iterator {}

	/**
	 * Calls the callback with the current value, the current key and the inner iterator as arguments
	 * @link http://www.php.net/manual/en/callbackfilteriterator.accept.php
	 * @return bool Returns true to accept the current item, or false otherwise.
	 */
	public function accept (): bool {}

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/filteriterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/filteriterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/iteratoriterator.getinneriterator.php
	 * @return Iterator|null The inner iterator as passed to IteratorIterator::__construct, or null when there is no inner iterator.
	 */
	public function getInnerIterator (): ?Iterator {}

	/**
	 * Checks if the iterator is valid
	 * @link http://www.php.net/manual/en/iteratoriterator.valid.php
	 * @return bool Returns true if the iterator is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Get the key of the current element
	 * @link http://www.php.net/manual/en/iteratoriterator.key.php
	 * @return mixed The key of the current element.
	 */
	public function key (): mixed {}

	/**
	 * Get the current value
	 * @link http://www.php.net/manual/en/iteratoriterator.current.php
	 * @return mixed The value of the current element.
	 */
	public function current (): mixed {}

}

/**
 * @link http://www.php.net/manual/en/class.recursivecallbackfilteriterator.php
 */
class RecursiveCallbackFilterIterator extends CallbackFilterIterator implements Iterator, Traversable, OuterIterator, RecursiveIterator {

	/**
	 * Create a RecursiveCallbackFilterIterator from a RecursiveIterator
	 * @link http://www.php.net/manual/en/recursivecallbackfilteriterator.construct.php
	 * @param RecursiveIterator $iterator The recursive iterator to be filtered.
	 * @param callable $callback The callback, which should return true to accept the current item
	 * or false otherwise.
	 * See Examples.
	 * <p>May be any valid callable value.</p>
	 * @return RecursiveIterator 
	 */
	public function __construct (RecursiveIterator $iterator, callable $callback): RecursiveIterator {}

	/**
	 * Check whether the inner iterator's current element has children
	 * @link http://www.php.net/manual/en/recursivecallbackfilteriterator.haschildren.php
	 * @return bool Returns true if the current element has children, false otherwise.
	 */
	public function hasChildren (): bool {}

	/**
	 * Return the inner iterator's children contained in a RecursiveCallbackFilterIterator
	 * @link http://www.php.net/manual/en/recursivecallbackfilteriterator.getchildren.php
	 * @return RecursiveCallbackFilterIterator Returns a RecursiveCallbackFilterIterator containing
	 * the children.
	 */
	public function getChildren (): RecursiveCallbackFilterIterator {}

	/**
	 * Calls the callback with the current value, the current key and the inner iterator as arguments
	 * @link http://www.php.net/manual/en/callbackfilteriterator.accept.php
	 * @return bool Returns true to accept the current item, or false otherwise.
	 */
	public function accept (): bool {}

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/filteriterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/filteriterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/iteratoriterator.getinneriterator.php
	 * @return Iterator|null The inner iterator as passed to IteratorIterator::__construct, or null when there is no inner iterator.
	 */
	public function getInnerIterator (): ?Iterator {}

	/**
	 * Checks if the iterator is valid
	 * @link http://www.php.net/manual/en/iteratoriterator.valid.php
	 * @return bool Returns true if the iterator is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Get the key of the current element
	 * @link http://www.php.net/manual/en/iteratoriterator.key.php
	 * @return mixed The key of the current element.
	 */
	public function key (): mixed {}

	/**
	 * Get the current value
	 * @link http://www.php.net/manual/en/iteratoriterator.current.php
	 * @return mixed The value of the current element.
	 */
	public function current (): mixed {}

}

/**
 * This extended FilterIterator allows a recursive
 * iteration using RecursiveIteratorIterator that only
 * shows those elements which have children.
 * @link http://www.php.net/manual/en/class.parentiterator.php
 */
class ParentIterator extends RecursiveFilterIterator implements RecursiveIterator, Iterator, Traversable, OuterIterator {

	/**
	 * Constructs a ParentIterator
	 * @link http://www.php.net/manual/en/parentiterator.construct.php
	 * @param RecursiveIterator $iterator 
	 * @return RecursiveIterator 
	 */
	public function __construct (RecursiveIterator $iterator): RecursiveIterator {}

	/**
	 * Determines acceptability
	 * @link http://www.php.net/manual/en/parentiterator.accept.php
	 * @return bool true if the current element is acceptable, otherwise false.
	 */
	public function accept (): bool {}

	/**
	 * Check whether the inner iterator's current element has children
	 * @link http://www.php.net/manual/en/recursivefilteriterator.haschildren.php
	 * @return bool true if the inner iterator has children, otherwise false
	 */
	public function hasChildren (): bool {}

	/**
	 * Return the inner iterator's children contained in a RecursiveFilterIterator
	 * @link http://www.php.net/manual/en/recursivefilteriterator.getchildren.php
	 * @return RecursiveFilterIterator|null Returns a RecursiveFilterIterator containing the inner iterator's children.
	 */
	public function getChildren (): ?RecursiveFilterIterator {}

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/filteriterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/filteriterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/iteratoriterator.getinneriterator.php
	 * @return Iterator|null The inner iterator as passed to IteratorIterator::__construct, or null when there is no inner iterator.
	 */
	public function getInnerIterator (): ?Iterator {}

	/**
	 * Checks if the iterator is valid
	 * @link http://www.php.net/manual/en/iteratoriterator.valid.php
	 * @return bool Returns true if the iterator is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Get the key of the current element
	 * @link http://www.php.net/manual/en/iteratoriterator.key.php
	 * @return mixed The key of the current element.
	 */
	public function key (): mixed {}

	/**
	 * Get the current value
	 * @link http://www.php.net/manual/en/iteratoriterator.current.php
	 * @return mixed The value of the current element.
	 */
	public function current (): mixed {}

}

/**
 * The Seekable iterator.
 * @link http://www.php.net/manual/en/class.seekableiterator.php
 */
interface SeekableIterator extends Iterator, Traversable {

	/**
	 * Seeks to a position
	 * @link http://www.php.net/manual/en/seekableiterator.seek.php
	 * @param int $offset 
	 * @return void No value is returned.
	 */
	abstract public function seek (int $offset): void;

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
 * The LimitIterator class allows iteration over 
 * a limited subset of items in an Iterator.
 * @link http://www.php.net/manual/en/class.limititerator.php
 */
class LimitIterator extends IteratorIterator implements Iterator, Traversable, OuterIterator {

	/**
	 * Construct a LimitIterator
	 * @link http://www.php.net/manual/en/limititerator.construct.php
	 * @param Iterator $iterator 
	 * @param int $offset [optional] 
	 * @param int $limit [optional] 
	 * @return Iterator 
	 */
	public function __construct (Iterator $iterator, int $offset = null, int $limit = -1): Iterator {}

	/**
	 * Rewind the iterator to the specified starting offset
	 * @link http://www.php.net/manual/en/limititerator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Check whether the current element is valid
	 * @link http://www.php.net/manual/en/limititerator.valid.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function valid (): bool {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/limititerator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Seek to the given position
	 * @link http://www.php.net/manual/en/limititerator.seek.php
	 * @param int $offset 
	 * @return int Returns the offset position after seeking.
	 */
	public function seek (int $offset): int {}

	/**
	 * Return the current position
	 * @link http://www.php.net/manual/en/limititerator.getposition.php
	 * @return int The current position.
	 */
	public function getPosition (): int {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/iteratoriterator.getinneriterator.php
	 * @return Iterator|null The inner iterator as passed to IteratorIterator::__construct, or null when there is no inner iterator.
	 */
	public function getInnerIterator (): ?Iterator {}

	/**
	 * Get the key of the current element
	 * @link http://www.php.net/manual/en/iteratoriterator.key.php
	 * @return mixed The key of the current element.
	 */
	public function key (): mixed {}

	/**
	 * Get the current value
	 * @link http://www.php.net/manual/en/iteratoriterator.current.php
	 * @return mixed The value of the current element.
	 */
	public function current (): mixed {}

}

/**
 * This object supports cached iteration over another iterator.
 * @link http://www.php.net/manual/en/class.cachingiterator.php
 */
class CachingIterator extends IteratorIterator implements Stringable, Iterator, Traversable, OuterIterator, ArrayAccess, Countable {
	/**
	 * Convert every element to string.
	const CALL_TOSTRING = 1;
	/**
	 * Don't throw exception in accessing children.
	const CATCH_GET_CHILD = 16;
	/**
	 * Use key for conversion to
	 * string.
	const TOSTRING_USE_KEY = 2;
	/**
	 * Use current for
	 * conversion to string.
	const TOSTRING_USE_CURRENT = 4;
	/**
	 * Use inner
	 * for conversion to string.
	const TOSTRING_USE_INNER = 8;
	/**
	 * Cache all read data.
	const FULL_CACHE = 256;


	/**
	 * Construct a new CachingIterator object for the iterator
	 * @link http://www.php.net/manual/en/cachingiterator.construct.php
	 * @param Iterator $iterator 
	 * @param int $flags [optional] 
	 * @return Iterator 
	 */
	public function __construct (Iterator $iterator, int $flags = \CachingIterator::CALL_TOSTRING): Iterator {}

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/cachingiterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Check whether the current element is valid
	 * @link http://www.php.net/manual/en/cachingiterator.valid.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function valid (): bool {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/cachingiterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Check whether the inner iterator has a valid next element
	 * @link http://www.php.net/manual/en/cachingiterator.hasnext.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasNext (): bool {}

	/**
	 * Return the string representation of the current element
	 * @link http://www.php.net/manual/en/cachingiterator.tostring.php
	 * @return string The string representation of the current element.
	 */
	public function __toString (): string {}

	/**
	 * Get flags used
	 * @link http://www.php.net/manual/en/cachingiterator.getflags.php
	 * @return int Description...
	 */
	public function getFlags (): int {}

	/**
	 * The setFlags purpose
	 * @link http://www.php.net/manual/en/cachingiterator.setflags.php
	 * @param int $flags 
	 * @return void No value is returned.
	 */
	public function setFlags (int $flags): void {}

	/**
	 * The offsetGet purpose
	 * @link http://www.php.net/manual/en/cachingiterator.offsetget.php
	 * @param string $key 
	 * @return mixed Description...
	 */
	public function offsetGet (string $key): mixed {}

	/**
	 * The offsetSet purpose
	 * @link http://www.php.net/manual/en/cachingiterator.offsetset.php
	 * @param string $key 
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function offsetSet (string $key, mixed $value): void {}

	/**
	 * The offsetUnset purpose
	 * @link http://www.php.net/manual/en/cachingiterator.offsetunset.php
	 * @param string $key 
	 * @return void No value is returned.
	 */
	public function offsetUnset (string $key): void {}

	/**
	 * The offsetExists purpose
	 * @link http://www.php.net/manual/en/cachingiterator.offsetexists.php
	 * @param string $key 
	 * @return bool Returns true if an entry referenced by the offset exists, false otherwise.
	 */
	public function offsetExists (string $key): bool {}

	/**
	 * Retrieve the contents of the cache
	 * @link http://www.php.net/manual/en/cachingiterator.getcache.php
	 * @return array An array containing the cache items.
	 */
	public function getCache (): array {}

	/**
	 * The number of elements in the iterator
	 * @link http://www.php.net/manual/en/cachingiterator.count.php
	 * @return int The count of the elements iterated over.
	 */
	public function count (): int {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/iteratoriterator.getinneriterator.php
	 * @return Iterator|null The inner iterator as passed to IteratorIterator::__construct, or null when there is no inner iterator.
	 */
	public function getInnerIterator (): ?Iterator {}

	/**
	 * Get the key of the current element
	 * @link http://www.php.net/manual/en/iteratoriterator.key.php
	 * @return mixed The key of the current element.
	 */
	public function key (): mixed {}

	/**
	 * Get the current value
	 * @link http://www.php.net/manual/en/iteratoriterator.current.php
	 * @return mixed The value of the current element.
	 */
	public function current (): mixed {}

}

/**
 * ...
 * @link http://www.php.net/manual/en/class.recursivecachingiterator.php
 */
class RecursiveCachingIterator extends CachingIterator implements Countable, ArrayAccess, OuterIterator, Traversable, Iterator, Stringable, RecursiveIterator {
	const CALL_TOSTRING = 1;
	const CATCH_GET_CHILD = 16;
	const TOSTRING_USE_KEY = 2;
	const TOSTRING_USE_CURRENT = 4;
	const TOSTRING_USE_INNER = 8;
	const FULL_CACHE = 256;


	/**
	 * Construct
	 * @link http://www.php.net/manual/en/recursivecachingiterator.construct.php
	 * @param Iterator $iterator 
	 * @param int $flags [optional] 
	 * @return Iterator 
	 */
	public function __construct (Iterator $iterator, int $flags = \RecursiveCachingIterator::CALL_TOSTRING): Iterator {}

	/**
	 * Check whether the current element of the inner iterator has children
	 * @link http://www.php.net/manual/en/recursivecachingiterator.haschildren.php
	 * @return bool true if the inner iterator has children, otherwise false
	 */
	public function hasChildren (): bool {}

	/**
	 * Return the inner iterator's children as a RecursiveCachingIterator
	 * @link http://www.php.net/manual/en/recursivecachingiterator.getchildren.php
	 * @return RecursiveCachingIterator|null The inner iterator's children, as a RecursiveCachingIterator; or null if there is no children.
	 */
	public function getChildren (): ?RecursiveCachingIterator {}

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/cachingiterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Check whether the current element is valid
	 * @link http://www.php.net/manual/en/cachingiterator.valid.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function valid (): bool {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/cachingiterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Check whether the inner iterator has a valid next element
	 * @link http://www.php.net/manual/en/cachingiterator.hasnext.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasNext (): bool {}

	/**
	 * Return the string representation of the current element
	 * @link http://www.php.net/manual/en/cachingiterator.tostring.php
	 * @return string The string representation of the current element.
	 */
	public function __toString (): string {}

	/**
	 * Get flags used
	 * @link http://www.php.net/manual/en/cachingiterator.getflags.php
	 * @return int Description...
	 */
	public function getFlags (): int {}

	/**
	 * The setFlags purpose
	 * @link http://www.php.net/manual/en/cachingiterator.setflags.php
	 * @param int $flags 
	 * @return void No value is returned.
	 */
	public function setFlags (int $flags): void {}

	/**
	 * The offsetGet purpose
	 * @link http://www.php.net/manual/en/cachingiterator.offsetget.php
	 * @param string $key 
	 * @return mixed Description...
	 */
	public function offsetGet (string $key): mixed {}

	/**
	 * The offsetSet purpose
	 * @link http://www.php.net/manual/en/cachingiterator.offsetset.php
	 * @param string $key 
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function offsetSet (string $key, mixed $value): void {}

	/**
	 * The offsetUnset purpose
	 * @link http://www.php.net/manual/en/cachingiterator.offsetunset.php
	 * @param string $key 
	 * @return void No value is returned.
	 */
	public function offsetUnset (string $key): void {}

	/**
	 * The offsetExists purpose
	 * @link http://www.php.net/manual/en/cachingiterator.offsetexists.php
	 * @param string $key 
	 * @return bool Returns true if an entry referenced by the offset exists, false otherwise.
	 */
	public function offsetExists (string $key): bool {}

	/**
	 * Retrieve the contents of the cache
	 * @link http://www.php.net/manual/en/cachingiterator.getcache.php
	 * @return array An array containing the cache items.
	 */
	public function getCache (): array {}

	/**
	 * The number of elements in the iterator
	 * @link http://www.php.net/manual/en/cachingiterator.count.php
	 * @return int The count of the elements iterated over.
	 */
	public function count (): int {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/iteratoriterator.getinneriterator.php
	 * @return Iterator|null The inner iterator as passed to IteratorIterator::__construct, or null when there is no inner iterator.
	 */
	public function getInnerIterator (): ?Iterator {}

	/**
	 * Get the key of the current element
	 * @link http://www.php.net/manual/en/iteratoriterator.key.php
	 * @return mixed The key of the current element.
	 */
	public function key (): mixed {}

	/**
	 * Get the current value
	 * @link http://www.php.net/manual/en/iteratoriterator.current.php
	 * @return mixed The value of the current element.
	 */
	public function current (): mixed {}

}

/**
 * This iterator ignores rewind operations. This allows processing an iterator in multiple partial foreach loops.
 * @link http://www.php.net/manual/en/class.norewinditerator.php
 */
class NoRewindIterator extends IteratorIterator implements Iterator, Traversable, OuterIterator {

	/**
	 * Construct a NoRewindIterator
	 * @link http://www.php.net/manual/en/norewinditerator.construct.php
	 * @param Iterator $iterator 
	 * @return Iterator 
	 */
	public function __construct (Iterator $iterator): Iterator {}

	/**
	 * Prevents the rewind operation on the inner iterator
	 * @link http://www.php.net/manual/en/norewinditerator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Validates the iterator
	 * @link http://www.php.net/manual/en/norewinditerator.valid.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function valid (): bool {}

	/**
	 * Get the current key
	 * @link http://www.php.net/manual/en/norewinditerator.key.php
	 * @return mixed The current key.
	 */
	public function key (): mixed {}

	/**
	 * Get the current value
	 * @link http://www.php.net/manual/en/norewinditerator.current.php
	 * @return mixed The current value.
	 */
	public function current (): mixed {}

	/**
	 * Forward to the next element
	 * @link http://www.php.net/manual/en/norewinditerator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/iteratoriterator.getinneriterator.php
	 * @return Iterator|null The inner iterator as passed to IteratorIterator::__construct, or null when there is no inner iterator.
	 */
	public function getInnerIterator (): ?Iterator {}

}

/**
 * An Iterator that iterates over several iterators one after the other.
 * @link http://www.php.net/manual/en/class.appenditerator.php
 */
class AppendIterator extends IteratorIterator implements Iterator, Traversable, OuterIterator {

	/**
	 * Constructs an AppendIterator
	 * @link http://www.php.net/manual/en/appenditerator.construct.php
	 */
	public function __construct () {}

	/**
	 * Appends an iterator
	 * @link http://www.php.net/manual/en/appenditerator.append.php
	 * @param Iterator $iterator 
	 * @return void No value is returned.
	 */
	public function append (Iterator $iterator): void {}

	/**
	 * Rewinds the Iterator
	 * @link http://www.php.net/manual/en/appenditerator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Checks validity of the current element
	 * @link http://www.php.net/manual/en/appenditerator.valid.php
	 * @return bool Returns true if the current iteration is valid, false otherwise.
	 */
	public function valid (): bool {}

	/**
	 * Gets the current value
	 * @link http://www.php.net/manual/en/appenditerator.current.php
	 * @return mixed The current value if it is valid or null otherwise.
	 */
	public function current (): mixed {}

	/**
	 * Moves to the next element
	 * @link http://www.php.net/manual/en/appenditerator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Gets an index of iterators
	 * @link http://www.php.net/manual/en/appenditerator.getiteratorindex.php
	 * @return int|null Returns the zero-based, integer index of the current inner iterator if it exists, or null otherwise.
	 */
	public function getIteratorIndex (): ?int {}

	/**
	 * Gets the ArrayIterator
	 * @link http://www.php.net/manual/en/appenditerator.getarrayiterator.php
	 * @return ArrayIterator Returns an ArrayIterator containing
	 * the appended iterators.
	 */
	public function getArrayIterator (): ArrayIterator {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/iteratoriterator.getinneriterator.php
	 * @return Iterator|null The inner iterator as passed to IteratorIterator::__construct, or null when there is no inner iterator.
	 */
	public function getInnerIterator (): ?Iterator {}

	/**
	 * Get the key of the current element
	 * @link http://www.php.net/manual/en/iteratoriterator.key.php
	 * @return mixed The key of the current element.
	 */
	public function key (): mixed {}

}

/**
 * The InfiniteIterator allows one to
 * infinitely iterate over an iterator without having to manually
 * rewind the iterator upon reaching its end.
 * @link http://www.php.net/manual/en/class.infiniteiterator.php
 */
class InfiniteIterator extends IteratorIterator implements Iterator, Traversable, OuterIterator {

	/**
	 * Constructs an InfiniteIterator
	 * @link http://www.php.net/manual/en/infiniteiterator.construct.php
	 * @param Iterator $iterator 
	 * @return Iterator 
	 */
	public function __construct (Iterator $iterator): Iterator {}

	/**
	 * Moves the inner Iterator forward or rewinds it
	 * @link http://www.php.net/manual/en/infiniteiterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/iteratoriterator.getinneriterator.php
	 * @return Iterator|null The inner iterator as passed to IteratorIterator::__construct, or null when there is no inner iterator.
	 */
	public function getInnerIterator (): ?Iterator {}

	/**
	 * Rewind to the first element
	 * @link http://www.php.net/manual/en/iteratoriterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Checks if the iterator is valid
	 * @link http://www.php.net/manual/en/iteratoriterator.valid.php
	 * @return bool Returns true if the iterator is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Get the key of the current element
	 * @link http://www.php.net/manual/en/iteratoriterator.key.php
	 * @return mixed The key of the current element.
	 */
	public function key (): mixed {}

	/**
	 * Get the current value
	 * @link http://www.php.net/manual/en/iteratoriterator.current.php
	 * @return mixed The value of the current element.
	 */
	public function current (): mixed {}

}

/**
 * This iterator can be used to filter another iterator based on a regular expression.
 * @link http://www.php.net/manual/en/class.regexiterator.php
 */
class RegexIterator extends FilterIterator implements OuterIterator, Traversable, Iterator {
	/**
	 * Special flag: Match the entry key instead of the entry value.
	const USE_KEY = 1;
	/**
	 * Inverts the return value of RegexIterator::accept.
	const INVERT_MATCH = 2;
	/**
	 * Only execute match (filter) for the current entry 
	 * (see preg_match).
	const MATCH = 0;
	/**
	 * Return the first match for the current entry 
	 * (see preg_match).
	const GET_MATCH = 1;
	/**
	 * Return all matches for the current entry 
	 * (see preg_match_all).
	const ALL_MATCHES = 2;
	/**
	 * Returns the split values for the current entry (see preg_split).
	const SPLIT = 3;
	/**
	 * Replace the current entry 
	 * (see preg_replace; Not fully implemented yet)
	const REPLACE = 4;


	public ?string $replacement;

	/**
	 * Create a new RegexIterator
	 * @link http://www.php.net/manual/en/regexiterator.construct.php
	 * @param Iterator $iterator 
	 * @param string $pattern 
	 * @param int $mode [optional] 
	 * @param int $flags [optional] 
	 * @param int $pregFlags [optional] 
	 * @return Iterator 
	 */
	public function __construct (Iterator $iterator, string $pattern, int $mode = \RegexIterator::MATCH, int $flags = null, int $pregFlags = null): Iterator {}

	/**
	 * Get accept status
	 * @link http://www.php.net/manual/en/regexiterator.accept.php
	 * @return bool true if a match, false otherwise.
	 */
	public function accept (): bool {}

	/**
	 * Returns operation mode
	 * @link http://www.php.net/manual/en/regexiterator.getmode.php
	 * @return int Returns the operation mode.
	 */
	public function getMode (): int {}

	/**
	 * Sets the operation mode
	 * @link http://www.php.net/manual/en/regexiterator.setmode.php
	 * @param int $mode 
	 * @return void No value is returned.
	 */
	public function setMode (int $mode): void {}

	/**
	 * Get flags
	 * @link http://www.php.net/manual/en/regexiterator.getflags.php
	 * @return int Returns the set flags.
	 */
	public function getFlags (): int {}

	/**
	 * Sets the flags
	 * @link http://www.php.net/manual/en/regexiterator.setflags.php
	 * @param int $flags 
	 * @return void No value is returned.
	 */
	public function setFlags (int $flags): void {}

	/**
	 * Returns current regular expression
	 * @link http://www.php.net/manual/en/regexiterator.getregex.php
	 * @return string 
	 */
	public function getRegex (): string {}

	/**
	 * Returns the regular expression flags
	 * @link http://www.php.net/manual/en/regexiterator.getpregflags.php
	 * @return int Returns a bitmask of the regular expression flags.
	 */
	public function getPregFlags (): int {}

	/**
	 * Sets the regular expression flags
	 * @link http://www.php.net/manual/en/regexiterator.setpregflags.php
	 * @param int $pregFlags 
	 * @return void No value is returned.
	 */
	public function setPregFlags (int $pregFlags): void {}

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/filteriterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/filteriterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/iteratoriterator.getinneriterator.php
	 * @return Iterator|null The inner iterator as passed to IteratorIterator::__construct, or null when there is no inner iterator.
	 */
	public function getInnerIterator (): ?Iterator {}

	/**
	 * Checks if the iterator is valid
	 * @link http://www.php.net/manual/en/iteratoriterator.valid.php
	 * @return bool Returns true if the iterator is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Get the key of the current element
	 * @link http://www.php.net/manual/en/iteratoriterator.key.php
	 * @return mixed The key of the current element.
	 */
	public function key (): mixed {}

	/**
	 * Get the current value
	 * @link http://www.php.net/manual/en/iteratoriterator.current.php
	 * @return mixed The value of the current element.
	 */
	public function current (): mixed {}

}

/**
 * This recursive iterator can filter another recursive iterator via a regular expression.
 * @link http://www.php.net/manual/en/class.recursiveregexiterator.php
 */
class RecursiveRegexIterator extends RegexIterator implements Iterator, Traversable, OuterIterator, RecursiveIterator {
	const USE_KEY = 1;
	const INVERT_MATCH = 2;
	const MATCH = 0;
	const GET_MATCH = 1;
	const ALL_MATCHES = 2;
	const SPLIT = 3;
	const REPLACE = 4;


	/**
	 * Creates a new RecursiveRegexIterator
	 * @link http://www.php.net/manual/en/recursiveregexiterator.construct.php
	 * @param RecursiveIterator $iterator 
	 * @param string $pattern 
	 * @param int $mode [optional] 
	 * @param int $flags [optional] 
	 * @param int $pregFlags [optional] 
	 * @return RecursiveIterator 
	 */
	public function __construct (RecursiveIterator $iterator, string $pattern, int $mode = \RecursiveRegexIterator::MATCH, int $flags = null, int $pregFlags = null): RecursiveIterator {}

	/**
	 * {@inheritdoc}
	 */
	public function accept () {}

	/**
	 * Returns whether an iterator can be obtained for the current entry
	 * @link http://www.php.net/manual/en/recursiveregexiterator.haschildren.php
	 * @return bool Returns true if an iterator can be obtained for the current entry, otherwise returns false.
	 */
	public function hasChildren (): bool {}

	/**
	 * Returns an iterator for the current entry
	 * @link http://www.php.net/manual/en/recursiveregexiterator.getchildren.php
	 * @return RecursiveRegexIterator An iterator for the current entry, if it can be iterated over by the inner iterator.
	 */
	public function getChildren (): RecursiveRegexIterator {}

	/**
	 * Returns operation mode
	 * @link http://www.php.net/manual/en/regexiterator.getmode.php
	 * @return int Returns the operation mode.
	 */
	public function getMode (): int {}

	/**
	 * Sets the operation mode
	 * @link http://www.php.net/manual/en/regexiterator.setmode.php
	 * @param int $mode 
	 * @return void No value is returned.
	 */
	public function setMode (int $mode): void {}

	/**
	 * Get flags
	 * @link http://www.php.net/manual/en/regexiterator.getflags.php
	 * @return int Returns the set flags.
	 */
	public function getFlags (): int {}

	/**
	 * Sets the flags
	 * @link http://www.php.net/manual/en/regexiterator.setflags.php
	 * @param int $flags 
	 * @return void No value is returned.
	 */
	public function setFlags (int $flags): void {}

	/**
	 * Returns current regular expression
	 * @link http://www.php.net/manual/en/regexiterator.getregex.php
	 * @return string 
	 */
	public function getRegex (): string {}

	/**
	 * Returns the regular expression flags
	 * @link http://www.php.net/manual/en/regexiterator.getpregflags.php
	 * @return int Returns a bitmask of the regular expression flags.
	 */
	public function getPregFlags (): int {}

	/**
	 * Sets the regular expression flags
	 * @link http://www.php.net/manual/en/regexiterator.setpregflags.php
	 * @param int $pregFlags 
	 * @return void No value is returned.
	 */
	public function setPregFlags (int $pregFlags): void {}

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/filteriterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/filteriterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/iteratoriterator.getinneriterator.php
	 * @return Iterator|null The inner iterator as passed to IteratorIterator::__construct, or null when there is no inner iterator.
	 */
	public function getInnerIterator (): ?Iterator {}

	/**
	 * Checks if the iterator is valid
	 * @link http://www.php.net/manual/en/iteratoriterator.valid.php
	 * @return bool Returns true if the iterator is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Get the key of the current element
	 * @link http://www.php.net/manual/en/iteratoriterator.key.php
	 * @return mixed The key of the current element.
	 */
	public function key (): mixed {}

	/**
	 * Get the current value
	 * @link http://www.php.net/manual/en/iteratoriterator.current.php
	 * @return mixed The value of the current element.
	 */
	public function current (): mixed {}

}

/**
 * The EmptyIterator class for an empty iterator.
 * @link http://www.php.net/manual/en/class.emptyiterator.php
 */
class EmptyIterator implements Iterator, Traversable {

	/**
	 * The current() method
	 * @link http://www.php.net/manual/en/emptyiterator.current.php
	 * @return never No value is returned.
	 */
	public function current (): never {}

	/**
	 * The next() method
	 * @link http://www.php.net/manual/en/emptyiterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * The key() method
	 * @link http://www.php.net/manual/en/emptyiterator.key.php
	 * @return never No value is returned.
	 */
	public function key (): never {}

	/**
	 * The valid() method
	 * @link http://www.php.net/manual/en/emptyiterator.valid.php
	 * @return false false
	 */
	public function valid (): false {}

	/**
	 * The rewind() method
	 * @link http://www.php.net/manual/en/emptyiterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

}

/**
 * Allows iterating over a RecursiveIterator to generate an ASCII graphic tree.
 * @link http://www.php.net/manual/en/class.recursivetreeiterator.php
 */
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
	 * Construct a RecursiveTreeIterator
	 * @link http://www.php.net/manual/en/recursivetreeiterator.construct.php
	 * @param RecursiveIterator|IteratorAggregate $iterator 
	 * @param int $flags [optional] 
	 * @param int $cachingIteratorFlags [optional] 
	 * @param int $mode [optional] 
	 * @return RecursiveIterator|IteratorAggregate 
	 */
	public function __construct (RecursiveIterator|IteratorAggregate $iterator, int $flags = \RecursiveTreeIterator::BYPASS_KEY, int $cachingIteratorFlags = \CachingIterator::CATCH_GET_CHILD, int $mode = \RecursiveTreeIterator::SELF_FIRST): RecursiveIterator|IteratorAggregate {}

	/**
	 * Get the key of the current element
	 * @link http://www.php.net/manual/en/recursivetreeiterator.key.php
	 * @return mixed Returns the current key prefixed and postfixed.
	 */
	public function key (): mixed {}

	/**
	 * Get current element
	 * @link http://www.php.net/manual/en/recursivetreeiterator.current.php
	 * @return mixed Returns the current element prefixed and postfixed.
	 */
	public function current (): mixed {}

	/**
	 * Get the prefix
	 * @link http://www.php.net/manual/en/recursivetreeiterator.getprefix.php
	 * @return string Returns the string to place in front of current element
	 */
	public function getPrefix (): string {}

	/**
	 * Set postfix
	 * @link http://www.php.net/manual/en/recursivetreeiterator.setpostfix.php
	 * @param string $postfix 
	 * @return void No value is returned.
	 */
	public function setPostfix (string $postfix): void {}

	/**
	 * Set a part of the prefix
	 * @link http://www.php.net/manual/en/recursivetreeiterator.setprefixpart.php
	 * @param int $part 
	 * @param string $value 
	 * @return void No value is returned.
	 */
	public function setPrefixPart (int $part, string $value): void {}

	/**
	 * Get current entry
	 * @link http://www.php.net/manual/en/recursivetreeiterator.getentry.php
	 * @return string Returns the part of the tree built for the current element.
	 */
	public function getEntry (): string {}

	/**
	 * Get the postfix
	 * @link http://www.php.net/manual/en/recursivetreeiterator.getpostfix.php
	 * @return string Returns the string to place after the current element.
	 */
	public function getPostfix (): string {}

	/**
	 * Rewind the iterator to the first element of the top level inner iterator
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Check whether the current position is valid
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.valid.php
	 * @return bool true if the current position is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Move forward to the next element
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Get the current depth of the recursive iteration
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.getdepth.php
	 * @return int The current depth of the recursive iteration.
	 */
	public function getDepth (): int {}

	/**
	 * The current active sub iterator
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.getsubiterator.php
	 * @param int|null $level [optional] 
	 * @return RecursiveIterator|null The current active sub iterator on success; null on failure.
	 */
	public function getSubIterator (?int $level = null): ?RecursiveIterator {}

	/**
	 * Get inner iterator
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.getinneriterator.php
	 * @return RecursiveIterator The current active sub iterator.
	 */
	public function getInnerIterator (): RecursiveIterator {}

	/**
	 * Begin Iteration
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.beginiteration.php
	 * @return void No value is returned.
	 */
	public function beginIteration (): void {}

	/**
	 * End Iteration
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.enditeration.php
	 * @return void No value is returned.
	 */
	public function endIteration (): void {}

	/**
	 * Has children
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.callhaschildren.php
	 * @return bool Returns whether the element has children.
	 */
	public function callHasChildren (): bool {}

	/**
	 * Get children
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.callgetchildren.php
	 * @return RecursiveIterator|null A RecursiveIterator on success, or null on failure.
	 */
	public function callGetChildren (): ?RecursiveIterator {}

	/**
	 * Begin children
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.beginchildren.php
	 * @return void No value is returned.
	 */
	public function beginChildren (): void {}

	/**
	 * End children
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.endchildren.php
	 * @return void No value is returned.
	 */
	public function endChildren (): void {}

	/**
	 * Next element
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.nextelement.php
	 * @return void No value is returned.
	 */
	public function nextElement (): void {}

	/**
	 * Set max depth
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.setmaxdepth.php
	 * @param int $maxDepth [optional] 
	 * @return void No value is returned.
	 */
	public function setMaxDepth (int $maxDepth = -1): void {}

	/**
	 * Get max depth
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.getmaxdepth.php
	 * @return int|false The maximum accepted depth, or false if any depth is allowed.
	 */
	public function getMaxDepth (): int|false {}

}

/**
 * This class allows objects to work as arrays.
 * @link http://www.php.net/manual/en/class.arrayobject.php
 */
class ArrayObject implements IteratorAggregate, Traversable, ArrayAccess, Serializable, Countable {
	/**
	 * Properties of the object have their normal functionality when accessed as list (var_dump, foreach, etc.).
	const STD_PROP_LIST = 1;
	/**
	 * Entries can be accessed as properties (read and write).
	const ARRAY_AS_PROPS = 2;


	/**
	 * Construct a new array object
	 * @link http://www.php.net/manual/en/arrayobject.construct.php
	 * @param array|object $array [optional] 
	 * @param int $flags [optional] 
	 * @param string $iteratorClass [optional] 
	 * @return array|object 
	 */
	public function __construct (array|object $array = '[]', int $flags = null, string $iteratorClass = 'ArrayIterator::class'): array|object {}

	/**
	 * Returns whether the requested index exists
	 * @link http://www.php.net/manual/en/arrayobject.offsetexists.php
	 * @param mixed $key 
	 * @return bool true if the requested index exists, otherwise false
	 */
	public function offsetExists (mixed $key): bool {}

	/**
	 * Returns the value at the specified index
	 * @link http://www.php.net/manual/en/arrayobject.offsetget.php
	 * @param mixed $key 
	 * @return mixed The value at the specified index or null.
	 */
	public function offsetGet (mixed $key): mixed {}

	/**
	 * Sets the value at the specified index to newval
	 * @link http://www.php.net/manual/en/arrayobject.offsetset.php
	 * @param mixed $key 
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function offsetSet (mixed $key, mixed $value): void {}

	/**
	 * Unsets the value at the specified index
	 * @link http://www.php.net/manual/en/arrayobject.offsetunset.php
	 * @param mixed $key 
	 * @return void No value is returned.
	 */
	public function offsetUnset (mixed $key): void {}

	/**
	 * Appends the value
	 * @link http://www.php.net/manual/en/arrayobject.append.php
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function append (mixed $value): void {}

	/**
	 * Creates a copy of the ArrayObject
	 * @link http://www.php.net/manual/en/arrayobject.getarraycopy.php
	 * @return array Returns a copy of the array. When the ArrayObject refers to an object,
	 * an array of the properties of that object will be returned.
	 */
	public function getArrayCopy (): array {}

	/**
	 * Get the number of public properties in the ArrayObject
	 * @link http://www.php.net/manual/en/arrayobject.count.php
	 * @return int The number of public properties in the ArrayObject.
	 * <p>When the ArrayObject is constructed from an array all properties are public.</p>
	 */
	public function count (): int {}

	/**
	 * Gets the behavior flags
	 * @link http://www.php.net/manual/en/arrayobject.getflags.php
	 * @return int Returns the behavior flags of the ArrayObject.
	 */
	public function getFlags (): int {}

	/**
	 * Sets the behavior flags
	 * @link http://www.php.net/manual/en/arrayobject.setflags.php
	 * @param int $flags 
	 * @return void No value is returned.
	 */
	public function setFlags (int $flags): void {}

	/**
	 * Sort the entries by value
	 * @link http://www.php.net/manual/en/arrayobject.asort.php
	 * @param int $flags [optional] The optional second parameter flags
	 * may be used to modify the sorting behavior using these values:
	 * <p>Sorting type flags:
	 * <p>
	 * <br>
	 * SORT_REGULAR - compare items normally;
	 * the details are described in the comparison operators section
	 * <br>
	 * SORT_NUMERIC - compare items numerically
	 * <br>
	 * SORT_STRING - compare items as strings
	 * <br>
	 * SORT_LOCALE_STRING - compare items as
	 * strings, based on the current locale. It uses the locale,
	 * which can be changed using setlocale
	 * <br>
	 * SORT_NATURAL - compare items as strings
	 * using "natural ordering" like natsort
	 * <br>
	 * SORT_FLAG_CASE - can be combined
	 * (bitwise OR) with
	 * SORT_STRING or
	 * SORT_NATURAL to sort strings case-insensitively
	 * </p></p>
	 * @return true Always returns true.
	 */
	public function asort (int $flags = SORT_REGULAR): true {}

	/**
	 * Sort the entries by key
	 * @link http://www.php.net/manual/en/arrayobject.ksort.php
	 * @param int $flags [optional] The optional second parameter flags
	 * may be used to modify the sorting behavior using these values:
	 * <p>Sorting type flags:
	 * <p>
	 * <br>
	 * SORT_REGULAR - compare items normally;
	 * the details are described in the comparison operators section
	 * <br>
	 * SORT_NUMERIC - compare items numerically
	 * <br>
	 * SORT_STRING - compare items as strings
	 * <br>
	 * SORT_LOCALE_STRING - compare items as
	 * strings, based on the current locale. It uses the locale,
	 * which can be changed using setlocale
	 * <br>
	 * SORT_NATURAL - compare items as strings
	 * using "natural ordering" like natsort
	 * <br>
	 * SORT_FLAG_CASE - can be combined
	 * (bitwise OR) with
	 * SORT_STRING or
	 * SORT_NATURAL to sort strings case-insensitively
	 * </p></p>
	 * @return true Always returns true.
	 */
	public function ksort (int $flags = SORT_REGULAR): true {}

	/**
	 * Sort the entries with a user-defined comparison function and maintain key association
	 * @link http://www.php.net/manual/en/arrayobject.uasort.php
	 * @param callable $callback 
	 * @return true Always returns true.
	 */
	public function uasort (callable $callback): true {}

	/**
	 * Sort the entries by keys using a user-defined comparison function
	 * @link http://www.php.net/manual/en/arrayobject.uksort.php
	 * @param callable $callback 
	 * @return true Always returns true.
	 */
	public function uksort (callable $callback): true {}

	/**
	 * Sort entries using a "natural order" algorithm
	 * @link http://www.php.net/manual/en/arrayobject.natsort.php
	 * @return true No value is returned.
	 */
	public function natsort (): true {}

	/**
	 * Sort an array using a case insensitive "natural order" algorithm
	 * @link http://www.php.net/manual/en/arrayobject.natcasesort.php
	 * @return true No value is returned.
	 */
	public function natcasesort (): true {}

	/**
	 * Unserialize an ArrayObject
	 * @link http://www.php.net/manual/en/arrayobject.unserialize.php
	 * @param string $data 
	 * @return void No value is returned.
	 */
	public function unserialize (string $data): void {}

	/**
	 * Serialize an ArrayObject
	 * @link http://www.php.net/manual/en/arrayobject.serialize.php
	 * @return string The serialized representation of the ArrayObject.
	 */
	public function serialize (): string {}

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
	 * Create a new iterator from an ArrayObject instance
	 * @link http://www.php.net/manual/en/arrayobject.getiterator.php
	 * @return Iterator An iterator from an ArrayObject.
	 */
	public function getIterator (): Iterator {}

	/**
	 * Exchange the array for another one
	 * @link http://www.php.net/manual/en/arrayobject.exchangearray.php
	 * @param array|object $array 
	 * @return array Returns the old array.
	 */
	public function exchangeArray (array|object $array): array {}

	/**
	 * Sets the iterator classname for the ArrayObject
	 * @link http://www.php.net/manual/en/arrayobject.setiteratorclass.php
	 * @param string $iteratorClass 
	 * @return void No value is returned.
	 */
	public function setIteratorClass (string $iteratorClass): void {}

	/**
	 * Gets the iterator classname for the ArrayObject
	 * @link http://www.php.net/manual/en/arrayobject.getiteratorclass.php
	 * @return string Returns the iterator class name that is used to iterate over this object.
	 */
	public function getIteratorClass (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

}

/**
 * This iterator allows to unset and modify values and keys while iterating
 * over Arrays and Objects.
 * <p>When you want to iterate over the same array multiple times you need to
 * instantiate ArrayObject and let it create ArrayIterator instances that
 * refer to it either by using foreach or by calling its getIterator()
 * method manually.</p>
 * @link http://www.php.net/manual/en/class.arrayiterator.php
 */
class ArrayIterator implements SeekableIterator, Traversable, Iterator, ArrayAccess, Serializable, Countable {
	/**
	 * Properties of the object have their normal functionality when accessed as list (var_dump, foreach, etc.).
	const STD_PROP_LIST = 1;
	/**
	 * Entries can be accessed as properties (read and write).
	const ARRAY_AS_PROPS = 2;


	/**
	 * Construct an ArrayIterator
	 * @link http://www.php.net/manual/en/arrayiterator.construct.php
	 * @param array|object $array [optional] 
	 * @param int $flags [optional] 
	 * @return array|object 
	 */
	public function __construct (array|object $array = '[]', int $flags = null): array|object {}

	/**
	 * Check if offset exists
	 * @link http://www.php.net/manual/en/arrayiterator.offsetexists.php
	 * @param mixed $key 
	 * @return bool true if the offset exists, otherwise false
	 */
	public function offsetExists (mixed $key): bool {}

	/**
	 * Get value for an offset
	 * @link http://www.php.net/manual/en/arrayiterator.offsetget.php
	 * @param mixed $key 
	 * @return mixed The value at offset key.
	 */
	public function offsetGet (mixed $key): mixed {}

	/**
	 * Set value for an offset
	 * @link http://www.php.net/manual/en/arrayiterator.offsetset.php
	 * @param mixed $key 
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function offsetSet (mixed $key, mixed $value): void {}

	/**
	 * Unset value for an offset
	 * @link http://www.php.net/manual/en/arrayiterator.offsetunset.php
	 * @param mixed $key 
	 * @return void No value is returned.
	 */
	public function offsetUnset (mixed $key): void {}

	/**
	 * Append an element
	 * @link http://www.php.net/manual/en/arrayiterator.append.php
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function append (mixed $value): void {}

	/**
	 * Get array copy
	 * @link http://www.php.net/manual/en/arrayiterator.getarraycopy.php
	 * @return array A copy of the array, or array of public properties
	 * if ArrayIterator refers to an object.
	 */
	public function getArrayCopy (): array {}

	/**
	 * Count elements
	 * @link http://www.php.net/manual/en/arrayiterator.count.php
	 * @return int The number of elements or public properties in the associated
	 * array or object, respectively.
	 */
	public function count (): int {}

	/**
	 * Get behavior flags
	 * @link http://www.php.net/manual/en/arrayiterator.getflags.php
	 * @return int Returns the behavior flags of the ArrayIterator.
	 */
	public function getFlags (): int {}

	/**
	 * Set behaviour flags
	 * @link http://www.php.net/manual/en/arrayiterator.setflags.php
	 * @param int $flags 
	 * @return void No value is returned.
	 */
	public function setFlags (int $flags): void {}

	/**
	 * Sort entries by values
	 * @link http://www.php.net/manual/en/arrayiterator.asort.php
	 * @param int $flags [optional] The optional second parameter flags
	 * may be used to modify the sorting behavior using these values:
	 * <p>Sorting type flags:
	 * <p>
	 * <br>
	 * SORT_REGULAR - compare items normally;
	 * the details are described in the comparison operators section
	 * <br>
	 * SORT_NUMERIC - compare items numerically
	 * <br>
	 * SORT_STRING - compare items as strings
	 * <br>
	 * SORT_LOCALE_STRING - compare items as
	 * strings, based on the current locale. It uses the locale,
	 * which can be changed using setlocale
	 * <br>
	 * SORT_NATURAL - compare items as strings
	 * using "natural ordering" like natsort
	 * <br>
	 * SORT_FLAG_CASE - can be combined
	 * (bitwise OR) with
	 * SORT_STRING or
	 * SORT_NATURAL to sort strings case-insensitively
	 * </p></p>
	 * @return true Always returns true.
	 */
	public function asort (int $flags = SORT_REGULAR): true {}

	/**
	 * Sort entries by keys
	 * @link http://www.php.net/manual/en/arrayiterator.ksort.php
	 * @param int $flags [optional] The optional second parameter flags
	 * may be used to modify the sorting behavior using these values:
	 * <p>Sorting type flags:
	 * <p>
	 * <br>
	 * SORT_REGULAR - compare items normally;
	 * the details are described in the comparison operators section
	 * <br>
	 * SORT_NUMERIC - compare items numerically
	 * <br>
	 * SORT_STRING - compare items as strings
	 * <br>
	 * SORT_LOCALE_STRING - compare items as
	 * strings, based on the current locale. It uses the locale,
	 * which can be changed using setlocale
	 * <br>
	 * SORT_NATURAL - compare items as strings
	 * using "natural ordering" like natsort
	 * <br>
	 * SORT_FLAG_CASE - can be combined
	 * (bitwise OR) with
	 * SORT_STRING or
	 * SORT_NATURAL to sort strings case-insensitively
	 * </p></p>
	 * @return true Always returns true.
	 */
	public function ksort (int $flags = SORT_REGULAR): true {}

	/**
	 * Sort with a user-defined comparison function and maintain index association
	 * @link http://www.php.net/manual/en/arrayiterator.uasort.php
	 * @param callable $callback 
	 * @return true Always returns true.
	 */
	public function uasort (callable $callback): true {}

	/**
	 * Sort by keys using a user-defined comparison function
	 * @link http://www.php.net/manual/en/arrayiterator.uksort.php
	 * @param callable $callback 
	 * @return true Always returns true.
	 */
	public function uksort (callable $callback): true {}

	/**
	 * Sort entries naturally
	 * @link http://www.php.net/manual/en/arrayiterator.natsort.php
	 * @return true Always returns true.
	 */
	public function natsort (): true {}

	/**
	 * Sort entries naturally, case insensitive
	 * @link http://www.php.net/manual/en/arrayiterator.natcasesort.php
	 * @return true Always returns true.
	 */
	public function natcasesort (): true {}

	/**
	 * Unserialize
	 * @link http://www.php.net/manual/en/arrayiterator.unserialize.php
	 * @param string $data 
	 * @return void No value is returned.
	 */
	public function unserialize (string $data): void {}

	/**
	 * Serialize
	 * @link http://www.php.net/manual/en/arrayiterator.serialize.php
	 * @return string The serialized ArrayIterator.
	 */
	public function serialize (): string {}

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
	 * Rewind array back to the start
	 * @link http://www.php.net/manual/en/arrayiterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Return current array entry
	 * @link http://www.php.net/manual/en/arrayiterator.current.php
	 * @return mixed The current array entry.
	 */
	public function current (): mixed {}

	/**
	 * Return current array key
	 * @link http://www.php.net/manual/en/arrayiterator.key.php
	 * @return string|int|null The current array key.
	 */
	public function key (): string|int|null {}

	/**
	 * Move to next entry
	 * @link http://www.php.net/manual/en/arrayiterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Check whether array contains more entries
	 * @link http://www.php.net/manual/en/arrayiterator.valid.php
	 * @return bool Returns true if the iterator is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Seek to position
	 * @link http://www.php.net/manual/en/arrayiterator.seek.php
	 * @param int $offset 
	 * @return void No value is returned.
	 */
	public function seek (int $offset): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

}

/**
 * This iterator allows to unset and modify values and keys while iterating over Arrays and Objects
 * in the same way as the ArrayIterator. Additionally it is possible to iterate
 * over the current iterator entry.
 * @link http://www.php.net/manual/en/class.recursivearrayiterator.php
 */
class RecursiveArrayIterator extends ArrayIterator implements Countable, Serializable, ArrayAccess, Iterator, Traversable, SeekableIterator, RecursiveIterator {
	const STD_PROP_LIST = 1;
	const ARRAY_AS_PROPS = 2;
	/**
	 * Treat only arrays (not objects) as having children for recursive iteration.
	const CHILD_ARRAYS_ONLY = 4;


	/**
	 * Returns whether current entry is an array or an object
	 * @link http://www.php.net/manual/en/recursivearrayiterator.haschildren.php
	 * @return bool Returns true if the current entry is an array or an object,
	 * otherwise false is returned.
	 */
	public function hasChildren (): bool {}

	/**
	 * Returns an iterator for the current entry if it is an array or an object
	 * @link http://www.php.net/manual/en/recursivearrayiterator.getchildren.php
	 * @return RecursiveArrayIterator|null An iterator for the current entry, if it is an array or object; or null on failure.
	 */
	public function getChildren (): ?RecursiveArrayIterator {}

	/**
	 * Construct an ArrayIterator
	 * @link http://www.php.net/manual/en/arrayiterator.construct.php
	 * @param array|object $array [optional] 
	 * @param int $flags [optional] 
	 * @return array|object 
	 */
	public function __construct (array|object $array = '[]', int $flags = null): array|object {}

	/**
	 * Check if offset exists
	 * @link http://www.php.net/manual/en/arrayiterator.offsetexists.php
	 * @param mixed $key 
	 * @return bool true if the offset exists, otherwise false
	 */
	public function offsetExists (mixed $key): bool {}

	/**
	 * Get value for an offset
	 * @link http://www.php.net/manual/en/arrayiterator.offsetget.php
	 * @param mixed $key 
	 * @return mixed The value at offset key.
	 */
	public function offsetGet (mixed $key): mixed {}

	/**
	 * Set value for an offset
	 * @link http://www.php.net/manual/en/arrayiterator.offsetset.php
	 * @param mixed $key 
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function offsetSet (mixed $key, mixed $value): void {}

	/**
	 * Unset value for an offset
	 * @link http://www.php.net/manual/en/arrayiterator.offsetunset.php
	 * @param mixed $key 
	 * @return void No value is returned.
	 */
	public function offsetUnset (mixed $key): void {}

	/**
	 * Append an element
	 * @link http://www.php.net/manual/en/arrayiterator.append.php
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function append (mixed $value): void {}

	/**
	 * Get array copy
	 * @link http://www.php.net/manual/en/arrayiterator.getarraycopy.php
	 * @return array A copy of the array, or array of public properties
	 * if ArrayIterator refers to an object.
	 */
	public function getArrayCopy (): array {}

	/**
	 * Count elements
	 * @link http://www.php.net/manual/en/arrayiterator.count.php
	 * @return int The number of elements or public properties in the associated
	 * array or object, respectively.
	 */
	public function count (): int {}

	/**
	 * Get behavior flags
	 * @link http://www.php.net/manual/en/arrayiterator.getflags.php
	 * @return int Returns the behavior flags of the ArrayIterator.
	 */
	public function getFlags (): int {}

	/**
	 * Set behaviour flags
	 * @link http://www.php.net/manual/en/arrayiterator.setflags.php
	 * @param int $flags 
	 * @return void No value is returned.
	 */
	public function setFlags (int $flags): void {}

	/**
	 * Sort entries by values
	 * @link http://www.php.net/manual/en/arrayiterator.asort.php
	 * @param int $flags [optional] The optional second parameter flags
	 * may be used to modify the sorting behavior using these values:
	 * <p>Sorting type flags:
	 * <p>
	 * <br>
	 * SORT_REGULAR - compare items normally;
	 * the details are described in the comparison operators section
	 * <br>
	 * SORT_NUMERIC - compare items numerically
	 * <br>
	 * SORT_STRING - compare items as strings
	 * <br>
	 * SORT_LOCALE_STRING - compare items as
	 * strings, based on the current locale. It uses the locale,
	 * which can be changed using setlocale
	 * <br>
	 * SORT_NATURAL - compare items as strings
	 * using "natural ordering" like natsort
	 * <br>
	 * SORT_FLAG_CASE - can be combined
	 * (bitwise OR) with
	 * SORT_STRING or
	 * SORT_NATURAL to sort strings case-insensitively
	 * </p></p>
	 * @return true Always returns true.
	 */
	public function asort (int $flags = SORT_REGULAR): true {}

	/**
	 * Sort entries by keys
	 * @link http://www.php.net/manual/en/arrayiterator.ksort.php
	 * @param int $flags [optional] The optional second parameter flags
	 * may be used to modify the sorting behavior using these values:
	 * <p>Sorting type flags:
	 * <p>
	 * <br>
	 * SORT_REGULAR - compare items normally;
	 * the details are described in the comparison operators section
	 * <br>
	 * SORT_NUMERIC - compare items numerically
	 * <br>
	 * SORT_STRING - compare items as strings
	 * <br>
	 * SORT_LOCALE_STRING - compare items as
	 * strings, based on the current locale. It uses the locale,
	 * which can be changed using setlocale
	 * <br>
	 * SORT_NATURAL - compare items as strings
	 * using "natural ordering" like natsort
	 * <br>
	 * SORT_FLAG_CASE - can be combined
	 * (bitwise OR) with
	 * SORT_STRING or
	 * SORT_NATURAL to sort strings case-insensitively
	 * </p></p>
	 * @return true Always returns true.
	 */
	public function ksort (int $flags = SORT_REGULAR): true {}

	/**
	 * Sort with a user-defined comparison function and maintain index association
	 * @link http://www.php.net/manual/en/arrayiterator.uasort.php
	 * @param callable $callback 
	 * @return true Always returns true.
	 */
	public function uasort (callable $callback): true {}

	/**
	 * Sort by keys using a user-defined comparison function
	 * @link http://www.php.net/manual/en/arrayiterator.uksort.php
	 * @param callable $callback 
	 * @return true Always returns true.
	 */
	public function uksort (callable $callback): true {}

	/**
	 * Sort entries naturally
	 * @link http://www.php.net/manual/en/arrayiterator.natsort.php
	 * @return true Always returns true.
	 */
	public function natsort (): true {}

	/**
	 * Sort entries naturally, case insensitive
	 * @link http://www.php.net/manual/en/arrayiterator.natcasesort.php
	 * @return true Always returns true.
	 */
	public function natcasesort (): true {}

	/**
	 * Unserialize
	 * @link http://www.php.net/manual/en/arrayiterator.unserialize.php
	 * @param string $data 
	 * @return void No value is returned.
	 */
	public function unserialize (string $data): void {}

	/**
	 * Serialize
	 * @link http://www.php.net/manual/en/arrayiterator.serialize.php
	 * @return string The serialized ArrayIterator.
	 */
	public function serialize (): string {}

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
	 * Rewind array back to the start
	 * @link http://www.php.net/manual/en/arrayiterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Return current array entry
	 * @link http://www.php.net/manual/en/arrayiterator.current.php
	 * @return mixed The current array entry.
	 */
	public function current (): mixed {}

	/**
	 * Return current array key
	 * @link http://www.php.net/manual/en/arrayiterator.key.php
	 * @return string|int|null The current array key.
	 */
	public function key (): string|int|null {}

	/**
	 * Move to next entry
	 * @link http://www.php.net/manual/en/arrayiterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Check whether array contains more entries
	 * @link http://www.php.net/manual/en/arrayiterator.valid.php
	 * @return bool Returns true if the iterator is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Seek to position
	 * @link http://www.php.net/manual/en/arrayiterator.seek.php
	 * @param int $offset 
	 * @return void No value is returned.
	 */
	public function seek (int $offset): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

}

/**
 * The SplFileInfo class offers a high-level object-oriented interface to
 * information for an individual file.
 * @link http://www.php.net/manual/en/class.splfileinfo.php
 */
class SplFileInfo implements Stringable {

	/**
	 * Construct a new SplFileInfo object
	 * @link http://www.php.net/manual/en/splfileinfo.construct.php
	 * @param string $filename 
	 * @return string 
	 */
	public function __construct (string $filename): string {}

	/**
	 * Gets the path without filename
	 * @link http://www.php.net/manual/en/splfileinfo.getpath.php
	 * @return string Returns the path to the file.
	 */
	public function getPath (): string {}

	/**
	 * Gets the filename
	 * @link http://www.php.net/manual/en/splfileinfo.getfilename.php
	 * @return string The filename.
	 */
	public function getFilename (): string {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/splfileinfo.getextension.php
	 * @return string Returns a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension (): string {}

	/**
	 * Gets the base name of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getbasename.php
	 * @param string $suffix [optional] 
	 * @return string Returns the base name without path information.
	 */
	public function getBasename (string $suffix = '""'): string {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname (): string {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return int|false Returns the file permissions on success, or false on failure.
	 */
	public function getPerms (): int|false {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return int|false Returns the inode number for the filesystem object on success, or false on failure.
	 */
	public function getInode (): int|false {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return int|false The filesize in bytes on success, or false on failure.
	 */
	public function getSize (): int|false {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return int|false The owner id in numerical format on success, or false on failure.
	 */
	public function getOwner (): int|false {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return int|false The group id in numerical format on success, or false on failure.
	 */
	public function getGroup (): int|false {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return int|false Returns the time the file was last accessed on success, or false on failure.
	 */
	public function getATime (): int|false {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return int|false Returns the last modified time for the file, in a Unix timestamp on success, or false on failure.
	 */
	public function getMTime (): int|false {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return int|false The last change time, in a Unix timestamp on success, or false on failure.
	 */
	public function getCTime (): int|false {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return string|false A string representing the type of the entry.
	 * May be one of file, link,
	 * dir, block, fifo,
	 * char, socket, or unknown, or false on failure.
	 */
	public function getType (): string|false {}

	/**
	 * Tells if the entry is writable
	 * @link http://www.php.net/manual/en/splfileinfo.iswritable.php
	 * @return bool Returns true if writable, false otherwise;
	 */
	public function isWritable (): bool {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool Returns true if readable, false otherwise.
	 */
	public function isReadable (): bool {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool Returns true if executable, false otherwise.
	 */
	public function isExecutable (): bool {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool Returns true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile (): bool {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool Returns true if a directory, false otherwise.
	 */
	public function isDir (): bool {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool Returns true if the file is a link, false otherwise.
	 */
	public function isLink (): bool {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return string|false Returns the target of the filesystem link on success, or false on failure.
	 */
	public function getLinkTarget (): string|false {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return string|false Returns the path to the file, or false if the file does not exist.
	 */
	public function getRealPath (): string|false {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo (?string $class = null): SplFileInfo {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo|null Returns an SplFileInfo object for the parent path of the file on success, or null on failure.
	 */
	public function getPathInfo (?string $class = null): ?SplFileInfo {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $mode [optional] 
	 * @param bool $useIncludePath [optional] 
	 * @param resource|null $context [optional] 
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $mode = '"r"', bool $useIncludePath = false, $context = null): SplFileObject {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setFileClass (string $class = 'SplFileObject::class'): void {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setInfoClass (string $class = 'SplFileInfo::class'): void {}

	/**
	 * Returns the path to the file as a string
	 * @link http://www.php.net/manual/en/splfileinfo.tostring.php
	 * @return string Returns the path to the file.
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

/**
 * The DirectoryIterator class provides a simple interface for viewing
 * the contents of filesystem directories.
 * @link http://www.php.net/manual/en/class.directoryiterator.php
 */
class DirectoryIterator extends SplFileInfo implements Stringable, SeekableIterator, Traversable, Iterator {

	/**
	 * Constructs a new directory iterator from a path
	 * @link http://www.php.net/manual/en/directoryiterator.construct.php
	 * @param string $directory 
	 * @return string 
	 */
	public function __construct (string $directory): string {}

	/**
	 * Return file name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getfilename.php
	 * @return string Returns the file name of the current DirectoryIterator item.
	 */
	public function getFilename (): string {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/directoryiterator.getextension.php
	 * @return string Returns a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension (): string {}

	/**
	 * Get base name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getbasename.php
	 * @param string $suffix [optional] 
	 * @return string The base name of the current DirectoryIterator item.
	 */
	public function getBasename (string $suffix = '""'): string {}

	/**
	 * Determine if current DirectoryIterator item is '.' or '..'
	 * @link http://www.php.net/manual/en/directoryiterator.isdot.php
	 * @return bool true if the entry is . or ..,
	 * otherwise false
	 */
	public function isDot (): bool {}

	/**
	 * Rewind the DirectoryIterator back to the start
	 * @link http://www.php.net/manual/en/directoryiterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Check whether current DirectoryIterator position is a valid file
	 * @link http://www.php.net/manual/en/directoryiterator.valid.php
	 * @return bool Returns true if the position is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Return the key for the current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.key.php
	 * @return mixed The key for the current DirectoryIterator item as an int.
	 */
	public function key (): mixed {}

	/**
	 * Return the current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.current.php
	 * @return mixed The current DirectoryIterator item.
	 */
	public function current (): mixed {}

	/**
	 * Move forward to next DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Seek to a DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.seek.php
	 * @param int $offset 
	 * @return void No value is returned.
	 */
	public function seek (int $offset): void {}

	/**
	 * Get file name as a string
	 * @link http://www.php.net/manual/en/directoryiterator.tostring.php
	 * @return string Returns the file name of the current DirectoryIterator item.
	 */
	public function __toString (): string {}

	/**
	 * Gets the path without filename
	 * @link http://www.php.net/manual/en/splfileinfo.getpath.php
	 * @return string Returns the path to the file.
	 */
	public function getPath (): string {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname (): string {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return int|false Returns the file permissions on success, or false on failure.
	 */
	public function getPerms (): int|false {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return int|false Returns the inode number for the filesystem object on success, or false on failure.
	 */
	public function getInode (): int|false {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return int|false The filesize in bytes on success, or false on failure.
	 */
	public function getSize (): int|false {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return int|false The owner id in numerical format on success, or false on failure.
	 */
	public function getOwner (): int|false {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return int|false The group id in numerical format on success, or false on failure.
	 */
	public function getGroup (): int|false {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return int|false Returns the time the file was last accessed on success, or false on failure.
	 */
	public function getATime (): int|false {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return int|false Returns the last modified time for the file, in a Unix timestamp on success, or false on failure.
	 */
	public function getMTime (): int|false {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return int|false The last change time, in a Unix timestamp on success, or false on failure.
	 */
	public function getCTime (): int|false {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return string|false A string representing the type of the entry.
	 * May be one of file, link,
	 * dir, block, fifo,
	 * char, socket, or unknown, or false on failure.
	 */
	public function getType (): string|false {}

	/**
	 * Tells if the entry is writable
	 * @link http://www.php.net/manual/en/splfileinfo.iswritable.php
	 * @return bool Returns true if writable, false otherwise;
	 */
	public function isWritable (): bool {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool Returns true if readable, false otherwise.
	 */
	public function isReadable (): bool {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool Returns true if executable, false otherwise.
	 */
	public function isExecutable (): bool {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool Returns true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile (): bool {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool Returns true if a directory, false otherwise.
	 */
	public function isDir (): bool {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool Returns true if the file is a link, false otherwise.
	 */
	public function isLink (): bool {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return string|false Returns the target of the filesystem link on success, or false on failure.
	 */
	public function getLinkTarget (): string|false {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return string|false Returns the path to the file, or false if the file does not exist.
	 */
	public function getRealPath (): string|false {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo (?string $class = null): SplFileInfo {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo|null Returns an SplFileInfo object for the parent path of the file on success, or null on failure.
	 */
	public function getPathInfo (?string $class = null): ?SplFileInfo {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $mode [optional] 
	 * @param bool $useIncludePath [optional] 
	 * @param resource|null $context [optional] 
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $mode = '"r"', bool $useIncludePath = false, $context = null): SplFileObject {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setFileClass (string $class = 'SplFileObject::class'): void {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setInfoClass (string $class = 'SplFileInfo::class'): void {}

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

/**
 * The Filesystem iterator
 * @link http://www.php.net/manual/en/class.filesystemiterator.php
 */
class FilesystemIterator extends DirectoryIterator implements Iterator, Traversable, SeekableIterator, Stringable {
	/**
	 * Masks FilesystemIterator::current
	const CURRENT_MODE_MASK = 240;
	/**
	 * Makes FilesystemIterator::current return the pathname.
	const CURRENT_AS_PATHNAME = 32;
	/**
	 * Makes FilesystemIterator::current return an SplFileInfo instance.
	const CURRENT_AS_FILEINFO = 0;
	/**
	 * Makes FilesystemIterator::current return $this (the FilesystemIterator).
	const CURRENT_AS_SELF = 16;
	/**
	 * Masks FilesystemIterator::key
	const KEY_MODE_MASK = 3840;
	/**
	 * Makes FilesystemIterator::key return the pathname.
	const KEY_AS_PATHNAME = 0;
	/**
	 * Makes RecursiveDirectoryIterator::hasChildren follow symlinks.
	const FOLLOW_SYMLINKS = 16384;
	/**
	 * Makes FilesystemIterator::key return the filename.
	const KEY_AS_FILENAME = 256;
	/**
	 * Same as FilesystemIterator::KEY_AS_FILENAME | FilesystemIterator::CURRENT_AS_FILEINFO.
	const NEW_CURRENT_AND_KEY = 256;
	/**
	 * Mask used for FilesystemIterator::getFlags and FilesystemIterator::setFlags.
	const OTHER_MODE_MASK = 28672;
	/**
	 * Skips dot files (. and ..).
	const SKIP_DOTS = 4096;
	/**
	 * Makes paths use Unix-style forward slash irrespective of system default.
	 * Note that the path that is passed to the
	 * constructor is not modified.
	const UNIX_PATHS = 8192;


	/**
	 * Constructs a new filesystem iterator
	 * @link http://www.php.net/manual/en/filesystemiterator.construct.php
	 * @param string $directory 
	 * @param int $flags [optional] 
	 * @return string 
	 */
	public function __construct (string $directory, int $flags = 'FilesystemIterator::KEY_AS_PATHNAME | FilesystemIterator::CURRENT_AS_FILEINFO | FilesystemIterator::SKIP_DOTS'): string {}

	/**
	 * Rewinds back to the beginning
	 * @link http://www.php.net/manual/en/filesystemiterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Retrieve the key for the current file
	 * @link http://www.php.net/manual/en/filesystemiterator.key.php
	 * @return string Returns the pathname or filename depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function key (): string {}

	/**
	 * The current file
	 * @link http://www.php.net/manual/en/filesystemiterator.current.php
	 * @return string|SplFileInfo|FilesystemIterator The filename, file information, or $this depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function current (): string|SplFileInfo|FilesystemIterator {}

	/**
	 * Get the handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.getflags.php
	 * @return int The integer value of the set flags.
	 */
	public function getFlags (): int {}

	/**
	 * Sets handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.setflags.php
	 * @param int $flags 
	 * @return void No value is returned.
	 */
	public function setFlags (int $flags): void {}

	/**
	 * Return file name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getfilename.php
	 * @return string Returns the file name of the current DirectoryIterator item.
	 */
	public function getFilename (): string {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/directoryiterator.getextension.php
	 * @return string Returns a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension (): string {}

	/**
	 * Get base name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getbasename.php
	 * @param string $suffix [optional] 
	 * @return string The base name of the current DirectoryIterator item.
	 */
	public function getBasename (string $suffix = '""'): string {}

	/**
	 * Determine if current DirectoryIterator item is '.' or '..'
	 * @link http://www.php.net/manual/en/directoryiterator.isdot.php
	 * @return bool true if the entry is . or ..,
	 * otherwise false
	 */
	public function isDot (): bool {}

	/**
	 * Check whether current DirectoryIterator position is a valid file
	 * @link http://www.php.net/manual/en/directoryiterator.valid.php
	 * @return bool Returns true if the position is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Move forward to next DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Seek to a DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.seek.php
	 * @param int $offset 
	 * @return void No value is returned.
	 */
	public function seek (int $offset): void {}

	/**
	 * Get file name as a string
	 * @link http://www.php.net/manual/en/directoryiterator.tostring.php
	 * @return string Returns the file name of the current DirectoryIterator item.
	 */
	public function __toString (): string {}

	/**
	 * Gets the path without filename
	 * @link http://www.php.net/manual/en/splfileinfo.getpath.php
	 * @return string Returns the path to the file.
	 */
	public function getPath (): string {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname (): string {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return int|false Returns the file permissions on success, or false on failure.
	 */
	public function getPerms (): int|false {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return int|false Returns the inode number for the filesystem object on success, or false on failure.
	 */
	public function getInode (): int|false {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return int|false The filesize in bytes on success, or false on failure.
	 */
	public function getSize (): int|false {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return int|false The owner id in numerical format on success, or false on failure.
	 */
	public function getOwner (): int|false {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return int|false The group id in numerical format on success, or false on failure.
	 */
	public function getGroup (): int|false {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return int|false Returns the time the file was last accessed on success, or false on failure.
	 */
	public function getATime (): int|false {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return int|false Returns the last modified time for the file, in a Unix timestamp on success, or false on failure.
	 */
	public function getMTime (): int|false {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return int|false The last change time, in a Unix timestamp on success, or false on failure.
	 */
	public function getCTime (): int|false {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return string|false A string representing the type of the entry.
	 * May be one of file, link,
	 * dir, block, fifo,
	 * char, socket, or unknown, or false on failure.
	 */
	public function getType (): string|false {}

	/**
	 * Tells if the entry is writable
	 * @link http://www.php.net/manual/en/splfileinfo.iswritable.php
	 * @return bool Returns true if writable, false otherwise;
	 */
	public function isWritable (): bool {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool Returns true if readable, false otherwise.
	 */
	public function isReadable (): bool {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool Returns true if executable, false otherwise.
	 */
	public function isExecutable (): bool {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool Returns true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile (): bool {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool Returns true if a directory, false otherwise.
	 */
	public function isDir (): bool {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool Returns true if the file is a link, false otherwise.
	 */
	public function isLink (): bool {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return string|false Returns the target of the filesystem link on success, or false on failure.
	 */
	public function getLinkTarget (): string|false {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return string|false Returns the path to the file, or false if the file does not exist.
	 */
	public function getRealPath (): string|false {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo (?string $class = null): SplFileInfo {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo|null Returns an SplFileInfo object for the parent path of the file on success, or null on failure.
	 */
	public function getPathInfo (?string $class = null): ?SplFileInfo {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $mode [optional] 
	 * @param bool $useIncludePath [optional] 
	 * @param resource|null $context [optional] 
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $mode = '"r"', bool $useIncludePath = false, $context = null): SplFileObject {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setFileClass (string $class = 'SplFileObject::class'): void {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setInfoClass (string $class = 'SplFileInfo::class'): void {}

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

/**
 * The RecursiveDirectoryIterator provides
 * an interface for iterating recursively over filesystem directories.
 * @link http://www.php.net/manual/en/class.recursivedirectoryiterator.php
 */
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
	 * Constructs a RecursiveDirectoryIterator
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.construct.php
	 * @param string $directory 
	 * @param int $flags [optional] 
	 * @return string 
	 */
	public function __construct (string $directory, int $flags = 'FilesystemIterator::KEY_AS_PATHNAME | FilesystemIterator::CURRENT_AS_FILEINFO'): string {}

	/**
	 * Returns whether current entry is a directory and not '.' or '..'
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.haschildren.php
	 * @param bool $allowLinks [optional] 
	 * @return bool Returns whether the current entry is a directory, but not '.' or '..'
	 */
	public function hasChildren (bool $allowLinks = false): bool {}

	/**
	 * Returns an iterator for the current entry if it is a directory
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.getchildren.php
	 * @return RecursiveDirectoryIterator The filename, file information, or $this depending on the set flags.
	 * See the FilesystemIterator
	 * constants.
	 */
	public function getChildren (): RecursiveDirectoryIterator {}

	/**
	 * Get sub path
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.getsubpath.php
	 * @return string The sub path.
	 */
	public function getSubPath (): string {}

	/**
	 * Get sub path and name
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.getsubpathname.php
	 * @return string The sub path (sub directory) and filename.
	 */
	public function getSubPathname (): string {}

	/**
	 * Rewinds back to the beginning
	 * @link http://www.php.net/manual/en/filesystemiterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Retrieve the key for the current file
	 * @link http://www.php.net/manual/en/filesystemiterator.key.php
	 * @return string Returns the pathname or filename depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function key (): string {}

	/**
	 * The current file
	 * @link http://www.php.net/manual/en/filesystemiterator.current.php
	 * @return string|SplFileInfo|FilesystemIterator The filename, file information, or $this depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function current (): string|SplFileInfo|FilesystemIterator {}

	/**
	 * Get the handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.getflags.php
	 * @return int The integer value of the set flags.
	 */
	public function getFlags (): int {}

	/**
	 * Sets handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.setflags.php
	 * @param int $flags 
	 * @return void No value is returned.
	 */
	public function setFlags (int $flags): void {}

	/**
	 * Return file name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getfilename.php
	 * @return string Returns the file name of the current DirectoryIterator item.
	 */
	public function getFilename (): string {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/directoryiterator.getextension.php
	 * @return string Returns a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension (): string {}

	/**
	 * Get base name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getbasename.php
	 * @param string $suffix [optional] 
	 * @return string The base name of the current DirectoryIterator item.
	 */
	public function getBasename (string $suffix = '""'): string {}

	/**
	 * Determine if current DirectoryIterator item is '.' or '..'
	 * @link http://www.php.net/manual/en/directoryiterator.isdot.php
	 * @return bool true if the entry is . or ..,
	 * otherwise false
	 */
	public function isDot (): bool {}

	/**
	 * Check whether current DirectoryIterator position is a valid file
	 * @link http://www.php.net/manual/en/directoryiterator.valid.php
	 * @return bool Returns true if the position is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Move forward to next DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Seek to a DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.seek.php
	 * @param int $offset 
	 * @return void No value is returned.
	 */
	public function seek (int $offset): void {}

	/**
	 * Get file name as a string
	 * @link http://www.php.net/manual/en/directoryiterator.tostring.php
	 * @return string Returns the file name of the current DirectoryIterator item.
	 */
	public function __toString (): string {}

	/**
	 * Gets the path without filename
	 * @link http://www.php.net/manual/en/splfileinfo.getpath.php
	 * @return string Returns the path to the file.
	 */
	public function getPath (): string {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname (): string {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return int|false Returns the file permissions on success, or false on failure.
	 */
	public function getPerms (): int|false {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return int|false Returns the inode number for the filesystem object on success, or false on failure.
	 */
	public function getInode (): int|false {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return int|false The filesize in bytes on success, or false on failure.
	 */
	public function getSize (): int|false {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return int|false The owner id in numerical format on success, or false on failure.
	 */
	public function getOwner (): int|false {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return int|false The group id in numerical format on success, or false on failure.
	 */
	public function getGroup (): int|false {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return int|false Returns the time the file was last accessed on success, or false on failure.
	 */
	public function getATime (): int|false {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return int|false Returns the last modified time for the file, in a Unix timestamp on success, or false on failure.
	 */
	public function getMTime (): int|false {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return int|false The last change time, in a Unix timestamp on success, or false on failure.
	 */
	public function getCTime (): int|false {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return string|false A string representing the type of the entry.
	 * May be one of file, link,
	 * dir, block, fifo,
	 * char, socket, or unknown, or false on failure.
	 */
	public function getType (): string|false {}

	/**
	 * Tells if the entry is writable
	 * @link http://www.php.net/manual/en/splfileinfo.iswritable.php
	 * @return bool Returns true if writable, false otherwise;
	 */
	public function isWritable (): bool {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool Returns true if readable, false otherwise.
	 */
	public function isReadable (): bool {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool Returns true if executable, false otherwise.
	 */
	public function isExecutable (): bool {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool Returns true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile (): bool {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool Returns true if a directory, false otherwise.
	 */
	public function isDir (): bool {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool Returns true if the file is a link, false otherwise.
	 */
	public function isLink (): bool {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return string|false Returns the target of the filesystem link on success, or false on failure.
	 */
	public function getLinkTarget (): string|false {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return string|false Returns the path to the file, or false if the file does not exist.
	 */
	public function getRealPath (): string|false {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo (?string $class = null): SplFileInfo {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo|null Returns an SplFileInfo object for the parent path of the file on success, or null on failure.
	 */
	public function getPathInfo (?string $class = null): ?SplFileInfo {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $mode [optional] 
	 * @param bool $useIncludePath [optional] 
	 * @param resource|null $context [optional] 
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $mode = '"r"', bool $useIncludePath = false, $context = null): SplFileObject {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setFileClass (string $class = 'SplFileObject::class'): void {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setInfoClass (string $class = 'SplFileInfo::class'): void {}

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

/**
 * Iterates through a file system in a similar fashion to 
 * glob.
 * @link http://www.php.net/manual/en/class.globiterator.php
 */
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
	 * Construct a directory using glob
	 * @link http://www.php.net/manual/en/globiterator.construct.php
	 * @param string $pattern 
	 * @param int $flags [optional] 
	 * @return string 
	 */
	public function __construct (string $pattern, int $flags = 'FilesystemIterator::KEY_AS_PATHNAME | FilesystemIterator::CURRENT_AS_FILEINFO'): string {}

	/**
	 * Get the number of directories and files
	 * @link http://www.php.net/manual/en/globiterator.count.php
	 * @return int The number of returned directories and files, as an
	 * int.
	 */
	public function count (): int {}

	/**
	 * Rewinds back to the beginning
	 * @link http://www.php.net/manual/en/filesystemiterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Retrieve the key for the current file
	 * @link http://www.php.net/manual/en/filesystemiterator.key.php
	 * @return string Returns the pathname or filename depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function key (): string {}

	/**
	 * The current file
	 * @link http://www.php.net/manual/en/filesystemiterator.current.php
	 * @return string|SplFileInfo|FilesystemIterator The filename, file information, or $this depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function current (): string|SplFileInfo|FilesystemIterator {}

	/**
	 * Get the handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.getflags.php
	 * @return int The integer value of the set flags.
	 */
	public function getFlags (): int {}

	/**
	 * Sets handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.setflags.php
	 * @param int $flags 
	 * @return void No value is returned.
	 */
	public function setFlags (int $flags): void {}

	/**
	 * Return file name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getfilename.php
	 * @return string Returns the file name of the current DirectoryIterator item.
	 */
	public function getFilename (): string {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/directoryiterator.getextension.php
	 * @return string Returns a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension (): string {}

	/**
	 * Get base name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getbasename.php
	 * @param string $suffix [optional] 
	 * @return string The base name of the current DirectoryIterator item.
	 */
	public function getBasename (string $suffix = '""'): string {}

	/**
	 * Determine if current DirectoryIterator item is '.' or '..'
	 * @link http://www.php.net/manual/en/directoryiterator.isdot.php
	 * @return bool true if the entry is . or ..,
	 * otherwise false
	 */
	public function isDot (): bool {}

	/**
	 * Check whether current DirectoryIterator position is a valid file
	 * @link http://www.php.net/manual/en/directoryiterator.valid.php
	 * @return bool Returns true if the position is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Move forward to next DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Seek to a DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.seek.php
	 * @param int $offset 
	 * @return void No value is returned.
	 */
	public function seek (int $offset): void {}

	/**
	 * Get file name as a string
	 * @link http://www.php.net/manual/en/directoryiterator.tostring.php
	 * @return string Returns the file name of the current DirectoryIterator item.
	 */
	public function __toString (): string {}

	/**
	 * Gets the path without filename
	 * @link http://www.php.net/manual/en/splfileinfo.getpath.php
	 * @return string Returns the path to the file.
	 */
	public function getPath (): string {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname (): string {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return int|false Returns the file permissions on success, or false on failure.
	 */
	public function getPerms (): int|false {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return int|false Returns the inode number for the filesystem object on success, or false on failure.
	 */
	public function getInode (): int|false {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return int|false The filesize in bytes on success, or false on failure.
	 */
	public function getSize (): int|false {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return int|false The owner id in numerical format on success, or false on failure.
	 */
	public function getOwner (): int|false {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return int|false The group id in numerical format on success, or false on failure.
	 */
	public function getGroup (): int|false {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return int|false Returns the time the file was last accessed on success, or false on failure.
	 */
	public function getATime (): int|false {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return int|false Returns the last modified time for the file, in a Unix timestamp on success, or false on failure.
	 */
	public function getMTime (): int|false {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return int|false The last change time, in a Unix timestamp on success, or false on failure.
	 */
	public function getCTime (): int|false {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return string|false A string representing the type of the entry.
	 * May be one of file, link,
	 * dir, block, fifo,
	 * char, socket, or unknown, or false on failure.
	 */
	public function getType (): string|false {}

	/**
	 * Tells if the entry is writable
	 * @link http://www.php.net/manual/en/splfileinfo.iswritable.php
	 * @return bool Returns true if writable, false otherwise;
	 */
	public function isWritable (): bool {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool Returns true if readable, false otherwise.
	 */
	public function isReadable (): bool {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool Returns true if executable, false otherwise.
	 */
	public function isExecutable (): bool {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool Returns true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile (): bool {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool Returns true if a directory, false otherwise.
	 */
	public function isDir (): bool {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool Returns true if the file is a link, false otherwise.
	 */
	public function isLink (): bool {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return string|false Returns the target of the filesystem link on success, or false on failure.
	 */
	public function getLinkTarget (): string|false {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return string|false Returns the path to the file, or false if the file does not exist.
	 */
	public function getRealPath (): string|false {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo (?string $class = null): SplFileInfo {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo|null Returns an SplFileInfo object for the parent path of the file on success, or null on failure.
	 */
	public function getPathInfo (?string $class = null): ?SplFileInfo {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $mode [optional] 
	 * @param bool $useIncludePath [optional] 
	 * @param resource|null $context [optional] 
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $mode = '"r"', bool $useIncludePath = false, $context = null): SplFileObject {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setFileClass (string $class = 'SplFileObject::class'): void {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setInfoClass (string $class = 'SplFileInfo::class'): void {}

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

/**
 * The SplFileObject class offers an object-oriented interface for a file.
 * @link http://www.php.net/manual/en/class.splfileobject.php
 */
class SplFileObject extends SplFileInfo implements Stringable, RecursiveIterator, Traversable, Iterator, SeekableIterator {
	/**
	 * Drop newlines at the end of a line.
	const DROP_NEW_LINE = 1;
	/**
	 * Read on rewind/next.
	const READ_AHEAD = 2;
	/**
	 * Skips empty lines in the file. This requires the READ_AHEAD flag be enabled, to work as expected.
	const SKIP_EMPTY = 4;
	/**
	 * Read lines as CSV rows.
	const READ_CSV = 8;


	/**
	 * Construct a new file object
	 * @link http://www.php.net/manual/en/splfileobject.construct.php
	 * @param string $filename 
	 * @param string $mode [optional] 
	 * @param bool $useIncludePath [optional] 
	 * @param resource|null $context [optional] 
	 * @return string 
	 */
	public function __construct (string $filename, string $mode = '"r"', bool $useIncludePath = false, $context = null): string {}

	/**
	 * Rewind the file to the first line
	 * @link http://www.php.net/manual/en/splfileobject.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Reached end of file
	 * @link http://www.php.net/manual/en/splfileobject.eof.php
	 * @return bool Returns true if file is at EOF, false otherwise.
	 */
	public function eof (): bool {}

	/**
	 * Not at EOF
	 * @link http://www.php.net/manual/en/splfileobject.valid.php
	 * @return bool Returns true if not reached EOF, false otherwise.
	 */
	public function valid (): bool {}

	/**
	 * Gets line from file
	 * @link http://www.php.net/manual/en/splfileobject.fgets.php
	 * @return string Returns a string containing the next line from the file.
	 */
	public function fgets (): string {}

	/**
	 * Read from file
	 * @link http://www.php.net/manual/en/splfileobject.fread.php
	 * @param int $length The number of bytes to read.
	 * @return string|false Returns the string read from the file or false on failure.
	 */
	public function fread (int $length): string|false {}

	/**
	 * Gets line from file and parse as CSV fields
	 * @link http://www.php.net/manual/en/splfileobject.fgetcsv.php
	 * @param string $separator [optional] 
	 * @param string $enclosure [optional] 
	 * @param string $escape [optional] 
	 * @return array|false Returns an indexed array containing the fields read, or false on error.
	 * <p>A blank line in a CSV file will be returned as an array
	 * comprising a single null field unless using SplFileObject::SKIP_EMPTY | SplFileObject::DROP_NEW_LINE, 
	 * in which case empty lines are skipped.</p>
	 */
	public function fgetcsv (string $separator = '","', string $enclosure = '"\\""', string $escape = '"\\\\"'): array|false {}

	/**
	 * Write a field array as a CSV line
	 * @link http://www.php.net/manual/en/splfileobject.fputcsv.php
	 * @param array $fields An array of values.
	 * @param string $separator [optional] The optional separator parameter sets the field
	 * delimiter (one single-byte character only).
	 * @param string $enclosure [optional] The optional enclosure parameter sets the field
	 * enclosure (one single-byte character only).
	 * @param string $escape [optional] The optional escape parameter sets the
	 * escape character (at most one single-byte character).
	 * An empty string ("") disables the proprietary escape mechanism.
	 * @param string $eol [optional] The optional eol parameter sets
	 * a custom End of Line sequence.
	 * @return int|false Returns the length of the written string or false on failure.
	 * <p>Returns false, and does not write the CSV line to the file, if the
	 * separator or enclosure
	 * parameter is not a single character.</p>
	 */
	public function fputcsv (array $fields, string $separator = '","', string $enclosure = '"\\""', string $escape = '"\\\\"', string $eol = '"\\n"'): int|false {}

	/**
	 * Set the delimiter, enclosure and escape character for CSV
	 * @link http://www.php.net/manual/en/splfileobject.setcsvcontrol.php
	 * @param string $separator [optional] 
	 * @param string $enclosure [optional] 
	 * @param string $escape [optional] 
	 * @return void No value is returned.
	 */
	public function setCsvControl (string $separator = '","', string $enclosure = '"\\""', string $escape = '"\\\\"'): void {}

	/**
	 * Get the delimiter, enclosure and escape character for CSV
	 * @link http://www.php.net/manual/en/splfileobject.getcsvcontrol.php
	 * @return array Returns an indexed array containing the delimiter, enclosure and escape character.
	 */
	public function getCsvControl (): array {}

	/**
	 * Portable file locking
	 * @link http://www.php.net/manual/en/splfileobject.flock.php
	 * @param int $operation 
	 * @param int $wouldBlock [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function flock (int $operation, int &$wouldBlock = null): bool {}

	/**
	 * Flushes the output to the file
	 * @link http://www.php.net/manual/en/splfileobject.fflush.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function fflush (): bool {}

	/**
	 * Return current file position
	 * @link http://www.php.net/manual/en/splfileobject.ftell.php
	 * @return int|false Returns the position of the file pointer as an integer, or false on error.
	 */
	public function ftell (): int|false {}

	/**
	 * Seek to a position
	 * @link http://www.php.net/manual/en/splfileobject.fseek.php
	 * @param int $offset 
	 * @param int $whence [optional] 
	 * @return int Returns 0 if the seek was successful, -1 otherwise. Note that seeking
	 * past EOF is not considered an error.
	 */
	public function fseek (int $offset, int $whence = SEEK_SET): int {}

	/**
	 * Gets character from file
	 * @link http://www.php.net/manual/en/splfileobject.fgetc.php
	 * @return string|false Returns a string containing a single character read from the file or false on EOF.
	 */
	public function fgetc (): string|false {}

	/**
	 * Output all remaining data on a file pointer
	 * @link http://www.php.net/manual/en/splfileobject.fpassthru.php
	 * @return int Returns the number of characters read from handle
	 * and passed through to the output.
	 */
	public function fpassthru (): int {}

	/**
	 * Parses input from file according to a format
	 * @link http://www.php.net/manual/en/splfileobject.fscanf.php
	 * @param string $format 
	 * @param mixed $vars 
	 * @return array|int|null If only one parameter is passed to this method, the values parsed will be
	 * returned as an array. Otherwise, if optional parameters are passed, the
	 * function will return the number of assigned values. The optional
	 * parameters must be passed by reference.
	 */
	public function fscanf (string $format, mixed &...$vars): array|int|null {}

	/**
	 * Write to file
	 * @link http://www.php.net/manual/en/splfileobject.fwrite.php
	 * @param string $data 
	 * @param int $length [optional] 
	 * @return int|false Returns the number of bytes written, or false on error.
	 */
	public function fwrite (string $data, int $length = null): int|false {}

	/**
	 * Gets information about the file
	 * @link http://www.php.net/manual/en/splfileobject.fstat.php
	 * @return array Returns an array with the statistics of the file; the format of the array
	 * is described in detail on the stat manual page.
	 */
	public function fstat (): array {}

	/**
	 * Truncates the file to a given length
	 * @link http://www.php.net/manual/en/splfileobject.ftruncate.php
	 * @param int $size 
	 * @return bool Returns true on success or false on failure.
	 */
	public function ftruncate (int $size): bool {}

	/**
	 * Retrieve current line of file
	 * @link http://www.php.net/manual/en/splfileobject.current.php
	 * @return string|array|false Retrieves the current line of the file. If the SplFileObject::READ_CSV flag is set, this method returns an array containing the current line parsed as CSV data.
	 * If the end of the file is reached, false is returned.
	 */
	public function current (): string|array|false {}

	/**
	 * Get line number
	 * @link http://www.php.net/manual/en/splfileobject.key.php
	 * @return int Returns the current line number.
	 */
	public function key (): int {}

	/**
	 * Read next line
	 * @link http://www.php.net/manual/en/splfileobject.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Sets flags for the SplFileObject
	 * @link http://www.php.net/manual/en/splfileobject.setflags.php
	 * @param int $flags 
	 * @return void No value is returned.
	 */
	public function setFlags (int $flags): void {}

	/**
	 * Gets flags for the SplFileObject
	 * @link http://www.php.net/manual/en/splfileobject.getflags.php
	 * @return int Returns an int representing the flags.
	 */
	public function getFlags (): int {}

	/**
	 * Set maximum line length
	 * @link http://www.php.net/manual/en/splfileobject.setmaxlinelen.php
	 * @param int $maxLength 
	 * @return void No value is returned.
	 */
	public function setMaxLineLen (int $maxLength): void {}

	/**
	 * Get maximum line length
	 * @link http://www.php.net/manual/en/splfileobject.getmaxlinelen.php
	 * @return int Returns the maximum line length if one has been set with
	 * SplFileObject::setMaxLineLen, default is 0.
	 */
	public function getMaxLineLen (): int {}

	/**
	 * SplFileObject does not have children
	 * @link http://www.php.net/manual/en/splfileobject.haschildren.php
	 * @return false Returns false
	 */
	public function hasChildren (): false {}

	/**
	 * No purpose
	 * @link http://www.php.net/manual/en/splfileobject.getchildren.php
	 * @return null Returns null.
	 */
	public function getChildren (): null {}

	/**
	 * Seek to specified line
	 * @link http://www.php.net/manual/en/splfileobject.seek.php
	 * @param int $line 
	 * @return void No value is returned.
	 */
	public function seek (int $line): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getCurrentLine () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * Gets the path without filename
	 * @link http://www.php.net/manual/en/splfileinfo.getpath.php
	 * @return string Returns the path to the file.
	 */
	public function getPath (): string {}

	/**
	 * Gets the filename
	 * @link http://www.php.net/manual/en/splfileinfo.getfilename.php
	 * @return string The filename.
	 */
	public function getFilename (): string {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/splfileinfo.getextension.php
	 * @return string Returns a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension (): string {}

	/**
	 * Gets the base name of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getbasename.php
	 * @param string $suffix [optional] 
	 * @return string Returns the base name without path information.
	 */
	public function getBasename (string $suffix = '""'): string {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname (): string {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return int|false Returns the file permissions on success, or false on failure.
	 */
	public function getPerms (): int|false {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return int|false Returns the inode number for the filesystem object on success, or false on failure.
	 */
	public function getInode (): int|false {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return int|false The filesize in bytes on success, or false on failure.
	 */
	public function getSize (): int|false {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return int|false The owner id in numerical format on success, or false on failure.
	 */
	public function getOwner (): int|false {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return int|false The group id in numerical format on success, or false on failure.
	 */
	public function getGroup (): int|false {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return int|false Returns the time the file was last accessed on success, or false on failure.
	 */
	public function getATime (): int|false {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return int|false Returns the last modified time for the file, in a Unix timestamp on success, or false on failure.
	 */
	public function getMTime (): int|false {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return int|false The last change time, in a Unix timestamp on success, or false on failure.
	 */
	public function getCTime (): int|false {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return string|false A string representing the type of the entry.
	 * May be one of file, link,
	 * dir, block, fifo,
	 * char, socket, or unknown, or false on failure.
	 */
	public function getType (): string|false {}

	/**
	 * Tells if the entry is writable
	 * @link http://www.php.net/manual/en/splfileinfo.iswritable.php
	 * @return bool Returns true if writable, false otherwise;
	 */
	public function isWritable (): bool {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool Returns true if readable, false otherwise.
	 */
	public function isReadable (): bool {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool Returns true if executable, false otherwise.
	 */
	public function isExecutable (): bool {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool Returns true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile (): bool {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool Returns true if a directory, false otherwise.
	 */
	public function isDir (): bool {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool Returns true if the file is a link, false otherwise.
	 */
	public function isLink (): bool {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return string|false Returns the target of the filesystem link on success, or false on failure.
	 */
	public function getLinkTarget (): string|false {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return string|false Returns the path to the file, or false if the file does not exist.
	 */
	public function getRealPath (): string|false {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo (?string $class = null): SplFileInfo {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo|null Returns an SplFileInfo object for the parent path of the file on success, or null on failure.
	 */
	public function getPathInfo (?string $class = null): ?SplFileInfo {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $mode [optional] 
	 * @param bool $useIncludePath [optional] 
	 * @param resource|null $context [optional] 
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $mode = '"r"', bool $useIncludePath = false, $context = null): SplFileObject {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setFileClass (string $class = 'SplFileObject::class'): void {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setInfoClass (string $class = 'SplFileInfo::class'): void {}

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

/**
 * The SplTempFileObject class offers an object-oriented interface for a temporary file.
 * @link http://www.php.net/manual/en/class.spltempfileobject.php
 */
class SplTempFileObject extends SplFileObject implements SeekableIterator, Iterator, Traversable, RecursiveIterator, Stringable {
	const DROP_NEW_LINE = 1;
	const READ_AHEAD = 2;
	const SKIP_EMPTY = 4;
	const READ_CSV = 8;


	/**
	 * Construct a new temporary file object
	 * @link http://www.php.net/manual/en/spltempfileobject.construct.php
	 * @param int $maxMemory [optional] 
	 * @return int 
	 */
	public function __construct (int $maxMemory = '2 * 1024 * 1024'): int {}

	/**
	 * Rewind the file to the first line
	 * @link http://www.php.net/manual/en/splfileobject.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Reached end of file
	 * @link http://www.php.net/manual/en/splfileobject.eof.php
	 * @return bool Returns true if file is at EOF, false otherwise.
	 */
	public function eof (): bool {}

	/**
	 * Not at EOF
	 * @link http://www.php.net/manual/en/splfileobject.valid.php
	 * @return bool Returns true if not reached EOF, false otherwise.
	 */
	public function valid (): bool {}

	/**
	 * Gets line from file
	 * @link http://www.php.net/manual/en/splfileobject.fgets.php
	 * @return string Returns a string containing the next line from the file.
	 */
	public function fgets (): string {}

	/**
	 * Read from file
	 * @link http://www.php.net/manual/en/splfileobject.fread.php
	 * @param int $length The number of bytes to read.
	 * @return string|false Returns the string read from the file or false on failure.
	 */
	public function fread (int $length): string|false {}

	/**
	 * Gets line from file and parse as CSV fields
	 * @link http://www.php.net/manual/en/splfileobject.fgetcsv.php
	 * @param string $separator [optional] 
	 * @param string $enclosure [optional] 
	 * @param string $escape [optional] 
	 * @return array|false Returns an indexed array containing the fields read, or false on error.
	 * <p>A blank line in a CSV file will be returned as an array
	 * comprising a single null field unless using SplFileObject::SKIP_EMPTY | SplFileObject::DROP_NEW_LINE, 
	 * in which case empty lines are skipped.</p>
	 */
	public function fgetcsv (string $separator = '","', string $enclosure = '"\\""', string $escape = '"\\\\"'): array|false {}

	/**
	 * Write a field array as a CSV line
	 * @link http://www.php.net/manual/en/splfileobject.fputcsv.php
	 * @param array $fields An array of values.
	 * @param string $separator [optional] The optional separator parameter sets the field
	 * delimiter (one single-byte character only).
	 * @param string $enclosure [optional] The optional enclosure parameter sets the field
	 * enclosure (one single-byte character only).
	 * @param string $escape [optional] The optional escape parameter sets the
	 * escape character (at most one single-byte character).
	 * An empty string ("") disables the proprietary escape mechanism.
	 * @param string $eol [optional] The optional eol parameter sets
	 * a custom End of Line sequence.
	 * @return int|false Returns the length of the written string or false on failure.
	 * <p>Returns false, and does not write the CSV line to the file, if the
	 * separator or enclosure
	 * parameter is not a single character.</p>
	 */
	public function fputcsv (array $fields, string $separator = '","', string $enclosure = '"\\""', string $escape = '"\\\\"', string $eol = '"\\n"'): int|false {}

	/**
	 * Set the delimiter, enclosure and escape character for CSV
	 * @link http://www.php.net/manual/en/splfileobject.setcsvcontrol.php
	 * @param string $separator [optional] 
	 * @param string $enclosure [optional] 
	 * @param string $escape [optional] 
	 * @return void No value is returned.
	 */
	public function setCsvControl (string $separator = '","', string $enclosure = '"\\""', string $escape = '"\\\\"'): void {}

	/**
	 * Get the delimiter, enclosure and escape character for CSV
	 * @link http://www.php.net/manual/en/splfileobject.getcsvcontrol.php
	 * @return array Returns an indexed array containing the delimiter, enclosure and escape character.
	 */
	public function getCsvControl (): array {}

	/**
	 * Portable file locking
	 * @link http://www.php.net/manual/en/splfileobject.flock.php
	 * @param int $operation 
	 * @param int $wouldBlock [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function flock (int $operation, int &$wouldBlock = null): bool {}

	/**
	 * Flushes the output to the file
	 * @link http://www.php.net/manual/en/splfileobject.fflush.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function fflush (): bool {}

	/**
	 * Return current file position
	 * @link http://www.php.net/manual/en/splfileobject.ftell.php
	 * @return int|false Returns the position of the file pointer as an integer, or false on error.
	 */
	public function ftell (): int|false {}

	/**
	 * Seek to a position
	 * @link http://www.php.net/manual/en/splfileobject.fseek.php
	 * @param int $offset 
	 * @param int $whence [optional] 
	 * @return int Returns 0 if the seek was successful, -1 otherwise. Note that seeking
	 * past EOF is not considered an error.
	 */
	public function fseek (int $offset, int $whence = SEEK_SET): int {}

	/**
	 * Gets character from file
	 * @link http://www.php.net/manual/en/splfileobject.fgetc.php
	 * @return string|false Returns a string containing a single character read from the file or false on EOF.
	 */
	public function fgetc (): string|false {}

	/**
	 * Output all remaining data on a file pointer
	 * @link http://www.php.net/manual/en/splfileobject.fpassthru.php
	 * @return int Returns the number of characters read from handle
	 * and passed through to the output.
	 */
	public function fpassthru (): int {}

	/**
	 * Parses input from file according to a format
	 * @link http://www.php.net/manual/en/splfileobject.fscanf.php
	 * @param string $format 
	 * @param mixed $vars 
	 * @return array|int|null If only one parameter is passed to this method, the values parsed will be
	 * returned as an array. Otherwise, if optional parameters are passed, the
	 * function will return the number of assigned values. The optional
	 * parameters must be passed by reference.
	 */
	public function fscanf (string $format, mixed &...$vars): array|int|null {}

	/**
	 * Write to file
	 * @link http://www.php.net/manual/en/splfileobject.fwrite.php
	 * @param string $data 
	 * @param int $length [optional] 
	 * @return int|false Returns the number of bytes written, or false on error.
	 */
	public function fwrite (string $data, int $length = null): int|false {}

	/**
	 * Gets information about the file
	 * @link http://www.php.net/manual/en/splfileobject.fstat.php
	 * @return array Returns an array with the statistics of the file; the format of the array
	 * is described in detail on the stat manual page.
	 */
	public function fstat (): array {}

	/**
	 * Truncates the file to a given length
	 * @link http://www.php.net/manual/en/splfileobject.ftruncate.php
	 * @param int $size 
	 * @return bool Returns true on success or false on failure.
	 */
	public function ftruncate (int $size): bool {}

	/**
	 * Retrieve current line of file
	 * @link http://www.php.net/manual/en/splfileobject.current.php
	 * @return string|array|false Retrieves the current line of the file. If the SplFileObject::READ_CSV flag is set, this method returns an array containing the current line parsed as CSV data.
	 * If the end of the file is reached, false is returned.
	 */
	public function current (): string|array|false {}

	/**
	 * Get line number
	 * @link http://www.php.net/manual/en/splfileobject.key.php
	 * @return int Returns the current line number.
	 */
	public function key (): int {}

	/**
	 * Read next line
	 * @link http://www.php.net/manual/en/splfileobject.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Sets flags for the SplFileObject
	 * @link http://www.php.net/manual/en/splfileobject.setflags.php
	 * @param int $flags 
	 * @return void No value is returned.
	 */
	public function setFlags (int $flags): void {}

	/**
	 * Gets flags for the SplFileObject
	 * @link http://www.php.net/manual/en/splfileobject.getflags.php
	 * @return int Returns an int representing the flags.
	 */
	public function getFlags (): int {}

	/**
	 * Set maximum line length
	 * @link http://www.php.net/manual/en/splfileobject.setmaxlinelen.php
	 * @param int $maxLength 
	 * @return void No value is returned.
	 */
	public function setMaxLineLen (int $maxLength): void {}

	/**
	 * Get maximum line length
	 * @link http://www.php.net/manual/en/splfileobject.getmaxlinelen.php
	 * @return int Returns the maximum line length if one has been set with
	 * SplFileObject::setMaxLineLen, default is 0.
	 */
	public function getMaxLineLen (): int {}

	/**
	 * SplFileObject does not have children
	 * @link http://www.php.net/manual/en/splfileobject.haschildren.php
	 * @return false Returns false
	 */
	public function hasChildren (): false {}

	/**
	 * No purpose
	 * @link http://www.php.net/manual/en/splfileobject.getchildren.php
	 * @return null Returns null.
	 */
	public function getChildren (): null {}

	/**
	 * Seek to specified line
	 * @link http://www.php.net/manual/en/splfileobject.seek.php
	 * @param int $line 
	 * @return void No value is returned.
	 */
	public function seek (int $line): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getCurrentLine () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * Gets the path without filename
	 * @link http://www.php.net/manual/en/splfileinfo.getpath.php
	 * @return string Returns the path to the file.
	 */
	public function getPath (): string {}

	/**
	 * Gets the filename
	 * @link http://www.php.net/manual/en/splfileinfo.getfilename.php
	 * @return string The filename.
	 */
	public function getFilename (): string {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/splfileinfo.getextension.php
	 * @return string Returns a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension (): string {}

	/**
	 * Gets the base name of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getbasename.php
	 * @param string $suffix [optional] 
	 * @return string Returns the base name without path information.
	 */
	public function getBasename (string $suffix = '""'): string {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname (): string {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return int|false Returns the file permissions on success, or false on failure.
	 */
	public function getPerms (): int|false {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return int|false Returns the inode number for the filesystem object on success, or false on failure.
	 */
	public function getInode (): int|false {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return int|false The filesize in bytes on success, or false on failure.
	 */
	public function getSize (): int|false {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return int|false The owner id in numerical format on success, or false on failure.
	 */
	public function getOwner (): int|false {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return int|false The group id in numerical format on success, or false on failure.
	 */
	public function getGroup (): int|false {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return int|false Returns the time the file was last accessed on success, or false on failure.
	 */
	public function getATime (): int|false {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return int|false Returns the last modified time for the file, in a Unix timestamp on success, or false on failure.
	 */
	public function getMTime (): int|false {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return int|false The last change time, in a Unix timestamp on success, or false on failure.
	 */
	public function getCTime (): int|false {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return string|false A string representing the type of the entry.
	 * May be one of file, link,
	 * dir, block, fifo,
	 * char, socket, or unknown, or false on failure.
	 */
	public function getType (): string|false {}

	/**
	 * Tells if the entry is writable
	 * @link http://www.php.net/manual/en/splfileinfo.iswritable.php
	 * @return bool Returns true if writable, false otherwise;
	 */
	public function isWritable (): bool {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool Returns true if readable, false otherwise.
	 */
	public function isReadable (): bool {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool Returns true if executable, false otherwise.
	 */
	public function isExecutable (): bool {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool Returns true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile (): bool {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool Returns true if a directory, false otherwise.
	 */
	public function isDir (): bool {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool Returns true if the file is a link, false otherwise.
	 */
	public function isLink (): bool {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return string|false Returns the target of the filesystem link on success, or false on failure.
	 */
	public function getLinkTarget (): string|false {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return string|false Returns the path to the file, or false if the file does not exist.
	 */
	public function getRealPath (): string|false {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo (?string $class = null): SplFileInfo {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo|null Returns an SplFileInfo object for the parent path of the file on success, or null on failure.
	 */
	public function getPathInfo (?string $class = null): ?SplFileInfo {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $mode [optional] 
	 * @param bool $useIncludePath [optional] 
	 * @param resource|null $context [optional] 
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $mode = '"r"', bool $useIncludePath = false, $context = null): SplFileObject {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setFileClass (string $class = 'SplFileObject::class'): void {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setInfoClass (string $class = 'SplFileInfo::class'): void {}

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

/**
 * The SplDoublyLinkedList class provides the main functionalities of a doubly linked list.
 * @link http://www.php.net/manual/en/class.spldoublylinkedlist.php
 */
class SplDoublyLinkedList implements Iterator, Traversable, Countable, ArrayAccess, Serializable {
	/**
	 * The list will be iterated in a last in, first out order, like a stack.
	const IT_MODE_LIFO = 2;
	/**
	 * The list will be iterated in a first in, first out order, like a queue.
	const IT_MODE_FIFO = 0;
	/**
	 * Iteration will remove the iterated elements.
	const IT_MODE_DELETE = 1;
	/**
	 * Iteration will not remove the iterated elements.
	const IT_MODE_KEEP = 0;


	/**
	 * Add/insert a new value at the specified index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.add.php
	 * @param int $index 
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function add (int $index, mixed $value): void {}

	/**
	 * Pops a node from the end of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.pop.php
	 * @return mixed The value of the popped node.
	 */
	public function pop (): mixed {}

	/**
	 * Shifts a node from the beginning of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.shift.php
	 * @return mixed The value of the shifted node.
	 */
	public function shift (): mixed {}

	/**
	 * Pushes an element at the end of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.push.php
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function push (mixed $value): void {}

	/**
	 * Prepends the doubly linked list with an element
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.unshift.php
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function unshift (mixed $value): void {}

	/**
	 * Peeks at the node from the end of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.top.php
	 * @return mixed The value of the last node.
	 */
	public function top (): mixed {}

	/**
	 * Peeks at the node from the beginning of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.bottom.php
	 * @return mixed The value of the first node.
	 */
	public function bottom (): mixed {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

	/**
	 * Counts the number of elements in the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.count.php
	 * @return int Returns the number of elements in the doubly linked list.
	 */
	public function count (): int {}

	/**
	 * Checks whether the doubly linked list is empty
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.isempty.php
	 * @return bool Returns whether the doubly linked list is empty.
	 */
	public function isEmpty (): bool {}

	/**
	 * Sets the mode of iteration
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.setiteratormode.php
	 * @param int $mode 
	 * @return int Returns the different modes and flags that affect the iteration.
	 */
	public function setIteratorMode (int $mode): int {}

	/**
	 * Returns the mode of iteration
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.getiteratormode.php
	 * @return int Returns the different modes and flags that affect the iteration.
	 */
	public function getIteratorMode (): int {}

	/**
	 * Returns whether the requested $index exists
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetexists.php
	 * @param int $index 
	 * @return bool true if the requested index exists, otherwise false
	 */
	public function offsetExists (int $index): bool {}

	/**
	 * Returns the value at the specified $index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetget.php
	 * @param int $index 
	 * @return mixed The value at the specified index.
	 */
	public function offsetGet (int $index): mixed {}

	/**
	 * Sets the value at the specified $index to $value
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetset.php
	 * @param int|null $index 
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function offsetSet (?int $index, mixed $value): void {}

	/**
	 * Unsets the value at the specified $index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetunset.php
	 * @param int $index 
	 * @return void No value is returned.
	 */
	public function offsetUnset (int $index): void {}

	/**
	 * Rewind iterator back to the start
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Return current array entry
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.current.php
	 * @return mixed The current node value.
	 */
	public function current (): mixed {}

	/**
	 * Return current node index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.key.php
	 * @return int The current node index.
	 */
	public function key (): int {}

	/**
	 * Move to previous entry
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.prev.php
	 * @return void No value is returned.
	 */
	public function prev (): void {}

	/**
	 * Move to next entry
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Check whether the doubly linked list contains more nodes
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.valid.php
	 * @return bool Returns <p>false otherwise.
	 */
	public function valid (): bool {}

	/**
	 * Unserializes the storage
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.unserialize.php
	 * @param string $data The serialized string.
	 * @return void No value is returned.
	 */
	public function unserialize (string $data): void {}

	/**
	 * Serializes the storage
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.serialize.php
	 * @return string The serialized string.
	 */
	public function serialize (): string {}

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

/**
 * The SplQueue class provides the main functionalities of a queue implemented using a doubly linked list by
 * setting the iterator mode to SplDoublyLinkedList::IT_MODE_FIFO.
 * @link http://www.php.net/manual/en/class.splqueue.php
 */
class SplQueue extends SplDoublyLinkedList implements Serializable, ArrayAccess, Countable, Traversable, Iterator {
	const IT_MODE_LIFO = 2;
	const IT_MODE_FIFO = 0;
	const IT_MODE_DELETE = 1;
	const IT_MODE_KEEP = 0;


	/**
	 * Adds an element to the queue
	 * @link http://www.php.net/manual/en/splqueue.enqueue.php
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function enqueue (mixed $value): void {}

	/**
	 * Dequeues a node from the queue
	 * @link http://www.php.net/manual/en/splqueue.dequeue.php
	 * @return mixed The value of the dequeued node.
	 */
	public function dequeue (): mixed {}

	/**
	 * Add/insert a new value at the specified index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.add.php
	 * @param int $index 
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function add (int $index, mixed $value): void {}

	/**
	 * Pops a node from the end of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.pop.php
	 * @return mixed The value of the popped node.
	 */
	public function pop (): mixed {}

	/**
	 * Shifts a node from the beginning of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.shift.php
	 * @return mixed The value of the shifted node.
	 */
	public function shift (): mixed {}

	/**
	 * Pushes an element at the end of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.push.php
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function push (mixed $value): void {}

	/**
	 * Prepends the doubly linked list with an element
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.unshift.php
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function unshift (mixed $value): void {}

	/**
	 * Peeks at the node from the end of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.top.php
	 * @return mixed The value of the last node.
	 */
	public function top (): mixed {}

	/**
	 * Peeks at the node from the beginning of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.bottom.php
	 * @return mixed The value of the first node.
	 */
	public function bottom (): mixed {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

	/**
	 * Counts the number of elements in the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.count.php
	 * @return int Returns the number of elements in the doubly linked list.
	 */
	public function count (): int {}

	/**
	 * Checks whether the doubly linked list is empty
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.isempty.php
	 * @return bool Returns whether the doubly linked list is empty.
	 */
	public function isEmpty (): bool {}

	/**
	 * Sets the mode of iteration
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.setiteratormode.php
	 * @param int $mode 
	 * @return int Returns the different modes and flags that affect the iteration.
	 */
	public function setIteratorMode (int $mode): int {}

	/**
	 * Returns the mode of iteration
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.getiteratormode.php
	 * @return int Returns the different modes and flags that affect the iteration.
	 */
	public function getIteratorMode (): int {}

	/**
	 * Returns whether the requested $index exists
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetexists.php
	 * @param int $index 
	 * @return bool true if the requested index exists, otherwise false
	 */
	public function offsetExists (int $index): bool {}

	/**
	 * Returns the value at the specified $index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetget.php
	 * @param int $index 
	 * @return mixed The value at the specified index.
	 */
	public function offsetGet (int $index): mixed {}

	/**
	 * Sets the value at the specified $index to $value
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetset.php
	 * @param int|null $index 
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function offsetSet (?int $index, mixed $value): void {}

	/**
	 * Unsets the value at the specified $index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetunset.php
	 * @param int $index 
	 * @return void No value is returned.
	 */
	public function offsetUnset (int $index): void {}

	/**
	 * Rewind iterator back to the start
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Return current array entry
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.current.php
	 * @return mixed The current node value.
	 */
	public function current (): mixed {}

	/**
	 * Return current node index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.key.php
	 * @return int The current node index.
	 */
	public function key (): int {}

	/**
	 * Move to previous entry
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.prev.php
	 * @return void No value is returned.
	 */
	public function prev (): void {}

	/**
	 * Move to next entry
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Check whether the doubly linked list contains more nodes
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.valid.php
	 * @return bool Returns <p>false otherwise.
	 */
	public function valid (): bool {}

	/**
	 * Unserializes the storage
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.unserialize.php
	 * @param string $data The serialized string.
	 * @return void No value is returned.
	 */
	public function unserialize (string $data): void {}

	/**
	 * Serializes the storage
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.serialize.php
	 * @return string The serialized string.
	 */
	public function serialize (): string {}

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

/**
 * The SplStack class provides the main functionalities of a stack implemented using a doubly linked list by
 * setting the iterator mode to SplDoublyLinkedList::IT_MODE_LIFO.
 * @link http://www.php.net/manual/en/class.splstack.php
 */
class SplStack extends SplDoublyLinkedList implements Serializable, ArrayAccess, Countable, Traversable, Iterator {
	const IT_MODE_LIFO = 2;
	const IT_MODE_FIFO = 0;
	const IT_MODE_DELETE = 1;
	const IT_MODE_KEEP = 0;


	/**
	 * Add/insert a new value at the specified index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.add.php
	 * @param int $index 
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function add (int $index, mixed $value): void {}

	/**
	 * Pops a node from the end of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.pop.php
	 * @return mixed The value of the popped node.
	 */
	public function pop (): mixed {}

	/**
	 * Shifts a node from the beginning of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.shift.php
	 * @return mixed The value of the shifted node.
	 */
	public function shift (): mixed {}

	/**
	 * Pushes an element at the end of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.push.php
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function push (mixed $value): void {}

	/**
	 * Prepends the doubly linked list with an element
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.unshift.php
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function unshift (mixed $value): void {}

	/**
	 * Peeks at the node from the end of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.top.php
	 * @return mixed The value of the last node.
	 */
	public function top (): mixed {}

	/**
	 * Peeks at the node from the beginning of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.bottom.php
	 * @return mixed The value of the first node.
	 */
	public function bottom (): mixed {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

	/**
	 * Counts the number of elements in the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.count.php
	 * @return int Returns the number of elements in the doubly linked list.
	 */
	public function count (): int {}

	/**
	 * Checks whether the doubly linked list is empty
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.isempty.php
	 * @return bool Returns whether the doubly linked list is empty.
	 */
	public function isEmpty (): bool {}

	/**
	 * Sets the mode of iteration
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.setiteratormode.php
	 * @param int $mode 
	 * @return int Returns the different modes and flags that affect the iteration.
	 */
	public function setIteratorMode (int $mode): int {}

	/**
	 * Returns the mode of iteration
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.getiteratormode.php
	 * @return int Returns the different modes and flags that affect the iteration.
	 */
	public function getIteratorMode (): int {}

	/**
	 * Returns whether the requested $index exists
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetexists.php
	 * @param int $index 
	 * @return bool true if the requested index exists, otherwise false
	 */
	public function offsetExists (int $index): bool {}

	/**
	 * Returns the value at the specified $index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetget.php
	 * @param int $index 
	 * @return mixed The value at the specified index.
	 */
	public function offsetGet (int $index): mixed {}

	/**
	 * Sets the value at the specified $index to $value
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetset.php
	 * @param int|null $index 
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function offsetSet (?int $index, mixed $value): void {}

	/**
	 * Unsets the value at the specified $index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetunset.php
	 * @param int $index 
	 * @return void No value is returned.
	 */
	public function offsetUnset (int $index): void {}

	/**
	 * Rewind iterator back to the start
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Return current array entry
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.current.php
	 * @return mixed The current node value.
	 */
	public function current (): mixed {}

	/**
	 * Return current node index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.key.php
	 * @return int The current node index.
	 */
	public function key (): int {}

	/**
	 * Move to previous entry
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.prev.php
	 * @return void No value is returned.
	 */
	public function prev (): void {}

	/**
	 * Move to next entry
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Check whether the doubly linked list contains more nodes
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.valid.php
	 * @return bool Returns <p>false otherwise.
	 */
	public function valid (): bool {}

	/**
	 * Unserializes the storage
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.unserialize.php
	 * @param string $data The serialized string.
	 * @return void No value is returned.
	 */
	public function unserialize (string $data): void {}

	/**
	 * Serializes the storage
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.serialize.php
	 * @return string The serialized string.
	 */
	public function serialize (): string {}

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

/**
 * The SplHeap class provides the main functionalities of a Heap.
 * @link http://www.php.net/manual/en/class.splheap.php
 */
abstract class SplHeap implements Iterator, Traversable, Countable {

	/**
	 * Extracts a node from top of the heap and sift up
	 * @link http://www.php.net/manual/en/splheap.extract.php
	 * @return mixed The value of the extracted node.
	 */
	public function extract (): mixed {}

	/**
	 * Inserts an element in the heap by sifting it up
	 * @link http://www.php.net/manual/en/splheap.insert.php
	 * @param mixed $value 
	 * @return true Always returns true.
	 */
	public function insert (mixed $value): true {}

	/**
	 * Peeks at the node from the top of the heap
	 * @link http://www.php.net/manual/en/splheap.top.php
	 * @return mixed The value of the node on the top.
	 */
	public function top (): mixed {}

	/**
	 * Counts the number of elements in the heap
	 * @link http://www.php.net/manual/en/splheap.count.php
	 * @return int Returns the number of elements in the heap.
	 */
	public function count (): int {}

	/**
	 * Checks whether the heap is empty
	 * @link http://www.php.net/manual/en/splheap.isempty.php
	 * @return bool Returns whether the heap is empty.
	 */
	public function isEmpty (): bool {}

	/**
	 * Rewind iterator back to the start (no-op)
	 * @link http://www.php.net/manual/en/splheap.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Return current node pointed by the iterator
	 * @link http://www.php.net/manual/en/splheap.current.php
	 * @return mixed The current node value.
	 */
	public function current (): mixed {}

	/**
	 * Return current node index
	 * @link http://www.php.net/manual/en/splheap.key.php
	 * @return int The current node index.
	 */
	public function key (): int {}

	/**
	 * Move to the next node
	 * @link http://www.php.net/manual/en/splheap.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Check whether the heap contains more nodes
	 * @link http://www.php.net/manual/en/splheap.valid.php
	 * @return bool Returns true if the heap contains any more nodes, false otherwise.
	 */
	public function valid (): bool {}

	/**
	 * Recover from the corrupted state and allow further actions on the heap
	 * @link http://www.php.net/manual/en/splheap.recoverfromcorruption.php
	 * @return bool Always returns true.
	 */
	public function recoverFromCorruption (): bool {}

	/**
	 * Compare elements in order to place them correctly in the heap while sifting up
	 * @link http://www.php.net/manual/en/splheap.compare.php
	 * @param mixed $value1 
	 * @param mixed $value2 
	 * @return int Result of the comparison, positive integer if value1 is greater than value2, 0 if they are equal, negative integer otherwise.
	 * <p>Having multiple elements with the same value in a Heap is not recommended. They will end up in an arbitrary relative position.</p>
	 */
	abstract protected function compare (mixed $value1, mixed $value2): int;

	/**
	 * Tells if the heap is in a corrupted state
	 * @link http://www.php.net/manual/en/splheap.iscorrupted.php
	 * @return bool Returns true if the heap is corrupted, false otherwise.
	 */
	public function isCorrupted (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

}

/**
 * The SplMinHeap class provides the main functionalities of a heap, keeping the minimum on the top.
 * @link http://www.php.net/manual/en/class.splminheap.php
 */
class SplMinHeap extends SplHeap implements Countable, Traversable, Iterator {

	/**
	 * Compare elements in order to place them correctly in the heap while sifting up
	 * @link http://www.php.net/manual/en/splminheap.compare.php
	 * @param mixed $value1 
	 * @param mixed $value2 
	 * @return int Result of the comparison, positive integer if value1 is lower than value2, 0 if they are equal, negative integer otherwise.
	 * <p>Having multiple elements with the same value in a Heap is not recommended. They will end up in an arbitrary relative position.</p>
	 */
	protected function compare (mixed $value1, mixed $value2): int {}

	/**
	 * Extracts a node from top of the heap and sift up
	 * @link http://www.php.net/manual/en/splheap.extract.php
	 * @return mixed The value of the extracted node.
	 */
	public function extract (): mixed {}

	/**
	 * Inserts an element in the heap by sifting it up
	 * @link http://www.php.net/manual/en/splheap.insert.php
	 * @param mixed $value 
	 * @return true Always returns true.
	 */
	public function insert (mixed $value): true {}

	/**
	 * Peeks at the node from the top of the heap
	 * @link http://www.php.net/manual/en/splheap.top.php
	 * @return mixed The value of the node on the top.
	 */
	public function top (): mixed {}

	/**
	 * Counts the number of elements in the heap
	 * @link http://www.php.net/manual/en/splheap.count.php
	 * @return int Returns the number of elements in the heap.
	 */
	public function count (): int {}

	/**
	 * Checks whether the heap is empty
	 * @link http://www.php.net/manual/en/splheap.isempty.php
	 * @return bool Returns whether the heap is empty.
	 */
	public function isEmpty (): bool {}

	/**
	 * Rewind iterator back to the start (no-op)
	 * @link http://www.php.net/manual/en/splheap.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Return current node pointed by the iterator
	 * @link http://www.php.net/manual/en/splheap.current.php
	 * @return mixed The current node value.
	 */
	public function current (): mixed {}

	/**
	 * Return current node index
	 * @link http://www.php.net/manual/en/splheap.key.php
	 * @return int The current node index.
	 */
	public function key (): int {}

	/**
	 * Move to the next node
	 * @link http://www.php.net/manual/en/splheap.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Check whether the heap contains more nodes
	 * @link http://www.php.net/manual/en/splheap.valid.php
	 * @return bool Returns true if the heap contains any more nodes, false otherwise.
	 */
	public function valid (): bool {}

	/**
	 * Recover from the corrupted state and allow further actions on the heap
	 * @link http://www.php.net/manual/en/splheap.recoverfromcorruption.php
	 * @return bool Always returns true.
	 */
	public function recoverFromCorruption (): bool {}

	/**
	 * Tells if the heap is in a corrupted state
	 * @link http://www.php.net/manual/en/splheap.iscorrupted.php
	 * @return bool Returns true if the heap is corrupted, false otherwise.
	 */
	public function isCorrupted (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

}

/**
 * The SplMaxHeap class provides the main functionalities of a heap, keeping the maximum on the top.
 * @link http://www.php.net/manual/en/class.splmaxheap.php
 */
class SplMaxHeap extends SplHeap implements Countable, Traversable, Iterator {

	/**
	 * Compare elements in order to place them correctly in the heap while sifting up
	 * @link http://www.php.net/manual/en/splmaxheap.compare.php
	 * @param mixed $value1 
	 * @param mixed $value2 
	 * @return int Result of the comparison, positive integer if value1 is greater than value2, 0 if they are equal, negative integer otherwise.
	 * <p>Having multiple elements with the same value in a Heap is not recommended. They will end up in an arbitrary relative position.</p>
	 */
	protected function compare (mixed $value1, mixed $value2): int {}

	/**
	 * Extracts a node from top of the heap and sift up
	 * @link http://www.php.net/manual/en/splheap.extract.php
	 * @return mixed The value of the extracted node.
	 */
	public function extract (): mixed {}

	/**
	 * Inserts an element in the heap by sifting it up
	 * @link http://www.php.net/manual/en/splheap.insert.php
	 * @param mixed $value 
	 * @return true Always returns true.
	 */
	public function insert (mixed $value): true {}

	/**
	 * Peeks at the node from the top of the heap
	 * @link http://www.php.net/manual/en/splheap.top.php
	 * @return mixed The value of the node on the top.
	 */
	public function top (): mixed {}

	/**
	 * Counts the number of elements in the heap
	 * @link http://www.php.net/manual/en/splheap.count.php
	 * @return int Returns the number of elements in the heap.
	 */
	public function count (): int {}

	/**
	 * Checks whether the heap is empty
	 * @link http://www.php.net/manual/en/splheap.isempty.php
	 * @return bool Returns whether the heap is empty.
	 */
	public function isEmpty (): bool {}

	/**
	 * Rewind iterator back to the start (no-op)
	 * @link http://www.php.net/manual/en/splheap.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Return current node pointed by the iterator
	 * @link http://www.php.net/manual/en/splheap.current.php
	 * @return mixed The current node value.
	 */
	public function current (): mixed {}

	/**
	 * Return current node index
	 * @link http://www.php.net/manual/en/splheap.key.php
	 * @return int The current node index.
	 */
	public function key (): int {}

	/**
	 * Move to the next node
	 * @link http://www.php.net/manual/en/splheap.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Check whether the heap contains more nodes
	 * @link http://www.php.net/manual/en/splheap.valid.php
	 * @return bool Returns true if the heap contains any more nodes, false otherwise.
	 */
	public function valid (): bool {}

	/**
	 * Recover from the corrupted state and allow further actions on the heap
	 * @link http://www.php.net/manual/en/splheap.recoverfromcorruption.php
	 * @return bool Always returns true.
	 */
	public function recoverFromCorruption (): bool {}

	/**
	 * Tells if the heap is in a corrupted state
	 * @link http://www.php.net/manual/en/splheap.iscorrupted.php
	 * @return bool Returns true if the heap is corrupted, false otherwise.
	 */
	public function isCorrupted (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

}

/**
 * The SplPriorityQueue class provides the main functionalities of a 
 * prioritized queue, implemented using a max heap.
 * @link http://www.php.net/manual/en/class.splpriorityqueue.php
 */
class SplPriorityQueue implements Iterator, Traversable, Countable {
	const EXTR_BOTH = 3;
	const EXTR_PRIORITY = 2;
	const EXTR_DATA = 1;


	/**
	 * Compare priorities in order to place elements correctly in the heap while sifting up
	 * @link http://www.php.net/manual/en/splpriorityqueue.compare.php
	 * @param mixed $priority1 
	 * @param mixed $priority2 
	 * @return int Result of the comparison, positive integer if priority1 is greater than priority2, 0 if they are equal, negative integer otherwise.
	 * <p>Multiple elements with the same priority will get dequeued in no particular order.</p>
	 */
	public function compare (mixed $priority1, mixed $priority2): int {}

	/**
	 * Inserts an element in the queue by sifting it up
	 * @link http://www.php.net/manual/en/splpriorityqueue.insert.php
	 * @param mixed $value 
	 * @param mixed $priority 
	 * @return bool Returns true.
	 */
	public function insert (mixed $value, mixed $priority): bool {}

	/**
	 * Sets the mode of extraction
	 * @link http://www.php.net/manual/en/splpriorityqueue.setextractflags.php
	 * @param int $flags 
	 * @return int Returns the flags of extraction.
	 */
	public function setExtractFlags (int $flags): int {}

	/**
	 * Peeks at the node from the top of the queue
	 * @link http://www.php.net/manual/en/splpriorityqueue.top.php
	 * @return mixed The value or priority (or both) of the top node, depending on the extract flag.
	 */
	public function top (): mixed {}

	/**
	 * Extracts a node from top of the heap and sift up
	 * @link http://www.php.net/manual/en/splpriorityqueue.extract.php
	 * @return mixed The value or priority (or both) of the extracted node, depending on the extract flag.
	 */
	public function extract (): mixed {}

	/**
	 * Counts the number of elements in the queue
	 * @link http://www.php.net/manual/en/splpriorityqueue.count.php
	 * @return int Returns the number of elements in the queue.
	 */
	public function count (): int {}

	/**
	 * Checks whether the queue is empty
	 * @link http://www.php.net/manual/en/splpriorityqueue.isempty.php
	 * @return bool Returns whether the queue is empty.
	 */
	public function isEmpty (): bool {}

	/**
	 * Rewind iterator back to the start (no-op)
	 * @link http://www.php.net/manual/en/splpriorityqueue.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Return current node pointed by the iterator
	 * @link http://www.php.net/manual/en/splpriorityqueue.current.php
	 * @return mixed The value or priority (or both) of the current node, depending on the extract flag.
	 */
	public function current (): mixed {}

	/**
	 * Return current node index
	 * @link http://www.php.net/manual/en/splpriorityqueue.key.php
	 * @return int The current node index.
	 */
	public function key (): int {}

	/**
	 * Move to the next node
	 * @link http://www.php.net/manual/en/splpriorityqueue.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Check whether the queue contains more nodes
	 * @link http://www.php.net/manual/en/splpriorityqueue.valid.php
	 * @return bool Returns true if the queue contains any more nodes, false otherwise.
	 */
	public function valid (): bool {}

	/**
	 * Recover from the corrupted state and allow further actions on the queue
	 * @link http://www.php.net/manual/en/splpriorityqueue.recoverfromcorruption.php
	 * @return bool Always returns true.
	 */
	public function recoverFromCorruption (): bool {}

	/**
	 * Tells if the priority queue is in a corrupted state
	 * @link http://www.php.net/manual/en/splpriorityqueue.iscorrupted.php
	 * @return bool Returns true if the priority queue is corrupted, false otherwise.
	 */
	public function isCorrupted (): bool {}

	/**
	 * Get the flags of extraction
	 * @link http://www.php.net/manual/en/splpriorityqueue.getextractflags.php
	 * @return int Returns the flags of extraction.
	 */
	public function getExtractFlags (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

}

/**
 * The SplFixedArray class provides the main functionalities of array. The 
 * main difference between a SplFixedArray and a normal PHP array is that 
 * the SplFixedArray must be resized manually and allows only integers within 
 * the range as indexes. The advantage is that it uses less memory than
 * a standard array.
 * @link http://www.php.net/manual/en/class.splfixedarray.php
 */
class SplFixedArray implements IteratorAggregate, Traversable, ArrayAccess, Countable, JsonSerializable {

	/**
	 * Constructs a new fixed array
	 * @link http://www.php.net/manual/en/splfixedarray.construct.php
	 * @param int $size [optional] 
	 * @return int 
	 */
	public function __construct (int $size = null): int {}

	/**
	 * Reinitialises the array after being unserialised
	 * @link http://www.php.net/manual/en/splfixedarray.wakeup.php
	 * @return void No value is returned.
	 */
	public function __wakeup (): void {}

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
	 * Returns the size of the array
	 * @link http://www.php.net/manual/en/splfixedarray.count.php
	 * @return int Returns the size of the array.
	 */
	public function count (): int {}

	/**
	 * Returns a PHP array from the fixed array
	 * @link http://www.php.net/manual/en/splfixedarray.toarray.php
	 * @return array Returns a PHP array, similar to the fixed array.
	 */
	public function toArray (): array {}

	/**
	 * Import a PHP array in a SplFixedArray instance
	 * @link http://www.php.net/manual/en/splfixedarray.fromarray.php
	 * @param array $array 
	 * @param bool $preserveKeys [optional] 
	 * @return SplFixedArray Returns an instance of SplFixedArray 
	 * containing the array content.
	 */
	public static function fromArray (array $array, bool $preserveKeys = true): SplFixedArray {}

	/**
	 * Gets the size of the array
	 * @link http://www.php.net/manual/en/splfixedarray.getsize.php
	 * @return int Returns the size of the array, as an int.
	 */
	public function getSize (): int {}

	/**
	 * Change the size of an array
	 * @link http://www.php.net/manual/en/splfixedarray.setsize.php
	 * @param int $size 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setSize (int $size): bool {}

	/**
	 * Returns whether the requested index exists
	 * @link http://www.php.net/manual/en/splfixedarray.offsetexists.php
	 * @param int $index 
	 * @return bool true if the requested index exists, otherwise false
	 */
	public function offsetExists (int $index): bool {}

	/**
	 * Returns the value at the specified index
	 * @link http://www.php.net/manual/en/splfixedarray.offsetget.php
	 * @param int $index 
	 * @return mixed The value at the specified index.
	 */
	public function offsetGet (int $index): mixed {}

	/**
	 * Sets a new value at a specified index
	 * @link http://www.php.net/manual/en/splfixedarray.offsetset.php
	 * @param int $index 
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	public function offsetSet (int $index, mixed $value): void {}

	/**
	 * Unsets the value at the specified $index
	 * @link http://www.php.net/manual/en/splfixedarray.offsetunset.php
	 * @param int $index 
	 * @return void No value is returned.
	 */
	public function offsetUnset (int $index): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): Iterator {}

	/**
	 * {@inheritdoc}
	 */
	public function jsonSerialize (): array {}

}

/**
 * The SplObserver interface is used alongside
 * SplSubject to implement the Observer Design Pattern.
 * @link http://www.php.net/manual/en/class.splobserver.php
 */
interface SplObserver  {

	/**
	 * Receive update from subject
	 * @link http://www.php.net/manual/en/splobserver.update.php
	 * @param SplSubject $subject 
	 * @return void No value is returned.
	 */
	abstract public function update (SplSubject $subject): void;

}

/**
 * The SplSubject interface is used alongside
 * SplObserver to implement the Observer Design Pattern.
 * @link http://www.php.net/manual/en/class.splsubject.php
 */
interface SplSubject  {

	/**
	 * Attach an SplObserver
	 * @link http://www.php.net/manual/en/splsubject.attach.php
	 * @param SplObserver $observer 
	 * @return void No value is returned.
	 */
	abstract public function attach (SplObserver $observer): void;

	/**
	 * Detach an observer
	 * @link http://www.php.net/manual/en/splsubject.detach.php
	 * @param SplObserver $observer 
	 * @return void No value is returned.
	 */
	abstract public function detach (SplObserver $observer): void;

	/**
	 * Notify an observer
	 * @link http://www.php.net/manual/en/splsubject.notify.php
	 * @return void No value is returned.
	 */
	abstract public function notify (): void;

}

/**
 * The SplObjectStorage class provides a map from objects to data or, by
 * ignoring data, an object set. This dual purpose can be useful in many
 * cases involving the need to uniquely identify objects.
 * @link http://www.php.net/manual/en/class.splobjectstorage.php
 */
class SplObjectStorage implements Countable, Iterator, Traversable, Serializable, ArrayAccess {

	/**
	 * Adds an object in the storage
	 * @link http://www.php.net/manual/en/splobjectstorage.attach.php
	 * @param object $object 
	 * @param mixed $info [optional] 
	 * @return void No value is returned.
	 */
	public function attach (object $object, mixed $info = null): void {}

	/**
	 * Removes an object from the storage
	 * @link http://www.php.net/manual/en/splobjectstorage.detach.php
	 * @param object $object 
	 * @return void No value is returned.
	 */
	public function detach (object $object): void {}

	/**
	 * Checks if the storage contains a specific object
	 * @link http://www.php.net/manual/en/splobjectstorage.contains.php
	 * @param object $object 
	 * @return bool Returns true if the object is in the storage, false otherwise.
	 */
	public function contains (object $object): bool {}

	/**
	 * Adds all objects from another storage
	 * @link http://www.php.net/manual/en/splobjectstorage.addall.php
	 * @param SplObjectStorage $storage 
	 * @return int The number of objects in the storage.
	 */
	public function addAll (SplObjectStorage $storage): int {}

	/**
	 * Removes objects contained in another storage from the current storage
	 * @link http://www.php.net/manual/en/splobjectstorage.removeall.php
	 * @param SplObjectStorage $storage 
	 * @return int Returns the number of remaining objects.
	 */
	public function removeAll (SplObjectStorage $storage): int {}

	/**
	 * Removes all objects except for those contained in another storage from the current storage
	 * @link http://www.php.net/manual/en/splobjectstorage.removeallexcept.php
	 * @param SplObjectStorage $storage 
	 * @return int Returns the number of remaining objects.
	 */
	public function removeAllExcept (SplObjectStorage $storage): int {}

	/**
	 * Returns the data associated with the current iterator entry
	 * @link http://www.php.net/manual/en/splobjectstorage.getinfo.php
	 * @return mixed The data associated with the current iterator position.
	 */
	public function getInfo (): mixed {}

	/**
	 * Sets the data associated with the current iterator entry
	 * @link http://www.php.net/manual/en/splobjectstorage.setinfo.php
	 * @param mixed $info 
	 * @return void No value is returned.
	 */
	public function setInfo (mixed $info): void {}

	/**
	 * Returns the number of objects in the storage
	 * @link http://www.php.net/manual/en/splobjectstorage.count.php
	 * @param int $mode [optional] 
	 * @return int The number of objects in the storage.
	 */
	public function count (int $mode = COUNT_NORMAL): int {}

	/**
	 * Rewind the iterator to the first storage element
	 * @link http://www.php.net/manual/en/splobjectstorage.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Returns if the current iterator entry is valid
	 * @link http://www.php.net/manual/en/splobjectstorage.valid.php
	 * @return bool Returns true if the iterator entry is valid, false otherwise.
	 */
	public function valid (): bool {}

	/**
	 * Returns the index at which the iterator currently is
	 * @link http://www.php.net/manual/en/splobjectstorage.key.php
	 * @return int The index corresponding to the position of the iterator.
	 */
	public function key (): int {}

	/**
	 * Returns the current storage entry
	 * @link http://www.php.net/manual/en/splobjectstorage.current.php
	 * @return object The object at the current iterator position.
	 */
	public function current (): object {}

	/**
	 * Move to the next entry
	 * @link http://www.php.net/manual/en/splobjectstorage.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Unserializes a storage from its string representation
	 * @link http://www.php.net/manual/en/splobjectstorage.unserialize.php
	 * @param string $data 
	 * @return void No value is returned.
	 */
	public function unserialize (string $data): void {}

	/**
	 * Serializes the storage
	 * @link http://www.php.net/manual/en/splobjectstorage.serialize.php
	 * @return string A string representing the storage.
	 */
	public function serialize (): string {}

	/**
	 * Checks whether an object exists in the storage
	 * @link http://www.php.net/manual/en/splobjectstorage.offsetexists.php
	 * @param object $object 
	 * @return bool Returns true if the object exists in the storage,
	 * and false otherwise.
	 */
	public function offsetExists (object $object): bool {}

	/**
	 * Returns the data associated with an object
	 * @link http://www.php.net/manual/en/splobjectstorage.offsetget.php
	 * @param object $object 
	 * @return mixed The data previously associated with the object in the storage.
	 */
	public function offsetGet (object $object): mixed {}

	/**
	 * Associates data to an object in the storage
	 * @link http://www.php.net/manual/en/splobjectstorage.offsetset.php
	 * @param object $object 
	 * @param mixed $info [optional] 
	 * @return void No value is returned.
	 */
	public function offsetSet (object $object, mixed $info = null): void {}

	/**
	 * Removes an object from the storage
	 * @link http://www.php.net/manual/en/splobjectstorage.offsetunset.php
	 * @param object $object 
	 * @return void No value is returned.
	 */
	public function offsetUnset (object $object): void {}

	/**
	 * Calculate a unique identifier for the contained objects
	 * @link http://www.php.net/manual/en/splobjectstorage.gethash.php
	 * @param object $object The object whose identifier is to be calculated.
	 * @return string A string with the calculated identifier.
	 */
	public function getHash (object $object): string {}

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

/**
 * An Iterator that sequentially iterates over all attached iterators
 * @link http://www.php.net/manual/en/class.multipleiterator.php
 */
class MultipleIterator implements Iterator, Traversable {
	/**
	 * Do not require all sub iterators to be valid in iteration.
	const MIT_NEED_ANY = 0;
	/**
	 * Require all sub iterators to be valid in iteration.
	const MIT_NEED_ALL = 1;
	/**
	 * Keys are created from the sub iterators position.
	const MIT_KEYS_NUMERIC = 0;
	/**
	 * Keys are created from sub iterators associated information.
	const MIT_KEYS_ASSOC = 2;


	/**
	 * Constructs a new MultipleIterator
	 * @link http://www.php.net/manual/en/multipleiterator.construct.php
	 * @param int $flags [optional] 
	 * @return int 
	 */
	public function __construct (int $flags = 'MultipleIterator::MIT_NEED_ALL | MultipleIterator::MIT_KEYS_NUMERIC'): int {}

	/**
	 * Gets the flag information
	 * @link http://www.php.net/manual/en/multipleiterator.getflags.php
	 * @return int Information about the flags, as an int.
	 */
	public function getFlags (): int {}

	/**
	 * Sets flags
	 * @link http://www.php.net/manual/en/multipleiterator.setflags.php
	 * @param int $flags 
	 * @return void No value is returned.
	 */
	public function setFlags (int $flags): void {}

	/**
	 * Attaches iterator information
	 * @link http://www.php.net/manual/en/multipleiterator.attachiterator.php
	 * @param Iterator $iterator 
	 * @param string|int|null $info [optional] 
	 * @return void Description...
	 */
	public function attachIterator (Iterator $iterator, string|int|null $info = null): void {}

	/**
	 * Detaches an iterator
	 * @link http://www.php.net/manual/en/multipleiterator.detachiterator.php
	 * @param Iterator $iterator 
	 * @return void No value is returned.
	 */
	public function detachIterator (Iterator $iterator): void {}

	/**
	 * Checks if an iterator is attached
	 * @link http://www.php.net/manual/en/multipleiterator.containsiterator.php
	 * @param Iterator $iterator 
	 * @return bool Returns true on success or false on failure.
	 */
	public function containsIterator (Iterator $iterator): bool {}

	/**
	 * Gets the number of attached iterator instances
	 * @link http://www.php.net/manual/en/multipleiterator.countiterators.php
	 * @return int The number of attached iterator instances (as an int).
	 */
	public function countIterators (): int {}

	/**
	 * Rewinds all attached iterator instances
	 * @link http://www.php.net/manual/en/multipleiterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Checks the validity of sub iterators
	 * @link http://www.php.net/manual/en/multipleiterator.valid.php
	 * @return bool Returns true if one or all sub iterators are valid depending on flags,
	 * otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Gets the registered iterator instances
	 * @link http://www.php.net/manual/en/multipleiterator.key.php
	 * @return array An array of all registered iterator instances.
	 */
	public function key (): array {}

	/**
	 * Gets the registered iterator instances
	 * @link http://www.php.net/manual/en/multipleiterator.current.php
	 * @return array An array containing the current values of each attached iterator.
	 */
	public function current (): array {}

	/**
	 * Moves all attached iterator instances forward
	 * @link http://www.php.net/manual/en/multipleiterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

}

/**
 * Return the interfaces which are implemented by the given class or interface
 * @link http://www.php.net/manual/en/function.class-implements.php
 * @param object|string $object_or_class 
 * @param bool $autoload [optional] 
 * @return array|false An array on success, or false when the given class doesn't exist.
 */
function class_implements (object|string $object_or_class, bool $autoload = true): array|false {}

/**
 * Return the parent classes of the given class
 * @link http://www.php.net/manual/en/function.class-parents.php
 * @param object|string $object_or_class 
 * @param bool $autoload [optional] 
 * @return array|false An array on success, or false when the given class doesn't exist.
 */
function class_parents (object|string $object_or_class, bool $autoload = true): array|false {}

/**
 * Return the traits used by the given class
 * @link http://www.php.net/manual/en/function.class-uses.php
 * @param object|string $object_or_class 
 * @param bool $autoload [optional] 
 * @return array|false An array on success, or false when the given class doesn't exist.
 */
function class_uses (object|string $object_or_class, bool $autoload = true): array|false {}

/**
 * Default implementation for __autoload()
 * @link http://www.php.net/manual/en/function.spl-autoload.php
 * @param string $class 
 * @param string|null $file_extensions [optional] 
 * @return void No value is returned.
 */
function spl_autoload (string $class, ?string $file_extensions = null): void {}

/**
 * Try all registered __autoload() functions to load the requested class
 * @link http://www.php.net/manual/en/function.spl-autoload-call.php
 * @param string $class 
 * @return void No value is returned.
 */
function spl_autoload_call (string $class): void {}

/**
 * Register and return default file extensions for spl_autoload
 * @link http://www.php.net/manual/en/function.spl-autoload-extensions.php
 * @param string|null $file_extensions [optional] 
 * @return string A comma delimited list of default file extensions for
 * spl_autoload.
 */
function spl_autoload_extensions (?string $file_extensions = null): string {}

/**
 * Return all registered __autoload() functions
 * @link http://www.php.net/manual/en/function.spl-autoload-functions.php
 * @return array An array of all registered __autoload functions.
 * If no function is registered, or the autoload queue is not activated,
 * then the return value will be an empty array.
 */
function spl_autoload_functions (): array {}

/**
 * Register given function as __autoload() implementation
 * @link http://www.php.net/manual/en/function.spl-autoload-register.php
 * @param callable|null $callback [optional] 
 * @param bool $throw [optional] 
 * @param bool $prepend [optional] 
 * @return bool Returns true on success or false on failure.
 */
function spl_autoload_register (?callable $callback = null, bool $throw = true, bool $prepend = false): bool {}

/**
 * Unregister given function as __autoload() implementation
 * @link http://www.php.net/manual/en/function.spl-autoload-unregister.php
 * @param callable $callback 
 * @return bool Returns true on success or false on failure.
 */
function spl_autoload_unregister (callable $callback): bool {}

/**
 * Return available SPL classes
 * @link http://www.php.net/manual/en/function.spl-classes.php
 * @return array Returns an array containing the currently available SPL classes.
 */
function spl_classes (): array {}

/**
 * Return hash id for given object
 * @link http://www.php.net/manual/en/function.spl-object-hash.php
 * @param object $object 
 * @return string A string that is unique for each currently existing object and is always
 * the same for each object.
 */
function spl_object_hash (object $object): string {}

/**
 * Return the integer object handle for given object
 * @link http://www.php.net/manual/en/function.spl-object-id.php
 * @param object $object 
 * @return int An integer identifier that is unique for each currently existing object and
 * is always the same for each object.
 */
function spl_object_id (object $object): int {}

/**
 * Call a function for every element in an iterator
 * @link http://www.php.net/manual/en/function.iterator-apply.php
 * @param Traversable $iterator 
 * @param callable $callback 
 * @param array|null $args [optional] 
 * @return int Returns the iteration count.
 */
function iterator_apply (Traversable $iterator, callable $callback, ?array $args = null): int {}

/**
 * Count the elements in an iterator
 * @link http://www.php.net/manual/en/function.iterator-count.php
 * @param Traversable|array $iterator 
 * @return int The number of elements in iterator.
 */
function iterator_count (Traversable|array $iterator): int {}

/**
 * Copy the iterator into an array
 * @link http://www.php.net/manual/en/function.iterator-to-array.php
 * @param Traversable|array $iterator 
 * @param bool $preserve_keys [optional] 
 * @return array An array containing the elements of the iterator.
 */
function iterator_to_array (Traversable|array $iterator, bool $preserve_keys = true): array {}

// End of SPL v.8.2.6
