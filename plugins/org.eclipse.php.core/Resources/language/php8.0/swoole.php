<?php

// Start of swoole v.5.0.3

namespace Swoole {

/**
 * @link http://www.php.net/manual/en/class.swoole-exception.php
 */
class Exception extends \Exception implements \Throwable, \Stringable {
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

class Error extends \Error implements \Throwable, \Stringable {
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
 * @link http://www.php.net/manual/en/class.swoole-event.php
 */
class Event  {

	/**
	 * @param mixed $fd
	 * @param callable|null $read_callback [optional]
	 * @param callable|null $write_callback [optional]
	 * @param int $events [optional]
	 */
	public static function add (mixed $fd = null, callable|null $read_callback = null, callable|null $write_callback = null, int $events = 512): int|false {}

	/**
	 * @param mixed $fd
	 */
	public static function del (mixed $fd = null): bool {}

	/**
	 * @param mixed $fd
	 * @param callable|null $read_callback [optional]
	 * @param callable|null $write_callback [optional]
	 * @param int $events [optional]
	 */
	public static function set (mixed $fd = null, callable|null $read_callback = null, callable|null $write_callback = null, int $events = 0): bool {}

	/**
	 * @param mixed $fd
	 * @param int $events [optional]
	 */
	public static function isset (mixed $fd = null, int $events = 1536): bool {}

	public static function dispatch (): bool {}

	/**
	 * @param callable $callback
	 */
	public static function defer (callable $callback): bool {}

	/**
	 * @param callable|null $callback
	 * @param bool $before [optional]
	 */
	public static function cycle (callable|null $callback = null, bool $before = ''): bool {}

	/**
	 * @param mixed $fd
	 * @param string $data
	 */
	public static function write (mixed $fd = null, string $data): bool {}

	public static function wait (): void {}

	public static function rshutdown (): void {}

	public static function exit (): void {}

}

/**
 * @link http://www.php.net/manual/en/class.swoole-atomic.php
 */
class Atomic  {

	/**
	 * @param int $value [optional]
	 */
	public function __construct (int $value = 0) {}

	/**
	 * @param int $add_value [optional]
	 */
	public function add (int $add_value = 1): int {}

	/**
	 * @param int $sub_value [optional]
	 */
	public function sub (int $sub_value = 1): int {}

	public function get (): int {}

	/**
	 * @param int $value
	 */
	public function set (int $value): void {}

	/**
	 * @param float $timeout [optional]
	 */
	public function wait (float $timeout = 1): bool {}

	/**
	 * @param int $count [optional]
	 */
	public function wakeup (int $count = 1): bool {}

	/**
	 * @param int $cmp_value
	 * @param int $new_value
	 */
	public function cmpset (int $cmp_value, int $new_value): bool {}

}


}


namespace Swoole\Atomic {

class Long  {

	/**
	 * @param int $value [optional]
	 */
	public function __construct (int $value = 0) {}

	/**
	 * @param int $add_value [optional]
	 */
	public function add (int $add_value = 1): int {}

	/**
	 * @param int $sub_value [optional]
	 */
	public function sub (int $sub_value = 1): int {}

	public function get (): int {}

	/**
	 * @param int $value
	 */
	public function set (int $value): void {}

	/**
	 * @param int $cmp_value
	 * @param int $new_value
	 */
	public function cmpset (int $cmp_value, int $new_value): bool {}

}


}


namespace Swoole {

/**
 * @link http://www.php.net/manual/en/class.swoole-lock.php
 */
class Lock  {
	const FILELOCK = 2;
	const MUTEX = 3;
	const SEM = 4;
	const RWLOCK = 1;

	public $errCode;


	/**
	 * @param int $type [optional]
	 * @param string $filename [optional]
	 */
	public function __construct (int $type = 3, string $filename = '') {}

	public function __destruct () {}

	public function lock (): bool {}

	/**
	 * @param float $timeout [optional]
	 */
	public function lockwait (float $timeout = 1): bool {}

	public function trylock (): bool {}

	public function lock_read (): bool {}

	public function trylock_read (): bool {}

	public function unlock (): bool {}

	public function destroy (): void {}

}

/**
 * @link http://www.php.net/manual/en/class.swoole-process.php
 */
class Process  {
	const IPC_NOWAIT = 256;
	const PIPE_MASTER = 1;
	const PIPE_WORKER = 2;
	const PIPE_READ = 3;
	const PIPE_WRITE = 4;

	public $pipe;
	public $msgQueueId;
	public $msgQueueKey;
	public $pid;
	public $id;
	private $callback;


	/**
	 * @param callable $callback
	 * @param bool $redirect_stdin_and_stdout [optional]
	 * @param int $pipe_type [optional]
	 * @param bool $enable_coroutine [optional]
	 */
	public function __construct (callable $callback, bool $redirect_stdin_and_stdout = '', int $pipe_type = 2, bool $enable_coroutine = '') {}

	public function __destruct () {}

	/**
	 * @param bool $blocking [optional]
	 */
	public static function wait (bool $blocking = 1): array|false {}

	/**
	 * @param int $signal_no
	 * @param callable|null $callback [optional]
	 */
	public static function signal (int $signal_no, callable|null $callback = null): bool {}

	/**
	 * @param int $usec
	 * @param int $type [optional]
	 */
	public static function alarm (int $usec, int $type = 0): bool {}

	/**
	 * @param int $pid
	 * @param int $signal_no [optional]
	 */
	public static function kill (int $pid, int $signal_no = 15): bool {}

	/**
	 * @param bool $nochdir [optional]
	 * @param bool $noclose [optional]
	 * @param array[] $pipes [optional]
	 */
	public static function daemon (bool $nochdir = 1, bool $noclose = 1, array $pipes = 'Array'): bool {}

	/**
	 * @param int $which
	 * @param int $priority
	 * @param int|null $who [optional]
	 */
	public function setPriority (int $which, int $priority, int|null $who = null): bool {}

	/**
	 * @param int $which
	 * @param int|null $who [optional]
	 */
	public function getPriority (int $which, int|null $who = null): int|false {}

	/**
	 * @param array[] $settings
	 */
	public function set (array $settings): void {}

	/**
	 * @param float $seconds
	 */
	public function setTimeout (float $seconds): bool {}

	/**
	 * @param bool $blocking
	 */
	public function setBlocking (bool $blocking): void {}

	/**
	 * @param int $key [optional]
	 * @param int $mode [optional]
	 * @param int $capacity [optional]
	 */
	public function useQueue (int $key = 0, int $mode = 2, int $capacity = -1): bool {}

	public function statQueue (): array|false {}

	public function freeQueue (): bool {}

	public function start (): int|bool {}

	/**
	 * @param string $data
	 */
	public function write (string $data): int|false {}

	/**
	 * @param int $which [optional]
	 */
	public function close (int $which = 0): bool {}

	/**
	 * @param int $size [optional]
	 */
	public function read (int $size = 8192): string|false {}

	/**
	 * @param string $data
	 */
	public function push (string $data): bool {}

	/**
	 * @param int $size [optional]
	 */
	public function pop (int $size = 65536): string|false {}

	/**
	 * @param int $exit_code [optional]
	 */
	public function exit (int $exit_code = 0): void {}

	/**
	 * @param string $exec_file
	 * @param array[] $args
	 */
	public function exec (string $exec_file, array $args): bool {}

	public function exportSocket (): Swoole\Coroutine\Socket|false {}

	/**
	 * @param string $process_name
	 */
	public function name (string $process_name): bool {}

}


}


namespace Swoole\Process {

class Pool  {
	public $master_pid;
	public $workers;


	/**
	 * @param int $worker_num
	 * @param int $ipc_type [optional]
	 * @param int $msgqueue_key [optional]
	 * @param bool $enable_coroutine [optional]
	 */
	public function __construct (int $worker_num, int $ipc_type = 0, int $msgqueue_key = 0, bool $enable_coroutine = '') {}

	public function __destruct () {}

	/**
	 * @param array[] $settings
	 */
	public function set (array $settings): void {}

	/**
	 * @param string $name
	 * @param callable $callback
	 */
	public function on (string $name, callable $callback): bool {}

	/**
	 * @param int $work_id [optional]
	 */
	public function getProcess (int $work_id = -1): Swoole\Process|false {}

	/**
	 * @param string $host
	 * @param int $port [optional]
	 * @param int $backlog [optional]
	 */
	public function listen (string $host, int $port = 0, int $backlog = 2048): bool {}

	/**
	 * @param string $data
	 */
	public function write (string $data): bool {}

	/**
	 * @param string $data
	 * @param int $dst_worker_id
	 */
	public function sendMessage (string $data, int $dst_worker_id): bool {}

	public function detach (): bool {}

	public function start (): ?false {}

	public function stop (): void {}

	public function shutdown (): bool {}

}


}


namespace Swoole {

/**
 * @link http://www.php.net/manual/en/class.swoole-table.php
 */
class Table implements \Iterator, \Traversable, \Countable {
	const TYPE_INT = 1;
	const TYPE_STRING = 3;
	const TYPE_FLOAT = 2;

	public $size;
	public $memorySize;


	/**
	 * @param int $table_size
	 * @param float $conflict_proportion [optional]
	 */
	public function __construct (int $table_size, float $conflict_proportion = 0.2) {}

	/**
	 * @param string $name
	 * @param int $type
	 * @param int $size [optional]
	 */
	public function column (string $name, int $type, int $size = 0): bool {}

	public function create (): bool {}

	public function destroy (): bool {}

	/**
	 * @param string $key
	 * @param array[] $value
	 */
	public function set (string $key, array $value): bool {}

	/**
	 * @param string $key
	 * @param string|null $field [optional]
	 */
	public function get (string $key, string|null $field = null): array|string|int|float|false {}

	public function count (): int {}

	/**
	 * @param string $key
	 */
	public function del (string $key): bool {}

	/**
	 * @param string $key
	 */
	public function delete (string $key): bool {}

	/**
	 * @param string $key
	 */
	public function exists (string $key): bool {}

	/**
	 * @param string $key
	 */
	public function exist (string $key): bool {}

	/**
	 * @param string $key
	 * @param string $column
	 * @param int|float $incrby [optional]
	 */
	public function incr (string $key, string $column, int|float $incrby = 1): int|float {}

	/**
	 * @param string $key
	 * @param string $column
	 * @param int|float $incrby [optional]
	 */
	public function decr (string $key, string $column, int|float $incrby = 1): int|float {}

	public function getSize (): int {}

	public function getMemorySize (): int {}

	public function stats (): array|false {}

	public function rewind (): void {}

	public function valid (): bool {}

	public function next (): void {}

	public function current (): mixed {}

	public function key (): mixed {}

}

/**
 * @link http://www.php.net/manual/en/class.swoole-timer.php
 */
class Timer  {

	/**
	 * @param array[] $settings
	 * @deprecated 
	 */
	public static function set (array $settings): void {}

	/**
	 * @param int $ms
	 * @param callable $callback
	 * @param mixed $params [optional]
	 */
	public static function tick (int $ms, callable $callback, mixed ...$params): int|false {}

	/**
	 * @param int $ms
	 * @param callable $callback
	 * @param mixed $params [optional]
	 */
	public static function after (int $ms, callable $callback, mixed ...$params): int|false {}

	/**
	 * @param int $timer_id
	 */
	public static function exists (int $timer_id): bool {}

	/**
	 * @param int $timer_id
	 */
	public static function info (int $timer_id): ?array {}

	public static function stats (): array {}

	public static function list (): Swoole\Timer\Iterator {}

	/**
	 * @param int $timer_id
	 */
	public static function clear (int $timer_id): bool {}

