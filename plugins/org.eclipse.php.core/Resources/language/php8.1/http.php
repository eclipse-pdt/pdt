<?php

// Start of http v.4.2.3

namespace http {

interface Exception  {
}


}


namespace http\Exception {

class RuntimeException extends \RuntimeException implements \Stringable, \Throwable, \http\Exception {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param \Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?\Throwable $previous = null): string {}

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
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

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

class UnexpectedValueException extends \UnexpectedValueException implements \Throwable, \Stringable, \http\Exception {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param \Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?\Throwable $previous = null): string {}

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
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

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

class BadMethodCallException extends \BadMethodCallException implements \Stringable, \Throwable, \http\Exception {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param \Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?\Throwable $previous = null): string {}

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
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

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

class InvalidArgumentException extends \InvalidArgumentException implements \Throwable, \Stringable, \http\Exception {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param \Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?\Throwable $previous = null): string {}

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
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

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

class BadHeaderException extends \DomainException implements \Throwable, \Stringable, \http\Exception {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param \Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?\Throwable $previous = null): string {}

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
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

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

class BadUrlException extends \DomainException implements \Throwable, \Stringable, \http\Exception {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param \Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?\Throwable $previous = null): string {}

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
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

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

class BadMessageException extends \DomainException implements \Throwable, \Stringable, \http\Exception {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param \Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?\Throwable $previous = null): string {}

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
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

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

class BadConversionException extends \DomainException implements \Throwable, \Stringable, \http\Exception {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param \Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?\Throwable $previous = null): string {}

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
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

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

class BadQueryStringException extends \DomainException implements \Throwable, \Stringable, \http\Exception {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param \Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?\Throwable $previous = null): string {}

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
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

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


}


namespace http {

class Cookie implements \Stringable {
	const PARSE_RAW = 1;
	const SECURE = 16;
	const HTTPONLY = 32;


