<?php

// Start of sqlite3 v.8.2.6

/**
 * A class that interfaces SQLite 3 databases.
 * @link http://www.php.net/manual/en/class.sqlite3.php
 */
class SQLite3  {
	const OK = 0;
	const DENY = 1;
	const IGNORE = 2;
	const CREATE_INDEX = 1;
	const CREATE_TABLE = 2;
	const CREATE_TEMP_INDEX = 3;
	const CREATE_TEMP_TABLE = 4;
	const CREATE_TEMP_TRIGGER = 5;
	const CREATE_TEMP_VIEW = 6;
	const CREATE_TRIGGER = 7;
	const CREATE_VIEW = 8;
	const DELETE = 9;
	const DROP_INDEX = 10;
	const DROP_TABLE = 11;
	const DROP_TEMP_INDEX = 12;
	const DROP_TEMP_TABLE = 13;
	const DROP_TEMP_TRIGGER = 14;
	const DROP_TEMP_VIEW = 15;
	const DROP_TRIGGER = 16;
	const DROP_VIEW = 17;
	const INSERT = 18;
	const PRAGMA = 19;
	const READ = 20;
	const SELECT = 21;
	const TRANSACTION = 22;
	const UPDATE = 23;
	const ATTACH = 24;
	const DETACH = 25;
	const ALTER_TABLE = 26;
	const REINDEX = 27;
	const ANALYZE = 28;
	const CREATE_VTABLE = 29;
	const DROP_VTABLE = 30;
	const FUNCTION = 31;
	const SAVEPOINT = 32;
	const COPY = 0;
	const RECURSIVE = 33;


	/**
	 * Instantiates an SQLite3 object and opens an SQLite 3 database
	 * @link http://www.php.net/manual/en/sqlite3.construct.php
	 * @param string $filename 
	 * @param int $flags [optional] 
	 * @param string $encryptionKey [optional] 
	 * @return string 
	 */
	public function __construct (string $filename, int $flags = 'SQLITE3_OPEN_READWRITE | SQLITE3_OPEN_CREATE', string $encryptionKey = '""'): string {}

	/**
	 * Opens an SQLite database
	 * @link http://www.php.net/manual/en/sqlite3.open.php
	 * @param string $filename 
	 * @param int $flags [optional] 
	 * @param string $encryptionKey [optional] 
	 * @return void No value is returned.
	 */
	public function open (string $filename, int $flags = 'SQLITE3_OPEN_READWRITE | SQLITE3_OPEN_CREATE', string $encryptionKey = '""'): void {}

	/**
	 * Closes the database connection
	 * @link http://www.php.net/manual/en/sqlite3.close.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function close (): bool {}

	/**
	 * Returns the SQLite3 library version as a string constant and as a number
	 * @link http://www.php.net/manual/en/sqlite3.version.php
	 * @return array Returns an associative array with the keys "versionString" and
	 * "versionNumber".
	 */
	public static function version (): array {}

	/**
	 * Returns the row ID of the most recent INSERT into the database
	 * @link http://www.php.net/manual/en/sqlite3.lastinsertrowid.php
	 * @return int Returns the row ID of the most recent INSERT into the database.
	 * If no successful INSERTs into rowid tables have ever occurred on this database connection,
	 * then SQLite3::lastInsertRowID returns 0.
	 */
	public function lastInsertRowID (): int {}

