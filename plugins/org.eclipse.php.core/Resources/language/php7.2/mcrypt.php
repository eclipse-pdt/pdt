<?php

// Start of mcrypt v.7.1.1

/**
 * Gets the key size of the specified cipher
 * @link http://www.php.net/manual/en/function.mcrypt-get-key-size.php
 * @param int $cipher One of the MCRYPT_ciphername constants, or the name of the algorithm as string.
 * @return int the maximum supported key size of the algorithm in bytes
 * or false on failure.
 * @deprecated 
 */
function mcrypt_get_key_size (int $cipher) {}

/**
 * Gets the block size of the specified cipher
 * @link http://www.php.net/manual/en/function.mcrypt-get-block-size.php
 * @param int $cipher One of the MCRYPT_ciphername constants, or the name of the algorithm as string.
 * @return int the algorithm block size in bytes or false on failure.
 * @deprecated 
 */
function mcrypt_get_block_size (int $cipher) {}

/**
 * Gets the name of the specified cipher
 * @link http://www.php.net/manual/en/function.mcrypt-get-cipher-name.php
 * @param int $cipher One of the MCRYPT_ciphername constants, or the name of the algorithm as string.
 * @return string This function returns the name of the cipher or false if the cipher does
 * not exist.
 * @deprecated 
 */
function mcrypt_get_cipher_name (int $cipher) {}

/**
 * Creates an initialization vector (IV) from a random source
 * @link http://www.php.net/manual/en/function.mcrypt-create-iv.php
 * @param int $size The size of the IV.
 * @param int $source [optional] <p>
 * The source of the IV. The source can be
 * MCRYPT_RAND (system random number generator),
 * MCRYPT_DEV_RANDOM (read data from
 * /dev/random) and
 * MCRYPT_DEV_URANDOM (read data from
 * /dev/urandom). Prior to 5.3.0,
 * MCRYPT_RAND was the only one supported on Windows.
 * </p>
 * <p>
 * Note that the default value of this parameter was
 * MCRYPT_DEV_RANDOM prior to PHP 5.6.0.
 * </p>
 * Note that MCRYPT_DEV_RANDOM may block until more
 * entropy is available.
 * @return string the initialization vector, or false on error.
 * @deprecated 
 */
function mcrypt_create_iv (int $size, int $source = null) {}

/**
 * Gets an array of all supported ciphers
 * @link http://www.php.net/manual/en/function.mcrypt-list-algorithms.php
 * @param string $lib_dir [optional] Specifies the directory where all algorithms are located. If not
 * specified, the value of the mcrypt.algorithms_dir
 * php.ini directive is used.
 * @return array an array with all the supported algorithms.
 * @deprecated 
 */
function mcrypt_list_algorithms (string $lib_dir = null) {}

/**
 * Gets an array of all supported modes
 * @link http://www.php.net/manual/en/function.mcrypt-list-modes.php
 * @param string $lib_dir [optional] Specifies the directory where all modes are located. If not
 * specified, the value of the mcrypt.modes_dir
 * php.ini directive is used.
 * @return array an array with all the supported modes.
 * @deprecated 
 */
function mcrypt_list_modes (string $lib_dir = null) {}

/**
 * Returns the size of the IV belonging to a specific cipher/mode combination
 * @link http://www.php.net/manual/en/function.mcrypt-get-iv-size.php
 * @param string $cipher One of the MCRYPT_ciphername constants, or the name of the algorithm as string.
 * @param string $mode <p>One of the MCRYPT_MODE_modename constants, or one of the following strings: "ecb", "cbc", "cfb", "ofb", "nofb" or "stream".</p>
 * <p>
 * The IV is ignored in ECB mode as this mode does not require it. You will
 * need to have the same IV (think: starting point) both at encryption and
 * decryption stages, otherwise your encryption will fail.
 * </p>
 * @return int the size of the Initialization Vector (IV) in bytes. On error the
 * function returns false. If the IV is ignored in the specified cipher/mode
 * combination zero is returned.
 * @deprecated 
 */
function mcrypt_get_iv_size (string $cipher, string $mode) {}

/**
 * Encrypts plaintext with given parameters
 * @link http://www.php.net/manual/en/function.mcrypt-encrypt.php
 * @param string $cipher One of the MCRYPT_ciphername constants, or the name of the algorithm as string.
 * @param string $key The key with which the data will be encrypted. If the provided key size is
 * not supported by the cipher, the function will emit a warning and return false
 * @param string $data <p>
 * The data that will be encrypted with the given cipher
 * and mode. If the size of the data is not n &#42; blocksize,
 * the data will be padded with '\0'.
 * </p>
 * <p>
 * The returned crypttext can be larger than the size of the data that was
 * given by data.
 * </p>
 * @param string $mode One of the MCRYPT_MODE_modename constants, or one of the following strings: "ecb", "cbc", "cfb", "ofb", "nofb" or "stream".
 * @param string $iv [optional] Used for the initialization in CBC, CFB, OFB modes, and in some algorithms in STREAM mode. If the provided IV size is not supported by the chaining mode or no IV was provided, but the chaining mode requires one, the function will emit a warning and return false.
 * @return string the encrypted data as a string or false on failure.
 * @deprecated 
 */
