<?php

// Start of memcached v.3.2.0

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
	const HAVE_IGBINARY = true;
	const HAVE_JSON = true;
	const HAVE_MSGPACK = true;
	const HAVE_ENCODING = true;
	const HAVE_SESSION = true;
	const HAVE_SASL = false;
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
	const RES_CONNECTION_FAILURE = 3;
	const RES_CONNECTION_BIND_FAILURE = 4;
	const RES_WRITE_FAILURE = 5;
	const RES_READ_FAILURE = 6;
	const RES_UNKNOWN_READ_FAILURE = 7;
	const RES_PROTOCOL_ERROR = 8;
	const RES_CLIENT_ERROR = 9;
	const RES_SERVER_ERROR = 10;
	const RES_DATA_EXISTS = 12;
	const RES_DATA_DOES_NOT_EXIST = 13;
	const RES_NOTSTORED = 14;
	const RES_STORED = 15;
	const RES_NOTFOUND = 16;
	const RES_PARTIAL_READ = 18;
	const RES_SOME_ERRORS = 19;
	const RES_NO_SERVERS = 20;
	const RES_END = 21;
	const RES_DELETED = 22;
	const RES_VALUE = 23;
	const RES_STAT = 24;
	const RES_ITEM = 25;
	const RES_ERRNO = 26;
	const RES_FAIL_UNIX_SOCKET = 27;
	const RES_NOT_SUPPORTED = 28;
	const RES_NO_KEY_PROVIDED = 29;
	const RES_FETCH_NOTFINISHED = 30;
	const RES_TIMEOUT = 31;
	const RES_BUFFERED = 32;
	const RES_BAD_KEY_PROVIDED = 33;
	const RES_INVALID_HOST_PROTOCOL = 34;
	const RES_SERVER_MARKED_DEAD = 35;
	const RES_UNKNOWN_STAT_KEY = 36;
	const RES_INVALID_ARGUMENTS = 38;
	const RES_PARSE_ERROR = 43;
	const RES_PARSE_USER_ERROR = 44;
	const RES_DEPRECATED = 45;
	const RES_IN_PROGRESS = 46;
	const RES_MAXIMUM_RETURN = 49;
	const RES_MEMORY_ALLOCATION_FAILURE = 17;
	const RES_CONNECTION_SOCKET_CREATE_FAILURE = 11;
	const RES_E2BIG = 37;
	const RES_KEY_TOO_BIG = 39;
	const RES_SERVER_TEMPORARILY_DISABLED = 47;
	const RES_SERVER_MEMORY_ALLOCATION_FAILURE = 48;
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
	 * @param string|null $persistent_id [optional]
	 * @param callable|null $callback [optional]
	 * @param string|null $connection_str [optional]
	 */
	public function __construct (string|null $persistent_id = null, callable|null $callback = null, string|null $connection_str = null) {}

	/**
	 * Return the result code of the last operation
	 * @link http://www.php.net/manual/en/memcached.getresultcode.php
	 * @return int Result code of the last Memcached operation.
	 */
	public function getResultCode (): int {}

	/**
	 * Return the message describing the result of the last operation
	 * @link http://www.php.net/manual/en/memcached.getresultmessage.php
	 * @return string Message describing the result of the last Memcached operation.
	 */
	public function getResultMessage (): string {}

	/**
	 * Retrieve an item
	 * @link http://www.php.net/manual/en/memcached.get.php
	 * @param string $key The key of the item to retrieve.
	 * @param callable $cache_cb [optional] Read-through caching callback or null.
	 * @param int $flags [optional] Flags to control the returned result. When Memcached::GET_EXTENDED
	 * is given, the function will also return the CAS token.
	 * @return mixed the value stored in the cache or false otherwise.
	 * If the flags is set to Memcached::GET_EXTENDED,
	 * an array containing the value and the CAS token is returned instead of only the value.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function get (string $key, callable $cache_cb = null, int $flags = null): mixed {}

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
	public function getByKey (string $server_key, string $key, callable $cache_cb = null, int $flags = null): mixed {}

	/**
	 * Retrieve multiple items
	 * @link http://www.php.net/manual/en/memcached.getmulti.php
	 * @param array $keys Array of keys to retrieve.
	 * @param int $flags [optional] The flags for the get operation.
	 * @return mixed the array of found items or false on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function getMulti (array $keys, int $flags = null): array|false {}

	/**
	 * Retrieve multiple items from a specific server
	 * @link http://www.php.net/manual/en/memcached.getmultibykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param array $keys Array of keys to retrieve.
	 * @param int $flags [optional] The flags for the get operation.
	 * @return mixed the array of found items or false on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function getMultiByKey (string $server_key, array $keys, int $flags = null): array|false {}

	/**
	 * Request multiple items
	 * @link http://www.php.net/manual/en/memcached.getdelayed.php
	 * @param array $keys Array of keys to request.
	 * @param bool $with_cas [optional] Whether to request CAS token values also.
	 * @param callable $value_cb [optional] The result callback or null.
	 * @return bool true on success or false on failure
	 * Use Memcached::getResultCode if necessary.
	 */
	public function getDelayed (array $keys, bool $with_cas = null, callable $value_cb = null): bool {}

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
	public function getDelayedByKey (string $server_key, array $keys, bool $with_cas = null, callable $value_cb = null): bool {}

	/**
	 * Fetch the next result
	 * @link http://www.php.net/manual/en/memcached.fetch.php
	 * @return array the next result or false otherwise.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_END if result set is exhausted.
	 */
	public function fetch (): array|false {}

	/**
	 * Fetch all the remaining results
	 * @link http://www.php.net/manual/en/memcached.fetchall.php
	 * @return mixed the results or false on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function fetchAll (): array|false {}

	/**
	 * Store an item
	 * @link http://www.php.net/manual/en/memcached.set.php
	 * @param string $key The key under which to store the value.
	 * @param mixed $value The value to store.
	 * @param int $expiration [optional] memcached.parameter.expiration
	 * @return bool true on success or false on failure
	 * Use Memcached::getResultCode if necessary.
	 */
	public function set (string $key, $value, int $expiration = null): bool {}

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
	public function setByKey (string $server_key, string $key, $value, int $expiration = null): bool {}

	/**
	 * Set a new expiration on an item
	 * @link http://www.php.net/manual/en/memcached.touch.php
	 * @param string $key The key under which to store the value.
	 * @param int $expiration memcached.parameter.expiration
	 * @return bool true on success or false on failure
	 * Use Memcached::getResultCode if necessary.
	 */
	public function touch (string $key, int $expiration): bool {}

	/**
	 * Set a new expiration on an item on a specific server
	 * @link http://www.php.net/manual/en/memcached.touchbykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param string $key The key under which to store the value.
	 * @param int $expiration memcached.parameter.expiration
	 * @return bool true on success or false on failure
	 * Use Memcached::getResultCode if necessary.
	 */
	public function touchByKey (string $server_key, string $key, int $expiration): bool {}

	/**
	 * Store multiple items
	 * @link http://www.php.net/manual/en/memcached.setmulti.php
	 * @param array $items An array of key/value pairs to store on the server.
	 * @param int $expiration [optional] memcached.parameter.expiration
	 * @return bool true on success or false on failure
	 * Use Memcached::getResultCode if necessary.
	 */
	public function setMulti (array $items, int $expiration = null): bool {}

	/**
	 * Store multiple items on a specific server
	 * @link http://www.php.net/manual/en/memcached.setmultibykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param array $items An array of key/value pairs to store on the server.
	 * @param int $expiration [optional] memcached.parameter.expiration
	 * @return bool true on success or false on failure
	 * Use Memcached::getResultCode if necessary.
	 */
	public function setMultiByKey (string $server_key, array $items, int $expiration = null): bool {}

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
	public function cas (float $cas_token, string $key, $value, int $expiration = null): bool {}

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
	public function casByKey (float $cas_token, string $server_key, string $key, $value, int $expiration = null): bool {}

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
	public function add (string $key, $value, int $expiration = null): bool {}

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
	public function addByKey (string $server_key, string $key, $value, int $expiration = null): bool {}

	/**
	 * Append data to an existing item
	 * @link http://www.php.net/manual/en/memcached.append.php
	 * @param string $key The key under which to store the value.
	 * @param string $value The string to append.
	 * @return bool true on success or false on failure
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function append (string $key, string $value): ?bool {}

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
	public function appendByKey (string $server_key, string $key, string $value): ?bool {}

	/**
	 * Prepend data to an existing item
	 * @link http://www.php.net/manual/en/memcached.prepend.php
	 * @param string $key The key of the item to prepend the data to.
	 * @param string $value The string to prepend.
	 * @return bool true on success or false on failure
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function prepend (string $key, string $value): ?bool {}

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
	public function prependByKey (string $server_key, string $key, string $value): ?bool {}

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
	public function replace (string $key, $value, int $expiration = null): bool {}

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
	public function replaceByKey (string $server_key, string $key, $value, int $expiration = null): bool {}

	/**
	 * Delete an item
	 * @link http://www.php.net/manual/en/memcached.delete.php
	 * @param string $key The key to be deleted.
	 * @param int $time [optional] <p>
	 * The amount of time the server will wait to delete the item.
	 * </p>
	 * memcached.note.delete-time
	 * @return bool true on success or false on failure
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function delete (string $key, int $time = null): bool {}

	/**
	 * Delete multiple items
	 * @link http://www.php.net/manual/en/memcached.deletemulti.php
	 * @param array $keys The keys to be deleted.
	 * @param int $time [optional] <p>
	 * The amount of time the server will wait to delete the items.
	 * </p>
	 * memcached.note.delete-time
	 * @return array 
	 */
	public function deleteMulti (array $keys, int $time = null): array {}

	/**
	 * Delete an item from a specific server
	 * @link http://www.php.net/manual/en/memcached.deletebykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param string $key The key to be deleted.
	 * @param int $time [optional] <p>
	 * The amount of time the server will wait to delete the item.
	 * </p>
	 * memcached.note.delete-time
	 * @return bool true on success or false on failure
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function deleteByKey (string $server_key, string $key, int $time = null): bool {}

	/**
	 * Delete multiple items from a specific server
	 * @link http://www.php.net/manual/en/memcached.deletemultibykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param array $keys The keys to be deleted.
	 * @param int $time [optional] <p>
	 * The amount of time the server will wait to delete the items.
	 * </p>
	 * memcached.note.delete-time
	 * @return bool 
	 */
	public function deleteMultiByKey (string $server_key, array $keys, int $time = null): array {}

	/**
	 * Increment numeric item's value
	 * @link http://www.php.net/manual/en/memcached.increment.php
	 * @param string $key The key of the item to increment.
	 * @param int $offset [optional] The amount by which to increment the item's value.
	 * @param int $initial_value [optional] The value to set the item to if it doesn't currently exist.
	 * @param int $expiry [optional] The expiry time to set on the item.
	 * @return mixed new item's value on success or false on failure.
	 */
	public function increment (string $key, int $offset = null, int $initial_value = null, int $expiry = null): int|false {}

	/**
	 * Decrement numeric item's value
	 * @link http://www.php.net/manual/en/memcached.decrement.php
	 * @param string $key The key of the item to decrement.
	 * @param int $offset [optional] The amount by which to decrement the item's value.
	 * @param int $initial_value [optional] The value to set the item to if it doesn't currently exist.
	 * @param int $expiry [optional] The expiry time to set on the item.
	 * @return mixed item's new value on success or false on failure.
	 */
	public function decrement (string $key, int $offset = null, int $initial_value = null, int $expiry = null): int|false {}

	/**
	 * Increment numeric item's value, stored on a specific server
	 * @link http://www.php.net/manual/en/memcached.incrementbykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param string $key The key of the item to increment.
	 * @param int $offset [optional] The amount by which to increment the item's value.
	 * @param int $initial_value [optional] The value to set the item to if it doesn't currently exist.
	 * @param int $expiry [optional] The expiry time to set on the item.
	 * @return mixed new item's value on success or false on failure.
	 */
	public function incrementByKey (string $server_key, string $key, int $offset = null, int $initial_value = null, int $expiry = null): int|false {}

	/**
	 * Decrement numeric item's value, stored on a specific server
	 * @link http://www.php.net/manual/en/memcached.decrementbykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @param string $key The key of the item to decrement.
	 * @param int $offset [optional] The amount by which to decrement the item's value.
	 * @param int $initial_value [optional] The value to set the item to if it doesn't currently exist.
	 * @param int $expiry [optional] The expiry time to set on the item.
	 * @return mixed item's new value on success or false on failure.
	 */
	public function decrementByKey (string $server_key, string $key, int $offset = null, int $initial_value = null, int $expiry = null): int|false {}

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
	public function addServer (string $host, int $port, int $weight = null): bool {}

	/**
	 * Add multiple servers to the server pool
	 * @link http://www.php.net/manual/en/memcached.addservers.php
	 * @param array $servers 
	 * @return bool true on success or false on failure
	 */
	public function addServers (array $servers): bool {}

	/**
	 * Get the list of the servers in the pool
	 * @link http://www.php.net/manual/en/memcached.getserverlist.php
	 * @return array The list of all servers in the server pool.
	 */
	public function getServerList (): array {}

	/**
	 * Map a key to a server
	 * @link http://www.php.net/manual/en/memcached.getserverbykey.php
	 * @param string $server_key memcached.parameter.server_key
	 * @return array an array containing three keys of host,
	 * port, and weight on success or false
	 * on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function getServerByKey (string $server_key): array|false {}

	/**
	 * Clears all servers from the server list
	 * @link http://www.php.net/manual/en/memcached.resetserverlist.php
	 * @return bool true on success or false on failure
	 */
	public function resetServerList (): bool {}

	/**
	 * Close any open connections
	 * @link http://www.php.net/manual/en/memcached.quit.php
	 * @return bool true on success or false on failure
	 */
	public function quit (): bool {}

	public function flushBuffers (): bool {}

	public function getLastErrorMessage (): string {}

	public function getLastErrorCode (): int {}

	public function getLastErrorErrno (): int {}

	public function getLastDisconnectedServer (): array|false {}

	/**
	 * Get server pool statistics
	 * @link http://www.php.net/manual/en/memcached.getstats.php
	 * @param string|null $type [optional]
	 * @return mixed Array of server statistics, one entry per server, or false on failure.
	 */
	public function getStats (string|null $type = null): array|false {}

	/**
	 * Get server pool version info
	 * @link http://www.php.net/manual/en/memcached.getversion.php
	 * @return array Array of server versions, one entry per server.
	 */
	public function getVersion (): array|false {}

	/**
	 * Gets the keys stored on all the servers
	 * @link http://www.php.net/manual/en/memcached.getallkeys.php
	 * @return mixed the keys stored on all the servers on success or false on failure.
	 */
	public function getAllKeys (): array|false {}

	/**
	 * Invalidate all items in the cache
	 * @link http://www.php.net/manual/en/memcached.flush.php
	 * @param int $delay [optional] Number of seconds to wait before invalidating the items.
	 * @return bool true on success or false on failure
	 * Use Memcached::getResultCode if necessary.
	 */
	public function flush (int $delay = null): bool {}

	/**
	 * Retrieve a Memcached option value
	 * @link http://www.php.net/manual/en/memcached.getoption.php
	 * @param int $option One of the Memcached::OPT_&#42; constants.
	 * @return mixed the value of the requested option, or false on
	 * error.
	 */
	public function getOption (int $option): mixed {}

	/**
	 * Set a Memcached option
	 * @link http://www.php.net/manual/en/memcached.setoption.php
	 * @param int $option One of the Memcached::OPT_&#42; constant.
	 * See Memcached Constants for more information.
	 * @param mixed $value <p>
	 * The value to be set.
	 * </p>
	 * <p>
	 * The options listed below require values specified via constants.
	 * <p>
	 * Memcached::OPT_HASH requires Memcached::HASH_&#42; values.
	 * Memcached::OPT_DISTRIBUTION requires Memcached::DISTRIBUTION_&#42; values.
	 * </p>
	 * </p>
	 * @return bool true on success or false on failure
	 */
	public function setOption (int $option, $value): bool {}

	/**
	 * Set Memcached options
	 * @link http://www.php.net/manual/en/memcached.setoptions.php
	 * @param array $options An associative array of options where the key is the option to set and
	 * the value is the new value for the option.
	 * @return bool true on success or false on failure
	 */
	public function setOptions (array $options): bool {}

	/**
	 * @param array[] $host_map
	 * @param array|null[] $forward_map
	 * @param int $replicas
	 */
	public function setBucket (array $host_map, array $forward_map = null, int $replicas): bool {}

	/**
	 * @param string $key
	 */
	public function setEncodingKey (string $key): bool {}

	/**
	 * Check if a persitent connection to memcache is being used
	 * @link http://www.php.net/manual/en/memcached.ispersistent.php
	 * @return bool true if Memcache instance uses a persistent connection, false otherwise.
	 */
	public function isPersistent (): bool {}

	/**
	 * Check if the instance was recently created
	 * @link http://www.php.net/manual/en/memcached.ispristine.php
	 * @return bool the true if instance is recently created, false otherwise.
	 */
	public function isPristine (): bool {}

	/**
	 * @param string $key
	 */
	public function checkKey (string $key): bool {}

}

/**
 * @link http://www.php.net/manual/en/class.memcachedexception.php
 */
class MemcachedException extends RuntimeException implements Stringable, Throwable {
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
// End of memcached v.3.2.0
