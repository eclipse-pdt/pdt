<?php

// Start of curl v.7.1.1

/**
 * CURLFile should be used to upload a file with
 * CURLOPT_POSTFIELDS.
 * @link http://www.php.net/manual/en/class.curlfile.php
 */
class CURLFile  {
	public $name;
	public $mime;
	public $postname;


	/**
	 * Create a CURLFile object
	 * @link http://www.php.net/manual/en/curlfile.construct.php
	 * @param string $filename Path to the file which will be uploaded.
	 * @param string $mimetype [optional] Mimetype of the file.
	 * @param string $postname [optional] Name of the file to be used in the upload data.
	 * @return string a CURLFile object.
	 */
	public function __construct (string $filename, string $mimetype = null, string $postname = null) {}

	/**
	 * Get file name
	 * @link http://www.php.net/manual/en/curlfile.getfilename.php
	 * @return string file name.
	 */
	public function getFilename () {}

	/**
	 * Get MIME type
	 * @link http://www.php.net/manual/en/curlfile.getmimetype.php
	 * @return string MIME type.
	 */
	public function getMimeType () {}

	/**
	 * Set MIME type
	 * @link http://www.php.net/manual/en/curlfile.setmimetype.php
	 * @param string $mime MIME type to be used in POST data.
	 * @return void 
	 */
	public function setMimeType (string $mime) {}

	/**
	 * Get file name for POST
	 * @link http://www.php.net/manual/en/curlfile.getpostfilename.php
	 * @return string file name for POST.
	 */
	public function getPostFilename () {}

	/**
	 * Set file name for POST
	 * @link http://www.php.net/manual/en/curlfile.setpostfilename.php
	 * @param string $postname Filename to be used in POST data.
	 * @return void 
	 */
	public function setPostFilename (string $postname) {}

	/**
	 * Unserialization handler
	 * @link http://www.php.net/manual/en/curlfile.wakeup.php
	 * @return void 
	 */
	public function __wakeup () {}

}

/**
 * Initialize a cURL session
 * @link http://www.php.net/manual/en/function.curl-init.php
 * @param string $url [optional] <p>
 * If provided, the CURLOPT_URL option will be set
 * to its value. You can manually set this using the 
 * curl_setopt function.
 * </p>
 * <p>
 * The file protocol is disabled by cURL if
 * open_basedir is set.
 * </p>
 * @return resource a cURL handle on success, false on errors.
 */
function curl_init (string $url = null) {}

/**
 * Copy a cURL handle along with all of its preferences
 * @link http://www.php.net/manual/en/function.curl-copy-handle.php
 * @param resource $ch 
 * @return resource a new cURL handle.
 */
function curl_copy_handle ($ch) {}

/**
 * Gets cURL version information
 * @link http://www.php.net/manual/en/function.curl-version.php
 * @param int $age [optional] 
 * @return array an associative array with the following elements: 
 * <table>
 * <tr valign="top">
 * <td>Indice</td>
 * <td>Value description</td>
 * </tr>
 * <tr valign="top">
 * <td>version_number</td>
 * <td>cURL 24 bit version number</td>
 * </tr>
 * <tr valign="top">
 * <td>version</td>
 * <td>cURL version number, as a string</td>
 * </tr>
 * <tr valign="top">
 * <td>ssl_version_number</td>
 * <td>OpenSSL 24 bit version number</td>
 * </tr>
 * <tr valign="top">
 * <td>ssl_version</td>
 * <td>OpenSSL version number, as a string</td>
 * </tr>
 * <tr valign="top">
 * <td>libz_version</td>
 * <td>zlib version number, as a string</td>
 * </tr>
 * <tr valign="top">
 * <td>host</td>
 * <td>Information about the host where cURL was built</td>
 * </tr>
 * <tr valign="top">
 * <td>age</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>features</td>
 * <td>A bitmask of the CURL_VERSION_XXX constants</td>
 * </tr>
 * <tr valign="top">
 * <td>protocols</td>
 * <td>An array of protocols names supported by cURL</td>
 * </tr>
 * </table>
 */
function curl_version (int $age = null) {}