	public static function clearAll (): bool {}

}


}


namespace Swoole\Timer {

class Iterator extends \ArrayIterator implements \Countable, \Serializable, \SeekableIterator, \ArrayAccess, \Traversable, \Iterator {
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

class Iterator extends \ArrayIterator implements \Countable, \Serializable, \SeekableIterator, \ArrayAccess, \Traversable, \Iterator {
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

class Iterator extends \ArrayIterator implements \Countable, \Serializable, \SeekableIterator, \ArrayAccess, \Traversable, \Iterator {
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


}


namespace Swoole {

class ExitException extends \Swoole\Exception implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	private $flags;
	private $status;


	public function getFlags (): int {}

	public function getStatus (): mixed {}

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


}


namespace Swoole {

class Runtime  {

	/**
	 * @param int|bool $enable [optional]
	 * @param int $flags [optional]
	 */
	public static function enableCoroutine (int|bool $enable = 2147479551, int $flags = 2147479551): bool {}

	public static function getHookFlags (): int {}

	/**
	 * @param int $flags
	 */
	public static function setHookFlags (int $flags): bool {}

}


}


namespace Swoole\Coroutine {

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


namespace Swoole {

/**
 * @link http://www.php.net/manual/en/class.swoole-client.php
 */
class Client  {
	const MSG_OOB = 1;
	const MSG_PEEK = 2;
	const MSG_DONTWAIT = 128;
	const MSG_WAITALL = 64;
	const SHUT_RDWR = 2;
	const SHUT_RD = 0;
	const SHUT_WR = 1;

	public $errCode;
	public $sock;
	public $reuse;
	public $reuseCount;
	public $type;
	public $id;
	public $setting;


	/**
	 * @param int $type
	 * @param bool $async [optional]
	 * @param string $id [optional]
	 */
	public function __construct (int $type, bool $async = '', string $id = '') {}

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
	public function connect (string $host, int $port = 0, float $timeout = 0.5, int $sock_flag = 0): bool {}

	/**
	 * @param int $size [optional]
	 * @param int $flag [optional]
	 */
	public function recv (int $size = 65536, int $flag = 0): string|false {}

	/**
	 * @param string $data
	 * @param int $flag [optional]
	 */
	public function send (string $data, int $flag = 0): int|false {}

	/**
	 * @param string $filename
	 * @param int $offset [optional]
	 * @param int $length [optional]
	 */
	public function sendfile (string $filename, int $offset = 0, int $length = 0): bool {}

	/**
	 * @param string $ip
	 * @param int $port
	 * @param string $data
	 */
	public function sendto (string $ip, int $port, string $data): bool {}

	/**
	 * @param int $how
	 */
	public function shutdown (int $how): bool {}

	public function enableSSL (): bool {}

	public function getPeerCert (): string|bool {}

	public function verifyPeerCert (): bool {}

	public function isConnected (): bool {}

	public function getsockname (): array|false {}

	public function getpeername (): array|false {}

	/**
	 * @param bool $force [optional]
	 */
	public function close (bool $force = ''): bool {}

}


}


namespace Swoole\Client {

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


namespace Swoole\Http2 {

class Request  {
	public $path;
	public $method;
	public $headers;
	public $cookies;
	public $data;
	public $pipeline;

}

class Response  {
	public $streamId;
	public $errCode;
	public $statusCode;
	public $pipeline;
	public $headers;
	public $set_cookie_headers;
	public $cookies;
	public $data;

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


namespace Swoole {

/**
 * @link http://www.php.net/manual/en/class.swoole-server.php
 */
class Server  {
	private $onStart;
	private $onBeforeShutdown;
	private $onShutdown;
	private $onWorkerStart;
	private $onWorkerStop;
	private $onBeforeReload;
	private $onAfterReload;
	private $onWorkerExit;
	private $onWorkerError;
	private $onTask;
	private $onFinish;
	private $onManagerStart;
	private $onManagerStop;
	private $onPipeMessage;
	public $setting;
	public $connections;
	public $host;
	public $port;
	public $type;
	public $ssl;
	public $mode;
	public $ports;
	public $master_pid;
	public $manager_pid;
	public $worker_id;
	public $taskworker;
	public $worker_pid;
	public $stats_timer;
	public $admin_server;


	/**
	 * @param string $host [optional]
	 * @param int $port [optional]
	 * @param int $mode [optional]
	 * @param int $sock_type [optional]
	 */
	public function __construct (string $host = '0.0.0.0', int $port = 0, int $mode = 1, int $sock_type = 1) {}

	public function __destruct () {}

	/**
	 * @param string $host
	 * @param int $port
	 * @param int $sock_type
	 */
	public function listen (string $host, int $port, int $sock_type): Swoole\Server\Port|false {}

	/**
	 * @param string $host
	 * @param int $port
	 * @param int $sock_type
	 */
	public function addlistener (string $host, int $port, int $sock_type): Swoole\Server\Port|false {}

	/**
	 * @param string $event_name
	 * @param callable $callback
	 */
	public function on (string $event_name, callable $callback): bool {}

	/**
	 * @param string $event_name
	 */
	public function getCallback (string $event_name): Closure|array|string|null {}

	/**
	 * @param array[] $settings
	 */
	public function set (array $settings): bool {}

	public function start (): bool {}

	/**
	 * @param string|int $fd
	 * @param string $send_data
	 * @param int $serverSocket [optional]
	 */
	public function send (string|int $fd, string $send_data, int $serverSocket = -1): bool {}

	/**
	 * @param string $ip
	 * @param int $port
	 * @param string $send_data
	 * @param int $server_socket [optional]
	 */
	public function sendto (string $ip, int $port, string $send_data, int $server_socket = -1): bool {}

	/**
	 * @param int $conn_fd
	 * @param string $send_data
	 */
	public function sendwait (int $conn_fd, string $send_data): bool {}

	/**
	 * @param int $fd
	 */
	public function exists (int $fd): bool {}

	/**
	 * @param int $fd
	 */
	public function exist (int $fd): bool {}

	/**
	 * @param int $fd
	 * @param bool $is_protected [optional]
	 */
	public function protect (int $fd, bool $is_protected = 1): bool {}

	/**
	 * @param int $conn_fd
	 * @param string $filename
	 * @param int $offset [optional]
	 * @param int $length [optional]
	 */
	public function sendfile (int $conn_fd, string $filename, int $offset = 0, int $length = 0): bool {}

	/**
	 * @param int $fd
	 * @param bool $reset [optional]
	 */
	public function close (int $fd, bool $reset = ''): bool {}

	/**
	 * @param int $fd
	 */
	public function confirm (int $fd): bool {}

	/**
	 * @param int $fd
	 */
	public function pause (int $fd): bool {}

	/**
	 * @param int $fd
	 */
	public function resume (int $fd): bool {}

	/**
	 * @param mixed $data
	 * @param int $taskWorkerIndex [optional]
	 * @param callable|null $finishCallback [optional]
	 */
	public function task (mixed $data = null, int $taskWorkerIndex = -1, callable|null $finishCallback = null): int|false {}

	/**
	 * @param mixed $data
	 * @param float $timeout [optional]
	 * @param int $taskWorkerIndex [optional]
	 */
	public function taskwait (mixed $data = null, float $timeout = 0.5, int $taskWorkerIndex = -1): mixed {}

	/**
	 * @param array[] $tasks
	 * @param float $timeout [optional]
	 */
	public function taskWaitMulti (array $tasks, float $timeout = 0.5): array|false {}

	/**
	 * @param array[] $tasks
	 * @param float $timeout [optional]
	 */
	public function taskCo (array $tasks, float $timeout = 0.5): array|false {}

	/**
	 * @param mixed $data
	 */
	public function finish (mixed $data = null): bool {}

	/**
	 * @param bool $only_reload_taskworker [optional]
	 */
	public function reload (bool $only_reload_taskworker = ''): bool {}

	public function shutdown (): bool {}

	/**
	 * @param int $workerId [optional]
	 * @param bool $waitEvent [optional]
	 */
	public function stop (int $workerId = -1, bool $waitEvent = ''): bool {}

	public function getLastError (): int {}

	/**
	 * @param bool $ifCloseConnection [optional]
	 */
	public function heartbeat (bool $ifCloseConnection = 1): array|false {}

	/**
	 * @param int $fd
	 * @param int $reactor_id [optional]
	 * @param bool $ignoreError [optional]
	 */
	public function getClientInfo (int $fd, int $reactor_id = -1, bool $ignoreError = ''): array|false {}

	/**
	 * @param int $start_fd [optional]
	 * @param int $find_count [optional]
	 */
	public function getClientList (int $start_fd = 0, int $find_count = 10): array|false {}

	public function getWorkerId (): int|false {}

	/**
	 * @param int $worker_id [optional]
	 */
	public function getWorkerPid (int $worker_id = -1): int|false {}

	/**
	 * @param int $worker_id [optional]
	 */
	public function getWorkerStatus (int $worker_id = -1): int|false {}

	public function getManagerPid (): int {}

	public function getMasterPid (): int {}

	/**
	 * @param int $fd
	 * @param int $reactor_id [optional]
	 * @param bool $ignoreError [optional]
	 */
	public function connection_info (int $fd, int $reactor_id = -1, bool $ignoreError = ''): array|false {}

	/**
	 * @param int $start_fd [optional]
	 * @param int $find_count [optional]
	 */
	public function connection_list (int $start_fd = 0, int $find_count = 10): array|false {}

	/**
	 * @param mixed $message
	 * @param int $dst_worker_id
	 */
	public function sendMessage (mixed $message = null, int $dst_worker_id): bool {}

	/**
	 * @param string $name
	 * @param int $process_id
	 * @param int $process_type
	 * @param mixed $data
	 * @param bool $json_decode [optional]
	 */
	public function command (string $name, int $process_id, int $process_type, mixed $data = null, bool $json_decode = 1): array|string|false {}

	/**
	 * @param string $name
	 * @param int $accepted_process_types
	 * @param callable $callback
	 */
	public function addCommand (string $name, int $accepted_process_types, callable $callback): bool {}

	/**
	 * @param \Swoole\Process $process
	 */
	public function addProcess (\Swoole\Process $process): int {}

	public function stats (): array {}

	/**
	 * @param int $fd
	 * @param int $uid
	 */
	public function bind (int $fd, int $uid): bool {}

}


}


namespace Swoole\Server {

final class Task  {
	public $data;
	public $dispatch_time;
	public $id;
	public $worker_id;
	public $flags;


	/**
	 * @param mixed $data
	 */
	public function finish (mixed $data = null): bool {}

	/**
	 * @param mixed $data
	 */
	public static function pack (mixed $data = null): string|false {}

	/**
	 * @param string $data
	 */
	public static function unpack (string $data): mixed {}

}

class Event  {
	public $reactor_id;
	public $fd;
	public $dispatch_time;
	public $data;

}

class Packet  {
	public $server_socket;
	public $server_port;
	public $dispatch_time;
	public $address;
	public $port;

}

class PipeMessage  {
	public $source_worker_id;
	public $dispatch_time;
	public $data;

}

class StatusInfo  {
	public $worker_id;
	public $worker_pid;
	public $status;
	public $exit_code;
	public $signal;

}

class TaskResult  {
	public $task_id;
	public $task_worker_id;
	public $dispatch_time;
	public $data;

}


}


namespace Swoole\Connection {

/**
 * @link http://www.php.net/manual/en/class.swoole-connection-iterator.php
 */
class Iterator implements \Iterator, \Traversable, \ArrayAccess, \Countable {

	public function __construct () {}

	public function __destruct () {}

	public function rewind (): void {}

	public function next (): void {}

	public function current (): mixed {}

	public function key (): mixed {}

	public function valid (): bool {}

