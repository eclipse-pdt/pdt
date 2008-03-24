<?php

// Start of mysqli v.0.1

class mysqli_sql_exception extends RuntimeException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $sqlstate;


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

	public function __toString () {}

}

final class mysqli_driver  {

	public function embedded_server_start () {}

	public function embedded_server_end () {}

}

class mysqli  {

	public function autocommit () {}

	public function change_user () {}

	public function character_set_name () {}

	public function client_encoding () {}

	public function close () {}

	public function commit () {}

	public function connect () {}

	public function debug () {}

	public function disable_reads_from_master () {}

	public function disable_rpl_parse () {}

	public function dump_debug_info () {}

	public function enable_reads_from_master () {}

	public function enable_rpl_parse () {}

	public function get_charset () {}

	public function get_client_info () {}

	public function get_server_info () {}

	public function get_warnings () {}

	public function init () {}

	public function kill () {}

	public function set_local_infile_default () {}

	public function set_local_infile_handler () {}

	public function master_query () {}

	public function multi_query () {}

	public function mysqli () {}

	public function more_results () {}

	public function next_result () {}

	public function options () {}

	public function ping () {}

	public function prepare () {}

	public function query () {}

	public function real_connect () {}

	public function real_escape_string () {}

	public function escape_string () {}

	public function real_query () {}

	public function rollback () {}

	public function rpl_parse_enabled () {}

	public function rpl_probe () {}

	public function rpl_query_type () {}

	public function select_db () {}

	public function set_charset () {}

	public function set_opt () {}

	public function slave_query () {}

	public function ssl_set () {}

	public function stat () {}

	public function stmt_init () {}

	public function store_result () {}

	public function thread_safe () {}

	public function use_result () {}

}

final class mysqli_warning  {

	protected function __construct () {}

	public function next () {}

}

class mysqli_result  {

	public function mysqli_result () {}

	public function close () {}

	public function free () {}

	public function data_seek () {}

	public function fetch_field () {}

	public function fetch_fields () {}

	public function fetch_field_direct () {}

	public function fetch_array () {}

	public function fetch_assoc () {}

	public function fetch_object () {}

	public function fetch_row () {}

	public function field_count () {}

	public function field_seek () {}

	public function free_result () {}

}

class mysqli_stmt  {

	public function mysqli_stmt () {}

	public function attr_get () {}

	public function attr_set () {}

	/**
	 * @param var1
	 */
	public function bind_param ($var1) {}

	public function bind_result () {}

	public function close () {}

	public function data_seek () {}

	public function execute () {}

	public function fetch () {}

	public function get_warnings () {}

	public function result_metadata () {}

	public function num_rows () {}

	public function send_long_data () {}

	public function stmt () {}

	public function free_result () {}

	public function reset () {}

	public function prepare () {}

	public function store_result () {}

}

function mysqli_affected_rows () {}

function mysqli_autocommit () {}

function mysqli_change_user () {}

function mysqli_character_set_name () {}

function mysqli_close () {}

function mysqli_commit () {}

function mysqli_connect () {}

function mysqli_connect_errno () {}

function mysqli_connect_error () {}

function mysqli_data_seek () {}

function mysqli_debug () {}

/**
 * Disable reads from master
 * @link http://php.net/manual/en/function.mysqli-disable-reads-from-master.php
 * @return void 
 */
function mysqli_disable_reads_from_master () {}

/**
 * Disable RPL parse
 * @link http://php.net/manual/en/function.mysqli-disable-rpl-parse.php
 * @param link mysqli
 * @return bool 
 */
function mysqli_disable_rpl_parse (mysqli $link) {}

function mysqli_dump_debug_info () {}

/**
 * Enable reads from master
 * @link http://php.net/manual/en/function.mysqli-enable-reads-from-master.php
 * @param link mysqli
 * @return bool 
 */
function mysqli_enable_reads_from_master (mysqli $link) {}

/**
 * Enable RPL parse
 * @link http://php.net/manual/en/function.mysqli-enable-rpl-parse.php
 * @param link mysqli
 * @return bool 
 */
function mysqli_enable_rpl_parse (mysqli $link) {}

function mysqli_embedded_server_end () {}

function mysqli_embedded_server_start () {}

function mysqli_errno () {}

function mysqli_error () {}

function mysqli_stmt_execute () {}

