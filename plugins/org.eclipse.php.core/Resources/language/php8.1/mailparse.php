<?php

// Start of mailparse v.3.1.4

class mimemessage  {
	public $data;


	/**
	 * @param mixed $mode
	 * @param mixed $source
	 */
	public function __construct ($mode = null, $source = null) {}

	/**
	 * @param mixed $item_to_find
	 */
	public function get_child ($item_to_find = null) {}

	public function get_child_count () {}

	public function get_parent () {}

	/**
	 * @param mixed $mode [optional]
	 * @param mixed $arg [optional]
	 */
	public function extract_headers ($mode = null, $arg = null) {}

	/**
	 * @param mixed $mode [optional]
	 * @param mixed $arg [optional]
	 */
	public function extract_body ($mode = null, $arg = null) {}

	public function enum_uue () {}

	/**
	 * @param mixed $index
	 * @param mixed $mode [optional]
	 * @param mixed $arg [optional]
	 */
	public function extract_uue ($index = null, $mode = null, $arg = null) {}

	public function remove () {}

	public function add_child () {}

}

/**
 * Parses a file
 * @link http://www.php.net/manual/en/function.mailparse-msg-parse-file.php
 * @param string $filename <p>
 * Path to the file holding the message.
 * The file is opened and streamed through the parser.
 * </p>
 * <p>
 * The message contained in filename is supposed to end with a newline
 * (CRLF); otherwise the last line of the message will not be parsed.
 * </p>
 * @return resource a MIME resource representing the structure, or
 * false on error.
 */
function mailparse_msg_parse_file (string $filename) {}

/**
 * Returns a handle on a given section in a mimemessage
 * @link http://www.php.net/manual/en/function.mailparse-msg-get-part.php
 * @param resource $mimemail A valid MIME resource.
 * @param string $mimesection 
 * @return resource 
 */
function mailparse_msg_get_part ($mimemail, string $mimesection) {}

/**
 * Returns an array of mime section names in the supplied message
 * @link http://www.php.net/manual/en/function.mailparse-msg-get-structure.php
 * @param resource $mimemail A valid MIME resource.
 * @return array 
 */
function mailparse_msg_get_structure ($mimemail) {}

/**
 * Returns an associative array of info about the message
 * @link http://www.php.net/manual/en/function.mailparse-msg-get-part-data.php
 * @param resource $mimemail A valid MIME resource.
 * @return array 
 */
function mailparse_msg_get_part_data ($mimemail) {}

/**
 * Extracts/decodes a message section
 * @link http://www.php.net/manual/en/function.mailparse-msg-extract-part.php
 * @param resource $mimemail A valid MIME resource.
 * @param string $msgbody 
 * @param callable $callbackfunc [optional] 
 * @return void 
 */
function mailparse_msg_extract_part ($mimemail, string $msgbody, callable $callbackfunc = null) {}

/**
 * Extracts/decodes a message section
 * @link http://www.php.net/manual/en/function.mailparse-msg-extract-part-file.php
 * @param resource $mimemail A valid MIME resource, created with
 * mailparse_msg_create.
 * @param mixed $filename Can be a file name or a valid stream resource.
 * @param callable $callbackfunc [optional] <p>
 * If set, this must be either a valid callback that will be passed the
 * extracted section, or null to make this function return the
 * extracted section.
 * </p>
 * <p>
 * If not specified, the contents will be sent to "stdout".
 * </p>
 * @return string If callbackfunc is not null returns true on
 * success.
 * <p>
 * If callbackfunc is set to null, returns the
 * extracted section as a string.
 * </p>
 * <p>
 * Returns false on error.
 * </p>
 */
function mailparse_msg_extract_part_file ($mimemail, $filename, callable $callbackfunc = null) {}

/**
 * Extracts a message section including headers without decoding the transfer encoding
 * @link http://www.php.net/manual/en/function.mailparse-msg-extract-whole-part-file.php
 * @param resource $mimemail A valid MIME resource.
 * @param string $filename 
 * @param callable $callbackfunc [optional] 
 * @return string 
 */
