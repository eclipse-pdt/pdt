<?php

// Start of standard v.8.2.6

#[AllowDynamicProperties]
final class __PHP_Incomplete_Class  {
}

/**
 * AssertionError is thrown when
 * an assertion made via assert fails.
 * @link http://www.php.net/manual/en/class.assertionerror.php
 */
class AssertionError extends Error implements Throwable, Stringable {

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

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
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

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
 * Children of this class are passed to
 * stream_filter_register.
 * Note that the __construct method is not called;
 * instead, php_user_filter::onCreate should be used for
 * initialization.
 * @link http://www.php.net/manual/en/class.php_user_filter.php
 */
class php_user_filter  {

	/**
	 * Name of the filter registered by
	 * stream_filter_append.
	 * @var string
	 * @link http://www.php.net/manual/en/class.php_user_filter.php#php_user_filter.props.filtername
	 */
	public string $filtername;

	public mixed $params;

	public  $stream;

	/**
	 * Called when applying the filter
	 * @link http://www.php.net/manual/en/php-user-filter.filter.php
	 * @param resource $in in is a resource pointing to a bucket brigade
	 * which contains one or more bucket objects containing data to be filtered.
	 * @param resource $out out is a resource pointing to a second bucket brigade
	 * into which your modified buckets should be placed.
	 * @param int $consumed consumed, which must always
	 * be declared by reference, should be incremented by the length of the data
	 * which your filter reads in and alters. In most cases this means you will
	 * increment consumed by $bucket-&gt;datalen
	 * for each $bucket.
	 * @param bool $closing If the stream is in the process of closing
	 * (and therefore this is the last pass through the filterchain),
	 * the closing parameter will be set to true.
	 * @return int The filter method must return one of
	 * three values upon completion.
	 * <table>
	 * <tr valign="top">
	 * <td>Return Value</td>
	 * <td>Meaning</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>PSFS_PASS_ON</td>
	 * <td>
	 * Filter processed successfully with data available in the
	 * out bucket brigade.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>PSFS_FEED_ME</td>
	 * <td>
	 * Filter processed successfully, however no data was available to
	 * return. More data is required from the stream or prior filter.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>PSFS_ERR_FATAL (default)</td>
	 * <td>
	 * The filter experienced an unrecoverable error and cannot continue.
	 * </td>
	 * </tr>
	 * </table>
	 */
	public function filter ($in, $out, int &$consumed, bool $closing): int {}

	/**
	 * Called when creating the filter
	 * @link http://www.php.net/manual/en/php-user-filter.oncreate.php
	 * @return bool Your implementation of
	 * this method should return false on failure, or true on success.
	 */
	public function onCreate (): bool {}

	/**
	 * Called when closing the filter
	 * @link http://www.php.net/manual/en/php-user-filter.onclose.php
	 * @return void Return value is ignored.
	 */
	public function onClose (): void {}

}

/**
 * Instances of Directory are created by calling the
 * dir function, not by the new operator.
 * @link http://www.php.net/manual/en/class.directory.php
 */
class Directory  {

	/**
	 * The directory that was opened.
	 * @var string
	 * @link http://www.php.net/manual/en/class.directory.php#directory.props.path
	 */
	public readonly string $path;

	/**
	 * Can be used with other directory functions such as
	 * readdir, rewinddir and
	 * closedir.
	 * @var resource
	 * @link http://www.php.net/manual/en/class.directory.php#directory.props.handle
	 */
	public readonly  $handle;

	/**
	 * Close directory handle
	 * @link http://www.php.net/manual/en/directory.close.php
	 * @return void 
	 */
	public function close (): void {}

	/**
	 * Rewind directory handle
	 * @link http://www.php.net/manual/en/directory.rewind.php
	 * @return void 
	 */
	public function rewind (): void {}

