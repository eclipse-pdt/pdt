<?php

// Start of SPL v.7.3.0

/**
 * Exception that represents error in the program logic. This kind of
 * exception should lead directly to a fix in your code.
 * @link http://www.php.net/manual/en/class.logicexception.php
 */
class LogicException extends Exception implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param mixed $message [optional]
	 * @param mixed $code [optional]
	 * @param mixed $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * Exception thrown if a callback refers to an undefined function or if some
 * arguments are missing.
 * @link http://www.php.net/manual/en/class.badfunctioncallexception.php
 */
class BadFunctionCallException extends LogicException implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param mixed $message [optional]
	 * @param mixed $code [optional]
	 * @param mixed $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * Exception thrown if a callback refers to an undefined method or if some
 * arguments are missing.
 * @link http://www.php.net/manual/en/class.badmethodcallexception.php
 */
class BadMethodCallException extends BadFunctionCallException implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param mixed $message [optional]
	 * @param mixed $code [optional]
	 * @param mixed $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * Exception thrown if a value does not adhere to a defined valid data domain.
 * @link http://www.php.net/manual/en/class.domainexception.php
 */
class DomainException extends LogicException implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param mixed $message [optional]
	 * @param mixed $code [optional]
	 * @param mixed $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * Exception thrown if an argument is not of the expected type.
 * @link http://www.php.net/manual/en/class.invalidargumentexception.php
 */
class InvalidArgumentException extends LogicException implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param mixed $message [optional]
	 * @param mixed $code [optional]
	 * @param mixed $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * Exception thrown if a length is invalid.
 * @link http://www.php.net/manual/en/class.lengthexception.php
 */
class LengthException extends LogicException implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param mixed $message [optional]
	 * @param mixed $code [optional]
	 * @param mixed $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * Exception thrown when an illegal index was requested. This represents
 * errors that should be detected at compile time.
 * @link http://www.php.net/manual/en/class.outofrangeexception.php
 */
class OutOfRangeException extends LogicException implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param mixed $message [optional]
	 * @param mixed $code [optional]
	 * @param mixed $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * Exception thrown if an error which can only be found on runtime occurs.
 * @link http://www.php.net/manual/en/class.runtimeexception.php
 */
class RuntimeException extends Exception implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param mixed $message [optional]
	 * @param mixed $code [optional]
	 * @param mixed $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * Exception thrown if a value is not a valid key. This represents errors
 * that cannot be detected at compile time.
 * @link http://www.php.net/manual/en/class.outofboundsexception.php
 */
class OutOfBoundsException extends RuntimeException implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param mixed $message [optional]
	 * @param mixed $code [optional]
	 * @param mixed $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * Exception thrown when adding an element to a full container.
 * @link http://www.php.net/manual/en/class.overflowexception.php
 */
class OverflowException extends RuntimeException implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param mixed $message [optional]
	 * @param mixed $code [optional]
	 * @param mixed $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * Exception thrown to indicate range errors during program execution.
 * Normally this means there was an arithmetic error other than
 * under/overflow. This is the runtime version of
 * DomainException.
 * @link http://www.php.net/manual/en/class.rangeexception.php
 */
class RangeException extends RuntimeException implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param mixed $message [optional]
	 * @param mixed $code [optional]
	 * @param mixed $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * Exception thrown when performing an invalid operation on an empty
 * container, such as removing an element.
 * @link http://www.php.net/manual/en/class.underflowexception.php
 */
class UnderflowException extends RuntimeException implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param mixed $message [optional]
	 * @param mixed $code [optional]
	 * @param mixed $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * Exception thrown if a value does not match with a set of values. Typically
 * this happens when a function calls another function and expects the return
 * value to be of a certain type or value not including arithmetic or buffer
 * related errors.
 * @link http://www.php.net/manual/en/class.unexpectedvalueexception.php
 */
class UnexpectedValueException extends RuntimeException implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param mixed $message [optional]
	 * @param mixed $code [optional]
	 * @param mixed $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

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
	 * @return bool true if the current entry can be iterated over, otherwise returns false.
	 */
	abstract public function hasChildren ();

	/**
	 * Returns an iterator for the current entry
	 * @link http://www.php.net/manual/en/recursiveiterator.getchildren.php
	 * @return RecursiveIterator An iterator for the current entry.
	 */
	abstract public function getChildren ();

	abstract public function current ();

	abstract public function next ();

	abstract public function key ();

	abstract public function valid ();

	abstract public function rewind ();

}

/**
 * Can be used to iterate through recursive iterators.
 * @link http://www.php.net/manual/en/class.recursiveiteratoriterator.php
 */
class RecursiveIteratorIterator implements Iterator, Traversable, OuterIterator {
	const LEAVES_ONLY = 0;
	const SELF_FIRST = 1;
	const CHILD_FIRST = 2;
	const CATCH_GET_CHILD = 16;


	/**
	 * Construct a RecursiveIteratorIterator
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.construct.php
	 * @param Traversable $iterator
	 * @param mixed $mode [optional]
	 * @param mixed $flags [optional]
	 */
	public function __construct (Traversable $iterator, $mode = null, $flags = null) {}

	/**
	 * Rewind the iterator to the first element of the top level inner iterator
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Check whether the current position is valid
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.valid.php
	 * @return bool true if the current position is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Access the current key
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.key.php
	 * @return mixed The current key.
	 */
	public function key () {}

	/**
	 * Access the current element value
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.current.php
	 * @return mixed The current elements value.
	 */
	public function current () {}

	/**
	 * Move forward to the next element
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Get the current depth of the recursive iteration
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.getdepth.php
	 * @return int The current depth of the recursive iteration.
	 */
	public function getDepth () {}

	/**
	 * The current active sub iterator
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.getsubiterator.php
	 * @param int $level [optional] 
	 * @return RecursiveIterator The current active sub iterator.
	 */
	public function getSubIterator (int $level = null) {}

	/**
	 * Get inner iterator
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.getinneriterator.php
	 * @return iterator The current active sub iterator.
	 */
	public function getInnerIterator () {}

	/**
	 * Begin Iteration
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.beginiteration.php
	 * @return void 
	 */
	public function beginIteration () {}

	/**
	 * End Iteration
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.enditeration.php
	 * @return void 
	 */
	public function endIteration () {}

	/**
	 * Has children
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.callhaschildren.php
	 * @return bool true if the element has children, otherwise false
	 */
	public function callHasChildren () {}

	/**
	 * Get children
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.callgetchildren.php
	 * @return RecursiveIterator A RecursiveIterator.
	 */
	public function callGetChildren () {}

	/**
	 * Begin children
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.beginchildren.php
	 * @return void 
	 */
	public function beginChildren () {}

	/**
	 * End children
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.endchildren.php
	 * @return void 
	 */
	public function endChildren () {}

	/**
	 * Next element
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.nextelement.php
	 * @return void 
	 */
	public function nextElement () {}

	/**
	 * Set max depth
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.setmaxdepth.php
	 * @param int $max_depth [optional] The maximum allowed depth. -1 is used
	 * for any depth.
	 * @return void 
	 */
	public function setMaxDepth (int $max_depth = null) {}

	/**
	 * Get max depth
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.getmaxdepth.php
	 * @return mixed The maximum accepted depth, or false if any depth is allowed.
	 */
	public function getMaxDepth () {}

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
	 * @return Iterator The inner iterator for the current entry.
	 */
	abstract public function getInnerIterator ();

	abstract public function current ();

	abstract public function next ();

	abstract public function key ();

	abstract public function valid ();

	abstract public function rewind ();

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
class IteratorIterator implements Iterator, Traversable, OuterIterator {

	/**
	 * Create an iterator from anything that is traversable
	 * @link http://www.php.net/manual/en/iteratoriterator.construct.php
	 * @param Traversable $iterator
	 */
	public function __construct (Traversable $iterator) {}

	/**
	 * Rewind to the first element
	 * @link http://www.php.net/manual/en/iteratoriterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Checks if the iterator is valid
	 * @link http://www.php.net/manual/en/iteratoriterator.valid.php
	 * @return bool true if the iterator is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Get the key of the current element
	 * @link http://www.php.net/manual/en/iteratoriterator.key.php
	 * @return scalar The key of the current element.
	 */
	public function key () {}

	/**
	 * Get the current value
	 * @link http://www.php.net/manual/en/iteratoriterator.current.php
	 * @return mixed The value of the current element.
	 */
	public function current () {}

	/**
	 * Forward to the next element
	 * @link http://www.php.net/manual/en/iteratoriterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/iteratoriterator.getinneriterator.php
	 * @return Traversable The inner iterator as passed to IteratorIterator::__construct.
	 */
	public function getInnerIterator () {}

}

/**
 * This abstract iterator filters out unwanted values. This class should be extended to
 * implement custom iterator filters. The FilterIterator::accept
 * must be implemented in the subclass.
 * @link http://www.php.net/manual/en/class.filteriterator.php
 */
abstract class FilterIterator extends IteratorIterator implements OuterIterator, Traversable, Iterator {

	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.filteriterator.php#filteriterator.props.name
	 */
	public $name;

	/**
	 * Construct a filterIterator
	 * @link http://www.php.net/manual/en/filteriterator.construct.php
	 * @param Iterator $iterator
	 */
	public function __construct (Iterator $iterator) {}

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/filteriterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://www.php.net/manual/en/filteriterator.valid.php
	 * @return bool true if the current element is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Get the current key
	 * @link http://www.php.net/manual/en/filteriterator.key.php
	 * @return mixed The current key.
	 */
	public function key () {}

	/**
	 * Get the current element value
	 * @link http://www.php.net/manual/en/filteriterator.current.php
	 * @return mixed The current element value.
	 */
	public function current () {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/filteriterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/filteriterator.getinneriterator.php
	 * @return Iterator The inner iterator.
	 */
	public function getInnerIterator () {}

	/**
	 * Check whether the current element of the iterator is acceptable
	 * @link http://www.php.net/manual/en/filteriterator.accept.php
	 * @return bool true if the current element is acceptable, otherwise false.
	 */
	abstract public function accept ();

}

/**
 * This abstract iterator filters out unwanted values for a RecursiveIterator.
 * This class should be extended to implement custom filters. 
 * The RecursiveFilterIterator::accept must be implemented in the subclass.
 * @link http://www.php.net/manual/en/class.recursivefilteriterator.php
 */
abstract class RecursiveFilterIterator extends FilterIterator implements Iterator, Traversable, OuterIterator, RecursiveIterator {

	/**
	 * Create a RecursiveFilterIterator from a RecursiveIterator
	 * @link http://www.php.net/manual/en/recursivefilteriterator.construct.php
	 * @param RecursiveIterator $iterator
	 */
	public function __construct (RecursiveIterator $iterator) {}

	/**
	 * Check whether the inner iterator's current element has children
	 * @link http://www.php.net/manual/en/recursivefilteriterator.haschildren.php
	 * @return bool true if the inner iterator has children, otherwise false
	 */
	public function hasChildren () {}

	/**
	 * Return the inner iterator's children contained in a RecursiveFilterIterator
	 * @link http://www.php.net/manual/en/recursivefilteriterator.getchildren.php
	 * @return RecursiveFilterIterator a RecursiveFilterIterator containing the inner iterator's children.
	 */
	public function getChildren () {}

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/filteriterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://www.php.net/manual/en/filteriterator.valid.php
	 * @return bool true if the current element is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Get the current key
	 * @link http://www.php.net/manual/en/filteriterator.key.php
	 * @return mixed The current key.
	 */
	public function key () {}

	/**
	 * Get the current element value
	 * @link http://www.php.net/manual/en/filteriterator.current.php
	 * @return mixed The current element value.
	 */
	public function current () {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/filteriterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/filteriterator.getinneriterator.php
	 * @return Iterator The inner iterator.
	 */
	public function getInnerIterator () {}

	/**
	 * Check whether the current element of the iterator is acceptable
	 * @link http://www.php.net/manual/en/filteriterator.accept.php
	 * @return bool true if the current element is acceptable, otherwise false.
	 */
	abstract public function accept ();

}

/**
 * @link http://www.php.net/manual/en/class.callbackfilteriterator.php
 */
class CallbackFilterIterator extends FilterIterator implements Iterator, Traversable, OuterIterator {

	/**
	 * Create a filtered iterator from another iterator
	 * @link http://www.php.net/manual/en/callbackfilteriterator.construct.php
	 * @param Iterator $iterator
	 * @param mixed $callback
	 */
	public function __construct (Iterator $iterator, $callback) {}

	/**
	 * Calls the callback with the current value, the current key and the inner iterator as arguments
	 * @link http://www.php.net/manual/en/callbackfilteriterator.accept.php
	 * @return bool true to accept the current item, or false otherwise.
	 */
	public function accept () {}

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/filteriterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://www.php.net/manual/en/filteriterator.valid.php
	 * @return bool true if the current element is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Get the current key
	 * @link http://www.php.net/manual/en/filteriterator.key.php
	 * @return mixed The current key.
	 */
	public function key () {}

	/**
	 * Get the current element value
	 * @link http://www.php.net/manual/en/filteriterator.current.php
	 * @return mixed The current element value.
	 */
	public function current () {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/filteriterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/filteriterator.getinneriterator.php
	 * @return Iterator The inner iterator.
	 */
	public function getInnerIterator () {}

}

/**
 * @link http://www.php.net/manual/en/class.recursivecallbackfilteriterator.php
 */
class RecursiveCallbackFilterIterator extends CallbackFilterIterator implements OuterIterator, Traversable, Iterator, RecursiveIterator {

	/**
	 * Create a RecursiveCallbackFilterIterator from a RecursiveIterator
	 * @link http://www.php.net/manual/en/recursivecallbackfilteriterator.construct.php
	 * @param RecursiveIterator $iterator
	 * @param mixed $callback
	 */
	public function __construct (RecursiveIterator $iterator, $callback) {}

	/**
	 * Check whether the inner iterator's current element has children
	 * @link http://www.php.net/manual/en/recursivecallbackfilteriterator.haschildren.php
	 * @return bool true if the current element has children, false otherwise.
	 */
	public function hasChildren () {}

	/**
	 * Return the inner iterator's children contained in a RecursiveCallbackFilterIterator
	 * @link http://www.php.net/manual/en/recursivecallbackfilteriterator.getchildren.php
	 * @return RecursiveCallbackFilterIterator a RecursiveCallbackFilterIterator containing
	 * the children.
	 */
	public function getChildren () {}

	/**
	 * Calls the callback with the current value, the current key and the inner iterator as arguments
	 * @link http://www.php.net/manual/en/callbackfilteriterator.accept.php
	 * @return bool true to accept the current item, or false otherwise.
	 */
	public function accept () {}

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/filteriterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://www.php.net/manual/en/filteriterator.valid.php
	 * @return bool true if the current element is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Get the current key
	 * @link http://www.php.net/manual/en/filteriterator.key.php
	 * @return mixed The current key.
	 */
	public function key () {}

	/**
	 * Get the current element value
	 * @link http://www.php.net/manual/en/filteriterator.current.php
	 * @return mixed The current element value.
	 */
	public function current () {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/filteriterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/filteriterator.getinneriterator.php
	 * @return Iterator The inner iterator.
	 */
	public function getInnerIterator () {}

}

/**
 * This extended FilterIterator allows a recursive
 * iteration using RecursiveIteratorIterator that only
 * shows those elements which have children.
 * @link http://www.php.net/manual/en/class.parentiterator.php
 */
class ParentIterator extends RecursiveFilterIterator implements RecursiveIterator, OuterIterator, Traversable, Iterator {

	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.parentiterator.php#parentiterator.props.name
	 */
	public $name;

	/**
	 * Constructs a ParentIterator
	 * @link http://www.php.net/manual/en/parentiterator.construct.php
	 * @param RecursiveIterator $iterator
	 */
	public function __construct (RecursiveIterator $iterator) {}

	/**
	 * Determines acceptability
	 * @link http://www.php.net/manual/en/parentiterator.accept.php
	 * @return bool true if the current element is acceptable, otherwise false.
	 */
	public function accept () {}

	/**
	 * Check whether the inner iterator's current element has children
	 * @link http://www.php.net/manual/en/recursivefilteriterator.haschildren.php
	 * @return bool true if the inner iterator has children, otherwise false
	 */
	public function hasChildren () {}

	/**
	 * Return the inner iterator's children contained in a RecursiveFilterIterator
	 * @link http://www.php.net/manual/en/recursivefilteriterator.getchildren.php
	 * @return RecursiveFilterIterator a RecursiveFilterIterator containing the inner iterator's children.
	 */
	public function getChildren () {}

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/filteriterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://www.php.net/manual/en/filteriterator.valid.php
	 * @return bool true if the current element is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Get the current key
	 * @link http://www.php.net/manual/en/filteriterator.key.php
	 * @return mixed The current key.
	 */
	public function key () {}

	/**
	 * Get the current element value
	 * @link http://www.php.net/manual/en/filteriterator.current.php
	 * @return mixed The current element value.
	 */
	public function current () {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/filteriterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/filteriterator.getinneriterator.php
	 * @return Iterator The inner iterator.
	 */
	public function getInnerIterator () {}

}

/**
 * The Seekable iterator.
 * @link http://www.php.net/manual/en/class.seekableiterator.php
 */
interface SeekableIterator extends Iterator, Traversable {

	/**
	 * Seeks to a position
	 * @link http://www.php.net/manual/en/seekableiterator.seek.php
	 * @param int $position The position to seek to.
	 * @return void 
	 */
	abstract public function seek (int $position);

	abstract public function current ();

	abstract public function next ();

	abstract public function key ();

	abstract public function valid ();

	abstract public function rewind ();

}

/**
 * The LimitIterator class allows iteration over 
 * a limited subset of items in an Iterator.
 * @link http://www.php.net/manual/en/class.limititerator.php
 */
class LimitIterator extends IteratorIterator implements OuterIterator, Traversable, Iterator {

	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.limititerator.php#limititerator.props.name
	 */
	public $name;

	/**
	 * Construct a LimitIterator
	 * @link http://www.php.net/manual/en/limititerator.construct.php
	 * @param Iterator $iterator
	 * @param mixed $offset [optional]
	 * @param mixed $count [optional]
	 */
	public function __construct (Iterator $iterator, $offset = null, $count = null) {}

	/**
	 * Rewind the iterator to the specified starting offset
	 * @link http://www.php.net/manual/en/limititerator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://www.php.net/manual/en/limititerator.valid.php
	 * @return bool true on success or false on failure
	 */
	public function valid () {}

	/**
	 * Get current key
	 * @link http://www.php.net/manual/en/limititerator.key.php
	 * @return mixed the key for the current item.
	 */
	public function key () {}

	/**
	 * Get current element
	 * @link http://www.php.net/manual/en/limititerator.current.php
	 * @return mixed the current element or null if there is none.
	 */
	public function current () {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/limititerator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Seek to the given position
	 * @link http://www.php.net/manual/en/limititerator.seek.php
	 * @param int $position The position to seek to.
	 * @return int the offset position after seeking.
	 */
	public function seek (int $position) {}

	/**
	 * Return the current position
	 * @link http://www.php.net/manual/en/limititerator.getposition.php
	 * @return int The current position.
	 */
	public function getPosition () {}

	/**
	 * Get inner iterator
	 * @link http://www.php.net/manual/en/limititerator.getinneriterator.php
	 * @return Iterator The inner iterator passed to LimitIterator::__construct.
	 */
	public function getInnerIterator () {}

}

/**
 * This object supports cached iteration over another iterator.
 * @link http://www.php.net/manual/en/class.cachingiterator.php
 */
class CachingIterator extends IteratorIterator implements OuterIterator, Traversable, Iterator, ArrayAccess, Countable {
	const CALL_TOSTRING = 1;
	const CATCH_GET_CHILD = 16;
	const TOSTRING_USE_KEY = 2;
	const TOSTRING_USE_CURRENT = 4;
	const TOSTRING_USE_INNER = 8;
	const FULL_CACHE = 256;


	/**
	 * Construct a new CachingIterator object for the iterator
	 * @link http://www.php.net/manual/en/cachingiterator.construct.php
	 * @param Iterator $iterator
	 * @param mixed $flags [optional]
	 */
	public function __construct (Iterator $iterator, $flags = null) {}

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/cachingiterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://www.php.net/manual/en/cachingiterator.valid.php
	 * @return void true on success or false on failure
	 */
	public function valid () {}

	/**
	 * Return the key for the current element
	 * @link http://www.php.net/manual/en/cachingiterator.key.php
	 * @return scalar 
	 */
	public function key () {}

