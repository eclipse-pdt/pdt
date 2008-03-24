<?php

// Start of mcrypt v.

/**
 * Deprecated: Encrypt/decrypt data in ECB mode
 * @link http://php.net/manual/en/function.mcrypt-ecb.php
 */
function mcrypt_ecb () {}

/**
 * Encrypt/decrypt data in CBC mode
 * @link http://php.net/manual/en/function.mcrypt-cbc.php
 */
function mcrypt_cbc () {}

/**
 * Encrypt/decrypt data in CFB mode
 * @link http://php.net/manual/en/function.mcrypt-cfb.php
 */
function mcrypt_cfb () {}

/**
 * Encrypt/decrypt data in OFB mode
 * @link http://php.net/manual/en/function.mcrypt-ofb.php
 */
function mcrypt_ofb () {}

/**
 * Get the key size of the specified cipher
 * @link http://php.net/manual/en/function.mcrypt-get-key-size.php
 */
function mcrypt_get_key_size () {}

/**
 * Get the block size of the specified cipher
 * @link http://php.net/manual/en/function.mcrypt-get-block-size.php
 */
function mcrypt_get_block_size () {}

/**
 * Get the name of the specified cipher
 * @link http://php.net/manual/en/function.mcrypt-get-cipher-name.php
 */
function mcrypt_get_cipher_name () {}

/**
 * Create an initialization vector (IV) from a random source
 * @link http://php.net/manual/en/function.mcrypt-create-iv.php
 */
function mcrypt_create_iv () {}

/**
 * Get an array of all supported ciphers
 * @link http://php.net/manual/en/function.mcrypt-list-algorithms.php
 */
function mcrypt_list_algorithms () {}

/**
 * Get an array of all supported modes
 * @link http://php.net/manual/en/function.mcrypt-list-modes.php
 */
function mcrypt_list_modes () {}

/**
 * Returns the size of the IV belonging to a specific cipher/mode combination
 * @link http://php.net/manual/en/function.mcrypt-get-iv-size.php
 */
function mcrypt_get_iv_size () {}

/**
 * Encrypts plaintext with given parameters
 * @link http://php.net/manual/en/function.mcrypt-encrypt.php
 */
function mcrypt_encrypt () {}

/**
 * Decrypts crypttext with given parameters
 * @link http://php.net/manual/en/function.mcrypt-decrypt.php
 * @param cipher string
 * @param key string
 * @param data string
 * @param mode string
 * @param iv string[optional]
 * @return string the decrypted data as a string.
 */
function mcrypt_decrypt ($cipher, $key, $data, $mode, $iv = null) {}

/**
 * Opens the module of the algorithm and the mode to be used
 * @link http://php.net/manual/en/function.mcrypt-module-open.php
 */
function mcrypt_module_open () {}

/**
 * This function initializes all buffers needed for encryption
 * @link http://php.net/manual/en/function.mcrypt-generic-init.php
 * @param td resource
 * @param key string
 * @param iv string
 * @return int 
 */
function mcrypt_generic_init ($td, $key, $iv) {}

/**
 * This function encrypts data
 * @link http://php.net/manual/en/function.mcrypt-generic.php
 */
function mcrypt_generic () {}

/**
 * Decrypt data
 * @link http://php.net/manual/en/function.mdecrypt-generic.php
 */
function mdecrypt_generic () {}

/**
 * This function terminates encryption
 * @link http://php.net/manual/en/function.mcrypt-generic-end.php
 */
function mcrypt_generic_end () {}

/**
 * This function deinitializes an encryption module
 * @link http://php.net/manual/en/function.mcrypt-generic-deinit.php
 */
function mcrypt_generic_deinit () {}

/**
 * Runs a self test on the opened module
 * @link http://php.net/manual/en/function.mcrypt-enc-self-test.php
 * @param td resource
 * @return int 
 */
function mcrypt_enc_self_test ($td) {}

/**
 * Checks whether the encryption of the opened mode works on blocks
 * @link http://php.net/manual/en/function.mcrypt-enc-is-block-algorithm-mode.php
 * @param td resource
 * @return bool true if the mode is for use with block algorithms, otherwise it
 */
function mcrypt_enc_is_block_algorithm_mode ($td) {}

