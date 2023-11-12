<?php

// Start of memcached v.3.2.0

/**
 * Represents a connection to a set of memcached servers.
 * @link http://www.php.net/manual/en/class.memcached.php
 */
class Memcached  {
	const LIBMEMCACHED_VERSION_HEX = 16777240;
	/**
	 * Enables or disables payload compression. When enabled,
	 * item values longer than a certain threshold (currently 100 bytes) will be
	 * compressed during storage and decompressed during retrieval
	 * transparently.
	 * <p>Type: bool, default: true.</p>
	const OPT_COMPRESSION = -1001;
	const OPT_COMPRESSION_TYPE = -1004;
	/**
	 * This can be used to create a "domain" for your item keys. The value
	 * specified here will be prefixed to each of the keys. It cannot be
	 * longer than 128 characters and will reduce the
	 * maximum available key size. The prefix is applied only to the item keys,
	 * not to the server keys.
	 * <p>Type: string, default: "".</p>
	const OPT_PREFIX_KEY = -1002;
	/**
	 * Specifies the serializer to use for serializing non-scalar values.
	 * The valid serializers are Memcached::SERIALIZER_PHP
	 * or Memcached::SERIALIZER_IGBINARY. The latter is
	 * supported only when memcached is configured with
	 * --enable-memcached-igbinary option and the
	 * igbinary extension is loaded.
	 * <p>Type: int, default: Memcached::SERIALIZER_PHP.</p>
	const OPT_SERIALIZER = -1003;
	const OPT_USER_FLAGS = -1006;
	const OPT_STORE_RETRY_COUNT = -1005;
	/**
	 * Indicates whether igbinary serializer support is available.
	 * <p>Type: bool.</p>
	const HAVE_IGBINARY = true;
	/**
	 * Indicates whether JSON serializer support is available.
	 * <p>Type: bool.</p>
	const HAVE_JSON = true;
	/**
	 * Indicates whether msgpack serializer support is available.
	 * <p>Type: bool.</p>
	 * <p>Available as of Memcached 3.0.0.</p>
	const HAVE_MSGPACK = true;
	const HAVE_ENCODING = true;
	/**
	 * Type: bool.
	 * <p>Available as of Memcached 3.0.0.</p>
	const HAVE_SESSION = true;
	/**
	 * Type: bool.
	 * <p>Available as of Memcached 3.0.0.</p>
	const HAVE_SASL = false;
	/**
	 * Specifies the hashing algorithm used for the item keys. The valid
	 * values are supplied via Memcached::HASH_&#42; constants.
	 * Each hash algorithm has its advantages and its disadvantages. Go with the
	 * default if you don't know or don't care.
	 * <p>Type: int, default: Memcached::HASH_DEFAULT</p>
	const OPT_HASH = 2;
	/**
	 * The default (Jenkins one-at-a-time) item key hashing algorithm.
	const HASH_DEFAULT = 0;
	/**
	 * MD5 item key hashing algorithm.
	const HASH_MD5 = 1;
	/**
	 * CRC item key hashing algorithm.
	const HASH_CRC = 2;
	/**
	 * FNV1_64 item key hashing algorithm.
	const HASH_FNV1_64 = 3;
	/**
	 * FNV1_64A item key hashing algorithm.
	const HASH_FNV1A_64 = 4;
	/**
	 * FNV1_32 item key hashing algorithm.
	const HASH_FNV1_32 = 5;
	/**
	 * FNV1_32A item key hashing algorithm.
	const HASH_FNV1A_32 = 6;
	/**
	 * Hsieh item key hashing algorithm.
	const HASH_HSIEH = 7;
	/**
	 * Murmur item key hashing algorithm.
	const HASH_MURMUR = 8;
	/**
	 * Specifies the method of distributing item keys to the servers.
	 * Currently supported methods are modulo and consistent hashing. Consistent
	 * hashing delivers better distribution and allows servers to be added to
	 * the cluster with minimal cache losses.
	 * <p>Type: int, default: Memcached::DISTRIBUTION_MODULA.</p>
	const OPT_DISTRIBUTION = 9;
	/**
	 * Modulo-based key distribution algorithm.
	const DISTRIBUTION_MODULA = 0;
	/**
	 * Consistent hashing key distribution algorithm (based on libketama).
	const DISTRIBUTION_CONSISTENT = 1;
	const DISTRIBUTION_VIRTUAL_BUCKET = 6;
	/**
	 * Enables or disables compatibility with libketama-like behavior. When
	 * enabled, the item key hashing algorithm is set to MD5 and distribution is
	 * set to be weighted consistent hashing distribution. This is useful
	 * because other libketama-based clients (Python, Ruby, etc.) with the same
	 * server configuration will be able to access the keys transparently.
	 * <p>It is highly recommended to enable this option if you want to use
	 * consistent hashing, and it may be enabled by default in future
	 * releases.</p>
	 * <p>Type: bool, default: false.</p>
	const OPT_LIBKETAMA_COMPATIBLE = 16;
	const OPT_LIBKETAMA_HASH = 17;
	const OPT_TCP_KEEPALIVE = 32;
	/**
	 * Enables or disables buffered I/O. Enabling buffered I/O causes
	 * storage commands to "buffer" instead of being sent. Any action that
	 * retrieves data causes this buffer to be sent to the remote connection.
	 * Quitting the connection or closing down the connection will also cause
	 * the buffered data to be pushed to the remote connection.
	 * <p>Type: bool, default: false.</p>
	const OPT_BUFFER_WRITES = 10;
	/**
	 * Enable the use of the binary protocol. Please note that you cannot
	 * toggle this option on an open connection.
	 * <p>Type: bool, default: false.</p>
	const OPT_BINARY_PROTOCOL = 18;
	/**
	 * Enables or disables asynchronous I/O. This is the fastest transport
	 * available for storage functions.
	 * <p>Type: bool, default: false.</p>
	const OPT_NO_BLOCK = 0;
	/**
	 * Enables or disables the no-delay feature for connecting sockets (may
	 * be faster in some environments).
	 * <p>Type: bool, default: false.</p>
	const OPT_TCP_NODELAY = 1;
	/**
	 * The maximum socket send buffer in bytes.
	 * <p>Type: int, default: varies by platform/kernel
	 * configuration.</p>
	const OPT_SOCKET_SEND_SIZE = 4;
	/**
	 * The maximum socket receive buffer in bytes.
	 * <p>Type: int, default: varies by platform/kernel
	 * configuration.</p>
	const OPT_SOCKET_RECV_SIZE = 5;
	/**
	 * In non-blocking mode this set the value of the timeout during socket
	 * connection, in milliseconds.
	 * <p>Type: int, default: 1000.</p>
	const OPT_CONNECT_TIMEOUT = 14;
	/**
	 * The amount of time, in seconds, to wait until retrying a failed
	 * connection attempt.
	 * <p>Type: int, default: 0.</p>
	const OPT_RETRY_TIMEOUT = 15;
	const OPT_DEAD_TIMEOUT = 36;
	/**
	 * Socket sending timeout, in microseconds. In cases where you cannot
	 * use non-blocking I/O this will allow you to still have timeouts on the
	 * sending of data.
	 * <p>Type: int, default: 0.</p>
	const OPT_SEND_TIMEOUT = 19;
	/**
	 * Socket reading timeout, in microseconds. In cases where you cannot
	 * use non-blocking I/O this will allow you to still have timeouts on the
	 * reading of data.
	 * <p>Type: int, default: 0.</p>
	const OPT_RECV_TIMEOUT = 20;
	/**
	 * Timeout for connection polling, in milliseconds.
	 * <p>Type: int, default: 1000.</p>
	const OPT_POLL_TIMEOUT = 8;
	/**
	 * Enables or disables caching of DNS lookups.
	 * <p>Type: bool, default: false.</p>
	const OPT_CACHE_LOOKUPS = 6;
	/**
	 * Specifies the failure limit for server connection attempts. The
	 * server will be removed after this many continuous connection
	 * failures.
	 * <p>Type: int, default: 0.</p>
	const OPT_SERVER_FAILURE_LIMIT = 21;
	const OPT_AUTO_EJECT_HOSTS = 28;
	const OPT_HASH_WITH_PREFIX_KEY = 25;
	/**
	 * Enables or disables ignoring the result of storage commands 
	 * (set, add, replace, append, prepend, delete, increment, decrement, etc.). 
	 * Storage commands will be sent without spending time waiting for a reply 
	 * (there would be no reply).
	 * Retrieval commands such as Memcached::get are unaffected by this setting.
	 * <p>Type: bool, default: false.</p>
	const OPT_NOREPLY = 26;
	const OPT_SORT_HOSTS = 12;
	const OPT_VERIFY_KEY = 13;
	const OPT_USE_UDP = 27;
	const OPT_NUMBER_OF_REPLICAS = 29;
	const OPT_RANDOMIZE_REPLICA_READ = 30;
	const OPT_REMOVE_FAILED_SERVERS = 35;
	const OPT_SERVER_TIMEOUT_LIMIT = 37;
	/**
	 * The operation was successful.
	const RES_SUCCESS = 0;
	/**
	 * The operation failed in some fashion.
	const RES_FAILURE = 1;
	/**
	 * DNS lookup failed.
	const RES_HOST_LOOKUP_FAILURE = 2;
	const RES_CONNECTION_FAILURE = 3;
	const RES_CONNECTION_BIND_FAILURE = 4;
	/**
	 * Failed to write network data.
	const RES_WRITE_FAILURE = 5;
	const RES_READ_FAILURE = 6;
	/**
	 * Failed to read network data.
	const RES_UNKNOWN_READ_FAILURE = 7;
	/**
	 * Bad command in memcached protocol.
	const RES_PROTOCOL_ERROR = 8;
	/**
	 * Error on the client side.
	const RES_CLIENT_ERROR = 9;
	/**
	 * Error on the server side.
	const RES_SERVER_ERROR = 10;
	/**
	 * Failed to do compare-and-swap: item you are trying to store has been
	 * modified since you last fetched it.
	const RES_DATA_EXISTS = 12;
	const RES_DATA_DOES_NOT_EXIST = 13;
	/**
	 * Item was not stored: but not because of an error. This normally
	 * means that either the condition for an "add" or a "replace" command
	 * wasn't met, or that the item is in a delete queue.
	const RES_NOTSTORED = 14;
	const RES_STORED = 15;
	/**
	 * Item with this key was not found (with "get" operation or "cas"
	 * operations).
	const RES_NOTFOUND = 16;
	/**
	 * Partial network data read error.
	const RES_PARTIAL_READ = 18;
	/**
	 * Some errors occurred during multi-get.
	const RES_SOME_ERRORS = 19;
	/**
	 * Server list is empty.
	const RES_NO_SERVERS = 20;
	/**
	 * End of result set.
	const RES_END = 21;
	const RES_DELETED = 22;
	const RES_VALUE = 23;
	const RES_STAT = 24;
	const RES_ITEM = 25;
	/**
	 * System error.
	const RES_ERRNO = 26;
	const RES_FAIL_UNIX_SOCKET = 27;
	const RES_NOT_SUPPORTED = 28;
	const RES_NO_KEY_PROVIDED = 29;
	const RES_FETCH_NOTFINISHED = 30;
	/**
	 * The operation timed out.
	const RES_TIMEOUT = 31;
	/**
	 * The operation was buffered.
	const RES_BUFFERED = 32;
	/**
	 * Bad key.
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
	/**
	 * Failed to create network socket.
	const RES_CONNECTION_SOCKET_CREATE_FAILURE = 11;
	/**
	 * Available as of Memcached 3.0.0.
	const RES_E2BIG = 37;
	/**
	 * Available as of Memcached 3.0.0.
	const RES_KEY_TOO_BIG = 39;
	/**
	 * Available as of Memcached 3.0.0.
	const RES_SERVER_TEMPORARILY_DISABLED = 47;
	/**
	 * Available as of Memcached 3.0.0.
	const RES_SERVER_MEMORY_ALLOCATION_FAILURE = 48;
	/**
	 * Payload failure: could not compress/decompress or serialize/unserialize the value.
	const RES_PAYLOAD_FAILURE = -1001;
	/**
	 * The default PHP serializer.
	const SERIALIZER_PHP = 1;
	/**
	 * The igbinary serializer.
	 * Instead of textual representation it stores PHP data structures in a
	 * compact binary form, resulting in space and time gains.
	const SERIALIZER_IGBINARY = 2;
	/**
	 * The JSON serializer.
	const SERIALIZER_JSON = 3;
	const SERIALIZER_JSON_ARRAY = 4;
	const SERIALIZER_MSGPACK = 5;
	const COMPRESSION_FASTLZ = 2;
	const COMPRESSION_ZLIB = 1;
	/**
	 * A flag for Memcached::getMulti and
	 * Memcached::getMultiByKey to ensure that the keys are
	 * returned in the same order as they were requested in. Non-existing keys
	 * get a default value of NULL.
	const GET_PRESERVE_ORDER = 1;
	/**
	 * A flag for Memcached::get, Memcached::getMulti and
	 * Memcached::getMultiByKey to ensure that the CAS token values
	 * are returned as well.
	 * <p>Available as of Memcached 3.0.0.</p>
	const GET_EXTENDED = 2;
	const GET_ERROR_RETURN_VALUE = false;


	/**
	 * Create a Memcached instance
	 * @link http://www.php.net/manual/en/memcached.construct.php
	 * @param string|null $persistent_id [optional] 
	 * @return string|null 
	 */
	public function __construct (?string $persistent_id = null): ?string {}

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
	 * @param string $key 
	 * @param callable $cache_cb [optional] 
	 * @param int $flags [optional] 
	 * @return mixed Returns the value stored in the cache or false otherwise.
	 * If the flags is set to Memcached::GET_EXTENDED,
	 * an array containing the value and the CAS token is returned instead of only the value.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function get (string $key, callable $cache_cb = null, int $flags = null): mixed {}