/**
 * Set an option for a cURL transfer
 * @link http://www.php.net/manual/en/function.curl-setopt.php
 * @param resource $ch 
 * @param int $option The CURLOPT_XXX option to set.
 * @param mixed $value <p>
 * The value to be set on option.
 * </p>
 * <p>
 * value should be a bool for the
 * following values of the option parameter:
 * <table>
 * <tr valign="top">
 * <td>Option</td>
 * <td>Set value to</td>
 * <td>Notes</td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_AUTOREFERER</td>
 * <td>
 * true to automatically set the Referer: field in
 * requests where it follows a Location: redirect.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_BINARYTRANSFER</td>
 * <td>
 * true to return the raw output when
 * CURLOPT_RETURNTRANSFER is used.
 * </td>
 * <td>
 * From PHP 5.1.3, this option has no effect: the raw output will
 * always be returned when
 * CURLOPT_RETURNTRANSFER is used.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_COOKIESESSION</td>
 * <td>
 * true to mark this as a new cookie "session". It will force libcurl
 * to ignore all cookies it is about to load that are "session cookies"
 * from the previous session. By default, libcurl always stores and
 * loads all cookies, independent if they are session cookies or not.
 * Session cookies are cookies without expiry date and they are meant
 * to be alive and existing for this "session" only.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_CERTINFO</td>
 * <td>
 * true to output SSL certification information to STDERR
 * on secure transfers.
 * </td>
 * <td>
 * Added in cURL 7.19.1. 
 * Available since PHP 5.3.2. 
 * Requires CURLOPT_VERBOSE to be on to have an effect.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_CONNECT_ONLY</td>
 * <td>
 * true tells the library to perform all the required proxy authentication 
 * and connection setup, but no data transfer. This option is implemented for 
 * HTTP, SMTP and POP3.
 * </td>
 * <td>
 * Added in 7.15.2.
 * Available since PHP 5.5.0.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_CRLF</td>
 * <td>
 * true to convert Unix newlines to CRLF newlines
 * on transfers.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_DNS_USE_GLOBAL_CACHE</td>
 * <td>
 * true to use a global DNS cache. This option is
 * not thread-safe and is enabled by default.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_FAILONERROR</td>
 * <td>
 * true to fail verbosely if the HTTP code returned
 * is greater than or equal to 400. The default behavior is to return
 * the page normally, ignoring the code.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSL_FALSESTART</td>
 * <td>
 * true to enable TLS false start.
 * </td>
 * <td>
 * Added in cURL 7.42.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_FILETIME</td>
 * <td>
 * true to attempt to retrieve the modification
 * date of the remote document. This value can be retrieved using
 * the CURLINFO_FILETIME option with
 * curl_getinfo.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_FOLLOWLOCATION</td>
 * <td>
 * true to follow any
 * "Location: " header that the server sends as
 * part of the HTTP header (note this is recursive, PHP will follow as
 * many "Location: " headers that it is sent,
 * unless CURLOPT_MAXREDIRS is set).
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_FORBID_REUSE</td>
 * <td>
 * true to force the connection to explicitly
 * close when it has finished processing, and not be pooled for reuse.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_FRESH_CONNECT</td>
 * <td>
 * true to force the use of a new connection
 * instead of a cached one.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_FTP_USE_EPRT</td>
 * <td>
 * true to use EPRT (and LPRT) when doing active
 * FTP downloads. Use false to disable EPRT and LPRT and use PORT
 * only.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_FTP_USE_EPSV</td>
 * <td>
 * true to first try an EPSV command for FTP
 * transfers before reverting back to PASV. Set to false
 * to disable EPSV.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_FTP_CREATE_MISSING_DIRS</td>
 * <td>
 * true to create missing directories when an FTP operation
 * encounters a path that currently doesn't exist.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_FTPAPPEND</td>
 * <td>
 * true to append to the remote file instead of
 * overwriting it.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_TCP_NODELAY</td>
 * <td>
 * true to disable TCP's Nagle algorithm, which tries to minimize
 * the number of small packets on the network.
 * </td>
 * <td>
 * Available since PHP 5.2.1 for versions compiled with libcurl 7.11.2 or
 * greater.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_FTPASCII</td>
 * <td>
 * An alias of
 * CURLOPT_TRANSFERTEXT. Use that instead.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_FTPLISTONLY</td>
 * <td>
 * true to only list the names of an FTP
 * directory.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_HEADER</td>
 * <td>
 * true to include the header in the output.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLINFO_HEADER_OUT</td>
 * <td>
 * true to track the handle's request string.
 * </td>
 * <td>
 * Available since PHP 5.1.3. The CURLINFO_
 * prefix is intentional.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_HTTPGET</td>
 * <td>
 * true to reset the HTTP request method to GET.
 * Since GET is the default, this is only necessary if the request
 * method has been changed.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_HTTPPROXYTUNNEL</td>
 * <td>
 * true to tunnel through a given HTTP proxy.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_MUTE</td>
 * <td>
 * true to be completely silent with regards to
 * the cURL functions.
 * </td>
 * <td>
 * Removed in cURL 7.15.5 (You can use CURLOPT_RETURNTRANSFER instead)
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_NETRC</td>
 * <td>
 * true to scan the ~/.netrc
 * file to find a username and password for the remote site that
 * a connection is being established with.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_NOBODY</td>
 * <td>
 * true to exclude the body from the output.
 * Request method is then set to HEAD. Changing this to false does
 * not change it to GET.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_NOPROGRESS</td>
 * <td><p>
 * true to disable the progress meter for cURL transfers.
 * <p>
 * PHP automatically sets this option to true, this should only be
 * changed for debugging purposes.
 * </p>
 * </p></td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_NOSIGNAL</td>
 * <td>
 * true to ignore any cURL function that causes a
 * signal to be sent to the PHP process. This is turned on by default
 * in multi-threaded SAPIs so timeout options can still be used.
 * </td>
 * <td>
 * Added in cURL 7.10.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_PATH_AS_IS</td>
 * <td>
 * true to not handle dot dot sequences.
 * </td>
 * <td>
 * Added in cURL 7.42.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_PIPEWAIT</td>
 * <td>
 * true to wait for pipelining/multiplexing.
 * </td>
 * <td>
 * Added in cURL 7.43.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_POST</td>
 * <td>
 * true to do a regular HTTP POST. This POST is the
 * normal application/x-www-form-urlencoded kind,
 * most commonly used by HTML forms.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_PUT</td>
 * <td>
 * true to HTTP PUT a file. The file to PUT must
 * be set with CURLOPT_INFILE and
 * CURLOPT_INFILESIZE.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_RETURNTRANSFER</td>
 * <td>
 * true to return the transfer as a string of the
 * return value of curl_exec instead of outputting
 * it out directly.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SAFE_UPLOAD</td>
 * <td>
 * true to disable support for the @ prefix for
 * uploading files in CURLOPT_POSTFIELDS, which
 * means that values starting with @ can be safely
 * passed as fields. CURLFile may be used for
 * uploads instead.
 * </td>
 * <td>
 * Added in PHP 5.5.0 with false as the default value. PHP 5.6.0
 * changes the default value to true. PHP 7 removes this option;
 * the CURLFile interface must be used to upload files.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SASL_IR</td>
 * <td>
 * true to enable sending the initial response in the first packet.
 * </td>
 * <td>
 * Added in cURL 7.31.10. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSL_ENABLE_ALPN</td>
 * <td>
 * false to disable ALPN in the SSL handshake (if the SSL backend
 * libcurl is built to use supports it), which can be used to
 * negotiate http2.
 * </td>
 * <td>
 * Added in cURL 7.36.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSL_ENABLE_NPN</td>
 * <td>
 * false to disable NPN in the SSL handshake (if the SSL backend
 * libcurl is built to use supports it), which can be used to
 * negotiate http2.
 * </td>
 * <td>
 * Added in cURL 7.36.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSL_VERIFYPEER</td>
 * <td>
 * false to stop cURL from verifying the peer's
 * certificate. Alternate certificates to verify against can be
 * specified with the CURLOPT_CAINFO option
 * or a certificate directory can be specified with the
 * CURLOPT_CAPATH option.
 * </td>
 * <td>
 * true by default as of cURL 7.10. Default bundle installed as of
 * cURL 7.10.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSL_VERIFYSTATUS</td>
 * <td>
 * true to verify the certificate's status.
 * </td>
 * <td>
 * Added in cURL 7.41.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_TCP_FASTOPEN</td>
 * <td>
 * true to enable TCP Fast Open.
 * </td>
 * <td>
 * Added in cURL 7.49.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_TFTP_NO_OPTIONS</td>
 * <td>
 * true to not send TFTP options requests.
 * </td>
 * <td>
 * Added in cURL 7.48.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_TRANSFERTEXT</td>
 * <td>
 * true to use ASCII mode for FTP transfers.
 * For LDAP, it retrieves data in plain text instead of HTML. On
 * Windows systems, it will not set STDOUT to binary
 * mode.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_UNRESTRICTED_AUTH</td>
 * <td>
 * true to keep sending the username and password
 * when following locations (using
 * CURLOPT_FOLLOWLOCATION), even when the
 * hostname has changed.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_UPLOAD</td>
 * <td>
 * true to prepare for an upload.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_VERBOSE</td>
 * <td>
 * true to output verbose information. Writes
 * output to STDERR, or the file specified using
 * CURLOPT_STDERR.
 * </td>
 * <td>
 * </td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * value should be an integer for the
 * following values of the option parameter:
 * <table>
 * <tr valign="top">
 * <td>Option</td>
 * <td>Set value to</td>
 * <td>Notes</td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_BUFFERSIZE</td>
 * <td>
 * The size of the buffer to use for each read. There is no guarantee
 * this request will be fulfilled, however.
 * </td>
 * <td>
 * Added in cURL 7.10.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_CLOSEPOLICY</td>
 * <td>
 * One of the CURLCLOSEPOLICY_&#42; values.
 * <p>
 * This option is deprecated, as it was never implemented in cURL and
 * never had any effect.
 * </p>
 * </td>
 * <td>
 * Removed in PHP 5.6.0.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_CONNECTTIMEOUT</td>
 * <td>
 * The number of seconds to wait while trying to connect. Use 0 to
 * wait indefinitely.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_CONNECTTIMEOUT_MS</td>
 * <td>
 * The number of milliseconds to wait while trying to connect. Use 0 to
 * wait indefinitely.
 * If libcurl is built to use the standard system name resolver, that
 * portion of the connect will still use full-second resolution for
 * timeouts with a minimum timeout allowed of one second.
 * </td>
 * <td>
 * Added in cURL 7.16.2. Available since PHP 5.2.3.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_DNS_CACHE_TIMEOUT</td>
 * <td>
 * The number of seconds to keep DNS entries in memory. This
 * option is set to 120 (2 minutes) by default.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_EXPECT_100_TIMEOUT_MS</td>
 * <td>
 * The timeout for Expect: 100-continue responses in milliseconds.
 * Defaults to 1000 milliseconds.
 * </td>
 * <td>
 * Added in cURL 7.36.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_FTPSSLAUTH</td>
 * <td>
 * The FTP authentication method (when is activated):
 * CURLFTPAUTH_SSL (try SSL first),
 * CURLFTPAUTH_TLS (try TLS first), or
 * CURLFTPAUTH_DEFAULT (let cURL decide).
 * </td>
 * <td>
 * Added in cURL 7.12.2.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_HEADEROPT</td>
 * <td>
 * How to deal with headers. One of the following constants:
 * CURLHEADER_UNIFIED: the headers specified in
 * CURLOPT_HTTPHEADER will be used in requests
 * both to servers and proxies. With this option enabled, 
 * CURLOPT_PROXYHEADER will not have any effect.
 * CURLHEADER_SEPARATE: makes
 * CURLOPT_HTTPHEADER headers only get sent to
 * a server and not to a proxy. Proxy headers must be set with
 * CURLOPT_PROXYHEADER to get used. Note that if
 * a non-CONNECT request is sent to a proxy, libcurl will send both
 * server headers and proxy headers. When doing CONNECT, libcurl will
 * send CURLOPT_PROXYHEADER headers only to the
 * proxy and then CURLOPT_HTTPHEADER headers
 * only to the server.
 * Defaults to CURLHEADER_SEPARATE as of cURL
 * 7.42.1, and CURLHEADER_UNIFIED before.
 * </td>
 * <td>
 * Added in cURL 7.37.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_HTTP_VERSION</td>
 * <td>
 * CURL_HTTP_VERSION_NONE (default, lets CURL
 * decide which version to use),
 * CURL_HTTP_VERSION_1_0 (forces HTTP/1.0),
 * or CURL_HTTP_VERSION_1_1 (forces HTTP/1.1).
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_HTTPAUTH</td>
 * <td>
 * <p>
 * The HTTP authentication method(s) to use. The options are:
 * CURLAUTH_BASIC,
 * CURLAUTH_DIGEST,
 * CURLAUTH_GSSNEGOTIATE,
 * CURLAUTH_NTLM,
 * CURLAUTH_ANY, and
 * CURLAUTH_ANYSAFE.
 * </p>
 * <p>
 * The bitwise | (or) operator can be used to combine
 * more than one method. If this is done, cURL will poll the server to see
 * what methods it supports and pick the best one.
 * </p>
 * <p>
 * CURLAUTH_ANY is an alias for
 * CURLAUTH_BASIC | CURLAUTH_DIGEST | CURLAUTH_GSSNEGOTIATE | CURLAUTH_NTLM.
 * </p>
 * <p>
 * CURLAUTH_ANYSAFE is an alias for
 * CURLAUTH_DIGEST | CURLAUTH_GSSNEGOTIATE | CURLAUTH_NTLM.
 * </p>
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_INFILESIZE</td>
 * <td>
 * The expected size, in bytes, of the file when uploading a file to
 * a remote site. Note that using this option will not stop libcurl
 * from sending more data, as exactly what is sent depends on
 * CURLOPT_READFUNCTION.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_LOW_SPEED_LIMIT</td>
 * <td>
 * The transfer speed, in bytes per second, that the transfer should be
 * below during the count of CURLOPT_LOW_SPEED_TIME
 * seconds before PHP considers the transfer too slow and aborts.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_LOW_SPEED_TIME</td>
 * <td>
 * The number of seconds the transfer speed should be below
 * CURLOPT_LOW_SPEED_LIMIT before PHP considers
 * the transfer too slow and aborts.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_MAXCONNECTS</td>
 * <td>
 * The maximum amount of persistent connections that are allowed.
 * When the limit is reached,
 * CURLOPT_CLOSEPOLICY is used to determine
 * which connection to close.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_MAXREDIRS</td>
 * <td>
 * The maximum amount of HTTP redirections to follow. Use this option
 * alongside CURLOPT_FOLLOWLOCATION.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_PORT</td>
 * <td>
 * An alternative port number to connect to.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_POSTREDIR</td>
 * <td>
 * A bitmask of 1 (301 Moved Permanently), 2 (302 Found)
 * and 4 (303 See Other) if the HTTP POST method should be maintained
 * when CURLOPT_FOLLOWLOCATION is set and a
 * specific type of redirect occurs.
 * </td>
 * <td>
 * Added in cURL 7.19.1. Available since PHP 5.3.2.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_PROTOCOLS</td>
 * <td>
 * <p>
 * Bitmask of CURLPROTO_&#42; values. If used, this bitmask 
 * limits what protocols libcurl may use in the transfer. This allows you to have
 * a libcurl built to support a wide range of protocols but still limit specific
 * transfers to only be allowed to use a subset of them. By default libcurl will
 * accept all protocols it supports. 
 * See also CURLOPT_REDIR_PROTOCOLS.
 * </p>
 * <p>
 * Valid protocol options are: 
 * CURLPROTO_HTTP,
 * CURLPROTO_HTTPS,
 * CURLPROTO_FTP,
 * CURLPROTO_FTPS,
 * CURLPROTO_SCP,
 * CURLPROTO_SFTP,
 * CURLPROTO_TELNET,
 * CURLPROTO_LDAP,
 * CURLPROTO_LDAPS,
 * CURLPROTO_DICT,
 * CURLPROTO_FILE,
 * CURLPROTO_TFTP,
 * CURLPROTO_ALL
 * </p>
 * </td>
 * <td>
 * Added in cURL 7.19.4.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_PROXYAUTH</td>
 * <td>
 * The HTTP authentication method(s) to use for the proxy connection.
 * Use the same bitmasks as described in
 * CURLOPT_HTTPAUTH. For proxy authentication,
 * only CURLAUTH_BASIC and
 * CURLAUTH_NTLM are currently supported.
 * </td>
 * <td>
 * Added in cURL 7.10.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_PROXYPORT</td>
 * <td>
 * The port number of the proxy to connect to. This port number can
 * also be set in CURLOPT_PROXY.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_PROXYTYPE</td>
 * <td>
 * Either CURLPROXY_HTTP (default),
 * CURLPROXY_SOCKS4,
 * CURLPROXY_SOCKS5,
 * CURLPROXY_SOCKS4A or
 * CURLPROXY_SOCKS5_HOSTNAME.
 * </td>
 * <td>
 * Added in cURL 7.10.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_REDIR_PROTOCOLS</td>
 * <td>
 * Bitmask of CURLPROTO_&#42; values. If used, this bitmask
 * limits what protocols libcurl may use in a transfer that it follows to in
 * a redirect when CURLOPT_FOLLOWLOCATION is enabled.
 * This allows you to limit specific transfers to only be allowed to use a subset
 * of protocols in redirections. By default libcurl will allow all protocols
 * except for FILE and SCP. This is a difference compared to pre-7.19.4 versions
 * which unconditionally would follow to all protocols supported. 
 * See also CURLOPT_PROTOCOLS for protocol constant values.
 * </td>
 * <td>
 * Added in cURL 7.19.4.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_RESUME_FROM</td>
 * <td>
 * The offset, in bytes, to resume a transfer from.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSL_OPTIONS</td>
 * <td>
 * Set SSL behavior options, which is a bitmask of any of the following constants:
 * CURLSSLOPT_ALLOW_BEAST: do not attempt to use
 * any workarounds for a security flaw in the SSL3 and TLS1.0 protocols.
 * CURLSSLOPT_NO_REVOKE: disable certificate
 * revocation checks for those SSL backends where such behavior is
 * present.
 * </td>
 * <td>
 * Added in cURL 7.25.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSL_VERIFYHOST</td>
 * <td>
 * 1 to check the existence of a common name in the
 * SSL peer certificate. 2 to check the existence of
 * a common name and also verify that it matches the hostname
 * provided. 0 to not check the names. In production environments the value of this option
 * should be kept at 2 (default value).
 * </td>
 * <td>
 * Support for value 1 removed in cURL 7.28.1.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSLVERSION</td>
 * <td>
 * One of CURL_SSLVERSION_DEFAULT (0),
 * CURL_SSLVERSION_TLSv1 (1),
 * CURL_SSLVERSION_SSLv2 (2),
 * CURL_SSLVERSION_SSLv3 (3),
 * CURL_SSLVERSION_TLSv1_0 (4),
 * CURL_SSLVERSION_TLSv1_1 (5) or
 * CURL_SSLVERSION_TLSv1_2 (6).
 * <p>
 * Your best bet is to not set this and let it use the default.
 * Setting it to 2 or 3 is very dangerous given the known
 * vulnerabilities in SSLv2 and SSLv3.
 * </p>
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_STREAM_WEIGHT</td>
 * <td>
 * Set the numerical stream weight (a number between 1 and 256).
 * </td>
 * <td>
 * Added in cURL 7.46.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_TIMECONDITION</td>
 * <td>
 * How CURLOPT_TIMEVALUE is treated.
 * Use CURL_TIMECOND_IFMODSINCE to return the
 * page only if it has been modified since the time specified in
 * CURLOPT_TIMEVALUE. If it hasn't been modified,
 * a "304 Not Modified" header will be returned
 * assuming CURLOPT_HEADER is true.
 * Use CURL_TIMECOND_IFUNMODSINCE for the reverse
 * effect. CURL_TIMECOND_IFMODSINCE is the
 * default.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_TIMEOUT</td>
 * <td>
 * The maximum number of seconds to allow cURL functions to execute.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_TIMEOUT_MS</td>
 * <td>
 * The maximum number of milliseconds to allow cURL functions to
 * execute.
 * If libcurl is built to use the standard system name resolver, that
 * portion of the connect will still use full-second resolution for
 * timeouts with a minimum timeout allowed of one second.
 * </td>
 * <td>
 * Added in cURL 7.16.2. Available since PHP 5.2.3.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_TIMEVALUE</td>
 * <td>
 * The time in seconds since January 1st, 1970. The time will be used
 * by CURLOPT_TIMECONDITION. By default,
 * CURL_TIMECOND_IFMODSINCE is used.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_MAX_RECV_SPEED_LARGE</td>
 * <td>
 * If a download exceeds this speed (counted in bytes per second) on
 * cumulative average during the transfer, the transfer will pause to
 * keep the average rate less than or equal to the parameter value.
 * Defaults to unlimited speed.
 * </td>
 * <td>
 * Added in cURL 7.15.5. Available since PHP 5.4.0.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_MAX_SEND_SPEED_LARGE</td>
 * <td>
 * If an upload exceeds this speed (counted in bytes per second) on
 * cumulative average during the transfer, the transfer will pause to
 * keep the average rate less than or equal to the parameter value.
 * Defaults to unlimited speed.
 * </td>
 * <td>
 * Added in cURL 7.15.5. Available since PHP 5.4.0.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSH_AUTH_TYPES</td>
 * <td>
 * A bitmask consisting of one or more of 
 * CURLSSH_AUTH_PUBLICKEY, 
 * CURLSSH_AUTH_PASSWORD, 
 * CURLSSH_AUTH_HOST, 
 * CURLSSH_AUTH_KEYBOARD. Set to 
 * CURLSSH_AUTH_ANY to let libcurl pick one.
 * </td>
 * <td>
 * Added in cURL 7.16.1. 
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_IPRESOLVE</td>
 * <td>
 * Allows an application to select what kind of IP addresses to use when
 * resolving host names. This is only interesting when using host names that
 * resolve addresses using more than one version of IP, possible values are
 * CURL_IPRESOLVE_WHATEVER, 
 * CURL_IPRESOLVE_V4, 
 * CURL_IPRESOLVE_V6, by default
 * CURL_IPRESOLVE_WHATEVER.
 * </td>
 * <td>
 * Added in cURL 7.10.8.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_FTP_FILEMETHOD</td>
 * <td>
 * Tell curl which method to use to reach a file on a FTP(S) server. Possible values are
 * CURLFTPMETHOD_MULTICWD, 
 * CURLFTPMETHOD_NOCWD and 
 * CURLFTPMETHOD_SINGLECWD.
 * </td>
 * <td>
 * Added in cURL 7.15.1. Available since PHP 5.3.0.
 * </td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * value should be a string for the
 * following values of the option parameter:
 * <table>
 * <tr valign="top">
 * <td>Option</td>
 * <td>Set value to</td>
 * <td>Notes</td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_CAINFO</td>
 * <td>
 * The name of a file holding one or more certificates to verify the
 * peer with. This only makes sense when used in combination with
 * CURLOPT_SSL_VERIFYPEER.
 * </td>
 * <td>
 * Might require an absolute path.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_CAPATH</td>
 * <td>
 * A directory that holds multiple CA certificates. Use this option
 * alongside CURLOPT_SSL_VERIFYPEER.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_COOKIE</td>
 * <td>
 * The contents of the "Cookie: " header to be
 * used in the HTTP request.
 * Note that multiple cookies are separated with a semicolon followed
 * by a space (e.g., "fruit=apple; colour=red")
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_COOKIEFILE</td>
 * <td>
 * The name of the file containing the cookie data. The cookie file can
 * be in Netscape format, or just plain HTTP-style headers dumped into
 * a file.
 * If the name is an empty string, no cookies are loaded, but cookie
 * handling is still enabled.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_COOKIEJAR</td>
 * <td>
 * The name of a file to save all internal cookies to when the handle is closed, 
 * e.g. after a call to curl_close.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_CUSTOMREQUEST</td>
 * <td><p>
 * A custom request method to use instead of
 * "GET" or "HEAD" when doing
 * a HTTP request. This is useful for doing
 * "DELETE" or other, more obscure HTTP requests.
 * Valid values are things like "GET",
 * "POST", "CONNECT" and so on;
 * i.e. Do not enter a whole HTTP request line here. For instance,
 * entering "GET /index.html HTTP/1.0\r\n\r\n"
 * would be incorrect.
 * <p>
 * Don't do this without making sure the server supports the custom
 * request method first.
 * </p>
 * </p></td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_DEFAULT_PROTOCOL</td>
 * <td><p>
 * The default protocol to use if the URL is missing a scheme name.
 * </p></td>
 * <td>
 * Added in cURL 7.45.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_DNS_INTERFACE</td>
 * <td><p>
 * Set the name of the network interface that the DNS resolver should bind to.
 * This must be an interface name (not an address).
 * </p></td>
 * <td>
 * Added in cURL 7.33.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_DNS_LOCAL_IP4</td>
 * <td><p>
 * Set the local IPv4 address that the resolver should bind to. The argument
 * should contain a single numerical IPv4 address as a string.
 * </p></td>
 * <td>
 * Added in cURL 7.33.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_DNS_LOCAL_IP6</td>
 * <td><p>
 * Set the local IPv6 address that the resolver should bind to. The argument
 * should contain a single numerical IPv6 address as a string.
 * </p></td>
 * <td>
 * Added in cURL 7.33.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_EGDSOCKET</td>
 * <td>
 * Like CURLOPT_RANDOM_FILE, except a filename
 * to an Entropy Gathering Daemon socket.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_ENCODING</td>
 * <td>
 * The contents of the "Accept-Encoding: " header.
 * This enables decoding of the response. Supported encodings are
 * "identity", "deflate", and
 * "gzip". If an empty string, "",
 * is set, a header containing all supported encoding types is sent.
 * </td>
 * <td>
 * Added in cURL 7.10.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_FTPPORT</td>
 * <td>
 * The value which will be used to get the IP address to use
 * for the FTP "PORT" instruction. The "PORT" instruction tells
 * the remote server to connect to our specified IP address. The
 * string may be a plain IP address, a hostname, a network
 * interface name (under Unix), or just a plain '-' to use the
 * systems default IP address.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_INTERFACE</td>
 * <td>
 * The name of the outgoing network interface to use. This can be an
 * interface name, an IP address or a host name.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_KEYPASSWD</td>
 * <td>
 * The password required to use the CURLOPT_SSLKEY 
 * or CURLOPT_SSH_PRIVATE_KEYFILE private key. 
 * </td>
 * <td>
 * Added in cURL 7.16.1. 
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_KRB4LEVEL</td>
 * <td>
 * The KRB4 (Kerberos 4) security level. Any of the following values
 * (in order from least to most powerful) are valid:
 * "clear",
 * "safe",
 * "confidential",
 * "private"..
 * If the string does not match one of these,
 * "private" is used. Setting this option to null
 * will disable KRB4 security. Currently KRB4 security only works
 * with FTP transactions.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_LOGIN_OPTIONS</td>
 * <td>
 * Can be used to set protocol specific login options, such as the
 * preferred authentication mechanism via "AUTH=NTLM" or "AUTH=&#42;",
 * and should be used in conjunction with the
 * CURLOPT_USERNAME option.
 * </td>
 * <td>
 * Added in cURL 7.34.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_PINNEDPUBLICKEY</td>
 * <td>
 * Set the pinned public key.
 * The string can be the file name of your pinned public key. The file
 * format expected is "PEM" or "DER". The string can also be any
 * number of base64 encoded sha256 hashes preceded by "sha256//" and
 * separated by ";".
 * </td>
 * <td>
 * Added in cURL 7.39.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_POSTFIELDS</td>
 * <td>
 * The full data to post in a HTTP "POST" operation.
 * To post a file, prepend a filename with @ and
 * use the full path. The filetype can be explicitly specified by
 * following the filename with the type in the format
 * ';type=mimetype'. This parameter can either be
 * passed as a urlencoded string like 'para1=val1&amp;para2=val2&amp;...' 
 * or as an array with the field name as key and field data as value.
 * If value is an array, the
 * Content-Type header will be set to
 * multipart/form-data.
 * As of PHP 5.2.0, value must be an array if
 * files are passed to this option with the @ prefix.
 * As of PHP 5.5.0, the @ prefix is deprecated and
 * files can be sent using CURLFile. The 
 * @ prefix can be disabled for safe passing of
 * values beginning with @ by setting the 
 * CURLOPT_SAFE_UPLOAD option to true.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_PRIVATE</td>
 * <td>
 * Any data that should be associated with this cURL handle. This data
 * can subsequently be retrieved with the
 * CURLINFO_PRIVATE option of
 * curl_getinfo. cURL does nothing with this data.
 * When using a cURL multi handle, this private data is typically a
 * unique key to identify a standard cURL handle.
 * </td>
 * <td>
 * Added in cURL 7.10.3. 
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_PROXY</td>
 * <td>
 * The HTTP proxy to tunnel requests through.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_PROXY_SERVICE_NAME</td>
 * <td>
 * The proxy authentication service name.
 * </td>
 * <td>
 * Added in cURL 7.34.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_PROXYUSERPWD</td>
 * <td>
 * A username and password formatted as
 * "[username]:[password]" to use for the
 * connection to the proxy.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_RANDOM_FILE</td>
 * <td>
 * A filename to be used to seed the random number generator for SSL.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_RANGE</td>
 * <td>
 * Range(s) of data to retrieve in the format
 * "X-Y" where X or Y are optional. HTTP transfers
 * also support several intervals, separated with commas in the format
 * "X-Y,N-M".
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_REFERER</td>
 * <td>
 * The contents of the "Referer: " header to be used
 * in a HTTP request.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SERVICE_NAME</td>
 * <td>
 * The authentication service name.
 * </td>
 * <td>
 * Added in cURL 7.43.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSH_HOST_PUBLIC_KEY_MD5</td>
 * <td>
 * A string containing 32 hexadecimal digits. The string should be the 
 * MD5 checksum of the remote host's public key, and libcurl will reject 
 * the connection to the host unless the md5sums match. 
 * This option is only for SCP and SFTP transfers.
 * </td>
 * <td>
 * Added in cURL 7.17.1. 
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSH_PUBLIC_KEYFILE</td>
 * <td>
 * The file name for your public key. If not used, libcurl defaults to 
 * $HOME/.ssh/id_dsa.pub if the HOME environment variable is set, 
 * and just "id_dsa.pub" in the current directory if HOME is not set.
 * </td>
 * <td>
 * Added in cURL 7.16.1. 
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSH_PRIVATE_KEYFILE</td>
 * <td>
 * The file name for your private key. If not used, libcurl defaults to 
 * $HOME/.ssh/id_dsa if the HOME environment variable is set, 
 * and just "id_dsa" in the current directory if HOME is not set. 
 * If the file is password-protected, set the password with 
 * CURLOPT_KEYPASSWD.
 * </td>
 * <td>
 * Added in cURL 7.16.1. 
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSL_CIPHER_LIST</td>
 * <td>
 * A list of ciphers to use for SSL. For example,
 * RC4-SHA and TLSv1 are valid
 * cipher lists.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSLCERT</td>
 * <td>
 * The name of a file containing a PEM formatted certificate.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSLCERTPASSWD</td>
 * <td>
 * The password required to use the
 * CURLOPT_SSLCERT certificate.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSLCERTTYPE</td>
 * <td>
 * The format of the certificate. Supported formats are
 * "PEM" (default), "DER",
 * and "ENG".
 * </td>
 * <td>
 * Added in cURL 7.9.3.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSLENGINE</td>
 * <td>
 * The identifier for the crypto engine of the private SSL key
 * specified in CURLOPT_SSLKEY.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSLENGINE_DEFAULT</td>
 * <td>
 * The identifier for the crypto engine used for asymmetric crypto
 * operations.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSLKEY</td>
 * <td>
 * The name of a file containing a private SSL key.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSLKEYPASSWD</td>
 * <td><p>
 * The secret password needed to use the private SSL key specified in
 * CURLOPT_SSLKEY.
 * <p>
 * Since this option contains a sensitive password, remember to keep
 * the PHP script it is contained within safe.
 * </p>
 * </p></td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SSLKEYTYPE</td>
 * <td>
 * The key type of the private SSL key specified in
 * CURLOPT_SSLKEY. Supported key types are
 * "PEM" (default), "DER",
 * and "ENG".
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_UNIX_SOCKET_PATH</td>
 * <td>
 * Enables the use of Unix domain sockets as connection endpoint and
 * sets the path to the given string.
 * </td>
 * <td>
 * Added in cURL 7.40.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_URL</td>
 * <td>
 * The URL to fetch. This can also be set when initializing a
 * session with curl_init.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_USERAGENT</td>
 * <td>
 * The contents of the "User-Agent: " header to be
 * used in a HTTP request.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_USERNAME</td>
 * <td>
 * The user name to use in authentication.
 * </td>
 * <td>
 * Added in cURL 7.19.1. Available since PHP 5.5.0.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_USERPWD</td>
 * <td>
 * A username and password formatted as
 * "[username]:[password]" to use for the
 * connection.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_XOAUTH2_BEARER</td>
 * <td>
 * Specifies the OAuth 2.0 access token.
 * </td>
 * <td>
 * Added in cURL 7.33.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * value should be an array for the
 * following values of the option parameter:
 * <table>
 * <tr valign="top">
 * <td>Option</td>
 * <td>Set value to</td>
 * <td>Notes</td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_CONNECT_TO</td>
 * <td>
 * Connect to a specific host and port instead of the URL's host and port.
 * Accepts an array of strings with the format
 * HOST:PORT:CONNECT-TO-HOST:CONNECT-TO-PORT.
 * </td>
 * <td>
 * Added in cURL 7.49.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_HTTP200ALIASES</td>
 * <td>
 * An array of HTTP 200 responses that will be treated as valid
 * responses and not as errors.
 * </td>
 * <td>
 * Added in cURL 7.10.3.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_HTTPHEADER</td>
 * <td>
 * An array of HTTP header fields to set, in the format
 * array('Content-type: text/plain', 'Content-length: 100')
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_POSTQUOTE</td>
 * <td>
 * An array of FTP commands to execute on the server after the FTP
 * request has been performed.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_PROXYHEADER</td>
 * <td>
 * An array of custom HTTP headers to pass to proxies.
 * </td>
 * <td>
 * Added in cURL 7.37.0. Available since PHP 7.0.7.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_QUOTE</td>
 * <td>
 * An array of FTP commands to execute on the server prior to the FTP
 * request.
 * </td>
 * <td>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_RESOLVE</td>
 * <td>
 * Provide a custom address for a specific host and port pair. An array
 * of hostname, port, and IP address strings, each element separated by
 * a colon. In the format:
 * array("example.com:80:127.0.0.1")
 * </td>
 * <td>
 * Added in cURL 7.21.3. Available since PHP 5.5.0.
 * </td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * value should be a stream resource (using
 * fopen, for example) for the following values of the
 * option parameter:
 * <table>
 * <tr valign="top">
 * <td>Option</td>
 * <td>Set value to</td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_FILE</td>
 * <td>
 * The file that the transfer should be written to. The default
 * is STDOUT (the browser window).
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_INFILE</td>
 * <td>
 * The file that the transfer should be read from when uploading.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_STDERR</td>
 * <td>
 * An alternative location to output errors to instead of
 * STDERR.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_WRITEHEADER</td>
 * <td>
 * The file that the header part of the transfer is written to.
 * </td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * value should be the name of a valid function or a Closure 
 * for the following values of the option parameter:
 * <table>
 * <tr valign="top">
 * <td>Option</td>
 * <td>Set value to</td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_HEADERFUNCTION</td>
 * <td>
 * A callback accepting two parameters.
 * The first is the cURL resource, the second is a
 * string with the header data to be written. The header data must
 * be written by this callback. Return the number of 
 * bytes written.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_PASSWDFUNCTION</td>
 * <td>
 * A callback accepting three parameters. 
 * The first is the cURL resource, the second is a
 * string containing a password prompt, and the third is the maximum
 * password length. Return the string containing the password.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_PROGRESSFUNCTION</td>
 * <td>
 * <p>
 * A callback accepting five parameters.
 * The first is the cURL resource, the second is the total number of
 * bytes expected to be downloaded in this transfer, the third is
 * the number of bytes downloaded so far, the fourth is the total
 * number of bytes expected to be uploaded in this transfer, and the
 * fifth is the number of bytes uploaded so far.
 * </p>
 * <p>
 * The callback is only called when the CURLOPT_NOPROGRESS
 * option is set to false.
 * </p>
 * <p>
 * Return a non-zero value to abort the transfer. In which case, the
 * transfer will set a CURLE_ABORTED_BY_CALLBACK
 * error.
 * </p>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_READFUNCTION</td>
 * <td>
 * A callback accepting three parameters. 
 * The first is the cURL resource, the second is a
 * stream resource provided to cURL through the option
 * CURLOPT_INFILE, and the third is the maximum
 * amount of data to be read. The callback must return a string
 * with a length equal or smaller than the amount of data requested,
 * typically by reading it from the passed stream resource. It should
 * return an empty string to signal EOF.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_WRITEFUNCTION</td>
 * <td>
 * A callback accepting two parameters. 
 * The first is the cURL resource, and the second is a
 * string with the data to be written. The data must be saved by
 * this callback. It must return the exact number of bytes written 
 * or the transfer will be aborted with an error.
 * </td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * Other values:
 * <table>
 * <tr valign="top">
 * <td>Option</td>
 * <td>Set value to</td>
 * </tr>
 * <tr valign="top">
 * <td>CURLOPT_SHARE</td>
 * <td>
 * A result of curl_share_init. Makes the cURL
 * handle to use the data from the shared handle.
 * </td>
 * </tr>
 * </table>
 * </p>
 * @return bool true on success or false on failure
 */
