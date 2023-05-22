<?php

// Start of gnupg v.1.5.1

class gnupg  {
	const SIG_MODE_NORMAL = 0;
	const SIG_MODE_DETACH = 1;
	const SIG_MODE_CLEAR = 2;
	const VALIDITY_UNKNOWN = 0;
	const VALIDITY_UNDEFINED = 1;
	const VALIDITY_NEVER = 2;
	const VALIDITY_MARGINAL = 3;
	const VALIDITY_FULL = 4;
	const VALIDITY_ULTIMATE = 5;
	const PROTOCOL_OpenPGP = 0;
	const PROTOCOL_CMS = 1;
	const SIGSUM_VALID = 1;
	const SIGSUM_GREEN = 2;
	const SIGSUM_RED = 4;
	const SIGSUM_KEY_REVOKED = 16;
	const SIGSUM_KEY_EXPIRED = 32;
	const SIGSUM_SIG_EXPIRED = 64;
	const SIGSUM_KEY_MISSING = 128;
	const SIGSUM_CRL_MISSING = 256;
	const SIGSUM_CRL_TOO_OLD = 512;
	const SIGSUM_BAD_POLICY = 1024;
	const SIGSUM_SYS_ERROR = 2048;
	const ERROR_WARNING = 1;
	const ERROR_EXCEPTION = 2;
	const ERROR_SILENT = 3;
	const PK_RSA = 1;
	const PK_RSA_E = 2;
	const PK_RSA_S = 3;
	const PK_DSA = 17;
	const PK_ELG = 20;
	const PK_ELG_E = 16;
	const PK_ECC = 18;
	const PK_ECDSA = 301;
	const PK_ECDH = 302;
	const PK_EDDSA = 303;


