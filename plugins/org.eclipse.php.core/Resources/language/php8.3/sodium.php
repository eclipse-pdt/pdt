<?php

// Start of sodium v.8.3.0

class SodiumException extends Exception implements Throwable, Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

/**
 * {@inheritdoc}
 */
function sodium_crypto_aead_aes256gcm_is_available (): bool {}

/**
 * {@inheritdoc}
 * @param string $ciphertext
 * @param string $additional_data
 * @param string $nonce
 * @param string $key
 */
function sodium_crypto_aead_chacha20poly1305_decrypt (string $ciphertext, string $additional_data, string $nonce, string $key): string|false {}

/**
 * {@inheritdoc}
 * @param string $message
 * @param string $additional_data
 * @param string $nonce
 * @param string $key
 */
function sodium_crypto_aead_chacha20poly1305_encrypt (string $message, string $additional_data, string $nonce, string $key): string {}

/**
 * {@inheritdoc}
 */
function sodium_crypto_aead_chacha20poly1305_keygen (): string {}

/**
 * {@inheritdoc}
 * @param string $ciphertext
 * @param string $additional_data
 * @param string $nonce
 * @param string $key
 */
function sodium_crypto_aead_chacha20poly1305_ietf_decrypt (string $ciphertext, string $additional_data, string $nonce, string $key): string|false {}

/**
 * {@inheritdoc}
 * @param string $message
 * @param string $additional_data
 * @param string $nonce
 * @param string $key
 */
function sodium_crypto_aead_chacha20poly1305_ietf_encrypt (string $message, string $additional_data, string $nonce, string $key): string {}

/**
 * {@inheritdoc}
 */
function sodium_crypto_aead_chacha20poly1305_ietf_keygen (): string {}

/**
 * {@inheritdoc}
 * @param string $ciphertext
 * @param string $additional_data
 * @param string $nonce
 * @param string $key
 */
function sodium_crypto_aead_xchacha20poly1305_ietf_decrypt (string $ciphertext, string $additional_data, string $nonce, string $key): string|false {}

/**
 * {@inheritdoc}
 */
function sodium_crypto_aead_xchacha20poly1305_ietf_keygen (): string {}

/**
 * {@inheritdoc}
 * @param string $message
 * @param string $additional_data
 * @param string $nonce
 * @param string $key
 */
function sodium_crypto_aead_xchacha20poly1305_ietf_encrypt (string $message, string $additional_data, string $nonce, string $key): string {}

/**
 * {@inheritdoc}
 * @param string $message
 * @param string $key
 */
function sodium_crypto_auth (string $message, string $key): string {}

/**
 * {@inheritdoc}
 */
function sodium_crypto_auth_keygen (): string {}

/**
 * {@inheritdoc}
 * @param string $mac
 * @param string $message
 * @param string $key
 */
function sodium_crypto_auth_verify (string $mac, string $message, string $key): bool {}

/**
 * {@inheritdoc}
 * @param string $message
 * @param string $nonce
 * @param string $key_pair
 */
function sodium_crypto_box (string $message, string $nonce, string $key_pair): string {}

/**
 * {@inheritdoc}
 */
function sodium_crypto_box_keypair (): string {}

/**
 * {@inheritdoc}
 * @param string $seed
 */
function sodium_crypto_box_seed_keypair (string $seed): string {}

/**
 * {@inheritdoc}
 * @param string $secret_key
 * @param string $public_key
 */
function sodium_crypto_box_keypair_from_secretkey_and_publickey (string $secret_key, string $public_key): string {}

/**
 * {@inheritdoc}
 * @param string $ciphertext
 * @param string $nonce
 * @param string $key_pair
 */
function sodium_crypto_box_open (string $ciphertext, string $nonce, string $key_pair): string|false {}

/**
 * {@inheritdoc}
 * @param string $key_pair
 */
function sodium_crypto_box_publickey (string $key_pair): string {}

/**
 * {@inheritdoc}
 * @param string $secret_key
 */
function sodium_crypto_box_publickey_from_secretkey (string $secret_key): string {}

/**
 * {@inheritdoc}
 * @param string $message
 * @param string $public_key
 */
