<?php

/**
 * Gets the version of the current Zend engine
 * @link http://php.net/manual/en/function.zend-version.php
 * @return string the Zend Engine version number, as a string.
 */
function zend_version () {}

/**
 * Returns the number of arguments passed to the function
 * @link http://php.net/manual/en/function.func-num-args.php
 * @return int the number of arguments passed into the current user-defined
 */
function func_num_args () {}

/**
 * Return an item from the argument list
 * @link http://php.net/manual/en/function.func-get-arg.php
 * @param arg_num int
 * @return mixed the specified argument, or false on error.
 */
function func_get_arg ($arg_num) {}

/**
 * Returns an array comprising a function's argument list
 * @link http://php.net/manual/en/function.func-get-args.php
 * @return array an array in which each element is a copy of the corresponding
 */
function func_get_args () {}

/**
 * Get string length
 * @link http://php.net/manual/en/function.strlen.php
 * @param string string
 * @return int 
 */
function strlen ($string) {}

/**
 * Binary safe string comparison
 * @link http://php.net/manual/en/function.strcmp.php
 * @param str1 string
 * @param str2 string
 * @return int &lt; 0 if str1 is less than
 */
function strcmp ($str1, $str2) {}

/**
 * Binary safe string comparison of the first n characters
 * @link http://php.net/manual/en/function.strncmp.php
 * @param str1 string
 * @param str2 string
 * @param len int
 * @return int &lt; 0 if str1 is less than
 */
function strncmp ($str1, $str2, $len) {}

/**
 * Binary safe case-insensitive string comparison
 * @link http://php.net/manual/en/function.strcasecmp.php
 * @param str1 string
 * @param str2 string
 * @return int &lt; 0 if str1 is less than
 */
function strcasecmp ($str1, $str2) {}

/**
 * Binary safe case-insensitive string comparison of the first n characters
 * @link http://php.net/manual/en/function.strncasecmp.php
 * @param str1 string
 * @param str2 string
 * @param len int
 * @return int &lt; 0 if str1 is less than
 */
function strncasecmp ($str1, $str2, $len) {}

/**
 * Return the current key and value pair from an array and advance the array cursor
 * @link http://php.net/manual/en/function.each.php
 * @param array array
 * @return array the current key and value pair from the array
 */
function each (array &$array) {}

/**
 * Sets which PHP errors are reported
 * @link http://php.net/manual/en/function.error-reporting.php
 * @param level int[optional]
 * @return int the old error_reporting
 */
function error_reporting ($level = null) {}

/**
 * Defines a named constant
 * @link http://php.net/manual/en/function.define.php
 * @param name string
 * @param value mixed
 * @param case_insensitive bool[optional]
 * @return bool 
 */
function define ($name, $value, $case_insensitive = null) {}

/**
 * Checks whether a given named constant exists
 * @link http://php.net/manual/en/function.defined.php
 * @param name string
 * @return bool true if the named constant given by name
 */
function defined ($name) {}

/**
 * Returns the name of the class of an object
 * @link http://php.net/manual/en/function.get-class.php
 * @param object object[optional]
 * @return string the name of the class of which object is an
 */
function get_class ($object = null) {}

/**
 * Retrieves the parent class name for object or class
 * @link http://php.net/manual/en/function.get-parent-class.php
 * @param object mixed[optional]
 * @return string the name of the parent class of the class of which
 */
function get_parent_class ($object = null) {}

/**
 * Checks if the class method exists
 * @link http://php.net/manual/en/function.method-exists.php
 * @param object object
 * @param method_name string
 * @return bool true if the method given by method_name
 */
function method_exists ($object, $method_name) {}

/**
 * Checks if the object or class has a property
 * @link http://php.net/manual/en/function.property-exists.php
 * @param class mixed
 * @param property string
 * @return bool true if the property exists, false if it doesn't exist or
 */
function property_exists ($class, $property) {}

/**
 * Checks if the class has been defined
 * @link http://php.net/manual/en/function.class-exists.php
 * @param class_name string
 * @param autoload bool[optional]
 * @return bool true if class_name is a defined class,
 */
function class_exists ($class_name, $autoload = null) {}

/**
 * Checks if the interface has been defined
 * @link http://php.net/manual/en/function.interface-exists.php
 * @param interface_name string
 * @param autoload bool[optional]
 * @return bool true if the interface given by
 */
function interface_exists ($interface_name, $autoload = null) {}

/**
 * Return &true; if the given function has been defined
 * @link http://php.net/manual/en/function.function-exists.php
 * @param function_name string
 * @return bool true if function_name exists and is a
 */
function function_exists ($function_name) {}

