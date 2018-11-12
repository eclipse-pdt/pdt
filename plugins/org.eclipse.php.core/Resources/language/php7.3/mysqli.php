<?php

// Start of mysqli v.7.3.0

/**
 * The mysqli exception handling class.
 * @link http://www.php.net/manual/en/class.mysqli_sql_exception.php
 */
final class mysqli_sql_exception extends RuntimeException implements Throwable {
	protected $message;
	protected $file;
	protected $line;
	protected $code;
	protected $sqlstate;


	final private function __clone () {}

	/**
	 * @param mixed $message [optional]
	 * @param mixed $code [optional]
	 * @param mixed $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * MySQLi Driver.
 * @link http://www.php.net/manual/en/class.mysqli_driver.php
 */
final class mysqli_driver  {
	public $client_info;
	public $client_version;
	public $driver_version;
	public $embedded;
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
	public $stat;
	public $sqlstate;
	public $protocol_version;
	public $thread_id;
	public $warning_count;


	/**
	 * Turns on or off auto-committing database modifications
	 * @link http://www.php.net/manual/en/mysqli.autocommit.php
	 * @param bool $mode Whether to turn on auto-commit or not.
	 * @return bool true on success or false on failure
	 */
	public function autocommit (bool $mode) {}

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
	 * @param string $name [optional] Savepoint name for the transaction.
	 * @return bool true on success or false on failure
	 */
	public function begin_transaction (int $flags = null, string $name = null) {}

	/**
	 * Changes the user of the specified database connection
	 * @link http://www.php.net/manual/en/mysqli.change-user.php
	 * @param string $user The MySQL user name.
	 * @param string $password The MySQL password.
	 * @param string $database <p>
	 * The database to change to.
	 * </p>
	 * <p>
	 * If desired, the null value may be passed resulting in only changing
	 * the user and not selecting a database. To select a database in this
	 * case use the mysqli_select_db function.
	 * </p>
	 * @return bool true on success or false on failure
	 */
	public function change_user (string $user, string $password, string $database) {}

	/**
	 * Returns the default character set for the database connection
	 * @link http://www.php.net/manual/en/mysqli.character-set-name.php
	 * @return string The default character set for the current connection
	 */
	public function character_set_name () {}

	/**
	 * Closes a previously opened database connection
	 * @link http://www.php.net/manual/en/mysqli.close.php
	 * @return bool true on success or false on failure
	 */
	public function close () {}

	/**
	 * Commits the current transaction
	 * @link http://www.php.net/manual/en/mysqli.commit.php
	 * @param int $flags [optional] A bitmask of MYSQLI_TRANS_COR_&#42; constants.
	 * @param string $name [optional] If provided then COMMIT/&#42;name&#42;/ is executed.
	 * @return bool true on success or false on failure
	 */
	public function commit (int $flags = null, string $name = null) {}

	/**
	 * @param mixed $host [optional]
	 * @param mixed $user [optional]
	 * @param mixed $password [optional]
	 * @param mixed $database [optional]
	 * @param mixed $port [optional]
	 * @param mixed $socket [optional]
	 */
	public function connect ($host = null, $user = null, $password = null, $database = null, $port = null, $socket = null) {}

	/**
	 * Dump debugging information into the log
	 * @link http://www.php.net/manual/en/mysqli.dump-debug-info.php
	 * @return bool true on success or false on failure
	 */
	public function dump_debug_info () {}

	/**
	 * Performs debugging operations
	 * @link http://www.php.net/manual/en/mysqli.debug.php
	 * @param string $message A string representing the debugging operation to perform
	 * @return bool true.
	 */
	public function debug (string $message) {}

	/**
	 * Returns a character set object
	 * @link http://www.php.net/manual/en/mysqli.get-charset.php
	 * @return object The function returns a character set object with the following properties:
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
	 * @return bool an array with connection stats if success, false otherwise.
	 */
	public function get_connection_stats () {}

	public function get_server_info () {}

	/**
	 * Get result of SHOW WARNINGS
	 * @link http://www.php.net/manual/en/mysqli.get-warnings.php
	 * @return mysqli_warning 
	 */
	public function get_warnings () {}

	/**
	 * Initializes MySQLi and returns a resource for use with mysqli_real_connect()
	 * @link http://www.php.net/manual/en/mysqli.init.php
	 * @return mysqli an object.
	 */
	public function init () {}

	/**
	 * Asks the server to kill a MySQL thread
	 * @link http://www.php.net/manual/en/mysqli.kill.php
	 * @param int $processid 
	 * @return bool true on success or false on failure
	 */
	public function kill (int $processid) {}

	/**
	 * Performs a query on the database
	 * @link http://www.php.net/manual/en/mysqli.multi-query.php
	 * @param string $query <p>
	 * The query, as a string.
	 * </p>
	 * <p>
	 * Data inside the query should be properly escaped.
	 * </p>
	 * @return bool false if the first statement failed.
	 * To retrieve subsequent errors from other statements you have to call
	 * mysqli_next_result first.
	 */
	public function multi_query (string $query) {}

