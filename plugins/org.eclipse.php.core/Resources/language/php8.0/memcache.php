<?php

// Start of memcache v.8.2

class MemcachePool  {

	/**
	 * @param mixed $host
	 * @param mixed $tcp_port [optional]
	 * @param mixed $udp_port [optional]
	 * @param mixed $persistent [optional]
	 * @param mixed $weight [optional]
	 * @param mixed $timeout [optional]
	 * @param mixed $retry_interval [optional]
	 */
	public function connect ($host = null, $tcp_port = null, $udp_port = null, $persistent = null, $weight = null, $timeout = null, $retry_interval = null) {}

	/**
	 * @param mixed $host
	 * @param mixed $tcp_port [optional]
	 * @param mixed $udp_port [optional]
	 * @param mixed $persistent [optional]
	 * @param mixed $weight [optional]
	 * @param mixed $timeout [optional]
	 * @param mixed $retry_interval [optional]
	 * @param mixed $status [optional]
	 */
	public function addserver ($host = null, $tcp_port = null, $udp_port = null, $persistent = null, $weight = null, $timeout = null, $retry_interval = null, $status = null): bool {}

	/**
	 * @param string $host
	 * @param int $tcp_port [optional]
	 * @param float $timeout [optional]
	 * @param int $retry_interval [optional]
	 * @param bool $status [optional]
	 * @param mixed $failure_callback [optional]
	 */
	public function setserverparams (string $host, int $tcp_port = null, float $timeout = null, int $retry_interval = null, bool $status = null, $failure_callback = null): bool {}

	/**
	 * @param callable|null $failure_callback
	 */
	public function setfailurecallback (callable|null $failure_callback = null): bool {}

	/**
	 * @param string $host
	 * @param int $tcp_port [optional]
	 */
	public function getserverstatus (string $host, int $tcp_port = null): int|bool {}

	/**
	 * @param string $key
	 */
	public function findserver (string $key): string|bool {}

	public function getversion (): string|bool {}

	/**
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function add (array|string $key, mixed $value = null, int $flags = null, int $exptime = null, int $cas = null): bool {}

	/**
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function set (array|string $key, mixed $value = null, int $flags = null, int $exptime = null, int $cas = null): bool {}

	/**
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function replace (array|string $key, mixed $value = null, int $flags = null, int $exptime = null, int $cas = null): bool {}

	/**
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function cas (array|string $key, mixed $value = null, int $flags = null, int $exptime = null, int $cas = null): bool {}

	/**
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function append (array|string $key, mixed $value = null, int $flags = null, int $exptime = null, int $cas = null): bool {}

	/**
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function prepend (array|string $key, mixed $value = null, int $flags = null, int $exptime = null, int $cas = null): bool {}

	/**
	 * @param array|string $key
	 * @param mixed $flags [optional]
	 * @param mixed $cas [optional]
	 */
	public function get (array|string $key, mixed &$flags = null, mixed &$cas = null): mixed {}

	/**
	 * @param array|string $key
	 * @param int $exptime [optional]
	 */
	public function delete (array|string $key, int $exptime = null): array|bool {}

	/**
	 * @param string $type [optional]
	 * @param int $slabid [optional]
	 * @param int $limit [optional]
	 */
	public function getstats (string $type = null, int $slabid = null, int $limit = null): array|bool {}

	/**
	 * @param string $type [optional]
	 * @param int $slabid [optional]
	 * @param int $limit [optional]
	 */
	public function getextendedstats (string $type = null, int $slabid = null, int $limit = null): array|bool {}

	/**
	 * @param int $threshold
	 * @param float $min_savings [optional]
	 */
	public function setcompressthreshold (int $threshold, float $min_savings = null): bool {}

	/**
	 * @param array|string $key
	 * @param int $value [optional]
	 * @param int $defval [optional]
	 * @param int $exptime [optional]
	 */
	public function increment (array|string $key, int $value = null, int $defval = null, int $exptime = null): array|int|bool {}

	/**
	 * @param array|string $key
	 * @param int $value [optional]
	 * @param int $defval [optional]
	 * @param int $exptime [optional]
	 */
	public function decrement (array|string $key, int $value = null, int $defval = null, int $exptime = null): array|int|bool {}

