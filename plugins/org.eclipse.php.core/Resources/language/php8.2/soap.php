<?php

// Start of soap v.8.2.6

/**
 * The SoapClient class provides a client for SOAP 1.1,
 * SOAP 1.2 servers.
 * It can be used in WSDL or non-WSDL mode.
 * @link http://www.php.net/manual/en/class.soapclient.php
 */
class SoapClient  {

	/**
	 * SoapClient constructor
	 * @link http://www.php.net/manual/en/soapclient.construct.php
	 * @param string|null $wsdl 
	 * @param array $options [optional] 
	 * @return string|null 
	 */
	public function __construct (?string $wsdl, array $options = '[]'): ?string {}

	/**
	 * Calls a SOAP function (deprecated)
	 * @link http://www.php.net/manual/en/soapclient.call.php
	 * @param string $name 
	 * @param array $args 
	 * @return mixed SOAP functions may return one, or multiple values. If only one value is
	 * returned by the SOAP function, the return value will be a scalar.
	 * If multiple values are returned, an associative array of named output
	 * parameters is returned instead.
	 * <p>On error, if the SoapClient object was constructed
	 * with the exceptions option set to false,
	 * a SoapFault object will be returned.</p>
	 */
	public function __call (string $name, array $args): mixed {}

	/**
	 * Calls a SOAP function
	 * @link http://www.php.net/manual/en/soapclient.soapcall.php
	 * @param string $name 
	 * @param array $args 
	 * @param array|null $options [optional] 
	 * @param SoapHeader|array|null $inputHeaders [optional] 
	 * @param array $outputHeaders [optional] 
	 * @return mixed SOAP functions may return one, or multiple values. If only one value is
	 * returned by the SOAP function, the return value will be a scalar.
	 * If multiple values are returned, an associative array of named output
	 * parameters is returned instead.
	 * <p>On error, if the SoapClient object was constructed
	 * with the exceptions option set to false,
	 * a SoapFault object will be returned.</p>
	 */
	public function __soapCall (string $name, array $args, ?array $options = null, SoapHeader|array|null $inputHeaders = null, array &$outputHeaders = null): mixed {}

	/**
	 * Returns list of available SOAP functions
	 * @link http://www.php.net/manual/en/soapclient.getfunctions.php
	 * @return array|null The array of SOAP function prototypes, detailing the return type,
	 * the function name and parameter types.
	 */
	public function __getFunctions (): ?array {}

	/**
	 * Returns a list of SOAP types
	 * @link http://www.php.net/manual/en/soapclient.gettypes.php
	 * @return array|null The array of SOAP types, detailing all structures and types.
	 */
	public function __getTypes (): ?array {}

	/**
	 * Returns last SOAP request
	 * @link http://www.php.net/manual/en/soapclient.getlastrequest.php
	 * @return string|null The last SOAP request, as an XML string.
	 */
	public function __getLastRequest (): ?string {}

	/**
	 * Returns last SOAP response
	 * @link http://www.php.net/manual/en/soapclient.getlastresponse.php
	 * @return string|null The last SOAP response, as an XML string.
	 */
	public function __getLastResponse (): ?string {}

	/**
	 * Returns the SOAP headers from the last request
	 * @link http://www.php.net/manual/en/soapclient.getlastrequestheaders.php
	 * @return string|null The last SOAP request headers.
	 */
	public function __getLastRequestHeaders (): ?string {}

	/**
	 * Returns the SOAP headers from the last response
	 * @link http://www.php.net/manual/en/soapclient.getlastresponseheaders.php
	 * @return string|null The last SOAP response headers.
	 */
	public function __getLastResponseHeaders (): ?string {}

	/**
	 * Performs a SOAP request
	 * @link http://www.php.net/manual/en/soapclient.dorequest.php
	 * @param string $request 
	 * @param string $location 
	 * @param string $action 
	 * @param int $version 
	 * @param bool $oneWay [optional] 
	 * @return string|null The XML SOAP response.
	 */
	public function __doRequest (string $request, string $location, string $action, int $version, bool $oneWay = false): ?string {}