function sodium_crypto_box_seal (string $message, string $public_key): string {}

/**
 * {@inheritdoc}
 * @param string $ciphertext
 * @param string $key_pair
 */
function sodium_crypto_box_seal_open (string $ciphertext, string $key_pair): string|false {}

/**
 * {@inheritdoc}
 * @param string $key_pair
 */
function sodium_crypto_box_secretkey (string $key_pair): string {}

/**
 * {@inheritdoc}
 * @param string $p
 * @param string $q
 */
function sodium_crypto_core_ristretto255_add (string $p, string $q): string {}

/**
 * {@inheritdoc}
 * @param string $s
 */
function sodium_crypto_core_ristretto255_from_hash (string $s): string {}

/**
 * {@inheritdoc}
 * @param string $s
 */
function sodium_crypto_core_ristretto255_is_valid_point (string $s): bool {}

/**
 * {@inheritdoc}
 */
function sodium_crypto_core_ristretto255_random (): string {}

/**
 * {@inheritdoc}
 * @param string $x
 * @param string $y
 */
function sodium_crypto_core_ristretto255_scalar_add (string $x, string $y): string {}

/**
 * {@inheritdoc}
 * @param string $s
 */
function sodium_crypto_core_ristretto255_scalar_complement (string $s): string {}

/**
 * {@inheritdoc}
 * @param string $s
 */
function sodium_crypto_core_ristretto255_scalar_invert (string $s): string {}

/**
 * {@inheritdoc}
 * @param string $x
 * @param string $y
 */
function sodium_crypto_core_ristretto255_scalar_mul (string $x, string $y): string {}

/**
 * {@inheritdoc}
 * @param string $s
 */
function sodium_crypto_core_ristretto255_scalar_negate (string $s): string {}

/**
 * {@inheritdoc}
 */
function sodium_crypto_core_ristretto255_scalar_random (): string {}

/**
 * {@inheritdoc}
 * @param string $s
 */
function sodium_crypto_core_ristretto255_scalar_reduce (string $s): string {}

/**
 * {@inheritdoc}
 * @param string $x
 * @param string $y
 */
function sodium_crypto_core_ristretto255_scalar_sub (string $x, string $y): string {}

/**
 * {@inheritdoc}
 * @param string $p
 * @param string $q
 */
function sodium_crypto_core_ristretto255_sub (string $p, string $q): string {}

/**
 * {@inheritdoc}
 */
function sodium_crypto_kx_keypair (): string {}

/**
 * {@inheritdoc}
 * @param string $key_pair
 */
function sodium_crypto_kx_publickey (string $key_pair): string {}

/**
 * {@inheritdoc}
 * @param string $key_pair
 */
function sodium_crypto_kx_secretkey (string $key_pair): string {}

/**
 * {@inheritdoc}
 * @param string $seed
 */
function sodium_crypto_kx_seed_keypair (string $seed): string {}

/**
 * {@inheritdoc}
 * @param string $client_key_pair
 * @param string $server_key
 */
function sodium_crypto_kx_client_session_keys (string $client_key_pair, string $server_key): array {}

/**
 * {@inheritdoc}
 * @param string $server_key_pair
 * @param string $client_key
 */
function sodium_crypto_kx_server_session_keys (string $server_key_pair, string $client_key): array {}

/**
 * {@inheritdoc}
 * @param string $message
 * @param string $key [optional]
 * @param int $length [optional]
 */
function sodium_crypto_generichash (string $message, string $key = '', int $length = 32): string {}

/**
 * {@inheritdoc}
 */
function sodium_crypto_generichash_keygen (): string {}

/**
 * {@inheritdoc}
 * @param string $key [optional]
 * @param int $length [optional]
 */
function sodium_crypto_generichash_init (string $key = '', int $length = 32): string {}

/**
 * {@inheritdoc}
 * @param string $state
 * @param string $message
 */
function sodium_crypto_generichash_update (string &$state, string $message): true {}

/**
 * {@inheritdoc}
 * @param string $state
 * @param int $length [optional]
 */
function sodium_crypto_generichash_final (string &$state, int $length = 32): string {}

