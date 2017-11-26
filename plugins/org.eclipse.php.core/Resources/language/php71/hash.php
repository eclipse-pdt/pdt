<?php

// Start of hash v.1.0

/**
 * Generate a hash value (message digest)
 * @link http://www.php.net/manual/en/function.hash.php
 * @param string $algo Name of selected hashing algorithm (e.g. "md5", "sha256", "haval160,4", etc..)
 * @param string $data Message to be hashed.
 * @param bool $raw_output [optional] When set to true, outputs raw binary data.
 * false outputs lowercase hexits.
 * @return string a string containing the calculated message digest as lowercase hexits
 * unless raw_output is set to true in which case the raw
 * binary representation of the message digest is returned.
 */
function hash (string $algo, string $data, bool $raw_output = null) {}

/**
 * Generate a hash value using the contents of a given file
 * @link http://www.php.net/manual/en/function.hash-file.php
 * @param string $algo Name of selected hashing algorithm (i.e. "md5", "sha256", "haval160,4", etc..)
 * @param string $filename URL describing location of file to be hashed; Supports fopen wrappers.
 * @param bool $raw_output [optional] When set to true, outputs raw binary data.
 * false outputs lowercase hexits.
 * @return string a string containing the calculated message digest as lowercase hexits
 * unless raw_output is set to true in which case the raw
 * binary representation of the message digest is returned.
 */
function hash_file (string $algo, string $filename, bool $raw_output = null) {}

/**
 * Generate a keyed hash value using the HMAC method
 * @link http://www.php.net/manual/en/function.hash-hmac.php
 * @param string $algo Name of selected hashing algorithm (i.e. "md5", "sha256", "haval160,4", etc..) See hash_hmac_algos for a list of supported algorithms.
 * @param string $data Message to be hashed.
 * @param string $key Shared secret key used for generating the HMAC variant of the message digest.
 * @param bool $raw_output [optional] When set to true, outputs raw binary data.
 * false outputs lowercase hexits.
 * @return string a string containing the calculated message digest as lowercase hexits
 * unless raw_output is set to true in which case the raw
 * binary representation of the message digest is returned.
 * Returns false when algo is unknown.
 */
function hash_hmac (string $algo, string $data, string $key, bool $raw_output = null) {}

/**
 * Generate a keyed hash value using the HMAC method and the contents of a given file
 * @link http://www.php.net/manual/en/function.hash-hmac-file.php
 * @param string $algo Name of selected hashing algorithm (i.e. "md5", "sha256", "haval160,4", etc..) See hash_hmac_algos for a list of supported algorithms.
 * @param string $filename URL describing location of file to be hashed; Supports fopen wrappers.
 * @param string $key Shared secret key used for generating the HMAC variant of the message digest.
 * @param bool $raw_output [optional] When set to true, outputs raw binary data.
 * false outputs lowercase hexits.
 * @return string a string containing the calculated message digest as lowercase hexits
 * unless raw_output is set to true in which case the raw
 * binary representation of the message digest is returned.
 */
function hash_hmac_file (string $algo, string $filename, string $key, bool $raw_output = null) {}

/**
 * Initialize an incremental hashing context
 * @link http://www.php.net/manual/en/function.hash-init.php
 * @param string $algo Name of selected hashing algorithm (i.e. "md5", "sha256", "haval160,4", etc..). For a list of supported algorithms see hash_algos.
 * @param int $options [optional] Optional settings for hash generation, currently supports only one option:
 * HASH_HMAC. When specified, the key
 * must be specified.
 * @param string $key [optional] When HASH_HMAC is specified for options,
 * a shared secret key to be used with the HMAC hashing method must be supplied in this
 * parameter.
 * @return resource a Hashing Context resource for use with hash_update,
 * hash_update_stream, hash_update_file,
 * and hash_final.
 */
function hash_init (string $algo, int $options = null, string $key = null) {}

/**
 * Pump data into an active hashing context
 * @link http://www.php.net/manual/en/function.hash-update.php
 * @param resource $context Hashing context returned by hash_init.
 * @param string $data Message to be included in the hash digest.
 * @return bool true.
 */
function hash_update ($context, string $data) {}

