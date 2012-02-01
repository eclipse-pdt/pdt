<?php

// Start of memcached v.2.0.0b2

class Memcached  {
	const OPT_COMPRESSION = -1001;
	const OPT_COMPRESSION_TYPE = -1004;
	const OPT_PREFIX_KEY = -1002;
	const OPT_SERIALIZER = -1003;
	const HAVE_IGBINARY = 0;
	const HAVE_JSON = 0;
	const HAVE_SESSION = 1;
	const HAVE_SASL = 1;
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
	const RES_AUTH_PROBLEM = 40;
	const RES_AUTH_FAILURE = 41;
	const RES_AUTH_CONTINUE = 42;
	const RES_PAYLOAD_FAILURE = -1001;
	const SERIALIZER_PHP = 1;
	const SERIALIZER_IGBINARY = 2;
	const SERIALIZER_JSON = 3;
	const SERIALIZER_JSON_ARRAY = 4;
	const COMPRESSION_FASTLZ = 2;
	const COMPRESSION_ZLIB = 1;
	const GET_PRESERVE_ORDER = 1;
	const GET_ERROR_RETURN_VALUE = false;


	/**
	 * Create a Memcached instance
	 * @link http://www.php.net/manual/en/memcached.construct.php
	 * @param persistent_id[optional]
	 * @param callback[optional]
	 */
	public function __construct ($persistent_id, $callback) {}

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
	 * @param key string <p>
	 * The key of the item to retrieve.
	 * </p>
	 * @param cache_cb callback[optional] <p>
	 * Read-through caching callback or &null;.
	 * </p>
	 * @param cas_token float[optional] <p>
	 * The variable to store the CAS token in.
	 * </p>
	 * @return mixed the value stored in the cache or false otherwise.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function get ($key, $cache_cb = null, &$cas_token = null) {}

	/**
	 * Retrieve an item from a specific server
	 * @link http://www.php.net/manual/en/memcached.getbykey.php
	 * @param server_key string <p>
	 * &memcached.parameter.server_key;
	 * </p>
	 * @param key string <p>
	 * The key of the item to fetch.
	 * </p>
	 * @param cache_cb callback[optional] <p>
	 * Read-through caching callback or &null;
	 * </p>
	 * @param cas_token float[optional] <p>
	 * The variable to store the CAS token in.
	 * </p>
	 * @return mixed the value stored in the cache or false otherwise.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function getByKey ($server_key, $key, $cache_cb = null, &$cas_token = null) {}

	/**
	 * Retrieve multiple items
	 * @link http://www.php.net/manual/en/memcached.getmulti.php
	 * @param keys array <p>
	 * Array of keys to retrieve.
	 * </p>
	 * @param cas_tokens array[optional] <p>
	 * The variable to store the CAS tokens for the found items.
	 * </p>
	 * @param flags int[optional] <p>
	 * The flags for the get operation.
	 * </p>
	 * @return mixed the array of found items&return.falseforfailure;.
	 * &memcached.result.getresultcode;
	 */
	public function getMulti (array $keys, array &$cas_tokens = null, $flags = null) {}

	/**
	 * Retrieve multiple items from a specific server
	 * @link http://www.php.net/manual/en/memcached.getmultibykey.php
	 * @param server_key string <p>
	 * &memcached.parameter.server_key;
	 * </p>
	 * @param keys array <p>
	 * Array of keys to retrieve.
	 * </p>
	 * @param cas_tokens string[optional] <p>
	 * The variable to store the CAS tokens for the found items.
	 * </p>
	 * @param flags int[optional] <p>
	 * The flags for the get operation.
	 * </p>
	 * @return array the array of found items&return.falseforfailure;.
	 * &memcached.result.getresultcode;
	 */
	public function getMultiByKey ($server_key, array $keys, &$cas_tokens = null, $flags = null) {}

	/**
	 * Request multiple items
	 * @link http://www.php.net/manual/en/memcached.getdelayed.php
	 * @param keys array <p>
	 * Array of keys to request.
	 * </p>
	 * @param with_cas bool[optional] <p>
	 * Whether to request CAS token values also.
	 * </p>
	 * @param value_cb callback[optional] <p>
	 * The result callback or &null;.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * &memcached.result.getresultcode;
	 */
	public function getDelayed (array $keys, $with_cas = null, $value_cb = null) {}