	/**
	 * Open a new connection to the MySQL server
	 * @link http://www.php.net/manual/en/mysqli.construct.php
	 * @param string $host [optional] <p>
	 * Can be either a host name or an IP address. Passing the null value
	 * or the string "localhost" to this parameter, the local host is
	 * assumed. When possible, pipes will be used instead of the TCP/IP
	 * protocol.
	 * </p>
	 * <p>
	 * Prepending host by p: opens a persistent connection.
	 * mysqli_change_user is automatically called on
	 * connections opened from the connection pool.
	 * </p>
	 * @param string $username [optional] The MySQL user name.
	 * @param string $passwd [optional] If not provided or null, the MySQL server will attempt to authenticate
	 * the user against those user records which have no password only. This
	 * allows one username to be used with different permissions (depending
	 * on if a password is provided or not).
	 * @param string $dbname [optional] If provided will specify the default database to be used when
	 * performing queries.
	 * @param int $port [optional] Specifies the port number to attempt to connect to the MySQL server.
	 * @param string $socket [optional] <p>
	 * Specifies the socket or named pipe that should be used.
	 * </p>
	 * <p>
	 * Specifying the socket parameter will not
	 * explicitly determine the type of connection to be used when
	 * connecting to the MySQL server. How the connection is made to the
	 * MySQL database is determined by the host
	 * parameter.
	 * </p>
	 * @return void an object which represents the connection to a MySQL Server.
	 */
	public function __construct (string $host = null, string $username = null, string $passwd = null, string $dbname = null, int $port = null, string $socket = null) {}

	/**
	 * Check if there are any more query results from a multi query
	 * @link http://www.php.net/manual/en/mysqli.more-results.php
	 * @return bool true if one or more result sets are available from a previous call to
	 * mysqli_multi_query, otherwise false.
	 */
	public function more_results () {}

	/**
	 * Prepare next result from multi_query
	 * @link http://www.php.net/manual/en/mysqli.next-result.php
	 * @return bool true on success or false on failure
	 */
	public function next_result () {}

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
	 * <td>connection timeout in seconds (supported on Windows with TCP/IP since PHP 5.3.1)</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_OPT_LOCAL_INFILE</td>
	 * <td>enable/disable use of LOAD LOCAL INFILE</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_INIT_COMMAND</td>
	 * <td>command to execute after when connecting to MySQL server</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_READ_DEFAULT_FILE</td>
	 * <td>
	 * Read options from named option file instead of my.cnf
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_READ_DEFAULT_GROUP</td>
	 * <td>
	 * Read options from the named group from my.cnf
	 * or the file specified with MYSQL_READ_DEFAULT_FILE.
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
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * @param mixed $value The value for the option.
	 * @return bool true on success or false on failure
	 */
	public function options (int $option, $value) {}

	/**
	 * Pings a server connection, or tries to reconnect if the connection has gone down
	 * @link http://www.php.net/manual/en/mysqli.ping.php
	 * @return bool true on success or false on failure
	 */
	public function ping () {}

	/**
	 * Poll connections
	 * @link http://www.php.net/manual/en/mysqli.poll.php
	 * @param array $read List of connections to check for outstanding results that can be read.
	 * @param array $error List of connections on which an error occured, for example, query
	 * failure or lost connection.
	 * @param array $reject List of connections rejected because no asynchronous query
	 * has been run on for which the function could poll results.
	 * @param int $sec Maximum number of seconds to wait, must be non-negative.
	 * @param int $usec [optional] Maximum number of microseconds to wait, must be non-negative.
	 * @return int number of ready connections upon success, false otherwise.
	 */
	public static function poll (array &$read, array &$error, array &$reject, int $sec, int $usec = null) {}

	/**
	 * Prepare an SQL statement for execution
	 * @link http://www.php.net/manual/en/mysqli.prepare.php
	 * @param string $query <p>
	 * The query, as a string.
	 * </p>
	 * <p>
	 * You should not add a terminating semicolon or \g
	 * to the statement.
	 * </p>
	 * <p>
	 * This parameter can include one or more parameter markers in the SQL
	 * statement by embedding question mark (?) characters
	 * at the appropriate positions.
	 * </p>
	 * <p>
	 * The markers are legal only in certain places in SQL statements.
	 * For example, they are allowed in the VALUES()
	 * list of an INSERT statement (to specify column
	 * values for a row), or in a comparison with a column in a
	 * WHERE clause to specify a comparison value.
	 * </p>
	 * <p>
	 * However, they are not allowed for identifiers (such as table or
	 * column names), in the select list that names the columns to be
	 * returned by a SELECT statement, or to specify both
	 * operands of a binary operator such as the = equal
	 * sign. The latter restriction is necessary because it would be
	 * impossible to determine the parameter type. It's not allowed to
	 * compare marker with NULL by 
	 * ? IS NULL too. In general, parameters are legal
	 * only in Data Manipulation Language (DML) statements, and not in Data
	 * Definition Language (DDL) statements.
	 * </p>
	 * @return mysqli_stmt mysqli_prepare returns a statement object or false if an error occurred.
	 */
	public function prepare (string $query) {}

