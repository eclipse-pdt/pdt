<?php

// Start of mailparse v.3.1.4

class mimemessage  {

	public $data;

	/**
	 * {@inheritdoc}
	 * @param mixed $mode
	 * @param mixed $source
	 */
	public function __construct ($mode = null, $source = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $item_to_find
	 */
	public function get_child ($item_to_find = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function get_child_count () {}

	/**
	 * {@inheritdoc}
	 */
	public function get_parent () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $mode [optional]
	 * @param mixed $arg [optional]
	 */
	public function extract_headers ($mode = NULL, $arg = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $mode [optional]
	 * @param mixed $arg [optional]
	 */
	public function extract_body ($mode = NULL, $arg = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function enum_uue () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $index
	 * @param mixed $mode [optional]
	 * @param mixed $arg [optional]
	 */
	public function extract_uue ($index = null, $mode = NULL, $arg = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function remove () {}

	/**
	 * {@inheritdoc}
	 */
	public function add_child () {}

}

/**
 * Parses a file
 * @link http://www.php.net/manual/en/function.mailparse-msg-parse-file.php
 * @param string $filename 
 * @return resource Returns a MIME resource representing the structure, or
 * false on error.
 */
function mailparse_msg_parse_file (string $filename) {}

/**
 * Returns a handle on a given section in a mimemessage
 * @link http://www.php.net/manual/en/function.mailparse-msg-get-part.php
 * @param resource $mimemail 
 * @param string $mimesection 
 * @return resource 
 */
function mailparse_msg_get_part ($mimemail, string $mimesection) {}

/**
 * Returns an array of mime section names in the supplied message
 * @link http://www.php.net/manual/en/function.mailparse-msg-get-structure.php
 * @param resource $mimemail 
 * @return array 
 */
function mailparse_msg_get_structure ($mimemail): array {}

/**
 * Returns an associative array of info about the message
 * @link http://www.php.net/manual/en/function.mailparse-msg-get-part-data.php
 * @param resource $mimemail 
 * @return array 
 */
function mailparse_msg_get_part_data ($mimemail): array {}

/**
 * Extracts/decodes a message section
 * @link http://www.php.net/manual/en/function.mailparse-msg-extract-part.php
 * @param resource $mimemail 
 * @param string $msgbody 
 * @param callable $callbackfunc [optional] 
 * @return void No value is returned.
 */
function mailparse_msg_extract_part ($mimemail, string $msgbody, callable $callbackfunc = null): void {}

/**
 * Extracts/decodes a message section
 * @link http://www.php.net/manual/en/function.mailparse-msg-extract-part-file.php
 * @param resource $mimemail 
 * @param mixed $filename 
 * @param callable $callbackfunc [optional] 
 * @return string If callbackfunc is not null returns true on
 * success.
 * <p>If callbackfunc is set to null, returns the
 * extracted section as a string.</p>
 * <p>Returns false on error.</p>
 */
function mailparse_msg_extract_part_file ($mimemail, mixed $filename, callable $callbackfunc = null): string {}

/**
 * Extracts a message section including headers without decoding the transfer encoding
 * @link http://www.php.net/manual/en/function.mailparse-msg-extract-whole-part-file.php
 * @param resource $mimemail 
 * @param string $filename 
 * @param callable $callbackfunc [optional] 
 * @return string 
 */
function mailparse_msg_extract_whole_part_file ($mimemail, string $filename, callable $callbackfunc = null): string {}

/**
 * Create a mime mail resource
 * @link http://www.php.net/manual/en/function.mailparse-msg-create.php
 * @return resource Returns a handle that can be used to parse a message.
 */
function mailparse_msg_create () {}

/**
 * Frees a MIME resource
 * @link http://www.php.net/manual/en/function.mailparse-msg-free.php
 * @param resource $mimemail 
 * @return bool Returns true on success or false on failure.
 */
function mailparse_msg_free ($mimemail): bool {}

/**
 * Incrementally parse data into buffer
 * @link http://www.php.net/manual/en/function.mailparse-msg-parse.php
 * @param resource $mimemail 
 * @param string $data 
 * @return bool Returns true on success or false on failure.
 */
function mailparse_msg_parse ($mimemail, string $data): bool {}

/**
 * Parse RFC 822 compliant addresses
 * @link http://www.php.net/manual/en/function.mailparse-rfc822-parse-addresses.php
 * @param string $addresses 
 * @return array Returns an array of associative arrays with the following keys for each
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
function mailparse_rfc822_parse_addresses (string $addresses): array {}

/**
 * Gets the best way of encoding
 * @link http://www.php.net/manual/en/function.mailparse-determine-best-xfer-encoding.php
 * @param resource $fp 
 * @return string Returns one of the character encodings supported by the
 * mbstring module.
 */
function mailparse_determine_best_xfer_encoding ($fp): string {}

/**
 * Streams data from source file pointer, apply encoding and write to destfp
 * @link http://www.php.net/manual/en/function.mailparse-stream-encode.php
 * @param resource $sourcefp 
 * @param resource $destfp 
 * @param string $encoding 
 * @return bool Returns true on success or false on failure.
 */
function mailparse_stream_encode ($sourcefp, $destfp, string $encoding): bool {}

/**
 * Scans the data from fp and extract each embedded uuencoded file
 * @link http://www.php.net/manual/en/function.mailparse-uudecode-all.php
 * @param resource $fp 
 * @return array Returns an array of associative arrays listing filename information.
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
function mailparse_uudecode_all ($fp): array {}

/**
 * {@inheritdoc}
 * @param mixed $header
 */
function mailparse_test ($header = null) {}


/**
 * 
 * @link http://www.php.net/manual/en/mailparse.constants.php
 * @var int
 */
define ('MAILPARSE_EXTRACT_OUTPUT', 0);

/**
 * 
 * @link http://www.php.net/manual/en/mailparse.constants.php
 * @var int
 */
define ('MAILPARSE_EXTRACT_STREAM', 1);

/**
 * 
 * @link http://www.php.net/manual/en/mailparse.constants.php
 * @var int
 */
define ('MAILPARSE_EXTRACT_RETURN', 2);

// End of mailparse v.3.1.4
