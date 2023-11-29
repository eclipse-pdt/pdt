<?php

// Start of redis v.6.0.2

class Redis  {
	const REDIS_NOT_FOUND = 0;
	const REDIS_STRING = 1;
	const REDIS_SET = 2;
	const REDIS_LIST = 3;
	const REDIS_ZSET = 4;
	const REDIS_HASH = 5;
	const REDIS_STREAM = 6;
	const ATOMIC = 0;
	const MULTI = 1;
	const PIPELINE = 2;
	const OPT_SERIALIZER = 1;
	const OPT_PREFIX = 2;
	const OPT_READ_TIMEOUT = 3;
	const OPT_TCP_KEEPALIVE = 6;
	const OPT_COMPRESSION = 7;
	const OPT_REPLY_LITERAL = 8;
	const OPT_COMPRESSION_LEVEL = 9;
	const OPT_NULL_MULTIBULK_AS_NULL = 10;
	const SERIALIZER_NONE = 0;
	const SERIALIZER_PHP = 1;
	const SERIALIZER_IGBINARY = 2;
	const SERIALIZER_MSGPACK = 3;
	const SERIALIZER_JSON = 4;
	const COMPRESSION_NONE = 0;
	const COMPRESSION_LZF = 1;
	const COMPRESSION_ZSTD = 2;
	const COMPRESSION_ZSTD_DEFAULT = 3;
	const COMPRESSION_ZSTD_MAX = 22;
	const COMPRESSION_LZ4 = 3;
	const OPT_SCAN = 4;
	const SCAN_RETRY = 1;
	const SCAN_NORETRY = 0;
	const SCAN_PREFIX = 2;
	const SCAN_NOPREFIX = 3;
	const BEFORE = "before";
	const AFTER = "after";
	const LEFT = "left";
	const RIGHT = "right";
	const OPT_MAX_RETRIES = 11;
	const OPT_BACKOFF_ALGORITHM = 12;
	const BACKOFF_ALGORITHM_DEFAULT = 0;
	const BACKOFF_ALGORITHM_CONSTANT = 6;
	const BACKOFF_ALGORITHM_UNIFORM = 5;
	const BACKOFF_ALGORITHM_EXPONENTIAL = 4;
	const BACKOFF_ALGORITHM_FULL_JITTER = 2;
	const BACKOFF_ALGORITHM_EQUAL_JITTER = 3;
	const BACKOFF_ALGORITHM_DECORRELATED_JITTER = 1;
	const OPT_BACKOFF_BASE = 13;
	const OPT_BACKOFF_CAP = 14;


	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	public function __construct (?array $options = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * {@inheritdoc}
	 * @param string $value
	 */
	public function _compress (string $value): string {}

	/**
	 * {@inheritdoc}
	 * @param string $value
	 */
	public function _uncompress (string $value): string {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function _prefix (string $key): string {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function _serialize (mixed $value = null): string {}

	/**
	 * {@inheritdoc}
	 * @param string $value
	 */
	public function _unserialize (string $value): mixed {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function _pack (mixed $value = null): string {}

	/**
	 * {@inheritdoc}
	 * @param string $value
	 */
	public function _unpack (string $value): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $subcmd
	 * @param string $args [optional]
	 */
	public function acl (string $subcmd, string ...$args): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 */
	public function append (string $key, mixed $value = null): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param mixed $credentials
	 */
	public function auth (mixed $credentials = null): Redis|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function bgSave (): Redis|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function bgrewriteaof (): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $start [optional]
	 * @param int $end [optional]
	 * @param bool $bybit [optional]
	 */
	public function bitcount (string $key, int $start = 0, int $end = -1, bool $bybit = false): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $operation
	 * @param string $deskey
	 * @param string $srckey
	 * @param string $other_keys [optional]
	 */
	public function bitop (string $operation, string $deskey, string $srckey, string ...$other_keys): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param bool $bit
	 * @param int $start [optional]
	 * @param int $end [optional]
	 * @param bool $bybit [optional]
	 */
	public function bitpos (string $key, bool $bit, int $start = 0, int $end = -1, bool $bybit = false): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_keys
	 * @param string|int|float $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function blPop (array|string $key_or_keys, string|int|float $timeout_or_key, mixed ...$extra_args): Redis|array|false|null {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_keys
	 * @param string|int|float $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function brPop (array|string $key_or_keys, string|int|float $timeout_or_key, mixed ...$extra_args): Redis|array|false|null {}

	/**
	 * {@inheritdoc}
	 * @param string $src
	 * @param string $dst
	 * @param int|float $timeout
	 */
	public function brpoplpush (string $src, string $dst, int|float $timeout): Redis|string|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param string|int $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function bzPopMax (array|string $key, string|int $timeout_or_key, mixed ...$extra_args): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param string|int $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function bzPopMin (array|string $key, string|int $timeout_or_key, mixed ...$extra_args): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param float $timeout
	 * @param array $keys
	 * @param string $from
	 * @param int $count [optional]
	 */
	public function bzmpop (float $timeout, array $keys, string $from, int $count = 1): Redis|array|false|null {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 * @param string $from
	 * @param int $count [optional]
	 */
	public function zmpop (array $keys, string $from, int $count = 1): Redis|array|false|null {}

	/**
	 * {@inheritdoc}
	 * @param float $timeout
	 * @param array $keys
	 * @param string $from
	 * @param int $count [optional]
	 */
	public function blmpop (float $timeout, array $keys, string $from, int $count = 1): Redis|array|false|null {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 * @param string $from
	 * @param int $count [optional]
	 */
	public function lmpop (array $keys, string $from, int $count = 1): Redis|array|false|null {}

	/**
	 * {@inheritdoc}
	 */
	public function clearLastError (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $opt
	 * @param mixed $args [optional]
	 */
	public function client (string $opt, mixed ...$args): mixed {}

	/**
	 * {@inheritdoc}
	 */
	public function close (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $opt [optional]
	 * @param mixed $args [optional]
	 */
	public function command (?string $opt = NULL, mixed ...$args): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $operation
	 * @param array|string|null $key_or_settings [optional]
	 * @param string|null $value [optional]
	 */
	public function config (string $operation, array|string|null $key_or_settings = NULL, ?string $value = NULL): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $host
	 * @param int $port [optional]
	 * @param float $timeout [optional]
	 * @param string|null $persistent_id [optional]
	 * @param int $retry_interval [optional]
	 * @param float $read_timeout [optional]
	 * @param array|null $context [optional]
	 */
	public function connect (string $host, int $port = 6379, float $timeout = 0, ?string $persistent_id = NULL, int $retry_interval = 0, float $read_timeout = 0, ?array $context = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $src
	 * @param string $dst
	 * @param array|null $options [optional]
	 */
	public function copy (string $src, string $dst, ?array $options = NULL): Redis|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function dbSize (): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function debug (string $key): Redis|string {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $by [optional]
	 */
	public function decr (string $key, int $by = 1): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $value
	 */
	public function decrBy (string $key, int $value): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param string $other_keys [optional]
	 */
	public function del (array|string $key, string ...$other_keys): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param string $other_keys [optional]
	 * @deprecated 
	 */
	public function delete (array|string $key, string ...$other_keys): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function discard (): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function dump (string $key): Redis|string {}

	/**
	 * {@inheritdoc}
	 * @param string $str
	 */
	public function echo (string $str): Redis|string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $script
	 * @param array $args [optional]
	 * @param int $num_keys [optional]
	 */
	public function eval (string $script, array $args = array (
), int $num_keys = 0): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $script_sha
	 * @param array $args [optional]
	 * @param int $num_keys [optional]
	 */
	public function eval_ro (string $script_sha, array $args = array (
), int $num_keys = 0): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $sha1
	 * @param array $args [optional]
	 * @param int $num_keys [optional]
	 */
	public function evalsha (string $sha1, array $args = array (
), int $num_keys = 0): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $sha1
	 * @param array $args [optional]
	 * @param int $num_keys [optional]
	 */
	public function evalsha_ro (string $sha1, array $args = array (
), int $num_keys = 0): mixed {}

	/**
	 * {@inheritdoc}
	 */
	public function exec (): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function exists (mixed $key = null, mixed ...$other_keys): Redis|int|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $timeout
	 * @param string|null $mode [optional]
	 */
	public function expire (string $key, int $timeout, ?string $mode = NULL): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $timestamp
	 * @param string|null $mode [optional]
	 */
	public function expireAt (string $key, int $timestamp, ?string $mode = NULL): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param array|null $to [optional]
	 * @param bool $abort [optional]
	 * @param int $timeout [optional]
	 */
	public function failover (?array $to = NULL, bool $abort = false, int $timeout = 0): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function expiretime (string $key): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function pexpiretime (string $key): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $fn
	 * @param array $keys [optional]
	 * @param array $args [optional]
	 */
	public function fcall (string $fn, array $keys = array (
), array $args = array (
)): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $fn
	 * @param array $keys [optional]
	 * @param array $args [optional]
	 */
	public function fcall_ro (string $fn, array $keys = array (
), array $args = array (
)): mixed {}

	/**
	 * {@inheritdoc}
	 * @param bool|null $sync [optional]
	 */
	public function flushAll (?bool $sync = NULL): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param bool|null $sync [optional]
	 */
	public function flushDB (?bool $sync = NULL): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $operation
	 * @param mixed $args [optional]
	 */
	public function function (string $operation, mixed ...$args): Redis|array|string|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param float $lng
	 * @param float $lat
	 * @param string $member
	 * @param mixed $other_triples_and_options [optional]
	 */
	public function geoadd (string $key, float $lng, float $lat, string $member, mixed ...$other_triples_and_options): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $src
	 * @param string $dst
	 * @param string|null $unit [optional]
	 */
	public function geodist (string $key, string $src, string $dst, ?string $unit = NULL): Redis|float|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 * @param string $other_members [optional]
	 */
	public function geohash (string $key, string $member, string ...$other_members): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 * @param string $other_members [optional]
	 */
	public function geopos (string $key, string $member, string ...$other_members): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param float $lng
	 * @param float $lat
	 * @param float $radius
	 * @param string $unit
	 * @param array $options [optional]
	 */
	public function georadius (string $key, float $lng, float $lat, float $radius, string $unit, array $options = array (
)): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param float $lng
	 * @param float $lat
	 * @param float $radius
	 * @param string $unit
	 * @param array $options [optional]
	 */
	public function georadius_ro (string $key, float $lng, float $lat, float $radius, string $unit, array $options = array (
)): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 * @param float $radius
	 * @param string $unit
	 * @param array $options [optional]
	 */
	public function georadiusbymember (string $key, string $member, float $radius, string $unit, array $options = array (
)): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 * @param float $radius
	 * @param string $unit
	 * @param array $options [optional]
	 */
	public function georadiusbymember_ro (string $key, string $member, float $radius, string $unit, array $options = array (
)): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array|string $position
	 * @param array|int|float $shape
	 * @param string $unit
	 * @param array $options [optional]
	 */
	public function geosearch (string $key, array|string $position, array|int|float $shape, string $unit, array $options = array (
)): array {}

	/**
	 * {@inheritdoc}
	 * @param string $dst
	 * @param string $src
	 * @param array|string $position
	 * @param array|int|float $shape
	 * @param string $unit
	 * @param array $options [optional]
	 */
	public function geosearchstore (string $dst, string $src, array|string $position, array|int|float $shape, string $unit, array $options = array (
)): Redis|array|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function get (string $key): mixed {}

	/**
	 * {@inheritdoc}
	 */
	public function getAuth (): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $idx
	 */
	public function getBit (string $key, int $idx): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array $options [optional]
	 */
	public function getEx (string $key, array $options = array (
)): Redis|string|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getDBNum (): int {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function getDel (string $key): Redis|string|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getHost (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getLastError (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getMode (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 */
	public function getOption (int $option): mixed {}

	/**
	 * {@inheritdoc}
	 */
	public function getPersistentID (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getPort (): int {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $start
	 * @param int $end
	 */
	public function getRange (string $key, int $start, int $end): Redis|string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key1
	 * @param string $key2
	 * @param array|null $options [optional]
	 */
	public function lcs (string $key1, string $key2, ?array $options = NULL): Redis|array|string|int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function getReadTimeout (): float {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 */
	public function getset (string $key, mixed $value = null): Redis|string|false {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimeout (): float|false {}

	/**
	 * {@inheritdoc}
	 */
	public function getTransferredBytes (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function clearTransferredBytes (): void {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $field
	 * @param string $other_fields [optional]
	 */
	public function hDel (string $key, string $field, string ...$other_fields): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $field
	 */
	public function hExists (string $key, string $field): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 */
	public function hGet (string $key, string $member): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function hGetAll (string $key): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $field
	 * @param int $value
	 */
	public function hIncrBy (string $key, string $field, int $value): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $field
	 * @param float $value
	 */
	public function hIncrByFloat (string $key, string $field, float $value): Redis|float|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function hKeys (string $key): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function hLen (string $key): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array $fields
	 */
	public function hMget (string $key, array $fields): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array $fieldvals
	 */
	public function hMset (string $key, array $fieldvals): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array|null $options [optional]
	 */
	public function hRandField (string $key, ?array $options = NULL): Redis|array|string {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 * @param mixed $value
	 */
	public function hSet (string $key, string $member, mixed $value = null): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $field
	 * @param string $value
	 */
	public function hSetNx (string $key, string $field, string $value): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $field
	 */
	public function hStrLen (string $key, string $field): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function hVals (string $key): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int|null $iterator
	 * @param string|null $pattern [optional]
	 * @param int $count [optional]
	 */
	public function hscan (string $key, ?int &$iterator = null, ?string $pattern = NULL, int $count = 0): Redis|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $by [optional]
	 */
	public function incr (string $key, int $by = 1): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $value
	 */
	public function incrBy (string $key, int $value): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param float $value
	 */
	public function incrByFloat (string $key, float $value): Redis|float|false {}

	/**
	 * {@inheritdoc}
	 * @param string $sections [optional]
	 */
	public function info (string ...$sections): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 */
	public function isConnected (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $pattern
	 */
	public function keys (string $pattern) {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $pos
	 * @param mixed $pivot
	 * @param mixed $value
	 */
	public function lInsert (string $key, string $pos, mixed $pivot = null, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function lLen (string $key): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $src
	 * @param string $dst
	 * @param string $wherefrom
	 * @param string $whereto
	 */
	public function lMove (string $src, string $dst, string $wherefrom, string $whereto): Redis|string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $src
	 * @param string $dst
	 * @param string $wherefrom
	 * @param string $whereto
	 * @param float $timeout
	 */
	public function blmove (string $src, string $dst, string $wherefrom, string $whereto, float $timeout): Redis|string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $count [optional]
	 */
	public function lPop (string $key, int $count = 0): Redis|array|string|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 * @param array|null $options [optional]
	 */
	public function lPos (string $key, mixed $value = null, ?array $options = NULL): Redis|array|int|bool|null {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $elements [optional]
	 */
	public function lPush (string $key, mixed ...$elements): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $elements [optional]
	 */
	public function rPush (string $key, mixed ...$elements): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 */
	public function lPushx (string $key, mixed $value = null): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 */
	public function rPushx (string $key, mixed $value = null): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $index
	 * @param mixed $value
	 */
	public function lSet (string $key, int $index, mixed $value = null): Redis|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function lastSave (): int {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $index
	 */
	public function lindex (string $key, int $index): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $start
	 * @param int $end
	 */
	public function lrange (string $key, int $start, int $end): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 * @param int $count [optional]
	 */
	public function lrem (string $key, mixed $value = null, int $count = 0): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $start
	 * @param int $end
	 */
	public function ltrim (string $key, int $start, int $end): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 */
	public function mget (array $keys): Redis|array {}

	/**
	 * {@inheritdoc}
	 * @param string $host
	 * @param int $port
	 * @param array|string $key
	 * @param int $dstdb
	 * @param int $timeout
	 * @param bool $copy [optional]
	 * @param bool $replace [optional]
	 * @param mixed $credentials [optional]
	 */
	public function migrate (string $host, int $port, array|string $key, int $dstdb, int $timeout, bool $copy = false, bool $replace = false, mixed $credentials = NULL): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $index
	 */
	public function move (string $key, int $index): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param array $key_values
	 */
	public function mset (array $key_values): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param array $key_values
	 */
	public function msetnx (array $key_values): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param int $value [optional]
	 */
	public function multi (int $value = 1): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $subcommand
	 * @param string $key
	 */
	public function object (string $subcommand, string $key): Redis|string|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $host
	 * @param int $port [optional]
	 * @param float $timeout [optional]
	 * @param string|null $persistent_id [optional]
	 * @param int $retry_interval [optional]
	 * @param float $read_timeout [optional]
	 * @param array|null $context [optional]
	 * @deprecated 
	 */
	public function open (string $host, int $port = 6379, float $timeout = 0, ?string $persistent_id = NULL, int $retry_interval = 0, float $read_timeout = 0, ?array $context = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $host
	 * @param int $port [optional]
	 * @param float $timeout [optional]
	 * @param string|null $persistent_id [optional]
	 * @param int $retry_interval [optional]
	 * @param float $read_timeout [optional]
	 * @param array|null $context [optional]
	 */
	public function pconnect (string $host, int $port = 6379, float $timeout = 0, ?string $persistent_id = NULL, int $retry_interval = 0, float $read_timeout = 0, ?array $context = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function persist (string $key): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $timeout
	 * @param string|null $mode [optional]
	 */
	public function pexpire (string $key, int $timeout, ?string $mode = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $timestamp
	 * @param string|null $mode [optional]
	 */
	public function pexpireAt (string $key, int $timestamp, ?string $mode = NULL): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array $elements
	 */
	public function pfadd (string $key, array $elements): Redis|int {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_keys
	 */
	public function pfcount (array|string $key_or_keys): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $dst
	 * @param array $srckeys
	 */
	public function pfmerge (string $dst, array $srckeys): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $message [optional]
	 */
	public function ping (?string $message = NULL): Redis|string|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function pipeline (): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $host
	 * @param int $port [optional]
	 * @param float $timeout [optional]
	 * @param string|null $persistent_id [optional]
	 * @param int $retry_interval [optional]
	 * @param float $read_timeout [optional]
	 * @param array|null $context [optional]
	 * @deprecated 
	 */
	public function popen (string $host, int $port = 6379, float $timeout = 0, ?string $persistent_id = NULL, int $retry_interval = 0, float $read_timeout = 0, ?array $context = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $expire
	 * @param mixed $value
	 */
	public function psetex (string $key, int $expire, mixed $value = null): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param array $patterns
	 * @param callable $cb
	 */
	public function psubscribe (array $patterns, callable $cb): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function pttl (string $key): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $channel
	 * @param string $message
	 */
	public function publish (string $channel, string $message): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $command
	 * @param mixed $arg [optional]
	 */
	public function pubsub (string $command, mixed $arg = NULL): mixed {}

	/**
	 * {@inheritdoc}
	 * @param array $patterns
	 */
	public function punsubscribe (array $patterns): Redis|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $count [optional]
	 */
	public function rPop (string $key, int $count = 0): Redis|array|string|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function randomKey (): Redis|string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $command
	 * @param mixed $args [optional]
	 */
	public function rawcommand (string $command, mixed ...$args): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $old_name
	 * @param string $new_name
	 */
	public function rename (string $old_name, string $new_name): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key_src
	 * @param string $key_dst
	 */
	public function renameNx (string $key_src, string $key_dst): Redis|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function reset (): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $ttl
	 * @param string $value
	 * @param array|null $options [optional]
	 */
	public function restore (string $key, int $ttl, string $value, ?array $options = NULL): Redis|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function role (): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $srckey
	 * @param string $dstkey
	 */
	public function rpoplpush (string $srckey, string $dstkey): Redis|string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 * @param mixed $other_values [optional]
	 */
	public function sAdd (string $key, mixed $value = null, mixed ...$other_values): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array $values
	 */
	public function sAddArray (string $key, array $values): int {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $other_keys [optional]
	 */
	public function sDiff (string $key, string ...$other_keys): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $dst
	 * @param string $key
	 * @param string $other_keys [optional]
	 */
	public function sDiffStore (string $dst, string $key, string ...$other_keys): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param string $other_keys [optional]
	 */
	public function sInter (array|string $key, string ...$other_keys): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 * @param int $limit [optional]
	 */
	public function sintercard (array $keys, int $limit = -1): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param string $other_keys [optional]
	 */
	public function sInterStore (array|string $key, string ...$other_keys): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function sMembers (string $key): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 * @param string $other_members [optional]
	 */
	public function sMisMember (string $key, string $member, string ...$other_members): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $src
	 * @param string $dst
	 * @param mixed $value
	 */
	public function sMove (string $src, string $dst, mixed $value = null): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $count [optional]
	 */
	public function sPop (string $key, int $count = 0): Redis|array|string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $count [optional]
	 */
	public function sRandMember (string $key, int $count = 0): Redis|array|string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $other_keys [optional]
	 */
	public function sUnion (string $key, string ...$other_keys): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $dst
	 * @param string $key
	 * @param string $other_keys [optional]
	 */
	public function sUnionStore (string $dst, string $key, string ...$other_keys): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function save (): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param int|null $iterator
	 * @param string|null $pattern [optional]
	 * @param int $count [optional]
	 * @param string|null $type [optional]
	 */
	public function scan (?int &$iterator = null, ?string $pattern = NULL, int $count = 0, ?string $type = NULL): array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function scard (string $key): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $command
	 * @param mixed $args [optional]
	 */
	public function script (string $command, mixed ...$args): mixed {}

	/**
	 * {@inheritdoc}
	 * @param int $db
	 */
	public function select (int $db): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 * @param mixed $options [optional]
	 */
	public function set (string $key, mixed $value = null, mixed $options = NULL): Redis|string|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $idx
	 * @param bool $value
	 */
	public function setBit (string $key, int $idx, bool $value): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $index
	 * @param string $value
	 */
	public function setRange (string $key, int $index, string $value): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 * @param mixed $value
	 */
	public function setOption (int $option, mixed $value = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $expire
	 * @param mixed $value
	 */
	public function setex (string $key, int $expire, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 */
	public function setnx (string $key, mixed $value = null): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 */
	public function sismember (string $key, mixed $value = null): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $host [optional]
	 * @param int $port [optional]
	 * @deprecated 
	 */
	public function slaveof (?string $host = NULL, int $port = 6379): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $host [optional]
	 * @param int $port [optional]
	 */
	public function replicaof (?string $host = NULL, int $port = 6379): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_array
	 * @param string $more_keys [optional]
	 */
	public function touch (array|string $key_or_array, string ...$more_keys): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $operation
	 * @param int $length [optional]
	 */
	public function slowlog (string $operation, int $length = 0): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array|null $options [optional]
	 */
	public function sort (string $key, ?array $options = NULL): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array|null $options [optional]
	 */
	public function sort_ro (string $key, ?array $options = NULL): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string|null $pattern [optional]
	 * @param mixed $get [optional]
	 * @param int $offset [optional]
	 * @param int $count [optional]
	 * @param string|null $store [optional]
	 * @deprecated 
	 */
	public function sortAsc (string $key, ?string $pattern = NULL, mixed $get = NULL, int $offset = -1, int $count = -1, ?string $store = NULL): array {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string|null $pattern [optional]
	 * @param mixed $get [optional]
	 * @param int $offset [optional]
	 * @param int $count [optional]
	 * @param string|null $store [optional]
	 * @deprecated 
	 */
	public function sortAscAlpha (string $key, ?string $pattern = NULL, mixed $get = NULL, int $offset = -1, int $count = -1, ?string $store = NULL): array {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string|null $pattern [optional]
	 * @param mixed $get [optional]
	 * @param int $offset [optional]
	 * @param int $count [optional]
	 * @param string|null $store [optional]
	 * @deprecated 
	 */
	public function sortDesc (string $key, ?string $pattern = NULL, mixed $get = NULL, int $offset = -1, int $count = -1, ?string $store = NULL): array {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string|null $pattern [optional]
	 * @param mixed $get [optional]
	 * @param int $offset [optional]
	 * @param int $count [optional]
	 * @param string|null $store [optional]
	 * @deprecated 
	 */
	public function sortDescAlpha (string $key, ?string $pattern = NULL, mixed $get = NULL, int $offset = -1, int $count = -1, ?string $store = NULL): array {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 * @param mixed $other_values [optional]
	 */
	public function srem (string $key, mixed $value = null, mixed ...$other_values): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int|null $iterator
	 * @param string|null $pattern [optional]
	 * @param int $count [optional]
	 */
	public function sscan (string $key, ?int &$iterator = null, ?string $pattern = NULL, int $count = 0): array|false {}

	/**
	 * {@inheritdoc}
	 * @param array $channels
	 * @param callable $cb
	 */
	public function ssubscribe (array $channels, callable $cb): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function strlen (string $key): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array $channels
	 * @param callable $cb
	 */
	public function subscribe (array $channels, callable $cb): bool {}

	/**
	 * {@inheritdoc}
	 * @param array $channels
	 */
	public function sunsubscribe (array $channels): Redis|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param int $src
	 * @param int $dst
	 */
	public function swapdb (int $src, int $dst): Redis|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function time (): Redis|array {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function ttl (string $key): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function type (string $key): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param string $other_keys [optional]
	 */
	public function unlink (array|string $key, string ...$other_keys): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array $channels
	 */
	public function unsubscribe (array $channels): Redis|array|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function unwatch (): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param string $other_keys [optional]
	 */
	public function watch (array|string $key, string ...$other_keys): Redis|bool {}

	/**
	 * {@inheritdoc}
	 * @param int $numreplicas
	 * @param int $timeout
	 */
	public function wait (int $numreplicas, int $timeout): int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $group
	 * @param array $ids
	 */
	public function xack (string $key, string $group, array $ids): int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $id
	 * @param array $values
	 * @param int $maxlen [optional]
	 * @param bool $approx [optional]
	 * @param bool $nomkstream [optional]
	 */
	public function xadd (string $key, string $id, array $values, int $maxlen = 0, bool $approx = false, bool $nomkstream = false): Redis|string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $group
	 * @param string $consumer
	 * @param int $min_idle
	 * @param string $start
	 * @param int $count [optional]
	 * @param bool $justid [optional]
	 */
	public function xautoclaim (string $key, string $group, string $consumer, int $min_idle, string $start, int $count = -1, bool $justid = false): Redis|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $group
	 * @param string $consumer
	 * @param int $min_idle
	 * @param array $ids
	 * @param array $options
	 */
	public function xclaim (string $key, string $group, string $consumer, int $min_idle, array $ids, array $options): Redis|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array $ids
	 */
	public function xdel (string $key, array $ids): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $operation
	 * @param string|null $key [optional]
	 * @param string|null $group [optional]
	 * @param string|null $id_or_consumer [optional]
	 * @param bool $mkstream [optional]
	 * @param int $entries_read [optional]
	 */
	public function xgroup (string $operation, ?string $key = NULL, ?string $group = NULL, ?string $id_or_consumer = NULL, bool $mkstream = false, int $entries_read = -2): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $operation
	 * @param string|null $arg1 [optional]
	 * @param string|null $arg2 [optional]
	 * @param int $count [optional]
	 */
	public function xinfo (string $operation, ?string $arg1 = NULL, ?string $arg2 = NULL, int $count = -1): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function xlen (string $key): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $group
	 * @param string|null $start [optional]
	 * @param string|null $end [optional]
	 * @param int $count [optional]
	 * @param string|null $consumer [optional]
	 */
	public function xpending (string $key, string $group, ?string $start = NULL, ?string $end = NULL, int $count = -1, ?string $consumer = NULL): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $start
	 * @param string $end
	 * @param int $count [optional]
	 */
	public function xrange (string $key, string $start, string $end, int $count = -1): Redis|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param array $streams
	 * @param int $count [optional]
	 * @param int $block [optional]
	 */
	public function xread (array $streams, int $count = -1, int $block = -1): Redis|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $group
	 * @param string $consumer
	 * @param array $streams
	 * @param int $count [optional]
	 * @param int $block [optional]
	 */
	public function xreadgroup (string $group, string $consumer, array $streams, int $count = 1, int $block = 1): Redis|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $end
	 * @param string $start
	 * @param int $count [optional]
	 */
	public function xrevrange (string $key, string $end, string $start, int $count = -1): Redis|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $threshold
	 * @param bool $approx [optional]
	 * @param bool $minid [optional]
	 * @param int $limit [optional]
	 */
	public function xtrim (string $key, string $threshold, bool $approx = false, bool $minid = false, int $limit = -1): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array|float $score_or_options
	 * @param mixed $more_scores_and_mems [optional]
	 */
	public function zAdd (string $key, array|float $score_or_options, mixed ...$more_scores_and_mems): Redis|int|float|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function zCard (string $key): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $start
	 * @param string $end
	 */
	public function zCount (string $key, string $start, string $end): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param float $value
	 * @param mixed $member
	 */
	public function zIncrBy (string $key, float $value, mixed $member = null): Redis|float|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $min
	 * @param string $max
	 */
	public function zLexCount (string $key, string $min, string $max): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function zMscore (string $key, mixed $member = null, mixed ...$other_members): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int|null $count [optional]
	 */
	public function zPopMax (string $key, ?int $count = NULL): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int|null $count [optional]
	 */
	public function zPopMin (string $key, ?int $count = NULL): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string|int $start
	 * @param string|int $end
	 * @param array|bool|null $options [optional]
	 */
	public function zRange (string $key, string|int $start, string|int $end, array|bool|null $options = NULL): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $min
	 * @param string $max
	 * @param int $offset [optional]
	 * @param int $count [optional]
	 */
	public function zRangeByLex (string $key, string $min, string $max, int $offset = -1, int $count = -1): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $start
	 * @param string $end
	 * @param array $options [optional]
	 */
	public function zRangeByScore (string $key, string $start, string $end, array $options = array (
)): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $dstkey
	 * @param string $srckey
	 * @param string $start
	 * @param string $end
	 * @param array|bool|null $options [optional]
	 */
	public function zrangestore (string $dstkey, string $srckey, string $start, string $end, array|bool|null $options = NULL): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array|null $options [optional]
	 */
	public function zRandMember (string $key, ?array $options = NULL): Redis|array|string {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $member
	 */
	public function zRank (string $key, mixed $member = null): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function zRem (mixed $key = null, mixed $member = null, mixed ...$other_members): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $min
	 * @param string $max
	 */
	public function zRemRangeByLex (string $key, string $min, string $max): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $start
	 * @param int $end
	 */
	public function zRemRangeByRank (string $key, int $start, int $end): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $start
	 * @param string $end
	 */
	public function zRemRangeByScore (string $key, string $start, string $end): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $start
	 * @param int $end
	 * @param mixed $scores [optional]
	 */
	public function zRevRange (string $key, int $start, int $end, mixed $scores = NULL): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $max
	 * @param string $min
	 * @param int $offset [optional]
	 * @param int $count [optional]
	 */
	public function zRevRangeByLex (string $key, string $max, string $min, int $offset = -1, int $count = -1): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $max
	 * @param string $min
	 * @param array|bool $options [optional]
	 */
	public function zRevRangeByScore (string $key, string $max, string $min, array|bool $options = array (
)): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $member
	 */
	public function zRevRank (string $key, mixed $member = null): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $member
	 */
	public function zScore (string $key, mixed $member = null): Redis|float|false {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 * @param array|null $options [optional]
	 */
	public function zdiff (array $keys, ?array $options = NULL): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $dst
	 * @param array $keys
	 */
	public function zdiffstore (string $dst, array $keys): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 * @param array|null $weights [optional]
	 * @param array|null $options [optional]
	 */
	public function zinter (array $keys, ?array $weights = NULL, ?array $options = NULL): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 * @param int $limit [optional]
	 */
	public function zintercard (array $keys, int $limit = -1): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $dst
	 * @param array $keys
	 * @param array|null $weights [optional]
	 * @param string|null $aggregate [optional]
	 */
	public function zinterstore (string $dst, array $keys, ?array $weights = NULL, ?string $aggregate = NULL): Redis|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int|null $iterator
	 * @param string|null $pattern [optional]
	 * @param int $count [optional]
	 */
	public function zscan (string $key, ?int &$iterator = null, ?string $pattern = NULL, int $count = 0): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 * @param array|null $weights [optional]
	 * @param array|null $options [optional]
	 */
	public function zunion (array $keys, ?array $weights = NULL, ?array $options = NULL): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $dst
	 * @param array $keys
	 * @param array|null $weights [optional]
	 * @param string|null $aggregate [optional]
	 */
	public function zunionstore (string $dst, array $keys, ?array $weights = NULL, ?string $aggregate = NULL): Redis|int|false {}

}

class RedisArray  {

	/**
	 * {@inheritdoc}
	 * @param string $function_name
	 * @param array $arguments
	 */
	public function __call (string $function_name, array $arguments): mixed {}

	/**
	 * {@inheritdoc}
	 * @param array|string $name_or_hosts
	 * @param array|null $options [optional]
	 */
	public function __construct (array|string $name_or_hosts, ?array $options = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function _continuum (): array|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function _distributor (): callable|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function _function (): callable|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function _hosts (): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $host
	 */
	public function _instance (string $host): Redis|bool|null {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $fn [optional]
	 */
	public function _rehash (?callable $fn = NULL): ?bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function _target (string $key): string|bool|null {}

	/**
	 * {@inheritdoc}
	 */
	public function bgsave (): array {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param string $otherkeys [optional]
	 */
	public function del (array|string $key, string ...$otherkeys): int|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function discard (): ?bool {}

	/**
	 * {@inheritdoc}
	 */
	public function exec (): ?bool {}

	/**
	 * {@inheritdoc}
	 */
	public function flushall (): array|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function flushdb (): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param int $opt
	 */
	public function getOption (int $opt): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int|null $iterator
	 * @param string|null $pattern [optional]
	 * @param int $count [optional]
	 */
	public function hscan (string $key, ?int &$iterator = null, ?string $pattern = NULL, int $count = 0): array|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function info (): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $pattern
	 */
	public function keys (string $pattern): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 */
	public function mget (array $keys): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param array $pairs
	 */
	public function mset (array $pairs): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $host
	 * @param int|null $mode [optional]
	 */
	public function multi (string $host, ?int $mode = NULL): RedisArray|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function ping (): array|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function save (): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param int|null $iterator
	 * @param string $node
	 * @param string|null $pattern [optional]
	 * @param int $count [optional]
	 */
	public function scan (?int &$iterator = null, string $node, ?string $pattern = NULL, int $count = 0): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function select (int $index): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param int $opt
	 * @param string $value
	 */
	public function setOption (int $opt, string $value): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int|null $iterator
	 * @param string|null $pattern [optional]
	 * @param int $count [optional]
	 */
	public function sscan (string $key, ?int &$iterator = null, ?string $pattern = NULL, int $count = 0): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param string $otherkeys [optional]
	 */
	public function unlink (array|string $key, string ...$otherkeys): int|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function unwatch (): ?bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int|null $iterator
	 * @param string|null $pattern [optional]
	 * @param int $count [optional]
	 */
	public function zscan (string $key, ?int &$iterator = null, ?string $pattern = NULL, int $count = 0): array|bool {}

}

class RedisCluster  {
	const OPT_SLAVE_FAILOVER = 5;
	const FAILOVER_NONE = 0;
	const FAILOVER_ERROR = 1;
	const FAILOVER_DISTRIBUTE = 2;
	const FAILOVER_DISTRIBUTE_SLAVES = 3;


	/**
	 * {@inheritdoc}
	 * @param string|null $name
	 * @param array|null $seeds [optional]
	 * @param int|float $timeout [optional]
	 * @param int|float $read_timeout [optional]
	 * @param bool $persistent [optional]
	 * @param mixed $auth [optional]
	 * @param array|null $context [optional]
	 */
	public function __construct (?string $name = null, ?array $seeds = NULL, int|float $timeout = 0, int|float $read_timeout = 0, bool $persistent = false, mixed $auth = NULL, ?array $context = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $value
	 */
	public function _compress (string $value): string {}

	/**
	 * {@inheritdoc}
	 * @param string $value
	 */
	public function _uncompress (string $value): string {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function _serialize (mixed $value = null): string|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $value
	 */
	public function _unserialize (string $value): mixed {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function _pack (mixed $value = null): string {}

	/**
	 * {@inheritdoc}
	 * @param string $value
	 */
	public function _unpack (string $value): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function _prefix (string $key): string|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function _masters (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function _redir (): ?string {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 * @param string $subcmd
	 * @param string $args [optional]
	 */
	public function acl (array|string $key_or_address, string $subcmd, string ...$args): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 */
	public function append (string $key, mixed $value = null): RedisCluster|int|bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 */
	public function bgrewriteaof (array|string $key_or_address): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 */
	public function bgsave (array|string $key_or_address): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $start [optional]
	 * @param int $end [optional]
	 * @param bool $bybit [optional]
	 */
	public function bitcount (string $key, int $start = 0, int $end = -1, bool $bybit = false): RedisCluster|int|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $operation
	 * @param string $deskey
	 * @param string $srckey
	 * @param string $otherkeys [optional]
	 */
	public function bitop (string $operation, string $deskey, string $srckey, string ...$otherkeys): RedisCluster|int|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param bool $bit
	 * @param int $start [optional]
	 * @param int $end [optional]
	 * @param bool $bybit [optional]
	 */
	public function bitpos (string $key, bool $bit, int $start = 0, int $end = -1, bool $bybit = false): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param string|int|float $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function blpop (array|string $key, string|int|float $timeout_or_key, mixed ...$extra_args): RedisCluster|array|false|null {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param string|int|float $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function brpop (array|string $key, string|int|float $timeout_or_key, mixed ...$extra_args): RedisCluster|array|false|null {}

	/**
	 * {@inheritdoc}
	 * @param string $srckey
	 * @param string $deskey
	 * @param int $timeout
	 */
	public function brpoplpush (string $srckey, string $deskey, int $timeout): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $src
	 * @param string $dst
	 * @param string $wherefrom
	 * @param string $whereto
	 */
	public function lmove (string $src, string $dst, string $wherefrom, string $whereto): Redis|string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $src
	 * @param string $dst
	 * @param string $wherefrom
	 * @param string $whereto
	 * @param float $timeout
	 */
	public function blmove (string $src, string $dst, string $wherefrom, string $whereto, float $timeout): Redis|string|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param string|int $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function bzpopmax (array|string $key, string|int $timeout_or_key, mixed ...$extra_args): array {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param string|int $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function bzpopmin (array|string $key, string|int $timeout_or_key, mixed ...$extra_args): array {}

	/**
	 * {@inheritdoc}
	 * @param float $timeout
	 * @param array $keys
	 * @param string $from
	 * @param int $count [optional]
	 */
	public function bzmpop (float $timeout, array $keys, string $from, int $count = 1): RedisCluster|array|false|null {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 * @param string $from
	 * @param int $count [optional]
	 */
	public function zmpop (array $keys, string $from, int $count = 1): RedisCluster|array|false|null {}

	/**
	 * {@inheritdoc}
	 * @param float $timeout
	 * @param array $keys
	 * @param string $from
	 * @param int $count [optional]
	 */
	public function blmpop (float $timeout, array $keys, string $from, int $count = 1): RedisCluster|array|false|null {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 * @param string $from
	 * @param int $count [optional]
	 */
	public function lmpop (array $keys, string $from, int $count = 1): RedisCluster|array|false|null {}

	/**
	 * {@inheritdoc}
	 */
	public function clearlasterror (): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 * @param string $subcommand
	 * @param string|null $arg [optional]
	 */
	public function client (array|string $key_or_address, string $subcommand, ?string $arg = NULL): array|string|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function close (): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 * @param string $command
	 * @param mixed $extra_args [optional]
	 */
	public function cluster (array|string $key_or_address, string $command, mixed ...$extra_args): mixed {}

	/**
	 * {@inheritdoc}
	 * @param mixed $extra_args [optional]
	 */
	public function command (mixed ...$extra_args): mixed {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 * @param string $subcommand
	 * @param mixed $extra_args [optional]
	 */
	public function config (array|string $key_or_address, string $subcommand, mixed ...$extra_args): mixed {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 */
	public function dbsize (array|string $key_or_address): RedisCluster|int {}

	/**
	 * {@inheritdoc}
	 * @param string $src
	 * @param string $dst
	 * @param array|null $options [optional]
	 */
	public function copy (string $src, string $dst, ?array $options = NULL): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $by [optional]
	 */
	public function decr (string $key, int $by = 1): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $value
	 */
	public function decrby (string $key, int $value): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param float $value
	 */
	public function decrbyfloat (string $key, float $value): float {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param string $other_keys [optional]
	 */
	public function del (array|string $key, string ...$other_keys): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function discard (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function dump (string $key): RedisCluster|string|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 * @param string $msg
	 */
	public function echo (array|string $key_or_address, string $msg): RedisCluster|string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $script
	 * @param array $args [optional]
	 * @param int $num_keys [optional]
	 */
	public function eval (string $script, array $args = array (
), int $num_keys = 0): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $script
	 * @param array $args [optional]
	 * @param int $num_keys [optional]
	 */
	public function eval_ro (string $script, array $args = array (
), int $num_keys = 0): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $script_sha
	 * @param array $args [optional]
	 * @param int $num_keys [optional]
	 */
	public function evalsha (string $script_sha, array $args = array (
), int $num_keys = 0): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $script_sha
	 * @param array $args [optional]
	 * @param int $num_keys [optional]
	 */
	public function evalsha_ro (string $script_sha, array $args = array (
), int $num_keys = 0): mixed {}

	/**
	 * {@inheritdoc}
	 */
	public function exec (): array|false {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function exists (mixed $key = null, mixed ...$other_keys): RedisCluster|int|bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function touch (mixed $key = null, mixed ...$other_keys): RedisCluster|int|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $timeout
	 * @param string|null $mode [optional]
	 */
	public function expire (string $key, int $timeout, ?string $mode = NULL): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $timestamp
	 * @param string|null $mode [optional]
	 */
	public function expireat (string $key, int $timestamp, ?string $mode = NULL): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function expiretime (string $key): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function pexpiretime (string $key): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 * @param bool $async [optional]
	 */
	public function flushall (array|string $key_or_address, bool $async = false): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 * @param bool $async [optional]
	 */
	public function flushdb (array|string $key_or_address, bool $async = false): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param float $lng
	 * @param float $lat
	 * @param string $member
	 * @param mixed $other_triples_and_options [optional]
	 */
	public function geoadd (string $key, float $lng, float $lat, string $member, mixed ...$other_triples_and_options): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $src
	 * @param string $dest
	 * @param string|null $unit [optional]
	 */
	public function geodist (string $key, string $src, string $dest, ?string $unit = NULL): RedisCluster|float|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 * @param string $other_members [optional]
	 */
	public function geohash (string $key, string $member, string ...$other_members): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 * @param string $other_members [optional]
	 */
	public function geopos (string $key, string $member, string ...$other_members): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param float $lng
	 * @param float $lat
	 * @param float $radius
	 * @param string $unit
	 * @param array $options [optional]
	 */
	public function georadius (string $key, float $lng, float $lat, float $radius, string $unit, array $options = array (
)): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param float $lng
	 * @param float $lat
	 * @param float $radius
	 * @param string $unit
	 * @param array $options [optional]
	 */
	public function georadius_ro (string $key, float $lng, float $lat, float $radius, string $unit, array $options = array (
)): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 * @param float $radius
	 * @param string $unit
	 * @param array $options [optional]
	 */
	public function georadiusbymember (string $key, string $member, float $radius, string $unit, array $options = array (
)): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 * @param float $radius
	 * @param string $unit
	 * @param array $options [optional]
	 */
	public function georadiusbymember_ro (string $key, string $member, float $radius, string $unit, array $options = array (
)): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array|string $position
	 * @param array|int|float $shape
	 * @param string $unit
	 * @param array $options [optional]
	 */
	public function geosearch (string $key, array|string $position, array|int|float $shape, string $unit, array $options = array (
)): RedisCluster|array {}

	/**
	 * {@inheritdoc}
	 * @param string $dst
	 * @param string $src
	 * @param array|string $position
	 * @param array|int|float $shape
	 * @param string $unit
	 * @param array $options [optional]
	 */
	public function geosearchstore (string $dst, string $src, array|string $position, array|int|float $shape, string $unit, array $options = array (
)): RedisCluster|array|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function get (string $key): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $value
	 */
	public function getbit (string $key, int $value): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function getlasterror (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getmode (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 */
	public function getoption (int $option): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $start
	 * @param int $end
	 */
	public function getrange (string $key, int $start, int $end): RedisCluster|string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key1
	 * @param string $key2
	 * @param array|null $options [optional]
	 */
	public function lcs (string $key1, string $key2, ?array $options = NULL): RedisCluster|array|string|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 */
	public function getset (string $key, mixed $value = null): RedisCluster|string|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function gettransferredbytes (): array|false {}

	/**
	 * {@inheritdoc}
	 */
	public function cleartransferredbytes (): void {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 * @param string $other_members [optional]
	 */
	public function hdel (string $key, string $member, string ...$other_members): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 */
	public function hexists (string $key, string $member): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 */
	public function hget (string $key, string $member): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function hgetall (string $key): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 * @param int $value
	 */
	public function hincrby (string $key, string $member, int $value): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 * @param float $value
	 */
	public function hincrbyfloat (string $key, string $member, float $value): RedisCluster|float|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function hkeys (string $key): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function hlen (string $key): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array $keys
	 */
	public function hmget (string $key, array $keys): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array $key_values
	 */
	public function hmset (string $key, array $key_values): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int|null $iterator
	 * @param string|null $pattern [optional]
	 * @param int $count [optional]
	 */
	public function hscan (string $key, ?int &$iterator = null, ?string $pattern = NULL, int $count = 0): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array|null $options [optional]
	 */
	public function hrandfield (string $key, ?array $options = NULL): RedisCluster|array|string {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 * @param mixed $value
	 */
	public function hset (string $key, string $member, mixed $value = null): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 * @param mixed $value
	 */
	public function hsetnx (string $key, string $member, mixed $value = null): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $field
	 */
	public function hstrlen (string $key, string $field): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function hvals (string $key): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $by [optional]
	 */
	public function incr (string $key, int $by = 1): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $value
	 */
	public function incrby (string $key, int $value): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param float $value
	 */
	public function incrbyfloat (string $key, float $value): RedisCluster|float|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 * @param string $sections [optional]
	 */
	public function info (array|string $key_or_address, string ...$sections): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $pattern
	 */
	public function keys (string $pattern): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 */
	public function lastsave (array|string $key_or_address): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $index
	 */
	public function lget (string $key, int $index): RedisCluster|string|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $index
	 */
	public function lindex (string $key, int $index): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $pos
	 * @param mixed $pivot
	 * @param mixed $value
	 */
	public function linsert (string $key, string $pos, mixed $pivot = null, mixed $value = null): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function llen (string $key): RedisCluster|int|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $count [optional]
	 */
	public function lpop (string $key, int $count = 0): RedisCluster|array|string|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 * @param array|null $options [optional]
	 */
	public function lpos (string $key, mixed $value = null, ?array $options = NULL): Redis|array|int|bool|null {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 * @param mixed $other_values [optional]
	 */
	public function lpush (string $key, mixed $value = null, mixed ...$other_values): RedisCluster|int|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 */
	public function lpushx (string $key, mixed $value = null): RedisCluster|int|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $start
	 * @param int $end
	 */
	public function lrange (string $key, int $start, int $end): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 * @param int $count [optional]
	 */
	public function lrem (string $key, mixed $value = null, int $count = 0): RedisCluster|int|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $index
	 * @param mixed $value
	 */
	public function lset (string $key, int $index, mixed $value = null): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $start
	 * @param int $end
	 */
	public function ltrim (string $key, int $start, int $end): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 */
	public function mget (array $keys): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param array $key_values
	 */
	public function mset (array $key_values): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param array $key_values
	 */
	public function msetnx (array $key_values): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param int $value [optional]
	 */
	public function multi (int $value = 1): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $subcommand
	 * @param string $key
	 */
	public function object (string $subcommand, string $key): RedisCluster|string|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function persist (string $key): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $timeout
	 * @param string|null $mode [optional]
	 */
	public function pexpire (string $key, int $timeout, ?string $mode = NULL): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $timestamp
	 * @param string|null $mode [optional]
	 */
	public function pexpireat (string $key, int $timestamp, ?string $mode = NULL): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array $elements
	 */
	public function pfadd (string $key, array $elements): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function pfcount (string $key): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array $keys
	 */
	public function pfmerge (string $key, array $keys): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 * @param string|null $message [optional]
	 */
	public function ping (array|string $key_or_address, ?string $message = NULL): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $timeout
	 * @param string $value
	 */
	public function psetex (string $key, int $timeout, string $value): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param array $patterns
	 * @param callable $callback
	 */
	public function psubscribe (array $patterns, callable $callback): void {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function pttl (string $key): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $channel
	 * @param string $message
	 */
	public function publish (string $channel, string $message): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 * @param string $values [optional]
	 */
	public function pubsub (array|string $key_or_address, string ...$values): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $pattern
	 * @param string $other_patterns [optional]
	 */
	public function punsubscribe (string $pattern, string ...$other_patterns): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 */
	public function randomkey (array|string $key_or_address): RedisCluster|string|bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 * @param string $command
	 * @param mixed $args [optional]
	 */
	public function rawcommand (array|string $key_or_address, string $command, mixed ...$args): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key_src
	 * @param string $key_dst
	 */
	public function rename (string $key_src, string $key_dst): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $newkey
	 */
	public function renamenx (string $key, string $newkey): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $timeout
	 * @param string $value
	 * @param array|null $options [optional]
	 */
	public function restore (string $key, int $timeout, string $value, ?array $options = NULL): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 */
	public function role (array|string $key_or_address): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $count [optional]
	 */
	public function rpop (string $key, int $count = 0): RedisCluster|array|string|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $src
	 * @param string $dst
	 */
	public function rpoplpush (string $src, string $dst): RedisCluster|string|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $elements [optional]
	 */
	public function rpush (string $key, mixed ...$elements): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $value
	 */
	public function rpushx (string $key, string $value): RedisCluster|int|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 * @param mixed $other_values [optional]
	 */
	public function sadd (string $key, mixed $value = null, mixed ...$other_values): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array $values
	 */
	public function saddarray (string $key, array $values): RedisCluster|int|bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 */
	public function save (array|string $key_or_address): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param int|null $iterator
	 * @param array|string $key_or_address
	 * @param string|null $pattern [optional]
	 * @param int $count [optional]
	 */
	public function scan (?int &$iterator = null, array|string $key_or_address, ?string $pattern = NULL, int $count = 0): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function scard (string $key): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 * @param mixed $args [optional]
	 */
	public function script (array|string $key_or_address, mixed ...$args): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $other_keys [optional]
	 */
	public function sdiff (string $key, string ...$other_keys): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $dst
	 * @param string $key
	 * @param string $other_keys [optional]
	 */
	public function sdiffstore (string $dst, string $key, string ...$other_keys): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 * @param mixed $options [optional]
	 */
	public function set (string $key, mixed $value = null, mixed $options = NULL): RedisCluster|string|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $offset
	 * @param bool $onoff
	 */
	public function setbit (string $key, int $offset, bool $onoff): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $expire
	 * @param mixed $value
	 */
	public function setex (string $key, int $expire, mixed $value = null): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 */
	public function setnx (string $key, mixed $value = null): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 * @param mixed $value
	 */
	public function setoption (int $option, mixed $value = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $offset
	 * @param string $value
	 */
	public function setrange (string $key, int $offset, string $value): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param string $other_keys [optional]
	 */
	public function sinter (array|string $key, string ...$other_keys): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 * @param int $limit [optional]
	 */
	public function sintercard (array $keys, int $limit = -1): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param string $other_keys [optional]
	 */
	public function sinterstore (array|string $key, string ...$other_keys): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 */
	public function sismember (string $key, mixed $value = null): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $member
	 * @param string $other_members [optional]
	 */
	public function smismember (string $key, string $member, string ...$other_members): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 * @param mixed $args [optional]
	 */
	public function slowlog (array|string $key_or_address, mixed ...$args): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function smembers (string $key): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $src
	 * @param string $dst
	 * @param string $member
	 */
	public function smove (string $src, string $dst, string $member): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array|null $options [optional]
	 */
	public function sort (string $key, ?array $options = NULL): RedisCluster|array|string|int|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array|null $options [optional]
	 */
	public function sort_ro (string $key, ?array $options = NULL): RedisCluster|array|string|int|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $count [optional]
	 */
	public function spop (string $key, int $count = 0): RedisCluster|array|string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $count [optional]
	 */
	public function srandmember (string $key, int $count = 0): RedisCluster|array|string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $value
	 * @param mixed $other_values [optional]
	 */
	public function srem (string $key, mixed $value = null, mixed ...$other_values): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int|null $iterator
	 * @param string|null $pattern [optional]
	 * @param int $count [optional]
	 */
	public function sscan (string $key, ?int &$iterator = null, ?string $pattern = NULL, int $count = 0): array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function strlen (string $key): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array $channels
	 * @param callable $cb
	 */
	public function subscribe (array $channels, callable $cb): void {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $other_keys [optional]
	 */
	public function sunion (string $key, string ...$other_keys): RedisCluster|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $dst
	 * @param string $key
	 * @param string $other_keys [optional]
	 */
	public function sunionstore (string $dst, string $key, string ...$other_keys): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key_or_address
	 */
	public function time (array|string $key_or_address): RedisCluster|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function ttl (string $key): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function type (string $key): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array $channels
	 */
	public function unsubscribe (array $channels): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param string $other_keys [optional]
	 */
	public function unlink (array|string $key, string ...$other_keys): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function unwatch (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $other_keys [optional]
	 */
	public function watch (string $key, string ...$other_keys): RedisCluster|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $group
	 * @param array $ids
	 */
	public function xack (string $key, string $group, array $ids): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $id
	 * @param array $values
	 * @param int $maxlen [optional]
	 * @param bool $approx [optional]
	 */
	public function xadd (string $key, string $id, array $values, int $maxlen = 0, bool $approx = false): RedisCluster|string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $group
	 * @param string $consumer
	 * @param int $min_iddle
	 * @param array $ids
	 * @param array $options
	 */
	public function xclaim (string $key, string $group, string $consumer, int $min_iddle, array $ids, array $options): RedisCluster|array|string|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array $ids
	 */
	public function xdel (string $key, array $ids): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $operation
	 * @param string|null $key [optional]
	 * @param string|null $group [optional]
	 * @param string|null $id_or_consumer [optional]
	 * @param bool $mkstream [optional]
	 * @param int $entries_read [optional]
	 */
	public function xgroup (string $operation, ?string $key = NULL, ?string $group = NULL, ?string $id_or_consumer = NULL, bool $mkstream = false, int $entries_read = -2): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $group
	 * @param string $consumer
	 * @param int $min_idle
	 * @param string $start
	 * @param int $count [optional]
	 * @param bool $justid [optional]
	 */
	public function xautoclaim (string $key, string $group, string $consumer, int $min_idle, string $start, int $count = -1, bool $justid = false): RedisCluster|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $operation
	 * @param string|null $arg1 [optional]
	 * @param string|null $arg2 [optional]
	 * @param int $count [optional]
	 */
	public function xinfo (string $operation, ?string $arg1 = NULL, ?string $arg2 = NULL, int $count = -1): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function xlen (string $key): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $group
	 * @param string|null $start [optional]
	 * @param string|null $end [optional]
	 * @param int $count [optional]
	 * @param string|null $consumer [optional]
	 */
	public function xpending (string $key, string $group, ?string $start = NULL, ?string $end = NULL, int $count = -1, ?string $consumer = NULL): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $start
	 * @param string $end
	 * @param int $count [optional]
	 */
	public function xrange (string $key, string $start, string $end, int $count = -1): RedisCluster|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param array $streams
	 * @param int $count [optional]
	 * @param int $block [optional]
	 */
	public function xread (array $streams, int $count = -1, int $block = -1): RedisCluster|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $group
	 * @param string $consumer
	 * @param array $streams
	 * @param int $count [optional]
	 * @param int $block [optional]
	 */
	public function xreadgroup (string $group, string $consumer, array $streams, int $count = 1, int $block = 1): RedisCluster|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $start
	 * @param string $end
	 * @param int $count [optional]
	 */
	public function xrevrange (string $key, string $start, string $end, int $count = -1): RedisCluster|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $maxlen
	 * @param bool $approx [optional]
	 * @param bool $minid [optional]
	 * @param int $limit [optional]
	 */
	public function xtrim (string $key, int $maxlen, bool $approx = false, bool $minid = false, int $limit = -1): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array|float $score_or_options
	 * @param mixed $more_scores_and_mems [optional]
	 */
	public function zadd (string $key, array|float $score_or_options, mixed ...$more_scores_and_mems): RedisCluster|int|float|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function zcard (string $key): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $start
	 * @param string $end
	 */
	public function zcount (string $key, string $start, string $end): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param float $value
	 * @param string $member
	 */
	public function zincrby (string $key, float $value, string $member): RedisCluster|float|false {}

	/**
	 * {@inheritdoc}
	 * @param string $dst
	 * @param array $keys
	 * @param array|null $weights [optional]
	 * @param string|null $aggregate [optional]
	 */
	public function zinterstore (string $dst, array $keys, ?array $weights = NULL, ?string $aggregate = NULL): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 * @param int $limit [optional]
	 */
	public function zintercard (array $keys, int $limit = -1): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $min
	 * @param string $max
	 */
	public function zlexcount (string $key, string $min, string $max): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int|null $value [optional]
	 */
	public function zpopmax (string $key, ?int $value = NULL): RedisCluster|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int|null $value [optional]
	 */
	public function zpopmin (string $key, ?int $value = NULL): RedisCluster|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param array|bool|null $options [optional]
	 */
	public function zrange (string $key, mixed $start = null, mixed $end = null, array|bool|null $options = NULL): RedisCluster|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $dstkey
	 * @param string $srckey
	 * @param int $start
	 * @param int $end
	 * @param array|bool|null $options [optional]
	 */
	public function zrangestore (string $dstkey, string $srckey, int $start, int $end, array|bool|null $options = NULL): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param array|null $options [optional]
	 */
	public function zrandmember (string $key, ?array $options = NULL): RedisCluster|array|string {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $min
	 * @param string $max
	 * @param int $offset [optional]
	 * @param int $count [optional]
	 */
	public function zrangebylex (string $key, string $min, string $max, int $offset = -1, int $count = -1): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $start
	 * @param string $end
	 * @param array $options [optional]
	 */
	public function zrangebyscore (string $key, string $start, string $end, array $options = array (
)): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $member
	 */
	public function zrank (string $key, mixed $member = null): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $value
	 * @param string $other_values [optional]
	 */
	public function zrem (string $key, string $value, string ...$other_values): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $min
	 * @param string $max
	 */
	public function zremrangebylex (string $key, string $min, string $max): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $min
	 * @param string $max
	 */
	public function zremrangebyrank (string $key, string $min, string $max): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $min
	 * @param string $max
	 */
	public function zremrangebyscore (string $key, string $min, string $max): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $min
	 * @param string $max
	 * @param array|null $options [optional]
	 */
	public function zrevrange (string $key, string $min, string $max, ?array $options = NULL): RedisCluster|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $min
	 * @param string $max
	 * @param array|null $options [optional]
	 */
	public function zrevrangebylex (string $key, string $min, string $max, ?array $options = NULL): RedisCluster|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $min
	 * @param string $max
	 * @param array|null $options [optional]
	 */
	public function zrevrangebyscore (string $key, string $min, string $max, ?array $options = NULL): RedisCluster|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $member
	 */
	public function zrevrank (string $key, mixed $member = null): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int|null $iterator
	 * @param string|null $pattern [optional]
	 * @param int $count [optional]
	 */
	public function zscan (string $key, ?int &$iterator = null, ?string $pattern = NULL, int $count = 0): RedisCluster|array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $member
	 */
	public function zscore (string $key, mixed $member = null): RedisCluster|float|false {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function zmscore (string $key, mixed $member = null, mixed ...$other_members): Redis|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $dst
	 * @param array $keys
	 * @param array|null $weights [optional]
	 * @param string|null $aggregate [optional]
	 */
	public function zunionstore (string $dst, array $keys, ?array $weights = NULL, ?string $aggregate = NULL): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 * @param array|null $weights [optional]
	 * @param array|null $options [optional]
	 */
	public function zinter (array $keys, ?array $weights = NULL, ?array $options = NULL): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param string $dst
	 * @param array $keys
	 */
	public function zdiffstore (string $dst, array $keys): RedisCluster|int|false {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 * @param array|null $weights [optional]
	 * @param array|null $options [optional]
	 */
	public function zunion (array $keys, ?array $weights = NULL, ?array $options = NULL): RedisCluster|array|false {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 * @param array|null $options [optional]
	 */
	public function zdiff (array $keys, ?array $options = NULL): RedisCluster|array|false {}

}

class RedisClusterException extends RuntimeException implements Stringable, Throwable {

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

class RedisSentinel  {

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	public function __construct (?array $options = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $master
	 */
	public function ckquorum (string $master) {}

	/**
	 * {@inheritdoc}
	 * @param string $master
	 */
	public function failover (string $master) {}

	/**
	 * {@inheritdoc}
	 */
	public function flushconfig () {}

	/**
	 * {@inheritdoc}
	 * @param string $master
	 */
	public function getMasterAddrByName (string $master) {}

	/**
	 * {@inheritdoc}
	 * @param string $master
	 */
	public function master (string $master) {}

	/**
	 * {@inheritdoc}
	 */
	public function masters () {}

	/**
	 * {@inheritdoc}
	 */
	public function myid (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function ping () {}

	/**
	 * {@inheritdoc}
	 * @param string $pattern
	 */
	public function reset (string $pattern) {}

	/**
	 * {@inheritdoc}
	 * @param string $master
	 */
	public function sentinels (string $master) {}

	/**
	 * {@inheritdoc}
	 * @param string $master
	 */
	public function slaves (string $master) {}

}

class RedisException extends RuntimeException implements Stringable, Throwable {

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
// End of redis v.6.0.2