	/**
	 * {@inheritdoc}
	 * @param mixed $cookie_string [optional]
	 * @param mixed $parser_flags [optional]
	 * @param mixed $allowed_extras [optional]
	 */
	public function __construct ($cookie_string = NULL, $parser_flags = NULL, $allowed_extras = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getCookies () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $cookies [optional]
	 */
	public function setCookies ($cookies = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $cookies
	 */
	public function addCookies ($cookies = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name
	 */
	public function getCookie ($name = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $cookie_name
	 * @param mixed $cookie_value [optional]
	 */
	public function setCookie ($cookie_name = null, $cookie_value = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $cookie_name
	 * @param mixed $cookie_value
	 */
	public function addCookie ($cookie_name = null, $cookie_value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtras () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $extras [optional]
	 */
	public function setExtras ($extras = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $extras
	 */
	public function addExtras ($extras = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name
	 */
	public function getExtra ($name = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $extra_name
	 * @param mixed $extra_value [optional]
	 */
	public function setExtra ($extra_name = null, $extra_value = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $extra_name
	 * @param mixed $extra_value
	 */
	public function addExtra ($extra_name = null, $extra_value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getDomain () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value [optional]
	 */
	public function setDomain ($value = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getPath () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value [optional]
	 */
	public function setPath ($value = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getExpires () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value [optional]
	 */
	public function setExpires ($value = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getMaxAge () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value [optional]
	 */
	public function setMaxAge ($value = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value [optional]
	 */
	public function setFlags ($value = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function toArray () {}

	/**
	 * {@inheritdoc}
	 */
	public function toString () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}


}


namespace http\Encoding {

abstract class Stream  {
	const FLUSH_NONE = 0;
	const FLUSH_SYNC = 1048576;
	const FLUSH_FULL = 2097152;


	/**
	 * {@inheritdoc}
	 * @param mixed $flags [optional]
	 */
	public function __construct ($flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $data
	 */
	public function update ($data = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function flush () {}

	/**
	 * {@inheritdoc}
	 */
	public function done () {}

	/**
	 * {@inheritdoc}
	 */
	public function finish () {}

}


}


namespace http\Encoding\Stream {

class Dechunk extends \http\Encoding\Stream  {
	const FLUSH_NONE = 0;
	const FLUSH_SYNC = 1048576;
	const FLUSH_FULL = 2097152;


	/**
	 * {@inheritdoc}
	 * @param mixed $data
	 * @param mixed $decoded_len [optional]
	 */
	public static function decode ($data = null, &$decoded_len = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $flags [optional]
	 */
	public function __construct ($flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $data
	 */
	public function update ($data = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function flush () {}

	/**
	 * {@inheritdoc}
	 */
	public function done () {}

	/**
	 * {@inheritdoc}
	 */
	public function finish () {}

}

class Deflate extends \http\Encoding\Stream  {
	const FLUSH_NONE = 0;
	const FLUSH_SYNC = 1048576;
	const FLUSH_FULL = 2097152;
	const TYPE_GZIP = 16;
	const TYPE_ZLIB = 0;
	const TYPE_RAW = 32;
	const LEVEL_DEF = 0;
	const LEVEL_MIN = 1;
	const LEVEL_MAX = 9;
	const STRATEGY_DEF = 0;
	const STRATEGY_FILT = 256;
	const STRATEGY_HUFF = 512;
	const STRATEGY_RLE = 768;
	const STRATEGY_FIXED = 1024;


	/**
	 * {@inheritdoc}
	 * @param mixed $data
	 * @param mixed $flags [optional]
	 */
	public static function encode ($data = null, $flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $flags [optional]
	 */
	public function __construct ($flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $data
	 */
	public function update ($data = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function flush () {}

	/**
	 * {@inheritdoc}
	 */
	public function done () {}

	/**
	 * {@inheritdoc}
	 */
	public function finish () {}

}

class Inflate extends \http\Encoding\Stream  {
	const FLUSH_NONE = 0;
	const FLUSH_SYNC = 1048576;
	const FLUSH_FULL = 2097152;


	/**
	 * {@inheritdoc}
	 * @param mixed $data
	 */
	public static function decode ($data = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $flags [optional]
	 */
	public function __construct ($flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $data
	 */
	public function update ($data = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function flush () {}

	/**
	 * {@inheritdoc}
	 */
	public function done () {}

	/**
	 * {@inheritdoc}
	 */
	public function finish () {}

}

class Enbrotli extends \http\Encoding\Stream  {
	const FLUSH_NONE = 0;
	const FLUSH_SYNC = 1048576;
	const FLUSH_FULL = 2097152;
	const LEVEL_MIN = 1;
	const LEVEL_DEF = 4;
	const LEVEL_MAX = 11;
	const WBITS_MIN = 160;
	const WBITS_DEF = 352;
	const WBITS_MAX = 384;
	const MODE_GENERIC = 0;
	const MODE_TEXT = 4096;
	const MODE_FONT = 8192;


	/**
	 * {@inheritdoc}
	 * @param mixed $data
	 * @param mixed $flags [optional]
	 */
	public static function encode ($data = null, $flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $flags [optional]
	 */
	public function __construct ($flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $data
	 */
	public function update ($data = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function flush () {}

	/**
	 * {@inheritdoc}
	 */
	public function done () {}

	/**
	 * {@inheritdoc}
	 */
	public function finish () {}

}

class Debrotli extends \http\Encoding\Stream  {
	const FLUSH_NONE = 0;
	const FLUSH_SYNC = 1048576;
	const FLUSH_FULL = 2097152;


	/**
	 * {@inheritdoc}
	 * @param mixed $data
	 */
	public static function decode ($data = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $flags [optional]
	 */
	public function __construct ($flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $data
	 */
	public function update ($data = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function flush () {}

	/**
	 * {@inheritdoc}
	 */
	public function done () {}

	/**
	 * {@inheritdoc}
	 */
	public function finish () {}

}


}


namespace http {

class Header implements \Stringable, \Serializable {
	const MATCH_LOOSE = 0;
	const MATCH_CASE = 1;
	const MATCH_WORD = 16;
	const MATCH_FULL = 32;
	const MATCH_STRICT = 33;


	public $name;

	public $value;

	/**
	 * {@inheritdoc}
	 * @param mixed $name [optional]
	 * @param mixed $value [optional]
	 */
	public function __construct ($name = NULL, $value = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 * @param mixed $serialized
	 */
	public function unserialize ($serialized = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function serialize () {}

	/**
	 * {@inheritdoc}
	 */
	public function toString () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 * @param mixed $flags [optional]
	 */
	public function match ($value = null, $flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $supported
	 * @param mixed $result [optional]
	 */
	public function negotiate ($supported = null, &$result = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $param_sep [optional]
	 * @param mixed $arg_sep [optional]
	 * @param mixed $val_sep [optional]
	 * @param mixed $flags [optional]
	 */
	public function getParams ($param_sep = NULL, $arg_sep = NULL, $val_sep = NULL, $flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $string
	 * @param mixed $header_class [optional]
	 */
	public static function parse ($string = null, $header_class = NULL) {}

}


}


namespace http\Header {

class Parser  {
	const CLEANUP = 1;
	const STATE_FAILURE = -1;
	const STATE_START = 0;
	const STATE_KEY = 1;
	const STATE_VALUE = 2;
	const STATE_VALUE_EX = 3;
	const STATE_HEADER_DONE = 4;
	const STATE_DONE = 5;


	/**
	 * {@inheritdoc}
	 */
	public function getState () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $data
	 * @param mixed $flags
	 * @param array|null $headers
	 */
	public function parse ($data = null, $flags = null, ?array &$headers = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $stream
	 * @param mixed $flags
	 * @param array|null $headers
	 */
	public function stream ($stream = null, $flags = null, ?array &$headers = null) {}

}


}


namespace http {

class Message implements \Stringable, \Countable, \Serializable, \Iterator, \Traversable {
	const TYPE_NONE = 0;
	const TYPE_REQUEST = 1;
	const TYPE_RESPONSE = 2;


	protected $type;

	protected $body;

	protected $requestMethod;

	protected $requestUrl;

	protected $responseStatus;

	protected $responseCode;

	protected $httpVersion;

	protected $headers;

	protected $parentMessage;

	/**
	 * {@inheritdoc}
	 * @param mixed $message [optional]
	 * @param mixed $greedy [optional]
	 */
	public function __construct ($message = NULL, $greedy = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getBody () {}

	/**
	 * {@inheritdoc}
	 * @param \http\Message\Body $body
	 */
	public function setBody (\http\Message\Body $body) {}

	/**
	 * {@inheritdoc}
	 * @param \http\Message\Body $body
	 */
	public function addBody (\http\Message\Body $body) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header
	 * @param mixed $into_class [optional]
	 */
	public function getHeader ($header = null, $into_class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header
	 * @param mixed $value [optional]
	 */
	public function setHeader ($header = null, $value = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header
	 * @param mixed $value
	 */
	public function addHeader ($header = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getHeaders () {}

	/**
	 * {@inheritdoc}
	 * @param array|null $headers
	 */
	public function setHeaders (?array $headers = null) {}

	/**
	 * {@inheritdoc}
	 * @param array $headers
	 * @param mixed $append [optional]
	 */
	public function addHeaders (array $headers, $append = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $type
	 */
	public function setType ($type = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getInfo () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $http_info
	 */
	public function setInfo ($http_info = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getResponseCode () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $response_code
	 * @param mixed $strict [optional]
	 */
	public function setResponseCode ($response_code = null, $strict = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getResponseStatus () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $response_status
	 */
	public function setResponseStatus ($response_status = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getRequestMethod () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $request_method
	 */
	public function setRequestMethod ($request_method = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getRequestUrl () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $url
	 */
	public function setRequestUrl ($url = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getHttpVersion () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $http_version
	 */
	public function setHttpVersion ($http_version = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getParentMessage () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $include_parent [optional]
	 */
	public function toString ($include_parent = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $callback
	 */
	public function toCallback ($callback = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $stream
	 */
	public function toStream ($stream = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function serialize () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $serialized
	 */
	public function unserialize ($serialized = null) {}

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
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function detach () {}

	/**
	 * {@inheritdoc}
	 * @param \http\Message $message
	 * @param mixed $top [optional]
	 */
	public function prepend (\http\Message $message, $top = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function reverse () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $boundary [optional]
	 */
	public function isMultipart (&$boundary = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function splitMultipartBody () {}

}


}


namespace http\Message {

class Parser  {
	const CLEANUP = 1;
	const DUMB_BODIES = 2;
	const EMPTY_REDIRECTS = 4;
	const GREEDY = 8;
	const STATE_FAILURE = -1;
	const STATE_START = 0;
	const STATE_HEADER = 1;
	const STATE_HEADER_DONE = 2;
	const STATE_BODY = 3;
	const STATE_BODY_DUMB = 4;
	const STATE_BODY_LENGTH = 5;
	const STATE_BODY_CHUNKED = 6;
	const STATE_BODY_DONE = 7;
	const STATE_UPDATE_CL = 8;
	const STATE_DONE = 9;


	/**
	 * {@inheritdoc}
	 */
	public function getState () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $data
	 * @param mixed $flags
	 * @param mixed $message
	 */
	public function parse ($data = null, $flags = null, &$message = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $stream
	 * @param mixed $flags
	 * @param mixed $message
	 */
	public function stream ($stream = null, $flags = null, &$message = null) {}

}

class Body implements \Stringable, \Serializable {

	/**
	 * {@inheritdoc}
	 * @param mixed $stream [optional]
	 */
	public function __construct ($stream = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function serialize () {}

	/**
	 * {@inheritdoc}
	 */
	public function toString () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param mixed $serialized
	 */
	public function unserialize ($serialized = null) {}

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
	 * @param mixed $stream
	 * @param mixed $offset [optional]
	 * @param mixed $maxlen [optional]
	 */
	public function toStream ($stream = null, $offset = NULL, $maxlen = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $callback
	 * @param mixed $offset [optional]
	 * @param mixed $maxlen [optional]
	 */
	public function toCallback ($callback = null, $offset = NULL, $maxlen = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getResource () {}

	/**
	 * {@inheritdoc}
	 */
	public function getBoundary () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $string
	 */
	public function append ($string = null) {}

	/**
	 * {@inheritdoc}
	 * @param array|null $fields [optional]
	 * @param array|null $files [optional]
	 */
	public function addForm (?array $fields = NULL, ?array $files = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param \http\Message $message
	 */
	public function addPart (\http\Message $message) {}

	/**
	 * {@inheritdoc}
	 */
	public function etag () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $field [optional]
	 */
	public function stat ($field = NULL) {}

}


}


namespace http {

class QueryString implements \Stringable, \Serializable, \ArrayAccess, \IteratorAggregate, \Traversable {
	const TYPE_BOOL = 18;
	const TYPE_INT = 4;
	const TYPE_FLOAT = 5;
	const TYPE_STRING = 6;
	const TYPE_ARRAY = 7;
	const TYPE_OBJECT = 8;


	static $instance;

	/**
	 * {@inheritdoc}
	 * @param mixed $params [optional]
	 */
	final public function __construct ($params = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function toArray () {}

	/**
	 * {@inheritdoc}
	 */
	public function toString () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name [optional]
	 * @param mixed $type [optional]
	 * @param mixed $defval [optional]
	 * @param mixed $delete [optional]
	 */
	public function get ($name = NULL, $type = NULL, $defval = NULL, $delete = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $params
	 */
	public function set ($params = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $params [optional]
	 */
	public function mod ($params = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name
	 * @param mixed $defval [optional]
	 * @param mixed $delete [optional]
	 */
	public function getBool ($name = null, $defval = NULL, $delete = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name
	 * @param mixed $defval [optional]
	 * @param mixed $delete [optional]
	 */
	public function getInt ($name = null, $defval = NULL, $delete = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name
	 * @param mixed $defval [optional]
	 * @param mixed $delete [optional]
	 */
	public function getFloat ($name = null, $defval = NULL, $delete = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name
	 * @param mixed $defval [optional]
	 * @param mixed $delete [optional]
	 */
	public function getString ($name = null, $defval = NULL, $delete = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name
	 * @param mixed $defval [optional]
	 * @param mixed $delete [optional]
	 */
	public function getArray ($name = null, $defval = NULL, $delete = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name
	 * @param mixed $defval [optional]
	 * @param mixed $delete [optional]
	 */
	public function getObject ($name = null, $defval = NULL, $delete = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): \Traversable {}

	/**
	 * {@inheritdoc}
	 */
	public static function getGlobalInstance () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $from_encoding
	 * @param mixed $to_encoding
	 */
	public function xlate ($from_encoding = null, $to_encoding = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function serialize () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $serialized
	 */
	public function unserialize ($serialized = null) {}

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
	 * @param mixed $name
	 */
	public function offsetGet ($name = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name
	 * @param mixed $value
	 */
	public function offsetSet ($name = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name
	 */
	public function offsetExists ($name = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name
	 */
	public function offsetUnset ($name = null) {}

}

class Client implements \SplSubject, \Countable {
	const DEBUG_INFO = 0;
	const DEBUG_IN = 1;
	const DEBUG_OUT = 2;
	const DEBUG_HEADER = 16;
	const DEBUG_BODY = 32;
	const DEBUG_SSL = 64;


	protected $options;

	protected $history;

	public $recordHistory;

	/**
	 * {@inheritdoc}
	 * @param mixed $driver [optional]
	 * @param mixed $persistent_handle_id [optional]
	 */
	public function __construct ($driver = NULL, $persistent_handle_id = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function reset () {}

	/**
	 * {@inheritdoc}
	 * @param \http\Client\Request $request
	 * @param mixed $callable [optional]
	 */
	public function enqueue (\http\Client\Request $request, $callable = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param \http\Client\Request $request
	 */
	public function dequeue (\http\Client\Request $request) {}

	/**
	 * {@inheritdoc}
	 * @param \http\Client\Request $request
	 * @param mixed $callable [optional]
	 */
	public function requeue (\http\Client\Request $request, $callable = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function send () {}

	/**
	 * {@inheritdoc}
	 */
	public function once () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $timeout [optional]
	 */
	public function wait ($timeout = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param \http\Client\Request|null $request [optional]
	 */
	public function getResponse (?\http\Client\Request $request = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getHistory () {}

	/**
	 * {@inheritdoc}
	 * @param array|null $settings
	 */
	public function configure (?array $settings = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $enable [optional]
	 * @deprecated 
	 */
	public function enablePipelining ($enable = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $enable [optional]
	 * @deprecated 
	 */
	public function enableEvents ($enable = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param \http\Client\Request|null $request [optional]
	 * @param mixed $progress [optional]
	 */
	public function notify (?\http\Client\Request $request = NULL, $progress = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param \SplObserver $observer
	 */
	public function attach (\SplObserver $observer) {}

	/**
	 * {@inheritdoc}
	 * @param \SplObserver $observer
	 */
	public function detach (\SplObserver $observer) {}

	/**
	 * {@inheritdoc}
	 */
	public function getObservers () {}

	/**
	 * {@inheritdoc}
	 * @param \http\Client\Request $request
	 */
	public function getProgressInfo (\http\Client\Request $request) {}

	/**
	 * {@inheritdoc}
	 * @param \http\Client\Request $request
	 */
	public function getTransferInfo (\http\Client\Request $request) {}

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	public function setOptions (?array $options = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getOptions () {}

	/**
	 * {@inheritdoc}
	 * @param array|null $ssl_option [optional]
	 */
	public function setSslOptions (?array $ssl_option = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param array|null $ssl_options [optional]
	 */
	public function addSslOptions (?array $ssl_options = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getSslOptions () {}

	/**
	 * {@inheritdoc}
	 * @param array|null $cookies [optional]
	 */
	public function setCookies (?array $cookies = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param array|null $cookies [optional]
	 */
	public function addCookies (?array $cookies = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getCookies () {}

	/**
	 * {@inheritdoc}
	 */
	public static function getAvailableDrivers () {}

	/**
	 * {@inheritdoc}
	 */
	public function getAvailableOptions () {}

	/**
	 * {@inheritdoc}
	 */
	public function getAvailableConfiguration () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $callback
	 */
	public function setDebug ($callback = null) {}

}


}


namespace http\Client {

class Request extends \http\Message implements \Traversable, \Iterator, \Serializable, \Countable, \Stringable {
	const TYPE_NONE = 0;
	const TYPE_REQUEST = 1;
	const TYPE_RESPONSE = 2;


	protected $options;

	/**
	 * {@inheritdoc}
	 * @param mixed $method [optional]
	 * @param mixed $url [optional]
	 * @param array|null $headers [optional]
	 * @param \http\Message\Body|null $body [optional]
	 */
	public function __construct ($method = NULL, $url = NULL, ?array $headers = NULL, ?\http\Message\Body $body = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $content_type
	 */
	public function setContentType ($content_type = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getContentType () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $query_data [optional]
	 */
	public function setQuery ($query_data = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getQuery () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $query_data
	 */
	public function addQuery ($query_data = null) {}

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	public function setOptions (?array $options = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getOptions () {}

	/**
	 * {@inheritdoc}
	 * @param array|null $ssl_options [optional]
	 */
	public function setSslOptions (?array $ssl_options = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getSslOptions () {}

	/**
	 * {@inheritdoc}
	 * @param array|null $ssl_options [optional]
	 */
	public function addSslOptions (?array $ssl_options = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getBody () {}

	/**
	 * {@inheritdoc}
	 * @param \http\Message\Body $body
	 */
	public function setBody (\http\Message\Body $body) {}

	/**
	 * {@inheritdoc}
	 * @param \http\Message\Body $body
	 */
	public function addBody (\http\Message\Body $body) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header
	 * @param mixed $into_class [optional]
	 */
	public function getHeader ($header = null, $into_class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header
	 * @param mixed $value [optional]
	 */
	public function setHeader ($header = null, $value = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header
	 * @param mixed $value
	 */
	public function addHeader ($header = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getHeaders () {}

	/**
	 * {@inheritdoc}
	 * @param array|null $headers
	 */
	public function setHeaders (?array $headers = null) {}

	/**
	 * {@inheritdoc}
	 * @param array $headers
	 * @param mixed $append [optional]
	 */
	public function addHeaders (array $headers, $append = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $type
	 */
	public function setType ($type = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getInfo () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $http_info
	 */
	public function setInfo ($http_info = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getResponseCode () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $response_code
	 * @param mixed $strict [optional]
	 */
	public function setResponseCode ($response_code = null, $strict = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getResponseStatus () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $response_status
	 */
	public function setResponseStatus ($response_status = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getRequestMethod () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $request_method
	 */
	public function setRequestMethod ($request_method = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getRequestUrl () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $url
	 */
	public function setRequestUrl ($url = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getHttpVersion () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $http_version
	 */
	public function setHttpVersion ($http_version = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getParentMessage () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $include_parent [optional]
	 */
	public function toString ($include_parent = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $callback
	 */
	public function toCallback ($callback = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $stream
	 */
	public function toStream ($stream = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function serialize () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $serialized
	 */
	public function unserialize ($serialized = null) {}

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
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function detach () {}

	/**
	 * {@inheritdoc}
	 * @param \http\Message $message
	 * @param mixed $top [optional]
	 */
	public function prepend (\http\Message $message, $top = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function reverse () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $boundary [optional]
	 */
	public function isMultipart (&$boundary = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function splitMultipartBody () {}

}

class Response extends \http\Message implements \Traversable, \Iterator, \Serializable, \Countable, \Stringable {
	const TYPE_NONE = 0;
	const TYPE_REQUEST = 1;
	const TYPE_RESPONSE = 2;


	protected $transferInfo;

	/**
	 * {@inheritdoc}
	 * @param mixed $flags [optional]
	 * @param mixed $allowed_extras [optional]
	 */
	public function getCookies ($flags = NULL, $allowed_extras = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $element [optional]
	 */
	public function getTransferInfo ($element = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $message [optional]
	 * @param mixed $greedy [optional]
	 */
	public function __construct ($message = NULL, $greedy = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getBody () {}

	/**
	 * {@inheritdoc}
	 * @param \http\Message\Body $body
	 */
	public function setBody (\http\Message\Body $body) {}

	/**
	 * {@inheritdoc}
	 * @param \http\Message\Body $body
	 */
	public function addBody (\http\Message\Body $body) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header
	 * @param mixed $into_class [optional]
	 */
	public function getHeader ($header = null, $into_class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header
	 * @param mixed $value [optional]
	 */
	public function setHeader ($header = null, $value = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header
	 * @param mixed $value
	 */
	public function addHeader ($header = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getHeaders () {}

	/**
	 * {@inheritdoc}
	 * @param array|null $headers
	 */
	public function setHeaders (?array $headers = null) {}

	/**
	 * {@inheritdoc}
	 * @param array $headers
	 * @param mixed $append [optional]
	 */
	public function addHeaders (array $headers, $append = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $type
	 */
	public function setType ($type = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getInfo () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $http_info
	 */
	public function setInfo ($http_info = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getResponseCode () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $response_code
	 * @param mixed $strict [optional]
	 */
	public function setResponseCode ($response_code = null, $strict = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getResponseStatus () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $response_status
	 */
	public function setResponseStatus ($response_status = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getRequestMethod () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $request_method
	 */
	public function setRequestMethod ($request_method = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getRequestUrl () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $url
	 */
	public function setRequestUrl ($url = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getHttpVersion () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $http_version
	 */
	public function setHttpVersion ($http_version = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getParentMessage () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $include_parent [optional]
	 */
	public function toString ($include_parent = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $callback
	 */
	public function toCallback ($callback = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $stream
	 */
	public function toStream ($stream = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function serialize () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $serialized
	 */
	public function unserialize ($serialized = null) {}

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
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function detach () {}

	/**
	 * {@inheritdoc}
	 * @param \http\Message $message
	 * @param mixed $top [optional]
	 */
	public function prepend (\http\Message $message, $top = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function reverse () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $boundary [optional]
	 */
	public function isMultipart (&$boundary = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function splitMultipartBody () {}

}


}


namespace http\Client\Curl {

interface User  {
	const POLL_NONE = 0;
	const POLL_IN = 1;
	const POLL_OUT = 2;
	const POLL_INOUT = 3;
	const POLL_REMOVE = 4;


	/**
	 * {@inheritdoc}
	 * @param mixed $run
	 */
	abstract public function init ($run = null);

	/**
	 * {@inheritdoc}
	 * @param int $timeout_ms
	 */
	abstract public function timer (int $timeout_ms);

	/**
	 * {@inheritdoc}
	 * @param mixed $socket
	 * @param int $action
	 */
	abstract public function socket ($socket = null, int $action);

	/**
	 * {@inheritdoc}
	 */
	abstract public function once ();

	/**
	 * {@inheritdoc}
	 * @param int $timeout_ms [optional]
	 */
	abstract public function wait (int $timeout_ms = NULL);

	/**
	 * {@inheritdoc}
	 */
	abstract public function send ();

}


}


namespace http {

class Url implements \Stringable {
	const REPLACE = 0;
	const JOIN_PATH = 1;
	const JOIN_QUERY = 2;
	const STRIP_USER = 4;
	const STRIP_PASS = 8;
	const STRIP_AUTH = 12;
	const STRIP_PORT = 32;
	const STRIP_PATH = 64;
	const STRIP_QUERY = 128;
	const STRIP_FRAGMENT = 256;
	const STRIP_ALL = 492;
	const FROM_ENV = 4096;
	const SANITIZE_PATH = 8192;
	const PARSE_MBLOC = 65536;
	const PARSE_MBUTF8 = 131072;
	const PARSE_TOIDN = 1048576;
	const PARSE_TOIDN_2003 = 9437184;
	const PARSE_TOIDN_2008 = 5242880;
	const PARSE_TOPCT = 2097152;
	const IGNORE_ERRORS = 268435456;
	const SILENT_ERRORS = 536870912;
	const STDFLAGS = 3350531;


	public $scheme;

	public $user;

	public $pass;

	public $host;

	public $port;

	public $path;

	public $query;

	public $fragment;

	/**
	 * {@inheritdoc}
	 * @param mixed $old_url [optional]
	 * @param mixed $new_url [optional]
	 * @param mixed $flags [optional]
	 */
	public function __construct ($old_url = NULL, $new_url = NULL, $flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $more_url_parts
	 * @param mixed $flags [optional]
	 */
	public function mod ($more_url_parts = null, $flags = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function toString () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function toArray () {}

}


}


namespace http\Env {

class Url extends \http\Url implements \Stringable {
	const REPLACE = 0;
	const JOIN_PATH = 1;
	const JOIN_QUERY = 2;
	const STRIP_USER = 4;
	const STRIP_PASS = 8;
	const STRIP_AUTH = 12;
	const STRIP_PORT = 32;
	const STRIP_PATH = 64;
	const STRIP_QUERY = 128;
	const STRIP_FRAGMENT = 256;
	const STRIP_ALL = 492;
	const FROM_ENV = 4096;
	const SANITIZE_PATH = 8192;
	const PARSE_MBLOC = 65536;
	const PARSE_MBUTF8 = 131072;
	const PARSE_TOIDN = 1048576;
	const PARSE_TOIDN_2003 = 9437184;
	const PARSE_TOIDN_2008 = 5242880;
	const PARSE_TOPCT = 2097152;
	const IGNORE_ERRORS = 268435456;
	const SILENT_ERRORS = 536870912;
	const STDFLAGS = 3350531;


	/**
	 * {@inheritdoc}
	 * @param mixed $old_url [optional]
	 * @param mixed $new_url [optional]
	 * @param mixed $flags [optional]
	 */
	public function __construct ($old_url = NULL, $new_url = NULL, $flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $more_url_parts
	 * @param mixed $flags [optional]
	 */
	public function mod ($more_url_parts = null, $flags = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function toString () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function toArray () {}

}


}


namespace http {

class Env  {

	/**
	 * {@inheritdoc}
	 * @param mixed $header_name [optional]
	 */
	public static function getRequestHeader ($header_name = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $body_class_name [optional]
	 */
	public static function getRequestBody ($body_class_name = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $code
	 */
	public static function getResponseStatusForCode ($code = null) {}

	/**
	 * {@inheritdoc}
	 */
	public static function getResponseStatusForAllCodes () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header_name [optional]
	 */
	public static function getResponseHeader ($header_name = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public static function getResponseCode () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header_name
	 * @param mixed $header_value [optional]
	 * @param mixed $response_code [optional]
	 * @param mixed $replace_header [optional]
	 */
	public static function setResponseHeader ($header_name = null, $header_value = NULL, $response_code = NULL, $replace_header = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $code
	 */
	public static function setResponseCode ($code = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $supported
	 * @param mixed $result_array [optional]
	 */
	public static function negotiateLanguage ($supported = null, &$result_array = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $supported
	 * @param mixed $result_array [optional]
	 */
	public static function negotiateContentType ($supported = null, &$result_array = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $supported
	 * @param mixed $result_array [optional]
	 */
	public static function negotiateEncoding ($supported = null, &$result_array = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $supported
	 * @param mixed $result_array [optional]
	 */
	public static function negotiateCharset ($supported = null, &$result_array = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $params
	 * @param mixed $supported
	 * @param mixed $primary_type_separator [optional]
	 * @param mixed $result_array [optional]
	 */
	public static function negotiate ($params = null, $supported = null, $primary_type_separator = NULL, &$result_array = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public static function reset () {}

}


}


namespace http\Env {

class Request extends \http\Message implements \Traversable, \Iterator, \Serializable, \Countable, \Stringable {
	const TYPE_NONE = 0;
	const TYPE_REQUEST = 1;
	const TYPE_RESPONSE = 2;


	protected $query;

	protected $form;

	protected $cookie;

	protected $files;

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name [optional]
	 * @param mixed $type [optional]
	 * @param mixed $defval [optional]
	 * @param mixed $delete [optional]
	 */
	public function getForm ($name = NULL, $type = NULL, $defval = NULL, $delete = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name [optional]
	 * @param mixed $type [optional]
	 * @param mixed $defval [optional]
	 * @param mixed $delete [optional]
	 */
	public function getQuery ($name = NULL, $type = NULL, $defval = NULL, $delete = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name [optional]
	 * @param mixed $type [optional]
	 * @param mixed $defval [optional]
	 * @param mixed $delete [optional]
	 */
	public function getCookie ($name = NULL, $type = NULL, $defval = NULL, $delete = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getFiles () {}

	/**
	 * {@inheritdoc}
	 */
	public function getBody () {}

	/**
	 * {@inheritdoc}
	 * @param \http\Message\Body $body
	 */
	public function setBody (\http\Message\Body $body) {}

	/**
	 * {@inheritdoc}
	 * @param \http\Message\Body $body
	 */
	public function addBody (\http\Message\Body $body) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header
	 * @param mixed $into_class [optional]
	 */
	public function getHeader ($header = null, $into_class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header
	 * @param mixed $value [optional]
	 */
	public function setHeader ($header = null, $value = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header
	 * @param mixed $value
	 */
	public function addHeader ($header = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getHeaders () {}

	/**
	 * {@inheritdoc}
	 * @param array|null $headers
	 */
	public function setHeaders (?array $headers = null) {}

	/**
	 * {@inheritdoc}
	 * @param array $headers
	 * @param mixed $append [optional]
	 */
	public function addHeaders (array $headers, $append = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $type
	 */
	public function setType ($type = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getInfo () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $http_info
	 */
	public function setInfo ($http_info = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getResponseCode () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $response_code
	 * @param mixed $strict [optional]
	 */
	public function setResponseCode ($response_code = null, $strict = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getResponseStatus () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $response_status
	 */
	public function setResponseStatus ($response_status = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getRequestMethod () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $request_method
	 */
	public function setRequestMethod ($request_method = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getRequestUrl () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $url
	 */
	public function setRequestUrl ($url = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getHttpVersion () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $http_version
	 */
	public function setHttpVersion ($http_version = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getParentMessage () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $include_parent [optional]
	 */
	public function toString ($include_parent = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $callback
	 */
	public function toCallback ($callback = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $stream
	 */
	public function toStream ($stream = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function serialize () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $serialized
	 */
	public function unserialize ($serialized = null) {}

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
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function detach () {}

	/**
	 * {@inheritdoc}
	 * @param \http\Message $message
	 * @param mixed $top [optional]
	 */
	public function prepend (\http\Message $message, $top = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function reverse () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $boundary [optional]
	 */
	public function isMultipart (&$boundary = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function splitMultipartBody () {}

}

class Response extends \http\Message implements \Traversable, \Iterator, \Serializable, \Countable, \Stringable {
	const TYPE_NONE = 0;
	const TYPE_REQUEST = 1;
	const TYPE_RESPONSE = 2;
	const CONTENT_ENCODING_NONE = 0;
	const CONTENT_ENCODING_GZIP = 1;
	const CACHE_NO = 0;
	const CACHE_HIT = 1;
	const CACHE_MISS = 2;


	protected $request;

	protected $cookies;

	protected $contentType;

	protected $contentDisposition;

	protected $contentEncoding;

	protected $cacheControl;

	protected $etag;

	protected $lastModified;

	protected $throttleDelay;

	protected $throttleChunk;

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $ob_string
	 * @param mixed $ob_flags [optional]
	 */
	public function __invoke ($ob_string = null, $ob_flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param \http\Message|null $env_request
	 */
	public function setEnvRequest (?\http\Message $env_request = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $cookie
	 */
	public function setCookie ($cookie = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $content_type
	 */
	public function setContentType ($content_type = null) {}

	/**
	 * {@inheritdoc}
	 * @param array|null $disposition_params
	 */
	public function setContentDisposition (?array $disposition_params = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $content_encoding
	 */
	public function setContentEncoding ($content_encoding = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $cache_control
	 */
	public function setCacheControl ($cache_control = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $last_modified
	 */
	public function setLastModified ($last_modified = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header_name [optional]
	 */
	public function isCachedByLastModified ($header_name = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $etag
	 */
	public function setEtag ($etag = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header_name [optional]
	 */
	public function isCachedByEtag ($header_name = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $chunk_size
	 * @param mixed $delay [optional]
	 */
	public function setThrottleRate ($chunk_size = null, $delay = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $stream [optional]
	 */
	public function send ($stream = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getBody () {}

	/**
	 * {@inheritdoc}
	 * @param \http\Message\Body $body
	 */
	public function setBody (\http\Message\Body $body) {}

	/**
	 * {@inheritdoc}
	 * @param \http\Message\Body $body
	 */
	public function addBody (\http\Message\Body $body) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header
	 * @param mixed $into_class [optional]
	 */
	public function getHeader ($header = null, $into_class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header
	 * @param mixed $value [optional]
	 */
	public function setHeader ($header = null, $value = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $header
	 * @param mixed $value
	 */
	public function addHeader ($header = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getHeaders () {}

	/**
	 * {@inheritdoc}
	 * @param array|null $headers
	 */
	public function setHeaders (?array $headers = null) {}

	/**
	 * {@inheritdoc}
	 * @param array $headers
	 * @param mixed $append [optional]
	 */
	public function addHeaders (array $headers, $append = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $type
	 */
	public function setType ($type = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getInfo () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $http_info
	 */
	public function setInfo ($http_info = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getResponseCode () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $response_code
	 * @param mixed $strict [optional]
	 */
	public function setResponseCode ($response_code = null, $strict = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getResponseStatus () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $response_status
	 */
	public function setResponseStatus ($response_status = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getRequestMethod () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $request_method
	 */
	public function setRequestMethod ($request_method = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getRequestUrl () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $url
	 */
	public function setRequestUrl ($url = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getHttpVersion () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $http_version
	 */
	public function setHttpVersion ($http_version = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getParentMessage () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $include_parent [optional]
	 */
	public function toString ($include_parent = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $callback
	 */
	public function toCallback ($callback = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $stream
	 */
	public function toStream ($stream = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function serialize () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $serialized
	 */
	public function unserialize ($serialized = null) {}

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
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function detach () {}

	/**
	 * {@inheritdoc}
	 * @param \http\Message $message
	 * @param mixed $top [optional]
	 */
	public function prepend (\http\Message $message, $top = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function reverse () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $boundary [optional]
	 */
	public function isMultipart (&$boundary = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function splitMultipartBody () {}

}


}


namespace http {

class Params implements \Stringable, \ArrayAccess {
	const DEF_PARAM_SEP = ",";
	const DEF_ARG_SEP = ";";
	const DEF_VAL_SEP = "=";
	const COOKIE_PARAM_SEP = "";
	const PARSE_RAW = 0;
	const PARSE_ESCAPED = 1;
	const PARSE_URLENCODED = 4;
	const PARSE_DIMENSION = 8;
	const PARSE_RFC5987 = 16;
	const PARSE_RFC5988 = 32;
	const PARSE_DEFAULT = 17;
	const PARSE_QUERY = 12;


	public $params;

	public $param_sep;

	public $arg_sep;

	public $val_sep;

	public $flags;

	/**
	 * {@inheritdoc}
	 * @param mixed $params [optional]
	 * @param mixed $param_sep [optional]
	 * @param mixed $arg_sep [optional]
	 * @param mixed $val_sep [optional]
	 * @param mixed $flags [optional]
	 */
	final public function __construct ($params = NULL, $param_sep = NULL, $arg_sep = NULL, $val_sep = NULL, $flags = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function toArray () {}

	/**
	 * {@inheritdoc}
	 */
	public function toString () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name
	 */
	public function offsetExists ($name = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name
	 */
	public function offsetUnset ($name = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name
	 * @param mixed $value
	 */
	public function offsetSet ($name = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name
	 */
	public function offsetGet ($name = null) {}

}

}


namespace {

define ('http\Client\Curl\FEATURES', 1441777565);
define ('http\Client\Curl\Features\IPV6', 1);
define ('http\Client\Curl\Features\KERBEROS4', 2);
define ('http\Client\Curl\Features\SSL', 4);
define ('http\Client\Curl\Features\LIBZ', 8);
define ('http\Client\Curl\Features\NTLM', 16);
define ('http\Client\Curl\Features\GSSNEGOTIATE', 32);
define ('http\Client\Curl\Features\ASYNCHDNS', 128);
define ('http\Client\Curl\Features\SPNEGO', 256);
define ('http\Client\Curl\Features\LARGEFILE', 512);
define ('http\Client\Curl\Features\IDN', 1024);
define ('http\Client\Curl\Features\SSPI', 2048);
define ('http\Client\Curl\Features\TLSAUTH_SRP', 16384);
define ('http\Client\Curl\Features\NTLM_WB', 32768);
define ('http\Client\Curl\Features\HTTP2', 65536);
define ('http\Client\Curl\Features\GSSAPI', 131072);
define ('http\Client\Curl\Features\KERBEROS5', 262144);
define ('http\Client\Curl\Features\UNIX_SOCKETS', 524288);
define ('http\Client\Curl\Features\PSL', 1048576);
define ('http\Client\Curl\Features\HTTPS_PROXY', 2097152);
define ('http\Client\Curl\Features\MULTI_SSL', 4194304);
define ('http\Client\Curl\Features\BROTLI', 8388608);
define ('http\Client\Curl\Features\ALTSVC', 16777216);
define ('http\Client\Curl\Features\HTTP3', 33554432);
define ('http\Client\Curl\Features\ZSTD', 67108864);
define ('http\Client\Curl\Features\UNICODE', 134217728);
define ('http\Client\Curl\Features\HSTS', 268435456);
define ('http\Client\Curl\VERSIONS', "libcurl/8.1.0 (SecureTransport) OpenSSL/1.1.1t zlib/1.2.11 brotli/1.0.9 zstd/1.5.5 libidn2/2.3.4 libssh2/1.10.0 nghttp2/1.53.0 librtmp/2.3");
define ('http\Client\Curl\Versions\CURL', "8.1.0");
define ('http\Client\Curl\Versions\SSL', "(SecureTransport) OpenSSL/1.1.1t");
define ('http\Client\Curl\Versions\LIBZ', "1.2.11");
define ('http\Client\Curl\Versions\ARES', null);
define ('http\Client\Curl\Versions\IDN', "2.3.4");
define ('http\Client\Curl\Versions\ICONV', null);
define ('http\Client\Curl\Versions\BROTLI', "1.0.9");
define ('http\Client\Curl\Versions\NGHTTP2', "1.53.0");
define ('http\Client\Curl\Versions\QUIC', null);
define ('http\Client\Curl\Versions\CAINFO', null);
define ('http\Client\Curl\Versions\CAPATH', null);
define ('http\Client\Curl\Versions\ZSTD', "1.5.5");
define ('http\Client\Curl\Versions\HYPER', null);
define ('http\Client\Curl\HTTP_VERSION_1_0', 1);
define ('http\Client\Curl\HTTP_VERSION_1_1', 2);
define ('http\Client\Curl\HTTP_VERSION_2_0', 3);
define ('http\Client\Curl\HTTP_VERSION_2TLS', 4);
define ('http\Client\Curl\HTTP_VERSION_2_PRIOR_KNOWLEDGE', 5);
define ('http\Client\Curl\HTTP_VERSION_3', 30);
define ('http\Client\Curl\HTTP_VERSION_ANY', 0);
define ('http\Client\Curl\SSL_VERSION_TLSv1', 1);
define ('http\Client\Curl\SSL_VERSION_TLSv1_0', 4);
define ('http\Client\Curl\SSL_VERSION_TLSv1_1', 5);
define ('http\Client\Curl\SSL_VERSION_TLSv1_2', 6);
define ('http\Client\Curl\SSL_VERSION_TLSv1_3', 7);
define ('http\Client\Curl\SSL_VERSION_SSLv2', 2);
define ('http\Client\Curl\SSL_VERSION_SSLv3', 3);
define ('http\Client\Curl\SSL_VERSION_ANY', 0);
define ('http\Client\Curl\TLSAUTH_SRP', 1);
define ('http\Client\Curl\SSL_VERSION_MAX_DEFAULT', 65536);
define ('http\Client\Curl\SSL_VERSION_MAX_TLSv1_0', 262144);
define ('http\Client\Curl\SSL_VERSION_MAX_TLSv1_1', 327680);
define ('http\Client\Curl\SSL_VERSION_MAX_TLSv1_2', 393216);
define ('http\Client\Curl\SSL_VERSION_MAX_TLSv1_3', 458752);
define ('http\Client\Curl\IPRESOLVE_V4', 1);
define ('http\Client\Curl\IPRESOLVE_V6', 2);
define ('http\Client\Curl\IPRESOLVE_ANY', 0);
define ('http\Client\Curl\AUTH_NONE', 0);
define ('http\Client\Curl\AUTH_BASIC', 1);
define ('http\Client\Curl\AUTH_DIGEST', 2);
define ('http\Client\Curl\AUTH_DIGEST_IE', 16);
define ('http\Client\Curl\AUTH_NTLM', 8);
define ('http\Client\Curl\AUTH_GSSNEG', 4);
define ('http\Client\Curl\AUTH_SPNEGO', 4);
define ('http\Client\Curl\AUTH_BEARER', 64);
define ('http\Client\Curl\AWS_SIGV4', 128);
define ('http\Client\Curl\AUTH_ANY', -17);
define ('http\Client\Curl\PROXY_SOCKS4', 4);
define ('http\Client\Curl\PROXY_SOCKS4A', 6);
define ('http\Client\Curl\PROXY_SOCKS5_HOSTNAME', 7);
define ('http\Client\Curl\PROXY_SOCKS5', 5);
define ('http\Client\Curl\PROXY_HTTP', 0);
define ('http\Client\Curl\PROXY_HTTP_1_0', 1);
define ('http\Client\Curl\POSTREDIR_301', 1);
define ('http\Client\Curl\POSTREDIR_302', 2);
define ('http\Client\Curl\POSTREDIR_303', 4);
define ('http\Client\Curl\POSTREDIR_ALL', 7);
define ('http\Client\Curl\ALTSVC_READONLYFILE', 4);
define ('http\Client\Curl\ALTSVC_H1', 8);
define ('http\Client\Curl\ALTSVC_H2', 16);
define ('http\Client\Curl\ALTSVC_H3', 32);
define ('http\Client\Curl\HSTS_ENABLE', 1);
define ('http\Client\Curl\HSTS_READONLYFILE', 2);


}

// End of http v.4.2.3
