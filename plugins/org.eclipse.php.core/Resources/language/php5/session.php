<?php

// Start of session v.

/**
 * Get and/or set the current session name
 * @link http://php.net/manual/en/function.session-name.php
 * @param name string[optional]
 * @return string the name of the current session.
 */
function session_name ($name = null) {}

/**
 * Get and/or set the current session module
 * @link http://php.net/manual/en/function.session-module-name.php
 * @param module string[optional]
 * @return string the name of the current session module.
 */
function session_module_name ($module = null) {}

/**
 * Get and/or set the current session save path
 * @link http://php.net/manual/en/function.session-save-path.php
 * @param path string[optional]
 * @return string the path of the current directory used for data storage.
 */
function session_save_path ($path = null) {}

/**
 * Get and/or set the current session id
 * @link http://php.net/manual/en/function.session-id.php
 * @param id string[optional]
 * @return string 
 */
function session_id ($id = null) {}

/**
 * Update the current session id with a newly generated one
 * @link http://php.net/manual/en/function.session-regenerate-id.php
 * @param delete_old_session bool[optional]
 * @return bool 
 */
function session_regenerate_id ($delete_old_session = null) {}

/**
 * Decodes session data from a string
 * @link http://php.net/manual/en/function.session-decode.php
 * @param data string
 * @return bool 
 */
function session_decode ($data) {}

/**
 * Register one or more global variables with the current session
 * @link http://php.net/manual/en/function.session-register.php
 * @param name mixed
 * @param _ mixed[optional]
 * @return bool 
 */
function session_register ($name, $_ = null) {}

/**
 * Unregister a global variable from the current session
 * @link http://php.net/manual/en/function.session-unregister.php
 * @param name string
 * @return bool 
 */
function session_unregister ($name) {}

/**
 * Find out whether a global variable is registered in a session
 * @link http://php.net/manual/en/function.session-is-registered.php
 * @param name string
 * @return bool 
 */
function session_is_registered ($name) {}

/**
 * Encodes the current session data as a string
 * @link http://php.net/manual/en/function.session-encode.php
 * @return string the contents of the current session encoded.
 */
function session_encode () {}

/**
 * Initialize session data
 * @link http://php.net/manual/en/function.session-start.php
 * @return bool 
 */
function session_start () {}

/**
 * Destroys all data registered to a session
 * @link http://php.net/manual/en/function.session-destroy.php
 * @return bool 
 */
function session_destroy () {}

/**
 * Free all session variables
 * @link http://php.net/manual/en/function.session-unset.php
 * @return void 
 */
function session_unset () {}

/**
 * Sets user-level session storage functions
 * @link http://php.net/manual/en/function.session-set-save-handler.php
 * @param open callback
 * @param close callback
 * @param read callback
 * @param write callback
 * @param destroy callback
 * @param gc callback
 * @return bool 
 */
function session_set_save_handler ($open, $close, $read, $write, $destroy, $gc) {}

/**
 * Get and/or set the current cache limiter
 * @link http://php.net/manual/en/function.session-cache-limiter.php
 * @param cache_limiter string[optional]
 * @return string the name of the current cache limiter.
 */
function session_cache_limiter ($cache_limiter = null) {}

/**
 * Return current cache expire
 * @link http://php.net/manual/en/function.session-cache-expire.php
 * @param new_cache_expire int[optional]
 * @return int the current setting of session.cache_expire.
 */
function session_cache_expire ($new_cache_expire = null) {}

/**
 * Set the session cookie parameters
 * @link http://php.net/manual/en/function.session-set-cookie-params.php
 * @param lifetime int
 * @param path string[optional]
 * @param domain string[optional]
 * @param secure bool[optional]
 * @param httponly bool[optional]
 * @return void 
 */
function session_set_cookie_params ($lifetime, $path = null, $domain = null, $secure = null, $httponly = null) {}

/**
 * Get the session cookie parameters
 * @link http://php.net/manual/en/function.session-get-cookie-params.php
 * @return array an array with the current session cookie information, the array
 */
function session_get_cookie_params () {}

/**
 * Write session data and end session
 * @link http://php.net/manual/en/function.session-write-close.php
 * @return void 
 */
function session_write_close () {}

/**
 * &Alias; <function>session_write_close</function>
 * @link http://php.net/manual/en/function.session-commit.php
 */
function session_commit () {}

// End of session v.
?>
