<?php

// Start of ibm_db2 v.1.6.0

/**
 * Returns a connection to a database
 * @link http://php.net/manual/en/function.db2-connect.php
 * @param database string
 * @param username string
 * @param password string
 * @param options array[optional]
 * @return resource a connection handle resource if the connection attempt is
 */
function db2_connect ($database, $username, $password, array $options = null) {}

/**
 * Commits a transaction
 * @link http://php.net/manual/en/function.db2-commit.php
 * @param connection resource
 * @return bool 
 */
function db2_commit ($connection) {}

/**
 * Returns a persistent connection to a database
 * @link http://php.net/manual/en/function.db2-pconnect.php
 * @param database string
 * @param username string
 * @param password string
 * @param options array[optional]
 * @return resource a connection handle resource if the connection attempt is
 */
function db2_pconnect ($database, $username, $password, array $options = null) {}

/**
 * Returns or sets the AUTOCOMMIT state for a database connection
 * @link http://php.net/manual/en/function.db2-autocommit.php
 * @param connection resource
 * @param value bool[optional]
 * @return mixed 
 */
function db2_autocommit ($connection, $value = null) {}

/**
 * Binds a PHP variable to an SQL statement parameter
 * @link http://php.net/manual/en/function.db2-bind-param.php
 * @param stmt resource
 * @param parameter_number int
 * @param variable_name string
 * @param parameter_type int[optional]
 * @param data_type int[optional]
 * @param precision int[optional]
 * @param scale int[optional]
 * @return bool 
 */
function db2_bind_param ($stmt, $parameter_number, $variable_name, $parameter_type = null, $data_type = null, $precision = null, $scale = null) {}

/**
 * Closes a database connection
 * @link http://php.net/manual/en/function.db2-close.php
 * @param connection resource
 * @return bool 
 */
function db2_close ($connection) {}

/**
 * Returns a result set listing the columns and associated privileges for a table
 * @link http://php.net/manual/en/function.db2-column-privileges.php
 * @param connection resource
 * @param qualifier string[optional]
 * @param schema string[optional]
 * @param table_name string[optional]
 * @param column_name string[optional]
 * @return resource a statement resource with a result set containing rows describing
 */
function db2_column_privileges ($connection, $qualifier = null, $schema = null, $table_name = null, $column_name = null) {}

function db2_columnprivileges () {}

/**
 * Returns a result set listing the columns and associated metadata for a table
 * @link http://php.net/manual/en/function.db2-columns.php
 * @param connection resource
 * @param qualifier string[optional]
 * @param schema string[optional]
 * @param table_name string[optional]
 * @param column_name string[optional]
 * @return resource a statement resource with a result set containing rows describing
 */
function db2_columns ($connection, $qualifier = null, $schema = null, $table_name = null, $column_name = null) {}

/**
 * Returns a result set listing the foreign keys for a table
 * @link http://php.net/manual/en/function.db2-foreign-keys.php
 * @param connection resource
 * @param qualifier string
 * @param schema string
 * @param table_name string
 * @return resource a statement resource with a result set containing rows describing
 */
function db2_foreign_keys ($connection, $qualifier, $schema, $table_name) {}

function db2_foreignkeys () {}

/**
 * Returns a result set listing primary keys for a table
 * @link http://php.net/manual/en/function.db2-primary-keys.php
 * @param connection resource
 * @param qualifier string
 * @param schema string
 * @param table_name string
 * @return resource a statement resource with a result set containing rows describing
 */
function db2_primary_keys ($connection, $qualifier, $schema, $table_name) {}

function db2_primarykeys () {}

/**
 * Returns a result set listing stored procedure parameters
 * @link http://php.net/manual/en/function.db2-procedure-columns.php
 * @param connection resource
 * @param qualifier string
 * @param schema string
 * @param procedure string
 * @param parameter string
 * @return resource a statement resource with a result set containing rows describing
 */
function db2_procedure_columns ($connection, $qualifier, $schema, $procedure, $parameter) {}

function db2_procedurecolumns () {}

/**
 * Returns a result set listing the stored procedures registered in a database
 * @link http://php.net/manual/en/function.db2-procedures.php
 * @param connection resource
 * @param qualifier string
 * @param schema string
 * @param procedure string
 * @return resource a statement resource with a result set containing rows describing
 */
function db2_procedures ($connection, $qualifier, $schema, $procedure) {}