/**
 * Alias for <function>mysqli_stmt_execute</function>
 * @link http://php.net/manual/en/function.mysqli-execute.php
 */
function mysqli_execute () {}

function mysqli_fetch_field () {}

function mysqli_fetch_fields () {}

function mysqli_fetch_field_direct () {}

function mysqli_fetch_lengths () {}

function mysqli_fetch_array () {}

function mysqli_fetch_assoc () {}

function mysqli_fetch_object () {}

function mysqli_fetch_row () {}

function mysqli_field_count () {}

function mysqli_field_seek () {}

function mysqli_field_tell () {}

function mysqli_free_result () {}

function mysqli_get_charset () {}

function mysqli_get_client_info () {}

function mysqli_get_client_version () {}

function mysqli_get_host_info () {}

function mysqli_get_proto_info () {}

function mysqli_get_server_info () {}

function mysqli_get_server_version () {}

function mysqli_get_warnings () {}

function mysqli_init () {}

function mysqli_info () {}

function mysqli_insert_id () {}

function mysqli_kill () {}

function mysqli_set_local_infile_default () {}

function mysqli_set_local_infile_handler () {}

/**
 * Enforce execution of a query on the master in a master/slave setup
 * @link http://php.net/manual/en/function.mysqli-master-query.php
 * @param link mysqli
 * @param query string
 * @return bool 
 */
function mysqli_master_query (mysqli $link, $query) {}

function mysqli_more_results () {}

function mysqli_multi_query () {}

function mysqli_next_result () {}

function mysqli_num_fields () {}

function mysqli_num_rows () {}

function mysqli_options () {}

function mysqli_ping () {}

function mysqli_prepare () {}

/**
 * Enables or disables internal report functions
 * @link http://php.net/manual/en/function.mysqli-report.php
 * @param flags int
 * @return bool 
 */
function mysqli_report ($flags) {}

function mysqli_query () {}

function mysqli_real_connect () {}

function mysqli_real_escape_string () {}

function mysqli_real_query () {}

function mysqli_rollback () {}

/**
 * Check if RPL parse is enabled
 * @link http://php.net/manual/en/function.mysqli-rpl-parse-enabled.php
 * @param link mysqli
 * @return int 
 */
function mysqli_rpl_parse_enabled (mysqli $link) {}

/**
 * RPL probe
 * @link http://php.net/manual/en/function.mysqli-rpl-probe.php
 * @param link mysqli
 * @return bool 
 */
function mysqli_rpl_probe (mysqli $link) {}

/**
 * Returns RPL query type
 * @link http://php.net/manual/en/function.mysqli-rpl-query-type.php
 * @param query string
 * @return int 
 */
function mysqli_rpl_query_type ($query) {}

function mysqli_select_db () {}

function mysqli_set_charset () {}

function mysqli_stmt_attr_get () {}

function mysqli_stmt_attr_set () {}

function mysqli_stmt_field_count () {}

function mysqli_stmt_init () {}

function mysqli_stmt_prepare () {}

function mysqli_stmt_result_metadata () {}

function mysqli_stmt_send_long_data () {}

/**
 * @param var1
 * @param var2
 */
function mysqli_stmt_bind_param ($var1, $var2) {}

/**
 * @param var1
 */
function mysqli_stmt_bind_result ($var1) {}

function mysqli_stmt_fetch () {}

function mysqli_stmt_free_result () {}

function mysqli_stmt_get_warnings () {}

function mysqli_stmt_insert_id () {}

function mysqli_stmt_reset () {}

function mysqli_stmt_param_count () {}

/**
 * Send the query and return
 * @link http://php.net/manual/en/function.mysqli-send-query.php
 * @param query string
 * @return bool 
 */
function mysqli_send_query ($query) {}

/**
 * Force execution of a query on a slave in a master/slave setup
 * @link http://php.net/manual/en/function.mysqli-slave-query.php
 * @param link mysqli
 * @param query string
 * @return bool 
 */
function mysqli_slave_query (mysqli $link, $query) {}

function mysqli_sqlstate () {}

function mysqli_ssl_set () {}

function mysqli_stat () {}

function mysqli_stmt_affected_rows () {}

function mysqli_stmt_close () {}

function mysqli_stmt_data_seek () {}

function mysqli_stmt_errno () {}

function mysqli_stmt_error () {}

function mysqli_stmt_num_rows () {}