/**
 * {@inheritdoc}
 * @param int $subkey_length
 * @param int $subkey_id
 * @param string $context
 * @param string $key
 */
function sodium_crypto_kdf_derive_from_key (int $subkey_length, int $subkey_id, string $context, string $key): string {}

/**
 * {@inheritdoc}
 */
function sodium_crypto_kdf_keygen (): string {}

/**
 * {@inheritdoc}
 * @param int $length
 * @param string $password
 * @param string $salt
 * @param int $opslimit
 * @param int $memlimit
 * @param int $algo [optional]
 */
function sodium_crypto_pwhash (int $length, string $password, string $salt, int $opslimit, int $memlimit, int $algo = 2): string {}

/**
 * {@inheritdoc}
 * @param string $password
 * @param int $opslimit
 * @param int $memlimit
 */
function sodium_crypto_pwhash_str (string $password, int $opslimit, int $memlimit): string {}

/**
 * {@inheritdoc}
 * @param string $hash
 * @param string $password
 */
function sodium_crypto_pwhash_str_verify (string $hash, string $password): bool {}

/**
 * {@inheritdoc}
 * @param string $password
 * @param int $opslimit
 * @param int $memlimit
 */
function sodium_crypto_pwhash_str_needs_rehash (string $password, int $opslimit, int $memlimit): bool {}

/**
 * {@inheritdoc}
 * @param int $length
 * @param string $password
 * @param string $salt
 * @param int $opslimit
 * @param int $memlimit
 */
function sodium_crypto_pwhash_scryptsalsa208sha256 (int $length, string $password, string $salt, int $opslimit, int $memlimit): string {}

/**
 * {@inheritdoc}
 * @param string $password
 * @param int $opslimit
 * @param int $memlimit
 */
function sodium_crypto_pwhash_scryptsalsa208sha256_str (string $password, int $opslimit, int $memlimit): string {}

/**
 * {@inheritdoc}
 * @param string $hash
 * @param string $password
 */
function sodium_crypto_pwhash_scryptsalsa208sha256_str_verify (string $hash, string $password): bool {}

/**
 * {@inheritdoc}
 * @param string $n
 * @param string $p
 */
function sodium_crypto_scalarmult (string $n, string $p): string {}

/**
 * {@inheritdoc}
 * @param string $n
 * @param string $p
 */
function sodium_crypto_scalarmult_ristretto255 (string $n, string $p): string {}

/**
 * {@inheritdoc}
 * @param string $n
 */
function sodium_crypto_scalarmult_ristretto255_base (string $n): string {}

/**
 * {@inheritdoc}
 * @param string $message
 * @param string $nonce
 * @param string $key
 */
function sodium_crypto_secretbox (string $message, string $nonce, string $key): string {}

/**
 * {@inheritdoc}
 */
function sodium_crypto_secretbox_keygen (): string {}

/**
 * {@inheritdoc}
 * @param string $ciphertext
 * @param string $nonce
 * @param string $key
 */
function sodium_crypto_secretbox_open (string $ciphertext, string $nonce, string $key): string|false {}

/**
 * {@inheritdoc}
 */
function sodium_crypto_secretstream_xchacha20poly1305_keygen (): string {}

/**
 * {@inheritdoc}
 * @param string $key
 */
function sodium_crypto_secretstream_xchacha20poly1305_init_push (string $key): array {}

/**
 * {@inheritdoc}
 * @param string $state
 * @param string $message
 * @param string $additional_data [optional]
 * @param int $tag [optional]
 */
function sodium_crypto_secretstream_xchacha20poly1305_push (string &$state, string $message, string $additional_data = '', int $tag = 0): string {}

/**
 * {@inheritdoc}
 * @param string $header
 * @param string $key
 */
function sodium_crypto_secretstream_xchacha20poly1305_init_pull (string $header, string $key): string {}

/**
 * {@inheritdoc}
 * @param string $state
 * @param string $ciphertext
 * @param string $additional_data [optional]
 */
function sodium_crypto_secretstream_xchacha20poly1305_pull (string &$state, string $ciphertext, string $additional_data = ''): array|false {}

