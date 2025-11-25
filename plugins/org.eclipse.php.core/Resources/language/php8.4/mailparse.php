<?php

// Start of mailparse v.3.1.6

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
 * {@inheritdoc}
 * @param mixed $filename
 */
function mailparse_msg_parse_file ($filename = null) {}

/**
 * {@inheritdoc}
 * @param mixed $fp
 * @param mixed $data
 */
function mailparse_msg_get_part ($fp = null, $data = null) {}

/**
 * {@inheritdoc}
 * @param mixed $fp
 */
function mailparse_msg_get_structure ($fp = null) {}

/**
 * {@inheritdoc}
 * @param mixed $fp
 */
function mailparse_msg_get_part_data ($fp = null) {}

/**
 * {@inheritdoc}
 * @param mixed $fp
 * @param mixed $msgbody
 * @param mixed $callback [optional]
 */
function mailparse_msg_extract_part ($fp = null, $msgbody = null, $callback = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $fp
 * @param mixed $filename
 * @param mixed $callback [optional]
 */
function mailparse_msg_extract_part_file ($fp = null, $filename = null, $callback = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $fp
 * @param mixed $filename
 * @param mixed $callback [optional]
 */
function mailparse_msg_extract_whole_part_file ($fp = null, $filename = null, $callback = NULL) {}

/**
 * {@inheritdoc}
 */
function mailparse_msg_create () {}

/**
 * {@inheritdoc}
 * @param mixed $fp
 */
function mailparse_msg_free ($fp = null) {}

/**
 * {@inheritdoc}
 * @param mixed $fp
 * @param mixed $data
 */
function mailparse_msg_parse ($fp = null, $data = null) {}

/**
 * {@inheritdoc}
 * @param mixed $addresses
 */
function mailparse_rfc822_parse_addresses ($addresses = null) {}

/**
 * {@inheritdoc}
 * @param mixed $fp
 */
function mailparse_determine_best_xfer_encoding ($fp = null) {}

/**
 * {@inheritdoc}
 * @param mixed $source_fp
 * @param mixed $dest_fp
 * @param mixed $encoding
 */
function mailparse_stream_encode ($source_fp = null, $dest_fp = null, $encoding = null) {}

/**
 * {@inheritdoc}
 * @param mixed $fp
 */
function mailparse_uudecode_all ($fp = null) {}

/**
 * {@inheritdoc}
 * @param mixed $header
 */
function mailparse_test ($header = null) {}

define ('MAILPARSE_EXTRACT_OUTPUT', 0);
define ('MAILPARSE_EXTRACT_STREAM', 1);
define ('MAILPARSE_EXTRACT_RETURN', 2);

// End of mailparse v.3.1.6