	/**
	 * Read entry from directory handle
	 * @link http://www.php.net/manual/en/directory.read.php
	 * @return string|false 
	 */
	public function read (): string|false {}

}

/**
 * Limits the maximum execution time
 * @link http://www.php.net/manual/en/function.set-time-limit.php
 * @param int $seconds 
 * @return bool Returns true on success, or false on failure.
 */
function set_time_limit (int $seconds): bool {}

/**
 * Call a header function
 * @link http://www.php.net/manual/en/function.header-register-callback.php
 * @param callable $callback Function called just before the headers are sent. It gets no parameters
 * and the return value is ignored.
 * @return bool Returns true on success or false on failure.
 */
function header_register_callback (callable $callback): bool {}

/**
 * Turn on output buffering
 * @link http://www.php.net/manual/en/function.ob-start.php
 * @param callable $callback [optional] 
 * @param int $chunk_size [optional] 
 * @param int $flags [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ob_start (callable $callback = null, int $chunk_size = null, int $flags = PHP_OUTPUT_HANDLER_STDFLAGS): bool {}

/**
 * Flush (send) the output buffer
 * @link http://www.php.net/manual/en/function.ob-flush.php
 * @return bool Returns true on success or false on failure.
 */
function ob_flush (): bool {}

/**
 * Clean (erase) the output buffer
 * @link http://www.php.net/manual/en/function.ob-clean.php
 * @return bool Returns true on success or false on failure.
 */
function ob_clean (): bool {}

/**
 * Flush (send) the output buffer and turn off output buffering
 * @link http://www.php.net/manual/en/function.ob-end-flush.php
 * @return bool Returns true on success or false on failure. Reasons for failure are first that you called the
 * function without an active buffer or that for some reason a buffer could
 * not be deleted (possible for special buffer).
 */
function ob_end_flush (): bool {}

/**
 * Clean (erase) the output buffer and turn off output buffering
 * @link http://www.php.net/manual/en/function.ob-end-clean.php
 * @return bool Returns true on success or false on failure. Reasons for failure are first that you called the
 * function without an active buffer or that for some reason a buffer could
 * not be deleted (possible for special buffer).
 */
function ob_end_clean (): bool {}

/**
 * Flush the output buffer, return it as a string and turn off output buffering
 * @link http://www.php.net/manual/en/function.ob-get-flush.php
 * @return string|false Returns the output buffer or false if no buffering is active.
 */
function ob_get_flush (): string|false {}

/**
 * Get current buffer contents and delete current output buffer
 * @link http://www.php.net/manual/en/function.ob-get-clean.php
 * @return string|false Returns the contents of the output buffer and end output buffering.
 * If output buffering isn't active then false is returned.
 */
function ob_get_clean (): string|false {}

/**
 * Return the contents of the output buffer
 * @link http://www.php.net/manual/en/function.ob-get-contents.php
 * @return string|false This will return the contents of the output buffer or false, if output
 * buffering isn't active.
 */
function ob_get_contents (): string|false {}

/**
 * Return the nesting level of the output buffering mechanism
 * @link http://www.php.net/manual/en/function.ob-get-level.php
 * @return int Returns the level of nested output buffering handlers or zero if output
 * buffering is not active.
 */
function ob_get_level (): int {}

/**
 * Return the length of the output buffer
 * @link http://www.php.net/manual/en/function.ob-get-length.php
 * @return int|false Returns the length of the output buffer contents, in bytes, or false if no
 * buffering is active.
 */
function ob_get_length (): int|false {}

/**
 * List all output handlers in use
 * @link http://www.php.net/manual/en/function.ob-list-handlers.php
 * @return array This will return an array with the output handlers in use (if any). If
 * output_buffering is enabled or
 * an anonymous function was used with ob_start,
 * ob_list_handlers will return "default output
 * handler".
 */
function ob_list_handlers (): array {}

/**
 * Get status of output buffers
 * @link http://www.php.net/manual/en/function.ob-get-status.php
 * @param bool $full_status [optional] 
 * @return array If called without the full_status parameter
 * or with full_status = false a simple array
 * with the following elements is returned:
 * <pre>
 * Array
 * (
 * [level] => 2
 * [type] => 0
 * [status] => 0
 * [name] => URL-Rewriter
 * [del] => 1
 * )
 * </pre>
 * <p>
 * Simple ob_get_status results
 * KeyValue
 * levelOutput nesting level
 * type0 (internal handler) or 1 (user supplied handler)
 * statusOne of PHP_OUTPUT_HANDLER_START (0), PHP_OUTPUT_HANDLER_CONT (1) or PHP_OUTPUT_HANDLER_END (2)
 * nameName of active output handler or ' default output handler' if none is set
 * delErase-flag as set by ob_start
 * </p>
 * <p>If called with full_status = true an array
 * with one element for each active output buffer level is returned.
 * The output level is used as key of the top level array and each array
 * element itself is another array holding status information
 * on one active output level.
 * <pre>
 * Array
 * (
 * [0] => Array
 * (
 * [chunk_size] => 0
 * [size] => 40960
 * [block_size] => 10240
 * [type] => 1
 * [status] => 0
 * [name] => default output handler
 * [del] => 1
 * )
 * [1] => Array
 * (
 * [chunk_size] => 0
 * [size] => 40960
 * [block_size] => 10240
 * [type] => 0
 * [buffer_size] => 0
 * [status] => 0
 * [name] => URL-Rewriter
 * [del] => 1
 * )
 * )
 * </pre></p>
 * <p>The full output contains these additional elements:
 * <p>
 * Full ob_get_status results
 * KeyValue
 * chunk_sizeChunk size as set by ob_start
 * size...
 * blocksize...
 * </p></p>
 */
function ob_get_status (bool $full_status = false): array {}

/**
 * Turn implicit flush on/off
 * @link http://www.php.net/manual/en/function.ob-implicit-flush.php
 * @param bool $enable [optional] 
 * @return void No value is returned.
 */
function ob_implicit_flush (bool $enable = true): void {}

/**
 * Reset URL rewriter values
 * @link http://www.php.net/manual/en/function.output-reset-rewrite-vars.php
 * @return bool Returns true on success or false on failure.
 */
function output_reset_rewrite_vars (): bool {}

/**
 * Add URL rewriter values
 * @link http://www.php.net/manual/en/function.output-add-rewrite-var.php
 * @param string $name 
 * @param string $value 
 * @return bool Returns true on success or false on failure.
 */
function output_add_rewrite_var (string $name, string $value): bool {}

/**
 * Register a URL wrapper implemented as a PHP class
 * @link http://www.php.net/manual/en/function.stream-wrapper-register.php
 * @param string $protocol 
 * @param string $class 
 * @param int $flags [optional] 
 * @return bool Returns true on success or false on failure.
 * <p>stream_wrapper_register will return false if the
 * protocol already has a handler.</p>
 */
function stream_wrapper_register (string $protocol, string $class, int $flags = null): bool {}

/**
 * {@inheritdoc}
 * @param string $protocol
 * @param string $class
 * @param int $flags [optional]
 */
function stream_register_wrapper (string $protocol, string $class, int $flags = 0): bool {}

/**
 * Unregister a URL wrapper
 * @link http://www.php.net/manual/en/function.stream-wrapper-unregister.php
 * @param string $protocol 
 * @return bool Returns true on success or false on failure.
 */
function stream_wrapper_unregister (string $protocol): bool {}

/**
 * Restores a previously unregistered built-in wrapper
 * @link http://www.php.net/manual/en/function.stream-wrapper-restore.php
 * @param string $protocol 
 * @return bool Returns true on success or false on failure.
 */
function stream_wrapper_restore (string $protocol): bool {}

/**
 * Push one or more elements onto the end of array
 * @link http://www.php.net/manual/en/function.array-push.php
 * @param array $array 
 * @param mixed $values 
 * @return int Returns the new number of elements in the array.
 */
function array_push (array &$array, mixed ...$values): int {}

/**
 * Sort an array by key in descending order
 * @link http://www.php.net/manual/en/function.krsort.php
 * @param array $array 
 * @param int $flags [optional] 
 * @return true Always returns true.
 */
function krsort (array &$array, int $flags = SORT_REGULAR): true {}

/**
 * Sort an array by key in ascending order
 * @link http://www.php.net/manual/en/function.ksort.php
 * @param array $array 
 * @param int $flags [optional] 
 * @return true Always returns true.
 */
function ksort (array &$array, int $flags = SORT_REGULAR): true {}

/**
 * Counts all elements in an array or in a Countable object
 * @link http://www.php.net/manual/en/function.count.php
 * @param Countable|array $value 
 * @param int $mode [optional] 
 * @return int Returns the number of elements in value.
 * Prior to PHP 8.0.0, if the parameter was neither an array nor an object that
 * implements the Countable interface,
 * 1 would be returned,
 * unless value was null, in which case
 * 0 would be returned.
 */
function count (Countable|array $value, int $mode = COUNT_NORMAL): int {}

/**
 * Alias of count
 * @link http://www.php.net/manual/en/function.sizeof.php
 * @param Countable|array $value 
 * @param int $mode [optional] 
 * @return int Returns the number of elements in value.
 * Prior to PHP 8.0.0, if the parameter was neither an array nor an object that
 * implements the Countable interface,
 * 1 would be returned,
 * unless value was null, in which case
 * 0 would be returned.
 */
function sizeof (Countable|array $value, int $mode = COUNT_NORMAL): int {}

/**
 * Sort an array using a "natural order" algorithm
 * @link http://www.php.net/manual/en/function.natsort.php
 * @param array $array 
 * @return true Always returns true.
 */
function natsort (array &$array): true {}

/**
 * Sort an array using a case insensitive "natural order" algorithm
 * @link http://www.php.net/manual/en/function.natcasesort.php
 * @param array $array 
 * @return true Always returns true.
 */
function natcasesort (array &$array): true {}

/**
 * Sort an array in ascending order and maintain index association
 * @link http://www.php.net/manual/en/function.asort.php
 * @param array $array 
 * @param int $flags [optional] 
 * @return true Always returns true.
 */
function asort (array &$array, int $flags = SORT_REGULAR): true {}

/**
 * Sort an array in descending order and maintain index association
 * @link http://www.php.net/manual/en/function.arsort.php
 * @param array $array 
 * @param int $flags [optional] 
 * @return true Always returns true.
 */
function arsort (array &$array, int $flags = SORT_REGULAR): true {}

/**
 * Sort an array in ascending order
 * @link http://www.php.net/manual/en/function.sort.php
 * @param array $array 
 * @param int $flags [optional] 
 * @return true Always returns true.
 */
function sort (array &$array, int $flags = SORT_REGULAR): true {}

/**
 * Sort an array in descending order
 * @link http://www.php.net/manual/en/function.rsort.php
 * @param array $array 
 * @param int $flags [optional] 
 * @return true Always returns true.
 */
function rsort (array &$array, int $flags = SORT_REGULAR): true {}

/**
 * Sort an array by values using a user-defined comparison function
 * @link http://www.php.net/manual/en/function.usort.php
 * @param array $array 
 * @param callable $callback 
 * @return true Always returns true.
 */
function usort (array &$array, callable $callback): true {}

/**
 * Sort an array with a user-defined comparison function and maintain index association
 * @link http://www.php.net/manual/en/function.uasort.php
 * @param array $array 
 * @param callable $callback 
 * @return true Always returns true.
 */
function uasort (array &$array, callable $callback): true {}

/**
 * Sort an array by keys using a user-defined comparison function
 * @link http://www.php.net/manual/en/function.uksort.php
 * @param array $array 
 * @param callable $callback 
 * @return true Always returns true.
 */
function uksort (array &$array, callable $callback): true {}

/**
 * Set the internal pointer of an array to its last element
 * @link http://www.php.net/manual/en/function.end.php
 * @param array|object $array 
 * @return mixed Returns the value of the last element or false for empty array.
 */
function end (array|object &$array): mixed {}

/**
 * Rewind the internal array pointer
 * @link http://www.php.net/manual/en/function.prev.php
 * @param array|object $array 
 * @return mixed Returns the array value in the previous place that's pointed to by
 * the internal array pointer, or false if there are no more
 * elements.
 */
function prev (array|object &$array): mixed {}

/**
 * Advance the internal pointer of an array
 * @link http://www.php.net/manual/en/function.next.php
 * @param array|object $array 
 * @return mixed Returns the array value in the next place that's pointed to by the
 * internal array pointer, or false if there are no more elements.
 */
function next (array|object &$array): mixed {}

/**
 * Set the internal pointer of an array to its first element
 * @link http://www.php.net/manual/en/function.reset.php
 * @param array|object $array 
 * @return mixed Returns the value of the first array element, or false if the array is
 * empty.
 */
function reset (array|object &$array): mixed {}

/**
 * Return the current element in an array
 * @link http://www.php.net/manual/en/function.current.php
 * @param array|object $array 
 * @return mixed The current function simply returns the
 * value of the array element that's currently being pointed to by the
 * internal pointer. It does not move the pointer in any way. If the
 * internal pointer points beyond the end of the elements list or the array is 
 * empty, current returns false.
 */
function current (array|object $array): mixed {}

/**
 * Alias of current
 * @link http://www.php.net/manual/en/function.pos.php
 * @param array|object $array 
 * @return mixed The current function simply returns the
 * value of the array element that's currently being pointed to by the
 * internal pointer. It does not move the pointer in any way. If the
 * internal pointer points beyond the end of the elements list or the array is 
 * empty, current returns false.
 */
function pos (array|object $array): mixed {}

/**
 * Fetch a key from an array
 * @link http://www.php.net/manual/en/function.key.php
 * @param array|object $array 
 * @return int|string|null The key function simply returns the
 * key of the array element that's currently being pointed to by the
 * internal pointer. It does not move the pointer in any way. If the
 * internal pointer points beyond the end of the elements list or the array is 
 * empty, key returns null.
 */
function key (array|object $array): int|string|null {}

/**
 * Find lowest value
 * @link http://www.php.net/manual/en/function.min.php
 * @param mixed $value 
 * @param mixed $values 
 * @return mixed min returns the parameter value considered "lowest" according to standard
 * comparisons. If multiple values of different types evaluate as equal (e.g. 0
 * and 'abc') the first provided to the function will be returned.
 */
function min (mixed $value, mixed ...$values): mixed {}

/**
 * Find highest value
 * @link http://www.php.net/manual/en/function.max.php
 * @param mixed $value 
 * @param mixed $values 
 * @return mixed max returns the parameter value considered "highest" according to standard
 * comparisons. If multiple values of different types evaluate as equal (e.g. 0
 * and 'abc') the first provided to the function will be returned.
 */
function max (mixed $value, mixed ...$values): mixed {}

/**
 * Apply a user supplied function to every member of an array
 * @link http://www.php.net/manual/en/function.array-walk.php
 * @param array|object $array 
 * @param callable $callback 
 * @param mixed $arg [optional] 
 * @return bool Returns true.
 */
function array_walk (array|object &$array, callable $callback, mixed $arg = null): bool {}

/**
 * Apply a user function recursively to every member of an array
 * @link http://www.php.net/manual/en/function.array-walk-recursive.php
 * @param array|object $array 
 * @param callable $callback 
 * @param mixed $arg [optional] 
 * @return bool Returns true on success or false on failure.
 */
function array_walk_recursive (array|object &$array, callable $callback, mixed $arg = null): bool {}

/**
 * Checks if a value exists in an array
 * @link http://www.php.net/manual/en/function.in-array.php
 * @param mixed $needle 
 * @param array $haystack 
 * @param bool $strict [optional] 
 * @return bool Returns true if needle is found in the array,
 * false otherwise.
 */
function in_array (mixed $needle, array $haystack, bool $strict = false): bool {}

/**
 * Searches the array for a given value and returns the first corresponding key if successful
 * @link http://www.php.net/manual/en/function.array-search.php
 * @param mixed $needle 
 * @param array $haystack 
 * @param bool $strict [optional] 
 * @return int|string|false Returns the key for needle if it is found in the
 * array, false otherwise.
 * <p>If needle is found in haystack
 * more than once, the first matching key is returned. To return the keys for
 * all matching values, use array_keys with the optional
 * search_value parameter instead.</p>
 */
function array_search (mixed $needle, array $haystack, bool $strict = false): int|string|false {}

/**
 * Import variables into the current symbol table from an array
 * @link http://www.php.net/manual/en/function.extract.php
 * @param array $array 
 * @param int $flags [optional] 
 * @param string $prefix [optional] 
 * @return int Returns the number of variables successfully imported into the symbol
 * table.
 */
function extract (array &$array, int $flags = EXTR_OVERWRITE, string $prefix = '""'): int {}

/**
 * Create array containing variables and their values
 * @link http://www.php.net/manual/en/function.compact.php
 * @param array|string $var_name 
 * @param array|string $var_names 
 * @return array Returns the output array with all the variables added to it.
 */
function compact (array|string $var_name, array|string ...$var_names): array {}

/**
 * Fill an array with values
 * @link http://www.php.net/manual/en/function.array-fill.php
 * @param int $start_index 
 * @param int $count 
 * @param mixed $value 
 * @return array Returns the filled array
 */
function array_fill (int $start_index, int $count, mixed $value): array {}

/**
 * Fill an array with values, specifying keys
 * @link http://www.php.net/manual/en/function.array-fill-keys.php
 * @param array $keys 
 * @param mixed $value 
 * @return array Returns the filled array
 */
function array_fill_keys (array $keys, mixed $value): array {}

/**
 * Create an array containing a range of elements
 * @link http://www.php.net/manual/en/function.range.php
 * @param string|int|float $start 
 * @param string|int|float $end 
 * @param int|float $step [optional] 
 * @return array Returns an array of elements from start to
 * end, inclusive.
 */
function range (string|int|float $start, string|int|float $end, int|float $step = 1): array {}

/**
 * Shuffle an array
 * @link http://www.php.net/manual/en/function.shuffle.php
 * @param array $array 
 * @return true Always returns true.
 */
function shuffle (array &$array): true {}

/**
 * Pop the element off the end of array
 * @link http://www.php.net/manual/en/function.array-pop.php
 * @param array $array 
 * @return mixed Returns the value of the last element of array.
 * If array is empty,
 * null will be returned.
 */
function array_pop (array &$array): mixed {}

/**
 * Shift an element off the beginning of array
 * @link http://www.php.net/manual/en/function.array-shift.php
 * @param array $array 
 * @return mixed Returns the shifted value, or null if array is
 * empty or is not an array.
 */
function array_shift (array &$array): mixed {}

/**
 * Prepend one or more elements to the beginning of an array
 * @link http://www.php.net/manual/en/function.array-unshift.php
 * @param array $array 
 * @param mixed $values 
 * @return int Returns the new number of elements in the array.
 */
function array_unshift (array &$array, mixed ...$values): int {}

/**
 * Remove a portion of the array and replace it with something else
 * @link http://www.php.net/manual/en/function.array-splice.php
 * @param array $array 
 * @param int $offset 
 * @param int|null $length [optional] 
 * @param mixed $replacement [optional] 
 * @return array Returns an array consisting of the extracted elements.
 */
function array_splice (array &$array, int $offset, ?int $length = null, mixed $replacement = '[]'): array {}

/**
 * Extract a slice of the array
 * @link http://www.php.net/manual/en/function.array-slice.php
 * @param array $array 
 * @param int $offset 
 * @param int|null $length [optional] 
 * @param bool $preserve_keys [optional] 
 * @return array Returns the slice. If the offset is larger than the size of the array,
 * an empty array is returned.
 */
function array_slice (array $array, int $offset, ?int $length = null, bool $preserve_keys = false): array {}

/**
 * Merge one or more arrays
 * @link http://www.php.net/manual/en/function.array-merge.php
 * @param array $arrays 
 * @return array Returns the resulting array.
 * If called without any arguments, returns an empty array.
 */
function array_merge (array ...$arrays): array {}

/**
 * Merge one or more arrays recursively
 * @link http://www.php.net/manual/en/function.array-merge-recursive.php
 * @param array $arrays 
 * @return array An array of values resulted from merging the arguments together.
 * If called without any arguments, returns an empty array.
 */
function array_merge_recursive (array ...$arrays): array {}

/**
 * Replaces elements from passed arrays into the first array
 * @link http://www.php.net/manual/en/function.array-replace.php
 * @param array $array 
 * @param array $replacements 
 * @return array Returns an array.
 */
function array_replace (array $array, array ...$replacements): array {}

/**
 * Replaces elements from passed arrays into the first array recursively
 * @link http://www.php.net/manual/en/function.array-replace-recursive.php
 * @param array $array 
 * @param array $replacements 
 * @return array Returns an array.
 */
function array_replace_recursive (array $array, array ...$replacements): array {}

/**
 * Return all the keys or a subset of the keys of an array
 * @link http://www.php.net/manual/en/function.array-keys.php
 * @param array $array 
 * @return array Returns an array of all the keys in array.
 */
function array_keys (array $array): array {}

/**
 * Gets the first key of an array
 * @link http://www.php.net/manual/en/function.array-key-first.php
 * @param array $array An array.
 * @return int|string|null Returns the first key of array if the array is not empty;
 * null otherwise.
 */
function array_key_first (array $array): int|string|null {}

/**
 * Gets the last key of an array
 * @link http://www.php.net/manual/en/function.array-key-last.php
 * @param array $array An array.
 * @return int|string|null Returns the last key of array if the array is not empty;
 * null otherwise.
 */
function array_key_last (array $array): int|string|null {}

/**
 * Return all the values of an array
 * @link http://www.php.net/manual/en/function.array-values.php
 * @param array $array 
 * @return array Returns an indexed array of values.
 */
function array_values (array $array): array {}

/**
 * Counts all the values of an array
 * @link http://www.php.net/manual/en/function.array-count-values.php
 * @param array $array 
 * @return array Returns an associative array of values from array as
 * keys and their count as value.
 */
function array_count_values (array $array): array {}

/**
 * Return the values from a single column in the input array
 * @link http://www.php.net/manual/en/function.array-column.php
 * @param array $array 
 * @param int|string|null $column_key 
 * @param int|string|null $index_key [optional] 
 * @return array Returns an array of values representing a single column from the input array.
 */
function array_column (array $array, int|string|null $column_key, int|string|null $index_key = null): array {}

/**
 * Return an array with elements in reverse order
 * @link http://www.php.net/manual/en/function.array-reverse.php
 * @param array $array 
 * @param bool $preserve_keys [optional] 
 * @return array Returns the reversed array.
 */
function array_reverse (array $array, bool $preserve_keys = false): array {}

/**
 * Pad array to the specified length with a value
 * @link http://www.php.net/manual/en/function.array-pad.php
 * @param array $array 
 * @param int $length 
 * @param mixed $value 
 * @return array Returns a copy of the array padded to size specified
 * by length with value 
 * value. If length is 
 * positive then the array is padded on the right, if it's negative then 
 * on the left. If the absolute value of length is less
 * than or equal to the length of the array then no
 * padding takes place.
 */
function array_pad (array $array, int $length, mixed $value): array {}

/**
 * Exchanges all keys with their associated values in an array
 * @link http://www.php.net/manual/en/function.array-flip.php
 * @param array $array 
 * @return array Returns the flipped array.
 */
function array_flip (array $array): array {}

/**
 * Changes the case of all keys in an array
 * @link http://www.php.net/manual/en/function.array-change-key-case.php
 * @param array $array 
 * @param int $case [optional] 
 * @return array Returns an array with its keys lower or uppercased, or null if
 * array is not an array.
 */
function array_change_key_case (array $array, int $case = CASE_LOWER): array {}

/**
 * Removes duplicate values from an array
 * @link http://www.php.net/manual/en/function.array-unique.php
 * @param array $array 
 * @param int $flags [optional] 
 * @return array Returns the filtered array.
 */
function array_unique (array $array, int $flags = SORT_STRING): array {}

/**
 * Computes the intersection of arrays using keys for comparison
 * @link http://www.php.net/manual/en/function.array-intersect-key.php
 * @param array $array 
 * @param array $arrays 
 * @return array Returns an associative array containing all the entries of 
 * array which have keys that are present in all
 * arguments.
 */
function array_intersect_key (array $array, array ...$arrays): array {}

/**
 * Computes the intersection of arrays using a callback function on the keys for comparison
 * @link http://www.php.net/manual/en/function.array-intersect-ukey.php
 * @param array $array 
 * @param array $arrays 
 * @param callable $key_compare_func 
 * @return array Returns the values of array whose keys exist
 * in all the arguments.
 */
function array_intersect_ukey (array $array, array $arrays, callable $key_compare_func): array {}

/**
 * Computes the intersection of arrays
 * @link http://www.php.net/manual/en/function.array-intersect.php
 * @param array $array 
 * @param array $arrays 
 * @return array Returns an array containing all of the values in 
 * array whose values exist in all of the parameters.
 */
function array_intersect (array $array, array ...$arrays): array {}

/**
 * Computes the intersection of arrays, compares data by a callback function
 * @link http://www.php.net/manual/en/function.array-uintersect.php
 * @param array $array 
 * @param array $arrays 
 * @param callable $value_compare_func 
 * @return array Returns an array containing all the values of array
 * that are present in all the arguments.
 */
function array_uintersect (array $array, array $arrays, callable $value_compare_func): array {}

/**
 * Computes the intersection of arrays with additional index check
 * @link http://www.php.net/manual/en/function.array-intersect-assoc.php
 * @param array $array 
 * @param array $arrays 
 * @return array Returns an associative array containing all the values in 
 * array that are present in all of the arguments.
 */
function array_intersect_assoc (array $array, array ...$arrays): array {}

/**
 * Computes the intersection of arrays with additional index check, compares data by a callback function
 * @link http://www.php.net/manual/en/function.array-uintersect-assoc.php
 * @param array $array 
 * @param array $arrays 
 * @param callable $value_compare_func 
 * @return array Returns an array containing all the values of
 * array that are present in all the arguments.
 */
function array_uintersect_assoc (array $array, array $arrays, callable $value_compare_func): array {}

/**
 * Computes the intersection of arrays with additional index check, compares indexes by a callback function
 * @link http://www.php.net/manual/en/function.array-intersect-uassoc.php
 * @param array $array 
 * @param array $arrays 
 * @param callable $key_compare_func 
 * @return array Returns the values of array whose values exist
 * in all of the arguments.
 */
function array_intersect_uassoc (array $array, array $arrays, callable $key_compare_func): array {}

/**
 * Computes the intersection of arrays with additional index check, compares data and indexes by separate callback functions
 * @link http://www.php.net/manual/en/function.array-uintersect-uassoc.php
 * @param array $array1 
 * @param array $arrays 
 * @param callable $value_compare_func 
 * @param callable $key_compare_func 
 * @return array Returns an array containing all the values of
 * array1 that are present in all the arguments.
 */
function array_uintersect_uassoc (array $array1, array $arrays, callable $value_compare_func, callable $key_compare_func): array {}

/**
 * Computes the difference of arrays using keys for comparison
 * @link http://www.php.net/manual/en/function.array-diff-key.php
 * @param array $array 
 * @param array $arrays 
 * @return array Returns an array containing all the entries from
 * array whose keys are absent from all of the
 * other arrays.
 */
function array_diff_key (array $array, array ...$arrays): array {}

/**
 * Computes the difference of arrays using a callback function on the keys for comparison
 * @link http://www.php.net/manual/en/function.array-diff-ukey.php
 * @param array $array 
 * @param array $arrays 
 * @param callable $key_compare_func 
 * @return array Returns an array containing all the entries from
 * array that are not present in any of the other arrays.
 */
function array_diff_ukey (array $array, array $arrays, callable $key_compare_func): array {}

/**
 * Computes the difference of arrays
 * @link http://www.php.net/manual/en/function.array-diff.php
 * @param array $array 
 * @param array $arrays 
 * @return array Returns an array containing all the entries from
 * array that are not present in any of the other arrays.
 * Keys in the array array are preserved.
 */
function array_diff (array $array, array ...$arrays): array {}

/**
 * Computes the difference of arrays by using a callback function for data comparison
 * @link http://www.php.net/manual/en/function.array-udiff.php
 * @param array $array 
 * @param array $arrays 
 * @param callable $value_compare_func 
 * @return array Returns an array containing all the values of array
 * that are not present in any of the other arguments.
 */
function array_udiff (array $array, array $arrays, callable $value_compare_func): array {}

/**
 * Computes the difference of arrays with additional index check
 * @link http://www.php.net/manual/en/function.array-diff-assoc.php
 * @param array $array 
 * @param array $arrays 
 * @return array Returns an array containing all the values from
 * array that are not present in any of the other arrays.
 */
function array_diff_assoc (array $array, array ...$arrays): array {}

/**
 * Computes the difference of arrays with additional index check which is performed by a user supplied callback function
 * @link http://www.php.net/manual/en/function.array-diff-uassoc.php
 * @param array $array 
 * @param array $arrays 
 * @param callable $key_compare_func 
 * @return array Returns an array containing all the entries from
 * array that are not present in any of the other arrays.
 */
function array_diff_uassoc (array $array, array $arrays, callable $key_compare_func): array {}

/**
 * Computes the difference of arrays with additional index check, compares data by a callback function
 * @link http://www.php.net/manual/en/function.array-udiff-assoc.php
 * @param array $array 
 * @param array $arrays 
 * @param callable $value_compare_func 
 * @return array array_udiff_assoc returns an array
 * containing all the values from array
 * that are not present in any of the other arguments.
 * Note that the keys are used in the comparison unlike
 * array_diff and array_udiff.
 * The comparison of arrays' data is performed by using an user-supplied
 * callback. In this aspect the behaviour is opposite to the behaviour of
 * array_diff_assoc which uses internal function for
 * comparison.
 */
function array_udiff_assoc (array $array, array $arrays, callable $value_compare_func): array {}

/**
 * Computes the difference of arrays with additional index check, compares data and indexes by a callback function
 * @link http://www.php.net/manual/en/function.array-udiff-uassoc.php
 * @param array $array 
 * @param array $arrays 
 * @param callable $value_compare_func 
 * @param callable $key_compare_func 
 * @return array Returns an array containing all the values from
 * array that are not present in any of the other
 * arguments.
 */
function array_udiff_uassoc (array $array, array $arrays, callable $value_compare_func, callable $key_compare_func): array {}

/**
 * Sort multiple or multi-dimensional arrays
 * @link http://www.php.net/manual/en/function.array-multisort.php
 * @param array $array1 
 * @param mixed $array1_sort_order [optional] 
 * @param mixed $array1_sort_flags [optional] 
 * @param mixed $rest 
 * @return bool Returns true on success or false on failure.
 */
function array_multisort (array &$array1, mixed $array1_sort_order = SORT_ASC, mixed $array1_sort_flags = SORT_REGULAR, mixed ...$rest): bool {}

/**
 * Pick one or more random keys out of an array
 * @link http://www.php.net/manual/en/function.array-rand.php
 * @param array $array 
 * @param int $num [optional] 
 * @return int|string|array When picking only one entry, array_rand returns
 * the key for a random entry. Otherwise, an array of keys for the random
 * entries is returned. This is done so that random keys can be picked
 * from the array as well as random values. If multiple keys are returned,
 * they will be returned in the order they were present in the original array.
 * Trying to pick more elements
 * than there are in the array will result in an
 * E_WARNING level error, and NULL will be returned.
 */
function array_rand (array $array, int $num = 1): int|string|array {}

/**
 * Calculate the sum of values in an array
 * @link http://www.php.net/manual/en/function.array-sum.php
 * @param array $array 
 * @return int|float Returns the sum of values as an integer or float; 0 if the
 * array is empty.
 */
function array_sum (array $array): int|float {}

/**
 * Calculate the product of values in an array
 * @link http://www.php.net/manual/en/function.array-product.php
 * @param array $array 
 * @return int|float Returns the product as an integer or float.
 */
function array_product (array $array): int|float {}

/**
 * Iteratively reduce the array to a single value using a callback function
 * @link http://www.php.net/manual/en/function.array-reduce.php
 * @param array $array 
 * @param callable $callback 
 * @param mixed $initial [optional] 
 * @return mixed Returns the resulting value.
 * <p>If the array is empty and initial is not passed,
 * array_reduce returns null.</p>
 */
function array_reduce (array $array, callable $callback, mixed $initial = null): mixed {}

/**
 * Filters elements of an array using a callback function
 * @link http://www.php.net/manual/en/function.array-filter.php
 * @param array $array 
 * @param callable|null $callback [optional] 
 * @param int $mode [optional] 
 * @return array Returns the filtered array.
 */
function array_filter (array $array, ?callable $callback = null, int $mode = null): array {}

/**
 * Applies the callback to the elements of the given arrays
 * @link http://www.php.net/manual/en/function.array-map.php
 * @param callable|null $callback 
 * @param array $array 
 * @param array $arrays 
 * @return array Returns an array containing the results of applying the callback
 * function to the corresponding value of array
 * (and arrays if more arrays are provided)
 * used as arguments for the callback.
 * <p>The returned array will preserve the keys of the array argument if and only
 * if exactly one array is passed. If more than one array is passed, the
 * returned array will have sequential integer keys.</p>
 */
function array_map (?callable $callback, array $array, array ...$arrays): array {}

/**
 * Checks if the given key or index exists in the array
 * @link http://www.php.net/manual/en/function.array-key-exists.php
 * @param string|int $key 
 * @param array $array 
 * @return bool Returns true on success or false on failure.
 * <p>array_key_exists will search for the keys in the first dimension only.
 * Nested keys in multidimensional arrays will not be found.</p>
 */
function array_key_exists (string|int $key, array $array): bool {}

/**
 * Alias of array_key_exists
 * @link http://www.php.net/manual/en/function.key-exists.php
 * @param string|int $key 
 * @param array $array 
 * @return bool Returns true on success or false on failure.
 * <p>array_key_exists will search for the keys in the first dimension only.
 * Nested keys in multidimensional arrays will not be found.</p>
 */
function key_exists (string|int $key, array $array): bool {}

/**
 * Split an array into chunks
 * @link http://www.php.net/manual/en/function.array-chunk.php
 * @param array $array 
 * @param int $length 
 * @param bool $preserve_keys [optional] 
 * @return array Returns a multidimensional numerically indexed array, starting with zero,
 * with each dimension containing length elements.
 */
function array_chunk (array $array, int $length, bool $preserve_keys = false): array {}

/**
 * Creates an array by using one array for keys and another for its values
 * @link http://www.php.net/manual/en/function.array-combine.php
 * @param array $keys 
 * @param array $values 
 * @return array Returns the combined array.
 */
function array_combine (array $keys, array $values): array {}

/**
 * Checks whether a given array is a list
 * @link http://www.php.net/manual/en/function.array-is-list.php
 * @param array $array 
 * @return bool Returns true if array is a list, false
 * otherwise.
 */
function array_is_list (array $array): bool {}

/**
 * Encodes data with MIME base64
 * @link http://www.php.net/manual/en/function.base64-encode.php
 * @param string $string 
 * @return string The encoded data, as a string.
 */
function base64_encode (string $string): string {}

/**
 * Decodes data encoded with MIME base64
 * @link http://www.php.net/manual/en/function.base64-decode.php
 * @param string $string 
 * @param bool $strict [optional] 
 * @return string|false Returns the decoded data or false on failure. The returned data may be
 * binary.
 */
function base64_decode (string $string, bool $strict = false): string|false {}

/**
 * Returns the value of a constant
 * @link http://www.php.net/manual/en/function.constant.php
 * @param string $name 
 * @return mixed Returns the value of the constant.
 */
function constant (string $name): mixed {}

/**
 * Converts a string containing an (IPv4) Internet Protocol dotted address into a long integer
 * @link http://www.php.net/manual/en/function.ip2long.php
 * @param string $ip 
 * @return int|false Returns the long integer or false if ip
 * is invalid.
 */
function ip2long (string $ip): int|false {}

/**
 * Converts a long integer address into a string in (IPv4) Internet standard dotted format
 * @link http://www.php.net/manual/en/function.long2ip.php
 * @param int $ip 
 * @return string|false Returns the Internet IP address as a string, or false on failure.
 */
function long2ip (int $ip): string|false {}

/**
 * Gets the value of an environment variable
 * @link http://www.php.net/manual/en/function.getenv.php
 * @param string $varname 
 * @param bool $local_only [optional] 
 * @return string|false Returns the value of the environment variable
 * varname, or false if the environment
 * variable varname does not exist.
 * If varname is omitted, all environment variables are
 * returned as associative array.
 */
function getenv (string $varname, bool $local_only = false): string|false {}

/**
 * Sets the value of an environment variable
 * @link http://www.php.net/manual/en/function.putenv.php
 * @param string $assignment 
 * @return bool Returns true on success or false on failure.
 */
function putenv (string $assignment): bool {}

/**
 * Gets options from the command line argument list
 * @link http://www.php.net/manual/en/function.getopt.php
 * @param string $short_options 
 * @param array $long_options [optional] 
 * @param int $rest_index [optional] 
 * @return array|false This function will return an array of option / argument pairs, or false on failure.
 * <p>The parsing of options will end at the first non-option found, anything
 * that follows is discarded.</p>
 */
function getopt (string $short_options, array $long_options = '[]', int &$rest_index = null): array|false {}

/**
 * Flush system output buffer
 * @link http://www.php.net/manual/en/function.flush.php
 * @return void No value is returned.
 */
function flush (): void {}

/**
 * Delay execution
 * @link http://www.php.net/manual/en/function.sleep.php
 * @param int $seconds 
 * @return int Returns zero on success.
 * <p>If the call was interrupted by a signal, sleep returns
 * a non-zero value. On Windows, this value will always be
 * 192 (the value of the
 * WAIT_IO_COMPLETION constant within the Windows API).
 * On other platforms, the return value will be the number of seconds left to
 * sleep.</p>
 */
function sleep (int $seconds): int {}

/**
 * Delay execution in microseconds
 * @link http://www.php.net/manual/en/function.usleep.php
 * @param int $microseconds 
 * @return void No value is returned.
 */
function usleep (int $microseconds): void {}

/**
 * Delay for a number of seconds and nanoseconds
 * @link http://www.php.net/manual/en/function.time-nanosleep.php
 * @param int $seconds 
 * @param int $nanoseconds 
 * @return array|bool Returns true on success or false on failure.
 * <p>If the delay was interrupted by a signal, an associative array will be
 * returned with the components:
 * <p>
 * <br>
 * seconds - number of seconds remaining in
 * the delay
 * <br>
 * nanoseconds - number of nanoseconds
 * remaining in the delay
 * </p></p>
 */
function time_nanosleep (int $seconds, int $nanoseconds): array|bool {}

/**
 * Make the script sleep until the specified time
 * @link http://www.php.net/manual/en/function.time-sleep-until.php
 * @param float $timestamp 
 * @return bool Returns true on success or false on failure.
 */
function time_sleep_until (float $timestamp): bool {}

/**
 * Gets the name of the owner of the current PHP script
 * @link http://www.php.net/manual/en/function.get-current-user.php
 * @return string Returns the username as a string.
 */
function get_current_user (): string {}

/**
 * Gets the value of a PHP configuration option
 * @link http://www.php.net/manual/en/function.get-cfg-var.php
 * @param string $option 
 * @return string|array|false Returns the current value of the PHP configuration variable specified by
 * option, or false if an error occurs.
 */
function get_cfg_var (string $option): string|array|false {}

/**
 * Send an error message to the defined error handling routines
 * @link http://www.php.net/manual/en/function.error-log.php
 * @param string $message 
 * @param int $message_type [optional] 
 * @param string|null $destination [optional] 
 * @param string|null $additional_headers [optional] 
 * @return bool Returns true on success or false on failure.
 * If message_type is zero, this function always returns true,
 * regardless of whether the error could be logged or not.
 */
function error_log (string $message, int $message_type = null, ?string $destination = null, ?string $additional_headers = null): bool {}

/**
 * Get the last occurred error
 * @link http://www.php.net/manual/en/function.error-get-last.php
 * @return array|null Returns an associative array describing the last error with keys "type",
 * "message", "file" and "line". If the error has been caused by a PHP
 * internal function then the "message" begins with its name.
 * Returns null if there hasn't been an error yet.
 */
function error_get_last (): ?array {}

/**
 * Clear the most recent error
 * @link http://www.php.net/manual/en/function.error-clear-last.php
 * @return void Clears the most recent errors, making it unable to be retrieved with
 * error_get_last.
 */
function error_clear_last (): void {}

/**
 * Call the callback given by the first parameter
 * @link http://www.php.net/manual/en/function.call-user-func.php
 * @param callable $callback 
 * @param mixed $args 
 * @return mixed Returns the return value of the callback.
 */
function call_user_func (callable $callback, mixed ...$args): mixed {}

/**
 * Call a callback with an array of parameters
 * @link http://www.php.net/manual/en/function.call-user-func-array.php
 * @param callable $callback 
 * @param array $args 
 * @return mixed Returns the return value of the callback, or false on error.
 */
function call_user_func_array (callable $callback, array $args): mixed {}

/**
 * Call a static method
 * @link http://www.php.net/manual/en/function.forward-static-call.php
 * @param callable $callback 
 * @param mixed $args 
 * @return mixed Returns the function result, or false on error.
 */
function forward_static_call (callable $callback, mixed ...$args): mixed {}

/**
 * Call a static method and pass the arguments as array
 * @link http://www.php.net/manual/en/function.forward-static-call-array.php
 * @param callable $callback 
 * @param array $args 
 * @return mixed Returns the function result, or false on error.
 */
function forward_static_call_array (callable $callback, array $args): mixed {}

/**
 * Register a function for execution on shutdown
 * @link http://www.php.net/manual/en/function.register-shutdown-function.php
 * @param callable $callback 
 * @param mixed $args 
 * @return void No value is returned.
 */
function register_shutdown_function (callable $callback, mixed ...$args): void {}

/**
 * Syntax highlighting of a file
 * @link http://www.php.net/manual/en/function.highlight-file.php
 * @param string $filename 
 * @param bool $return [optional] 
 * @return string|bool If return is set to true, returns the highlighted
 * code as a string instead of printing it out. Otherwise, it will return
 * true on success, false on failure.
 */
function highlight_file (string $filename, bool $return = false): string|bool {}

/**
 * Alias of highlight_file
 * @link http://www.php.net/manual/en/function.show-source.php
 * @param string $filename 
 * @param bool $return [optional] 
 * @return string|bool If return is set to true, returns the highlighted
 * code as a string instead of printing it out. Otherwise, it will return
 * true on success, false on failure.
 */
function show_source (string $filename, bool $return = false): string|bool {}

/**
 * Return source with stripped comments and whitespace
 * @link http://www.php.net/manual/en/function.php-strip-whitespace.php
 * @param string $filename 
 * @return string The stripped source code will be returned on success, or an empty string
 * on failure.
 * <p>This function respects the value of the
 * short_open_tag
 * ini directive.</p>
 */
function php_strip_whitespace (string $filename): string {}

/**
 * Syntax highlighting of a string
 * @link http://www.php.net/manual/en/function.highlight-string.php
 * @param string $string 
 * @param bool $return [optional] 
 * @return string|bool If return is set to true, returns the highlighted
 * code as a string instead of printing it out. Otherwise, it will return
 * true on success, false on failure.
 */
function highlight_string (string $string, bool $return = false): string|bool {}

/**
 * Gets the value of a configuration option
 * @link http://www.php.net/manual/en/function.ini-get.php
 * @param string $option 
 * @return string|false Returns the value of the configuration option as a string on success, or an
 * empty string for null values. Returns false if the
 * configuration option doesn't exist.
 */
function ini_get (string $option): string|false {}

/**
 * Gets all configuration options
 * @link http://www.php.net/manual/en/function.ini-get-all.php
 * @param string|null $extension [optional] 
 * @param bool $details [optional] 
 * @return array|false Returns an associative array with directive name as the array key.
 * Returns false and raises an E_WARNING level error
 * if the extension doesn't exist.
 * <p>When details is true (default) the array will
 * contain global_value (set in
 * php.ini), local_value (perhaps set with
 * ini_set or .htaccess), and
 * access (the access level).</p>
 * <p>When details is false the value will be the
 * current value of the option.</p>
 * <p>See the manual section
 * for information on what access levels mean.</p>
 * <p>It's possible for a directive to have multiple access levels, which is
 * why access shows the appropriate bitmask values.</p>
 */
function ini_get_all (?string $extension = null, bool $details = true): array|false {}

/**
 * Sets the value of a configuration option
 * @link http://www.php.net/manual/en/function.ini-set.php
 * @param string $option 
 * @param string|int|float|bool|null $value 
 * @return string|false Returns the old value on success, false on failure.
 */
function ini_set (string $option, string|int|float|bool|null $value): string|false {}

/**
 * Alias of ini_set
 * @link http://www.php.net/manual/en/function.ini-alter.php
 * @param string $option 
 * @param string|int|float|bool|null $value 
 * @return string|false Returns the old value on success, false on failure.
 */
function ini_alter (string $option, string|int|float|bool|null $value): string|false {}

/**
 * Restores the value of a configuration option
 * @link http://www.php.net/manual/en/function.ini-restore.php
 * @param string $option 
 * @return void No value is returned.
 */
function ini_restore (string $option): void {}

/**
 * Get interpreted size from ini shorthand syntax
 * @link http://www.php.net/manual/en/function.ini-parse-quantity.php
 * @param string $shorthand 
 * @return int Returns the interpreted size in bytes as an int.
 */
function ini_parse_quantity (string $shorthand): int {}

/**
 * Sets the include_path configuration option
 * @link http://www.php.net/manual/en/function.set-include-path.php
 * @param string $include_path 
 * @return string|false Returns the old include_path on
 * success or false on failure.
 */
function set_include_path (string $include_path): string|false {}

/**
 * Gets the current include_path configuration option
 * @link http://www.php.net/manual/en/function.get-include-path.php
 * @return string|false Returns the path, as a string, or false on failure.
 */
function get_include_path (): string|false {}

/**
 * Prints human-readable information about a variable
 * @link http://www.php.net/manual/en/function.print-r.php
 * @param mixed $value 
 * @param bool $return [optional] 
 * @return string|bool If given a string, int or float,
 * the value itself will be printed. If given an array, values
 * will be presented in a format that shows keys and elements. Similar
 * notation is used for objects.
 * <p>When the return parameter is true, this function
 * will return a string. Otherwise, the return value is true.</p>
 */
function print_r (mixed $value, bool $return = false): string|bool {}

/**
 * Check whether client disconnected
 * @link http://www.php.net/manual/en/function.connection-aborted.php
 * @return int Returns 1 if client disconnected, 0 otherwise.
 */
function connection_aborted (): int {}

/**
 * Returns connection status bitfield
 * @link http://www.php.net/manual/en/function.connection-status.php
 * @return int Returns the connection status bitfield, which can be used against the
 * CONNECTION_XXX constants to determine the connection
 * status.
 */
function connection_status (): int {}

/**
 * Set whether a client disconnect should abort script execution
 * @link http://www.php.net/manual/en/function.ignore-user-abort.php
 * @param bool|null $enable [optional] 
 * @return int Returns the previous setting, as an integer.
 */
function ignore_user_abort (?bool $enable = null): int {}

/**
 * Get port number associated with an Internet service and protocol
 * @link http://www.php.net/manual/en/function.getservbyname.php
 * @param string $service 
 * @param string $protocol 
 * @return int|false Returns the port number, or false if service or
 * protocol is not found.
 */
function getservbyname (string $service, string $protocol): int|false {}

/**
 * Get Internet service which corresponds to port and protocol
 * @link http://www.php.net/manual/en/function.getservbyport.php
 * @param int $port 
 * @param string $protocol 
 * @return string|false Returns the Internet service name as a string, or false on failure.
 */
function getservbyport (int $port, string $protocol): string|false {}

/**
 * Get protocol number associated with protocol name
 * @link http://www.php.net/manual/en/function.getprotobyname.php
 * @param string $protocol 
 * @return int|false Returns the protocol number, or false on failure.
 */
function getprotobyname (string $protocol): int|false {}

/**
 * Get protocol name associated with protocol number
 * @link http://www.php.net/manual/en/function.getprotobynumber.php
 * @param int $protocol 
 * @return string|false Returns the protocol name as a string, or false on failure.
 */
function getprotobynumber (int $protocol): string|false {}

/**
 * Register a function for execution on each tick
 * @link http://www.php.net/manual/en/function.register-tick-function.php
 * @param callable $callback 
 * @param mixed $args 
 * @return bool Returns true on success or false on failure.
 */
function register_tick_function (callable $callback, mixed ...$args): bool {}

/**
 * De-register a function for execution on each tick
 * @link http://www.php.net/manual/en/function.unregister-tick-function.php
 * @param callable $callback 
 * @return void No value is returned.
 */
function unregister_tick_function (callable $callback): void {}

/**
 * Tells whether the file was uploaded via HTTP POST
 * @link http://www.php.net/manual/en/function.is-uploaded-file.php
 * @param string $filename 
 * @return bool Returns true on success or false on failure.
 */
function is_uploaded_file (string $filename): bool {}

/**
 * Moves an uploaded file to a new location
 * @link http://www.php.net/manual/en/function.move-uploaded-file.php
 * @param string $from 
 * @param string $to 
 * @return bool Returns true on success.
 * <p>If from is not a valid upload file,
 * then no action will occur, and
 * move_uploaded_file will return
 * false.</p>
 * <p>If from is a valid upload file, but
 * cannot be moved for some reason, no action will occur, and
 * move_uploaded_file will return
 * false. Additionally, a warning will be issued.</p>
 */
function move_uploaded_file (string $from, string $to): bool {}

/**
 * Parse a configuration file
 * @link http://www.php.net/manual/en/function.parse-ini-file.php
 * @param string $filename 
 * @param bool $process_sections [optional] 
 * @param int $scanner_mode [optional] 
 * @return array|false The settings are returned as an associative array on success,
 * and false on failure.
 */
function parse_ini_file (string $filename, bool $process_sections = false, int $scanner_mode = INI_SCANNER_NORMAL): array|false {}

/**
 * Parse a configuration string
 * @link http://www.php.net/manual/en/function.parse-ini-string.php
 * @param string $ini_string 
 * @param bool $process_sections [optional] 
 * @param int $scanner_mode [optional] 
 * @return array|false The settings are returned as an associative array on success,
 * and false on failure.
 */
function parse_ini_string (string $ini_string, bool $process_sections = false, int $scanner_mode = INI_SCANNER_NORMAL): array|false {}

/**
 * Gets system load average
 * @link http://www.php.net/manual/en/function.sys-getloadavg.php
 * @return array|false Returns an array with three samples (last 1, 5 and 15
 * minutes).
 */
function sys_getloadavg (): array|false {}

/**
 * Tells what the user's browser is capable of
 * @link http://www.php.net/manual/en/function.get-browser.php
 * @param string|null $user_agent [optional] 
 * @param bool $return_array [optional] 
 * @return object|array|false The information is returned in an object or an array which will contain
 * various data elements representing, for instance, the browser's major and
 * minor version numbers and ID string; true/false values for features
 * such as frames, JavaScript, and cookies; and so forth.
 * <p>The cookies value simply means that the browser
 * itself is capable of accepting cookies and does not mean the user has
 * enabled the browser to accept cookies or not. The only way to test if
 * cookies are accepted is to set one with setcookie,
 * reload, and check for the value.</p>
 * <p>Returns false when no information can be retrieved, such as when the
 * browscap configuration setting in 
 * php.ini has not been set.</p>
 */
function get_browser (?string $user_agent = null, bool $return_array = false): object|array|false {}

/**
 * Calculates the crc32 polynomial of a string
 * @link http://www.php.net/manual/en/function.crc32.php
 * @param string $string 
 * @return int Returns the crc32 checksum of string as an integer.
 */
function crc32 (string $string): int {}

/**
 * One-way string hashing
 * @link http://www.php.net/manual/en/function.crypt.php
 * @param string $string 
 * @param string $salt 
 * @return string Returns the hashed string or a string that is shorter than 13 characters
 * and is guaranteed to differ from the salt on failure.
 */
function crypt (string $string, string $salt): string {}

/**
 * Parse a time/date generated with strftime
 * @link http://www.php.net/manual/en/function.strptime.php
 * @param string $timestamp 
 * @param string $format 
 * @return array|false Returns an array or false on failure.
 * <p><table>
 * The following parameters are returned in the array
 * <table>
 * <tr valign="top">
 * <td>parameters</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>"tm_sec"</td>
 * <td>Seconds after the minute (0-61)</td>
 * </tr>
 * <tr valign="top">
 * <td>"tm_min"</td>
 * <td>Minutes after the hour (0-59)</td>
 * </tr>
 * <tr valign="top">
 * <td>"tm_hour"</td>
 * <td>Hour since midnight (0-23)</td>
 * </tr>
 * <tr valign="top">
 * <td>"tm_mday"</td>
 * <td>Day of the month (1-31)</td>
 * </tr>
 * <tr valign="top">
 * <td>"tm_mon"</td>
 * <td>Months since January (0-11)</td>
 * </tr>
 * <tr valign="top">
 * <td>"tm_year"</td>
 * <td>Years since 1900</td>
 * </tr>
 * <tr valign="top">
 * <td>"tm_wday"</td>
 * <td>Days since Sunday (0-6)</td>
 * </tr>
 * <tr valign="top">
 * <td>"tm_yday"</td>
 * <td>Days since January 1 (0-365)</td>
 * </tr>
 * <tr valign="top">
 * <td>"unparsed"</td>
 * <td>the timestamp part which was not
 * recognized using the specified format</td>
 * </tr>
 * </table>
 * </table></p>
 * @deprecated 1
 */
function strptime (string $timestamp, string $format): array|false {}

/**
 * Gets the host name
 * @link http://www.php.net/manual/en/function.gethostname.php
 * @return string|false Returns a string with the hostname on success, otherwise false is 
 * returned.
 */
function gethostname (): string|false {}

/**
 * Get the Internet host name corresponding to a given IP address
 * @link http://www.php.net/manual/en/function.gethostbyaddr.php
 * @param string $ip 
 * @return string|false Returns the host name on success, the unmodified ip
 * on failure, or false on malformed input.
 */
function gethostbyaddr (string $ip): string|false {}

/**
 * Get the IPv4 address corresponding to a given Internet host name
 * @link http://www.php.net/manual/en/function.gethostbyname.php
 * @param string $hostname 
 * @return string Returns the IPv4 address or a string containing the unmodified
 * hostname on failure.
 */
function gethostbyname (string $hostname): string {}

/**
 * Get a list of IPv4 addresses corresponding to a given Internet host
 * name
 * @link http://www.php.net/manual/en/function.gethostbynamel.php
 * @param string $hostname 
 * @return array|false Returns an array of IPv4 addresses or false if
 * hostname could not be resolved.
 */
function gethostbynamel (string $hostname): array|false {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param string $type [optional]
 */
function dns_check_record (string $hostname, string $type = 'MX'): bool {}

/**
 * Check DNS records corresponding to a given Internet host name or IP address
 * @link http://www.php.net/manual/en/function.checkdnsrr.php
 * @param string $hostname 
 * @param string $type [optional] 
 * @return bool Returns true if any records are found; returns false if no records
 * were found or if an error occurred.
 */
function checkdnsrr (string $hostname, string $type = '"MX"'): bool {}

/**
 * Fetch DNS Resource Records associated with a hostname
 * @link http://www.php.net/manual/en/function.dns-get-record.php
 * @param string $hostname 
 * @param int $type [optional] 
 * @param array $authoritative_name_servers [optional] 
 * @param array $additional_records [optional] 
 * @param bool $raw [optional] 
 * @return array|false This function returns an array of associative arrays,
 * or false on failure. Each associative array contains
 * at minimum the following keys:
 * <table>
 * Basic DNS attributes
 * <table>
 * <tr valign="top">
 * <td>Attribute</td>
 * <td>Meaning</td>
 * </tr>
 * <tr valign="top">
 * <td>host</td>
 * <td>
 * The record in the DNS namespace to which the rest of the associated data refers.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>class</td>
 * <td>
 * dns_get_record only returns Internet class records and as
 * such this parameter will always return IN.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>type</td>
 * <td>
 * String containing the record type. Additional attributes will also be contained
 * in the resulting array dependant on the value of type. See table below.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ttl</td>
 * <td>
 * "Time To Live" remaining for this record. This will not equal
 * the record's original ttl, but will rather equal the original ttl minus whatever
 * length of time has passed since the authoritative name server was queried.
 * </td>
 * </tr>
 * </table>
 * </table>
 * <p><table>
 * Other keys in associative arrays dependant on 'type'
 * <table>
 * <tr valign="top">
 * <td>Type</td>
 * <td>Extra Columns</td>
 * </tr>
 * <tr valign="top">
 * <td>A</td>
 * <td>
 * ip: An IPv4 addresses in dotted decimal notation.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>MX</td>
 * <td>
 * pri: Priority of mail exchanger.
 * Lower numbers indicate greater priority.
 * target: FQDN of the mail exchanger.
 * See also dns_get_mx.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CNAME</td>
 * <td>
 * target: FQDN of location in DNS namespace to which
 * the record is aliased.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>NS</td>
 * <td>
 * target: FQDN of the name server which is authoritative
 * for this hostname.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>PTR</td>
 * <td>
 * target: Location within the DNS namespace to which
 * this record points.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>TXT</td>
 * <td>
 * txt: Arbitrary string data associated with this record.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>HINFO</td>
 * <td>
 * cpu: IANA number designating the CPU of the machine
 * referenced by this record.
 * os: IANA number designating the Operating System on
 * the machine referenced by this record.
 * See IANA's Operating System
 * Names for the meaning of these values.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CAA</td>
 * <td>
 * flags: A one-byte bitfield; currently only bit 0 is defined,
 * meaning 'critical'; other bits are reserved and should be ignored.
 * tag: The CAA tag name (alphanumeric ASCII string).
 * value: The CAA tag value (binary string, may use subformats).
 * For additional information see: RFC 6844
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>SOA</td>
 * <td>
 * mname: FQDN of the machine from which the resource
 * records originated.
 * rname: Email address of the administrative contact
 * for this domain.
 * serial: Serial # of this revision of the requested
 * domain.
 * refresh: Refresh interval (seconds) secondary name
 * servers should use when updating remote copies of this domain.
 * retry: Length of time (seconds) to wait after a
 * failed refresh before making a second attempt.
 * expire: Maximum length of time (seconds) a secondary
 * DNS server should retain remote copies of the zone data without a
 * successful refresh before discarding.
 * minimum-ttl: Minimum length of time (seconds) a
 * client can continue to use a DNS resolution before it should request
 * a new resolution from the server. Can be overridden by individual
 * resource records.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>AAAA</td>
 * <td>
 * ipv6: IPv6 address
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>A6</td>
 * <td>
 * masklen: Length (in bits) to inherit from the target
 * specified by chain.
 * ipv6: Address for this specific record to merge with
 * chain.
 * chain: Parent record to merge with
 * ipv6 data.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>SRV</td>
 * <td>
 * pri: (Priority) lowest priorities should be used first.
 * weight: Ranking to weight which of commonly prioritized
 * targets should be chosen at random.
 * target and port: hostname and port
 * where the requested service can be found.
 * For additional information see: RFC 2782
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>NAPTR</td>
 * <td>
 * order and pref: Equivalent to
 * pri and weight above.
 * flags, services, regex,
 * and replacement: Parameters as defined by
 * RFC 2915.
 * </td>
 * </tr>
 * </table>
 * </table></p>
 */
function dns_get_record (string $hostname, int $type = DNS_ANY, array &$authoritative_name_servers = null, array &$additional_records = null, bool $raw = false): array|false {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param mixed $hosts
 * @param mixed $weights [optional]
 */
function dns_get_mx (string $hostname, &$hosts = null, &$weights = NULL): bool {}

/**
 * Get MX records corresponding to a given Internet host name
 * @link http://www.php.net/manual/en/function.getmxrr.php
 * @param string $hostname 
 * @param array $hosts 
 * @param array $weights [optional] 
 * @return bool Returns true if any records are found; returns false if no records
 * were found or if an error occurred.
 */
function getmxrr (string $hostname, array &$hosts, array &$weights = null): bool {}

/**
 * Get network interfaces
 * @link http://www.php.net/manual/en/function.net-get-interfaces.php
 * @return array|false Returns an associative array where the key is the name of the interface and
 * the value an associative array of interface attributes,
 * or false on failure.
 * <p>Each interface associative array contains:
 * <table>
 * Interface attributes
 * <table>
 * <tr valign="top">
 * <td>Name</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>description</td>
 * <td>
 * Optional string value for description of the interface.
 * Windows only.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>mac</td>
 * <td>
 * Optional string value for MAC address of the interface.
 * Windows only.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>mtu</td>
 * <td>
 * Integer value for Maximum transmission unit (MTU) of the interface.
 * Windows only.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>unicast</td>
 * <td>
 * Array of associative arrays, see Unicast attributes below.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>up</td>
 * <td>
 * Boolean status (on/off) for interface.
 * </td>
 * </tr>
 * </table>
 * </table></p>
 * <p><table>
 * Unicast attributes
 * <table>
 * <tr valign="top">
 * <td>Name</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>flags</td>
 * <td>
 * Integer value.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>family</td>
 * <td>
 * Integer value.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>address</td>
 * <td>
 * String value for address in either IPv4 or IPv6.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>netmask</td>
 * <td>
 * String value for netmask in either IPv4 or IPv6.
 * </td>
 * </tr>
 * </table>
 * </table></p>
 */
function net_get_interfaces (): array|false {}

/**
 * Convert a pathname and a project identifier to a System V IPC key
 * @link http://www.php.net/manual/en/function.ftok.php
 * @param string $filename 
 * @param string $project_id 
 * @return int On success the return value will be the created key value, otherwise
 * -1 is returned.
 */
function ftok (string $filename, string $project_id): int {}

/**
 * Get the system's high resolution time
 * @link http://www.php.net/manual/en/function.hrtime.php
 * @param bool $as_number [optional] 
 * @return array|int|float|false Returns an array of integers in the form [seconds, nanoseconds], if the
 * parameter as_number is false. Otherwise the nanoseconds
 * are returned as int (64bit platforms) or float
 * (32bit platforms).
 * Returns false on failure.
 */
function hrtime (bool $as_number = false): array|int|float|false {}

/**
 * Calculate the md5 hash of a string
 * @link http://www.php.net/manual/en/function.md5.php
 * @param string $string 
 * @param bool $binary [optional] 
 * @return string Returns the hash as a 32-character hexadecimal number.
 */
function md5 (string $string, bool $binary = false): string {}

/**
 * Calculates the md5 hash of a given file
 * @link http://www.php.net/manual/en/function.md5-file.php
 * @param string $filename 
 * @param bool $binary [optional] 
 * @return string|false Returns a string on success, false otherwise.
 */
function md5_file (string $filename, bool $binary = false): string|false {}

/**
 * Gets PHP script owner's UID
 * @link http://www.php.net/manual/en/function.getmyuid.php
 * @return int|false Returns the user ID of the current script, or false on error.
 */
function getmyuid (): int|false {}

/**
 * Get PHP script owner's GID
 * @link http://www.php.net/manual/en/function.getmygid.php
 * @return int|false Returns the group ID of the current script, or false on error.
 */
function getmygid (): int|false {}

/**
 * Gets PHP's process ID
 * @link http://www.php.net/manual/en/function.getmypid.php
 * @return int|false Returns the current PHP process ID, or false on error.
 */
function getmypid (): int|false {}

/**
 * Gets the inode of the current script
 * @link http://www.php.net/manual/en/function.getmyinode.php
 * @return int|false Returns the current script's inode as an integer, or false on error.
 */
function getmyinode (): int|false {}

/**
 * Gets time of last page modification
 * @link http://www.php.net/manual/en/function.getlastmod.php
 * @return int|false Returns the time of the last modification of the current
 * page. The value returned is a Unix timestamp, suitable for
 * feeding to date. Returns false on error.
 */
function getlastmod (): int|false {}

/**
 * Calculate the sha1 hash of a string
 * @link http://www.php.net/manual/en/function.sha1.php
 * @param string $string 
 * @param bool $binary [optional] 
 * @return string Returns the sha1 hash as a string.
 */
function sha1 (string $string, bool $binary = false): string {}

/**
 * Calculate the sha1 hash of a file
 * @link http://www.php.net/manual/en/function.sha1-file.php
 * @param string $filename 
 * @param bool $binary [optional] 
 * @return string|false Returns a string on success, false otherwise.
 */
function sha1_file (string $filename, bool $binary = false): string|false {}

/**
 * Open connection to system logger
 * @link http://www.php.net/manual/en/function.openlog.php
 * @param string $prefix 
 * @param int $flags 
 * @param int $facility 
 * @return bool Returns true on success or false on failure.
 */
function openlog (string $prefix, int $flags, int $facility): bool {}

/**
 * Close connection to system logger
 * @link http://www.php.net/manual/en/function.closelog.php
 * @return true Always returns true.
 */
function closelog (): true {}

/**
 * Generate a system log message
 * @link http://www.php.net/manual/en/function.syslog.php
 * @param int $priority 
 * @param string $message 
 * @return true Always returns true.
 */
function syslog (int $priority, string $message): true {}

/**
 * Converts a packed internet address to a human readable representation
 * @link http://www.php.net/manual/en/function.inet-ntop.php
 * @param string $ip 
 * @return string|false Returns a string representation of the address or false on failure.
 */
function inet_ntop (string $ip): string|false {}

/**
 * Converts a human readable IP address to its packed in_addr representation
 * @link http://www.php.net/manual/en/function.inet-pton.php
 * @param string $ip 
 * @return string|false Returns the in_addr representation of the given
 * ip, or false if a syntactically invalid
 * ip is given (for example, an IPv4 address
 * without dots or an IPv6 address without colons).
 */
function inet_pton (string $ip): string|false {}

/**
 * Calculate the metaphone key of a string
 * @link http://www.php.net/manual/en/function.metaphone.php
 * @param string $string 
 * @param int $max_phonemes [optional] 
 * @return string Returns the metaphone key as a string.
 */
function metaphone (string $string, int $max_phonemes = null): string {}

/**
 * Send a raw HTTP header
 * @link http://www.php.net/manual/en/function.header.php
 * @param string $header 
 * @param bool $replace [optional] 
 * @param int $response_code [optional] 
 * @return void No value is returned.
 */
function header (string $header, bool $replace = true, int $response_code = null): void {}

/**
 * Remove previously set headers
 * @link http://www.php.net/manual/en/function.header-remove.php
 * @param string|null $name [optional] 
 * @return void No value is returned.
 */
function header_remove (?string $name = null): void {}

/**
 * Send a cookie without urlencoding the cookie value
 * @link http://www.php.net/manual/en/function.setrawcookie.php
 * @param string $name 
 * @param string $value [optional] 
 * @param int $expires_or_options [optional] 
 * @param string $path [optional] 
 * @param string $domain [optional] 
 * @param bool $secure [optional] 
 * @param bool $httponly [optional] 
 * @return bool Returns true on success or false on failure.
 */
function setrawcookie (string $name, string $value = null, int $expires_or_options = null, string $path = null, string $domain = null, bool $secure = false, bool $httponly = false): bool {}

/**
 * Send a cookie
 * @link http://www.php.net/manual/en/function.setcookie.php
 * @param string $name 
 * @param string $value [optional] 
 * @param int $expires_or_options [optional] 
 * @param string $path [optional] 
 * @param string $domain [optional] 
 * @param bool $secure [optional] 
 * @param bool $httponly [optional] 
 * @return bool If output exists prior to calling this function,
 * setcookie will fail and return false. If
 * setcookie successfully runs, it will return true.
 * This does not indicate whether the user accepted the cookie.
 */
function setcookie (string $name, string $value = '""', int $expires_or_options = null, string $path = '""', string $domain = '""', bool $secure = false, bool $httponly = false): bool {}

/**
 * Get or Set the HTTP response code
 * @link http://www.php.net/manual/en/function.http-response-code.php
 * @param int $response_code [optional] 
 * @return int|bool If response_code is provided, then the previous
 * status code will be returned. If response_code is not
 * provided, then the current status code will be returned. Both of these
 * values will default to a 200 status code if used in a web
 * server environment.
 * <p>false will be returned if response_code is not
 * provided and it is not invoked in a web server environment (such as from a
 * CLI application). true will be returned if
 * response_code is provided and it is not invoked in a
 * web server environment (but only when no previous response status has been
 * set).</p>
 */
function http_response_code (int $response_code = null): int|bool {}

/**
 * Checks if or where headers have been sent
 * @link http://www.php.net/manual/en/function.headers-sent.php
 * @param string $filename [optional] 
 * @param int $line [optional] 
 * @return bool headers_sent will return false if no HTTP headers
 * have already been sent or true otherwise.
 */
function headers_sent (string &$filename = null, int &$line = null): bool {}

/**
 * Returns a list of response headers sent (or ready to send)
 * @link http://www.php.net/manual/en/function.headers-list.php
 * @return array Returns a numerically indexed array of headers.
 */
function headers_list (): array {}

/**
 * Convert special characters to HTML entities
 * @link http://www.php.net/manual/en/function.htmlspecialchars.php
 * @param string $string 
 * @param int $flags [optional] 
 * @param string|null $encoding [optional] 
 * @param bool $double_encode [optional] 
 * @return string The converted string.
 * <p>If the input string contains an invalid code unit
 * sequence within the given encoding an empty string
 * will be returned, unless either the ENT_IGNORE or
 * ENT_SUBSTITUTE flags are set.</p>
 */
function htmlspecialchars (string $string, int $flags = 'ENT_QUOTES | ENT_SUBSTITUTE | ENT_HTML401', ?string $encoding = null, bool $double_encode = true): string {}

/**
 * Convert special HTML entities back to characters
 * @link http://www.php.net/manual/en/function.htmlspecialchars-decode.php
 * @param string $string 
 * @param int $flags [optional] 
 * @return string Returns the decoded string.
 */
function htmlspecialchars_decode (string $string, int $flags = 'ENT_QUOTES | ENT_SUBSTITUTE | ENT_HTML401'): string {}

/**
 * Convert HTML entities to their corresponding characters
 * @link http://www.php.net/manual/en/function.html-entity-decode.php
 * @param string $string 
 * @param int $flags [optional] 
 * @param string|null $encoding [optional] 
 * @return string Returns the decoded string.
 */
function html_entity_decode (string $string, int $flags = 'ENT_QUOTES | ENT_SUBSTITUTE | ENT_HTML401', ?string $encoding = null): string {}

/**
 * Convert all applicable characters to HTML entities
 * @link http://www.php.net/manual/en/function.htmlentities.php
 * @param string $string 
 * @param int $flags [optional] 
 * @param string|null $encoding [optional] 
 * @param bool $double_encode [optional] 
 * @return string Returns the encoded string.
 * <p>If the input string contains an invalid code unit
 * sequence within the given encoding an empty string
 * will be returned, unless either the ENT_IGNORE or
 * ENT_SUBSTITUTE flags are set.</p>
 */
function htmlentities (string $string, int $flags = 'ENT_QUOTES | ENT_SUBSTITUTE | ENT_HTML401', ?string $encoding = null, bool $double_encode = true): string {}

/**
 * Returns the translation table used by htmlspecialchars and htmlentities
 * @link http://www.php.net/manual/en/function.get-html-translation-table.php
 * @param int $table [optional] 
 * @param int $flags [optional] 
 * @param string $encoding [optional] 
 * @return array Returns the translation table as an array, with the original characters
 * as keys and entities as values.
 */
function get_html_translation_table (int $table = HTML_SPECIALCHARS, int $flags = 'ENT_QUOTES | ENT_SUBSTITUTE | ENT_HTML401', string $encoding = '"UTF-8"'): array {}

/**
 * Checks if assertion is false
 * @link http://www.php.net/manual/en/function.assert.php
 * @param mixed $assertion 
 * @param string $description [optional] 
 * @return bool false if the assertion is false, true otherwise.
 */
function assert (mixed $assertion, string $description = null): bool {}

/**
 * Set/get the various assert flags
 * @link http://www.php.net/manual/en/function.assert-options.php
 * @param int $what 
 * @param mixed $value [optional] 
 * @return mixed Returns the original setting of any option or false on errors.
 */
function assert_options (int $what, mixed $value = null): mixed {}

/**
 * Convert binary data into hexadecimal representation
 * @link http://www.php.net/manual/en/function.bin2hex.php
 * @param string $string 
 * @return string Returns the hexadecimal representation of the given string.
 */
function bin2hex (string $string): string {}

/**
 * Decodes a hexadecimally encoded binary string
 * @link http://www.php.net/manual/en/function.hex2bin.php
 * @param string $string Hexadecimal representation of data.
 * @return string|false Returns the binary representation of the given data or false on failure.
 */
function hex2bin (string $string): string|false {}

/**
 * Finds the length of the initial segment of a string consisting
 * entirely of characters contained within a given mask
 * @link http://www.php.net/manual/en/function.strspn.php
 * @param string $string 
 * @param string $characters 
 * @param int $offset [optional] 
 * @param int|null $length [optional] 
 * @return int Returns the length of the initial segment of string
 * which consists entirely of characters in characters.
 * <p>When a offset parameter is set, the returned length
 * is counted starting from this position, not from the beginning of
 * string.</p>
 */
function strspn (string $string, string $characters, int $offset = null, ?int $length = null): int {}

/**
 * Find length of initial segment not matching mask
 * @link http://www.php.net/manual/en/function.strcspn.php
 * @param string $string 
 * @param string $characters 
 * @param int $offset [optional] 
 * @param int|null $length [optional] 
 * @return int Returns the length of the initial segment of string
 * which consists entirely of characters not in characters.
 * <p>When a offset parameter is set, the returned length
 * is counted starting from this position, not from the beginning of
 * string.</p>
 */
function strcspn (string $string, string $characters, int $offset = null, ?int $length = null): int {}

/**
 * Query language and locale information
 * @link http://www.php.net/manual/en/function.nl-langinfo.php
 * @param int $item 
 * @return string|false Returns the element as a string, or false if item
 * is not valid.
 */
function nl_langinfo (int $item): string|false {}

/**
 * Locale based string comparison
 * @link http://www.php.net/manual/en/function.strcoll.php
 * @param string $string1 
 * @param string $string2 
 * @return int Returns &lt; 0 if string1 is less than
 * string2; &gt; 0 if
 * string1 is greater than
 * string2, and 0 if they are equal.
 */
function strcoll (string $string1, string $string2): int {}

/**
 * Strip whitespace (or other characters) from the beginning and end of a string
 * @link http://www.php.net/manual/en/function.trim.php
 * @param string $string 
 * @param string $characters [optional] 
 * @return string The trimmed string.
 */
function trim (string $string, string $characters = '" \\n\\r\\t\\v\\x00"'): string {}

/**
 * Strip whitespace (or other characters) from the end of a string
 * @link http://www.php.net/manual/en/function.rtrim.php
 * @param string $string 
 * @param string $characters [optional] 
 * @return string Returns the modified string.
 */
function rtrim (string $string, string $characters = '" \\n\\r\\t\\v\\x00"'): string {}

/**
 * Alias of rtrim
 * @link http://www.php.net/manual/en/function.chop.php
 * @param string $string 
 * @param string $characters [optional] 
 * @return string Returns the modified string.
 */
function chop (string $string, string $characters = '" \\n\\r\\t\\v\\x00"'): string {}

/**
 * Strip whitespace (or other characters) from the beginning of a string
 * @link http://www.php.net/manual/en/function.ltrim.php
 * @param string $string 
 * @param string $characters [optional] 
 * @return string This function returns a string with whitespace stripped from the
 * beginning of string.
 * Without the second parameter,
 * ltrim will strip these characters:
 * <p>
 * <br>
 * " " (ASCII 32
 * (0x20)), an ordinary space.
 * <br>
 * "\t" (ASCII 9
 * (0x09)), a tab.
 * <br>
 * "\n" (ASCII 10
 * (0x0A)), a new line (line feed).
 * <br>
 * "\r" (ASCII 13
 * (0x0D)), a carriage return.
 * <br>
 * "\0" (ASCII 0
 * (0x00)), the NUL-byte.
 * <br>
 * "\v" (ASCII 11
 * (0x0B)), a vertical tab.
 * </p>
 */
function ltrim (string $string, string $characters = '" \\n\\r\\t\\v\\x00"'): string {}

/**
 * Wraps a string to a given number of characters
 * @link http://www.php.net/manual/en/function.wordwrap.php
 * @param string $string 
 * @param int $width [optional] 
 * @param string $break [optional] 
 * @param bool $cut_long_words [optional] 
 * @return string Returns the given string wrapped at the specified length.
 */
function wordwrap (string $string, int $width = 75, string $break = '"\\n"', bool $cut_long_words = false): string {}

/**
 * Split a string by a string
 * @link http://www.php.net/manual/en/function.explode.php
 * @param string $separator 
 * @param string $string 
 * @param int $limit [optional] 
 * @return array Returns an array of strings
 * created by splitting the string parameter on
 * boundaries formed by the separator.
 * <p>If separator is an empty string (""),
 * explode throws a ValueError.
 * If separator contains a value that is not
 * contained in string and a negative
 * limit is used, then an empty array will be
 * returned, otherwise an array containing
 * string will be returned. If separator 
 * values appear at the start or end of string, said values 
 * will be added as an empty array value either in the first or last 
 * position of the returned array respectively.</p>
 */
function explode (string $separator, string $string, int $limit = PHP_INT_MAX): array {}

/**
 * Join array elements with a string
 * @link http://www.php.net/manual/en/function.implode.php
 * @param string $separator 
 * @param array $array 
 * @return string Returns a string containing a string representation of all the array
 * elements in the same order, with the separator string between each element.
 */
function implode (string $separator, array $array): string {}

/**
 * Alias of implode
 * @link http://www.php.net/manual/en/function.join.php
 * @param string $separator 
 * @param array $array 
 * @return string Returns a string containing a string representation of all the array
 * elements in the same order, with the separator string between each element.
 */
function join (string $separator, array $array): string {}

/**
 * Tokenize string
 * @link http://www.php.net/manual/en/function.strtok.php
 * @param string $string 
 * @param string $token 
 * @return string|false A string token, or false if no more tokens are available.
 */
function strtok (string $string, string $token): string|false {}

/**
 * Make a string uppercase
 * @link http://www.php.net/manual/en/function.strtoupper.php
 * @param string $string 
 * @return string Returns the uppercased string.
 */
function strtoupper (string $string): string {}

/**
 * Make a string lowercase
 * @link http://www.php.net/manual/en/function.strtolower.php
 * @param string $string 
 * @return string Returns the lowercased string.
 */
function strtolower (string $string): string {}

/**
 * Returns trailing name component of path
 * @link http://www.php.net/manual/en/function.basename.php
 * @param string $path 
 * @param string $suffix [optional] 
 * @return string Returns the base name of the given path.
 */
function basename (string $path, string $suffix = '""'): string {}

/**
 * Returns a parent directory's path
 * @link http://www.php.net/manual/en/function.dirname.php
 * @param string $path 
 * @param int $levels [optional] 
 * @return string Returns the path of a parent directory. If there are no slashes in
 * path, a dot ('.') is returned,
 * indicating the current directory. Otherwise, the returned string is
 * path with any trailing
 * /component removed.
 * <p>Be careful when using this function in a loop that can reach the
 * top-level directory as this can result in an infinite loop.
 * <pre>
 * <code>&lt;?php
 * dirname(&&#35;039;.&&#35;039;); &#47;&#47; Will return &&#35;039;.&&#35;039;.
 * dirname(&&#35;039;&#47;&&#35;039;); &#47;&#47; Will return `\` on Windows and &&#35;039;&#47;&&#35;039; on &#42;nix systems.
 * dirname(&&#35;039;\\&&#35;039;); &#47;&#47; Will return `\` on Windows and &&#35;039;.&&#35;039; on &#42;nix systems.
 * dirname(&&#35;039;C:\\&&#35;039;); &#47;&#47; Will return &&#35;039;C:\&&#35;039; on Windows and &&#35;039;.&&#35;039; on &#42;nix systems.
 * ?&gt;</code>
 * </pre></p>
 */
function dirname (string $path, int $levels = 1): string {}

/**
 * Returns information about a file path
 * @link http://www.php.net/manual/en/function.pathinfo.php
 * @param string $path 
 * @param int $flags [optional] 
 * @return array|string If the flags parameter is not passed, an
 * associative array containing the following elements is
 * returned:
 * dirname, basename,
 * extension (if any), and filename.
 * <p>If the path has more than one extension,
 * PATHINFO_EXTENSION returns only the last one and
 * PATHINFO_FILENAME only strips the last one.
 * (see first example below).</p>
 * <p>If the path does not have an extension, no
 * extension element will be returned
 * (see second example below).</p>
 * <p>If the basename of the path starts
 * with a dot, the following characters are interpreted as
 * extension, and the filename is empty
 * (see third example below).</p>
 * <p>If flags is present, returns a
 * string containing the requested element.</p>
 */
function pathinfo (string $path, int $flags = PATHINFO_ALL): array|string {}

/**
 * Case-insensitive strstr
 * @link http://www.php.net/manual/en/function.stristr.php
 * @param string $haystack 
 * @param string $needle 
 * @param bool $before_needle [optional] 
 * @return string|false Returns the matched substring. If needle is not
 * found, returns false.
 */
function stristr (string $haystack, string $needle, bool $before_needle = false): string|false {}

/**
 * Find the first occurrence of a string
 * @link http://www.php.net/manual/en/function.strstr.php
 * @param string $haystack 
 * @param string $needle 
 * @param bool $before_needle [optional] 
 * @return string|false Returns the portion of string, or false if needle
 * is not found.
 */
function strstr (string $haystack, string $needle, bool $before_needle = false): string|false {}

/**
 * Alias of strstr
 * @link http://www.php.net/manual/en/function.strchr.php
 * @param string $haystack 
 * @param string $needle 
 * @param bool $before_needle [optional] 
 * @return string|false Returns the portion of string, or false if needle
 * is not found.
 */
function strchr (string $haystack, string $needle, bool $before_needle = false): string|false {}

/**
 * Find the position of the first occurrence of a substring in a string
 * @link http://www.php.net/manual/en/function.strpos.php
 * @param string $haystack 
 * @param string $needle 
 * @param int $offset [optional] 
 * @return int|false Returns the position of where the needle exists relative to the beginning of
 * the haystack string (independent of offset).
 * Also note that string positions start at 0, and not 1.
 * <p>Returns false if the needle was not found.</p>
 */
function strpos (string $haystack, string $needle, int $offset = null): int|false {}

/**
 * Find the position of the first occurrence of a case-insensitive substring in a string
 * @link http://www.php.net/manual/en/function.stripos.php
 * @param string $haystack 
 * @param string $needle 
 * @param int $offset [optional] 
 * @return int|false Returns the position of where the needle exists relative to the beginning of
 * the haystack string (independent of offset).
 * Also note that string positions start at 0, and not 1.
 * <p>Returns false if the needle was not found.</p>
 */
function stripos (string $haystack, string $needle, int $offset = null): int|false {}

/**
 * Find the position of the last occurrence of a substring in a string
 * @link http://www.php.net/manual/en/function.strrpos.php
 * @param string $haystack 
 * @param string $needle 
 * @param int $offset [optional] 
 * @return int|false Returns the position where the needle exists relative to the beginning of
 * the haystack string (independent of search direction
 * or offset).
 * String positions start at 0, and not 1.
 * <p>Returns false if the needle was not found.</p>
 */
function strrpos (string $haystack, string $needle, int $offset = null): int|false {}

/**
 * Find the position of the last occurrence of a case-insensitive substring in a string
 * @link http://www.php.net/manual/en/function.strripos.php
 * @param string $haystack 
 * @param string $needle 
 * @param int $offset [optional] 
 * @return int|false Returns the position where the needle exists relative to the beginnning of
 * the haystack string (independent of search direction
 * or offset).
 * String positions start at 0, and not 1.
 * <p>Returns false if the needle was not found.</p>
 */
function strripos (string $haystack, string $needle, int $offset = null): int|false {}

/**
 * Find the last occurrence of a character in a string
 * @link http://www.php.net/manual/en/function.strrchr.php
 * @param string $haystack 
 * @param string $needle 
 * @return string|false This function returns the portion of string, or false if
 * needle is not found.
 */
function strrchr (string $haystack, string $needle): string|false {}

/**
 * Determine if a string contains a given substring
 * @link http://www.php.net/manual/en/function.str-contains.php
 * @param string $haystack 
 * @param string $needle 
 * @return bool Returns true if needle is in
 * haystack, false otherwise.
 */
function str_contains (string $haystack, string $needle): bool {}

/**
 * Checks if a string starts with a given substring
 * @link http://www.php.net/manual/en/function.str-starts-with.php
 * @param string $haystack 
 * @param string $needle 
 * @return bool Returns true if haystack begins with
 * needle, false otherwise.
 */
function str_starts_with (string $haystack, string $needle): bool {}

/**
 * Checks if a string ends with a given substring
 * @link http://www.php.net/manual/en/function.str-ends-with.php
 * @param string $haystack 
 * @param string $needle 
 * @return bool Returns true if haystack ends with
 * needle, false otherwise.
 */
function str_ends_with (string $haystack, string $needle): bool {}

/**
 * Split a string into smaller chunks
 * @link http://www.php.net/manual/en/function.chunk-split.php
 * @param string $string 
 * @param int $length [optional] 
 * @param string $separator [optional] 
 * @return string Returns the chunked string.
 */
function chunk_split (string $string, int $length = 76, string $separator = '"\\r\\n"'): string {}

/**
 * Return part of a string
 * @link http://www.php.net/manual/en/function.substr.php
 * @param string $string 
 * @param int $offset 
 * @param int|null $length [optional] 
 * @return string Returns the extracted part of string, or
 * an empty string.
 */
function substr (string $string, int $offset, ?int $length = null): string {}

/**
 * Replace text within a portion of a string
 * @link http://www.php.net/manual/en/function.substr-replace.php
 * @param array|string $string 
 * @param array|string $replace 
 * @param array|int $offset 
 * @param array|int|null $length [optional] 
 * @return string|array The result string is returned. If string is an
 * array then array is returned.
 */
function substr_replace (array|string $string, array|string $replace, array|int $offset, array|int|null $length = null): string|array {}

/**
 * Quote meta characters
 * @link http://www.php.net/manual/en/function.quotemeta.php
 * @param string $string 
 * @return string Returns the string with meta characters quoted, or false if an empty
 * string is given as string.
 */
function quotemeta (string $string): string {}

/**
 * Convert the first byte of a string to a value between 0 and 255
 * @link http://www.php.net/manual/en/function.ord.php
 * @param string $character 
 * @return int An integer between 0 and 255.
 */
function ord (string $character): int {}

/**
 * Generate a single-byte string from a number
 * @link http://www.php.net/manual/en/function.chr.php
 * @param int $codepoint 
 * @return string A single-character string containing the specified byte.
 */
function chr (int $codepoint): string {}

/**
 * Make a string's first character uppercase
 * @link http://www.php.net/manual/en/function.ucfirst.php
 * @param string $string 
 * @return string Returns the resulting string.
 */
function ucfirst (string $string): string {}

/**
 * Make a string's first character lowercase
 * @link http://www.php.net/manual/en/function.lcfirst.php
 * @param string $string 
 * @return string Returns the resulting string.
 */
function lcfirst (string $string): string {}

/**
 * Uppercase the first character of each word in a string
 * @link http://www.php.net/manual/en/function.ucwords.php
 * @param string $string 
 * @param string $separators [optional] 
 * @return string Returns the modified string.
 */
function ucwords (string $string, string $separators = '" \\t\\r\\n\\f\\v"'): string {}

/**
 * Translate characters or replace substrings
 * @link http://www.php.net/manual/en/function.strtr.php
 * @param string $string 
 * @param string $from 
 * @param string $to 
 * @return string Returns the translated string.
 */
function strtr (string $string, string $from, string $to): string {}

/**
 * Reverse a string
 * @link http://www.php.net/manual/en/function.strrev.php
 * @param string $string 
 * @return string Returns the reversed string.
 */
function strrev (string $string): string {}

/**
 * Calculate the similarity between two strings
 * @link http://www.php.net/manual/en/function.similar-text.php
 * @param string $string1 
 * @param string $string2 
 * @param float $percent [optional] 
 * @return int Returns the number of matching chars in both strings.
 * <p>The number of matching characters is calculated by finding the longest first
 * common substring, and then doing this for the prefixes and the suffixes,
 * recursively. The lengths of all found common substrings are added.</p>
 */
function similar_text (string $string1, string $string2, float &$percent = null): int {}

/**
 * Quote string with slashes in a C style
 * @link http://www.php.net/manual/en/function.addcslashes.php
 * @param string $string 
 * @param string $characters 
 * @return string Returns the escaped string.
 */
function addcslashes (string $string, string $characters): string {}

/**
 * Quote string with slashes
 * @link http://www.php.net/manual/en/function.addslashes.php
 * @param string $string 
 * @return string Returns the escaped string.
 */
function addslashes (string $string): string {}

/**
 * Un-quote string quoted with addcslashes
 * @link http://www.php.net/manual/en/function.stripcslashes.php
 * @param string $string 
 * @return string Returns the unescaped string.
 */
function stripcslashes (string $string): string {}

/**
 * Un-quotes a quoted string
 * @link http://www.php.net/manual/en/function.stripslashes.php
 * @param string $string 
 * @return string Returns a string with backslashes stripped off.
 * (\' becomes ' and so on.)
 * Double backslashes (\\) are made into a single
 * backslash (\).
 */
function stripslashes (string $string): string {}

/**
 * Replace all occurrences of the search string with the replacement string
 * @link http://www.php.net/manual/en/function.str-replace.php
 * @param array|string $search 
 * @param array|string $replace 
 * @param string|array $subject 
 * @param int $count [optional] 
 * @return string|array This function returns a string or an array with the replaced values.
 */
function str_replace (array|string $search, array|string $replace, string|array $subject, int &$count = null): string|array {}

/**
 * Case-insensitive version of str_replace
 * @link http://www.php.net/manual/en/function.str-ireplace.php
 * @param array|string $search 
 * @param array|string $replace 
 * @param string|array $subject 
 * @param int $count [optional] 
 * @return string|array Returns a string or an array of replacements.
 */
function str_ireplace (array|string $search, array|string $replace, string|array $subject, int &$count = null): string|array {}

/**
 * Convert logical Hebrew text to visual text
 * @link http://www.php.net/manual/en/function.hebrev.php
 * @param string $string 
 * @param int $max_chars_per_line [optional] 
 * @return string Returns the visual string.
 */
function hebrev (string $string, int $max_chars_per_line = null): string {}

/**
 * Inserts HTML line breaks before all newlines in a string
 * @link http://www.php.net/manual/en/function.nl2br.php
 * @param string $string 
 * @param bool $use_xhtml [optional] 
 * @return string Returns the altered string.
 */
function nl2br (string $string, bool $use_xhtml = true): string {}

/**
 * Strip HTML and PHP tags from a string
 * @link http://www.php.net/manual/en/function.strip-tags.php
 * @param string $string 
 * @param array|string|null $allowed_tags [optional] 
 * @return string Returns the stripped string.
 */
function strip_tags (string $string, array|string|null $allowed_tags = null): string {}

/**
 * Set locale information
 * @link http://www.php.net/manual/en/function.setlocale.php
 * @param int $category 
 * @param string $locales 
 * @param string $rest 
 * @return string|false Returns the new current locale, or false if the locale functionality is
 * not implemented on your platform, the specified locale does not exist or
 * the category name is invalid.
 * <p>An invalid category name also causes a warning message. Category/locale
 * names can be found in RFC 1766
 * and ISO 639.
 * Different systems have different naming schemes for locales.</p>
 * <p>The return value of setlocale depends
 * on the system that PHP is running. It returns exactly
 * what the system setlocale function returns.</p>
 */
function setlocale (int $category, string $locales, string ...$rest): string|false {}

/**
 * Parses the string into variables
 * @link http://www.php.net/manual/en/function.parse-str.php
 * @param string $string 
 * @param array $result 
 * @return void No value is returned.
 */
function parse_str (string $string, array &$result): void {}

/**
 * Parse a CSV string into an array
 * @link http://www.php.net/manual/en/function.str-getcsv.php
 * @param string $string 
 * @param string $separator [optional] 
 * @param string $enclosure [optional] 
 * @param string $escape [optional] 
 * @return array Returns an indexed array containing the fields read.
 */
function str_getcsv (string $string, string $separator = '","', string $enclosure = '"\\""', string $escape = '"\\\\"'): array {}

/**
 * Repeat a string
 * @link http://www.php.net/manual/en/function.str-repeat.php
 * @param string $string 
 * @param int $times 
 * @return string Returns the repeated string.
 */
function str_repeat (string $string, int $times): string {}

/**
 * Return information about characters used in a string
 * @link http://www.php.net/manual/en/function.count-chars.php
 * @param string $string 
 * @param int $mode [optional] 
 * @return array|string Depending on mode
 * count_chars returns one of the following:
 * <p>
 * <br>
 * 0 - an array with the byte-value as key and the frequency of
 * every byte as value.
 * <br>
 * 1 - same as 0 but only byte-values with a frequency greater
 * than zero are listed.
 * <br>
 * 2 - same as 0 but only byte-values with a frequency equal to
 * zero are listed.
 * <br>
 * 3 - a string containing all unique characters is returned.
 * <br>
 * 4 - a string containing all not used characters is returned.
 * </p>
 */
function count_chars (string $string, int $mode = null): array|string {}

/**
 * String comparisons using a "natural order" algorithm
 * @link http://www.php.net/manual/en/function.strnatcmp.php
 * @param string $string1 
 * @param string $string2 
 * @return int Similar to other string comparison functions, this one returns -1 if
 * string1 is less than string2;
 * 1 if string1 is greater than
 * string2, and 0 if they are equal.
 */
function strnatcmp (string $string1, string $string2): int {}

/**
 * Get numeric formatting information
 * @link http://www.php.net/manual/en/function.localeconv.php
 * @return array localeconv returns data based upon the current locale
 * as set by setlocale. The associative array that is
 * returned contains the following fields:
 * <table>
 * <tr valign="top">
 * <td>Array element</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>decimal_point</td>
 * <td>Decimal point character</td>
 * </tr>
 * <tr valign="top">
 * <td>thousands_sep</td>
 * <td>Thousands separator</td>
 * </tr>
 * <tr valign="top">
 * <td>grouping</td>
 * <td>Array containing numeric groupings</td>
 * </tr>
 * <tr valign="top">
 * <td>int_curr_symbol</td>
 * <td>International currency symbol (i.e. USD)</td>
 * </tr>
 * <tr valign="top">
 * <td>currency_symbol</td>
 * <td>Local currency symbol (i.e. $)</td>
 * </tr>
 * <tr valign="top">
 * <td>mon_decimal_point</td>
 * <td>Monetary decimal point character</td>
 * </tr>
 * <tr valign="top">
 * <td>mon_thousands_sep</td>
 * <td>Monetary thousands separator</td>
 * </tr>
 * <tr valign="top">
 * <td>mon_grouping</td>
 * <td>Array containing monetary groupings</td>
 * </tr>
 * <tr valign="top">
 * <td>positive_sign</td>
 * <td>Sign for positive values</td>
 * </tr>
 * <tr valign="top">
 * <td>negative_sign</td>
 * <td>Sign for negative values</td>
 * </tr>
 * <tr valign="top">
 * <td>int_frac_digits</td>
 * <td>International fractional digits</td>
 * </tr>
 * <tr valign="top">
 * <td>frac_digits</td>
 * <td>Local fractional digits</td>
 * </tr>
 * <tr valign="top">
 * <td>p_cs_precedes</td>
 * <td>
 * true if currency_symbol precedes a positive value, false
 * if it succeeds one
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>p_sep_by_space</td>
 * <td>
 * true if a space separates currency_symbol from a positive
 * value, false otherwise
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>n_cs_precedes</td>
 * <td>
 * true if currency_symbol precedes a negative value, false
 * if it succeeds one
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>n_sep_by_space</td>
 * <td>
 * true if a space separates currency_symbol from a negative
 * value, false otherwise
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>p_sign_posn</td>
 * <td>
 * <p>
 * 0 - Parentheses surround the quantity and currency_symbol
 * 1 - The sign string precedes the quantity and currency_symbol
 * 2 - The sign string succeeds the quantity and currency_symbol
 * 3 - The sign string immediately precedes the currency_symbol
 * 4 - The sign string immediately succeeds the currency_symbol
 * </p>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>n_sign_posn</td>
 * <td>
 * <p>
 * 0 - Parentheses surround the quantity and currency_symbol
 * 1 - The sign string precedes the quantity and currency_symbol
 * 2 - The sign string succeeds the quantity and currency_symbol
 * 3 - The sign string immediately precedes the currency_symbol
 * 4 - The sign string immediately succeeds the currency_symbol
 * </p>
 * </td>
 * </tr>
 * </table>
 * <p>The p_sign_posn, and n_sign_posn contain a string
 * of formatting options. Each number representing one of the above listed conditions.</p>
 * <p>The grouping fields contain arrays that define the way numbers should be
 * grouped. For example, the monetary grouping field for the nl_NL locale (in
 * UTF-8 mode with the euro sign), would contain a 2 item array with the
 * values 3 and 3. The higher the index in the array, the farther left the
 * grouping is. If an array element is equal to CHAR_MAX,
 * no further grouping is done. If an array element is equal to 0, the previous
 * element should be used.</p>
 */
function localeconv (): array {}

/**
 * Case insensitive string comparisons using a "natural order" algorithm
 * @link http://www.php.net/manual/en/function.strnatcasecmp.php
 * @param string $string1 
 * @param string $string2 
 * @return int Similar to other string comparison functions, this one returns -1 if
 * string1 is less than string2
 * 1 if string1 is greater than
 * string2, and 0 if they are equal.
 */
function strnatcasecmp (string $string1, string $string2): int {}

/**
 * Count the number of substring occurrences
 * @link http://www.php.net/manual/en/function.substr-count.php
 * @param string $haystack 
 * @param string $needle 
 * @param int $offset [optional] 
 * @param int|null $length [optional] 
 * @return int This function returns an int.
 */
function substr_count (string $haystack, string $needle, int $offset = null, ?int $length = null): int {}

/**
 * Pad a string to a certain length with another string
 * @link http://www.php.net/manual/en/function.str-pad.php
 * @param string $string 
 * @param int $length 
 * @param string $pad_string [optional] 
 * @param int $pad_type [optional] 
 * @return string Returns the padded string.
 */
function str_pad (string $string, int $length, string $pad_string = '" "', int $pad_type = STR_PAD_RIGHT): string {}

/**
 * Parses input from a string according to a format
 * @link http://www.php.net/manual/en/function.sscanf.php
 * @param string $string 
 * @param string $format 
 * @param mixed $vars 
 * @return array|int|null If only two parameters were passed to this function, the values parsed will
 * be returned as an array. Otherwise, if optional parameters are passed, the
 * function will return the number of assigned values. The optional parameters
 * must be passed by reference.
 * <p>If there are more substrings expected in the format
 * than there are available within string,
 * null will be returned.</p>
 */
function sscanf (string $string, string $format, mixed &...$vars): array|int|null {}

/**
 * Perform the rot13 transform on a string
 * @link http://www.php.net/manual/en/function.str-rot13.php
 * @param string $string 
 * @return string Returns the ROT13 version of the given string.
 */
function str_rot13 (string $string): string {}

/**
 * Randomly shuffles a string
 * @link http://www.php.net/manual/en/function.str-shuffle.php
 * @param string $string 
 * @return string Returns the shuffled string.
 */
function str_shuffle (string $string): string {}

/**
 * Return information about words used in a string
 * @link http://www.php.net/manual/en/function.str-word-count.php
 * @param string $string 
 * @param int $format [optional] 
 * @param string|null $characters [optional] 
 * @return array|int Returns an array or an integer, depending on the
 * format chosen.
 */
function str_word_count (string $string, int $format = null, ?string $characters = null): array|int {}

/**
 * Convert a string to an array
 * @link http://www.php.net/manual/en/function.str-split.php
 * @param string $string 
 * @param int $length [optional] 
 * @return array If the optional length parameter is
 * specified, the returned array will be broken down into chunks with each
 * being length in length, except the final chunk
 * which may be shorter if the string does not divide evenly. The default
 * length is 1, meaning every chunk will be one byte in size.
 */
function str_split (string $string, int $length = 1): array {}

/**
 * Search a string for any of a set of characters
 * @link http://www.php.net/manual/en/function.strpbrk.php
 * @param string $string 
 * @param string $characters 
 * @return string|false Returns a string starting from the character found, or false if it is
 * not found.
 */
function strpbrk (string $string, string $characters): string|false {}

/**
 * Binary safe comparison of two strings from an offset, up to length characters
 * @link http://www.php.net/manual/en/function.substr-compare.php
 * @param string $haystack 
 * @param string $needle 
 * @param int $offset 
 * @param int|null $length [optional] 
 * @param bool $case_insensitive [optional] 
 * @return int Returns -1 if haystack from position
 * offset is less than needle, 1
 * if it is greater than needle, and 0 if they are equal.
 * If offset is equal to (prior to PHP 7.2.18, 7.3.5) or
 * greater than the length of haystack, or the
 * length is set and is less than 0,
 * substr_compare prints a warning and returns
 * false.
 */
function substr_compare (string $haystack, string $needle, int $offset, ?int $length = null, bool $case_insensitive = false): int {}

/**
 * Converts a string from ISO-8859-1 to UTF-8
 * @link http://www.php.net/manual/en/function.utf8-encode.php
 * @param string $string 
 * @return string Returns the UTF-8 translation of string.
 * @deprecated 1
 */
function utf8_encode (string $string): string {}

/**
 * Converts a string from UTF-8 to ISO-8859-1, replacing invalid or unrepresentable
 * characters
 * @link http://www.php.net/manual/en/function.utf8-decode.php
 * @param string $string 
 * @return string Returns the ISO-8859-1 translation of string.
 * @deprecated 1
 */
function utf8_decode (string $string): string {}

/**
 * Open directory handle
 * @link http://www.php.net/manual/en/function.opendir.php
 * @param string $directory 
 * @param resource|null $context [optional] 
 * @return resource|false Returns a directory handle resource on success,
 * or false on failure
 */
function opendir (string $directory, $context = null) {}

/**
 * Return an instance of the Directory class
 * @link http://www.php.net/manual/en/function.dir.php
 * @param string $directory Directory to open
 * @param resource|null $context [optional] >A context stream
 * resource.
 * @return Directory|false Returns an instance of Directory, or false in case of error.
 */
function dir (string $directory, $context = null): Directory|false {}

/**
 * Close directory handle
 * @link http://www.php.net/manual/en/function.closedir.php
 * @param resource|null $dir_handle [optional] 
 * @return void No value is returned.
 */
function closedir ($dir_handle = null): void {}

/**
 * Change directory
 * @link http://www.php.net/manual/en/function.chdir.php
 * @param string $directory 
 * @return bool Returns true on success or false on failure.
 */
function chdir (string $directory): bool {}

/**
 * Gets the current working directory
 * @link http://www.php.net/manual/en/function.getcwd.php
 * @return string|false Returns the current working directory on success, or false on
 * failure.
 * <p>On some Unix variants, getcwd will return
 * false if any one of the parent directories does not have the
 * readable or search mode set, even if the current directory
 * does. See chmod for more information on
 * modes and permissions.</p>
 */
function getcwd (): string|false {}

/**
 * Rewind directory handle
 * @link http://www.php.net/manual/en/function.rewinddir.php
 * @param resource|null $dir_handle [optional] 
 * @return void No value is returned.
 */
function rewinddir ($dir_handle = null): void {}

/**
 * Read entry from directory handle
 * @link http://www.php.net/manual/en/function.readdir.php
 * @param resource|null $dir_handle [optional] 
 * @return string|false Returns the entry name on success or false on failure.
 */
function readdir ($dir_handle = null): string|false {}

/**
 * List files and directories inside the specified path
 * @link http://www.php.net/manual/en/function.scandir.php
 * @param string $directory 
 * @param int $sorting_order [optional] 
 * @param resource|null $context [optional] 
 * @return array|false Returns an array of filenames on success, or false on 
 * failure. If directory is not a directory, then 
 * boolean false is returned, and an error of level 
 * E_WARNING is generated.
 */
function scandir (string $directory, int $sorting_order = SCANDIR_SORT_ASCENDING, $context = null): array|false {}

/**
 * Find pathnames matching a pattern
 * @link http://www.php.net/manual/en/function.glob.php
 * @param string $pattern 
 * @param int $flags [optional] 
 * @return array|false Returns an array containing the matched files/directories, an empty array
 * if no file matched or false on error.
 * <p>On some systems it is impossible to distinguish between empty match and an
 * error.</p>
 */
function glob (string $pattern, int $flags = null): array|false {}

/**
 * Execute an external program
 * @link http://www.php.net/manual/en/function.exec.php
 * @param string $command 
 * @param array $output [optional] 
 * @param int $result_code [optional] 
 * @return string|false The last line from the result of the command. If you need to execute a 
 * command and have all the data from the command passed directly back without 
 * any interference, use the passthru function.
 * <p>Returns false on failure.</p>
 * <p>To get the output of the executed command, be sure to set and use the
 * output parameter.</p>
 */
function exec (string $command, array &$output = null, int &$result_code = null): string|false {}

/**
 * Execute an external program and display the output
 * @link http://www.php.net/manual/en/function.system.php
 * @param string $command 
 * @param int $result_code [optional] 
 * @return string|false Returns the last line of the command output on success, and false
 * on failure.
 */
function system (string $command, int &$result_code = null): string|false {}

/**
 * Execute an external program and display raw output
 * @link http://www.php.net/manual/en/function.passthru.php
 * @param string $command 
 * @param int $result_code [optional] 
 * @return false|null Returns null on success or false on failure.
 */
function passthru (string $command, int &$result_code = null): ?false {}

/**
 * Escape shell metacharacters
 * @link http://www.php.net/manual/en/function.escapeshellcmd.php
 * @param string $command 
 * @return string The escaped string.
 */
function escapeshellcmd (string $command): string {}

/**
 * Escape a string to be used as a shell argument
 * @link http://www.php.net/manual/en/function.escapeshellarg.php
 * @param string $arg 
 * @return string The escaped string.
 */
function escapeshellarg (string $arg): string {}

/**
 * Execute command via shell and return the complete output as a string
 * @link http://www.php.net/manual/en/function.shell-exec.php
 * @param string $command 
 * @return string|false|null A string containing the output from the executed command, false if the pipe
 * cannot be established or null if an error occurs or the command produces no output.
 * <p>This function can return null both when an error occurs or the program
 * produces no output. It is not possible to detect execution failures using
 * this function. exec should be used when access to the
 * program exit code is required.</p>
 */
function shell_exec (string $command): string|false|null {}

/**
 * Change the priority of the current process
 * @link http://www.php.net/manual/en/function.proc-nice.php
 * @param int $priority 
 * @return bool Returns true on success or false on failure.
 * If an error occurs, like the user lacks permission to change the priority, 
 * an error of level E_WARNING is also generated.
 */
function proc_nice (int $priority): bool {}

/**
 * Portable advisory file locking
 * @link http://www.php.net/manual/en/function.flock.php
 * @param resource $stream 
 * @param int $operation 
 * @param int $would_block [optional] 
 * @return bool Returns true on success or false on failure.
 */
function flock ($stream, int $operation, int &$would_block = null): bool {}

/**
 * Extracts all meta tag content attributes from a file and returns an array
 * @link http://www.php.net/manual/en/function.get-meta-tags.php
 * @param string $filename 
 * @param bool $use_include_path [optional] 
 * @return array|false Returns an array with all the parsed meta tags.
 * <p>The value of the name property becomes the key, the value of the content
 * property becomes the value of the returned array, so you can easily use
 * standard array functions to traverse it or access single values. 
 * Special characters in the value of the name property are substituted with
 * '_', the rest is converted to lower case. If two meta tags have the same
 * name, only the last one is returned.</p>
 * <p>Returns false on failure.</p>
 */
function get_meta_tags (string $filename, bool $use_include_path = false): array|false {}

/**
 * Closes process file pointer
 * @link http://www.php.net/manual/en/function.pclose.php
 * @param resource $handle 
 * @return int Returns the termination status of the process that was run. In case of 
 * an error then -1 is returned.
 * <p>If PHP has been compiled with --enable-sigchild, the return value of this function is undefined.</p>
 */
function pclose ($handle): int {}

/**
 * Opens process file pointer
 * @link http://www.php.net/manual/en/function.popen.php
 * @param string $command 
 * @param string $mode 
 * @return resource|false Returns a file pointer identical to that returned by
 * fopen, except that it is unidirectional (may
 * only be used for reading or writing) and must be closed with
 * pclose. This pointer may be used with
 * fgets, fgetss, and
 * fwrite. When the mode is 'r', the returned
 * file pointer equals to the STDOUT of the command, when the mode
 * is 'w', the returned file pointer equals to the STDIN of the
 * command.
 * <p>If an error occurs, returns false.</p>
 */
function popen (string $command, string $mode) {}

/**
 * Outputs a file
 * @link http://www.php.net/manual/en/function.readfile.php
 * @param string $filename 
 * @param bool $use_include_path [optional] 
 * @param resource|null $context [optional] 
 * @return int|false Returns the number of bytes read from the file on success,
 * or false on failure
 */
function readfile (string $filename, bool $use_include_path = false, $context = null): int|false {}

/**
 * Rewind the position of a file pointer
 * @link http://www.php.net/manual/en/function.rewind.php
 * @param resource $stream 
 * @return bool Returns true on success or false on failure.
 */
function rewind ($stream): bool {}

/**
 * Removes directory
 * @link http://www.php.net/manual/en/function.rmdir.php
 * @param string $directory 
 * @param resource|null $context [optional] 
 * @return bool Returns true on success or false on failure.
 */
function rmdir (string $directory, $context = null): bool {}

/**
 * Changes the current umask
 * @link http://www.php.net/manual/en/function.umask.php
 * @param int|null $mask [optional] 
 * @return int If mask is null, umask 
 * simply returns the current umask otherwise the old umask is returned.
 */
function umask (?int $mask = null): int {}

/**
 * Closes an open file pointer
 * @link http://www.php.net/manual/en/function.fclose.php
 * @param resource $stream 
 * @return bool Returns true on success or false on failure.
 */
function fclose ($stream): bool {}

/**
 * Tests for end-of-file on a file pointer
 * @link http://www.php.net/manual/en/function.feof.php
 * @param resource $stream 
 * @return bool Returns true if the file pointer is at EOF or an error occurs
 * (including socket timeout); otherwise returns false.
 */
function feof ($stream): bool {}

/**
 * Gets character from file pointer
 * @link http://www.php.net/manual/en/function.fgetc.php
 * @param resource $stream 
 * @return string|false Returns a string containing a single character read from the file pointed
 * to by stream. Returns false on EOF.
 */
function fgetc ($stream): string|false {}

/**
 * Gets line from file pointer
 * @link http://www.php.net/manual/en/function.fgets.php
 * @param resource $stream 
 * @param int|null $length [optional] 
 * @return string|false Returns a string of up to length - 1 bytes read from
 * the file pointed to by stream. If there is no more data 
 * to read in the file pointer, then false is returned.
 * <p>If an error occurs, false is returned.</p>
 */
function fgets ($stream, ?int $length = null): string|false {}

/**
 * Binary-safe file read
 * @link http://www.php.net/manual/en/function.fread.php
 * @param resource $stream 
 * @param int $length 
 * @return string|false Returns the read string or false on failure.
 */
function fread ($stream, int $length): string|false {}

/**
 * Opens file or URL
 * @link http://www.php.net/manual/en/function.fopen.php
 * @param string $filename 
 * @param string $mode 
 * @param bool $use_include_path [optional] 
 * @param resource|null $context [optional] 
 * @return resource|false Returns a file pointer resource on success,
 * or false on failure
 */
function fopen (string $filename, string $mode, bool $use_include_path = false, $context = null) {}

/**
 * Parses input from a file according to a format
 * @link http://www.php.net/manual/en/function.fscanf.php
 * @param resource $stream 
 * @param string $format 
 * @param mixed $vars 
 * @return array|int|false|null If only two parameters were passed to this function, the values parsed will be
 * returned as an array. Otherwise, if optional parameters are passed, the
 * function will return the number of assigned values. The optional
 * parameters must be passed by reference.
 * <p>If there are more substrings expected in the format
 * than there are available within string,
 * null will be returned. On other errors, false will be returned.</p>
 */
function fscanf ($stream, string $format, mixed &...$vars): array|int|false|null {}

/**
 * Output all remaining data on a file pointer
 * @link http://www.php.net/manual/en/function.fpassthru.php
 * @param resource $stream 
 * @return int Returns the number of characters read from stream
 * and passed through to the output.
 */
function fpassthru ($stream): int {}

/**
 * Truncates a file to a given length
 * @link http://www.php.net/manual/en/function.ftruncate.php
 * @param resource $stream 
 * @param int $size 
 * @return bool Returns true on success or false on failure.
 */
function ftruncate ($stream, int $size): bool {}

/**
 * Gets information about a file using an open file pointer
 * @link http://www.php.net/manual/en/function.fstat.php
 * @param resource $stream 
 * @return array|false Returns an array with the statistics of the file; the format of the array
 * is described in detail on the stat manual page.
 * Returns false on failure.
 */
function fstat ($stream): array|false {}

/**
 * Seeks on a file pointer
 * @link http://www.php.net/manual/en/function.fseek.php
 * @param resource $stream 
 * @param int $offset 
 * @param int $whence [optional] 
 * @return int Upon success, returns 0; otherwise, returns -1.
 */
function fseek ($stream, int $offset, int $whence = SEEK_SET): int {}

/**
 * Returns the current position of the file read/write pointer
 * @link http://www.php.net/manual/en/function.ftell.php
 * @param resource $stream 
 * @return int|false Returns the position of the file pointer referenced by
 * stream as an integer; i.e., its offset into the file stream.
 * <p>If an error occurs, returns false.</p>
 */
function ftell ($stream): int|false {}

/**
 * Flushes the output to a file
 * @link http://www.php.net/manual/en/function.fflush.php
 * @param resource $stream 
 * @return bool Returns true on success or false on failure.
 */
function fflush ($stream): bool {}

/**
 * Synchronizes changes to the file (including meta-data)
 * @link http://www.php.net/manual/en/function.fsync.php
 * @param resource $stream 
 * @return bool Returns true on success or false on failure.
 */
function fsync ($stream): bool {}

/**
 * Synchronizes data (but not meta-data) to the file
 * @link http://www.php.net/manual/en/function.fdatasync.php
 * @param resource $stream 
 * @return bool Returns true on success or false on failure.
 */
function fdatasync ($stream): bool {}

/**
 * Binary-safe file write
 * @link http://www.php.net/manual/en/function.fwrite.php
 * @param resource $stream 
 * @param string $data 
 * @param int|null $length [optional] 
 * @return int|false fwrite returns the number of bytes
 * written, or false on failure.
 */
function fwrite ($stream, string $data, ?int $length = null): int|false {}

/**
 * Alias of fwrite
 * @link http://www.php.net/manual/en/function.fputs.php
 * @param resource $stream 
 * @param string $data 
 * @param int|null $length [optional] 
 * @return int|false fwrite returns the number of bytes
 * written, or false on failure.
 */
function fputs ($stream, string $data, ?int $length = null): int|false {}

/**
 * Makes directory
 * @link http://www.php.net/manual/en/function.mkdir.php
 * @param string $directory 
 * @param int $permissions [optional] 
 * @param bool $recursive [optional] 
 * @param resource|null $context [optional] 
 * @return bool Returns true on success or false on failure.
 * <p>If the directory to be created already exists, that is considered an error
 * and false will still be returned. Use is_dir or
 * file_exists to check if the directory already exists
 * before trying to create it.</p>
 */
function mkdir (string $directory, int $permissions = 0777, bool $recursive = false, $context = null): bool {}

/**
 * Renames a file or directory
 * @link http://www.php.net/manual/en/function.rename.php
 * @param string $from 
 * @param string $to 
 * @param resource|null $context [optional] 
 * @return bool Returns true on success or false on failure.
 */
function rename (string $from, string $to, $context = null): bool {}

/**
 * Copies file
 * @link http://www.php.net/manual/en/function.copy.php
 * @param string $from 
 * @param string $to 
 * @param resource|null $context [optional] 
 * @return bool Returns true on success or false on failure.
 */
function copy (string $from, string $to, $context = null): bool {}

/**
 * Create file with unique file name
 * @link http://www.php.net/manual/en/function.tempnam.php
 * @param string $directory 
 * @param string $prefix 
 * @return string|false Returns the new temporary filename (with path), or false on
 * failure.
 */
function tempnam (string $directory, string $prefix): string|false {}

/**
 * Creates a temporary file
 * @link http://www.php.net/manual/en/function.tmpfile.php
 * @return resource|false Returns a file handle, similar to the one returned by
 * fopen, for the new file or false on failure.
 */
function tmpfile () {}

/**
 * Reads entire file into an array
 * @link http://www.php.net/manual/en/function.file.php
 * @param string $filename 
 * @param int $flags [optional] 
 * @param resource|null $context [optional] 
 * @return array|false Returns the file in an array. Each element of the array corresponds to a
 * line in the file, with the newline still attached. Upon failure,
 * file returns false.
 * <p>Each line in the resulting array will include the line ending, unless
 * FILE_IGNORE_NEW_LINES is used.</p>
 */
function file (string $filename, int $flags = null, $context = null): array|false {}

/**
 * Reads entire file into a string
 * @link http://www.php.net/manual/en/function.file-get-contents.php
 * @param string $filename 
 * @param bool $use_include_path [optional] 
 * @param resource|null $context [optional] 
 * @param int $offset [optional] 
 * @param int|null $length [optional] 
 * @return string|false The function returns the read data or false on failure.
 */
function file_get_contents (string $filename, bool $use_include_path = false, $context = null, int $offset = null, ?int $length = null): string|false {}

/**
 * Deletes a file
 * @link http://www.php.net/manual/en/function.unlink.php
 * @param string $filename 
 * @param resource|null $context [optional] 
 * @return bool Returns true on success or false on failure.
 */
function unlink (string $filename, $context = null): bool {}

/**
 * Write data to a file
 * @link http://www.php.net/manual/en/function.file-put-contents.php
 * @param string $filename 
 * @param mixed $data 
 * @param int $flags [optional] 
 * @param resource|null $context [optional] 
 * @return int|false This function returns the number of bytes that were written to the file, or
 * false on failure.
 */
function file_put_contents (string $filename, mixed $data, int $flags = null, $context = null): int|false {}

/**
 * Format line as CSV and write to file pointer
 * @link http://www.php.net/manual/en/function.fputcsv.php
 * @param resource $stream 
 * @param array $fields 
 * @param string $separator [optional] 
 * @param string $enclosure [optional] 
 * @param string $escape [optional] 
 * @param string $eol [optional] 
 * @return int|false Returns the length of the written string or false on failure.
 */
function fputcsv ($stream, array $fields, string $separator = '","', string $enclosure = '"\\""', string $escape = '"\\\\"', string $eol = '"\\n"'): int|false {}

/**
 * Gets line from file pointer and parse for CSV fields
 * @link http://www.php.net/manual/en/function.fgetcsv.php
 * @param resource $stream 
 * @param int|null $length [optional] 
 * @param string $separator [optional] 
 * @param string $enclosure [optional] 
 * @param string $escape [optional] 
 * @return array|false Returns an indexed array containing the fields read on success, or false on failure.
 * <p>A blank line in a CSV file will be returned as an array
 * comprising a single null field, and will not be treated
 * as an error.</p>
 */
function fgetcsv ($stream, ?int $length = null, string $separator = '","', string $enclosure = '"\\""', string $escape = '"\\\\"'): array|false {}

/**
 * Returns canonicalized absolute pathname
 * @link http://www.php.net/manual/en/function.realpath.php
 * @param string $path 
 * @return string|false Returns the canonicalized absolute pathname on success. The resulting path 
 * will have no symbolic link, /./ or /../ components. Trailing delimiters,
 * such as \ and /, are also removed.
 * <p>realpath returns false on failure, e.g. if
 * the file does not exist.</p>
 * <p>The running script must have executable permissions on all directories in
 * the hierarchy, otherwise realpath will return
 * false.</p>
 * <p>For case-insensitive filesystems realpath may or may
 * not normalize the character case.</p>
 * <p>The function realpath will not work for a file which
 * is inside a Phar as such path would be a virtual path, not a real one.</p>
 * <p>On Windows, junctions and symbolic links to directories are only expanded by
 * one level.</p>
 */
function realpath (string $path): string|false {}

/**
 * Match filename against a pattern
 * @link http://www.php.net/manual/en/function.fnmatch.php
 * @param string $pattern 
 * @param string $filename 
 * @param int $flags [optional] 
 * @return bool Returns true if there is a match, false otherwise.
 */
function fnmatch (string $pattern, string $filename, int $flags = null): bool {}

/**
 * Returns directory path used for temporary files
 * @link http://www.php.net/manual/en/function.sys-get-temp-dir.php
 * @return string Returns the path of the temporary directory.
 */
function sys_get_temp_dir (): string {}

/**
 * Gets last access time of file
 * @link http://www.php.net/manual/en/function.fileatime.php
 * @param string $filename 
 * @return int|false Returns the time the file was last accessed, or false on failure.
 * The time is returned as a Unix timestamp.
 */
function fileatime (string $filename): int|false {}

/**
 * Gets inode change time of file
 * @link http://www.php.net/manual/en/function.filectime.php
 * @param string $filename 
 * @return int|false Returns the time the file was last changed, or false on failure.
 * The time is returned as a Unix timestamp.
 */
function filectime (string $filename): int|false {}

/**
 * Gets file group
 * @link http://www.php.net/manual/en/function.filegroup.php
 * @param string $filename 
 * @return int|false Returns the group ID of the file, or false if
 * an error occurs. The group ID is returned in numerical format, use
 * posix_getgrgid to resolve it to a group name.
 * Upon failure, false is returned.
 */
function filegroup (string $filename): int|false {}

/**
 * Gets file inode
 * @link http://www.php.net/manual/en/function.fileinode.php
 * @param string $filename 
 * @return int|false Returns the inode number of the file, or false on failure.
 */
function fileinode (string $filename): int|false {}

/**
 * Gets file modification time
 * @link http://www.php.net/manual/en/function.filemtime.php
 * @param string $filename 
 * @return int|false Returns the time the file was last modified, or false on failure.
 * The time is returned as a Unix timestamp, which is
 * suitable for the date function.
 */
function filemtime (string $filename): int|false {}

/**
 * Gets file owner
 * @link http://www.php.net/manual/en/function.fileowner.php
 * @param string $filename 
 * @return int|false Returns the user ID of the owner of the file, or false on failure.
 * The user ID is returned in numerical format, use
 * posix_getpwuid to resolve it to a username.
 */
function fileowner (string $filename): int|false {}

/**
 * Gets file permissions
 * @link http://www.php.net/manual/en/function.fileperms.php
 * @param string $filename 
 * @return int|false Returns the file's permissions as a numeric mode. Lower bits of this mode
 * are the same as the permissions expected by chmod,
 * however on most platforms the return value will also include information on
 * the type of file given as filename. The examples
 * below demonstrate how to test the return value for specific permissions and
 * file types on POSIX systems, including Linux and macOS.
 * <p>For local files, the specific return value is that of the
 * st_mode member of the structure returned by the C
 * library's stat function. Exactly which bits are set
 * can vary from platform to platform, and looking up your specific platform's
 * documentation is recommended if parsing the non-permission bits of the
 * return value is required.</p>
 * <p>Returns false on failure.</p>
 */
function fileperms (string $filename): int|false {}

/**
 * Gets file size
 * @link http://www.php.net/manual/en/function.filesize.php
 * @param string $filename 
 * @return int|false Returns the size of the file in bytes, or false (and generates an error
 * of level E_WARNING) in case of an error.
 */
function filesize (string $filename): int|false {}

/**
 * Gets file type
 * @link http://www.php.net/manual/en/function.filetype.php
 * @param string $filename 
 * @return string|false Returns the type of the file. Possible values are fifo, char,
 * dir, block, link, file, socket and unknown.
 * <p>Returns false if an error occurs. filetype will also
 * produce an E_NOTICE message if the stat call fails
 * or if the file type is unknown.</p>
 */
function filetype (string $filename): string|false {}

/**
 * Checks whether a file or directory exists
 * @link http://www.php.net/manual/en/function.file-exists.php
 * @param string $filename 
 * @return bool Returns true if the file or directory specified by
 * filename exists; false otherwise.
 * <p>This function will return false for symlinks pointing to non-existing
 * files.</p>
 * <p>The check is done using the real UID/GID instead of the effective one.</p>
 */
function file_exists (string $filename): bool {}

/**
 * Tells whether the filename is writable
 * @link http://www.php.net/manual/en/function.is-writable.php
 * @param string $filename 
 * @return bool Returns true if the filename exists and is
 * writable.
 */
function is_writable (string $filename): bool {}

/**
 * Alias of is_writable
 * @link http://www.php.net/manual/en/function.is-writeable.php
 * @param string $filename 
 * @return bool Returns true if the filename exists and is
 * writable.
 */
function is_writeable (string $filename): bool {}

/**
 * Tells whether a file exists and is readable
 * @link http://www.php.net/manual/en/function.is-readable.php
 * @param string $filename 
 * @return bool Returns true if the file or directory specified by
 * filename exists and is readable, false otherwise.
 */
function is_readable (string $filename): bool {}

/**
 * Tells whether the filename is executable
 * @link http://www.php.net/manual/en/function.is-executable.php
 * @param string $filename 
 * @return bool Returns true if the filename exists and is executable, or false on
 * error. On POSIX systems, a file is executable if the executable bit of the
 * file permissions is set. For Windows, see the note below.
 */
function is_executable (string $filename): bool {}

/**
 * Tells whether the filename is a regular file
 * @link http://www.php.net/manual/en/function.is-file.php
 * @param string $filename 
 * @return bool Returns true if the filename exists and is a regular file, false
 * otherwise.
 */
function is_file (string $filename): bool {}

/**
 * Tells whether the filename is a directory
 * @link http://www.php.net/manual/en/function.is-dir.php
 * @param string $filename 
 * @return bool Returns true if the filename exists and is a directory, false
 * otherwise.
 */
function is_dir (string $filename): bool {}

/**
 * Tells whether the filename is a symbolic link
 * @link http://www.php.net/manual/en/function.is-link.php
 * @param string $filename 
 * @return bool Returns true if the filename exists and is a symbolic link, false
 * otherwise.
 */
function is_link (string $filename): bool {}

/**
 * Gives information about a file
 * @link http://www.php.net/manual/en/function.stat.php
 * @param string $filename 
 * @return array|false <table>
 * stat and fstat result
 * format
 * <table>
 * <tr valign="top">
 * <td>Numeric</td>
 * <td>Associative</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>0</td>
 * <td>dev</td>
 * <td>device number &#42;&#42;&#42;</td>
 * </tr>
 * <tr valign="top">
 * <td>1</td>
 * <td>ino</td>
 * <td>inode number &#42;&#42;&#42;&#42;</td>
 * </tr>
 * <tr valign="top">
 * <td>2</td>
 * <td>mode</td>
 * <td>inode protection mode &#42;&#42;&#42;&#42;&#42;</td>
 * </tr>
 * <tr valign="top">
 * <td>3</td>
 * <td>nlink</td>
 * <td>number of links</td>
 * </tr>
 * <tr valign="top">
 * <td>4</td>
 * <td>uid</td>
 * <td>userid of owner &#42;</td>
 * </tr>
 * <tr valign="top">
 * <td>5</td>
 * <td>gid</td>
 * <td>groupid of owner &#42;</td>
 * </tr>
 * <tr valign="top">
 * <td>6</td>
 * <td>rdev</td>
 * <td>device type, if inode device</td>
 * </tr>
 * <tr valign="top">
 * <td>7</td>
 * <td>size</td>
 * <td>size in bytes</td>
 * </tr>
 * <tr valign="top">
 * <td>8</td>
 * <td>atime</td>
 * <td>time of last access (Unix timestamp)</td>
 * </tr>
 * <tr valign="top">
 * <td>9</td>
 * <td>mtime</td>
 * <td>time of last modification (Unix timestamp)</td>
 * </tr>
 * <tr valign="top">
 * <td>10</td>
 * <td>ctime</td>
 * <td>time of last inode change (Unix timestamp)</td>
 * </tr>
 * <tr valign="top">
 * <td>11</td>
 * <td>blksize</td>
 * <td>blocksize of filesystem IO &#42;&#42;</td>
 * </tr>
 * <tr valign="top">
 * <td>12</td>
 * <td>blocks</td>
 * <td>number of 512-byte blocks allocated &#42;&#42;</td>
 * </tr>
 * </table>
 * </table>
 * <p>&#42; On Windows this will always be 0.</p>
 * <p>&#42;&#42; Only valid on systems supporting the st_blksize type - other
 * systems (e.g. Windows) return -1.</p>
 * <p>&#42;&#42;&#42; On Windows, as of PHP 7.4.0, this is the serial number of the volume that contains the file,
 * which is a 64-bit unsigned integer, so may overflow.
 * Previously, it was the numeric representation of the drive letter (e.g. 2
 * for C:) for stat, and 0 for
 * lstat.</p>
 * <p>&#42;&#42;&#42;&#42; On Windows, as of PHP 7.4.0, this is the identifier associated with the file,
 * which is a 64-bit unsigned integer, so may overflow.
 * Previously, it was always 0.</p>
 * <p>&#42;&#42;&#42;&#42;&#42; On Windows, the writable permission bit is set according to the read-only
 * file attribute, and the same value is reported for all users, group and owner.
 * The ACL is not taken into account, contrary to is_writable.</p>
 * <p>The value of mode contains information read by several functions. 
 * When written in octal, starting from the right, the first three digits are returned by
 * chmod. The next digit is ignored by PHP. The next two digits indicate
 * the file type:
 * <table>
 * mode file types
 * <table>
 * <tr valign="top">
 * <td>mode in octal</td>
 * <td>Meaning</td>
 * </tr>
 * <tr valign="top">
 * <td>0140000</td>
 * <td>socket</td>
 * </tr>
 * <tr valign="top">
 * <td>0120000</td>
 * <td>link</td>
 * </tr>
 * <tr valign="top">
 * <td>0100000</td>
 * <td>regular file</td>
 * </tr>
 * <tr valign="top">
 * <td>0060000</td>
 * <td>block device</td>
 * </tr>
 * <tr valign="top">
 * <td>0040000</td>
 * <td>directory</td>
 * </tr>
 * <tr valign="top">
 * <td>0020000</td>
 * <td>character device</td>
 * </tr>
 * <tr valign="top">
 * <td>0010000</td>
 * <td>fifo</td>
 * </tr>
 * </table>
 * </table>
 * So for example a regular file could be 0100644 and a directory could be
 * 0040755.</p>
 * <p>In case of error, stat returns false.</p>
 */
function stat (string $filename): array|false {}

/**
 * Gives information about a file or symbolic link
 * @link http://www.php.net/manual/en/function.lstat.php
 * @param string $filename 
 * @return array|false See the manual page for stat for information on
 * the structure of the array that lstat returns.
 * This function is identical to the stat function
 * except that if the filename parameter is a symbolic
 * link, the status of the symbolic link is returned, not the status of the
 * file pointed to by the symbolic link.
 * <p>On failure, false is returned.</p>
 */
function lstat (string $filename): array|false {}

/**
 * Changes file owner
 * @link http://www.php.net/manual/en/function.chown.php
 * @param string $filename 
 * @param string|int $user 
 * @return bool Returns true on success or false on failure.
 */
function chown (string $filename, string|int $user): bool {}

/**
 * Changes file group
 * @link http://www.php.net/manual/en/function.chgrp.php
 * @param string $filename 
 * @param string|int $group 
 * @return bool Returns true on success or false on failure.
 */
function chgrp (string $filename, string|int $group): bool {}

/**
 * Changes user ownership of symlink
 * @link http://www.php.net/manual/en/function.lchown.php
 * @param string $filename 
 * @param string|int $user 
 * @return bool Returns true on success or false on failure.
 */
function lchown (string $filename, string|int $user): bool {}

/**
 * Changes group ownership of symlink
 * @link http://www.php.net/manual/en/function.lchgrp.php
 * @param string $filename 
 * @param string|int $group 
 * @return bool Returns true on success or false on failure.
 */
function lchgrp (string $filename, string|int $group): bool {}

/**
 * Changes file mode
 * @link http://www.php.net/manual/en/function.chmod.php
 * @param string $filename 
 * @param int $permissions 
 * @return bool Returns true on success or false on failure.
 */
function chmod (string $filename, int $permissions): bool {}

/**
 * Sets access and modification time of file
 * @link http://www.php.net/manual/en/function.touch.php
 * @param string $filename 
 * @param int|null $mtime [optional] 
 * @param int|null $atime [optional] 
 * @return bool Returns true on success or false on failure.
 */
function touch (string $filename, ?int $mtime = null, ?int $atime = null): bool {}

/**
 * Clears file status cache
 * @link http://www.php.net/manual/en/function.clearstatcache.php
 * @param bool $clear_realpath_cache [optional] 
 * @param string $filename [optional] 
 * @return void No value is returned.
 */
function clearstatcache (bool $clear_realpath_cache = false, string $filename = '""'): void {}

/**
 * Returns the total size of a filesystem or disk partition
 * @link http://www.php.net/manual/en/function.disk-total-space.php
 * @param string $directory 
 * @return float|false Returns the total number of bytes as a float
 * or false on failure.
 */
function disk_total_space (string $directory): float|false {}

/**
 * Returns available space on filesystem or disk partition
 * @link http://www.php.net/manual/en/function.disk-free-space.php
 * @param string $directory 
 * @return float|false Returns the number of available bytes as a float
 * or false on failure.
 */
function disk_free_space (string $directory): float|false {}

/**
 * Alias of disk_free_space
 * @link http://www.php.net/manual/en/function.diskfreespace.php
 * @param string $directory 
 * @return float|false Returns the number of available bytes as a float
 * or false on failure.
 */
function diskfreespace (string $directory): float|false {}

/**
 * Get realpath cache entries
 * @link http://www.php.net/manual/en/function.realpath-cache-get.php
 * @return array Returns an array of realpath cache entries. The keys are original path
 * entries, and the values are arrays of data items, containing the resolved
 * path, expiration date, and other options kept in the cache.
 */
function realpath_cache_get (): array {}

/**
 * Get realpath cache size
 * @link http://www.php.net/manual/en/function.realpath-cache-size.php
 * @return int Returns how much memory realpath cache is using.
 */
function realpath_cache_size (): int {}

/**
 * Return a formatted string
 * @link http://www.php.net/manual/en/function.sprintf.php
 * @param string $format 
 * @param mixed $values 
 * @return string Returns a string produced according to the formatting string
 * format.
 */
function sprintf (string $format, mixed ...$values): string {}

/**
 * Output a formatted string
 * @link http://www.php.net/manual/en/function.printf.php
 * @param string $format 
 * @param mixed $values 
 * @return int Returns the length of the outputted string.
 */
function printf (string $format, mixed ...$values): int {}

/**
 * Output a formatted string
 * @link http://www.php.net/manual/en/function.vprintf.php
 * @param string $format 
 * @param array $values 
 * @return int Returns the length of the outputted string.
 */
function vprintf (string $format, array $values): int {}

/**
 * Return a formatted string
 * @link http://www.php.net/manual/en/function.vsprintf.php
 * @param string $format 
 * @param array $values 
 * @return string Return array values as a formatted string according to
 * format.
 */
function vsprintf (string $format, array $values): string {}

/**
 * Write a formatted string to a stream
 * @link http://www.php.net/manual/en/function.fprintf.php
 * @param resource $stream 
 * @param string $format 
 * @param mixed $values 
 * @return int Returns the length of the string written.
 */
function fprintf ($stream, string $format, mixed ...$values): int {}

/**
 * Write a formatted string to a stream
 * @link http://www.php.net/manual/en/function.vfprintf.php
 * @param resource $stream 
 * @param string $format 
 * @param array $values 
 * @return int Returns the length of the outputted string.
 */
function vfprintf ($stream, string $format, array $values): int {}

/**
 * Open Internet or Unix domain socket connection
 * @link http://www.php.net/manual/en/function.fsockopen.php
 * @param string $hostname 
 * @param int $port [optional] 
 * @param int $error_code [optional] 
 * @param string $error_message [optional] 
 * @param float|null $timeout [optional] 
 * @return resource|false fsockopen returns a file pointer which may be used
 * together with the other file functions (such as
 * fgets, fgetss,
 * fwrite, fclose, and
 * feof). If the call fails, it will return false
 */
function fsockopen (string $hostname, int $port = -1, int &$error_code = null, string &$error_message = null, ?float $timeout = null) {}

/**
 * Open persistent Internet or Unix domain socket connection
 * @link http://www.php.net/manual/en/function.pfsockopen.php
 * @param string $hostname 
 * @param int $port [optional] 
 * @param int $error_code [optional] 
 * @param string $error_message [optional] 
 * @param float|null $timeout [optional] 
 * @return resource|false pfsockopen returns a file pointer which may be used
 * together with the other file functions (such as
 * fgets, fgetss,
 * fwrite, fclose, and
 * feof), or false on failure.
 */
function pfsockopen (string $hostname, int $port = -1, int &$error_code = null, string &$error_message = null, ?float $timeout = null) {}

/**
 * Generate URL-encoded query string
 * @link http://www.php.net/manual/en/function.http-build-query.php
 * @param array|object $data 
 * @param string $numeric_prefix [optional] 
 * @param string|null $arg_separator [optional] 
 * @param int $encoding_type [optional] 
 * @return string Returns a URL-encoded string.
 */
function http_build_query (array|object $data, string $numeric_prefix = '""', ?string $arg_separator = null, int $encoding_type = PHP_QUERY_RFC1738): string {}

/**
 * Get Mime-Type for image-type returned by getimagesize,
 * exif_read_data, exif_thumbnail, exif_imagetype
 * @link http://www.php.net/manual/en/function.image-type-to-mime-type.php
 * @param int $image_type 
 * @return string The returned values are as follows
 * <table>
 * Returned values Constants
 * <table>
 * <tr valign="top">
 * <td>image_type</td>
 * <td>Returned value</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_GIF</td>
 * <td>image/gif</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_JPEG</td>
 * <td>image/jpeg</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_PNG</td>
 * <td>image/png</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_SWF</td>
 * <td>application/x-shockwave-flash</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_PSD</td>
 * <td>image/psd</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_BMP</td>
 * <td>image/bmp</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_TIFF_II (intel byte order)</td>
 * <td>image/tiff</td>
 * </tr>
 * <tr valign="top">
 * <td>
 * IMAGETYPE_TIFF_MM (motorola byte order)
 * </td>
 * <td>image/tiff</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_JPC</td>
 * <td>application/octet-stream</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_JP2</td>
 * <td>image/jp2</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_JPX</td>
 * <td>application/octet-stream</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_JB2</td>
 * <td>application/octet-stream</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_SWC</td>
 * <td>application/x-shockwave-flash</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_IFF</td>
 * <td>image/iff</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_WBMP</td>
 * <td>image/vnd.wap.wbmp</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_XBM</td>
 * <td>image/xbm</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_ICO</td>
 * <td>image/vnd.microsoft.icon</td>
 * </tr>
 * <tr valign="top">
 * <td>IMAGETYPE_WEBP</td>
 * <td>image/webp</td>
 * </tr>
 * </table>
 * </table>
 */
function image_type_to_mime_type (int $image_type): string {}

/**
 * Get file extension for image type
 * @link http://www.php.net/manual/en/function.image-type-to-extension.php
 * @param int $image_type 
 * @param bool $include_dot [optional] 
 * @return string|false A string with the extension corresponding to the given image type, or false on failure.
 */
function image_type_to_extension (int $image_type, bool $include_dot = true): string|false {}

/**
 * Get the size of an image
 * @link http://www.php.net/manual/en/function.getimagesize.php
 * @param string $filename 
 * @param array $image_info [optional] 
 * @return array|false Returns an array with up to 7 elements. Not all image types will include
 * the channels and bits elements.
 * <p>Index 0 and 1 contains respectively the width and the height of the image.</p>
 * <p>Some formats may contain no image or may contain multiple images. In these
 * cases, getimagesize might not be able to properly
 * determine the image size. getimagesize will return
 * zero for width and height in these cases.</p>
 * <p>Index 2 is one of the IMAGETYPE_XXX constants indicating 
 * the type of the image.</p>
 * <p>Index 3 is a text string with the correct 
 * height="yyy" width="xxx" string that can be used
 * directly in an IMG tag.</p>
 * <p>mime is the correspondant MIME type of the image.
 * This information can be used to deliver images with the correct HTTP 
 * Content-type header:
 * getimagesize and MIME types
 * <pre>
 * <code>&lt;?php
 * $size = getimagesize($filename);
 * $fp = fopen($filename, &quot;rb&quot;);
 * if ($size &amp;&amp; $fp) {
 * header(&quot;Content-type: {$size[&&#35;039;mime&&#35;039;]}&quot;);
 * fpassthru($fp);
 * exit;
 * } else {
 * &#47;&#47; error
 * }
 * ?&gt;</code>
 * </pre></p>
 * <p>channels will be 3 for RGB pictures and 4 for CMYK
 * pictures.</p>
 * <p>bits is the number of bits for each color.</p>
 * <p>For some image types, the presence of channels and
 * bits values can be a bit
 * confusing. As an example, GIF always uses 3 channels
 * per pixel, but the number of bits per pixel cannot be calculated for an
 * animated GIF with a global color table.</p>
 * <p>On failure, false is returned.</p>
 */
function getimagesize (string $filename, array &$image_info = null): array|false {}

/**
 * Get the size of an image from a string
 * @link http://www.php.net/manual/en/function.getimagesizefromstring.php
 * @param string $string The image data, as a string.
 * @param array $image_info [optional] See getimagesize.
 * @return array|false See getimagesize.
 */
function getimagesizefromstring (string $string, array &$image_info = null): array|false {}

/**
 * Outputs information about PHP's configuration
 * @link http://www.php.net/manual/en/function.phpinfo.php
 * @param int $flags [optional] 
 * @return true Always returns true.
 */
function phpinfo (int $flags = INFO_ALL): true {}

/**
 * Gets the current PHP version
 * @link http://www.php.net/manual/en/function.phpversion.php
 * @param string|null $extension [optional] 
 * @return string|false Returns the current PHP version as a string.
 * If a string argument is provided for
 * extension parameter, phpversion
 * returns the version of that extension, or false if there is no version
 * information associated or the extension isn't enabled.
 */
function phpversion (?string $extension = null): string|false {}

/**
 * Prints out the credits for PHP
 * @link http://www.php.net/manual/en/function.phpcredits.php
 * @param int $flags [optional] 
 * @return true Always returns true.
 */
function phpcredits (int $flags = CREDITS_ALL): true {}

/**
 * Returns the type of interface between web server and PHP
 * @link http://www.php.net/manual/en/function.php-sapi-name.php
 * @return string|false Returns the interface type, as a lowercase string, or false on failure.
 * <p>Although not exhaustive, the possible return values include 
 * apache, 
 * apache2handler, 
 * cgi (until PHP 5.3), 
 * cgi-fcgi, cli, cli-server,
 * embed, fpm-fcgi,
 * litespeed, 
 * phpdbg.</p>
 */
function php_sapi_name (): string|false {}

/**
 * Returns information about the operating system PHP is running on
 * @link http://www.php.net/manual/en/function.php-uname.php
 * @param string $mode [optional] 
 * @return string Returns the description, as a string.
 */
function php_uname (string $mode = '"a"'): string {}

/**
 * Return a list of .ini files parsed from the additional ini dir
 * @link http://www.php.net/manual/en/function.php-ini-scanned-files.php
 * @return string|false Returns a comma-separated string of .ini files on success. Each comma is
 * followed by a newline. If the configure directive --with-config-file-scan-dir wasn't set and the
 * PHP_INI_SCAN_DIR environment variable isn't set, false
 * is returned. If it was set and the directory was empty, an empty string is
 * returned. If a file is unrecognizable, the file will still make it into
 * the returned string but a PHP error will also result. This PHP error will
 * be seen both at compile time and while using
 * php_ini_scanned_files.
 */
function php_ini_scanned_files (): string|false {}

/**
 * Retrieve a path to the loaded php.ini file
 * @link http://www.php.net/manual/en/function.php-ini-loaded-file.php
 * @return string|false The loaded php.ini path, or false if one is not loaded.
 */
function php_ini_loaded_file (): string|false {}

/**
 * Embeds binary IPTC data into a JPEG image
 * @link http://www.php.net/manual/en/function.iptcembed.php
 * @param string $iptc_data 
 * @param string $filename 
 * @param int $spool [optional] 
 * @return string|bool If spool is less than 2, the JPEG will be returned,
 * or false on failure. Otherwise returns true on success
 * or false on failure.
 */
function iptcembed (string $iptc_data, string $filename, int $spool = null): string|bool {}

/**
 * Parse a binary IPTC block into single tags
 * @link http://www.php.net/manual/en/function.iptcparse.php
 * @param string $iptc_block 
 * @return array|false Returns an array using the tagmarker as an index and the value as the
 * value. It returns false on error or if no IPTC data was found.
 */
function iptcparse (string $iptc_block): array|false {}

/**
 * Calculate Levenshtein distance between two strings
 * @link http://www.php.net/manual/en/function.levenshtein.php
 * @param string $string1 
 * @param string $string2 
 * @param int $insertion_cost [optional] 
 * @param int $replacement_cost [optional] 
 * @param int $deletion_cost [optional] 
 * @return int This function returns the Levenshtein-Distance between the
 * two argument strings.
 */
function levenshtein (string $string1, string $string2, int $insertion_cost = 1, int $replacement_cost = 1, int $deletion_cost = 1): int {}

/**
 * Returns the target of a symbolic link
 * @link http://www.php.net/manual/en/function.readlink.php
 * @param string $path 
 * @return string|false Returns the contents of the symbolic link path or false on error.
 */
function readlink (string $path): string|false {}

/**
 * Gets information about a link
 * @link http://www.php.net/manual/en/function.linkinfo.php
 * @param string $path 
 * @return int|false linkinfo returns the st_dev field
 * of the Unix C stat structure returned by the lstat
 * system call. Returns a non-negative integer on success, -1 in case the link was not found, 
 * or false if an open.base_dir violation occurs.
 */
function linkinfo (string $path): int|false {}

/**
 * Creates a symbolic link
 * @link http://www.php.net/manual/en/function.symlink.php
 * @param string $target 
 * @param string $link 
 * @return bool Returns true on success or false on failure.
 */
function symlink (string $target, string $link): bool {}

/**
 * Create a hard link
 * @link http://www.php.net/manual/en/function.link.php
 * @param string $target 
 * @param string $link 
 * @return bool Returns true on success or false on failure.
 */
function link (string $target, string $link): bool {}

/**
 * Send mail
 * @link http://www.php.net/manual/en/function.mail.php
 * @param string $to 
 * @param string $subject 
 * @param string $message 
 * @param array|string $additional_headers [optional] 
 * @param string $additional_params [optional] 
 * @return bool Returns true if the mail was successfully accepted for delivery, false otherwise.
 * <p>It is important to note that just because the mail was accepted for delivery,
 * it does NOT mean the mail will actually reach the intended destination.</p>
 */
function mail (string $to, string $subject, string $message, array|string $additional_headers = '[]', string $additional_params = '""'): bool {}

/**
 * Absolute value
 * @link http://www.php.net/manual/en/function.abs.php
 * @param int|float $num 
 * @return int|float The absolute value of num. If the
 * argument num is
 * of type float, the return type is also float,
 * otherwise it is int (as float usually has a
 * bigger value range than int).
 */
function abs (int|float $num): int|float {}

/**
 * Round fractions up
 * @link http://www.php.net/manual/en/function.ceil.php
 * @param int|float $num 
 * @return float num rounded up to the next highest
 * integer.
 * The return value of ceil is still of type
 * float as the value range of float is 
 * usually bigger than that of int.
 */
function ceil (int|float $num): float {}

/**
 * Round fractions down
 * @link http://www.php.net/manual/en/function.floor.php
 * @param int|float $num 
 * @return float num rounded to the next lowest integer.
 * The return value of floor is still of type
 * float.
 * This function returns false in case of an error (e.g. passing an array).
 */
function floor (int|float $num): float {}

/**
 * Rounds a float
 * @link http://www.php.net/manual/en/function.round.php
 * @param int|float $num 
 * @param int $precision [optional] 
 * @param int $mode [optional] 
 * @return float The value rounded to the given precision as a float.
 */
function round (int|float $num, int $precision = null, int $mode = PHP_ROUND_HALF_UP): float {}

/**
 * Sine
 * @link http://www.php.net/manual/en/function.sin.php
 * @param float $num 
 * @return float The sine of num
 */
function sin (float $num): float {}

/**
 * Cosine
 * @link http://www.php.net/manual/en/function.cos.php
 * @param float $num 
 * @return float The cosine of num
 */
function cos (float $num): float {}

/**
 * Tangent
 * @link http://www.php.net/manual/en/function.tan.php
 * @param float $num 
 * @return float The tangent of num
 */
function tan (float $num): float {}

/**
 * Arc sine
 * @link http://www.php.net/manual/en/function.asin.php
 * @param float $num 
 * @return float The arc sine of num in radians
 */
function asin (float $num): float {}

/**
 * Arc cosine
 * @link http://www.php.net/manual/en/function.acos.php
 * @param float $num 
 * @return float The arc cosine of num in radians.
 */
function acos (float $num): float {}

/**
 * Arc tangent
 * @link http://www.php.net/manual/en/function.atan.php
 * @param float $num 
 * @return float The arc tangent of num in radians.
 */
function atan (float $num): float {}

/**
 * Inverse hyperbolic tangent
 * @link http://www.php.net/manual/en/function.atanh.php
 * @param float $num 
 * @return float Inverse hyperbolic tangent of num
 */
function atanh (float $num): float {}

/**
 * Arc tangent of two variables
 * @link http://www.php.net/manual/en/function.atan2.php
 * @param float $y 
 * @param float $x 
 * @return float The arc tangent of y/x 
 * in radians.
 */
function atan2 (float $y, float $x): float {}

/**
 * Hyperbolic sine
 * @link http://www.php.net/manual/en/function.sinh.php
 * @param float $num 
 * @return float The hyperbolic sine of num
 */
function sinh (float $num): float {}

/**
 * Hyperbolic cosine
 * @link http://www.php.net/manual/en/function.cosh.php
 * @param float $num 
 * @return float The hyperbolic cosine of num
 */
function cosh (float $num): float {}

/**
 * Hyperbolic tangent
 * @link http://www.php.net/manual/en/function.tanh.php
 * @param float $num 
 * @return float The hyperbolic tangent of num
 */
function tanh (float $num): float {}

/**
 * Inverse hyperbolic sine
 * @link http://www.php.net/manual/en/function.asinh.php
 * @param float $num 
 * @return float The inverse hyperbolic sine of num
 */
function asinh (float $num): float {}

/**
 * Inverse hyperbolic cosine
 * @link http://www.php.net/manual/en/function.acosh.php
 * @param float $num 
 * @return float The inverse hyperbolic cosine of num
 */
function acosh (float $num): float {}

/**
 * Returns exp(number) - 1, computed in a way that is accurate even
 * when the value of number is close to zero
 * @link http://www.php.net/manual/en/function.expm1.php
 * @param float $num 
 * @return float 'e' to the power of num minus one
 */
function expm1 (float $num): float {}

/**
 * Returns log(1 + number), computed in a way that is accurate even when
 * the value of number is close to zero
 * @link http://www.php.net/manual/en/function.log1p.php
 * @param float $num 
 * @return float log(1 + num)
 */
function log1p (float $num): float {}

/**
 * Get value of pi
 * @link http://www.php.net/manual/en/function.pi.php
 * @return float The value of pi as float.
 */
function pi (): float {}

/**
 * Finds whether a value is a legal finite number
 * @link http://www.php.net/manual/en/function.is-finite.php
 * @param float $num 
 * @return bool true if num is a legal finite
 * number within the allowed range for a PHP float on this platform,
 * else false.
 */
function is_finite (float $num): bool {}

/**
 * Finds whether a value is not a number
 * @link http://www.php.net/manual/en/function.is-nan.php
 * @param float $num 
 * @return bool Returns true if num is 'not a number',
 * else false.
 */
function is_nan (float $num): bool {}

/**
 * Integer division
 * @link http://www.php.net/manual/en/function.intdiv.php
 * @param int $num1 Number to be divided.
 * @param int $num2 Number which divides the num1.
 * @return int The integer quotient of the division of num1 by num2.
 */
function intdiv (int $num1, int $num2): int {}

/**
 * Finds whether a value is infinite
 * @link http://www.php.net/manual/en/function.is-infinite.php
 * @param float $num 
 * @return bool true if num is infinite, else false.
 */
function is_infinite (float $num): bool {}

/**
 * Exponential expression
 * @link http://www.php.net/manual/en/function.pow.php
 * @param mixed $num 
 * @param mixed $exponent 
 * @return int|float|object num raised to the power of exponent.
 * If both arguments are non-negative integers and the result can be represented
 * as an integer, the result will be returned with int type,
 * otherwise it will be returned as a float.
 */
function pow (mixed $num, mixed $exponent): int|float|object {}

/**
 * Calculates the exponent of e
 * @link http://www.php.net/manual/en/function.exp.php
 * @param float $num 
 * @return float 'e' raised to the power of num
 */
function exp (float $num): float {}

/**
 * Natural logarithm
 * @link http://www.php.net/manual/en/function.log.php
 * @param float $num 
 * @param float $base [optional] 
 * @return float The logarithm of num to 
 * base, if given, or the
 * natural logarithm.
 */
function log (float $num, float $base = M_E): float {}

/**
 * Base-10 logarithm
 * @link http://www.php.net/manual/en/function.log10.php
 * @param float $num 
 * @return float The base-10 logarithm of num
 */
function log10 (float $num): float {}

/**
 * Square root
 * @link http://www.php.net/manual/en/function.sqrt.php
 * @param float $num 
 * @return float The square root of num
 * or the special value NAN for negative numbers.
 */
function sqrt (float $num): float {}

/**
 * Calculate the length of the hypotenuse of a right-angle triangle
 * @link http://www.php.net/manual/en/function.hypot.php
 * @param float $x 
 * @param float $y 
 * @return float Calculated length of the hypotenuse
 */
function hypot (float $x, float $y): float {}

/**
 * Converts the number in degrees to the radian equivalent
 * @link http://www.php.net/manual/en/function.deg2rad.php
 * @param float $num 
 * @return float The radian equivalent of num
 */
function deg2rad (float $num): float {}

/**
 * Converts the radian number to the equivalent number in degrees
 * @link http://www.php.net/manual/en/function.rad2deg.php
 * @param float $num 
 * @return float The equivalent of num in degrees
 */
function rad2deg (float $num): float {}

/**
 * Binary to decimal
 * @link http://www.php.net/manual/en/function.bindec.php
 * @param string $binary_string 
 * @return int|float The decimal value of binary_string
 */
function bindec (string $binary_string): int|float {}

/**
 * Hexadecimal to decimal
 * @link http://www.php.net/manual/en/function.hexdec.php
 * @param string $hex_string 
 * @return int|float The decimal representation of hex_string
 */
function hexdec (string $hex_string): int|float {}

/**
 * Octal to decimal
 * @link http://www.php.net/manual/en/function.octdec.php
 * @param string $octal_string 
 * @return int|float The decimal representation of octal_string
 */
function octdec (string $octal_string): int|float {}

/**
 * Decimal to binary
 * @link http://www.php.net/manual/en/function.decbin.php
 * @param int $num 
 * @return string Binary string representation of num
 */
function decbin (int $num): string {}

/**
 * Decimal to octal
 * @link http://www.php.net/manual/en/function.decoct.php
 * @param int $num 
 * @return string Octal string representation of num
 */
function decoct (int $num): string {}

/**
 * Decimal to hexadecimal
 * @link http://www.php.net/manual/en/function.dechex.php
 * @param int $num 
 * @return string Hexadecimal string representation of num.
 */
function dechex (int $num): string {}

/**
 * Convert a number between arbitrary bases
 * @link http://www.php.net/manual/en/function.base-convert.php
 * @param string $num 
 * @param int $from_base 
 * @param int $to_base 
 * @return string num converted to base to_base
 */
function base_convert (string $num, int $from_base, int $to_base): string {}

/**
 * Format a number with grouped thousands
 * @link http://www.php.net/manual/en/function.number-format.php
 * @param float $num 
 * @param int $decimals [optional] 
 * @param string|null $decimal_separator [optional] 
 * @param string|null $thousands_separator [optional] 
 * @return string A formatted version of num.
 */
function number_format (float $num, int $decimals = null, ?string $decimal_separator = '"."', ?string $thousands_separator = '","'): string {}

/**
 * Returns the floating point remainder (modulo) of the division
 * of the arguments
 * @link http://www.php.net/manual/en/function.fmod.php
 * @param float $num1 
 * @param float $num2 
 * @return float The floating point remainder of 
 * num1/num2
 */
function fmod (float $num1, float $num2): float {}

/**
 * Divides two numbers, according to IEEE 754
 * @link http://www.php.net/manual/en/function.fdiv.php
 * @param float $num1 
 * @param float $num2 
 * @return float The floating point result of
 * num1/num2
 */
function fdiv (float $num1, float $num2): float {}

/**
 * Return current Unix timestamp with microseconds
 * @link http://www.php.net/manual/en/function.microtime.php
 * @param bool $as_float [optional] 
 * @return string|float By default, microtime returns a string in
 * the form "msec sec", where sec is the number of seconds 
 * since the Unix epoch (0:00:00 January 1,1970 GMT), and msec 
 * measures microseconds that have elapsed since sec 
 * and is also expressed in seconds as a decimal fraction.
 * <p>If as_float is set to true, then
 * microtime returns a float, which
 * represents the current time in seconds since the Unix epoch accurate to the
 * nearest microsecond.</p>
 */
function microtime (bool $as_float = false): string|float {}

/**
 * Get current time
 * @link http://www.php.net/manual/en/function.gettimeofday.php
 * @param bool $as_float [optional] 
 * @return array|float By default an array is returned. If as_float
 * is set, then a float is returned.
 * <p>Array keys:
 * <p>
 * <br>
 * "sec" - seconds since the Unix Epoch
 * <br>
 * "usec" - microseconds
 * <br>
 * "minuteswest" - minutes west of Greenwich
 * <br>
 * "dsttime" - type of dst correction
 * </p></p>
 */
function gettimeofday (bool $as_float = false): array|float {}

/**
 * Gets the current resource usages
 * @link http://www.php.net/manual/en/function.getrusage.php
 * @param int $mode [optional] 
 * @return array|false Returns an associative array containing the data returned from the system
 * call. All entries are accessible by using their documented field names.
 * Returns false on failure.
 */
function getrusage (int $mode = null): array|false {}

/**
 * Pack data into binary string
 * @link http://www.php.net/manual/en/function.pack.php
 * @param string $format 
 * @param mixed $values 
 * @return string Returns a binary string containing data.
 */
function pack (string $format, mixed ...$values): string {}

/**
 * Unpack data from binary string
 * @link http://www.php.net/manual/en/function.unpack.php
 * @param string $format 
 * @param string $string 
 * @param int $offset [optional] 
 * @return array|false Returns an associative array containing unpacked elements of binary
 * string, or false on failure.
 */
function unpack (string $format, string $string, int $offset = null): array|false {}

/**
 * Returns information about the given hash
 * @link http://www.php.net/manual/en/function.password-get-info.php
 * @param string $hash A hash created by password_hash.
 * @return array Returns an associative array with three elements: 
 * <p>
 * <br>
 * algo, which will match a
 * password algorithm constant
 * <br>
 * algoName, which has the human readable name of the
 * algorithm
 * <br>
 * options, which includes the options
 * provided when calling password_hash
 * </p>
 */
function password_get_info (string $hash): array {}

/**
 * Creates a password hash
 * @link http://www.php.net/manual/en/function.password-hash.php
 * @param string $password The user's password.
 * <p>Using the PASSWORD_BCRYPT as the
 * algorithm, will result
 * in the password parameter being truncated to a
 * maximum length of 72 bytes.</p>
 * @param string|int|null $algo A password algorithm constant denoting the algorithm to use when hashing the password.
 * @param array $options [optional] An associative array containing options. See the password algorithm constants for documentation on the supported options for each algorithm.
 * <p>If omitted, a random salt will be created and the default cost will be
 * used.</p>
 * @return string Returns the hashed password.
 * <p>The used algorithm, cost and salt are returned as part of the hash. Therefore,
 * all information that's needed to verify the hash is included in it. This allows
 * the password_verify function to verify the hash without
 * needing separate storage for the salt or algorithm information.</p>
 */
function password_hash (string $password, string|int|null $algo, array $options = '[]'): string {}

/**
 * Checks if the given hash matches the given options
 * @link http://www.php.net/manual/en/function.password-needs-rehash.php
 * @param string $hash A hash created by password_hash.
 * @param string|int|null $algo A password algorithm constant denoting the algorithm to use when hashing the password.
 * @param array $options [optional] An associative array containing options. See the password algorithm constants for documentation on the supported options for each algorithm.
 * @return bool Returns true if the hash should be rehashed to match the given
 * algo and options, or false
 * otherwise.
 */
function password_needs_rehash (string $hash, string|int|null $algo, array $options = '[]'): bool {}

/**
 * Verifies that a password matches a hash
 * @link http://www.php.net/manual/en/function.password-verify.php
 * @param string $password The user's password.
 * @param string $hash A hash created by password_hash.
 * @return bool Returns true if the password and hash match, or false otherwise.
 */
function password_verify (string $password, string $hash): bool {}

/**
 * Get available password hashing algorithm IDs
 * @link http://www.php.net/manual/en/function.password-algos.php
 * @return array Returns the available password hashing algorithm IDs.
 */
function password_algos (): array {}

/**
 * Execute a command and open file pointers for input/output
 * @link http://www.php.net/manual/en/function.proc-open.php
 * @param array|string $command 
 * @param array $descriptor_spec 
 * @param array $pipes 
 * @param string|null $cwd [optional] 
 * @param array|null $env_vars [optional] 
 * @param array|null $options [optional] 
 * @return resource|false Returns a resource representing the process, which should be freed using
 * proc_close when you are finished with it. On failure
 * returns false.
 */
function proc_open (array|string $command, array $descriptor_spec, array &$pipes, ?string $cwd = null, ?array $env_vars = null, ?array $options = null) {}

/**
 * Close a process opened by proc_open and return the exit code of that process
 * @link http://www.php.net/manual/en/function.proc-close.php
 * @param resource $process 
 * @return int Returns the termination status of the process that was run. In case of 
 * an error then -1 is returned.
 * <p>If PHP has been compiled with --enable-sigchild, the return value of this function is undefined.</p>
 */
function proc_close ($process): int {}

/**
 * Kills a process opened by proc_open
 * @link http://www.php.net/manual/en/function.proc-terminate.php
 * @param resource $process 
 * @param int $signal [optional] 
 * @return bool Returns the termination status of the process that was run.
 */
function proc_terminate ($process, int $signal = 15): bool {}

/**
 * Get information about a process opened by proc_open
 * @link http://www.php.net/manual/en/function.proc-get-status.php
 * @param resource $process 
 * @return array An array of collected information.
 * The returned array contains the following elements:
 * <p><table>
 * <tr valign="top"><td>element</td><td>type</td><td>description</td></tr>
 * <tr valign="top">
 * <td>command</td>
 * <td>string</td>
 * <td>
 * The command string that was passed to proc_open.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>pid</td>
 * <td>int</td>
 * <td>process id</td>
 * </tr>
 * <tr valign="top">
 * <td>running</td>
 * <td>bool</td>
 * <td>
 * true if the process is still running, false if it has
 * terminated.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>signaled</td>
 * <td>bool</td>
 * <td>
 * true if the child process has been terminated by
 * an uncaught signal. Always set to false on Windows.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>stopped</td>
 * <td>bool</td>
 * <td>
 * true if the child process has been stopped by a
 * signal. Always set to false on Windows.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>exitcode</td>
 * <td>int</td>
 * <td>
 * The exit code returned by the process (which is only
 * meaningful if running is false).
 * Only first call of this function return real value, next calls return
 * -1.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>termsig</td>
 * <td>int</td>
 * <td>
 * The number of the signal that caused the child process to terminate
 * its execution (only meaningful if signaled is true).
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>stopsig</td>
 * <td>int</td>
 * <td>
 * The number of the signal that caused the child process to stop its
 * execution (only meaningful if stopped is true).
 * </td>
 * </tr>
 * </table></p>
 */
function proc_get_status ($process): array {}

/**
 * Convert a quoted-printable string to an 8 bit string
 * @link http://www.php.net/manual/en/function.quoted-printable-decode.php
 * @param string $string 
 * @return string Returns the 8-bit binary string.
 */
function quoted_printable_decode (string $string): string {}

/**
 * Convert a 8 bit string to a quoted-printable string
 * @link http://www.php.net/manual/en/function.quoted-printable-encode.php
 * @param string $string 
 * @return string Returns the encoded string.
 */
function quoted_printable_encode (string $string): string {}

/**
 * Calculate the soundex key of a string
 * @link http://www.php.net/manual/en/function.soundex.php
 * @param string $string 
 * @return string Returns the soundex key as a string with four characters.
 * If at least one letter is contained in string, the returned
 * string starts with a letter. Otherwise "0000" is returned.
 */
function soundex (string $string): string {}

/**
 * Runs the equivalent of the select() system call on the given
 * arrays of streams with a timeout specified by seconds and microseconds
 * @link http://www.php.net/manual/en/function.stream-select.php
 * @param array|null $read 
 * @param array|null $write 
 * @param array|null $except 
 * @param int|null $seconds 
 * @param int|null $microseconds [optional] 
 * @return int|false On success stream_select returns the number of
 * stream resources contained in the modified arrays, which may be zero if
 * the timeout expires before anything interesting happens. On error false
 * is returned and a warning raised (this can happen if the system call is
 * interrupted by an incoming signal).
 */
function stream_select (?array &$read, ?array &$write, ?array &$except, ?int $seconds, ?int $microseconds = null): int|false {}

/**
 * Creates a stream context
 * @link http://www.php.net/manual/en/function.stream-context-create.php
 * @param array|null $options [optional] 
 * @param array|null $params [optional] 
 * @return resource A stream context resource.
 */
function stream_context_create (?array $options = null, ?array $params = null) {}

/**
 * Set parameters for a stream/wrapper/context
 * @link http://www.php.net/manual/en/function.stream-context-set-params.php
 * @param resource $context 
 * @param array $params 
 * @return bool Returns true on success or false on failure.
 */
function stream_context_set_params ($context, array $params): bool {}

/**
 * Retrieves parameters from a context
 * @link http://www.php.net/manual/en/function.stream-context-get-params.php
 * @param resource $context 
 * @return array Returns an associate array containing all context options and parameters.
 */
function stream_context_get_params ($context): array {}

/**
 * Sets an option for a stream/wrapper/context
 * @link http://www.php.net/manual/en/function.stream-context-set-option.php
 * @param resource $stream_or_context 
 * @param string $wrapper 
 * @param string $option 
 * @param mixed $value 
 * @return bool Returns true on success or false on failure.
 */
function stream_context_set_option ($stream_or_context, string $wrapper, string $option, mixed $value): bool {}

/**
 * Retrieve options for a stream/wrapper/context
 * @link http://www.php.net/manual/en/function.stream-context-get-options.php
 * @param resource $stream_or_context 
 * @return array Returns an associative array with the options.
 */
function stream_context_get_options ($stream_or_context): array {}

/**
 * Retrieve the default stream context
 * @link http://www.php.net/manual/en/function.stream-context-get-default.php
 * @param array|null $options [optional] 
 * @return resource A stream context resource.
 */
function stream_context_get_default (?array $options = null) {}

/**
 * Set the default stream context
 * @link http://www.php.net/manual/en/function.stream-context-set-default.php
 * @param array $options 
 * @return resource Returns the default stream context.
 */
function stream_context_set_default (array $options) {}

/**
 * Attach a filter to a stream
 * @link http://www.php.net/manual/en/function.stream-filter-prepend.php
 * @param resource $stream 
 * @param string $filtername 
 * @param int $read_write [optional] 
 * @param mixed $params [optional] 
 * @return resource Returns a resource on success or false on failure. The resource can be
 * used to refer to this filter instance during a call to
 * stream_filter_remove.
 * <p>false is returned if stream is not a resource or
 * if filtername cannot be located.</p>
 */
function stream_filter_prepend ($stream, string $filtername, int $read_write = null, mixed $params = null) {}

/**
 * Attach a filter to a stream
 * @link http://www.php.net/manual/en/function.stream-filter-append.php
 * @param resource $stream 
 * @param string $filtername 
 * @param int $read_write [optional] 
 * @param mixed $params [optional] 
 * @return resource Returns a resource on success or false on failure. The resource can be
 * used to refer to this filter instance during a call to
 * stream_filter_remove.
 * <p>false is returned if stream is not a resource or
 * if filtername cannot be located.</p>
 */
function stream_filter_append ($stream, string $filtername, int $read_write = null, mixed $params = null) {}

/**
 * Remove a filter from a stream
 * @link http://www.php.net/manual/en/function.stream-filter-remove.php
 * @param resource $stream_filter 
 * @return bool Returns true on success or false on failure.
 */
function stream_filter_remove ($stream_filter): bool {}

/**
 * Open Internet or Unix domain socket connection
 * @link http://www.php.net/manual/en/function.stream-socket-client.php
 * @param string $address 
 * @param int $error_code [optional] 
 * @param string $error_message [optional] 
 * @param float|null $timeout [optional] 
 * @param int $flags [optional] 
 * @param resource|null $context [optional] 
 * @return resource|false On success a stream resource is returned which may
 * be used together with the other file functions (such as
 * fgets, fgetss,
 * fwrite, fclose, and
 * feof), false on failure.
 */
function stream_socket_client (string $address, int &$error_code = null, string &$error_message = null, ?float $timeout = null, int $flags = STREAM_CLIENT_CONNECT, $context = null) {}

/**
 * Create an Internet or Unix domain server socket
 * @link http://www.php.net/manual/en/function.stream-socket-server.php
 * @param string $address 
 * @param int $error_code [optional] 
 * @param string $error_message [optional] 
 * @param int $flags [optional] 
 * @param resource|null $context [optional] 
 * @return resource|false Returns the created stream, or false on error.
 */
function stream_socket_server (string $address, int &$error_code = null, string &$error_message = null, int $flags = 'STREAM_SERVER_BIND | STREAM_SERVER_LISTEN', $context = null) {}

/**
 * Accept a connection on a socket created by stream_socket_server
 * @link http://www.php.net/manual/en/function.stream-socket-accept.php
 * @param resource $socket 
 * @param float|null $timeout [optional] 
 * @param string $peer_name [optional] 
 * @return resource|false Returns a stream to the accepted socket connection or false on failure.
 */
function stream_socket_accept ($socket, ?float $timeout = null, string &$peer_name = null) {}

/**
 * Retrieve the name of the local or remote sockets
 * @link http://www.php.net/manual/en/function.stream-socket-get-name.php
 * @param resource $socket 
 * @param bool $remote 
 * @return string|false The name of the socket, or false on failure.
 */
function stream_socket_get_name ($socket, bool $remote): string|false {}

/**
 * Receives data from a socket, connected or not
 * @link http://www.php.net/manual/en/function.stream-socket-recvfrom.php
 * @param resource $socket 
 * @param int $length 
 * @param int $flags [optional] 
 * @param string|null $address [optional] 
 * @return string|false Returns the read data, as a string, or false on failure.
 */
function stream_socket_recvfrom ($socket, int $length, int $flags = null, ?string &$address = null): string|false {}

/**
 * Sends a message to a socket, whether it is connected or not
 * @link http://www.php.net/manual/en/function.stream-socket-sendto.php
 * @param resource $socket 
 * @param string $data 
 * @param int $flags [optional] 
 * @param string $address [optional] 
 * @return int|false Returns a result code, as an integer, or false on failure.
 */
function stream_socket_sendto ($socket, string $data, int $flags = null, string $address = '""'): int|false {}

/**
 * Turns encryption on/off on an already connected socket
 * @link http://www.php.net/manual/en/function.stream-socket-enable-crypto.php
 * @param resource $stream 
 * @param bool $enable 
 * @param int|null $crypto_method [optional] 
 * @param resource|null $session_stream [optional] 
 * @return int|bool Returns true on success, false if negotiation has failed or
 * 0 if there isn't enough data and you should try again
 * (only for non-blocking sockets).
 */
function stream_socket_enable_crypto ($stream, bool $enable, ?int $crypto_method = null, $session_stream = null): int|bool {}

/**
 * Shutdown a full-duplex connection
 * @link http://www.php.net/manual/en/function.stream-socket-shutdown.php
 * @param resource $stream 
 * @param int $mode 
 * @return bool Returns true on success or false on failure.
 */
function stream_socket_shutdown ($stream, int $mode): bool {}

/**
 * Creates a pair of connected, indistinguishable socket streams
 * @link http://www.php.net/manual/en/function.stream-socket-pair.php
 * @param int $domain 
 * @param int $type 
 * @param int $protocol 
 * @return array|false Returns an array with the two socket resources on success, or
 * false on failure.
 */
function stream_socket_pair (int $domain, int $type, int $protocol): array|false {}

/**
 * Copies data from one stream to another
 * @link http://www.php.net/manual/en/function.stream-copy-to-stream.php
 * @param resource $from 
 * @param resource $to 
 * @param int|null $length [optional] 
 * @param int $offset [optional] 
 * @return int|false Returns the total count of bytes copied, or false on failure.
 */
function stream_copy_to_stream ($from, $to, ?int $length = null, int $offset = null): int|false {}

/**
 * Reads remainder of a stream into a string
 * @link http://www.php.net/manual/en/function.stream-get-contents.php
 * @param resource $stream 
 * @param int|null $length [optional] 
 * @param int $offset [optional] 
 * @return string|false Returns a string or false on failure.
 */
function stream_get_contents ($stream, ?int $length = null, int $offset = -1): string|false {}

/**
 * Tells whether the stream supports locking
 * @link http://www.php.net/manual/en/function.stream-supports-lock.php
 * @param resource $stream 
 * @return bool Returns true on success or false on failure.
 */
function stream_supports_lock ($stream): bool {}

/**
 * Sets write file buffering on the given stream
 * @link http://www.php.net/manual/en/function.stream-set-write-buffer.php
 * @param resource $stream 
 * @param int $size 
 * @return int Returns 0 on success, or another value if the request cannot be honored.
 */
function stream_set_write_buffer ($stream, int $size): int {}

/**
 * Alias of stream_set_write_buffer
 * @link http://www.php.net/manual/en/function.set-file-buffer.php
 * @param resource $stream 
 * @param int $size 
 * @return int Returns 0 on success, or another value if the request cannot be honored.
 */
function set_file_buffer ($stream, int $size): int {}

/**
 * Set read file buffering on the given stream
 * @link http://www.php.net/manual/en/function.stream-set-read-buffer.php
 * @param resource $stream The file pointer.
 * @param int $size The number of bytes to buffer. If size
 * is 0 then read operations are unbuffered. This ensures that all reads
 * with fread are completed before other processes are
 * allowed to read from that input stream.
 * @return int Returns 0 on success, or another value if the request
 * cannot be honored.
 */
function stream_set_read_buffer ($stream, int $size): int {}

/**
 * Set blocking/non-blocking mode on a stream
 * @link http://www.php.net/manual/en/function.stream-set-blocking.php
 * @param resource $stream 
 * @param bool $enable 
 * @return bool Returns true on success or false on failure.
 */
function stream_set_blocking ($stream, bool $enable): bool {}

/**
 * Alias of stream_set_blocking
 * @link http://www.php.net/manual/en/function.socket-set-blocking.php
 * @param resource $stream 
 * @param bool $enable 
 * @return bool Returns true on success or false on failure.
 */
function socket_set_blocking ($stream, bool $enable): bool {}

/**
 * Retrieves header/meta data from streams/file pointers
 * @link http://www.php.net/manual/en/function.stream-get-meta-data.php
 * @param resource $stream 
 * @return array The result array contains the following items:
 * <p>timed_out (bool) - true if the stream
 * timed out while waiting for data on the last call to
 * fread or fgets.</p>
 * <p>blocked (bool) - true if the stream is
 * in blocking IO mode. See stream_set_blocking.</p>
 * <p>eof (bool) - true if the stream has reached
 * end-of-file. Note that for socket streams this member can be true
 * even when unread_bytes is non-zero. To
 * determine if there is more data to be read, use
 * feof instead of reading this item.</p>
 * <p>unread_bytes (int) - the number of bytes
 * currently contained in the PHP's own internal buffer.</p>
 * <p>stream_type (string) - a label describing
 * the underlying implementation of the stream.</p>
 * <p>wrapper_type (string) - a label describing
 * the protocol wrapper implementation layered over the stream.
 * See for more information about wrappers.</p>
 * <p>wrapper_data (mixed) - wrapper specific
 * data attached to this stream. See for
 * more information about wrappers and their wrapper data.</p>
 * <p>mode (string) - the type of access required for
 * this stream (see Table 1 of the fopen() reference)</p>
 * <p>seekable (bool) - whether the current stream can
 * be seeked.</p>
 * <p>uri (string) - the URI/filename associated with this
 * stream.</p>
 * <p>crypto (array) - the TLS connection metadata for this
 * stream. (Note: Only provided when the resource's stream uses TLS.)</p>
 */
function stream_get_meta_data ($stream): array {}

/**
 * Alias of stream_get_meta_data
 * @link http://www.php.net/manual/en/function.socket-get-status.php
 * @param resource $stream 
 * @return array The result array contains the following items:
 * <p>timed_out (bool) - true if the stream
 * timed out while waiting for data on the last call to
 * fread or fgets.</p>
 * <p>blocked (bool) - true if the stream is
 * in blocking IO mode. See stream_set_blocking.</p>
 * <p>eof (bool) - true if the stream has reached
 * end-of-file. Note that for socket streams this member can be true
 * even when unread_bytes is non-zero. To
 * determine if there is more data to be read, use
 * feof instead of reading this item.</p>
 * <p>unread_bytes (int) - the number of bytes
 * currently contained in the PHP's own internal buffer.</p>
 * <p>stream_type (string) - a label describing
 * the underlying implementation of the stream.</p>
 * <p>wrapper_type (string) - a label describing
 * the protocol wrapper implementation layered over the stream.
 * See for more information about wrappers.</p>
 * <p>wrapper_data (mixed) - wrapper specific
 * data attached to this stream. See for
 * more information about wrappers and their wrapper data.</p>
 * <p>mode (string) - the type of access required for
 * this stream (see Table 1 of the fopen() reference)</p>
 * <p>seekable (bool) - whether the current stream can
 * be seeked.</p>
 * <p>uri (string) - the URI/filename associated with this
 * stream.</p>
 * <p>crypto (array) - the TLS connection metadata for this
 * stream. (Note: Only provided when the resource's stream uses TLS.)</p>
 */
function socket_get_status ($stream): array {}

/**
 * Gets line from stream resource up to a given delimiter
 * @link http://www.php.net/manual/en/function.stream-get-line.php
 * @param resource $stream 
 * @param int $length 
 * @param string $ending [optional] 
 * @return string|false Returns a string of up to length bytes read from the file
 * pointed to by stream, or false on failure.
 */
function stream_get_line ($stream, int $length, string $ending = '""'): string|false {}

/**
 * Resolve filename against the include path
 * @link http://www.php.net/manual/en/function.stream-resolve-include-path.php
 * @param string $filename 
 * @return string|false Returns a string containing the resolved absolute filename, or false on failure.
 */
function stream_resolve_include_path (string $filename): string|false {}

/**
 * Retrieve list of registered streams
 * @link http://www.php.net/manual/en/function.stream-get-wrappers.php
 * @return array Returns an indexed array containing the name of all stream wrappers
 * available on the running system.
 */
function stream_get_wrappers (): array {}

/**
 * Retrieve list of registered socket transports
 * @link http://www.php.net/manual/en/function.stream-get-transports.php
 * @return array Returns an indexed array of socket transports names.
 */
function stream_get_transports (): array {}

/**
 * Checks if a stream is a local stream
 * @link http://www.php.net/manual/en/function.stream-is-local.php
 * @param resource|string $stream 
 * @return bool Returns true on success or false on failure.
 */
function stream_is_local ($stream): bool {}

/**
 * Check if a stream is a TTY
 * @link http://www.php.net/manual/en/function.stream-isatty.php
 * @param resource $stream 
 * @return bool Returns true on success or false on failure.
 */
function stream_isatty ($stream): bool {}

/**
 * Set the stream chunk size
 * @link http://www.php.net/manual/en/function.stream-set-chunk-size.php
 * @param resource $stream The target stream.
 * @param int $size The desired new chunk size.
 * @return int Returns the previous chunk size on success.
 * <p>Will return false if size is less than 1 or
 * greater than PHP_INT_MAX.</p>
 */
function stream_set_chunk_size ($stream, int $size): int {}

/**
 * Set timeout period on a stream
 * @link http://www.php.net/manual/en/function.stream-set-timeout.php
 * @param resource $stream 
 * @param int $seconds 
 * @param int $microseconds [optional] 
 * @return bool Returns true on success or false on failure.
 */
function stream_set_timeout ($stream, int $seconds, int $microseconds = null): bool {}

/**
 * Alias of stream_set_timeout
 * @link http://www.php.net/manual/en/function.socket-set-timeout.php
 * @param resource $stream 
 * @param int $seconds 
 * @param int $microseconds [optional] 
 * @return bool Returns true on success or false on failure.
 */
function socket_set_timeout ($stream, int $seconds, int $microseconds = null): bool {}

/**
 * Get the type of a variable
 * @link http://www.php.net/manual/en/function.gettype.php
 * @param mixed $value 
 * @return string Possible values for the returned string are:
 * <p>
 * "boolean"
 * "integer"
 * "double" (for historical reasons "double" is
 * returned in case of a float, and not simply
 * "float")
 * "string"
 * "array"
 * "object"
 * "resource"
 * "resource (closed)" as of PHP 7.2.0
 * "NULL"
 * "unknown type"
 * </p>
 */
function gettype (mixed $value): string {}

/**
 * Gets the type name of a variable in a way that is suitable for debugging
 * @link http://www.php.net/manual/en/function.get-debug-type.php
 * @param mixed $value 
 * @return string Possible values for the returned string are:
 * <table>
 * <tr valign="top">
 * <td>Type + State</td>
 * <td>Return Value</td>
 * <td>Notes</td>
 * </tr>
 * <tr valign="top">
 * <td>null</td>
 * <td>
 * "null"
 * </td>
 * <td>-</td>
 * </tr>
 * <tr valign="top">
 * <td>Booleans (true or false)</td>
 * <td>
 * "bool"
 * </td>
 * <td>-</td>
 * </tr>
 * <tr valign="top">
 * <td>Integers</td>
 * <td>
 * "int"
 * </td>
 * <td>-</td>
 * </tr>
 * <tr valign="top">
 * <td>Floats</td>
 * <td>
 * "float"
 * </td>
 * <td>-</td>
 * </tr>
 * <tr valign="top">
 * <td>Strings</td>
 * <td>
 * "string"
 * </td>
 * <td>-</td>
 * </tr>
 * <tr valign="top">
 * <td>Arrays</td>
 * <td>
 * "array"
 * </td>
 * <td>-</td>
 * </tr>
 * <tr valign="top">
 * <td>Resources</td>
 * <td>
 * "resource (resourcename)"
 * </td>
 * <td>-</td>
 * </tr>
 * <tr valign="top">
 * <td>Resources (Closed)</td>
 * <td>
 * "resource (closed)"
 * </td>
 * <td>Example: A file stream after being closed with fclose.</td>
 * </tr>
 * <tr valign="top">
 * <td>Objects from Named Classes</td>
 * <td>
 * The full name of the class including its namespace e.g. Foo\Bar
 * </td>
 * <td>-</td>
 * </tr>
 * <tr valign="top">
 * <td>Objects from Anonymous Classes</td>
 * <td>
 * "class@anonymous"
 * </td>
 * <td>
 * Anonymous classes are those created through the $x = new class { ... } syntax
 * </td>
 * </tr>
 * </table>
 */
function get_debug_type (mixed $value): string {}

/**
 * Set the type of a variable
 * @link http://www.php.net/manual/en/function.settype.php
 * @param mixed $var 
 * @param string $type 
 * @return bool Returns true on success or false on failure.
 */
function settype (mixed &$var, string $type): bool {}

/**
 * Get the integer value of a variable
 * @link http://www.php.net/manual/en/function.intval.php
 * @param mixed $value 
 * @param int $base [optional] 
 * @return int The integer value of value on success, or 0 on
 * failure. Empty arrays return 0, non-empty arrays return 1.
 * <p>The maximum value depends on the system. 32 bit systems have a 
 * maximum signed integer range of -2147483648 to 2147483647. So for example 
 * on such a system, intval('1000000000000') will return 
 * 2147483647. The maximum signed integer value for 64 bit systems is 
 * 9223372036854775807.</p>
 * <p>Strings will most likely return 0 although this depends on the 
 * leftmost characters of the string. The common rules of 
 * integer casting 
 * apply.</p>
 */
function intval (mixed $value, int $base = 10): int {}

/**
 * Get float value of a variable
 * @link http://www.php.net/manual/en/function.floatval.php
 * @param mixed $value 
 * @return float The float value of the given variable. Empty arrays return 0, non-empty
 * arrays return 1.
 * <p>Strings will most likely return 0 although this depends on the 
 * leftmost characters of the string. The common rules of 
 * float casting 
 * apply.</p>
 */
function floatval (mixed $value): float {}

/**
 * Alias of floatval
 * @link http://www.php.net/manual/en/function.doubleval.php
 * @param mixed $value 
 * @return float The float value of the given variable. Empty arrays return 0, non-empty
 * arrays return 1.
 * <p>Strings will most likely return 0 although this depends on the 
 * leftmost characters of the string. The common rules of 
 * float casting 
 * apply.</p>
 */
function doubleval (mixed $value): float {}

/**
 * Get the boolean value of a variable
 * @link http://www.php.net/manual/en/function.boolval.php
 * @param mixed $value The scalar value being converted to a bool.
 * @return bool The bool value of value.
 */
function boolval (mixed $value): bool {}

/**
 * Get string value of a variable
 * @link http://www.php.net/manual/en/function.strval.php
 * @param mixed $value 
 * @return string The string value of value.
 */
function strval (mixed $value): string {}

/**
 * Finds whether a variable is null
 * @link http://www.php.net/manual/en/function.is-null.php
 * @param mixed $value 
 * @return bool Returns true if value is null, false
 * otherwise.
 */
function is_null (mixed $value): bool {}

/**
 * Finds whether a variable is a resource
 * @link http://www.php.net/manual/en/function.is-resource.php
 * @param mixed $value 
 * @return bool Returns true if value is a resource,
 * false otherwise.
 */
function is_resource (mixed $value): bool {}

/**
 * Finds out whether a variable is a boolean
 * @link http://www.php.net/manual/en/function.is-bool.php
 * @param mixed $value 
 * @return bool Returns true if value is a bool,
 * false otherwise.
 */
function is_bool (mixed $value): bool {}

/**
 * Find whether the type of a variable is integer
 * @link http://www.php.net/manual/en/function.is-int.php
 * @param mixed $value 
 * @return bool Returns true if value is an int,
 * false otherwise.
 */
function is_int (mixed $value): bool {}

/**
 * Alias of is_int
 * @link http://www.php.net/manual/en/function.is-integer.php
 * @param mixed $value 
 * @return bool Returns true if value is an int,
 * false otherwise.
 */
function is_integer (mixed $value): bool {}

/**
 * Alias of is_int
 * @link http://www.php.net/manual/en/function.is-long.php
 * @param mixed $value 
 * @return bool Returns true if value is an int,
 * false otherwise.
 */
function is_long (mixed $value): bool {}

/**
 * Finds whether the type of a variable is float
 * @link http://www.php.net/manual/en/function.is-float.php
 * @param mixed $value 
 * @return bool Returns true if value is a float,
 * false otherwise.
 */
function is_float (mixed $value): bool {}

/**
 * Alias of is_float
 * @link http://www.php.net/manual/en/function.is-double.php
 * @param mixed $value 
 * @return bool Returns true if value is a float,
 * false otherwise.
 */
function is_double (mixed $value): bool {}

/**
 * Finds whether a variable is a number or a numeric string
 * @link http://www.php.net/manual/en/function.is-numeric.php
 * @param mixed $value 
 * @return bool Returns true if value is a number or a
 * numeric string,
 * false otherwise.
 */
function is_numeric (mixed $value): bool {}

/**
 * Find whether the type of a variable is string
 * @link http://www.php.net/manual/en/function.is-string.php
 * @param mixed $value 
 * @return bool Returns true if value is of type string,
 * false otherwise.
 */
function is_string (mixed $value): bool {}

/**
 * Finds whether a variable is an array
 * @link http://www.php.net/manual/en/function.is-array.php
 * @param mixed $value 
 * @return bool Returns true if value is an array,
 * false otherwise.
 */
function is_array (mixed $value): bool {}

/**
 * Finds whether a variable is an object
 * @link http://www.php.net/manual/en/function.is-object.php
 * @param mixed $value 
 * @return bool Returns true if value is an object,
 * false otherwise.
 */
function is_object (mixed $value): bool {}

/**
 * Finds whether a variable is a scalar
 * @link http://www.php.net/manual/en/function.is-scalar.php
 * @param mixed $value 
 * @return bool Returns true if value is a scalar, false
 * otherwise.
 */
function is_scalar (mixed $value): bool {}

/**
 * Verify that a value can be called as a function from the current scope.
 * @link http://www.php.net/manual/en/function.is-callable.php
 * @param mixed $value 
 * @param bool $syntax_only [optional] 
 * @param string $callable_name [optional] 
 * @return bool Returns true if value is callable, false 
 * otherwise.
 */
function is_callable (mixed $value, bool $syntax_only = false, string &$callable_name = null): bool {}

/**
 * Verify that the contents of a variable is an iterable value
 * @link http://www.php.net/manual/en/function.is-iterable.php
 * @param mixed $value 
 * @return bool Returns true if value is iterable, false 
 * otherwise.
 */
function is_iterable (mixed $value): bool {}

/**
 * Verify that the contents of a variable is a countable value
 * @link http://www.php.net/manual/en/function.is-countable.php
 * @param mixed $value 
 * @return bool Returns true if value is countable, false 
 * otherwise.
 */
function is_countable (mixed $value): bool {}

/**
 * Generate a unique ID
 * @link http://www.php.net/manual/en/function.uniqid.php
 * @param string $prefix [optional] 
 * @param bool $more_entropy [optional] 
 * @return string Returns timestamp based unique identifier as a string.
 * <p>This function tries to create unique identifier, but it does not
 * guarantee 100% uniqueness of return value.</p>
 */
function uniqid (string $prefix = '""', bool $more_entropy = false): string {}

/**
 * Parse a URL and return its components
 * @link http://www.php.net/manual/en/function.parse-url.php
 * @param string $url 
 * @param int $component [optional] 
 * @return int|string|array|null|false On seriously malformed URLs, parse_url may return
 * false.
 * <p>If the component parameter is omitted, an
 * associative array is returned. At least one element will be
 * present within the array. Potential keys within this array are:
 * <p>
 * <br>
 * scheme - e.g. http
 * <br>
 * host 
 * <br>
 * port
 * <br>
 * user
 * <br>
 * pass
 * <br>
 * path
 * <br>
 * query - after the question mark ?
 * <br>
 * fragment - after the hashmark #
 * </p></p>
 * <p>If the component parameter is specified,
 * parse_url returns a string (or an
 * int, in the case of PHP_URL_PORT)
 * instead of an array. If the requested component doesn't exist
 * within the given URL, null will be returned.
 * As of PHP 8.0.0, parse_url distinguishes absent and empty
 * queries and fragments:</p>
 * <p><pre>
 * http://example.com/foo → query = null, fragment = null
 * http://example.com/foo? → query = "", fragment = null
 * http://example.com/foo# → query = null, fragment = ""
 * http://example.com/foo?# → query = "", fragment = ""
 * </pre></p>
 * <p>Previously all cases resulted in query and fragment being null.</p>
 * <p>Note that control characters (cf. ctype_cntrl) in the
 * components are replaced with underscores (_).</p>
 */
function parse_url (string $url, int $component = -1): int|string|array|null|false {}

/**
 * URL-encodes string
 * @link http://www.php.net/manual/en/function.urlencode.php
 * @param string $string 
 * @return string Returns a string in which all non-alphanumeric characters except
 * -_. have been replaced with a percent
 * (%) sign followed by two hex digits and spaces encoded
 * as plus (+) signs. It is encoded the same way that the
 * posted data from a WWW form is encoded, that is the same way as in
 * application/x-www-form-urlencoded media type. This
 * differs from the RFC 3986 encoding (see
 * rawurlencode) in that for historical reasons, spaces
 * are encoded as plus (+) signs.
 */
function urlencode (string $string): string {}

/**
 * Decodes URL-encoded string
 * @link http://www.php.net/manual/en/function.urldecode.php
 * @param string $string 
 * @return string Returns the decoded string.
 */
function urldecode (string $string): string {}

/**
 * URL-encode according to RFC 3986
 * @link http://www.php.net/manual/en/function.rawurlencode.php
 * @param string $string 
 * @return string Returns a string in which all non-alphanumeric characters except
 * -_.~ have been replaced with a percent
 * (%) sign followed by two hex digits. This is the
 * encoding described in RFC 3986 for
 * protecting literal characters from being interpreted as special URL
 * delimiters, and for protecting URLs from being mangled by transmission
 * media with character conversions (like some email systems).
 */
function rawurlencode (string $string): string {}

/**
 * Decode URL-encoded strings
 * @link http://www.php.net/manual/en/function.rawurldecode.php
 * @param string $string 
 * @return string Returns the decoded URL, as a string.
 */
function rawurldecode (string $string): string {}

/**
 * Fetches all the headers sent by the server in response to an HTTP request
 * @link http://www.php.net/manual/en/function.get-headers.php
 * @param string $url 
 * @param bool $associative [optional] 
 * @param resource|null $context [optional] 
 * @return array|false Returns an indexed or associative array with the headers, or false on
 * failure.
 */
function get_headers (string $url, bool $associative = false, $context = null): array|false {}

/**
 * Returns a bucket object from the brigade to operate on
 * @link http://www.php.net/manual/en/function.stream-bucket-make-writeable.php
 * @param resource $brigade 
 * @return object|null Returns a bucket object with the properties listed below or null.
 * <p>
 * data
 * (string)
 * <br>
 * <p>
 * data bucket The current string in the bucket.
 * </p>
 * datalen
 * (integer)
 * <br>
 * <p>
 * datalen bucket The length of the string in the bucket.
 * </p>
 * </p>
 * <p>data bucket The current string in the bucket.</p>
 * <p>datalen bucket The length of the string in the bucket.</p>
 */
function stream_bucket_make_writeable ($brigade): ?object {}

/**
 * Prepend bucket to brigade
 * @link http://www.php.net/manual/en/function.stream-bucket-prepend.php
 * @param resource $brigade brigade is a resource pointing to a bucket brigade
 * which contains one or more bucket objects.
 * @param object $bucket A bucket object.
 * @return void No value is returned.
 */
function stream_bucket_prepend ($brigade, object $bucket): void {}

/**
 * Append bucket to brigade
 * @link http://www.php.net/manual/en/function.stream-bucket-append.php
 * @param resource $brigade 
 * @param object $bucket 
 * @return void 
 */
function stream_bucket_append ($brigade, object $bucket): void {}

/**
 * Create a new bucket for use on the current stream
 * @link http://www.php.net/manual/en/function.stream-bucket-new.php
 * @param resource $stream 
 * @param string $buffer 
 * @return object 
 */
function stream_bucket_new ($stream, string $buffer): object {}

/**
 * Retrieve list of registered filters
 * @link http://www.php.net/manual/en/function.stream-get-filters.php
 * @return array Returns an indexed array containing the name of all stream filters
 * available.
 */
function stream_get_filters (): array {}

/**
 * Register a user defined stream filter
 * @link http://www.php.net/manual/en/function.stream-filter-register.php
 * @param string $filter_name 
 * @param string $class 
 * @return bool Returns true on success or false on failure.
 * <p>stream_filter_register will return false if the
 * filter_name is already defined.</p>
 */
function stream_filter_register (string $filter_name, string $class): bool {}

/**
 * Uuencode a string
 * @link http://www.php.net/manual/en/function.convert-uuencode.php
 * @param string $string 
 * @return string Returns the uuencoded data.
 */
function convert_uuencode (string $string): string {}

/**
 * Decode a uuencoded string
 * @link http://www.php.net/manual/en/function.convert-uudecode.php
 * @param string $string 
 * @return string|false Returns the decoded data as a string or false on failure.
 */
function convert_uudecode (string $string): string|false {}

/**
 * Dumps information about a variable
 * @link http://www.php.net/manual/en/function.var-dump.php
 * @param mixed $value 
 * @param mixed $values 
 * @return void No value is returned.
 */
function var_dump (mixed $value, mixed ...$values): void {}

/**
 * Outputs or returns a parsable string representation of a variable
 * @link http://www.php.net/manual/en/function.var-export.php
 * @param mixed $value 
 * @param bool $return [optional] 
 * @return string|null Returns the variable representation when the return 
 * parameter is used and evaluates to true. Otherwise, this function will
 * return null.
 */
function var_export (mixed $value, bool $return = false): ?string {}

/**
 * Dumps a string representation of an internal zval structure to output
 * @link http://www.php.net/manual/en/function.debug-zval-dump.php
 * @param mixed $value 
 * @param mixed $values 
 * @return void No value is returned.
 */
function debug_zval_dump (mixed $value, mixed ...$values): void {}

/**
 * Generates a storable representation of a value
 * @link http://www.php.net/manual/en/function.serialize.php
 * @param mixed $value 
 * @return string Returns a string containing a byte-stream representation of 
 * value that can be stored anywhere.
 * <p>Note that this is a binary string which may include null bytes, and needs
 * to be stored and handled as such. For example,
 * serialize output should generally be stored in a BLOB
 * field in a database, rather than a CHAR or TEXT field.</p>
 */
function serialize (mixed $value): string {}

/**
 * Creates a PHP value from a stored representation
 * @link http://www.php.net/manual/en/function.unserialize.php
 * @param string $data 
 * @param array $options [optional] 
 * @return mixed The converted value is returned, and can be a bool,
 * int, float, string,
 * array or object.
 * <p>In case the passed string is not unserializeable, false is returned and
 * E_NOTICE is issued.</p>
 */
function unserialize (string $data, array $options = '[]'): mixed {}

/**
 * Returns the amount of memory allocated to PHP
 * @link http://www.php.net/manual/en/function.memory-get-usage.php
 * @param bool $real_usage [optional] 
 * @return int Returns the memory amount in bytes.
 */
function memory_get_usage (bool $real_usage = false): int {}

/**
 * Returns the peak of memory allocated by PHP
 * @link http://www.php.net/manual/en/function.memory-get-peak-usage.php
 * @param bool $real_usage [optional] 
 * @return int Returns the memory peak in bytes.
 */
function memory_get_peak_usage (bool $real_usage = false): int {}

/**
 * Reset the peak memory usage
 * @link http://www.php.net/manual/en/function.memory-reset-peak-usage.php
 * @return void No value is returned.
 */
function memory_reset_peak_usage (): void {}

/**
 * Compares two "PHP-standardized" version number strings
 * @link http://www.php.net/manual/en/function.version-compare.php
 * @param string $version1 
 * @param string $version2 
 * @param string|null $operator [optional] 
 * @return int|bool By default, version_compare returns
 * -1 if the first version is lower than the second,
 * 0 if they are equal, and
 * 1 if the second is lower.
 * <p>When using the optional operator argument, the
 * function will return true if the relationship is the one specified
 * by the operator, false otherwise.</p>
 */
function version_compare (string $version1, string $version2, ?string $operator = null): int|bool {}

/**
 * Loads a PHP extension at runtime
 * @link http://www.php.net/manual/en/function.dl.php
 * @param string $extension_filename 
 * @return bool Returns true on success or false on failure. If the functionality of loading modules is not available
 * or has been disabled (by setting
 * enable_dl off
 * in php.ini) an E_ERROR is emitted
 * and execution is stopped. If dl fails because the
 * specified library couldn't be loaded, in addition to false an
 * E_WARNING message is emitted.
 */
function dl (string $extension_filename): bool {}

/**
 * Sets the process title
 * @link http://www.php.net/manual/en/function.cli-set-process-title.php
 * @param string $title The new title.
 * @return bool Returns true on success or false on failure.
 */
function cli_set_process_title (string $title): bool {}

/**
 * Returns the current process title
 * @link http://www.php.net/manual/en/function.cli-get-process-title.php
 * @return string|null Return a string with the current process title or null on error.
 */
function cli_get_process_title (): ?string {}


/**
 * 
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('EXTR_OVERWRITE', 0);

/**
 * 
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('EXTR_SKIP', 1);

/**
 * 
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('EXTR_PREFIX_SAME', 2);

/**
 * 
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('EXTR_PREFIX_ALL', 3);

/**
 * 
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('EXTR_PREFIX_INVALID', 4);

/**
 * 
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('EXTR_PREFIX_IF_EXISTS', 5);

/**
 * 
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('EXTR_IF_EXISTS', 6);

/**
 * 
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('EXTR_REFS', 256);

/**
 * SORT_ASC is used with
 * array_multisort to sort in ascending order.
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('SORT_ASC', 4);

/**
 * SORT_DESC is used with
 * array_multisort to sort in descending order.
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('SORT_DESC', 3);

/**
 * SORT_REGULAR is used to compare items normally.
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('SORT_REGULAR', 0);

/**
 * SORT_NUMERIC is used to compare items numerically.
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('SORT_NUMERIC', 1);

/**
 * SORT_STRING is used to compare items as strings.
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('SORT_STRING', 2);

/**
 * SORT_LOCALE_STRING is used to compare items as
 * strings, based on the current locale.
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('SORT_LOCALE_STRING', 5);

/**
 * SORT_NATURAL is used to compare items as
 * strings using "natural ordering" like natsort.
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('SORT_NATURAL', 6);

/**
 * SORT_FLAG_CASE can be combined (bitwise OR) with
 * SORT_STRING or SORT_NATURAL to
 * sort strings case-insensitively. As of PHP 8.2.0, only ASCII case folding
 * will be done.
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('SORT_FLAG_CASE', 8);

/**
 * CASE_LOWER is used with
 * array_change_key_case and is used to convert array
 * keys to lower case. This is also the default case for
 * array_change_key_case. As of PHP 8.2.0, only ASCII
 * characters will be converted.
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('CASE_LOWER', 0);

/**
 * CASE_UPPER is used with
 * array_change_key_case and is used to convert array
 * keys to upper case. As of PHP 8.2.0, only ASCII characters will be
 * converted.
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('CASE_UPPER', 1);

/**
 * 
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('COUNT_NORMAL', 0);

/**
 * 
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('COUNT_RECURSIVE', 1);

/**
 * ARRAY_FILTER_USE_BOTH is used with
 * array_filter to pass both value and key to the given callback function.
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('ARRAY_FILTER_USE_BOTH', 1);

/**
 * ARRAY_FILTER_USE_KEY is used with
 * array_filter to pass each key as the first argument to the given callback function.
 * @link http://www.php.net/manual/en/array.constants.php
 * @var int
 */
define ('ARRAY_FILTER_USE_KEY', 2);

/**
 * 
 * @link http://www.php.net/manual/en/misc.constants.php
 * @var int
 */
define ('CONNECTION_ABORTED', 1);

/**
 * 
 * @link http://www.php.net/manual/en/misc.constants.php
 * @var int
 */
define ('CONNECTION_NORMAL', 0);

/**
 * 
 * @link http://www.php.net/manual/en/misc.constants.php
 * @var int
 */
define ('CONNECTION_TIMEOUT', 2);
define ('INI_USER', 1);
define ('INI_PERDIR', 2);
define ('INI_SYSTEM', 4);
define ('INI_ALL', 7);

/**
 * Normal INI scanner mode.
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('INI_SCANNER_NORMAL', 0);

/**
 * Raw INI scanner mode.
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('INI_SCANNER_RAW', 1);

/**
 * Typed INI scanner mode.
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('INI_SCANNER_TYPED', 2);

/**
 * 
 * @link http://www.php.net/manual/en/url.constants.php
 * @var int
 */
define ('PHP_URL_SCHEME', 0);

/**
 * Outputs the hostname of the URL parsed.
 * @link http://www.php.net/manual/en/url.constants.php
 * @var int
 */
define ('PHP_URL_HOST', 1);

/**
 * Outputs the port of the URL parsed.
 * @link http://www.php.net/manual/en/url.constants.php
 * @var int
 */
define ('PHP_URL_PORT', 2);

/**
 * Outputs the user of the URL parsed.
 * @link http://www.php.net/manual/en/url.constants.php
 * @var int
 */
define ('PHP_URL_USER', 3);

/**
 * Outputs the password of the URL parsed.
 * @link http://www.php.net/manual/en/url.constants.php
 * @var int
 */
define ('PHP_URL_PASS', 4);

/**
 * Outputs the path of the URL parsed.
 * @link http://www.php.net/manual/en/url.constants.php
 * @var int
 */
define ('PHP_URL_PATH', 5);

/**
 * Outputs the query string of the URL parsed.
 * @link http://www.php.net/manual/en/url.constants.php
 * @var int
 */
define ('PHP_URL_QUERY', 6);

/**
 * Outputs the fragment (string after the hashmark #) of the URL parsed.
 * @link http://www.php.net/manual/en/url.constants.php
 * @var int
 */
define ('PHP_URL_FRAGMENT', 7);

/**
 * Encoding is performed per
 * RFC 1738 and the
 * application/x-www-form-urlencoded media type, which
 * implies that spaces are encoded as plus (+) signs.
 * @link http://www.php.net/manual/en/url.constants.php
 * @var int
 */
define ('PHP_QUERY_RFC1738', 1);

/**
 * Encoding is performed according to RFC 3986,
 * and spaces will be percent encoded (%20).
 * @link http://www.php.net/manual/en/url.constants.php
 * @var int
 */
define ('PHP_QUERY_RFC3986', 2);
define ('M_E', 2.718281828459);
define ('M_LOG2E', 1.442695040889);
define ('M_LOG10E', 0.43429448190325);
define ('M_LN2', 0.69314718055995);
define ('M_LN10', 2.302585092994);
define ('M_PI', 3.1415926535898);
define ('M_PI_2', 1.5707963267949);
define ('M_PI_4', 0.78539816339745);
define ('M_1_PI', 0.31830988618379);
define ('M_2_PI', 0.63661977236758);
define ('M_SQRTPI', 1.7724538509055);
define ('M_2_SQRTPI', 1.1283791670955);
define ('M_LNPI', 1.1447298858494);
define ('M_EULER', 0.57721566490153);
define ('M_SQRT2', 1.4142135623731);
define ('M_SQRT1_2', 0.70710678118655);
define ('M_SQRT3', 1.7320508075689);
define ('INF', INF);
define ('NAN', NAN);
define ('PHP_ROUND_HALF_UP', 1);
define ('PHP_ROUND_HALF_DOWN', 2);
define ('PHP_ROUND_HALF_EVEN', 3);
define ('PHP_ROUND_HALF_ODD', 4);
define ('DNS_A', 1);
define ('DNS_NS', 2);
define ('DNS_CNAME', 16);
define ('DNS_SOA', 32);
define ('DNS_PTR', 2048);
define ('DNS_HINFO', 4096);
define ('DNS_CAA', 8192);
define ('DNS_MX', 16384);
define ('DNS_TXT', 32768);
define ('DNS_SRV', 33554432);
define ('DNS_NAPTR', 67108864);
define ('DNS_AAAA', 134217728);
define ('DNS_A6', 16777216);
define ('DNS_ANY', 268435456);
define ('DNS_ALL', 251721779);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_GIF', 1);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_JPEG', 2);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_PNG', 3);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_SWF', 4);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_PSD', 5);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_BMP', 6);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_TIFF_II', 7);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_TIFF_MM', 8);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_JPC', 9);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_JP2', 10);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_JPX', 11);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_JB2', 12);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_SWC', 13);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_IFF', 14);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_WBMP', 15);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_JPEG2000', 9);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_XBM', 16);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_ICO', 17);