function mcrypt_encrypt (string $cipher, string $key, string $data, string $mode, string $iv = null) {}

/**
 * Decrypts crypttext with given parameters
 * @link http://www.php.net/manual/en/function.mcrypt-decrypt.php
 * @param string $cipher One of the MCRYPT_ciphername constants, or the name of the algorithm as string.
 * @param string $key The key with which the data was encrypted. If the provided key size is
 * not supported by the cipher, the function will emit a warning and return false
 * @param string $data The data that will be decrypted with the given cipher
 * and mode. If the size of the data is not n &#42; blocksize,
 * the data will be padded with '\0'.
 * @param string $mode One of the MCRYPT_MODE_modename constants, or one of the following strings: "ecb", "cbc", "cfb", "ofb", "nofb" or "stream".
 * @param string $iv [optional] Used for the initialization in CBC, CFB, OFB modes, and in some algorithms in STREAM mode. If the provided IV size is not supported by the chaining mode or no IV was provided, but the chaining mode requires one, the function will emit a warning and return false.
 * @return string the decrypted data as a string or false on failure.
 * @deprecated 
 */
function mcrypt_decrypt (string $cipher, string $key, string $data, string $mode, string $iv = null) {}

/**
 * Opens the module of the algorithm and the mode to be used
 * @link http://www.php.net/manual/en/function.mcrypt-module-open.php
 * @param string $algorithm One of the MCRYPT_ciphername constants, or the name of the algorithm as string.
 * @param string $algorithm_directory The algorithm_directory parameter is used to locate
 * the encryption module. When you supply a directory name, it is used. When
 * you set it to an empty string (""), the value set by the
 * mcrypt.algorithms_dir php.ini directive is used. When
 * it is not set, the default directory that is used is the one that was compiled
 * into libmcrypt (usually /usr/local/lib/libmcrypt).
 * @param string $mode One of the MCRYPT_MODE_modename constants, or one of the following strings: "ecb", "cbc", "cfb", "ofb", "nofb" or "stream".
 * @param string $mode_directory The mode_directory parameter is used to locate
 * the encryption module. When you supply a directory name, it is used. When
 * you set it to an empty string (""), the value set by the
 * mcrypt.modes_dir php.ini directive is used. When
 * it is not set, the default directory that is used is the one that was compiled-in
 * into libmcrypt (usually /usr/local/lib/libmcrypt).
 * @return resource Normally it returns an encryption descriptor, or false on error.
 * @deprecated 
 */
function mcrypt_module_open (string $algorithm, string $algorithm_directory, string $mode, string $mode_directory) {}

/**
 * This function initializes all buffers needed for encryption
 * @link http://www.php.net/manual/en/function.mcrypt-generic-init.php
 * @param resource $td The encryption descriptor.
 * @param string $key The maximum length of the key should be the one obtained by calling
 * mcrypt_enc_get_key_size and every value smaller
 * than this is legal.
 * @param string $iv The IV should normally have the size of the algorithms block size, but
 * you must obtain the size by calling
 * mcrypt_enc_get_iv_size. IV is ignored in ECB. IV
 * MUST exist in CFB, CBC, STREAM, nOFB and OFB modes. It needs to be
 * random and unique (but not secret). The same IV must be used for
 * encryption/decryption. If you do not want to use it you should set it
 * to zeros, but this is not recommended.
 * @return int The function returns a negative value on error: -3 when the key length
 * was incorrect, -4 when there was a memory allocation problem and any
 * other return value is an unknown error. If an error occurs a warning will
 * be displayed accordingly. false is returned if incorrect parameters
 * were passed.
 * @deprecated 
 */
function mcrypt_generic_init ($td, string $key, string $iv) {}

/**
 * This function encrypts data
 * @link http://www.php.net/manual/en/function.mcrypt-generic.php
 * @param resource $td <p>
 * The encryption descriptor.
 * </p>
 * <p>
 * The encryption handle should always be initialized with
 * mcrypt_generic_init with a key and an IV before
 * calling this function. Where the encryption is done, you should free the
 * encryption buffers by calling mcrypt_generic_deinit.
 * See mcrypt_module_open for an example.
 * </p>
 * @param string $data The data to encrypt.
 * @return string the encrypted data.
 * @deprecated 
 */
