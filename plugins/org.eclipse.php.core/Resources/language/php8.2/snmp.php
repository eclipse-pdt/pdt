<?php

// Start of snmp v.8.2.6

/**
 * Represents SNMP session.
 * @link http://www.php.net/manual/en/class.snmp.php
 */
class SNMP  {
	const VERSION_1 = 0;
	const VERSION_2c = 1;
	const VERSION_2C = 1;
	const VERSION_3 = 3;
	/**
	 * No SNMP-specific error occurred.
	const ERRNO_NOERROR = 0;
	/**
	 * All SNMP::ERRNO_&#42; codes bitwise OR'ed.
	const ERRNO_ANY = 126;
	/**
	 * A generic SNMP error occurred.
	const ERRNO_GENERIC = 2;
	/**
	 * Request to SNMP agent timed out.
	const ERRNO_TIMEOUT = 4;
	/**
	 * SNMP agent returned an error in reply.
	const ERRNO_ERROR_IN_REPLY = 8;
	/**
	 * SNMP agent faced OID cycling reporning
	 * non-increasing OID while executing (BULK)WALK command.
	 * This indicates bogus remote SNMP agent.
	const ERRNO_OID_NOT_INCREASING = 16;
	/**
	 * Library failed while parsing OID (and/or type for SET command).
	 * No queries has been made.
	const ERRNO_OID_PARSING_ERROR = 32;
	/**
	 * Library will use multiple queries for SET operation requested.
	 * That means that operation will be performed in a non-transaction manner 
	 * and second or subsequent chunks may fail if a type or value failure
	 * will be faced.
	const ERRNO_MULTIPLE_SET_QUERIES = 64;


	/**
	 * Read-only property with remote agent configuration: hostname,
	 * port, default timeout, default retries count
	 * @var array
	 * @link http://www.php.net/manual/en/class.snmp.php#snmp.props.info
	 */
	public readonly array $info;

	/**
	 * Maximum OID per GET/SET/GETBULK request
	 * @var int|null
	 * @link http://www.php.net/manual/en/class.snmp.php#snmp.props.max_oids
	 */
	public ?int $max_oids;

	/**
	 * Controls the method how the SNMP values will be returned
	 * @var int
	 * @link http://www.php.net/manual/en/class.snmp.php#snmp.props.valueretrieval
	 */
	public int $valueretrieval;

	/**
	 * Value of quick_print within the NET-SNMP library
	 * <p>Sets the value of quick_print within the NET-SNMP library. When this
	 * is set (1), the SNMP library will return 'quick printed' values. This
	 * means that just the value will be printed. When quick_print is not
	 * enabled (default) the NET-SNMP library prints extra information
	 * including the type of the value (i.e. IpAddress or OID). Additionally,
	 * if quick_print is not enabled, the library prints additional hex values
	 * for all strings of three characters or less.</p>
	 * @var bool
	 * @link http://www.php.net/manual/en/class.snmp.php#snmp.props.quick_print
	 */
	public bool $quick_print;

	/**
	 * Controls the way enum values are printed
	 * <p>Parameter toggles if walk/get etc. should automatically lookup enum values
	 * in the MIB and return them together with their human readable string.</p>
	 * @var bool
	 * @link http://www.php.net/manual/en/class.snmp.php#snmp.props.enum_print
	 */
	public bool $enum_print;

	/**
	 * Controls OID output format
	 * @var int
	 * @link http://www.php.net/manual/en/class.snmp.php#snmp.props.oid_output_format
	 */
	public int $oid_output_format;

	/**
	 * Controls disabling check for increasing OID while walking OID tree
	 * <p>Some SNMP agents are known for returning OIDs out
	 * of order but can complete the walk anyway. Other agents return OIDs
	 * that are out of order and can cause SNMP::walk
	 * to loop indefinitely until memory limit will be reached.
	 * PHP SNMP library by default performs OID increasing check and stops
	 * walking on OID tree when it detects possible loop with issuing warning
	 * about non-increasing OID faced.
	 * Set oid_increasing_check to false to disable this
	 * check.</p>
	 * @var bool
	 * @link http://www.php.net/manual/en/class.snmp.php#snmp.props.oid_increasing_check
	 */
	public bool $oid_increasing_check;

