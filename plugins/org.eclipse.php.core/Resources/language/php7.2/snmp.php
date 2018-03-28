<?php

// Start of snmp v.0.1

/**
 * Represents SNMP session.
 * @link http://www.php.net/manual/en/class.snmp.php
 */
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


	/**
	 * Maximum OID per GET/SET/GETBULK request
	 * @var int
	 * @link http://www.php.net/manual/en/class.snmp.php#snmp.props.max_oids
	 */
	public $max_oids;

	/**
	 * Controls the method how the SNMP values will be returned
	 * @var int
	 * @link http://www.php.net/manual/en/class.snmp.php#snmp.props.valueretrieval
	 */
	public $valueretrieval;

	/**
	 * Value of quick_print within the NET-SNMP library
	 * @var bool
	 * @link http://www.php.net/manual/en/class.snmp.php#snmp.props.quick_print
	 */
	public $quick_print;

	/**
	 * Controls the way enum values are printed
	 * @var bool
	 * @link http://www.php.net/manual/en/class.snmp.php#snmp.props.enum_print
	 */
	public $enum_print;

	/**
	 * Controls OID output format
	 * @var int
	 * @link http://www.php.net/manual/en/class.snmp.php#snmp.props.oid_output_format
	 */
	public $oid_output_format;

	/**
	 * Controls disabling check for increasing OID while walking OID tree
	 * @var bool
	 * @link http://www.php.net/manual/en/class.snmp.php#snmp.props.oid_increasing_check
	 */
	public $oid_increasing_check;

	/**
	 * Controls which failures will raise SNMPException instead of
	 * warning. Use bitwise OR'ed SNMP::ERRNO_&#42; constants.
	 * By default all SNMP exceptions are disabled.
	 * @var int
	 * @link http://www.php.net/manual/en/class.snmp.php#snmp.props.exceptions_enabled
	 */
	public $exceptions_enabled;

	/**
	 * Read-only property with remote agent configuration: hostname,
	 * port, default timeout, default retries count
	 * @var array
	 * @link http://www.php.net/manual/en/class.snmp.php#snmp.props.info
	 */
	public $info;

	/**
	 * Creates SNMP instance representing session to remote SNMP agent
	 * @link http://www.php.net/manual/en/snmp.construct.php
	 * @param $version
	 * @param $host
	 * @param $community
	 * @param $timeout [optional]
	 * @param $retries [optional]
	 */
	public function __construct ($version, $host, $community, $timeout = null, $retries = null) {}

	/**
	 * Close SNMP session
	 * @link http://www.php.net/manual/en/snmp.close.php
	 * @return bool true on success or false on failure
	 */
	public function close () {}

	/**
	 * Configures security-related SNMPv3 session parameters
	 * @link http://www.php.net/manual/en/snmp.setsecurity.php
	 * @param string $sec_level the security level (noAuthNoPriv|authNoPriv|authPriv)
	 * @param string $auth_protocol [optional] the authentication protocol (MD5 or SHA)
	 * @param string $auth_passphrase [optional] the authentication pass phrase
	 * @param string $priv_protocol [optional] the privacy protocol (DES or AES)
	 * @param string $priv_passphrase [optional] the privacy pass phrase
	 * @param string $contextName [optional] the context name
	 * @param string $contextEngineID [optional] the context EngineID
	 * @return bool true on success or false on failure
	 */
	public function setSecurity (string $sec_level, string $auth_protocol = null, string $auth_passphrase = null, string $priv_protocol = null, string $priv_passphrase = null, string $contextName = null, string $contextEngineID = null) {}

	/**
	 * Fetch an SNMP object
	 * @link http://www.php.net/manual/en/snmp.get.php
	 * @param mixed $object_id The SNMP object (OID) or objects
	 * @param bool $preserve_keys [optional] When object_id is a array and
	 * preserve_keys set to true keys in results
	 * will be taken exactly as in object_id,
	 * otherwise SNMP::oid_output_format property is used to determinate
	 * the form of keys.
	 * @return mixed SNMP objects requested as string or array
	 * depending on object_id type or false on error.
	 */
	public function get ($object_id, bool $preserve_keys = null) {}

	/**
	 * Fetch an SNMP object which
	 * follows the given object id
	 * @link http://www.php.net/manual/en/snmp.getnext.php
	 * @param mixed $object_id The SNMP object (OID) or objects
	 * @return mixed SNMP objects requested as string or array
	 * depending on object_id type or false on error.
	 */
	public function getnext ($object_id) {}

	/**
	 * Fetch SNMP object subtree
	 * @link http://www.php.net/manual/en/snmp.walk.php
	 * @param string $object_id Root of subtree to be fetched
	 * @param bool $suffix_as_key [optional] By default full OID notation is used for keys in output array.
	 * If set to true subtree prefix will be removed from keys leaving only suffix of object_id.
	 * @param int $max_repetitions [optional] This specifies the maximum number of iterations over the repeating variables.
	 * The default is to use this value from SNMP object.
	 * @param int $non_repeaters [optional] This specifies the number of supplied variables that should not be iterated over.
	 * The default is to use this value from SNMP object.
	 * @return array an associative array of the SNMP object ids and their values on success or false on error.
	 * When a SNMP error occures SNMP::getErrno and
	 * SNMP::getError can be used for retrieving error
	 * number (specific to SNMP extension, see class constants) and error message
	 * respectively.
	 */
	public function walk (string $object_id, bool $suffix_as_key = null, int $max_repetitions = null, int $non_repeaters = null) {}

	/**
	 * Set the value of an SNMP object
	 * @link http://www.php.net/manual/en/snmp.set.php
	 * @param mixed $object_id <p>
	 * The SNMP object id
	 * </p>
	 * <p>
	 * When count of OIDs in object_id array is greater than
	 * max_oids object property set method will have to use multiple queries
	 * to perform requested value updates. In this case type and value checks
	 * are made per-chunk so second or subsequent requests may fail due to
	 * wrong type or value for OID requested. To mark this a warning is
	 * raised when count of OIDs in object_id array is greater than max_oids.
	 * </p>
	 * @param mixed $type snmp.set.type.values
	 * snmp.set.type.values.asn.mapping
	 * snmp.set.type.values.equal.note
	 * snmp.set.type.values.bitset.note
	 * @param mixed $value The new value.
	 * @return bool true on success or false on failure
	 */
	public function set ($object_id, $type, $value) {}

	/**
	 * Get last error code
	 * @link http://www.php.net/manual/en/snmp.geterrno.php
	 * @return int one of SNMP error code values described in constants chapter.
	 */
	public function getErrno () {}

	/**
	 * Get last error message
	 * @link http://www.php.net/manual/en/snmp.geterror.php
	 * @return string String describing error from last SNMP request.
	 */
	public function getError () {}

}