	/**
	 * Returns the numeric result code of the most recent failed SQLite request
	 * @link http://www.php.net/manual/en/sqlite3.lasterrorcode.php
	 * @return int Returns an integer value representing the numeric result code of the most
	 * recent failed SQLite request.
	 */
	public function lastErrorCode (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function lastExtendedErrorCode () {}

	/**
	 * Returns English text describing the most recent failed SQLite request
	 * @link http://www.php.net/manual/en/sqlite3.lasterrormsg.php
	 * @return string Returns an English string describing the most recent failed SQLite request.
	 */
	public function lastErrorMsg (): string {}

	/**
	 * Returns the number of database rows that were changed (or inserted or
	 * deleted) by the most recent SQL statement
	 * @link http://www.php.net/manual/en/sqlite3.changes.php
	 * @return int Returns an int value corresponding to the number of
	 * database rows changed (or inserted or deleted) by the most recent SQL
	 * statement.
	 */
	public function changes (): int {}

	/**
	 * Sets the busy connection handler
	 * @link http://www.php.net/manual/en/sqlite3.busytimeout.php
	 * @param int $milliseconds 
	 * @return bool Returns true on success, or false on failure.
	 */
	public function busyTimeout (int $milliseconds): bool {}

	/**
	 * Attempts to load an SQLite extension library
	 * @link http://www.php.net/manual/en/sqlite3.loadextension.php
	 * @param string $name 
	 * @return bool Returns true if the extension is successfully loaded, false on failure.
	 */
	public function loadExtension (string $name): bool {}

	/**
	 * Backup one database to another database
	 * @link http://www.php.net/manual/en/sqlite3.backup.php
	 * @param SQLite3 $destination A database connection opened with SQLite3::open.
	 * @param string $sourceDatabase [optional] The database name is "main" for the main database,
	 * "temp" for the temporary database,
	 * or the name specified after the AS keyword
	 * in an ATTACH statement for an attached database.
	 * @param string $destinationDatabase [optional] Analogous to sourceDatabase
	 * but for the destination.
	 * @return bool Returns true on success or false on failure.
	 */
	public function backup (SQLite3 $destination, string $sourceDatabase = '"main"', string $destinationDatabase = '"main"'): bool {}

	/**
	 * Returns a string that has been properly escaped
	 * @link http://www.php.net/manual/en/sqlite3.escapestring.php
	 * @param string $string 
	 * @return string Returns a properly escaped string that may be used safely in an SQL
	 * statement.
	 */
	public static function escapeString (string $string): string {}

	/**
	 * Prepares an SQL statement for execution
	 * @link http://www.php.net/manual/en/sqlite3.prepare.php
	 * @param string $query 
	 * @return SQLite3Stmt|false Returns an SQLite3Stmt object on success or false on failure.
	 */
	public function prepare (string $query): SQLite3Stmt|false {}

	/**
	 * Executes a result-less query against a given database
	 * @link http://www.php.net/manual/en/sqlite3.exec.php
	 * @param string $query 
	 * @return bool Returns true if the query succeeded, false on failure.
	 */
	public function exec (string $query): bool {}

	/**
	 * Executes an SQL query
	 * @link http://www.php.net/manual/en/sqlite3.query.php
	 * @param string $query 
	 * @return SQLite3Result|false Returns an SQLite3Result object, or false on failure.
	 */
	public function query (string $query): SQLite3Result|false {}

	/**
	 * Executes a query and returns a single result
	 * @link http://www.php.net/manual/en/sqlite3.querysingle.php
	 * @param string $query 
	 * @param bool $entireRow [optional] 
	 * @return mixed Returns the value of the first column of results or an array of the entire
	 * first row (if entireRow is true).
	 * <p>If the query is valid but no results are returned, then null will be
	 * returned if entireRow is false, otherwise an
	 * empty array is returned.</p>
	 * <p>Invalid or failing queries will return false.</p>
	 */
	public function querySingle (string $query, bool $entireRow = false): mixed {}

	/**
	 * Registers a PHP function for use as an SQL scalar function
	 * @link http://www.php.net/manual/en/sqlite3.createfunction.php
	 * @param string $name 
	 * @param callable $callback 
	 * @param int $argCount [optional] 
	 * @param int $flags [optional] 
	 * @return bool Returns true upon successful creation of the function, false on failure.
	 */
	public function createFunction (string $name, callable $callback, int $argCount = -1, int $flags = null): bool {}

	/**
	 * Registers a PHP function for use as an SQL aggregate function
	 * @link http://www.php.net/manual/en/sqlite3.createaggregate.php
	 * @param string $name 
	 * @param callable $stepCallback 
	 * @param callable $finalCallback 
	 * @param int $argCount [optional] 
	 * @return bool Returns true upon successful creation of the aggregate, or false on failure.
	 */
	public function createAggregate (string $name, callable $stepCallback, callable $finalCallback, int $argCount = -1): bool {}

	/**
	 * Registers a PHP function for use as an SQL collating function
	 * @link http://www.php.net/manual/en/sqlite3.createcollation.php
	 * @param string $name Name of the SQL collating function to be created or redefined
	 * @param callable $callback The name of a PHP function or user-defined function to apply as a
	 * callback, defining the behavior of the collation. It should accept two
	 * values and return as strcmp does, i.e. it should
	 * return -1, 1, or 0 if the first string sorts before, sorts after, or is
	 * equal to the second.
	 * <p>This function need to be defined as:
	 * intcollation
	 * mixedvalue1
	 * mixedvalue2</p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function createCollation (string $name, callable $callback): bool {}

	/**
	 * Opens a stream resource to read a BLOB
	 * @link http://www.php.net/manual/en/sqlite3.openblob.php
	 * @param string $table 
	 * @param string $column 
	 * @param int $rowid 
	 * @param string $database [optional] 
	 * @param int $flags [optional] 
	 * @return resource|false Returns a stream resource, or false on failure.
	 */
	public function openBlob (string $table, string $column, int $rowid, string $database = '"main"', int $flags = SQLITE3_OPEN_READONLY) {}

	/**
	 * Enable throwing exceptions
	 * @link http://www.php.net/manual/en/sqlite3.enableexceptions.php
	 * @param bool $enable [optional] When true, the SQLite3 instance, and 
	 * SQLite3Stmt and SQLite3Result
	 * instances derived from it, will throw exceptions on error.
	 * <p>When false, the SQLite3 instance, and
	 * SQLite3Stmt and SQLite3Result
	 * instances derived from it, will raise warnings on error.</p>
	 * <p>For either mode, the error code and message, if any, will be available via
	 * SQLite3::lastErrorCode and
	 * SQLite3::lastErrorMsg respectively.</p>
	 * @return bool Returns the old value; true if exceptions were enabled, false otherwise.
	 */
	public function enableExceptions (bool $enable = false): bool {}

	/**
	 * {@inheritdoc}
	 * @param bool $enable [optional]
	 */
	public function enableExtendedResultCodes (bool $enable = true) {}

	/**
	 * Configures a callback to be used as an authorizer to limit what a statement can do
	 * @link http://www.php.net/manual/en/sqlite3.setauthorizer.php
	 * @param callable|null $callback The callable to be called.
	 * <p>If null is passed instead, this will disable the current authorizer callback.</p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function setAuthorizer (?callable $callback): bool {}

}

/**
 * A class that handles prepared statements for the SQLite 3 extension.
 * @link http://www.php.net/manual/en/class.sqlite3stmt.php
 */
class SQLite3Stmt  {

	/**
	 * Constructs an SQLite3Stmt object
	 * @link http://www.php.net/manual/en/sqlite3stmt.construct.php
	 * @param SQLite3 $sqlite3 
	 * @param string $query 
	 * @return SQLite3 
	 */
	private function __construct (SQLite3 $sqlite3, string $query): SQLite3 {}

	/**
	 * Binds a parameter to a statement variable
	 * @link http://www.php.net/manual/en/sqlite3stmt.bindparam.php
	 * @param string|int $param 
	 * @param mixed $var 
	 * @param int $type [optional] 
	 * @return bool Returns true if the parameter is bound to the statement variable, false
	 * on failure.
	 */
	public function bindParam (string|int $param, mixed &$var, int $type = SQLITE3_TEXT): bool {}

	/**
	 * Binds the value of a parameter to a statement variable
	 * @link http://www.php.net/manual/en/sqlite3stmt.bindvalue.php
	 * @param string|int $param 
	 * @param mixed $value 
	 * @param int $type [optional] 
	 * @return bool Returns true if the value is bound to the statement variable, or false on failure.
	 */
	public function bindValue (string|int $param, mixed $value, int $type = SQLITE3_TEXT): bool {}

	/**
	 * Clears all current bound parameters
	 * @link http://www.php.net/manual/en/sqlite3stmt.clear.php
	 * @return bool Returns true on successful clearing of bound parameters, false on
	 * failure.
	 */
	public function clear (): bool {}

	/**
	 * Closes the prepared statement
	 * @link http://www.php.net/manual/en/sqlite3stmt.close.php
	 * @return bool Returns true
	 */
	public function close (): bool {}

	/**
	 * Executes a prepared statement and returns a result set object
	 * @link http://www.php.net/manual/en/sqlite3stmt.execute.php
	 * @return SQLite3Result|false Returns an SQLite3Result object on successful execution of the prepared
	 * statement, false on failure.
	 */
	public function execute (): SQLite3Result|false {}

	/**
	 * Get the SQL of the statement
	 * @link http://www.php.net/manual/en/sqlite3stmt.getsql.php
	 * @param bool $expand [optional] Whether to retrieve the expanded SQL. Passing true is only supported as
	 * of libsqlite 3.14.
	 * @return string|false Returns the SQL of the prepared statement, or false on failure.
	 */
	public function getSQL (bool $expand = false): string|false {}

	/**
	 * Returns the number of parameters within the prepared statement
	 * @link http://www.php.net/manual/en/sqlite3stmt.paramcount.php
	 * @return int Returns the number of parameters within the prepared statement.
	 */
	public function paramCount (): int {}

	/**
	 * Returns whether a statement is definitely read only
	 * @link http://www.php.net/manual/en/sqlite3stmt.readonly.php
	 * @return bool Returns true if a statement is definitely read only, false otherwise.
	 */
	public function readOnly (): bool {}

	/**
	 * Resets the prepared statement
	 * @link http://www.php.net/manual/en/sqlite3stmt.reset.php
	 * @return bool Returns true if the statement is successfully reset, or false on failure.
	 */
	public function reset (): bool {}

}

/**
 * A class that handles result sets for the SQLite 3 extension.
 * @link http://www.php.net/manual/en/class.sqlite3result.php
 */
class SQLite3Result  {

	/**
	 * Constructs an SQLite3Result
	 * @link http://www.php.net/manual/en/sqlite3result.construct.php
	 */
	private function __construct () {}

	/**
	 * Returns the number of columns in the result set
	 * @link http://www.php.net/manual/en/sqlite3result.numcolumns.php
	 * @return int Returns the number of columns in the result set.
	 */
	public function numColumns (): int {}

	/**
	 * Returns the name of the nth column
	 * @link http://www.php.net/manual/en/sqlite3result.columnname.php
	 * @param int $column 
	 * @return string|false Returns the string name of the column identified by
	 * column, or false if the column does not exist.
	 */
	public function columnName (int $column): string|false {}

	/**
	 * Returns the type of the nth column
	 * @link http://www.php.net/manual/en/sqlite3result.columntype.php
	 * @param int $column 
	 * @return int|false Returns the data type index of the column identified by
	 * column (one of
	 * SQLITE3_INTEGER, SQLITE3_FLOAT,
	 * SQLITE3_TEXT, SQLITE3_BLOB, or
	 * SQLITE3_NULL), or false if the column does not exist.
	 */
	public function columnType (int $column): int|false {}

	/**
	 * Fetches a result row as an associative or numerically indexed array or both
	 * @link http://www.php.net/manual/en/sqlite3result.fetcharray.php
	 * @param int $mode [optional] 
	 * @return array|false Returns a result row as an associatively or numerically indexed array or
	 * both. Alternately will return false if there are no more rows.
	 * <p>The types of the values of the returned array are mapped from SQLite3 types
	 * as follows: integers are mapped to int if they fit into the
	 * range PHP_INT_MIN..PHP_INT_MAX, and
	 * to string otherwise. Floats are mapped to float,
	 * NULL values are mapped to null, and strings
	 * and blobs are mapped to string.</p>
	 */
	public function fetchArray (int $mode = SQLITE3_BOTH): array|false {}

	/**
	 * Resets the result set back to the first row
	 * @link http://www.php.net/manual/en/sqlite3result.reset.php
	 * @return bool Returns true if the result set is successfully reset
	 * back to the first row, false on failure.
	 */
	public function reset (): bool {}

	/**
	 * Closes the result set
	 * @link http://www.php.net/manual/en/sqlite3result.finalize.php
	 * @return bool Returns true.
	 */
	public function finalize (): bool {}

}

/**
 * Specifies that the Sqlite3Result::fetchArray
 * method shall return an array indexed by column name as returned in the
 * corresponding result set.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 * @var int
 */
define ('SQLITE3_ASSOC', 1);

/**
 * Specifies that the Sqlite3Result::fetchArray
 * method shall return an array indexed by column number as returned in the
 * corresponding result set, starting at column 0.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 * @var int
 */
define ('SQLITE3_NUM', 2);

/**
 * Specifies that the Sqlite3Result::fetchArray
 * method shall return an array indexed by both column name and number as
 * returned in the corresponding result set, starting at column 0.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 * @var int
 */
define ('SQLITE3_BOTH', 3);

/**
 * Represents the SQLite3 INTEGER storage class.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 * @var int
 */
define ('SQLITE3_INTEGER', 1);

/**
 * Represents the SQLite3 REAL (FLOAT) storage class.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 * @var int
 */
define ('SQLITE3_FLOAT', 2);

/**
 * Represents the SQLite3 TEXT storage class.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 * @var int
 */
define ('SQLITE3_TEXT', 3);

/**
 * Represents the SQLite3 BLOB storage class.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 * @var int
 */
define ('SQLITE3_BLOB', 4);

/**
 * Represents the SQLite3 NULL storage class.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 * @var int
 */
define ('SQLITE3_NULL', 5);

/**
 * Specifies that the SQLite3 database be opened for reading only.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 * @var int
 */
define ('SQLITE3_OPEN_READONLY', 1);

/**
 * Specifies that the SQLite3 database be opened for reading and writing.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 * @var int
 */
define ('SQLITE3_OPEN_READWRITE', 2);

/**
 * Specifies that the SQLite3 database be created if it does not already
 * exist.
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 * @var int
 */
define ('SQLITE3_OPEN_CREATE', 4);

/**
 * Specifies that a function created with SQLite3::createFunction
 * is deterministic, i.e. it always returns the same result given the same inputs within
 * a single SQL statement. (Available as of PHP 7.1.4.)
 * @link http://www.php.net/manual/en/sqlite3.constants.php
 * @var int
 */
define ('SQLITE3_DETERMINISTIC', 2048);

// End of sqlite3 v.8.2.6