	/**
	 * Controls which failures will raise SNMPException instead of
	 * warning. Use bitwise OR'ed SNMP::ERRNO_&#42; constants.
	 * By default all SNMP exceptions are disabled.
	 * @var int
	 * @link http://www.php.net/manual/en/class.snmp.php#snmp.props.exceptions_enabled
	 */
	public int $exceptions_enabled;

	/**
	 * Creates SNMP instance representing session to remote SNMP agent
	 * @link http://www.php.net/manual/en/snmp.construct.php
	 * @param int $version SNMP protocol version:
	 * SNMP::VERSION_1, 
	 * SNMP::VERSION_2C, 
	 * SNMP::VERSION_3.
	 * @param string $hostname The SNMP agent. hostname may be suffixed with
	 * optional SNMP agent port after colon. IPv6 addresses must be enclosed in square
	 * brackets if used with port. If FQDN is used for hostname
	 * it will be resolved by php-snmp library, not by Net-SNMP engine. Usage
	 * of IPv6 addresses when specifying FQDN may be forced by enclosing FQDN
	 * into square brackets. Here it is some examples:
	 * <table>
	 * <table>
	 * <tr valign="top"><td>IPv4 with default port</td><td>127.0.0.1</td></tr>
	 * <tr valign="top"><td>IPv6 with default port</td><td>::1 or [::1]</td></tr>
	 * <tr valign="top"><td>IPv4 with specific port</td><td>127.0.0.1:1161</td></tr>
	 * <tr valign="top"><td>IPv6 with specific port</td><td>[::1]:1161</td></tr>
	 * <tr valign="top"><td>FQDN with default port</td><td>host.domain</td></tr>
	 * <tr valign="top"><td>FQDN with specific port</td><td>host.domain:1161</td></tr>
	 * <tr valign="top"><td>FQDN with default port, force usage of IPv6 address</td><td>[host.domain]</td></tr>
	 * <tr valign="top"><td>FQDN with specific port, force usage of IPv6 address</td><td>[host.domain]:1161</td></tr>
	 * </table>
	 * </table>
	 * @param string $community The purpuse of community is
	 * SNMP version specific:
	 * @param int $timeout [optional] The number of microseconds until the first timeout.
	 * @param int $retries [optional] The number of retries in case timeout occurs.
	 * @return int 
	 */
	public function __construct (int $version, string $hostname, string $community, int $timeout = -1, int $retries = -1): int {}

	/**
	 * Close SNMP session
	 * @link http://www.php.net/manual/en/snmp.close.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function close (): bool {}

	/**
	 * Configures security-related SNMPv3 session parameters
	 * @link http://www.php.net/manual/en/snmp.setsecurity.php
	 * @param string $securityLevel the security level (noAuthNoPriv|authNoPriv|authPriv)
	 * @param string $authProtocol [optional] the authentication protocol (MD5 or SHA)
	 * @param string $authPassphrase [optional] the authentication pass phrase
	 * @param string $privacyProtocol [optional] the privacy protocol (DES or AES)
	 * @param string $privacyPassphrase [optional] the privacy pass phrase
	 * @param string $contextName [optional] the context name
	 * @param string $contextEngineId [optional] the context EngineID
	 * @return bool Returns true on success or false on failure.
	 */
	public function setSecurity (string $securityLevel, string $authProtocol = '""', string $authPassphrase = '""', string $privacyProtocol = '""', string $privacyPassphrase = '""', string $contextName = '""', string $contextEngineId = '""'): bool {}

	/**
	 * Fetch an SNMP object
	 * @link http://www.php.net/manual/en/snmp.get.php
	 * @param array|string $objectId 
	 * @param bool $preserveKeys [optional] 
	 * @return mixed Returns SNMP objects requested as string or array
	 * depending on objectId type or false on error.
	 */
	public function get (array|string $objectId, bool $preserveKeys = false): mixed {}

	/**
	 * Fetch an SNMP object which
	 * follows the given object id
	 * @link http://www.php.net/manual/en/snmp.getnext.php
	 * @param array|string $objectId 
	 * @return mixed Returns SNMP objects requested as string or array
	 * depending on objectId type or false on error.
	 */
	public function getnext (array|string $objectId): mixed {}

