<?php

// Start of session v.8.3.0

interface SessionHandlerInterface  {

	/**
	 * {@inheritdoc}
	 * @param string $path
	 * @param string $name
	 */
	abstract public function open (string $path, string $name);

	/**
	 * {@inheritdoc}
	 */
	abstract public function close ();

	/**
	 * {@inheritdoc}
	 * @param string $id
	 */
	abstract public function read (string $id);

	/**
	 * {@inheritdoc}
	 * @param string $id
	 * @param string $data
	 */
	abstract public function write (string $id, string $data);

	/**
	 * {@inheritdoc}
	 * @param string $id
	 */
	abstract public function destroy (string $id);

	/**
	 * {@inheritdoc}
	 * @param int $max_lifetime
	 */
	abstract public function gc (int $max_lifetime);

}

interface SessionIdInterface  {

	/**
	 * {@inheritdoc}
	 */
	abstract public function create_sid ();

}

interface SessionUpdateTimestampHandlerInterface  {

	/**
	 * {@inheritdoc}
	 * @param string $id
	 */
	abstract public function validateId (string $id);

	/**
	 * {@inheritdoc}
	 * @param string $id
	 * @param string $data
	 */
	abstract public function updateTimestamp (string $id, string $data);

}

class SessionHandler implements SessionHandlerInterface, SessionIdInterface {

	/**
	 * {@inheritdoc}
	 * @param string $path
	 * @param string $name
	 */
	public function open (string $path, string $name) {}

	/**
	 * {@inheritdoc}
	 */
	public function close () {}

	/**
	 * {@inheritdoc}
	 * @param string $id
	 */
	public function read (string $id) {}

	/**
	 * {@inheritdoc}
	 * @param string $id
	 * @param string $data
	 */
	public function write (string $id, string $data) {}

	/**
	 * {@inheritdoc}
	 * @param string $id
	 */
	public function destroy (string $id) {}

	/**
	 * {@inheritdoc}
	 * @param int $max_lifetime
	 */
	public function gc (int $max_lifetime) {}

	/**
	 * {@inheritdoc}
	 */
	public function create_sid () {}

}

/**
 * {@inheritdoc}
 * @param string|null $name [optional]
 */
function session_name (?string $name = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string|null $module [optional]
 */
function session_module_name (?string $module = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string|null $path [optional]
 */
function session_save_path (?string $path = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string|null $id [optional]
 */
function session_id (?string $id = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $prefix [optional]
 */
function session_create_id (string $prefix = ''): string|false {}

/**
 * {@inheritdoc}
 * @param bool $delete_old_session [optional]
 */
function session_regenerate_id (bool $delete_old_session = false): bool {}

/**
 * {@inheritdoc}
 * @param string $data
 */
function session_decode (string $data): bool {}

/**
 * {@inheritdoc}
 */
function session_encode (): string|false {}

/**
 * {@inheritdoc}
 */
function session_destroy (): bool {}

/**
 * {@inheritdoc}
 */
function session_unset (): bool {}

/**
 * {@inheritdoc}
 */
function session_gc (): int|false {}

/**
 * {@inheritdoc}
 */
function session_get_cookie_params (): array {}

/**
 * {@inheritdoc}
 */
function session_write_close (): bool {}

/**
 * {@inheritdoc}
 */
function session_abort (): bool {}

/**
 * {@inheritdoc}
 */
function session_reset (): bool {}

/**
 * {@inheritdoc}
 */
function session_status (): int {}

/**
 * {@inheritdoc}
 */
function session_register_shutdown (): void {}

/**
 * {@inheritdoc}
 */
function session_commit (): bool {}

/**
 * {@inheritdoc}
 * @param mixed $open
 * @param mixed $close [optional]
 * @param callable $read [optional]
 * @param callable $write [optional]
 * @param callable $destroy [optional]
 * @param callable $gc [optional]
 * @param callable|null $create_sid [optional]
 * @param callable|null $validate_sid [optional]
 * @param callable|null $update_timestamp [optional]
 */
function session_set_save_handler ($open = null, $close = NULL, callable $read = NULL, callable $write = NULL, callable $destroy = NULL, callable $gc = NULL, ?callable $create_sid = NULL, ?callable $validate_sid = NULL, ?callable $update_timestamp = NULL): bool {}

/**
 * {@inheritdoc}
 * @param string|null $value [optional]
 */
function session_cache_limiter (?string $value = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param int|null $value [optional]
 */
function session_cache_expire (?int $value = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param array|int $lifetime_or_options
 * @param string|null $path [optional]
 * @param string|null $domain [optional]
 * @param bool|null $secure [optional]
 * @param bool|null $httponly [optional]
 */
function session_set_cookie_params (array|int $lifetime_or_options, ?string $path = NULL, ?string $domain = NULL, ?bool $secure = NULL, ?bool $httponly = NULL): bool {}

/**
 * {@inheritdoc}
 * @param array $options [optional]
 */
function session_start (array $options = array (
)): bool {}

define ('PHP_SESSION_DISABLED', 0);
define ('PHP_SESSION_NONE', 1);
define ('PHP_SESSION_ACTIVE', 2);

// End of session v.8.3.0