	/**
	 * Defines a cookie for SOAP requests
	 * @link http://www.php.net/manual/en/soapclient.setcookie.php
	 * @param string $name 
	 * @param string|null $value [optional] 
	 * @return void No value is returned.
	 */
	public function __setCookie (string $name, ?string $value = null): void {}

	/**
	 * Get list of cookies
	 * @link http://www.php.net/manual/en/soapclient.getcookies.php
	 * @return array 
	 */
	public function __getCookies (): array {}

	/**
	 * Sets SOAP headers for subsequent calls
	 * @link http://www.php.net/manual/en/soapclient.setsoapheaders.php
	 * @param SoapHeader|array|null $headers [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function __setSoapHeaders (SoapHeader|array|null $headers = null): bool {}

	/**
	 * Sets the location of the Web service to use
	 * @link http://www.php.net/manual/en/soapclient.setlocation.php
	 * @param string|null $location [optional] 
	 * @return string|null The old endpoint URL.
	 */
	public function __setLocation (?string $location = null): ?string {}

}

/**
 * A class representing a variable or object for use with SOAP services.
 * @link http://www.php.net/manual/en/class.soapvar.php
 */
class SoapVar  {

	public int $enc_type;

	public mixed $enc_value;

	public ?string $enc_stype;

	public ?string $enc_ns;

	public ?string $enc_name;

	public ?string $enc_namens;

	/**
	 * SoapVar constructor
	 * @link http://www.php.net/manual/en/soapvar.construct.php
	 * @param mixed $data 
	 * @param int|null $encoding 
	 * @param string|null $typeName [optional] 
	 * @param string|null $typeNamespace [optional] 
	 * @param string|null $nodeName [optional] 
	 * @param string|null $nodeNamespace [optional] 
	 * @return mixed 
	 */
	public function __construct (mixed $data, ?int $encoding, ?string $typeName = null, ?string $typeNamespace = null, ?string $nodeName = null, ?string $nodeNamespace = null): mixed {}

}

/**
 * The SoapServer class provides a server for the SOAP 1.1
 * and SOAP 1.2 protocols. It can be
 * used with or without a WSDL service description.
 * @link http://www.php.net/manual/en/class.soapserver.php
 */
class SoapServer  {

	/**
	 * SoapServer constructor
	 * @link http://www.php.net/manual/en/soapserver.construct.php
	 * @param string|null $wsdl 
	 * @param array $options [optional] 
	 * @return string|null 
	 */
	public function __construct (?string $wsdl, array $options = '[]'): ?string {}

	/**
	 * Issue SoapServer fault indicating an error
	 * @link http://www.php.net/manual/en/soapserver.fault.php
	 * @param string $code 
	 * @param string $string 
	 * @param string $actor [optional] 
	 * @param mixed $details [optional] 
	 * @param string $name [optional] 
	 * @return void No value is returned.
	 */
	public function fault (string $code, string $string, string $actor = '""', mixed $details = null, string $name = '""'): void {}

	/**
	 * Add a SOAP header to the response
	 * @link http://www.php.net/manual/en/soapserver.addsoapheader.php
	 * @param SoapHeader $header 
	 * @return void No value is returned.
	 */
	public function addSoapHeader (SoapHeader $header): void {}

	/**
	 * Sets SoapServer persistence mode
	 * @link http://www.php.net/manual/en/soapserver.setpersistence.php
	 * @param int $mode 
	 * @return void No value is returned.
	 */
	public function setPersistence (int $mode): void {}

	/**
	 * Sets the class which handles SOAP requests
	 * @link http://www.php.net/manual/en/soapserver.setclass.php
	 * @param string $class 
	 * @param mixed $args 
	 * @return void No value is returned.
	 */
	public function setClass (string $class, mixed ...$args): void {}

	/**
	 * Sets the object which will be used to handle SOAP requests
	 * @link http://www.php.net/manual/en/soapserver.setobject.php
	 * @param object $object 
	 * @return void No value is returned.
	 */
	public function setObject (object $object): void {}