	/**
	 * Fetch SNMP object subtree
	 * @link http://www.php.net/manual/en/snmp.walk.php
	 * @param array|string $objectId Root of subtree to be fetched
	 * @param bool $suffixAsKey [optional] By default full OID notation is used for keys in output array.
	 * If set to true subtree prefix will be removed from keys leaving only suffix of object_id.
	 * @param int $maxRepetitions [optional] This specifies the maximum number of iterations over the repeating variables.
	 * The default is to use this value from SNMP object.
	 * @param int $nonRepeaters [optional] This specifies the number of supplied variables that should not be iterated over.
	 * The default is to use this value from SNMP object.
	 * @return array|false Returns an associative array of the SNMP object ids and their values on success or false on error.
	 * When a SNMP error occures SNMP::getErrno and
	 * SNMP::getError can be used for retrieving error
	 * number (specific to SNMP extension, see class constants) and error message
	 * respectively.
	 */
	public function walk (array|string $objectId, bool $suffixAsKey = false, int $maxRepetitions = -1, int $nonRepeaters = -1): array|false {}

	/**
	 * Set the value of an SNMP object
	 * @link http://www.php.net/manual/en/snmp.set.php
	 * @param array|string $objectId The SNMP object id
	 * <p>When count of OIDs in object_id array is greater than
	 * max_oids object property set method will have to use multiple queries
	 * to perform requested value updates. In this case type and value checks
	 * are made per-chunk so second or subsequent requests may fail due to
	 * wrong type or value for OID requested. To mark this a warning is
	 * raised when count of OIDs in object_id array is greater than max_oids.</p>
	 * @param array|string $type >
	 * The MIB defines the type of each object id. It has to be specified as a single character from the below list.
	 * <p>>
	 * If OPAQUE_SPECIAL_TYPES was defined while compiling the SNMP library, the following are also valid:</p>
	 * <p>>
	 * Most of these will use the obvious corresponding ASN.1 type. 's', 'x', 'd' and 'b' are all different ways of specifying an OCTET STRING value, and
	 * the 'u' unsigned type is also used for handling Gauge32 values.</p>
	 * <p>>
	 * If the MIB-Files are loaded by into the MIB Tree with "snmp_read_mib" or by specifying it in the libsnmp config, '=' may be used as
	 * the type parameter for all object ids as the type can then be automatically read from the MIB.</p>
	 * <p>>
	 * Note that there are two ways to set a variable of the type BITS like e.g.
	 * "SYNTAX BITS {telnet(0), ftp(1), http(2), icmp(3), snmp(4), ssh(5), https(6)}":</p>
	 * <p>>
	 * See examples section for more details.</p>
	 * @param array|string $value The new value.
	 * @return bool Returns true on success or false on failure.
	 */
	public function set (array|string $objectId, array|string $type, array|string $value): bool {}

	/**
	 * Get last error code
	 * @link http://www.php.net/manual/en/snmp.geterrno.php
	 * @return int Returns one of SNMP error code values described in constants chapter.
	 */
	public function getErrno (): int {}

	/**
	 * Get last error message
	 * @link http://www.php.net/manual/en/snmp.geterror.php
	 * @return string String describing error from last SNMP request.
	 */
	public function getError (): string {}

}

/**
 * Represents an error raised by SNMP. You should not throw a 
 * SNMPException from your own code.
 * See Exceptions for more
 * information about Exceptions in PHP.
 * @link http://www.php.net/manual/en/class.snmpexception.php
 */
