<?php

// Start of session v.7.1.1

/**
 * SessionHandlerInterface is an
 * interface which defines a
 * prototype for creating a custom session handler. In order to pass a custom
 * session handler to session_set_save_handler using its
 * OOP invocation, the class must implement this interface.
 * <p>Please note the callback methods of this class are designed to be called internally by
 * PHP and are not meant to be called from user-space code.</p>
 * @link http://www.php.net/manual/en/class.sessionhandlerinterface.php
 */
interface SessionHandlerInterface  {

	/**
	 * Initialize session
	 * @link http://www.php.net/manual/en/sessionhandlerinterface.open.php
	 * @param string $save_path The path where to store/retrieve the session.
	 * @param string $session_name The session name.
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	abstract public function open (string $save_path, string $session_name);

	/**
	 * Close the session
	 * @link http://www.php.net/manual/en/sessionhandlerinterface.close.php
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	abstract public function close ();

	/**
	 * Read session data
	 * @link http://www.php.net/manual/en/sessionhandlerinterface.read.php
	 * @param string $session_id The session id.
	 * @return string an encoded string of the read data. If nothing was read, it must return an empty string. Note this value is returned internally to PHP for processing.
	 */
	abstract public function read (string $session_id);

	/**
	 * Write session data
	 * @link http://www.php.net/manual/en/sessionhandlerinterface.write.php
	 * @param string $session_id The session id.
	 * @param string $session_data The encoded session data. This data is the result of the PHP internally encoding the $_SESSION superglobal to a serialized
	 * string and passing it as this parameter. Please note sessions use an alternative serialization method.
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	abstract public function write (string $session_id, string $session_data);

	/**
	 * Destroy a session
	 * @link http://www.php.net/manual/en/sessionhandlerinterface.destroy.php
	 * @param string $session_id The session ID being destroyed.
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	abstract public function destroy (string $session_id);

	/**
	 * Cleanup old sessions
	 * @link http://www.php.net/manual/en/sessionhandlerinterface.gc.php
	 * @param int $maxlifetime Sessions that have not updated for the last maxlifetime seconds will be removed.
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	abstract public function gc (int $maxlifetime);

}

interface SessionIdInterface  {

	abstract public function create_sid ();

}

interface SessionUpdateTimestampHandlerInterface  {

	/**
	 * @param $key
	 */
	abstract public function validateId ($key);

	/**
	 * @param $key
	 * @param $val
	 */
	abstract public function updateTimestamp ($key, $val);

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
 * <p>Please note the callback methods of this class are designed to be called internally by
 * PHP and are not meant to be called from user-space code. The return values are equally processed internally
 * by PHP. For more information on the session workflow, please refer session_set_save_handler.</p>
 * @link http://www.php.net/manual/en/class.sessionhandler.php
 */
class SessionHandler implements SessionHandlerInterface, SessionIdInterface {

	/**
	 * Initialize session
	 * @link http://www.php.net/manual/en/sessionhandler.open.php
	 * @param string $save_path The path where to store/retrieve the session.
	 * @param string $session_name The session name.
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	public function open (string $save_path, string $session_name) {}

	/**
	 * Close the session
	 * @link http://www.php.net/manual/en/sessionhandler.close.php
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	public function close () {}

	/**
	 * Read session data
	 * @link http://www.php.net/manual/en/sessionhandler.read.php
	 * @param string $session_id The session id to read data for.
	 * @return string an encoded string of the read data. If nothing was read, it must return an empty string. Note this value is returned internally to PHP for processing.
	 */
	public function read (string $session_id) {}

