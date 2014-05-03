<?php

// Start of memcache v.3.0.8

class MemcachePool  {

	public function connect () {}

	public function addserver () {}

	public function setserverparams () {}

	public function setfailurecallback () {}

	public function getserverstatus () {}

	public function findserver () {}

	public function getversion () {}

	public function add () {}

	public function set () {}

	public function replace () {}

	public function cas () {}

	public function append () {}

	public function prepend () {}

	public function get () {}

	public function delete () {}

	public function getstats () {}

	public function getextendedstats () {}

	public function setcompressthreshold () {}

	public function increment () {}

	public function decrement () {}

	public function close () {}

	public function flush () {}

}

class Memcache extends MemcachePool  {

	/**
	 * Open memcached server connection
	 * @link http://www.php.net/manual/en/memcache.connect.php
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
	 * <p>
	 * Please note: port defaults to
	 * memcache.default_port
	 * if not specified. For this reason it is wise to specify the port
	 * explicitly in this method call.
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
	 * @link http://www.php.net/manual/en/memcache.pconnect.php
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
	 * @return mixed a Memcache object or false on failure.
	 */
	public function pconnect ($host, $port = null, $timeout = null) {}

	/**
	 * Add a memcached server to connection pool
	 * @link http://www.php.net/manual/en/memcache.addserver.php
	 * @param host string <p>
	 * Point to the host where memcached is listening for connections. This parameter
	 * may also specify other transports like unix:///path/to/memcached.sock
	 * to use UNIX domain sockets, in this case port must also
	 * be set to 0.
	 * </p>
	 * @param port int[optional] <p>
	 * Point to the port where memcached is listening for connections.
	 * Set this
	 * parameter to 0 when using UNIX domain sockets.
	 * </p>
	 * <p>
	 * Please note: port defaults to
	 * memcache.default_port
	 * if not specified. For this reason it is wise to specify the port
	 * explicitly in this method call.
	 * </p>
	 * @param persistent bool[optional] <p>
	 * Controls the use of a persistent connection. Default to true.
	 * </p>
	 * @param weight int[optional] <p>
	 * Number of buckets to create for this server which in turn control its
	 * probability of it being selected. The probability is relative to the
	 * total weight of all servers.
	 * </p>
	 * @param timeout int[optional] <p>
	 * Value in seconds which will be used for connecting to the daemon. Think
	 * twice before changing the default value of 1 second - you can lose all
	 * the advantages of caching if your connection is too slow.
	 * </p>
	 * @param retry_interval int[optional] <p>
	 * Controls how often a failed server will be retried, the default value
	 * is 15 seconds. Setting this parameter to -1 disables automatic retry. 
	 * Neither this nor the persistent parameter has any 
	 * effect when the extension is loaded dynamically via dl.
	 * </p>
	 * <p>
	 * Each failed connection struct has its own timeout and before it has expired 
	 * the struct will be skipped when selecting backends to serve a request. Once 
	 * expired the connection will be successfully reconnected or marked as failed 
	 * for another retry_interval seconds. The typical 
	 * effect is that each web server child will retry the connection about every
	 * retry_interval seconds when serving a page.
	 * </p>
	 * @param status bool[optional] <p>
	 * Controls if the server should be flagged as online. Setting this parameter
	 * to false and retry_interval to -1 allows a failed 
	 * server to be kept in the pool so as not to affect the key distribution 
	 * algorithm. Requests for this server will then failover or fail immediately 
	 * depending on the memcache.allow_failover setting.
	 * Default to true, meaning the server should be considered online.
	 * </p>
	 * @param failure_callback callable[optional] <p>
	 * Allows the user to specify a callback function to run upon encountering an 
	 * error. The callback is run before failover is attempted. The function takes 
	 * two parameters, the hostname and port of the failed server.
	 * </p>
	 * @param timeoutms int[optional] <p>
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function addserver ($host, $port = null, $persistent = null, $weight = null, $timeout = null, $retry_interval = null, $status = null, $failure_callback = null, $timeoutms = null) {}

	/**
	 * Changes server parameters and status at runtime
	 * @link http://www.php.net/manual/en/memcache.setserverparams.php
	 * @param host string <p>
	 * Point to the host where memcached is listening for connections.
	 * </p>
	 * @param port int[optional] <p>
	 * Point to the port where memcached is listening for connections.
	 * </p>
	 * @param timeout int[optional] <p>
	 * Value in seconds which will be used for connecting to the daemon. Think
	 * twice before changing the default value of 1 second - you can lose all
	 * the advantages of caching if your connection is too slow.
	 * </p>
	 * @param retry_interval int[optional] <p>
	 * Controls how often a failed server will be retried, the default value
	 * is 15 seconds. Setting this parameter to -1 disables automatic retry. 
	 * Neither this nor the persistent parameter has any 
	 * effect when the extension is loaded dynamically via dl.
	 * </p>
	 * @param status bool[optional] <p>
	 * Controls if the server should be flagged as online. Setting this parameter
	 * to false and retry_interval to -1 allows a failed 
	 * server to be kept in the pool so as not to affect the key distribution 
	 * algoritm. Requests for this server will then failover or fail immediately 
	 * depending on the memcache.allow_failover setting.
	 * Default to true, meaning the server should be considered online.
	 * </p>
	 * @param failure_callback callable[optional] <p>
	 * Allows the user to specify a callback function to run upon encountering an 
	 * error. The callback is run before failover is attempted. The function takes 
	 * two parameters, the hostname and port of the failed server.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function setserverparams ($host, $port = null, $timeout = null, $retry_interval = null, $status = null, $failure_callback = null) {}

	/**
	 * Returns server status
	 * @link http://www.php.net/manual/en/memcache.getserverstatus.php
	 * @param host string <p>
	 * Point to the host where memcached is listening for connections.
	 * </p>
	 * @param port int[optional] <p>
	 * Point to the port where memcached is listening for connections.
	 * </p>
	 * @return int a the servers status. 0 if server is failed, non-zero otherwise
	 */
	public function getserverstatus ($host, $port = null) {}

