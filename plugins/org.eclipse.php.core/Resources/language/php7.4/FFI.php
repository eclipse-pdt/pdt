<?php

// Start of FFI v.7.4.0

namespace FFI {

/**
 * @link http://www.php.net/manual/en/class.ffi-exception.php
 */
class Exception extends \Error implements \Throwable {
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
 * @link http://www.php.net/manual/en/class.ffi-parserexception.php
 */
final class ParserException extends \FFI\Exception implements \Throwable {
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


}


namespace {

/**
 * Objects of this class are created by the factory methods FFI::cdef,
 * FFI::load or FFI::scope. Defined C variables
 * are made available as properties of the FFI instance, and defined C functions are made available
 * as methods of the FFI instance. Declared C types can be used to create new C data structures
 * using FFI::new and FFI::type.
 * <p>FFI definition parsing and shared library loading may take significant time. It is not useful
 * to do it on each HTTP request in a Web environment. However, it is possible to preload FFI definitions
 * and libraries at PHP startup, and to instantiate FFI objects when necessary. Header files
 * may be extended with special FFI_SCOPE defines (e.g. #define FFI_SCOPE "foo"”";
 * the default scope is "C") and then loaded by FFI::load during preloading.
 * This leads to the creation of a persistent binding, that will be available to all the following
 * requests through FFI::scope.
 * Refer to the complete PHP/FFI/preloading example
 * for details.</p>
 * <p>It is possible to preload more than one C header file into the same scope.</p>
 * @link http://www.php.net/manual/en/class.ffi.php
 */
final class FFI  {

	/**
	 * Creates a new FFI object
	 * @link http://www.php.net/manual/en/ffi.cdef.php
	 * @param string $code [optional] <p>
	 * A string containing a sequence of declarations in regular C language
	 * (types, structures, functions, variables, etc). Actually, this string may
	 * be copy-pasted from C header files.
	 * </p>
	 * <p>
	 * C preprocessor directives are not supported, i.e. #include, 
	 * #define and CPP macros do not work.
	 * </p>
	 * @param string $lib [optional] <p>
	 * The name of a shared library file, to be loaded and linked with the
	 * definitions.
	 * </p>
	 * <p>
	 * If lib is omitted, platforms supporting RTLD_DEFAULT
	 * attempt to lookup symbols declared in code in the normal global
	 * scope. Other systems will fail to resolve these symbols.
	 * </p>
	 * @return FFI the freshly created FFI object.
	 */
	public static function cdef (string $code = null, string $lib = null) {}

	/**
	 * Loads C declarations from a C header file
	 * @link http://www.php.net/manual/en/ffi.load.php
	 * @param string $filename <p>
	 * The name of a C header file.
	 * </p>
	 * <p>
	 * C preprocessor directives are not supported, i.e. #include, 
	 * #define and CPP macros do not work.
	 * </p>
	 * @return FFI the freshly created FFI object.
	 */
	public static function load (string $filename) {}

	/**
	 * Instantiates an FFI object with C declarations parsed during preloading
	 * @link http://www.php.net/manual/en/ffi.scope.php
	 * @param string $scope_name The scope name defined by a special FFI_SCOPE define.
	 * @return FFI the freshly created FFI object.
	 */
	public static function scope (string $scope_name) {}

	/**
	 * Creates a C data structure
	 * @link http://www.php.net/manual/en/ffi.new.php
	 * @param mixed $type type is a valid C declaration as string, or an
	 * instance of FFI\CType which has already been created.
	 * @param bool $owned [optional] Whether to create owned (i.e. managed) or unmanaged data. Managed data lives together
	 * with the returned FFI\CData object, and is released when the
	 * last reference to that object is released by regular PHP reference counting or GC.
	 * Unmanaged data should be released by calling FFI::free,
	 * when no longer needed.
	 * @param bool $persistent [optional] Whether to allocate the C data structure permanently on the system heap (using 
	 * malloc), or on the PHP request heap (using emalloc).
	 * @return FFI\CData the freshly created FFI\CData object.
	 */
	public static function new ($type, bool $owned = null, bool $persistent = null) {}

	/**
	 * Releases an unmanaged data structure
	 * @link http://www.php.net/manual/en/ffi.free.php
	 * @param FFI\CData $ptr The handle of the unmanaged pointer to a C data structure.
	 * @return void 
	 */
	public static function free (FFI\CData &$ptr) {}

	/**
	 * Performs a C type cast
	 * @link http://www.php.net/manual/en/ffi.cast.php
	 * @param mixed $type A valid C declaration as string, or an instance of FFI\CType
	 * which has already been created.
	 * @param FFI\CData $ptr The handle of the pointer to a C data structure.
	 * @return FFI\CData the freshly created FFI\CData object.
	 */
	public static function cast ($type, FFI\CData &$ptr) {}

	/**
	 * Creates an FFI\CType object from a C declaration
	 * @link http://www.php.net/manual/en/ffi.type.php
	 * @param mixed $type A valid C declaration as string, or an instance of FFI\CType
	 * which has already been created.
	 * @return FFI\CType the freshly created FFI\CType object.
	 */
	public static function type ($type) {}

	/**
	 * Gets the FFI\CType of FFI\CData
	 * @link http://www.php.net/manual/en/ffi.typeof.php
	 * @param FFI\CData $ptr The handle of the pointer to a C data structure.
	 * @return FFI\CType the FFI\CType object representing the type of the given
	 * FFI\CData object.
	 */
	public static function typeof (FFI\CData &$ptr) {}

