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
 * @param string $connection_string 
 * @param int $flags [optional] 
 * @return PgSql\Connection|false Returns an PgSql\Connection instance on success, or false on failure.
 */
function pg_connect (string $connection_string, int $flags = null): PgSql\Connection|false {}

/**
 * Open a persistent PostgreSQL connection
 * @link http://www.php.net/manual/en/function.pg-pconnect.php
 * @param string $connection_string 
 * @param int $flags [optional] 
 * @return PgSql\Connection|false Returns an PgSql\Connection instance on success, or false on failure.
 */
function pg_pconnect (string $connection_string, int $flags = null): PgSql\Connection|false {}

/**
 * Poll the status of an in-progress asynchronous PostgreSQL connection
 * attempt
 * @link http://www.php.net/manual/en/function.pg-connect-poll.php
 * @param PgSql\Connection $connection >An PgSql\Connection instance.
 * @return int Returns PGSQL_POLLING_FAILED,
 * PGSQL_POLLING_READING,
 * PGSQL_POLLING_WRITING,
 * PGSQL_POLLING_OK, or
 * PGSQL_POLLING_ACTIVE.
 */
function pg_connect_poll (PgSql\Connection $connection): int {}

/**
 * Closes a PostgreSQL connection
 * @link http://www.php.net/manual/en/function.pg-close.php
 * @param PgSql\Connection|null $connection [optional] 
 * @return true Always returns true.
 */
function pg_close (?PgSql\Connection $connection = null): true {}

/**
 * Get the database name
 * @link http://www.php.net/manual/en/function.pg-dbname.php
 * @param PgSql\Connection|null $connection [optional] 
 * @return string A string containing the name of the database the 
 * connection is to.
 */
function pg_dbname (?PgSql\Connection $connection = null): string {}

/**
 * Get the last error message string of a connection
 * @link http://www.php.net/manual/en/function.pg-last-error.php
 * @param PgSql\Connection|null $connection [optional] 
 * @return string A string containing the last error message on the 
 * given connection.
 */
