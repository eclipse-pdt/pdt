<?php

// Start of soap v.8.1.19

/**
 * The SoapClient class provides a client for SOAP 1.1,
 * SOAP 1.2 servers.
 * It can be used in WSDL or non-WSDL mode.
 * @link http://www.php.net/manual/en/class.soapclient.php
 */
class SoapClient  {
	private $uri;
	private $style;
	private $use;
	private $location;
	private $trace;
	private $compression;
	private $sdl;
	private $typemap;
	private $httpsocket;
	private $httpurl;
	private $_login;
	private $_password;
	private $_use_digest;
	private $_digest;
	private $_proxy_host;
	private $_proxy_port;
	private $_proxy_login;
	private $_proxy_password;
	private $_exceptions;
	private $_encoding;
	private $_classmap;
	private $_features;
	private $_connection_timeout;
	private $_stream_context;
	private $_user_agent;
	private $_keep_alive;
	private $_ssl_method;
	private $_soap_version;
	private $_use_proxy;
	private $_cookies;
	private $__default_headers;
	private $__soap_fault;
	private $__last_request;
	private $__last_response;
	private $__last_request_headers;
	private $__last_response_headers;


	/**
	 * SoapClient constructor
	 * @link http://www.php.net/manual/en/soapclient.construct.php
	 * @param ?string|null $wsdl
	 * @param array[] $options [optional]
	 */
	public function __construct (?string|null $wsdl = nullarray , $options = 'Array') {}

	/**
	 * Calls a SOAP function (deprecated)
	 * @link http://www.php.net/manual/en/soapclient.call.php
	 * @param string $name The name of the SOAP function to call.
	 * @param array $args An array of the arguments to pass to the function.
	 * This can be either an ordered or an associative array.
	 * Note that most SOAP servers require parameter names to be provided, in which case this must be an associative array.
	 * @return mixed SOAP functions may return one, or multiple values. If only one value is
	 * returned by the SOAP function, the return value will be a scalar.
	 * If multiple values are returned, an associative array of named output
	 * parameters is returned instead.
	 * <p>
	 * On error, if the SoapClient object was constructed
	 * with the exceptions option set to false,
	 * a SoapFault object will be returned.
	 * </p>
	 */
	public function __call (string $name, array $args) {}

	/**
	 * Calls a SOAP function
	 * @link http://www.php.net/manual/en/soapclient.soapcall.php
	 * @param string $name The name of the SOAP function to call.
	 * @param array $args An array of the arguments to pass to the function. This can be either
	 * an ordered or an associative array. Note that most SOAP servers require
	 * parameter names to be provided, in which case this must be an
	 * associative array.
	 * @param mixed $options [optional] <p>
	 * An associative array of options to pass to the client.
	 * </p>
	 * <p>
	 * The location option is the URL of the remote Web service.
	 * </p>
	 * <p>
	 * The uri option is the target namespace of the SOAP service.
	 * </p>
	 * <p>
	 * The soapaction option is the action to call.
	 * </p>
	 * @param mixed $inputHeaders [optional] An array of headers to be sent along with the SOAP request.
	 * @param array $outputHeaders [optional] If supplied, this array will be filled with the headers from the SOAP response.
	 * @return mixed SOAP functions may return one, or multiple values. If only one value is
	 * returned by the SOAP function, the return value will be a scalar.
	 * If multiple values are returned, an associative array of named output
	 * parameters is returned instead.
	 * <p>
	 * On error, if the SoapClient object was constructed
	 * with the exceptions option set to false,
	 * a SoapFault object will be returned.
	 * </p>
	 */
	public function __soapCall (string $name, array $args, $options = null, $inputHeaders = null, array &$outputHeaders = null) {}

	/**
	 * Returns list of available SOAP functions
	 * @link http://www.php.net/manual/en/soapclient.getfunctions.php
	 * @return mixed The array of SOAP function prototypes, detailing the return type,
	 * the function name and parameter types.
	 */
	public function __getFunctions () {}

	/**
	 * Returns a list of SOAP types
	 * @link http://www.php.net/manual/en/soapclient.gettypes.php
	 * @return mixed The array of SOAP types, detailing all structures and types.
	 */
	public function __getTypes () {}

