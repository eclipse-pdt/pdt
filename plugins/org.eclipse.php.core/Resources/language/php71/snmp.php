<?php

// Start of snmp v.0.1

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
	 * Creates SNMP instance representing session to remote <acronym>SNMP</acronym> agent
	 * @link http://www.php.net/manual/en/snmp.construct.php
	 * @param $version
	 * @param $host
	 * @param $community
	 * @param $timeout [optional]
	 * @param $retries [optional]
	 */
	public function __construct ($version, $host, $community, $timeout = null, $retries = null) {}

	/**
	 * Close <acronym>SNMP</acronym> session
	 * @link http://www.php.net/manual/en/snmp.close.php
	 */
	public function close () {}

	/**
	 * Configures security-related <acronym>SNMP</acronym>v3 session parameters
	 * @link http://www.php.net/manual/en/snmp.setsecurity.php
	 * @param $sec_level
	 * @param $auth_protocol
	 * @param $auth_passphrase
	 * @param $priv_protocol
	 * @param $priv_passphrase
	 * @param $contextName
	 * @param $contextEngineID
	 */
	public function setSecurity ($sec_level, $auth_protocol, $auth_passphrase, $priv_protocol, $priv_passphrase, $contextName, $contextEngineID) {}

	/**
	 * Fetch an <acronym>SNMP</acronym> object
	 * @link http://www.php.net/manual/en/snmp.get.php
	 * @param $object_id
	 * @param $use_orignames [optional]
	 */
	public function get ($object_id, $use_orignames = null) {}

	/**
	 * Fetch an <acronym>SNMP</acronym> object which
  follows the given object id
	 * @link http://www.php.net/manual/en/snmp.getnext.php
	 * @param $object_id
	 * @param $use_orignames [optional]
	 */
	public function getnext ($object_id, $use_orignames = null) {}

	/**
	 * Fetch <acronym>SNMP</acronym> object subtree
	 * @link http://www.php.net/manual/en/snmp.walk.php
	 * @param $object_id
	 * @param $suffix_keys
	 * @param $max_repetitions
	 * @param $non_repeaters
	 */
	public function walk ($object_id, $suffix_keys, $max_repetitions, $non_repeaters) {}

	/**
	 * Set the value of an SNMP object
	 * @link http://www.php.net/manual/en/snmp.set.php
	 * @param $object_id
	 * @param $type
	 * @param $value
	 */
	public function set ($object_id, $type, $value) {}

	/**
	 * Get last error code
	 * @link http://www.php.net/manual/en/snmp.geterrno.php
	 */
	public function getErrno () {}

	/**
	 * Get last error message
	 * @link http://www.php.net/manual/en/snmp.geterror.php
	 */
	public function getError () {}

}

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
 * Fetch an <acronym>SNMP</acronym> object
 * @link http://www.php.net/manual/en/function.snmpget.php
 * @param string $hostname <p>
 * The SNMP agent.
 * </p>
 * @param string $community <p>
 * The read community.
 * </p>
 * @param string $object_id <p>
 * The SNMP object.
 * </p>
 * @param int $timeout [optional] <p>
 * The number of microseconds until the first timeout.
 * </p>
 * @param int $retries [optional] <p>
 * The number of times to retry if timeouts occur.
 * </p>
 * @return string SNMP object value on success or false on error.
 */
function snmpget ($hostname, $community, $object_id, $timeout = null, $retries = null) {}

/**
 * Fetch the <acronym>SNMP</acronym> object which follows the given object id
 * @link http://www.php.net/manual/en/function.snmpgetnext.php
 * @param string $host <p>The hostname of the SNMP agent (server).</p>
 * @param string $community <p>The read community.</p>
 * @param string $object_id <p>The SNMP object id which precedes the wanted one.</p>
 * @param int $timeout [optional] <p>The number of microseconds until the first timeout.</p>
 * @param int $retries [optional] <p>The number of times to retry if timeouts occur.</p>
 * @return string SNMP object value on success or false on error.
 * In case of an error, an E_WARNING message is shown.
 */
function snmpgetnext ($host, $community, $object_id, $timeout = null, $retries = null) {}

/**
 * Fetch all the <acronym>SNMP</acronym> objects from an agent
 * @link http://www.php.net/manual/en/function.snmpwalk.php
 * @param string $hostname <p>
 * The SNMP agent (server).
 * </p>
 * @param string $community <p>
 * The read community.
 * </p>
 * @param string $object_id <p>
 * If &null;, object_id is taken as the root of
 * the SNMP objects tree and all objects under that tree are returned as
 * an array. 
 * </p>
 * <p>
 * If object_id is specified, all the SNMP objects
 * below that object_id are returned.
 * </p>
 * @param int $timeout [optional] <p>
 * The number of microseconds until the first timeout.
 * </p>
 * @param int $retries [optional] <p>The number of times to retry if timeouts occur.</p>
 * @return array an array of SNMP object values starting from the
 * object_id as root or false on error.
 */
