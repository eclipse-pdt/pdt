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

final class mysqli_driver  {
	public $client_info;
	public $client_version;
	public $driver_version;
	public $embedded;
	public $reconnect;
	public $report_mode;

}

class mysqli  {
	public $affected_rows;
	public $client_info;
	public $client_version;
	public $connect_errno;
	public $connect_error;
	public $errno;
	public $error;
	public $field_count;
	public $host_info;
	public $info;
	public $insert_id;
	public $server_info;
	public $server_version;
	public $sqlstate;
	public $protocol_version;
	public $thread_id;
	public $warning_count;


	public function autocommit () {}

	public function change_user () {}

	public function character_set_name () {}

	public function client_encoding () {}

	public function close () {}

	public function commit () {}

	public function connect () {}

	public function dump_debug_info () {}

	public function debug () {}

	public function get_charset () {}

	public function get_client_info () {}

	public function get_server_info () {}

	public function get_warnings () {}

	public function init () {}

	public function kill () {}

	public function set_local_infile_default () {}

	public function set_local_infile_handler () {}

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

	public function select_db () {}

	public function set_charset () {}

	public function set_opt () {}

	public function ssl_set () {}

	public function stat () {}

	public function stmt_init () {}

	public function store_result () {}

	public function thread_safe () {}

	public function use_result () {}

	public function refresh () {}

}

final class mysqli_warning  {
	public $message;
	public $sqlstate;
	public $errno;


	protected function __construct () {}

	public function next () {}

}

class mysqli_result  {
	public $current_field;
	public $field_count;
	public $lengths;
	public $num_rows;
	public $type;


	public function __construct () {}

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

	public function field_seek () {}

	public function free_result () {}

}

class mysqli_stmt  {
	public $affected_rows;
	public $insert_id;
	public $num_rows;
	public $param_count;
	public $field_count;
	public $errno;
	public $error;
	public $sqlstate;
	public $id;