/**
 * Returns a result set listing the unique row identifier columns for a table
 * @link http://php.net/manual/en/function.db2-special-columns.php
 * @param connection resource
 * @param qualifier string
 * @param schema string
 * @param table_name string
 * @param scope int
 * @return resource a statement resource with a result set containing rows with unique
 */
function db2_special_columns ($connection, $qualifier, $schema, $table_name, $scope) {}

function db2_specialcolumns () {}

/**
 * Returns a result set listing the index and statistics for a table
 * @link http://php.net/manual/en/function.db2-statistics.php
 * @param connection resource
 * @param qualifier string
 * @param schema string
 * @param table_name string
 * @param unique bool
 * @return resource a statement resource with a result set containing rows describing
 */
function db2_statistics ($connection, $qualifier, $schema, $table_name, $unique) {}

/**
 * Returns a result set listing the tables and associated privileges in a database
 * @link http://php.net/manual/en/function.db2-table-privileges.php
 * @param connection resource
 * @param qualifier string[optional]
 * @param schema string[optional]
 * @param table_name string[optional]
 * @return resource a statement resource with a result set containing rows describing
 */
function db2_table_privileges ($connection, $qualifier = null, $schema = null, $table_name = null) {}

function db2_tableprivileges () {}

/**
 * Returns a result set listing the tables and associated metadata in a database
 * @link http://php.net/manual/en/function.db2-tables.php
 * @param connection resource
 * @param qualifier string[optional]
 * @param schema string[optional]
 * @param table_name string[optional]
 * @param table_type string[optional]
 * @return resource a statement resource with a result set containing rows describing
 */
function db2_tables ($connection, $qualifier = null, $schema = null, $table_name = null, $table_type = null) {}

/**
 * Executes an SQL statement directly
 * @link http://php.net/manual/en/function.db2-exec.php
 * @param connection resource
 * @param statement string
 * @param options array[optional]
 * @return resource a statement resource if the SQL statement was issued successfully,
 */
function db2_exec ($connection, $statement, array $options = null) {}

/**
 * Prepares an SQL statement to be executed
 * @link http://php.net/manual/en/function.db2-prepare.php
 * @param connection resource
 * @param statement string
 * @param options array[optional]
 * @return resource a statement resource if the SQL statement was successfully parsed and
 */
function db2_prepare ($connection, $statement, array $options = null) {}

/**
 * Executes a prepared SQL statement
 * @link http://php.net/manual/en/function.db2-execute.php
 * @param stmt resource
 * @param parameters array[optional]
 * @return bool 
 */
function db2_execute ($stmt, array $parameters = null) {}

/**
 * Returns a string containing the last SQL statement error message
 * @link http://php.net/manual/en/function.db2-stmt-errormsg.php
 * @param stmt resource[optional]
 * @return string a string containing the error message and SQLCODE value for the
 */
function db2_stmt_errormsg ($stmt = null) {}

/**
 * Returns the last connection error message and SQLCODE value
 * @link http://php.net/manual/en/function.db2-conn-errormsg.php
 * @param connection resource[optional]
 * @return string a string containing the error message and SQLCODE value resulting
 */
function db2_conn_errormsg ($connection = null) {}

/**
 * Returns a string containing the SQLSTATE returned by the last connection attempt
 * @link http://php.net/manual/en/function.db2-conn-error.php
 * @param connection resource[optional]
 * @return string the SQLSTATE value resulting from a failed connection attempt.
 */
function db2_conn_error ($connection = null) {}

/**
 * Returns a string containing the SQLSTATE returned by an SQL statement
 * @link http://php.net/manual/en/function.db2-stmt-error.php
 * @param stmt resource[optional]
 * @return string a string containing an SQLSTATE value.
 */
function db2_stmt_error ($stmt = null) {}

/**
 * Requests the next result set from a stored procedure
 * @link http://php.net/manual/en/function.db2-next-result.php
 * @param stmt resource
 * @return resource a new statement resource containing the next result set if the
 */
function db2_next_result ($stmt) {}

/**
 * Returns the number of fields contained in a result set
 * @link http://php.net/manual/en/function.db2-num-fields.php
 * @param stmt resource
 * @return int an integer value representing the number of fields in the result
 */
function db2_num_fields ($stmt) {}

/**
 * Returns the number of rows affected by an SQL statement
 * @link http://php.net/manual/en/function.db2-num-rows.php
 * @param stmt resource
 * @return int the number of rows affected by the last SQL statement issued by
 */
