<?php

// Start of session v.8.2.6

/**
 * SessionHandlerInterface is an interface which defines the minimal
 * prototype for creating a custom session handler. In order to pass a custom
 * session handler to session_set_save_handler using its
 * OOP invocation, the class can implement this interface.
 * <p>Please note the callback methods of this class are designed to be called internally by
 * PHP and are not meant to be called from user-space code.</p>
 * @link http://www.php.net/manual/en/class.sessionhandlerinterface.php
 */
interface SessionHandlerInterface  {

	/**
	 * Initialize session
	 * @link http://www.php.net/manual/en/sessionhandlerinterface.open.php
	 * @param string $path The path where to store/retrieve the session.
	 * @param string $name The session name.
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	abstract public function open (string $path, string $name): bool;

	/**
	 * Close the session
	 * @link http://www.php.net/manual/en/sessionhandlerinterface.close.php
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	abstract public function close (): bool;

	/**
	 * Read session data
	 * @link http://www.php.net/manual/en/sessionhandlerinterface.read.php
	 * @param string $id The session id.
	 * @return string|false Returns an encoded string of the read data. If nothing was read, it must return false. Note this value is returned internally to PHP for processing.
	 */
	abstract public function read (string $id): string|false;

	/**
	 * Write session data
	 * @link http://www.php.net/manual/en/sessionhandlerinterface.write.php
	 * @param string $id The session id.
	 * @param string $data The encoded session data. This data is the result of the PHP internally encoding the $_SESSION superglobal to a serialized
	 * string and passing it as this parameter. Please note sessions use an alternative serialization method.
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	abstract public function write (string $id, string $data): bool;

	/**
	 * Destroy a session
	 * @link http://www.php.net/manual/en/sessionhandlerinterface.destroy.php
	 * @param string $id 
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	abstract public function destroy (string $id): bool;

	/**
	 * Cleanup old sessions
	 * @link http://www.php.net/manual/en/sessionhandlerinterface.gc.php
	 * @param int $max_lifetime Sessions that have not updated for the last max_lifetime seconds will be removed.
	 * @return int|false Returns the number of deleted sessions on success, or false on failure.
	 * Note this value is returned internally to PHP for processing.
	 */
	abstract public function gc (int $max_lifetime): int|false;

}

/**
 * SessionIdInterface is an interface which defines optional methods
 * for creating a custom session handler. In order to pass a custom
 * session handler to session_set_save_handler using its
 * OOP invocation, the class can implement this interface.
 * <p>Note that the callback methods of classes implementing this interface are designed to be called
 * internally by PHP and are not meant to be called from user-space code.</p>
 * @link http://www.php.net/manual/en/class.sessionidinterface.php
 */
interface SessionIdInterface  {

	/**
	 * Create session ID
	 * @link http://www.php.net/manual/en/sessionidinterface.create-sid.php
	 * @return string The new session ID.
	 * Note that this value is returned internally to PHP for processing.
	 */
	abstract public function create_sid (): string;

}

/**
 * SessionUpdateTimestampHandlerInterface is an interface which defines optional methods
 * for creating a custom session handler. In order to pass a custom
 * session handler to session_set_save_handler using its
 * OOP invocation, the class can implement this interface.
 * <p>Note that the callback methods of classes implementing this interface are designed to be called
 * internally by PHP and are not meant to be called from user-space code.</p>
 * @link http://www.php.net/manual/en/class.sessionupdatetimestamphandlerinterface.php
 */
interface SessionUpdateTimestampHandlerInterface  {

	/**
	 * Validate ID
	 * @link http://www.php.net/manual/en/sessionupdatetimestamphandlerinterface.validateid.php
	 * @param string $id The session ID.
	 * @return bool Returns true for valid ID, false otherwise.
	 * Note that this value is returned internally to PHP for processing.
	 */
	abstract public function validateId (string $id): bool;

	/**
	 * Update timestamp
	 * @link http://www.php.net/manual/en/sessionupdatetimestamphandlerinterface.updatetimestamp.php
	 * @param string $id The session ID.
	 * @param string $data The session data.
	 * @return bool Returns true if the timestamp was updated, false otherwise.
	 * Note that this value is returned internally to PHP for processing.
	 */
	abstract public function updateTimestamp (string $id, string $data): bool;

}

