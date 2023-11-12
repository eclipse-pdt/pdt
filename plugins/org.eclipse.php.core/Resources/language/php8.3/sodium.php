<?php

// Start of sodium v.8.2.6

/**
 * Exceptions thrown by the sodium functions.
 * @link http://www.php.net/manual/en/class.sodiumexception.php
 */
class SodiumException extends Exception implements Throwable, Stringable {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return int Returns the exception code as int in
	 * Exception but possibly as other type in
	 * Exception descendants (for example as
	 * string in PDOException).
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Returns the filename in which the exception was created.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Returns the line number where the exception was created.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString (): string {}

}

/**
 * Check if hardware supports AES256-GCM
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-aes256gcm-is-available.php
 * @return bool Returns true if it is safe to encrypt with AES-256-GCM, and false otherwise.
 */
function sodium_crypto_aead_aes256gcm_is_available (): bool {}

/**
 * Verify then decrypt with ChaCha20-Poly1305
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-chacha20poly1305-decrypt.php
 * @param string $ciphertext Must be in the format provided by sodium_crypto_aead_chacha20poly1305_encrypt
 * (ciphertext and tag, concatenated).
 * @param string $additional_data Additional, authenticated data. This is used in the verification of the authentication tag
 * appended to the ciphertext, but it is not encrypted or stored in the ciphertext.
 * @param string $nonce A number that must be only used once, per message. 8 bytes long.
 * @param string $key Encryption key (256-bit).
 * @return string|false Returns the plaintext on success, or false on failure.
 */
function sodium_crypto_aead_chacha20poly1305_decrypt (string $ciphertext, string $additional_data, string $nonce, string $key): string|false {}

/**
 * Encrypt then authenticate with ChaCha20-Poly1305
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-chacha20poly1305-encrypt.php
 * @param string $message The plaintext message to encrypt.
 * @param string $additional_data Additional, authenticated data. This is used in the verification of the authentication tag
 * appended to the ciphertext, but it is not encrypted or stored in the ciphertext.
 * @param string $nonce A number that must be only used once, per message. 8 bytes long.
 * @param string $key Encryption key (256-bit).
 * @return string Returns the ciphertext and tag on success, or false on failure.
 */
function sodium_crypto_aead_chacha20poly1305_encrypt (string $message, string $additional_data, string $nonce, string $key): string {}

/**
 * Generate a random ChaCha20-Poly1305 key.
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-chacha20poly1305-keygen.php
 * @return string Returns a 256-bit random key.
 */
function sodium_crypto_aead_chacha20poly1305_keygen (): string {}

/**
 * Verify that the ciphertext includes a valid tag
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-chacha20poly1305-ietf-decrypt.php
 * @param string $ciphertext Must be in the format provided by sodium_crypto_aead_chacha20poly1305_ietf_encrypt
 * (ciphertext and tag, concatenated).
 * @param string $additional_data Additional, authenticated data. This is used in the verification of the authentication tag
 * appended to the ciphertext, but it is not encrypted or stored in the ciphertext.
 * @param string $nonce A number that must be only used once, per message. 12 bytes long.
 * @param string $key Encryption key (256-bit).
 * @return string|false Returns the plaintext on success, or false on failure.
 */
function sodium_crypto_aead_chacha20poly1305_ietf_decrypt (string $ciphertext, string $additional_data, string $nonce, string $key): string|false {}

/**
 * Encrypt a message
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-chacha20poly1305-ietf-encrypt.php
 * @param string $message The plaintext message to encrypt.
 * @param string $additional_data Additional, authenticated data. This is used in the verification of the authentication tag
 * appended to the ciphertext, but it is not encrypted or stored in the ciphertext.
 * @param string $nonce A number that must be only used once, per message. 12 bytes long.
 * @param string $key Encryption key (256-bit).
 * @return string Returns the ciphertext and tag on success, or false on failure.
 */
function sodium_crypto_aead_chacha20poly1305_ietf_encrypt (string $message, string $additional_data, string $nonce, string $key): string {}

/**
 * Generate a random ChaCha20-Poly1305 (IETF) key.
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-chacha20poly1305-ietf-keygen.php
 * @return string Returns a 256-bit random key.
 */
function sodium_crypto_aead_chacha20poly1305_ietf_keygen (): string {}

/**
 * (Preferred) Verify then decrypt with XChaCha20-Poly1305
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-xchacha20poly1305-ietf-decrypt.php
 * @param string $ciphertext Must be in the format provided by sodium_crypto_aead_chacha20poly1305_ietf_encrypt
 * (ciphertext and tag, concatenated).
 * @param string $additional_data Additional, authenticated data. This is used in the verification of the authentication tag
 * appended to the ciphertext, but it is not encrypted or stored in the ciphertext.
 * @param string $nonce A number that must be only used once, per message. 24 bytes long.
 * This is a large enough bound to generate randomly (i.e. random_bytes).
 * @param string $key Encryption key (256-bit).
 * @return string|false Returns the plaintext on success, or false on failure.
 */
function sodium_crypto_aead_xchacha20poly1305_ietf_decrypt (string $ciphertext, string $additional_data, string $nonce, string $key): string|false {}

/**
 * Generate a random XChaCha20-Poly1305 key.
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-xchacha20poly1305-ietf-keygen.php
 * @return string Returns a 256-bit random key.
 */
function sodium_crypto_aead_xchacha20poly1305_ietf_keygen (): string {}

/**
 * (Preferred) Encrypt then authenticate with XChaCha20-Poly1305
 * @link http://www.php.net/manual/en/function.sodium-crypto-aead-xchacha20poly1305-ietf-encrypt.php
 * @param string $message The plaintext message to encrypt.
 * @param string $additional_data Additional, authenticated data. This is used in the verification of the authentication tag
 * appended to the ciphertext, but it is not encrypted or stored in the ciphertext.
 * @param string $nonce A number that must be only used once, per message. 24 bytes long.
 * This is a large enough bound to generate randomly (i.e. random_bytes).
 * @param string $key Encryption key (256-bit).
 * @return string Returns the ciphertext and tag on success, or false on failure.
 */
function sodium_crypto_aead_xchacha20poly1305_ietf_encrypt (string $message, string $additional_data, string $nonce, string $key): string {}

/**
 * Compute a tag for the message
 * @link http://www.php.net/manual/en/function.sodium-crypto-auth.php
 * @param string $message The message you intend to authenticate
 * @param string $key Authentication key
 * @return string Authentication tag
 */
function sodium_crypto_auth (string $message, string $key): string {}