/**
 * Returns an array with the names of included or required files
 * @link http://php.net/manual/en/function.get-included-files.php
 * @return array an array of the names of all files.
 */
function get_included_files () {}

/**
 * &Alias; <function>get_included_files</function>
 * @link http://php.net/manual/en/function.get-required-files.php
 */
function get_required_files () {}

/**
 * Checks if the object has this class as one of its parents
 * @link http://php.net/manual/en/function.is-subclass-of.php
 * @param object mixed
 * @param class_name string
 * @return bool 
 */
function is_subclass_of ($object, $class_name) {}

/**
 * Checks if the object is of this class or has this class as one of its parents
 * @link http://php.net/manual/en/function.is-a.php
 * @param object object
 * @param class_name string
 * @return bool true if the object is of this class or has this class as one of
 */
function is_a ($object, $class_name) {}

/**
 * Get the default properties of the class
 * @link http://php.net/manual/en/function.get-class-vars.php
 * @param class_name string
 * @return array an associative array of default public properties of the class.
 */
function get_class_vars ($class_name) {}

/**
 * Gets the public properties of the given object
 * @link http://php.net/manual/en/function.get-object-vars.php
 * @param object object
 * @return array an associative array of defined object accessible non-static properties
 */
function get_object_vars ($object) {}

/**
 * Gets the class methods' names
 * @link http://php.net/manual/en/function.get-class-methods.php
 * @param class_name mixed
 * @return array an array of method names defined for the class specified by
 */
function get_class_methods ($class_name) {}

/**
 * Generates a user-level error/warning/notice message
 * @link http://php.net/manual/en/function.trigger-error.php
 * @param error_msg string
 * @param error_type int[optional]
 * @return bool 
 */
function trigger_error ($error_msg, $error_type = null) {}

/**
 * Alias of <function>trigger_error</function>
 * @link http://php.net/manual/en/function.user-error.php
 */
function user_error () {}

/**
 * Sets a user-defined error handler function
 * @link http://php.net/manual/en/function.set-error-handler.php
 * @param error_handler callback
 * @param error_types int[optional]
 * @return mixed a string containing the previously defined
 */
function set_error_handler ($error_handler, $error_types = null) {}

/**
 * Restores the previous error handler function
 * @link http://php.net/manual/en/function.restore-error-handler.php
 * @return bool 
 */
function restore_error_handler () {}

/**
 * Sets a user-defined exception handler function
 * @link http://php.net/manual/en/function.set-exception-handler.php
 * @param exception_handler callback
 * @return string the name of the previously defined exception handler, or &null; on error. If
 */
function set_exception_handler ($exception_handler) {}

/**
 * Restores the previously defined exception handler function
 * @link http://php.net/manual/en/function.restore-exception-handler.php
 * @return bool 
 */
function restore_exception_handler () {}

/**
 * Returns an array with the name of the defined classes
 * @link http://php.net/manual/en/function.get-declared-classes.php
 * @return array an array of the names of the declared classes in the current
 */
function get_declared_classes () {}

/**
 * Returns an array of all declared interfaces
 * @link http://php.net/manual/en/function.get-declared-interfaces.php
 * @return array an array of the names of the declared interfaces in the current
 */
function get_declared_interfaces () {}

/**
 * Returns an array of all defined functions
 * @link http://php.net/manual/en/function.get-defined-functions.php
 * @return array an multidimensional array containing a list of all defined
 */
function get_defined_functions () {}

/**
 * Returns an array of all defined variables
 * @link http://php.net/manual/en/function.get-defined-vars.php
 * @return array 
 */
function get_defined_vars () {}

/**
 * Create an anonymous (lambda-style) function
 * @link http://php.net/manual/en/function.create-function.php
 * @param args string
 * @param code string
 * @return string a unique function name as a string, or false on error.
 */
function create_function ($args, $code) {}

/**
 * Returns the resource type
 * @link http://php.net/manual/en/function.get-resource-type.php
 * @param handle resource
 * @return string 
 */
function get_resource_type ($handle) {}

/**
 * Returns an array with the names of all modules compiled and loaded
 * @link http://php.net/manual/en/function.get-loaded-extensions.php
 * @param zend_extensions bool[optional]
 * @return array an indexed array of all the modules names.
 */
function get_loaded_extensions ($zend_extensions = null) {}

/**
 * Find out whether an extension is loaded
 * @link http://php.net/manual/en/function.extension-loaded.php
 * @param name string
 * @return bool true if the extension identified by name
 */
function extension_loaded ($name) {}

/**
 * Returns an array with the names of the functions of a module
 * @link http://php.net/manual/en/function.get-extension-funcs.php
 * @param module_name string
 * @return array an array with all the functions, or false if
 */
