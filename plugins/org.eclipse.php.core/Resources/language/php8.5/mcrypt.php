<?php

// Start of mcrypt v.1.0.6

/**
 * {@inheritdoc}
 * @param mixed $cipher
 * @param mixed $module
 * @deprecated 
 */
function mcrypt_get_key_size ($cipher = null, $module = null) {}

/**
 * {@inheritdoc}
 * @param mixed $cipher
 * @param mixed $module
 * @deprecated 
 */
function mcrypt_get_block_size ($cipher = null, $module = null) {}

/**
 * {@inheritdoc}
 * @param mixed $cipher
 * @deprecated 
 */
function mcrypt_get_cipher_name ($cipher = null) {}

/**
 * {@inheritdoc}
 * @param mixed $size
 * @param mixed $source [optional]
 * @deprecated 
 */
function mcrypt_create_iv ($size = null, $source = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $lib_dir [optional]
 * @deprecated 
 */
function mcrypt_list_algorithms ($lib_dir = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $lib_dir [optional]
 * @deprecated 
 */
function mcrypt_list_modes ($lib_dir = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $cipher
 * @param mixed $module
 * @deprecated 
 */
function mcrypt_get_iv_size ($cipher = null, $module = null) {}

/**
 * {@inheritdoc}
 * @param mixed $cipher
 * @param mixed $key
 * @param mixed $data
 * @param mixed $mode
 * @param mixed $iv [optional]
 * @deprecated 
 */
function mcrypt_encrypt ($cipher = null, $key = null, $data = null, $mode = null, $iv = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $cipher
 * @param mixed $key
 * @param mixed $data
 * @param mixed $mode
 * @param mixed $iv [optional]
 * @deprecated 
 */
function mcrypt_decrypt ($cipher = null, $key = null, $data = null, $mode = null, $iv = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $cipher
 * @param mixed $cipher_directory
 * @param mixed $mode
 * @param mixed $mode_directory
 * @deprecated 
 */
function mcrypt_module_open ($cipher = null, $cipher_directory = null, $mode = null, $mode_directory = null) {}

/**
 * {@inheritdoc}
 * @param mixed $td
 * @param mixed $key
 * @param mixed $iv
 * @deprecated 
 */
function mcrypt_generic_init ($td = null, $key = null, $iv = null) {}

/**
 * {@inheritdoc}
 * @param mixed $td
 * @param mixed $data
 * @deprecated 
 */
function mcrypt_generic ($td = null, $data = null) {}

/**
 * {@inheritdoc}
 * @param mixed $td
 * @param mixed $data
 * @deprecated 
 */
function mdecrypt_generic ($td = null, $data = null) {}

/**
 * {@inheritdoc}
 * @param mixed $td
 * @deprecated 
 */
function mcrypt_generic_deinit ($td = null) {}

/**
 * {@inheritdoc}
 * @param mixed $td
 * @deprecated 
 */
function mcrypt_enc_self_test ($td = null) {}

/**
 * {@inheritdoc}
 * @param mixed $td
 * @deprecated 
 */
function mcrypt_enc_is_block_algorithm_mode ($td = null) {}

/**
 * {@inheritdoc}
 * @param mixed $td
 * @deprecated 
 */
function mcrypt_enc_is_block_algorithm ($td = null) {}

/**
 * {@inheritdoc}
 * @param mixed $td
 * @deprecated 
 */
function mcrypt_enc_is_block_mode ($td = null) {}

/**
 * {@inheritdoc}
 * @param mixed $td
 * @deprecated 
 */
function mcrypt_enc_get_block_size ($td = null) {}

/**
 * {@inheritdoc}
 * @param mixed $td
 * @deprecated 
 */
function mcrypt_enc_get_key_size ($td = null) {}

/**
 * {@inheritdoc}
 * @param mixed $td
 * @deprecated 
 */
function mcrypt_enc_get_supported_key_sizes ($td = null) {}

/**
 * {@inheritdoc}
 * @param mixed $td
 * @deprecated 
 */
function mcrypt_enc_get_iv_size ($td = null) {}

/**
 * {@inheritdoc}
 * @param mixed $td
 * @deprecated 
 */
function mcrypt_enc_get_algorithms_name ($td = null) {}

/**
 * {@inheritdoc}
 * @param mixed $td
 * @deprecated 
 */
function mcrypt_enc_get_modes_name ($td = null) {}

/**
 * {@inheritdoc}
 * @param mixed $algorithm
 * @param mixed $lib_dir [optional]
 * @deprecated 
 */
function mcrypt_module_self_test ($algorithm = null, $lib_dir = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $mode
 * @param mixed $lib_dir [optional]
 * @deprecated 
 */
function mcrypt_module_is_block_algorithm_mode ($mode = null, $lib_dir = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $algorithm
 * @param mixed $lib_dir [optional]
 * @deprecated 
 */
function mcrypt_module_is_block_algorithm ($algorithm = null, $lib_dir = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $mode
 * @param mixed $lib_dir [optional]
 * @deprecated 
 */
function mcrypt_module_is_block_mode ($mode = null, $lib_dir = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $algorithm
 * @param mixed $lib_dir [optional]
 * @deprecated 
 */
function mcrypt_module_get_algo_block_size ($algorithm = null, $lib_dir = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $algorithm
 * @param mixed $lib_dir [optional]
 * @deprecated 
 */
function mcrypt_module_get_algo_key_size ($algorithm = null, $lib_dir = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $algorithm
 * @param mixed $lib_dir [optional]
 * @deprecated 
 */
function mcrypt_module_get_supported_key_sizes ($algorithm = null, $lib_dir = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $td
 * @deprecated 
 */
function mcrypt_module_close ($td = null) {}

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

// End of mcrypt v.1.0.6
