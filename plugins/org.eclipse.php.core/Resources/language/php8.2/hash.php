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
	 * @return array 
	 */
	public function __serialize (): array {}

	/**
	 * Deserializes the data parameter into a HashContext object
	 * @link http://www.php.net/manual/en/hashcontext.unserialize.php
	 * @param array $data The value being deserialized.
	 * @return void 
	 */
	public function __unserialize (array $data): void {}

}

/**
 * Generate a hash value (message digest)
 * @link http://www.php.net/manual/en/function.hash.php
 * @param string $algo Name of selected hashing algorithm (i.e. "md5", "sha256", "haval160,4", etc..). For a list of supported algorithms see hash_algos.
 * @param string $data Message to be hashed.
 * @param bool $binary [optional] When set to true, outputs raw binary data.
 * false outputs lowercase hexits.
 * @param array $options [optional] An array of options for the various hashing algorithms. Currently, only the "seed" parameter is
 * supported by the MurmurHash variants.
 * @return string a string containing the calculated message digest as lowercase hexits
 * unless binary is set to true in which case the raw
 * binary representation of the message digest is returned.
 */
function hash (string $algo, string $data, bool $binary = null, array $options = null): string {}

/**
 * Generate a hash value using the contents of a given file
 * @link http://www.php.net/manual/en/function.hash-file.php
 * @param string $algo Name of selected hashing algorithm (i.e. "md5", "sha256", "haval160,4", etc..). For a list of supported algorithms see hash_algos.
 * @param string $filename URL describing location of file to be hashed; Supports fopen wrappers.
 * @param bool $binary [optional] When set to true, outputs raw binary data.
 * false outputs lowercase hexits.
 * @param array $options [optional] An array of options for the various hashing algorithms. Currently, only the "seed" parameter is
 * supported by the MurmurHash variants.
 * @return mixed a string containing the calculated message digest as lowercase hexits
 * unless binary is set to true in which case the raw
 * binary representation of the message digest is returned.
 */
function hash_file (string $algo, string $filename, bool $binary = null, array $options = null): string|false {}

/**
 * Generate a keyed hash value using the HMAC method
 * @link http://www.php.net/manual/en/function.hash-hmac.php
 * @param string $algo Name of selected hashing algorithm (i.e. "md5", "sha256", "haval160,4", etc..) See hash_hmac_algos for a list of supported algorithms.
 * @param string $data Message to be hashed.
 * @param string $key Shared secret key used for generating the HMAC variant of the message digest.
 * @param bool $binary [optional] When set to true, outputs raw binary data.
 * false outputs lowercase hexits.
 * @return string a string containing the calculated message digest as lowercase hexits
 * unless binary is set to true in which case the raw
 * binary representation of the message digest is returned.
 */
function hash_hmac (string $algo, string $data, string $key, bool $binary = null): string {}

/**
 * Generate a keyed hash value using the HMAC method and the contents of a given file
 * @link http://www.php.net/manual/en/function.hash-hmac-file.php
 * @param string $algo Name of selected hashing algorithm (i.e. "md5", "sha256", "haval160,4", etc..) See hash_hmac_algos for a list of supported algorithms.
 * @param string $filename URL describing location of file to be hashed; Supports fopen wrappers.
 * @param string $key Shared secret key used for generating the HMAC variant of the message digest.
 * @param bool $binary [optional] When set to true, outputs raw binary data.
 * false outputs lowercase hexits.
 * @return mixed a string containing the calculated message digest as lowercase hexits
 * unless binary is set to true in which case the raw
 * binary representation of the message digest is returned.
 * Returns false if the file filename cannot be read.
 */
function hash_hmac_file (string $algo, string $filename, string $key, bool $binary = null): string|false {}

/**
 * Initialize an incremental hashing context
 * @link http://www.php.net/manual/en/function.hash-init.php
 * @param string $algo Name of selected hashing algorithm (i.e. "md5", "sha256", "haval160,4", etc..). For a list of supported algorithms see hash_algos.
 * @param int $flags [optional] Optional settings for hash generation, currently supports only one option:
 * HASH_HMAC. When specified, the key
 * must be specified.
 * @param string $key [optional] When HASH_HMAC is specified for flags,
 * a shared secret key to be used with the HMAC hashing method must be supplied in this
 * parameter.
 * @param array $options [optional] An array of options for the various hashing algorithms. Currently, only the "seed" parameter is
 * supported by the MurmurHash variants.
 * @return HashContext a Hashing Context for use with hash_update,
 * hash_update_stream, hash_update_file,
 * and hash_final.
 */
function hash_init (string $algo, int $flags = null, string $key = null, array $options = null): HashContext {}

/**
 * Pump data into an active hashing context
 * @link http://www.php.net/manual/en/function.hash-update.php
 * @param HashContext $context Hashing context returned by hash_init.
 * @param string $data Message to be included in the hash digest.
 * @return bool true.
 */
function hash_update (HashContext $context, string $data): bool {}

/**
 * Pump data into an active hashing context from an open stream
 * @link http://www.php.net/manual/en/function.hash-update-stream.php
 * @param HashContext $context Hashing context returned by hash_init.
 * @param resource $stream Open file handle as returned by any stream creation function.
 * @param int $length [optional] Maximum number of characters to copy from stream
 * into the hashing context.
 * @return int Actual number of bytes added to the hashing context from stream.
 */
function hash_update_stream (HashContext $context, $stream, int $length = null): int {}

