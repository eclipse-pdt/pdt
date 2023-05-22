<?php

// Start of redis v.5.3.7

class Redis  {
	const REDIS_NOT_FOUND = 0;
	const REDIS_STRING = 1;
	const REDIS_SET = 2;
	const REDIS_LIST = 3;
	const REDIS_ZSET = 4;
	const REDIS_HASH = 5;
	const REDIS_STREAM = 6;
	const PIPELINE = 2;
	const ATOMIC = 0;
	const MULTI = 1;
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
	const COMPRESSION_ZSTD_MIN = 1;
	const COMPRESSION_ZSTD_DEFAULT = 3;
	const COMPRESSION_ZSTD_MAX = 22;
	const COMPRESSION_LZ4 = 3;
	const OPT_SCAN = 4;
	const SCAN_RETRY = 1;
	const SCAN_NORETRY = 0;
	const SCAN_PREFIX = 2;
	const SCAN_NOPREFIX = 3;
	const AFTER = "after";
	const BEFORE = "before";
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
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function _prefix ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function _serialize ($value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function _unserialize ($value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function _pack ($value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function _unpack ($value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function _compress ($value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function _uncompress ($value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $subcmd
	 * @param mixed $args [optional]
	 */
	public function acl ($subcmd = null, ...$args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function append ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $auth
	 */
	public function auth ($auth = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function bgSave () {}

	/**
	 * {@inheritdoc}
	 */
	public function bgrewriteaof () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function bitcount ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $operation
	 * @param mixed $ret_key
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function bitop ($operation = null, $ret_key = null, $key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $bit
	 * @param mixed $start [optional]
	 * @param mixed $end [optional]
	 */
	public function bitpos ($key = null, $bit = null, $start = NULL, $end = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function blPop ($key = null, $timeout_or_key = null, ...$extra_args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function brPop ($key = null, $timeout_or_key = null, ...$extra_args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $src
	 * @param mixed $dst
	 * @param mixed $timeout
	 */
	public function brpoplpush ($src = null, $dst = null, $timeout = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function bzPopMax ($key = null, $timeout_or_key = null, ...$extra_args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function bzPopMin ($key = null, $timeout_or_key = null, ...$extra_args) {}

	/**
	 * {@inheritdoc}
	 */
	public function clearLastError () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $cmd
	 * @param mixed $args [optional]
	 */
	public function client ($cmd = null, ...$args) {}

	/**
	 * {@inheritdoc}
	 */
	public function close () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $args [optional]
	 */
	public function command (...$args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $cmd
	 * @param mixed $key
	 * @param mixed $value [optional]
	 */
	public function config ($cmd = null, $key = null, $value = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $host
	 * @param mixed $port [optional]
	 * @param mixed $timeout [optional]
	 * @param mixed $retry_interval [optional]
	 */
	public function connect ($host = null, $port = NULL, $timeout = NULL, $retry_interval = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function dbSize () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function debug ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function decr ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function decrBy ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function del ($key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 */
	public function discard () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function dump ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $msg
	 */
	public function echo ($msg = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $script
	 * @param mixed $args [optional]
	 * @param mixed $num_keys [optional]
	 */
	public function eval ($script = null, $args = NULL, $num_keys = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $script_sha
	 * @param mixed $args [optional]
	 * @param mixed $num_keys [optional]
	 */
	public function evalsha ($script_sha = null, $args = NULL, $num_keys = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function exec () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function exists ($key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $timeout
	 */
	public function expire ($key = null, $timeout = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $timestamp
	 */
	public function expireAt ($key = null, $timestamp = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $async [optional]
	 */
	public function flushAll ($async = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $async [optional]
	 */
	public function flushDB ($async = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $lng
	 * @param mixed $lat
	 * @param mixed $member
	 * @param mixed $other_triples [optional]
	 */
	public function geoadd ($key = null, $lng = null, $lat = null, $member = null, ...$other_triples) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $src
	 * @param mixed $dst
	 * @param mixed $unit [optional]
	 */
	public function geodist ($key = null, $src = null, $dst = null, $unit = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function geohash ($key = null, $member = null, ...$other_members) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function geopos ($key = null, $member = null, ...$other_members) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $lng
	 * @param mixed $lan
	 * @param mixed $radius
	 * @param mixed $unit
	 * @param array $opts [optional]
	 */
	public function georadius ($key = null, $lng = null, $lan = null, $radius = null, $unit = null, array $opts = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $lng
	 * @param mixed $lan
	 * @param mixed $radius
	 * @param mixed $unit
	 * @param array $opts [optional]
	 */
	public function georadius_ro ($key = null, $lng = null, $lan = null, $radius = null, $unit = null, array $opts = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $radius
	 * @param mixed $unit
	 * @param array $opts [optional]
	 */
	public function georadiusbymember ($key = null, $member = null, $radius = null, $unit = null, array $opts = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $radius
	 * @param mixed $unit
	 * @param array $opts [optional]
	 */
	public function georadiusbymember_ro ($key = null, $member = null, $radius = null, $unit = null, array $opts = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function get ($key = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getAuth () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $offset
	 */
	public function getBit ($key = null, $offset = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getDBNum () {}

	/**
	 * {@inheritdoc}
	 */
	public function getHost () {}

	/**
	 * {@inheritdoc}
	 */
	public function getLastError () {}

	/**
	 * {@inheritdoc}
	 */
	public function getMode () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $option
	 */
	public function getOption ($option = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getPersistentID () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPort () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 */
	public function getRange ($key = null, $start = null, $end = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getReadTimeout () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function getSet ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimeout () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function hDel ($key = null, $member = null, ...$other_members) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function hExists ($key = null, $member = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function hGet ($key = null, $member = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function hGetAll ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hIncrBy ($key = null, $member = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hIncrByFloat ($key = null, $member = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function hKeys ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function hLen ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param array $keys
	 */
	public function hMget ($key = null, array $keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param array $pairs
	 */
	public function hMset ($key = null, array $pairs) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hSet ($key = null, $member = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hSetNx ($key = null, $member = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function hStrLen ($key = null, $member = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function hVals ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function hscan ($str_key = null, &$i_iterator = null, $str_pattern = NULL, $i_count = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function incr ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function incrBy ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function incrByFloat ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $option [optional]
	 */
	public function info ($option = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function isConnected () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $pattern
	 */
	public function keys ($pattern = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $position
	 * @param mixed $pivot
	 * @param mixed $value
	 */
	public function lInsert ($key = null, $position = null, $pivot = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function lLen ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function lPop ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function lPush ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function lPushx ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $index
	 * @param mixed $value
	 */
	public function lSet ($key = null, $index = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function lastSave () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $index
	 */
	public function lindex ($key = null, $index = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 */
	public function lrange ($key = null, $start = null, $end = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $count
	 */
	public function lrem ($key = null, $value = null, $count = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $stop
	 */
	public function ltrim ($key = null, $start = null, $stop = null) {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 */
	public function mget (array $keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $host
	 * @param mixed $port
	 * @param mixed $key
	 * @param mixed $db
	 * @param mixed $timeout
	 * @param mixed $copy [optional]
	 * @param mixed $replace [optional]
	 */
	public function migrate ($host = null, $port = null, $key = null, $db = null, $timeout = null, $copy = NULL, $replace = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $dbindex
	 */
	public function move ($key = null, $dbindex = null) {}

	/**
	 * {@inheritdoc}
	 * @param array $pairs
	 */
	public function mset (array $pairs) {}

	/**
	 * {@inheritdoc}
	 * @param array $pairs
	 */
	public function msetnx (array $pairs) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $mode [optional]
	 */
	public function multi ($mode = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $field
	 * @param mixed $key
	 */
	public function object ($field = null, $key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $host
	 * @param mixed $port [optional]
	 * @param mixed $timeout [optional]
	 */
	public function pconnect ($host = null, $port = NULL, $timeout = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function persist ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $timestamp
	 */
	public function pexpire ($key = null, $timestamp = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $timestamp
	 */
	public function pexpireAt ($key = null, $timestamp = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param array $elements
	 */
	public function pfadd ($key = null, array $elements) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function pfcount ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $dstkey
	 * @param array $keys
	 */
	public function pfmerge ($dstkey = null, array $keys) {}

	/**
	 * {@inheritdoc}
	 */
	public function ping () {}

	/**
	 * {@inheritdoc}
	 */
	public function pipeline () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $expire
	 * @param mixed $value
	 */
	public function psetex ($key = null, $expire = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param array $patterns
	 * @param mixed $callback
	 */
	public function psubscribe (array $patterns, $callback = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function pttl ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $channel
	 * @param mixed $message
	 */
	public function publish ($channel = null, $message = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $cmd
	 * @param mixed $args [optional]
	 */
	public function pubsub ($cmd = null, ...$args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $pattern
	 * @param mixed $other_patterns [optional]
	 */
	public function punsubscribe ($pattern = null, ...$other_patterns) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function rPop ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function rPush ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function rPushx ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function randomKey () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $cmd
	 * @param mixed $args [optional]
	 */
	public function rawcommand ($cmd = null, ...$args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $newkey
	 */
	public function rename ($key = null, $newkey = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $newkey
	 */
	public function renameNx ($key = null, $newkey = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $ttl
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function restore ($ttl = null, $key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function role () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $src
	 * @param mixed $dst
	 */
	public function rpoplpush ($src = null, $dst = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function sAdd ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param array $options
	 */
	public function sAddArray ($key = null, array $options) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sDiff ($key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $dst
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sDiffStore ($dst = null, $key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sInter ($key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $dst
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sInterStore ($dst = null, $key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function sMembers ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function sMisMember ($key = null, $member = null, ...$other_members) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $src
	 * @param mixed $dst
	 * @param mixed $value
	 */
	public function sMove ($src = null, $dst = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function sPop ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $count [optional]
	 */
	public function sRandMember ($key = null, $count = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sUnion ($key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $dst
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sUnionStore ($dst = null, $key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 */
	public function save () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function scan (&$i_iterator = null, $str_pattern = NULL, $i_count = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function scard ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $cmd
	 * @param mixed $args [optional]
	 */
	public function script ($cmd = null, ...$args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $dbindex
	 */
	public function select ($dbindex = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $opts [optional]
	 */
	public function set ($key = null, $value = null, $opts = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function setBit ($key = null, $offset = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $option
	 * @param mixed $value
	 */
	public function setOption ($option = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function setRange ($key = null, $offset = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $expire
	 * @param mixed $value
	 */
	public function setex ($key = null, $expire = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function setnx ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function sismember ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $host [optional]
	 * @param mixed $port [optional]
	 */
	public function slaveof ($host = NULL, $port = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $arg
	 * @param mixed $option [optional]
	 */
	public function slowlog ($arg = null, $option = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param array $options [optional]
	 */
	public function sort ($key = null, array $options = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $pattern [optional]
	 * @param mixed $get [optional]
	 * @param mixed $start [optional]
	 * @param mixed $end [optional]
	 * @param mixed $getList [optional]
	 * @deprecated 
	 */
	public function sortAsc ($key = null, $pattern = NULL, $get = NULL, $start = NULL, $end = NULL, $getList = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $pattern [optional]
	 * @param mixed $get [optional]
	 * @param mixed $start [optional]
	 * @param mixed $end [optional]
	 * @param mixed $getList [optional]
	 * @deprecated 
	 */
	public function sortAscAlpha ($key = null, $pattern = NULL, $get = NULL, $start = NULL, $end = NULL, $getList = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $pattern [optional]
	 * @param mixed $get [optional]
	 * @param mixed $start [optional]
	 * @param mixed $end [optional]
	 * @param mixed $getList [optional]
	 * @deprecated 
	 */
	public function sortDesc ($key = null, $pattern = NULL, $get = NULL, $start = NULL, $end = NULL, $getList = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $pattern [optional]
	 * @param mixed $get [optional]
	 * @param mixed $start [optional]
	 * @param mixed $end [optional]
	 * @param mixed $getList [optional]
	 * @deprecated 
	 */
	public function sortDescAlpha ($key = null, $pattern = NULL, $get = NULL, $start = NULL, $end = NULL, $getList = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function srem ($key = null, $member = null, ...$other_members) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function sscan ($str_key = null, &$i_iterator = null, $str_pattern = NULL, $i_count = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function strlen ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param array $channels
	 * @param mixed $callback
	 */
	public function subscribe (array $channels, $callback = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $srcdb
	 * @param mixed $dstdb
	 */
	public function swapdb ($srcdb = null, $dstdb = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function time () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function ttl ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function type ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function unlink ($key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $channel
	 * @param mixed $other_channels [optional]
	 */
	public function unsubscribe ($channel = null, ...$other_channels) {}

	/**
	 * {@inheritdoc}
	 */
	public function unwatch () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $numslaves
	 * @param mixed $timeout
	 */
	public function wait ($numslaves = null, $timeout = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function watch ($key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $str_group
	 * @param array $arr_ids
	 */
	public function xack ($str_key = null, $str_group = null, array $arr_ids) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $str_id
	 * @param array $arr_fields
	 * @param mixed $i_maxlen [optional]
	 * @param mixed $boo_approximate [optional]
	 */
	public function xadd ($str_key = null, $str_id = null, array $arr_fields, $i_maxlen = NULL, $boo_approximate = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $str_group
	 * @param mixed $str_consumer
	 * @param mixed $i_min_idle
	 * @param array $arr_ids
	 * @param array $arr_opts [optional]
	 */
	public function xclaim ($str_key = null, $str_group = null, $str_consumer = null, $i_min_idle = null, array $arr_ids, array $arr_opts = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param array $arr_ids
	 */
	public function xdel ($str_key = null, array $arr_ids) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_operation
	 * @param mixed $str_key [optional]
	 * @param mixed $str_arg1 [optional]
	 * @param mixed $str_arg2 [optional]
	 * @param mixed $str_arg3 [optional]
	 */
	public function xgroup ($str_operation = null, $str_key = NULL, $str_arg1 = NULL, $str_arg2 = NULL, $str_arg3 = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_cmd
	 * @param mixed $str_key [optional]
	 * @param mixed $str_group [optional]
	 */
	public function xinfo ($str_cmd = null, $str_key = NULL, $str_group = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function xlen ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $str_group
	 * @param mixed $str_start [optional]
	 * @param mixed $str_end [optional]
	 * @param mixed $i_count [optional]
	 * @param mixed $str_consumer [optional]
	 */
	public function xpending ($str_key = null, $str_group = null, $str_start = NULL, $str_end = NULL, $i_count = NULL, $str_consumer = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $str_start
	 * @param mixed $str_end
	 * @param mixed $i_count [optional]
	 */
	public function xrange ($str_key = null, $str_start = null, $str_end = null, $i_count = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param array $arr_streams
	 * @param mixed $i_count [optional]
	 * @param mixed $i_block [optional]
	 */
	public function xread (array $arr_streams, $i_count = NULL, $i_block = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_group
	 * @param mixed $str_consumer
	 * @param array $arr_streams
	 * @param mixed $i_count [optional]
	 * @param mixed $i_block [optional]
	 */
	public function xreadgroup ($str_group = null, $str_consumer = null, array $arr_streams, $i_count = NULL, $i_block = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $str_start
	 * @param mixed $str_end
	 * @param mixed $i_count [optional]
	 */
	public function xrevrange ($str_key = null, $str_start = null, $str_end = null, $i_count = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $i_maxlen
	 * @param mixed $boo_approximate [optional]
	 */
	public function xtrim ($str_key = null, $i_maxlen = null, $boo_approximate = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $score
	 * @param mixed $value
	 * @param mixed $extra_args [optional]
	 */
	public function zAdd ($key = null, $score = null, $value = null, ...$extra_args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function zCard ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zCount ($key = null, $min = null, $max = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $member
	 */
	public function zIncrBy ($key = null, $value = null, $member = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zLexCount ($key = null, $min = null, $max = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function zPopMax ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function zPopMin ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param mixed $scores [optional]
	 */
	public function zRange ($key = null, $start = null, $end = null, $scores = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 * @param mixed $offset [optional]
	 * @param mixed $limit [optional]
	 */
	public function zRangeByLex ($key = null, $min = null, $max = null, $offset = NULL, $limit = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param array $options [optional]
	 */
	public function zRangeByScore ($key = null, $start = null, $end = null, array $options = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function zRank ($key = null, $member = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function zRem ($key = null, $member = null, ...$other_members) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zRemRangeByLex ($key = null, $min = null, $max = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 */
	public function zRemRangeByRank ($key = null, $start = null, $end = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zRemRangeByScore ($key = null, $min = null, $max = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param mixed $scores [optional]
	 */
	public function zRevRange ($key = null, $start = null, $end = null, $scores = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 * @param mixed $offset [optional]
	 * @param mixed $limit [optional]
	 */
	public function zRevRangeByLex ($key = null, $min = null, $max = null, $offset = NULL, $limit = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param array $options [optional]
	 */
	public function zRevRangeByScore ($key = null, $start = null, $end = null, array $options = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function zRevRank ($key = null, $member = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function zScore ($key = null, $member = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param array $keys
	 * @param array|null $weights [optional]
	 * @param mixed $aggregate [optional]
	 */
	public function zinterstore ($key = null, array $keys, ?array $weights = NULL, $aggregate = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function zscan ($str_key = null, &$i_iterator = null, $str_pattern = NULL, $i_count = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param array $keys
	 * @param array|null $weights [optional]
	 * @param mixed $aggregate [optional]
	 */
	public function zunionstore ($key = null, array $keys, ?array $weights = NULL, $aggregate = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 * @deprecated 
	 */
	public function delete ($key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $script
	 * @param mixed $args [optional]
	 * @param mixed $num_keys [optional]
	 * @deprecated 
	 */
	public function evaluate ($script = null, $args = NULL, $num_keys = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $script_sha
	 * @param mixed $args [optional]
	 * @param mixed $num_keys [optional]
	 * @deprecated 
	 */
	public function evaluateSha ($script_sha = null, $args = NULL, $num_keys = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $pattern
	 * @deprecated 
	 */
	public function getKeys ($pattern = null) {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 * @deprecated 
	 */
	public function getMultiple (array $keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $index
	 * @deprecated 
	 */
	public function lGet ($key = null, $index = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @deprecated 
	 */
	public function lGetRange ($key = null, $start = null, $end = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $count
	 * @deprecated 
	 */
	public function lRemove ($key = null, $value = null, $count = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @deprecated 
	 */
	public function lSize ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $stop
	 * @deprecated 
	 */
	public function listTrim ($key = null, $start = null, $stop = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $host
	 * @param mixed $port [optional]
	 * @param mixed $timeout [optional]
	 * @param mixed $retry_interval [optional]
	 * @deprecated 
	 */
	public function open ($host = null, $port = NULL, $timeout = NULL, $retry_interval = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $host
	 * @param mixed $port [optional]
	 * @param mixed $timeout [optional]
	 * @deprecated 
	 */
	public function popen ($host = null, $port = NULL, $timeout = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $newkey
	 * @deprecated 
	 */
	public function renameKey ($key = null, $newkey = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 * @deprecated 
	 */
	public function sContains ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @deprecated 
	 */
	public function sGetMembers ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 * @deprecated 
	 */
	public function sRemove ($key = null, $member = null, ...$other_members) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @deprecated 
	 */
	public function sSize ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $msg
	 * @deprecated 
	 */
	public function sendEcho ($msg = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $timeout
	 * @deprecated 
	 */
	public function setTimeout ($key = null, $timeout = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @deprecated 
	 */
	public function substr ($key = null, $start = null, $end = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 * @deprecated 
	 */
	public function zDelete ($key = null, $member = null, ...$other_members) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 * @deprecated 
	 */
	public function zDeleteRangeByRank ($key = null, $min = null, $max = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 * @deprecated 
	 */
	public function zDeleteRangeByScore ($key = null, $min = null, $max = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param array $keys
	 * @param array|null $weights [optional]
	 * @param mixed $aggregate [optional]
	 * @deprecated 
	 */
	public function zInter ($key = null, array $keys, ?array $weights = NULL, $aggregate = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 * @deprecated 
	 */
	public function zRemove ($key = null, $member = null, ...$other_members) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 * @deprecated 
	 */
	public function zRemoveRangeByScore ($key = null, $min = null, $max = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param mixed $scores [optional]
	 * @deprecated 
	 */
	public function zReverseRange ($key = null, $start = null, $end = null, $scores = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @deprecated 
	 */
	public function zSize ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param array $keys
	 * @param array|null $weights [optional]
	 * @param mixed $aggregate [optional]
	 * @deprecated 
	 */
	public function zUnion ($key = null, array $keys, ?array $weights = NULL, $aggregate = NULL) {}

}

class RedisArray  {

	/**
	 * {@inheritdoc}
	 * @param mixed $function_name
	 * @param mixed $arguments
	 */
	public function __call ($function_name = null, $arguments = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name_or_hosts
	 * @param array $options [optional]
	 */
	public function __construct ($name_or_hosts = null, array $options = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function _continuum () {}

	/**
	 * {@inheritdoc}
	 */
	public function _distributor () {}

	/**
	 * {@inheritdoc}
	 */
	public function _function () {}

	/**
	 * {@inheritdoc}
	 */
	public function _hosts () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $host
	 */
	public function _instance ($host = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $callable [optional]
	 */
	public function _rehash ($callable = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function _target ($key = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function bgsave () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $keys
	 */
	public function del ($keys = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function discard () {}

	/**
	 * {@inheritdoc}
	 */
	public function exec () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $async [optional]
	 */
	public function flushall ($async = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $async [optional]
	 */
	public function flushdb ($async = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $opt
	 */
	public function getOption ($opt = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function hscan ($str_key = null, &$i_iterator = null, $str_pattern = NULL, $i_count = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function info () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $pattern
	 */
	public function keys ($pattern = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $keys
	 */
	public function mget ($keys = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $pairs
	 */
	public function mset ($pairs = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $host
	 * @param mixed $mode [optional]
	 */
	public function multi ($host = null, $mode = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function ping () {}

	/**
	 * {@inheritdoc}
	 */
	public function save () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $iterator
	 * @param mixed $node
	 * @param mixed $pattern [optional]
	 * @param mixed $count [optional]
	 */
	public function scan (&$iterator = null, $node = null, $pattern = NULL, $count = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 */
	public function select ($index = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $opt
	 * @param mixed $value
	 */
	public function setOption ($opt = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function sscan ($str_key = null, &$i_iterator = null, $str_pattern = NULL, $i_count = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function unlink () {}

	/**
	 * {@inheritdoc}
	 */
	public function unwatch () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function zscan ($str_key = null, &$i_iterator = null, $str_pattern = NULL, $i_count = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $keys
	 */
	public function delete ($keys = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $keys
	 */
	public function getMultiple ($keys = null) {}

}

class RedisCluster  {
	const REDIS_NOT_FOUND = 0;
	const REDIS_STRING = 1;
	const REDIS_SET = 2;
	const REDIS_LIST = 3;
	const REDIS_ZSET = 4;
	const REDIS_HASH = 5;
	const REDIS_STREAM = 6;
	const ATOMIC = 0;
	const MULTI = 1;
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
	const COMPRESSION_ZSTD_MIN = 1;
	const COMPRESSION_ZSTD_DEFAULT = 3;
	const COMPRESSION_ZSTD_MAX = 22;
	const COMPRESSION_LZ4 = 3;
	const OPT_SCAN = 4;
	const SCAN_RETRY = 1;
	const SCAN_NORETRY = 0;
	const SCAN_PREFIX = 2;
	const SCAN_NOPREFIX = 3;
	const AFTER = "after";
	const BEFORE = "before";
	const OPT_SLAVE_FAILOVER = 5;
	const FAILOVER_NONE = 0;
	const FAILOVER_ERROR = 1;
	const FAILOVER_DISTRIBUTE = 2;
	const FAILOVER_DISTRIBUTE_SLAVES = 3;
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
	 * @param mixed $name
	 * @param array $seeds [optional]
	 * @param mixed $timeout [optional]
	 * @param mixed $read_timeout [optional]
	 * @param mixed $persistent [optional]
	 * @param mixed $auth [optional]
	 */
	public function __construct ($name = null, array $seeds = NULL, $timeout = NULL, $read_timeout = NULL, $persistent = NULL, $auth = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function _masters () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function _prefix ($key = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function _redir () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function _serialize ($value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function _unserialize ($value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function _compress ($value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function _uncompress ($value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function _pack ($value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function _unpack ($value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key_or_address
	 * @param mixed $subcmd
	 * @param mixed $args [optional]
	 */
	public function acl ($key_or_address = null, $subcmd = null, ...$args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function append ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key_or_address
	 */
	public function bgrewriteaof ($key_or_address = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key_or_address
	 */
	public function bgsave ($key_or_address = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function bitcount ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $operation
	 * @param mixed $ret_key
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function bitop ($operation = null, $ret_key = null, $key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $bit
	 * @param mixed $start [optional]
	 * @param mixed $end [optional]
	 */
	public function bitpos ($key = null, $bit = null, $start = NULL, $end = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function blpop ($key = null, $timeout_or_key = null, ...$extra_args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function brpop ($key = null, $timeout_or_key = null, ...$extra_args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $src
	 * @param mixed $dst
	 * @param mixed $timeout
	 */
	public function brpoplpush ($src = null, $dst = null, $timeout = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function clearlasterror () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function bzpopmax ($key = null, $timeout_or_key = null, ...$extra_args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function bzpopmin ($key = null, $timeout_or_key = null, ...$extra_args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key_or_address
	 * @param mixed $arg [optional]
	 * @param mixed $other_args [optional]
	 */
	public function client ($key_or_address = null, $arg = NULL, ...$other_args) {}

	/**
	 * {@inheritdoc}
	 */
	public function close () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key_or_address
	 * @param mixed $arg [optional]
	 * @param mixed $other_args [optional]
	 */
	public function cluster ($key_or_address = null, $arg = NULL, ...$other_args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $args [optional]
	 */
	public function command (...$args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key_or_address
	 * @param mixed $arg [optional]
	 * @param mixed $other_args [optional]
	 */
	public function config ($key_or_address = null, $arg = NULL, ...$other_args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key_or_address
	 */
	public function dbsize ($key_or_address = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function decr ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function decrby ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function del ($key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 */
	public function discard () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function dump ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $msg
	 */
	public function echo ($msg = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $script
	 * @param mixed $args [optional]
	 * @param mixed $num_keys [optional]
	 */
	public function eval ($script = null, $args = NULL, $num_keys = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $script_sha
	 * @param mixed $args [optional]
	 * @param mixed $num_keys [optional]
	 */
	public function evalsha ($script_sha = null, $args = NULL, $num_keys = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function exec () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function exists ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $timeout
	 */
	public function expire ($key = null, $timeout = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $timestamp
	 */
	public function expireat ($key = null, $timestamp = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key_or_address
	 * @param mixed $async [optional]
	 */
	public function flushall ($key_or_address = null, $async = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key_or_address
	 * @param mixed $async [optional]
	 */
	public function flushdb ($key_or_address = null, $async = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $lng
	 * @param mixed $lat
	 * @param mixed $member
	 * @param mixed $other_triples [optional]
	 */
	public function geoadd ($key = null, $lng = null, $lat = null, $member = null, ...$other_triples) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $src
	 * @param mixed $dst
	 * @param mixed $unit [optional]
	 */
	public function geodist ($key = null, $src = null, $dst = null, $unit = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function geohash ($key = null, $member = null, ...$other_members) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function geopos ($key = null, $member = null, ...$other_members) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $lng
	 * @param mixed $lan
	 * @param mixed $radius
	 * @param mixed $unit
	 * @param array $opts [optional]
	 */
	public function georadius ($key = null, $lng = null, $lan = null, $radius = null, $unit = null, array $opts = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $lng
	 * @param mixed $lan
	 * @param mixed $radius
	 * @param mixed $unit
	 * @param array $opts [optional]
	 */
	public function georadius_ro ($key = null, $lng = null, $lan = null, $radius = null, $unit = null, array $opts = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $radius
	 * @param mixed $unit
	 * @param array $opts [optional]
	 */
	public function georadiusbymember ($key = null, $member = null, $radius = null, $unit = null, array $opts = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $radius
	 * @param mixed $unit
	 * @param array $opts [optional]
	 */
	public function georadiusbymember_ro ($key = null, $member = null, $radius = null, $unit = null, array $opts = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function get ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $offset
	 */
	public function getbit ($key = null, $offset = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getlasterror () {}

	/**
	 * {@inheritdoc}
	 */
	public function getmode () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $option
	 */
	public function getoption ($option = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 */
	public function getrange ($key = null, $start = null, $end = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function getset ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function hdel ($key = null, $member = null, ...$other_members) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function hexists ($key = null, $member = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function hget ($key = null, $member = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function hgetall ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hincrby ($key = null, $member = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hincrbyfloat ($key = null, $member = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function hkeys ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function hlen ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param array $keys
	 */
	public function hmget ($key = null, array $keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param array $pairs
	 */
	public function hmset ($key = null, array $pairs) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function hscan ($str_key = null, &$i_iterator = null, $str_pattern = NULL, $i_count = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hset ($key = null, $member = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hsetnx ($key = null, $member = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function hstrlen ($key = null, $member = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function hvals ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function incr ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function incrby ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function incrbyfloat ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key_or_address
	 * @param mixed $option [optional]
	 */
	public function info ($key_or_address = null, $option = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $pattern
	 */
	public function keys ($pattern = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key_or_address
	 */
	public function lastsave ($key_or_address = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $index
	 */
	public function lget ($key = null, $index = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $index
	 */
	public function lindex ($key = null, $index = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $position
	 * @param mixed $pivot
	 * @param mixed $value
	 */
	public function linsert ($key = null, $position = null, $pivot = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function llen ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function lpop ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function lpush ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function lpushx ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 */
	public function lrange ($key = null, $start = null, $end = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function lrem ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $index
	 * @param mixed $value
	 */
	public function lset ($key = null, $index = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $stop
	 */
	public function ltrim ($key = null, $start = null, $stop = null) {}

	/**
	 * {@inheritdoc}
	 * @param array $keys
	 */
	public function mget (array $keys) {}

	/**
	 * {@inheritdoc}
	 * @param array $pairs
	 */
	public function mset (array $pairs) {}

	/**
	 * {@inheritdoc}
	 * @param array $pairs
	 */
	public function msetnx (array $pairs) {}

	/**
	 * {@inheritdoc}
	 */
	public function multi () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $field
	 * @param mixed $key
	 */
	public function object ($field = null, $key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function persist ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $timestamp
	 */
	public function pexpire ($key = null, $timestamp = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $timestamp
	 */
	public function pexpireat ($key = null, $timestamp = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param array $elements
	 */
	public function pfadd ($key = null, array $elements) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function pfcount ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $dstkey
	 * @param array $keys
	 */
	public function pfmerge ($dstkey = null, array $keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key_or_address
	 */
	public function ping ($key_or_address = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $expire
	 * @param mixed $value
	 */
	public function psetex ($key = null, $expire = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param array $patterns
	 * @param mixed $callback
	 */
	public function psubscribe (array $patterns, $callback = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function pttl ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $channel
	 * @param mixed $message
	 */
	public function publish ($channel = null, $message = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key_or_address
	 * @param mixed $arg [optional]
	 * @param mixed $other_args [optional]
	 */
	public function pubsub ($key_or_address = null, $arg = NULL, ...$other_args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $pattern
	 * @param mixed $other_patterns [optional]
	 */
	public function punsubscribe ($pattern = null, ...$other_patterns) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key_or_address
	 */
	public function randomkey ($key_or_address = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $cmd
	 * @param mixed $args [optional]
	 */
	public function rawcommand ($cmd = null, ...$args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $newkey
	 */
	public function rename ($key = null, $newkey = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $newkey
	 */
	public function renamenx ($key = null, $newkey = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $ttl
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function restore ($ttl = null, $key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function role () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function rpop ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $src
	 * @param mixed $dst
	 */
	public function rpoplpush ($src = null, $dst = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function rpush ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function rpushx ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function sadd ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param array $options
	 */
	public function saddarray ($key = null, array $options) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key_or_address
	 */
	public function save ($key_or_address = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $i_iterator
	 * @param mixed $str_node
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function scan (&$i_iterator = null, $str_node = null, $str_pattern = NULL, $i_count = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function scard ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key_or_address
	 * @param mixed $arg [optional]
	 * @param mixed $other_args [optional]
	 */
	public function script ($key_or_address = null, $arg = NULL, ...$other_args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sdiff ($key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $dst
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sdiffstore ($dst = null, $key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $opts [optional]
	 */
	public function set ($key = null, $value = null, $opts = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function setbit ($key = null, $offset = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $expire
	 * @param mixed $value
	 */
	public function setex ($key = null, $expire = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function setnx ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $option
	 * @param mixed $value
	 */
	public function setoption ($option = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function setrange ($key = null, $offset = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sinter ($key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $dst
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sinterstore ($dst = null, $key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function sismember ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key_or_address
	 * @param mixed $arg [optional]
	 * @param mixed $other_args [optional]
	 */
	public function slowlog ($key_or_address = null, $arg = NULL, ...$other_args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function smembers ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $src
	 * @param mixed $dst
	 * @param mixed $value
	 */
	public function smove ($src = null, $dst = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param array $options [optional]
	 */
	public function sort ($key = null, array $options = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function spop ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $count [optional]
	 */
	public function srandmember ($key = null, $count = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function srem ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function sscan ($str_key = null, &$i_iterator = null, $str_pattern = NULL, $i_count = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function strlen ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param array $channels
	 * @param mixed $callback
	 */
	public function subscribe (array $channels, $callback = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sunion ($key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $dst
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sunionstore ($dst = null, $key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 */
	public function time () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function ttl ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function type ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $channel
	 * @param mixed $other_channels [optional]
	 */
	public function unsubscribe ($channel = null, ...$other_channels) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function unlink ($key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 */
	public function unwatch () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function watch ($key = null, ...$other_keys) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $str_group
	 * @param array $arr_ids
	 */
	public function xack ($str_key = null, $str_group = null, array $arr_ids) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $str_id
	 * @param array $arr_fields
	 * @param mixed $i_maxlen [optional]
	 * @param mixed $boo_approximate [optional]
	 */
	public function xadd ($str_key = null, $str_id = null, array $arr_fields, $i_maxlen = NULL, $boo_approximate = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $str_group
	 * @param mixed $str_consumer
	 * @param mixed $i_min_idle
	 * @param array $arr_ids
	 * @param array $arr_opts [optional]
	 */
	public function xclaim ($str_key = null, $str_group = null, $str_consumer = null, $i_min_idle = null, array $arr_ids, array $arr_opts = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param array $arr_ids
	 */
	public function xdel ($str_key = null, array $arr_ids) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_operation
	 * @param mixed $str_key [optional]
	 * @param mixed $str_arg1 [optional]
	 * @param mixed $str_arg2 [optional]
	 * @param mixed $str_arg3 [optional]
	 */
	public function xgroup ($str_operation = null, $str_key = NULL, $str_arg1 = NULL, $str_arg2 = NULL, $str_arg3 = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_cmd
	 * @param mixed $str_key [optional]
	 * @param mixed $str_group [optional]
	 */
	public function xinfo ($str_cmd = null, $str_key = NULL, $str_group = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function xlen ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $str_group
	 * @param mixed $str_start [optional]
	 * @param mixed $str_end [optional]
	 * @param mixed $i_count [optional]
	 * @param mixed $str_consumer [optional]
	 */
	public function xpending ($str_key = null, $str_group = null, $str_start = NULL, $str_end = NULL, $i_count = NULL, $str_consumer = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $str_start
	 * @param mixed $str_end
	 * @param mixed $i_count [optional]
	 */
	public function xrange ($str_key = null, $str_start = null, $str_end = null, $i_count = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param array $arr_streams
	 * @param mixed $i_count [optional]
	 * @param mixed $i_block [optional]
	 */
	public function xread (array $arr_streams, $i_count = NULL, $i_block = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_group
	 * @param mixed $str_consumer
	 * @param array $arr_streams
	 * @param mixed $i_count [optional]
	 * @param mixed $i_block [optional]
	 */
	public function xreadgroup ($str_group = null, $str_consumer = null, array $arr_streams, $i_count = NULL, $i_block = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $str_start
	 * @param mixed $str_end
	 * @param mixed $i_count [optional]
	 */
	public function xrevrange ($str_key = null, $str_start = null, $str_end = null, $i_count = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $i_maxlen
	 * @param mixed $boo_approximate [optional]
	 */
	public function xtrim ($str_key = null, $i_maxlen = null, $boo_approximate = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $score
	 * @param mixed $value
	 * @param mixed $extra_args [optional]
	 */
	public function zadd ($key = null, $score = null, $value = null, ...$extra_args) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function zcard ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zcount ($key = null, $min = null, $max = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $member
	 */
	public function zincrby ($key = null, $value = null, $member = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param array $keys
	 * @param array|null $weights [optional]
	 * @param mixed $aggregate [optional]
	 */
	public function zinterstore ($key = null, array $keys, ?array $weights = NULL, $aggregate = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zlexcount ($key = null, $min = null, $max = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function zpopmax ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function zpopmin ($key = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param mixed $scores [optional]
	 */
	public function zrange ($key = null, $start = null, $end = null, $scores = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 * @param mixed $offset [optional]
	 * @param mixed $limit [optional]
	 */
	public function zrangebylex ($key = null, $min = null, $max = null, $offset = NULL, $limit = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param array $options [optional]
	 */
	public function zrangebyscore ($key = null, $start = null, $end = null, array $options = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function zrank ($key = null, $member = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function zrem ($key = null, $member = null, ...$other_members) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zremrangebylex ($key = null, $min = null, $max = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zremrangebyrank ($key = null, $min = null, $max = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zremrangebyscore ($key = null, $min = null, $max = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param mixed $scores [optional]
	 */
	public function zrevrange ($key = null, $start = null, $end = null, $scores = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 * @param mixed $offset [optional]
	 * @param mixed $limit [optional]
	 */
	public function zrevrangebylex ($key = null, $min = null, $max = null, $offset = NULL, $limit = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param array $options [optional]
	 */
	public function zrevrangebyscore ($key = null, $start = null, $end = null, array $options = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function zrevrank ($key = null, $member = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str_key
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function zscan ($str_key = null, &$i_iterator = null, $str_pattern = NULL, $i_count = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function zscore ($key = null, $member = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param array $keys
	 * @param array|null $weights [optional]
	 * @param mixed $aggregate [optional]
	 */
	public function zunionstore ($key = null, array $keys, ?array $weights = NULL, $aggregate = NULL) {}

}

class RedisSentinel  {

	/**
	 * {@inheritdoc}
	 * @param mixed $host
	 * @param mixed $port [optional]
	 * @param mixed $timeout [optional]
	 * @param mixed $persistent [optional]
	 * @param mixed $retry_interval [optional]
	 * @param mixed $read_timeout [optional]
	 */
	public function __construct ($host = null, $port = NULL, $timeout = NULL, $persistent = NULL, $retry_interval = NULL, $read_timeout = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function ckquorum ($value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function failover ($value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function flushconfig () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function getMasterAddrByName ($value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function master ($value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function masters () {}

	/**
	 * {@inheritdoc}
	 */
	public function ping () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function reset ($value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function sentinels ($value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function slaves ($value = null) {}

}

class RedisException extends Exception implements Throwable, Stringable {

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

class RedisClusterException extends Exception implements Throwable, Stringable {

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
// End of redis v.5.3.7