function snmpwalk ($hostname, $community, $object_id, $timeout = null, $retries = null) {}

/**
 * Return all objects including their respective object ID within the specified one
 * @link http://www.php.net/manual/en/function.snmprealwalk.php
 * @param string $host <p>The hostname of the SNMP agent (server).</p>
 * @param string $community <p>The read community.</p>
 * @param string $object_id <p>The SNMP object id which precedes the wanted one.</p>
 * @param int $timeout [optional] <p>The number of microseconds until the first timeout.</p>
 * @param int $retries [optional] <p>The number of times to retry if timeouts occur.</p>
 * @return array an associative array of the SNMP object ids and their values on success or false on error.
 * In case of an error, an E_WARNING message is shown.
 */
function snmprealwalk ($host, $community, $object_id, $timeout = null, $retries = null) {}

/**
 * Query for a tree of information about a network entity
 * @link http://www.php.net/manual/en/function.snmpwalkoid.php
 * @param string $hostname <p>
 * The SNMP agent.
 * </p>
 * @param string $community <p>
 * The read community.
 * </p>
 * @param string $object_id <p>
 * If &null;, object_id is taken as the root of
 * the SNMP objects tree and all objects under that tree are returned as
 * an array. 
 * </p>
 * <p>
 * If object_id is specified, all the SNMP objects
 * below that object_id are returned.
 * </p>
 * @param int $timeout [optional] <p>
 * The number of microseconds until the first timeout.
 * </p>
 * @param int $retries [optional] <p>
 * The number of times to retry if timeouts occur.
 * </p>
 * @return array an associative array with object ids and their respective
 * object value starting from the object_id
 * as root or false on error.
 */
function snmpwalkoid ($hostname, $community, $object_id, $timeout = null, $retries = null) {}

/**
 * Set the value of an <acronym>SNMP</acronym> object
 * @link http://www.php.net/manual/en/function.snmpset.php
 * @param string $host <p>
 * The hostname of the SNMP agent (server).
 * </p>
 * @param string $community <p>
 * The write community.
 * </p>
 * @param string $object_id <p>
 * The SNMP object id.
 * </p>
 * @param string $type &snmp.set.type.values;
 * &snmp.set.type.values.asn.mapping;
 * &snmp.set.type.values.equal.note;
 * &snmp.set.type.values.bitset.note;
 * @param mixed $value <p>
 * The new value.
 * </p>
 * @param int $timeout [optional] <p>
 * The number of microseconds until the first timeout.
 * </p>
 * @param int $retries [optional] <p>
 * The number of times to retry if timeouts occur.
 * </p>
 * @return bool true on success or false on failure
 * <p>
 * If the SNMP host rejects the data type, an E_WARNING message like "Warning: Error in packet. Reason: (badValue) The value given has the wrong type or length." is shown.
 * If an unknown or invalid OID is specified the warning probably reads "Could not add variable".
 * </p>
 */
function snmpset ($host, $community, $object_id, $type, $value, $timeout = null, $retries = null) {}

/**
 * Fetches the current value of the UCD library's quick_print setting
 * @link http://www.php.net/manual/en/function.snmp-get-quick-print.php
 * @param $d
 * @return bool true if quick_print is on, false otherwise.
 */
function snmp_get_quick_print ($d) {}

/**
 * Set the value of <parameter>quick_print</parameter> within the UCD <acronym>SNMP</acronym> library
 * @link http://www.php.net/manual/en/function.snmp-set-quick-print.php
 * @param bool $quick_print <p>
 * </p>
 * @return bool 
 */
function snmp_set_quick_print ($quick_print) {}

/**
 * Return all values that are enums with their enum value instead of the raw integer
 * @link http://www.php.net/manual/en/function.snmp-set-enum-print.php
 * @param int $enum_print <p>
 * As the value is interpreted as boolean by the Net-SNMP library, it can only be "0" or "1".
 * </p>
 * @return bool 
 */
function snmp_set_enum_print ($enum_print) {}

