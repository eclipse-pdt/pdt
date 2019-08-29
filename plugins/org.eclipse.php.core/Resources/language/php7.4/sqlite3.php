<?php

// Start of sqlite3 v.7.3.0

/**
 * A class that interfaces SQLite 3 databases.
 * @link http://www.php.net/manual/en/class.sqlite3.php
 */
class SQLite3  {

	/**
	 * Opens an SQLite database
	 * @link http://www.php.net/manual/en/sqlite3.open.php
	 * @param string $filename Path to the SQLite database, or :memory: to use in-memory database.
	 * @param int $flags [optional] <p>
	 * Optional flags used to determine how to open the SQLite database. By
	 * default, open uses SQLITE3_OPEN_READWRITE | SQLITE3_OPEN_CREATE.
	 * <p>
	 * <br>
	 * <p>
	 * SQLITE3_OPEN_READONLY: Open the database for
	 * reading only.
	 * </p>
	 * <br>
	 * <p>
	 * SQLITE3_OPEN_READWRITE: Open the database for
	 * reading and writing.
	 * </p>
	 * <br>
	 * <p>
	 * SQLITE3_OPEN_CREATE: Create the database if it
	 * does not exist.
	 * </p>
	 * </p>
	 * </p>
	 * @param string $encryption_key [optional] An optional encryption key used when encrypting and decrypting an
	 * SQLite database. If the SQLite encryption module is not installed,
	 * this parameter will have no effect.
	 * @return void 
	 */
	public function open (string $filename, int $flags = null, string $encryption_key = null) {}

	/**
	 * Closes the database connection
	 * @link http://www.php.net/manual/en/sqlite3.close.php
	 * @return bool true on success, false on failure.
	 */
	public function close () {}

	/**
	 * Executes a result-less query against a given database
	 * @link http://www.php.net/manual/en/sqlite3.exec.php
	 * @param string $query The SQL query to execute (typically an INSERT, UPDATE, or DELETE
	 * query).
	 * @return bool true if the query succeeded, false on failure.
	 */
	public function exec (string $query) {}

	/**
	 * Returns the SQLite3 library version as a string constant and as a number
	 * @link http://www.php.net/manual/en/sqlite3.version.php
	 * @return array an associative array with the keys "versionString" and
	 * "versionNumber".
	 */
	public static function version () {}

	/**
	 * Returns the row ID of the most recent INSERT into the database
	 * @link http://www.php.net/manual/en/sqlite3.lastinsertrowid.php
	 * @return int the row ID of the most recent INSERT into the database
	 */
	public function lastInsertRowID () {}

	/**
	 * Returns the numeric result code of the most recent failed SQLite request
	 * @link http://www.php.net/manual/en/sqlite3.lasterrorcode.php
	 * @return int an integer value representing the numeric result code of the most
	 * recent failed SQLite request.
	 */
	public function lastErrorCode () {}

	/**
	 * Returns English text describing the most recent failed SQLite request
	 * @link http://www.php.net/manual/en/sqlite3.lasterrormsg.php
	 * @return string an English string describing the most recent failed SQLite request.
	 */
	public function lastErrorMsg () {}

	/**
	 * Sets the busy connection handler
	 * @link http://www.php.net/manual/en/sqlite3.busytimeout.php
	 * @param int $msecs The milliseconds to sleep. Setting this value to a value less than
	 * or equal to zero, will turn off an already set timeout handler.
	 * @return bool true on success, false on failure.
	 */
	public function busyTimeout (int $msecs) {}

	/**
	 * Attempts to load an SQLite extension library
	 * @link http://www.php.net/manual/en/sqlite3.loadextension.php
	 * @param string $shared_library The name of the library to load. The library must be located in the
	 * directory specified in the configure option sqlite3.extension_dir.
	 * @return bool true if the extension is successfully loaded, false on failure.
	 */
	public function loadExtension (string $shared_library) {}

	/**
	 * Returns the number of database rows that were changed (or inserted or
	 * deleted) by the most recent SQL statement
	 * @link http://www.php.net/manual/en/sqlite3.changes.php
	 * @return int an integer value corresponding to the number of
	 * database rows changed (or inserted or deleted) by the most recent SQL
	 * statement.
	 */
	public function changes () {}

	/**
	 * Returns a string that has been properly escaped
	 * @link http://www.php.net/manual/en/sqlite3.escapestring.php
	 * @param string $value The string to be escaped.
	 * @return string a properly escaped string that may be used safely in an SQL
	 * statement.
	 */
	public static function escapeString (string $value) {}