	/**
	 * {@inheritdoc}
	 * @param mixed $options [optional]
	 */
	public function __construct ($options = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $pattern
	 * @param mixed $secret_only [optional]
	 */
	public function keyinfo ($pattern = null, $secret_only = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $text
	 * @param mixed $signature
	 * @param mixed $plaintext [optional]
	 */
	public function verify ($text = null, $signature = null, &$plaintext = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getengineinfo () {}

	/**
	 * {@inheritdoc}
	 */
	public function geterror () {}

	/**
	 * {@inheritdoc}
	 */
	public function geterrorinfo () {}

	/**
	 * {@inheritdoc}
	 */
	public function clearsignkeys () {}

	/**
	 * {@inheritdoc}
	 */
	public function clearencryptkeys () {}

	/**
	 * {@inheritdoc}
	 */
	public function cleardecryptkeys () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $armor
	 */
	public function setarmor ($armor = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $text
	 */
	public function encrypt ($text = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $enctext
	 */
	public function decrypt ($enctext = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $pattern
	 */
	public function export ($pattern = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $kye
	 */
	public function import ($kye = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getprotocol () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $signmode
	 */
	public function setsignmode ($signmode = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $text
	 */
	public function sign ($text = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $text
	 */
	public function encryptsign ($text = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $enctext
	 * @param mixed $plaintext
	 */
	public function decryptverify ($enctext = null, &$plaintext = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $kye
	 * @param mixed $passphrase
	 */
	public function addsignkey ($kye = null, $passphrase = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $kye
	 */
	public function addencryptkey ($kye = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $kye
	 * @param mixed $passphrase
	 */
	public function adddecryptkey ($kye = null, $passphrase = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $kye
	 * @param mixed $allow_secret
	 */
	public function deletekey ($kye = null, $allow_secret = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $pattern
	 */
	public function gettrustlist ($pattern = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $kyeid
	 */
	public function listsignatures ($kyeid = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $errnmode
	 */
	public function seterrormode ($errnmode = null) {}

}

class gnupg_keylistiterator implements Iterator, Traversable {

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

}

/**
 * Initialize a connection
 * @link http://www.php.net/manual/en/function.gnupg-init.php
 * @param array|null $options [optional] 
 * @return resource A GnuPG resource connection used by other GnuPG functions.
 */
function gnupg_init (?array $options = null) {}

/**
 * Returns an array with information about all keys that matches the given pattern
 * @link http://www.php.net/manual/en/function.gnupg-keyinfo.php
 * @param resource $identifier 
 * @param string $pattern 
 * @return array Returns an array with information about all keys that matches the given
 * pattern or false, if an error has occurred.
 */
function gnupg_keyinfo ($identifier, string $pattern): array {}

/**
 * Signs a given text
 * @link http://www.php.net/manual/en/function.gnupg-sign.php
 * @param resource $identifier 
 * @param string $plaintext 
 * @return string On success, this function returns the signed text or the signature.
 * On failure, this function returns false.
 */
function gnupg_sign ($identifier, string $plaintext): string {}

/**
 * Verifies a signed text
 * @link http://www.php.net/manual/en/function.gnupg-verify.php
 * @param resource $identifier 
 * @param string $signed_text 
 * @param string $signature 
 * @param string $plaintext [optional] 
 * @return array On success, this function returns information about the signature.
 * On failure, this function returns false.
 */
function gnupg_verify ($identifier, string $signed_text, string $signature, string &$plaintext = null): array {}

/**
 * Removes all keys which were set for signing before
 * @link http://www.php.net/manual/en/function.gnupg-clearsignkeys.php
 * @param resource $identifier 
 * @return bool Returns true on success or false on failure.
 */
function gnupg_clearsignkeys ($identifier): bool {}

/**
 * Removes all keys which were set for encryption before
 * @link http://www.php.net/manual/en/function.gnupg-clearencryptkeys.php
 * @param resource $identifier 
 * @return bool Returns true on success or false on failure.
 */
function gnupg_clearencryptkeys ($identifier): bool {}

/**
 * Removes all keys which were set for decryption before
 * @link http://www.php.net/manual/en/function.gnupg-cleardecryptkeys.php
 * @param resource $identifier 
 * @return bool Returns true on success or false on failure.
 */
function gnupg_cleardecryptkeys ($identifier): bool {}

/**
 * Toggle armored output
 * @link http://www.php.net/manual/en/function.gnupg-setarmor.php
 * @param resource $identifier 
 * @param int $armor 
 * @return bool Returns true on success or false on failure.
 */
function gnupg_setarmor ($identifier, int $armor): bool {}

/**
 * Encrypts a given text
 * @link http://www.php.net/manual/en/function.gnupg-encrypt.php
 * @param resource $identifier 
 * @param string $plaintext 
 * @return string On success, this function returns the encrypted text.
 * On failure, this function returns false.
 */
function gnupg_encrypt ($identifier, string $plaintext): string {}

/**
 * Decrypts a given text
 * @link http://www.php.net/manual/en/function.gnupg-decrypt.php
 * @param resource $identifier 
 * @param string $text 
 * @return string On success, this function returns the decrypted text.
 * On failure, this function returns false.
 */
function gnupg_decrypt ($identifier, string $text): string {}

/**
 * Exports a key
 * @link http://www.php.net/manual/en/function.gnupg-export.php
 * @param resource $identifier 
 * @param string $fingerprint 
 * @return string On success, this function returns the keydata.
 * On failure, this function returns false.
 */
function gnupg_export ($identifier, string $fingerprint): string {}

/**
 * Imports a key
 * @link http://www.php.net/manual/en/function.gnupg-import.php
 * @param resource $identifier 
 * @param string $keydata 
 * @return array On success, this function returns and info-array about the importprocess.
 * On failure, this function returns false.
 */
function gnupg_import ($identifier, string $keydata): array {}

/**
 * Returns the engine info
 * @link http://www.php.net/manual/en/function.gnupg-getengineinfo.php
 * @param resource $identifier 
 * @return array Returns an array with engine info consting of protocol,
 * file_name and home_dir.
 */
function gnupg_getengineinfo ($identifier): array {}

/**
 * Returns the currently active protocol for all operations
 * @link http://www.php.net/manual/en/function.gnupg-getprotocol.php
 * @param resource $identifier 
 * @return int Returns the currently active protocol, which can be one of
 * GNUPG_PROTOCOL_OpenPGP or
 * GNUPG_PROTOCOL_CMS.
 */
function gnupg_getprotocol ($identifier): int {}

/**
 * Sets the mode for signing
 * @link http://www.php.net/manual/en/function.gnupg-setsignmode.php
 * @param resource $identifier 
 * @param int $signmode 
 * @return bool Returns true on success or false on failure.
 */
function gnupg_setsignmode ($identifier, int $signmode): bool {}

/**
 * Encrypts and signs a given text
 * @link http://www.php.net/manual/en/function.gnupg-encryptsign.php
 * @param resource $identifier 
 * @param string $plaintext 
 * @return string On success, this function returns the encrypted and signed text.
 * On failure, this function returns false.
 */
function gnupg_encryptsign ($identifier, string $plaintext): string {}

/**
 * Decrypts and verifies a given text
 * @link http://www.php.net/manual/en/function.gnupg-decryptverify.php
 * @param resource $identifier 
 * @param string $text 
 * @param string $plaintext 
 * @return array On success, this function returns information about the signature and
 * fills the plaintext parameter with the decrypted text.
 * On failure, this function returns false.
 */
function gnupg_decryptverify ($identifier, string $text, string &$plaintext): array {}

/**
 * Returns the errortext, if a function fails
 * @link http://www.php.net/manual/en/function.gnupg-geterror.php
 * @param resource $identifier 
 * @return string Returns an errortext, if an error has occurred, otherwise false.
 */
function gnupg_geterror ($identifier): string {}

/**
 * Returns the error info
 * @link http://www.php.net/manual/en/function.gnupg-geterrorinfo.php
 * @param resource $identifier 
 * @return array Returns an array with error info.
 */
function gnupg_geterrorinfo ($identifier): array {}

/**
 * Add a key for signing
 * @link http://www.php.net/manual/en/function.gnupg-addsignkey.php
 * @param resource $identifier 
 * @param string $fingerprint 
 * @param string $passphrase [optional] 
 * @return bool Returns true on success or false on failure.
 */
function gnupg_addsignkey ($identifier, string $fingerprint, string $passphrase = null): bool {}

/**
 * Add a key for encryption
 * @link http://www.php.net/manual/en/function.gnupg-addencryptkey.php
 * @param resource $identifier 
 * @param string $fingerprint 
 * @return bool Returns true on success or false on failure.
 */
function gnupg_addencryptkey ($identifier, string $fingerprint): bool {}

/**
 * Add a key for decryption
 * @link http://www.php.net/manual/en/function.gnupg-adddecryptkey.php
 * @param resource $identifier 
 * @param string $fingerprint 
 * @param string $passphrase 
 * @return bool Returns true on success or false on failure.
 */
function gnupg_adddecryptkey ($identifier, string $fingerprint, string $passphrase): bool {}

/**
 * Delete a key from the keyring
 * @link http://www.php.net/manual/en/function.gnupg-deletekey.php
 * @param resource $identifier 
 * @param string $key 
 * @param bool $allow_secret 
 * @return bool Returns true on success or false on failure.
 */
function gnupg_deletekey ($identifier, string $key, bool $allow_secret): bool {}

/**
 * Search the trust items
 * @link http://www.php.net/manual/en/function.gnupg-gettrustlist.php
 * @param resource $identifier 
 * @param string $pattern 
 * @return array On success, this function returns an array of trust items.
 * On failure, this function returns null.
 */
function gnupg_gettrustlist ($identifier, string $pattern): array {}

/**
 * List key signatures
 * @link http://www.php.net/manual/en/function.gnupg-listsignatures.php
 * @param resource $identifier 
 * @param string $keyid 
 * @return array On success, this function returns an array of key signatures.
 * On failure, this function returns null.
 */
function gnupg_listsignatures ($identifier, string $keyid): array {}

/**
 * Sets the mode for error_reporting
 * @link http://www.php.net/manual/en/function.gnupg-seterrormode.php
 * @param resource $identifier 
 * @param int $errormode 
 * @return void No value is returned.
 */
function gnupg_seterrormode ($identifier, int $errormode): void {}


/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_SIG_MODE_NORMAL', 0);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_SIG_MODE_DETACH', 1);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_SIG_MODE_CLEAR', 2);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_VALIDITY_UNKNOWN', 0);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_VALIDITY_UNDEFINED', 1);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_VALIDITY_NEVER', 2);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_VALIDITY_MARGINAL', 3);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_VALIDITY_FULL', 4);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_VALIDITY_ULTIMATE', 5);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_PROTOCOL_OpenPGP', 0);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_PROTOCOL_CMS', 1);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_SIGSUM_VALID', 1);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_SIGSUM_GREEN', 2);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_SIGSUM_RED', 4);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_SIGSUM_KEY_REVOKED', 16);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_SIGSUM_KEY_EXPIRED', 32);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_SIGSUM_SIG_EXPIRED', 64);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_SIGSUM_KEY_MISSING', 128);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_SIGSUM_CRL_MISSING', 256);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_SIGSUM_CRL_TOO_OLD', 512);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_SIGSUM_BAD_POLICY', 1024);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_SIGSUM_SYS_ERROR', 2048);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_ERROR_WARNING', 1);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_ERROR_EXCEPTION', 2);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 * @var int
 */
define ('GNUPG_ERROR_SILENT', 3);
define ('GNUPG_PK_RSA', 1);
define ('GNUPG_PK_RSA_E', 2);
define ('GNUPG_PK_RSA_S', 3);
define ('GNUPG_PK_DSA', 17);
define ('GNUPG_PK_ELG', 20);
define ('GNUPG_PK_ELG_E', 16);
define ('GNUPG_PK_ECC', 18);
define ('GNUPG_PK_ECDSA', 301);
define ('GNUPG_PK_ECDH', 302);
define ('GNUPG_PK_EDDSA', 303);
define ('GNUPG_GPGME_VERSION', "1.20.0");

// End of gnupg v.1.5.1
