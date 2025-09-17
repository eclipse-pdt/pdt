<?php

// Start of pdo_pgsql v.8.4.7

namespace Pdo {

class Pgsql extends \PDO  {
	const PARAM_NULL = 0;
	const PARAM_BOOL = 5;
	const PARAM_INT = 1;
	const PARAM_STR = 2;
	const PARAM_LOB = 3;
	const PARAM_STMT = 4;
	const PARAM_INPUT_OUTPUT = 2147483648;
	const PARAM_STR_NATL = 1073741824;
	const PARAM_STR_CHAR = 536870912;
	const PARAM_EVT_ALLOC = 0;
	const PARAM_EVT_FREE = 1;
	const PARAM_EVT_EXEC_PRE = 2;
	const PARAM_EVT_EXEC_POST = 3;
	const PARAM_EVT_FETCH_PRE = 4;
	const PARAM_EVT_FETCH_POST = 5;
	const PARAM_EVT_NORMALIZE = 6;
	const FETCH_DEFAULT = 0;
	const FETCH_LAZY = 1;
	const FETCH_ASSOC = 2;
	const FETCH_NUM = 3;
	const FETCH_BOTH = 4;
	const FETCH_OBJ = 5;
	const FETCH_BOUND = 6;
	const FETCH_COLUMN = 7;
	const FETCH_CLASS = 8;
	const FETCH_INTO = 9;
	const FETCH_FUNC = 10;
	const FETCH_GROUP = 65536;
	const FETCH_UNIQUE = 196608;
	const FETCH_KEY_PAIR = 12;
	const FETCH_CLASSTYPE = 262144;
	const FETCH_SERIALIZE = 524288;
	const FETCH_PROPS_LATE = 1048576;
	const FETCH_NAMED = 11;
	const ATTR_AUTOCOMMIT = 0;
	const ATTR_PREFETCH = 1;
	const ATTR_TIMEOUT = 2;
	const ATTR_ERRMODE = 3;
	const ATTR_SERVER_VERSION = 4;
	const ATTR_CLIENT_VERSION = 5;
	const ATTR_SERVER_INFO = 6;
	const ATTR_CONNECTION_STATUS = 7;
	const ATTR_CASE = 8;
	const ATTR_CURSOR_NAME = 9;
	const ATTR_CURSOR = 10;
	const ATTR_ORACLE_NULLS = 11;
	const ATTR_PERSISTENT = 12;
	const ATTR_STATEMENT_CLASS = 13;
	const ATTR_FETCH_TABLE_NAMES = 14;
	const ATTR_FETCH_CATALOG_NAMES = 15;
	const ATTR_DRIVER_NAME = 16;
	const ATTR_STRINGIFY_FETCHES = 17;
	const ATTR_MAX_COLUMN_LEN = 18;
	const ATTR_EMULATE_PREPARES = 20;
	const ATTR_DEFAULT_FETCH_MODE = 19;
	const ATTR_DEFAULT_STR_PARAM = 21;
	const ERRMODE_SILENT = 0;
	const ERRMODE_WARNING = 1;
	const ERRMODE_EXCEPTION = 2;
	const CASE_NATURAL = 0;
	const CASE_LOWER = 2;
	const CASE_UPPER = 1;
	const NULL_NATURAL = 0;
	const NULL_EMPTY_STRING = 1;
	const NULL_TO_STRING = 2;
	const ERR_NONE = 00000;
	const FETCH_ORI_NEXT = 0;
	const FETCH_ORI_PRIOR = 1;
	const FETCH_ORI_FIRST = 2;
	const FETCH_ORI_LAST = 3;
	const FETCH_ORI_ABS = 4;
	const FETCH_ORI_REL = 5;
	const CURSOR_FWDONLY = 0;
	const CURSOR_SCROLL = 1;
	const DBLIB_ATTR_CONNECTION_TIMEOUT = 1000;
	const DBLIB_ATTR_QUERY_TIMEOUT = 1001;
	const DBLIB_ATTR_STRINGIFY_UNIQUEIDENTIFIER = 1002;
	const DBLIB_ATTR_VERSION = 1003;
	const DBLIB_ATTR_TDS_VERSION = 1004;
	const DBLIB_ATTR_SKIP_EMPTY_ROWSETS = 1005;
	const DBLIB_ATTR_DATETIME_CONVERT = 1006;
	const MYSQL_ATTR_USE_BUFFERED_QUERY = 1000;
	const MYSQL_ATTR_LOCAL_INFILE = 1001;
	const MYSQL_ATTR_INIT_COMMAND = 1002;
	const MYSQL_ATTR_COMPRESS = 1003;
	const MYSQL_ATTR_DIRECT_QUERY = 1004;
	const MYSQL_ATTR_FOUND_ROWS = 1005;
	const MYSQL_ATTR_IGNORE_SPACE = 1006;
	const MYSQL_ATTR_SSL_KEY = 1007;
	const MYSQL_ATTR_SSL_CERT = 1008;
	const MYSQL_ATTR_SSL_CA = 1009;
	const MYSQL_ATTR_SSL_CAPATH = 1010;
	const MYSQL_ATTR_SSL_CIPHER = 1011;
	const MYSQL_ATTR_SERVER_PUBLIC_KEY = 1012;
	const MYSQL_ATTR_MULTI_STATEMENTS = 1013;
	const MYSQL_ATTR_SSL_VERIFY_SERVER_CERT = 1014;
	const MYSQL_ATTR_LOCAL_INFILE_DIRECTORY = 1015;
	const ODBC_ATTR_USE_CURSOR_LIBRARY = 1000;
	const ODBC_ATTR_ASSUME_UTF8 = 1001;
	const ODBC_SQL_USE_IF_NEEDED = 0;
	const ODBC_SQL_USE_DRIVER = 2;
	const ODBC_SQL_USE_ODBC = 1;
	const PGSQL_ATTR_DISABLE_PREPARES = 1000;
	const PGSQL_TRANSACTION_IDLE = 0;
	const PGSQL_TRANSACTION_ACTIVE = 1;
	const PGSQL_TRANSACTION_INTRANS = 2;
	const PGSQL_TRANSACTION_INERROR = 3;
	const PGSQL_TRANSACTION_UNKNOWN = 4;
	/**
	 * Send the query and the parameters to the server together in a single
	 * call, avoiding the need to create a named prepared statement separately.
	 * If the query is only going to be executed once this can reduce latency by
	 * avoiding an unnecessary server round-trip.
	const ATTR_DISABLE_PREPARES = 1000;
	/**
	 * Returns the amount of memory, in bytes, allocated to the specified
	 * query result PDOStatement instance,
	 * or null if no results exist before the query is executed.
	const ATTR_RESULT_MEMORY_SIZE = 1001;
	const TRANSACTION_IDLE = 0;
	const TRANSACTION_ACTIVE = 1;
	const TRANSACTION_INTRANS = 2;
	const TRANSACTION_INERROR = 3;
	const TRANSACTION_UNKNOWN = 4;


	/**
	 * Escapes a string for use as an SQL identifier
	 * @link http://www.php.net/manual/en/pdo-pgsql.escapeidentifier.php
	 * @param string $input A string containing text to be escaped.
	 * @return string A string containing the escaped data.
	 */
	public function escapeIdentifier (string $input): string {}