	/**
	 * Return the current element
	 * @link http://www.php.net/manual/en/cachingiterator.current.php
	 * @return void Mixed
	 */
	public function current () {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/cachingiterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Check whether the inner iterator has a valid next element
	 * @link http://www.php.net/manual/en/cachingiterator.hasnext.php
	 * @return void true on success or false on failure
	 */
	public function hasNext () {}

	/**
	 * Return the string representation of the current element
	 * @link http://www.php.net/manual/en/cachingiterator.tostring.php
	 * @return void The string representation of the current element.
	 */
	public function __toString () {}

	/**
	 * Returns the inner iterator
	 * @link http://www.php.net/manual/en/cachingiterator.getinneriterator.php
	 * @return Iterator an object implementing the Iterator interface.
	 */
	public function getInnerIterator () {}

	/**
	 * Get flags used
	 * @link http://www.php.net/manual/en/cachingiterator.getflags.php
	 * @return int Description...
	 */
	public function getFlags () {}

	/**
	 * The setFlags purpose
	 * @link http://www.php.net/manual/en/cachingiterator.setflags.php
	 * @param int $flags Bitmask of the flags to set.
	 * @return void 
	 */
	public function setFlags (int $flags) {}

	/**
	 * The offsetGet purpose
	 * @link http://www.php.net/manual/en/cachingiterator.offsetget.php
	 * @param string $index Description...
	 * @return void Description...
	 */
	public function offsetGet (string $index) {}

	/**
	 * The offsetSet purpose
	 * @link http://www.php.net/manual/en/cachingiterator.offsetset.php
	 * @param mixed $index The index of the element to be set.
	 * @param mixed $newval The new value for the index.
	 * @return void 
	 */
	public function offsetSet ($index, $newval) {}

	/**
	 * The offsetUnset purpose
	 * @link http://www.php.net/manual/en/cachingiterator.offsetunset.php
	 * @param string $index The index of the element to be unset.
	 * @return void 
	 */
	public function offsetUnset (string $index) {}

	/**
	 * The offsetExists purpose
	 * @link http://www.php.net/manual/en/cachingiterator.offsetexists.php
	 * @param mixed $index The index being checked.
	 * @return void true if an entry referenced by the offset exists, false otherwise.
	 */
	public function offsetExists ($index) {}

	/**
	 * Retrieve the contents of the cache
	 * @link http://www.php.net/manual/en/cachingiterator.getcache.php
	 * @return array An array containing the cache items.
	 */
	public function getCache () {}

	/**
	 * The number of elements in the iterator
	 * @link http://www.php.net/manual/en/cachingiterator.count.php
	 * @return int The count of the elements iterated over.
	 */
	public function count () {}

}

/**
 * ...
 * @link http://www.php.net/manual/en/class.recursivecachingiterator.php
 */
class RecursiveCachingIterator extends CachingIterator implements Countable, ArrayAccess, Iterator, Traversable, OuterIterator, RecursiveIterator {
	const CALL_TOSTRING = 1;
	const CATCH_GET_CHILD = 16;
	const TOSTRING_USE_KEY = 2;
	const TOSTRING_USE_CURRENT = 4;
	const TOSTRING_USE_INNER = 8;
	const FULL_CACHE = 256;


	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.recursivecachingiterator.php#recursivecachingiterator.props.name
	 */
	public $name;

	/**
	 * Construct
	 * @link http://www.php.net/manual/en/recursivecachingiterator.construct.php
	 * @param Iterator $iterator
	 * @param mixed $flags [optional]
	 */
	public function __construct (Iterator $iterator, $flags = null) {}

	/**
	 * Check whether the current element of the inner iterator has children
	 * @link http://www.php.net/manual/en/recursivecachingiterator.haschildren.php
	 * @return bool true if the inner iterator has children, otherwise false
	 */
	public function hasChildren () {}

	/**
	 * Return the inner iterator's children as a RecursiveCachingIterator
	 * @link http://www.php.net/manual/en/recursivecachingiterator.getchildren.php
	 * @return RecursiveCachingIterator The inner iterator's children, as a RecursiveCachingIterator.
	 */
	public function getChildren () {}

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/cachingiterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://www.php.net/manual/en/cachingiterator.valid.php
	 * @return void true on success or false on failure
	 */
	public function valid () {}

	/**
	 * Return the key for the current element
	 * @link http://www.php.net/manual/en/cachingiterator.key.php
	 * @return scalar 
	 */
	public function key () {}

	/**
	 * Return the current element
	 * @link http://www.php.net/manual/en/cachingiterator.current.php
	 * @return void Mixed
	 */
	public function current () {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/cachingiterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Check whether the inner iterator has a valid next element
	 * @link http://www.php.net/manual/en/cachingiterator.hasnext.php
	 * @return void true on success or false on failure
	 */
	public function hasNext () {}

	/**
	 * Return the string representation of the current element
	 * @link http://www.php.net/manual/en/cachingiterator.tostring.php
	 * @return void The string representation of the current element.
	 */
	public function __toString () {}

	/**
	 * Returns the inner iterator
	 * @link http://www.php.net/manual/en/cachingiterator.getinneriterator.php
	 * @return Iterator an object implementing the Iterator interface.
	 */
	public function getInnerIterator () {}

	/**
	 * Get flags used
	 * @link http://www.php.net/manual/en/cachingiterator.getflags.php
	 * @return int Description...
	 */
	public function getFlags () {}

	/**
	 * The setFlags purpose
	 * @link http://www.php.net/manual/en/cachingiterator.setflags.php
	 * @param int $flags Bitmask of the flags to set.
	 * @return void 
	 */
	public function setFlags (int $flags) {}

	/**
	 * The offsetGet purpose
	 * @link http://www.php.net/manual/en/cachingiterator.offsetget.php
	 * @param string $index Description...
	 * @return void Description...
	 */
	public function offsetGet (string $index) {}

	/**
	 * The offsetSet purpose
	 * @link http://www.php.net/manual/en/cachingiterator.offsetset.php
	 * @param mixed $index The index of the element to be set.
	 * @param mixed $newval The new value for the index.
	 * @return void 
	 */
	public function offsetSet ($index, $newval) {}

	/**
	 * The offsetUnset purpose
	 * @link http://www.php.net/manual/en/cachingiterator.offsetunset.php
	 * @param string $index The index of the element to be unset.
	 * @return void 
	 */
	public function offsetUnset (string $index) {}

	/**
	 * The offsetExists purpose
	 * @link http://www.php.net/manual/en/cachingiterator.offsetexists.php
	 * @param mixed $index The index being checked.
	 * @return void true if an entry referenced by the offset exists, false otherwise.
	 */
	public function offsetExists ($index) {}

	/**
	 * Retrieve the contents of the cache
	 * @link http://www.php.net/manual/en/cachingiterator.getcache.php
	 * @return array An array containing the cache items.
	 */
	public function getCache () {}

	/**
	 * The number of elements in the iterator
	 * @link http://www.php.net/manual/en/cachingiterator.count.php
	 * @return int The count of the elements iterated over.
	 */
	public function count () {}

}

/**
 * This iterator cannot be rewound.
 * @link http://www.php.net/manual/en/class.norewinditerator.php
 */
class NoRewindIterator extends IteratorIterator implements OuterIterator, Traversable, Iterator {

	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.norewinditerator.php#norewinditerator.props.name
	 */
	public $name;

	/**
	 * Construct a NoRewindIterator
	 * @link http://www.php.net/manual/en/norewinditerator.construct.php
	 * @param Iterator $iterator
	 */
	public function __construct (Iterator $iterator) {}

	/**
	 * Prevents the rewind operation on the inner iterator
	 * @link http://www.php.net/manual/en/norewinditerator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Validates the iterator
	 * @link http://www.php.net/manual/en/norewinditerator.valid.php
	 * @return bool true on success or false on failure
	 */
	public function valid () {}

	/**
	 * Get the current key
	 * @link http://www.php.net/manual/en/norewinditerator.key.php
	 * @return mixed The current key.
	 */
	public function key () {}

	/**
	 * Get the current value
	 * @link http://www.php.net/manual/en/norewinditerator.current.php
	 * @return mixed The current value.
	 */
	public function current () {}

	/**
	 * Forward to the next element
	 * @link http://www.php.net/manual/en/norewinditerator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/norewinditerator.getinneriterator.php
	 * @return iterator The inner iterator, as passed to NoRewindIterator::__construct.
	 */
	public function getInnerIterator () {}

}

/**
 * An Iterator that iterates over several iterators one after the other.
 * @link http://www.php.net/manual/en/class.appenditerator.php
 */
class AppendIterator extends IteratorIterator implements OuterIterator, Traversable, Iterator {

	/**
	 * Constructs an AppendIterator
	 * @link http://www.php.net/manual/en/appenditerator.construct.php
	 */
	public function __construct () {}

	/**
	 * Appends an iterator
	 * @link http://www.php.net/manual/en/appenditerator.append.php
	 * @param Iterator $iterator The iterator to append.
	 * @return void 
	 */
	public function append ($iterator) {}

	/**
	 * Rewinds the Iterator
	 * @link http://www.php.net/manual/en/appenditerator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Checks validity of the current element
	 * @link http://www.php.net/manual/en/appenditerator.valid.php
	 * @return bool true if the current iteration is valid, false otherwise.
	 */
	public function valid () {}

	/**
	 * Gets the current key
	 * @link http://www.php.net/manual/en/appenditerator.key.php
	 * @return scalar The current key if it is valid or null otherwise.
	 */
	public function key () {}

	/**
	 * Gets the current value
	 * @link http://www.php.net/manual/en/appenditerator.current.php
	 * @return mixed The current value if it is valid or null otherwise.
	 */
	public function current () {}

	/**
	 * Moves to the next element
	 * @link http://www.php.net/manual/en/appenditerator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Gets the inner iterator
	 * @link http://www.php.net/manual/en/appenditerator.getinneriterator.php
	 * @return Iterator The current inner iterator, or null if there is not one.
	 */
	public function getInnerIterator () {}

	/**
	 * Gets an index of iterators
	 * @link http://www.php.net/manual/en/appenditerator.getiteratorindex.php
	 * @return int an integer, which is the zero-based index
	 * of the current inner iterator.
	 */
	public function getIteratorIndex () {}

	/**
	 * Gets the ArrayIterator
	 * @link http://www.php.net/manual/en/appenditerator.getarrayiterator.php
	 * @return ArrayIterator an ArrayIterator containing
	 * the appended iterators.
	 */
	public function getArrayIterator () {}

}

/**
 * The InfiniteIterator allows one to
 * infinitely iterate over an iterator without having to manually
 * rewind the iterator upon reaching its end.
 * @link http://www.php.net/manual/en/class.infiniteiterator.php
 */
class InfiniteIterator extends IteratorIterator implements OuterIterator, Traversable, Iterator {

	/**
	 * Constructs an InfiniteIterator
	 * @link http://www.php.net/manual/en/infiniteiterator.construct.php
	 * @param Iterator $iterator
	 */
	public function __construct (Iterator $iterator) {}

	/**
	 * Moves the inner Iterator forward or rewinds it
	 * @link http://www.php.net/manual/en/infiniteiterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Rewind to the first element
	 * @link http://www.php.net/manual/en/iteratoriterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Checks if the iterator is valid
	 * @link http://www.php.net/manual/en/iteratoriterator.valid.php
	 * @return bool true if the iterator is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Get the key of the current element
	 * @link http://www.php.net/manual/en/iteratoriterator.key.php
	 * @return scalar The key of the current element.
	 */
	public function key () {}

	/**
	 * Get the current value
	 * @link http://www.php.net/manual/en/iteratoriterator.current.php
	 * @return mixed The value of the current element.
	 */
	public function current () {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/iteratoriterator.getinneriterator.php
	 * @return Traversable The inner iterator as passed to IteratorIterator::__construct.
	 */
	public function getInnerIterator () {}

}

/**
 * This iterator can be used to filter another iterator based on a regular expression.
 * @link http://www.php.net/manual/en/class.regexiterator.php
 */
class RegexIterator extends FilterIterator implements Iterator, Traversable, OuterIterator {
	const USE_KEY = 1;
	const INVERT_MATCH = 2;
	const MATCH = 0;
	const GET_MATCH = 1;
	const ALL_MATCHES = 2;
	const SPLIT = 3;
	const REPLACE = 4;

	public $replacement;


	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.regexiterator.php#regexiterator.props.name
	 */
	public $name;

	/**
	 * Create a new RegexIterator
	 * @link http://www.php.net/manual/en/regexiterator.construct.php
	 * @param Iterator $iterator
	 * @param mixed $regex
	 * @param mixed $mode [optional]
	 * @param mixed $flags [optional]
	 * @param mixed $preg_flags [optional]
	 */
	public function __construct (Iterator $iterator, $regex, $mode = null, $flags = null, $preg_flags = null) {}

	/**
	 * Get accept status
	 * @link http://www.php.net/manual/en/regexiterator.accept.php
	 * @return bool true if a match, false otherwise.
	 */
	public function accept () {}

	/**
	 * Returns operation mode
	 * @link http://www.php.net/manual/en/regexiterator.getmode.php
	 * @return int the operation mode.
	 */
	public function getMode () {}

	/**
	 * Sets the operation mode
	 * @link http://www.php.net/manual/en/regexiterator.setmode.php
	 * @param int $mode <p>
	 * The operation mode.
	 * </p>
	 * <p>
	 * The available modes are listed below. The actual
	 * meanings of these modes are described in the
	 * predefined constants.
	 * <table>
	 * RegexIterator modes
	 * <table>
	 * <tr valign="top">
	 * <td>value</td>
	 * <td>constant</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>0</td>
	 * <td>
	 * RegexIterator::MATCH
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>1</td>
	 * <td>
	 * RegexIterator::GET_MATCH
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>2</td>
	 * <td>
	 * RegexIterator::ALL_MATCHES
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>3</td>
	 * <td>
	 * RegexIterator::SPLIT
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>4</td>
	 * <td>
	 * RegexIterator::REPLACE
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * @return void 
	 */
	public function setMode (int $mode) {}

	/**
	 * Get flags
	 * @link http://www.php.net/manual/en/regexiterator.getflags.php
	 * @return int the set flags.
	 */
	public function getFlags () {}

	/**
	 * Sets the flags
	 * @link http://www.php.net/manual/en/regexiterator.setflags.php
	 * @param int $flags <p>
	 * The flags to set, a bitmask of class constants.
	 * </p>
	 * <p>
	 * The available flags are listed below. The actual
	 * meanings of these flags are described in the
	 * predefined constants.
	 * <table>
	 * RegexIterator flags
	 * <table>
	 * <tr valign="top">
	 * <td>value</td>
	 * <td>constant</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>1</td>
	 * <td>
	 * RegexIterator::USE_KEY
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * @return void 
	 */
	public function setFlags (int $flags) {}

	/**
	 * Returns the regular expression flags
	 * @link http://www.php.net/manual/en/regexiterator.getpregflags.php
	 * @return int a bitmask of the regular expression flags.
	 */
	public function getPregFlags () {}

	/**
	 * Sets the regular expression flags
	 * @link http://www.php.net/manual/en/regexiterator.setpregflags.php
	 * @param int $preg_flags The regular expression flags. See RegexIterator::__construct
	 * for an overview of available flags.
	 * @return void 
	 */
	public function setPregFlags (int $preg_flags) {}

	/**
	 * Returns current regular expression
	 * @link http://www.php.net/manual/en/regexiterator.getregex.php
	 * @return string 
	 */
	public function getRegex () {}

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/filteriterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://www.php.net/manual/en/filteriterator.valid.php
	 * @return bool true if the current element is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Get the current key
	 * @link http://www.php.net/manual/en/filteriterator.key.php
	 * @return mixed The current key.
	 */
	public function key () {}

	/**
	 * Get the current element value
	 * @link http://www.php.net/manual/en/filteriterator.current.php
	 * @return mixed The current element value.
	 */
	public function current () {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/filteriterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/filteriterator.getinneriterator.php
	 * @return Iterator The inner iterator.
	 */
	public function getInnerIterator () {}

}

/**
 * This recursive iterator can filter another recursive iterator via a regular expression.
 * @link http://www.php.net/manual/en/class.recursiveregexiterator.php
 */
class RecursiveRegexIterator extends RegexIterator implements OuterIterator, Traversable, Iterator, RecursiveIterator {
	const USE_KEY = 1;
	const INVERT_MATCH = 2;
	const MATCH = 0;
	const GET_MATCH = 1;
	const ALL_MATCHES = 2;
	const SPLIT = 3;
	const REPLACE = 4;

	public $replacement;


	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.recursiveregexiterator.php#recursiveregexiterator.props.name
	 */
	public $name;

	/**
	 * Creates a new RecursiveRegexIterator
	 * @link http://www.php.net/manual/en/recursiveregexiterator.construct.php
	 * @param RecursiveIterator $iterator
	 * @param mixed $regex
	 * @param mixed $mode [optional]
	 * @param mixed $flags [optional]
	 * @param mixed $preg_flags [optional]
	 */
	public function __construct (RecursiveIterator $iterator, $regex, $mode = null, $flags = null, $preg_flags = null) {}

	public function accept () {}

	/**
	 * Returns whether an iterator can be obtained for the current entry
	 * @link http://www.php.net/manual/en/recursiveregexiterator.haschildren.php
	 * @return bool true if an iterator can be obtained for the current entry, otherwise returns false.
	 */
	public function hasChildren () {}

	/**
	 * Returns an iterator for the current entry
	 * @link http://www.php.net/manual/en/recursiveregexiterator.getchildren.php
	 * @return RecursiveRegexIterator An iterator for the current entry, if it can be iterated over by the inner iterator.
	 */
	public function getChildren () {}

	/**
	 * Returns operation mode
	 * @link http://www.php.net/manual/en/regexiterator.getmode.php
	 * @return int the operation mode.
	 */
	public function getMode () {}

	/**
	 * Sets the operation mode
	 * @link http://www.php.net/manual/en/regexiterator.setmode.php
	 * @param int $mode <p>
	 * The operation mode.
	 * </p>
	 * <p>
	 * The available modes are listed below. The actual
	 * meanings of these modes are described in the
	 * predefined constants.
	 * <table>
	 * RegexIterator modes
	 * <table>
	 * <tr valign="top">
	 * <td>value</td>
	 * <td>constant</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>0</td>
	 * <td>
	 * RegexIterator::MATCH
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>1</td>
	 * <td>
	 * RegexIterator::GET_MATCH
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>2</td>
	 * <td>
	 * RegexIterator::ALL_MATCHES
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>3</td>
	 * <td>
	 * RegexIterator::SPLIT
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>4</td>
	 * <td>
	 * RegexIterator::REPLACE
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * @return void 
	 */
	public function setMode (int $mode) {}

	/**
	 * Get flags
	 * @link http://www.php.net/manual/en/regexiterator.getflags.php
	 * @return int the set flags.
	 */
	public function getFlags () {}

	/**
	 * Sets the flags
	 * @link http://www.php.net/manual/en/regexiterator.setflags.php
	 * @param int $flags <p>
	 * The flags to set, a bitmask of class constants.
	 * </p>
	 * <p>
	 * The available flags are listed below. The actual
	 * meanings of these flags are described in the
	 * predefined constants.
	 * <table>
	 * RegexIterator flags
	 * <table>
	 * <tr valign="top">
	 * <td>value</td>
	 * <td>constant</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>1</td>
	 * <td>
	 * RegexIterator::USE_KEY
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * @return void 
	 */
	public function setFlags (int $flags) {}

	/**
	 * Returns the regular expression flags
	 * @link http://www.php.net/manual/en/regexiterator.getpregflags.php
	 * @return int a bitmask of the regular expression flags.
	 */
	public function getPregFlags () {}

	/**
	 * Sets the regular expression flags
	 * @link http://www.php.net/manual/en/regexiterator.setpregflags.php
	 * @param int $preg_flags The regular expression flags. See RegexIterator::__construct
	 * for an overview of available flags.
	 * @return void 
	 */
	public function setPregFlags (int $preg_flags) {}

	/**
	 * Returns current regular expression
	 * @link http://www.php.net/manual/en/regexiterator.getregex.php
	 * @return string 
	 */
	public function getRegex () {}

	/**
	 * Rewind the iterator
	 * @link http://www.php.net/manual/en/filteriterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Check whether the current element is valid
	 * @link http://www.php.net/manual/en/filteriterator.valid.php
	 * @return bool true if the current element is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Get the current key
	 * @link http://www.php.net/manual/en/filteriterator.key.php
	 * @return mixed The current key.
	 */
	public function key () {}

	/**
	 * Get the current element value
	 * @link http://www.php.net/manual/en/filteriterator.current.php
	 * @return mixed The current element value.
	 */
	public function current () {}

	/**
	 * Move the iterator forward
	 * @link http://www.php.net/manual/en/filteriterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Get the inner iterator
	 * @link http://www.php.net/manual/en/filteriterator.getinneriterator.php
	 * @return Iterator The inner iterator.
	 */
	public function getInnerIterator () {}

}

/**
 * The EmptyIterator class for an empty iterator.
 * @link http://www.php.net/manual/en/class.emptyiterator.php
 */
class EmptyIterator implements Iterator, Traversable {

	/**
	 * The rewind() method
	 * @link http://www.php.net/manual/en/emptyiterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * The valid() method
	 * @link http://www.php.net/manual/en/emptyiterator.valid.php
	 * @return bool false
	 */
	public function valid () {}

	/**
	 * The key() method
	 * @link http://www.php.net/manual/en/emptyiterator.key.php
	 * @return scalar 
	 */
	public function key () {}