/**
 * >
 * Image type constant used by the image_type_to_mime_type
 * and image_type_to_extension functions.
 * (Available as of PHP 7.1.0)
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMAGETYPE_WEBP', 18);
define ('IMAGETYPE_AVIF', 19);
define ('IMAGETYPE_UNKNOWN', 0);
define ('IMAGETYPE_COUNT', 20);
define ('LOG_EMERG', 0);
define ('LOG_ALERT', 1);
define ('LOG_CRIT', 2);
define ('LOG_ERR', 3);
define ('LOG_WARNING', 4);
define ('LOG_NOTICE', 5);
define ('LOG_INFO', 6);
define ('LOG_DEBUG', 7);
define ('LOG_KERN', 0);
define ('LOG_USER', 8);
define ('LOG_MAIL', 16);
define ('LOG_DAEMON', 24);
define ('LOG_AUTH', 32);
define ('LOG_SYSLOG', 40);
define ('LOG_LPR', 48);
define ('LOG_NEWS', 56);
define ('LOG_UUCP', 64);
define ('LOG_CRON', 72);
define ('LOG_AUTHPRIV', 80);
define ('LOG_LOCAL0', 128);
define ('LOG_LOCAL1', 136);
define ('LOG_LOCAL2', 144);
define ('LOG_LOCAL3', 152);
define ('LOG_LOCAL4', 160);
define ('LOG_LOCAL5', 168);
define ('LOG_LOCAL6', 176);
define ('LOG_LOCAL7', 184);
define ('LOG_PID', 1);
define ('LOG_CONS', 2);
define ('LOG_ODELAY', 4);
define ('LOG_NDELAY', 8);
define ('LOG_NOWAIT', 16);
define ('LOG_PERROR', 32);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('STR_PAD_LEFT', 0);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('STR_PAD_RIGHT', 1);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('STR_PAD_BOTH', 2);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('PATHINFO_DIRNAME', 1);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('PATHINFO_BASENAME', 2);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('PATHINFO_EXTENSION', 4);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('PATHINFO_FILENAME', 8);
define ('PATHINFO_ALL', 15);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('CHAR_MAX', 127);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('LC_CTYPE', 2);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('LC_NUMERIC', 4);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('LC_TIME', 5);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('LC_COLLATE', 1);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('LC_MONETARY', 3);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('LC_ALL', 0);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('LC_MESSAGES', 6);
define ('INFO_GENERAL', 1);
define ('INFO_CREDITS', 2);
define ('INFO_CONFIGURATION', 4);
define ('INFO_MODULES', 8);
define ('INFO_ENVIRONMENT', 16);
define ('INFO_VARIABLES', 32);
define ('INFO_LICENSE', 64);
define ('INFO_ALL', 4294967295);
define ('CREDITS_GROUP', 1);
define ('CREDITS_GENERAL', 2);
define ('CREDITS_SAPI', 4);
define ('CREDITS_MODULES', 8);
define ('CREDITS_DOCS', 16);
define ('CREDITS_FULLPAGE', 32);
define ('CREDITS_QA', 64);
define ('CREDITS_ALL', 4294967295);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('HTML_SPECIALCHARS', 0);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('HTML_ENTITIES', 1);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('ENT_COMPAT', 2);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('ENT_QUOTES', 3);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('ENT_NOQUOTES', 0);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('ENT_IGNORE', 4);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('ENT_SUBSTITUTE', 8);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('ENT_DISALLOWED', 128);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('ENT_HTML401', 0);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('ENT_XML1', 16);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('ENT_XHTML', 32);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('ENT_HTML5', 48);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('SEEK_SET', 0);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('SEEK_CUR', 1);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('SEEK_END', 2);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('LOCK_SH', 1);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('LOCK_EX', 2);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('LOCK_UN', 3);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('LOCK_NB', 4);
define ('STREAM_NOTIFY_CONNECT', 2);
define ('STREAM_NOTIFY_AUTH_REQUIRED', 3);
define ('STREAM_NOTIFY_AUTH_RESULT', 10);
define ('STREAM_NOTIFY_MIME_TYPE_IS', 4);
define ('STREAM_NOTIFY_FILE_SIZE_IS', 5);
define ('STREAM_NOTIFY_REDIRECTED', 6);
define ('STREAM_NOTIFY_PROGRESS', 7);
define ('STREAM_NOTIFY_FAILURE', 9);
define ('STREAM_NOTIFY_COMPLETED', 8);
define ('STREAM_NOTIFY_RESOLVE', 1);
define ('STREAM_NOTIFY_SEVERITY_INFO', 0);
define ('STREAM_NOTIFY_SEVERITY_WARN', 1);
define ('STREAM_NOTIFY_SEVERITY_ERR', 2);
define ('STREAM_FILTER_READ', 1);
define ('STREAM_FILTER_WRITE', 2);
define ('STREAM_FILTER_ALL', 3);
define ('STREAM_CLIENT_PERSISTENT', 1);
define ('STREAM_CLIENT_ASYNC_CONNECT', 2);
define ('STREAM_CLIENT_CONNECT', 4);
define ('STREAM_CRYPTO_METHOD_ANY_CLIENT', 127);
define ('STREAM_CRYPTO_METHOD_SSLv2_CLIENT', 3);
define ('STREAM_CRYPTO_METHOD_SSLv3_CLIENT', 5);
define ('STREAM_CRYPTO_METHOD_SSLv23_CLIENT', 57);
define ('STREAM_CRYPTO_METHOD_TLS_CLIENT', 121);
define ('STREAM_CRYPTO_METHOD_TLSv1_0_CLIENT', 9);
define ('STREAM_CRYPTO_METHOD_TLSv1_1_CLIENT', 17);
define ('STREAM_CRYPTO_METHOD_TLSv1_2_CLIENT', 33);
define ('STREAM_CRYPTO_METHOD_TLSv1_3_CLIENT', 65);
define ('STREAM_CRYPTO_METHOD_ANY_SERVER', 126);
define ('STREAM_CRYPTO_METHOD_SSLv2_SERVER', 2);
define ('STREAM_CRYPTO_METHOD_SSLv3_SERVER', 4);
define ('STREAM_CRYPTO_METHOD_SSLv23_SERVER', 120);
define ('STREAM_CRYPTO_METHOD_TLS_SERVER', 120);
define ('STREAM_CRYPTO_METHOD_TLSv1_0_SERVER', 8);
define ('STREAM_CRYPTO_METHOD_TLSv1_1_SERVER', 16);
define ('STREAM_CRYPTO_METHOD_TLSv1_2_SERVER', 32);
define ('STREAM_CRYPTO_METHOD_TLSv1_3_SERVER', 64);
define ('STREAM_CRYPTO_PROTO_SSLv3', 4);
define ('STREAM_CRYPTO_PROTO_TLSv1_0', 8);
define ('STREAM_CRYPTO_PROTO_TLSv1_1', 16);
define ('STREAM_CRYPTO_PROTO_TLSv1_2', 32);
define ('STREAM_CRYPTO_PROTO_TLSv1_3', 64);
define ('STREAM_SHUT_RD', 0);
define ('STREAM_SHUT_WR', 1);
define ('STREAM_SHUT_RDWR', 2);
define ('STREAM_PF_INET', 2);
define ('STREAM_PF_INET6', 30);
define ('STREAM_PF_UNIX', 1);
define ('STREAM_IPPROTO_IP', 0);
define ('STREAM_IPPROTO_TCP', 6);
define ('STREAM_IPPROTO_UDP', 17);
define ('STREAM_IPPROTO_ICMP', 1);
define ('STREAM_IPPROTO_RAW', 255);
define ('STREAM_SOCK_STREAM', 1);
define ('STREAM_SOCK_DGRAM', 2);
define ('STREAM_SOCK_RAW', 3);
define ('STREAM_SOCK_SEQPACKET', 5);
define ('STREAM_SOCK_RDM', 4);
define ('STREAM_PEEK', 2);
define ('STREAM_OOB', 1);
define ('STREAM_SERVER_BIND', 4);
define ('STREAM_SERVER_LISTEN', 8);

/**
 * Search for filename in
 * include_path.
 * @link http://www.php.net/manual/en/ini.include-path.php
 * @var int
 */
