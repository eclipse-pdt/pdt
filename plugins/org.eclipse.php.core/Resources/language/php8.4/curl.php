<?php

// Start of curl v.8.4.7

final class CurlHandle  {
}

final class CurlMultiHandle  {
}

final class CurlShareHandle  {
}

class CURLFile  {

	public string $name;

	public string $mime;

	public string $postname;

	/**
	 * Create a CURLFile object
	 * @link http://www.php.net/manual/en/curlfile.construct.php
	 * @param string $filename Path to the file which will be uploaded.
	 * @param string|null $mime_type [optional] Mimetype of the file.
	 * @param string|null $posted_filename [optional] Name of the file to be used in the upload data.
	 * @return CURLFile Returns a CURLFile object.
	 */
	public function __construct (string $filename, ?string $mime_type = null, ?string $posted_filename = null): CURLFile {}

	/**
	 * Get file name
	 * @link http://www.php.net/manual/en/curlfile.getfilename.php
	 * @return string Returns file name.
	 */
	public function getFilename (): string {}

	/**
	 * Get MIME type
	 * @link http://www.php.net/manual/en/curlfile.getmimetype.php
	 * @return string Returns MIME type.
	 */
	public function getMimeType (): string {}

	/**
	 * Get file name for POST
	 * @link http://www.php.net/manual/en/curlfile.getpostfilename.php
	 * @return string Returns file name for POST.
	 */
	public function getPostFilename (): string {}

	/**
	 * Set MIME type
	 * @link http://www.php.net/manual/en/curlfile.setmimetype.php
	 * @param string $mime_type MIME type to be used in POST data.
	 * @return void No value is returned.
	 */
	public function setMimeType (string $mime_type): void {}

	/**
	 * Set file name for POST
	 * @link http://www.php.net/manual/en/curlfile.setpostfilename.php
	 * @param string $posted_filename Filename to be used in POST data.
	 * @return void No value is returned.
	 */
	public function setPostFilename (string $posted_filename): void {}

}

class CURLStringFile  {

	public string $data;

	public string $postname;

	public string $mime;

	/**
	 * Create a CURLStringFile object
	 * @link http://www.php.net/manual/en/curlstringfile.construct.php
	 * @param string $data The contents to be uploaded.
	 * @param string $postname The name of the file to be used in the upload data.
	 * @param string $mime [optional] MIME type of the file (default is application/octet-stream).
	 * @return string 
	 */
	public function __construct (string $data, string $postname, string $mime = '"application/octet-stream"'): string {}

}

/**
 * Close a cURL session
 * @link http://www.php.net/manual/en/function.curl-close.php
 * @param CurlHandle $handle 
 * @return void No value is returned.
 */
function curl_close (CurlHandle $handle): void {}

/**
 * Copy a cURL handle along with all of its preferences
 * @link http://www.php.net/manual/en/function.curl-copy-handle.php
 * @param CurlHandle $handle 
 * @return CurlHandle|false Returns a new cURL handle, or false on failure.
 */
function curl_copy_handle (CurlHandle $handle): CurlHandle|false {}

/**
 * Return the last error number
 * @link http://www.php.net/manual/en/function.curl-errno.php
 * @param CurlHandle $handle 
 * @return int Returns the error number or 0 (zero) if no error
 * occurred.
 */
function curl_errno (CurlHandle $handle): int {}

/**
 * Return a string containing the last error for the current session
 * @link http://www.php.net/manual/en/function.curl-error.php
 * @param CurlHandle $handle 
 * @return string Returns the error message or '' (the empty string) if no
 * error occurred.
 */
function curl_error (CurlHandle $handle): string {}

/**
 * URL encodes the given string
 * @link http://www.php.net/manual/en/function.curl-escape.php
 * @param CurlHandle $handle A cURL handle returned by
 * curl_init.
 * @param string $string The string to be encoded.
 * @return string|false Returns escaped string or false on failure.
 */
function curl_escape (CurlHandle $handle, string $string): string|false {}

/**
 * Decodes the given URL encoded string
 * @link http://www.php.net/manual/en/function.curl-unescape.php
 * @param CurlHandle $handle A cURL handle returned by
 * curl_init.
 * @param string $string The URL encoded string to be decoded.
 * @return string|false Returns decoded string or false on failure.
 */
function curl_unescape (CurlHandle $handle, string $string): string|false {}

/**
 * Set a cURL multi option
 * @link http://www.php.net/manual/en/function.curl-multi-setopt.php
 * @param CurlMultiHandle $multi_handle A cURL multi handle returned by
 * curl_multi_init.
 * @param int $option One of the CURLMOPT_&#42; constants.
 * @param mixed $value The value to be set on option.
 * See the description of the
 * CURLMOPT_&#42; constants
 * for details on the type of values each constant expects.
 * @return bool Returns true on success or false on failure.
 */
function curl_multi_setopt (CurlMultiHandle $multi_handle, int $option, mixed $value): bool {}

/**
 * Perform a cURL session
 * @link http://www.php.net/manual/en/function.curl-exec.php
 * @param CurlHandle $handle 
 * @return string|bool On success, this function flushes the result directly to the
 * stdout and returns true, or false on failure.
 * However, if the CURLOPT_RETURNTRANSFER
 * option is set, it will return
 * the result on success, false on failure.
 * <p>Note that response status codes which indicate errors (such as 404
 * Not found) are not regarded as failure.
 * curl_getinfo can be used to check for these.</p>
 */
function curl_exec (CurlHandle $handle): string|bool {}

/**
 * Create a CURLFile object
 * @link http://www.php.net/manual/en/curlfile.construct.php
 * @param string $filename Path to the file which will be uploaded.
 * @param string|null $mime_type [optional] Mimetype of the file.
 * @param string|null $posted_filename [optional] Name of the file to be used in the upload data.
 * @return CURLFile Returns a CURLFile object.
 */
function curl_file_create (string $filename, ?string $mime_type = null, ?string $posted_filename = null): CURLFile {}

/**
 * Get information regarding a specific transfer
 * @link http://www.php.net/manual/en/function.curl-getinfo.php
 * @param CurlHandle $handle 
 * @param int|null $option [optional] 
 * @return mixed If option is given, returns its value.
 * Otherwise, returns an associative array with the following elements
 * (which correspond to option), or false on failure:
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
 * <br>
 * "posttransfer_time_us" (Available as of PHP 8.4.0 and cURL 8.10.0)
 * </p>
 * Note that private data is not included in the associative array and must be retrieved individually with the CURLINFO_PRIVATE option.
 */
function curl_getinfo (CurlHandle $handle, ?int $option = null): mixed {}

/**
 * Initialize a cURL session
 * @link http://www.php.net/manual/en/function.curl-init.php
 * @param string|null $url [optional] 
 * @return CurlHandle|false Returns a cURL handle on success, false on errors.
 */
function curl_init (?string $url = null): CurlHandle|false {}

/**
 * Performs any connection upkeep checks
 * @link http://www.php.net/manual/en/function.curl_upkeep.php
 * @param CurlHandle $handle 
 * @return bool Returns true on success or false on failure.
 */
function curl_upkeep (CurlHandle $handle): bool {}

/**
 * Add a normal cURL handle to a cURL multi handle
 * @link http://www.php.net/manual/en/function.curl-multi-add-handle.php
 * @param CurlMultiHandle $multi_handle 
 * @param CurlHandle $handle 
 * @return int Returns 0 on success, or one of the CURLM_&#42; errors
 * code.
 */
function curl_multi_add_handle (CurlMultiHandle $multi_handle, CurlHandle $handle): int {}

/**
 * Remove all cURL handles from a multi handle
 * @link http://www.php.net/manual/en/function.curl-multi-close.php
 * @param CurlMultiHandle $multi_handle 
 * @return void No value is returned.
 */
function curl_multi_close (CurlMultiHandle $multi_handle): void {}

/**
 * Return the last multi curl error number
 * @link http://www.php.net/manual/en/function.curl-multi-errno.php
 * @param CurlMultiHandle $multi_handle A cURL multi handle returned by
 * curl_multi_init.
 * @return int Return an integer containing the last multi curl error number.
 */
function curl_multi_errno (CurlMultiHandle $multi_handle): int {}

/**
 * Run the sub-connections of the current cURL handle
 * @link http://www.php.net/manual/en/function.curl-multi-exec.php
 * @param CurlMultiHandle $multi_handle 
 * @param int $still_running 
 * @return int A cURL code defined in the cURL Predefined Constants.
 * <p>This only returns errors regarding the whole multi stack. There might still have 
 * occurred problems on individual transfers even when this function returns 
 * CURLM_OK.</p>
 */
function curl_multi_exec (CurlMultiHandle $multi_handle, int &$still_running): int {}

/**
 * Return the content of a cURL handle if CURLOPT_RETURNTRANSFER is set
 * @link http://www.php.net/manual/en/function.curl-multi-getcontent.php
 * @param CurlHandle $handle 
 * @return string|null Return the content of a cURL handle if CURLOPT_RETURNTRANSFER is set or null if not set.
 */
function curl_multi_getcontent (CurlHandle $handle): ?string {}

/**
 * Get information about the current transfers
 * @link http://www.php.net/manual/en/function.curl-multi-info-read.php
 * @param CurlMultiHandle $multi_handle 
 * @param int $queued_messages [optional] 
 * @return array|false On success, returns an associative array for the message, false on failure.
 * <p><table>
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
 * </table></p>
 */
function curl_multi_info_read (CurlMultiHandle $multi_handle, int &$queued_messages = null): array|false {}

/**
 * Returns a new cURL multi handle
 * @link http://www.php.net/manual/en/function.curl-multi-init.php
 * @return CurlMultiHandle Returns a cURL multi handle.
 */
function curl_multi_init (): CurlMultiHandle {}

/**
 * Remove a handle from a set of cURL handles
 * @link http://www.php.net/manual/en/function.curl-multi-remove-handle.php
 * @param CurlMultiHandle $multi_handle 
 * @param CurlHandle $handle 
 * @return int Returns 0 on success, or one of the CURLM_&#42; error
 * codes.
 */
function curl_multi_remove_handle (CurlMultiHandle $multi_handle, CurlHandle $handle): int {}

/**
 * Wait until reading or writing is possible for any cURL multi handle connection
 * @link http://www.php.net/manual/en/function.curl-multi-select.php
 * @param CurlMultiHandle $multi_handle 
 * @param float $timeout [optional] 
 * @return int On success, returns the number of active descriptors contained in
 * the descriptor sets. This may be 0 if there was no activity on any
 * of the descriptors. On failure, this function will
 * return -1 on a select failure (from the
 * underlying select() system call).
 */
function curl_multi_select (CurlMultiHandle $multi_handle, float $timeout = 1.0): int {}

/**
 * Return string describing error code
 * @link http://www.php.net/manual/en/function.curl-multi-strerror.php
 * @param int $error_code One of the CURLM_&#42; constants.
 * @return string|null Returns error string for valid error code, null otherwise.
 */
function curl_multi_strerror (int $error_code): ?string {}

/**
 * Pause and unpause a connection
 * @link http://www.php.net/manual/en/function.curl-pause.php
 * @param CurlHandle $handle A cURL handle returned by
 * curl_init.
 * @param int $flags One of CURLPAUSE_&#42; constants.
 * @return int Returns an error code (CURLE_OK for no error).
 */
function curl_pause (CurlHandle $handle, int $flags): int {}

/**
 * Reset all options of a libcurl session handle
 * @link http://www.php.net/manual/en/function.curl-reset.php
 * @param CurlHandle $handle A cURL handle returned by
 * curl_init.
 * @return void No value is returned.
 */
function curl_reset (CurlHandle $handle): void {}

/**
 * Set multiple options for a cURL transfer
 * @link http://www.php.net/manual/en/function.curl-setopt-array.php
 * @param CurlHandle $handle 
 * @param array $options 
 * @return bool Returns true if all options were successfully set. If an option could
 * not be successfully set, false is immediately returned, ignoring any
 * future options in the options array.
 */
function curl_setopt_array (CurlHandle $handle, array $options): bool {}

/**
 * Set an option for a cURL transfer
 * @link http://www.php.net/manual/en/function.curl-setopt.php
 * @param CurlHandle $handle 
 * @param int $option 
 * @param mixed $value 
 * @return bool Returns true on success or false on failure.
 */
function curl_setopt (CurlHandle $handle, int $option, mixed $value): bool {}

/**
 * Close a cURL share handle
 * @link http://www.php.net/manual/en/function.curl-share-close.php
 * @param CurlShareHandle $share_handle A cURL share handle returned by
 * curl_share_init.
 * @return void No value is returned.
 */
function curl_share_close (CurlShareHandle $share_handle): void {}

/**
 * Return the last share curl error number
 * @link http://www.php.net/manual/en/function.curl-share-errno.php
 * @param CurlShareHandle $share_handle A cURL share handle returned by
 * curl_share_init.
 * @return int Returns an integer containing the last share curl error number.
 */
function curl_share_errno (CurlShareHandle $share_handle): int {}

/**
 * Initialize a cURL share handle
 * @link http://www.php.net/manual/en/function.curl-share-init.php
 * @return CurlShareHandle Returns a cURL share handle.
 */
function curl_share_init (): CurlShareHandle {}

/**
 * Set an option for a cURL share handle
 * @link http://www.php.net/manual/en/function.curl-share-setopt.php
 * @param CurlShareHandle $share_handle A cURL share handle returned by
 * curl_share_init.
 * @param int $option One of the CURLSHOPT_&#42; constants.
 * @param mixed $value One of the CURL_LOCK_DATA_&#42; constants.
 * @return bool Returns true on success or false on failure.
 */
function curl_share_setopt (CurlShareHandle $share_handle, int $option, mixed $value): bool {}

/**
 * Return string describing the given error code
 * @link http://www.php.net/manual/en/function.curl-share-strerror.php
 * @param int $error_code One of the cURL error codes constants.
 * @return string|null Returns error description or null for invalid error code.
 */
function curl_share_strerror (int $error_code): ?string {}

/**
 * Return string describing the given error code
 * @link http://www.php.net/manual/en/function.curl-strerror.php
 * @param int $error_code One of the cURL error codes constants.
 * @return string|null Returns error description or null for invalid error code.
 */
function curl_strerror (int $error_code): ?string {}

/**
 * Gets cURL version information
 * @link http://www.php.net/manual/en/function.curl-version.php
 * @return array|false Returns an associative array with the following elements: 
 * <table>
 * <tr valign="top">
 * <td>Key</td>
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
 * </tr>
 * <tr valign="top">
 * <td>features</td>
 * <td>A bitmask of the CURL_VERSION_&#42; constants</td>
 * </tr>
 * <tr valign="top">
 * <td>protocols</td>
 * <td>An array of protocols names supported by cURL</td>
 * </tr>
 * <tr valign="top">
 * <td>feature_list</td>
 * <td>
 * An associative array of all known cURL features, and whether they
 * are supported (true) or not (false)
 * </td>
 * </tr>
 * </table>
 */
function curl_version (): array|false {}


/**
 * true to automatically set the Referer: field in
 * requests where it follows a Location: redirect.
 * Defaults to 0.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_AUTOREFERER', 58);

/**
 * This constant is no longer used as of PHP 5.5.0.
 * Deprecated as of PHP 8.4.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_BINARYTRANSFER', 19914);

/**
 * The size of the buffer to use for each read. There is no guarantee
 * this request will be fulfilled, however.
 * This option accepts any value that can be cast to a valid int.
 * Defaults to CURL_MAX_WRITE_SIZE (currently, 16kB).
 * Available as of cURL 7.10.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_BUFFERSIZE', 98);

/**
 * A string with the name of a file holding one or more certificates to verify the
 * peer with. This only makes sense when used in combination with
 * CURLOPT_SSL_VERIFYPEER. Might require an absolute path.
 * Available as of cURL 7.4.2.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CAINFO', 10065);

/**
 * A string with a directory that holds multiple CA certificates.
 * Use this option alongside CURLOPT_SSL_VERIFYPEER.
 * Available as of cURL 7.9.8.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CAPATH', 10097);

/**
 * The number of seconds to wait while trying to connect. Use 0 to
 * wait indefinitely.
 * This option accepts any value that can be cast to a valid int.
 * Defaults to 300.
 * Available as of cURL 7.7.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CONNECTTIMEOUT', 78);

/**
 * A string with the contents of the Cookie: header to be used in the HTTP request.
 * Note that multiple cookies are separated with a semicolon followed
 * by a space (e.g., fruit=apple; colour=red).
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_COOKIE', 10022);

/**
 * A string with the name of the file containing the cookie data.
 * The cookie file can be in Netscape format, or just plain HTTP-style headers dumped into a file.
 * If the name is an empty string, no cookies are loaded, but cookie
 * handling is still enabled.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_COOKIEFILE', 10031);

/**
 * A string with the name of a file to save all internal cookies to when
 * the handle's destructor is called.
 * Available as of cURL 7.9.0.
 * As of PHP 8.0.0, curl_close is a no-op
 * and does not destroy the handle.
 * If cookies need to be written prior to the handle being automatically
 * destroyed, call unset on the handle.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_COOKIEJAR', 10082);

/**
 * true to mark this as a new cookie "session". It will force cURL
 * to ignore all cookies it is about to load that are "session cookies"
 * from the previous session. By default, cURL always stores and
 * loads all cookies, independent if they are session cookies or not.
 * Session cookies are cookies without expiry date and they are meant
 * to be alive and existing for this "session" only.
 * Available as of cURL 7.9.7.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_COOKIESESSION', 96);

/**
 * true to convert Unix newlines to CRLF newlines
 * on transfers.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CRLF', 27);

/**
 * A custom request method to use instead of
 * GET or HEAD when doing
 * a HTTP request. This is useful for doing
 * DELETE or other, more obscure HTTP requests.
 * Valid values are things like GET,
 * POST, CONNECT and so on;
 * i.e. Do not enter a whole HTTP request line here. For instance,
 * entering GET /index.html HTTP/1.0\r\n\r\n
 * would be incorrect.
 * This option accepts a string or null.
 * Available as of cURL 7.1.0.
 * <p>
 * Don't do this without making sure the server supports the custom
 * request method first.
 * </p>
 * <p>Don't do this without making sure the server supports the custom
 * request method first.</p>
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CUSTOMREQUEST', 10036);

/**
 * The number of seconds to keep DNS entries in memory. This
 * option is set to 120 (2 minutes) by default.
 * This option accepts any value that can be cast to a valid int.
 * Available as of cURL 7.9.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DNS_CACHE_TIMEOUT', 92);

/**
 * true to use a global DNS cache. This option is not thread-safe.
 * It is conditionally enabled by default if PHP is built for non-threaded use
 * (CLI, FCGI, Apache2-Prefork, etc.).
 * Available as of cURL 7.9.3 and deprecated as of cURL 7.11.1.
 * As of PHP 8.4, this option no longer has any effect.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DNS_USE_GLOBAL_CACHE', 91);

/**
 * Like CURLOPT_RANDOM_FILE, except a filename
 * to an Entropy Gathering Daemon socket.
 * Available as of cURL 7.7.0 and deprecated as of cURL 7.84.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_EGDSOCKET', 10077);

/**
 * The contents of the Accept-Encoding: header as a string.
 * This enables decoding of the response. Supported encodings are:
 * <p>
 * identity
 * deflate
 * gzip
 * </p>.
 * If an empty string is set,
 * a header containing all supported encoding types is sent.
 * Available as of cURL 7.10 and deprecated as of cURL 7.21.6.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_ENCODING', 10102);

/**
 * true to fail verbosely if the HTTP code returned
 * is greater than or equal to 400. The default behavior is to return
 * the page normally, ignoring the code.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FAILONERROR', 45);

/**
 * Accepts a file handle resource
 * to the file that the transfer should be written to.
 * The default is STDOUT (the browser window).
 * Available as of cURL 7.1.0 and deprecated as of cURL 7.9.7.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FILE', 10001);

/**
 * Set to true to attempt to retrieve the modification
 * date of the remote document. This value can be retrieved using
 * the CURLINFO_FILETIME option with
 * curl_getinfo.
 * Available as of cURL 7.5.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FILETIME', 69);

/**
 * Set to true to follow any Location: header that the server sends as
 * part of the HTTP header.
 * See also CURLOPT_MAXREDIRS.
 * This constant is not available when open_basedir
 * is enabled.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/ini.open-basedir.php
 * @var int
 */
