<?php

// Start of sysvsem v.

/**
 * Get a semaphore id
 * @link http://php.net/manual/en/function.sem-get.php
 * @param key int
 * @param max_acquire int[optional]
 * @param perm int[optional]
 * @param auto_release int[optional]
 * @return resource a positive semaphore identifier on success, or false on
 */
function sem_get ($key, $max_acquire = null, $perm = null, $auto_release = null) {}

/**
 * Acquire a semaphore
 * @link http://php.net/manual/en/function.sem-acquire.php
 * @param sem_identifier resource
 * @return bool 
 */
function sem_acquire ($sem_identifier) {}

/**
 * Release a semaphore
 * @link http://php.net/manual/en/function.sem-release.php
 * @param sem_identifier resource
 * @return bool 
 */
function sem_release ($sem_identifier) {}

/**
 * Remove a semaphore
 * @link http://php.net/manual/en/function.sem-remove.php
 * @param sem_identifier resource
 * @return bool 
 */
function sem_remove ($sem_identifier) {}

// End of sysvsem v.
?>