/**
 * Generate a random key for sodium_crypto_auth
 * @link http://www.php.net/manual/en/function.sodium-crypto-auth-keygen.php
 * @return string Returns a 256-bit random key.
 */
function sodium_crypto_auth_keygen (): string {}

/**
 * Verifies that the tag is valid for the message
 * @link http://www.php.net/manual/en/function.sodium-crypto-auth-verify.php
 * @param string $mac Authentication tag produced by sodium_crypto_auth
 * @param string $message Message
 * @param string $key Authentication key
 * @return bool Returns true on success or false on failure.
 */
function sodium_crypto_auth_verify (string $mac, string $message, string $key): bool {}

/**
 * Authenticated public-key encryption
 * @link http://www.php.net/manual/en/function.sodium-crypto-box.php
 * @param string $message The message to be encrypted.
 * @param string $nonce A number that must be only used once, per message. 24 bytes long.
 * This is a large enough bound to generate randomly (i.e. random_bytes).
 * @param string $key_pair See sodium_crypto_box_keypair_from_secretkey_and_publickey.
 * This should include the sender's X25519 secret key and the recipient's X25519 public key.
 * @return string Returns the encrypted message (ciphertext plus authentication tag). The ciphertext will be
 * 16 bytes longer than the plaintext, and a raw binary string. See sodium_bin2base64
 * for safe encoding for storage.
 */
function sodium_crypto_box (string $message, string $nonce, string $key_pair): string {}

/**
 * Randomly generate a secret key and a corresponding public key
 * @link http://www.php.net/manual/en/function.sodium-crypto-box-keypair.php
 * @return string One string containing both the X25519 secret key and corresponding X25519 public key.
 */
function sodium_crypto_box_keypair (): string {}

/**
 * Deterministically derive the key pair from a single key
 * @link http://www.php.net/manual/en/function.sodium-crypto-box-seed-keypair.php
 * @param string $seed Some cryptographic input. Must be 32 bytes.
 * @return string X25519 Keypair (secret key and public key).
 */
function sodium_crypto_box_seed_keypair (string $seed): string {}

/**
 * Create a unified keypair string from a secret key and public key
 * @link http://www.php.net/manual/en/function.sodium-crypto-box-keypair-from-secretkey-and-publickey.php
 * @param string $secret_key Secret key.
 * @param string $public_key Public key.
 * @return string X25519 Keypair.
 */
function sodium_crypto_box_keypair_from_secretkey_and_publickey (string $secret_key, string $public_key): string {}

/**
 * Authenticated public-key decryption
 * @link http://www.php.net/manual/en/function.sodium-crypto-box-open.php
 * @param string $ciphertext The encrypted message to attempt to decrypt.
 * @param string $nonce A number that must be only used once, per message. 24 bytes long.
 * This is a large enough bound to generate randomly (i.e. random_bytes).
 * @param string $key_pair See sodium_crypto_box_keypair_from_secretkey_and_publickey.
 * This should include the sender's public key and the recipient's secret key.
 * @return string|false Returns the plaintext message on success, or false on failure.
 */
function sodium_crypto_box_open (string $ciphertext, string $nonce, string $key_pair): string|false {}

/**
 * Extract the public key from a crypto_box keypair
 * @link http://www.php.net/manual/en/function.sodium-crypto-box-publickey.php
 * @param string $key_pair A keypair, such as one generated by sodium_crypto_box_keypair or
 * sodium_crypto_box_seed_keypair
 * @return string X25519 public key.
 */
function sodium_crypto_box_publickey (string $key_pair): string {}

/**
 * Calculate the public key from a secret key
 * @link http://www.php.net/manual/en/function.sodium-crypto-box-publickey-from-secretkey.php
 * @param string $secret_key X25519 secret key
 * @return string X25519 public key.
 */
function sodium_crypto_box_publickey_from_secretkey (string $secret_key): string {}

/**
 * Anonymous public-key encryption
 * @link http://www.php.net/manual/en/function.sodium-crypto-box-seal.php
 * @param string $message The message to encrypt.
 * @param string $public_key The public key that corresponds to the only key that can decrypt the message.
 * @return string A ciphertext string in the format of (one-time public key, encrypted message, authentication tag).
 */
function sodium_crypto_box_seal (string $message, string $public_key): string {}

/**
 * Anonymous public-key decryption
 * @link http://www.php.net/manual/en/function.sodium-crypto-box-seal-open.php
 * @param string $ciphertext The encrypted message
 * @param string $key_pair The keypair of the recipient. Must include the secret key.
 * @return string|false The plaintext on success, or false on failure.
 */
function sodium_crypto_box_seal_open (string $ciphertext, string $key_pair): string|false {}

/**
 * Extracts the secret key from a crypto_box keypair
 * @link http://www.php.net/manual/en/function.sodium-crypto-box-secretkey.php
 * @param string $key_pair A keypair, such as one generated by sodium_crypto_box_keypair or
 * sodium_crypto_box_seed_keypair
 * @return string X25519 secret key.
 */
function sodium_crypto_box_secretkey (string $key_pair): string {}

/**
 * Adds an element
 * @link http://www.php.net/manual/en/function.sodium-crypto-core-ristretto255-add.php
 * @param string $p An element.
 * @param string $q An element.
 * @return string Returns a 32-byte random string.
 */
function sodium_crypto_core_ristretto255_add (string $p, string $q): string {}

/**
 * Maps a vector
 * @link http://www.php.net/manual/en/function.sodium-crypto-core-ristretto255-from-hash.php
 * @param string $s A 64-bytes vector.
 * @return string Returns a 32-byte random string.
 */
function sodium_crypto_core_ristretto255_from_hash (string $s): string {}

/**
 * Determines if a point on the ristretto255 curve
 * @link http://www.php.net/manual/en/function.sodium-crypto-core-ristretto255-is-valid-point.php
 * @param string $s An Elliptic-curve point.
 * @return bool Returns true if s is on the ristretto255 curve, false otherwise.
 */
function sodium_crypto_core_ristretto255_is_valid_point (string $s): bool {}

/**
 * Generates a random key
 * @link http://www.php.net/manual/en/function.sodium-crypto-core-ristretto255-random.php
 * @return string Returns a 32-byte random string.
 */
function sodium_crypto_core_ristretto255_random (): string {}

/**
 * Adds a scalar value
 * @link http://www.php.net/manual/en/function.sodium-crypto-core-ristretto255-scalar-add.php
 * @param string $x Scalar, representing the X coordinate.
 * @param string $y Scalar, representing the Y coordinate.
 * @return string Returns a 32-byte random string.
 */
