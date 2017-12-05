<?php

// Start of pgsql v.7.1.1

/**
 * Open a PostgreSQL connection
 * @link http://www.php.net/manual/en/function.pg-connect.php
 * @param string $connection_string <p>
 * The connection_string can be empty to use all default parameters, or it 
 * can contain one or more parameter settings separated by whitespace. 
 * Each parameter setting is in the form keyword = value. Spaces around 
 * the equal sign are optional. To write an empty value or a value 
 * containing spaces, surround it with single quotes, e.g., keyword = 
 * 'a value'. Single quotes and backslashes within the value must be 
 * escaped with a backslash, i.e., \' and \\. 
 * </p>
 * <p>
 * The currently recognized parameter keywords are:
 * host, hostaddr, port,
 * dbname (defaults to value of user),
 * user,
 * password, connect_timeout,
 * options, tty (ignored), sslmode,
 * requiressl (deprecated in favor of sslmode), and
 * service. Which of these arguments exist depends
 * on your PostgreSQL version.
 * </p>
 * <p>
 * The options parameter can be used to set command line parameters 
 * to be invoked by the server.
 * </p>
 * @param int $connect_type [optional] <p>
 * If PGSQL_CONNECT_FORCE_NEW is passed, then a new connection
 * is created, even if the connection_string is identical to
 * an existing connection.
 * </p>
 * <p>
 * If PGSQL_CONNECT_ASYNC is given, then the
 * connection is established asynchronously. The state of the connection
 * can then be checked via pg_connect_poll or
 * pg_connection_status.
 * </p>
 * @return resource PostgreSQL connection resource on success, false on failure.
 */
function pg_connect (string $connection_string, int $connect_type = null) {}

/**
 * Open a persistent PostgreSQL connection
 * @link http://www.php.net/manual/en/function.pg-pconnect.php
 * @param string $connection_string <p>
 * The connection_string can be empty to use all default parameters, or it 
 * can contain one or more parameter settings separated by whitespace. 
 * Each parameter setting is in the form keyword = value. Spaces around 
 * the equal sign are optional. To write an empty value or a value 
 * containing spaces, surround it with single quotes, e.g., keyword = 
 * 'a value'. Single quotes and backslashes within the value must be 
 * escaped with a backslash, i.e., \' and \\. 
 * </p>
 * <p>
 * The currently recognized parameter keywords are:
 * host, hostaddr, port,
 * dbname, user,
 * password, connect_timeout,
 * options, tty (ignored), sslmode,
 * requiressl (deprecated in favor of sslmode), and
 * service. Which of these arguments exist depends
 * on your PostgreSQL version.
 * </p>
 * @param int $connect_type [optional] If PGSQL_CONNECT_FORCE_NEW is passed, then a new connection
 * is created, even if the connection_string is identical to
 * an existing connection.
 * @return resource PostgreSQL connection resource on success, false on failure.
 */
function pg_pconnect (string $connection_string, int $connect_type = null) {}

/**
 * Poll the status of an in-progress asynchronous PostgreSQL connection
 * attempt.
 * @link http://www.php.net/manual/en/function.pg-connect-poll.php
 * @param resource $connection [optional] PostgreSQL database connection resource.
 * @return int PGSQL_POLLING_FAILED,
 * PGSQL_POLLING_READING,
 * PGSQL_POLLING_WRITING,
 * PGSQL_POLLING_OK, or
 * PGSQL_POLLING_ACTIVE.
 */
function pg_connect_poll ($connection = null) {}

/**
 * Closes a PostgreSQL connection
 * @link http://www.php.net/manual/en/function.pg-close.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @return bool true on success or false on failure
 */
function pg_close ($connection = null) {}

/**
 * Get connection status
 * @link http://www.php.net/manual/en/function.pg-connection-status.php
 * @param resource $connection PostgreSQL database connection resource.
 * @return int PGSQL_CONNECTION_OK or 
 * PGSQL_CONNECTION_BAD.
 */
function pg_connection_status ($connection) {}

/**
 * Get connection is busy or not
 * @link http://www.php.net/manual/en/function.pg-connection-busy.php
 * @param resource $connection PostgreSQL database connection resource.
 * @return bool true if the connection is busy, false otherwise.
 */
function pg_connection_busy ($connection) {}

/**
 * Reset connection (reconnect)
 * @link http://www.php.net/manual/en/function.pg-connection-reset.php
 * @param resource $connection PostgreSQL database connection resource.
 * @return bool true on success or false on failure
 */
function pg_connection_reset ($connection) {}

/**
 * Returns the host name associated with the connection
 * @link http://www.php.net/manual/en/function.pg-host.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @return string A string containing the name of the host the 
 * connection is to, or false on error.
 */
function pg_host ($connection = null) {}

/**
 * Get the database name
 * @link http://www.php.net/manual/en/function.pg-dbname.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @return string A string containing the name of the database the 
 * connection is to, or false on error.
 */
function pg_dbname ($connection = null) {}

/**
 * Return the port number associated with the connection
 * @link http://www.php.net/manual/en/function.pg-port.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @return int An int containing the port number of the database
 * server the connection is to, 
 * or false on error.
 */
function pg_port ($connection = null) {}

/**
 * Return the TTY name associated with the connection
 * @link http://www.php.net/manual/en/function.pg-tty.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @return string A string containing the debug TTY of 
 * the connection, or false on error.
 */
function pg_tty ($connection = null) {}

/**
 * Get the options associated with the connection
 * @link http://www.php.net/manual/en/function.pg-options.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @return string A string containing the connection
 * options, or false on error.
 */
function pg_options ($connection = null) {}

/**
 * Returns an array with client, protocol and server version (when available)
 * @link http://www.php.net/manual/en/function.pg-version.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @return array an array with client, protocol 
 * and server keys and values (if available). Returns
 * false on error or invalid connection.
 */
function pg_version ($connection = null) {}

/**
 * Ping database connection
 * @link http://www.php.net/manual/en/function.pg-ping.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @return bool true on success or false on failure
 */
function pg_ping ($connection = null) {}

/**
 * Looks up a current parameter setting of the server.
 * @link http://www.php.net/manual/en/function.pg-parameter-status.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @param string $param_name Possible param_name values include server_version, 
 * server_encoding, client_encoding, 
 * is_superuser, session_authorization, 
 * DateStyle, TimeZone, and 
 * integer_datetimes.
 * @return string A string containing the value of the parameter, false on failure or invalid
 * param_name.
 */
function pg_parameter_status ($connection = null, string $param_name) {}

/**
 * Returns the current in-transaction status of the server.
 * @link http://www.php.net/manual/en/function.pg-transaction-status.php
 * @param resource $connection PostgreSQL database connection resource.
 * @return int The status can be PGSQL_TRANSACTION_IDLE (currently idle),
 * PGSQL_TRANSACTION_ACTIVE (a command is in progress),
 * PGSQL_TRANSACTION_INTRANS (idle, in a valid transaction block),
 * or PGSQL_TRANSACTION_INERROR (idle, in a failed transaction block).
 * PGSQL_TRANSACTION_UNKNOWN is reported if the connection is bad.
 * PGSQL_TRANSACTION_ACTIVE is reported only when a query
 * has been sent to the server and not yet completed.
 */