	/**
	 * Performs a query on the database
	 * @link http://www.php.net/manual/en/mysqli.query.php
	 * @param string $query <p>
	 * The query string.
	 * </p>
	 * <p>
	 * Data inside the query should be properly escaped.
	 * </p>
	 * @param int $resultmode [optional] <p>
	 * Either the constant MYSQLI_USE_RESULT or
	 * MYSQLI_STORE_RESULT depending on the desired
	 * behavior. By default, MYSQLI_STORE_RESULT is used.
	 * </p>
	 * <p>
	 * If you use MYSQLI_USE_RESULT all subsequent calls
	 * will return error Commands out of sync unless you
	 * call mysqli_free_result
	 * </p>
	 * <p>
	 * With MYSQLI_ASYNC (available with mysqlnd), it is
	 * possible to perform query asynchronously.
	 * mysqli_poll is then used to get results from such
	 * queries.
	 * </p>
	 * @return mysqli_result|bool false on failure. For successful SELECT, SHOW, DESCRIBE or
	 * EXPLAIN queries mysqli_query will return
	 * a mysqli_result object. For other successful queries mysqli_query will
	 * return true.
	 */
	public function query (string $query, int $resultmode = null) {}

	/**
	 * Opens a connection to a mysql server
	 * @link http://www.php.net/manual/en/mysqli.real-connect.php
	 * @param string $host [optional] Can be either a host name or an IP address. Passing the null value
	 * or the string "localhost" to this parameter, the local host is
	 * assumed. When possible, pipes will be used instead of the TCP/IP
	 * protocol.
	 * @param string $username [optional] The MySQL user name.
	 * @param string $passwd [optional] If provided or null, the MySQL server will attempt to authenticate
	 * the user against those user records which have no password only. This
	 * allows one username to be used with different permissions (depending
	 * on if a password as provided or not).
	 * @param string $dbname [optional] If provided will specify the default database to be used when
	 * performing queries.
	 * @param int $port [optional] Specifies the port number to attempt to connect to the MySQL server.
	 * @param string $socket [optional] <p>
	 * Specifies the socket or named pipe that should be used.
	 * </p>
	 * <p>
	 * Specifying the socket parameter will not
	 * explicitly determine the type of connection to be used when
	 * connecting to the MySQL server. How the connection is made to the
	 * MySQL database is determined by the host
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
	public function real_connect (string $host = null, string $username = null, string $passwd = null, string $dbname = null, int $port = null, string $socket = null, int $flags = null) {}

	/**
	 * Escapes special characters in a string for use in an SQL statement, taking into account the current charset of the connection
	 * @link http://www.php.net/manual/en/mysqli.real-escape-string.php
	 * @param string $escapestr <p>
	 * The string to be escaped.
	 * </p>
	 * <p>
	 * Characters encoded are NUL (ASCII 0), \n, \r, \, ', ", and
	 * Control-Z.
	 * </p>
	 * @return string an escaped string.
	 */
	public function real_escape_string (string $escapestr) {}

	/**
	 * Get result from async query
	 * @link http://www.php.net/manual/en/mysqli.reap-async-query.php
	 * @return mysqli_result mysqli_result in success, false otherwise.
	 */
	public function reap_async_query () {}

	/**
	 * @param mixed $string_to_escape
	 */
	public function escape_string ($string_to_escape) {}

	/**
	 * Execute an SQL query
	 * @link http://www.php.net/manual/en/mysqli.real-query.php
	 * @param string $query <p>
	 * The query, as a string.
	 * </p>
	 * <p>
	 * Data inside the query should be properly escaped.
	 * </p>
	 * @return bool true on success or false on failure
	 */
	public function real_query (string $query) {}

	/**
	 * Removes the named savepoint from the set of savepoints of the current transaction
	 * @link http://www.php.net/manual/en/mysqli.release-savepoint.php
	 * @param string $name 
	 * @return bool true on success or false on failure
	 */
	public function release_savepoint (string $name) {}

	/**
	 * Rolls back current transaction
	 * @link http://www.php.net/manual/en/mysqli.rollback.php
	 * @param int $flags [optional] A bitmask of MYSQLI_TRANS_COR_&#42; constants.
	 * @param string $name [optional] If provided then ROLLBACK/&#42;name&#42;/ is executed.
	 * @return bool true on success or false on failure
	 */
	public function rollback (int $flags = null, string $name = null) {}

	/**
	 * Set a named transaction savepoint
	 * @link http://www.php.net/manual/en/mysqli.savepoint.php
	 * @param string $name 
	 * @return bool true on success or false on failure
	 */
	public function savepoint (string $name) {}

	/**
	 * Selects the default database for database queries
	 * @link http://www.php.net/manual/en/mysqli.select-db.php
	 * @param string $dbname The database name.
	 * @return bool true on success or false on failure
	 */
	public function select_db (string $dbname) {}

	/**
	 * Sets the default client character set
	 * @link http://www.php.net/manual/en/mysqli.set-charset.php
	 * @param string $charset The charset to be set as default.
	 * @return bool true on success or false on failure
	 */
	public function set_charset (string $charset) {}