function curl_setopt ($ch, int $option, $value) {}

/**
 * Set multiple options for a cURL transfer
 * @link http://www.php.net/manual/en/function.curl-setopt-array.php
 * @param resource $ch 
 * @param array $options An array specifying which options to set and their values.
 * The keys should be valid curl_setopt constants or
 * their integer equivalents.
 * @return bool true if all options were successfully set. If an option could
 * not be successfully set, false is immediately returned, ignoring any
 * future options in the options array.
 */
function curl_setopt_array ($ch, array $options) {}

/**
 * Perform a cURL session
 * @link http://www.php.net/manual/en/function.curl-exec.php
 * @param resource $ch 
 * @return mixed true on success or false on failure However, if the CURLOPT_RETURNTRANSFER
 * option is set, it will return
 * the result on success, false on failure.
 */
function curl_exec ($ch) {}

/**
 * Get information regarding a specific transfer
 * @link http://www.php.net/manual/en/function.curl-getinfo.php
 * @param resource $ch 
 * @param int $opt [optional] <p>
 * This may be one of the following constants:
 * <p>
 * <br>
 * CURLINFO_EFFECTIVE_URL - Last effective URL
 * <br>
 * CURLINFO_HTTP_CODE - Last received HTTP code
 * <br>
 * CURLINFO_FILETIME - Remote time of the retrieved document, with the CURLOPT_FILETIME enabled; if -1 is returned the time of the document is unknown
 * <br>
 * CURLINFO_TOTAL_TIME - Total transaction time in seconds for last transfer
 * <br>
 * CURLINFO_NAMELOOKUP_TIME - Time in seconds until name resolving was complete
 * <br>
 * CURLINFO_CONNECT_TIME - Time in seconds it took to establish the connection
 * <br>
 * CURLINFO_PRETRANSFER_TIME - Time in seconds from start until just before file transfer begins
 * <br>
 * CURLINFO_STARTTRANSFER_TIME - Time in seconds until the first byte is about to be transferred
 * <br>
 * CURLINFO_REDIRECT_COUNT - Number of redirects, with the CURLOPT_FOLLOWLOCATION option enabled
 * <br>
 * CURLINFO_REDIRECT_TIME - Time in seconds of all redirection steps before final transaction was started, with the CURLOPT_FOLLOWLOCATION option enabled
 * <br>
 * CURLINFO_REDIRECT_URL - With the CURLOPT_FOLLOWLOCATION option disabled: redirect URL found in the last transaction, that should be requested manually next. With the CURLOPT_FOLLOWLOCATION option enabled: this is empty. The redirect URL in this case is available in CURLINFO_EFFECTIVE_URL 
 * <br>
 * CURLINFO_PRIMARY_IP - IP address of the most recent connection
 * <br>
 * CURLINFO_PRIMARY_PORT - Destination port of the most recent connection
 * <br>
 * CURLINFO_LOCAL_IP - Local (source) IP address of the most recent connection
 * <br>
 * CURLINFO_LOCAL_PORT - Local (source) port of the most recent connection
 * <br>
 * CURLINFO_SIZE_UPLOAD - Total number of bytes uploaded
 * <br>
 * CURLINFO_SIZE_DOWNLOAD - Total number of bytes downloaded
 * <br>
 * CURLINFO_SPEED_DOWNLOAD - Average download speed
 * <br>
 * CURLINFO_SPEED_UPLOAD - Average upload speed
 * <br>
 * CURLINFO_HEADER_SIZE - Total size of all headers received
 * <br>
 * CURLINFO_HEADER_OUT - The request string sent. For this to 
 * work, add the CURLINFO_HEADER_OUT option to the handle by calling 
 * curl_setopt
 * <br>
 * CURLINFO_REQUEST_SIZE - Total size of issued requests, currently only for HTTP requests
 * <br>
 * CURLINFO_SSL_VERIFYRESULT - Result of SSL certification verification requested by setting CURLOPT_SSL_VERIFYPEER
 * <br>
 * CURLINFO_CONTENT_LENGTH_DOWNLOAD - Content length of download, read from Content-Length: field
 * <br>
 * CURLINFO_CONTENT_LENGTH_UPLOAD - Specified size of upload
 * <br>
 * CURLINFO_CONTENT_TYPE - Content-Type: of the requested document. NULL indicates server did not send valid Content-Type: header
 * <br>
 * CURLINFO_PRIVATE - Private data associated with this cURL handle, previously set with the CURLOPT_PRIVATE option of curl_setopt
 * <br>
 * CURLINFO_RESPONSE_CODE - The last response code
 * <br>
 * CURLINFO_HTTP_CONNECTCODE - The CONNECT response code
 * <br>
 * CURLINFO_HTTPAUTH_AVAIL - Bitmask indicating the authentication method(s) available according to the previous response
 * <br>
 * CURLINFO_PROXYAUTH_AVAIL - Bitmask indicating the proxy authentication method(s) available according to the previous response
 * <br>
 * CURLINFO_OS_ERRNO - Errno from a connect failure. The number is OS and system specific.
 * <br>
 * CURLINFO_NUM_CONNECTS - Number of connections curl had to create to achieve the previous transfer
 * <br>
 * CURLINFO_SSL_ENGINES - OpenSSL crypto-engines supported
 * <br>
 * CURLINFO_COOKIELIST - All known cookies
 * <br>
 * CURLINFO_FTP_ENTRY_PATH - Entry path in FTP server
 * <br>
 * CURLINFO_APPCONNECT_TIME - Time in seconds it took from the start until the SSL/SSH connect/handshake to the remote host was completed
 * <br>
 * CURLINFO_CERTINFO - TLS certificate chain
 * <br>
 * CURLINFO_CONDITION_UNMET - Info on unmet time conditional
 * <br>
 * CURLINFO_RTSP_CLIENT_CSEQ - Next RTSP client CSeq
 * <br>
 * CURLINFO_RTSP_CSEQ_RECV - Recently received CSeq
 * <br>
 * CURLINFO_RTSP_SERVER_CSEQ - Next RTSP server CSeq
 * <br>
 * CURLINFO_RTSP_SESSION_ID - RTSP session ID
 * </p>
 * </p>
 * @return mixed If opt is given, returns its value.
 * Otherwise, returns an associative array with the following elements 
 * (which correspond to opt), or false on failure:
 * <p>
 * <br>
 * "url"
 * <br>
 * "content_type"
 * <br>
 * "http_code"
 * <br>
 * "header_size"
 * <br>
 * "request_size"
 * <br>
 * "filetime"
 * <br>
 * "ssl_verify_result"
 * <br>
 * "redirect_count"
 * <br>
 * "total_time"
 * <br>
 * "namelookup_time"
 * <br>
 * "connect_time"
 * <br>
 * "pretransfer_time"
 * <br>
 * "size_upload"
 * <br>
 * "size_download"
 * <br>
 * "speed_download"
 * <br>
 * "speed_upload"
 * <br>
 * "download_content_length"
 * <br>
 * "upload_content_length"
 * <br>
 * "starttransfer_time"
 * <br>
 * "redirect_time"
 * <br>
 * "certinfo"
 * <br>
 * "primary_ip"
 * <br>
 * "primary_port"
 * <br>
 * "local_ip"
 * <br>
 * "local_port"
 * <br>
 * "redirect_url"
 * <br>
 * "request_header" (This is only set if the CURLINFO_HEADER_OUT 
 * is set by a previous call to curl_setopt)
 * </p>
 * Note that private data is not included in the associative array and must be retrieved individually with the CURLINFO_PRIVATE option.
 */
