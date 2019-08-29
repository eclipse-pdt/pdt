<?php

// Start of memcached v.3.0.3

/**
 * Represents a connection to a set of memcached servers.
 * @link http://www.php.net/manual/en/class.memcached.php
 */
class Memcached  {
	const LIBMEMCACHED_VERSION_HEX = 16777240;
	const OPT_COMPRESSION = -1001;
	const OPT_COMPRESSION_TYPE = -1004;
	const OPT_PREFIX_KEY = -1002;
	const OPT_SERIALIZER = -1003;
	const OPT_USER_FLAGS = -1006;
	const OPT_STORE_RETRY_COUNT = -1005;
	const HAVE_IGBINARY = false;
	const HAVE_JSON = false;
	const HAVE_MSGPACK = false;
	const HAVE_SESSION = true;
	const HAVE_SASL = true;
	const OPT_HASH = 2;
	const HASH_DEFAULT = 0;
	const HASH_MD5 = 1;
	const HASH_CRC = 2;
	const HASH_FNV1_64 = 3;
	const HASH_FNV1A_64 = 4;
	const HASH_FNV1_32 = 5;
	const HASH_FNV1A_32 = 6;
	const HASH_HSIEH = 7;
	const HASH_MURMUR = 8;
	const OPT_DISTRIBUTION = 9;
	const DISTRIBUTION_MODULA = 0;
	const DISTRIBUTION_CONSISTENT = 1;
	const DISTRIBUTION_VIRTUAL_BUCKET = 6;
	const OPT_LIBKETAMA_COMPATIBLE = 16;
	const OPT_LIBKETAMA_HASH = 17;
	const OPT_TCP_KEEPALIVE = 32;
	const OPT_BUFFER_WRITES = 10;
	const OPT_BINARY_PROTOCOL = 18;
	const OPT_NO_BLOCK = 0;
	const OPT_TCP_NODELAY = 1;
	const OPT_SOCKET_SEND_SIZE = 4;
	const OPT_SOCKET_RECV_SIZE = 5;
	const OPT_CONNECT_TIMEOUT = 14;
	const OPT_RETRY_TIMEOUT = 15;
	const OPT_DEAD_TIMEOUT = 36;
	const OPT_SEND_TIMEOUT = 19;
	const OPT_RECV_TIMEOUT = 20;
	const OPT_POLL_TIMEOUT = 8;
	const OPT_CACHE_LOOKUPS = 6;
	const OPT_SERVER_FAILURE_LIMIT = 21;
	const OPT_AUTO_EJECT_HOSTS = 28;
	const OPT_HASH_WITH_PREFIX_KEY = 25;
	const OPT_NOREPLY = 26;
	const OPT_SORT_HOSTS = 12;
	const OPT_VERIFY_KEY = 13;
	const OPT_USE_UDP = 27;
	const OPT_NUMBER_OF_REPLICAS = 29;
	const OPT_RANDOMIZE_REPLICA_READ = 30;
	const OPT_REMOVE_FAILED_SERVERS = 35;
	const OPT_SERVER_TIMEOUT_LIMIT = 37;
	const RES_SUCCESS = 0;
	const RES_FAILURE = 1;
	const RES_HOST_LOOKUP_FAILURE = 2;
	const RES_UNKNOWN_READ_FAILURE = 7;
	const RES_PROTOCOL_ERROR = 8;
	const RES_CLIENT_ERROR = 9;
	const RES_SERVER_ERROR = 10;
	const RES_WRITE_FAILURE = 5;
	const RES_DATA_EXISTS = 12;
	const RES_NOTSTORED = 14;
	const RES_NOTFOUND = 16;
	const RES_PARTIAL_READ = 18;
	const RES_SOME_ERRORS = 19;
	const RES_NO_SERVERS = 20;
	const RES_END = 21;
	const RES_ERRNO = 26;
	const RES_BUFFERED = 32;
	const RES_TIMEOUT = 31;
	const RES_BAD_KEY_PROVIDED = 33;
	const RES_STORED = 15;
	const RES_DELETED = 22;
	const RES_STAT = 24;
	const RES_ITEM = 25;
	const RES_NOT_SUPPORTED = 28;
	const RES_FETCH_NOTFINISHED = 30;
	const RES_SERVER_MARKED_DEAD = 35;
	const RES_UNKNOWN_STAT_KEY = 36;
	const RES_INVALID_HOST_PROTOCOL = 34;
	const RES_MEMORY_ALLOCATION_FAILURE = 17;
	const RES_CONNECTION_SOCKET_CREATE_FAILURE = 11;
	const RES_E2BIG = 37;
	const RES_KEY_TOO_BIG = 39;
	const RES_SERVER_TEMPORARILY_DISABLED = 47;
	const RES_SERVER_MEMORY_ALLOCATION_FAILURE = 48;
	const RES_AUTH_PROBLEM = 40;
	const RES_AUTH_FAILURE = 41;
	const RES_AUTH_CONTINUE = 42;
	const RES_PAYLOAD_FAILURE = -1001;
	const SERIALIZER_PHP = 1;
	const SERIALIZER_IGBINARY = 2;
	const SERIALIZER_JSON = 3;
	const SERIALIZER_JSON_ARRAY = 4;
	const SERIALIZER_MSGPACK = 5;
	const COMPRESSION_FASTLZ = 2;
	const COMPRESSION_ZLIB = 1;
	const GET_PRESERVE_ORDER = 1;
	const GET_EXTENDED = 2;
	const GET_ERROR_RETURN_VALUE = false;


