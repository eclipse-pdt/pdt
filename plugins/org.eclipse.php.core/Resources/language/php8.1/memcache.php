<?php

// Start of memcache v.8.2

class MemcachePool  {

	/**
	 * {@inheritdoc}
	 * @param mixed $host
	 * @param mixed $tcp_port [optional]
	 * @param mixed $udp_port [optional]
	 * @param mixed $persistent [optional]
	 * @param mixed $weight [optional]
	 * @param mixed $timeout [optional]
	 * @param mixed $retry_interval [optional]
	 */
	public function connect ($host = null, $tcp_port = NULL, $udp_port = NULL, $persistent = NULL, $weight = NULL, $timeout = NULL, $retry_interval = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $host
	 * @param mixed $tcp_port [optional]
	 * @param mixed $udp_port [optional]
	 * @param mixed $persistent [optional]
	 * @param mixed $weight [optional]
	 * @param mixed $timeout [optional]
	 * @param mixed $retry_interval [optional]
	 * @param mixed $status [optional]
	 */
	public function addserver ($host = null, $tcp_port = NULL, $udp_port = NULL, $persistent = NULL, $weight = NULL, $timeout = NULL, $retry_interval = NULL, $status = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $host
	 * @param int $tcp_port [optional]
	 * @param float $timeout [optional]
	 * @param int $retry_interval [optional]
	 * @param bool $status [optional]
	 * @param mixed $failure_callback [optional]
	 */
	public function setserverparams (string $host, int $tcp_port = NULL, float $timeout = NULL, int $retry_interval = NULL, bool $status = NULL, $failure_callback = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $failure_callback
	 */
	public function setfailurecallback (?callable $failure_callback = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $host
	 * @param int $tcp_port [optional]
	 */
	public function getserverstatus (string $host, int $tcp_port = NULL): int|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function findserver (string $key): string|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getversion (): string|bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function add (array|string $key, mixed $value = NULL, int $flags = NULL, int $exptime = NULL, int $cas = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function set (array|string $key, mixed $value = NULL, int $flags = NULL, int $exptime = NULL, int $cas = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function replace (array|string $key, mixed $value = NULL, int $flags = NULL, int $exptime = NULL, int $cas = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function cas (array|string $key, mixed $value = NULL, int $flags = NULL, int $exptime = NULL, int $cas = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function append (array|string $key, mixed $value = NULL, int $flags = NULL, int $exptime = NULL, int $cas = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function prepend (array|string $key, mixed $value = NULL, int $flags = NULL, int $exptime = NULL, int $cas = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param mixed $flags [optional]
	 * @param mixed $cas [optional]
	 */
	public function get (array|string $key, mixed &$flags = NULL, mixed &$cas = NULL): mixed {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param int $exptime [optional]
	 */
	public function delete (array|string $key, int $exptime = NULL): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $type [optional]
	 * @param int $slabid [optional]
	 * @param int $limit [optional]
	 */
	public function getstats (string $type = NULL, int $slabid = NULL, int $limit = NULL): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $type [optional]
	 * @param int $slabid [optional]
	 * @param int $limit [optional]
	 */
	public function getextendedstats (string $type = NULL, int $slabid = NULL, int $limit = NULL): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param int $threshold
	 * @param float $min_savings [optional]
	 */
	public function setcompressthreshold (int $threshold, float $min_savings = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param int $value [optional]
	 * @param int $defval [optional]
	 * @param int $exptime [optional]
	 */
	public function increment (array|string $key, int $value = NULL, int $defval = NULL, int $exptime = NULL): array|int|bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param int $value [optional]
	 * @param int $defval [optional]
	 * @param int $exptime [optional]
	 */
	public function decrement (array|string $key, int $value = NULL, int $defval = NULL, int $exptime = NULL): array|int|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function close (): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $delay [optional]
	 */
	public function flush (int $delay = NULL): bool {}

	/**
	 * {@inheritdoc}
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
	 * @param string $host 
	 * @param int $port [optional] 
	 * @param int $timeout [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function connect (string $host, int $port = null, int $timeout = null): bool {}

	/**
	 * Open memcached server persistent connection
	 * @link http://www.php.net/manual/en/memcache.pconnect.php
	 * @param string $host 
	 * @param int $port [optional] 
	 * @param int $timeout [optional] 
	 * @return mixed Returns a Memcache object or false on failure.
	 */
	public function pconnect (string $host, int $port = null, int $timeout = null): mixed {}

	/**
	 * Add a memcached server to connection pool
	 * @link http://www.php.net/manual/en/memcache.addserver.php
	 * @param string $host 
	 * @param int $port [optional] 
	 * @param bool $persistent [optional] 
	 * @param int $weight [optional] 
	 * @param int $timeout [optional] 
	 * @param int $retry_interval [optional] 
	 * @param bool $status [optional] 
	 * @param callable $failure_callback [optional] 
	 * @param int $timeoutms [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function addserver (string $host, int $port = 11211, bool $persistent = null, int $weight = null, int $timeout = null, int $retry_interval = null, bool $status = null, callable $failure_callback = null, int $timeoutms = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $host
	 * @param int $tcp_port [optional]
	 * @param float $timeout [optional]
	 * @param int $retry_interval [optional]
	 * @param bool $status [optional]
	 * @param mixed $failure_callback [optional]
	 */
	public function setserverparams (string $host, int $tcp_port = NULL, float $timeout = NULL, int $retry_interval = NULL, bool $status = NULL, $failure_callback = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $failure_callback
	 */
	public function setfailurecallback (?callable $failure_callback = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $host
	 * @param int $tcp_port [optional]
	 */
	public function getserverstatus (string $host, int $tcp_port = NULL): int|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function findserver (string $key): string|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getversion (): string|bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function add (array|string $key, mixed $value = NULL, int $flags = NULL, int $exptime = NULL, int $cas = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function set (array|string $key, mixed $value = NULL, int $flags = NULL, int $exptime = NULL, int $cas = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function replace (array|string $key, mixed $value = NULL, int $flags = NULL, int $exptime = NULL, int $cas = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function cas (array|string $key, mixed $value = NULL, int $flags = NULL, int $exptime = NULL, int $cas = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function append (array|string $key, mixed $value = NULL, int $flags = NULL, int $exptime = NULL, int $cas = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param mixed $value [optional]
	 * @param int $flags [optional]
	 * @param int $exptime [optional]
	 * @param int $cas [optional]
	 */
	public function prepend (array|string $key, mixed $value = NULL, int $flags = NULL, int $exptime = NULL, int $cas = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param mixed $flags [optional]
	 * @param mixed $cas [optional]
	 */
	public function get (array|string $key, mixed &$flags = NULL, mixed &$cas = NULL): mixed {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param int $exptime [optional]
	 */
	public function delete (array|string $key, int $exptime = NULL): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $type [optional]
	 * @param int $slabid [optional]
	 * @param int $limit [optional]
	 */
	public function getstats (string $type = NULL, int $slabid = NULL, int $limit = NULL): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $type [optional]
	 * @param int $slabid [optional]
	 * @param int $limit [optional]
	 */
	public function getextendedstats (string $type = NULL, int $slabid = NULL, int $limit = NULL): array|bool {}

	/**
	 * {@inheritdoc}
	 * @param int $threshold
	 * @param float $min_savings [optional]
	 */
	public function setcompressthreshold (int $threshold, float $min_savings = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param int $value [optional]
	 * @param int $defval [optional]
	 * @param int $exptime [optional]
	 */
	public function increment (array|string $key, int $value = NULL, int $defval = NULL, int $exptime = NULL): array|int|bool {}

	/**
	 * {@inheritdoc}
	 * @param array|string $key
	 * @param int $value [optional]
	 * @param int $defval [optional]
	 * @param int $exptime [optional]
	 */
	public function decrement (array|string $key, int $value = NULL, int $defval = NULL, int $exptime = NULL): array|int|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function close (): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $delay [optional]
	 */
	public function flush (int $delay = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $username
	 * @param string $password
	 */
	public function setSaslAuthData (string $username, string $password): bool {}

}

/**
 * {@inheritdoc}
 * @param mixed $host
 * @param mixed $port [optional]
 * @param mixed $timeout [optional]
 * @param mixed $unused4 [optional]
 * @param mixed $unused5 [optional]
 * @param mixed $unused6 [optional]
 * @param mixed $unused7 [optional]
 * @param mixed $unugsed8 [optional]
 */
function memcache_connect ($host = null, $port = NULL, $timeout = NULL, $unused4 = NULL, $unused5 = NULL, $unused6 = NULL, $unused7 = NULL, $unugsed8 = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $host
 * @param mixed $port [optional]
 * @param mixed $timeout [optional]
 * @param mixed $unused4 [optional]
 * @param mixed $unused5 [optional]
 * @param mixed $unused6 [optional]
 * @param mixed $unused7 [optional]
 * @param mixed $unugsed8 [optional]
 */
function memcache_pconnect ($host = null, $port = NULL, $timeout = NULL, $unused4 = NULL, $unused5 = NULL, $unused6 = NULL, $unused7 = NULL, $unugsed8 = NULL) {}

/**
 * {@inheritdoc}
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
function memcache_add_server (MemcachePool $memcache, $host = null, $port = NULL, $tcp_port = NULL, $persistent = NULL, $weight = NULL, $timeout = NULL, $retry_interval = NULL, $status = NULL, $failure_callback = NULL): bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 * @param string $host
 * @param int $tcp_port [optional]
 * @param float $timeout [optional]
 * @param int $retry_interval [optional]
 * @param bool $status [optional]
 * @param mixed $failure_callback [optional]
 */
function memcache_set_server_params (MemcachePool $memcache, string $host, int $tcp_port = NULL, float $timeout = NULL, int $retry_interval = NULL, bool $status = NULL, $failure_callback = NULL): bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 * @param callable|null $failure_callback
 */
function memcache_set_failure_callback (MemcachePool $memcache, ?callable $failure_callback = null): bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 * @param string $host
 * @param int $tcp_port [optional]
 */
function memcache_get_server_status (MemcachePool $memcache, string $host, int $tcp_port = NULL): int|bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 */
function memcache_get_version (MemcachePool $memcache): string|bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 * @param array|string $key
 * @param mixed $value [optional]
 * @param int $flags [optional]
 * @param int $exptime [optional]
 * @param int $cas [optional]
 */
function memcache_add (MemcachePool $memcache, array|string $key, mixed $value = NULL, int $flags = NULL, int $exptime = NULL, int $cas = NULL): bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 * @param array|string $key
 * @param mixed $value [optional]
 * @param int $flags [optional]
 * @param int $exptime [optional]
 * @param int $cas [optional]
 */
function memcache_set (MemcachePool $memcache, array|string $key, mixed $value = NULL, int $flags = NULL, int $exptime = NULL, int $cas = NULL): bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 * @param array|string $key
 * @param mixed $value [optional]
 * @param int $flags [optional]
 * @param int $exptime [optional]
 * @param int $cas [optional]
 */
function memcache_replace (MemcachePool $memcache, array|string $key, mixed $value = NULL, int $flags = NULL, int $exptime = NULL, int $cas = NULL): bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 * @param array|string $key
 * @param mixed $value [optional]
 * @param int $flags [optional]
 * @param int $exptime [optional]
 * @param int $cas [optional]
 */
function memcache_cas (MemcachePool $memcache, array|string $key, mixed $value = NULL, int $flags = NULL, int $exptime = NULL, int $cas = NULL): bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 * @param array|string $key
 * @param mixed $value [optional]
 * @param int $flags [optional]
 * @param int $exptime [optional]
 * @param int $cas [optional]
 */
function memcache_append (MemcachePool $memcache, array|string $key, mixed $value = NULL, int $flags = NULL, int $exptime = NULL, int $cas = NULL): bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 * @param array|string $key
 * @param mixed $value [optional]
 * @param int $flags [optional]
 * @param int $exptime [optional]
 * @param int $cas [optional]
 */
function memcache_prepend (MemcachePool $memcache, array|string $key, mixed $value = NULL, int $flags = NULL, int $exptime = NULL, int $cas = NULL): bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 * @param mixed $key
 * @param mixed $flags [optional]
 * @param mixed $cas [optional]
 */
function memcache_get (MemcachePool $memcache, $key = null, &$flags = NULL, &$cas = NULL): mixed {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 * @param mixed $key
 * @param mixed $exptime [optional]
 */
function memcache_delete (MemcachePool $memcache, $key = null, $exptime = NULL): array|bool {}

/**
 * Turn debug output on/off
 * @link http://www.php.net/manual/en/function.memcache-debug.php
 * @param bool $on_off 
 * @return bool Returns true if PHP was built with --enable-debug option, otherwise
 * returns false.
 */
function memcache_debug (bool $on_off): bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 * @param string $type [optional]
 * @param int $slabid [optional]
 * @param int $limit [optional]
 */
function memcache_get_stats (MemcachePool $memcache, string $type = NULL, int $slabid = NULL, int $limit = NULL): array|bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 * @param string $type [optional]
 * @param int $slabid [optional]
 * @param int $limit [optional]
 */
function memcache_get_extended_stats (MemcachePool $memcache, string $type = NULL, int $slabid = NULL, int $limit = NULL): array|bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 * @param int $threshold
 * @param float $min_savings [optional]
 */
function memcache_set_compress_threshold (MemcachePool $memcache, int $threshold, float $min_savings = NULL): bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 * @param array|string $key
 * @param int $value [optional]
 * @param int $defval [optional]
 * @param int $exptime [optional]
 */
function memcache_increment (MemcachePool $memcache, array|string $key, int $value = NULL, int $defval = NULL, int $exptime = NULL): array|int|bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 * @param array|string $key
 * @param int $value [optional]
 * @param int $defval [optional]
 * @param int $exptime [optional]
 */
function memcache_decrement (MemcachePool $memcache, array|string $key, int $value = NULL, int $defval = NULL, int $exptime = NULL): array|int|bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 */
function memcache_close (MemcachePool $memcache): bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 * @param int $delay [optional]
 */
function memcache_flush (MemcachePool $memcache, int $delay = NULL): bool {}

/**
 * {@inheritdoc}
 * @param MemcachePool $memcache
 * @param string $username
 * @param string $password
 */
function memcache_set_sasl_auth_data (MemcachePool $memcache, string $username, string $password): bool {}

define ('MEMCACHE_COMPRESSED', 2);
define ('MEMCACHE_USER1', 65536);
define ('MEMCACHE_USER2', 131072);
define ('MEMCACHE_USER3', 262144);
define ('MEMCACHE_USER4', 524288);
define ('MEMCACHE_HAVE_SESSION', 1);

// End of memcache v.8.2
