<?php

namespace PsrExt\Log {

class InvalidArgumentException extends \InvalidArgumentException implements \Throwable, \Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class LogLevel  {
	const EMERGENCY = "emergency";
	const ALERT = "alert";
	const CRITICAL = "critical";
	const ERROR = "error";
	const WARNING = "warning";
	const NOTICE = "notice";
	const INFO = "info";
	const DEBUG = "debug";

}

abstract class AbstractLogger implements \PsrExt\Log\LoggerInterface {

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function emergency ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function alert ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function critical ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function error ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function warning ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function notice ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function info ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function debug ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $level
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function log ($level = null, $message = null, array $context = 'Array')

}

class NullLogger extends \PsrExt\Log\AbstractLogger implements \PsrExt\Log\LoggerInterface {

	/**
	 * @param mixed $level
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function log ($level = null, $message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function emergency ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function alert ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function critical ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function error ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function warning ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function notice ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function info ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function debug ($message = null, array $context = 'Array') {}

}


}


namespace Swoole {

/**
 * @link http://www.php.net/manual/en/class.swoole-coroutine.php
 */
class Coroutine  {

	/**
	 * @param callable $func
	 * @param mixed $param [optional]
	 */
	public static function create (callable $func, mixed ...$param): int|false {}

	/**
	 * @param callable $callback
	 */
	public static function defer (callable $callback): void {}

	/**
	 * @param array[] $options
	 */
	public static function set (array $options): void {}

	public static function getOptions (): ?array {}

	/**
	 * @param int $cid
	 */
	public static function exists (int $cid): bool {}

	public static function yield (): bool {}

	/**
	 * @param int $cid
	 */
	public static function cancel (int $cid): bool {}

	/**
	 * @param array[] $cid_array
	 * @param float $timeout [optional]
	 */
	public static function join (array $cid_array, float $timeout = -1): bool {}

	public static function isCanceled (): bool {}

	public static function suspend (): bool {}

	/**
	 * @param int $cid
	 */
	public static function resume (int $cid): bool {}

	public static function stats (): array {}

	public static function getCid (): int {}

	public static function getuid (): int {}

	/**
	 * @param int $cid [optional]
	 */
	public static function getPcid (int $cid = 0): int|false {}

	/**
	 * @param int $cid [optional]
	 */
	public static function getContext (int $cid = 0): ?Swoole\Coroutine\Context {}

	/**
	 * @param int $cid [optional]
	 * @param int $options [optional]
	 * @param int $limit [optional]
	 */
	public static function getBackTrace (int $cid = 0, int $options = 1, int $limit = 0): array|false {}

	/**
	 * @param int $cid [optional]
	 * @param int $options [optional]
	 * @param int $limit [optional]
	 */
	public static function printBackTrace (int $cid = 0, int $options = 0, int $limit = 0): void {}

	/**
	 * @param int $cid [optional]
	 */
	public static function getElapsed (int $cid = 0): int {}

	/**
	 * @param int $cid [optional]
	 */
	public static function getStackUsage (int $cid = 0): int|false {}

	public static function list (): Swoole\Coroutine\Iterator {}

	public static function listCoroutines (): Swoole\Coroutine\Iterator {}

	public static function enableScheduler (): bool {}

	public static function disableScheduler (): bool {}

	/**
	 * @param string $domain_name
	 * @param int $type [optional]
	 * @param float $timeout [optional]
	 */
	public static function gethostbyname (string $domain_name, int $type = 2, float $timeout = -1): string|false {}

	/**
	 * @param string $domain_name
	 * @param float $timeout [optional]
	 * @param int $type [optional]
	 */
	public static function dnsLookup (string $domain_name, float $timeout = 60, int $type = 2): string|false {}

	/**
	 * @param string $command
	 * @param bool $get_error_stream [optional]
	 */
	public static function exec (string $command, bool $get_error_stream = ''): array|false {}

	/**
	 * @param float $seconds
	 */
	public static function sleep (float $seconds): bool {}

	/**
	 * @param string $domain
	 * @param int $family [optional]
	 * @param int $socktype [optional]
	 * @param int $protocol [optional]
	 * @param string|null $service [optional]
	 * @param float $timeout [optional]
	 */
	public static function getaddrinfo (string $domain, int $family = 2, int $socktype = 1, int $protocol = 6, string|null $service = null, float $timeout = -1): array|bool {}

	/**
	 * @param string $path
	 */
	public static function statvfs (string $path): array {}

	/**
	 * @param string $filename
	 * @param int $flag [optional]
	 */
	public static function readFile (string $filename, int $flag = 0): string|false {}

	/**
	 * @param string $filename
	 * @param string $fileContent
	 * @param int $flags [optional]
	 */
	public static function writeFile (string $filename, string $fileContent, int $flags = 0): int|false {}

	/**
	 * @param float $timeout [optional]
	 */
	public static function wait (float $timeout = -1): array|false {}

	/**
	 * @param int $pid
	 * @param float $timeout [optional]
	 */
	public static function waitPid (int $pid, float $timeout = -1): array|false {}

	/**
	 * @param int $signo
	 * @param float $timeout [optional]
	 */
	public static function waitSignal (int $signo, float $timeout = -1): bool {}

	/**
	 * @param mixed $socket
	 * @param int $events [optional]
	 * @param float $timeout [optional]
	 */
	public static function waitEvent (mixed $socket = null, int $events = 512, float $timeout = -1): int|false {}

	/**
	 * @param mixed $handle
	 * @param int $length [optional]
	 * @deprecated 
	 */
	public static function fread ($handle = null, int $length = 0): string|false {}

	/**
	 * @param mixed $handle
	 * @deprecated 
	 */
	public static function fgets ($handle = null): string|false {}

	/**
	 * @param mixed $handle
	 * @param string $data
	 * @param int $length [optional]
	 * @deprecated 
	 */
	public static function fwrite ($handle = null, string $data, int $length = 0): int|false {}

}


}


namespace Swoole\Coroutine {

class Iterator extends \ArrayIterator implements \Countable, \Serializable, \ArrayAccess, \Iterator, \Traversable, \SeekableIterator {
	const STD_PROP_LIST = 1;
	const ARRAY_AS_PROPS = 2;


	/**
	 * Construct an ArrayIterator
	 * @link http://www.php.net/manual/en/arrayiterator.construct.php
	 * @param object|array $array [optional]
	 * @param int $flags [optional]
	 */
	public function __construct (object|array $array = 'Array', int $flags = 0) {}

	/**
	 * Check if offset exists
	 * @link http://www.php.net/manual/en/arrayiterator.offsetexists.php
	 * @param mixed $key The offset being checked.
	 * @return bool true if the offset exists, otherwise false
	 */
	public function offsetExists ($key) {}

	/**
	 * Get value for an offset
	 * @link http://www.php.net/manual/en/arrayiterator.offsetget.php
	 * @param mixed $key The offset to get the value from.
	 * @return mixed The value at offset key.
	 */
	public function offsetGet ($key) {}

	/**
	 * Set value for an offset
	 * @link http://www.php.net/manual/en/arrayiterator.offsetset.php
	 * @param mixed $key The index to set for.
	 * @param mixed $value The new value to store at the index.
	 * @return void 
	 */
	public function offsetSet ($key, $value) {}

	/**
	 * Unset value for an offset
	 * @link http://www.php.net/manual/en/arrayiterator.offsetunset.php
	 * @param mixed $key The offset to unset.
	 * @return void 
	 */
	public function offsetUnset ($key) {}

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
	 * @return int the behavior flags of the ArrayIterator.
	 */
	public function getFlags () {}

	/**
	 * Set behaviour flags
	 * @link http://www.php.net/manual/en/arrayiterator.setflags.php
	 * @param int $flags <p>
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
	public function setFlags (int $flags) {}

	/**
	 * Sort entries by values
	 * @link http://www.php.net/manual/en/arrayiterator.asort.php
	 * @param int $flags [optional] 
	 * @return true Always returns true.
	 */
	public function asort (int $flags = null) {}

	/**
	 * Sort entries by keys
	 * @link http://www.php.net/manual/en/arrayiterator.ksort.php
	 * @param int $flags [optional] 
	 * @return true Always returns true.
	 */
	public function ksort (int $flags = null) {}

	/**
	 * Sort with a user-defined comparison function and maintain index association
	 * @link http://www.php.net/manual/en/arrayiterator.uasort.php
	 * @param callable $callback sort.callback.description
	 * @return true Always returns true.
	 */
	public function uasort (callable $callback) {}

	/**
	 * Sort by keys using a user-defined comparison function
	 * @link http://www.php.net/manual/en/arrayiterator.uksort.php
	 * @param callable $callback sort.callback.description
	 * @return true Always returns true.
	 */
	public function uksort (callable $callback) {}

	/**
	 * Sort entries naturally
	 * @link http://www.php.net/manual/en/arrayiterator.natsort.php
	 * @return true Always returns true.
	 */
	public function natsort () {}

	/**
	 * Sort entries naturally, case insensitive
	 * @link http://www.php.net/manual/en/arrayiterator.natcasesort.php
	 * @return true Always returns true.
	 */
	public function natcasesort () {}

	/**
	 * Unserialize
	 * @link http://www.php.net/manual/en/arrayiterator.unserialize.php
	 * @param string $data The serialized ArrayIterator object to be unserialized.
	 * @return void 
	 */
	public function unserialize (string $data) {}

	/**
	 * Serialize
	 * @link http://www.php.net/manual/en/arrayiterator.serialize.php
	 * @return string The serialized ArrayIterator.
	 */
	public function serialize () {}

	public function __serialize () {}

	/**
	 * @param array[] $data
	 */
	public function __unserialize (array $data) {}

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
	 * @param int $offset The position to seek to.
	 * @return void 
	 */
	public function seek (int $offset) {}

	public function __debugInfo () {}

}

class Context extends \ArrayObject implements \Countable, \Serializable, \ArrayAccess, \Traversable, \IteratorAggregate {
	const STD_PROP_LIST = 1;
	const ARRAY_AS_PROPS = 2;


	/**
	 * Construct a new array object
	 * @link http://www.php.net/manual/en/arrayobject.construct.php
	 * @param object|array $array [optional]
	 * @param int $flags [optional]
	 * @param string $iteratorClass [optional]
	 */
	public function __construct (object|array $array = 'Array', int $flags = 0, string $iteratorClass = 'ArrayIterator') {}

	/**
	 * Returns whether the requested index exists
	 * @link http://www.php.net/manual/en/arrayobject.offsetexists.php
	 * @param mixed $key The index being checked.
	 * @return bool true if the requested index exists, otherwise false
	 */
	public function offsetExists ($key) {}

	/**
	 * Returns the value at the specified index
	 * @link http://www.php.net/manual/en/arrayobject.offsetget.php
	 * @param mixed $key The index with the value.
	 * @return mixed The value at the specified index or null.
	 */
	public function offsetGet ($key) {}

	/**
	 * Sets the value at the specified index to newval
	 * @link http://www.php.net/manual/en/arrayobject.offsetset.php
	 * @param mixed $key The index being set.
	 * @param mixed $value The new value for the key.
	 * @return void 
	 */
	public function offsetSet ($key, $value) {}

	/**
	 * Unsets the value at the specified index
	 * @link http://www.php.net/manual/en/arrayobject.offsetunset.php
	 * @param mixed $key The index being unset.
	 * @return void 
	 */
	public function offsetUnset ($key) {}

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
	 * an array of the properties of that object will be returned.
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
	 * @param int $flags [optional] 
	 * @return true Always returns true.
	 */
	public function asort (int $flags = null) {}

	/**
	 * Sort the entries by key
	 * @link http://www.php.net/manual/en/arrayobject.ksort.php
	 * @param int $flags [optional] 
	 * @return true Always returns true.
	 */
	public function ksort (int $flags = null) {}

	/**
	 * Sort the entries with a user-defined comparison function and maintain key association
	 * @link http://www.php.net/manual/en/arrayobject.uasort.php
	 * @param callable $callback sort.callback.description
	 * @return true Always returns true.
	 */
	public function uasort (callable $callback) {}

	/**
	 * Sort the entries by keys using a user-defined comparison function
	 * @link http://www.php.net/manual/en/arrayobject.uksort.php
	 * @param callable $callback sort.callback.description
	 * @return true Always returns true.
	 */
	public function uksort (callable $callback) {}

	/**
	 * Sort entries using a "natural order" algorithm
	 * @link http://www.php.net/manual/en/arrayobject.natsort.php
	 * @return true 
	 */
	public function natsort () {}

	/**
	 * Sort an array using a case insensitive "natural order" algorithm
	 * @link http://www.php.net/manual/en/arrayobject.natcasesort.php
	 * @return true 
	 */
	public function natcasesort () {}

	/**
	 * Unserialize an ArrayObject
	 * @link http://www.php.net/manual/en/arrayobject.unserialize.php
	 * @param string $data The serialized ArrayObject.
	 * @return void 
	 */
	public function unserialize (string $data) {}

	/**
	 * Serialize an ArrayObject
	 * @link http://www.php.net/manual/en/arrayobject.serialize.php
	 * @return string The serialized representation of the ArrayObject.
	 */
	public function serialize () {}

	public function __serialize () {}

	/**
	 * @param array[] $data
	 */
	public function __unserialize (array $data) {}

	/**
	 * Create a new iterator from an ArrayObject instance
	 * @link http://www.php.net/manual/en/arrayobject.getiterator.php
	 * @return Iterator An iterator from an ArrayObject.
	 */
	public function getIterator () {}

	/**
	 * Exchange the array for another one
	 * @link http://www.php.net/manual/en/arrayobject.exchangearray.php
	 * @param mixed $array The new array or object to exchange with the current array.
	 * @return array the old array.
	 */
	public function exchangeArray ($array) {}

	/**
	 * Sets the iterator classname for the ArrayObject
	 * @link http://www.php.net/manual/en/arrayobject.setiteratorclass.php
	 * @param string $iteratorClass The classname of the array iterator to use when iterating over this object.
	 * @return void 
	 */
	public function setIteratorClass (string $iteratorClass) {}

	/**
	 * Gets the iterator classname for the ArrayObject
	 * @link http://www.php.net/manual/en/arrayobject.getiteratorclass.php
	 * @return string the iterator class name that is used to iterate over this object.
	 */
	public function getIteratorClass () {}

	public function __debugInfo () {}

}

class System  {

	/**
	 * @param string $domain_name
	 * @param int $type [optional]
	 * @param float $timeout [optional]
	 */
	public static function gethostbyname (string $domain_name, int $type = 2, float $timeout = -1): string|false {}

	/**
	 * @param string $domain_name
	 * @param float $timeout [optional]
	 * @param int $type [optional]
	 */
	public static function dnsLookup (string $domain_name, float $timeout = 60, int $type = 2): string|false {}

	/**
	 * @param string $command
	 * @param bool $get_error_stream [optional]
	 */
	public static function exec (string $command, bool $get_error_stream = ''): array|false {}

	/**
	 * @param float $seconds
	 */
	public static function sleep (float $seconds): bool {}

	/**
	 * @param string $domain
	 * @param int $family [optional]
	 * @param int $socktype [optional]
	 * @param int $protocol [optional]
	 * @param string|null $service [optional]
	 * @param float $timeout [optional]
	 */
	public static function getaddrinfo (string $domain, int $family = 2, int $socktype = 1, int $protocol = 6, string|null $service = null, float $timeout = -1): array|bool {}

	/**
	 * @param string $path
	 */
	public static function statvfs (string $path): array {}

	/**
	 * @param string $filename
	 * @param int $flag [optional]
	 */
	public static function readFile (string $filename, int $flag = 0): string|false {}

	/**
	 * @param string $filename
	 * @param string $fileContent
	 * @param int $flags [optional]
	 */
	public static function writeFile (string $filename, string $fileContent, int $flags = 0): int|false {}

	/**
	 * @param float $timeout [optional]
	 */
	public static function wait (float $timeout = -1): array|false {}

	/**
	 * @param int $pid
	 * @param float $timeout [optional]
	 */
	public static function waitPid (int $pid, float $timeout = -1): array|false {}

	/**
	 * @param int $signo
	 * @param float $timeout [optional]
	 */
	public static function waitSignal (int $signo, float $timeout = -1): bool {}

	/**
	 * @param mixed $socket
	 * @param int $events [optional]
	 * @param float $timeout [optional]
	 */
	public static function waitEvent (mixed $socket = null, int $events = 512, float $timeout = -1): int|false {}

	/**
	 * @param mixed $handle
	 * @param int $length [optional]
	 * @deprecated 
	 */
	public static function fread ($handle = null, int $length = 0): string|false {}

	/**
	 * @param mixed $handle
	 * @param string $data
	 * @param int $length [optional]
	 * @deprecated 
	 */
	public static function fwrite ($handle = null, string $data, int $length = 0): int|false {}

	/**
	 * @param mixed $handle
	 * @deprecated 
	 */
	public static function fgets ($handle = null): string|false {}

}

final class Scheduler  {

	/**
	 * @param callable $func
	 * @param mixed $param [optional]
	 */
	public function add (callable $func, mixed ...$param): void {}

	/**
	 * @param int $n
	 * @param callable $func
	 * @param mixed $param [optional]
	 */
	public function parallel (int $n, callable $func, mixed ...$param): void {}

	/**
	 * @param array[] $settings
	 */
	public function set (array $settings): void {}

	public function getOptions (): ?array {}

	public function start (): bool {}

}

class Channel  {
	public $capacity;
	public $errCode;


	/**
	 * @param int $size [optional]
	 */
	public function __construct (int $size = 1) {}

	/**
	 * @param mixed $data
	 * @param float $timeout [optional]
	 */
	public function push (mixed $data = null, float $timeout = -1): bool {}

	/**
	 * @param float $timeout [optional]
	 */
	public function pop (float $timeout = -1): mixed {}

	public function isEmpty (): bool {}

	public function isFull (): bool {}

	public function close (): bool {}

	public function stats (): array {}

	public function length (): int {}

}

class Channel  {
	public $capacity;
	public $errCode;


	/**
	 * @param int $size [optional]
	 */
	public function __construct (int $size = 1) {}

	/**
	 * @param mixed $data
	 * @param float $timeout [optional]
	 */
	public function push (mixed $data = null, float $timeout = -1): bool {}

	/**
	 * @param float $timeout [optional]
	 */
	public function pop (float $timeout = -1): mixed {}

	public function isEmpty (): bool {}

	public function isFull (): bool {}

	public function close (): bool {}

	public function stats (): array {}

	public function length (): int {}

}

class Socket  {
	public $fd;
	public $domain;
	public $type;
	public $protocol;
	public $errCode;
	public $errMsg;


	/**
	 * @param int $domain
	 * @param int $type
	 * @param int $protocol [optional]
	 */
	public function __construct (int $domain, int $type, int $protocol = 0) {}

	/**
	 * @param string $address
	 * @param int $port [optional]
	 */
	public function bind (string $address, int $port = 0): bool {}

	/**
	 * @param int $backlog [optional]
	 */
	public function listen (int $backlog = 512): bool {}

	/**
	 * @param float $timeout [optional]
	 */
	public function accept (float $timeout = 0): Swoole\Coroutine\Socket|false {}

	/**
	 * @param string $host
	 * @param int $port [optional]
	 * @param float $timeout [optional]
	 */
	public function connect (string $host, int $port = 0, float $timeout = 0): bool {}

	public function checkLiveness (): bool {}

	/**
	 * @param int $event
	 */
	public function getBoundCid (int $event): int {}

	/**
	 * @param int $length [optional]
	 */
	public function peek (int $length = 65536): string|false {}

	/**
	 * @param int $length [optional]
	 * @param float $timeout [optional]
	 */
	public function recv (int $length = 65536, float $timeout = 0): string|false {}

	/**
	 * @param int $length [optional]
	 * @param float $timeout [optional]
	 */
	public function recvAll (int $length = 65536, float $timeout = 0): string|false {}

	/**
	 * @param int $length [optional]
	 * @param float $timeout [optional]
	 */
	public function recvLine (int $length = 65536, float $timeout = 0): string|false {}

	/**
	 * @param int $length [optional]
	 * @param float $timeout [optional]
	 */
	public function recvWithBuffer (int $length = 65536, float $timeout = 0): string|false {}

	/**
	 * @param float $timeout [optional]
	 */
	public function recvPacket (float $timeout = 0): string|false {}

	/**
	 * @param string $data
	 * @param float $timeout [optional]
	 */
	public function send (string $data, float $timeout = 0): int|false {}

	/**
	 * @param array[] $io_vector
	 * @param float $timeout [optional]
	 */
	public function readVector (array $io_vector, float $timeout = 0): array|false {}

	/**
	 * @param array[] $io_vector
	 * @param float $timeout [optional]
	 */
	public function readVectorAll (array $io_vector, float $timeout = 0): array|false {}

	/**
	 * @param array[] $io_vector
	 * @param float $timeout [optional]
	 */
	public function writeVector (array $io_vector, float $timeout = 0): int|false {}

	/**
	 * @param array[] $io_vector
	 * @param float $timeout [optional]
	 */
	public function writeVectorAll (array $io_vector, float $timeout = 0): int|false {}

	/**
	 * @param string $file
	 * @param int $offset [optional]
	 * @param int $length [optional]
	 */
	public function sendFile (string $file, int $offset = 0, int $length = 0): bool {}

	/**
	 * @param string $data
	 * @param float $timeout [optional]
	 */
	public function sendAll (string $data, float $timeout = 0): int|false {}

	/**
	 * @param mixed $peername
	 * @param float $timeout [optional]
	 */
	public function recvfrom (mixed &$peername = null, float $timeout = 0): string|false {}

	/**
	 * @param string $addr
	 * @param int $port
	 * @param string $data
	 */
	public function sendto (string $addr, int $port, string $data): int|false {}

	/**
	 * @param int $level
	 * @param int $opt_name
	 */
	public function getOption (int $level, int $opt_name): mixed {}

	/**
	 * @param array[] $settings
	 */
	public function setProtocol (array $settings): bool {}

	/**
	 * @param int $level
	 * @param int $opt_name
	 * @param mixed $opt_value
	 */
	public function setOption (int $level, int $opt_name, mixed $opt_value = null): bool {}

	public function sslHandshake (): bool {}

	/**
	 * @param int $how [optional]
	 */
	public function shutdown (int $how = 2): bool {}

	/**
	 * @param int $event [optional]
	 */
	public function cancel (int $event = 512): bool {}

	public function close (): bool {}

	public function getpeername (): array|false {}

	public function getsockname (): array|false {}

	public function isClosed (): bool {}

	/**
	 * @param mixed $stream
	 */
	public static function import ($stream = null): Swoole\Coroutine\Socket|false {}

}


}


namespace Swoole\Coroutine\Socket {

class Exception extends \Swoole\Exception implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}


}


namespace Swoole\Coroutine {

/**
 * @link http://www.php.net/manual/en/class.swoole-coroutine-client.php
 */
class Client  {
	const MSG_OOB = 1;
	const MSG_PEEK = 2;
	const MSG_DONTWAIT = 128;
	const MSG_WAITALL = 64;

	public $errCode;
	public $errMsg;
	public $fd;
	public $socket;
	public $type;
	public $setting;
	public $connected;


	/**
	 * @param int $type
	 */
	public function __construct (int $type) {}

	public function __destruct () {}

	/**
	 * @param array[] $settings
	 */
	public function set (array $settings): bool {}

	/**
	 * @param string $host
	 * @param int $port [optional]
	 * @param float $timeout [optional]
	 * @param int $sock_flag [optional]
	 */
	public function connect (string $host, int $port = 0, float $timeout = 0, int $sock_flag = 0): bool {}

	/**
	 * @param float $timeout [optional]
	 */
	public function recv (float $timeout = 0): string|false {}

	/**
	 * @param int $length [optional]
	 */
	public function peek (int $length = 65535): string|false {}

	/**
	 * @param string $data
	 * @param float $timeout [optional]
	 */
	public function send (string $data, float $timeout = 0): int|false {}