function curl_getinfo ($ch, int $opt = null) {}

/**
 * Return a string containing the last error for the current session
 * @link http://www.php.net/manual/en/function.curl-error.php
 * @param resource $ch 
 * @return string the error message or '' (the empty string) if no
 * error occurred.
 */
function curl_error ($ch) {}

/**
 * Return the last error number
 * @link http://www.php.net/manual/en/function.curl-errno.php
 * @param resource $ch 
 * @return int the error number or 0 (zero) if no error
 * occurred.
 */
function curl_errno ($ch) {}

/**
 * Close a cURL session
 * @link http://www.php.net/manual/en/function.curl-close.php
 * @param resource $ch 
 * @return void 
 */
function curl_close ($ch) {}

/**
 * Return string describing the given error code
 * @link http://www.php.net/manual/en/function.curl-strerror.php
 * @param int $errornum One of the cURL error codes constants.
 * @return string error description or null for invalid error code.
 */
function curl_strerror (int $errornum) {}

/**
 * Return string describing error code
 * @link http://www.php.net/manual/en/function.curl-multi-strerror.php
 * @param int $errornum One of the CURLM error codes constants.
 * @return string error string for valid error code, null otherwise.
 */
function curl_multi_strerror (int $errornum) {}

/**
 * Return string describing the given error code
 * @link http://www.php.net/manual/en/function.curl-share-strerror.php
 * @param int $errornum One of the cURL error codes constants.
 * @return string error description or null for invalid error code.
 */