function db2_num_rows ($stmt) {}

/**
 * Returns the name of the column in the result set
 * @link http://php.net/manual/en/function.db2-field-name.php
 * @param stmt resource
 * @param column mixed
 * @return string a string containing the name of the specified column. If the
 */
function db2_field_name ($stmt, $column) {}

/**
 * Returns the maximum number of bytes required to display a column
 * @link http://php.net/manual/en/function.db2-field-display-size.php
 * @param stmt resource
 * @param column mixed
 * @return int an integer value with the maximum number of bytes required to
 */
function db2_field_display_size ($stmt, $column) {}

/**
 * Returns the position of the named column in a result set
 * @link http://php.net/manual/en/function.db2-field-num.php
 * @param stmt resource
 * @param column mixed
 * @return int an integer containing the 0-indexed position of the named column in
 */
function db2_field_num ($stmt, $column) {}

/**
 * Returns the precision of the indicated column in a result set
 * @link http://php.net/manual/en/function.db2-field-precision.php
 * @param stmt resource
 * @param column mixed
 * @return int an integer containing the precision of the specified column. If the
 */
function db2_field_precision ($stmt, $column) {}

/**
 * Returns the scale of the indicated column in a result set
 * @link http://php.net/manual/en/function.db2-field-scale.php
 * @param stmt resource
 * @param column mixed
 * @return int an integer containing the scale of the specified column. If the
 */
function db2_field_scale ($stmt, $column) {}

/**
 * Returns the data type of the indicated column in a result set
 * @link http://php.net/manual/en/function.db2-field-type.php
 * @param stmt resource
 * @param column mixed
 * @return string a string containing the defined data type of the specified column.
 */
function db2_field_type ($stmt, $column) {}

/**
 * Returns the width of the current value of the indicated column in a result set
 * @link http://php.net/manual/en/function.db2-field-width.php
 * @param stmt resource
 * @param column mixed
 * @return int an integer containing the width of the specified character or
 */
function db2_field_width ($stmt, $column) {}

/**
 * Returns the cursor type used by a statement resource
 * @link http://php.net/manual/en/function.db2-cursor-type.php
 * @param stmt resource
 * @return int either DB2_FORWARD_ONLY if the statement
 */
function db2_cursor_type ($stmt) {}

/**
 * Rolls back a transaction
 * @link http://php.net/manual/en/function.db2-rollback.php
 * @param connection resource
 * @return bool 
 */
function db2_rollback ($connection) {}

/**
 * Frees resources associated with the indicated statement resource
 * @link http://php.net/manual/en/function.db2-free-stmt.php
 * @param stmt resource
 * @return bool 
 */
function db2_free_stmt ($stmt) {}

/**
 * Returns a single column from a row in the result set
 * @link http://php.net/manual/en/function.db2-result.php
 * @param stmt resource
 * @param column mixed
 * @return mixed the value of the requested field if the field exists in the result
 */
function db2_result ($stmt, $column) {}

/**
 * Sets the result set pointer to the next row or requested row
 * @link http://php.net/manual/en/function.db2-fetch-row.php
 * @param stmt resource
 * @param row_number int[optional]
 * @return bool true if the requested row exists in the result set. Returns
 */
function db2_fetch_row ($stmt, $row_number = null) {}

/**
 * Returns an array, indexed by column name, representing a row in a result set
 * @link http://php.net/manual/en/function.db2-fetch-assoc.php
 * @param stmt resource
 * @param row_number int[optional]
 * @return array an associative array with column values indexed by the column name
 */
function db2_fetch_assoc ($stmt, $row_number = null) {}

/**
 * Returns an array, indexed by column position, representing a row in a result set
 * @link http://php.net/manual/en/function.db2-fetch-array.php
 * @param stmt resource
 * @param row_number int[optional]
 * @return array a 0-indexed array with column values indexed by the column position
 */
function db2_fetch_array ($stmt, $row_number = null) {}

/**
 * Returns an array, indexed by both column name and position, representing a row in a result set
 * @link http://php.net/manual/en/function.db2-fetch-both.php
 * @param stmt resource
 * @param row_number int[optional]
 * @return array an associative array with column values indexed by both the column
 */
function db2_fetch_both ($stmt, $row_number = null) {}

/**
 * Frees resources associated with a result set
 * @link http://php.net/manual/en/function.db2-free-result.php
 * @param stmt resource
 * @return bool 
 */
function db2_free_result ($stmt) {}

