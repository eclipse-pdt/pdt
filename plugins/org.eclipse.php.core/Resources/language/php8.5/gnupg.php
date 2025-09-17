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
 * {@inheritdoc}
 * @param mixed $options [optional]
 */
function gnupg_init ($options = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 * @param mixed $pattern
 * @param mixed $secret_only [optional]
 */
function gnupg_keyinfo ($res = null, $pattern = null, $secret_only = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 * @param mixed $text
 */
function gnupg_sign ($res = null, $text = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 * @param mixed $text
 * @param mixed $signature
 * @param mixed $plaintext [optional]
 */
function gnupg_verify ($res = null, $text = null, $signature = null, &$plaintext = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 */
function gnupg_clearsignkeys ($res = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 */
function gnupg_clearencryptkeys ($res = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 */
function gnupg_cleardecryptkeys ($res = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 * @param mixed $armor
 */
function gnupg_setarmor ($res = null, $armor = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 * @param mixed $text
 */
function gnupg_encrypt ($res = null, $text = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 * @param mixed $enctext
 */
function gnupg_decrypt ($res = null, $enctext = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 * @param mixed $pattern
 */
function gnupg_export ($res = null, $pattern = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 * @param mixed $kye
 */
function gnupg_import ($res = null, $kye = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 */
function gnupg_getengineinfo ($res = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 */
function gnupg_getprotocol ($res = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 * @param mixed $signmode
 */
function gnupg_setsignmode ($res = null, $signmode = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 * @param mixed $text
 */
function gnupg_encryptsign ($res = null, $text = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 * @param mixed $enctext
 * @param mixed $plaintext
 */
function gnupg_decryptverify ($res = null, $enctext = null, &$plaintext = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 */
function gnupg_geterror ($res = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 */
function gnupg_geterrorinfo ($res = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 * @param mixed $kye
 * @param mixed $passphrase
 */
function gnupg_addsignkey ($res = null, $kye = null, $passphrase = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 * @param mixed $kye
 */
function gnupg_addencryptkey ($res = null, $kye = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 * @param mixed $kye
 * @param mixed $passphrase
 */
function gnupg_adddecryptkey ($res = null, $kye = null, $passphrase = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 * @param mixed $kye
 * @param mixed $allow_secret
 */
function gnupg_deletekey ($res = null, $kye = null, $allow_secret = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 * @param mixed $pattern
 */
function gnupg_gettrustlist ($res = null, $pattern = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 * @param mixed $kyeid
 */
function gnupg_listsignatures ($res = null, $kyeid = null) {}

/**
 * {@inheritdoc}
 * @param mixed $res
 * @param mixed $errnmode
 */
function gnupg_seterrormode ($res = null, $errnmode = null) {}

define ('GNUPG_SIG_MODE_NORMAL', 0);
define ('GNUPG_SIG_MODE_DETACH', 1);
define ('GNUPG_SIG_MODE_CLEAR', 2);
define ('GNUPG_VALIDITY_UNKNOWN', 0);
define ('GNUPG_VALIDITY_UNDEFINED', 1);
define ('GNUPG_VALIDITY_NEVER', 2);
define ('GNUPG_VALIDITY_MARGINAL', 3);
define ('GNUPG_VALIDITY_FULL', 4);
define ('GNUPG_VALIDITY_ULTIMATE', 5);
define ('GNUPG_PROTOCOL_OpenPGP', 0);
define ('GNUPG_PROTOCOL_CMS', 1);
define ('GNUPG_SIGSUM_VALID', 1);
define ('GNUPG_SIGSUM_GREEN', 2);
define ('GNUPG_SIGSUM_RED', 4);
define ('GNUPG_SIGSUM_KEY_REVOKED', 16);
define ('GNUPG_SIGSUM_KEY_EXPIRED', 32);
define ('GNUPG_SIGSUM_SIG_EXPIRED', 64);
define ('GNUPG_SIGSUM_KEY_MISSING', 128);
define ('GNUPG_SIGSUM_CRL_MISSING', 256);
define ('GNUPG_SIGSUM_CRL_TOO_OLD', 512);
define ('GNUPG_SIGSUM_BAD_POLICY', 1024);
define ('GNUPG_SIGSUM_SYS_ERROR', 2048);
define ('GNUPG_ERROR_WARNING', 1);
define ('GNUPG_ERROR_EXCEPTION', 2);
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
define ('GNUPG_GPGME_VERSION', "1.23.2");

// End of gnupg v.1.5.1
