<?php
/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Robert Gruendler - initial API and implementation and/or initial documentation
 *******************************************************************************/

// Start of OAuth v.1.2.3

class OAuth  {
	public $debug;
	public $sslChecks;
	public $debugInfo;


	/**
	 * Create a new OAuth object
	 * @link http://www.php.net/manual/en/oauth.construct.php
	 * @param consumer_key
	 * @param consumer_secret
	 * @param signature_method[optional]
	 * @param auth_type[optional]
	 */
	public function __construct ($consumer_key, $consumer_secret, $signature_method, $auth_type) {}

	/**
	 * Set the RSA certificate
	 * @link http://www.php.net/manual/en/oauth.setrsacertificate.php
	 * @param cert string <p>
	 * The RSA certificate.
	 * </p>
	 * @return mixed true on success, or false on failure (e.g., the RSA certificate
	 * cannot be parsed.)
	 */
	public function setRSACertificate ($cert) {}

	/**
	 * Fetch a request token
	 * @link http://www.php.net/manual/en/oauth.getrequesttoken.php
	 * @param request_token_url string <p>
	 * URL to the request token API.
	 * </p>
	 * @param callback_url string[optional] <p>
	 * OAuth callback URL. If callback_url is passed and is an empty value, it is set to "oob" to address the OAuth 2009.1 advisory.
	 * </p>
	 * @return array an array containing the parsed OAuth response on success or false on failure.
	 */
	public function getRequestToken ($request_token_url, $callback_url = null) {}

	/**
	 * Fetch an access token
	 * @link http://www.php.net/manual/en/oauth.getaccesstoken.php
	 * @param access_token_url string <p>
	 * URL to the access token API.
	 * </p>
	 * @param auth_session_handle string[optional] <p>
	 * Authorization session handle, this parameter does not have any
	 * citation in the core OAuth 1.0 specification but may be 
	 * implemented by large providers. 
	 * See ScalableOAuth
	 * for more information.
	 * </p>
	 * @param verifier_token string[optional] <p>
	 * For service providers which support 1.0a, a verifier_token
	 * must be passed while exchanging the request token for the access
	 * token. If the verifier_token is present in $_GET 
	 * or $_POST it is passed automatically and the caller 
	 * does not need to specify a verifier_token (usually if the access token 
	 * is exchanged at the oauth_callback URL).
	 * See ScalableOAuth
	 * for more information.
	 * </p>
	 * @return array an array containing the parsed OAuth response on success or false on failure.
	 */
	public function getAccessToken ($access_token_url, $auth_session_handle = null, $verifier_token = null) {}

	/**
	 * Get the last response
	 * @link http://www.php.net/manual/en/oauth.getlastresponse.php
	 * @return string a string containing the last response.
	 */
	public function getLastResponse () {}

	/**
	 * Get HTTP information about the last response
	 * @link http://www.php.net/manual/en/oauth.getlastresponseinfo.php
	 * @return array an array containing the response information for the last
	 * request. Constants from curl_getinfo may be
	 * used.
	 */
	public function getLastResponseInfo () {}

	/**
	 * Get headers for last response
	 * @link http://www.php.net/manual/en/oauth.getlastresponseheaders.php
	 * @return string A string containing the last response's headers&return.falseforfailure;
	 */
	public function getLastResponseHeaders () {}

	/**
	 * Sets the token and secret
	 * @link http://www.php.net/manual/en/oauth.settoken.php
	 * @param token string <p>
	 * The OAuth token.
	 * </p>
	 * @param token_secret string <p>
	 * The OAuth token secret.
	 * </p>
	 * @return bool true
	 */
	public function setToken ($token, $token_secret) {}

	/**
	 * The setRequestEngine purpose
	 * @link http://www.php.net/manual/en/oauth.setrequestengine.php
	 * @param reqengine int <p>
	 * The desired request engine. Set to OAUTH_REQENGINE_STREAMS
	 * to use PHP Streams, or OAUTH_REQENGINE_CURL to use
	 * Curl.
	 * </p>
	 * @return void 
	 */
	public function setRequestEngine ($reqengine) {}