function pg_transaction_status ($connection) {}

/**
 * Execute a query
 * @link http://www.php.net/manual/en/function.pg-query.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @param string $query <p>
 * The SQL statement or statements to be executed. When multiple statements are passed to the function,
 * they are automatically executed as one transaction, unless there are explicit BEGIN/COMMIT commands
 * included in the query string. However, using multiple transactions in one function call is not recommended.
 * </p>
 * <p>
 * String interpolation of user-supplied data is extremely dangerous and is
 * likely to lead to SQL
 * injection vulnerabilities. In most cases
 * pg_query_params should be preferred, passing
 * user-supplied values as parameters rather than substituting them into
 * the query string.
 * </p>
 * <p>
 * Any user-supplied data substituted directly into a query string should
 * be properly escaped.
 * </p>
 * @return resource A query result resource on success or false on failure.
 */
function pg_query ($connection = null, string $query) {}

/**
 * Submits a command to the server and waits for the result, with the ability to pass parameters separately from the SQL command text.
 * @link http://www.php.net/manual/en/function.pg-query-params.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @param string $query <p>
 * The parameterized SQL statement. Must contain only a single statement.
 * (multiple statements separated by semi-colons are not allowed.) If any parameters 
 * are used, they are referred to as $1, $2, etc.
 * </p>
 * <p>
 * User-supplied values should always be passed as parameters, not
 * interpolated into the query string, where they form possible
 * SQL injection
 * attack vectors and introduce bugs when handling data containing quotes.
 * If for some reason you cannot use a parameter, ensure that interpolated
 * values are properly escaped.
 * </p>
 * @param array $params <p>
 * An array of parameter values to substitute for the $1, $2, etc. placeholders
 * in the original prepared query string. The number of elements in the array
 * must match the number of placeholders.
 * </p>
 * <p>
 * Values intended for bytea fields are not supported as
 * parameters. Use pg_escape_bytea instead, or use the
 * large object functions.
 * </p>
 * @return resource A query result resource on success or false on failure.
 */
function pg_query_params ($connection = null, string $query, array $params) {}

/**
 * Submits a request to create a prepared statement with the 
 * given parameters, and waits for completion.
 * @link http://www.php.net/manual/en/function.pg-prepare.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @param string $stmtname The name to give the prepared statement. Must be unique per-connection. If
 * "" is specified, then an unnamed statement is created, overwriting any
 * previously defined unnamed statement.
 * @param string $query The parameterized SQL statement. Must contain only a single statement.
 * (multiple statements separated by semi-colons are not allowed.) If any parameters 
 * are used, they are referred to as $1, $2, etc.
 * @return resource A query result resource on success or false on failure.
 */
function pg_prepare ($connection = null, string $stmtname, string $query) {}

/**
 * Sends a request to execute a prepared statement with given parameters, and waits for the result.
 * @link http://www.php.net/manual/en/function.pg-execute.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @param string $stmtname The name of the prepared statement to execute. if
 * "" is specified, then the unnamed statement is executed. The name must have
 * been previously prepared using pg_prepare, 
 * pg_send_prepare or a PREPARE SQL
 * command.
 * @param array $params <p>
 * An array of parameter values to substitute for the $1, $2, etc. placeholders
 * in the original prepared query string. The number of elements in the array
 * must match the number of placeholders.
 * </p>
 * <p>
 * Elements are converted to strings by calling this function.
 * </p>
 * @return resource A query result resource on success or false on failure.
 */
function pg_execute ($connection = null, string $stmtname, array $params) {}

/**
 * Sends asynchronous query
 * @link http://www.php.net/manual/en/function.pg-send-query.php
 * @param resource $connection PostgreSQL database connection resource.
 * @param string $query <p>
 * The SQL statement or statements to be executed.
 * </p>
 * <p>
 * Data inside the query should be properly escaped.
 * </p>
 * @return bool true on success or false on failure
 * <p>
 * Use pg_get_result to determine the query result.
 * </p>
 */
function pg_send_query ($connection, string $query) {}

/**
 * Submits a command and separate parameters to the server without waiting for the result(s).
 * @link http://www.php.net/manual/en/function.pg-send-query-params.php
 * @param resource $connection PostgreSQL database connection resource.
 * @param string $query The parameterized SQL statement. Must contain only a single statement.
 * (multiple statements separated by semi-colons are not allowed.) If any parameters 
 * are used, they are referred to as $1, $2, etc.
 * @param array $params An array of parameter values to substitute for the $1, $2, etc. placeholders
 * in the original prepared query string. The number of elements in the array
 * must match the number of placeholders.
 * @return bool true on success or false on failure
 * <p>
 * Use pg_get_result to determine the query result.
 * </p>
 */
function pg_send_query_params ($connection, string $query, array $params) {}

/**
 * Sends a request to create a prepared statement with the given parameters, without waiting for completion.
 * @link http://www.php.net/manual/en/function.pg-send-prepare.php
 * @param resource $connection PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @param string $stmtname The name to give the prepared statement. Must be unique per-connection. If
 * "" is specified, then an unnamed statement is created, overwriting any
 * previously defined unnamed statement.
 * @param string $query The parameterized SQL statement. Must contain only a single statement.
 * (multiple statements separated by semi-colons are not allowed.) If any parameters 
 * are used, they are referred to as $1, $2, etc.
 * @return bool true on success, false on failure. Use pg_get_result
 * to determine the query result.
 */
function pg_send_prepare ($connection, string $stmtname, string $query) {}

/**
 * Sends a request to execute a prepared statement with given parameters, without waiting for the result(s).
 * @link http://www.php.net/manual/en/function.pg-send-execute.php
 * @param resource $connection PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @param string $stmtname The name of the prepared statement to execute. if
 * "" is specified, then the unnamed statement is executed. The name must have
 * been previously prepared using pg_prepare, 
 * pg_send_prepare or a PREPARE SQL
 * command.
 * @param array $params An array of parameter values to substitute for the $1, $2, etc. placeholders
 * in the original prepared query string. The number of elements in the array
 * must match the number of placeholders.
 * @return bool true on success, false on failure. Use pg_get_result
 * to determine the query result.
 */
function pg_send_execute ($connection, string $stmtname, array $params) {}

/**
 * Cancel an asynchronous query
 * @link http://www.php.net/manual/en/function.pg-cancel-query.php
 * @param resource $connection PostgreSQL database connection resource.
 * @return bool true on success or false on failure
 */
function pg_cancel_query ($connection) {}

/**
 * Returns values from a result resource
 * @link http://www.php.net/manual/en/function.pg-fetch-result.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @param int $row Row number in result to fetch. Rows are numbered from 0 upwards. If omitted,
 * next row is fetched.
 * @param mixed $field A string representing the name of the field (column) to fetch, otherwise
 * an int representing the field number to fetch. Fields are
 * numbered from 0 upwards.
 * @return string Boolean is returned as &quot;t&quot; or &quot;f&quot;. All
 * other types, including arrays are returned as strings formatted
 * in the same default PostgreSQL manner that you would see in the
 * psql program. Database NULL
 * values are returned as null.
 * <p>
 * false is returned if row exceeds the number
 * of rows in the set, or on any other error.
 * </p>
 */