define ('FILE_USE_INCLUDE_PATH', 1);

/**
 * Strip EOL characters.
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('FILE_IGNORE_NEW_LINES', 2);

/**
 * Skip empty lines.
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('FILE_SKIP_EMPTY_LINES', 4);

/**
 * Append content to existing file.
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('FILE_APPEND', 8);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('FILE_NO_DEFAULT_CONTEXT', 16);

/**
 * Text mode.
 * <p>
 * This constant has no effect, and is only available for 
 * forward compatibility.
 * </p>
 * <p>This constant has no effect, and is only available for 
 * forward compatibility.</p>
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('FILE_TEXT', 0);

/**
 * Binary mode.
 * <p>
 * This constant has no effect, and is only available for 
 * forward compatibility.
 * </p>
 * <p>This constant has no effect, and is only available for 
 * forward compatibility.</p>
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('FILE_BINARY', 0);

/**
 * Disable backslash escaping.
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('FNM_NOESCAPE', 1);

/**
 * Slash in string only matches slash in the given pattern.
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('FNM_PATHNAME', 2);

/**
 * Leading period in string must be exactly matched by period in the given pattern.
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('FNM_PERIOD', 4);

/**
 * Caseless match. Part of the GNU extension.
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('FNM_CASEFOLD', 16);
define ('PSFS_PASS_ON', 2);
define ('PSFS_FEED_ME', 1);
define ('PSFS_ERR_FATAL', 0);
define ('PSFS_FLAG_NORMAL', 0);
define ('PSFS_FLAG_FLUSH_INC', 1);
define ('PSFS_FLAG_FLUSH_CLOSE', 2);

/**
 * The default algorithm to use for hashing if no algorithm is provided.
 * This may change in newer PHP releases when newer, stronger hashing
 * algorithms are supported.
 * <p>It is worth noting that over time this constant can (and likely will)
 * change. Therefore you should be aware that the length of the resulting
 * hash can change. Therefore, if you use PASSWORD_DEFAULT
 * you should store the resulting hash in a way that can store more than 60
 * characters (255 is the recommended width).</p>
 * <p>Values for this constant:</p>
 * @link http://www.php.net/manual/en/password.constants.php
 * @var mixed
 */