define ('CURLOPT_FOLLOWLOCATION', 52);

/**
 * Set to true to force the connection to explicitly
 * close when it has finished processing, and not be pooled for reuse.
 * Available as of cURL 7.7.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FORBID_REUSE', 75);

/**
 * Set to true to force the use of a new connection
 * instead of a cached one.
 * Available as of cURL 7.7.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FRESH_CONNECT', 74);

/**
 * Set to true to append to the remote file instead of
 * overwriting it.
 * Available as of cURL 7.1.0 and deprecated as of cURL 7.16.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTPAPPEND', 50);

/**
 * Set to true to only list the names of an FTP directory.
 * Available as of cURL 7.1.0 and deprecated as of cURL 7.16.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTPLISTONLY', 48);

/**
 * A string which will be used to get the IP address to use for the FTP PORT instruction. The PORT instruction tells
 * the remote server to connect to our specified IP address. The
 * string may be a plain IP address, a hostname,
 * a network interface name (under Unix),
 * or just a plain - to use the system's default IP address.
 * This option accepts a string or null.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTPPORT', 10017);

/**
 * Set to true to use EPRT (and LPRT) when doing active FTP downloads.
 * Set to false to disable EPRT and LPRT and use PORT only.
 * Available as of cURL 7.10.5.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTP_USE_EPRT', 106);

/**
 * Set to true to first try an EPSV command for FTP transfers before reverting back to PASV.
 * Set to false to disable EPSV.
 * Available as of cURL 7.9.2.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTP_USE_EPSV', 85);

/**
 * Set to true to include the headers in the output sent to the callback
 * defined by CURLOPT_WRITEFUNCTION.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HEADER', 42);

/**
 * A callable with the following signature:
 * intcallback
 * resourcecurlHandle
 * stringheaderData
 * <p>
 * curlHandle
 * <br>
 * The cURL handle.
 * headerData
 * <br>
 * The header data which must be written by the callback.
 * </p>
 * The callback should return the number of bytes written.
 * Available as of cURL 7.7.2.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HEADERFUNCTION', 20079);

/**
 * An array of HTTP 200 responses that will be treated as valid responses and not as errors.
 * Available as of cURL 7.10.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HTTP200ALIASES', 10104);

/**
 * Set to true to reset the HTTP request method to GET. Since GET is the default, this is only necessary if the request
 * method has been changed.
 * Available as of cURL 7.8.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HTTPGET', 80);

/**
 * An array of HTTP header fields to set, in the format
 * array('Content-type: text/plain', 'Content-length: 100')
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HTTPHEADER', 10023);

/**
 * true to tunnel through a given HTTP proxy.
 * Available as of cURL 7.3.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HTTPPROXYTUNNEL', 61);

/**
 * Set to one of the
 * CURL_HTTP_VERSION_&#42; constants
 * for cURL to use the specified HTTP version.
 * Available as of cURL 7.9.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HTTP_VERSION', 84);

/**
 * Accepts a file handle resource
 * to the file that the transfer should be read from when uploading.
 * Available as of cURL 7.1.0 and deprecated as of cURL 7.9.7.
 * Use CURLOPT_READDATA instead.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_INFILE', 10009);

/**
 * The expected size, in bytes, of the file when uploading a file to
 * a remote site. Note that using this option will not stop cURL
 * from sending more data, as exactly what is sent depends on
 * CURLOPT_READFUNCTION.
 * This option accepts any value that can be cast to a valid int.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_INFILESIZE', 14);

/**
 * Set to a string with the name of the outgoing network interface to use.
 * This can be an interface name, an IP address or a host name.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_INTERFACE', 10062);

/**
 * The KRB4 (Kerberos 4) security level. Any of the following string values
 * (in order from least to most powerful) are valid:
 * <p>
 * clear
 * safe
 * confidential
 * private
 * </p>.
 * If the string does not match one of these,
 * private is used. Setting this option to null
 * will disable KRB4 security. Currently KRB4 security only works
 * with FTP transactions.
 * Available as of cURL 7.3.0 and deprecated as of cURL 7.17.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_KRB4LEVEL', 10063);

/**
 * The transfer speed, in bytes per second, that the transfer should be
 * below during the count of CURLOPT_LOW_SPEED_TIME
 * seconds before PHP considers the transfer too slow and aborts.
 * This option accepts any value that can be cast to a valid int.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_LOW_SPEED_LIMIT', 19);

/**
 * The number of seconds the transfer speed should be below
 * CURLOPT_LOW_SPEED_LIMIT before PHP considers
 * the transfer too slow and aborts.
 * This option accepts any value that can be cast to a valid int.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_LOW_SPEED_TIME', 20);

/**
 * The maximum amount of persistent connections that are allowed.
 * When the limit is reached, the oldest one in the cache is closed
 * to prevent increasing the number of open connections.
 * This option accepts any value that can be cast to a valid int.
 * Available as of cURL 7.7.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_MAXCONNECTS', 71);

/**
 * The maximum amount of HTTP redirections to follow. Use this option alongside CURLOPT_FOLLOWLOCATION.
 * Default value of 20 is set to prevent infinite redirects.
 * Setting to -1 allows inifinite redirects, and 0 refuses all redirects.
 * Available as of cURL 7.5.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_MAXREDIRS', 68);

/**
 * Set to true to scan the ~/.netrc
 * file to find a username and password for the remote site that
 * a connection is being established with.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_NETRC', 51);

/**
 * Set to true to exclude the body from the output.
 * For HTTP(S), cURL makes a HEAD request. For most other protocols,
 * cURL is not asking for the body data at all.
 * Changing this to false will result in body data being included in the output.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_NOBODY', 44);

/**
 * Set to true to disable the progress meter for cURL transfers.
 * <p>
 * PHP automatically sets this option to true, this should only be
 * changed for debugging purposes.
 * </p>
 * Available as of cURL 7.1.0.
 * <p>PHP automatically sets this option to true, this should only be
 * changed for debugging purposes.</p>
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_NOPROGRESS', 43);

/**
 * true to ignore any cURL function that causes a
 * signal to be sent to the PHP process. This is turned on by default
 * in multi-threaded SAPIs so timeout options can still be used.
 * Available as of cURL 7.10.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_NOSIGNAL', 99);

/**
 * An int with an alternative port number to connect to
 * instead of the one specified in the URL or the default port for the used protocol.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PORT', 3);

/**
 * Set to true to do a HTTP POST request.
 * This request uses the application/x-www-form-urlencoded header.
 * Defaults to false.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_POST', 47);

/**
 * The full data to post in a HTTP POST operation.
 * This parameter can either be
 * passed as a urlencoded string like 'para1=val1&amp;para2=val2&amp;...'
 * or as an array with the field name as key and field data as value.
 * If value is an array, the
 * Content-Type header will be set to
 * multipart/form-data.
 * Files can be sent using CURLFile or CURLStringFile,
 * in which case value must be an array.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_POSTFIELDS', 10015);

/**
 * An array of FTP command strings
 * to execute on the server after the FTP request has been performed.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_POSTQUOTE', 10039);

/**
 * Set an array of FTP command strings to pass to the server
 * after the transfer type is set.
 * These commands are not performed when a directory listing is performed,
 * only for file transfers.
 * Available as of cURL 7.9.5.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PREQUOTE', 10093);

/**
 * Any data that should be associated with this cURL handle. This data
 * can subsequently be retrieved with the
 * CURLINFO_PRIVATE option of
 * curl_getinfo. cURL does nothing with this data.
 * When using a cURL multi handle, this private data is typically a
 * unique key to identify a standard cURL handle.
 * Available as of cURL 7.10.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PRIVATE', 10103);

/**
 * A callable with the following signature:
 * intcallback
 * resourcecurlHandle
 * intbytesToDownload
 * intbytesDownloaded
 * intbytesToUpload
 * intbytesUploaded
 * <p>
 * curlHandle
 * <br>
 * The cURL handle.
 * bytesToDownload
 * <br>
 * The total number of bytes expected to be downloaded in this transfer.
 * bytesDownloaded
 * <br>
 * The number of bytes downloaded so far.
 * bytesToUpload
 * <br>
 * The total number of bytes expected to be uploaded in this transfer.
 * bytesUploaded
 * <br>
 * The number of bytes uploaded so far.
 * </p>
 * The callback should return an int with a non-zero value to abort the transfer
 * and set a CURLE_ABORTED_BY_CALLBACK error.
 * <p>
 * The callback is only called when the CURLOPT_NOPROGRESS
 * option is set to false.
 * </p>
 * Available as of cURL 7.1.0 and deprecated as of cURL 7.32.0.
 * Use CURLOPT_XFERINFOFUNCTION instead.
 * <p>The callback is only called when the CURLOPT_NOPROGRESS
 * option is set to false.</p>
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROGRESSFUNCTION', 20056);

/**
 * A string with the HTTP proxy to tunnel requests through.
 * This should be the hostname, the dotted numerical IP address
 * or a numerical IPv6 address written within [brackets].
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY', 10004);

/**
 * An int with the port number of the proxy to connect to.
 * This port number can also be set in CURLOPT_PROXY.
 * Setting this to zero makes cURL use the default proxy port number
 * or the port number specified in the proxy URL string.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXYPORT', 59);

/**
 * Sets the type of the proxy to one of the
 * CURLPROXY_&#42; constants.
 * Defaults to CURLPROXY_HTTP.
 * Available as of cURL 7.10.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXYTYPE', 101);

/**
 * A string with a username and password formatted as
 * [username]:[password] to use for the
 * connection to the proxy.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXYUSERPWD', 10006);

/**
 * true to HTTP PUT a file. The file to PUT must
 * be set with CURLOPT_READDATA and
 * CURLOPT_INFILESIZE.
 * Available as of cURL 7.1.0 and deprecated as of cURL 7.12.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PUT', 54);

/**
 * An array of FTP command strings to execute on the server prior to the FTP request.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_QUOTE', 10028);

/**
 * A string with a filename to be used to seed the random number generator for SSL.
 * Available as of cURL 7.7.0 and deprecated as of cURL 7.84.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_RANDOM_FILE', 10076);

/**
 * A string with the range(s) of data to retrieve in the format X-Y where X or Y are optional. HTTP transfers
 * also support several intervals, separated with commas in the format
 * X-Y,N-M.
 * Set to null to disable requesting a byte range.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_RANGE', 10007);

/**
 * Sets a file pointer resource that will be used by the file read function
 * set with CURLOPT_READFUNCTION.
 * Available as of cURL 7.9.7.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_READDATA', 10009);

/**
 * A callable with the following signature:
 * stringcallback
 * resourcecurlHandle
 * resourcestreamResource
 * intmaxAmountOfDataToRead
 * <p>
 * curlHandle
 * <br>
 * The cURL handle.
 * streamResource
 * <br>
 * Stream resource provided to cURL through the option
 * CURLOPT_READDATA.
 * maxAmountOfDataToRead
 * <br>
 * The maximum amount of data to be read.
 * </p>
 * The callback should return a string
 * with a length equal or smaller than the amount of data requested,
 * typically by reading it from the passed stream resource. It should
 * return an empty string to signal EOF.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_READFUNCTION', 20012);

/**
 * A string with the contents of the Referer: 
 * header to be used in a HTTP request.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_REFERER', 10016);

/**
 * The offset, in bytes, to resume a transfer from.
 * This option accepts any value that can be cast to a valid int.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_RESUME_FROM', 21);

/**
 * true to return the transfer as a string of the
 * return value of curl_exec instead of outputting
 * it directly.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_RETURNTRANSFER', 19913);

/**
 * A result of curl_share_init. Makes the cURL
 * handle to use the data from the shared handle.
 * Available as of cURL 7.10.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SHARE', 10100);

/**
 * The name of a file containing a PEM formatted certificate.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLCERT', 10025);

/**
 * The password required to use the
 * CURLOPT_SSLCERT certificate.
 * Available as of cURL 7.1.0 and deprecated as of cURL 7.17.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLCERTPASSWD', 10026);

/**
 * A string with the format of the certificate. Supported formats are:
 * <p>
 * PEM
 * DER
 * ENG
 * P12
 * </p>.
 * P12 (for PKCS#12-encoded files) is available as of OpenSSL 0.9.3.
 * Defaults to PEM.
 * Available as of cURL 7.9.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLCERTTYPE', 10086);

/**
 * The string identifier for the crypto engine of the private SSL key
 * specified in CURLOPT_SSLKEY.
 * Available as of cURL 7.9.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLENGINE', 10089);

/**
 * The string identifier for the crypto engine used for asymmetric crypto
 * operations.
 * Available as of cURL 7.9.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLENGINE_DEFAULT', 90);

/**
 * The name of a file containing a private SSL key.
 * Available as of cURL 7.9.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLKEY', 10087);

/**
 * The secret password needed to use the private SSL key specified in
 * CURLOPT_SSLKEY.
 * <p>
 * Since this option contains a sensitive password, remember to keep
 * the PHP script it is contained within safe.
 * </p>
 * Available as of cURL 7.9.3 and deprecated as of cURL 7.17.0.
 * <p>Since this option contains a sensitive password, remember to keep
 * the PHP script it is contained within safe.</p>
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLKEYPASSWD', 10026);

/**
 * The key type of the private SSL key specified in
 * CURLOPT_SSLKEY. Supported key types are:
 * <p>
 * PEM
 * DER
 * ENG
 * </p>.
 * Defaults to PEM.
 * Available as of cURL 7.9.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLKEYTYPE', 10088);

/**
 * One of
 * the CURL_SSLVERSION_&#42; constants.
 * It is better to not set this option and leave the defaults.
 * As setting this to
 * CURL_SSLVERSION_SSLv2
 * or
 * CURL_SSLVERSION_SSLv3
 * is very dangerous, given the known
 * vulnerabilities in SSLv2 and SSLv3.
 * Defaults to CURL_SSLVERSION_DEFAULT.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLVERSION', 32);

/**
 * A colon-separated string of ciphers to use
 * for the TLS 1.2 (1.1, 1.0) connection.
 * Available as of cURL 7.9.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSL_CIPHER_LIST', 10083);

/**
 * 2 to verify that a Common Name field or a Subject Alternate Name
 * field in the SSL peer certificate matches the provided hostname.
 * 0 to not check the names.
 * 1 should not be used.
 * In production environments the value of this option
 * should be kept at 2 (default value). Support for value 1 removed in cURL 7.28.1.
 * Available as of cURL 7.8.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSL_VERIFYHOST', 81);

/**
 * false to stop cURL from verifying the peer's
 * certificate. Alternate certificates to verify against can be
 * specified with the CURLOPT_CAINFO option
 * or a certificate directory can be specified with the
 * CURLOPT_CAPATH option.
 * Defaults to true as of cURL 7.10.
 * Default bundle of CA certificates installed as of cURL 7.10.
 * Available as of cURL 7.4.2.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSL_VERIFYPEER', 64);

/**
 * Accepts a file handle resource pointing to
 * an alternative location to output errors to instead of
 * STDERR.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_STDERR', 10037);

/**
 * Sets the maximum number of TCP keep-alive probes.
 * The default is 9.
 * Available as of PHP 8.4.0 and cURL 8.9.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TCP_KEEPCNT', 326);

/**
 * Set an array of strings to pass to the telnet negotiations.
 * The variables should be in the format &gt;option=value&lt;.
 * cURL supports the options TTYPE,
 * XDISPLOC and NEW_ENV.
 * Available as of cURL 7.7.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TELNETOPTIONS', 10070);

/**
 * Set how CURLOPT_TIMEVALUE is treated.
 * Use CURL_TIMECOND_IFMODSINCE to return the
 * page only if it has been modified since the time specified in
 * CURLOPT_TIMEVALUE. If it hasn't been modified,
 * a 304 Not Modified header will be returned
 * assuming CURLOPT_HEADER is true.
 * Use CURL_TIMECOND_IFUNMODSINCE for the reverse
 * effect. Use CURL_TIMECOND_NONE to ignore
 * CURLOPT_TIMEVALUE and always return the page.
 * CURL_TIMECOND_NONE is the default.
 * Prior to cURL 7.46.0 the default was
 * CURL_TIMECOND_IFMODSINCE.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TIMECONDITION', 33);

/**
 * The maximum number of seconds to allow cURL functions to execute.
 * Defaults to 0, meaning that functions never time out during transfer.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TIMEOUT', 13);

/**
 * The time in seconds since January 1st, 1970. The time will be used
 * by CURLOPT_TIMECONDITION.
 * Defaults to 0.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TIMEVALUE', 34);

/**
 * true to use ASCII mode for FTP transfers.
 * For LDAP, it retrieves data in plain text instead of HTML. On
 * Windows systems, it will not set STDOUT to binary
 * mode.
 * Defaults to false.
 * Available as of cURL 7.1.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TRANSFERTEXT', 53);

/**
 * true to keep sending the username and password
 * when following locations (using
 * CURLOPT_FOLLOWLOCATION), even when the
 * hostname has changed.
 * Defaults to false.
 * Available as of cURL 7.10.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_UNRESTRICTED_AUTH', 105);

/**
 * true to prepare for and perform an upload.
 * Defaults to false.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_UPLOAD', 46);

/**
 * The URL to fetch. This can also be set when initializing a
 * session with curl_init.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_URL', 10002);

/**
 * The contents of the User-Agent: header to be
 * used in a HTTP request.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_USERAGENT', 10018);

/**
 * A username and password formatted as
 * [username]:[password] to use for the
 * connection.
 * Available as cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_USERPWD', 10005);

/**
 * true to output verbose information. Writes
 * output to STDERR, or the file specified using
 * CURLOPT_STDERR.
 * Defaults to false.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_VERBOSE', 41);

/**
 * A callable with the following signature:
 * intcallback
 * resourcecurlHandle
 * stringdata
 * <p>
 * curlHandle
 * <br>
 * The cURL handle.
 * data
 * <br>
 * The data to be written.
 * </p>
 * The data must be saved by the callback
 * and the callback must return the exact number of bytes written
 * or the transfer will be aborted with an error.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_WRITEFUNCTION', 20011);

/**
 * Accepts a file handle resource to the file that the header part of the transfer is written to.
 * Available as of cURL 7.1.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_WRITEHEADER', 10029);

/**
 * A callable with the following signature:
 * intcallback
 * resourcecurlHandle
 * intbytesToDownload
 * intbytesDownloaded
 * intbytesToUpload
 * intbytesUploaded
 * <p>
 * curlHandle
 * <br>
 * The cURL handle.
 * bytesToDownload
 * <br>
 * The total number of bytes expected to be downloaded in this transfer.
 * bytesDownloaded
 * <br>
 * The number of bytes downloaded so far.
 * bytesToUpload
 * <br>
 * The total number of bytes expected to be uploaded in this transfer.
 * bytesUploaded
 * <br>
 * The number of bytes uploaded so far.
 * </p>
 * Return 1 to abort the transfer
 * and set a CURLE_ABORTED_BY_CALLBACK error.
 * Available as of PHP 8.2.0 and cURL 7.32.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_XFERINFOFUNCTION', 20219);

/**
 * Available as of PHP 8.4.0.
 * This option requires CURLOPT_VERBOSE option enabled.
 * A callable to replace the standard cURL verbose output.
 * This callback gets called during various stages of the request with verbose debug information.
 * The callback should match the following signature:
 * voidcallback
 * CurlHandlecurlHandle
 * inttype
 * stringdata
 * <p>
 * curlHandle
 * <br>
 * The cURL handle.
 * type
 * <br>
 * One of the following constants indicating the type of the data value:
 * <p>
 * CURLINFO_TEXT
 * (int)
 * <br>
 * Informational text.
 * CURLINFO_HEADER_IN
 * (int)
 * <br>
 * Header (or header-like) data received from the peer.
 * CURLINFO_HEADER_OUT
 * (int)
 * <br>
 * Header (or header-like) data sent to the peer.
 * CURLINFO_DATA_IN
 * (int)
 * <br>
 * Unprocessed protocol data received from the peer.
 * Even if the data is encoded or compressed, it is not provided decoded nor decompressed to this callback.
 * CURLINFO_DATA_OUT
 * (int)
 * <br>
 * Protocol data sent to the peer.
 * CURLINFO_SSL_DATA_IN
 * (int)
 * <br>
 * SSL/TLS (binary) data received from the peer.
 * CURLINFO_SSL_DATA_OUT
 * (int)
 * <br>
 * SSL/TLS (binary) data sent to the peer.
 * </p>
 * data
 * <br>
 * Verbose debug data of the type indicate by the type parameter.
 * </p>
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DEBUGFUNCTION', 20094);

/**
 * Informational text.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_TEXT', 0);

/**
 * Header (or header-like) data received from the peer.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_HEADER_IN', 1);

/**
 * Unprocessed protocol data received from the peer.
 * Even if the data is encoded or compressed, it is not provided decoded nor decompressed to this callback.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_DATA_IN', 3);

/**
 * Protocol data sent to the peer.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_DATA_OUT', 4);

/**
 * SSL/TLS (binary) data sent to the peer.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SSL_DATA_OUT', 6);

/**
 * SSL/TLS (binary) data received from the peer.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SSL_DATA_IN', 5);

/**
 * Aborted by callback. A callback returned "abort" to libcurl.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_ABORTED_BY_CALLBACK', 42);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_BAD_CALLING_ORDER', 44);

/**
 * Unrecognized transfer encoding.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_BAD_CONTENT_ENCODING', 61);

/**
 * The download could not be resumed because the specified offset was out of the file boundary.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_BAD_DOWNLOAD_RESUME', 36);

/**
 * A function was called with a bad parameter.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_BAD_FUNCTION_ARGUMENT', 43);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_BAD_PASSWORD_ENTERED', 46);

/**
 * Failed to connect to host or proxy.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_COULDNT_CONNECT', 7);

/**
 * Could not resolve host. The given remote host was not resolved.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_COULDNT_RESOLVE_HOST', 6);

/**
 * Could not resolve proxy. The given proxy host could not be resolved.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_COULDNT_RESOLVE_PROXY', 5);

/**
 * Early initialization code failed.
 * This is likely to be an internal error or problem,
 * or a resource problem where something fundamental could not get done at init time.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FAILED_INIT', 2);

/**
 * A file given with FILE:// could not be opened.
 * Most likely because the file path does not identify an existing file
 * or due to the lack of appropriate file permissions.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FILE_COULDNT_READ_FILE', 37);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_ACCESS_DENIED', 9);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_BAD_DOWNLOAD_RESUME', 36);

/**
 * An internal failure to lookup the host used for the new connection.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_CANT_GET_HOST', 15);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_CANT_RECONNECT', 16);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_COULDNT_GET_SIZE', 32);

/**
 * This was either a unexpected reply to a 'RETR' command
 * or a zero byte transfer complete.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_COULDNT_RETR_FILE', 19);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_COULDNT_SET_ASCII', 29);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_COULDNT_SET_BINARY', 17);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_COULDNT_STOR_FILE', 25);

/**
 * The FTP REST command returned error.
 * This should never happen if the server is sane.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_COULDNT_USE_REST', 31);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_PARTIAL_FILE', 18);

/**
 * The FTP PORT command returned error.
 * This mostly happens when a good enough address has not been specified for libcurl to use.
 * See CURLOPT_FTPPORT.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_PORT_FAILED', 30);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_QUOTE_ERROR', 21);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_USER_PASSWORD_INCORRECT', 10);

/**
 * FTP servers return a 227-line as a response to a PASV command.
 * If libcurl fails to parse that line, this return code is passed back.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_WEIRD_227_FORMAT', 14);

/**
 * After having sent the FTP password to the server, libcurl expects a proper reply.
 * This error code indicates that an unexpected code was returned.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_WEIRD_PASS_REPLY', 11);

/**
 * libcurl failed to get a sensible result back from the server
 * as a response to either a PASV or a EPSV command. The server is flawed.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_WEIRD_PASV_REPLY', 13);

/**
 * The server sent data libcurl could not parse.
 * This error code is known as CURLE_WEIRD_SERVER_REPLY
 * as of cURL 7.51.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_WEIRD_SERVER_REPLY', 8);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_WEIRD_USER_REPLY', 12);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_WRITE_ERROR', 20);

/**
 * Function not found. A required zlib function was not found.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FUNCTION_NOT_FOUND', 41);

/**
 * Nothing was returned from the server, and under the circumstances,
 * getting nothing is considered an error.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_GOT_NOTHING', 52);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_HTTP_NOT_FOUND', 22);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_HTTP_PORT_FAILED', 45);

/**
 * This is an odd error that mainly occurs due to internal confusion.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_HTTP_POST_ERROR', 34);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_HTTP_RANGE_ERROR', 33);

/**
 * This is returned if CURLOPT_FAILONERROR is set true
 * and the HTTP server returns an error code that is greater than or equal to 400.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_HTTP_RETURNED_ERROR', 22);

/**
 * LDAP cannot bind. LDAP bind operation failed.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_LDAP_CANNOT_BIND', 38);

/**
 * LDAP search failed.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_LDAP_SEARCH_FAILED', 39);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_LIBRARY_NOT_FOUND', 40);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_MALFORMAT_USER', 24);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_OBSOLETE', 50);

/**
 * All fine. Proceed as usual.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_OK', 0);

/**
 * Operation timeout.
 * The specified time-out period was reached according to the conditions.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_OPERATION_TIMEDOUT', 28);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_OPERATION_TIMEOUTED', 28);

/**
 * A memory allocation request failed.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_OUT_OF_MEMORY', 27);

/**
 * A file transfer was shorter or larger than expected.
 * This happens when the server first reports an expected transfer size,
 * and then delivers data that does not match the previously given size.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_PARTIAL_FILE', 18);

/**
 * There was a problem reading a local file or an error returned by the read callback.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_READ_ERROR', 26);

/**
 * Failure with receiving network data.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_RECV_ERROR', 56);

/**
 * Failed sending network data.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_SEND_ERROR', 55);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_SHARE_IN_USE', 57);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_SSL_CACERT', 60);

/**
 * Problem with the local client certificate.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_SSL_CERTPROBLEM', 58);

/**
 * Could not use specified cipher.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_SSL_CIPHER', 59);

/**
 * A problem occurred somewhere in the SSL/TLS handshake.
 * Reading the message in the error buffer provides more details on the problem.
 * Could be certificates (file formats, paths, permissions), passwords, and others.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_SSL_CONNECT_ERROR', 35);

/**
 * The specified crypto engine was not found.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_SSL_ENGINE_NOTFOUND', 53);

/**
 * Failed setting the selected SSL crypto engine as default.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_SSL_ENGINE_SETFAILED', 54);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_SSL_PEER_CERTIFICATE', 60);

/**
 * Failed to match the pinned key specified with
 * CURLOPT_PINNEDPUBLICKEY.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_SSL_PINNEDPUBKEYNOTMATCH', 90);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_TELNET_OPTION_SYNTAX', 49);

/**
 * Too many redirects. When following redirects, libcurl hit the maximum amount.
 * The limit can be set with CURLOPT_MAXREDIRS.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_TOO_MANY_REDIRECTS', 47);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_UNKNOWN_TELNET_OPTION', 48);

/**
 * The URL passed to libcurl used a protocol that libcurl does not support.
 * The issue might be a compile-time option that was not used,
 * a misspelled protocol string or just a protocol libcurl has no code for.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_UNSUPPORTED_PROTOCOL', 1);

/**
 * The URL was not properly formatted.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_URL_MALFORMAT', 3);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_URL_MALFORMAT_USER', 4);

/**
 * An error occurred when writing received data to a local file,
 * or an error was returned to libcurl from a write callback.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_WRITE_ERROR', 23);

/**
 * Time in seconds it took to establish the connection
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_CONNECT_TIME', 3145733);

/**
 * Content length of download, read from Content-Length: field
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_CONTENT_LENGTH_DOWNLOAD', 3145743);

/**
 * Specified size of upload
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_CONTENT_LENGTH_UPLOAD', 3145744);

/**
 * Content-Type of the requested document.
 * NULL indicates server did not send valid Content-Type header
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_CONTENT_TYPE', 1048594);

/**
 * Last effective URL
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_EFFECTIVE_URL', 1048577);

/**
 * Remote time of the retrieved document, with the CURLOPT_FILETIME enabled; if -1 is returned the time of the document is unknown
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_FILETIME', 2097166);

/**
 * The request string sent. For this to work, add the CURLINFO_HEADER_OUT option to the handle by calling curl_setopt
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_HEADER_OUT', 2);

/**
 * Total size of all headers received
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_HEADER_SIZE', 2097163);

/**
 * The last response code.
 * As of cURL 7.10.8, this is a legacy alias of CURLINFO_RESPONSE_CODE.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_HTTP_CODE', 2097154);

/**
 * The last enum value in the underlying CURLINFO enum
 * in libcurl.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_LASTONE', 70);

/**
 * Time in seconds until name resolving was complete
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_NAMELOOKUP_TIME', 3145732);

/**
 * Time in seconds from start until just before file transfer begins
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_PRETRANSFER_TIME', 3145734);

/**
 * Private data associated with this cURL handle, previously set with the CURLOPT_PRIVATE option of curl_setopt
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_PRIVATE', 1048597);

/**
 * Number of redirects, with the CURLOPT_FOLLOWLOCATION option enabled
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_REDIRECT_COUNT', 2097172);

/**
 * Time in seconds of all redirection steps before final transaction was started, with the CURLOPT_FOLLOWLOCATION option enabled
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_REDIRECT_TIME', 3145747);

/**
 * Total size of issued requests, currently only for HTTP requests
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_REQUEST_SIZE', 2097164);

/**
 * Total number of bytes downloaded
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SIZE_DOWNLOAD', 3145736);

/**
 * Total number of bytes uploaded
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SIZE_UPLOAD', 3145735);

/**
 * Average download speed
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SPEED_DOWNLOAD', 3145737);

/**
 * Average upload speed
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SPEED_UPLOAD', 3145738);

/**
 * Result of SSL certification verification requested by setting CURLOPT_SSL_VERIFYPEER
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SSL_VERIFYRESULT', 2097165);

/**
 * Time in seconds until the first byte is about to be transferred
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_STARTTRANSFER_TIME', 3145745);

/**
 * Total transaction time in seconds for last transfer
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_TOTAL_TIME', 3145731);

/**
 * Get the last used HTTP method.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_EFFECTIVE_METHOD', 1048634);

/**
 * Default built-in CA path string.
 * Available as of PHP 8.3.0 and cURL 7.84.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_CAPATH', 1048638);

/**
 * Default built-in CA certificate path.
 * Available as of PHP 8.3.0 and cURL 7.84.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_CAINFO', 1048637);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLMSG_DONE', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLVERSION_NOW', 11);

/**
 * An easy handle was not good/valid. It could mean that it is not an easy handle at all, or possibly that the handle already is in use by this or another multi handle.
 * Available as of cURL 7.9.6.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLM_BAD_EASY_HANDLE', 2);

/**
 * The passed-in handle is not a valid multi handle.
 * Available as of cURL 7.9.6.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLM_BAD_HANDLE', 1);

/**
 * As of cURL 7.20.0, this constant is not used.
 * Before cURL 7.20.0, this status could be returned by
 * curl_multi_exec when curl_multi_select
 * or a similar function was called before it returned any other constant.
 * Available as of cURL 7.9.6.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLM_CALL_MULTI_PERFORM', -1);

/**
 * Internal libcurl error.
 * Available as of cURL 7.9.6.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLM_INTERNAL_ERROR', 4);

/**
 * No errors.
 * Available as of cURL 7.9.6.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLM_OK', 0);

/**
 * Ran out of memory while processing multi handles.
 * Available as of cURL 7.9.6.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLM_OUT_OF_MEMORY', 3);

/**
 * An easy handle already added to a multi handle was attempted to get added a second time.
 * Available as of cURL 7.32.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLM_ADDED_ALREADY', 7);

/**
 * Available as of cURL 7.10.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROXY_HTTP', 0);

/**
 * Available as of cURL 7.10.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROXY_SOCKS4', 4);

/**
 * Available as of cURL 7.10.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROXY_SOCKS5', 5);

/**
 * Available as of cURL 7.10.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSHOPT_NONE', 0);

/**
 * Specifies a type of data that should be shared.
 * Available as of cURL 7.10.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSHOPT_SHARE', 1);

/**
 * Specifies a type of data that will be no longer shared.
 * Available as of cURL 7.10.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSHOPT_UNSHARE', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_HTTP_VERSION_1_0', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_HTTP_VERSION_1_1', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_HTTP_VERSION_NONE', 0);

/**
 * Shares/unshares cookie data.
 * Available as of cURL 7.10.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_LOCK_DATA_COOKIE', 2);

/**
 * Shares/unshares DNS cache.
 * Note that when you use cURL multi handles,
 * all handles added to the same multi handle will share DNS cache by default.
 * Available as of cURL 7.10.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_LOCK_DATA_DNS', 3);

/**
 * Shares/unshares SSL session IDs, reducing the time spent
 * on the SSL handshake when reconnecting to the same server.
 * Note that SSL session IDs are reused within the same handle by default.
 * Available as of cURL 7.10.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_LOCK_DATA_SSL_SESSION', 4);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_NETRC_IGNORED', 0);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_NETRC_OPTIONAL', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_NETRC_REQUIRED', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_DEFAULT', 0);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_SSLv2', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_SSLv3', 3);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_TLSv1', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_TIMECOND_IFMODSINCE', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_TIMECOND_IFUNMODSINCE', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_TIMECOND_LASTMOD', 3);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_TIMECOND_NONE', 0);

/**
 * Asynchronous DNS resolves.
 * Available as of PHP 7.3.0 and cURL 7.10.7
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_ASYNCHDNS', 128);

/**
 * Character conversions supported.
 * Available as of PHP 7.3.0 and cURL 7.15.4
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_CONV', 4096);

/**
 * Built with debug capabilities.
 * Available as of PHP 7.3.0 and cURL 7.10.6
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_DEBUG', 64);

/**
 * Negotiate auth is supported.
 * Available as of PHP 7.3.0 and cURL 7.10.6 (deprecated as of cURL 7.38.0)
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_GSSNEGOTIATE', 32);

/**
 * Internationalized Domain Names are supported.
 * Available as of PHP 7.3.0 and cURL 7.12.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_IDN', 1024);

/**
 * IPv6-enabled.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_IPV6', 1);

/**
 * Kerberos V4 auth is supported.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_KERBEROS4', 2);

/**
 * Supports files larger than 2GB.
 * Available as of cURL 7.33.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_LARGEFILE', 512);

/**
 * libz features are present.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_LIBZ', 8);

/**
 * NTLM auth is supported.
 * Available as of PHP 7.3.0 and cURL 7.10.6
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_NTLM', 16);

/**
 * SPNEGO auth is supported.
 * Available as of PHP 7.3.0 and cURL 7.10.8
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_SPNEGO', 256);

/**
 * SSL options are present.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_SSL', 4);

/**
 * Built against Windows SSPI.
 * Available as of PHP 7.3.0 and cURL 7.13.2
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_SSPI', 2048);

/**
 * A bitmask of HTTP authentication method(s) to use. The options are:
 * <p>
 * CURLAUTH_BASIC
 * CURLAUTH_DIGEST
 * CURLAUTH_GSSNEGOTIATE
 * CURLAUTH_NTLM
 * CURLAUTH_AWS_SIGV4
 * CURLAUTH_ANY
 * CURLAUTH_ANYSAFE
 * </p>.
 * If more than one method is used, cURL will poll the server to see
 * what methods it supports and pick the best one.
 * CURLAUTH_ANY sets all bits. cURL will automatically select
 * the one it finds most secure.
 * CURLAUTH_ANYSAFE sets all bits except CURLAUTH_BASIC.
 * cURL will automatically select the one it finds most secure.
 * Available as of cURL 7.10.6.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HTTPAUTH', 107);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_ANY', -17);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_ANYSAFE', -18);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_BASIC', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_DIGEST', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_GSSNEGOTIATE', 4);

/**
 * Available as of cURL 7.10.6.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_NONE', 0);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_NTLM', 8);

/**
 * The CONNECT response code
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_HTTP_CONNECTCODE', 2097174);

/**
 * Set to true to create missing directories when an FTP operation
 * encounters a path that currently doesn't exist.
 * Available as of cURL 7.10.7.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTP_CREATE_MISSING_DIRS', 110);

/**
 * A bitmask of the HTTP authentication method(s)
 * (CURLAUTH_&#42; constants)
 * to use for the proxy connection.
 * For proxy authentication, only CURLAUTH_BASIC and
 * CURLAUTH_NTLM are currently supported.
 * Defaults to CURLAUTH_BASIC.
 * Available as of cURL 7.10.7.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXYAUTH', 111);

/**
 * Maximum file size exceeded.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FILESIZE_EXCEEDED', 63);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_LDAP_INVALID_URL', 62);

/**
 * Bitmask indicating the authentication method(s) available according to the previous response
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_HTTPAUTH_AVAIL', 2097175);

/**
 * The last response code.
 * Available as of cURL 7.10.8
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_RESPONSE_CODE', 2097154);

/**
 * Bitmask indicating the proxy authentication method(s) available according to the previous response
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_PROXYAUTH_AVAIL', 2097176);

/**
 * A timeout in seconds cURL will wait for a response from an FTP server.
 * This option overrides CURLOPT_TIMEOUT.
 * This option accepts any value that can be cast to a valid int.
 * This option name is replaced with CURLOPT_SERVER_RESPONSE_TIMEOUT,
 * available as of PHP 8.4.0.
 * Available as of cURL 7.10.8 and deprecated as of cURL 7.85.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTP_RESPONSE_TIMEOUT', 112);

/**
 * A timeout in seconds cURL will wait for a response from an
 * FTP, SFTP, IMAP,
 * SCP, SMTP, or a POP3 server.
 * This option replaces the existing CURLOPT_FTP_RESPONSE_TIMEOUT
 * option which is deprecated in cURL 7.85.0.
 * Available as of PHP 8.4.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SERVER_RESPONSE_TIMEOUT', 112);

/**
 * Allows an application to select what kind of IP addresses to use when
 * resolving host names. This is only interesting when using host names that
 * resolve addresses using more than one version of IP.
 * Set to one of the
 * CURL_IPRESOLVE_&#42; constants.
 * Defaults to CURL_IPRESOLVE_WHATEVER.
 * Available as of cURL 7.10.8.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_IPRESOLVE', 113);

/**
 * Sets the maximum accepted size (in bytes) of a file to download.
 * If the file requested is found larger than this value,
 * the transfer is aborted
 * and CURLE_FILESIZE_EXCEEDED is returned.
 * Passing 0 disables this option,
 * and passing a negative size returns a
 * CURLE_BAD_FUNCTION_ARGUMENT.
 * If the file size is not known prior to the start of download,
 * this option has no effect.
 * For setting a size limit above 2GB,
 * CURLOPT_MAXFILESIZE_LARGE should be used.
 * As of cURL 8.4.0, this option also stops ongoing transfers
 * if they reach this threshold.
 * This option accepts any value that can be cast to a valid int.
 * Defaults to 0.
 * Available as of cURL 7.10.8.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_MAXFILESIZE', 114);

/**
 * Use only IPv4 addresses when establishing a connection
 * or choosing one from the connection pool.
 * Available as of cURL 7.10.8.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_IPRESOLVE_V4', 1);

/**
 * Use only IPv6 addresses when establishing a connection
 * or choosing one from the connection pool.
 * Available as of cURL 7.10.8.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_IPRESOLVE_V6', 2);

/**
 * Use addresses of all IP versions allowed by the system.
 * Available as of cURL 7.10.8.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_IPRESOLVE_WHATEVER', 0);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_SSL_FAILED', 64);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTPSSL_ALL', 3);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTPSSL_CONTROL', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTPSSL_NONE', 0);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTPSSL_TRY', 1);

/**
 * Available as of cURL 7.11.0 and deprecated as of cURL 7.16.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTP_SSL', 119);

/**
 * Set a string containing the full path name to a .netrc file.
 * If this option is omitted and CURLOPT_NETRC is set,
 * cURL checks for a .netrc file
 * in the current user's home directory.
 * Available as of cURL 7.11.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_NETRC_FILE', 10118);

/**
 * The maximum file size in bytes allowed to download. If the file requested is found larger than this value,
 * the transfer will not start and CURLE_FILESIZE_EXCEEDED will be returned.
 * The file size is not always known prior to download, and for such files this option has no effect even if
 * the file transfer ends up being larger than this given limit.
 * This option accepts any value that can be cast to a valid int.
 * Available as of PHP 8.2.0 and cURL 7.11.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_MAXFILESIZE_LARGE', 30117);

/**
 * true to disable TCP's Nagle algorithm, which tries to minimize
 * the number of small packets on the network.
 * Defaults to true.
 * Available as of cURL 7.11.2.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TCP_NODELAY', 121);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTPAUTH_DEFAULT', 0);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTPAUTH_SSL', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTPAUTH_TLS', 2);

/**
 * Set the FTP over SSL authentication method (if activated) to any of the
 * CURLFTPAUTH_&#42; constants.
 * Defaults to CURLFTPAUTH_DEFAULT.
 * Available as of cURL 7.12.2.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTPSSLAUTH', 129);

/**
 * Pass a string that will be sent as account information over FTP
 * (using the ACCT command) after username and password has been provided
 * to the server.
 * Set to null to disable sending the account information.
 * Defaults to null.
 * Available as of cURL 7.13.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTP_ACCOUNT', 10134);

/**
 * Errno from a connect failure. The number is OS and system specific.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_OS_ERRNO', 2097177);

/**
 * Number of connections curl had to create to achieve the previous transfer
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_NUM_CONNECTS', 2097178);

/**
 * OpenSSL crypto-engines supported
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SSL_ENGINES', 4194331);

/**
 * All known cookies
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_COOKIELIST', 4194332);

/**
 * A cookie string (i.e. a single line in Netscape/Mozilla format, or a regular
 * HTTP-style Set-Cookie header) adds that single cookie to the internal cookie store.
 * <p>
 * ALL
 * erases all cookies held in memory
 * SESS
 * erases all session cookies held in memory
 * FLUSH
 * writes all known cookies to the file specified by CURLOPT_COOKIEJAR
 * RELOAD
 * loads all cookies from the files specified by CURLOPT_COOKIEFILE
 * </p>.
 * Available as of cURL 7.14.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_COOKIELIST', 10135);

/**
 * If set to 1,
 * ignore the Content-Length header in the HTTP response
 * and ignore asking for or relying on it for FTP transfers.
 * Defaults to 0.
 * Available as of cURL 7.14.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_IGNORE_CONTENT_LENGTH', 136);

/**
 * If this option is set to 1
 * cURL will not use the IP address the server suggests
 * in its 227-response to cURL's PASV command
 * but will use the IP address it used for the connection.
 * The port number received from the 227-response will not be ignored by cURL.
 * Defaults to 1 as of cURL 7.74.0
 * and 0 prior to that.
 * Available as of cURL 7.15.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTP_SKIP_PASV_IP', 137);

/**
 * Tell cURL which method to use to reach a file on a FTP(S) server. Possible values are
 * any of the CURLFTPMETHOD_&#42; constants.
 * Defaults to CURLFTPMETHOD_MULTICWD.
 * Available as of cURL 7.15.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTP_FILEMETHOD', 138);

/**
 * true tells the library to perform all the required proxy authentication
 * and connection setup, but no data transfer. This option is implemented for
 * HTTP, SMTP and POP3.
 * Defaults to false.
 * Available as of cURL 7.15.2.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CONNECT_ONLY', 141);

/**
 * Sets the local port number of the socket used for the connection.
 * This option accepts any value that can be cast to a valid int.
 * Defaults to 0.
 * Available as of cURL 7.15.2.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_LOCALPORT', 139);

/**
 * The number of attempts cURL makes to find a working local port number,
 * starting with the one set with CURLOPT_LOCALPORT.
 * This option accepts any value that can be cast to a valid int.
 * Defaults to 1.
 * Available as of cURL 7.15.2.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_LOCALPORTRANGE', 140);

/**
 * Available as of PHP 8.2.0 and cURL 7.15.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTPMETHOD_DEFAULT', 0);

/**
 * Do a single CWD operation
 * for each path part in the given URL.
 * Available as of cURL 7.15.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTPMETHOD_MULTICWD', 1);

/**
 * libcurl makes no CWD at all.
 * libcurl does SIZE, RETR,
 * STOR etc.
 * and gives a full path to the server for all these commands.
 * Available as of cURL 7.15.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTPMETHOD_NOCWD', 2);

/**
 * libcurl does one CWD with the full target directory
 * and then operates on the file like in the multicwd case.
 * Available as of cURL 7.15.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTPMETHOD_SINGLECWD', 3);

/**
 * Entry path in FTP server
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_FTP_ENTRY_PATH', 1048606);

/**
 * Pass a string that will be used to try to authenticate over FTP
 * if the USER/PASS negotiation fails.
 * Available as of cURL 7.15.5.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTP_ALTERNATIVE_TO_USER', 10147);

/**
 * If a download exceeds this speed (counted in bytes per second) on
 * cumulative average during the transfer, the transfer will pause to
 * keep the average rate less than or equal to the parameter value.
 * Defaults to unlimited speed.
 * This option accepts any value that can be cast to a valid int.
 * Available as of cURL 7.15.5.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_MAX_RECV_SPEED_LARGE', 30146);

/**
 * If an upload exceeds this speed (counted in bytes per second) on
 * cumulative average during the transfer, the transfer will pause to
 * keep the average rate less than or equal to the parameter value.
 * Defaults to unlimited speed.
 * This option accepts any value that can be cast to a valid int.
 * Available as of cURL 7.15.5.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_MAX_SEND_SPEED_LARGE', 30145);

/**
 * Problem with reading the SSL CA cert.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_SSL_CACERT_BADFILE', 77);

/**
 * Set to 0 to disable and 1 to enable
 * SSL session-ID caching.
 * By default all transfers are done using the cache enabled.
 * Available as of cURL 7.16.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSL_SESSIONID_CACHE', 150);

/**
 * Pass 1 to enable or 0 to disable. Enabling pipelining on a multi
 * handle will make it attempt to perform HTTP Pipelining as far as
 * possible for transfers using this handle. This means that adding
 * a second request that can use an already existing connection will "pipe"
 * the second request on the same connection.
 * As of cURL 7.43.0, the value is a bitmask,
 * and passing 2 will try to multiplex the new
 * transfer over an existing HTTP/2 connection.
 * Passing 3 instructs cURL to ask for pipelining and multiplexing
 * independently of each other.
 * As of cURL 7.62.0, setting the pipelining bit has no effect.
 * Instead of integer literals, the CURLPIPE_&#42; constants can also be used.
 * Available as of cURL 7.16.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLMOPT_PIPELINING', 3);

/**
 * An unspecified error occurred during the SSH session.
 * Available as of cURL 7.16.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_SSH', 79);

/**
 * This option makes cURL use CCC (Clear Command Channel)
 * which shuts down the SSL/TLS layer after authenticating
 * making the rest of the control channel communication unencrypted.
 * Use one of the CURLFTPSSL_CCC_&#42; constants.
 * Defaults to CURLFTPSSL_CCC_NONE.
 * Available as of cURL 7.16.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTP_SSL_CCC', 154);

/**
 * A bitmask consisting of one or more of the following constants:
 * <p>
 * CURLSSH_AUTH_PUBLICKEY
 * CURLSSH_AUTH_PASSWORD
 * CURLSSH_AUTH_HOST
 * CURLSSH_AUTH_KEYBOARD
 * CURLSSH_AUTH_AGENT
 * CURLSSH_AUTH_ANY
 * </p>.
 * Defaults to CURLSSH_AUTH_ANY.
 * Available as of cURL 7.16.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSH_AUTH_TYPES', 151);

/**
 * The file name for a private key. If not used, cURL defaults to
 * $HOME/.ssh/id_dsa if the HOME environment variable is set,
 * and just id_dsa in the current directory if HOME is not set.
 * If the file is password-protected, set the password with
 * CURLOPT_KEYPASSWD.
 * Available as of cURL 7.16.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSH_PRIVATE_KEYFILE', 10153);

/**
 * The file name for a public key. If not used, cURL defaults to
 * $HOME/.ssh/id_dsa.pub if the HOME environment variable is set,
 * and just id_dsa.pub in the current directory if HOME is not set.
 * Available as of cURL 7.16.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSH_PUBLIC_KEYFILE', 10152);

/**
 * Initiate the shutdown and wait for a reply.
 * Available as of cURL 7.16.2.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTPSSL_CCC_ACTIVE', 2);

/**
 * Do not attempt to use CCC (Clear Command Channel).
 * Available as of cURL 7.16.2.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTPSSL_CCC_NONE', 0);

/**
 * Do not initiate the shutdown, but wait for the server to do it.
 * Do not send a reply.
 * Available as of cURL 7.16.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTPSSL_CCC_PASSIVE', 1);

/**
 * The number of milliseconds to wait while trying to connect.
 * Use 0 to wait indefinitely.
 * If cURL is built to use the standard system name resolver, that
 * portion of the connect will still use full-second resolution for
 * timeouts with a minimum timeout allowed of one second.
 * This option accepts any value that can be cast to a valid int.
 * Defaults to 300000.
 * Available as of cURL 7.16.2.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CONNECTTIMEOUT_MS', 156);

/**
 * false to get the raw HTTP response body.
 * Available as of cURL 7.16.2.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HTTP_CONTENT_DECODING', 158);

/**
 * If set to 0, transfer decoding is disabled.
 * If set to 1, transfer decoding is enabled.
 * cURL does chunked transfer decoding by default
 * unless this option is set to 0.
 * Defaults to 1.
 * Available as of cURL 7.16.2.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HTTP_TRANSFER_DECODING', 157);

/**
 * The maximum number of milliseconds to allow cURL functions to
 * execute.
 * If cURL is built to use the standard system name resolver, that
 * portion of the connect will still use full-second resolution for
 * timeouts with a minimum timeout allowed of one second.
 * Defaults to 0, meaning that functions never time out during transfer.
 * Available as of cURL 7.16.2.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TIMEOUT_MS', 155);

/**
 * Specifies the maximum amount of simultaneously open connections
 * that libcurl may cache.
 * By default the size will be enlarged to fit four times the number
 * of handles added via curl_multi_add_handle.
 * When the cache is full, curl closes the oldest one in the cache
 * to prevent the number of open connections from increasing.
 * Available as of cURL 7.16.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLMOPT_MAXCONNECTS', 6);

/**
 * Set the kerberos security level for FTP and also enables kerberos awareness.
 * This should be set to one of the following strings:
 * <p>
 * clear
 * safe
 * confidential
 * private
 * </p>.
 * If the string is set but does not match one of these,
 * private is used.
 * Setting this option to null will disable kerberos support for FTP.
 * Defaults to null.
 * Available as of cURL 7.16.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_KRBLEVEL', 10063);

/**
 * Sets the value of the permissions (int) that is set on newly created directories
 * on the remote server. The default value is 0755.
 * The only protocols that can use this are
 * sftp://, scp://
 * and file://.
 * Available as of cURL 7.16.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_NEW_DIRECTORY_PERMS', 160);

/**
 * Sets the value of the permissions (as an int) that are set on newly created files
 * on the remote server. The default value is 0644.
 * The only protocols that can use this are
 * sftp://, scp://
 * and file://.
 * Available as of cURL 7.16.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_NEW_FILE_PERMS', 159);

/**
 * Setting this option to 1 will cause FTP uploads
 * to append to the remote file instead of overwriting it.
 * Defaults to 0.
 * Available as of cURL 7.17.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_APPEND', 50);

/**
 * Setting this option to 1 will have different effects
 * based on the protocol it is used with.
 * FTP and SFTP based URLs will list only the names of files in a directory.
 * POP3 will list the email message or messages on the POP3 server.
 * For FILE, this option has no effect
 * as directories are always listed in this mode.
 * Using this option with CURLOPT_WILDCARDMATCH
 * will prevent the latter from having any effect.
 * Defaults to 0.
 * Available as of cURL 7.17.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DIRLISTONLY', 48);

/**
 * Sets the desired level of SSL/TLS for the transfer
 * when using FTP, SMTP, POP3, IMAP, etc.
 * These are all protocols that start out plain text
 * and get "upgraded" to SSL using the STARTTLS command.
 * Set to one of the
 * CURLUSESSL_&#42; constants.
 * Available as of cURL 7.17.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_USE_SSL', 119);

/**
 * Require SSL for all communication
 * or fail with CURLE_USE_SSL_FAILED.
 * Available as of cURL 7.17.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLUSESSL_ALL', 3);

/**
 * Require SSL for the control connection
 * or fail with CURLE_USE_SSL_FAILED.
 * Available as of cURL 7.17.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLUSESSL_CONTROL', 2);

/**
 * Do not attempt to use SSL.
 * Available as of cURL 7.17.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLUSESSL_NONE', 0);

/**
 * Try using SSL, proceed as normal otherwise.
 * Note that server may close the connection if the negotiation does not succeed.
 * Available as of cURL 7.17.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLUSESSL_TRY', 1);

/**
 * A string containing 32 hexadecimal digits which should contain the
 * MD5 checksum of the remote host's public key, and cURL will reject
 * the connection to the host unless the md5sums match.
 * This option is only for SCP and SFTP transfers.
 * Available as of cURL 7.17.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSH_HOST_PUBLIC_KEY_MD5', 10162);

/**
 * Set to 1 to set the transfer mode (binary or ASCII)
 * for FTP transfers done via an HTTP proxy, by appending
 * type=a or type=i to the URL.
 * Without this setting or it being set to 0,
 * CURLOPT_TRANSFERTEXT has no effect
 * when doing FTP via a proxy.
 * Defaults to 0.
 * Available as of cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_TRANSFER_MODE', 166);

/**
 * Pause sending and receiving data.
 * Available as of cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPAUSE_ALL', 5);

/**
 * Unpause sending and receiving data.
 * Available as of cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPAUSE_CONT', 0);

/**
 * Pause receiving data.
 * Available as of cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPAUSE_RECV', 1);

/**
 * Unpause receiving data.
 * Available as of cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPAUSE_RECV_CONT', 0);

/**
 * Pause sending data.
 * Available as of cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPAUSE_SEND', 4);

/**
 * Unpause sending data.
 * Available as of cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPAUSE_SEND_CONT', 0);

/**
 * Available as of cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_READFUNC_PAUSE', 268435457);

/**
 * Available as of cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_WRITEFUNC_PAUSE', 268435457);

/**
 * Available as of cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROXY_SOCKS4A', 6);

/**
 * Available as of cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROXY_SOCKS5_HOSTNAME', 7);

/**
 * With the CURLOPT_FOLLOWLOCATION option disabled: redirect URL found in the last transaction, that should be requested manually next. With the CURLOPT_FOLLOWLOCATION option enabled: this is empty. The redirect URL in this case is available in CURLINFO_EFFECTIVE_URL
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_REDIRECT_URL', 1048607);

/**
 * Time in seconds it took from the start until the SSL/SSH connect/handshake to the remote host was completed
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_APPCONNECT_TIME', 3145761);

/**
 * IP address of the most recent connection
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_PRIMARY_IP', 1048608);

/**
 * The scope id value to use when connecting to IPv6 addresses.
 * This option accepts any value that can be cast to a valid int.
 * Defaults to 0.
 * Available as of cURL 7.19.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_ADDRESS_SCOPE', 171);

/**
 * Pass a string naming a file with the concatenation of
 * CRL (Certificate Revocation List) (in PEM format)
 * to use in the certificate validation that occurs during the SSL exchange.
 * When cURL is built to use GnuTLS,
 * there is no way to influence the use of CRL passed
 * to help in the verification process.
 * When cURL is built with OpenSSL support,
 * X509_V_FLAG_CRL_CHECK
 * and X509_V_FLAG_CRL_CHECK_ALL are both set,
 * requiring CRL check against all the elements of the certificate chain
 * if a CRL file is passed.
 * Also note that CURLOPT_CRLFILE implies
 * CURLSSLOPT_NO_PARTIALCHAIN
 * as of cURL 7.71.0 due to an OpenSSL bug.
 * Available as of cURL 7.19.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CRLFILE', 10169);

/**
 * If set to a string naming a file holding a CA certificate in PEM format,
 * an additional check against the peer certificate is performed
 * to verify the issuer is indeed the one associated
 * with the certificate provided by the option.
 * For the result of the check to be considered a failure,
 * this option should be used in combination with the
 * CURLOPT_SSL_VERIFYPEER option.
 * Available as of cURL 7.19.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_ISSUERCERT', 10170);

/**
 * Set to a string with the password required to use the CURLOPT_SSLKEY
 * or CURLOPT_SSH_PRIVATE_KEYFILE private key.
 * Setting this option to null disables using a password for these options.
 * Available as of cURL 7.17.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_KEYPASSWD', 10026);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSH_AUTH_ANY', -1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSH_AUTH_DEFAULT', -1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSH_AUTH_HOST', 4);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSH_AUTH_KEYBOARD', 8);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSH_AUTH_NONE', 0);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSH_AUTH_PASSWORD', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSH_AUTH_PUBLICKEY', 1);

/**
 * TLS certificate chain
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_CERTINFO', 4194338);

/**
 * true to output SSL certification information to STDERR
 * on secure transfers.
 * Requires CURLOPT_VERBOSE to be on to have an effect.
 * Defaults to false.
 * Available as of cURL 7.19.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CERTINFO', 172);

/**
 * Set to a string with the password to use in authentication.
 * Available as of cURL 7.19.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PASSWORD', 10174);

/**
 * Set to a bitmask of CURL_REDIR_POST_301,
 * CURL_REDIR_POST_302 and CURL_REDIR_POST_303
 * if the HTTP POST method should be maintained
 * when CURLOPT_FOLLOWLOCATION is set and a
 * specific type of redirect occurs.
 * Available as of cURL 7.19.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_POSTREDIR', 161);

/**
 * Set a string with the password to be used for authentication with the proxy.
 * Available as of cURL 7.19.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXYPASSWORD', 10176);

/**
 * Set a string with the username to be used for authentication with the proxy.
 * Available as of cURL 7.19.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXYUSERNAME', 10175);

/**
 * The user name to use in authentication.
 * Available as of cURL 7.19.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_USERNAME', 10173);

/**
 * Available as of PHP 7.0.7 and cURL 7.18.2
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_REDIR_POST_301', 1);

/**
 * Available as of PHP 7.0.7 and cURL 7.18.2
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_REDIR_POST_302', 2);

/**
 * Available as of PHP 7.0.7 and cURL 7.18.2
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_REDIR_POST_ALL', 7);

/**
 * Use HTTP Digest authentication with an IE flavor.
 * Available as of cURL 7.19.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_DIGEST_IE', 16);

/**
 * Info on unmet time conditional
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_CONDITION_UNMET', 2097187);

/**
 * Set a <p>string
 * is a single &#42; character which matches all hosts,
 * effectively disabling the proxy.
 * Setting this option to an empty string enables the proxy for all hostnames.
 * Since cURL 7.86.0, IP addresses set with this option
 * can be provided using CIDR notation.
 * Available as of cURL 7.19.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_NOPROXY', 10177);

/**
 * Bitmask of CURLPROTO_&#42; values.
 * If used, this bitmask limits what protocols cURL may use in the transfer.
 * Defaults to CURLPROTO_ALL, ie. cURL will accept all protocols it supports.
 * See also CURLOPT_REDIR_PROTOCOLS.
 * Available as of cURL 7.19.4 and deprecated as of cURL 7.85.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROTOCOLS', 181);

/**
 * Bitmask of CURLPROTO_&#42; values
 * which limit what protocols cURL may use in a transfer that it follows to in
 * a redirect when CURLOPT_FOLLOWLOCATION is enabled.
 * This allows limiting specific transfers to only be allowed to use a subset
 * of protocols in redirections.
 * As of cURL 7.19.4, by default cURL will allow all protocols
 * except for FILE and SCP.
 * Prior to cURL 7.19.4, cURL would unconditionally follow to all supported protocols.
 * See also CURLOPT_PROTOCOLS for protocol constant values.
 * Available as of cURL 7.19.4 and deprecated as of cURL 7.85.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_REDIR_PROTOCOLS', 182);

/**
 * Set to 1 to enable and 0 to disable
 * the unprotected exchange of the protection mode negotiation
 * as part of the GSSAPI negotiation.
 * Available as of cURL 7.19.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SOCKS5_GSSAPI_NEC', 180);

/**
 * Set a string holding the name of the SOCKS5 service.
 * Defaults to rcmd.
 * Available as of cURL 7.19.4 and deprecated as of cURL 7.49.0.
 * Use CURLOPT_PROXY_SERVICE_NAME instead.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SOCKS5_GSSAPI_SERVICE', 10179);

/**
 * Set the blocksize to use for TFTP data transmission.
 * Valid range is 8-65464 bytes.
 * The default of 512 bytes is used if this option is not specified.
 * The specified block size is only used if supported by the remote server.
 * If the server does not return an option acknowledgment
 * or returns an option acknowledgment with no block size,
 * the default of 512 bytes is used.
 * Available as of cURL 7.19.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TFTP_BLKSIZE', 178);

/**
 * Available as of cURL 7.19.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_ALL', -1);

/**
 * Available as of cURL 7.19.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_DICT', 512);

/**
 * Available as of cURL 7.19.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_FILE', 1024);

/**
 * Available as of cURL 7.19.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_FTP', 4);

/**
 * Available as of cURL 7.19.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_FTPS', 8);

/**
 * Available as of cURL 7.19.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_HTTP', 1);

/**
 * Available as of cURL 7.19.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_HTTPS', 2);

/**
 * Available as of cURL 7.19.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_LDAP', 128);

/**
 * Available as of cURL 7.19.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_LDAPS', 256);

/**
 * Available as of cURL 7.19.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_SCP', 16);

/**
 * Available as of cURL 7.19.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_SFTP', 32);

/**
 * Available as of cURL 7.19.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_TELNET', 64);

/**
 * Available as of cURL 7.19.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_TFTP', 2048);

/**
 * Available as of PHP 7.0.7 and cURL 7.19.3
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROXY_HTTP_1_0', 1);

/**
 * Available as of PHP 7.0.7 and cURL 7.19.3
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTP_CREATE_DIR', 1);

/**
 * Available as of PHP 7.0.7 and cURL 7.19.3
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTP_CREATE_DIR_NONE', 0);

/**
 * Available as of PHP 7.0.7 and cURL 7.19.3
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTP_CREATE_DIR_RETRY', 2);

/**
 * Debug memory tracking supported.
 * Available as of PHP 7.3.6 and cURL 7.19.6
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_CURLDEBUG', 8192);

/**
 * Set to the filename of the known_host file to use
 * which should use the OpenSSH file format as supported by libssh2.
 * Available as of cURL 7.19.6.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSH_KNOWNHOSTS', 10183);

/**
 * Available as of PHP 8.3.0 and cURL 7.19.6
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLKHMATCH_OK', 0);

/**
 * Available as of PHP 8.3.0 and cURL 7.19.6
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLKHMATCH_MISMATCH', 1);

/**
 * Available as of PHP 8.3.0 and cURL 7.19.6
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLKHMATCH_MISSING', 2);

/**
 * Available as of PHP 8.3.0 and cURL 7.19.6
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLKHMATCH_LAST', 3);

/**
 * Next RTSP client CSeq
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_RTSP_CLIENT_CSEQ', 2097189);

/**
 * Recently received CSeq
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_RTSP_CSEQ_RECV', 2097191);

/**
 * Next RTSP server CSeq
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_RTSP_SERVER_CSEQ', 2097190);

/**
 * RTSP session ID
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_RTSP_SESSION_ID', 1048612);

/**
 * Set to 1 to send a PRET command
 * before PASV (and EPSV).
 * Has no effect when using the active FTP transfers mode.
 * Defaults to 0.
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTP_USE_PRET', 188);

/**
 * Set a string with the sender's email address when sending SMTP mail.
 * The email address should be specified with angled brackets
 * (&gt;&lt;) around it,
 * which if not specified are added automatically.
 * If this parameter is not specified then an empty address is sent
 * to the SMTP server which might cause the email to be rejected.
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_MAIL_FROM', 10186);

/**
 * Set to an array of strings
 * with the recipients to pass to the server in an SMTP mail request.
 * Each recipient should be specified within a pair of angled brackets
 * (&gt;&lt;).
 * If an angled bracket is not used as the first character,
 * cURL assumes a single email address has been provided
 * and encloses that address within brackets.
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_MAIL_RCPT', 10187);

/**
 * Set an int with the CSEQ number to issue for the next RTSP request.
 * Useful if the application is resuming a previously broken connection.
 * The CSEQ increments from this new number henceforth.
 * Defaults to 0.
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_RTSP_CLIENT_CSEQ', 193);

/**
 * Sets the kind of RTSP request to make.
 * Must be one of the CURL_RTSPREQ_&#42;
 * constants.
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_RTSP_REQUEST', 189);

/**
 * Set an int with the CSEQ number to expect
 * for the next RTSP Server to Client request.
 * This feature (listening for Server requests) is unimplemented.
 * Defaults to 0.
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_RTSP_SERVER_CSEQ', 194);

/**
 * Set a string with the value of the current RTSP Session ID for the handle.
 * Once this value is set to any non-null value,
 * cURL returns CURLE_RTSP_SESSION_ERROR
 * if the ID received from the server does not match.
 * If set to null, cURL automatically sets the ID
 * the first time the server sets it in a response.
 * Defaults to null
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_RTSP_SESSION_ID', 10190);

/**
 * Sets a string with the stream URI to operate on.
 * If not set, cURL defaults to operating on generic server options
 * by passing &#42; in the place of the RTSP Stream URI.
 * When working with RTSP, CURLOPT_RTSP_STREAM_URI
 * indicates what URL to send to the server in the request header
 * while the CURLOPT_URL indicates
 * where to make the connection to.
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_RTSP_STREAM_URI', 10191);

/**
 * Set the Transport: header for this RTSP session.
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_RTSP_TRANSPORT', 10192);

/**
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_IMAP', 4096);

/**
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_IMAPS', 8192);

/**
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_POP3', 16384);

/**
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_POP3S', 32768);

/**
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_RTSP', 262144);

/**
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_SMTP', 65536);

/**
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_SMTPS', 131072);

/**
 * When sent by a client, this method changes the description of the session.
 * ANNOUNCE acts like an HTTP PUT or POST
 * just like CURL_RTSPREQ_SET_PARAMETER.
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_RTSPREQ_ANNOUNCE', 3);

/**
 * Used to get the low level description of a stream.
 * The application should note what formats it understands
 * in the Accept: header. Unless set manually,
 * libcurl automatically adds in Accept: application/sdp.
 * Time-condition headers are added to DESCRIBE requests
 * if the CURLOPT_TIMECONDITION option is used.
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_RTSPREQ_DESCRIBE', 2);

/**
 * Retrieve a parameter from the server.
 * By default, libcurl adds a Content-Type: text/parameters
 * header on all non-empty requests unless a custom one is set.
 * GET_PARAMETER acts just like an HTTP PUT or POST.
 * Applications wishing to send a heartbeat message
 * should use an empty GET_PARAMETER request.
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_RTSPREQ_GET_PARAMETER', 8);

/**
 * Used to retrieve the available methods of the server.
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_RTSPREQ_OPTIONS', 1);

/**
 * Send a PAUSE command to the server.
 * Use the CURLOPT_RANGE option with a single value
 * to indicate when the stream should be halted (e.g. npt=25).
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_RTSPREQ_PAUSE', 6);

/**
 * Send a PLAY command to the server.
 * Use the CURLOPT_RANGE option
 * to modify the playback time (e.g. npt=10-15).
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_RTSPREQ_PLAY', 5);

/**
 * Set the RTSP request type to this value to receive interleaved RTP data.
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_RTSPREQ_RECEIVE', 11);

/**
 * Used to tell the server to record a session.
 * Use the CURLOPT_RANGE option to modify the record time.
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_RTSPREQ_RECORD', 10);

/**
 * Set a parameter on the server.
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_RTSPREQ_SET_PARAMETER', 9);

/**
 * Used to initialize the transport layer for the session.
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_RTSPREQ_SETUP', 4);

/**
 * Terminates an RTSP session.
 * Simply closing a connection does not terminate the RTSP session
 * since it is valid to control an RTSP session over different connections.
 * Available as of cURL 7.20.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_RTSPREQ_TEARDOWN', 7);

/**
 * Local (source) IP address of the most recent connection
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_LOCAL_IP', 1048617);

/**
 * Local (source) port of the most recent connection
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_LOCAL_PORT', 2097194);

/**
 * Destination port of the most recent connection
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_PRIMARY_PORT', 2097192);

/**
 * Pass a callable that will be used for wildcard matching.
 * The signature of the callback should be:
 * intcallback
 * resourcecurlHandle
 * stringpattern
 * stringstring
 * <p>
 * curlHandle
 * <br>
 * The cURL handle.
 * pattern
 * <br>
 * The wildcard pattern.
 * string
 * <br>
 * The string to run the wildcard pattern matching on.
 * </p>
 * The callback should return
 * CURL_FNMATCHFUNC_MATCH if pattern matches the string,
 * CURL_FNMATCHFUNC_NOMATCH if not
 * or CURL_FNMATCHFUNC_FAIL if an error occurred.
 * Available as of cURL 7.21.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FNMATCH_FUNCTION', 20200);

/**
 * Set to 1 to transfer multiple files
 * according to a filename pattern.
 * The pattern can be specified as part of the
 * CURLOPT_URL option,
 * using an fnmatch-like pattern (Shell Pattern Matching)
 * in the last part of URL (filename).
 * Available as of cURL 7.21.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_WILDCARDMATCH', 197);

/**
 * Available as of cURL 7.21.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_RTMP', 524288);

/**
 * Available as of cURL 7.21.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_RTMPE', 2097152);

/**
 * Available as of cURL 7.21.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_RTMPS', 8388608);

/**
 * Available as of cURL 7.21.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_RTMPT', 1048576);

/**
 * Available as of cURL 7.21.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_RTMPTE', 4194304);

/**
 * Available as of cURL 7.21.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_RTMPTS', 16777216);

/**
 * Returned by the wildcard match callback function if an error occurred.
 * Available as of cURL 7.21.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_FNMATCHFUNC_FAIL', 2);

/**
 * Returned by the wildcard match callback function
 * if pattern matches the string.
 * Available as of cURL 7.21.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_FNMATCHFUNC_MATCH', 0);

/**
 * Returned by the wildcard match callback function
 * if pattern does not match the string.
 * Available as of cURL 7.21.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_FNMATCHFUNC_NOMATCH', 1);

/**
 * Available as of cURL 7.21.2.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_GOPHER', 33554432);

/**
 * This is a meta symbol. OR this value together
 * with a single specific auth value
 * to force libcurl to probe for unrestricted auth and if not,
 * only that single auth algorithm is acceptable.
 * Available as of cURL 7.21.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_ONLY', 2147483648);

/**
 * Provide an array of colon-separated strings
 * with custom addresses for specific host and port pairs in the following format:
 * array(
 * "example.com:80:127.0.0.1",
 * "example2.com:443:127.0.0.2",
 * )
 * Available as of cURL 7.21.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_RESOLVE', 10203);

/**
 * Set a password to use for the TLS authentication method specified
 * with the CURLOPT_TLSAUTH_TYPE option. Requires that
 * the CURLOPT_TLSAUTH_USERNAME option also be set.
 * This feature relies on TLS SRP which does not work with TLS 1.3.
 * Available as of cURL 7.21.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TLSAUTH_PASSWORD', 10205);

/**
 * Set a string with the method of the TLS authentication.
 * Supported method is SRP
 * (TLS Secure Remote Password authentication).
 * Available as of cURL 7.21.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TLSAUTH_TYPE', 10206);

/**
 * Set a string with the username to use for the TLS authentication method
 * specified with the CURLOPT_TLSAUTH_TYPE option.
 * Requires that the CURLOPT_TLSAUTH_PASSWORD option
 * also be set.
 * This feature relies on TLS SRP which does not work with TLS 1.3.
 * Available as of cURL 7.21.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TLSAUTH_USERNAME', 10204);

/**
 * Available as of cURL 7.21.4.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_TLSAUTH_SRP', 1);

/**
 * TLS-SRP auth is supported.
 * Available as of PHP 7.3.0 and cURL 7.21.4
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_TLSAUTH_SRP', 16384);

/**
 * Sets a string with the contents
 * of the Accept-Encoding: header sent in an HTTP request.
 * Set to null to disable sending the Accept-Encoding: header.
 * Defaults to null.
 * Available as of cURL 7.21.6.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_ACCEPT_ENCODING', 10102);

/**
 * Set to 1 to enable and 0 to disable
 * requesting compressed Transfer Encoding in the outgoing
 * HTTP request. If the server responds with a compressed
 * Transfer Encoding,
 * cURL will automatically uncompress it on reception.
 * Defaults to 0.
 * Available as of cURL 7.21.6.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TRANSFER_ENCODING', 207);

/**
 * Available as of PHP 7.0.7 and cURL 7.22.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_NTLM_WB', 32);

/**
 * Allow unconditional GSSAPI credential delegation.
 * Available as of cURL 7.22.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLGSSAPI_DELEGATION_FLAG', 2);

/**
 * Delegate only if the OK-AS-DELEGATE flag is set
 * in the service ticket if this feature is supported by the GSS-API implementation
 * and the definition of GSS_C_DELEG_POLICY_FLAG
 * was available at compile-time.
 * Available as of cURL 7.22.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLGSSAPI_DELEGATION_POLICY_FLAG', 1);

/**
 * Set to CURLGSSAPI_DELEGATION_FLAG
 * to allow unconditional GSSAPI credential delegation.
 * Set to CURLGSSAPI_DELEGATION_POLICY_FLAG
 * to delegate only if the OK-AS-DELEGATE flag is set
 * in the service ticket.
 * Defaults to CURLGSSAPI_DELEGATION_NONE.
 * Available as of cURL 7.22.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_GSSAPI_DELEGATION', 210);

/**
 * NTLM delegation to winbind helper is supported.
 * Available as of PHP 7.3.0 and cURL 7.22.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_NTLM_WB', 32768);

/**
 * The maximum number of milliseconds to wait for a server
 * to connect back to cURL when an active FTP connection is used.
 * This option accepts any value that can be cast to a valid int.
 * Defaults to 60000 milliseconds.
 * Available as of cURL 7.24.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_ACCEPTTIMEOUT_MS', 212);

/**
 * Pass a <p>192.168.1.100,192.168.1.101:8080).
 * Available as of cURL 7.24.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DNS_SERVERS', 10211);

/**
 * Set a string with the authentication address (identity)
 * of a submitted message that is being relayed to another server.
 * The address should not be specified within a pair of angled brackets
 * (&gt;&lt;).
 * If an empty string is used then a pair of brackets are sent by cURL
 * as required by RFC 2554.
 * Available as of cURL 7.25.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_MAIL_AUTH', 10217);

/**
 * Set SSL behavior options, which is a bitmask of the
 * CURLSSLOPT_&#42; constants.
 * Defaults to none of the bits being set.
 * Available as of PHP 7.0.7. and cURL 7.25.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSL_OPTIONS', 216);

/**
 * If set to 1, TCP keepalive probes will be sent. The delay and
 * frequency of these probes can be controlled by the CURLOPT_TCP_KEEPIDLE
 * and CURLOPT_TCP_KEEPINTVL options, provided the operating system
 * supports them. If set to 0 (default) keepalive probes are disabled.
 * The maximum number of probes can be set with the CURLOPT_TCP_KEEPCNT
 * option.
 * Available as of cURL 7.25.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TCP_KEEPALIVE', 213);

/**
 * Sets the delay, in seconds, that the operating system will wait while the connection is
 * idle before sending keepalive probes, if CURLOPT_TCP_KEEPALIVE is
 * enabled. Not all operating systems support this option.
 * The default is 60.
 * Available as of cURL 7.25.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TCP_KEEPIDLE', 214);

/**
 * Sets the interval, in seconds, that the operating system will wait between sending
 * keepalive probes, if CURLOPT_TCP_KEEPALIVE is enabled.
 * Not all operating systems support this option.
 * The default is 60.
 * Available as of cURL 7.25.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TCP_KEEPINTVL', 215);

/**
 * Available as of cURL 7.25.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSLOPT_ALLOW_BEAST', 1);

/**
 * Available as of PHP 7.0.7 and cURL 7.25.1
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_REDIR_POST_303', 4);

/**
 * Available as of PHP 7.0.7 and cURL 7.28.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSH_AUTH_AGENT', 16);

/**
 * Specifies the chunk length threshold for pipelining in bytes.
 * Available as of PHP 7.0.7 and cURL 7.30.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLMOPT_CHUNK_LENGTH_PENALTY_SIZE', 30010);

/**
 * Specifies the size threshold for pipelining penalty in bytes.
 * Available as of PHP 7.0.7 and cURL 7.30.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLMOPT_CONTENT_LENGTH_PENALTY_SIZE', 30009);

/**
 * Specifies the maximum number of connections to a single host.
 * Available as of PHP 7.0.7 and cURL 7.30.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLMOPT_MAX_HOST_CONNECTIONS', 7);

/**
 * Specifies the maximum number of requests in a pipeline.
 * Available as of PHP 7.0.7 and cURL 7.30.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLMOPT_MAX_PIPELINE_LENGTH', 8);

/**
 * Specifies the maximum number of simultaneously open connections.
 * Available as of PHP 7.0.7 and cURL 7.30.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLMOPT_MAX_TOTAL_CONNECTIONS', 13);

/**
 * true to enable sending the initial response in the first packet.
 * Available as of PHP 7.0.7 and cURL 7.31.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SASL_IR', 218);

/**
 * Set the name of the network interface that the DNS resolver should bind to.
 * This must be an interface name (not an address).
 * This option accepts a string or null.
 * Available as of PHP 7.0.7 and cURL 7.33.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DNS_INTERFACE', 10221);

/**
 * Set the local IPv4 address that the resolver should bind to.
 * The argument should contain a single numerical IPv4 address.
 * This option accepts a string or null.
 * Available as of PHP 7.0.7 and cURL 7.33.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DNS_LOCAL_IP4', 10222);

/**
 * Set the local IPv6 address that the resolver should bind to.
 * The argument should contain a single numerical IPv6 address.
 * This option accepts a string or null.
 * Available as of PHP 7.0.7 and cURL 7.33.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DNS_LOCAL_IP6', 10223);

/**
 * Specifies the OAuth 2.0 access token.
 * Set to null to disable.
 * Defaults to null.
 * Available as of PHP 7.0.7 and cURL 7.33.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_XOAUTH2_BEARER', 10220);

/**
 * Available as of cURL 7.33.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_HTTP_VERSION_2_0', 3);

/**
 * HTTP2 support built-in.
 * Available as of cURL 7.33.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_HTTP2', 65536);

/**
 * Can be used to set protocol specific login options, such as the
 * preferred authentication mechanism via AUTH=NTLM or AUTH=&#42;, and should be used in conjunction with the
 * CURLOPT_USERNAME option.
 * Available as of PHP 7.0.7 and cURL 7.34.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_LOGIN_OPTIONS', 10224);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_TLSv1_0', 4);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_TLSv1_1', 5);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_TLSv1_2', 6);

/**
 * The timeout for Expect: 100-continue responses in milliseconds.
 * Defaults to 1000 milliseconds.
 * This option accepts any value that can be cast to a valid int.
 * Available as of PHP 7.0.7 and cURL 7.36.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_EXPECT_100_TIMEOUT_MS', 227);

/**
 * false to disable ALPN in the SSL handshake (if the SSL backend
 * cURL is built to use supports it), which can be used to
 * negotiate http2.
 * Available as of PHP 7.0.7 and cURL 7.36.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSL_ENABLE_ALPN', 226);

/**
 * false to disable NPN in the SSL handshake (if the SSL backend
 * cURL is built to use supports it), which can be used to
 * negotiate http2.
 * Available as of PHP 7.0.7 and cURL 7.36.0, and deprecated as of cURL 7.86.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSL_ENABLE_NPN', 225);

/**
 * Available as of PHP 7.0.7 and cURL 7.37.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLHEADER_SEPARATE', 1);

/**
 * Available as of PHP 7.0.7 and cURL 7.37.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLHEADER_UNIFIED', 0);

/**
 * Send HTTP headers to both proxy and host or separately.
 * Possible values are any of the
 * CURLHEADER_&#42; constants.
 * Defaults to CURLHEADER_SEPARATE as of cURL
 * 7.42.1, and CURLHEADER_UNIFIED prior to that.
 * Available as of PHP 7.0.7 and cURL 7.37.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HEADEROPT', 229);

/**
 * An array of custom HTTP header strings to pass to proxies.
 * Available as of PHP 7.0.7 and cURL 7.37.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXYHEADER', 10228);

/**
 * Available as of PHP 7.0.7 and cURL 7.38.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_NEGOTIATE', 4);

/**
 * Built against a GSS-API library.
 * Available as of PHP 7.3.0 and cURL 7.38.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_GSSAPI', 131072);

/**
 * Set a string with the pinned public key.
 * The string can be the file name of the pinned public key
 * in a PEM or DER file format. The string can also be any
 * number of base64 encoded sha256 hashes preceded by sha256// and
 * separated by ;.
 * Available as of PHP 7.0.7 and cURL 7.39.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PINNEDPUBLICKEY', 10230);

/**
 * Enables the use of Unix domain sockets as connection endpoint and
 * sets the path to the given string.
 * Set to null to disable.
 * Defaults to null.
 * Available as of PHP 7.0.7 and cURL 7.40.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_UNIX_SOCKET_PATH', 10231);

/**
 * Available as of PHP 7.0.7 and cURL 7.40.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_SMB', 67108864);

/**
 * Available as of PHP 7.0.7 and cURL 7.40.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_SMBS', 134217728);

/**
 * Kerberos V5 auth is supported.
 * Available as of PHP 7.0.7 and cURL 7.40.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_KERBEROS5', 262144);

/**
 * Unix domain sockets support.
 * Available as of PHP 7.0.7 and cURL 7.40.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_UNIX_SOCKETS', 524288);

/**
 * true to enable and false to disable verification of the certificate's status.
 * Available as of PHP 7.0.7 and cURL 7.41.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSL_VERIFYSTATUS', 232);

/**
 * Set to true for cURL not alter URL paths before passing them on to the server.
 * Defaults to false, which squashes sequences of /../
 * or /./ that may exist in the URL's path part
 * which is supposed to be removed according to RFC 3986 section 5.2.4.
 * Available as of PHP 7.0.7 and cURL 7.42.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PATH_AS_IS', 234);

/**
 * true to enable and false to disable TLS false start
 * which is a mode where a TLS client starts sending application data
 * before verifying the server's Finished message.
 * Available as of PHP 7.0.7 and cURL 7.42.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSL_FALSESTART', 233);

/**
 * Available as of PHP 7.0.7 and cURL 7.43.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_HTTP_VERSION_2', 3);

/**
 * Set to true to wait for an existing connection to confirm
 * whether it can do multiplexing and use it if it can
 * before creating and using a new connection.
 * Available as of PHP 7.0.7 and cURL 7.43.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PIPEWAIT', 237);

/**
 * A string with the proxy authentication service name.
 * Available as of PHP 7.0.7, cURL 7.43.0 (for HTTP proxies) and cURL 7.49.0 (for SOCKS5 proxies).
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SERVICE_NAME', 10235);

/**
 * A string with the authentication service name.
 * Available as of PHP 7.0.7 and cURL 7.43.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SERVICE_NAME', 10236);

/**
 * Available as of cURL 7.43.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPIPE_NOTHING', 0);

/**
 * Available as of cURL 7.43.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPIPE_HTTP1', 1);

/**
 * Available as of cURL 7.43.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPIPE_MULTIPLEX', 2);

/**
 * Available as of PHP 7.0.7 and cURL 7.44.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSLOPT_NO_REVOKE', 2);

/**
 * A string with the default protocol to use if the URL is missing a scheme name.
 * Available as of PHP 7.0.7 and cURL 7.45.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DEFAULT_PROTOCOL', 10238);

/**
 * Set the numerical stream weight (a number between 1 and 256).
 * Available as of PHP 7.0.7 and cURL 7.46.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_STREAM_WEIGHT', 239);

/**
 * Pass a callable that will be registered to handle server
 * pushes and should have the following signature:
 * intpushfunction
 * resourceparent_ch
 * resourcepushed_ch
 * arrayheaders
 * <p>
 * parent_ch
 * <br>
 * The parent cURL handle (the request the client made).
 * pushed_ch
 * <br>
 * A new cURL handle for the pushed request.
 * headers
 * <br>
 * The push promise headers.
 * </p>
 * The push function is supposed to return either
 * CURL_PUSH_OK if it can handle the push, or
 * CURL_PUSH_DENY to reject it.
 * Available as of PHP 7.1.0 and cURL 7.44.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLMOPT_PUSHFUNCTION', 20014);

/**
 * Available as of PHP 7.1.0 and cURL 7.44.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_PUSH_OK', 0);

/**
 * Available as of PHP 7.1.0 and cURL 7.44.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_PUSH_DENY', 1);

/**
 * Available as of PHP 7.0.7 and cURL 7.47.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_HTTP_VERSION_2TLS', 4);

/**
 * Mozilla's Public Suffix List, used for cookie domain verification.
 * Available as of PHP 7.3.6 and cURL 7.47.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_PSL', 1048576);

/**
 * true to not send TFTP options requests.
 * Defaults to false.
 * Available as of PHP 7.0.7 and cURL 7.48.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TFTP_NO_OPTIONS', 242);

/**
 * Available as of PHP 7.0.7 and cURL 7.49.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_HTTP_VERSION_2_PRIOR_KNOWLEDGE', 5);

/**
 * Connect to a specific host and port instead of the URL's host and port.
 * Accepts an array of strings with the format
 * HOST:PORT:CONNECT-TO-HOST:CONNECT-TO-PORT.
 * Available as of PHP 7.0.7 and cURL 7.49.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CONNECT_TO', 10243);

/**
 * true to enable and false to disable TCP Fast Open.
 * Available as of PHP 7.0.7 and cURL 7.49.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TCP_FASTOPEN', 244);

/**
 * The version used in the last HTTP connection. The return value will be one of the defined CURL_HTTP_VERSION_&#42; constants or 0 if the version can't be determined.
 * Available as of PHP 7.3.0 and cURL 7.50.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_HTTP_VERSION', 2097198);

/**
 * The server sent data libcurl could not parse.
 * This error code was known as CURLE_FTP_WEIRD_SERVER_REPLY
 * before cURL 7.51.0.
 * Available as of PHP 7.3.0 and cURL 7.51.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_WEIRD_SERVER_REPLY', 8);

/**
 * Set to true to keep sending the request body if the HTTP code returned is equal to or larger than 300.
 * The default action would be to stop sending
 * and close the stream or connection. Suitable for manual NTLM authentication.
 * Most applications do not need this option.
 * Available as of PHP 7.3.0 and cURL 7.51.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_KEEP_SENDING_ON_ERROR', 245);

/**
 * Available as of PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_TLSv1_3', 7);

/**
 * Available as of PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_HTTPS_PROXY', 2097152);

/**
 * The protocol used in the last HTTP connection. The returned value will be exactly one of the CURLPROTO_&#42; values.
 * Available as of PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_PROTOCOL', 2097200);

/**
 * The result of the certificate verification that was requested (using the CURLOPT_PROXY_SSL_VERIFYPEER option). Only used for HTTPS proxies.
 * Available as of PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_PROXY_SSL_VERIFYRESULT', 2097199);

/**
 * The URL scheme used for the most recent connection.
 * Available as of PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SCHEME', 1048625);

/**
 * Set a string holding the host name or dotted numerical
 * IP address to be used as the preproxy that cURL connects to before
 * it connects to the HTTP(S) proxy specified in the
 * CURLOPT_PROXY option for the upcoming request.
 * The preproxy can only be a SOCKS proxy and it should be prefixed with
 * [scheme]:// to specify which kind of socks is used.
 * A numerical IPv6 address must be written within [brackets].
 * Setting the preproxy to an empty string explicitly disables the use of a preproxy.
 * To specify port number in this string, append :[port]
 * to the end of the host name. The proxy's port number may optionally be
 * specified with the separate option CURLOPT_PROXYPORT.
 * Defaults to using port 1080 for proxies if a port is not specified.
 * Available as of PHP 7.3.0 and cURL 7.52.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PRE_PROXY', 10262);

/**
 * The path to proxy Certificate Authority (CA) bundle. Set the path as a
 * string naming a file holding one or more certificates to
 * verify the HTTPS proxy with.
 * This option is for connecting to an HTTPS proxy, not an HTTPS server.
 * Defaults set to the system path where cURL's cacert bundle is assumed
 * to be stored.
 * Available as of PHP 7.3.0 and cURL 7.52.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_CAINFO', 10246);

/**
 * A string with the directory holding multiple CA certificates
 * to verify the HTTPS proxy with.
 * Available as of PHP 7.3.0 and cURL 7.52.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_CAPATH', 10247);

/**
 * Set to a string with the file name
 * with the concatenation of CRL (Certificate Revocation List)
 * in PEM format to use in the certificate validation that occurs during
 * the SSL exchange.
 * Available as of PHP 7.3.0 and cURL 7.52.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_CRLFILE', 10260);

/**
 * Set the string be used as the password required to use the
 * CURLOPT_PROXY_SSLKEY private key.
 * A passphrase is not needed to load a certificate
 * but one is needed to load a private key.
 * This option is for connecting to an HTTPS proxy, not an HTTPS server.
 * Available as of PHP 7.3.0 and cURL 7.52.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_KEYPASSWD', 10258);

/**
 * Set the pinned public key for HTTPS proxy.
 * The string can be the file name of the pinned public key
 * which is expected to be in a PEM
 * or DER file format.
 * The string can also be any number of base64 encoded sha256 hashes
 * preceded by sha256// and separated by ;.
 * Available as of PHP 7.3.0 and cURL 7.52.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_PINNEDPUBLICKEY', 10263);

/**
 * A <p>!, - and +
 * can be used as operators.
 * Available as of PHP 7.3.0 and cURL 7.52.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSL_CIPHER_LIST', 10259);

/**
 * Set proxy SSL behavior options, which is a bitmask of the
 * CURLSSLOPT_&#42; constants.
 * Available as of PHP 7.3.0 and cURL 7.52.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSL_OPTIONS', 261);

/**
 * Set to 2 to verify in the HTTPS proxy's certificate name fields against the proxy name.
 * When set to 0 the connection succeeds regardless of the names used in the certificate.
 * Use that ability with caution!
 * Set to 1 in cURL 7.28.0 and earlier as a debug option.
 * Set to 1 in cURL 7.28.1 to 7.65.3 CURLE_BAD_FUNCTION_ARGUMENT is returned.
 * As of cURL 7.66.0 1 and 2 is treated as the same value.
 * Defaults to 2.
 * In production environments the value of this option should be kept at 2.
 * Available as of PHP 7.3.0 and cURL 7.52.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSL_VERIFYHOST', 249);

/**
 * Set to false to stop cURL from verifying the peer's certificate.
 * Alternate certificates to verify against can be
 * specified with the CURLOPT_CAINFO option
 * or a certificate directory can be specified with the
 * CURLOPT_CAPATH option.
 * When set to false, the peer certificate verification succeeds regardless.
 * true by default.
 * Available as of PHP 7.3.0 and cURL 7.52.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSL_VERIFYPEER', 248);

/**
 * A string with the file name of the client certificate used to connect to the HTTPS proxy.
 * The default format is P12 on Secure Transport and PEM on other engines,
 * and can be changed with CURLOPT_PROXY_SSLCERTTYPE.
 * With NSS or Secure Transport, this can also be the nickname of the certificate
 * used for authentication as it is named in the security database.
 * If a file from the current directory is to be used,
 * it must be prefixed with ./
 * in order to avoid confusion with a nickname.
 * Available as of PHP 7.3.0 and cURL 7.52.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSLCERT', 10254);

/**
 * A string with the format of the client certificate used when connecting to an HTTPS proxy.
 * Supported formats are PEM and DER, except with Secure Transport.
 * OpenSSL (versions 0.9.3 and later) and Secure Transport
 * (on iOS 5 or later, or OS X 10.7 or later) also support P12 for
 * PKCS#12-encoded files. Defaults to PEM.
 * Available as of PHP 7.3.0 and cURL 7.52.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSLCERTTYPE', 10255);

/**
 * A string with the file name of the private key
 * used for connecting to the HTTPS proxy.
 * The default format is PEM and can be changed with
 * CURLOPT_PROXY_SSLKEYTYPE.
 * (iOS and Mac OS X only) This option is ignored if cURL was built against
 * Secure Transport. Available if built with TLS enabled.
 * Available as of PHP 7.3.0 and cURL 7.52.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSLKEY', 10256);

/**
 * A string with the format of the private key.
 * Supported formats are:
 * <p>
 * PEM
 * DER
 * ENG
 * </p>.
 * Available as of PHP 7.3.0 and cURL 7.52.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSLKEYTYPE', 10257);

/**
 * Set the preferred HTTPS proxy TLS version to one of the
 * CURL_SSLVERSION_&#42;
 * constants.
 * Defaults to CURL_SSLVERSION_DEFAULT.
 * It is better to not set this option and leave the default
 * CURL_SSLVERSION_DEFAULT
 * which will attempt to figure out the remote SSL protocol version.
 * Available as of PHP 7.3.0 and cURL 7.52.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSLVERSION', 250);

/**
 * A string with the password to use for the TLS authentication method specified with the
 * CURLOPT_PROXY_TLSAUTH_TYPE option. Requires that the
 * CURLOPT_PROXY_TLSAUTH_USERNAME option to also be set.
 * Available as of PHP 7.3.0 and cURL 7.52.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_TLSAUTH_PASSWORD', 10252);

/**
 * The method of the TLS authentication used for the HTTPS connection.
 * Supported method is SRP.
 * <p>
 * Secure Remote Password (SRP) authentication for TLS provides mutual authentication
 * if both sides have a shared secret. To use TLS-SRP, the
 * CURLOPT_PROXY_TLSAUTH_USERNAME and
 * CURLOPT_PROXY_TLSAUTH_PASSWORD options must also be set.
 * </p>
 * Available as of PHP 7.3.0 and cURL 7.52.0.
 * <p>Secure Remote Password (SRP) authentication for TLS provides mutual authentication
 * if both sides have a shared secret. To use TLS-SRP, the
 * CURLOPT_PROXY_TLSAUTH_USERNAME and
 * CURLOPT_PROXY_TLSAUTH_PASSWORD options must also be set.</p>
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_TLSAUTH_TYPE', 10253);

/**
 * The username to use for the HTTPS proxy TLS authentication method specified with the
 * CURLOPT_PROXY_TLSAUTH_TYPE option. Requires that the
 * CURLOPT_PROXY_TLSAUTH_PASSWORD option to also be set.
 * Available as of PHP 7.3.0 and cURL 7.52.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_TLSAUTH_USERNAME', 10251);

/**
 * Available as of PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROXY_HTTPS', 2);

/**
 * Available as of PHP 7.3.0 and cURL 7.53.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_MAX_READ_SIZE', 10485760);

/**
 * Enables the use of an abstract Unix domain socket instead of
 * establishing a TCP connection to a host and sets the path to
 * the given string. This option shares the same semantics
 * as CURLOPT_UNIX_SOCKET_PATH. These two options
 * share the same storage and therefore only one of them can be set
 * per handle.
 * Available as of PHP 7.3.0 and cURL 7.53.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_ABSTRACT_UNIX_SOCKET', 10264);

/**
 * Available as of PHP 7.3.0 and cURL 7.54.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_MAX_DEFAULT', 65536);

/**
 * Available as of PHP 7.3.0 and cURL 7.54.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_MAX_NONE', 0);

/**
 * Available as of PHP 7.3.0 and cURL 7.54.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_MAX_TLSv1_0', 262144);

/**
 * Available as of PHP 7.3.0 and cURL 7.54.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_MAX_TLSv1_1', 327680);

/**
 * Available as of PHP 7.3.0 and cURL 7.54.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_MAX_TLSv1_2', 393216);

/**
 * Available as of PHP 7.3.0 and cURL 7.54.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_MAX_TLSv1_3', 458752);

/**
 * true to suppress proxy CONNECT response headers from the user callback functions
 * CURLOPT_HEADERFUNCTION and CURLOPT_WRITEFUNCTION,
 * when CURLOPT_HTTPPROXYTUNNEL is used and a CONNECT request is made.
 * Defaults to false.
 * Available as of PHP 7.3.0 and cURL 7.54.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SUPPRESS_CONNECT_HEADERS', 265);

/**
 * Available as of PHP 7.3.0 and cURL 7.54.1
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_GSSAPI', 4);

/**
 * The content-length of the download. This is the value read from the Content-Length: field. -1 if the size isn't known.
 * Available as of PHP 7.3.0 and cURL 7.55.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_CONTENT_LENGTH_DOWNLOAD_T', 6291471);

/**
 * The specified size of the upload. -1 if the size isn't known.
 * Available as of PHP 7.3.0 and cURL 7.55.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_CONTENT_LENGTH_UPLOAD_T', 6291472);

/**
 * Total number of bytes that were downloaded. The number is only for the latest transfer and will be reset again for each new transfer.
 * Available as of PHP 7.3.0 and cURL 7.50.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SIZE_DOWNLOAD_T', 6291464);

/**
 * Total number of bytes that were uploaded.
 * Available as of PHP 7.3.0 and cURL 7.50.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SIZE_UPLOAD_T', 6291463);

/**
 * The average download speed in bytes/second that curl measured for the complete download.
 * Available as of PHP 7.3.0 and cURL 7.50.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SPEED_DOWNLOAD_T', 6291465);

/**
 * The average upload speed in bytes/second that curl measured for the complete upload.
 * Available as of PHP 7.3.0 and cURL 7.50.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SPEED_UPLOAD_T', 6291466);

/**
 * A string to use in the upcoming request
 * instead of the path as extracted from the URL.
 * Available as of PHP 7.3.0 and cURL 7.55.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_REQUEST_TARGET', 10266);

/**
 * The SOCKS5 authentication method(s) to use. The options are:
 * <p>
 * CURLAUTH_BASIC
 * CURLAUTH_GSSAPI
 * CURLAUTH_NONE
 * </p>.
 * When more than one method is set, cURL will poll the server to see
 * what methods it supports and pick the best one.
 * Defaults to CURLAUTH_BASIC|CURLAUTH_GSSAPI.
 * Set the actual username and password with the CURLOPT_PROXYUSERPWD option.
 * Available as of PHP 7.3.0 and cURL 7.55.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SOCKS5_AUTH', 267);

/**
 * true to enable, false to disable built-in SSH compression.
 * Note that the server can disregard this request.
 * Defaults to false.
 * Available as of PHP 7.3.0 and cURL 7.56.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSH_COMPRESSION', 268);

/**
 * Available as of PHP 7.3.0 and cURL 7.56.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_MULTI_SSL', 4194304);

/**
 * Available as of PHP 7.3.0 and cURL 7.57.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_BROTLI', 8388608);

/**
 * Shares/unshares the connection cache.
 * Available as of PHP 7.3.0 and cURL 7.10.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_LOCK_DATA_CONNECT', 5);

/**
 * Available as of PHP 7.3.0 and cURL 7.58.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSH_AUTH_GSSAPI', 32);

/**
 * Remote time of the retrieved document (as Unix timestamp), an alternative to CURLINFO_FILETIME to allow systems with 32 bit long variables to extract dates outside of the 32bit timestamp range.
 * Available as of PHP 7.3.0 and cURL 7.59.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_FILETIME_T', 6291470);

/**
 * Head start for IPv6 for the happy eyeballs algorithm. Happy eyeballs attempts
 * to connect to both IPv4 and IPv6 addresses for dual-stack hosts,
 * preferring IPv6 first for timeout milliseconds.
 * Defaults to CURL_HET_DEFAULT, which is currently 200 milliseconds.
 * This option accepts any value that can be cast to a valid int.
 * Available as of PHP 7.3.0 and cURL 7.59.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HAPPY_EYEBALLS_TIMEOUT_MS', 271);

/**
 * The time in seconds since January 1st, 1970. The time will be used
 * by CURLOPT_TIMECONDITION. Defaults to zero.
 * The difference between this option and CURLOPT_TIMEVALUE
 * is the type of the argument. On systems where 'long' is only 32 bit wide,
 * this option has to be used to set dates beyond the year 2038.
 * Available as of PHP 7.3.0 and cURL 7.59.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TIMEVALUE_LARGE', 30270);

/**
 * true to shuffle the order of all returned addresses so that they will be used
 * in a random order, when a name is resolved and more than one IP address is returned.
 * This may cause IPv4 to be used before IPv6 or vice versa.
 * Available as of PHP 7.3.0 and cURL 7.60.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DNS_SHUFFLE_ADDRESSES', 275);

/**
 * true to send an HAProxy PROXY protocol v1 header at the start of the connection.
 * The default action is not to send this header.
 * Available as of PHP 7.3.0 and cURL 7.60.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HAPROXYPROTOCOL', 274);

/**
 * Shares/unshares the Public Suffix List.
 * Available as of PHP 7.3.0 and cURL 7.61.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_LOCK_DATA_PSL', 6);

/**
 * Available as of PHP 7.3.0 and cURL 7.61.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_BEARER', 64);

/**
 * Time, in microseconds, it took from the start until the SSL/SSH connect/handshake to the remote host was completed.
 * Available as of PHP 7.3.0 and cURL 7.61.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_APPCONNECT_TIME_T', 6291512);

/**
 * Total time taken, in microseconds, from the start until the connection to the remote host (or proxy) was completed.
 * Available as of PHP 7.3.0 and cURL 7.61.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_CONNECT_TIME_T', 6291508);

/**
 * Time in microseconds from the start until the name resolving was completed.
 * Available as of PHP 7.3.0 and cURL 7.61.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_NAMELOOKUP_TIME_T', 6291507);

/**
 * Time taken from the start until the file transfer is just about to begin, in microseconds.
 * Available as of PHP 7.3.0 and cURL 7.61.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_PRETRANSFER_TIME_T', 6291509);

/**
 * Total time, in microseconds, it took for all redirection steps include name lookup, connect, pretransfer and transfer before final transaction was started.
 * Available as of PHP 7.3.0 and cURL 7.61.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_REDIRECT_TIME_T', 6291511);

/**
 * Time, in microseconds, it took from the start until the first byte is received.
 * Available as of PHP 7.3.0 and cURL 7.61.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_STARTTRANSFER_TIME_T', 6291510);

/**
 * Total time in microseconds for the previous transfer, including name resolving, TCP connect etc.
 * Available as of PHP 7.3.0 and cURL 7.61.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_TOTAL_TIME_T', 6291506);

/**
 * Time it took from the start until the last byte is sent, in microseconds.
 * Available as of PHP 8.4.0 and cURL 8.10.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_POSTTRANSFER_TIME_T', 6291523);

/**
 * true to not allow URLs that include a username.
 * Usernames are allowed by default.
 * Available as of PHP 7.3.0 and cURL 7.61.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DISALLOW_USERNAME_IN_URL', 278);

/**
 * A <p>CURLOPT_PROXY_SSL_CIPHER_LIST option.
 * Available as of PHP 7.3.0 and cURL 7.61.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_TLS13_CIPHERS', 10277);

/**
 * A <p>CURLOPT_SSL_CIPHER_LIST option.
 * Available as of PHP 7.3.0 and cURL 7.61.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TLS13_CIPHERS', 10276);

/**
 * Provides the DNS-over-HTTPS URL.
 * This option accepts a string or null.
 * Available as of PHP 8.1.0 and cURL 7.62.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DOH_URL', 10279);

/**
 * Some protocols have "connection upkeep" mechanisms. These mechanisms usually send some traffic
 * on existing connections in order to keep them alive. This option defines the connection upkeep interval.
 * Currently, the only protocol with a connection upkeep mechanism is HTTP/2. When the connection upkeep
 * interval is exceeded, an HTTP/2 PING frame is sent on the connection.
 * Defaults to CURL_UPKEEP_INTERVAL_DEFAULT
 * which is currently 60 seconds.
 * Available as of PHP 8.2.0 and cURL 7.62.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_UPKEEP_INTERVAL_MS', 281);

/**
 * Preferred buffer size in bytes for the cURL upload buffer.
 * The upload buffer size by default is 64 kilobytes. The maximum buffer size allowed to be set is 2 megabytes.
 * The minimum buffer size allowed to be set is 16 kilobytes.
 * Available as of PHP 8.2.0 and cURL 7.62.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_UPLOAD_BUFFERSIZE', 280);

/**
 * Whether to allow HTTP/0.9 responses. Defaults to false as of cURL 7.66.0;
 * formerly it defaulted to true.
 * Available as of PHP 7.3.15 and 7.4.3, respectively, and cURL 7.64.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HTTP09_ALLOWED', 285);

/**
 * Available as of PHP 8.2.0 and cURL 7.64.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLALTSVC_H1', 8);

/**
 * Available as of PHP 8.2.0 and cURL 7.64.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLALTSVC_H2', 16);

/**
 * Available as of PHP 8.2.0 and cURL 7.64.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLALTSVC_H3', 32);

/**
 * Available as of PHP 8.2.0 and cURL 7.64.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLALTSVC_READONLYFILE', 4);

/**
 * Pass a string with the filename for cURL to use as the Alt-Svc cache file to read existing cache contents from and
 * possibly also write it back to a after a transfer, unless CURLALTSVC_READONLYFILE
 * is set via CURLOPT_ALTSVC_CTRL.
 * Available as of PHP 8.2.0 and cURL 7.64.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_ALTSVC', 10287);

/**
 * Populate the bitmask with the correct set of features to instruct cURL how to handle Alt-Svc for the
 * transfers using this handle. cURL only accepts Alt-Svc headers over HTTPS. It will also only complete
 * a request to an alternative origin if that origin is properly hosted over HTTPS.
 * Setting any bit will enable the alt-svc engine.
 * Set to any of the
 * CURLALTSVC_&#42; constants.
 * Defaults to Alt-Svc handling being disabled.
 * Available as of PHP 8.2.0 and cURL 7.64.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_ALTSVC_CTRL', 286);

/**
 * Available as of PHP 7.3.6 and cURL 7.64.1
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_ALTSVC', 16777216);

/**
 * The maximum idle time allowed for an existing connection to be considered for reuse.
 * Default maximum age is set to 118 seconds.
 * This option accepts any value that can be cast to a valid int.
 * Available as of PHP 8.2.0 and cURL 7.65.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_MAXAGE_CONN', 288);

/**
 * The authorization identity (authzid) string for the transfer. Only applicable to the PLAIN SASL
 * authentication mechanism where it is optional. When not specified, only the authentication identity
 * (authcid) as specified by the username will be sent to the server, along with the password.
 * The server will derive the authzid from the authcid when not provided, which it will then use internally.
 * Available as of PHP 8.2.0 and cURL 7.66.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SASL_AUTHZID', 10289);

/**
 * Available as of PHP 8.2.0 and cURL 7.66.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_HTTP3', 33554432);

/**
 * The information from the Retry-After header, or zero if there was no valid header.
 * Available as of PHP 8.2.0 and cURL 7.66.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_RETRY_AFTER', 6291513);

/**
 * Available as of PHP 8.4.0 and cURL 7.66.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_HTTP_VERSION_3', 30);

/**
 * Specifies the maximum number of concurrent streams for connections
 * that cURL should support on connections using HTTP/2.
 * Valid values range from 1
 * to 2147483647 (2^31 - 1).
 * The value passed here would be honored
 * based on other system resources properties.
 * Default is 100.
 * Available as of PHP 8.2.0 and cURL 7.67.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLMOPT_MAX_CONCURRENT_STREAMS', 16);

/**
 * Available as of PHP 8.2.0 and cURL 7.68.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSLOPT_NO_PARTIALCHAIN', 4);

/**
 * Set to 1 to allow RCPT TO command
 * to fail for some recipients which makes cURL ignore errors
 * for individual recipients and proceed with the remaining accepted recipients.
 * If all recipients trigger failures and this flag is specified,
 * cURL aborts the SMTP conversation
 * and returns the error received from to the last RCPT TO command.
 * Replaced by CURLOPT_MAIL_RCPT_ALLOWFAILS as of cURL 8.2.0.
 * Available as of PHP 8.2.0 and cURL 7.69.0.
 * Deprecated as of cURL 8.2.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_MAIL_RCPT_ALLLOWFAILS', 290);

/**
 * Available as of PHP 8.2.0 and cURL 7.70.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSLOPT_REVOKE_BEST_EFFORT', 8);

/**
 * Pass a string with binary data of a CA SSL certificate in PEM format.
 * If set, an additional check against the peer certificate is performed
 * to verify the issuer is the one associated with the certificate provided by the option.
 * Available as of PHP 8.1.0 and cURL 7.71.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_ISSUERCERT_BLOB', 40295);

/**
 * Proxy issuer SSL certificate filename string.
 * Available as of PHP 8.1.0 and cURL 7.71.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_ISSUERCERT', 10296);

/**
 * A string with the proxy issuer SSL certificate.
 * Available as of PHP 8.1.0 and cURL 7.71.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_ISSUERCERT_BLOB', 40297);

/**
 * A string with the SSL proxy client certificate.
 * Available as of PHP 8.1.0 and cURL 7.71.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSLCERT_BLOB', 40293);

/**
 * A string with the private key for connecting to the HTTPS proxy.
 * Available as of PHP 8.1.0 and cURL 7.71.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSLKEY_BLOB', 40294);

/**
 * A string with the SSL client certificate.
 * Available as of PHP 8.1.0 and cURL 7.71.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLCERT_BLOB', 40291);

/**
 * A string private key for client cert.
 * Available as of PHP 8.1.0 and cURL 7.71.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLKEY_BLOB', 40292);

/**
 * Available as of PHP 8.2.0 and cURL 7.71.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_MQTT', 268435456);

/**
 * Available as of PHP 8.2.0 and cURL 7.71.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSLOPT_NATIVE_CA', 16);

/**
 * Available as of PHP 8.2.0 and cURL 7.72.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_UNICODE', 134217728);

/**
 * Available as of PHP 8.2.0 and cURL 7.72.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_ZSTD', 67108864);

/**
 * Proxy handshake error.
 * CURLINFO_PROXY_ERROR provides extra details on the specific problem.
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_PROXY', 97);

/**
 * The detailed (SOCKS) proxy error code when the most recent transfer returned a CURLE_PROXY error. The returned value will be exactly one of the CURLPX_&#42; values. The error code will be CURLPX_OK if no response code was available.
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_PROXY_ERROR', 2097211);

/**
 * A colon delimited list of elliptic curve algorithms. For example,
 * X25519:P-521 is a valid list of two elliptic curves.
 * This option defines the client's key exchange algorithms in the SSL handshake,
 * if the SSL backend cURL is built to use supports it.
 * Available as of PHP 8.2.0 and cURL 7.73.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSL_EC_CURVES', 10298);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_BAD_ADDRESS_TYPE', 1);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_BAD_VERSION', 2);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_CLOSED', 3);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_GSSAPI', 4);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_GSSAPI_PERMSG', 5);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_GSSAPI_PROTECTION', 6);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_IDENTD', 7);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_IDENTD_DIFFER', 8);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_LONG_HOSTNAME', 9);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_LONG_PASSWD', 10);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_LONG_USER', 11);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_NO_AUTH', 12);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_OK', 0);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_RECV_ADDRESS', 13);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_RECV_AUTH', 14);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_RECV_CONNECT', 15);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_RECV_REQACK', 16);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_REPLY_ADDRESS_TYPE_NOT_SUPPORTED', 17);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_REPLY_COMMAND_NOT_SUPPORTED', 18);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_REPLY_CONNECTION_REFUSED', 19);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_REPLY_GENERAL_SERVER_FAILURE', 20);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_REPLY_HOST_UNREACHABLE', 21);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_REPLY_NETWORK_UNREACHABLE', 22);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_REPLY_NOT_ALLOWED', 23);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_REPLY_TTL_EXPIRED', 24);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_REPLY_UNASSIGNED', 25);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_REQUEST_FAILED', 26);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_RESOLVE_HOST', 27);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_SEND_AUTH', 28);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_SEND_CONNECT', 29);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_SEND_REQUEST', 30);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_UNKNOWN_FAIL', 31);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_UNKNOWN_MODE', 32);

/**
 * Available as of PHP 8.2.0 and cURL 7.73.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPX_USER_REJECTED', 33);

/**
 * Available as of PHP 8.2.0 and cURL 7.74.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLHSTS_ENABLE', 1);

/**
 * Available as of PHP 8.2.0 and cURL 7.74.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLHSTS_READONLYFILE', 2);

/**
 * string with HSTS (HTTP Strict Transport Security) cache file name
 * or null to allow HSTS without reading from or writing to any file
 * and clear the list of files to read HSTS data from.
 * Available as of PHP 8.2.0 and cURL 7.74.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HSTS', 10300);

/**
 * Accepts a bitmask of HSTS (HTTP Strict Transport Security) features
 * defined by the CURLHSTS_&#42; constants.
 * Available as of PHP 8.2.0 and cURL 7.74.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HSTS_CTRL', 299);

/**
 * Available as of PHP 8.2.0 and cURL 7.74.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_HSTS', 268435456);

/**
 * Available as of PHP 8.2.0 and cURL 7.75.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_AWS_SIGV4', 128);

/**
 * Provides AWS V4 signature authentication on HTTP(S) header as a string.
 * This option overrides any other authentication types that have been set in
 * CURLOPT_HTTPAUTH. This method cannot be combined with other authentication types.
 * Available as of PHP 8.2.0 and cURL 7.75.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_AWS_SIGV4', 10305);

/**
 * The Referer header.
 * Available as of PHP 8.2.0 and cURL 7.76.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_REFERER', 1048636);

/**
 * Set to 2 to verify the DNS-over-HTTPS server's SSL certificate name fields against the host name.
 * Available as of PHP 8.2.0 and cURL 7.76.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DOH_SSL_VERIFYHOST', 307);

/**
 * Set to 1 to enable and 0 to disable
 * verification of the authenticity of the DNS-over-HTTPS server's SSL certificate.
 * Available as of PHP 8.2.0 and cURL 7.76.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DOH_SSL_VERIFYPEER', 306);

/**
 * Set to 1 to enable and 0 to disable
 * the verification of the status of the DNS-over-HTTPS server certificate
 * using the "Certificate Status Request" TLS extension (OCSP stapling).
 * Available as of PHP 8.2.0 and cURL 7.76.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DOH_SSL_VERIFYSTATUS', 308);

/**
 * Available as of PHP 8.2.0 and cURL 7.76.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_GSASL', 536870912);

/**
 * A string with the name of a PEM file holding one or more certificates to verify the
 * peer with. This option overrides CURLOPT_CAINFO.
 * Available as of PHP 8.2.0 and cURL 7.77.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CAINFO_BLOB', 40309);

/**
 * A string with the name of a PEM file holding one or more certificates to verify the HTTPS proxy with.
 * This option is for connecting to an HTTPS proxy, not an HTTPS server.
 * Defaults set to the system path where cURL's cacert bundle is assumed
 * to be stored.
 * Available as of PHP 8.2.0 and cURL 7.77.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_CAINFO_BLOB', 40310);

/**
 * Available as of PHP 8.2.0 and cURL 7.77.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSLOPT_AUTO_CLIENT_CERT', 32);

/**
 * The maximum time in seconds, since the creation of the connection, that is allowed for an existing
 * connection to have for it to be considered for reuse. If a connection is found in the cache that is older
 * than this value, it will instead be closed once any in-progress transfers are complete.
 * Default is 0 seconds, meaning the option is disabled and all connections are eligible for reuse.
 * This option accepts any value that can be cast to a valid int.
 * Available as of PHP 8.2.0 and cURL 7.80.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_MAXLIFETIME_CONN', 314);

/**
 * A string with the base64-encoded SHA256 hash
 * of the remote host's public key.
 * The transfer will fail if the given hash does not match the hash the remote host provides.
 * Available as of PHP 8.2.0 and cURL 7.80.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSH_HOST_PUBLIC_KEY_SHA256', 10311);

/**
 * A callable with the following signature that gets called after the
 * connection is established, but before the request payload (for example, the
 * GET/POST/DELETE request of an HTTP connection) is sent, and can be used to abort
 * or allow the connection depending on the source and destination IP address and
 * port numbers:
 * intcallback
 * CurlHandlecurlHandle
 * stringdestination_ip
 * stringlocal_ip
 * intdestination_port
 * intlocal_port
 * <p>
 * curlHandle
 * <br>
 * The cURL handle.
 * destination_ip
 * <br>
 * The primary IP of the remote server established with this connection.
 * For FTP, this is the IP for the control connection.
 * IPv6 addresses are represented without surrounding brackets.
 * local_ip
 * <br>
 * The originating IP for this connection.
 * IPv6 addresses are represented without surrounding brackets.
 * destination_port
 * <br>
 * The primary port number on the remote server established with this connection.
 * For FTP, this is the port for the control connection.
 * This can be a TCP or a UDP port number depending on the protocol.
 * local_port
 * <br>
 * The originating port number for this connection.
 * This can be a TCP or a UDP port number depending on the protocol.
 * </p>
 * Return CURL_PREREQFUNC_OK to allow the request, or
 * CURL_PREREQFUNC_ABORT to abort the transfer.
 * Available as of PHP 8.4.0 and cURL 7.80.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PREREQFUNCTION', 20312);

/**
 * Available as of PHP 8.4.0 and cURL 7.80.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_PREREQFUNC_OK', 0);

/**
 * Available as of PHP 8.4.0 and cURL 7.80.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_PREREQFUNC_ABORT', 1);

/**
 * Set to a bitmask of CURLMIMEOPT_&#42;
 * constants. Currently there is only one available option:
 * CURLMIMEOPT_FORMESCAPE.
 * Available as of PHP 8.3.0 and cURL 7.81.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_MIME_OPTIONS', 315);

/**
 * Available as of PHP 8.3.0 and cURL 7.81.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLMIMEOPT_FORMESCAPE', 1);

/**
 * A callable that will be called when SSH host key verification is needed.
 * The callback must have the following signature:
 * intcallback
 * resourcecurlHandle
 * intkeyType
 * stringkey
 * intkeyLength
 * <p>
 * curlHandle
 * <br>
 * The cURL handle.
 * keyType
 * <br>
 * One of the CURLKHTYPE_&#42; key types.
 * key
 * <br>
 * The key to check.
 * keyLength
 * <br>
 * The length of the key in bytes.
 * </p>
 * This callback overrides CURLOPT_SSH_KNOWNHOSTS.
 * Available as of PHP 8.3.0 and cURL 7.84.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSH_HOSTKEYFUNCTION', 20316);

/**
 * Set to a <p>ALL to enable all protocols.
 * By default, cURL accepts all protocols it was built with support for.
 * Available protocols are:
 * <p>
 * DICT
 * FILE
 * FTP
 * FTPS
 * GOPHER
 * GOPHERS
 * HTTP
 * HTTPS
 * IMAP
 * IMAPS
 * LDAP
 * LDAPS
 * MQTT
 * POP3
 * POP3S
 * RTMP
 * RTMPE
 * RTMPS
 * RTMPT
 * RTMPTE
 * RTMPTS
 * RTSP
 * SCP
 * SFTP
 * SMB
 * SMBS
 * SMTP
 * SMTPS
 * TELNET
 * TFTP
 * WS
 * WSS
 * </p>.
 * Available as of PHP 8.3.0 and cURL 7.85.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROTOCOLS_STR', 10318);

/**
 * Set to a <p>CURLOPT_FOLLOWLOCATION is enabled.
 * Set to ALL to enable all protocols.
 * As of cURL 7.65.2 it defaults to FTP,
 * FTPS, HTTP and HTTPS.
 * From cURL 7.40.0 to 7.65.1, this defaults to all protocols except
 * FILE, SCP, SMB and
 * SMBS.
 * Prior to cURL 7.40.0, this defaults to all protocols except
 * FILE and SCP.
 * Available protocols are:
 * <p>
 * DICT
 * FILE
 * FTP
 * FTPS
 * GOPHER
 * GOPHERS
 * HTTP
 * HTTPS
 * IMAP
 * IMAPS
 * LDAP
 * LDAPS
 * MQTT
 * POP3
 * POP3S
 * RTMP
 * RTMPE
 * RTMPS
 * RTMPT
 * RTMPTE
 * RTMPTS
 * RTSP
 * SCP
 * SFTP
 * SMB
 * SMBS
 * SMTP
 * SMTPS
 * TELNET
 * TFTP
 * WS
 * WSS
 * </p>.
 * Available as of PHP 8.3.0 and cURL 7.85.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_REDIR_PROTOCOLS_STR', 10319);

/**
 * Accepts a bitmask setting WebSocket behavior options.
 * The only available option is CURLWS_RAW_MODE.
 * Defaults to 0.
 * Available as of PHP 8.3.0 and cURL 7.86.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_WS_OPTIONS', 320);

/**
 * Available as of PHP 8.3.0 and cURL 7.86.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLWS_RAW_MODE', 1);

/**
 * Sets the maximum time in seconds any in memory cached CA certificate store
 * may be kept and reused for new connections.
 * This option accepts any value that can be cast to a valid int.
 * Defaults to 86400 (24 hours).
 * Available as of PHP 8.3.0 and cURL 7.87.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CA_CACHE_TIMEOUT', 321);

/**
 * Set to true for cURL to skip cleanup of resources
 * when recovering from a timeout.
 * This allows for a swift termination of the cURL process
 * at the expense of a possible leak of associated resources.
 * Available as of PHP 8.3.0 and cURL 7.87.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_QUICK_EXIT', 322);

/**
 * Available as of PHP 8.4.0 and cURL 7.88.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_HTTP_VERSION_3ONLY', 31);

/**
 * Always true, what disables support for the @ prefix for
 * uploading files in CURLOPT_POSTFIELDS, which
 * means that values starting with @ can be safely
 * passed as fields. CURLFile may be used for
 * uploads instead.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SAFE_UPLOAD', -1);

// End of curl v.8.4.7
