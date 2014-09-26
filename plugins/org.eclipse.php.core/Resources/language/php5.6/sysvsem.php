<?php

// Start of sysvsem v.

/**
 * Get a semaphore id
 * @link http://www.php.net/manual/en/function.sem-get.php
 * @param key int <p>
 * </p>
 * @param max_acquire int[optional] <p>
 * The number of processes that can acquire the semaphore simultaneously
 * is set to max_acquire.
 * </p>
 * @param perm int[optional] <p>
 * The semaphore permissions. Actually this value is
 * set only if the process finds it is the only process currently
 * attached to the semaphore.
 * </p>
 * @param auto_release int[optional] <p>
 * Specifies if the semaphore should be automatically released on request
 * shutdown.
 * </p>
 * @return resource a positive semaphore identifier on success, or false on
 * error.
 */
function sem_get ($key, $max_acquire = null, $perm = null, $auto_release = null) {}

/**
 * Acquire a semaphore
 * @link http://www.php.net/manual/en/function.sem-acquire.php
 * @param sem_identifier resource <p>
 * sem_identifier is a semaphore resource,
 * obtained from sem_get.
 * </p>
 * @return bool true on success or false on failure
 */
function sem_acquire ($sem_identifier) {}

/**
 * Release a semaphore
 * @link http://www.php.net/manual/en/function.sem-release.php
 * @param sem_identifier resource <p>
 * A Semaphore resource handle as returned by
 * sem_get.
 * </p>
 * @return bool true on success or false on failure
 */
function sem_release ($sem_identifier) {}

/**
 * Remove a semaphore
 * @link http://www.php.net/manual/en/function.sem-remove.php
 * @param sem_identifier resource <p>
 * A semaphore resource identifier as returned
 * by sem_get.
 * </p>
 * @return bool true on success or false on failure
 */
function sem_remove ($sem_identifier) {}

// End of sysvsem v.
