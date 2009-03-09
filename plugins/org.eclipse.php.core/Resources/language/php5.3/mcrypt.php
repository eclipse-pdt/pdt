<?php

// Start of mcrypt v.

/**
 * Deprecated: Encrypt/decrypt data in ECB mode
 * @link http://php.net/manual/en/function.mcrypt-ecb.php
 * @param cipher int 
 * @param key string 
 * @param data string 
 * @param mode int 
 * @return string 
 */
function mcrypt_ecb ($cipher, $key, $data, $mode) {}

/**
 * Encrypt/decrypt data in CBC mode
 * @link http://php.net/manual/en/function.mcrypt-cbc.php
 * @param cipher int 
 * @param key string 
 * @param data string 
 * @param mode int 
 * @param iv string[optional] 
 * @return string 
 */
function mcrypt_cbc ($cipher, $key, $data, $mode, $iv = null) {}

/**
 * Encrypt/decrypt data in CFB mode
 * @link http://php.net/manual/en/function.mcrypt-cfb.php
 * @param cipher int 
 * @param key string 
 * @param data string 
 * @param mode int 
 * @param iv string 
 * @return string 
 */
function mcrypt_cfb ($cipher, $key, $data, $mode, $iv) {}

/**
 * Encrypt/decrypt data in OFB mode
 * @link http://php.net/manual/en/function.mcrypt-ofb.php
 * @param cipher int 
 * @param key string 
 * @param data string 
 * @param mode int 
 * @param iv string 
 * @return string 
 */
function mcrypt_ofb ($cipher, $key, $data, $mode, $iv) {}

/**
 * Get the key size of the specified cipher
 * @link http://php.net/manual/en/function.mcrypt-get-key-size.php
 * @param cipher int 
 * @return int 
 */
function mcrypt_get_key_size ($cipher) {}

/**
 * Get the block size of the specified cipher
 * @link http://php.net/manual/en/function.mcrypt-get-block-size.php
 * @param cipher int 
 * @return int 
 */
function mcrypt_get_block_size ($cipher) {}

/**
 * Get the name of the specified cipher
 * @link http://php.net/manual/en/function.mcrypt-get-cipher-name.php
 * @param cipher int 
 * @return string 
 */
function mcrypt_get_cipher_name ($cipher) {}

/**
 * Create an initialization vector (IV) from a random source
 * @link http://php.net/manual/en/function.mcrypt-create-iv.php
 * @param size int 
 * @param source int[optional] 
 * @return string 
 */
function mcrypt_create_iv ($size, $source = null) {}

/**
 * Get an array of all supported ciphers
 * @link http://php.net/manual/en/function.mcrypt-list-algorithms.php
 * @param lib_dir string[optional] 
 * @return array 
 */
function mcrypt_list_algorithms ($lib_dir = null) {}

/**
 * Get an array of all supported modes
 * @link http://php.net/manual/en/function.mcrypt-list-modes.php
 * @param lib_dir string[optional] 
 * @return array 
 */
function mcrypt_list_modes ($lib_dir = null) {}

/**
 * Returns the size of the IV belonging to a specific cipher/mode combination
 * @link http://php.net/manual/en/function.mcrypt-get-iv-size.php
 * @param cipher string 
 * @param mode string 
 * @return int 
 */
function mcrypt_get_iv_size ($cipher, $mode) {}

/**
 * Encrypts plaintext with given parameters
 * @link http://php.net/manual/en/function.mcrypt-encrypt.php
 * @param cipher string 
 * @param key string 
 * @param data string 
 * @param mode string 
 * @param iv string[optional] 
 * @return string 
 */
function mcrypt_encrypt ($cipher, $key, $data, $mode, $iv = null) {}

/**
 * Decrypts crypttext with given parameters
 * @link http://php.net/manual/en/function.mcrypt-decrypt.php
 * @param cipher string <p>
 * cipher is one of the MCRYPT_ciphername constants
 * of the name of the algorithm as string.
 * </p>
 * @param key string <p>
 * key is the key with which the data is encrypted.
 * If it's smaller that the required keysize, it is padded with
 * '\0'.
 * </p>
 * @param data string <p>
 * data is the data that will be decrypted with
 * the given cipher and mode. If the size of the data is not n * blocksize,
 * the data will be padded with '\0'.
 * </p>
 * @param mode string <p>
 * mode is one of the MCRYPT_MODE_modename
 * constants of one of "ecb", "cbc", "cfb", "ofb", "nofb" or "stream".
 * </p>
 * @param iv string[optional] <p>
 * The iv parameter is used for the initialisation
 * in CBC, CFB, OFB modes, and in some algorithms in STREAM mode. If you
 * do not supply an IV, while it is needed for an algorithm, the function
 * issues a warning and uses an IV with all bytes set to
 * '\0'.
 * </p>
 * @return string the decrypted data as a string.
 */
