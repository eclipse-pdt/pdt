<?php

// Start of PDO v.8.2.6

/**
 * Represents an error raised by PDO. You should not throw a
 * PDOException from your own code.
 * See Exceptions for more
 * information about Exceptions in PHP.
 * @link http://www.php.net/manual/en/class.pdoexception.php
 */
class PDOException extends RuntimeException implements Stringable, Throwable {

	/**
	 * The exception code
	 * @var int
	 * @link http://www.php.net/manual/en/class.pdoexception.php#pdoexception.props.code
	 */
	protected int $code;

	/**
	 * Corresponds to PDO::errorInfo or
	 * PDOStatement::errorInfo
	 * @var array|null
	 * @link http://www.php.net/manual/en/class.pdoexception.php#pdoexception.props.errorinfo
	 */
	public ?array $errorInfo;

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return int Returns the exception code as int in
	 * Exception but possibly as other type in
	 * Exception descendants (for example as
	 * string in PDOException).
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Returns the filename in which the exception was created.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Returns the line number where the exception was created.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString (): string {}

}

/**
 * Represents a connection between PHP and a database server.
 * @link http://www.php.net/manual/en/class.pdo.php
 */
class PDO  {
	/**
	 * Represents the SQL NULL data type.
	 * @var int
	const PARAM_NULL = 0;
	/**
	 * Represents a boolean data type.
	 * @var int
	const PARAM_BOOL = 5;
	/**
	 * Represents the SQL INTEGER data type.
	 * @var int
	const PARAM_INT = 1;
	/**
	 * Represents the SQL CHAR, VARCHAR, or other string data type.
	 * @var int
	const PARAM_STR = 2;
	/**
	 * Represents the SQL large object data type.
	 * @var int
	const PARAM_LOB = 3;
	/**
	 * Represents a recordset type. Not currently supported by any drivers.
	 * @var int
	const PARAM_STMT = 4;
	/**
	 * Specifies that the parameter is an INOUT parameter for a stored
	 * procedure. You must bitwise-OR this value with an explicit
	 * PDO::PARAM_&#42; data type.
	 * @var int
	const PARAM_INPUT_OUTPUT = 2147483648;
	/**
	 * Flag to denote a string uses the national character set.
	 * Available since PHP 7.2.0
	 * @var int
	const PARAM_STR_NATL = 1073741824;
	/**
	 * Flag to denote a string uses the regular character set.
	 * Available since PHP 7.2.0
	 * @var int
	const PARAM_STR_CHAR = 536870912;
	/**
	 * Allocation event
	 * @var int
	const PARAM_EVT_ALLOC = 0;
	/**
	 * Deallocation event
	 * @var int
	const PARAM_EVT_FREE = 1;
	/**
	 * Event triggered prior to execution of a prepared statement.
	 * @var int
	const PARAM_EVT_EXEC_PRE = 2;
	/**
	 * Event triggered subsequent to execution of a prepared statement.
	 * @var int
	const PARAM_EVT_EXEC_POST = 3;
	/**
	 * Event triggered prior to fetching a result from a resultset.
	 * @var int
	const PARAM_EVT_FETCH_PRE = 4;
	/**
	 * Event triggered subsequent to fetching a result from a resultset.
	 * @var int
	const PARAM_EVT_FETCH_POST = 5;
	/**
	 * Event triggered during bound parameter registration
	 * allowing the driver to normalize the parameter name.
	 * @var int
	const PARAM_EVT_NORMALIZE = 6;
	/**
	 * Specifies that the default fetch mode shall be used. Available as of PHP 8.0.7.
	 * @var int
	const FETCH_DEFAULT = 0;
	/**
	 * Specifies that the fetch method shall return each row as an object with
	 * variable names that correspond to the column names returned in the result
	 * set. PDO::FETCH_LAZY creates the object variable names as they are accessed.
	 * Not valid inside PDOStatement::fetchAll.
	 * @var int
	const FETCH_LAZY = 1;
	/**
	 * Specifies that the fetch method shall return each row as an array indexed
	 * by column name as returned in the corresponding result set. If the result
	 * set contains multiple columns with the same name,
	 * PDO::FETCH_ASSOC returns
	 * only a single value per column name.
	 * @var int
	const FETCH_ASSOC = 2;
	/**
	 * Specifies that the fetch method shall return each row as an array indexed
	 * by column number as returned in the corresponding result set, starting at
	 * column 0.
	 * @var int
	const FETCH_NUM = 3;
	/**
	 * Specifies that the fetch method shall return each row as an array indexed
	 * by both column name and number as returned in the corresponding result set,
	 * starting at column 0.
	 * @var int
	const FETCH_BOTH = 4;
	/**
	 * Specifies that the fetch method shall return each row as an object with
	 * property names that correspond to the column names returned in the result
	 * set.
	 * @var int
	const FETCH_OBJ = 5;
	/**
	 * Specifies that the fetch method shall return TRUE and assign the values of
	 * the columns in the result set to the PHP variables to which they were
	 * bound with the PDOStatement::bindParam or
	 * PDOStatement::bindColumn methods.
	 * @var int
	const FETCH_BOUND = 6;
	/**
	 * Specifies that the fetch method shall return only a single requested
	 * column from the next row in the result set.
	 * @var int
	const FETCH_COLUMN = 7;
	/**
	 * Specifies that the fetch method shall return a new instance of the
	 * requested class, mapping the columns to named properties in the class.
	 * The magic
	 * __set
	 * method is called if the property doesn't exist in the requested class
	 * @var int
	const FETCH_CLASS = 8;
	/**
	 * Specifies that the fetch method shall update an existing instance of the
	 * requested class, mapping the columns to named properties in the class.
	 * @var int
	const FETCH_INTO = 9;
	/**
	 * Allows completely customize the way data is treated on the fly (only 
	 * valid inside PDOStatement::fetchAll).
	 * @var int
	const FETCH_FUNC = 10;
	/**
	 * Group return by values. Usually combined with
	 * PDO::FETCH_COLUMN or 
	 * PDO::FETCH_KEY_PAIR.
	 * @var int
	const FETCH_GROUP = 65536;
	/**
	 * Fetch only the unique values.
	 * @var int
	const FETCH_UNIQUE = 196608;
	/**
	 * Fetch a two-column result into an array where the first column is a key and the second column
	 * is the value.
	 * @var int
	const FETCH_KEY_PAIR = 12;
	/**
	 * Determine the class name from the value of first column.
	 * @var int
	const FETCH_CLASSTYPE = 262144;
	/**
	 * As PDO::FETCH_INTO but object is provided as a serialized string.
	 * The class constructor is never called if this flag is set.
	 * Deprecated as of PHP 8.1.0.
	 * @var int
	const FETCH_SERIALIZE = 524288;
	/**
	 * Call the constructor before setting properties.
	 * @var int
	const FETCH_PROPS_LATE = 1048576;
	/**
	 * Specifies that the fetch method shall return each row as an array indexed
	 * by column name as returned in the corresponding result set. If the result
	 * set contains multiple columns with the same name,
	 * PDO::FETCH_NAMED returns
	 * an array of values per column name.
	 * @var int
	const FETCH_NAMED = 11;
	/**
	 * If this value is false, PDO attempts to disable autocommit so that the
	 * connection begins a transaction.
	 * @var int
	const ATTR_AUTOCOMMIT = 0;
	/**
	 * Setting the prefetch size allows you to balance speed against memory
	 * usage for your application. Not all database/driver combinations support
	 * setting of the prefetch size. A larger prefetch size results in
	 * increased performance at the cost of higher memory usage.
	 * @var int
	const ATTR_PREFETCH = 1;
	/**
	 * Sets the timeout value in seconds for communications with the database.
	 * @var int
	const ATTR_TIMEOUT = 2;
	/**
	 * See the Errors and error
	 * handling section for more information about this attribute.
	 * @var int
	const ATTR_ERRMODE = 3;
	/**
	 * This is a read only attribute; it will return information about the
	 * version of the database server to which PDO is connected.
	 * @var int
	const ATTR_SERVER_VERSION = 4;
	/**
	 * This is a read only attribute; it will return information about the
	 * version of the client libraries that the PDO driver is using.
	 * @var int
	const ATTR_CLIENT_VERSION = 5;
	/**
	 * This is a read only attribute; it will return some meta information about the
	 * database server to which PDO is connected.
	 * @var int
	const ATTR_SERVER_INFO = 6;
	/**
	 * @var int
	const ATTR_CONNECTION_STATUS = 7;
	/**
	 * Force column names to a specific case specified by the PDO::CASE_&#42;
	 * constants.
	 * @var int
	const ATTR_CASE = 8;
	/**
	 * Get or set the name to use for a cursor. Most useful when using
	 * scrollable cursors and positioned updates.
	 * @var int
	const ATTR_CURSOR_NAME = 9;
	/**
	 * Selects the cursor type. PDO currently supports either
	 * PDO::CURSOR_FWDONLY and 
	 * PDO::CURSOR_SCROLL. Stick with
	 * PDO::CURSOR_FWDONLY unless you know that you need a
	 * scrollable cursor.
	 * @var int
	const ATTR_CURSOR = 10;
	/**
	 * Convert empty strings to SQL NULL values on data fetches.
	 * @var int
	const ATTR_ORACLE_NULLS = 11;
	/**
	 * Request a persistent connection, rather than creating a new connection.
	 * See Connections and Connection
	 * management for more information on this attribute.
	 * @var mixed
	const ATTR_PERSISTENT = 12;
	/**
	 * Sets the class name of which statements are returned as.
	 * @var int
	const ATTR_STATEMENT_CLASS = 13;
	/**
	 * Prepend the containing table name to each column name returned in the
	 * result set. The table name and column name are separated by a decimal (.)
	 * character. Support of this attribute is at the driver level; it may not
	 * be supported by your driver.
	 * @var int
	const ATTR_FETCH_TABLE_NAMES = 14;
	/**
	 * Prepend the containing catalog name to each column name returned in the
	 * result set. The catalog name and column name are separated by a decimal
	 * (.) character. Support of this attribute is at the driver level; it may
	 * not be supported by your driver.
	 * @var int
	const ATTR_FETCH_CATALOG_NAMES = 15;
	/**
	 * using PDO::ATTR_DRIVER_NAME
	 * <pre>
	 * <code>&lt;?php
	 * if ($db-&gt;getAttribute(PDO::ATTR_DRIVER_NAME) == &&#35;039;mysql&&#35;039;) {
	 * echo &quot;Running on mysql; doing something mysql specific here\n&quot;;
	 * }
	 * ?&gt;</code>
	 * </pre>
	 * @var string
	const ATTR_DRIVER_NAME = 16;
	/**
	 * Forces all values fetched to be treated as strings.
	 * @var int
	const ATTR_STRINGIFY_FETCHES = 17;
	/**
	 * Sets the maximum column name length.
	 * @var int
	const ATTR_MAX_COLUMN_LEN = 18;
	/**
	 * @var int
	const ATTR_EMULATE_PREPARES = 20;
	/**
	 * @var int
	const ATTR_DEFAULT_FETCH_MODE = 19;
	/**
	 * Sets the default string parameter type, this can be one of PDO::PARAM_STR_NATL 
	 * and PDO::PARAM_STR_CHAR.
	 * Available since PHP 7.2.0.
	 * @var int
	const ATTR_DEFAULT_STR_PARAM = 21;
	/**
	 * Do not raise an error or exception if an error occurs. The developer is
	 * expected to explicitly check for errors. This is the default mode.
	 * See Errors and error handling
	 * for more information about this attribute.
	 * @var int
	const ERRMODE_SILENT = 0;
	/**
	 * Issue a PHP E_WARNING message if an error occurs.
	 * See Errors and error handling
	 * for more information about this attribute.
	 * @var int
	const ERRMODE_WARNING = 1;
	/**
	 * Throw a PDOException if an error occurs.
	 * See Errors and error handling
	 * for more information about this attribute.
	 * @var int
	const ERRMODE_EXCEPTION = 2;
	/**
	 * Leave column names as returned by the database driver.
	 * @var int
	const CASE_NATURAL = 0;
	/**
	 * Force column names to lower case.
	 * @var int
	const CASE_LOWER = 2;
	/**
	 * Force column names to upper case.
	 * @var int
	const CASE_UPPER = 1;
	/**
	 * @var int
	const NULL_NATURAL = 0;
	/**
	 * @var int
	const NULL_EMPTY_STRING = 1;
	/**
	 * @var int
	const NULL_TO_STRING = 2;
	/**
	 * Corresponds to SQLSTATE '00000', meaning that the SQL statement was
	 * successfully issued with no errors or warnings. This constant is for
	 * your convenience when checking PDO::errorCode or
	 * PDOStatement::errorCode to determine if an error
	 * occurred. You will usually know if this is the case by examining the
	 * return code from the method that raised the error condition anyway.
	 * @var string
	const ERR_NONE = 00000;
	/**
	 * Fetch the next row in the result set. Valid only for scrollable cursors.
	 * @var int
	const FETCH_ORI_NEXT = 0;
	/**
	 * Fetch the previous row in the result set. Valid only for scrollable
	 * cursors.
	 * @var int
	const FETCH_ORI_PRIOR = 1;
	/**
	 * Fetch the first row in the result set. Valid only for scrollable cursors.
	 * @var int
	const FETCH_ORI_FIRST = 2;
	/**
	 * Fetch the last row in the result set. Valid only for scrollable cursors.
	 * @var int
	const FETCH_ORI_LAST = 3;
	/**
	 * Fetch the requested row by row number from the result set. Valid only
	 * for scrollable cursors.
	 * @var int
	const FETCH_ORI_ABS = 4;
	/**
	 * Fetch the requested row by relative position from the current position
	 * of the cursor in the result set. Valid only for scrollable cursors.
	 * @var int
	const FETCH_ORI_REL = 5;
	/**
	 * Create a PDOStatement object with a forward-only cursor. This is the
	 * default cursor choice, as it is the fastest and most common data access
	 * pattern in PHP.
	 * @var int
	const CURSOR_FWDONLY = 0;
	/**
	 * Create a PDOStatement object with a scrollable cursor. Pass the
	 * PDO::FETCH_ORI_&#42; constants to control the rows fetched from the result set.
	 * @var int
	const CURSOR_SCROLL = 1;
	const DBLIB_ATTR_CONNECTION_TIMEOUT = 1000;
	const DBLIB_ATTR_QUERY_TIMEOUT = 1001;
	const DBLIB_ATTR_STRINGIFY_UNIQUEIDENTIFIER = 1002;
	const DBLIB_ATTR_VERSION = 1003;
	const DBLIB_ATTR_TDS_VERSION = 1004;
	const DBLIB_ATTR_SKIP_EMPTY_ROWSETS = 1005;
	const DBLIB_ATTR_DATETIME_CONVERT = 1006;
	/**
	 * Setting MySQL unbuffered mode
	 * <pre>
	 * <code>&lt;?php
	 * $pdo = new PDO(&quot;mysql:host=localhost;dbname=world&quot;, &&#35;039;my_user&&#35;039;, &&#35;039;my_password&&#35;039;);
	 * $pdo-&gt;setAttribute(PDO::MYSQL_ATTR_USE_BUFFERED_QUERY, false);
	 * $unbufferedResult = $pdo-&gt;query(&quot;SELECT Name FROM City&quot;);
	 * foreach ($unbufferedResult as $row) {
	 * echo $row[&&#35;039;Name&&#35;039;] . PHP_EOL;
	 * }
	 * ?&gt;</code>
	 * </pre>
	 * @var bool
	const MYSQL_ATTR_USE_BUFFERED_QUERY = 1000;
	/**
	 * Enable LOAD LOCAL INFILE.
	 * <p>Note, this constant can only be used in the driver_options 
	 * array when constructing a new database handle.</p>
	 * @var int
	const MYSQL_ATTR_LOCAL_INFILE = 1001;
	/**
	 * Command to execute when connecting to the MySQL server. Will
	 * automatically be re-executed when reconnecting.
	 * <p>Note, this constant can only be used in the driver_options 
	 * array when constructing a new database handle.</p>
	 * @var string
	const MYSQL_ATTR_INIT_COMMAND = 1002;
	/**
	 * Enable network communication compression.
	 * @var int
	const MYSQL_ATTR_COMPRESS = 1003;
	/**
	 * Perform direct queries, don't use prepared statements.
	 * @var int
	const MYSQL_ATTR_DIRECT_QUERY = 1004;
	/**
	 * Return the number of found (matched) rows, not the 
	 * number of changed rows.
	 * @var int
	const MYSQL_ATTR_FOUND_ROWS = 1005;
	/**
	 * Permit spaces after function names. Makes all functions 
	 * names reserved words.
	 * @var int
	const MYSQL_ATTR_IGNORE_SPACE = 1006;
	/**
	 * The file path to the SSL key.
	 * @var int
	const MYSQL_ATTR_SSL_KEY = 1007;
	/**
	 * The file path to the SSL certificate.
	 * @var int
	const MYSQL_ATTR_SSL_CERT = 1008;
	/**
	 * The file path to the SSL certificate authority.
	 * @var int
	const MYSQL_ATTR_SSL_CA = 1009;
	/**
	 * The file path to the directory that contains the trusted SSL
	 * CA certificates, which are stored in PEM format.
	 * @var int
	const MYSQL_ATTR_SSL_CAPATH = 1010;
	/**
	 * A list of one or more permissible ciphers to use for SSL encryption, in a format
	 * understood by OpenSSL. For example: DHE-RSA-AES256-SHA:AES128-SHA
	 * @var int
	const MYSQL_ATTR_SSL_CIPHER = 1011;
	const MYSQL_ATTR_SERVER_PUBLIC_KEY = 1012;
	/**
	 * Disables multi query execution in both PDO::prepare
	 * and PDO::query when set to false.
	 * <p>Note, this constant can only be used in the driver_options 
	 * array when constructing a new database handle.</p>
	 * @var int
	const MYSQL_ATTR_MULTI_STATEMENTS = 1013;
	/**
	 * Provides a way to disable verification of the server SSL certificate.
	 * <p>This exists as of PHP 7.0.18 and PHP 7.1.4.</p>
	 * @var int
	const MYSQL_ATTR_SSL_VERIFY_SERVER_CERT = 1014;
	/**
	 * Allows restricting LOCAL DATA loading to files located in this designated 
	 * directory. Available as of PHP 8.1.0.
	 * <p>Note, this constant can only be used in the driver_options 
	 * array when constructing a new database handle.</p>
	 * @var string
	const MYSQL_ATTR_LOCAL_INFILE_DIRECTORY = 1015;
	/**
	 * This option controls whether the ODBC cursor library is used. The ODBC cursor library
	 * supports some advanced ODBC features (e.g. block scrollable cursors), which may not
	 * be implemented by the driver. The following values are supported:
	 * <p>
	 * <br>
	 * <p>
	 * PDO::ODBC_SQL_USE_IF_NEEDED (the default):
	 * use the ODBC cursor library when needed.
	 * </p>
	 * <br>
	 * <p>
	 * PDO::ODBC_SQL_USE_DRIVER:
	 * never use the ODBC cursor library.
	 * </p>
	 * <br>
	 * <p>
	 * PDO::ODBC_SQL_USE_ODBC:
	 * always use the ODBC cursor library.
	 * </p>
	 * </p>
	 * <p>PDO::ODBC_SQL_USE_IF_NEEDED (the default):
	 * use the ODBC cursor library when needed.</p>
	 * <p>PDO::ODBC_SQL_USE_DRIVER:
	 * never use the ODBC cursor library.</p>
	 * <p>PDO::ODBC_SQL_USE_ODBC:
	 * always use the ODBC cursor library.</p>
	 * @var int
	const ODBC_ATTR_USE_CURSOR_LIBRARY = 1000;
	/**
	 * Windows only. If true, UTF-16 encoded character data (CHAR,
	 * VARCHAR and LONGVARCHAR) is converted to
	 * UTF-8 when reading from or writing data to the database.
	 * If false (the default), character encoding conversion may be done by the driver.
	 * @var bool
	const ODBC_ATTR_ASSUME_UTF8 = 1001;
	const ODBC_SQL_USE_IF_NEEDED = 0;
	const ODBC_SQL_USE_DRIVER = 2;
	const ODBC_SQL_USE_ODBC = 1;
	/**
	 * Send the query and the parameters to the server together in a single
	 * call, avoiding the need to create a named prepared statement separately.
	 * If the query is only going to be executed once this can reduce latency by
	 * avoiding an unnecessary server round-trip.
	 * @var int
	const PGSQL_ATTR_DISABLE_PREPARES = 1000;
	const PGSQL_TRANSACTION_IDLE = 0;
	const PGSQL_TRANSACTION_ACTIVE = 1;
	const PGSQL_TRANSACTION_INTRANS = 2;
	const PGSQL_TRANSACTION_INERROR = 3;
	const PGSQL_TRANSACTION_UNKNOWN = 4;
	/**
	 * Specifies that a function created with PDO::sqliteCreateFunction
	 * is deterministic, i.e. it always returns the same result given the same inputs within
	 * a single SQL statement. (Available as of PHP 7.1.4.)
	 * @var int
	const SQLITE_DETERMINISTIC = 2048;
	const SQLITE_ATTR_OPEN_FLAGS = 1000;
	const SQLITE_OPEN_READONLY = 1;
	const SQLITE_OPEN_READWRITE = 2;
	const SQLITE_OPEN_CREATE = 4;
	const SQLITE_ATTR_READONLY_STATEMENT = 1001;
	const SQLITE_ATTR_EXTENDED_RESULT_CODES = 1002;
	const SQLSRV_ATTR_ENCODING = 1000;
	/**
	 * A non-negative integer representing the timeout period, in seconds. Zero (0) 
	 * is the default and means no timeout. This constant can be passed to 
	 * PDOStatement::setAttribute, PDO::setAttribute, and PDO::prepare.
	 * @var int
	const SQLSRV_ATTR_QUERY_TIMEOUT = 1001;
	/**
	 * Indicates that a query should be executed directly, without being prepared. 
	 * This constant can be passed to PDO::setAttribute, and PDO::prepare. For more 
	 * information, see 
	 * Direct and Prepared Statement Execution.
	 * @var int
	const SQLSRV_ATTR_DIRECT_QUERY = 1002;
	const SQLSRV_ATTR_CURSOR_SCROLL_TYPE = 1003;
	const SQLSRV_ATTR_CLIENT_BUFFER_MAX_KB_SIZE = 1004;
	const SQLSRV_ATTR_FETCHES_NUMERIC_TYPE = 1005;
	const SQLSRV_ATTR_FETCHES_DATETIME_TYPE = 1006;
	const SQLSRV_ATTR_FORMAT_DECIMALS = 1007;
	const SQLSRV_ATTR_DECIMAL_PLACES = 1008;
	const SQLSRV_ATTR_DATA_CLASSIFICATION = 1009;
	const SQLSRV_PARAM_OUT_DEFAULT_SIZE = -1;
	/**
	 * Specifies that data is sent/retrieved to/from the server according to 
	 * PDO::SQLSRV_ENCODING_SYSTEM if specified during connection. The connection's 
	 * encoding is used if specified in a prepare statement. This constant can be 
	 * passed to PDOStatement::setAttribute, PDO::setAttribute, PDO::prepare, 
	 * PDOStatement::bindColumn, and PDOStatement::bindParam.
	 * @var int
	const SQLSRV_ENCODING_DEFAULT = 1;
	/**
	 * Specifies that data is sent/retrieved to/from the server as 8-bit characters 
	 * as specified in the code page of the Windows locale that is set on the system. 
	 * Any multi-byte characters or characters that do not map into this code page 
	 * are substituted with a single byte question mark (?) character. This constant 
	 * can be passed to PDOStatement::setAttribute, PDO::setAttribute, PDO::prepare, 
	 * PDOStatement::bindColumn, and PDOStatement::bindParam.
	 * @var int
	const SQLSRV_ENCODING_SYSTEM = 3;
	/**
	 * Specifies that data is sent/retrieved as a raw byte stream to/from the server 
	 * without performing encoding or translation. This constant can be passed to 
	 * PDOStatement::setAttribute, PDO::prepare, PDOStatement::bindColumn, and 
	 * PDOStatement::bindParam.
	 * @var int
	const SQLSRV_ENCODING_BINARY = 2;
	/**
	 * Specifies that data is sent/retrieved to/from the server in UTF-8 encoding. 
	 * This is the default encoding. This constant can be passed to 
	 * PDOStatement::setAttribute, PDO::setAttribute, PDO::prepare, 
	 * PDOStatement::bindColumn, and PDOStatement::bindParam.
	 * @var int
	const SQLSRV_ENCODING_UTF8 = 65001;
	const SQLSRV_CURSOR_STATIC = 3;
	const SQLSRV_CURSOR_DYNAMIC = 2;
	const SQLSRV_CURSOR_KEYSET = 1;
	const SQLSRV_CURSOR_BUFFERED = 42;
	/**
	 * This constant is an acceptable value for the SQLSRV DSN key TransactionIsolation. 
	 * This constant sets the transaction isolation level for the connection to 
	 * Read Uncommitted.
	 * @var int
	const SQLSRV_TXN_READ_UNCOMMITTED = "READ_UNCOMMITTED";
	/**
	 * This constant is an acceptable value for the SQLSRV DSN key TransactionIsolation. 
	 * This constant sets the transaction isolation level for the connection to 
	 * Read Committed.
	 * @var int
	const SQLSRV_TXN_READ_COMMITTED = "READ_COMMITTED";
	/**
	 * This constant is an acceptable value for the SQLSRV DSN key TransactionIsolation. 
	 * This constant sets the transaction isolation level for the connection to 
	 * Repeateable Read.
	 * @var int
	const SQLSRV_TXN_REPEATABLE_READ = "REPEATABLE_READ";
	/**
	 * This constant is an acceptable value for the SQLSRV DSN key TransactionIsolation. 
	 * This constant sets the transaction isolation level for the connection to 
	 * Serializable.
	 * @var int
	const SQLSRV_TXN_SERIALIZABLE = "SERIALIZABLE";
	/**
	 * This constant is an acceptable value for the SQLSRV DSN key TransactionIsolation. 
	 * This constant sets the transaction isolation level for the connection to Snapshot.
	 * @var int
	const SQLSRV_TXN_SNAPSHOT = "SNAPSHOT";


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
	 * @return PDOStatement|false If the database server successfully prepares the statement,
	 * PDO::prepare returns a
	 * PDOStatement object.
	 * If the database server cannot successfully prepare the statement,
	 * PDO::prepare returns false or emits
	 * PDOException (depending on error handling).
	 * <p>Emulated prepared statements does not communicate with the database server
	 * so PDO::prepare does not check the statement.</p>
	 */
	public function prepare (string $query, array $options = '[]'): PDOStatement|false {}