function pg_fetch_result ($result, int $row, $field) {}

/**
 * Get a row as an enumerated array
 * @link http://www.php.net/manual/en/function.pg-fetch-row.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @param int $row [optional] Row number in result to fetch. Rows are numbered from 0 upwards. If
 * omitted or null, the next row is fetched.
 * @param int $result_type [optional] 
 * @return array An array, indexed from 0 upwards, with each value
 * represented as a string. Database NULL
 * values are returned as null.
 * <p>
 * false is returned if row exceeds the number
 * of rows in the set, there are no more rows, or on any other error.
 * </p>
 */
function pg_fetch_row ($result, int $row = null, int $result_type = null) {}

/**
 * Fetch a row as an associative array
 * @link http://www.php.net/manual/en/function.pg-fetch-assoc.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @param int $row [optional] Row number in result to fetch. Rows are numbered from 0 upwards. If
 * omitted or null, the next row is fetched.
 * @return array An array indexed associatively (by field name).
 * Each value in the array is represented as a 
 * string. Database NULL
 * values are returned as null.
 * <p>
 * false is returned if row exceeds the number
 * of rows in the set, there are no more rows, or on any other error.
 * </p>
 */
function pg_fetch_assoc ($result, int $row = null) {}

/**
 * Fetch a row as an array
 * @link http://www.php.net/manual/en/function.pg-fetch-array.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @param int $row [optional] Row number in result to fetch. Rows are numbered from 0 upwards. If
 * omitted or null, the next row is fetched.
 * @param int $result_type [optional] An optional parameter that controls
 * how the returned array is indexed.
 * result_type is a constant and can take the
 * following values: PGSQL_ASSOC, 
 * PGSQL_NUM and PGSQL_BOTH.
 * Using PGSQL_NUM, pg_fetch_array
 * will return an array with numerical indices, using
 * PGSQL_ASSOC it will return only associative indices
 * while PGSQL_BOTH, the default, will return both
 * numerical and associative indices.
 * @return array An array indexed numerically (beginning with 0) or
 * associatively (indexed by field name), or both.
 * Each value in the array is represented as a 
 * string. Database NULL
 * values are returned as null.
 * <p>
 * false is returned if row exceeds the number
 * of rows in the set, there are no more rows, or on any other error.
 * </p>
 */
function pg_fetch_array ($result, int $row = null, int $result_type = null) {}

/**
 * Fetch a row as an object
 * @link http://www.php.net/manual/en/function.pg-fetch-object.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @param int $row [optional] Row number in result to fetch. Rows are numbered from 0 upwards. If
 * omitted or null, the next row is fetched.
 * @param int $result_type [optional] Ignored and deprecated.
 * @return object An object with one attribute for each field
 * name in the result. Database NULL
 * values are returned as null.
 * <p>
 * false is returned if row exceeds the number
 * of rows in the set, there are no more rows, or on any other error.
 * </p>
 */
function pg_fetch_object ($result, int $row = null, int $result_type = null) {}

/**
 * Fetches all rows from a result as an array
 * @link http://www.php.net/manual/en/function.pg-fetch-all.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @return array An array with all rows in the result. Each row is an array
 * of field values indexed by field name.
 * <p>
 * false is returned if there are no rows in the result, or on any
 * other error.
 * </p>
 */
function pg_fetch_all ($result) {}

/**
 * Fetches all rows in a particular result column as an array
 * @link http://www.php.net/manual/en/function.pg-fetch-all-columns.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @param int $column [optional] Column number, zero-based, to be retrieved from the result resource. Defaults
 * to the first column if not specified.
 * @return array An array with all values in the result column.
 * <p>
 * false is returned if column is larger than the number 
 * of columns in the result, or on any other error.
 * </p>
 */
function pg_fetch_all_columns ($result, int $column = null) {}

/**
 * Returns number of affected records (tuples)
 * @link http://www.php.net/manual/en/function.pg-affected-rows.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @return int The number of rows affected by the query. If no tuple is
 * affected, it will return 0.
 */
function pg_affected_rows ($result) {}

/**
 * Get asynchronous query result
 * @link http://www.php.net/manual/en/function.pg-get-result.php
 * @param resource $connection [optional] PostgreSQL database connection resource.
 * @return resource The result resource, or false if no more results are available.
 */
function pg_get_result ($connection = null) {}

/**
 * Set internal row offset in result resource
 * @link http://www.php.net/manual/en/function.pg-result-seek.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @param int $offset Row to move the internal offset to in the result resource.
 * Rows are numbered starting from zero.
 * @return bool true on success or false on failure
 */
function pg_result_seek ($result, int $offset) {}

/**
 * Get status of query result
 * @link http://www.php.net/manual/en/function.pg-result-status.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @param int $type [optional] Either PGSQL_STATUS_LONG to return the numeric status 
 * of the result, or PGSQL_STATUS_STRING 
 * to return the command tag of the result.
 * If not specified, PGSQL_STATUS_LONG is the default.
 * @return mixed Possible return values are PGSQL_EMPTY_QUERY,
 * PGSQL_COMMAND_OK, PGSQL_TUPLES_OK, PGSQL_COPY_OUT,
 * PGSQL_COPY_IN, PGSQL_BAD_RESPONSE, PGSQL_NONFATAL_ERROR and
 * PGSQL_FATAL_ERROR if PGSQL_STATUS_LONG is
 * specified. Otherwise, a string containing the PostgreSQL command tag is returned.
 */
function pg_result_status ($result, int $type = null) {}

/**
 * Free result memory
 * @link http://www.php.net/manual/en/function.pg-free-result.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @return bool true on success or false on failure
 */
function pg_free_result ($result) {}

/**
 * Returns the last row's OID
 * @link http://www.php.net/manual/en/function.pg-last-oid.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @return string A string containing the OID assigned to the most recently inserted
 * row in the specified connection, or false on error or
 * no available OID.
 */
function pg_last_oid ($result) {}

/**
 * Returns the number of rows in a result
 * @link http://www.php.net/manual/en/function.pg-num-rows.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @return int The number of rows in the result. On error, -1 is returned.
 */
function pg_num_rows ($result) {}

/**
 * Returns the number of fields in a result
 * @link http://www.php.net/manual/en/function.pg-num-fields.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @return int The number of fields (columns) in the result. On error, -1 is returned.
 */
function pg_num_fields ($result) {}

/**
 * Returns the name of a field
 * @link http://www.php.net/manual/en/function.pg-field-name.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @param int $field_number Field number, starting from 0.
 * @return string The field name, or false on error.
 */
function pg_field_name ($result, int $field_number) {}

/**
 * Returns the field number of the named field
 * @link http://www.php.net/manual/en/function.pg-field-num.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @param string $field_name The name of the field.
 * @return int The field number (numbered from 0), or -1 on error.
 */
