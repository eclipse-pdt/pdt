<?php

// Start of hash v.1.0

/**
 * Generate a hash value (message digest)
 * @link http://www.php.net/manual/en/function.hash.php
 * @param algo string <p>
 * Name of selected hashing algorithm (i.e. "md5", "sha256", "haval160,4", etc..)
 * </p>
 * @param data string <p>
 * Message to be hashed.
 * </p>
 * @param raw_output bool[optional] <p>
 * When set to true, outputs raw binary data.
 * false outputs lowercase hexits.
 * </p>
 * @return string a string containing the calculated message digest as lowercase hexits
 * unless raw_output is set to true in which case the raw
 * binary representation of the message digest is returned.
 */
function hash ($algo, $data, $raw_output = null) {}

/**
 * Generate a hash value using the contents of a given file
 * @link http://www.php.net/manual/en/function.hash-file.php
 * @param algo string <p>
 * Name of selected hashing algorithm (i.e. "md5", "sha256", "haval160,4", etc..)
 * </p>
 * @param filename string <p>
 * URL describing location of file to be hashed; Supports fopen wrappers.
 * </p>
 * @param raw_output bool[optional] <p>
 * When set to true, outputs raw binary data.
 * false outputs lowercase hexits.
 * </p>
 * @return string a string containing the calculated message digest as lowercase hexits
 * unless raw_output is set to true in which case the raw
 * binary representation of the message digest is returned.
 */
function hash_file ($algo, $filename, $raw_output = null) {}

/**
 * Generate a keyed hash value using the HMAC method
 * @link http://www.php.net/manual/en/function.hash-hmac.php
 * @param algo string <p>
 * Name of selected hashing algorithm (i.e. "md5", "sha256", "haval160,4", etc..) See hash_algos for a list of supported algorithms.
 * </p>
 * @param data string <p>
 * Message to be hashed.
 * </p>
 * @param key string <p>
 * Shared secret key used for generating the HMAC variant of the message digest.
 * </p>
 * @param raw_output bool[optional] <p>
 * When set to true, outputs raw binary data.
 * false outputs lowercase hexits.
 * </p>
 * @return string a string containing the calculated message digest as lowercase hexits
 * unless raw_output is set to true in which case the raw
 * binary representation of the message digest is returned.
 */
function hash_hmac ($algo, $data, $key, $raw_output = null) {}

/**
 * Generate a keyed hash value using the HMAC method and the contents of a given file
 * @link http://www.php.net/manual/en/function.hash-hmac-file.php
 * @param algo string <p>
 * Name of selected hashing algorithm (i.e. "md5", "sha256", "haval160,4", etc..) See hash_algos for a list of supported algorithms.
 * </p>
 * @param filename string <p>
 * URL describing location of file to be hashed; Supports fopen wrappers.
 * </p>
 * @param key string <p>
 * Shared secret key used for generating the HMAC variant of the message digest.
 * </p>
 * @param raw_output bool[optional] <p>
 * When set to true, outputs raw binary data.
 * false outputs lowercase hexits.
 * </p>
 * @return string a string containing the calculated message digest as lowercase hexits
 * unless raw_output is set to true in which case the raw
 * binary representation of the message digest is returned.
 */
function hash_hmac_file ($algo, $filename, $key, $raw_output = null) {}

/**
 * Initialize an incremental hashing context
 * @link http://www.php.net/manual/en/function.hash-init.php
 * @param algo string <p>
 * Name of selected hashing algorithm (i.e. "md5", "sha256", "haval160,4", etc..). For a list of supported algorithms see hash_algos.
 * </p>
 * @param options int[optional] <p>
 * Optional settings for hash generation, currently supports only one option:
 * HASH_HMAC. When specified, the key
 * must be specified.
 * </p>
 * @param key string[optional] <p>
 * When HASH_HMAC is specified for options,
 * a shared secret key to be used with the HMAC hashing method must be supplied in this
 * parameter.
 * </p>
 * @return resource a Hashing Context resource for use with hash_update,
 * hash_update_stream, hash_update_file,
 * and hash_final.
 */
function hash_init ($algo, $options = null, $key = null) {}

/**
 * Pump data into an active hashing context
 * @link http://www.php.net/manual/en/function.hash-update.php
 * @param context resource <p>
 * Hashing context returned by hash_init.
 * </p>
 * @param data string <p>
 * Message to be included in the hash digest.
 * </p>
 * @return bool true.
 */
function hash_update ($context, $data) {}

/**
 * Pump data into an active hashing context from an open stream
 * @link http://www.php.net/manual/en/function.hash-update-stream.php
 * @param context resource <p>
 * Hashing context returned by hash_init.
 * </p>
 * @param handle resource <p>
 * Open file handle as returned by any stream creation function.
 * </p>
 * @param length int[optional] <p>
 * Maximum number of characters to copy from handle
 * into the hashing context.
 * </p>
 * @return int Actual number of bytes added to the hashing context from handle.
 */
