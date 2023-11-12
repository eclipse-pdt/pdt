<?php

// Start of shmop v.8.2.6

/**
 * A fully opaque class which replaces shmop resources as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/class.shmop.php
 */
final class Shmop  {
}

/**
 * Create or open shared memory block
 * @link http://www.php.net/manual/en/function.shmop-open.php
 * @param int $key 
 * @param string $mode 
 * @param int $permissions 
 * @param int $size 
 * @return Shmop|false On success shmop_open will return a Shmop instance that you can
 * use to access the shared memory segment you've created. false is
 * returned on failure.
 */
function shmop_open (int $key, string $mode, int $permissions, int $size): Shmop|false {}

/**
 * Read data from shared memory block
 * @link http://www.php.net/manual/en/function.shmop-read.php
 * @param Shmop $shmop 
 * @param int $offset 
 * @param int $size 
 * @return string Returns the data or false on failure.
 */
function shmop_read (Shmop $shmop, int $offset, int $size): string {}

/**
 * Close shared memory block
 * @link http://www.php.net/manual/en/function.shmop-close.php
 * @param Shmop $shmop 
 * @return void No value is returned.
 * @deprecated 1
 */
function shmop_close (Shmop $shmop): void {}

/**
 * Get size of shared memory block
 * @link http://www.php.net/manual/en/function.shmop-size.php
 * @param Shmop $shmop 
 * @return int Returns an int, which represents the number of bytes the shared memory
 * block occupies.
 */
function shmop_size (Shmop $shmop): int {}

/**
 * Write data into shared memory block
 * @link http://www.php.net/manual/en/function.shmop-write.php
 * @param Shmop $shmop 
 * @param string $data 
 * @param int $offset 
 * @return int The size of the written data.
 */
function shmop_write (Shmop $shmop, string $data, int $offset): int {}

/**
 * Delete shared memory block
 * @link http://www.php.net/manual/en/function.shmop-delete.php
 * @param Shmop $shmop 
 * @return bool Returns true on success or false on failure.
 */
function shmop_delete (Shmop $shmop): bool {}

// End of shmop v.8.2.6