function pg_last_error (?PgSql\Connection $connection = null): string {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection|null $connection [optional]
 * @deprecated 
 */
function pg_errormessage (?PgSql\Connection $connection = NULL): string {}

/**
 * Get the options associated with the connection
 * @link http://www.php.net/manual/en/function.pg-options.php
 * @param PgSql\Connection|null $connection [optional] 
 * @return string A string containing the connection
 * options.
 */
function pg_options (?PgSql\Connection $connection = null): string {}

/**
 * Return the port number associated with the connection
 * @link http://www.php.net/manual/en/function.pg-port.php
 * @param PgSql\Connection|null $connection [optional] 
 * @return string A string containing the port number of the database
 * server the connection is to, 
 * or empty string on error.
 */
function pg_port (?PgSql\Connection $connection = null): string {}

/**
 * Return the TTY name associated with the connection
 * @link http://www.php.net/manual/en/function.pg-tty.php
 * @param PgSql\Connection|null $connection [optional] 
 * @return string A string containing the debug TTY of the connection.
 */
function pg_tty (?PgSql\Connection $connection = null): string {}

/**
 * Returns the host name associated with the connection
 * @link http://www.php.net/manual/en/function.pg-host.php
 * @param PgSql\Connection|null $connection [optional] 
 * @return string A string containing the name of the host the
 * connection is to, or an empty string on error.
 */
function pg_host (?PgSql\Connection $connection = null): string {}

/**
 * Returns an array with client, protocol and server version (when available)
 * @link http://www.php.net/manual/en/function.pg-version.php
 * @param PgSql\Connection|null $connection [optional] 
 * @return array Returns an array with client, protocol 
 * and server keys and values (if available).
 */
function pg_version (?PgSql\Connection $connection = null): array {}

/**
 * Looks up a current parameter setting of the server
 * @link http://www.php.net/manual/en/function.pg-parameter-status.php
 * @param PgSql\Connection $connection [optional] 
 * @param string $param_name 
 * @return string A string containing the value of the parameter, false on failure or invalid
 * param_name.
 */
function pg_parameter_status (PgSql\Connection $connection = null, string $param_name): string {}

/**
 * Ping database connection
 * @link http://www.php.net/manual/en/function.pg-ping.php
 * @param PgSql\Connection|null $connection [optional] 
 * @return bool Returns true on success or false on failure.
 */
function pg_ping (?PgSql\Connection $connection = null): bool {}

/**
 * Execute a query
 * @link http://www.php.net/manual/en/function.pg-query.php
 * @param PgSql\Connection $connection [optional] 
 * @param string $query 
 * @return PgSql\Result|false An PgSql\Result instance on success, or false on failure.
 */
function pg_query (PgSql\Connection $connection = null, string $query): PgSql\Result|false {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param string $query [optional]
 */
function pg_exec ($connection = null, string $query = NULL): PgSql\Result|false {}

/**
 * Submits a command to the server and waits for the result, with the ability to pass parameters separately from the SQL command text
 * @link http://www.php.net/manual/en/function.pg-query-params.php
 * @param PgSql\Connection $connection [optional] 
 * @param string $query 
 * @param array $params 
 * @return PgSql\Result|false An PgSql\Result instance on success, or false on failure.
 */
function pg_query_params (PgSql\Connection $connection = null, string $query, array $params): PgSql\Result|false {}

/**
 * Submits a request to create a prepared statement with the 
 * given parameters, and waits for completion
 * @link http://www.php.net/manual/en/function.pg-prepare.php
 * @param PgSql\Connection $connection [optional] 
 * @param string $stmtname 
 * @param string $query 
 * @return PgSql\Result|false An PgSql\Result instance on success, or false on failure.
 */
function pg_prepare (PgSql\Connection $connection = null, string $stmtname, string $query): PgSql\Result|false {}

/**
 * Sends a request to execute a prepared statement with given parameters, and waits for the result
 * @link http://www.php.net/manual/en/function.pg-execute.php
 * @param PgSql\Connection $connection [optional] 
 * @param string $stmtname 
 * @param array $params 
 * @return PgSql\Result|false An PgSql\Result instance on success, or false on failure.
 */
function pg_execute (PgSql\Connection $connection = null, string $stmtname, array $params): PgSql\Result|false {}

/**
 * Returns the number of rows in a result
 * @link http://www.php.net/manual/en/function.pg-num-rows.php
 * @param PgSql\Result $result 
 * @return int The number of rows in the result. On error, -1 is returned.
 */
function pg_num_rows (PgSql\Result $result): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @deprecated 
 */
function pg_numrows (PgSql\Result $result): int {}

/**
 * Returns the number of fields in a result
 * @link http://www.php.net/manual/en/function.pg-num-fields.php
 * @param PgSql\Result $result 
 * @return int The number of fields (columns) in the result. On error, -1 is returned.
 */
function pg_num_fields (PgSql\Result $result): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @deprecated 
 */
function pg_numfields (PgSql\Result $result): int {}

/**
 * Returns number of affected records (tuples)
 * @link http://www.php.net/manual/en/function.pg-affected-rows.php
 * @param PgSql\Result $result 
 * @return int The number of rows affected by the query. If no tuple is
 * affected, it will return 0.
 */
function pg_affected_rows (PgSql\Result $result): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @deprecated 
 */
function pg_cmdtuples (PgSql\Result $result): int {}

/**
 * Returns the last notice message from PostgreSQL server
 * @link http://www.php.net/manual/en/function.pg-last-notice.php
 * @param PgSql\Connection $connection 
 * @param int $mode [optional] 
 * @return array|string|bool A string containing the last notice on the 
 * given connection with
 * PGSQL_NOTICE_LAST,
 * an array with PGSQL_NOTICE_ALL,
 * a bool with PGSQL_NOTICE_CLEAR.
 */
function pg_last_notice (PgSql\Connection $connection, int $mode = PGSQL_NOTICE_LAST): array|string|bool {}

/**
 * Returns the name or oid of the tables field
 * @link http://www.php.net/manual/en/function.pg-field-table.php
 * @param PgSql\Result $result 
 * @param int $field 
 * @param bool $oid_only [optional] 
 * @return string|int|false On success either the fields table name or oid, or false on failure.
 */
function pg_field_table (PgSql\Result $result, int $field, bool $oid_only = false): string|int|false {}

/**
 * Returns the name of a field
 * @link http://www.php.net/manual/en/function.pg-field-name.php
 * @param PgSql\Result $result 
 * @param int $field 
 * @return string The field name.
 */
function pg_field_name (PgSql\Result $result, int $field): string {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int $field
 * @deprecated 
 */
function pg_fieldname (PgSql\Result $result, int $field): string {}

/**
 * Returns the internal storage size of the named field
 * @link http://www.php.net/manual/en/function.pg-field-size.php
 * @param PgSql\Result $result 
 * @param int $field 
 * @return int The internal field storage size (in bytes). -1 indicates a variable
 * length field.
 */
function pg_field_size (PgSql\Result $result, int $field): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int $field
 * @deprecated 
 */
function pg_fieldsize (PgSql\Result $result, int $field): int {}

/**
 * Returns the type name for the corresponding field number
 * @link http://www.php.net/manual/en/function.pg-field-type.php
 * @param PgSql\Result $result 
 * @param int $field 
 * @return string A string containing the base name of the field's type.
 */
function pg_field_type (PgSql\Result $result, int $field): string {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int $field
 * @deprecated 
 */
function pg_fieldtype (PgSql\Result $result, int $field): string {}

/**
 * Returns the type ID (OID) for the corresponding field number
 * @link http://www.php.net/manual/en/function.pg-field-type-oid.php
 * @param PgSql\Result $result 
 * @param int $field 
 * @return string|int The OID of the field's base type.
 */
function pg_field_type_oid (PgSql\Result $result, int $field): string|int {}

/**
 * Returns the field number of the named field
 * @link http://www.php.net/manual/en/function.pg-field-num.php
 * @param PgSql\Result $result 
 * @param string $field 
 * @return int The field number (numbered from 0), or -1 on error.
 */
function pg_field_num (PgSql\Result $result, string $field): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param string $field
 * @deprecated 
 */
function pg_fieldnum (PgSql\Result $result, string $field): int {}

/**
 * Returns values from a result instance
 * @link http://www.php.net/manual/en/function.pg-fetch-result.php
 * @param PgSql\Result $result 
 * @param int $row 
 * @param mixed $field 
 * @return string|false|null Boolean is returned as "t" or "f". All
 * other types, including arrays are returned as strings formatted
 * in the same default PostgreSQL manner that you would see in the
 * psql program. Database NULL
 * values are returned as null.
 * <p>false is returned if row exceeds the number
 * of rows in the set, or on any other error.</p>
 */
function pg_fetch_result (PgSql\Result $result, int $row, mixed $field): string|false|null {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param mixed $row
 * @param string|int $field [optional]
 * @deprecated 
 */
function pg_result (PgSql\Result $result, $row = null, string|int $field = NULL): string|false|null {}

/**
 * Get a row as an enumerated array
 * @link http://www.php.net/manual/en/function.pg-fetch-row.php
 * @param PgSql\Result $result 
 * @param int|null $row [optional] 
 * @param int $mode [optional] 
 * @return array|false An array, indexed from 0 upwards, with each value
 * represented as a string. Database NULL
 * values are returned as null.
 * <p>false is returned if row exceeds the number
 * of rows in the set, there are no more rows, or on any other error.</p>
 */
function pg_fetch_row (PgSql\Result $result, ?int $row = null, int $mode = PGSQL_NUM): array|false {}

/**
 * Fetch a row as an associative array
 * @link http://www.php.net/manual/en/function.pg-fetch-assoc.php
 * @param PgSql\Result $result 
 * @param int|null $row [optional] 
 * @return array|false An array indexed associatively (by field name).
 * Each value in the array is represented as a 
 * string. Database NULL
 * values are returned as null.
 * <p>false is returned if row exceeds the number
 * of rows in the set, there are no more rows, or on any other error.</p>
 */
function pg_fetch_assoc (PgSql\Result $result, ?int $row = null): array|false {}

/**
 * Fetch a row as an array
 * @link http://www.php.net/manual/en/function.pg-fetch-array.php
 * @param PgSql\Result $result 
 * @param int|null $row [optional] 
 * @param int $mode [optional] 
 * @return array|false An array indexed numerically (beginning with 0) or
 * associatively (indexed by field name), or both.
 * Each value in the array is represented as a 
 * string. Database NULL
 * values are returned as null.
 * <p>false is returned if row exceeds the number
 * of rows in the set, there are no more rows, or on any other error.
 * Fetching from the result of a query other than SELECT will also return false.</p>
 */
function pg_fetch_array (PgSql\Result $result, ?int $row = null, int $mode = PGSQL_BOTH): array|false {}

/**
 * Fetch a row as an object
 * @link http://www.php.net/manual/en/function.pg-fetch-object.php
 * @param PgSql\Result $result 
 * @param int|null $row [optional] 
 * @param string $class [optional] 
 * @param array $constructor_args [optional] 
 * @return object|false An object with one attribute for each field
 * name in the result. Database NULL
 * values are returned as null.
 * <p>false is returned if row exceeds the number
 * of rows in the set, there are no more rows, or on any other error.</p>
 */
function pg_fetch_object (PgSql\Result $result, ?int $row = null, string $class = '"stdClass"', array $constructor_args = '[]'): object|false {}

/**
 * Fetches all rows from a result as an array
 * @link http://www.php.net/manual/en/function.pg-fetch-all.php
 * @param PgSql\Result $result 
 * @param int $mode [optional] 
 * @return array An array with all rows in the result. Each row is an array
 * of field values indexed by field name.
 */
function pg_fetch_all (PgSql\Result $result, int $mode = PGSQL_ASSOC): array {}

/**
 * Fetches all rows in a particular result column as an array
 * @link http://www.php.net/manual/en/function.pg-fetch-all-columns.php
 * @param PgSql\Result $result 
 * @param int $field [optional] 
 * @return array An array with all values in the result column.
 */
function pg_fetch_all_columns (PgSql\Result $result, int $field = null): array {}

/**
 * Set internal row offset in result instance
 * @link http://www.php.net/manual/en/function.pg-result-seek.php
 * @param PgSql\Result $result 
 * @param int $row 
 * @return bool Returns true on success or false on failure.
 */
function pg_result_seek (PgSql\Result $result, int $row): bool {}

/**
 * Returns the printed length
 * @link http://www.php.net/manual/en/function.pg-field-prtlen.php
 * @param PgSql\Result $result 
 * @param int $row_number 
 * @param mixed $field_name_or_number 
 * @return int The field printed length.
 */
function pg_field_prtlen (PgSql\Result $result, int $row_number, mixed $field_name_or_number): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param mixed $row
 * @param string|int $field [optional]
 * @deprecated 
 */
function pg_fieldprtlen (PgSql\Result $result, $row = null, string|int $field = NULL): int|false {}

/**
 * Test if a field is SQL NULL
 * @link http://www.php.net/manual/en/function.pg-field-is-null.php
 * @param PgSql\Result $result 
 * @param int $row 
 * @param mixed $field 
 * @return int Returns 1 if the field in the given row is SQL NULL, 0
 * if not. false is returned if the row is out of range, or upon any other error.
 */
function pg_field_is_null (PgSql\Result $result, int $row, mixed $field): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param mixed $row
 * @param string|int $field [optional]
 * @deprecated 
 */
function pg_fieldisnull (PgSql\Result $result, $row = null, string|int $field = NULL): int|false {}

/**
 * Free result memory
 * @link http://www.php.net/manual/en/function.pg-free-result.php
 * @param PgSql\Result $result 
 * @return bool Returns true on success or false on failure.
 */
function pg_free_result (PgSql\Result $result): bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @deprecated 
 */
function pg_freeresult (PgSql\Result $result): bool {}

/**
 * Returns the last row's OID
 * @link http://www.php.net/manual/en/function.pg-last-oid.php
 * @param PgSql\Result $result 
 * @return string|int|false An int or string containing the OID assigned to the most recently inserted
 * row in the specified connection, or false on error or
 * no available OID.
 */
function pg_last_oid (PgSql\Result $result): string|int|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @deprecated 
 */
function pg_getlastoid (PgSql\Result $result): string|int|false {}

/**
 * Enable tracing a PostgreSQL connection
 * @link http://www.php.net/manual/en/function.pg-trace.php
 * @param string $filename 
 * @param string $mode [optional] 
 * @param PgSql\Connection|null $connection [optional] 
 * @return bool Returns true on success or false on failure.
 */
function pg_trace (string $filename, string $mode = '"w"', ?PgSql\Connection $connection = null): bool {}

/**
 * Disable tracing of a PostgreSQL connection
 * @link http://www.php.net/manual/en/function.pg-untrace.php
 * @param PgSql\Connection|null $connection [optional] 
 * @return true Always returns true.
 */
function pg_untrace (?PgSql\Connection $connection = null): true {}

/**
 * Create a large object
 * @link http://www.php.net/manual/en/function.pg-lo-create.php
 * @param PgSql\Connection $connection [optional] 
 * @param mixed $object_id [optional] 
 * @return int A large object OID, or false on failure.
 */
function pg_lo_create (PgSql\Connection $connection = null, mixed $object_id = null): int {}

/**
 * {@inheritdoc}
 * @param mixed $connection [optional]
 * @param mixed $oid [optional]
 * @deprecated 
 */
function pg_locreate ($connection = NULL, $oid = NULL): string|int|false {}

/**
 * Delete a large object
 * @link http://www.php.net/manual/en/function.pg-lo-unlink.php
 * @param PgSql\Connection $connection 
 * @param int $oid 
 * @return bool Returns true on success or false on failure.
 */
function pg_lo_unlink (PgSql\Connection $connection, int $oid): bool {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param mixed $oid [optional]
 * @deprecated 
 */
function pg_lounlink ($connection = null, $oid = NULL): bool {}

/**
 * Open a large object
 * @link http://www.php.net/manual/en/function.pg-lo-open.php
 * @param PgSql\Connection $connection 
 * @param int $oid 
 * @param string $mode 
 * @return PgSql\Lob|false An PgSql\Lob instance, or false on failure.
 */
function pg_lo_open (PgSql\Connection $connection, int $oid, string $mode): PgSql\Lob|false {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param mixed $oid [optional]
 * @param string $mode [optional]
 * @deprecated 
 */
function pg_loopen ($connection = null, $oid = NULL, string $mode = NULL): PgSql\Lob|false {}

/**
 * Close a large object
 * @link http://www.php.net/manual/en/function.pg-lo-close.php
 * @param PgSql\Lob $lob 
 * @return bool Returns true on success or false on failure.
 */
function pg_lo_close (PgSql\Lob $lob): bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Lob $lob
 * @deprecated 
 */
function pg_loclose (PgSql\Lob $lob): bool {}

/**
 * Read a large object
 * @link http://www.php.net/manual/en/function.pg-lo-read.php
 * @param PgSql\Lob $lob 
 * @param int $length [optional] 
 * @return string|false A string containing length bytes from the
 * large object, or false on error.
 */
function pg_lo_read (PgSql\Lob $lob, int $length = 8192): string|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Lob $lob
 * @param int $length [optional]
 * @deprecated 
 */
function pg_loread (PgSql\Lob $lob, int $length = 8192): string|false {}

/**
 * Write to a large object
 * @link http://www.php.net/manual/en/function.pg-lo-write.php
 * @param PgSql\Lob $lob 
 * @param string $data 
 * @param int|null $length [optional] 
 * @return int|false The number of bytes written to the large object, or false on error.
 */
function pg_lo_write (PgSql\Lob $lob, string $data, ?int $length = null): int|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Lob $lob
 * @param string $data
 * @param int|null $length [optional]
 * @deprecated 
 */
function pg_lowrite (PgSql\Lob $lob, string $data, ?int $length = NULL): int|false {}

/**
 * Reads an entire large object and send straight to browser
 * @link http://www.php.net/manual/en/function.pg-lo-read-all.php
 * @param PgSql\Lob $lob 
 * @return int Number of bytes read.
 */
function pg_lo_read_all (PgSql\Lob $lob): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Lob $lob
 * @deprecated 
 */
function pg_loreadall (PgSql\Lob $lob): int {}

/**
 * Import a large object from file
 * @link http://www.php.net/manual/en/function.pg-lo-import.php
 * @param PgSql\Connection $connection [optional] 
 * @param string $pathname 
 * @param mixed $object_id [optional] 
 * @return int The OID of the newly created large object, or false on failure.
 */
function pg_lo_import (PgSql\Connection $connection = null, string $pathname, mixed $object_id = null): int {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param mixed $filename [optional]
 * @param mixed $oid [optional]
 * @deprecated 
 */
function pg_loimport ($connection = null, $filename = NULL, $oid = NULL): string|int|false {}

/**
 * Export a large object to file
 * @link http://www.php.net/manual/en/function.pg-lo-export.php
 * @param PgSql\Connection $connection [optional] 
 * @param int $oid 
 * @param string $pathname 
 * @return bool Returns true on success or false on failure.
 */
function pg_lo_export (PgSql\Connection $connection = null, int $oid, string $pathname): bool {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param mixed $oid [optional]
 * @param mixed $filename [optional]
 * @deprecated 
 */
function pg_loexport ($connection = null, $oid = NULL, $filename = NULL): bool {}

/**
 * Seeks position within a large object
 * @link http://www.php.net/manual/en/function.pg-lo-seek.php
 * @param PgSql\Lob $lob 
 * @param int $offset 
 * @param int $whence [optional] 
 * @return bool Returns true on success or false on failure.
 */
function pg_lo_seek (PgSql\Lob $lob, int $offset, int $whence = SEEK_CUR): bool {}

/**
 * Returns current seek position a of large object
 * @link http://www.php.net/manual/en/function.pg-lo-tell.php
 * @param PgSql\Lob $lob 
 * @return int The current seek offset (in number of bytes) from the beginning of the large
 * object. If there is an error, the return value is negative.
 */
function pg_lo_tell (PgSql\Lob $lob): int {}

/**
 * Truncates a large object
 * @link http://www.php.net/manual/en/function.pg-lo-truncate.php
 * @param PgSql\Lob $lob 
 * @param int $size 
 * @return bool Returns true on success or false on failure.
 */
function pg_lo_truncate (PgSql\Lob $lob, int $size): bool {}

/**
 * Determines the verbosity of messages returned by pg_last_error 
 * and pg_result_error
 * @link http://www.php.net/manual/en/function.pg-set-error-verbosity.php
 * @param PgSql\Connection $connection [optional] 
 * @param int $verbosity 
 * @return int The previous verbosity level: PGSQL_ERRORS_TERSE,
 * PGSQL_ERRORS_DEFAULT
 * or PGSQL_ERRORS_VERBOSE.
 */
function pg_set_error_verbosity (PgSql\Connection $connection = null, int $verbosity): int {}

/**
 * Set the client encoding
 * @link http://www.php.net/manual/en/function.pg-set-client-encoding.php
 * @param PgSql\Connection $connection [optional] 
 * @param string $encoding 
 * @return int Returns 0 on success or -1 on error.
 */
function pg_set_client_encoding (PgSql\Connection $connection = null, string $encoding): int {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param string $encoding [optional]
 * @deprecated 
 */
function pg_setclientencoding ($connection = null, string $encoding = NULL): int {}

/**
 * Gets the client encoding
 * @link http://www.php.net/manual/en/function.pg-client-encoding.php
 * @param PgSql\Connection|null $connection [optional] 
 * @return string The client encoding.
 */
function pg_client_encoding (?PgSql\Connection $connection = null): string {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection|null $connection [optional]
 * @deprecated 
 */
function pg_clientencoding (?PgSql\Connection $connection = NULL): string {}

/**
 * Sync with PostgreSQL backend
 * @link http://www.php.net/manual/en/function.pg-end-copy.php
 * @param PgSql\Connection|null $connection [optional] 
 * @return bool Returns true on success or false on failure.
 */
function pg_end_copy (?PgSql\Connection $connection = null): bool {}

/**
 * Send a NULL-terminated string to PostgreSQL backend
 * @link http://www.php.net/manual/en/function.pg-put-line.php
 * @param PgSql\Connection $connection [optional] 
 * @param string $data 
 * @return bool Returns true on success or false on failure.
 */
function pg_put_line (PgSql\Connection $connection = null, string $data): bool {}

/**
 * Copy a table to an array
 * @link http://www.php.net/manual/en/function.pg-copy-to.php
 * @param PgSql\Connection $connection 
 * @param string $table_name 
 * @param string $separator [optional] 
 * @param string $null_as [optional] 
 * @return array|false An array with one element for each line of COPY data, or false on failure.
 */
function pg_copy_to (PgSql\Connection $connection, string $table_name, string $separator = '"\\t"', string $null_as = '"\\\\\\\\N"'): array|false {}

/**
 * Insert records into a table from an array
 * @link http://www.php.net/manual/en/function.pg-copy-from.php
 * @param PgSql\Connection $connection 
 * @param string $table_name 
 * @param array $rows 
 * @param string $separator [optional] 
 * @param string $null_as [optional] 
 * @return bool Returns true on success or false on failure.
 */
function pg_copy_from (PgSql\Connection $connection, string $table_name, array $rows, string $separator = '"\\t"', string $null_as = '"\\\\\\\\N"'): bool {}

/**
 * Escape a string for query
 * @link http://www.php.net/manual/en/function.pg-escape-string.php
 * @param PgSql\Connection $connection [optional] 
 * @param string $data 
 * @return string A string containing the escaped data.
 */
function pg_escape_string (PgSql\Connection $connection = null, string $data): string {}

/**
 * Escape a string for insertion into a bytea field
 * @link http://www.php.net/manual/en/function.pg-escape-bytea.php
 * @param PgSql\Connection $connection [optional] 
 * @param string $data 
 * @return string A string containing the escaped data.
 */
function pg_escape_bytea (PgSql\Connection $connection = null, string $data): string {}

/**
 * Unescape binary for bytea type
 * @link http://www.php.net/manual/en/function.pg-unescape-bytea.php
 * @param string $string 
 * @return string A string containing the unescaped data.
 */
function pg_unescape_bytea (string $string): string {}

/**
 * Escape a literal for insertion into a text field
 * @link http://www.php.net/manual/en/function.pg-escape-literal.php
 * @param PgSql\Connection $connection [optional] 
 * @param string $data 
 * @return string A string containing the escaped data.
 */
function pg_escape_literal (PgSql\Connection $connection = null, string $data): string {}

/**
 * Escape a identifier for insertion into a text field
 * @link http://www.php.net/manual/en/function.pg-escape-identifier.php
 * @param PgSql\Connection $connection [optional] 
 * @param string $data 
 * @return string A string containing the escaped data.
 */
function pg_escape_identifier (PgSql\Connection $connection = null, string $data): string {}

/**
 * Get error message associated with result
 * @link http://www.php.net/manual/en/function.pg-result-error.php
 * @param PgSql\Result $result 
 * @return string|false Returns a string. Returns empty string if there is no error. If there is an error associated with the
 * result parameter, returns false.
 */
function pg_result_error (PgSql\Result $result): string|false {}

/**
 * Returns an individual field of an error report
 * @link http://www.php.net/manual/en/function.pg-result-error-field.php
 * @param PgSql\Result $result 
 * @param int $field_code 
 * @return string|false|null A string containing the contents of the error field, null if the field does not exist or false
 * on failure.
 */
function pg_result_error_field (PgSql\Result $result, int $field_code): string|false|null {}

/**
 * Get connection status
 * @link http://www.php.net/manual/en/function.pg-connection-status.php
 * @param PgSql\Connection $connection 
 * @return int PGSQL_CONNECTION_OK or 
 * PGSQL_CONNECTION_BAD.
 */
function pg_connection_status (PgSql\Connection $connection): int {}

/**
 * Returns the current in-transaction status of the server
 * @link http://www.php.net/manual/en/function.pg-transaction-status.php
 * @param PgSql\Connection $connection 
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
 * @param PgSql\Connection $connection 
 * @return bool Returns true on success or false on failure.
 */
function pg_connection_reset (PgSql\Connection $connection): bool {}

/**
 * Cancel an asynchronous query
 * @link http://www.php.net/manual/en/function.pg-cancel-query.php
 * @param PgSql\Connection $connection 
 * @return bool Returns true on success or false on failure.
 */
function pg_cancel_query (PgSql\Connection $connection): bool {}

/**
 * Get connection is busy or not
 * @link http://www.php.net/manual/en/function.pg-connection-busy.php
 * @param PgSql\Connection $connection 
 * @return bool Returns true if the connection is busy, false otherwise.
 */
function pg_connection_busy (PgSql\Connection $connection): bool {}

/**
 * Sends asynchronous query
 * @link http://www.php.net/manual/en/function.pg-send-query.php
 * @param PgSql\Connection $connection 
 * @param string $query 
 * @return int|bool Returns true on success, false or 0 on failure. Use pg_get_result
 * to determine the query result.
 */
function pg_send_query (PgSql\Connection $connection, string $query): int|bool {}

/**
 * Submits a command and separate parameters to the server without waiting for the result(s)
 * @link http://www.php.net/manual/en/function.pg-send-query-params.php
 * @param PgSql\Connection $connection 
 * @param string $query 
 * @param array $params 
 * @return int|bool Returns true on success, false or 0 on failure. Use pg_get_result
 * to determine the query result.
 */
function pg_send_query_params (PgSql\Connection $connection, string $query, array $params): int|bool {}

/**
 * Sends a request to create a prepared statement with the given parameters, without waiting for completion
 * @link http://www.php.net/manual/en/function.pg-send-prepare.php
 * @param PgSql\Connection $connection 
 * @param string $statement_name 
 * @param string $query 
 * @return int|bool Returns true on success, false or 0 on failure. Use pg_get_result
 * to determine the query result.
 */
function pg_send_prepare (PgSql\Connection $connection, string $statement_name, string $query): int|bool {}

/**
 * Sends a request to execute a prepared statement with given parameters, without waiting for the result(s)
 * @link http://www.php.net/manual/en/function.pg-send-execute.php
 * @param PgSql\Connection $connection 
 * @param string $statement_name 
 * @param array $params 
 * @return int|bool Returns true on success, false or 0 on failure. Use pg_get_result
 * to determine the query result.
 */
function pg_send_execute (PgSql\Connection $connection, string $statement_name, array $params): int|bool {}

/**
 * Get asynchronous query result
 * @link http://www.php.net/manual/en/function.pg-get-result.php
 * @param PgSql\Connection $connection 
 * @return PgSql\Result|false An PgSql\Result instance, or false if no more results are available.
 */
function pg_get_result (PgSql\Connection $connection): PgSql\Result|false {}

/**
 * Get status of query result
 * @link http://www.php.net/manual/en/function.pg-result-status.php
 * @param PgSql\Result $result 
 * @param int $mode [optional] 
 * @return string|int Possible return values are PGSQL_EMPTY_QUERY,
 * PGSQL_COMMAND_OK, PGSQL_TUPLES_OK, PGSQL_COPY_OUT,
 * PGSQL_COPY_IN, PGSQL_BAD_RESPONSE, PGSQL_NONFATAL_ERROR and
 * PGSQL_FATAL_ERROR if PGSQL_STATUS_LONG is
 * specified. Otherwise, a string containing the PostgreSQL command tag is returned.
 */
function pg_result_status (PgSql\Result $result, int $mode = PGSQL_STATUS_LONG): string|int {}

/**
 * Gets SQL NOTIFY message
 * @link http://www.php.net/manual/en/function.pg-get-notify.php
 * @param PgSql\Connection $connection 
 * @param int $mode [optional] 
 * @return array|false An array containing the NOTIFY message name and backend PID.
 * If supported by the server, the array also contains the server version and the payload.
 * Otherwise if no NOTIFY is waiting, then false is returned.
 */
function pg_get_notify (PgSql\Connection $connection, int $mode = PGSQL_ASSOC): array|false {}

/**
 * Gets the backend's process ID
 * @link http://www.php.net/manual/en/function.pg-get-pid.php
 * @param PgSql\Connection $connection 
 * @return int The backend database process ID.
 */
function pg_get_pid (PgSql\Connection $connection): int {}

/**
 * Get a read only handle to the socket underlying a PostgreSQL connection
 * @link http://www.php.net/manual/en/function.pg-socket.php
 * @param PgSql\Connection $connection >An PgSql\Connection instance.
 * @return resource|false A socket resource on success or false on failure.
 */
function pg_socket (PgSql\Connection $connection) {}

/**
 * Reads input on the connection
 * @link http://www.php.net/manual/en/function.pg-consume-input.php
 * @param PgSql\Connection $connection >An PgSql\Connection instance.
 * @return bool true if no error occurred, or false if there was an error. Note that
 * true does not necessarily indicate that input was waiting to be read.
 */
function pg_consume_input (PgSql\Connection $connection): bool {}

/**
 * Flush outbound query data on the connection
 * @link http://www.php.net/manual/en/function.pg-flush.php
 * @param PgSql\Connection $connection >An PgSql\Connection instance.
 * @return int|bool Returns true if the flush was successful or no data was waiting to be
 * flushed, 0 if part of the pending data was flushed but
 * more remains or false on failure.
 */
function pg_flush (PgSql\Connection $connection): int|bool {}

/**
 * Get meta data for table
 * @link http://www.php.net/manual/en/function.pg-meta-data.php
 * @param PgSql\Connection $connection 
 * @param string $table_name 
 * @param bool $extended [optional] 
 * @return array|false An array of the table definition, or false on failure.
 */
function pg_meta_data (PgSql\Connection $connection, string $table_name, bool $extended = false): array|false {}

/**
 * Convert associative array values into forms suitable for SQL statements
 * @link http://www.php.net/manual/en/function.pg-convert.php
 * @param PgSql\Connection $connection 
 * @param string $table_name 
 * @param array $values 
 * @param int $flags [optional] 
 * @return array|false An array of converted values, or false on failure.
 */
function pg_convert (PgSql\Connection $connection, string $table_name, array $values, int $flags = null): array|false {}

/**
 * Insert array into table
 * @link http://www.php.net/manual/en/function.pg-insert.php
 * @param PgSql\Connection $connection 
 * @param string $table_name 
 * @param array $values 
 * @param int $flags [optional] 
 * @return PgSql\Result|string|bool Returns true on success or false on failure.. Or returns a string on success if PGSQL_DML_STRING is passed
 * via flags.
 */
function pg_insert (PgSql\Connection $connection, string $table_name, array $values, int $flags = PGSQL_DML_EXEC): PgSql\Result|string|bool {}

/**
 * Update table
 * @link http://www.php.net/manual/en/function.pg-update.php
 * @param PgSql\Connection $connection 
 * @param string $table_name 
 * @param array $values 
 * @param array $conditions 
 * @param int $flags [optional] 
 * @return string|bool Returns true on success or false on failure. Returns string if PGSQL_DML_STRING is passed
 * via flags.
 */
function pg_update (PgSql\Connection $connection, string $table_name, array $values, array $conditions, int $flags = PGSQL_DML_EXEC): string|bool {}

/**
 * Deletes records
 * @link http://www.php.net/manual/en/function.pg-delete.php
 * @param PgSql\Connection $connection 
 * @param string $table_name 
 * @param array $conditions 
 * @param int $flags [optional] 
 * @return string|bool Returns true on success or false on failure. Returns string if PGSQL_DML_STRING is passed
 * via flags.
 */
function pg_delete (PgSql\Connection $connection, string $table_name, array $conditions, int $flags = PGSQL_DML_EXEC): string|bool {}

/**
 * Select records
 * @link http://www.php.net/manual/en/function.pg-select.php
 * @param PgSql\Connection $connection 
 * @param string $table_name 
 * @param array $conditions 
 * @param int $flags [optional] 
 * @param int $mode [optional] 
 * @return array|string|false Returns string if PGSQL_DML_STRING is passed
 * via flags, otherwise it returns an array on success, or false on failure.
 */
function pg_select (PgSql\Connection $connection, string $table_name, array $conditions, int $flags = PGSQL_DML_EXEC, int $mode = PGSQL_ASSOC): array|string|false {}


/**
 * Short libpq version that contains only numbers and dots.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var string
 */
define ('PGSQL_LIBPQ_VERSION', 15.3);

/**
 * Prior to PHP 8.0.0, the long libpq version that includes compiler information.
 * As of PHP 8.0.0, the value is identical to PGSQL_LIBPQ_VERSION,
 * and using PGSQL_LIBPQ_VERSION_STR is deprecated.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var string
 */
define ('PGSQL_LIBPQ_VERSION_STR', 15.3);

/**
 * Passed to pg_connect to force the creation of a new connection,
 * rather than re-using an existing identical connection.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_CONNECT_FORCE_NEW', 2);

/**
 * Passed to pg_connect to create an asynchronous
 * connection.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_CONNECT_ASYNC', 4);

/**
 * Passed to pg_fetch_array. Return an associative array of field
 * names and values.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_ASSOC', 1);

/**
 * Passed to pg_fetch_array. Return a numerically indexed array of field
 * numbers and values.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_NUM', 2);

/**
 * Passed to pg_fetch_array. Return an array of field values
 * that is both numerically indexed (by field number) and associated (by field name).
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_BOTH', 3);

/**
 * Used by pg_last_notice.
 * Available since PHP 7.1.0.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_NOTICE_LAST', 1);

/**
 * Used by pg_last_notice.
 * Available since PHP 7.1.0.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_NOTICE_ALL', 2);

/**
 * Used by pg_last_notice.
 * Available since PHP 7.1.0.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_NOTICE_CLEAR', 3);

/**
 * Returned by pg_connection_status indicating that the database
 * connection is in an invalid state.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_CONNECTION_BAD', 1);

/**
 * Returned by pg_connection_status indicating that the database
 * connection is in a valid state.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_CONNECTION_OK', 0);

/**
 * 
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_CONNECTION_STARTED', 2);

/**
 * 
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_CONNECTION_MADE', 3);

/**
 * 
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_CONNECTION_AWAITING_RESPONSE', 4);

/**
 * 
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_CONNECTION_AUTH_OK', 5);

/**
 * 
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_CONNECTION_SETENV', 6);

/**
 * Returned by pg_connect_poll to indicate that the
 * connection attempt failed.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_POLLING_FAILED', 0);

/**
 * Returned by pg_connect_poll to indicate that the
 * connection is waiting for the PostgreSQL socket to be readable.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_POLLING_READING', 1);

/**
 * Returned by pg_connect_poll to indicate that the
 * connection is waiting for the PostgreSQL socket to be writable.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_POLLING_WRITING', 2);

/**
 * Returned by pg_connect_poll to indicate that the
 * connection is ready to be used.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_POLLING_OK', 3);

/**
 * Returned by pg_connect_poll to indicate that the
 * connection is currently active.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_POLLING_ACTIVE', 4);

/**
 * Returned by pg_transaction_status. Connection is
 * currently idle, not in a transaction.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_TRANSACTION_IDLE', 0);

/**
 * Returned by pg_transaction_status. A command
 * is in progress on the connection. A query has been sent via the connection
 * and not yet completed.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_TRANSACTION_ACTIVE', 1);

/**
 * Returned by pg_transaction_status. The connection
 * is idle, in a transaction block.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_TRANSACTION_INTRANS', 2);

/**
 * Returned by pg_transaction_status. The connection
 * is idle, in a failed transaction block.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_TRANSACTION_INERROR', 3);

/**
 * Returned by pg_transaction_status. The connection
 * is bad.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_TRANSACTION_UNKNOWN', 4);

/**
 * Passed to pg_set_error_verbosity.
 * Specified that returned messages include severity, primary text, 
 * and position only; this will normally fit on a single line.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_ERRORS_TERSE', 0);

/**
 * Passed to pg_set_error_verbosity.
 * The default mode produces messages that include the above 
 * plus any detail, hint, or context fields (these may span 
 * multiple lines).
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_ERRORS_DEFAULT', 1);

/**
 * Passed to pg_set_error_verbosity.
 * The verbose mode includes all available fields.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_ERRORS_VERBOSE', 2);

/**
 * Passed to pg_lo_seek. Seek operation is to begin
 * from the start of the object.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_SEEK_SET', 0);

/**
 * Passed to pg_lo_seek. Seek operation is to begin
 * from the current position.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_SEEK_CUR', 1);

/**
 * Passed to pg_lo_seek. Seek operation is to begin
 * from the end of the object.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_SEEK_END', 2);

/**
 * Passed to pg_result_status. Indicates that
 * numerical result code is desired.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_STATUS_LONG', 1);

/**
 * Passed to pg_result_status. Indicates that
 * textual result command tag is desired.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_STATUS_STRING', 2);

/**
 * Returned by pg_result_status. The string sent to the server
 * was empty.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_EMPTY_QUERY', 0);

/**
 * Returned by pg_result_status. Successful completion of a 
 * command returning no data.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_COMMAND_OK', 1);

/**
 * Returned by pg_result_status. Successful completion of a command 
 * returning data (such as a SELECT or SHOW).
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_TUPLES_OK', 2);

/**
 * Returned by pg_result_status. Copy Out (from server) data 
 * transfer started.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_COPY_OUT', 3);

/**
 * Returned by pg_result_status. Copy In (to server) data 
 * transfer started.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_COPY_IN', 4);

/**
 * Returned by pg_result_status. The server's response 
 * was not understood.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_BAD_RESPONSE', 5);

/**
 * Returned by pg_result_status. A nonfatal error 
 * (a notice or warning) occurred.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_NONFATAL_ERROR', 6);

/**
 * Returned by pg_result_status. A fatal error 
 * occurred.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
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
 * @var int
 */
define ('PGSQL_DIAG_SEVERITY', 83);

/**
 * Passed to pg_result_error_field.
 * The SQLSTATE code for the error. The SQLSTATE code identifies the type of error 
 * that has occurred; it can be used by front-end applications to perform specific 
 * operations (such as error handling) in response to a particular database error. 
 * This field is not localizable, and is always present.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_DIAG_SQLSTATE', 67);

/**
 * Passed to pg_result_error_field.
 * The primary human-readable error message (typically one line). Always present.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_DIAG_MESSAGE_PRIMARY', 77);

/**
 * Passed to pg_result_error_field.
 * Detail: an optional secondary error message carrying more detail about the problem. May run to multiple lines.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_DIAG_MESSAGE_DETAIL', 68);

/**
 * Passed to pg_result_error_field.
 * Hint: an optional suggestion what to do about the problem. This is intended to differ from detail in that it
 * offers advice (potentially inappropriate) rather than hard facts. May run to multiple lines.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_DIAG_MESSAGE_HINT', 72);

/**
 * Passed to pg_result_error_field.
 * A string containing a decimal integer indicating an error cursor position as an index into the original 
 * statement string. The first character has index 1, and positions are measured in characters not bytes.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
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
 * @var int
 */
define ('PGSQL_DIAG_INTERNAL_POSITION', 112);

/**
 * Passed to pg_result_error_field.
 * The text of a failed internally-generated command. This could be, for example, a 
 * SQL query issued by a PL/pgSQL function.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_DIAG_INTERNAL_QUERY', 113);

/**
 * Passed to pg_result_error_field.
 * An indication of the context in which the error occurred. Presently 
 * this includes a call stack traceback of active procedural language 
 * functions and internally-generated queries. The trace is one entry 
 * per line, most recent first.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_DIAG_CONTEXT', 87);

/**
 * Passed to pg_result_error_field.
 * The file name of the PostgreSQL source-code location where the error 
 * was reported.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_DIAG_SOURCE_FILE', 70);

/**
 * Passed to pg_result_error_field.
 * The line number of the PostgreSQL source-code location where the 
 * error was reported.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_DIAG_SOURCE_LINE', 76);

/**
 * Passed to pg_result_error_field.
 * The name of the PostgreSQL source-code function reporting the error.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_DIAG_SOURCE_FUNCTION', 82);

/**
 * Available since PHP 7.3.0.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var string
 */
define ('PGSQL_DIAG_SCHEMA_NAME', 115);

/**
 * Available since PHP 7.3.0.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var string
 */
define ('PGSQL_DIAG_TABLE_NAME', 116);

/**
 * Available since PHP 7.3.0.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var string
 */
define ('PGSQL_DIAG_COLUMN_NAME', 99);

/**
 * Available since PHP 7.3.0.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var string
 */
define ('PGSQL_DIAG_DATATYPE_NAME', 100);

/**
 * Available since PHP 7.3.0.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var string
 */
define ('PGSQL_DIAG_CONSTRAINT_NAME', 110);

/**
 * The severity; the field contents are ERROR, FATAL, or PANIC (in an error message), or WARNING, NOTICE, DEBUG, INFO, or LOG (in a notice message). This is identical to the PG_DIAG_SEVERITY field except that the contents are never localized. This is present only in versions 9.6 and later / PHP 7.3.0 and later.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_DIAG_SEVERITY_NONLOCALIZED', 86);

/**
 * Passed to pg_convert.
 * Ignore default values in the table during conversion.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_CONV_IGNORE_DEFAULT', 2);

/**
 * Passed to pg_convert.
 * Use SQL NULL in place of an empty string.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_CONV_FORCE_NULL', 4);

/**
 * Passed to pg_convert.
 * Ignore conversion of null into SQL NOT NULL columns.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_CONV_IGNORE_NOT_NULL', 8);

/**
 * Passed to pg_insert, pg_select,
 * pg_update and pg_delete.
 * Apply escape to all parameters instead of calling pg_convert
 * internally. This option omits meta data look up. Query could be as fast as
 * pg_query and pg_send_query.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_DML_ESCAPE', 4096);

/**
 * Passed to pg_insert, pg_select,
 * pg_update and pg_delete.
 * All parameters passed as is. Manual escape is required
 * if parameters contain user supplied data. Use pg_escape_string
 * for it.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_DML_NO_CONV', 256);

/**
 * Passed to pg_insert, pg_select,
 * pg_update and pg_delete.
 * Execute query by these functions.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_DML_EXEC', 512);

/**
 * Passed to pg_insert, pg_select,
 * pg_update and pg_delete.
 * Execute asynchronous query by these functions.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_DML_ASYNC', 1024);

/**
 * Passed to pg_insert, pg_select,
 * pg_update and pg_delete.
 * Return executed query string.
 * @link http://www.php.net/manual/en/pgsql.constants.php
 * @var int
 */
define ('PGSQL_DML_STRING', 2048);


}

// End of pgsql v.8.2.6