	/**
	 * Retrieve an item from a specific server
	 * @link http://www.php.net/manual/en/memcached.getbykey.php
	 * @param string $server_key 
	 * @param string $key 
	 * @param callable $cache_cb [optional] 
	 * @param int $flags [optional] 
	 * @return mixed Returns the value stored in the cache or false otherwise.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function getByKey (string $server_key, string $key, callable $cache_cb = null, int $flags = null): mixed {}

	/**
	 * Retrieve multiple items
	 * @link http://www.php.net/manual/en/memcached.getmulti.php
	 * @param array $keys 
	 * @param int $flags [optional] 
	 * @return mixed Returns the array of found items or false on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function getMulti (array $keys, int $flags = null): mixed {}

	/**
	 * Retrieve multiple items from a specific server
	 * @link http://www.php.net/manual/en/memcached.getmultibykey.php
	 * @param string $server_key 
	 * @param array $keys 
	 * @param int $flags [optional] 
	 * @return array|false Returns the array of found items or false on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function getMultiByKey (string $server_key, array $keys, int $flags = null): array|false {}

	/**
	 * Request multiple items
	 * @link http://www.php.net/manual/en/memcached.getdelayed.php
	 * @param array $keys 
	 * @param bool $with_cas [optional] 
	 * @param callable $value_cb [optional] 
	 * @return bool Returns true on success or false on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function getDelayed (array $keys, bool $with_cas = null, callable $value_cb = null): bool {}

	/**
	 * Request multiple items from a specific server
	 * @link http://www.php.net/manual/en/memcached.getdelayedbykey.php
	 * @param string $server_key 
	 * @param array $keys 
	 * @param bool $with_cas [optional] 
	 * @param callable $value_cb [optional] 
	 * @return bool Returns true on success or false on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function getDelayedByKey (string $server_key, array $keys, bool $with_cas = null, callable $value_cb = null): bool {}

	/**
	 * Fetch the next result
	 * @link http://www.php.net/manual/en/memcached.fetch.php
	 * @return array Returns the next result or false otherwise.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_END if result set is exhausted.
	 */
	public function fetch (): array {}