/**
 * Checks whether the algorithm of the opened mode is a block algorithm
 * @link http://php.net/manual/en/function.mcrypt-enc-is-block-algorithm.php
 * @param td resource
 * @return bool true if the algorithm is a block algorithm or false if it is
 */
function mcrypt_enc_is_block_algorithm ($td) {}

/**
 * Checks whether the opened mode outputs blocks
 * @link http://php.net/manual/en/function.mcrypt-enc-is-block-mode.php
 * @param td resource
 * @return bool true if the mode outputs blocks of bytes or false if it outputs bytes.
 */
function mcrypt_enc_is_block_mode ($td) {}

/**
 * Returns the blocksize of the opened algorithm
 * @link http://php.net/manual/en/function.mcrypt-enc-get-block-size.php
 * @param td resource
 * @return int the block size of the specified algorithm in bytes.
 */
function mcrypt_enc_get_block_size ($td) {}

/**
 * Returns the maximum supported keysize of the opened mode
 * @link http://php.net/manual/en/function.mcrypt-enc-get-key-size.php
 * @param td resource
 * @return int the maximum supported key size of the algorithm in bytes.
 */
function mcrypt_enc_get_key_size ($td) {}

/**
 * Returns an array with the supported keysizes of the opened algorithm
 * @link http://php.net/manual/en/function.mcrypt-enc-get-supported-key-sizes.php
 */
function mcrypt_enc_get_supported_key_sizes () {}

/**
 * Returns the size of the IV of the opened algorithm
 * @link http://php.net/manual/en/function.mcrypt-enc-get-iv-size.php
 * @param td resource
 * @return int the size of the IV, or 0 if the IV is ignored in the algorithm.
 */
function mcrypt_enc_get_iv_size ($td) {}

/**
 * Returns the name of the opened algorithm
 * @link http://php.net/manual/en/function.mcrypt-enc-get-algorithms-name.php
 */
function mcrypt_enc_get_algorithms_name () {}

/**
 * Returns the name of the opened mode
 * @link http://php.net/manual/en/function.mcrypt-enc-get-modes-name.php
 * @param td resource
 * @return string the name as a string.
 */
function mcrypt_enc_get_modes_name ($td) {}

/**
 * This function runs a self test on the specified module
 * @link http://php.net/manual/en/function.mcrypt-module-self-test.php
 */
function mcrypt_module_self_test () {}

/**
 * Returns if the specified module is a block algorithm or not
 * @link http://php.net/manual/en/function.mcrypt-module-is-block-algorithm-mode.php
 */
function mcrypt_module_is_block_algorithm_mode () {}

/**
 * This function checks whether the specified algorithm is a block algorithm
 * @link http://php.net/manual/en/function.mcrypt-module-is-block-algorithm.php
 */
function mcrypt_module_is_block_algorithm () {}

/**
 * Returns if the specified mode outputs blocks or not
 * @link http://php.net/manual/en/function.mcrypt-module-is-block-mode.php
 */
function mcrypt_module_is_block_mode () {}

/**
 * Returns the blocksize of the specified algorithm
 * @link http://php.net/manual/en/function.mcrypt-module-get-algo-block-size.php
 * @param algorithm string
 * @param lib_dir string[optional]
 * @return int the block size of the algorithm specified in bytes.
 */
function mcrypt_module_get_algo_block_size ($algorithm, $lib_dir = null) {}

/**
 * Returns the maximum supported keysize of the opened mode
 * @link http://php.net/manual/en/function.mcrypt-module-get-algo-key-size.php
 * @param algorithm string
 * @param lib_dir string[optional]
 * @return int 
 */
function mcrypt_module_get_algo_key_size ($algorithm, $lib_dir = null) {}

/**
 * Returns an array with the supported keysizes of the opened algorithm
 * @link http://php.net/manual/en/function.mcrypt-module-get-supported-key-sizes.php
 */
function mcrypt_module_get_supported_key_sizes () {}

/**
 * Close the mcrypt module
 * @link http://php.net/manual/en/function.mcrypt-module-close.php
 * @param td resource
 * @return bool 
 */
function mcrypt_module_close ($td) {}

define ('MCRYPT_ENCRYPT', 0);
define ('MCRYPT_DECRYPT', 1);
define ('MCRYPT_DEV_RANDOM', 0);
define ('MCRYPT_DEV_URANDOM', 1);
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

// End of mcrypt v.
?>
