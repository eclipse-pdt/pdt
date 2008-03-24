<?php

// Start of pgsql v.

/**
 * Open a PostgreSQL connection
 * @link http://php.net/manual/en/function.pg-connect.php
 * @param connection_string string
 * @param connect_type int[optional]
 * @return resource 
 */
function pg_connect ($connection_string, $connect_type = null) {}

/**
 * Open a persistent PostgreSQL connection
 * @link http://php.net/manual/en/function.pg-pconnect.php
 * @param connection_string string
 * @param connect_type int[optional]
 * @return resource 
 */
function pg_pconnect ($connection_string, $connect_type = null) {}

/**
 * Closes a PostgreSQL connection
 * @link http://php.net/manual/en/function.pg-close.php
 * @param connection resource[optional]
 * @return bool 
 */
function pg_close ($connection = null) {}

/**
 * Get connection status
 * @link http://php.net/manual/en/function.pg-connection-status.php
 * @param connection resource
 * @return int 
 */
function pg_connection_status ($connection) {}

/**
 * Get connection is busy or not
 * @link http://php.net/manual/en/function.pg-connection-busy.php
 * @param connection resource
 * @return bool true if the connection is busy, false otherwise.
 */
function pg_connection_busy ($connection) {}

/**
 * Reset connection (reconnect)
 * @link http://php.net/manual/en/function.pg-connection-reset.php
 * @param connection resource
 * @return bool 
 */
function pg_connection_reset ($connection) {}

/**
 * Returns the host name associated with the connection
 * @link http://php.net/manual/en/function.pg-host.php
 * @param connection resource[optional]
 * @return string 
 */
function pg_host ($connection = null) {}

/**
 * Get the database name
 * @link http://php.net/manual/en/function.pg-dbname.php
 * @param connection resource[optional]
 * @return string 
 */
function pg_dbname ($connection = null) {}

/**
 * Return the port number associated with the connection
 * @link http://php.net/manual/en/function.pg-port.php
 * @param connection resource[optional]
 * @return int 
 */
function pg_port ($connection = null) {}

/**
 * Return the TTY name associated with the connection
 * @link http://php.net/manual/en/function.pg-tty.php
 * @param connection resource[optional]
 * @return string 
 */
function pg_tty ($connection = null) {}

/**
 * Get the options associated with the connection
 * @link http://php.net/manual/en/function.pg-options.php
 * @param connection resource[optional]
 * @return string 
 */
function pg_options ($connection = null) {}

/**
 * Returns an array with client, protocol and server version (when available)
 * @link http://php.net/manual/en/function.pg-version.php
 * @param connection resource[optional]
 * @return array an array with client, protocol
 */
function pg_version ($connection = null) {}

/**
 * Ping database connection
 * @link http://php.net/manual/en/function.pg-ping.php
 * @param connection resource[optional]
 * @return bool 
 */
function pg_ping ($connection = null) {}

/**
 * Looks up a current parameter setting of the server.
 * @link http://php.net/manual/en/function.pg-parameter-status.php
 * @param connection resource
 * @param param_name string
 * @return string 
 */
function pg_parameter_status ($connection, $param_name) {}

/**
 * Returns the current in-transaction status of the server.
 * @link http://php.net/manual/en/function.pg-transaction-status.php
 * @param connection resource
 * @return int 
 */
function pg_transaction_status ($connection) {}

/**
 * Execute a query
 * @link http://php.net/manual/en/function.pg-query.php
 * @param query string
 * @return resource 
 */
function pg_query ($query) {}

/**
 * Submits a command to the server and waits for the result, with the ability to pass parameters separately from the SQL command text.
 * @link http://php.net/manual/en/function.pg-query-params.php
 * @param connection resource
 * @param query string
 * @param params array
 * @return resource 
 */
function pg_query_params ($connection, $query, array $params) {}

/**
 * Submits a request to create a prepared statement with the 
  given parameters, and waits for completion.
 * @link http://php.net/manual/en/function.pg-prepare.php
 * @param connection resource
 * @param stmtname string
 * @param query string
 * @return resource 
 */