	/**
	 * Create a Memcached instance
	 * @link http://www.php.net/manual/en/memcached.construct.php
	 * @param mixed $persistent_id [optional]
	 * @param mixed $callback [optional]
	 */
	public function __construct ($persistent_id = null, $callback = null) {}

	/**
	 * Return the result code of the last operation
	 * @link http://www.php.net/manual/en/memcached.getresultcode.php
	 * @return int Result code of the last Memcached operation.
	 */
	public function getResultCode () {}

	/**
	 * Return the message describing the result of the last operation
	 * @link http://www.php.net/manual/en/memcached.getresultmessage.php
	 * @return string Message describing the result of the last Memcached operation.
	 */
	public function getResultMessage () {}

	/**
	 * Retrieve an item
	 * @link http://www.php.net/manual/en/memcached.get.php
	 * @param string $key The key of the item to retrieve.
	 * @param callable $cache_cb [optional] Read-through caching callback or null.
	 * @param int $flags [optional] Flags to control the returned result. When value of Memcached::GET_EXTENDED
	 * is given will return the CAS token.
	 * @return mixed the value stored in the cache or false otherwise.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function get (string $key, callable $cache_cb = null, int $flags = null) {}

	/**
	 * Retrieve an item from a specific server
	 * @link http://www.php.net/manual/en/memcached.getbykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param string $key The key of the item to fetch.
	 * @param callable $cache_cb [optional] Read-through caching callback or null
	 * @param int $flags [optional] Flags to control the returned result. When value of Memcached::GET_EXTENDED
	 * is given will return the CAS token.
	 * @return mixed the value stored in the cache or false otherwise.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function getByKey (string $server_key, string $key, callable $cache_cb = null, int $flags = null) {}

	/**
	 * Retrieve multiple items
	 * @link http://www.php.net/manual/en/memcached.getmulti.php
	 * @param array $keys Array of keys to retrieve.
	 * @param int $flags [optional] The flags for the get operation.
	 * @return mixed the array of found items or false on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function getMulti (array $keys, int $flags = null) {}

	/**
	 * Retrieve multiple items from a specific server
	 * @link http://www.php.net/manual/en/memcached.getmultibykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param array $keys Array of keys to retrieve.
	 * @param int $flags [optional] The flags for the get operation.
	 * @return array the array of found items or false on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function getMultiByKey (string $server_key, array $keys, int $flags = null) {}

	/**
	 * Request multiple items
	 * @link http://www.php.net/manual/en/memcached.getdelayed.php
	 * @param array $keys Array of keys to request.
	 * @param bool $with_cas [optional] Whether to request CAS token values also.
	 * @param callable $value_cb [optional] The result callback or null.
	 * @return bool true on success or false on failure
	 * Use Memcached::getResultCode if necessary.
	 */
	public function getDelayed (array $keys, bool $with_cas = null, callable $value_cb = null) {}