function sodium_crypto_core_ristretto255_scalar_add (string $x, string $y): string {}

/**
 * The sodium_crypto_core_ristretto255_scalar_complement purpose
 * @link http://www.php.net/manual/en/function.sodium-crypto-core-ristretto255-scalar-complement.php
 * @param string $s Scalar value.
 * @return string Returns a 32-byte random string.
 */
function sodium_crypto_core_ristretto255_scalar_complement (string $s): string {}

/**
 * Inverts a scalar value
 * @link http://www.php.net/manual/en/function.sodium-crypto-core-ristretto255-scalar-invert.php
 * @param string $s Scalar value.
 * @return string Returns a 32-byte random string.
 */
function sodium_crypto_core_ristretto255_scalar_invert (string $s): string {}

/**
 * Multiplies a scalar value
 * @link http://www.php.net/manual/en/function.sodium-crypto-core-ristretto255-scalar-mul.php
 * @param string $x Scalar, representing the X coordinate.
 * @param string $y Scalar, representing the Y coordinate.
 * @return string Returns a 32-byte random string.
 */
function sodium_crypto_core_ristretto255_scalar_mul (string $x, string $y): string {}

/**
 * Negates a scalar value
 * @link http://www.php.net/manual/en/function.sodium-crypto-core-ristretto255-scalar-negate.php
 * @param string $s Scalar value.
 * @return string Returns a 32-byte random string.
 */
function sodium_crypto_core_ristretto255_scalar_negate (string $s): string {}

/**
 * Generates a random key
 * @link http://www.php.net/manual/en/function.sodium-crypto-core-ristretto255-scalar-random.php
 * @return string Returns a 32-byte random string.
 */
function sodium_crypto_core_ristretto255_scalar_random (): string {}

/**
 * Reduces a scalar value
 * @link http://www.php.net/manual/en/function.sodium-crypto-core-ristretto255-scalar-reduce.php
 * @param string $s Scalar value.
 * @return string Returns a 32-byte random string.
 */
function sodium_crypto_core_ristretto255_scalar_reduce (string $s): string {}

/**
 * Subtracts a scalar value
 * @link http://www.php.net/manual/en/function.sodium-crypto-core-ristretto255-scalar-sub.php
 * @param string $x Scalar, representing the X coordinate.
 * @param string $y Scalar, representing the Y coordinate.
 * @return string Returns a 32-byte random string.
 */
function sodium_crypto_core_ristretto255_scalar_sub (string $x, string $y): string {}

/**
 * Subtracts an element
 * @link http://www.php.net/manual/en/function.sodium-crypto-core-ristretto255-sub.php
 * @param string $p An element.
 * @param string $q An element.
 * @return string Returns a 32-byte random string.
 */
function sodium_crypto_core_ristretto255_sub (string $p, string $q): string {}

/**
 * Creates a new sodium keypair
 * @link http://www.php.net/manual/en/function.sodium-crypto-kx-keypair.php
 * @return string Returns the new keypair on success; throws an exception otherwise.
 */
function sodium_crypto_kx_keypair (): string {}

/**
 * Extract the public key from a crypto_kx keypair
 * @link http://www.php.net/manual/en/function.sodium-crypto-kx-publickey.php
 * @param string $key_pair X25519 keypair, such as one generated by
 * sodium_crypto_kx_keypair.
 * @return string X25519 public key.
 */
function sodium_crypto_kx_publickey (string $key_pair): string {}

/**
 * Extract the secret key from a crypto_kx keypair.
 * @link http://www.php.net/manual/en/function.sodium-crypto-kx-secretkey.php
 * @param string $key_pair X25519 keypair, such as one generated by
 * sodium_crypto_kx_keypair.
 * @return string X25519 secret key.
 */
function sodium_crypto_kx_secretkey (string $key_pair): string {}

/**
 * Description
 * @link http://www.php.net/manual/en/function.sodium-crypto-kx-seed-keypair.php
 * @param string $seed 
 * @return string 
 */
function sodium_crypto_kx_seed_keypair (string $seed): string {}

/**
 * Calculate the client-side session keys.
 * @link http://www.php.net/manual/en/function.sodium-crypto-kx-client-session-keys.php
 * @param string $client_key_pair A crypto_kx keypair, such as one generated by sodium_crypto_kx_keypair.
 * @param string $server_key A crypto_kx public key.
 * @return array An array consisting of two strings. The first should be used for receiving data
 * from the server. The second should be used for sending data to the server.
 */
function sodium_crypto_kx_client_session_keys (string $client_key_pair, string $server_key): array {}

/**
 * Calculate the server-side session keys.
 * @link http://www.php.net/manual/en/function.sodium-crypto-kx-server-session-keys.php
 * @param string $server_key_pair A crypto_kx keypair, such as one generated by sodium_crypto_kx_keypair.
 * @param string $client_key A crypto_kx public key.
 * @return array An array consisting of two strings. The first should be used for receiving data
 * from the client. The second should be used for sending data to the client.
 */
function sodium_crypto_kx_server_session_keys (string $server_key_pair, string $client_key): array {}

/**
 * Get a hash of the message
 * @link http://www.php.net/manual/en/function.sodium-crypto-generichash.php
 * @param string $message The message being hashed.
 * @param string $key [optional] (Optional) cryptographic key. This serves the same function as a HMAC key, but it's
 * utilized as a reserved section of the internal BLAKE2 state.
 * @param int $length [optional] Output size.
 * @return string The cryptographic hash as raw bytes. If a hex-encoded output is desired,
 * the result can be passed to sodium_bin2hex.
 */
function sodium_crypto_generichash (string $message, string $key = '""', int $length = SODIUM_CRYPTO_GENERICHASH_BYTES): string {}

/**
 * Generate a random generichash key
 * @link http://www.php.net/manual/en/function.sodium-crypto-generichash-keygen.php
 * @return string A random 256-bit key.
 */
function sodium_crypto_generichash_keygen (): string {}

/**
 * Initialize a hash for streaming
 * @link http://www.php.net/manual/en/function.sodium-crypto-generichash-init.php
 * @param string $key [optional] The generichash key.
 * @param int $length [optional] The expected output length of the hash function.
 * @return string Returns a hash state, serialized as a raw binary string.
 */
function sodium_crypto_generichash_init (string $key = '""', int $length = SODIUM_CRYPTO_GENERICHASH_BYTES): string {}

