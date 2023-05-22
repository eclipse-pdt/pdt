<?php

// Start of FFI v.8.2.6

namespace FFI {

/**
 * @link http://www.php.net/manual/en/class.ffi-exception.php
 */
class Exception extends \Error implements \Throwable, \Stringable {

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
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
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

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
 * @link http://www.php.net/manual/en/class.ffi-parserexception.php
 */
final class ParserException extends \FFI\Exception implements \Stringable, \Throwable {

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
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
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

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
	const __BIGGEST_ALIGNMENT__ = 8;


	/**
	 * Creates a new FFI object
	 * @link http://www.php.net/manual/en/ffi.cdef.php
	 * @param string $code [optional] A string containing a sequence of declarations in regular C language
	 * (types, structures, functions, variables, etc). Actually, this string may
	 * be copy-pasted from C header files.
	 * <p>C preprocessor directives are not supported, i.e. #include, 
	 * #define and CPP macros do not work.</p>
	 * @param string|null $lib [optional] The name of a shared library file, to be loaded and linked with the
	 * definitions.
	 * <p>If lib is omitted, platforms supporting RTLD_DEFAULT
	 * attempt to lookup symbols declared in code in the normal global
	 * scope. Other systems will fail to resolve these symbols.</p>
	 * @return FFI Returns the freshly created FFI object.
	 */
	public static function cdef (string $code = '""', ?string $lib = null): FFI {}

	/**
	 * Loads C declarations from a C header file
	 * @link http://www.php.net/manual/en/ffi.load.php
	 * @param string $filename The name of a C header file.
	 * <p>C preprocessor directives are not supported, i.e. #include,
	 * #define and CPP macros do not work, except for special cases
	 * listed below.</p>
	 * <p>The header file should contain a #define statement for the
	 * FFI_SCOPE variable, e.g.: #define FFI_SCOPE "MYLIB".
	 * Refer to the class introduction for details.</p>
	 * <p>The header file may contain a #define statement for the
	 * FFI_LIB variable to specify the library it exposes. If it is
	 * a system library only the file name is required, e.g.: #define FFI_LIB
	 * "libc.so.6". If it is a custom library, a relative path is required,
	 * e.g.: #define FFI_LIB "./mylib.so".</p>
	 * @return FFI|null Returns the freshly created FFI object, or null on failure.
	 */
	public static function load (string $filename): ?FFI {}

	/**
	 * Instantiates an FFI object with C declarations parsed during preloading
	 * @link http://www.php.net/manual/en/ffi.scope.php
	 * @param string $name The scope name defined by a special FFI_SCOPE define.
	 * @return FFI Returns the freshly created FFI object.
	 */
	public static function scope (string $name): FFI {}

	/**
	 * Creates a C data structure
	 * @link http://www.php.net/manual/en/ffi.new.php
	 * @param FFI\CType|string $type type is a valid C declaration as string, or an
	 * instance of FFI\CType which has already been created.
	 * @param bool $owned [optional] Whether to create owned (i.e. managed) or unmanaged data. Managed data lives together
	 * with the returned FFI\CData object, and is released when the
	 * last reference to that object is released by regular PHP reference counting or GC.
	 * Unmanaged data should be released by calling FFI::free,
	 * when no longer needed.
	 * @param bool $persistent [optional] Whether to allocate the C data structure permanently on the system heap (using 
	 * malloc), or on the PHP request heap (using emalloc).
	 * @return FFI\CData|null Returns the freshly created FFI\CData object,
	 * or null on failure.
	 */
	public static function new (FFI\CType|string $type, bool $owned = true, bool $persistent = false): ?FFI\CData {}

	/**
	 * Releases an unmanaged data structure
	 * @link http://www.php.net/manual/en/ffi.free.php
	 * @param FFI\CData $ptr The handle of the unmanaged pointer to a C data structure.
	 * @return void No value is returned.
	 */
	public static function free (FFI\CData &$ptr): void {}

	/**
	 * Performs a C type cast
	 * @link http://www.php.net/manual/en/ffi.cast.php
	 * @param FFI\CType|string $type A valid C declaration as string, or an instance of FFI\CType
	 * which has already been created.
	 * @param FFI\CData|int|float|bool|null $ptr The handle of the pointer to a C data structure.
	 * @return FFI\CData|null Returns the freshly created FFI\CData object.
	 */
	public static function cast (FFI\CType|string $type, FFI\CData|int|float|bool|null &$ptr): ?FFI\CData {}

	/**
	 * Creates an FFI\CType object from a C declaration
	 * @link http://www.php.net/manual/en/ffi.type.php
	 * @param string $type A valid C declaration as string, or an instance of FFI\CType
	 * which has already been created.
	 * @return FFI\CType|null Returns the freshly created FFI\CType object,
	 * or null on failure.
	 */
	public static function type (string $type): ?FFI\CType {}