	public function __construct () {}

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

function mysqli_dump_debug_info () {}

function mysqli_debug () {}

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
 * @param flags int <p>
 * <table>
 * Supported flags
 * <tr valign="top">
 * <td>Name</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>MYSQLI_REPORT_OFF</td>
 * <td>Turns reporting off</td>
 * </tr>
 * <tr valign="top">
 * <td>MYSQLI_REPORT_ERROR</td>
 * <td>Report errors from mysqli function calls</td>
 * </tr>
 * <tr valign="top">
 * <td>MYSQLI_REPORT_STRICT</td>
 * <td>Report warnings from mysqli function calls</td>
 * </tr>
 * <tr valign="top">
 * <td>MYSQLI_REPORT_INDEX</td>
 * <td>Report if no index or bad index was used in a query</td>
 * </tr>
 * <tr valign="top">
 * <td>MYSQLI_REPORT_ALL</td>
 * <td>Set all options (report all)</td>
 * </tr>
 * </table>
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function mysqli_report ($flags) {}

function mysqli_query () {}

function mysqli_real_connect () {}

function mysqli_real_escape_string () {}

function mysqli_real_query () {}

function mysqli_rollback () {}

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

function mysqli_stmt_store_result () {}

function mysqli_store_result () {}

function mysqli_thread_id () {}

function mysqli_thread_safe () {}

function mysqli_use_result () {}

function mysqli_warning_count () {}

function mysqli_refresh () {}

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
 * <p>
 * Read options from the named group from my.cnf
 * or the file specified with MYSQLI_READ_DEFAULT_FILE
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_READ_DEFAULT_GROUP', 5);

/**
 * <p>
 * Read options from the named option file instead of from my.cnf
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_READ_DEFAULT_FILE', 4);

/**
 * <p>
 * Connect timeout in seconds
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_OPT_CONNECT_TIMEOUT', 0);

/**
 * <p>
 * Enables command LOAD LOCAL INFILE
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_OPT_LOCAL_INFILE', 8);

/**
 * <p>
 * Command to execute when connecting to MySQL server. Will automatically be re-executed when reconnecting.
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_INIT_COMMAND', 3);

/**
 * <p>
 * Use SSL (encrypted protocol). This option should not be set by application programs; 
 * it is set internally in the MySQL client library
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CLIENT_SSL', 2048);

/**
 * <p>
 * Use compression protocol
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CLIENT_COMPRESS', 32);

/**
 * <p>
 * Allow interactive_timeout seconds (instead of wait_timeout seconds) of inactivity before closing the connection. 
 * The client's session wait_timeout variable will be set to the value of the session interactive_timeout variable. 
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CLIENT_INTERACTIVE', 1024);

/**
 * <p>
 * Allow spaces after function names. Makes all functions names reserved words. 
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CLIENT_IGNORE_SPACE', 256);

/**
 * <p>
 * Don't allow the db_name.tbl_name.col_name syntax.
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CLIENT_NO_SCHEMA', 16);
define ('MYSQLI_CLIENT_FOUND_ROWS', 2);

/**
 * <p>
 * For using buffered resultsets
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_STORE_RESULT', 0);

/**
 * <p>
 * For using unbuffered resultsets
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_USE_RESULT', 1);

/**
 * <p>
 * Columns are returned into the array having the fieldname as the array index.
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_ASSOC', 1);

/**
 * <p>
 * Columns are returned into the array having an enumerated index.
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_NUM', 2);

/**
 * <p>
 * Columns are returned into the array having both a numerical index and the fieldname as the associative index. 
 * </p>
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
 * <p>
 * Indicates that a field is defined as NOT NULL
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_NOT_NULL_FLAG', 1);

/**
 * <p>
 * Field is part of a primary index
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_PRI_KEY_FLAG', 2);

/**
 * <p>
 * Field is part of a unique index.
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_UNIQUE_KEY_FLAG', 4);

/**
 * <p>
 * Field is part of an index.
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_MULTIPLE_KEY_FLAG', 8);

/**
 * <p>
 * Field is defined as BLOB
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_BLOB_FLAG', 16);

/**
 * <p>
 * Field is defined as UNSIGNED
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_UNSIGNED_FLAG', 32);

/**
 * <p>
 * Field is defined as ZEROFILL
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_ZEROFILL_FLAG', 64);

/**
 * <p>
 * Field is defined as AUTO_INCREMENT
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_AUTO_INCREMENT_FLAG', 512);

/**
 * <p>
 * Field is defined as TIMESTAMP
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TIMESTAMP_FLAG', 1024);

/**
 * <p>
 * Field is defined as SET
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_SET_FLAG', 2048);

/**
 * <p>
 * Field is defined as NUMERIC
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_NUM_FLAG', 32768);

/**
 * <p>
 * Field is part of an multi-index
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_PART_KEY_FLAG', 16384);

/**
 * <p>
 * Field is part of GROUP BY
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_GROUP_FLAG', 32768);

/**
 * <p>
 * Field is defined as ENUM. Available since PHP 5.3.0.
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_ENUM_FLAG', 256);
define ('MYSQLI_BINARY_FLAG', 128);
define ('MYSQLI_NO_DEFAULT_VALUE_FLAG', 4096);

/**
 * <p>
 * Field is defined as DECIMAL
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_DECIMAL', 0);

/**
 * <p>
 * Field is defined as TINYINT
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_TINY', 1);

/**
 * <p>
 * Field is defined as SMALLINT
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_SHORT', 2);

/**
 * <p>
 * Field is defined as INT
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_LONG', 3);

/**
 * <p>
 * Field is defined as FLOAT
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_FLOAT', 4);

/**
 * <p>
 * Field is defined as DOUBLE
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_DOUBLE', 5);

/**
 * <p>
 * Field is defined as DEFAULT NULL
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_NULL', 6);

/**
 * <p>
 * Field is defined as TIMESTAMP
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_TIMESTAMP', 7);

/**
 * <p>
 * Field is defined as BIGINT
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_LONGLONG', 8);

/**
 * <p>
 * Field is defined as MEDIUMINT
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_INT24', 9);

/**
 * <p>
 * Field is defined as DATE
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_DATE', 10);

/**
 * <p>
 * Field is defined as TIME
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_TIME', 11);

/**
 * <p>
 * Field is defined as DATETIME
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_DATETIME', 12);

/**
 * <p>
 * Field is defined as YEAR
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_YEAR', 13);

/**
 * <p>
 * Field is defined as DATE
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_NEWDATE', 14);

/**
 * <p>
 * Field is defined as ENUM
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_ENUM', 247);

/**
 * <p>
 * Field is defined as SET
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_SET', 248);

/**
 * <p>
 * Field is defined as TINYBLOB
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_TINY_BLOB', 249);

/**
 * <p>
 * Field is defined as MEDIUMBLOB
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_MEDIUM_BLOB', 250);

/**
 * <p>
 * Field is defined as LONGBLOB
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_LONG_BLOB', 251);

/**
 * <p>
 * Field is defined as BLOB
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_BLOB', 252);

/**
 * <p>
 * Field is defined as VARCHAR
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_VAR_STRING', 253);

/**
 * <p>
 * Field is defined as CHAR
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_STRING', 254);
define ('MYSQLI_TYPE_CHAR', 1);
define ('MYSQLI_TYPE_INTERVAL', 247);

/**
 * <p>
 * Field is defined as GEOMETRY
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_GEOMETRY', 255);

/**
 * <p>
 * Precision math DECIMAL or NUMERIC field (MySQL 5.0.3 and up)
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_NEWDECIMAL', 246);

/**
 * <p>
 * Field is defined as BIT (MySQL 5.0.3 and up)
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_BIT', 16);
define ('MYSQLI_SET_CHARSET_NAME', 7);

/**
 * <p>
 * No more data available for bind variable
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_NO_DATA', 100);

/**
 * <p>
 * Data truncation occurred. Available since PHP 5.1.0 and MySQL 5.0.5.
 * </p>
 * @link http://php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_DATA_TRUNCATED', 101);
define ('MYSQLI_REPORT_INDEX', 4);
define ('MYSQLI_REPORT_ERROR', 1);
define ('MYSQLI_REPORT_STRICT', 2);
define ('MYSQLI_REPORT_ALL', 255);
define ('MYSQLI_REPORT_OFF', 0);
define ('MYSQLI_DEBUG_TRACE_ENABLED', 0);
define ('MYSQLI_SERVER_QUERY_NO_GOOD_INDEX_USED', 16);
define ('MYSQLI_SERVER_QUERY_NO_INDEX_USED', 32);
define ('MYSQLI_REFRESH_GRANT', 1);
define ('MYSQLI_REFRESH_LOG', 2);
define ('MYSQLI_REFRESH_TABLES', 4);
define ('MYSQLI_REFRESH_HOSTS', 8);
define ('MYSQLI_REFRESH_STATUS', 16);
define ('MYSQLI_REFRESH_THREADS', 32);
define ('MYSQLI_REFRESH_SLAVE', 64);
define ('MYSQLI_REFRESH_MASTER', 128);

// End of mysqli v.0.1
?>
