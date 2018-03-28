<?php

// Start of sysvsem v.7.1.1

/**
 * Get a semaphore id
 * @link http://www.php.net/manual/en/function.sem-get.php
 * @param int $key 
 * @param int $max_acquire [optional] The number of processes that can acquire the semaphore simultaneously
 * is set to max_acquire.
 * @param int $perm [optional] The semaphore permissions. Actually this value is
 * set only if the process finds it is the only process currently
 * attached to the semaphore.
 * @param int $auto_release [optional] Specifies if the semaphore should be automatically released on request
 * shutdown.
 * @return resource a positive semaphore identifier on success, or false on
 * error.
 */
function sem_get (int $key, int $max_acquire = null, int $perm = null, int $auto_release = null) {}

/**
 * Acquire a semaphore
 * @link http://www.php.net/manual/en/function.sem-acquire.php
 * @param resource $sem_identifier sem_identifier is a semaphore resource,
 * obtained from sem_get.
 * @param bool $nowait [optional] Specifies if the process shouldn't wait for the semaphore to be acquired.
 * If set to true, the call will return
 * false immediately if a semaphore cannot be immediately
 * acquired.
 * @return bool true on success or false on failure
 */
function sem_acquire ($sem_identifier, bool $nowait = null) {}

/**
 * Release a semaphore
 * @link http://www.php.net/manual/en/function.sem-release.php
 * @param resource $sem_identifier A Semaphore resource handle as returned by
 * sem_get.
 * @return bool true on success or false on failure
 */
function sem_release ($sem_identifier) {}

/**
 * Remove a semaphore
 * @link http://www.php.net/manual/en/function.sem-remove.php
 * @param resource $sem_identifier A semaphore resource identifier as returned
 * by sem_get.
 * @return bool true on success or false on failure
 */
function sem_remove ($sem_identifier) {}

// End of sysvsem v.7.1.1
