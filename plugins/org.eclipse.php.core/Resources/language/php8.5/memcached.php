<?php

// Start of memcached v.3.2.0

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
	 * {@inheritdoc}
	 * @param string|null $persistent_id [optional]
	 * @param callable|null $callback [optional]
	 * @param string|null $connection_str [optional]
	 */
	public function __construct (?string $persistent_id = NULL, ?callable $callback = NULL, ?string $connection_str = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getResultCode (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getResultMessage (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param callable|null $cache_cb [optional]
	 * @param int $get_flags [optional]
	 */
	public function get (string $key, ?callable $cache_cb = NULL, int $get_flags = 0): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $server_key
	 * @param string $key
	 * @param callable|null $cache_cb [optional]
	 * @param int $get_flags [optional]
	 */
	public function getByKey (string $server_key, string $key, ?callable $cache_cb = NULL, int $get_flags = 0): mixed {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 * @param int $get_flags [optional]
	 */
	public function getMulti (array $keys, int $get_flags = 0): array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $server_key
	 * @param array $keys
	 * @param int $get_flags [optional]
	 */
	public function getMultiByKey (string $server_key, array $keys, int $get_flags = 0): array|false {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 * @param bool $with_cas [optional]
	 * @param callable|null $value_cb [optional]
	 */
	public function getDelayed (array $keys, bool $with_cas = false, ?callable $value_cb = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $server_key
	 * @param array $keys
	 * @param bool $with_cas [optional]
	 * @param callable|null $value_cb [optional]
	 */
	public function getDelayedByKey (string $server_key, array $keys, bool $with_cas = false, ?callable $value_cb = NULL): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function fetch (): array|false {}

	/**
	 * {@inheritdoc}
	 */
	public function fetchAll (): array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 * @param int $expiration [optional]
	 */
	public function set (string $key, mixed $value = null, int $expiration = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $server_key
	 * @param string $key
	 * @param mixed $value
	 * @param int $expiration [optional]
	 */
	public function setByKey (string $server_key, string $key, mixed $value = null, int $expiration = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $expiration [optional]
	 */
	public function touch (string $key, int $expiration = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $server_key
	 * @param string $key
	 * @param int $expiration [optional]
	 */
	public function touchByKey (string $server_key, string $key, int $expiration = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param array $items
	 * @param int $expiration [optional]
	 */
	public function setMulti (array $items, int $expiration = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $server_key
	 * @param array $items
	 * @param int $expiration [optional]
	 */
	public function setMultiByKey (string $server_key, array $items, int $expiration = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $cas_token
	 * @param string $key
	 * @param mixed $value
	 * @param int $expiration [optional]
	 */
	public function cas (string $cas_token, string $key, mixed $value = null, int $expiration = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $cas_token
	 * @param string $server_key
	 * @param string $key
	 * @param mixed $value
	 * @param int $expiration [optional]
	 */
	public function casByKey (string $cas_token, string $server_key, string $key, mixed $value = null, int $expiration = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 * @param int $expiration [optional]
	 */
	public function add (string $key, mixed $value = null, int $expiration = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $server_key
	 * @param string $key
	 * @param mixed $value
	 * @param int $expiration [optional]
	 */
	public function addByKey (string $server_key, string $key, mixed $value = null, int $expiration = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $value
	 */
	public function append (string $key, string $value): ?bool {}

	/**
	 * {@inheritdoc}
	 * @param string $server_key
	 * @param string $key
	 * @param string $value
	 */
	public function appendByKey (string $server_key, string $key, string $value): ?bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $value
	 */
	public function prepend (string $key, string $value): ?bool {}

	/**
	 * {@inheritdoc}
	 * @param string $server_key
	 * @param string $key
	 * @param string $value
	 */
	public function prependByKey (string $server_key, string $key, string $value): ?bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 * @param int $expiration [optional]
	 */
	public function replace (string $key, mixed $value = null, int $expiration = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $server_key
	 * @param string $key
	 * @param mixed $value
	 * @param int $expiration [optional]
	 */
	public function replaceByKey (string $server_key, string $key, mixed $value = null, int $expiration = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $time [optional]
	 */
	public function delete (string $key, int $time = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 * @param int $time [optional]
	 */
	public function deleteMulti (array $keys, int $time = 0): array {}

	/**
	 * {@inheritdoc}
	 * @param string $server_key
	 * @param string $key
	 * @param int $time [optional]
	 */
	public function deleteByKey (string $server_key, string $key, int $time = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $server_key
	 * @param array $keys
	 * @param int $time [optional]
	 */
	public function deleteMultiByKey (string $server_key, array $keys, int $time = 0): array {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $offset [optional]
	 * @param int $initial_value [optional]
	 * @param int $expiry [optional]
	 */
	public function increment (string $key, int $offset = 1, int $initial_value = 0, int $expiry = 0): int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $offset [optional]
	 * @param int $initial_value [optional]
	 * @param int $expiry [optional]
	 */
	public function decrement (string $key, int $offset = 1, int $initial_value = 0, int $expiry = 0): int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $server_key
	 * @param string $key
	 * @param int $offset [optional]
	 * @param int $initial_value [optional]
	 * @param int $expiry [optional]
	 */
	public function incrementByKey (string $server_key, string $key, int $offset = 1, int $initial_value = 0, int $expiry = 0): int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $server_key
	 * @param string $key
	 * @param int $offset [optional]
	 * @param int $initial_value [optional]
	 * @param int $expiry [optional]
	 */
	public function decrementByKey (string $server_key, string $key, int $offset = 1, int $initial_value = 0, int $expiry = 0): int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $host
	 * @param int $port
	 * @param int $weight [optional]
	 */
	public function addServer (string $host, int $port, int $weight = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param array $servers
	 */
	public function addServers (array $servers): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getServerList (): array {}

	/**
	 * {@inheritdoc}
	 * @param string $server_key
	 */
	public function getServerByKey (string $server_key): array|false {}

	/**
	 * {@inheritdoc}
	 */
	public function resetServerList (): bool {}

	/**
	 * {@inheritdoc}
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
	 * {@inheritdoc}
	 * @param string|null $type [optional]
	 */
	public function getStats (?string $type = NULL): array|false {}

	/**
	 * {@inheritdoc}
	 */
	public function getVersion (): array|false {}

	/**
	 * {@inheritdoc}
	 */
	public function getAllKeys (): array|false {}

	/**
	 * {@inheritdoc}
	 * @param int $delay [optional]
	 */
	public function flush (int $delay = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 */
	public function getOption (int $option): mixed {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 * @param mixed $value
	 */
	public function setOption (int $option, mixed $value = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param array $options
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
	 * {@inheritdoc}
	 */
	public function isPersistent (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function isPristine (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function checkKey (string $key): bool {}

}

class MemcachedException extends RuntimeException implements Stringable, Throwable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}
// End of memcached v.3.2.0