	/**
	 * Alias of mysqli_options
	 * @link http://www.php.net/manual/en/function.mysqli-set-opt.php
	 * @param mixed $option
	 * @param mixed $value
	 */
	public function set_opt ($option, $value) {}

	/**
	 * Used for establishing secure connections using SSL
	 * @link http://www.php.net/manual/en/mysqli.ssl-set.php
	 * @param string $key The path name to the key file.
	 * @param string $cert The path name to the certificate file.
	 * @param string $ca The path name to the certificate authority file.
	 * @param string $capath The pathname to a directory that contains trusted SSL CA certificates
	 * in PEM format.
	 * @param string $cipher A list of allowable ciphers to use for SSL encryption.
	 * @return bool This function always returns true value. If SSL setup is
	 * incorrect mysqli_real_connect will return an error
	 * when you attempt to connect.
	 */
	public function ssl_set (string $key, string $cert, string $ca, string $capath, string $cipher) {}

	/**
	 * Gets the current system status
	 * @link http://www.php.net/manual/en/mysqli.stat.php
	 * @return string A string describing the server status. false if an error occurred.
	 */
	public function stat () {}

	/**
	 * Initializes a statement and returns an object for use with mysqli_stmt_prepare
	 * @link http://www.php.net/manual/en/mysqli.stmt-init.php
	 * @return mysqli_stmt an object.
	 */
	public function stmt_init () {}

	/**
	 * Transfers a result set from the last query
	 * @link http://www.php.net/manual/en/mysqli.store-result.php
	 * @param int $option [optional] The option that you want to set. It can be one of the following values:
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
	 * released earlier (available with mysqlnd only, since PHP 5.6.0)</td>
	 * </tr>
	 * </table>
	 * </table>
	 * @return mysqli_result a buffered result object or false if an error occurred.
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
	public function store_result (int $option = null) {}

	/**
	 * Returns whether thread safety is given or not
	 * @link http://www.php.net/manual/en/mysqli.thread-safe.php
	 * @return bool true if the client library is thread-safe, otherwise false.
	 */
	public function thread_safe () {}

	/**
	 * Initiate a result set retrieval
	 * @link http://www.php.net/manual/en/mysqli.use-result.php
	 * @return mysqli_result an unbuffered result object or false if an error occurred.
	 */
	public function use_result () {}

	/**
	 * Refreshes
	 * @link http://www.php.net/manual/en/mysqli.refresh.php
	 * @param int $options <p>
	 * The options to refresh, using the MYSQLI_REFRESH_&#42; constants as documented
	 * within the MySQLi constants documentation.
	 * </p>
	 * <p>
	 * See also the official MySQL Refresh
	 * documentation.
	 * </p>
	 * @return bool true if the refresh was a success, otherwise false
	 */
	public function refresh (int $options) {}

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
	 * The __construct purpose
	 * @link http://www.php.net/manual/en/mysqli-warning.construct.php
	 */
	protected function __construct () {}

	/**
	 * The next purpose
	 * @link http://www.php.net/manual/en/mysqli-warning.next.php
	 * @return void 
	 */
	public function next () {}

}

/**
 * Represents the result set obtained from a query against the database.
 * <p>Changelog</p>
 * @link http://www.php.net/manual/en/class.mysqli_result.php
 */
class mysqli_result implements Traversable {
	public $current_field;
	public $field_count;
	public $lengths;
	public $num_rows;
	public $type;


	public function __construct () {}

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
	 * @param int $offset The field offset. Must be between zero and the total number of rows
	 * minus one (0..mysqli_num_rows - 1).
	 * @return bool true on success or false on failure
	 */
	public function data_seek (int $offset) {}

	/**
	 * Returns the next field in the result set
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-field.php
	 * @return object an object which contains field definition information or false
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
	 * <td>Database (since PHP 5.3.6)</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>catalog</td>
	 * <td>The catalog name, always "def" (since PHP 5.3.6)</td>
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
	 * @return array an array of objects which contains field definition information or
	 * false if no field information is available.
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
	 * <td>The maximum width of the field for the result set.</td>
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
	 * @param int $fieldnr The field number. This value must be in the range from 
	 * 0 to number of fields - 1.
	 * @return object an object which contains field definition information or false
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
	 * <td>The number of decimals used (for numeric fields)</td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 */
	public function fetch_field_direct (int $fieldnr) {}

	/**
	 * Fetches all result rows as an associative array, a numeric array, or both
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-all.php
	 * @param int $resulttype [optional] This optional parameter is a constant indicating what type of array
	 * should be produced from the current row data. The possible values for
	 * this parameter are the constants MYSQLI_ASSOC,
	 * MYSQLI_NUM, or MYSQLI_BOTH.
	 * @return mixed an array of associative or numeric arrays holding result rows.
	 */
	public function fetch_all (int $resulttype = null) {}

