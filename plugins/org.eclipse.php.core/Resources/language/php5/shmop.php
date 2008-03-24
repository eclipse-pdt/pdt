<?php

// Start of shmop v.

/**
 * Create or open shared memory block
 * @link http://php.net/manual/en/function.shmop-open.php
 * @param key int
 * @param flags string
 * @param mode int
 * @param size int
 * @return int 
 */
function shmop_open ($key, $flags, $mode, $size) {}

/**
 * Read data from shared memory block
 * @link http://php.net/manual/en/function.shmop-read.php
 * @param shmid int
 * @param start int
 * @param count int
 * @return string the data or false on failure.
 */
function shmop_read ($shmid, $start, $count) {}

/**
 * Close shared memory block
 * @link http://php.net/manual/en/function.shmop-close.php
 * @param shmid int
 * @return void 
 */
function shmop_close ($shmid) {}

/**
 * Get size of shared memory block
 * @link http://php.net/manual/en/function.shmop-size.php
 * @param shmid int
 * @return int an int, which represents the number of bytes the shared memory
 */
function shmop_size ($shmid) {}

/**
 * Write data into shared memory block
 * @link http://php.net/manual/en/function.shmop-write.php
 * @param shmid int
 * @param data string
 * @param offset int
 * @return int 
 */
function shmop_write ($shmid, $data, $offset) {}

/**
 * Delete shared memory block
 * @link http://php.net/manual/en/function.shmop-delete.php
 * @param shmid int
 * @return bool 
 */
function shmop_delete ($shmid) {}

// End of shmop v.
?>