function pg_prepare ($connection, $stmtname, $query) {}

/**
 * Sends a request to execute a prepared statement with given parameters, and waits for the result.
 * @link http://php.net/manual/en/function.pg-execute.php
 * @param connection resource
 * @param stmtname string
 * @param params array
 * @return resource 
 */
function pg_execute ($connection, $stmtname, array $params) {}

/**
 * Sends asynchronous query
 * @link http://php.net/manual/en/function.pg-send-query.php
 * @param connection resource
 * @param query string
 * @return bool 
 */
function pg_send_query ($connection, $query) {}

/**
 * Submits a command and separate parameters to the server without waiting for the result(s).
 * @link http://php.net/manual/en/function.pg-send-query-params.php
 * @param connection resource
 * @param query string
 * @param params array
 * @return bool 
 */
function pg_send_query_params ($connection, $query, array $params) {}

/**
 * Sends a request to create a prepared statement with the given parameters, without waiting for completion.
 * @link http://php.net/manual/en/function.pg-send-prepare.php
 * @param connection resource
 * @param stmtname string
 * @param query string
 * @return bool true on success, false on failure. Use pg_get_result
 */
function pg_send_prepare ($connection, $stmtname, $query) {}

/**
 * Sends a request to execute a prepared statement with given parameters, without waiting for the result(s).
 * @link http://php.net/manual/en/function.pg-send-execute.php
 * @param connection resource
 * @param stmtname string
 * @param params array
 * @return bool true on success, false on failure. Use pg_get_result
 */
function pg_send_execute ($connection, $stmtname, array $params) {}

/**
 * Cancel an asynchronous query
 * @link http://php.net/manual/en/function.pg-cancel-query.php
 * @param connection resource
 * @return bool 
 */
function pg_cancel_query ($connection) {}

/**
 * Returns values from a result resource
 * @link http://php.net/manual/en/function.pg-fetch-result.php
 * @param result resource
 * @param row int
 * @param field mixed
 * @return string 
 */
function pg_fetch_result ($result, $row, $field) {}

/**
 * Get a row as an enumerated array
 * @link http://php.net/manual/en/function.pg-fetch-row.php
 * @param result resource
 * @param row int[optional]
 * @param result_type int[optional]
 * @return array 
 */
function pg_fetch_row ($result, $row = null, $result_type = null) {}

/**
 * Fetch a row as an associative array
 * @link http://php.net/manual/en/function.pg-fetch-assoc.php
 * @param result resource
 * @param row int[optional]
 * @return array 
 */
function pg_fetch_assoc ($result, $row = null) {}

/**
 * Fetch a row as an array
 * @link http://php.net/manual/en/function.pg-fetch-array.php
 * @param result resource
 * @param row int[optional]
 * @param result_type int[optional]
 * @return array 
 */
function pg_fetch_array ($result, $row = null, $result_type = null) {}

/**
 * Fetch a row as an object
 * @link http://php.net/manual/en/function.pg-fetch-object.php
 * @param result resource
 * @param row int[optional]
 * @param result_type int[optional]
 * @return object 
 */
function pg_fetch_object ($result, $row = null, $result_type = null) {}

/**
 * Fetches all rows from a result as an array
 * @link http://php.net/manual/en/function.pg-fetch-all.php
 * @param result resource
 * @return array 
 */
function pg_fetch_all ($result) {}

/**
 * Fetches all rows in a particular result column as an array
 * @link http://php.net/manual/en/function.pg-fetch-all-columns.php
 * @param result resource
 * @param column int[optional]
 * @return array 
 */
function pg_fetch_all_columns ($result, $column = null) {}

/**
 * Returns number of affected records (tuples)
 * @link http://php.net/manual/en/function.pg-affected-rows.php
 * @param result resource
 * @return int 
 */
function pg_affected_rows ($result) {}

/**
 * Get asynchronous query result
 * @link http://php.net/manual/en/function.pg-get-result.php
 * @param connection resource[optional]
 * @return resource 
 */