	/**
	 * The current() method
	 * @link http://www.php.net/manual/en/emptyiterator.current.php
	 * @return mixed 
	 */
	public function current () {}

	/**
	 * The next() method
	 * @link http://www.php.net/manual/en/emptyiterator.next.php
	 * @return void 
	 */
	public function next () {}

}

/**
 * Allows iterating over a RecursiveIterator to generate an ASCII graphic tree.
 * @link http://www.php.net/manual/en/class.recursivetreeiterator.php
 */
class RecursiveTreeIterator extends RecursiveIteratorIterator implements OuterIterator, Traversable, Iterator {
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
	 * @param Traversable $iterator
	 * @param mixed $flags [optional]
	 * @param mixed $caching_it_flags [optional]
	 * @param mixed $mode [optional]
	 */
	public function __construct (Traversable $iterator, $flags = null, $caching_it_flags = null, $mode = null) {}

	/**
	 * Rewind iterator
	 * @link http://www.php.net/manual/en/recursivetreeiterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Check validity
	 * @link http://www.php.net/manual/en/recursivetreeiterator.valid.php
	 * @return bool true if the current position is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Get the key of the current element
	 * @link http://www.php.net/manual/en/recursivetreeiterator.key.php
	 * @return string the current key prefixed and postfixed.
	 */
	public function key () {}

	/**
	 * Get current element
	 * @link http://www.php.net/manual/en/recursivetreeiterator.current.php
	 * @return string the current element prefixed and postfixed.
	 */
	public function current () {}

	/**
	 * Move to next element
	 * @link http://www.php.net/manual/en/recursivetreeiterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Begin iteration
	 * @link http://www.php.net/manual/en/recursivetreeiterator.beginiteration.php
	 * @return RecursiveIterator A RecursiveIterator.
	 */
	public function beginIteration () {}

	/**
	 * End iteration
	 * @link http://www.php.net/manual/en/recursivetreeiterator.enditeration.php
	 * @return void 
	 */
	public function endIteration () {}

	/**
	 * Has children
	 * @link http://www.php.net/manual/en/recursivetreeiterator.callhaschildren.php
	 * @return bool true if there are children, otherwise false
	 */
	public function callHasChildren () {}

	/**
	 * Get children
	 * @link http://www.php.net/manual/en/recursivetreeiterator.callgetchildren.php
	 * @return RecursiveIterator A RecursiveIterator.
	 */
	public function callGetChildren () {}

	/**
	 * Begin children
	 * @link http://www.php.net/manual/en/recursivetreeiterator.beginchildren.php
	 * @return void 
	 */
	public function beginChildren () {}

	/**
	 * End children
	 * @link http://www.php.net/manual/en/recursivetreeiterator.endchildren.php
	 * @return void 
	 */
	public function endChildren () {}

	/**
	 * Next element
	 * @link http://www.php.net/manual/en/recursivetreeiterator.nextelement.php
	 * @return void 
	 */
	public function nextElement () {}

	/**
	 * Get the prefix
	 * @link http://www.php.net/manual/en/recursivetreeiterator.getprefix.php
	 * @return string the string to place in front of current element
	 */
	public function getPrefix () {}

	/**
	 * Set a part of the prefix
	 * @link http://www.php.net/manual/en/recursivetreeiterator.setprefixpart.php
	 * @param int $part One of the RecursiveTreeIterator::PREFIX_&#42; constants.
	 * @param string $value The value to assign to the part of the prefix specified in part.
	 * @return void 
	 */
	public function setPrefixPart (int $part, string $value) {}

	/**
	 * Get current entry
	 * @link http://www.php.net/manual/en/recursivetreeiterator.getentry.php
	 * @return string the part of the tree built for the current element.
	 */
	public function getEntry () {}

	/**
	 * Set postfix
	 * @link http://www.php.net/manual/en/recursivetreeiterator.setpostfix.php
	 * @param string $postfix 
	 * @return void 
	 */
	public function setPostfix (string $postfix) {}

	/**
	 * Get the postfix
	 * @link http://www.php.net/manual/en/recursivetreeiterator.getpostfix.php
	 * @return string the string to place after the current element.
	 */
	public function getPostfix () {}

	/**
	 * Get the current depth of the recursive iteration
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.getdepth.php
	 * @return int The current depth of the recursive iteration.
	 */
	public function getDepth () {}

	/**
	 * The current active sub iterator
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.getsubiterator.php
	 * @param int $level [optional] 
	 * @return RecursiveIterator The current active sub iterator.
	 */
	public function getSubIterator (int $level = null) {}

	/**
	 * Get inner iterator
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.getinneriterator.php
	 * @return iterator The current active sub iterator.
	 */
	public function getInnerIterator () {}

	/**
	 * Set max depth
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.setmaxdepth.php
	 * @param int $max_depth [optional] The maximum allowed depth. -1 is used
	 * for any depth.
	 * @return void 
	 */
	public function setMaxDepth (int $max_depth = null) {}

	/**
	 * Get max depth
	 * @link http://www.php.net/manual/en/recursiveiteratoriterator.getmaxdepth.php
	 * @return mixed The maximum accepted depth, or false if any depth is allowed.
	 */
	public function getMaxDepth () {}

}

/**
 * This class allows objects to work as arrays.
 * @link http://www.php.net/manual/en/class.arrayobject.php
 */
class ArrayObject implements IteratorAggregate, Traversable, ArrayAccess, Serializable, Countable {
	const STD_PROP_LIST = 1;
	const ARRAY_AS_PROPS = 2;


	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.arrayobject.php#arrayobject.props.name
	 */
	public $name;

	/**
	 * Construct a new array object
	 * @link http://www.php.net/manual/en/arrayobject.construct.php
	 * @param mixed $input [optional]
	 * @param mixed $flags [optional]
	 * @param mixed $iterator_class [optional]
	 */
	public function __construct ($input = null, $flags = null, $iterator_class = null) {}

	/**
	 * Returns whether the requested index exists
	 * @link http://www.php.net/manual/en/arrayobject.offsetexists.php
	 * @param mixed $index The index being checked.
	 * @return bool true if the requested index exists, otherwise false
	 */
	public function offsetExists ($index) {}

	/**
	 * Returns the value at the specified index
	 * @link http://www.php.net/manual/en/arrayobject.offsetget.php
	 * @param mixed $index The index with the value.
	 * @return mixed The value at the specified index or null.
	 */
	public function offsetGet ($index) {}

	/**
	 * Sets the value at the specified index to newval
	 * @link http://www.php.net/manual/en/arrayobject.offsetset.php
	 * @param mixed $index The index being set.
	 * @param mixed $newval The new value for the index.
	 * @return void 
	 */
	public function offsetSet ($index, $newval) {}

	/**
	 * Unsets the value at the specified index
	 * @link http://www.php.net/manual/en/arrayobject.offsetunset.php
	 * @param mixed $index The index being unset.
	 * @return void 
	 */
	public function offsetUnset ($index) {}

	/**
	 * Appends the value
	 * @link http://www.php.net/manual/en/arrayobject.append.php
	 * @param mixed $value The value being appended.
	 * @return void 
	 */
	public function append ($value) {}

	/**
	 * Creates a copy of the ArrayObject
	 * @link http://www.php.net/manual/en/arrayobject.getarraycopy.php
	 * @return array a copy of the array. When the ArrayObject refers to an object,
	 * an array of the public properties of that object will be returned.
	 */
	public function getArrayCopy () {}

	/**
	 * Get the number of public properties in the ArrayObject
	 * @link http://www.php.net/manual/en/arrayobject.count.php
	 * @return int The number of public properties in the ArrayObject.
	 * <p>
	 * When the ArrayObject is constructed from an array all properties are public.
	 * </p>
	 */
	public function count () {}

	/**
	 * Gets the behavior flags
	 * @link http://www.php.net/manual/en/arrayobject.getflags.php
	 * @return int the behavior flags of the ArrayObject.
	 */
	public function getFlags () {}

	/**
	 * Sets the behavior flags
	 * @link http://www.php.net/manual/en/arrayobject.setflags.php
	 * @param int $flags <p>
	 * The new ArrayObject behavior.
	 * It takes on either a bitmask, or named constants. Using named
	 * constants is strongly encouraged to ensure compatibility for future
	 * versions.
	 * </p>
	 * <p>
	 * The available behavior flags are listed below. The actual
	 * meanings of these flags are described in the
	 * predefined constants.
	 * <table>
	 * ArrayObject behavior flags
	 * <table>
	 * <tr valign="top">
	 * <td>value</td>
	 * <td>constant</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>1</td>
	 * <td>
	 * ArrayObject::STD_PROP_LIST
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>2</td>
	 * <td>
	 * ArrayObject::ARRAY_AS_PROPS
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * @return void 
	 */
	public function setFlags (int $flags) {}

	/**
	 * Sort the entries by value
	 * @link http://www.php.net/manual/en/arrayobject.asort.php
	 * @return void 
	 */
	public function asort () {}

	/**
	 * Sort the entries by key
	 * @link http://www.php.net/manual/en/arrayobject.ksort.php
	 * @return void 
	 */
	public function ksort () {}

	/**
	 * Sort the entries with a user-defined comparison function and maintain key association
	 * @link http://www.php.net/manual/en/arrayobject.uasort.php
	 * @param callable $cmp_function Function cmp_function should accept two
	 * parameters which will be filled by pairs of entries.
	 * The comparison function must return an integer less than, equal
	 * to, or greater than zero if the first argument is considered to
	 * be respectively less than, equal to, or greater than the
	 * second.
	 * @return void 
	 */
	public function uasort (callable $cmp_function) {}

	/**
	 * Sort the entries by keys using a user-defined comparison function
	 * @link http://www.php.net/manual/en/arrayobject.uksort.php
	 * @param callable $cmp_function <p>
	 * The callback comparison function.
	 * </p>
	 * <p>
	 * Function cmp_function should accept two
	 * parameters which will be filled by pairs of entry keys.
	 * The comparison function must return an integer less than, equal
	 * to, or greater than zero if the first argument is considered to
	 * be respectively less than, equal to, or greater than the
	 * second.
	 * </p>
	 * @return void 
	 */
	public function uksort (callable $cmp_function) {}

	/**
	 * Sort entries using a "natural order" algorithm
	 * @link http://www.php.net/manual/en/arrayobject.natsort.php
	 * @return void 
	 */
	public function natsort () {}

	/**
	 * Sort an array using a case insensitive "natural order" algorithm
	 * @link http://www.php.net/manual/en/arrayobject.natcasesort.php
	 * @return void 
	 */
	public function natcasesort () {}

	/**
	 * Unserialize an ArrayObject
	 * @link http://www.php.net/manual/en/arrayobject.unserialize.php
	 * @param string $serialized The serialized ArrayObject.
	 * @return void The unserialized ArrayObject.
	 */
	public function unserialize (string $serialized) {}

	/**
	 * Serialize an ArrayObject
	 * @link http://www.php.net/manual/en/arrayobject.serialize.php
	 * @return string The serialized representation of the ArrayObject.
	 */
	public function serialize () {}

	/**
	 * Create a new iterator from an ArrayObject instance
	 * @link http://www.php.net/manual/en/arrayobject.getiterator.php
	 * @return ArrayIterator An iterator from an ArrayObject.
	 */
	public function getIterator () {}

	/**
	 * Exchange the array for another one
	 * @link http://www.php.net/manual/en/arrayobject.exchangearray.php
	 * @param mixed $input The new array or object to exchange with the current array.
	 * @return array the old array.
	 */
	public function exchangeArray ($input) {}

	/**
	 * Sets the iterator classname for the ArrayObject
	 * @link http://www.php.net/manual/en/arrayobject.setiteratorclass.php
	 * @param string $iterator_class The classname of the array iterator to use when iterating over this object.
	 * @return void 
	 */
	public function setIteratorClass (string $iterator_class) {}

	/**
	 * Gets the iterator classname for the ArrayObject
	 * @link http://www.php.net/manual/en/arrayobject.getiteratorclass.php
	 * @return string the iterator class name that is used to iterate over this object.
	 */
	public function getIteratorClass () {}

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
class ArrayIterator implements Iterator, Traversable, ArrayAccess, SeekableIterator, Serializable, Countable {
	const STD_PROP_LIST = 1;
	const ARRAY_AS_PROPS = 2;


	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.arrayiterator.php#arrayiterator.props.name
	 */
	public $name;

	/**
	 * Construct an ArrayIterator
	 * @link http://www.php.net/manual/en/arrayiterator.construct.php
	 * @param mixed $array [optional]
	 * @param mixed $ar_flags [optional]
	 */
	public function __construct ($array = null, $ar_flags = null) {}

	/**
	 * Check if offset exists
	 * @link http://www.php.net/manual/en/arrayiterator.offsetexists.php
	 * @param mixed $index The offset being checked.
	 * @return bool true if the offset exists, otherwise false
	 */
	public function offsetExists ($index) {}

	/**
	 * Get value for an offset
	 * @link http://www.php.net/manual/en/arrayiterator.offsetget.php
	 * @param mixed $index The offset to get the value from.
	 * @return mixed The value at offset index.
	 */
	public function offsetGet ($index) {}

	/**
	 * Set value for an offset
	 * @link http://www.php.net/manual/en/arrayiterator.offsetset.php
	 * @param mixed $index The index to set for.
	 * @param mixed $newval The new value to store at the index.
	 * @return void 
	 */
	public function offsetSet ($index, $newval) {}

	/**
	 * Unset value for an offset
	 * @link http://www.php.net/manual/en/arrayiterator.offsetunset.php
	 * @param mixed $index The offset to unset.
	 * @return void 
	 */
	public function offsetUnset ($index) {}

	/**
	 * Append an element
	 * @link http://www.php.net/manual/en/arrayiterator.append.php
	 * @param mixed $value The value to append.
	 * @return void 
	 */
	public function append ($value) {}

	/**
	 * Get array copy
	 * @link http://www.php.net/manual/en/arrayiterator.getarraycopy.php
	 * @return array A copy of the array, or array of public properties
	 * if ArrayIterator refers to an object.
	 */
	public function getArrayCopy () {}

	/**
	 * Count elements
	 * @link http://www.php.net/manual/en/arrayiterator.count.php
	 * @return int The number of elements or public properties in the associated
	 * array or object, respectively.
	 */
	public function count () {}

	/**
	 * Get behavior flags
	 * @link http://www.php.net/manual/en/arrayiterator.getflags.php
	 * @return void the behavior flags of the ArrayIterator.
	 */
	public function getFlags () {}

	/**
	 * Set behaviour flags
	 * @link http://www.php.net/manual/en/arrayiterator.setflags.php
	 * @param string $flags <p>
	 * The new ArrayIterator behavior.
	 * It takes on either a bitmask, or named constants. Using named
	 * constants is strongly encouraged to ensure compatibility for future
	 * versions.
	 * </p>
	 * <p>
	 * The available behavior flags are listed below. The actual
	 * meanings of these flags are described in the
	 * predefined constants.
	 * <table>
	 * ArrayIterator behavior flags
	 * <table>
	 * <tr valign="top">
	 * <td>value</td>
	 * <td>constant</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>1</td>
	 * <td>
	 * ArrayIterator::STD_PROP_LIST
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>2</td>
	 * <td>
	 * ArrayIterator::ARRAY_AS_PROPS
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * @return void 
	 */
	public function setFlags (string $flags) {}

	/**
	 * Sort array by values
	 * @link http://www.php.net/manual/en/arrayiterator.asort.php
	 * @return void 
	 */
	public function asort () {}

	/**
	 * Sort array by keys
	 * @link http://www.php.net/manual/en/arrayiterator.ksort.php
	 * @return void 
	 */
	public function ksort () {}

	/**
	 * Sort with a user-defined comparison function and maintain index association
	 * @link http://www.php.net/manual/en/arrayiterator.uasort.php
	 * @param callable $cmp_function <p>
	 * return.callbacksort
	 * </p>
	 * callback.cmp
	 * @return void 
	 */
	public function uasort (callable $cmp_function) {}

	/**
	 * Sort by keys using a user-defined comparison function
	 * @link http://www.php.net/manual/en/arrayiterator.uksort.php
	 * @param callable $cmp_function <p>
	 * return.callbacksort
	 * </p>
	 * callback.cmp
	 * @return void 
	 */
	public function uksort (callable $cmp_function) {}

	/**
	 * Sort an array naturally
	 * @link http://www.php.net/manual/en/arrayiterator.natsort.php
	 * @return void 
	 */
	public function natsort () {}

	/**
	 * Sort an array naturally, case insensitive
	 * @link http://www.php.net/manual/en/arrayiterator.natcasesort.php
	 * @return void 
	 */
	public function natcasesort () {}

	/**
	 * Unserialize
	 * @link http://www.php.net/manual/en/arrayiterator.unserialize.php
	 * @param string $serialized The serialized ArrayIterator object to be unserialized.
	 * @return string The ArrayIterator.
	 */
	public function unserialize (string $serialized) {}

	/**
	 * Serialize
	 * @link http://www.php.net/manual/en/arrayiterator.serialize.php
	 * @return string The serialized ArrayIterator.
	 */
	public function serialize () {}

	/**
	 * Rewind array back to the start
	 * @link http://www.php.net/manual/en/arrayiterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Return current array entry
	 * @link http://www.php.net/manual/en/arrayiterator.current.php
	 * @return mixed The current array entry.
	 */
	public function current () {}

	/**
	 * Return current array key
	 * @link http://www.php.net/manual/en/arrayiterator.key.php
	 * @return mixed The current array key.
	 */
	public function key () {}

	/**
	 * Move to next entry
	 * @link http://www.php.net/manual/en/arrayiterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Check whether array contains more entries
	 * @link http://www.php.net/manual/en/arrayiterator.valid.php
	 * @return bool true if the iterator is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Seek to position
	 * @link http://www.php.net/manual/en/arrayiterator.seek.php
	 * @param int $position The position to seek to.
	 * @return void 
	 */
	public function seek (int $position) {}

}

/**
 * This iterator allows to unset and modify values and keys while iterating over Arrays and Objects
 * in the same way as the ArrayIterator. Additionally it is possible to iterate
 * over the current iterator entry.
 * @link http://www.php.net/manual/en/class.recursivearrayiterator.php
 */
class RecursiveArrayIterator extends ArrayIterator implements Countable, Serializable, SeekableIterator, ArrayAccess, Traversable, Iterator, RecursiveIterator {
	const STD_PROP_LIST = 1;
	const ARRAY_AS_PROPS = 2;
	const CHILD_ARRAYS_ONLY = 4;


	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.recursivearrayiterator.php#recursivearrayiterator.props.name
	 */
	public $name;

	/**
	 * Returns whether current entry is an array or an object
	 * @link http://www.php.net/manual/en/recursivearrayiterator.haschildren.php
	 * @return bool true if the current entry is an array or an object,
	 * otherwise false is returned.
	 */
	public function hasChildren () {}

	/**
	 * Returns an iterator for the current entry if it is an array or an object
	 * @link http://www.php.net/manual/en/recursivearrayiterator.getchildren.php
	 * @return RecursiveArrayIterator An iterator for the current entry, if it is an array or object.
	 */
	public function getChildren () {}

	/**
	 * Construct an ArrayIterator
	 * @link http://www.php.net/manual/en/arrayiterator.construct.php
	 * @param mixed $array [optional]
	 * @param mixed $ar_flags [optional]
	 */
	public function __construct ($array = null, $ar_flags = null) {}

	/**
	 * Check if offset exists
	 * @link http://www.php.net/manual/en/arrayiterator.offsetexists.php
	 * @param mixed $index The offset being checked.
	 * @return bool true if the offset exists, otherwise false
	 */
	public function offsetExists ($index) {}

	/**
	 * Get value for an offset
	 * @link http://www.php.net/manual/en/arrayiterator.offsetget.php
	 * @param mixed $index The offset to get the value from.
	 * @return mixed The value at offset index.
	 */
	public function offsetGet ($index) {}

	/**
	 * Set value for an offset
	 * @link http://www.php.net/manual/en/arrayiterator.offsetset.php
	 * @param mixed $index The index to set for.
	 * @param mixed $newval The new value to store at the index.
	 * @return void 
	 */
	public function offsetSet ($index, $newval) {}

	/**
	 * Unset value for an offset
	 * @link http://www.php.net/manual/en/arrayiterator.offsetunset.php
	 * @param mixed $index The offset to unset.
	 * @return void 
	 */
	public function offsetUnset ($index) {}

	/**
	 * Append an element
	 * @link http://www.php.net/manual/en/arrayiterator.append.php
	 * @param mixed $value The value to append.
	 * @return void 
	 */
	public function append ($value) {}

	/**
	 * Get array copy
	 * @link http://www.php.net/manual/en/arrayiterator.getarraycopy.php
	 * @return array A copy of the array, or array of public properties
	 * if ArrayIterator refers to an object.
	 */
	public function getArrayCopy () {}

	/**
	 * Count elements
	 * @link http://www.php.net/manual/en/arrayiterator.count.php
	 * @return int The number of elements or public properties in the associated
	 * array or object, respectively.
	 */
	public function count () {}

