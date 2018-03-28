<?php

// Start of apc v.5.1.8

/**
 * The APCIterator class makes it easier to iterate
 * over large APC caches. This is helpful as it allows iterating over large
 * caches in steps, while grabbing a defined number of entries per lock instance,
 * so it frees the cache locks for other activities rather than hold up the
 * entire cache to grab 100 (the default) entries. Also, using regular expression
 * matching is more efficient as it's been moved to the C level.
 * @link http://www.php.net/manual/en/class.apciterator.php
 */
class APCIterator extends APCuIterator implements Traversable, Iterator {

	/**
	 * Constructs an APCIterator iterator object
	 * @link http://www.php.net/manual/en/apciterator.construct.php
	 * @param $ignored
	 * @param $search [optional]
	 * @param $format [optional]
	 * @param $chunk_size [optional]
	 * @param $list [optional]
	 */
	public function __construct ($ignored, $search = null, $format = null, $chunk_size = null, $list = null) {}

	/**
	 * Rewinds iterator
	 * @link http://www.php.net/manual/en/apcuiterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Get current item
	 * @link http://www.php.net/manual/en/apcuiterator.current.php
	 * @return mixed the current item on success, or false if no
	 * more items or exist, or on failure.
	 */
	public function current () {}

	/**
	 * Get iterator key
	 * @link http://www.php.net/manual/en/apcuiterator.key.php
	 * @return string the key on success, or false upon failure.
	 */
	public function key () {}

	/**
	 * Move pointer to next item
	 * @link http://www.php.net/manual/en/apcuiterator.next.php
	 * @return void true on success or false on failure
	 */
	public function next () {}

	/**
	 * Checks if current position is valid
	 * @link http://www.php.net/manual/en/apcuiterator.valid.php
	 * @return void true if the current iterator position is valid, otherwise false.
	 */
	public function valid () {}

	/**
	 * Get total cache hits
	 * @link http://www.php.net/manual/en/apcuiterator.gettotalhits.php
	 * @return int The number of hits on success, or false on failure.
	 */
	public function getTotalHits () {}

	/**
	 * Get total cache size
	 * @link http://www.php.net/manual/en/apcuiterator.gettotalsize.php
	 * @return int The total cache size.
	 */
	public function getTotalSize () {}

	/**
	 * Get total count
	 * @link http://www.php.net/manual/en/apcuiterator.gettotalcount.php
	 * @return int The total count.
	 */
	public function getTotalCount () {}

}

/**
 * Retrieves cached information from APC's data store
 * @link http://www.php.net/manual/en/function.apc-cache-info.php
 * @param string $cache_type [optional] <p>
 * If cache_type is "user",
 * information about the user cache will be returned.
 * </p>
 * <p> 
 * If cache_type is "filehits",
 * information about which files have been served from the bytecode cache 
 * for the current request will be returned. This feature must be enabled at
 * compile time using --enable-filehits.
 * </p>
 * <p>
 * If an invalid or no cache_type is specified, information about 
 * the system cache (cached files) will be returned.
 * </p>
 * @param bool $limited [optional] If limited is true, the
 * return value will exclude the individual list of cache entries. This
 * is useful when trying to optimize calls for statistics gathering.
 * @return array Array of cached data (and meta-data) or false on failure
 */
function apc_cache_info (string $cache_type = null, bool $limited = null) {}

/**
 * Clears the APC cache
 * @link http://www.php.net/manual/en/function.apc-clear-cache.php
 * @param string $cache_type [optional] If cache_type is "user", the
 * user cache will be cleared; otherwise, the system cache (cached files)
 * will be cleared.
 * @return bool true always
 */
function apc_clear_cache (string $cache_type = null) {}

/**
 * Cache a variable in the data store
 * @link http://www.php.net/manual/en/function.apc-store.php
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
function apc_store (string $key, $var, int $ttl = null) {}

/**
 * Fetch a stored variable from the cache
 * @link http://www.php.net/manual/en/function.apc-fetch.php
 * @param mixed $key The key used to store the value (with
 * apc_store). If an array is passed then each
 * element is fetched and returned.
 * @param bool $success [optional] Set to true in success and false in failure.
 * @return mixed The stored variable or array of variables on success; false on failure
 */
function apc_fetch ($key, bool &$success = null) {}

function apc_enabled () {}

/**
 * Removes a stored variable from the cache
 * @link http://www.php.net/manual/en/function.apc-delete.php
 * @param string $key The key used to store the value (with
 * apc_store).
 * @return mixed true on success or false on failure
 */
function apc_delete (string $key) {}

/**
 * Cache a new variable in the data store
 * @link http://www.php.net/manual/en/function.apc-add.php
 * @param string $key Store the variable using this name. keys are
 * cache-unique, so attempting to use apc_add to
 * store data with a key that already exists will not overwrite the
 * existing data, and will instead return false. (This is the only
 * difference between apc_add and
 * apc_store.)
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
function apc_add (string $key, $var, int $ttl = null) {}

/**
 * Retrieves APC's Shared Memory Allocation information
 * @link http://www.php.net/manual/en/function.apc-sma-info.php
 * @param bool $limited [optional] When set to false (default) apc_sma_info will
 * return a detailed information about each segment.
 * @return array Array of Shared Memory Allocation data; false on failure.
 */
function apc_sma_info (bool $limited = null) {}

/**
 * Increase a stored number
 * @link http://www.php.net/manual/en/function.apc-inc.php
 * @param string $key The key of the value being increased.
 * @param int $step [optional] The step, or value to increase.
 * @param bool $success [optional] Optionally pass the success or fail boolean value to
 * this referenced variable.
 * @return int the current value of key's value on success,
 * or false on failure
 */
function apc_inc (string $key, int $step = null, bool &$success = null) {}

/**
 * Decrease a stored number
 * @link http://www.php.net/manual/en/function.apc-dec.php
 * @param string $key The key of the value being decreased.
 * @param int $step [optional] The step, or value to decrease.
 * @param bool $success [optional] Optionally pass the success or fail boolean value to
 * this referenced variable.
 * @return int the current value of key's value on success,
 * or false on failure
 */
function apc_dec (string $key, int $step = null, bool &$success = null) {}

/**
 * Updates an old value with a new value
 * @link http://www.php.net/manual/en/function.apc-cas.php
 * @param string $key The key of the value being updated.
 * @param int $old The old value (the value currently stored).
 * @param int $new The new value to update to.
 * @return bool true on success or false on failure
 */
function apc_cas (string $key, int $old, int $new) {}

/**
 * Checks if APC key exists
 * @link http://www.php.net/manual/en/function.apc-exists.php
 * @param mixed $keys A string, or an array of strings, that
 * contain keys.
 * @return mixed true if the key exists, otherwise false Or if an
 * array was passed to keys, then
 * an array is returned that contains all existing keys, or an empty
 * array if none exist.
 */
function apc_exists ($keys) {}

// End of apc v.5.1.8
