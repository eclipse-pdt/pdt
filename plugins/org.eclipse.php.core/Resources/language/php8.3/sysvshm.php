<?php

// Start of sysvshm v.8.3.0

final class SysvSharedMemory  {
}

/**
 * {@inheritdoc}
 * @param int $key
 * @param int|null $size [optional]
 * @param int $permissions [optional]
 */
function shm_attach (int $key, ?int $size = NULL, int $permissions = 438): SysvSharedMemory|false {}

/**
 * {@inheritdoc}
 * @param SysvSharedMemory $shm
 */
function shm_detach (SysvSharedMemory $shm): bool {}

/**
 * {@inheritdoc}
 * @param SysvSharedMemory $shm
 * @param int $key
 */
function shm_has_var (SysvSharedMemory $shm, int $key): bool {}

/**
 * {@inheritdoc}
 * @param SysvSharedMemory $shm
 */
function shm_remove (SysvSharedMemory $shm): bool {}

/**
 * {@inheritdoc}
 * @param SysvSharedMemory $shm
 * @param int $key
 * @param mixed $value
 */
function shm_put_var (SysvSharedMemory $shm, int $key, mixed $value = null): bool {}

/**
 * {@inheritdoc}
 * @param SysvSharedMemory $shm
 * @param int $key
 */
function shm_get_var (SysvSharedMemory $shm, int $key): mixed {}

/**
 * {@inheritdoc}
 * @param SysvSharedMemory $shm
 * @param int $key
 */
function shm_remove_var (SysvSharedMemory $shm, int $key): bool {}

// End of sysvshm v.8.3.0