function pg_field_num ($result, string $field_name) {}

/**
 * Returns the internal storage size of the named field
 * @link http://www.php.net/manual/en/function.pg-field-size.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @param int $field_number Field number, starting from 0.
 * @return int The internal field storage size (in bytes). -1 indicates a variable
 * length field. false is returned on error.
 */
function pg_field_size ($result, int $field_number) {}

/**
 * Returns the type name for the corresponding field number
 * @link http://www.php.net/manual/en/function.pg-field-type.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @param int $field_number Field number, starting from 0.
 * @return string A string containing the base name of the field's type, or false
 * on error.
 */
function pg_field_type ($result, int $field_number) {}

/**
 * Returns the type ID (OID) for the corresponding field number
 * @link http://www.php.net/manual/en/function.pg-field-type-oid.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @param int $field_number Field number, starting from 0.
 * @return int The OID of the field's base type. false is returned on error.
 */
function pg_field_type_oid ($result, int $field_number) {}

/**
 * Returns the printed length
 * @link http://www.php.net/manual/en/function.pg-field-prtlen.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @param int $row_number 
 * @param mixed $field_name_or_number 
 * @return int The field printed length, or false on error.
 */
function pg_field_prtlen ($result, int $row_number, $field_name_or_number) {}

/**
 * Test if a field is SQL NULL
 * @link http://www.php.net/manual/en/function.pg-field-is-null.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @param int $row Row number in result to fetch. Rows are numbered from 0 upwards. If omitted,
 * current row is fetched.
 * @param mixed $field Field number (starting from 0) as an integer or 
 * the field name as a string.
 * @return int 1 if the field in the given row is SQL NULL, 0
 * if not. false is returned if the row is out of range, or upon any other error.
 */
function pg_field_is_null ($result, int $row, $field) {}

/**
 * Returns the name or oid of the tables field
 * @link http://www.php.net/manual/en/function.pg-field-table.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @param int $field_number Field number, starting from 0.
 * @param bool $oid_only [optional] By default the tables name that field belongs to is returned but
 * if oid_only is set to true, then the
 * oid will instead be returned.
 * @return mixed On success either the fields table name or oid. Or, false on failure.
 */
function pg_field_table ($result, int $field_number, bool $oid_only = null) {}

/**
 * Gets SQL NOTIFY message
 * @link http://www.php.net/manual/en/function.pg-get-notify.php
 * @param resource $connection PostgreSQL database connection resource.
 * @param int $result_type [optional] An optional parameter that controls
 * how the returned array is indexed.
 * result_type is a constant and can take the
 * following values: PGSQL_ASSOC, 
 * PGSQL_NUM and PGSQL_BOTH.
 * Using PGSQL_NUM, pg_get_notify
 * will return an array with numerical indices, using
 * PGSQL_ASSOC it will return only associative indices
 * while PGSQL_BOTH, the default, will return both
 * numerical and associative indices.
 * @return array An array containing the NOTIFY message name and backend PID.
 * As of PHP 5.4.0 and if supported by the server, the array also contains the server version and the payload.
 * Otherwise if no NOTIFY is waiting, then false is returned.
 */
function pg_get_notify ($connection, int $result_type = null) {}

/**
 * Get a read only handle to the socket underlying a PostgreSQL connection
 * @link http://www.php.net/manual/en/function.pg-socket.php
 * @param resource $connection PostgreSQL database connection resource.
 * @return resource A socket resource on success or false on failure.
 */
function pg_socket ($connection) {}

/**
 * Reads input on the connection
 * @link http://www.php.net/manual/en/function.pg-consume-input.php
 * @param resource $connection PostgreSQL database connection resource.
 * @return bool true if no error occurred, or false if there was an error. Note that
 * true does not necessarily indicate that input was waiting to be read.
 */
function pg_consume_input ($connection) {}

/**
 * Flush outbound query data on the connection
 * @link http://www.php.net/manual/en/function.pg-flush.php
 * @param resource $connection PostgreSQL database connection resource.
 * @return mixed true if the flush was successful or no data was waiting to be
 * flushed, 0 if part of the pending data was flushed but
 * more remains or false on failure.
 */
function pg_flush ($connection) {}

/**
 * Gets the backend's process ID
 * @link http://www.php.net/manual/en/function.pg-get-pid.php
 * @param resource $connection PostgreSQL database connection resource.
 * @return int The backend database process ID.
 */
function pg_get_pid ($connection) {}

/**
 * Get error message associated with result
 * @link http://www.php.net/manual/en/function.pg-result-error.php
 * @param resource $result PostgreSQL query result resource, returned by pg_query,
 * pg_query_params or pg_execute
 * (among others).
 * @return string a string. Returns empty string if there is no error. If there is an error associated with the
 * result parameter, returns false.
 */
function pg_result_error ($result) {}

/**
 * Returns an individual field of an error report.
 * @link http://www.php.net/manual/en/function.pg-result-error-field.php
 * @param resource $result A PostgreSQL query result resource from a previously executed
 * statement.
 * @param int $fieldcode Possible fieldcode values are: PGSQL_DIAG_SEVERITY,
 * PGSQL_DIAG_SQLSTATE, PGSQL_DIAG_MESSAGE_PRIMARY,
 * PGSQL_DIAG_MESSAGE_DETAIL,
 * PGSQL_DIAG_MESSAGE_HINT, PGSQL_DIAG_STATEMENT_POSITION,
 * PGSQL_DIAG_INTERNAL_POSITION (PostgreSQL 8.0+ only),
 * PGSQL_DIAG_INTERNAL_QUERY (PostgreSQL 8.0+ only),
 * PGSQL_DIAG_CONTEXT, PGSQL_DIAG_SOURCE_FILE,
 * PGSQL_DIAG_SOURCE_LINE or
 * PGSQL_DIAG_SOURCE_FUNCTION.
 * @return string A string containing the contents of the error field, null if the field does not exist or false
 * on failure.
 */
function pg_result_error_field ($result, int $fieldcode) {}

/**
 * Get the last error message string of a connection
 * @link http://www.php.net/manual/en/function.pg-last-error.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @return string A string containing the last error message on the 
 * given connection, or false on error.
 */
function pg_last_error ($connection = null) {}

/**
 * Returns the last notice message from PostgreSQL server
 * @link http://www.php.net/manual/en/function.pg-last-notice.php
 * @param resource $connection PostgreSQL database connection resource.
 * @return string A string containing the last notice on the 
 * given connection, or false on error.
 */
function pg_last_notice ($connection) {}

/**
 * Send a NULL-terminated string to PostgreSQL backend
 * @link http://www.php.net/manual/en/function.pg-put-line.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @param string $data A line of text to be sent directly to the PostgreSQL backend. A NULL
 * terminator is added automatically.
 * @return bool true on success or false on failure
 */
function pg_put_line ($connection = null, string $data) {}

/**
 * Sync with PostgreSQL backend
 * @link http://www.php.net/manual/en/function.pg-end-copy.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @return bool true on success or false on failure
 */