	/**
	 * Get behavior flags
	 * @link http://www.php.net/manual/en/arrayiterator.getflags.php
	 * @return void the behavior flags of the ArrayIterator.
	 */
	public function getFlags () {}

	/**
	 * Set behaviour flags
	 * @link http://www.php.net/manual/en/arrayiterator.setflags.php
	 * @param string $flags <p>
	 * The new ArrayIterator behavior.
	 * It takes on either a bitmask, or named constants. Using named
	 * constants is strongly encouraged to ensure compatibility for future
	 * versions.
	 * </p>
	 * <p>
	 * The available behavior flags are listed below. The actual
	 * meanings of these flags are described in the
	 * predefined constants.
	 * <table>
	 * ArrayIterator behavior flags
	 * <table>
	 * <tr valign="top">
	 * <td>value</td>
	 * <td>constant</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>1</td>
	 * <td>
	 * ArrayIterator::STD_PROP_LIST
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>2</td>
	 * <td>
	 * ArrayIterator::ARRAY_AS_PROPS
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * @return void 
	 */
	public function setFlags (string $flags) {}

	/**
	 * Sort array by values
	 * @link http://www.php.net/manual/en/arrayiterator.asort.php
	 * @return void 
	 */
	public function asort () {}

	/**
	 * Sort array by keys
	 * @link http://www.php.net/manual/en/arrayiterator.ksort.php
	 * @return void 
	 */
	public function ksort () {}

	/**
	 * Sort with a user-defined comparison function and maintain index association
	 * @link http://www.php.net/manual/en/arrayiterator.uasort.php
	 * @param callable $cmp_function <p>
	 * return.callbacksort
	 * </p>
	 * callback.cmp
	 * @return void 
	 */
	public function uasort (callable $cmp_function) {}

	/**
	 * Sort by keys using a user-defined comparison function
	 * @link http://www.php.net/manual/en/arrayiterator.uksort.php
	 * @param callable $cmp_function <p>
	 * return.callbacksort
	 * </p>
	 * callback.cmp
	 * @return void 
	 */
	public function uksort (callable $cmp_function) {}

	/**
	 * Sort an array naturally
	 * @link http://www.php.net/manual/en/arrayiterator.natsort.php
	 * @return void 
	 */
	public function natsort () {}

	/**
	 * Sort an array naturally, case insensitive
	 * @link http://www.php.net/manual/en/arrayiterator.natcasesort.php
	 * @return void 
	 */
	public function natcasesort () {}

	/**
	 * Unserialize
	 * @link http://www.php.net/manual/en/arrayiterator.unserialize.php
	 * @param string $serialized The serialized ArrayIterator object to be unserialized.
	 * @return string The ArrayIterator.
	 */
	public function unserialize (string $serialized) {}

	/**
	 * Serialize
	 * @link http://www.php.net/manual/en/arrayiterator.serialize.php
	 * @return string The serialized ArrayIterator.
	 */
	public function serialize () {}

	/**
	 * Rewind array back to the start
	 * @link http://www.php.net/manual/en/arrayiterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Return current array entry
	 * @link http://www.php.net/manual/en/arrayiterator.current.php
	 * @return mixed The current array entry.
	 */
	public function current () {}

	/**
	 * Return current array key
	 * @link http://www.php.net/manual/en/arrayiterator.key.php
	 * @return mixed The current array key.
	 */
	public function key () {}

	/**
	 * Move to next entry
	 * @link http://www.php.net/manual/en/arrayiterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Check whether array contains more entries
	 * @link http://www.php.net/manual/en/arrayiterator.valid.php
	 * @return bool true if the iterator is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Seek to position
	 * @link http://www.php.net/manual/en/arrayiterator.seek.php
	 * @param int $position The position to seek to.
	 * @return void 
	 */
	public function seek (int $position) {}

}

/**
 * The SplFileInfo class offers a high-level object oriented interface to
 * information for an individual file.
 * @link http://www.php.net/manual/en/class.splfileinfo.php
 */
class SplFileInfo  {

	/**
	 * Construct a new SplFileInfo object
	 * @link http://www.php.net/manual/en/splfileinfo.construct.php
	 * @param mixed $file_name
	 */
	public function __construct ($file_name) {}

	/**
	 * Gets the path without filename
	 * @link http://www.php.net/manual/en/splfileinfo.getpath.php
	 * @return string the path to the file.
	 */
	public function getPath () {}