/**
 * Pump data into an active hashing context from an open stream
 * @link http://www.php.net/manual/en/function.hash-update-stream.php
 * @param resource $context Hashing context returned by hash_init.
 * @param resource $handle Open file handle as returned by any stream creation function.
 * @param int $length [optional] Maximum number of characters to copy from handle
 * into the hashing context.
 * @return int Actual number of bytes added to the hashing context from handle.
 */
function hash_update_stream ($context, $handle, int $length = null) {}

/**
 * Pump data into an active hashing context from a file
 * @link http://www.php.net/manual/en/function.hash-update-file.php
 * @param resource $hcontext Hashing context returned by hash_init.
 * @param string $filename URL describing location of file to be hashed; Supports fopen wrappers.
 * @param resource $scontext [optional] Stream context as returned by stream_context_create.
 * @return bool true on success or false on failure
 */
function hash_update_file ($hcontext, string $filename, $scontext = null) {}

/**
 * Finalize an incremental hash and return resulting digest
 * @link http://www.php.net/manual/en/function.hash-final.php
 * @param resource $context Hashing context returned by hash_init.
 * @param bool $raw_output [optional] When set to true, outputs raw binary data.
 * false outputs lowercase hexits.
 * @return string a string containing the calculated message digest as lowercase hexits
 * unless raw_output is set to true in which case the raw
 * binary representation of the message digest is returned.
 */
function hash_final ($context, bool $raw_output = null) {}

/**
 * Copy hashing context
 * @link http://www.php.net/manual/en/function.hash-copy.php
 * @param resource $context Hashing context returned by hash_init.
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
 * @param string $algo Name of selected hashing algorithm (i.e. md5,
 * sha256, haval160,4, etc..) See
 * hash_algos for a list of supported algorithms.
 * @param string $password The password to use for the derivation.
 * @param string $salt The salt to use for the derivation. This value should be generated randomly.
 * @param int $iterations The number of internal iterations to perform for the derivation.
 * @param int $length [optional] <p>
 * The length of the output string. If raw_output
 * is true this corresponds to the byte-length of the derived key, if
 * raw_output is false this corresponds to twice the
 * byte-length of the derived key (as every byte of the key is returned as
 * two hexits).
 * </p>
 * <p>
 * If 0 is passed, the entire output of the supplied
 * algorithm is used.
 * </p>
 * @param bool $raw_output [optional] When set to true, outputs raw binary data. false outputs lowercase
 * hexits.
 * @return string a string containing the derived key as lowercase hexits unless
 * raw_output is set to true in which case the raw
 * binary representation of the derived key is returned.
 */
function hash_pbkdf2 (string $algo, string $password, string $salt, int $iterations, int $length = null, bool $raw_output = null) {}

/**
 * Timing attack safe string comparison
 * @link http://www.php.net/manual/en/function.hash-equals.php
 * @param string $known_string The string of known length to compare against
 * @param string $user_string The user-supplied string
 * @return bool true when the two strings are equal, false otherwise.
 */
function hash_equals (string $known_string, string $user_string) {}

/**
 * Generate a HKDF key derivation of a supplied key input
 * @link http://www.php.net/manual/en/function.hash-hkdf.php
 * @param string $algo <p>
 * Name of selected hashing algorithm (i.e. "sha256", "sha512", "haval160,4", etc..)
 * See <p>
 * <p>
 * Non-cryptographic hash functions are not allowed.
 * </p>
 * </p>
 * @param string $ikm Input keying material (raw binary). Cannot be empty.
 * @param int $length [optional] <p>
 * Desired output length in bytes.
 * Cannot be greater than 255 times the chosen hash function size.
 * </p>
 * <p>
 * If length is 0, the output length
 * will default to the chosen hash function size.
 * </p>
 * @param string $info [optional] Application/context-specific info string.
 * @param string $salt [optional] <p>
 * Salt to use during derivation.
 * </p>
 * <p>
 * While optional, adding random salt significantly improves the strength of HKDF.
 * </p>
 * @return string a string containing a raw binary representation of the derived key
 * (also known as output keying material - OKM); or false on failure.
 */
function hash_hkdf (string $algo, string $ikm, int $length = null, string $info = null, string $salt = null) {}


/**
 * Optional flag for hash_init.
 * Indicates that the HMAC digest-keying algorithm should be
 * applied to the current hashing context.
 * @link http://www.php.net/manual/en/hash.constants.php
 */
define ('HASH_HMAC', 1);

// End of hash v.1.0