	/**
	 * Returns last SOAP request
	 * @link http://www.php.net/manual/en/soapclient.getlastrequest.php
	 * @return mixed The last SOAP request, as an XML string.
	 */
	public function __getLastRequest () {}

	/**
	 * Returns last SOAP response
	 * @link http://www.php.net/manual/en/soapclient.getlastresponse.php
	 * @return mixed The last SOAP response, as an XML string.
	 */
	public function __getLastResponse () {}

	/**
	 * Returns the SOAP headers from the last request
	 * @link http://www.php.net/manual/en/soapclient.getlastrequestheaders.php
	 * @return mixed The last SOAP request headers.
	 */
	public function __getLastRequestHeaders () {}

	/**
	 * Returns the SOAP headers from the last response
	 * @link http://www.php.net/manual/en/soapclient.getlastresponseheaders.php
	 * @return mixed The last SOAP response headers.
	 */
	public function __getLastResponseHeaders () {}

	/**
	 * Performs a SOAP request
	 * @link http://www.php.net/manual/en/soapclient.dorequest.php
	 * @param string $request The XML SOAP request.
	 * @param string $location The URL to request.
	 * @param string $action The SOAP action.
	 * @param int $version The SOAP version.
	 * @param bool $oneWay [optional] If one_way is set to 1, this method returns nothing. 
	 * Use this where a response is not expected.
	 * @return mixed The XML SOAP response.
	 */
	public function __doRequest (string $request, string $location, string $action, int $version, bool $oneWay = null) {}

	/**
	 * Defines a cookie for SOAP requests
	 * @link http://www.php.net/manual/en/soapclient.setcookie.php
	 * @param string $name The name of the cookie.
	 * @param mixed $value [optional] The value of the cookie. If not specified, the cookie will be deleted.
	 * @return void 
	 */
	public function __setCookie (string $name, $value = null) {}

	/**
	 * Get list of cookies
	 * @link http://www.php.net/manual/en/soapclient.getcookies.php
	 * @return array 
	 */
	public function __getCookies () {}

	/**
	 * Sets SOAP headers for subsequent calls
	 * @link http://www.php.net/manual/en/soapclient.setsoapheaders.php
	 * @param mixed $headers [optional] The headers to be set. It could be SoapHeader
	 * object or array of SoapHeader objects.
	 * If not specified or set to null, the headers will be deleted.
	 * @return bool true on success or false on failure
	 */
	public function __setSoapHeaders ($headers = null) {}

	/**
	 * Sets the location of the Web service to use
	 * @link http://www.php.net/manual/en/soapclient.setlocation.php
	 * @param mixed $location [optional] The new endpoint URL.
	 * @return mixed The old endpoint URL.
	 */
	public function __setLocation ($location = null) {}

}

/**
 * A class representing a variable or object for use with SOAP services.
 * @link http://www.php.net/manual/en/class.soapvar.php
 */
class SoapVar  {
	public $enc_type;
	public $enc_value;
	public $enc_stype;
	public $enc_ns;
	public $enc_name;
	public $enc_namens;


	/**
	 * SoapVar constructor
	 * @link http://www.php.net/manual/en/soapvar.construct.php
	 * @param mixed|null $data
	 * @param ?int|null $encoding
	 * @param ?string|null $typeName [optional]
	 * @param ?string|null $typeNamespace [optional]
	 * @param ?string|null $nodeName [optional]
	 * @param ?string|null $nodeNamespace [optional]
	 */
	public function __construct (mixed|null $data = null?int|null , $encoding = null?string|null , $typeName = null?string|null , $typeNamespace = null?string|null , $nodeName = null?string|null , $nodeNamespace = null) {}

}

/**
 * The SoapServer class provides a server for the SOAP 1.1
 * and SOAP 1.2 protocols. It can be
 * used with or without a WSDL service description.
 * @link http://www.php.net/manual/en/class.soapserver.php
 */
class SoapServer  {
	private $service;
	private $__soap_fault;


