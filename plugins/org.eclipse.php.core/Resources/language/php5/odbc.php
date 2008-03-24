<?php

// Start of odbc v.1.0

/**
 * Toggle autocommit behaviour
 * @link http://php.net/manual/en/function.odbc-autocommit.php
 * @param connection_id resource
 * @param OnOff bool[optional]
 * @return mixed 
 */
function odbc_autocommit ($connection_id, $OnOff = null) {}

/**
 * Handling of binary column data
 * @link http://php.net/manual/en/function.odbc-binmode.php
 * @param result_id resource
 * @param mode int
 * @return bool 
 */
function odbc_binmode ($result_id, $mode) {}

/**
 * Close an ODBC connection
 * @link http://php.net/manual/en/function.odbc-close.php
 * @param connection_id resource
 * @return void 
 */
function odbc_close ($connection_id) {}

/**
 * Close all ODBC connections
 * @link http://php.net/manual/en/function.odbc-close-all.php
 * @return void 
 */
function odbc_close_all () {}

/**
 * Lists the column names in specified tables
 * @link http://php.net/manual/en/function.odbc-columns.php
 * @param connection_id resource
 * @param qualifier string[optional]
 * @param schema string[optional]
 * @param table_name string[optional]
 * @param column_name string[optional]
 * @return resource an ODBC result identifier or false on failure.
 */
function odbc_columns ($connection_id, $qualifier = null, $schema = null, $table_name = null, $column_name = null) {}

/**
 * Commit an ODBC transaction
 * @link http://php.net/manual/en/function.odbc-commit.php
 * @param connection_id resource
 * @return bool 
 */
function odbc_commit ($connection_id) {}

/**
 * Connect to a datasource
 * @link http://php.net/manual/en/function.odbc-connect.php
 */
function odbc_connect () {}

/**
 * Get cursorname
 * @link http://php.net/manual/en/function.odbc-cursor.php
 * @param result_id resource
 * @return string the cursor name, as a string.
 */
function odbc_cursor ($result_id) {}

/**
 * Returns information about a current connection
 * @link http://php.net/manual/en/function.odbc-data-source.php
 * @param connection_id resource
 * @param fetch_type int
 * @return array false on error, and an array upon success.
 */
function odbc_data_source ($connection_id, $fetch_type) {}

/**
 * Execute a prepared statement
 * @link http://php.net/manual/en/function.odbc-execute.php
 */
function odbc_execute () {}

/**
 * Get the last error code
 * @link http://php.net/manual/en/function.odbc-error.php
 * @param connection_id resource[optional]
 * @return string 
 */
function odbc_error ($connection_id = null) {}

/**
 * Get the last error message
 * @link http://php.net/manual/en/function.odbc-errormsg.php
 * @param connection_id resource[optional]
 * @return string 
 */
function odbc_errormsg ($connection_id = null) {}

/**
 * Prepare and execute a SQL statement
 * @link http://php.net/manual/en/function.odbc-exec.php
 * @param connection_id resource
 * @param query_string string
 * @param flags int[optional]
 * @return resource an ODBC result identifier if the SQL command was executed
 */
function odbc_exec ($connection_id, $query_string, $flags = null) {}

/**
 * Fetch a result row as an associative array
 * @link http://php.net/manual/en/function.odbc-fetch-array.php
 * @param result resource
 * @param rownumber int[optional]
 * @return array an array that corresponds to the fetched row, or false if there
 */
function odbc_fetch_array ($result, $rownumber = null) {}

/**
 * Fetch a result row as an object
 * @link http://php.net/manual/en/function.odbc-fetch-object.php
 * @param result resource
 * @param rownumber int[optional]
 * @return object an object that corresponds to the fetched row, or false if there
 */
function odbc_fetch_object ($result, $rownumber = null) {}

/**
 * Fetch a row
 * @link http://php.net/manual/en/function.odbc-fetch-row.php
 * @param result_id resource
 * @param row_number int[optional]
 * @return bool true if there was a row, false otherwise.
 */
function odbc_fetch_row ($result_id, $row_number = null) {}

/**
 * Fetch one result row into array
 * @link http://php.net/manual/en/function.odbc-fetch-into.php
 * @param var1
 * @param var2
 */
