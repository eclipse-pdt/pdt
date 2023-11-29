<?php

// Start of shmop v.8.3.0

final class Shmop  {
}

/**
 * {@inheritdoc}
 * @param int $key
 * @param string $mode
 * @param int $permissions
 * @param int $size
 */
function shmop_open (int $key, string $mode, int $permissions, int $size): Shmop|false {}

/**
 * {@inheritdoc}
 * @param Shmop $shmop
 * @param int $offset
 * @param int $size
 */
function shmop_read (Shmop $shmop, int $offset, int $size): string {}

/**
 * {@inheritdoc}
 * @param Shmop $shmop
 * @deprecated 
 */
function shmop_close (Shmop $shmop): void {}

/**
 * {@inheritdoc}
 * @param Shmop $shmop
 */
function shmop_size (Shmop $shmop): int {}

/**
 * {@inheritdoc}
 * @param Shmop $shmop
 * @param string $data
 * @param int $offset
 */
function shmop_write (Shmop $shmop, string $data, int $offset): int {}

/**
 * {@inheritdoc}
 * @param Shmop $shmop
 */
function shmop_delete (Shmop $shmop): bool {}

// End of shmop v.8.3.0