define ('PASSWORD_DEFAULT', "2y");

/**
 * PASSWORD_BCRYPT is used to create new password
 * hashes using the CRYPT_BLOWFISH algorithm.
 * <p>This will always result in a hash using the "$2y$" crypt format, 
 * which is always 60 characters wide.</p>
 * <p>Supported Options:</p>
 * <p>salt (string) - to manually provide a salt to use when hashing the password.
 * Note that this will override and prevent a salt from being automatically generated.</p>
 * <p>If omitted, a random salt will be generated by password_hash for
 * each password hashed. This is the intended mode of operation
 * and as of PHP 7.0.0 the salt option has been deprecated.</p>
 * <p>cost (int) - which denotes the algorithmic cost that 
 * should be used. Examples of these values can be found on the crypt 
 * page.</p>
 * <p>If omitted, a default value of 10 will be used. This is a good
 * baseline cost, but you may want to consider increasing it depending on your hardware.</p>
 * @link http://www.php.net/manual/en/password.constants.php
 * @var string
 */
define ('PASSWORD_BCRYPT', "2y");

/**
 * PASSWORD_ARGON2I is used to create new password
 * hashes using the Argon2i algorithm.
 * <p>Supported Options:</p>
 * <p>memory_cost (int) - Maximum memory (in kibibytes) that may 
 * be used to compute the Argon2 hash. Defaults to PASSWORD_ARGON2_DEFAULT_MEMORY_COST.</p>
 * <p>time_cost (int) - Maximum amount of time it may 
 * take to compute the Argon2 hash. Defaults to PASSWORD_ARGON2_DEFAULT_TIME_COST.</p>
 * <p>threads (int) - Number of threads to use for computing 
 * the Argon2 hash. Defaults to PASSWORD_ARGON2_DEFAULT_THREADS.
 * Only available with libargon2, not with libsodium implementation.</p>
 * <p>Available as of PHP 7.2.0.</p>
 * @link http://www.php.net/manual/en/password.constants.php
 * @var string
 */