/**
 * Represents an error raised by SNMP. You should not throw a 
 * SNMPException from your own code.
 * See Exceptions for more
 * information about Exceptions in PHP.
 * @link http://www.php.net/manual/en/class.snmpexception.php
 */
class SNMPException extends RuntimeException implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param $message [optional]
	 * @param $code [optional]
	 * @param $previous [optional]
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
 * Fetch an SNMP object
 * @link http://www.php.net/manual/en/function.snmpget.php
 * @param string $hostname The SNMP agent.
 * @param string $community The read community.
 * @param string $object_id The SNMP object.
 * @param int $timeout [optional] The number of microseconds until the first timeout.
 * @param int $retries [optional] The number of times to retry if timeouts occur.
 * @return string SNMP object value on success or false on error.
 */
function snmpget (string $hostname, string $community, string $object_id, int $timeout = null, int $retries = null) {}

/**
 * Fetch the SNMP object which follows the given object id
 * @link http://www.php.net/manual/en/function.snmpgetnext.php
 * @param string $host The hostname of the SNMP agent (server).
 * @param string $community The read community.
 * @param string $object_id The SNMP object id which precedes the wanted one.
 * @param int $timeout [optional] The number of microseconds until the first timeout.
 * @param int $retries [optional] The number of times to retry if timeouts occur.
 * @return string SNMP object value on success or false on error.
 * In case of an error, an E_WARNING message is shown.
 */
function snmpgetnext (string $host, string $community, string $object_id, int $timeout = null, int $retries = null) {}

/**
 * Fetch all the SNMP objects from an agent
 * @link http://www.php.net/manual/en/function.snmpwalk.php
 * @param string $hostname The SNMP agent (server).
 * @param string $community The read community.
 * @param string $object_id <p>
 * If null, object_id is taken as the root of
 * the SNMP objects tree and all objects under that tree are returned as
 * an array. 
 * </p>
 * <p>
 * If object_id is specified, all the SNMP objects
 * below that object_id are returned.
 * </p>
 * @param int $timeout [optional] The number of microseconds until the first timeout.
 * @param int $retries [optional] The number of times to retry if timeouts occur.
 * @return array an array of SNMP object values starting from the
 * object_id as root or false on error.
 */
