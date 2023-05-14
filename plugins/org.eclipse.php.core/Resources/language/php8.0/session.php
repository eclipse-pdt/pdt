<?php

// Start of session v.8.0.28

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
	abstract public function open (string $path, string $name)

	/**
	 * Close the session
	 * @link http://www.php.net/manual/en/sessionhandlerinterface.close.php
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	abstract public function close ()

	/**
	 * Read session data
	 * @link http://www.php.net/manual/en/sessionhandlerinterface.read.php
	 * @param string $id The session id.
	 * @return mixed an encoded string of the read data. If nothing was read, it must return false. Note this value is returned internally to PHP for processing.
	 */
	abstract public function read (string $id)

	/**
	 * Write session data
	 * @link http://www.php.net/manual/en/sessionhandlerinterface.write.php
	 * @param string $id The session id.
	 * @param string $data The encoded session data. This data is the result of the PHP internally encoding the $_SESSION superglobal to a serialized
	 * string and passing it as this parameter. Please note sessions use an alternative serialization method.
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	abstract public function write (string $id, string $data)

	/**
	 * Destroy a session
	 * @link http://www.php.net/manual/en/sessionhandlerinterface.destroy.php
	 * @param string $id The session ID being destroyed.
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	abstract public function destroy (string $id)

	/**
	 * Cleanup old sessions
	 * @link http://www.php.net/manual/en/sessionhandlerinterface.gc.php
	 * @param int $max_lifetime Sessions that have not updated for the last max_lifetime seconds will be removed.
	 * @return mixed the number of deleted sessions on success, or false on failure.
	 * Note this value is returned internally to PHP for processing.
	 */
	abstract public function gc (int $max_lifetime)

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
	abstract public function create_sid ()

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
	 * @return bool true for valid ID, false otherwise.
	 * Note that this value is returned internally to PHP for processing.
	 */
	abstract public function validateId (string $id)

	/**
	 * Update timestamp
	 * @link http://www.php.net/manual/en/sessionupdatetimestamphandlerinterface.updatetimestamp.php
	 * @param string $id The session ID.
	 * @param string $data The session data.
	 * @return bool true if the timestamp was updated, false otherwise.
	 * Note that this value is returned internally to PHP for processing.
	 */
	abstract public function updateTimestamp (string $id, string $data)

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
	public function open (string $path, string $name) {}

	/**
	 * Close the session
	 * @link http://www.php.net/manual/en/sessionhandler.close.php
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	public function close () {}

	/**
	 * Read session data
	 * @link http://www.php.net/manual/en/sessionhandler.read.php
	 * @param string $id The session id to read data for.
	 * @return mixed an encoded string of the read data. If nothing was read, it must return false. Note this value is returned internally to PHP for processing.
	 */
	public function read (string $id) {}

	/**
	 * Write session data
	 * @link http://www.php.net/manual/en/sessionhandler.write.php
	 * @param string $id The session id.
	 * @param string $data The encoded session data. This data is the result of the PHP internally encoding the $_SESSION superglobal to a serialized
	 * string and passing it as this parameter. Please note sessions use an alternative serialization method.
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	public function write (string $id, string $data) {}

	/**
	 * Destroy a session
	 * @link http://www.php.net/manual/en/sessionhandler.destroy.php
	 * @param string $id The session ID being destroyed.
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	public function destroy (string $id) {}

	/**
	 * Cleanup old sessions
	 * @link http://www.php.net/manual/en/sessionhandler.gc.php
	 * @param int $max_lifetime Sessions that have not updated for the last max_lifetime seconds will be removed.
	 * @return mixed the number of deleted sessions on success, or false on failure.
	 * Note this value is returned internally to PHP for processing.
	 */
	public function gc (int $max_lifetime) {}

	/**
	 * Return a new session ID
	 * @link http://www.php.net/manual/en/sessionhandler.create-sid.php
	 * @return string A session ID valid for the default session handler.
	 */
	public function create_sid () {}

}

/**
 * Get and/or set the current session name
 * @link http://www.php.net/manual/en/function.session-name.php
 * @param mixed $name [optional] <p>
 * The session name references the name of the session, which is 
 * used in cookies and URLs (e.g. PHPSESSID). It
 * should contain only alphanumeric characters; it should be short and
 * descriptive (i.e. for users with enabled cookie warnings).
 * If name is specified and not null, the name of the current
 * session is changed to its value.
 * </p>
 * <p>
 * <p>
 * The session name can't consist of digits only, at least one letter
 * must be present. Otherwise a new session id is generated every time.
 * </p>
 * </p>
 * @return mixed the name of the current session. If name is given
 * and function updates the session name, name of the old session
 * is returned, or false on failure.
 */
function session_name ($name = null): string|false {}

