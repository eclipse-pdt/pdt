<?php

// Start of SQLite v.2.0-dev

/**
 * Represents an opened SQLite database.
 * @link http://php.net/manual/en/ref.sqlite.php
 */
class SQLiteDatabase  {

	/**
	 * @param var1
	 * @param var2
	 * @param var3
	 */
	final public function __construct ($var1, $var2, &$var3) {}

	/**
	 * @param var1
	 * @param var2
	 * @param var3
	 */
	public function query ($var1, $var2, &$var3) {}

	/**
	 * @param var1
	 * @param var2
	 */
	public function queryExec ($var1, &$var2) {}

	public function arrayQuery () {}

	public function singleQuery () {}

	/**
	 * @param var1
	 * @param var2
	 * @param var3
	 */
	public function unbufferedQuery ($var1, $var2, &$var3) {}

	public function lastInsertRowid () {}

	public function changes () {}

	public function createAggregate () {}

	public function createFunction () {}

	public function busyTimeout () {}

	public function lastError () {}

	public function fetchColumnTypes () {}

}

/**
 * Represents a buffered SQLite result set.
 * @link http://php.net/manual/en/ref.sqlite.php
 */
final class SQLiteResult implements Iterator, Traversable, Countable {

	public function fetch () {}

	public function fetchObject () {}

	public function fetchSingle () {}

	public function fetchAll () {}

	public function column () {}

	public function numFields () {}

	public function fieldName () {}

	public function current () {}

	public function key () {}

	public function next () {}

	public function valid () {}

	public function rewind () {}

	public function count () {}

	public function prev () {}

	public function hasPrev () {}

	public function numRows () {}

	public function seek () {}

}

/**
 * Represents an unbuffered SQLite result set. Unbuffered results sets are sequential, forward-seeking only.
 * @link http://php.net/manual/en/ref.sqlite.php
 */
final class SQLiteUnbuffered  {

	public function fetch () {}

	public function fetchObject () {}

	public function fetchSingle () {}

	public function fetchAll () {}

	public function column () {}

	public function numFields () {}

	public function fieldName () {}

	public function current () {}

	public function next () {}

	public function valid () {}

}

final class SQLiteException extends RuntimeException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


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

/**
 * Opens a SQLite database and create the database if it does not exist
 * @link http://php.net/manual/en/function.sqlite-open.php
 * @param filename string
 * @param mode int[optional]
 * @param error_message string[optional]
 * @return resource a resource (database handle) on success, false on error.
 */
function sqlite_open ($filename, $mode = null, &$error_message = null) {}

/**
 * Opens a persistent handle to an SQLite database and create the database if it does not exist
 * @link http://php.net/manual/en/function.sqlite-popen.php
 * @param filename string
 * @param mode int[optional]
 * @param error_message string[optional]
 * @return resource a resource (database handle) on success, false on error.
 */
function sqlite_popen ($filename, $mode = null, &$error_message = null) {}

/**
 * Closes an open SQLite database
 * @link http://php.net/manual/en/function.sqlite-close.php
 * @param dbhandle resource
 * @return void 
 */
function sqlite_close ($dbhandle) {}

/**
 * Executes a query against a given database and returns a result handle
 * @link http://php.net/manual/en/function.sqlite-query.php
 * @param query string
 * @param result_type int[optional]
 * @param error_msg string[optional]
 * @return SQLiteResult 
 */
function sqlite_query ($query, $result_type = null, &$error_msg = null) {}

/**
 * Executes a result-less query against a given database
 * @link http://php.net/manual/en/function.sqlite-exec.php
 * @param query string
 * @param error_msg string[optional]
 * @return bool 
 */
function sqlite_exec ($query, &$error_msg = null) {}

/**
 * Execute a query against a given database and returns an array
 * @link http://php.net/manual/en/function.sqlite-array-query.php
 * @param query string
 * @param result_type int[optional]
 * @param decode_binary bool[optional]
 * @return array an array of the entire result set; false otherwise.
 */
function sqlite_array_query ($query, $result_type = null, $decode_binary = null) {}

/**
 * Executes a query and returns either an array for one single column or the value of the first row
 * @link http://php.net/manual/en/function.sqlite-single-query.php
 * @param query string
 * @param first_row_only bool[optional]
 * @param decode_binary bool[optional]
 * @return array 
 */
function sqlite_single_query ($query, $first_row_only = null, $decode_binary = null) {}

/**
 * Fetches the next row from a result set as an array
 * @link http://php.net/manual/en/function.sqlite-fetch-array.php
 * @param result_type int[optional]
 * @param decode_binary bool[optional]
 * @return array an array of the next row from a result set; false if the
 */
function sqlite_fetch_array ($result_type = null, $decode_binary = null) {}

/**
 * Fetches the next row from a result set as an object
 * @link http://php.net/manual/en/function.sqlite-fetch-object.php
 * @param class_name string[optional]
 * @param ctor_params array[optional]
 * @param decode_binary bool[optional]
 * @return object 
 */