	/**
	 * Request multiple items from a specific server
	 * @link http://www.php.net/manual/en/memcached.getdelayedbykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param array $keys Array of keys to request.
	 * @param bool $with_cas [optional] Whether to request CAS token values also.
	 * @param callable $value_cb [optional] The result callback or null.
	 * @return bool true on success or false on failure
	 * Use Memcached::getResultCode if necessary.
	 */
	public function getDelayedByKey (string $server_key, array $keys, bool $with_cas = null, callable $value_cb = null) {}

	/**
	 * Fetch the next result
	 * @link http://www.php.net/manual/en/memcached.fetch.php
	 * @return array the next result or false otherwise.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_END if result set is exhausted.
	 */
	public function fetch () {}

	/**
	 * Fetch all the remaining results
	 * @link http://www.php.net/manual/en/memcached.fetchall.php
	 * @return array the results or false on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function fetchAll () {}

	/**
	 * Store an item
	 * @link http://www.php.net/manual/en/memcached.set.php
	 * @param string $key The key under which to store the value.
	 * @param mixed $value The value to store.
	 * @param int $expiration [optional] memcached.parameter.expiration
	 * @return bool true on success or false on failure
	 * Use Memcached::getResultCode if necessary.
	 */
	public function set (string $key, $value, int $expiration = null) {}

	/**
	 * Store an item on a specific server
	 * @link http://www.php.net/manual/en/memcached.setbykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param string $key The key under which to store the value.
	 * @param mixed $value The value to store.
	 * @param int $expiration [optional] memcached.parameter.expiration
	 * @return bool true on success or false on failure
	 * Use Memcached::getResultCode if necessary.
	 */
	public function setByKey (string $server_key, string $key, $value, int $expiration = null) {}

	/**
	 * Set a new expiration on an item
	 * @link http://www.php.net/manual/en/memcached.touch.php
	 * @param string $key The key under which to store the value.
	 * @param int $expiration memcached.parameter.expiration
	 * @return bool true on success or false on failure
	 * Use Memcached::getResultCode if necessary.
	 */
	public function touch (string $key, int $expiration) {}

	/**
	 * Set a new expiration on an item on a specific server
	 * @link http://www.php.net/manual/en/memcached.touchbykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param string $key The key under which to store the value.
	 * @param int $expiration memcached.parameter.expiration
	 * @return bool true on success or false on failure
	 * Use Memcached::getResultCode if necessary.
	 */
	public function touchByKey (string $server_key, string $key, int $expiration) {}

	/**
	 * Store multiple items
	 * @link http://www.php.net/manual/en/memcached.setmulti.php
	 * @param array $items An array of key/value pairs to store on the server.
	 * @param int $expiration [optional] memcached.parameter.expiration
	 * @return bool true on success or false on failure
	 * Use Memcached::getResultCode if necessary.
	 */
	public function setMulti (array $items, int $expiration = null) {}

	/**
	 * Store multiple items on a specific server
	 * @link http://www.php.net/manual/en/memcached.setmultibykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param array $items An array of key/value pairs to store on the server.
	 * @param int $expiration [optional] memcached.parameter.expiration
	 * @return bool true on success or false on failure
	 * Use Memcached::getResultCode if necessary.
	 */
	public function setMultiByKey (string $server_key, array $items, int $expiration = null) {}

	/**
	 * Compare and swap an item
	 * @link http://www.php.net/manual/en/memcached.cas.php
	 * @param float $cas_token Unique value associated with the existing item. Generated by memcache.
	 * @param string $key The key under which to store the value.
	 * @param mixed $value The value to store.
	 * @param int $expiration [optional] memcached.parameter.expiration
	 * @return bool true on success or false on failure
	 * The Memcached::getResultCode will return
	 * Memcached::RES_DATA_EXISTS if the item you are trying
	 * to store has been modified since you last fetched it.
	 */
	public function cas (float $cas_token, string $key, $value, int $expiration = null) {}

