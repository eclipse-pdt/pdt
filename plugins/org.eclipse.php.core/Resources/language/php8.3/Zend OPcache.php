<?php

// Start of Zend OPcache v.8.2.6

/**
 * Resets the contents of the opcode cache
 * @link http://www.php.net/manual/en/function.opcache-reset.php
 * @return bool Returns true if the opcode cache was reset, or false if the opcode
 * cache is disabled.
 */
function opcache_reset (): bool {}

/**
 * Get status information about the cache
 * @link http://www.php.net/manual/en/function.opcache-get-status.php
 * @param bool $include_scripts [optional] Include script specific state information
 * @return array|false Returns an array of information, optionally containing script specific state information,
 * or false on failure.
 */
function opcache_get_status (bool $include_scripts = true): array|false {}

/**
 * Compiles and caches a PHP script without executing it
 * @link http://www.php.net/manual/en/function.opcache-compile-file.php
 * @param string $filename The path to the PHP script to be compiled.
 * @return bool Returns true if filename was compiled successfully
 * or false on failure.
 */
function opcache_compile_file (string $filename): bool {}

/**
 * Invalidates a cached script
 * @link http://www.php.net/manual/en/function.opcache-invalidate.php
 * @param string $filename The path to the script being invalidated.
 * @param bool $force [optional] If set to true, the script will be invalidated regardless of whether
 * invalidation is necessary.
 * @return bool Returns true if the opcode cache for filename was
 * invalidated or if there was nothing to invalidate, or false if the opcode
 * cache is disabled.
 */
function opcache_invalidate (string $filename, bool $force = false): bool {}

/**
 * Get configuration information about the cache
 * @link http://www.php.net/manual/en/function.opcache-get-configuration.php
 * @return array|false Returns an array of information, including ini, blacklist and version
 */
function opcache_get_configuration (): array|false {}

/**
 * Tells whether a script is cached in OPCache
 * @link http://www.php.net/manual/en/function.opcache-is-script-cached.php
 * @param string $filename The path to the PHP script to be checked.
 * @return bool Returns true if filename is cached in OPCache,
 * false otherwise.
 */
function opcache_is_script_cached (string $filename): bool {}

// End of Zend OPcache v.8.2.6
