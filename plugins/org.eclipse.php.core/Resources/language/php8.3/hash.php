<?php

// Start of hash v.8.2.6

/**
 * @link http://www.php.net/manual/en/class.hashcontext.php
 */
final class HashContext  {

	/**
	 * Private constructor to disallow direct instantiation
	 * @link http://www.php.net/manual/en/hashcontext.construct.php
	 */
	private function __construct () {}

	/**
	 * Serializes the HashContext object
	 * @link http://www.php.net/manual/en/hashcontext.serialize.php
	 * @return array No value is returned.
	 */
	public function __serialize (): array {}

	/**
	 * Deserializes the data parameter into a HashContext object
	 * @link http://www.php.net/manual/en/hashcontext.unserialize.php
	 * @param array $data The value being deserialized.
	 * @return void No value is returned.
	 */
	public function __unserialize (array $data): void {}

}

/**
 * Generate a hash value (message digest)
 * @link http://www.php.net/manual/en/function.hash.php
 * @param string $algo 
 * @param string $data 
 * @param bool $binary [optional] 
 * @param array $options [optional] 
 * @return string Returns a string containing the calculated message digest as lowercase hexits
 * unless binary is set to true in which case the raw
 * binary representation of the message digest is returned.
 */
function hash (string $algo, string $data, bool $binary = false, array $options = '[]'): string {}

/**
 * Generate a hash value using the contents of a given file
 * @link http://www.php.net/manual/en/function.hash-file.php
 * @param string $algo 
 * @param string $filename 
 * @param bool $binary [optional] 
 * @param array $options [optional] 
 * @return string|false Returns a string containing the calculated message digest as lowercase hexits
 * unless binary is set to true in which case the raw
 * binary representation of the message digest is returned.
 */
function hash_file (string $algo, string $filename, bool $binary = false, array $options = '[]'): string|false {}

/**
 * Generate a keyed hash value using the HMAC method
 * @link http://www.php.net/manual/en/function.hash-hmac.php
 * @param string $algo 
 * @param string $data 
 * @param string $key 
 * @param bool $binary [optional] 
 * @return string Returns a string containing the calculated message digest as lowercase hexits
 * unless binary is set to true in which case the raw
 * binary representation of the message digest is returned.
 */
function hash_hmac (string $algo, string $data, string $key, bool $binary = false): string {}

/**
 * Generate a keyed hash value using the HMAC method and the contents of a given file
 * @link http://www.php.net/manual/en/function.hash-hmac-file.php
 * @param string $algo 
 * @param string $filename 
 * @param string $key 
 * @param bool $binary [optional] 
 * @return string|false Returns a string containing the calculated message digest as lowercase hexits
 * unless binary is set to true in which case the raw
 * binary representation of the message digest is returned.
 * Returns false if the file filename cannot be read.
 */
function hash_hmac_file (string $algo, string $filename, string $key, bool $binary = false): string|false {}

/**
 * Initialize an incremental hashing context
 * @link http://www.php.net/manual/en/function.hash-init.php
 * @param string $algo 
 * @param int $flags [optional] 
 * @param string $key [optional] 
 * @param array $options [optional] 
 * @return HashContext Returns a Hashing Context for use with hash_update,
 * hash_update_stream, hash_update_file,
 * and hash_final.
 */
function hash_init (string $algo, int $flags = null, string $key = '""', array $options = '[]'): HashContext {}

/**
 * Pump data into an active hashing context
 * @link http://www.php.net/manual/en/function.hash-update.php
 * @param HashContext $context 
 * @param string $data 
 * @return bool Returns true.
 */
function hash_update (HashContext $context, string $data): bool {}

/**
 * Pump data into an active hashing context from an open stream
 * @link http://www.php.net/manual/en/function.hash-update-stream.php
 * @param HashContext $context 
 * @param resource $stream 
 * @param int $length [optional] 
 * @return int Actual number of bytes added to the hashing context from stream.
 */
function hash_update_stream (HashContext $context, $stream, int $length = -1): int {}

/**
 * Pump data into an active hashing context from a file
 * @link http://www.php.net/manual/en/function.hash-update-file.php
 * @param HashContext $context 
 * @param string $filename 
 * @param resource|null $stream_context [optional] 
 * @return bool Returns true on success or false on failure.
 */
function hash_update_file (HashContext $context, string $filename, $stream_context = null): bool {}

/**
 * Finalize an incremental hash and return resulting digest
 * @link http://www.php.net/manual/en/function.hash-final.php
 * @param HashContext $context 
 * @param bool $binary [optional] 
 * @return string Returns a string containing the calculated message digest as lowercase hexits
 * unless binary is set to true in which case the raw
 * binary representation of the message digest is returned.
 */