	/**
	 * Request multiple items from a specific server
	 * @link http://www.php.net/manual/en/memcached.getdelayedbykey.php
	 * @param server_key string <p>
	 * &memcached.parameter.server_key;
	 * </p>
	 * @param keys array <p>
	 * Array of keys to request.
	 * </p>
	 * @param with_cas bool[optional] <p>
	 * Whether to request CAS token values also.
	 * </p>
	 * @param value_cb callback[optional] <p>
	 * The result callback or &null;.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * &memcached.result.getresultcode;
	 */
	public function getDelayedByKey ($server_key, array $keys, $with_cas = null, $value_cb = null) {}

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
	 * @return array the results&return.falseforfailure;.
	 * &memcached.result.getresultcode;
	 */
	public function fetchAll () {}

	/**
	 * Store an item
	 * @link http://www.php.net/manual/en/memcached.set.php
	 * @param key string <p>
	 * &memcached.parameter.key;
	 * </p>
	 * @param value mixed <p>
	 * &memcached.parameter.value;
	 * </p>
	 * @param expiration int[optional] <p>
	 * &memcached.parameter.expiration;
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * &memcached.result.getresultcode;
	 */
	public function set ($key, $value, $expiration = null) {}

	/**
	 * Store an item on a specific server
	 * @link http://www.php.net/manual/en/memcached.setbykey.php
	 * @param server_key string <p>
	 * &memcached.parameter.server_key;
	 * </p>
	 * @param key string <p>
	 * &memcached.parameter.key;
	 * </p>
	 * @param value mixed <p>
	 * &memcached.parameter.value;
	 * </p>
	 * @param expiration int[optional] <p>
	 * &memcached.parameter.expiration;
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * &memcached.result.getresultcode;
	 */
	public function setByKey ($server_key, $key, $value, $expiration = null) {}

	/**
	 * Store multiple items
	 * @link http://www.php.net/manual/en/memcached.setmulti.php
	 * @param items array <p>
	 * &memcached.parameter.items;
	 * </p>
	 * @param expiration int[optional] <p>
	 * &memcached.parameter.expiration;
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * &memcached.result.getresultcode;
	 */
	public function setMulti (array $items, $expiration = null) {}

	/**
	 * Store multiple items on a specific server
	 * @link http://www.php.net/manual/en/memcached.setmultibykey.php
	 * @param server_key string <p>
	 * &memcached.parameter.server_key;
	 * </p>
	 * @param items array <p>
	 * &memcached.parameter.items;
	 * </p>
	 * @param expiration int[optional] <p>
	 * &memcached.parameter.expiration;
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * &memcached.result.getresultcode;
	 */
	public function setMultiByKey ($server_key, array $items, $expiration = null) {}

	/**
	 * Compare and swap an item
	 * @link http://www.php.net/manual/en/memcached.cas.php
	 * @param cas_token float <p>
	 * Unique value associated with the existing item. Generated by memcache.
	 * </p>
	 * @param key string <p>
	 * &memcached.parameter.key;
	 * </p>
	 * @param value mixed <p>
	 * &memcached.parameter.value;
	 * </p>
	 * @param expiration int[optional] <p>
	 * &memcached.parameter.expiration;
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_DATA_EXISTS if the item you are trying
	 * to store has been modified since you last fetched it.
	 */
	public function cas ($cas_token, $key, $value, $expiration = null) {}

	/**
	 * Compare and swap an item on a specific server
	 * @link http://www.php.net/manual/en/memcached.casbykey.php
	 * @param cas_token float <p>
	 * Unique value associated with the existing item. Generated by memcache.
	 * </p>
	 * @param server_key string <p>
	 * &memcached.parameter.server_key;
	 * </p>
	 * @param key string <p>
	 * &memcached.parameter.key;
	 * </p>
	 * @param value mixed <p>
	 * &memcached.parameter.value;
	 * </p>
	 * @param expiration int[optional] <p>
	 * &memcached.parameter.expiration;
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_DATA_EXISTS if the item you are trying
	 * to store has been modified since you last fetched it.
	 */
	public function casByKey ($cas_token, $server_key, $key, $value, $expiration = null) {}