	/**
	 * SoapServer constructor
	 * @link http://www.php.net/manual/en/soapserver.construct.php
	 * @param ?string|null $wsdl
	 * @param array[] $options [optional]
	 */
	public function __construct (?string|null $wsdl = nullarray , $options = 'Array') {}

	/**
	 * Issue SoapServer fault indicating an error
	 * @link http://www.php.net/manual/en/soapserver.fault.php
	 * @param string $code The error code to return
	 * @param string $string A brief description of the error
	 * @param string $actor [optional] A string identifying the actor that caused the fault.
	 * @param mixed $details [optional] More details of the fault
	 * @param string $name [optional] The name of the fault. This can be used to select a name from a WSDL file.
	 * @return void 
	 */
	public function fault (string $code, string $string, string $actor = null, $details = null, string $name = null) {}

	/**
	 * Add a SOAP header to the response
	 * @link http://www.php.net/manual/en/soapserver.addsoapheader.php
	 * @param SoapHeader $header The header to be returned.
	 * @return void 
	 */
	public function addSoapHeader (SoapHeader $header) {}

	/**
	 * Sets SoapServer persistence mode
	 * @link http://www.php.net/manual/en/soapserver.setpersistence.php
	 * @param int $mode <p>
	 * One of the SOAP_PERSISTENCE_XXX constants.
	 * </p>
	 * <p>
	 * SOAP_PERSISTENCE_REQUEST - SoapServer data does not persist between
	 * requests. This is the default behavior of any SoapServer
	 * object after setClass is called. 
	 * </p>
	 * <p>
	 * SOAP_PERSISTENCE_SESSION - SoapServer data persists between requests.
	 * This is accomplished by serializing the SoapServer class data into
	 * $_SESSION['_bogus_session_name'], because of this 
	 * session_start must be called before this persistence mode is set. 
	 * </p>
	 * @return void 
	 */
	public function setPersistence (int $mode) {}

	/**
	 * Sets the class which handles SOAP requests
	 * @link http://www.php.net/manual/en/soapserver.setclass.php
	 * @param string $class The name of the exported class.
	 * @param mixed $args These optional parameters will be passed to the default class constructor
	 * during object creation.
	 * @return void 
	 */
	public function setClass (string $class, $args) {}

	/**
	 * Sets the object which will be used to handle SOAP requests
	 * @link http://www.php.net/manual/en/soapserver.setobject.php
	 * @param object $object The object to handle the requests.
	 * @return void 
	 */
	public function setObject ($object) {}

	/**
	 * Returns list of defined functions
	 * @link http://www.php.net/manual/en/soapserver.getfunctions.php
	 * @return array An array of the defined functions.
	 */
	public function getFunctions () {}

	/**
	 * Adds one or more functions to handle SOAP requests
	 * @link http://www.php.net/manual/en/soapserver.addfunction.php
	 * @param mixed $functions <p>
	 * To export one function, pass the function name into this parameter as
	 * a string.
	 * </p>
	 * <p>
	 * To export several functions, pass an array of function names.
	 * </p>
	 * <p>
	 * To export all the functions, pass a special constant SOAP_FUNCTIONS_ALL.
	 * </p>
	 * <p>
	 * functions must receive all input arguments in the same
	 * order as defined in the WSDL file (They should not receive any output parameters
	 * as arguments) and return one or more values. To return several values they must
	 * return an array with named output parameters.
	 * </p>
	 * @return void 
	 */
	public function addFunction ($functions) {}

	/**
	 * Handles a SOAP request
	 * @link http://www.php.net/manual/en/soapserver.handle.php
	 * @param mixed $request [optional] The SOAP request. If this argument is omitted, the request is assumed to be
	 * in the raw POST data of the HTTP request.
	 * @return void 
	 */
	public function handle ($request = null) {}

}

/**
 * Represents a SOAP fault.
 * @link http://www.php.net/manual/en/class.soapfault.php
 */
