<?php

// Start of mcrypt v.1.0.6

/**
 * Gets the key size of the specified cipher
 * @link http://www.php.net/manual/en/function.mcrypt-get-key-size.php
 * @param int $cipher 
 * @return int|false Returns the maximum supported key size of the algorithm in bytes
 * or false on failure.
 * @deprecated 1
 */
function mcrypt_get_key_size (int $cipher): int|false {}

/**
 * Gets the block size of the specified cipher
 * @link http://www.php.net/manual/en/function.mcrypt-get-block-size.php
 * @param int $cipher 
 * @return int|false Returns the algorithm block size in bytes or false on failure.
 * @deprecated 1
 */
function mcrypt_get_block_size (int $cipher): int|false {}

/**
 * Gets the name of the specified cipher
 * @link http://www.php.net/manual/en/function.mcrypt-get-cipher-name.php
 * @param int $cipher 
 * @return string This function returns the name of the cipher or false if the cipher does
 * not exist.
 * @deprecated 1
 */
function mcrypt_get_cipher_name (int $cipher): string {}

/**
 * Creates an initialization vector (IV) from a random source
 * @link http://www.php.net/manual/en/function.mcrypt-create-iv.php
 * @param int $size 
 * @param int $source [optional] 
 * @return string Returns the initialization vector, or false on error.
 * @deprecated 1
 */
function mcrypt_create_iv (int $size, int $source = MCRYPT_DEV_URANDOM): string {}

/**
 * Gets an array of all supported ciphers
 * @link http://www.php.net/manual/en/function.mcrypt-list-algorithms.php
 * @param string $lib_dir [optional] 
 * @return array Returns an array with all the supported algorithms.
 * @deprecated 1
 */
function mcrypt_list_algorithms (string $lib_dir = 'ini_get("mcrypt.algorithms_dir")'): array {}

/**
 * Gets an array of all supported modes
 * @link http://www.php.net/manual/en/function.mcrypt-list-modes.php
 * @param string $lib_dir [optional] 
 * @return array Returns an array with all the supported modes.
 * @deprecated 1
 */
function mcrypt_list_modes (string $lib_dir = 'ini_get("mcrypt.modes_dir")'): array {}

/**
 * Returns the size of the IV belonging to a specific cipher/mode combination
 * @link http://www.php.net/manual/en/function.mcrypt-get-iv-size.php
 * @param string $cipher 
 * @param string $mode 
 * @return int Returns the size of the Initialization Vector (IV) in bytes. On error the
 * function returns false. If the IV is ignored in the specified cipher/mode
 * combination zero is returned.
 * @deprecated 1
 */
function mcrypt_get_iv_size (string $cipher, string $mode): int {}

/**
 * Encrypts plaintext with given parameters
 * @link http://www.php.net/manual/en/function.mcrypt-encrypt.php
 * @param string $cipher 
 * @param string $key 
 * @param string $data 
 * @param string $mode 
 * @param string $iv [optional] 
 * @return string|false Returns the encrypted data as a string or false on failure.
 * @deprecated 1
 */
function mcrypt_encrypt (string $cipher, string $key, string $data, string $mode, string $iv = null): string|false {}

/**
 * Decrypts crypttext with given parameters
 * @link http://www.php.net/manual/en/function.mcrypt-decrypt.php
 * @param string $cipher 
 * @param string $key 
 * @param string $data 
 * @param string $mode 
 * @param string $iv [optional] 
 * @return string|false Returns the decrypted data as a string or false on failure.
 * @deprecated 1
 */
function mcrypt_decrypt (string $cipher, string $key, string $data, string $mode, string $iv = null): string|false {}

/**
 * Opens the module of the algorithm and the mode to be used
 * @link http://www.php.net/manual/en/function.mcrypt-module-open.php
 * @param string $algorithm 
 * @param string $algorithm_directory 
 * @param string $mode 
 * @param string $mode_directory 
 * @return resource Normally it returns an encryption descriptor, or false on error.
 * @deprecated 1
 */
