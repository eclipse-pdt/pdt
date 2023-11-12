<?php

// Start of sysvsem v.8.2.6

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
 * @param int $max_acquire [optional] 
 * @param int $permissions [optional] 
 * @param bool $auto_release [optional] 
 * @return SysvSemaphore|false Returns a positive semaphore identifier on success, or false on
 * error.
 */
function sem_get (int $key, int $max_acquire = 1, int $permissions = 0666, bool $auto_release = true): SysvSemaphore|false {}

/**
 * Acquire a semaphore
 * @link http://www.php.net/manual/en/function.sem-acquire.php
 * @param SysvSemaphore $semaphore 
 * @param bool $non_blocking [optional] 
 * @return bool Returns true on success or false on failure.
 */
function sem_acquire (SysvSemaphore $semaphore, bool $non_blocking = false): bool {}

/**
 * Release a semaphore
 * @link http://www.php.net/manual/en/function.sem-release.php
 * @param SysvSemaphore $semaphore 
 * @return bool Returns true on success or false on failure.
 */
function sem_release (SysvSemaphore $semaphore): bool {}

/**
 * Remove a semaphore
 * @link http://www.php.net/manual/en/function.sem-remove.php
 * @param SysvSemaphore $semaphore 
 * @return bool Returns true on success or false on failure.
 */
function sem_remove (SysvSemaphore $semaphore): bool {}

// End of sysvsem v.8.2.6
