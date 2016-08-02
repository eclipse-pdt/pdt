<?php

// Start of Core v.7.0.8

class stdClass  {
}

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
	 * @return Traversable Returns an external iterator.
	 */
	abstract public function getIterator () {}

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
	 * @return mixed Returns the current element.
	 */
	abstract public function current () {}

	/**
	 * Move forward to next element
	 * @link http://www.php.net/manual/en/iterator.next.php
	 * @return void This method is called after each
	 * foreach loop.
	 */
	abstract public function next () {}

	/**
	 * Return the key of the current element
	 * @link http://www.php.net/manual/en/iterator.key.php
	 * @return scalar Returns the key of the current element.
	 */
	abstract public function key () {}

	/**
	 * Checks if current position is valid
	 * @link http://www.php.net/manual/en/iterator.valid.php
	 * @return boolean This method is called after Iterator::rewind and
	 * Iterator::next to check if the current position is
	 * valid.
	 */
	abstract public function valid () {}

	/**
	 * Rewind the Iterator to the first element
	 * @link http://www.php.net/manual/en/iterator.rewind.php
	 * @return void This is the first method called when starting a
	 * foreach loop. It will not be
	 * executed after foreach loops.
	 */
	abstract public function rewind () {}

}

/**
 * Interface to provide accessing objects as arrays.
 * @link http://www.php.net/manual/en/class.arrayaccess.php
 */
interface ArrayAccess  {

	/**
	 * Whether an offset exists
	 * @link http://www.php.net/manual/en/arrayaccess.offsetexists.php
	 * @param mixed $offset <p>
	 * An offset to check for.
	 * </p>
	 * @return boolean When using empty ArrayAccess::offsetGet will
	 * be called and checked if empty only if ArrayAccess::offsetExists
	 * returns true.
	 */
	abstract public function offsetExists ($offset) {}

	/**
	 * Offset to retrieve
	 * @link http://www.php.net/manual/en/arrayaccess.offsetget.php
	 * @param mixed $offset <p>
	 * The offset to retrieve.
	 * </p>
	 * @return mixed This method is executed when checking if offset is empty.
	 */
	abstract public function offsetGet ($offset) {}

	/**
	 * Assign a value to the specified offset
	 * @link http://www.php.net/manual/en/arrayaccess.offsetset.php
	 * @param mixed $offset <p>
	 * The offset to assign the value to.
	 * </p>
	 * @param mixed $value <p>
	 * The value to set.
	 * </p>
	 * @return void Assigns a value to the specified offset.
	 */
	abstract public function offsetSet ($offset, $value) {}

	/**
	 * Unset an offset
	 * @link http://www.php.net/manual/en/arrayaccess.offsetunset.php
	 * @param mixed $offset <p>
	 * The offset to unset.
	 * </p>
	 * @return void This method will not be called when type-casting to
	 * (unset)
	 */
	abstract public function offsetUnset ($offset) {}

}

/**
 * Interface for customized serializing.
 * <p>Classes that implement this interface no longer support
 * __sleep() and
 * __wakeup(). The method serialize is
 * called whenever an instance needs to be serialized. This does not invoke __destruct()
 * or has any other side effect unless programmed inside the method. When the data is
 * unserialized the class is known and the appropriate unserialize() method is called as
 * a constructor instead of calling __construct(). If you need to execute the standard
 * constructor you may do so in the method.</p>
 * <p>Note, that when an old instance of a class that implements this interface
 * now, which had been serialized before the class implemeted the interface, is
 * unserialized, __wakeup() is called
 * instead of the serialize method, what might be useful for migration
 * purposes.</p>
 * @link http://www.php.net/manual/en/class.serializable.php
 */
interface Serializable  {

	/**
	 * String representation of object
	 * @link http://www.php.net/manual/en/serializable.serialize.php
	 * @return string This method acts as the
	 * destructor of the object. The
	 * __destruct() method will not be called after this
	 * method.
	 */
	abstract public function serialize () {}

	/**
	 * Constructs the object
	 * @link http://www.php.net/manual/en/serializable.unserialize.php
	 * @param string $serialized <p>
	 * The string representation of the object.
	 * </p>
	 * @return void This method acts as the
	 * constructor of the object. The
	 * __construct() method will not be called after this
	 * method.
	 */
	abstract public function unserialize ($serialized) {}

}

/**
 * Throwable is the base interface for any object that
 * can be thrown via a &throw; statement in PHP 7, including
 * Error and Exception.
 * <p>PHP classes cannot implement the Throwable
 * interface directly, and must instead extend
 * Exception.</p>
 * @link http://www.php.net/manual/en/class.throwable.php
 */
interface Throwable  {

	/**
	 * Gets the message
	 * @link http://www.php.net/manual/en/throwable.getmessage.php
	 * @return string Returns the message associated with the thrown object.
	 */
	abstract public function getMessage () {}

	/**
	 * Gets the exception code
	 * @link http://www.php.net/manual/en/throwable.getcode.php
	 * @return int Returns the error code associated with the thrown object.
	 */
	abstract public function getCode () {}

	/**
	 * Gets the file in which the exception occurred
	 * @link http://www.php.net/manual/en/throwable.getfile.php
	 * @return string Returns the name of the file from which the object was thrown.
	 */
	abstract public function getFile () {}