/**
 * Add message to a hash
 * @link http://www.php.net/manual/en/function.sodium-crypto-generichash-update.php
 * @param string $state The return value of sodium_crypto_generichash_init.
 * @param string $message Data to append to the hashing state.
 * @return true Always returns true.
 */
function sodium_crypto_generichash_update (string &$state, string $message): true {}

/**
 * Complete the hash
 * @link http://www.php.net/manual/en/function.sodium-crypto-generichash-final.php
 * @param string $state Hash state returned from sodium_crypto_generichash_init
 * @param int $length [optional] Output length.
 * @return string Cryptographic hash.
 */
function sodium_crypto_generichash_final (string &$state, int $length = SODIUM_CRYPTO_GENERICHASH_BYTES): string {}

/**
 * Derive a subkey
 * @link http://www.php.net/manual/en/function.sodium-crypto-kdf-derive-from-key.php
 * @param int $subkey_length Length of the key to return (in bytes)
 * @param int $subkey_id Return the Nth subkey from a given root key. Useful for seeking.
 * @param string $context Application-specific context.
 * @param string $key The root key from which the subkey is derived.
 * @return string A string of pseudorandom (raw binary) bytes.
 */
function sodium_crypto_kdf_derive_from_key (int $subkey_length, int $subkey_id, string $context, string $key): string {}

/**
 * Generate a random root key for the KDF interface
 * @link http://www.php.net/manual/en/function.sodium-crypto-kdf-keygen.php
 * @return string A random 256-bit key.
 */
function sodium_crypto_kdf_keygen (): string {}

/**
 * Derive a key from a password, using Argon2
 * @link http://www.php.net/manual/en/function.sodium-crypto-pwhash.php
 * @param int $length int; The length of the password hash to generate, in bytes.
 * @param string $password string; The password to generate a hash for.
 * @param string $salt A salt to add to the password before hashing. The salt should be unpredictable, ideally generated from a good random number source such as random_bytes, and have a length of at least SODIUM_CRYPTO_PWHASH_SALTBYTES bytes.
 * @param int $opslimit Represents a maximum amount of computations to perform. Raising this number will make the function require more CPU cycles to compute a key. There are some constants available to set the operations limit to appropriate values depending on intended use, in order of strength: SODIUM_CRYPTO_PWHASH_OPSLIMIT_INTERACTIVE, SODIUM_CRYPTO_PWHASH_OPSLIMIT_MODERATE and SODIUM_CRYPTO_PWHASH_OPSLIMIT_SENSITIVE.
 * @param int $memlimit The maximum amount of RAM that the function will use, in bytes. There are constants to help you choose an appropriate value, in order of size: SODIUM_CRYPTO_PWHASH_MEMLIMIT_INTERACTIVE, SODIUM_CRYPTO_PWHASH_MEMLIMIT_MODERATE, and SODIUM_CRYPTO_PWHASH_MEMLIMIT_SENSITIVE. Typically these should be paired with the matching opslimit values.
 * @param int $algo [optional] int A number indicating the hash algorithm to use. By default SODIUM_CRYPTO_PWHASH_ALG_DEFAULT (the currently recommended algorithm, which can change from one version of libsodium to another), or explicitly using SODIUM_CRYPTO_PWHASH_ALG_ARGON2ID13, representing the Argon2id algorithm version 1.3.
 * @return string Returns the derived key. The return value is a binary string of the hash, not an ASCII-encoded representation, and does not contain additional information about the parameters used to create the hash, so you will need to keep that information if you are ever going to verify the password in future. Use sodium_crypto_pwhash_str to avoid needing to do all that.
 */
function sodium_crypto_pwhash (int $length, string $password, string $salt, int $opslimit, int $memlimit, int $algo = SODIUM_CRYPTO_PWHASH_ALG_DEFAULT): string {}

/**
 * Get an ASCII-encoded hash
 * @link http://www.php.net/manual/en/function.sodium-crypto-pwhash-str.php
 * @param string $password string; The password to generate a hash for.
 * @param int $opslimit Represents a maximum amount of computations to perform. Raising this number will make the function require more CPU cycles to compute a key. There are constants available to set the operations limit to appropriate values depending on intended use, in order of strength: SODIUM_CRYPTO_PWHASH_OPSLIMIT_INTERACTIVE, SODIUM_CRYPTO_PWHASH_OPSLIMIT_MODERATE and SODIUM_CRYPTO_PWHASH_OPSLIMIT_SENSITIVE.
 * @param int $memlimit The maximum amount of RAM that the function will use, in bytes. There are constants to help you choose an appropriate value, in order of size: SODIUM_CRYPTO_PWHASH_MEMLIMIT_INTERACTIVE, SODIUM_CRYPTO_PWHASH_MEMLIMIT_MODERATE, and SODIUM_CRYPTO_PWHASH_MEMLIMIT_SENSITIVE. Typically these should be paired with the matching opslimit values.
 * @return string Returns the hashed password.
 * <p>In order to produce the same password hash from the same password, the same values for opslimit and memlimit must be used. These are embedded within the generated hash, so
 * everything that's needed to verify the hash is included. This allows
 * the sodium_crypto_pwhash_str_verify function to verify the hash without
 * needing separate storage for the other parameters.</p>
 */
function sodium_crypto_pwhash_str (string $password, int $opslimit, int $memlimit): string {}

/**
 * Verifies that a password matches a hash
 * @link http://www.php.net/manual/en/function.sodium-crypto-pwhash-str-verify.php
 * @param string $hash A hash created by password_hash.
 * @param string $password The user's password.
 * @return bool Returns true if the password and hash match, or false otherwise.
 */
function sodium_crypto_pwhash_str_verify (string $hash, string $password): bool {}

/**
 * Determine whether or not to rehash a password
 * @link http://www.php.net/manual/en/function.sodium-crypto-pwhash-str-needs-rehash.php
 * @param string $password Password hash
 * @param int $opslimit Configured opslimit; see sodium_crypto_pwhash_str
 * @param int $memlimit Configured memlimit; see sodium_crypto_pwhash_str
 * @return bool Returns true if the provided memlimit/opslimit do not match what's stored in the hash.
 * Returns false if they match.
 */
function sodium_crypto_pwhash_str_needs_rehash (string $password, int $opslimit, int $memlimit): bool {}

