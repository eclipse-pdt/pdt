<?php

// Start of Reflection v.8.1.19

/**
 * The ReflectionException class.
 * @link http://www.php.net/manual/en/class.reflectionexception.php
 */
class ReflectionException extends Exception implements Throwable, Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param ?Throwable|null $previous [optional]
	 */
	public function __construct (string $message = ''int , $code = 0?Throwable|null , $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ??Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

/**
 * The reflection class.
 * @link http://www.php.net/manual/en/class.reflection.php
 */
class Reflection  {

	/**
	 * Gets modifier names
	 * @link http://www.php.net/manual/en/reflection.getmodifiernames.php
	 * @param int $modifiers Bitfield of the modifiers to get.
	 * @return array An array of modifier names.
	 */
	public static function getModifierNames (int $modifiers) {}

}

/**
 * Reflector is an interface implemented by all
 * exportable Reflection classes.
 * @link http://www.php.net/manual/en/class.reflector.php
 */
interface Reflector extends Stringable {

	abstract public function __toString (): string

}

/**
 * A parent class to ReflectionFunction, read its
 * description for details.
 * @link http://www.php.net/manual/en/class.reflectionfunctionabstract.php
 */
abstract class ReflectionFunctionAbstract implements Reflector, Stringable {
	public $name;


	/**
	 * Clones function
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.clone.php
	 * @return void 
	 */
	private function __clone (): void {}

	/**
	 * Checks if function in namespace
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.innamespace.php
	 * @return bool true if it's in a namespace, otherwise false
	 */
	public function inNamespace () {}

	/**
	 * Checks if closure
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.isclosure.php
	 * @return bool true if the function is a Closure, otherwise false.
	 */
	public function isClosure () {}

	/**
	 * Checks if deprecated
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.isdeprecated.php
	 * @return bool true if it's deprecated, otherwise false
	 */
	public function isDeprecated () {}

	/**
	 * Checks if is internal
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.isinternal.php
	 * @return bool true if it's internal, otherwise false
	 */
	public function isInternal () {}

	/**
	 * Checks if user defined
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.isuserdefined.php
	 * @return bool true if it's user-defined, otherwise false;
	 */
	public function isUserDefined () {}

	/**
	 * Returns whether this function is a generator
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.isgenerator.php
	 * @return bool true if the function is generator, false if it is not or null
	 * on failure.
	 */
	public function isGenerator () {}

	/**
	 * Checks if the function is variadic
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.isvariadic.php
	 * @return bool true if the function is variadic, otherwise false.
	 */
	public function isVariadic () {}

	/**
	 * Checks if the function is static
	 * @link http://www.php.net/manual/en/reflectiofunctionabstract.isstatic.php
	 * @return bool true if the function is static, otherwise false
	 */
	public function isStatic () {}

	/**
	 * Returns this pointer bound to closure
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getclosurethis.php
	 * @return mixed $this pointer.
	 * Returns null in case of an error.
	 */
	public function getClosureThis () {}

	/**
	 * Returns the scope associated to the closure
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getclosurescopeclass.php
	 * @return mixed the class on success or null on failure.
	 */
	public function getClosureScopeClass () {}

	public function getClosureCalledClass () {}

	/**
	 * Returns an array of the used variables in the Closure
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getclosureusedvariables.php
	 * @return array an array of the used variables in the Closure.
	 */
	public function getClosureUsedVariables (): array {}

	/**
	 * Gets doc comment
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getdoccomment.php
	 * @return mixed The doc comment if it exists, otherwise false
	 */
	public function getDocComment () {}

	/**
	 * Gets end line number
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getendline.php
	 * @return mixed The ending line number of the user defined function, or false if unknown.
	 */
	public function getEndLine () {}

	/**
	 * Gets extension info
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getextension.php
	 * @return mixed The extension information, as a ReflectionExtension object,
	 * or null for user-defined functions.
	 */
	public function getExtension () {}

	/**
	 * Gets extension name
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getextensionname.php
	 * @return mixed The name of the extension which defined the function,
	 * or false for user-defined functions.
	 */
	public function getExtensionName () {}

	/**
	 * Gets file name
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getfilename.php
	 * @return mixed the filename of the file in which the function has been defined.
	 * If the class is defined in the PHP core or in a PHP extension, false
	 * is returned.
	 */
	public function getFileName () {}

	/**
	 * Gets function name
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getname.php
	 * @return string The name of the function.
	 */
	public function getName () {}

	/**
	 * Gets namespace name
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getnamespacename.php
	 * @return string The namespace name.
	 */
	public function getNamespaceName () {}

	/**
	 * Gets number of parameters
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getnumberofparameters.php
	 * @return int The number of parameters.
	 */
	public function getNumberOfParameters () {}

	/**
	 * Gets number of required parameters
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getnumberofrequiredparameters.php
	 * @return int The number of required parameters.
	 */
	public function getNumberOfRequiredParameters () {}

	/**
	 * Gets parameters
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getparameters.php
	 * @return array The parameters, as a ReflectionParameter object.
	 */
	public function getParameters () {}

	/**
	 * Gets function short name
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getshortname.php
	 * @return string The short name of the function.
	 */
	public function getShortName () {}

	/**
	 * Gets starting line number
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getstartline.php
	 * @return mixed The starting line number, or false if unknown.
	 */
	public function getStartLine () {}

	/**
	 * Gets static variables
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getstaticvariables.php
	 * @return array An array of static variables.
	 */
	public function getStaticVariables () {}

	/**
	 * Checks if returns reference
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.returnsreference.php
	 * @return bool true if it returns a reference, otherwise false
	 */
	public function returnsReference () {}

	/**
	 * Checks if the function has a specified return type
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.hasreturntype.php
	 * @return bool true if the function is a specified return type, otherwise false.
	 */
	public function hasReturnType () {}

	/**
	 * Gets the specified return type of a function
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getreturntype.php
	 * @return mixed a ReflectionType object if a return type is
	 * specified, null otherwise.
	 */
	public function getReturnType () {}

	/**
	 * Returns whether the function has a tentative return type
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.hastentativereturntype.php
	 * @return bool true if the function has a tentative return type, otherwise false.
	 */
	public function hasTentativeReturnType (): bool {}

	/**
	 * Returns the tentative return type associated with the function
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.gettentativereturntype.php
	 * @return mixed a ReflectionType object if a tentative return type is
	 * specified, null otherwise.
	 */
	public function getTentativeReturnType (): ??ReflectionType {}

	/**
	 * Gets Attributes
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getattributes.php
	 * @param mixed $name [optional] 
	 * @param int $flags [optional] 
	 * @return array Array of attributes, as a ReflectionAttribute object.
	 */
	public function getAttributes ($name = null, int $flags = null): array {}

	abstract public function __toString (): string

}

/**
 * The ReflectionFunction class reports
 * information about a function.
 * @link http://www.php.net/manual/en/class.reflectionfunction.php
 */
class ReflectionFunction extends ReflectionFunctionAbstract implements Stringable, Reflector {
	const IS_DEPRECATED = 2048;

	public $name;


	/**
	 * Constructs a ReflectionFunction object
	 * @link http://www.php.net/manual/en/reflectionfunction.construct.php
	 * @param Closure|string $function
	 */
	public function __construct (Closure|string $function) {}

	/**
	 * To string
	 * @link http://www.php.net/manual/en/reflectionfunction.tostring.php
	 * @return string ReflectionFunction::export-like output for 
	 * the function.
	 */
	public function __toString (): string {}

	/**
	 * Checks if function is disabled
	 * @link http://www.php.net/manual/en/reflectionfunction.isdisabled.php
	 * @return bool true if it's disable, otherwise false
	 * @deprecated 
	 */
	public function isDisabled () {}

	/**
	 * Invokes function
	 * @link http://www.php.net/manual/en/reflectionfunction.invoke.php
	 * @param mixed $args The passed in argument list. It accepts a variable number of
	 * arguments which are passed to the function much like
	 * call_user_func is.
	 * @return mixed the result of the invoked function call.
	 */
	public function invoke ($args) {}

	/**
	 * Invokes function args
	 * @link http://www.php.net/manual/en/reflectionfunction.invokeargs.php
	 * @param array $args The passed arguments to the function as an array, much like 
	 * call_user_func_array works.
	 * @return mixed the result of the invoked function
	 */
	public function invokeArgs (array $args) {}

	/**
	 * Returns a dynamically created closure for the function
	 * @link http://www.php.net/manual/en/reflectionfunction.getclosure.php
	 * @return Closure Closure.
	 */
	public function getClosure () {}

	/**
	 * Checks if function in namespace
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.innamespace.php
	 * @return bool true if it's in a namespace, otherwise false
	 */
	public function inNamespace () {}

	/**
	 * Checks if closure
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.isclosure.php
	 * @return bool true if the function is a Closure, otherwise false.
	 */
	public function isClosure () {}

	/**
	 * Checks if deprecated
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.isdeprecated.php
	 * @return bool true if it's deprecated, otherwise false
	 */
	public function isDeprecated () {}

	/**
	 * Checks if is internal
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.isinternal.php
	 * @return bool true if it's internal, otherwise false
	 */
	public function isInternal () {}

	/**
	 * Checks if user defined
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.isuserdefined.php
	 * @return bool true if it's user-defined, otherwise false;
	 */
	public function isUserDefined () {}

	/**
	 * Returns whether this function is a generator
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.isgenerator.php
	 * @return bool true if the function is generator, false if it is not or null
	 * on failure.
	 */
	public function isGenerator () {}

	/**
	 * Checks if the function is variadic
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.isvariadic.php
	 * @return bool true if the function is variadic, otherwise false.
	 */
	public function isVariadic () {}

	/**
	 * Checks if the function is static
	 * @link http://www.php.net/manual/en/reflectiofunctionabstract.isstatic.php
	 * @return bool true if the function is static, otherwise false
	 */
	public function isStatic () {}

	/**
	 * Returns this pointer bound to closure
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getclosurethis.php
	 * @return mixed $this pointer.
	 * Returns null in case of an error.
	 */
	public function getClosureThis () {}

	/**
	 * Returns the scope associated to the closure
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getclosurescopeclass.php
	 * @return mixed the class on success or null on failure.
	 */
	public function getClosureScopeClass () {}

	public function getClosureCalledClass () {}

	/**
	 * Returns an array of the used variables in the Closure
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getclosureusedvariables.php
	 * @return array an array of the used variables in the Closure.
	 */
	public function getClosureUsedVariables (): array {}

	/**
	 * Gets doc comment
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getdoccomment.php
	 * @return mixed The doc comment if it exists, otherwise false
	 */
	public function getDocComment () {}

	/**
	 * Gets end line number
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getendline.php
	 * @return mixed The ending line number of the user defined function, or false if unknown.
	 */
	public function getEndLine () {}

	/**
	 * Gets extension info
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getextension.php
	 * @return mixed The extension information, as a ReflectionExtension object,
	 * or null for user-defined functions.
	 */
	public function getExtension () {}

	/**
	 * Gets extension name
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getextensionname.php
	 * @return mixed The name of the extension which defined the function,
	 * or false for user-defined functions.
	 */
	public function getExtensionName () {}

	/**
	 * Gets file name
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getfilename.php
	 * @return mixed the filename of the file in which the function has been defined.
	 * If the class is defined in the PHP core or in a PHP extension, false
	 * is returned.
	 */
	public function getFileName () {}

	/**
	 * Gets function name
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getname.php
	 * @return string The name of the function.
	 */
	public function getName () {}

	/**
	 * Gets namespace name
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getnamespacename.php
	 * @return string The namespace name.
	 */
	public function getNamespaceName () {}

	/**
	 * Gets number of parameters
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getnumberofparameters.php
	 * @return int The number of parameters.
	 */
	public function getNumberOfParameters () {}

	/**
	 * Gets number of required parameters
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getnumberofrequiredparameters.php
	 * @return int The number of required parameters.
	 */
	public function getNumberOfRequiredParameters () {}

	/**
	 * Gets parameters
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getparameters.php
	 * @return array The parameters, as a ReflectionParameter object.
	 */
	public function getParameters () {}

	/**
	 * Gets function short name
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getshortname.php
	 * @return string The short name of the function.
	 */
	public function getShortName () {}

	/**
	 * Gets starting line number
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getstartline.php
	 * @return mixed The starting line number, or false if unknown.
	 */
	public function getStartLine () {}

	/**
	 * Gets static variables
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getstaticvariables.php
	 * @return array An array of static variables.
	 */
	public function getStaticVariables () {}

	/**
	 * Checks if returns reference
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.returnsreference.php
	 * @return bool true if it returns a reference, otherwise false
	 */
	public function returnsReference () {}

	/**
	 * Checks if the function has a specified return type
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.hasreturntype.php
	 * @return bool true if the function is a specified return type, otherwise false.
	 */
	public function hasReturnType () {}

	/**
	 * Gets the specified return type of a function
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getreturntype.php
	 * @return mixed a ReflectionType object if a return type is
	 * specified, null otherwise.
	 */
	public function getReturnType () {}

	/**
	 * Returns whether the function has a tentative return type
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.hastentativereturntype.php
	 * @return bool true if the function has a tentative return type, otherwise false.
	 */
	public function hasTentativeReturnType (): bool {}

	/**
	 * Returns the tentative return type associated with the function
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.gettentativereturntype.php
	 * @return mixed a ReflectionType object if a tentative return type is
	 * specified, null otherwise.
	 */
	public function getTentativeReturnType (): ??ReflectionType {}

	/**
	 * Gets Attributes
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getattributes.php
	 * @param mixed $name [optional] 
	 * @param int $flags [optional] 
	 * @return array Array of attributes, as a ReflectionAttribute object.
	 */
	public function getAttributes ($name = null, int $flags = null): array {}

}

/**
 * The ReflectionGenerator class reports
 * information about a generator.
 * @link http://www.php.net/manual/en/class.reflectiongenerator.php
 */
final class ReflectionGenerator  {

	/**
	 * Constructs a ReflectionGenerator object
	 * @link http://www.php.net/manual/en/reflectiongenerator.construct.php
	 * @param Generator $generator
	 */
	public function __construct (Generator $generator) {}

	/**
	 * Gets the currently executing line of the generator
	 * @link http://www.php.net/manual/en/reflectiongenerator.getexecutingline.php
	 * @return int the line number of the currently executing statement in the generator.
	 */
	public function getExecutingLine () {}

	/**
	 * Gets the file name of the currently executing generator
	 * @link http://www.php.net/manual/en/reflectiongenerator.getexecutingfile.php
	 * @return string the full path and file name of the currently executing generator.
	 */
	public function getExecutingFile () {}

	/**
	 * Gets the trace of the executing generator
	 * @link http://www.php.net/manual/en/reflectiongenerator.gettrace.php
	 * @param int $options [optional] <p>
	 * The value of options can be any of
	 * the following flags.
	 * </p>
	 * <p>
	 * <table>
	 * Available options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>
	 * DEBUG_BACKTRACE_PROVIDE_OBJECT
	 * </td>
	 * <td>
	 * Default.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>
	 * DEBUG_BACKTRACE_IGNORE_ARGS
	 * </td>
	 * <td>
	 * Don't include the argument information for functions in the stack
	 * trace.
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * @return array the trace of the currently executing generator.
	 */
	public function getTrace (int $options = null) {}

	/**
	 * Gets the function name of the generator
	 * @link http://www.php.net/manual/en/reflectiongenerator.getfunction.php
	 * @return ReflectionFunctionAbstract a ReflectionFunctionAbstract class. This will
	 * be ReflectionFunction for functions, or
	 * ReflectionMethod for methods.
	 */
	public function getFunction () {}

	/**
	 * Gets the $this value of the generator
	 * @link http://www.php.net/manual/en/reflectiongenerator.getthis.php
	 * @return mixed the $this value, or null if the generator was
	 * not created in a class context.
	 */
	public function getThis () {}

	/**
	 * Gets the executing Generator object
	 * @link http://www.php.net/manual/en/reflectiongenerator.getexecutinggenerator.php
	 * @return Generator the currently executing Generator object.
	 */
	public function getExecutingGenerator () {}

}

/**
 * The ReflectionParameter class retrieves
 * information about function's or method's parameters.
 * <p>To introspect function parameters, first create an instance
 * of the ReflectionFunction or
 * ReflectionMethod classes and then use their
 * ReflectionFunctionAbstract::getParameters method
 * to retrieve an array of parameters.</p>
 * @link http://www.php.net/manual/en/class.reflectionparameter.php
 */
class ReflectionParameter implements Stringable, Reflector {
	public $name;


	/**
	 * Clone
	 * @link http://www.php.net/manual/en/reflectionparameter.clone.php
	 * @return void 
	 */
	private function __clone (): void {}

	/**
	 * Construct
	 * @link http://www.php.net/manual/en/reflectionparameter.construct.php
	 * @param mixed $function
	 * @param string|int $param
	 */
	public function __construct ($function = nullstring|int , $param) {}

	/**
	 * To string
	 * @link http://www.php.net/manual/en/reflectionparameter.tostring.php
	 * @return string 
	 */
	public function __toString (): string {}

	/**
	 * Gets parameter name
	 * @link http://www.php.net/manual/en/reflectionparameter.getname.php
	 * @return string The name of the reflected parameter.
	 */
	public function getName () {}

	/**
	 * Checks if passed by reference
	 * @link http://www.php.net/manual/en/reflectionparameter.ispassedbyreference.php
	 * @return bool true if the parameter is passed in by reference, otherwise false
	 */
	public function isPassedByReference () {}

	/**
	 * Returns whether this parameter can be passed by value
	 * @link http://www.php.net/manual/en/reflectionparameter.canbepassedbyvalue.php
	 * @return bool true if the parameter can be passed by value, false otherwise.
	 * Returns null in case of an error.
	 */
	public function canBePassedByValue () {}

	/**
	 * Gets declaring function
	 * @link http://www.php.net/manual/en/reflectionparameter.getdeclaringfunction.php
	 * @return ReflectionFunctionAbstract A ReflectionFunction object.
	 */
	public function getDeclaringFunction () {}

	/**
	 * Gets declaring class
	 * @link http://www.php.net/manual/en/reflectionparameter.getdeclaringclass.php
	 * @return mixed A ReflectionClass object or null if called on function.
	 */
	public function getDeclaringClass () {}

	/**
	 * Get a ReflectionClass object for the parameter being reflected or null
	 * @link http://www.php.net/manual/en/reflectionparameter.getclass.php
	 * @return mixed A ReflectionClass object, or null if no type is declared,
	 * or the declared type is not a class or interface.
	 * @deprecated 
	 */
	public function getClass () {}

	/**
	 * Checks if parameter has a type
	 * @link http://www.php.net/manual/en/reflectionparameter.hastype.php
	 * @return bool true if a type is specified, false otherwise.
	 */
	public function hasType () {}

	/**
	 * Gets a parameter's type
	 * @link http://www.php.net/manual/en/reflectionparameter.gettype.php
	 * @return mixed a ReflectionType object if a parameter type is
	 * specified, null otherwise.
	 */
	public function getType () {}

	/**
	 * Checks if parameter expects an array
	 * @link http://www.php.net/manual/en/reflectionparameter.isarray.php
	 * @return bool true if an array is expected, false otherwise.
	 * @deprecated 
	 */
	public function isArray () {}

	/**
	 * Returns whether parameter MUST be callable
	 * @link http://www.php.net/manual/en/reflectionparameter.iscallable.php
	 * @return bool true if the parameter is callable, false if it is
	 * not or null on failure.
	 * @deprecated 
	 */
	public function isCallable () {}

	/**
	 * Checks if null is allowed
	 * @link http://www.php.net/manual/en/reflectionparameter.allowsnull.php
	 * @return bool true if null is allowed, otherwise false
	 */
	public function allowsNull () {}

	/**
	 * Gets parameter position
	 * @link http://www.php.net/manual/en/reflectionparameter.getposition.php
	 * @return int The position of the parameter, left to right, starting at position #0.
	 */
	public function getPosition () {}

	/**
	 * Checks if optional
	 * @link http://www.php.net/manual/en/reflectionparameter.isoptional.php
	 * @return bool true if the parameter is optional, otherwise false
	 */
	public function isOptional () {}

	/**
	 * Checks if a default value is available
	 * @link http://www.php.net/manual/en/reflectionparameter.isdefaultvalueavailable.php
	 * @return bool true if a default value is available, otherwise false
	 */
	public function isDefaultValueAvailable () {}

	/**
	 * Gets default parameter value
	 * @link http://www.php.net/manual/en/reflectionparameter.getdefaultvalue.php
	 * @return mixed The parameters default value.
	 */
	public function getDefaultValue () {}

	/**
	 * Returns whether the default value of this parameter is a constant
	 * @link http://www.php.net/manual/en/reflectionparameter.isdefaultvalueconstant.php
	 * @return bool true if the default value is constant, and false otherwise.
	 */
	public function isDefaultValueConstant () {}

	/**
	 * Returns the default value's constant name if default value is constant or null
	 * @link http://www.php.net/manual/en/reflectionparameter.getdefaultvalueconstantname.php
	 * @return mixed string on success or null on failure.
	 */
	public function getDefaultValueConstantName () {}

	/**
	 * Checks if the parameter is variadic
	 * @link http://www.php.net/manual/en/reflectionparameter.isvariadic.php
	 * @return bool true if the parameter is variadic, otherwise false.
	 */
	public function isVariadic () {}

	public function isPromoted (): bool {}

	/**
	 * Gets Attributes
	 * @link http://www.php.net/manual/en/reflectionparameter.getattributes.php
	 * @param mixed $name [optional] 
	 * @param int $flags [optional] 
	 * @return array Array of attributes, as a ReflectionAttribute object.
	 */
	public function getAttributes ($name = null, int $flags = null): array {}

}

/**
 * The ReflectionType class reports
 * information about a function's parameter/return type or a class's property type.
 * The Reflection extension declares the following subtypes:
 * <p>
 * ReflectionNamedType (as of PHP 7.1.0)
 * ReflectionUnionType (as of PHP 8.0.0)
 * ReflectionIntersectionType (as of PHP 8.1.0)
 * </p>
 * @link http://www.php.net/manual/en/class.reflectiontype.php
 */
abstract class ReflectionType implements Stringable {

	private function __clone (): void {}

	/**
	 * Checks if null is allowed
	 * @link http://www.php.net/manual/en/reflectiontype.allowsnull.php
	 * @return bool true if null is allowed, otherwise false
	 */
	public function allowsNull () {}

	/**
	 * To string
	 * @link http://www.php.net/manual/en/reflectiontype.tostring.php
	 * @return string the type of the parameter.
	 */
	public function __toString (): string {}

}

/**
 * @link http://www.php.net/manual/en/class.reflectionnamedtype.php
 */
class ReflectionNamedType extends ReflectionType implements Stringable {

	/**
	 * Get the name of the type as a string
	 * @link http://www.php.net/manual/en/reflectionnamedtype.getname.php
	 * @return string the name of the type being reflected.
	 */
	public function getName () {}

	/**
	 * Checks if it is a built-in type
	 * @link http://www.php.net/manual/en/reflectionnamedtype.isbuiltin.php
	 * @return bool true if it's a built-in type, otherwise false
	 */
	public function isBuiltin () {}

	/**
	 * Checks if null is allowed
	 * @link http://www.php.net/manual/en/reflectiontype.allowsnull.php
	 * @return bool true if null is allowed, otherwise false
	 */
	public function allowsNull () {}

	/**
	 * To string
	 * @link http://www.php.net/manual/en/reflectiontype.tostring.php
	 * @return string the type of the parameter.
	 */
	public function __toString (): string {}

}

/**
 * @link http://www.php.net/manual/en/class.reflectionuniontype.php
 */
class ReflectionUnionType extends ReflectionType implements Stringable {

	/**
	 * Returns the types included in the union type
	 * @link http://www.php.net/manual/en/reflectionuniontype.gettypes.php
	 * @return array An array of ReflectionType objects.
	 */
	public function getTypes (): array {}

	/**
	 * Checks if null is allowed
	 * @link http://www.php.net/manual/en/reflectiontype.allowsnull.php
	 * @return bool true if null is allowed, otherwise false
	 */
	public function allowsNull () {}

	/**
	 * To string
	 * @link http://www.php.net/manual/en/reflectiontype.tostring.php
	 * @return string the type of the parameter.
	 */
	public function __toString (): string {}

}

/**
 * @link http://www.php.net/manual/en/class.reflectionintersectiontype.php
 */
class ReflectionIntersectionType extends ReflectionType implements Stringable {

	/**
	 * Returns the types included in the intersection type
	 * @link http://www.php.net/manual/en/reflectionintersectiontype.gettypes.php
	 * @return array An array of ReflectionType objects.
	 */
	public function getTypes (): array {}

	/**
	 * Checks if null is allowed
	 * @link http://www.php.net/manual/en/reflectiontype.allowsnull.php
	 * @return bool true if null is allowed, otherwise false
	 */
	public function allowsNull () {}

	/**
	 * To string
	 * @link http://www.php.net/manual/en/reflectiontype.tostring.php
	 * @return string the type of the parameter.
	 */
	public function __toString (): string {}

}

/**
 * The ReflectionMethod class reports
 * information about a method.
 * @link http://www.php.net/manual/en/class.reflectionmethod.php
 */
class ReflectionMethod extends ReflectionFunctionAbstract implements Stringable, Reflector {
	const IS_STATIC = 16;
	const IS_PUBLIC = 1;
	const IS_PROTECTED = 2;
	const IS_PRIVATE = 4;
	const IS_ABSTRACT = 64;
	const IS_FINAL = 32;

	public $name;
	public $class;


	/**
	 * Constructs a ReflectionMethod
	 * @link http://www.php.net/manual/en/reflectionmethod.construct.php
	 * @param object|string $objectOrMethod
	 * @param ?string|null $method [optional]
	 */
	public function __construct (object|string $objectOrMethod?string|null , $method = null) {}

	/**
	 * Returns the string representation of the Reflection method object
	 * @link http://www.php.net/manual/en/reflectionmethod.tostring.php
	 * @return string A string representation of this ReflectionMethod instance.
	 */
	public function __toString (): string {}

	/**
	 * Checks if method is public
	 * @link http://www.php.net/manual/en/reflectionmethod.ispublic.php
	 * @return bool true if the method is public, otherwise false
	 */
	public function isPublic () {}

	/**
	 * Checks if method is private
	 * @link http://www.php.net/manual/en/reflectionmethod.isprivate.php
	 * @return bool true if the method is private, otherwise false
	 */
	public function isPrivate () {}

	/**
	 * Checks if method is protected
	 * @link http://www.php.net/manual/en/reflectionmethod.isprotected.php
	 * @return bool true if the method is protected, otherwise false
	 */
	public function isProtected () {}

	/**
	 * Checks if method is abstract
	 * @link http://www.php.net/manual/en/reflectionmethod.isabstract.php
	 * @return bool true if the method is abstract, otherwise false
	 */
	public function isAbstract () {}

	/**
	 * Checks if method is final
	 * @link http://www.php.net/manual/en/reflectionmethod.isfinal.php
	 * @return bool true if the method is final, otherwise false
	 */
	public function isFinal () {}

	/**
	 * Checks if method is a constructor
	 * @link http://www.php.net/manual/en/reflectionmethod.isconstructor.php
	 * @return bool true if the method is a constructor, otherwise false
	 */
	public function isConstructor () {}

	/**
	 * Checks if method is a destructor
	 * @link http://www.php.net/manual/en/reflectionmethod.isdestructor.php
	 * @return bool true if the method is a destructor, otherwise false
	 */
	public function isDestructor () {}

	/**
	 * Returns a dynamically created closure for the method
	 * @link http://www.php.net/manual/en/reflectionmethod.getclosure.php
	 * @param mixed $object [optional] Forbidden for static methods, required for other methods.
	 * @return Closure Closure.
	 * Returns null in case of an error.
	 */
	public function getClosure ($object = null) {}

	/**
	 * Gets the method modifiers
	 * @link http://www.php.net/manual/en/reflectionmethod.getmodifiers.php
	 * @return int A numeric representation of the modifiers.
	 * The actual meaning of these modifiers are described under
	 * predefined constants.
	 */
	public function getModifiers () {}

	/**
	 * Invoke
	 * @link http://www.php.net/manual/en/reflectionmethod.invoke.php
	 * @param mixed $object The object to invoke the method on. For static methods, pass
	 * null to this parameter.
	 * @param mixed $args Zero or more parameters to be passed to the method.
	 * It accepts a variable number of parameters which are passed to the method.
	 * @return mixed the method result.
	 */
	public function invoke ($object, $args) {}

	/**
	 * Invoke args
	 * @link http://www.php.net/manual/en/reflectionmethod.invokeargs.php
	 * @param mixed $object The object to invoke the method on. In case of static methods, you can pass
	 * null to this parameter.
	 * @param array $args The parameters to be passed to the function, as an array.
	 * @return mixed the method result.
	 */
	public function invokeArgs ($object, array $args) {}

	/**
	 * Gets declaring class for the reflected method
	 * @link http://www.php.net/manual/en/reflectionmethod.getdeclaringclass.php
	 * @return ReflectionClass A ReflectionClass object of the class that the
	 * reflected method is part of.
	 */
	public function getDeclaringClass () {}

	/**
	 * Gets the method prototype (if there is one)
	 * @link http://www.php.net/manual/en/reflectionmethod.getprototype.php
	 * @return ReflectionMethod A ReflectionMethod instance of the method prototype.
	 */
	public function getPrototype () {}

	/**
	 * Set method accessibility
	 * @link http://www.php.net/manual/en/reflectionmethod.setaccessible.php
	 * @param bool $accessible true to allow accessibility, or false.
	 * @return void 
	 */
	public function setAccessible (bool $accessible) {}

	/**
	 * Checks if function in namespace
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.innamespace.php
	 * @return bool true if it's in a namespace, otherwise false
	 */
	public function inNamespace () {}

	/**
	 * Checks if closure
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.isclosure.php
	 * @return bool true if the function is a Closure, otherwise false.
	 */
	public function isClosure () {}

	/**
	 * Checks if deprecated
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.isdeprecated.php
	 * @return bool true if it's deprecated, otherwise false
	 */
	public function isDeprecated () {}

	/**
	 * Checks if is internal
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.isinternal.php
	 * @return bool true if it's internal, otherwise false
	 */
	public function isInternal () {}

	/**
	 * Checks if user defined
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.isuserdefined.php
	 * @return bool true if it's user-defined, otherwise false;
	 */
	public function isUserDefined () {}

	/**
	 * Returns whether this function is a generator
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.isgenerator.php
	 * @return bool true if the function is generator, false if it is not or null
	 * on failure.
	 */
	public function isGenerator () {}

	/**
	 * Checks if the function is variadic
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.isvariadic.php
	 * @return bool true if the function is variadic, otherwise false.
	 */
	public function isVariadic () {}

	/**
	 * Checks if the function is static
	 * @link http://www.php.net/manual/en/reflectiofunctionabstract.isstatic.php
	 * @return bool true if the function is static, otherwise false
	 */
	public function isStatic () {}

	/**
	 * Returns this pointer bound to closure
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getclosurethis.php
	 * @return mixed $this pointer.
	 * Returns null in case of an error.
	 */
	public function getClosureThis () {}

	/**
	 * Returns the scope associated to the closure
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getclosurescopeclass.php
	 * @return mixed the class on success or null on failure.
	 */
	public function getClosureScopeClass () {}

	public function getClosureCalledClass () {}

	/**
	 * Returns an array of the used variables in the Closure
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getclosureusedvariables.php
	 * @return array an array of the used variables in the Closure.
	 */
	public function getClosureUsedVariables (): array {}

	/**
	 * Gets doc comment
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getdoccomment.php
	 * @return mixed The doc comment if it exists, otherwise false
	 */
	public function getDocComment () {}

	/**
	 * Gets end line number
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getendline.php
	 * @return mixed The ending line number of the user defined function, or false if unknown.
	 */
	public function getEndLine () {}

	/**
	 * Gets extension info
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getextension.php
	 * @return mixed The extension information, as a ReflectionExtension object,
	 * or null for user-defined functions.
	 */
	public function getExtension () {}

	/**
	 * Gets extension name
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getextensionname.php
	 * @return mixed The name of the extension which defined the function,
	 * or false for user-defined functions.
	 */
	public function getExtensionName () {}

	/**
	 * Gets file name
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getfilename.php
	 * @return mixed the filename of the file in which the function has been defined.
	 * If the class is defined in the PHP core or in a PHP extension, false
	 * is returned.
	 */
	public function getFileName () {}

	/**
	 * Gets function name
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getname.php
	 * @return string The name of the function.
	 */
	public function getName () {}

	/**
	 * Gets namespace name
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getnamespacename.php
	 * @return string The namespace name.
	 */
	public function getNamespaceName () {}

	/**
	 * Gets number of parameters
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getnumberofparameters.php
	 * @return int The number of parameters.
	 */
	public function getNumberOfParameters () {}

	/**
	 * Gets number of required parameters
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getnumberofrequiredparameters.php
	 * @return int The number of required parameters.
	 */
	public function getNumberOfRequiredParameters () {}

	/**
	 * Gets parameters
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getparameters.php
	 * @return array The parameters, as a ReflectionParameter object.
	 */
	public function getParameters () {}

	/**
	 * Gets function short name
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getshortname.php
	 * @return string The short name of the function.
	 */
	public function getShortName () {}

	/**
	 * Gets starting line number
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getstartline.php
	 * @return mixed The starting line number, or false if unknown.
	 */
	public function getStartLine () {}

	/**
	 * Gets static variables
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getstaticvariables.php
	 * @return array An array of static variables.
	 */
	public function getStaticVariables () {}

	/**
	 * Checks if returns reference
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.returnsreference.php
	 * @return bool true if it returns a reference, otherwise false
	 */
	public function returnsReference () {}

	/**
	 * Checks if the function has a specified return type
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.hasreturntype.php
	 * @return bool true if the function is a specified return type, otherwise false.
	 */
	public function hasReturnType () {}

	/**
	 * Gets the specified return type of a function
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getreturntype.php
	 * @return mixed a ReflectionType object if a return type is
	 * specified, null otherwise.
	 */
	public function getReturnType () {}

	/**
	 * Returns whether the function has a tentative return type
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.hastentativereturntype.php
	 * @return bool true if the function has a tentative return type, otherwise false.
	 */
	public function hasTentativeReturnType (): bool {}

	/**
	 * Returns the tentative return type associated with the function
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.gettentativereturntype.php
	 * @return mixed a ReflectionType object if a tentative return type is
	 * specified, null otherwise.
	 */
	public function getTentativeReturnType (): ??ReflectionType {}

	/**
	 * Gets Attributes
	 * @link http://www.php.net/manual/en/reflectionfunctionabstract.getattributes.php
	 * @param mixed $name [optional] 
	 * @param int $flags [optional] 
	 * @return array Array of attributes, as a ReflectionAttribute object.
	 */
	public function getAttributes ($name = null, int $flags = null): array {}

}

/**
 * The ReflectionClass class reports
 * information about a class.
 * @link http://www.php.net/manual/en/class.reflectionclass.php
 */
class ReflectionClass implements Stringable, Reflector {
	const IS_IMPLICIT_ABSTRACT = 16;
	const IS_EXPLICIT_ABSTRACT = 64;
	const IS_FINAL = 32;

	public $name;


	private function __clone (): void {}

	/**
	 * Constructs a ReflectionClass
	 * @link http://www.php.net/manual/en/reflectionclass.construct.php
	 * @param object|string $objectOrClass
	 */
	public function __construct (object|string $objectOrClass) {}

	/**
	 * Returns the string representation of the ReflectionClass object
	 * @link http://www.php.net/manual/en/reflectionclass.tostring.php
	 * @return string A string representation of this ReflectionClass instance.
	 */
	public function __toString (): string {}

	/**
	 * Gets class name
	 * @link http://www.php.net/manual/en/reflectionclass.getname.php
	 * @return string The class name.
	 */
	public function getName () {}

	/**
	 * Checks if class is defined internally by an extension, or the core
	 * @link http://www.php.net/manual/en/reflectionclass.isinternal.php
	 * @return bool true on success or false on failure
	 */
	public function isInternal () {}

	/**
	 * Checks if user defined
	 * @link http://www.php.net/manual/en/reflectionclass.isuserdefined.php
	 * @return bool true on success or false on failure
	 */
	public function isUserDefined () {}

	/**
	 * Checks if class is anonymous
	 * @link http://www.php.net/manual/en/reflectionclass.isanonymous.php
	 * @return bool true on success or false on failure
	 */
	public function isAnonymous () {}

	/**
	 * Checks if the class is instantiable
	 * @link http://www.php.net/manual/en/reflectionclass.isinstantiable.php
	 * @return bool true on success or false on failure
	 */
	public function isInstantiable () {}

	/**
	 * Returns whether this class is cloneable
	 * @link http://www.php.net/manual/en/reflectionclass.iscloneable.php
	 * @return bool true if the class is cloneable, false otherwise.
	 */
	public function isCloneable () {}

	/**
	 * Gets the filename of the file in which the class has been defined
	 * @link http://www.php.net/manual/en/reflectionclass.getfilename.php
	 * @return mixed the filename of the file in which the class has been defined.
	 * If the class is defined in the PHP core or in a PHP extension, false
	 * is returned.
	 */
	public function getFileName () {}

	/**
	 * Gets starting line number
	 * @link http://www.php.net/manual/en/reflectionclass.getstartline.php
	 * @return mixed The starting line number, as an int, or false if unknown.
	 */
	public function getStartLine () {}

	/**
	 * Gets end line
	 * @link http://www.php.net/manual/en/reflectionclass.getendline.php
	 * @return mixed The ending line number of the user defined class, or false if unknown.
	 */
	public function getEndLine () {}

	/**
	 * Gets doc comments
	 * @link http://www.php.net/manual/en/reflectionclass.getdoccomment.php
	 * @return mixed The doc comment if it exists, otherwise false.
	 */
	public function getDocComment () {}

	/**
	 * Gets the constructor of the class
	 * @link http://www.php.net/manual/en/reflectionclass.getconstructor.php
	 * @return mixed A ReflectionMethod object reflecting the class' constructor, or null if the class
	 * has no constructor.
	 */
	public function getConstructor () {}

	/**
	 * Checks if method is defined
	 * @link http://www.php.net/manual/en/reflectionclass.hasmethod.php
	 * @param string $name Name of the method being checked for.
	 * @return bool true if it has the method, otherwise false
	 */
	public function hasMethod (string $name) {}

	/**
	 * Gets a ReflectionMethod for a class method
	 * @link http://www.php.net/manual/en/reflectionclass.getmethod.php
	 * @param string $name The method name to reflect.
	 * @return ReflectionMethod A ReflectionMethod.
	 */
	public function getMethod (string $name) {}

	/**
	 * Gets an array of methods
	 * @link http://www.php.net/manual/en/reflectionclass.getmethods.php
	 * @param mixed $filter [optional] <p>
	 * Filter the results to include only methods with certain attributes. Defaults
	 * to no filtering.
	 * </p>
	 * <p>
	 * Any bitwise disjunction of ReflectionMethod::IS_STATIC,
	 * ReflectionMethod::IS_PUBLIC,
	 * ReflectionMethod::IS_PROTECTED,
	 * ReflectionMethod::IS_PRIVATE,
	 * ReflectionMethod::IS_ABSTRACT,
	 * ReflectionMethod::IS_FINAL,
	 * so that all methods with any of the given
	 * attributes will be returned.
	 * </p>
	 * Note that other bitwise operations, for instance ~
	 * will not work as expected. In other words, it is not possible to
	 * retrieve all non-static methods, for example.
	 * @return array An array of ReflectionMethod objects
	 * reflecting each method.
	 */
	public function getMethods ($filter = null) {}

	/**
	 * Checks if property is defined
	 * @link http://www.php.net/manual/en/reflectionclass.hasproperty.php
	 * @param string $name Name of the property being checked for.
	 * @return bool true if it has the property, otherwise false
	 */
	public function hasProperty (string $name) {}

	/**
	 * Gets a ReflectionProperty for a class's property
	 * @link http://www.php.net/manual/en/reflectionclass.getproperty.php
	 * @param string $name The property name.
	 * @return ReflectionProperty A ReflectionProperty.
	 */
	public function getProperty (string $name) {}

	/**
	 * Gets properties
	 * @link http://www.php.net/manual/en/reflectionclass.getproperties.php
	 * @param mixed $filter [optional] The optional filter, for filtering desired property types. It's configured using
	 * the ReflectionProperty constants,
	 * and defaults to all property types.
	 * @return array An array of ReflectionProperty objects.
	 */
	public function getProperties ($filter = null) {}

	/**
	 * Checks if constant is defined
	 * @link http://www.php.net/manual/en/reflectionclass.hasconstant.php
	 * @param string $name The name of the constant being checked for.
	 * @return bool true if the constant is defined, otherwise false.
	 */
	public function hasConstant (string $name) {}

	/**
	 * Gets constants
	 * @link http://www.php.net/manual/en/reflectionclass.getconstants.php
	 * @param mixed $filter [optional] The optional filter, for filtering desired constant visibilities. It's configured using
	 * the ReflectionClassConstant constants,
	 * and defaults to all constant visibilities.
	 * @return array An array of constants, where the keys hold the name
	 * and the values the value of the constants.
	 */
	public function getConstants ($filter = null) {}

	/**
	 * Gets class constants
	 * @link http://www.php.net/manual/en/reflectionclass.getreflectionconstants.php
	 * @param mixed $filter [optional] The optional filter, for filtering desired constant visibilities. It's configured using
	 * the ReflectionClassConstant constants,
	 * and defaults to all constant visibilities.
	 * @return array An array of ReflectionClassConstant objects.
	 */
	public function getReflectionConstants ($filter = null) {}

	/**
	 * Gets defined constant
	 * @link http://www.php.net/manual/en/reflectionclass.getconstant.php
	 * @param string $name The name of the class constant to get.
	 * @return mixed Value of the constant with the name name.
	 * Returns false if the constant was not found in the class.
	 */
	public function getConstant (string $name) {}

	/**
	 * Gets a ReflectionClassConstant for a class's constant
	 * @link http://www.php.net/manual/en/reflectionclass.getreflectionconstant.php
	 * @param string $name The class constant name.
	 * @return mixed A ReflectionClassConstant, or false on failure.
	 */
	public function getReflectionConstant (string $name) {}

	/**
	 * Gets the interfaces
	 * @link http://www.php.net/manual/en/reflectionclass.getinterfaces.php
	 * @return array An associative array of interfaces, with keys as interface
	 * names and the array values as ReflectionClass objects.
	 */
	public function getInterfaces () {}

	/**
	 * Gets the interface names
	 * @link http://www.php.net/manual/en/reflectionclass.getinterfacenames.php
	 * @return array A numerical array with interface names as the values.
	 */
	public function getInterfaceNames () {}

	/**
	 * Checks if the class is an interface
	 * @link http://www.php.net/manual/en/reflectionclass.isinterface.php
	 * @return bool true on success or false on failure
	 */
	public function isInterface () {}

	/**
	 * Returns an array of traits used by this class
	 * @link http://www.php.net/manual/en/reflectionclass.gettraits.php
	 * @return array an array with trait names in keys and instances of trait's
	 * ReflectionClass in values.
	 * Returns null in case of an error.
	 */
	public function getTraits () {}

	/**
	 * Returns an array of names of traits used by this class
	 * @link http://www.php.net/manual/en/reflectionclass.gettraitnames.php
	 * @return array an array with trait names in values.
	 */
	public function getTraitNames () {}

	/**
	 * Returns an array of trait aliases
	 * @link http://www.php.net/manual/en/reflectionclass.gettraitaliases.php
	 * @return array an array with new method names in keys and original names (in the
	 * format "TraitName::original") in values.
	 */
	public function getTraitAliases () {}

	/**
	 * Returns whether this is a trait
	 * @link http://www.php.net/manual/en/reflectionclass.istrait.php
	 * @return bool true if this is a trait, false otherwise.
	 * Returns null in case of an error.
	 */
	public function isTrait () {}

	/**
	 * Returns whether this is an enum
	 * @link http://www.php.net/manual/en/reflectionclass.isenum.php
	 * @return bool true if this is an enum, false otherwise.
	 */
	public function isEnum (): bool {}

	/**
	 * Checks if class is abstract
	 * @link http://www.php.net/manual/en/reflectionclass.isabstract.php
	 * @return bool true on success or false on failure
	 */
	public function isAbstract () {}

	/**
	 * Checks if class is final
	 * @link http://www.php.net/manual/en/reflectionclass.isfinal.php
	 * @return bool true on success or false on failure
	 */
	public function isFinal () {}

	/**
	 * Gets the class modifiers
	 * @link http://www.php.net/manual/en/reflectionclass.getmodifiers.php
	 * @return int bitmask of 
	 * modifier constants.
	 */
	public function getModifiers () {}

	/**
	 * Checks class for instance
	 * @link http://www.php.net/manual/en/reflectionclass.isinstance.php
	 * @param object $object The object being compared to.
	 * @return bool true on success or false on failure
	 */
	public function isInstance ($object) {}

	/**
	 * Creates a new class instance from given arguments
	 * @link http://www.php.net/manual/en/reflectionclass.newinstance.php
	 * @param mixed $args Accepts a variable number of arguments which are passed to the class
	 * constructor, much like call_user_func.
	 * @return object 
	 */
	public function newInstance ($args) {}

	/**
	 * Creates a new class instance without invoking the constructor
	 * @link http://www.php.net/manual/en/reflectionclass.newinstancewithoutconstructor.php
	 * @return object 
	 */
	public function newInstanceWithoutConstructor () {}

	/**
	 * Creates a new class instance from given arguments
	 * @link http://www.php.net/manual/en/reflectionclass.newinstanceargs.php
	 * @param array $args [optional] The parameters to be passed to the class constructor as an array.
	 * @return mixed a new instance of the class, or null on failure.
	 */
	public function newInstanceArgs (array $args = null) {}

	/**
	 * Gets parent class
	 * @link http://www.php.net/manual/en/reflectionclass.getparentclass.php
	 * @return mixed A ReflectionClass or false if there's no parent.
	 */
	public function getParentClass () {}

	/**
	 * Checks if a subclass
	 * @link http://www.php.net/manual/en/reflectionclass.issubclassof.php
	 * @param mixed $class Either the name of the class as string or
	 * a ReflectionClass object of the class to check against.
	 * @return bool true on success or false on failure
	 */
	public function isSubclassOf ($class) {}

	/**
	 * Gets static properties
	 * @link http://www.php.net/manual/en/reflectionclass.getstaticproperties.php
	 * @return mixed The static properties, as an array, or null on failure.
	 */
	public function getStaticProperties () {}

	/**
	 * Gets static property value
	 * @link http://www.php.net/manual/en/reflectionclass.getstaticpropertyvalue.php
	 * @param string $name The name of the static property for which to return a value.
	 * @param mixed $def_value [optional] A default value to return in case the class does not declare a static
	 * property with the given name. If the property does
	 * not exist and this argument is omitted, a
	 * ReflectionException is thrown.
	 * @return mixed The value of the static property.
	 */
	public function getStaticPropertyValue (string $name, &$def_value = null) {}

	/**
	 * Sets static property value
	 * @link http://www.php.net/manual/en/reflectionclass.setstaticpropertyvalue.php
	 * @param string $name Property name.
	 * @param mixed $value New property value.
	 * @return void 
	 */
	public function setStaticPropertyValue (string $name, $value) {}

	/**
	 * Gets default properties
	 * @link http://www.php.net/manual/en/reflectionclass.getdefaultproperties.php
	 * @return array An array of default properties, with the key being the name of
	 * the property and the value being the default value of the property or null
	 * if the property doesn't have a default value. The function does not distinguish
	 * between static and non static properties and does not take visibility modifiers
	 * into account.
	 */
	public function getDefaultProperties () {}

	/**
	 * Check whether this class is iterable
	 * @link http://www.php.net/manual/en/reflectionclass.isiterable.php
	 * @return bool true on success or false on failure
	 */
	public function isIterable () {}

	/**
	 * Alias: ReflectionClass::isIterable
	 * @link http://www.php.net/manual/en/reflectionclass.isiterateable.php
	 */
	public function isIterateable () {}

	/**
	 * Implements interface
	 * @link http://www.php.net/manual/en/reflectionclass.implementsinterface.php
	 * @param mixed $interface The interface name.
	 * @return bool true on success or false on failure
	 */
	public function implementsInterface ($interface) {}

	/**
	 * Gets a ReflectionExtension object for the extension which defined the class
	 * @link http://www.php.net/manual/en/reflectionclass.getextension.php
	 * @return mixed A ReflectionExtension object representing the extension which defined the class,
	 * or null for user-defined classes.
	 */
	public function getExtension () {}

	/**
	 * Gets the name of the extension which defined the class
	 * @link http://www.php.net/manual/en/reflectionclass.getextensionname.php
	 * @return mixed The name of the extension which defined the class, or false for user-defined classes.
	 */
	public function getExtensionName () {}

	/**
	 * Checks if in namespace
	 * @link http://www.php.net/manual/en/reflectionclass.innamespace.php
	 * @return bool true on success or false on failure
	 */
	public function inNamespace () {}

	/**
	 * Gets namespace name
	 * @link http://www.php.net/manual/en/reflectionclass.getnamespacename.php
	 * @return string The namespace name.
	 */
	public function getNamespaceName () {}

	/**
	 * Gets short name
	 * @link http://www.php.net/manual/en/reflectionclass.getshortname.php
	 * @return string The class short name.
	 */
	public function getShortName () {}

	/**
	 * Gets Attributes
	 * @link http://www.php.net/manual/en/reflectionclass.getattributes.php
	 * @param mixed $name [optional] 
	 * @param int $flags [optional] 
	 * @return array Array of attributes, as a ReflectionAttribute object.
	 */
	public function getAttributes ($name = null, int $flags = null): array {}

}

/**
 * The ReflectionObject class reports
 * information about an object.
 * @link http://www.php.net/manual/en/class.reflectionobject.php
 */
class ReflectionObject extends ReflectionClass implements Reflector, Stringable {
	const IS_IMPLICIT_ABSTRACT = 16;
	const IS_EXPLICIT_ABSTRACT = 64;
	const IS_FINAL = 32;

	public $name;


	/**
	 * Constructs a ReflectionObject
	 * @link http://www.php.net/manual/en/reflectionobject.construct.php
	 * @param object $object
	 */
	public function __construct (object $object) {}

	/**
	 * Returns the string representation of the ReflectionClass object
	 * @link http://www.php.net/manual/en/reflectionclass.tostring.php
	 * @return string A string representation of this ReflectionClass instance.
	 */
	public function __toString (): string {}

	/**
	 * Gets class name
	 * @link http://www.php.net/manual/en/reflectionclass.getname.php
	 * @return string The class name.
	 */
	public function getName () {}

	/**
	 * Checks if class is defined internally by an extension, or the core
	 * @link http://www.php.net/manual/en/reflectionclass.isinternal.php
	 * @return bool true on success or false on failure
	 */
	public function isInternal () {}

	/**
	 * Checks if user defined
	 * @link http://www.php.net/manual/en/reflectionclass.isuserdefined.php
	 * @return bool true on success or false on failure
	 */
	public function isUserDefined () {}

	/**
	 * Checks if class is anonymous
	 * @link http://www.php.net/manual/en/reflectionclass.isanonymous.php
	 * @return bool true on success or false on failure
	 */
	public function isAnonymous () {}

	/**
	 * Checks if the class is instantiable
	 * @link http://www.php.net/manual/en/reflectionclass.isinstantiable.php
	 * @return bool true on success or false on failure
	 */
	public function isInstantiable () {}

	/**
	 * Returns whether this class is cloneable
	 * @link http://www.php.net/manual/en/reflectionclass.iscloneable.php
	 * @return bool true if the class is cloneable, false otherwise.
	 */
	public function isCloneable () {}

	/**
	 * Gets the filename of the file in which the class has been defined
	 * @link http://www.php.net/manual/en/reflectionclass.getfilename.php
	 * @return mixed the filename of the file in which the class has been defined.
	 * If the class is defined in the PHP core or in a PHP extension, false
	 * is returned.
	 */
	public function getFileName () {}

	/**
	 * Gets starting line number
	 * @link http://www.php.net/manual/en/reflectionclass.getstartline.php
	 * @return mixed The starting line number, as an int, or false if unknown.
	 */
	public function getStartLine () {}

	/**
	 * Gets end line
	 * @link http://www.php.net/manual/en/reflectionclass.getendline.php
	 * @return mixed The ending line number of the user defined class, or false if unknown.
	 */
	public function getEndLine () {}

	/**
	 * Gets doc comments
	 * @link http://www.php.net/manual/en/reflectionclass.getdoccomment.php
	 * @return mixed The doc comment if it exists, otherwise false.
	 */
	public function getDocComment () {}

	/**
	 * Gets the constructor of the class
	 * @link http://www.php.net/manual/en/reflectionclass.getconstructor.php
	 * @return mixed A ReflectionMethod object reflecting the class' constructor, or null if the class
	 * has no constructor.
	 */
	public function getConstructor () {}

	/**
	 * Checks if method is defined
	 * @link http://www.php.net/manual/en/reflectionclass.hasmethod.php
	 * @param string $name Name of the method being checked for.
	 * @return bool true if it has the method, otherwise false
	 */
	public function hasMethod (string $name) {}

	/**
	 * Gets a ReflectionMethod for a class method
	 * @link http://www.php.net/manual/en/reflectionclass.getmethod.php
	 * @param string $name The method name to reflect.
	 * @return ReflectionMethod A ReflectionMethod.
	 */
	public function getMethod (string $name) {}

	/**
	 * Gets an array of methods
	 * @link http://www.php.net/manual/en/reflectionclass.getmethods.php
	 * @param mixed $filter [optional] <p>
	 * Filter the results to include only methods with certain attributes. Defaults
	 * to no filtering.
	 * </p>
	 * <p>
	 * Any bitwise disjunction of ReflectionMethod::IS_STATIC,
	 * ReflectionMethod::IS_PUBLIC,
	 * ReflectionMethod::IS_PROTECTED,
	 * ReflectionMethod::IS_PRIVATE,
	 * ReflectionMethod::IS_ABSTRACT,
	 * ReflectionMethod::IS_FINAL,
	 * so that all methods with any of the given
	 * attributes will be returned.
	 * </p>
	 * Note that other bitwise operations, for instance ~
	 * will not work as expected. In other words, it is not possible to
	 * retrieve all non-static methods, for example.
	 * @return array An array of ReflectionMethod objects
	 * reflecting each method.
	 */
	public function getMethods ($filter = null) {}

	/**
	 * Checks if property is defined
	 * @link http://www.php.net/manual/en/reflectionclass.hasproperty.php
	 * @param string $name Name of the property being checked for.
	 * @return bool true if it has the property, otherwise false
	 */
	public function hasProperty (string $name) {}

	/**
	 * Gets a ReflectionProperty for a class's property
	 * @link http://www.php.net/manual/en/reflectionclass.getproperty.php
	 * @param string $name The property name.
	 * @return ReflectionProperty A ReflectionProperty.
	 */
	public function getProperty (string $name) {}

	/**
	 * Gets properties
	 * @link http://www.php.net/manual/en/reflectionclass.getproperties.php
	 * @param mixed $filter [optional] The optional filter, for filtering desired property types. It's configured using
	 * the ReflectionProperty constants,
	 * and defaults to all property types.
	 * @return array An array of ReflectionProperty objects.
	 */
	public function getProperties ($filter = null) {}

	/**
	 * Checks if constant is defined
	 * @link http://www.php.net/manual/en/reflectionclass.hasconstant.php
	 * @param string $name The name of the constant being checked for.
	 * @return bool true if the constant is defined, otherwise false.
	 */
	public function hasConstant (string $name) {}

	/**
	 * Gets constants
	 * @link http://www.php.net/manual/en/reflectionclass.getconstants.php
	 * @param mixed $filter [optional] The optional filter, for filtering desired constant visibilities. It's configured using
	 * the ReflectionClassConstant constants,
	 * and defaults to all constant visibilities.
	 * @return array An array of constants, where the keys hold the name
	 * and the values the value of the constants.
	 */
	public function getConstants ($filter = null) {}

	/**
	 * Gets class constants
	 * @link http://www.php.net/manual/en/reflectionclass.getreflectionconstants.php
	 * @param mixed $filter [optional] The optional filter, for filtering desired constant visibilities. It's configured using
	 * the ReflectionClassConstant constants,
	 * and defaults to all constant visibilities.
	 * @return array An array of ReflectionClassConstant objects.
	 */
	public function getReflectionConstants ($filter = null) {}

	/**
	 * Gets defined constant
	 * @link http://www.php.net/manual/en/reflectionclass.getconstant.php
	 * @param string $name The name of the class constant to get.
	 * @return mixed Value of the constant with the name name.
	 * Returns false if the constant was not found in the class.
	 */
	public function getConstant (string $name) {}

	/**
	 * Gets a ReflectionClassConstant for a class's constant
	 * @link http://www.php.net/manual/en/reflectionclass.getreflectionconstant.php
	 * @param string $name The class constant name.
	 * @return mixed A ReflectionClassConstant, or false on failure.
	 */
	public function getReflectionConstant (string $name) {}

	/**
	 * Gets the interfaces
	 * @link http://www.php.net/manual/en/reflectionclass.getinterfaces.php
	 * @return array An associative array of interfaces, with keys as interface
	 * names and the array values as ReflectionClass objects.
	 */
	public function getInterfaces () {}

	/**
	 * Gets the interface names
	 * @link http://www.php.net/manual/en/reflectionclass.getinterfacenames.php
	 * @return array A numerical array with interface names as the values.
	 */
	public function getInterfaceNames () {}

	/**
	 * Checks if the class is an interface
	 * @link http://www.php.net/manual/en/reflectionclass.isinterface.php
	 * @return bool true on success or false on failure
	 */
	public function isInterface () {}

	/**
	 * Returns an array of traits used by this class
	 * @link http://www.php.net/manual/en/reflectionclass.gettraits.php
	 * @return array an array with trait names in keys and instances of trait's
	 * ReflectionClass in values.
	 * Returns null in case of an error.
	 */
	public function getTraits () {}

	/**
	 * Returns an array of names of traits used by this class
	 * @link http://www.php.net/manual/en/reflectionclass.gettraitnames.php
	 * @return array an array with trait names in values.
	 */
	public function getTraitNames () {}

	/**
	 * Returns an array of trait aliases
	 * @link http://www.php.net/manual/en/reflectionclass.gettraitaliases.php
	 * @return array an array with new method names in keys and original names (in the
	 * format "TraitName::original") in values.
	 */
	public function getTraitAliases () {}

	/**
	 * Returns whether this is a trait
	 * @link http://www.php.net/manual/en/reflectionclass.istrait.php
	 * @return bool true if this is a trait, false otherwise.
	 * Returns null in case of an error.
	 */
	public function isTrait () {}

	/**
	 * Returns whether this is an enum
	 * @link http://www.php.net/manual/en/reflectionclass.isenum.php
	 * @return bool true if this is an enum, false otherwise.
	 */
	public function isEnum (): bool {}

	/**
	 * Checks if class is abstract
	 * @link http://www.php.net/manual/en/reflectionclass.isabstract.php
	 * @return bool true on success or false on failure
	 */
	public function isAbstract () {}

	/**
	 * Checks if class is final
	 * @link http://www.php.net/manual/en/reflectionclass.isfinal.php
	 * @return bool true on success or false on failure
	 */
	public function isFinal () {}

	/**
	 * Gets the class modifiers
	 * @link http://www.php.net/manual/en/reflectionclass.getmodifiers.php
	 * @return int bitmask of 
	 * modifier constants.
	 */
	public function getModifiers () {}

	/**
	 * Checks class for instance
	 * @link http://www.php.net/manual/en/reflectionclass.isinstance.php
	 * @param object $object The object being compared to.
	 * @return bool true on success or false on failure
	 */
	public function isInstance ($object) {}

	/**
	 * Creates a new class instance from given arguments
	 * @link http://www.php.net/manual/en/reflectionclass.newinstance.php
	 * @param mixed $args Accepts a variable number of arguments which are passed to the class
	 * constructor, much like call_user_func.
	 * @return object 
	 */
	public function newInstance ($args) {}

	/**
	 * Creates a new class instance without invoking the constructor
	 * @link http://www.php.net/manual/en/reflectionclass.newinstancewithoutconstructor.php
	 * @return object 
	 */
	public function newInstanceWithoutConstructor () {}

	/**
	 * Creates a new class instance from given arguments
	 * @link http://www.php.net/manual/en/reflectionclass.newinstanceargs.php
	 * @param array $args [optional] The parameters to be passed to the class constructor as an array.
	 * @return mixed a new instance of the class, or null on failure.
	 */
	public function newInstanceArgs (array $args = null) {}

	/**
	 * Gets parent class
	 * @link http://www.php.net/manual/en/reflectionclass.getparentclass.php
	 * @return mixed A ReflectionClass or false if there's no parent.
	 */
	public function getParentClass () {}

	/**
	 * Checks if a subclass
	 * @link http://www.php.net/manual/en/reflectionclass.issubclassof.php
	 * @param mixed $class Either the name of the class as string or
	 * a ReflectionClass object of the class to check against.
	 * @return bool true on success or false on failure
	 */
	public function isSubclassOf ($class) {}

	/**
	 * Gets static properties
	 * @link http://www.php.net/manual/en/reflectionclass.getstaticproperties.php
	 * @return mixed The static properties, as an array, or null on failure.
	 */
	public function getStaticProperties () {}

	/**
	 * Gets static property value
	 * @link http://www.php.net/manual/en/reflectionclass.getstaticpropertyvalue.php
	 * @param string $name The name of the static property for which to return a value.
	 * @param mixed $def_value [optional] A default value to return in case the class does not declare a static
	 * property with the given name. If the property does
	 * not exist and this argument is omitted, a
	 * ReflectionException is thrown.
	 * @return mixed The value of the static property.
	 */
	public function getStaticPropertyValue (string $name, &$def_value = null) {}

	/**
	 * Sets static property value
	 * @link http://www.php.net/manual/en/reflectionclass.setstaticpropertyvalue.php
	 * @param string $name Property name.
	 * @param mixed $value New property value.
	 * @return void 
	 */
	public function setStaticPropertyValue (string $name, $value) {}

	/**
	 * Gets default properties
	 * @link http://www.php.net/manual/en/reflectionclass.getdefaultproperties.php
	 * @return array An array of default properties, with the key being the name of
	 * the property and the value being the default value of the property or null
	 * if the property doesn't have a default value. The function does not distinguish
	 * between static and non static properties and does not take visibility modifiers
	 * into account.
	 */
	public function getDefaultProperties () {}

	/**
	 * Check whether this class is iterable
	 * @link http://www.php.net/manual/en/reflectionclass.isiterable.php
	 * @return bool true on success or false on failure
	 */
	public function isIterable () {}

	/**
	 * Alias: ReflectionClass::isIterable
	 * @link http://www.php.net/manual/en/reflectionclass.isiterateable.php
	 */
	public function isIterateable () {}

	/**
	 * Implements interface
	 * @link http://www.php.net/manual/en/reflectionclass.implementsinterface.php
	 * @param mixed $interface The interface name.
	 * @return bool true on success or false on failure
	 */
	public function implementsInterface ($interface) {}

	/**
	 * Gets a ReflectionExtension object for the extension which defined the class
	 * @link http://www.php.net/manual/en/reflectionclass.getextension.php
	 * @return mixed A ReflectionExtension object representing the extension which defined the class,
	 * or null for user-defined classes.
	 */
	public function getExtension () {}

	/**
	 * Gets the name of the extension which defined the class
	 * @link http://www.php.net/manual/en/reflectionclass.getextensionname.php
	 * @return mixed The name of the extension which defined the class, or false for user-defined classes.
	 */
	public function getExtensionName () {}

	/**
	 * Checks if in namespace
	 * @link http://www.php.net/manual/en/reflectionclass.innamespace.php
	 * @return bool true on success or false on failure
	 */
	public function inNamespace () {}

	/**
	 * Gets namespace name
	 * @link http://www.php.net/manual/en/reflectionclass.getnamespacename.php
	 * @return string The namespace name.
	 */
	public function getNamespaceName () {}

	/**
	 * Gets short name
	 * @link http://www.php.net/manual/en/reflectionclass.getshortname.php
	 * @return string The class short name.
	 */
	public function getShortName () {}

	/**
	 * Gets Attributes
	 * @link http://www.php.net/manual/en/reflectionclass.getattributes.php
	 * @param mixed $name [optional] 
	 * @param int $flags [optional] 
	 * @return array Array of attributes, as a ReflectionAttribute object.
	 */
	public function getAttributes ($name = null, int $flags = null): array {}

}

/**
 * The ReflectionProperty class reports
 * information about class properties.
 * @link http://www.php.net/manual/en/class.reflectionproperty.php
 */
class ReflectionProperty implements Stringable, Reflector {
	const IS_STATIC = 16;
	const IS_READONLY = 128;
	const IS_PUBLIC = 1;
	const IS_PROTECTED = 2;
	const IS_PRIVATE = 4;

	public $name;
	public $class;


	/**
	 * Clone
	 * @link http://www.php.net/manual/en/reflectionproperty.clone.php
	 * @return void 
	 */
	private function __clone (): void {}

	/**
	 * Construct a ReflectionProperty object
	 * @link http://www.php.net/manual/en/reflectionproperty.construct.php
	 * @param object|string $class
	 * @param string $property
	 */
	public function __construct (object|string $classstring , $property) {}

	/**
	 * To string
	 * @link http://www.php.net/manual/en/reflectionproperty.tostring.php
	 * @return string 
	 */
	public function __toString (): string {}

	/**
	 * Gets property name
	 * @link http://www.php.net/manual/en/reflectionproperty.getname.php
	 * @return string The name of the reflected property.
	 */
	public function getName () {}

	/**
	 * Gets value
	 * @link http://www.php.net/manual/en/reflectionproperty.getvalue.php
	 * @param mixed $object [optional] If the property is non-static an object must be provided to fetch the
	 * property from. If you want to fetch the default property without
	 * providing an object use ReflectionClass::getDefaultProperties
	 * instead.
	 * @return mixed The current value of the property.
	 */
	public function getValue ($object = null) {}

	/**
	 * Set property value
	 * @link http://www.php.net/manual/en/reflectionproperty.setvalue.php
	 * @param object $object If the property is non-static an object must be provided to change
	 * the property on. If the property is static this parameter is left
	 * out and only value needs to be provided.
	 * @param mixed $value The new value.
	 * @return void 
	 */
	public function setValue ($object, $value) {}

	/**
	 * Checks whether a property is initialized
	 * @link http://www.php.net/manual/en/reflectionproperty.isinitialized.php
	 * @param mixed $object [optional] If the property is non-static an object must be provided to fetch the
	 * property from.
	 * @return bool false for typed properties prior to initialization,
	 * and for properties that have been explicitly unset.
	 * For all other properties true will be returned.
	 */
	public function isInitialized ($object = null) {}

	/**
	 * Checks if property is public
	 * @link http://www.php.net/manual/en/reflectionproperty.ispublic.php
	 * @return bool true if the property is public, false otherwise.
	 */
	public function isPublic () {}

	/**
	 * Checks if property is private
	 * @link http://www.php.net/manual/en/reflectionproperty.isprivate.php
	 * @return bool true if the property is private, false otherwise.
	 */
	public function isPrivate () {}

	/**
	 * Checks if property is protected
	 * @link http://www.php.net/manual/en/reflectionproperty.isprotected.php
	 * @return bool true if the property is protected, false otherwise.
	 */
	public function isProtected () {}

	/**
	 * Checks if property is static
	 * @link http://www.php.net/manual/en/reflectionproperty.isstatic.php
	 * @return bool true if the property is static, false otherwise.
	 */
	public function isStatic () {}

	/**
	 * Checks if property is readonly
	 * @link http://www.php.net/manual/en/reflectionproperty.isreadonly.php
	 * @return bool true if the property is readonly, false otherwise.
	 */
	public function isReadOnly (): bool {}

	/**
	 * Checks if property is a default property
	 * @link http://www.php.net/manual/en/reflectionproperty.isdefault.php
	 * @return bool true if the property was declared at compile-time, or false if
	 * it was created at run-time.
	 */
	public function isDefault () {}

	/**
	 * Checks if property is promoted
	 * @link http://www.php.net/manual/en/reflectionproperty.ispromoted.php
	 * @return bool true if the property is promoted, false otherwise.
	 */
	public function isPromoted (): bool {}

	/**
	 * Gets the property modifiers
	 * @link http://www.php.net/manual/en/reflectionproperty.getmodifiers.php
	 * @return int A numeric representation of the modifiers.
	 * The actual meaning of these modifiers are described under
	 * predefined constants.
	 */
	public function getModifiers () {}

	/**
	 * Gets declaring class
	 * @link http://www.php.net/manual/en/reflectionproperty.getdeclaringclass.php
	 * @return ReflectionClass A ReflectionClass object.
	 */
	public function getDeclaringClass () {}

	/**
	 * Gets the property doc comment
	 * @link http://www.php.net/manual/en/reflectionproperty.getdoccomment.php
	 * @return mixed The doc comment if it exists, otherwise false.
	 */
	public function getDocComment () {}

	/**
	 * Set property accessibility
	 * @link http://www.php.net/manual/en/reflectionproperty.setaccessible.php
	 * @param bool $accessible true to allow accessibility, or false.
	 * @return void 
	 */
	public function setAccessible (bool $accessible) {}

	/**
	 * Gets a property's type
	 * @link http://www.php.net/manual/en/reflectionproperty.gettype.php
	 * @return mixed a ReflectionType if the property has a type,
	 * and null otherwise.
	 */
	public function getType () {}

	/**
	 * Checks if property has a type
	 * @link http://www.php.net/manual/en/reflectionproperty.hastype.php
	 * @return bool true if a type is specified, false otherwise.
	 */
	public function hasType () {}

	/**
	 * Checks if property has a default value declared
	 * @link http://www.php.net/manual/en/reflectionproperty.hasdefaultvalue.php
	 * @return bool If the property has any default value (including null) true is returned;
	 * if the property is typed without a default value declared or is a dynamic property, false is returned.
	 */
	public function hasDefaultValue (): bool {}

	/**
	 * Returns the default value declared for a property
	 * @link http://www.php.net/manual/en/reflectionproperty.getdefaultvalue.php
	 * @return mixed The default value if the property has any default value (including null).
	 * If there is no default value, then null is returned. It is not possible to differentiate
	 * between a null default value and an unitialized typed property.
	 * Use ReflectionProperty::hasDefaultValue to detect the difference.
	 */
	public function getDefaultValue () {}

	/**
	 * Gets Attributes
	 * @link http://www.php.net/manual/en/reflectionproperty.getattributes.php
	 * @param mixed $name [optional] 
	 * @param int $flags [optional] 
	 * @return array Array of attributes, as a ReflectionAttribute object.
	 */
	public function getAttributes ($name = null, int $flags = null): array {}

}

/**
 * The ReflectionClassConstant class reports
 * information about a class constant.
 * @link http://www.php.net/manual/en/class.reflectionclassconstant.php
 */
class ReflectionClassConstant implements Stringable, Reflector {
	const IS_PUBLIC = 1;
	const IS_PROTECTED = 2;
	const IS_PRIVATE = 4;
	const IS_FINAL = 32;

	public $name;
	public $class;


	private function __clone (): void {}

	/**
	 * Constructs a ReflectionClassConstant
	 * @link http://www.php.net/manual/en/reflectionclassconstant.construct.php
	 * @param object|string $class
	 * @param string $constant
	 */
	public function __construct (object|string $classstring , $constant) {}

	/**
	 * Returns the string representation of the ReflectionClassConstant object
	 * @link http://www.php.net/manual/en/reflectionclassconstant.tostring.php
	 * @return string A string representation of this ReflectionClassConstant instance.
	 */
	public function __toString (): string {}

	/**
	 * Get name of the constant
	 * @link http://www.php.net/manual/en/reflectionclassconstant.getname.php
	 * @return string the constant's name.
	 */
	public function getName () {}

	/**
	 * Gets value
	 * @link http://www.php.net/manual/en/reflectionclassconstant.getvalue.php
	 * @return mixed The value of the class constant.
	 */
	public function getValue () {}

	/**
	 * Checks if class constant is public
	 * @link http://www.php.net/manual/en/reflectionclassconstant.ispublic.php
	 * @return bool true if the class constant is public, otherwise false
	 */
	public function isPublic () {}

	/**
	 * Checks if class constant is private
	 * @link http://www.php.net/manual/en/reflectionclassconstant.isprivate.php
	 * @return bool true if the class constant is private, otherwise false
	 */
	public function isPrivate () {}

	/**
	 * Checks if class constant is protected
	 * @link http://www.php.net/manual/en/reflectionclassconstant.isprotected.php
	 * @return bool true if the class constant is protected, otherwise false
	 */
	public function isProtected () {}

	/**
	 * Checks if class constant is final
	 * @link http://www.php.net/manual/en/reflectionclassconstant.isfinal.php
	 * @return bool true if the class constant is final, otherwise false
	 */
	public function isFinal (): bool {}

	/**
	 * Gets the class constant modifiers
	 * @link http://www.php.net/manual/en/reflectionclassconstant.getmodifiers.php
	 * @return int A numeric representation of the modifiers.
	 * The actual meaning of these modifiers are described under
	 * predefined constants.
	 */
	public function getModifiers () {}

	/**
	 * Gets declaring class
	 * @link http://www.php.net/manual/en/reflectionclassconstant.getdeclaringclass.php
	 * @return ReflectionClass A ReflectionClass object.
	 */
	public function getDeclaringClass () {}

	/**
	 * Gets doc comments
	 * @link http://www.php.net/manual/en/reflectionclassconstant.getdoccomment.php
	 * @return mixed The doc comment if it exists, otherwise false
	 */
	public function getDocComment () {}

	/**
	 * Gets Attributes
	 * @link http://www.php.net/manual/en/reflectionclassconstant.getattributes.php
	 * @param mixed $name [optional] 
	 * @param int $flags [optional] 
	 * @return array Array of attributes, as a ReflectionAttribute object.
	 */
	public function getAttributes ($name = null, int $flags = null): array {}

	/**
	 * Checks if class constant is an Enum case
	 * @link http://www.php.net/manual/en/reflectionclassconstant.isenumcase.php
	 * @return bool true if the class constant is an Enum case; false otherwise.
	 */
	public function isEnumCase (): bool {}

}

/**
 * The ReflectionExtension class reports
 * information about an extension.
 * @link http://www.php.net/manual/en/class.reflectionextension.php
 */
class ReflectionExtension implements Stringable, Reflector {
	public $name;


	/**
	 * Clones
	 * @link http://www.php.net/manual/en/reflectionextension.clone.php
	 * @return void No value is returned, if called a fatal error will occur.
	 */
	private function __clone (): void {}

	/**
	 * Constructs a ReflectionExtension
	 * @link http://www.php.net/manual/en/reflectionextension.construct.php
	 * @param string $name
	 */
	public function __construct (string $name) {}

	/**
	 * To string
	 * @link http://www.php.net/manual/en/reflectionextension.tostring.php
	 * @return string the exported extension as a string, in the same way as the 
	 * ReflectionExtension::export.
	 */
	public function __toString (): string {}

	/**
	 * Gets extension name
	 * @link http://www.php.net/manual/en/reflectionextension.getname.php
	 * @return string The extensions name.
	 */
	public function getName () {}

	/**
	 * Gets extension version
	 * @link http://www.php.net/manual/en/reflectionextension.getversion.php
	 * @return mixed The version of the extension, or null if the extension has no version.
	 */
	public function getVersion () {}

	/**
	 * Gets extension functions
	 * @link http://www.php.net/manual/en/reflectionextension.getfunctions.php
	 * @return array An associative array of ReflectionFunction objects, 
	 * for each function defined in the extension with the keys being the function
	 * names. If no function are defined, an empty array is returned.
	 */
	public function getFunctions () {}

	/**
	 * Gets constants
	 * @link http://www.php.net/manual/en/reflectionextension.getconstants.php
	 * @return array An associative array with constant names as keys.
	 */
	public function getConstants () {}

	/**
	 * Gets extension ini entries
	 * @link http://www.php.net/manual/en/reflectionextension.getinientries.php
	 * @return array An associative array with the ini entries as keys,
	 * with their defined values as values.
	 */
	public function getINIEntries () {}

	/**
	 * Gets classes
	 * @link http://www.php.net/manual/en/reflectionextension.getclasses.php
	 * @return array An array of ReflectionClass objects, one
	 * for each class within the extension. If no classes are defined,
	 * an empty array is returned.
	 */
	public function getClasses () {}

	/**
	 * Gets class names
	 * @link http://www.php.net/manual/en/reflectionextension.getclassnames.php
	 * @return array An array of class names, as defined in the extension.
	 * If no classes are defined, an empty array is returned.
	 */
	public function getClassNames () {}

	/**
	 * Gets dependencies
	 * @link http://www.php.net/manual/en/reflectionextension.getdependencies.php
	 * @return array An associative array with dependencies as keys and
	 * either Required, Optional 
	 * or Conflicts as the values.
	 */
	public function getDependencies () {}

	/**
	 * Print extension info
	 * @link http://www.php.net/manual/en/reflectionextension.info.php
	 * @return void Information about the extension.
	 */
	public function info () {}

	/**
	 * Returns whether this extension is persistent
	 * @link http://www.php.net/manual/en/reflectionextension.ispersistent.php
	 * @return bool true for extensions loaded by extension, false
	 * otherwise.
	 */
	public function isPersistent () {}

	/**
	 * Returns whether this extension is temporary
	 * @link http://www.php.net/manual/en/reflectionextension.istemporary.php
	 * @return bool true for extensions loaded by dl,
	 * false otherwise.
	 */
	public function isTemporary () {}

}

/**
 * @link http://www.php.net/manual/en/class.reflectionzendextension.php
 */
class ReflectionZendExtension implements Stringable, Reflector {
	public $name;


	/**
	 * Clone handler
	 * @link http://www.php.net/manual/en/reflectionzendextension.clone.php
	 * @return void 
	 */
	private function __clone (): void {}

	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/reflectionzendextension.construct.php
	 * @param string $name
	 */
	public function __construct (string $name) {}

	/**
	 * To string handler
	 * @link http://www.php.net/manual/en/reflectionzendextension.tostring.php
	 * @return string 
	 */
	public function __toString (): string {}

	/**
	 * Gets name
	 * @link http://www.php.net/manual/en/reflectionzendextension.getname.php
	 * @return string 
	 */
	public function getName () {}

	/**
	 * Gets version
	 * @link http://www.php.net/manual/en/reflectionzendextension.getversion.php
	 * @return string 
	 */
	public function getVersion () {}

	/**
	 * Gets author
	 * @link http://www.php.net/manual/en/reflectionzendextension.getauthor.php
	 * @return string 
	 */
	public function getAuthor () {}

	/**
	 * Gets URL
	 * @link http://www.php.net/manual/en/reflectionzendextension.geturl.php
	 * @return string 
	 */
	public function getURL () {}

	/**
	 * Gets copyright
	 * @link http://www.php.net/manual/en/reflectionzendextension.getcopyright.php
	 * @return string 
	 */
	public function getCopyright () {}

}

/**
 * The ReflectionReference class provides information about
 * a reference.
 * @link http://www.php.net/manual/en/class.reflectionreference.php
 */
final class ReflectionReference  {

	/**
	 * Create a ReflectionReference from an array element
	 * @link http://www.php.net/manual/en/reflectionreference.fromarrayelement.php
	 * @param array $array The array which contains the potential reference.
	 * @param mixed $key The key; either an integer or a string.
	 * @return mixed a ReflectionReference instance if
	 * $array[$key] is a reference, or null otherwise.
	 */
	public static function fromArrayElement (array $array, $key): ??ReflectionReference {}

	/**
	 * Get unique ID of a reference
	 * @link http://www.php.net/manual/en/reflectionreference.getid.php
	 * @return string a string of unspecified format.
	 */
	public function getId (): string {}

	private function __clone (): void {}

	/**
	 * Private constructor to disallow direct instantiation
	 * @link http://www.php.net/manual/en/reflectionreference.construct.php
	 */
	private function __construct () {}

}

/**
 * The ReflectionAttribute class provides information about
 * an Attribute.
 * @link http://www.php.net/manual/en/class.reflectionattribute.php
 */
class ReflectionAttribute implements Stringable, Reflector {
	const IS_INSTANCEOF = 2;


	/**
	 * Gets attribute name
	 * @link http://www.php.net/manual/en/reflectionattribute.getname.php
	 * @return string The name of the attribute.
	 */
	public function getName (): string {}

	/**
	 * Returns the target of the attribute as bitmask
	 * @link http://www.php.net/manual/en/reflectionattribute.gettarget.php
	 * @return int Gets target of the attribute as bitmask.
	 */
	public function getTarget (): int {}

	/**
	 * Returns whether the attribute of this name has been repeated on a code element
	 * @link http://www.php.net/manual/en/reflectionattribute.isrepeated.php
	 * @return bool true when attribute is used repeatedly, otherwise false.
	 */
	public function isRepeated (): bool {}

	/**
	 * Gets arguments passed to attribute
	 * @link http://www.php.net/manual/en/reflectionattribute.getarguments.php
	 * @return array The arguments passed to attribute.
	 */
	public function getArguments (): array {}

	/**
	 * Instantiates the attribute class represented by this ReflectionAttribute class and arguments
	 * @link http://www.php.net/manual/en/reflectionattribute.newinstance.php
	 * @return object New instance of the attribute.
	 */
	public function newInstance (): object {}

	public function __toString (): string {}

	private function __clone (): void {}

	/**
	 * Private constructor to disallow direct instantiation
	 * @link http://www.php.net/manual/en/reflectionattribute.construct.php
	 */
	private function __construct () {}

}

/**
 * The ReflectionEnum class reports
 * information about an Enum.
 * @link http://www.php.net/manual/en/class.reflectionenum.php
 */
class ReflectionEnum extends ReflectionClass implements Reflector, Stringable {
	const IS_IMPLICIT_ABSTRACT = 16;
	const IS_EXPLICIT_ABSTRACT = 64;
	const IS_FINAL = 32;

	public $name;


	/**
	 * Instantiates a ReflectionEnum object
	 * @link http://www.php.net/manual/en/reflectionenum.construct.php
	 * @param object|string $objectOrClass
	 */
	public function __construct (object|string $objectOrClass) {}

	/**
	 * Checks for a case on an Enum
	 * @link http://www.php.net/manual/en/reflectionenum.hascase.php
	 * @param string $name The case to check for.
	 * @return bool true if the case is defined, false if not.
	 */
	public function hasCase (string $name): bool {}

	/**
	 * Returns a specific case of an Enum
	 * @link http://www.php.net/manual/en/reflectionenum.getcase.php
	 * @param string $name The name of the case to retrieve.
	 * @return ReflectionEnumUnitCase An instance of ReflectionEnumUnitCase
	 * or ReflectionEnumBackedCase, as appropriate.
	 */
	public function getCase (string $name): ReflectionEnumUnitCase {}

	/**
	 * Returns a list of all cases on an Enum
	 * @link http://www.php.net/manual/en/reflectionenum.getcases.php
	 * @return array An array of Enum reflection objects, one for each case in the Enum. For a Unit Enum,
	 * they will all be instances of ReflectionEnumUnitCase. For a Backed
	 * Enum, they will all be instances of ReflectionEnumBackedCase.
	 */
	public function getCases (): array {}

	/**
	 * Determines if an Enum is a Backed Enum
	 * @link http://www.php.net/manual/en/reflectionenum.isbacked.php
	 * @return bool true if the Enum has a backing scalar, false if not.
	 */
	public function isBacked (): bool {}

	/**
	 * Gets the backing type of an Enum, if any
	 * @link http://www.php.net/manual/en/reflectionenum.getbackingtype.php
	 * @return mixed An instance of ReflectionNamedType, or null
	 * if the Enum has no backing type.
	 */
	public function getBackingType (): ??ReflectionType {}

	/**
	 * Returns the string representation of the ReflectionClass object
	 * @link http://www.php.net/manual/en/reflectionclass.tostring.php
	 * @return string A string representation of this ReflectionClass instance.
	 */
	public function __toString (): string {}

	/**
	 * Gets class name
	 * @link http://www.php.net/manual/en/reflectionclass.getname.php
	 * @return string The class name.
	 */
	public function getName () {}

	/**
	 * Checks if class is defined internally by an extension, or the core
	 * @link http://www.php.net/manual/en/reflectionclass.isinternal.php
	 * @return bool true on success or false on failure
	 */
	public function isInternal () {}

	/**
	 * Checks if user defined
	 * @link http://www.php.net/manual/en/reflectionclass.isuserdefined.php
	 * @return bool true on success or false on failure
	 */
	public function isUserDefined () {}

	/**
	 * Checks if class is anonymous
	 * @link http://www.php.net/manual/en/reflectionclass.isanonymous.php
	 * @return bool true on success or false on failure
	 */
	public function isAnonymous () {}

	/**
	 * Checks if the class is instantiable
	 * @link http://www.php.net/manual/en/reflectionclass.isinstantiable.php
	 * @return bool true on success or false on failure
	 */
	public function isInstantiable () {}

	/**
	 * Returns whether this class is cloneable
	 * @link http://www.php.net/manual/en/reflectionclass.iscloneable.php
	 * @return bool true if the class is cloneable, false otherwise.
	 */
	public function isCloneable () {}

	/**
	 * Gets the filename of the file in which the class has been defined
	 * @link http://www.php.net/manual/en/reflectionclass.getfilename.php
	 * @return mixed the filename of the file in which the class has been defined.
	 * If the class is defined in the PHP core or in a PHP extension, false
	 * is returned.
	 */
	public function getFileName () {}

	/**
	 * Gets starting line number
	 * @link http://www.php.net/manual/en/reflectionclass.getstartline.php
	 * @return mixed The starting line number, as an int, or false if unknown.
	 */
	public function getStartLine () {}

	/**
	 * Gets end line
	 * @link http://www.php.net/manual/en/reflectionclass.getendline.php
	 * @return mixed The ending line number of the user defined class, or false if unknown.
	 */
	public function getEndLine () {}

	/**
	 * Gets doc comments
	 * @link http://www.php.net/manual/en/reflectionclass.getdoccomment.php
	 * @return mixed The doc comment if it exists, otherwise false.
	 */
	public function getDocComment () {}

	/**
	 * Gets the constructor of the class
	 * @link http://www.php.net/manual/en/reflectionclass.getconstructor.php
	 * @return mixed A ReflectionMethod object reflecting the class' constructor, or null if the class
	 * has no constructor.
	 */
	public function getConstructor () {}

	/**
	 * Checks if method is defined
	 * @link http://www.php.net/manual/en/reflectionclass.hasmethod.php
	 * @param string $name Name of the method being checked for.
	 * @return bool true if it has the method, otherwise false
	 */
	public function hasMethod (string $name) {}

	/**
	 * Gets a ReflectionMethod for a class method
	 * @link http://www.php.net/manual/en/reflectionclass.getmethod.php
	 * @param string $name The method name to reflect.
	 * @return ReflectionMethod A ReflectionMethod.
	 */
	public function getMethod (string $name) {}

	/**
	 * Gets an array of methods
	 * @link http://www.php.net/manual/en/reflectionclass.getmethods.php
	 * @param mixed $filter [optional] <p>
	 * Filter the results to include only methods with certain attributes. Defaults
	 * to no filtering.
	 * </p>
	 * <p>
	 * Any bitwise disjunction of ReflectionMethod::IS_STATIC,
	 * ReflectionMethod::IS_PUBLIC,
	 * ReflectionMethod::IS_PROTECTED,
	 * ReflectionMethod::IS_PRIVATE,
	 * ReflectionMethod::IS_ABSTRACT,
	 * ReflectionMethod::IS_FINAL,
	 * so that all methods with any of the given
	 * attributes will be returned.
	 * </p>
	 * Note that other bitwise operations, for instance ~
	 * will not work as expected. In other words, it is not possible to
	 * retrieve all non-static methods, for example.
	 * @return array An array of ReflectionMethod objects
	 * reflecting each method.
	 */
	public function getMethods ($filter = null) {}

	/**
	 * Checks if property is defined
	 * @link http://www.php.net/manual/en/reflectionclass.hasproperty.php
	 * @param string $name Name of the property being checked for.
	 * @return bool true if it has the property, otherwise false
	 */
	public function hasProperty (string $name) {}

	/**
	 * Gets a ReflectionProperty for a class's property
	 * @link http://www.php.net/manual/en/reflectionclass.getproperty.php
	 * @param string $name The property name.
	 * @return ReflectionProperty A ReflectionProperty.
	 */
	public function getProperty (string $name) {}

	/**
	 * Gets properties
	 * @link http://www.php.net/manual/en/reflectionclass.getproperties.php
	 * @param mixed $filter [optional] The optional filter, for filtering desired property types. It's configured using
	 * the ReflectionProperty constants,
	 * and defaults to all property types.
	 * @return array An array of ReflectionProperty objects.
	 */
	public function getProperties ($filter = null) {}

	/**
	 * Checks if constant is defined
	 * @link http://www.php.net/manual/en/reflectionclass.hasconstant.php
	 * @param string $name The name of the constant being checked for.
	 * @return bool true if the constant is defined, otherwise false.
	 */
	public function hasConstant (string $name) {}

	/**
	 * Gets constants
	 * @link http://www.php.net/manual/en/reflectionclass.getconstants.php
	 * @param mixed $filter [optional] The optional filter, for filtering desired constant visibilities. It's configured using
	 * the ReflectionClassConstant constants,
	 * and defaults to all constant visibilities.
	 * @return array An array of constants, where the keys hold the name
	 * and the values the value of the constants.
	 */
	public function getConstants ($filter = null) {}

	/**
	 * Gets class constants
	 * @link http://www.php.net/manual/en/reflectionclass.getreflectionconstants.php
	 * @param mixed $filter [optional] The optional filter, for filtering desired constant visibilities. It's configured using
	 * the ReflectionClassConstant constants,
	 * and defaults to all constant visibilities.
	 * @return array An array of ReflectionClassConstant objects.
	 */
	public function getReflectionConstants ($filter = null) {}

	/**
	 * Gets defined constant
	 * @link http://www.php.net/manual/en/reflectionclass.getconstant.php
	 * @param string $name The name of the class constant to get.
	 * @return mixed Value of the constant with the name name.
	 * Returns false if the constant was not found in the class.
	 */
	public function getConstant (string $name) {}

	/**
	 * Gets a ReflectionClassConstant for a class's constant
	 * @link http://www.php.net/manual/en/reflectionclass.getreflectionconstant.php
	 * @param string $name The class constant name.
	 * @return mixed A ReflectionClassConstant, or false on failure.
	 */
	public function getReflectionConstant (string $name) {}

	/**
	 * Gets the interfaces
	 * @link http://www.php.net/manual/en/reflectionclass.getinterfaces.php
	 * @return array An associative array of interfaces, with keys as interface
	 * names and the array values as ReflectionClass objects.
	 */
	public function getInterfaces () {}

	/**
	 * Gets the interface names
	 * @link http://www.php.net/manual/en/reflectionclass.getinterfacenames.php
	 * @return array A numerical array with interface names as the values.
	 */
	public function getInterfaceNames () {}

	/**
	 * Checks if the class is an interface
	 * @link http://www.php.net/manual/en/reflectionclass.isinterface.php
	 * @return bool true on success or false on failure
	 */
	public function isInterface () {}

	/**
	 * Returns an array of traits used by this class
	 * @link http://www.php.net/manual/en/reflectionclass.gettraits.php
	 * @return array an array with trait names in keys and instances of trait's
	 * ReflectionClass in values.
	 * Returns null in case of an error.
	 */
	public function getTraits () {}

	/**
	 * Returns an array of names of traits used by this class
	 * @link http://www.php.net/manual/en/reflectionclass.gettraitnames.php
	 * @return array an array with trait names in values.
	 */
	public function getTraitNames () {}

	/**
	 * Returns an array of trait aliases
	 * @link http://www.php.net/manual/en/reflectionclass.gettraitaliases.php
	 * @return array an array with new method names in keys and original names (in the
	 * format "TraitName::original") in values.
	 */
	public function getTraitAliases () {}

	/**
	 * Returns whether this is a trait
	 * @link http://www.php.net/manual/en/reflectionclass.istrait.php
	 * @return bool true if this is a trait, false otherwise.
	 * Returns null in case of an error.
	 */
	public function isTrait () {}

	/**
	 * Returns whether this is an enum
	 * @link http://www.php.net/manual/en/reflectionclass.isenum.php
	 * @return bool true if this is an enum, false otherwise.
	 */
	public function isEnum (): bool {}

	/**
	 * Checks if class is abstract
	 * @link http://www.php.net/manual/en/reflectionclass.isabstract.php
	 * @return bool true on success or false on failure
	 */
	public function isAbstract () {}

	/**
	 * Checks if class is final
	 * @link http://www.php.net/manual/en/reflectionclass.isfinal.php
	 * @return bool true on success or false on failure
	 */
	public function isFinal () {}

	/**
	 * Gets the class modifiers
	 * @link http://www.php.net/manual/en/reflectionclass.getmodifiers.php
	 * @return int bitmask of 
	 * modifier constants.
	 */
	public function getModifiers () {}

	/**
	 * Checks class for instance
	 * @link http://www.php.net/manual/en/reflectionclass.isinstance.php
	 * @param object $object The object being compared to.
	 * @return bool true on success or false on failure
	 */
	public function isInstance ($object) {}

	/**
	 * Creates a new class instance from given arguments
	 * @link http://www.php.net/manual/en/reflectionclass.newinstance.php
	 * @param mixed $args Accepts a variable number of arguments which are passed to the class
	 * constructor, much like call_user_func.
	 * @return object 
	 */
	public function newInstance ($args) {}

	/**
	 * Creates a new class instance without invoking the constructor
	 * @link http://www.php.net/manual/en/reflectionclass.newinstancewithoutconstructor.php
	 * @return object 
	 */
	public function newInstanceWithoutConstructor () {}

	/**
	 * Creates a new class instance from given arguments
	 * @link http://www.php.net/manual/en/reflectionclass.newinstanceargs.php
	 * @param array $args [optional] The parameters to be passed to the class constructor as an array.
	 * @return mixed a new instance of the class, or null on failure.
	 */
	public function newInstanceArgs (array $args = null) {}

	/**
	 * Gets parent class
	 * @link http://www.php.net/manual/en/reflectionclass.getparentclass.php
	 * @return mixed A ReflectionClass or false if there's no parent.
	 */
	public function getParentClass () {}

	/**
	 * Checks if a subclass
	 * @link http://www.php.net/manual/en/reflectionclass.issubclassof.php
	 * @param mixed $class Either the name of the class as string or
	 * a ReflectionClass object of the class to check against.
	 * @return bool true on success or false on failure
	 */
	public function isSubclassOf ($class) {}

	/**
	 * Gets static properties
	 * @link http://www.php.net/manual/en/reflectionclass.getstaticproperties.php
	 * @return mixed The static properties, as an array, or null on failure.
	 */
	public function getStaticProperties () {}

	/**
	 * Gets static property value
	 * @link http://www.php.net/manual/en/reflectionclass.getstaticpropertyvalue.php
	 * @param string $name The name of the static property for which to return a value.
	 * @param mixed $def_value [optional] A default value to return in case the class does not declare a static
	 * property with the given name. If the property does
	 * not exist and this argument is omitted, a
	 * ReflectionException is thrown.
	 * @return mixed The value of the static property.
	 */
	public function getStaticPropertyValue (string $name, &$def_value = null) {}

	/**
	 * Sets static property value
	 * @link http://www.php.net/manual/en/reflectionclass.setstaticpropertyvalue.php
	 * @param string $name Property name.
	 * @param mixed $value New property value.
	 * @return void 
	 */
	public function setStaticPropertyValue (string $name, $value) {}

	/**
	 * Gets default properties
	 * @link http://www.php.net/manual/en/reflectionclass.getdefaultproperties.php
	 * @return array An array of default properties, with the key being the name of
	 * the property and the value being the default value of the property or null
	 * if the property doesn't have a default value. The function does not distinguish
	 * between static and non static properties and does not take visibility modifiers
	 * into account.
	 */
	public function getDefaultProperties () {}

	/**
	 * Check whether this class is iterable
	 * @link http://www.php.net/manual/en/reflectionclass.isiterable.php
	 * @return bool true on success or false on failure
	 */
	public function isIterable () {}

	/**
	 * Alias: ReflectionClass::isIterable
	 * @link http://www.php.net/manual/en/reflectionclass.isiterateable.php
	 */
	public function isIterateable () {}

	/**
	 * Implements interface
	 * @link http://www.php.net/manual/en/reflectionclass.implementsinterface.php
	 * @param mixed $interface The interface name.
	 * @return bool true on success or false on failure
	 */
	public function implementsInterface ($interface) {}

	/**
	 * Gets a ReflectionExtension object for the extension which defined the class
	 * @link http://www.php.net/manual/en/reflectionclass.getextension.php
	 * @return mixed A ReflectionExtension object representing the extension which defined the class,
	 * or null for user-defined classes.
	 */
	public function getExtension () {}

	/**
	 * Gets the name of the extension which defined the class
	 * @link http://www.php.net/manual/en/reflectionclass.getextensionname.php
	 * @return mixed The name of the extension which defined the class, or false for user-defined classes.
	 */
	public function getExtensionName () {}

	/**
	 * Checks if in namespace
	 * @link http://www.php.net/manual/en/reflectionclass.innamespace.php
	 * @return bool true on success or false on failure
	 */
	public function inNamespace () {}

	/**
	 * Gets namespace name
	 * @link http://www.php.net/manual/en/reflectionclass.getnamespacename.php
	 * @return string The namespace name.
	 */
	public function getNamespaceName () {}

	/**
	 * Gets short name
	 * @link http://www.php.net/manual/en/reflectionclass.getshortname.php
	 * @return string The class short name.
	 */
	public function getShortName () {}

	/**
	 * Gets Attributes
	 * @link http://www.php.net/manual/en/reflectionclass.getattributes.php
	 * @param mixed $name [optional] 
	 * @param int $flags [optional] 
	 * @return array Array of attributes, as a ReflectionAttribute object.
	 */
	public function getAttributes ($name = null, int $flags = null): array {}

}

/**
 * The ReflectionEnumUnitCase class reports
 * information about an Enum unit case, which has no scalar equivalent.
 * @link http://www.php.net/manual/en/class.reflectionenumunitcase.php
 */
class ReflectionEnumUnitCase extends ReflectionClassConstant implements Reflector, Stringable {
	const IS_PUBLIC = 1;
	const IS_PROTECTED = 2;
	const IS_PRIVATE = 4;
	const IS_FINAL = 32;

	public $name;
	public $class;


	/**
	 * Instantiates a ReflectionEnumUnitCase object
	 * @link http://www.php.net/manual/en/reflectionenumunitcase.construct.php
	 * @param object|string $class
	 * @param string $constant
	 */
	public function __construct (object|string $classstring , $constant) {}

	/**
	 * Gets the reflection of the enum of this case
	 * @link http://www.php.net/manual/en/reflectionenumunitcase.getenum.php
	 * @return ReflectionEnum A ReflectionEnum instance describing the Enum
	 * this case belongs to.
	 */
	public function getEnum (): ReflectionEnum {}

	/**
	 * Gets the enum case object described by this reflection object
	 * @link http://www.php.net/manual/en/reflectionenumunitcase.getvalue.php
	 * @return UnitEnum The enum case object described by this reflection object.
	 */
	public function getValue (): UnitEnum {}

	/**
	 * Returns the string representation of the ReflectionClassConstant object
	 * @link http://www.php.net/manual/en/reflectionclassconstant.tostring.php
	 * @return string A string representation of this ReflectionClassConstant instance.
	 */
	public function __toString (): string {}

	/**
	 * Get name of the constant
	 * @link http://www.php.net/manual/en/reflectionclassconstant.getname.php
	 * @return string the constant's name.
	 */
	public function getName () {}

	/**
	 * Checks if class constant is public
	 * @link http://www.php.net/manual/en/reflectionclassconstant.ispublic.php
	 * @return bool true if the class constant is public, otherwise false
	 */
	public function isPublic () {}

	/**
	 * Checks if class constant is private
	 * @link http://www.php.net/manual/en/reflectionclassconstant.isprivate.php
	 * @return bool true if the class constant is private, otherwise false
	 */
	public function isPrivate () {}

	/**
	 * Checks if class constant is protected
	 * @link http://www.php.net/manual/en/reflectionclassconstant.isprotected.php
	 * @return bool true if the class constant is protected, otherwise false
	 */
	public function isProtected () {}

	/**
	 * Checks if class constant is final
	 * @link http://www.php.net/manual/en/reflectionclassconstant.isfinal.php
	 * @return bool true if the class constant is final, otherwise false
	 */
	public function isFinal (): bool {}

	/**
	 * Gets the class constant modifiers
	 * @link http://www.php.net/manual/en/reflectionclassconstant.getmodifiers.php
	 * @return int A numeric representation of the modifiers.
	 * The actual meaning of these modifiers are described under
	 * predefined constants.
	 */
	public function getModifiers () {}

	/**
	 * Gets declaring class
	 * @link http://www.php.net/manual/en/reflectionclassconstant.getdeclaringclass.php
	 * @return ReflectionClass A ReflectionClass object.
	 */
	public function getDeclaringClass () {}

	/**
	 * Gets doc comments
	 * @link http://www.php.net/manual/en/reflectionclassconstant.getdoccomment.php
	 * @return mixed The doc comment if it exists, otherwise false
	 */
	public function getDocComment () {}

	/**
	 * Gets Attributes
	 * @link http://www.php.net/manual/en/reflectionclassconstant.getattributes.php
	 * @param mixed $name [optional] 
	 * @param int $flags [optional] 
	 * @return array Array of attributes, as a ReflectionAttribute object.
	 */
	public function getAttributes ($name = null, int $flags = null): array {}

	/**
	 * Checks if class constant is an Enum case
	 * @link http://www.php.net/manual/en/reflectionclassconstant.isenumcase.php
	 * @return bool true if the class constant is an Enum case; false otherwise.
	 */
	public function isEnumCase (): bool {}

}

/**
 * The ReflectionEnumBackedCase class reports
 * information about an Enum backed case, which has a scalar equivalent.
 * @link http://www.php.net/manual/en/class.reflectionenumbackedcase.php
 */
class ReflectionEnumBackedCase extends ReflectionEnumUnitCase implements Stringable, Reflector {
	const IS_PUBLIC = 1;
	const IS_PROTECTED = 2;
	const IS_PRIVATE = 4;
	const IS_FINAL = 32;

	public $name;
	public $class;


	/**
	 * Instantiates a ReflectionEnumBackedCase object
	 * @link http://www.php.net/manual/en/reflectionenumbackedcase.construct.php
	 * @param object|string $class
	 * @param string $constant
	 */
	public function __construct (object|string $classstring , $constant) {}

	/**
	 * Gets the scalar value backing this Enum case
	 * @link http://www.php.net/manual/en/reflectionenumbackedcase.getbackingvalue.php
	 * @return mixed The scalar equivalent of this enum case.
	 */
	public function getBackingValue (): string|int {}

	/**
	 * Gets the reflection of the enum of this case
	 * @link http://www.php.net/manual/en/reflectionenumunitcase.getenum.php
	 * @return ReflectionEnum A ReflectionEnum instance describing the Enum
	 * this case belongs to.
	 */
	public function getEnum (): ReflectionEnum {}

	/**
	 * Gets the enum case object described by this reflection object
	 * @link http://www.php.net/manual/en/reflectionenumunitcase.getvalue.php
	 * @return UnitEnum The enum case object described by this reflection object.
	 */
	public function getValue (): UnitEnum {}

	/**
	 * Returns the string representation of the ReflectionClassConstant object
	 * @link http://www.php.net/manual/en/reflectionclassconstant.tostring.php
	 * @return string A string representation of this ReflectionClassConstant instance.
	 */
	public function __toString (): string {}

	/**
	 * Get name of the constant
	 * @link http://www.php.net/manual/en/reflectionclassconstant.getname.php
	 * @return string the constant's name.
	 */
	public function getName () {}

	/**
	 * Checks if class constant is public
	 * @link http://www.php.net/manual/en/reflectionclassconstant.ispublic.php
	 * @return bool true if the class constant is public, otherwise false
	 */
	public function isPublic () {}

	/**
	 * Checks if class constant is private
	 * @link http://www.php.net/manual/en/reflectionclassconstant.isprivate.php
	 * @return bool true if the class constant is private, otherwise false
	 */
	public function isPrivate () {}

	/**
	 * Checks if class constant is protected
	 * @link http://www.php.net/manual/en/reflectionclassconstant.isprotected.php
	 * @return bool true if the class constant is protected, otherwise false
	 */
	public function isProtected () {}

	/**
	 * Checks if class constant is final
	 * @link http://www.php.net/manual/en/reflectionclassconstant.isfinal.php
	 * @return bool true if the class constant is final, otherwise false
	 */
	public function isFinal (): bool {}

	/**
	 * Gets the class constant modifiers
	 * @link http://www.php.net/manual/en/reflectionclassconstant.getmodifiers.php
	 * @return int A numeric representation of the modifiers.
	 * The actual meaning of these modifiers are described under
	 * predefined constants.
	 */
	public function getModifiers () {}

	/**
	 * Gets declaring class
	 * @link http://www.php.net/manual/en/reflectionclassconstant.getdeclaringclass.php
	 * @return ReflectionClass A ReflectionClass object.
	 */
	public function getDeclaringClass () {}

	/**
	 * Gets doc comments
	 * @link http://www.php.net/manual/en/reflectionclassconstant.getdoccomment.php
	 * @return mixed The doc comment if it exists, otherwise false
	 */
	public function getDocComment () {}

	/**
	 * Gets Attributes
	 * @link http://www.php.net/manual/en/reflectionclassconstant.getattributes.php
	 * @param mixed $name [optional] 
	 * @param int $flags [optional] 
	 * @return array Array of attributes, as a ReflectionAttribute object.
	 */
	public function getAttributes ($name = null, int $flags = null): array {}

	/**
	 * Checks if class constant is an Enum case
	 * @link http://www.php.net/manual/en/reflectionclassconstant.isenumcase.php
	 * @return bool true if the class constant is an Enum case; false otherwise.
	 */
	public function isEnumCase (): bool {}

}

/**
 * @link http://www.php.net/manual/en/class.reflectionfiber.php
 */
final class ReflectionFiber  {

	/**
	 * Constructs a ReflectionFiber object
	 * @link http://www.php.net/manual/en/reflectionfiber.construct.php
	 * @param Fiber $fiber
	 */
	public function __construct (Fiber $fiber) {}

	/**
	 * Get the reflected Fiber instance
	 * @link http://www.php.net/manual/en/reflectionfiber.getfiber.php
	 * @return Fiber The Fiber instance being reflected.
	 */
	public function getFiber (): Fiber {}

	/**
	 * Get the file name of the current execution point
	 * @link http://www.php.net/manual/en/reflectionfiber.getexecutingfile.php
	 * @return string The full path and file name of the reflected fiber.
	 */
	public function getExecutingFile (): ??string {}

	/**
	 * Get the line number of the current execution point
	 * @link http://www.php.net/manual/en/reflectionfiber.getexecutingline.php
	 * @return int The line number of the current execution point in the fiber.
	 */
	public function getExecutingLine (): ??int {}

	/**
	 * Gets the callable used to create the Fiber
	 * @link http://www.php.net/manual/en/reflectionfiber.getcallable.php
	 * @return callable The callable used to create the Fiber.
	 */
	public function getCallable (): callable {}

	/**
	 * Get the backtrace of the current execution point
	 * @link http://www.php.net/manual/en/reflectionfiber.gettrace.php
	 * @param int $options [optional] <p>
	 * The value of options can be any of
	 * the following flags.
	 * </p>
	 * <p>
	 * <table>
	 * Available options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>
	 * DEBUG_BACKTRACE_PROVIDE_OBJECT
	 * </td>
	 * <td>
	 * Default.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>
	 * DEBUG_BACKTRACE_IGNORE_ARGS
	 * </td>
	 * <td>
	 * Don't include the argument information for functions in the stack
	 * trace.
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * @return array The backtrace of the current execution point in the fiber.
	 */
	public function getTrace (int $options = null): array {}

}
// End of Reflection v.8.1.19