<?php

// Start of PDO v.8.3.0

class PDOException extends RuntimeException implements Stringable, Throwable {

	protected $code;

	public ?array $errorInfo;

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class PDO  {
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
	const SQLITE_DETERMINISTIC = 2048;
	const SQLITE_ATTR_OPEN_FLAGS = 1000;
	const SQLITE_OPEN_READONLY = 1;
	const SQLITE_OPEN_READWRITE = 2;
	const SQLITE_OPEN_CREATE = 4;
	const SQLITE_ATTR_READONLY_STATEMENT = 1001;
	const SQLITE_ATTR_EXTENDED_RESULT_CODES = 1002;
	const SQLSRV_ATTR_ENCODING = 1000;
	const SQLSRV_ATTR_QUERY_TIMEOUT = 1001;
	const SQLSRV_ATTR_DIRECT_QUERY = 1002;
	const SQLSRV_ATTR_CURSOR_SCROLL_TYPE = 1003;
	const SQLSRV_ATTR_CLIENT_BUFFER_MAX_KB_SIZE = 1004;
	const SQLSRV_ATTR_FETCHES_NUMERIC_TYPE = 1005;
	const SQLSRV_ATTR_FETCHES_DATETIME_TYPE = 1006;
	const SQLSRV_ATTR_FORMAT_DECIMALS = 1007;
	const SQLSRV_ATTR_DECIMAL_PLACES = 1008;
	const SQLSRV_ATTR_DATA_CLASSIFICATION = 1009;
	const SQLSRV_PARAM_OUT_DEFAULT_SIZE = -1;
	const SQLSRV_ENCODING_DEFAULT = 1;
	const SQLSRV_ENCODING_SYSTEM = 3;
	const SQLSRV_ENCODING_BINARY = 2;
	const SQLSRV_ENCODING_UTF8 = 65001;
	const SQLSRV_CURSOR_STATIC = 3;
	const SQLSRV_CURSOR_DYNAMIC = 2;
	const SQLSRV_CURSOR_KEYSET = 1;
	const SQLSRV_CURSOR_BUFFERED = 42;
	const SQLSRV_TXN_READ_UNCOMMITTED = "READ_UNCOMMITTED";
	const SQLSRV_TXN_READ_COMMITTED = "READ_COMMITTED";
	const SQLSRV_TXN_REPEATABLE_READ = "REPEATABLE_READ";
	const SQLSRV_TXN_SERIALIZABLE = "SERIALIZABLE";
	const SQLSRV_TXN_SNAPSHOT = "SNAPSHOT";


	/**
	 * {@inheritdoc}
	 * @param string $dsn
	 * @param string|null $username [optional]
	 * @param string|null $password [optional]
	 * @param array|null $options [optional]
	 */
	public function __construct (string $dsn, ?string $username = NULL, ?string $password = NULL, ?array $options = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function beginTransaction () {}

	/**
	 * {@inheritdoc}
	 */
	public function commit () {}

	/**
	 * {@inheritdoc}
	 */
	public function errorCode () {}

	/**
	 * {@inheritdoc}
	 */
	public function errorInfo () {}

	/**
	 * {@inheritdoc}
	 * @param string $statement
	 */
	public function exec (string $statement) {}

	/**
	 * {@inheritdoc}
	 * @param int $attribute
	 */
	public function getAttribute (int $attribute) {}

	/**
	 * {@inheritdoc}
	 */
	public static function getAvailableDrivers () {}

	/**
	 * {@inheritdoc}
	 */
	public function inTransaction () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $name [optional]
	 */
	public function lastInsertId (?string $name = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $query
	 * @param array $options [optional]
	 */
	public function prepare (string $query, array $options = array (
)) {}

	/**
	 * {@inheritdoc}
	 * @param string $query
	 * @param int|null $fetchMode [optional]
	 * @param mixed $fetchModeArgs [optional]
	 */
	public function query (string $query, ?int $fetchMode = NULL, mixed ...$fetchModeArgs) {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 * @param int $type [optional]
	 */
	public function quote (string $string, int $type = 2) {}

	/**
	 * {@inheritdoc}
	 */
	public function rollBack () {}

	/**
	 * {@inheritdoc}
	 * @param int $attribute
	 * @param mixed $value
	 */
	public function setAttribute (int $attribute, mixed $value = null) {}

}

class PDOStatement implements IteratorAggregate, Traversable {

	public string $queryString;

	/**
	 * {@inheritdoc}
	 * @param string|int $column
	 * @param mixed $var
	 * @param int $type [optional]
	 * @param int $maxLength [optional]
	 * @param mixed $driverOptions [optional]
	 */
	public function bindColumn (string|int $column, mixed &$var = null, int $type = 2, int $maxLength = 0, mixed $driverOptions = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $param
	 * @param mixed $var
	 * @param int $type [optional]
	 * @param int $maxLength [optional]
	 * @param mixed $driverOptions [optional]
	 */
	public function bindParam (string|int $param, mixed &$var = null, int $type = 2, int $maxLength = 0, mixed $driverOptions = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $param
	 * @param mixed $value
	 * @param int $type [optional]
	 */
	public function bindValue (string|int $param, mixed $value = null, int $type = 2) {}

	/**
	 * {@inheritdoc}
	 */
	public function closeCursor () {}

	/**
	 * {@inheritdoc}
	 */
	public function columnCount () {}

	/**
	 * {@inheritdoc}
	 */
	public function debugDumpParams () {}

	/**
	 * {@inheritdoc}
	 */
	public function errorCode () {}

	/**
	 * {@inheritdoc}
	 */
	public function errorInfo () {}

	/**
	 * {@inheritdoc}
	 * @param array|null $params [optional]
	 */
	public function execute (?array $params = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $mode [optional]
	 * @param int $cursorOrientation [optional]
	 * @param int $cursorOffset [optional]
	 */
	public function fetch (int $mode = 0, int $cursorOrientation = 0, int $cursorOffset = 0) {}

	/**
	 * {@inheritdoc}
	 * @param int $mode [optional]
	 * @param mixed $args [optional]
	 */
	public function fetchAll (int $mode = 0, mixed ...$args) {}

	/**
	 * {@inheritdoc}
	 * @param int $column [optional]
	 */
	public function fetchColumn (int $column = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 * @param array $constructorArgs [optional]
	 */
	public function fetchObject (?string $class = 'stdClass', array $constructorArgs = array (
)) {}

	/**
	 * {@inheritdoc}
	 * @param int $name
	 */
	public function getAttribute (int $name) {}

	/**
	 * {@inheritdoc}
	 * @param int $column
	 */
	public function getColumnMeta (int $column) {}

	/**
	 * {@inheritdoc}
	 */
	public function nextRowset () {}

	/**
	 * {@inheritdoc}
	 */
	public function rowCount () {}

	/**
	 * {@inheritdoc}
	 * @param int $attribute
	 * @param mixed $value
	 */
	public function setAttribute (int $attribute, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param int $mode
	 * @param mixed $args [optional]
	 */
	public function setFetchMode (int $mode, mixed ...$args) {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): Iterator {}

}

final class PDORow  {

	public string $queryString;
}

/**
 * {@inheritdoc}
 */
function pdo_drivers (): array {}

// End of PDO v.8.3.0