function get_extension_funcs ($module_name) {}

/**
 * Returns an associative array with the names of all the constants and their values
 * @link http://php.net/manual/en/function.get-defined-constants.php
 * @param categorize mixed[optional]
 * @return array 
 */
function get_defined_constants ($categorize = null) {}

/**
 * Generates a backtrace
 * @link http://php.net/manual/en/function.debug-backtrace.php
 * @param provide_object bool[optional]
 * @return array an associative array. The possible returned elements
 */
function debug_backtrace ($provide_object = null) {}

/**
 * Prints a backtrace
 * @link http://php.net/manual/en/function.debug-print-backtrace.php
 * @return void 
 */
function debug_print_backtrace () {}

class stdClass  {
}

class Exception  {
	protected $message;
	private $string;
	protected $code;
	protected $file;
	protected $line;
	private $trace;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class ErrorException extends Exception  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $severity;


	/**
	 * @param message[optional]
	 * @param code[optional]
	 * @param severity[optional]
	 * @param filename[optional]
	 * @param lineno[optional]
	 */
	public function __construct ($message, $code, $severity, $filename, $lineno) {}

	final public function getSeverity () {}

	final private function __clone () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

interface Traversable  {
}

interface IteratorAggregate extends Traversable {

	abstract public function getIterator () {}

}

interface Iterator extends Traversable {

	abstract public function current () {}

	abstract public function next () {}

	abstract public function key () {}

	abstract public function valid () {}

	abstract public function rewind () {}

}

interface ArrayAccess  {

	/**
	 * @param offset
	 */
	abstract public function offsetExists ($offset) {}

	/**
	 * @param offset
	 */
	abstract public function offsetGet ($offset) {}

	/**
	 * @param offset
	 * @param value
	 */
	abstract public function offsetSet ($offset, $value) {}

	/**
	 * @param offset
	 */
	abstract public function offsetUnset ($offset) {}

}

interface Serializable  {

	abstract public function serialize () {}