	/**
	 * Copy data from a PHP array into a table
	 * @link http://www.php.net/manual/en/pdo-pgsql.copyfromarray.php
	 * @param string $tableName String containing table name.
	 * @param array $rows An indexed array of strings with fields
	 * separated by separator.
	 * @param string $separator [optional] Delimiter used to separate fields in an entry of the
	 * rows array.
	 * @param string $nullAs [optional] How to interpret SQL NULL values.
	 * @param string|null $fields [optional] List of fields to insert.
	 * @return bool Returns true on success or false on failure.
	 */
	public function copyFromArray (string $tableName, array $rows, string $separator = '"\\t"', string $nullAs = '"\\\\\\\\N"', ?string $fields = null): bool {}

	/**
	 * Copy data from file into table
	 * @link http://www.php.net/manual/en/pdo-pgsql.copyfromfile.php
	 * @param string $tableName String containing table name.
	 * @param string $filename Filename containing the data to import.
	 * @param string $separator [optional] Delimiter used to separate fields in an entry of the
	 * rows array.
	 * @param string $nullAs [optional] How to interpret SQL NULL values.
	 * @param string|null $fields [optional] List of fields to insert.
	 * @return bool Returns true on success or false on failure.
	 */
	public function copyFromFile (string $tableName, string $filename, string $separator = '"\\t"', string $nullAs = '"\\\\\\\\N"', ?string $fields = null): bool {}

