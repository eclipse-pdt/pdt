<?php

// Start of Zend Data Cache v.

function zend_shm_cache_store () {}

function zend_shm_cache_fetch () {}

function zend_shm_cache_delete () {}

function zend_shm_cache_clear () {}

function zend_disk_cache_store () {}

function zend_disk_cache_fetch () {}

function zend_disk_cache_delete () {}

function zend_disk_cache_clear () {}

/**
 * Cache a variable in the data store
 * @link http://php.net/manual/en/function.apc-add.php
 * @param key string <p>
 * Store the variable using this name. keys are
 * cache-unique, so attempting to use apc_add to
 * store data with a key that already exists will not overwrite the
 * existing data, and will instead return false. (This is the only
 * difference between apc_add and
 * apc_store.)
 * </p>
 * @param var mixed <p>
 * The variable to store
 * </p>
 * @param ttl int[optional] <p>
 * Time To Live; store var in the cache for
 * ttl seconds. After the
 * ttl has passed, the stored variable will be
 * expunged from the cache (on the next request). If no ttl
 * is supplied (or if the ttl is
 * 0), the value will persist until it is removed from
 * the cache manually, or otherwise fails to exist in the cache (clear,
 * restart, etc.).
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function apc_add ($key, $var, $ttl = null) {}

/**
 * Cache a variable in the data store
 * @link http://php.net/manual/en/function.apc-store.php
 * @param key string <p>
 * Store the variable using this name. keys are
 * cache-unique, so storing a second value with the same
 * key will overwrite the original value.
 * </p>
 * @param var mixed <p>
 * The variable to store
 * </p>
 * @param ttl int[optional] <p>
 * Time To Live; store var in the cache for
 * ttl seconds. After the
 * ttl has passed, the stored variable will be
 * expunged from the cache (on the next request). If no ttl
 * is supplied (or if the ttl is
 * 0), the value will persist until it is removed from
 * the cache manually, or otherwise fails to exist in the cache (clear,
 * restart, etc.).
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function apc_store ($key, $var, $ttl = null) {}

/**
 * Fetch a stored variable from the cache
 * @link http://php.net/manual/en/function.apc-fetch.php
 * @param key string <p>
 * The key used to store the value (with
 * apc_store).
 * </p>
 * @param success bool[optional] <p>
 * Set to true in success and false in failure.
 * </p>
 * @return mixed The stored variable on success; false on failure
 */
function apc_fetch ($key, &$success = null) {}

/**
 * Removes a stored variable from the cache
 * @link http://php.net/manual/en/function.apc-delete.php
 * @param key string <p>
 * The key used to store the value (with
 * apc_store).
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function apc_delete ($key) {}

/**
 * Clears the APC cache
 * @link http://php.net/manual/en/function.apc-clear-cache.php
 * @param cache_type string[optional] <p>
 * If cache_type is "user", the
 * user cache will be cleared; otherwise, the system cache (cached files)
 * will be cleared.
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function apc_clear_cache ($cache_type = null) {}

// End of Zend Data Cache v.
?>