function mailparse_msg_extract_whole_part_file ($mimemail, string $filename, callable $callbackfunc = null) {}

/**
 * Create a mime mail resource
 * @link http://www.php.net/manual/en/function.mailparse-msg-create.php
 * @return resource a handle that can be used to parse a message.
 */
function mailparse_msg_create () {}

/**
 * Frees a MIME resource
 * @link http://www.php.net/manual/en/function.mailparse-msg-free.php
 * @param resource $mimemail A valid MIME resource allocated by
 * mailparse_msg_create or 
 * mailparse_msg_parse_file.
 * @return bool true on success or false on failure
 */
function mailparse_msg_free ($mimemail) {}

/**
 * Incrementally parse data into buffer
 * @link http://www.php.net/manual/en/function.mailparse-msg-parse.php
 * @param resource $mimemail A valid MIME resource.
 * @param string $data The final chunk of data is supposed to end with a newline
 * (CRLF); otherwise the last line of the message will not be parsed.
 * @return bool true on success or false on failure
 */
function mailparse_msg_parse ($mimemail, string $data) {}

/**
 * Parse RFC 822 compliant addresses
 * @link http://www.php.net/manual/en/function.mailparse-rfc822-parse-addresses.php
 * @param string $addresses <p>
 * A string containing addresses, like in:
 * Wez Furlong &lt;wez@example.com&gt;, doe@example.com
 * </p>
 * <p>
 * This string must not include the header name.
 * </p>
 * @return array an array of associative arrays with the following keys for each
 * recipient:
 * <table>
 * <tr valign="top">
 * <td>display</td>
 * <td>
 * The recipient name, for display purpose. If this part is not set for a
 * recipient, this key will hold the same value as 
 * address.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>address</td>
 * <td>The email address</td>
 * </tr>
 * <tr valign="top">
 * <td>is_group</td>
 * <td>true if the recipient is a newsgroup, false otherwise.</td>
 * </tr>
 * </table>
 */
function mailparse_rfc822_parse_addresses (string $addresses) {}

/**
 * Gets the best way of encoding
 * @link http://www.php.net/manual/en/function.mailparse-determine-best-xfer-encoding.php
 * @param resource $fp A valid file pointer, which must be seek-able.
 * @return string one of the character encodings supported by the
 * mbstring module.
 */
function mailparse_determine_best_xfer_encoding ($fp) {}

/**
 * Streams data from source file pointer, apply encoding and write to destfp
 * @link http://www.php.net/manual/en/function.mailparse-stream-encode.php
 * @param resource $sourcefp A valid file handle. The file is streamed through the parser.
 * @param resource $destfp The destination file handle in which the encoded data will be written.
 * @param string $encoding One of the character encodings supported by the
 * mbstring module.
 * @return bool true on success or false on failure
 */
function mailparse_stream_encode ($sourcefp, $destfp, string $encoding) {}

/**
 * Scans the data from fp and extract each embedded uuencoded file
 * @link http://www.php.net/manual/en/function.mailparse-uudecode-all.php
 * @param resource $fp A valid file pointer.
 * @return array an array of associative arrays listing filename information.
 * <table>
 * <tr valign="top">
 * <td>filename</td>
 * <td>Path to the temporary file name created</td>
 * </tr>
 * <tr valign="top">
 * <td>origfilename</td>
 * <td>The original filename, for uuencoded parts only</td>
 * </tr>
 * </table>
 * The first filename entry is the message body. The next entries are the
 * decoded uuencoded files.
 */
function mailparse_uudecode_all ($fp) {}

/**
 * @param mixed $header
 */
function mailparse_test ($header = null) {}


/**
 * 
 * @link http://www.php.net/manual/en/mailparse.constants.php
 */
define ('MAILPARSE_EXTRACT_OUTPUT', 0);

/**
 * 
 * @link http://www.php.net/manual/en/mailparse.constants.php
 */
define ('MAILPARSE_EXTRACT_STREAM', 1);

/**
 * 
 * @link http://www.php.net/manual/en/mailparse.constants.php
 */
define ('MAILPARSE_EXTRACT_RETURN', 2);

// End of mailparse v.3.1.4