	/**
	 * Copy data from database table into PHP array
	 * @link http://www.php.net/manual/en/pdo-pgsql.copytoarray.php
	 * @param string $tableName String containing table name.
	 * @param string $separator [optional] Delimiter used to separate fields in an entry of the
	 * rows array.
	 * @param string $nullAs [optional] How to interpret SQL NULL values.
	 * @param string|null $fields [optional] List of fields to export.
	 * @return array|false Returns an array of rows, or false on failure.
	 */
	public function copyToArray (string $tableName, string $separator = '"\\t"', string $nullAs = '"\\\\\\\\N"', ?string $fields = null): array|false {}

	/**
	 * Copy data from table into file
	 * @link http://www.php.net/manual/en/pdo-pgsql.copytofile.php
	 * @param string $tableName String containing table name.
	 * @param string $filename Filename to export data.
	 * @param string $separator [optional] Delimiter used to separate fields in an entry of the
	 * rows array.
	 * @param string $nullAs [optional] How to interpret SQL NULL values.
	 * @param string|null $fields [optional] List of fields to insert.
	 * @return bool Returns true on success or false on failure.
	 */
	public function copyToFile (string $tableName, string $filename, string $separator = '"\\t"', string $nullAs = '"\\\\\\\\N"', ?string $fields = null): bool {}

	/**
	 * Creates a new large object
	 * @link http://www.php.net/manual/en/pdo-pgsql.lobcreate.php
	 * @return string|false Returns the OID of the newly created large object on success,
	 * or false on failure.
	 */
	public function lobCreate (): string|false {}

	/**
	 * Opens an existing large object stream
	 * @link http://www.php.net/manual/en/pdo-pgsql.lobopen.php
	 * @param string $oid A large object identifier.
	 * @param string $mode [optional] If mode is r, open the stream for reading.
	 * If mode is w, open the stream for writing.
	 * @return resource|false Returns a stream resource on success, or false on failure.
	 */
	public function lobOpen (string $oid, string $mode = '"rb"') {}

	/**
	 * Deletes the large object
	 * @link http://www.php.net/manual/en/pdo-pgsql.lobunlink.php
	 * @param string $oid A large object identifier.
	 * @return bool Returns true on success or false on failure.
	 */
	public function lobUnlink (string $oid): bool {}

	/**
	 * Get asynchronous notification
	 * @link http://www.php.net/manual/en/pdo-pgsql.getnotify.php
	 * @param int $fetchMode [optional] The format the result set should be returned as,
	 * one of the following constants:
	 * <p>
	 * PDO::FETCH_DEFAULT
	 * PDO::FETCH_BOTH
	 * PDO::FETCH_ASSOC
	 * PDO::FETCH_NUM
	 * </p>
	 * @param int $timeoutMilliseconds [optional] The length of time to wait for a response, in milliseconds.
	 * @return array|false If one or more notifications is pending, returns a single row,
	 * with fields message and pid,
	 * otherwise returns false.
	 */
	public function getNotify (int $fetchMode = \PDO::FETCH_DEFAULT, int $timeoutMilliseconds = null): array|false {}

	/**
	 * Get the PID of the backend process handling this connection
	 * @link http://www.php.net/manual/en/pdo-pgsql.getpid.php
	 * @return int Returns the PID as an int.
	 */
	public function getPid (): int {}

	/**
	 * Set a callback to handle notice and warning messages generated by the backend
	 * @link http://www.php.net/manual/en/pdo-pgsql.setnoticecallback.php
	 * @param callable|null $callback Otherwise, the handler is a callback with the following signature:
	 * voidhandler
	 * stringmessage
	 * <p>
	 * message
	 * <br>
	 * A message generated by the backend.
	 * </p>
	 * @return void No value is returned.
	 */
	public function setNoticeCallback (?callable $callback): void {}

