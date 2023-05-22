<?php

// Start of mysqli v.8.2.6

/**
 * The mysqli exception handling class.
 * @link http://www.php.net/manual/en/class.mysqli_sql_exception.php
 */
final class mysqli_sql_exception extends RuntimeException implements Stringable, Throwable {

	/**
	 * The sql state with the error.
	 * @var string
	 * @link http://www.php.net/manual/en/class.mysqli_sql_exception.php#mysqli_sql_exception.props.sqlstate
	 */
	protected string $sqlstate;

	/**
	 * Returns the SQLSTATE error code
	 * @link http://www.php.net/manual/en/mysqli-sql-exception.getsqlstate.php
	 * @return string Returns a string containing the SQLSTATE error code for the last error. The error code consists of five characters.
	 */
	public function getSqlState (): string {}

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
 * The mysqli_driver class is an instance of the monostate
 * pattern, i.e. there is only one driver which can be accessed though an arbitrary
 * amount of mysqli_driver instances.
 * @link http://www.php.net/manual/en/class.mysqli_driver.php
 */
final class mysqli_driver  {

	/**
	 * The Client API header version
	 * @var string
	 * @link http://www.php.net/manual/en/class.mysqli_driver.php#mysqli_driver.props.client_info
	 */
	public readonly string $client_info;

	/**
	 * The Client version
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli_driver.php#mysqli_driver.props.client_version
	 */
	public readonly int $client_version;

	/**
	 * The MySQLi Driver version
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli_driver.php#mysqli_driver.props.driver_version
	 */
	public readonly int $driver_version;

	/**
	 * Sets mysqli error reporting mode
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli_driver.php#mysqli_driver.props.report_mode
	 */
	public int $report_mode;
}

/**
 * Represents a connection between PHP and a MySQL database.
 * @link http://www.php.net/manual/en/class.mysqli.php
 */
class mysqli  {

	/**
	 * Gets the number of affected rows in a previous MySQL operation
	 * @var int|string
	 * @link http://www.php.net/manual/en/class.mysqli.php#mysqli.props.affected_rows
	 */
	public int|string $affected_rows;

	/**
	 * Get MySQL client info
	 * @var string
	 * @link http://www.php.net/manual/en/class.mysqli.php#mysqli.props.client_info
	 */
	public string $client_info;

	/**
	 * Returns the MySQL client version as an integer
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli.php#mysqli.props.client_version
	 */
	public int $client_version;

	/**
	 * Returns the error code from last connect call
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli.php#mysqli.props.connect_errno
	 */
	public int $connect_errno;

	/**
	 * Returns a description of the last connection error
	 * @var string|null
	 * @link http://www.php.net/manual/en/class.mysqli.php#mysqli.props.connect_error
	 */
	public ?string $connect_error;

	/**
	 * Returns the error code for the most recent function call
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli.php#mysqli.props.errno
	 */
	public int $errno;

	/**
	 * Returns a string description of the last error
	 * @var string
	 * @link http://www.php.net/manual/en/class.mysqli.php#mysqli.props.error
	 */
	public string $error;

	/**
	 * Returns a list of errors from the last command executed
	 * @var array
	 * @link http://www.php.net/manual/en/class.mysqli.php#mysqli.props.error_list
	 */
	public array $error_list;

	/**
	 * Returns the number of columns for the most recent query
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli.php#mysqli.props.field_count
	 */
	public int $field_count;

	/**
	 * Returns a string representing the type of connection used
	 * @var string
	 * @link http://www.php.net/manual/en/class.mysqli.php#mysqli.props.host_info
	 */
	public string $host_info;

	/**
	 * Retrieves information about the most recently executed query
	 * @var string|null
	 * @link http://www.php.net/manual/en/class.mysqli.php#mysqli.props.info
	 */
	public ?string $info;

	/**
	 * Returns the value generated for an AUTO_INCREMENT column by the last query
	 * @var int|string
	 * @link http://www.php.net/manual/en/class.mysqli.php#mysqli.props.insert_id
	 */
	public int|string $insert_id;

	/**
	 * Returns the version of the MySQL server
	 * @var string
	 * @link http://www.php.net/manual/en/class.mysqli.php#mysqli.props.server_info
	 */
	public string $server_info;

	/**
	 * Returns the version of the MySQL server as an integer
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli.php#mysqli.props.server_version
	 */
	public int $server_version;

	/**
	 * Returns the SQLSTATE error from previous MySQL operation
	 * @var string
	 * @link http://www.php.net/manual/en/class.mysqli.php#mysqli.props.sqlstate
	 */
	public string $sqlstate;

	/**
	 * Returns the version of the MySQL protocol used
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli.php#mysqli.props.protocol_version
	 */
	public int $protocol_version;

	/**
	 * Returns the thread ID for the current connection
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli.php#mysqli.props.thread_id
	 */
	public int $thread_id;

	/**
	 * Returns the number of warnings from the last query for the given link
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli.php#mysqli.props.warning_count
	 */
	public int $warning_count;

	/**
	 * Open a new connection to the MySQL server
	 * @link http://www.php.net/manual/en/mysqli.construct.php
	 * @param string|null $hostname [optional] 
	 * @param string|null $username [optional] 
	 * @param string|null $password [optional] 
	 * @param string|null $database [optional] 
	 * @param int|null $port [optional] 
	 * @param string|null $socket [optional] 
	 * @return bool mysqli::__construct always returns an object which represents the connection to a MySQL Server, 
	 * regardless of it being successful or not.
	 * <p>mysqli_connect returns an object which represents the connection to a MySQL Server,
	 * or false on failure.</p>
	 * <p>mysqli::connect returns null on success or false on failure.</p>
	 */
	public function __construct (?string $hostname = null, ?string $username = null, ?string $password = null, ?string $database = null, ?int $port = null, ?string $socket = null): bool {}

	/**
	 * Turns on or off auto-committing database modifications
	 * @link http://www.php.net/manual/en/mysqli.autocommit.php
	 * @param bool $enable 
	 * @return bool Returns true on success or false on failure.
	 */
	public function autocommit (bool $enable): bool {}

	/**
	 * Starts a transaction
	 * @link http://www.php.net/manual/en/mysqli.begin-transaction.php
	 * @param int $flags [optional] 
	 * @param string|null $name [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function begin_transaction (int $flags = null, ?string $name = null): bool {}

	/**
	 * Changes the user of the specified database connection
	 * @link http://www.php.net/manual/en/mysqli.change-user.php
	 * @param string $username 
	 * @param string $password 
	 * @param string|null $database 
	 * @return bool Returns true on success or false on failure.
	 */
	public function change_user (string $username, string $password, ?string $database): bool {}

	/**
	 * Returns the current character set of the database connection
	 * @link http://www.php.net/manual/en/mysqli.character-set-name.php
	 * @return string The current character set of the connection
	 */
	public function character_set_name (): string {}

	/**
	 * Closes a previously opened database connection
	 * @link http://www.php.net/manual/en/mysqli.close.php
	 * @return true Always returns true.
	 */
	public function close (): true {}

	/**
	 * Commits the current transaction
	 * @link http://www.php.net/manual/en/mysqli.commit.php
	 * @param int $flags [optional] 
	 * @param string|null $name [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function commit (int $flags = null, ?string $name = null): bool {}

	/**
	 * Open a new connection to the MySQL server
	 * @link http://www.php.net/manual/en/mysqli.construct.php
	 * @param string|null $hostname [optional] 
	 * @param string|null $username [optional] 
	 * @param string|null $password [optional] 
	 * @param string|null $database [optional] 
	 * @param int|null $port [optional] 
	 * @param string|null $socket [optional] 
	 * @return bool mysqli::__construct always returns an object which represents the connection to a MySQL Server, 
	 * regardless of it being successful or not.
	 * <p>mysqli_connect returns an object which represents the connection to a MySQL Server,
	 * or false on failure.</p>
	 * <p>mysqli::connect returns null on success or false on failure.</p>
	 */
	public function connect (?string $hostname = null, ?string $username = null, ?string $password = null, ?string $database = null, ?int $port = null, ?string $socket = null): bool {}

	/**
	 * Dump debugging information into the log
	 * @link http://www.php.net/manual/en/mysqli.dump-debug-info.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function dump_debug_info (): bool {}

	/**
	 * Performs debugging operations
	 * @link http://www.php.net/manual/en/mysqli.debug.php
	 * @param string $options 
	 * @return true Always returns true.
	 */
	public function debug (string $options): true {}

	/**
	 * Returns a character set object
	 * @link http://www.php.net/manual/en/mysqli.get-charset.php
	 * @return object|null The function returns a character set object with the following properties:
	 * <p>
	 * charset
	 * <br><p>Character set name</p>
	 * collation
	 * <br><p>Collation name</p>
	 * dir
	 * <br><p>Directory the charset description was fetched from (?) or "" for built-in character sets</p>
	 * min_length
	 * <br><p>Minimum character length in bytes</p>
	 * max_length
	 * <br><p>Maximum character length in bytes</p>
	 * number
	 * <br><p>Internal character set number</p>
	 * state
	 * <br><p>Character set status (?)</p>
	 * </p>
	 * <p>Character set name</p>
	 * <p>Collation name</p>
	 * <p>Directory the charset description was fetched from (?) or "" for built-in character sets</p>
	 * <p>Minimum character length in bytes</p>
	 * <p>Maximum character length in bytes</p>
	 * <p>Internal character set number</p>
	 * <p>Character set status (?)</p>
	 */
	public function get_charset (): ?object {}

	/**
	 * Prepares, binds parameters, and executes SQL statement
	 * @link http://www.php.net/manual/en/mysqli.execute-query.php
	 * @param string $query 
	 * @param array|null $params [optional] 
	 * @return mysqli_result|bool Returns false on failure. For successful queries which produce a result
	 * set, such as SELECT, SHOW, DESCRIBE or
	 * EXPLAIN, returns
	 * a mysqli_result object. For other successful queries,
	 * returns true.
	 */
	public function execute_query (string $query, ?array $params = null): mysqli_result|bool {}

	/**
	 * Get MySQL client info
	 * @link http://www.php.net/manual/en/mysqli.get-client-info.php
	 * @return string A string that represents the MySQL client library version.
	 * @deprecated 
	 */
	public function get_client_info (): string {}

	/**
	 * Returns statistics about the client connection
	 * @link http://www.php.net/manual/en/mysqli.get-connection-stats.php
	 * @return array Returns an array with connection stats.
	 */
	public function get_connection_stats (): array {}

	/**
	 * Returns the version of the MySQL server
	 * @link http://www.php.net/manual/en/mysqli.get-server-info.php
	 * @return string A character string representing the server version.
	 */
	public function get_server_info (): string {}

	/**
	 * Get result of SHOW WARNINGS
	 * @link http://www.php.net/manual/en/mysqli.get-warnings.php
	 * @return mysqli_warning|false 
	 */
	public function get_warnings (): mysqli_warning|false {}

	/**
	 * Initializes MySQLi and returns an object for use with mysqli_real_connect()
	 * @link http://www.php.net/manual/en/mysqli.init.php
	 * @return bool|null mysqli::init returns null on success, or false on failure.
	 * mysqli_init returns an object on success, or false on failure.
	 * @deprecated 
	 */
	public function init (): ?bool {}

	/**
	 * Asks the server to kill a MySQL thread
	 * @link http://www.php.net/manual/en/mysqli.kill.php
	 * @param int $process_id 
	 * @return bool Returns true on success or false on failure.
	 */
	public function kill (int $process_id): bool {}