	/**
	 * Prepares an SQL statement for execution
	 * @link http://www.php.net/manual/en/sqlite3.prepare.php
	 * @param string $query The SQL query to prepare.
	 * @return SQLite3Stmt an SQLite3Stmt object on success or false on failure.
	 */
	public function prepare (string $query) {}

	/**
	 * Executes an SQL query
	 * @link http://www.php.net/manual/en/sqlite3.query.php
	 * @param string $query The SQL query to execute.
	 * @return SQLite3Result an SQLite3Result object, or false on failure.
	 */
	public function query (string $query) {}

	/**
	 * Executes a query and returns a single result
	 * @link http://www.php.net/manual/en/sqlite3.querysingle.php
	 * @param string $query The SQL query to execute.
	 * @param bool $entire_row [optional] By default, querySingle returns the value of the
	 * first column returned by the query. If
	 * entire_row is true, then it returns an array
	 * of the entire first row.
	 * @return mixed the value of the first column of results or an array of the entire
	 * first row (if entire_row is true).
	 * <p>
	 * If the query is valid but no results are returned, then null will be
	 * returned if entire_row is false, otherwise an
	 * empty array is returned.
	 * </p>
	 * <p>
	 * Invalid or failing queries will return false.
	 * </p>
	 */
	public function querySingle (string $query, bool $entire_row = null) {}

	/**
	 * Registers a PHP function for use as an SQL scalar function
	 * @link http://www.php.net/manual/en/sqlite3.createfunction.php
	 * @param string $name Name of the SQL function to be created or redefined.
	 * @param mixed $callback <p>
	 * The name of a PHP function or user-defined function to apply as a
	 * callback, defining the behavior of the SQL function.
	 * </p>
	 * <p>
	 * This function need to be defined as:
	 * mixedcallback
	 * mixedvalue1
	 * mixed...
	 * <p>
	 * value1
	 * <br>
	 * <p>
	 * The first argument passed to the SQL function.
	 * </p>
	 * ...
	 * <br>
	 * <p>
	 * Further arguments passed to the SQL function.
	 * </p>
	 * </p>
	 * </p>
	 * @param int $argument_count [optional] The number of arguments that the SQL function takes. If
	 * this parameter is -1, then the SQL function may take
	 * any number of arguments.
	 * @param int $flags [optional] A bitwise conjunction of flags. Currently, only
	 * SQLITE3_DETERMINISTIC is supported, which specifies
	 * that the function always returns the same result given the same inputs
	 * within a single SQL statement.
	 * @return bool true upon successful creation of the function, false on failure.
	 */
	public function createFunction (string $name, $callback, int $argument_count = null, int $flags = null) {}

	/**
	 * Registers a PHP function for use as an SQL aggregate function
	 * @link http://www.php.net/manual/en/sqlite3.createaggregate.php
	 * @param string $name Name of the SQL aggregate to be created or redefined.
	 * @param mixed $step_callback <p>
	 * Callback function called for each row of the result set. Your PHP
	 * function should accumulate the result and store it in the aggregation
	 * context.
	 * </p>
	 * <p>
	 * This function need to be defined as:
	 * mixedstep
	 * mixedcontext
	 * intrownumber
	 * mixedvalue1
	 * mixed...
	 * <p>
	 * context
	 * <br>
	 * <p>
	 * null for the first row; on subsequent rows it will have the value
	 * that was previously returned from the step function; you should use
	 * this to maintain the aggregate state.
	 * </p>
	 * rownumber
	 * <br>
	 * <p>
	 * The current row number.
	 * </p>
	 * value1
	 * <br>
	 * <p>
	 * The first argument passed to the aggregate.
	 * </p>
	 * ...
	 * <br>
	 * <p>
	 * Further arguments passed to the aggregate.
	 * </p>
	 * </p>
	 * The return value of this function will be used as the
	 * context argument in the next call of the step or
	 * finalize functions.
	 * </p>
	 * @param mixed $final_callback <p>
	 * Callback function to aggregate the "stepped" data from each row. 
	 * Once all the rows have been processed, this function will be called
	 * and it should then take the data from the aggregation context and
	 * return the result. This callback function should return a type understood
	 * by SQLite (i.e. scalar type).
	 * </p>
	 * <p>
	 * This function need to be defined as:
	 * mixedfini
	 * mixedcontext
	 * intrownumber
	 * <p>
	 * context
	 * <br>
	 * <p>
	 * Holds the return value from the very last call to the step function.
	 * </p>
	 * rownumber
	 * <br>
	 * <p>
	 * Always 0.
	 * </p>
	 * </p>
	 * The return value of this function will be used as the return value for
	 * the aggregate.
	 * </p>
	 * @param int $argument_count [optional] The number of arguments that the SQL aggregate takes. If
	 * this parameter is negative, then the SQL aggregate may take
	 * any number of arguments.
	 * @return bool true upon successful creation of the aggregate, false on
	 * failure.
	 */
	public function createAggregate (string $name, $step_callback, $final_callback, int $argument_count = null) {}