	/**
	 * Creates a PDO instance representing a connection to a database
	 * @link http://www.php.net/manual/en/pdo.construct.php
	 * @param string $dsn 
	 * @param string|null $username [optional] 
	 * @param string|null $password [optional] 
	 * @param array|null $options [optional] 
	 * @return string 
	 */
	public function __construct (string $dsn, ?string $username = null, ?string $password = null, ?array $options = null): string {}

	/**
	 * Connect to a database and return a PDO subclass for drivers that support it
	 * @link http://www.php.net/manual/en/pdo.connect.php
	 * @param string $dsn 
	 * @param string|null $username [optional] 
	 * @param string|null $password [optional] 
	 * @param array|null $options [optional] 
	 * @return static Returns an instance of a PDO subclass for the
	 * corresponding PDO driver if it exists,
	 * or a generic PDO instance.
	 */
	public static function connect (string $dsn, ?string $username = null, ?string $password = null, ?array $options = null): static {}

	/**
	 * Initiates a transaction
	 * @link http://www.php.net/manual/en/pdo.begintransaction.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function beginTransaction (): bool {}

	/**
	 * Commits a transaction
	 * @link http://www.php.net/manual/en/pdo.commit.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function commit (): bool {}

	/**
	 * Fetch the SQLSTATE associated with the last operation on the database handle
	 * @link http://www.php.net/manual/en/pdo.errorcode.php
	 * @return string|null Returns an SQLSTATE, a five characters alphanumeric identifier defined in
	 * the ANSI SQL-92 standard. Briefly, an SQLSTATE consists of a
	 * two characters class value followed by a three characters subclass value. A
	 * class value of 01 indicates a warning and is accompanied by a return code
	 * of SQL_SUCCESS_WITH_INFO. Class values other than '01', except for the
	 * class 'IM', indicate an error. The class 'IM' is specific to warnings
	 * and errors that derive from the implementation of PDO (or perhaps ODBC,
	 * if you're using the ODBC driver) itself. The subclass value '000' in any
	 * class indicates that there is no subclass for that SQLSTATE.
	 * <p>PDO::errorCode only retrieves error codes for operations
	 * performed directly on the database handle. If you create a PDOStatement
	 * object through PDO::prepare or
	 * PDO::query and invoke an error on the statement
	 * handle, PDO::errorCode will not reflect that error.
	 * You must call PDOStatement::errorCode to return the error
	 * code for an operation performed on a particular statement handle.</p>
	 * <p>Returns null if no operation has been run on the database handle.</p>
	 */
	public function errorCode (): ?string {}

	/**
	 * Fetch extended error information associated with the last operation on the database handle
	 * @link http://www.php.net/manual/en/pdo.errorinfo.php
	 * @return array PDO::errorInfo returns an array of error information
	 * about the last operation performed by this database handle. The array
	 * consists of at least the following fields:
	 * <table>
	 * <tr valign="top">
	 * <td>Element</td>
	 * <td>Information</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>0</td>
	 * <td>SQLSTATE error code (a five characters alphanumeric identifier defined
	 * in the ANSI SQL standard).</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>1</td>
	 * <td>Driver-specific error code.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>2</td>
	 * <td>Driver-specific error message.</td>
	 * </tr>
	 * </table>
	 * <p>If the SQLSTATE error code is not set or there is no driver-specific
	 * error, the elements following element 0 will be set to null.</p>
	 * <p>PDO::errorInfo only retrieves error information for
	 * operations performed directly on the database handle. If you create a
	 * PDOStatement object through PDO::prepare or
	 * PDO::query and invoke an error on the statement
	 * handle, PDO::errorInfo will not reflect the error
	 * from the statement handle. You must call
	 * PDOStatement::errorInfo to return the error
	 * information for an operation performed on a particular statement handle.</p>
	 */
	public function errorInfo (): array {}