/**
 * Get and/or set the current session module
 * @link http://www.php.net/manual/en/function.session-module-name.php
 * @param mixed $module [optional] If module is specified and not null, that module will be
 * used instead.
 * Passing "user" to this parameter is forbidden. Instead
 * session_set_save_handler has to be called to set a user
 * defined session handler.
 * @return mixed the name of the current session module, or false on failure.
 */
function session_module_name ($module = null): string|false {}

/**
 * Get and/or set the current session save path
 * @link http://www.php.net/manual/en/function.session-save-path.php
 * @param mixed $path [optional] <p>
 * Session data path. If specified and not null, the path to which data is saved will
 * be changed. session_save_path needs to be called
 * before session_start for that purpose.
 * </p>
 * <p>
 * <p>
 * On some operating systems, you may want to specify a path on a
 * filesystem that handles lots of small files efficiently. For example,
 * on Linux, reiserfs may provide better performance than ext2fs.
 * </p>
 * </p>
 * @return mixed the path of the current directory used for data storage, or false on failure.
 */
function session_save_path ($path = null): string|false {}

/**
 * Get and/or set the current session id
 * @link http://www.php.net/manual/en/function.session-id.php
 * @param mixed $id [optional] <p>
 * If id is specified and not null, it will replace the current
 * session id. session_id needs to be called before
 * session_start for that purpose. Depending on the
 * session handler, not all characters are allowed within the session id.
 * For example, the file session handler only allows characters in the
 * range a-z A-Z 0-9 , (comma) and - (minus)!
 * </p>
 * When using session cookies, specifying an id
 * for session_id will always send a new cookie
 * when session_start is called, regardless if the
 * current session id is identical to the one being set.
 * @return mixed session_id returns the session id for the current
 * session or the empty string ("") if there is no current
 * session (no current session id exists).
 * On failure, false is returned.
 */
function session_id ($id = null): string|false {}

/**
 * Create new session id
 * @link http://www.php.net/manual/en/function.session-create-id.php
 * @param string $prefix [optional] If prefix is specified, new session id
 * is prefixed by prefix. Not all
 * characters are allowed within the session id. Characters in
 * the range a-z A-Z 0-9 , (comma) and -
 * (minus) are allowed.
 * @return mixed session_create_id returns new collision free
 * session id for the current session. If it is used without active
 * session, it omits collision check.
 * On failure, false is returned.
 */
function session_create_id (string $prefix = null): string|false {}

/**
 * Update the current session id with a newly generated one
 * @link http://www.php.net/manual/en/function.session-regenerate-id.php
 * @param bool $delete_old_session [optional] Whether to delete the old associated session file or not.
 * You should not delete old session if you need to avoid
 * races caused by deletion or detect/avoid session hijack
 * attacks.
 * @return bool true on success or false on failure
 */
function session_regenerate_id (bool $delete_old_session = null): bool {}

/**
 * Decodes session data from a session encoded string
 * @link http://www.php.net/manual/en/function.session-decode.php
 * @param string $data The encoded data to be stored.
 * @return bool true on success or false on failure
 */
function session_decode (string $data): bool {}

/**
 * Encodes the current session data as a session encoded string
 * @link http://www.php.net/manual/en/function.session-encode.php
 * @return mixed the contents of the current session encoded, or false on failure.
 */
function session_encode (): string|false {}

/**
 * Destroys all data registered to a session
 * @link http://www.php.net/manual/en/function.session-destroy.php
 * @return bool true on success or false on failure
 */
function session_destroy (): bool {}

/**
 * Free all session variables
 * @link http://www.php.net/manual/en/function.session-unset.php
 * @return bool true on success or false on failure
 */
function session_unset (): bool {}

/**
 * Perform session data garbage collection
 * @link http://www.php.net/manual/en/function.session-gc.php
 * @return mixed session_gc returns number of deleted session
 * data for success, false for failure.
 * <p>
 * Old save handlers do not return number of deleted session data, but 
 * only success/failure flag. If this is the case, number of deleted
 * session data became 1 regardless of actually deleted data.
 * </p>
 */
function session_gc (): int|false {}

/**
 * Get the session cookie parameters
 * @link http://www.php.net/manual/en/function.session-get-cookie-params.php
 * @return array an array with the current session cookie information, the array
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
 * @return bool true on success or false on failure
 */
function session_write_close (): bool {}

/**
 * Discard session array changes and finish session
 * @link http://www.php.net/manual/en/function.session-abort.php
 * @return bool true on success or false on failure
 */
function session_abort (): bool {}

/**
 * Re-initialize session array with original values
 * @link http://www.php.net/manual/en/function.session-reset.php
 * @return bool true on success or false on failure
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
 * @return void 
 */
function session_register_shutdown (): void {}

/**
 * Alias: session_write_close
 * @link http://www.php.net/manual/en/function.session-commit.php
 */
