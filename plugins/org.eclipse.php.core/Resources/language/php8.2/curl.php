<?php

// Start of curl v.8.2.6

/**
 * A fully opaque class which replaces curl resources as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/class.curlhandle.php
 */
final class CurlHandle  {
}

/**
 * A fully opaque class which replaces curl_multi resources as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/class.curlmultihandle.php
 */
final class CurlMultiHandle  {
}

/**
 * A fully opaque class which replaces curl_share resources as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/class.curlsharehandle.php
 */
final class CurlShareHandle  {
}

/**
 * This class or CURLStringFile should be used to upload a file with
 * CURLOPT_POSTFIELDS.
 * <p>Unserialization of CURLFile instances is not allowed.
 * As of PHP 7.4.0, serialization is forbidden in the first place.</p>
 * @link http://www.php.net/manual/en/class.curlfile.php
 */
class CURLFile  {

	/**
	 * Name of the file to be uploaded.
	 * @var string
	 * @link http://www.php.net/manual/en/class.curlfile.php#curlfile.props.name
	 */
	public string $name;

	/**
	 * MIME type of the file (default is application/octet-stream).
	 * @var string
	 * @link http://www.php.net/manual/en/class.curlfile.php#curlfile.props.mime
	 */
	public string $mime;

	/**
	 * The name of the file in the upload data (defaults to the name property).
	 * @var string
	 * @link http://www.php.net/manual/en/class.curlfile.php#curlfile.props.postname
	 */
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

/**
 * CURLStringFile makes it possible to upload a file directly from a variable.
 * This is similar to CURLFile, but works with the contents of the file, not filename.
 * This class or CURLFile should be used to upload the contents of the file with CURLOPT_POSTFIELDS.
 * @link http://www.php.net/manual/en/class.curlstringfile.php
 */
class CURLStringFile  {

	/**
	 * The contents to be uploaded.
	 * @var string
	 * @link http://www.php.net/manual/en/class.curlstringfile.php#curlstringfile.props.data
	 */
	public string $data;

	/**
	 * The name of the file to be used in the upload data.
	 * @var string
	 * @link http://www.php.net/manual/en/class.curlstringfile.php#curlstringfile.props.postname
	 */
	public string $postname;

	/**
	 * MIME type of the file (default is application/octet-stream).
	 * @var string
	 * @link http://www.php.net/manual/en/class.curlstringfile.php#curlstringfile.props.mime
	 */
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
 * Set an option for the cURL multi handle
 * @link http://www.php.net/manual/en/function.curl-multi-setopt.php
 * @param CurlMultiHandle $multi_handle 
 * @param int $option One of the CURLMOPT_&#42; constants.
 * @param mixed $value The value to be set on option.
 * <p>value should be an int for the
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
 * As of cURL 7.43.0, the value is a bitmask, and you can also pass 2 to try to multiplex the new
 * transfer over an existing HTTP/2 connection if possible.
 * Passing 3 instructs cURL to ask for pipelining and multiplexing
 * independently of each other.
 * As of cURL 7.62.0, setting the pipelining bit has no effect.
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
 * </table></p>
 * <p>The parent cURL handle (the request the client made).</p>
 * <p>A new cURL handle for the pushed request.</p>
 * <p>The push promise headers.</p>
 * @return bool Returns true on success or false on failure.
 */
function curl_multi_setopt (CurlMultiHandle $multi_handle, int $option, mixed $value): bool {}

/**
 * Perform a cURL session
 * @link http://www.php.net/manual/en/function.curl-exec.php
 * @param CurlHandle $handle 
 * @return string|bool Returns true on success or false on failure. However, if the CURLOPT_RETURNTRANSFER
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
 * @return int Returns 0 on success, or one of the CURLM_XXX errors
 * code.
 */
function curl_multi_add_handle (CurlMultiHandle $multi_handle, CurlHandle $handle): int {}

/**
 * Close a set of cURL handles
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
 * @return CurlMultiHandle Returns a cURL multi handle on success, false on failure.
 */
function curl_multi_init (): CurlMultiHandle {}

/**
 * Remove a multi handle from a set of cURL handles
 * @link http://www.php.net/manual/en/function.curl-multi-remove-handle.php
 * @param CurlMultiHandle $multi_handle 
 * @param CurlHandle $handle 
 * @return int Returns 0 on success, or one of the CURLM_XXX error
 * codes.
 */
function curl_multi_remove_handle (CurlMultiHandle $multi_handle, CurlHandle $handle): int {}

/**
 * Wait for activity on any curl_multi connection
 * @link http://www.php.net/manual/en/function.curl-multi-select.php
 * @param CurlMultiHandle $multi_handle 
 * @param float $timeout [optional] 
 * @return int On success, returns the number of descriptors contained in 
 * the descriptor sets. This may be 0 if there was no activity on any
 * of the descriptors. On failure, this function will return -1 on a select
 * failure (from the underlying select system call).
 */
function curl_multi_select (CurlMultiHandle $multi_handle, float $timeout = 1.0): int {}

/**
 * Return string describing error code
 * @link http://www.php.net/manual/en/function.curl-multi-strerror.php
 * @param int $error_code One of the CURLM error codes constants.
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
 * @return int Returns an integer containing the last share curl error number,
 * or false on failure.
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
 * @param mixed $value <table>
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
 * <td>A bitmask of the CURL_VERSION_XXX constants</td>
 * </tr>
 * <tr valign="top">
 * <td>protocols</td>
 * <td>An array of protocols names supported by cURL</td>
 * </tr>
 * </table>
 */
function curl_version (): array|false {}


/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_AUTOREFERER', 58);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_BINARYTRANSFER', 19914);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_BUFFERSIZE', 98);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CAINFO', 10065);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CAPATH', 10097);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CONNECTTIMEOUT', 78);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_COOKIE', 10022);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_COOKIEFILE', 10031);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_COOKIEJAR', 10082);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_COOKIESESSION', 96);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CRLF', 27);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CUSTOMREQUEST', 10036);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DNS_CACHE_TIMEOUT', 92);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DNS_USE_GLOBAL_CACHE', 91);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_EGDSOCKET', 10077);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_ENCODING', 10102);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FAILONERROR', 45);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FILE', 10001);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FILETIME', 69);