function pg_get_result ($connection = null) {}

/**
 * Set internal row offset in result resource
 * @link http://php.net/manual/en/function.pg-result-seek.php
 * @param result resource
 * @param offset int
 * @return bool 
 */
function pg_result_seek ($result, $offset) {}

/**
 * Get status of query result
 * @link http://php.net/manual/en/function.pg-result-status.php
 * @param result resource
 * @param type int[optional]
 * @return mixed 
 */
function pg_result_status ($result, $type = null) {}

/**
 * Free result memory
 * @link http://php.net/manual/en/function.pg-free-result.php
 * @param result resource
 * @return bool 
 */
function pg_free_result ($result) {}

/**
 * Returns the last row's OID
 * @link http://php.net/manual/en/function.pg-last-oid.php
 * @param result resource
 * @return string 
 */
function pg_last_oid ($result) {}

/**
 * Returns the number of rows in a result
 * @link http://php.net/manual/en/function.pg-num-rows.php
 * @param result resource
 * @return int 
 */
function pg_num_rows ($result) {}

/**
 * Returns the number of fields in a result
 * @link http://php.net/manual/en/function.pg-num-fields.php
 * @param result resource
 * @return int 
 */
function pg_num_fields ($result) {}

/**
 * Returns the name of a field
 * @link http://php.net/manual/en/function.pg-field-name.php
 * @param result resource
 * @param field_number int
 * @return string 
 */
function pg_field_name ($result, $field_number) {}

/**
 * Returns the field number of the named field
 * @link http://php.net/manual/en/function.pg-field-num.php
 * @param result resource
 * @param field_name string
 * @return int 
 */
function pg_field_num ($result, $field_name) {}

/**
 * Returns the internal storage size of the named field
 * @link http://php.net/manual/en/function.pg-field-size.php
 * @param result resource
 * @param field_number int
 * @return int 
 */
function pg_field_size ($result, $field_number) {}

/**
 * Returns the type name for the corresponding field number
 * @link http://php.net/manual/en/function.pg-field-type.php
 * @param result resource
 * @param field_number int
 * @return string 
 */
function pg_field_type ($result, $field_number) {}

/**
 * Returns the type ID (OID) for the corresponding field number
 * @link http://php.net/manual/en/function.pg-field-type-oid.php
 * @param result resource
 * @param field_number int
 * @return int 
 */
function pg_field_type_oid ($result, $field_number) {}

/**
 * Returns the printed length
 * @link http://php.net/manual/en/function.pg-field-prtlen.php
 * @param result resource
 * @param row_number int
 * @param field_name_or_number mixed
 * @return int 
 */
function pg_field_prtlen ($result, $row_number, $field_name_or_number) {}

/**
 * Test if a field is SQL <literal>NULL</literal>
 * @link http://php.net/manual/en/function.pg-field-is-null.php
 * @param result resource
 * @param row int
 * @param field mixed
 * @return int 1 if the field in the given row is SQL NULL, 0
 */
function pg_field_is_null ($result, $row, $field) {}

/**
 * Returns the name or oid of the tables field
 * @link http://php.net/manual/en/function.pg-field-table.php
 * @param result resource
 * @param field_number int
 * @param oid_only bool[optional]
 * @return mixed 
 */
function pg_field_table ($result, $field_number, $oid_only = null) {}

/**
 * Gets SQL NOTIFY message
 * @link http://php.net/manual/en/function.pg-get-notify.php
 * @param connection resource
 * @param result_type int[optional]
 * @return array 
 */
function pg_get_notify ($connection, $result_type = null) {}

/**
 * Gets the backend's process ID
 * @link http://php.net/manual/en/function.pg-get-pid.php
 * @param connection resource
 * @return int 
 */
function pg_get_pid ($connection) {}

/**
 * Get error message associated with result
 * @link http://php.net/manual/en/function.pg-result-error.php
 * @param result resource
 * @return string a string if there is an error associated with the
 */
function pg_result_error ($result) {}

