<?php

// Start of apcu v.5.1.22

/**
 * The APCUIterator class makes it easier to iterate
 * over large APCu caches. This is helpful as it allows iterating over large
 * caches in steps, while grabbing a defined number of entries per lock instance,
 * so it frees the cache locks for other activities rather than hold up the
 * entire cache to grab 100 (the default) entries. Also, using regular expression
 * matching is more efficient as it's been moved to the C level.
 * @link http://www.php.net/manual/en/class.apcuiterator.php
 */
class APCUIterator implements Iterator, Traversable {

	/**
	 * Constructs an APCUIterator iterator object
	 * @link http://www.php.net/manual/en/apcuiterator.construct.php
	 * @param mixed $search [optional]
	 * @param int $format [optional]
	 * @param int $chunk_size [optional]
	 * @param int $list [optional]
	 */
	public function __construct ($search = null, int $format = 4294967295, int $chunk_size = 0, int $list = 1) {}

	/**
	 * Rewinds iterator
	 * @link http://www.php.net/manual/en/apcuiterator.rewind.php
	 * @return void 
	 */
	public function rewind (): void {}

	/**
	 * Move pointer to next item
	 * @link http://www.php.net/manual/en/apcuiterator.next.php
	 * @return bool true on success or false on failure
	 */
	public function next (): void {}

	/**
	 * Checks if current position is valid
	 * @link http://www.php.net/manual/en/apcuiterator.valid.php
	 * @return bool true if the current iterator position is valid, otherwise false.
	 */
	public function valid (): bool {}

	/**
	 * Get iterator key
	 * @link http://www.php.net/manual/en/apcuiterator.key.php
	 * @return string the key on success, or false upon failure.
	 */
	public function key (): string|int {}

	/**
	 * Get current item
	 * @link http://www.php.net/manual/en/apcuiterator.current.php
	 * @return mixed the current item on success, or false if no
	 * more items or exist, or on failure.
	 */
	public function current (): mixed {}

	/**
	 * Get total cache hits
	 * @link http://www.php.net/manual/en/apcuiterator.gettotalhits.php
	 * @return int The number of hits on success, or false on failure.
	 */
	public function getTotalHits (): int {}

	/**
	 * Get total cache size
	 * @link http://www.php.net/manual/en/apcuiterator.gettotalsize.php
	 * @return int The total cache size.
	 */
	public function getTotalSize (): int {}

	/**
	 * Get total count
	 * @link http://www.php.net/manual/en/apcuiterator.gettotalcount.php
	 * @return int The total count.
	 */
	public function getTotalCount (): int {}

}

/**
 * Clears the APCu cache
 * @link http://www.php.net/manual/en/function.apcu-clear-cache.php
 * @return bool true always
 */
function apcu_clear_cache (): bool {}

/**
 * Retrieves cached information from APCu's data store
 * @link http://www.php.net/manual/en/function.apcu-cache-info.php
 * @param bool $limited [optional] If limited is true, the
 * return value will exclude the individual list of cache entries. This
 * is useful when trying to optimize calls for statistics gathering.
 * @return mixed Array of cached data (and meta-data) or false on failure
 */
function apcu_cache_info (bool $limited = null): array|false {}

/**
 * Get detailed information about the cache key
 * @link http://www.php.net/manual/en/function.apcu-key-info.php
 * @param string $key Get detailed information about the cache key
 * @return mixed An array containing the detailed information about the cache key, or null if the key does not exist.
 */
function apcu_key_info (string $key): ?array {}

/**
 * Retrieves APCu Shared Memory Allocation information
 * @link http://www.php.net/manual/en/function.apcu-sma-info.php
 * @param bool $limited [optional] When set to false (default) apcu_sma_info will
 * return a detailed information about each segment.
 * @return mixed Array of Shared Memory Allocation data; false on failure.
 */
function apcu_sma_info (bool $limited = null): array|false {}

/**
 * Whether APCu is usable in the current environment
 * @link http://www.php.net/manual/en/function.apcu-enabled.php
 * @return bool true when APCu is usable in the current environment, false otherwise.
 */
function apcu_enabled (): bool {}

/**
 * Cache a variable in the data store
 * @link http://www.php.net/manual/en/function.apcu-store.php
 * @param string $key Store the variable using this name. keys are
 * cache-unique, so storing a second value with the same
 * key will overwrite the original value.
 * @param mixed $var The variable to store
 * @param int $ttl [optional] Time To Live; store var in the cache for
 * ttl seconds. After the
 * ttl has passed, the stored variable will be
 * expunged from the cache (on the next request). If no ttl
 * is supplied (or if the ttl is
 * 0), the value will persist until it is removed from
 * the cache manually, or otherwise fails to exist in the cache (clear,
 * restart, etc.).
 * @return bool true on success or false on failure
 * Second syntax returns array with error keys.
 */
function apcu_store (string $key, $var, int $ttl = null): array|bool {}

/**
 * Cache a new variable in the data store
 * @link http://www.php.net/manual/en/function.apcu-add.php
 * @param string $key Store the variable using this name. keys are
 * cache-unique, so attempting to use apcu_add to
 * store data with a key that already exists will not overwrite the
 * existing data, and will instead return false. (This is the only
 * difference between apcu_add and
 * apcu_store.)
 * @param mixed $var The variable to store
 * @param int $ttl [optional] Time To Live; store var in the cache for
 * ttl seconds. After the
 * ttl has passed, the stored variable will be
 * expunged from the cache (on the next request). If no ttl
 * is supplied (or if the ttl is
 * 0), the value will persist until it is removed from
 * the cache manually, or otherwise fails to exist in the cache (clear,
 * restart, etc.).
 * @return bool TRUE if something has effectively been added into the cache, FALSE otherwise.
 * Second syntax returns array with error keys.
 */