	/**
	 * Execute an SQL statement and return the number of affected rows
	 * @link http://www.php.net/manual/en/pdo.exec.php
	 * @param string $statement 
	 * @return int|false PDO::exec returns the number of rows that were modified
	 * or deleted by the SQL statement you issued. If no rows were affected, 
	 * PDO::exec returns 0.
	 * <p>The following example incorrectly relies on the return value of
	 * PDO::exec, wherein a statement that affected 0 rows
	 * results in a call to die:
	 * <pre>
	 * <code>&lt;?php
	 * $db-&gt;exec() or die(print_r($db-&gt;errorInfo(), true)); &#47;&#47; incorrect
	 * ?&gt;</code>
	 * </pre></p>
	 */
	public function exec (string $statement): int|false {}

	/**
	 * Retrieve a database connection attribute
	 * @link http://www.php.net/manual/en/pdo.getattribute.php
	 * @param int $attribute 
	 * @return mixed A successful call returns the value of the requested PDO attribute.
	 * An unsuccessful call returns null.
	 */
	public function getAttribute (int $attribute): mixed {}

	/**
	 * Return an array of available PDO drivers
	 * @link http://www.php.net/manual/en/pdo.getavailabledrivers.php
	 * @return array PDO::getAvailableDrivers returns an array of PDO driver names. If
	 * no drivers are available, it returns an empty array.
	 */
	public static function getAvailableDrivers (): array {}

	/**
	 * Checks if inside a transaction
	 * @link http://www.php.net/manual/en/pdo.intransaction.php
	 * @return bool Returns true if a transaction is currently active, and false if not.
	 */
	public function inTransaction (): bool {}

	/**
	 * Returns the ID of the last inserted row or sequence value
	 * @link http://www.php.net/manual/en/pdo.lastinsertid.php
	 * @param string|null $name [optional] 
	 * @return string|false If a sequence name was not specified for the name
	 * parameter, PDO::lastInsertId returns a
	 * string representing the row ID of the last row that was inserted into
	 * the database.
	 * <p>If a sequence name was specified for the name
	 * parameter, PDO::lastInsertId returns a
	 * string representing the last value retrieved from the specified sequence
	 * object.</p>
	 * <p>If the PDO driver does not support this capability,
	 * PDO::lastInsertId triggers an
	 * IM001 SQLSTATE.</p>
	 */
	public function lastInsertId (?string $name = null): string|false {}

	/**
	 * Prepares a statement for execution and returns a statement object
	 * @link http://www.php.net/manual/en/pdo.prepare.php
	 * @param string $query 
	 * @param array $options [optional] 
	 * @return \PDOStatement|false If the database server successfully prepares the statement,
	 * PDO::prepare returns a
	 * PDOStatement object.
	 * If the database server cannot successfully prepare the statement,
	 * PDO::prepare returns false or emits
	 * PDOException (depending on error handling).
	 * <p>Emulated prepared statements does not communicate with the database server
	 * so PDO::prepare does not check the statement.</p>
	 */
	public function prepare (string $query, array $options = '[]'): \PDOStatement|false {}

	/**
	 * Prepares and executes an SQL statement without placeholders
	 * @link http://www.php.net/manual/en/pdo.query.php
	 * @param string $query 
	 * @param int|null $fetchMode [optional] 
	 * @return \PDOStatement|false Returns a PDOStatement object or false on failure.
	 */
	public function query (string $query, ?int $fetchMode = null): \PDOStatement|false {}

	/**
	 * Quotes a string for use in a query
	 * @link http://www.php.net/manual/en/pdo.quote.php
	 * @param string $string 
	 * @param int $type [optional] 
	 * @return string|false Returns a quoted string that is theoretically safe to pass into an
	 * SQL statement. Returns false if the driver does not support quoting in
	 * this way.
	 */
	public function quote (string $string, int $type = \PDO::PARAM_STR): string|false {}

	/**
	 * Rolls back a transaction
	 * @link http://www.php.net/manual/en/pdo.rollback.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function rollBack (): bool {}

	/**
	 * Set an attribute
	 * @link http://www.php.net/manual/en/pdo.setattribute.php
	 * @param int $attribute 
	 * @param mixed $value 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setAttribute (int $attribute, mixed $value): bool {}

}

}

// End of pdo_pgsql v.8.4.7