function curl_share_strerror (int $errornum) {}

/**
 * Reset all options of a libcurl session handle
 * @link http://www.php.net/manual/en/function.curl-reset.php
 * @param resource $ch 
 * @return void 
 */
function curl_reset ($ch) {}

/**
 * URL encodes the given string
 * @link http://www.php.net/manual/en/function.curl-escape.php
 * @param resource $ch 
 * @param string $str The string to be encoded.
 * @return string escaped string or false on failure.
 */
function curl_escape ($ch, string $str) {}

/**
 * Decodes the given URL encoded string
 * @link http://www.php.net/manual/en/function.curl-unescape.php
 * @param resource $ch 
 * @param string $str The URL encoded string to be decoded.
 * @return string decoded string or false on failure.
 */
function curl_unescape ($ch, string $str) {}

/**
 * Pause and unpause a connection
 * @link http://www.php.net/manual/en/function.curl-pause.php
 * @param resource $ch 
 * @param int $bitmask One of CURLPAUSE_&#42; constants.
 * @return int an error code (CURLE_OK for no error).
 */
function curl_pause ($ch, int $bitmask) {}

/**
 * Returns a new cURL multi handle
 * @link http://www.php.net/manual/en/function.curl-multi-init.php
 * @return resource a cURL multi handle resource on success, false on failure.
 */
function curl_multi_init () {}

/**
 * Add a normal cURL handle to a cURL multi handle
 * @link http://www.php.net/manual/en/function.curl-multi-add-handle.php
 * @param resource $mh 
 * @param resource $ch 
 * @return int 0 on success, or one of the CURLM_XXX errors
 * code.
 */
function curl_multi_add_handle ($mh, $ch) {}

/**
 * Remove a multi handle from a set of cURL handles
 * @link http://www.php.net/manual/en/function.curl-multi-remove-handle.php
 * @param resource $mh 
 * @param resource $ch 
 * @return int 0 on success, or one of the CURLM_XXX error
 * codes.
 */
function curl_multi_remove_handle ($mh, $ch) {}

/**
 * Wait for activity on any curl_multi connection
 * @link http://www.php.net/manual/en/function.curl-multi-select.php
 * @param resource $mh 
 * @param float $timeout [optional] Time, in seconds, to wait for a response.
 * @return int On success, returns the number of descriptors contained in 
 * the descriptor sets. This may be 0 if there was no activity on any
 * of the descriptors. On failure, this function will return -1 on a select
 * failure (from the underlying select system call).
 */
function curl_multi_select ($mh, float $timeout = null) {}

/**
 * Run the sub-connections of the current cURL handle
 * @link http://www.php.net/manual/en/function.curl-multi-exec.php
 * @param resource $mh 
 * @param int $still_running A reference to a flag to tell whether the operations are still running.
 * @return int A cURL code defined in the cURL Predefined Constants.
 * <p>
 * This only returns errors regarding the whole multi stack. There might still have 
 * occurred problems on individual transfers even when this function returns 
 * CURLM_OK.
 * </p>
 */
function curl_multi_exec ($mh, int &$still_running) {}

/**
 * Return the content of a cURL handle if CURLOPT_RETURNTRANSFER is set
 * @link http://www.php.net/manual/en/function.curl-multi-getcontent.php
 * @param resource $ch 
 * @return string Return the content of a cURL handle if CURLOPT_RETURNTRANSFER is set.
 */
function curl_multi_getcontent ($ch) {}

/**
 * Get information about the current transfers
 * @link http://www.php.net/manual/en/function.curl-multi-info-read.php
 * @param resource $mh 
 * @param int $msgs_in_queue [optional] Number of messages that are still in the queue
 * @return array On success, returns an associative array for the message, false on failure.
 * <p>
 * <table>
 * Contents of the returned array
 * <table>
 * <tr valign="top">
 * <td>Key:</td>
 * <td>Value:</td>
 * </tr>
 * <tr valign="top">
 * <td>msg</td>
 * <td>The CURLMSG_DONE constant. Other return values
 * are currently not available.</td>
 * </tr>
 * <tr valign="top">
 * <td>result</td>
 * <td>One of the CURLE_&#42; constants. If everything is
 * OK, the CURLE_OK will be the result.</td>
 * </tr>
 * <tr valign="top">
 * <td>handle</td>
 * <td>Resource of type curl indicates the handle which it concerns.</td>
 * </tr>
 * </table>
 * </table>
 * </p>
 */
function curl_multi_info_read ($mh, int &$msgs_in_queue = null) {}

/**
 * Close a set of cURL handles
 * @link http://www.php.net/manual/en/function.curl-multi-close.php
 * @param resource $mh 
 * @return void 
 */
function curl_multi_close ($mh) {}

/**
 * Return the last multi curl error number
 * @link http://www.php.net/manual/en/function.curl-multi-errno.php
 * @param resource $mh 
 * @return int Return an integer containing the last multi curl error number,
 * or false on failure.
 */
function curl_multi_errno ($mh) {}

