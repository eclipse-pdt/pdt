<?php

// Start of pgsql v.8.2.6

namespace PgSql {

/**
 * A fully opaque class which replaces a pgsql link resource as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/class.pgsql-connection.php
 */
final class Connection  {
}

/**
 * A fully opaque class which replaces a pgsql result resource as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/class.pgsql-result.php
 */
final class Result  {
}

/**
 * A fully opaque class which replaces a pgsql large object resource as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/class.pgsql-lob.php
 */
final class Lob  {
}


}


namespace {

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
 * @param int $flags [optional] <p>
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
 * @return mixed an PgSql\Connection instance on success, or false on failure.
 */
function pg_connect (string $connection_string, int $flags = null): PgSql\Connection|false {}

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
 * @param int $flags [optional] If PGSQL_CONNECT_FORCE_NEW is passed, then a new connection
 * is created, even if the connection_string is identical to
 * an existing connection.
 * @return mixed an PgSql\Connection instance on success, or false on failure.
 */
function pg_pconnect (string $connection_string, int $flags = null): PgSql\Connection|false {}

/**
 * Poll the status of an in-progress asynchronous PostgreSQL connection
 * attempt
 * @link http://www.php.net/manual/en/function.pg-connect-poll.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @return int PGSQL_POLLING_FAILED,
 * PGSQL_POLLING_READING,
 * PGSQL_POLLING_WRITING,
 * PGSQL_POLLING_OK, or
 * PGSQL_POLLING_ACTIVE.
 */
function pg_connect_poll (PgSql\Connection $connection): int {}

/**
 * Closes a PostgreSQL connection
 * @link http://www.php.net/manual/en/function.pg-close.php
 * @param mixed $connection [optional] pgsql.parameter.connection-with-nullable-default
 * @return true Always returns true.
 */
function pg_close ($connection = null): bool {}

/**
 * Get the database name
 * @link http://www.php.net/manual/en/function.pg-dbname.php
 * @param mixed $connection [optional] pgsql.parameter.connection-with-nullable-default
 * @return string A string containing the name of the database the 
 * connection is to.
 */
function pg_dbname ($connection = null): string {}

/**
 * Get the last error message string of a connection
 * @link http://www.php.net/manual/en/function.pg-last-error.php
 * @param mixed $connection [optional] pgsql.parameter.connection-with-nullable-default
 * @return string A string containing the last error message on the 
 * given connection.
 */
function pg_last_error ($connection = null): string {}

/**
 * @param PgSql\Connection|null $connection [optional]
 * @deprecated 
 */
function pg_errormessage (PgSql\Connection|null $connection = null): string {}

/**
 * Get the options associated with the connection
 * @link http://www.php.net/manual/en/function.pg-options.php
 * @param mixed $connection [optional] pgsql.parameter.connection-with-nullable-default
 * @return string A string containing the connection
 * options.
 */
function pg_options ($connection = null): string {}

/**
 * Return the port number associated with the connection
 * @link http://www.php.net/manual/en/function.pg-port.php
 * @param mixed $connection [optional] pgsql.parameter.connection-with-nullable-default
 * @return string A string containing the port number of the database
 * server the connection is to, 
 * or empty string on error.
 */
function pg_port ($connection = null): string {}

/**
 * Return the TTY name associated with the connection
 * @link http://www.php.net/manual/en/function.pg-tty.php
 * @param mixed $connection [optional] pgsql.parameter.connection-with-nullable-default
 * @return string A string containing the debug TTY of the connection.
 */
function pg_tty ($connection = null): string {}

/**
 * Returns the host name associated with the connection
 * @link http://www.php.net/manual/en/function.pg-host.php
 * @param mixed $connection [optional] pgsql.parameter.connection-with-nullable-default
 * @return string A string containing the name of the host the
 * connection is to, or an empty string on error.
 */
function pg_host ($connection = null): string {}

/**
 * Returns an array with client, protocol and server version (when available)
 * @link http://www.php.net/manual/en/function.pg-version.php
 * @param mixed $connection [optional] pgsql.parameter.connection-with-nullable-default
 * @return array an array with client, protocol 
 * and server keys and values (if available).
 */
function pg_version ($connection = null): array {}

/**
 * Looks up a current parameter setting of the server
 * @link http://www.php.net/manual/en/function.pg-parameter-status.php
 * @param PgSql\Connection $connection [optional] pgsql.parameter.connection-with-unspecified-default
 * @param string $param_name Possible param_name values include server_version, 
 * server_encoding, client_encoding, 
 * is_superuser, session_authorization, 
 * DateStyle, TimeZone, and 
 * integer_datetimes. Note that this value is case-sensitive.
 * @return string A string containing the value of the parameter, false on failure or invalid
 * param_name.
 */
function pg_parameter_status (PgSql\Connection $connection = null, string $param_name): string|false {}

/**
 * Ping database connection
 * @link http://www.php.net/manual/en/function.pg-ping.php
 * @param mixed $connection [optional] pgsql.parameter.connection-with-nullable-default
 * @return bool true on success or false on failure
 */
function pg_ping ($connection = null): bool {}

/**
 * Execute a query
 * @link http://www.php.net/manual/en/function.pg-query.php
 * @param PgSql\Connection $connection [optional] pgsql.parameter.connection-with-unspecified-default
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
 * @return mixed An PgSql\Result instance on success, or false on failure.
 */
function pg_query (PgSql\Connection $connection = null, string $query): PgSql\Result|false {}

/**
 * @param mixed $connection
 * @param string $query [optional]
 */
function pg_exec ($connection = null, string $query = null): PgSql\Result|false {}

/**
 * Submits a command to the server and waits for the result, with the ability to pass parameters separately from the SQL command text
 * @link http://www.php.net/manual/en/function.pg-query-params.php
 * @param PgSql\Connection $connection [optional] pgsql.parameter.connection-with-unspecified-default
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
 * @return mixed An PgSql\Result instance on success, or false on failure.
 */
function pg_query_params (PgSql\Connection $connection = null, string $query, array $params): PgSql\Result|false {}

/**
 * Submits a request to create a prepared statement with the 
 * given parameters, and waits for completion
 * @link http://www.php.net/manual/en/function.pg-prepare.php
 * @param PgSql\Connection $connection [optional] pgsql.parameter.connection-with-unspecified-default
 * @param string $stmtname The name to give the prepared statement. Must be unique per-connection. If
 * "" is specified, then an unnamed statement is created, overwriting any
 * previously defined unnamed statement.
 * @param string $query The parameterized SQL statement. Must contain only a single statement.
 * (multiple statements separated by semi-colons are not allowed.) If any parameters 
 * are used, they are referred to as $1, $2, etc.
 * @return mixed An PgSql\Result instance on success, or false on failure.
 */
function pg_prepare (PgSql\Connection $connection = null, string $stmtname, string $query): PgSql\Result|false {}

/**
 * Sends a request to execute a prepared statement with given parameters, and waits for the result
 * @link http://www.php.net/manual/en/function.pg-execute.php
 * @param PgSql\Connection $connection [optional] pgsql.parameter.connection-with-unspecified-default
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
 * @return mixed An PgSql\Result instance on success, or false on failure.
 */
function pg_execute (PgSql\Connection $connection = null, string $stmtname, array $params): PgSql\Result|false {}

/**
 * Returns the number of rows in a result
 * @link http://www.php.net/manual/en/function.pg-num-rows.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @return int The number of rows in the result. On error, -1 is returned.
 */
function pg_num_rows (PgSql\Result $result): int {}

/**
 * @param PgSql\Result $result
 * @deprecated 
 */
function pg_numrows (PgSql\Result $result): int {}

/**
 * Returns the number of fields in a result
 * @link http://www.php.net/manual/en/function.pg-num-fields.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @return int The number of fields (columns) in the result. On error, -1 is returned.
 */
function pg_num_fields (PgSql\Result $result): int {}

/**
 * @param PgSql\Result $result
 * @deprecated 
 */
function pg_numfields (PgSql\Result $result): int {}

/**
 * Returns number of affected records (tuples)
 * @link http://www.php.net/manual/en/function.pg-affected-rows.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @return int The number of rows affected by the query. If no tuple is
 * affected, it will return 0.
 */
function pg_affected_rows (PgSql\Result $result): int {}

/**
 * @param PgSql\Result $result
 * @deprecated 
 */
function pg_cmdtuples (PgSql\Result $result): int {}

/**
 * Returns the last notice message from PostgreSQL server
 * @link http://www.php.net/manual/en/function.pg-last-notice.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @param int $mode [optional] One of PGSQL_NOTICE_LAST (to return last notice),
 * PGSQL_NOTICE_ALL (to return all notices),
 * or PGSQL_NOTICE_CLEAR (to clear notices).
 * @return mixed A string containing the last notice on the 
 * given connection with
 * PGSQL_NOTICE_LAST,
 * an array with PGSQL_NOTICE_ALL,
 * a bool with PGSQL_NOTICE_CLEAR.
 */
function pg_last_notice (PgSql\Connection $connection, int $mode = null): array|string|bool {}

/**
 * Returns the name or oid of the tables field
 * @link http://www.php.net/manual/en/function.pg-field-table.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @param int $field Field number, starting from 0.
 * @param bool $oid_only [optional] By default the tables name that field belongs to is returned but
 * if oid_only is set to true, then the
 * oid will instead be returned.
 * @return mixed On success either the fields table name or oid, or false on failure.
 */
function pg_field_table (PgSql\Result $result, int $field, bool $oid_only = null): string|int|false {}

/**
 * Returns the name of a field
 * @link http://www.php.net/manual/en/function.pg-field-name.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @param int $field Field number, starting from 0.
 * @return string The field name.
 */
function pg_field_name (PgSql\Result $result, int $field): string {}

/**
 * @param PgSql\Result $result
 * @param int $field
 * @deprecated 
 */
function pg_fieldname (PgSql\Result $result, int $field): string {}

/**
 * Returns the internal storage size of the named field
 * @link http://www.php.net/manual/en/function.pg-field-size.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @param int $field Field number, starting from 0.
 * @return int The internal field storage size (in bytes). -1 indicates a variable
 * length field.
 */
function pg_field_size (PgSql\Result $result, int $field): int {}

/**
 * @param PgSql\Result $result
 * @param int $field
 * @deprecated 
 */
function pg_fieldsize (PgSql\Result $result, int $field): int {}

/**
 * Returns the type name for the corresponding field number
 * @link http://www.php.net/manual/en/function.pg-field-type.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @param int $field Field number, starting from 0.
 * @return string A string containing the base name of the field's type.
 */
function pg_field_type (PgSql\Result $result, int $field): string {}

/**
 * @param PgSql\Result $result
 * @param int $field
 * @deprecated 
 */
function pg_fieldtype (PgSql\Result $result, int $field): string {}

/**
 * Returns the type ID (OID) for the corresponding field number
 * @link http://www.php.net/manual/en/function.pg-field-type-oid.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @param int $field Field number, starting from 0.
 * @return mixed The OID of the field's base type.
 */
function pg_field_type_oid (PgSql\Result $result, int $field): string|int {}

/**
 * Returns the field number of the named field
 * @link http://www.php.net/manual/en/function.pg-field-num.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @param string $field The name of the field.
 * The given name is treated like an identifier in an SQL command,
 * that is, it is downcased unless double-quoted.
 * @return int The field number (numbered from 0), or -1 on error.
 */
function pg_field_num (PgSql\Result $result, string $field): int {}

/**
 * @param PgSql\Result $result
 * @param string $field
 * @deprecated 
 */
function pg_fieldnum (PgSql\Result $result, string $field): int {}

/**
 * Returns values from a result instance
 * @link http://www.php.net/manual/en/function.pg-fetch-result.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @param int $row Row number in result to fetch. Rows are numbered from 0 upwards. If omitted,
 * next row is fetched.
 * @param mixed $field A string representing the name of the field (column) to fetch, otherwise
 * an int representing the field number to fetch. Fields are
 * numbered from 0 upwards.
 * @return mixed Boolean is returned as &quot;t&quot; or &quot;f&quot;. All
 * other types, including arrays are returned as strings formatted
 * in the same default PostgreSQL manner that you would see in the
 * psql program. Database NULL
 * values are returned as null.
 * <p>
 * false is returned if row exceeds the number
 * of rows in the set, or on any other error.
 * </p>
 */
function pg_fetch_result (PgSql\Result $result, int $row, $field): string|false|null {}

/**
 * @param PgSql\Result $result
 * @param mixed $row
 * @param string|int $field [optional]
 * @deprecated 
 */
function pg_result (PgSql\Result $result, $row = null, string|int $field = null): string|false|null {}

/**
 * Get a row as an enumerated array
 * @link http://www.php.net/manual/en/function.pg-fetch-row.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @param mixed $row [optional] Row number in result to fetch. Rows are numbered from 0 upwards. If
 * omitted or null, the next row is fetched.
 * @param int $mode [optional] pgsql.parameter.mode
 * @return mixed An array, indexed from 0 upwards, with each value
 * represented as a string. Database NULL
 * values are returned as null.
 * <p>
 * false is returned if row exceeds the number
 * of rows in the set, there are no more rows, or on any other error.
 * </p>
 */
function pg_fetch_row (PgSql\Result $result, $row = null, int $mode = null): array|false {}

/**
 * Fetch a row as an associative array
 * @link http://www.php.net/manual/en/function.pg-fetch-assoc.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @param mixed $row [optional] Row number in result to fetch. Rows are numbered from 0 upwards. If
 * omitted or null, the next row is fetched.
 * @return mixed An array indexed associatively (by field name).
 * Each value in the array is represented as a 
 * string. Database NULL
 * values are returned as null.
 * <p>
 * false is returned if row exceeds the number
 * of rows in the set, there are no more rows, or on any other error.
 * </p>
 */
function pg_fetch_assoc (PgSql\Result $result, $row = null): array|false {}

/**
 * Fetch a row as an array
 * @link http://www.php.net/manual/en/function.pg-fetch-array.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @param mixed $row [optional] Row number in result to fetch. Rows are numbered from 0 upwards. If
 * omitted or null, the next row is fetched.
 * @param int $mode [optional] pgsql.parameter.mode
 * @return mixed An array indexed numerically (beginning with 0) or
 * associatively (indexed by field name), or both.
 * Each value in the array is represented as a 
 * string. Database NULL
 * values are returned as null.
 * <p>
 * false is returned if row exceeds the number
 * of rows in the set, there are no more rows, or on any other error.
 * Fetching from the result of a query other than SELECT will also return false.
 * </p>
 */
function pg_fetch_array (PgSql\Result $result, $row = null, int $mode = null): array|false {}

/**
 * Fetch a row as an object
 * @link http://www.php.net/manual/en/function.pg-fetch-object.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @param mixed $row [optional] Row number in result to fetch. Rows are numbered from 0 upwards. If
 * omitted or null, the next row is fetched.
 * @param string $class [optional] The name of the class to instantiate, set the properties of and return.
 * If not specified, a stdClass object is returned.
 * @param array $constructor_args [optional] An optional array of parameters to pass to the constructor
 * for class objects.
 * @return mixed An object with one attribute for each field
 * name in the result. Database NULL
 * values are returned as null.
 * <p>
 * false is returned if row exceeds the number
 * of rows in the set, there are no more rows, or on any other error.
 * </p>
 */
function pg_fetch_object (PgSql\Result $result, $row = null, string $class = null, array $constructor_args = null): object|false {}

/**
 * Fetches all rows from a result as an array
 * @link http://www.php.net/manual/en/function.pg-fetch-all.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @param int $mode [optional] pgsql.parameter.mode
 * @return array An array with all rows in the result. Each row is an array
 * of field values indexed by field name.
 */
function pg_fetch_all (PgSql\Result $result, int $mode = null): array {}

/**
 * Fetches all rows in a particular result column as an array
 * @link http://www.php.net/manual/en/function.pg-fetch-all-columns.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @param int $field [optional] Column number. Defaults to the first column if not specified.
 * @return array An array with all values in the result column.
 */
function pg_fetch_all_columns (PgSql\Result $result, int $field = null): array {}

/**
 * Set internal row offset in result instance
 * @link http://www.php.net/manual/en/function.pg-result-seek.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @param int $row Row to move the internal offset to in the PgSql\Result instance.
 * Rows are numbered starting from zero.
 * @return bool true on success or false on failure
 */
function pg_result_seek (PgSql\Result $result, int $row): bool {}

/**
 * Returns the printed length
 * @link http://www.php.net/manual/en/function.pg-field-prtlen.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @param int $row_number 
 * @param mixed $field_name_or_number 
 * @return int The field printed length.
 */
function pg_field_prtlen (PgSql\Result $result, int $row_number, $field_name_or_number): int|false {}

/**
 * @param PgSql\Result $result
 * @param mixed $row
 * @param string|int $field [optional]
 * @deprecated 
 */
function pg_fieldprtlen (PgSql\Result $result, $row = null, string|int $field = null): int|false {}

/**
 * Test if a field is SQL NULL
 * @link http://www.php.net/manual/en/function.pg-field-is-null.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @param int $row Row number in result to fetch. Rows are numbered from 0 upwards. If omitted,
 * current row is fetched.
 * @param mixed $field Field number (starting from 0) as an int or 
 * the field name as a string.
 * @return int 1 if the field in the given row is SQL NULL, 0
 * if not. false is returned if the row is out of range, or upon any other error.
 */
function pg_field_is_null (PgSql\Result $result, int $row, $field): int|false {}

/**
 * @param PgSql\Result $result
 * @param mixed $row
 * @param string|int $field [optional]
 * @deprecated 
 */
function pg_fieldisnull (PgSql\Result $result, $row = null, string|int $field = null): int|false {}

/**
 * Free result memory
 * @link http://www.php.net/manual/en/function.pg-free-result.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @return bool true on success or false on failure
 */
function pg_free_result (PgSql\Result $result): bool {}

/**
 * @param PgSql\Result $result
 * @deprecated 
 */
function pg_freeresult (PgSql\Result $result): bool {}

/**
 * Returns the last row's OID
 * @link http://www.php.net/manual/en/function.pg-last-oid.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @return mixed An int or string containing the OID assigned to the most recently inserted
 * row in the specified connection, or false on error or
 * no available OID.
 */
function pg_last_oid (PgSql\Result $result): string|int|false {}

/**
 * @param PgSql\Result $result
 * @deprecated 
 */
function pg_getlastoid (PgSql\Result $result): string|int|false {}

/**
 * Enable tracing a PostgreSQL connection
 * @link http://www.php.net/manual/en/function.pg-trace.php
 * @param string $filename The full path and file name of the file in which to write the
 * trace log. Same as in fopen.
 * @param string $mode [optional] An optional file access mode, same as for fopen.
 * @param mixed $connection [optional] pgsql.parameter.connection-with-nullable-default
 * @return bool true on success or false on failure
 */
function pg_trace (string $filename, string $mode = null, $connection = null): bool {}

/**
 * Disable tracing of a PostgreSQL connection
 * @link http://www.php.net/manual/en/function.pg-untrace.php
 * @param mixed $connection [optional] pgsql.parameter.connection-with-nullable-default
 * @return true Always returns true.
 */
function pg_untrace ($connection = null): bool {}

/**
 * Create a large object
 * @link http://www.php.net/manual/en/function.pg-lo-create.php
 * @param PgSql\Connection $connection [optional] pgsql.parameter.connection-with-unspecified-default
 * @param mixed $object_id [optional] If an object_id is given the function
 * will try to create a large object with this id, else a free
 * object id is assigned by the server. The parameter
 * relies on functionality that first
 * appeared in PostgreSQL 8.1.
 * @return int A large object OID, or false on failure.
 */
function pg_lo_create (PgSql\Connection $connection = null, $object_id = null): string|int|false {}

/**
 * @param mixed $connection [optional]
 * @param mixed $oid [optional]
 * @deprecated 
 */
function pg_locreate ($connection = null, $oid = null): string|int|false {}

/**
 * Delete a large object
 * @link http://www.php.net/manual/en/function.pg-lo-unlink.php
 * @param PgSql\Connection $connection pgsql.parameter.connection-with-unspecified-default
 * @param int $oid The OID of the large object in the database.
 * @return bool true on success or false on failure
 */
function pg_lo_unlink (PgSql\Connection $connection, int $oid): bool {}

/**
 * @param mixed $connection
 * @param mixed $oid [optional]
 * @deprecated 
 */
function pg_lounlink ($connection = null, $oid = null): bool {}

/**
 * Open a large object
 * @link http://www.php.net/manual/en/function.pg-lo-open.php
 * @param PgSql\Connection $connection pgsql.parameter.connection-with-unspecified-default
 * @param int $oid The OID of the large object in the database.
 * @param string $mode Can be either "r" for read-only, "w" for write only or "rw" for read and 
 * write.
 * @return mixed An PgSql\Lob instance, or false on failure.
 */
function pg_lo_open (PgSql\Connection $connection, int $oid, string $mode): PgSql\Lob|false {}

/**
 * @param mixed $connection
 * @param mixed $oid [optional]
 * @param string $mode [optional]
 * @deprecated 
 */
function pg_loopen ($connection = null, $oid = null, string $mode = null): PgSql\Lob|false {}

/**
 * Close a large object
 * @link http://www.php.net/manual/en/function.pg-lo-close.php
 * @param PgSql\Lob $lob An PgSql\Lob instance, returned by pg_lo_open.
 * @return bool true on success or false on failure
 */
function pg_lo_close (PgSql\Lob $lob): bool {}

/**
 * @param PgSql\Lob $lob
 * @deprecated 
 */
function pg_loclose (PgSql\Lob $lob): bool {}

/**
 * Read a large object
 * @link http://www.php.net/manual/en/function.pg-lo-read.php
 * @param PgSql\Lob $lob An PgSql\Lob instance, returned by pg_lo_open.
 * @param int $length [optional] An optional maximum number of bytes to return.
 * @return mixed A string containing length bytes from the
 * large object, or false on error.
 */
function pg_lo_read (PgSql\Lob $lob, int $length = null): string|false {}

/**
 * @param PgSql\Lob $lob
 * @param int $length [optional]
 * @deprecated 
 */
function pg_loread (PgSql\Lob $lob, int $length = 8192): string|false {}

/**
 * Write to a large object
 * @link http://www.php.net/manual/en/function.pg-lo-write.php
 * @param PgSql\Lob $lob An PgSql\Lob instance, returned by pg_lo_open.
 * @param string $data The data to be written to the large object. If length is
 * an int and is less than the length of data, only
 * length bytes will be written.
 * @param mixed $length [optional] An optional maximum number of bytes to write. Must be greater than zero
 * and no greater than the length of data. Defaults to
 * the length of data.
 * @return mixed The number of bytes written to the large object, or false on error.
 */
function pg_lo_write (PgSql\Lob $lob, string $data, $length = null): int|false {}

/**
 * @param PgSql\Lob $lob
 * @param string $data
 * @param int|null $length [optional]
 * @deprecated 
 */
function pg_lowrite (PgSql\Lob $lob, string $data, int|null $length = null): int|false {}

/**
 * Reads an entire large object and send straight to browser
 * @link http://www.php.net/manual/en/function.pg-lo-read-all.php
 * @param PgSql\Lob $lob An PgSql\Lob instance, returned by pg_lo_open.
 * @return int Number of bytes read.
 */
function pg_lo_read_all (PgSql\Lob $lob): int {}

/**
 * @param PgSql\Lob $lob
 * @deprecated 
 */
function pg_loreadall (PgSql\Lob $lob): int {}

/**
 * Import a large object from file
 * @link http://www.php.net/manual/en/function.pg-lo-import.php
 * @param PgSql\Connection $connection [optional] pgsql.parameter.connection-with-unspecified-default
 * @param string $pathname The full path and file name of the file on the client
 * filesystem from which to read the large object data.
 * @param mixed $object_id [optional] If an object_id is given the function
 * will try to create a large object with this id, else a free
 * object id is assigned by the server. The parameter
 * relies on functionality that first
 * appeared in PostgreSQL 8.1.
 * @return int The OID of the newly created large object, or false on failure.
 */
function pg_lo_import (PgSql\Connection $connection = null, string $pathname, $object_id = null): string|int|false {}

/**
 * @param mixed $connection
 * @param mixed $filename [optional]
 * @param mixed $oid [optional]
 * @deprecated 
 */
function pg_loimport ($connection = null, $filename = null, $oid = null): string|int|false {}

/**
 * Export a large object to file
 * @link http://www.php.net/manual/en/function.pg-lo-export.php
 * @param PgSql\Connection $connection [optional] pgsql.parameter.connection-with-unspecified-default
 * @param int $oid The OID of the large object in the database.
 * @param string $pathname The full path and file name of the file in which to write the
 * large object on the client filesystem.
 * @return bool true on success or false on failure
 */
function pg_lo_export (PgSql\Connection $connection = null, int $oid, string $pathname): bool {}

/**
 * @param mixed $connection
 * @param mixed $oid [optional]
 * @param mixed $filename [optional]
 * @deprecated 
 */
function pg_loexport ($connection = null, $oid = null, $filename = null): bool {}

/**
 * Seeks position within a large object
 * @link http://www.php.net/manual/en/function.pg-lo-seek.php
 * @param PgSql\Lob $lob An PgSql\Lob instance, returned by pg_lo_open.
 * @param int $offset The number of bytes to seek.
 * @param int $whence [optional] One of the constants PGSQL_SEEK_SET (seek from object start), 
 * PGSQL_SEEK_CUR (seek from current position)
 * or PGSQL_SEEK_END (seek from object end) .
 * @return bool true on success or false on failure
 */
function pg_lo_seek (PgSql\Lob $lob, int $offset, int $whence = null): bool {}

/**
 * Returns current seek position a of large object
 * @link http://www.php.net/manual/en/function.pg-lo-tell.php
 * @param PgSql\Lob $lob An PgSql\Lob instance, returned by pg_lo_open.
 * @return int The current seek offset (in number of bytes) from the beginning of the large
 * object. If there is an error, the return value is negative.
 */
function pg_lo_tell (PgSql\Lob $lob): int {}

/**
 * Truncates a large object
 * @link http://www.php.net/manual/en/function.pg-lo-truncate.php
 * @param PgSql\Lob $lob An PgSql\Lob instance, returned by pg_lo_open.
 * @param int $size The number of bytes to truncate.
 * @return bool true on success or false on failure
 */
function pg_lo_truncate (PgSql\Lob $lob, int $size): bool {}

/**
 * Determines the verbosity of messages returned by pg_last_error 
 * and pg_result_error
 * @link http://www.php.net/manual/en/function.pg-set-error-verbosity.php
 * @param PgSql\Connection $connection [optional] pgsql.parameter.connection-with-unspecified-default
 * @param int $verbosity The required verbosity: PGSQL_ERRORS_TERSE,
 * PGSQL_ERRORS_DEFAULT
 * or PGSQL_ERRORS_VERBOSE.
 * @return int The previous verbosity level: PGSQL_ERRORS_TERSE,
 * PGSQL_ERRORS_DEFAULT
 * or PGSQL_ERRORS_VERBOSE.
 */
function pg_set_error_verbosity (PgSql\Connection $connection = null, int $verbosity): int|false {}

/**
 * Set the client encoding
 * @link http://www.php.net/manual/en/function.pg-set-client-encoding.php
 * @param PgSql\Connection $connection [optional] pgsql.parameter.connection-with-unspecified-default
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
function pg_set_client_encoding (PgSql\Connection $connection = null, string $encoding): int {}

/**
 * @param mixed $connection
 * @param string $encoding [optional]
 * @deprecated 
 */
function pg_setclientencoding ($connection = null, string $encoding = null): int {}

/**
 * Gets the client encoding
 * @link http://www.php.net/manual/en/function.pg-client-encoding.php
 * @param mixed $connection [optional] pgsql.parameter.connection-with-nullable-default
 * @return string The client encoding.
 */
function pg_client_encoding ($connection = null): string {}

/**
 * @param PgSql\Connection|null $connection [optional]
 * @deprecated 
 */
function pg_clientencoding (PgSql\Connection|null $connection = null): string {}

/**
 * Sync with PostgreSQL backend
 * @link http://www.php.net/manual/en/function.pg-end-copy.php
 * @param mixed $connection [optional] pgsql.parameter.connection-with-nullable-default
 * @return bool true on success or false on failure
 */
function pg_end_copy ($connection = null): bool {}

/**
 * Send a NULL-terminated string to PostgreSQL backend
 * @link http://www.php.net/manual/en/function.pg-put-line.php
 * @param PgSql\Connection $connection [optional] pgsql.parameter.connection-with-unspecified-default
 * @param string $data A line of text to be sent directly to the PostgreSQL backend. A NULL
 * terminator is added automatically.
 * @return bool true on success or false on failure
 */
function pg_put_line (PgSql\Connection $connection = null, string $data): bool {}

/**
 * Copy a table to an array
 * @link http://www.php.net/manual/en/function.pg-copy-to.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @param string $table_name Name of the table from which to copy the data into rows.
 * @param string $separator [optional] The token that separates values for each field in each element of
 * rows. Default is \t.
 * @param string $null_as [optional] How SQL NULL values are represented in the
 * rows. Default is \\N ("\\\\N").
 * @return mixed An array with one element for each line of COPY data, or false on failure.
 */
function pg_copy_to (PgSql\Connection $connection, string $table_name, string $separator = null, string $null_as = null): array|false {}

/**
 * Insert records into a table from an array
 * @link http://www.php.net/manual/en/function.pg-copy-from.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @param string $table_name Name of the table into which to copy the rows.
 * @param array $rows An array of data to be copied into table_name.
 * Each value in rows becomes a row in table_name.
 * Each value in rows should be a delimited string of the values
 * to insert into each field. Values should be linefeed terminated.
 * @param string $separator [optional] The token that separates values for each field in each element of
 * rows. Default is \t.
 * @param string $null_as [optional] How SQL NULL values are represented in the
 * rows. Default is \\N ("\\\\N").
 * @return bool true on success or false on failure
 */
function pg_copy_from (PgSql\Connection $connection, string $table_name, array $rows, string $separator = null, string $null_as = null): bool {}

/**
 * Escape a string for query
 * @link http://www.php.net/manual/en/function.pg-escape-string.php
 * @param PgSql\Connection $connection [optional] pgsql.parameter.connection-with-unspecified-default
 * @param string $data A string containing text to be escaped.
 * @return string A string containing the escaped data.
 */
function pg_escape_string (PgSql\Connection $connection = null, string $data): string {}

/**
 * Escape a string for insertion into a bytea field
 * @link http://www.php.net/manual/en/function.pg-escape-bytea.php
 * @param PgSql\Connection $connection [optional] pgsql.parameter.connection-with-unspecified-default
 * @param string $data A string containing text or binary data to be inserted into a bytea
 * column.
 * @return string A string containing the escaped data.
 */
function pg_escape_bytea (PgSql\Connection $connection = null, string $data): string {}

/**
 * Unescape binary for bytea type
 * @link http://www.php.net/manual/en/function.pg-unescape-bytea.php
 * @param string $string A string containing PostgreSQL bytea data to be converted into
 * a PHP binary string.
 * @return string A string containing the unescaped data.
 */
function pg_unescape_bytea (string $string): string {}

/**
 * Escape a literal for insertion into a text field
 * @link http://www.php.net/manual/en/function.pg-escape-literal.php
 * @param PgSql\Connection $connection [optional] pgsql.parameter.connection-with-unspecified-default
 * @param string $data A string containing text to be escaped.
 * @return string A string containing the escaped data.
 */
function pg_escape_literal (PgSql\Connection $connection = null, string $data): string|false {}

/**
 * Escape a identifier for insertion into a text field
 * @link http://www.php.net/manual/en/function.pg-escape-identifier.php
 * @param PgSql\Connection $connection [optional] pgsql.parameter.connection-with-unspecified-default
 * @param string $data A string containing text to be escaped.
 * @return string A string containing the escaped data.
 */
function pg_escape_identifier (PgSql\Connection $connection = null, string $data): string|false {}

/**
 * Get error message associated with result
 * @link http://www.php.net/manual/en/function.pg-result-error.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @return mixed a string. Returns empty string if there is no error. If there is an error associated with the
 * result parameter, returns false.
 */
function pg_result_error (PgSql\Result $result): string|false {}

/**
 * Returns an individual field of an error report
 * @link http://www.php.net/manual/en/function.pg-result-error-field.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @param int $field_code Possible field_code values are: PGSQL_DIAG_SEVERITY,
 * PGSQL_DIAG_SQLSTATE, PGSQL_DIAG_MESSAGE_PRIMARY,
 * PGSQL_DIAG_MESSAGE_DETAIL,
 * PGSQL_DIAG_MESSAGE_HINT, PGSQL_DIAG_STATEMENT_POSITION,
 * PGSQL_DIAG_INTERNAL_POSITION (PostgreSQL 8.0+ only),
 * PGSQL_DIAG_INTERNAL_QUERY (PostgreSQL 8.0+ only),
 * PGSQL_DIAG_CONTEXT, PGSQL_DIAG_SOURCE_FILE,
 * PGSQL_DIAG_SOURCE_LINE or
 * PGSQL_DIAG_SOURCE_FUNCTION.
 * @return mixed A string containing the contents of the error field, null if the field does not exist or false
 * on failure.
 */
function pg_result_error_field (PgSql\Result $result, int $field_code): string|false|null {}

/**
 * Get connection status
 * @link http://www.php.net/manual/en/function.pg-connection-status.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @return int PGSQL_CONNECTION_OK or 
 * PGSQL_CONNECTION_BAD.
 */
function pg_connection_status (PgSql\Connection $connection): int {}

/**
 * Returns the current in-transaction status of the server
 * @link http://www.php.net/manual/en/function.pg-transaction-status.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @return int The status can be PGSQL_TRANSACTION_IDLE (currently idle),
 * PGSQL_TRANSACTION_ACTIVE (a command is in progress),
 * PGSQL_TRANSACTION_INTRANS (idle, in a valid transaction block),
 * or PGSQL_TRANSACTION_INERROR (idle, in a failed transaction block).
 * PGSQL_TRANSACTION_UNKNOWN is reported if the connection is bad.
 * PGSQL_TRANSACTION_ACTIVE is reported only when a query
 * has been sent to the server and not yet completed.
 */
function pg_transaction_status (PgSql\Connection $connection): int {}

/**
 * Reset connection (reconnect)
 * @link http://www.php.net/manual/en/function.pg-connection-reset.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @return bool true on success or false on failure
 */
function pg_connection_reset (PgSql\Connection $connection): bool {}

/**
 * Cancel an asynchronous query
 * @link http://www.php.net/manual/en/function.pg-cancel-query.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @return bool true on success or false on failure
 */
function pg_cancel_query (PgSql\Connection $connection): bool {}

/**
 * Get connection is busy or not
 * @link http://www.php.net/manual/en/function.pg-connection-busy.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @return bool true if the connection is busy, false otherwise.
 */
function pg_connection_busy (PgSql\Connection $connection): bool {}

/**
 * Sends asynchronous query
 * @link http://www.php.net/manual/en/function.pg-send-query.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @param string $query <p>
 * The SQL statement or statements to be executed.
 * </p>
 * <p>
 * Data inside the query should be properly escaped.
 * </p>
 * @return mixed true on success, false or 0 on failure. Use pg_get_result
 * to determine the query result.
 */
function pg_send_query (PgSql\Connection $connection, string $query): int|bool {}

/**
 * Submits a command and separate parameters to the server without waiting for the result(s)
 * @link http://www.php.net/manual/en/function.pg-send-query-params.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @param string $query The parameterized SQL statement. Must contain only a single statement.
 * (multiple statements separated by semi-colons are not allowed.) If any parameters 
 * are used, they are referred to as $1, $2, etc.
 * @param array $params An array of parameter values to substitute for the $1, $2, etc. placeholders
 * in the original prepared query string. The number of elements in the array
 * must match the number of placeholders.
 * @return mixed true on success, false or 0 on failure. Use pg_get_result
 * to determine the query result.
 */
function pg_send_query_params (PgSql\Connection $connection, string $query, array $params): int|bool {}

/**
 * Sends a request to create a prepared statement with the given parameters, without waiting for completion
 * @link http://www.php.net/manual/en/function.pg-send-prepare.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @param string $statement_name The name to give the prepared statement. Must be unique per-connection. If
 * "" is specified, then an unnamed statement is created, overwriting any
 * previously defined unnamed statement.
 * @param string $query The parameterized SQL statement. Must contain only a single statement.
 * (multiple statements separated by semi-colons are not allowed.) If any parameters 
 * are used, they are referred to as $1, $2, etc.
 * @return mixed true on success, false or 0 on failure. Use pg_get_result
 * to determine the query result.
 */
function pg_send_prepare (PgSql\Connection $connection, string $statement_name, string $query): int|bool {}

/**
 * Sends a request to execute a prepared statement with given parameters, without waiting for the result(s)
 * @link http://www.php.net/manual/en/function.pg-send-execute.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @param string $statement_name The name of the prepared statement to execute. If
 * "" is specified, then the unnamed statement is executed. The name must have
 * been previously prepared using pg_prepare, 
 * pg_send_prepare or a PREPARE SQL
 * command.
 * @param array $params An array of parameter values to substitute for the $1, $2, etc. placeholders
 * in the original prepared query string. The number of elements in the array
 * must match the number of placeholders.
 * @return mixed true on success, false or 0 on failure. Use pg_get_result
 * to determine the query result.
 */
function pg_send_execute (PgSql\Connection $connection, string $statement_name, array $params): int|bool {}

/**
 * Get asynchronous query result
 * @link http://www.php.net/manual/en/function.pg-get-result.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @return mixed An PgSql\Result instance, or false if no more results are available.
 */
function pg_get_result (PgSql\Connection $connection): PgSql\Result|false {}

/**
 * Get status of query result
 * @link http://www.php.net/manual/en/function.pg-result-status.php
 * @param PgSql\Result $result pgsql.parameter.result
 * @param int $mode [optional] Either PGSQL_STATUS_LONG to return the numeric status 
 * of the result, or PGSQL_STATUS_STRING 
 * to return the command tag of the result.
 * If not specified, PGSQL_STATUS_LONG is the default.
 * @return mixed Possible return values are PGSQL_EMPTY_QUERY,
 * PGSQL_COMMAND_OK, PGSQL_TUPLES_OK, PGSQL_COPY_OUT,
 * PGSQL_COPY_IN, PGSQL_BAD_RESPONSE, PGSQL_NONFATAL_ERROR and
 * PGSQL_FATAL_ERROR if PGSQL_STATUS_LONG is
 * specified. Otherwise, a string containing the PostgreSQL command tag is returned.
 */
function pg_result_status (PgSql\Result $result, int $mode = null): string|int {}

/**
 * Gets SQL NOTIFY message
 * @link http://www.php.net/manual/en/function.pg-get-notify.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @param int $mode [optional] pgsql.parameter.mode
 * @return mixed An array containing the NOTIFY message name and backend PID.
 * If supported by the server, the array also contains the server version and the payload.
 * Otherwise if no NOTIFY is waiting, then false is returned.
 */
function pg_get_notify (PgSql\Connection $connection, int $mode = null): array|false {}

/**
 * Gets the backend's process ID
 * @link http://www.php.net/manual/en/function.pg-get-pid.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @return int The backend database process ID.
 */
function pg_get_pid (PgSql\Connection $connection): int {}

/**
 * Get a read only handle to the socket underlying a PostgreSQL connection
 * @link http://www.php.net/manual/en/function.pg-socket.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @return mixed A socket resource on success or false on failure.
 */
function pg_socket (PgSql\Connection $connection) {}

/**
 * Reads input on the connection
 * @link http://www.php.net/manual/en/function.pg-consume-input.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @return bool true if no error occurred, or false if there was an error. Note that
 * true does not necessarily indicate that input was waiting to be read.
 */
function pg_consume_input (PgSql\Connection $connection): bool {}

/**
 * Flush outbound query data on the connection
 * @link http://www.php.net/manual/en/function.pg-flush.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @return mixed true if the flush was successful or no data was waiting to be
 * flushed, 0 if part of the pending data was flushed but
 * more remains or false on failure.
 */
function pg_flush (PgSql\Connection $connection): int|bool {}

/**
 * Get meta data for table
 * @link http://www.php.net/manual/en/function.pg-meta-data.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @param string $table_name The name of the table.
 * @param bool $extended [optional] Flag for returning extended meta data. Default to false.
 * @return mixed An array of the table definition, or false on failure.
 */
function pg_meta_data (PgSql\Connection $connection, string $table_name, bool $extended = null): array|false {}

/**
 * Convert associative array values into forms suitable for SQL statements
 * @link http://www.php.net/manual/en/function.pg-convert.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @param string $table_name Name of the table against which to convert types.
 * @param array $values Data to be converted.
 * @param int $flags [optional] Any number of PGSQL_CONV_IGNORE_DEFAULT,
 * PGSQL_CONV_FORCE_NULL or
 * PGSQL_CONV_IGNORE_NOT_NULL, combined.
 * @return mixed An array of converted values, or false on failure.
 */
function pg_convert (PgSql\Connection $connection, string $table_name, array $values, int $flags = null): array|false {}

/**
 * Insert array into table
 * @link http://www.php.net/manual/en/function.pg-insert.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @param string $table_name Name of the table into which to insert rows. The table table_name must at least
 * have as many columns as values has elements.
 * @param array $values An array whose keys are field names in the table table_name,
 * and whose values are the values of those fields that are to be inserted.
 * @param int $flags [optional] Any number of PGSQL_CONV_OPTS,
 * PGSQL_DML_NO_CONV,
 * PGSQL_DML_ESCAPE,
 * PGSQL_DML_EXEC,
 * PGSQL_DML_ASYNC or
 * PGSQL_DML_STRING combined. If PGSQL_DML_STRING is part of the
 * flags then query string is returned. When PGSQL_DML_NO_CONV
 * or PGSQL_DML_ESCAPE is set, it does not call pg_convert internally.
 * @return mixed true on success or false on failure. Or returns a string on success if PGSQL_DML_STRING is passed
 * via flags.
 */
function pg_insert (PgSql\Connection $connection, string $table_name, array $values, int $flags = null): PgSql\Result|string|bool {}

/**
 * Update table
 * @link http://www.php.net/manual/en/function.pg-update.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @param string $table_name Name of the table into which to update rows.
 * @param array $values An array whose keys are field names in the table table_name,
 * and whose values are what matched rows are to be updated to.
 * @param array $conditions An array whose keys are field names in the table table_name,
 * and whose values are the conditions that a row must meet to be updated.
 * @param int $flags [optional] Any number of PGSQL_CONV_FORCE_NULL,
 * PGSQL_DML_NO_CONV,
 * PGSQL_DML_ESCAPE,
 * PGSQL_DML_EXEC,
 * PGSQL_DML_ASYNC or
 * PGSQL_DML_STRING combined. If PGSQL_DML_STRING is part of the
 * flags then query string is returned. When PGSQL_DML_NO_CONV
 * or PGSQL_DML_ESCAPE is set, it does not call pg_convert internally.
 * @return mixed true on success or false on failure Returns string if PGSQL_DML_STRING is passed
 * via flags.
 */
function pg_update (PgSql\Connection $connection, string $table_name, array $values, array $conditions, int $flags = null): string|bool {}

/**
 * Deletes records
 * @link http://www.php.net/manual/en/function.pg-delete.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @param string $table_name Name of the table from which to delete rows.
 * @param array $conditions An array whose keys are field names in the table table_name,
 * and whose values are the values of those fields that are to be deleted.
 * @param int $flags [optional] Any number of PGSQL_CONV_FORCE_NULL,
 * PGSQL_DML_NO_CONV,
 * PGSQL_DML_ESCAPE,
 * PGSQL_DML_EXEC,
 * PGSQL_DML_ASYNC or
 * PGSQL_DML_STRING combined. If PGSQL_DML_STRING is part of the
 * flags then query string is returned. When PGSQL_DML_NO_CONV
 * or PGSQL_DML_ESCAPE is set, it does not call pg_convert internally.
 * @return mixed true on success or false on failure Returns string if PGSQL_DML_STRING is passed
 * via flags.
 */
function pg_delete (PgSql\Connection $connection, string $table_name, array $conditions, int $flags = null): string|bool {}

/**
 * Select records
 * @link http://www.php.net/manual/en/function.pg-select.php
 * @param PgSql\Connection $connection An PgSql\Connection instance.
 * @param string $table_name Name of the table from which to select rows.
 * @param array $conditions An array whose keys are field names in the table table_name,
 * and whose values are the conditions that a row must meet to be retrieved.
 * @param int $flags [optional] Any number of PGSQL_CONV_FORCE_NULL,
 * PGSQL_DML_NO_CONV,
 * PGSQL_DML_ESCAPE,
 * PGSQL_DML_EXEC,
 * PGSQL_DML_ASYNC or
 * PGSQL_DML_STRING combined. If PGSQL_DML_STRING is part of the
 * flags then query string is returned. When PGSQL_DML_NO_CONV
 * or PGSQL_DML_ESCAPE is set, it does not call pg_convert internally.
 * @param int $mode [optional] 
 * @return mixed string if PGSQL_DML_STRING is passed
 * via flags, otherwise it returns an array on success, or false on failure.
 */
function pg_select (PgSql\Connection $connection, string $table_name, array $conditions, int $flags = null, int $mode = null): array|string|false {}


/**
 * Short libpq version that contains only numbers and dots.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_LIBPQ_VERSION', 15.3);

/**
 * Prior to PHP 8.0.0, the long libpq version that includes compiler information.
 * As of PHP 8.0.0, the value is identical to PGSQL_LIBPQ_VERSION,
 * and using PGSQL_LIBPQ_VERSION_STR is deprecated.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_LIBPQ_VERSION_STR', 15.3);

/**
 * Passed to pg_connect to force the creation of a new connection,
 * rather than re-using an existing identical connection.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_CONNECT_FORCE_NEW', 2);

/**
 * Passed to pg_connect to create an asynchronous
 * connection.
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

/**
 * Used by pg_last_notice.
 * Available since PHP 7.1.0.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_NOTICE_LAST', 1);

/**
 * Used by pg_last_notice.
 * Available since PHP 7.1.0.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_NOTICE_ALL', 2);

/**
 * Used by pg_last_notice.
 * Available since PHP 7.1.0.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
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

/**
 * 
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_CONNECTION_STARTED', 2);

/**
 * 
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_CONNECTION_MADE', 3);

/**
 * 
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_CONNECTION_AWAITING_RESPONSE', 4);

/**
 * 
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_CONNECTION_AUTH_OK', 5);

/**
 * 
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
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
 * Available since PHP 7.3.0.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_SCHEMA_NAME', 115);

/**
 * Available since PHP 7.3.0.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_TABLE_NAME', 116);

/**
 * Available since PHP 7.3.0.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_COLUMN_NAME', 99);

/**
 * Available since PHP 7.3.0.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_DATATYPE_NAME', 100);

/**
 * Available since PHP 7.3.0.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_CONSTRAINT_NAME', 110);

/**
 * The severity; the field contents are ERROR, FATAL, or PANIC (in an error message), or WARNING, NOTICE, DEBUG, INFO, or LOG (in a notice message). This is identical to the PG_DIAG_SEVERITY field except that the contents are never localized. This is present only in versions 9.6 and later / PHP 7.3.0 and later.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_SEVERITY_NONLOCALIZED', 86);

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


}

// End of pgsql v.8.2.6