class SNMPException extends RuntimeException implements Stringable, Throwable {

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
 * Fetch an SNMP object
 * @link http://www.php.net/manual/en/function.snmpget.php
 * @param string $hostname 
 * @param string $community 
 * @param array|string $object_id 
 * @param int $timeout [optional] 
 * @param int $retries [optional] 
 * @return mixed Returns SNMP object value on success or false on error.
 */
function snmpget (string $hostname, string $community, array|string $object_id, int $timeout = -1, int $retries = -1): mixed {}

/**
 * Fetch the SNMP object which follows the given object id
 * @link http://www.php.net/manual/en/function.snmpgetnext.php
 * @param string $hostname 
 * @param string $community 
 * @param array|string $object_id 
 * @param int $timeout [optional] 
 * @param int $retries [optional] 
 * @return mixed Returns SNMP object value on success or false on error.
 * In case of an error, an E_WARNING message is shown.
 */
function snmpgetnext (string $hostname, string $community, array|string $object_id, int $timeout = -1, int $retries = -1): mixed {}

/**
 * Fetch all the SNMP objects from an agent
 * @link http://www.php.net/manual/en/function.snmpwalk.php
 * @param string $hostname 
 * @param string $community 
 * @param array|string $object_id 
 * @param int $timeout [optional] 
 * @param int $retries [optional] 
 * @return array|false Returns an array of SNMP object values starting from the
 * object_id as root or false on error.
 */
function snmpwalk (string $hostname, string $community, array|string $object_id, int $timeout = -1, int $retries = -1): array|false {}

/**
 * Return all objects including their respective object ID within the specified one
 * @link http://www.php.net/manual/en/function.snmprealwalk.php
 * @param string $hostname 
 * @param string $community 
 * @param array|string $object_id 
 * @param int $timeout [optional] 
 * @param int $retries [optional] 
 * @return array|false Returns an associative array of the SNMP object ids and their values on success or false on error.
 * In case of an error, an E_WARNING message is shown.
 */
function snmprealwalk (string $hostname, string $community, array|string $object_id, int $timeout = -1, int $retries = -1): array|false {}

/**
 * Query for a tree of information about a network entity
 * @link http://www.php.net/manual/en/function.snmpwalkoid.php
 * @param string $hostname 
 * @param string $community 
 * @param array|string $object_id 
 * @param int $timeout [optional] 
 * @param int $retries [optional] 
 * @return array|false Returns an associative array with object ids and their respective
 * object value starting from the object_id
 * as root or false on error.
 */
function snmpwalkoid (string $hostname, string $community, array|string $object_id, int $timeout = -1, int $retries = -1): array|false {}

/**
 * Set the value of an SNMP object
 * @link http://www.php.net/manual/en/function.snmpset.php
 * @param string $hostname 
 * @param string $community 
 * @param array|string $object_id 
 * @param array|string $type 
 * @param array|string $value 
 * @param int $timeout [optional] 
 * @param int $retries [optional] 
 * @return bool Returns true on success or false on failure.
 * <p>If the SNMP host rejects the data type, an E_WARNING message like "Warning: Error in packet. Reason: (badValue) The value given has the wrong type or length." is shown.
 * If an unknown or invalid OID is specified the warning probably reads "Could not add variable".</p>
 */
function snmpset (string $hostname, string $community, array|string $object_id, array|string $type, array|string $value, int $timeout = -1, int $retries = -1): bool {}

/**
 * Fetches the current value of the NET-SNMP library's quick_print setting
 * @link http://www.php.net/manual/en/function.snmp-get-quick-print.php
 * @return bool Returns true if quick_print is on, false otherwise.
 */
function snmp_get_quick_print (): bool {}

/**
 * Set the value of enable within the NET-SNMP library
 * @link http://www.php.net/manual/en/function.snmp-set-quick-print.php
 * @param bool $enable 
 * @return true Always returns true.
 */
function snmp_set_quick_print (bool $enable): true {}

/**
 * Return all values that are enums with their enum value instead of the raw integer
 * @link http://www.php.net/manual/en/function.snmp-set-enum-print.php
 * @param bool $enable As the value is interpreted as boolean by the Net-SNMP library, it can only be "0" or "1".
 * @return true Always returns true.
 */
function snmp_set_enum_print (bool $enable): true {}

/**
 * Set the OID output format
 * @link http://www.php.net/manual/en/function.snmp-set-oid-output-format.php
 * @param int $format 
 * @return true Always returns true.
 */
function snmp_set_oid_output_format (int $format): true {}

/**
 * {@inheritdoc}
 * @param int $format
 */
function snmp_set_oid_numeric_print (int $format): bool {}

/**
 * Fetch an SNMP object
 * @link http://www.php.net/manual/en/function.snmp2-get.php
 * @param string $hostname The SNMP agent.
 * @param string $community The read community.
 * @param array|string $object_id The SNMP object.
 * @param int $timeout [optional] The number of microseconds until the first timeout.
 * @param int $retries [optional] The number of times to retry if timeouts occur.
 * @return mixed Returns SNMP object value on success or false on error.
 */
function snmp2_get (string $hostname, string $community, array|string $object_id, int $timeout = -1, int $retries = -1): mixed {}

/**
 * Fetch the SNMP object which follows the given object id
 * @link http://www.php.net/manual/en/function.snmp2-getnext.php
 * @param string $hostname The hostname of the SNMP agent (server).
 * @param string $community The read community.
 * @param array|string $object_id The SNMP object id which precedes the wanted one.
 * @param int $timeout [optional] The number of microseconds until the first timeout.
 * @param int $retries [optional] The number of times to retry if timeouts occur.
 * @return mixed Returns SNMP object value on success or false on error.
 * In case of an error, an E_WARNING message is shown.
 */
function snmp2_getnext (string $hostname, string $community, array|string $object_id, int $timeout = -1, int $retries = -1): mixed {}

/**
 * Fetch all the SNMP objects from an agent
 * @link http://www.php.net/manual/en/function.snmp2-walk.php
 * @param string $hostname The SNMP agent (server).
 * @param string $community The read community.
 * @param array|string $object_id If null, object_id is taken as the root of
 * the SNMP objects tree and all objects under that tree are returned as
 * an array.
 * <p>If object_id is specified, all the SNMP objects
 * below that object_id are returned.</p>
 * @param int $timeout [optional] The number of microseconds until the first timeout.
 * @param int $retries [optional] The number of times to retry if timeouts occur.
 * @return array|false Returns an array of SNMP object values starting from the
 * object_id as root or false on error.
 */
function snmp2_walk (string $hostname, string $community, array|string $object_id, int $timeout = -1, int $retries = -1): array|false {}

/**
 * Return all objects including their respective object ID within the specified one
 * @link http://www.php.net/manual/en/function.snmp2-real-walk.php
 * @param string $hostname The hostname of the SNMP agent (server).
 * @param string $community The read community.
 * @param array|string $object_id The SNMP object id which precedes the wanted one.
 * @param int $timeout [optional] The number of microseconds until the first timeout.
 * @param int $retries [optional] The number of times to retry if timeouts occur.
 * @return array|false Returns an associative array of the SNMP object ids and their values on success or false on error.
 * In case of an error, an E_WARNING message is shown.
 */
function snmp2_real_walk (string $hostname, string $community, array|string $object_id, int $timeout = -1, int $retries = -1): array|false {}

/**
 * Set the value of an SNMP object
 * @link http://www.php.net/manual/en/function.snmp2-set.php
 * @param string $hostname The hostname of the SNMP agent (server).
 * @param string $community The write community.
 * @param array|string $object_id The SNMP object id.
 * @param array|string $type >
 * The MIB defines the type of each object id. It has to be specified as a single character from the below list.
 * <p>>
 * If OPAQUE_SPECIAL_TYPES was defined while compiling the SNMP library, the following are also valid:</p>
 * <p>>
 * Most of these will use the obvious corresponding ASN.1 type. 's', 'x', 'd' and 'b' are all different ways of specifying an OCTET STRING value, and
 * the 'u' unsigned type is also used for handling Gauge32 values.</p>
 * <p>>
 * If the MIB-Files are loaded by into the MIB Tree with "snmp_read_mib" or by specifying it in the libsnmp config, '=' may be used as
 * the type parameter for all object ids as the type can then be automatically read from the MIB.</p>
 * <p>>
 * Note that there are two ways to set a variable of the type BITS like e.g.
 * "SYNTAX BITS {telnet(0), ftp(1), http(2), icmp(3), snmp(4), ssh(5), https(6)}":</p>
 * <p>>
 * See examples section for more details.</p>
 * @param array|string $value The new value.
 * @param int $timeout [optional] The number of microseconds until the first timeout.
 * @param int $retries [optional] The number of times to retry if timeouts occur.
 * @return bool Returns true on success or false on failure.
 * <p>If the SNMP host rejects the data type, an E_WARNING message like "Warning: Error in packet. Reason: (badValue) The value given has the wrong type or length." is shown.
 * If an unknown or invalid OID is specified the warning probably reads "Could not add variable".</p>
 */
function snmp2_set (string $hostname, string $community, array|string $object_id, array|string $type, array|string $value, int $timeout = -1, int $retries = -1): bool {}

/**
 * Fetch an SNMP object
 * @link http://www.php.net/manual/en/function.snmp3-get.php
 * @param string $hostname The hostname of the SNMP agent (server).
 * @param string $security_name the security name, usually some kind of username
 * @param string $security_level the security level (noAuthNoPriv|authNoPriv|authPriv)
 * @param string $auth_protocol the authentication protocol ("MD5", "SHA",
 * "SHA256", or "SHA512")
 * @param string $auth_passphrase the authentication pass phrase
 * @param string $privacy_protocol the privacy protocol (DES or AES)
 * @param string $privacy_passphrase the privacy pass phrase
 * @param array|string $object_id The SNMP object id.
 * @param int $timeout [optional] The number of microseconds until the first timeout.
 * @param int $retries [optional] The number of times to retry if timeouts occur.
 * @return mixed Returns SNMP object value on success or false on error.
 */
function snmp3_get (string $hostname, string $security_name, string $security_level, string $auth_protocol, string $auth_passphrase, string $privacy_protocol, string $privacy_passphrase, array|string $object_id, int $timeout = -1, int $retries = -1): mixed {}

/**
 * Fetch the SNMP object which follows the given object id
 * @link http://www.php.net/manual/en/function.snmp3-getnext.php
 * @param string $hostname The hostname of the
 * SNMP agent (server).
 * @param string $security_name the security name, usually some kind of username
 * @param string $security_level the security level (noAuthNoPriv|authNoPriv|authPriv)
 * @param string $auth_protocol the authentication protocol ("MD5", "SHA",
 * "SHA256", or "SHA512")
 * @param string $auth_passphrase the authentication pass phrase
 * @param string $privacy_protocol the privacy protocol (DES or AES)
 * @param string $privacy_passphrase the privacy pass phrase
 * @param array|string $object_id The SNMP object id.
 * @param int $timeout [optional] The number of microseconds until the first timeout.
 * @param int $retries [optional] The number of times to retry if timeouts occur.
 * @return mixed Returns SNMP object value on success or false on error.
 * In case of an error, an E_WARNING message is shown.
 */
function snmp3_getnext (string $hostname, string $security_name, string $security_level, string $auth_protocol, string $auth_passphrase, string $privacy_protocol, string $privacy_passphrase, array|string $object_id, int $timeout = -1, int $retries = -1): mixed {}

/**
 * Fetch all the SNMP objects from an agent
 * @link http://www.php.net/manual/en/function.snmp3-walk.php
 * @param string $hostname The hostname of the SNMP agent (server).
 * @param string $security_name the security name, usually some kind of username
 * @param string $security_level the security level (noAuthNoPriv|authNoPriv|authPriv)
 * @param string $auth_protocol the authentication protocol ("MD5", "SHA",
 * "SHA256", or "SHA512")
 * @param string $auth_passphrase the authentication pass phrase
 * @param string $privacy_protocol the privacy protocol (DES or AES)
 * @param string $privacy_passphrase the privacy pass phrase
 * @param array|string $object_id If null, object_id is taken as the root of
 * the SNMP objects tree and all objects under that tree are returned as
 * an array.
 * <p>If object_id is specified, all the SNMP objects
 * below that object_id are returned.</p>
 * @param int $timeout [optional] The number of microseconds until the first timeout.
 * @param int $retries [optional] The number of times to retry if timeouts occur.
 * @return array|false Returns an array of SNMP object values starting from the
 * object_id as root or false on error.
 */
function snmp3_walk (string $hostname, string $security_name, string $security_level, string $auth_protocol, string $auth_passphrase, string $privacy_protocol, string $privacy_passphrase, array|string $object_id, int $timeout = -1, int $retries = -1): array|false {}

/**
 * Return all objects including their respective object ID within the specified one
 * @link http://www.php.net/manual/en/function.snmp3-real-walk.php
 * @param string $hostname The hostname of the
 * SNMP agent (server).
 * @param string $security_name the security name, usually some kind of username
 * @param string $security_level the security level (noAuthNoPriv|authNoPriv|authPriv)
 * @param string $auth_protocol the authentication protocol (MD5 or SHA)
 * @param string $auth_passphrase the authentication pass phrase
 * @param string $privacy_protocol the authentication protocol ("MD5", "SHA",
 * "SHA256", or "SHA512")
 * @param string $privacy_passphrase the privacy pass phrase
 * @param array|string $object_id The SNMP object id.
 * @param int $timeout [optional] The number of microseconds until the first timeout.
 * @param int $retries [optional] The number of times to retry if timeouts occur.
 * @return array|false Returns an associative array of the
 * SNMP object ids and their values on success or false on error.
 * In case of an error, an E_WARNING message is shown.
 */
function snmp3_real_walk (string $hostname, string $security_name, string $security_level, string $auth_protocol, string $auth_passphrase, string $privacy_protocol, string $privacy_passphrase, array|string $object_id, int $timeout = -1, int $retries = -1): array|false {}

/**
 * Set the value of an SNMP object
 * @link http://www.php.net/manual/en/function.snmp3-set.php
 * @param string $hostname The hostname of the SNMP agent (server).
 * @param string $security_name the security name, usually some kind of username
 * @param string $security_level the security level (noAuthNoPriv|authNoPriv|authPriv)
 * @param string $auth_protocol the authentication protocol (MD5 or SHA)
 * @param string $auth_passphrase the authentication pass phrase
 * @param string $privacy_protocol the privacy protocol (DES or AES)
 * @param string $privacy_passphrase the privacy pass phrase
 * @param array|string $object_id The SNMP object id.
 * @param array|string $type >
 * The MIB defines the type of each object id. It has to be specified as a single character from the below list.
 * <p>>
 * If OPAQUE_SPECIAL_TYPES was defined while compiling the SNMP library, the following are also valid:</p>
 * <p>>
 * Most of these will use the obvious corresponding ASN.1 type. 's', 'x', 'd' and 'b' are all different ways of specifying an OCTET STRING value, and
 * the 'u' unsigned type is also used for handling Gauge32 values.</p>
 * <p>>
 * If the MIB-Files are loaded by into the MIB Tree with "snmp_read_mib" or by specifying it in the libsnmp config, '=' may be used as
 * the type parameter for all object ids as the type can then be automatically read from the MIB.</p>
 * <p>>
 * Note that there are two ways to set a variable of the type BITS like e.g.
 * "SYNTAX BITS {telnet(0), ftp(1), http(2), icmp(3), snmp(4), ssh(5), https(6)}":</p>
 * <p>>
 * See examples section for more details.</p>
 * @param array|string $value The new value
 * @param int $timeout [optional] The number of microseconds until the first timeout.
 * @param int $retries [optional] The number of times to retry if timeouts occur.
 * @return bool Returns true on success or false on failure.
 * <p>If the SNMP host rejects the data type, an E_WARNING message like "Warning: Error in packet. Reason: (badValue) The value given has the wrong type or length." is shown.
 * If an unknown or invalid OID is specified the warning probably reads "Could not add variable".</p>
 */
function snmp3_set (string $hostname, string $security_name, string $security_level, string $auth_protocol, string $auth_passphrase, string $privacy_protocol, string $privacy_passphrase, array|string $object_id, array|string $type, array|string $value, int $timeout = -1, int $retries = -1): bool {}

/**
 * Specify the method how the SNMP values will be returned
 * @link http://www.php.net/manual/en/function.snmp-set-valueretrieval.php
 * @param int $method 
 * @return true Always returns true.
 */
function snmp_set_valueretrieval (int $method): true {}

/**
 * Return the method how the SNMP values will be returned
 * @link http://www.php.net/manual/en/function.snmp-get-valueretrieval.php
 * @return int OR-ed combitantion of constants ( SNMP_VALUE_LIBRARY or
 * SNMP_VALUE_PLAIN ) with
 * possible SNMP_VALUE_OBJECT set.
 */
function snmp_get_valueretrieval (): int {}

/**
 * Reads and parses a MIB file into the active MIB tree
 * @link http://www.php.net/manual/en/function.snmp-read-mib.php
 * @param string $filename 
 * @return bool Returns true on success or false on failure.
 */
function snmp_read_mib (string $filename): bool {}


/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_OID_OUTPUT_SUFFIX', 1);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_OID_OUTPUT_MODULE', 2);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_OID_OUTPUT_FULL', 3);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_OID_OUTPUT_NUMERIC', 4);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_OID_OUTPUT_UCD', 5);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_OID_OUTPUT_NONE', 6);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_VALUE_LIBRARY', 0);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_VALUE_PLAIN', 1);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_VALUE_OBJECT', 2);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_BIT_STR', 3);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_OCTET_STR', 4);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_OPAQUE', 68);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_NULL', 5);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_OBJECT_ID', 6);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_IPADDRESS', 64);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_COUNTER', 66);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_UNSIGNED', 66);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_TIMETICKS', 67);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_UINTEGER', 71);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_INTEGER', 2);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 * @var int
 */
define ('SNMP_COUNTER64', 70);

// End of snmp v.8.2.6