/**
 * Set an option for the cURL multi handle
 * @link http://www.php.net/manual/en/function.curl-multi-setopt.php
 * @param resource $mh 
 * @param int $option One of the CURLMOPT_&#42; constants.
 * @param mixed $value <p>
 * The value to be set on option.
 * </p>
 * <p>
 * value should be an int for the
 * following values of the option parameter:
 * <table>
 * <tr valign="top">
 * <td>Option</td>
 * <td>Set value to</td>
 * </tr>
 * <tr valign="top">
 * <td>CURLMOPT_PIPELINING</td>
 * <td>
 * Pass 1 to enable or 0 to disable. Enabling pipelining on a multi
 * handle will make it attempt to perform HTTP Pipelining as far as
 * possible for transfers using this handle. This means that if you add
 * a second request that can use an already existing connection, the
 * second request will be "piped" on the same connection.
 * As of cURL 7.43.0 you can also pass 2 to try to multiplex the new
 * transfer over an existing HTTP/2 connection if possible.
 * Instead of integer literals, you can also use the CURLPIPE_&#42;
 * constants if available.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLMOPT_MAXCONNECTS</td>
 * <td>
 * Pass a number that will be used as the maximum amount of
 * simultaneously open connections that libcurl may cache.
 * By default the size will be enlarged to fit four times the number
 * of handles added via curl_multi_add_handle.
 * When the cache is full, curl closes the oldest one in the cache
 * to prevent the number of open connections from increasing.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLMOPT_CHUNK_LENGTH_PENALTY_SIZE</td>
 * <td>
 * Pass a number that specifies the chunk length threshold for pipelining
 * in bytes.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLMOPT_CONTENT_LENGTH_PENALTY_SIZE</td>
 * <td>
 * Pass a number that specifies the size threshold for pipelining
 * penalty in bytes.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLMOPT_MAX_HOST_CONNECTIONS</td>
 * <td>
 * Pass a number that specifies the maximum number of connections to a
 * single host.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLMOPT_MAX_PIPELINE_LENGTH</td>
 * <td>
 * Pass a number that specifies the maximum number of requests in a
 * pipeline.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLMOPT_MAX_TOTAL_CONNECTIONS</td>
 * <td>
 * Pass a number that specifies the maximum number of simultaneously
 * open connections.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLMOPT_PUSHFUNCTION</td>
 * <td>
 * Pass a callable that will be registered to handle server
 * pushes and should have the following signature:
 * intpushfunction
 * resourceparent_ch
 * resourcepushed_ch
 * arrayheaders
 * <p>
 * parent_ch
 * <br>
 * <p>
 * The parent cURL handle (the request the client made).
 * </p>
 * pushed_ch
 * <br>
 * <p>
 * A new cURL handle for the pushed request.
 * </p>
 * headers
 * <br>
 * <p>
 * The push promise headers.
 * </p>
 * </p>
 * The push function is supposed to return either
 * CURL_PUSH_OK if it can handle the push, or
 * CURL_PUSH_DENY to reject it.
 * </td>
 * </tr>
 * </table>
 * </p>
 * @return bool true on success or false on failure
 */
function curl_multi_setopt ($mh, int $option, $value) {}

/**
 * Initialize a cURL share handle
 * @link http://www.php.net/manual/en/function.curl-share-init.php
 * @return resource resource of type "cURL Share Handle".
 */
function curl_share_init () {}

/**
 * Close a cURL share handle
 * @link http://www.php.net/manual/en/function.curl-share-close.php
 * @param resource $sh A cURL share handle returned by curl_share_init
 * @return void 
 */
function curl_share_close ($sh) {}

/**
 * Set an option for a cURL share handle.
 * @link http://www.php.net/manual/en/function.curl-share-setopt.php
 * @param resource $sh A cURL share handle returned by curl_share_init.
 * @param int $option <table>
 * <tr valign="top">
 * <td>Option</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>CURLSHOPT_SHARE</td>
 * <td>
 * Specifies a type of data that should be shared.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURLSHOPT_UNSHARE</td>
 * <td>
 * Specifies a type of data that will be no longer shared.
 * </td>
 * </tr>
 * </table>
 * @param string $value <table>
 * <tr valign="top">
 * <td>Value</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>CURL_LOCK_DATA_COOKIE</td>
 * <td>
 * Shares cookie data.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURL_LOCK_DATA_DNS</td>
 * <td>
 * Shares DNS cache. Note that when you use cURL multi handles,
 * all handles added to the same multi handle will share DNS cache
 * by default.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>CURL_LOCK_DATA_SSL_SESSION</td>
 * <td>
 * Shares SSL session IDs, reducing the time spent on the SSL
 * handshake when reconnecting to the same server. Note that SSL
 * session IDs are reused within the same handle by default.
 * </td>
 * </tr>
 * </table>
 * @return bool true on success or false on failure
 */
function curl_share_setopt ($sh, int $option, string $value) {}

/**
 * Return the last share curl error number
 * @link http://www.php.net/manual/en/function.curl-share-errno.php
 * @param resource $sh A cURL share handle returned by curl_share_init.
 * @return int an integer containing the last share curl error number,
 * or false on failure.
 */
function curl_share_errno ($sh) {}

/**
 * Create a CURLFile object
 * @link http://www.php.net/manual/en/function.curl-file-create.php
 * @param $filename
 * @param $mimetype [optional]
 * @param $postname [optional]
 */
function curl_file_create ($filename, $mimetype = null, $postname = null) {}