	/**
	 * Fetch a result row as an associative, a numeric array, or both
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-array.php
	 * @param int $resulttype [optional] <p>
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
	 * @return mixed an array of strings that corresponds to the fetched row or null if there
	 * are no more rows in resultset.
	 */
	public function fetch_array (int $resulttype = null) {}

	/**
	 * Fetch a result row as an associative array
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-assoc.php
	 * @return array an associative array of strings representing the fetched row in the result
	 * set, where each key in the array represents the name of one of the result
	 * set's columns or null if there are no more rows in resultset.
	 * <p>
	 * If two or more columns of the result have the same field names, the last
	 * column will take precedence. To access the other column(s) of the same
	 * name, you either need to access the result with numeric indices by using
	 * mysqli_fetch_row or add alias names.
	 * </p>
	 */
	public function fetch_assoc () {}

	/**
	 * Returns the current row of a result set as an object
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-object.php
	 * @param string $class_name [optional] The name of the class to instantiate, set the properties of and return.
	 * If not specified, a stdClass object is returned.
	 * @param array $params [optional] An optional array of parameters to pass to the constructor
	 * for class_name objects.
	 * @return object an object with string properties that corresponds to the fetched
	 * row or null if there are no more rows in resultset.
	 */
	public function fetch_object (string $class_name = null, array $params = null) {}

	/**
	 * Get a result row as an enumerated array
	 * @link http://www.php.net/manual/en/mysqli-result.fetch-row.php
	 * @return mixed mysqli_fetch_row returns an array of strings that corresponds to the fetched row
	 * or null if there are no more rows in result set.
	 */
	public function fetch_row () {}

	/**
	 * Set result pointer to a specified field offset
	 * @link http://www.php.net/manual/en/mysqli-result.field-seek.php
	 * @param int $fieldnr The field number. This value must be in the range from 
	 * 0 to number of fields - 1.
	 * @return bool true on success or false on failure
	 */
	public function field_seek (int $fieldnr) {}

	public function free_result () {}

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
	 */
	public function __construct () {}

	/**
	 * Used to get the current value of a statement attribute
	 * @link http://www.php.net/manual/en/mysqli-stmt.attr-get.php
	 * @param int $attr The attribute that you want to get.
	 * @return int false if the attribute is not found, otherwise returns the value of the attribute.
	 */
	public function attr_get (int $attr) {}

	/**
	 * Used to modify the behavior of a prepared statement
	 * @link http://www.php.net/manual/en/mysqli-stmt.attr-set.php
	 * @param int $attr <p>
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
	 * update the metadata MYSQL_FIELD->max_length value.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_STMT_ATTR_CURSOR_TYPE</td>
	 * <td>
	 * Type of cursor to open for statement when mysqli_stmt_execute
	 * is invoked. mode can be MYSQLI_CURSOR_TYPE_NO_CURSOR
	 * (the default) or MYSQLI_CURSOR_TYPE_READ_ONLY.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MYSQLI_STMT_ATTR_PREFETCH_ROWS</td>
	 * <td>
	 * Number of rows to fetch from server at a time when using a cursor.
	 * mode can be in the range from 1 to the maximum
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
	 * @param int $mode The value to assign to the attribute.
	 * @return bool 
	 */
	public function attr_set (int $attr, int $mode) {}

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
	 * <td>corresponding variable has type integer</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>d</td>
	 * <td>corresponding variable has type double</td>
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
	 * @param mixed $var1 The number of variables and length of string 
	 * types must match the parameters in the statement.
	 * @param mixed $_ [optional] 
	 * @return bool true on success or false on failure
	 */
	public function bind_param (string $types, &$var1, &$_ = null) {}

	/**
	 * Binds variables to a prepared statement for result storage
	 * @link http://www.php.net/manual/en/mysqli-stmt.bind-result.php
	 * @param mixed $var1 The variable to be bound.
	 * @param mixed $_ [optional] 
	 * @return bool true on success or false on failure
	 */
	public function bind_result (&$var1, &$_ = null) {}

	/**
	 * Closes a prepared statement
	 * @link http://www.php.net/manual/en/mysqli-stmt.close.php
	 * @return bool true on success or false on failure
	 */
	public function close () {}

	/**
	 * Seeks to an arbitrary row in statement result set
	 * @link http://www.php.net/manual/en/mysqli-stmt.data-seek.php
	 * @param int $offset Must be between zero and the total number of rows minus one (0..
	 * mysqli_stmt_num_rows - 1).
	 * @return void 
	 */
	public function data_seek (int $offset) {}

	/**
	 * Executes a prepared Query
	 * @link http://www.php.net/manual/en/mysqli-stmt.execute.php
	 * @return bool true on success or false on failure
	 */
	public function execute () {}

	/**
	 * Fetch results from a prepared statement into the bound variables
	 * @link http://www.php.net/manual/en/mysqli-stmt.fetch.php
	 * @return bool 
	 */
	public function fetch () {}

	/**
	 * Get result of SHOW WARNINGS
	 * @link http://www.php.net/manual/en/mysqli-stmt.get-warnings.php
	 * @param mysqli_stmt $stmt 
	 * @return object 
	 */
	public function get_warnings (mysqli_stmt $stmt) {}