function mcrypt_decrypt ($cipher, $key, $data, $mode, $iv = null) {}

/**
 * Opens the module of the algorithm and the mode to be used
 * @link http://php.net/manual/en/function.mcrypt-module-open.php
 * @param algorithm string 
 * @param algorithm_directory string 
 * @param mode string 
 * @param mode_directory string 
 * @return resource 
 */
function mcrypt_module_open ($algorithm, $algorithm_directory, $mode, $mode_directory) {}

/**
 * This function initializes all buffers needed for encryption
 * @link http://php.net/manual/en/function.mcrypt-generic-init.php
 * @param td resource <p>
 * The encryption descriptor.
 * </p>
 * @param key string <p>
 * The maximum length of the key should be the one obtained by calling
 * mcrypt_enc_get_key_size and every value smaller
 * than this is legal.
 * </p>
 * @param iv string <p>
 * The IV should normally have the size of the algorithms block size, but
 * you must obtain the size by calling
 * mcrypt_enc_get_iv_size. IV is ignored in ECB. IV
 * MUST exist in CFB, CBC, STREAM, nOFB and OFB modes. It needs to be
 * random and unique (but not secret). The same IV must be used for
 * encryption/decryption. If you do not want to use it you should set it
 * to zeros, but this is not recommended.
 * </p>
 * @return int The function returns a negative value on error, -3 when the key length
 * was incorrect, -4 when there was a memory allocation problem and any
 * other return value is an unknown error. If an error occurs a warning will
 * be displayed accordingly. false is returned if incorrect parameters
 * were passed.
 */
function mcrypt_generic_init ($td, $key, $iv) {}

/**
 * This function encrypts data
 * @link http://php.net/manual/en/function.mcrypt-generic.php
 * @param td resource 
 * @param data string 
 * @return string 
 */
function mcrypt_generic ($td, $data) {}

/**
 * Decrypt data
 * @link http://php.net/manual/en/function.mdecrypt-generic.php
 * @param td resource 
 * @param data string 
 * @return string 
 */
function mdecrypt_generic ($td, $data) {}

/**
 * This function terminates encryption
 * @link http://php.net/manual/en/function.mcrypt-generic-end.php
 * @param td resource 
 * @return bool 
 */
function mcrypt_generic_end ($td) {}

/**
 * This function deinitializes an encryption module
 * @link http://php.net/manual/en/function.mcrypt-generic-deinit.php
 * @param td resource 
 * @return bool 
 */
function mcrypt_generic_deinit ($td) {}

/**
 * Runs a self test on the opened module
 * @link http://php.net/manual/en/function.mcrypt-enc-self-test.php
 * @param td resource <p>
 * The encryption descriptor.
 * </p>
 * @return int If the self test succeeds it returns false. In case of an error, it
 * returns true.
 */
function mcrypt_enc_self_test ($td) {}

/**
 * Checks whether the encryption of the opened mode works on blocks
 * @link http://php.net/manual/en/function.mcrypt-enc-is-block-algorithm-mode.php
 * @param td resource <p>
 * The encryption descriptor.
 * </p>
 * @return bool true if the mode is for use with block algorithms, otherwise it
 * returns false.
 */
function mcrypt_enc_is_block_algorithm_mode ($td) {}

/**
 * Checks whether the algorithm of the opened mode is a block algorithm
 * @link http://php.net/manual/en/function.mcrypt-enc-is-block-algorithm.php
 * @param td resource <p>
 * The encryption descriptor.
 * </p>
 * @return bool true if the algorithm is a block algorithm or false if it is
 * a stream one.
 */
function mcrypt_enc_is_block_algorithm ($td) {}

/**
 * Checks whether the opened mode outputs blocks
 * @link http://php.net/manual/en/function.mcrypt-enc-is-block-mode.php
 * @param td resource <p>
 * The encryption descriptor.
 * </p>
 * @return bool true if the mode outputs blocks of bytes or false if it outputs bytes.
 */
function mcrypt_enc_is_block_mode ($td) {}

/**
 * Returns the blocksize of the opened algorithm
 * @link http://php.net/manual/en/function.mcrypt-enc-get-block-size.php
 * @param td resource <p>
 * The encryption descriptor.
 * </p>
 * @return int the block size of the specified algorithm in bytes.
 */