	/**
	 * Performs one or more queries on the database
	 * @link http://www.php.net/manual/en/mysqli.multi-query.php
	 * @param string $query 
	 * @return bool Returns false if the first statement failed.
	 * To retrieve subsequent errors from other statements you have to call
	 * mysqli_next_result first.
	 */
	public function multi_query (string $query): bool {}

	/**
	 * Check if there are any more query results from a multi query
	 * @link http://www.php.net/manual/en/mysqli.more-results.php
	 * @return bool Returns true if one or more result sets (including errors) are available from a previous call to
	 * mysqli_multi_query, otherwise false.
	 */
	public function more_results (): bool {}

	/**
	 * Prepare next result from multi_query
	 * @link http://www.php.net/manual/en/mysqli.next-result.php
	 * @return bool Returns true on success or false on failure. Also returns false if the next statement resulted in an error, unlike mysqli_more_results.
	 */
	public function next_result (): bool {}

	/**
	 * Pings a server connection, or tries to reconnect if the connection has gone down
	 * @link http://www.php.net/manual/en/mysqli.ping.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function ping (): bool {}

	/**
	 * Poll connections
	 * @link http://www.php.net/manual/en/mysqli.poll.php
	 * @param array|null $read 
	 * @param array|null $error 
	 * @param array $reject 
	 * @param int $seconds 
	 * @param int $microseconds [optional] 
	 * @return int|false Returns number of ready connections upon success, false otherwise.
	 */
	public static function poll (?array &$read, ?array &$error, array &$reject, int $seconds, int $microseconds = null): int|false {}

	/**
	 * Prepares an SQL statement for execution
	 * @link http://www.php.net/manual/en/mysqli.prepare.php
	 * @param string $query 
	 * @return mysqli_stmt|false mysqli_prepare returns a statement object or false if an error occurred.
	 */
	public function prepare (string $query): mysqli_stmt|false {}

	/**
	 * Performs a query on the database
	 * @link http://www.php.net/manual/en/mysqli.query.php
	 * @param string $query 
	 * @param int $result_mode [optional] 
	 * @return mysqli_result|bool Returns false on failure. For successful queries which produce a result
	 * set, such as SELECT, SHOW, DESCRIBE or
	 * EXPLAIN, mysqli_query will return
	 * a mysqli_result object. For other successful queries,
	 * mysqli_query will
	 * return true.
	 */
	public function query (string $query, int $result_mode = MYSQLI_STORE_RESULT): mysqli_result|bool {}

	/**
	 * Opens a connection to a mysql server
	 * @link http://www.php.net/manual/en/mysqli.real-connect.php
	 * @param string|null $hostname [optional] 
	 * @param string|null $username [optional] 
	 * @param string|null $password [optional] 
	 * @param string|null $database [optional] 
	 * @param int|null $port [optional] 
	 * @param string|null $socket [optional] 
	 * @param int $flags [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function real_connect (?string $hostname = null, ?string $username = null, ?string $password = null, ?string $database = null, ?int $port = null, ?string $socket = null, int $flags = null): bool {}

	/**
	 * Escapes special characters in a string for use in an SQL statement, taking into account the current charset of the connection
	 * @link http://www.php.net/manual/en/mysqli.real-escape-string.php
	 * @param string $string 
	 * @return string Returns an escaped string.
	 */
	public function real_escape_string (string $string): string {}

	/**
	 * Get result from async query
	 * @link http://www.php.net/manual/en/mysqli.reap-async-query.php
	 * @return mysqli_result|bool Returns false on failure. For successful queries which produce a result set, such as SELECT, SHOW, DESCRIBE or
	 * EXPLAIN, mysqli_reap_async_query will return
	 * a mysqli_result object. For other successful queries, mysqli_reap_async_query will
	 * return true.
	 */
	public function reap_async_query (): mysqli_result|bool {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 */
	public function escape_string (string $string) {}

	/**
	 * Execute an SQL query
	 * @link http://www.php.net/manual/en/mysqli.real-query.php
	 * @param string $query 
	 * @return bool Returns true on success or false on failure.
	 */
	public function real_query (string $query): bool {}

	/**
	 * Removes the named savepoint from the set of savepoints of the current transaction
	 * @link http://www.php.net/manual/en/mysqli.release-savepoint.php
	 * @param string $name 
	 * @return bool Returns true on success or false on failure.
	 */
	public function release_savepoint (string $name): bool {}

	/**
	 * Rolls back current transaction
	 * @link http://www.php.net/manual/en/mysqli.rollback.php
	 * @param int $flags [optional] 
	 * @param string|null $name [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function rollback (int $flags = null, ?string $name = null): bool {}

	/**
	 * Set a named transaction savepoint
	 * @link http://www.php.net/manual/en/mysqli.savepoint.php
	 * @param string $name 
	 * @return bool Returns true on success or false on failure.
	 */
	public function savepoint (string $name): bool {}

	/**
	 * Selects the default database for database queries
	 * @link http://www.php.net/manual/en/mysqli.select-db.php
	 * @param string $database 
	 * @return bool Returns true on success or false on failure.
	 */
	public function select_db (string $database): bool {}

	/**
	 * Sets the client character set
	 * @link http://www.php.net/manual/en/mysqli.set-charset.php
	 * @param string $charset 
	 * @return bool Returns true on success or false on failure.
	 */
	public function set_charset (string $charset): bool {}

	/**
	 * Set options
	 * @link http://www.php.net/manual/en/mysqli.options.php
	 * @param int $option 
	 * @param string|int $value 
	 * @return bool Returns true on success or false on failure.
	 */
	public function options (int $option, string|int $value): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 * @param mixed $value
	 */
	public function set_opt (int $option, $value = null) {}

	/**
	 * Used for establishing secure connections using SSL
	 * @link http://www.php.net/manual/en/mysqli.ssl-set.php
	 * @param string|null $key 
	 * @param string|null $certificate 
	 * @param string|null $ca_certificate 
	 * @param string|null $ca_path 
	 * @param string|null $cipher_algos 
	 * @return true Always returns true. If SSL setup is
	 * incorrect mysqli_real_connect will return an error
	 * when you attempt to connect.
	 */
	public function ssl_set (?string $key, ?string $certificate, ?string $ca_certificate, ?string $ca_path, ?string $cipher_algos): true {}

	/**
	 * Gets the current system status
	 * @link http://www.php.net/manual/en/mysqli.stat.php
	 * @return string|false A string describing the server status. false if an error occurred.
	 */
	public function stat (): string|false {}

	/**
	 * Initializes a statement and returns an object for use with mysqli_stmt_prepare
	 * @link http://www.php.net/manual/en/mysqli.stmt-init.php
	 * @return mysqli_stmt|false Returns an object.
	 */
	public function stmt_init (): mysqli_stmt|false {}

	/**
	 * Transfers a result set from the last query
	 * @link http://www.php.net/manual/en/mysqli.store-result.php
	 * @param int $mode [optional] 
	 * @return mysqli_result|false Returns a buffered result object or false if an error occurred.
	 * <p>mysqli_store_result returns false in case the query
	 * didn't return a result set (if the query was, for example an INSERT
	 * statement). This function also returns false if the reading of the
	 * result set failed. You can check if you have got an error by checking
	 * if mysqli_error doesn't return an empty string, if
	 * mysqli_errno returns a non zero value, or if
	 * mysqli_field_count returns a non zero value.
	 * Also possible reason for this function returning false after
	 * successful call to mysqli_query can be too large
	 * result set (memory for it cannot be allocated). If
	 * mysqli_field_count returns a non-zero value, the
	 * statement should have produced a non-empty result set.</p>
	 */
	public function store_result (int $mode = null): mysqli_result|false {}

	/**
	 * Returns whether thread safety is given or not
	 * @link http://www.php.net/manual/en/mysqli.thread-safe.php
	 * @return bool true if the client library is thread-safe, otherwise false.
	 */
	public function thread_safe (): bool {}

	/**
	 * Initiate a result set retrieval
	 * @link http://www.php.net/manual/en/mysqli.use-result.php
	 * @return mysqli_result|false Returns an unbuffered result object or false if an error occurred.
	 */
	public function use_result (): mysqli_result|false {}

	/**
	 * Refreshes
	 * @link http://www.php.net/manual/en/mysqli.refresh.php
	 * @param int $flags 
	 * @return bool true if the refresh was a success, otherwise false
	 */
	public function refresh (int $flags): bool {}

}

/**
 * Represents a MySQL warning.
 * @link http://www.php.net/manual/en/class.mysqli_warning.php
 */
final class mysqli_warning  {

	/**
	 * Message string
	 * @var string
	 * @link http://www.php.net/manual/en/class.mysqli_warning.php#mysqli_warning.props.message
	 */
	public string $message;

	/**
	 * SQL state
	 * @var string
	 * @link http://www.php.net/manual/en/class.mysqli_warning.php#mysqli_warning.props.sqlstate
	 */
	public string $sqlstate;

	/**
	 * Error number
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli_warning.php#mysqli_warning.props.errno
	 */
	public int $errno;

	/**
	 * Private constructor to disallow direct instantiation
	 * @link http://www.php.net/manual/en/mysqli-warning.construct.php
	 */
	private function __construct () {}

	/**
	 * Fetch next warning
	 * @link http://www.php.net/manual/en/mysqli-warning.next.php
	 * @return bool Returns true if next warning was fetched successfully.
	 * If there are no more warnings, it will return false
	 */
	public function next (): bool {}

}

/**
 * Represents the result set obtained from a query against the database.
 * @link http://www.php.net/manual/en/class.mysqli_result.php
 */
class mysqli_result implements IteratorAggregate, Traversable {

	/**
	 * Get current field offset of a result pointer
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli_result.php#mysqli_result.props.current_field
	 */
	public int $current_field;

	/**
	 * Gets the number of fields in the result set
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli_result.php#mysqli_result.props.field_count
	 */
	public int $field_count;

	/**
	 * Returns the lengths of the columns of the current row in the result set
	 * @var array|null
	 * @link http://www.php.net/manual/en/class.mysqli_result.php#mysqli_result.props.lengths
	 */
	public ?array $lengths;

	/**
	 * Gets the number of rows in the result set
	 * @var int|string
	 * @link http://www.php.net/manual/en/class.mysqli_result.php#mysqli_result.props.num_rows
	 */
	public int|string $num_rows;

	/**
	 * Stores whether the result is buffered or unbuffered as an int
	 * (MYSQLI_STORE_RESULT or
	 * MYSQLI_USE_RESULT, respectively).
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli_result.php#mysqli_result.props.type
	 */
	public int $type;

	/**
	 * Constructs a mysqli_result object
	 * @link http://www.php.net/manual/en/mysqli-result.construct.php
	 * @param mysqli $mysql Procedural style only: A mysqli object
	 * returned by mysqli_connect or mysqli_init
	 * @param int $result_mode [optional] The result mode can be one of 2 constants indicating how the result will
	 * be returned from the MySQL server.
	 * <p>MYSQLI_STORE_RESULT (default) - creates a
	 * mysqli_result object with buffered result set.</p>
	 * <p>MYSQLI_USE_RESULT - creates a
	 * mysqli_result object with unbuffered result set. 
	 * As long as there are pending records waiting to be fetched, the
	 * connection line will be busy and all subsequent calls will return error 
	 * Commands out of sync. To avoid the error all records 
	 * must be fetched from the server or the result set must be discarded by
	 * calling mysqli_free_result. The connection must
	 * remain open for the rows to be fetched.</p>
	 * @return mysqli 
	 */
	public function __construct (mysqli $mysql, int $result_mode = MYSQLI_STORE_RESULT): mysqli {}