function odbc_fetch_into ($var1, &$var2) {}

/**
 * Get the length (precision) of a field
 * @link http://php.net/manual/en/function.odbc-field-len.php
 * @param result_id resource
 * @param field_number int
 * @return int the field name as a string, or false on error.
 */
function odbc_field_len ($result_id, $field_number) {}

/**
 * Get the scale of a field
 * @link http://php.net/manual/en/function.odbc-field-scale.php
 * @param result_id resource
 * @param field_number int
 * @return int the field scale as a integer, or false on error.
 */
function odbc_field_scale ($result_id, $field_number) {}

/**
 * Get the columnname
 * @link http://php.net/manual/en/function.odbc-field-name.php
 * @param result_id resource
 * @param field_number int
 * @return string the field name as a string, or false on error.
 */
function odbc_field_name ($result_id, $field_number) {}

/**
 * Datatype of a field
 * @link http://php.net/manual/en/function.odbc-field-type.php
 * @param result_id resource
 * @param field_number int
 * @return string the field type as a string, or false on error.
 */
function odbc_field_type ($result_id, $field_number) {}

/**
 * Return column number
 * @link http://php.net/manual/en/function.odbc-field-num.php
 * @param result_id resource
 * @param field_name string
 * @return int the field number as a integer, or false on error.
 */
function odbc_field_num ($result_id, $field_name) {}

/**
 * Free resources associated with a result
 * @link http://php.net/manual/en/function.odbc-free-result.php
 * @param result_id resource
 * @return bool 
 */
function odbc_free_result ($result_id) {}

/**
 * Returns a result identifier containing information about data types supported by the data source
 * @link http://php.net/manual/en/function.odbc-gettypeinfo.php
 */
function odbc_gettypeinfo () {}

/**
 * Handling of LONG columns
 * @link http://php.net/manual/en/function.odbc-longreadlen.php
 * @param result_id resource
 * @param length int
 * @return bool 
 */
function odbc_longreadlen ($result_id, $length) {}

/**
 * Checks if multiple results are available
 * @link http://php.net/manual/en/function.odbc-next-result.php
 * @param result_id resource
 * @return bool true if there are more result sets, false otherwise.
 */
function odbc_next_result ($result_id) {}

/**
 * Number of columns in a result
 * @link http://php.net/manual/en/function.odbc-num-fields.php
 * @param result_id resource
 * @return int the number of fields, or -1 on error.
 */
function odbc_num_fields ($result_id) {}

/**
 * Number of rows in a result
 * @link http://php.net/manual/en/function.odbc-num-rows.php
 * @param result_id resource
 * @return int the number of rows in an ODBC result.
 */
function odbc_num_rows ($result_id) {}

/**
 * Open a persistent database connection
 * @link http://php.net/manual/en/function.odbc-pconnect.php
 */
function odbc_pconnect () {}

/**
 * Prepares a statement for execution
 * @link http://php.net/manual/en/function.odbc-prepare.php
 */
function odbc_prepare () {}

/**
 * Get result data
 * @link http://php.net/manual/en/function.odbc-result.php
 */
function odbc_result () {}

/**
 * Print result as HTML table
 * @link http://php.net/manual/en/function.odbc-result-all.php
 * @param result_id resource
 * @param format string[optional]
 * @return int the number of rows in the result or false on error.
 */
function odbc_result_all ($result_id, $format = null) {}

/**
 * Rollback a transaction
 * @link http://php.net/manual/en/function.odbc-rollback.php
 * @param connection_id resource
 * @return bool 
 */
function odbc_rollback ($connection_id) {}

/**
 * Adjust ODBC settings
 * @link http://php.net/manual/en/function.odbc-setoption.php
 */
function odbc_setoption () {}

/**
 * Returns either the optimal set of columns that uniquely identifies a row in the table or columns that are automatically updated when any value in the row is updated by a transaction
 * @link http://php.net/manual/en/function.odbc-specialcolumns.php
 */
function odbc_specialcolumns () {}

/**
 * Retrieve statistics about a table
 * @link http://php.net/manual/en/function.odbc-statistics.php
 */