function session_commit (): bool {}

/**
 * Sets user-level session storage functions
 * @link http://www.php.net/manual/en/function.session-set-save-handler.php
 * @param callable $open <p>
 * A callable with the following signature:
 * boolopen
 * stringsavePath
 * stringsessionName
 * </p>
 * <p>
 * The open callback works like a constructor in classes and is
 * executed when the session is being opened. It is the first callback
 * function executed when the session is started automatically or
 * manually with session_start.
 * Return value is true for success, false for failure.
 * </p>
 * @param callable $close <p>
 * A callable with the following signature:
 * boolclose
 * </p>
 * <p>
 * The close callback works like a destructor in classes and is
 * executed after the session write callback has been called. It is also invoked when
 * session_write_close is called.
 * Return value should be true for success, false for failure.
 * </p>
 * @param callable $read <p>
 * A callable with the following signature:
 * stringread
 * stringsessionId
 * </p>
 * <p>
 * The read callback must always return a session encoded (serialized)
 * string, or an empty string if there is no data to read.
 * </p>
 * <p>
 * This callback is called internally by PHP when the session starts or
 * when session_start is called. Before this callback is invoked
 * PHP will invoke the open callback.
 * </p>
 * <p>
 * The value this callback returns must be in exactly the same serialized format that was originally
 * passed for storage to the write callback. The value returned will be
 * unserialized automatically by PHP and used to populate the $_SESSION superglobal.
 * While the data looks similar to serialize please note it is a different format
 * which is specified in the session.serialize_handler ini setting.
 * </p>
 * @param callable $write <p>
 * A callable with the following signature:
 * boolwrite
 * stringsessionId
 * stringdata
 * </p>
 * <p>
 * The write callback is called when the session needs to be saved and closed. This
 * callback receives the current session ID a serialized version the $_SESSION superglobal. The serialization
 * method used internally by PHP is specified in the session.serialize_handler ini setting.
 * </p>
 * <p>
 * The serialized session data passed to this callback should be stored against the passed session ID. When retrieving
 * this data, the read callback must return the exact value that was originally passed to
 * the write callback.
 * </p>
 * <p>
 * This callback is invoked when PHP shuts down or explicitly when session_write_close
 * is called. Note that after executing this function PHP will internally execute the close callback.
 * <p>
 * The "write" handler is not executed until after the output stream is
 * closed. Thus, output from debugging statements in the "write"
 * handler will never be seen in the browser. If debugging output is
 * necessary, it is suggested that the debug output be written to a
 * file instead.
 * </p>
 * </p>
 * @param callable $destroy <p>
 * A callable with the following signature:
 * booldestroy
 * stringsessionId
 * </p>
 * <p>
 * This callback is executed when a session is destroyed with session_destroy or with
 * session_regenerate_id with the destroy parameter set to true.
 * Return value should be true for success, false for failure.
 * </p>
 * @param callable $gc <p>
 * A callable with the following signature:
 * boolgc
 * intlifetime
 * </p>
 * <p>
 * The garbage collector callback is invoked internally by PHP periodically in order to
 * purge old session data. The frequency is controlled by
 * session.gc_probability and session.gc_divisor.
 * The value of lifetime which is passed to this callback can be set in session.gc_maxlifetime.
 * Return value should be true for success, false for failure.
 * </p>
 * @param callable $create_sid [optional] <p>
 * A callable with the following signature:
 * stringcreate_sid
 * </p>
 * <p>
 * This callback is executed when a new session ID is required. No
 * parameters are provided, and the return value should be a string that
 * is a valid session ID for your handler.
 * </p>
 * @param callable $validate_sid [optional] <p>
 * A callable with the following signature:
 * boolvalidate_sid
 * stringkey
 * </p>
 * <p>
 * This callback is executed when a session is to be started, a session ID is supplied
 * and session.use_strict_mode is enabled.
 * The key is the session ID to validate.
 * A session ID is valid, if a session with that ID already exists.
 * The return value should be true for success, false for failure.
 * </p>
 * @param callable $update_timestamp [optional] <p>
 * A callable with the following signature:
 * boolupdate_timestamp
 * stringkey
 * stringval
 * </p>
 * <p>
 * This callback is executed when a session is updated.
 * key is the session ID, val is the session data.
 * The return value should be true for success, false for failure.
 * </p>
 * @return bool true on success or false on failure
 */
function session_set_save_handler (callable $open, callable $close, callable $read, callable $write, callable $destroy, callable $gc, callable $create_sid = null, callable $validate_sid = null, callable $update_timestamp = null): bool {}