/**
 * This constant is not available when open_basedir 
 * is enabled.
 * @link http://www.php.net/manual/en/ini.open-basedir.php
 * @var int
 */
define ('CURLOPT_FOLLOWLOCATION', 52);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FORBID_REUSE', 75);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FRESH_CONNECT', 74);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTPAPPEND', 50);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTPLISTONLY', 48);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTPPORT', 10017);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTP_USE_EPRT', 106);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTP_USE_EPSV', 85);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HEADER', 42);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HEADERFUNCTION', 20079);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HTTP200ALIASES', 10104);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HTTPGET', 80);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HTTPHEADER', 10023);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HTTPPROXYTUNNEL', 61);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HTTP_VERSION', 84);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_INFILE', 10009);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_INFILESIZE', 14);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_INTERFACE', 10062);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_KRB4LEVEL', 10063);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_LOW_SPEED_LIMIT', 19);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_LOW_SPEED_TIME', 20);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_MAXCONNECTS', 71);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_MAXREDIRS', 68);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_NETRC', 51);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_NOBODY', 44);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_NOPROGRESS', 43);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_NOSIGNAL', 99);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PORT', 3);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_POST', 47);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_POSTFIELDS', 10015);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_POSTQUOTE', 10039);
define ('CURLOPT_PREQUOTE', 10093);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PRIVATE', 10103);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROGRESSFUNCTION', 20056);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY', 10004);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXYPORT', 59);

