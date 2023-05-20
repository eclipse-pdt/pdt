<?php

// Start of gnupg v.1.5.1

/**
 * @link http://www.php.net/manual/en/class.gnupg.php
 */
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
	 * @param mixed $options [optional]
	 */
	public function __construct ($options = null) {}

	/**
	 * @param mixed $pattern
	 * @param mixed $secret_only [optional]
	 */
	public function keyinfo ($pattern = null, $secret_only = null) {}

	/**
	 * @param mixed $text
	 * @param mixed $signature
	 * @param mixed $plaintext [optional]
	 */
	public function verify ($text = null, $signature = null, &$plaintext = null) {}

	public function getengineinfo () {}

	public function geterror () {}

	public function geterrorinfo () {}

	public function clearsignkeys () {}

	public function clearencryptkeys () {}

	public function cleardecryptkeys () {}

	/**
	 * @param mixed $armor
	 */
	public function setarmor ($armor = null) {}

	/**
	 * @param mixed $text
	 */
	public function encrypt ($text = null) {}

	/**
	 * @param mixed $enctext
	 */
	public function decrypt ($enctext = null) {}

	/**
	 * @param mixed $pattern
	 */
	public function export ($pattern = null) {}

	/**
	 * @param mixed $kye
	 */
	public function import ($kye = null) {}

	public function getprotocol () {}

	/**
	 * @param mixed $signmode
	 */
	public function setsignmode ($signmode = null) {}

	/**
	 * @param mixed $text
	 */
	public function sign ($text = null) {}

	/**
	 * @param mixed $text
	 */
	public function encryptsign ($text = null) {}

	/**
	 * @param mixed $enctext
	 * @param mixed $plaintext
	 */
	public function decryptverify ($enctext = null, &$plaintext = null) {}

	/**
	 * @param mixed $kye
	 * @param mixed $passphrase
	 */
	public function addsignkey ($kye = null, $passphrase = null) {}

	/**
	 * @param mixed $kye
	 */
	public function addencryptkey ($kye = null) {}

	/**
	 * @param mixed $kye
	 * @param mixed $passphrase
	 */
	public function adddecryptkey ($kye = null, $passphrase = null) {}

	/**
	 * @param mixed $kye
	 * @param mixed $allow_secret
	 */
	public function deletekey ($kye = null, $allow_secret = null) {}

	/**
	 * @param mixed $pattern
	 */
	public function gettrustlist ($pattern = null) {}

	/**
	 * @param mixed $kyeid
	 */
	public function listsignatures ($kyeid = null) {}

	/**
	 * @param mixed $errnmode
	 */
	public function seterrormode ($errnmode = null) {}

}

class gnupg_keylistiterator implements Iterator, Traversable {

	public function __construct () {}

	public function current () {}

	public function key () {}

	public function next () {}

	public function rewind () {}

	public function valid () {}

}

/**
 * Initialize a connection
 * @link http://www.php.net/manual/en/function.gnupg-init.php
 * @param mixed $options [optional] Must be an associative array. It is used to change the default configuration
 * of the crypto engine.
 * <table>
 * Configuration overrides
 * <table>
 * <tr valign="top">
 * <td>key</td>
 * <td>type</td>
 * <td>description</td>
 * </tr>
 * <tr valign="top">
 * <td>file_name</td>
 * <td>string</td>
 * <td>
 * It is the file name of the executable program implementing this protocol
 * which is usually path of the gpg executable.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>home_dir</td>
 * <td>string</td>
 * <td>
 * It is the directory name of the configuration directory. It also overrides
 * GNUPGHOME environment variable that is used for the same
 * purpose.
 * </td>
 * </tr>
 * </table>
 * </table>
 * @return resource A GnuPG resource connection used by other GnuPG functions.
 */
function gnupg_init ($options = null) {}

/**
 * Returns an array with information about all keys that matches the given pattern
 * @link http://www.php.net/manual/en/function.gnupg-keyinfo.php
 * @param resource $identifier gnupg.identifier
 * @param string $pattern The pattern being checked against the keys.
 * @return array an array with information about all keys that matches the given
 * pattern or false, if an error has occurred.
 */
function gnupg_keyinfo ($identifier, string $pattern) {}

/**
 * Signs a given text
 * @link http://www.php.net/manual/en/function.gnupg-sign.php
 * @param resource $identifier gnupg.identifier
 * @param string $plaintext The plain text being signed.
 * @return string On success, this function returns the signed text or the signature.
 * On failure, this function returns false.
 */
function gnupg_sign ($identifier, string $plaintext) {}

/**
 * Verifies a signed text
 * @link http://www.php.net/manual/en/function.gnupg-verify.php
 * @param resource $identifier gnupg.identifier
 * @param string $signed_text The signed text.
 * @param string $signature The signature.
 * To verify a clearsigned text, set signature to false.
 * @param string $plaintext [optional] The plain text.
 * If this optional parameter is passed, it is
 * filled with the plain text.
 * @return array On success, this function returns information about the signature.
 * On failure, this function returns false.
 */