	/**
	 * Add an item under a new key
	 * @link http://www.php.net/manual/en/memcached.add.php
	 * @param key string <p>
	 * &memcached.parameter.key;
	 * </p>
	 * @param value mixed <p>
	 * &memcached.parameter.value;
	 * </p>
	 * @param expiration int[optional] <p>
	 * &memcached.parameter.expiration;
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key already exists.
	 */
	public function add ($key, $value, $expiration = null) {}

	/**
	 * Add an item under a new key on a specific server
	 * @link http://www.php.net/manual/en/memcached.addbykey.php
	 * @param server_key string <p>
	 * &memcached.parameter.server_key;
	 * </p>
	 * @param key string <p>
	 * &memcached.parameter.key;
	 * </p>
	 * @param value mixed <p>
	 * &memcached.parameter.value;
	 * </p>
	 * @param expiration int[optional] <p>
	 * &memcached.parameter.expiration;
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key already exists.
	 */
	public function addByKey ($server_key, $key, $value, $expiration = null) {}

	/**
	 * Append data to an existing item
	 * @link http://www.php.net/manual/en/memcached.append.php
	 * @param key string <p>
	 * &memcached.parameter.key;
	 * </p>
	 * @param value string <p>
	 * The string to append.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function append ($key, $value) {}

	/**
	 * Append data to an existing item on a specific server
	 * @link http://www.php.net/manual/en/memcached.appendbykey.php
	 * @param server_key string <p>
	 * &memcached.parameter.server_key;
	 * </p>
	 * @param key string <p>
	 * &memcached.parameter.key;
	 * </p>
	 * @param value string <p>
	 * The string to append.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function appendByKey ($server_key, $key, $value) {}

	/**
	 * Prepend data to an existing item
	 * @link http://www.php.net/manual/en/memcached.prepend.php
	 * @param key string <p>
	 * The key of the item to prepend the data to.
	 * </p>
	 * @param value string <p>
	 * The string to prepend.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function prepend ($key, $value) {}

	/**
	 * Prepend data to an existing item on a specific server
	 * @link http://www.php.net/manual/en/memcached.prependbykey.php
	 * @param server_key string <p>
	 * &memcached.parameter.server_key;
	 * </p>
	 * @param key string <p>
	 * The key of the item to prepend the data to.
	 * </p>
	 * @param value string <p>
	 * The string to prepend.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function prependByKey ($server_key, $key, $value) {}

	/**
	 * Replace the item under an existing key
	 * @link http://www.php.net/manual/en/memcached.replace.php
	 * @param key string <p>
	 * &memcached.parameter.key;
	 * </p>
	 * @param value mixed <p>
	 * &memcached.parameter.value;
	 * </p>
	 * @param expiration int[optional] <p>
	 * &memcached.parameter.expiration;
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function replace ($key, $value, $expiration = null) {}

	/**
	 * Replace the item under an existing key on a specific server
	 * @link http://www.php.net/manual/en/memcached.replacebykey.php
	 * @param server_key string <p>
	 * &memcached.parameter.server_key;
	 * </p>
	 * @param key string <p>
	 * &memcached.parameter.key;
	 * </p>
	 * @param value mixed <p>
	 * &memcached.parameter.value;
	 * </p>
	 * @param expiration int[optional] <p>
	 * &memcached.parameter.expiration;
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function replaceByKey ($server_key, $key, $value, $expiration = null) {}

	/**
	 * Delete an item
	 * @link http://www.php.net/manual/en/memcached.delete.php
	 * @param key string <p>
	 * The key to be deleted.
	 * </p>
	 * @param time int[optional] <p>
	 * The amount of time the server will wait to delete the item.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function delete ($key, $time = null) {}

	/**
	 * @param keys
	 * @param time[optional]
	 */
	public function deleteMulti ($keys, $time) {}

	/**
	 * Delete an item from a specific server
	 * @link http://www.php.net/manual/en/memcached.deletebykey.php
	 * @param server_key string <p>
	 * &memcached.parameter.server_key;
	 * </p>
	 * @param key string <p>
	 * The key to be deleted.
	 * </p>
	 * @param time int[optional] <p>
	 * The amount of time the server will wait to delete the item.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function deleteByKey ($server_key, $key, $time = null) {}

	/**
	 * @param server_key
	 * @param keys
	 * @param time[optional]
	 */
	public function deleteMultiByKey ($server_key, $keys, $time) {}