	/**
	 * Gets the filename
	 * @link http://www.php.net/manual/en/splfileinfo.getfilename.php
	 * @return string The filename.
	 */
	public function getFilename () {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/splfileinfo.getextension.php
	 * @return string a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension () {}

	/**
	 * Gets the base name of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getbasename.php
	 * @param string $suffix [optional] Optional suffix to omit from the base name returned.
	 * @return string the base name without path information.
	 */
	public function getBasename (string $suffix = null) {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname () {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return int the file permissions.
	 */
	public function getPerms () {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return int the inode number for the filesystem object.
	 */
	public function getInode () {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return int The filesize in bytes.
	 */
	public function getSize () {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return int The owner id in numerical format.
	 */
	public function getOwner () {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return int The group id in numerical format.
	 */
	public function getGroup () {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return int the time the file was last accessed.
	 */
	public function getATime () {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return int the last modified time for the file, in a Unix timestamp.
	 */
	public function getMTime () {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return int The last change time, in a Unix timestamp.
	 */
	public function getCTime () {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return string A string representing the type of the entry.
	 * May be one of file, link,
	 * or dir
	 */
	public function getType () {}

	/**
	 * Tells if the entry is writable
	 * @link http://www.php.net/manual/en/splfileinfo.iswritable.php
	 * @return bool true if writable, false otherwise;
	 */
	public function isWritable () {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool true if readable, false otherwise.
	 */
	public function isReadable () {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool true if executable, false otherwise.
	 */
	public function isExecutable () {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile () {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool true if a directory, false otherwise.
	 */
	public function isDir () {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool true if the file is a link, false otherwise.
	 */
	public function isLink () {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return string the target of the filesystem link.
	 */
	public function getLinkTarget () {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return string the path to the file, or false if the file does not exist.
	 */
	public function getRealPath () {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param string $class_name [optional] Name of an SplFileInfo derived class to use.
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo (string $class_name = null) {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param string $class_name [optional] Name of an SplFileInfo derived class to use.
	 * @return SplFileInfo an SplFileInfo object for the parent path of the file.
	 */
	public function getPathInfo (string $class_name = null) {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $open_mode [optional] The mode for opening the file. See the fopen
	 * documentation for descriptions of possible modes. The default 
	 * is read only.
	 * @param bool $use_include_path [optional] parameter.use_include_path
	 * @param resource $context [optional] parameter.context
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $open_mode = null, bool $use_include_path = null, $context = null) {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class_name [optional] The class name to use when SplFileInfo::openFile
	 * is called.
	 * @return void 
	 */
	public function setFileClass (string $class_name = null) {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class_name [optional] The class name to use when
	 * SplFileInfo::getFileInfo and
	 * SplFileInfo::getPathInfo are called.
	 * @return void 
	 */
	public function setInfoClass (string $class_name = null) {}

	final public function _bad_state_ex () {}

	/**
	 * Returns the path to the file as a string
	 * @link http://www.php.net/manual/en/splfileinfo.tostring.php
	 * @return void the path to the file.
	 */
	public function __toString () {}

}

/**
 * The DirectoryIterator class provides a simple interface for viewing
 * the contents of filesystem directories.
 * @link http://www.php.net/manual/en/class.directoryiterator.php
 */
class DirectoryIterator extends SplFileInfo implements Iterator, Traversable, SeekableIterator {

	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.directoryiterator.php#directoryiterator.props.name
	 */
	public $name;

	/**
	 * Constructs a new directory iterator from a path
	 * @link http://www.php.net/manual/en/directoryiterator.construct.php
	 * @param mixed $path
	 */
	public function __construct ($path) {}

	/**
	 * Return file name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getfilename.php
	 * @return string the file name of the current DirectoryIterator item.
	 */
	public function getFilename () {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/directoryiterator.getextension.php
	 * @return string a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension () {}

	/**
	 * Get base name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getbasename.php
	 * @param string $suffix [optional] If the base name ends in suffix, 
	 * this will be cut.
	 * @return string The base name of the current DirectoryIterator item.
	 */
	public function getBasename (string $suffix = null) {}

	/**
	 * Determine if current DirectoryIterator item is '.' or '..'
	 * @link http://www.php.net/manual/en/directoryiterator.isdot.php
	 * @return bool true if the entry is . or ..,
	 * otherwise false
	 */
	public function isDot () {}

	/**
	 * Rewind the DirectoryIterator back to the start
	 * @link http://www.php.net/manual/en/directoryiterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Check whether current DirectoryIterator position is a valid file
	 * @link http://www.php.net/manual/en/directoryiterator.valid.php
	 * @return bool true if the position is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Return the key for the current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.key.php
	 * @return string The key for the current DirectoryIterator item.
	 */
	public function key () {}

	/**
	 * Return the current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.current.php
	 * @return DirectoryIterator The current DirectoryIterator item.
	 */
	public function current () {}

	/**
	 * Move forward to next DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Seek to a DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.seek.php
	 * @param int $position The zero-based numeric position to seek to.
	 * @return void 
	 */
	public function seek (int $position) {}

	/**
	 * Get file name as a string
	 * @link http://www.php.net/manual/en/directoryiterator.tostring.php
	 * @return string the file name of the current DirectoryIterator item.
	 */
	public function __toString () {}

	/**
	 * Gets the path without filename
	 * @link http://www.php.net/manual/en/splfileinfo.getpath.php
	 * @return string the path to the file.
	 */
	public function getPath () {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname () {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return int the file permissions.
	 */
	public function getPerms () {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return int the inode number for the filesystem object.
	 */
	public function getInode () {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return int The filesize in bytes.
	 */
	public function getSize () {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return int The owner id in numerical format.
	 */
	public function getOwner () {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return int The group id in numerical format.
	 */
	public function getGroup () {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return int the time the file was last accessed.
	 */
	public function getATime () {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return int the last modified time for the file, in a Unix timestamp.
	 */
	public function getMTime () {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return int The last change time, in a Unix timestamp.
	 */
	public function getCTime () {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return string A string representing the type of the entry.
	 * May be one of file, link,
	 * or dir
	 */
	public function getType () {}

	/**
	 * Tells if the entry is writable
	 * @link http://www.php.net/manual/en/splfileinfo.iswritable.php
	 * @return bool true if writable, false otherwise;
	 */
	public function isWritable () {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool true if readable, false otherwise.
	 */
	public function isReadable () {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool true if executable, false otherwise.
	 */
	public function isExecutable () {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile () {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool true if a directory, false otherwise.
	 */
	public function isDir () {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool true if the file is a link, false otherwise.
	 */
	public function isLink () {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return string the target of the filesystem link.
	 */
	public function getLinkTarget () {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return string the path to the file, or false if the file does not exist.
	 */
	public function getRealPath () {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param string $class_name [optional] Name of an SplFileInfo derived class to use.
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo (string $class_name = null) {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param string $class_name [optional] Name of an SplFileInfo derived class to use.
	 * @return SplFileInfo an SplFileInfo object for the parent path of the file.
	 */
	public function getPathInfo (string $class_name = null) {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $open_mode [optional] The mode for opening the file. See the fopen
	 * documentation for descriptions of possible modes. The default 
	 * is read only.
	 * @param bool $use_include_path [optional] parameter.use_include_path
	 * @param resource $context [optional] parameter.context
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $open_mode = null, bool $use_include_path = null, $context = null) {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class_name [optional] The class name to use when SplFileInfo::openFile
	 * is called.
	 * @return void 
	 */
	public function setFileClass (string $class_name = null) {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class_name [optional] The class name to use when
	 * SplFileInfo::getFileInfo and
	 * SplFileInfo::getPathInfo are called.
	 * @return void 
	 */
	public function setInfoClass (string $class_name = null) {}

	final public function _bad_state_ex () {}

}

/**
 * The Filesystem iterator
 * @link http://www.php.net/manual/en/class.filesystemiterator.php
 */
class FilesystemIterator extends DirectoryIterator implements SeekableIterator, Traversable, Iterator {
	const CURRENT_MODE_MASK = 240;
	const CURRENT_AS_PATHNAME = 32;
	const CURRENT_AS_FILEINFO = 0;
	const CURRENT_AS_SELF = 16;
	const KEY_MODE_MASK = 3840;
	const KEY_AS_PATHNAME = 0;
	const FOLLOW_SYMLINKS = 512;
	const KEY_AS_FILENAME = 256;
	const NEW_CURRENT_AND_KEY = 256;
	const OTHER_MODE_MASK = 12288;
	const SKIP_DOTS = 4096;
	const UNIX_PATHS = 8192;


	/**
	 * Constructs a new filesystem iterator
	 * @link http://www.php.net/manual/en/filesystemiterator.construct.php
	 * @param mixed $path
	 * @param mixed $flags [optional]
	 */
	public function __construct ($path, $flags = null) {}

	/**
	 * Rewinds back to the beginning
	 * @link http://www.php.net/manual/en/filesystemiterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Move to the next file
	 * @link http://www.php.net/manual/en/filesystemiterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Retrieve the key for the current file
	 * @link http://www.php.net/manual/en/filesystemiterator.key.php
	 * @return string the pathname or filename depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function key () {}

	/**
	 * The current file
	 * @link http://www.php.net/manual/en/filesystemiterator.current.php
	 * @return mixed The filename, file information, or $this depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function current () {}

	/**
	 * Get the handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.getflags.php
	 * @return int The integer value of the set flags.
	 */
	public function getFlags () {}

	/**
	 * Sets handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.setflags.php
	 * @param int $flags [optional] The handling flags to set.
	 * See the FilesystemIterator constants.
	 * @return void 
	 */
	public function setFlags (int $flags = null) {}

	/**
	 * Return file name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getfilename.php
	 * @return string the file name of the current DirectoryIterator item.
	 */
	public function getFilename () {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/directoryiterator.getextension.php
	 * @return string a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension () {}

	/**
	 * Get base name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getbasename.php
	 * @param string $suffix [optional] If the base name ends in suffix, 
	 * this will be cut.
	 * @return string The base name of the current DirectoryIterator item.
	 */
	public function getBasename (string $suffix = null) {}

	/**
	 * Determine if current DirectoryIterator item is '.' or '..'
	 * @link http://www.php.net/manual/en/directoryiterator.isdot.php
	 * @return bool true if the entry is . or ..,
	 * otherwise false
	 */
	public function isDot () {}

	/**
	 * Check whether current DirectoryIterator position is a valid file
	 * @link http://www.php.net/manual/en/directoryiterator.valid.php
	 * @return bool true if the position is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Seek to a DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.seek.php
	 * @param int $position The zero-based numeric position to seek to.
	 * @return void 
	 */
	public function seek (int $position) {}

	/**
	 * Get file name as a string
	 * @link http://www.php.net/manual/en/directoryiterator.tostring.php
	 * @return string the file name of the current DirectoryIterator item.
	 */
	public function __toString () {}

	/**
	 * Gets the path without filename
	 * @link http://www.php.net/manual/en/splfileinfo.getpath.php
	 * @return string the path to the file.
	 */
	public function getPath () {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname () {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return int the file permissions.
	 */
	public function getPerms () {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return int the inode number for the filesystem object.
	 */
	public function getInode () {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return int The filesize in bytes.
	 */
	public function getSize () {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return int The owner id in numerical format.
	 */
	public function getOwner () {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return int The group id in numerical format.
	 */
	public function getGroup () {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return int the time the file was last accessed.
	 */
	public function getATime () {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return int the last modified time for the file, in a Unix timestamp.
	 */
	public function getMTime () {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return int The last change time, in a Unix timestamp.
	 */
	public function getCTime () {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return string A string representing the type of the entry.
	 * May be one of file, link,
	 * or dir
	 */
	public function getType () {}

	/**
	 * Tells if the entry is writable
	 * @link http://www.php.net/manual/en/splfileinfo.iswritable.php
	 * @return bool true if writable, false otherwise;
	 */
	public function isWritable () {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool true if readable, false otherwise.
	 */
	public function isReadable () {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool true if executable, false otherwise.
	 */
	public function isExecutable () {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile () {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool true if a directory, false otherwise.
	 */
	public function isDir () {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool true if the file is a link, false otherwise.
	 */
	public function isLink () {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return string the target of the filesystem link.
	 */
	public function getLinkTarget () {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return string the path to the file, or false if the file does not exist.
	 */
	public function getRealPath () {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param string $class_name [optional] Name of an SplFileInfo derived class to use.
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo (string $class_name = null) {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param string $class_name [optional] Name of an SplFileInfo derived class to use.
	 * @return SplFileInfo an SplFileInfo object for the parent path of the file.
	 */
	public function getPathInfo (string $class_name = null) {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $open_mode [optional] The mode for opening the file. See the fopen
	 * documentation for descriptions of possible modes. The default 
	 * is read only.
	 * @param bool $use_include_path [optional] parameter.use_include_path
	 * @param resource $context [optional] parameter.context
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $open_mode = null, bool $use_include_path = null, $context = null) {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class_name [optional] The class name to use when SplFileInfo::openFile
	 * is called.
	 * @return void 
	 */
	public function setFileClass (string $class_name = null) {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class_name [optional] The class name to use when
	 * SplFileInfo::getFileInfo and
	 * SplFileInfo::getPathInfo are called.
	 * @return void 
	 */
	public function setInfoClass (string $class_name = null) {}

	final public function _bad_state_ex () {}

}

/**
 * The RecursiveDirectoryIterator provides
 * an interface for iterating recursively over filesystem directories.
 * @link http://www.php.net/manual/en/class.recursivedirectoryiterator.php
 */
class RecursiveDirectoryIterator extends FilesystemIterator implements Iterator, Traversable, SeekableIterator, RecursiveIterator {
	const CURRENT_MODE_MASK = 240;
	const CURRENT_AS_PATHNAME = 32;
	const CURRENT_AS_FILEINFO = 0;
	const CURRENT_AS_SELF = 16;
	const KEY_MODE_MASK = 3840;
	const KEY_AS_PATHNAME = 0;
	const FOLLOW_SYMLINKS = 512;
	const KEY_AS_FILENAME = 256;
	const NEW_CURRENT_AND_KEY = 256;
	const OTHER_MODE_MASK = 12288;
	const SKIP_DOTS = 4096;
	const UNIX_PATHS = 8192;


	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.recursivedirectoryiterator.php#recursivedirectoryiterator.props.name
	 */
	public $name;

	/**
	 * Constructs a RecursiveDirectoryIterator
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.construct.php
	 * @param mixed $path
	 * @param mixed $flags [optional]
	 */
	public function __construct ($path, $flags = null) {}

	/**
	 * Returns whether current entry is a directory and not '.' or '..'
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.haschildren.php
	 * @param bool $allow_links [optional] 
	 * @return bool whether the current entry is a directory, but not '.' or '..'
	 */
	public function hasChildren (bool $allow_links = null) {}

	/**
	 * Returns an iterator for the current entry if it is a directory
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.getchildren.php
	 * @return mixed The filename, file information, or $this depending on the set flags.
	 * See the FilesystemIterator
	 * constants.
	 */
	public function getChildren () {}

	/**
	 * Get sub path
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.getsubpath.php
	 * @return string The sub path.
	 */
	public function getSubPath () {}

	/**
	 * Get sub path and name
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.getsubpathname.php
	 * @return string The sub path (sub directory) and filename.
	 */
	public function getSubPathname () {}

	/**
	 * Rewinds back to the beginning
	 * @link http://www.php.net/manual/en/filesystemiterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Move to the next file
	 * @link http://www.php.net/manual/en/filesystemiterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Retrieve the key for the current file
	 * @link http://www.php.net/manual/en/filesystemiterator.key.php
	 * @return string the pathname or filename depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function key () {}

	/**
	 * The current file
	 * @link http://www.php.net/manual/en/filesystemiterator.current.php
	 * @return mixed The filename, file information, or $this depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function current () {}

	/**
	 * Get the handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.getflags.php
	 * @return int The integer value of the set flags.
	 */
	public function getFlags () {}

	/**
	 * Sets handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.setflags.php
	 * @param int $flags [optional] The handling flags to set.
	 * See the FilesystemIterator constants.
	 * @return void 
	 */
	public function setFlags (int $flags = null) {}

	/**
	 * Return file name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getfilename.php
	 * @return string the file name of the current DirectoryIterator item.
	 */
	public function getFilename () {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/directoryiterator.getextension.php
	 * @return string a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension () {}

	/**
	 * Get base name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getbasename.php
	 * @param string $suffix [optional] If the base name ends in suffix, 
	 * this will be cut.
	 * @return string The base name of the current DirectoryIterator item.
	 */
	public function getBasename (string $suffix = null) {}

	/**
	 * Determine if current DirectoryIterator item is '.' or '..'
	 * @link http://www.php.net/manual/en/directoryiterator.isdot.php
	 * @return bool true if the entry is . or ..,
	 * otherwise false
	 */
	public function isDot () {}

	/**
	 * Check whether current DirectoryIterator position is a valid file
	 * @link http://www.php.net/manual/en/directoryiterator.valid.php
	 * @return bool true if the position is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Seek to a DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.seek.php
	 * @param int $position The zero-based numeric position to seek to.
	 * @return void 
	 */
	public function seek (int $position) {}

	/**
	 * Get file name as a string
	 * @link http://www.php.net/manual/en/directoryiterator.tostring.php
	 * @return string the file name of the current DirectoryIterator item.
	 */
	public function __toString () {}

	/**
	 * Gets the path without filename
	 * @link http://www.php.net/manual/en/splfileinfo.getpath.php
	 * @return string the path to the file.
	 */
	public function getPath () {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname () {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return int the file permissions.
	 */
	public function getPerms () {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return int the inode number for the filesystem object.
	 */
	public function getInode () {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return int The filesize in bytes.
	 */
	public function getSize () {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return int The owner id in numerical format.
	 */
	public function getOwner () {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return int The group id in numerical format.
	 */
	public function getGroup () {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return int the time the file was last accessed.
	 */
	public function getATime () {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return int the last modified time for the file, in a Unix timestamp.
	 */
	public function getMTime () {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return int The last change time, in a Unix timestamp.
	 */
	public function getCTime () {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return string A string representing the type of the entry.
	 * May be one of file, link,
	 * or dir
	 */
	public function getType () {}

	/**
	 * Tells if the entry is writable
	 * @link http://www.php.net/manual/en/splfileinfo.iswritable.php
	 * @return bool true if writable, false otherwise;
	 */
	public function isWritable () {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool true if readable, false otherwise.
	 */
	public function isReadable () {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool true if executable, false otherwise.
	 */
	public function isExecutable () {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile () {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool true if a directory, false otherwise.
	 */
	public function isDir () {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool true if the file is a link, false otherwise.
	 */
	public function isLink () {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return string the target of the filesystem link.
	 */
	public function getLinkTarget () {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return string the path to the file, or false if the file does not exist.
	 */
	public function getRealPath () {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param string $class_name [optional] Name of an SplFileInfo derived class to use.
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo (string $class_name = null) {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param string $class_name [optional] Name of an SplFileInfo derived class to use.
	 * @return SplFileInfo an SplFileInfo object for the parent path of the file.
	 */
	public function getPathInfo (string $class_name = null) {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $open_mode [optional] The mode for opening the file. See the fopen
	 * documentation for descriptions of possible modes. The default 
	 * is read only.
	 * @param bool $use_include_path [optional] parameter.use_include_path
	 * @param resource $context [optional] parameter.context
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $open_mode = null, bool $use_include_path = null, $context = null) {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class_name [optional] The class name to use when SplFileInfo::openFile
	 * is called.
	 * @return void 
	 */
	public function setFileClass (string $class_name = null) {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class_name [optional] The class name to use when
	 * SplFileInfo::getFileInfo and
	 * SplFileInfo::getPathInfo are called.
	 * @return void 
	 */
	public function setInfoClass (string $class_name = null) {}

	final public function _bad_state_ex () {}

}

/**
 * Iterates through a file system in a similar fashion to 
 * glob.
 * @link http://www.php.net/manual/en/class.globiterator.php
 */
class GlobIterator extends FilesystemIterator implements Iterator, Traversable, SeekableIterator, Countable {
	const CURRENT_MODE_MASK = 240;
	const CURRENT_AS_PATHNAME = 32;
	const CURRENT_AS_FILEINFO = 0;
	const CURRENT_AS_SELF = 16;
	const KEY_MODE_MASK = 3840;
	const KEY_AS_PATHNAME = 0;
	const FOLLOW_SYMLINKS = 512;
	const KEY_AS_FILENAME = 256;
	const NEW_CURRENT_AND_KEY = 256;
	const OTHER_MODE_MASK = 12288;
	const SKIP_DOTS = 4096;
	const UNIX_PATHS = 8192;


	/**
	 * Construct a directory using glob
	 * @link http://www.php.net/manual/en/globiterator.construct.php
	 * @param mixed $path
	 * @param mixed $flags [optional]
	 */
	public function __construct ($path, $flags = null) {}

	/**
	 * Get the number of directories and files
	 * @link http://www.php.net/manual/en/globiterator.count.php
	 * @return int The number of returned directories and files, as an
	 * integer.
	 */
	public function count () {}

	/**
	 * Rewinds back to the beginning
	 * @link http://www.php.net/manual/en/filesystemiterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Move to the next file
	 * @link http://www.php.net/manual/en/filesystemiterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Retrieve the key for the current file
	 * @link http://www.php.net/manual/en/filesystemiterator.key.php
	 * @return string the pathname or filename depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function key () {}

	/**
	 * The current file
	 * @link http://www.php.net/manual/en/filesystemiterator.current.php
	 * @return mixed The filename, file information, or $this depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function current () {}

	/**
	 * Get the handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.getflags.php
	 * @return int The integer value of the set flags.
	 */
	public function getFlags () {}

	/**
	 * Sets handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.setflags.php
	 * @param int $flags [optional] The handling flags to set.
	 * See the FilesystemIterator constants.
	 * @return void 
	 */
	public function setFlags (int $flags = null) {}

	/**
	 * Return file name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getfilename.php
	 * @return string the file name of the current DirectoryIterator item.
	 */
	public function getFilename () {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/directoryiterator.getextension.php
	 * @return string a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension () {}

	/**
	 * Get base name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getbasename.php
	 * @param string $suffix [optional] If the base name ends in suffix, 
	 * this will be cut.
	 * @return string The base name of the current DirectoryIterator item.
	 */
	public function getBasename (string $suffix = null) {}

	/**
	 * Determine if current DirectoryIterator item is '.' or '..'
	 * @link http://www.php.net/manual/en/directoryiterator.isdot.php
	 * @return bool true if the entry is . or ..,
	 * otherwise false
	 */
	public function isDot () {}

	/**
	 * Check whether current DirectoryIterator position is a valid file
	 * @link http://www.php.net/manual/en/directoryiterator.valid.php
	 * @return bool true if the position is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Seek to a DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.seek.php
	 * @param int $position The zero-based numeric position to seek to.
	 * @return void 
	 */
	public function seek (int $position) {}

	/**
	 * Get file name as a string
	 * @link http://www.php.net/manual/en/directoryiterator.tostring.php
	 * @return string the file name of the current DirectoryIterator item.
	 */
	public function __toString () {}

	/**
	 * Gets the path without filename
	 * @link http://www.php.net/manual/en/splfileinfo.getpath.php
	 * @return string the path to the file.
	 */
	public function getPath () {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname () {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return int the file permissions.
	 */
	public function getPerms () {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return int the inode number for the filesystem object.
	 */
	public function getInode () {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return int The filesize in bytes.
	 */
	public function getSize () {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return int The owner id in numerical format.
	 */
	public function getOwner () {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return int The group id in numerical format.
	 */
	public function getGroup () {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return int the time the file was last accessed.
	 */
	public function getATime () {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return int the last modified time for the file, in a Unix timestamp.
	 */
	public function getMTime () {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return int The last change time, in a Unix timestamp.
	 */
	public function getCTime () {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return string A string representing the type of the entry.
	 * May be one of file, link,
	 * or dir
	 */
	public function getType () {}

	/**
	 * Tells if the entry is writable
	 * @link http://www.php.net/manual/en/splfileinfo.iswritable.php
	 * @return bool true if writable, false otherwise;
	 */
	public function isWritable () {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool true if readable, false otherwise.
	 */
	public function isReadable () {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool true if executable, false otherwise.
	 */
	public function isExecutable () {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile () {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool true if a directory, false otherwise.
	 */
	public function isDir () {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool true if the file is a link, false otherwise.
	 */
	public function isLink () {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return string the target of the filesystem link.
	 */
	public function getLinkTarget () {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return string the path to the file, or false if the file does not exist.
	 */
	public function getRealPath () {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param string $class_name [optional] Name of an SplFileInfo derived class to use.
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo (string $class_name = null) {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param string $class_name [optional] Name of an SplFileInfo derived class to use.
	 * @return SplFileInfo an SplFileInfo object for the parent path of the file.
	 */
	public function getPathInfo (string $class_name = null) {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $open_mode [optional] The mode for opening the file. See the fopen
	 * documentation for descriptions of possible modes. The default 
	 * is read only.
	 * @param bool $use_include_path [optional] parameter.use_include_path
	 * @param resource $context [optional] parameter.context
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $open_mode = null, bool $use_include_path = null, $context = null) {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class_name [optional] The class name to use when SplFileInfo::openFile
	 * is called.
	 * @return void 
	 */
	public function setFileClass (string $class_name = null) {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class_name [optional] The class name to use when
	 * SplFileInfo::getFileInfo and
	 * SplFileInfo::getPathInfo are called.
	 * @return void 
	 */
	public function setInfoClass (string $class_name = null) {}

	final public function _bad_state_ex () {}

}

/**
 * The SplFileObject class offers an object oriented interface for a file.
 * @link http://www.php.net/manual/en/class.splfileobject.php
 */
class SplFileObject extends SplFileInfo implements RecursiveIterator, Traversable, Iterator, SeekableIterator {
	const DROP_NEW_LINE = 1;
	const READ_AHEAD = 2;
	const SKIP_EMPTY = 4;
	const READ_CSV = 8;


	/**
	 * Construct a new file object
	 * @link http://www.php.net/manual/en/splfileobject.construct.php
	 * @param mixed $file_name
	 * @param mixed $open_mode [optional]
	 * @param mixed $use_include_path [optional]
	 * @param mixed $context [optional]
	 */
	public function __construct ($file_name, $open_mode = null, $use_include_path = null, $context = null) {}

	/**
	 * Rewind the file to the first line
	 * @link http://www.php.net/manual/en/splfileobject.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Reached end of file
	 * @link http://www.php.net/manual/en/splfileobject.eof.php
	 * @return bool true if file is at EOF, false otherwise.
	 */
	public function eof () {}

	/**
	 * Not at EOF
	 * @link http://www.php.net/manual/en/splfileobject.valid.php
	 * @return bool true if not reached EOF, false otherwise.
	 */
	public function valid () {}

	/**
	 * Gets line from file
	 * @link http://www.php.net/manual/en/splfileobject.fgets.php
	 * @return string a string containing the next line from the file, or false on error.
	 */
	public function fgets () {}

	/**
	 * Gets line from file and parse as CSV fields
	 * @link http://www.php.net/manual/en/splfileobject.fgetcsv.php
	 * @param string $delimiter [optional] The field delimiter (one character only). Defaults as a comma or the value set using SplFileObject::setCsvControl.
	 * @param string $enclosure [optional] The field enclosure character (one character only). Defaults as a double quotation mark or the value set using SplFileObject::setCsvControl.
	 * @param string $escape [optional] <p>
	 * The escape character (one character only). Defaults as a backslash (\) or the value set using SplFileObject::setCsvControl.
	 * </p>
	 * Usually an enclosure character is escpaped inside
	 * a field by doubling it; however, the escape
	 * character can be used as an alternative. So for the default parameter
	 * values "" and \" have the same
	 * meaning. Other than allowing to escape the
	 * enclosure character the
	 * escape character has no special meaning; it isn't
	 * even meant to escape itself.
	 * @return array an indexed array containing the fields read, or false on error.
	 * <p>
	 * A blank line in a CSV file will be returned as an array
	 * comprising a single null field unless using SplFileObject::SKIP_EMPTY | SplFileObject::DROP_NEW_LINE, 
	 * in which case empty lines are skipped.
	 * </p>
	 */
	public function fgetcsv (string $delimiter = null, string $enclosure = null, string $escape = null) {}

	/**
	 * Write a field array as a CSV line
	 * @link http://www.php.net/manual/en/splfileobject.fputcsv.php
	 * @param array $fields An array of values.
	 * @param string $delimiter [optional] The optional delimiter parameter sets the field
	 * delimiter (one character only).
	 * @param string $enclosure [optional] The optional enclosure parameter sets the field
	 * enclosure (one character only).
	 * @param string $escape [optional] The optional escape parameter sets the
	 * escape character (one character only).
	 * @return int the length of the written string or false on failure.
	 * <p>
	 * Returns false, and does not write the CSV line to the file, if the
	 * delimiter or enclosure
	 * parameter is not a single character.
	 * </p>
	 */
	public function fputcsv (array $fields, string $delimiter = null, string $enclosure = null, string $escape = null) {}

	/**
	 * Set the delimiter, enclosure and escape character for CSV
	 * @link http://www.php.net/manual/en/splfileobject.setcsvcontrol.php
	 * @param string $delimiter [optional] The field delimiter (one character only).
	 * @param string $enclosure [optional] The field enclosure character (one character only).
	 * @param string $escape [optional] The field escape character (one character only).
	 * @return void 
	 */
	public function setCsvControl (string $delimiter = null, string $enclosure = null, string $escape = null) {}

	/**
	 * Get the delimiter, enclosure and escape character for CSV
	 * @link http://www.php.net/manual/en/splfileobject.getcsvcontrol.php
	 * @return array an indexed array containing the delimiter, enclosure and escape character.
	 */
	public function getCsvControl () {}

	/**
	 * Portable file locking
	 * @link http://www.php.net/manual/en/splfileobject.flock.php
	 * @param int $operation <p>
	 * operation is one of the following:
	 * <p>
	 * <br>
	 * LOCK_SH to acquire a shared lock (reader).
	 * <br>
	 * LOCK_EX to acquire an exclusive lock (writer).
	 * <br>
	 * LOCK_UN to release a lock (shared or exclusive).
	 * <br>
	 * LOCK_NB to not block while locking.
	 * </p>
	 * </p>
	 * @param int $wouldblock [optional] Set to true if the lock would block (EWOULDBLOCK errno condition).
	 * @return bool true on success or false on failure
	 */
	public function flock (int $operation, int &$wouldblock = null) {}

	/**
	 * Flushes the output to the file
	 * @link http://www.php.net/manual/en/splfileobject.fflush.php
	 * @return bool true on success or false on failure
	 */
	public function fflush () {}

	/**
	 * Return current file position
	 * @link http://www.php.net/manual/en/splfileobject.ftell.php
	 * @return int the position of the file pointer as an integer, or false on error.
	 */
	public function ftell () {}

	/**
	 * Seek to a position
	 * @link http://www.php.net/manual/en/splfileobject.fseek.php
	 * @param int $offset The offset. A negative value can be used to move backwards through the file which
	 * is useful when SEEK_END is used as the whence value.
	 * @param int $whence [optional] <p>
	 * whence values are:
	 * <p>
	 * SEEK_SET - Set position equal to offset bytes.
	 * SEEK_CUR - Set position to current location plus offset.
	 * SEEK_END - Set position to end-of-file plus offset.
	 * </p>
	 * </p>
	 * <p>
	 * If whence is not specified, it is assumed to be SEEK_SET.
	 * </p>
	 * @return int 0 if the seek was successful, -1 otherwise. Note that seeking
	 * past EOF is not considered an error.
	 */
	public function fseek (int $offset, int $whence = null) {}

	/**
	 * Gets character from file
	 * @link http://www.php.net/manual/en/splfileobject.fgetc.php
	 * @return string a string containing a single character read from the file or false on EOF.
	 */
	public function fgetc () {}

	/**
	 * Output all remaining data on a file pointer
	 * @link http://www.php.net/manual/en/splfileobject.fpassthru.php
	 * @return int the number of characters read from handle
	 * and passed through to the output.
	 */
	public function fpassthru () {}

	/**
	 * Gets line from file and strip HTML tags
	 * @link http://www.php.net/manual/en/splfileobject.fgetss.php
	 * @param string $allowable_tags [optional] Optional parameter to specify tags which should not be stripped.
	 * @return string a string containing the next line of the file with HTML and PHP
	 * code stripped, or false on error.
	 */
	public function fgetss (string $allowable_tags = null) {}

	/**
	 * Parses input from file according to a format
	 * @link http://www.php.net/manual/en/splfileobject.fscanf.php
	 * @param string $format The specified format as described in the sprintf documentation.
	 * @param mixed $_ [optional] 
	 * @return mixed If only one parameter is passed to this method, the values parsed will be
	 * returned as an array. Otherwise, if optional parameters are passed, the
	 * function will return the number of assigned values. The optional
	 * parameters must be passed by reference.
	 */
	public function fscanf (string $format, &$_ = null) {}

	/**
	 * Write to file
	 * @link http://www.php.net/manual/en/splfileobject.fwrite.php
	 * @param string $str The string to be written to the file.
	 * @param int $length [optional] If the length argument is given, writing will
	 * stop after length bytes have been written or
	 * the end of string is reached, whichever comes
	 * first.
	 * @return int the number of bytes written, or 0 on error.
	 */
	public function fwrite (string $str, int $length = null) {}

	/**
	 * Read from file
	 * @link http://www.php.net/manual/en/splfileobject.fread.php
	 * @param int $length The number of bytes to read.
	 * @return string the string read from the file or false on failure.
	 */
	public function fread (int $length) {}

	/**
	 * Gets information about the file
	 * @link http://www.php.net/manual/en/splfileobject.fstat.php
	 * @return array an array with the statistics of the file; the format of the array
	 * is described in detail on the stat manual page.
	 */
	public function fstat () {}

	/**
	 * Truncates the file to a given length
	 * @link http://www.php.net/manual/en/splfileobject.ftruncate.php
	 * @param int $size <p>
	 * The size to truncate to.
	 * </p>
	 * <p>
	 * If size is larger than the file it is extended with null bytes.
	 * </p>
	 * <p>
	 * If size is smaller than the file, the extra data will be lost.
	 * </p>
	 * @return bool true on success or false on failure
	 */
	public function ftruncate (int $size) {}

	/**
	 * Retrieve current line of file
	 * @link http://www.php.net/manual/en/splfileobject.current.php
	 * @return string|array Retrieves the current line of the file. If the SplFileObject::READ_CSV flag is set, this method returns an array containing the current line parsed as CSV data.
	 */
	public function current () {}

	/**
	 * Get line number
	 * @link http://www.php.net/manual/en/splfileobject.key.php
	 * @return int the current line number.
	 */
	public function key () {}

	/**
	 * Read next line
	 * @link http://www.php.net/manual/en/splfileobject.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Sets flags for the SplFileObject
	 * @link http://www.php.net/manual/en/splfileobject.setflags.php
	 * @param int $flags Bit mask of the flags to set. See 
	 * SplFileObject constants 
	 * for the available flags.
	 * @return void 
	 */
	public function setFlags (int $flags) {}

	/**
	 * Gets flags for the SplFileObject
	 * @link http://www.php.net/manual/en/splfileobject.getflags.php
	 * @return int an integer representing the flags.
	 */
	public function getFlags () {}

	/**
	 * Set maximum line length
	 * @link http://www.php.net/manual/en/splfileobject.setmaxlinelen.php
	 * @param int $max_len The maximum length of a line.
	 * @return void 
	 */
	public function setMaxLineLen (int $max_len) {}

	/**
	 * Get maximum line length
	 * @link http://www.php.net/manual/en/splfileobject.getmaxlinelen.php
	 * @return int the maximum line length if one has been set with
	 * SplFileObject::setMaxLineLen, default is 0.
	 */
	public function getMaxLineLen () {}

	/**
	 * SplFileObject does not have children
	 * @link http://www.php.net/manual/en/splfileobject.haschildren.php
	 * @return bool false
	 */
	public function hasChildren () {}

	/**
	 * No purpose
	 * @link http://www.php.net/manual/en/splfileobject.getchildren.php
	 * @return void 
	 */
	public function getChildren () {}

	/**
	 * Seek to specified line
	 * @link http://www.php.net/manual/en/splfileobject.seek.php
	 * @param int $line_pos The zero-based line number to seek to.
	 * @return void 
	 */
	public function seek (int $line_pos) {}

	/**
	 * Alias of SplFileObject::fgets
	 * @link http://www.php.net/manual/en/splfileobject.getcurrentline.php
	 */
	public function getCurrentLine () {}

	/**
	 * Alias of SplFileObject::current
	 * @link http://www.php.net/manual/en/splfileobject.tostring.php
	 */
	public function __toString () {}

	/**
	 * Gets the path without filename
	 * @link http://www.php.net/manual/en/splfileinfo.getpath.php
	 * @return string the path to the file.
	 */
	public function getPath () {}

	/**
	 * Gets the filename
	 * @link http://www.php.net/manual/en/splfileinfo.getfilename.php
	 * @return string The filename.
	 */
	public function getFilename () {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/splfileinfo.getextension.php
	 * @return string a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension () {}

	/**
	 * Gets the base name of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getbasename.php
	 * @param string $suffix [optional] Optional suffix to omit from the base name returned.
	 * @return string the base name without path information.
	 */
	public function getBasename (string $suffix = null) {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname () {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return int the file permissions.
	 */
	public function getPerms () {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return int the inode number for the filesystem object.
	 */
	public function getInode () {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return int The filesize in bytes.
	 */
	public function getSize () {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return int The owner id in numerical format.
	 */
	public function getOwner () {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return int The group id in numerical format.
	 */
	public function getGroup () {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return int the time the file was last accessed.
	 */
	public function getATime () {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return int the last modified time for the file, in a Unix timestamp.
	 */
	public function getMTime () {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return int The last change time, in a Unix timestamp.
	 */
	public function getCTime () {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return string A string representing the type of the entry.
	 * May be one of file, link,
	 * or dir
	 */
	public function getType () {}

	/**
	 * Tells if the entry is writable
	 * @link http://www.php.net/manual/en/splfileinfo.iswritable.php
	 * @return bool true if writable, false otherwise;
	 */
	public function isWritable () {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool true if readable, false otherwise.
	 */
	public function isReadable () {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool true if executable, false otherwise.
	 */
	public function isExecutable () {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile () {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool true if a directory, false otherwise.
	 */
	public function isDir () {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool true if the file is a link, false otherwise.
	 */
	public function isLink () {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return string the target of the filesystem link.
	 */
	public function getLinkTarget () {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return string the path to the file, or false if the file does not exist.
	 */
	public function getRealPath () {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param string $class_name [optional] Name of an SplFileInfo derived class to use.
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo (string $class_name = null) {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param string $class_name [optional] Name of an SplFileInfo derived class to use.
	 * @return SplFileInfo an SplFileInfo object for the parent path of the file.
	 */
	public function getPathInfo (string $class_name = null) {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $open_mode [optional] The mode for opening the file. See the fopen
	 * documentation for descriptions of possible modes. The default 
	 * is read only.
	 * @param bool $use_include_path [optional] parameter.use_include_path
	 * @param resource $context [optional] parameter.context
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $open_mode = null, bool $use_include_path = null, $context = null) {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class_name [optional] The class name to use when SplFileInfo::openFile
	 * is called.
	 * @return void 
	 */
	public function setFileClass (string $class_name = null) {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class_name [optional] The class name to use when
	 * SplFileInfo::getFileInfo and
	 * SplFileInfo::getPathInfo are called.
	 * @return void 
	 */
	public function setInfoClass (string $class_name = null) {}

	final public function _bad_state_ex () {}

}

/**
 * The SplTempFileObject class offers an object oriented interface for a temporary file.
 * @link http://www.php.net/manual/en/class.spltempfileobject.php
 */
class SplTempFileObject extends SplFileObject implements SeekableIterator, Iterator, Traversable, RecursiveIterator {
	const DROP_NEW_LINE = 1;
	const READ_AHEAD = 2;
	const SKIP_EMPTY = 4;
	const READ_CSV = 8;


	/**
	 * Construct a new temporary file object
	 * @link http://www.php.net/manual/en/spltempfileobject.construct.php
	 * @param mixed $max_memory [optional]
	 */
	public function __construct ($max_memory = null) {}

	/**
	 * Rewind the file to the first line
	 * @link http://www.php.net/manual/en/splfileobject.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Reached end of file
	 * @link http://www.php.net/manual/en/splfileobject.eof.php
	 * @return bool true if file is at EOF, false otherwise.
	 */
	public function eof () {}

	/**
	 * Not at EOF
	 * @link http://www.php.net/manual/en/splfileobject.valid.php
	 * @return bool true if not reached EOF, false otherwise.
	 */
	public function valid () {}

	/**
	 * Gets line from file
	 * @link http://www.php.net/manual/en/splfileobject.fgets.php
	 * @return string a string containing the next line from the file, or false on error.
	 */
	public function fgets () {}

	/**
	 * Gets line from file and parse as CSV fields
	 * @link http://www.php.net/manual/en/splfileobject.fgetcsv.php
	 * @param string $delimiter [optional] The field delimiter (one character only). Defaults as a comma or the value set using SplFileObject::setCsvControl.
	 * @param string $enclosure [optional] The field enclosure character (one character only). Defaults as a double quotation mark or the value set using SplFileObject::setCsvControl.
	 * @param string $escape [optional] <p>
	 * The escape character (one character only). Defaults as a backslash (\) or the value set using SplFileObject::setCsvControl.
	 * </p>
	 * Usually an enclosure character is escpaped inside
	 * a field by doubling it; however, the escape
	 * character can be used as an alternative. So for the default parameter
	 * values "" and \" have the same
	 * meaning. Other than allowing to escape the
	 * enclosure character the
	 * escape character has no special meaning; it isn't
	 * even meant to escape itself.
	 * @return array an indexed array containing the fields read, or false on error.
	 * <p>
	 * A blank line in a CSV file will be returned as an array
	 * comprising a single null field unless using SplFileObject::SKIP_EMPTY | SplFileObject::DROP_NEW_LINE, 
	 * in which case empty lines are skipped.
	 * </p>
	 */
	public function fgetcsv (string $delimiter = null, string $enclosure = null, string $escape = null) {}

	/**
	 * Write a field array as a CSV line
	 * @link http://www.php.net/manual/en/splfileobject.fputcsv.php
	 * @param array $fields An array of values.
	 * @param string $delimiter [optional] The optional delimiter parameter sets the field
	 * delimiter (one character only).
	 * @param string $enclosure [optional] The optional enclosure parameter sets the field
	 * enclosure (one character only).
	 * @param string $escape [optional] The optional escape parameter sets the
	 * escape character (one character only).
	 * @return int the length of the written string or false on failure.
	 * <p>
	 * Returns false, and does not write the CSV line to the file, if the
	 * delimiter or enclosure
	 * parameter is not a single character.
	 * </p>
	 */
	public function fputcsv (array $fields, string $delimiter = null, string $enclosure = null, string $escape = null) {}

	/**
	 * Set the delimiter, enclosure and escape character for CSV
	 * @link http://www.php.net/manual/en/splfileobject.setcsvcontrol.php
	 * @param string $delimiter [optional] The field delimiter (one character only).
	 * @param string $enclosure [optional] The field enclosure character (one character only).
	 * @param string $escape [optional] The field escape character (one character only).
	 * @return void 
	 */
	public function setCsvControl (string $delimiter = null, string $enclosure = null, string $escape = null) {}

	/**
	 * Get the delimiter, enclosure and escape character for CSV
	 * @link http://www.php.net/manual/en/splfileobject.getcsvcontrol.php
	 * @return array an indexed array containing the delimiter, enclosure and escape character.
	 */
	public function getCsvControl () {}

	/**
	 * Portable file locking
	 * @link http://www.php.net/manual/en/splfileobject.flock.php
	 * @param int $operation <p>
	 * operation is one of the following:
	 * <p>
	 * <br>
	 * LOCK_SH to acquire a shared lock (reader).
	 * <br>
	 * LOCK_EX to acquire an exclusive lock (writer).
	 * <br>
	 * LOCK_UN to release a lock (shared or exclusive).
	 * <br>
	 * LOCK_NB to not block while locking.
	 * </p>
	 * </p>
	 * @param int $wouldblock [optional] Set to true if the lock would block (EWOULDBLOCK errno condition).
	 * @return bool true on success or false on failure
	 */
	public function flock (int $operation, int &$wouldblock = null) {}

	/**
	 * Flushes the output to the file
	 * @link http://www.php.net/manual/en/splfileobject.fflush.php
	 * @return bool true on success or false on failure
	 */
	public function fflush () {}

	/**
	 * Return current file position
	 * @link http://www.php.net/manual/en/splfileobject.ftell.php
	 * @return int the position of the file pointer as an integer, or false on error.
	 */
	public function ftell () {}

	/**
	 * Seek to a position
	 * @link http://www.php.net/manual/en/splfileobject.fseek.php
	 * @param int $offset The offset. A negative value can be used to move backwards through the file which
	 * is useful when SEEK_END is used as the whence value.
	 * @param int $whence [optional] <p>
	 * whence values are:
	 * <p>
	 * SEEK_SET - Set position equal to offset bytes.
	 * SEEK_CUR - Set position to current location plus offset.
	 * SEEK_END - Set position to end-of-file plus offset.
	 * </p>
	 * </p>
	 * <p>
	 * If whence is not specified, it is assumed to be SEEK_SET.
	 * </p>
	 * @return int 0 if the seek was successful, -1 otherwise. Note that seeking
	 * past EOF is not considered an error.
	 */
	public function fseek (int $offset, int $whence = null) {}

	/**
	 * Gets character from file
	 * @link http://www.php.net/manual/en/splfileobject.fgetc.php
	 * @return string a string containing a single character read from the file or false on EOF.
	 */
	public function fgetc () {}

	/**
	 * Output all remaining data on a file pointer
	 * @link http://www.php.net/manual/en/splfileobject.fpassthru.php
	 * @return int the number of characters read from handle
	 * and passed through to the output.
	 */
	public function fpassthru () {}

	/**
	 * Gets line from file and strip HTML tags
	 * @link http://www.php.net/manual/en/splfileobject.fgetss.php
	 * @param string $allowable_tags [optional] Optional parameter to specify tags which should not be stripped.
	 * @return string a string containing the next line of the file with HTML and PHP
	 * code stripped, or false on error.
	 */
	public function fgetss (string $allowable_tags = null) {}

	/**
	 * Parses input from file according to a format
	 * @link http://www.php.net/manual/en/splfileobject.fscanf.php
	 * @param string $format The specified format as described in the sprintf documentation.
	 * @param mixed $_ [optional] 
	 * @return mixed If only one parameter is passed to this method, the values parsed will be
	 * returned as an array. Otherwise, if optional parameters are passed, the
	 * function will return the number of assigned values. The optional
	 * parameters must be passed by reference.
	 */
	public function fscanf (string $format, &$_ = null) {}

	/**
	 * Write to file
	 * @link http://www.php.net/manual/en/splfileobject.fwrite.php
	 * @param string $str The string to be written to the file.
	 * @param int $length [optional] If the length argument is given, writing will
	 * stop after length bytes have been written or
	 * the end of string is reached, whichever comes
	 * first.
	 * @return int the number of bytes written, or 0 on error.
	 */
	public function fwrite (string $str, int $length = null) {}

	/**
	 * Read from file
	 * @link http://www.php.net/manual/en/splfileobject.fread.php
	 * @param int $length The number of bytes to read.
	 * @return string the string read from the file or false on failure.
	 */
	public function fread (int $length) {}

	/**
	 * Gets information about the file
	 * @link http://www.php.net/manual/en/splfileobject.fstat.php
	 * @return array an array with the statistics of the file; the format of the array
	 * is described in detail on the stat manual page.
	 */
	public function fstat () {}

	/**
	 * Truncates the file to a given length
	 * @link http://www.php.net/manual/en/splfileobject.ftruncate.php
	 * @param int $size <p>
	 * The size to truncate to.
	 * </p>
	 * <p>
	 * If size is larger than the file it is extended with null bytes.
	 * </p>
	 * <p>
	 * If size is smaller than the file, the extra data will be lost.
	 * </p>
	 * @return bool true on success or false on failure
	 */
	public function ftruncate (int $size) {}

	/**
	 * Retrieve current line of file
	 * @link http://www.php.net/manual/en/splfileobject.current.php
	 * @return string|array Retrieves the current line of the file. If the SplFileObject::READ_CSV flag is set, this method returns an array containing the current line parsed as CSV data.
	 */
	public function current () {}

	/**
	 * Get line number
	 * @link http://www.php.net/manual/en/splfileobject.key.php
	 * @return int the current line number.
	 */
	public function key () {}

	/**
	 * Read next line
	 * @link http://www.php.net/manual/en/splfileobject.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Sets flags for the SplFileObject
	 * @link http://www.php.net/manual/en/splfileobject.setflags.php
	 * @param int $flags Bit mask of the flags to set. See 
	 * SplFileObject constants 
	 * for the available flags.
	 * @return void 
	 */
	public function setFlags (int $flags) {}

	/**
	 * Gets flags for the SplFileObject
	 * @link http://www.php.net/manual/en/splfileobject.getflags.php
	 * @return int an integer representing the flags.
	 */
	public function getFlags () {}

	/**
	 * Set maximum line length
	 * @link http://www.php.net/manual/en/splfileobject.setmaxlinelen.php
	 * @param int $max_len The maximum length of a line.
	 * @return void 
	 */
	public function setMaxLineLen (int $max_len) {}

	/**
	 * Get maximum line length
	 * @link http://www.php.net/manual/en/splfileobject.getmaxlinelen.php
	 * @return int the maximum line length if one has been set with
	 * SplFileObject::setMaxLineLen, default is 0.
	 */
	public function getMaxLineLen () {}

	/**
	 * SplFileObject does not have children
	 * @link http://www.php.net/manual/en/splfileobject.haschildren.php
	 * @return bool false
	 */
	public function hasChildren () {}

	/**
	 * No purpose
	 * @link http://www.php.net/manual/en/splfileobject.getchildren.php
	 * @return void 
	 */
	public function getChildren () {}

	/**
	 * Seek to specified line
	 * @link http://www.php.net/manual/en/splfileobject.seek.php
	 * @param int $line_pos The zero-based line number to seek to.
	 * @return void 
	 */
	public function seek (int $line_pos) {}

	/**
	 * Alias of SplFileObject::fgets
	 * @link http://www.php.net/manual/en/splfileobject.getcurrentline.php
	 */
	public function getCurrentLine () {}

	/**
	 * Alias of SplFileObject::current
	 * @link http://www.php.net/manual/en/splfileobject.tostring.php
	 */
	public function __toString () {}

	/**
	 * Gets the path without filename
	 * @link http://www.php.net/manual/en/splfileinfo.getpath.php
	 * @return string the path to the file.
	 */
	public function getPath () {}

	/**
	 * Gets the filename
	 * @link http://www.php.net/manual/en/splfileinfo.getfilename.php
	 * @return string The filename.
	 */
	public function getFilename () {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/splfileinfo.getextension.php
	 * @return string a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension () {}

	/**
	 * Gets the base name of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getbasename.php
	 * @param string $suffix [optional] Optional suffix to omit from the base name returned.
	 * @return string the base name without path information.
	 */
	public function getBasename (string $suffix = null) {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname () {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return int the file permissions.
	 */
	public function getPerms () {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return int the inode number for the filesystem object.
	 */
	public function getInode () {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return int The filesize in bytes.
	 */
	public function getSize () {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return int The owner id in numerical format.
	 */
	public function getOwner () {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return int The group id in numerical format.
	 */
	public function getGroup () {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return int the time the file was last accessed.
	 */
	public function getATime () {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return int the last modified time for the file, in a Unix timestamp.
	 */
	public function getMTime () {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return int The last change time, in a Unix timestamp.
	 */
	public function getCTime () {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return string A string representing the type of the entry.
	 * May be one of file, link,
	 * or dir
	 */
	public function getType () {}

	/**
	 * Tells if the entry is writable
	 * @link http://www.php.net/manual/en/splfileinfo.iswritable.php
	 * @return bool true if writable, false otherwise;
	 */
	public function isWritable () {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool true if readable, false otherwise.
	 */
	public function isReadable () {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool true if executable, false otherwise.
	 */
	public function isExecutable () {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile () {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool true if a directory, false otherwise.
	 */
	public function isDir () {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool true if the file is a link, false otherwise.
	 */
	public function isLink () {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return string the target of the filesystem link.
	 */
	public function getLinkTarget () {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return string the path to the file, or false if the file does not exist.
	 */
	public function getRealPath () {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param string $class_name [optional] Name of an SplFileInfo derived class to use.
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo (string $class_name = null) {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param string $class_name [optional] Name of an SplFileInfo derived class to use.
	 * @return SplFileInfo an SplFileInfo object for the parent path of the file.
	 */
	public function getPathInfo (string $class_name = null) {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $open_mode [optional] The mode for opening the file. See the fopen
	 * documentation for descriptions of possible modes. The default 
	 * is read only.
	 * @param bool $use_include_path [optional] parameter.use_include_path
	 * @param resource $context [optional] parameter.context
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $open_mode = null, bool $use_include_path = null, $context = null) {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class_name [optional] The class name to use when SplFileInfo::openFile
	 * is called.
	 * @return void 
	 */
	public function setFileClass (string $class_name = null) {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class_name [optional] The class name to use when
	 * SplFileInfo::getFileInfo and
	 * SplFileInfo::getPathInfo are called.
	 * @return void 
	 */
	public function setInfoClass (string $class_name = null) {}

	final public function _bad_state_ex () {}

}

/**
 * The SplDoublyLinkedList class provides the main functionalities of a doubly linked list.
 * @link http://www.php.net/manual/en/class.spldoublylinkedlist.php
 */
class SplDoublyLinkedList implements Iterator, Traversable, Countable, ArrayAccess, Serializable {
	const IT_MODE_LIFO = 2;
	const IT_MODE_FIFO = 0;
	const IT_MODE_DELETE = 1;
	const IT_MODE_KEEP = 0;


	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.spldoublylinkedlist.php#spldoublylinkedlist.props.name
	 */
	public $name;

	/**
	 * Pops a node from the end of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.pop.php
	 * @return mixed The value of the popped node.
	 */
	public function pop () {}

	/**
	 * Shifts a node from the beginning of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.shift.php
	 * @return mixed The value of the shifted node.
	 */
	public function shift () {}

	/**
	 * Pushes an element at the end of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.push.php
	 * @param mixed $value The value to push.
	 * @return void 
	 */
	public function push ($value) {}

	/**
	 * Prepends the doubly linked list with an element
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.unshift.php
	 * @param mixed $value The value to unshift.
	 * @return void 
	 */
	public function unshift ($value) {}

	/**
	 * Peeks at the node from the end of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.top.php
	 * @return mixed The value of the last node.
	 */
	public function top () {}

	/**
	 * Peeks at the node from the beginning of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.bottom.php
	 * @return mixed The value of the first node.
	 */
	public function bottom () {}

	/**
	 * Checks whether the doubly linked list is empty
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.isempty.php
	 * @return bool whether the doubly linked list is empty.
	 */
	public function isEmpty () {}

	/**
	 * Sets the mode of iteration
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.setiteratormode.php
	 * @param int $mode <p>
	 * There are two orthogonal sets of modes that can be set:
	 * </p>
	 * <p>
	 * <br>
	 * The direction of the iteration (either one or the other):
	 * <p>
	 * <br>SplDoublyLinkedList::IT_MODE_LIFO (Stack style)
	 * <br>SplDoublyLinkedList::IT_MODE_FIFO (Queue style)
	 * </p>
	 * <br>
	 * The behavior of the iterator (either one or the other):
	 * <p>
	 * <br>SplDoublyLinkedList::IT_MODE_DELETE (Elements are deleted by the iterator)
	 * <br>SplDoublyLinkedList::IT_MODE_KEEP (Elements are traversed by the iterator)
	 * </p>
	 * </p>
	 * <p>
	 * The default mode is: SplDoublyLinkedList::IT_MODE_FIFO | SplDoublyLinkedList::IT_MODE_KEEP
	 * </p>
	 * @return void 
	 */
	public function setIteratorMode (int $mode) {}

	/**
	 * Returns the mode of iteration
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.getiteratormode.php
	 * @return int the different modes and flags that affect the iteration.
	 */
	public function getIteratorMode () {}

	/**
	 * Counts the number of elements in the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.count.php
	 * @return int the number of elements in the doubly linked list.
	 */
	public function count () {}

	/**
	 * Returns whether the requested $index exists
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetexists.php
	 * @param mixed $index The index being checked.
	 * @return bool true if the requested index exists, otherwise false
	 */
	public function offsetExists ($index) {}

	/**
	 * Returns the value at the specified $index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetget.php
	 * @param mixed $index The index with the value.
	 * @return mixed The value at the specified index.
	 */
	public function offsetGet ($index) {}

	/**
	 * Sets the value at the specified $index to $newval
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetset.php
	 * @param mixed $index The index being set.
	 * @param mixed $newval The new value for the index.
	 * @return void 
	 */
	public function offsetSet ($index, $newval) {}

	/**
	 * Unsets the value at the specified $index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetunset.php
	 * @param mixed $index The index being unset.
	 * @return void 
	 */
	public function offsetUnset ($index) {}

	/**
	 * Add/insert a new value at the specified index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.add.php
	 * @param mixed $index The index where the new value is to be inserted.
	 * @param mixed $newval The new value for the index.
	 * @return void 
	 */
	public function add ($index, $newval) {}

	/**
	 * Rewind iterator back to the start
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Return current array entry
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.current.php
	 * @return mixed The current node value.
	 */
	public function current () {}

	/**
	 * Return current node index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.key.php
	 * @return mixed The current node index.
	 */
	public function key () {}

	/**
	 * Move to next entry
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Move to previous entry
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.prev.php
	 * @return void 
	 */
	public function prev () {}

	/**
	 * Check whether the doubly linked list contains more nodes
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.valid.php
	 * @return bool true if the doubly linked list contains any more nodes, false otherwise.
	 */
	public function valid () {}

	/**
	 * Unserializes the storage
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.unserialize.php
	 * @param string $serialized The serialized string.
	 * @return void 
	 */
	public function unserialize (string $serialized) {}

	/**
	 * Serializes the storage
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.serialize.php
	 * @return string The serialized string.
	 */
	public function serialize () {}

}

/**
 * The SplQueue class provides the main functionalities of a queue implemented using a doubly linked list.
 * @link http://www.php.net/manual/en/class.splqueue.php
 */
class SplQueue extends SplDoublyLinkedList implements Serializable, ArrayAccess, Countable, Traversable, Iterator {
	const IT_MODE_LIFO = 2;
	const IT_MODE_FIFO = 0;
	const IT_MODE_DELETE = 1;
	const IT_MODE_KEEP = 0;


	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.splqueue.php#splqueue.props.name
	 */
	public $name;

	/**
	 * Adds an element to the queue
	 * @link http://www.php.net/manual/en/splqueue.enqueue.php
	 * @param mixed $value The value to enqueue.
	 * @return void 
	 */
	public function enqueue ($value) {}

	/**
	 * Dequeues a node from the queue
	 * @link http://www.php.net/manual/en/splqueue.dequeue.php
	 * @return mixed The value of the dequeued node.
	 */
	public function dequeue () {}

	/**
	 * Pops a node from the end of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.pop.php
	 * @return mixed The value of the popped node.
	 */
	public function pop () {}

	/**
	 * Shifts a node from the beginning of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.shift.php
	 * @return mixed The value of the shifted node.
	 */
	public function shift () {}

	/**
	 * Pushes an element at the end of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.push.php
	 * @param mixed $value The value to push.
	 * @return void 
	 */
	public function push ($value) {}

	/**
	 * Prepends the doubly linked list with an element
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.unshift.php
	 * @param mixed $value The value to unshift.
	 * @return void 
	 */
	public function unshift ($value) {}

	/**
	 * Peeks at the node from the end of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.top.php
	 * @return mixed The value of the last node.
	 */
	public function top () {}

	/**
	 * Peeks at the node from the beginning of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.bottom.php
	 * @return mixed The value of the first node.
	 */
	public function bottom () {}

	/**
	 * Checks whether the doubly linked list is empty
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.isempty.php
	 * @return bool whether the doubly linked list is empty.
	 */
	public function isEmpty () {}

	/**
	 * Sets the mode of iteration
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.setiteratormode.php
	 * @param int $mode <p>
	 * There are two orthogonal sets of modes that can be set:
	 * </p>
	 * <p>
	 * <br>
	 * The direction of the iteration (either one or the other):
	 * <p>
	 * <br>SplDoublyLinkedList::IT_MODE_LIFO (Stack style)
	 * <br>SplDoublyLinkedList::IT_MODE_FIFO (Queue style)
	 * </p>
	 * <br>
	 * The behavior of the iterator (either one or the other):
	 * <p>
	 * <br>SplDoublyLinkedList::IT_MODE_DELETE (Elements are deleted by the iterator)
	 * <br>SplDoublyLinkedList::IT_MODE_KEEP (Elements are traversed by the iterator)
	 * </p>
	 * </p>
	 * <p>
	 * The default mode is: SplDoublyLinkedList::IT_MODE_FIFO | SplDoublyLinkedList::IT_MODE_KEEP
	 * </p>
	 * @return void 
	 */
	public function setIteratorMode (int $mode) {}

	/**
	 * Returns the mode of iteration
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.getiteratormode.php
	 * @return int the different modes and flags that affect the iteration.
	 */
	public function getIteratorMode () {}

	/**
	 * Counts the number of elements in the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.count.php
	 * @return int the number of elements in the doubly linked list.
	 */
	public function count () {}

	/**
	 * Returns whether the requested $index exists
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetexists.php
	 * @param mixed $index The index being checked.
	 * @return bool true if the requested index exists, otherwise false
	 */
	public function offsetExists ($index) {}

	/**
	 * Returns the value at the specified $index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetget.php
	 * @param mixed $index The index with the value.
	 * @return mixed The value at the specified index.
	 */
	public function offsetGet ($index) {}

	/**
	 * Sets the value at the specified $index to $newval
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetset.php
	 * @param mixed $index The index being set.
	 * @param mixed $newval The new value for the index.
	 * @return void 
	 */
	public function offsetSet ($index, $newval) {}

	/**
	 * Unsets the value at the specified $index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetunset.php
	 * @param mixed $index The index being unset.
	 * @return void 
	 */
	public function offsetUnset ($index) {}

	/**
	 * Add/insert a new value at the specified index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.add.php
	 * @param mixed $index The index where the new value is to be inserted.
	 * @param mixed $newval The new value for the index.
	 * @return void 
	 */
	public function add ($index, $newval) {}

	/**
	 * Rewind iterator back to the start
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Return current array entry
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.current.php
	 * @return mixed The current node value.
	 */
	public function current () {}

	/**
	 * Return current node index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.key.php
	 * @return mixed The current node index.
	 */
	public function key () {}

	/**
	 * Move to next entry
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Move to previous entry
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.prev.php
	 * @return void 
	 */
	public function prev () {}

	/**
	 * Check whether the doubly linked list contains more nodes
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.valid.php
	 * @return bool true if the doubly linked list contains any more nodes, false otherwise.
	 */
	public function valid () {}

	/**
	 * Unserializes the storage
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.unserialize.php
	 * @param string $serialized The serialized string.
	 * @return void 
	 */
	public function unserialize (string $serialized) {}

	/**
	 * Serializes the storage
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.serialize.php
	 * @return string The serialized string.
	 */
	public function serialize () {}

}

/**
 * The SplStack class provides the main functionalities of a stack implemented using a doubly linked list.
 * @link http://www.php.net/manual/en/class.splstack.php
 */
class SplStack extends SplDoublyLinkedList implements Serializable, ArrayAccess, Countable, Traversable, Iterator {
	const IT_MODE_LIFO = 2;
	const IT_MODE_FIFO = 0;
	const IT_MODE_DELETE = 1;
	const IT_MODE_KEEP = 0;


	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.splstack.php#splstack.props.name
	 */
	public $name;

	/**
	 * Pops a node from the end of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.pop.php
	 * @return mixed The value of the popped node.
	 */
	public function pop () {}

	/**
	 * Shifts a node from the beginning of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.shift.php
	 * @return mixed The value of the shifted node.
	 */
	public function shift () {}

	/**
	 * Pushes an element at the end of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.push.php
	 * @param mixed $value The value to push.
	 * @return void 
	 */
	public function push ($value) {}

	/**
	 * Prepends the doubly linked list with an element
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.unshift.php
	 * @param mixed $value The value to unshift.
	 * @return void 
	 */
	public function unshift ($value) {}

	/**
	 * Peeks at the node from the end of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.top.php
	 * @return mixed The value of the last node.
	 */
	public function top () {}

	/**
	 * Peeks at the node from the beginning of the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.bottom.php
	 * @return mixed The value of the first node.
	 */
	public function bottom () {}

	/**
	 * Checks whether the doubly linked list is empty
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.isempty.php
	 * @return bool whether the doubly linked list is empty.
	 */
	public function isEmpty () {}

	/**
	 * Sets the mode of iteration
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.setiteratormode.php
	 * @param int $mode <p>
	 * There are two orthogonal sets of modes that can be set:
	 * </p>
	 * <p>
	 * <br>
	 * The direction of the iteration (either one or the other):
	 * <p>
	 * <br>SplDoublyLinkedList::IT_MODE_LIFO (Stack style)
	 * <br>SplDoublyLinkedList::IT_MODE_FIFO (Queue style)
	 * </p>
	 * <br>
	 * The behavior of the iterator (either one or the other):
	 * <p>
	 * <br>SplDoublyLinkedList::IT_MODE_DELETE (Elements are deleted by the iterator)
	 * <br>SplDoublyLinkedList::IT_MODE_KEEP (Elements are traversed by the iterator)
	 * </p>
	 * </p>
	 * <p>
	 * The default mode is: SplDoublyLinkedList::IT_MODE_FIFO | SplDoublyLinkedList::IT_MODE_KEEP
	 * </p>
	 * @return void 
	 */
	public function setIteratorMode (int $mode) {}

	/**
	 * Returns the mode of iteration
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.getiteratormode.php
	 * @return int the different modes and flags that affect the iteration.
	 */
	public function getIteratorMode () {}

	/**
	 * Counts the number of elements in the doubly linked list
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.count.php
	 * @return int the number of elements in the doubly linked list.
	 */
	public function count () {}

	/**
	 * Returns whether the requested $index exists
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetexists.php
	 * @param mixed $index The index being checked.
	 * @return bool true if the requested index exists, otherwise false
	 */
	public function offsetExists ($index) {}

	/**
	 * Returns the value at the specified $index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetget.php
	 * @param mixed $index The index with the value.
	 * @return mixed The value at the specified index.
	 */
	public function offsetGet ($index) {}

	/**
	 * Sets the value at the specified $index to $newval
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetset.php
	 * @param mixed $index The index being set.
	 * @param mixed $newval The new value for the index.
	 * @return void 
	 */
	public function offsetSet ($index, $newval) {}

	/**
	 * Unsets the value at the specified $index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.offsetunset.php
	 * @param mixed $index The index being unset.
	 * @return void 
	 */
	public function offsetUnset ($index) {}

	/**
	 * Add/insert a new value at the specified index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.add.php
	 * @param mixed $index The index where the new value is to be inserted.
	 * @param mixed $newval The new value for the index.
	 * @return void 
	 */
	public function add ($index, $newval) {}

	/**
	 * Rewind iterator back to the start
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Return current array entry
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.current.php
	 * @return mixed The current node value.
	 */
	public function current () {}

	/**
	 * Return current node index
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.key.php
	 * @return mixed The current node index.
	 */
	public function key () {}

	/**
	 * Move to next entry
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Move to previous entry
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.prev.php
	 * @return void 
	 */
	public function prev () {}

	/**
	 * Check whether the doubly linked list contains more nodes
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.valid.php
	 * @return bool true if the doubly linked list contains any more nodes, false otherwise.
	 */
	public function valid () {}

	/**
	 * Unserializes the storage
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.unserialize.php
	 * @param string $serialized The serialized string.
	 * @return void 
	 */
	public function unserialize (string $serialized) {}

	/**
	 * Serializes the storage
	 * @link http://www.php.net/manual/en/spldoublylinkedlist.serialize.php
	 * @return string The serialized string.
	 */
	public function serialize () {}

}

/**
 * The SplHeap class provides the main functionalities of a Heap.
 * @link http://www.php.net/manual/en/class.splheap.php
 */
abstract class SplHeap implements Iterator, Traversable, Countable {

	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.splheap.php#splheap.props.name
	 */
	public $name;

	/**
	 * Extracts a node from top of the heap and sift up
	 * @link http://www.php.net/manual/en/splheap.extract.php
	 * @return mixed The value of the extracted node.
	 */
	public function extract () {}

	/**
	 * Inserts an element in the heap by sifting it up
	 * @link http://www.php.net/manual/en/splheap.insert.php
	 * @param mixed $value The value to insert.
	 * @return void 
	 */
	public function insert ($value) {}

	/**
	 * Peeks at the node from the top of the heap
	 * @link http://www.php.net/manual/en/splheap.top.php
	 * @return mixed The value of the node on the top.
	 */
	public function top () {}

	/**
	 * Counts the number of elements in the heap
	 * @link http://www.php.net/manual/en/splheap.count.php
	 * @return int the number of elements in the heap.
	 */
	public function count () {}

	/**
	 * Checks whether the heap is empty
	 * @link http://www.php.net/manual/en/splheap.isempty.php
	 * @return bool whether the heap is empty.
	 */
	public function isEmpty () {}

	/**
	 * Rewind iterator back to the start (no-op)
	 * @link http://www.php.net/manual/en/splheap.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Return current node pointed by the iterator
	 * @link http://www.php.net/manual/en/splheap.current.php
	 * @return mixed The current node value.
	 */
	public function current () {}

	/**
	 * Return current node index
	 * @link http://www.php.net/manual/en/splheap.key.php
	 * @return mixed The current node index.
	 */
	public function key () {}

	/**
	 * Move to the next node
	 * @link http://www.php.net/manual/en/splheap.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Check whether the heap contains more nodes
	 * @link http://www.php.net/manual/en/splheap.valid.php
	 * @return bool true if the heap contains any more nodes, false otherwise.
	 */
	public function valid () {}

	/**
	 * Recover from the corrupted state and allow further actions on the heap
	 * @link http://www.php.net/manual/en/splheap.recoverfromcorruption.php
	 * @return void 
	 */
	public function recoverFromCorruption () {}

	/**
	 * Tells if the heap is in a corrupted state
	 * @link http://www.php.net/manual/en/splheap.iscorrupted.php
	 * @return bool true if the heap is corrupted, false otherwise.
	 */
	public function isCorrupted () {}

	/**
	 * Compare elements in order to place them correctly in the heap while sifting up
	 * @link http://www.php.net/manual/en/splheap.compare.php
	 * @param mixed $value1 The value of the first node being compared.
	 * @param mixed $value2 The value of the second node being compared.
	 * @return int Result of the comparison, positive integer if value1 is greater than value2, 0 if they are equal, negative integer otherwise.
	 * <p>
	 * Having multiple elements with the same value in a Heap is not recommended. They will end up in an arbitrary relative position.
	 * </p>
	 */
	abstract protected function compare ($value1, $value2);

}

/**
 * The SplMinHeap class provides the main functionalities of a heap, keeping the minimum on the top.
 * @link http://www.php.net/manual/en/class.splminheap.php
 */
class SplMinHeap extends SplHeap implements Countable, Traversable, Iterator {

	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.splminheap.php#splminheap.props.name
	 */
	public $name;

	/**
	 * Compare elements in order to place them correctly in the heap while sifting up
	 * @link http://www.php.net/manual/en/splminheap.compare.php
	 * @param mixed $value1 The value of the first node being compared.
	 * @param mixed $value2 The value of the second node being compared.
	 * @return int Result of the comparison, positive integer if value1 is lower than value2, 0 if they are equal, negative integer otherwise.
	 * <p>
	 * Having multiple elements with the same value in a Heap is not recommended. They will end up in an arbitrary relative position.
	 * </p>
	 */
	protected function compare ($value1, $value2) {}

	/**
	 * Extracts a node from top of the heap and sift up
	 * @link http://www.php.net/manual/en/splheap.extract.php
	 * @return mixed The value of the extracted node.
	 */
	public function extract () {}

	/**
	 * Inserts an element in the heap by sifting it up
	 * @link http://www.php.net/manual/en/splheap.insert.php
	 * @param mixed $value The value to insert.
	 * @return void 
	 */
	public function insert ($value) {}

	/**
	 * Peeks at the node from the top of the heap
	 * @link http://www.php.net/manual/en/splheap.top.php
	 * @return mixed The value of the node on the top.
	 */
	public function top () {}

	/**
	 * Counts the number of elements in the heap
	 * @link http://www.php.net/manual/en/splheap.count.php
	 * @return int the number of elements in the heap.
	 */
	public function count () {}

	/**
	 * Checks whether the heap is empty
	 * @link http://www.php.net/manual/en/splheap.isempty.php
	 * @return bool whether the heap is empty.
	 */
	public function isEmpty () {}

	/**
	 * Rewind iterator back to the start (no-op)
	 * @link http://www.php.net/manual/en/splheap.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Return current node pointed by the iterator
	 * @link http://www.php.net/manual/en/splheap.current.php
	 * @return mixed The current node value.
	 */
	public function current () {}

	/**
	 * Return current node index
	 * @link http://www.php.net/manual/en/splheap.key.php
	 * @return mixed The current node index.
	 */
	public function key () {}

	/**
	 * Move to the next node
	 * @link http://www.php.net/manual/en/splheap.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Check whether the heap contains more nodes
	 * @link http://www.php.net/manual/en/splheap.valid.php
	 * @return bool true if the heap contains any more nodes, false otherwise.
	 */
	public function valid () {}

	/**
	 * Recover from the corrupted state and allow further actions on the heap
	 * @link http://www.php.net/manual/en/splheap.recoverfromcorruption.php
	 * @return void 
	 */
	public function recoverFromCorruption () {}

	/**
	 * Tells if the heap is in a corrupted state
	 * @link http://www.php.net/manual/en/splheap.iscorrupted.php
	 * @return bool true if the heap is corrupted, false otherwise.
	 */
	public function isCorrupted () {}

}

/**
 * The SplMaxHeap class provides the main functionalities of a heap, keeping the maximum on the top.
 * @link http://www.php.net/manual/en/class.splmaxheap.php
 */
class SplMaxHeap extends SplHeap implements Countable, Traversable, Iterator {

	/**
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.splmaxheap.php#splmaxheap.props.name
	 */
	public $name;

	/**
	 * Compare elements in order to place them correctly in the heap while sifting up
	 * @link http://www.php.net/manual/en/splmaxheap.compare.php
	 * @param mixed $value1 The value of the first node being compared.
	 * @param mixed $value2 The value of the second node being compared.
	 * @return int Result of the comparison, positive integer if value1 is greater than value2, 0 if they are equal, negative integer otherwise.
	 * <p>
	 * Having multiple elements with the same value in a Heap is not recommended. They will end up in an arbitrary relative position.
	 * </p>
	 */
	protected function compare ($value1, $value2) {}

	/**
	 * Extracts a node from top of the heap and sift up
	 * @link http://www.php.net/manual/en/splheap.extract.php
	 * @return mixed The value of the extracted node.
	 */
	public function extract () {}

	/**
	 * Inserts an element in the heap by sifting it up
	 * @link http://www.php.net/manual/en/splheap.insert.php
	 * @param mixed $value The value to insert.
	 * @return void 
	 */
	public function insert ($value) {}

	/**
	 * Peeks at the node from the top of the heap
	 * @link http://www.php.net/manual/en/splheap.top.php
	 * @return mixed The value of the node on the top.
	 */
	public function top () {}

	/**
	 * Counts the number of elements in the heap
	 * @link http://www.php.net/manual/en/splheap.count.php
	 * @return int the number of elements in the heap.
	 */
	public function count () {}

	/**
	 * Checks whether the heap is empty
	 * @link http://www.php.net/manual/en/splheap.isempty.php
	 * @return bool whether the heap is empty.
	 */
	public function isEmpty () {}

	/**
	 * Rewind iterator back to the start (no-op)
	 * @link http://www.php.net/manual/en/splheap.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Return current node pointed by the iterator
	 * @link http://www.php.net/manual/en/splheap.current.php
	 * @return mixed The current node value.
	 */
	public function current () {}

	/**
	 * Return current node index
	 * @link http://www.php.net/manual/en/splheap.key.php
	 * @return mixed The current node index.
	 */
	public function key () {}

	/**
	 * Move to the next node
	 * @link http://www.php.net/manual/en/splheap.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Check whether the heap contains more nodes
	 * @link http://www.php.net/manual/en/splheap.valid.php
	 * @return bool true if the heap contains any more nodes, false otherwise.
	 */
	public function valid () {}

	/**
	 * Recover from the corrupted state and allow further actions on the heap
	 * @link http://www.php.net/manual/en/splheap.recoverfromcorruption.php
	 * @return void 
	 */
	public function recoverFromCorruption () {}

	/**
	 * Tells if the heap is in a corrupted state
	 * @link http://www.php.net/manual/en/splheap.iscorrupted.php
	 * @return bool true if the heap is corrupted, false otherwise.
	 */
	public function isCorrupted () {}

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
	 * Prop description
	 * @var string
	 * @link http://www.php.net/manual/en/class.splpriorityqueue.php#splpriorityqueue.props.name
	 */
	public $name;

	/**
	 * Compare priorities in order to place elements correctly in the heap while sifting up
	 * @link http://www.php.net/manual/en/splpriorityqueue.compare.php
	 * @param mixed $priority1 The priority of the first node being compared.
	 * @param mixed $priority2 The priority of the second node being compared.
	 * @return int Result of the comparison, positive integer if priority1 is greater than priority2, 0 if they are equal, negative integer otherwise.
	 * <p>
	 * Multiple elements with the same priority will get dequeued in no particular order.
	 * </p>
	 */
	public function compare ($priority1, $priority2) {}

	/**
	 * Inserts an element in the queue by sifting it up
	 * @link http://www.php.net/manual/en/splpriorityqueue.insert.php
	 * @param mixed $value The value to insert.
	 * @param mixed $priority The associated priority.
	 * @return void 
	 */
	public function insert ($value, $priority) {}

	/**
	 * Sets the mode of extraction
	 * @link http://www.php.net/manual/en/splpriorityqueue.setextractflags.php
	 * @param int $flags <p>
	 * Defines what is extracted by SplPriorityQueue::current,
	 * SplPriorityQueue::top and 
	 * SplPriorityQueue::extract.
	 * </p>
	 * <p>
	 * <br>SplPriorityQueue::EXTR_DATA (0x00000001): Extract the data
	 * <br>SplPriorityQueue::EXTR_PRIORITY (0x00000002): Extract the priority
	 * <br>SplPriorityQueue::EXTR_BOTH (0x00000003): Extract an array containing both
	 * </p>
	 * <p>
	 * The default mode is SplPriorityQueue::EXTR_DATA.
	 * </p>
	 * @return void 
	 */
	public function setExtractFlags (int $flags) {}

	/**
	 * Get the flags of extraction
	 * @link http://www.php.net/manual/en/splpriorityqueue.getextractflags.php
	 * @return int 
	 */
	public function getExtractFlags () {}

	/**
	 * Peeks at the node from the top of the queue
	 * @link http://www.php.net/manual/en/splpriorityqueue.top.php
	 * @return mixed The value or priority (or both) of the top node, depending on the extract flag.
	 */
	public function top () {}

	/**
	 * Extracts a node from top of the heap and sift up
	 * @link http://www.php.net/manual/en/splpriorityqueue.extract.php
	 * @return mixed The value or priority (or both) of the extracted node, depending on the extract flag.
	 */
	public function extract () {}

	/**
	 * Counts the number of elements in the queue
	 * @link http://www.php.net/manual/en/splpriorityqueue.count.php
	 * @return int the number of elements in the queue.
	 */
	public function count () {}

	/**
	 * Checks whether the queue is empty
	 * @link http://www.php.net/manual/en/splpriorityqueue.isempty.php
	 * @return bool whether the queue is empty.
	 */
	public function isEmpty () {}

	/**
	 * Rewind iterator back to the start (no-op)
	 * @link http://www.php.net/manual/en/splpriorityqueue.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Return current node pointed by the iterator
	 * @link http://www.php.net/manual/en/splpriorityqueue.current.php
	 * @return mixed The value or priority (or both) of the current node, depending on the extract flag.
	 */
	public function current () {}

	/**
	 * Return current node index
	 * @link http://www.php.net/manual/en/splpriorityqueue.key.php
	 * @return mixed The current node index.
	 */
	public function key () {}

	/**
	 * Move to the next node
	 * @link http://www.php.net/manual/en/splpriorityqueue.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Check whether the queue contains more nodes
	 * @link http://www.php.net/manual/en/splpriorityqueue.valid.php
	 * @return bool true if the queue contains any more nodes, false otherwise.
	 */
	public function valid () {}

	/**
	 * Recover from the corrupted state and allow further actions on the queue
	 * @link http://www.php.net/manual/en/splpriorityqueue.recoverfromcorruption.php
	 * @return void 
	 */
	public function recoverFromCorruption () {}

	/**
	 * Tells if the priority queue is in a corrupted state
	 * @link http://www.php.net/manual/en/splpriorityqueue.iscorrupted.php
	 * @return bool true if the priority queue is corrupted, false otherwise.
	 */
	public function isCorrupted () {}

}

/**
 * The SplFixedArray class provides the main functionalities of array. The 
 * main differences between a SplFixedArray and a normal PHP array is that 
 * the SplFixedArray is of fixed length and allows only integers within 
 * the range as indexes. The advantage is that it allows a faster array
 * implementation.
 * @link http://www.php.net/manual/en/class.splfixedarray.php
 */
class SplFixedArray implements Iterator, Traversable, ArrayAccess, Countable {

	/**
	 * Constructs a new fixed array
	 * @link http://www.php.net/manual/en/splfixedarray.construct.php
	 * @param mixed $size [optional]
	 */
	public function __construct ($size = null) {}

	/**
	 * Reinitialises the array after being unserialised
	 * @link http://www.php.net/manual/en/splfixedarray.wakeup.php
	 * @return void 
	 */
	public function __wakeup () {}

	/**
	 * Returns the size of the array
	 * @link http://www.php.net/manual/en/splfixedarray.count.php
	 * @return int the size of the array.
	 */
	public function count () {}

	/**
	 * Returns a PHP array from the fixed array
	 * @link http://www.php.net/manual/en/splfixedarray.toarray.php
	 * @return array a PHP array, similar to the fixed array.
	 */
	public function toArray () {}

	/**
	 * Import a PHP array in a SplFixedArray instance
	 * @link http://www.php.net/manual/en/splfixedarray.fromarray.php
	 * @param array $array The array to import.
	 * @param bool $save_indexes [optional] Try to save the numeric indexes used in the original array.
	 * @return SplFixedArray an instance of SplFixedArray 
	 * containing the array content.
	 */
	public static function fromArray (array $array, bool $save_indexes = null) {}

	/**
	 * Gets the size of the array
	 * @link http://www.php.net/manual/en/splfixedarray.getsize.php
	 * @return int the size of the array, as an integer.
	 */
	public function getSize () {}

	/**
	 * Change the size of an array
	 * @link http://www.php.net/manual/en/splfixedarray.setsize.php
	 * @param int $size The new array size. This should be a value between 0 and PHP_INT_MAX.
	 * @return bool true on success or false on failure
	 */
	public function setSize (int $size) {}

	/**
	 * Returns whether the requested index exists
	 * @link http://www.php.net/manual/en/splfixedarray.offsetexists.php
	 * @param int $index The index being checked.
	 * @return bool true if the requested index exists, otherwise false
	 */
	public function offsetExists (int $index) {}

	/**
	 * Returns the value at the specified index
	 * @link http://www.php.net/manual/en/splfixedarray.offsetget.php
	 * @param int $index The index with the value.
	 * @return mixed The value at the specified index.
	 */
	public function offsetGet (int $index) {}

	/**
	 * Sets a new value at a specified index
	 * @link http://www.php.net/manual/en/splfixedarray.offsetset.php
	 * @param int $index The index being set.
	 * @param mixed $newval The new value for the index.
	 * @return void 
	 */
	public function offsetSet (int $index, $newval) {}

	/**
	 * Unsets the value at the specified $index
	 * @link http://www.php.net/manual/en/splfixedarray.offsetunset.php
	 * @param int $index The index being unset.
	 * @return void 
	 */
	public function offsetUnset (int $index) {}

	/**
	 * Rewind iterator back to the start
	 * @link http://www.php.net/manual/en/splfixedarray.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Return current array entry
	 * @link http://www.php.net/manual/en/splfixedarray.current.php
	 * @return mixed The current element value.
	 */
	public function current () {}

	/**
	 * Return current array index
	 * @link http://www.php.net/manual/en/splfixedarray.key.php
	 * @return int The current array index.
	 */
	public function key () {}

	/**
	 * Move to next entry
	 * @link http://www.php.net/manual/en/splfixedarray.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Check whether the array contains more elements
	 * @link http://www.php.net/manual/en/splfixedarray.valid.php
	 * @return bool true if the array contains any more elements, false otherwise.
	 */
	public function valid () {}

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
	 * @param SplSubject $subject The SplSubject notifying the observer of an update.
	 * @return void 
	 */
	abstract public function update ($subject);

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
	 * @param SplObserver $observer The SplObserver to attach.
	 * @return void 
	 */
	abstract public function attach ($observer);

	/**
	 * Detach an observer
	 * @link http://www.php.net/manual/en/splsubject.detach.php
	 * @param SplObserver $observer The SplObserver to detach.
	 * @return void 
	 */
	abstract public function detach ($observer);

	/**
	 * Notify an observer
	 * @link http://www.php.net/manual/en/splsubject.notify.php
	 * @return void 
	 */
	abstract public function notify ();

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
	 * @param object $object The object to add.
	 * @param mixed $data [optional] The data to associate with the object.
	 * @return void 
	 */
	public function attach ($object, $data = null) {}

	/**
	 * Removes an object from the storage
	 * @link http://www.php.net/manual/en/splobjectstorage.detach.php
	 * @param object $object The object to remove.
	 * @return void 
	 */
	public function detach ($object) {}

	/**
	 * Checks if the storage contains a specific object
	 * @link http://www.php.net/manual/en/splobjectstorage.contains.php
	 * @param object $object The object to look for.
	 * @return bool true if the object is in the storage, false otherwise.
	 */
	public function contains ($object) {}

	/**
	 * Adds all objects from another storage
	 * @link http://www.php.net/manual/en/splobjectstorage.addall.php
	 * @param SplObjectStorage $storage The storage you want to import.
	 * @return void 
	 */
	public function addAll (SplObjectStorage $storage) {}

	/**
	 * Removes objects contained in another storage from the current storage
	 * @link http://www.php.net/manual/en/splobjectstorage.removeall.php
	 * @param SplObjectStorage $storage The storage containing the elements to remove.
	 * @return void 
	 */
	public function removeAll (SplObjectStorage $storage) {}

	/**
	 * Removes all objects except for those contained in another storage from the current storage
	 * @link http://www.php.net/manual/en/splobjectstorage.removeallexcept.php
	 * @param SplObjectStorage $storage The storage containing the elements to retain in the current storage.
	 * @return void 
	 */
	public function removeAllExcept (SplObjectStorage $storage) {}

	/**
	 * Returns the data associated with the current iterator entry
	 * @link http://www.php.net/manual/en/splobjectstorage.getinfo.php
	 * @return mixed The data associated with the current iterator position.
	 */
	public function getInfo () {}

	/**
	 * Sets the data associated with the current iterator entry
	 * @link http://www.php.net/manual/en/splobjectstorage.setinfo.php
	 * @param mixed $data The data to associate with the current iterator entry.
	 * @return void 
	 */
	public function setInfo ($data) {}

	/**
	 * Calculate a unique identifier for the contained objects
	 * @link http://www.php.net/manual/en/splobjectstorage.gethash.php
	 * @param object $object The object whose identifier is to be calculated.
	 * @return string A string with the calculated identifier.
	 */
	public function getHash ($object) {}

	/**
	 * Returns the number of objects in the storage
	 * @link http://www.php.net/manual/en/splobjectstorage.count.php
	 * @return int The number of objects in the storage.
	 */
	public function count () {}

	/**
	 * Rewind the iterator to the first storage element
	 * @link http://www.php.net/manual/en/splobjectstorage.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Returns if the current iterator entry is valid
	 * @link http://www.php.net/manual/en/splobjectstorage.valid.php
	 * @return bool true if the iterator entry is valid, false otherwise.
	 */
	public function valid () {}

	/**
	 * Returns the index at which the iterator currently is
	 * @link http://www.php.net/manual/en/splobjectstorage.key.php
	 * @return int The index corresponding to the position of the iterator.
	 */
	public function key () {}

	/**
	 * Returns the current storage entry
	 * @link http://www.php.net/manual/en/splobjectstorage.current.php
	 * @return object The object at the current iterator position.
	 */
	public function current () {}

	/**
	 * Move to the next entry
	 * @link http://www.php.net/manual/en/splobjectstorage.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Unserializes a storage from its string representation
	 * @link http://www.php.net/manual/en/splobjectstorage.unserialize.php
	 * @param string $serialized The serialized representation of a storage.
	 * @return void 
	 */
	public function unserialize (string $serialized) {}

	/**
	 * Serializes the storage
	 * @link http://www.php.net/manual/en/splobjectstorage.serialize.php
	 * @return string A string representing the storage.
	 */
	public function serialize () {}

	/**
	 * Checks whether an object exists in the storage
	 * @link http://www.php.net/manual/en/splobjectstorage.offsetexists.php
	 * @param object $object The object to look for.
	 * @return bool true if the object exists in the storage,
	 * and false otherwise.
	 */
	public function offsetExists ($object) {}

	/**
	 * Associates data to an object in the storage
	 * @link http://www.php.net/manual/en/splobjectstorage.offsetset.php
	 * @param object $object The object to associate data with.
	 * @param mixed $data [optional] The data to associate with the object.
	 * @return void 
	 */
	public function offsetSet ($object, $data = null) {}

	/**
	 * Removes an object from the storage
	 * @link http://www.php.net/manual/en/splobjectstorage.offsetunset.php
	 * @param object $object The object to remove.
	 * @return void 
	 */
	public function offsetUnset ($object) {}

	/**
	 * Returns the data associated with an object
	 * @link http://www.php.net/manual/en/splobjectstorage.offsetget.php
	 * @param object $object The object to look for.
	 * @return mixed The data previously associated with the object in the storage.
	 */
	public function offsetGet ($object) {}

}

/**
 * An Iterator that sequentially iterates over all attached iterators
 * @link http://www.php.net/manual/en/class.multipleiterator.php
 */
class MultipleIterator implements Iterator, Traversable {
	const MIT_NEED_ANY = 0;
	const MIT_NEED_ALL = 1;
	const MIT_KEYS_NUMERIC = 0;
	const MIT_KEYS_ASSOC = 2;


	/**
	 * Constructs a new MultipleIterator
	 * @link http://www.php.net/manual/en/multipleiterator.construct.php
	 * @param mixed $flags
	 */
	public function __construct ($flags) {}

	/**
	 * Gets the flag information
	 * @link http://www.php.net/manual/en/multipleiterator.getflags.php
	 * @return int Information about the flags, as an integer.
	 */
	public function getFlags () {}

	/**
	 * Sets flags
	 * @link http://www.php.net/manual/en/multipleiterator.setflags.php
	 * @param int $flags The flags to set, according to the
	 * Flag Constants
	 * @return void 
	 */
	public function setFlags (int $flags) {}

	/**
	 * Attaches iterator information
	 * @link http://www.php.net/manual/en/multipleiterator.attachiterator.php
	 * @param Iterator $iterator The new iterator to attach.
	 * @param string $infos [optional] The associative information for the Iterator, which must be an
	 * integer, a string, or null.
	 * @return void Description...
	 */
	public function attachIterator ($iterator, string $infos = null) {}

	/**
	 * Detaches an iterator
	 * @link http://www.php.net/manual/en/multipleiterator.detachiterator.php
	 * @param Iterator $iterator The iterator to detach.
	 * @return void 
	 */
	public function detachIterator ($iterator) {}

	/**
	 * Checks if an iterator is attached
	 * @link http://www.php.net/manual/en/multipleiterator.containsiterator.php
	 * @param Iterator $iterator The iterator to check.
	 * @return bool true on success or false on failure
	 */
	public function containsIterator ($iterator) {}

	/**
	 * Gets the number of attached iterator instances
	 * @link http://www.php.net/manual/en/multipleiterator.countiterators.php
	 * @return int The number of attached iterator instances (as an integer).
	 */
	public function countIterators () {}

	/**
	 * Rewinds all attached iterator instances
	 * @link http://www.php.net/manual/en/multipleiterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Checks the validity of sub iterators
	 * @link http://www.php.net/manual/en/multipleiterator.valid.php
	 * @return bool true if one or all sub iterators are valid depending on flags,
	 * otherwise false
	 */
	public function valid () {}

	/**
	 * Gets the registered iterator instances
	 * @link http://www.php.net/manual/en/multipleiterator.key.php
	 * @return array An array of all registered iterator instances,
	 * or false if no sub iterator is attached.
	 */
	public function key () {}

	/**
	 * Gets the registered iterator instances
	 * @link http://www.php.net/manual/en/multipleiterator.current.php
	 * @return array An array containing the current values of each attached iterator,
	 * or false if no iterators are attached.
	 */
	public function current () {}

	/**
	 * Moves all attached iterator instances forward
	 * @link http://www.php.net/manual/en/multipleiterator.next.php
	 * @return void 
	 */
	public function next () {}

}

/**
 * Return available SPL classes
 * @link http://www.php.net/manual/en/function.spl-classes.php
 * @return array an array containing the currently available SPL classes.
 */
function spl_classes () {}

/**
 * Default implementation for __autoload()
 * @link http://www.php.net/manual/en/function.spl-autoload.php
 * @param string $class_name The lowercased name of the class (and namespace) being instantiated.
 * @param string $file_extensions [optional] By default it checks all include paths to
 * contain filenames built up by the lowercase class name appended by the
 * filename extensions .inc and .php.
 * @return void 
 */
function spl_autoload (string $class_name, string $file_extensions = null) {}

/**
 * Register and return default file extensions for spl_autoload
 * @link http://www.php.net/manual/en/function.spl-autoload-extensions.php
 * @param string $file_extensions [optional] When calling without an argument, it simply returns the current list
 * of extensions each separated by comma. To modify the list of file
 * extensions, simply invoke the functions with the new list of file
 * extensions to use in a single string with each extensions separated
 * by comma.
 * @return string A comma delimited list of default file extensions for
 * spl_autoload.
 */
function spl_autoload_extensions (string $file_extensions = null) {}

/**
 * Register given function as __autoload() implementation
 * @link http://www.php.net/manual/en/function.spl-autoload-register.php
 * @param callable $autoload_function [optional] The autoload function being registered.
 * If no parameter is provided, then the default implementation of
 * spl_autoload will be registered.
 * @param bool $throw [optional] This parameter specifies whether
 * spl_autoload_register should throw 
 * exceptions when the autoload_function
 * cannot be registered.
 * @param bool $prepend [optional] If true, spl_autoload_register will prepend
 * the autoloader on the autoload queue instead of appending it.
 * @return bool true on success or false on failure
 */
function spl_autoload_register (callable $autoload_function = null, bool $throw = null, bool $prepend = null) {}

/**
 * Unregister given function as __autoload() implementation
 * @link http://www.php.net/manual/en/function.spl-autoload-unregister.php
 * @param mixed $autoload_function The autoload function being unregistered.
 * @return bool true on success or false on failure
 */
function spl_autoload_unregister ($autoload_function) {}

/**
 * Return all registered __autoload() functions
 * @link http://www.php.net/manual/en/function.spl-autoload-functions.php
 * @return array An array of all registered __autoload functions.
 * If the autoload queue is not activated then the return value is false.
 * If no function is registered the return value will be an empty array.
 */
function spl_autoload_functions () {}

/**
 * Try all registered __autoload() functions to load the requested class
 * @link http://www.php.net/manual/en/function.spl-autoload-call.php
 * @param string $class_name The class name being searched.
 * @return void 
 */
function spl_autoload_call (string $class_name) {}

/**
 * Return the parent classes of the given class
 * @link http://www.php.net/manual/en/function.class-parents.php
 * @param mixed $class An object (class instance) or a string (class name).
 * @param bool $autoload [optional] Whether to allow this function to load the class automatically through
 * the __autoload magic method.
 * @return array An array on success, or false on error.
 */
function class_parents ($class, bool $autoload = null) {}

/**
 * Return the interfaces which are implemented by the given class or interface
 * @link http://www.php.net/manual/en/function.class-implements.php
 * @param mixed $class An object (class instance) or a string (class or interface name).
 * @param bool $autoload [optional] Whether to allow this function to load the class automatically through
 * the __autoload magic method.
 * @return array An array on success, or false on error.
 */
function class_implements ($class, bool $autoload = null) {}

/**
 * Return the traits used by the given class
 * @link http://www.php.net/manual/en/function.class-uses.php
 * @param mixed $class An object (class instance) or a string (class name).
 * @param bool $autoload [optional] Whether to allow this function to load the class automatically through
 * the __autoload magic method.
 * @return array An array on success, or false on error.
 */
function class_uses ($class, bool $autoload = null) {}

/**
 * Return hash id for given object
 * @link http://www.php.net/manual/en/function.spl-object-hash.php
 * @param object $obj 
 * @return string A string that is unique for each currently existing object and is always
 * the same for each object.
 */
function spl_object_hash ($obj) {}

/**
 * Return the integer object handle for given object
 * @link http://www.php.net/manual/en/function.spl-object-id.php
 * @param object $obj 
 * @return int An integer identifier that is unique for each currently existing object and
 * is always the same for each object.
 */
function spl_object_id ($obj) {}

/**
 * Copy the iterator into an array
 * @link http://www.php.net/manual/en/function.iterator-to-array.php
 * @param Traversable $iterator The iterator being copied.
 * @param bool $use_keys [optional] <p>
 * Whether to use the iterator element keys as index.
 * </p>
 * <p>
 * In PHP 5.5 and later, if a key is an array or
 * object, a warning will be generated. null keys will be
 * converted to an empty string, float keys will be
 * truncated to their integer counterpart,
 * resource keys will generate a warning and be converted to
 * their resource ID, and boolean keys will be converted to
 * integers.
 * </p>
 * <p>
 * If this parameter is not set or set to true, duplicate keys will be
 * overwritten. The last value with a given key will be in the returned
 * array. Set this paramater to false to get all the values
 * in any case.
 * </p>
 * @return array An array containing the elements of the iterator.
 */
function iterator_to_array ($iterator, bool $use_keys = null) {}

/**
 * Count the elements in an iterator
 * @link http://www.php.net/manual/en/function.iterator-count.php
 * @param Traversable $iterator The iterator being counted.
 * @return int The number of elements in iterator.
 */
function iterator_count ($iterator) {}

/**
 * Call a function for every element in an iterator
 * @link http://www.php.net/manual/en/function.iterator-apply.php
 * @param Traversable $iterator The iterator object to iterate over.
 * @param callable $function The callback function to call on every element.
 * This function only receives the given args, so it
 * is nullary by default. If count($args) === 3, for
 * instance, the callback function is ternary.
 * The function must return true in order to
 * continue iterating over the iterator.
 * @param array $args [optional] An array of arguments; each element of
 * args is passed to the callback
 * function as separate argument.
 * @return int the iteration count.
 */
function iterator_apply ($iterator, callable $function, array $args = null) {}

// End of SPL v.7.3.0
