<?php

// Start of sysvshm v.8.2.6

/**
 * A fully opaque class which replaces a sysvshm resource as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/class.sysvsharedmemory.php
 */
final class SysvSharedMemory  {
}

/**
 * Creates or open a shared memory segment
 * @link http://www.php.net/manual/en/function.shm-attach.php
 * @param int $key 
 * @param int|null $size [optional] 
 * @param int $permissions [optional] 
 * @return SysvSharedMemory|false Returns a SysvSharedMemory instance on success, or false on failure.
 */
function shm_attach (int $key, ?int $size = null, int $permissions = 0666): SysvSharedMemory|false {}

/**
 * Disconnects from shared memory segment
 * @link http://www.php.net/manual/en/function.shm-detach.php
 * @param SysvSharedMemory $shm 
 * @return bool Returns true on success or false on failure.
 */
function shm_detach (SysvSharedMemory $shm): bool {}

/**
 * Check whether a specific entry exists
 * @link http://www.php.net/manual/en/function.shm-has-var.php
 * @param SysvSharedMemory $shm 
 * @param int $key 
 * @return bool Returns true if the entry exists, otherwise false
 */
function shm_has_var (SysvSharedMemory $shm, int $key): bool {}

/**
 * Removes shared memory from Unix systems
 * @link http://www.php.net/manual/en/function.shm-remove.php
 * @param SysvSharedMemory $shm 
 * @return bool Returns true on success or false on failure.
 */
function shm_remove (SysvSharedMemory $shm): bool {}

/**
 * Inserts or updates a variable in shared memory
 * @link http://www.php.net/manual/en/function.shm-put-var.php
 * @param SysvSharedMemory $shm 
 * @param int $key 
 * @param mixed $value 
 * @return bool Returns true on success or false on failure.
 */
function shm_put_var (SysvSharedMemory $shm, int $key, mixed $value): bool {}

/**
 * Returns a variable from shared memory
 * @link http://www.php.net/manual/en/function.shm-get-var.php
 * @param SysvSharedMemory $shm 
 * @param int $key 
 * @return mixed Returns the variable with the given key.
 */
function shm_get_var (SysvSharedMemory $shm, int $key): mixed {}

/**
 * Removes a variable from shared memory
 * @link http://www.php.net/manual/en/function.shm-remove-var.php
 * @param SysvSharedMemory $shm 
 * @param int $key 
 * @return bool Returns true on success or false on failure.
 */
function shm_remove_var (SysvSharedMemory $shm, int $key): bool {}

// End of sysvshm v.8.2.6