function mcrypt_generic ($td, string $data) {}

/**
 * Decrypts data
 * @link http://www.php.net/manual/en/function.mdecrypt-generic.php
 * @param resource $td An encryption descriptor returned by
 * mcrypt_module_open
 * @param string $data Encrypted data.
 * @return string 
 * @deprecated 
 */
function mdecrypt_generic ($td, string $data) {}

/**
 * This function deinitializes an encryption module
 * @link http://www.php.net/manual/en/function.mcrypt-generic-deinit.php
 * @param resource $td The encryption descriptor.
 * @return bool true on success or false on failure
 * @deprecated 
 */
function mcrypt_generic_deinit ($td) {}

/**
 * Runs a self test on the opened module
 * @link http://www.php.net/manual/en/function.mcrypt-enc-self-test.php
 * @param resource $td The encryption descriptor.
 * @return int If the self test succeeds it returns false. In case of an error, it
 * returns true.
 * @deprecated 
 */
function mcrypt_enc_self_test ($td) {}

/**
 * Checks whether the encryption of the opened mode works on blocks
 * @link http://www.php.net/manual/en/function.mcrypt-enc-is-block-algorithm-mode.php
 * @param resource $td The encryption descriptor.
 * @return bool true if the mode is for use with block algorithms, otherwise it
 * returns false.
 * @deprecated 
 */
function mcrypt_enc_is_block_algorithm_mode ($td) {}

/**
 * Checks whether the algorithm of the opened mode is a block algorithm
 * @link http://www.php.net/manual/en/function.mcrypt-enc-is-block-algorithm.php
 * @param resource $td The encryption descriptor.
 * @return bool true if the algorithm is a block algorithm or false if it is
 * a stream one.
 * @deprecated 
 */
function mcrypt_enc_is_block_algorithm ($td) {}

/**
 * Checks whether the opened mode outputs blocks
 * @link http://www.php.net/manual/en/function.mcrypt-enc-is-block-mode.php
 * @param resource $td The encryption descriptor.
 * @return bool true if the mode outputs blocks of bytes,
 * or false if it outputs just bytes.
 * @deprecated 
 */
function mcrypt_enc_is_block_mode ($td) {}

/**
 * Returns the blocksize of the opened algorithm
 * @link http://www.php.net/manual/en/function.mcrypt-enc-get-block-size.php
 * @param resource $td The encryption descriptor.
 * @return int the block size of the specified algorithm in bytes.
 * @deprecated 
 */
function mcrypt_enc_get_block_size ($td) {}

/**
 * Returns the maximum supported keysize of the opened mode
 * @link http://www.php.net/manual/en/function.mcrypt-enc-get-key-size.php
 * @param resource $td The encryption descriptor.
 * @return int the maximum supported key size of the algorithm in bytes.
 * @deprecated 
 */
function mcrypt_enc_get_key_size ($td) {}

/**
 * Returns an array with the supported keysizes of the opened algorithm
 * @link http://www.php.net/manual/en/function.mcrypt-enc-get-supported-key-sizes.php
 * @param resource $td The encryption descriptor.
 * @return array an array with the key sizes supported by the algorithm
 * specified by the encryption descriptor. If it returns an empty
 * array then all key sizes between 1 and
 * mcrypt_enc_get_key_size are supported by the
 * algorithm.
 * @deprecated 
 */
function mcrypt_enc_get_supported_key_sizes ($td) {}

/**
 * Returns the size of the IV of the opened algorithm
 * @link http://www.php.net/manual/en/function.mcrypt-enc-get-iv-size.php
 * @param resource $td The encryption descriptor.
 * @return int the size of the IV, or 0 if the IV is ignored by the algorithm.
 * @deprecated 
 */
function mcrypt_enc_get_iv_size ($td) {}

/**
 * Returns the name of the opened algorithm
 * @link http://www.php.net/manual/en/function.mcrypt-enc-get-algorithms-name.php
 * @param resource $td The encryption descriptor.
 * @return string the name of the opened algorithm as a string.
 * @deprecated 
 */
function mcrypt_enc_get_algorithms_name ($td) {}

/**
 * Returns the name of the opened mode
 * @link http://www.php.net/manual/en/function.mcrypt-enc-get-modes-name.php
 * @param resource $td The encryption descriptor.
 * @return string the name as a string.
 * @deprecated 
 */
function mcrypt_enc_get_modes_name ($td) {}

/**
 * This function runs a self test on the specified module
 * @link http://www.php.net/manual/en/function.mcrypt-module-self-test.php
 * @param string $algorithm One of the MCRYPT_ciphername constants, or the name of the algorithm as string.
 * @param string $lib_dir [optional] The optional lib_dir parameter can contain the
 * location where the algorithm module is on the system.
 * @return bool The function returns true if the self test succeeds, or false when it
 * fails.
 * @deprecated 
 */