/**
 * Derives a key from a password, using scrypt
 * @link http://www.php.net/manual/en/function.sodium-crypto-pwhash-scryptsalsa208sha256.php
 * @param int $length The length of the password hash to generate, in bytes.
 * @param string $password The password to generate a hash for.
 * @param string $salt A salt to add to the password before hashing. The salt should be unpredictable, ideally generated from a good random number source such as random_bytes, and have a length of at least SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_SALTBYTES bytes.
 * @param int $opslimit Represents a maximum amount of computations to perform. Raising this number will make the function require more CPU cycles to compute a key. There are some constants available to set the operations limit to appropriate values depending on intended use, in order of strength: SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_OPSLIMIT_INTERACTIVE and SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_OPSLIMIT_SENSITIVE.
 * @param int $memlimit The maximum amount of RAM that the function will use, in bytes. There are constants to help you choose an appropriate value, in order of size: SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_MEMLIMIT_INTERACTIVE and SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_MEMLIMIT_SENSITIVE. Typically these should be paired with the matching opslimit values.
 * @return string A string of bytes of the desired length.
 */
function sodium_crypto_pwhash_scryptsalsa208sha256 (int $length, string $password, string $salt, int $opslimit, int $memlimit): string {}

/**
 * Get an ASCII encoded hash
 * @link http://www.php.net/manual/en/function.sodium-crypto-pwhash-scryptsalsa208sha256-str.php
 * @param string $password 
 * @param int $opslimit 
 * @param int $memlimit 
 * @return string 
 */
function sodium_crypto_pwhash_scryptsalsa208sha256_str (string $password, int $opslimit, int $memlimit): string {}

/**
 * Verify that the password is a valid password verification string
 * @link http://www.php.net/manual/en/function.sodium-crypto-pwhash-scryptsalsa208sha256-str-verify.php
 * @param string $hash 
 * @param string $password 
 * @return bool 
 */
function sodium_crypto_pwhash_scryptsalsa208sha256_str_verify (string $hash, string $password): bool {}

/**
 * Compute a shared secret given a user's secret key and another user's public key
 * @link http://www.php.net/manual/en/function.sodium-crypto-scalarmult.php
 * @param string $n scalar, which is typically a secret key
 * @param string $p point (x-coordinate), which is typically a public key
 * @return string A 32-byte random string.
 */
function sodium_crypto_scalarmult (string $n, string $p): string {}

/**
 * Computes a shared secret
 * @link http://www.php.net/manual/en/function.sodium-crypto-scalarmult-ristretto255.php
 * @param string $n A scalar, which is typically a secret key.
 * @param string $p A point (x-coordinate), which is typically a public key.
 * @return string Returns a 32-byte random string.
 */
function sodium_crypto_scalarmult_ristretto255 (string $n, string $p): string {}

/**
 * Calculates the public key from a secret key
 * @link http://www.php.net/manual/en/function.sodium-crypto-scalarmult-ristretto255-base.php
 * @param string $n A secret key.
 * @return string Returns a 32-byte random string.
 */
function sodium_crypto_scalarmult_ristretto255_base (string $n): string {}

/**
 * Authenticated shared-key encryption
 * @link http://www.php.net/manual/en/function.sodium-crypto-secretbox.php
 * @param string $message The plaintext message to encrypt.
 * @param string $nonce A number that must be only used once, per message. 24 bytes long.
 * This is a large enough bound to generate randomly (i.e. random_bytes).
 * @param string $key Encryption key (256-bit).
 * @return string Returns the encrypted string.
 */
function sodium_crypto_secretbox (string $message, string $nonce, string $key): string {}

/**
 * Generate random key for sodium_crypto_secretbox
 * @link http://www.php.net/manual/en/function.sodium-crypto-secretbox-keygen.php
 * @return string Returns the generated string of cryptographically secure random bytes.
 */
function sodium_crypto_secretbox_keygen (): string {}

/**
 * Authenticated shared-key decryption
 * @link http://www.php.net/manual/en/function.sodium-crypto-secretbox-open.php
 * @param string $ciphertext Must be in the format provided by sodium_crypto_secretbox
 * (ciphertext and tag, concatenated).
 * @param string $nonce A number that must be only used once, per message. 24 bytes long.
 * This is a large enough bound to generate randomly (i.e. random_bytes).
 * @param string $key Encryption key (256-bit).
 * @return string|false The decrypted string on success or false on failure.
 */
function sodium_crypto_secretbox_open (string $ciphertext, string $nonce, string $key): string|false {}

/**
 * Generate a random secretstream key.
 * @link http://www.php.net/manual/en/function.sodium-crypto-secretstream-xchacha20poly1305-keygen.php
 * @return string Returns a string of random bytes.
 */
function sodium_crypto_secretstream_xchacha20poly1305_keygen (): string {}

/**
 * Initialize a secretstream context for encryption
 * @link http://www.php.net/manual/en/function.sodium-crypto-secretstream-xchacha20poly1305-init-push.php
 * @param string $key Cryptography key. See sodium_crypto_secretstream_xchacha20poly1305_keygen.
 * @return array An array with two string values:
 */
function sodium_crypto_secretstream_xchacha20poly1305_init_push (string $key): array {}

/**
 * Encrypt a chunk of data so that it can safely be decrypted in a streaming API
 * @link http://www.php.net/manual/en/function.sodium-crypto-secretstream-xchacha20poly1305-push.php
 * @param string $state See sodium_crypto_secretstream_xchacha20poly1305_init_pull
 * and sodium_crypto_secretstream_xchacha20poly1305_init_push
 * @param string $message 
 * @param string $additional_data [optional] 
 * @param int $tag [optional] Optional. Can be used to assert decryption behavior
 * (i.e. re-keying or indicating the final chunk in a stream).
 * @return string Returns the encrypted ciphertext.
 */
function sodium_crypto_secretstream_xchacha20poly1305_push (string &$state, string $message, string $additional_data = '""', int $tag = SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_TAG_MESSAGE): string {}

/**
 * Initialize a secretstream context for decryption
 * @link http://www.php.net/manual/en/function.sodium-crypto-secretstream-xchacha20poly1305-init-pull.php
 * @param string $header The header of the secretstream. This should be one of the values produced by
 * sodium_crypto_secretstream_xchacha20poly1305_init_push.
 * @param string $key Encryption key (256-bit).
 * @return string Secretstream state.
 */
function sodium_crypto_secretstream_xchacha20poly1305_init_pull (string $header, string $key): string {}