	/**
	 * Fetch all the remaining results
	 * @link http://www.php.net/manual/en/memcached.fetchall.php
	 * @return array|false Returns the results or false on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function fetchAll (): array|false {}

	/**
	 * Store an item
	 * @link http://www.php.net/manual/en/memcached.set.php
	 * @param string $key 
	 * @param mixed $value 
	 * @param int $expiration [optional] 
	 * @return bool Returns true on success or false on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function set (string $key, mixed $value, int $expiration = null): bool {}

	/**
	 * Store an item on a specific server
	 * @link http://www.php.net/manual/en/memcached.setbykey.php
	 * @param string $server_key 
	 * @param string $key 
	 * @param mixed $value 
	 * @param int $expiration [optional] 
	 * @return bool Returns true on success or false on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function setByKey (string $server_key, string $key, mixed $value, int $expiration = null): bool {}

	/**
	 * Set a new expiration on an item
	 * @link http://www.php.net/manual/en/memcached.touch.php
	 * @param string $key 
	 * @param int $expiration 
	 * @return bool Returns true on success or false on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function touch (string $key, int $expiration): bool {}

	/**
	 * Set a new expiration on an item on a specific server
	 * @link http://www.php.net/manual/en/memcached.touchbykey.php
	 * @param string $server_key 
	 * @param string $key 
	 * @param int $expiration 
	 * @return bool Returns true on success or false on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function touchByKey (string $server_key, string $key, int $expiration): bool {}

	/**
	 * Store multiple items
	 * @link http://www.php.net/manual/en/memcached.setmulti.php
	 * @param array $items 
	 * @param int $expiration [optional] 
	 * @return bool Returns true on success or false on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function setMulti (array $items, int $expiration = null): bool {}

	/**
	 * Store multiple items on a specific server
	 * @link http://www.php.net/manual/en/memcached.setmultibykey.php
	 * @param string $server_key 
	 * @param array $items 
	 * @param int $expiration [optional] 
	 * @return bool Returns true on success or false on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function setMultiByKey (string $server_key, array $items, int $expiration = null): bool {}

	/**
	 * Compare and swap an item
	 * @link http://www.php.net/manual/en/memcached.cas.php
	 * @param float $cas_token 
	 * @param string $key 
	 * @param mixed $value 
	 * @param int $expiration [optional] 
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_DATA_EXISTS if the item you are trying
	 * to store has been modified since you last fetched it.
	 */
	public function cas (float $cas_token, string $key, mixed $value, int $expiration = null): bool {}