function mcrypt_module_self_test (string $algorithm, string $lib_dir = null) {}

/**
 * Returns if the specified module is a block algorithm or not
 * @link http://www.php.net/manual/en/function.mcrypt-module-is-block-algorithm-mode.php
 * @param string $mode The mode to check.
 * @param string $lib_dir [optional] The optional lib_dir parameter can contain the
 * location where the algorithm module is on the system.
 * @return bool This function returns true if the mode is for use with block
 * algorithms, otherwise it returns false. (e.g. false for stream, and
 * true for cbc, cfb, ofb).
 * @deprecated 
 */
function mcrypt_module_is_block_algorithm_mode (string $mode, string $lib_dir = null) {}

/**
 * This function checks whether the specified algorithm is a block algorithm
 * @link http://www.php.net/manual/en/function.mcrypt-module-is-block-algorithm.php
 * @param string $algorithm The algorithm to check.
 * @param string $lib_dir [optional] The optional lib_dir parameter can contain the
 * location where the algorithm module is on the system.
 * @return bool This function returns true if the specified algorithm is a block
 * algorithm, or false if it is a stream one.
 * @deprecated 
 */
function mcrypt_module_is_block_algorithm (string $algorithm, string $lib_dir = null) {}

/**
 * Returns if the specified mode outputs blocks or not
 * @link http://www.php.net/manual/en/function.mcrypt-module-is-block-mode.php
 * @param string $mode One of the MCRYPT_MODE_modename constants, or one of the following strings: "ecb", "cbc", "cfb", "ofb", "nofb" or "stream".
 * @param string $lib_dir [optional] The optional lib_dir parameter can contain the
 * location where the algorithm module is on the system.
 * @return bool This function returns true if the mode outputs blocks of bytes or
 * false if it outputs just bytes. (e.g. true for cbc and ecb, and
 * false for cfb and stream).
 * @deprecated 
 */
function mcrypt_module_is_block_mode (string $mode, string $lib_dir = null) {}

/**
 * Returns the blocksize of the specified algorithm
 * @link http://www.php.net/manual/en/function.mcrypt-module-get-algo-block-size.php
 * @param string $algorithm The algorithm name.
 * @param string $lib_dir [optional] This optional parameter can contain the location where the mode module
 * is on the system.
 * @return int the block size of the algorithm specified in bytes.
 * @deprecated 
 */
function mcrypt_module_get_algo_block_size (string $algorithm, string $lib_dir = null) {}

/**
 * Returns the maximum supported keysize of the opened mode
 * @link http://www.php.net/manual/en/function.mcrypt-module-get-algo-key-size.php
 * @param string $algorithm The algorithm name.
 * @param string $lib_dir [optional] This optional parameter can contain the location where the mode module
 * is on the system.
 * @return int This function returns the maximum supported key size of the
 * algorithm specified in bytes.
 * @deprecated 
 */
function mcrypt_module_get_algo_key_size (string $algorithm, string $lib_dir = null) {}

/**
 * Returns an array with the supported keysizes of the opened algorithm
 * @link http://www.php.net/manual/en/function.mcrypt-module-get-supported-key-sizes.php
 * @param string $algorithm The algorithm to be used.
 * @param string $lib_dir [optional] The optional lib_dir parameter can contain the
 * location where the algorithm module is on the system.
 * @return array an array with the key sizes supported by the specified algorithm.
 * If it returns an empty array then all key sizes between 1 and
 * mcrypt_module_get_algo_key_size are supported by the
 * algorithm.
 * @deprecated 
 */
function mcrypt_module_get_supported_key_sizes (string $algorithm, string $lib_dir = null) {}

/**
 * Closes the mcrypt module
 * @link http://www.php.net/manual/en/function.mcrypt-module-close.php
 * @param resource $td The encryption descriptor.
 * @return bool true on success or false on failure
 * @deprecated 
 */
function mcrypt_module_close ($td) {}


/**
 * 
 * @link http://www.php.net/manual/en/mcrypt.constants.php
 */
define ('MCRYPT_ENCRYPT', 0);

/**
 * 
 * @link http://www.php.net/manual/en/mcrypt.constants.php
 */
define ('MCRYPT_DECRYPT', 1);

/**
 * 
 * @link http://www.php.net/manual/en/mcrypt.constants.php
 */
define ('MCRYPT_DEV_RANDOM', 0);

/**
 * 
 * @link http://www.php.net/manual/en/mcrypt.constants.php
 */
define ('MCRYPT_DEV_URANDOM', 1);

/**
 * 
 * @link http://www.php.net/manual/en/mcrypt.constants.php
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

// End of mcrypt v.7.1.1
