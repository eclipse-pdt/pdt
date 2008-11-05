<?php

// Start of soap v.

/**
 * @link http://php.net/manual/en/ref.soap.php
 */
class SoapClient  {

	public function SoapClient () {}

	/**
	 * Calls a SOAP function (deprecated)
	 * @link http://php.net/manual/en/function.soap-soapclient-call.php
	 * @param function_name string 
	 * @param arguments array 
	 * @param options array[optional] 
	 * @param input_headers array[optional] 
	 * @param output_headers array[optional] 
	 * @return mixed 
	 */
	public function __call ($function_name, array $arguments, array $options = null, array $input_headers = null, array $output_headers = null) {}

	/**
	 * Calls a SOAP function
	 * @link http://php.net/manual/en/function.soap-soapclient-soapcall.php
	 * @param function_name string 
	 * @param arguments array 
	 * @param options array[optional] 
	 * @param input_headers mixed[optional] 
	 * @param output_headers array[optional] 
	 * @return mixed SOAP functions may return one, or multiple values. If only one value is returned
	 * by the SOAP function, the return value of __soapCall will be
	 * a simple value (e.g. an integer, a string, etc). If multiple values are
	 * returned, __soapCall will return
	 * an associative array of named output parameters.
	 */
	public function __soapCall ($function_name, array $arguments, array $options = null, $input_headers = null, array &$output_headers = null) {}

	/**
	 * Returns last SOAP request
	 * @link http://php.net/manual/en/function.soap-soapclient-getlastrequest.php
	 * @return string The last SOAP request.
	 */
	public function __getLastRequest () {}

	/**
	 * Returns last SOAP response.
	 * @link http://php.net/manual/en/function.soap-soapclient-getlastresponse.php
	 * @return string The last SOAP response.
	 */
	public function __getLastResponse () {}

	/**
	 * Returns last SOAP request headers
	 * @link http://php.net/manual/en/function.soap-soapclient-getlastrequestheaders.php
	 * @return string The last SOAP request headers.
	 */
	public function __getLastRequestHeaders () {}

	/**
	 * Returns last SOAP response headers.
	 * @link http://php.net/manual/en/function.soap-soapclient-getlastresponseheaders.php
	 * @return string The last SOAP response headers.
	 */
	public function __getLastResponseHeaders () {}

	/**
	 * Returns list of SOAP functions
	 * @link http://php.net/manual/en/function.soap-soapclient-getfunctions.php
	 * @return array The list of SOAP functions.
	 */
	public function __getFunctions () {}

	/**
	 * Returns list of SOAP types
	 * @link http://php.net/manual/en/function.soap-soapclient-gettypes.php
	 * @return array The list of SOAP types.
	 */
	public function __getTypes () {}

	/**
	 * Performs a SOAP request
	 * @link http://php.net/manual/en/function.soap-soapclient-dorequest.php
	 * @param request string <p>
	 * The XML SOAP request.
	 * </p>
	 * @param location string <p>
	 * The URL to request.
	 * </p>
	 * @param action string <p>
	 * The SOAP action.
	 * </p>
	 * @param version int <p>
	 * The SOAP version.
	 * </p>
	 * @param one_way int[optional] <p>
	 * </p>
	 * @return string The XML SOAP response.
	 */
	public function __doRequest ($request, $location, $action, $version, $one_way = null) {}

	/**
	 * Sets the cookie that will be sent with the SOAP request
	 * @link http://php.net/manual/en/function.soap-soapclient-setcookie.php
	 * @param name string <p>
	 * The name of the cookie.
	 * </p>
	 * @param value string[optional] <p>
	 * The value of the cookie. If not specified, the cookie will be deleted.
	 * </p>
	 * @return void 
	 */
	public function __setCookie ($name, $value = null) {}

	public function __setLocation () {}

	public function __setSoapHeaders () {}

}

/**
 * SoapVar is a special low-level class for encoding
 * parameters and returning values in non-WSDL mode. It's
 * just a data holder and does not have any special methods except the constructor. 
 * It's useful when you want to set the type property in SOAP request or response.
 * @link http://php.net/manual/en/ref.soap.php
 */
class SoapVar  {

	public function SoapVar () {}

}

/**
 * @link http://php.net/manual/en/ref.soap.php
 */
class SoapServer  {

	public function SoapServer () {}

	/**
	 * Sets persistence mode of SoapServer
	 * @link http://php.net/manual/en/function.soap-soapserver-setpersistence.php
	 * @param mode int <p>
	 * One of the SOAP_PERSISTENCE_XXX constants.
	 * </p>
	 * @return void 
	 */
	public function setPersistence ($mode) {}