function mcrypt_module_open (string $algorithm, string $algorithm_directory, string $mode, string $mode_directory) {}

/**
 * This function initializes all buffers needed for encryption
 * @link http://www.php.net/manual/en/function.mcrypt-generic-init.php
 * @param resource $td 
 * @param string $key 
 * @param string $iv 
 * @return int The function returns a negative value on error: -3 when the key length
 * was incorrect, -4 when there was a memory allocation problem and any
 * other return value is an unknown error. If an error occurs a warning will
 * be displayed accordingly. false is returned if incorrect parameters
 * were passed.
 * @deprecated 1
 */
function mcrypt_generic_init ($td, string $key, string $iv): int {}

/**
 * This function encrypts data
 * @link http://www.php.net/manual/en/function.mcrypt-generic.php
 * @param resource $td 
 * @param string $data 
 * @return string Returns the encrypted data.
 * @deprecated 1
 */
function mcrypt_generic ($td, string $data): string {}

/**
 * Decrypts data
 * @link http://www.php.net/manual/en/function.mdecrypt-generic.php
 * @param resource $td 
 * @param string $data 
 * @return string Returns decrypted string.
 * @deprecated 1
 */
function mdecrypt_generic ($td, string $data): string {}

/**
 * This function deinitializes an encryption module
 * @link http://www.php.net/manual/en/function.mcrypt-generic-deinit.php
 * @param resource $td 
 * @return bool Returns true on success or false on failure.
 * @deprecated 1
 */
function mcrypt_generic_deinit ($td): bool {}

/**
 * Runs a self test on the opened module
 * @link http://www.php.net/manual/en/function.mcrypt-enc-self-test.php
 * @param resource $td 
 * @return int Returns 0 on success and a negative int on failure.
 * @deprecated 1
 */
function mcrypt_enc_self_test ($td): int {}

/**
 * Checks whether the encryption of the opened mode works on blocks
 * @link http://www.php.net/manual/en/function.mcrypt-enc-is-block-algorithm-mode.php
 * @param resource $td 
 * @return bool Returns true if the mode is for use with block algorithms, otherwise it
 * returns false.
 * @deprecated 1
 */
function mcrypt_enc_is_block_algorithm_mode ($td): bool {}

/**
 * Checks whether the algorithm of the opened mode is a block algorithm
 * @link http://www.php.net/manual/en/function.mcrypt-enc-is-block-algorithm.php
 * @param resource $td 
 * @return bool Returns true if the algorithm is a block algorithm or false if it is
 * a stream one.
 * @deprecated 1
 */
function mcrypt_enc_is_block_algorithm ($td): bool {}

/**
 * Checks whether the opened mode outputs blocks
 * @link http://www.php.net/manual/en/function.mcrypt-enc-is-block-mode.php
 * @param resource $td 
 * @return bool Returns true if the mode outputs blocks of bytes,
 * or false if it outputs just bytes.
 * @deprecated 1
 */
function mcrypt_enc_is_block_mode ($td): bool {}

/**
 * Returns the blocksize of the opened algorithm
 * @link http://www.php.net/manual/en/function.mcrypt-enc-get-block-size.php
 * @param resource $td 
 * @return int Returns the block size of the specified algorithm in bytes.
 * @deprecated 1
 */
function mcrypt_enc_get_block_size ($td): int {}

/**
 * Returns the maximum supported keysize of the opened mode
 * @link http://www.php.net/manual/en/function.mcrypt-enc-get-key-size.php
 * @param resource $td 
 * @return int Returns the maximum supported key size of the algorithm in bytes.
 * @deprecated 1
 */
function mcrypt_enc_get_key_size ($td): int {}

/**
 * Returns an array with the supported keysizes of the opened algorithm
 * @link http://www.php.net/manual/en/function.mcrypt-enc-get-supported-key-sizes.php
 * @param resource $td 
 * @return array Returns an array with the key sizes supported by the algorithm
 * specified by the encryption descriptor. If it returns an empty
 * array then all key sizes between 1 and
 * mcrypt_enc_get_key_size are supported by the
 * algorithm.
 * @deprecated 1
 */
