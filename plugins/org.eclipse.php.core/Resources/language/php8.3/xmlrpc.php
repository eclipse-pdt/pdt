<?php

// Start of xmlrpc v.7.4.0

/**
 * Generates XML for a PHP value
 * @link http://www.php.net/manual/en/function.xmlrpc-encode.php
 * @param mixed $value 
 * @return string 
 */
function xmlrpc_encode ($value) {}

/**
 * Decodes XML into native PHP types
 * @link http://www.php.net/manual/en/function.xmlrpc-decode.php
 * @param string $xml XML response returned by XMLRPC method.
 * @param string $encoding [optional] Input encoding supported by iconv.
 * @return mixed either an array, or an integer, or a string, or a boolean according
 * to the response returned by the XMLRPC method.
 */
function xmlrpc_decode (string $xml, string $encoding = null) {}

/**
 * Decodes XML into native PHP types
 * @link http://www.php.net/manual/en/function.xmlrpc-decode-request.php
 * @param string $xml 
 * @param string $method 
 * @param string $encoding [optional] 
 * @return mixed 
 */
function xmlrpc_decode_request (string $xml, string &$method, string $encoding = null) {}

/**
 * Generates XML for a method request
 * @link http://www.php.net/manual/en/function.xmlrpc-encode-request.php
 * @param string $method Name of the method to call.
 * @param mixed $params Method parameters compatible with method signature.
 * @param array $output_options [optional] <p>
 * Array specifying output options may contain (default values are
 * emphasised):
 * <p>
 * <br><p>output_type: php, xml</p>
 * <br><p>verbosity: no_white_space, newlines_only, pretty</p>
 * <br><p>escaping: cdata, non-ascii, non-print, markup
 * (may be a string with one value or an array with multiple values)</p>
 * <br><p>version: simple, xmlrpc, soap 1.1, auto</p>
 * <br><p>encoding: iso-8859-1, other character set supported by iconv</p>
 * </p>
 * </p>
 * @return string a string containing the XML representation of the request.
 */
function xmlrpc_encode_request (string $method, $params, array $output_options = null) {}

/**
 * Gets xmlrpc type for a PHP value
 * @link http://www.php.net/manual/en/function.xmlrpc-get-type.php
 * @param mixed $value PHP value
 * @return string the XML-RPC type.
 */
function xmlrpc_get_type ($value) {}

/**
 * Sets xmlrpc type, base64 or datetime, for a PHP string value
 * @link http://www.php.net/manual/en/function.xmlrpc-set-type.php
 * @param string $value Value to set the type
 * @param string $type 'base64' or 'datetime'
 * @return bool true on success or false on failure
 * If successful, value is converted to an object.
 */
function xmlrpc_set_type (string &$value, string $type) {}

/**
 * Determines if an array value represents an XMLRPC fault
 * @link http://www.php.net/manual/en/function.xmlrpc-is-fault.php
 * @param array $arg Array returned by xmlrpc_decode.
 * @return bool true if the argument means fault, false otherwise. Fault
 * description is available in $arg["faultString"], fault
 * code is in $arg["faultCode"].
 */
function xmlrpc_is_fault (array $arg) {}

/**
 * Creates an xmlrpc server
 * @link http://www.php.net/manual/en/function.xmlrpc-server-create.php
 * @return resource 
 */
function xmlrpc_server_create () {}

/**
 * Destroys server resources
 * @link http://www.php.net/manual/en/function.xmlrpc-server-destroy.php
 * @param resource $server 
 * @return bool 
 */
function xmlrpc_server_destroy ($server) {}

/**
 * Register a PHP function to resource method matching method_name
 * @link http://www.php.net/manual/en/function.xmlrpc-server-register-method.php
 * @param resource $server 
 * @param string $method_name 
 * @param string $function 
 * @return bool 
 */
function xmlrpc_server_register_method ($server, string $method_name, string $function) {}

/**
 * Parses XML requests and call methods
 * @link http://www.php.net/manual/en/function.xmlrpc-server-call-method.php
 * @param resource $server 
 * @param string $xml 
 * @param mixed $user_data 
 * @param array $output_options [optional] 
 * @return string 
 */
function xmlrpc_server_call_method ($server, string $xml, $user_data, array $output_options = null) {}

/**
 * Decodes XML into a list of method descriptions
 * @link http://www.php.net/manual/en/function.xmlrpc-parse-method-descriptions.php
 * @param string $xml 
 * @return array 
 */
function xmlrpc_parse_method_descriptions (string $xml) {}

/**
 * Adds introspection documentation
 * @link http://www.php.net/manual/en/function.xmlrpc-server-add-introspection-data.php
 * @param resource $server 
 * @param array $desc 
 * @return int 
 */
function xmlrpc_server_add_introspection_data ($server, array $desc) {}

/**
 * Register a PHP function to generate documentation
 * @link http://www.php.net/manual/en/function.xmlrpc-server-register-introspection-callback.php
 * @param resource $server 
 * @param string $function 
 * @return bool 
 */
function xmlrpc_server_register_introspection_callback ($server, string $function) {}

// End of xmlrpc v.7.4.0
