<?php

// Start of openssl v.

/**
 * Frees a private key
 * @link http://php.net/manual/en/function.openssl-pkey-free.php
 * @param key resource
 * @return void 
 */
function openssl_pkey_free ($key) {}

/**
 * Generates a new private key
 * @link http://php.net/manual/en/function.openssl-pkey-new.php
 * @param configargs array[optional]
 * @return resource a resource identifier for the pkey on success, or false on
 */
function openssl_pkey_new (array $configargs = null) {}

/**
 * Gets an exportable representation of a key into a string
 * @link http://php.net/manual/en/function.openssl-pkey-export.php
 * @param key mixed
 * @param out string
 * @param passphrase string[optional]
 * @param configargs array[optional]
 * @return bool 
 */
function openssl_pkey_export ($key, &$out, $passphrase = null, array $configargs = null) {}

/**
 * Gets an exportable representation of a key into a file
 * @link http://php.net/manual/en/function.openssl-pkey-export-to-file.php
 * @param key mixed
 * @param outfilename string
 * @param passphrase string[optional]
 * @param configargs array[optional]
 * @return bool 
 */
function openssl_pkey_export_to_file ($key, $outfilename, $passphrase = null, array $configargs = null) {}

/**
 * Get a private key
 * @link http://php.net/manual/en/function.openssl-pkey-get-private.php
 * @param key mixed
 * @param passphrase string[optional]
 * @return resource a positive key resource identifier on success, or false on error.
 */
function openssl_pkey_get_private ($key, $passphrase = null) {}

/**
 * Extract public key from certificate and prepare it for use
 * @link http://php.net/manual/en/function.openssl-pkey-get-public.php
 * @param certificate mixed
 * @return resource a positive key resource identifier on success, or false on error.
 */
function openssl_pkey_get_public ($certificate) {}

/**
 * Returns an array with the key details
 * @link http://php.net/manual/en/function.openssl-pkey-get-details.php
 * @param key resource
 * @return array an array with the key details in success or false in failure.
 */
function openssl_pkey_get_details ($key) {}

/**
 * Free key resource
 * @link http://php.net/manual/en/function.openssl-free-key.php
 * @param key_identifier resource
 * @return void 
 */
function openssl_free_key ($key_identifier) {}

/**
 * &Alias; <function>openssl_pkey_get_private</function>
 * @link http://php.net/manual/en/function.openssl-get-privatekey.php
 */
function openssl_get_privatekey () {}

/**
 * &Alias; <function>openssl_pkey_get_public</function>
 * @link http://php.net/manual/en/function.openssl-get-publickey.php
 */
function openssl_get_publickey () {}

/**
 * Parse an X.509 certificate and return a resource identifier for
  it
 * @link http://php.net/manual/en/function.openssl-x509-read.php
 * @param x509certdata mixed
 * @return resource a resource identifier on success, or false on failure.
 */
function openssl_x509_read ($x509certdata) {}

/**
 * Free certificate resource
 * @link http://php.net/manual/en/function.openssl-x509-free.php
 * @param x509cert resource
 * @return void 
 */
function openssl_x509_free ($x509cert) {}

/**
 * Parse an X509 certificate and return the information as an array
 * @link http://php.net/manual/en/function.openssl-x509-parse.php
 * @param x509cert mixed
 * @param shortnames bool[optional]
 * @return array 
 */
function openssl_x509_parse ($x509cert, $shortnames = null) {}

/**
 * Verifies if a certificate can be used for a particular purpose
 * @link http://php.net/manual/en/function.openssl-x509-checkpurpose.php
 * @param x509cert mixed
 * @param purpose int
 * @param cainfo array[optional]
 * @param untrustedfile string[optional]
 * @return int true if the certificate can be used for the intended purpose,
 */
function openssl_x509_checkpurpose ($x509cert, $purpose, array $cainfo = null, $untrustedfile = null) {}

/**
 * Checks if a private key corresponds to a certificate
 * @link http://php.net/manual/en/function.openssl-x509-check-private-key.php
 * @param cert mixed
 * @param key mixed
 * @return bool true if key is the private key that
 */
function openssl_x509_check_private_key ($cert, $key) {}

/**
 * Exports a certificate as a string
 * @link http://php.net/manual/en/function.openssl-x509-export.php
 * @param x509 mixed
 * @param output string
 * @param notext bool[optional]
 * @return bool 
 */
function openssl_x509_export ($x509, &$output, $notext = null) {}

/**
 * Exports a certificate to file
 * @link http://php.net/manual/en/function.openssl-x509-export-to-file.php
 * @param x509 mixed
 * @param outfilename string
 * @param notext bool[optional]
 * @return bool 
 */
function openssl_x509_export_to_file ($x509, $outfilename, $notext = null) {}

/**
 * Exports a PKCS#12 Compatible Certificate Store File to variable.
 * @link http://php.net/manual/en/function.openssl-pkcs12-export.php
 * @param x509 mixed
 * @param out string
 * @param priv_key mixed
 * @param pass string
 * @param args array[optional]
 * @return bool 
 */