	/**
	 * Compare and swap an item on a specific server
	 * @link http://www.php.net/manual/en/memcached.casbykey.php
	 * @param float $cas_token 
	 * @param string $server_key 
	 * @param string $key 
	 * @param mixed $value 
	 * @param int $expiration [optional] 
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_DATA_EXISTS if the item you are trying
	 * to store has been modified since you last fetched it.
	 */
	public function casByKey (float $cas_token, string $server_key, string $key, mixed $value, int $expiration = null): bool {}

	/**
	 * Add an item under a new key
	 * @link http://www.php.net/manual/en/memcached.add.php
	 * @param string $key 
	 * @param mixed $value 
	 * @param int $expiration [optional] 
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key already exists.
	 */
	public function add (string $key, mixed $value, int $expiration = null): bool {}

	/**
	 * Add an item under a new key on a specific server
	 * @link http://www.php.net/manual/en/memcached.addbykey.php
	 * @param string $server_key 
	 * @param string $key 
	 * @param mixed $value 
	 * @param int $expiration [optional] 
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key already exists.
	 */
	public function addByKey (string $server_key, string $key, mixed $value, int $expiration = null): bool {}

	/**
	 * Append data to an existing item
	 * @link http://www.php.net/manual/en/memcached.append.php
	 * @param string $key 
	 * @param string $value 
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function append (string $key, string $value): bool {}

	/**
	 * Append data to an existing item on a specific server
	 * @link http://www.php.net/manual/en/memcached.appendbykey.php
	 * @param string $server_key 
	 * @param string $key 
	 * @param string $value 
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function appendByKey (string $server_key, string $key, string $value): bool {}

	/**
	 * Prepend data to an existing item
	 * @link http://www.php.net/manual/en/memcached.prepend.php
	 * @param string $key 
	 * @param string $value 
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function prepend (string $key, string $value): bool {}

	/**
	 * Prepend data to an existing item on a specific server
	 * @link http://www.php.net/manual/en/memcached.prependbykey.php
	 * @param string $server_key 
	 * @param string $key 
	 * @param string $value 
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function prependByKey (string $server_key, string $key, string $value): bool {}

	/**
	 * Replace the item under an existing key
	 * @link http://www.php.net/manual/en/memcached.replace.php
	 * @param string $key 
	 * @param mixed $value 
	 * @param int $expiration [optional] 
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function replace (string $key, mixed $value, int $expiration = null): bool {}

	/**
	 * Replace the item under an existing key on a specific server
	 * @link http://www.php.net/manual/en/memcached.replacebykey.php
	 * @param string $server_key 
	 * @param string $key 
	 * @param mixed $value 
	 * @param int $expiration [optional] 
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTSTORED if the key does not exist.
	 */
	public function replaceByKey (string $server_key, string $key, mixed $value, int $expiration = null): bool {}