/**
 * Returns an individual field of an error report.
 * @link http://php.net/manual/en/function.pg-result-error-field.php
 * @param result resource
 * @param fieldcode int
 * @return string 
 */
function pg_result_error_field ($result, $fieldcode) {}

/**
 * Get the last error message string of a connection
 * @link http://php.net/manual/en/function.pg-last-error.php
 * @param connection resource[optional]
 * @return string 
 */
function pg_last_error ($connection = null) {}

/**
 * Returns the last notice message from PostgreSQL server
 * @link http://php.net/manual/en/function.pg-last-notice.php
 * @param connection resource
 * @return string 
 */
function pg_last_notice ($connection) {}

/**
 * Send a NULL-terminated string to PostgreSQL backend
 * @link http://php.net/manual/en/function.pg-put-line.php
 * @param data string
 * @return bool 
 */
function pg_put_line ($data) {}

/**
 * Sync with PostgreSQL backend
 * @link http://php.net/manual/en/function.pg-end-copy.php
 * @param connection resource[optional]
 * @return bool 
 */
function pg_end_copy ($connection = null) {}

/**
 * Copy a table to an array
 * @link http://php.net/manual/en/function.pg-copy-to.php
 * @param connection resource
 * @param table_name string
 * @param delimiter string[optional]
 * @param null_as string[optional]
 * @return array 
 */
function pg_copy_to ($connection, $table_name, $delimiter = null, $null_as = null) {}

/**
 * Insert records into a table from an array
 * @link http://php.net/manual/en/function.pg-copy-from.php
 * @param connection resource
 * @param table_name string
 * @param rows array
 * @param delimiter string[optional]
 * @param null_as string[optional]
 * @return bool 
 */
function pg_copy_from ($connection, $table_name, array $rows, $delimiter = null, $null_as = null) {}

/**
 * Enable tracing a PostgreSQL connection
 * @link http://php.net/manual/en/function.pg-trace.php
 * @param pathname string
 * @param mode string[optional]
 * @param connection resource[optional]
 * @return bool 
 */
function pg_trace ($pathname, $mode = null, $connection = null) {}

/**
 * Disable tracing of a PostgreSQL connection
 * @link http://php.net/manual/en/function.pg-untrace.php
 * @param connection resource[optional]
 * @return bool 
 */
function pg_untrace ($connection = null) {}

/**
 * Create a large object
 * @link http://php.net/manual/en/function.pg-lo-create.php
 * @param connection resource[optional]
 * @return int 
 */
function pg_lo_create ($connection = null) {}

/**
 * Delete a large object
 * @link http://php.net/manual/en/function.pg-lo-unlink.php
 * @param connection resource
 * @param oid int
 * @return bool 
 */
function pg_lo_unlink ($connection, $oid) {}

/**
 * Open a large object
 * @link http://php.net/manual/en/function.pg-lo-open.php
 * @param connection resource
 * @param oid int
 * @param mode string
 * @return resource 
 */
function pg_lo_open ($connection, $oid, $mode) {}

/**
 * Close a large object
 * @link http://php.net/manual/en/function.pg-lo-close.php
 * @param large_object resource
 * @return bool 
 */
function pg_lo_close ($large_object) {}

/**
 * Read a large object
 * @link http://php.net/manual/en/function.pg-lo-read.php
 * @param large_object resource
 * @param len int[optional]
 * @return string 
 */
function pg_lo_read ($large_object, $len = null) {}

/**
 * Write to a large object
 * @link http://php.net/manual/en/function.pg-lo-write.php
 * @param large_object resource
 * @param data string
 * @param len int[optional]
 * @return int 
 */
function pg_lo_write ($large_object, $data, $len = null) {}

/**
 * Reads an entire large object and send straight to browser
 * @link http://php.net/manual/en/function.pg-lo-read-all.php
 * @param large_object resource
 * @return int 
 */
function pg_lo_read_all ($large_object) {}

/**
 * Import a large object from file
 * @link http://php.net/manual/en/function.pg-lo-import.php
 * @param connection resource
 * @param pathname string
 * @return int 
 */