	/**
	 * Set the OAuth version
	 * @link http://www.php.net/manual/en/oauth.setversion.php
	 * @param version string <p>
	 * OAuth version, default value is always "1.0"
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function setVersion ($version) {}

	/**
	 * Set authorization type
	 * @link http://www.php.net/manual/en/oauth.setauthtype.php
	 * @param auth_type int <p>
	 * auth_type can be one of the following flags (in order of decreasing preference as per OAuth 1.0 section 5.2):
	 * OAUTH_AUTH_TYPE_AUTHORIZATION
	 * Pass the OAuth parameters in the HTTP Authorization header.
	 * @return mixed true if a parameter is correctly set, otherwise false
	 * (e.g., if an invalid auth_type is passed in.)
	 */
	public function setAuthType ($auth_type) {}

	/**
	 * Set the nonce for subsequent requests
	 * @link http://www.php.net/manual/en/oauth.setnonce.php
	 * @param nonce string <p>
	 * The value for oauth_nonce.
	 * </p>
	 * @return mixed true on success, or false if the
	 * nonce is considered invalid.
	 */
	public function setNonce ($nonce) {}

	/**
	 * Set the timestamp
	 * @link http://www.php.net/manual/en/oauth.settimestamp.php
	 * @param timestamp string <p>
	 * The timestamp.
	 * </p>
	 * @return mixed true, unless the timestamp is invalid, in which
	 * case false is returned.
	 */
	public function setTimestamp ($timestamp) {}

	/**
	 * Fetch an OAuth protected resource
	 * @link http://www.php.net/manual/en/oauth.fetch.php
	 * @param protected_resource_url string <p>
	 * URL to the OAuth protected resource.
	 * </p>
	 * @param extra_parameters array[optional] <p>
	 * Extra parameters to send with the request for the resource.
	 * </p>
	 * @param http_method string[optional] <p>
	 * One of the OAUTH_HTTP_METHOD_*
	 * OAUTH constants, which includes
	 * GET, POST, PUT, HEAD, or DELETE.
	 * </p>
	 * <p>
	 * HEAD (OAUTH_HTTP_METHOD_HEAD) can be useful for
	 * discovering information prior to the request (if OAuth credentials are
	 * in the Authorization header).
	 * </p>
	 * @param http_headers array[optional] <p>
	 * HTTP client headers (such as User-Agent, Accept, etc.)
	 * </p>
	 * @return mixed Returns true on success or false on failure.
	 */
	public function fetch ($protected_resource_url, array $extra_parameters = null, $http_method = null, array $http_headers = null) {}

	/**
	 * Turn on verbose debugging
	 * @link http://www.php.net/manual/en/oauth.enabledebug.php
	 * @return bool true
	 */
	public function enableDebug () {}

	/**
	 * Turn off verbose debugging
	 * @link http://www.php.net/manual/en/oauth.disabledebug.php
	 * @return bool true
	 */
	public function disableDebug () {}

	/**
	 * Turn on SSL checks
	 * @link http://www.php.net/manual/en/oauth.enablesslchecks.php
	 * @return bool true
	 */
	public function enableSSLChecks () {}

	/**
	 * Turn off SSL checks
	 * @link http://www.php.net/manual/en/oauth.disablesslchecks.php
	 * @return bool true
	 */
	public function disableSSLChecks () {}

	/**
	 * Turn on redirects
	 * @link http://www.php.net/manual/en/oauth.enableredirects.php
	 * @return bool true
	 */
	public function enableRedirects () {}

	/**
	 * Turn off redirects
	 * @link http://www.php.net/manual/en/oauth.disableredirects.php
	 * @return bool true
	 */
	public function disableRedirects () {}

	/**
	 * Set CA path and info
	 * @link http://www.php.net/manual/en/oauth.setcapath.php
	 * @param ca_path string[optional] <p>
	 * The CA Path being set.
	 * </p>
	 * @param ca_info string[optional] <p>
	 * The CA Info being set.
	 * </p>
	 * @return mixed true on success, or false if either ca_path
	 * or ca_info are considered invalid.
	 */
	public function setCAPath ($ca_path = null, $ca_info = null) {}