	/**
	 * Prepares and executes an SQL statement without placeholders
	 * @link http://www.php.net/manual/en/pdo.query.php
	 * @param string $query 
	 * @param int|null $fetchMode [optional] 
	 * @return PDOStatement|false Returns a PDOStatement object or false on failure.
	 */
	public function query (string $query, ?int $fetchMode = null): PDOStatement|false {}

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

/**
 * Represents a prepared statement and, after the statement is executed, an 
 * associated result set.
 * @link http://www.php.net/manual/en/class.pdostatement.php
 */
class PDOStatement implements IteratorAggregate, Traversable {

	/**
	 * Used query string.
	 * @var string
	 * @link http://www.php.net/manual/en/class.pdostatement.php#pdostatement.props.querystring
	 */
	public string $queryString;

	/**
	 * Bind a column to a PHP variable
	 * @link http://www.php.net/manual/en/pdostatement.bindcolumn.php
	 * @param string|int $column 
	 * @param mixed $var 
	 * @param int $type [optional] 
	 * @param int $maxLength [optional] 
	 * @param mixed $driverOptions [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function bindColumn (string|int $column, mixed &$var, int $type = \PDO::PARAM_STR, int $maxLength = null, mixed $driverOptions = null): bool {}

	/**
	 * Binds a parameter to the specified variable name
	 * @link http://www.php.net/manual/en/pdostatement.bindparam.php
	 * @param string|int $param 
	 * @param mixed $var 
	 * @param int $type [optional] 
	 * @param int $maxLength [optional] 
	 * @param mixed $driverOptions [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function bindParam (string|int $param, mixed &$var, int $type = \PDO::PARAM_STR, int $maxLength = null, mixed $driverOptions = null): bool {}

	/**
	 * Binds a value to a parameter
	 * @link http://www.php.net/manual/en/pdostatement.bindvalue.php
	 * @param string|int $param 
	 * @param mixed $value 
	 * @param int $type [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function bindValue (string|int $param, mixed $value, int $type = \PDO::PARAM_STR): bool {}

	/**
	 * Closes the cursor, enabling the statement to be executed again
	 * @link http://www.php.net/manual/en/pdostatement.closecursor.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function closeCursor (): bool {}

	/**
	 * Returns the number of columns in the result set
	 * @link http://www.php.net/manual/en/pdostatement.columncount.php
	 * @return int Returns the number of columns in the result set represented by the
	 * PDOStatement object, even if the result set is empty. If there is no result set,
	 * PDOStatement::columnCount returns 0.
	 */
	public function columnCount (): int {}