function mcrypt_enc_get_block_size ($td) {}

/**
 * Returns the maximum supported keysize of the opened mode
 * @link http://php.net/manual/en/function.mcrypt-enc-get-key-size.php
 * @param td resource <p>
 * The encryption descriptor.
 * </p>
 * @return int the maximum supported key size of the algorithm in bytes.
 */
function mcrypt_enc_get_key_size ($td) {}

/**
 * Returns an array with the supported keysizes of the opened algorithm
 * @link http://php.net/manual/en/function.mcrypt-enc-get-supported-key-sizes.php
 * @param td resource 
 * @return array 
 */
function mcrypt_enc_get_supported_key_sizes ($td) {}

/**
 * Returns the size of the IV of the opened algorithm
 * @link http://php.net/manual/en/function.mcrypt-enc-get-iv-size.php
 * @param td resource <p>
 * The encryption descriptor.
 * </p>
 * @return int the size of the IV, or 0 if the IV is ignored in the algorithm.
 */
function mcrypt_enc_get_iv_size ($td) {}

/**
 * Returns the name of the opened algorithm
 * @link http://php.net/manual/en/function.mcrypt-enc-get-algorithms-name.php
 * @param td resource 
 * @return string 
 */
function mcrypt_enc_get_algorithms_name ($td) {}

/**
 * Returns the name of the opened mode
 * @link http://php.net/manual/en/function.mcrypt-enc-get-modes-name.php
 * @param td resource <p>
 * The encryption descriptor.
 * </p>
 * @return string the name as a string.
 */
function mcrypt_enc_get_modes_name ($td) {}

/**
 * This function runs a self test on the specified module
 * @link http://php.net/manual/en/function.mcrypt-module-self-test.php
 * @param algorithm string 
 * @param lib_dir string[optional] 
 * @return bool 
 */
function mcrypt_module_self_test ($algorithm, $lib_dir = null) {}

/**
 * Returns if the specified module is a block algorithm or not
 * @link http://php.net/manual/en/function.mcrypt-module-is-block-algorithm-mode.php
 * @param mode string 
 * @param lib_dir string[optional] 
 * @return bool 
 */
function mcrypt_module_is_block_algorithm_mode ($mode, $lib_dir = null) {}

/**
 * This function checks whether the specified algorithm is a block algorithm
 * @link http://php.net/manual/en/function.mcrypt-module-is-block-algorithm.php
 * @param algorithm string 
 * @param lib_dir string[optional] 
 * @return bool 
 */
function mcrypt_module_is_block_algorithm ($algorithm, $lib_dir = null) {}

/**
 * Returns if the specified mode outputs blocks or not
 * @link http://php.net/manual/en/function.mcrypt-module-is-block-mode.php
 * @param mode string 
 * @param lib_dir string[optional] 
 * @return bool 
 */
function mcrypt_module_is_block_mode ($mode, $lib_dir = null) {}

/**
 * Returns the blocksize of the specified algorithm
 * @link http://php.net/manual/en/function.mcrypt-module-get-algo-block-size.php
 * @param algorithm string <p>
 * The algorithm name.
 * </p>
 * @param lib_dir string[optional] <p>
 * This optional parameter can contain the location where the mode module
 * is on the system.
 * </p>
 * @return int the block size of the algorithm specified in bytes.
 */
function mcrypt_module_get_algo_block_size ($algorithm, $lib_dir = null) {}

/**
 * Returns the maximum supported keysize of the opened mode
 * @link http://php.net/manual/en/function.mcrypt-module-get-algo-key-size.php
 * @param algorithm string <p>
 * The algorithm name.
 * </p>
 * @param lib_dir string[optional] <p>
 * This optional parameter can contain the location where the mode module
 * is on the system.
 * </p>
 * @return int This function returns the maximum supported key size of the
 * algorithm specified in bytes.
 */
function mcrypt_module_get_algo_key_size ($algorithm, $lib_dir = null) {}

/**
 * Returns an array with the supported keysizes of the opened algorithm
 * @link http://php.net/manual/en/function.mcrypt-module-get-supported-key-sizes.php
 * @param algorithm string 
 * @param lib_dir string[optional] 
 * @return array 
 */
function mcrypt_module_get_supported_key_sizes ($algorithm, $lib_dir = null) {}

/**
 * Close the mcrypt module
 * @link http://php.net/manual/en/function.mcrypt-module-close.php
 * @param td resource <p>
 * The encryption descriptor.
 * </p>
 * @return bool Returns true on success or false on failure.
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