/**
 * Decrypt a chunk of data from an encrypted stream
 * @link http://www.php.net/manual/en/function.sodium-crypto-secretstream-xchacha20poly1305-pull.php
 * @param string $state See sodium_crypto_secretstream_xchacha20poly1305_init_pull
 * and sodium_crypto_secretstream_xchacha20poly1305_init_push
 * @param string $ciphertext The ciphertext chunk to decrypt.
 * @param string $additional_data [optional] Optional additional data to include in the authentication tag.
 * @return array|false An array with two values:
 * <p>
 * <br>
 * <p>
 * string; The decrypted chunk
 * </p>
 * <br>
 * <p>
 * int; An optional tag (if provided during push). Possible values:
 * <p>
 * SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_TAG_MESSAGE:
 * the most common tag, that doesn't add any information about the nature of the message.
 * SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_TAG_FINAL:
 * indicates that the message marks the end of the stream, and erases the secret key used to encrypt the previous sequence.
 * SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_TAG_PUSH:
 * indicates that the message marks the end of a set of messages, but not the end of the stream.
 * For example, a huge JSON string sent as multiple chunks can use this tag to indicate to the application that the
 * string is complete and that it can be decoded. But the stream itself is not closed, and more data may follow.
 * SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_TAG_REKEY:
 * "forget" the key used to encrypt this message and the previous ones, and derive a new secret key.
 * </p>
 * </p>
 * </p>
 * <p>string; The decrypted chunk</p>
 * <p>int; An optional tag (if provided during push). Possible values:
 * <p>
 * SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_TAG_MESSAGE:
 * the most common tag, that doesn't add any information about the nature of the message.
 * SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_TAG_FINAL:
 * indicates that the message marks the end of the stream, and erases the secret key used to encrypt the previous sequence.
 * SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_TAG_PUSH:
 * indicates that the message marks the end of a set of messages, but not the end of the stream.
 * For example, a huge JSON string sent as multiple chunks can use this tag to indicate to the application that the
 * string is complete and that it can be decoded. But the stream itself is not closed, and more data may follow.
 * SODIUM_CRYPTO_SECRETSTREAM_XCHACHA20POLY1305_TAG_REKEY:
 * "forget" the key used to encrypt this message and the previous ones, and derive a new secret key.
 * </p></p>
 */
function sodium_crypto_secretstream_xchacha20poly1305_pull (string &$state, string $ciphertext, string $additional_data = '""'): array|false {}

/**
 * Explicitly rotate the key in the secretstream state
 * @link http://www.php.net/manual/en/function.sodium-crypto-secretstream-xchacha20poly1305-rekey.php
 * @param string $state Secretstream state.
 * @return void No value is returned.
 */
function sodium_crypto_secretstream_xchacha20poly1305_rekey (string &$state): void {}

/**
 * Compute a short hash of a message and key
 * @link http://www.php.net/manual/en/function.sodium-crypto-shorthash.php
 * @param string $message The message to hash.
 * @param string $key The hash key.
 * @return string 
 */
function sodium_crypto_shorthash (string $message, string $key): string {}

/**
 * Get random bytes for key
 * @link http://www.php.net/manual/en/function.sodium-crypto-shorthash-keygen.php
 * @return string 
 */
function sodium_crypto_shorthash_keygen (): string {}

/**
 * Sign a message
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign.php
 * @param string $message Message to sign.
 * @param string $secret_key Secret key. See sodium_crypto_sign_secretkey
 * @return string Signed message (not encrypted).
 */
function sodium_crypto_sign (string $message, string $secret_key): string {}

/**
 * Sign the message
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-detached.php
 * @param string $message Message to sign.
 * @param string $secret_key Secret key. See sodium_crypto_sign_secretkey
 * @return string Cryptographic signature.
 */
function sodium_crypto_sign_detached (string $message, string $secret_key): string {}

/**
 * Convert an Ed25519 public key to a Curve25519 public key
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-ed25519-pk-to-curve25519.php
 * @param string $public_key Public key suitable for the crypto_sign functions.
 * @return string Public key suitable for the crypto_box functions.
 */
function sodium_crypto_sign_ed25519_pk_to_curve25519 (string $public_key): string {}

/**
 * Convert an Ed25519 secret key to a Curve25519 secret key
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-ed25519-sk-to-curve25519.php
 * @param string $secret_key Secret key suitable for the crypto_sign functions.
 * @return string Secret key suitable for the crypto_box functions.
 */
function sodium_crypto_sign_ed25519_sk_to_curve25519 (string $secret_key): string {}

/**
 * Randomly generate a secret key and a corresponding public key
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-keypair.php
 * @return string Ed25519 keypair.
 */
function sodium_crypto_sign_keypair (): string {}

/**
 * Join a secret key and public key together
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-keypair-from-secretkey-and-publickey.php
 * @param string $secret_key Ed25519 secret key
 * @param string $public_key Ed25519 public key
 * @return string Keypair
 */
function sodium_crypto_sign_keypair_from_secretkey_and_publickey (string $secret_key, string $public_key): string {}

/**
 * Check that the signed message has a valid signature
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-open.php
 * @param string $signed_message A message signed with sodium_crypto_sign
 * @param string $public_key An Ed25519 public key
 * @return string|false Returns the original signed message on success, or false on failure.
 */
function sodium_crypto_sign_open (string $signed_message, string $public_key): string|false {}

/**
 * Extract the Ed25519 public key from a keypair
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-publickey.php
 * @param string $key_pair Ed25519 keypair (see: sodium_crypto_sign_keypair)
 * @return string Ed25519 public key
 */
function sodium_crypto_sign_publickey (string $key_pair): string {}

/**
 * Extract the Ed25519 secret key from a keypair
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-secretkey.php
 * @param string $key_pair Ed25519 keypair (see: sodium_crypto_sign_keypair)
 * @return string Ed25519 secret key
 */
function sodium_crypto_sign_secretkey (string $key_pair): string {}

/**
 * Extract the Ed25519 public key from the secret key
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-publickey-from-secretkey.php
 * @param string $secret_key Ed25519 secret key
 * @return string Ed25519 public key
 */
function sodium_crypto_sign_publickey_from_secretkey (string $secret_key): string {}

/**
 * Deterministically derive the key pair from a single key
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-seed-keypair.php
 * @param string $seed Some cryptographic input. Must be 32 bytes.
 * @return string Keypair (secret key and public key)
 */
function sodium_crypto_sign_seed_keypair (string $seed): string {}

/**
 * Verify signature for the message
 * @link http://www.php.net/manual/en/function.sodium-crypto-sign-verify-detached.php
 * @param string $signature The cryptographic signature obtained from sodium_crypto_sign_detached
 * @param string $message The message being verified
 * @param string $public_key Ed25519 public key
 * @return bool Returns true on success or false on failure.
 */
function sodium_crypto_sign_verify_detached (string $signature, string $message, string $public_key): bool {}