	/**
	 * Compare and swap an item on a specific server
	 * @link http://www.php.net/manual/en/memcached.casbykey.php
	 * @param float $cas_token Unique value associated with the existing item. Generated by memcache.
	 * @param string $server_key memcached.parameter.server_key
	 * @param string $key The key under which to store the value.
	 * @param mixed $value The value to store.
	 * @param int $expiration [optional] memcached.parameter.expiration
	 * @return bool true on success or false on failure
	 * The Memcached::getResultCode will return
	 * Memcached::RES_DATA_EXISTS if the item you are trying
	 * to store has been modified since you last fetched it.
	 */
	public function casByKey (float $cas_token, string $server_key, string $key, $value, int $expiration = null) {}

	/**
	 * Add an item under a new key
	 * @link http://www.php.net/manual/en/memcached.add.php
	 * @param string $key The key under which to store the value.
	 * @param mixed $value The value to store.
	 * @param int $expiration [optional] memcached.parameter.expiration
	 * @return bool true on success or false on failure
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key already exists.
	 */
	public function add (string $key, $value, int $expiration = null) {}

	/**
	 * Add an item under a new key on a specific server
	 * @link http://www.php.net/manual/en/memcached.addbykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param string $key The key under which to store the value.
	 * @param mixed $value The value to store.
	 * @param int $expiration [optional] memcached.parameter.expiration
	 * @return bool true on success or false on failure
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key already exists.
	 */
	public function addByKey (string $server_key, string $key, $value, int $expiration = null) {}

	/**
	 * Append data to an existing item
	 * @link http://www.php.net/manual/en/memcached.append.php
	 * @param string $key The key under which to store the value.
	 * @param string $value The string to append.
	 * @return bool true on success or false on failure
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function append (string $key, string $value) {}

	/**
	 * Append data to an existing item on a specific server
	 * @link http://www.php.net/manual/en/memcached.appendbykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param string $key The key under which to store the value.
	 * @param string $value The string to append.
	 * @return bool true on success or false on failure
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function appendByKey (string $server_key, string $key, string $value) {}

	/**
	 * Prepend data to an existing item
	 * @link http://www.php.net/manual/en/memcached.prepend.php
	 * @param string $key The key of the item to prepend the data to.
	 * @param string $value The string to prepend.
	 * @return bool true on success or false on failure
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function prepend (string $key, string $value) {}

	/**
	 * Prepend data to an existing item on a specific server
	 * @link http://www.php.net/manual/en/memcached.prependbykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param string $key The key of the item to prepend the data to.
	 * @param string $value The string to prepend.
	 * @return bool true on success or false on failure
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function prependByKey (string $server_key, string $key, string $value) {}

	/**
	 * Replace the item under an existing key
	 * @link http://www.php.net/manual/en/memcached.replace.php
	 * @param string $key The key under which to store the value.
	 * @param mixed $value The value to store.
	 * @param int $expiration [optional] memcached.parameter.expiration
	 * @return bool true on success or false on failure
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function replace (string $key, $value, int $expiration = null) {}

	/**
	 * Replace the item under an existing key on a specific server
	 * @link http://www.php.net/manual/en/memcached.replacebykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param string $key The key under which to store the value.
	 * @param mixed $value The value to store.
	 * @param int $expiration [optional] memcached.parameter.expiration
	 * @return bool true on success or false on failure
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function replaceByKey (string $server_key, string $key, $value, int $expiration = null) {}

	/**
	 * Delete an item
	 * @link http://www.php.net/manual/en/memcached.delete.php
	 * @param string $key The key to be deleted.
	 * @param int $time [optional] The amount of time the server will wait to delete the item.
	 * @return bool true on success or false on failure
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function delete (string $key, int $time = null) {}

	/**
	 * Delete multiple items
	 * @link http://www.php.net/manual/en/memcached.deletemulti.php
	 * @param array $keys The keys to be deleted.
	 * @param int $time [optional] The amount of time the server will wait to delete the items.
	 * @return array array indexed by keys and where values are indicating whether operation succeeded or not.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function deleteMulti (array $keys, int $time = null) {}

	/**
	 * Delete an item from a specific server
	 * @link http://www.php.net/manual/en/memcached.deletebykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param string $key The key to be deleted.
	 * @param int $time [optional] The amount of time the server will wait to delete the item.
	 * @return bool true on success or false on failure
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function deleteByKey (string $server_key, string $key, int $time = null) {}

	/**
	 * Delete multiple items from a specific server
	 * @link http://www.php.net/manual/en/memcached.deletemultibykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param array $keys The keys to be deleted.
	 * @param int $time [optional] The amount of time the server will wait to delete the items.
	 * @return bool true on success or false on failure
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function deleteMultiByKey (string $server_key, array $keys, int $time = null) {}

	/**
	 * Increment numeric item's value
	 * @link http://www.php.net/manual/en/memcached.increment.php
	 * @param string $key The key of the item to increment.
	 * @param int $offset [optional] The amount by which to increment the item's value.
	 * @param int $initial_value [optional] The value to set the item to if it doesn't currently exist.
	 * @param int $expiry [optional] The expiry time to set on the item.
	 * @return int new item's value on success or false on failure.
	 */
	public function increment (string $key, int $offset = null, int $initial_value = null, int $expiry = null) {}