function mysqli_stmt_sqlstate () {}

function mysqli_store_result () {}

function mysqli_stmt_store_result () {}

function mysqli_thread_id () {}

function mysqli_thread_safe () {}

function mysqli_use_result () {}

function mysqli_warning_count () {}

/**
 * Alias for <function>mysqli_stmt_bind_param</function>
 * @link http://php.net/manual/en/function.mysqli-bind-param.php
 * @param var1
 * @param var2
 */
function mysqli_bind_param ($var1, $var2) {}

/**
 * Alias for <function>mysqli_stmt_bind_result</function>
 * @link http://php.net/manual/en/function.mysqli-bind-result.php
 * @param var1
 */
function mysqli_bind_result ($var1) {}

/**
 * Alias of <function>mysqli_character_set_name</function>
 * @link http://php.net/manual/en/function.mysqli-client-encoding.php
 */
function mysqli_client_encoding () {}

/**
 * Alias of <function>mysqli_real_escape_string</function>
 * @link http://php.net/manual/en/function.mysqli-escape-string.php
 */
function mysqli_escape_string () {}

/**
 * Alias for <function>mysqli_stmt_fetch</function>
 * @link http://php.net/manual/en/function.mysqli-fetch.php
 */
function mysqli_fetch () {}

/**
 * Alias for <function>mysqli_stmt_param_count</function>
 * @link http://php.net/manual/en/function.mysqli-param-count.php
 */
function mysqli_param_count () {}

/**
 * Alias for <function>mysqli_stmt_result_metadata</function>
 * @link http://php.net/manual/en/function.mysqli-get-metadata.php
 */
function mysqli_get_metadata () {}

/**
 * Alias for <function>mysqli_stmt_send_long_data</function>
 * @link http://php.net/manual/en/function.mysqli-send-long-data.php
 */
function mysqli_send_long_data () {}

/**
 * Alias of <function>mysqli_options</function>
 * @link http://php.net/manual/en/function.mysqli-set-opt.php
 */
function mysqli_set_opt () {}