	/**
	 * Gets CA information
	 * @link http://www.php.net/manual/en/oauth.getcapath.php
	 * @return array An array of Certificate Authority information, specifically as
	 * ca_path and ca_info keys within the returned
	 * associative array.
	 */
	public function getCAPath () {}

	/**
	 * Generate a signature
	 * @link http://www.php.net/manual/en/oauth.generatesignature.php
	 * @param http_method string <p>
	 * HTTP method for request
	 * </p>
	 * @param url string <p>
	 * URL for request
	 * </p>
	 * @param extra_parameters mixed[optional] <p>
	 * String or array of additional parameters.
	 * </p>
	 * @return string A string containing the generated signature&return.falseforfailure;
	 */
	public function generateSignature ($http_method, $url, $extra_parameters = null) {}

	/**
	 * @param timeout_in_milliseconds
	 */
	public function setTimeout ($timeout_in_milliseconds) {}

	/**
	 * Tweak specific SSL checks for requests.
	 * @link http://www.php.net/manual/en/oauth.setsslchecks.php
	 * @param sslcheck int <p>
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function setSSLChecks ($sslcheck) {}

	/**
	 * Generate OAuth header string signature
	 * @link http://www.php.net/manual/en/oauth.getrequestheader.php
	 * @param http_method string <p>
	 * HTTP method for request.
	 * </p>
	 * @param url string <p>
	 * URL for request.
	 * </p>
	 * @param extra_parameters mixed[optional] <p>
	 * String or array of additional parameters.
	 * </p>
	 * @return string A string containing the generated request header&return.falseforfailure;
	 */
	public function getRequestHeader ($http_method, $url, $extra_parameters = null) {}

	/**
	 * The destructor
	 * @link http://www.php.net/manual/en/oauth.destruct.php
	 * @return void 
	 */
	public function __destruct () {}

}