function snmpwalk (string $hostname, string $community, string $object_id, int $timeout = null, int $retries = null) {}

/**
 * Return all objects including their respective object ID within the specified one
 * @link http://www.php.net/manual/en/function.snmprealwalk.php
 * @param string $host The hostname of the SNMP agent (server).
 * @param string $community The read community.
 * @param string $object_id The SNMP object id which precedes the wanted one.
 * @param int $timeout [optional] The number of microseconds until the first timeout.
 * @param int $retries [optional] The number of times to retry if timeouts occur.
 * @return array an associative array of the SNMP object ids and their values on success or false on error.
 * In case of an error, an E_WARNING message is shown.
 */
function snmprealwalk (string $host, string $community, string $object_id, int $timeout = null, int $retries = null) {}

/**
 * Query for a tree of information about a network entity
 * @link http://www.php.net/manual/en/function.snmpwalkoid.php
 * @param string $hostname The SNMP agent.
 * @param string $community The read community.
 * @param string $object_id <p>
 * If null, object_id is taken as the root of
 * the SNMP objects tree and all objects under that tree are returned as
 * an array. 
 * </p>
 * <p>
 * If object_id is specified, all the SNMP objects
 * below that object_id are returned.
 * </p>
 * @param int $timeout [optional] The number of microseconds until the first timeout.
 * @param int $retries [optional] The number of times to retry if timeouts occur.
 * @return array an associative array with object ids and their respective
 * object value starting from the object_id
 * as root or false on error.
 */
function snmpwalkoid (string $hostname, string $community, string $object_id, int $timeout = null, int $retries = null) {}

/**
 * Set the value of an SNMP object
 * @link http://www.php.net/manual/en/function.snmpset.php
 * @param string $host The hostname of the SNMP agent (server).
 * @param string $community The write community.
 * @param string $object_id The SNMP object id.
 * @param string $type snmp.set.type.values
 * snmp.set.type.values.asn.mapping
 * snmp.set.type.values.equal.note
 * snmp.set.type.values.bitset.note
 * @param mixed $value The new value.
 * @param int $timeout [optional] The number of microseconds until the first timeout.
 * @param int $retries [optional] The number of times to retry if timeouts occur.
 * @return bool true on success or false on failure
 * <p>
 * If the SNMP host rejects the data type, an E_WARNING message like "Warning: Error in packet. Reason: (badValue) The value given has the wrong type or length." is shown.
 * If an unknown or invalid OID is specified the warning probably reads "Could not add variable".
 * </p>
 */
function snmpset (string $host, string $community, string $object_id, string $type, $value, int $timeout = null, int $retries = null) {}

/**
 * Fetches the current value of the UCD library's quick_print setting
 * @link http://www.php.net/manual/en/function.snmp-get-quick-print.php
 * @param $d
 * @return bool true if quick_print is on, false otherwise.
 */
function snmp_get_quick_print ($d) {}

/**
 * Set the value of quick_print within the UCD SNMP library
 * @link http://www.php.net/manual/en/function.snmp-set-quick-print.php
 * @param bool $quick_print 
 * @return bool 
 */
function snmp_set_quick_print (bool $quick_print) {}

/**
 * Return all values that are enums with their enum value instead of the raw integer
 * @link http://www.php.net/manual/en/function.snmp-set-enum-print.php
 * @param int $enum_print As the value is interpreted as boolean by the Net-SNMP library, it can only be "0" or "1".
 * @return bool 
 */
function snmp_set_enum_print (int $enum_print) {}

/**
 * Set the OID output format
 * @link http://www.php.net/manual/en/function.snmp-set-oid-output-format.php
 * @param int $oid_format <table>
 * OID .1.3.6.1.2.1.1.3.0 representation for various oid_format values
 * <table>
 * <tr valign="top"><td>SNMP_OID_OUTPUT_FULL</td><td>.iso.org.dod.internet.mgmt.mib-2.system.sysUpTime.sysUpTimeInstance</td></tr>
 * <tr valign="top"><td>SNMP_OID_OUTPUT_NUMERIC</td><td>.1.3.6.1.2.1.1.3.0</td> </tr>
 * </table>
 * </table>
 * <p>Begining from PHP 5.4.0 four additional constants available:
 * <table>
 * <table>
 * <tr valign="top"><td>SNMP_OID_OUTPUT_MODULE</td><td>DISMAN-EVENT-MIB::sysUpTimeInstance</td></tr>
 * <tr valign="top"><td>SNMP_OID_OUTPUT_SUFFIX</td><td>sysUpTimeInstance</td></tr>
 * <tr valign="top"><td>SNMP_OID_OUTPUT_UCD</td><td>system.sysUpTime.sysUpTimeInstance</td></tr>
 * <tr valign="top"><td>SNMP_OID_OUTPUT_NONE</td><td>Undefined</td></tr>
 * </table>
 * </table> 
 * </p>
 * @return bool 
 */
