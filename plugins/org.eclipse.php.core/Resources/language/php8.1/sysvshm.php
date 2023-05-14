<?php

// Start of sysvshm v.8.1.19

/**
 * A fully opaque class which replaces a sysvshm resource as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/class.sysvsharedmemory.php
 */
final class SysvSharedMemory  {
}

/**
 * Creates or open a shared memory segment
 * @link http://www.php.net/manual/en/function.shm-attach.php
 * @param int $key A numeric shared memory segment ID
 * @param mixed $size [optional] The memory size. If not provided, default to the
 * sysvshm.init_mem in the php.ini, otherwise 10000
 * bytes.
 * @param int $permissions [optional] The optional permission bits. Default to 0666.
 * @return mixed a SysvSharedMemory instance on success, or false on failure.
 */
function shm_attach (int $key, $size = null, int $permissions = null): SysvSharedMemory|false {}

/**
 * Disconnects from shared memory segment
 * @link http://www.php.net/manual/en/function.shm-detach.php
 * @param SysvSharedMemory $shm A shared memory segment obtained from shm_attach.
 * @return bool true on success or false on failure
 */
function shm_detach (SysvSharedMemory $shm): bool {}

/**
 * Check whether a specific entry exists
 * @link http://www.php.net/manual/en/function.shm-has-var.php
 * @param SysvSharedMemory $shm A shared memory segment obtained from shm_attach.
 * @param int $key The variable key.
 * @return bool true if the entry exists, otherwise false
 */
function shm_has_var (SysvSharedMemory $shm, int $key): bool {}

/**
 * Removes shared memory from Unix systems
 * @link http://www.php.net/manual/en/function.shm-remove.php
 * @param SysvSharedMemory $shm A shared memory segment obtained from shm_attach.
 * @return bool true on success or false on failure
 */
function shm_remove (SysvSharedMemory $shm): bool {}

/**
 * Inserts or updates a variable in shared memory
 * @link http://www.php.net/manual/en/function.shm-put-var.php
 * @param SysvSharedMemory $shm A shared memory segment obtained from shm_attach.
 * @param int $key The variable key.
 * @param mixed $value The variable. All variable types
 * that serialize supports may be used: generally
 * this means all types except for resources and some internal objects
 * that cannot be serialized.
 * @return bool true on success or false on failure
 */
function shm_put_var (SysvSharedMemory $shm, int $key, $value): bool {}

/**
 * Returns a variable from shared memory
 * @link http://www.php.net/manual/en/function.shm-get-var.php
 * @param SysvSharedMemory $shm A shared memory segment obtained from shm_attach.
 * @param int $key The variable key.
 * @return mixed the variable with the given key.
 */
function shm_get_var (SysvSharedMemory $shm, int $key): ?mixed {}

/**
 * Removes a variable from shared memory
 * @link http://www.php.net/manual/en/function.shm-remove-var.php
 * @param SysvSharedMemory $shm A shared memory segment obtained from shm_attach.
 * @param int $key The variable key.
 * @return bool true on success or false on failure
 */
function shm_remove_var (SysvSharedMemory $shm, int $key): bool {}

// End of sysvshm v.8.1.19