/**
 * Set the OID output format
 * @link http://www.php.net/manual/en/function.snmp-set-oid-output-format.php
 * @param int $oid_format <table>
 * OID .1.3.6.1.2.1.1.3.0 representation for various oid_format values
 * <tr valign="top"><td>SNMP_OID_OUTPUT_FULL</td><td>.iso.org.dod.internet.mgmt.mib-2.system.sysUpTime.sysUpTimeInstance</td></tr>
 * <tr valign="top"><td>SNMP_OID_OUTPUT_NUMERIC</td><td>.1.3.6.1.2.1.1.3.0</td> </tr>
 * </table>
 * <p>Begining from PHP 5.4.0 four additional constants available:
 * <table>
 * <tr valign="top"><td>SNMP_OID_OUTPUT_MODULE</td><td>DISMAN-EVENT-MIB::sysUpTimeInstance</td></tr>
 * <tr valign="top"><td>SNMP_OID_OUTPUT_SUFFIX</td><td>sysUpTimeInstance</td></tr>
 * <tr valign="top"><td>SNMP_OID_OUTPUT_UCD</td><td>system.sysUpTime.sysUpTimeInstance</td></tr>
 * <tr valign="top"><td>SNMP_OID_OUTPUT_NONE</td><td>Undefined</td></tr>
 * </table> 
 * </p>
 * @return bool 
 */
function snmp_set_oid_output_format ($oid_format) {}

/**
 * Set the OID output format
 * @link http://www.php.net/manual/en/function.snmp-set-oid-numeric-print.php
 * @param int $oid_format 
 * @return void 
 */
function snmp_set_oid_numeric_print ($oid_format) {}

/**
 * Fetch an <acronym>SNMP</acronym> object
 * @link http://www.php.net/manual/en/function.snmp2-get.php
 * @param string $host <p>
 * The SNMP agent.
 * </p>
 * @param string $community <p>
 * The read community.
 * </p>
 * @param string $object_id <p>
 * The SNMP object.
 * </p>
 * @param string $timeout [optional] <p>
 * The number of microseconds until the first timeout.
 * </p>
 * @param string $retries [optional] <p>
 * The number of times to retry if timeouts occur.
 * </p>
 * @return string SNMP object value on success or false on error.
 */
function snmp2_get ($host, $community, $object_id, $timeout = null, $retries = null) {}

/**
 * Fetch the <acronym>SNMP</acronym> object which follows the given object id
 * @link http://www.php.net/manual/en/function.snmp2-getnext.php
 * @param string $host <p>
 * The hostname of the SNMP agent (server).
 * </p>
 * @param string $community <p>
 * The read community.
 * </p>
 * @param string $object_id <p>
 * The SNMP object id which precedes the wanted one.
 * </p>
 * @param string $timeout [optional] <p>
 * The number of microseconds until the first timeout.
 * </p>
 * @param string $retries [optional] <p>
 * The number of times to retry if timeouts occur.
 * </p>
 * @return string SNMP object value on success or false on error.
 * In case of an error, an E_WARNING message is shown.
 */
function snmp2_getnext ($host, $community, $object_id, $timeout = null, $retries = null) {}

/**
 * Fetch all the <acronym>SNMP</acronym> objects from an agent
 * @link http://www.php.net/manual/en/function.snmp2-walk.php
 * @param string $host <p>
 * The SNMP agent (server).
 * </p>
 * @param string $community <p>
 * The read community.
 * </p>
 * @param string $object_id <p>
 * If &null;, object_id is taken as the root of
 * the SNMP objects tree and all objects under that tree are returned as
 * an array.
 * </p>
 * <p>
 * If object_id is specified, all the SNMP objects
 * below that object_id are returned.
 * </p>
 * @param string $timeout [optional] <p>
 * The number of microseconds until the first timeout.
 * </p>
 * @param string $retries [optional] <p>
 * The number of times to retry if timeouts occur.
 * </p>
 * @return array an array of SNMP object values starting from the
 * object_id as root or false on error.
 */
function snmp2_walk ($host, $community, $object_id, $timeout = null, $retries = null) {}

/**
 * Return all objects including their respective object ID within the specified one
 * @link http://www.php.net/manual/en/function.snmp2-real-walk.php
 * @param string $host <p>
 * The hostname of the SNMP agent (server).
 * </p>
 * @param string $community <p>
 * The read community.
 * </p>
 * @param string $object_id <p>
 * The SNMP object id which precedes the wanted one.
 * </p>
 * @param string $timeout [optional] <p>
 * The number of microseconds until the first timeout.
 * </p>
 * @param string $retries [optional] <p>
 * The number of times to retry if timeouts occur.
 * </p>
 * @return array an associative array of the SNMP object ids and their values on success or false on error.
 * In case of an error, an E_WARNING message is shown.
 */
function snmp2_real_walk ($host, $community, $object_id, $timeout = null, $retries = null) {}