/**
 * Available as of cURL 7.10.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXYTYPE', 101);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXYUSERPWD', 10006);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PUT', 54);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_QUOTE', 10028);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_RANDOM_FILE', 10076);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_RANGE', 10007);
define ('CURLOPT_READDATA', 10009);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_READFUNCTION', 20012);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_REFERER', 10016);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_RESUME_FROM', 21);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_RETURNTRANSFER', 19913);
define ('CURLOPT_SHARE', 10100);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLCERT', 10025);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLCERTPASSWD', 10026);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLCERTTYPE', 10086);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLENGINE', 10089);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLENGINE_DEFAULT', 90);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLKEY', 10087);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLKEYPASSWD', 10026);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLKEYTYPE', 10088);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLVERSION', 32);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSL_CIPHER_LIST', 10083);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSL_VERIFYHOST', 81);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSL_VERIFYPEER', 64);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_STDERR', 10037);
define ('CURLOPT_TELNETOPTIONS', 10070);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TIMECONDITION', 33);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TIMEOUT', 13);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TIMEVALUE', 34);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TRANSFERTEXT', 53);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_UNRESTRICTED_AUTH', 105);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_UPLOAD', 46);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_URL', 10002);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_USERAGENT', 10018);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_USERPWD', 10005);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_VERBOSE', 41);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_WRITEFUNCTION', 20011);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_WRITEHEADER', 10029);
define ('CURLOPT_XFERINFOFUNCTION', 20219);

/**
 * 
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
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_BAD_CONTENT_ENCODING', 61);
define ('CURLE_BAD_DOWNLOAD_RESUME', 36);

/**
 * 
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
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_COULDNT_CONNECT', 7);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_COULDNT_RESOLVE_HOST', 6);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_COULDNT_RESOLVE_PROXY', 5);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FAILED_INIT', 2);

/**
 * 
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
 * 
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
 * 
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
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_COULDNT_USE_REST', 31);
define ('CURLE_FTP_PARTIAL_FILE', 18);

/**
 * 
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
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_WEIRD_227_FORMAT', 14);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_WEIRD_PASS_REPLY', 11);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FTP_WEIRD_PASV_REPLY', 13);

/**
 * 
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
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_FUNCTION_NOT_FOUND', 41);

/**
 * 
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
 * 
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
define ('CURLE_HTTP_RETURNED_ERROR', 22);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_LDAP_CANNOT_BIND', 38);

/**
 * 
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
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_OK', 0);
define ('CURLE_OPERATION_TIMEDOUT', 28);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_OPERATION_TIMEOUTED', 28);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_OUT_OF_MEMORY', 27);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_PARTIAL_FILE', 18);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_READ_ERROR', 26);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_RECV_ERROR', 56);

/**
 * 
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
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_SSL_CERTPROBLEM', 58);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_SSL_CIPHER', 59);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_SSL_CONNECT_ERROR', 35);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_SSL_ENGINE_NOTFOUND', 53);

/**
 * 
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
define ('CURLE_SSL_PINNEDPUBKEYNOTMATCH', 90);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_TELNET_OPTION_SYNTAX', 49);

/**
 * 
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
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_UNSUPPORTED_PROTOCOL', 1);

/**
 * 
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
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_WRITE_ERROR', 23);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_CONNECT_TIME', 3145733);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_CONTENT_LENGTH_DOWNLOAD', 3145743);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_CONTENT_LENGTH_UPLOAD', 3145744);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_CONTENT_TYPE', 1048594);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_EFFECTIVE_URL', 1048577);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_FILETIME', 2097166);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_HEADER_OUT', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_HEADER_SIZE', 2097163);

/**
 * As of cURL 7.10.8, this is a legacy alias of
 * CURLINFO_RESPONSE_CODE
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_HTTP_CODE', 2097154);
define ('CURLINFO_LASTONE', 62);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_NAMELOOKUP_TIME', 3145732);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_PRETRANSFER_TIME', 3145734);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_PRIVATE', 1048597);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_REDIRECT_COUNT', 2097172);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_REDIRECT_TIME', 3145747);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_REQUEST_SIZE', 2097164);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SIZE_DOWNLOAD', 3145736);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SIZE_UPLOAD', 3145735);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SPEED_DOWNLOAD', 3145737);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SPEED_UPLOAD', 3145738);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SSL_VERIFYRESULT', 2097165);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_STARTTRANSFER_TIME', 3145745);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_TOTAL_TIME', 3145731);
define ('CURLINFO_EFFECTIVE_METHOD', 1048634);

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
define ('CURLVERSION_NOW', 10);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLM_BAD_EASY_HANDLE', 2);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLM_BAD_HANDLE', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLM_CALL_MULTI_PERFORM', -1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLM_INTERNAL_ERROR', 4);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLM_OK', 0);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLM_OUT_OF_MEMORY', 3);
define ('CURLM_ADDED_ALREADY', 7);

/**
 * Available since cURL 7.10.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROXY_HTTP', 0);

/**
 * Available since cURL 7.10.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROXY_SOCKS4', 4);

/**
 * Available since cURL 7.10.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROXY_SOCKS5', 5);
define ('CURLSHOPT_NONE', 0);
define ('CURLSHOPT_SHARE', 1);
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
define ('CURL_LOCK_DATA_COOKIE', 2);
define ('CURL_LOCK_DATA_DNS', 3);
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
 * Available since PHP 7.3.0 and cURL 7.10.7
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_ASYNCHDNS', 128);

/**
 * Character conversions supported.
 * Available since PHP 7.3.0 and cURL 7.15.4
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_CONV', 4096);

/**
 * Built with debug capabilities.
 * Available since PHP 7.3.0 and cURL 7.10.6
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_DEBUG', 64);

/**
 * Negotiate auth is supported.
 * Available since PHP 7.3.0 and cURL 7.10.6 (deprecated since 7.38.0)
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_GSSNEGOTIATE', 32);

/**
 * Internationized Domain Names are supported.
 * Available since PHP 7.3.0 and cURL 7.12.0
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
 * Available since cURL 7.33.0
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
 * Available since PHP 7.3.0 and cURL 7.10.6
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_NTLM', 16);

/**
 * SPNEGO auth is supported.
 * Available since PHP 7.3.0 and cURL 7.10.8
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
 * Available since PHP 7.3.0 and cURL 7.13.2
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_SSPI', 2048);

/**
 * 
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
define ('CURLAUTH_NONE', 0);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_NTLM', 8);
define ('CURLINFO_HTTP_CONNECTCODE', 2097174);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTP_CREATE_MISSING_DIRS', 110);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXYAUTH', 111);

/**
 * 
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
define ('CURLINFO_HTTPAUTH_AVAIL', 2097175);

/**
 * Available since cURL 7.10.8
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
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
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTP_SSL', 119);
define ('CURLOPT_NETRC_FILE', 10118);
define ('CURLOPT_MAXFILESIZE_LARGE', 30117);

/**
 * 
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
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_FTPSSLAUTH', 129);
define ('CURLOPT_FTP_ACCOUNT', 10134);
define ('CURLINFO_OS_ERRNO', 2097177);
define ('CURLINFO_NUM_CONNECTS', 2097178);
define ('CURLINFO_SSL_ENGINES', 4194331);
define ('CURLINFO_COOKIELIST', 4194332);

/**
 * Available since cURL 7.14.1
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_COOKIELIST', 10135);
define ('CURLOPT_IGNORE_CONTENT_LENGTH', 136);
define ('CURLOPT_FTP_SKIP_PASV_IP', 137);
define ('CURLOPT_FTP_FILEMETHOD', 138);
define ('CURLOPT_CONNECT_ONLY', 141);
define ('CURLOPT_LOCALPORT', 139);
define ('CURLOPT_LOCALPORTRANGE', 140);
define ('CURLFTPMETHOD_DEFAULT', 0);
define ('CURLFTPMETHOD_MULTICWD', 1);
define ('CURLFTPMETHOD_NOCWD', 2);
define ('CURLFTPMETHOD_SINGLECWD', 3);
define ('CURLINFO_FTP_ENTRY_PATH', 1048606);
define ('CURLOPT_FTP_ALTERNATIVE_TO_USER', 10147);

/**
 * Available since cURL 7.15.5
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_MAX_RECV_SPEED_LARGE', 30146);

/**
 * Available since cURL 7.15.5
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_MAX_SEND_SPEED_LARGE', 30145);
define ('CURLE_SSL_CACERT_BADFILE', 77);
define ('CURLOPT_SSL_SESSIONID_CACHE', 150);

/**
 * Available since cURL 7.16.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLMOPT_PIPELINING', 3);

/**
 * Available since cURL 7.16.1.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_SSH', 79);
define ('CURLOPT_FTP_SSL_CCC', 154);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSH_AUTH_TYPES', 151);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSH_PRIVATE_KEYFILE', 10153);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSH_PUBLIC_KEYFILE', 10152);
define ('CURLFTPSSL_CCC_ACTIVE', 2);
define ('CURLFTPSSL_CCC_NONE', 0);
define ('CURLFTPSSL_CCC_PASSIVE', 1);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CONNECTTIMEOUT_MS', 156);
define ('CURLOPT_HTTP_CONTENT_DECODING', 158);
define ('CURLOPT_HTTP_TRANSFER_DECODING', 157);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TIMEOUT_MS', 155);

/**
 * Available since cURL 7.16.3.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
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
 * @var int
 */