function pg_end_copy ($connection = null) {}

/**
 * Copy a table to an array
 * @link http://www.php.net/manual/en/function.pg-copy-to.php
 * @param resource $connection PostgreSQL database connection resource.
 * @param string $table_name Name of the table from which to copy the data into rows.
 * @param string $delimiter [optional] The token that separates values for each field in each element of
 * rows. Default is TAB.
 * @param string $null_as [optional] How SQL NULL values are represented in the
 * rows. Default is \N ("\\N").
 * @return array An array with one element for each line of COPY data.
 * It returns false on failure.
 */
function pg_copy_to ($connection, string $table_name, string $delimiter = null, string $null_as = null) {}

/**
 * Insert records into a table from an array
 * @link http://www.php.net/manual/en/function.pg-copy-from.php
 * @param resource $connection PostgreSQL database connection resource.
 * @param string $table_name Name of the table into which to copy the rows.
 * @param array $rows An array of data to be copied into table_name.
 * Each value in rows becomes a row in table_name.
 * Each value in rows should be a delimited string of the values
 * to insert into each field. Values should be linefeed terminated.
 * @param string $delimiter [optional] The token that separates values for each field in each element of
 * rows. Default is TAB.
 * @param string $null_as [optional] How SQL NULL values are represented in the
 * rows. Default is \N ("\\N").
 * @return bool true on success or false on failure
 */
function pg_copy_from ($connection, string $table_name, array $rows, string $delimiter = null, string $null_as = null) {}

/**
 * Enable tracing a PostgreSQL connection
 * @link http://www.php.net/manual/en/function.pg-trace.php
 * @param string $pathname The full path and file name of the file in which to write the
 * trace log. Same as in fopen.
 * @param string $mode [optional] An optional file access mode, same as for fopen.
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @return bool true on success or false on failure
 */
function pg_trace (string $pathname, string $mode = null, $connection = null) {}

/**
 * Disable tracing of a PostgreSQL connection
 * @link http://www.php.net/manual/en/function.pg-untrace.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @return bool Always returns true.
 */
function pg_untrace ($connection = null) {}

/**
 * Create a large object
 * @link http://www.php.net/manual/en/function.pg-lo-create.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @param mixed $object_id [optional] If an object_id is given the function
 * will try to create a large object with this id, else a free
 * object id is assigned by the server. The parameter
 * was added in PHP 5.3 and relies on functionality that first
 * appeared in PostgreSQL 8.1.
 * @return int A large object OID or false on error.
 */
function pg_lo_create ($connection = null, $object_id = null) {}

/**
 * Delete a large object
 * @link http://www.php.net/manual/en/function.pg-lo-unlink.php
 * @param resource $connection PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @param int $oid The OID of the large object in the database.
 * @return bool true on success or false on failure
 */
function pg_lo_unlink ($connection, int $oid) {}

/**
 * Open a large object
 * @link http://www.php.net/manual/en/function.pg-lo-open.php
 * @param resource $connection PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @param int $oid The OID of the large object in the database.
 * @param string $mode Can be either "r" for read-only, "w" for write only or "rw" for read and 
 * write.
 * @return resource A large object resource or false on error.
 */
function pg_lo_open ($connection, int $oid, string $mode) {}

/**
 * Close a large object
 * @link http://www.php.net/manual/en/function.pg-lo-close.php
 * @param resource $large_object 
 * @return bool true on success or false on failure
 */
function pg_lo_close ($large_object) {}

/**
 * Read a large object
 * @link http://www.php.net/manual/en/function.pg-lo-read.php
 * @param resource $large_object PostgreSQL large object (LOB) resource, returned by pg_lo_open.
 * @param int $len [optional] An optional maximum number of bytes to return.
 * @return string A string containing len bytes from the
 * large object, or false on error.
 */
function pg_lo_read ($large_object, int $len = null) {}

/**
 * Write to a large object
 * @link http://www.php.net/manual/en/function.pg-lo-write.php
 * @param resource $large_object PostgreSQL large object (LOB) resource, returned by pg_lo_open.
 * @param string $data The data to be written to the large object. If len is
 * specified and is less than the length of data, only
 * len bytes will be written.
 * @param int $len [optional] An optional maximum number of bytes to write. Must be greater than zero
 * and no greater than the length of data. Defaults to
 * the length of data.
 * @return int The number of bytes written to the large object, or false on error.
 */
function pg_lo_write ($large_object, string $data, int $len = null) {}

/**
 * Reads an entire large object and send straight to browser
 * @link http://www.php.net/manual/en/function.pg-lo-read-all.php
 * @param resource $large_object PostgreSQL large object (LOB) resource, returned by pg_lo_open.
 * @return int Number of bytes read or false on error.
 */
function pg_lo_read_all ($large_object) {}

/**
 * Import a large object from file
 * @link http://www.php.net/manual/en/function.pg-lo-import.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @param string $pathname The full path and file name of the file on the client
 * filesystem from which to read the large object data.
 * @param mixed $object_id [optional] If an object_id is given the function
 * will try to create a large object with this id, else a free
 * object id is assigned by the server. The parameter
 * was added in PHP 5.3 and relies on functionality that first
 * appeared in PostgreSQL 8.1.
 * @return int The OID of the newly created large object, or
 * false on failure.
 */
function pg_lo_import ($connection = null, string $pathname, $object_id = null) {}

/**
 * Export a large object to file
 * @link http://www.php.net/manual/en/function.pg-lo-export.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @param int $oid The OID of the large object in the database.
 * @param string $pathname The full path and file name of the file in which to write the
 * large object on the client filesystem.
 * @return bool true on success or false on failure
 */
function pg_lo_export ($connection = null, int $oid, string $pathname) {}

/**
 * Seeks position within a large object
 * @link http://www.php.net/manual/en/function.pg-lo-seek.php
 * @param resource $large_object PostgreSQL large object (LOB) resource, returned by pg_lo_open.
 * @param int $offset The number of bytes to seek.
 * @param int $whence [optional] One of the constants PGSQL_SEEK_SET (seek from object start), 
 * PGSQL_SEEK_CUR (seek from current position)
 * or PGSQL_SEEK_END (seek from object end) .
 * @return bool true on success or false on failure
 */
function pg_lo_seek ($large_object, int $offset, int $whence = null) {}

/**
 * Returns current seek position a of large object
 * @link http://www.php.net/manual/en/function.pg-lo-tell.php
 * @param resource $large_object PostgreSQL large object (LOB) resource, returned by pg_lo_open.
 * @return int The current seek offset (in number of bytes) from the beginning of the large
 * object. If there is an error, the return value is negative.
 */
function pg_lo_tell ($large_object) {}

/**
 * Truncates a large object
 * @link http://www.php.net/manual/en/function.pg-lo-truncate.php
 * @param resource $large_object PostgreSQL large object (LOB) resource, returned by pg_lo_open.
 * @param int $size The number of bytes to truncate.
 * @return bool true on success or false on failure
 */
function pg_lo_truncate ($large_object, int $size) {}

