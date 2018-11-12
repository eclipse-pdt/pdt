<?php

// Start of Core v.7.3.0

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

	abstract public function getIterator ();

}

/**
 * Interface for external iterators or objects that can be iterated
 * themselves internally.
 * @link http://www.php.net/manual/en/class.iterator.php
 */
interface Iterator extends Traversable {

	abstract public function current ();

	abstract public function next ();

	abstract public function key ();

	abstract public function valid ();

	abstract public function rewind ();

}

/**
 * Interface to provide accessing objects as arrays.
 * @link http://www.php.net/manual/en/class.arrayaccess.php
 */
interface ArrayAccess  {

	/**
	 * @param mixed $offset
	 */
	abstract public function offsetExists ($offset);

	/**
	 * @param mixed $offset
	 */
	abstract public function offsetGet ($offset);

	/**
	 * @param mixed $offset
	 * @param mixed $value
	 */
	abstract public function offsetSet ($offset, $value);

	/**
	 * @param mixed $offset
	 */
	abstract public function offsetUnset ($offset);

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
 * @link http://www.php.net/manual/en/class.serializable.php
 */
interface Serializable  {

	abstract public function serialize ();

	/**
	 * @param mixed $serialized
	 */
	abstract public function unserialize ($serialized);

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
	 * @return int The custom count as an integer.
	 * <p>
	 * The return value is cast to an integer.
	 * </p>
	 */
	abstract public function count ();

}

/**
 * Throwable is the base interface for any object that
 * can be thrown via a throw statement in PHP 7, including
 * Error and Exception.
 * <p>PHP classes cannot implement the Throwable
 * interface directly, and must instead extend
 * Exception.</p>
 * @link http://www.php.net/manual/en/class.throwable.php
 */
interface Throwable  {

	abstract public function getMessage ();

	abstract public function getCode ();

	abstract public function getFile ();

	abstract public function getLine ();

	abstract public function getTrace ();

	abstract public function getPrevious ();

	abstract public function getTraceAsString ();

	abstract public function __toString ();

}

/**
 * Exception is the base class for
 * all Exceptions in PHP 5, and the base class for all user exceptions in PHP
 * 7.
 * <p>Before PHP 7, Exception did not implement the
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
	 * @param mixed $message [optional]
	 * @param mixed $code [optional]
	 * @param mixed $severity [optional]
	 * @param mixed $filename [optional]
	 * @param mixed $lineno [optional]
	 * @param mixed $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $severity = null, $filename = null, $lineno = null, $previous = null) {}

	final public function getSeverity () {}

	final private function __clone () {}

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
 * CompileError is thrown for some
 * compilation errors, which formerly issued a fatal error.
 * @link http://www.php.net/manual/en/class.compileerror.php
 */
class CompileError extends Error implements Throwable {
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
 * ParseError is thrown when an
 * error occurs while parsing PHP code, such as when
 * eval is called.
 * @link http://www.php.net/manual/en/class.parseerror.php
 */
class ParseError extends CompileError implements Throwable {
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
 * ArgumentCountError is thrown
 * when too few arguments are passed to a user-defined function or method.
 * @link http://www.php.net/manual/en/class.argumentcounterror.php
 */
class ArgumentCountError extends TypeError implements Throwable {
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
 * DivisionByZeroError is thrown
 * when an attempt is made to divide a number by zero.
 * @link http://www.php.net/manual/en/class.divisionbyzeroerror.php
 */
class DivisionByZeroError extends ArithmeticError implements Throwable {
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

	private function __construct () {}

	/**
	 * @param mixed $closure
	 * @param mixed $newthis
	 * @param mixed $newscope [optional]
	 */
	public static function bind ($closure, $newthis, $newscope = null) {}

	/**
	 * @param mixed $newthis
	 * @param mixed $newscope [optional]
	 */
	public function bindTo ($newthis, $newscope = null) {}

	/**
	 * @param mixed $newthis
	 * @param mixed $parameters [optional]
	 */
	public function call ($newthis, ...$parameters) {}

	/**
	 * @param mixed $callable
	 */
	public static function fromCallable ($callable) {}

}

/**
 * Generator objects are returned from generators.
 * <p>Generator objects cannot be instantiated via
 * new.</p>
 * @link http://www.php.net/manual/en/class.generator.php
 */
final class Generator implements Iterator, Traversable {

	public function rewind () {}

	public function valid () {}

	public function current () {}

	public function key () {}

	public function next () {}

