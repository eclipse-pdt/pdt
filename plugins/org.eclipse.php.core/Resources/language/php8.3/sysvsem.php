<?php

// Start of sysvsem v.8.3.0

final class SysvSemaphore  {
}

/**
 * {@inheritdoc}
 * @param int $key
 * @param int $max_acquire [optional]
 * @param int $permissions [optional]
 * @param bool $auto_release [optional]
 */
function sem_get (int $key, int $max_acquire = 1, int $permissions = 438, bool $auto_release = true): SysvSemaphore|false {}

/**
 * {@inheritdoc}
 * @param SysvSemaphore $semaphore
 * @param bool $non_blocking [optional]
 */
function sem_acquire (SysvSemaphore $semaphore, bool $non_blocking = false): bool {}

/**
 * {@inheritdoc}
 * @param SysvSemaphore $semaphore
 */
function sem_release (SysvSemaphore $semaphore): bool {}

/**
 * {@inheritdoc}
 * @param SysvSemaphore $semaphore
 */
function sem_remove (SysvSemaphore $semaphore): bool {}

// End of sysvsem v.8.3.0