	/**
	 * Returns result set metadata from a prepared statement
	 * @link http://www.php.net/manual/en/mysqli-stmt.result-metadata.php
	 * @return mysqli_result a result object or false if an error occurred.
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
	 * @param int $param_nr Indicates which parameter to associate the data with. Parameters are
	 * numbered beginning with 0.
	 * @param string $data A string containing data to be sent.
	 * @return bool true on success or false on failure
	 */
	public function send_long_data (int $param_nr, string $data) {}

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
	 * Prepare an SQL statement for execution
	 * @link http://www.php.net/manual/en/mysqli-stmt.prepare.php
	 * @param string $query <p>
	 * The query, as a string. It must consist of a single SQL statement.
	 * </p>
	 * <p>
	 * You can include one or more parameter markers in the SQL statement by
	 * embedding question mark (?) characters at the
	 * appropriate positions.
	 * </p>
	 * <p>
	 * You should not add a terminating semicolon or \g
	 * to the statement.
	 * </p>
	 * <p>
	 * The markers are legal only in certain places in SQL statements.
	 * For example, they are allowed in the VALUES() list of an INSERT statement
	 * (to specify column values for a row), or in a comparison with a column in
	 * a WHERE clause to specify a comparison value.
	 * </p>
	 * <p>
	 * However, they are not allowed for identifiers (such as table or column names),
	 * in the select list that names the columns to be returned by a SELECT statement),
	 * or to specify both operands of a binary operator such as the =
	 * equal sign. The latter restriction is necessary because it would be impossible
	 * to determine the parameter type. In general, parameters are legal only in Data
	 * Manipulation Language (DML) statements, and not in Data Definition Language
	 * (DDL) statements.
	 * </p>
	 * @return mixed true on success or false on failure
	 */
	public function prepare (string $query) {}

	/**
	 * Transfers a result set from a prepared statement
	 * @link http://www.php.net/manual/en/mysqli-stmt.store-result.php
	 * @return bool true on success or false on failure
	 */
	public function store_result () {}

	/**
	 * Gets a result set from a prepared statement
	 * @link http://www.php.net/manual/en/mysqli-stmt.get-result.php
	 * @return mysqli_result a resultset for successful SELECT queries, or false for other DML
	 * queries or on failure. The mysqli_errno function can be
	 * used to distinguish between the two types of failure.
	 */
	public function get_result () {}

}

/**
 * @param mixed $link
 */
function mysqli_affected_rows ($link) {}

/**
 * @param mixed $link
 * @param mixed $mode
 */
function mysqli_autocommit ($link, $mode) {}

/**
 * @param mixed $link
 * @param mixed $flags [optional]
 * @param mixed $name [optional]
 */
function mysqli_begin_transaction ($link, $flags = null, $name = null) {}

/**
 * @param mixed $link
 * @param mixed $user
 * @param mixed $password
 * @param mixed $database
 */
function mysqli_change_user ($link, $user, $password, $database) {}

/**
 * @param mixed $link
 */
function mysqli_character_set_name ($link) {}

/**
 * @param mixed $link
 */
function mysqli_close ($link) {}

/**
 * @param mixed $link
 * @param mixed $flags [optional]
 * @param mixed $name [optional]
 */
function mysqli_commit ($link, $flags = null, $name = null) {}

/**
 * Alias: mysqli::__construct
 * @link http://www.php.net/manual/en/function.mysqli-connect.php
 * @param mixed $host [optional]
 * @param mixed $user [optional]
 * @param mixed $password [optional]
 * @param mixed $database [optional]
 * @param mixed $port [optional]
 * @param mixed $socket [optional]
 */
function mysqli_connect ($host = null, $user = null, $password = null, $database = null, $port = null, $socket = null) {}

function mysqli_connect_errno () {}

function mysqli_connect_error () {}

/**
 * @param mixed $result
 * @param mixed $offset
 */
function mysqli_data_seek ($result, $offset) {}

/**
 * @param mixed $link
 */
function mysqli_dump_debug_info ($link) {}

/**
 * @param mixed $debug_options
 */
function mysqli_debug ($debug_options) {}

/**
 * @param mixed $link
 */
function mysqli_errno ($link) {}

/**
 * @param mixed $link
 */
function mysqli_error ($link) {}

/**
 * @param mixed $link
 */
function mysqli_error_list ($link) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_execute ($stmt) {}

/**
 * Alias for mysqli_stmt_execute
 * @link http://www.php.net/manual/en/function.mysqli-execute.php
 * @param mixed $stmt
 */
function mysqli_execute ($stmt) {}

/**
 * @param mixed $result
 */
function mysqli_fetch_field ($result) {}

/**
 * @param mixed $result
 */
function mysqli_fetch_fields ($result) {}

/**
 * @param mixed $result
 * @param mixed $field_nr
 */
function mysqli_fetch_field_direct ($result, $field_nr) {}

/**
 * @param mixed $result
 */
function mysqli_fetch_lengths ($result) {}

/**
 * @param mixed $result
 * @param mixed $result_type [optional]
 */
