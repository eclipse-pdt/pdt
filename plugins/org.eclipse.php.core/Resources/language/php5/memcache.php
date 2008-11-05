<?php

// Start of memcache v.0.1

class Memcache  {

	/**
	 * Open memcached server connection
	 * @link http://php.net/manual/en/function.memcache-connect.php
	 * @param host string <p>
	 * Point to the host where memcached is listening for connections. This parameter
	 * may also specify other transports like unix:///path/to/memcached.sock
	 * to use UNIX domain sockets, in this case port must also
	 * be set to 0.
	 * </p>
	 * @param port int[optional] <p>
	 * Point to the port where memcached is listening for connections. Set this
	 * parameter to 0 when using UNIX domain sockets.
	 * </p>
	 * @param timeout int[optional] <p>
	 * Value in seconds which will be used for connecting to the daemon. Think
	 * twice before changing the default value of 1 second - you can lose all
	 * the advantages of caching if your connection is too slow.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function connect ($host, $port = null, $timeout = null) {}

	/**
	 * Open memcached server persistent connection
	 * @link http://php.net/manual/en/function.memcache-pconnect.php
	 * @param host string <p>
	 * Point to the host where memcached is listening for connections. This parameter
	 * may also specify other transports like unix:///path/to/memcached.sock
	 * to use UNIX domain sockets, in this case port must also
	 * be set to 0.
	 * </p>
	 * @param port int[optional] <p>
	 * Point to the port where memcached is listening for connections. Set this
	 * parameter to 0 when using UNIX domain sockets.
	 * </p>
	 * @param timeout int[optional] <p>
	 * Value in seconds which will be used for connecting to the daemon. Think
	 * twice before changing the default value of 1 second - you can lose all
	 * the advantages of caching if your connection is too slow.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function pconnect ($host, $port = null, $timeout = null) {}

	/**
	 * Return version of the server
	 * @link http://php.net/manual/en/function.memcache-getversion.php
	 * @return string a string of server version number or false on failure.
	 */
	public function getversion () {}

	/**
	 * Add an item to the server
	 * @link http://php.net/manual/en/function.memcache-add.php
	 * @param key string <p>
	 * The key that will be associated with the item.
	 * </p>
	 * @param var mixed <p>
	 * The variable to store. Strings and integers are stored as is, other
	 * types are stored serialized.
	 * </p>
	 * @param flag int[optional] <p>
	 * Use MEMCACHE_COMPRESSED to store the item
	 * compressed (uses zlib).
	 * </p>
	 * @param expire int[optional] <p>
	 * Expiration time of the item. If it's equal to zero, the item will never
	 * expire. You can also use Unix timestamp or a number of seconds starting
	 * from current time, but in the latter case the number of seconds may not
	 * exceed 2592000 (30 days).
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * Returns false if such key already exist. For the rest
	 * Memcache::add behaves similarly to
	 * Memcache::set.
	 */
	public function add ($key, $var, $flag = null, $expire = null) {}

	/**
	 * Store data at the server
	 * @link http://php.net/manual/en/function.memcache-set.php
	 * @param key string <p>
	 * The key that will be associated with the item.
	 * </p>
	 * @param var mixed <p>
	 * The variable to store. Strings and integers are stored as is, other
	 * types are stored serialized.
	 * </p>
	 * @param flag int[optional] <p>
	 * Use MEMCACHE_COMPRESSED to store the item
	 * compressed (uses zlib).
	 * </p>
	 * @param expire int[optional] <p>
	 * Expiration time of the item. If it's equal to zero, the item will never
	 * expire. You can also use Unix timestamp or a number of seconds starting
	 * from current time, but in the latter case the number of seconds may not
	 * exceed 2592000 (30 days).
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function set ($key, $var, $flag = null, $expire = null) {}

	/**
	 * Replace value of the existing item
	 * @link http://php.net/manual/en/function.memcache-replace.php
	 * @param key string <p>
	 * The key that will be associated with the item.
	 * </p>
	 * @param var mixed <p>
	 * The variable to store. Strings and integers are stored as is, other
	 * types are stored serialized.
	 * </p>
	 * @param flag int[optional] <p>
	 * Use MEMCACHE_COMPRESSED to store the item
	 * compressed (uses zlib).
	 * </p>
	 * @param expire int[optional] <p>
	 * Expiration time of the item. If it's equal to zero, the item will never
	 * expire. You can also use Unix timestamp or a number of seconds starting
	 * from current time, but in the latter case the number of seconds may not
	 * exceed 2592000 (30 days).
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function replace ($key, $var, $flag = null, $expire = null) {}

	/**
	 * Retrieve item from the server
	 * @link http://php.net/manual/en/function.memcache-get.php
	 * @param key string <p>
	 * The key or array of keys to fetch.
	 * </p>
	 * @param flags int[optional] <p>
	 * If present, flags fetched along with the values will be written to this parameter. These
	 * flags are the same as the ones given to for example Memcache::set.
	 * The lowest byte of the int is reserved for pecl/memcache internal usage (e.g. to indicate
	 * compression and serialization status).
	 * </p>
	 * @return string the string associated with the key or
	 * false on failure or if such key was not found.
	 */
	public function get ($key, &$flags = null) {}