	/**
	 * Frees the memory associated with a result
	 * @link http://www.php.net/manual/en/mysqli-result.free.php
	 * @return void No value is returned.
	 */
	public function close (): void {}

	/**
	 * Frees the memory associated with a result
	 * @link http://www.php.net/manual/en/mysqli-result.free.php
	 * @return void No value is returned.
	 */
	public function free (): void {}

	/**
	 * Adjusts the result pointer to an arbitrary row in the result
	 * @link http://www.php.net/manual/en/mysqli-result.data-seek.php
	 * @param int $offset 
	 * @return bool Returns true on success or false on failure.
	 */
	public function data_seek (int $offset): bool {}

	/**
	 * Returns the next field in the result set
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-field.php
	 * @return object|false Returns an object which contains field definition information or false
	 * if no field information is available.
	 * <p><table>
	 * Object properties
	 * <table>
	 * <tr valign="top">
	 * <td>Property</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>name</td>
	 * <td>The name of the column</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>orgname</td>
	 * <td>Original column name if an alias was specified</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>table</td>
	 * <td>The name of the table this field belongs to (if not calculated)</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>orgtable</td>
	 * <td>Original table name if an alias was specified</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>def</td>
	 * <td>Reserved for default value, currently always ""</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>db</td>
	 * <td>The name of the database</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>catalog</td>
	 * <td>The catalog name, always "def"</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>max_length</td>
	 * <td>The maximum width of the field for the result set.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>length</td>
	 * <td>The width of the field, as specified in the table definition.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>charsetnr</td>
	 * <td>The character set number for the field.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>flags</td>
	 * <td>An integer representing the bit-flags for the field.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>type</td>
	 * <td>The data type used for this field</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>decimals</td>
	 * <td>The number of decimals used (for integer fields)</td>
	 * </tr>
	 * </table>
	 * </table></p>
	 */
	public function fetch_field (): object|false {}

	/**
	 * Returns an array of objects representing the fields in a result set
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-fields.php
	 * @return array Returns an array of objects containing field definition information.
	 * <p><table>
	 * Object properties
	 * <table>
	 * <tr valign="top">
	 * <td>Property</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>name</td>
	 * <td>The name of the column</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>orgname</td>
	 * <td>Original column name if an alias was specified</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>table</td>
	 * <td>The name of the table this field belongs to (if not calculated)</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>orgtable</td>
	 * <td>Original table name if an alias was specified</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>max_length</td>
	 * <td>The maximum width of the field for the result set. As of PHP 8.1, this value is always 0.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>length</td>
	 * <td>
	 * The width of the field, in bytes, as specified in the table definition. Note that 
	 * this number (bytes) might differ from your table definition value (characters), depending on
	 * the character set you use. For example, the character set utf8 has 3 bytes per character, 
	 * so varchar(10) will return a length of 30 for utf8 (10&#42;3), but return 10 for latin1 (10&#42;1).
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>charsetnr</td>
	 * <td>The character set number (id) for the field.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>flags</td>
	 * <td>An integer representing the bit-flags for the field.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>type</td>
	 * <td>The data type used for this field</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>decimals</td>
	 * <td>The number of decimals used (for integer fields)</td>
	 * </tr>
	 * </table>
	 * </table></p>
	 */
	public function fetch_fields (): array {}

	/**
	 * Fetch meta-data for a single field
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-field-direct.php
	 * @param int $index 
	 * @return object|false Returns an object which contains field definition information or false
	 * if no field information for specified fieldnr is 
	 * available.
	 * <p><table>
	 * Object attributes
	 * <table>
	 * <tr valign="top">
	 * <td>Attribute</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>name</td>
	 * <td>The name of the column</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>orgname</td>
	 * <td>Original column name if an alias was specified</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>table</td>
	 * <td>The name of the table this field belongs to (if not calculated)</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>orgtable</td>
	 * <td>Original table name if an alias was specified</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>def</td>
	 * <td>The default value for this field, represented as a string</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>max_length</td>
	 * <td>The maximum width of the field for the result set. As of PHP 8.1, this value is always 0.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>length</td>
	 * <td>The width of the field, as specified in the table definition.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>charsetnr</td>
	 * <td>The character set number for the field.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>flags</td>
	 * <td>An integer representing the bit-flags for the field.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>type</td>
	 * <td>The data type used for this field</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>decimals</td>
	 * <td>The number of decimals used (for numeric fields)</td>
	 * </tr>
	 * </table>
	 * </table></p>
	 */
	public function fetch_field_direct (int $index): object|false {}

	/**
	 * Fetch all result rows as an associative array, a numeric array, or both
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-all.php
	 * @param int $mode [optional] 
	 * @return array Returns an array of associative or numeric arrays holding result rows.
	 */
	public function fetch_all (int $mode = MYSQLI_NUM): array {}

	/**
	 * Fetch the next row of a result set as an associative, a numeric array, or both
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-array.php
	 * @param int $mode [optional] 
	 * @return array|null|false Returns an array representing the fetched row, null if there
	 * are no more rows in the result set, or false on failure.
	 */
	public function fetch_array (int $mode = MYSQLI_BOTH): array|null|false {}

	/**
	 * Fetch the next row of a result set as an associative array
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-assoc.php
	 * @return array|null|false Returns an associative array representing the fetched row,
	 * where each key in the array represents the name of one of the result
	 * set's columns, null if there
	 * are no more rows in the result set, or false on failure.
	 */
	public function fetch_assoc (): array|null|false {}

	/**
	 * Fetch the next row of a result set as an object
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-object.php
	 * @param string $class [optional] 
	 * @param array $constructor_args [optional] 
	 * @return object|null|false Returns an object representing the fetched row, where each property
	 * represents the name of the result set's column, null if there
	 * are no more rows in the result set, or false on failure.
	 */
	public function fetch_object (string $class = '"stdClass"', array $constructor_args = '[]'): object|null|false {}

	/**
	 * Fetch the next row of a result set as an enumerated array
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-row.php
	 * @return array|null|false Returns an enumerated array representing the fetched row, null if there
	 * are no more rows in the result set, or false on failure.
	 */
	public function fetch_row (): array|null|false {}

	/**
	 * Fetch a single column from the next row of a result set
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-column.php
	 * @param int $column [optional] 
	 * @return null|int|float|string|false Returns a single column
	 * from the next row of a result set or false if there are no more rows.
	 * <p>There is no way to return another column from the same row if you
	 * use this function to retrieve data.</p>
	 */
	public function fetch_column (int $column = null): null|int|float|string|false {}

	/**
	 * Set result pointer to a specified field offset
	 * @link http://www.php.net/manual/en/mysqli-result.field-seek.php
	 * @param int $index 
	 * @return bool Returns true on success or false on failure.
	 */
	public function field_seek (int $index): bool {}

	/**
	 * Frees the memory associated with a result
	 * @link http://www.php.net/manual/en/mysqli-result.free.php
	 * @return void No value is returned.
	 */
	public function free_result (): void {}

	/**
	 * Retrieve an external iterator
	 * @link http://www.php.net/manual/en/mysqli-result.getiterator.php
	 * @return Iterator 
	 */
	public function getIterator (): Iterator {}

}

/**
 * Represents a prepared statement.
 * @link http://www.php.net/manual/en/class.mysqli_stmt.php
 */
class mysqli_stmt  {

	/**
	 * Returns the total number of rows changed, deleted, inserted, or
	 * matched by the last statement executed
	 * 
	 * @var int|string
	 * @link http://www.php.net/manual/en/class.mysqli_stmt.php#mysqli_stmt.props.affected_rows
	 */
	public int|string $affected_rows;

	/**
	 * Get the ID generated from the previous INSERT operation
	 * @var int|string
	 * @link http://www.php.net/manual/en/class.mysqli_stmt.php#mysqli_stmt.props.insert_id
	 */
	public int|string $insert_id;

	/**
	 * Returns the number of rows fetched from the server
	 * @var int|string
	 * @link http://www.php.net/manual/en/class.mysqli_stmt.php#mysqli_stmt.props.num_rows
	 */
	public int|string $num_rows;

	/**
	 * Returns the number of parameters for the given statement
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli_stmt.php#mysqli_stmt.props.param_count
	 */
	public int $param_count;

	/**
	 * Returns the number of columns in the given statement
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli_stmt.php#mysqli_stmt.props.field_count
	 */
	public int $field_count;

	/**
	 * Returns the error code for the most recent statement call
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli_stmt.php#mysqli_stmt.props.errno
	 */
	public int $errno;

	/**
	 * Returns a string description for last statement error
	 * @var string
	 * @link http://www.php.net/manual/en/class.mysqli_stmt.php#mysqli_stmt.props.error
	 */
	public string $error;

	/**
	 * Returns a list of errors from the last statement executed
	 * @var array
	 * @link http://www.php.net/manual/en/class.mysqli_stmt.php#mysqli_stmt.props.error_list
	 */
	public array $error_list;

	/**
	 * Returns SQLSTATE error from previous statement operation
	 * @var string
	 * @link http://www.php.net/manual/en/class.mysqli_stmt.php#mysqli_stmt.props.sqlstate
	 */
	public string $sqlstate;

	/**
	 * Stores the statement ID.
	 * @var int
	 * @link http://www.php.net/manual/en/class.mysqli_stmt.php#mysqli_stmt.props.id
	 */
	public int $id;

	/**
	 * Constructs a new mysqli_stmt object
	 * @link http://www.php.net/manual/en/mysqli-stmt.construct.php
	 * @param mysqli $mysql 
	 * @param string|null $query [optional] 
	 * @return mysqli 
	 */
	public function __construct (mysqli $mysql, ?string $query = null): mysqli {}

	/**
	 * Used to get the current value of a statement attribute
	 * @link http://www.php.net/manual/en/mysqli-stmt.attr-get.php
	 * @param int $attribute 
	 * @return int Returns the value of the attribute.
	 */
	public function attr_get (int $attribute): int {}

	/**
	 * Used to modify the behavior of a prepared statement
	 * @link http://www.php.net/manual/en/mysqli-stmt.attr-set.php
	 * @param int $attribute 
	 * @param int $value 
	 * @return bool Returns true on success or false on failure.
	 */
	public function attr_set (int $attribute, int $value): bool {}

	/**
	 * Binds variables to a prepared statement as parameters
	 * @link http://www.php.net/manual/en/mysqli-stmt.bind-param.php
	 * @param string $types 
	 * @param mixed $var 
	 * @param mixed $vars 
	 * @return bool Returns true on success or false on failure.
	 */
	public function bind_param (string $types, mixed &$var, mixed &...$vars): bool {}

	/**
	 * Binds variables to a prepared statement for result storage
	 * @link http://www.php.net/manual/en/mysqli-stmt.bind-result.php
	 * @param mixed $var 
	 * @param mixed $vars 
	 * @return bool Returns true on success or false on failure.
	 */
	public function bind_result (mixed &$var, mixed &...$vars): bool {}

	/**
	 * Closes a prepared statement
	 * @link http://www.php.net/manual/en/mysqli-stmt.close.php
	 * @return true Always returns true.
	 */
	public function close (): true {}

	/**
	 * Adjusts the result pointer to an arbitrary row in the buffered result
	 * @link http://www.php.net/manual/en/mysqli-stmt.data-seek.php
	 * @param int $offset 
	 * @return void No value is returned.
	 */
	public function data_seek (int $offset): void {}