function pg_lo_import ($connection, $pathname) {}

/**
 * Export a large object to file
 * @link http://php.net/manual/en/function.pg-lo-export.php
 * @param connection resource
 * @param oid int
 * @param pathname string
 * @return bool 
 */
function pg_lo_export ($connection, $oid, $pathname) {}

/**
 * Seeks position within a large object
 * @link http://php.net/manual/en/function.pg-lo-seek.php
 * @param large_object resource
 * @param offset int
 * @param whence int[optional]
 * @return bool 
 */
function pg_lo_seek ($large_object, $offset, $whence = null) {}

/**
 * Returns current seek position a of large object
 * @link http://php.net/manual/en/function.pg-lo-tell.php
 * @param large_object resource
 * @return int 
 */
function pg_lo_tell ($large_object) {}

/**
 * Escape a string for insertion into a text field
 * @link http://php.net/manual/en/function.pg-escape-string.php
 * @param connection resource[optional]
 * @param data string
 * @return string 
 */
function pg_escape_string ($connection = null, $data) {}

/**
 * Escape a string for insertion into a bytea field
 * @link http://php.net/manual/en/function.pg-escape-bytea.php
 * @param connection resource[optional]
 * @param data string
 * @return string 
 */
function pg_escape_bytea ($connection = null, $data) {}

/**
 * Unescape binary for bytea type
 * @link http://php.net/manual/en/function.pg-unescape-bytea.php
 * @param data string
 * @return string 
 */
function pg_unescape_bytea ($data) {}

/**
 * Determines the verbosity of messages returned by <function>pg_last_error</function> 
   and <function>pg_result_error</function>.
 * @link http://php.net/manual/en/function.pg-set-error-verbosity.php
 * @param connection resource
 * @param verbosity int
 * @return int 
 */
function pg_set_error_verbosity ($connection, $verbosity) {}

/**
 * Gets the client encoding
 * @link http://php.net/manual/en/function.pg-client-encoding.php
 * @param connection resource[optional]
 * @return string 
 */
function pg_client_encoding ($connection = null) {}

/**
 * Set the client encoding
 * @link http://php.net/manual/en/function.pg-set-client-encoding.php
 * @param encoding string
 * @return int 0 on success or -1 on error.
 */
function pg_set_client_encoding ($encoding) {}

/**
 * Get meta data for table
 * @link http://php.net/manual/en/function.pg-meta-data.php
 * @param connection resource
 * @param table_name string
 * @return array 
 */
function pg_meta_data ($connection, $table_name) {}

/**
 * Convert associative array values into suitable for SQL statement
 * @link http://php.net/manual/en/function.pg-convert.php
 * @param connection resource
 * @param table_name string
 * @param assoc_array array
 * @param options int[optional]
 * @return array 
 */
function pg_convert ($connection, $table_name, array $assoc_array, $options = null) {}

/**
 * Insert array into table
 * @link http://php.net/manual/en/function.pg-insert.php
 * @param connection resource
 * @param table_name string
 * @param assoc_array array
 * @param options int[optional]
 * @return mixed 
 */
function pg_insert ($connection, $table_name, array $assoc_array, $options = null) {}

/**
 * Update table
 * @link http://php.net/manual/en/function.pg-update.php
 * @param connection resource
 * @param table_name string
 * @param data array
 * @param condition array
 * @param options int[optional]
 * @return mixed 
 */
function pg_update ($connection, $table_name, array $data, array $condition, $options = null) {}

/**
 * Deletes records
 * @link http://php.net/manual/en/function.pg-delete.php
 * @param connection resource
 * @param table_name string
 * @param assoc_array array
 * @param options int[optional]
 * @return mixed 
 */
function pg_delete ($connection, $table_name, array $assoc_array, $options = null) {}

/**
 * Select records
 * @link http://php.net/manual/en/function.pg-select.php
 * @param connection resource
 * @param table_name string
 * @param assoc_array array
 * @param options int[optional]
 * @return mixed 
 */