define ('PASSWORD_ARGON2I', "argon2i");

/**
 * PASSWORD_ARGON2ID is used to create new password
 * hashes using the Argon2id algorithm. It supports the same options as
 * PASSWORD_ARGON2I.
 * <p>Available as of PHP 7.3.0.</p>
 * @link http://www.php.net/manual/en/constant.password-argon2i.php
 * @var string
 */
define ('PASSWORD_ARGON2ID', "argon2id");
define ('PASSWORD_BCRYPT_DEFAULT_COST', 10);

/**
 * Default amount of memory in bytes that will be used while trying to
 * compute a hash.
 * <p>Available as of PHP 7.2.0.</p>
 * @link http://www.php.net/manual/en/password.constants.php
 * @var int
 */
define ('PASSWORD_ARGON2_DEFAULT_MEMORY_COST', 65536);

/**
 * Default amount of time that will be spent trying to compute a hash.
 * <p>Available as of PHP 7.2.0.</p>
 * @link http://www.php.net/manual/en/password.constants.php
 * @var int
 */
define ('PASSWORD_ARGON2_DEFAULT_TIME_COST', 4);

/**
 * Default number of threads that Argon2lib will use.
 * Not available with libsodium implementation.
 * <p>Available as of PHP 7.2.0.</p>
 * @link http://www.php.net/manual/en/password.constants.php
 * @var int
 */