/**
 * SessionHandler is a special class that can be used
 * to expose the current internal PHP session save handler by inheritance.
 * There are seven methods which wrap the seven internal session save handler
 * callbacks (open, close,
 * read, write,
 * destroy, gc and
 * create_sid). By default, this class will wrap
 * whatever internal save handler is set as defined by the
 * session.save_handler
 * configuration directive which is usually files by
 * default. Other internal session save handlers are provided by PHP
 * extensions such as SQLite (as sqlite), Memcache (as
 * memcache), and Memcached (as
 * memcached).
 * <p>When a plain instance of SessionHandler is set as the save handler using
 * session_set_save_handler it will wrap the current save handlers.
 * A class extending from SessionHandler allows you to override
 * the methods or intercept or filter them by calls the parent class methods which ultimately wrap
 * the internal PHP session handlers.</p>
 * <p>This allows you, for example, to intercept the read and write
 * methods to encrypt/decrypt the session data and then pass the result to and from the parent class.
 * Alternatively one might chose to entirely override a method like the garbage collection callback
 * gc.</p>
 * <p>Because the SessionHandler wraps the current internal save handler
 * methods, the above example of encryption can be applied to any internal save handler without
 * having to know the internals of the handlers.</p>
 * <p>To use this class, first set the save handler you wish to expose using
 * session.save_handler and then pass an instance of
 * SessionHandler or one extending it to session_set_save_handler.</p>
 * <p>Please note that the callback methods of this class are designed to be called internally by
 * PHP and are not meant to be called from user-space code. The return values are equally processed internally
 * by PHP. For more information on the session workflow, please refer to session_set_save_handler.</p>
 * @link http://www.php.net/manual/en/class.sessionhandler.php
 */
class SessionHandler implements SessionHandlerInterface, SessionIdInterface {

	/**
	 * Initialize session
	 * @link http://www.php.net/manual/en/sessionhandler.open.php
	 * @param string $path The path where to store/retrieve the session.
	 * @param string $name The session name.
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	public function open (string $path, string $name): bool {}

	/**
	 * Close the session
	 * @link http://www.php.net/manual/en/sessionhandler.close.php
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	public function close (): bool {}

	/**
	 * Read session data
	 * @link http://www.php.net/manual/en/sessionhandler.read.php
	 * @param string $id The session id to read data for.
	 * @return string|false Returns an encoded string of the read data. If nothing was read, it must return false. Note this value is returned internally to PHP for processing.
	 */
	public function read (string $id): string|false {}

	/**
	 * Write session data
	 * @link http://www.php.net/manual/en/sessionhandler.write.php
	 * @param string $id The session id.
	 * @param string $data The encoded session data. This data is the result of the PHP internally encoding the $_SESSION superglobal to a serialized
	 * string and passing it as this parameter. Please note sessions use an alternative serialization method.
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	public function write (string $id, string $data): bool {}

	/**
	 * Destroy a session
	 * @link http://www.php.net/manual/en/sessionhandler.destroy.php
	 * @param string $id 
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	public function destroy (string $id): bool {}

	/**
	 * Cleanup old sessions
	 * @link http://www.php.net/manual/en/sessionhandler.gc.php
	 * @param int $max_lifetime Sessions that have not updated for the last max_lifetime seconds will be removed.
	 * @return int|false Returns the number of deleted sessions on success, or false on failure.
	 * Note this value is returned internally to PHP for processing.
	 */
	public function gc (int $max_lifetime): int|false {}

	/**
	 * Return a new session ID
	 * @link http://www.php.net/manual/en/sessionhandler.create-sid.php
	 * @return string A session ID valid for the default session handler.
	 */
	public function create_sid (): string {}

}

/**
 * Get and/or set the current session name
 * @link http://www.php.net/manual/en/function.session-name.php
 * @param string|null $name [optional] 
 * @return string|false Returns the name of the current session. If name is given
 * and function updates the session name, name of the old session
 * is returned, or false on failure.
 */