	/**
	 * Returns list of defined functions
	 * @link http://www.php.net/manual/en/soapserver.getfunctions.php
	 * @return array An array of the defined functions.
	 */
	public function getFunctions (): array {}

	/**
	 * Adds one or more functions to handle SOAP requests
	 * @link http://www.php.net/manual/en/soapserver.addfunction.php
	 * @param array|string|int $functions 
	 * @return void No value is returned.
	 */
	public function addFunction (array|string|int $functions): void {}

	/**
	 * Handles a SOAP request
	 * @link http://www.php.net/manual/en/soapserver.handle.php
	 * @param string|null $request [optional] 
	 * @return void No value is returned.
	 */
	public function handle (?string $request = null): void {}

}

/**
 * Represents a SOAP fault.
 * @link http://www.php.net/manual/en/class.soapfault.php
 */
class SoapFault extends Exception implements Stringable, Throwable {

	public string $faultstring;

	public ?string $faultcode;

	public ?string $faultcodens;

	public ?string $faultactor;

	public mixed $detail;

	public ?string $_name;

	public mixed $headerfault;

	/**
	 * SoapFault constructor
	 * @link http://www.php.net/manual/en/soapfault.construct.php
	 * @param array|string|null $code 
	 * @param string $string 
	 * @param string|null $actor [optional] 
	 * @param mixed $details [optional] 
	 * @param string|null $name [optional] 
	 * @param mixed $headerFault [optional] 
	 * @return array|string|null 
	 */
	public function __construct (array|string|null $code, string $string, ?string $actor = null, mixed $details = null, ?string $name = null, mixed $headerFault = null): array|string|null {}

	/**
	 * Obtain a string representation of a SoapFault
	 * @link http://www.php.net/manual/en/soapfault.tostring.php
	 * @return string A string describing the SoapFault.
	 */
	public function __toString (): string {}

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

}

/**
 * Represents parameter to a SOAP call.
 * @link http://www.php.net/manual/en/class.soapparam.php
 */
class SoapParam  {

	public string $param_name;

	public mixed $param_data;

	/**
	 * SoapParam constructor
	 * @link http://www.php.net/manual/en/soapparam.construct.php
	 * @param mixed $data 
	 * @param string $name 
	 * @return mixed 
	 */
	public function __construct (mixed $data, string $name): mixed {}

}

/**
 * Represents a SOAP header.
 * @link http://www.php.net/manual/en/class.soapheader.php
 */
class SoapHeader  {

	public string $namespace;

	public string $name;

	public mixed $data;

	public bool $mustUnderstand;

	public string|int|null $actor;

	/**
	 * SoapHeader constructor
	 * @link http://www.php.net/manual/en/soapheader.construct.php
	 * @param string $namespace 
	 * @param string $name 
	 * @param mixed $data [optional] 
	 * @param bool $mustunderstand [optional] 
	 * @param string $actor [optional] 
	 * @return string 
	 */
	public function __construct (string $namespace, string $name, mixed $data = null, bool $mustunderstand = null, string $actor = null): string {}

}

/**
 * Set whether to use the SOAP error handler
 * @link http://www.php.net/manual/en/function.use-soap-error-handler.php
 * @param bool $enable [optional] 
 * @return bool Returns the original value.
 */
function use_soap_error_handler (bool $enable = true): bool {}

/**
 * Checks if a SOAP call has failed
 * @link http://www.php.net/manual/en/function.is-soap-fault.php
 * @param mixed $object 
 * @return bool This will return true on error, and false otherwise.
 */
function is_soap_fault (mixed $object): bool {}