function odbc_statistics () {}

/**
 * Get the list of table names stored in a specific data source
 * @link http://php.net/manual/en/function.odbc-tables.php
 */
function odbc_tables () {}

/**
 * Gets the primary keys for a table
 * @link http://php.net/manual/en/function.odbc-primarykeys.php
 * @param connection_id resource
 * @param qualifier string
 * @param owner string
 * @param table string
 * @return resource an ODBC result identifier or false on failure.
 */
function odbc_primarykeys ($connection_id, $qualifier, $owner, $table) {}

/**
 * Lists columns and associated privileges for the given table
 * @link http://php.net/manual/en/function.odbc-columnprivileges.php
 * @param connection_id resource
 * @param qualifier string
 * @param owner string
 * @param table_name string
 * @param column_name string
 * @return resource an ODBC result identifier or false on failure.
 */
function odbc_columnprivileges ($connection_id, $qualifier, $owner, $table_name, $column_name) {}

/**
 * Lists tables and the privileges associated with each table
 * @link http://php.net/manual/en/function.odbc-tableprivileges.php
 */
function odbc_tableprivileges () {}

/**
 * Returns a list of foreign keys in the specified table or a list of foreign keys in other tables that refer to the primary key in the specified table
 * @link http://php.net/manual/en/function.odbc-foreignkeys.php
 */
function odbc_foreignkeys () {}

/**
 * Get the list of procedures stored in a specific data source
 * @link http://php.net/manual/en/function.odbc-procedures.php
 */
function odbc_procedures () {}

/**
 * Retrieve information about parameters to procedures
 * @link http://php.net/manual/en/function.odbc-procedurecolumns.php
 */
function odbc_procedurecolumns () {}

/**
 * &Alias; <function>odbc_exec</function>
 * @link http://php.net/manual/en/function.odbc-do.php
 */
function odbc_do () {}

/**
 * &Alias; <function>odbc_field_len</function>
 * @link http://php.net/manual/en/function.odbc-field-precision.php
 */
function odbc_field_precision () {}

define ('ODBC_TYPE', "");
define ('ODBC_BINMODE_PASSTHRU', 0);
define ('ODBC_BINMODE_RETURN', 1);
define ('ODBC_BINMODE_CONVERT', 2);
define ('SQL_ODBC_CURSORS', 110);
define ('SQL_CUR_USE_DRIVER', 2);
define ('SQL_CUR_USE_IF_NEEDED', 0);
define ('SQL_CUR_USE_ODBC', 1);
define ('SQL_CONCURRENCY', 7);
define ('SQL_CONCUR_READ_ONLY', 1);
define ('SQL_CONCUR_LOCK', 2);
define ('SQL_CONCUR_ROWVER', 3);
define ('SQL_CONCUR_VALUES', 4);
define ('SQL_CURSOR_TYPE', 6);
define ('SQL_CURSOR_FORWARD_ONLY', 0);
define ('SQL_CURSOR_KEYSET_DRIVEN', 1);
define ('SQL_CURSOR_DYNAMIC', 2);
define ('SQL_CURSOR_STATIC', 3);
define ('SQL_KEYSET_SIZE', 8);
define ('SQL_FETCH_FIRST', 2);
define ('SQL_FETCH_NEXT', 1);
define ('SQL_CHAR', 1);
define ('SQL_VARCHAR', 12);
define ('SQL_LONGVARCHAR', -1);
define ('SQL_DECIMAL', 3);
define ('SQL_NUMERIC', 2);
define ('SQL_BIT', -7);
define ('SQL_TINYINT', -6);
define ('SQL_SMALLINT', 5);
define ('SQL_INTEGER', 4);
define ('SQL_BIGINT', -5);
define ('SQL_REAL', 7);
define ('SQL_FLOAT', 6);
define ('SQL_DOUBLE', 8);
define ('SQL_BINARY', -2);
define ('SQL_VARBINARY', -3);
define ('SQL_LONGVARBINARY', -4);
define ('SQL_DATE', 9);
define ('SQL_TIME', 10);
define ('SQL_TIMESTAMP', 11);

// End of odbc v.1.0
?>
