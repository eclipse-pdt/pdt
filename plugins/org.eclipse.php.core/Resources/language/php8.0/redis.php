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


	public function __construct () {}

	public function __destruct () {}

	/**
	 * @param mixed $key
	 */
	public function _prefix ($key = null) {}

	/**
	 * @param mixed $value
	 */
	public function _serialize ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public function _unserialize ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public function _pack ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public function _unpack ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public function _compress ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public function _uncompress ($value = null) {}

	/**
	 * @param mixed $subcmd
	 * @param mixed $args [optional]
	 */
	public function acl ($subcmd = null, ...$args) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function append ($key = null, $value = null) {}

	/**
	 * @param mixed $auth
	 */
	public function auth ($auth = null) {}

	public function bgSave () {}

	public function bgrewriteaof () {}

	/**
	 * @param mixed $key
	 */
	public function bitcount ($key = null) {}

	/**
	 * @param mixed $operation
	 * @param mixed $ret_key
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function bitop ($operation = null, $ret_key = null, $key = null, ...$other_keys) {}

	/**
	 * @param mixed $key
	 * @param mixed $bit
	 * @param mixed $start [optional]
	 * @param mixed $end [optional]
	 */
	public function bitpos ($key = null, $bit = null, $start = null, $end = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function blPop ($key = null, $timeout_or_key = null, ...$extra_args) {}

	/**
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function brPop ($key = null, $timeout_or_key = null, ...$extra_args) {}

	/**
	 * @param mixed $src
	 * @param mixed $dst
	 * @param mixed $timeout
	 */
	public function brpoplpush ($src = null, $dst = null, $timeout = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function bzPopMax ($key = null, $timeout_or_key = null, ...$extra_args) {}

	/**
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function bzPopMin ($key = null, $timeout_or_key = null, ...$extra_args) {}

	public function clearLastError () {}

	/**
	 * @param mixed $cmd
	 * @param mixed $args [optional]
	 */
	public function client ($cmd = null, ...$args) {}

	public function close () {}

	/**
	 * @param mixed $args [optional]
	 */
	public function command (...$args) {}

	/**
	 * @param mixed $cmd
	 * @param mixed $key
	 * @param mixed $value [optional]
	 */
	public function config ($cmd = null, $key = null, $value = null) {}

	/**
	 * @param mixed $host
	 * @param mixed $port [optional]
	 * @param mixed $timeout [optional]
	 * @param mixed $retry_interval [optional]
	 */
	public function connect ($host = null, $port = null, $timeout = null, $retry_interval = null) {}

	public function dbSize () {}

	/**
	 * @param mixed $key
	 */
	public function debug ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function decr ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function decrBy ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function del ($key = null, ...$other_keys) {}

	public function discard () {}

	/**
	 * @param mixed $key
	 */
	public function dump ($key = null) {}

	/**
	 * @param mixed $msg
	 */
	public function echo ($msg = null) {}

	/**
	 * @param mixed $script
	 * @param mixed $args [optional]
	 * @param mixed $num_keys [optional]
	 */
	public function eval ($script = null, $args = null, $num_keys = null) {}

	/**
	 * @param mixed $script_sha
	 * @param mixed $args [optional]
	 * @param mixed $num_keys [optional]
	 */
	public function evalsha ($script_sha = null, $args = null, $num_keys = null) {}

	public function exec () {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function exists ($key = null, ...$other_keys) {}

	/**
	 * @param mixed $key
	 * @param mixed $timeout
	 */
	public function expire ($key = null, $timeout = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timestamp
	 */
	public function expireAt ($key = null, $timestamp = null) {}

	/**
	 * @param mixed $async [optional]
	 */
	public function flushAll ($async = null) {}

	/**
	 * @param mixed $async [optional]
	 */
	public function flushDB ($async = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $lng
	 * @param mixed $lat
	 * @param mixed $member
	 * @param mixed $other_triples [optional]
	 */
	public function geoadd ($key = null, $lng = null, $lat = null, $member = null, ...$other_triples) {}

	/**
	 * @param mixed $key
	 * @param mixed $src
	 * @param mixed $dst
	 * @param mixed $unit [optional]
	 */
	public function geodist ($key = null, $src = null, $dst = null, $unit = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function geohash ($key = null, $member = null, ...$other_members) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function geopos ($key = null, $member = null, ...$other_members) {}

	/**
	 * @param mixed $key
	 * @param mixed $lng
	 * @param mixed $lan
	 * @param mixed $radius
	 * @param mixed $unit
	 * @param array[] $opts [optional]
	 */
	public function georadius ($key = null, $lng = null, $lan = null, $radius = null, $unit = null, array $opts = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $lng
	 * @param mixed $lan
	 * @param mixed $radius
	 * @param mixed $unit
	 * @param array[] $opts [optional]
	 */
	public function georadius_ro ($key = null, $lng = null, $lan = null, $radius = null, $unit = null, array $opts = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $radius
	 * @param mixed $unit
	 * @param array[] $opts [optional]
	 */
	public function georadiusbymember ($key = null, $member = null, $radius = null, $unit = null, array $opts = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $radius
	 * @param mixed $unit
	 * @param array[] $opts [optional]
	 */
	public function georadiusbymember_ro ($key = null, $member = null, $radius = null, $unit = null, array $opts = null) {}

	/**
	 * @param mixed $key
	 */
	public function get ($key = null) {}

	public function getAuth () {}

	/**
	 * @param mixed $key
	 * @param mixed $offset
	 */
	public function getBit ($key = null, $offset = null) {}

	public function getDBNum () {}

	public function getHost () {}

	public function getLastError () {}

	public function getMode () {}

	/**
	 * @param mixed $option
	 */
	public function getOption ($option = null) {}

	public function getPersistentID () {}

	public function getPort () {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 */
	public function getRange ($key = null, $start = null, $end = null) {}

	public function getReadTimeout () {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function getSet ($key = null, $value = null) {}

	public function getTimeout () {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function hDel ($key = null, $member = null, ...$other_members) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function hExists ($key = null, $member = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function hGet ($key = null, $member = null) {}

	/**
	 * @param mixed $key
	 */
	public function hGetAll ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hIncrBy ($key = null, $member = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hIncrByFloat ($key = null, $member = null, $value = null) {}

	/**
	 * @param mixed $key
	 */
	public function hKeys ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function hLen ($key = null) {}

	/**
	 * @param mixed $key
	 * @param array[] $keys
	 */
	public function hMget ($key = null, array $keys) {}

	/**
	 * @param mixed $key
	 * @param array[] $pairs
	 */
	public function hMset ($key = null, array $pairs) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hSet ($key = null, $member = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hSetNx ($key = null, $member = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function hStrLen ($key = null, $member = null) {}

	/**
	 * @param mixed $key
	 */
	public function hVals ($key = null) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function hscan ($str_key = null, &$i_iterator = null, $str_pattern = null, $i_count = null) {}

	/**
	 * @param mixed $key
	 */
	public function incr ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function incrBy ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function incrByFloat ($key = null, $value = null) {}

	/**
	 * @param mixed $option [optional]
	 */
	public function info ($option = null) {}

	public function isConnected () {}

	/**
	 * @param mixed $pattern
	 */
	public function keys ($pattern = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $position
	 * @param mixed $pivot
	 * @param mixed $value
	 */
	public function lInsert ($key = null, $position = null, $pivot = null, $value = null) {}

	/**
	 * @param mixed $key
	 */
	public function lLen ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function lPop ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function lPush ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function lPushx ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $index
	 * @param mixed $value
	 */
	public function lSet ($key = null, $index = null, $value = null) {}

	public function lastSave () {}

	/**
	 * @param mixed $key
	 * @param mixed $index
	 */
	public function lindex ($key = null, $index = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 */
	public function lrange ($key = null, $start = null, $end = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $count
	 */
	public function lrem ($key = null, $value = null, $count = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $stop
	 */
	public function ltrim ($key = null, $start = null, $stop = null) {}

	/**
	 * @param array[] $keys
	 */
	public function mget (array $keys) {}

	/**
	 * @param mixed $host
	 * @param mixed $port
	 * @param mixed $key
	 * @param mixed $db
	 * @param mixed $timeout
	 * @param mixed $copy [optional]
	 * @param mixed $replace [optional]
	 */
	public function migrate ($host = null, $port = null, $key = null, $db = null, $timeout = null, $copy = null, $replace = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $dbindex
	 */
	public function move ($key = null, $dbindex = null) {}

	/**
	 * @param array[] $pairs
	 */
	public function mset (array $pairs) {}

	/**
	 * @param array[] $pairs
	 */
	public function msetnx (array $pairs) {}

	/**
	 * @param mixed $mode [optional]
	 */
	public function multi ($mode = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $key
	 */
	public function object ($field = null, $key = null) {}

	/**
	 * @param mixed $host
	 * @param mixed $port [optional]
	 * @param mixed $timeout [optional]
	 */
	public function pconnect ($host = null, $port = null, $timeout = null) {}

	/**
	 * @param mixed $key
	 */
	public function persist ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timestamp
	 */
	public function pexpire ($key = null, $timestamp = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timestamp
	 */
	public function pexpireAt ($key = null, $timestamp = null) {}

	/**
	 * @param mixed $key
	 * @param array[] $elements
	 */
	public function pfadd ($key = null, array $elements) {}

	/**
	 * @param mixed $key
	 */
	public function pfcount ($key = null) {}

	/**
	 * @param mixed $dstkey
	 * @param array[] $keys
	 */
	public function pfmerge ($dstkey = null, array $keys) {}

	public function ping () {}

	public function pipeline () {}

	/**
	 * @param mixed $key
	 * @param mixed $expire
	 * @param mixed $value
	 */
	public function psetex ($key = null, $expire = null, $value = null) {}

	/**
	 * @param array[] $patterns
	 * @param mixed $callback
	 */
	public function psubscribe (array $patterns, $callback = null) {}

	/**
	 * @param mixed $key
	 */
	public function pttl ($key = null) {}

	/**
	 * @param mixed $channel
	 * @param mixed $message
	 */
	public function publish ($channel = null, $message = null) {}

	/**
	 * @param mixed $cmd
	 * @param mixed $args [optional]
	 */
	public function pubsub ($cmd = null, ...$args) {}

	/**
	 * @param mixed $pattern
	 * @param mixed $other_patterns [optional]
	 */
	public function punsubscribe ($pattern = null, ...$other_patterns) {}

	/**
	 * @param mixed $key
	 */
	public function rPop ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function rPush ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function rPushx ($key = null, $value = null) {}

	public function randomKey () {}

	/**
	 * @param mixed $cmd
	 * @param mixed $args [optional]
	 */
	public function rawcommand ($cmd = null, ...$args) {}

	/**
	 * @param mixed $key
	 * @param mixed $newkey
	 */
	public function rename ($key = null, $newkey = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $newkey
	 */
	public function renameNx ($key = null, $newkey = null) {}

	/**
	 * @param mixed $ttl
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function restore ($ttl = null, $key = null, $value = null) {}

	public function role () {}

	/**
	 * @param mixed $src
	 * @param mixed $dst
	 */
	public function rpoplpush ($src = null, $dst = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function sAdd ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param array[] $options
	 */
	public function sAddArray ($key = null, array $options) {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sDiff ($key = null, ...$other_keys) {}

	/**
	 * @param mixed $dst
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sDiffStore ($dst = null, $key = null, ...$other_keys) {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sInter ($key = null, ...$other_keys) {}

	/**
	 * @param mixed $dst
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sInterStore ($dst = null, $key = null, ...$other_keys) {}

	/**
	 * @param mixed $key
	 */
	public function sMembers ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function sMisMember ($key = null, $member = null, ...$other_members) {}

	/**
	 * @param mixed $src
	 * @param mixed $dst
	 * @param mixed $value
	 */
	public function sMove ($src = null, $dst = null, $value = null) {}

	/**
	 * @param mixed $key
	 */
	public function sPop ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $count [optional]
	 */
	public function sRandMember ($key = null, $count = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sUnion ($key = null, ...$other_keys) {}

	/**
	 * @param mixed $dst
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sUnionStore ($dst = null, $key = null, ...$other_keys) {}

	public function save () {}

	/**
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function scan (&$i_iterator = null, $str_pattern = null, $i_count = null) {}

	/**
	 * @param mixed $key
	 */
	public function scard ($key = null) {}

	/**
	 * @param mixed $cmd
	 * @param mixed $args [optional]
	 */
	public function script ($cmd = null, ...$args) {}

	/**
	 * @param mixed $dbindex
	 */
	public function select ($dbindex = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $opts [optional]
	 */
	public function set ($key = null, $value = null, $opts = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function setBit ($key = null, $offset = null, $value = null) {}

	/**
	 * @param mixed $option
	 * @param mixed $value
	 */
	public function setOption ($option = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function setRange ($key = null, $offset = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $expire
	 * @param mixed $value
	 */
	public function setex ($key = null, $expire = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function setnx ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function sismember ($key = null, $value = null) {}

	/**
	 * @param mixed $host [optional]
	 * @param mixed $port [optional]
	 */
	public function slaveof ($host = null, $port = null) {}

	/**
	 * @param mixed $arg
	 * @param mixed $option [optional]
	 */
	public function slowlog ($arg = null, $option = null) {}

	/**
	 * @param mixed $key
	 * @param array[] $options [optional]
	 */
	public function sort ($key = null, array $options = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $pattern [optional]
	 * @param mixed $get [optional]
	 * @param mixed $start [optional]
	 * @param mixed $end [optional]
	 * @param mixed $getList [optional]
	 * @deprecated 
	 */
	public function sortAsc ($key = null, $pattern = null, $get = null, $start = null, $end = null, $getList = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $pattern [optional]
	 * @param mixed $get [optional]
	 * @param mixed $start [optional]
	 * @param mixed $end [optional]
	 * @param mixed $getList [optional]
	 * @deprecated 
	 */
	public function sortAscAlpha ($key = null, $pattern = null, $get = null, $start = null, $end = null, $getList = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $pattern [optional]
	 * @param mixed $get [optional]
	 * @param mixed $start [optional]
	 * @param mixed $end [optional]
	 * @param mixed $getList [optional]
	 * @deprecated 
	 */
	public function sortDesc ($key = null, $pattern = null, $get = null, $start = null, $end = null, $getList = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $pattern [optional]
	 * @param mixed $get [optional]
	 * @param mixed $start [optional]
	 * @param mixed $end [optional]
	 * @param mixed $getList [optional]
	 * @deprecated 
	 */
	public function sortDescAlpha ($key = null, $pattern = null, $get = null, $start = null, $end = null, $getList = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function srem ($key = null, $member = null, ...$other_members) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function sscan ($str_key = null, &$i_iterator = null, $str_pattern = null, $i_count = null) {}

	/**
	 * @param mixed $key
	 */
	public function strlen ($key = null) {}

	/**
	 * @param array[] $channels
	 * @param mixed $callback
	 */
	public function subscribe (array $channels, $callback = null) {}

	/**
	 * @param mixed $srcdb
	 * @param mixed $dstdb
	 */
	public function swapdb ($srcdb = null, $dstdb = null) {}

	public function time () {}

	/**
	 * @param mixed $key
	 */
	public function ttl ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function type ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function unlink ($key = null, ...$other_keys) {}

	/**
	 * @param mixed $channel
	 * @param mixed $other_channels [optional]
	 */
	public function unsubscribe ($channel = null, ...$other_channels) {}

	public function unwatch () {}

	/**
	 * @param mixed $numslaves
	 * @param mixed $timeout
	 */
	public function wait ($numslaves = null, $timeout = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function watch ($key = null, ...$other_keys) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $str_group
	 * @param array[] $arr_ids
	 */
	public function xack ($str_key = null, $str_group = null, array $arr_ids) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $str_id
	 * @param array[] $arr_fields
	 * @param mixed $i_maxlen [optional]
	 * @param mixed $boo_approximate [optional]
	 */
	public function xadd ($str_key = null, $str_id = null, array $arr_fields, $i_maxlen = null, $boo_approximate = null) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $str_group
	 * @param mixed $str_consumer
	 * @param mixed $i_min_idle
	 * @param array[] $arr_ids
	 * @param array[] $arr_opts [optional]
	 */
	public function xclaim ($str_key = null, $str_group = null, $str_consumer = null, $i_min_idle = null, array $arr_ids, array $arr_opts = null) {}

	/**
	 * @param mixed $str_key
	 * @param array[] $arr_ids
	 */
	public function xdel ($str_key = null, array $arr_ids) {}

	/**
	 * @param mixed $str_operation
	 * @param mixed $str_key [optional]
	 * @param mixed $str_arg1 [optional]
	 * @param mixed $str_arg2 [optional]
	 * @param mixed $str_arg3 [optional]
	 */
	public function xgroup ($str_operation = null, $str_key = null, $str_arg1 = null, $str_arg2 = null, $str_arg3 = null) {}

	/**
	 * @param mixed $str_cmd
	 * @param mixed $str_key [optional]
	 * @param mixed $str_group [optional]
	 */
	public function xinfo ($str_cmd = null, $str_key = null, $str_group = null) {}

	/**
	 * @param mixed $key
	 */
	public function xlen ($key = null) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $str_group
	 * @param mixed $str_start [optional]
	 * @param mixed $str_end [optional]
	 * @param mixed $i_count [optional]
	 * @param mixed $str_consumer [optional]
	 */
	public function xpending ($str_key = null, $str_group = null, $str_start = null, $str_end = null, $i_count = null, $str_consumer = null) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $str_start
	 * @param mixed $str_end
	 * @param mixed $i_count [optional]
	 */
	public function xrange ($str_key = null, $str_start = null, $str_end = null, $i_count = null) {}

	/**
	 * @param array[] $arr_streams
	 * @param mixed $i_count [optional]
	 * @param mixed $i_block [optional]
	 */
	public function xread (array $arr_streams, $i_count = null, $i_block = null) {}

	/**
	 * @param mixed $str_group
	 * @param mixed $str_consumer
	 * @param array[] $arr_streams
	 * @param mixed $i_count [optional]
	 * @param mixed $i_block [optional]
	 */
	public function xreadgroup ($str_group = null, $str_consumer = null, array $arr_streams, $i_count = null, $i_block = null) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $str_start
	 * @param mixed $str_end
	 * @param mixed $i_count [optional]
	 */
	public function xrevrange ($str_key = null, $str_start = null, $str_end = null, $i_count = null) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $i_maxlen
	 * @param mixed $boo_approximate [optional]
	 */
	public function xtrim ($str_key = null, $i_maxlen = null, $boo_approximate = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $score
	 * @param mixed $value
	 * @param mixed $extra_args [optional]
	 */
	public function zAdd ($key = null, $score = null, $value = null, ...$extra_args) {}

	/**
	 * @param mixed $key
	 */
	public function zCard ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zCount ($key = null, $min = null, $max = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $member
	 */
	public function zIncrBy ($key = null, $value = null, $member = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zLexCount ($key = null, $min = null, $max = null) {}

	/**
	 * @param mixed $key
	 */
	public function zPopMax ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function zPopMin ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param mixed $scores [optional]
	 */
	public function zRange ($key = null, $start = null, $end = null, $scores = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 * @param mixed $offset [optional]
	 * @param mixed $limit [optional]
	 */
	public function zRangeByLex ($key = null, $min = null, $max = null, $offset = null, $limit = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param array[] $options [optional]
	 */
	public function zRangeByScore ($key = null, $start = null, $end = null, array $options = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function zRank ($key = null, $member = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function zRem ($key = null, $member = null, ...$other_members) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zRemRangeByLex ($key = null, $min = null, $max = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 */
	public function zRemRangeByRank ($key = null, $start = null, $end = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zRemRangeByScore ($key = null, $min = null, $max = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param mixed $scores [optional]
	 */
	public function zRevRange ($key = null, $start = null, $end = null, $scores = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 * @param mixed $offset [optional]
	 * @param mixed $limit [optional]
	 */
	public function zRevRangeByLex ($key = null, $min = null, $max = null, $offset = null, $limit = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param array[] $options [optional]
	 */
	public function zRevRangeByScore ($key = null, $start = null, $end = null, array $options = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function zRevRank ($key = null, $member = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function zScore ($key = null, $member = null) {}

	/**
	 * @param mixed $key
	 * @param array[] $keys
	 * @param array|null[] $weights [optional]
	 * @param mixed $aggregate [optional]
	 */
	public function zinterstore ($key = null, array $keys, array $weights = null, $aggregate = null) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function zscan ($str_key = null, &$i_iterator = null, $str_pattern = null, $i_count = null) {}

	/**
	 * @param mixed $key
	 * @param array[] $keys
	 * @param array|null[] $weights [optional]
	 * @param mixed $aggregate [optional]
	 */
	public function zunionstore ($key = null, array $keys, array $weights = null, $aggregate = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 * @deprecated 
	 */
	public function delete ($key = null, ...$other_keys) {}

	/**
	 * @param mixed $script
	 * @param mixed $args [optional]
	 * @param mixed $num_keys [optional]
	 * @deprecated 
	 */
	public function evaluate ($script = null, $args = null, $num_keys = null) {}

	/**
	 * @param mixed $script_sha
	 * @param mixed $args [optional]
	 * @param mixed $num_keys [optional]
	 * @deprecated 
	 */
	public function evaluateSha ($script_sha = null, $args = null, $num_keys = null) {}

	/**
	 * @param mixed $pattern
	 * @deprecated 
	 */
	public function getKeys ($pattern = null) {}

	/**
	 * @param array[] $keys
	 * @deprecated 
	 */
	public function getMultiple (array $keys) {}

	/**
	 * @param mixed $key
	 * @param mixed $index
	 * @deprecated 
	 */
	public function lGet ($key = null, $index = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @deprecated 
	 */
	public function lGetRange ($key = null, $start = null, $end = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $count
	 * @deprecated 
	 */
	public function lRemove ($key = null, $value = null, $count = null) {}

	/**
	 * @param mixed $key
	 * @deprecated 
	 */
	public function lSize ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $stop
	 * @deprecated 
	 */
	public function listTrim ($key = null, $start = null, $stop = null) {}

	/**
	 * @param mixed $host
	 * @param mixed $port [optional]
	 * @param mixed $timeout [optional]
	 * @param mixed $retry_interval [optional]
	 * @deprecated 
	 */
	public function open ($host = null, $port = null, $timeout = null, $retry_interval = null) {}

	/**
	 * @param mixed $host
	 * @param mixed $port [optional]
	 * @param mixed $timeout [optional]
	 * @deprecated 
	 */
	public function popen ($host = null, $port = null, $timeout = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $newkey
	 * @deprecated 
	 */
	public function renameKey ($key = null, $newkey = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 * @deprecated 
	 */
	public function sContains ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @deprecated 
	 */
	public function sGetMembers ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 * @deprecated 
	 */
	public function sRemove ($key = null, $member = null, ...$other_members) {}

	/**
	 * @param mixed $key
	 * @deprecated 
	 */
	public function sSize ($key = null) {}

	/**
	 * @param mixed $msg
	 * @deprecated 
	 */
	public function sendEcho ($msg = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timeout
	 * @deprecated 
	 */
	public function setTimeout ($key = null, $timeout = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @deprecated 
	 */
	public function substr ($key = null, $start = null, $end = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 * @deprecated 
	 */
	public function zDelete ($key = null, $member = null, ...$other_members) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 * @deprecated 
	 */
	public function zDeleteRangeByRank ($key = null, $min = null, $max = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 * @deprecated 
	 */
	public function zDeleteRangeByScore ($key = null, $min = null, $max = null) {}

	/**
	 * @param mixed $key
	 * @param array[] $keys
	 * @param array|null[] $weights [optional]
	 * @param mixed $aggregate [optional]
	 * @deprecated 
	 */
	public function zInter ($key = null, array $keys, array $weights = null, $aggregate = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 * @deprecated 
	 */
	public function zRemove ($key = null, $member = null, ...$other_members) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 * @deprecated 
	 */
	public function zRemoveRangeByScore ($key = null, $min = null, $max = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param mixed $scores [optional]
	 * @deprecated 
	 */
	public function zReverseRange ($key = null, $start = null, $end = null, $scores = null) {}

	/**
	 * @param mixed $key
	 * @deprecated 
	 */
	public function zSize ($key = null) {}

	/**
	 * @param mixed $key
	 * @param array[] $keys
	 * @param array|null[] $weights [optional]
	 * @param mixed $aggregate [optional]
	 * @deprecated 
	 */
	public function zUnion ($key = null, array $keys, array $weights = null, $aggregate = null) {}

}

class RedisArray  {

	/**
	 * @param mixed $function_name
	 * @param mixed $arguments
	 */
	public function __call ($function_name = null, $arguments = null) {}

	/**
	 * @param mixed $name_or_hosts
	 * @param array[] $options [optional]
	 */
	public function __construct ($name_or_hosts = null, array $options = null) {}

	public function _continuum () {}

	public function _distributor () {}

	public function _function () {}

	public function _hosts () {}

	/**
	 * @param mixed $host
	 */
	public function _instance ($host = null) {}

	/**
	 * @param mixed $callable [optional]
	 */
	public function _rehash ($callable = null) {}

	/**
	 * @param mixed $key
	 */
	public function _target ($key = null) {}

	public function bgsave () {}

	/**
	 * @param mixed $keys
	 */
	public function del ($keys = null) {}

	public function discard () {}

	public function exec () {}

	/**
	 * @param mixed $async [optional]
	 */
	public function flushall ($async = null) {}

	/**
	 * @param mixed $async [optional]
	 */
	public function flushdb ($async = null) {}

	/**
	 * @param mixed $opt
	 */
	public function getOption ($opt = null) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function hscan ($str_key = null, &$i_iterator = null, $str_pattern = null, $i_count = null) {}

	public function info () {}

	/**
	 * @param mixed $pattern
	 */
	public function keys ($pattern = null) {}

	/**
	 * @param mixed $keys
	 */
	public function mget ($keys = null) {}

	/**
	 * @param mixed $pairs
	 */
	public function mset ($pairs = null) {}

	/**
	 * @param mixed $host
	 * @param mixed $mode [optional]
	 */
	public function multi ($host = null, $mode = null) {}

	public function ping () {}

	public function save () {}

	/**
	 * @param mixed $iterator
	 * @param mixed $node
	 * @param mixed $pattern [optional]
	 * @param mixed $count [optional]
	 */
	public function scan (&$iterator = null, $node = null, $pattern = null, $count = null) {}

	/**
	 * @param mixed $index
	 */
	public function select ($index = null) {}

	/**
	 * @param mixed $opt
	 * @param mixed $value
	 */
	public function setOption ($opt = null, $value = null) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function sscan ($str_key = null, &$i_iterator = null, $str_pattern = null, $i_count = null) {}

	public function unlink () {}

	public function unwatch () {}

	/**
	 * @param mixed $str_key
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function zscan ($str_key = null, &$i_iterator = null, $str_pattern = null, $i_count = null) {}

	/**
	 * @param mixed $keys
	 */
	public function delete ($keys = null) {}

	/**
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
	 * @param mixed $name
	 * @param array[] $seeds [optional]
	 * @param mixed $timeout [optional]
	 * @param mixed $read_timeout [optional]
	 * @param mixed $persistent [optional]
	 * @param mixed $auth [optional]
	 */
	public function __construct ($name = null, array $seeds = null, $timeout = null, $read_timeout = null, $persistent = null, $auth = null) {}

	public function _masters () {}

	/**
	 * @param mixed $key
	 */
	public function _prefix ($key = null) {}

	public function _redir () {}

	/**
	 * @param mixed $value
	 */
	public function _serialize ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public function _unserialize ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public function _compress ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public function _uncompress ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public function _pack ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public function _unpack ($value = null) {}

	/**
	 * @param mixed $key_or_address
	 * @param mixed $subcmd
	 * @param mixed $args [optional]
	 */
	public function acl ($key_or_address = null, $subcmd = null, ...$args) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function append ($key = null, $value = null) {}

	/**
	 * @param mixed $key_or_address
	 */
	public function bgrewriteaof ($key_or_address = null) {}

	/**
	 * @param mixed $key_or_address
	 */
	public function bgsave ($key_or_address = null) {}

	/**
	 * @param mixed $key
	 */
	public function bitcount ($key = null) {}

	/**
	 * @param mixed $operation
	 * @param mixed $ret_key
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function bitop ($operation = null, $ret_key = null, $key = null, ...$other_keys) {}

	/**
	 * @param mixed $key
	 * @param mixed $bit
	 * @param mixed $start [optional]
	 * @param mixed $end [optional]
	 */
	public function bitpos ($key = null, $bit = null, $start = null, $end = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function blpop ($key = null, $timeout_or_key = null, ...$extra_args) {}

	/**
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function brpop ($key = null, $timeout_or_key = null, ...$extra_args) {}

	/**
	 * @param mixed $src
	 * @param mixed $dst
	 * @param mixed $timeout
	 */
	public function brpoplpush ($src = null, $dst = null, $timeout = null) {}

	public function clearlasterror () {}

	/**
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function bzpopmax ($key = null, $timeout_or_key = null, ...$extra_args) {}

	/**
	 * @param mixed $key
	 * @param mixed $timeout_or_key
	 * @param mixed $extra_args [optional]
	 */
	public function bzpopmin ($key = null, $timeout_or_key = null, ...$extra_args) {}

	/**
	 * @param mixed $key_or_address
	 * @param mixed $arg [optional]
	 * @param mixed $other_args [optional]
	 */
	public function client ($key_or_address = null, $arg = null, ...$other_args) {}

	public function close () {}

	/**
	 * @param mixed $key_or_address
	 * @param mixed $arg [optional]
	 * @param mixed $other_args [optional]
	 */
	public function cluster ($key_or_address = null, $arg = null, ...$other_args) {}

	/**
	 * @param mixed $args [optional]
	 */
	public function command (...$args) {}

	/**
	 * @param mixed $key_or_address
	 * @param mixed $arg [optional]
	 * @param mixed $other_args [optional]
	 */
	public function config ($key_or_address = null, $arg = null, ...$other_args) {}

	/**
	 * @param mixed $key_or_address
	 */
	public function dbsize ($key_or_address = null) {}

	/**
	 * @param mixed $key
	 */
	public function decr ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function decrby ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function del ($key = null, ...$other_keys) {}

	public function discard () {}

	/**
	 * @param mixed $key
	 */
	public function dump ($key = null) {}

	/**
	 * @param mixed $msg
	 */
	public function echo ($msg = null) {}

	/**
	 * @param mixed $script
	 * @param mixed $args [optional]
	 * @param mixed $num_keys [optional]
	 */
	public function eval ($script = null, $args = null, $num_keys = null) {}

	/**
	 * @param mixed $script_sha
	 * @param mixed $args [optional]
	 * @param mixed $num_keys [optional]
	 */
	public function evalsha ($script_sha = null, $args = null, $num_keys = null) {}

	public function exec () {}

	/**
	 * @param mixed $key
	 */
	public function exists ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timeout
	 */
	public function expire ($key = null, $timeout = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timestamp
	 */
	public function expireat ($key = null, $timestamp = null) {}

	/**
	 * @param mixed $key_or_address
	 * @param mixed $async [optional]
	 */
	public function flushall ($key_or_address = null, $async = null) {}

	/**
	 * @param mixed $key_or_address
	 * @param mixed $async [optional]
	 */
	public function flushdb ($key_or_address = null, $async = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $lng
	 * @param mixed $lat
	 * @param mixed $member
	 * @param mixed $other_triples [optional]
	 */
	public function geoadd ($key = null, $lng = null, $lat = null, $member = null, ...$other_triples) {}

	/**
	 * @param mixed $key
	 * @param mixed $src
	 * @param mixed $dst
	 * @param mixed $unit [optional]
	 */
	public function geodist ($key = null, $src = null, $dst = null, $unit = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function geohash ($key = null, $member = null, ...$other_members) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function geopos ($key = null, $member = null, ...$other_members) {}

	/**
	 * @param mixed $key
	 * @param mixed $lng
	 * @param mixed $lan
	 * @param mixed $radius
	 * @param mixed $unit
	 * @param array[] $opts [optional]
	 */
	public function georadius ($key = null, $lng = null, $lan = null, $radius = null, $unit = null, array $opts = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $lng
	 * @param mixed $lan
	 * @param mixed $radius
	 * @param mixed $unit
	 * @param array[] $opts [optional]
	 */
	public function georadius_ro ($key = null, $lng = null, $lan = null, $radius = null, $unit = null, array $opts = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $radius
	 * @param mixed $unit
	 * @param array[] $opts [optional]
	 */
	public function georadiusbymember ($key = null, $member = null, $radius = null, $unit = null, array $opts = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $radius
	 * @param mixed $unit
	 * @param array[] $opts [optional]
	 */
	public function georadiusbymember_ro ($key = null, $member = null, $radius = null, $unit = null, array $opts = null) {}

	/**
	 * @param mixed $key
	 */
	public function get ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $offset
	 */
	public function getbit ($key = null, $offset = null) {}

	public function getlasterror () {}

	public function getmode () {}

	/**
	 * @param mixed $option
	 */
	public function getoption ($option = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 */
	public function getrange ($key = null, $start = null, $end = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function getset ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function hdel ($key = null, $member = null, ...$other_members) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function hexists ($key = null, $member = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function hget ($key = null, $member = null) {}

	/**
	 * @param mixed $key
	 */
	public function hgetall ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hincrby ($key = null, $member = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hincrbyfloat ($key = null, $member = null, $value = null) {}

	/**
	 * @param mixed $key
	 */
	public function hkeys ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function hlen ($key = null) {}

	/**
	 * @param mixed $key
	 * @param array[] $keys
	 */
	public function hmget ($key = null, array $keys) {}

	/**
	 * @param mixed $key
	 * @param array[] $pairs
	 */
	public function hmset ($key = null, array $pairs) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function hscan ($str_key = null, &$i_iterator = null, $str_pattern = null, $i_count = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hset ($key = null, $member = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $value
	 */
	public function hsetnx ($key = null, $member = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function hstrlen ($key = null, $member = null) {}

	/**
	 * @param mixed $key
	 */
	public function hvals ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function incr ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function incrby ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function incrbyfloat ($key = null, $value = null) {}

	/**
	 * @param mixed $key_or_address
	 * @param mixed $option [optional]
	 */
	public function info ($key_or_address = null, $option = null) {}

	/**
	 * @param mixed $pattern
	 */
	public function keys ($pattern = null) {}

	/**
	 * @param mixed $key_or_address
	 */
	public function lastsave ($key_or_address = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $index
	 */
	public function lget ($key = null, $index = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $index
	 */
	public function lindex ($key = null, $index = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $position
	 * @param mixed $pivot
	 * @param mixed $value
	 */
	public function linsert ($key = null, $position = null, $pivot = null, $value = null) {}

	/**
	 * @param mixed $key
	 */
	public function llen ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function lpop ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function lpush ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function lpushx ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 */
	public function lrange ($key = null, $start = null, $end = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function lrem ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $index
	 * @param mixed $value
	 */
	public function lset ($key = null, $index = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $stop
	 */
	public function ltrim ($key = null, $start = null, $stop = null) {}

	/**
	 * @param array[] $keys
	 */
	public function mget (array $keys) {}

	/**
	 * @param array[] $pairs
	 */
	public function mset (array $pairs) {}

	/**
	 * @param array[] $pairs
	 */
	public function msetnx (array $pairs) {}

	public function multi () {}

	/**
	 * @param mixed $field
	 * @param mixed $key
	 */
	public function object ($field = null, $key = null) {}

	/**
	 * @param mixed $key
	 */
	public function persist ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timestamp
	 */
	public function pexpire ($key = null, $timestamp = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $timestamp
	 */
	public function pexpireat ($key = null, $timestamp = null) {}

	/**
	 * @param mixed $key
	 * @param array[] $elements
	 */
	public function pfadd ($key = null, array $elements) {}

	/**
	 * @param mixed $key
	 */
	public function pfcount ($key = null) {}

	/**
	 * @param mixed $dstkey
	 * @param array[] $keys
	 */
	public function pfmerge ($dstkey = null, array $keys) {}

	/**
	 * @param mixed $key_or_address
	 */
	public function ping ($key_or_address = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $expire
	 * @param mixed $value
	 */
	public function psetex ($key = null, $expire = null, $value = null) {}

	/**
	 * @param array[] $patterns
	 * @param mixed $callback
	 */
	public function psubscribe (array $patterns, $callback = null) {}

	/**
	 * @param mixed $key
	 */
	public function pttl ($key = null) {}

	/**
	 * @param mixed $channel
	 * @param mixed $message
	 */
	public function publish ($channel = null, $message = null) {}

	/**
	 * @param mixed $key_or_address
	 * @param mixed $arg [optional]
	 * @param mixed $other_args [optional]
	 */
	public function pubsub ($key_or_address = null, $arg = null, ...$other_args) {}

	/**
	 * @param mixed $pattern
	 * @param mixed $other_patterns [optional]
	 */
	public function punsubscribe ($pattern = null, ...$other_patterns) {}

	/**
	 * @param mixed $key_or_address
	 */
	public function randomkey ($key_or_address = null) {}

	/**
	 * @param mixed $cmd
	 * @param mixed $args [optional]
	 */
	public function rawcommand ($cmd = null, ...$args) {}

	/**
	 * @param mixed $key
	 * @param mixed $newkey
	 */
	public function rename ($key = null, $newkey = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $newkey
	 */
	public function renamenx ($key = null, $newkey = null) {}

	/**
	 * @param mixed $ttl
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function restore ($ttl = null, $key = null, $value = null) {}

	public function role () {}

	/**
	 * @param mixed $key
	 */
	public function rpop ($key = null) {}

	/**
	 * @param mixed $src
	 * @param mixed $dst
	 */
	public function rpoplpush ($src = null, $dst = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function rpush ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function rpushx ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function sadd ($key = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param array[] $options
	 */
	public function saddarray ($key = null, array $options) {}

	/**
	 * @param mixed $key_or_address
	 */
	public function save ($key_or_address = null) {}

	/**
	 * @param mixed $i_iterator
	 * @param mixed $str_node
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function scan (&$i_iterator = null, $str_node = null, $str_pattern = null, $i_count = null) {}

	/**
	 * @param mixed $key
	 */
	public function scard ($key = null) {}

	/**
	 * @param mixed $key_or_address
	 * @param mixed $arg [optional]
	 * @param mixed $other_args [optional]
	 */
	public function script ($key_or_address = null, $arg = null, ...$other_args) {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sdiff ($key = null, ...$other_keys) {}

	/**
	 * @param mixed $dst
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sdiffstore ($dst = null, $key = null, ...$other_keys) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $opts [optional]
	 */
	public function set ($key = null, $value = null, $opts = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function setbit ($key = null, $offset = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $expire
	 * @param mixed $value
	 */
	public function setex ($key = null, $expire = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function setnx ($key = null, $value = null) {}

	/**
	 * @param mixed $option
	 * @param mixed $value
	 */
	public function setoption ($option = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function setrange ($key = null, $offset = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sinter ($key = null, ...$other_keys) {}

	/**
	 * @param mixed $dst
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sinterstore ($dst = null, $key = null, ...$other_keys) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function sismember ($key = null, $value = null) {}

	/**
	 * @param mixed $key_or_address
	 * @param mixed $arg [optional]
	 * @param mixed $other_args [optional]
	 */
	public function slowlog ($key_or_address = null, $arg = null, ...$other_args) {}

	/**
	 * @param mixed $key
	 */
	public function smembers ($key = null) {}

	/**
	 * @param mixed $src
	 * @param mixed $dst
	 * @param mixed $value
	 */
	public function smove ($src = null, $dst = null, $value = null) {}

	/**
	 * @param mixed $key
	 * @param array[] $options [optional]
	 */
	public function sort ($key = null, array $options = null) {}

	/**
	 * @param mixed $key
	 */
	public function spop ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $count [optional]
	 */
	public function srandmember ($key = null, $count = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function srem ($key = null, $value = null) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function sscan ($str_key = null, &$i_iterator = null, $str_pattern = null, $i_count = null) {}

	/**
	 * @param mixed $key
	 */
	public function strlen ($key = null) {}

	/**
	 * @param array[] $channels
	 * @param mixed $callback
	 */
	public function subscribe (array $channels, $callback = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sunion ($key = null, ...$other_keys) {}

	/**
	 * @param mixed $dst
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function sunionstore ($dst = null, $key = null, ...$other_keys) {}

	public function time () {}

	/**
	 * @param mixed $key
	 */
	public function ttl ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function type ($key = null) {}

	/**
	 * @param mixed $channel
	 * @param mixed $other_channels [optional]
	 */
	public function unsubscribe ($channel = null, ...$other_channels) {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function unlink ($key = null, ...$other_keys) {}

	public function unwatch () {}

	/**
	 * @param mixed $key
	 * @param mixed $other_keys [optional]
	 */
	public function watch ($key = null, ...$other_keys) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $str_group
	 * @param array[] $arr_ids
	 */
	public function xack ($str_key = null, $str_group = null, array $arr_ids) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $str_id
	 * @param array[] $arr_fields
	 * @param mixed $i_maxlen [optional]
	 * @param mixed $boo_approximate [optional]
	 */
	public function xadd ($str_key = null, $str_id = null, array $arr_fields, $i_maxlen = null, $boo_approximate = null) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $str_group
	 * @param mixed $str_consumer
	 * @param mixed $i_min_idle
	 * @param array[] $arr_ids
	 * @param array[] $arr_opts [optional]
	 */
	public function xclaim ($str_key = null, $str_group = null, $str_consumer = null, $i_min_idle = null, array $arr_ids, array $arr_opts = null) {}

	/**
	 * @param mixed $str_key
	 * @param array[] $arr_ids
	 */
	public function xdel ($str_key = null, array $arr_ids) {}

	/**
	 * @param mixed $str_operation
	 * @param mixed $str_key [optional]
	 * @param mixed $str_arg1 [optional]
	 * @param mixed $str_arg2 [optional]
	 * @param mixed $str_arg3 [optional]
	 */
	public function xgroup ($str_operation = null, $str_key = null, $str_arg1 = null, $str_arg2 = null, $str_arg3 = null) {}

	/**
	 * @param mixed $str_cmd
	 * @param mixed $str_key [optional]
	 * @param mixed $str_group [optional]
	 */
	public function xinfo ($str_cmd = null, $str_key = null, $str_group = null) {}

	/**
	 * @param mixed $key
	 */
	public function xlen ($key = null) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $str_group
	 * @param mixed $str_start [optional]
	 * @param mixed $str_end [optional]
	 * @param mixed $i_count [optional]
	 * @param mixed $str_consumer [optional]
	 */
	public function xpending ($str_key = null, $str_group = null, $str_start = null, $str_end = null, $i_count = null, $str_consumer = null) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $str_start
	 * @param mixed $str_end
	 * @param mixed $i_count [optional]
	 */
	public function xrange ($str_key = null, $str_start = null, $str_end = null, $i_count = null) {}

	/**
	 * @param array[] $arr_streams
	 * @param mixed $i_count [optional]
	 * @param mixed $i_block [optional]
	 */
	public function xread (array $arr_streams, $i_count = null, $i_block = null) {}

	/**
	 * @param mixed $str_group
	 * @param mixed $str_consumer
	 * @param array[] $arr_streams
	 * @param mixed $i_count [optional]
	 * @param mixed $i_block [optional]
	 */
	public function xreadgroup ($str_group = null, $str_consumer = null, array $arr_streams, $i_count = null, $i_block = null) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $str_start
	 * @param mixed $str_end
	 * @param mixed $i_count [optional]
	 */
	public function xrevrange ($str_key = null, $str_start = null, $str_end = null, $i_count = null) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $i_maxlen
	 * @param mixed $boo_approximate [optional]
	 */
	public function xtrim ($str_key = null, $i_maxlen = null, $boo_approximate = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $score
	 * @param mixed $value
	 * @param mixed $extra_args [optional]
	 */
	public function zadd ($key = null, $score = null, $value = null, ...$extra_args) {}

	/**
	 * @param mixed $key
	 */
	public function zcard ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zcount ($key = null, $min = null, $max = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $member
	 */
	public function zincrby ($key = null, $value = null, $member = null) {}

	/**
	 * @param mixed $key
	 * @param array[] $keys
	 * @param array|null[] $weights [optional]
	 * @param mixed $aggregate [optional]
	 */
	public function zinterstore ($key = null, array $keys, array $weights = null, $aggregate = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zlexcount ($key = null, $min = null, $max = null) {}

	/**
	 * @param mixed $key
	 */
	public function zpopmax ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public function zpopmin ($key = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param mixed $scores [optional]
	 */
	public function zrange ($key = null, $start = null, $end = null, $scores = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 * @param mixed $offset [optional]
	 * @param mixed $limit [optional]
	 */
	public function zrangebylex ($key = null, $min = null, $max = null, $offset = null, $limit = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param array[] $options [optional]
	 */
	public function zrangebyscore ($key = null, $start = null, $end = null, array $options = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function zrank ($key = null, $member = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 * @param mixed $other_members [optional]
	 */
	public function zrem ($key = null, $member = null, ...$other_members) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zremrangebylex ($key = null, $min = null, $max = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zremrangebyrank ($key = null, $min = null, $max = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 */
	public function zremrangebyscore ($key = null, $min = null, $max = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param mixed $scores [optional]
	 */
	public function zrevrange ($key = null, $start = null, $end = null, $scores = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $min
	 * @param mixed $max
	 * @param mixed $offset [optional]
	 * @param mixed $limit [optional]
	 */
	public function zrevrangebylex ($key = null, $min = null, $max = null, $offset = null, $limit = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $start
	 * @param mixed $end
	 * @param array[] $options [optional]
	 */
	public function zrevrangebyscore ($key = null, $start = null, $end = null, array $options = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function zrevrank ($key = null, $member = null) {}

	/**
	 * @param mixed $str_key
	 * @param mixed $i_iterator
	 * @param mixed $str_pattern [optional]
	 * @param mixed $i_count [optional]
	 */
	public function zscan ($str_key = null, &$i_iterator = null, $str_pattern = null, $i_count = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $member
	 */
	public function zscore ($key = null, $member = null) {}

	/**
	 * @param mixed $key
	 * @param array[] $keys
	 * @param array|null[] $weights [optional]
	 * @param mixed $aggregate [optional]
	 */
	public function zunionstore ($key = null, array $keys, array $weights = null, $aggregate = null) {}

}

class RedisSentinel  {

	/**
	 * @param mixed $host
	 * @param mixed $port [optional]
	 * @param mixed $timeout [optional]
	 * @param mixed $persistent [optional]
	 * @param mixed $retry_interval [optional]
	 * @param mixed $read_timeout [optional]
	 */
	public function __construct ($host = null, $port = null, $timeout = null, $persistent = null, $retry_interval = null, $read_timeout = null) {}

	/**
	 * @param mixed $value
	 */
	public function ckquorum ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public function failover ($value = null) {}

	public function flushconfig () {}

	/**
	 * @param mixed $value
	 */
	public function getMasterAddrByName ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public function master ($value = null) {}

	public function masters () {}

	public function ping () {}

	/**
	 * @param mixed $value
	 */
	public function reset ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public function sentinels ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public function slaves ($value = null) {}

}

class RedisException extends Exception implements Throwable, Stringable {
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

class RedisClusterException extends Exception implements Throwable, Stringable {
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
// End of redis v.5.3.7
