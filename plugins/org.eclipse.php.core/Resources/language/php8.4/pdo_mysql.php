<?php

// Start of pdo_mysql v.8.4.7

namespace Pdo {

class Mysql extends \PDO  {
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
	/**
	 * By default all statements are executed in
	 * buffered mode.
	 * If this attribute is set to false on a
	 * Pdo\Mysql object,
	 * the MySQL driver will use the unbuffered mode.
	const ATTR_USE_BUFFERED_QUERY = 1000;
	/**
	 * Enable LOAD LOCAL INFILE.
	 * Can only be used in the driver_options
	 * array when constructing a new database handle.
	const ATTR_LOCAL_INFILE = 1001;
	/**
	 * Command to execute when connecting to the MySQL server.
	 * Will automatically be re-executed when reconnecting.
	 * Can only be used in the driver_options
	 * array when constructing a new database handle.
	const ATTR_INIT_COMMAND = 1002;
	/**
	 * Enable network communication compression.
	const ATTR_COMPRESS = 1003;
	/**
	 * Alias of PDO::ATTR_EMULATE_PREPARES.
	const ATTR_DIRECT_QUERY = 1004;
	/**
	 * Return the number of found (matched) rows,
	 * not the number of changed rows.
	 * Can only be used in the driver_options
	 * array when constructing a new database handle.
	const ATTR_FOUND_ROWS = 1005;
	/**
	 * Permit spaces after SQL function names.
	 * Makes all SQL functions names reserved words.
	 * Can only be used in the driver_options
	 * array when constructing a new database handle.
	const ATTR_IGNORE_SPACE = 1006;
	/**
	 * The file path to the SSL key.
	 * Can only be used in the driver_options
	 * array when constructing a new database handle.
	const ATTR_SSL_KEY = 1007;
	/**
	 * The file path to the SSL certificate.
	 * Can only be used in the driver_options
	 * array when constructing a new database handle.
	const ATTR_SSL_CERT = 1008;
	/**
	 * The file path to the SSL certificate authority.
	 * Can only be used in the driver_options
	 * array when constructing a new database handle.
	const ATTR_SSL_CA = 1009;
	/**
	 * The file path to the directory that contains the trusted
	 * SSL CA certificates,
	 * which are stored in PEM format.
	 * Can only be used in the driver_options
	 * array when constructing a new database handle.
	const ATTR_SSL_CAPATH = 1010;
	/**
	 * A list of one or more permissible ciphers to use for
	 * SSL encryption, in a format understood by OpenSSL.
	 * For example: DHE-RSA-AES256-SHA:AES128-SHA
	 * Can only be used in the driver_options
	 * array when constructing a new database handle.
	const ATTR_SSL_CIPHER = 1011;
	/**
	 * RSA public key file used with the SHA-256 based authentication.
	 * Can only be used in the driver_options
	 * array when constructing a new database handle.
	const ATTR_SERVER_PUBLIC_KEY = 1012;
	/**
	 * Disables multi query execution in both
	 * PDO::prepare and
	 * PDO::query when set to false.
	 * Can only be used in the driver_options
	 * array when constructing a new database handle.
	const ATTR_MULTI_STATEMENTS = 1013;
	/**
	 * Provides a way to disable verification of the server SSL certificate.
	 * This option is available only with mysqlnd.
	 * Can only be used in the driver_options
	 * array when constructing a new database handle.
	const ATTR_SSL_VERIFY_SERVER_CERT = 1014;
	/**
	 * Allows restricting LOCAL DATA loading to files located in this
	 * designated directory.
	 * Can only be used in the driver_options
	 * array when constructing a new database handle.
	const ATTR_LOCAL_INFILE_DIRECTORY = 1015;


	/**
	 * Returns the number of warnings from the last executed query
	 * @link http://www.php.net/manual/en/pdo-mysql.getwarningcount.php
	 * @return int Returns an int representing the number of warnings generated by the last query.
	 */
	public function getWarningCount (): int {}

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

// End of pdo_mysql v.8.4.7