class SoapFault extends Exception implements Stringable, Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	public $faultstring;
	public $faultcode;
	public $faultcodens;
	public $faultactor;
	public $detail;
	public $_name;
	public $headerfault;


	/**
	 * SoapFault constructor
	 * @link http://www.php.net/manual/en/soapfault.construct.php
	 * @param array|string|null|null $code
	 * @param string $string
	 * @param ?string|null $actor [optional]
	 * @param mixed|null $details [optional]
	 * @param ?string|null $name [optional]
	 * @param mixed|null $headerFault [optional]
	 */
	public function __construct (array|string|null|null $code = nullstring , $string?string|null , $actor = nullmixed|null , $details = null?string|null , $name = nullmixed|null , $headerFault = null) {}

	/**
	 * Obtain a string representation of a SoapFault
	 * @link http://www.php.net/manual/en/soapfault.tostring.php
	 * @return string A string describing the SoapFault.
	 */
	public function __toString (): string {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ??Throwable {}

	final public function getTraceAsString (): string {}

}

/**
 * Represents parameter to a SOAP call.
 * @link http://www.php.net/manual/en/class.soapparam.php
 */
class SoapParam  {
	public $param_name;
	public $param_data;


	/**
	 * SoapParam constructor
	 * @link http://www.php.net/manual/en/soapparam.construct.php
	 * @param mixed|null $data
	 * @param string $name
	 */
	public function __construct (mixed|null $data = nullstring , $name) {}

}

/**
 * Represents a SOAP header.
 * @link http://www.php.net/manual/en/class.soapheader.php
 */
class SoapHeader  {
	public $namespace;
	public $name;
	public $data;
	public $mustUnderstand;
	public $actor;


	/**
	 * SoapHeader constructor
	 * @link http://www.php.net/manual/en/soapheader.construct.php
	 * @param string $namespace
	 * @param string $name
	 * @param mixed|null $data [optional]
	 * @param bool $mustUnderstand [optional]
	 * @param string|int|null|null $actor [optional]
	 */
	public function __construct (string $namespacestring , $namemixed|null , $data = nullbool , $mustUnderstand = ''string|int|null|null , $actor = null) {}

}

/**
 * Set whether to use the SOAP error handler
 * @link http://www.php.net/manual/en/function.use-soap-error-handler.php
 * @param bool $enable [optional] Set to true to send error details to clients.
 * @return bool the original value.
 */
function use_soap_error_handler (bool $enable = null): bool {}

/**
 * Checks if a SOAP call has failed
 * @link http://www.php.net/manual/en/function.is-soap-fault.php
 * @param mixed $object The object to test.
 * @return bool This will return true on error, and false otherwise.
 */
function is_soap_fault ($object): bool {}