	/**
	 * Registers a PHP function for use as an SQL collating function
	 * @link http://www.php.net/manual/en/sqlite3.createcollation.php
	 * @param string $name Name of the SQL collating function to be created or redefined
	 * @param callable $callback <p>
	 * The name of a PHP function or user-defined function to apply as a
	 * callback, defining the behavior of the collation. It should accept two
	 * values and return as strcmp does, i.e. it should
	 * return -1, 1, or 0 if the first string sorts before, sorts after, or is
	 * equal to the second.
	 * </p>
	 * <p>
	 * This function need to be defined as:
	 * intcollation
	 * mixedvalue1
	 * mixedvalue2
	 * </p>
	 * @return bool true on success or false on failure
	 */
	public function createCollation (string $name, callable $callback) {}

	/**
	 * Opens a stream resource to read a BLOB
	 * @link http://www.php.net/manual/en/sqlite3.openblob.php
	 * @param string $table The table name.
	 * @param string $column The column name.
	 * @param int $rowid The row ID.
	 * @param string $dbname [optional] The symbolic name of the DB
	 * @param int $flags [optional] Either SQLITE3_OPEN_READONLY or 
	 * SQLITE3_OPEN_READWRITE to open the stream
	 * for reading only, or for reading and writing, respectively.
	 * @return resource a stream resource, or false on failure.
	 */
	public function openBlob (string $table, string $column, int $rowid, string $dbname = null, int $flags = null) {}

	/**
	 * Enable throwing exceptions
	 * @link http://www.php.net/manual/en/sqlite3.enableexceptions.php
	 * @param bool $enableExceptions [optional] 
	 * @return bool the old value; true if exceptions were enabled, false otherwise.
	 */
	public function enableExceptions (bool $enableExceptions = null) {}

	/**
	 * Instantiates an SQLite3 object and opens an SQLite 3 database
	 * @link http://www.php.net/manual/en/sqlite3.construct.php
	 * @param mixed $filename
	 * @param mixed $flags [optional]
	 * @param mixed $encryption_key [optional]
	 */
	public function __construct ($filename, $flags = null, $encryption_key = null) {}

}

/**
 * A class that handles prepared statements for the SQLite 3 extension.
 * @link http://www.php.net/manual/en/class.sqlite3stmt.php
 */
class SQLite3Stmt  {

	/**
	 * Returns the number of parameters within the prepared statement
	 * @link http://www.php.net/manual/en/sqlite3stmt.paramcount.php
	 * @return int the number of parameters within the prepared statement.
	 */
	public function paramCount () {}

	/**
	 * Closes the prepared statement
	 * @link http://www.php.net/manual/en/sqlite3stmt.close.php
	 * @return bool true
	 */
	public function close () {}

	/**
	 * Resets the prepared statement
	 * @link http://www.php.net/manual/en/sqlite3stmt.reset.php
	 * @return bool true if the statement is successfully reset, false on failure.
	 */
	public function reset () {}

	/**
	 * Clears all current bound parameters
	 * @link http://www.php.net/manual/en/sqlite3stmt.clear.php
	 * @return bool true on successful clearing of bound parameters, false on
	 * failure.
	 */
	public function clear () {}

	/**
	 * Executes a prepared statement and returns a result set object
	 * @link http://www.php.net/manual/en/sqlite3stmt.execute.php
	 * @return SQLite3Result an SQLite3Result object on successful execution of the prepared
	 * statement, false on failure.
	 */
	public function execute () {}