	/**
	 * Dump an SQL prepared command
	 * @link http://www.php.net/manual/en/pdostatement.debugdumpparams.php
	 * @return bool|null Returns null, or false in case of an error.
	 */
	public function debugDumpParams (): ?bool {}

	/**
	 * Fetch the SQLSTATE associated with the last operation on the statement handle
	 * @link http://www.php.net/manual/en/pdostatement.errorcode.php
	 * @return string|null Identical to PDO::errorCode, except that 
	 * PDOStatement::errorCode only retrieves error codes
	 * for operations performed with PDOStatement objects.
	 */
	public function errorCode (): ?string {}

	/**
	 * Fetch extended error information associated with the last operation on the statement handle
	 * @link http://www.php.net/manual/en/pdostatement.errorinfo.php
	 * @return array PDOStatement::errorInfo returns an array of
	 * error information about the last operation performed by this
	 * statement handle. The array consists of at least the following fields:
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
	 * <td>Driver specific error code.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>2</td>
	 * <td>Driver specific error message.</td>
	 * </tr>
	 * </table>
	 */
	public function errorInfo (): array {}

	/**
	 * Executes a prepared statement
	 * @link http://www.php.net/manual/en/pdostatement.execute.php
	 * @param array|null $params [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function execute (?array $params = null): bool {}

	/**
	 * Fetches the next row from a result set
	 * @link http://www.php.net/manual/en/pdostatement.fetch.php
	 * @param int $mode [optional] 
	 * @param int $cursorOrientation [optional] 
	 * @param int $cursorOffset [optional] 
	 * @return mixed The return value of this function on success depends on the fetch type. In
	 * all cases, false is returned on failure or if there are no more rows.
	 */
	public function fetch (int $mode = \PDO::FETCH_DEFAULT, int $cursorOrientation = \PDO::FETCH_ORI_NEXT, int $cursorOffset = null): mixed {}

