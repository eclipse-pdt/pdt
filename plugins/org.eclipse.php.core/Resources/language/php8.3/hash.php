<?php

// Start of hash v.8.3.0

final class HashContext  {

	/**
	 * {@inheritdoc}
	 */
	private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data): void {}

}

/**
 * {@inheritdoc}
 * @param string $algo
 * @param string $data
 * @param bool $binary [optional]
 * @param array $options [optional]
 */
function hash (string $algo, string $data, bool $binary = false, array $options = array (
)): string {}

/**
 * {@inheritdoc}
 * @param string $algo
 * @param string $filename
 * @param bool $binary [optional]
 * @param array $options [optional]
 */
function hash_file (string $algo, string $filename, bool $binary = false, array $options = array (
)): string|false {}

/**
 * {@inheritdoc}
 * @param string $algo
 * @param string $data
 * @param string $key
 * @param bool $binary [optional]
 */
function hash_hmac (string $algo, string $data, string $key, bool $binary = false): string {}

/**
 * {@inheritdoc}
 * @param string $algo
 * @param string $filename
 * @param string $key
 * @param bool $binary [optional]
 */
function hash_hmac_file (string $algo, string $filename, string $key, bool $binary = false): string|false {}

/**
 * {@inheritdoc}
 * @param string $algo
 * @param int $flags [optional]
 * @param string $key [optional]
 * @param array $options [optional]
 */
function hash_init (string $algo, int $flags = 0, string $key = '', array $options = array (
)): HashContext {}

/**
 * {@inheritdoc}
 * @param HashContext $context
 * @param string $data
 */
function hash_update (HashContext $context, string $data): bool {}

/**
 * {@inheritdoc}
 * @param HashContext $context
 * @param mixed $stream
 * @param int $length [optional]
 */
function hash_update_stream (HashContext $context, $stream = null, int $length = -1): int {}

/**
 * {@inheritdoc}
 * @param HashContext $context
 * @param string $filename
 * @param mixed $stream_context [optional]
 */
function hash_update_file (HashContext $context, string $filename, $stream_context = NULL): bool {}

/**
 * {@inheritdoc}
 * @param HashContext $context
 * @param bool $binary [optional]
 */
function hash_final (HashContext $context, bool $binary = false): string {}

/**
 * {@inheritdoc}
 * @param HashContext $context
 */
function hash_copy (HashContext $context): HashContext {}

/**
 * {@inheritdoc}
 */
function hash_algos (): array {}

/**
 * {@inheritdoc}
 */
function hash_hmac_algos (): array {}

/**
 * {@inheritdoc}
 * @param string $algo
 * @param string $password
 * @param string $salt
 * @param int $iterations
 * @param int $length [optional]
 * @param bool $binary [optional]
 * @param array $options [optional]
 */
function hash_pbkdf2 (string $algo, string $password, string $salt, int $iterations, int $length = 0, bool $binary = false, array $options = array (
)): string {}

/**
 * {@inheritdoc}
 * @param string $known_string
 * @param string $user_string
 */
function hash_equals (string $known_string, string $user_string): bool {}

/**
 * {@inheritdoc}
 * @param string $algo
 * @param string $key
 * @param int $length [optional]
 * @param string $info [optional]
 * @param string $salt [optional]
 */
function hash_hkdf (string $algo, string $key, int $length = 0, string $info = '', string $salt = ''): string {}

/**
 * {@inheritdoc}
 * @param int $algo
 * @deprecated 
 */
function mhash_get_block_size (int $algo): int|false {}

/**
 * {@inheritdoc}
 * @param int $algo
 * @deprecated 
 */
function mhash_get_hash_name (int $algo): string|false {}

/**
 * {@inheritdoc}
 * @param int $algo
 * @param string $password
 * @param string $salt
 * @param int $length
 * @deprecated 
 */
function mhash_keygen_s2k (int $algo, string $password, string $salt, int $length): string|false {}

/**
 * {@inheritdoc}
 * @deprecated 
 */
function mhash_count (): int {}

/**
 * {@inheritdoc}
 * @param int $algo
 * @param string $data
 * @param string|null $key [optional]
 * @deprecated 
 */
function mhash (int $algo, string $data, ?string $key = NULL): string|false {}

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

// End of hash v.8.3.0