	/**
	 * Executes a prepared statement
	 * @link http://www.php.net/manual/en/mysqli-stmt.execute.php
	 * @param array|null $params [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function execute (?array $params = null): bool {}

	/**
	 * Fetch results from a prepared statement into the bound variables
	 * @link http://www.php.net/manual/en/mysqli-stmt.fetch.php
	 * @return bool|null 
	 */
	public function fetch (): ?bool {}

	/**
	 * Get result of SHOW WARNINGS
	 * @link http://www.php.net/manual/en/mysqli-stmt.get-warnings.php
	 * @return mysqli_warning|false 
	 */
	public function get_warnings (): mysqli_warning|false {}

	/**
	 * Returns result set metadata from a prepared statement
	 * @link http://www.php.net/manual/en/mysqli-stmt.result-metadata.php
	 * @return mysqli_result|false Returns a result object or false if an error occurred.
	 */
	public function result_metadata (): mysqli_result|false {}

	/**
	 * Check if there are more query results from a multiple query
	 * @link http://www.php.net/manual/en/mysqli-stmt.more-results.php
	 * @return bool Returns true if more results exist, otherwise false.
	 */
	public function more_results (): bool {}

	/**
	 * Reads the next result from a multiple query
	 * @link http://www.php.net/manual/en/mysqli-stmt.next-result.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function next_result (): bool {}

	/**
	 * Returns the number of rows fetched from the server
	 * @link http://www.php.net/manual/en/mysqli-stmt.num-rows.php
	 * @return int|string An int representing the number of buffered rows.
	 * Returns 0 in unbuffered mode unless all rows have been
	 * fetched from the server.
	 * <p>If the number of rows is greater than PHP_INT_MAX,
	 * the number will be returned as a string.</p>
	 */
	public function num_rows (): int|string {}

	/**
	 * Send data in blocks
	 * @link http://www.php.net/manual/en/mysqli-stmt.send-long-data.php
	 * @param int $param_num 
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function send_long_data (int $param_num, string $data): bool {}

	/**
	 * Frees stored result memory for the given statement handle
	 * @link http://www.php.net/manual/en/mysqli-stmt.free-result.php
	 * @return void No value is returned.
	 */
	public function free_result (): void {}

	/**
	 * Resets a prepared statement
	 * @link http://www.php.net/manual/en/mysqli-stmt.reset.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function reset (): bool {}

	/**
	 * Prepares an SQL statement for execution
	 * @link http://www.php.net/manual/en/mysqli-stmt.prepare.php
	 * @param string $query 
	 * @return bool Returns true on success or false on failure.
	 */
	public function prepare (string $query): bool {}