	public function count (): int {}

	/**
	 * @param mixed $fd
	 */
	public function offsetExists (mixed $fd = null): bool {}

	/**
	 * @param mixed $fd
	 */
	public function offsetGet (mixed $fd = null): mixed {}

	/**
	 * @param mixed $fd
	 * @param mixed $value
	 */
	public function offsetSet (mixed $fd = null, mixed $value = null): void {}

	/**
	 * @param mixed $fd
	 */
	public function offsetUnset (mixed $fd = null): void {}

}


}


namespace Swoole\Server {

/**
 * @link http://www.php.net/manual/en/class.swoole-server-port.php
 */
class Port  {
	private $onConnect;
	private $onReceive;
	private $onClose;
	private $onPacket;
	private $onBufferFull;
	private $onBufferEmpty;
	private $onRequest;
	private $onHandshake;
	private $onOpen;
	private $onMessage;
	private $onDisconnect;
	private $onBeforeHandshakeResponse;
	public $host;
	public $port;
	public $type;
	public $sock;
	public $ssl;
	public $setting;
	public $connections;


	private function __construct () {}

	public function __destruct () {}

	/**
	 * @param array[] $settings
	 */
	public function set (array $settings): void {}

	/**
	 * @param string $event_name
	 * @param callable $callback
	 */
	public function on (string $event_name, callable $callback): bool {}

	/**
	 * @param string $event_name
	 */
	public function getCallback (string $event_name): ?Closure {}

}


}


namespace Swoole\Http {

/**
 * @link http://www.php.net/manual/en/class.swoole-http-request.php
 */
class Request  {
	public $fd;
	public $streamId;
	public $header;
	public $server;
	public $cookie;
	public $get;
	public $files;
	public $post;
	public $tmpfiles;


	public function getContent (): string|false {}

	public function rawContent (): string|false {}

	public function getData (): string|false {}

	/**
	 * @param array[] $options [optional]
	 */
	public static function create (array $options = 'Array'): Swoole\Http\Request {}

	/**
	 * @param string $data
	 */
	public function parse (string $data): int|false {}

	public function isCompleted (): bool {}

	public function getMethod (): string|false {}

	public function __destruct () {}

}

/**
 * @link http://www.php.net/manual/en/class.swoole-http-response.php
 */
class Response  {
	public $fd;
	public $socket;
	public $header;
	public $cookie;
	public $trailer;


	public function initHeader (): bool {}

	public function isWritable (): bool {}

	/**
	 * @param string $name
	 * @param string $value [optional]
	 * @param int $expires [optional]
	 * @param string $path [optional]
	 * @param string $domain [optional]
	 * @param bool $secure [optional]
	 * @param bool $httponly [optional]
	 * @param string $samesite [optional]
	 * @param string $priority [optional]
	 */
	public function cookie (string $name, string $value = '', int $expires = 0, string $path = '/', string $domain = '', bool $secure = '', bool $httponly = '', string $samesite = '', string $priority = ''): bool {}

	/**
	 * @param string $name
	 * @param string $value [optional]
	 * @param int $expires [optional]
	 * @param string $path [optional]
	 * @param string $domain [optional]
	 * @param bool $secure [optional]
	 * @param bool $httponly [optional]
	 * @param string $samesite [optional]
	 * @param string $priority [optional]
	 */
	public function setCookie (string $name, string $value = '', int $expires = 0, string $path = '/', string $domain = '', bool $secure = '', bool $httponly = '', string $samesite = '', string $priority = ''): bool {}

	/**
	 * @param string $name
	 * @param string $value [optional]
	 * @param int $expires [optional]
	 * @param string $path [optional]
	 * @param string $domain [optional]
	 * @param bool $secure [optional]
	 * @param bool $httponly [optional]
	 * @param string $samesite [optional]
	 * @param string $priority [optional]
	 */
	public function rawcookie (string $name, string $value = '', int $expires = 0, string $path = '/', string $domain = '', bool $secure = '', bool $httponly = '', string $samesite = '', string $priority = ''): bool {}

	/**
	 * @param int $http_code
	 * @param string $reason [optional]
	 */
	public function status (int $http_code, string $reason = ''): bool {}

	/**
	 * @param int $http_code
	 * @param string $reason [optional]
	 */
	public function setStatusCode (int $http_code, string $reason = ''): bool {}

	/**
	 * @param string $key
	 * @param array|string $value
	 * @param bool $format [optional]
	 */
	public function header (string $key, array|string $value, bool $format = 1): bool {}

	/**
	 * @param string $key
	 * @param array|string $value
	 * @param bool $format [optional]
	 */
	public function setHeader (string $key, array|string $value, bool $format = 1): bool {}

	/**
	 * @param string $key
	 * @param string $value
	 */
	public function trailer (string $key, string $value): bool {}

	public function ping (): bool {}

	/**
	 * @param int $error_code [optional]
	 * @param string $debug_data [optional]
	 */
	public function goaway (int $error_code = 0, string $debug_data = ''): bool {}

	/**
	 * @param string $content
	 */
	public function write (string $content): bool {}

	/**
	 * @param string|null $content [optional]
	 */
	public function end (string|null $content = null): bool {}

	/**
	 * @param string $filename
	 * @param int $offset [optional]
	 * @param int $length [optional]
	 */
	public function sendfile (string $filename, int $offset = 0, int $length = 0): bool {}

	/**
	 * @param string $location
	 * @param int $http_code [optional]
	 */
	public function redirect (string $location, int $http_code = 302): bool {}

	public function detach (): bool {}

	/**
	 * @param object|array|int $server [optional]
	 * @param int $fd [optional]
	 */
	public static function create (object|array|int $server = -1, int $fd = -1): Swoole\Http\Response|false {}

	public function upgrade (): bool {}

	/**
	 * @param Swoole\WebSocket\Frame|string $data
	 * @param int $opcode [optional]
	 * @param int $flags [optional]
	 */
	public function push (Swoole\WebSocket\Frame|string $data, int $opcode = 1, int $flags = 1): bool {}

	/**
	 * @param float $timeout [optional]
	 */
	public function recv (float $timeout = 0): Swoole\WebSocket\Frame|string|false {}

	public function close (): bool {}

	public function __destruct () {}

}

/**
 * @link http://www.php.net/manual/en/class.swoole-http-server.php
 */
class Server extends \Swoole\Server  {
	public $setting;
	public $connections;
	public $host;
	public $port;
	public $type;
	public $ssl;
	public $mode;
	public $ports;
	public $master_pid;
	public $manager_pid;
	public $worker_id;
	public $taskworker;
	public $worker_pid;
	public $stats_timer;
	public $admin_server;


	/**
	 * @param string $host [optional]
	 * @param int $port [optional]
	 * @param int $mode [optional]
	 * @param int $sock_type [optional]
	 */
	public function __construct (string $host = '0.0.0.0', int $port = 0, int $mode = 1, int $sock_type = 1) {}

	public function __destruct () {}

	/**
	 * @param string $host
	 * @param int $port
	 * @param int $sock_type
	 */
	public function listen (string $host, int $port, int $sock_type): Swoole\Server\Port|false {}

	/**
	 * @param string $host
	 * @param int $port
	 * @param int $sock_type
	 */
	public function addlistener (string $host, int $port, int $sock_type): Swoole\Server\Port|false {}

	/**
	 * @param string $event_name
	 * @param callable $callback
	 */
	public function on (string $event_name, callable $callback): bool {}

	/**
	 * @param string $event_name
	 */
	public function getCallback (string $event_name): Closure|array|string|null {}

	/**
	 * @param array[] $settings
	 */
	public function set (array $settings): bool {}

	public function start (): bool {}

	/**
	 * @param string|int $fd
	 * @param string $send_data
	 * @param int $serverSocket [optional]
	 */
	public function send (string|int $fd, string $send_data, int $serverSocket = -1): bool {}

	/**
	 * @param string $ip
	 * @param int $port
	 * @param string $send_data
	 * @param int $server_socket [optional]
	 */
	public function sendto (string $ip, int $port, string $send_data, int $server_socket = -1): bool {}

	/**
	 * @param int $conn_fd
	 * @param string $send_data
	 */
	public function sendwait (int $conn_fd, string $send_data): bool {}

	/**
	 * @param int $fd
	 */
	public function exists (int $fd): bool {}

	/**
	 * @param int $fd
	 */
	public function exist (int $fd): bool {}

	/**
	 * @param int $fd
	 * @param bool $is_protected [optional]
	 */
	public function protect (int $fd, bool $is_protected = 1): bool {}

	/**
	 * @param int $conn_fd
	 * @param string $filename
	 * @param int $offset [optional]
	 * @param int $length [optional]
	 */
	public function sendfile (int $conn_fd, string $filename, int $offset = 0, int $length = 0): bool {}

	/**
	 * @param int $fd
	 * @param bool $reset [optional]
	 */
	public function close (int $fd, bool $reset = ''): bool {}

	/**
	 * @param int $fd
	 */
	public function confirm (int $fd): bool {}

	/**
	 * @param int $fd
	 */
	public function pause (int $fd): bool {}

	/**
	 * @param int $fd
	 */
	public function resume (int $fd): bool {}

	/**
	 * @param mixed $data
	 * @param int $taskWorkerIndex [optional]
	 * @param callable|null $finishCallback [optional]
	 */
	public function task (mixed $data = null, int $taskWorkerIndex = -1, callable|null $finishCallback = null): int|false {}

	/**
	 * @param mixed $data
	 * @param float $timeout [optional]
	 * @param int $taskWorkerIndex [optional]
	 */
	public function taskwait (mixed $data = null, float $timeout = 0.5, int $taskWorkerIndex = -1): mixed {}

	/**
	 * @param array[] $tasks
	 * @param float $timeout [optional]
	 */
	public function taskWaitMulti (array $tasks, float $timeout = 0.5): array|false {}

	/**
	 * @param array[] $tasks
	 * @param float $timeout [optional]
	 */
	public function taskCo (array $tasks, float $timeout = 0.5): array|false {}

	/**
	 * @param mixed $data
	 */
	public function finish (mixed $data = null): bool {}

	/**
	 * @param bool $only_reload_taskworker [optional]
	 */
	public function reload (bool $only_reload_taskworker = ''): bool {}

	public function shutdown (): bool {}

	/**
	 * @param int $workerId [optional]
	 * @param bool $waitEvent [optional]
	 */
	public function stop (int $workerId = -1, bool $waitEvent = ''): bool {}

	public function getLastError (): int {}

	/**
	 * @param bool $ifCloseConnection [optional]
	 */
	public function heartbeat (bool $ifCloseConnection = 1): array|false {}

	/**
	 * @param int $fd
	 * @param int $reactor_id [optional]
	 * @param bool $ignoreError [optional]
	 */
	public function getClientInfo (int $fd, int $reactor_id = -1, bool $ignoreError = ''): array|false {}

	/**
	 * @param int $start_fd [optional]
	 * @param int $find_count [optional]
	 */
	public function getClientList (int $start_fd = 0, int $find_count = 10): array|false {}

	public function getWorkerId (): int|false {}

	/**
	 * @param int $worker_id [optional]
	 */
	public function getWorkerPid (int $worker_id = -1): int|false {}

	/**
	 * @param int $worker_id [optional]
	 */
	public function getWorkerStatus (int $worker_id = -1): int|false {}

	public function getManagerPid (): int {}

	public function getMasterPid (): int {}

	/**
	 * @param int $fd
	 * @param int $reactor_id [optional]
	 * @param bool $ignoreError [optional]
	 */
	public function connection_info (int $fd, int $reactor_id = -1, bool $ignoreError = ''): array|false {}

