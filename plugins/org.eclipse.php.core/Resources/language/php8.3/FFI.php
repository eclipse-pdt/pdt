<?php

// Start of FFI v.8.3.0

namespace FFI {

class Exception extends \Error implements \Throwable, \Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

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
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

final class ParserException extends \FFI\Exception implements \Stringable, \Throwable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

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
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}


}


namespace {

final class FFI  {
	const __BIGGEST_ALIGNMENT__ = 8;


	/**
	 * {@inheritdoc}
	 * @param string $code [optional]
	 * @param string|null $lib [optional]
	 */
	public static function cdef (string $code = '', ?string $lib = NULL): FFI {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 */
	public static function load (string $filename): ?FFI {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public static function scope (string $name): FFI {}

	/**
	 * {@inheritdoc}
	 * @param FFI\CType|string $type
	 * @param bool $owned [optional]
	 * @param bool $persistent [optional]
	 */
	public static function new (FFI\CType|string $type, bool $owned = true, bool $persistent = false): ?FFI\CData {}

	/**
	 * {@inheritdoc}
	 * @param FFI\CData $ptr
	 */
	public static function free (FFI\CData &$ptr): void {}

	/**
	 * {@inheritdoc}
	 * @param FFI\CType|string $type
	 * @param mixed $ptr
	 */
	public static function cast (FFI\CType|string $type, &$ptr = null): ?FFI\CData {}

	/**
	 * {@inheritdoc}
	 * @param string $type
	 */
	public static function type (string $type): ?FFI\CType {}

	/**
	 * {@inheritdoc}
	 * @param FFI\CData $ptr
	 */
	public static function typeof (FFI\CData &$ptr): FFI\CType {}

	/**
	 * {@inheritdoc}
	 * @param FFI\CType $type
	 * @param array $dimensions
	 */
	public static function arrayType (FFI\CType $type, array $dimensions): FFI\CType {}

	/**
	 * {@inheritdoc}
	 * @param FFI\CData $ptr
	 */
	public static function addr (FFI\CData &$ptr): FFI\CData {}

	/**
	 * {@inheritdoc}
	 * @param FFI\CData|FFI\CType $ptr
	 */
	public static function sizeof (FFI\CData|FFI\CType &$ptr): int {}

	/**
	 * {@inheritdoc}
	 * @param FFI\CData|FFI\CType $ptr
	 */
	public static function alignof (FFI\CData|FFI\CType &$ptr): int {}

	/**
	 * {@inheritdoc}
	 * @param FFI\CData $to
	 * @param mixed $from
	 * @param int $size
	 */
	public static function memcpy (FFI\CData &$to, &$from = null, int $size): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $ptr1
	 * @param mixed $ptr2
	 * @param int $size
	 */
	public static function memcmp (&$ptr1 = null, &$ptr2 = null, int $size): int {}

	/**
	 * {@inheritdoc}
	 * @param FFI\CData $ptr
	 * @param int $value
	 * @param int $size
	 */
	public static function memset (FFI\CData &$ptr, int $value, int $size): void {}

	/**
	 * {@inheritdoc}
	 * @param FFI\CData $ptr
	 * @param int|null $size [optional]
	 */
	public static function string (FFI\CData &$ptr, ?int $size = NULL): string {}

	/**
	 * {@inheritdoc}
	 * @param FFI\CData $ptr
	 */
	public static function isNull (FFI\CData &$ptr): bool {}

}


}


namespace FFI {

final class CData  {
}

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
	 * {@inheritdoc}
	 */
	public function getName (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getKind (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getSize (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getAlignment (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getAttributes (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getEnumKind (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getArrayElementType (): \FFI\CType {}

	/**
	 * {@inheritdoc}
	 */
	public function getArrayLength (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getPointerType (): \FFI\CType {}

	/**
	 * {@inheritdoc}
	 */
	public function getStructFieldNames (): array {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getStructFieldOffset (string $name): int {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getStructFieldType (string $name): \FFI\CType {}

	/**
	 * {@inheritdoc}
	 */
	public function getFuncABI (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getFuncReturnType (): \FFI\CType {}

	/**
	 * {@inheritdoc}
	 */
	public function getFuncParameterCount (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function getFuncParameterType (int $index): \FFI\CType {}

}

}

// End of FFI v.8.3.0