define ('CURLOPT_SSH_HOST_PUBLIC_KEY_MD5', 10162);
define ('CURLOPT_PROXY_TRANSFER_MODE', 166);

/**
 * Available since cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPAUSE_ALL', 5);

/**
 * Available since cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPAUSE_CONT', 0);

/**
 * Available since cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPAUSE_RECV', 1);

/**
 * Available since cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPAUSE_RECV_CONT', 0);

/**
 * Available since cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPAUSE_SEND', 4);

/**
 * Available since cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPAUSE_SEND_CONT', 0);
define ('CURL_READFUNC_PAUSE', 268435457);
define ('CURL_WRITEFUNC_PAUSE', 268435457);

/**
 * Available since cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROXY_SOCKS4A', 6);

/**
 * Available since cURL 7.18.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROXY_SOCKS5_HOSTNAME', 7);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var string
 */
define ('CURLINFO_REDIRECT_URL', 1048607);
define ('CURLINFO_APPCONNECT_TIME', 3145761);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var string
 */
define ('CURLINFO_PRIMARY_IP', 1048608);
define ('CURLOPT_ADDRESS_SCOPE', 171);
define ('CURLOPT_CRLFILE', 10169);
define ('CURLOPT_ISSUERCERT', 10170);

/**
 * 
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
define ('CURLINFO_CERTINFO', 4194338);
define ('CURLOPT_CERTINFO', 172);
define ('CURLOPT_PASSWORD', 10174);
define ('CURLOPT_POSTREDIR', 161);
define ('CURLOPT_PROXYPASSWORD', 10176);
define ('CURLOPT_PROXYUSERNAME', 10175);

/**
 * Available since cURL 7.19.1
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_USERNAME', 10173);

/**
 * Available since PHP 7.0.7 and cURL 7.18.2
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_REDIR_POST_301', 1);

/**
 * Available since PHP 7.0.7 and cURL 7.18.2
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_REDIR_POST_302', 2);

/**
 * Available since PHP 7.0.7 and cURL 7.18.2
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
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
 * @var int
 */
define ('CURLPROXY_HTTP_1_0', 1);

/**
 * Available since PHP 7.0.7 and cURL 7.19.3
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTP_CREATE_DIR', 1);

/**
 * Available since PHP 7.0.7 and cURL 7.19.3
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTP_CREATE_DIR_NONE', 0);

/**
 * Available since PHP 7.0.7 and cURL 7.19.3
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLFTP_CREATE_DIR_RETRY', 2);

/**
 * Debug memory tracking supported.
 * Available since PHP 7.3.6 and cURL 7.19.6
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_CURLDEBUG', 8192);
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
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var string
 */
define ('CURLINFO_LOCAL_IP', 1048617);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_LOCAL_PORT', 2097194);