function mysqli_fetch_all ($result, $result_type = null) {}

/**
 * @param mixed $result
 * @param mixed $result_type [optional]
 */
function mysqli_fetch_array ($result, $result_type = null) {}

/**
 * @param mixed $result
 */
function mysqli_fetch_assoc ($result) {}

/**
 * @param mixed $result
 * @param mixed $class_name [optional]
 * @param mixed $params [optional]
 */
function mysqli_fetch_object ($result, $class_name = nullarray , $params = null) {}

/**
 * @param mixed $result
 */
function mysqli_fetch_row ($result) {}

/**
 * @param mixed $link
 */
function mysqli_field_count ($link) {}

/**
 * @param mixed $result
 * @param mixed $field_nr
 */
function mysqli_field_seek ($result, $field_nr) {}

/**
 * @param mixed $result
 */
function mysqli_field_tell ($result) {}

/**
 * @param mixed $result
 */
function mysqli_free_result ($result) {}

/**
 * @param mixed $link
 */
function mysqli_get_connection_stats ($link) {}

/**
 * Returns client per-process statistics
 * @link http://www.php.net/manual/en/function.mysqli-get-client-stats.php
 * @return array an array with client stats if success, false otherwise.
 */
function mysqli_get_client_stats () {}

/**
 * @param mixed $link
 */
function mysqli_get_charset ($link) {}

function mysqli_get_client_info () {}

/**
 * @param mixed $link
 */
function mysqli_get_client_version ($link) {}

/**
 * Return information about open and cached links
 * @link http://www.php.net/manual/en/function.mysqli-get-links-stats.php
 * @return array mysqli_get_links_stats returns an associative array
 * with three elements, keyed as follows:
 * <p>
 * total
 * <br>
 * <p>
 * An integer indicating the total number of open links in
 * any state.
 * active_plinks
 * <br>
 * <p>
 * An integer representing the number of active persistent
 * connections.
 * </p>
 * cached_plinks
 * <br>
 * <p>
 * An integer representing the number of inactive persistent
 * connections.
 * </p>
 * </p>
 * </p>
 */
function mysqli_get_links_stats () {}

/**
 * @param mixed $link
 */
function mysqli_get_host_info ($link) {}

/**
 * @param mixed $link
 */
function mysqli_get_proto_info ($link) {}

/**
 * @param mixed $link
 */
function mysqli_get_server_info ($link) {}

/**
 * @param mixed $link
 */
function mysqli_get_server_version ($link) {}

/**
 * @param mixed $link
 */
function mysqli_get_warnings ($link) {}

function mysqli_init () {}

/**
 * @param mixed $link
 */
function mysqli_info ($link) {}

/**
 * @param mixed $link
 */
function mysqli_insert_id ($link) {}

/**
 * @param mixed $link
 * @param mixed $connection_id
 */
function mysqli_kill ($link, $connection_id) {}

/**
 * @param mixed $link
 */
function mysqli_more_results ($link) {}

/**
 * @param mixed $link
 * @param mixed $query [optional]
 */
function mysqli_multi_query ($link, $query = null) {}

/**
 * @param mixed $link
 */
function mysqli_next_result ($link) {}

/**
 * @param mixed $result
 */
function mysqli_num_fields ($result) {}

/**
 * @param mixed $result
 */
function mysqli_num_rows ($result) {}

/**
 * @param mixed $link
 * @param mixed $option
 * @param mixed $value
 */
function mysqli_options ($link, $option, $value) {}

/**
 * @param mixed $link
 */
function mysqli_ping ($link) {}

/**
 * @param mixed $read
 * @param mixed $write
 * @param mixed $error
 * @param mixed $sec
 * @param mixed $usec [optional]
 */
function mysqli_poll (array &$read = nullarray , &$write = nullarray , &$error = null, $sec, $usec = null) {}

/**
 * @param mixed $link
 * @param mixed $query
 */
function mysqli_prepare ($link, $query) {}

/**
 * Alias: mysqli_driver->report_mode
 * @link http://www.php.net/manual/en/function.mysqli-report.php
 * @param mixed $flags
 */
function mysqli_report ($flags) {}

/**
 * @param mixed $link
 * @param mixed $query
 * @param mixed $resultmode [optional]
 */
function mysqli_query ($link, $query, $resultmode = null) {}

/**
 * @param mixed $link
 * @param mixed $host [optional]
 * @param mixed $user [optional]
 * @param mixed $password [optional]
 * @param mixed $database [optional]
 * @param mixed $port [optional]
 * @param mixed $socket [optional]
 * @param mixed $flags [optional]
 */
function mysqli_real_connect ($link, $host = null, $user = null, $password = null, $database = null, $port = null, $socket = null, $flags = null) {}

/**
 * @param mixed $link
 * @param mixed $string_to_escape
 */
function mysqli_real_escape_string ($link, $string_to_escape) {}

/**
 * @param mixed $link
 * @param mixed $query [optional]
 */
function mysqli_real_query ($link, $query = null) {}

/**
 * @param mixed $link
 */
function mysqli_reap_async_query ($link) {}