/**
 * Generate a deterministic sequence of bytes from a seed
 * @link http://www.php.net/manual/en/function.sodium-crypto-stream.php
 * @param int $length The number of bytes to return.
 * @param string $nonce A number that must be only used once, per message. 24 bytes long.
 * This is a large enough bound to generate randomly (i.e. random_bytes).
 * @param string $key Encryption key (256-bit).
 * @return string String of pseudorandom bytes.
 */
function sodium_crypto_stream (int $length, string $nonce, string $key): string {}

/**
 * Generate a random sodium_crypto_stream key.
 * @link http://www.php.net/manual/en/function.sodium-crypto-stream-keygen.php
 * @return string Encryption key (256-bit).
 */
function sodium_crypto_stream_keygen (): string {}

/**
 * Encrypt a message without authentication
 * @link http://www.php.net/manual/en/function.sodium-crypto-stream-xor.php
 * @param string $message The message to encrypt
 * @param string $nonce A number that must be only used once, per message. 24 bytes long.
 * This is a large enough bound to generate randomly (i.e. random_bytes).
 * @param string $key Encryption key (256-bit).
 * @return string Encrypted message.
 */
function sodium_crypto_stream_xor (string $message, string $nonce, string $key): string {}

/**
 * Expands the key and nonce into a keystream of pseudorandom bytes
 * @link http://www.php.net/manual/en/function.sodium-crypto-stream-xchacha20.php
 * @param int $length Number of bytes desired.
 * @param string $nonce 24-byte nonce.
 * @param string $key Key, possibly generated from sodium_crypto_stream_xchacha20_keygen.
 * @return string Returns a pseudorandom stream that can be used with sodium_crypto_stream_xchacha20_xor.
 */
function sodium_crypto_stream_xchacha20 (int $length, string $nonce, string $key): string {}

/**
 * Returns a secure random key
 * @link http://www.php.net/manual/en/function.sodium-crypto-stream-xchacha20-keygen.php
 * @return string Returns a 32-byte secure random key for use with sodium_crypto_stream_xchacha20.
 */
function sodium_crypto_stream_xchacha20_keygen (): string {}

/**
 * Encrypts a message using a nonce and a secret key (no authentication)
 * @link http://www.php.net/manual/en/function.sodium-crypto-stream-xchacha20-xor.php
 * @param string $message The message to encrypt.
 * @param string $nonce 24-byte nonce.
 * @param string $key Key, possibly generated from sodium_crypto_stream_xchacha20_keygen.
 * @return string Encrypted message.
 */
function sodium_crypto_stream_xchacha20_xor (string $message, string $nonce, string $key): string {}

/**
 * Encrypts a message using a nonce and a secret key (no authentication)
 * @link http://www.php.net/manual/en/function.sodium-crypto-stream-xchacha20-xor-ic.php
 * @param string $message The message to encrypt.
 * @param string $nonce 24-byte nonce.
 * @param int $counter The initial value of the block counter.
 * @param string $key Key, possibly generated from sodium_crypto_stream_xchacha20_keygen.
 * @return string Encrypted message, or false on failure.
 */
function sodium_crypto_stream_xchacha20_xor_ic (string $message, string $nonce, int $counter, string $key): string {}

/**
 * Add large numbers
 * @link http://www.php.net/manual/en/function.sodium-add.php
 * @param string $string1 String representing an arbitrary-length unsigned integer in little-endian byte order.
 * This parameter is passed by reference and will hold the sum of the two parameters.
 * @param string $string2 String representing an arbitrary-length unsigned integer in little-endian byte order.
 * @return void No value is returned.
 */
function sodium_add (string &$string1, string $string2): void {}

/**
 * Compare large numbers
 * @link http://www.php.net/manual/en/function.sodium-compare.php
 * @param string $string1 Left operand
 * @param string $string2 Right operand
 * @return int Returns -1 if string1 is less than string2.
 * <p>Returns 1 if string1 is greater than string2.</p>
 * <p>Returns 0 if both strings are equal.</p>
 */
function sodium_compare (string $string1, string $string2): int {}

/**
 * Increment large number
 * @link http://www.php.net/manual/en/function.sodium-increment.php
 * @param string $string String to increment.
 * @return void No value is returned.
 */
function sodium_increment (string &$string): void {}

/**
 * Test for equality in constant-time
 * @link http://www.php.net/manual/en/function.sodium-memcmp.php
 * @param string $string1 String to compare
 * @param string $string2 Other string to compare
 * @return int Returns 0 if both strings are equal; -1 otherwise.
 */
function sodium_memcmp (string $string1, string $string2): int {}

/**
 * Overwrite a string with NUL characters
 * @link http://www.php.net/manual/en/function.sodium-memzero.php
 * @param string $string String.
 * @return void No value is returned.
 */
function sodium_memzero (string &$string): void {}

/**
 * Add padding data
 * @link http://www.php.net/manual/en/function.sodium-pad.php
 * @param string $string Unpadded string.
 * @param int $block_size The string will be padded until it is an even multiple of the block size.
 * @return string Padded string.
 */
function sodium_pad (string $string, int $block_size): string {}

/**
 * Remove padding data
 * @link http://www.php.net/manual/en/function.sodium-unpad.php
 * @param string $string Padded string.
 * @param int $block_size The block size for padding.
 * @return string Unpadded string.
 */
function sodium_unpad (string $string, int $block_size): string {}

/**
 * Encode to hexadecimal
 * @link http://www.php.net/manual/en/function.sodium-bin2hex.php
 * @param string $string Raw binary string.
 * @return string Hex encoded string.
 */
function sodium_bin2hex (string $string): string {}

/**
 * Decodes a hexadecimally encoded binary string
 * @link http://www.php.net/manual/en/function.sodium-hex2bin.php
 * @param string $string Hexadecimal representation of data.
 * @param string $ignore [optional] Optional string argument for characters to ignore.
 * @return string Returns the binary representation of the given string data.
 */
function sodium_hex2bin (string $string, string $ignore = '""'): string {}

/**
 * Encodes a raw binary string with base64.
 * @link http://www.php.net/manual/en/function.sodium-bin2base64.php
 * @param string $string Raw binary string.
 * @param int $id SODIUM_BASE64_VARIANT_ORIGINAL for standard (A-Za-z0-9/\+)
 * Base64 encoding.
 * SODIUM_BASE64_VARIANT_ORIGINAL_NO_PADDING for standard (A-Za-z0-9/\+)
 * Base64 encoding, without = padding characters.
 * SODIUM_BASE64_VARIANT_URLSAFE for URL-safe (A-Za-z0-9\-_) Base64 encoding.
 * SODIUM_BASE64_VARIANT_URLSAFE_NO_PADDING for URL-safe (A-Za-z0-9\-_)
 * Base64 encoding, without = padding characters.
 * @return string Base64-encoded string.
 */