	/**
	 * Dynamically constructs a new C array type
	 * @link http://www.php.net/manual/en/ffi.arraytype.php
	 * @param FFI\CType $type A valid C declaration as string, or an instance of FFI\CType
	 * which has already been created.
	 * @param array $dims The dimensions of the type as array.
	 * @return FFI\CType the freshly created FFI\CType object.
	 */
	public static function arrayType (FFI\CType $type, array $dims) {}

	/**
	 * Creates an unmanaged pointer to C data
	 * @link http://www.php.net/manual/en/ffi.addr.php
	 * @param FFI\CData $ptr The handle of the pointer to a C data structure.
	 * @return FFI\CData the freshly created FFI\CData object.
	 */
	public static function addr (FFI\CData &$ptr) {}

	/**
	 * Gets the size of C data or types
	 * @link http://www.php.net/manual/en/ffi.sizeof.php
	 * @param mixed $ptr The handle of the C data or type.
	 * @return int The size of the memory area pointed at by ptr.
	 */
	public static function sizeof (&$ptr) {}

	/**
	 * Gets the alignment
	 * @link http://www.php.net/manual/en/ffi.alignof.php
	 * @param mixed $ptr The handle of the C data or type.
	 * @return int the alignment of the given FFI\CData or
	 * FFI\CType object.
	 */
	public static function alignof (&$ptr) {}

	/**
	 * Copies one memory area to another
	 * @link http://www.php.net/manual/en/ffi.memcpy.php
	 * @param FFI\CData $dst The start of the memory area to copy to.
	 * @param mixed $src The start of the memory area to copy from.
	 * @param int $size The number of bytes to copy.
	 * @return void 
	 */
	public static function memcpy (FFI\CData &$dst, &$src, int $size) {}

	/**
	 * Compares memory areas
	 * @link http://www.php.net/manual/en/ffi.memcmp.php
	 * @param mixed $ptr1 The start of one memory area.
	 * @param mixed $ptr2 The start of another memory area.
	 * @param int $size The number of bytes to compare.
	 * @return int &lt; 0 if the contents of the memory area starting at ptr1
	 * are considered less than the contents of the memory area starting at ptr2,
	 * &gt; 0 if the contents of the first memory area are considered greater than the second,
	 * and 0 if they are equal.
	 */
	public static function memcmp (&$ptr1, &$ptr2, int $size) {}

	/**
	 * Fills a memory area
	 * @link http://www.php.net/manual/en/ffi.memset.php
	 * @param FFI\CData $ptr The start of the memory area to fill.
	 * @param int $ch The byte to fill with.
	 * @param int $size The number of bytes to fill.
	 * @return void 
	 */
	public static function memset (FFI\CData &$ptr, int $ch, int $size) {}

	/**
	 * Creates a PHP string from a memory area
	 * @link http://www.php.net/manual/en/ffi.string.php
	 * @param FFI\CData $ptr The start of the memory area from which to create a string.
	 * @param int $size [optional] The number of bytes to copy to the string.
	 * If size is omitted, ptr must be a zero terminated
	 * array of C chars.
	 * @return string The freshly created PHP string.
	 */
	public static function string (FFI\CData &$ptr, int $size = null) {}

	/**
	 * Checks whether a FFI\CData is a null pointer
	 * @link http://www.php.net/manual/en/ffi.isnull.php
	 * @param FFI\CData $ptr The handle of the pointer to a C data structure.
	 * @return bool whether a FFI\CData is a null pointer.
	 */
	public static function isNull (FFI\CData &$ptr) {}

}


}


namespace FFI {

/**
 * FFI\CData objects can be used in a number of ways as a regular
 * PHP data:
 * <p>
 * <br>
 * C data of scalar types can be read and assigned via the $cdata property, e.g. 
 * $x = FFI::new('int'); $x->cdata = 42;
 * <br>
 * C struct and union fields can be accessed as regular PHP object property, e.g.
 * $cdata->field
 * <br>
 * C array elements can be accessed as regular PHP array elements, e.g.
 * $cdata[$offset]
 * <br>
 * C arrays can be iterated using foreach statements.
 * <br>
 * C arrays can be used as arguments of count.
 * <br>
 * C pointers can be dereferenced as arrays, e.g. $cdata[0]
 * <br>
 * C pointers can be compared using regualar comparison operators (&lt;,
 * &lt;=, ==, !=, &gt;=, &gt;).
 * <br>
 * C pointers can be incremented and decremented using regular +/-/
 * ++/–- operations, e.g. $cdata += 5
 * <br>
 * C pointers can be subtracted from another using regular - operations.
 * <br>
 * C pointers to functions can be called as a regular PHP closure, e.g. $cdata()
 * <br>
 * Any C data can be duplicated using the clone
 * operator, e.g. $cdata2 = clone $cdata;
 * <br>
 * Any C data can be visualized using var_dump, print_r, etc.
 * </p>
 * Noteable limitations are that FFI\CData instances do not support
 * isset, empty and unset,
 * and that wrapped C structs and unions do not implement Traversable.
 * @link http://www.php.net/manual/en/class.ffi-cdata.php
 */
final class CData  {
}

/**
 * @link http://www.php.net/manual/en/class.ffi-ctype.php
 */
final class CType  {
}

}

// End of FFI v.7.4.0