	/**
	 * Delete an item
	 * @link http://www.php.net/manual/en/memcached.delete.php
	 * @param string $key 
	 * @param int $time [optional] 
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function delete (string $key, int $time = null): bool {}

	/**
	 * Delete multiple items
	 * @link http://www.php.net/manual/en/memcached.deletemulti.php
	 * @param array $keys 
	 * @param int $time [optional] 
	 * @return array >
	 * Returns an array indexed by keys. Each element
	 * is true if the corresponding key was deleted, or one of the
	 * Memcached::RES_&#42; constants if the corresponding deletion
	 * failed.
	 * <p>>
	 * The Memcached::getResultCode will return
	 * the result code for the last executed delete operation, that is, the delete
	 * operation for the last element of keys.</p>
	 */
	public function deleteMulti (array $keys, int $time = null): array {}

	/**
	 * Delete an item from a specific server
	 * @link http://www.php.net/manual/en/memcached.deletebykey.php
	 * @param string $server_key 
	 * @param string $key 
	 * @param int $time [optional] 
	 * @return bool Returns true on success or false on failure.
	 * The Memcached::getResultCode will return
	 * Memcached::RES_NOTFOUND if the key does not exist.
	 */
	public function deleteByKey (string $server_key, string $key, int $time = null): bool {}