function session_name (?string $name = null): string|false {}

/**
 * Get and/or set the current session module
 * @link http://www.php.net/manual/en/function.session-module-name.php
 * @param string|null $module [optional] 
 * @return string|false Returns the name of the current session module, or false on failure.
 */
function session_module_name (?string $module = null): string|false {}

/**
 * Get and/or set the current session save path
 * @link http://www.php.net/manual/en/function.session-save-path.php
 * @param string|null $path [optional] 
 * @return string|false Returns the path of the current directory used for data storage, or false on failure.
 */
function session_save_path (?string $path = null): string|false {}

/**
 * Get and/or set the current session id
 * @link http://www.php.net/manual/en/function.session-id.php
 * @param string|null $id [optional] 
 * @return string|false session_id returns the session id for the current
 * session or the empty string ("") if there is no current
 * session (no current session id exists).
 * On failure, false is returned.
 */
function session_id (?string $id = null): string|false {}

/**
 * Create new session id
 * @link http://www.php.net/manual/en/function.session-create-id.php
 * @param string $prefix [optional] 
 * @return string|false session_create_id returns new collision free
 * session id for the current session. If it is used without active
 * session, it omits collision check.
 * On failure, false is returned.
 */
function session_create_id (string $prefix = '""'): string|false {}

/**
 * Update the current session id with a newly generated one
 * @link http://www.php.net/manual/en/function.session-regenerate-id.php
 * @param bool $delete_old_session [optional] 
 * @return bool Returns true on success or false on failure.
 */
function session_regenerate_id (bool $delete_old_session = false): bool {}

/**
 * Decodes session data from a session encoded string
 * @link http://www.php.net/manual/en/function.session-decode.php
 * @param string $data 
 * @return bool Returns true on success or false on failure.
 */
function session_decode (string $data): bool {}

/**
 * Encodes the current session data as a session encoded string
 * @link http://www.php.net/manual/en/function.session-encode.php
 * @return string|false Returns the contents of the current session encoded, or false on failure.
 */
function session_encode (): string|false {}

/**
 * Destroys all data registered to a session
 * @link http://www.php.net/manual/en/function.session-destroy.php
 * @return bool Returns true on success or false on failure.
 */
function session_destroy (): bool {}

/**
 * Free all session variables
 * @link http://www.php.net/manual/en/function.session-unset.php
 * @return bool Returns true on success or false on failure.
 */
function session_unset (): bool {}

/**
 * Perform session data garbage collection
 * @link http://www.php.net/manual/en/function.session-gc.php
 * @return int|false session_gc returns number of deleted session
 * data for success, false for failure.
 * <p>Old save handlers do not return number of deleted session data, but 
 * only success/failure flag. If this is the case, number of deleted
 * session data became 1 regardless of actually deleted data.</p>
 */
function session_gc (): int|false {}

/**
 * Get the session cookie parameters
 * @link http://www.php.net/manual/en/function.session-get-cookie-params.php
 * @return array Returns an array with the current session cookie information, the array
 * contains the following items:
 * <p>
 * <br>
 * "lifetime" - The
 * lifetime of the cookie in seconds.
 * <br>
 * "path" - The path where
 * information is stored.
 * <br>
 * "domain" - The domain
 * of the cookie.
 * <br>
 * "secure" - The cookie
 * should only be sent over secure connections.
 * <br>
 * "httponly" - The
 * cookie can only be accessed through the HTTP protocol.
 * <br>
 * "samesite" - Controls
 * the cross-domain sending of the cookie.
 * </p>
 */
function session_get_cookie_params (): array {}

/**
 * Write session data and end session
 * @link http://www.php.net/manual/en/function.session-write-close.php
 * @return bool Returns true on success or false on failure.
 */
function session_write_close (): bool {}

/**
 * Discard session array changes and finish session
 * @link http://www.php.net/manual/en/function.session-abort.php
 * @return bool Returns true on success or false on failure.
 */
function session_abort (): bool {}

