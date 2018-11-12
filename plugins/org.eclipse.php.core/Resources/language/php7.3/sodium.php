<?php

// Start of sodium v.7.3.0

/**
 * @link http://www.php.net/manual/en/class.sodiumexception.php
 */
class SodiumException extends Exception implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param mixed $message [optional]
	 * @param mixed $code [optional]
	 * @param mixed $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * Check if hardware supports AES256-GCM
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-aes256gcm-is-available.php
 * @return bool 
 */
function sodium_crypto_aead_aes256gcm_is_available () {}

/**
 * Decrypt in combined mode with precalculation
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-aes256gcm-decrypt.php
 * @param string $ciphertext 
 * @param string $ad 
 * @param string $nonce 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_aead_aes256gcm_decrypt (string $ciphertext, string $ad, string $nonce, string $key) {}

/**
 * Encrypt in combined mode with precalculation
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-aes256gcm-encrypt.php
 * @param string $msg 
 * @param string $ad 
 * @param string $nonce 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_aead_aes256gcm_encrypt (string $msg, string $ad, string $nonce, string $key) {}

/**
 * Get random bytes for key
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-aes256gcm-keygen.php
 * @return string 
 */
function sodium_crypto_aead_aes256gcm_keygen () {}

/**
 * Verify that the ciphertext includes a valid tag
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-chacha20poly1305-decrypt.php
 * @param string $ciphertext 
 * @param string $ad 
 * @param string $nonce 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_aead_chacha20poly1305_decrypt (string $ciphertext, string $ad, string $nonce, string $key) {}

/**
 * Encrypt a message
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-chacha20poly1305-encrypt.php
 * @param string $msg 
 * @param string $ad 
 * @param string $nonce 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_aead_chacha20poly1305_encrypt (string $msg, string $ad, string $nonce, string $key) {}

/**
 * Get random bytes for key
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-chacha20poly1305-keygen.php
 * @return string 
 */
function sodium_crypto_aead_chacha20poly1305_keygen () {}

/**
 * Verify that the ciphertext includes a valid tag
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-chacha20poly1305-ietf-decrypt.php
 * @param string $ciphertext 
 * @param string $ad 
 * @param string $nonce 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_aead_chacha20poly1305_ietf_decrypt (string $ciphertext, string $ad, string $nonce, string $key) {}

/**
 * Encrypt a message
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-chacha20poly1305-ietf-encrypt.php
 * @param string $msg 
 * @param string $ad 
 * @param string $nonce 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_aead_chacha20poly1305_ietf_encrypt (string $msg, string $ad, string $nonce, string $key) {}

/**
 * Get random bytes for key
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-chacha20poly1305-ietf-keygen.php
 * @return string 
 */