function snmp_set_oid_output_format (int $oid_format) {}

/**
 * Set the OID output format
 * @link http://www.php.net/manual/en/function.snmp-set-oid-numeric-print.php
 * @param int $oid_format 
 * @return void 
 */
function snmp_set_oid_numeric_print (int $oid_format) {}

/**
 * Fetch an SNMP object
 * @link http://www.php.net/manual/en/function.snmp2-get.php
 * @param string $host The SNMP agent.
 * @param string $community The read community.
 * @param string $object_id The SNMP object.
 * @param string $timeout [optional] The number of microseconds until the first timeout.
 * @param string $retries [optional] The number of times to retry if timeouts occur.
 * @return string SNMP object value on success or false on error.
 */
function snmp2_get (string $host, string $community, string $object_id, string $timeout = null, string $retries = null) {}

/**
 * Fetch the SNMP object which follows the given object id
 * @link http://www.php.net/manual/en/function.snmp2-getnext.php
 * @param string $host The hostname of the SNMP agent (server).
 * @param string $community The read community.
 * @param string $object_id The SNMP object id which precedes the wanted one.
 * @param string $timeout [optional] The number of microseconds until the first timeout.
 * @param string $retries [optional] The number of times to retry if timeouts occur.
 * @return string SNMP object value on success or false on error.
 * In case of an error, an E_WARNING message is shown.
 */
function snmp2_getnext (string $host, string $community, string $object_id, string $timeout = null, string $retries = null) {}

/**
 * Fetch all the SNMP objects from an agent
 * @link http://www.php.net/manual/en/function.snmp2-walk.php
 * @param string $host The SNMP agent (server).
 * @param string $community The read community.
 * @param string $object_id <p>
 * If null, object_id is taken as the root of
 * the SNMP objects tree and all objects under that tree are returned as
 * an array.
 * </p>
 * <p>
 * If object_id is specified, all the SNMP objects
 * below that object_id are returned.
 * </p>
 * @param string $timeout [optional] The number of microseconds until the first timeout.
 * @param string $retries [optional] The number of times to retry if timeouts occur.
 * @return array an array of SNMP object values starting from the
 * object_id as root or false on error.
 */
function snmp2_walk (string $host, string $community, string $object_id, string $timeout = null, string $retries = null) {}

/**
 * Return all objects including their respective object ID within the specified one
 * @link http://www.php.net/manual/en/function.snmp2-real-walk.php
 * @param string $host The hostname of the SNMP agent (server).
 * @param string $community The read community.
 * @param string $object_id The SNMP object id which precedes the wanted one.
 * @param string $timeout [optional] The number of microseconds until the first timeout.
 * @param string $retries [optional] The number of times to retry if timeouts occur.
 * @return array an associative array of the SNMP object ids and their values on success or false on error.
 * In case of an error, an E_WARNING message is shown.
 */
function snmp2_real_walk (string $host, string $community, string $object_id, string $timeout = null, string $retries = null) {}

/**
 * Set the value of an SNMP object
 * @link http://www.php.net/manual/en/function.snmp2-set.php
 * @param string $host The hostname of the SNMP agent (server).
 * @param string $community The write community.
 * @param string $object_id The SNMP object id.
 * @param string $type snmp.set.type.values
 * snmp.set.type.values.asn.mapping
 * snmp.set.type.values.equal.note
 * snmp.set.type.values.bitset.note
 * @param string $value The new value.
 * @param string $timeout [optional] The number of microseconds until the first timeout.
 * @param string $retries [optional] The number of times to retry if timeouts occur.
 * @return bool true on success or false on failure
 * <p>
 * If the SNMP host rejects the data type, an E_WARNING message like "Warning: Error in packet. Reason: (badValue) The value given has the wrong type or length." is shown.
 * If an unknown or invalid OID is specified the warning probably reads "Could not add variable".
 * </p>
 */
function snmp2_set (string $host, string $community, string $object_id, string $type, string $value, string $timeout = null, string $retries = null) {}