	public function close (): bool {}

	/**
	 * @param int $delay [optional]
	 */
	public function flush (int $delay = null): bool {}

	/**
	 * @param string $username
	 * @param string $password
	 */
	public function setSaslAuthData (string $username, string $password): bool {}

}

/**
 * Represents a connection to a set of memcache servers.
 * @link http://www.php.net/manual/en/class.memcache.php
 */
class Memcache extends MemcachePool  {

	/**
	 * Open memcached server connection
	 * @link http://www.php.net/manual/en/memcache.connect.php
	 * @param string $host Point to the host where memcached is listening for connections. This parameter
	 * may also specify other transports like unix:///path/to/memcached.sock
	 * to use UNIX domain sockets, in this case port must also
	 * be set to 0.
	 * @param int $port [optional] <p>
	 * Point to the port where memcached is listening for connections. Set this
	 * parameter to 0 when using UNIX domain sockets.
	 * </p>
	 * <p>
	 * Please note: port defaults to
	 * memcache.default_port
	 * if not specified. For this reason it is wise to specify the port
	 * explicitly in this method call.
	 * </p>
	 * @param int $timeout [optional] Value in seconds which will be used for connecting to the daemon. Think
	 * twice before changing the default value of 1 second - you can lose all
	 * the advantages of caching if your connection is too slow.
	 * @return bool true on success or false on failure
	 */
	public function connect (string $host, int $port = null, int $timeout = null) {}

	/**
	 * Open memcached server persistent connection
	 * @link http://www.php.net/manual/en/memcache.pconnect.php
	 * @param string $host Point to the host where memcached is listening for connections. This parameter
	 * may also specify other transports like unix:///path/to/memcached.sock
	 * to use UNIX domain sockets, in this case port must also
	 * be set to 0.
	 * @param int $port [optional] Point to the port where memcached is listening for connections. Set this
	 * parameter to 0 when using UNIX domain sockets.
	 * @param int $timeout [optional] Value in seconds which will be used for connecting to the daemon. Think
	 * twice before changing the default value of 1 second - you can lose all
	 * the advantages of caching if your connection is too slow.
	 * @return mixed a Memcache object or false on failure.
	 */
	public function pconnect (string $host, int $port = null, int $timeout = null) {}

	/**
	 * Add a memcached server to connection pool
	 * @link http://www.php.net/manual/en/memcache.addserver.php
	 * @param string $host Point to the host where memcached is listening for connections. This parameter
	 * may also specify other transports like unix:///path/to/memcached.sock
	 * to use UNIX domain sockets, in this case port must also
	 * be set to 0.
	 * @param int $port [optional] <p>
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
	 * @param bool $persistent [optional] Controls the use of a persistent connection. Default to true.
	 * @param int $weight [optional] Number of buckets to create for this server which in turn control its
	 * probability of it being selected. The probability is relative to the
	 * total weight of all servers.
	 * @param int $timeout [optional] Value in seconds which will be used for connecting to the daemon. Think
	 * twice before changing the default value of 1 second - you can lose all
	 * the advantages of caching if your connection is too slow.
	 * @param int $retry_interval [optional] <p>
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
	 * @param bool $status [optional] Controls if the server should be flagged as online. Setting this parameter
	 * to false and retry_interval to -1 allows a failed 
	 * server to be kept in the pool so as not to affect the key distribution 
	 * algorithm. Requests for this server will then failover or fail immediately 
	 * depending on the memcache.allow_failover setting.
	 * Default to true, meaning the server should be considered online.
	 * @param callable $failure_callback [optional] Allows the user to specify a callback function to run upon encountering an 
	 * error. The callback is run before failover is attempted. The function takes 
	 * two parameters, the hostname and port of the failed server.
	 * @param int $timeoutms [optional] 
	 * @return bool true on success or false on failure
	 */
	public function addserver (string $host, int $port = null, bool $persistent = null, int $weight = null, int $timeout = null, int $retry_interval = null, bool $status = null, callable $failure_callback = null, int $timeoutms = null): bool {}