	/**
	 * Binds a parameter to a statement variable
	 * @link http://www.php.net/manual/en/sqlite3stmt.bindparam.php
	 * @param mixed $sql_param Either a string or an int identifying the statement variable to which the
	 * parameter should be bound.
	 * @param mixed $param The parameter to bind to a statement variable.
	 * @param int $type [optional] <p>
	 * The data type of the parameter to bind.
	 * <p>
	 * <br>
	 * <p>
	 * SQLITE3_INTEGER: The value is a signed integer,
	 * stored in 1, 2, 3, 4, 6, or 8 bytes depending on the magnitude of
	 * the value.
	 * </p>
	 * <br>
	 * <p>
	 * SQLITE3_FLOAT: The value is a floating point
	 * value, stored as an 8-byte IEEE floating point number.
	 * </p>
	 * <br>
	 * <p>
	 * SQLITE3_TEXT: The value is a text string, stored
	 * using the database encoding (UTF-8, UTF-16BE or UTF-16-LE).
	 * </p>
	 * <br>
	 * <p>
	 * SQLITE3_BLOB: The value is a blob of data, stored
	 * exactly as it was input.
	 * </p>
	 * <br>
	 * <p>
	 * SQLITE3_NULL: The value is a NULL value.
	 * </p>
	 * </p>
	 * </p>
	 * <p>
	 * As of PHP 7.0.7, if type is omitted, it is automatically
	 * detected from the type of the param: boolean
	 * and integer are treated as SQLITE3_INTEGER,
	 * float as SQLITE3_FLOAT, null
	 * as SQLITE3_NULL and all others as SQLITE3_TEXT.
	 * Formerly, if type has been omitted, it has defaulted
	 * to SQLITE3_TEXT.
	 * </p>
	 * <p>
	 * If param is null, it is always treated as
	 * SQLITE3_NULL, regardless of the given
	 * type.
	 * </p>
	 * @return bool true if the parameter is bound to the statement variable, false
	 * on failure.
	 */
	public function bindParam ($sql_param, &$param, int $type = null) {}

	/**
	 * Binds the value of a parameter to a statement variable
	 * @link http://www.php.net/manual/en/sqlite3stmt.bindvalue.php
	 * @param mixed $sql_param Either a string or an int identifying the statement variable to which the
	 * value should be bound.
	 * @param mixed $value The value to bind to a statement variable.
	 * @param int $type [optional] <p>
	 * The data type of the value to bind.
	 * <p>
	 * <br>
	 * <p>
	 * SQLITE3_INTEGER: The value is a signed integer,
	 * stored in 1, 2, 3, 4, 6, or 8 bytes depending on the magnitude of
	 * the value.
	 * </p>
	 * <br>
	 * <p>
	 * SQLITE3_FLOAT: The value is a floating point
	 * value, stored as an 8-byte IEEE floating point number.
	 * </p>
	 * <br>
	 * <p>
	 * SQLITE3_TEXT: The value is a text string, stored
	 * using the database encoding (UTF-8, UTF-16BE or UTF-16-LE).
	 * </p>
	 * <br>
	 * <p>
	 * SQLITE3_BLOB: The value is a blob of data, stored
	 * exactly as it was input.
	 * </p>
	 * <br>
	 * <p>
	 * SQLITE3_NULL: The value is a NULL value.
	 * </p>
	 * </p>
	 * </p>
	 * <p>
	 * As of PHP 7.0.7, if type is omitted, it is automatically
	 * detected from the type of the value: boolean
	 * and integer are treated as SQLITE3_INTEGER,
	 * float as SQLITE3_FLOAT, null
	 * as SQLITE3_NULL and all others as SQLITE3_TEXT.
	 * Formerly, if type has been omitted, it has defaulted
	 * to SQLITE3_TEXT.
	 * </p>
	 * <p>
	 * If value is null, it is always treated as
	 * SQLITE3_NULL, regardless of the given
	 * type.
	 * </p>
	 * @return bool true if the value is bound to the statement variable, false
	 * on failure.
	 */
	public function bindValue ($sql_param, $value, int $type = null) {}

	/**
	 * Returns whether a statement is definitely read only
	 * @link http://www.php.net/manual/en/sqlite3stmt.readonly.php
	 * @return bool true if a statement is definitely read only, false otherwise.
	 */
	public function readOnly () {}

	/**
	 * @param mixed $sqlite3
	 */
	private function __construct ($sqlite3) {}

}

/**
 * A class that handles result sets for the SQLite 3 extension.
 * @link http://www.php.net/manual/en/class.sqlite3result.php
 */
class SQLite3Result  {

	/**
	 * Returns the number of columns in the result set
	 * @link http://www.php.net/manual/en/sqlite3result.numcolumns.php
	 * @return int the number of columns in the result set.
	 */
	public function numColumns () {}

	/**
	 * Returns the name of the nth column
	 * @link http://www.php.net/manual/en/sqlite3result.columnname.php
	 * @param int $column_number The numeric zero-based index of the column.
	 * @return string the string name of the column identified by
	 * column_number.
	 */
	public function columnName (int $column_number) {}