function mcrypt_enc_get_supported_key_sizes ($td): array {}

/**
 * Returns the size of the IV of the opened algorithm
 * @link http://www.php.net/manual/en/function.mcrypt-enc-get-iv-size.php
 * @param resource $td 
 * @return int Returns the size of the IV, or 0 if the IV is ignored by the algorithm.
 * @deprecated 1
 */
function mcrypt_enc_get_iv_size ($td): int {}

/**
 * Returns the name of the opened algorithm
 * @link http://www.php.net/manual/en/function.mcrypt-enc-get-algorithms-name.php
 * @param resource $td 
 * @return string Returns the name of the opened algorithm as a string.
 * @deprecated 1
 */
function mcrypt_enc_get_algorithms_name ($td): string {}

/**
 * Returns the name of the opened mode
 * @link http://www.php.net/manual/en/function.mcrypt-enc-get-modes-name.php
 * @param resource $td 
 * @return string Returns the name as a string.
 * @deprecated 1
 */
function mcrypt_enc_get_modes_name ($td): string {}

/**
 * This function runs a self test on the specified module
 * @link http://www.php.net/manual/en/function.mcrypt-module-self-test.php
 * @param string $algorithm 
 * @param string $lib_dir [optional] 
 * @return bool The function returns true if the self test succeeds, or false when it
 * fails.
 * @deprecated 1
 */
function mcrypt_module_self_test (string $algorithm, string $lib_dir = null): bool {}

/**
 * Returns if the specified module is a block algorithm or not
 * @link http://www.php.net/manual/en/function.mcrypt-module-is-block-algorithm-mode.php
 * @param string $mode 
 * @param string $lib_dir [optional] 
 * @return bool This function returns true if the mode is for use with block
 * algorithms, otherwise it returns false. (e.g. false for stream, and
 * true for cbc, cfb, ofb).
 * @deprecated 1
 */
function mcrypt_module_is_block_algorithm_mode (string $mode, string $lib_dir = null): bool {}

/**
 * This function checks whether the specified algorithm is a block algorithm
 * @link http://www.php.net/manual/en/function.mcrypt-module-is-block-algorithm.php
 * @param string $algorithm 
 * @param string $lib_dir [optional] 
 * @return bool This function returns true if the specified algorithm is a block
 * algorithm, or false if it is a stream one.
 * @deprecated 1
 */
function mcrypt_module_is_block_algorithm (string $algorithm, string $lib_dir = null): bool {}

/**
 * Returns if the specified mode outputs blocks or not
 * @link http://www.php.net/manual/en/function.mcrypt-module-is-block-mode.php
 * @param string $mode 
 * @param string $lib_dir [optional] 
 * @return bool This function returns true if the mode outputs blocks of bytes or
 * false if it outputs just bytes. (e.g. true for cbc and ecb, and
 * false for cfb and stream).
 * @deprecated 1
 */
function mcrypt_module_is_block_mode (string $mode, string $lib_dir = null): bool {}

/**
 * Returns the blocksize of the specified algorithm
 * @link http://www.php.net/manual/en/function.mcrypt-module-get-algo-block-size.php
 * @param string $algorithm 
 * @param string $lib_dir [optional] 
 * @return int Returns the block size of the algorithm specified in bytes.
 * @deprecated 1
 */
function mcrypt_module_get_algo_block_size (string $algorithm, string $lib_dir = null): int {}

/**
 * Returns the maximum supported keysize of the opened mode
 * @link http://www.php.net/manual/en/function.mcrypt-module-get-algo-key-size.php
 * @param string $algorithm 
 * @param string $lib_dir [optional] 
 * @return int This function returns the maximum supported key size of the
 * algorithm specified in bytes.
 * @deprecated 1
 */
