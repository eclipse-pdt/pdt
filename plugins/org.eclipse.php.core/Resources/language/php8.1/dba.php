<?php

// Start of dba v.8.1.19

/**
 * Open database persistently
 * @link http://www.php.net/manual/en/function.dba-popen.php
 * @param string $path 
 * @param string $mode 
 * @param string|null $handler [optional] 
 * @param int $permission [optional] 
 * @param int $map_size [optional] 
 * @param int|null $flags [optional] 
 * @return resource|bool Returns a positive handle on success or false on failure.
 */
function dba_popen (string $path, string $mode, ?string $handler = null, int $permission = 0644, int $map_size = null, ?int $flags = null): resource|bool {}

/**
 * Open database
 * @link http://www.php.net/manual/en/function.dba-open.php
 * @param string $path 
 * @param string $mode 
 * @param string|null $handler [optional] 
 * @param int $permission [optional] 
 * @param int $map_size [optional] 
 * @param int|null $flags [optional] 
 * @return resource|bool Returns a positive handle on success or false on failure.
 */
function dba_open (string $path, string $mode, ?string $handler = null, int $permission = 0644, int $map_size = null, ?int $flags = null): resource|bool {}

/**
 * Close a DBA database
 * @link http://www.php.net/manual/en/function.dba-close.php
 * @param resource $dba 
 * @return void No value is returned.
 */
function dba_close (resource $dba): void {}

/**
 * Check whether key exists
 * @link http://www.php.net/manual/en/function.dba-exists.php
 * @param string|array $key 
 * @param resource $dba 
 * @return bool Returns true if the key exists, false otherwise.
 */
function dba_exists (string|array $key, resource $dba): bool {}

/**
 * Fetch data specified by key
 * @link http://www.php.net/manual/en/function.dba-fetch.php
 * @param string|array $key 
 * @param resource $dba 
 * @param int $skip [optional] 
 * @return string|bool Returns the associated string if the key/data pair is found, false 
 * otherwise.
 */
function dba_fetch (string|array $key, resource $dba, int $skip = null): string|bool {}

/**
 * Splits a key in string representation into array representation
 * @link http://www.php.net/manual/en/function.dba-key-split.php
 * @param string|bool|null $key 
 * @return array|bool Returns an array of the form array(0 =&gt; group, 1 =&gt; 
 * value_name). This function will return false if 
 * key is null or false.
 */
function dba_key_split (string|bool|null $key): array|bool {}

/**
 * Fetch first key
 * @link http://www.php.net/manual/en/function.dba-firstkey.php
 * @param resource $dba 
 * @return string|bool Returns the key on success or false on failure.
 */
function dba_firstkey (resource $dba): string|bool {}

/**
 * Fetch next key
 * @link http://www.php.net/manual/en/function.dba-nextkey.php
 * @param resource $dba 
 * @return string|bool Returns the key on success or false on failure.
 */
function dba_nextkey (resource $dba): string|bool {}

/**
 * Delete DBA entry specified by key
 * @link http://www.php.net/manual/en/function.dba-delete.php
 * @param string|array $key 
 * @param resource $dba 
 * @return bool Returns true on success or false on failure.
 */
function dba_delete (string|array $key, resource $dba): bool {}

/**
 * Insert entry
 * @link http://www.php.net/manual/en/function.dba-insert.php
 * @param string|array $key 
 * @param string $value 
 * @param resource $dba 
 * @return bool Returns true on success or false on failure.
 */
function dba_insert (string|array $key, string $value, resource $dba): bool {}

/**
 * Replace or insert entry
 * @link http://www.php.net/manual/en/function.dba-replace.php
 * @param string|array $key 
 * @param string $value 
 * @param resource $dba 
 * @return bool Returns true on success or false on failure.
 */
function dba_replace (string|array $key, string $value, resource $dba): bool {}

/**
 * Optimize database
 * @link http://www.php.net/manual/en/function.dba-optimize.php
 * @param resource $dba 
 * @return bool Returns true on success or false on failure.
 */
function dba_optimize (resource $dba): bool {}

/**
 * Synchronize database
 * @link http://www.php.net/manual/en/function.dba-sync.php
 * @param resource $dba 
 * @return bool Returns true on success or false on failure.
 */
function dba_sync (resource $dba): bool {}

/**
 * List all the handlers available
 * @link http://www.php.net/manual/en/function.dba-handlers.php
 * @param bool $full_info [optional] 
 * @return array Returns an array of database handlers. If full_info
 * is set to true, the array will be associative with the handlers names as
 * keys, and their version information as value. Otherwise, the result will be
 * an indexed array of handlers names.
 * <p>When the internal cdb library is used you will see 
 * cdb and cdb_make.</p>
 */
function dba_handlers (bool $full_info = false): array {}

/**
 * List all open database files
 * @link http://www.php.net/manual/en/function.dba-list.php
 * @return array An associative array, in the form resourceid =&gt; filename.
 */
function dba_list (): array {}

// End of dba v.8.1.19