define ('PASSWORD_ARGON2_DEFAULT_THREADS', 1);
define ('PASSWORD_ARGON2_PROVIDER', "standard");
define ('ABDAY_1', 14);
define ('ABDAY_2', 15);
define ('ABDAY_3', 16);
define ('ABDAY_4', 17);
define ('ABDAY_5', 18);
define ('ABDAY_6', 19);
define ('ABDAY_7', 20);
define ('DAY_1', 7);
define ('DAY_2', 8);
define ('DAY_3', 9);
define ('DAY_4', 10);
define ('DAY_5', 11);
define ('DAY_6', 12);
define ('DAY_7', 13);
define ('ABMON_1', 33);
define ('ABMON_2', 34);
define ('ABMON_3', 35);
define ('ABMON_4', 36);
define ('ABMON_5', 37);
define ('ABMON_6', 38);
define ('ABMON_7', 39);
define ('ABMON_8', 40);
define ('ABMON_9', 41);
define ('ABMON_10', 42);
define ('ABMON_11', 43);
define ('ABMON_12', 44);
define ('MON_1', 21);
define ('MON_2', 22);
define ('MON_3', 23);
define ('MON_4', 24);
define ('MON_5', 25);
define ('MON_6', 26);
define ('MON_7', 27);
define ('MON_8', 28);
define ('MON_9', 29);
define ('MON_10', 30);
define ('MON_11', 31);
define ('MON_12', 32);
define ('AM_STR', 5);
define ('PM_STR', 6);
define ('D_T_FMT', 1);
define ('D_FMT', 2);
define ('T_FMT', 3);
define ('T_FMT_AMPM', 4);
define ('ERA', 45);
define ('ERA_D_T_FMT', 47);
define ('ERA_D_FMT', 46);
define ('ERA_T_FMT', 48);
define ('ALT_DIGITS', 49);
define ('CRNCYSTR', 56);
define ('RADIXCHAR', 50);
define ('THOUSEP', 51);
define ('YESEXPR', 52);
define ('NOEXPR', 53);
define ('YESSTR', 54);
define ('NOSTR', 55);
define ('CODESET', 0);