	/**
	 * @param string $host
	 * @param int $tcp_port [optional]
	 * @param float $timeout [optional]
	 * @param int $retry_interval [optional]
	 * @param bool $status [optional]
	 * @param mixed $failure_callback [optional]
	 */
	public function setserverparams (string $host, int $tcp_port = null, float $timeout = null, int $retry_interval = null, bool $status = null, $failure_callback = null): bool {}

	/**
	 * @param callable|null $failure_callback
	 */
	public function setfailurecallback (callable|null $failure_callback = null): bool {}

	/**
	 * @param string $host
	 * @param int $tcp_port [optional]
	 */
	public function getserverstatus (string $host, int $tcp_port = null): int|bool {}

	/**
	 * @param string $key
	 */
	public function findserver (string $key): string|bool {}

	public function getversion (): string|bool {}

	/**
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function add (array|string $key, mixed $value = null, int $flags = null, int $exptime = null, int $cas = null): bool {}

	/**
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function set (array|string $key, mixed $value = null, int $flags = null, int $exptime = null, int $cas = null): bool {}

	/**
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function replace (array|string $key, mixed $value = null, int $flags = null, int $exptime = null, int $cas = null): bool {}

	/**
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function cas (array|string $key, mixed $value = null, int $flags = null, int $exptime = null, int $cas = null): bool {}

	/**
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function append (array|string $key, mixed $value = null, int $flags = null, int $exptime = null, int $cas = null): bool {}

	/**
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function prepend (array|string $key, mixed $value = null, int $flags = null, int $exptime = null, int $cas = null): bool {}

	/**
	 * @param array|string $key
	 * @param mixed $flags [optional]
	 * @param mixed $cas [optional]
	 */
	public function get (array|string $key, mixed &$flags = null, mixed &$cas = null): mixed {}

	/**
	 * @param array|string $key
	 * @param int $exptime [optional]
	 */
	public function delete (array|string $key, int $exptime = null): array|bool {}

	/**
	 * @param string $type [optional]
	 * @param int $slabid [optional]
	 * @param int $limit [optional]
	 */
	public function getstats (string $type = null, int $slabid = null, int $limit = null): array|bool {}

	/**
	 * @param string $type [optional]
	 * @param int $slabid [optional]
	 * @param int $limit [optional]
	 */
	public function getextendedstats (string $type = null, int $slabid = null, int $limit = null): array|bool {}

	/**
	 * @param int $threshold
	 * @param float $min_savings [optional]
	 */
	public function setcompressthreshold (int $threshold, float $min_savings = null): bool {}

	/**
	 * @param array|string $key
	 * @param int $value [optional]
	 * @param int $defval [optional]
	 * @param int $exptime [optional]
	 */
	public function increment (array|string $key, int $value = null, int $defval = null, int $exptime = null): array|int|bool {}

	/**
	 * @param array|string $key
	 * @param int $value [optional]
	 * @param int $defval [optional]
	 * @param int $exptime [optional]
	 */
	public function decrement (array|string $key, int $value = null, int $defval = null, int $exptime = null): array|int|bool {}

	public function close (): bool {}

	/**
	 * @param int $delay [optional]
	 */
	public function flush (int $delay = null): bool {}

	/**
	 * @param string $username
	 * @param string $password
	 */
	public function setSaslAuthData (string $username, string $password): bool {}

}

/**
 * @param mixed $host
 * @param mixed $port [optional]
 * @param mixed $timeout [optional]
 * @param mixed $unused4 [optional]
 * @param mixed $unused5 [optional]
 * @param mixed $unused6 [optional]
 * @param mixed $unused7 [optional]
 * @param mixed $unugsed8 [optional]
 */
function memcache_connect ($host = null, $port = null, $timeout = null, $unused4 = null, $unused5 = null, $unused6 = null, $unused7 = null, $unugsed8 = null) {}

/**
 * @param mixed $host
 * @param mixed $port [optional]
 * @param mixed $timeout [optional]
 * @param mixed $unused4 [optional]
 * @param mixed $unused5 [optional]
 * @param mixed $unused6 [optional]
 * @param mixed $unused7 [optional]
 * @param mixed $unugsed8 [optional]
 */
