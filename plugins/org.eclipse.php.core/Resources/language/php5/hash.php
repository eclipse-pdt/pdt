<?php

// Start of hash v.1.0

/**
 * Generate a hash value (message digest)
 * @link http://php.net/manual/en/function.hash.php
 * @param algo string
 * @param data string
 * @param raw_output bool[optional]
 * @return string a string containing the calculated message digest as lowercase hexits
 */
function hash ($algo, $data, $raw_output = null) {}

/**
 * Generate a hash value using the contents of a given file
 * @link http://php.net/manual/en/function.hash-file.php
 * @param algo string
 * @param filename string
 * @param raw_output bool[optional]
 * @return string a string containing the calculated message digest as lowercase hexits
 */
function hash_file ($algo, $filename, $raw_output = null) {}

/**
 * Generate a keyed hash value using the HMAC method
 * @link http://php.net/manual/en/function.hash-hmac.php
 * @param algo string
 * @param data string
 * @param key string
 * @param raw_output bool[optional]
 * @return string a string containing the calculated message digest as lowercase hexits
 */
function hash_hmac ($algo, $data, $key, $raw_output = null) {}

/**
 * Generate a keyed hash value using the HMAC method and the contents of a given file
 * @link http://php.net/manual/en/function.hash-hmac-file.php
 * @param algo string
 * @param filename string
 * @param key string
 * @param raw_output bool[optional]
 * @return string a string containing the calculated message digest as lowercase hexits
 */
function hash_hmac_file ($algo, $filename, $key, $raw_output = null) {}

/**
 * Initialize an incremental hashing context
 * @link http://php.net/manual/en/function.hash-init.php
 * @param algo string
 * @param options int[optional]
 * @param key string[optional]
 * @return resource a Hashing Context resource for use with hash_update,
 */
function hash_init ($algo, $options = null, $key = null) {}

/**
 * Pump data into an active hashing context
 * @link http://php.net/manual/en/function.hash-update.php
 * @param context resource
 * @param data string
 * @return bool true.
 */
function hash_update ($context, $data) {}

/**
 * Pump data into an active hashing context from an open stream
 * @link http://php.net/manual/en/function.hash-update-stream.php
 * @param context resource
 * @param handle resource
 * @param length int[optional]
 * @return int 
 */
function hash_update_stream ($context, $handle, $length = null) {}

/**
 * Pump data into an active hashing context from a file
 * @link http://php.net/manual/en/function.hash-update-file.php
 * @param context resource
 * @param filename string
 * @param context resource[optional]
 * @return bool 
 */
function hash_update_file ($context, $filename, $context = null) {}

/**
 * Finalize an incremental hash and return resulting digest
 * @link http://php.net/manual/en/function.hash-final.php
 * @param context resource
 * @param raw_output bool[optional]
 * @return string a string containing the calculated message digest as lowercase hexits
 */
function hash_final ($context, $raw_output = null) {}

/**
 * Return a list of registered hashing algorithms
 * @link http://php.net/manual/en/function.hash-algos.php
 * @return array a numerically indexed array containing the list of supported
 */
function hash_algos () {}


/**
 * Optional flag for hash_init.
 * Indicates that the HMAC digest-keying algorithm should be
 * applied to the current hashing context.
 * @link http://php.net/manual/en/hash.constants.php
 */
define ('HASH_HMAC', 1);

// End of hash v.1.0
?>