/**
 * Fetch an SNMP object
 * @link http://www.php.net/manual/en/function.snmp3-get.php
 * @param string $host The hostname of the SNMP agent (server).
 * @param string $sec_name the security name, usually some kind of username
 * @param string $sec_level the security level (noAuthNoPriv|authNoPriv|authPriv)
 * @param string $auth_protocol the authentication protocol (MD5 or SHA)
 * @param string $auth_passphrase the authentication pass phrase
 * @param string $priv_protocol the privacy protocol (DES or AES)
 * @param string $priv_passphrase the privacy pass phrase
 * @param string $object_id The SNMP object id.
 * @param string $timeout [optional] The number of microseconds until the first timeout.
 * @param string $retries [optional] The number of times to retry if timeouts occur.
 * @return string SNMP object value on success or false on error.
 */
function snmp3_get (string $host, string $sec_name, string $sec_level, string $auth_protocol, string $auth_passphrase, string $priv_protocol, string $priv_passphrase, string $object_id, string $timeout = null, string $retries = null) {}

/**
 * Fetch the SNMP object which follows the given object id
 * @link http://www.php.net/manual/en/function.snmp3-getnext.php
 * @param string $host The hostname of the
 * SNMP agent (server).
 * @param string $sec_name the security name, usually some kind of username
 * @param string $sec_level the security level (noAuthNoPriv|authNoPriv|authPriv)
 * @param string $auth_protocol the authentication protocol (MD5 or SHA)
 * @param string $auth_passphrase the authentication pass phrase
 * @param string $priv_protocol the privacy protocol (DES or AES)
 * @param string $priv_passphrase the privacy pass phrase
 * @param string $object_id The SNMP object id.
 * @param string $timeout [optional] The number of microseconds until the first timeout.
 * @param string $retries [optional] The number of times to retry if timeouts occur.
 * @return string SNMP object value on success or false on error.
 * In case of an error, an E_WARNING message is shown.
 */
function snmp3_getnext (string $host, string $sec_name, string $sec_level, string $auth_protocol, string $auth_passphrase, string $priv_protocol, string $priv_passphrase, string $object_id, string $timeout = null, string $retries = null) {}

/**
 * Fetch all the SNMP objects from an agent
 * @link http://www.php.net/manual/en/function.snmp3-walk.php
 * @param string $host The hostname of the SNMP agent (server).
 * @param string $sec_name the security name, usually some kind of username
 * @param string $sec_level the security level (noAuthNoPriv|authNoPriv|authPriv)
 * @param string $auth_protocol the authentication protocol (MD5 or SHA)
 * @param string $auth_passphrase the authentication pass phrase
 * @param string $priv_protocol the privacy protocol (DES or AES)
 * @param string $priv_passphrase the privacy pass phrase
 * @param string $object_id <p>
 * If null, object_id is taken as the root of
 * the SNMP objects tree and all objects under that tree are returned as
 * an array. 
 * </p>
 * <p>
 * If object_id is specified, all the SNMP objects
 * below that object_id are returned.
 * </p>
 * @param string $timeout [optional] The number of microseconds until the first timeout.
 * @param string $retries [optional] The number of times to retry if timeouts occur.
 * @return array an array of SNMP object values starting from the
 * object_id as root or false on error.
 */
function snmp3_walk (string $host, string $sec_name, string $sec_level, string $auth_protocol, string $auth_passphrase, string $priv_protocol, string $priv_passphrase, string $object_id, string $timeout = null, string $retries = null) {}

/**
 * Return all objects including their respective object ID within the specified one
 * @link http://www.php.net/manual/en/function.snmp3-real-walk.php
 * @param string $host The hostname of the
 * SNMP agent (server).
 * @param string $sec_name the security name, usually some kind of username
 * @param string $sec_level the security level (noAuthNoPriv|authNoPriv|authPriv)
 * @param string $auth_protocol the authentication protocol (MD5 or SHA)
 * @param string $auth_passphrase the authentication pass phrase
 * @param string $priv_protocol the privacy protocol (DES or AES)
 * @param string $priv_passphrase the privacy pass phrase
 * @param string $object_id The SNMP object id.
 * @param string $timeout [optional] The number of microseconds until the first timeout.
 * @param string $retries [optional] The number of times to retry if timeouts occur.
 * @return array an associative array of the
 * SNMP object ids and their values on success or false on error.
 * In case of an error, an E_WARNING message is shown.
 */
function snmp3_real_walk (string $host, string $sec_name, string $sec_level, string $auth_protocol, string $auth_passphrase, string $priv_protocol, string $priv_passphrase, string $object_id, string $timeout = null, string $retries = null) {}