/**
 * Pump data into an active hashing context from a file
 * @link http://www.php.net/manual/en/function.hash-update-file.php
 * @param HashContext $context Hashing context returned by hash_init.
 * @param string $filename URL describing location of file to be hashed; Supports fopen wrappers.
 * @param mixed $stream_context [optional] Stream context as returned by stream_context_create.
 * @return bool true on success or false on failure
 */
function hash_update_file (HashContext $context, string $filename, $stream_context = null): bool {}

/**
 * Finalize an incremental hash and return resulting digest
 * @link http://www.php.net/manual/en/function.hash-final.php
 * @param HashContext $context Hashing context returned by hash_init.
 * @param bool $binary [optional] When set to true, outputs raw binary data.
 * false outputs lowercase hexits.
 * @return string a string containing the calculated message digest as lowercase hexits
 * unless binary is set to true in which case the raw
 * binary representation of the message digest is returned.
 */
function hash_final (HashContext $context, bool $binary = null): string {}

/**
 * Copy hashing context
 * @link http://www.php.net/manual/en/function.hash-copy.php
 * @param HashContext $context Hashing context returned by hash_init.
 * @return HashContext a copy of Hashing Context.
 */
function hash_copy (HashContext $context): HashContext {}

/**
 * Return a list of registered hashing algorithms
 * @link http://www.php.net/manual/en/function.hash-algos.php
 * @return array a numerically indexed array containing the list of supported
 * hashing algorithms.
 */
function hash_algos (): array {}

/**
 * Return a list of registered hashing algorithms suitable for hash_hmac
 * @link http://www.php.net/manual/en/function.hash-hmac-algos.php
 * @return array a numerically indexed array containing the list of supported hashing
 * algorithms suitable for hash_hmac.
 */
function hash_hmac_algos (): array {}

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
 * The length of the output string. If binary
 * is true this corresponds to the byte-length of the derived key, if
 * binary is false this corresponds to twice the
 * byte-length of the derived key (as every byte of the key is returned as
 * two hexits).
 * </p>
 * <p>
 * If 0 is passed, the entire output of the supplied
 * algorithm is used.
 * </p>
 * @param bool $binary [optional] When set to true, outputs raw binary data. false outputs lowercase
 * hexits.
 * @return string a string containing the derived key as lowercase hexits unless
 * binary is set to true in which case the raw
 * binary representation of the derived key is returned.
 */
function hash_pbkdf2 (string $algo, string $password, string $salt, int $iterations, int $length = null, bool $binary = null): string {}

/**
 * Timing attack safe string comparison
 * @link http://www.php.net/manual/en/function.hash-equals.php
 * @param string $known_string The string of known length to compare against
 * @param string $user_string The user-supplied string
 * @return bool true when the two strings are equal, false otherwise.
 */
function hash_equals (string $known_string, string $user_string): bool {}

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
 * @param string $key Input keying material (raw binary). Cannot be empty.
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
 * (also known as output keying material - OKM).
 */
function hash_hkdf (string $algo, string $key, int $length = null, string $info = null, string $salt = null): string {}

/**
 * Gets the block size of the specified hash
 * @link http://www.php.net/manual/en/function.mhash-get-block-size.php
 * @param int $algo The hash ID. One of the MHASH_hashname constants.
 * @return mixed the size in bytes or false, if the algo
 * does not exist.
 * @deprecated 
 */
function mhash_get_block_size (int $algo): int|false {}

/**
 * Gets the name of the specified hash
 * @link http://www.php.net/manual/en/function.mhash-get-hash-name.php
 * @param int $algo The hash ID. One of the MHASH_hashname constants.
 * @return mixed the name of the hash or false, if the hash does not exist.
 * @deprecated 
 */
function mhash_get_hash_name (int $algo): string|false {}

/**
 * Generates a key
 * @link http://www.php.net/manual/en/function.mhash-keygen-s2k.php
 * @param int $algo The hash ID used to create the key.
 * One of the MHASH_hashname constants.
 * @param string $password An user supplied password.
 * @param string $salt Must be different and random enough for every key you generate in
 * order to create different keys. Because salt
 * must be known when you check the keys, it is a good idea to append
 * the key to it. Salt has a fixed length of 8 bytes and will be padded
 * with zeros if you supply less bytes.
 * @param int $length The key length, in bytes.
 * @return mixed the generated key as a string, or false on error.
 * @deprecated 
 */
function mhash_keygen_s2k (int $algo, string $password, string $salt, int $length): string|false {}

/**
 * Gets the highest available hash ID
 * @link http://www.php.net/manual/en/function.mhash-count.php
 * @return int the highest available hash ID. Hashes are numbered from 0 to this
 * hash ID.
 * @deprecated 
 */
function mhash_count (): int {}

/**
 * Computes hash
 * @link http://www.php.net/manual/en/function.mhash.php
 * @param int $algo The hash ID. One of the MHASH_hashname constants.
 * @param string $data The user input, as a string.
 * @param mixed $key [optional] If specified, the function will return the resulting HMAC instead.
 * HMAC is keyed hashing for message authentication, or simply a message
 * digest that depends on the specified key. Not all algorithms 
 * supported in mhash can be used in HMAC mode.
 * @return mixed the resulting hash (also called digest) or HMAC as a string, or
 * false on error.
 * @deprecated 
 */
function mhash (int $algo, string $data, $key = null): string|false {}


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
define ('MHASH_CRC32C', 34);
define ('MHASH_MURMUR3A', 35);
define ('MHASH_MURMUR3C', 36);
define ('MHASH_MURMUR3F', 37);
define ('MHASH_XXH32', 38);
define ('MHASH_XXH64', 39);
define ('MHASH_XXH3', 40);
define ('MHASH_XXH128', 41);

// End of hash v.8.2.6