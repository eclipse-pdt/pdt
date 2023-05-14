<?php

// Start of sysvsem v.8.0.28

/**
 * A fully opaque class which replaces a sysvsem resource as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/class.sysvsemaphore.php
 */
final class SysvSemaphore  {
}

/**
 * Get a semaphore id
 * @link http://www.php.net/manual/en/function.sem-get.php
 * @param int $key 
 * @param int $max_acquire [optional] The number of processes that can acquire the semaphore simultaneously
 * is set to max_acquire.
 * @param int $permissions [optional] The semaphore permissions. Actually this value is
 * set only if the process finds it is the only process currently
 * attached to the semaphore.
 * @param bool $auto_release [optional] Specifies if the semaphore should be automatically released on request
 * shutdown.
 * @return mixed a positive semaphore identifier on success, or false on
 * error.
 */
function sem_get (int $key, int $max_acquire = null, int $permissions = null, bool $auto_release = null): SysvSemaphore|false {}

/**
 * Acquire a semaphore
 * @link http://www.php.net/manual/en/function.sem-acquire.php
 * @param SysvSemaphore $semaphore semaphore is a semaphore
 * obtained from sem_get.
 * @param bool $non_blocking [optional] Specifies if the process shouldn't wait for the semaphore to be acquired.
 * If set to true, the call will return
 * false immediately if a semaphore cannot be immediately
 * acquired.
 * @return bool true on success or false on failure
 */
function sem_acquire (SysvSemaphore $semaphore, bool $non_blocking = null): bool {}

/**
 * Release a semaphore
 * @link http://www.php.net/manual/en/function.sem-release.php
 * @param SysvSemaphore $semaphore A Semaphore as returned by
 * sem_get.
 * @return bool true on success or false on failure
 */
function sem_release (SysvSemaphore $semaphore): bool {}

/**
 * Remove a semaphore
 * @link http://www.php.net/manual/en/function.sem-remove.php
 * @param SysvSemaphore $semaphore A semaphore as returned
 * by sem_get.
 * @return bool true on success or false on failure
 */
function sem_remove (SysvSemaphore $semaphore): bool {}

// End of sysvsem v.8.0.28