	/**
	 * Delete multiple items from a specific server
	 * @link http://www.php.net/manual/en/memcached.deletemultibykey.php
	 * @param string $server_key 
	 * @param array $keys 
	 * @param int $time [optional] 
	 * @return bool >
	 * Returns an array indexed by keys. Each element
	 * is true if the corresponding key was deleted, or one of the
	 * Memcached::RES_&#42; constants if the corresponding deletion
	 * failed.
	 * <p>>
	 * The Memcached::getResultCode will return
	 * the result code for the last executed delete operation, that is, the delete
	 * operation for the last element of keys.</p>
	 */
	public function deleteMultiByKey (string $server_key, array $keys, int $time = null): bool {}

	/**
	 * Increment numeric item's value
	 * @link http://www.php.net/manual/en/memcached.increment.php
	 * @param string $key 
	 * @param int $offset [optional] 
	 * @param int $initial_value [optional] 
	 * @param int $expiry [optional] 
	 * @return int|false Returns new item's value on success or false on failure.
	 */
	public function increment (string $key, int $offset = 1, int $initial_value = null, int $expiry = null): int|false {}

	/**
	 * Decrement numeric item's value
	 * @link http://www.php.net/manual/en/memcached.decrement.php
	 * @param string $key 
	 * @param int $offset [optional] 
	 * @param int $initial_value [optional] 
	 * @param int $expiry [optional] 
	 * @return int|false Returns item's new value on success or false on failure.
	 */
	public function decrement (string $key, int $offset = 1, int $initial_value = null, int $expiry = null): int|false {}

	/**
	 * Increment numeric item's value, stored on a specific server
	 * @link http://www.php.net/manual/en/memcached.incrementbykey.php
	 * @param string $server_key 
	 * @param string $key 
	 * @param int $offset [optional] 
	 * @param int $initial_value [optional] 
	 * @param int $expiry [optional] 
	 * @return int|false Returns new item's value on success or false on failure.
	 */
	public function incrementByKey (string $server_key, string $key, int $offset = 1, int $initial_value = null, int $expiry = null): int|false {}

	/**
	 * Decrement numeric item's value, stored on a specific server
	 * @link http://www.php.net/manual/en/memcached.decrementbykey.php
	 * @param string $server_key 
	 * @param string $key 
	 * @param int $offset [optional] 
	 * @param int $initial_value [optional] 
	 * @param int $expiry [optional] 
	 * @return int|false Returns item's new value on success or false on failure.
	 */
	public function decrementByKey (string $server_key, string $key, int $offset = 1, int $initial_value = null, int $expiry = null): int|false {}

