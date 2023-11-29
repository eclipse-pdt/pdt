<?php

// Start of soap v.8.3.0

class SoapClient  {

	/**
	 * {@inheritdoc}
	 * @param string|null $wsdl
	 * @param array $options [optional]
	 */
	public function __construct (?string $wsdl = null, array $options = array (
)) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param array $args
	 */
	public function __call (string $name, array $args) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param array $args
	 * @param array|null $options [optional]
	 * @param mixed $inputHeaders [optional]
	 * @param mixed $outputHeaders [optional]
	 */
	public function __soapCall (string $name, array $args, ?array $options = NULL, $inputHeaders = NULL, &$outputHeaders = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __getFunctions () {}

	/**
	 * {@inheritdoc}
	 */
	public function __getTypes () {}

	/**
	 * {@inheritdoc}
	 */
	public function __getLastRequest () {}

	/**
	 * {@inheritdoc}
	 */
	public function __getLastResponse () {}

	/**
	 * {@inheritdoc}
	 */
	public function __getLastRequestHeaders () {}

	/**
	 * {@inheritdoc}
	 */
	public function __getLastResponseHeaders () {}

	/**
	 * {@inheritdoc}
	 * @param string $request
	 * @param string $location
	 * @param string $action
	 * @param int $version
	 * @param bool $oneWay [optional]
	 */
	public function __doRequest (string $request, string $location, string $action, int $version, bool $oneWay = false) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param string|null $value [optional]
	 */
	public function __setCookie (string $name, ?string $value = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __getCookies () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $headers [optional]
	 */
	public function __setSoapHeaders ($headers = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $location [optional]
	 */
	public function __setLocation (?string $location = NULL) {}

}

class SoapVar  {

	public int $enc_type;

	public mixed $enc_value;

	public ?string $enc_stype;

	public ?string $enc_ns;

	public ?string $enc_name;

	public ?string $enc_namens;

	/**
	 * {@inheritdoc}
	 * @param mixed $data
	 * @param int|null $encoding
	 * @param string|null $typeName [optional]
	 * @param string|null $typeNamespace [optional]
	 * @param string|null $nodeName [optional]
	 * @param string|null $nodeNamespace [optional]
	 */
	public function __construct (mixed $data = null, ?int $encoding = null, ?string $typeName = NULL, ?string $typeNamespace = NULL, ?string $nodeName = NULL, ?string $nodeNamespace = NULL) {}

}

class SoapServer  {

	/**
	 * {@inheritdoc}
	 * @param string|null $wsdl
	 * @param array $options [optional]
	 */
	public function __construct (?string $wsdl = null, array $options = array (
)) {}

	/**
	 * {@inheritdoc}
	 * @param string $code
	 * @param string $string
	 * @param string $actor [optional]
	 * @param mixed $details [optional]
	 * @param string $name [optional]
	 */
	public function fault (string $code, string $string, string $actor = '', mixed $details = NULL, string $name = '') {}

	/**
	 * {@inheritdoc}
	 * @param SoapHeader $header
	 */
	public function addSoapHeader (SoapHeader $header) {}

	/**
	 * {@inheritdoc}
	 * @param int $mode
	 */
	public function setPersistence (int $mode) {}

	/**
	 * {@inheritdoc}
	 * @param string $class
	 * @param mixed $args [optional]
	 */
	public function setClass (string $class, mixed ...$args) {}

	/**
	 * {@inheritdoc}
	 * @param object $object
	 */
	public function setObject (object $object) {}

	/**
	 * {@inheritdoc}
	 */
	public function getFunctions () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $functions
	 */
	public function addFunction ($functions = null) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $request [optional]
	 */
	public function handle (?string $request = NULL) {}

}

class SoapFault extends Exception implements Stringable, Throwable {

	public string $faultstring;

	public ?string $faultcode;

	public ?string $faultcodens;

	public ?string $faultactor;

	public mixed $detail;

	public ?string $_name;

	public mixed $headerfault;

	/**
	 * {@inheritdoc}
	 * @param array|string|null $code
	 * @param string $string
	 * @param string|null $actor [optional]
	 * @param mixed $details [optional]
	 * @param string|null $name [optional]
	 * @param mixed $headerFault [optional]
	 */
	public function __construct (array|string|null $code = null, string $string, ?string $actor = NULL, mixed $details = NULL, ?string $name = NULL, mixed $headerFault = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

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

}

class SoapParam  {

	public string $param_name;

	public mixed $param_data;

	/**
	 * {@inheritdoc}
	 * @param mixed $data
	 * @param string $name
	 */
	public function __construct (mixed $data = null, string $name) {}

}

class SoapHeader  {

	public string $namespace;

	public string $name;

	public mixed $data;

	public bool $mustUnderstand;

	public string|int|null $actor;

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 * @param string $name
	 * @param mixed $data [optional]
	 * @param bool $mustUnderstand [optional]
	 * @param string|int|null $actor [optional]
	 */
	public function __construct (string $namespace, string $name, mixed $data = NULL, bool $mustUnderstand = false, string|int|null $actor = NULL) {}

}

/**
 * {@inheritdoc}
 * @param bool $enable [optional]
 */
function use_soap_error_handler (bool $enable = true): bool {}

/**
 * {@inheritdoc}
 * @param mixed $object
 */
function is_soap_fault (mixed $object = null): bool {}

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

// End of soap v.8.3.0