/**
 * Escape a string for query
 * @link http://www.php.net/manual/en/function.pg-escape-string.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @param string $data A string containing text to be escaped.
 * @return string A string containing the escaped data.
 */
function pg_escape_string ($connection = null, string $data) {}

/**
 * Escape a string for insertion into a bytea field
 * @link http://www.php.net/manual/en/function.pg-escape-bytea.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @param string $data A string containing text or binary data to be inserted into a bytea
 * column.
 * @return string A string containing the escaped data.
 */
function pg_escape_bytea ($connection = null, string $data) {}

/**
 * Unescape binary for bytea type
 * @link http://www.php.net/manual/en/function.pg-unescape-bytea.php
 * @param string $data A string containing PostgreSQL bytea data to be converted into
 * a PHP binary string.
 * @return string A string containing the unescaped data.
 */
function pg_unescape_bytea (string $data) {}

/**
 * Escape a literal for insertion into a text field
 * @link http://www.php.net/manual/en/function.pg-escape-literal.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * When there is no default connection, it raises E_WARNING
 * and returns false.
 * @param string $data A string containing text to be escaped.
 * @return string A string containing the escaped data.
 */
function pg_escape_literal ($connection = null, string $data) {}

/**
 * Escape a identifier for insertion into a text field
 * @link http://www.php.net/manual/en/function.pg-escape-identifier.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @param string $data A string containing text to be escaped.
 * @return string A string containing the escaped data.
 */
function pg_escape_identifier ($connection = null, string $data) {}

/**
 * Determines the verbosity of messages returned by pg_last_error 
 * and pg_result_error.
 * @link http://www.php.net/manual/en/function.pg-set-error-verbosity.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @param int $verbosity The required verbosity: PGSQL_ERRORS_TERSE,
 * PGSQL_ERRORS_DEFAULT
 * or PGSQL_ERRORS_VERBOSE.
 * @return int The previous verbosity level: PGSQL_ERRORS_TERSE,
 * PGSQL_ERRORS_DEFAULT
 * or PGSQL_ERRORS_VERBOSE.
 */
function pg_set_error_verbosity ($connection = null, int $verbosity) {}

/**
 * Gets the client encoding
 * @link http://www.php.net/manual/en/function.pg-client-encoding.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @return string The client encoding, or false on error.
 */
function pg_client_encoding ($connection = null) {}

/**
 * Set the client encoding
 * @link http://www.php.net/manual/en/function.pg-set-client-encoding.php
 * @param resource $connection [optional] PostgreSQL database connection resource. When 
 * connection is not present, the default connection 
 * is used. The default connection is the last connection made by 
 * pg_connect or pg_pconnect.
 * @param string $encoding <p>
 * The required client encoding. One of SQL_ASCII, EUC_JP, 
 * EUC_CN, EUC_KR, EUC_TW, 
 * UNICODE, MULE_INTERNAL, LATINX (X=1...9), 
 * KOI8, WIN, ALT, SJIS, 
 * BIG5 or WIN1250.
 * </p>
 * <p>
 * The exact list of available encodings depends on your PostgreSQL version, so check your
 * PostgreSQL manual for a more specific list.
 * </p>
 * @return int 0 on success or -1 on error.
 */
function pg_set_client_encoding ($connection = null, string $encoding) {}

/**
 * Get meta data for table
 * @link http://www.php.net/manual/en/function.pg-meta-data.php
 * @param resource $connection PostgreSQL database connection resource.
 * @param string $table_name The name of the table.
 * @param bool $extended [optional] Flag for returning extended meta data. Default to false.
 * @return array An array of the table definition, or false on error.
 */
function pg_meta_data ($connection, string $table_name, bool $extended = null) {}

/**
 * Convert associative array values into forms suitable for SQL statements
 * @link http://www.php.net/manual/en/function.pg-convert.php
 * @param resource $connection PostgreSQL database connection resource.
 * @param string $table_name Name of the table against which to convert types.
 * @param array $assoc_array Data to be converted.
 * @param int $options [optional] Any number of PGSQL_CONV_IGNORE_DEFAULT,
 * PGSQL_CONV_FORCE_NULL or
 * PGSQL_CONV_IGNORE_NOT_NULL, combined.
 * @return array An array of converted values, or false on error.
 */
function pg_convert ($connection, string $table_name, array $assoc_array, int $options = null) {}

/**
 * Insert array into table
 * @link http://www.php.net/manual/en/function.pg-insert.php
 * @param resource $connection PostgreSQL database connection resource.
 * @param string $table_name Name of the table into which to insert rows. The table table_name must at least 
 * have as many columns as assoc_array has elements.
 * @param array $assoc_array An array whose keys are field names in the table table_name,
 * and whose values are the values of those fields that are to be inserted.
 * @param int $options [optional] Any number of PGSQL_CONV_OPTS,
 * PGSQL_DML_NO_CONV,
 * PGSQL_DML_ESCAPE,
 * PGSQL_DML_EXEC,
 * PGSQL_DML_ASYNC or
 * PGSQL_DML_STRING combined. If PGSQL_DML_STRING is part of the
 * options then query string is returned. When PGSQL_DML_NO_CONV
 * or PGSQL_DML_ESCAPE is set, it does not call pg_convert internally.
 * @return mixed the connection resource on success, or false on failure. Returns string if PGSQL_DML_STRING is passed
 * via options.
 */
function pg_insert ($connection, string $table_name, array $assoc_array, int $options = null) {}

/**
 * Update table
 * @link http://www.php.net/manual/en/function.pg-update.php
 * @param resource $connection PostgreSQL database connection resource.
 * @param string $table_name Name of the table into which to update rows.
 * @param array $data An array whose keys are field names in the table table_name,
 * and whose values are what matched rows are to be updated to.
 * @param array $condition An array whose keys are field names in the table table_name,
 * and whose values are the conditions that a row must meet to be updated.
 * @param int $options [optional] Any number of PGSQL_CONV_FORCE_NULL,
 * PGSQL_DML_NO_CONV,
 * PGSQL_DML_ESCAPE,
 * PGSQL_DML_EXEC,
 * PGSQL_DML_ASYNC or
 * PGSQL_DML_STRING combined. If PGSQL_DML_STRING is part of the
 * options then query string is returned. When PGSQL_DML_NO_CONV
 * or PGSQL_DML_ESCAPE is set, it does not call pg_convert internally.
 * @return mixed true on success or false on failure Returns string if PGSQL_DML_STRING is passed
 * via options.
 */
function pg_update ($connection, string $table_name, array $data, array $condition, int $options = null) {}

/**
 * Deletes records
 * @link http://www.php.net/manual/en/function.pg-delete.php
 * @param resource $connection PostgreSQL database connection resource.
 * @param string $table_name Name of the table from which to delete rows.
 * @param array $assoc_array An array whose keys are field names in the table table_name,
 * and whose values are the values of those fields that are to be deleted.
 * @param int $options [optional] Any number of PGSQL_CONV_FORCE_NULL,
 * PGSQL_DML_NO_CONV,
 * PGSQL_DML_ESCAPE,
 * PGSQL_DML_EXEC,
 * PGSQL_DML_ASYNC or
 * PGSQL_DML_STRING combined. If PGSQL_DML_STRING is part of the
 * options then query string is returned. When PGSQL_DML_NO_CONV
 * or PGSQL_DML_ESCAPE is set, it does not call pg_convert internally.
 * @return mixed true on success or false on failure Returns string if PGSQL_DML_STRING is passed
 * via options.
 */