	/**
	 * Increment numeric item's value
	 * @link http://www.php.net/manual/en/memcached.increment.php
	 * @param key string <p>
	 * The key of the item to increment.
	 * </p>
	 * @param offset int[optional] <p>
	 * The amount by which to increment the item's value.
	 * </p>
	 * @return int new item's value on success&return.falseforfailure;.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function increment ($key, $offset = null) {}

	/**
	 * Decrement numeric item's value
	 * @link http://www.php.net/manual/en/memcached.decrement.php
	 * @param key string <p>
	 * The key of the item to decrement.
	 * </p>
	 * @param offset int[optional] <p>
	 * The amount by which to decrement the item's value.
	 * </p>
	 * @return int item's new value on success&return.falseforfailure;.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function decrement ($key, $offset = null) {}

	/**
	 * @param server_key
	 * @param key
	 * @param offset[optional]
	 * @param initial_value[optional]
	 * @param expiry[optional]
	 */
	public function incrementByKey ($server_key, $key, $offset, $initial_value, $expiry) {}

	/**
	 * @param server_key
	 * @param key
	 * @param offset[optional]
	 * @param initial_value[optional]
	 * @param expiry[optional]
	 */
	public function decrementByKey ($server_key, $key, $offset, $initial_value, $expiry) {}

	/**
	 * Add a server to the server pool
	 * @link http://www.php.net/manual/en/memcached.addserver.php
	 * @param host string <p>
	 * The hostname of the memcache server. If the hostname is invalid, data-related
	 * operations will set 
	 * Memcached::RES_HOST_LOOKUP_FAILURE result code.
	 * </p>
	 * @param port int <p>
	 * The port on which memcache is running. Usually, this is
	 * 11211.
	 * </p>
	 * @param weight int[optional] <p>
	 * The weight of the server relative to the total weight of all the
	 * servers in the pool. This controls the probability of the server being
	 * selected for operations. This is used only with consistent distribution
	 * option and usually corresponds to the amount of memory available to
	 * memcache on that server.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function addServer ($host, $port, $weight = null) {}

	/**
	 * Add multiple servers to the server pool
	 * @link http://www.php.net/manual/en/memcached.addservers.php
	 * @param servers array 
	 * @return bool Returns true on success or false on failure.
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
	 * @param server_key string <p>
	 * &memcached.parameter.server_key;
	 * </p>
	 * @return array Returns true on success or false on failure.
	 * &memcached.result.getresultcode;
	 */
	public function getServerByKey ($server_key) {}

	/**
	 * Get server pool statistics
	 * @link http://www.php.net/manual/en/memcached.getstats.php
	 * @return array Array of server statistics, one entry per server.
	 */
	public function getStats () {}

	/**
	 * Get server pool version info
	 * @link http://www.php.net/manual/en/memcached.getversion.php
	 * @return array Array of server versions, one entry per server.
	 */
	public function getVersion () {}

	public function getAllKeys () {}

	/**
	 * Invalidate all items in the cache
	 * @link http://www.php.net/manual/en/memcached.flush.php
	 * @param delay int[optional] <p>
	 * Numer of seconds to wait before invalidating the items.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * &memcached.result.getresultcode;
	 */
	public function flush ($delay = null) {}

	/**
	 * Retrieve a Memcached option value
	 * @link http://www.php.net/manual/en/memcached.getoption.php
	 * @param option int <p>
	 * One of the Memcached::OPT_* constants.
	 * </p>
	 * @return mixed the value of the requested option, or false on
	 * error.
	 */
	public function getOption ($option) {}

	/**
	 * Set a Memcached option
	 * @link http://www.php.net/manual/en/memcached.setoption.php
	 * @param option int 
	 * @param value mixed 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setOption ($option, $value) {}

	/**
	 * @param options
	 */
	public function setOptions ($options) {}

	/**
	 * @param username
	 * @param password
	 */
	public function setSaslAuthData ($username, $password) {}

	public function isPersistent () {}

	public function isPristine () {}

}

class MemcachedException extends RuntimeException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 * @param previous[optional]
	 */
	public function __construct ($message, $code, $previous) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}
// End of memcached v.2.0.0b2
?>