	/**
	 * @param string $filename
	 * @param int $offset [optional]
	 * @param int $length [optional]
	 */
	public function sendfile (string $filename, int $offset = 0, int $length = 0): bool {}

	/**
	 * @param string $address
	 * @param int $port
	 * @param string $data
	 */
	public function sendto (string $address, int $port, string $data): bool {}

	/**
	 * @param int $length
	 * @param mixed $address
	 * @param mixed $port [optional]
	 */
	public function recvfrom (int $length, mixed &$address = null, mixed &$port = null): string|false {}

	public function enableSSL (): bool {}

	public function getPeerCert (): string|false {}

	/**
	 * @param bool $allow_self_signed [optional]
	 */
	public function verifyPeerCert (bool $allow_self_signed = ''): bool {}

	public function isConnected (): bool {}

	public function getsockname (): array|false {}

	public function getpeername (): array|false {}

	public function close (): bool {}

	public function exportSocket (): Swoole\Coroutine\Socket|false {}

}


}


namespace Swoole\Coroutine\Http {

/**
 * @link http://www.php.net/manual/en/class.swoole-coroutine-http-client.php
 */
class Client  {
	public $socket;
	public $errCode;
	public $errMsg;
	public $connected;
	public $host;
	public $port;
	public $ssl;
	public $setting;
	public $requestMethod;
	public $requestHeaders;
	public $requestBody;
	public $uploadFiles;
	public $downloadFile;
	public $downloadOffset;
	public $statusCode;
	public $headers;
	public $set_cookie_headers;
	public $cookies;
	public $body;


	/**
	 * @param string $host
	 * @param int $port [optional]
	 * @param bool $ssl [optional]
	 */
	public function __construct (string $host, int $port = 0, bool $ssl = '') {}

	public function __destruct () {}

	/**
	 * @param array[] $settings
	 */
	public function set (array $settings): bool {}

	public function getDefer (): bool {}

	/**
	 * @param bool $defer [optional]
	 */
	public function setDefer (bool $defer = 1): bool {}

	/**
	 * @param string $method
	 */
	public function setMethod (string $method): bool {}

	/**
	 * @param array[] $headers
	 */
	public function setHeaders (array $headers): bool {}

	/**
	 * @param string $username
	 * @param string $password
	 */
	public function setBasicAuth (string $username, string $password): void {}

	/**
	 * @param array[] $cookies
	 */
	public function setCookies (array $cookies): bool {}

	/**
	 * @param array|string $data
	 */
	public function setData (array|string $data): bool {}

	/**
	 * @param string $path
	 * @param string $name
	 * @param string|null $type [optional]
	 * @param string|null $filename [optional]
	 * @param int $offset [optional]
	 * @param int $length [optional]
	 */
	public function addFile (string $path, string $name, string|null $type = null, string|null $filename = null, int $offset = 0, int $length = 0): bool {}

	/**
	 * @param string $path
	 * @param string $name
	 * @param string|null $type [optional]
	 * @param string|null $filename [optional]
	 */
	public function addData (string $path, string $name, string|null $type = null, string|null $filename = null): bool {}

	/**
	 * @param string $path
	 */
	public function execute (string $path): bool {}

	public function getpeername (): array|false {}

	public function getsockname (): array|false {}

	/**
	 * @param string $path
	 */
	public function get (string $path): bool {}

	/**
	 * @param string $path
	 * @param mixed $data
	 */
	public function post (string $path, mixed $data = null): bool {}

	/**
	 * @param string $path
	 * @param string $file
	 * @param int $offset [optional]
	 */
	public function download (string $path, string $file, int $offset = 0): bool {}

	public function getBody (): string|false {}

	public function getHeaders (): array|false|null {}

	public function getCookies (): array|false|null {}

	public function getStatusCode (): int|false {}

	public function getHeaderOut (): string|false {}

	public function getPeerCert (): string|false {}

	/**
	 * @param string $path
	 */
	public function upgrade (string $path): bool {}

	/**
	 * @param mixed $data
	 * @param int $opcode [optional]
	 * @param int $flags [optional]
	 */
	public function push (mixed $data = null, int $opcode = 1, int $flags = 1): bool {}

	/**
	 * @param float $timeout [optional]
	 */
	public function recv (float $timeout = 0): Swoole\WebSocket\Frame|bool {}

	public function close (): bool {}

}


}


namespace Swoole\Coroutine\Http\Client {

class Exception extends \Swoole\Exception implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}


}


namespace Swoole\Coroutine\Http2 {

class Client  {
	public $errCode;
	public $errMsg;
	public $sock;
	public $type;
	public $setting;
	public $socket;
	public $connected;
	public $host;
	public $port;
	public $ssl;


	/**
	 * @param string $host
	 * @param int $port [optional]
	 * @param bool $open_ssl [optional]
	 */
	public function __construct (string $host, int $port = 80, bool $open_ssl = '') {}

	public function __destruct () {}

	/**
	 * @param array[] $settings
	 */
	public function set (array $settings): bool {}

	public function connect (): bool {}

	/**
	 * @param string $key [optional]
	 */
	public function stats (string $key = ''): array|int {}

	/**
	 * @param int $stream_id
	 */
	public function isStreamExist (int $stream_id): bool {}

	/**
	 * @param \Swoole\Http2\Request $request
	 */
	public function send (\Swoole\Http2\Request $request): int|false {}

	/**
	 * @param int $stream_id
	 * @param mixed $data
	 * @param bool $end_stream [optional]
	 */
	public function write (int $stream_id, mixed $data = null, bool $end_stream = ''): bool {}

	/**
	 * @param float $timeout [optional]
	 */
	public function recv (float $timeout = 0): Swoole\Http2\Response|false {}

	/**
	 * @param float $timeout [optional]
	 */
	public function read (float $timeout = 0): Swoole\Http2\Response|false {}

	/**
	 * @param int $error_code [optional]
	 * @param string $debug_data [optional]
	 */
	public function goaway (int $error_code = 0, string $debug_data = ''): bool {}

	public function ping (): bool {}

	public function close (): bool {}

}


}


namespace Swoole\Coroutine\Http2\Client {

class Exception extends \Swoole\Exception implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}


}


namespace Swoole\Coroutine {

/**
 * @link http://www.php.net/manual/en/class.swoole-coroutine-mysql.php
 */
class MySQL  {
	private $socket;
	public $serverInfo;
	public $sock;
	public $connected;
	public $connect_errno;
	public $connect_error;
	public $affected_rows;
	public $insert_id;
	public $error;
	public $errno;


	public function __construct () {}

	public function __destruct () {}

	public function getDefer () {}

	/**
	 * @param mixed $defer [optional]
	 */
	public function setDefer ($defer = null) {}

	/**
	 * @param array[] $server_config [optional]
	 */
	public function connect (array $server_config = null) {}

	/**
	 * @param mixed $sql
	 * @param mixed $timeout [optional]
	 */
	public function query ($sql = null, $timeout = null) {}

	public function fetch () {}

	public function fetchAll () {}

	public function nextResult () {}

	/**
	 * @param mixed $query
	 * @param mixed $timeout [optional]
	 */
	public function prepare ($query = null, $timeout = null) {}

	public function recv () {}

	/**
	 * @param mixed $timeout [optional]
	 */
	public function begin ($timeout = null) {}

	/**
	 * @param mixed $timeout [optional]
	 */
	public function commit ($timeout = null) {}

	/**
	 * @param mixed $timeout [optional]
	 */
	public function rollback ($timeout = null) {}

	public function close () {}

}


}


namespace Swoole\Coroutine\MySQL {

class Statement  {
	public $id;
	public $affected_rows;
	public $insert_id;
	public $error;
	public $errno;


	/**
	 * @param mixed $params [optional]
	 * @param mixed $timeout [optional]
	 */
	public function execute ($params = null, $timeout = null) {}

	/**
	 * @param mixed $timeout [optional]
	 */
	public function fetch ($timeout = null) {}

	/**
	 * @param mixed $timeout [optional]
	 */
	public function fetchAll ($timeout = null) {}

	/**
	 * @param mixed $timeout [optional]
	 */
	public function nextResult ($timeout = null) {}

	/**
	 * @param mixed $timeout [optional]
	 */
	public function recv ($timeout = null) {}

	public function close () {}

}

/**
 * @link http://www.php.net/manual/en/class.swoole-coroutine-mysql-exception.php
 */
class Exception extends \Swoole\Exception implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}


}


namespace Swoole\Coroutine {

class Redis  {
	public $host;
	public $port;
	public $setting;
	public $sock;
	public $connected;
	public $errType;
	public $errCode;
	public $errMsg;


	/**
	 * @param mixed $config [optional]
	 * @deprecated 
	 */
	public function __construct ($config = null) {}

	public function __destruct () {}

	/**
	 * @param mixed $host
	 * @param mixed $port [optional]
	 * @param mixed $serialize [optional]
	 */
	public function connect ($host = null, $port = null, $serialize = null) {}

	public function getAuth () {}

	public function getDBNum () {}

	public function getOptions () {}

	/**
	 * @param mixed $options
	 */
	public function setOptions ($options = null) {}

	public function getDefer () {}

	/**
	 * @param mixed $defer
	 */
	public function setDefer ($defer = null) {}

	public function recv () {}

	/**
	 * @param array[] $params
	 */
	public function request (array $params) {}