function pg_delete ($connection, string $table_name, array $assoc_array, int $options = null) {}

/**
 * Select records
 * @link http://www.php.net/manual/en/function.pg-select.php
 * @param resource $connection PostgreSQL database connection resource.
 * @param string $table_name Name of the table from which to select rows.
 * @param array $assoc_array An array whose keys are field names in the table table_name,
 * and whose values are the conditions that a row must meet to be retrieved.
 * @param int $options [optional] Any number of PGSQL_CONV_FORCE_NULL,
 * PGSQL_DML_NO_CONV,
 * PGSQL_DML_ESCAPE,
 * PGSQL_DML_EXEC,
 * PGSQL_DML_ASYNC or
 * PGSQL_DML_STRING combined. If PGSQL_DML_STRING is part of the
 * options then query string is returned. When PGSQL_DML_NO_CONV
 * or PGSQL_DML_ESCAPE is set, it does not call pg_convert internally.
 * @return mixed true on success or false on failure Returns string if PGSQL_DML_STRING is passed
 * via options.
 */
function pg_select ($connection, string $table_name, array $assoc_array, int $options = null) {}

/**
 * @param $connection [optional]
 * @param $query [optional]
 */
function pg_exec ($connection = null, $query = null) {}

/**
 * @param $result
 */
function pg_getlastoid ($result) {}

/**
 * @param $result
 */
function pg_cmdtuples ($result) {}

/**
 * @param $connection [optional]
 */
function pg_errormessage ($connection = null) {}

/**
 * @param $result
 */
function pg_numrows ($result) {}

/**
 * @param $result
 */
function pg_numfields ($result) {}

/**
 * @param $result
 * @param $field_number
 */
function pg_fieldname ($result, $field_number) {}

/**
 * @param $result
 * @param $field_number
 */
function pg_fieldsize ($result, $field_number) {}

/**
 * @param $result
 * @param $field_number
 */
function pg_fieldtype ($result, $field_number) {}

/**
 * @param $result
 * @param $field_name
 */
function pg_fieldnum ($result, $field_name) {}

/**
 * @param $result
 * @param $row [optional]
 * @param $field_name_or_number [optional]
 */
function pg_fieldprtlen ($result, $row = null, $field_name_or_number = null) {}

/**
 * @param $result
 * @param $row [optional]
 * @param $field_name_or_number [optional]
 */
function pg_fieldisnull ($result, $row = null, $field_name_or_number = null) {}

/**
 * @param $result
 */
function pg_freeresult ($result) {}

/**
 * @param $connection
 */
function pg_result ($connection) {}

/**
 * @param $large_object
 */
function pg_loreadall ($large_object) {}

/**
 * @param $connection [optional]
 * @param $large_object_id [optional]
 */
function pg_locreate ($connection = null, $large_object_id = null) {}

/**
 * @param $connection [optional]
 * @param $large_object_oid [optional]
 */
function pg_lounlink ($connection = null, $large_object_oid = null) {}

/**
 * @param $connection [optional]
 * @param $large_object_oid [optional]
 * @param $mode [optional]
 */
function pg_loopen ($connection = null, $large_object_oid = null, $mode = null) {}

/**
 * @param $large_object
 */
function pg_loclose ($large_object) {}

/**
 * @param $large_object
 * @param $len [optional]
 */
function pg_loread ($large_object, $len = null) {}

/**
 * @param $large_object
 * @param $buf
 * @param $len [optional]
 */
function pg_lowrite ($large_object, $buf, $len = null) {}

/**
 * @param $connection [optional]
 * @param $filename [optional]
 * @param $large_object_oid [optional]
 */
function pg_loimport ($connection = null, $filename = null, $large_object_oid = null) {}

/**
 * @param $connection [optional]
 * @param $objoid [optional]
 * @param $filename [optional]
 */
function pg_loexport ($connection = null, $objoid = null, $filename = null) {}

/**
 * @param $connection [optional]
 */
function pg_clientencoding ($connection = null) {}

/**
 * @param $connection [optional]
 * @param $encoding [optional]
 */
function pg_setclientencoding ($connection = null, $encoding = null) {}