function hash_update_stream ($context, $handle, $length = null) {}

/**
 * Pump data into an active hashing context from a file
 * @link http://www.php.net/manual/en/function.hash-update-file.php
 * @param hcontext resource <p>
 * Hashing context returned by hash_init.
 * </p>
 * @param filename string <p>
 * URL describing location of file to be hashed; Supports fopen wrappers.
 * </p>
 * @param scontext resource[optional] <p>
 * Stream context as returned by stream_context_create.
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function hash_update_file ($hcontext, $filename, $scontext = null) {}

/**
 * Finalize an incremental hash and return resulting digest
 * @link http://www.php.net/manual/en/function.hash-final.php
 * @param context resource <p>
 * Hashing context returned by hash_init.
 * </p>
 * @param raw_output bool[optional] <p>
 * When set to true, outputs raw binary data.
 * false outputs lowercase hexits.
 * </p>
 * @return string a string containing the calculated message digest as lowercase hexits
 * unless raw_output is set to true in which case the raw
 * binary representation of the message digest is returned.
 */
function hash_final ($context, $raw_output = null) {}

/**
 * Copy hashing context
 * @link http://www.php.net/manual/en/function.hash-copy.php
 * @param context resource <p>
 * Hashing context returned by hash_init.
 * </p>
 * @return resource a copy of Hashing Context resource.
 */
function hash_copy ($context) {}

/**
 * Return a list of registered hashing algorithms
 * @link http://www.php.net/manual/en/function.hash-algos.php
 * @return array a numerically indexed array containing the list of supported
 * hashing algorithms.
 */
function hash_algos () {}

/**
 * Generate a PBKDF2 key derivation of a supplied password
 * @link http://www.php.net/manual/en/function.hash-pbkdf2.php
 * @param algo
 * @param password
 * @param salt
 * @param iterations
 * @param length[optional]
 * @param raw_output[optional]
 */
function hash_pbkdf2 ($algo, $password, $salt, $iterations, $length, $raw_output) {}

/**
 * Generates a key
 * @link http://www.php.net/manual/en/function.mhash-keygen-s2k.php
 * @param hash int <p>
 * The hash ID used to create the key.
 * One of the MHASH_hashname constants.
 * </p>
 * @param password string <p>
 * An user supplied password.
 * </p>
 * @param salt string <p>
 * Must be different and random enough for every key you generate in
 * order to create different keys. Because salt
 * must be known when you check the keys, it is a good idea to append
 * the key to it. Salt has a fixed length of 8 bytes and will be padded
 * with zeros if you supply less bytes.
 * </p>
 * @param bytes int <p>
 * The key length, in bytes.
 * </p>
 * @return string the generated key as a string, or false on error.
 */
function mhash_keygen_s2k ($hash, $password, $salt, $bytes) {}

/**
 * Gets the block size of the specified hash
 * @link http://www.php.net/manual/en/function.mhash-get-block-size.php
 * @param hash int <p>
 * The hash ID. One of the MHASH_hashname constants.
 * </p>
 * @return int the size in bytes or false, if the hash
 * does not exist.
 */
function mhash_get_block_size ($hash) {}

/**
 * Gets the name of the specified hash
 * @link http://www.php.net/manual/en/function.mhash-get-hash-name.php
 * @param hash int <p>
 * The hash ID. One of the MHASH_hashname constants.
 * </p>
 * @return string the name of the hash or false, if the hash does not exist.
 */
function mhash_get_hash_name ($hash) {}

/**
 * Gets the highest available hash ID
 * @link http://www.php.net/manual/en/function.mhash-count.php
 * @return int the highest available hash ID. Hashes are numbered from 0 to this
 * hash ID.
 */
function mhash_count () {}

/**
 * Computes hash
 * @link http://www.php.net/manual/en/function.mhash.php
 * @param hash int <p>
 * The hash ID. One of the MHASH_hashname constants.
 * </p>
 * @param data string <p>
 * The user input, as a string.
 * </p>
 * @param key string[optional] <p>
 * If specified, the function will return the resulting HMAC instead.
 * HMAC is keyed hashing for message authentication, or simply a message
 * digest that depends on the specified key. Not all algorithms 
 * supported in mhash can be used in HMAC mode.
 * </p>
 * @return string the resulting hash (also called digest) or HMAC as a string, or
 * false on error.
 */
function mhash ($hash, $data, $key = null) {}


/**
 * Optional flag for hash_init.
 * Indicates that the HMAC digest-keying algorithm should be
 * applied to the current hashing context.
 * @link http://www.php.net/manual/en/hash.constants.php
 */
define ('HASH_HMAC', 1);
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
define ('MHASH_SNEFRU256', 27);
define ('MHASH_MD2', 28);
define ('MHASH_FNV132', 29);
define ('MHASH_FNV1A32', 30);
define ('MHASH_FNV164', 31);
define ('MHASH_FNV1A64', 32);
define ('MHASH_JOAAT', 33);

// End of hash v.1.0