/**
 * 
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
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

/**
 * TLS-SRP auth is supported.
 * Available since PHP 7.3.0 and cURL 7.21.4
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_TLSAUTH_SRP', 16384);
define ('CURLOPT_ACCEPT_ENCODING', 10102);
define ('CURLOPT_TRANSFER_ENCODING', 207);

/**
 * Available since PHP 7.0.7 and cURL 7.22.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_NTLM_WB', 32);
define ('CURLGSSAPI_DELEGATION_FLAG', 2);
define ('CURLGSSAPI_DELEGATION_POLICY_FLAG', 1);
define ('CURLOPT_GSSAPI_DELEGATION', 210);

/**
 * NTLM delegation to winbind helper is supported.
 * Available since PHP 7.3.0 and cURL 7.22.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_NTLM_WB', 32768);
define ('CURLOPT_ACCEPTTIMEOUT_MS', 212);
define ('CURLOPT_DNS_SERVERS', 10211);
define ('CURLOPT_MAIL_AUTH', 10217);

/**
 * Available since cURL 7.25.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSL_OPTIONS', 216);

/**
 * Available since cURL 7.25.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TCP_KEEPALIVE', 213);

/**
 * Available since cURL 7.25.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TCP_KEEPIDLE', 214);

/**
 * Available since cURL 7.25.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TCP_KEEPINTVL', 215);

/**
 * Available since cURL 7.25.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSLOPT_ALLOW_BEAST', 1);

/**
 * Available since PHP 7.0.7 and cURL 7.25.1
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_REDIR_POST_303', 4);

/**
 * Available since PHP 7.0.7 and cURL 7.28.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSH_AUTH_AGENT', 16);

/**
 * Available since PHP 7.0.7 and cURL 7.30.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLMOPT_CHUNK_LENGTH_PENALTY_SIZE', 30010);

/**
 * Available since PHP 7.0.7 and cURL 7.30.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLMOPT_CONTENT_LENGTH_PENALTY_SIZE', 30009);

/**
 * Available since PHP 7.0.7 and cURL 7.30.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLMOPT_MAX_HOST_CONNECTIONS', 7);

/**
 * Available since PHP 7.0.7 and cURL 7.30.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLMOPT_MAX_PIPELINE_LENGTH', 8);

/**
 * Available since PHP 7.0.7 and cURL 7.30.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLMOPT_MAX_TOTAL_CONNECTIONS', 13);

/**
 * Available since PHP 7.0.7 and cURL 7.31.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SASL_IR', 218);

/**
 * Available since PHP 7.0.7 and cURL 7.33.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DNS_INTERFACE', 10221);

/**
 * Available since PHP 7.0.7 and cURL 7.33.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DNS_LOCAL_IP4', 10222);

/**
 * Available since PHP 7.0.7 and cURL 7.33.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DNS_LOCAL_IP6', 10223);

/**
 * Available since PHP 7.0.7 and cURL 7.33.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_XOAUTH2_BEARER', 10220);

/**
 * Available since cURL 7.33.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_HTTP_VERSION_2_0', 3);

/**
 * HTTP2 support built-in.
 * Available since cURL 7.33.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_HTTP2', 65536);

/**
 * Available since PHP 7.0.7 and cURL 7.34.0
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
 * Available since PHP 7.0.7 and cURL 7.36.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_EXPECT_100_TIMEOUT_MS', 227);

/**
 * Available since PHP 7.0.7 and cURL 7.36.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSL_ENABLE_ALPN', 226);

/**
 * Available since PHP 7.0.7 and cURL 7.36.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSL_ENABLE_NPN', 225);

/**
 * Available since PHP 7.0.7 and cURL 7.37.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLHEADER_SEPARATE', 1);

/**
 * Available since PHP 7.0.7 and cURL 7.37.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLHEADER_UNIFIED', 0);

/**
 * Available since PHP 7.0.7 and cURL 7.37.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HEADEROPT', 229);

/**
 * Available since PHP 7.0.7 and cURL 7.37.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXYHEADER', 10228);

/**
 * Available since PHP 7.0.7 and cURL 7.38.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_NEGOTIATE', 4);

/**
 * Built against a GSS-API library.
 * Available since PHP 7.3.0 and cURL 7.38.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_GSSAPI', 131072);

/**
 * Available since PHP 7.0.7 and cURL 7.39.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PINNEDPUBLICKEY', 10230);

/**
 * Available since PHP 7.0.7 and cURL 7.40.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_UNIX_SOCKET_PATH', 10231);

/**
 * Available since PHP 7.0.7 and cURL 7.40.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_SMB', 67108864);

/**
 * Available since PHP 7.0.7 and cURL 7.40.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROTO_SMBS', 134217728);

/**
 * Kerberos V5 auth is supported.
 * Available since PHP 7.0.7 and cURL 7.40.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_KERBEROS5', 262144);

/**
 * Unix domain sockets support.
 * Available since PHP 7.0.7 and cURL 7.40.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_UNIX_SOCKETS', 524288);

/**
 * Available since PHP 7.0.7 and cURL 7.41.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSL_VERIFYSTATUS', 232);

/**
 * Available since PHP 7.0.7 and cURL 7.42.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PATH_AS_IS', 234);

/**
 * Available since PHP 7.0.7 and cURL 7.42.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSL_FALSESTART', 233);

/**
 * Available since PHP 7.0.7 and cURL 7.43.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_HTTP_VERSION_2', 3);

/**
 * Available since PHP 7.0.7 and cURL 7.43.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PIPEWAIT', 237);

/**
 * Available since PHP 7.0.7 and cURL 7.43.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SERVICE_NAME', 10235);

/**
 * Available since PHP 7.0.7 and cURL 7.43.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SERVICE_NAME', 10236);

/**
 * Available since cURL 7.43.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPIPE_NOTHING', 0);

/**
 * Available since cURL 7.43.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPIPE_HTTP1', 1);

/**
 * Available since cURL 7.43.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPIPE_MULTIPLEX', 2);

/**
 * Available since PHP 7.0.7 and cURL 7.44.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSLOPT_NO_REVOKE', 2);

/**
 * Available since PHP 7.0.7 and cURL 7.45.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DEFAULT_PROTOCOL', 10238);

/**
 * Available since PHP 7.0.7 and cURL 7.46.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_STREAM_WEIGHT', 239);

/**
 * Available since PHP 7.1.0 and cURL 7.44.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLMOPT_PUSHFUNCTION', 20014);

/**
 * Available since PHP 7.1.0 and cURL 7.44.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_PUSH_OK', 0);

/**
 * Available since PHP 7.1.0 and cURL 7.44.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_PUSH_DENY', 1);

/**
 * Available since PHP 7.0.7 and cURL 7.47.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_HTTP_VERSION_2TLS', 4);

/**
 * Mozilla's Public Suffix List, used for cookie domain verification.
 * Available since PHP 7.3.6 and cURL 7.47.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_PSL', 1048576);

/**
 * Available since PHP 7.0.7 and cURL 7.48.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TFTP_NO_OPTIONS', 242);

/**
 * Available since PHP 7.0.7 and cURL 7.49.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_HTTP_VERSION_2_PRIOR_KNOWLEDGE', 5);

/**
 * Available since PHP 7.0.7 and cURL 7.49.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_CONNECT_TO', 10243);

/**
 * Available since PHP 7.0.7 and cURL 7.49.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TCP_FASTOPEN', 244);

/**
 * Available since PHP 7.3.0 and cURL 7.50.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_HTTP_VERSION', 2097198);

/**
 * Available since PHP 7.3.0 and cURL 7.51.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLE_WEIRD_SERVER_REPLY', 8);

/**
 * Available since PHP 7.3.0 and cURL 7.51.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_KEEP_SENDING_ON_ERROR', 245);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_TLSv1_3', 7);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_HTTPS_PROXY', 2097152);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_PROTOCOL', 2097200);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_PROXY_SSL_VERIFYRESULT', 2097199);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SCHEME', 1048625);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PRE_PROXY', 10262);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_CAINFO', 10246);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_CAPATH', 10247);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_CRLFILE', 10260);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_KEYPASSWD', 10258);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_PINNEDPUBLICKEY', 10263);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSL_CIPHER_LIST', 10259);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSL_OPTIONS', 261);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSL_VERIFYHOST', 249);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSL_VERIFYPEER', 248);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSLCERT', 10254);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSLCERTTYPE', 10255);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSLKEY', 10256);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSLKEYTYPE', 10257);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSLVERSION', 250);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_TLSAUTH_PASSWORD', 10252);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_TLSAUTH_TYPE', 10253);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_TLSAUTH_USERNAME', 10251);

/**
 * Available since PHP 7.3.0 and cURL 7.52.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLPROXY_HTTPS', 2);

/**
 * Available since PHP 7.3.0 and cURL 7.53.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_MAX_READ_SIZE', 10485760);

/**
 * Available since PHP 7.3.0 and cURL 7.53.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_ABSTRACT_UNIX_SOCKET', 10264);

/**
 * Available since PHP 7.3.0 and cURL 7.54.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_MAX_DEFAULT', 65536);

/**
 * Available since PHP 7.3.0 and cURL 7.54.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_MAX_NONE', 0);

/**
 * Available since PHP 7.3.0 and cURL 7.54.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_MAX_TLSv1_0', 262144);

/**
 * Available since PHP 7.3.0 and cURL 7.54.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_MAX_TLSv1_1', 327680);

/**
 * Available since PHP 7.3.0 and cURL 7.54.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_MAX_TLSv1_2', 393216);

/**
 * Available since PHP 7.3.0 and cURL 7.54.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_SSLVERSION_MAX_TLSv1_3', 458752);

/**
 * Available since PHP 7.3.0 and cURL 7.54.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SUPPRESS_CONNECT_HEADERS', 265);

/**
 * Available since PHP 7.3.0 and cURL 7.54.1
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_GSSAPI', 4);

/**
 * Available since PHP 7.3.0 and cURL 7.55.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_CONTENT_LENGTH_DOWNLOAD_T', 6291471);

/**
 * Available since PHP 7.3.0 and cURL 7.55.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_CONTENT_LENGTH_UPLOAD_T', 6291472);

/**
 * Available since PHP 7.3.0 and cURL 7.50.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SIZE_DOWNLOAD_T', 6291464);

/**
 * Available since PHP 7.3.0 and cURL 7.50.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SIZE_UPLOAD_T', 6291463);

/**
 * Available since PHP 7.3.0 and cURL 7.50.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SPEED_DOWNLOAD_T', 6291465);

/**
 * Available since PHP 7.3.0 and cURL 7.50.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_SPEED_UPLOAD_T', 6291466);

/**
 * Available since PHP 7.3.0 and cURL 7.55.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_REQUEST_TARGET', 10266);

/**
 * Available since PHP 7.3.0 and cURL 7.55.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SOCKS5_AUTH', 267);

/**
 * Available since PHP 7.3.0 and cURL 7.56.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSH_COMPRESSION', 268);

/**
 * Available since PHP 7.3.0 and cURL 7.56.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_MULTI_SSL', 4194304);

/**
 * Available since PHP 7.3.0 and cURL 7.57.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_BROTLI', 8388608);

/**
 * Available since PHP 7.3.0 and cURL 7.10.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_LOCK_DATA_CONNECT', 5);

/**
 * Available since PHP 7.3.0 and cURL 7.58.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLSSH_AUTH_GSSAPI', 32);

/**
 * Available since PHP 7.3.0 and cURL 7.59.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_FILETIME_T', 6291470);

/**
 * Available since PHP 7.3.0 and cURL 7.59.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HAPPY_EYEBALLS_TIMEOUT_MS', 271);

/**
 * Available since PHP 7.3.0 and cURL 7.59.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TIMEVALUE_LARGE', 30270);

/**
 * Available since PHP 7.3.0 and cURL 7.60.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DNS_SHUFFLE_ADDRESSES', 275);

/**
 * Available since PHP 7.3.0 and cURL 7.60.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_HAPROXYPROTOCOL', 274);

/**
 * Available since PHP 7.3.0 and cURL 7.61.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_LOCK_DATA_PSL', 6);

/**
 * Available since PHP 7.3.0 and cURL 7.61.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLAUTH_BEARER', 64);

/**
 * Available since PHP 7.3.0 and cURL 7.61.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_APPCONNECT_TIME_T', 6291512);

/**
 * Available since PHP 7.3.0 and cURL 7.61.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_CONNECT_TIME_T', 6291508);

/**
 * Available since PHP 7.3.0 and cURL 7.61.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_NAMELOOKUP_TIME_T', 6291507);

/**
 * Available since PHP 7.3.0 and cURL 7.61.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_PRETRANSFER_TIME_T', 6291509);

/**
 * Available since PHP 7.3.0 and cURL 7.61.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_REDIRECT_TIME_T', 6291511);

/**
 * Available since PHP 7.3.0 and cURL 7.61.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_STARTTRANSFER_TIME_T', 6291510);

/**
 * Available since PHP 7.3.0 and cURL 7.61.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLINFO_TOTAL_TIME_T', 6291506);

/**
 * Available since PHP 7.3.0 and cURL 7.61.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DISALLOW_USERNAME_IN_URL', 278);

/**
 * Available since PHP 7.3.0 and cURL 7.61.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_TLS13_CIPHERS', 10277);

/**
 * Available since PHP 7.3.0 and cURL 7.61.0
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_TLS13_CIPHERS', 10276);

/**
 * Provides the DNS-over-HTTPS URL.
 * Available as of PHP 8.1.0 and cURL 7.62.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_DOH_URL', 10279);
define ('CURLOPT_UPKEEP_INTERVAL_MS', 281);
define ('CURLOPT_UPLOAD_BUFFERSIZE', 280);
define ('CURLOPT_HTTP09_ALLOWED', 285);
define ('CURLALTSVC_H1', 8);
define ('CURLALTSVC_H2', 16);
define ('CURLALTSVC_H3', 32);
define ('CURLALTSVC_READONLYFILE', 4);
define ('CURLOPT_ALTSVC', 10287);
define ('CURLOPT_ALTSVC_CTRL', 286);

/**
 * Available since PHP 7.3.6 and cURL 7.64.1
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURL_VERSION_ALTSVC', 16777216);
define ('CURLOPT_MAXAGE_CONN', 288);
define ('CURLOPT_SASL_AUTHZID', 10289);
define ('CURL_VERSION_HTTP3', 33554432);
define ('CURLINFO_RETRY_AFTER', 6291513);
define ('CURLMOPT_MAX_CONCURRENT_STREAMS', 16);
define ('CURLSSLOPT_NO_PARTIALCHAIN', 4);
define ('CURLOPT_MAIL_RCPT_ALLLOWFAILS', 290);
define ('CURLSSLOPT_REVOKE_BEST_EFFORT', 8);

/**
 * Issuer SSL certificate from memory blob.
 * Available as of PHP 8.1.0 and cURL 7.71.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_ISSUERCERT_BLOB', 40295);

/**
 * Proxy issuer SSL certificate filename.
 * Available as of PHP 8.1.0 and cURL 7.71.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_ISSUERCERT', 10296);

/**
 * Proxy issuer SSL certificate from memory blob.
 * Available as of PHP 8.1.0 and cURL 7.71.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_ISSUERCERT_BLOB', 40297);

/**
 * SSL proxy client certificate from memory blob.
 * Available as of PHP 8.1.0 and cURL 7.71.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSLCERT_BLOB', 40293);

/**
 * Private key for proxy cert from memory blob.
 * Available as of PHP 8.1.0 and cURL 7.71.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_PROXY_SSLKEY_BLOB', 40294);

/**
 * SSL client certificate from memory blob.
 * Available as of PHP 8.1.0 and cURL 7.71.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLCERT_BLOB', 40291);

/**
 * Private key for client cert from memory blob.
 * Available as of PHP 8.1.0 and cURL 7.71.0.
 * @link http://www.php.net/manual/en/curl.constants.php
 * @var int
 */