	/**
	 * Fetches the remaining rows from a result set
	 * @link http://www.php.net/manual/en/pdostatement.fetchall.php
	 * @param int $mode [optional] 
	 * @return array PDOStatement::fetchAll returns an array containing
	 * all of the remaining rows in the result set. The array represents each
	 * row as either an array of column values or an object with properties
	 * corresponding to each column name. An empty array is returned if there
	 * are zero results to fetch.
	 * <p>Using this method to fetch large result sets will result in a heavy
	 * demand on system and possibly network resources. Rather than retrieving
	 * all of the data and manipulating it in PHP, consider using the database
	 * server to manipulate the result sets. For example, use the WHERE and
	 * ORDER BY clauses in SQL to restrict results before retrieving and
	 * processing them with PHP.</p>
	 */
	public function fetchAll (int $mode = \PDO::FETCH_DEFAULT): array {}

	/**
	 * Returns a single column from the next row of a result set
	 * @link http://www.php.net/manual/en/pdostatement.fetchcolumn.php
	 * @param int $column [optional] 
	 * @return mixed PDOStatement::fetchColumn returns a single column
	 * from the next row of a result set or false if there are no more rows.
	 * <p>There is no way to return another column from the same row if you
	 * use PDOStatement::fetchColumn to retrieve data.</p>
	 */
	public function fetchColumn (int $column = null): mixed {}

