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
 * @param dsn string
 * @param user string
 * @param password string
 * @param cursor_type int[optional]
 * @return resource an ODBC connection id or 0 (false) on
 */
function odbc_connect ($dsn, $user, $password, $cursor_type = null) {}

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
 * @param result_id resource
 * @param parameters_array array[optional]
 * @return bool 
 */
function odbc_execute ($result_id, array $parameters_array = null) {}

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
 * @param result_id resource
 * @param result_array array
 * @param rownumber int[optional]
 * @return int the number of columns in the result;
 */
function odbc_fetch_into ($result_id, array $result_array, $rownumber = null) {}

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
 * Retrieves information about data types supported by the data source
 * @link http://php.net/manual/en/function.odbc-gettypeinfo.php
 * @param connection_id resource
 * @param data_type int[optional]
 * @return resource an ODBC result identifier or
 */
function odbc_gettypeinfo ($connection_id, $data_type = null) {}

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
 * @param dsn string
 * @param user string
 * @param password string
 * @param cursor_type int[optional]
 * @return resource an ODBC connection id or 0 (false) on
 */
function odbc_pconnect ($dsn, $user, $password, $cursor_type = null) {}

/**
 * Prepares a statement for execution
 * @link http://php.net/manual/en/function.odbc-prepare.php
 * @param connection_id resource
 * @param query_string string
 * @return resource an ODBC result identifier if the SQL command was prepared
 */
function odbc_prepare ($connection_id, $query_string) {}

/**
 * Get result data
 * @link http://php.net/manual/en/function.odbc-result.php
 * @param result_id resource
 * @param field mixed
 * @return mixed the string contents of the field, false on error, &null; for
 */
function odbc_result ($result_id, $field) {}

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
 * @param id resource
 * @param function int
 * @param option int
 * @param param int
 * @return bool 
 */
function odbc_setoption ($id, $function, $option, $param) {}

/**
 * Retrieves special columns
 * @link http://php.net/manual/en/function.odbc-specialcolumns.php
 * @param connection_id resource
 * @param type int
 * @param qualifier string
 * @param owner string
 * @param table string
 * @param scope int
 * @param nullable int
 * @return resource an ODBC result identifier or false on
 */
function odbc_specialcolumns ($connection_id, $type, $qualifier, $owner, $table, $scope, $nullable) {}

/**
 * Retrieve statistics about a table
 * @link http://php.net/manual/en/function.odbc-statistics.php
 * @param connection_id resource
 * @param qualifier string
 * @param owner string
 * @param table_name string
 * @param unique int
 * @param accuracy int
 * @return resource an ODBC result identifier or false on failure.
 */
function odbc_statistics ($connection_id, $qualifier, $owner, $table_name, $unique, $accuracy) {}

/**
 * Get the list of table names stored in a specific data source
 * @link http://php.net/manual/en/function.odbc-tables.php
 * @param connection_id resource
 * @param qualifier string[optional]
 * @param owner string[optional]
 * @param name string[optional]
 * @param types string[optional]
 * @return resource an ODBC result identifier containing the information
 */
function odbc_tables ($connection_id, $qualifier = null, $owner = null, $name = null, $types = null) {}

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
 * @param connection_id resource
 * @param qualifier string
 * @param owner string
 * @param name string
 * @return resource 
 */
function odbc_tableprivileges ($connection_id, $qualifier, $owner, $name) {}

/**
 * Retrieves a list of foreign keys
 * @link http://php.net/manual/en/function.odbc-foreignkeys.php
 * @param connection_id resource
 * @param pk_qualifier string
 * @param pk_owner string
 * @param pk_table string
 * @param fk_qualifier string
 * @param fk_owner string
 * @param fk_table string
 * @return resource an ODBC result identifier or false on failure.
 */
function odbc_foreignkeys ($connection_id, $pk_qualifier, $pk_owner, $pk_table, $fk_qualifier, $fk_owner, $fk_table) {}

/**
 * Get the list of procedures stored in a specific data source
 * @link http://php.net/manual/en/function.odbc-procedures.php
 * @param connection_id resource
 * @return resource an ODBC
 */
function odbc_procedures ($connection_id) {}

/**
 * Retrieve information about parameters to procedures
 * @link http://php.net/manual/en/function.odbc-procedurecolumns.php
 * @param connection_id resource
 * @return resource the list of input and output parameters, as well as the
 */
function odbc_procedurecolumns ($connection_id) {}

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