function gnupg_verify ($identifier, string $signed_text, string $signature, string &$plaintext = null) {}

/**
 * Removes all keys which were set for signing before
 * @link http://www.php.net/manual/en/function.gnupg-clearsignkeys.php
 * @param resource $identifier gnupg.identifier
 * @return bool true on success or false on failure
 */
function gnupg_clearsignkeys ($identifier) {}

/**
 * Removes all keys which were set for encryption before
 * @link http://www.php.net/manual/en/function.gnupg-clearencryptkeys.php
 * @param resource $identifier gnupg.identifier
 * @return bool true on success or false on failure
 */
function gnupg_clearencryptkeys ($identifier) {}

/**
 * Removes all keys which were set for decryption before
 * @link http://www.php.net/manual/en/function.gnupg-cleardecryptkeys.php
 * @param resource $identifier gnupg.identifier
 * @return bool true on success or false on failure
 */
function gnupg_cleardecryptkeys ($identifier) {}

/**
 * Toggle armored output
 * @link http://www.php.net/manual/en/function.gnupg-setarmor.php
 * @param resource $identifier gnupg.identifier
 * @param int $armor Pass a non-zero integer-value to this function to enable armored-output
 * (default).
 * Pass 0 to disable armored output.
 * @return bool true on success or false on failure
 */
function gnupg_setarmor ($identifier, int $armor) {}

/**
 * Encrypts a given text
 * @link http://www.php.net/manual/en/function.gnupg-encrypt.php
 * @param resource $identifier gnupg.identifier
 * @param string $plaintext The text being encrypted.
 * @return string On success, this function returns the encrypted text.
 * On failure, this function returns false.
 */
function gnupg_encrypt ($identifier, string $plaintext) {}

/**
 * Decrypts a given text
 * @link http://www.php.net/manual/en/function.gnupg-decrypt.php
 * @param resource $identifier gnupg.identifier
 * @param string $text The text being decrypted.
 * @return string On success, this function returns the decrypted text.
 * On failure, this function returns false.
 */
function gnupg_decrypt ($identifier, string $text) {}

/**
 * Exports a key
 * @link http://www.php.net/manual/en/function.gnupg-export.php
 * @param resource $identifier gnupg.identifier
 * @param string $fingerprint The fingerprint key.
 * @return string On success, this function returns the keydata.
 * On failure, this function returns false.
 */
function gnupg_export ($identifier, string $fingerprint) {}

/**
 * Imports a key
 * @link http://www.php.net/manual/en/function.gnupg-import.php
 * @param resource $identifier gnupg.identifier
 * @param string $keydata The data key that is being imported.
 * @return array On success, this function returns and info-array about the importprocess.
 * On failure, this function returns false.
 */
function gnupg_import ($identifier, string $keydata) {}

/**
 * Returns the engine info
 * @link http://www.php.net/manual/en/function.gnupg-getengineinfo.php
 * @param resource $identifier gnupg.identifier
 * @return array an array with engine info consting of protocol,
 * file_name and home_dir.
 */
function gnupg_getengineinfo ($identifier) {}

/**
 * Returns the currently active protocol for all operations
 * @link http://www.php.net/manual/en/function.gnupg-getprotocol.php
 * @param resource $identifier gnupg.identifier
 * @return int the currently active protocol, which can be one of
 * GNUPG_PROTOCOL_OpenPGP or
 * GNUPG_PROTOCOL_CMS.
 */
function gnupg_getprotocol ($identifier) {}

/**
 * Sets the mode for signing
 * @link http://www.php.net/manual/en/function.gnupg-setsignmode.php
 * @param resource $identifier gnupg.identifier
 * @param int $signmode 
 * @return bool true on success or false on failure
 */
function gnupg_setsignmode ($identifier, int $signmode) {}

/**
 * Encrypts and signs a given text
 * @link http://www.php.net/manual/en/function.gnupg-encryptsign.php
 * @param resource $identifier gnupg.identifier
 * @param string $plaintext The text being encrypted.
 * @return string On success, this function returns the encrypted and signed text.
 * On failure, this function returns false.
 */
function gnupg_encryptsign ($identifier, string $plaintext) {}

/**
 * Decrypts and verifies a given text
 * @link http://www.php.net/manual/en/function.gnupg-decryptverify.php
 * @param resource $identifier gnupg.identifier
 * @param string $text The text being decrypted.
 * @param string $plaintext The parameter plaintext gets filled with the decrypted
 * text.
 * @return array On success, this function returns information about the signature and
 * fills the plaintext parameter with the decrypted text.
 * On failure, this function returns false.
 */
function gnupg_decryptverify ($identifier, string $text, string &$plaintext) {}