/**
 * @param mixed $link
 * @param mixed $name
 */
function mysqli_release_savepoint ($link, $name) {}

/**
 * @param mixed $link
 * @param mixed $flags [optional]
 * @param mixed $name [optional]
 */
function mysqli_rollback ($link, $flags = null, $name = null) {}

/**
 * @param mixed $link
 * @param mixed $name
 */
function mysqli_savepoint ($link, $name) {}

/**
 * @param mixed $link
 * @param mixed $database
 */
function mysqli_select_db ($link, $database) {}

/**
 * @param mixed $link
 * @param mixed $charset
 */
function mysqli_set_charset ($link, $charset) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_affected_rows ($stmt) {}

/**
 * @param mixed $stmt
 * @param mixed $attribute
 */
function mysqli_stmt_attr_get ($stmt, $attribute) {}

/**
 * @param mixed $stmt
 * @param mixed $attribute
 * @param mixed $value
 */
function mysqli_stmt_attr_set ($stmt, $attribute, $value) {}

/**
 * @param mixed $stmt
 * @param mixed $types
 * @param mixed $vars
 */
function mysqli_stmt_bind_param ($stmt, $types, &...$vars) {}

/**
 * @param mixed $stmt
 * @param mixed $vars
 */
function mysqli_stmt_bind_result ($stmt, &...$vars) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_close ($stmt) {}

/**
 * @param mixed $stmt
 * @param mixed $offset
 */
function mysqli_stmt_data_seek ($stmt, $offset) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_errno ($stmt) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_error ($stmt) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_error_list ($stmt) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_fetch ($stmt) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_field_count ($stmt) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_free_result ($stmt) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_get_result ($stmt) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_get_warnings ($stmt) {}

/**
 * @param mixed $link
 */
function mysqli_stmt_init ($link) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_insert_id ($stmt) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_more_results ($stmt) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_next_result ($stmt) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_num_rows ($stmt) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_param_count ($stmt) {}

/**
 * @param mixed $stmt
 * @param mixed $query
 */
function mysqli_stmt_prepare ($stmt, $query) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_reset ($stmt) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_result_metadata ($stmt) {}

/**
 * @param mixed $stmt
 * @param mixed $param_nr
 * @param mixed $data
 */
function mysqli_stmt_send_long_data ($stmt, $param_nr, $data) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_store_result ($stmt) {}

/**
 * @param mixed $stmt
 */
function mysqli_stmt_sqlstate ($stmt) {}

/**
 * @param mixed $link
 */
function mysqli_sqlstate ($link) {}

/**
 * @param mixed $link
 * @param mixed $key
 * @param mixed $cert
 * @param mixed $certificate_authority
 * @param mixed $certificate_authority_path
 * @param mixed $cipher
 */
function mysqli_ssl_set ($link, $key, $cert, $certificate_authority, $certificate_authority_path, $cipher) {}

/**
 * @param mixed $link
 */
function mysqli_stat ($link) {}

/**
 * @param mixed $link
 * @param mixed $flags [optional]
 */
function mysqli_store_result ($link, $flags = null) {}

/**
 * @param mixed $link
 */
function mysqli_thread_id ($link) {}

function mysqli_thread_safe () {}

/**
 * @param mixed $link
 */
function mysqli_use_result ($link) {}

/**
 * @param mixed $link
 */
function mysqli_warning_count ($link) {}

/**
 * @param mixed $link
 * @param mixed $options
 */
function mysqli_refresh ($link, $options) {}

/**
 * Alias: mysqli_real_escape_string
 * @link http://www.php.net/manual/en/function.mysqli-escape-string.php
 * @param mixed $link
 * @param mixed $query
 * @param mixed $resultmode [optional]
 */
function mysqli_escape_string ($link, $query, $resultmode = null) {}

function mysqli_set_opt () {}


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
define ('MYSQLI_OPT_READ_TIMEOUT', 11);
define ('MYSQLI_OPT_NET_CMD_BUFFER_SIZE', 202);
define ('MYSQLI_OPT_NET_READ_BUFFER_SIZE', 203);
define ('MYSQLI_OPT_INT_AND_FLOAT_NATIVE', 201);
define ('MYSQLI_OPT_SSL_VERIFY_SERVER_CERT', 21);
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
define ('MYSQLI_CLIENT_SSL_DONT_VERIFY_SERVER_CERT', 64);
define ('MYSQLI_CLIENT_CAN_HANDLE_EXPIRED_PASSWORDS', 4194304);
define ('MYSQLI_OPT_CAN_HANDLE_EXPIRED_PASSWORDS', 37);

/**
 * For using buffered resultsets
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_STORE_RESULT', 0);

/**
 * For using unbuffered resultsets
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
 * Field is defined as ENUM. Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/mysqli.constants.php
 */
define ('MYSQLI_ENUM_FLAG', 256);

/**
 * Field is defined as BINARY. Available since PHP 5.3.0.
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
 * Data truncation occurred. Available since PHP 5.1.0 and MySQL 5.0.5.
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

// End of mysqli v.7.3.0
