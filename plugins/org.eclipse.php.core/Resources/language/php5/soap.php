<?php

// Start of soap v.

class SoapClient  {

	public function SoapClient () {}

	/**
	 * @param var1
	 * @param var2
	 */
	public function __call ($var1, $var2) {}

	/**
	 * @param function_name
	 * @param arguments
	 * @param options[optional]
	 * @param input_headers[optional]
	 * @param output_headers[optional]
	 */
	public function __soapCall ($function_name, $arguments, $options, $input_headers, &$output_headers) {}

	public function __getLastRequest () {}

	public function __getLastResponse () {}

	public function __getLastRequestHeaders () {}

	public function __getLastResponseHeaders () {}

	public function __getFunctions () {}

	public function __getTypes () {}

	public function __doRequest () {}

	public function __setCookie () {}

	public function __setLocation () {}

	public function __setSoapHeaders () {}

}

class SoapVar  {

	public function SoapVar () {}

}

class SoapServer  {

	public function SoapServer () {}

	public function setPersistence () {}

	public function setClass () {}

	public function setObject () {}

	public function addFunction () {}

	public function getFunctions () {}

	public function handle () {}

	public function fault () {}

	public function addSoapHeader () {}

}

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

class SoapParam  {

	public function SoapParam () {}

}

class SoapHeader  {

	public function SoapHeader () {}

}

/**
 * Set whether to use the SOAP error handler
 * @link http://php.net/manual/en/function.use-soap-error-handler.php
 * @param handler bool[optional] <p>
 * Set to true to send error details to clients.
 * </p>
 * @return bool 
 */
function use_soap_error_handler ($handler = null) {}

/**
 * Checks if a SOAP call has failed
 * @link http://php.net/manual/en/function.is-soap-fault.php
 * @param object mixed <p>
 * The object to test.
 * </p>
 * @return bool This will return true on error, and false otherwise.
 */
function is_soap_fault ($object) {}

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

// End of soap v.
?>