function sqlite_fetch_object ($class_name = null, array $ctor_params = null, $decode_binary = null) {}

/**
 * Fetches the first column of a result set as a string
 * @link http://php.net/manual/en/function.sqlite-fetch-single.php
 * @param decode_binary bool[optional]
 * @return string 
 */
function sqlite_fetch_single ($decode_binary = null) {}

/**
 * &Alias; <function>sqlite_fetch_single</function>
 * @link http://php.net/manual/en/function.sqlite-fetch-string.php
 */
function sqlite_fetch_string () {}

/**
 * Fetches all rows from a result set as an array of arrays
 * @link http://php.net/manual/en/function.sqlite-fetch-all.php
 * @param result_type int[optional]
 * @param decode_binary bool[optional]
 * @return array an array of the remaining rows in a result set. If called right
 */
function sqlite_fetch_all ($result_type = null, $decode_binary = null) {}

/**
 * Fetches the current row from a result set as an array
 * @link http://php.net/manual/en/function.sqlite-current.php
 * @param result_type int[optional]
 * @param decode_binary bool[optional]
 * @return array an array of the current row from a result set; false if the
 */
function sqlite_current ($result_type = null, $decode_binary = null) {}

/**
 * Fetches a column from the current row of a result set
 * @link http://php.net/manual/en/function.sqlite-column.php
 * @param index_or_name mixed
 * @param decode_binary bool[optional]
 * @return mixed 
 */
function sqlite_column ($index_or_name, $decode_binary = null) {}

/**
 * Returns the version of the linked SQLite library
 * @link http://php.net/manual/en/function.sqlite-libversion.php
 * @return string 
 */
function sqlite_libversion () {}

/**
 * Returns the encoding of the linked SQLite library
 * @link http://php.net/manual/en/function.sqlite-libencoding.php
 * @return string 
 */
function sqlite_libencoding () {}

/**
 * Returns the number of rows that were changed by the most
   recent SQL statement
 * @link http://php.net/manual/en/function.sqlite-changes.php
 * @return int 
 */
function sqlite_changes () {}

/**
 * Returns the rowid of the most recently inserted row
 * @link http://php.net/manual/en/function.sqlite-last-insert-rowid.php
 * @return int 
 */
function sqlite_last_insert_rowid () {}

/**
 * Returns the number of rows in a buffered result set
 * @link http://php.net/manual/en/function.sqlite-num-rows.php
 * @return int 
 */
function sqlite_num_rows () {}

/**
 * Returns the number of fields in a result set
 * @link http://php.net/manual/en/function.sqlite-num-fields.php
 * @return int 
 */
function sqlite_num_fields () {}

/**
 * Returns the name of a particular field
 * @link http://php.net/manual/en/function.sqlite-field-name.php
 * @param field_index int
 * @return string the name of a field in an SQLite result set, given the ordinal
 */
function sqlite_field_name ($field_index) {}

/**
 * Seek to a particular row number of a buffered result set
 * @link http://php.net/manual/en/function.sqlite-seek.php
 * @param rownum int
 * @return bool false if the row does not exist, true otherwise.
 */
function sqlite_seek ($rownum) {}

/**
 * Seek to the first row number
 * @link http://php.net/manual/en/function.sqlite-rewind.php
 * @return bool false if there are no rows in the result set, true otherwise.
 */
function sqlite_rewind () {}

/**
 * Seek to the next row number
 * @link http://php.net/manual/en/function.sqlite-next.php
 * @return bool true on success, or false if there are no more rows.
 */
function sqlite_next () {}

/**
 * Seek to the previous row number of a result set
 * @link http://php.net/manual/en/function.sqlite-prev.php
 * @return bool true on success, or false if there are no more previous rows.
 */
function sqlite_prev () {}

/**
 * Returns whether more rows are available
 * @link http://php.net/manual/en/function.sqlite-valid.php
 * @return bool true if there are more rows available from the
 */
function sqlite_valid () {}

/**
 * Finds whether or not more rows are available
 * @link http://php.net/manual/en/function.sqlite-has-more.php
 * @param result resource
 * @return bool true if there are more rows available from the
 */
function sqlite_has_more ($result) {}

/**
 * Returns whether or not a previous row is available
 * @link http://php.net/manual/en/function.sqlite-has-prev.php
 * @return bool true if there are more previous rows available from the
 */
function sqlite_has_prev () {}

/**
 * Escapes a string for use as a query parameter
 * @link http://php.net/manual/en/function.sqlite-escape-string.php
 * @param item string
 * @return string an escaped string for use in an SQLite SQL statement.
 */
function sqlite_escape_string ($item) {}

/**
 * Set busy timeout duration, or disable busy handlers
 * @link http://php.net/manual/en/function.sqlite-busy-timeout.php
 * @param milliseconds int
 * @return void 
 */
function sqlite_busy_timeout ($milliseconds) {}