	/**
	 * Return version of the server
	 * @link http://www.php.net/manual/en/memcache.getversion.php
	 * @return string a string of server version number or false on failure.
	 */
	public function getversion () {}

	/**
	 * Add an item to the server
	 * @link http://www.php.net/manual/en/memcache.add.php
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
	 * @link http://www.php.net/manual/en/memcache.set.php
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
	 * @link http://www.php.net/manual/en/memcache.replace.php
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
	 * @link http://www.php.net/manual/en/memcache.get.php
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
	 * an array of found key-value pairs when key is an array.
	 * Returns false on failure, key is not found or
	 * key is an empty array.
	 */
	public function get ($key, &$flags = null) {}

	/**
	 * Delete item from the server
	 * @link http://www.php.net/manual/en/memcache.delete.php
	 * @param key string <p>
	 * The key associated with the item to delete.
	 * </p>
	 * @param timeout int[optional] <p>
	 * This deprecated parameter is not supported, and defaults to 0 seconds.
	 * Do not use this parameter.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function delete ($key, $timeout = null) {}

	/**
	 * Get statistics of the server
	 * @link http://www.php.net/manual/en/memcache.getstats.php
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
	 * cachedump to limit the number of entries to dump.
	 * </p>
	 * @return array an associative array of server statistics or false on failure.
	 */
	public function getstats ($type = null, $slabid = null, $limit = null) {}

	/**
	 * Get statistics from all servers in pool
	 * @link http://www.php.net/manual/en/memcache.getextendedstats.php
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
	 * cachedump to limit the number of entries to dump.
	 * </p>
	 * @return array a two-dimensional associative array of server statistics or false
	 * on failure.
	 */
	public function getextendedstats ($type = null, $slabid = null, $limit = null) {}

	/**
	 * Enable automatic compression of large values
	 * @link http://www.php.net/manual/en/memcache.setcompressthreshold.php
	 * @param threshold int <p>
	 * Controls the minimum value length before attempting to compress automatically. 
	 * </p>
	 * @param min_savings float[optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setcompressthreshold ($threshold, $min_savings = null) {}

	/**
	 * Increment item's value
	 * @link http://www.php.net/manual/en/memcache.increment.php
	 * @param key string <p>
	 * Key of the item to increment.
	 * </p>
	 * @param value int[optional] <p>
	 * Increment the item by value.
	 * </p>
	 * @return int new items value on success  or false on failure.
	 */
	public function increment ($key, $value = null) {}

	/**
	 * Decrement item's value
	 * @link http://www.php.net/manual/en/memcache.decrement.php
	 * @param key string <p>
	 * Key of the item do decrement.
	 * </p>
	 * @param value int[optional] <p>
	 * Decrement the item by value.
	 * </p>
	 * @return int item's new value on success or false on failure.
	 */
	public function decrement ($key, $value = null) {}

	/**
	 * Close memcached server connection
	 * @link http://www.php.net/manual/en/memcache.close.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function close () {}

	/**
	 * Flush all existing items at the server
	 * @link http://www.php.net/manual/en/memcache.flush.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function flush () {}

}

function memcache_connect () {}

function memcache_pconnect () {}

function memcache_add_server () {}

function memcache_set_server_params () {}

function memcache_set_failure_callback () {}

function memcache_get_server_status () {}

function memcache_get_version () {}

function memcache_add () {}

function memcache_set () {}

function memcache_replace () {}

function memcache_cas () {}

function memcache_append () {}

function memcache_prepend () {}

function memcache_get () {}

function memcache_delete () {}

/**
 * Turn debug output on/off
 * @link http://www.php.net/manual/en/function.memcache-debug.php
 * @param on_off bool <p>
 * Turns debug output on if equals to true.
 * Turns debug output off if equals to false.
 * </p>
 * @return bool true if PHP was built with --enable-debug option, otherwise
 * returns false.
 */
function memcache_debug ($on_off) {}

function memcache_get_stats () {}

function memcache_get_extended_stats () {}

function memcache_set_compress_threshold () {}

function memcache_increment () {}

function memcache_decrement () {}

function memcache_close () {}

function memcache_flush () {}

define ('MEMCACHE_COMPRESSED', 2);
define ('MEMCACHE_USER1', 65536);
define ('MEMCACHE_USER2', 131072);
define ('MEMCACHE_USER3', 262144);
define ('MEMCACHE_USER4', 524288);
define ('MEMCACHE_HAVE_SESSION', 1);

// End of memcache v.3.0.8