	/**
	 * @param int $start_fd [optional]
	 * @param int $find_count [optional]
	 */
	public function connection_list (int $start_fd = 0, int $find_count = 10): array|false {}

	/**
	 * @param mixed $message
	 * @param int $dst_worker_id
	 */
	public function sendMessage (mixed $message = null, int $dst_worker_id): bool {}

	/**
	 * @param string $name
	 * @param int $process_id
	 * @param int $process_type
	 * @param mixed $data
	 * @param bool $json_decode [optional]
	 */
	public function command (string $name, int $process_id, int $process_type, mixed $data = null, bool $json_decode = 1): array|string|false {}

	/**
	 * @param string $name
	 * @param int $accepted_process_types
	 * @param callable $callback
	 */
	public function addCommand (string $name, int $accepted_process_types, callable $callback): bool {}

	/**
	 * @param \Swoole\Process $process
	 */
	public function addProcess (\Swoole\Process $process): int {}

	public function stats (): array {}

	/**
	 * @param int $fd
	 * @param int $uid
	 */
	public function bind (int $fd, int $uid): bool {}

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


namespace Swoole\WebSocket {

/**
 * @link http://www.php.net/manual/en/class.swoole-websocket-server.php
 */
class Server extends \Swoole\Http\Server  {
	public $setting;
	public $connections;
	public $host;
	public $port;
	public $type;
	public $ssl;
	public $mode;
	public $ports;
	public $master_pid;
	public $manager_pid;
	public $worker_id;
	public $taskworker;
	public $worker_pid;
	public $stats_timer;
	public $admin_server;


	/**
	 * @param int $fd
	 * @param Swoole\WebSocket\Frame|string $data
	 * @param int $opcode [optional]
	 * @param int $flags [optional]
	 */
	public function push (int $fd, Swoole\WebSocket\Frame|string $data, int $opcode = 1, int $flags = 1): bool {}

	/**
	 * @param int $fd
	 * @param int $code [optional]
	 * @param string $reason [optional]
	 */
	public function disconnect (int $fd, int $code = 1000, string $reason = ''): bool {}

	/**
	 * @param int $fd
	 */
	public function isEstablished (int $fd): bool {}

	/**
	 * @param Swoole\WebSocket\Frame|string $data
	 * @param int $opcode [optional]
	 * @param int $flags [optional]
	 */
	public static function pack (Swoole\WebSocket\Frame|string $data, int $opcode = 1, int $flags = 1): string {}

	/**
	 * @param string $data
	 */
	public static function unpack (string $data): Swoole\WebSocket\Frame {}

	/**
	 * @param string $host [optional]
	 * @param int $port [optional]
	 * @param int $mode [optional]
	 * @param int $sock_type [optional]
	 */
	public function __construct (string $host = '0.0.0.0', int $port = 0, int $mode = 1, int $sock_type = 1) {}

	public function __destruct () {}

	/**
	 * @param string $host
	 * @param int $port
	 * @param int $sock_type
	 */
	public function listen (string $host, int $port, int $sock_type): Swoole\Server\Port|false {}

	/**
	 * @param string $host
	 * @param int $port
	 * @param int $sock_type
	 */
	public function addlistener (string $host, int $port, int $sock_type): Swoole\Server\Port|false {}

	/**
	 * @param string $event_name
	 * @param callable $callback
	 */
	public function on (string $event_name, callable $callback): bool {}

	/**
	 * @param string $event_name
	 */
	public function getCallback (string $event_name): Closure|array|string|null {}

	/**
	 * @param array[] $settings
	 */
	public function set (array $settings): bool {}

	public function start (): bool {}

	/**
	 * @param string|int $fd
	 * @param string $send_data
	 * @param int $serverSocket [optional]
	 */
	public function send (string|int $fd, string $send_data, int $serverSocket = -1): bool {}

	/**
	 * @param string $ip
	 * @param int $port
	 * @param string $send_data
	 * @param int $server_socket [optional]
	 */
	public function sendto (string $ip, int $port, string $send_data, int $server_socket = -1): bool {}

	/**
	 * @param int $conn_fd
	 * @param string $send_data
	 */
	public function sendwait (int $conn_fd, string $send_data): bool {}

	/**
	 * @param int $fd
	 */
	public function exists (int $fd): bool {}

	/**
	 * @param int $fd
	 */
	public function exist (int $fd): bool {}

	/**
	 * @param int $fd
	 * @param bool $is_protected [optional]
	 */
	public function protect (int $fd, bool $is_protected = 1): bool {}

	/**
	 * @param int $conn_fd
	 * @param string $filename
	 * @param int $offset [optional]
	 * @param int $length [optional]
	 */
	public function sendfile (int $conn_fd, string $filename, int $offset = 0, int $length = 0): bool {}

	/**
	 * @param int $fd
	 * @param bool $reset [optional]
	 */
	public function close (int $fd, bool $reset = ''): bool {}

	/**
	 * @param int $fd
	 */
	public function confirm (int $fd): bool {}

	/**
	 * @param int $fd
	 */
	public function pause (int $fd): bool {}

	/**
	 * @param int $fd
	 */
	public function resume (int $fd): bool {}

	/**
	 * @param mixed $data
	 * @param int $taskWorkerIndex [optional]
	 * @param callable|null $finishCallback [optional]
	 */
	public function task (mixed $data = null, int $taskWorkerIndex = -1, callable|null $finishCallback = null): int|false {}

	/**
	 * @param mixed $data
	 * @param float $timeout [optional]
	 * @param int $taskWorkerIndex [optional]
	 */
	public function taskwait (mixed $data = null, float $timeout = 0.5, int $taskWorkerIndex = -1): mixed {}

	/**
	 * @param array[] $tasks
	 * @param float $timeout [optional]
	 */
	public function taskWaitMulti (array $tasks, float $timeout = 0.5): array|false {}

	/**
	 * @param array[] $tasks
	 * @param float $timeout [optional]
	 */
	public function taskCo (array $tasks, float $timeout = 0.5): array|false {}

	/**
	 * @param mixed $data
	 */
	public function finish (mixed $data = null): bool {}

	/**
	 * @param bool $only_reload_taskworker [optional]
	 */
	public function reload (bool $only_reload_taskworker = ''): bool {}

	public function shutdown (): bool {}

	/**
	 * @param int $workerId [optional]
	 * @param bool $waitEvent [optional]
	 */
	public function stop (int $workerId = -1, bool $waitEvent = ''): bool {}

	public function getLastError (): int {}

	/**
	 * @param bool $ifCloseConnection [optional]
	 */
	public function heartbeat (bool $ifCloseConnection = 1): array|false {}

	/**
	 * @param int $fd
	 * @param int $reactor_id [optional]
	 * @param bool $ignoreError [optional]
	 */
	public function getClientInfo (int $fd, int $reactor_id = -1, bool $ignoreError = ''): array|false {}

	/**
	 * @param int $start_fd [optional]
	 * @param int $find_count [optional]
	 */
	public function getClientList (int $start_fd = 0, int $find_count = 10): array|false {}

	public function getWorkerId (): int|false {}

	/**
	 * @param int $worker_id [optional]
	 */
	public function getWorkerPid (int $worker_id = -1): int|false {}

	/**
	 * @param int $worker_id [optional]
	 */
	public function getWorkerStatus (int $worker_id = -1): int|false {}

	public function getManagerPid (): int {}

	public function getMasterPid (): int {}

	/**
	 * @param int $fd
	 * @param int $reactor_id [optional]
	 * @param bool $ignoreError [optional]
	 */
	public function connection_info (int $fd, int $reactor_id = -1, bool $ignoreError = ''): array|false {}

	/**
	 * @param int $start_fd [optional]
	 * @param int $find_count [optional]
	 */
	public function connection_list (int $start_fd = 0, int $find_count = 10): array|false {}

	/**
	 * @param mixed $message
	 * @param int $dst_worker_id
	 */
	public function sendMessage (mixed $message = null, int $dst_worker_id): bool {}

	/**
	 * @param string $name
	 * @param int $process_id
	 * @param int $process_type
	 * @param mixed $data
	 * @param bool $json_decode [optional]
	 */
	public function command (string $name, int $process_id, int $process_type, mixed $data = null, bool $json_decode = 1): array|string|false {}

	/**
	 * @param string $name
	 * @param int $accepted_process_types
	 * @param callable $callback
	 */
	public function addCommand (string $name, int $accepted_process_types, callable $callback): bool {}

	/**
	 * @param \Swoole\Process $process
	 */
	public function addProcess (\Swoole\Process $process): int {}

	public function stats (): array {}

	/**
	 * @param int $fd
	 * @param int $uid
	 */
	public function bind (int $fd, int $uid): bool {}

}

/**
 * @link http://www.php.net/manual/en/class.swoole-websocket-frame.php
 */
class Frame implements \Stringable {
	public $fd;
	public $data;
	public $opcode;
	public $flags;
	public $finish;


	public function __toString (): string {}

	/**
	 * @param Swoole\WebSocket\Frame|string $data
	 * @param int $opcode [optional]
	 * @param int $flags [optional]
	 */
	public static function pack (Swoole\WebSocket\Frame|string $data, int $opcode = 1, int $flags = 1): string {}

	/**
	 * @param string $data
	 */
	public static function unpack (string $data): Swoole\WebSocket\Frame {}

}

class CloseFrame extends \Swoole\WebSocket\Frame implements \Stringable {
	public $fd;
	public $data;
	public $flags;
	public $finish;
	public $opcode;
	public $code;
	public $reason;


	public function __toString (): string {}

	/**
	 * @param Swoole\WebSocket\Frame|string $data
	 * @param int $opcode [optional]
	 * @param int $flags [optional]
	 */
	public static function pack (Swoole\WebSocket\Frame|string $data, int $opcode = 1, int $flags = 1): string {}

	/**
	 * @param string $data
	 */
	public static function unpack (string $data): Swoole\WebSocket\Frame {}

}


}


namespace Swoole\Redis {

/**
 * @link http://www.php.net/manual/en/class.swoole-redis-server.php
 */
class Server extends \Swoole\Server  {
	const NIL = 1;
	const ERROR = 0;
	const STATUS = 2;
	const INT = 3;
	const STRING = 4;
	const SET = 5;
	const MAP = 6;

	public $setting;
	public $connections;
	public $host;
	public $port;
	public $type;
	public $ssl;
	public $mode;
	public $ports;
	public $master_pid;
	public $manager_pid;
	public $worker_id;
	public $taskworker;
	public $worker_pid;
	public $stats_timer;
	public $admin_server;


	/**
	 * @param string $command
	 * @param callable $callback
	 */
	public function setHandler (string $command, callable $callback): bool {}

	/**
	 * @param string $command
	 */
	public function getHandler (string $command): Closure {}

	/**
	 * @param int $type
	 * @param mixed $value [optional]
	 */
	public static function format (int $type, mixed $value = null): string|false {}

	/**
	 * @param string $host [optional]
	 * @param int $port [optional]
	 * @param int $mode [optional]
	 * @param int $sock_type [optional]
	 */
	public function __construct (string $host = '0.0.0.0', int $port = 0, int $mode = 1, int $sock_type = 1) {}

	public function __destruct () {}

	/**
	 * @param string $host
	 * @param int $port
	 * @param int $sock_type
	 */
	public function listen (string $host, int $port, int $sock_type): Swoole\Server\Port|false {}

	/**
	 * @param string $host
	 * @param int $port
	 * @param int $sock_type
	 */
	public function addlistener (string $host, int $port, int $sock_type): Swoole\Server\Port|false {}

	/**
	 * @param string $event_name
	 * @param callable $callback
	 */
	public function on (string $event_name, callable $callback): bool {}