/**
 * Returns the errortext, if a function fails
 * @link http://www.php.net/manual/en/function.gnupg-geterror.php
 * @param resource $identifier gnupg.identifier
 * @return string an errortext, if an error has occurred, otherwise false.
 */
function gnupg_geterror ($identifier) {}

/**
 * Returns the error info
 * @link http://www.php.net/manual/en/function.gnupg-geterrorinfo.php
 * @param resource $identifier gnupg.identifier
 * @return array an array with error info.
 */
function gnupg_geterrorinfo ($identifier) {}

/**
 * Add a key for signing
 * @link http://www.php.net/manual/en/function.gnupg-addsignkey.php
 * @param resource $identifier gnupg.identifier
 * @param string $fingerprint The fingerprint key.
 * @param string $passphrase [optional] The pass phrase.
 * @return bool true on success or false on failure
 */
function gnupg_addsignkey ($identifier, string $fingerprint, string $passphrase = null) {}

/**
 * Add a key for encryption
 * @link http://www.php.net/manual/en/function.gnupg-addencryptkey.php
 * @param resource $identifier gnupg.identifier
 * @param string $fingerprint The fingerprint key.
 * @return bool true on success or false on failure
 */
function gnupg_addencryptkey ($identifier, string $fingerprint) {}

/**
 * Add a key for decryption
 * @link http://www.php.net/manual/en/function.gnupg-adddecryptkey.php
 * @param resource $identifier gnupg.identifier
 * @param string $fingerprint The fingerprint key.
 * @param string $passphrase The pass phrase.
 * @return bool true on success or false on failure
 */
function gnupg_adddecryptkey ($identifier, string $fingerprint, string $passphrase) {}

/**
 * Delete a key from the keyring
 * @link http://www.php.net/manual/en/function.gnupg-deletekey.php
 * @param resource $identifier gnupg.identifier
 * @param string $key The key to delete.
 * @param bool $allow_secret It specifies whether to delete secret keys as well.
 * @return bool true on success or false on failure
 */
function gnupg_deletekey ($identifier, string $key, bool $allow_secret) {}

/**
 * Search the trust items
 * @link http://www.php.net/manual/en/function.gnupg-gettrustlist.php
 * @param resource $identifier gnupg.identifier
 * @param string $pattern Expression to limit the list of trust items to only the ones matching the pattern.
 * @return array On success, this function returns an array of trust items.
 * On failure, this function returns null.
 */
function gnupg_gettrustlist ($identifier, string $pattern) {}

/**
 * List key signatures
 * @link http://www.php.net/manual/en/function.gnupg-listsignatures.php
 * @param resource $identifier gnupg.identifier
 * @param string $keyid The key ID to list signatures for.
 * @return array On success, this function returns an array of key signatures.
 * On failure, this function returns null.
 */
function gnupg_listsignatures ($identifier, string $keyid) {}

/**
 * Sets the mode for error_reporting
 * @link http://www.php.net/manual/en/function.gnupg-seterrormode.php
 * @param resource $identifier gnupg.identifier
 * @param int $errormode <p>
 * The error mode.
 * </p>
 * <p>
 * errormode takes a constant indicating what type of
 * error_reporting should be used. The possible values are
 * GNUPG_ERROR_WARNING,
 * GNUPG_ERROR_EXCEPTION and
 * GNUPG_ERROR_SILENT.
 * By default GNUPG_ERROR_SILENT is used.
 * </p>
 * @return void 
 */
function gnupg_seterrormode ($identifier, int $errormode) {}


/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_SIG_MODE_NORMAL', 0);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_SIG_MODE_DETACH', 1);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_SIG_MODE_CLEAR', 2);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_VALIDITY_UNKNOWN', 0);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_VALIDITY_UNDEFINED', 1);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_VALIDITY_NEVER', 2);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_VALIDITY_MARGINAL', 3);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_VALIDITY_FULL', 4);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_VALIDITY_ULTIMATE', 5);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_PROTOCOL_OpenPGP', 0);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_PROTOCOL_CMS', 1);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_SIGSUM_VALID', 1);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_SIGSUM_GREEN', 2);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_SIGSUM_RED', 4);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_SIGSUM_KEY_REVOKED', 16);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_SIGSUM_KEY_EXPIRED', 32);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_SIGSUM_SIG_EXPIRED', 64);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_SIGSUM_KEY_MISSING', 128);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_SIGSUM_CRL_MISSING', 256);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_SIGSUM_CRL_TOO_OLD', 512);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_SIGSUM_BAD_POLICY', 1024);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_SIGSUM_SYS_ERROR', 2048);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_ERROR_WARNING', 1);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
 */
define ('GNUPG_ERROR_EXCEPTION', 2);

/**
 * 
 * @link http://www.php.net/manual/en/gnupg.constants.php
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