/**
 * {@inheritdoc}
 * @param string $state
 */
function sodium_crypto_secretstream_xchacha20poly1305_rekey (string &$state): void {}

/**
 * {@inheritdoc}
 * @param string $message
 * @param string $key
 */
function sodium_crypto_shorthash (string $message, string $key): string {}

/**
 * {@inheritdoc}
 */
function sodium_crypto_shorthash_keygen (): string {}

/**
 * {@inheritdoc}
 * @param string $message
 * @param string $secret_key
 */
function sodium_crypto_sign (string $message, string $secret_key): string {}

/**
 * {@inheritdoc}
 * @param string $message
 * @param string $secret_key
 */
function sodium_crypto_sign_detached (string $message, string $secret_key): string {}

/**
 * {@inheritdoc}
 * @param string $public_key
 */
function sodium_crypto_sign_ed25519_pk_to_curve25519 (string $public_key): string {}

/**
 * {@inheritdoc}
 * @param string $secret_key
 */
function sodium_crypto_sign_ed25519_sk_to_curve25519 (string $secret_key): string {}

/**
 * {@inheritdoc}
 */
function sodium_crypto_sign_keypair (): string {}

/**
 * {@inheritdoc}
 * @param string $secret_key
 * @param string $public_key
 */
function sodium_crypto_sign_keypair_from_secretkey_and_publickey (string $secret_key, string $public_key): string {}

/**
 * {@inheritdoc}
 * @param string $signed_message
 * @param string $public_key
 */
function sodium_crypto_sign_open (string $signed_message, string $public_key): string|false {}

/**
 * {@inheritdoc}
 * @param string $key_pair
 */
function sodium_crypto_sign_publickey (string $key_pair): string {}

/**
 * {@inheritdoc}
 * @param string $key_pair
 */
function sodium_crypto_sign_secretkey (string $key_pair): string {}

/**
 * {@inheritdoc}
 * @param string $secret_key
 */
function sodium_crypto_sign_publickey_from_secretkey (string $secret_key): string {}

/**
 * {@inheritdoc}
 * @param string $seed
 */
function sodium_crypto_sign_seed_keypair (string $seed): string {}

/**
 * {@inheritdoc}
 * @param string $signature
 * @param string $message
 * @param string $public_key
 */
function sodium_crypto_sign_verify_detached (string $signature, string $message, string $public_key): bool {}

/**
 * {@inheritdoc}
 * @param int $length
 * @param string $nonce
 * @param string $key
 */
function sodium_crypto_stream (int $length, string $nonce, string $key): string {}

/**
 * {@inheritdoc}
 */
function sodium_crypto_stream_keygen (): string {}

/**
 * {@inheritdoc}
 * @param string $message
 * @param string $nonce
 * @param string $key
 */
function sodium_crypto_stream_xor (string $message, string $nonce, string $key): string {}

/**
 * {@inheritdoc}
 * @param int $length
 * @param string $nonce
 * @param string $key
 */
function sodium_crypto_stream_xchacha20 (int $length, string $nonce, string $key): string {}

/**
 * {@inheritdoc}
 */
function sodium_crypto_stream_xchacha20_keygen (): string {}

/**
 * {@inheritdoc}
 * @param string $message
 * @param string $nonce
 * @param string $key
 */
function sodium_crypto_stream_xchacha20_xor (string $message, string $nonce, string $key): string {}

/**
 * {@inheritdoc}
 * @param string $message
 * @param string $nonce
 * @param int $counter
 * @param string $key
 */
function sodium_crypto_stream_xchacha20_xor_ic (string $message, string $nonce, int $counter, string $key): string {}

/**
 * {@inheritdoc}
 * @param string $string1
 * @param string $string2
 */
function sodium_add (string &$string1, string $string2): void {}

/**
 * {@inheritdoc}
 * @param string $string1
 * @param string $string2
 */