function sodium_crypto_aead_chacha20poly1305_ietf_keygen () {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-xchacha20poly1305-ietf-decrypt.php
 * @param string $ciphertext 
 * @param string $ad 
 * @param string $nonce 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_aead_xchacha20poly1305_ietf_decrypt (string $ciphertext, string $ad, string $nonce, string $key) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-xchacha20poly1305-ietf-keygen.php
 * @return string 
 */
function sodium_crypto_aead_xchacha20poly1305_ietf_keygen () {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-xchacha20poly1305-ietf-encrypt.php
 * @param string $msg 
 * @param string $ad 
 * @param string $nonce 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_aead_xchacha20poly1305_ietf_encrypt (string $msg, string $ad, string $nonce, string $key) {}

/**
 * Compute a tag for the message
 * @link http://www.php.net/manual/en/function.sodium-crypto-auth.php
 * @param string $msg 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_auth (string $msg, string $key) {}

/**
 * Get random bytes for key
 * @link http://www.php.net/manual/en/function.sodium-crypto-auth-keygen.php
 * @return string 
 */
function sodium_crypto_auth_keygen () {}

/**
 * Verifies that the tag is valid for the message
 * @link http://www.php.net/manual/en/function.sodium-crypto-auth-verify.php
 * @param string $signature 
 * @param string $msg 
 * @param string $key 
 * @return bool 
 */
function sodium_crypto_auth_verify (string $signature, string $msg, string $key) {}

/**
 * Encrypt a message
 * @link http://www.php.net/manual/en/function.sodium-crypto-box.php
 * @param string $msg 
 * @param string $nonce 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_box (string $msg, string $nonce, string $key) {}

/**
 * Randomly generate a secret key and a corresponding public key
 * @link http://www.php.net/manual/en/function.sodium-crypto-box-keypair.php
 * @return string 
 */
function sodium_crypto_box_keypair () {}

/**
 * Deterministically derive the key pair from a single key
 * @link http://www.php.net/manual/en/function.sodium-crypto-box-seed-keypair.php
 * @param string $key 
 * @return string 
 */
function sodium_crypto_box_seed_keypair (string $key) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-box-keypair-from-secretkey-and-publickey.php
 * @param string $secret_key 
 * @param string $public_key 
 * @return string 
 */
function sodium_crypto_box_keypair_from_secretkey_and_publickey (string $secret_key, string $public_key) {}

/**
 * Verify and decrypt a ciphertext
 * @link http://www.php.net/manual/en/function.sodium-crypto-box-open.php
 * @param string $ciphertext 
 * @param string $nonce 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_box_open (string $ciphertext, string $nonce, string $key) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-box-publickey.php
 * @param string $key 
 * @return string 
 */
function sodium_crypto_box_publickey (string $key) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-box-publickey-from-secretkey.php
 * @param string $key 
 * @return string 
 */
function sodium_crypto_box_publickey_from_secretkey (string $key) {}

/**
 * Encrypt a message
 * @link http://www.php.net/manual/en/function.sodium-crypto-box-seal.php
 * @param string $msg 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_box_seal (string $msg, string $key) {}

/**
 * Decrypt the ciphertext
 * @link http://www.php.net/manual/en/function.sodium-crypto-box-seal-open.php
 * @param string $ciphertext 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_box_seal_open (string $ciphertext, string $key) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-box-secretkey.php
 * @param string $key 
 * @return string 
 */
function sodium_crypto_box_secretkey (string $key) {}

/**
 * Creates a new sodium keypair
 * @link http://www.php.net/manual/en/function.sodium-crypto-kx-keypair.php
 * @return string the new keypair on success; throws an exception otherwise.
 */
function sodium_crypto_kx_keypair () {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-kx-publickey.php
 * @param string $key 
 * @return string 
 */
function sodium_crypto_kx_publickey (string $key) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-kx-secretkey.php
 * @param string $key 
 * @return string 
 */
function sodium_crypto_kx_secretkey (string $key) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-kx-seed-keypair.php
 * @param string $string 
 * @return string 
 */
function sodium_crypto_kx_seed_keypair (string $string) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-kx-client-session-keys.php
 * @param string $client_keypair 
 * @param string $server_key 
 * @return array 
 */
function sodium_crypto_kx_client_session_keys (string $client_keypair, string $server_key) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-kx-server-session-keys.php
 * @param string $server_keypair 
 * @param string $client_key 
 * @return array 
 */
function sodium_crypto_kx_server_session_keys (string $server_keypair, string $client_key) {}

/**
 * Get a hash of the message
 * @link http://www.php.net/manual/en/function.sodium-crypto-generichash.php
 * @param string $msg 
 * @param string $key [optional] 
 * @param int $length [optional] 
 * @return string 
 */
function sodium_crypto_generichash (string $msg, string $key = null, int $length = null) {}

/**
 * Get random bytes for key
 * @link http://www.php.net/manual/en/function.sodium-crypto-generichash-keygen.php
 * @return string 
 */
function sodium_crypto_generichash_keygen () {}

/**
 * Initialize a hash
 * @link http://www.php.net/manual/en/function.sodium-crypto-generichash-init.php
 * @param string $key [optional] 
 * @param int $length [optional] 
 * @return string 
 */
function sodium_crypto_generichash_init (string $key = null, int $length = null) {}

/**
 * Add message to a hash
 * @link http://www.php.net/manual/en/function.sodium-crypto-generichash-update.php
 * @param string $state 
 * @param string $msg 
 * @return bool 
 */
function sodium_crypto_generichash_update (string &$state, string $msg) {}

/**
 * Complete the hash
 * @link http://www.php.net/manual/en/function.sodium-crypto-generichash-final.php
 * @param string $state 
 * @param int $length [optional] 
 * @return string 
 */
function sodium_crypto_generichash_final (string &$state, int $length = null) {}

/**
 * Derive a subkey
 * @link http://www.php.net/manual/en/function.sodium-crypto-kdf-derive-from-key.php
 * @param int $subkey_len 
 * @param int $subkey_id 
 * @param string $context 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_kdf_derive_from_key (int $subkey_len, int $subkey_id, string $context, string $key) {}

/**
 * Get random bytes for key
 * @link http://www.php.net/manual/en/function.sodium-crypto-kdf-keygen.php
 * @return string 
 */
function sodium_crypto_kdf_keygen () {}

/**
 * Derive a key from a password
 * @link http://www.php.net/manual/en/function.sodium-crypto-pwhash.php
 * @param int $length integer; The length of the password hash to generate, in bytes.
 * @param string $password string; The password to generate a hash for.
 * @param string $salt string A salt to add to the password before hashing. The salt should be unpredictable, ideally generated from a good random mumber source such as random_bytes, and have a length of at least SODIUM_CRYPTO_PWHASH_SALTBYTES bytes.
 * @param int $opslimit Represents a maximum amount of computations to perform. Raising this number will make the function require more CPU cycles to compute a key. There are some constants available to set the operations limit to appropriate values depending on intended use, in order of strength: SODIUM_CRYPTO_PWHASH_OPSLIMIT_INTERACTIVE, SODIUM_CRYPTO_PWHASH_OPSLIMIT_MODERATE and SODIUM_CRYPTO_PWHASH_OPSLIMIT_SENSITIVE.
 * @param int $memlimit The maximum amount of RAM that the function will use, in bytes. There are constants to help you choose an appropriate value, in order of size: SODIUM_CRYPTO_PWHASH_MEMLIMIT_INTERACTIVE, SODIUM_CRYPTO_PWHASH_MEMLIMIT_MODERATE, and SODIUM_CRYPTO_PWHASH_MEMLIMIT_SENSITIVE. Typically these should be paired with the matching opslimit values.
 * @param int $alg [optional] integer A number indicating the hash algorithm to use. By default SODIUM_CRYPTO_PWHASH_ALG_DEFAULT (the currently recommended algorithm, which can change from one version of libsodium to another), or explicitly using SODIUM_CRYPTO_PWHASH_ALG_ARGON2I13, representing the Argon2id algorithm version 1.3.
 * @return string the hashed password, or false on failure.
 * <p>
 * The used algorithm, opslimit, memlimit and salt are embedded within the hash, so
 * all information needed to verify the hash is included. This allows
 * the password_verify function to verify the hash without
 * needing separate storage for the salt or algorithm information.
 * </p>
 */
function sodium_crypto_pwhash (int $length, string $password, string $salt, int $opslimit, int $memlimit, int $alg = null) {}

/**
 * Get an ASCII encoded hash
 * @link http://www.php.net/manual/en/function.sodium-crypto-pwhash-str.php
 * @param string $password 
 * @param int $opslimit 
 * @param int $memlimit 
 * @return string 
 */
function sodium_crypto_pwhash_str (string $password, int $opslimit, int $memlimit) {}

/**
 * Verify that hash is a valid password verification string
 * @link http://www.php.net/manual/en/function.sodium-crypto-pwhash-str-verify.php
 * @param string $hash 
 * @param string $password 
 * @return bool 
 */
function sodium_crypto_pwhash_str_verify (string $hash, string $password) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-pwhash-str-needs-rehash.php
 * @param string $password 
 * @param int $opslimit 
 * @param int $memlimit 
 * @return bool 
 */
function sodium_crypto_pwhash_str_needs_rehash (string $password, int $opslimit, int $memlimit) {}

/**
 * Derives a key from a password
 * @link http://www.php.net/manual/en/function.sodium-crypto-pwhash-scryptsalsa208sha256.php
 * @param int $length 
 * @param string $password 
 * @param string $salt 
 * @param int $opslimit 
 * @param int $memlimit 
 * @return string 
 */
function sodium_crypto_pwhash_scryptsalsa208sha256 (int $length, string $password, string $salt, int $opslimit, int $memlimit) {}

/**
 * Get an ASCII encoded hash
 * @link http://www.php.net/manual/en/function.sodium-crypto-pwhash-scryptsalsa208sha256-str.php
 * @param string $password 
 * @param int $opslimit 
 * @param int $memlimit 
 * @return string 
 */
function sodium_crypto_pwhash_scryptsalsa208sha256_str (string $password, int $opslimit, int $memlimit) {}

/**
 * Verify that the password is a valid password verification string
 * @link http://www.php.net/manual/en/function.sodium-crypto-pwhash-scryptsalsa208sha256-str-verify.php
 * @param string $hash 
 * @param string $password 
 * @return bool 
 */
function sodium_crypto_pwhash_scryptsalsa208sha256_str_verify (string $hash, string $password) {}

/**
 * Compute a shared secret given a user's secret key and another user's public key
 * @link http://www.php.net/manual/en/function.sodium-crypto-scalarmult.php
 * @param string $n 
 * @param string $p 
 * @return string 
 */
function sodium_crypto_scalarmult (string $n, string $p) {}

/**
 * Encrypt a message
 * @link http://www.php.net/manual/en/function.sodium-crypto-secretbox.php
 * @param string $string 
 * @param string $nonce 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_secretbox (string $string, string $nonce, string $key) {}

/**
 * Get random bytes for key
 * @link http://www.php.net/manual/en/function.sodium-crypto-secretbox-keygen.php
 * @return string 
 */
function sodium_crypto_secretbox_keygen () {}

/**
 * Verify and decrypt a ciphertext
 * @link http://www.php.net/manual/en/function.sodium-crypto-secretbox-open.php
 * @param string $ciphertext 
 * @param string $nonce 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_secretbox_open (string $ciphertext, string $nonce, string $key) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-secretstream-xchacha20poly1305-keygen.php
 * @return string 
 */
function sodium_crypto_secretstream_xchacha20poly1305_keygen () {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-secretstream-xchacha20poly1305-init-push.php
 * @param string $key 
 * @return array 
 */
function sodium_crypto_secretstream_xchacha20poly1305_init_push (string $key) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-secretstream-xchacha20poly1305-push.php
 * @param string $state 
 * @param string $msg 
 * @param string $ad [optional] 
 * @param int $tag [optional] 
 * @return string 
 */
function sodium_crypto_secretstream_xchacha20poly1305_push (string &$state, string $msg, string $ad = null, int $tag = null) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-secretstream-xchacha20poly1305-init-pull.php
 * @param string $header 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_secretstream_xchacha20poly1305_init_pull (string $header, string $key) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-secretstream-xchacha20poly1305-pull.php
 * @param string $state 
 * @param string $c 
 * @param string $ad [optional] 
 * @return array 
 */
function sodium_crypto_secretstream_xchacha20poly1305_pull (string &$state, string $c, string $ad = null) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-secretstream-xchacha20poly1305-rekey.php
 * @param string $state 
 * @return void 
 */
function sodium_crypto_secretstream_xchacha20poly1305_rekey (string &$state) {}

/**
 * Compute a fixed-size fingerprint for the message
 * @link http://www.php.net/manual/en/function.sodium-crypto-shorthash.php
 * @param string $msg 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_shorthash (string $msg, string $key) {}

/**
 * Get random bytes for key
 * @link http://www.php.net/manual/en/function.sodium-crypto-shorthash-keygen.php
 * @return string 
 */
function sodium_crypto_shorthash_keygen () {}

/**
 * Sign a message
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign.php
 * @param string $msg 
 * @param string $secret_key 
 * @return string 
 */
function sodium_crypto_sign (string $msg, string $secret_key) {}

/**
 * Sign the message
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-detached.php
 * @param string $msg 
 * @param string $keypair 
 * @return string 
 */
function sodium_crypto_sign_detached (string $msg, string $keypair) {}

/**
 * Convert an Ed25519 public key to a Curve25519 public key
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-ed25519-pk-to-curve25519.php
 * @param string $key 
 * @return string 
 */
function sodium_crypto_sign_ed25519_pk_to_curve25519 (string $key) {}

/**
 * Convert an Ed25519 secret key to a Curve25519 secret key
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-ed25519-sk-to-curve25519.php
 * @param string $key 
 * @return string 
 */
function sodium_crypto_sign_ed25519_sk_to_curve25519 (string $key) {}

/**
 * Randomly generate a secret key and a corresponding public key
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-keypair.php
 * @return string 
 */
function sodium_crypto_sign_keypair () {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-keypair-from-secretkey-and-publickey.php
 * @param string $secret_key 
 * @param string $public_key 
 * @return string 
 */
function sodium_crypto_sign_keypair_from_secretkey_and_publickey (string $secret_key, string $public_key) {}

/**
 * Check that the signed message has a valid signature
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-open.php
 * @param string $string 
 * @param string $keypair 
 * @return string 
 */
function sodium_crypto_sign_open (string $string, string $keypair) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-publickey.php
 * @param string $keypair 
 * @return string 
 */
function sodium_crypto_sign_publickey (string $keypair) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-secretkey.php
 * @param string $key 
 * @return string 
 */
function sodium_crypto_sign_secretkey (string $key) {}

/**
 * Extract the public key from the secret key
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-publickey-from-secretkey.php
 * @param string $key 
 * @return string 
 */
function sodium_crypto_sign_publickey_from_secretkey (string $key) {}

/**
 * Deterministically derive the key pair from a single key
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-seed-keypair.php
 * @param string $key 
 * @return string 
 */
function sodium_crypto_sign_seed_keypair (string $key) {}

/**
 * Verify signature for the message
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-verify-detached.php
 * @param string $signature 
 * @param string $msg 
 * @param string $key 
 * @return bool 
 */
function sodium_crypto_sign_verify_detached (string $signature, string $msg, string $key) {}

/**
 * Generate a deterministic sequence of bytes from a seed
 * @link http://www.php.net/manual/en/function.sodium-crypto-stream.php
 * @param int $length 
 * @param string $nonce 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_stream (int $length, string $nonce, string $key) {}

/**
 * Get random bytes for key
 * @link http://www.php.net/manual/en/function.sodium-crypto-stream-keygen.php
 * @return string 
 */
function sodium_crypto_stream_keygen () {}

/**
 * Encrypt a message
 * @link http://www.php.net/manual/en/function.sodium-crypto-stream-xor.php
 * @param string $msg 
 * @param string $nonce 
 * @param string $key 
 * @return string 
 */
function sodium_crypto_stream_xor (string $msg, string $nonce, string $key) {}

/**
 * Add large numbers
 * @link http://www.php.net/manual/en/function.sodium-add.php
 * @param string $val 
 * @param string $addv 
 * @return void 
 */
function sodium_add (string &$val, string $addv) {}

/**
 * Compare large numbers
 * @link http://www.php.net/manual/en/function.sodium-compare.php
 * @param string $buf1 
 * @param string $buf2 
 * @return int 
 */
function sodium_compare (string $buf1, string $buf2) {}

/**
 * Increment large number
 * @link http://www.php.net/manual/en/function.sodium-increment.php
 * @param string $val 
 * @return void 
 */
function sodium_increment (string &$val) {}

/**
 * Test for equality in constant-time
 * @link http://www.php.net/manual/en/function.sodium-memcmp.php
 * @param string $buf1 
 * @param string $buf2 
 * @return int 
 */
function sodium_memcmp (string $buf1, string $buf2) {}

/**
 * Overwrite buf with zeros
 * @link http://www.php.net/manual/en/function.sodium-memzero.php
 * @param string $buf 
 * @return void 
 */
function sodium_memzero (string &$buf) {}

/**
 * Add padding data
 * @link http://www.php.net/manual/en/function.sodium-pad.php
 * @param string $unpadded 
 * @param int $length 
 * @return string 
 */
function sodium_pad (string $unpadded, int $length) {}

/**
 * Remove padding data
 * @link http://www.php.net/manual/en/function.sodium-unpad.php
 * @param string $padded 
 * @param int $length 
 * @return string 
 */
function sodium_unpad (string $padded, int $length) {}

/**
 * Encode to hexadecimal
 * @link http://www.php.net/manual/en/function.sodium-bin2hex.php
 * @param string $bin 
 * @return string 
 */
function sodium_bin2hex (string $bin) {}

/**
 * Decodes a hexadecimally encoded binary string
 * @link http://www.php.net/manual/en/function.sodium-hex2bin.php
 * @param string $hex Hexadecimal representation of data.
 * @param string $ignore [optional] Optional string argument for characters to ignore.
 * @return string the binary representation of the given hex data.
 */
function sodium_hex2bin (string $hex, string $ignore = null) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-bin2base64.php
 * @param string $bin 
 * @param int $id 
 * @return string 
 */
function sodium_bin2base64 (string $bin, int $id) {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-base642bin.php
 * @param string $b64 
 * @param int $id 
 * @param string $ignore [optional] 
 * @return string 
 */
function sodium_base642bin (string $b64, int $id, string $ignore = null) {}

/**
 * Alias: sodium_crypto_box_publickey_from_secretkey
 * @link http://www.php.net/manual/en/function.sodium-crypto-scalarmult-base.php
 * @param mixed $string_1
 * @param mixed $string_2
 */
function sodium_crypto_scalarmult_base ($string_1, $string_2) {}


/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_LIBRARY_VERSION', "1.0.16");

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_LIBRARY_MAJOR_VERSION', 10);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_LIBRARY_MINOR_VERSION', 1);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_AEAD_AES256GCM_KEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_AEAD_AES256GCM_NSECBYTES', 0);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_AEAD_AES256GCM_NPUBBYTES', 12);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_AEAD_AES256GCM_ABYTES', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_KEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_NSECBYTES', 0);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_NPUBBYTES', 8);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_ABYTES', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_IETF_KEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_IETF_NSECBYTES', 0);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_IETF_NPUBBYTES', 12);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_IETF_ABYTES', 16);
define ('SODIUM_CRYPTO_AEAD_XCHACHA20POLY1305_IETF_KEYBYTES', 32);
define ('SODIUM_CRYPTO_AEAD_XCHACHA20POLY1305_IETF_NSECBYTES', 0);
define ('SODIUM_CRYPTO_AEAD_XCHACHA20POLY1305_IETF_NPUBBYTES', 24);
define ('SODIUM_CRYPTO_AEAD_XCHACHA20POLY1305_IETF_ABYTES', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_AUTH_BYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_AUTH_KEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_BOX_SEALBYTES', 48);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_BOX_SECRETKEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_BOX_PUBLICKEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_BOX_KEYPAIRBYTES', 64);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_BOX_MACBYTES', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_BOX_NONCEBYTES', 24);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_BOX_SEEDBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_KDF_BYTES_MIN', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_KDF_BYTES_MAX', 64);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_KDF_CONTEXTBYTES', 8);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_KDF_KEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_KX_SEEDBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_KX_SESSIONKEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_KX_PUBLICKEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_KX_SECRETKEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_KX_KEYPAIRBYTES', 64);
define ('SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_ABYTES', 17);
define ('SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_HEADERBYTES', 24);
define ('SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_KEYBYTES', 32);
define ('SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_MESSAGEBYTES_MAX', 274877906816);
define ('SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_TAG_MESSAGE', 0);
define ('SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_TAG_PUSH', 1);
define ('SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_TAG_REKEY', 2);
define ('SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_TAG_FINAL', 3);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_GENERICHASH_BYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_GENERICHASH_BYTES_MIN', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_GENERICHASH_BYTES_MAX', 64);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_GENERICHASH_KEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_GENERICHASH_KEYBYTES_MIN', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_GENERICHASH_KEYBYTES_MAX', 64);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_PWHASH_ALG_ARGON2I13', 1);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_PWHASH_ALG_ARGON2ID13', 2);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_PWHASH_ALG_DEFAULT', 2);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_PWHASH_SALTBYTES', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_PWHASH_STRPREFIX', "$argon2id$");

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_PWHASH_OPSLIMIT_INTERACTIVE', 2);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_PWHASH_MEMLIMIT_INTERACTIVE', 67108864);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_PWHASH_OPSLIMIT_MODERATE', 3);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_PWHASH_MEMLIMIT_MODERATE', 268435456);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_PWHASH_OPSLIMIT_SENSITIVE', 4);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_PWHASH_MEMLIMIT_SENSITIVE', 1073741824);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_SALTBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_STRPREFIX', "$7$");

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_OPSLIMIT_INTERACTIVE', 524288);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_MEMLIMIT_INTERACTIVE', 16777216);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_OPSLIMIT_SENSITIVE', 33554432);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_MEMLIMIT_SENSITIVE', 1073741824);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_SCALARMULT_BYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_SCALARMULT_SCALARBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_SHORTHASH_BYTES', 8);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_SHORTHASH_KEYBYTES', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_SECRETBOX_KEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_SECRETBOX_MACBYTES', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_SECRETBOX_NONCEBYTES', 24);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_SIGN_BYTES', 64);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_SIGN_SEEDBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_SIGN_PUBLICKEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_SIGN_SECRETKEYBYTES', 64);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_SIGN_KEYPAIRBYTES', 96);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_STREAM_NONCEBYTES', 24);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 */
define ('SODIUM_CRYPTO_STREAM_KEYBYTES', 32);
define ('SODIUM_BASE64_VARIANT_ORIGINAL', 1);
define ('SODIUM_BASE64_VARIANT_ORIGINAL_NO_PADDING', 3);
define ('SODIUM_BASE64_VARIANT_URLSAFE', 5);
define ('SODIUM_BASE64_VARIANT_URLSAFE_NO_PADDING', 7);

// End of sodium v.7.3.0
