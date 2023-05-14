<?php

// Start of mysqli v.8.0.28

/**
 * The mysqli exception handling class.
 * @link http://www.php.net/manual/en/class.mysqli_sql_exception.php
 */
final class mysqli_sql_exception extends RuntimeException implements Stringable, Throwable {
	protected $message;
	protected $file;
	protected $line;
	protected $code;
	protected $sqlstate;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param ?Throwable|null $previous [optional]
	 */
	public function __construct (string $message = ''int , $code = 0?Throwable|null , $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ??Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

/**
 * The mysqli_driver class is an instance of the monostate
 * pattern, i.e. there is only one driver which can be accessed though an arbitrary
 * amount of mysqli_driver instances.
 * @link http://www.php.net/manual/en/class.mysqli_driver.php
 */
final class mysqli_driver  {
	public $client_info;
	public $client_version;
	public $driver_version;
	public $reconnect;
	public $report_mode;

}

/**
 * Represents a connection between PHP and a MySQL database.
 * @link http://www.php.net/manual/en/class.mysqli.php
 */
class mysqli  {
	public $affected_rows;
	public $client_info;
	public $client_version;
	public $connect_errno;
	public $connect_error;
	public $errno;
	public $error;
	public $error_list;
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


	/**
	 * Open a new connection to the MySQL server
	 * @link http://www.php.net/manual/en/mysqli.construct.php
	 * @param mixed $hostname [optional] <p>
	 * Can be either a host name or an IP address. When passing null, the value is retrieved from
	 * mysqli.default_host.
	 * When possible, pipes will be used instead of the TCP/IP protocol.
	 * The TCP/IP protocol is used if a host name and port number are provided together e.g. localhost:3308.
	 * </p>
	 * <p>
	 * Prepending host by p: opens a persistent connection.
	 * mysqli_change_user is automatically called on
	 * connections opened from the connection pool.
	 * </p>
	 * @param mixed $username [optional] The MySQL username or null to assume the username based on the
	 * mysqli.default_user ini option.
	 * @param mixed $password [optional] The MySQL password or null to assume the password based on the
	 * mysqli.default_pw ini option.
	 * @param mixed $database [optional] The default database to be used when performing queries or null.
	 * @param mixed $port [optional] The port number to attempt to connect to the MySQL server or null to assume the port based on the
	 * mysqli.default_port ini option.
	 * @param mixed $socket [optional] <p>
	 * The socket or named pipe that should be used or null to assume the socket based on the
	 * mysqli.default_socket ini option.
	 * </p>
	 * <p>
	 * Specifying the socket parameter will not
	 * explicitly determine the type of connection to be used when
	 * connecting to the MySQL server. How the connection is made to the
	 * MySQL database is determined by the hostname
	 * parameter.
	 * </p>
	 * @return bool mysqli::__construct always returns an object which represents the connection to a MySQL Server, 
	 * regardless of it being successful or not.
	 * <p>
	 * mysqli_connect returns an object which represents the connection to a MySQL Server,
	 * or false on failure.
	 * </p>
	 * <p>
	 * mysqli::connect returns null on success or false on failure.
	 * </p>
	 */
	public function __construct ($hostname = null, $username = null, $password = null, $database = null, $port = null, $socket = null) {}

	/**
	 * Turns on or off auto-committing database modifications
	 * @link http://www.php.net/manual/en/mysqli.autocommit.php
	 * @param bool $enable Whether to turn on auto-commit or not.
	 * @return bool true on success or false on failure
	 */
	public function autocommit (bool $enable) {}

	/**
	 * Starts a transaction
	 * @link http://www.php.net/manual/en/mysqli.begin-transaction.php
	 * @param int $flags [optional] <p>
	 * Valid flags are:
	 * </p>
	 * <p>
	 * <br>
	 * <p>
	 * MYSQLI_TRANS_START_READ_ONLY: 
	 * Start the transaction as "START TRANSACTION READ ONLY".
	 * Requires MySQL 5.6 and above.
	 * </p>
	 * <br>
	 * <p>
	 * MYSQLI_TRANS_START_READ_WRITE: 
	 * Start the transaction as "START TRANSACTION READ WRITE".
	 * Requires MySQL 5.6 and above.
	 * </p>
	 * <br>
	 * <p>
	 * MYSQLI_TRANS_START_WITH_CONSISTENT_SNAPSHOT: 
	 * Start the transaction as "START TRANSACTION WITH CONSISTENT SNAPSHOT".
	 * </p>
	 * </p>
	 * @param mixed $name [optional] Savepoint name for the transaction.
	 * @return bool true on success or false on failure
	 */
	public function begin_transaction (int $flags = null, $name = null) {}

	/**
	 * Changes the user of the specified database connection
	 * @link http://www.php.net/manual/en/mysqli.change-user.php
	 * @param string $username The MySQL user name.
	 * @param string $password The MySQL password.
	 * @param mixed $database <p>
	 * The database to change to.
	 * </p>
	 * <p>
	 * If desired, the null value may be passed resulting in only changing
	 * the user and not selecting a database. To select a database in this
	 * case use the mysqli_select_db function.
	 * </p>
	 * @return bool true on success or false on failure
	 */
	public function change_user (string $username, string $password, $database) {}

	/**
	 * Returns the current character set of the database connection
	 * @link http://www.php.net/manual/en/mysqli.character-set-name.php
	 * @return string The current character set of the connection
	 */
	public function character_set_name () {}

	/**
	 * Closes a previously opened database connection
	 * @link http://www.php.net/manual/en/mysqli.close.php
	 * @return true Always returns true.
	 */
	public function close () {}

	/**
	 * Commits the current transaction
	 * @link http://www.php.net/manual/en/mysqli.commit.php
	 * @param int $flags [optional] A bitmask of MYSQLI_TRANS_COR_&#42; constants.
	 * @param mixed $name [optional] If provided then COMMIT/&#42;name&#42;/ is executed.
	 * @return bool true on success or false on failure
	 */
	public function commit (int $flags = null, $name = null) {}

	/**
	 * @param ?string|null $hostname [optional]
	 * @param ?string|null $username [optional]
	 * @param ?string|null $password [optional]
	 * @param ?string|null $database [optional]
	 * @param ?int|null $port [optional]
	 * @param ?string|null $socket [optional]
	 */
	public function connect (?string|null $hostname = null?string|null , $username = null?string|null , $password = null?string|null , $database = null?int|null , $port = null?string|null , $socket = null) {}

	/**
	 * Dump debugging information into the log
	 * @link http://www.php.net/manual/en/mysqli.dump-debug-info.php
	 * @return bool true on success or false on failure
	 */
	public function dump_debug_info () {}

	/**
	 * Performs debugging operations
	 * @link http://www.php.net/manual/en/mysqli.debug.php
	 * @param string $options <p>
	 * A string representing the debugging operation to perform
	 * </p>
	 * <p>
	 * The debug control string is a sequence of colon separated fields as follows:
	 * <pre>::</pre>
	 * Each field consists of a mandatory flag character followed by an optional <p>flag[,modifier,modifier,...,modifier]
	 * </p>
	 * <p>
	 * <table>
	 * Recognized Flag Characters
	 * <table>
	 * <tr valign="top">
	 * <td>option character</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>O</td>
	 * <td>MYSQLND_DEBUG_FLUSH</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>A/a</td>
	 * <td>MYSQLND_DEBUG_APPEND</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>F</td>
	 * <td>MYSQLND_DEBUG_DUMP_FILE</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>i</td>
	 * <td>MYSQLND_DEBUG_DUMP_PID</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>L</td>
	 * <td>MYSQLND_DEBUG_DUMP_LINE</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>m</td>
	 * <td>MYSQLND_DEBUG_TRACE_MEMORY_CALLS</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>n</td>
	 * <td>MYSQLND_DEBUG_DUMP_LEVEL</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>o</td>
	 * <td>output to file</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>T</td>
	 * <td>MYSQLND_DEBUG_DUMP_TIME</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>t</td>
	 * <td>MYSQLND_DEBUG_DUMP_TRACE</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>x</td>
	 * <td>MYSQLND_DEBUG_PROFILE_CALLS</td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * @return true Always returns true.
	 */
	public function debug (string $options) {}

	/**
	 * Returns a character set object
	 * @link http://www.php.net/manual/en/mysqli.get-charset.php
	 * @return mixed The function returns a character set object with the following properties:
	 * <p>
	 * charset
	 * <br><p>Character set name
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
	 * </p>
	 */
	public function get_charset () {}

	public function get_client_info () {}

	/**
	 * Returns statistics about the client connection
	 * @link http://www.php.net/manual/en/mysqli.get-connection-stats.php
	 * @return array an array with connection stats.
	 */
	public function get_connection_stats () {}

	public function get_server_info () {}

	/**
	 * Get result of SHOW WARNINGS
	 * @link http://www.php.net/manual/en/mysqli.get-warnings.php
	 * @return mixed 
	 */
	public function get_warnings () {}

	/**
	 * Initializes MySQLi and returns an object for use with mysqli_real_connect()
	 * @link http://www.php.net/manual/en/mysqli.init.php
	 * @return mixed mysqli::init returns null on success, or false on failure.
	 * mysqli_init returns an object on success, or false on failure.
	 */
	public function init () {}

	/**
	 * Asks the server to kill a MySQL thread
	 * @link http://www.php.net/manual/en/mysqli.kill.php
	 * @param int $process_id 
	 * @return bool true on success or false on failure
	 */
	public function kill (int $process_id) {}

	/**
	 * Performs one or more queries on the database
	 * @link http://www.php.net/manual/en/mysqli.multi-query.php
	 * @param string $query A string containing the queries to be executed.
	 * Multiple queries must be separated by a semicolon.
	 * @return bool false if the first statement failed.
	 * To retrieve subsequent errors from other statements you have to call
	 * mysqli_next_result first.
	 */
	public function multi_query (string $query) {}

	/**
	 * Check if there are any more query results from a multi query
	 * @link http://www.php.net/manual/en/mysqli.more-results.php
	 * @return bool true if one or more result sets (including errors) are available from a previous call to
	 * mysqli_multi_query, otherwise false.
	 */
	public function more_results () {}

	/**
	 * Prepare next result from multi_query
	 * @link http://www.php.net/manual/en/mysqli.next-result.php
	 * @return bool true on success or false on failure Also returns false if the next statement resulted in an error, unlike mysqli_more_results.
	 */
	public function next_result () {}

	/**
	 * Pings a server connection, or tries to reconnect if the connection has gone down
	 * @link http://www.php.net/manual/en/mysqli.ping.php
	 * @return bool true on success or false on failure
	 */
	public function ping () {}

	/**
	 * Poll connections
	 * @link http://www.php.net/manual/en/mysqli.poll.php
	 * @param mixed $read List of connections to check for outstanding results that can be read.
	 * @param mixed $error List of connections on which an error occurred, for example, query
	 * failure or lost connection.
	 * @param array $reject List of connections rejected because no asynchronous query
	 * has been run on for which the function could poll results.
	 * @param int $seconds Maximum number of seconds to wait, must be non-negative.
	 * @param int $microseconds [optional] Maximum number of microseconds to wait, must be non-negative.
	 * @return mixed number of ready connections upon success, false otherwise.
	 */
	public static function poll (&$read, &$error, array &$reject, int $seconds, int $microseconds = null) {}

	/**
	 * Prepares an SQL statement for execution
	 * @link http://www.php.net/manual/en/mysqli.prepare.php
	 * @param string $query <p>
	 * The query, as a string. It must consist of a single SQL statement.
	 * </p>
	 * <p>
	 * The SQL statement may contain zero or more parameter markers
	 * represented by question mark (?) characters
	 * at the appropriate positions.
	 * </p>
	 * <p>
	 * The markers are legal only in certain places in SQL statements.
	 * For example, they are permitted in the VALUES()
	 * list of an INSERT statement (to specify column
	 * values for a row), or in a comparison with a column in a
	 * WHERE clause to specify a comparison value.
	 * However, they are not permitted for identifiers (such as table or
	 * column names).
	 * </p>
	 * @return mixed mysqli_prepare returns a statement object or false if an error occurred.
	 */
	public function prepare (string $query) {}

	/**
	 * Performs a query on the database
	 * @link http://www.php.net/manual/en/mysqli.query.php
	 * @param string $query The query string.
	 * @param int $result_mode [optional] <p>
	 * The result mode can be one of 3 constants indicating how the result will
	 * be returned from the MySQL server.
	 * </p>
	 * <p>
	 * MYSQLI_STORE_RESULT (default) - returns a
	 * mysqli_result object with buffered result set.
	 * </p>
	 * <p>
	 * MYSQLI_USE_RESULT - returns a
	 * mysqli_result object with unbuffered result set. 
	 * As long as there are pending records waiting to be fetched, the
	 * connection line will be busy and all subsequent calls will return error 
	 * Commands out of sync. To avoid the error all records 
	 * must be fetched from the server or the result set must be discarded by
	 * calling mysqli_free_result.
	 * </p>
	 * <p>
	 * MYSQLI_ASYNC (available with mysqlnd) - the query is
	 * performed asynchronously and no result set is immediately returned.
	 * mysqli_poll is then used to get results from such
	 * queries. Used in combination with either
	 * MYSQLI_STORE_RESULT or
	 * MYSQLI_USE_RESULT constant.
	 * </p>
	 * @return mixed false on failure. For successful queries which produce a result
	 * set, such as SELECT, SHOW, DESCRIBE or
	 * EXPLAIN, mysqli_query will return
	 * a mysqli_result object. For other successful queries,
	 * mysqli_query will
	 * return true.
	 */
	public function query (string $query, int $result_mode = null) {}

	/**
	 * Opens a connection to a mysql server
	 * @link http://www.php.net/manual/en/mysqli.real-connect.php
	 * @param mixed $hostname [optional] Can be either a host name or an IP address. When passing null, the value is retrieved from
	 * mysqli.default_host.
	 * When possible, pipes will be used instead of the TCP/IP protocol.
	 * The TCP/IP protocol is used if a host name and port number are provided together e.g. localhost:3308.
	 * @param mixed $username [optional] The MySQL username or null to assume the username based on the
	 * mysqli.default_user ini option.
	 * @param mixed $password [optional] The MySQL password or null to assume the password based on the
	 * mysqli.default_pw ini option.
	 * @param mixed $database [optional] The default database to be used when performing queries or null.
	 * @param mixed $port [optional] The port number to attempt to connect to the MySQL server or null to assume the port based on the
	 * mysqli.default_port ini option.
	 * @param mixed $socket [optional] <p>
	 * The socket or named pipe that should be used or null to assume the socket based on the
	 * mysqli.default_socket ini option.
	 * </p>
	 * <p>
	 * Specifying the socket parameter will not
	 * explicitly determine the type of connection to be used when
	 * connecting to the MySQL server. How the connection is made to the
	 * MySQL database is determined by the hostname
	 * parameter.
	 * </p>
	 * @param int $flags [optional] <p>
	 * With the parameter flags you can set different
	 * connection options:
	 * </p>
	 * <table>
	 * Supported flags
	 * <table>
	 * <tr valign="top">
	 * <td>Name</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_CLIENT_COMPRESS</td>
	 * <td>Use compression protocol</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_CLIENT_FOUND_ROWS</td>
	 * <td>return number of matched rows, not the number of affected rows</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_CLIENT_IGNORE_SPACE</td>
	 * <td>Allow spaces after function names. Makes all function names reserved words.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_CLIENT_INTERACTIVE</td>
	 * <td>
	 * Allow interactive_timeout seconds (instead of
	 * wait_timeout seconds) of inactivity before closing the connection
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_CLIENT_SSL</td>
	 * <td>Use SSL (encryption)</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_CLIENT_SSL_DONT_VERIFY_SERVER_CERT</td>
	 * <td>
	 * Like MYSQLI_CLIENT_SSL, but disables validation of the provided 
	 * SSL certificate. This is only for installations using MySQL Native Driver and MySQL 5.6 or later.
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>
	 * For security reasons the MULTI_STATEMENT flag is
	 * not supported in PHP. If you want to execute multiple queries use the
	 * mysqli_multi_query function.
	 * </p>
	 * @return bool true on success or false on failure
	 */
	public function real_connect ($hostname = null, $username = null, $password = null, $database = null, $port = null, $socket = null, int $flags = null) {}

	/**
	 * Escapes special characters in a string for use in an SQL statement, taking into account the current charset of the connection
	 * @link http://www.php.net/manual/en/mysqli.real-escape-string.php
	 * @param string $string <p>
	 * The string to be escaped.
	 * </p>
	 * <p>
	 * Characters encoded are NUL (ASCII 0), \n, \r, \, ', ", and
	 * Control-Z.
	 * </p>
	 * @return string an escaped string.
	 */
	public function real_escape_string (string $string) {}

	/**
	 * Get result from async query
	 * @link http://www.php.net/manual/en/mysqli.reap-async-query.php
	 * @return mixed false on failure. For successful queries which produce a result set, such as SELECT, SHOW, DESCRIBE or
	 * EXPLAIN, mysqli_reap_async_query will return
	 * a mysqli_result object. For other successful queries, mysqli_reap_async_query will
	 * return true.
	 */
	public function reap_async_query () {}

	/**
	 * Alias: mysqli_real_escape_string
	 * @link http://www.php.net/manual/en/function.mysqli-escape-string.php
	 * @param string $string
	 */
	public function escape_string (string $string) {}

	/**
	 * Execute an SQL query
	 * @link http://www.php.net/manual/en/mysqli.real-query.php
	 * @param string $query The query string.
	 * @return bool true on success or false on failure
	 */
	public function real_query (string $query) {}

	/**
	 * Removes the named savepoint from the set of savepoints of the current transaction
	 * @link http://www.php.net/manual/en/mysqli.release-savepoint.php
	 * @param string $name The identifier of the savepoint.
	 * @return bool true on success or false on failure
	 */
	public function release_savepoint (string $name) {}

	/**
	 * Rolls back current transaction
	 * @link http://www.php.net/manual/en/mysqli.rollback.php
	 * @param int $flags [optional] A bitmask of MYSQLI_TRANS_COR_&#42; constants.
	 * @param mixed $name [optional] If provided then ROLLBACK/&#42;name&#42;/ is executed.
	 * @return bool true on success or false on failure
	 */
	public function rollback (int $flags = null, $name = null) {}

	/**
	 * Set a named transaction savepoint
	 * @link http://www.php.net/manual/en/mysqli.savepoint.php
	 * @param string $name The identifier of the savepoint.
	 * @return bool true on success or false on failure
	 */
	public function savepoint (string $name) {}

	/**
	 * Selects the default database for database queries
	 * @link http://www.php.net/manual/en/mysqli.select-db.php
	 * @param string $database The database name.
	 * @return bool true on success or false on failure
	 */
	public function select_db (string $database) {}

	/**
	 * Sets the client character set
	 * @link http://www.php.net/manual/en/mysqli.set-charset.php
	 * @param string $charset The desired character set.
	 * @return bool true on success or false on failure
	 */
	public function set_charset (string $charset) {}

	/**
	 * Set options
	 * @link http://www.php.net/manual/en/mysqli.options.php
	 * @param int $option The option that you want to set. It can be one of the following values:
	 * <table>
	 * Valid options
	 * <table>
	 * <tr valign="top">
	 * <td>Name</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_OPT_CONNECT_TIMEOUT</td>
	 * <td>Connection timeout in seconds</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_OPT_READ_TIMEOUT</td>
	 * <td>Command execution result timeout in seconds. Available as of PHP 7.2.0.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_OPT_LOCAL_INFILE</td>
	 * <td>Enable/disable use of LOAD LOCAL INFILE</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_INIT_COMMAND</td>
	 * <td>Command to execute after when connecting to MySQL server</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_SET_CHARSET_NAME</td>
	 * <td>The charset to be set as default.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_READ_DEFAULT_FILE</td>
	 * <td>
	 * Read options from named option file instead of my.cnf
	 * Not supported by mysqlnd.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_READ_DEFAULT_GROUP</td>
	 * <td>
	 * Read options from the named group from my.cnf
	 * or the file specified with MYSQL_READ_DEFAULT_FILE.
	 * Not supported by mysqlnd.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_SERVER_PUBLIC_KEY</td>
	 * <td>
	 * RSA public key file used with the SHA-256 based authentication.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_OPT_NET_CMD_BUFFER_SIZE</td>
	 * <td>
	 * The size of the internal command/network buffer. Only valid for
	 * mysqlnd.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_OPT_NET_READ_BUFFER_SIZE</td>
	 * <td>
	 * Maximum read chunk size in bytes when reading the body of a MySQL
	 * command packet. Only valid for mysqlnd.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_OPT_INT_AND_FLOAT_NATIVE</td>
	 * <td>
	 * Convert integer and float columns back to PHP numbers. Only valid
	 * for mysqlnd.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_OPT_SSL_VERIFY_SERVER_CERT</td>
	 * <td>
	 * Whether to verify server certificate or not.
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * @param mixed $value The value for the option.
	 * @return bool true on success or false on failure
	 */
	public function options (int $option, $value) {}

	/**
	 * Alias: mysqli_options
	 * @link http://www.php.net/manual/en/function.mysqli-set-opt.php
	 * @param int $option
	 * @param mixed $value
	 */
	public function set_opt (int $option, $value = null) {}

	/**
	 * Used for establishing secure connections using SSL
	 * @link http://www.php.net/manual/en/mysqli.ssl-set.php
	 * @param mixed $key The path name to the key file.
	 * @param mixed $certificate The path name to the certificate file.
	 * @param mixed $ca_certificate The path name to the certificate authority file.
	 * @param mixed $ca_path The pathname to a directory that contains trusted SSL CA certificates
	 * in PEM format.
	 * @param mixed $cipher_algos A list of allowable ciphers to use for SSL encryption.
	 * @return true Always returns true. If SSL setup is
	 * incorrect mysqli_real_connect will return an error
	 * when you attempt to connect.
	 */
	public function ssl_set ($key, $certificate, $ca_certificate, $ca_path, $cipher_algos) {}

	/**
	 * Gets the current system status
	 * @link http://www.php.net/manual/en/mysqli.stat.php
	 * @return mixed A string describing the server status. false if an error occurred.
	 */
	public function stat () {}

	/**
	 * Initializes a statement and returns an object for use with mysqli_stmt_prepare
	 * @link http://www.php.net/manual/en/mysqli.stmt-init.php
	 * @return mixed an object.
	 */
	public function stmt_init () {}

	/**
	 * Transfers a result set from the last query
	 * @link http://www.php.net/manual/en/mysqli.store-result.php
	 * @param int $mode [optional] The option that you want to set. As of PHP 8.1, this parameter has no effect. It can be one of the following values:
	 * <table>
	 * Valid options
	 * <table>
	 * <tr valign="top">
	 * <td>Name</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_STORE_RESULT_COPY_DATA</td>
	 * <td>Copy results from the internal mysqlnd buffer into the PHP variables fetched. By default,
	 * mysqlnd will use a reference logic to avoid copying and duplicating results held in memory.
	 * For certain result sets, for example, result sets with many small rows, the copy approach can
	 * reduce the overall memory usage because PHP variables holding results may be
	 * released earlier (available with mysqlnd only)</td>
	 * </tr>
	 * </table>
	 * </table>
	 * @return mixed a buffered result object or false if an error occurred.
	 * <p>
	 * mysqli_store_result returns false in case the query
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
	 * statement should have produced a non-empty result set.
	 * </p>
	 */
	public function store_result (int $mode = null) {}

	/**
	 * Returns whether thread safety is given or not
	 * @link http://www.php.net/manual/en/mysqli.thread-safe.php
	 * @return bool true if the client library is thread-safe, otherwise false.
	 */
	public function thread_safe () {}

	/**
	 * Initiate a result set retrieval
	 * @link http://www.php.net/manual/en/mysqli.use-result.php
	 * @return mixed an unbuffered result object or false if an error occurred.
	 */
	public function use_result () {}

	/**
	 * Refreshes
	 * @link http://www.php.net/manual/en/mysqli.refresh.php
	 * @param int $flags <p>
	 * The options to refresh, using the MYSQLI_REFRESH_&#42; constants as documented
	 * within the MySQLi constants documentation.
	 * </p>
	 * <p>
	 * See also the official MySQL Refresh
	 * documentation.
	 * </p>
	 * @return bool true if the refresh was a success, otherwise false
	 */
	public function refresh (int $flags) {}

}

/**
 * Represents a MySQL warning.
 * @link http://www.php.net/manual/en/class.mysqli_warning.php
 */
final class mysqli_warning  {
	public $message;
	public $sqlstate;
	public $errno;


	/**
	 * Private constructor to disallow direct instantiation
	 * @link http://www.php.net/manual/en/mysqli-warning.construct.php
	 */
	private function __construct () {}

	/**
	 * Fetch next warning
	 * @link http://www.php.net/manual/en/mysqli-warning.next.php
	 * @return bool true if next warning was fetched successfully.
	 * If there are no more warnings, it will return false
	 */
	public function next (): bool {}

}

/**
 * Represents the result set obtained from a query against the database.
 * @link http://www.php.net/manual/en/class.mysqli_result.php
 */
class mysqli_result implements IteratorAggregate, Traversable {
	public $current_field;
	public $field_count;
	public $lengths;
	public $num_rows;
	public $type;


	/**
	 * Constructs a mysqli_result object
	 * @link http://www.php.net/manual/en/mysqli-result.construct.php
	 * @param mysqli $mysql
	 * @param int $result_mode [optional]
	 */
	public function __construct (mysqli $mysqlint , $result_mode = 0) {}

	public function close () {}

	/**
	 * Frees the memory associated with a result
	 * @link http://www.php.net/manual/en/mysqli-result.free.php
	 * @return void 
	 */
	public function free () {}

	/**
	 * Adjusts the result pointer to an arbitrary row in the result
	 * @link http://www.php.net/manual/en/mysqli-result.data-seek.php
	 * @param int $offset The row offset. Must be between zero and the total number of rows
	 * minus one (0..mysqli_num_rows - 1).
	 * @return bool true on success or false on failure
	 */
	public function data_seek (int $offset) {}

	/**
	 * Returns the next field in the result set
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-field.php
	 * @return mixed an object which contains field definition information or false
	 * if no field information is available.
	 * <p>
	 * <table>
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
	 * </table>
	 * </p>
	 */
	public function fetch_field () {}

	/**
	 * Returns an array of objects representing the fields in a result set
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-fields.php
	 * @return array an array of objects containing field definition information.
	 * <p>
	 * <table>
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
	 * </table>
	 * </p>
	 */
	public function fetch_fields () {}

	/**
	 * Fetch meta-data for a single field
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-field-direct.php
	 * @param int $index The field number. This value must be in the range from 
	 * 0 to number of fields - 1.
	 * @return mixed an object which contains field definition information or false
	 * if no field information for specified fieldnr is 
	 * available.
	 * <p>
	 * <table>
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
	 * </table>
	 * </p>
	 */
	public function fetch_field_direct (int $index) {}

	/**
	 * Fetch all result rows as an associative array, a numeric array, or both
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-all.php
	 * @param int $mode [optional] This optional parameter is a constant indicating what type of array
	 * should be produced from the current row data. The possible values for
	 * this parameter are the constants MYSQLI_ASSOC,
	 * MYSQLI_NUM, or MYSQLI_BOTH.
	 * @return array an array of associative or numeric arrays holding result rows.
	 */
	public function fetch_all (int $mode = null) {}

	/**
	 * Fetch the next row of a result set as an associative, a numeric array, or both
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-array.php
	 * @param int $mode [optional] <p>
	 * This optional parameter is a constant indicating what type of array
	 * should be produced from the current row data. The possible values for
	 * this parameter are the constants MYSQLI_ASSOC,
	 * MYSQLI_NUM, or MYSQLI_BOTH.
	 * </p>
	 * <p>
	 * By using the MYSQLI_ASSOC constant this function
	 * will behave identically to the mysqli_fetch_assoc,
	 * while MYSQLI_NUM will behave identically to the
	 * mysqli_fetch_row function. The final option 
	 * MYSQLI_BOTH will create a single array with the
	 * attributes of both.
	 * </p>
	 * @return mixed an array representing the fetched row, null if there
	 * are no more rows in the result set, or false on failure.
	 */
	public function fetch_array (int $mode = null) {}

	/**
	 * Fetch the next row of a result set as an associative array
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-assoc.php
	 * @return mixed an associative array representing the fetched row,
	 * where each key in the array represents the name of one of the result
	 * set's columns, null if there
	 * are no more rows in the result set, or false on failure.
	 */
	public function fetch_assoc () {}

	/**
	 * Fetch the next row of a result set as an object
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-object.php
	 * @param string $class [optional] The name of the class to instantiate, set the properties of and return.
	 * If not specified, a stdClass object is returned.
	 * @param array $constructor_args [optional] An optional array of parameters to pass to the constructor
	 * for class objects.
	 * @return mixed an object representing the fetched row, where each property
	 * represents the name of the result set's column, null if there
	 * are no more rows in the result set, or false on failure.
	 */
	public function fetch_object (string $class = null, array $constructor_args = null) {}

	/**
	 * Fetch the next row of a result set as an enumerated array
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-row.php
	 * @return mixed an enumerated array representing the fetched row, null if there
	 * are no more rows in the result set, or false on failure.
	 */
	public function fetch_row () {}

	/**
	 * Set result pointer to a specified field offset
	 * @link http://www.php.net/manual/en/mysqli-result.field-seek.php
	 * @param int $index The field number. This value must be in the range from 
	 * 0 to number of fields - 1.
	 * @return bool true on success or false on failure
	 */
	public function field_seek (int $index) {}

	public function free_result () {}

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
	public $affected_rows;
	public $insert_id;
	public $num_rows;
	public $param_count;
	public $field_count;
	public $errno;
	public $error;
	public $error_list;
	public $sqlstate;
	public $id;


	/**
	 * Constructs a new mysqli_stmt object
	 * @link http://www.php.net/manual/en/mysqli-stmt.construct.php
	 * @param mysqli $mysql
	 * @param ?string|null $query [optional]
	 */
	public function __construct (mysqli $mysql?string|null , $query = null) {}

	/**
	 * Used to get the current value of a statement attribute
	 * @link http://www.php.net/manual/en/mysqli-stmt.attr-get.php
	 * @param int $attribute The attribute that you want to get.
	 * @return int the value of the attribute.
	 */
	public function attr_get (int $attribute) {}

	/**
	 * Used to modify the behavior of a prepared statement
	 * @link http://www.php.net/manual/en/mysqli-stmt.attr-set.php
	 * @param int $attribute <p>
	 * The attribute that you want to set. It can have one of the following values:
	 * <table>
	 * Attribute values
	 * <table>
	 * <tr valign="top">
	 * <td>Character</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_STMT_ATTR_UPDATE_MAX_LENGTH</td>
	 * <td>
	 * Setting to true causes mysqli_stmt_store_result to
	 * update the metadata MYSQL_FIELD-&gt;max_length value.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_STMT_ATTR_CURSOR_TYPE</td>
	 * <td>
	 * Type of cursor to open for statement when mysqli_stmt_execute
	 * is invoked. value can be MYSQLI_CURSOR_TYPE_NO_CURSOR
	 * (the default) or MYSQLI_CURSOR_TYPE_READ_ONLY.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_STMT_ATTR_PREFETCH_ROWS</td>
	 * <td>
	 * Number of rows to fetch from server at a time when using a cursor.
	 * value can be in the range from 1 to the maximum
	 * value of unsigned long. The default is 1.
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * <p>
	 * If you use the MYSQLI_STMT_ATTR_CURSOR_TYPE option with
	 * MYSQLI_CURSOR_TYPE_READ_ONLY, a cursor is opened for the
	 * statement when you invoke mysqli_stmt_execute. If there
	 * is already an open cursor from a previous mysqli_stmt_execute call,
	 * it closes the cursor before opening a new one. mysqli_stmt_reset
	 * also closes any open cursor before preparing the statement for re-execution.
	 * mysqli_stmt_free_result closes any open cursor.
	 * </p>
	 * <p>
	 * If you open a cursor for a prepared statement, mysqli_stmt_store_result
	 * is unnecessary.
	 * </p>
	 * @param int $value The value to assign to the attribute.
	 * @return bool true on success or false on failure
	 */
	public function attr_set (int $attribute, int $value) {}

	/**
	 * Binds variables to a prepared statement as parameters
	 * @link http://www.php.net/manual/en/mysqli-stmt.bind-param.php
	 * @param string $types A string that contains one or more characters which specify the types
	 * for the corresponding bind variables:
	 * <table>
	 * Type specification chars
	 * <table>
	 * <tr valign="top">
	 * <td>Character</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>i</td>
	 * <td>corresponding variable has type int</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>d</td>
	 * <td>corresponding variable has type float</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>s</td>
	 * <td>corresponding variable has type string</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>b</td>
	 * <td>corresponding variable is a blob and will be sent in packets</td>
	 * </tr>
	 * </table>
	 * </table>
	 * @param mixed $var The number of variables and length of string 
	 * types must match the parameters in the statement.
	 * @param mixed $vars 
	 * @return bool true on success or false on failure
	 */
	public function bind_param (string $types, &$var, &$vars) {}

	/**
	 * Binds variables to a prepared statement for result storage
	 * @link http://www.php.net/manual/en/mysqli-stmt.bind-result.php
	 * @param mixed $var The first variable to be bound.
	 * @param mixed $vars Further variables to be bound.
	 * @return bool true on success or false on failure
	 */
	public function bind_result (&$var, &$vars) {}

	/**
	 * Closes a prepared statement
	 * @link http://www.php.net/manual/en/mysqli-stmt.close.php
	 * @return true Always returns true.
	 */
	public function close () {}

	/**
	 * Adjusts the result pointer to an arbitrary row in the buffered result
	 * @link http://www.php.net/manual/en/mysqli-stmt.data-seek.php
	 * @param int $offset Must be between zero and the total number of rows minus one (0..
	 * mysqli_stmt_num_rows - 1).
	 * @return void 
	 */
	public function data_seek (int $offset) {}

	/**
	 * Executes a prepared statement
	 * @link http://www.php.net/manual/en/mysqli-stmt.execute.php
	 * @param mixed $params [optional] An optional list array with as many elements as there are bound parameters in the SQL statement being executed. Each value is treated as a string.
	 * @return bool true on success or false on failure
	 */
	public function execute ($params = null) {}

	/**
	 * Fetch results from a prepared statement into the bound variables
	 * @link http://www.php.net/manual/en/mysqli-stmt.fetch.php
	 * @return mixed 
	 */
	public function fetch () {}

	/**
	 * Get result of SHOW WARNINGS
	 * @link http://www.php.net/manual/en/mysqli-stmt.get-warnings.php
	 * @return mixed 
	 */
	public function get_warnings () {}

	/**
	 * Returns result set metadata from a prepared statement
	 * @link http://www.php.net/manual/en/mysqli-stmt.result-metadata.php
	 * @return mixed a result object or false if an error occurred.
	 */
	public function result_metadata () {}

	/**
	 * Check if there are more query results from a multiple query
	 * @link http://www.php.net/manual/en/mysqli-stmt.more-results.php
	 * @return bool true if more results exist, otherwise false.
	 */
	public function more_results () {}

	/**
	 * Reads the next result from a multiple query
	 * @link http://www.php.net/manual/en/mysqli-stmt.next-result.php
	 * @return bool true on success or false on failure
	 */
	public function next_result () {}

	public function num_rows () {}

	/**
	 * Send data in blocks
	 * @link http://www.php.net/manual/en/mysqli-stmt.send-long-data.php
	 * @param int $param_num Indicates which parameter to associate the data with. Parameters are
	 * numbered beginning with 0.
	 * @param string $data A string containing data to be sent.
	 * @return bool true on success or false on failure
	 */
	public function send_long_data (int $param_num, string $data) {}

	/**
	 * Frees stored result memory for the given statement handle
	 * @link http://www.php.net/manual/en/mysqli-stmt.free-result.php
	 * @return void 
	 */
	public function free_result () {}

	/**
	 * Resets a prepared statement
	 * @link http://www.php.net/manual/en/mysqli-stmt.reset.php
	 * @return bool true on success or false on failure
	 */
	public function reset () {}

	/**
	 * Prepares an SQL statement for execution
	 * @link http://www.php.net/manual/en/mysqli-stmt.prepare.php
	 * @param string $query <p>
	 * The query, as a string. It must consist of a single SQL statement.
	 * </p>
	 * <p>
	 * The SQL statement may contain zero or more parameter markers
	 * represented by question mark (?) characters
	 * at the appropriate positions.
	 * </p>
	 * <p>
	 * The markers are legal only in certain places in SQL statements.
	 * For example, they are permitted in the VALUES()
	 * list of an INSERT statement (to specify column
	 * values for a row), or in a comparison with a column in a
	 * WHERE clause to specify a comparison value.
	 * However, they are not permitted for identifiers (such as table or
	 * column names).
	 * </p>
	 * @return bool true on success or false on failure
	 */
	public function prepare (string $query) {}

	/**
	 * Stores a result set in an internal buffer
	 * @link http://www.php.net/manual/en/mysqli-stmt.store-result.php
	 * @return bool true on success or false on failure
	 */
	public function store_result () {}

	/**
	 * Gets a result set from a prepared statement as a mysqli_result object
	 * @link http://www.php.net/manual/en/mysqli-stmt.get-result.php
	 * @return mixed false on failure. For successful queries which produce a result set, such as SELECT, SHOW, DESCRIBE or
	 * EXPLAIN, mysqli_stmt_get_result will return
	 * a mysqli_result object. For other successful queries, mysqli_stmt_get_result will
	 * return false. The mysqli_stmt_errno function can be
	 * used to distinguish between the two reasons for false; due to a bug, prior to PHP 7.4.13,
	 * mysqli_errno had to be used for this purpose.
	 */
	public function get_result () {}

}

/**
 * @param mysqli $mysql
 */
function mysqli_affected_rows (mysqli $mysql): string|int {}

/**
 * @param mysqli $mysql
 * @param bool $enable
 */
function mysqli_autocommit (mysqli $mysqlbool , $enable): bool {}

/**
 * @param mysqli $mysql
 * @param int $flags [optional]
 * @param ?string|null $name [optional]
 */
function mysqli_begin_transaction (mysqli $mysqlint , $flags = 0?string|null , $name = null): bool {}

/**
 * @param mysqli $mysql
 * @param string $username
 * @param string $password
 * @param ?string|null $database
 */
function mysqli_change_user (mysqli $mysqlstring , $usernamestring , $password?string|null , $database = null): bool {}

/**
 * @param mysqli $mysql
 */
function mysqli_character_set_name (mysqli $mysql): string {}

/**
 * @param mysqli $mysql
 */
function mysqli_close (mysqli $mysql): bool {}

/**
 * @param mysqli $mysql
 * @param int $flags [optional]
 * @param ?string|null $name [optional]
 */
function mysqli_commit (mysqli $mysqlint , $flags = 0?string|null , $name = null): bool {}

/**
 * Alias: mysqli::__construct
 * @link http://www.php.net/manual/en/function.mysqli-connect.php
 * @param ?string|null $hostname [optional]
 * @param ?string|null $username [optional]
 * @param ?string|null $password [optional]
 * @param ?string|null $database [optional]
 * @param ?int|null $port [optional]
 * @param ?string|null $socket [optional]
 */
function mysqli_connect (?string|null $hostname = null?string|null , $username = null?string|null , $password = null?string|null , $database = null?int|null , $port = null?string|null , $socket = null): mysqli|false {}

function mysqli_connect_errno (): int {}

function mysqli_connect_error (): ??string {}

/**
 * @param mysqli_result $result
 * @param int $offset
 */
function mysqli_data_seek (mysqli_result $resultint , $offset): bool {}

/**
 * @param mysqli $mysql
 */
function mysqli_dump_debug_info (mysqli $mysql): bool {}

/**
 * @param string $options
 */
function mysqli_debug (string $options): bool {}

/**
 * @param mysqli $mysql
 */
function mysqli_errno (mysqli $mysql): int {}

/**
 * @param mysqli $mysql
 */
function mysqli_error (mysqli $mysql): string {}

/**
 * @param mysqli $mysql
 */
function mysqli_error_list (mysqli $mysql): array {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_execute (mysqli_stmt $statement): bool {}

/**
 * Alias: mysqli_stmt_execute
 * @link http://www.php.net/manual/en/function.mysqli-execute.php
 * @param mysqli_stmt $statement
 */
function mysqli_execute (mysqli_stmt $statement): bool {}

/**
 * @param mysqli_result $result
 */
function mysqli_fetch_field (mysqli_result $result): object|false {}

/**
 * @param mysqli_result $result
 */
function mysqli_fetch_fields (mysqli_result $result): array {}

/**
 * @param mysqli_result $result
 * @param int $index
 */
function mysqli_fetch_field_direct (mysqli_result $resultint , $index): object|false {}

/**
 * @param mysqli_result $result
 */
function mysqli_fetch_lengths (mysqli_result $result): array|false {}

/**
 * @param mysqli_result $result
 * @param int $mode [optional]
 */
function mysqli_fetch_all (mysqli_result $resultint , $mode = 2): array {}

/**
 * @param mysqli_result $result
 * @param int $mode [optional]
 */
function mysqli_fetch_array (mysqli_result $resultint , $mode = 3): ?array|false|null {}

/**
 * @param mysqli_result $result
 */
function mysqli_fetch_assoc (mysqli_result $result): ?array|false|null {}

/**
 * @param mysqli_result $result
 * @param string $class [optional]
 * @param array[] $constructor_args [optional]
 */
function mysqli_fetch_object (mysqli_result $resultstring , $class = 'stdClass'array , $constructor_args = 'Array'): ?object|false|null {}

/**
 * @param mysqli_result $result
 */
function mysqli_fetch_row (mysqli_result $result): ?array|false|null {}

/**
 * @param mysqli $mysql
 */
function mysqli_field_count (mysqli $mysql): int {}

/**
 * @param mysqli_result $result
 * @param int $index
 */
function mysqli_field_seek (mysqli_result $resultint , $index): bool {}

/**
 * @param mysqli_result $result
 */
function mysqli_field_tell (mysqli_result $result): int {}

/**
 * @param mysqli_result $result
 */
function mysqli_free_result (mysqli_result $result): void {}

/**
 * @param mysqli $mysql
 */
function mysqli_get_connection_stats (mysqli $mysql): array {}

/**
 * Returns client per-process statistics
 * @link http://www.php.net/manual/en/function.mysqli-get-client-stats.php
 * @return array an array with client stats.
 */
function mysqli_get_client_stats (): array {}

/**
 * @param mysqli $mysql
 */
function mysqli_get_charset (mysqli $mysql): ??object {}

/**
 * @param ?mysqli|null $mysql [optional]
 */
function mysqli_get_client_info (?mysqli|null $mysql = null): string {}

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
 * </p>
 */
function mysqli_get_links_stats (): array {}

/**
 * @param mysqli $mysql
 */
function mysqli_get_host_info (mysqli $mysql): string {}

/**
 * @param mysqli $mysql
 */
function mysqli_get_proto_info (mysqli $mysql): int {}

/**
 * @param mysqli $mysql
 */
function mysqli_get_server_info (mysqli $mysql): string {}

/**
 * @param mysqli $mysql
 */
function mysqli_get_server_version (mysqli $mysql): int {}

/**
 * @param mysqli $mysql
 */
function mysqli_get_warnings (mysqli $mysql): mysqli_warning|false {}

function mysqli_init (): mysqli|false {}

/**
 * @param mysqli $mysql
 */
function mysqli_info (mysqli $mysql): ??string {}

/**
 * @param mysqli $mysql
 */
function mysqli_insert_id (mysqli $mysql): string|int {}

/**
 * @param mysqli $mysql
 * @param int $process_id
 */
function mysqli_kill (mysqli $mysqlint , $process_id): bool {}

/**
 * @param mysqli $mysql
 */
function mysqli_more_results (mysqli $mysql): bool {}

/**
 * @param mysqli $mysql
 * @param string $query
 */
function mysqli_multi_query (mysqli $mysqlstring , $query): bool {}

/**
 * @param mysqli $mysql
 */
function mysqli_next_result (mysqli $mysql): bool {}

/**
 * @param mysqli_result $result
 */
function mysqli_num_fields (mysqli_result $result): int {}

/**
 * @param mysqli_result $result
 */
function mysqli_num_rows (mysqli_result $result): string|int {}

/**
 * @param mysqli $mysql
 * @param int $option
 * @param mixed $value
 */
function mysqli_options (mysqli $mysqlint , $option, $value = null): bool {}

/**
 * @param mysqli $mysql
 * @param int $option
 * @param mixed $value
 */
function mysqli_set_opt (mysqli $mysqlint , $option, $value = null): bool {}

/**
 * @param mysqli $mysql
 */
function mysqli_ping (mysqli $mysql): bool {}

/**
 * @param ?array|null[] $read
 * @param ?array|null[] $error
 * @param array[] $reject
 * @param int $seconds
 * @param int $microseconds [optional]
 */
function mysqli_poll (array &$read = nullarray , &$error = nullarray , &$rejectint , $secondsint , $microseconds = 0): int|false {}

/**
 * @param mysqli $mysql
 * @param string $query
 */
function mysqli_prepare (mysqli $mysqlstring , $query): mysqli_stmt|false {}

/**
 * Alias: mysqli_driver->report_mode
 * @link http://www.php.net/manual/en/function.mysqli-report.php
 * @param int $flags
 */
function mysqli_report (int $flags): bool {}

/**
 * @param mysqli $mysql
 * @param string $query
 * @param int $result_mode [optional]
 */
function mysqli_query (mysqli $mysqlstring , $queryint , $result_mode = 0): mysqli_result|bool {}

/**
 * @param mysqli $mysql
 * @param ?string|null $hostname [optional]
 * @param ?string|null $username [optional]
 * @param ?string|null $password [optional]
 * @param ?string|null $database [optional]
 * @param ?int|null $port [optional]
 * @param ?string|null $socket [optional]
 * @param int $flags [optional]
 */
function mysqli_real_connect (mysqli $mysql?string|null , $hostname = null?string|null , $username = null?string|null , $password = null?string|null , $database = null?int|null , $port = null?string|null , $socket = nullint , $flags = 0): bool {}

/**
 * @param mysqli $mysql
 * @param string $string
 */
function mysqli_real_escape_string (mysqli $mysqlstring , $string): string {}

/**
 * @param mysqli $mysql
 * @param string $string
 */
function mysqli_escape_string (mysqli $mysqlstring , $string): string {}

/**
 * @param mysqli $mysql
 * @param string $query
 */
function mysqli_real_query (mysqli $mysqlstring , $query): bool {}

/**
 * @param mysqli $mysql
 */
function mysqli_reap_async_query (mysqli $mysql): mysqli_result|bool {}

/**
 * @param mysqli $mysql
 * @param string $name
 */
function mysqli_release_savepoint (mysqli $mysqlstring , $name): bool {}

/**
 * @param mysqli $mysql
 * @param int $flags [optional]
 * @param ?string|null $name [optional]
 */
function mysqli_rollback (mysqli $mysqlint , $flags = 0?string|null , $name = null): bool {}

/**
 * @param mysqli $mysql
 * @param string $name
 */
function mysqli_savepoint (mysqli $mysqlstring , $name): bool {}

/**
 * @param mysqli $mysql
 * @param string $database
 */
function mysqli_select_db (mysqli $mysqlstring , $database): bool {}

/**
 * @param mysqli $mysql
 * @param string $charset
 */
function mysqli_set_charset (mysqli $mysqlstring , $charset): bool {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_affected_rows (mysqli_stmt $statement): string|int {}

/**
 * @param mysqli_stmt $statement
 * @param int $attribute
 */
function mysqli_stmt_attr_get (mysqli_stmt $statementint , $attribute): int {}

/**
 * @param mysqli_stmt $statement
 * @param int $attribute
 * @param int $value
 */
function mysqli_stmt_attr_set (mysqli_stmt $statementint , $attributeint , $value): bool {}

/**
 * @param mysqli_stmt $statement
 * @param string $types
 * @param mixed|null $vars [optional]
 */
function mysqli_stmt_bind_param (mysqli_stmt $statementstring , $typesmixed|null , &...$vars): bool {}

/**
 * @param mysqli_stmt $statement
 * @param mixed|null $vars [optional]
 */
function mysqli_stmt_bind_result (mysqli_stmt $statementmixed|null , &...$vars): bool {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_close (mysqli_stmt $statement): bool {}

/**
 * @param mysqli_stmt $statement
 * @param int $offset
 */
function mysqli_stmt_data_seek (mysqli_stmt $statementint , $offset): void {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_errno (mysqli_stmt $statement): int {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_error (mysqli_stmt $statement): string {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_error_list (mysqli_stmt $statement): array {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_fetch (mysqli_stmt $statement): ??bool {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_field_count (mysqli_stmt $statement): int {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_free_result (mysqli_stmt $statement): void {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_get_result (mysqli_stmt $statement): mysqli_result|false {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_get_warnings (mysqli_stmt $statement): mysqli_warning|false {}

/**
 * @param mysqli $mysql
 */
function mysqli_stmt_init (mysqli $mysql): mysqli_stmt|false {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_insert_id (mysqli_stmt $statement): string|int {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_more_results (mysqli_stmt $statement): bool {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_next_result (mysqli_stmt $statement): bool {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_num_rows (mysqli_stmt $statement): string|int {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_param_count (mysqli_stmt $statement): int {}

/**
 * @param mysqli_stmt $statement
 * @param string $query
 */
function mysqli_stmt_prepare (mysqli_stmt $statementstring , $query): bool {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_reset (mysqli_stmt $statement): bool {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_result_metadata (mysqli_stmt $statement): mysqli_result|false {}

/**
 * @param mysqli_stmt $statement
 * @param int $param_num
 * @param string $data
 */
function mysqli_stmt_send_long_data (mysqli_stmt $statementint , $param_numstring , $data): bool {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_store_result (mysqli_stmt $statement): bool {}

/**
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_sqlstate (mysqli_stmt $statement): string {}

/**
 * @param mysqli $mysql
 */
function mysqli_sqlstate (mysqli $mysql): string {}

/**
 * @param mysqli $mysql
 * @param ?string|null $key
 * @param ?string|null $certificate
 * @param ?string|null $ca_certificate
 * @param ?string|null $ca_path
 * @param ?string|null $cipher_algos
 */
function mysqli_ssl_set (mysqli $mysql?string|null , $key = null?string|null , $certificate = null?string|null , $ca_certificate = null?string|null , $ca_path = null?string|null , $cipher_algos = null): bool {}

/**
 * @param mysqli $mysql
 */
function mysqli_stat (mysqli $mysql): string|false {}

/**
 * @param mysqli $mysql
 * @param int $mode [optional]
 */
function mysqli_store_result (mysqli $mysqlint , $mode = 0): mysqli_result|false {}

/**
 * @param mysqli $mysql
 */
function mysqli_thread_id (mysqli $mysql): int {}

function mysqli_thread_safe (): bool {}

/**
 * @param mysqli $mysql
 */
function mysqli_use_result (mysqli $mysql): mysqli_result|false {}

/**
 * @param mysqli $mysql
 */
function mysqli_warning_count (mysqli $mysql): int {}

/**
 * @param mysqli $mysql
 * @param int $flags
 */
function mysqli_refresh (mysqli $mysqlint , $flags): bool {}


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

// End of mysqli v.8.0.28
