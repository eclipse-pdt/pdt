<?php

// Start of shmop v.7.3.0

/**
 * Create or open shared memory block
 * @link http://www.php.net/manual/en/function.shmop-open.php
 * @param int $key System's id for the shared memory block.
 * Can be passed as a decimal or hex.
 * @param string $flags <p>
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
 * @param int $mode The permissions that you wish to assign to your memory segment, those
 * are the same as permission for a file. Permissions need to be passed
 * in octal form, like for example 0644
 * @param int $size The size of the shared memory block you wish to create in bytes
 * @return resource On success shmop_open will return an resource that you can
 * use to access the shared memory segment you've created. false is 
 * returned on failure.
 */
function shmop_open (int $key, string $flags, int $mode, int $size) {}

/**
 * Read data from shared memory block
 * @link http://www.php.net/manual/en/function.shmop-read.php
 * @param resource $shmid The shared memory block identifier created by 
 * shmop_open
 * @param int $start Offset from which to start reading
 * @param int $count The number of bytes to read.
 * 0 reads shmop_size($shmid) - $start bytes.
 * @return string the data or false on failure.
 */
function shmop_read ($shmid, int $start, int $count) {}

/**
 * Close shared memory block
 * @link http://www.php.net/manual/en/function.shmop-close.php
 * @param resource $shmid The shared memory block resource created by 
 * shmop_open
 * @return void 
 */
function shmop_close ($shmid) {}

/**
 * Get size of shared memory block
 * @link http://www.php.net/manual/en/function.shmop-size.php
 * @param resource $shmid The shared memory block identifier created by 
 * shmop_open
 * @return int an int, which represents the number of bytes the shared memory
 * block occupies.
 */
function shmop_size ($shmid) {}

/**
 * Write data into shared memory block
 * @link http://www.php.net/manual/en/function.shmop-write.php
 * @param resource $shmid The shared memory block identifier created by 
 * shmop_open
 * @param string $data A string to write into shared memory block
 * @param int $offset Specifies where to start writing data inside the shared memory
 * segment.
 * @return int The size of the written data, or false on 
 * failure.
 */
function shmop_write ($shmid, string $data, int $offset) {}

/**
 * Delete shared memory block
 * @link http://www.php.net/manual/en/function.shmop-delete.php
 * @param resource $shmid The shared memory block resource created by 
 * shmop_open
 * @return bool true on success or false on failure
 */
function shmop_delete ($shmid) {}

// End of shmop v.7.3.0