function memcache_pconnect ($host = null, $port = null, $timeout = null, $unused4 = null, $unused5 = null, $unused6 = null, $unused7 = null, $unugsed8 = null) {}

/**
 * @param MemcachePool $memcache
 * @param mixed $host
 * @param mixed $port [optional]
 * @param mixed $tcp_port [optional]
 * @param mixed $persistent [optional]
 * @param mixed $weight [optional]
 * @param mixed $timeout [optional]
 * @param mixed $retry_interval [optional]
 * @param mixed $status [optional]
 * @param mixed $failure_callback [optional]
 */
function memcache_add_server (MemcachePool $memcache, $host = null, $port = null, $tcp_port = null, $persistent = null, $weight = null, $timeout = null, $retry_interval = null, $status = null, $failure_callback = null): bool {}

/**
 * @param MemcachePool $memcache
 * @param string $host
 * @param int $tcp_port [optional]
 * @param float $timeout [optional]
 * @param int $retry_interval [optional]
 * @param bool $status [optional]
 * @param mixed $failure_callback [optional]
 */
function memcache_set_server_params (MemcachePool $memcache, string $host, int $tcp_port = null, float $timeout = null, int $retry_interval = null, bool $status = null, $failure_callback = null): bool {}

/**
 * @param MemcachePool $memcache
 * @param callable|null $failure_callback
 */
function memcache_set_failure_callback (MemcachePool $memcache, callable|null $failure_callback = null): bool {}

/**
 * @param MemcachePool $memcache
 * @param string $host
 * @param int $tcp_port [optional]
 */
function memcache_get_server_status (MemcachePool $memcache, string $host, int $tcp_port = null): int|bool {}

/**
 * @param MemcachePool $memcache
 */
function memcache_get_version (MemcachePool $memcache): string|bool {}

/**
 * @param MemcachePool $memcache
 * @param array|string $key
 * @param mixed $value [optional]
 * @param int $flags [optional]
 * @param int $exptime [optional]
 * @param int $cas [optional]
 */
function memcache_add (MemcachePool $memcache, array|string $key, mixed $value = null, int $flags = null, int $exptime = null, int $cas = null): bool {}

/**
 * @param MemcachePool $memcache
 * @param array|string $key
 * @param mixed $value [optional]
 * @param int $flags [optional]
 * @param int $exptime [optional]
 * @param int $cas [optional]
 */
function memcache_set (MemcachePool $memcache, array|string $key, mixed $value = null, int $flags = null, int $exptime = null, int $cas = null): bool {}

/**
 * @param MemcachePool $memcache
 * @param array|string $key
 * @param mixed $value [optional]
 * @param int $flags [optional]
 * @param int $exptime [optional]
 * @param int $cas [optional]
 */
function memcache_replace (MemcachePool $memcache, array|string $key, mixed $value = null, int $flags = null, int $exptime = null, int $cas = null): bool {}

/**
 * @param MemcachePool $memcache
 * @param array|string $key
 * @param mixed $value [optional]
 * @param int $flags [optional]
 * @param int $exptime [optional]
 * @param int $cas [optional]
 */
function memcache_cas (MemcachePool $memcache, array|string $key, mixed $value = null, int $flags = null, int $exptime = null, int $cas = null): bool {}

/**
 * @param MemcachePool $memcache
 * @param array|string $key
 * @param mixed $value [optional]
 * @param int $flags [optional]
 * @param int $exptime [optional]
 * @param int $cas [optional]
 */
function memcache_append (MemcachePool $memcache, array|string $key, mixed $value = null, int $flags = null, int $exptime = null, int $cas = null): bool {}

/**
 * @param MemcachePool $memcache
 * @param array|string $key
 * @param mixed $value [optional]
 * @param int $flags [optional]
 * @param int $exptime [optional]
 * @param int $cas [optional]
 */
function memcache_prepend (MemcachePool $memcache, array|string $key, mixed $value = null, int $flags = null, int $exptime = null, int $cas = null): bool {}

/**
 * @param MemcachePool $memcache
 * @param mixed $key
 * @param mixed $flags [optional]
 * @param mixed $cas [optional]
 */
