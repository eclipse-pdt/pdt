<?php

// Start of mhash v.

/**
 * Get the block size of the specified hash
 * @link http://php.net/manual/en/function.mhash-get-block-size.php
 * @param hash int
 * @return int the size in bytes or false, if the hash
 */
function mhash_get_block_size ($hash) {}

/**
 * Get the name of the specified hash
 * @link http://php.net/manual/en/function.mhash-get-hash-name.php
 * @param hash int
 * @return string the name of the hash or false, if the hash does not exist.
 */
function mhash_get_hash_name ($hash) {}

/**
 * Generates a key
 * @link http://php.net/manual/en/function.mhash-keygen-s2k.php
 * @param hash int
 * @param password string
 * @param salt string
 * @param bytes int
 * @return string the generated key as a string, or false on error.
 */
function mhash_keygen_s2k ($hash, $password, $salt, $bytes) {}

/**
 * Get the highest available hash id
 * @link http://php.net/manual/en/function.mhash-count.php
 * @return int the highest available hash id. Hashes are numbered from 0 to this
 */
function mhash_count () {}

/**
 * Compute hash
 * @link http://php.net/manual/en/function.mhash.php
 * @param hash int
 * @param data string
 * @param key string[optional]
 * @return string the resulting hash (also called digest) or HMAC as a string, or
 */
function mhash ($hash, $data, $key = null) {}

define ('MHASH_CRC32', 0);
define ('MHASH_MD5', 1);
define ('MHASH_SHA1', 2);
define ('MHASH_HAVAL256', 3);
define ('MHASH_RIPEMD160', 5);
define ('MHASH_TIGER', 7);
define ('MHASH_GOST', 8);
define ('MHASH_CRC32B', 9);
define ('MHASH_HAVAL224', 10);
define ('MHASH_HAVAL192', 11);
define ('MHASH_HAVAL160', 12);
define ('MHASH_HAVAL128', 13);
define ('MHASH_TIGER128', 14);
define ('MHASH_TIGER160', 15);
define ('MHASH_MD4', 16);
define ('MHASH_SHA256', 17);
define ('MHASH_ADLER32', 18);
define ('MHASH_SHA224', 19);
define ('MHASH_SHA512', 20);
define ('MHASH_SHA384', 21);
define ('MHASH_WHIRLPOOL', 22);
define ('MHASH_RIPEMD128', 23);
define ('MHASH_RIPEMD256', 24);
define ('MHASH_RIPEMD320', 25);
define ('MHASH_SNEFRU128', 26);
define ('MHASH_SNEFRU256', 27);
define ('MHASH_MD2', 28);

// End of mhash v.
?>