class OAuthException extends Exception  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	public $lastResponse;
	public $debugInfo;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 * @param previous[optional]
	 */
	public function __construct ($message, $code, $previous) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class OAuthProvider  {

	/**
	 * Constructs a new OAuthProvider object
	 * @link http://www.php.net/manual/en/oauthprovider.construct.php
	 * @param params_array[optional]
	 */
	public function __construct ($params_array) {}

	/**
	 * Set the consumerHandler handler callback
	 * @link http://www.php.net/manual/en/oauthprovider.consumerhandler.php
	 * @param callback_function callable <p>
	 * The callable functions name.
	 * </p>
	 * @return void 
	 */
	public function consumerHandler ($callback_function) {}

	/**
	 * Set the tokenHandler handler callback
	 * @link http://www.php.net/manual/en/oauthprovider.tokenhandler.php
	 * @param callback_function callable <p>
	 * The callable functions name.
	 * </p>
	 * @return void 
	 */
	public function tokenHandler ($callback_function) {}

	/**
	 * Set the timestampNonceHandler handler callback
	 * @link http://www.php.net/manual/en/oauthprovider.timestampnoncehandler.php
	 * @param callback_function callable <p>
	 * The callable functions name.
	 * </p>
	 * @return void 
	 */
	public function timestampNonceHandler ($callback_function) {}

	/**
	 * Calls the consumerNonceHandler callback
	 * @link http://www.php.net/manual/en/oauthprovider.callconsumerhandler.php
	 * @return void 
	 */
	public function callconsumerHandler () {}

	/**
	 * Calls the tokenNonceHandler callback
	 * @link http://www.php.net/manual/en/oauthprovider.calltokenhandler.php
	 * @return void 
	 */
	public function calltokenHandler () {}

	/**
	 * Calls the timestampNonceHandler callback
	 * @link http://www.php.net/manual/en/oauthprovider.calltimestampnoncehandler.php
	 * @return void 
	 */
	public function callTimestampNonceHandler () {}

	/**
	 * Check an oauth request
	 * @link http://www.php.net/manual/en/oauthprovider.checkoauthrequest.php
	 * @param uri string[optional] <p>
	 * The optional URI, or endpoint.
	 * </p>
	 * @param method string[optional] <p>
	 * The HTTP method. Optionally pass in one of the 
	 * OAUTH_HTTP_METHOD_* OAuth constants.
	 * </p>
	 * @return void 
	 */
	public function checkOAuthRequest ($uri = null, $method = null) {}

	/**
	 * Sets isRequestTokenEndpoint
	 * @link http://www.php.net/manual/en/oauthprovider.isrequesttokenendpoint.php
	 * @param will_issue_request_token bool <p>
	 * Sets whether or not it will issue a request token, thus determining if
	 * OAuthProvider::tokenHandler needs to be called.
	 * </p>
	 * @return void 
	 */
	public function isRequestTokenEndpoint ($will_issue_request_token) {}

	/**
	 * Set request token path
	 * @link http://www.php.net/manual/en/oauthprovider.setrequesttokenpath.php
	 * @param path string <p>
	 * The path.
	 * </p>
	 * @return bool true
	 */
	final public function setRequestTokenPath ($path) {}

	/**
	 * Add required parameters
	 * @link http://www.php.net/manual/en/oauthprovider.addrequiredparameter.php
	 * @param req_params string <p>
	 * The required parameters.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	final public function addRequiredParameter ($req_params) {}

	/**
	 * Report a problem
	 * @link http://www.php.net/manual/en/oauthprovider.reportproblem.php
	 * @param oauthexception string <p>
	 * The OAuthException.
	 * </p>
	 * @param send_headers bool[optional] 
	 * @return string 
	 */
	final public static function reportProblem ($oauthexception, $send_headers = null) {}

	/**
	 * Set a parameter
	 * @link http://www.php.net/manual/en/oauthprovider.setparam.php
	 * @param param_key string <p>
	 * The parameter key.
	 * </p>
	 * @param param_val mixed[optional] <p>
	 * The optional parameter value.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	final public function setParam ($param_key, $param_val = null) {}

	/**
	 * Remove a required parameter
	 * @link http://www.php.net/manual/en/oauthprovider.removerequiredparameter.php
	 * @param req_params string <p>
	 * The required parameter to be removed.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	final public function removeRequiredParameter ($req_params) {}

	/**
	 * Generate a random token
	 * @link http://www.php.net/manual/en/oauthprovider.generatetoken.php
	 * @param size int <p>
	 * The desired token length, in terms of bytes.
	 * </p>
	 * @param strong bool[optional] <p>
	 * Setting to true means /dev/random will be used for
	 * entropy, as otherwise the non-blocking /dev/urandom is used.
	 * This parameter is ignored on Windows.
	 * </p>
	 * @return string The generated token, as a string of bytes.
	 */
	final public static function generateToken ($size, $strong = null) {}

	/**
	 * is2LeggedEndpoint
	 * @link http://www.php.net/manual/en/oauthprovider.is2leggedendpoint.php
	 * @param params_array mixed <p>
	 * </p>
	 * @return void An OAuthProvider object.
	 */
	public function is2LeggedEndpoint ($params_array) {}

}

/**
 * Encode a URI to RFC 3986
 * @link http://www.php.net/manual/en/function.oauth-urlencode.php
 * @param uri string <p>
 * URI to encode.
 * </p>
 * @return string an RFC 3986 encoded string.
 */
function oauth_urlencode ($uri) {}

/**
 * Generate a Signature Base String
 * @link http://www.php.net/manual/en/function.oauth-get-sbs.php
 * @param http_method string <p>
 * The HTTP method.
 * </p>
 * @param uri string <p>
 * URI to encode.
 * </p>
 * @param request_parameters array[optional] <p>
 * Array of request parameters.
 * </p>
 * @return string a Signature Base String.
 */
function oauth_get_sbs ($http_method, $uri, array $request_parameters = null) {}