	/**
	 * Fetches the next row and returns it as an object
	 * @link http://www.php.net/manual/en/pdostatement.fetchobject.php
	 * @param string|null $class [optional] 
	 * @param array $constructorArgs [optional] 
	 * @return object|false Returns an instance of the required class with property names that
	 * correspond to the column names or false on failure.
	 */
	public function fetchObject (?string $class = '"stdClass"', array $constructorArgs = '[]'): object|false {}

	/**
	 * Retrieve a statement attribute
	 * @link http://www.php.net/manual/en/pdostatement.getattribute.php
	 * @param int $name 
	 * @return mixed Returns the attribute value.
	 */
	public function getAttribute (int $name): mixed {}

	/**
	 * Returns metadata for a column in a result set
	 * @link http://www.php.net/manual/en/pdostatement.getcolumnmeta.php
	 * @param int $column 
	 * @return array|false Returns an associative array containing the following values representing
	 * the metadata for a single column:
	 * <p>Returns false if the requested column does not exist in the result set,
	 * or if no result set exists.</p>
	 */
	public function getColumnMeta (int $column): array|false {}

	/**
	 * Advances to the next rowset in a multi-rowset statement handle
	 * @link http://www.php.net/manual/en/pdostatement.nextrowset.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function nextRowset (): bool {}

	/**
	 * Returns the number of rows affected by the last SQL statement
	 * @link http://www.php.net/manual/en/pdostatement.rowcount.php
	 * @return int Returns the number of rows.
	 */
	public function rowCount (): int {}

	/**
	 * Set a statement attribute
	 * @link http://www.php.net/manual/en/pdostatement.setattribute.php
	 * @param int $attribute 
	 * @param mixed $value 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setAttribute (int $attribute, mixed $value): bool {}

	/**
	 * Set the default fetch mode for this statement
	 * @link http://www.php.net/manual/en/pdostatement.setfetchmode.php
	 * @param int $mode 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setFetchMode (int $mode): bool {}

	/**
	 * Gets result set iterator
	 * @link http://www.php.net/manual/en/pdostatement.getiterator.php
	 * @return Iterator 
	 */
	public function getIterator (): Iterator {}

}

final class PDORow  {

	public string $queryString;
}

/**
 * Return an array of available PDO drivers
 * @link http://www.php.net/manual/en/pdo.getavailabledrivers.php
 * @return array PDO::getAvailableDrivers returns an array of PDO driver names. If
 * no drivers are available, it returns an empty array.
 */
function pdo_drivers (): array {}

// End of PDO v.8.2.6