	public function close () {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $timeout [optional]
	 * @param mixed $opt [optional]
	 */
	public function set ($key = null, $value = null, $timeout = null, $opt = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function setBit ($key = null, $offset = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $expire
	 * @param mixed $value
	 */
	public function setEx ($key = null, $expire = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $expire
	 * @param mixed $value
	 */
	public function psetEx ($key = null, $expire = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $index
	 * @param mixed $value
	 */
	public function lSet ($key = null, $index = null, $value = null) {}

	/**
	 * @param mixed $key
	 */
	public function get ($key = null) {}

	/**
	 * @param mixed $keys
	 */
	public function mGet ($keys = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function del ($key = null, $other_keys = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function hDel ($key = null, $member = null, $other_members = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hSet ($key = null, $member = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $pairs
	 */
	public function hMSet ($key = null, $pairs = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hSetNx ($key = null, $member = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function delete ($key = null, $other_keys = null) {}

	/**
	 * @param mixed $pairs
	 */
	public function mSet ($pairs = null) {}

	/**
	 * @param mixed $pairs
	 */
	public function mSetNx ($pairs = null) {}

	/**
	 * @param mixed $pattern
	 */
	public function getKeys ($pattern = null) {}

	/**
	 * @param mixed $pattern
	 */
	public function keys ($pattern = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function exists ($key = null, $other_keys = null) {}

	/**
	 * @param mixed $key
	 */
	public function type ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function strLen ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function lPop ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function blPop ($key = null, $timeout_or_key = null, $extra_args = null) {}

	/**
	 * @param mixed $key
	 */
	public function rPop ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function brPop ($key = null, $timeout_or_key = null, $extra_args = null) {}

	/**
	 * @param mixed $src
	 * @param mixed $dst
	 * @param mixed $timeout
	 */
	public function bRPopLPush ($src = null, $dst = null, $timeout = null) {}

	/**
	 * @param mixed $key
	 */
	public function lSize ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function lLen ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function sSize ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function scard ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function sPop ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function sMembers ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function sGetMembers ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $count [optional]
	 */
	public function sRandMember ($key = null, $count = null) {}

	/**
	 * @param mixed $key
	 */
	public function persist ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function ttl ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function pttl ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function zCard ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function zSize ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function hLen ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function hKeys ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function hVals ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function hGetAll ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function debug ($key = null) {}

	/**
	 * @param mixed $ttl
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function restore ($ttl = null, $key = null, $value = null) {}

	/**
	 * @param mixed $key
	 */
	public function dump ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $newkey
	 */
	public function renameKey ($key = null, $newkey = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $newkey
	 */
	public function rename ($key = null, $newkey = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $newkey
	 */
	public function renameNx ($key = null, $newkey = null) {}

	/**
	 * @param mixed $src
	 * @param mixed $dst
	 */
	public function rpoplpush ($src = null, $dst = null) {}

	public function randomKey () {}

	/**
	 * @param mixed $key
	 * @param mixed $elements
	 */
	public function pfadd ($key = null, $elements = null) {}

	/**
	 * @param mixed $key
	 */
	public function pfcount ($key = null) {}

	/**
	 * @param mixed $dstkey
	 * @param mixed $keys
	 */
	public function pfmerge ($dstkey = null, $keys = null) {}

	public function ping () {}

	/**
	 * @param mixed $password
	 */
	public function auth ($password = null) {}

	public function unwatch () {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function watch ($key = null, $other_keys = null) {}

	public function save () {}

	public function bgSave () {}

	public function lastSave () {}

	public function flushDB () {}

	public function flushAll () {}

	public function dbSize () {}

	public function bgrewriteaof () {}

	public function time () {}

	public function role () {}

	/**
	 * @param mixed $key
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function setRange ($key = null, $offset = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function setNx ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function getSet ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function append ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function lPushx ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function lPush ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function rPush ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function rPushx ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function sContains ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function sismember ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function zScore ($key = null, $member = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function zRank ($key = null, $member = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function zRevRank ($key = null, $member = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function hGet ($key = null, $member = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $keys
	 */
	public function hMGet ($key = null, $keys = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function hExists ($key = null, $member = null) {}

	/**
	 * @param mixed $channel
	 * @param mixed $message
	 */
	public function publish ($channel = null, $message = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $member
	 */
	public function zIncrBy ($key = null, $value = null, $member = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $score
	 * @param mixed $value
	 */
	public function zAdd ($key = null, $score = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $count
	 */
	public function zPopMin ($key = null, $count = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $count
	 */
	public function zPopMax ($key = null, $count = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function bzPopMin ($key = null, $timeout_or_key = null, $extra_args = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function bzPopMax ($key = null, $timeout_or_key = null, $extra_args = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zDeleteRangeByScore ($key = null, $min = null, $max = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zRemRangeByScore ($key = null, $min = null, $max = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zCount ($key = null, $min = null, $max = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param mixed $scores [optional]
	 */
	public function zRange ($key = null, $start = null, $end = null, $scores = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param mixed $scores [optional]
	 */
	public function zRevRange ($key = null, $start = null, $end = null, $scores = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param mixed $options [optional]
	 */
	public function zRangeByScore ($key = null, $start = null, $end = null, $options = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param mixed $options [optional]
	 */
	public function zRevRangeByScore ($key = null, $start = null, $end = null, $options = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 * @param mixed $offset [optional]
	 * @param mixed $limit [optional]
	 */
	public function zRangeByLex ($key = null, $min = null, $max = null, $offset = null, $limit = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 * @param mixed $offset [optional]
	 * @param mixed $limit [optional]
	 */
	public function zRevRangeByLex ($key = null, $min = null, $max = null, $offset = null, $limit = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $keys
	 * @param mixed $weights [optional]
	 * @param mixed $aggregate [optional]
	 */
	public function zInter ($key = null, $keys = null, $weights = null, $aggregate = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $keys
	 * @param mixed $weights [optional]
	 * @param mixed $aggregate [optional]
	 */
	public function zinterstore ($key = null, $keys = null, $weights = null, $aggregate = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $keys
	 * @param mixed $weights [optional]
	 * @param mixed $aggregate [optional]
	 */
	public function zUnion ($key = null, $keys = null, $weights = null, $aggregate = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $keys
	 * @param mixed $weights [optional]
	 * @param mixed $aggregate [optional]
	 */
	public function zunionstore ($key = null, $keys = null, $weights = null, $aggregate = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function incrBy ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hIncrBy ($key = null, $member = null, $value = null) {}

	/**
	 * @param mixed $key
	 */
	public function incr ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function decrBy ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 */
	public function decr ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $offset
	 */
	public function getBit ($key = null, $offset = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $position
	 * @param mixed $pivot
	 * @param mixed $value
	 */
	public function lInsert ($key = null, $position = null, $pivot = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $index
	 */
	public function lGet ($key = null, $index = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $integer
	 */
	public function lIndex ($key = null, $integer = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timeout
	 */
	public function setTimeout ($key = null, $timeout = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $integer
	 */
	public function expire ($key = null, $integer = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timestamp
	 */
	public function pexpire ($key = null, $timestamp = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timestamp
	 */
	public function expireAt ($key = null, $timestamp = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timestamp
	 */
	public function pexpireAt ($key = null, $timestamp = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $dbindex
	 */
	public function move ($key = null, $dbindex = null) {}

	/**
	 * @param mixed $dbindex
	 */
	public function select ($dbindex = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 */
	public function getRange ($key = null, $start = null, $end = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $stop
	 */
	public function listTrim ($key = null, $start = null, $stop = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $stop
	 */
	public function ltrim ($key = null, $start = null, $stop = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 */
	public function lGetRange ($key = null, $start = null, $end = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 */
	public function lRange ($key = null, $start = null, $end = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $count
	 */
	public function lRem ($key = null, $value = null, $count = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $count
	 */
	public function lRemove ($key = null, $value = null, $count = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 */
	public function zDeleteRangeByRank ($key = null, $start = null, $end = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zRemRangeByRank ($key = null, $min = null, $max = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function incrByFloat ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hIncrByFloat ($key = null, $member = null, $value = null) {}

	/**
	 * @param mixed $key
	 */
	public function bitCount ($key = null) {}

	/**
	 * @param mixed $operation
	 * @param mixed $ret_key
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function bitOp ($operation = null, $ret_key = null, $key = null, $other_keys = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function sAdd ($key = null, $value = null) {}

	/**
	 * @param mixed $src
	 * @param mixed $dst
	 * @param mixed $value
	 */
	public function sMove ($src = null, $dst = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sDiff ($key = null, $other_keys = null) {}

	/**
	 * @param mixed $dst
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sDiffStore ($dst = null, $key = null, $other_keys = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sUnion ($key = null, $other_keys = null) {}

	/**
	 * @param mixed $dst
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sUnionStore ($dst = null, $key = null, $other_keys = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sInter ($key = null, $other_keys = null) {}

	/**
	 * @param mixed $dst
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sInterStore ($dst = null, $key = null, $other_keys = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function sRemove ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function srem ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function zDelete ($key = null, $member = null, $other_members = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function zRemove ($key = null, $member = null, $other_members = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function zRem ($key = null, $member = null, $other_members = null) {}

	/**
	 * @param mixed $patterns
	 */
	public function pSubscribe ($patterns = null) {}

	/**
	 * @param mixed $channels
	 */
	public function subscribe ($channels = null) {}

	/**
	 * @param mixed $channels
	 */
	public function unsubscribe ($channels = null) {}

	/**
	 * @param mixed $patterns
	 */
	public function pUnSubscribe ($patterns = null) {}

	public function multi () {}

	public function exec () {}

	/**
	 * @param mixed $script
	 * @param mixed $args [optional]
	 * @param mixed $num_keys [optional]
	 */
	public function eval ($script = null, $args = null, $num_keys = null) {}

	/**
	 * @param mixed $script_sha
	 * @param mixed $args [optional]
	 * @param mixed $num_keys [optional]
	 */
	public function evalSha ($script_sha = null, $args = null, $num_keys = null) {}

	/**
	 * @param mixed $cmd
	 * @param mixed $args [optional]
	 */
	public function script ($cmd = null, $args = null) {}

	/**
	 * @param mixed $key
	 */
	public function xLen ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $id
	 * @param mixed $pairs
	 * @param mixed $options [optional]
	 */
	public function xAdd ($key = null, $id = null, $pairs = null, $options = null) {}

	/**
	 * @param mixed $streams
	 * @param mixed $options [optional]
	 */
	public function xRead ($streams = null, $options = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $id
	 */
	public function xDel ($key = null, $id = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param mixed $count [optional]
	 */
	public function xRange ($key = null, $start = null, $end = null, $count = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param mixed $count [optional]
	 */
	public function xRevRange ($key = null, $start = null, $end = null, $count = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $options [optional]
	 */
	public function xTrim ($key = null, $options = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $group_name
	 * @param mixed $id
	 * @param mixed $mkstream [optional]
	 */
	public function xGroupCreate ($key = null, $group_name = null, $id = null, $mkstream = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $group_name
	 * @param mixed $id
	 */
	public function xGroupSetId ($key = null, $group_name = null, $id = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $group_name
	 */
	public function xGroupDestroy ($key = null, $group_name = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $group_name
	 * @param mixed $consumer_name
	 */
	public function xGroupCreateConsumer ($key = null, $group_name = null, $consumer_name = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $group_name
	 * @param mixed $consumer_name
	 */
	public function xGroupDelConsumer ($key = null, $group_name = null, $consumer_name = null) {}

	/**
	 * @param mixed $group_name
	 * @param mixed $consumer_name
	 * @param mixed $streams
	 * @param mixed $options [optional]
	 */
	public function xReadGroup ($group_name = null, $consumer_name = null, $streams = null, $options = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $group_name
	 * @param mixed $options [optional]
	 */
	public function xPending ($key = null, $group_name = null, $options = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $group_name
	 * @param mixed $id
	 */
	public function xAck ($key = null, $group_name = null, $id = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $group_name
	 * @param mixed $consumer_name
	 * @param mixed $min_idle_time
	 * @param mixed $id
	 * @param mixed $options [optional]
	 */
	public function xClaim ($key = null, $group_name = null, $consumer_name = null, $min_idle_time = null, $id = null, $options = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $group_name
	 * @param mixed $consumer_name
	 * @param mixed $min_idle_time
	 * @param mixed $start
	 * @param mixed $options [optional]
	 */
	public function xAutoClaim ($key = null, $group_name = null, $consumer_name = null, $min_idle_time = null, $start = null, $options = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $group_name
	 */
	public function xInfoConsumers ($key = null, $group_name = null) {}

	/**
	 * @param mixed $key
	 */
	public function xInfoGroups ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function xInfoStream ($key = null) {}

}


}


namespace Swoole\Coroutine\Http {

final class Server  {
	public $fd;
	public $host;
	public $port;
	public $ssl;
	public $settings;
	public $errCode;
	public $errMsg;


	/**
	 * @param string $host
	 * @param int $port [optional]
	 * @param bool $ssl [optional]
	 * @param bool $reuse_port [optional]
	 */
	public function __construct (string $host, int $port = 0, bool $ssl = '', bool $reuse_port = '') {}

	public function __destruct () {}

	/**
	 * @param array[] $settings
	 */
	public function set (array $settings): bool {}

	/**
	 * @param string $pattern
	 * @param callable $callback
	 */
	public function handle (string $pattern, callable $callback): void {}

	private function onAccept (): void {}

	public function start (): bool {}

	public function shutdown (): void {}

}


}


namespace Swoole {

class Constant  {
	const EVENT_START = "start";
	const EVENT_BEFORE_SHUTDOWN = "beforeShutdown";
	const EVENT_SHUTDOWN = "shutdown";
	const EVENT_WORKER_START = "workerStart";
	const EVENT_WORKER_STOP = "workerStop";
	const EVENT_BEFORE_RELOAD = "beforeReload";
	const EVENT_AFTER_RELOAD = "afterReload";
	const EVENT_TASK = "task";
	const EVENT_FINISH = "finish";
	const EVENT_WORKER_EXIT = "workerExit";
	const EVENT_WORKER_ERROR = "workerError";
	const EVENT_MANAGER_START = "managerStart";
	const EVENT_MANAGER_STOP = "managerStop";
	const EVENT_PIPE_MESSAGE = "pipeMessage";
	const EVENT_CONNECT = "connect";
	const EVENT_RECEIVE = "receive";
	const EVENT_CLOSE = "close";
	const EVENT_PACKET = "packet";
	const EVENT_BUFFER_FULL = "bufferFull";
	const EVENT_BUFFER_EMPTY = "bufferEmpty";
	const EVENT_REQUEST = "request";
	const EVENT_HANDSHAKE = "handshake";
	const EVENT_BEFORE_HANDSHAKE_RESPONSE = "beforeHandshakeResponse";
	const EVENT_OPEN = "open";
	const EVENT_MESSAGE = "message";
	const EVENT_DISCONNECT = "disconnect";
	const EVENT_ERROR = "error";
	const OPTION_DEBUG_MODE = "debug_mode";
	const OPTION_TRACE_FLAGS = "trace_flags";
	const OPTION_LOG_FILE = "log_file";
	const OPTION_LOG_LEVEL = "log_level";
	const OPTION_LOG_DATE_FORMAT = "log_date_format";
	const OPTION_LOG_DATE_WITH_MICROSECONDS = "log_date_with_microseconds";
	const OPTION_LOG_ROTATION = "log_rotation";
	const OPTION_DISPLAY_ERRORS = "display_errors";
	const OPTION_DNS_SERVER = "dns_server";
	const OPTION_SOCKET_DNS_TIMEOUT = "socket_dns_timeout";
	const OPTION_SOCKET_CONNECT_TIMEOUT = "socket_connect_timeout";
	const OPTION_SOCKET_WRITE_TIMEOUT = "socket_write_timeout";
	const OPTION_SOCKET_SEND_TIMEOUT = "socket_send_timeout";
	const OPTION_SOCKET_READ_TIMEOUT = "socket_read_timeout";
	const OPTION_SOCKET_RECV_TIMEOUT = "socket_recv_timeout";
	const OPTION_SOCKET_BUFFER_SIZE = "socket_buffer_size";
	const OPTION_SOCKET_TIMEOUT = "socket_timeout";
	const OPTION_HTTP2_HEADER_TABLE_SIZE = "http2_header_table_size";
	const OPTION_HTTP2_ENABLE_PUSH = "http2_enable_push";
	const OPTION_HTTP2_MAX_CONCURRENT_STREAMS = "http2_max_concurrent_streams";
	const OPTION_HTTP2_INIT_WINDOW_SIZE = "http2_init_window_size";
	const OPTION_HTTP2_MAX_FRAME_SIZE = "http2_max_frame_size";
	const OPTION_HTTP2_MAX_HEADER_LIST_SIZE = "http2_max_header_list_size";
	const OPTION_AIO_CORE_WORKER_NUM = "aio_core_worker_num";
	const OPTION_AIO_WORKER_NUM = "aio_worker_num";
	const OPTION_AIO_MAX_WAIT_TIME = "aio_max_wait_time";
	const OPTION_AIO_MAX_IDLE_TIME = "aio_max_idle_time";
	const OPTION_ENABLE_SIGNALFD = "enable_signalfd";
	const OPTION_WAIT_SIGNAL = "wait_signal";
	const OPTION_DNS_CACHE_REFRESH_TIME = "dns_cache_refresh_time";
	const OPTION_THREAD_NUM = "thread_num";
	const OPTION_MIN_THREAD_NUM = "min_thread_num";
	const OPTION_MAX_THREAD_NUM = "max_thread_num";
	const OPTION_SOCKET_DONTWAIT = "socket_dontwait";
	const OPTION_DNS_LOOKUP_RANDOM = "dns_lookup_random";
	const OPTION_USE_ASYNC_RESOLVER = "use_async_resolver";
	const OPTION_ENABLE_COROUTINE = "enable_coroutine";
	const OPTION_SSL_PROTOCOLS = "ssl_protocols";
	const OPTION_SSL_COMPRESS = "ssl_compress";
	const OPTION_SSL_CERT_FILE = "ssl_cert_file";
	const OPTION_SSL_KEY_FILE = "ssl_key_file";
	const OPTION_SSL_PASSPHRASE = "ssl_passphrase";
	const OPTION_SSL_HOST_NAME = "ssl_host_name";
	const OPTION_SSL_VERIFY_PEER = "ssl_verify_peer";
	const OPTION_SSL_ALLOW_SELF_SIGNED = "ssl_allow_self_signed";
	const OPTION_SSL_CAFILE = "ssl_cafile";
	const OPTION_SSL_CAPATH = "ssl_capath";
	const OPTION_SSL_VERIFY_DEPTH = "ssl_verify_depth";
	const OPTION_SSL_CIPHERS = "ssl_ciphers";
	const OPTION_OPEN_EOF_CHECK = "open_eof_check";
	const OPTION_OPEN_EOF_SPLIT = "open_eof_split";
	const OPTION_PACKAGE_EOF = "package_eof";
	const OPTION_OPEN_MQTT_PROTOCOL = "open_mqtt_protocol";
	const OPTION_OPEN_LENGTH_CHECK = "open_length_check";
	const OPTION_PACKAGE_LENGTH_TYPE = "package_length_type";
	const OPTION_PACKAGE_LENGTH_OFFSET = "package_length_offset";
	const OPTION_PACKAGE_BODY_OFFSET = "package_body_offset";
	const OPTION_PACKAGE_LENGTH_FUNC = "package_length_func";
	const OPTION_PACKAGE_MAX_LENGTH = "package_max_length";
	const OPTION_BUFFER_HIGH_WATERMARK = "buffer_high_watermark";
	const OPTION_BUFFER_LOW_WATERMARK = "buffer_low_watermark";
	const OPTION_BIND_PORT = "bind_port";
	const OPTION_BIND_ADDRESS = "bind_address";
	const OPTION_OPEN_TCP_NODELAY = "open_tcp_nodelay";
	const OPTION_SOCKS5_HOST = "socks5_host";
	const OPTION_SOCKS5_PORT = "socks5_port";
	const OPTION_SOCKS5_USERNAME = "socks5_username";
	const OPTION_SOCKS5_PASSWORD = "socks5_password";
	const OPTION_HTTP_PROXY_HOST = "http_proxy_host";
	const OPTION_HTTP_PROXY_PORT = "http_proxy_port";
	const OPTION_HTTP_PROXY_USERNAME = "http_proxy_username";
	const OPTION_HTTP_PROXY_USER = "http_proxy_user";
	const OPTION_HTTP_PROXY_PASSWORD = "http_proxy_password";
	const OPTION_MAX_CORO_NUM = "max_coro_num";
	const OPTION_MAX_COROUTINE = "max_coroutine";
	const OPTION_ENABLE_DEADLOCK_CHECK = "enable_deadlock_check";
	const OPTION_HOOK_FLAGS = "hook_flags";
	const OPTION_ENABLE_PREEMPTIVE_SCHEDULER = "enable_preemptive_scheduler";
	const OPTION_C_STACK_SIZE = "c_stack_size";
	const OPTION_STACK_SIZE = "stack_size";
	const OPTION_NAME_RESOLVER = "name_resolver";
	const OPTION_DNS_CACHE_EXPIRE = "dns_cache_expire";
	const OPTION_DNS_CACHE_CAPACITY = "dns_cache_capacity";
	const OPTION_MAX_CONCURRENCY = "max_concurrency";
	const OPTION_CONNECT_TIMEOUT = "connect_timeout";
	const OPTION_TIMEOUT = "timeout";
	const OPTION_MAX_RETRIES = "max_retries";
	const OPTION_DEFER = "defer";
	const OPTION_LOWERCASE_HEADER = "lowercase_header";
	const OPTION_KEEP_ALIVE = "keep_alive";
	const OPTION_WEBSOCKET_MASK = "websocket_mask";
	const OPTION_HTTP_COMPRESSION = "http_compression";
	const OPTION_BODY_DECOMPRESSION = "body_decompression";
	const OPTION_WEBSOCKET_COMPRESSION = "websocket_compression";
	const OPTION_HTTP_PARSE_COOKIE = "http_parse_cookie";
	const OPTION_HTTP_PARSE_POST = "http_parse_post";
	const OPTION_HTTP_PARSE_FILES = "http_parse_files";
	const OPTION_HTTP_COMPRESSION_LEVEL = "http_compression_level";
	const OPTION_COMPRESSION_LEVEL = "compression_level";
	const OPTION_HTTP_GZIP_LEVEL = "http_gzip_level";
	const OPTION_HTTP_COMPRESSION_MIN_LENGTH = "http_compression_min_length";
	const OPTION_COMPRESSION_MIN_LENGTH = "compression_min_length";
	const OPTION_HTTP_COMPRESSION_TYPES = "http_compression_types";
	const OPTION_COMPRESSION_TYPES = "compression_types";
	const OPTION_UPLOAD_TMP_DIR = "upload_tmp_dir";
	const OPTION_HOST = "host";
	const OPTION_PORT = "port";
	const OPTION_SSL = "ssl";
	const OPTION_USER = "user";
	const OPTION_PASSWORD = "password";
	const OPTION_DATABASE = "database";
	const OPTION_CHARSET = "charset";
	const OPTION_STRICT_TYPE = "strict_type";
	const OPTION_FETCH_MODE = "fetch_mode";
	const OPTION_ENABLE_MESSAGE_BUS = "enable_message_bus";
	const OPTION_MAX_PACKAGE_SIZE = "max_package_size";
	const OPTION_SERIALIZE = "serialize";
	const OPTION_RECONNECT = "reconnect";
	const OPTION_COMPATIBILITY_MODE = "compatibility_mode";
	const OPTION_CHROOT = "chroot";
	const OPTION_GROUP = "group";
	const OPTION_DAEMONIZE = "daemonize";
	const OPTION_PID_FILE = "pid_file";
	const OPTION_REACTOR_NUM = "reactor_num";
	const OPTION_SINGLE_THREAD = "single_thread";
	const OPTION_WORKER_NUM = "worker_num";
	const OPTION_MAX_WAIT_TIME = "max_wait_time";
	const OPTION_MAX_QUEUED_BYTES = "max_queued_bytes";
	const OPTION_WORKER_MAX_CONCURRENCY = "worker_max_concurrency";
	const OPTION_SEND_TIMEOUT = "send_timeout";
	const OPTION_DISPATCH_MODE = "dispatch_mode";
	const OPTION_SEND_YIELD = "send_yield";
	const OPTION_DISPATCH_FUNC = "dispatch_func";
	const OPTION_DISCARD_TIMEOUT_REQUEST = "discard_timeout_request";
	const OPTION_ENABLE_UNSAFE_EVENT = "enable_unsafe_event";
	const OPTION_ENABLE_DELAY_RECEIVE = "enable_delay_receive";
	const OPTION_ENABLE_REUSE_PORT = "enable_reuse_port";
	const OPTION_TASK_USE_OBJECT = "task_use_object";
	const OPTION_TASK_OBJECT = "task_object";
	const OPTION_EVENT_OBJECT = "event_object";
	const OPTION_TASK_ENABLE_COROUTINE = "task_enable_coroutine";
	const OPTION_TASK_WORKER_NUM = "task_worker_num";
	const OPTION_TASK_IPC_MODE = "task_ipc_mode";
	const OPTION_TASK_TMPDIR = "task_tmpdir";
	const OPTION_TASK_MAX_REQUEST = "task_max_request";
	const OPTION_TASK_MAX_REQUEST_GRACE = "task_max_request_grace";
	const OPTION_MAX_CONNECTION = "max_connection";
	const OPTION_MAX_CONN = "max_conn";
	const OPTION_START_SESSION_ID = "start_session_id";
	const OPTION_HEARTBEAT_CHECK_INTERVAL = "heartbeat_check_interval";
	const OPTION_HEARTBEAT_IDLE_TIME = "heartbeat_idle_time";
	const OPTION_MAX_REQUEST = "max_request";
	const OPTION_MAX_REQUEST_GRACE = "max_request_grace";
	const OPTION_RELOAD_ASYNC = "reload_async";
	const OPTION_OPEN_CPU_AFFINITY = "open_cpu_affinity";
	const OPTION_CPU_AFFINITY_IGNORE = "cpu_affinity_ignore";
	const OPTION_UPLOAD_MAX_FILESIZE = "upload_max_filesize";
	const OPTION_ENABLE_STATIC_HANDLER = "enable_static_handler";
	const OPTION_DOCUMENT_ROOT = "document_root";
	const OPTION_HTTP_AUTOINDEX = "http_autoindex";
	const OPTION_HTTP_INDEX_FILES = "http_index_files";
	const OPTION_STATIC_HANDLER_LOCATIONS = "static_handler_locations";
	const OPTION_INPUT_BUFFER_SIZE = "input_buffer_size";
	const OPTION_BUFFER_INPUT_SIZE = "buffer_input_size";
	const OPTION_OUTPUT_BUFFER_SIZE = "output_buffer_size";
	const OPTION_BUFFER_OUTPUT_SIZE = "buffer_output_size";
	const OPTION_MESSAGE_QUEUE_KEY = "message_queue_key";
	const OPTION_BACKLOG = "backlog";
	const OPTION_KERNEL_SOCKET_RECV_BUFFER_SIZE = "kernel_socket_recv_buffer_size";
	const OPTION_KERNEL_SOCKET_SEND_BUFFER_SIZE = "kernel_socket_send_buffer_size";
	const OPTION_TCP_DEFER_ACCEPT = "tcp_defer_accept";
	const OPTION_OPEN_TCP_KEEPALIVE = "open_tcp_keepalive";
	const OPTION_OPEN_HTTP_PROTOCOL = "open_http_protocol";
	const OPTION_OPEN_WEBSOCKET_PROTOCOL = "open_websocket_protocol";
	const OPTION_WEBSOCKET_SUBPROTOCOL = "websocket_subprotocol";
	const OPTION_OPEN_WEBSOCKET_CLOSE_FRAME = "open_websocket_close_frame";
	const OPTION_OPEN_WEBSOCKET_PING_FRAME = "open_websocket_ping_frame";
	const OPTION_OPEN_WEBSOCKET_PONG_FRAME = "open_websocket_pong_frame";
	const OPTION_OPEN_HTTP2_PROTOCOL = "open_http2_protocol";
	const OPTION_OPEN_REDIS_PROTOCOL = "open_redis_protocol";
	const OPTION_MAX_IDLE_TIME = "max_idle_time";
	const OPTION_TCP_KEEPIDLE = "tcp_keepidle";
	const OPTION_TCP_KEEPINTERVAL = "tcp_keepinterval";
	const OPTION_TCP_KEEPCOUNT = "tcp_keepcount";
	const OPTION_TCP_USER_TIMEOUT = "tcp_user_timeout";
	const OPTION_TCP_FASTOPEN = "tcp_fastopen";
	const OPTION_PACKAGE_BODY_START = "package_body_start";
	const OPTION_SSL_CLIENT_CERT_FILE = "ssl_client_cert_file";
	const OPTION_SSL_PREFER_SERVER_CIPHERS = "ssl_prefer_server_ciphers";
	const OPTION_SSL_ECDH_CURVE = "ssl_ecdh_curve";
	const OPTION_SSL_DHPARAM = "ssl_dhparam";
	const OPTION_SSL_SNI_CERTS = "ssl_sni_certs";
	const OPTION_OPEN_SSL = "open_ssl";
	const OPTION_OPEN_FASTCGI_PROTOCOL = "open_fastcgi_protocol";
	const OPTION_READ_TIMEOUT = "read_timeout";
	const OPTION_WRITE_TIMEOUT = "write_timeout";
	const OPTION_SSL_DISABLE_COMPRESSION = "ssl_disable_compression";
	const OPTION_SSL_GREASE = "ssl_grease";
	const OPTION_EXIT_CONDITION = "exit_condition";
	const OPTION_DEADLOCK_CHECK_DISABLE_TRACE = "deadlock_check_disable_trace";
	const OPTION_DEADLOCK_CHECK_LIMIT = "deadlock_check_limit";
	const OPTION_DEADLOCK_CHECK_DEPTH = "deadlock_check_depth";
	const OPTION_STATS_FILE = "stats_file";
	const OPTION_STATS_TIMER_INTERVAL = "stats_timer_interval";
	const OPTION_ADMIN_SERVER = "admin_server";
	const OPTION_HTTP_CLIENT_DRIVER = "http_client_driver";

}

class StringObject implements \Stringable {
	/**
     * @var string
     */
	protected $string;


	/**
     * StringObject constructor.
     */
	public function __construct (string $string = '') {}

	public function __toString (): string {}

	/**
	 * @param string $string [optional]
	 */
	public static function from (string $string = ''): self {}

	public function length (): int {}

	/**
     * @return false|int
     */
	public function indexOf (string $needle, int $offset = 0) {}

	/**
     * @return false|int
     */
	public function lastIndexOf (string $needle, int $offset = 0) {}

	/**
     * @return false|int
     */
	public function pos (string $needle, int $offset = 0) {}

	/**
     * @return false|int
     */
	public function rpos (string $needle, int $offset = 0) {}

	/**
     * @return static
     */
	public function reverse (): self {}

	/**
     * @return false|int
     */
	public function ipos (string $needle) {}

	/**
     * @return static
     */
	public function lower (): self {}

	/**
     * @return static
     */
	public function upper (): self {}

	/**
     * @param mixed $characters
     * @return static
     */
	public function trim ($characters = null): self {}

	/**
     * @return static
     */
	public function ltrim (): self {}

	/**
     * @return static
     */
	public function rtrim (): self {}

	/**
     * @return static
     */
	public function substr (int $offset, int|null $length = null) {}

	/**
     * @return static
     */
	public function repeat (int $times): self {}

	/**
     * @param mixed $str
     * @return static
     */
	public function append ($str = null): self {}

	/**
     * @param null|int $count
     * @return static
     */
	public function replace (string $search, string $replace, &$count = null): self {}

	/**
	 * @param string $needle
	 */
	public function startsWith (string $needle): bool {}

	/**
	 * @param string $needle
	 */
	public function endsWith (string $needle): bool {}

	/**
	 * @param mixed $str
	 * @param bool $strict [optional]
	 */
	public function equals ($str = null, bool $strict = ''): bool {}

	/**
	 * @param string $subString
	 */
	public function contains (string $subString): bool {}

	/**
	 * @param string $delimiter
	 * @param int $limit [optional]
	 */
	public function split (string $delimiter, int $limit = 9223372036854775807): Swoole\ArrayObject {}

	/**
	 * @param int $index
	 */
	public function char (int $index): string {}

	/**
     * @return static
     */
	public function chunkSplit (int $chunkLength = 76, string $chunkEnd = ''): self {}

	/**
	 * @param int $splitLength [optional]
	 */
	public function chunk (int $splitLength = 1): Swoole\ArrayObject {}

	public function toString (): string {}

	/**
	 * @param array[] $value
	 */
	protected static function detectArrayType (array $value): Swoole\ArrayObject {}

}

class MultibyteStringObject extends \Swoole\StringObject implements \Stringable {
	/**
     * @var string
     */
	protected $string;


	public function length (): int {}

	/**
     * @return false|int
     */
	public function indexOf (string $needle, int $offset = 0, string|null $encoding = null) {}

	/**
     * @return false|int
     */
	public function lastIndexOf (string $needle, int $offset = 0, string|null $encoding = null) {}

	/**
     * @return false|int
     */
	public function pos (string $needle, int $offset = 0, string|null $encoding = null) {}

	/**
     * @return false|int
     */
	public function rpos (string $needle, int $offset = 0, string|null $encoding = null) {}

	/**
     * @return false|int
     */
	public function ipos (string $needle, string|null $encoding = null) {}

	/**
     * @return static
     */
	public function substr (int $offset, int|null $length = null, string|null $encoding = null) {}

	/**
	 * @param int $splitLength [optional]
	 * @param int|null $limit [optional]
	 */
	public function chunk (int $splitLength = 1, int|null $limit = null): Swoole\ArrayObject {}

	/**
     * StringObject constructor.
     */
	public function __construct (string $string = '') {}

	public function __toString (): string {}

	/**
	 * @param string $string [optional]
	 */
	public static function from (string $string = ''): self {}

	/**
     * @return static
     */
	public function reverse (): self {}

	/**
     * @return static
     */
	public function lower (): self {}

	/**
     * @return static
     */
	public function upper (): self {}

	/**
     * @param mixed $characters
     * @return static
     */
	public function trim ($characters = null): self {}

	/**
     * @return static
     */
	public function ltrim (): self {}

	/**
     * @return static
     */
	public function rtrim (): self {}

	/**
     * @return static
     */
	public function repeat (int $times): self {}

	/**
     * @param mixed $str
     * @return static
     */
	public function append ($str = null): self {}

	/**
     * @param null|int $count
     * @return static
     */
	public function replace (string $search, string $replace, &$count = null): self {}

	/**
	 * @param string $needle
	 */
	public function startsWith (string $needle): bool {}

	/**
	 * @param string $needle
	 */
	public function endsWith (string $needle): bool {}

	/**
	 * @param mixed $str
	 * @param bool $strict [optional]
	 */
	public function equals ($str = null, bool $strict = ''): bool {}

	/**
	 * @param string $subString
	 */
	public function contains (string $subString): bool {}

	/**
	 * @param string $delimiter
	 * @param int $limit [optional]
	 */
	public function split (string $delimiter, int $limit = 9223372036854775807): Swoole\ArrayObject {}

	/**
	 * @param int $index
	 */
	public function char (int $index): string {}

	/**
     * @return static
     */
	public function chunkSplit (int $chunkLength = 76, string $chunkEnd = ''): self {}

	public function toString (): string {}

	/**
	 * @param array[] $value
	 */
	protected static function detectArrayType (array $value): Swoole\ArrayObject {}

}


}


namespace Swoole\Exception {

class ArrayKeyNotExists extends \RuntimeException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}


}


namespace Swoole {

class ArrayObject implements \ArrayAccess, \Serializable, \Countable, \Iterator, \Traversable {
	/**
     * @var array
     */
	protected $array;


	/**
     * ArrayObject constructor.
     */
	public function __construct (array $array = 'Array') {}

	public function __toArray (): array {}

	public function __serialize (): array {}

	/**
	 * @param array[] $data
	 */
	public function __unserialize (array $data): void {}

	/**
	 * @param array[] $array [optional]
	 */
	public static function from (array $array = 'Array'): self {}

	public function toArray (): array {}

	public function isEmpty (): bool {}

	public function count (): int {}

	/**
     * @return mixed
     */
	public function current () {}

	/**
     * @return mixed
     */
	public function key () {}

	public function valid (): bool {}

	/**
     * @return mixed
     */
	public function rewind () {}

	/**
     * @return mixed
     */
	public function next () {}

	/**
     * @param mixed $key
     * @return ArrayObject|StringObject
     */
	public function get ($key = null) {}

	/**
     * @param mixed $key
     * @param mixed $default
     * @return ArrayObject|StringObject
     */
	public function getOr ($key = null, $default = null) {}

	/**
     * @return mixed
     */
	public function last () {}

	/**
     * @return null|int|string
     */
	public function firstKey () {}

	/**
     * @return null|int|string
     */
	public function lastKey () {}

	/**
     * @return mixed
     */
	public function first () {}

	/**
     * @param mixed $key
     * @param mixed $value
     * @return $this
     */
	public function set ($key = null, $value = null): self {}

	/**
     * @param mixed $key
     * @return $this
     */
	public function delete ($key = null): self {}

	/**
     * @param mixed $value
     * @return $this
     */
	public function remove ($value = null, bool $strict = 1, bool $loop = ''): self {}

	/**
     * @return $this
     */
	public function clear (): self {}

	/**
     * @param mixed $key
     * @return null|mixed
     */
	public function offsetGet ($key = null) {}

	/**
     * @param mixed $key
     * @param mixed $value
     */
	public function offsetSet ($key = null, $value = null): void {}

	/**
     * @param mixed $key
     */
	public function offsetUnset ($key = null): void {}

	/**
     * @param mixed $key
     * @return bool
     */
	public function offsetExists ($key = null) {}

	/**
     * @param mixed $key
     */
	public function exists ($key = null): bool {}

	/**
     * @param mixed $value
     */
	public function contains ($value = null, bool $strict = 1): bool {}

	/**
     * @param mixed $value
     * @return mixed
     */
	public function indexOf ($value = null, bool $strict = 1) {}

	/**
     * @param mixed $value
     * @return mixed
     */
	public function lastIndexOf ($value = null, bool $strict = 1) {}

	/**
     * @param mixed $needle
     * @return mixed
     */
	public function search ($needle = null, bool $strict = 1) {}

	/**
	 * @param string $glue [optional]
	 */
	public function join (string $glue = ''): Swoole\StringObject {}

	public function serialize (): Swoole\StringObject {}

	/**
     * @param string $string
     * @return $this
     */
	public function unserialize ($string = null): self {}

	/**
     * @return float|int
     */
	public function sum () {}

	/**
     * @return float|int
     */
	public function product () {}

	/**
     * @param mixed $value
     * @return int
     */
	public function push ($value = null) {}

	/**
     * @param mixed $value
     * @return int
     */
	public function pushFront ($value = null) {}

	/**
	 * @param mixed $values [optional]
	 */
	public function append (...$values): Swoole\ArrayObject {}

	/**
     * @param mixed $value
     * @return int
     */
	public function pushBack ($value = null) {}

	/**
     * @param mixed $value
     * @return $this
     */
	public function insert (int $offset, $value = null): self {}

	/**
     * @return mixed
     */
	public function pop () {}

	/**
     * @return mixed
     */
	public function popFront () {}

	/**
     * @return mixed
     */
	public function popBack () {}

	/**
     * @param mixed $offset
     * @param int $length
     * @return static
     */
	public function slice ($offset = null, int|null $length = null, bool $preserve_keys = ''): self {}

	/**
     * @return ArrayObject|mixed|StringObject
     */
	public function randomGet () {}

	/**
     * @return $this
     */
	public function each (callable $fn): self {}

	/**
     * @param array $args
     * @return static
     */
	public function map (callable $fn, ...$args): self {}

	/**
     * @param null $initial
     * @return mixed
     */
	public function reduce (callable $fn, $initial = null) {}

	/**
     * @param array $args
     * @return static
     */
	public function keys (...$args): self {}

	/**
     * @return static
     */
	public function values (): self {}

	/**
     * @param mixed $column_key
     * @param mixed $index
     * @return static
     */
	public function column ($column_key = null, $index = null): self {}

	/**
     * @return static
     */
	public function unique (int $sort_flags = 2): self {}

	/**
     * @return static
     */
	public function reverse (bool $preserve_keys = ''): self {}

	/**
     * @return static
     */
	public function chunk (int $size, bool $preserve_keys = ''): self {}

	/**
     * Swap keys and values in an array.
     * @return static
     */
	public function flip (): self {}

	/**
     * @return static
     */
	public function filter (callable $fn, int $flag = 0): self {}

	/**
     * @return $this
     */
	public function asort (int $sort_flags = 0): self {}

	/**
     * @return $this
     */
	public function arsort (int $sort_flags = 0): self {}

	/**
     * @return $this
     */
	public function krsort (int $sort_flags = 0): self {}

	/**
     * @return $this
     */
	public function ksort (int $sort_flags = 0): self {}

	/**
     * @return $this
     */
	public function natcasesort (): self {}

	/**
     * @return $this
     */
	public function natsort (): self {}

	/**
     * @return $this
     */
	public function rsort (int $sort_flags = 0): self {}

	/**
     * @return $this
     */
	public function shuffle (): self {}

	/**
     * @return $this
     */
	public function sort (int $sort_flags = 0): self {}

	/**
     * @return $this
     */
	public function uasort (callable $value_compare_func): self {}

	/**
     * @return $this
     */
	public function uksort (callable $value_compare_func): self {}

	/**
     * @return $this
     */
	public function usort (callable $value_compare_func): self {}

	/**
     * @param mixed $value
     * @return ArrayObject|mixed|StringObject
     */
	protected static function detectType ($value = null) {}

	/**
	 * @param string $value
	 */
	protected static function detectStringType (string $value): Swoole\StringObject {}

	/**
     * @return static
     */
	protected static function detectArrayType (array $value): self {}

}

class ObjectProxy  {
	/** @var object */
	protected $__object;


	/**
	 * @param mixed $object
	 */
	public function __construct ($object = null) {}

	public function __getObject () {}

	/**
	 * @param string $name
	 */
	public function __get (string $name) {}

	/**
	 * @param string $name
	 * @param mixed $value
	 */
	public function __set (string $name, $value = null): void {}

	/**
	 * @param mixed $name
	 */
	public function __isset ($name = null) {}

	/**
	 * @param string $name
	 */
	public function __unset (string $name): void {}

	/**
	 * @param string $name
	 * @param array[] $arguments
	 */
	public function __call (string $name, array $arguments) {}

	/**
	 * @param mixed $arguments [optional]
	 */
	public function __invoke (...$arguments) {}

}


}


namespace Swoole\Coroutine {

class WaitGroup  {
	protected $chan;
	protected $count;
	protected $waiting;


	/**
	 * @param int $delta [optional]
	 */
	public function __construct (int $delta = 0) {}

	/**
	 * @param int $delta [optional]
	 */
	public function add (int $delta = 1): void {}

	public function done (): void {}

	/**
	 * @param float $timeout [optional]
	 */
	public function wait (float $timeout = -1): bool {}

	public function count (): int {}

}

class Server  {
	/** @var string */
	public $host;
	/** @var int */
	public $port;
	/** @var int */
	public $type;
	/** @var int */
	public $fd;
	/** @var int */
	public $errCode;
	/** @var array */
	public $setting;
	/** @var bool */
	protected $running;
	/** @var null|callable */
	protected $fn;
	/** @var Socket */
	protected $socket;


	/**
     * Server constructor.
     * @throws Exception
     */
	public function __construct (string $host, int $port = 0, bool $ssl = '', bool $reuse_port = '') {}

	/**
	 * @param array[] $setting
	 */
	public function set (array $setting): void {}

	/**
	 * @param callable $fn
	 */
	public function handle (callable $fn): void {}

	public function shutdown (): bool {}

	public function start (): bool {}

}


}


namespace Swoole\Coroutine\Server {

class Connection  {
	protected $socket;


	/**
	 * @param \Swoole\Coroutine\Socket $conn
	 */
	public function __construct (\Swoole\Coroutine\Socket $conn) {}

	/**
	 * @param float $timeout [optional]
	 */
	public function recv (float $timeout = 0) {}

	/**
	 * @param string $data
	 */
	public function send (string $data) {}

	public function close (): bool {}

	public function exportSocket (): Swoole\Coroutine\Socket {}

}


}


namespace Swoole\Coroutine {

class Barrier  {
	private $cid;
	private $timer;
	private static $cancel_list;


	public function __destruct () {}

	public static function make () {}

	/**
     * @throws Exception
     */
	public static function wait (\Swoole\Coroutine\Barrier &$barrier, float $timeout = -1) {}

}


}


namespace Swoole\Coroutine\Http {

class ClientProxy  {
	private $body;
	private $statusCode;
	private $headers;
	private $cookies;


	/**
	 * @param mixed $body
	 * @param mixed $statusCode
	 * @param mixed $headers
	 * @param mixed $cookies
	 */
	public function __construct ($body = null, $statusCode = null, $headers = null, $cookies = null) {}

	public function getBody () {}

	public function getStatusCode () {}

	public function getHeaders () {}

	public function getCookies () {}

}


}


namespace Swoole {

class ConnectionPool  {
	const DEFAULT_SIZE = 64;

	/** @var Channel */
	protected $pool;
	/** @var callable */
	protected $constructor;
	/** @var int */
	protected $size;
	/** @var int */
	protected $num;
	/** @var null|string */
	protected $proxy;


	/**
	 * @param callable $constructor
	 * @param int $size [optional]
	 * @param string|null $proxy [optional]
	 */
	public function __construct (callable $constructor, int $size = 64, string|null $proxy = null) {}

	public function fill (): void {}

	/**
	 * @param float $timeout [optional]
	 */
	public function get (float $timeout = -1) {}

	/**
	 * @param mixed $connection
	 */
	public function put ($connection = null): void {}

	public function close (): void {}

	protected function make (): void {}

}


}


namespace Swoole\Database {

class ObjectProxy extends \Swoole\ObjectProxy  {
	/** @var object */
	protected $__object;


	public function __clone () {}

	/**
	 * @param mixed $object
	 */
	public function __construct ($object = null) {}

	public function __getObject () {}

	/**
	 * @param string $name
	 */
	public function __get (string $name) {}

	/**
	 * @param string $name
	 * @param mixed $value
	 */
	public function __set (string $name, $value = null): void {}

	/**
	 * @param mixed $name
	 */
	public function __isset ($name = null) {}

	/**
	 * @param string $name
	 */
	public function __unset (string $name): void {}

	/**
	 * @param string $name
	 * @param array[] $arguments
	 */
	public function __call (string $name, array $arguments) {}

	/**
	 * @param mixed $arguments [optional]
	 */
	public function __invoke (...$arguments) {}

}

class MysqliConfig  {
	/** @var string */
	protected $host;
	/** @var int */
	protected $port;
	/** @var null|string */
	protected $unixSocket;
	/** @var string */
	protected $dbname;
	/** @var string */
	protected $charset;
	/** @var string */
	protected $username;
	/** @var string */
	protected $password;
	/** @var array */
	protected $options;


	public function getHost (): string {}

	/**
	 * @param mixed $host
	 */
	public function withHost ($host = null): self {}

	public function getPort (): int {}

	public function getUnixSocket (): string {}

	/**
	 * @param string|null $unixSocket
	 */
	public function withUnixSocket (string|null $unixSocket = null): self {}

	/**
	 * @param int $port
	 */
	public function withPort (int $port): self {}

	public function getDbname (): string {}

	/**
	 * @param string $dbname
	 */
	public function withDbname (string $dbname): self {}

	public function getCharset (): string {}

	/**
	 * @param string $charset
	 */
	public function withCharset (string $charset): self {}

	public function getUsername (): string {}

	/**
	 * @param string $username
	 */
	public function withUsername (string $username): self {}

	public function getPassword (): string {}

	/**
	 * @param string $password
	 */
	public function withPassword (string $password): self {}

	public function getOptions (): array {}

	/**
	 * @param array[] $options
	 */
	public function withOptions (array $options): self {}

}

class MysqliException extends \Exception implements \Throwable, \Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

/**
 * @method \mysqli|MysqliProxy get()
 * @method void put(mysqli|MysqliProxy $connection)
 */
class MysqliPool extends \Swoole\ConnectionPool  {
	const DEFAULT_SIZE = 64;

	/** @var MysqliConfig */
	protected $config;
	/** @var Channel */
	protected $pool;
	/** @var callable */
	protected $constructor;
	/** @var int */
	protected $size;
	/** @var int */
	protected $num;
	/** @var null|string */
	protected $proxy;


	/**
	 * @param \Swoole\Database\MysqliConfig $config
	 * @param int $size [optional]
	 */
	public function __construct (\Swoole\Database\MysqliConfig $config, int $size = 64) {}

	public function fill (): void {}

	/**
	 * @param float $timeout [optional]
	 */
	public function get (float $timeout = -1) {}

	/**
	 * @param mixed $connection
	 */
	public function put ($connection = null): void {}

	public function close (): void {}

	protected function make (): void {}

}

class MysqliProxy extends \Swoole\Database\ObjectProxy  {
	const IO_METHOD_REGEX = "/^autocommit|begin_transaction|change_user|close|commit|kill|multi_query|ping|prepare|query|real_connect|real_query|reap_async_query|refresh|release_savepoint|rollback|savepoint|select_db|send_query|set_charset|ssl_set$/i";
	const IO_ERRORS = array (
  0 => 2002,
  1 => 2006,
  2 => 2013,
);

	/** @var \mysqli */
	protected $__object;
	/** @var string */
	protected $charsetContext;
	/** @var null|array */
	protected $setOptContext;
	/** @var null|array */
	protected $changeUserContext;
	/** @var callable */
	protected $constructor;
	/** @var int */
	protected $round;


	/**
	 * @param callable $constructor
	 */
	public function __construct (callable $constructor) {}

	/**
	 * @param string $name
	 * @param array[] $arguments
	 */
	public function __call (string $name, array $arguments) {}

	public function getRound (): int {}

	public function reconnect (): void {}

	/**
	 * @param int $option
	 * @param mixed $value
	 */
	public function options (int $option, $value = null): bool {}

	/**
	 * @param int $option
	 * @param mixed $value
	 */
	public function set_opt (int $option, $value = null): bool {}

	/**
	 * @param string $charset
	 */
	public function set_charset (string $charset): bool {}

	/**
	 * @param string $user
	 * @param string $password
	 * @param string $database
	 */
	public function change_user (string $user, string $password, string $database): bool {}

	public function __clone () {}

	public function __getObject () {}

	/**
	 * @param string $name
	 */
	public function __get (string $name) {}

	/**
	 * @param string $name
	 * @param mixed $value
	 */
	public function __set (string $name, $value = null): void {}

	/**
	 * @param mixed $name
	 */
	public function __isset ($name = null) {}

	/**
	 * @param string $name
	 */
	public function __unset (string $name): void {}

	/**
	 * @param mixed $arguments [optional]
	 */
	public function __invoke (...$arguments) {}

}

class MysqliStatementProxy extends \Swoole\Database\ObjectProxy  {
	const IO_METHOD_REGEX = "/^close|execute|fetch|prepare$/i";

	/** @var \mysqli_stmt */
	protected $__object;
	/** @var null|string */
	protected $queryString;
	/** @var null|array */
	protected $attrSetContext;
	/** @var null|array */
	protected $bindParamContext;
	/** @var null|array */
	protected $bindResultContext;
	/** @var \Mysqli|MysqliProxy */
	protected $parent;
	/** @var int */
	protected $parentRound;


	/**
	 * @param \mysqli_stmt $object
	 * @param string|null $queryString
	 * @param \Swoole\Database\MysqliProxy $parent
	 */
	public function __construct (\mysqli_stmt $object, string|null $queryString = null, \Swoole\Database\MysqliProxy $parent) {}

	/**
	 * @param string $name
	 * @param array[] $arguments
	 */
	public function __call (string $name, array $arguments) {}

	/**
	 * @param mixed $attr
	 * @param mixed $mode
	 */
	public function attr_set ($attr = null, $mode = null): bool {}

	/**
	 * @param mixed $types
	 * @param mixed $arguments [optional]
	 */
	public function bind_param ($types = null, &...$arguments): bool {}

	/**
	 * @param mixed $arguments [optional]
	 */
	public function bind_result (&...$arguments): bool {}

	public function __clone () {}

	public function __getObject () {}

	/**
	 * @param string $name
	 */
	public function __get (string $name) {}

	/**
	 * @param string $name
	 * @param mixed $value
	 */
	public function __set (string $name, $value = null): void {}

	/**
	 * @param mixed $name
	 */
	public function __isset ($name = null) {}

	/**
	 * @param string $name
	 */
	public function __unset (string $name): void {}

	/**
	 * @param mixed $arguments [optional]
	 */
	public function __invoke (...$arguments) {}

}

class PDOConfig  {
	const DRIVER_MYSQL = "mysql";

	/** @var string */
	protected $driver;
	/** @var string */
	protected $host;
	/** @var int */
	protected $port;
	/** @var null|string */
	protected $unixSocket;
	/** @var string */
	protected $dbname;
	/** @var string */
	protected $charset;
	/** @var string */
	protected $username;
	/** @var string */
	protected $password;
	/** @var array */
	protected $options;


	public function getDriver (): string {}

	/**
	 * @param string $driver
	 */
	public function withDriver (string $driver): self {}

	public function getHost (): string {}

	/**
	 * @param mixed $host
	 */
	public function withHost ($host = null): self {}

	public function getPort (): int {}

	public function hasUnixSocket (): bool {}

	public function getUnixSocket (): string {}

	/**
	 * @param string|null $unixSocket
	 */
	public function withUnixSocket (string|null $unixSocket = null): self {}

	/**
	 * @param int $port
	 */
	public function withPort (int $port): self {}

	public function getDbname (): string {}

	/**
	 * @param string $dbname
	 */
	public function withDbname (string $dbname): self {}

	public function getCharset (): string {}

	/**
	 * @param string $charset
	 */
	public function withCharset (string $charset): self {}

	public function getUsername (): string {}

	/**
	 * @param string $username
	 */
	public function withUsername (string $username): self {}

	public function getPassword (): string {}

	/**
	 * @param string $password
	 */
	public function withPassword (string $password): self {}

	public function getOptions (): array {}

	/**
	 * @param array[] $options
	 */
	public function withOptions (array $options): self {}

	/**
     * Returns the list of available drivers
     *
     * @return string[]
     */
	public static function getAvailableDrivers () {}

}

/**
 * @method \PDO|PDOProxy get()
 * @method void put(PDO|PDOProxy $connection)
 */
class PDOPool extends \Swoole\ConnectionPool  {
	const DEFAULT_SIZE = 64;

	/** @var int */
	protected $size;
	/** @var PDOConfig */
	protected $config;
	/** @var Channel */
	protected $pool;
	/** @var callable */
	protected $constructor;
	/** @var int */
	protected $num;
	/** @var null|string */
	protected $proxy;


	/**
	 * @param \Swoole\Database\PDOConfig $config
	 * @param int $size [optional]
	 */
	public function __construct (\Swoole\Database\PDOConfig $config, int $size = 64) {}

	public function fill (): void {}

	/**
	 * @param float $timeout [optional]
	 */
	public function get (float $timeout = -1) {}

	/**
	 * @param mixed $connection
	 */
	public function put ($connection = null): void {}

	public function close (): void {}

	protected function make (): void {}

}

class PDOProxy extends \Swoole\Database\ObjectProxy  {
	const IO_ERRORS = array (
  0 => 2002,
  1 => 2006,
  2 => 2013,
);

	/** @var \PDO */
	protected $__object;
	/** @var null|array */
	protected $setAttributeContext;
	/** @var callable */
	protected $constructor;
	/** @var int */
	protected $round;


	/**
	 * @param callable $constructor
	 */
	public function __construct (callable $constructor) {}

	/**
	 * @param string $name
	 * @param array[] $arguments
	 */
	public function __call (string $name, array $arguments) {}

	public function getRound (): int {}

	public function reconnect (): void {}

	/**
	 * @param int $attribute
	 * @param mixed $value
	 */
	public function setAttribute (int $attribute, $value = null): bool {}

	public function inTransaction (): bool {}

	public function __clone () {}

	public function __getObject () {}

	/**
	 * @param string $name
	 */
	public function __get (string $name) {}

	/**
	 * @param string $name
	 * @param mixed $value
	 */
	public function __set (string $name, $value = null): void {}

	/**
	 * @param mixed $name
	 */
	public function __isset ($name = null) {}

	/**
	 * @param string $name
	 */
	public function __unset (string $name): void {}

	/**
	 * @param mixed $arguments [optional]
	 */
	public function __invoke (...$arguments) {}

}

class PDOStatementProxy extends \Swoole\Database\ObjectProxy  {
	/** @var \PDOStatement */
	protected $__object;
	/** @var null|array */
	protected $setAttributeContext;
	/** @var null|array */
	protected $setFetchModeContext;
	/** @var null|array */
	protected $bindParamContext;
	/** @var null|array */
	protected $bindColumnContext;
	/** @var null|array */
	protected $bindValueContext;
	/** @var \PDO|PDOProxy */
	protected $parent;
	/** @var int */
	protected $parentRound;


	/**
	 * @param \PDOStatement $object
	 * @param \Swoole\Database\PDOProxy $parent
	 */
	public function __construct (\PDOStatement $object, \Swoole\Database\PDOProxy $parent) {}

	/**
	 * @param string $name
	 * @param array[] $arguments
	 */
	public function __call (string $name, array $arguments) {}

	/**
	 * @param int $attribute
	 * @param mixed $value
	 */
	public function setAttribute (int $attribute, $value = null): bool {}

	/**
	 * @param int $mode
	 * @param mixed $args [optional]
	 */
	public function setFetchMode (int $mode, ...$args): bool {}

	/**
	 * @param mixed $parameter
	 * @param mixed $variable
	 * @param mixed $data_type [optional]
	 * @param mixed $length [optional]
	 * @param mixed $driver_options [optional]
	 */
	public function bindParam ($parameter = null, &$variable = null, $data_type = null, $length = null, $driver_options = null): bool {}

	/**
	 * @param mixed $column
	 * @param mixed $param
	 * @param mixed $type [optional]
	 * @param mixed $maxlen [optional]
	 * @param mixed $driverdata [optional]
	 */
	public function bindColumn ($column = null, &$param = null, $type = null, $maxlen = null, $driverdata = null): bool {}

	/**
	 * @param mixed $parameter
	 * @param mixed $value
	 * @param mixed $data_type [optional]
	 */
	public function bindValue ($parameter = null, $value = null, $data_type = null): bool {}

	public function __clone () {}

	public function __getObject () {}

	/**
	 * @param string $name
	 */
	public function __get (string $name) {}

	/**
	 * @param string $name
	 * @param mixed $value
	 */
	public function __set (string $name, $value = null): void {}

	/**
	 * @param mixed $name
	 */
	public function __isset ($name = null) {}

	/**
	 * @param string $name
	 */
	public function __unset (string $name): void {}

	/**
	 * @param mixed $arguments [optional]
	 */
	public function __invoke (...$arguments) {}

}

class RedisConfig  {
	/** @var string */
	protected $host;
	/** @var int */
	protected $port;
	/** @var float */
	protected $timeout;
	/** @var string */
	protected $reserved;
	/** @var int */
	protected $retry_interval;
	/** @var float */
	protected $read_timeout;
	/** @var string */
	protected $auth;
	/** @var int */
	protected $dbIndex;


	public function getHost () {}

	/**
	 * @param mixed $host
	 */
	public function withHost ($host = null): self {}

	public function getPort (): int {}

	/**
	 * @param int $port
	 */
	public function withPort (int $port): self {}

	public function getTimeout (): float {}

	/**
	 * @param float $timeout
	 */
	public function withTimeout (float $timeout): self {}

	public function getReserved (): string {}

	/**
	 * @param string $reserved
	 */
	public function withReserved (string $reserved): self {}

	public function getRetryInterval (): int {}

	/**
	 * @param int $retry_interval
	 */
	public function withRetryInterval (int $retry_interval): self {}

	public function getReadTimeout (): float {}

	/**
	 * @param float $read_timeout
	 */
	public function withReadTimeout (float $read_timeout): self {}

	public function getAuth (): string {}

	/**
	 * @param string $auth
	 */
	public function withAuth (string $auth): self {}

	public function getDbIndex (): int {}

	/**
	 * @param int $dbIndex
	 */
	public function withDbIndex (int $dbIndex): self {}

}

/**
 * @method \Redis get()
 * @method void put(Redis $connection)
 */
class RedisPool extends \Swoole\ConnectionPool  {
	const DEFAULT_SIZE = 64;

	/** @var RedisConfig */
	protected $config;
	/** @var Channel */
	protected $pool;
	/** @var callable */
	protected $constructor;
	/** @var int */
	protected $size;
	/** @var int */
	protected $num;
	/** @var null|string */
	protected $proxy;


	/**
	 * @param \Swoole\Database\RedisConfig $config
	 * @param int $size [optional]
	 */
	public function __construct (\Swoole\Database\RedisConfig $config, int $size = 64) {}

	public function fill (): void {}

	/**
	 * @param float $timeout [optional]
	 */
	public function get (float $timeout = -1) {}

	/**
	 * @param mixed $connection
	 */
	public function put ($connection = null): void {}

	public function close (): void {}

	protected function make (): void {}

}


}


namespace Swoole\Http {

abstract class Status  {
	const CONTINUE = 100;
	const SWITCHING_PROTOCOLS = 101;
	const PROCESSING = 102;
	const OK = 200;
	const CREATED = 201;
	const ACCEPTED = 202;
	const NON_AUTHORITATIVE_INFORMATION = 203;
	const NO_CONTENT = 204;
	const RESET_CONTENT = 205;
	const PARTIAL_CONTENT = 206;
	const MULTI_STATUS = 207;
	const ALREADY_REPORTED = 208;
	const IM_USED = 226;
	const MULTIPLE_CHOICES = 300;
	const MOVED_PERMANENTLY = 301;
	const FOUND = 302;
	const SEE_OTHER = 303;
	const NOT_MODIFIED = 304;
	const USE_PROXY = 305;
	const SWITCH_PROXY = 306;
	const TEMPORARY_REDIRECT = 307;
	const PERMANENT_REDIRECT = 308;
	const BAD_REQUEST = 400;
	const UNAUTHORIZED = 401;
	const PAYMENT_REQUIRED = 402;
	const FORBIDDEN = 403;
	const NOT_FOUND = 404;
	const METHOD_NOT_ALLOWED = 405;
	const NOT_ACCEPTABLE = 406;
	const PROXY_AUTHENTICATION_REQUIRED = 407;
	const REQUEST_TIME_OUT = 408;
	const CONFLICT = 409;
	const GONE = 410;
	const LENGTH_REQUIRED = 411;
	const PRECONDITION_FAILED = 412;
	const REQUEST_ENTITY_TOO_LARGE = 413;
	const REQUEST_URI_TOO_LARGE = 414;
	const UNSUPPORTED_MEDIA_TYPE = 415;
	const REQUESTED_RANGE_NOT_SATISFIABLE = 416;
	const EXPECTATION_FAILED = 417;
	const MISDIRECTED_REQUEST = 421;
	const UNPROCESSABLE_ENTITY = 422;
	const LOCKED = 423;
	const FAILED_DEPENDENCY = 424;
	const UNORDERED_COLLECTION = 425;
	const UPGRADE_REQUIRED = 426;
	const PRECONDITION_REQUIRED = 428;
	const TOO_MANY_REQUESTS = 429;
	const REQUEST_HEADER_FIELDS_TOO_LARGE = 431;
	const UNAVAILABLE_FOR_LEGAL_REASONS = 451;
	const INTERNAL_SERVER_ERROR = 500;
	const NOT_IMPLEMENTED = 501;
	const BAD_GATEWAY = 502;
	const SERVICE_UNAVAILABLE = 503;
	const GATEWAY_TIME_OUT = 504;
	const HTTP_VERSION_NOT_SUPPORTED = 505;
	const VARIANT_ALSO_NEGOTIATES = 506;
	const INSUFFICIENT_STORAGE = 507;
	const LOOP_DETECTED = 508;
	const NOT_EXTENDED = 510;
	const NETWORK_AUTHENTICATION_REQUIRED = 511;

	protected static $reasonPhrases;


	public static function getReasonPhrases (): array {}

	/**
	 * @param int $value
	 */
	public static function getReasonPhrase (int $value): string {}

}


}


namespace Swoole\Curl {

class Exception extends \Swoole\Exception implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

final class Handler implements \Stringable {
	/**
     * @var Client
     */
	private $client;
	private $info;
	private $withHeaderOut;
	private $withFileTime;
	private $urlInfo;
	private $postData;
	private $infile;
	private $infileSize;
	private $outputStream;
	private $proxyType;
	private $proxy;
	private $proxyPort;
	private $proxyUsername;
	private $proxyPassword;
	private $clientOptions;
	private $followLocation;
	private $autoReferer;
	private $maxRedirects;
	private $withHeader;
	private $nobody;
	/** @var callable */
	private $headerFunction;
	/** @var callable */
	private $readFunction;
	/** @var callable */
	private $writeFunction;
	private $noProgress;
	/** @var callable */
	private $progressFunction;
	private $returnTransfer;
	private $method;
	private $headers;
	private $headerMap;
	private $transfer;
	private $errCode;
	private $errMsg;
	private $failOnError;
	private $closed;
	private $cookieJar;
	private $resolve;
	private $unix_socket_path;


	/**
	 * @param string $url [optional]
	 */
	public function __construct (string $url = '') {}

	public function __toString (): string {}

	public function isAvailable (): bool {}

	/**
	 * @param int $opt
	 * @param mixed $value
	 */
	public function setOpt (int $opt, $value = null): bool {}

	public function exec () {}

	public function getInfo () {}

	public function errno () {}

	public function error () {}

	public function reset () {}

	public function getContent () {}

	public function close () {}

	/**
	 * @param array|null[] $urlInfo [optional]
	 */
	private function create (array $urlInfo = null): void {}

	private function getUrl (): string {}

	/**
	 * @param string $url
	 * @param bool $setInfo [optional]
	 */
	private function setUrl (string $url, bool $setInfo = 1): bool {}

	/**
	 * @param array[] $urlInfo
	 */
	private function setUrlInfo (array $urlInfo): bool {}

	/**
	 * @param int $port
	 */
	private function setPort (int $port): void {}

	/**
	 * @param mixed $code
	 * @param mixed $msg [optional]
	 */
	private function setError ($code = null, $msg = null): void {}

	/**
	 * @param string $headerName
	 */
	private function hasHeader (string $headerName): bool {}

	/**
	 * @param string $headerName
	 * @param string $value
	 */
	private function setHeader (string $headerName, string $value): void {}

	/**
     * @param mixed $value
     * @throws Swoole\Curl\Exception
     */
	private function setOption (int $opt, $value = null): bool {}

	private function execute () {}

	/**
	 * @param array[] $parsedUrl
	 */
	private static function unparseUrl (array $parsedUrl): string {}

	/**
	 * @param string $location
	 */
	private function getRedirectUrl (string $location): array {}

}


}


namespace Swoole {

/**
 * FastCGI constants.
 */
class FastCGI  {
	const HEADER_LEN = 8;
	const HEADER_FORMAT = "Cversion/Ctype/nrequestId/ncontentLength/CpaddingLength/Creserved";
	const MAX_CONTENT_LENGTH = 65535;
	const VERSION_1 = 1;
	const BEGIN_REQUEST = 1;
	const ABORT_REQUEST = 2;
	const END_REQUEST = 3;
	const PARAMS = 4;
	const STDIN = 5;
	const STDOUT = 6;
	const STDERR = 7;
	const DATA = 8;
	const GET_VALUES = 9;
	const GET_VALUES_RESULT = 10;
	const UNKNOWN_TYPE = 11;
	const DEFAULT_REQUEST_ID = 1;
	const KEEP_CONN = 1;
	const RESPONDER = 1;
	const AUTHORIZER = 2;
	const FILTER = 3;
	const REQUEST_COMPLETE = 0;
	const CANT_MPX_CONN = 1;
	const OVERLOADED = 2;
	const UNKNOWN_ROLE = 3;

}


}


namespace Swoole\FastCGI {

/**
 * FastCGI record.
 */
class Record implements \Stringable {
	/**
     * Identifies the FastCGI protocol version.
     *
     * @var int
     */
	protected $version;
	/**
     * Identifies the FastCGI record type, i.e. the general function that the record performs.
     *
     * @var int
     */
	protected $type;
	/**
     * Identifies the FastCGI request to which the record belongs.
     *
     * @var int
     */
	protected $requestId;
	/**
     * Reserved byte for future proposes
     *
     * @var int
     */
	protected $reserved;
	/**
     * The number of bytes in the contentData component of the record.
     *
     * @var int
     */
	private $contentLength;
	/**
     * The number of bytes in the paddingData component of the record.
     *
     * @var int
     */
	private $paddingLength;
	/**
     * Binary data, between 0 and 65535 bytes of data, interpreted according to the record type.
     *
     * @var string
     */
	private $contentData;
	/**
     * Padding data, between 0 and 255 bytes of data, which are ignored.
     *
     * @var string
     */
	private $paddingData;


	/**
     * Returns the binary message representation of record
     */
	final public function __toString (): string {}

	/**
     * Unpacks the message from the binary data buffer
     *
     * @param string $data Binary buffer with raw data
     *
     * @return static
     */
	final public static function unpack (string $data): self {}

	/**
     * Sets the content data and adjusts the length fields
     *
     * @return static
     */
	public function setContentData (string $data): self {}

	/**
     * Returns the context data from the record
     */
	public function getContentData (): string {}

	/**
     * Returns the version of record
     */
	public function getVersion (): int {}

	/**
     * Returns record type
     */
	public function getType (): int {}

	/**
     * Returns request ID
     */
	public function getRequestId (): int {}

	/**
     * Sets request ID
     *
     * There should be only one unique ID for all active requests,
     * use random number or preferably resetting auto-increment.
     *
     * @return static
     */
	public function setRequestId (int $requestId): self {}

	/**
     * Returns the size of content length
     */
	final public function getContentLength (): int {}

	/**
     * Returns the size of padding length
     */
	final public function getPaddingLength (): int {}

	/**
     * Method to unpack the payload for the record.
     *
     * NB: Default implementation will be always called
     *
     * @param static $self Instance of current frame
     * @param string $data Binary data
     */
	protected static function unpackPayload ($self = null, string $data): void {}

	/**
     * Implementation of packing the payload
     */
	protected function packPayload (): string {}

}


}


namespace Swoole\FastCGI\Record {

/**
 * Params request record
 */
class Params extends \Swoole\FastCGI\Record implements \Stringable {
	/**
     * List of params
     *
     * @var array
     */
	protected $values;
	/**
     * Identifies the FastCGI protocol version.
     *
     * @var int
     */
	protected $version;
	/**
     * Identifies the FastCGI record type, i.e. the general function that the record performs.
     *
     * @var int
     */
	protected $type;
	/**
     * Identifies the FastCGI request to which the record belongs.
     *
     * @var int
     */
	protected $requestId;
	/**
     * Reserved byte for future proposes
     *
     * @var int
     */
	protected $reserved;


	/**
     * Constructs a param request
     */
	public function __construct (array $values = 'Array') {}

	/**
     * Returns an associative list of parameters
     */
	public function getValues (): array {}

	/**
     * {@inheritdoc}
     * @param static $self
     */
	protected static function unpackPayload ($self = null, string $data): void {}

	/** {@inheritdoc} */
	protected function packPayload (): string {}

	/**
     * Returns the binary message representation of record
     */
	final public function __toString (): string {}

	/**
     * Unpacks the message from the binary data buffer
     *
     * @param string $data Binary buffer with raw data
     *
     * @return static
     */
	final public static function unpack (string $data): self {}

	/**
     * Sets the content data and adjusts the length fields
     *
     * @return static
     */
	public function setContentData (string $data): self {}

	/**
     * Returns the context data from the record
     */
	public function getContentData (): string {}

	/**
     * Returns the version of record
     */
	public function getVersion (): int {}

	/**
     * Returns record type
     */
	public function getType (): int {}

	/**
     * Returns request ID
     */
	public function getRequestId (): int {}

	/**
     * Sets request ID
     *
     * There should be only one unique ID for all active requests,
     * use random number or preferably resetting auto-increment.
     *
     * @return static
     */
	public function setRequestId (int $requestId): self {}

	/**
     * Returns the size of content length
     */
	final public function getContentLength (): int {}

	/**
     * Returns the size of padding length
     */
	final public function getPaddingLength (): int {}

}

/**
 * The Web server sends a FCGI_ABORT_REQUEST record to abort a request
 */
class AbortRequest extends \Swoole\FastCGI\Record implements \Stringable {
	/**
     * Identifies the FastCGI protocol version.
     *
     * @var int
     */
	protected $version;
	/**
     * Identifies the FastCGI record type, i.e. the general function that the record performs.
     *
     * @var int
     */
	protected $type;
	/**
     * Identifies the FastCGI request to which the record belongs.
     *
     * @var int
     */
	protected $requestId;
	/**
     * Reserved byte for future proposes
     *
     * @var int
     */
	protected $reserved;


	/**
	 * @param int $requestId [optional]
	 */
	public function __construct (int $requestId = 0) {}

	/**
     * Returns the binary message representation of record
     */
	final public function __toString (): string {}

	/**
     * Unpacks the message from the binary data buffer
     *
     * @param string $data Binary buffer with raw data
     *
     * @return static
     */
	final public static function unpack (string $data): self {}

	/**
     * Sets the content data and adjusts the length fields
     *
     * @return static
     */
	public function setContentData (string $data): self {}

	/**
     * Returns the context data from the record
     */
	public function getContentData (): string {}

	/**
     * Returns the version of record
     */
	public function getVersion (): int {}

	/**
     * Returns record type
     */
	public function getType (): int {}

	/**
     * Returns request ID
     */
	public function getRequestId (): int {}

	/**
     * Sets request ID
     *
     * There should be only one unique ID for all active requests,
     * use random number or preferably resetting auto-increment.
     *
     * @return static
     */
	public function setRequestId (int $requestId): self {}

	/**
     * Returns the size of content length
     */
	final public function getContentLength (): int {}

	/**
     * Returns the size of padding length
     */
	final public function getPaddingLength (): int {}

	/**
     * Method to unpack the payload for the record.
     *
     * NB: Default implementation will be always called
     *
     * @param static $self Instance of current frame
     * @param string $data Binary data
     */
	protected static function unpackPayload ($self = null, string $data): void {}

	/**
     * Implementation of packing the payload
     */
	protected function packPayload (): string {}

}

/**
 * The Web server sends a FCGI_BEGIN_REQUEST record to start a request.
 */
class BeginRequest extends \Swoole\FastCGI\Record implements \Stringable {
	/**
     * The role component sets the role the Web server expects the application to play.
     * The currently-defined roles are:
     *   FCGI_RESPONDER
     *   FCGI_AUTHORIZER
     *   FCGI_FILTER
     *
     * @var int
     */
	protected $role;
	/**
     * The flags component contains a bit that controls connection shutdown.
     *
     * flags & FCGI_KEEP_CONN:
     *   If zero, the application closes the connection after responding to this request.
     *   If not zero, the application does not close the connection after responding to this request;
     *   the Web server retains responsibility for the connection.
     *
     * @var int
     */
	protected $flags;
	/**
     * Reserved data, 5 bytes maximum
     *
     * @var string
     */
	protected $reserved1;
	/**
     * Identifies the FastCGI protocol version.
     *
     * @var int
     */
	protected $version;
	/**
     * Identifies the FastCGI record type, i.e. the general function that the record performs.
     *
     * @var int
     */
	protected $type;
	/**
     * Identifies the FastCGI request to which the record belongs.
     *
     * @var int
     */
	protected $requestId;
	/**
     * Reserved byte for future proposes
     *
     * @var int
     */
	protected $reserved;


	/**
	 * @param int $role [optional]
	 * @param int $flags [optional]
	 * @param string $reserved [optional]
	 */
	public function __construct (int $role = 3, int $flags = 0, string $reserved = '') {}

	/**
     * Returns the role
     *
     * The role component sets the role the Web server expects the application to play.
     * The currently-defined roles are:
     *   FCGI_RESPONDER
     *   FCGI_AUTHORIZER
     *   FCGI_FILTER
     */
	public function getRole (): int {}

	/**
     * Returns the flags
     *
     * The flags component contains a bit that controls connection shutdown.
     *
     * flags & FCGI_KEEP_CONN:
     *   If zero, the application closes the connection after responding to this request.
     *   If not zero, the application does not close the connection after responding to this request;
     *   the Web server retains responsibility for the connection.
     */
	public function getFlags (): int {}

	/**
     * {@inheritdoc}
     * @param static $self
     */
	protected static function unpackPayload ($self = null, string $data): void {}

	/** {@inheritdoc} */
	protected function packPayload (): string {}

	/**
     * Returns the binary message representation of record
     */
	final public function __toString (): string {}

	/**
     * Unpacks the message from the binary data buffer
     *
     * @param string $data Binary buffer with raw data
     *
     * @return static
     */
	final public static function unpack (string $data): self {}

	/**
     * Sets the content data and adjusts the length fields
     *
     * @return static
     */
	public function setContentData (string $data): self {}

	/**
     * Returns the context data from the record
     */
	public function getContentData (): string {}

	/**
     * Returns the version of record
     */
	public function getVersion (): int {}

	/**
     * Returns record type
     */
	public function getType (): int {}

	/**
     * Returns request ID
     */
	public function getRequestId (): int {}

	/**
     * Sets request ID
     *
     * There should be only one unique ID for all active requests,
     * use random number or preferably resetting auto-increment.
     *
     * @return static
     */
	public function setRequestId (int $requestId): self {}

	/**
     * Returns the size of content length
     */
	final public function getContentLength (): int {}

	/**
     * Returns the size of padding length
     */
	final public function getPaddingLength (): int {}

}

/**
 * Data binary stream
 *
 * FCGI_DATA is a second stream record type used to send additional data to the application.
 */
class Data extends \Swoole\FastCGI\Record implements \Stringable {
	/**
     * Identifies the FastCGI protocol version.
     *
     * @var int
     */
	protected $version;
	/**
     * Identifies the FastCGI record type, i.e. the general function that the record performs.
     *
     * @var int
     */
	protected $type;
	/**
     * Identifies the FastCGI request to which the record belongs.
     *
     * @var int
     */
	protected $requestId;
	/**
     * Reserved byte for future proposes
     *
     * @var int
     */
	protected $reserved;


	/**
	 * @param string $contentData [optional]
	 */
	public function __construct (string $contentData = '') {}

	/**
     * Returns the binary message representation of record
     */
	final public function __toString (): string {}

	/**
     * Unpacks the message from the binary data buffer
     *
     * @param string $data Binary buffer with raw data
     *
     * @return static
     */
	final public static function unpack (string $data): self {}

	/**
     * Sets the content data and adjusts the length fields
     *
     * @return static
     */
	public function setContentData (string $data): self {}

	/**
     * Returns the context data from the record
     */
	public function getContentData (): string {}

	/**
     * Returns the version of record
     */
	public function getVersion (): int {}

	/**
     * Returns record type
     */
	public function getType (): int {}

	/**
     * Returns request ID
     */
	public function getRequestId (): int {}

	/**
     * Sets request ID
     *
     * There should be only one unique ID for all active requests,
     * use random number or preferably resetting auto-increment.
     *
     * @return static
     */
	public function setRequestId (int $requestId): self {}

	/**
     * Returns the size of content length
     */
	final public function getContentLength (): int {}

	/**
     * Returns the size of padding length
     */
	final public function getPaddingLength (): int {}

	/**
     * Method to unpack the payload for the record.
     *
     * NB: Default implementation will be always called
     *
     * @param static $self Instance of current frame
     * @param string $data Binary data
     */
	protected static function unpackPayload ($self = null, string $data): void {}

	/**
     * Implementation of packing the payload
     */
	protected function packPayload (): string {}

}

/**
 * The application sends a FCGI_END_REQUEST record to terminate a request, either because the application
 * has processed the request or because the application has rejected the request.
 */
class EndRequest extends \Swoole\FastCGI\Record implements \Stringable {
	/**
     * The appStatus component is an application-level status code. Each role documents its usage of appStatus.
     *
     * @var int
     */
	protected $appStatus;
	/**
     * The protocolStatus component is a protocol-level status code.
     *
     * The possible protocolStatus values are:
     *   FCGI_REQUEST_COMPLETE: normal end of request.
     *   FCGI_CANT_MPX_CONN: rejecting a new request.
     *      This happens when a Web server sends concurrent requests over one connection to an application that is
     *      designed to process one request at a time per connection.
     *   FCGI_OVERLOADED: rejecting a new request.
     *      This happens when the application runs out of some resource, e.g. database connections.
     *   FCGI_UNKNOWN_ROLE: rejecting a new request.
     *      This happens when the Web server has specified a role that is unknown to the application.
     *
     * @var int
     */
	protected $protocolStatus;
	/**
     * Reserved data, 3 bytes maximum
     *
     * @var string
     */
	protected $reserved1;
	/**
     * Identifies the FastCGI protocol version.
     *
     * @var int
     */
	protected $version;
	/**
     * Identifies the FastCGI record type, i.e. the general function that the record performs.
     *
     * @var int
     */
	protected $type;
	/**
     * Identifies the FastCGI request to which the record belongs.
     *
     * @var int
     */
	protected $requestId;
	/**
     * Reserved byte for future proposes
     *
     * @var int
     */
	protected $reserved;


	/**
	 * @param int $protocolStatus [optional]
	 * @param int $appStatus [optional]
	 * @param string $reserved [optional]
	 */
	public function __construct (int $protocolStatus = 0, int $appStatus = 0, string $reserved = '') {}

	/**
     * Returns app status
     *
     * The appStatus component is an application-level status code. Each role documents its usage of appStatus.
     */
	public function getAppStatus (): int {}

	/**
     * Returns the protocol status
     *
     * The possible protocolStatus values are:
     *   FCGI_REQUEST_COMPLETE: normal end of request.
     *   FCGI_CANT_MPX_CONN: rejecting a new request.
     *      This happens when a Web server sends concurrent requests over one connection to an application that is
     *      designed to process one request at a time per connection.
     *   FCGI_OVERLOADED: rejecting a new request.
     *      This happens when the application runs out of some resource, e.g. database connections.
     *   FCGI_UNKNOWN_ROLE: rejecting a new request.
     *      This happens when the Web server has specified a role that is unknown to the application.
     */
	public function getProtocolStatus (): int {}

	/**
     * {@inheritdoc}
     * @param static $self
     */
	protected static function unpackPayload ($self = null, string $data): void {}

	/** {@inheritdoc} */
	protected function packPayload (): string {}

	/**
     * Returns the binary message representation of record
     */
	final public function __toString (): string {}

	/**
     * Unpacks the message from the binary data buffer
     *
     * @param string $data Binary buffer with raw data
     *
     * @return static
     */
	final public static function unpack (string $data): self {}

	/**
     * Sets the content data and adjusts the length fields
     *
     * @return static
     */
	public function setContentData (string $data): self {}

	/**
     * Returns the context data from the record
     */
	public function getContentData (): string {}

	/**
     * Returns the version of record
     */
	public function getVersion (): int {}

	/**
     * Returns record type
     */
	public function getType (): int {}

	/**
     * Returns request ID
     */
	public function getRequestId (): int {}

	/**
     * Sets request ID
     *
     * There should be only one unique ID for all active requests,
     * use random number or preferably resetting auto-increment.
     *
     * @return static
     */
	public function setRequestId (int $requestId): self {}

	/**
     * Returns the size of content length
     */
	final public function getContentLength (): int {}

	/**
     * Returns the size of padding length
     */
	final public function getPaddingLength (): int {}

}

/**
 * GetValues API
 *
 * The Web server can query specific variables within the application.
 * The server will typically perform a query on application startup in order to to automate certain aspects of
 * system configuration.
 *
 * The application responds by sending a record {FCGI_GET_VALUES_RESULT, 0, ...} with the values supplied.
 * If the application doesn't understand a variable name that was included in the query, it omits that name from
 * the response.
 *
 * FCGI_GET_VALUES is designed to allow an open-ended set of variables.
 *
 * The initial set provides information to help the server perform application and connection management:
 *   FCGI_MAX_CONNS:  The maximum number of concurrent transport connections this application will accept,
 *                    e.g. "1" or "10".
 *   FCGI_MAX_REQS:   The maximum number of concurrent requests this application will accept, e.g. "1" or "50".
 *   FCGI_MPXS_CONNS: "0" if this application does not multiplex connections (i.e. handle concurrent requests
 *                    over each connection), "1" otherwise.
 */
class GetValues extends \Swoole\FastCGI\Record\Params implements \Stringable {
	/**
     * List of params
     *
     * @var array
     */
	protected $values;
	/**
     * Identifies the FastCGI protocol version.
     *
     * @var int
     */
	protected $version;
	/**
     * Identifies the FastCGI record type, i.e. the general function that the record performs.
     *
     * @var int
     */
	protected $type;
	/**
     * Identifies the FastCGI request to which the record belongs.
     *
     * @var int
     */
	protected $requestId;
	/**
     * Reserved byte for future proposes
     *
     * @var int
     */
	protected $reserved;


	/**
     * Constructs a request
     *
     * @param array $keys List of keys to receive
     */
	public function __construct (array $keys = 'Array') {}

	/**
     * Returns an associative list of parameters
     */
	public function getValues (): array {}

	/**
     * {@inheritdoc}
     * @param static $self
     */
	protected static function unpackPayload ($self = null, string $data): void {}

	/** {@inheritdoc} */
	protected function packPayload (): string {}

	/**
     * Returns the binary message representation of record
     */
	final public function __toString (): string {}

	/**
     * Unpacks the message from the binary data buffer
     *
     * @param string $data Binary buffer with raw data
     *
     * @return static
     */
	final public static function unpack (string $data): self {}

	/**
     * Sets the content data and adjusts the length fields
     *
     * @return static
     */
	public function setContentData (string $data): self {}

	/**
     * Returns the context data from the record
     */
	public function getContentData (): string {}

	/**
     * Returns the version of record
     */
	public function getVersion (): int {}

	/**
     * Returns record type
     */
	public function getType (): int {}

	/**
     * Returns request ID
     */
	public function getRequestId (): int {}

	/**
     * Sets request ID
     *
     * There should be only one unique ID for all active requests,
     * use random number or preferably resetting auto-increment.
     *
     * @return static
     */
	public function setRequestId (int $requestId): self {}

	/**
     * Returns the size of content length
     */
	final public function getContentLength (): int {}

	/**
     * Returns the size of padding length
     */
	final public function getPaddingLength (): int {}

}

/**
 * GetValues API
 *
 * The Web server can query specific variables within the application.
 * The server will typically perform a query on application startup in order to to automate certain aspects of
 * system configuration.
 *
 * The application responds by sending a record {FCGI_GET_VALUES_RESULT, 0, ...} with the values supplied.
 * If the application doesn't understand a variable name that was included in the query, it omits that name from
 * the response.
 *
 * FCGI_GET_VALUES is designed to allow an open-ended set of variables.
 *
 * The initial set provides information to help the server perform application and connection management:
 *   FCGI_MAX_CONNS:  The maximum number of concurrent transport connections this application will accept,
 *                    e.g. "1" or "10".
 *   FCGI_MAX_REQS:   The maximum number of concurrent requests this application will accept, e.g. "1" or "50".
 *   FCGI_MPXS_CONNS: "0" if this application does not multiplex connections (i.e. handle concurrent requests
 *                    over each connection), "1" otherwise.
 */
class GetValuesResult extends \Swoole\FastCGI\Record\Params implements \Stringable {
	/**
     * List of params
     *
     * @var array
     */
	protected $values;
	/**
     * Identifies the FastCGI protocol version.
     *
     * @var int
     */
	protected $version;
	/**
     * Identifies the FastCGI record type, i.e. the general function that the record performs.
     *
     * @var int
     */
	protected $type;
	/**
     * Identifies the FastCGI request to which the record belongs.
     *
     * @var int
     */
	protected $requestId;
	/**
     * Reserved byte for future proposes
     *
     * @var int
     */
	protected $reserved;


	/**
     * Constructs a param request
     */
	public function __construct (array $values = 'Array') {}

	/**
     * Returns an associative list of parameters
     */
	public function getValues (): array {}

	/**
     * {@inheritdoc}
     * @param static $self
     */
	protected static function unpackPayload ($self = null, string $data): void {}

	/** {@inheritdoc} */
	protected function packPayload (): string {}

	/**
     * Returns the binary message representation of record
     */
	final public function __toString (): string {}

	/**
     * Unpacks the message from the binary data buffer
     *
     * @param string $data Binary buffer with raw data
     *
     * @return static
     */
	final public static function unpack (string $data): self {}

	/**
     * Sets the content data and adjusts the length fields
     *
     * @return static
     */
	public function setContentData (string $data): self {}

	/**
     * Returns the context data from the record
     */
	public function getContentData (): string {}

	/**
     * Returns the version of record
     */
	public function getVersion (): int {}

	/**
     * Returns record type
     */
	public function getType (): int {}

	/**
     * Returns request ID
     */
	public function getRequestId (): int {}

	/**
     * Sets request ID
     *
     * There should be only one unique ID for all active requests,
     * use random number or preferably resetting auto-increment.
     *
     * @return static
     */
	public function setRequestId (int $requestId): self {}

	/**
     * Returns the size of content length
     */
	final public function getContentLength (): int {}

	/**
     * Returns the size of padding length
     */
	final public function getPaddingLength (): int {}

}

/**
 * Stdin binary stream
 *
 * FCGI_STDIN is a stream record type used in sending arbitrary data from the Web server to the application
 */
class Stdin extends \Swoole\FastCGI\Record implements \Stringable {
	/**
     * Identifies the FastCGI protocol version.
     *
     * @var int
     */
	protected $version;
	/**
     * Identifies the FastCGI record type, i.e. the general function that the record performs.
     *
     * @var int
     */
	protected $type;
	/**
     * Identifies the FastCGI request to which the record belongs.
     *
     * @var int
     */
	protected $requestId;
	/**
     * Reserved byte for future proposes
     *
     * @var int
     */
	protected $reserved;


	/**
	 * @param string $contentData [optional]
	 */
	public function __construct (string $contentData = '') {}

	/**
     * Returns the binary message representation of record
     */
	final public function __toString (): string {}

	/**
     * Unpacks the message from the binary data buffer
     *
     * @param string $data Binary buffer with raw data
     *
     * @return static
     */
	final public static function unpack (string $data): self {}

	/**
     * Sets the content data and adjusts the length fields
     *
     * @return static
     */
	public function setContentData (string $data): self {}

	/**
     * Returns the context data from the record
     */
	public function getContentData (): string {}

	/**
     * Returns the version of record
     */
	public function getVersion (): int {}

	/**
     * Returns record type
     */
	public function getType (): int {}

	/**
     * Returns request ID
     */
	public function getRequestId (): int {}

	/**
     * Sets request ID
     *
     * There should be only one unique ID for all active requests,
     * use random number or preferably resetting auto-increment.
     *
     * @return static
     */
	public function setRequestId (int $requestId): self {}

	/**
     * Returns the size of content length
     */
	final public function getContentLength (): int {}

	/**
     * Returns the size of padding length
     */
	final public function getPaddingLength (): int {}

	/**
     * Method to unpack the payload for the record.
     *
     * NB: Default implementation will be always called
     *
     * @param static $self Instance of current frame
     * @param string $data Binary data
     */
	protected static function unpackPayload ($self = null, string $data): void {}

	/**
     * Implementation of packing the payload
     */
	protected function packPayload (): string {}

}

/**
 * Stdout binary stream
 *
 * FCGI_STDOUT is a stream record for sending arbitrary data from the application to the Web server
 */
class Stdout extends \Swoole\FastCGI\Record implements \Stringable {
	/**
     * Identifies the FastCGI protocol version.
     *
     * @var int
     */
	protected $version;
	/**
     * Identifies the FastCGI record type, i.e. the general function that the record performs.
     *
     * @var int
     */
	protected $type;
	/**
     * Identifies the FastCGI request to which the record belongs.
     *
     * @var int
     */
	protected $requestId;
	/**
     * Reserved byte for future proposes
     *
     * @var int
     */
	protected $reserved;


	/**
	 * @param string $contentData [optional]
	 */
	public function __construct (string $contentData = '') {}

	/**
     * Returns the binary message representation of record
     */
	final public function __toString (): string {}

	/**
     * Unpacks the message from the binary data buffer
     *
     * @param string $data Binary buffer with raw data
     *
     * @return static
     */
	final public static function unpack (string $data): self {}

	/**
     * Sets the content data and adjusts the length fields
     *
     * @return static
     */
	public function setContentData (string $data): self {}

	/**
     * Returns the context data from the record
     */
	public function getContentData (): string {}

	/**
     * Returns the version of record
     */
	public function getVersion (): int {}

	/**
     * Returns record type
     */
	public function getType (): int {}

	/**
     * Returns request ID
     */
	public function getRequestId (): int {}

	/**
     * Sets request ID
     *
     * There should be only one unique ID for all active requests,
     * use random number or preferably resetting auto-increment.
     *
     * @return static
     */
	public function setRequestId (int $requestId): self {}

	/**
     * Returns the size of content length
     */
	final public function getContentLength (): int {}

	/**
     * Returns the size of padding length
     */
	final public function getPaddingLength (): int {}

	/**
     * Method to unpack the payload for the record.
     *
     * NB: Default implementation will be always called
     *
     * @param static $self Instance of current frame
     * @param string $data Binary data
     */
	protected static function unpackPayload ($self = null, string $data): void {}

	/**
     * Implementation of packing the payload
     */
	protected function packPayload (): string {}

}

/**
 * Stderr binary stream
 *
 * FCGI_STDERR is a stream record for sending arbitrary data from the application to the Web server
 */
class Stderr extends \Swoole\FastCGI\Record implements \Stringable {
	/**
     * Identifies the FastCGI protocol version.
     *
     * @var int
     */
	protected $version;
	/**
     * Identifies the FastCGI record type, i.e. the general function that the record performs.
     *
     * @var int
     */
	protected $type;
	/**
     * Identifies the FastCGI request to which the record belongs.
     *
     * @var int
     */
	protected $requestId;
	/**
     * Reserved byte for future proposes
     *
     * @var int
     */
	protected $reserved;


	/**
	 * @param string $contentData [optional]
	 */
	public function __construct (string $contentData = '') {}

	/**
     * Returns the binary message representation of record
     */
	final public function __toString (): string {}

	/**
     * Unpacks the message from the binary data buffer
     *
     * @param string $data Binary buffer with raw data
     *
     * @return static
     */
	final public static function unpack (string $data): self {}

	/**
     * Sets the content data and adjusts the length fields
     *
     * @return static
     */
	public function setContentData (string $data): self {}

	/**
     * Returns the context data from the record
     */
	public function getContentData (): string {}

	/**
     * Returns the version of record
     */
	public function getVersion (): int {}

	/**
     * Returns record type
     */
	public function getType (): int {}

	/**
     * Returns request ID
     */
	public function getRequestId (): int {}

	/**
     * Sets request ID
     *
     * There should be only one unique ID for all active requests,
     * use random number or preferably resetting auto-increment.
     *
     * @return static
     */
	public function setRequestId (int $requestId): self {}

	/**
     * Returns the size of content length
     */
	final public function getContentLength (): int {}

	/**
     * Returns the size of padding length
     */
	final public function getPaddingLength (): int {}

	/**
     * Method to unpack the payload for the record.
     *
     * NB: Default implementation will be always called
     *
     * @param static $self Instance of current frame
     * @param string $data Binary data
     */
	protected static function unpackPayload ($self = null, string $data): void {}

	/**
     * Implementation of packing the payload
     */
	protected function packPayload (): string {}

}

/**
 * Record for unknown queries
 *
 * The set of management record types is likely to grow in future versions of this protocol.
 * To provide for this evolution, the protocol includes the FCGI_UNKNOWN_TYPE management record.
 * When an application receives a management record whose type T it does not understand, the application responds
 * with {FCGI_UNKNOWN_TYPE, 0, {T}}.
 */
class UnknownType extends \Swoole\FastCGI\Record implements \Stringable {
	/**
     * Type of the unrecognized management record.
     *
     * @var int
     */
	protected $type1;
	/**
     * Reserved data, 7 bytes maximum
     *
     * @var string
     */
	protected $reserved1;
	/**
     * Identifies the FastCGI protocol version.
     *
     * @var int
     */
	protected $version;
	/**
     * Identifies the FastCGI record type, i.e. the general function that the record performs.
     *
     * @var int
     */
	protected $type;
	/**
     * Identifies the FastCGI request to which the record belongs.
     *
     * @var int
     */
	protected $requestId;
	/**
     * Reserved byte for future proposes
     *
     * @var int
     */
	protected $reserved;


	/**
	 * @param int $type [optional]
	 * @param string $reserved [optional]
	 */
	public function __construct (int $type = 0, string $reserved = '') {}

	/**
     * Returns the unrecognized type
     */
	public function getUnrecognizedType (): int {}

	/**
     * {@inheritdoc}
     * @param static $self
     */
	public static function unpackPayload ($self = null, string $data): void {}

	/** {@inheritdoc} */
	protected function packPayload (): string {}

	/**
     * Returns the binary message representation of record
     */
	final public function __toString (): string {}

	/**
     * Unpacks the message from the binary data buffer
     *
     * @param string $data Binary buffer with raw data
     *
     * @return static
     */
	final public static function unpack (string $data): self {}

	/**
     * Sets the content data and adjusts the length fields
     *
     * @return static
     */
	public function setContentData (string $data): self {}

	/**
     * Returns the context data from the record
     */
	public function getContentData (): string {}

	/**
     * Returns the version of record
     */
	public function getVersion (): int {}

	/**
     * Returns record type
     */
	public function getType (): int {}

	/**
     * Returns request ID
     */
	public function getRequestId (): int {}

	/**
     * Sets request ID
     *
     * There should be only one unique ID for all active requests,
     * use random number or preferably resetting auto-increment.
     *
     * @return static
     */
	public function setRequestId (int $requestId): self {}

	/**
     * Returns the size of content length
     */
	final public function getContentLength (): int {}

	/**
     * Returns the size of padding length
     */
	final public function getPaddingLength (): int {}

}


}


namespace Swoole\FastCGI {

/**
 * Utility class to simplify parsing of FastCGI protocol data.
 */
class FrameParser  {
	/**
     * Mapping of constants to the classes
     *
     * @var array
     */
	protected static $classMapping;


	/**
     * Checks if the buffer contains a valid frame to parse
     *
     * @param string $buffer Binary buffer
     */
	public static function hasFrame (string $buffer): bool {}

	/**
     * Parses a frame from the binary buffer
     *
     * @param string $buffer Binary buffer
     *
     * @return Record One of the corresponding FastCGI record
     */
	public static function parseFrame (string &$buffer): Swoole\FastCGI\Record {}

}

class Message  {
	/** @var array */
	protected $params;
	/** @var string */
	protected $body;
	/** @var string */
	protected $error;


	/**
	 * @param string $name
	 */
	public function getParam (string $name): ?string {}

	/**
	 * @param string $name
	 * @param string $value
	 */
	public function withParam (string $name, string $value): self {}

	/**
	 * @param string $name
	 */
	public function withoutParam (string $name): self {}

	public function getParams (): array {}

	/**
	 * @param array[] $params
	 */
	public function withParams (array $params): self {}

	/**
	 * @param array[] $params
	 */
	public function withAddedParams (array $params): self {}

	public function getBody (): string {}

	/**
	 * @param mixed $body
	 */
	public function withBody ($body = null): self {}

	public function getError (): string {}

	/**
	 * @param string $error
	 */
	public function withError (string $error): self {}

}

class Request extends \Swoole\FastCGI\Message implements \Stringable {
	protected $keepConn;
	/** @var array */
	protected $params;
	/** @var string */
	protected $body;
	/** @var string */
	protected $error;


	public function __toString (): string {}

	public function getKeepConn (): bool {}

	/**
	 * @param bool $keepConn
	 */
	public function withKeepConn (bool $keepConn): self {}

	/**
	 * @param string $name
	 */
	public function getParam (string $name): ?string {}

	/**
	 * @param string $name
	 * @param string $value
	 */
	public function withParam (string $name, string $value): self {}

	/**
	 * @param string $name
	 */
	public function withoutParam (string $name): self {}

	public function getParams (): array {}

	/**
	 * @param array[] $params
	 */
	public function withParams (array $params): self {}

	/**
	 * @param array[] $params
	 */
	public function withAddedParams (array $params): self {}

	public function getBody (): string {}

	/**
	 * @param mixed $body
	 */
	public function withBody ($body = null): self {}

	public function getError (): string {}

	/**
	 * @param string $error
	 */
	public function withError (string $error): self {}

}

class Response extends \Swoole\FastCGI\Message  {
	/** @var array */
	protected $params;
	/** @var string */
	protected $body;
	/** @var string */
	protected $error;


	/**
	 * @param array[] $records [optional]
	 */
	public function __construct (array $records = 'Array') {}

	/**
	 * @param array[] $records
	 */
	public static function verify (array $records): bool {}

	/**
	 * @param string $name
	 */
	public function getParam (string $name): ?string {}

	/**
	 * @param string $name
	 * @param string $value
	 */
	public function withParam (string $name, string $value): self {}

	/**
	 * @param string $name
	 */
	public function withoutParam (string $name): self {}

	public function getParams (): array {}

	/**
	 * @param array[] $params
	 */
	public function withParams (array $params): self {}

	/**
	 * @param array[] $params
	 */
	public function withAddedParams (array $params): self {}

	public function getBody (): string {}

	/**
	 * @param mixed $body
	 */
	public function withBody ($body = null): self {}

	public function getError (): string {}

	/**
	 * @param string $error
	 */
	public function withError (string $error): self {}

}

class HttpRequest extends \Swoole\FastCGI\Request implements \Stringable {
	protected $params;
	protected $keepConn;
	/** @var string */
	protected $body;
	/** @var string */
	protected $error;


	public function getScheme (): ?string {}

	/**
	 * @param string $scheme
	 */
	public function withScheme (string $scheme): self {}

	public function withoutScheme (): void {}

	public function getMethod (): ?string {}

	/**
	 * @param string $method
	 */
	public function withMethod (string $method): self {}

	public function withoutMethod (): void {}

	public function getDocumentRoot (): ?string {}

	/**
	 * @param string $documentRoot
	 */
	public function withDocumentRoot (string $documentRoot): self {}

	public function withoutDocumentRoot (): void {}

	public function getScriptFilename (): ?string {}

	/**
	 * @param string $scriptFilename
	 */
	public function withScriptFilename (string $scriptFilename): self {}

	public function withoutScriptFilename (): void {}

	public function getScriptName (): ?string {}

	/**
	 * @param string $scriptName
	 */
	public function withScriptName (string $scriptName): self {}

	public function withoutScriptName (): void {}

	/**
	 * @param string $uri
	 */
	public function withUri (string $uri): self {}

	public function getDocumentUri (): ?string {}

	/**
	 * @param string $documentUri
	 */
	public function withDocumentUri (string $documentUri): self {}

	public function withoutDocumentUri (): void {}

	public function getRequestUri (): ?string {}

	/**
	 * @param string $requestUri
	 */
	public function withRequestUri (string $requestUri): self {}

	public function withoutRequestUri (): void {}

	/**
	 * @param mixed $query
	 */
	public function withQuery ($query = null): self {}

	public function getQueryString (): ?string {}

	/**
	 * @param string $queryString
	 */
	public function withQueryString (string $queryString): self {}

	public function withoutQueryString (): void {}

	public function getContentType (): ?string {}

	/**
	 * @param string $contentType
	 */
	public function withContentType (string $contentType): self {}

	public function withoutContentType (): void {}

	public function getContentLength (): ?int {}

	/**
	 * @param int $contentLength
	 */
	public function withContentLength (int $contentLength): self {}

	public function withoutContentLength (): void {}

	public function getGatewayInterface (): ?string {}

	/**
	 * @param string $gatewayInterface
	 */
	public function withGatewayInterface (string $gatewayInterface): self {}

	public function withoutGatewayInterface (): void {}

	public function getServerProtocol (): ?string {}

	/**
	 * @param string $serverProtocol
	 */
	public function withServerProtocol (string $serverProtocol): self {}

	public function withoutServerProtocol (): void {}

	/**
	 * @param string $protocolVersion
	 */
	public function withProtocolVersion (string $protocolVersion): self {}

	public function getServerSoftware (): ?string {}

	/**
	 * @param string $serverSoftware
	 */
	public function withServerSoftware (string $serverSoftware): self {}

	public function withoutServerSoftware (): void {}

	public function getRemoteAddr (): ?string {}

	/**
	 * @param string $remoteAddr
	 */
	public function withRemoteAddr (string $remoteAddr): self {}

	public function withoutRemoteAddr (): void {}

	public function getRemotePort (): ?int {}

	/**
	 * @param int $remotePort
	 */
	public function withRemotePort (int $remotePort): self {}

	public function withoutRemotePort (): void {}

	public function getServerAddr (): ?string {}

	/**
	 * @param string $serverAddr
	 */
	public function withServerAddr (string $serverAddr): self {}

	public function withoutServerAddr (): void {}

	public function getServerPort (): ?int {}

	/**
	 * @param int $serverPort
	 */
	public function withServerPort (int $serverPort): self {}

	public function withoutServerPort (): void {}

	public function getServerName (): ?string {}

	/**
	 * @param string $serverName
	 */
	public function withServerName (string $serverName): self {}

	public function withoutServerName (): void {}

	public function getRedirectStatus (): ?string {}

	/**
	 * @param string $redirectStatus
	 */
	public function withRedirectStatus (string $redirectStatus): self {}

	public function withoutRedirectStatus (): void {}

	/**
	 * @param string $name
	 */
	public function getHeader (string $name): ?string {}

	/**
	 * @param string $name
	 * @param string $value
	 */
	public function withHeader (string $name, string $value): self {}

	/**
	 * @param string $name
	 */
	public function withoutHeader (string $name): void {}

	public function getHeaders (): array {}

	/**
	 * @param array[] $headers
	 */
	public function withHeaders (array $headers): self {}

	/** @return $this */
	public function withBody ($body = null): Swoole\FastCGI\Message {}

	/**
	 * @param string $name
	 */
	protected static function convertHeaderNameToParamName (string $name) {}

	/**
	 * @param string $name
	 */
	protected static function convertParamNameToHeaderName (string $name) {}

	public function __toString (): string {}

	public function getKeepConn (): bool {}

	/**
	 * @param bool $keepConn
	 */
	public function withKeepConn (bool $keepConn): self {}

	/**
	 * @param string $name
	 */
	public function getParam (string $name): ?string {}

	/**
	 * @param string $name
	 * @param string $value
	 */
	public function withParam (string $name, string $value): self {}

	/**
	 * @param string $name
	 */
	public function withoutParam (string $name): self {}

	public function getParams (): array {}

	/**
	 * @param array[] $params
	 */
	public function withParams (array $params): self {}

	/**
	 * @param array[] $params
	 */
	public function withAddedParams (array $params): self {}

	public function getBody (): string {}

	public function getError (): string {}

	/**
	 * @param string $error
	 */
	public function withError (string $error): self {}

}

class HttpResponse extends \Swoole\FastCGI\Response  {
	/** @var int */
	protected $statusCode;
	/** @var string */
	protected $reasonPhrase;
	/** @var array */
	protected $headers;
	/** @var array */
	protected $headersMap;
	/** @var array */
	protected $setCookieHeaderLines;
	/** @var array */
	protected $params;
	/** @var string */
	protected $body;
	/** @var string */
	protected $error;


	/**
	 * @param array[] $records [optional]
	 */
	public function __construct (array $records = 'Array') {}

	public function getStatusCode (): int {}

	/**
	 * @param int $statusCode
	 */
	public function withStatusCode (int $statusCode): self {}

	public function getReasonPhrase (): string {}

	/**
	 * @param string $reasonPhrase
	 */
	public function withReasonPhrase (string $reasonPhrase): self {}

	/**
	 * @param string $name
	 */
	public function getHeader (string $name): ?string {}

	public function getHeaders (): array {}

	/**
	 * @param string $name
	 * @param string $value
	 */
	public function withHeader (string $name, string $value): self {}

	/**
	 * @param array[] $headers
	 */
	public function withHeaders (array $headers): self {}

	public function getSetCookieHeaderLines (): array {}

	/**
	 * @param string $value
	 */
	public function withSetCookieHeaderLine (string $value): self {}

	/**
	 * @param array[] $records
	 */
	public static function verify (array $records): bool {}

	/**
	 * @param string $name
	 */
	public function getParam (string $name): ?string {}

	/**
	 * @param string $name
	 * @param string $value
	 */
	public function withParam (string $name, string $value): self {}

	/**
	 * @param string $name
	 */
	public function withoutParam (string $name): self {}

	public function getParams (): array {}

	/**
	 * @param array[] $params
	 */
	public function withParams (array $params): self {}

	/**
	 * @param array[] $params
	 */
	public function withAddedParams (array $params): self {}

	public function getBody (): string {}

	/**
	 * @param mixed $body
	 */
	public function withBody ($body = null): self {}

	public function getError (): string {}

	/**
	 * @param string $error
	 */
	public function withError (string $error): self {}

}


}


namespace Swoole\Coroutine\FastCGI {

class Client  {
	/** @var int */
	protected $af;
	/** @var string */
	protected $host;
	/** @var int */
	protected $port;
	/** @var bool */
	protected $ssl;
	/** @var Socket */
	protected $socket;


	/**
	 * @param string $host
	 * @param int $port [optional]
	 * @param bool $ssl [optional]
	 */
	public function __construct (string $host, int $port = 0, bool $ssl = '') {}

	/**
     * @return HttpResponse|Response
     * @throws Exception
     */
	public function execute (\Swoole\FastCGI\Request $request, float $timeout = -1): Swoole\FastCGI\Response {}

	/**
	 * @param string $url
	 */
	public static function parseUrl (string $url): array {}

	/**
	 * @param string $url
	 * @param string $path
	 * @param mixed $data [optional]
	 * @param float $timeout [optional]
	 */
	public static function call (string $url, string $path, $data = null, float $timeout = -1): string {}

	/**
	 * @param int|null $errno [optional]
	 */
	protected function ioException (int|null $errno = null): void {}

}


}


namespace Swoole\Coroutine\FastCGI\Client {

class Exception extends \Swoole\Exception implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}


}


namespace Swoole\Coroutine\FastCGI {

class Proxy  {
	protected $host;
	protected $port;
	protected $timeout;
	protected $documentRoot;
	protected $https;
	protected $index;
	protected $params;
	protected $staticFileFilter;


	/**
	 * @param string $url
	 * @param string $documentRoot [optional]
	 */
	public function __construct (string $url, string $documentRoot = '/') {}

	/**
	 * @param float $timeout
	 */
	public function withTimeout (float $timeout): self {}

	/**
	 * @param bool $https
	 */
	public function withHttps (bool $https): self {}

	/**
	 * @param string $index
	 */
	public function withIndex (string $index): self {}

	/**
	 * @param string $name
	 */
	public function getParam (string $name): ?string {}

	/**
	 * @param string $name
	 * @param string $value
	 */
	public function withParam (string $name, string $value): self {}

	/**
	 * @param string $name
	 */
	public function withoutParam (string $name): self {}

	public function getParams (): array {}

	/**
	 * @param array[] $params
	 */
	public function withParams (array $params): self {}

	/**
	 * @param array[] $params
	 */
	public function withAddedParams (array $params): self {}

	/**
	 * @param callable|null $filter
	 */
	public function withStaticFileFilter (callable|null $filter = null): self {}

	/**
	 * @param mixed $userRequest
	 */
	public function translateRequest ($userRequest = null): Swoole\FastCGI\HttpRequest {}

	/**
	 * @param \Swoole\FastCGI\HttpResponse $response
	 * @param mixed $userResponse
	 */
	public function translateResponse (\Swoole\FastCGI\HttpResponse $response, $userResponse = null): void {}

	/**
	 * @param mixed $userRequest
	 * @param mixed $userResponse
	 */
	public function pass ($userRequest = null, $userResponse = null): void {}

	/**
	 * @param \Swoole\FastCGI\HttpRequest $request
	 * @param mixed $userResponse
	 */
	public function staticFileFiltrate (\Swoole\FastCGI\HttpRequest $request, $userResponse = null): bool {}

}


}


namespace Swoole\Process {

class Manager  {
	/**
     * @var Pool
     */
	protected $pool;
	/**
     * @var int
     */
	protected $ipcType;
	/**
     * @var int
     */
	protected $msgQueueKey;
	/**
     * @var array
     */
	protected $startFuncMap;


	/**
	 * @param int $ipcType [optional]
	 * @param int $msgQueueKey [optional]
	 */
	public function __construct (int $ipcType = 0, int $msgQueueKey = 0) {}

	/**
	 * @param callable $func
	 * @param bool $enableCoroutine [optional]
	 */
	public function add (callable $func, bool $enableCoroutine = ''): self {}

	/**
	 * @param int $workerNum
	 * @param callable $func
	 * @param bool $enableCoroutine [optional]
	 */
	public function addBatch (int $workerNum, callable $func, bool $enableCoroutine = ''): self {}

	public function start (): void {}

	/**
	 * @param int $ipcType
	 */
	public function setIPCType (int $ipcType): self {}

	public function getIPCType (): int {}

	/**
	 * @param int $msgQueueKey
	 */
	public function setMsgQueueKey (int $msgQueueKey): self {}

	public function getMsgQueueKey (): int {}

}


}


namespace Swoole\Server {

class Admin  {
	const SIZE_OF_ZVAL = 16;
	const SIZE_OF_ZEND_STRING = 32;
	const SIZE_OF_ZEND_OBJECT = 56;
	const SIZE_OF_ZEND_ARRAY = 56;

	private static $map;
	private static $allList;
	private static $postMethodList;
	private static $accessToken;


	/**
	 * @param \Swoole\Server $server
	 */
	public static function init (\Swoole\Server $server) {}

	public static function getAccessToken (): string {}

	/**
	 * @param \Swoole\Server $server
	 */
	public static function start (\Swoole\Server $server) {}

	/**
     * @param $server Server
     * @param mixed $msg
     * @return false|string
     */
	public static function handlerGetResources ($server = null, $msg = null) {}

	/**
     * @param $server Server
     * @param mixed $msg
     * @return false|string
     */
	public static function handlerGetWorkerInfo ($server = null, $msg = null) {}

	/**
     * @param mixed $server
     * @param mixed $msg
     * @return false|string
     */
	public static function handlerCloseSession ($server = null, $msg = null) {}

	/**
     * @param mixed $server
     * @param mixed $msg
     * @return false|string
     */
	public static function handlerGetTimerList ($server = null, $msg = null) {}

	/**
     * @param mixed $server
     * @param mixed $msg
     * @return false|string
     */
	public static function handlerGetCoroutineList ($server = null, $msg = null) {}

	/**
	 * @param mixed $server
	 * @param mixed $msg
	 */
	public static function handlerGetObjects ($server = null, $msg = null) {}

	/**
	 * @param mixed $server
	 * @param mixed $msg
	 */
	public static function handlerGetClassInfo ($server = null, $msg = null) {}

	/**
	 * @param mixed $server
	 * @param mixed $msg
	 */
	public static function handlerGetFunctionInfo ($server = null, $msg = null) {}

	/**
	 * @param mixed $server
	 * @param mixed $msg
	 */
	public static function handlerGetObjectByHandle ($server = null, $msg = null) {}

	/**
	 * @param mixed $server
	 * @param mixed $msg
	 */
	public static function handlerGetVersionInfo ($server = null, $msg = null) {}

	/**
	 * @param mixed $server
	 * @param mixed $msg
	 */
	public static function handlerGetDefinedFunctions ($server = null, $msg = null) {}

	/**
	 * @param mixed $server
	 * @param mixed $msg
	 */
	public static function handlerGetDeclaredClasses ($server = null, $msg = null) {}

	/**
	 * @param mixed $server
	 * @param mixed $msg
	 */
	public static function handlerGetServerMemoryUsage ($server = null, $msg = null) {}

	/**
	 * @param mixed $server
	 * @param mixed $msg
	 */
	public static function handlerGetServerCpuUsage ($server = null, $msg = null) {}

	/**
	 * @param mixed $server
	 * @param mixed $msg
	 */
	public static function handlerGetStaticPropertyValue ($server = null, $msg = null) {}

	/**
	 * @param \Swoole\Server $server
	 * @param array[] $list
	 */
	private static function handlerMulti (\Swoole\Server $server, array $list) {}

	/**
	 * @param \Swoole\Server $server
	 * @param \Swoole\StringObject $process
	 * @param mixed $cmd
	 * @param mixed $data
	 * @param bool $json_decode [optional]
	 */
	private static function handlerGetAll (\Swoole\Server $server, \Swoole\StringObject $process, $cmd = null, $data = null, bool $json_decode = 1) {}

	/**
	 * @param mixed $cmd
	 * @param mixed $data
	 * @param \Swoole\Server $server
	 * @param bool $json_decode [optional]
	 */
	private static function handlerGetMaster ($cmd = null, $data = null, \Swoole\Server $server, bool $json_decode = '') {}

	/**
	 * @param mixed $cmd
	 * @param mixed $data
	 * @param \Swoole\Server $server
	 * @param bool $json_decode [optional]
	 */
	private static function handlerGetManager ($cmd = null, $data = null, \Swoole\Server $server, bool $json_decode = '') {}

	/**
	 * @param mixed $cmd
	 * @param mixed $data
	 * @param \Swoole\Server $server
	 * @param bool $json_decode [optional]
	 */
	private static function handlerGetAllReactor ($cmd = null, $data = null, \Swoole\Server $server, bool $json_decode = '') {}

	/**
	 * @param mixed $cmd
	 * @param mixed $data
	 * @param \Swoole\Server $server
	 * @param bool $json_decode [optional]
	 */
	private static function handlerGetAllWorker ($cmd = null, $data = null, \Swoole\Server $server, bool $json_decode = '') {}

	/**
	 * @param mixed $cmd
	 * @param mixed $data
	 * @param \Swoole\Server $server
	 * @param bool $json_decode [optional]
	 */
	private static function handlerGetAllTaskWorker ($cmd = null, $data = null, \Swoole\Server $server, bool $json_decode = '') {}

	/**
	 * @param mixed $pid
	 */
	private static function getProcessCpuUsage ($pid = null) {}

	/**
	 * @param mixed $pid [optional]
	 */
	private static function getProcessMemoryRealUsage ($pid = null) {}

	/**
	 * @param mixed $pid [optional]
	 */
	private static function getProcessStatus ($pid = null) {}

	/**
	 * @param array[] $a
	 */
	private static function getArrayMemorySize (array $a): int {}

	/**
	 * @param string $s
	 */
	private static function getStringMemorySize (string $s): int {}

	/**
	 * @param object $o
	 */
	private static function getObjectMemorySize (object $o): int {}

	/**
	 * @param \Swoole\Server $server
	 */
	private static function haveMasterProcess (\Swoole\Server $server): bool {}

	/**
	 * @param \Swoole\Server $server
	 */
	private static function haveManagerProcess (\Swoole\Server $server): bool {}

	/**
	 * @param mixed $data
	 * @param mixed $code [optional]
	 */
	private static function json ($data = null, $code = null) {}

}

class Helper  {
	const STATS_TIMER_INTERVAL_TIME = 1000;
	const GLOBAL_OPTIONS = array (
  'debug_mode' => true,
  'trace_flags' => true,
  'log_file' => true,
  'log_level' => true,
  'log_date_format' => true,
  'log_date_with_microseconds' => true,
  'log_rotation' => true,
  'display_errors' => true,
  'dns_server' => true,
  'socket_dns_timeout' => true,
  'socket_connect_timeout' => true,
  'socket_write_timeout' => true,
  'socket_send_timeout' => true,
  'socket_read_timeout' => true,
  'socket_recv_timeout' => true,
  'socket_buffer_size' => true,
  'socket_timeout' => true,
  'http2_header_table_size' => true,
  'http2_enable_push' => true,
  'http2_max_concurrent_streams' => true,
  'http2_init_window_size' => true,
  'http2_max_frame_size' => true,
  'http2_max_header_list_size' => true,
);
	const SERVER_OPTIONS = array (
  'chroot' => true,
  'user' => true,
  'group' => true,
  'daemonize' => true,
  'pid_file' => true,
  'reactor_num' => true,
  'single_thread' => true,
  'worker_num' => true,
  'max_wait_time' => true,
  'max_queued_bytes' => true,
  'max_concurrency' => true,
  'worker_max_concurrency' => true,
  'enable_coroutine' => true,
  'send_timeout' => true,
  'dispatch_mode' => true,
  'send_yield' => true,
  'dispatch_func' => true,
  'discard_timeout_request' => true,
  'enable_unsafe_event' => true,
  'enable_delay_receive' => true,
  'enable_reuse_port' => true,
  'task_use_object' => true,
  'task_object' => true,
  'event_object' => true,
  'task_enable_coroutine' => true,
  'task_worker_num' => true,
  'task_ipc_mode' => true,
  'task_tmpdir' => true,
  'task_max_request' => true,
  'task_max_request_grace' => true,
  'max_connection' => true,
  'max_conn' => true,
  'start_session_id' => true,
  'heartbeat_check_interval' => true,
  'heartbeat_idle_time' => true,
  'max_request' => true,
  'max_request_grace' => true,
  'reload_async' => true,
  'open_cpu_affinity' => true,
  'cpu_affinity_ignore' => true,
  'http_parse_cookie' => true,
  'http_parse_post' => true,
  'http_parse_files' => true,
  'http_compression' => true,
  'http_compression_level' => true,
  'compression_level' => true,
  'http_gzip_level' => true,
  'http_compression_min_length' => true,
  'compression_min_length' => true,
  'websocket_compression' => true,
  'upload_tmp_dir' => true,
  'upload_max_filesize' => true,
  'enable_static_handler' => true,
  'document_root' => true,
  'http_autoindex' => true,
  'http_index_files' => true,
  'http_compression_types' => true,
  'compression_types' => true,
  'static_handler_locations' => true,
  'input_buffer_size' => true,
  'buffer_input_size' => true,
  'output_buffer_size' => true,
  'buffer_output_size' => true,
  'message_queue_key' => true,
);
	const PORT_OPTIONS = array (
  'ssl_cert_file' => true,
  'ssl_key_file' => true,
  'backlog' => true,
  'socket_buffer_size' => true,
  'kernel_socket_recv_buffer_size' => true,
  'kernel_socket_send_buffer_size' => true,
  'heartbeat_idle_time' => true,
  'buffer_high_watermark' => true,
  'buffer_low_watermark' => true,
  'open_tcp_nodelay' => true,
  'tcp_defer_accept' => true,
  'open_tcp_keepalive' => true,
  'open_eof_check' => true,
  'open_eof_split' => true,
  'package_eof' => true,
  'open_http_protocol' => true,
  'open_websocket_protocol' => true,
  'websocket_subprotocol' => true,
  'open_websocket_close_frame' => true,
  'open_websocket_ping_frame' => true,
  'open_websocket_pong_frame' => true,
  'open_http2_protocol' => true,
  'open_mqtt_protocol' => true,
  'open_redis_protocol' => true,
  'max_idle_time' => true,
  'tcp_keepidle' => true,
  'tcp_keepinterval' => true,
  'tcp_keepcount' => true,
  'tcp_user_timeout' => true,
  'tcp_fastopen' => true,
  'open_length_check' => true,
  'package_length_type' => true,
  'package_length_offset' => true,
  'package_body_offset' => true,
  'package_body_start' => true,
  'package_length_func' => true,
  'package_max_length' => true,
  'ssl_compress' => true,
  'ssl_protocols' => true,
  'ssl_verify_peer' => true,
  'ssl_allow_self_signed' => true,
  'ssl_client_cert_file' => true,
  'ssl_verify_depth' => true,
  'ssl_prefer_server_ciphers' => true,
  'ssl_ciphers' => true,
  'ssl_ecdh_curve' => true,
  'ssl_dhparam' => true,
  'ssl_sni_certs' => true,
);
	const AIO_OPTIONS = array (
  'aio_core_worker_num' => true,
  'aio_worker_num' => true,
  'aio_max_wait_time' => true,
  'aio_max_idle_time' => true,
  'enable_signalfd' => true,
  'wait_signal' => true,
  'dns_cache_refresh_time' => true,
  'thread_num' => true,
  'min_thread_num' => true,
  'max_thread_num' => true,
  'socket_dontwait' => true,
  'dns_lookup_random' => true,
  'use_async_resolver' => true,
  'enable_coroutine' => true,
);
	const COROUTINE_OPTIONS = array (
  'max_coro_num' => true,
  'max_coroutine' => true,
  'enable_deadlock_check' => true,
  'hook_flags' => true,
  'enable_preemptive_scheduler' => true,
  'c_stack_size' => true,
  'stack_size' => true,
  'name_resolver' => true,
  'dns_cache_expire' => true,
  'dns_cache_capacity' => true,
  'max_concurrency' => true,
);
	const HELPER_OPTIONS = array (
  'stats_file' => true,
  'stats_timer_interval' => true,
  'admin_server' => true,
);


	/**
	 * @param array[] $input_options
	 */
	public static function checkOptions (array $input_options) {}

	/**
	 * @param \Swoole\Server $server
	 */
	public static function onBeforeStart (\Swoole\Server $server) {}

	/**
	 * @param \Swoole\Server $server
	 */
	public static function onBeforeShutdown (\Swoole\Server $server) {}

	/**
	 * @param \Swoole\Server $server
	 * @param int $workerId
	 */
	public static function onWorkerStart (\Swoole\Server $server, int $workerId) {}

	/**
	 * @param \Swoole\Server $server
	 * @param int $workerId
	 */
	public static function onWorkerExit (\Swoole\Server $server, int $workerId) {}

	/**
	 * @param \Swoole\Server $server
	 * @param int $workerId
	 */
	public static function onWorkerStop (\Swoole\Server $server, int $workerId) {}

	/**
	 * @param \Swoole\Server $server
	 */
	public static function onStart (\Swoole\Server $server) {}

	/**
	 * @param \Swoole\Server $server
	 */
	public static function onShutdown (\Swoole\Server $server) {}

	/**
	 * @param \Swoole\Server $server
	 */
	public static function onBeforeReload (\Swoole\Server $server) {}

	/**
	 * @param \Swoole\Server $server
	 */
	public static function onAfterReload (\Swoole\Server $server) {}

	/**
	 * @param \Swoole\Server $server
	 */
	public static function onManagerStart (\Swoole\Server $server) {}

	/**
	 * @param \Swoole\Server $server
	 */
	public static function onManagerStop (\Swoole\Server $server) {}

	/**
	 * @param \Swoole\Server $server
	 */
	public static function onWorkerError (\Swoole\Server $server) {}

}


}


namespace Swoole {

abstract class NameResolver  {
	protected $baseUrl;
	protected $prefix;
	protected $info;
	private $filter_fn;


	/**
	 * @param mixed $url
	 * @param mixed $prefix [optional]
	 */
	public function __construct ($url = null, $prefix = null) {}

	/**
	 * @param string $name
	 * @param string $ip
	 * @param int $port
	 * @param array[] $options [optional]
	 */
	abstract public function join (string $name, string $ip, int $port, array $options = 'Array'): bool

	/**
	 * @param string $name
	 * @param string $ip
	 * @param int $port
	 */
	abstract public function leave (string $name, string $ip, int $port): bool

	/**
	 * @param string $name
	 */
	abstract public function getCluster (string $name): ?Swoole\NameResolver\Cluster

	/**
	 * @param callable $fn
	 */
	public function withFilter (callable $fn): self {}

	public function getFilter () {}

	public function hasFilter (): bool {}

	/**
     * return string: final result, non-empty string must be a valid IP address,
     * and an empty string indicates name lookup failed, and lookup operation will not continue.
     * return Cluster: has multiple nodes and failover is possible
     * return false or null: try another name resolver
     * @return null|Cluster|false|string
     */
	public function lookup (string $name) {}

	/**
     * !!! The host MUST BE IP ADDRESS
     * @param mixed $url
     */
	protected function checkServerUrl ($url = null) {}

	/**
     * @param $r ClientProxy
     * @param mixed $url
     * @return bool
     */
	protected function checkResponse ($r = null, $url = null) {}

}


}


namespace Swoole\NameResolver {

class Exception extends \RuntimeException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class Cluster  {
	/**
     * @var array
     */
	private $nodes;


	/**
     * @throws Exception
     */
	public function add (string $host, int $port, int $weight = 100): void {}

	/**
     * @return false|string
     */
	public function pop () {}

	public function count (): int {}

}

class Redis extends \Swoole\NameResolver  {
	private $serverHost;
	private $serverPort;
	protected $baseUrl;
	protected $prefix;
	protected $info;


	/**
	 * @param mixed $url
	 * @param mixed $prefix [optional]
	 */
	public function __construct ($url = null, $prefix = null) {}

	/**
	 * @param string $name
	 * @param string $ip
	 * @param int $port
	 * @param array[] $options [optional]
	 */
	public function join (string $name, string $ip, int $port, array $options = 'Array'): bool {}

	/**
	 * @param string $name
	 * @param string $ip
	 * @param int $port
	 */
	public function leave (string $name, string $ip, int $port): bool {}

	/**
	 * @param string $name
	 */
	public function getCluster (string $name): ?Swoole\NameResolver\Cluster {}

	protected function connect () {}

	/**
	 * @param callable $fn
	 */
	public function withFilter (callable $fn): self {}

	public function getFilter () {}

	public function hasFilter (): bool {}

	/**
     * return string: final result, non-empty string must be a valid IP address,
     * and an empty string indicates name lookup failed, and lookup operation will not continue.
     * return Cluster: has multiple nodes and failover is possible
     * return false or null: try another name resolver
     * @return null|Cluster|false|string
     */
	public function lookup (string $name) {}

	/**
     * !!! The host MUST BE IP ADDRESS
     * @param mixed $url
     */
	protected function checkServerUrl ($url = null) {}

	/**
     * @param $r ClientProxy
     * @param mixed $url
     * @return bool
     */
	protected function checkResponse ($r = null, $url = null) {}

}

class Nacos extends \Swoole\NameResolver  {
	protected $baseUrl;
	protected $prefix;
	protected $info;


	/**
     * @throws Coroutine\Http\Client\Exception|Exception
     */
	public function join (string $name, string $ip, int $port, array $options = 'Array'): bool {}

	/**
     * @throws Coroutine\Http\Client\Exception|Exception
     */
	public function leave (string $name, string $ip, int $port): bool {}

	/**
     * @throws Coroutine\Http\Client\Exception|Exception|\Swoole\Exception
     */
	public function getCluster (string $name): ?Swoole\NameResolver\Cluster {}

	/**
	 * @param mixed $url
	 * @param mixed $prefix [optional]
	 */
	public function __construct ($url = null, $prefix = null) {}

	/**
	 * @param callable $fn
	 */
	public function withFilter (callable $fn): self {}

	public function getFilter () {}

	public function hasFilter (): bool {}

	/**
     * return string: final result, non-empty string must be a valid IP address,
     * and an empty string indicates name lookup failed, and lookup operation will not continue.
     * return Cluster: has multiple nodes and failover is possible
     * return false or null: try another name resolver
     * @return null|Cluster|false|string
     */
	public function lookup (string $name) {}

	/**
     * !!! The host MUST BE IP ADDRESS
     * @param mixed $url
     */
	protected function checkServerUrl ($url = null) {}

	/**
     * @param $r ClientProxy
     * @param mixed $url
     * @return bool
     */
	protected function checkResponse ($r = null, $url = null) {}

}

class Consul extends \Swoole\NameResolver  {
	protected $baseUrl;
	protected $prefix;
	protected $info;


	/**
	 * @param string $name
	 * @param string $ip
	 * @param int $port
	 * @param array[] $options [optional]
	 */
	public function join (string $name, string $ip, int $port, array $options = 'Array'): bool {}

	/**
	 * @param string $name
	 * @param string $ip
	 * @param int $port
	 */
	public function leave (string $name, string $ip, int $port): bool {}

	/**
	 * @param string $name
	 * @param string $ip
	 * @param int $port
	 */
	public function enableMaintenanceMode (string $name, string $ip, int $port): bool {}

	/**
	 * @param string $name
	 */
	public function getCluster (string $name): ?Swoole\NameResolver\Cluster {}

	/**
	 * @param string $name
	 * @param string $ip
	 * @param int $port
	 */
	private function getServiceId (string $name, string $ip, int $port): string {}

	/**
	 * @param mixed $url
	 * @param mixed $prefix [optional]
	 */
	public function __construct ($url = null, $prefix = null) {}

	/**
	 * @param callable $fn
	 */
	public function withFilter (callable $fn): self {}

	public function getFilter () {}

	public function hasFilter (): bool {}

	/**
     * return string: final result, non-empty string must be a valid IP address,
     * and an empty string indicates name lookup failed, and lookup operation will not continue.
     * return Cluster: has multiple nodes and failover is possible
     * return false or null: try another name resolver
     * @return null|Cluster|false|string
     */
	public function lookup (string $name) {}

	/**
     * !!! The host MUST BE IP ADDRESS
     * @param mixed $url
     */
	protected function checkServerUrl ($url = null) {}

	/**
     * @param $r ClientProxy
     * @param mixed $url
     * @return bool
     */
	protected function checkResponse ($r = null, $url = null) {}

}


}


namespace {

class SwooleLibrary  {
	public static $options;

}


}


namespace Swoole\Coroutine {

class WaitGroup  {
	protected $chan;
	protected $count;
	protected $waiting;


	/**
	 * @param int $delta [optional]
	 */
	public function __construct (int $delta = 0) {}

	/**
	 * @param int $delta [optional]
	 */
	public function add (int $delta = 1): void {}

	public function done (): void {}

	/**
	 * @param float $timeout [optional]
	 */
	public function wait (float $timeout = -1): bool {}

	public function count (): int {}

}

class Server  {
	/** @var string */
	public $host;
	/** @var int */
	public $port;
	/** @var int */
	public $type;
	/** @var int */
	public $fd;
	/** @var int */
	public $errCode;
	/** @var array */
	public $setting;
	/** @var bool */
	protected $running;
	/** @var null|callable */
	protected $fn;
	/** @var Socket */
	protected $socket;


	/**
     * Server constructor.
     * @throws Exception
     */
	public function __construct (string $host, int $port = 0, bool $ssl = '', bool $reuse_port = '') {}

	/**
	 * @param array[] $setting
	 */
	public function set (array $setting): void {}

	/**
	 * @param callable $fn
	 */
	public function handle (callable $fn): void {}

	public function shutdown (): bool {}

	public function start (): bool {}

}


}


namespace Swoole\Coroutine\Server {

class Connection  {
	protected $socket;


	/**
	 * @param \Swoole\Coroutine\Socket $conn
	 */
	public function __construct (\Swoole\Coroutine\Socket $conn) {}

	/**
	 * @param float $timeout [optional]
	 */
	public function recv (float $timeout = 0) {}

	/**
	 * @param string $data
	 */
	public function send (string $data) {}

	public function close (): bool {}

	public function exportSocket (): Swoole\Coroutine\Socket {}

}


}


namespace Swoole\Coroutine\FastCGI {

class Client  {
	/** @var int */
	protected $af;
	/** @var string */
	protected $host;
	/** @var int */
	protected $port;
	/** @var bool */
	protected $ssl;
	/** @var Socket */
	protected $socket;


	/**
	 * @param string $host
	 * @param int $port [optional]
	 * @param bool $ssl [optional]
	 */
	public function __construct (string $host, int $port = 0, bool $ssl = '') {}

	/**
     * @return HttpResponse|Response
     * @throws Exception
     */
	public function execute (\Swoole\FastCGI\Request $request, float $timeout = -1): Swoole\FastCGI\Response {}

	/**
	 * @param string $url
	 */
	public static function parseUrl (string $url): array {}

	/**
	 * @param string $url
	 * @param string $path
	 * @param mixed $data [optional]
	 * @param float $timeout [optional]
	 */
	public static function call (string $url, string $path, $data = null, float $timeout = -1): string {}

	/**
	 * @param int|null $errno [optional]
	 */
	protected function ioException (int|null $errno = null): void {}

}


}


namespace Swoole\Coroutine\FastCGI\Client {

class Exception extends \Swoole\Exception implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}


}


namespace Swoole\Coroutine\FastCGI {

class Proxy  {
	protected $host;
	protected $port;
	protected $timeout;
	protected $documentRoot;
	protected $https;
	protected $index;
	protected $params;
	protected $staticFileFilter;


	/**
	 * @param string $url
	 * @param string $documentRoot [optional]
	 */
	public function __construct (string $url, string $documentRoot = '/') {}

	/**
	 * @param float $timeout
	 */
	public function withTimeout (float $timeout): self {}

	/**
	 * @param bool $https
	 */
	public function withHttps (bool $https): self {}

	/**
	 * @param string $index
	 */
	public function withIndex (string $index): self {}

	/**
	 * @param string $name
	 */
	public function getParam (string $name): ?string {}

	/**
	 * @param string $name
	 * @param string $value
	 */
	public function withParam (string $name, string $value): self {}

	/**
	 * @param string $name
	 */
	public function withoutParam (string $name): self {}

	public function getParams (): array {}

	/**
	 * @param array[] $params
	 */
	public function withParams (array $params): self {}

	/**
	 * @param array[] $params
	 */
	public function withAddedParams (array $params): self {}

	/**
	 * @param callable|null $filter
	 */
	public function withStaticFileFilter (callable|null $filter = null): self {}

	/**
	 * @param mixed $userRequest
	 */
	public function translateRequest ($userRequest = null): Swoole\FastCGI\HttpRequest {}

	/**
	 * @param \Swoole\FastCGI\HttpResponse $response
	 * @param mixed $userResponse
	 */
	public function translateResponse (\Swoole\FastCGI\HttpResponse $response, $userResponse = null): void {}

	/**
	 * @param mixed $userRequest
	 * @param mixed $userResponse
	 */
	public function pass ($userRequest = null, $userResponse = null): void {}

	/**
	 * @param \Swoole\FastCGI\HttpRequest $request
	 * @param mixed $userResponse
	 */
	public function staticFileFiltrate (\Swoole\FastCGI\HttpRequest $request, $userResponse = null): bool {}

}


}


namespace Swoole\Process {

class Manager  {
	/**
     * @var Pool
     */
	protected $pool;
	/**
     * @var int
     */
	protected $ipcType;
	/**
     * @var int
     */
	protected $msgQueueKey;
	/**
     * @var array
     */
	protected $startFuncMap;


	/**
	 * @param int $ipcType [optional]
	 * @param int $msgQueueKey [optional]
	 */
	public function __construct (int $ipcType = 0, int $msgQueueKey = 0) {}

	/**
	 * @param callable $func
	 * @param bool $enableCoroutine [optional]
	 */
	public function add (callable $func, bool $enableCoroutine = ''): self {}

	/**
	 * @param int $workerNum
	 * @param callable $func
	 * @param bool $enableCoroutine [optional]
	 */
	public function addBatch (int $workerNum, callable $func, bool $enableCoroutine = ''): self {}

	public function start (): void {}

	/**
	 * @param int $ipcType
	 */
	public function setIPCType (int $ipcType): self {}

	public function getIPCType (): int {}

	/**
	 * @param int $msgQueueKey
	 */
	public function setMsgQueueKey (int $msgQueueKey): self {}

	public function getMsgQueueKey (): int {}

}


}


namespace PsrExt\Cache {

interface CacheException  {
}

interface CacheItemInterface  {

	abstract public function getKey ()

	abstract public function get ()

	abstract public function isHit ()

	/**
	 * @param mixed $value
	 */
	abstract public function set ($value = null)

	/**
	 * @param mixed $expiration
	 */
	abstract public function expiresAt ($expiration = null)

	/**
	 * @param mixed $time
	 */
	abstract public function expiresAfter ($time = null)

}

interface CacheItemPoolInterface  {

	/**
	 * @param mixed $key
	 */
	abstract public function getItem ($key = null)

	/**
	 * @param array[] $keys [optional]
	 */
	abstract public function getItems (array $keys = null)

	/**
	 * @param mixed $key
	 */
	abstract public function hasItem ($key = null)

	abstract public function clear ()

	/**
	 * @param mixed $key
	 */
	abstract public function deleteItem ($key = null)

	/**
	 * @param array[] $keys
	 */
	abstract public function deleteItems (array $keys)

	/**
	 * @param Psr\Cache\CacheItemInterface $logger
	 */
	abstract public function save (Psr\Cache\CacheItemInterface $logger)

	/**
	 * @param Psr\Cache\CacheItemInterface $logger
	 */
	abstract public function saveDeferred (Psr\Cache\CacheItemInterface $logger)

	abstract public function commit ()

}

interface InvalidArgumentException extends \PsrExt\Cache\CacheException {
}


}


namespace PsrExt\Container {

interface ContainerExceptionInterface  {
}

interface ContainerInterface  {

	/**
	 * @param string $id
	 */
	abstract public function get (string $id)

	/**
	 * @param string $id
	 */
	abstract public function has (string $id)

}

interface NotFoundExceptionInterface extends \PsrExt\Container\ContainerExceptionInterface {
}


}


namespace PsrExt\Http\Message {

interface MessageInterface  {

	abstract public function getProtocolVersion ()

	/**
	 * @param mixed $version
	 */
	abstract public function withProtocolVersion ($version = null)

	abstract public function getHeaders ()

	/**
	 * @param mixed $name
	 */
	abstract public function hasHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeaderLine ($name = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withAddedHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 */
	abstract public function withoutHeader ($name = null)

	abstract public function getBody ()

	/**
	 * @param Psr\Http\Message\StreamInterface $body
	 */
	abstract public function withBody (Psr\Http\Message\StreamInterface $body)

}

interface RequestInterface extends \PsrExt\Http\Message\MessageInterface {

	abstract public function getRequestTarget ()

	/**
	 * @param mixed $requestTarget
	 */
	abstract public function withRequestTarget ($requestTarget = null)

	abstract public function getMethod ()

	/**
	 * @param mixed $method
	 */
	abstract public function withMethod ($method = null)

	abstract public function getUri ()

	/**
	 * @param Psr\Http\Message\UriInterface $uri
	 * @param mixed $preserveHost [optional]
	 */
	abstract public function withUri (Psr\Http\Message\UriInterface $uri, $preserveHost = null)

	abstract public function getProtocolVersion ()

	/**
	 * @param mixed $version
	 */
	abstract public function withProtocolVersion ($version = null)

	abstract public function getHeaders ()

	/**
	 * @param mixed $name
	 */
	abstract public function hasHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeaderLine ($name = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withAddedHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 */
	abstract public function withoutHeader ($name = null)

	abstract public function getBody ()

	/**
	 * @param Psr\Http\Message\StreamInterface $body
	 */
	abstract public function withBody (Psr\Http\Message\StreamInterface $body)

}

interface ResponseInterface extends \PsrExt\Http\Message\MessageInterface {

	abstract public function getStatusCode ()

	/**
	 * @param mixed $code
	 * @param mixed $reasonPhrase [optional]
	 */
	abstract public function withStatus ($code = null, $reasonPhrase = null)

	abstract public function getReasonPhrase ()

	abstract public function getProtocolVersion ()

	/**
	 * @param mixed $version
	 */
	abstract public function withProtocolVersion ($version = null)

	abstract public function getHeaders ()

	/**
	 * @param mixed $name
	 */
	abstract public function hasHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeaderLine ($name = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withAddedHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 */
	abstract public function withoutHeader ($name = null)

	abstract public function getBody ()

	/**
	 * @param Psr\Http\Message\StreamInterface $body
	 */
	abstract public function withBody (Psr\Http\Message\StreamInterface $body)

}

interface ServerRequestInterface extends \PsrExt\Http\Message\RequestInterface, \PsrExt\Http\Message\MessageInterface {

	abstract public function getServerParams ()

	abstract public function getCookieParams ()

	/**
	 * @param array[] $cookies
	 */
	abstract public function withCookieParams (array $cookies)

	abstract public function getQueryParams ()

	/**
	 * @param array[] $query
	 */
	abstract public function withQueryParams (array $query)

	abstract public function getUploadedFiles ()

	/**
	 * @param array[] $uploadedFiles
	 */
	abstract public function withUploadedFiles (array $uploadedFiles)

	abstract public function getParsedBody ()

	/**
	 * @param mixed $parsedBody
	 */
	abstract public function withParsedBody ($parsedBody = null)

	abstract public function getAttributes ()

	/**
	 * @param mixed $name
	 * @param mixed $default [optional]
	 */
	abstract public function getAttribute ($name = null, $default = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withAttribute ($name = null, $value = null)

	/**
	 * @param mixed $name
	 */
	abstract public function withoutAttribute ($name = null)

	abstract public function getRequestTarget ()

	/**
	 * @param mixed $requestTarget
	 */
	abstract public function withRequestTarget ($requestTarget = null)

	abstract public function getMethod ()

	/**
	 * @param mixed $method
	 */
	abstract public function withMethod ($method = null)

	abstract public function getUri ()

	/**
	 * @param Psr\Http\Message\UriInterface $uri
	 * @param mixed $preserveHost [optional]
	 */
	abstract public function withUri (Psr\Http\Message\UriInterface $uri, $preserveHost = null)

	abstract public function getProtocolVersion ()

	/**
	 * @param mixed $version
	 */
	abstract public function withProtocolVersion ($version = null)

	abstract public function getHeaders ()

	/**
	 * @param mixed $name
	 */
	abstract public function hasHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeaderLine ($name = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withAddedHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 */
	abstract public function withoutHeader ($name = null)

	abstract public function getBody ()

	/**
	 * @param Psr\Http\Message\StreamInterface $body
	 */
	abstract public function withBody (Psr\Http\Message\StreamInterface $body)

}

interface StreamInterface extends \Stringable {

	abstract public function __toString (): string

	abstract public function close ()

	abstract public function detach ()

	abstract public function getSize ()

	abstract public function tell ()

	abstract public function eof ()

	abstract public function isSeekable ()

	/**
	 * @param mixed $offset
	 * @param mixed $whence [optional]
	 */
	abstract public function seek ($offset = null, $whence = null)

	abstract public function rewind ()

	abstract public function isWritable ()

	/**
	 * @param mixed $string
	 */
	abstract public function write ($string = null)

	abstract public function isReadable ()

	/**
	 * @param mixed $length
	 */
	abstract public function read ($length = null)

	abstract public function getContents ()

	/**
	 * @param mixed $key [optional]
	 */
	abstract public function getMetadata ($key = null)

}

interface UploadedFileInterface  {

	abstract public function getStream ()

	/**
	 * @param mixed $targetPath
	 */
	abstract public function moveTo ($targetPath = null)

	abstract public function getSize ()

	abstract public function getError ()

	abstract public function getClientFilename ()

	abstract public function getClientMediaType ()

}

interface UriInterface extends \Stringable {

	abstract public function getScheme ()

	abstract public function getAuthority ()

	abstract public function getUserInfo ()

	abstract public function getHost ()

	abstract public function getPort ()

	abstract public function getPath ()

	abstract public function getQuery ()

	abstract public function getFragment ()

	/**
	 * @param mixed $scheme
	 */
	abstract public function withScheme ($scheme = null)

	/**
	 * @param mixed $user
	 * @param mixed $password [optional]
	 */
	abstract public function withUserInfo ($user = null, $password = null)

	/**
	 * @param mixed $host
	 */
	abstract public function withHost ($host = null)

	/**
	 * @param mixed $port
	 */
	abstract public function withPort ($port = null)

	/**
	 * @param mixed $path
	 */
	abstract public function withPath ($path = null)

	/**
	 * @param mixed $query
	 */
	abstract public function withQuery ($query = null)

	/**
	 * @param mixed $fragment
	 */
	abstract public function withFragment ($fragment = null)

	abstract public function __toString (): string

}


}


namespace PsrExt\Link {

interface LinkInterface  {

	abstract public function getHref ()

	abstract public function isTemplated ()

	abstract public function getRels ()

	abstract public function getAttributes ()

}

interface LinkProviderInterface  {

	abstract public function getLinks ()

	/**
	 * @param mixed $rel
	 */
	abstract public function getLinksByRel ($rel = null)

}

interface EvolvableLinkInterface extends \PsrExt\Link\LinkInterface {

	/**
	 * @param mixed $href
	 */
	abstract public function withHref ($href = null)

	/**
	 * @param mixed $rel
	 */
	abstract public function withRel ($rel = null)

	/**
	 * @param mixed $rel
	 */
	abstract public function withoutRel ($rel = null)

	/**
	 * @param mixed $attribute
	 * @param mixed $value
	 */
	abstract public function withAttribute ($attribute = null, $value = null)

	/**
	 * @param mixed $attribute
	 */
	abstract public function withoutAttribute ($attribute = null)

	abstract public function getHref ()

	abstract public function isTemplated ()

	abstract public function getRels ()

	abstract public function getAttributes ()

}

interface EvolvableLinkProviderInterface extends \PsrExt\Link\LinkProviderInterface {

	/**
	 * @param Psr\Link\LinkInterface $link
	 */
	abstract public function withLink (Psr\Link\LinkInterface $link)

	/**
	 * @param Psr\Link\LinkInterface $link
	 */
	abstract public function withoutLink (Psr\Link\LinkInterface $link)

	abstract public function getLinks ()

	/**
	 * @param mixed $rel
	 */
	abstract public function getLinksByRel ($rel = null)

}


}


namespace PsrExt\Log {

interface LoggerInterface  {

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function emergency ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function alert ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function critical ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function error ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function warning ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function notice ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function info ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function debug ($message = null, array $context = 'Array')

	/**
	 * @param mixed $level
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function log ($level = null, $message = null, array $context = 'Array')

}

interface LoggerAwareInterface  {

	/**
	 * @param Psr\Log\LoggerInterface $logger
	 */
	abstract public function setLogger (Psr\Log\LoggerInterface $logger)

}


}


namespace PsrExt\SimpleCache {

interface CacheException  {
}

interface CacheInterface  {

	/**
	 * @param mixed $key
	 * @param mixed $default [optional]
	 */
	abstract public function get ($key = null, $default = null)

	/**
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $ttl [optional]
	 */
	abstract public function set ($key = null, $value = null, $ttl = null)

	/**
	 * @param mixed $key
	 */
	abstract public function delete ($key = null)

	abstract public function clear ()

	/**
	 * @param mixed $keys
	 * @param mixed $default [optional]
	 */
	abstract public function getMultiple ($keys = null, $default = null)

	/**
	 * @param mixed $values
	 * @param mixed $ttl [optional]
	 */
	abstract public function setMultiple ($values = null, $ttl = null)

	/**
	 * @param mixed $keys
	 */
	abstract public function deleteMultiple ($keys = null)

	/**
	 * @param mixed $key
	 */
	abstract public function has ($key = null)

}

interface InvalidArgumentException extends \PsrExt\SimpleCache\CacheException {
}


}


namespace PsrExt\Http\Server {

interface RequestHandlerInterface  {

	/**
	 * @param Psr\Http\Message\ServerRequestInterface $request
	 */
	abstract public function handle (Psr\Http\Message\ServerRequestInterface $request): Psr\Http\Message\ResponseInterface

}

interface MiddlewareInterface  {

	/**
	 * @param Psr\Http\Message\ServerRequestInterface $request
	 * @param Psr\Http\Server\RequestHandlerInterface $handler
	 */
	abstract public function process (Psr\Http\Message\ServerRequestInterface $request, Psr\Http\Server\RequestHandlerInterface $handler): Psr\Http\Message\ResponseInterface

}


}


namespace PsrExt\Http\Message {

interface RequestFactoryInterface  {

	/**
	 * @param string $method
	 * @param mixed $uri
	 */
	abstract public function createRequest (string $method, $uri = null): Psr\Http\Message\RequestInterface

}

interface ResponseFactoryInterface  {

	/**
	 * @param int $code [optional]
	 * @param string $reasonPhrase [optional]
	 */
	abstract public function createResponse (int $code = null, string $reasonPhrase = null): Psr\Http\Message\ResponseInterface

}

interface ServerRequestFactoryInterface  {

	/**
	 * @param string $method
	 * @param mixed $uri
	 * @param array[] $serverParams [optional]
	 */
	abstract public function createServerRequest (string $method, $uri = null, array $serverParams = null): Psr\Http\Message\ServerRequestInterface

}

interface StreamFactoryInterface  {

	/**
	 * @param string $content [optional]
	 */
	abstract public function createStream (string $content = null): Psr\Http\Message\StreamInterface

	/**
	 * @param string $filename
	 * @param string $mode [optional]
	 */
	abstract public function createStreamFromFile (string $filename, string $mode = null): Psr\Http\Message\StreamInterface

	/**
	 * @param mixed $resouce
	 */
	abstract public function createStreamFromResource ($resouce = null): Psr\Http\Message\StreamInterface

}

interface UploadedFileFactoryInterface  {

	/**
	 * @param Psr\Http\Message\StreamInterface $stream
	 * @param int|null $size [optional]
	 * @param int $error [optional]
	 * @param string|null $clientFilename [optional]
	 * @param string|null $clientMediaType [optional]
	 */
	abstract public function createUploadedFile (Psr\Http\Message\StreamInterface $stream, int|null $size = null, int $error = null, string|null $clientFilename = null, string|null $clientMediaType = null): Psr\Http\Message\UploadedFileInterface

}

interface UriFactoryInterface  {

	/**
	 * @param string $uri [optional]
	 */
	abstract public function createUri (string $uri = null): Psr\Http\Message\UriInterface

}


}


namespace PsrExt\Http\Client {

interface ClientInterface  {

	/**
	 * @param Psr\Http\Message\RequestInterface $request
	 */
	abstract public function sendRequest (Psr\Http\Message\RequestInterface $request): Psr\Http\Message\ResponseInterface

}

interface ClientExceptionInterface extends \Throwable, \Stringable {

	abstract public function getMessage (): string

	abstract public function getCode ()

	abstract public function getFile (): string

	abstract public function getLine (): int

	abstract public function getTrace (): array

	abstract public function getPrevious (): ?Throwable

	abstract public function getTraceAsString (): string

	abstract public function __toString (): string

}

interface NetworkExceptionInterface extends \PsrExt\Http\Client\ClientExceptionInterface, \Stringable, \Throwable {

	abstract public function getRequest (): Psr\Http\Message\RequestInterface

	abstract public function getMessage (): string

	abstract public function getCode ()

	abstract public function getFile (): string

	abstract public function getLine (): int

	abstract public function getTrace (): array

	abstract public function getPrevious (): ?Throwable

	abstract public function getTraceAsString (): string

	abstract public function __toString (): string

}

interface RequestExceptionInterface extends \PsrExt\Http\Client\ClientExceptionInterface, \Stringable, \Throwable {

	abstract public function getRequest (): Psr\Http\Message\RequestInterface

	abstract public function getMessage (): string

	abstract public function getCode ()

	abstract public function getFile (): string

	abstract public function getLine (): int

	abstract public function getTrace (): array

	abstract public function getPrevious (): ?Throwable

	abstract public function getTraceAsString (): string

	abstract public function __toString (): string

}


}


namespace PsrExt\EventDispatcher {

interface EventDispatcherInterface  {

	/**
	 * @param object $event
	 */
	abstract public function dispatch (object $event)

}

interface ListenerProviderInterface  {

	/**
	 * @param object $event
	 */
	abstract public function getListenersForEvent (object $event): iterable

}

interface StoppableEventInterface  {

	abstract public function isPropagationStopped (): bool

}


}


namespace PsrExt\Log {

abstract trait LoggerTrait  {

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function emergency ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function alert ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function critical ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function error ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function warning ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function notice ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function info ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function debug ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $level
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function log ($level = null, $message = null, array $context = 'Array')

}

trait LoggerAwareTrait  {
	protected $logger;


	/**
	 * @param Psr\Log\LoggerInterface $logger
	 */
	public function setLogger (Psr\Log\LoggerInterface $logger) {}

}


}


namespace {


/**
 * The full path and filename of the file with symlinks resolved. If used inside an include,
 * the name of the included file is returned.
 * @link http://www.php.net/manual/en/language.constants.php
 */
define ('__FILE__', null);

/**
 * The current line number of the file.
 * @link http://www.php.net/manual/en/language.constants.php
 */
define ('__LINE__', null);

/**
 * The class name. The class name includes the namespace
 * it was declared in (e.g. Foo\Bar).
 * When used
 * in a trait method, __CLASS__ is the name of the class the trait
 * is used in.
 * @link http://www.php.net/manual/en/language.constants.php
 */
define ('__CLASS__', null);

/**
 * The function name, or {closure} for anonymous functions.
 * @link http://www.php.net/manual/en/language.constants.php
 */
define ('__FUNCTION__', null);

/**
 * The class method name.
 * @link http://www.php.net/manual/en/language.constants.php
 */
define ('__METHOD__', null);

/**
 * The directory of the file. If used inside an include,
 * the directory of the included file is returned. This is equivalent
 * to dirname(__FILE__). This directory name
 * does not have a trailing slash unless it is the root directory.
 * @link http://www.php.net/manual/en/language.constants.php
 */
define ('__DIR__', null);

/**
 * The name of the current namespace.
 * @link http://www.php.net/manual/en/language.constants.php
 */
define ('__NAMESPACE__', null);

/**
 * The trait name. The trait name includes the namespace
 * it was declared in (e.g. Foo\Bar).
 * @link http://www.php.net/manual/en/language.constants.php
 */
define ('__TRAIT__', null);

}