	/**
	 * Sets class which will handle SOAP requests
	 * @link http://php.net/manual/en/function.soap-soapserver-setclass.php
	 * @param class_name string <p>
	 * The name of the exported class.
	 * </p>
	 * @param args mixed[optional] <p>
	 * These optional parameters will be passed to the default class constructor
	 * during object creation. 
	 * </p>
	 * @param _ mixed[optional] 
	 * @return void 
	 */
	public function setClass ($class_name, $args = null, $_ = null) {}

	public function setObject () {}

	/**
	 * Adds one or several functions those will handle SOAP requests
	 * @link http://php.net/manual/en/function.soap-soapserver-addfunction.php
	 * @param functions mixed <p>
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
	 * Returns list of defined functions
	 * @link http://php.net/manual/en/function.soap-soapserver-getfunctions.php
	 * @return array The list of all functions.
	 */
	public function getFunctions () {}

	/**
	 * Handles a SOAP request
	 * @link http://php.net/manual/en/function.soap-soapserver-handle.php
	 * @param soap_request string[optional] <p>
	 * The SOAP request. If this argument is omitted, the request is supposed
	 * to be in the $HTTP_RAW_POST_DATA PHP variable.
	 * </p>
	 * @return void 
	 */
	public function handle ($soap_request = null) {}

	/**
	 * Issue SoapServer fault indicating an error
	 * @link http://php.net/manual/en/function.soap-soapserver-fault.php
	 * @param code string 
	 * @param string string 
	 * @param actor string[optional] 
	 * @param details mixed[optional] 
	 * @param name string[optional] 
	 * @return void 
	 */
	public function fault ($code, $string, $actor = null, $details = null, $name = null) {}

	public function addSoapHeader () {}

}

/**
 * @link http://php.net/manual/en/ref.soap.php
 */
class SoapFault extends Exception  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function SoapFault () {}

	public function __toString () {}

	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 */
	public function __construct ($message, $code) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getTraceAsString () {}

}

/**
 * SoapParam is a special low-level class for naming 
 * parameters and returning values in non-WSDL mode. 
 * It's just a data holder and it does not have any special methods except 
 * its constructor.
 * @link http://php.net/manual/en/ref.soap.php
 */
class SoapParam  {

	public function SoapParam () {}

}

/**
 * SoapHeader is a special low-level class for passing 
 * or returning SOAP headers. It's just a data holder and it does not have any 
 * special methods except its constructor. It can be used in the method to pass a SOAP header or 
 * in a SOAP header handler to return the header in a SOAP response.
 * @link http://php.net/manual/en/ref.soap.php
 */
class SoapHeader  {

	public function SoapHeader () {}

}

/**
 * Set whether to use the SOAP error handler and return the former value
 * @link http://php.net/manual/en/function.use-soap-error-handler.php
 * @param handler bool[optional] 
 * @return bool 
 */
function use_soap_error_handler ($handler = null) {}

/**
 * Checks if SOAP call was failed
 * @link http://php.net/manual/en/function.is-soap-fault.php
 * @param obj mixed <p>
 * The tested object.
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function is_soap_fault ($obj) {}

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

/**
 * Added in PHP 5.1.0.
 * @link http://php.net/manual/en/soap.constants.php
 */
define ('XSD_ANYXML', 147);
define ('APACHE_MAP', 200);
define ('SOAP_ENC_OBJECT', 301);
define ('SOAP_ENC_ARRAY', 300);
define ('XSD_1999_TIMEINSTANT', 401);
define ('XSD_NAMESPACE', "http://www.w3.org/2001/XMLSchema");
define ('XSD_1999_NAMESPACE', "http://www.w3.org/1999/XMLSchema");
define ('SOAP_SINGLE_ELEMENT_ARRAYS', 1);

/**
 * Added in PHP 5.1.0.
 * @link http://php.net/manual/en/soap.constants.php
 */
define ('SOAP_WAIT_ONE_WAY_CALLS', 2);
define ('SOAP_USE_XSI_ARRAY_TYPE', 4);

/**
 * Switches off WSDL caching even if
 * soap.wsdl_cache_enabled
 * is on. Available since PHP 5.1.5.
 * @link http://php.net/manual/en/soap.constants.php
 */
define ('WSDL_CACHE_NONE', 0);

/**
 * Available since PHP 5.1.5.
 * @link http://php.net/manual/en/soap.constants.php
 */
define ('WSDL_CACHE_DISK', 1);

/**
 * Caches WSDL data in process memory. Available since PHP 5.1.5.
 * @link http://php.net/manual/en/soap.constants.php
 */
define ('WSDL_CACHE_MEMORY', 2);

/**
 * Available since PHP 5.1.5.
 * @link http://php.net/manual/en/soap.constants.php
 */
define ('WSDL_CACHE_BOTH', 3);

// End of soap v.
?>