/**
 * Short libpq version that contains only numbers and dots.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_LIBPQ_VERSION', "9.5.5");

/**
 * Long libpq version that includes compiler information.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_LIBPQ_VERSION_STR', "PostgreSQL 9.5.5 on x86_64-pc-linux-gnu, compiled by gcc (Ubuntu 5.4.0-6ubuntu1~16.04.2) 5.4.0 20160609, 64-bit");

/**
 * Passed to pg_connect to force the creation of a new connection,
 * rather than re-using an existing identical connection.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_CONNECT_FORCE_NEW', 2);

/**
 * Passed to pg_connect to create an asynchronous
 * connection. Added in PHP 5.6.0.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_CONNECT_ASYNC', 4);

/**
 * Passed to pg_fetch_array. Return an associative array of field
 * names and values.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_ASSOC', 1);

/**
 * Passed to pg_fetch_array. Return a numerically indexed array of field
 * numbers and values.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_NUM', 2);

/**
 * Passed to pg_fetch_array. Return an array of field values
 * that is both numerically indexed (by field number) and associated (by field name).
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_BOTH', 3);
define ('PGSQL_NOTICE_LAST', 1);
define ('PGSQL_NOTICE_ALL', 2);
define ('PGSQL_NOTICE_CLEAR', 3);

/**
 * Returned by pg_connection_status indicating that the database
 * connection is in an invalid state.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_CONNECTION_BAD', 1);

/**
 * Returned by pg_connection_status indicating that the database
 * connection is in a valid state.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_CONNECTION_OK', 0);
define ('PGSQL_CONNECTION_STARTED', 2);
define ('PGSQL_CONNECTION_MADE', 3);
define ('PGSQL_CONNECTION_AWAITING_RESPONSE', 4);
define ('PGSQL_CONNECTION_AUTH_OK', 5);
define ('PGSQL_CONNECTION_SETENV', 6);

/**
 * Returned by pg_connect_poll to indicate that the
 * connection attempt failed.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_POLLING_FAILED', 0);

/**
 * Returned by pg_connect_poll to indicate that the
 * connection is waiting for the PostgreSQL socket to be readable.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_POLLING_READING', 1);

/**
 * Returned by pg_connect_poll to indicate that the
 * connection is waiting for the PostgreSQL socket to be writable.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_POLLING_WRITING', 2);

/**
 * Returned by pg_connect_poll to indicate that the
 * connection is ready to be used.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_POLLING_OK', 3);

/**
 * Returned by pg_connect_poll to indicate that the
 * connection is currently active.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_POLLING_ACTIVE', 4);

/**
 * Returned by pg_transaction_status. Connection is
 * currently idle, not in a transaction.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_TRANSACTION_IDLE', 0);

/**
 * Returned by pg_transaction_status. A command
 * is in progress on the connection. A query has been sent via the connection
 * and not yet completed.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_TRANSACTION_ACTIVE', 1);

/**
 * Returned by pg_transaction_status. The connection
 * is idle, in a transaction block.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_TRANSACTION_INTRANS', 2);

/**
 * Returned by pg_transaction_status. The connection
 * is idle, in a failed transaction block.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_TRANSACTION_INERROR', 3);

/**
 * Returned by pg_transaction_status. The connection
 * is bad.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_TRANSACTION_UNKNOWN', 4);

/**
 * Passed to pg_set_error_verbosity.
 * Specified that returned messages include severity, primary text, 
 * and position only; this will normally fit on a single line.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_ERRORS_TERSE', 0);

/**
 * Passed to pg_set_error_verbosity.
 * The default mode produces messages that include the above 
 * plus any detail, hint, or context fields (these may span 
 * multiple lines).
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_ERRORS_DEFAULT', 1);

/**
 * Passed to pg_set_error_verbosity.
 * The verbose mode includes all available fields.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_ERRORS_VERBOSE', 2);

/**
 * Passed to pg_lo_seek. Seek operation is to begin
 * from the start of the object.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_SEEK_SET', 0);

/**
 * Passed to pg_lo_seek. Seek operation is to begin
 * from the current position.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_SEEK_CUR', 1);

/**
 * Passed to pg_lo_seek. Seek operation is to begin
 * from the end of the object.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_SEEK_END', 2);

/**
 * Passed to pg_result_status. Indicates that
 * numerical result code is desired.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_STATUS_LONG', 1);

/**
 * Passed to pg_result_status. Indicates that
 * textual result command tag is desired.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_STATUS_STRING', 2);

/**
 * Returned by pg_result_status. The string sent to the server
 * was empty.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_EMPTY_QUERY', 0);

/**
 * Returned by pg_result_status. Successful completion of a 
 * command returning no data.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_COMMAND_OK', 1);

/**
 * Returned by pg_result_status. Successful completion of a command 
 * returning data (such as a SELECT or SHOW).
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_TUPLES_OK', 2);

/**
 * Returned by pg_result_status. Copy Out (from server) data 
 * transfer started.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_COPY_OUT', 3);

/**
 * Returned by pg_result_status. Copy In (to server) data 
 * transfer started.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_COPY_IN', 4);

/**
 * Returned by pg_result_status. The server's response 
 * was not understood.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_BAD_RESPONSE', 5);

/**
 * Returned by pg_result_status. A nonfatal error 
 * (a notice or warning) occurred.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_NONFATAL_ERROR', 6);

/**
 * Returned by pg_result_status. A fatal error 
 * occurred.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_FATAL_ERROR', 7);

/**
 * Passed to pg_result_error_field.
 * The severity; the field contents are ERROR, 
 * FATAL, or PANIC (in an error message), or 
 * WARNING, NOTICE, DEBUG, 
 * INFO, or LOG (in a notice message), or a localized 
 * translation of one of these. Always present.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_SEVERITY', 83);

/**
 * Passed to pg_result_error_field.
 * The SQLSTATE code for the error. The SQLSTATE code identifies the type of error 
 * that has occurred; it can be used by front-end applications to perform specific 
 * operations (such as error handling) in response to a particular database error. 
 * This field is not localizable, and is always present.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_SQLSTATE', 67);

/**
 * Passed to pg_result_error_field.
 * The primary human-readable error message (typically one line). Always present.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_MESSAGE_PRIMARY', 77);

/**
 * Passed to pg_result_error_field.
 * Detail: an optional secondary error message carrying more detail about the problem. May run to multiple lines.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_MESSAGE_DETAIL', 68);

/**
 * Passed to pg_result_error_field.
 * Hint: an optional suggestion what to do about the problem. This is intended to differ from detail in that it
 * offers advice (potentially inappropriate) rather than hard facts. May run to multiple lines.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_MESSAGE_HINT', 72);

/**
 * Passed to pg_result_error_field.
 * A string containing a decimal integer indicating an error cursor position as an index into the original 
 * statement string. The first character has index 1, and positions are measured in characters not bytes.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_STATEMENT_POSITION', 80);

/**
 * Passed to pg_result_error_field.
 * This is defined the same as the PG_DIAG_STATEMENT_POSITION field, but 
 * it is used when the cursor position refers to an internally generated 
 * command rather than the one submitted by the client. The 
 * PG_DIAG_INTERNAL_QUERY field will always appear when this 
 * field appears.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_INTERNAL_POSITION', 112);

/**
 * Passed to pg_result_error_field.
 * The text of a failed internally-generated command. This could be, for example, a 
 * SQL query issued by a PL/pgSQL function.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_INTERNAL_QUERY', 113);

/**
 * Passed to pg_result_error_field.
 * An indication of the context in which the error occurred. Presently 
 * this includes a call stack traceback of active procedural language 
 * functions and internally-generated queries. The trace is one entry 
 * per line, most recent first.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_CONTEXT', 87);

/**
 * Passed to pg_result_error_field.
 * The file name of the PostgreSQL source-code location where the error 
 * was reported.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_SOURCE_FILE', 70);

/**
 * Passed to pg_result_error_field.
 * The line number of the PostgreSQL source-code location where the 
 * error was reported.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_SOURCE_LINE', 76);

/**
 * Passed to pg_result_error_field.
 * The name of the PostgreSQL source-code function reporting the error.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_SOURCE_FUNCTION', 82);

/**
 * Passed to pg_convert.
 * Ignore default values in the table during conversion.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_CONV_IGNORE_DEFAULT', 2);

/**
 * Passed to pg_convert.
 * Use SQL NULL in place of an empty string.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_CONV_FORCE_NULL', 4);

/**
 * Passed to pg_convert.
 * Ignore conversion of null into SQL NOT NULL columns.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_CONV_IGNORE_NOT_NULL', 8);

/**
 * Passed to pg_insert, pg_select,
 * pg_update and pg_delete.
 * Apply escape to all parameters instead of calling pg_convert
 * internally. This option omits meta data look up. Query could be as fast as
 * pg_query and pg_send_query.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DML_ESCAPE', 4096);

/**
 * Passed to pg_insert, pg_select,
 * pg_update and pg_delete.
 * All parameters passed as is. Manual escape is required
 * if parameters contain user supplied data. Use pg_escape_string
 * for it.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DML_NO_CONV', 256);

/**
 * Passed to pg_insert, pg_select,
 * pg_update and pg_delete.
 * Execute query by these functions.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DML_EXEC', 512);

/**
 * Passed to pg_insert, pg_select,
 * pg_update and pg_delete.
 * Execute asynchronous query by these functions.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DML_ASYNC', 1024);

/**
 * Passed to pg_insert, pg_select,
 * pg_update and pg_delete.
 * Return executed query string.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DML_STRING', 2048);

// End of pgsql v.7.1.1