/**
 * Returns the error code of the last error for a database
 * @link http://php.net/manual/en/function.sqlite-last-error.php
 * @return int 
 */
function sqlite_last_error () {}

/**
 * Returns the textual description of an error code
 * @link http://php.net/manual/en/function.sqlite-error-string.php
 * @param error_code int
 * @return string a human readable description of the error_code,
 */
function sqlite_error_string ($error_code) {}

/**
 * Execute a query that does not prefetch and buffer all data
 * @link http://php.net/manual/en/function.sqlite-unbuffered-query.php
 * @param query string
 * @param result_type int[optional]
 * @param error_msg string[optional]
 * @return SQLiteUnbuffered a result handle or false on failure.
 */
function sqlite_unbuffered_query ($query, $result_type = null, &$error_msg = null) {}

/**
 * Register an aggregating UDF for use in SQL statements
 * @link http://php.net/manual/en/function.sqlite-create-aggregate.php
 * @param function_name string
 * @param step_func callback
 * @param finalize_func callback
 * @param num_args int[optional]
 * @return void 
 */
function sqlite_create_aggregate ($function_name, $step_func, $finalize_func, $num_args = null) {}

/**
 * Registers a "regular" User Defined Function for use in SQL statements
 * @link http://php.net/manual/en/function.sqlite-create-function.php
 * @param function_name string
 * @param callback callback
 * @param num_args int[optional]
 * @return void 
 */
function sqlite_create_function ($function_name, $callback, $num_args = null) {}

/**
 * Opens a SQLite database and returns a SQLiteDatabase object
 * @link http://php.net/manual/en/function.sqlite-factory.php
 * @param filename string
 * @param mode int[optional]
 * @param error_message string[optional]
 * @return SQLiteDatabase a SQLiteDatabase object on success, &null; on error.
 */
function sqlite_factory ($filename, $mode = null, &$error_message = null) {}

/**
 * Encode binary data before returning it from an UDF
 * @link http://php.net/manual/en/function.sqlite-udf-encode-binary.php
 * @param data string
 * @return string 
 */
function sqlite_udf_encode_binary ($data) {}

/**
 * Decode binary data passed as parameters to an UDF
 * @link http://php.net/manual/en/function.sqlite-udf-decode-binary.php
 * @param data string
 * @return string 
 */
function sqlite_udf_decode_binary ($data) {}

/**
 * Return an array of column types from a particular table
 * @link http://php.net/manual/en/function.sqlite-fetch-column-types.php
 * @param table_name string
 * @param result_type int[optional]
 * @return array an array of column data types; false on error.
 */
function sqlite_fetch_column_types ($table_name, $result_type = null) {}


/**
 * Columns are returned into the array having both a numerical index
 * and the field name as the array index.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_BOTH', 3);

/**
 * Columns are returned into the array having a numerical index to the
 * fields. This index starts with 0, the first field in the result.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_NUM', 2);

/**
 * Columns are returned into the array having the field name as the array
 * index.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_ASSOC', 1);

/**
 * Successful result.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_OK', 0);

/**
 * SQL error or missing database.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_ERROR', 1);

/**
 * An internal logic error in SQLite.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_INTERNAL', 2);

/**
 * Access permission denied.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_PERM', 3);

/**
 * Callback routine requested an abort.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_ABORT', 4);

/**
 * The database file is locked.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_BUSY', 5);

/**
 * A table in the database is locked.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_LOCKED', 6);

/**
 * Memory allocation failed.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_NOMEM', 7);

/**
 * Attempt to write a readonly database.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_READONLY', 8);

/**
 * Operation terminated internally.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_INTERRUPT', 9);

/**
 * Disk I/O error occurred.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_IOERR', 10);

/**
 * The database disk image is malformed.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_CORRUPT', 11);

/**
 * (Internal) Table or record not found.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_NOTFOUND', 12);

/**
 * Insertion failed because database is full.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_FULL', 13);

/**
 * Unable to open the database file.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_CANTOPEN', 14);

/**
 * Database lock protocol error.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_PROTOCOL', 15);

/**
 * (Internal) Database table is empty.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_EMPTY', 16);

/**
 * The database schema changed.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_SCHEMA', 17);

/**
 * Too much data for one row of a table.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_TOOBIG', 18);

/**
 * Abort due to constraint violation.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_CONSTRAINT', 19);

/**
 * Data type mismatch.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_MISMATCH', 20);

/**
 * Library used incorrectly.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_MISUSE', 21);

/**
 * Uses of OS features not supported on host.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_NOLFS', 22);

/**
 * Authorized failed.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_AUTH', 23);
define ('SQLITE_NOTADB', 26);
define ('SQLITE_FORMAT', 24);

/**
 * Internal process has another row ready.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_ROW', 100);

/**
 * Internal process has finished executing.
 * @link http://php.net/manual/en/sqlite.constants.php
 */
define ('SQLITE_DONE', 101);

// End of SQLite v.2.0-dev
?>