function pg_select ($connection, $table_name, array $assoc_array, $options = null) {}

function pg_exec () {}

function pg_getlastoid () {}

function pg_cmdtuples () {}

function pg_errormessage () {}

function pg_numrows () {}

function pg_numfields () {}

function pg_fieldname () {}

function pg_fieldsize () {}

function pg_fieldtype () {}

function pg_fieldnum () {}

function pg_fieldprtlen () {}

function pg_fieldisnull () {}

function pg_freeresult () {}

function pg_result () {}

function pg_loreadall () {}

function pg_locreate () {}

function pg_lounlink () {}

function pg_loopen () {}

function pg_loclose () {}

function pg_loread () {}

function pg_lowrite () {}

function pg_loimport () {}

function pg_loexport () {}

function pg_clientencoding () {}

function pg_setclientencoding () {}


/**
 * Passed to pg_connect to force the creation of a new connection,
 * rather then re-using an existing identical connection.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_CONNECT_FORCE_NEW', 2);

/**
 * Passed to pg_fetch_array. Return an associative array of field
 * names and values.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_ASSOC', 1);

/**
 * Passed to pg_fetch_array. Return a numerically indexed array of field
 * numbers and values.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_NUM', 2);

/**
 * Passed to pg_fetch_array. Return an array of field values
 * that is both numerically indexed (by field number) and associated (by field name).
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_BOTH', 3);

/**
 * Returned by pg_connection_status indicating that the database
 * connection is in an invalid state.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_CONNECTION_BAD', 1);

/**
 * Returned by pg_connection_status indicating that the database
 * connection is in a valid state.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_CONNECTION_OK', 0);

/**
 * Returned by pg_transaction_status. Connection is
 * currently idle, not in a transaction.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_TRANSACTION_IDLE', 0);

/**
 * Returned by pg_transaction_status. A command
 * is in progress on the connection. A query has been sent via the connection
 * and not yet completed.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_TRANSACTION_ACTIVE', 1);

/**
 * Returned by pg_transaction_status. The connection
 * is idle, in a transaction block.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_TRANSACTION_INTRANS', 2);

/**
 * Returned by pg_transaction_status. The connection
 * is idle, in a failed transaction block.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_TRANSACTION_INERROR', 3);

/**
 * Returned by pg_transaction_status. The connection
 * is bad.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_TRANSACTION_UNKNOWN', 4);

/**
 * Passed to pg_set_error_verbosity.
 * Specified that returned messages include severity, primary text, 
 * and position only; this will normally fit on a single line.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_ERRORS_TERSE', 0);

/**
 * Passed to pg_set_error_verbosity.
 * The default mode produces messages that include the above 
 * plus any detail, hint, or context fields (these may span 
 * multiple lines).
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_ERRORS_DEFAULT', 1);

/**
 * Passed to pg_set_error_verbosity.
 * The verbose mode includes all available fields.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_ERRORS_VERBOSE', 2);

/**
 * Passed to pg_lo_seek. Seek operation is to begin
 * from the start of the object.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_SEEK_SET', 0);

/**
 * Passed to pg_lo_seek. Seek operation is to begin
 * from the current position.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_SEEK_CUR', 1);

/**
 * Passed to pg_lo_seek. Seek operation is to begin
 * from the end of the object.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_SEEK_END', 2);

/**
 * Passed to pg_result_status. Indicates that
 * numerical result code is desired.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_STATUS_LONG', 1);

/**
 * Passed to pg_result_status. Indicates that
 * textual result command tag is desired.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_STATUS_STRING', 2);

/**
 * Returned by pg_result_status. The string sent to the server
 * was empty.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_EMPTY_QUERY', 0);

/**
 * Returned by pg_result_status. Successful completion of a 
 * command returning no data.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_COMMAND_OK', 1);

/**
 * Returned by pg_result_status. Successful completion of a command 
 * returning data (such as a SELECT or SHOW).
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_TUPLES_OK', 2);

/**
 * Returned by pg_result_status. Copy Out (from server) data 
 * transfer started.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_COPY_OUT', 3);

/**
 * Returned by pg_result_status. Copy In (to server) data 
 * transfer started.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_COPY_IN', 4);

/**
 * Returned by pg_result_status. The server's response 
 * was not understood.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_BAD_RESPONSE', 5);

/**
 * Returned by pg_result_status. A nonfatal error 
 * (a notice or warning) occurred.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_NONFATAL_ERROR', 6);

/**
 * Returned by pg_result_status. A fatal error 
 * occurred.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_FATAL_ERROR', 7);

/**
 * Passed to pg_result_error_field.
 * The severity; the field contents are ERROR, 
 * FATAL, or PANIC (in an error message), or 
 * WARNING, NOTICE, DEBUG, 
 * INFO, or LOG (in a notice message), or a localized 
 * translation of one of these. Always present.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_SEVERITY', 83);

/**
 * Passed to pg_result_error_field.
 * The SQLSTATE code for the error. The SQLSTATE code identifies the type of error 
 * that has occurred; it can be used by front-end applications to perform specific 
 * operations (such as error handling) in response to a particular database error. 
 * This field is not localizable, and is always present.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_SQLSTATE', 67);

/**
 * Passed to pg_result_error_field.
 * The primary human-readable error message (typically one line). Always present.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_MESSAGE_PRIMARY', 77);

/**
 * Passed to pg_result_error_field.
 * Detail: an optional secondary error message carrying more detail about the problem. May run to multiple lines.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_MESSAGE_DETAIL', 68);

/**
 * Passed to pg_result_error_field.
 * Hint: an optional suggestion what to do about the problem. This is intended to differ from detail in that it
 * offers advice (potentially inappropriate) rather than hard facts. May run to multiple lines.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_MESSAGE_HINT', 72);

/**
 * Passed to pg_result_error_field.
 * A string containing a decimal integer indicating an error cursor position as an index into the original 
 * statement string. The first character has index 1, and positions are measured in characters not bytes.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_STATEMENT_POSITION', 80);

/**
 * Passed to pg_result_error_field.
 * This is defined the same as the PG_DIAG_STATEMENT_POSITION field, but 
 * it is used when the cursor position refers to an internally generated 
 * command rather than the one submitted by the client. The 
 * PG_DIAG_INTERNAL_QUERY field will always appear when this 
 * field appears.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_INTERNAL_POSITION', 112);

/**
 * Passed to pg_result_error_field.
 * The text of a failed internally-generated command. This could be, for example, a 
 * SQL query issued by a PL/pgSQL function.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_INTERNAL_QUERY', 113);

/**
 * Passed to pg_result_error_field.
 * An indication of the context in which the error occurred. Presently 
 * this includes a call stack traceback of active procedural language 
 * functions and internally-generated queries. The trace is one entry 
 * per line, most recent first.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_CONTEXT', 87);

/**
 * Passed to pg_result_error_field.
 * The file name of the PostgreSQL source-code location where the error 
 * was reported.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_SOURCE_FILE', 70);

/**
 * Passed to pg_result_error_field.
 * The line number of the PostgreSQL source-code location where the 
 * error was reported.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_SOURCE_LINE', 76);

/**
 * Passed to pg_result_error_field.
 * The name of the PostgreSQL source-code function reporting the error.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_DIAG_SOURCE_FUNCTION', 82);

/**
 * Passed to pg_convert.
 * Ignore conversion of &null; into SQL NOT NULL columns.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_CONV_IGNORE_DEFAULT', 2);

/**
 * Passed to pg_convert.
 * Use SQL NULL in place of an empty string.
 * @link http://php.net/manual/en/pgsql.constants.php
 */
define ('PGSQL_CONV_FORCE_NULL', 4);
define ('PGSQL_CONV_IGNORE_NOT_NULL', 8);
define ('PGSQL_DML_NO_CONV', 256);
define ('PGSQL_DML_EXEC', 512);
define ('PGSQL_DML_ASYNC', 1024);
define ('PGSQL_DML_STRING', 2048);

// End of pgsql v.
?>