	/**
	 * Decrement numeric item's value
	 * @link http://www.php.net/manual/en/memcached.decrement.php
	 * @param string $key The key of the item to decrement.
	 * @param int $offset [optional] The amount by which to decrement the item's value.
	 * @param int $initial_value [optional] The value to set the item to if it doesn't currently exist.
	 * @param int $expiry [optional] The expiry time to set on the item.
	 * @return int item's new value on success or false on failure.
	 */
	public function decrement (string $key, int $offset = null, int $initial_value = null, int $expiry = null) {}

	/**
	 * Increment numeric item's value, stored on a specific server
	 * @link http://www.php.net/manual/en/memcached.incrementbykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param string $key The key of the item to increment.
	 * @param int $offset [optional] The amount by which to increment the item's value.
	 * @param int $initial_value [optional] The value to set the item to if it doesn't currently exist.
	 * @param int $expiry [optional] The expiry time to set on the item.
	 * @return int new item's value on success or false on failure.
	 */
	public function incrementByKey (string $server_key, string $key, int $offset = null, int $initial_value = null, int $expiry = null) {}

	/**
	 * Decrement numeric item's value, stored on a specific server
	 * @link http://www.php.net/manual/en/memcached.decrementbykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param string $key The key of the item to decrement.
	 * @param int $offset [optional] The amount by which to decrement the item's value.
	 * @param int $initial_value [optional] The value to set the item to if it doesn't currently exist.
	 * @param int $expiry [optional] The expiry time to set on the item.
	 * @return int item's new value on success or false on failure.
	 */
	public function decrementByKey (string $server_key, string $key, int $offset = null, int $initial_value = null, int $expiry = null) {}

	/**
	 * Add a server to the server pool
	 * @link http://www.php.net/manual/en/memcached.addserver.php
	 * @param string $host The hostname of the memcache server. If the hostname is invalid,
	 * data-related operations will set 
	 * Memcached::RES_HOST_LOOKUP_FAILURE result code. As
	 * of version 2.0.0b1, this parameter may also specify the path of a unix
	 * socket filepath ex. /path/to/memcached.sock
	 * to use UNIX domain sockets, in this case port
	 * must also be set to 0.
	 * @param int $port The port on which memcache is running. Usually, this is
	 * 11211. As of version 2.0.0b1, set this parameter to 0 when 
	 * using UNIX domain sockets.
	 * @param int $weight [optional] The weight of the server relative to the total weight of all the
	 * servers in the pool. This controls the probability of the server being
	 * selected for operations. This is used only with consistent distribution
	 * option and usually corresponds to the amount of memory available to
	 * memcache on that server.
	 * @return bool true on success or false on failure
	 */
	public function addServer (string $host, int $port, int $weight = null) {}

	/**
	 * Add multiple servers to the server pool
	 * @link http://www.php.net/manual/en/memcached.addservers.php
	 * @param array $servers 
	 * @return bool true on success or false on failure
	 */
	public function addServers (array $servers) {}

