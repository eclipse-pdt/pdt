<?php

// Start of snmp v.8.3.0

class SNMP  {
	const VERSION_1 = 0;
	const VERSION_2c = 1;
	const VERSION_2C = 1;
	const VERSION_3 = 3;
	const ERRNO_NOERROR = 0;
	const ERRNO_ANY = 126;
	const ERRNO_GENERIC = 2;
	const ERRNO_TIMEOUT = 4;
	const ERRNO_ERROR_IN_REPLY = 8;
	const ERRNO_OID_NOT_INCREASING = 16;
	const ERRNO_OID_PARSING_ERROR = 32;
	const ERRNO_MULTIPLE_SET_QUERIES = 64;


	public array $info;

	public ?int $max_oids;

	public int $valueretrieval;

	public bool $quick_print;

	public bool $enum_print;

	public int $oid_output_format;

	public bool $oid_increasing_check;

	public int $exceptions_enabled;

	/**
	 * {@inheritdoc}
	 * @param int $version
	 * @param string $hostname
	 * @param string $community
	 * @param int $timeout [optional]
	 * @param int $retries [optional]
	 */
	public function __construct (int $version, string $hostname, string $community, int $timeout = -1, int $retries = -1) {}

	/**
	 * {@inheritdoc}
	 */
	public function close () {}

	/**
	 * {@inheritdoc}
	 * @param string $securityLevel
	 * @param string $authProtocol [optional]
	 * @param string $authPassphrase [optional]
	 * @param string $privacyProtocol [optional]
	 * @param string $privacyPassphrase [optional]
	 * @param string $contextName [optional]
	 * @param string $contextEngineId [optional]
	 */
	public function setSecurity (string $securityLevel, string $authProtocol = '', string $authPassphrase = '', string $privacyProtocol = '', string $privacyPassphrase = '', string $contextName = '', string $contextEngineId = '') {}

	/**
	 * {@inheritdoc}
	 * @param array|string $objectId
	 * @param bool $preserveKeys [optional]
	 */
	public function get (array|string $objectId, bool $preserveKeys = false) {}

	/**
	 * {@inheritdoc}
	 * @param array|string $objectId
	 */
	public function getnext (array|string $objectId) {}

	/**
	 * {@inheritdoc}
	 * @param array|string $objectId
	 * @param bool $suffixAsKey [optional]
	 * @param int $maxRepetitions [optional]
	 * @param int $nonRepeaters [optional]
	 */
	public function walk (array|string $objectId, bool $suffixAsKey = false, int $maxRepetitions = -1, int $nonRepeaters = -1) {}