function openssl_pkcs12_export ($x509, &$out, $priv_key, $pass, array $args = null) {}

/**
 * Exports a PKCS#12 Compatible Certificate Store File
 * @link http://php.net/manual/en/function.openssl-pkcs12-export-to-file.php
 * @param x509 mixed
 * @param filename string
 * @param priv_key mixed
 * @param pass string
 * @param args array[optional]
 * @return bool 
 */
function openssl_pkcs12_export_to_file ($x509, $filename, $priv_key, $pass, array $args = null) {}

/**
 * Parse a PKCS#12 Certificate Store into an array
 * @link http://php.net/manual/en/function.openssl-pkcs12-read.php
 * @param PKCS12 mixed
 * @param certs array
 * @param pass string
 * @return bool 
 */
function openssl_pkcs12_read ($PKCS12, array &$certs, $pass) {}

/**
 * Generates a CSR
 * @link http://php.net/manual/en/function.openssl-csr-new.php
 * @param dn array
 * @param privkey resource
 * @param configargs array[optional]
 * @param extraattribs array[optional]
 * @return mixed the CSR.
 */
function openssl_csr_new (array $dn, &$privkey, array $configargs = null, array $extraattribs = null) {}

/**
 * Exports a CSR as a string
 * @link http://php.net/manual/en/function.openssl-csr-export.php
 * @param csr resource
 * @param out string
 * @param notext bool[optional]
 * @return bool 
 */
function openssl_csr_export ($csr, &$out, $notext = null) {}

/**
 * Exports a CSR to a file
 * @link http://php.net/manual/en/function.openssl-csr-export-to-file.php
 * @param csr resource
 * @param outfilename string
 * @param notext bool[optional]
 * @return bool 
 */
function openssl_csr_export_to_file ($csr, $outfilename, $notext = null) {}

/**
 * Sign a CSR with another certificate (or itself) and generate a certificate
 * @link http://php.net/manual/en/function.openssl-csr-sign.php
 * @param csr mixed
 * @param cacert mixed
 * @param priv_key mixed
 * @param days int
 * @param configargs array[optional]
 * @param serial int[optional]
 * @return resource an x509 certificate resource on success, false on failure.
 */
function openssl_csr_sign ($csr, $cacert, $priv_key, $days, array $configargs = null, $serial = null) {}

/**
 * Returns the subject of a CERT
 * @link http://php.net/manual/en/function.openssl-csr-get-subject.php
 * @param csr mixed
 * @param use_shortnames bool[optional]
 * @return array 
 */
function openssl_csr_get_subject ($csr, $use_shortnames = null) {}

/**
 * Returns the public key of a CERT
 * @link http://php.net/manual/en/function.openssl-csr-get-public-key.php
 * @param csr mixed
 * @param use_shortnames bool[optional]
 * @return resource 
 */
function openssl_csr_get_public_key ($csr, $use_shortnames = null) {}

/**
 * Generate signature
 * @link http://php.net/manual/en/function.openssl-sign.php
 * @param data string
 * @param signature string
 * @param priv_key_id mixed
 * @param signature_alg int[optional]
 * @return bool 
 */
function openssl_sign ($data, &$signature, $priv_key_id, $signature_alg = null) {}

/**
 * Verify signature
 * @link http://php.net/manual/en/function.openssl-verify.php
 * @param data string
 * @param signature string
 * @param pub_key_id mixed
 * @param signature_alg int[optional]
 * @return int 1 if the signature is correct, 0 if it is incorrect, and
 */
function openssl_verify ($data, $signature, $pub_key_id, $signature_alg = null) {}

/**
 * Seal (encrypt) data
 * @link http://php.net/manual/en/function.openssl-seal.php
 * @param data string
 * @param sealed_data string
 * @param env_keys array
 * @param pub_key_ids array
 * @return int the length of the sealed data on success, or false on error.
 */
function openssl_seal ($data, &$sealed_data, array &$env_keys, array $pub_key_ids) {}

/**
 * Open sealed data
 * @link http://php.net/manual/en/function.openssl-open.php
 * @param sealed_data string
 * @param open_data string
 * @param env_key string
 * @param priv_key_id mixed
 * @return bool 
 */
function openssl_open ($sealed_data, &$open_data, $env_key, $priv_key_id) {}

/**
 * Verifies the signature of an S/MIME signed message
 * @link http://php.net/manual/en/function.openssl-pkcs7-verify.php
 * @param filename string
 * @param flags int
 * @param outfilename string[optional]
 * @param cainfo array[optional]
 * @param extracerts string[optional]
 * @param content string[optional]
 * @return mixed true if the signature is verified, false if it is not correct
 */
function openssl_pkcs7_verify ($filename, $flags, $outfilename = null, array $cainfo = null, $extracerts = null, $content = null) {}