/**
 * Read options from the named group from my.cnf
 * or the file specified with MYSQLI_READ_DEFAULT_FILE
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_READ_DEFAULT_GROUP', 5);

/**
 * Read options from the named option file instead of from my.cnf
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_READ_DEFAULT_FILE', 4);

/**
 * Connect timeout in seconds
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_OPT_CONNECT_TIMEOUT', 0);

/**
 * Enables command LOAD LOCAL INFILE
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_OPT_LOCAL_INFILE', 8);

/**
 * Command to execute when connecting to MySQL server. Will automatically be re-executed when reconnecting.
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_INIT_COMMAND', 3);

/**
 * Use SSL (encrypted protocol). This option should not be set by application programs; 
 * it is set internally in the MySQL client library
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CLIENT_SSL', 2048);

/**
 * Use compression protocol
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CLIENT_COMPRESS', 32);

/**
 * Allow interactive_timeout seconds (instead of wait_timeout seconds) of inactivity before closing the connection. 
 * The client's session wait_timeout variable will be set to the value of the session interactive_timeout variable.
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CLIENT_INTERACTIVE', 1024);

/**
 * Allow spaces after function names. Makes all functions names reserved words.
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CLIENT_IGNORE_SPACE', 256);

/**
 * Don't allow the db_name.tbl_name.col_name syntax.
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CLIENT_NO_SCHEMA', 16);
define ('MYSQLI_CLIENT_FOUND_ROWS', 2);

/**
 * For using buffered resultsets
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_STORE_RESULT', 0);

/**
 * For using unbuffered resultsets
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_USE_RESULT', 1);

/**
 * Columns are returned into the array having the fieldname as the array index.
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_ASSOC', 1);

/**
 * Columns are returned into the array having an enumerated index.
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_NUM', 2);

/**
 * Columns are returned into the array having both a numerical index and the fieldname as the associative index.
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_BOTH', 3);
define ('MYSQLI_STMT_ATTR_UPDATE_MAX_LENGTH', 0);
define ('MYSQLI_STMT_ATTR_CURSOR_TYPE', 1);
define ('MYSQLI_CURSOR_TYPE_NO_CURSOR', 0);
define ('MYSQLI_CURSOR_TYPE_READ_ONLY', 1);
define ('MYSQLI_CURSOR_TYPE_FOR_UPDATE', 2);
define ('MYSQLI_CURSOR_TYPE_SCROLLABLE', 4);
define ('MYSQLI_STMT_ATTR_PREFETCH_ROWS', 2);

/**
 * Indicates that a field is defined as NOT NULL
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_NOT_NULL_FLAG', 1);

/**
 * Field is part of a primary index
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_PRI_KEY_FLAG', 2);

/**
 * Field is part of a unique index.
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_UNIQUE_KEY_FLAG', 4);

/**
 * Field is part of an index.
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_MULTIPLE_KEY_FLAG', 8);

/**
 * Field is defined as BLOB
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_BLOB_FLAG', 16);

/**
 * Field is defined as UNSIGNED
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_UNSIGNED_FLAG', 32);

/**
 * Field is defined as ZEROFILL
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_ZEROFILL_FLAG', 64);

/**
 * Field is defined as AUTO_INCREMENT
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_AUTO_INCREMENT_FLAG', 512);

/**
 * Field is defined as TIMESTAMP
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TIMESTAMP_FLAG', 1024);

/**
 * Field is defined as SET
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_SET_FLAG', 2048);

/**
 * Field is defined as NUMERIC
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_NUM_FLAG', 32768);

/**
 * Field is part of an multi-index
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_PART_KEY_FLAG', 16384);

/**
 * Field is part of GROUP BY
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_GROUP_FLAG', 32768);

/**
 * Field is defined as DECIMAL
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_DECIMAL', 0);

/**
 * Field is defined as TINYINT
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_TINY', 1);

/**
 * Field is defined as INT
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_SHORT', 2);

/**
 * Field is defined as INT
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_LONG', 3);

/**
 * Field is defined as FLOAT
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_FLOAT', 4);

/**
 * Field is defined as DOUBLE
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_DOUBLE', 5);

/**
 * Field is defined as DEFAULT NULL
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_NULL', 6);

/**
 * Field is defined as TIMESTAMP
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_TIMESTAMP', 7);

/**
 * Field is defined as BIGINT
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_LONGLONG', 8);

/**
 * Field is defined as MEDIUMINT
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_INT24', 9);

/**
 * Field is defined as DATE
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_DATE', 10);

/**
 * Field is defined as TIME
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_TIME', 11);

/**
 * Field is defined as DATETIME
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_DATETIME', 12);

/**
 * Field is defined as YEAR
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_YEAR', 13);

/**
 * Field is defined as DATE
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_NEWDATE', 14);

/**
 * Field is defined as ENUM
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_ENUM', 247);

/**
 * Field is defined as SET
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_SET', 248);

/**
 * Field is defined as TINYBLOB
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_TINY_BLOB', 249);

/**
 * Field is defined as MEDIUMBLOB
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_MEDIUM_BLOB', 250);

/**
 * Field is defined as LONGBLOB
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_LONG_BLOB', 251);

/**
 * Field is defined as BLOB
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_BLOB', 252);

/**
 * Field is defined as VARCHAR
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_VAR_STRING', 253);

/**
 * Field is defined as CHAR
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_STRING', 254);
define ('MYSQLI_TYPE_CHAR', 1);
define ('MYSQLI_TYPE_INTERVAL', 247);

/**
 * Field is defined as GEOMETRY
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_GEOMETRY', 255);

/**
 * Precision math DECIMAL or NUMERIC field (MySQL 5.0.3 and up)
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_NEWDECIMAL', 246);

/**
 * Field is defined as BIT (MySQL 5.0.3 and up)
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_BIT', 16);
define ('MYSQLI_SET_CHARSET_NAME', 7);
define ('MYSQLI_RPL_MASTER', 0);
define ('MYSQLI_RPL_SLAVE', 1);
define ('MYSQLI_RPL_ADMIN', 2);

/**
 * No more data available for bind variable
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_NO_DATA', 100);

/**
 * Data truncation occurred. Available since PHP 5.1.0 and MySQL 5.0.5.
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_DATA_TRUNCATED', 101);
define ('MYSQLI_REPORT_INDEX', 4);
define ('MYSQLI_REPORT_ERROR', 1);
define ('MYSQLI_REPORT_STRICT', 2);
define ('MYSQLI_REPORT_ALL', 255);
define ('MYSQLI_REPORT_OFF', 0);

// End of mysqli v.0.1
?>