	/**
	 * Delete item from the server
	 * @link http://php.net/manual/en/function.memcache-delete.php
	 * @param key string <p>
	 * The key associated with the item to delete.
	 * </p>
	 * @param timeout int[optional] <p>
	 * Execution time of the item. If it's equal to zero, the item will be
	 * deleted right away whereas if you set it to 30, the item will be
	 * deleted in 30 seconds. 
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function delete ($key, $timeout = null) {}

	/**
	 * Get statistics of the server
	 * @link http://php.net/manual/en/function.memcache-getstats.php
	 * @param type string[optional] <p>
	 * The type of statistics to fetch. Valid values are {reset, 
	 * malloc, maps, cachedump, slabs, items, sizes}. According to
	 * the memcached protocol spec these additional arguments "are 
	 * subject to change for the convenience of memcache developers".
	 * </p>
	 * @param slabid int[optional] <p>
	 * Used in conjunction with type set to 
	 * cachedump to identify the slab to dump from. The cachedump
	 * command ties up the server and is strictly to be used for 
	 * debugging purposes.
	 * </p>
	 * @param limit int[optional] <p>
	 * Used in conjunction with type set to 
	 * cachedump to limit the number of entries to dump. Default value
	 * is 100.
	 * </p>
	 * @return array an associative array of server statistics or false on failure.
	 */
	public function getstats ($type = null, $slabid = null, $limit = null) {}

	/**
	 * Increment item's value
	 * @link http://php.net/manual/en/function.memcache-increment.php
	 * @param key string <p>
	 * Key of the item to increment.
	 * </p>
	 * @param value int[optional] <p>
	 * Increment the item by value. Optional and defaults to 1.
	 * </p>
	 * @return int new item's value on success or false on failure.
	 */
	public function increment ($key, $value = null) {}

	/**
	 * Decrement item's value
	 * @link http://php.net/manual/en/function.memcache-decrement.php
	 * @param key string <p>
	 * Key of the item do decrement.
	 * </p>
	 * @param value int[optional] <p>
	 * Decrement the item by value. Optional and defaults to 1.
	 * </p>
	 * @return int item's new value on success or false on failure.
	 */
	public function decrement ($key, $value = null) {}

	/**
	 * Close memcached server connection
	 * @link http://php.net/manual/en/function.memcache-close.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function close () {}

	/**
	 * Flush all existing items at the server
	 * @link http://php.net/manual/en/function.memcache-flush.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function flush () {}

}

function memcache_connect () {}

function memcache_pconnect () {}

function memcache_get_version () {}

function memcache_add () {}

function memcache_set () {}

function memcache_replace () {}

function memcache_get () {}

function memcache_delete () {}

/**
 * Turn debug output on/off
 * @link http://php.net/manual/en/function.memcache-debug.php
 * @param on_off bool <p>
 * Turns debug output on if equals to true.
 * Turns debug output off if equals to false.
 * </p>
 * @return bool true if PHP was built with --enable-debug option, otherwise
 * returns false.
 */
function memcache_debug ($on_off) {}

function memcache_get_stats () {}

function memcache_increment () {}

function memcache_decrement () {}

function memcache_close () {}

function memcache_flush () {}

define ('MEMCACHE_COMPRESSED', 2);

// End of memcache v.0.1
?>