/**
 * Decrypts an S/MIME encrypted message
 * @link http://php.net/manual/en/function.openssl-pkcs7-decrypt.php
 * @param infilename string
 * @param outfilename string
 * @param recipcert mixed
 * @param recipkey mixed[optional]
 * @return bool 
 */
function openssl_pkcs7_decrypt ($infilename, $outfilename, $recipcert, $recipkey = null) {}

/**
 * Sign an S/MIME message
 * @link http://php.net/manual/en/function.openssl-pkcs7-sign.php
 * @param infilename string
 * @param outfilename string
 * @param signcert mixed
 * @param privkey mixed
 * @param headers array
 * @param flags int[optional]
 * @param extracerts string[optional]
 * @return bool 
 */
function openssl_pkcs7_sign ($infilename, $outfilename, $signcert, $privkey, array $headers, $flags = null, $extracerts = null) {}

/**
 * Encrypt an S/MIME message
 * @link http://php.net/manual/en/function.openssl-pkcs7-encrypt.php
 * @param infile string
 * @param outfile string
 * @param recipcerts mixed
 * @param headers array
 * @param flags int[optional]
 * @param cipherid int[optional]
 * @return bool 
 */
function openssl_pkcs7_encrypt ($infile, $outfile, $recipcerts, array $headers, $flags = null, $cipherid = null) {}

/**
 * Encrypts data with private key
 * @link http://php.net/manual/en/function.openssl-private-encrypt.php
 * @param data string
 * @param crypted string
 * @param key mixed
 * @param padding int[optional]
 * @return bool 
 */
function openssl_private_encrypt ($data, &$crypted, $key, $padding = null) {}

/**
 * Decrypts data with private key
 * @link http://php.net/manual/en/function.openssl-private-decrypt.php
 * @param data string
 * @param decrypted string
 * @param key mixed
 * @param padding int[optional]
 * @return bool 
 */
function openssl_private_decrypt ($data, &$decrypted, $key, $padding = null) {}

/**
 * Encrypts data with public key
 * @link http://php.net/manual/en/function.openssl-public-encrypt.php
 * @param data string
 * @param crypted string
 * @param key mixed
 * @param padding int[optional]
 * @return bool 
 */
function openssl_public_encrypt ($data, &$crypted, $key, $padding = null) {}

/**
 * Decrypts data with public key
 * @link http://php.net/manual/en/function.openssl-public-decrypt.php
 * @param data string
 * @param decrypted string
 * @param key mixed
 * @param padding int[optional]
 * @return bool 
 */
function openssl_public_decrypt ($data, &$decrypted, $key, $padding = null) {}

/**
 * Return openSSL error message
 * @link http://php.net/manual/en/function.openssl-error-string.php
 * @return string an error message string, or false if there are no more error
 */
function openssl_error_string () {}

define ('OPENSSL_VERSION_TEXT', "OpenSSL 0.9.7d 17 Mar 2004");
define ('OPENSSL_VERSION_NUMBER', 9465935);
define ('X509_PURPOSE_SSL_CLIENT', 1);
define ('X509_PURPOSE_SSL_SERVER', 2);
define ('X509_PURPOSE_NS_SSL_SERVER', 3);
define ('X509_PURPOSE_SMIME_SIGN', 4);
define ('X509_PURPOSE_SMIME_ENCRYPT', 5);
define ('X509_PURPOSE_CRL_SIGN', 6);
define ('X509_PURPOSE_ANY', 7);

/**
 * Used as default algorithm by openssl_sign and
 * openssl_verify.
 * @link http://php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_ALGO_SHA1', 1);
define ('OPENSSL_ALGO_MD5', 2);
define ('OPENSSL_ALGO_MD4', 3);
define ('OPENSSL_ALGO_MD2', 4);
define ('PKCS7_DETACHED', 64);
define ('PKCS7_TEXT', 1);
define ('PKCS7_NOINTERN', 16);
define ('PKCS7_NOVERIFY', 32);
define ('PKCS7_NOCHAIN', 8);
define ('PKCS7_NOCERTS', 2);
define ('PKCS7_NOATTR', 256);
define ('PKCS7_BINARY', 128);
define ('PKCS7_NOSIGS', 4);
define ('OPENSSL_PKCS1_PADDING', 1);
define ('OPENSSL_SSLV23_PADDING', 2);
define ('OPENSSL_NO_PADDING', 3);
define ('OPENSSL_PKCS1_OAEP_PADDING', 4);
define ('OPENSSL_CIPHER_RC2_40', 0);
define ('OPENSSL_CIPHER_RC2_128', 1);
define ('OPENSSL_CIPHER_RC2_64', 2);
define ('OPENSSL_CIPHER_DES', 3);
define ('OPENSSL_CIPHER_3DES', 4);
define ('OPENSSL_KEYTYPE_RSA', 0);
define ('OPENSSL_KEYTYPE_DSA', 1);
define ('OPENSSL_KEYTYPE_DH', 2);

// End of openssl v.
?>