/**
 * Set the value of an <acronym>SNMP</acronym> object
 * @link http://www.php.net/manual/en/function.snmp2-set.php
 * @param string $host <p>
 * The hostname of the SNMP agent (server).
 * </p>
 * @param string $community <p>
 * The write community.
 * </p>
 * @param string $object_id <p>
 * The SNMP object id.
 * </p>
 * @param string $type &snmp.set.type.values;
 * &snmp.set.type.values.asn.mapping;
 * &snmp.set.type.values.equal.note;
 * &snmp.set.type.values.bitset.note;
 * @param string $value <p>
 * The new value.
 * </p>
 * @param string $timeout [optional] <p>
 * The number of microseconds until the first timeout.
 * </p>
 * @param string $retries [optional] <p>
 * The number of times to retry if timeouts occur.
 * </p>
 * @return bool true on success or false on failure
 * <p>
 * If the SNMP host rejects the data type, an E_WARNING message like "Warning: Error in packet. Reason: (badValue) The value given has the wrong type or length." is shown.
 * If an unknown or invalid OID is specified the warning probably reads "Could not add variable".
 * </p>
 */
function snmp2_set ($host, $community, $object_id, $type, $value, $timeout = null, $retries = null) {}

/**
 * Fetch an <acronym>SNMP</acronym> object
 * @link http://www.php.net/manual/en/function.snmp3-get.php
 * @param string $host <p>
 * The hostname of the SNMP agent (server).
 * </p>
 * @param string $sec_name <p>
 * the security name, usually some kind of username
 * </p>
 * @param string $sec_level <p>
 * the security level (noAuthNoPriv|authNoPriv|authPriv)
 * </p>
 * @param string $auth_protocol <p>
 * the authentication protocol (MD5 or SHA)
 * </p>
 * @param string $auth_passphrase <p>
 * the authentication pass phrase
 * </p>
 * @param string $priv_protocol <p>
 * the privacy protocol (DES or AES)
 * </p>
 * @param string $priv_passphrase <p>
 * the privacy pass phrase
 * </p>
 * @param string $object_id <p>
 * The SNMP object id.
 * </p>
 * @param string $timeout [optional] <p>
 * The number of microseconds until the first timeout.
 * </p>
 * @param string $retries [optional] <p>
 * The number of times to retry if timeouts occur.
 * </p>
 * @return string SNMP object value on success or false on error.
 */
function snmp3_get ($host, $sec_name, $sec_level, $auth_protocol, $auth_passphrase, $priv_protocol, $priv_passphrase, $object_id, $timeout = null, $retries = null) {}

/**
 * Fetch the <acronym>SNMP</acronym> object which follows the given object id
 * @link http://www.php.net/manual/en/function.snmp3-getnext.php
 * @param string $host <p>
 * The hostname of the
 * SNMP agent (server).
 * </p>
 * @param string $sec_name <p>
 * the security name, usually some kind of username
 * </p>
 * @param string $sec_level <p>
 * the security level (noAuthNoPriv|authNoPriv|authPriv)
 * </p>
 * @param string $auth_protocol <p>
 * the authentication protocol (MD5 or SHA)
 * </p>
 * @param string $auth_passphrase <p>
 * the authentication pass phrase
 * </p>
 * @param string $priv_protocol <p>
 * the privacy protocol (DES or AES)
 * </p>
 * @param string $priv_passphrase <p>
 * the privacy pass phrase
 * </p>
 * @param string $object_id <p>
 * The SNMP object id.
 * </p>
 * @param string $timeout [optional] <p>
 * The number of microseconds until the first timeout.
 * </p>
 * @param string $retries [optional] <p>
 * The number of times to retry if timeouts occur.
 * </p>
 * @return string SNMP object value on success or false on error.
 * In case of an error, an E_WARNING message is shown.
 */
function snmp3_getnext ($host, $sec_name, $sec_level, $auth_protocol, $auth_passphrase, $priv_protocol, $priv_passphrase, $object_id, $timeout = null, $retries = null) {}