/**
 * Specifies use of SOAP 1.1 when passed as soap_version
 * option to SoapServer::__construct or
 * SoapClient::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_1_1', 1);

/**
 * Specifies use of SOAP 1.2 when passed as soap_version
 * option to SoapServer::__construct or
 * SoapClient::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_1_2', 2);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_PERSISTENCE_SESSION', 1);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_PERSISTENCE_REQUEST', 2);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_FUNCTIONS_ALL', 999);

/**
 * Specifies use of SOAP Encoding when passed as use
 * option to SoapClient::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_ENCODED', 1);

/**
 * Specifies use of service-specific encoding when passed as use
 * option to SoapClient::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_LITERAL', 2);

/**
 * Specifies use of RPC-style binding when passed as style
 * option to SoapClient::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_RPC', 1);

/**
 * Specifies use of document binding when passed as style
 * option to SoapClient::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_DOCUMENT', 2);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_ACTOR_NEXT', 1);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_ACTOR_NONE', 2);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_ACTOR_UNLIMATERECEIVER', 3);

/**
 * Specifies use of an "Accept-Encoding" header
 * when passed as part of
 * the compression option
 * to SoapClient::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_COMPRESSION_ACCEPT', 32);

/**
 * Specifies use of gzip compression
 * when passed as part of
 * the compression option
 * to SoapClient::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_COMPRESSION_GZIP', 0);

/**
 * Specifies use of deflate compression
 * when passed as part of
 * the compression option
 * to SoapClient::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_COMPRESSION_DEFLATE', 16);

/**
 * Specifies use of HTTP Basic Authentication when passed as
 * authentication option to
 * SoapClient::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_AUTHENTICATION_BASIC', 0);

/**
 * Specifies use of HTTP Digest Authentication when passed as
 * authentication option to
 * SoapClient::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_AUTHENTICATION_DIGEST', 1);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('UNKNOWN_TYPE', 999998);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_STRING', 101);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_BOOLEAN', 102);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_DECIMAL', 103);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_FLOAT', 104);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_DOUBLE', 105);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_DURATION', 106);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_DATETIME', 107);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_TIME', 108);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_DATE', 109);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_GYEARMONTH', 110);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_GYEAR', 111);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_GMONTHDAY', 112);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_GDAY', 113);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_GMONTH', 114);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_HEXBINARY', 115);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_BASE64BINARY', 116);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_ANYURI', 117);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_QNAME', 118);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_NOTATION', 119);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_NORMALIZEDSTRING', 120);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_TOKEN', 121);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_LANGUAGE', 122);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_NMTOKEN', 123);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_NAME', 124);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_NCNAME', 125);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_ID', 126);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_IDREF', 127);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_IDREFS', 128);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_ENTITY', 129);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_ENTITIES', 130);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_INTEGER', 131);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_NONPOSITIVEINTEGER', 132);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_NEGATIVEINTEGER', 133);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_LONG', 134);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_INT', 135);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_SHORT', 136);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_BYTE', 137);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_NONNEGATIVEINTEGER', 138);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_UNSIGNEDLONG', 139);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_UNSIGNEDINT', 140);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_UNSIGNEDSHORT', 141);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_UNSIGNEDBYTE', 142);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_POSITIVEINTEGER', 143);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_NMTOKENS', 144);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_ANYTYPE', 145);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_ANYXML', 147);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('APACHE_MAP', 200);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_ENC_OBJECT', 301);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_ENC_ARRAY', 300);

/**
 * 
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_1999_TIMEINSTANT', 401);

/**
 * Used with the
 * features option
 * to SoapClient::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('XSD_NAMESPACE', "http://www.w3.org/2001/XMLSchema");
define ('XSD_1999_NAMESPACE', "http://www.w3.org/1999/XMLSchema");
define ('SOAP_SINGLE_ELEMENT_ARRAYS', 1);

/**
 * Used with the
 * features option
 * to SoapClient::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_WAIT_ONE_WAY_CALLS', 2);

/**
 * Used with the
 * features option
 * to SoapClient::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_USE_XSI_ARRAY_TYPE', 4);

/**
 * Disables the WSDL cache when used in the
 * soap.wsdl_cache
 * configuration option or the
 * wsdl_cache option
 * to SoapClient::__construct
 * and SoapServer::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('WSDL_CACHE_NONE', 0);

/**
 * Specifies use of the on-disk WSDL cache only when used in the
 * soap.wsdl_cache
 * configuration option or the
 * wsdl_cache option
 * to SoapClient::__construct
 * and SoapServer::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('WSDL_CACHE_DISK', 1);

/**
 * Specifies use of the in-memory WSDL cache only when used in the
 * soap.wsdl_cache
 * configuration option or the
 * wsdl_cache option
 * to SoapClient::__construct
 * and SoapServer::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('WSDL_CACHE_MEMORY', 2);

/**
 * Specifies use of both on-disk and in-memory WSDL caches when used in the
 * soap.wsdl_cache
 * configuration option or the
 * wsdl_cache option
 * to SoapClient::__construct
 * and SoapServer::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('WSDL_CACHE_BOTH', 3);

/**
 * Used with the deprecated
 * ssl_method option
 * to SoapClient::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_SSL_METHOD_TLS', 0);

/**
 * Used with the deprecated
 * ssl_method option
 * to SoapClient::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_SSL_METHOD_SSLv2', 1);

/**
 * Used with the deprecated
 * ssl_method option
 * to SoapClient::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_SSL_METHOD_SSLv3', 2);

/**
 * Used with the deprecated
 * ssl_method option
 * to SoapClient::__construct.
 * @link http://www.php.net/manual/en/soap.constants.php
 */
define ('SOAP_SSL_METHOD_SSLv23', 3);

// End of soap v.8.1.19