	/**
	 * Gets the line on which the object was instantiated
	 * @link http://www.php.net/manual/en/throwable.getline.php
	 * @return int Returns the line number where the thrown object was instantiated.
	 */
	abstract public function getLine () {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/throwable.gettrace.php
	 * @return array Returns the stack trace as an array.
	 */
	abstract public function getTrace () {}

	/**
	 * Returns the previous Throwable
	 * @link http://www.php.net/manual/en/throwable.getprevious.php
	 * @return Throwable Returns any previous Throwable (for example, one provided as the third
	 * parameter to Exception::__construct).
	 */
	abstract public function getPrevious () {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/throwable.gettraceasstring.php
	 * @return string the stack trace as a string.
	 */
	abstract public function getTraceAsString () {}

	/**
	 * Gets a string representation of the thrown object
	 * @link http://www.php.net/manual/en/throwable.tostring.php
	 * @return string the string representation of the thrown object.
	 */
	abstract public function __toString () {}

}

/**
 * Exception is the base class for
 * all Exceptions in PHP 5, and the base class for all user exceptions in PHP
 * 7.
 * <p>In PHP 7, Exception implements the
 * Throwable interface.</p>
 * @link http://www.php.net/manual/en/class.exception.php
 */
class Exception implements Throwable {
	protected $message;
	private $string;
	protected $code;
	protected $file;
	protected $line;
	private $trace;
	private $previous;


	/**
	 * Clone the exception
	 * @link http://www.php.net/manual/en/exception.clone.php
	 * @return void Tries to clone the Exception, which results in Fatal error.
	 */
	final private function __clone () {}

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param $message [optional]
	 * @param $code [optional]
	 * @param $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	/**
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message.
	 */
	final public function getMessage () {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return mixed Returns the Exception code.
	 */
	final public function getCode () {}

	/**
	 * Gets the file in which the exception occurred
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Get the name of the file the exception was created.
	 */
	final public function getFile () {}

	/**
	 * Gets the line in which the exception occurred
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Get line number where the exception was created.
	 */
	final public function getLine () {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace.
	 */
	final public function getTrace () {}

	/**
	 * Returns previous Exception
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return Exception Returns previous Exception (the third parameter of Exception::__construct).
	 */
	final public function getPrevious () {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString () {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString () {}

}

/**
 * An Error Exception.
 * @link http://www.php.net/manual/en/class.errorexception.php
 */
class ErrorException extends Exception implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $severity;


	/**
	 * Constructs the exception
	 * @link http://www.php.net/manual/en/errorexception.construct.php
	 * @param $message [optional]
	 * @param $code [optional]
	 * @param $severity [optional]
	 * @param $filename [optional]
	 * @param $lineno [optional]
	 * @param $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $severity = null, $filename = null, $lineno = null, $previous = null) {}

	/**
	 * Gets the exception severity
	 * @link http://www.php.net/manual/en/errorexception.getseverity.php
	 * @return int Returns the severity of the exception.
	 */
	final public function getSeverity () {}

	/**
	 * Clone the exception
	 * @link http://www.php.net/manual/en/exception.clone.php
	 * @return void Tries to clone the Exception, which results in Fatal error.
	 */
	final private function __clone () {}

	public function __wakeup () {}

	/**
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message.
	 */
	final public function getMessage () {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return mixed Returns the Exception code.
	 */
	final public function getCode () {}

	/**
	 * Gets the file in which the exception occurred
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Get the name of the file the exception was created.
	 */
	final public function getFile () {}

	/**
	 * Gets the line in which the exception occurred
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Get line number where the exception was created.
	 */
	final public function getLine () {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace.
	 */
	final public function getTrace () {}

	/**
	 * Returns previous Exception
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return Exception Returns previous Exception (the third parameter of Exception::__construct).
	 */
	final public function getPrevious () {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString () {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString () {}

}

/**
 * Error is the base class for all
 * internal PHP errors.
 * @link http://www.php.net/manual/en/class.error.php
 */
class Error implements Throwable {
	protected $message;
	private $string;
	protected $code;
	protected $file;
	protected $line;
	private $trace;
	private $previous;


	/**
	 * Clone the error
	 * @link http://www.php.net/manual/en/error.clone.php
	 * @return void Error can not be clone, so this method results in fatal error.
	 */
	final private function __clone () {}

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
	 * @param $message [optional]
	 * @param $code [optional]
	 * @param $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	/**
	 * Gets the error message
	 * @link http://www.php.net/manual/en/error.getmessage.php
	 * @return string Returns the error message.
	 */
	final public function getMessage () {}

	/**
	 * Gets the error code
	 * @link http://www.php.net/manual/en/error.getcode.php
	 * @return mixed Returns the error code.
	 */
	final public function getCode () {}

	/**
	 * Gets the file in which the error occurred
	 * @link http://www.php.net/manual/en/error.getfile.php
	 * @return string Get the name of the file the error occurred.
	 */
	final public function getFile () {}

	/**
	 * Gets the line in which the error occurred
	 * @link http://www.php.net/manual/en/error.getline.php
	 * @return int Get line number where the error occurred.
	 */
	final public function getLine () {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/error.gettrace.php
	 * @return array Returns the stack trace.
	 */
	final public function getTrace () {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/error.getprevious.php
	 * @return Throwable Returns previous Throwable (the third parameter of Error::__construct).
	 */
	final public function getPrevious () {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/error.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	final public function getTraceAsString () {}

	/**
	 * String representation of the error
	 * @link http://www.php.net/manual/en/error.tostring.php
	 * @return string Returns the string representation of the error.
	 */
	public function __toString () {}

}

/**
 * ParseError is thrown when an
 * error occurs while parsing PHP code, such as when
 * eval is called.
 * @link http://www.php.net/manual/en/class.parseerror.php
 */
class ParseError extends Error implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * Clone the error
	 * @link http://www.php.net/manual/en/error.clone.php
	 * @return void Error can not be clone, so this method results in fatal error.
	 */
	final private function __clone () {}

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
	 * @param $message [optional]
	 * @param $code [optional]
	 * @param $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	/**
	 * Gets the error message
	 * @link http://www.php.net/manual/en/error.getmessage.php
	 * @return string Returns the error message.
	 */
	final public function getMessage () {}

	/**
	 * Gets the error code
	 * @link http://www.php.net/manual/en/error.getcode.php
	 * @return mixed Returns the error code.
	 */
	final public function getCode () {}

	/**
	 * Gets the file in which the error occurred
	 * @link http://www.php.net/manual/en/error.getfile.php
	 * @return string Get the name of the file the error occurred.
	 */
	final public function getFile () {}

	/**
	 * Gets the line in which the error occurred
	 * @link http://www.php.net/manual/en/error.getline.php
	 * @return int Get line number where the error occurred.
	 */
	final public function getLine () {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/error.gettrace.php
	 * @return array Returns the stack trace.
	 */
	final public function getTrace () {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/error.getprevious.php
	 * @return Throwable Returns previous Throwable (the third parameter of Error::__construct).
	 */
	final public function getPrevious () {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/error.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	final public function getTraceAsString () {}

	/**
	 * String representation of the error
	 * @link http://www.php.net/manual/en/error.tostring.php
	 * @return string Returns the string representation of the error.
	 */
	public function __toString () {}

}

/**
 * There are three scenarios where a
 * TypeError may be thrown. The
 * first is where the argument type being passed to a function does not match
 * its corresponding declared parameter type. The second is where a value
 * being returned from a function does not match the declared function return
 * type. The third is where an invalid number of arguments are passed to a
 * built-in PHP function (strict mode only).
 * @link http://www.php.net/manual/en/class.typeerror.php
 */
class TypeError extends Error implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * Clone the error
	 * @link http://www.php.net/manual/en/error.clone.php
	 * @return void Error can not be clone, so this method results in fatal error.
	 */
	final private function __clone () {}

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
	 * @param $message [optional]
	 * @param $code [optional]
	 * @param $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	/**
	 * Gets the error message
	 * @link http://www.php.net/manual/en/error.getmessage.php
	 * @return string Returns the error message.
	 */
	final public function getMessage () {}

	/**
	 * Gets the error code
	 * @link http://www.php.net/manual/en/error.getcode.php
	 * @return mixed Returns the error code.
	 */
	final public function getCode () {}

	/**
	 * Gets the file in which the error occurred
	 * @link http://www.php.net/manual/en/error.getfile.php
	 * @return string Get the name of the file the error occurred.
	 */
	final public function getFile () {}

	/**
	 * Gets the line in which the error occurred
	 * @link http://www.php.net/manual/en/error.getline.php
	 * @return int Get line number where the error occurred.
	 */
	final public function getLine () {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/error.gettrace.php
	 * @return array Returns the stack trace.
	 */
	final public function getTrace () {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/error.getprevious.php
	 * @return Throwable Returns previous Throwable (the third parameter of Error::__construct).
	 */
	final public function getPrevious () {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/error.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	final public function getTraceAsString () {}

	/**
	 * String representation of the error
	 * @link http://www.php.net/manual/en/error.tostring.php
	 * @return string Returns the string representation of the error.
	 */
	public function __toString () {}

}

/**
 * ArithmeticError is thrown when
 * an error occurs while performing mathematical operations. In PHP 7.0,
 * these errors include attempting to perform a bitshift by a negative
 * amount, and any call to intdiv that would result in a
 * value outside the possible bounds of an integer.
 * @link http://www.php.net/manual/en/class.arithmeticerror.php
 */
class ArithmeticError extends Error implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * Clone the error
	 * @link http://www.php.net/manual/en/error.clone.php
	 * @return void Error can not be clone, so this method results in fatal error.
	 */
	final private function __clone () {}

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
	 * @param $message [optional]
	 * @param $code [optional]
	 * @param $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	/**
	 * Gets the error message
	 * @link http://www.php.net/manual/en/error.getmessage.php
	 * @return string Returns the error message.
	 */
	final public function getMessage () {}

	/**
	 * Gets the error code
	 * @link http://www.php.net/manual/en/error.getcode.php
	 * @return mixed Returns the error code.
	 */
	final public function getCode () {}

	/**
	 * Gets the file in which the error occurred
	 * @link http://www.php.net/manual/en/error.getfile.php
	 * @return string Get the name of the file the error occurred.
	 */
	final public function getFile () {}

	/**
	 * Gets the line in which the error occurred
	 * @link http://www.php.net/manual/en/error.getline.php
	 * @return int Get line number where the error occurred.
	 */
	final public function getLine () {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/error.gettrace.php
	 * @return array Returns the stack trace.
	 */
	final public function getTrace () {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/error.getprevious.php
	 * @return Throwable Returns previous Throwable (the third parameter of Error::__construct).
	 */
	final public function getPrevious () {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/error.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	final public function getTraceAsString () {}

	/**
	 * String representation of the error
	 * @link http://www.php.net/manual/en/error.tostring.php
	 * @return string Returns the string representation of the error.
	 */
	public function __toString () {}

}

/**
 * DivisionByZeroError is thrown
 * when an attempt is made to divide a number by zero.
 * @link http://www.php.net/manual/en/class.divisionbyzeroerror.php
 */
class DivisionByZeroError extends ArithmeticError implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * Clone the error
	 * @link http://www.php.net/manual/en/error.clone.php
	 * @return void Error can not be clone, so this method results in fatal error.
	 */
	final private function __clone () {}

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
	 * @param $message [optional]
	 * @param $code [optional]
	 * @param $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	/**
	 * Gets the error message
	 * @link http://www.php.net/manual/en/error.getmessage.php
	 * @return string Returns the error message.
	 */
	final public function getMessage () {}

	/**
	 * Gets the error code
	 * @link http://www.php.net/manual/en/error.getcode.php
	 * @return mixed Returns the error code.
	 */
	final public function getCode () {}

	/**
	 * Gets the file in which the error occurred
	 * @link http://www.php.net/manual/en/error.getfile.php
	 * @return string Get the name of the file the error occurred.
	 */
	final public function getFile () {}

	/**
	 * Gets the line in which the error occurred
	 * @link http://www.php.net/manual/en/error.getline.php
	 * @return int Get line number where the error occurred.
	 */
	final public function getLine () {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/error.gettrace.php
	 * @return array Returns the stack trace.
	 */
	final public function getTrace () {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/error.getprevious.php
	 * @return Throwable Returns previous Throwable (the third parameter of Error::__construct).
	 */
	final public function getPrevious () {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/error.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	final public function getTraceAsString () {}

	/**
	 * String representation of the error
	 * @link http://www.php.net/manual/en/error.tostring.php
	 * @return string Returns the string representation of the error.
	 */
	public function __toString () {}

}

/**
 * Class used to represent anonymous
 * functions.
 * <p>Anonymous functions, implemented in PHP 5.3, yield objects of this type.
 * This fact used to be considered an implementation detail, but it can now
 * be relied upon. Starting with PHP 5.4, this class has methods that allow
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
	 * @param Closure $closure <p>
	 * The anonymous functions to bind.
	 * </p>
	 * @param object $newthis <p>
	 * The object to which the given anonymous function should be bound, or
	 * NULL for the closure to be unbound.
	 * </p>
	 * @param mixed $newscope [optional] <p>
	 * The class scope to which associate the closure is to be associated, or
	 * 'static' to keep the current one. If an object is given, the type of the
	 * object will be used instead. This determines the visibility of protected
	 * and private methods of the bound object.
	 * It is not allowed to pass (an object of) an internal class as this parameter.
	 * </p>
	 * @return Closure This method is a static version of Closure::bindTo.
	 * See the documentation of that method for more information.
	 */
	public static function bind (Closure $closure, $newthis, $newscope = null) {}

	/**
	 * Duplicates the closure with a new bound object and class scope
	 * @link http://www.php.net/manual/en/closure.bindto.php
	 * @param object $newthis <p>
	 * The object to which the given anonymous function should be bound, or
	 * NULL for the closure to be unbound. 
	 * </p>
	 * @param mixed $newscope [optional] <p>
	 * The class scope to which associate the closure is to be associated, or
	 * 'static' to keep the current one. If an object is given, the type of the
	 * object will be used instead. This determines the visibility of protected
	 * and private methods of the bound object.
	 * </p>
	 * @return Closure If you only want to duplicate the anonymous functions, you can use
	 * cloning instead.
	 */
	public function bindTo ($newthis, $newscope = null) {}

	/**
	 * Binds and calls the closure
	 * @link http://www.php.net/manual/en/closure.call.php
	 * @param object $newthis <p>
	 * The object to bind the closure to for the duration of the
	 * call.
	 * </p>
	 * @param mixed $_ [optional] 
	 * @return mixed Temporarily binds the closure to newthis, and calls
	 * it with any given parameters.
	 */
	public function call ($newthis, $_ = null) {}

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
	 * @return void If iteration has already begun, this will throw an exception.
	 */
	public function rewind () {}

	/**
	 * Check if the iterator has been closed
	 * @link http://www.php.net/manual/en/generator.valid.php
	 * @return bool false if the iterator has been closed. Otherwise returns true.
	 */
	public function valid () {}

	/**
	 * Get the yielded value
	 * @link http://www.php.net/manual/en/generator.current.php
	 * @return mixed the yielded value.
	 */
	public function current () {}

	/**
	 * Get the yielded key
	 * @link http://www.php.net/manual/en/generator.key.php
	 * @return mixed Gets the key of the yielded value.
	 */
	public function key () {}

	/**
	 * Resume execution of the generator
	 * @link http://www.php.net/manual/en/generator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Send a value to the generator
	 * @link http://www.php.net/manual/en/generator.send.php
	 * @param mixed $value <p>
	 * Value to send into the generator. This value will be the return value of the
	 * yield expression the generator is currently at.
	 * </p>
	 * @return mixed If the generator is not at a yield expression when this method is called, it
	 * will first be let to advance to the first yield expression before sending the
	 * value. As such it is not necessary to "prime" PHP generators with a
	 * Generator::next call (like it is done in Python).
	 */
	public function send ($value) {}

	/**
	 * Throw an exception into the generator
	 * @link http://www.php.net/manual/en/generator.throw.php
	 * @param Exception $exception <p>
	 * Exception to throw into the generator.
	 * </p>
	 * @return mixed If the generator is already closed when this method is invoked, the exception will
	 * be thrown in the caller's context instead.
	 */
	public function throw (Exception $exception) {}

	/**
	 * Get the return value of a generator
	 * @link http://www.php.net/manual/en/generator.getreturn.php
	 * @return mixed the generator's return value once it has finished executing.
	 */
	public function getReturn () {}

	/**
	 * Serialize callback
	 * @link http://www.php.net/manual/en/generator.wakeup.php
	 * @return void Throws an exception as generators can't be serialized.
	 */
	public function __wakeup () {}

}

class ClosedGeneratorException extends Exception implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * Clone the exception
	 * @link http://www.php.net/manual/en/exception.clone.php
	 * @return void Tries to clone the Exception, which results in Fatal error.
	 */
	final private function __clone () {}

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param $message [optional]
	 * @param $code [optional]
	 * @param $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	/**
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message.
	 */
	final public function getMessage () {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return mixed Returns the Exception code.
	 */
	final public function getCode () {}

	/**
	 * Gets the file in which the exception occurred
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Get the name of the file the exception was created.
	 */
	final public function getFile () {}

	/**
	 * Gets the line in which the exception occurred
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Get line number where the exception was created.
	 */
	final public function getLine () {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace.
	 */
	final public function getTrace () {}

	/**
	 * Returns previous Exception
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return Exception Returns previous Exception (the third parameter of Exception::__construct).
	 */
	final public function getPrevious () {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString () {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString () {}

}

/**
 * Gets the version of the current Zend engine
 * @link http://www.php.net/manual/en/function.zend-version.php
 * @return string Returns a string containing the version of the currently running
 * Zend Engine.
 */
function zend_version () {}

/**
 * Returns the number of arguments passed to the function
 * @link http://www.php.net/manual/en/function.func-num-args.php
 * @return int This function may be used in conjunction with 
 * func_get_arg and func_get_args
 * to allow user-defined functions to accept variable-length argument lists.
 */
function func_num_args () {}

/**
 * Return an item from the argument list
 * @link http://www.php.net/manual/en/function.func-get-arg.php
 * @param int $arg_num <p>
 * The argument offset. Function arguments are counted starting from
 * zero.
 * </p>
 * @return mixed This function may be used in conjunction with 
 * func_get_args and func_num_args
 * to allow user-defined functions to accept variable-length argument lists.
 */
function func_get_arg ($arg_num) {}

/**
 * Returns an array comprising a function's argument list
 * @link http://www.php.net/manual/en/function.func-get-args.php
 * @return array This function may be used in conjunction with 
 * func_get_arg and func_num_args
 * to allow user-defined functions to accept variable-length argument lists.
 */
function func_get_args () {}

/**
 * Get string length
 * @link http://www.php.net/manual/en/function.strlen.php
 * @param string $string <p>
 * The string being measured for length.
 * </p>
 * @return int Returns the length of the given string.
 */
function strlen ($string) {}

/**
 * Binary safe string comparison
 * @link http://www.php.net/manual/en/function.strcmp.php
 * @param string $str1 <p>
 * The first string.
 * </p>
 * @param string $str2 <p>
 * The second string.
 * </p>
 * @return int &lt; 0 if str1 is less than
 * str2; &gt; 0 if str1
 * is greater than str2, and 0 if they are
 * equal.
 */
function strcmp ($str1, $str2) {}

/**
 * Binary safe string comparison of the first n characters
 * @link http://www.php.net/manual/en/function.strncmp.php
 * @param string $str1 <p>
 * The first string.
 * </p>
 * @param string $str2 <p>
 * The second string.
 * </p>
 * @param int $len <p>
 * Number of characters to use in the comparison.
 * </p>
 * @return int Note that this comparison is case sensitive.
 */
function strncmp ($str1, $str2, $len) {}

/**
 * Binary safe case-insensitive string comparison
 * @link http://www.php.net/manual/en/function.strcasecmp.php
 * @param string $str1 <p>
 * The first string
 * </p>
 * @param string $str2 <p>
 * The second string
 * </p>
 * @return int Binary safe case-insensitive string comparison.
 */
function strcasecmp ($str1, $str2) {}

/**
 * Binary safe case-insensitive string comparison of the first n characters
 * @link http://www.php.net/manual/en/function.strncasecmp.php
 * @param string $str1 <p>
 * The first string.
 * </p>
 * @param string $str2 <p>
 * The second string.
 * </p>
 * @param int $len <p>
 * The length of strings to be used in the comparison.
 * </p>
 * @return int This function is similar to strcasecmp, with the
 * difference that you can specify the (upper limit of the) number of
 * characters from each string to be used in the comparison.
 */
function strncasecmp ($str1, $str2, $len) {}

/**
 * Return the current key and value pair from an array and advance the array cursor
 * @link http://www.php.net/manual/en/function.each.php
 * @param array $array <p>
 * The input array.
 * </p>
 * @return array After each has executed, the array cursor
 * will be left on the next element of the array, or past the last
 * element if it hits the end of the array. You have to use
 * reset if you want to traverse the array
 * again using each.
 */
function each (array &$array) {}

/**
 * Sets which PHP errors are reported
 * @link http://www.php.net/manual/en/function.error-reporting.php
 * @param int $level [optional] <p>
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
 * @return int The error_reporting function sets the
 * error_reporting
 * directive at runtime. PHP has many levels of errors, using
 * this function sets that level for the duration (runtime) of
 * your script. If the optional level is
 * not set, error_reporting will just return
 * the current error reporting level.
 */
function error_reporting ($level = null) {}

/**
 * Defines a named constant
 * @link http://www.php.net/manual/en/function.define.php
 * @param string $name <p>
 * The name of the constant.
 * </p>
 * @param mixed $value <p>
 * The value of the constant. In PHP 5, value must
 * be a scalar value (integer,
 * float, string, boolean, or
 * NULL). In PHP 7, array values are also accepted.
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
 * <p>
 * Case-insensitive constants are stored as lower-case.
 * </p>
 * @return bool Defines a named constant at runtime.
 */
function define ($name, $value, $case_insensitive = null) {}

/**
 * Checks whether a given named constant exists
 * @link http://www.php.net/manual/en/function.defined.php
 * @param string $name <p>
 * The constant name.
 * </p>
 * @return bool If you want to see if a variable exists, use isset
 * as defined only applies to constants. If you want to see if a
 * function exists, use function_exists.
 */
function defined ($name) {}

/**
 * Returns the name of the class of an object
 * @link http://www.php.net/manual/en/function.get-class.php
 * @param object $object [optional] <p>
 * The tested object. This parameter may be omitted when inside a class.
 * </p>
 * @return string Gets the name of the class of the given object.
 */
function get_class ($object = null) {}

/**
 * the "Late Static Binding" class name
 * @link http://www.php.net/manual/en/function.get-called-class.php
 * @return string Gets the name of the class the static method is called in.
 */
function get_called_class () {}

/**
 * Retrieves the parent class name for object or class
 * @link http://www.php.net/manual/en/function.get-parent-class.php
 * @param mixed $object [optional] <p>
 * The tested object or class name. This parameter is optional if called
 * from the object's method.
 * </p>
 * @return string Retrieves the parent class name for object or class.
 */
function get_parent_class ($object = null) {}

/**
 * Checks if the class method exists
 * @link http://www.php.net/manual/en/function.method-exists.php
 * @param mixed $object <p>
 * An object instance or a class name
 * </p>
 * @param string $method_name <p>
 * The method name
 * </p>
 * @return bool Checks if the class method exists in the given
 * object.
 */
function method_exists ($object, $method_name) {}

/**
 * Checks if the object or class has a property
 * @link http://www.php.net/manual/en/function.property-exists.php
 * @param mixed $class <p>
 * The class name or an object of the class to test for
 * </p>
 * @param string $property <p>
 * The name of the property
 * </p>
 * @return bool As opposed with isset,
 * property_exists returns true even if the property
 * has the value NULL.
 */
function property_exists ($class, $property) {}

/**
 * Checks if the class has been defined
 * @link http://www.php.net/manual/en/function.class-exists.php
 * @param string $class_name <p>
 * The class name. The name is matched in a case-insensitive manner.
 * </p>
 * @param bool $autoload [optional] <p>
 * Whether or not to call &link.autoload; by default.
 * </p>
 * @return bool This function checks whether or not the given class has been defined.
 */
function class_exists ($class_name, $autoload = null) {}

/**
 * Checks if the interface has been defined
 * @link http://www.php.net/manual/en/function.interface-exists.php
 * @param string $interface_name <p>
 * The interface name
 * </p>
 * @param bool $autoload [optional] <p>
 * Whether to call &link.autoload; or not by default.
 * </p>
 * @return bool Checks if the given interface has been defined.
 */
function interface_exists ($interface_name, $autoload = null) {}

/**
 * Checks if the trait exists
 * @link http://www.php.net/manual/en/function.trait-exists.php
 * @param string $traitname <p>
 * Name of the trait to check
 * </p>
 * @param bool $autoload [optional] <p>
 * Whether to autoload if not already loaded.
 * </p>
 * @return bool true if trait exists, false if not, NULL in case of an error.
 */
function trait_exists ($traitname, $autoload = null) {}

/**
 * Return true if the given function has been defined
 * @link http://www.php.net/manual/en/function.function-exists.php
 * @param string $function_name <p>
 * The function name, as a string.
 * </p>
 * @return bool Checks the list of defined functions, both built-in (internal) and
 * user-defined, for function_name.
 */
function function_exists ($function_name) {}

/**
 * Creates an alias for a class
 * @link http://www.php.net/manual/en/function.class-alias.php
 * @param string $original <p>
 * The original class.
 * </p>
 * @param string $alias <p>
 * The alias name for the class.
 * </p>
 * @param bool $autoload [optional] <p>
 * Whether to autoload if the original class is not found.
 * </p>
 * @return bool Creates an alias named alias
 * based on the user defined class original.
 * The aliased class is exactly the same as the original class.
 */
function class_alias ($original, $alias, $autoload = null) {}

/**
 * Returns an array with the names of included or required files
 * @link http://www.php.net/manual/en/function.get-included-files.php
 * @return array Gets the names of all files that have been included using
 * include, include_once,
 * require or require_once.
 */
function get_included_files () {}

/**
 * Alias get_included_files
 * @link http://www.php.net/manual/en/function.get-required-files.php
 */
function get_required_files () {}

/**
 * Checks if the object has this class as one of its parents or implements it.
 * @link http://www.php.net/manual/en/function.is-subclass-of.php
 * @param mixed $object <p>
 * A class name or an object instance. No error is generated if the class does not exist.
 * </p>
 * @param string $class_name <p>
 * The class name
 * </p>
 * @param bool $allow_string [optional] <p>
 * If this parameter set to false, string class name as object
 * is not allowed. This also prevents from calling autoloader if the class doesn't exist.
 * </p>
 * @return bool Checks if the given object has the class
 * class_name as one of its parents or implements it.
 */
function is_subclass_of ($object, $class_name, $allow_string = null) {}

/**
 * Checks if the object is of this class or has this class as one of its parents
 * @link http://www.php.net/manual/en/function.is-a.php
 * @param object $object <p>
 * The tested object
 * </p>
 * @param string $class_name <p>
 * The class name
 * </p>
 * @param bool $allow_string [optional] <p>
 * If this parameter set to false, string class name as object
 * is not allowed. This also prevents from calling autoloader if the class doesn't exist.
 * </p>
 * @return bool Checks if the given object is of this class or has
 * this class as one of its parents.
 */
function is_a ($object, $class_name, $allow_string = null) {}

/**
 * Get the default properties of the class
 * @link http://www.php.net/manual/en/function.get-class-vars.php
 * @param string $class_name <p>
 * The class name
 * </p>
 * @return array Get the default properties of the given class.
 */
function get_class_vars ($class_name) {}

/**
 * Gets the properties of the given object
 * @link http://www.php.net/manual/en/function.get-object-vars.php
 * @param object $object <p>
 * An object instance.
 * </p>
 * @return array Gets the accessible non-static properties of the given 
 * object according to scope.
 */
function get_object_vars ($object) {}

/**
 * Gets the class methods' names
 * @link http://www.php.net/manual/en/function.get-class-methods.php
 * @param mixed $class_name <p>
 * The class name or an object instance
 * </p>
 * @return array Gets the class methods names.
 */
function get_class_methods ($class_name) {}

/**
 * Generates a user-level error/warning/notice message
 * @link http://www.php.net/manual/en/function.trigger-error.php
 * @param string $error_msg <p>
 * The designated error message for this error. It's limited to 1024 
 * bytes in length. Any additional characters beyond 1024 bytes will be 
 * truncated.
 * </p>
 * @param int $error_type [optional] <p>
 * The designated error type for this error. It only works with the E_USER
 * family of constants, and will default to E_USER_NOTICE.
 * </p>
 * @return bool This function is useful when you need to generate a particular response to
 * an exception at runtime.
 */
function trigger_error ($error_msg, $error_type = null) {}

/**
 * Alias of trigger_error
 * @link http://www.php.net/manual/en/function.user-error.php
 * @param $message
 * @param $error_type [optional]
 */
function user_error ($message, $error_type = null) {}

/**
 * Sets a user-defined error handler function
 * @link http://www.php.net/manual/en/function.set-error-handler.php
 * @param callable $error_handler <p>
 * A callback with the following signature.
 * NULL may be passed instead, to reset this handler to its default state.
 * Instead of a function name, an array containing an object reference 
 * and a method name can also be supplied.
 * </p>
 * <p>
 * boolhandler
 * interrno
 * stringerrstr
 * stringerrfile
 * interrline
 * arrayerrcontext
 * errno
 * The first parameter, errno, contains the
 * level of the error raised, as an integer.
 * @param int $error_types [optional] <p>
 * Can be used to mask the triggering of the
 * error_handler function just like the error_reporting ini setting 
 * controls which errors are shown. Without this mask set the
 * error_handler will be called for every error
 * regardless to the setting of the error_reporting setting.
 * </p>
 * @return mixed If errors occur before the script is executed (e.g. on file uploads) the 
 * custom error handler cannot be called since it is not registered at that 
 * time.
 */
function set_error_handler ($error_handler, $error_types = null) {}

/**
 * Restores the previous error handler function
 * @link http://www.php.net/manual/en/function.restore-error-handler.php
 * @return bool Used after changing the error handler function using
 * set_error_handler, to revert to the previous error
 * handler (which could be the built-in or a user defined function).
 */
function restore_error_handler () {}

/**
 * Sets a user-defined exception handler function
 * @link http://www.php.net/manual/en/function.set-exception-handler.php
 * @param callable $exception_handler <p>
 * Name of the function to be called when an uncaught exception occurs.
 * This handler function
 * needs to accept one parameter, which will be the exception object that
 * was thrown. This is the handler signature before PHP 7:
 * </p>
 * <p>
 * voidhandler
 * Exceptionex
 * </p>
 * <p>
 * Since PHP 7, most errors are reported by throwing Error
 * exceptions, which will be caught by the handler as well. Both Error
 * and Exception implements the Throwable interface.
 * This is the handler signature since PHP 7:
 * </p>
 * <p>
 * voidhandler
 * Throwableex
 * </p>
 * <p>
 * NULL may be passed instead, to reset this handler to its default state.
 * </p>
 * <p>
 * Note that providing an explicit Exception type
 * hint for the ex parameter in your callback will
 * cause issues with the changed exception hierarchy in PHP 7.
 * </p>
 * @return callable Sets the default exception handler if an exception is not caught within a
 * try/catch block. Execution will stop after the
 * exception_handler is called.
 */
function set_exception_handler ($exception_handler) {}

/**
 * Restores the previously defined exception handler function
 * @link http://www.php.net/manual/en/function.restore-exception-handler.php
 * @return bool Used after changing the exception handler function using
 * set_exception_handler, to revert to the previous
 * exception handler (which could be the built-in or a user defined
 * function).
 */
function restore_exception_handler () {}

/**
 * Returns an array with the name of the defined classes
 * @link http://www.php.net/manual/en/function.get-declared-classes.php
 * @return array Gets the declared classes.
 */
function get_declared_classes () {}

/**
 * Returns an array of all declared traits
 * @link http://www.php.net/manual/en/function.get-declared-traits.php
 * @return array an array with names of all declared traits in values.
 * Returns NULL in case of a failure.
 */
function get_declared_traits () {}

/**
 * Returns an array of all declared interfaces
 * @link http://www.php.net/manual/en/function.get-declared-interfaces.php
 * @return array Gets the declared interfaces.
 */
function get_declared_interfaces () {}

/**
 * Returns an array of all defined functions
 * @link http://www.php.net/manual/en/function.get-defined-functions.php
 * @return array Gets an array of all defined functions.
 */
function get_defined_functions () {}

/**
 * Returns an array of all defined variables
 * @link http://www.php.net/manual/en/function.get-defined-vars.php
 * @return array This function returns a multidimensional array containing a list of
 * all defined variables, be them environment, server or user-defined
 * variables, within the scope that get_defined_vars is
 * called.
 */
function get_defined_vars () {}

/**
 * Create an anonymous (lambda-style) function
 * @link http://www.php.net/manual/en/function.create-function.php
 * @param string $args <p>
 * The function arguments.
 * </p>
 * @param string $code <p>
 * The function code.
 * </p>
 * @return string If you are using PHP 5.3.0 or newer a native
 * anonymous function should be used instead.
 */
function create_function ($args, $code) {}

/**
 * Returns the resource type
 * @link http://www.php.net/manual/en/function.get-resource-type.php
 * @param resource $handle <p>
 * The evaluated resource handle.
 * </p>
 * @return string This function gets the type of the given resource.
 */
function get_resource_type ($handle) {}

/**
 * Returns active resources
 * @link http://www.php.net/manual/en/function.get-resources.php
 * @param string $type [optional] <p>
 * If defined, this will cause get_resources to only
 * return resources of the given type.
 * A list of resource types is available.
 * </p>
 * <p>
 * If the string Unknown is provided as
 * the type, then only resources that are of an unknown type will be
 * returned.
 * </p>
 * <p>
 * If omitted, all resources will be returned.
 * </p>
 * @return array Returns an array of all currently active resources, optionally
 * filtered by resource type.
 */
function get_resources ($type = null) {}

/**
 * Returns an array with the names of all modules compiled and loaded
 * @link http://www.php.net/manual/en/function.get-loaded-extensions.php
 * @param bool $zend_extensions [optional] <p>
 * Only return Zend extensions, if not then regular extensions, like 
 * mysqli are listed. Defaults to false (return regular extensions).
 * </p>
 * @return array This function returns the names of all the modules compiled and
 * loaded in the PHP interpreter.
 */
function get_loaded_extensions ($zend_extensions = null) {}

/**
 * Find out whether an extension is loaded
 * @link http://www.php.net/manual/en/function.extension-loaded.php
 * @param string $name <p>
 * The extension name. This parameter is case-insensitive.
 * </p>
 * <p>
 * You can see the names of various extensions by using
 * phpinfo or if you're using the
 * CGI or CLI version of
 * PHP you can use the -m switch to
 * list all available extensions:
 * </p>
 * @return bool Finds out whether the extension is loaded.
 */
function extension_loaded ($name) {}

/**
 * Returns an array with the names of the functions of a module
 * @link http://www.php.net/manual/en/function.get-extension-funcs.php
 * @param string $module_name <p>
 * The module name.
 * </p>
 * <p>
 * This parameter must be in lowercase.
 * </p>
 * @return array This function returns the names of all the functions defined in
 * the module indicated by module_name.
 */
function get_extension_funcs ($module_name) {}

/**
 * Returns an associative array with the names of all the constants and their values
 * @link http://www.php.net/manual/en/function.get-defined-constants.php
 * @param bool $categorize [optional] <p>
 * Causing this function to return a multi-dimensional
 * array with categories in the keys of the first dimension and constants
 * and their values in the second dimension.
 * ]]>
 * &example.outputs.similar;
 * Array
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
 * ]]>
 * </p>
 * @return array Returns the names and values of all the constants currently defined.
 * This includes those created by extensions as well as those created with
 * the define function.
 */
function get_defined_constants ($categorize = null) {}

/**
 * Generates a backtrace
 * @link http://www.php.net/manual/en/function.debug-backtrace.php
 * @param int $options [optional] <p>
 * As of 5.3.6, this parameter is a bitmask for the following options:
 * <table>
 * debug_backtrace options
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
 * Before 5.3.6, the only values recognized are true or false, which are the same as 
 * setting or not setting the DEBUG_BACKTRACE_PROVIDE_OBJECT option respectively.
 * </p>
 * @param int $limit [optional] <p>
 * As of 5.4.0, this parameter can be used to limit the number of stack frames returned.
 * By default (limit=0) it returns all stack frames.
 * </p>
 * @return array debug_backtrace generates a PHP backtrace.
 */
function debug_backtrace ($options = null, $limit = null) {}

/**
 * Prints a backtrace
 * @link http://www.php.net/manual/en/function.debug-print-backtrace.php
 * @param int $options [optional] <p>
 * As of 5.3.6, this parameter is a bitmask for the following options:
 * <table>
 * debug_print_backtrace options
 * <tr valign="top">
 * <td>DEBUG_BACKTRACE_IGNORE_ARGS</td>
 * <td>
 * Whether or not to omit the "args" index, and thus all the function/method arguments,
 * to save memory.
 * </td>
 * </tr>
 * </table>
 * </p>
 * @param int $limit [optional] <p>
 * As of 5.4.0, this parameter can be used to limit the number of stack frames printed.
 * By default (limit=0) it prints all stack frames.
 * </p>
 * @return void debug_print_backtrace prints a PHP backtrace. It
 * prints the function calls, included/required files and
 * evaled stuff.
 */
function debug_print_backtrace ($options = null, $limit = null) {}

/**
 * Reclaims memory used by the Zend Engine memory manager
 * @link http://www.php.net/manual/en/function.gc-mem-caches.php
 * @return int Reclaims memory used by the Zend Engine memory manager.
 */
function gc_mem_caches () {}

/**
 * Forces collection of any existing garbage cycles
 * @link http://www.php.net/manual/en/function.gc-collect-cycles.php
 * @return int Forces collection of any existing garbage cycles.
 */
function gc_collect_cycles () {}

/**
 * Returns status of the circular reference collector
 * @link http://www.php.net/manual/en/function.gc-enabled.php
 * @return bool Returns status of the circular reference collector.
 */
function gc_enabled () {}

/**
 * Activates the circular reference collector
 * @link http://www.php.net/manual/en/function.gc-enable.php
 * @return void Activates the circular reference collector, setting
 * zend.enable_gc to 1.
 */
function gc_enable () {}

/**
 * Deactivates the circular reference collector
 * @link http://www.php.net/manual/en/function.gc-disable.php
 * @return void Deactivates the circular reference collector, setting
 * zend.enable_gc to 0.
 */
function gc_disable () {}


/**
 * Fatal run-time errors. These indicate errors that can not be
 * recovered from, such as a memory allocation problem.
 * Execution of the script is halted.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_ERROR', 1);

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
 * Enable to have PHP suggest changes
 * to your code which will ensure the best interoperability
 * and forward compatibility of your code.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_STRICT', 2048);

/**
 * Run-time notices. Enable this to receive warnings about code
 * that will not work in future versions.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_DEPRECATED', 8192);

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
 * User-generated warning message. This is like an
 * E_DEPRECATED, except it is generated in PHP code by
 * using the PHP function trigger_error.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_USER_DEPRECATED', 16384);

/**
 * All errors and warnings, as supported, except of level
 * E_STRICT prior to PHP 5.4.0.
 * @link http://www.php.net/manual/en/errorfunc.constants.php
 */
define ('E_ALL', 32767);
define ('DEBUG_BACKTRACE_PROVIDE_OBJECT', 1);
define ('DEBUG_BACKTRACE_IGNORE_ARGS', 2);
define ('TRUE', true);
define ('FALSE', false);
define ('ZEND_THREAD_SAFE', false);
define ('ZEND_DEBUG_BUILD', false);
define ('NULL', null);
define ('PHP_VERSION', "7.0.8");
define ('PHP_MAJOR_VERSION', 7);
define ('PHP_MINOR_VERSION', 0);
define ('PHP_RELEASE_VERSION', 8);
define ('PHP_EXTRA_VERSION', "");
define ('PHP_VERSION_ID', 70008);
define ('PHP_ZTS', 0);
define ('PHP_DEBUG', 0);
define ('PHP_OS', "Linux");
define ('PHP_SAPI', "cli");
define ('DEFAULT_INCLUDE_PATH', ".:/local_build/jenkins/jobs/php-linux/workspace/php/lib/php");
define ('PEAR_INSTALL_DIR', "/local_build/jenkins/jobs/php-linux/workspace/php/lib/php");
define ('PEAR_EXTENSION_DIR', "/local_build/jenkins/jobs/php-linux/workspace/php/lib/php/extensions/no-debug-non-zts-20151012");
define ('PHP_EXTENSION_DIR', "/local_build/jenkins/jobs/php-linux/workspace/php/lib/php/extensions/no-debug-non-zts-20151012");
define ('PHP_PREFIX', "/local_build/jenkins/jobs/php-linux/workspace/php");
define ('PHP_BINDIR', "/local_build/jenkins/jobs/php-linux/workspace/php/bin");
define ('PHP_MANDIR', "/local_build/jenkins/jobs/php-linux/workspace/php/php/man");
define ('PHP_LIBDIR', "/local_build/jenkins/jobs/php-linux/workspace/php/lib/php");
define ('PHP_DATADIR', "/local_build/jenkins/jobs/php-linux/workspace/php/share/php");
define ('PHP_SYSCONFDIR', "/local_build/jenkins/jobs/php-linux/workspace/php/etc");
define ('PHP_LOCALSTATEDIR', "/local_build/jenkins/jobs/php-linux/workspace/php/var");
define ('PHP_CONFIG_FILE_PATH', "/local_build/jenkins/jobs/php-linux/workspace/php/lib");
define ('PHP_CONFIG_FILE_SCAN_DIR', "");
define ('PHP_SHLIB_SUFFIX', "so");
define ('PHP_EOL', "\n");
define ('PHP_MAXPATHLEN', 4096);
define ('PHP_INT_MAX', 9223372036854775807);
define ('PHP_INT_MIN', -9223372036854775808);
define ('PHP_INT_SIZE', 8);
define ('PHP_BINARY', "/mnt/hdd200/tmp/ZendStudio/php/php");

/**
 * <p>
 * Indicates that output buffering has begun.
 * </p>
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_START', 1);

/**
 * <p>
 * Indicates that the output buffer is being flushed, and had data to output.
 * </p>
 * <p>
 * Available since PHP 5.4.
 * </p>
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_WRITE', 0);

/**
 * <p>
 * Indicates that the buffer has been flushed.
 * </p>
 * <p>
 * Available since PHP 5.4.
 * </p>
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_FLUSH', 4);

/**
 * <p>
 * Indicates that the output buffer has been cleaned.
 * </p>
 * <p>
 * Available since PHP 5.4.
 * </p>
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_CLEAN', 2);

/**
 * <p>
 * Indicates that this is the final output buffering operation.
 * </p>
 * <p>
 * Available since PHP 5.4.
 * </p>
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_FINAL', 8);

/**
 * <p>
 * Indicates that the buffer has been flushed, but output buffering will
 * continue.
 * </p>
 * <p>
 * As of PHP 5.4, this is an alias for
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
 * As of PHP 5.4, this is an alias for
 * PHP_OUTPUT_HANDLER_FINAL.
 * </p>
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_END', 8);

/**
 * <p>
 * Controls whether an output buffer created by
 * ob_start can be cleaned.
 * </p>
 * <p>
 * Available since PHP 5.4.
 * </p>
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_CLEANABLE', 16);

/**
 * <p>
 * Controls whether an output buffer created by
 * ob_start can be flushed.
 * </p>
 * <p>
 * Available since PHP 5.4.
 * </p>
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_FLUSHABLE', 32);

/**
 * <p>
 * Controls whether an output buffer created by
 * ob_start can be removed before the end of the script.
 * </p>
 * <p>
 * Available since PHP 5.4.
 * </p>
 * @link http://www.php.net/manual/en/outcontrol.constants.php
 */
define ('PHP_OUTPUT_HANDLER_REMOVABLE', 64);

/**
 * <p>
 * The default set of output buffer flags; currently equivalent to
 * PHP_OUTPUT_HANDLER_CLEANABLE |
 * PHP_OUTPUT_HANDLER_FLUSHABLE |
 * PHP_OUTPUT_HANDLER_REMOVABLE.
 * </p>
 * <p>
 * Available since PHP 5.4.
 * </p>
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
define ('DEBUGGER_VERSION', "9.0.0");
define ('STDIN', "Resource id #1");
define ('STDOUT', "Resource id #2");
define ('STDERR', "Resource id #3");

// End of Core v.7.0.8