/**
 * <p>
 * OAuth HMAC-SHA1 signature method.
 * </p>
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_SIG_METHOD_HMACSHA1', "HMAC-SHA1");

/**
 * OAuth HMAC-SHA256 signature method.
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_SIG_METHOD_HMACSHA256', "HMAC-SHA256");

/**
 * OAuth RSA-SHA1 signature method.
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_SIG_METHOD_RSASHA1', "RSA-SHA1");
define ('OAUTH_SIG_METHOD_PLAINTEXT', "PLAINTEXT");

/**
 * <p>
 * This constant represents putting OAuth parameters in the
 * Authorization header.
 * </p>
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_AUTH_TYPE_AUTHORIZATION', 3);

/**
 * <p>
 * This constant represents putting OAuth parameters in the request
 * URI.
 * </p>
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_AUTH_TYPE_URI', 1);

/**
 * <p>
 * This constant represents putting OAuth parameters as part of the
 * HTTP POST body.
 * </p>
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_AUTH_TYPE_FORM', 2);

/**
 * <p>
 * This constant indicates a NoAuth OAuth request.
 * </p>
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_AUTH_TYPE_NONE', 4);

/**
 * <p>
 * Use the GET method for the OAuth request.
 * </p>
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_HTTP_METHOD_GET', "GET");

/**
 * <p>
 * Use the POST method for the OAuth request.
 * </p>
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_HTTP_METHOD_POST', "POST");

/**
 * <p>
 * Use the PUT method for the OAuth request.
 * </p>
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_HTTP_METHOD_PUT', "PUT");

/**
 * <p>
 * Use the HEAD method for the OAuth request.
 * </p>
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_HTTP_METHOD_HEAD', "HEAD");

/**
 * Use the DELETE method for the OAuth request.
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_HTTP_METHOD_DELETE', "DELETE");

/**
 * Used by OAuth::setRequestEngine to set the engine to 
 * PHP streams,
 * as opposed to OAUTH_REQENGINE_CURL for 
 * Curl.
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_REQENGINE_STREAMS', 1);

/**
 * Used by OAuth::setRequestEngine to set the engine to 
 * Curl, as opposed to 
 * OAUTH_REQENGINE_STREAMS for PHP streams.
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_REQENGINE_CURL', 2);
define ('OAUTH_SSLCHECK_NONE', 0);
define ('OAUTH_SSLCHECK_HOST', 1);
define ('OAUTH_SSLCHECK_PEER', 2);
define ('OAUTH_SSLCHECK_BOTH', 3);

/**
 * Life is good.
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_OK', 0);

/**
 * The oauth_nonce value was used in a previous request,
 * therefore it cannot be used now.
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_BAD_NONCE', 4);

/**
 * The oauth_timestamp value was not accepted by the service provider. In
 * this case, the response should also contain the oauth_acceptable_timestamps
 * parameter.
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_BAD_TIMESTAMP', 8);

/**
 * The oauth_consumer_key is temporarily unacceptable to the service provider.
 * For example, the service provider may be throttling the consumer.
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_CONSUMER_KEY_UNKNOWN', 16);

/**
 * The consumer key was refused.
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_CONSUMER_KEY_REFUSED', 32);

/**
 * The oauth_signature is invalid, as it does not match the
 * signature computed by the service provider.
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_INVALID_SIGNATURE', 64);

/**
 * The oauth_token has been consumed. It can no longer be
 * used because it has already been used in the previous request(s).
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_TOKEN_USED', 128);

/**
 * The oauth_token has expired.
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_TOKEN_EXPIRED', 256);

/**
 * The oauth_token has been revoked, and will never be accepted.
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_TOKEN_REVOKED', 512);

/**
 * The oauth_token was not accepted by the service provider.
 * The reason is not known, but it might be because the token was never issued,
 * already consumed, expired, and/or forgotten by the service provider.
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_TOKEN_REJECTED', 1024);

/**
 * The oauth_verifier is incorrect.
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_VERIFIER_INVALID', 2048);

/**
 * A required parameter was not received. In this case, the response should also
 * contain the oauth_parameters_absent parameter.
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_PARAMETER_ABSENT', 4096);

/**
 * The oauth_signature_method was not accepted by service provider.
 * @link http://www.php.net/manual/en/oauth.constants.php
 */
define ('OAUTH_SIGNATURE_METHOD_REJECTED', 8192);

// End of OAuth v.1.2.3
?>