function memcache_get (MemcachePool $memcache, $key = null, &$flags = null, &$cas = null): mixed {}

/**
 * @param MemcachePool $memcache
 * @param mixed $key
 * @param mixed $exptime [optional]
 */
function memcache_delete (MemcachePool $memcache, $key = null, $exptime = null): array|bool {}

/**
 * Turn debug output on/off
 * @link http://www.php.net/manual/en/function.memcache-debug.php
 * @param bool $on_off Turns debug output on if equals to true.
 * Turns debug output off if equals to false.
 * @return bool true if PHP was built with --enable-debug option, otherwise
 * returns false.
 */
function memcache_debug (bool $on_off): bool {}

/**
 * @param MemcachePool $memcache
 * @param string $type [optional]
 * @param int $slabid [optional]
 * @param int $limit [optional]
 */
function memcache_get_stats (MemcachePool $memcache, string $type = null, int $slabid = null, int $limit = null): array|bool {}

/**
 * @param MemcachePool $memcache
 * @param string $type [optional]
 * @param int $slabid [optional]
 * @param int $limit [optional]
 */
function memcache_get_extended_stats (MemcachePool $memcache, string $type = null, int $slabid = null, int $limit = null): array|bool {}

/**
 * @param MemcachePool $memcache
 * @param int $threshold
 * @param float $min_savings [optional]
 */
function memcache_set_compress_threshold (MemcachePool $memcache, int $threshold, float $min_savings = null): bool {}

/**
 * @param MemcachePool $memcache
 * @param array|string $key
 * @param int $value [optional]
 * @param int $defval [optional]
 * @param int $exptime [optional]
 */
function memcache_increment (MemcachePool $memcache, array|string $key, int $value = null, int $defval = null, int $exptime = null): array|int|bool {}

/**
 * @param MemcachePool $memcache
 * @param array|string $key
 * @param int $value [optional]
 * @param int $defval [optional]
 * @param int $exptime [optional]
 */
function memcache_decrement (MemcachePool $memcache, array|string $key, int $value = null, int $defval = null, int $exptime = null): array|int|bool {}

/**
 * @param MemcachePool $memcache
 */
function memcache_close (MemcachePool $memcache): bool {}

/**
 * @param MemcachePool $memcache
 * @param int $delay [optional]
 */
function memcache_flush (MemcachePool $memcache, int $delay = null): bool {}

/**
 * @param MemcachePool $memcache
 * @param string $username
 * @param string $password
 */
function memcache_set_sasl_auth_data (MemcachePool $memcache, string $username, string $password): bool {}


/**
 * Used to turn on-the-fly data compression on with
 * Memcache::set, 
 * Memcache::add and
 * Memcache::replace.
 * @link http://www.php.net/manual/en/memcache.constants.php
 */
define ('MEMCACHE_COMPRESSED', 2);

/**
 * Used to turn user-defined application flag on with
 * Memcache::set, 
 * Memcache::add and
 * Memcache::replace.
 * @link http://www.php.net/manual/en/memcache.constants.php
 */
define ('MEMCACHE_USER1', 65536);

/**
 * Used to turn user-defined application flag on with
 * Memcache::set, 
 * Memcache::add and
 * Memcache::replace.
 * @link http://www.php.net/manual/en/memcache.constants.php
 */
define ('MEMCACHE_USER2', 131072);

/**
 * Used to turn user-defined application flag on with
 * Memcache::set, 
 * Memcache::add and
 * Memcache::replace.
 * @link http://www.php.net/manual/en/memcache.constants.php
 */
define ('MEMCACHE_USER3', 262144);

/**
 * Used to turn user-defined application flag on with
 * Memcache::set, 
 * Memcache::add and
 * Memcache::replace.
 * @link http://www.php.net/manual/en/memcache.constants.php
 */
define ('MEMCACHE_USER4', 524288);

/**
 * 1 if this Memcache session handler is available, 0 otherwise.
 * @link http://www.php.net/manual/en/memcache.constants.php
 */
define ('MEMCACHE_HAVE_SESSION', 1);

// End of memcache v.8.2