	/**
	 * Gets the FFI\CType of FFI\CData
	 * @link http://www.php.net/manual/en/ffi.typeof.php
	 * @param FFI\CData $ptr The handle of the pointer to a C data structure.
	 * @return FFI\CType Returns the FFI\CType object representing the type of the given
	 * FFI\CData object.
	 */
	public static function typeof (FFI\CData &$ptr): FFI\CType {}

	/**
	 * Dynamically constructs a new C array type
	 * @link http://www.php.net/manual/en/ffi.arraytype.php
	 * @param FFI\CType $type A valid C declaration as string, or an instance of FFI\CType
	 * which has already been created.
	 * @param array $dimensions The dimensions of the type as array.
	 * @return FFI\CType Returns the freshly created FFI\CType object.
	 */
	public static function arrayType (FFI\CType $type, array $dimensions): FFI\CType {}

	/**
	 * Creates an unmanaged pointer to C data
	 * @link http://www.php.net/manual/en/ffi.addr.php
	 * @param FFI\CData $ptr The handle of the pointer to a C data structure.
	 * @return FFI\CData Returns the freshly created FFI\CData object.
	 */
	public static function addr (FFI\CData &$ptr): FFI\CData {}

	/**
	 * Gets the size of C data or types
	 * @link http://www.php.net/manual/en/ffi.sizeof.php
	 * @param FFI\CData|FFI\CType $ptr The handle of the C data or type.
	 * @return int The size of the memory area pointed at by ptr.
	 */
	public static function sizeof (FFI\CData|FFI\CType &$ptr): int {}

	/**
	 * Gets the alignment
	 * @link http://www.php.net/manual/en/ffi.alignof.php
	 * @param FFI\CData|FFI\CType $ptr The handle of the C data or type.
	 * @return int Returns the alignment of the given FFI\CData or
	 * FFI\CType object.
	 */
	public static function alignof (FFI\CData|FFI\CType &$ptr): int {}

	/**
	 * Copies one memory area to another
	 * @link http://www.php.net/manual/en/ffi.memcpy.php
	 * @param FFI\CData $to The start of the memory area to copy to.
	 * @param FFI\CData|string $from The start of the memory area to copy from.
	 * @param int $size The number of bytes to copy.
	 * @return void No value is returned.
	 */
	public static function memcpy (FFI\CData &$to, FFI\CData|string &$from, int $size): void {}

	/**
	 * Compares memory areas
	 * @link http://www.php.net/manual/en/ffi.memcmp.php
	 * @param string|FFI\CData $ptr1 The start of one memory area.
	 * @param string|FFI\CData $ptr2 The start of another memory area.
	 * @param int $size The number of bytes to compare.
	 * @return int Returns &lt; 0 if the contents of the memory area starting at ptr1
	 * are considered less than the contents of the memory area starting at ptr2,
	 * &gt; 0 if the contents of the first memory area are considered greater than the second,
	 * and 0 if they are equal.
	 */
	public static function memcmp (string|FFI\CData &$ptr1, string|FFI\CData &$ptr2, int $size): int {}

	/**
	 * Fills a memory area
	 * @link http://www.php.net/manual/en/ffi.memset.php
	 * @param FFI\CData $ptr The start of the memory area to fill.
	 * @param int $value The byte to fill with.
	 * @param int $size The number of bytes to fill.
	 * @return void No value is returned.
	 */
	public static function memset (FFI\CData &$ptr, int $value, int $size): void {}

	/**
	 * Creates a PHP string from a memory area
	 * @link http://www.php.net/manual/en/ffi.string.php
	 * @param FFI\CData $ptr The start of the memory area from which to create a string.
	 * @param int|null $size [optional] The number of bytes to copy to the string.
	 * If size is omitted, ptr must be a zero terminated
	 * array of C chars.
	 * @return string The freshly created PHP string.
	 */
	public static function string (FFI\CData &$ptr, ?int $size = null): string {}

