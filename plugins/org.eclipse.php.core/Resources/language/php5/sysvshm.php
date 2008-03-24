<?php

// Start of sysvshm v.

/**
 * Creates or open a shared memory segment
 * @link http://php.net/manual/en/function.shm-attach.php
 * @param key int
 * @param memsize int[optional]
 * @param perm int[optional]
 * @return int a shared memory segment identifier.
 */
function shm_attach ($key, $memsize = null, $perm = null) {}

/**
 * Removes shared memory from Unix systems
 * @link http://php.net/manual/en/function.shm-remove.php
 * @param shm_identifier int
 * @return bool 
 */
function shm_remove ($shm_identifier) {}

/**
 * Disconnects from shared memory segment
 * @link http://php.net/manual/en/function.shm-detach.php
 * @param shm_identifier int
 * @return bool 
 */
function shm_detach ($shm_identifier) {}

/**
 * Inserts or updates a variable in shared memory
 * @link http://php.net/manual/en/function.shm-put-var.php
 * @param shm_identifier int
 * @param variable_key int
 * @param variable mixed
 * @return bool 
 */
function shm_put_var ($shm_identifier, $variable_key, $variable) {}

/**
 * Returns a variable from shared memory
 * @link http://php.net/manual/en/function.shm-get-var.php
 * @param shm_identifier int
 * @param variable_key int
 * @return mixed the variable with the given key.
 */
function shm_get_var ($shm_identifier, $variable_key) {}

/**
 * Removes a variable from shared memory
 * @link http://php.net/manual/en/function.shm-remove-var.php
 * @param shm_identifier int
 * @param variable_key int
 * @return bool 
 */
function shm_remove_var ($shm_identifier, $variable_key) {}

// End of sysvshm v.
?>