	/**
	 * Write session data
	 * @link http://www.php.net/manual/en/sessionhandler.write.php
	 * @param string $session_id The session id.
	 * @param string $session_data The encoded session data. This data is the result of the PHP internally encoding the $_SESSION superglobal to a serialized
	 * string and passing it as this parameter. Please note sessions use an alternative serialization method.
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	public function write (string $session_id, string $session_data) {}

	/**
	 * Destroy a session
	 * @link http://www.php.net/manual/en/sessionhandler.destroy.php
	 * @param string $session_id The session ID being destroyed.
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	public function destroy (string $session_id) {}

	/**
	 * Cleanup old sessions
	 * @link http://www.php.net/manual/en/sessionhandler.gc.php
	 * @param int $maxlifetime Sessions that have not updated for the last maxlifetime seconds will be removed.
	 * @return bool The return value (usually true on success, false on failure). Note this value is returned internally to PHP for processing.
	 */
	public function gc (int $maxlifetime) {}

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
 * @param string $name [optional] <p>
 * The session name references the name of the session, which is 
 * used in cookies and URLs (e.g. PHPSESSID). It
 * should contain only alphanumeric characters; it should be short and
 * descriptive (i.e. for users with enabled cookie warnings).
 * If name is specified, the name of the current
 * session is changed to its value.
 * </p>
 * <p>
 * <p>
 * The session name can't consist of digits only, at least one letter
 * must be present. Otherwise a new session id is generated every time.
 * </p>
 * </p>
 * @return string the name of the current session. If name is given
 * and function updates the session name, name of the old session
 * is returned.
 */
function session_name (string $name = null) {}

/**
 * Get and/or set the current session module
 * @link http://www.php.net/manual/en/function.session-module-name.php
 * @param string $module [optional] If module is specified, that module will be
 * used instead.
 * @return string the name of the current session module.
 */
function session_module_name (string $module = null) {}

/**
 * Get and/or set the current session save path
 * @link http://www.php.net/manual/en/function.session-save-path.php
 * @param string $path [optional] <p>
 * Session data path. If specified, the path to which data is saved will
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
 * @return string the path of the current directory used for data storage.
 */
function session_save_path (string $path = null) {}

/**
 * Get and/or set the current session id
 * @link http://www.php.net/manual/en/function.session-id.php
 * @param string $id [optional] <p>
 * If id is specified, it will replace the current
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
 * @return string session_id returns the session id for the current
 * session or the empty string ("") if there is no current
 * session (no current session id exists).
 */
function session_id (string $id = null) {}

/**
 * Create new session id
 * @link http://www.php.net/manual/en/function.session-create-id.php
 * @param string $prefix [optional] If prefix is specified, new session id
 * is prefixed by prefix. Not all
 * characters are allowed within the session id. Characters in
 * the range a-z A-Z 0-9 , (comma) and -
 * (minus) are allowed.
 * @return string session_create_id returns new collision free
 * session id for the current session. If it is used without active
 * session, it omits collision check.
 */
function session_create_id (string $prefix = null) {}

/**
 * Update the current session id with a newly generated one
 * @link http://www.php.net/manual/en/function.session-regenerate-id.php
 * @param bool $delete_old_session [optional] Whether to delete the old associated session file or not.
 * You should not delete old session if you need to avoid
 * races caused by deletion or detect/avoid session hijack
 * attacks.
 * @return bool true on success or false on failure
 */
function session_regenerate_id (bool $delete_old_session = null) {}

/**
 * Decodes session data from a session encoded string
 * @link http://www.php.net/manual/en/function.session-decode.php
 * @param string $data The encoded data to be stored.
 * @return bool true on success or false on failure
 */
function session_decode (string $data) {}

/**
 * Encodes the current session data as a session encoded string
 * @link http://www.php.net/manual/en/function.session-encode.php
 * @return string the contents of the current session encoded.
 */
function session_encode () {}

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
function session_start (array $options = null) {}

/**
 * Destroys all data registered to a session
 * @link http://www.php.net/manual/en/function.session-destroy.php
 * @return bool true on success or false on failure
 */
function session_destroy () {}

/**
 * Free all session variables
 * @link http://www.php.net/manual/en/function.session-unset.php
 * @return void 
 */
function session_unset () {}

/**
 * Perform session data garbage collection
 * @link http://www.php.net/manual/en/function.session-gc.php
 * @return int session_gc returns number of deleted session
 * data for success, false for failure.
 * <p>
 * Old save handlers do not return number of deleted session data, but 
 * only success/failure flag. If this is the case, number of deleted
 * session data became 1 regardless of actually deleted data.
 * </p>
 */
function session_gc () {}

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
 * @return bool true on success or false on failure
 */
function session_set_save_handler (callable $open, callable $close, callable $read, callable $write, callable $destroy, callable $gc, callable $create_sid = null, callable $validate_sid = null, callable $update_timestamp = null) {}

/**
 * Get and/or set the current cache limiter
 * @link http://www.php.net/manual/en/function.session-cache-limiter.php
 * @param string $cache_limiter [optional] <p>
 * If cache_limiter is specified, the name of the
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
 * @return string the name of the current cache limiter.
 */
function session_cache_limiter (string $cache_limiter = null) {}

/**
 * Return current cache expire
 * @link http://www.php.net/manual/en/function.session-cache-expire.php
 * @param string $new_cache_expire [optional] <p>
 * If new_cache_expire is given, the current cache
 * expire is replaced with new_cache_expire.
 * </p>
 * <p>
 * Setting new_cache_expire is of value only, if
 * session.cache_limiter is set to a value
 * different from nocache.
 * </p>
 * @return int the current setting of session.cache_expire.
 * The value returned should be read in minutes, defaults to 180.
 */
function session_cache_expire (string $new_cache_expire = null) {}

/**
 * Set the session cookie parameters
 * @link http://www.php.net/manual/en/function.session-set-cookie-params.php
 * @param int $lifetime Lifetime of the
 * session cookie, defined in seconds.
 * @param string $path [optional] Path on the domain where
 * the cookie will work. Use a single slash ('/') for all paths on the
 * domain.
 * @param string $domain [optional] Cookie domain, for
 * example 'www.php.net'. To make cookies visible on all subdomains then
 * the domain must be prefixed with a dot like '.php.net'.
 * @param bool $secure [optional] If true cookie will only be sent over
 * secure connections.
 * @param bool $httponly [optional] If set to true then PHP will attempt to send the
 * httponly
 * flag when setting the session cookie.
 * @return void 
 */
function session_set_cookie_params (int $lifetime, string $path = null, string $domain = null, bool $secure = null, bool $httponly = null) {}

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
 * </p>
 */
function session_get_cookie_params () {}

/**
 * Write session data and end session
 * @link http://www.php.net/manual/en/function.session-write-close.php
 * @return void 
 */
function session_write_close () {}

/**
 * Discard session array changes and finish session
 * @link http://www.php.net/manual/en/function.session-abort.php
 * @return void 
 */
function session_abort () {}

/**
 * Re-initialize session array with original values
 * @link http://www.php.net/manual/en/function.session-reset.php
 * @return void 
 */
function session_reset () {}

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
function session_status () {}

/**
 * Session shutdown function
 * @link http://www.php.net/manual/en/function.session-register-shutdown.php
 * @return void 
 */
function session_register_shutdown () {}

/**
 * Alias: session_write_close
 * @link http://www.php.net/manual/en/function.session-commit.php
 */
function session_commit () {}


/**
 * Since PHP 5.4.0. Return value of session_status if sessions are disabled.
 * @link http://www.php.net/manual/en/session.constants.php
 */
define ('PHP_SESSION_DISABLED', 0);

/**
 * Since PHP 5.4.0. Return value of session_status if sessions are enabled,
 * but no session exists.
 * @link http://www.php.net/manual/en/session.constants.php
 */
define ('PHP_SESSION_NONE', 1);

/**
 * Since PHP 5.4.0. Return value of session_status if sessions are enabled,
 * and a session exists.
 * @link http://www.php.net/manual/en/session.constants.php
 */
define ('PHP_SESSION_ACTIVE', 2);

// End of session v.7.1.1
