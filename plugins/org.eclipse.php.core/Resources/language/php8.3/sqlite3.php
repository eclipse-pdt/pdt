<?php

// Start of sqlite3 v.8.3.0

class SQLite3Exception extends Exception implements Throwable, Stringable {

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
	 * {@inheritdoc}
	 * @param string $filename
	 * @param int $flags [optional]
	 * @param string $encryptionKey [optional]
	 */
	public function __construct (string $filename, int $flags = 6, string $encryptionKey = '') {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param int $flags [optional]
	 * @param string $encryptionKey [optional]
	 */
	public function open (string $filename, int $flags = 6, string $encryptionKey = '') {}

	/**
	 * {@inheritdoc}
	 */
	public function close () {}

	/**
	 * {@inheritdoc}
	 */
	public static function version () {}

	/**
	 * {@inheritdoc}
	 */
	public function lastInsertRowID () {}

	/**
	 * {@inheritdoc}
	 */
	public function lastErrorCode () {}

	/**
	 * {@inheritdoc}
	 */
	public function lastExtendedErrorCode () {}

	/**
	 * {@inheritdoc}
	 */
	public function lastErrorMsg () {}

	/**
	 * {@inheritdoc}
	 */
	public function changes () {}

	/**
	 * {@inheritdoc}
	 * @param int $milliseconds
	 */
	public function busyTimeout (int $milliseconds) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function loadExtension (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param SQLite3 $destination
	 * @param string $sourceDatabase [optional]
	 * @param string $destinationDatabase [optional]
	 */
	public function backup (SQLite3 $destination, string $sourceDatabase = 'main', string $destinationDatabase = 'main') {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 */
	public static function escapeString (string $string) {}

	/**
	 * {@inheritdoc}
	 * @param string $query
	 */
	public function prepare (string $query) {}

	/**
	 * {@inheritdoc}
	 * @param string $query
	 */
	public function exec (string $query) {}

	/**
	 * {@inheritdoc}
	 * @param string $query
	 */
	public function query (string $query) {}

	/**
	 * {@inheritdoc}
	 * @param string $query
	 * @param bool $entireRow [optional]
	 */
	public function querySingle (string $query, bool $entireRow = false) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param callable $callback
	 * @param int $argCount [optional]
	 * @param int $flags [optional]
	 */
	public function createFunction (string $name, callable $callback, int $argCount = -1, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param callable $stepCallback
	 * @param callable $finalCallback
	 * @param int $argCount [optional]
	 */
	public function createAggregate (string $name, callable $stepCallback, callable $finalCallback, int $argCount = -1) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param callable $callback
	 */
	public function createCollation (string $name, callable $callback) {}

	/**
	 * {@inheritdoc}
	 * @param string $table
	 * @param string $column
	 * @param int $rowid
	 * @param string $database [optional]
	 * @param int $flags [optional]
	 */
	public function openBlob (string $table, string $column, int $rowid, string $database = 'main', int $flags = 1) {}

	/**
	 * {@inheritdoc}
	 * @param bool $enable [optional]
	 */
	public function enableExceptions (bool $enable = false) {}

	/**
	 * {@inheritdoc}
	 * @param bool $enable [optional]
	 */
	public function enableExtendedResultCodes (bool $enable = true) {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $callback
	 */
	public function setAuthorizer (?callable $callback = null) {}

}

class SQLite3Stmt  {

	/**
	 * {@inheritdoc}
	 * @param SQLite3 $sqlite3
	 * @param string $query
	 */
	private function __construct (SQLite3 $sqlite3, string $query) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $param
	 * @param mixed $var
	 * @param int $type [optional]
	 */
	public function bindParam (string|int $param, mixed &$var = null, int $type = 3) {}

	/**
	 * {@inheritdoc}
	 * @param string|int $param
	 * @param mixed $value
	 * @param int $type [optional]
	 */
	public function bindValue (string|int $param, mixed $value = null, int $type = 3) {}

	/**
	 * {@inheritdoc}
	 */
	public function clear () {}

	/**
	 * {@inheritdoc}
	 */
	public function close () {}

	/**
	 * {@inheritdoc}
	 */
	public function execute () {}

	/**
	 * {@inheritdoc}
	 * @param bool $expand [optional]
	 */
	public function getSQL (bool $expand = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function paramCount () {}

	/**
	 * {@inheritdoc}
	 */
	public function readOnly () {}

	/**
	 * {@inheritdoc}
	 */
	public function reset () {}

}

class SQLite3Result  {

	/**
	 * {@inheritdoc}
	 */
	private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function numColumns () {}

	/**
	 * {@inheritdoc}
	 * @param int $column
	 */
	public function columnName (int $column) {}

	/**
	 * {@inheritdoc}
	 * @param int $column
	 */
	public function columnType (int $column) {}

	/**
	 * {@inheritdoc}
	 * @param int $mode [optional]
	 */
	public function fetchArray (int $mode = 3) {}

	/**
	 * {@inheritdoc}
	 */
	public function reset () {}

	/**
	 * {@inheritdoc}
	 */
	public function finalize () {}

}
define ('SQLITE3_ASSOC', 1);
define ('SQLITE3_NUM', 2);
define ('SQLITE3_BOTH', 3);
define ('SQLITE3_INTEGER', 1);
define ('SQLITE3_FLOAT', 2);
define ('SQLITE3_TEXT', 3);
define ('SQLITE3_BLOB', 4);
define ('SQLITE3_NULL', 5);
define ('SQLITE3_OPEN_READONLY', 1);
define ('SQLITE3_OPEN_READWRITE', 2);
define ('SQLITE3_OPEN_CREATE', 4);
define ('SQLITE3_DETERMINISTIC', 2048);

// End of sqlite3 v.8.3.0