function hash_final (HashContext $context, bool $binary = false): string {}

/**
 * Copy hashing context
 * @link http://www.php.net/manual/en/function.hash-copy.php
 * @param HashContext $context 
 * @return HashContext Returns a copy of Hashing Context.
 */
function hash_copy (HashContext $context): HashContext {}

/**
 * Return a list of registered hashing algorithms
 * @link http://www.php.net/manual/en/function.hash-algos.php
 * @return array Returns a numerically indexed array containing the list of supported
 * hashing algorithms.
 */
function hash_algos (): array {}

/**
 * Return a list of registered hashing algorithms suitable for hash_hmac
 * @link http://www.php.net/manual/en/function.hash-hmac-algos.php
 * @return array Returns a numerically indexed array containing the list of supported hashing
 * algorithms suitable for hash_hmac.
 */
function hash_hmac_algos (): array {}

/**
 * Generate a PBKDF2 key derivation of a supplied password
 * @link http://www.php.net/manual/en/function.hash-pbkdf2.php
 * @param string $algo 
 * @param string $password 
 * @param string $salt 
 * @param int $iterations 
 * @param int $length [optional] 
 * @param bool $binary [optional] 
 * @return string Returns a string containing the derived key as lowercase hexits unless
 * binary is set to true in which case the raw
 * binary representation of the derived key is returned.
 */
function hash_pbkdf2 (string $algo, string $password, string $salt, int $iterations, int $length = null, bool $binary = false): string {}

/**
 * Timing attack safe string comparison
 * @link http://www.php.net/manual/en/function.hash-equals.php
 * @param string $known_string The string of known length to compare against
 * @param string $user_string The user-supplied string
 * @return bool Returns true when the two strings are equal, false otherwise.
 */
function hash_equals (string $known_string, string $user_string): bool {}

/**
 * Generate a HKDF key derivation of a supplied key input
 * @link http://www.php.net/manual/en/function.hash-hkdf.php
 * @param string $algo 
 * @param string $key 
 * @param int $length [optional] 
 * @param string $info [optional] 
 * @param string $salt [optional] 
 * @return string Returns a string containing a raw binary representation of the derived key
 * (also known as output keying material - OKM).
 */
function hash_hkdf (string $algo, string $key, int $length = null, string $info = '""', string $salt = '""'): string {}

/**
 * Gets the block size of the specified hash
 * @link http://www.php.net/manual/en/function.mhash-get-block-size.php
 * @param int $algo 
 * @return int|false Returns the size in bytes or false, if the algo
 * does not exist.
 * @deprecated 1
 */
function mhash_get_block_size (int $algo): int|false {}

/**
 * Gets the name of the specified hash
 * @link http://www.php.net/manual/en/function.mhash-get-hash-name.php
 * @param int $algo 
 * @return string|false Returns the name of the hash or false, if the hash does not exist.
 * @deprecated 1
 */
function mhash_get_hash_name (int $algo): string|false {}

/**
 * Generates a key
 * @link http://www.php.net/manual/en/function.mhash-keygen-s2k.php
 * @param int $algo 
 * @param string $password 
 * @param string $salt 
 * @param int $length 
 * @return string|false Returns the generated key as a string, or false on error.
 * @deprecated 1
 */
function mhash_keygen_s2k (int $algo, string $password, string $salt, int $length): string|false {}

/**
 * Gets the highest available hash ID
 * @link http://www.php.net/manual/en/function.mhash-count.php
 * @return int Returns the highest available hash ID. Hashes are numbered from 0 to this
 * hash ID.
 * @deprecated 1
 */
function mhash_count (): int {}

/**
 * Computes hash
 * @link http://www.php.net/manual/en/function.mhash.php
 * @param int $algo 
 * @param string $data 
 * @param string|null $key [optional] 
 * @return string|false Returns the resulting hash (also called digest) or HMAC as a string, or
 * false on error.
 * @deprecated 1
 */
function mhash (int $algo, string $data, ?string $key = null): string|false {}


/**
 * Optional flag for hash_init.
 * Indicates that the HMAC digest-keying algorithm should be
 * applied to the current hashing context.
 * @link http://www.php.net/manual/en/hash.constants.php
 * @var int
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
define ('MHASH_CRC32C', 34);
define ('MHASH_MURMUR3A', 35);
define ('MHASH_MURMUR3C', 36);
define ('MHASH_MURMUR3F', 37);
define ('MHASH_XXH32', 38);
define ('MHASH_XXH64', 39);
define ('MHASH_XXH3', 40);
define ('MHASH_XXH128', 41);

// End of hash v.8.2.6