	/**
	 * Returns the type of the nth column
	 * @link http://www.php.net/manual/en/sqlite3result.columntype.php
	 * @param int $column_number The numeric zero-based index of the column.
	 * @return int the data type index of the column identified by
	 * column_number (one of
	 * SQLITE3_INTEGER, SQLITE3_FLOAT,
	 * SQLITE3_TEXT, SQLITE3_BLOB, or
	 * SQLITE3_NULL).
	 */
	public function columnType (int $column_number) {}

	/**
	 * Fetches a result row as an associative or numerically indexed array or both
	 * @link http://www.php.net/manual/en/sqlite3result.fetcharray.php
	 * @param int $mode [optional] <p>
	 * Controls how the next row will be returned to the caller. This value
	 * must be one of either SQLITE3_ASSOC,
	 * SQLITE3_NUM, or SQLITE3_BOTH.
	 * <p>
	 * <br>
	 * <p>
	 * SQLITE3_ASSOC: returns an array indexed by column
	 * name as returned in the corresponding result set
	 * </p>
	 * <br>
	 * <p>
	 * SQLITE3_NUM: returns an array indexed by column
	 * number as returned in the corresponding result set, starting at
	 * column 0
	 * </p>
	 * <br>
	 * <p>
	 * SQLITE3_BOTH: returns an array indexed by both
	 * column name and number as returned in the corresponding result set,
	 * starting at column 0
	 * </p>
	 * </p>
	 * </p>
	 * @return array a result row as an associatively or numerically indexed array or
	 * both. Alternately will return false if there are no more rows.
	 * <p>
	 * The types of the values of the returned array are mapped from SQLite3 types
	 * as follows: integers are mapped to integer if they fit into the
	 * range PHP_INT_MIN..PHP_INT_MAX, and
	 * to string otherwise. Floats are mapped to float,
	 * NULL values are mapped to null, and strings
	 * and blobs are mapped to string.
	 * </p>
	 */
	public function fetchArray (int $mode = null) {}

	/**
	 * Resets the result set back to the first row
	 * @link http://www.php.net/manual/en/sqlite3result.reset.php
	 * @return bool true if the result set is successfully reset
	 * back to the first row, false on failure.
	 */
	public function reset () {}

	/**
	 * Closes the result set
	 * @link http://www.php.net/manual/en/sqlite3result.finalize.php
	 * @return bool true.
	 */
	public function finalize () {}

	private function __construct () {}

}

/**
 * Specifies that the Sqlite3Result::fetchArray
 * method shall return an array indexed by column name as returned in the
 * corresponding result set.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 */
define ('SQLITE3_ASSOC', 1);

/**
 * Specifies that the Sqlite3Result::fetchArray
 * method shall return an array indexed by column number as returned in the
 * corresponding result set, starting at column 0.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 */
define ('SQLITE3_NUM', 2);

/**
 * Specifies that the Sqlite3Result::fetchArray
 * method shall return an array indexed by both column name and number as
 * returned in the corresponding result set, starting at column 0.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 */
define ('SQLITE3_BOTH', 3);

/**
 * Represents the SQLite3 INTEGER storage class.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 */
define ('SQLITE3_INTEGER', 1);

/**
 * Represents the SQLite3 REAL (FLOAT) storage class.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 */
define ('SQLITE3_FLOAT', 2);

/**
 * Represents the SQLite3 TEXT storage class.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 */
define ('SQLITE3_TEXT', 3);

/**
 * Represents the SQLite3 BLOB storage class.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 */
define ('SQLITE3_BLOB', 4);

/**
 * Represents the SQLite3 NULL storage class.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 */
define ('SQLITE3_NULL', 5);

/**
 * Specifies that the SQLite3 database be opened for reading only.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 */
define ('SQLITE3_OPEN_READONLY', 1);

/**
 * Specifies that the SQLite3 database be opened for reading and writing.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 */
define ('SQLITE3_OPEN_READWRITE', 2);

/**
 * Specifies that the SQLite3 database be created if it does not already
 * exist.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 */
define ('SQLITE3_OPEN_CREATE', 4);

/**
 * Specifies that a function created with SQLite3::createFunction
 * is deterministic, i.e. it always returns the same result given the same inputs within
 * a single SQL statement. (Available as of PHP 7.1.4.)
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 */
define ('SQLITE3_DETERMINISTIC', 2048);

// End of sqlite3 v.7.3.0