	/**
	 * @param mixed $value
	 */
	public function send ($value) {}

	/**
	 * @param mixed $exception
	 */
	public function throw ($exception) {}

	public function getReturn () {}

	public function __wakeup () {}

}

class ClosedGeneratorException extends Exception implements Throwable {
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
 * Gets the version of the current Zend engine
 * @link http://www.php.net/manual/en/function.zend-version.php
 * @return string the Zend Engine version number, as a string.
 */
function zend_version () {}

/**
 * Returns the number of arguments passed to the function
 * @link http://www.php.net/manual/en/function.func-num-args.php
 * @return int the number of arguments passed into the current user-defined
 * function.
 */
function func_num_args () {}

/**
 * Return an item from the argument list
 * @link http://www.php.net/manual/en/function.func-get-arg.php
 * @param int $arg_num The argument offset. Function arguments are counted starting from
 * zero.
 * @return mixed the specified argument, or false on error.
 */
function func_get_arg (int $arg_num) {}

/**
 * Returns an array comprising a function's argument list
 * @link http://www.php.net/manual/en/function.func-get-args.php
 * @return array an array in which each element is a copy of the corresponding
 * member of the current user-defined function's argument list.
 */
function func_get_args () {}

/**
 * Get string length
 * @link http://www.php.net/manual/en/function.strlen.php
 * @param string $string The string being measured for length.
 * @return int The length of the string on success, 
 * and 0 if the string is empty.
 */
function strlen (string $string) {}

/**
 * Binary safe string comparison
 * @link http://www.php.net/manual/en/function.strcmp.php
 * @param string $str1 The first string.
 * @param string $str2 The second string.
 * @return int &lt; 0 if str1 is less than
 * str2; &gt; 0 if str1
 * is greater than str2, and 0 if they are
 * equal.
 */
function strcmp (string $str1, string $str2) {}

/**
 * Binary safe string comparison of the first n characters
 * @link http://www.php.net/manual/en/function.strncmp.php
 * @param string $str1 The first string.
 * @param string $str2 The second string.
 * @param int $len Number of characters to use in the comparison.
 * @return int &lt; 0 if str1 is less than
 * str2; &gt; 0 if str1
 * is greater than str2, and 0 if they are
 * equal.
 */
function strncmp (string $str1, string $str2, int $len) {}

/**
 * Binary safe case-insensitive string comparison
 * @link http://www.php.net/manual/en/function.strcasecmp.php
 * @param string $str1 The first string
 * @param string $str2 The second string
 * @return int &lt; 0 if str1 is less than
 * str2; &gt; 0 if str1
 * is greater than str2, and 0 if they are
 * equal.
 */
function strcasecmp (string $str1, string $str2) {}

/**
 * Binary safe case-insensitive string comparison of the first n characters
 * @link http://www.php.net/manual/en/function.strncasecmp.php
 * @param string $str1 The first string.
 * @param string $str2 The second string.
 * @param int $len The length of strings to be used in the comparison.
 * @return int &lt; 0 if str1 is less than
 * str2; &gt; 0 if str1 is
 * greater than str2, and 0 if they are equal.
 */
function strncasecmp (string $str1, string $str2, int $len) {}

/**
 * Return the current key and value pair from an array and advance the array cursor
 * @link http://www.php.net/manual/en/function.each.php
 * @param array $array The input array.
 * @return array the current key and value pair from the array
 * array. This pair is returned in a four-element
 * array, with the keys 0, 1,
 * key, and value. Elements
 * 0 and key contain the key name of
 * the array element, and 1 and value
 * contain the data.
 * <p>
 * If the internal pointer for the array points past the end of the
 * array contents, each returns
 * false.
 * </p>
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
 * @return int the old error_reporting
 * level or the current level if no level parameter is
 * given.
 */
function error_reporting (int $level = null) {}

/**
 * Defines a named constant
 * @link http://www.php.net/manual/en/function.define.php
 * @param string $name <p>
 * The name of the constant.
 * </p>
 * <p>
 * It is possible to define constants with reserved or
 * even invalid names, whose value can (only) be retrieved with
 * constant. However, doing so is not recommended.
 * </p>
 * @param mixed $value <p>
 * The value of the constant. In PHP 5, value must
 * be a scalar value (integer,
 * float, string, boolean, or
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
 * <p>
 * Case-insensitive constants are stored as lower-case.
 * </p>
 * @return bool true on success or false on failure
 */
function define (string $name, $value, bool $case_insensitive = null) {}

/**
 * Checks whether a given named constant exists
 * @link http://www.php.net/manual/en/function.defined.php
 * @param string $name The constant name.
 * @return bool true if the named constant given by name
 * has been defined, false otherwise.
 */
function defined (string $name) {}

/**
 * Returns the name of the class of an object
 * @link http://www.php.net/manual/en/function.get-class.php
 * @param object $object [optional] <p>
 * The tested object. This parameter may be omitted when inside a class.
 * </p>
 * Explicitly passing null as the object is no
 * longer allowed as of PHP 7.2.0.
 * The parameter is still optional and calling get_class
 * without a parameter from inside a class will work, but passing null now
 * emits an E_WARNING notice.
 * @return string the name of the class of which object is an
 * instance. Returns false if object is not an 
 * object.
 * <p>
 * If object is omitted when inside a class, the
 * name of that class is returned.
 * </p>
 * <p>
 * If the object is an instance of a class which exists 
 * in a namespace, the qualified namespaced name of that class is returned.
 * </p>
 */
function get_class ($object = null) {}

/**
 * The "Late Static Binding" class name
 * @link http://www.php.net/manual/en/function.get-called-class.php
 * @return string the class name. Returns false if called from outside a class.
 */
function get_called_class () {}

/**
 * Retrieves the parent class name for object or class
 * @link http://www.php.net/manual/en/function.get-parent-class.php
 * @param mixed $object [optional] The tested object or class name. This parameter is optional if called
 * from the object's method.
 * @return string the name of the parent class of the class of which
 * object is an instance or the name.
 * <p>
 * If the object does not have a parent or the class given does not exist false will be returned.
 * </p>
 * <p>
 * If called without parameter outside object, this function returns false.
 * </p>
 */
function get_parent_class ($object = null) {}

/**
 * Checks if the class method exists
 * @link http://www.php.net/manual/en/function.method-exists.php
 * @param mixed $object An object instance or a class name
 * @param string $method_name The method name
 * @return bool true if the method given by method_name
 * has been defined for the given object, false 
 * otherwise.
 */
function method_exists ($object, string $method_name) {}

/**
 * Checks if the object or class has a property
 * @link http://www.php.net/manual/en/function.property-exists.php
 * @param mixed $class The class name or an object of the class to test for
 * @param string $property The name of the property
 * @return bool true if the property exists, false if it doesn't exist or
 * null in case of an error.
 */
function property_exists ($class, string $property) {}

/**
 * Checks if the class has been defined
 * @link http://www.php.net/manual/en/function.class-exists.php
 * @param string $class_name The class name. The name is matched in a case-insensitive manner.
 * @param bool $autoload [optional] Whether or not to call link.autoload by default.
 * @return bool true if class_name is a defined class,
 * false otherwise.
 */
function class_exists (string $class_name, bool $autoload = null) {}

/**
 * Checks if the interface has been defined
 * @link http://www.php.net/manual/en/function.interface-exists.php
 * @param string $interface_name The interface name
 * @param bool $autoload [optional] Whether to call link.autoload or not by default.
 * @return bool true if the interface given by 
 * interface_name has been defined, false otherwise.
 */
function interface_exists (string $interface_name, bool $autoload = null) {}

/**
 * Checks if the trait exists
 * @link http://www.php.net/manual/en/function.trait-exists.php
 * @param string $traitname Name of the trait to check
 * @param bool $autoload [optional] Whether to autoload if not already loaded.
 * @return bool true if trait exists, false if not, null in case of an error.
 */
function trait_exists (string $traitname, bool $autoload = null) {}

/**
 * Return true if the given function has been defined
 * @link http://www.php.net/manual/en/function.function-exists.php
 * @param string $function_name The function name, as a string.
 * @return bool true if function_name exists and is a
 * function, false otherwise.
 * <p>
 * This function will return false for constructs, such as 
 * include_once and echo.
 * </p>
 */
function function_exists (string $function_name) {}

/**
 * Creates an alias for a class
 * @link http://www.php.net/manual/en/function.class-alias.php
 * @param string $original The original class.
 * @param string $alias The alias name for the class.
 * @param bool $autoload [optional] Whether to autoload if the original class is not found.
 * @return bool true on success or false on failure
 */
function class_alias (string $original, string $alias, bool $autoload = null) {}

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
function get_included_files () {}

/**
 * Alias: get_included_files
 * @link http://www.php.net/manual/en/function.get-required-files.php
 */
function get_required_files () {}

/**
 * Checks if the object has this class as one of its parents or implements it
 * @link http://www.php.net/manual/en/function.is-subclass-of.php
 * @param mixed $object A class name or an object instance. No error is generated if the class does not exist.
 * @param string $class_name The class name
 * @param bool $allow_string [optional] If this parameter set to false, string class name as object
 * is not allowed. This also prevents from calling autoloader if the class doesn't exist.
 * @return bool This function returns true if the object object,
 * belongs to a class which is a subclass of
 * class_name, false otherwise.
 */
function is_subclass_of ($object, string $class_name, bool $allow_string = null) {}

/**
 * Checks if the object is of this class or has this class as one of its parents
 * @link http://www.php.net/manual/en/function.is-a.php
 * @param object $object The tested object
 * @param string $class_name The class name
 * @param bool $allow_string [optional] If this parameter set to false, string class name as object
 * is not allowed. This also prevents from calling autoloader if the class doesn't exist.
 * @return bool true if the object is of this class or has this class as one of
 * its parents, false otherwise.
 */
function is_a ($object, string $class_name, bool $allow_string = null) {}

/**
 * Get the default properties of the class
 * @link http://www.php.net/manual/en/function.get-class-vars.php
 * @param string $class_name The class name
 * @return array an associative array of declared properties visible from the
 * current scope, with their default value.
 * The resulting array elements are in the form of 
 * varname => value.
 * In case of an error, it returns false.
 */
function get_class_vars (string $class_name) {}

/**
 * Gets the properties of the given object
 * @link http://www.php.net/manual/en/function.get-object-vars.php
 * @param object $object An object instance.
 * @return array an associative array of defined object accessible non-static properties 
 * for the specified object in scope. If a property has
 * not been assigned a value, it will be returned with a null value.
 */
function get_object_vars ($object) {}

/**
 * Gets the class methods' names
 * @link http://www.php.net/manual/en/function.get-class-methods.php
 * @param mixed $class_name The class name or an object instance
 * @return array an array of method names defined for the class specified by
 * class_name. In case of an error, it returns null.
 */
function get_class_methods ($class_name) {}

/**
 * Generates a user-level error/warning/notice message
 * @link http://www.php.net/manual/en/function.trigger-error.php
 * @param string $error_msg The designated error message for this error. It's limited to 1024 
 * bytes in length. Any additional characters beyond 1024 bytes will be 
 * truncated.
 * @param int $error_type [optional] The designated error type for this error. It only works with the E_USER
 * family of constants, and will default to E_USER_NOTICE.
 * @return bool This function returns false if wrong error_type is
 * specified, true otherwise.
 */
function trigger_error (string $error_msg, int $error_type = null) {}

/**
 * Alias of trigger_error
 * @link http://www.php.net/manual/en/function.user-error.php
 * @param mixed $message
 * @param mixed $error_type [optional]
 */
function user_error ($message, $error_type = null) {}

/**
 * Sets a user-defined error handler function
 * @link http://www.php.net/manual/en/function.set-error-handler.php
 * @param callable $error_handler <p>
 * A callback with the following signature.
 * null may be passed instead, to reset this handler to its default state.
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
 * <p>
 * errno
 * <br>
 * The first parameter, errno, contains the
 * level of the error raised, as an integer.
 * errstr
 * <br>
 * The second parameter, errstr, contains the
 * error message, as a string.
 * errfile
 * <br>
 * The third parameter is optional, errfile,
 * which contains the filename that the error was raised in, as a string.
 * errline
 * <br>
 * The fourth parameter is optional, errline,
 * which contains the line number the error was raised at, as an integer.
 * errcontext
 * <br>
 * The fifth parameter is optional, errcontext,
 * which is an array that points to the active symbol table at the point
 * the error occurred. In other words, errcontext
 * will contain an array of every variable that existed in the scope the
 * error was triggered in.
 * User error handler must not modify error context.
 * This parameter has been DEPRECATED as of PHP 7.2.0.
 * Relying on it is highly discouraged.
 * </p>
 * </p>
 * <p>
 * If the function returns false then the normal error handler continues.
 * </p>
 * @param int $error_types [optional] Can be used to mask the triggering of the
 * error_handler function just like the error_reporting ini setting 
 * controls which errors are shown. Without this mask set the
 * error_handler will be called for every error
 * regardless to the setting of the error_reporting setting.
 * @return mixed a string containing the previously defined error handler (if any). If
 * the built-in error handler is used null is returned. null is also returned
 * in case of an error such as an invalid callback. If the previous error handler
 * was a class method, this function will return an indexed array with the class
 * and the method name.
 */
function set_error_handler (callable $error_handler, int $error_types = null) {}

/**
 * Restores the previous error handler function
 * @link http://www.php.net/manual/en/function.restore-error-handler.php
 * @return bool This function always returns true.
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
 * null may be passed instead, to reset this handler to its default state.
 * </p>
 * <p>
 * Note that providing an explicit Exception type
 * hint for the ex parameter in your callback will
 * cause issues with the changed exception hierarchy in PHP 7.
 * </p>
 * @return callable the name of the previously defined exception handler, or null on error. If
 * no previous handler was defined, null is also returned.
 */
function set_exception_handler (callable $exception_handler) {}

/**
 * Restores the previously defined exception handler function
 * @link http://www.php.net/manual/en/function.restore-exception-handler.php
 * @return bool This function always returns true.
 */
function restore_exception_handler () {}

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
function get_declared_classes () {}

/**
 * Returns an array of all declared traits
 * @link http://www.php.net/manual/en/function.get-declared-traits.php
 * @return array an array with names of all declared traits in values.
 * Returns null in case of a failure.
 */
function get_declared_traits () {}

/**
 * Returns an array of all declared interfaces
 * @link http://www.php.net/manual/en/function.get-declared-interfaces.php
 * @return array an array of the names of the declared interfaces in the current
 * script.
 */
function get_declared_interfaces () {}

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
function get_defined_functions (bool $exclude_disabled = null) {}

/**
 * Returns an array of all defined variables
 * @link http://www.php.net/manual/en/function.get-defined-vars.php
 * @return array A multidimensional array with all the variables.
 */
function get_defined_vars () {}

/**
 * Create an anonymous (lambda-style) function
 * @link http://www.php.net/manual/en/function.create-function.php
 * @param string $args The function arguments.
 * @param string $code The function code.
 * @return string a unique function name as a string, or false on error.
 * @deprecated 
 */
function create_function (string $args, string $code) {}

/**
 * Returns the resource type
 * @link http://www.php.net/manual/en/function.get-resource-type.php
 * @param resource $handle The evaluated resource handle.
 * @return string If the given handle is a resource, this function
 * will return a string representing its type. If the type is not identified
 * by this function, the return value will be the string 
 * Unknown.
 * <p>
 * This function will return null and generate an error if 
 * handle is not a resource.
 * </p>
 */
function get_resource_type ($handle) {}

/**
 * Returns active resources
 * @link http://www.php.net/manual/en/function.get-resources.php
 * @param string $type [optional] <p>
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
function get_resources (string $type = null) {}

/**
 * Returns an array with the names of all modules compiled and loaded
 * @link http://www.php.net/manual/en/function.get-loaded-extensions.php
 * @param bool $zend_extensions [optional] Only return Zend extensions, if not then regular extensions, like 
 * mysqli are listed. Defaults to false (return regular extensions).
 * @return array an indexed array of all the modules names.
 */
function get_loaded_extensions (bool $zend_extensions = null) {}

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
 * @return bool true if the extension identified by name
 * is loaded, false otherwise.
 */
function extension_loaded (string $name) {}

/**
 * Returns an array with the names of the functions of a module
 * @link http://www.php.net/manual/en/function.get-extension-funcs.php
 * @param string $module_name <p>
 * The module name.
 * </p>
 * <p>
 * This parameter must be in lowercase.
 * </p>
 * @return array an array with all the functions, or false if 
 * module_name is not a valid extension.
 */
function get_extension_funcs (string $module_name) {}

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
function get_defined_constants (bool $categorize = null) {}

/**
 * Generates a backtrace
 * @link http://www.php.net/manual/en/function.debug-backtrace.php
 * @param int $options [optional] As of 5.3.6, this parameter is a bitmask for the following options:
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
 * Before 5.3.6, the only values recognized are true or false, which are the same as 
 * setting or not setting the DEBUG_BACKTRACE_PROVIDE_OBJECT option respectively.
 * @param int $limit [optional] As of 5.4.0, this parameter can be used to limit the number of stack frames returned.
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
 * <td>integer</td>
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
 * The current call type. If a method call, "->" is returned. If a static
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
function debug_backtrace (int $options = null, int $limit = null) {}

/**
 * Prints a backtrace
 * @link http://www.php.net/manual/en/function.debug-print-backtrace.php
 * @param int $options [optional] As of 5.3.6, this parameter is a bitmask for the following options:
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
 * @param int $limit [optional] As of 5.4.0, this parameter can be used to limit the number of stack frames printed.
 * By default (limit=0) it prints all stack frames.
 * @return void 
 */
function debug_print_backtrace (int $options = null, int $limit = null) {}

/**
 * Reclaims memory used by the Zend Engine memory manager
 * @link http://www.php.net/manual/en/function.gc-mem-caches.php
 * @return int the number of bytes freed.
 */
function gc_mem_caches () {}

/**
 * Forces collection of any existing garbage cycles
 * @link http://www.php.net/manual/en/function.gc-collect-cycles.php
 * @return int number of collected cycles.
 */
function gc_collect_cycles () {}

/**
 * Returns status of the circular reference collector
 * @link http://www.php.net/manual/en/function.gc-enabled.php
 * @return bool true if the garbage collector is enabled, false otherwise.
 */
function gc_enabled () {}

/**
 * Activates the circular reference collector
 * @link http://www.php.net/manual/en/function.gc-enable.php
 * @return void 
 */
function gc_enable () {}

/**
 * Deactivates the circular reference collector
 * @link http://www.php.net/manual/en/function.gc-disable.php
 * @return void 
 */
function gc_disable () {}

function gc_status () {}


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
define ('ZEND_THREAD_SAFE', true);
define ('ZEND_DEBUG_BUILD', false);
define ('NULL', null);
define ('PHP_VERSION', "7.3.0");
define ('PHP_MAJOR_VERSION', 7);
define ('PHP_MINOR_VERSION', 3);
define ('PHP_RELEASE_VERSION', 0);
define ('PHP_EXTRA_VERSION', "");
define ('PHP_VERSION_ID', 70300);
define ('PHP_ZTS', 1);
define ('PHP_DEBUG', 0);
define ('PHP_OS', "WINNT");
define ('PHP_OS_FAMILY', "Windows");
define ('PHP_SAPI', "cli");
define ('DEFAULT_INCLUDE_PATH', ".;C:\php\pear");
define ('PEAR_INSTALL_DIR', "C:\php\pear");
define ('PEAR_EXTENSION_DIR', "C:\php\ext");
define ('PHP_EXTENSION_DIR', "C:\php\ext");
define ('PHP_PREFIX', "C:\php");
define ('PHP_BINDIR', "C:\php");
define ('PHP_LIBDIR', "C:\php");
define ('PHP_DATADIR', "C:\php");
define ('PHP_SYSCONFDIR', "C:\php");
define ('PHP_LOCALSTATEDIR', "C:\php");
define ('PHP_CONFIG_FILE_PATH', "C:\WINDOWS");
define ('PHP_CONFIG_FILE_SCAN_DIR', "");
define ('PHP_SHLIB_SUFFIX', "dll");
define ('PHP_EOL', "\r\n");
define ('PHP_MAXPATHLEN', 2048);
define ('PHP_INT_MAX', 9223372036854775807);
define ('PHP_INT_MIN', -9223372036854775808);
define ('PHP_INT_SIZE', 8);
define ('PHP_FD_SETSIZE', 256);
define ('PHP_FLOAT_DIG', 15);
define ('PHP_FLOAT_EPSILON', 2.2204460492503E-16);
define ('PHP_FLOAT_MAX', 1.7976931348623E+308);
define ('PHP_FLOAT_MIN', 2.2250738585072E-308);
define ('PHP_WINDOWS_VERSION_MAJOR', 10);
define ('PHP_WINDOWS_VERSION_MINOR', 0);
define ('PHP_WINDOWS_VERSION_BUILD', 17134);
define ('PHP_WINDOWS_VERSION_PLATFORM', 2);
define ('PHP_WINDOWS_VERSION_SP_MAJOR', 0);
define ('PHP_WINDOWS_VERSION_SP_MINOR', 0);
define ('PHP_WINDOWS_VERSION_SUITEMASK', 256);
define ('PHP_WINDOWS_VERSION_PRODUCTTYPE', 1);
define ('PHP_WINDOWS_NT_DOMAIN_CONTROLLER', 2);
define ('PHP_WINDOWS_NT_SERVER', 3);
define ('PHP_WINDOWS_NT_WORKSTATION', 1);
define ('PHP_BINARY', "C:\php\php.exe");

/**
 * Indicates that output buffering has begun.
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
define ('STDIN', "Resource id #1");
define ('STDOUT', "Resource id #2");
define ('STDERR', "Resource id #3");

// End of Core v.7.3.0