	/**
	 * @param serialized
	 */
	abstract public function unserialize ($serialized) {}

}


/**
 * Fatal run-time errors. These indicate errors that can not be
 * recovered from, such as a memory allocation problem.
 * Execution of the script is halted.
 * @link http://php.net/manual/en/errorfunc.constants.php
 */
define ('E_ERROR', 1);

/**
 * Catchable fatal error. It indicates that a probably dangerous error
 * occured, but did not leave the Engine in an unstable state. If the error
 * is not caught by a user defined handle (see also
 * set_error_handler), the application aborts as it
 * was an E_ERROR.
 * @link http://php.net/manual/en/errorfunc.constants.php
 */
define ('E_RECOVERABLE_ERROR', 4096);

/**
 * Run-time warnings (non-fatal errors). Execution of the script is not
 * halted.
 * @link http://php.net/manual/en/errorfunc.constants.php
 */
define ('E_WARNING', 2);

/**
 * Compile-time parse errors. Parse errors should only be generated by
 * the parser.
 * @link http://php.net/manual/en/errorfunc.constants.php
 */
define ('E_PARSE', 4);

/**
 * Run-time notices. Indicate that the script encountered something that
 * could indicate an error, but could also happen in the normal course of
 * running a script.
 * @link http://php.net/manual/en/errorfunc.constants.php
 */
define ('E_NOTICE', 8);

/**
 * Run-time notices. Enable to have PHP suggest changes
 * to your code which will ensure the best interoperability
 * and forward compatibility of your code.
 * @link http://php.net/manual/en/errorfunc.constants.php
 */
define ('E_STRICT', 2048);

/**
 * Fatal errors that occur during PHP's initial startup. This is like an
 * E_ERROR, except it is generated by the core of PHP.
 * @link http://php.net/manual/en/errorfunc.constants.php
 */
define ('E_CORE_ERROR', 16);

/**
 * Warnings (non-fatal errors) that occur during PHP's initial startup.
 * This is like an E_WARNING, except it is generated
 * by the core of PHP.
 * @link http://php.net/manual/en/errorfunc.constants.php
 */
define ('E_CORE_WARNING', 32);

/**
 * Fatal compile-time errors. This is like an E_ERROR,
 * except it is generated by the Zend Scripting Engine.
 * @link http://php.net/manual/en/errorfunc.constants.php
 */
define ('E_COMPILE_ERROR', 64);

/**
 * Compile-time warnings (non-fatal errors). This is like an
 * E_WARNING, except it is generated by the Zend
 * Scripting Engine.
 * @link http://php.net/manual/en/errorfunc.constants.php
 */
define ('E_COMPILE_WARNING', 128);

/**
 * User-generated error message. This is like an
 * E_ERROR, except it is generated in PHP code by
 * using the PHP function trigger_error.
 * @link http://php.net/manual/en/errorfunc.constants.php
 */
define ('E_USER_ERROR', 256);

/**
 * User-generated warning message. This is like an
 * E_WARNING, except it is generated in PHP code by
 * using the PHP function trigger_error.
 * @link http://php.net/manual/en/errorfunc.constants.php
 */
define ('E_USER_WARNING', 512);

/**
 * User-generated notice message. This is like an
 * E_NOTICE, except it is generated in PHP code by
 * using the PHP function trigger_error.
 * @link http://php.net/manual/en/errorfunc.constants.php
 */
define ('E_USER_NOTICE', 1024);

/**
 * All errors and warnings, as supported, except of level
 * E_STRICT in PHP &lt; 6.
 * @link http://php.net/manual/en/errorfunc.constants.php
 */
define ('E_ALL', 6143);
define ('TRUE', true);
define ('FALSE', false);
define ('NULL', null);
define ('ZEND_THREAD_SAFE', false);
define ('PHP_VERSION', "5.2.6");
define ('PHP_OS', "Linux");
define ('PHP_SAPI', "cli");
define ('DEFAULT_INCLUDE_PATH', ".:/usr/local/zend/share/pear");
define ('PEAR_INSTALL_DIR', "/usr/local/zend/share/pear");
define ('PEAR_EXTENSION_DIR', "/usr/local/zend/lib/php/20060613");
define ('PHP_EXTENSION_DIR', "/usr/local/zend/lib/php/20060613");
define ('PHP_PREFIX', "/usr/local/zend");
define ('PHP_BINDIR', "/usr/local/zend/bin");
define ('PHP_LIBDIR', "/usr/local/zend/lib/php");
define ('PHP_DATADIR', "/usr/local/zend/share/php");
define ('PHP_SYSCONFDIR', "/usr/local/zend/etc");
define ('PHP_LOCALSTATEDIR', "/usr/local/zend/var");
define ('PHP_CONFIG_FILE_PATH', "/usr/local/zend/etc");
define ('PHP_CONFIG_FILE_SCAN_DIR', "/usr/local/zend/etc/conf.d");
define ('PHP_SHLIB_SUFFIX', "so");
define ('PHP_EOL', "\n");
define ('PHP_INT_MAX', 9223372036854775807);
define ('PHP_INT_SIZE', 8);
define ('PHP_OUTPUT_HANDLER_START', 1);
define ('PHP_OUTPUT_HANDLER_CONT', 2);
define ('PHP_OUTPUT_HANDLER_END', 4);
define ('UPLOAD_ERR_OK', 0);
define ('UPLOAD_ERR_INI_SIZE', 1);
define ('UPLOAD_ERR_FORM_SIZE', 2);
define ('UPLOAD_ERR_PARTIAL', 3);
define ('UPLOAD_ERR_NO_FILE', 4);
define ('UPLOAD_ERR_NO_TMP_DIR', 6);
define ('UPLOAD_ERR_CANT_WRITE', 7);
define ('UPLOAD_ERR_EXTENSION', 8);
define ('DEBUGGER_VERSION', "5.2.14");
define ('STDIN', "Resource id #1");
define ('STDOUT', "Resource id #2");
define ('STDERR', "Resource id #3");

/**
 * The full path and filename of the file. If used inside an include,
 * the name of the included file is returned.
 * Since PHP 4.0.2, __FILE__ always contains an
 * absolute path with symlinks resolved whereas in older versions it contained relative path
 * under some circumstances.
 * @link http://php.net/manual/en/language.constants.php
 */
define ('__FILE__', null);

/**
 * The current line number of the file.
 * @link http://php.net/manual/en/language.constants.php
 */
define ('__LINE__', null);

/**
 * The class name. (Added in PHP 4.3.0) As of PHP 5 this constant 
 * returns the class name as it was declared (case-sensitive). In PHP
 * 4 its value is always lowercased.
 * @link http://php.net/manual/en/language.constants.php
 */
define ('__CLASS__', null);

/**
 * The function name. (Added in PHP 4.3.0) As of PHP 5 this constant 
 * returns the function name as it was declared (case-sensitive). In
 * PHP 4 its value is always lowercased.
 * @link http://php.net/manual/en/language.constants.php
 */
define ('__FUNCTION__', null);

/**
 * The class method name. (Added in PHP 5.0.0) The method name is
 * returned as it was declared (case-sensitive).
 * @link http://php.net/manual/en/language.constants.php
 */
define ('__METHOD__', null);
?>