/**
 * Set options for connection or statement resources
 * @link http://php.net/manual/en/function.db2-set-option.php
 * @param resource resource
 * @param options array
 * @param type int
 * @return bool 
 */
function db2_set_option ($resource, array $options, $type) {}

function db2_setoption () {}

/**
 * Returns an object with properties representing columns in the fetched row
 * @link http://php.net/manual/en/function.db2-fetch-object.php
 * @param stmt resource
 * @param row_number int[optional]
 * @return object an object representing a single row in the result set. The
 */
function db2_fetch_object ($stmt, $row_number = null) {}

/**
 * Returns an object with properties that describe the DB2 database server
 * @link http://php.net/manual/en/function.db2-server-info.php
 * @param connection resource
 * @return object an object on a successful call. Returns false on failure.
 */
function db2_server_info ($connection) {}

/**
 * Returns an object with properties that describe the DB2 database client
 * @link http://php.net/manual/en/function.db2-client-info.php
 * @param connection resource
 * @return object an object on a successful call. Returns false on failure.
 */
function db2_client_info ($connection) {}

/**
 * Used to escape certain characters
 * @link http://php.net/manual/en/function.db2-escape-string.php
 * @param string_literal string
 * @return string string_literal with the special characters
 */
function db2_escape_string ($string_literal) {}

/**
 * Gets a user defined size of LOB files with each invocation
 * @link http://php.net/manual/en/function.db2-lob-read.php
 * @param stmt resource
 * @param colnum int
 * @param length int
 * @return string the amount of data the user specifies. Returns
 */
function db2_lob_read ($stmt, $colnum, $length) {}

/**
 * Retrieves an option value for a statement resource or a connection resource
 * @link http://php.net/manual/en/function.db2-get-option.php
 * @param resource resource
 * @param option string
 * @return string the current setting of the connection attribute provided on success
 */
function db2_get_option ($resource, $option) {}

function db2_getoption () {}


/**
 * Specifies that binary data shall be returned as is. This is the default
 * mode.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_BINARY', 1);

/**
 * Specifies that binary data shall be converted to a hexadecimal encoding
 * and returned as an ASCII string.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_CONVERT', 2);

/**
 * Specifies that binary data shall be converted to a &null; value.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_PASSTHRU', 3);

/**
 * Specifies a scrollable cursor for a statement resource. This mode enables
 * random access to rows in a result set, but currently is supported only by
 * IBM DB2 Universal Database.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_SCROLLABLE', 1);

/**
 * Specifies a forward-only cursor for a statement resource. This is the
 * default cursor type and is supported on all database servers.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_FORWARD_ONLY', 0);

/**
 * Specifies the PHP variable should be bound as an IN parameter for a
 * stored procedure.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_PARAM_IN', 1);

/**
 * Specifies the PHP variable should be bound as an OUT parameter for a
 * stored procedure.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_PARAM_OUT', 4);

/**
 * Specifies the PHP variable should be bound as an INOUT parameter for a
 * stored procedure.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_PARAM_INOUT', 2);

/**
 * Specifies that the column should be bound directly to a file for input.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_PARAM_FILE', 11);

/**
 * Specifies that autocommit should be turned on.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_AUTOCOMMIT_ON', 1);

/**
 * Specifies that autocommit should be turned off.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_AUTOCOMMIT_OFF', 0);

/**
 * Specifies that deferred prepare should be turned on for the specified statement resource.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_DEFERRED_PREPARE_ON', 1);

/**
 * Specifies that deferred prepare should be turned off for the specified statement resource.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_DEFERRED_PREPARE_OFF', 0);

/**
 * Specifies that the variable should be bound as a DOUBLE, FLOAT, or REAL
 * data type.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_DOUBLE', 8);

/**
 * Specifies that the variable should be bound as a SMALLINT, INTEGER, or
 * BIGINT data type.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_LONG', 4);

/**
 * Specifies that the variable should be bound as a CHAR or VARCHAR data type.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_CHAR', 1);
define ('DB2_XML', -370);

/**
 * Specifies that column names will be returned in their natural case.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_CASE_NATURAL', 0);

/**
 * Specifies that column names will be returned in lower case.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_CASE_LOWER', 1);

/**
 * Specifies that column names will be returned in upper case.
 * @link http://php.net/manual/en/ibm-db2.constants.php
 */
define ('DB2_CASE_UPPER', 2);

// End of ibm_db2 v.1.6.0
?>