define ('SOAP_1_1', 1);
define ('SOAP_1_2', 2);
define ('SOAP_PERSISTENCE_SESSION', 1);
define ('SOAP_PERSISTENCE_REQUEST', 2);
define ('SOAP_FUNCTIONS_ALL', 999);
define ('SOAP_ENCODED', 1);
define ('SOAP_LITERAL', 2);
define ('SOAP_RPC', 1);
define ('SOAP_DOCUMENT', 2);
define ('SOAP_ACTOR_NEXT', 1);
define ('SOAP_ACTOR_NONE', 2);
define ('SOAP_ACTOR_UNLIMATERECEIVER', 3);
define ('SOAP_COMPRESSION_ACCEPT', 32);
define ('SOAP_COMPRESSION_GZIP', 0);
define ('SOAP_COMPRESSION_DEFLATE', 16);
define ('SOAP_AUTHENTICATION_BASIC', 0);
define ('SOAP_AUTHENTICATION_DIGEST', 1);
define ('UNKNOWN_TYPE', 999998);
define ('XSD_STRING', 101);
define ('XSD_BOOLEAN', 102);
define ('XSD_DECIMAL', 103);
define ('XSD_FLOAT', 104);
define ('XSD_DOUBLE', 105);
define ('XSD_DURATION', 106);
define ('XSD_DATETIME', 107);
define ('XSD_TIME', 108);
define ('XSD_DATE', 109);
define ('XSD_GYEARMONTH', 110);
define ('XSD_GYEAR', 111);
define ('XSD_GMONTHDAY', 112);
define ('XSD_GDAY', 113);
define ('XSD_GMONTH', 114);
define ('XSD_HEXBINARY', 115);
define ('XSD_BASE64BINARY', 116);
define ('XSD_ANYURI', 117);
define ('XSD_QNAME', 118);
define ('XSD_NOTATION', 119);
define ('XSD_NORMALIZEDSTRING', 120);
define ('XSD_TOKEN', 121);
define ('XSD_LANGUAGE', 122);
define ('XSD_NMTOKEN', 123);
define ('XSD_NAME', 124);
define ('XSD_NCNAME', 125);
define ('XSD_ID', 126);
define ('XSD_IDREF', 127);
define ('XSD_IDREFS', 128);
define ('XSD_ENTITY', 129);
define ('XSD_ENTITIES', 130);
define ('XSD_INTEGER', 131);
define ('XSD_NONPOSITIVEINTEGER', 132);
define ('XSD_NEGATIVEINTEGER', 133);
define ('XSD_LONG', 134);
define ('XSD_INT', 135);
define ('XSD_SHORT', 136);
define ('XSD_BYTE', 137);
define ('XSD_NONNEGATIVEINTEGER', 138);
define ('XSD_UNSIGNEDLONG', 139);
define ('XSD_UNSIGNEDINT', 140);
define ('XSD_UNSIGNEDSHORT', 141);
define ('XSD_UNSIGNEDBYTE', 142);
define ('XSD_POSITIVEINTEGER', 143);
define ('XSD_NMTOKENS', 144);
define ('XSD_ANYTYPE', 145);
define ('XSD_ANYXML', 147);
define ('APACHE_MAP', 200);
define ('SOAP_ENC_OBJECT', 301);
define ('SOAP_ENC_ARRAY', 300);
define ('XSD_1999_TIMEINSTANT', 401);
define ('XSD_NAMESPACE', "http://www.w3.org/2001/XMLSchema");
define ('XSD_1999_NAMESPACE', "http://www.w3.org/1999/XMLSchema");
define ('SOAP_SINGLE_ELEMENT_ARRAYS', 1);
define ('SOAP_WAIT_ONE_WAY_CALLS', 2);
define ('SOAP_USE_XSI_ARRAY_TYPE', 4);
define ('WSDL_CACHE_NONE', 0);
define ('WSDL_CACHE_DISK', 1);
define ('WSDL_CACHE_MEMORY', 2);
define ('WSDL_CACHE_BOTH', 3);
define ('SOAP_SSL_METHOD_TLS', 0);
define ('SOAP_SSL_METHOD_SSLv2', 1);
define ('SOAP_SSL_METHOD_SSLv3', 2);
define ('SOAP_SSL_METHOD_SSLv23', 3);

// End of soap v.8.2.6