	/**
	 * @param string $event_name
	 */
	public function getCallback (string $event_name): Closure|array|string|null {}

	/**
	 * @param array[] $settings
	 */
	public function set (array $settings): bool {}

	public function start (): bool {}

	/**
	 * @param string|int $fd
	 * @param string $send_data
	 * @param int $serverSocket [optional]
	 */
	public function send (string|int $fd, string $send_data, int $serverSocket = -1): bool {}

	/**
	 * @param string $ip
	 * @param int $port
	 * @param string $send_data
	 * @param int $server_socket [optional]
	 */
	public function sendto (string $ip, int $port, string $send_data, int $server_socket = -1): bool {}

	/**
	 * @param int $conn_fd
	 * @param string $send_data
	 */
	public function sendwait (int $conn_fd, string $send_data): bool {}

	/**
	 * @param int $fd
	 */
	public function exists (int $fd): bool {}

	/**
	 * @param int $fd
	 */
	public function exist (int $fd): bool {}

	/**
	 * @param int $fd
	 * @param bool $is_protected [optional]
	 */
	public function protect (int $fd, bool $is_protected = 1): bool {}

	/**
	 * @param int $conn_fd
	 * @param string $filename
	 * @param int $offset [optional]
	 * @param int $length [optional]
	 */
	public function sendfile (int $conn_fd, string $filename, int $offset = 0, int $length = 0): bool {}

	/**
	 * @param int $fd
	 * @param bool $reset [optional]
	 */
	public function close (int $fd, bool $reset = ''): bool {}

	/**
	 * @param int $fd
	 */
	public function confirm (int $fd): bool {}

	/**
	 * @param int $fd
	 */
	public function pause (int $fd): bool {}

	/**
	 * @param int $fd
	 */
	public function resume (int $fd): bool {}

	/**
	 * @param mixed $data
	 * @param int $taskWorkerIndex [optional]
	 * @param callable|null $finishCallback [optional]
	 */
	public function task (mixed $data = null, int $taskWorkerIndex = -1, callable|null $finishCallback = null): int|false {}

	/**
	 * @param mixed $data
	 * @param float $timeout [optional]
	 * @param int $taskWorkerIndex [optional]
	 */
	public function taskwait (mixed $data = null, float $timeout = 0.5, int $taskWorkerIndex = -1): mixed {}

	/**
	 * @param array[] $tasks
	 * @param float $timeout [optional]
	 */
	public function taskWaitMulti (array $tasks, float $timeout = 0.5): array|false {}

	/**
	 * @param array[] $tasks
	 * @param float $timeout [optional]
	 */
	public function taskCo (array $tasks, float $timeout = 0.5): array|false {}

	/**
	 * @param mixed $data
	 */
	public function finish (mixed $data = null): bool {}

	/**
	 * @param bool $only_reload_taskworker [optional]
	 */
	public function reload (bool $only_reload_taskworker = ''): bool {}

	public function shutdown (): bool {}

	/**
	 * @param int $workerId [optional]
	 * @param bool $waitEvent [optional]
	 */
	public function stop (int $workerId = -1, bool $waitEvent = ''): bool {}

	public function getLastError (): int {}

	/**
	 * @param bool $ifCloseConnection [optional]
	 */
	public function heartbeat (bool $ifCloseConnection = 1): array|false {}

	/**
	 * @param int $fd
	 * @param int $reactor_id [optional]
	 * @param bool $ignoreError [optional]
	 */
	public function getClientInfo (int $fd, int $reactor_id = -1, bool $ignoreError = ''): array|false {}

	/**
	 * @param int $start_fd [optional]
	 * @param int $find_count [optional]
	 */
	public function getClientList (int $start_fd = 0, int $find_count = 10): array|false {}

	public function getWorkerId (): int|false {}

	/**
	 * @param int $worker_id [optional]
	 */
	public function getWorkerPid (int $worker_id = -1): int|false {}

	/**
	 * @param int $worker_id [optional]
	 */
	public function getWorkerStatus (int $worker_id = -1): int|false {}

	public function getManagerPid (): int {}

	public function getMasterPid (): int {}

	/**
	 * @param int $fd
	 * @param int $reactor_id [optional]
	 * @param bool $ignoreError [optional]
	 */
	public function connection_info (int $fd, int $reactor_id = -1, bool $ignoreError = ''): array|false {}

	/**
	 * @param int $start_fd [optional]
	 * @param int $find_count [optional]
	 */
	public function connection_list (int $start_fd = 0, int $find_count = 10): array|false {}

	/**
	 * @param mixed $message
	 * @param int $dst_worker_id
	 */
	public function sendMessage (mixed $message = null, int $dst_worker_id): bool {}

	/**
	 * @param string $name
	 * @param int $process_id
	 * @param int $process_type
	 * @param mixed $data
	 * @param bool $json_decode [optional]
	 */
	public function command (string $name, int $process_id, int $process_type, mixed $data = null, bool $json_decode = 1): array|string|false {}

	/**
	 * @param string $name
	 * @param int $accepted_process_types
	 * @param callable $callback
	 */
	public function addCommand (string $name, int $accepted_process_types, callable $callback): bool {}

	/**
	 * @param \Swoole\Process $process
	 */
	public function addProcess (\Swoole\Process $process): int {}

	public function stats (): array {}

	/**
	 * @param int $fd
	 * @param int $uid
	 */
	public function bind (int $fd, int $uid): bool {}

}


}


namespace Swoole\NameResolver {

class Context  {

