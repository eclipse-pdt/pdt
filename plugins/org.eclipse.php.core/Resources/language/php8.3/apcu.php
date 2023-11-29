<?php

// Start of apcu v.5.1.22

class APCUIterator implements Iterator, Traversable {

	/**
	 * {@inheritdoc}
	 * @param mixed $search [optional]
	 * @param int $format [optional]
	 * @param int $chunk_size [optional]
	 * @param int $list [optional]
	 */
	public function __construct ($search = NULL, int $format = 4294967295, int $chunk_size = 0, int $list = 1) {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function next (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function valid (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function key (): string|int {}

	/**
	 * {@inheritdoc}
	 */
	public function current (): mixed {}

	/**
	 * {@inheritdoc}
	 */
	public function getTotalHits (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getTotalSize (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getTotalCount (): int {}

}

/**
 * {@inheritdoc}
 */
function apcu_clear_cache (): bool {}

/**
 * {@inheritdoc}
 * @param bool $limited [optional]
 */
function apcu_cache_info (bool $limited = false): array|false {}

/**
 * {@inheritdoc}
 * @param string $key
 */
function apcu_key_info (string $key): ?array {}

/**
 * {@inheritdoc}
 * @param bool $limited [optional]
 */
function apcu_sma_info (bool $limited = false): array|false {}

/**
 * {@inheritdoc}
 */
function apcu_enabled (): bool {}

/**
 * {@inheritdoc}
 * @param mixed $key
 * @param mixed $value [optional]
 * @param int $ttl [optional]
 */
function apcu_store ($key = null, mixed $value = NULL, int $ttl = 0): array|bool {}

/**
 * {@inheritdoc}
 * @param mixed $key
 * @param mixed $value [optional]
 * @param int $ttl [optional]
 */
function apcu_add ($key = null, mixed $value = NULL, int $ttl = 0): array|bool {}

/**
 * {@inheritdoc}
 * @param string $key
 * @param int $step [optional]
 * @param mixed $success [optional]
 * @param int $ttl [optional]
 */
function apcu_inc (string $key, int $step = 1, &$success = NULL, int $ttl = 0): int|false {}

/**
 * {@inheritdoc}
 * @param string $key
 * @param int $step [optional]
 * @param mixed $success [optional]
 * @param int $ttl [optional]
 */
function apcu_dec (string $key, int $step = 1, &$success = NULL, int $ttl = 0): int|false {}

/**
 * {@inheritdoc}
 * @param string $key
 * @param int $old
 * @param int $new
 */
function apcu_cas (string $key, int $old, int $new): bool {}

/**
 * {@inheritdoc}
 * @param mixed $key
 * @param mixed $success [optional]
 */
function apcu_fetch ($key = null, &$success = NULL): mixed {}

/**
 * {@inheritdoc}
 * @param mixed $key
 */
function apcu_exists ($key = null): array|bool {}

/**
 * {@inheritdoc}
 * @param mixed $key
 */
function apcu_delete ($key = null): array|bool {}

/**
 * {@inheritdoc}
 * @param string $key
 * @param callable $callback
 * @param int $ttl [optional]
 */
function apcu_entry (string $key, callable $callback, int $ttl = 0): mixed {}

define ('APC_LIST_ACTIVE', 1);
define ('APC_LIST_DELETED', 2);
define ('APC_ITER_TYPE', 1);
define ('APC_ITER_KEY', 2);
define ('APC_ITER_VALUE', 4);
define ('APC_ITER_NUM_HITS', 8);
define ('APC_ITER_MTIME', 16);
define ('APC_ITER_CTIME', 32);
define ('APC_ITER_DTIME', 64);
define ('APC_ITER_ATIME', 128);
define ('APC_ITER_REFCOUNT', 256);
define ('APC_ITER_MEM_SIZE', 512);
define ('APC_ITER_TTL', 1024);
define ('APC_ITER_NONE', 0);
define ('APC_ITER_ALL', 4294967295);

// End of apcu v.5.1.22