	/**
	 * Add a server to the server pool
	 * @link http://www.php.net/manual/en/memcached.addserver.php
	 * @param string $host 
	 * @param int $port 
	 * @param int $weight [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function addServer (string $host, int $port, int $weight = null): bool {}

	/**
	 * Add multiple servers to the server pool
	 * @link http://www.php.net/manual/en/memcached.addservers.php
	 * @param array $servers 
	 * @return bool Returns true on success or false on failure.
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
	 * @param string $server_key 
	 * @return array Returns an array containing three keys of host,
	 * port, and weight on success or false
	 * on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function getServerByKey (string $server_key): array {}

	/**
	 * Clears all servers from the server list
	 * @link http://www.php.net/manual/en/memcached.resetserverlist.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function resetServerList (): bool {}

	/**
	 * Close any open connections
	 * @link http://www.php.net/manual/en/memcached.quit.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function quit (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function flushBuffers (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getLastErrorMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getLastErrorCode (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getLastErrorErrno (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getLastDisconnectedServer (): array|false {}

	/**
	 * Get server pool statistics
	 * @link http://www.php.net/manual/en/memcached.getstats.php
	 * @param string|null $type [optional]
	 * @return array|false Array of server statistics, one entry per server, or false on failure.
	 */
	public function getStats (?string $type = NULL): array|false {}

	/**
	 * Get server pool version info
	 * @link http://www.php.net/manual/en/memcached.getversion.php
	 * @return array Array of server versions, one entry per server.
	 */
	public function getVersion (): array {}

	/**
	 * Gets the keys stored on all the servers
	 * @link http://www.php.net/manual/en/memcached.getallkeys.php
	 * @return array|false Returns the keys stored on all the servers on success or false on failure.
	 */
	public function getAllKeys (): array|false {}

	/**
	 * Invalidate all items in the cache
	 * @link http://www.php.net/manual/en/memcached.flush.php
	 * @param int $delay [optional] 
	 * @return bool Returns true on success or false on failure.
	 * Use Memcached::getResultCode if necessary.
	 */
	public function flush (int $delay = null): bool {}

	/**
	 * Retrieve a Memcached option value
	 * @link http://www.php.net/manual/en/memcached.getoption.php
	 * @param int $option 
	 * @return mixed Returns the value of the requested option, or false on
	 * error.
	 */
	public function getOption (int $option): mixed {}

	/**
	 * Set a Memcached option
	 * @link http://www.php.net/manual/en/memcached.setoption.php
	 * @param int $option 
	 * @param mixed $value 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setOption (int $option, mixed $value): bool {}

	/**
	 * Set Memcached options
	 * @link http://www.php.net/manual/en/memcached.setoptions.php
	 * @param array $options An associative array of options where the key is the option to set and
	 * the value is the new value for the option.
	 * @return bool Returns true on success or false on failure.
	 */
	public function setOptions (array $options): bool {}

	/**
	 * {@inheritdoc}
	 * @param array $host_map
	 * @param array|null $forward_map
	 * @param int $replicas
	 */
	public function setBucket (array $host_map, ?array $forward_map = null, int $replicas): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function setEncodingKey (string $key): bool {}

	/**
	 * Check if a persitent connection to memcache is being used
	 * @link http://www.php.net/manual/en/memcached.ispersistent.php
	 * @return bool Returns true if Memcache instance uses a persistent connection, false otherwise.
	 */
	public function isPersistent (): bool {}

	/**
	 * Check if the instance was recently created
	 * @link http://www.php.net/manual/en/memcached.ispristine.php
	 * @return bool Returns the true if instance is recently created, false otherwise.
	 */
	public function isPristine (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function checkKey (string $key): bool {}

}

/**
 * @link http://www.php.net/manual/en/class.memcachedexception.php
 */
class MemcachedException extends RuntimeException implements Stringable, Throwable {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
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
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return int Returns the exception code as int in
	 * Exception but possibly as other type in
	 * Exception descendants (for example as
	 * string in PDOException).
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Returns the filename in which the exception was created.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Returns the line number where the exception was created.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString (): string {}

}
// End of memcached v.3.2.0