/**
 * Available since PHP 5.1.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_AUTOREFERER', 58);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_BINARYTRANSFER', 19914);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_BUFFERSIZE', 98);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_CAINFO', 10065);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_CAPATH', 10097);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_CONNECTTIMEOUT', 78);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_COOKIE', 10022);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_COOKIEFILE', 10031);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_COOKIEJAR', 10082);

/**
 * Available since PHP 5.1.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_COOKIESESSION', 96);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_CRLF', 27);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_CUSTOMREQUEST', 10036);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_DNS_CACHE_TIMEOUT', 92);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_DNS_USE_GLOBAL_CACHE', 91);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_EGDSOCKET', 10077);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_ENCODING', 10102);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_FAILONERROR', 45);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_FILE', 10001);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_FILETIME', 69);

/**
 * This constant is not available when open_basedir 
 * or safe_mode are enabled.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_FOLLOWLOCATION', 52);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_FORBID_REUSE', 75);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_FRESH_CONNECT', 74);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_FTPAPPEND', 50);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_FTPLISTONLY', 48);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_FTPPORT', 10017);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_FTP_USE_EPRT', 106);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_FTP_USE_EPSV', 85);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_HEADER', 42);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_HEADERFUNCTION', 20079);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_HTTP200ALIASES', 10104);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_HTTPGET', 80);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_HTTPHEADER', 10023);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_HTTPPROXYTUNNEL', 61);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_HTTP_VERSION', 84);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_INFILE', 10009);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_INFILESIZE', 14);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_INTERFACE', 10062);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_KRB4LEVEL', 10063);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_LOW_SPEED_LIMIT', 19);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_LOW_SPEED_TIME', 20);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_MAXCONNECTS', 71);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_MAXREDIRS', 68);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_NETRC', 51);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_NOBODY', 44);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_NOPROGRESS', 43);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_NOSIGNAL', 99);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_PORT', 3);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_POST', 47);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_POSTFIELDS', 10015);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_POSTQUOTE', 10039);
define ('CURLOPT_PREQUOTE', 10093);

/**
 * Available since PHP 5.2.4
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_PRIVATE', 10103);

/**
 * Available since PHP 5.3.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_PROGRESSFUNCTION', 20056);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_PROXY', 10004);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_PROXYPORT', 59);

/**
 * Available as of cURL 7.10.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_PROXYTYPE', 101);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_PROXYUSERPWD', 10006);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_PUT', 54);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_QUOTE', 10028);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_RANDOM_FILE', 10076);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_RANGE', 10007);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_READDATA', 10009);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_READFUNCTION', 20012);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_REFERER', 10016);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_RESUME_FROM', 21);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_RETURNTRANSFER', 19913);
define ('CURLOPT_SHARE', 10100);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSLCERT', 10025);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSLCERTPASSWD', 10026);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSLCERTTYPE', 10086);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSLENGINE', 10089);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSLENGINE_DEFAULT', 90);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSLKEY', 10087);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSLKEYPASSWD', 10026);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSLKEYTYPE', 10088);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSLVERSION', 32);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSL_CIPHER_LIST', 10083);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSL_VERIFYHOST', 81);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSL_VERIFYPEER', 64);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_STDERR', 10037);
define ('CURLOPT_TELNETOPTIONS', 10070);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_TIMECONDITION', 33);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_TIMEOUT', 13);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_TIMEVALUE', 34);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_TRANSFERTEXT', 53);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_UNRESTRICTED_AUTH', 105);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_UPLOAD', 46);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_URL', 10002);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_USERAGENT', 10018);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_USERPWD', 10005);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_VERBOSE', 41);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_WRITEFUNCTION', 20011);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_WRITEHEADER', 10029);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_ABORTED_BY_CALLBACK', 42);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_BAD_CALLING_ORDER', 44);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_BAD_CONTENT_ENCODING', 61);
define ('CURLE_BAD_DOWNLOAD_RESUME', 36);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_BAD_FUNCTION_ARGUMENT', 43);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_BAD_PASSWORD_ENTERED', 46);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_COULDNT_CONNECT', 7);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_COULDNT_RESOLVE_HOST', 6);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_COULDNT_RESOLVE_PROXY', 5);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FAILED_INIT', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FILE_COULDNT_READ_FILE', 37);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_ACCESS_DENIED', 9);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_BAD_DOWNLOAD_RESUME', 36);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_CANT_GET_HOST', 15);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_CANT_RECONNECT', 16);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_COULDNT_GET_SIZE', 32);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_COULDNT_RETR_FILE', 19);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_COULDNT_SET_ASCII', 29);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_COULDNT_SET_BINARY', 17);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_COULDNT_STOR_FILE', 25);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_COULDNT_USE_REST', 31);
define ('CURLE_FTP_PARTIAL_FILE', 18);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_PORT_FAILED', 30);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_QUOTE_ERROR', 21);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_USER_PASSWORD_INCORRECT', 10);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_WEIRD_227_FORMAT', 14);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_WEIRD_PASS_REPLY', 11);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_WEIRD_PASV_REPLY', 13);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_WEIRD_SERVER_REPLY', 8);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_WEIRD_USER_REPLY', 12);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_WRITE_ERROR', 20);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FUNCTION_NOT_FOUND', 41);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_GOT_NOTHING', 52);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_HTTP_NOT_FOUND', 22);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_HTTP_PORT_FAILED', 45);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_HTTP_POST_ERROR', 34);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_HTTP_RANGE_ERROR', 33);
define ('CURLE_HTTP_RETURNED_ERROR', 22);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_LDAP_CANNOT_BIND', 38);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_LDAP_SEARCH_FAILED', 39);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_LIBRARY_NOT_FOUND', 40);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_MALFORMAT_USER', 24);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_OBSOLETE', 50);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_OK', 0);
define ('CURLE_OPERATION_TIMEDOUT', 28);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_OPERATION_TIMEOUTED', 28);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_OUT_OF_MEMORY', 27);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_PARTIAL_FILE', 18);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_READ_ERROR', 26);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_RECV_ERROR', 56);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_SEND_ERROR', 55);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_SHARE_IN_USE', 57);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_SSL_CACERT', 60);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_SSL_CERTPROBLEM', 58);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_SSL_CIPHER', 59);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_SSL_CONNECT_ERROR', 35);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_SSL_ENGINE_NOTFOUND', 53);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_SSL_ENGINE_SETFAILED', 54);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_SSL_PEER_CERTIFICATE', 51);
define ('CURLE_SSL_PINNEDPUBKEYNOTMATCH', 90);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_TELNET_OPTION_SYNTAX', 49);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_TOO_MANY_REDIRECTS', 47);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_UNKNOWN_TELNET_OPTION', 48);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_UNSUPPORTED_PROTOCOL', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_URL_MALFORMAT', 3);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_URL_MALFORMAT_USER', 4);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_WRITE_ERROR', 23);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_CONNECT_TIME', 3145733);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_CONTENT_LENGTH_DOWNLOAD', 3145743);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_CONTENT_LENGTH_UPLOAD', 3145744);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_CONTENT_TYPE', 1048594);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_EFFECTIVE_URL', 1048577);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_FILETIME', 2097166);

/**
 * Available since PHP 5.1.3
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_HEADER_OUT', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_HEADER_SIZE', 2097163);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_HTTP_CODE', 2097154);
define ('CURLINFO_LASTONE', 44);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_NAMELOOKUP_TIME', 3145732);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_PRETRANSFER_TIME', 3145734);

/**
 * Available since PHP 5.2.4
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_PRIVATE', 1048597);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_REDIRECT_COUNT', 2097172);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_REDIRECT_TIME', 3145747);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_REQUEST_SIZE', 2097164);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_SIZE_DOWNLOAD', 3145736);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_SIZE_UPLOAD', 3145735);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_SPEED_DOWNLOAD', 3145737);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_SPEED_UPLOAD', 3145738);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_SSL_VERIFYRESULT', 2097165);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_STARTTRANSFER_TIME', 3145745);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_TOTAL_TIME', 3145731);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLMSG_DONE', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLVERSION_NOW', 3);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLM_BAD_EASY_HANDLE', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLM_BAD_HANDLE', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLM_CALL_MULTI_PERFORM', -1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLM_INTERNAL_ERROR', 4);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLM_OK', 0);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLM_OUT_OF_MEMORY', 3);
define ('CURLM_ADDED_ALREADY', 7);

/**
 * Available since cURL 7.10.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLPROXY_HTTP', 0);

/**
 * Available since PHP 5.2.10 and cURL 7.10.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLPROXY_SOCKS4', 4);

/**
 * Available since cURL 7.10.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLPROXY_SOCKS5', 5);
define ('CURLSHOPT_NONE', 0);
define ('CURLSHOPT_SHARE', 1);
define ('CURLSHOPT_UNSHARE', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_HTTP_VERSION_1_0', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_HTTP_VERSION_1_1', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_HTTP_VERSION_NONE', 0);
define ('CURL_LOCK_DATA_COOKIE', 2);
define ('CURL_LOCK_DATA_DNS', 3);
define ('CURL_LOCK_DATA_SSL_SESSION', 4);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_NETRC_IGNORED', 0);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_NETRC_OPTIONAL', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_NETRC_REQUIRED', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_SSLVERSION_DEFAULT', 0);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_SSLVERSION_SSLv2', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_SSLVERSION_SSLv3', 3);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_SSLVERSION_TLSv1', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_TIMECOND_IFMODSINCE', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_TIMECOND_IFUNMODSINCE', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_TIMECOND_LASTMOD', 3);
define ('CURL_TIMECOND_NONE', 0);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_VERSION_IPV6', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_VERSION_KERBEROS4', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_VERSION_LIBZ', 8);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_VERSION_SSL', 4);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_HTTPAUTH', 107);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLAUTH_ANY', -17);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLAUTH_ANYSAFE', -18);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLAUTH_BASIC', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLAUTH_DIGEST', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLAUTH_GSSNEGOTIATE', 4);
define ('CURLAUTH_NONE', 0);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLAUTH_NTLM', 8);
define ('CURLINFO_HTTP_CONNECTCODE', 2097174);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_FTP_CREATE_MISSING_DIRS', 110);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_PROXYAUTH', 111);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FILESIZE_EXCEEDED', 63);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_LDAP_INVALID_URL', 62);
define ('CURLINFO_HTTPAUTH_AVAIL', 2097175);
define ('CURLINFO_RESPONSE_CODE', 2097154);
define ('CURLINFO_PROXYAUTH_AVAIL', 2097176);
define ('CURLOPT_FTP_RESPONSE_TIMEOUT', 112);
define ('CURLOPT_IPRESOLVE', 113);
define ('CURLOPT_MAXFILESIZE', 114);
define ('CURL_IPRESOLVE_V4', 1);
define ('CURL_IPRESOLVE_V6', 2);
define ('CURL_IPRESOLVE_WHATEVER', 0);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_FTP_SSL_FAILED', 64);

/**
 * Available since PHP 5.2.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLFTPSSL_ALL', 3);

/**
 * Available since PHP 5.2.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLFTPSSL_CONTROL', 2);

/**
 * Available since PHP 5.2.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLFTPSSL_NONE', 0);

/**
 * Available since PHP 5.2.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLFTPSSL_TRY', 1);

/**
 * Available since PHP 5.2.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_FTP_SSL', 119);
define ('CURLOPT_NETRC_FILE', 10118);

/**
 * Available since PHP 5.1.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLFTPAUTH_DEFAULT', 0);

/**
 * Available since PHP 5.1.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLFTPAUTH_SSL', 1);

/**
 * Available since PHP 5.1.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLFTPAUTH_TLS', 2);

/**
 * Available since PHP 5.1.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_FTPSSLAUTH', 129);
define ('CURLOPT_FTP_ACCOUNT', 10134);

/**
 * Available since PHP 5.2.1
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_TCP_NODELAY', 121);
define ('CURLINFO_OS_ERRNO', 2097177);
define ('CURLINFO_NUM_CONNECTS', 2097178);
define ('CURLINFO_SSL_ENGINES', 4194331);
define ('CURLINFO_COOKIELIST', 4194332);
define ('CURLOPT_COOKIELIST', 10135);
define ('CURLOPT_IGNORE_CONTENT_LENGTH', 136);
define ('CURLOPT_FTP_SKIP_PASV_IP', 137);
define ('CURLOPT_FTP_FILEMETHOD', 138);
define ('CURLOPT_CONNECT_ONLY', 141);
define ('CURLOPT_LOCALPORT', 139);
define ('CURLOPT_LOCALPORTRANGE', 140);
define ('CURLFTPMETHOD_MULTICWD', 1);
define ('CURLFTPMETHOD_NOCWD', 2);
define ('CURLFTPMETHOD_SINGLECWD', 3);
define ('CURLINFO_FTP_ENTRY_PATH', 1048606);
define ('CURLOPT_FTP_ALTERNATIVE_TO_USER', 10147);

/**
 * Available since PHP 5.4.0 and cURL 7.15.5
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_MAX_RECV_SPEED_LARGE', 30146);

/**
 * Available since PHP 5.4.0 and cURL 7.15.5
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_MAX_SEND_SPEED_LARGE', 30145);
define ('CURLE_SSL_CACERT_BADFILE', 77);
define ('CURLOPT_SSL_SESSIONID_CACHE', 150);

/**
 * Available since PHP 5.5.0 and cURL 7.16.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLMOPT_PIPELINING', 3);

/**
 * Available since PHP 5.3.0 and cURL 7.16.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLE_SSH', 79);
define ('CURLOPT_FTP_SSL_CCC', 154);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSH_AUTH_TYPES', 151);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSH_PRIVATE_KEYFILE', 10153);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSH_PUBLIC_KEYFILE', 10152);
define ('CURLFTPSSL_CCC_ACTIVE', 2);
define ('CURLFTPSSL_CCC_NONE', 0);
define ('CURLFTPSSL_CCC_PASSIVE', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_CONNECTTIMEOUT_MS', 156);
define ('CURLOPT_HTTP_CONTENT_DECODING', 158);
define ('CURLOPT_HTTP_TRANSFER_DECODING', 157);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_TIMEOUT_MS', 155);

/**
 * Available since PHP 5.5.0 and cURL 7.16.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLMOPT_MAXCONNECTS', 6);
define ('CURLOPT_KRBLEVEL', 10063);
define ('CURLOPT_NEW_DIRECTORY_PERMS', 160);
define ('CURLOPT_NEW_FILE_PERMS', 159);
define ('CURLOPT_APPEND', 50);
define ('CURLOPT_DIRLISTONLY', 48);
define ('CURLOPT_USE_SSL', 119);
define ('CURLUSESSL_ALL', 3);
define ('CURLUSESSL_CONTROL', 2);
define ('CURLUSESSL_NONE', 0);
define ('CURLUSESSL_TRY', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSH_HOST_PUBLIC_KEY_MD5', 10162);
define ('CURLOPT_PROXY_TRANSFER_MODE', 166);

/**
 * Available since PHP 5.5.0 and cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLPAUSE_ALL', 5);

/**
 * Available since PHP 5.5.0 and cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLPAUSE_CONT', 0);

/**
 * Available since PHP 5.5.0 and cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLPAUSE_RECV', 1);

/**
 * Available since PHP 5.5.0 and cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLPAUSE_RECV_CONT', 0);

/**
 * Available since PHP 5.5.0 and cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLPAUSE_SEND', 4);

/**
 * Available since PHP 5.5.0 and cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLPAUSE_SEND_CONT', 0);
define ('CURL_READFUNC_PAUSE', 268435457);
define ('CURL_WRITEFUNC_PAUSE', 268435457);

/**
 * Available since PHP 5.5.23 and PHP 5.6.7 and cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLPROXY_SOCKS4A', 6);

/**
 * Available since PHP 5.5.23 and PHP 5.6.7 and cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLPROXY_SOCKS5_HOSTNAME', 7);

/**
 * Available since PHP 5.3.7
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_REDIRECT_URL', 1048607);
define ('CURLINFO_APPCONNECT_TIME', 3145761);

/**
 * Available since PHP 5.4.7
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_PRIMARY_IP', 1048608);
define ('CURLOPT_ADDRESS_SCOPE', 171);
define ('CURLOPT_CRLFILE', 10169);
define ('CURLOPT_ISSUERCERT', 10170);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_KEYPASSWD', 10026);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLSSH_AUTH_ANY', -1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLSSH_AUTH_DEFAULT', -1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLSSH_AUTH_HOST', 4);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLSSH_AUTH_KEYBOARD', 8);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLSSH_AUTH_NONE', 0);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLSSH_AUTH_PASSWORD', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLSSH_AUTH_PUBLICKEY', 1);
define ('CURLINFO_CERTINFO', 4194338);
define ('CURLOPT_CERTINFO', 172);
define ('CURLOPT_PASSWORD', 10174);
define ('CURLOPT_POSTREDIR', 161);
define ('CURLOPT_PROXYPASSWORD', 10176);
define ('CURLOPT_PROXYUSERNAME', 10175);

/**
 * Available since PHP 5.5.0 and cURL 7.19.1
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_USERNAME', 10173);

/**
 * Available since PHP 7.0.7 and cURL 7.18.2
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_REDIR_POST_301', 1);

/**
 * Available since PHP 7.0.7 and cURL 7.18.2
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_REDIR_POST_302', 2);

/**
 * Available since PHP 7.0.7 and cURL 7.18.2
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_REDIR_POST_ALL', 7);
define ('CURLAUTH_DIGEST_IE', 16);
define ('CURLINFO_CONDITION_UNMET', 2097187);
define ('CURLOPT_NOPROXY', 10177);
define ('CURLOPT_PROTOCOLS', 181);
define ('CURLOPT_REDIR_PROTOCOLS', 182);
define ('CURLOPT_SOCKS5_GSSAPI_NEC', 180);
define ('CURLOPT_SOCKS5_GSSAPI_SERVICE', 10179);
define ('CURLOPT_TFTP_BLKSIZE', 178);
define ('CURLPROTO_ALL', -1);
define ('CURLPROTO_DICT', 512);
define ('CURLPROTO_FILE', 1024);
define ('CURLPROTO_FTP', 4);
define ('CURLPROTO_FTPS', 8);
define ('CURLPROTO_HTTP', 1);
define ('CURLPROTO_HTTPS', 2);
define ('CURLPROTO_LDAP', 128);
define ('CURLPROTO_LDAPS', 256);
define ('CURLPROTO_SCP', 16);
define ('CURLPROTO_SFTP', 32);
define ('CURLPROTO_TELNET', 64);
define ('CURLPROTO_TFTP', 2048);

/**
 * Available since PHP 7.0.7 and cURL 7.19.3
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLPROXY_HTTP_1_0', 1);

/**
 * Available since PHP 7.0.7 and cURL 7.19.3
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLFTP_CREATE_DIR', 1);

/**
 * Available since PHP 7.0.7 and cURL 7.19.3
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLFTP_CREATE_DIR_NONE', 0);

/**
 * Available since PHP 7.0.7 and cURL 7.19.3
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLFTP_CREATE_DIR_RETRY', 2);
define ('CURLOPT_SSH_KNOWNHOSTS', 10183);
define ('CURLINFO_RTSP_CLIENT_CSEQ', 2097189);
define ('CURLINFO_RTSP_CSEQ_RECV', 2097191);
define ('CURLINFO_RTSP_SERVER_CSEQ', 2097190);
define ('CURLINFO_RTSP_SESSION_ID', 1048612);
define ('CURLOPT_FTP_USE_PRET', 188);
define ('CURLOPT_MAIL_FROM', 10186);
define ('CURLOPT_MAIL_RCPT', 10187);
define ('CURLOPT_RTSP_CLIENT_CSEQ', 193);
define ('CURLOPT_RTSP_REQUEST', 189);
define ('CURLOPT_RTSP_SERVER_CSEQ', 194);
define ('CURLOPT_RTSP_SESSION_ID', 10190);
define ('CURLOPT_RTSP_STREAM_URI', 10191);
define ('CURLOPT_RTSP_TRANSPORT', 10192);
define ('CURLPROTO_IMAP', 4096);
define ('CURLPROTO_IMAPS', 8192);
define ('CURLPROTO_POP3', 16384);
define ('CURLPROTO_POP3S', 32768);
define ('CURLPROTO_RTSP', 262144);
define ('CURLPROTO_SMTP', 65536);
define ('CURLPROTO_SMTPS', 131072);
define ('CURL_RTSPREQ_ANNOUNCE', 3);
define ('CURL_RTSPREQ_DESCRIBE', 2);
define ('CURL_RTSPREQ_GET_PARAMETER', 8);
define ('CURL_RTSPREQ_OPTIONS', 1);
define ('CURL_RTSPREQ_PAUSE', 6);
define ('CURL_RTSPREQ_PLAY', 5);
define ('CURL_RTSPREQ_RECEIVE', 11);
define ('CURL_RTSPREQ_RECORD', 10);
define ('CURL_RTSPREQ_SET_PARAMETER', 9);
define ('CURL_RTSPREQ_SETUP', 4);
define ('CURL_RTSPREQ_TEARDOWN', 7);

/**
 * Available since PHP 5.4.7
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_LOCAL_IP', 1048617);

/**
 * Available since PHP 5.4.7
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_LOCAL_PORT', 2097194);

/**
 * Available since PHP 5.4.7
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLINFO_PRIMARY_PORT', 2097192);
define ('CURLOPT_FNMATCH_FUNCTION', 20200);
define ('CURLOPT_WILDCARDMATCH', 197);
define ('CURLPROTO_RTMP', 524288);
define ('CURLPROTO_RTMPE', 2097152);
define ('CURLPROTO_RTMPS', 8388608);
define ('CURLPROTO_RTMPT', 1048576);
define ('CURLPROTO_RTMPTE', 4194304);
define ('CURLPROTO_RTMPTS', 16777216);
define ('CURL_FNMATCHFUNC_FAIL', 2);
define ('CURL_FNMATCHFUNC_MATCH', 0);
define ('CURL_FNMATCHFUNC_NOMATCH', 1);
define ('CURLPROTO_GOPHER', 33554432);
define ('CURLAUTH_ONLY', 2147483648);
define ('CURLOPT_RESOLVE', 10203);
define ('CURLOPT_TLSAUTH_PASSWORD', 10205);
define ('CURLOPT_TLSAUTH_TYPE', 10206);
define ('CURLOPT_TLSAUTH_USERNAME', 10204);
define ('CURL_TLSAUTH_SRP', 1);
define ('CURLOPT_ACCEPT_ENCODING', 10102);
define ('CURLOPT_TRANSFER_ENCODING', 207);

/**
 * Available since PHP 7.0.7 and cURL 7.22.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLAUTH_NTLM_WB', 32);
define ('CURLGSSAPI_DELEGATION_FLAG', 2);
define ('CURLGSSAPI_DELEGATION_POLICY_FLAG', 1);
define ('CURLOPT_GSSAPI_DELEGATION', 210);
define ('CURLOPT_ACCEPTTIMEOUT_MS', 212);
define ('CURLOPT_DNS_SERVERS', 10211);
define ('CURLOPT_MAIL_AUTH', 10217);

/**
 * Available since PHP 5.5.0 and cURL 7.25.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSL_OPTIONS', 216);
define ('CURLOPT_TCP_KEEPALIVE', 213);
define ('CURLOPT_TCP_KEEPIDLE', 214);
define ('CURLOPT_TCP_KEEPINTVL', 215);

/**
 * Available since PHP 5.5.0 and cURL 7.25.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLSSLOPT_ALLOW_BEAST', 1);

/**
 * Available since PHP 7.0.7 and cURL 7.25.1
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_REDIR_POST_303', 4);

/**
 * Available since PHP 7.0.7 and cURL 7.28.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLSSH_AUTH_AGENT', 16);

/**
 * Available since PHP 7.0.7 and cURL 7.30.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLMOPT_CHUNK_LENGTH_PENALTY_SIZE', 30010);

/**
 * Available since PHP 7.0.7 and cURL 7.30.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLMOPT_CONTENT_LENGTH_PENALTY_SIZE', 30009);

/**
 * Available since PHP 7.0.7 and cURL 7.30.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLMOPT_MAX_HOST_CONNECTIONS', 7);

/**
 * Available since PHP 7.0.7 and cURL 7.30.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLMOPT_MAX_PIPELINE_LENGTH', 8);

/**
 * Available since PHP 7.0.7 and cURL 7.30.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLMOPT_MAX_TOTAL_CONNECTIONS', 13);

/**
 * Available since PHP 7.0.7 and cURL 7.31.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SASL_IR', 218);

/**
 * Available since PHP 7.0.7 and cURL 7.33.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_DNS_INTERFACE', 10221);

/**
 * Available since PHP 7.0.7 and cURL 7.33.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_DNS_LOCAL_IP4', 10222);

/**
 * Available since PHP 7.0.7 and cURL 7.33.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_DNS_LOCAL_IP6', 10223);

/**
 * Available since PHP 7.0.7 and cURL 7.33.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_XOAUTH2_BEARER', 10220);
define ('CURL_HTTP_VERSION_2_0', 3);

/**
 * Available since PHP 5.5.24 and 5.6.8 and cURL 7.33.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_VERSION_HTTP2', 65536);

/**
 * Available since PHP 7.0.7 and cURL 7.34.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_LOGIN_OPTIONS', 10224);

/**
 * Available since PHP 5.5.19 and 5.6.3
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_SSLVERSION_TLSv1_0', 4);

/**
 * Available since PHP 5.5.19 and 5.6.3
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_SSLVERSION_TLSv1_1', 5);

/**
 * Available since PHP 5.5.19 and 5.6.3
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_SSLVERSION_TLSv1_2', 6);

/**
 * Available since PHP 7.0.7 and cURL 7.36.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_EXPECT_100_TIMEOUT_MS', 227);

/**
 * Available since PHP 7.0.7 and cURL 7.36.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSL_ENABLE_ALPN', 226);

/**
 * Available since PHP 7.0.7 and cURL 7.36.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSL_ENABLE_NPN', 225);

/**
 * Available since PHP 7.0.7 and cURL 7.37.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLHEADER_SEPARATE', 1);

/**
 * Available since PHP 7.0.7 and cURL 7.37.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLHEADER_UNIFIED', 0);

/**
 * Available since PHP 7.0.7 and cURL 7.37.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_HEADEROPT', 229);

/**
 * Available since PHP 7.0.7 and cURL 7.37.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_PROXYHEADER', 10228);

/**
 * Available since PHP 7.0.7 and cURL 7.38.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLAUTH_NEGOTIATE', 4);

/**
 * Available since PHP 7.0.7 and cURL 7.39.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_PINNEDPUBLICKEY', 10230);

/**
 * Available since PHP 7.0.7 and cURL 7.40.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_UNIX_SOCKET_PATH', 10231);

/**
 * Available since PHP 7.0.7 and cURL 7.40.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLPROTO_SMB', 67108864);

/**
 * Available since PHP 7.0.7 and cURL 7.40.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLPROTO_SMBS', 134217728);

/**
 * Available since PHP 7.0.7 and cURL 7.41.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSL_VERIFYSTATUS', 232);

/**
 * Available since PHP 7.0.7 and cURL 7.42.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_PATH_AS_IS', 234);

/**
 * Available since PHP 7.0.7 and cURL 7.42.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SSL_FALSESTART', 233);

/**
 * Available since PHP 7.0.7 and cURL 7.43.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_HTTP_VERSION_2', 3);

/**
 * Available since PHP 7.0.7 and cURL 7.43.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_PIPEWAIT', 237);

/**
 * Available since PHP 7.0.7 and cURL 7.43.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_PROXY_SERVICE_NAME', 10235);

/**
 * Available since PHP 7.0.7 and cURL 7.43.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_SERVICE_NAME', 10236);

/**
 * Available since PHP 7.0.0 and cURL 7.43.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLPIPE_NOTHING', 0);

/**
 * Available since PHP 7.0.0 and cURL 7.43.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLPIPE_HTTP1', 1);

/**
 * Available since PHP 7.0.0 and cURL 7.43.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLPIPE_MULTIPLEX', 2);

/**
 * Available since PHP 7.0.7 and cURL 7.44.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLSSLOPT_NO_REVOKE', 2);

/**
 * Available since PHP 7.0.7 and cURL 7.45.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_DEFAULT_PROTOCOL', 10238);

/**
 * Available since PHP 7.0.7 and cURL 7.46.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLOPT_STREAM_WEIGHT', 239);

/**
 * Available since PHP 7.1.0 and cURL 7.44.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURLMOPT_PUSHFUNCTION', 20014);

/**
 * Available since PHP 7.1.0 and cURL 7.44.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_PUSH_OK', 0);

/**
 * Available since PHP 7.1.0 and cURL 7.44.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_PUSH_DENY', 1);

/**
 * Available since PHP 7.0.7 and cURL 7.47.0
 * @link http://www.php.net/manual/en/curl.constants.php
 */
define ('CURL_HTTP_VERSION_2TLS', 4);
define ('CURLOPT_SAFE_UPLOAD', -1);

// End of curl v.7.1.1