define ('CURLOPT_SSLKEY_BLOB', 40292);
define ('CURLPROTO_MQTT', 268435456);
define ('CURLSSLOPT_NATIVE_CA', 16);
define ('CURL_VERSION_UNICODE', 134217728);
define ('CURL_VERSION_ZSTD', 67108864);
define ('CURLE_PROXY', 97);
define ('CURLINFO_PROXY_ERROR', 2097211);
define ('CURLOPT_SSL_EC_CURVES', 10298);
define ('CURLPX_BAD_ADDRESS_TYPE', 1);
define ('CURLPX_BAD_VERSION', 2);
define ('CURLPX_CLOSED', 3);
define ('CURLPX_GSSAPI', 4);
define ('CURLPX_GSSAPI_PERMSG', 5);
define ('CURLPX_GSSAPI_PROTECTION', 6);
define ('CURLPX_IDENTD', 7);
define ('CURLPX_IDENTD_DIFFER', 8);
define ('CURLPX_LONG_HOSTNAME', 9);
define ('CURLPX_LONG_PASSWD', 10);
define ('CURLPX_LONG_USER', 11);
define ('CURLPX_NO_AUTH', 12);
define ('CURLPX_OK', 0);
define ('CURLPX_RECV_ADDRESS', 13);
define ('CURLPX_RECV_AUTH', 14);
define ('CURLPX_RECV_CONNECT', 15);
define ('CURLPX_RECV_REQACK', 16);
define ('CURLPX_REPLY_ADDRESS_TYPE_NOT_SUPPORTED', 17);
define ('CURLPX_REPLY_COMMAND_NOT_SUPPORTED', 18);
define ('CURLPX_REPLY_CONNECTION_REFUSED', 19);
define ('CURLPX_REPLY_GENERAL_SERVER_FAILURE', 20);
define ('CURLPX_REPLY_HOST_UNREACHABLE', 21);
define ('CURLPX_REPLY_NETWORK_UNREACHABLE', 22);
define ('CURLPX_REPLY_NOT_ALLOWED', 23);
define ('CURLPX_REPLY_TTL_EXPIRED', 24);
define ('CURLPX_REPLY_UNASSIGNED', 25);
define ('CURLPX_REQUEST_FAILED', 26);
define ('CURLPX_RESOLVE_HOST', 27);
define ('CURLPX_SEND_AUTH', 28);
define ('CURLPX_SEND_CONNECT', 29);
define ('CURLPX_SEND_REQUEST', 30);
define ('CURLPX_UNKNOWN_FAIL', 31);
define ('CURLPX_UNKNOWN_MODE', 32);
define ('CURLPX_USER_REJECTED', 33);
define ('CURLHSTS_ENABLE', 1);
define ('CURLHSTS_READONLYFILE', 2);
define ('CURLOPT_HSTS', 10300);
define ('CURLOPT_HSTS_CTRL', 299);
define ('CURL_VERSION_HSTS', 268435456);
define ('CURLAUTH_AWS_SIGV4', 128);
define ('CURLOPT_AWS_SIGV4', 10305);
define ('CURLINFO_REFERER', 1048636);
define ('CURLOPT_DOH_SSL_VERIFYHOST', 307);
define ('CURLOPT_DOH_SSL_VERIFYPEER', 306);
define ('CURLOPT_DOH_SSL_VERIFYSTATUS', 308);
define ('CURL_VERSION_GSASL', 536870912);
define ('CURLOPT_CAINFO_BLOB', 40309);
define ('CURLOPT_PROXY_CAINFO_BLOB', 40310);
define ('CURLSSLOPT_AUTO_CLIENT_CERT', 32);
define ('CURLOPT_MAXLIFETIME_CONN', 314);
define ('CURLOPT_SSH_HOST_PUBLIC_KEY_SHA256', 10311);
define ('CURLOPT_SAFE_UPLOAD', -1);

// End of curl v.8.2.6