function sodium_bin2base64 (string $string, int $id): string {}

/**
 * Decodes a base64-encoded string into raw binary.
 * @link http://www.php.net/manual/en/function.sodium-base642bin.php
 * @param string $string string; Encoded string.
 * @param int $id SODIUM_BASE64_VARIANT_ORIGINAL for standard (A-Za-z0-9/\+)
 * Base64 encoding.
 * SODIUM_BASE64_VARIANT_ORIGINAL_NO_PADDING for standard (A-Za-z0-9/\+)
 * Base64 encoding, without = padding characters.
 * SODIUM_BASE64_VARIANT_URLSAFE for URL-safe (A-Za-z0-9\-_) Base64 encoding.
 * SODIUM_BASE64_VARIANT_URLSAFE_NO_PADDING for URL-safe (A-Za-z0-9\-_)
 * Base64 encoding, without = padding characters.
 * @param string $ignore [optional] Characters to ignore when decoding (e.g. whitespace characters).
 * @return string Decoded string.
 */
function sodium_base642bin (string $string, int $id, string $ignore = '""'): string {}

/**
 * Alias of sodium_crypto_box_publickey_from_secretkey
 * @link http://www.php.net/manual/en/function.sodium-crypto-scalarmult-base.php
 * @param string $secret_key X25519 secret key
 * @return string X25519 public key.
 */
function sodium_crypto_scalarmult_base (string $secret_key): string {}


/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var string
 */
define ('SODIUM_LIBRARY_VERSION', "1.0.18");

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_LIBRARY_MAJOR_VERSION', 10);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_LIBRARY_MINOR_VERSION', 3);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_KEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_NSECBYTES', 0);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_NPUBBYTES', 8);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_ABYTES', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_IETF_KEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_IETF_NSECBYTES', 0);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_IETF_NPUBBYTES', 12);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_AEAD_CHACHA20POLY1305_IETF_ABYTES', 16);
define ('SODIUM_CRYPTO_AEAD_XCHACHA20POLY1305_IETF_KEYBYTES', 32);
define ('SODIUM_CRYPTO_AEAD_XCHACHA20POLY1305_IETF_NSECBYTES', 0);
define ('SODIUM_CRYPTO_AEAD_XCHACHA20POLY1305_IETF_NPUBBYTES', 24);
define ('SODIUM_CRYPTO_AEAD_XCHACHA20POLY1305_IETF_ABYTES', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_AUTH_BYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_AUTH_KEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_BOX_SEALBYTES', 48);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_BOX_SECRETKEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_BOX_PUBLICKEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_BOX_KEYPAIRBYTES', 64);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_BOX_MACBYTES', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_BOX_NONCEBYTES', 24);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_BOX_SEEDBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_KDF_BYTES_MIN', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_KDF_BYTES_MAX', 64);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_KDF_CONTEXTBYTES', 8);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_KDF_KEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_KX_SEEDBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_KX_SESSIONKEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_KX_PUBLICKEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_KX_SECRETKEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
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
 * @var int
 */
define ('SODIUM_CRYPTO_GENERICHASH_BYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_GENERICHASH_BYTES_MIN', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_GENERICHASH_BYTES_MAX', 64);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_GENERICHASH_KEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_GENERICHASH_KEYBYTES_MIN', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_GENERICHASH_KEYBYTES_MAX', 64);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_PWHASH_ALG_ARGON2I13', 1);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_PWHASH_ALG_ARGON2ID13', 2);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_PWHASH_ALG_DEFAULT', 2);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_PWHASH_SALTBYTES', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var string
 */
define ('SODIUM_CRYPTO_PWHASH_STRPREFIX', "$argon2id$");

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_PWHASH_OPSLIMIT_INTERACTIVE', 2);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_PWHASH_MEMLIMIT_INTERACTIVE', 67108864);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_PWHASH_OPSLIMIT_MODERATE', 3);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_PWHASH_MEMLIMIT_MODERATE', 268435456);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_PWHASH_OPSLIMIT_SENSITIVE', 4);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_PWHASH_MEMLIMIT_SENSITIVE', 1073741824);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_SALTBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var string
 */
define ('SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_STRPREFIX', "$7$");

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_OPSLIMIT_INTERACTIVE', 524288);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_MEMLIMIT_INTERACTIVE', 16777216);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_OPSLIMIT_SENSITIVE', 33554432);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_PWHASH_SCRYPTSALSA208SHA256_MEMLIMIT_SENSITIVE', 1073741824);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_SCALARMULT_BYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_SCALARMULT_SCALARBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_SHORTHASH_BYTES', 8);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_SHORTHASH_KEYBYTES', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_SECRETBOX_KEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_SECRETBOX_MACBYTES', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_SECRETBOX_NONCEBYTES', 24);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_SIGN_BYTES', 64);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_SIGN_SEEDBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_SIGN_PUBLICKEYBYTES', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_SIGN_SECRETKEYBYTES', 64);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_SIGN_KEYPAIRBYTES', 96);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_STREAM_NONCEBYTES', 24);

/**
 * 
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_STREAM_KEYBYTES', 32);

/**
 * Available as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_STREAM_XCHACHA20_NONCEBYTES', 24);

/**
 * Available as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_STREAM_XCHACHA20_KEYBYTES', 32);
define ('SODIUM_BASE64_VARIANT_ORIGINAL', 1);
define ('SODIUM_BASE64_VARIANT_ORIGINAL_NO_PADDING', 3);
define ('SODIUM_BASE64_VARIANT_URLSAFE', 5);
define ('SODIUM_BASE64_VARIANT_URLSAFE_NO_PADDING', 7);

/**
 * Available as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_SCALARMULT_RISTRETTO255_BYTES', 32);

/**
 * Available as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_SCALARMULT_RISTRETTO255_SCALARBYTES', 32);

/**
 * Available as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_CORE_RISTRETTO255_BYTES', 32);

/**
 * Available as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_CORE_RISTRETTO255_HASHBYTES', 64);

/**
 * Available as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_CORE_RISTRETTO255_SCALARBYTES', 32);

/**
 * Available as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/sodium.constants.php
 * @var int
 */
define ('SODIUM_CRYPTO_CORE_RISTRETTO255_NONREDUCEDSCALARBYTES', 64);

// End of sodium v.8.2.6