	/**
	 * @param int $family [optional]
	 * @param bool $withPort [optional]
	 */
	public function __construct (int $family = 2, bool $withPort = '') {}

}


}


namespace {

/**
 * Get the version of Swoole
 * @link http://www.php.net/manual/en/function.swoole-version.php
 * @return string The version of Swoole.
 */
function swoole_version (): string {}

/**
 * Get the number of CPU
 * @link http://www.php.net/manual/en/function.swoole-cpu-num.php
 * @return int The number of CPU.
 */
function swoole_cpu_num (): int {}

/**
 * Get the lastest error message
 * @link http://www.php.net/manual/en/function.swoole-last-error.php
 * @return int 
 */
function swoole_last_error (): int {}

/**
 * @param string $domain_name
 * @param float $timeout [optional]
 * @param int $type [optional]
 */
function swoole_async_dns_lookup_coro (string $domain_name, float $timeout = 60, int $type = 2): string|false {}

/**
 * Update the async I/O options
 * @link http://www.php.net/manual/en/function.swoole-async-set.php
 * @param array $settings 
 * @return void 
 */
function swoole_async_set (array $settings): void {}

/**
 * @param callable $func
 * @param mixed $params [optional]
 */
function swoole_coroutine_create (callable $func, mixed ...$params): int|false {}

/**
 * @param callable $callback
 */
function swoole_coroutine_defer (callable $callback): void {}

/**
 * @param int $domain
 * @param int $type
 * @param int $protocol
 */
function swoole_coroutine_socketpair (int $domain, int $type, int $protocol): array|false {}

/**
 * @param int $count [optional]
 * @param float $sleep_time [optional]
 */
function swoole_test_kernel_coroutine (int $count = 100, float $sleep_time = 1): void {}

/**
 * Get the file description which are ready to read/write or error
 * @link http://www.php.net/manual/en/function.swoole-client-select.php
 * @param array $read_array 
 * @param array $write_array 
 * @param array $error_array 
 * @param float $timeout [optional] 
 * @return int 
 */
function swoole_client_select (array &$read_array, array &$write_array, array &$error_array, float $timeout = null): int|false {}

/**
 * Select the file descriptions which are ready to read/write or error in the eventloop
 * @link http://www.php.net/manual/en/function.swoole-select.php
 * @param array $read_array 
 * @param array $write_array 
 * @param array $error_array 
 * @param float $timeout [optional] 
 * @return int 
 */
function swoole_select (array &$read_array, array &$write_array, array &$error_array, float $timeout = null): int|false {}

/**
 * Set the process name
 * @link http://www.php.net/manual/en/function.swoole-set-process-name.php
 * @param string $process_name 
 * @param int $size [optional] 
 * @return void 
 */
function swoole_set_process_name (string $process_name, int $size = null): bool {}

/**
 * Get the IPv4 IP addresses of each NIC on the machine
 * @link http://www.php.net/manual/en/function.swoole-get-local-ip.php
 * @return array 
 */
function swoole_get_local_ip (): array {}

function swoole_get_local_mac (): array {}

/**
 * Convert the Errno into error messages
 * @link http://www.php.net/manual/en/function.swoole-strerror.php
 * @param int $errno 
 * @param int $error_type [optional] 
 * @return string 
 */
function swoole_strerror (int $errno, int $error_type = null): string {}

/**
 * Get the error code of the latest system call
 * @link http://www.php.net/manual/en/function.swoole-errno.php
 * @return int 
 */
function swoole_errno (): int {}

/**
 * Clear errors in the socket or on the last error code
 * @link http://www.php.net/manual/en/function.swoole-clear-error.php
 * @return void 
 */
function swoole_clear_error (): void {}

/**
 * Output error messages to the log
 * @link http://www.php.net/manual/en/function.swoole-error-log.php
 * @param int $level Log Level, constants can be used: SWOOLE_LOG_DEBUG,
 * SWOOLE_LOG_TRACE,
 * SWOOLE_LOG_INFO,
 * SWOOLE_LOG_NOTICE,
 * SWOOLE_LOG_WARNING,
 * SWOOLE_LOG_ERROR,
 * SWOOLE_LOG_NONE
 * @param string $msg Message content to be written to the log.
 * @return void 
 */
function swoole_error_log (int $level, string $msg): void {}

/**
 * @param int $level
 * @param int $error
 * @param string $msg
 */
function swoole_error_log_ex (int $level, int $error, string $msg): void {}

/**
 * @param int $error
 */
function swoole_ignore_error (int $error): void {}

/**
 * @param string $data
 * @param int $type [optional]
 */
function swoole_hashcode (string $data, int $type = 0): int|false {}

/**
 * @param string $suffix
 * @param string $mime_type
 */
function swoole_mime_type_add (string $suffix, string $mime_type): bool {}

/**
 * @param string $suffix
 * @param string $mime_type
 */
function swoole_mime_type_set (string $suffix, string $mime_type): void {}

/**
 * @param string $suffix
 */
function swoole_mime_type_delete (string $suffix): bool {}

/**
 * @param string $filename
 */
function swoole_mime_type_get (string $filename): string {}

/**
 * @param string $filename
 */
function swoole_get_mime_type (string $filename): string {}

/**
 * @param string $filename
 */
function swoole_mime_type_exists (string $filename): bool {}

function swoole_mime_type_list (): array {}

function swoole_clear_dns_cache (): void {}

/**
 * @param string $str
 * @param int $offset
 * @param int $length [optional]
 * @param array[] $options [optional]
 */
function swoole_substr_unserialize (string $str, int $offset, int $length = 0, array $options = 'Array'): mixed {}

/**
 * @param string $str
 * @param int $offset
 * @param int $length [optional]
 * @param bool $associative [optional]
 * @param int $depth [optional]
 * @param int $flags [optional]
 */
function swoole_substr_json_decode (string $str, int $offset, int $length = 0, bool $associative = '', int $depth = 512, int $flags = 0): mixed {}

function swoole_internal_call_user_shutdown_begin (): bool {}

function swoole_get_objects (): array|false {}

function swoole_get_vm_status (): array {}

/**
 * @param int $handle
 */
function swoole_get_object_by_handle (int $handle): object|false {}

/**
 * @param string $name
 * @param Swoole\NameResolver\Context $ctx
 */
function swoole_name_resolver_lookup (string $name, Swoole\NameResolver\Context $ctx): string {}

/**
 * @param Swoole\NameResolver $ns
 */
function swoole_name_resolver_add (Swoole\NameResolver $ns): bool {}

/**
 * @param Swoole\NameResolver $ns
 */
function swoole_name_resolver_remove (Swoole\NameResolver $ns): bool {}

/**
 * @param callable $func
 */
function go (callable $func): int|false {}

/**
 * @param callable $callback
 */
function defer (callable $callback): void {}

/**
 * Add new callback functions of a socket into the EventLoop
 * @link http://www.php.net/manual/en/function.swoole-event-add.php
 * @param int $fd 
 * @param callable $read_callback [optional] 
 * @param callable $write_callback [optional] 
 * @param int $events [optional] 
 * @return int 
 */
function swoole_event_add (int $fd, callable $read_callback = null, callable $write_callback = null, int $events = null): int|false {}

/**
 * Remove all event callback functions of a socket
 * @link http://www.php.net/manual/en/function.swoole-event-del.php
 * @param int $fd 
 * @return bool true on success or false on failure
 */
function swoole_event_del (int $fd): bool {}

/**
 * Update the event callback functions of a socket
 * @link http://www.php.net/manual/en/function.swoole-event-set.php
 * @param int $fd 
 * @param callable $read_callback [optional] 
 * @param callable $write_callback [optional] 
 * @param int $events [optional] 
 * @return bool 
 */
function swoole_event_set (int $fd, callable $read_callback = null, callable $write_callback = null, int $events = null): bool {}

/**
 * Start the event loop
 * @link http://www.php.net/manual/en/function.swoole-event-wait.php
 * @return void 
 */
function swoole_event_wait (): void {}

/**
 * @param mixed $fd
 * @param int $events [optional]
 */
function swoole_event_isset (mixed $fd = null, int $events = 1536): bool {}

function swoole_event_dispatch (): bool {}

/**
 * Add callback function to the next event loop
 * @link http://www.php.net/manual/en/function.swoole-event-defer.php
 * @param callable $callback 
 * @return bool true on success or false on failure
 */
function swoole_event_defer (callable $callback): bool {}

/**
 * @param callable|null $callback
 * @param bool $before [optional]
 */
function swoole_event_cycle (callable|null $callback = null, bool $before = ''): bool {}

/**
 * Write data to a socket
 * @link http://www.php.net/manual/en/function.swoole-event-write.php
 * @param int $fd 
 * @param string $data 
 * @return bool true on success or false on failure
 */
function swoole_event_write (int $fd, string $data): bool {}

/**
 * Exit the eventloop, only available at the client side
 * @link http://www.php.net/manual/en/function.swoole-event-exit.php
 * @return void 
 */
function swoole_event_exit (): void {}

/**
 * @param array[] $settings
 */
function swoole_timer_set (array $settings): void {}

/**
 * Trigger a one time callback function in the future
 * @link http://www.php.net/manual/en/function.swoole-timer-after.php
 * @param int $ms 
 * @param callable $callback 
 * @param mixed $param [optional] 
 * @return int 
 */
function swoole_timer_after (int $ms, callable $callback, $param = null): int|false {}

/**
 * Trigger a timer tick callback function by time interval
 * @link http://www.php.net/manual/en/function.swoole-timer-tick.php
 * @param int $ms 
 * @param callable $callback 
 * @param mixed $param [optional] 
 * @return int 
 */
function swoole_timer_tick (int $ms, callable $callback, $param = null): int|false {}

/**
 * @param int $timer_id
 */
function swoole_timer_info (int $timer_id): ?array {}

function swoole_timer_list (): Swoole\Timer\Iterator {}

/**
 * Check if a timer callback function is existed
 * @link http://www.php.net/manual/en/function.swoole-timer-exists.php
 * @param int $timer_id 
 * @return bool 
 */
function swoole_timer_exists (int $timer_id): bool {}

function swoole_timer_stats (): array {}

/**
 * @param int $timer_id
 */
function swoole_timer_clear (int $timer_id): bool {}

function swoole_timer_clear_all (): bool {}


/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_VERSION', "5.0.3");
define ('SWOOLE_VERSION_ID', 50003);
define ('SWOOLE_MAJOR_VERSION', 5);
define ('SWOOLE_MINOR_VERSION', 0);
define ('SWOOLE_RELEASE_VERSION', 3);
define ('SWOOLE_EXTRA_VERSION', "");
define ('SWOOLE_DEBUG', false);
define ('SWOOLE_HAVE_COMPRESSION', true);
define ('SWOOLE_HAVE_ZLIB', true);
define ('SWOOLE_HAVE_BROTLI', true);
define ('SWOOLE_USE_HTTP2', true);
define ('SWOOLE_USE_SHORTNAME', true);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_SOCK_TCP', 1);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_SOCK_TCP6', 3);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_SOCK_UDP', 2);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_SOCK_UDP6', 4);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_SOCK_UNIX_DGRAM', 6);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_SOCK_UNIX_STREAM', 5);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_TCP', 1);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_TCP6', 3);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_UDP', 2);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_UDP6', 4);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_UNIX_DGRAM', 6);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_UNIX_STREAM', 5);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_SOCK_SYNC', false);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_SOCK_ASYNC', true);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_SYNC', 2048);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_ASYNC', 1024);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_KEEP', 4096);
define ('SWOOLE_SSL', 512);
define ('SWOOLE_SSLv3_METHOD', 1);
define ('SWOOLE_SSLv3_SERVER_METHOD', 2);
define ('SWOOLE_SSLv3_CLIENT_METHOD', 3);
define ('SWOOLE_TLSv1_METHOD', 6);
define ('SWOOLE_TLSv1_SERVER_METHOD', 7);
define ('SWOOLE_TLSv1_CLIENT_METHOD', 8);
define ('SWOOLE_TLSv1_1_METHOD', 9);
define ('SWOOLE_TLSv1_1_SERVER_METHOD', 10);
define ('SWOOLE_TLSv1_1_CLIENT_METHOD', 11);
define ('SWOOLE_TLSv1_2_METHOD', 12);
define ('SWOOLE_TLSv1_2_SERVER_METHOD', 13);
define ('SWOOLE_TLSv1_2_CLIENT_METHOD', 14);
define ('SWOOLE_DTLS_SERVER_METHOD', 16);
define ('SWOOLE_DTLS_CLIENT_METHOD', 15);
define ('SWOOLE_SSLv23_METHOD', 0);
define ('SWOOLE_SSLv23_SERVER_METHOD', 4);
define ('SWOOLE_SSLv23_CLIENT_METHOD', 5);
define ('SWOOLE_TLS_METHOD', 0);
define ('SWOOLE_TLS_SERVER_METHOD', 4);
define ('SWOOLE_TLS_CLIENT_METHOD', 5);
define ('SWOOLE_SSL_TLSv1', 8);
define ('SWOOLE_SSL_TLSv1_1', 16);
define ('SWOOLE_SSL_TLSv1_2', 32);
define ('SWOOLE_SSL_TLSv1_3', 64);
define ('SWOOLE_SSL_DTLS', 128);
define ('SWOOLE_SSL_SSLv2', 2);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_EVENT_READ', 512);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_EVENT_WRITE', 1024);
define ('SWOOLE_STRERROR_SYSTEM', 0);
define ('SWOOLE_STRERROR_GAI', 1);
define ('SWOOLE_STRERROR_DNS', 2);
define ('SWOOLE_STRERROR_SWOOLE', 9);
define ('SWOOLE_ERROR_MALLOC_FAIL', 501);
define ('SWOOLE_ERROR_SYSTEM_CALL_FAIL', 502);
define ('SWOOLE_ERROR_PHP_FATAL_ERROR', 503);
define ('SWOOLE_ERROR_NAME_TOO_LONG', 504);
define ('SWOOLE_ERROR_INVALID_PARAMS', 505);
define ('SWOOLE_ERROR_QUEUE_FULL', 506);
define ('SWOOLE_ERROR_OPERATION_NOT_SUPPORT', 507);
define ('SWOOLE_ERROR_PROTOCOL_ERROR', 508);
define ('SWOOLE_ERROR_WRONG_OPERATION', 509);
define ('SWOOLE_ERROR_FILE_NOT_EXIST', 700);
define ('SWOOLE_ERROR_FILE_TOO_LARGE', 701);
define ('SWOOLE_ERROR_FILE_EMPTY', 702);
define ('SWOOLE_ERROR_DNSLOOKUP_DUPLICATE_REQUEST', 710);
define ('SWOOLE_ERROR_DNSLOOKUP_RESOLVE_FAILED', 711);
define ('SWOOLE_ERROR_DNSLOOKUP_RESOLVE_TIMEOUT', 712);
define ('SWOOLE_ERROR_DNSLOOKUP_UNSUPPORTED', 713);
define ('SWOOLE_ERROR_DNSLOOKUP_NO_SERVER', 714);
define ('SWOOLE_ERROR_BAD_IPV6_ADDRESS', 720);
define ('SWOOLE_ERROR_UNREGISTERED_SIGNAL', 721);
define ('SWOOLE_ERROR_EVENT_SOCKET_REMOVED', 800);
define ('SWOOLE_ERROR_SESSION_CLOSED_BY_SERVER', 1001);
define ('SWOOLE_ERROR_SESSION_CLOSED_BY_CLIENT', 1002);
define ('SWOOLE_ERROR_SESSION_CLOSING', 1003);
define ('SWOOLE_ERROR_SESSION_CLOSED', 1004);
define ('SWOOLE_ERROR_SESSION_NOT_EXIST', 1005);
define ('SWOOLE_ERROR_SESSION_INVALID_ID', 1006);
define ('SWOOLE_ERROR_SESSION_DISCARD_TIMEOUT_DATA', 1007);
define ('SWOOLE_ERROR_SESSION_DISCARD_DATA', 1008);
define ('SWOOLE_ERROR_OUTPUT_BUFFER_OVERFLOW', 1009);
define ('SWOOLE_ERROR_OUTPUT_SEND_YIELD', 1010);
define ('SWOOLE_ERROR_SSL_NOT_READY', 1011);
define ('SWOOLE_ERROR_SSL_CANNOT_USE_SENFILE', 1012);
define ('SWOOLE_ERROR_SSL_EMPTY_PEER_CERTIFICATE', 1013);
define ('SWOOLE_ERROR_SSL_VERIFY_FAILED', 1014);
define ('SWOOLE_ERROR_SSL_BAD_CLIENT', 1015);
define ('SWOOLE_ERROR_SSL_BAD_PROTOCOL', 1016);
define ('SWOOLE_ERROR_SSL_RESET', 1017);
define ('SWOOLE_ERROR_SSL_HANDSHAKE_FAILED', 1018);
define ('SWOOLE_ERROR_PACKAGE_LENGTH_TOO_LARGE', 1201);
define ('SWOOLE_ERROR_PACKAGE_LENGTH_NOT_FOUND', 1202);
define ('SWOOLE_ERROR_DATA_LENGTH_TOO_LARGE', 1203);
define ('SWOOLE_ERROR_PACKAGE_MALFORMED_DATA', 1204);
define ('SWOOLE_ERROR_TASK_PACKAGE_TOO_BIG', 2001);
define ('SWOOLE_ERROR_TASK_DISPATCH_FAIL', 2002);
define ('SWOOLE_ERROR_TASK_TIMEOUT', 2003);
define ('SWOOLE_ERROR_HTTP2_STREAM_ID_TOO_BIG', 3001);
define ('SWOOLE_ERROR_HTTP2_STREAM_NO_HEADER', 3002);
define ('SWOOLE_ERROR_HTTP2_STREAM_NOT_FOUND', 3003);
define ('SWOOLE_ERROR_HTTP2_STREAM_IGNORE', 3004);
define ('SWOOLE_ERROR_HTTP2_SEND_CONTROL_FRAME_FAILED', 3005);
define ('SWOOLE_ERROR_AIO_BAD_REQUEST', 4001);
define ('SWOOLE_ERROR_AIO_CANCELED', 4002);
define ('SWOOLE_ERROR_AIO_TIMEOUT', 4003);
define ('SWOOLE_ERROR_CLIENT_NO_CONNECTION', 5001);
define ('SWOOLE_ERROR_SOCKET_CLOSED', 6001);
define ('SWOOLE_ERROR_SOCKET_POLL_TIMEOUT', 6002);
define ('SWOOLE_ERROR_SOCKS5_UNSUPPORT_VERSION', 7001);
define ('SWOOLE_ERROR_SOCKS5_UNSUPPORT_METHOD', 7002);
define ('SWOOLE_ERROR_SOCKS5_AUTH_FAILED', 7003);
define ('SWOOLE_ERROR_SOCKS5_SERVER_ERROR', 7004);
define ('SWOOLE_ERROR_SOCKS5_HANDSHAKE_FAILED', 7005);
define ('SWOOLE_ERROR_HTTP_PROXY_HANDSHAKE_ERROR', 7101);
define ('SWOOLE_ERROR_HTTP_INVALID_PROTOCOL', 7102);
define ('SWOOLE_ERROR_HTTP_PROXY_HANDSHAKE_FAILED', 7103);
define ('SWOOLE_ERROR_HTTP_PROXY_BAD_RESPONSE', 7104);
define ('SWOOLE_ERROR_HTTP_CONFLICT_HEADER', 7105);
define ('SWOOLE_ERROR_WEBSOCKET_BAD_CLIENT', 8501);
define ('SWOOLE_ERROR_WEBSOCKET_BAD_OPCODE', 8502);
define ('SWOOLE_ERROR_WEBSOCKET_UNCONNECTED', 8503);
define ('SWOOLE_ERROR_WEBSOCKET_HANDSHAKE_FAILED', 8504);
define ('SWOOLE_ERROR_WEBSOCKET_PACK_FAILED', 8505);
define ('SWOOLE_ERROR_WEBSOCKET_UNPACK_FAILED', 8506);
define ('SWOOLE_ERROR_WEBSOCKET_INCOMPLETE_PACKET', 8507);
define ('SWOOLE_ERROR_SERVER_MUST_CREATED_BEFORE_CLIENT', 9001);
define ('SWOOLE_ERROR_SERVER_TOO_MANY_SOCKET', 9002);
define ('SWOOLE_ERROR_SERVER_WORKER_TERMINATED', 9003);
define ('SWOOLE_ERROR_SERVER_INVALID_LISTEN_PORT', 9004);
define ('SWOOLE_ERROR_SERVER_TOO_MANY_LISTEN_PORT', 9005);
define ('SWOOLE_ERROR_SERVER_PIPE_BUFFER_FULL', 9006);
define ('SWOOLE_ERROR_SERVER_NO_IDLE_WORKER', 9007);
define ('SWOOLE_ERROR_SERVER_ONLY_START_ONE', 9008);
define ('SWOOLE_ERROR_SERVER_SEND_IN_MASTER', 9009);
define ('SWOOLE_ERROR_SERVER_INVALID_REQUEST', 9010);
define ('SWOOLE_ERROR_SERVER_CONNECT_FAIL', 9011);
define ('SWOOLE_ERROR_SERVER_INVALID_COMMAND', 9012);
define ('SWOOLE_ERROR_SERVER_IS_NOT_REGULAR_FILE', 9013);
define ('SWOOLE_ERROR_SERVER_SEND_TO_WOKER_TIMEOUT', 9014);
define ('SWOOLE_ERROR_SERVER_WORKER_EXIT_TIMEOUT', 9101);
define ('SWOOLE_ERROR_SERVER_WORKER_ABNORMAL_PIPE_DATA', 9102);
define ('SWOOLE_ERROR_SERVER_WORKER_UNPROCESSED_DATA', 9103);
define ('SWOOLE_ERROR_CO_OUT_OF_COROUTINE', 10001);
define ('SWOOLE_ERROR_CO_HAS_BEEN_BOUND', 10002);
define ('SWOOLE_ERROR_CO_HAS_BEEN_DISCARDED', 10003);
define ('SWOOLE_ERROR_CO_MUTEX_DOUBLE_UNLOCK', 10004);
define ('SWOOLE_ERROR_CO_BLOCK_OBJECT_LOCKED', 10005);
define ('SWOOLE_ERROR_CO_BLOCK_OBJECT_WAITING', 10006);
define ('SWOOLE_ERROR_CO_YIELD_FAILED', 10007);
define ('SWOOLE_ERROR_CO_GETCONTEXT_FAILED', 10008);
define ('SWOOLE_ERROR_CO_SWAPCONTEXT_FAILED', 10009);
define ('SWOOLE_ERROR_CO_MAKECONTEXT_FAILED', 10010);
define ('SWOOLE_ERROR_CO_IOCPINIT_FAILED', 10011);
define ('SWOOLE_ERROR_CO_PROTECT_STACK_FAILED', 10012);
define ('SWOOLE_ERROR_CO_STD_THREAD_LINK_ERROR', 10013);
define ('SWOOLE_ERROR_CO_DISABLED_MULTI_THREAD', 10014);
define ('SWOOLE_ERROR_CO_CANNOT_CANCEL', 10015);
define ('SWOOLE_ERROR_CO_NOT_EXISTS', 10016);
define ('SWOOLE_ERROR_CO_CANCELED', 10017);
define ('SWOOLE_ERROR_CO_TIMEDOUT', 10018);
define ('SWOOLE_ERROR_CO_SOCKET_CLOSE_WAIT', 10019);
define ('SWOOLE_TRACE_SERVER', 2);
define ('SWOOLE_TRACE_CLIENT', 4);
define ('SWOOLE_TRACE_BUFFER', 8);
define ('SWOOLE_TRACE_CONN', 16);
define ('SWOOLE_TRACE_EVENT', 32);
define ('SWOOLE_TRACE_WORKER', 64);
define ('SWOOLE_TRACE_MEMORY', 128);
define ('SWOOLE_TRACE_REACTOR', 256);
define ('SWOOLE_TRACE_PHP', 512);
define ('SWOOLE_TRACE_HTTP', 1024);
define ('SWOOLE_TRACE_HTTP2', 2048);
define ('SWOOLE_TRACE_EOF_PROTOCOL', 4096);
define ('SWOOLE_TRACE_LENGTH_PROTOCOL', 8192);
define ('SWOOLE_TRACE_CLOSE', 16384);
define ('SWOOLE_TRACE_WEBSOCKET', 32768);
define ('SWOOLE_TRACE_REDIS_CLIENT', 65536);
define ('SWOOLE_TRACE_MYSQL_CLIENT', 131072);
define ('SWOOLE_TRACE_HTTP_CLIENT', 262144);
define ('SWOOLE_TRACE_AIO', 524288);
define ('SWOOLE_TRACE_SSL', 1048576);
define ('SWOOLE_TRACE_NORMAL', 2097152);
define ('SWOOLE_TRACE_CHANNEL', 4194304);
define ('SWOOLE_TRACE_TIMER', 8388608);
define ('SWOOLE_TRACE_SOCKET', 16777216);
define ('SWOOLE_TRACE_COROUTINE', 33554432);
define ('SWOOLE_TRACE_CONTEXT', 67108864);
define ('SWOOLE_TRACE_CO_HTTP_SERVER', 134217728);
define ('SWOOLE_TRACE_TABLE', 268435456);
define ('SWOOLE_TRACE_CO_CURL', 536870912);
define ('SWOOLE_TRACE_CARES', 1073741824);
define ('SWOOLE_TRACE_ZLIB', 2147483648);
define ('SWOOLE_TRACE_ALL', 9223372036854775807);

/**
 * Debug log.
 * Available as of swoole 2.1.2.
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_LOG_DEBUG', 0);

/**
 * Trace log.
 * Available as of swoole 2.1.2.
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_LOG_TRACE', 1);

/**
 * Info log.
 * Available as of swoole 2.1.2.
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_LOG_INFO', 2);

/**
 * Notice log.
 * Available as of swoole 2.1.2.
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_LOG_NOTICE', 3);

/**
 * Warning log.
 * Available as of swoole 2.1.2.
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_LOG_WARNING', 4);

/**
 * Error log.
 * Available as of swoole 2.1.2.
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_LOG_ERROR', 5);

/**
 * None log.
 * Available as of swoole 4.3.2.
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_LOG_NONE', 6);
define ('SWOOLE_LOG_ROTATION_SINGLE', 0);
define ('SWOOLE_LOG_ROTATION_MONTHLY', 1);
define ('SWOOLE_LOG_ROTATION_DAILY', 2);
define ('SWOOLE_LOG_ROTATION_HOURLY', 3);
define ('SWOOLE_LOG_ROTATION_EVERY_MINUTE', 4);
define ('SWOOLE_IPC_NONE', 0);
define ('SWOOLE_IPC_UNIXSOCK', 1);
define ('SWOOLE_IPC_SOCKET', 3);
define ('SWOOLE_IOV_MAX', 1024);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_FILELOCK', 2);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_MUTEX', 3);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_SEM', 4);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_RWLOCK', 1);
define ('SWOOLE_MSGQUEUE_ORIENT', 1);
define ('SWOOLE_MSGQUEUE_BALANCE', 2);
define ('SWOOLE_TIMER_MIN_MS', 1);
define ('SWOOLE_TIMER_MIN_SEC', 0.001);
define ('SWOOLE_TIMER_MAX_MS', 9223372036854775807);
define ('SWOOLE_TIMER_MAX_SEC', 9.2233720368548E+15);
define ('SWOOLE_DEFAULT_MAX_CORO_NUM', 100000);
define ('SWOOLE_CORO_MAX_NUM_LIMIT', 9223372036854775807);
define ('SWOOLE_CORO_INIT', 0);
define ('SWOOLE_CORO_WAITING', 1);
define ('SWOOLE_CORO_RUNNING', 2);
define ('SWOOLE_CORO_END', 3);
define ('SWOOLE_EXIT_IN_COROUTINE', 2);
define ('SWOOLE_EXIT_IN_SERVER', 4);
define ('SWOOLE_CHANNEL_OK', 0);
define ('SWOOLE_CHANNEL_TIMEOUT', -1);
define ('SWOOLE_CHANNEL_CLOSED', -2);
define ('SWOOLE_CHANNEL_CANCELED', -3);
define ('SWOOLE_HOOK_TCP', 2);
define ('SWOOLE_HOOK_UDP', 4);
define ('SWOOLE_HOOK_UNIX', 8);
define ('SWOOLE_HOOK_UDG', 16);
define ('SWOOLE_HOOK_SSL', 32);
define ('SWOOLE_HOOK_TLS', 64);
define ('SWOOLE_HOOK_STREAM_FUNCTION', 128);
define ('SWOOLE_HOOK_STREAM_SELECT', 128);
define ('SWOOLE_HOOK_FILE', 256);
define ('SWOOLE_HOOK_STDIO', 32768);
define ('SWOOLE_HOOK_SLEEP', 512);
define ('SWOOLE_HOOK_PROC', 1024);
define ('SWOOLE_HOOK_CURL', 2048);
define ('SWOOLE_HOOK_NATIVE_CURL', 4096);
define ('SWOOLE_HOOK_BLOCKING_FUNCTION', 8192);
define ('SWOOLE_HOOK_SOCKETS', 16384);
define ('SWOOLE_HOOK_ALL', 2147479551);
define ('SOCKET_ECANCELED', 89);
define ('SWOOLE_HTTP_CLIENT_ESTATUS_CONNECT_FAILED', -1);
define ('SWOOLE_HTTP_CLIENT_ESTATUS_REQUEST_TIMEOUT', -2);
define ('SWOOLE_HTTP_CLIENT_ESTATUS_SERVER_RESET', -3);
define ('SWOOLE_HTTP_CLIENT_ESTATUS_SEND_FAILED', -4);
define ('SWOOLE_HTTP2_TYPE_DATA', 0);
define ('SWOOLE_HTTP2_TYPE_HEADERS', 1);
define ('SWOOLE_HTTP2_TYPE_PRIORITY', 2);
define ('SWOOLE_HTTP2_TYPE_RST_STREAM', 3);
define ('SWOOLE_HTTP2_TYPE_SETTINGS', 4);
define ('SWOOLE_HTTP2_TYPE_PUSH_PROMISE', 5);
define ('SWOOLE_HTTP2_TYPE_PING', 6);
define ('SWOOLE_HTTP2_TYPE_GOAWAY', 7);
define ('SWOOLE_HTTP2_TYPE_WINDOW_UPDATE', 8);
define ('SWOOLE_HTTP2_TYPE_CONTINUATION', 9);
define ('SWOOLE_HTTP2_ERROR_NO_ERROR', 0);
define ('SWOOLE_HTTP2_ERROR_PROTOCOL_ERROR', 1);
define ('SWOOLE_HTTP2_ERROR_INTERNAL_ERROR', 2);
define ('SWOOLE_HTTP2_ERROR_FLOW_CONTROL_ERROR', 3);
define ('SWOOLE_HTTP2_ERROR_SETTINGS_TIMEOUT', 4);
define ('SWOOLE_HTTP2_ERROR_STREAM_CLOSED', 5);
define ('SWOOLE_HTTP2_ERROR_FRAME_SIZE_ERROR', 6);
define ('SWOOLE_HTTP2_ERROR_REFUSED_STREAM', 7);
define ('SWOOLE_HTTP2_ERROR_CANCEL', 8);
define ('SWOOLE_HTTP2_ERROR_COMPRESSION_ERROR', 9);
define ('SWOOLE_HTTP2_ERROR_CONNECT_ERROR', 10);
define ('SWOOLE_HTTP2_ERROR_ENHANCE_YOUR_CALM', 11);
define ('SWOOLE_HTTP2_ERROR_INADEQUATE_SECURITY', 12);
define ('SWOOLE_HTTP2_ERROR_HTTP_1_1_REQUIRED', 13);
define ('SWOOLE_MYSQLND_CR_UNKNOWN_ERROR', 2000);
define ('SWOOLE_MYSQLND_CR_CONNECTION_ERROR', 2002);
define ('SWOOLE_MYSQLND_CR_SERVER_GONE_ERROR', 2006);
define ('SWOOLE_MYSQLND_CR_OUT_OF_MEMORY', 2008);
define ('SWOOLE_MYSQLND_CR_SERVER_LOST', 2013);
define ('SWOOLE_MYSQLND_CR_COMMANDS_OUT_OF_SYNC', 2014);
define ('SWOOLE_MYSQLND_CR_CANT_FIND_CHARSET', 2019);
define ('SWOOLE_MYSQLND_CR_MALFORMED_PACKET', 2027);
define ('SWOOLE_MYSQLND_CR_NOT_IMPLEMENTED', 2054);
define ('SWOOLE_MYSQLND_CR_NO_PREPARE_STMT', 2030);
define ('SWOOLE_MYSQLND_CR_PARAMS_NOT_BOUND', 2031);
define ('SWOOLE_MYSQLND_CR_INVALID_PARAMETER_NO', 2034);
define ('SWOOLE_MYSQLND_CR_INVALID_BUFFER_USE', 2035);
define ('SWOOLE_REDIS_MODE_MULTI', 0);
define ('SWOOLE_REDIS_MODE_PIPELINE', 1);
define ('SWOOLE_REDIS_TYPE_NOT_FOUND', 0);
define ('SWOOLE_REDIS_TYPE_STRING', 1);
define ('SWOOLE_REDIS_TYPE_SET', 2);
define ('SWOOLE_REDIS_TYPE_LIST', 3);
define ('SWOOLE_REDIS_TYPE_ZSET', 4);
define ('SWOOLE_REDIS_TYPE_HASH', 5);
define ('SWOOLE_REDIS_ERR_IO', 1);
define ('SWOOLE_REDIS_ERR_OTHER', 2);
define ('SWOOLE_REDIS_ERR_EOF', 3);
define ('SWOOLE_REDIS_ERR_PROTOCOL', 4);
define ('SWOOLE_REDIS_ERR_OOM', 5);
define ('SWOOLE_REDIS_ERR_CLOSED', 6);
define ('SWOOLE_REDIS_ERR_NOAUTH', 7);
define ('SWOOLE_REDIS_ERR_ALLOC', 8);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_BASE', 1);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_PROCESS', 2);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_IPC_UNSOCK', 1);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_IPC_MSGQUEUE', 2);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('SWOOLE_IPC_PREEMPTIVE', 3);
define ('SWOOLE_SERVER_COMMAND_MASTER', 2);
define ('SWOOLE_SERVER_COMMAND_MANAGER', 32);
define ('SWOOLE_SERVER_COMMAND_REACTOR_THREAD', 4);
define ('SWOOLE_SERVER_COMMAND_EVENT_WORKER', 8);
define ('SWOOLE_SERVER_COMMAND_WORKER', 8);
define ('SWOOLE_SERVER_COMMAND_TASK_WORKER', 16);
define ('SWOOLE_DISPATCH_ROUND', 1);
define ('SWOOLE_DISPATCH_FDMOD', 2);
define ('SWOOLE_DISPATCH_IDLE_WORKER', 3);
define ('SWOOLE_DISPATCH_IPMOD', 4);
define ('SWOOLE_DISPATCH_UIDMOD', 5);
define ('SWOOLE_DISPATCH_USERFUNC', 6);
define ('SWOOLE_DISPATCH_STREAM', 7);
define ('SWOOLE_DISPATCH_CO_CONN_LB', 8);
define ('SWOOLE_DISPATCH_CO_REQ_LB', 9);
define ('SWOOLE_DISPATCH_CONCURRENT_LB', 10);
define ('SWOOLE_DISPATCH_RESULT_DISCARD_PACKET', -1);
define ('SWOOLE_DISPATCH_RESULT_CLOSE_CONNECTION', -2);
define ('SWOOLE_DISPATCH_RESULT_USERFUNC_FALLBACK', -3);
define ('SWOOLE_TASK_TMPFILE', 1);
define ('SWOOLE_TASK_SERIALIZE', 2);
define ('SWOOLE_TASK_NONBLOCK', 4);
define ('SWOOLE_TASK_CALLBACK', 8);
define ('SWOOLE_TASK_WAITALL', 16);
define ('SWOOLE_TASK_COROUTINE', 32);
define ('SWOOLE_TASK_PEEK', 64);
define ('SWOOLE_TASK_NOREPLY', 128);
define ('SWOOLE_WORKER_BUSY', 1);
define ('SWOOLE_WORKER_IDLE', 2);
define ('SWOOLE_WORKER_EXIT', 3);
define ('SWOOLE_WEBSOCKET_STATUS_CONNECTION', 1);
define ('SWOOLE_WEBSOCKET_STATUS_HANDSHAKE', 2);
define ('SWOOLE_WEBSOCKET_STATUS_ACTIVE', 3);
define ('SWOOLE_WEBSOCKET_STATUS_CLOSING', 4);
define ('SWOOLE_WEBSOCKET_OPCODE_CONTINUATION', 0);
define ('SWOOLE_WEBSOCKET_OPCODE_TEXT', 1);
define ('SWOOLE_WEBSOCKET_OPCODE_BINARY', 2);
define ('SWOOLE_WEBSOCKET_OPCODE_CLOSE', 8);
define ('SWOOLE_WEBSOCKET_OPCODE_PING', 9);
define ('SWOOLE_WEBSOCKET_OPCODE_PONG', 10);
define ('SWOOLE_WEBSOCKET_FLAG_FIN', 1);
define ('SWOOLE_WEBSOCKET_FLAG_RSV1', 4);
define ('SWOOLE_WEBSOCKET_FLAG_RSV2', 8);
define ('SWOOLE_WEBSOCKET_FLAG_RSV3', 16);
define ('SWOOLE_WEBSOCKET_FLAG_MASK', 32);
define ('SWOOLE_WEBSOCKET_FLAG_COMPRESS', 2);
define ('SWOOLE_WEBSOCKET_CLOSE_NORMAL', 1000);
define ('SWOOLE_WEBSOCKET_CLOSE_GOING_AWAY', 1001);
define ('SWOOLE_WEBSOCKET_CLOSE_PROTOCOL_ERROR', 1002);
define ('SWOOLE_WEBSOCKET_CLOSE_DATA_ERROR', 1003);
define ('SWOOLE_WEBSOCKET_CLOSE_STATUS_ERROR', 1005);
define ('SWOOLE_WEBSOCKET_CLOSE_ABNORMAL', 1006);
define ('SWOOLE_WEBSOCKET_CLOSE_MESSAGE_ERROR', 1007);
define ('SWOOLE_WEBSOCKET_CLOSE_POLICY_ERROR', 1008);
define ('SWOOLE_WEBSOCKET_CLOSE_MESSAGE_TOO_BIG', 1009);
define ('SWOOLE_WEBSOCKET_CLOSE_EXTENSION_MISSING', 1010);
define ('SWOOLE_WEBSOCKET_CLOSE_SERVER_ERROR', 1011);
define ('SWOOLE_WEBSOCKET_CLOSE_TLS', 1015);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('WEBSOCKET_STATUS_CONNECTION', 1);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('WEBSOCKET_STATUS_HANDSHAKE', 2);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('WEBSOCKET_STATUS_FRAME', 3);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('WEBSOCKET_STATUS_ACTIVE', 3);
define ('WEBSOCKET_STATUS_CLOSING', 4);
define ('WEBSOCKET_OPCODE_CONTINUATION', 0);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('WEBSOCKET_OPCODE_TEXT', 1);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('WEBSOCKET_OPCODE_BINARY', 2);
define ('WEBSOCKET_OPCODE_CLOSE', 8);

/**
 * 
 * @link http://www.php.net/manual/en/swoole.constants.php
 */
define ('WEBSOCKET_OPCODE_PING', 9);
define ('WEBSOCKET_OPCODE_PONG', 10);
define ('WEBSOCKET_CLOSE_NORMAL', 1000);
define ('WEBSOCKET_CLOSE_GOING_AWAY', 1001);
define ('WEBSOCKET_CLOSE_PROTOCOL_ERROR', 1002);
define ('WEBSOCKET_CLOSE_DATA_ERROR', 1003);
define ('WEBSOCKET_CLOSE_STATUS_ERROR', 1005);
define ('WEBSOCKET_CLOSE_ABNORMAL', 1006);
define ('WEBSOCKET_CLOSE_MESSAGE_ERROR', 1007);
define ('WEBSOCKET_CLOSE_POLICY_ERROR', 1008);
define ('WEBSOCKET_CLOSE_MESSAGE_TOO_BIG', 1009);
define ('WEBSOCKET_CLOSE_EXTENSION_MISSING', 1010);
define ('WEBSOCKET_CLOSE_SERVER_ERROR', 1011);
define ('WEBSOCKET_CLOSE_TLS', 1015);


}

// End of swoole v.5.0.3