/**
 * 
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('CRYPT_SALT_LENGTH', 123);

/**
 * Indicates whether standard DES-based hashes are supported in crypt. Always 1.
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('CRYPT_STD_DES', 1);

/**
 * Indicates whether extended DES-based hashes are supported in crypt. Always 1.
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('CRYPT_EXT_DES', 1);

/**
 * Indicates whether MD5 hashes are supported in crypt. Always 1.
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('CRYPT_MD5', 1);

/**
 * Indicates whether Blowfish hashes are supported in crypt. Always 1.
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('CRYPT_BLOWFISH', 1);

/**
 * Indicates whether SHA-256 hashes are supported in crypt. Always 1.
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('CRYPT_SHA256', 1);

/**
 * Indicates whether SHA-512 hashes are supported in crypt. Always 1.
 * @link http://www.php.net/manual/en/string.constants.php
 * @var int
 */
define ('CRYPT_SHA512', 1);

/**
 * 
 * @link http://www.php.net/manual/en/dir.constants.php
 * @var string
 */
define ('DIRECTORY_SEPARATOR', "/");

/**
 * Semicolon on Windows, colon otherwise.
 * @link http://www.php.net/manual/en/dir.constants.php
 * @var string
 */
define ('PATH_SEPARATOR', ":");

/**
 * 
 * @link http://www.php.net/manual/en/dir.constants.php
 * @var int
 */
define ('SCANDIR_SORT_ASCENDING', 0);

/**
 * 
 * @link http://www.php.net/manual/en/dir.constants.php
 * @var int
 */
define ('SCANDIR_SORT_DESCENDING', 1);

/**
 * 
 * @link http://www.php.net/manual/en/dir.constants.php
 * @var int
 */
define ('SCANDIR_SORT_NONE', 2);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('GLOB_BRACE', 128);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('GLOB_MARK', 8);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('GLOB_NOSORT', 32);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('GLOB_NOCHECK', 16);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('GLOB_NOESCAPE', 8192);
define ('GLOB_ERR', 4);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('GLOB_ONLYDIR', 1073741824);

/**
 * 
 * @link http://www.php.net/manual/en/filesystem.constants.php
 * @var int
 */
define ('GLOB_AVAILABLE_FLAGS', 1073750204);
define ('ASSERT_ACTIVE', 1);
define ('ASSERT_CALLBACK', 2);
define ('ASSERT_BAIL', 3);
define ('ASSERT_WARNING', 4);
define ('ASSERT_EXCEPTION', 5);
define ('STREAM_USE_PATH', 1);
define ('STREAM_IGNORE_URL', 2);
define ('STREAM_REPORT_ERRORS', 8);
define ('STREAM_MUST_SEEK', 16);
define ('STREAM_URL_STAT_LINK', 1);
define ('STREAM_URL_STAT_QUIET', 2);
define ('STREAM_MKDIR_RECURSIVE', 1);
define ('STREAM_IS_URL', 1);
define ('STREAM_OPTION_BLOCKING', 1);
define ('STREAM_OPTION_READ_TIMEOUT', 4);
define ('STREAM_OPTION_READ_BUFFER', 2);
define ('STREAM_OPTION_WRITE_BUFFER', 3);
define ('STREAM_BUFFER_NONE', 0);
define ('STREAM_BUFFER_LINE', 1);
define ('STREAM_BUFFER_FULL', 2);
define ('STREAM_CAST_AS_STREAM', 0);
define ('STREAM_CAST_FOR_SELECT', 3);
define ('STREAM_META_TOUCH', 1);
define ('STREAM_META_OWNER', 3);
define ('STREAM_META_OWNER_NAME', 2);
define ('STREAM_META_GROUP', 5);
define ('STREAM_META_GROUP_NAME', 4);
define ('STREAM_META_ACCESS', 6);

// End of standard v.8.2.6