/**
 * Fetch all the <acronym>SNMP</acronym> objects from an agent
 * @link http://www.php.net/manual/en/function.snmp3-walk.php
 * @param string $host <p>
 * The hostname of the SNMP agent (server).
 * </p>
 * @param string $sec_name <p>
 * the security name, usually some kind of username
 * </p>
 * @param string $sec_level <p>
 * the security level (noAuthNoPriv|authNoPriv|authPriv)
 * </p>
 * @param string $auth_protocol <p>
 * the authentication protocol (MD5 or SHA)
 * </p>
 * @param string $auth_passphrase <p>
 * the authentication pass phrase
 * </p>
 * @param string $priv_protocol <p>
 * the privacy protocol (DES or AES)
 * </p>
 * @param string $priv_passphrase <p>
 * the privacy pass phrase
 * </p>
 * @param string $object_id <p>
 * If &null;, object_id is taken as the root of
 * the SNMP objects tree and all objects under that tree are returned as
 * an array. 
 * </p>
 * <p>
 * If object_id is specified, all the SNMP objects
 * below that object_id are returned.
 * </p>
 * @param string $timeout [optional] <p>
 * The number of microseconds until the first timeout.
 * </p>
 * @param string $retries [optional] <p>
 * The number of times to retry if timeouts occur.
 * </p>
 * @return array an array of SNMP object values starting from the
 * object_id as root or false on error.
 */
function snmp3_walk ($host, $sec_name, $sec_level, $auth_protocol, $auth_passphrase, $priv_protocol, $priv_passphrase, $object_id, $timeout = null, $retries = null) {}

/**
 * Return all objects including their respective object ID within the specified one
 * @link http://www.php.net/manual/en/function.snmp3-real-walk.php
 * @param string $host <p>
 * The hostname of the
 * SNMP agent (server).
 * </p>
 * @param string $sec_name <p>
 * the security name, usually some kind of username
 * </p>
 * @param string $sec_level <p>
 * the security level (noAuthNoPriv|authNoPriv|authPriv)
 * </p>
 * @param string $auth_protocol <p>
 * the authentication protocol (MD5 or SHA)
 * </p>
 * @param string $auth_passphrase <p>
 * the authentication pass phrase
 * </p>
 * @param string $priv_protocol <p>
 * the privacy protocol (DES or AES)
 * </p>
 * @param string $priv_passphrase <p>
 * the privacy pass phrase
 * </p>
 * @param string $object_id <p>
 * The SNMP object id.
 * </p>
 * @param string $timeout [optional] <p>
 * The number of microseconds until the first timeout.
 * </p>
 * @param string $retries [optional] <p>
 * The number of times to retry if timeouts occur.
 * </p>
 * @return array an associative array of the
 * SNMP object ids and their values on success or false on error.
 * In case of an error, an E_WARNING message is shown.
 */
function snmp3_real_walk ($host, $sec_name, $sec_level, $auth_protocol, $auth_passphrase, $priv_protocol, $priv_passphrase, $object_id, $timeout = null, $retries = null) {}

/**
 * Set the value of an SNMP object
 * @link http://www.php.net/manual/en/function.snmp3-set.php
 * @param string $host <p>
 * The hostname of the SNMP agent (server).
 * </p>
 * @param string $sec_name <p>
 * the security name, usually some kind of username
 * </p>
 * @param string $sec_level <p>
 * the security level (noAuthNoPriv|authNoPriv|authPriv)
 * </p>
 * @param string $auth_protocol <p>
 * the authentication protocol (MD5 or SHA)
 * </p>
 * @param string $auth_passphrase <p>
 * the authentication pass phrase
 * </p>
 * @param string $priv_protocol <p>
 * the privacy protocol (DES or AES)
 * </p>
 * @param string $priv_passphrase <p>
 * the privacy pass phrase
 * </p>
 * @param string $object_id <p>
 * The SNMP object id.
 * </p>
 * @param string $type &snmp.set.type.values;
 * &snmp.set.type.values.asn.mapping;
 * &snmp.set.type.values.equal.note;
 * &snmp.set.type.values.bitset.note;
 * @param string $value <p>
 * The new value
 * </p>
 * @param int $timeout [optional] <p>
 * The number of microseconds until the first timeout.
 * </p>
 * @param int $retries [optional] <p>
 * The number of times to retry if timeouts occur.
 * </p>
 * @return bool true on success or false on failure
 * <p>
 * If the SNMP host rejects the data type, an E_WARNING message like "Warning: Error in packet. Reason: (badValue) The value given has the wrong type or length." is shown.
 * If an unknown or invalid OID is specified the warning probably reads "Could not add variable".
 * </p>
 */
function snmp3_set ($host, $sec_name, $sec_level, $auth_protocol, $auth_passphrase, $priv_protocol, $priv_passphrase, $object_id, $type, $value, $timeout = null, $retries = null) {}

/**
 * Specify the method how the SNMP values will be returned
 * @link http://www.php.net/manual/en/function.snmp-set-valueretrieval.php
 * @param int $method <table>
 * types
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
 * @return bool 
 */
function snmp_set_valueretrieval ($method) {}

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
 * @param string $filename <p>The filename of the MIB.</p>
 * @return bool 
 */
function snmp_read_mib ($filename) {}


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

// End of snmp v.0.1