	/**
	 * Checks whether a FFI\CData is a null pointer
	 * @link http://www.php.net/manual/en/ffi.isnull.php
	 * @param FFI\CData $ptr The handle of the pointer to a C data structure.
	 * @return bool Returns whether a FFI\CData is a null pointer.
	 */
	public static function isNull (FFI\CData &$ptr): bool {}

}


}


namespace FFI {

/**
 * FFI\CData objects can be used in a number of ways as a regular
 * PHP data:
 * <p>
 * <br>
 * C data of scalar types can be read and assigned via the $cdata property, e.g. 
 * $x = FFI::new('int'); $x-&gt;cdata = 42;
 * <br>
 * C struct and union fields can be accessed as regular PHP object property, e.g.
 * $cdata-&gt;field
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
 * C pointers can be compared using regular comparison operators (&lt;,
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
 * Notable limitations are that FFI\CData instances do not support
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
	const TYPE_VOID = 0;
	const TYPE_FLOAT = 1;
	const TYPE_DOUBLE = 2;
	const TYPE_LONGDOUBLE = 3;
	const TYPE_UINT8 = 4;
	const TYPE_SINT8 = 5;
	const TYPE_UINT16 = 6;
	const TYPE_SINT16 = 7;
	const TYPE_UINT32 = 8;
	const TYPE_SINT32 = 9;
	const TYPE_UINT64 = 10;
	const TYPE_SINT64 = 11;
	const TYPE_ENUM = 12;
	const TYPE_BOOL = 13;
	const TYPE_CHAR = 14;
	const TYPE_POINTER = 15;
	const TYPE_FUNC = 16;
	const TYPE_ARRAY = 17;
	const TYPE_STRUCT = 18;
	const ATTR_CONST = 1;
	const ATTR_INCOMPLETE_TAG = 2;
	const ATTR_VARIADIC = 4;
	const ATTR_INCOMPLETE_ARRAY = 8;
	const ATTR_VLA = 16;
	const ATTR_UNION = 32;
	const ATTR_PACKED = 64;
	const ATTR_MS_STRUCT = 128;
	const ATTR_GCC_STRUCT = 256;
	const ABI_DEFAULT = 0;
	const ABI_CDECL = 1;
	const ABI_FASTCALL = 2;
	const ABI_THISCALL = 3;
	const ABI_STDCALL = 4;
	const ABI_PASCAL = 5;
	const ABI_REGISTER = 6;
	const ABI_MS = 7;
	const ABI_SYSV = 8;
	const ABI_VECTORCALL = 9;


	/**
	 * Description
	 * @link http://www.php.net/manual/en/ffi-ctype.getname.php
	 * @return string 
	 */
	public function getName (): string {}

	/**
	 * Description
	 * @link http://www.php.net/manual/en/ffi-ctype.getkind.php
	 * @return int 
	 */
	public function getKind (): int {}

	/**
	 * Description
	 * @link http://www.php.net/manual/en/ffi-ctype.getsize.php
	 * @return int 
	 */
	public function getSize (): int {}

	/**
	 * Description
	 * @link http://www.php.net/manual/en/ffi-ctype.getalignment.php
	 * @return int 
	 */
	public function getAlignment (): int {}

	/**
	 * Description
	 * @link http://www.php.net/manual/en/ffi-ctype.getattributes.php
	 * @return int 
	 */
	public function getAttributes (): int {}

	/**
	 * Description
	 * @link http://www.php.net/manual/en/ffi-ctype.getenumkind.php
	 * @return int 
	 */
	public function getEnumKind (): int {}

	/**
	 * Description
	 * @link http://www.php.net/manual/en/ffi-ctype.getarrayelementtype.php
	 * @return \FFI\CType 
	 */
	public function getArrayElementType (): \FFI\CType {}

	/**
	 * Description
	 * @link http://www.php.net/manual/en/ffi-ctype.getarraylength.php
	 * @return int 
	 */
	public function getArrayLength (): int {}

	/**
	 * Description
	 * @link http://www.php.net/manual/en/ffi-ctype.getpointertype.php
	 * @return \FFI\CType 
	 */
	public function getPointerType (): \FFI\CType {}

	/**
	 * Description
	 * @link http://www.php.net/manual/en/ffi-ctype.getstructfieldnames.php
	 * @return array 
	 */
	public function getStructFieldNames (): array {}

	/**
	 * Description
	 * @link http://www.php.net/manual/en/ffi-ctype.getstructfieldoffset.php
	 * @param string $name 
	 * @return int 
	 */
	public function getStructFieldOffset (string $name): int {}

	/**
	 * Description
	 * @link http://www.php.net/manual/en/ffi-ctype.getstructfieldtype.php
	 * @param string $name 
	 * @return \FFI\CType 
	 */
	public function getStructFieldType (string $name): \FFI\CType {}

	/**
	 * Description
	 * @link http://www.php.net/manual/en/ffi-ctype.getfuncabi.php
	 * @return int 
	 */
	public function getFuncABI (): int {}

	/**
	 * Description
	 * @link http://www.php.net/manual/en/ffi-ctype.getfuncreturntype.php
	 * @return \FFI\CType 
	 */
	public function getFuncReturnType (): \FFI\CType {}

	/**
	 * Description
	 * @link http://www.php.net/manual/en/ffi-ctype.getfuncparametercount.php
	 * @return int 
	 */
	public function getFuncParameterCount (): int {}

	/**
	 * Description
	 * @link http://www.php.net/manual/en/ffi-ctype.getfuncparametertype.php
	 * @param int $index 
	 * @return \FFI\CType 
	 */
	public function getFuncParameterType (int $index): \FFI\CType {}

}

}

// End of FFI v.8.2.6