	/**
	 * Stores a result set in an internal buffer
	 * @link http://www.php.net/manual/en/mysqli-stmt.store-result.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function store_result (): bool {}

	/**
	 * Gets a result set from a prepared statement as a mysqli_result object
	 * @link http://www.php.net/manual/en/mysqli-stmt.get-result.php
	 * @return mysqli_result|false Returns false on failure. For successful queries which produce a result set, such as SELECT, SHOW, DESCRIBE or
	 * EXPLAIN, mysqli_stmt_get_result will return
	 * a mysqli_result object. For other successful queries, mysqli_stmt_get_result will
	 * return false. The mysqli_stmt_errno function can be
	 * used to distinguish between the two reasons for false; due to a bug, prior to PHP 7.4.13,
	 * mysqli_errno had to be used for this purpose.
	 */
	public function get_result (): mysqli_result|false {}

}

/**
 * Gets the number of affected rows in a previous MySQL operation
 * @link http://www.php.net/manual/en/mysqli.affected-rows.php
 * @param mysqli $mysql 
 * @return int|string An integer greater than zero indicates the number of rows affected or
 * retrieved. Zero indicates that no records were updated for an 
 * UPDATE statement, no rows matched the
 * WHERE clause in the query or that no query has yet been
 * executed. -1 indicates that the query returned an error or
 * that mysqli_affected_rows was called for an unbuffered
 * SELECT query.
 * <p>If the number of affected rows is greater than the maximum int value
 * (PHP_INT_MAX), the number of affected rows will be
 * returned as a string.</p>
 */
function mysqli_affected_rows (mysqli $mysql): int|string {}

/**
 * Turns on or off auto-committing database modifications
 * @link http://www.php.net/manual/en/mysqli.autocommit.php
 * @param mysqli $mysql 
 * @param bool $enable 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_autocommit (mysqli $mysql, bool $enable): bool {}

/**
 * Starts a transaction
 * @link http://www.php.net/manual/en/mysqli.begin-transaction.php
 * @param mysqli $mysql 
 * @param int $flags [optional] 
 * @param string|null $name [optional] 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_begin_transaction (mysqli $mysql, int $flags = null, ?string $name = null): bool {}

/**
 * Changes the user of the specified database connection
 * @link http://www.php.net/manual/en/mysqli.change-user.php
 * @param mysqli $mysql 
 * @param string $username 
 * @param string $password 
 * @param string|null $database 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_change_user (mysqli $mysql, string $username, string $password, ?string $database): bool {}

/**
 * Returns the current character set of the database connection
 * @link http://www.php.net/manual/en/mysqli.character-set-name.php
 * @param mysqli $mysql 
 * @return string The current character set of the connection
 */
function mysqli_character_set_name (mysqli $mysql): string {}

/**
 * Closes a previously opened database connection
 * @link http://www.php.net/manual/en/mysqli.close.php
 * @param mysqli $mysql 
 * @return true Always returns true.
 */
function mysqli_close (mysqli $mysql): true {}

/**
 * Commits the current transaction
 * @link http://www.php.net/manual/en/mysqli.commit.php
 * @param mysqli $mysql 
 * @param int $flags [optional] 
 * @param string|null $name [optional] 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_commit (mysqli $mysql, int $flags = null, ?string $name = null): bool {}

/**
 * Open a new connection to the MySQL server
 * @link http://www.php.net/manual/en/mysqli.construct.php
 * @param string|null $hostname [optional] 
 * @param string|null $username [optional] 
 * @param string|null $password [optional] 
 * @param string|null $database [optional] 
 * @param int|null $port [optional] 
 * @param string|null $socket [optional] 
 * @return mysqli|false mysqli::__construct always returns an object which represents the connection to a MySQL Server, 
 * regardless of it being successful or not.
 * <p>mysqli_connect returns an object which represents the connection to a MySQL Server,
 * or false on failure.</p>
 * <p>mysqli::connect returns null on success or false on failure.</p>
 */
function mysqli_connect (?string $hostname = null, ?string $username = null, ?string $password = null, ?string $database = null, ?int $port = null, ?string $socket = null): mysqli|false {}

/**
 * Returns the error code from last connect call
 * @link http://www.php.net/manual/en/mysqli.connect-errno.php
 * @return int An error code for the last connection attempt, if it failed.
 * Zero means no error occurred.
 */
function mysqli_connect_errno (): int {}

/**
 * Returns a description of the last connection error
 * @link http://www.php.net/manual/en/mysqli.connect-error.php
 * @return string|null A string that describes the error. null is returned if no error occurred.
 */
function mysqli_connect_error (): ?string {}

/**
 * Adjusts the result pointer to an arbitrary row in the result
 * @link http://www.php.net/manual/en/mysqli-result.data-seek.php
 * @param mysqli_result $result 
 * @param int $offset 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_data_seek (mysqli_result $result, int $offset): bool {}

/**
 * Dump debugging information into the log
 * @link http://www.php.net/manual/en/mysqli.dump-debug-info.php
 * @param mysqli $mysql 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_dump_debug_info (mysqli $mysql): bool {}

/**
 * Performs debugging operations
 * @link http://www.php.net/manual/en/mysqli.debug.php
 * @param string $options 
 * @return true Always returns true.
 */
function mysqli_debug (string $options): true {}

/**
 * Returns the error code for the most recent function call
 * @link http://www.php.net/manual/en/mysqli.errno.php
 * @param mysqli $mysql 
 * @return int An error code value for the last call, if it failed. zero means no error
 * occurred.
 */
function mysqli_errno (mysqli $mysql): int {}

/**
 * Returns a string description of the last error
 * @link http://www.php.net/manual/en/mysqli.error.php
 * @param mysqli $mysql 
 * @return string A string that describes the error. An empty string if no error occurred.
 */
function mysqli_error (mysqli $mysql): string {}

/**
 * Returns a list of errors from the last command executed
 * @link http://www.php.net/manual/en/mysqli.error-list.php
 * @param mysqli $mysql 
 * @return array A list of errors, each as an associative array
 * containing the errno, error, and sqlstate.
 */
function mysqli_error_list (mysqli $mysql): array {}

/**
 * Executes a prepared statement
 * @link http://www.php.net/manual/en/mysqli-stmt.execute.php
 * @param mysqli_stmt $statement 
 * @param array|null $params [optional] 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_stmt_execute (mysqli_stmt $statement, ?array $params = null): bool {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 * @param array|null $params [optional]
 */
function mysqli_execute (mysqli_stmt $statement, ?array $params = NULL): bool {}

/**
 * Prepares, binds parameters, and executes SQL statement
 * @link http://www.php.net/manual/en/mysqli.execute-query.php
 * @param mysqli $mysql 
 * @param string $query 
 * @param array|null $params [optional] 
 * @return mysqli_result|bool Returns false on failure. For successful queries which produce a result
 * set, such as SELECT, SHOW, DESCRIBE or
 * EXPLAIN, returns
 * a mysqli_result object. For other successful queries,
 * returns true.
 */
function mysqli_execute_query (mysqli $mysql, string $query, ?array $params = null): mysqli_result|bool {}

/**
 * Returns the next field in the result set
 * @link http://www.php.net/manual/en/mysqli-result.fetch-field.php
 * @param mysqli_result $result 
 * @return object|false Returns an object which contains field definition information or false
 * if no field information is available.
 * <p><table>
 * Object properties
 * <table>
 * <tr valign="top">
 * <td>Property</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>name</td>
 * <td>The name of the column</td>
 * </tr>
 * <tr valign="top">
 * <td>orgname</td>
 * <td>Original column name if an alias was specified</td>
 * </tr>
 * <tr valign="top">
 * <td>table</td>
 * <td>The name of the table this field belongs to (if not calculated)</td>
 * </tr>
 * <tr valign="top">
 * <td>orgtable</td>
 * <td>Original table name if an alias was specified</td>
 * </tr>
 * <tr valign="top">
 * <td>def</td>
 * <td>Reserved for default value, currently always ""</td>
 * </tr>
 * <tr valign="top">
 * <td>db</td>
 * <td>The name of the database</td>
 * </tr>
 * <tr valign="top">
 * <td>catalog</td>
 * <td>The catalog name, always "def"</td>
 * </tr>
 * <tr valign="top">
 * <td>max_length</td>
 * <td>The maximum width of the field for the result set.</td>
 * </tr>
 * <tr valign="top">
 * <td>length</td>
 * <td>The width of the field, as specified in the table definition.</td>
 * </tr>
 * <tr valign="top">
 * <td>charsetnr</td>
 * <td>The character set number for the field.</td>
 * </tr>
 * <tr valign="top">
 * <td>flags</td>
 * <td>An integer representing the bit-flags for the field.</td>
 * </tr>
 * <tr valign="top">
 * <td>type</td>
 * <td>The data type used for this field</td>
 * </tr>
 * <tr valign="top">
 * <td>decimals</td>
 * <td>The number of decimals used (for integer fields)</td>
 * </tr>
 * </table>
 * </table></p>
 */
function mysqli_fetch_field (mysqli_result $result): object|false {}

/**
 * Returns an array of objects representing the fields in a result set
 * @link http://www.php.net/manual/en/mysqli-result.fetch-fields.php
 * @param mysqli_result $result 
 * @return array Returns an array of objects containing field definition information.
 * <p><table>
 * Object properties
 * <table>
 * <tr valign="top">
 * <td>Property</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>name</td>
 * <td>The name of the column</td>
 * </tr>
 * <tr valign="top">
 * <td>orgname</td>
 * <td>Original column name if an alias was specified</td>
 * </tr>
 * <tr valign="top">
 * <td>table</td>
 * <td>The name of the table this field belongs to (if not calculated)</td>
 * </tr>
 * <tr valign="top">
 * <td>orgtable</td>
 * <td>Original table name if an alias was specified</td>
 * </tr>
 * <tr valign="top">
 * <td>max_length</td>
 * <td>The maximum width of the field for the result set. As of PHP 8.1, this value is always 0.</td>
 * </tr>
 * <tr valign="top">
 * <td>length</td>
 * <td>
 * The width of the field, in bytes, as specified in the table definition. Note that 
 * this number (bytes) might differ from your table definition value (characters), depending on
 * the character set you use. For example, the character set utf8 has 3 bytes per character, 
 * so varchar(10) will return a length of 30 for utf8 (10&#42;3), but return 10 for latin1 (10&#42;1).
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>charsetnr</td>
 * <td>The character set number (id) for the field.</td>
 * </tr>
 * <tr valign="top">
 * <td>flags</td>
 * <td>An integer representing the bit-flags for the field.</td>
 * </tr>
 * <tr valign="top">
 * <td>type</td>
 * <td>The data type used for this field</td>
 * </tr>
 * <tr valign="top">
 * <td>decimals</td>
 * <td>The number of decimals used (for integer fields)</td>
 * </tr>
 * </table>
 * </table></p>
 */
function mysqli_fetch_fields (mysqli_result $result): array {}

/**
 * Fetch meta-data for a single field
 * @link http://www.php.net/manual/en/mysqli-result.fetch-field-direct.php
 * @param mysqli_result $result 
 * @param int $index 
 * @return object|false Returns an object which contains field definition information or false
 * if no field information for specified fieldnr is 
 * available.
 * <p><table>
 * Object attributes
 * <table>
 * <tr valign="top">
 * <td>Attribute</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>name</td>
 * <td>The name of the column</td>
 * </tr>
 * <tr valign="top">
 * <td>orgname</td>
 * <td>Original column name if an alias was specified</td>
 * </tr>
 * <tr valign="top">
 * <td>table</td>
 * <td>The name of the table this field belongs to (if not calculated)</td>
 * </tr>
 * <tr valign="top">
 * <td>orgtable</td>
 * <td>Original table name if an alias was specified</td>
 * </tr>
 * <tr valign="top">
 * <td>def</td>
 * <td>The default value for this field, represented as a string</td>
 * </tr>
 * <tr valign="top">
 * <td>max_length</td>
 * <td>The maximum width of the field for the result set. As of PHP 8.1, this value is always 0.</td>
 * </tr>
 * <tr valign="top">
 * <td>length</td>
 * <td>The width of the field, as specified in the table definition.</td>
 * </tr>
 * <tr valign="top">
 * <td>charsetnr</td>
 * <td>The character set number for the field.</td>
 * </tr>
 * <tr valign="top">
 * <td>flags</td>
 * <td>An integer representing the bit-flags for the field.</td>
 * </tr>
 * <tr valign="top">
 * <td>type</td>
 * <td>The data type used for this field</td>
 * </tr>
 * <tr valign="top">
 * <td>decimals</td>
 * <td>The number of decimals used (for numeric fields)</td>
 * </tr>
 * </table>
 * </table></p>
 */
function mysqli_fetch_field_direct (mysqli_result $result, int $index): object|false {}

/**
 * Returns the lengths of the columns of the current row in the result set
 * @link http://www.php.net/manual/en/mysqli-result.lengths.php
 * @param mysqli_result $result 
 * @return array|false An array of integers representing the size of each column (not including
 * any terminating null characters). false if an error occurred.
 * <p>mysqli_fetch_lengths is valid only for the current
 * row of the result set. It returns false if you call it before calling
 * mysqli_fetch_row/array/object or after retrieving all rows in the result.</p>
 */
function mysqli_fetch_lengths (mysqli_result $result): array|false {}

/**
 * Fetch all result rows as an associative array, a numeric array, or both
 * @link http://www.php.net/manual/en/mysqli-result.fetch-all.php
 * @param mysqli_result $result 
 * @param int $mode [optional] 
 * @return array Returns an array of associative or numeric arrays holding result rows.
 */
function mysqli_fetch_all (mysqli_result $result, int $mode = MYSQLI_NUM): array {}

/**
 * Fetch the next row of a result set as an associative, a numeric array, or both
 * @link http://www.php.net/manual/en/mysqli-result.fetch-array.php
 * @param mysqli_result $result 
 * @param int $mode [optional] 
 * @return array|null|false Returns an array representing the fetched row, null if there
 * are no more rows in the result set, or false on failure.
 */
function mysqli_fetch_array (mysqli_result $result, int $mode = MYSQLI_BOTH): array|null|false {}

/**
 * Fetch the next row of a result set as an associative array
 * @link http://www.php.net/manual/en/mysqli-result.fetch-assoc.php
 * @param mysqli_result $result 
 * @return array|null|false Returns an associative array representing the fetched row,
 * where each key in the array represents the name of one of the result
 * set's columns, null if there
 * are no more rows in the result set, or false on failure.
 */
function mysqli_fetch_assoc (mysqli_result $result): array|null|false {}

/**
 * Fetch the next row of a result set as an object
 * @link http://www.php.net/manual/en/mysqli-result.fetch-object.php
 * @param mysqli_result $result 
 * @param string $class [optional] 
 * @param array $constructor_args [optional] 
 * @return object|null|false Returns an object representing the fetched row, where each property
 * represents the name of the result set's column, null if there
 * are no more rows in the result set, or false on failure.
 */
function mysqli_fetch_object (mysqli_result $result, string $class = '"stdClass"', array $constructor_args = '[]'): object|null|false {}

/**
 * Fetch the next row of a result set as an enumerated array
 * @link http://www.php.net/manual/en/mysqli-result.fetch-row.php
 * @param mysqli_result $result 
 * @return array|null|false Returns an enumerated array representing the fetched row, null if there
 * are no more rows in the result set, or false on failure.
 */
function mysqli_fetch_row (mysqli_result $result): array|null|false {}

/**
 * Fetch a single column from the next row of a result set
 * @link http://www.php.net/manual/en/mysqli-result.fetch-column.php
 * @param mysqli_result $result 
 * @param int $column [optional] 
 * @return null|int|float|string|false Returns a single column
 * from the next row of a result set or false if there are no more rows.
 * <p>There is no way to return another column from the same row if you
 * use this function to retrieve data.</p>
 */
function mysqli_fetch_column (mysqli_result $result, int $column = null): null|int|float|string|false {}

/**
 * Returns the number of columns for the most recent query
 * @link http://www.php.net/manual/en/mysqli.field-count.php
 * @param mysqli $mysql 
 * @return int An integer representing the number of fields in a result set.
 */
function mysqli_field_count (mysqli $mysql): int {}

/**
 * Set result pointer to a specified field offset
 * @link http://www.php.net/manual/en/mysqli-result.field-seek.php
 * @param mysqli_result $result 
 * @param int $index 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_field_seek (mysqli_result $result, int $index): bool {}

/**
 * Get current field offset of a result pointer
 * @link http://www.php.net/manual/en/mysqli-result.current-field.php
 * @param mysqli_result $result 
 * @return int Returns current offset of field cursor.
 */
function mysqli_field_tell (mysqli_result $result): int {}

/**
 * Frees the memory associated with a result
 * @link http://www.php.net/manual/en/mysqli-result.free.php
 * @param mysqli_result $result 
 * @return void No value is returned.
 */
function mysqli_free_result (mysqli_result $result): void {}

/**
 * Returns statistics about the client connection
 * @link http://www.php.net/manual/en/mysqli.get-connection-stats.php
 * @param mysqli $mysql Procedural style only: A mysqli object
 * returned by mysqli_connect or mysqli_init
 * @return array Returns an array with connection stats.
 */
function mysqli_get_connection_stats (mysqli $mysql): array {}

/**
 * Returns client per-process statistics
 * @link http://www.php.net/manual/en/function.mysqli-get-client-stats.php
 * @return array Returns an array with client stats.
 */
function mysqli_get_client_stats (): array {}

/**
 * Returns a character set object
 * @link http://www.php.net/manual/en/mysqli.get-charset.php
 * @param mysqli $mysql 
 * @return object|null The function returns a character set object with the following properties:
 * <p>
 * charset
 * <br><p>Character set name</p>
 * collation
 * <br><p>Collation name</p>
 * dir
 * <br><p>Directory the charset description was fetched from (?) or "" for built-in character sets</p>
 * min_length
 * <br><p>Minimum character length in bytes</p>
 * max_length
 * <br><p>Maximum character length in bytes</p>
 * number
 * <br><p>Internal character set number</p>
 * state
 * <br><p>Character set status (?)</p>
 * </p>
 * <p>Character set name</p>
 * <p>Collation name</p>
 * <p>Directory the charset description was fetched from (?) or "" for built-in character sets</p>
 * <p>Minimum character length in bytes</p>
 * <p>Maximum character length in bytes</p>
 * <p>Internal character set number</p>
 * <p>Character set status (?)</p>
 */
function mysqli_get_charset (mysqli $mysql): ?object {}

/**
 * Get MySQL client info
 * @link http://www.php.net/manual/en/mysqli.get-client-info.php
 * @param mysqli|null $mysql [optional] 
 * @return string A string that represents the MySQL client library version.
 */
function mysqli_get_client_info (?mysqli $mysql = null): string {}

/**
 * Returns the MySQL client version as an integer
 * @link http://www.php.net/manual/en/mysqli.get-client-version.php
 * @return int A number that represents the MySQL client library version in format:
 * main_version&#42;10000 + minor_version &#42;100 + sub_version.
 * For example, 4.1.0 is returned as 40100.
 * <p>This is useful to quickly determine the version of the client library
 * to know if some capability exists.</p>
 */
function mysqli_get_client_version (): int {}

/**
 * Return information about open and cached links
 * @link http://www.php.net/manual/en/function.mysqli-get-links-stats.php
 * @return array mysqli_get_links_stats returns an associative array
 * with three elements, keyed as follows:
 * <p>
 * total
 * <br>
 * <p>
 * An int indicating the total number of open links in
 * any state.
 * </p>
 * active_plinks
 * <br>
 * <p>
 * An int representing the number of active persistent
 * connections.
 * </p>
 * cached_plinks
 * <br>
 * <p>
 * An int representing the number of inactive persistent
 * connections.
 * </p>
 * </p>
 * <p>An int indicating the total number of open links in
 * any state.</p>
 * <p>An int representing the number of active persistent
 * connections.</p>
 * <p>An int representing the number of inactive persistent
 * connections.</p>
 */
function mysqli_get_links_stats (): array {}

/**
 * Returns a string representing the type of connection used
 * @link http://www.php.net/manual/en/mysqli.get-host-info.php
 * @param mysqli $mysql 
 * @return string A character string representing the server hostname and the connection type.
 */
function mysqli_get_host_info (mysqli $mysql): string {}

/**
 * Returns the version of the MySQL protocol used
 * @link http://www.php.net/manual/en/mysqli.get-proto-info.php
 * @param mysqli $mysql 
 * @return int Returns an integer representing the protocol version.
 */
function mysqli_get_proto_info (mysqli $mysql): int {}

/**
 * Returns the version of the MySQL server
 * @link http://www.php.net/manual/en/mysqli.get-server-info.php
 * @param mysqli $mysql 
 * @return string A character string representing the server version.
 */
function mysqli_get_server_info (mysqli $mysql): string {}

/**
 * Returns the version of the MySQL server as an integer
 * @link http://www.php.net/manual/en/mysqli.get-server-version.php
 * @param mysqli $mysql 
 * @return int An integer representing the server version.
 * <p>The form of this version number is
 * main_version &#42; 10000 + minor_version &#42; 100 + sub_version
 * (i.e. version 4.1.0 is 40100).</p>
 */
function mysqli_get_server_version (mysqli $mysql): int {}

/**
 * Get result of SHOW WARNINGS
 * @link http://www.php.net/manual/en/mysqli.get-warnings.php
 * @param mysqli $mysql 
 * @return mysqli_warning|false 
 */
function mysqli_get_warnings (mysqli $mysql): mysqli_warning|false {}

/**
 * Initializes MySQLi and returns an object for use with mysqli_real_connect()
 * @link http://www.php.net/manual/en/mysqli.init.php
 * @return mysqli|false mysqli::init returns null on success, or false on failure.
 * mysqli_init returns an object on success, or false on failure.
 */
function mysqli_init (): mysqli|false {}

/**
 * Retrieves information about the most recently executed query
 * @link http://www.php.net/manual/en/mysqli.info.php
 * @param mysqli $mysql 
 * @return string|null A character string representing additional information about the most recently executed query.
 */
function mysqli_info (mysqli $mysql): ?string {}

/**
 * Returns the value generated for an AUTO_INCREMENT column by the last query
 * @link http://www.php.net/manual/en/mysqli.insert-id.php
 * @param mysqli $mysql 
 * @return int|string The value of the AUTO_INCREMENT field that was updated
 * by the previous query. Returns zero if there was no previous query on the
 * connection or if the query did not update an AUTO_INCREMENT
 * value.
 * <p>Only statements issued using the current connection affect the return value. The
 * value is not affected by statements issued using other connections or clients.</p>
 * <p>If the number is greater than the maximum int value, it will be returned as
 * a string.</p>
 */
function mysqli_insert_id (mysqli $mysql): int|string {}

/**
 * Asks the server to kill a MySQL thread
 * @link http://www.php.net/manual/en/mysqli.kill.php
 * @param mysqli $mysql 
 * @param int $process_id 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_kill (mysqli $mysql, int $process_id): bool {}

/**
 * Check if there are any more query results from a multi query
 * @link http://www.php.net/manual/en/mysqli.more-results.php
 * @param mysqli $mysql 
 * @return bool Returns true if one or more result sets (including errors) are available from a previous call to
 * mysqli_multi_query, otherwise false.
 */
function mysqli_more_results (mysqli $mysql): bool {}

/**
 * Performs one or more queries on the database
 * @link http://www.php.net/manual/en/mysqli.multi-query.php
 * @param mysqli $mysql 
 * @param string $query 
 * @return bool Returns false if the first statement failed.
 * To retrieve subsequent errors from other statements you have to call
 * mysqli_next_result first.
 */
function mysqli_multi_query (mysqli $mysql, string $query): bool {}

/**
 * Prepare next result from multi_query
 * @link http://www.php.net/manual/en/mysqli.next-result.php
 * @param mysqli $mysql 
 * @return bool Returns true on success or false on failure. Also returns false if the next statement resulted in an error, unlike mysqli_more_results.
 */
function mysqli_next_result (mysqli $mysql): bool {}

/**
 * Gets the number of fields in the result set
 * @link http://www.php.net/manual/en/mysqli-result.field-count.php
 * @param mysqli_result $result 
 * @return int An int representing the number of fields.
 */
function mysqli_num_fields (mysqli_result $result): int {}

/**
 * Gets the number of rows in the result set
 * @link http://www.php.net/manual/en/mysqli-result.num-rows.php
 * @param mysqli_result $result 
 * @return int|string An int representing the number of fetched rows.
 * Returns 0 in unbuffered mode unless all rows have been
 * fetched from the server.
 * <p>If the number of rows is greater than PHP_INT_MAX,
 * the number will be returned as a string.</p>
 */
function mysqli_num_rows (mysqli_result $result): int|string {}

/**
 * Set options
 * @link http://www.php.net/manual/en/mysqli.options.php
 * @param mysqli $mysql 
 * @param int $option 
 * @param string|int $value 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_options (mysqli $mysql, int $option, string|int $value): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param int $option
 * @param mixed $value
 */
function mysqli_set_opt (mysqli $mysql, int $option, $value = null): bool {}

/**
 * Pings a server connection, or tries to reconnect if the connection has gone down
 * @link http://www.php.net/manual/en/mysqli.ping.php
 * @param mysqli $mysql 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_ping (mysqli $mysql): bool {}

/**
 * Poll connections
 * @link http://www.php.net/manual/en/mysqli.poll.php
 * @param array|null $read 
 * @param array|null $error 
 * @param array $reject 
 * @param int $seconds 
 * @param int $microseconds [optional] 
 * @return int|false Returns number of ready connections upon success, false otherwise.
 */
function mysqli_poll (?array &$read, ?array &$error, array &$reject, int $seconds, int $microseconds = null): int|false {}

/**
 * Prepares an SQL statement for execution
 * @link http://www.php.net/manual/en/mysqli.prepare.php
 * @param mysqli $mysql 
 * @param string $query 
 * @return mysqli_stmt|false mysqli_prepare returns a statement object or false if an error occurred.
 */
function mysqli_prepare (mysqli $mysql, string $query): mysqli_stmt|false {}

/**
 * Sets mysqli error reporting mode
 * @link http://www.php.net/manual/en/mysqli-driver.report-mode.php
 * @param int $flags 
 * @return bool Returns true.
 */
function mysqli_report (int $flags): bool {}

/**
 * Performs a query on the database
 * @link http://www.php.net/manual/en/mysqli.query.php
 * @param mysqli $mysql 
 * @param string $query 
 * @param int $result_mode [optional] 
 * @return mysqli_result|bool Returns false on failure. For successful queries which produce a result
 * set, such as SELECT, SHOW, DESCRIBE or
 * EXPLAIN, mysqli_query will return
 * a mysqli_result object. For other successful queries,
 * mysqli_query will
 * return true.
 */
function mysqli_query (mysqli $mysql, string $query, int $result_mode = MYSQLI_STORE_RESULT): mysqli_result|bool {}

/**
 * Opens a connection to a mysql server
 * @link http://www.php.net/manual/en/mysqli.real-connect.php
 * @param mysqli $mysql 
 * @param string|null $hostname [optional] 
 * @param string|null $username [optional] 
 * @param string|null $password [optional] 
 * @param string|null $database [optional] 
 * @param int|null $port [optional] 
 * @param string|null $socket [optional] 
 * @param int $flags [optional] 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_real_connect (mysqli $mysql, ?string $hostname = null, ?string $username = null, ?string $password = null, ?string $database = null, ?int $port = null, ?string $socket = null, int $flags = null): bool {}

/**
 * Escapes special characters in a string for use in an SQL statement, taking into account the current charset of the connection
 * @link http://www.php.net/manual/en/mysqli.real-escape-string.php
 * @param mysqli $mysql 
 * @param string $string 
 * @return string Returns an escaped string.
 */
function mysqli_real_escape_string (mysqli $mysql, string $string): string {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param string $string
 */
function mysqli_escape_string (mysqli $mysql, string $string): string {}

/**
 * Execute an SQL query
 * @link http://www.php.net/manual/en/mysqli.real-query.php
 * @param mysqli $mysql 
 * @param string $query 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_real_query (mysqli $mysql, string $query): bool {}

/**
 * Get result from async query
 * @link http://www.php.net/manual/en/mysqli.reap-async-query.php
 * @param mysqli $mysql 
 * @return mysqli_result|bool Returns false on failure. For successful queries which produce a result set, such as SELECT, SHOW, DESCRIBE or
 * EXPLAIN, mysqli_reap_async_query will return
 * a mysqli_result object. For other successful queries, mysqli_reap_async_query will
 * return true.
 */
function mysqli_reap_async_query (mysqli $mysql): mysqli_result|bool {}

/**
 * Removes the named savepoint from the set of savepoints of the current transaction
 * @link http://www.php.net/manual/en/mysqli.release-savepoint.php
 * @param mysqli $mysql 
 * @param string $name 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_release_savepoint (mysqli $mysql, string $name): bool {}

/**
 * Rolls back current transaction
 * @link http://www.php.net/manual/en/mysqli.rollback.php
 * @param mysqli $mysql 
 * @param int $flags [optional] 
 * @param string|null $name [optional] 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_rollback (mysqli $mysql, int $flags = null, ?string $name = null): bool {}

/**
 * Set a named transaction savepoint
 * @link http://www.php.net/manual/en/mysqli.savepoint.php
 * @param mysqli $mysql 
 * @param string $name 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_savepoint (mysqli $mysql, string $name): bool {}

/**
 * Selects the default database for database queries
 * @link http://www.php.net/manual/en/mysqli.select-db.php
 * @param mysqli $mysql 
 * @param string $database 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_select_db (mysqli $mysql, string $database): bool {}

/**
 * Sets the client character set
 * @link http://www.php.net/manual/en/mysqli.set-charset.php
 * @param mysqli $mysql 
 * @param string $charset 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_set_charset (mysqli $mysql, string $charset): bool {}

/**
 * Returns the total number of rows changed, deleted, inserted, or
 * matched by the last statement executed
 * @link http://www.php.net/manual/en/mysqli-stmt.affected-rows.php
 * @param mysqli_stmt $statement 
 * @return int|string An integer greater than zero indicates the number of rows affected or
 * retrieved. Zero indicates that no records were updated for an 
 * UPDATE statement, no rows matched the
 * WHERE clause in the query or that no query has yet been
 * executed. -1 indicates that the query returned an error or
 * that, for a SELECT query,
 * mysqli_stmt_affected_rows was called prior to calling
 * mysqli_stmt_store_result.
 * <p>If the number of affected rows is greater than maximum PHP int value, the
 * number of affected rows will be returned as a string value.</p>
 */
function mysqli_stmt_affected_rows (mysqli_stmt $statement): int|string {}

/**
 * Used to get the current value of a statement attribute
 * @link http://www.php.net/manual/en/mysqli-stmt.attr-get.php
 * @param mysqli_stmt $statement 
 * @param int $attribute 
 * @return int Returns the value of the attribute.
 */
function mysqli_stmt_attr_get (mysqli_stmt $statement, int $attribute): int {}

/**
 * Used to modify the behavior of a prepared statement
 * @link http://www.php.net/manual/en/mysqli-stmt.attr-set.php
 * @param mysqli_stmt $statement 
 * @param int $attribute 
 * @param int $value 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_stmt_attr_set (mysqli_stmt $statement, int $attribute, int $value): bool {}

/**
 * Binds variables to a prepared statement as parameters
 * @link http://www.php.net/manual/en/mysqli-stmt.bind-param.php
 * @param mysqli_stmt $statement 
 * @param string $types 
 * @param mixed $var 
 * @param mixed $vars 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_stmt_bind_param (mysqli_stmt $statement, string $types, mixed &$var, mixed &...$vars): bool {}

/**
 * Binds variables to a prepared statement for result storage
 * @link http://www.php.net/manual/en/mysqli-stmt.bind-result.php
 * @param mysqli_stmt $statement 
 * @param mixed $var 
 * @param mixed $vars 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_stmt_bind_result (mysqli_stmt $statement, mixed &$var, mixed &...$vars): bool {}

/**
 * Closes a prepared statement
 * @link http://www.php.net/manual/en/mysqli-stmt.close.php
 * @param mysqli_stmt $statement 
 * @return true Always returns true.
 */
function mysqli_stmt_close (mysqli_stmt $statement): true {}

/**
 * Adjusts the result pointer to an arbitrary row in the buffered result
 * @link http://www.php.net/manual/en/mysqli-stmt.data-seek.php
 * @param mysqli_stmt $statement 
 * @param int $offset 
 * @return void No value is returned.
 */
function mysqli_stmt_data_seek (mysqli_stmt $statement, int $offset): void {}

/**
 * Returns the error code for the most recent statement call
 * @link http://www.php.net/manual/en/mysqli-stmt.errno.php
 * @param mysqli_stmt $statement 
 * @return int An error code value. Zero means no error occurred.
 */
function mysqli_stmt_errno (mysqli_stmt $statement): int {}

/**
 * Returns a string description for last statement error
 * @link http://www.php.net/manual/en/mysqli-stmt.error.php
 * @param mysqli_stmt $statement 
 * @return string A string that describes the error. An empty string if no error occurred.
 */
function mysqli_stmt_error (mysqli_stmt $statement): string {}

/**
 * Returns a list of errors from the last statement executed
 * @link http://www.php.net/manual/en/mysqli-stmt.error-list.php
 * @param mysqli_stmt $statement 
 * @return array A list of errors, each as an associative array
 * containing the errno, error, and sqlstate.
 */
function mysqli_stmt_error_list (mysqli_stmt $statement): array {}

/**
 * Fetch results from a prepared statement into the bound variables
 * @link http://www.php.net/manual/en/mysqli-stmt.fetch.php
 * @param mysqli_stmt $statement 
 * @return bool|null 
 */
function mysqli_stmt_fetch (mysqli_stmt $statement): ?bool {}

/**
 * Returns the number of columns in the given statement
 * @link http://www.php.net/manual/en/mysqli-stmt.field-count.php
 * @param mysqli_stmt $statement 
 * @return int Returns an integer representing the number of columns.
 */
function mysqli_stmt_field_count (mysqli_stmt $statement): int {}

/**
 * Frees stored result memory for the given statement handle
 * @link http://www.php.net/manual/en/mysqli-stmt.free-result.php
 * @param mysqli_stmt $statement 
 * @return void No value is returned.
 */
function mysqli_stmt_free_result (mysqli_stmt $statement): void {}

/**
 * Gets a result set from a prepared statement as a mysqli_result object
 * @link http://www.php.net/manual/en/mysqli-stmt.get-result.php
 * @param mysqli_stmt $statement 
 * @return mysqli_result|false Returns false on failure. For successful queries which produce a result set, such as SELECT, SHOW, DESCRIBE or
 * EXPLAIN, mysqli_stmt_get_result will return
 * a mysqli_result object. For other successful queries, mysqli_stmt_get_result will
 * return false. The mysqli_stmt_errno function can be
 * used to distinguish between the two reasons for false; due to a bug, prior to PHP 7.4.13,
 * mysqli_errno had to be used for this purpose.
 */
function mysqli_stmt_get_result (mysqli_stmt $statement): mysqli_result|false {}

/**
 * Get result of SHOW WARNINGS
 * @link http://www.php.net/manual/en/mysqli-stmt.get-warnings.php
 * @param mysqli_stmt $statement 
 * @return mysqli_warning|false 
 */
function mysqli_stmt_get_warnings (mysqli_stmt $statement): mysqli_warning|false {}

/**
 * Initializes a statement and returns an object for use with mysqli_stmt_prepare
 * @link http://www.php.net/manual/en/mysqli.stmt-init.php
 * @param mysqli $mysql 
 * @return mysqli_stmt|false Returns an object.
 */
function mysqli_stmt_init (mysqli $mysql): mysqli_stmt|false {}

/**
 * Get the ID generated from the previous INSERT operation
 * @link http://www.php.net/manual/en/mysqli-stmt.insert-id.php
 * @param mysqli_stmt $statement 
 * @return int|string 
 */
function mysqli_stmt_insert_id (mysqli_stmt $statement): int|string {}

/**
 * Check if there are more query results from a multiple query
 * @link http://www.php.net/manual/en/mysqli-stmt.more-results.php
 * @param mysqli_stmt $statement 
 * @return bool Returns true if more results exist, otherwise false.
 */
function mysqli_stmt_more_results (mysqli_stmt $statement): bool {}

/**
 * Reads the next result from a multiple query
 * @link http://www.php.net/manual/en/mysqli-stmt.next-result.php
 * @param mysqli_stmt $statement 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_stmt_next_result (mysqli_stmt $statement): bool {}

/**
 * Returns the number of rows fetched from the server
 * @link http://www.php.net/manual/en/mysqli-stmt.num-rows.php
 * @param mysqli_stmt $statement 
 * @return int|string An int representing the number of buffered rows.
 * Returns 0 in unbuffered mode unless all rows have been
 * fetched from the server.
 * <p>If the number of rows is greater than PHP_INT_MAX,
 * the number will be returned as a string.</p>
 */
function mysqli_stmt_num_rows (mysqli_stmt $statement): int|string {}

/**
 * Returns the number of parameters for the given statement
 * @link http://www.php.net/manual/en/mysqli-stmt.param-count.php
 * @param mysqli_stmt $statement 
 * @return int Returns an integer representing the number of parameters.
 */
function mysqli_stmt_param_count (mysqli_stmt $statement): int {}

/**
 * Prepares an SQL statement for execution
 * @link http://www.php.net/manual/en/mysqli-stmt.prepare.php
 * @param mysqli_stmt $statement 
 * @param string $query 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_stmt_prepare (mysqli_stmt $statement, string $query): bool {}

/**
 * Resets a prepared statement
 * @link http://www.php.net/manual/en/mysqli-stmt.reset.php
 * @param mysqli_stmt $statement 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_stmt_reset (mysqli_stmt $statement): bool {}

/**
 * Returns result set metadata from a prepared statement
 * @link http://www.php.net/manual/en/mysqli-stmt.result-metadata.php
 * @param mysqli_stmt $statement 
 * @return mysqli_result|false Returns a result object or false if an error occurred.
 */
function mysqli_stmt_result_metadata (mysqli_stmt $statement): mysqli_result|false {}

/**
 * Send data in blocks
 * @link http://www.php.net/manual/en/mysqli-stmt.send-long-data.php
 * @param mysqli_stmt $statement 
 * @param int $param_num 
 * @param string $data 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_stmt_send_long_data (mysqli_stmt $statement, int $param_num, string $data): bool {}

/**
 * Stores a result set in an internal buffer
 * @link http://www.php.net/manual/en/mysqli-stmt.store-result.php
 * @param mysqli_stmt $statement 
 * @return bool Returns true on success or false on failure.
 */
function mysqli_stmt_store_result (mysqli_stmt $statement): bool {}

/**
 * Returns SQLSTATE error from previous statement operation
 * @link http://www.php.net/manual/en/mysqli-stmt.sqlstate.php
 * @param mysqli_stmt $statement 
 * @return string Returns a string containing the SQLSTATE error code for the last error.
 * The error code consists of five characters. '00000' means no error.
 */
function mysqli_stmt_sqlstate (mysqli_stmt $statement): string {}

/**
 * Returns the SQLSTATE error from previous MySQL operation
 * @link http://www.php.net/manual/en/mysqli.sqlstate.php
 * @param mysqli $mysql 
 * @return string Returns a string containing the SQLSTATE error code for the last error.
 * The error code consists of five characters. '00000' means no error.
 */
function mysqli_sqlstate (mysqli $mysql): string {}

/**
 * Used for establishing secure connections using SSL
 * @link http://www.php.net/manual/en/mysqli.ssl-set.php
 * @param mysqli $mysql 
 * @param string|null $key 
 * @param string|null $certificate 
 * @param string|null $ca_certificate 
 * @param string|null $ca_path 
 * @param string|null $cipher_algos 
 * @return true Always returns true. If SSL setup is
 * incorrect mysqli_real_connect will return an error
 * when you attempt to connect.
 */
function mysqli_ssl_set (mysqli $mysql, ?string $key, ?string $certificate, ?string $ca_certificate, ?string $ca_path, ?string $cipher_algos): true {}

/**
 * Gets the current system status
 * @link http://www.php.net/manual/en/mysqli.stat.php
 * @param mysqli $mysql 
 * @return string|false A string describing the server status. false if an error occurred.
 */
function mysqli_stat (mysqli $mysql): string|false {}

/**
 * Transfers a result set from the last query
 * @link http://www.php.net/manual/en/mysqli.store-result.php
 * @param mysqli $mysql 
 * @param int $mode [optional] 
 * @return mysqli_result|false Returns a buffered result object or false if an error occurred.
 * <p>mysqli_store_result returns false in case the query
 * didn't return a result set (if the query was, for example an INSERT
 * statement). This function also returns false if the reading of the
 * result set failed. You can check if you have got an error by checking
 * if mysqli_error doesn't return an empty string, if
 * mysqli_errno returns a non zero value, or if
 * mysqli_field_count returns a non zero value.
 * Also possible reason for this function returning false after
 * successful call to mysqli_query can be too large
 * result set (memory for it cannot be allocated). If
 * mysqli_field_count returns a non-zero value, the
 * statement should have produced a non-empty result set.</p>
 */
function mysqli_store_result (mysqli $mysql, int $mode = null): mysqli_result|false {}

/**
 * Returns the thread ID for the current connection
 * @link http://www.php.net/manual/en/mysqli.thread-id.php
 * @param mysqli $mysql 
 * @return int Returns the Thread ID for the current connection.
 */
function mysqli_thread_id (mysqli $mysql): int {}

/**
 * Returns whether thread safety is given or not
 * @link http://www.php.net/manual/en/mysqli.thread-safe.php
 * @return bool true if the client library is thread-safe, otherwise false.
 */
function mysqli_thread_safe (): bool {}

/**
 * Initiate a result set retrieval
 * @link http://www.php.net/manual/en/mysqli.use-result.php
 * @param mysqli $mysql 
 * @return mysqli_result|false Returns an unbuffered result object or false if an error occurred.
 */
function mysqli_use_result (mysqli $mysql): mysqli_result|false {}

/**
 * Returns the number of warnings from the last query for the given link
 * @link http://www.php.net/manual/en/mysqli.warning-count.php
 * @param mysqli $mysql 
 * @return int Number of warnings or zero if there are no warnings.
 */
function mysqli_warning_count (mysqli $mysql): int {}

/**
 * Refreshes
 * @link http://www.php.net/manual/en/mysqli.refresh.php
 * @param mysqli $mysql 
 * @param int $flags 
 * @return bool true if the refresh was a success, otherwise false
 */
function mysqli_refresh (mysqli $mysql, int $flags): bool {}


/**
 * Read options from the named group from my.cnf
 * or the file specified with MYSQLI_READ_DEFAULT_FILE
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_READ_DEFAULT_GROUP', 5);

/**
 * Read options from the named option file instead of from my.cnf
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_READ_DEFAULT_FILE', 4);

/**
 * Connect timeout in seconds
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_OPT_CONNECT_TIMEOUT', 0);

/**
 * Enables command LOAD LOCAL INFILE
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_OPT_LOCAL_INFILE', 8);
define ('MYSQLI_OPT_LOAD_DATA_LOCAL_DIR', 43);

/**
 * Command to execute when connecting to MySQL server. Will automatically be re-executed when reconnecting.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_INIT_COMMAND', 3);

/**
 * Command execution result timeout in seconds. Available as of PHP 7.2.0.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_OPT_READ_TIMEOUT', 11);

/**
 * The size of the internal command/network buffer. Only valid for mysqlnd.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_OPT_NET_CMD_BUFFER_SIZE', 202);

/**
 * Maximum read chunk size in bytes when reading the body of a MySQL command packet.
 * Only valid for mysqlnd.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_OPT_NET_READ_BUFFER_SIZE', 203);

/**
 * Convert integer and float columns back to PHP numbers. Only valid for mysqlnd.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_OPT_INT_AND_FLOAT_NATIVE', 201);

/**
 * Requires MySQL 5.1.10 and up
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_OPT_SSL_VERIFY_SERVER_CERT', 21);

/**
 * 
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_SERVER_PUBLIC_KEY', 35);

/**
 * Use SSL (encrypted protocol). This option should not be set by application programs; 
 * it is set internally in the MySQL client library
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CLIENT_SSL', 2048);

/**
 * Use compression protocol
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CLIENT_COMPRESS', 32);

/**
 * Allow interactive_timeout seconds
 * (instead of wait_timeout seconds) of inactivity before
 * closing the connection. The client's session
 * wait_timeout variable will be set to
 * the value of the session interactive_timeout variable.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CLIENT_INTERACTIVE', 1024);

/**
 * Allow spaces after function names. Makes all functions names reserved words.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CLIENT_IGNORE_SPACE', 256);

/**
 * Don't allow the db_name.tbl_name.col_name syntax.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CLIENT_NO_SCHEMA', 16);
define ('MYSQLI_CLIENT_FOUND_ROWS', 2);
define ('MYSQLI_CLIENT_SSL_VERIFY_SERVER_CERT', 1073741824);

/**
 * Requires MySQL 5.6.5 and up.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CLIENT_SSL_DONT_VERIFY_SERVER_CERT', 64);
define ('MYSQLI_CLIENT_CAN_HANDLE_EXPIRED_PASSWORDS', 4194304);
define ('MYSQLI_OPT_CAN_HANDLE_EXPIRED_PASSWORDS', 37);

/**
 * For using buffered result sets. It has a value of 0.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_STORE_RESULT', 0);

/**
 * For using unbuffered result sets. It has a value of 1.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_USE_RESULT', 1);
define ('MYSQLI_ASYNC', 8);
define ('MYSQLI_STORE_RESULT_COPY_DATA', 16);

/**
 * Columns are returned into the array having the fieldname as the array index.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_ASSOC', 1);

/**
 * Columns are returned into the array having an enumerated index.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_NUM', 2);

/**
 * Columns are returned into the array having both a numerical index and the fieldname as the associative index.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_BOTH', 3);

/**
 * 
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_STMT_ATTR_UPDATE_MAX_LENGTH', 0);

/**
 * 
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_STMT_ATTR_CURSOR_TYPE', 1);

/**
 * 
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CURSOR_TYPE_NO_CURSOR', 0);

/**
 * 
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CURSOR_TYPE_READ_ONLY', 1);

/**
 * 
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CURSOR_TYPE_FOR_UPDATE', 2);

/**
 * 
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_CURSOR_TYPE_SCROLLABLE', 4);

/**
 * 
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_STMT_ATTR_PREFETCH_ROWS', 2);

/**
 * Indicates that a field is defined as NOT NULL
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_NOT_NULL_FLAG', 1);

/**
 * Field is part of a primary index
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_PRI_KEY_FLAG', 2);

/**
 * Field is part of a unique index.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_UNIQUE_KEY_FLAG', 4);

/**
 * Field is part of an index.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_MULTIPLE_KEY_FLAG', 8);

/**
 * Field is defined as BLOB
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_BLOB_FLAG', 16);

/**
 * Field is defined as UNSIGNED
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_UNSIGNED_FLAG', 32);

/**
 * Field is defined as ZEROFILL
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_ZEROFILL_FLAG', 64);

/**
 * Field is defined as AUTO_INCREMENT
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_AUTO_INCREMENT_FLAG', 512);

/**
 * Field is defined as TIMESTAMP
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TIMESTAMP_FLAG', 1024);

/**
 * Field is defined as SET
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_SET_FLAG', 2048);

/**
 * Field is defined as NUMERIC
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_NUM_FLAG', 32768);

/**
 * Field is part of an multi-index
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_PART_KEY_FLAG', 16384);

/**
 * Field is part of GROUP BY
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_GROUP_FLAG', 32768);

/**
 * Field is defined as ENUM.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_ENUM_FLAG', 256);

/**
 * Field is defined as BINARY.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_BINARY_FLAG', 128);
define ('MYSQLI_NO_DEFAULT_VALUE_FLAG', 4096);
define ('MYSQLI_ON_UPDATE_NOW_FLAG', 8192);

/**
 * Field is defined as DECIMAL
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_DECIMAL', 0);

/**
 * Field is defined as TINYINT
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_TINY', 1);

/**
 * Field is defined as SMALLINT
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_SHORT', 2);

/**
 * Field is defined as INT
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_LONG', 3);

/**
 * Field is defined as FLOAT
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_FLOAT', 4);

/**
 * Field is defined as DOUBLE
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_DOUBLE', 5);

/**
 * Field is defined as DEFAULT NULL
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_NULL', 6);

/**
 * Field is defined as TIMESTAMP
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_TIMESTAMP', 7);

/**
 * Field is defined as BIGINT
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_LONGLONG', 8);

/**
 * Field is defined as MEDIUMINT
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_INT24', 9);

/**
 * Field is defined as DATE
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_DATE', 10);

/**
 * Field is defined as TIME
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_TIME', 11);

/**
 * Field is defined as DATETIME
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_DATETIME', 12);

/**
 * Field is defined as YEAR
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_YEAR', 13);

/**
 * Field is defined as DATE
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_NEWDATE', 14);

/**
 * Field is defined as ENUM
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_ENUM', 247);

/**
 * Field is defined as SET
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_SET', 248);

/**
 * Field is defined as TINYBLOB
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_TINY_BLOB', 249);

/**
 * Field is defined as MEDIUMBLOB
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_MEDIUM_BLOB', 250);

/**
 * Field is defined as LONGBLOB
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_LONG_BLOB', 251);

/**
 * Field is defined as BLOB
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_BLOB', 252);

/**
 * Field is defined as VARCHAR
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_VAR_STRING', 253);

/**
 * Field is defined as CHAR or BINARY
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_STRING', 254);

/**
 * Field is defined as TINYINT.
 * For CHAR, see MYSQLI_TYPE_STRING
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_CHAR', 1);

/**
 * Field is defined as INTERVAL
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_INTERVAL', 247);

/**
 * Field is defined as GEOMETRY
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_GEOMETRY', 255);

/**
 * Field is defined as JSON.
 * Only valid for mysqlnd and MySQL 5.7.8 and up.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_JSON', 245);

/**
 * Precision math DECIMAL or NUMERIC field (MySQL 5.0.3 and up)
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_NEWDECIMAL', 246);

/**
 * Field is defined as BIT (MySQL 5.0.3 and up)
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TYPE_BIT', 16);

/**
 * 
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_SET_CHARSET_NAME', 7);
define ('MYSQLI_SET_CHARSET_DIR', 6);

/**
 * No more data available for bind variable
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_NO_DATA', 100);

/**
 * Data truncation occurred. Available since MySQL 5.0.5.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_DATA_TRUNCATED', 101);

/**
 * Report if no index or bad index was used in a query.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_REPORT_INDEX', 4);

/**
 * Report errors from mysqli function calls.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_REPORT_ERROR', 1);

/**
 * Throw a mysqli_sql_exception for errors instead of warnings.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_REPORT_STRICT', 2);

/**
 * Set all options on (report all).
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_REPORT_ALL', 255);

/**
 * Turns reporting off.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_REPORT_OFF', 0);

/**
 * Is set to 1 if mysqli_debug functionality is enabled.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_DEBUG_TRACE_ENABLED', 0);

/**
 * 
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_SERVER_QUERY_NO_GOOD_INDEX_USED', 16);

/**
 * 
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_SERVER_QUERY_NO_INDEX_USED', 32);
define ('MYSQLI_SERVER_QUERY_WAS_SLOW', 2048);
define ('MYSQLI_SERVER_PS_OUT_PARAMS', 4096);

/**
 * Refreshes the grant tables.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_REFRESH_GRANT', 1);

/**
 * Flushes the logs, like executing the
 * FLUSH LOGS SQL statement.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_REFRESH_LOG', 2);

/**
 * Flushes the table cache, like executing the
 * FLUSH TABLES SQL statement.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_REFRESH_TABLES', 4);

/**
 * Flushes the host cache, like executing the
 * FLUSH HOSTS SQL statement.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_REFRESH_HOSTS', 8);

/**
 * Reset the status variables, like executing the
 * FLUSH STATUS SQL statement.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_REFRESH_STATUS', 16);

/**
 * Flushes the thread cache.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_REFRESH_THREADS', 32);

/**
 * Alias of MYSQLI_REFRESH_SLAVE constant.
 * Available as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_REFRESH_REPLICA', 64);

/**
 * On a slave replication server: resets the master server information, and 
 * restarts the slave. Like executing the RESET SLAVE
 * SQL statement.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_REFRESH_SLAVE', 64);

/**
 * On a master replication server: removes the binary log files listed in the
 * binary log index, and truncates the index file. Like executing the
 * RESET MASTER SQL statement.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_REFRESH_MASTER', 128);
define ('MYSQLI_REFRESH_BACKUP_LOG', 2097152);
define ('MYSQLI_TRANS_START_WITH_CONSISTENT_SNAPSHOT', 1);

/**
 * Start the transaction as "START TRANSACTION READ WRITE" with
 * mysqli_begin_transaction.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TRANS_START_READ_WRITE', 2);

/**
 * Start the transaction as "START TRANSACTION READ ONLY" with
 * mysqli_begin_transaction.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TRANS_START_READ_ONLY', 4);

/**
 * Appends "AND CHAIN" to mysqli_commit or
 * mysqli_rollback.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TRANS_COR_AND_CHAIN', 1);

/**
 * Appends "AND NO CHAIN" to mysqli_commit or
 * mysqli_rollback.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TRANS_COR_AND_NO_CHAIN', 2);

/**
 * Appends "RELEASE" to mysqli_commit or
 * mysqli_rollback.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TRANS_COR_RELEASE', 4);

/**
 * Appends "NO RELEASE" to mysqli_commit or
 * mysqli_rollback.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_TRANS_COR_NO_RELEASE', 8);

/**
 * Whether the mysqli extension has been built against a MariaDB client library.
 * Available as of PHP 8.1.2.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_IS_MARIADB', false);

// End of mysqli v.8.2.6