function apcu_add (string $key, $var, int $ttl = null): array|bool {}

/**
 * Increase a stored number
 * @link http://www.php.net/manual/en/function.apcu-inc.php
 * @param string $key The key of the value being increased.
 * @param int $step [optional] The step, or value to increase.
 * @param bool $success [optional] Optionally pass the success or fail boolean value to
 * this referenced variable.
 * @param int $ttl [optional] TTL to use if the operation inserts a new value (rather than incrementing an existing one).
 * @return mixed the current value of key's value on success,
 * or false on failure
 */
function apcu_inc (string $key, int $step = null, bool &$success = null, int $ttl = null): int|false {}

/**
 * Decrease a stored number
 * @link http://www.php.net/manual/en/function.apcu-dec.php
 * @param string $key The key of the value being decreased.
 * @param int $step [optional] The step, or value to decrease.
 * @param bool $success [optional] Optionally pass the success or fail boolean value to
 * this referenced variable.
 * @param int $ttl [optional] TTL to use if the operation inserts a new value (rather than decrementing an existing one).
 * @return mixed the current value of key's value on success,
 * or false on failure
 */
function apcu_dec (string $key, int $step = null, bool &$success = null, int $ttl = null): int|false {}

/**
 * Updates an old value with a new value
 * @link http://www.php.net/manual/en/function.apcu-cas.php
 * @param string $key The key of the value being updated.
 * @param int $old The old value (the value currently stored).
 * @param int $new The new value to update to.
 * @return bool true on success or false on failure
 */
function apcu_cas (string $key, int $old, int $new): bool {}

/**
 * Fetch a stored variable from the cache
 * @link http://www.php.net/manual/en/function.apcu-fetch.php
 * @param mixed $key The key used to store the value (with
 * apcu_store). If an array is passed then each
 * element is fetched and returned.
 * @param bool $success [optional] Set to true in success and false in failure.
 * @return mixed The stored variable or array of variables on success; false on failure
 */
function apcu_fetch ($key, bool &$success = null): mixed {}

/**
 * Checks if entry exists
 * @link http://www.php.net/manual/en/function.apcu-exists.php
 * @param mixed $keys A string, or an array of strings, that
 * contain keys.
 * @return mixed true if the key exists, otherwise false Or if an
 * array was passed to keys, then
 * an array is returned that contains all existing keys, or an empty
 * array if none exist.
 */
function apcu_exists ($keys): array|bool {}

/**
 * Removes a stored variable from the cache
 * @link http://www.php.net/manual/en/function.apcu-delete.php
 * @param mixed $key A key used to store the value as a
 * string for a single key,
 * or as an array of strings for several keys,
 * or as an APCUIterator object.
 * @return mixed If key is an array, an indexed array of the keys is returned.
 * Otherwise true is returned on success, or false on failure.
 */
function apcu_delete ($key): array|bool {}

/**
 * Atomically fetch or generate a cache entry
 * @link http://www.php.net/manual/en/function.apcu-entry.php
 * @param string $key Identity of cache entry
 * @param callable $generator A callable that accepts key as the only argument and returns the value to cache.
 * @param int $ttl [optional] Time To Live; store var in the cache for
 * ttl seconds. After the
 * ttl has passed, the stored variable will be
 * expunged from the cache (on the next request). If no ttl
 * is supplied (or if the ttl is
 * 0), the value will persist until it is removed from
 * the cache manually, or otherwise fails to exist in the cache (clear,
 * restart, etc.).
 * @return mixed the cached value
 */
function apcu_entry (string $key, callable $generator, int $ttl = null): mixed {}


/**
 * 
 * @link http://www.php.net/manual/en/apcu.constants.php
 */
define ('APC_LIST_ACTIVE', 1);

/**
 * 
 * @link http://www.php.net/manual/en/apcu.constants.php
 */
define ('APC_LIST_DELETED', 2);

/**
 * 
 * @link http://www.php.net/manual/en/apcu.constants.php
 */
define ('APC_ITER_TYPE', 1);

/**
 * 
 * @link http://www.php.net/manual/en/apcu.constants.php
 */
define ('APC_ITER_KEY', 2);

/**
 * 
 * @link http://www.php.net/manual/en/apcu.constants.php
 */
define ('APC_ITER_VALUE', 4);

/**
 * 
 * @link http://www.php.net/manual/en/apcu.constants.php
 */
define ('APC_ITER_NUM_HITS', 8);

/**
 * 
 * @link http://www.php.net/manual/en/apcu.constants.php
 */
define ('APC_ITER_MTIME', 16);

/**
 * 
 * @link http://www.php.net/manual/en/apcu.constants.php
 */
define ('APC_ITER_CTIME', 32);

/**
 * 
 * @link http://www.php.net/manual/en/apcu.constants.php
 */
define ('APC_ITER_DTIME', 64);

/**
 * 
 * @link http://www.php.net/manual/en/apcu.constants.php
 */
define ('APC_ITER_ATIME', 128);

/**
 * 
 * @link http://www.php.net/manual/en/apcu.constants.php
 */
define ('APC_ITER_REFCOUNT', 256);

/**
 * 
 * @link http://www.php.net/manual/en/apcu.constants.php
 */
define ('APC_ITER_MEM_SIZE', 512);

/**
 * 
 * @link http://www.php.net/manual/en/apcu.constants.php
 */
define ('APC_ITER_TTL', 1024);

/**
 * 
 * @link http://www.php.net/manual/en/apcu.constants.php
 */
define ('APC_ITER_NONE', 0);

/**
 * 
 * @link http://www.php.net/manual/en/apcu.constants.php
 */
define ('APC_ITER_ALL', 4294967295);

// End of apcu v.5.1.22