function mcrypt_module_get_algo_key_size (string $algorithm, string $lib_dir = null): int {}

/**
 * Returns an array with the supported keysizes of the opened algorithm
 * @link http://www.php.net/manual/en/function.mcrypt-module-get-supported-key-sizes.php
 * @param string $algorithm 
 * @param string $lib_dir [optional] 
 * @return array Returns an array with the key sizes supported by the specified algorithm.
 * If it returns an empty array then all key sizes between 1 and
 * mcrypt_module_get_algo_key_size are supported by the
 * algorithm.
 * @deprecated 1
 */
function mcrypt_module_get_supported_key_sizes (string $algorithm, string $lib_dir = null): array {}

/**
 * Closes the mcrypt module
 * @link http://www.php.net/manual/en/function.mcrypt-module-close.php
 * @param resource $td 
 * @return bool Returns true on success or false on failure.
 * @deprecated 1
 */
function mcrypt_module_close ($td): bool {}


/**
 * 
 * @link http://www.php.net/manual/en/mcrypt.constants.php
 * @var int
 */
define ('MCRYPT_ENCRYPT', 0);

/**
 * 
 * @link http://www.php.net/manual/en/mcrypt.constants.php
 * @var int
 */
define ('MCRYPT_DECRYPT', 1);

/**
 * 
 * @link http://www.php.net/manual/en/mcrypt.constants.php
 * @var int
 */
define ('MCRYPT_DEV_RANDOM', 0);

/**
 * 
 * @link http://www.php.net/manual/en/mcrypt.constants.php
 * @var int
 */
define ('MCRYPT_DEV_URANDOM', 1);

/**
 * 
 * @link http://www.php.net/manual/en/mcrypt.constants.php
 * @var int
 */
define ('MCRYPT_RAND', 2);
define ('MCRYPT_3DES', "tripledes");
define ('MCRYPT_ARCFOUR_IV', "arcfour-iv");
define ('MCRYPT_ARCFOUR', "arcfour");
define ('MCRYPT_BLOWFISH', "blowfish");
define ('MCRYPT_BLOWFISH_COMPAT', "blowfish-compat");
define ('MCRYPT_CAST_128', "cast-128");
define ('MCRYPT_CAST_256', "cast-256");
define ('MCRYPT_CRYPT', "crypt");
define ('MCRYPT_DES', "des");
define ('MCRYPT_ENIGNA', "crypt");
define ('MCRYPT_GOST', "gost");
define ('MCRYPT_LOKI97', "loki97");
define ('MCRYPT_PANAMA', "panama");
define ('MCRYPT_RC2', "rc2");
define ('MCRYPT_RIJNDAEL_128', "rijndael-128");
define ('MCRYPT_RIJNDAEL_192', "rijndael-192");
define ('MCRYPT_RIJNDAEL_256', "rijndael-256");
define ('MCRYPT_SAFER64', "safer-sk64");
define ('MCRYPT_SAFER128', "safer-sk128");
define ('MCRYPT_SAFERPLUS', "saferplus");
define ('MCRYPT_SERPENT', "serpent");
define ('MCRYPT_THREEWAY', "threeway");
define ('MCRYPT_TRIPLEDES', "tripledes");
define ('MCRYPT_TWOFISH', "twofish");
define ('MCRYPT_WAKE', "wake");
define ('MCRYPT_XTEA', "xtea");
define ('MCRYPT_IDEA', "idea");
define ('MCRYPT_MARS', "mars");
define ('MCRYPT_RC6', "rc6");
define ('MCRYPT_SKIPJACK', "skipjack");
define ('MCRYPT_MODE_CBC', "cbc");
define ('MCRYPT_MODE_CFB', "cfb");
define ('MCRYPT_MODE_ECB', "ecb");
define ('MCRYPT_MODE_NOFB', "nofb");
define ('MCRYPT_MODE_OFB', "ofb");
define ('MCRYPT_MODE_STREAM', "stream");

// End of mcrypt v.1.0.6