/**
 * Set the value of an SNMP object
 * @link http://www.php.net/manual/en/function.snmp3-set.php
 * @param string $host The hostname of the SNMP agent (server).
 * @param string $sec_name the security name, usually some kind of username
 * @param string $sec_level the security level (noAuthNoPriv|authNoPriv|authPriv)
 * @param string $auth_protocol the authentication protocol (MD5 or SHA)
 * @param string $auth_passphrase the authentication pass phrase
 * @param string $priv_protocol the privacy protocol (DES or AES)
 * @param string $priv_passphrase the privacy pass phrase
 * @param string $object_id The SNMP object id.
 * @param string $type snmp.set.type.values
 * snmp.set.type.values.asn.mapping
 * snmp.set.type.values.equal.note
 * snmp.set.type.values.bitset.note
 * @param string $value The new value
 * @param int $timeout [optional] The number of microseconds until the first timeout.
 * @param int $retries [optional] The number of times to retry if timeouts occur.
 * @return bool true on success or false on failure
 * <p>
 * If the SNMP host rejects the data type, an E_WARNING message like "Warning: Error in packet. Reason: (badValue) The value given has the wrong type or length." is shown.
 * If an unknown or invalid OID is specified the warning probably reads "Could not add variable".
 * </p>
 */
function snmp3_set (string $host, string $sec_name, string $sec_level, string $auth_protocol, string $auth_passphrase, string $priv_protocol, string $priv_passphrase, string $object_id, string $type, string $value, int $timeout = null, int $retries = null) {}

/**
 * Specify the method how the SNMP values will be returned
 * @link http://www.php.net/manual/en/function.snmp-set-valueretrieval.php
 * @param int $method <table>
 * types
 * <table>
 * <tr valign="top">
 * <td>SNMP_VALUE_LIBRARY</td>
 * <td>The return values will be as returned by the Net-SNMP library.</td>
 * </tr>
 * <tr valign="top">
 * <td>SNMP_VALUE_PLAIN</td>
 * <td>The return values will be the plain value without the SNMP type hint.</td>
 * </tr>
 * <tr valign="top">
 * <td>SNMP_VALUE_OBJECT</td>
 * <td>
 * The return values will be objects with the properties "value" and "type", where the latter
 * is one of the SNMP_OCTET_STR, SNMP_COUNTER etc. constants. The
 * way "value" is returned is based on which one of constants
 * SNMP_VALUE_LIBRARY, SNMP_VALUE_PLAIN is set.
 * </td>
 * </tr>
 * </table>
 * </table>
 * @return bool 
 */
function snmp_set_valueretrieval (int $method) {}

/**
 * Return the method how the SNMP values will be returned
 * @link http://www.php.net/manual/en/function.snmp-get-valueretrieval.php
 * @return int OR-ed combitantion of constants ( SNMP_VALUE_LIBRARY or
 * SNMP_VALUE_PLAIN ) with
 * possible SNMP_VALUE_OBJECT set.
 */
function snmp_get_valueretrieval () {}

/**
 * Reads and parses a MIB file into the active MIB tree
 * @link http://www.php.net/manual/en/function.snmp-read-mib.php
 * @param string $filename The filename of the MIB.
 * @return bool 
 */
function snmp_read_mib (string $filename) {}


/**
 * As of 5.4.0
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_OID_OUTPUT_SUFFIX', 1);

/**
 * As of 5.4.0
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_OID_OUTPUT_MODULE', 2);

/**
 * As of 5.2.0
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_OID_OUTPUT_FULL', 3);

/**
 * As of 5.2.0
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_OID_OUTPUT_NUMERIC', 4);

/**
 * As of 5.4.0
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_OID_OUTPUT_UCD', 5);

/**
 * As of 5.4.0
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_OID_OUTPUT_NONE', 6);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_VALUE_LIBRARY', 0);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_VALUE_PLAIN', 1);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_VALUE_OBJECT', 2);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_BIT_STR', 3);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_OCTET_STR', 4);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_OPAQUE', 68);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_NULL', 5);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_OBJECT_ID', 6);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_IPADDRESS', 64);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_COUNTER', 66);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_UNSIGNED', 66);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_TIMETICKS', 67);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_UINTEGER', 71);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_INTEGER', 2);

/**
 * 
 * @link http://www.php.net/manual/en/snmp.constants.php
 */
define ('SNMP_COUNTER64', 70);

// End of snmp v.0.1