	/**
	 * {@inheritdoc}
	 * @param array|string $objectId
	 * @param array|string $type
	 * @param array|string $value
	 */
	public function set (array|string $objectId, array|string $type, array|string $value) {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrno () {}

	/**
	 * {@inheritdoc}
	 */
	public function getError () {}

}

class SNMPException extends RuntimeException implements Stringable, Throwable {

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
 * @param string $hostname
 * @param string $community
 * @param array|string $object_id
 * @param int $timeout [optional]
 * @param int $retries [optional]
 */
function snmpget (string $hostname, string $community, array|string $object_id, int $timeout = -1, int $retries = -1): mixed {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param string $community
 * @param array|string $object_id
 * @param int $timeout [optional]
 * @param int $retries [optional]
 */
function snmpgetnext (string $hostname, string $community, array|string $object_id, int $timeout = -1, int $retries = -1): mixed {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param string $community
 * @param array|string $object_id
 * @param int $timeout [optional]
 * @param int $retries [optional]
 */
function snmpwalk (string $hostname, string $community, array|string $object_id, int $timeout = -1, int $retries = -1): array|false {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param string $community
 * @param array|string $object_id
 * @param int $timeout [optional]
 * @param int $retries [optional]
 */
function snmprealwalk (string $hostname, string $community, array|string $object_id, int $timeout = -1, int $retries = -1): array|false {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param string $community
 * @param array|string $object_id
 * @param int $timeout [optional]
 * @param int $retries [optional]
 */
function snmpwalkoid (string $hostname, string $community, array|string $object_id, int $timeout = -1, int $retries = -1): array|false {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param string $community
 * @param array|string $object_id
 * @param array|string $type
 * @param array|string $value
 * @param int $timeout [optional]
 * @param int $retries [optional]
 */
function snmpset (string $hostname, string $community, array|string $object_id, array|string $type, array|string $value, int $timeout = -1, int $retries = -1): bool {}

/**
 * {@inheritdoc}
 */
function snmp_get_quick_print (): bool {}

/**
 * {@inheritdoc}
 * @param bool $enable
 */
function snmp_set_quick_print (bool $enable): true {}

/**
 * {@inheritdoc}
 * @param bool $enable
 */
function snmp_set_enum_print (bool $enable): true {}

/**
 * {@inheritdoc}
 * @param int $format
 */
function snmp_set_oid_output_format (int $format): true {}

/**
 * {@inheritdoc}
 * @param int $format
 */
function snmp_set_oid_numeric_print (int $format): true {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param string $community
 * @param array|string $object_id
 * @param int $timeout [optional]
 * @param int $retries [optional]
 */
function snmp2_get (string $hostname, string $community, array|string $object_id, int $timeout = -1, int $retries = -1): mixed {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param string $community
 * @param array|string $object_id
 * @param int $timeout [optional]
 * @param int $retries [optional]
 */
function snmp2_getnext (string $hostname, string $community, array|string $object_id, int $timeout = -1, int $retries = -1): mixed {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param string $community
 * @param array|string $object_id
 * @param int $timeout [optional]
 * @param int $retries [optional]
 */
function snmp2_walk (string $hostname, string $community, array|string $object_id, int $timeout = -1, int $retries = -1): array|false {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param string $community
 * @param array|string $object_id
 * @param int $timeout [optional]
 * @param int $retries [optional]
 */
function snmp2_real_walk (string $hostname, string $community, array|string $object_id, int $timeout = -1, int $retries = -1): array|false {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param string $community
 * @param array|string $object_id
 * @param array|string $type
 * @param array|string $value
 * @param int $timeout [optional]
 * @param int $retries [optional]
 */
function snmp2_set (string $hostname, string $community, array|string $object_id, array|string $type, array|string $value, int $timeout = -1, int $retries = -1): bool {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param string $security_name
 * @param string $security_level
 * @param string $auth_protocol
 * @param string $auth_passphrase
 * @param string $privacy_protocol
 * @param string $privacy_passphrase
 * @param array|string $object_id
 * @param int $timeout [optional]
 * @param int $retries [optional]
 */
function snmp3_get (string $hostname, string $security_name, string $security_level, string $auth_protocol, string $auth_passphrase, string $privacy_protocol, string $privacy_passphrase, array|string $object_id, int $timeout = -1, int $retries = -1): mixed {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param string $security_name
 * @param string $security_level
 * @param string $auth_protocol
 * @param string $auth_passphrase
 * @param string $privacy_protocol
 * @param string $privacy_passphrase
 * @param array|string $object_id
 * @param int $timeout [optional]
 * @param int $retries [optional]
 */
function snmp3_getnext (string $hostname, string $security_name, string $security_level, string $auth_protocol, string $auth_passphrase, string $privacy_protocol, string $privacy_passphrase, array|string $object_id, int $timeout = -1, int $retries = -1): mixed {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param string $security_name
 * @param string $security_level
 * @param string $auth_protocol
 * @param string $auth_passphrase
 * @param string $privacy_protocol
 * @param string $privacy_passphrase
 * @param array|string $object_id
 * @param int $timeout [optional]
 * @param int $retries [optional]
 */
function snmp3_walk (string $hostname, string $security_name, string $security_level, string $auth_protocol, string $auth_passphrase, string $privacy_protocol, string $privacy_passphrase, array|string $object_id, int $timeout = -1, int $retries = -1): array|false {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param string $security_name
 * @param string $security_level
 * @param string $auth_protocol
 * @param string $auth_passphrase
 * @param string $privacy_protocol
 * @param string $privacy_passphrase
 * @param array|string $object_id
 * @param int $timeout [optional]
 * @param int $retries [optional]
 */
function snmp3_real_walk (string $hostname, string $security_name, string $security_level, string $auth_protocol, string $auth_passphrase, string $privacy_protocol, string $privacy_passphrase, array|string $object_id, int $timeout = -1, int $retries = -1): array|false {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param string $security_name
 * @param string $security_level
 * @param string $auth_protocol
 * @param string $auth_passphrase
 * @param string $privacy_protocol
 * @param string $privacy_passphrase
 * @param array|string $object_id
 * @param array|string $type
 * @param array|string $value
 * @param int $timeout [optional]
 * @param int $retries [optional]
 */
function snmp3_set (string $hostname, string $security_name, string $security_level, string $auth_protocol, string $auth_passphrase, string $privacy_protocol, string $privacy_passphrase, array|string $object_id, array|string $type, array|string $value, int $timeout = -1, int $retries = -1): bool {}

/**
 * {@inheritdoc}
 * @param int $method
 */
function snmp_set_valueretrieval (int $method): true {}

/**
 * {@inheritdoc}
 */
function snmp_get_valueretrieval (): int {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function snmp_read_mib (string $filename): bool {}

define ('SNMP_OID_OUTPUT_SUFFIX', 1);
define ('SNMP_OID_OUTPUT_MODULE', 2);
define ('SNMP_OID_OUTPUT_FULL', 3);
define ('SNMP_OID_OUTPUT_NUMERIC', 4);
define ('SNMP_OID_OUTPUT_UCD', 5);
define ('SNMP_OID_OUTPUT_NONE', 6);
define ('SNMP_VALUE_LIBRARY', 0);
define ('SNMP_VALUE_PLAIN', 1);
define ('SNMP_VALUE_OBJECT', 2);
define ('SNMP_BIT_STR', 3);
define ('SNMP_OCTET_STR', 4);
define ('SNMP_OPAQUE', 68);
define ('SNMP_NULL', 5);
define ('SNMP_OBJECT_ID', 6);
define ('SNMP_IPADDRESS', 64);
define ('SNMP_COUNTER', 66);
define ('SNMP_UNSIGNED', 66);
define ('SNMP_TIMETICKS', 67);
define ('SNMP_UINTEGER', 71);
define ('SNMP_INTEGER', 2);
define ('SNMP_COUNTER64', 70);

// End of snmp v.8.3.0