function sodium_compare (string $string1, string $string2): int {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function sodium_increment (string &$string): void {}

/**
 * {@inheritdoc}
 * @param string $string1
 * @param string $string2
 */
function sodium_memcmp (string $string1, string $string2): int {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function sodium_memzero (string &$string): void {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $block_size
 */
function sodium_pad (string $string, int $block_size): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $block_size
 */
function sodium_unpad (string $string, int $block_size): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function sodium_bin2hex (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string $ignore [optional]
 */
function sodium_hex2bin (string $string, string $ignore = ''): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $id
 */
function sodium_bin2base64 (string $string, int $id): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $id
 * @param string $ignore [optional]
 */
function sodium_base642bin (string $string, int $id, string $ignore = ''): string {}

/**
 * {@inheritdoc}
 * @param string $secret_key
 */
function sodium_crypto_scalarmult_base (string $secret_key): string {}

define ('SODIUM_LIBRARY_VERSION', "1.0.19");
define ('SODIUM_LIBRARY_MAJOR_VERSION', 26);
define ('SODIUM_LIBRARY_MINOR_VERSION', 1);
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_KEYBYTES', 32);
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_NSECBYTES', 0);
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_NPUBBYTES', 8);
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_ABYTES', 16);
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_IETF_KEYBYTES', 32);
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_IETF_NSECBYTES', 0);
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_IETF_NPUBBYTES', 12);
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_IETF_ABYTES', 16);
define ('SODIUM_CRYPTO_AEAD_XCHACHA20POLY1305_IETF_KEYBYTES', 32);
define ('SODIUM_CRYPTO_AEAD_XCHACHA20POLY1305_IETF_NSECBYTES', 0);
define ('SODIUM_CRYPTO_AEAD_XCHACHA20POLY1305_IETF_NPUBBYTES', 24);
define ('SODIUM_CRYPTO_AEAD_XCHACHA20POLY1305_IETF_ABYTES', 16);
define ('SODIUM_CRYPTO_AUTH_BYTES', 32);
define ('SODIUM_CRYPTO_AUTH_KEYBYTES', 32);
define ('SODIUM_CRYPTO_BOX_SEALBYTES', 48);
define ('SODIUM_CRYPTO_BOX_SECRETKEYBYTES', 32);
define ('SODIUM_CRYPTO_BOX_PUBLICKEYBYTES', 32);
define ('SODIUM_CRYPTO_BOX_KEYPAIRBYTES', 64);
define ('SODIUM_CRYPTO_BOX_MACBYTES', 16);
define ('SODIUM_CRYPTO_BOX_NONCEBYTES', 24);
define ('SODIUM_CRYPTO_BOX_SEEDBYTES', 32);
define ('SODIUM_CRYPTO_KDF_BYTES_MIN', 16);
define ('SODIUM_CRYPTO_KDF_BYTES_MAX', 64);
define ('SODIUM_CRYPTO_KDF_CONTEXTBYTES', 8);
define ('SODIUM_CRYPTO_KDF_KEYBYTES', 32);
define ('SODIUM_CRYPTO_KX_SEEDBYTES', 32);
define ('SODIUM_CRYPTO_KX_SESSIONKEYBYTES', 32);
define ('SODIUM_CRYPTO_KX_PUBLICKEYBYTES', 32);
define ('SODIUM_CRYPTO_KX_SECRETKEYBYTES', 32);
define ('SODIUM_CRYPTO_KX_KEYPAIRBYTES', 64);
define ('SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_ABYTES', 17);
define ('SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_HEADERBYTES', 24);
define ('SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_KEYBYTES', 32);
define ('SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_MESSAGEBYTES_MAX', 274877906816);
define ('SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_TAG_MESSAGE', 0);
define ('SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_TAG_PUSH', 1);
define ('SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_TAG_REKEY', 2);
define ('SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_TAG_FINAL', 3);
define ('SODIUM_CRYPTO_GENERICHASH_BYTES', 32);
define ('SODIUM_CRYPTO_GENERICHASH_BYTES_MIN', 16);
define ('SODIUM_CRYPTO_GENERICHASH_BYTES_MAX', 64);
define ('SODIUM_CRYPTO_GENERICHASH_KEYBYTES', 32);
define ('SODIUM_CRYPTO_GENERICHASH_KEYBYTES_MIN', 16);
define ('SODIUM_CRYPTO_GENERICHASH_KEYBYTES_MAX', 64);
define ('SODIUM_CRYPTO_PWHASH_ALG_ARGON2I13', 1);
define ('SODIUM_CRYPTO_PWHASH_ALG_ARGON2ID13', 2);
define ('SODIUM_CRYPTO_PWHASH_ALG_DEFAULT', 2);
define ('SODIUM_CRYPTO_PWHASH_SALTBYTES', 16);
define ('SODIUM_CRYPTO_PWHASH_STRPREFIX', "$argon2id$");
define ('SODIUM_CRYPTO_PWHASH_OPSLIMIT_INTERACTIVE', 2);
define ('SODIUM_CRYPTO_PWHASH_MEMLIMIT_INTERACTIVE', 67108864);
define ('SODIUM_CRYPTO_PWHASH_OPSLIMIT_MODERATE', 3);
define ('SODIUM_CRYPTO_PWHASH_MEMLIMIT_MODERATE', 268435456);
define ('SODIUM_CRYPTO_PWHASH_OPSLIMIT_SENSITIVE', 4);
define ('SODIUM_CRYPTO_PWHASH_MEMLIMIT_SENSITIVE', 1073741824);
define ('SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_SALTBYTES', 32);
define ('SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_STRPREFIX', "$7$");
define ('SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_OPSLIMIT_INTERACTIVE', 524288);
define ('SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_MEMLIMIT_INTERACTIVE', 16777216);
define ('SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_OPSLIMIT_SENSITIVE', 33554432);
define ('SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_MEMLIMIT_SENSITIVE', 1073741824);
define ('SODIUM_CRYPTO_SCALARMULT_BYTES', 32);
define ('SODIUM_CRYPTO_SCALARMULT_SCALARBYTES', 32);
define ('SODIUM_CRYPTO_SHORTHASH_BYTES', 8);
define ('SODIUM_CRYPTO_SHORTHASH_KEYBYTES', 16);
define ('SODIUM_CRYPTO_SECRETBOX_KEYBYTES', 32);
define ('SODIUM_CRYPTO_SECRETBOX_MACBYTES', 16);
define ('SODIUM_CRYPTO_SECRETBOX_NONCEBYTES', 24);
define ('SODIUM_CRYPTO_SIGN_BYTES', 64);
define ('SODIUM_CRYPTO_SIGN_SEEDBYTES', 32);
define ('SODIUM_CRYPTO_SIGN_PUBLICKEYBYTES', 32);
define ('SODIUM_CRYPTO_SIGN_SECRETKEYBYTES', 64);
define ('SODIUM_CRYPTO_SIGN_KEYPAIRBYTES', 96);
define ('SODIUM_CRYPTO_STREAM_NONCEBYTES', 24);
define ('SODIUM_CRYPTO_STREAM_KEYBYTES', 32);
define ('SODIUM_CRYPTO_STREAM_XCHACHA20_NONCEBYTES', 24);
define ('SODIUM_CRYPTO_STREAM_XCHACHA20_KEYBYTES', 32);
define ('SODIUM_BASE64_VARIANT_ORIGINAL', 1);
define ('SODIUM_BASE64_VARIANT_ORIGINAL_NO_PADDING', 3);
define ('SODIUM_BASE64_VARIANT_URLSAFE', 5);
define ('SODIUM_BASE64_VARIANT_URLSAFE_NO_PADDING', 7);
define ('SODIUM_CRYPTO_SCALARMULT_RISTRETTO255_BYTES', 32);
define ('SODIUM_CRYPTO_SCALARMULT_RISTRETTO255_SCALARBYTES', 32);
define ('SODIUM_CRYPTO_CORE_RISTRETTO255_BYTES', 32);
define ('SODIUM_CRYPTO_CORE_RISTRETTO255_HASHBYTES', 64);
define ('SODIUM_CRYPTO_CORE_RISTRETTO255_SCALARBYTES', 32);
define ('SODIUM_CRYPTO_CORE_RISTRETTO255_NONREDUCEDSCALARBYTES', 64);

// End of sodium v.8.3.0