/**
 * Re-initialize session array with original values
 * @link http://www.php.net/manual/en/function.session-reset.php
 * @return bool Returns true on success or false on failure.
 */
function session_reset (): bool {}

/**
 * Returns the current session status
 * @link http://www.php.net/manual/en/function.session-status.php
 * @return int <br>
 * PHP_SESSION_DISABLED if sessions are disabled.
 * <br>
 * PHP_SESSION_NONE if sessions are enabled, but none exists.
 * <br>
 * PHP_SESSION_ACTIVE if sessions are enabled, and one exists.
 */
function session_status (): int {}

/**
 * Session shutdown function
 * @link http://www.php.net/manual/en/function.session-register-shutdown.php
 * @return void No value is returned.
 */
function session_register_shutdown (): void {}

/**
 * Alias of session_write_close
 * @link http://www.php.net/manual/en/function.session-commit.php
 * @return bool Returns true on success or false on failure.
 */
function session_commit (): bool {}

/**
 * Sets user-level session storage functions
 * @link http://www.php.net/manual/en/function.session-set-save-handler.php
 * @param callable $open 
 * @param callable $close 
 * @param callable $read 
 * @param callable $write 
 * @param callable $destroy 
 * @param callable $gc 
 * @param callable $create_sid [optional] 
 * @param callable $validate_sid [optional] 
 * @param callable $update_timestamp [optional] 
 * @return bool Returns true on success or false on failure.
 */
function session_set_save_handler (callable $open, callable $close, callable $read, callable $write, callable $destroy, callable $gc, callable $create_sid = null, callable $validate_sid = null, callable $update_timestamp = null): bool {}

/**
 * Get and/or set the current cache limiter
 * @link http://www.php.net/manual/en/function.session-cache-limiter.php
 * @param string|null $value [optional] 
 * @return string|false Returns the name of the current cache limiter.
 * On failure to change the value, false is returned.
 */
function session_cache_limiter (?string $value = null): string|false {}

/**
 * Get and/or set current cache expire
 * @link http://www.php.net/manual/en/function.session-cache-expire.php
 * @param int|null $value [optional] 
 * @return int|false Returns the current setting of session.cache_expire.
 * The value returned should be read in minutes, defaults to 180.
 * On failure to change the value, false is returned.
 */
function session_cache_expire (?int $value = null): int|false {}

/**
 * Set the session cookie parameters
 * @link http://www.php.net/manual/en/function.session-set-cookie-params.php
 * @param int $lifetime_or_options 
 * @param string|null $path [optional] 
 * @param string|null $domain [optional] 
 * @param bool|null $secure [optional] 
 * @param bool|null $httponly [optional] 
 * @return bool Returns true on success or false on failure.
 */
function session_set_cookie_params (int $lifetime_or_options, ?string $path = null, ?string $domain = null, ?bool $secure = null, ?bool $httponly = null): bool {}

/**
 * Start new or resume existing session
 * @link http://www.php.net/manual/en/function.session-start.php
 * @param array $options [optional] If provided, this is an associative array of options that will override
 * the currently set
 * session configuration directives.
 * The keys should not include the session. prefix.
 * <p>In addition to the normal set of configuration directives, a
 * read_and_close option may also be provided. If set to
 * true, this will result in the session being closed immediately after
 * being read, thereby avoiding unnecessary locking if the session data
 * won't be changed.</p>
 * @return bool This function returns true if a session was successfully started,
 * otherwise false.
 */
function session_start (array $options = '[]'): bool {}


/**
 * Return value of session_status if sessions are disabled.
 * @link http://www.php.net/manual/en/session.constants.php
 * @var int
 */
define ('PHP_SESSION_DISABLED', 0);

/**
 * Return value of session_status if sessions are enabled,
 * but no session exists.
 * @link http://www.php.net/manual/en/session.constants.php
 * @var int
 */
define ('PHP_SESSION_NONE', 1);

/**
 * Return value of session_status if sessions are enabled,
 * and a session exists.
 * @link http://www.php.net/manual/en/session.constants.php
 * @var int
 */
define ('PHP_SESSION_ACTIVE', 2);

// End of session v.8.2.6
