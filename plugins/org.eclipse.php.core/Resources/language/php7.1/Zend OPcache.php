<?php

// Start of Zend OPcache v.7.1.1

/**
 * Resets the contents of the opcode cache
 * @link http://www.php.net/manual/en/function.opcache-reset.php
 * @return boolean true if the opcode cache was reset, or false if the opcode
 * cache is disabled.
 */
function opcache_reset () {}

/**
 * Invalidates a cached script
 * @link http://www.php.net/manual/en/function.opcache-invalidate.php
 * @param string $script The path to the script being invalidated.
 * @param boolean $force [optional] If set to true, the script will be invalidated regardless of whether
 * invalidation is necessary.
 * @return boolean true if the opcode cache for script was
 * invalidated or if there was nothing to invalidate, or false if the opcode
 * cache is disabled.
 */
function opcache_invalidate (string $script, bool $force = null) {}

/**
 * Compiles and caches a PHP script without executing it
 * @link http://www.php.net/manual/en/function.opcache-compile-file.php
 * @param string $file The path to the PHP script to be compiled.
 * @return boolean true if file was compiled successfully
 * or false on failure.
 */
function opcache_compile_file (string $file) {}

/**
 * Tells whether a script is cached in OPCache
 * @link http://www.php.net/manual/en/function.opcache-is-script-cached.php
 * @param string $file The path to the PHP script to be checked.
 * @return boolean true if file is cached in OPCache,
 * false otherwise.
 */
function opcache_is_script_cached (string $file) {}

/**
 * Get configuration information about the cache
 * @link http://www.php.net/manual/en/function.opcache-get-configuration.php
 * @return array an array of information, including ini, blacklist and version
 */
function opcache_get_configuration () {}

/**
 * Get status information about the cache
 * @link http://www.php.net/manual/en/function.opcache-get-status.php
 * @param boolean $get_scripts [optional] Include script specific state information
 * @return array an array of information, optionally containing script specific state information
 */
function opcache_get_status (bool $get_scripts = null) {}

// End of Zend OPcache v.7.1.1