/**
 * Get and/or set the current cache limiter
 * @link http://www.php.net/manual/en/function.session-cache-limiter.php
 * @param mixed $value [optional] <p>
 * If value is specified and not null, the name of the
 * current cache limiter is changed to the new value.
 * </p>
 * <table>
 * Possible values
 * <table>
 * <tr valign="top">
 * <td>Value</td>
 * <td>Headers sent</td>
 * </tr>
 * <tr valign="top">
 * <td>public</td>
 * <td>
 * <pre>
 * Expires: (sometime in the future, according session.cache_expire)
 * Cache-Control: public, max-age=(sometime in the future, according to session.cache_expire)
 * Last-Modified: (the timestamp of when the session was last saved)
 * </pre>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>private_no_expire</td>
 * <td>
 * <pre>
 * Cache-Control: private, max-age=(session.cache_expire in the future), pre-check=(session.cache_expire in the future)
 * Last-Modified: (the timestamp of when the session was last saved)
 * </pre>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>private</td>
 * <td>
 * <pre>
 * Expires: Thu, 19 Nov 1981 08:52:00 GMT
 * Cache-Control: private, max-age=(session.cache_expire in the future), pre-check=(session.cache_expire in the future)
 * Last-Modified: (the timestamp of when the session was last saved)
 * </pre>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>nocache</td>
 * <td>
 * <pre>
 * Expires: Thu, 19 Nov 1981 08:52:00 GMT
 * Cache-Control: no-store, no-cache, must-revalidate, post-check=0, pre-check=0
 * Pragma: no-cache
 * </pre>
 * </td>
 * </tr>
 * </table>
 * </table>
 * @return mixed the name of the current cache limiter.
 * On failure to change the value, false is returned.
 */
function session_cache_limiter ($value = null): string|false {}

/**
 * Get and/or set current cache expire
 * @link http://www.php.net/manual/en/function.session-cache-expire.php
 * @param mixed $value [optional] <p>
 * If value is given and not null, the current cache
 * expire is replaced with value.
 * </p>
 * <p>
 * Setting value is of value only, if
 * session.cache_limiter is set to a value
 * different from nocache.
 * </p>
 * @return mixed the current setting of session.cache_expire.
 * The value returned should be read in minutes, defaults to 180.
 * On failure to change the value, false is returned.
 */
function session_cache_expire ($value = null): int|false {}

/**
 * Set the session cookie parameters
 * @link http://www.php.net/manual/en/function.session-set-cookie-params.php
 * @param int $lifetime_or_options <p>
 * When using the first signature, lifetime of the
 * session cookie, defined in seconds.
 * </p>
 * <p>
 * When using the second signature,
 * an associative array which may have any of the keys
 * lifetime, path, domain,
 * secure, httponly and samesite.
 * The values have the same meaning as described for the parameters with the
 * same name. The value of the samesite element should be
 * either Lax or Strict.
 * If any of the allowed options are not given, their default values are the
 * same as the default values of the explicit parameters. If the
 * samesite element is omitted, no SameSite cookie
 * attribute is set.
 * </p>
 * @param mixed $path [optional] Path on the domain where
 * the cookie will work. Use a single slash ('/') for all paths on the
 * domain.
 * @param mixed $domain [optional] Cookie domain, for
 * example 'www.php.net'. To make cookies visible on all subdomains then
 * the domain must be prefixed with a dot like '.php.net'.
 * @param mixed $secure [optional] If true cookie will only be sent over
 * secure connections.
 * @param mixed $httponly [optional] If set to true then PHP will attempt to send the
 * httponly
 * flag when setting the session cookie.
 * @return bool true on success or false on failure
 */
function session_set_cookie_params (int $lifetime_or_options, $path = null, $domain = null, $secure = null, $httponly = null): bool {}

/**
 * Start new or resume existing session
 * @link http://www.php.net/manual/en/function.session-start.php
 * @param array $options [optional] <p>
 * If provided, this is an associative array of options that will override
 * the currently set
 * session configuration directives.
 * The keys should not include the session. prefix.
 * </p>
 * <p>
 * In addition to the normal set of configuration directives, a
 * read_and_close option may also be provided. If set to
 * true, this will result in the session being closed immediately after
 * being read, thereby avoiding unnecessary locking if the session data
 * won't be changed.
 * </p>
 * @return bool This function returns true if a session was successfully started,
 * otherwise false.
 */
function session_start (array $options = null): bool {}


/**
 * Return value of session_status if sessions are disabled.
 * @link http://www.php.net/manual/en/session.constants.php
 */
define ('PHP_SESSION_DISABLED', 0);

/**
 * Return value of session_status if sessions are enabled,
 * but no session exists.
 * @link http://www.php.net/manual/en/session.constants.php
 */
define ('PHP_SESSION_NONE', 1);

/**
 * Return value of session_status if sessions are enabled,
 * and a session exists.
 * @link http://www.php.net/manual/en/session.constants.php
 */
define ('PHP_SESSION_ACTIVE', 2);

// End of session v.8.0.28