	/**
	 * Get the list of the servers in the pool
	 * @link http://www.php.net/manual/en/memcached.getserverlist.php
	 * @return array The list of all servers in the server pool.
	 */
	public function getServerList () {}

	/**
	 * Map a key to a server
	 * @link http://www.php.net/manual/en/memcached.getserverbykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @return array an array containing three keys of host,
	 * port, and weight on success or false
	 * on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function getServerByKey (string $server_key) {}

	/**
	 * Clears all servers from the server list
	 * @link http://www.php.net/manual/en/memcached.resetserverlist.php
	 * @return bool true on success or false on failure
	 */
	public function resetServerList () {}

	/**
	 * Close any open connections
	 * @link http://www.php.net/manual/en/memcached.quit.php
	 * @return bool true on success or false on failure
	 */
	public function quit () {}

	public function flushBuffers () {}

	public function getLastErrorMessage () {}

	public function getLastErrorCode () {}

	public function getLastErrorErrno () {}

	public function getLastDisconnectedServer () {}

	/**
	 * Get server pool statistics
	 * @link http://www.php.net/manual/en/memcached.getstats.php
	 * @param $args
	 * @return array Array of server statistics, one entry per server.
	 */
	public function getStats ($args) {}

	/**
	 * Get server pool version info
	 * @link http://www.php.net/manual/en/memcached.getversion.php
	 * @return array Array of server versions, one entry per server.
	 */
	public function getVersion () {}

	/**
	 * Gets the keys stored on all the servers
	 * @link http://www.php.net/manual/en/memcached.getallkeys.php
	 * @return array the keys stored on all the servers on success or false on failure.
	 */
	public function getAllKeys () {}

	/**
	 * Invalidate all items in the cache
	 * @link http://www.php.net/manual/en/memcached.flush.php
	 * @param int $delay [optional] Number of seconds to wait before invalidating the items.
	 * @return bool true on success or false on failure
	 * Use Memcached::getResultCode if necessary.
	 */
	public function flush (int $delay = null) {}

	/**
	 * Retrieve a Memcached option value
	 * @link http://www.php.net/manual/en/memcached.getoption.php
	 * @param int $option One of the Memcached::OPT_&#42; constants.
	 * @return mixed the value of the requested option, or false on
	 * error.
	 */
	public function getOption (int $option) {}

	/**
	 * Set a Memcached option
	 * @link http://www.php.net/manual/en/memcached.setoption.php
	 * @param int $option 
	 * @param mixed $value 
	 * @return bool true on success or false on failure
	 */
	public function setOption (int $option, $value) {}

	/**
	 * Set Memcached options
	 * @link http://www.php.net/manual/en/memcached.setoptions.php
	 * @param array $options An associative array of options where the key is the option to set and
	 * the value is the new value for the option.
	 * @return bool true on success or false on failure
	 */
	public function setOptions (array $options) {}

	/**
	 * @param $host_map
	 * @param $forward_map
	 * @param $replicas
	 */
	public function setBucket ($host_map, $forward_map, $replicas) {}

	/**
	 * Set the credentials to use for authentication
	 * @link http://www.php.net/manual/en/memcached.setsaslauthdata.php
	 * @param string $username The username to use for authentication.
	 * @param string $password The password to use for authentication.
	 * @return void 
	 */
	public function setSaslAuthData (string $username, string $password) {}

	/**
	 * Check if a persitent connection to memcache is being used
	 * @link http://www.php.net/manual/en/memcached.ispersistent.php
	 * @return bool true if Memcache instance uses a persistent connection, false otherwise.
	 */
	public function isPersistent () {}

	/**
	 * Check if the instance was recently created
	 * @link http://www.php.net/manual/en/memcached.ispristine.php
	 * @return bool the true if instance is recently created, false otherwise.
	 */
	public function isPristine () {}

}

/**
 * @link http://www.php.net/manual/en/class.memcachedexception.php
 */
class MemcachedException extends RuntimeException implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param mixed $message [optional]
	 * @param mixed $code [optional]
	 * @param mixed $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}
// End of memcached v.3.0.3
