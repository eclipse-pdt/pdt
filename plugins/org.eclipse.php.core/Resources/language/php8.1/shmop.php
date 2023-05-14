<?php

// Start of shmop v.8.1.19

/**
 * A fully opaque class which replaces shmop resources as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/class.shmop.php
 */
final class Shmop  {
}

/**
 * Create or open shared memory block
 * @link http://www.php.net/manual/en/function.shmop-open.php
 * @param int $key System's id for the shared memory block.
 * Can be passed as a decimal or hex.
 * @param string $mode <p>
 * The flags that you can use:
 * <p>
 * <br>
 * "a" for access (sets SHM_RDONLY for shmat)
 * use this flag when you need to open an existing shared memory
 * segment for read only
 * <br>
 * "c" for create (sets IPC_CREATE)
 * use this flag when you need to create a new shared memory segment
 * or if a segment with the same key exists, try to open it for read
 * and write
 * <br>
 * "w" for read &amp; write access
 * use this flag when you need to read and write to a shared memory
 * segment, use this flag in most cases.
 * <br>
 * "n" create a new memory segment (sets IPC_CREATE|IPC_EXCL)
 * use this flag when you want to create a new shared memory segment
 * but if one already exists with the same flag, fail. This is useful
 * for security purposes, using this you can prevent race condition
 * exploits.
 * </p>
 * </p>
 * @param int $permissions The permissions that you wish to assign to your memory segment, those
 * are the same as permission for a file. Permissions need to be passed
 * in octal form, like for example 0644
 * @param int $size The size of the shared memory block you wish to create in bytes
 * @return mixed On success shmop_open will return a Shmop instance that you can
 * use to access the shared memory segment you've created. false is
 * returned on failure.
 */
function shmop_open (int $key, string $mode, int $permissions, int $size): Shmop|false {}

/**
 * Read data from shared memory block
 * @link http://www.php.net/manual/en/function.shmop-read.php
 * @param Shmop $shmop The shared memory block identifier created by 
 * shmop_open
 * @param int $offset Offset from which to start reading; must be greater than or equal to zero
 * and less than or equal to the actual size of the shared memory segment.
 * @param int $size The number of bytes to read; must be greater than or equal to zero,
 * and the sum of offset and size 
 * must be less than or equal to the actual size of the shared memory segment.
 * 0 reads shmop_size($shmid) - $start bytes.
 * @return string the data or false on failure.
 */
function shmop_read (Shmop $shmop, int $offset, int $size): string {}

/**
 * Close shared memory block
 * @link http://www.php.net/manual/en/function.shmop-close.php
 * @param Shmop $shmop The shared memory block resource created by 
 * shmop_open
 * @return void 
 * @deprecated 
 */
function shmop_close (Shmop $shmop): void {}

/**
 * Get size of shared memory block
 * @link http://www.php.net/manual/en/function.shmop-size.php
 * @param Shmop $shmop The shared memory block identifier created by 
 * shmop_open
 * @return int an int, which represents the number of bytes the shared memory
 * block occupies.
 */
function shmop_size (Shmop $shmop): int {}

/**
 * Write data into shared memory block
 * @link http://www.php.net/manual/en/function.shmop-write.php
 * @param Shmop $shmop The shared memory block identifier created by 
 * shmop_open
 * @param string $data A string to write into shared memory block
 * @param int $offset Specifies where to start writing data inside the shared memory
 * segment. The offset must be greater than or equal to zero
 * and less than or equal to the actual size of the shared memory segment.
 * @return int The size of the written data.
 */
function shmop_write (Shmop $shmop, string $data, int $offset): int {}

/**
 * Delete shared memory block
 * @link http://www.php.net/manual/en/function.shmop-delete.php
 * @param Shmop $shmop The shared memory block resource created by 
 * shmop_open
 * @return bool true on success or false on failure
 */
function shmop_delete (Shmop $shmop): bool {}

// End of shmop v.8.1.19
