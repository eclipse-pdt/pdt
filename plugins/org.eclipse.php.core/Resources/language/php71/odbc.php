<?php

// Start of odbc v.7.1.1

/**
 * Toggle autocommit behaviour
 * @link http://www.php.net/manual/en/function.odbc-autocommit.php
 * @param resource $connection_id odbc.connection.id
 * @param bool $OnOff [optional] If OnOff is true, auto-commit is enabled, if
 * it is false auto-commit is disabled.
 * @return mixed Without the OnOff parameter, this function returns
 * auto-commit status for connection_id. Non-zero is
 * returned if auto-commit is on, 0 if it is off, or false if an error
 * occurs.
 * <p>
 * If OnOff is set, this function returns true on
 * success and false on failure.
 * </p>
 */
function odbc_autocommit ($connection_id, bool $OnOff = null) {}

/**
 * Handling of binary column data
 * @link http://www.php.net/manual/en/function.odbc-binmode.php
 * @param resource $result_id <p>
 * The result identifier.
 * </p>
 * <p>
 * If result_id is 0, the
 * settings apply as default for new results.
 * Default for longreadlen is 4096 and
 * mode defaults to
 * ODBC_BINMODE_RETURN. Handling of binary long
 * columns is also affected by odbc_longreadlen.
 * </p>
 * @param int $mode <p>
 * Possible values for mode are:
 * <p>
 * <br>
 * ODBC_BINMODE_PASSTHRU: Passthru BINARY data
 * <br>
 * ODBC_BINMODE_RETURN: Return as is
 * <br>
 * ODBC_BINMODE_CONVERT: Convert to char and return
 * </p>
 * </p>
 * @return bool true on success or false on failure
 */
function odbc_binmode ($result_id, int $mode) {}

/**
 * Close an ODBC connection
 * @link http://www.php.net/manual/en/function.odbc-close.php
 * @param resource $connection_id odbc.connection.id
 * @return void 
 */
function odbc_close ($connection_id) {}

/**
 * Close all ODBC connections
 * @link http://www.php.net/manual/en/function.odbc-close-all.php
 * @return void 
 */
function odbc_close_all () {}

/**
 * Lists the column names in specified tables
 * @link http://www.php.net/manual/en/function.odbc-columns.php
 * @param resource $connection_id odbc.connection.id
 * @param string $qualifier [optional] The qualifier.
 * @param string $schema [optional] The owner.
 * @param string $table_name [optional] The table name.
 * @param string $column_name [optional] The column name.
 * @return resource an ODBC result identifier or false on failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>TABLE_QUALIFIER
 * <br>TABLE_SCHEM
 * <br>TABLE_NAME
 * <br>COLUMN_NAME
 * <br>DATA_TYPE
 * <br>TYPE_NAME
 * <br>PRECISION
 * <br>LENGTH
 * <br>SCALE
 * <br>RADIX
 * <br>NULLABLE
 * <br>REMARKS
 * </p>
 * </p>
 * <p>
 * The result set is ordered by TABLE_QUALIFIER, TABLE_SCHEM and
 * TABLE_NAME.
 * </p>
 */
function odbc_columns ($connection_id, string $qualifier = null, string $schema = null, string $table_name = null, string $column_name = null) {}

/**
 * Commit an ODBC transaction
 * @link http://www.php.net/manual/en/function.odbc-commit.php
 * @param resource $connection_id odbc.connection.id
 * @return bool true on success or false on failure
 */
function odbc_commit ($connection_id) {}

/**
 * Connect to a datasource
 * @link http://www.php.net/manual/en/function.odbc-connect.php
 * @param string $dsn The database source name for the connection. Alternatively, a
 * DSN-less connection string can be used.
 * @param string $user The username.
 * @param string $password The password.
 * @param int $cursor_type [optional] <p>
 * This sets the type of cursor to be used
 * for this connection. This parameter is not normally needed, but
 * can be useful for working around problems with some ODBC drivers.
 * </p>
 * The following constants are defined for cursortype:
 * <p>
 * <p>
 * <br>
 * SQL_CUR_USE_IF_NEEDED
 * <br>
 * SQL_CUR_USE_ODBC
 * <br>
 * SQL_CUR_USE_DRIVER
 * </p>
 * </p>
 * @return resource an ODBC connection or (false) on error.
 */
function odbc_connect (string $dsn, string $user, string $password, int $cursor_type = null) {}

/**
 * Get cursorname
 * @link http://www.php.net/manual/en/function.odbc-cursor.php
 * @param resource $result_id The result identifier.
 * @return string the cursor name, as a string.
 */
function odbc_cursor ($result_id) {}

/**
 * Returns information about a current connection
 * @link http://www.php.net/manual/en/function.odbc-data-source.php
 * @param resource $connection_id odbc.connection.id
 * @param int $fetch_type The fetch_type can be one of two constant types:
 * SQL_FETCH_FIRST, SQL_FETCH_NEXT.
 * Use SQL_FETCH_FIRST the first time this function is
 * called, thereafter use the SQL_FETCH_NEXT.
 * @return array false on error, and an array upon success.
 */
function odbc_data_source ($connection_id, int $fetch_type) {}

/**
 * Execute a prepared statement
 * @link http://www.php.net/manual/en/function.odbc-execute.php
 * @param resource $result_id The result id resource, from odbc_prepare.
 * @param array $parameters_array [optional] <p>
 * Parameters in parameter_array will be
 * substituted for placeholders in the prepared statement in order.
 * Elements of this array will be converted to strings by calling this
 * function.
 * </p>
 * <p>
 * Any parameters in parameter_array which
 * start and end with single quotes will be taken as the name of a
 * file to read and send to the database server as the data for the
 * appropriate placeholder.
 * </p>
 * If you wish to store a string which actually begins and ends with
 * single quotes, you must add a space or other non-single-quote character
 * to the beginning or end of the parameter, which will prevent the
 * parameter from being taken as a file name. If this is not an option,
 * then you must use another mechanism to store the string, such as
 * executing the query directly with odbc_exec).
 * @return bool true on success or false on failure
 */
function odbc_execute ($result_id, array $parameters_array = null) {}

/**
 * Get the last error code
 * @link http://www.php.net/manual/en/function.odbc-error.php
 * @param resource $connection_id [optional] odbc.connection.id
 * @return string If connection_id is specified, the last state
 * of that connection is returned, else the last state of any connection
 * is returned.
 * <p>
 * This function returns meaningful value only if last odbc query failed
 * (i.e. odbc_exec returned false).
 * </p>
 */
function odbc_error ($connection_id = null) {}

/**
 * Get the last error message
 * @link http://www.php.net/manual/en/function.odbc-errormsg.php
 * @param resource $connection_id [optional] odbc.connection.id
 * @return string If connection_id is specified, the last state
 * of that connection is returned, else the last state of any connection
 * is returned.
 * <p>
 * This function returns meaningful value only if last odbc query failed
 * (i.e. odbc_exec returned false).
 * </p>
 */
function odbc_errormsg ($connection_id = null) {}

/**
 * Prepare and execute an SQL statement
 * @link http://www.php.net/manual/en/function.odbc-exec.php
 * @param resource $connection_id odbc.connection.id
 * @param string $query_string The SQL statement.
 * @param int $flags [optional] This parameter is currently not used.
 * @return resource an ODBC result identifier if the SQL command was executed
 * successfully, or false on error.
 */
function odbc_exec ($connection_id, string $query_string, int $flags = null) {}

/**
 * Fetch a result row as an associative array
 * @link http://www.php.net/manual/en/function.odbc-fetch-array.php
 * @param resource $result The result resource from odbc_exec.
 * @param int $rownumber [optional] Optionally choose which row number to retrieve.
 * @return array an array that corresponds to the fetched row, or false if there 
 * are no more rows.
 */
function odbc_fetch_array ($result, int $rownumber = null) {}

/**
 * Fetch a result row as an object
 * @link http://www.php.net/manual/en/function.odbc-fetch-object.php
 * @param resource $result The result resource from odbc_exec.
 * @param int $rownumber [optional] Optionally choose which row number to retrieve.
 * @return object an object that corresponds to the fetched row, or false if there 
 * are no more rows.
 */
function odbc_fetch_object ($result, int $rownumber = null) {}

/**
 * Fetch a row
 * @link http://www.php.net/manual/en/function.odbc-fetch-row.php
 * @param resource $result_id The result identifier.
 * @param int $row_number [optional] <p>
 * If row_number is not specified,
 * odbc_fetch_row will try to fetch the next row in
 * the result set. Calls to odbc_fetch_row with and
 * without row_number can be mixed.
 * </p>
 * <p>
 * To step through the result more than once, you can call
 * odbc_fetch_row with
 * row_number 1, and then continue doing
 * odbc_fetch_row without
 * row_number to review the result. If a driver
 * doesn't support fetching rows by number, the
 * row_number parameter is ignored.
 * </p>
 * @return bool true if there was a row, false otherwise.
 */
function odbc_fetch_row ($result_id, int $row_number = null) {}

/**
 * Fetch one result row into array
 * @link http://www.php.net/manual/en/function.odbc-fetch-into.php
 * @param resource $result_id The result resource.
 * @param array $result_array The result array
 * that can be of any type since it will be converted to type
 * array. The array will contain the column values starting at array
 * index 0.
 * @param int $rownumber [optional] The row number.
 * @return int the number of columns in the result;
 * false on error.
 */
function odbc_fetch_into ($result_id, array &$result_array, int $rownumber = null) {}

/**
 * Get the length (precision) of a field
 * @link http://www.php.net/manual/en/function.odbc-field-len.php
 * @param resource $result_id The result identifier.
 * @param int $field_number The field number. Field numbering starts at 1.
 * @return int the field length, or false on error.
 */
function odbc_field_len ($result_id, int $field_number) {}

/**
 * Get the scale of a field
 * @link http://www.php.net/manual/en/function.odbc-field-scale.php
 * @param resource $result_id The result identifier.
 * @param int $field_number The field number. Field numbering starts at 1.
 * @return int the field scale as a integer, or false on error.
 */
function odbc_field_scale ($result_id, int $field_number) {}

/**
 * Get the columnname
 * @link http://www.php.net/manual/en/function.odbc-field-name.php
 * @param resource $result_id The result identifier.
 * @param int $field_number The field number. Field numbering starts at 1.
 * @return string the field name as a string, or false on error.
 */
function odbc_field_name ($result_id, int $field_number) {}

/**
 * Datatype of a field
 * @link http://www.php.net/manual/en/function.odbc-field-type.php
 * @param resource $result_id The result identifier.
 * @param int $field_number The field number. Field numbering starts at 1.
 * @return string the field type as a string, or false on error.
 */
function odbc_field_type ($result_id, int $field_number) {}

/**
 * Return column number
 * @link http://www.php.net/manual/en/function.odbc-field-num.php
 * @param resource $result_id The result identifier.
 * @param string $field_name The field name.
 * @return int the field number as a integer, or false on error.
 * Field numbering starts at 1.
 */
function odbc_field_num ($result_id, string $field_name) {}

/**
 * Free resources associated with a result
 * @link http://www.php.net/manual/en/function.odbc-free-result.php
 * @param resource $result_id The result identifier.
 * @return bool Always returns true.
 */
function odbc_free_result ($result_id) {}

/**
 * Retrieves information about data types supported by the data source
 * @link http://www.php.net/manual/en/function.odbc-gettypeinfo.php
 * @param resource $connection_id odbc.connection.id
 * @param int $data_type [optional] The data type, which can be used to restrict the information to a
 * single data type.
 * @return resource an ODBC result identifier or
 * false on failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>TYPE_NAME
 * <br>DATA_TYPE
 * <br>PRECISION
 * <br>LITERAL_PREFIX
 * <br>LITERAL_SUFFIX
 * <br>CREATE_PARAMS
 * <br>NULLABLE
 * <br>CASE_SENSITIVE
 * <br>SEARCHABLE
 * <br>UNSIGNED_ATTRIBUTE
 * <br>MONEY
 * <br>AUTO_INCREMENT
 * <br>LOCAL_TYPE_NAME
 * <br>MINIMUM_SCALE
 * <br>MAXIMUM_SCALE
 * </p>
 * </p>
 * <p>
 * The result set is ordered by DATA_TYPE and TYPE_NAME.
 * </p>
 */
function odbc_gettypeinfo ($connection_id, int $data_type = null) {}

/**
 * Handling of LONG columns
 * @link http://www.php.net/manual/en/function.odbc-longreadlen.php
 * @param resource $result_id The result identifier.
 * @param int $length The number of bytes returned to PHP is controlled by the parameter
 * length. If it is set to 0, Long column data is passed through to the
 * client.
 * @return bool true on success or false on failure
 */
function odbc_longreadlen ($result_id, int $length) {}

/**
 * Checks if multiple results are available
 * @link http://www.php.net/manual/en/function.odbc-next-result.php
 * @param resource $result_id The result identifier.
 * @return bool true if there are more result sets, false otherwise.
 */
function odbc_next_result ($result_id) {}

/**
 * Number of columns in a result
 * @link http://www.php.net/manual/en/function.odbc-num-fields.php
 * @param resource $result_id The result identifier returned by odbc_exec.
 * @return int the number of fields, or -1 on error.
 */
function odbc_num_fields ($result_id) {}

/**
 * Number of rows in a result
 * @link http://www.php.net/manual/en/function.odbc-num-rows.php
 * @param resource $result_id The result identifier returned by odbc_exec.
 * @return int the number of rows in an ODBC result.
 * This function will return -1 on error.
 */
function odbc_num_rows ($result_id) {}

/**
 * Open a persistent database connection
 * @link http://www.php.net/manual/en/function.odbc-pconnect.php
 * @param string $dsn 
 * @param string $user 
 * @param string $password 
 * @param int $cursor_type [optional] 
 * @return resource an ODBC connection id or 0 (false) on
 * error.
 */
function odbc_pconnect (string $dsn, string $user, string $password, int $cursor_type = null) {}

/**
 * Prepares a statement for execution
 * @link http://www.php.net/manual/en/function.odbc-prepare.php
 * @param resource $connection_id odbc.connection.id
 * @param string $query_string The query string statement being prepared.
 * @return resource an ODBC result identifier if the SQL command was prepared
 * successfully. Returns false on error.
 */
function odbc_prepare ($connection_id, string $query_string) {}

/**
 * Get result data
 * @link http://www.php.net/manual/en/function.odbc-result.php
 * @param resource $result_id The ODBC resource.
 * @param mixed $field The field name being retrieved. It can either be an integer containing
 * the column number of the field you want; or it can be a string
 * containing the name of the field.
 * @return mixed the string contents of the field, false on error, null for
 * NULL data, or true for binary data.
 */
function odbc_result ($result_id, $field) {}

/**
 * Print result as HTML table
 * @link http://www.php.net/manual/en/function.odbc-result-all.php
 * @param resource $result_id The result identifier.
 * @param string $format [optional] Additional overall table formatting.
 * @return int the number of rows in the result or false on error.
 */
function odbc_result_all ($result_id, string $format = null) {}

/**
 * Rollback a transaction
 * @link http://www.php.net/manual/en/function.odbc-rollback.php
 * @param resource $connection_id odbc.connection.id
 * @return bool true on success or false on failure
 */
function odbc_rollback ($connection_id) {}

/**
 * Adjust ODBC settings
 * @link http://www.php.net/manual/en/function.odbc-setoption.php
 * @param resource $id Is a connection id or result id on which to change the settings.
 * For SQLSetConnectOption(), this is a connection id.
 * For SQLSetStmtOption(), this is a result id.
 * @param int $function Is the ODBC function to use. The value should be
 * 1 for SQLSetConnectOption() and
 * 2 for SQLSetStmtOption().
 * @param int $option The option to set.
 * @param int $param The value for the given option.
 * @return bool true on success or false on failure
 */
function odbc_setoption ($id, int $function, int $option, int $param) {}

/**
 * Retrieves special columns
 * @link http://www.php.net/manual/en/function.odbc-specialcolumns.php
 * @param resource $connection_id odbc.connection.id
 * @param int $type When the type argument is SQL_BEST_ROWID,
 * odbc_specialcolumns returns the
 * column or columns that uniquely identify each row in the table.
 * When the type argument is SQL_ROWVER,
 * odbc_specialcolumns returns the column or columns in the
 * specified table, if any, that are automatically updated by the data source
 * when any value in the row is updated by any transaction.
 * @param string $qualifier The qualifier.
 * @param string $owner The owner.
 * @param string $table The table.
 * @param int $scope The scope, which orders the result set.
 * @param int $nullable The nullable option.
 * @return resource an ODBC result identifier or false on
 * failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>SCOPE
 * <br>COLUMN_NAME
 * <br>DATA_TYPE
 * <br>TYPE_NAME
 * <br>PRECISION
 * <br>LENGTH
 * <br>SCALE
 * <br>PSEUDO_COLUMN
 * </p>
 * </p>
 */
function odbc_specialcolumns ($connection_id, int $type, string $qualifier, string $owner, string $table, int $scope, int $nullable) {}

/**
 * Retrieve statistics about a table
 * @link http://www.php.net/manual/en/function.odbc-statistics.php
 * @param resource $connection_id odbc.connection.id
 * @param string $qualifier The qualifier.
 * @param string $owner The owner.
 * @param string $table_name The table name.
 * @param int $unique The unique attribute.
 * @param int $accuracy The accuracy.
 * @return resource an ODBC result identifier or false on failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>TABLE_QUALIFIER
 * <br>TABLE_OWNER
 * <br>TABLE_NAME
 * <br>NON_UNIQUE
 * <br>INDEX_QUALIFIER
 * <br>INDEX_NAME
 * <br>TYPE
 * <br>SEQ_IN_INDEX
 * <br>COLUMN_NAME
 * <br>COLLATION
 * <br>CARDINALITY
 * <br>PAGES
 * <br>FILTER_CONDITION
 * </p>
 * </p>
 */
function odbc_statistics ($connection_id, string $qualifier, string $owner, string $table_name, int $unique, int $accuracy) {}

/**
 * Get the list of table names stored in a specific data source
 * @link http://www.php.net/manual/en/function.odbc-tables.php
 * @param resource $connection_id odbc.connection.id
 * @param string $qualifier [optional] The qualifier.
 * @param string $owner [optional] The owner. Accepts search patterns ('%' to match zero or more
 * characters and '_' to match a single character).
 * @param string $name [optional] The name. Accepts search patterns ('%' to match zero or more
 * characters and '_' to match a single character).
 * @param string $types [optional] If table_type is not an empty string, it
 * must contain a list of comma-separated values for the types of
 * interest; each value may be enclosed in single quotes (') or
 * unquoted. For example, "'TABLE','VIEW'" or "TABLE, VIEW". If the
 * data source does not support a specified table type,
 * odbc_tables does not return any results for
 * that type.
 * @return resource an ODBC result identifier containing the information 
 * or false on failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>TABLE_QUALIFIER
 * <br>TABLE_OWNER
 * <br>TABLE_NAME
 * <br>TABLE_TYPE
 * <br>REMARKS
 * </p>
 * </p>
 */
function odbc_tables ($connection_id, string $qualifier = null, string $owner = null, string $name = null, string $types = null) {}

/**
 * Gets the primary keys for a table
 * @link http://www.php.net/manual/en/function.odbc-primarykeys.php
 * @param resource $connection_id odbc.connection.id
 * @param string $qualifier 
 * @param string $owner 
 * @param string $table 
 * @return resource an ODBC result identifier or false on failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>TABLE_QUALIFIER
 * <br>TABLE_OWNER
 * <br>TABLE_NAME
 * <br>COLUMN_NAME
 * <br>KEY_SEQ
 * <br>PK_NAME
 * </p>
 * </p>
 */
function odbc_primarykeys ($connection_id, string $qualifier, string $owner, string $table) {}

/**
 * Lists columns and associated privileges for the given table
 * @link http://www.php.net/manual/en/function.odbc-columnprivileges.php
 * @param resource $connection_id odbc.connection.id
 * @param string $qualifier The qualifier.
 * @param string $owner The owner.
 * @param string $table_name The table name.
 * @param string $column_name The column_name argument accepts search
 * patterns ('%' to match zero or more characters and '_' to match a
 * single character).
 * @return resource an ODBC result identifier or false on failure.
 * This result identifier can be used to fetch a list of columns and
 * associated privileges.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>TABLE_QUALIFIER
 * <br>TABLE_OWNER
 * <br>TABLE_NAME
 * <br>GRANTOR
 * <br>GRANTEE
 * <br>PRIVILEGE
 * <br>IS_GRANTABLE
 * </p>
 * </p>
 * <p>
 * The result set is ordered by TABLE_QUALIFIER, TABLE_OWNER and
 * TABLE_NAME.
 * </p>
 */
function odbc_columnprivileges ($connection_id, string $qualifier, string $owner, string $table_name, string $column_name) {}

/**
 * Lists tables and the privileges associated with each table
 * @link http://www.php.net/manual/en/function.odbc-tableprivileges.php
 * @param resource $connection_id odbc.connection.id
 * @param string $qualifier The qualifier.
 * @param string $owner The owner. Accepts the following search patterns:
 * ('%' to match zero or more characters and '_' to match a single character)
 * @param string $name The name. Accepts the following search patterns:
 * ('%' to match zero or more characters and '_' to match a single character)
 * @return resource An ODBC result identifier or false on failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>TABLE_QUALIFIER
 * <br>TABLE_OWNER
 * <br>TABLE_NAME
 * <br>GRANTOR
 * <br>GRANTEE
 * <br>PRIVILEGE
 * <br>IS_GRANTABLE
 * </p>
 * </p>
 */
function odbc_tableprivileges ($connection_id, string $qualifier, string $owner, string $name) {}

/**
 * Retrieves a list of foreign keys
 * @link http://www.php.net/manual/en/function.odbc-foreignkeys.php
 * @param resource $connection_id odbc.connection.id
 * @param string $pk_qualifier The primary key qualifier.
 * @param string $pk_owner The primary key owner.
 * @param string $pk_table The primary key table.
 * @param string $fk_qualifier The foreign key qualifier.
 * @param string $fk_owner The foreign key owner.
 * @param string $fk_table The foreign key table.
 * @return resource an ODBC result identifier or false on failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>PKTABLE_QUALIFIER
 * <br>PKTABLE_OWNER
 * <br>PKTABLE_NAME
 * <br>PKCOLUMN_NAME
 * <br>FKTABLE_QUALIFIER
 * <br>FKTABLE_OWNER
 * <br>FKTABLE_NAME
 * <br>FKCOLUMN_NAME
 * <br>KEY_SEQ
 * <br>UPDATE_RULE
 * <br>DELETE_RULE
 * <br>FK_NAME
 * <br>PK_NAME
 * </p>
 * </p>
 */
function odbc_foreignkeys ($connection_id, string $pk_qualifier, string $pk_owner, string $pk_table, string $fk_qualifier, string $fk_owner, string $fk_table) {}

/**
 * Get the list of procedures stored in a specific data source
 * @link http://www.php.net/manual/en/function.odbc-procedures.php
 * @param resource $connection_id odbc.connection.id
 * @return resource an ODBC
 * result identifier containing the information or false on failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>PROCEDURE_QUALIFIER
 * <br>PROCEDURE_OWNER
 * <br>PROCEDURE_NAME
 * <br>NUM_INPUT_PARAMS
 * <br>NUM_OUTPUT_PARAMS
 * <br>NUM_RESULT_SETS
 * <br>REMARKS
 * <br>PROCEDURE_TYPE
 * </p>
 * </p>
 */
function odbc_procedures ($connection_id) {}

/**
 * Retrieve information about parameters to procedures
 * @link http://www.php.net/manual/en/function.odbc-procedurecolumns.php
 * @param resource $connection_id odbc.connection.id
 * @return resource the list of input and output parameters, as well as the
 * columns that make up the result set for the specified procedures. 
 * Returns an ODBC result identifier or false on failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>PROCEDURE_QUALIFIER
 * <br>PROCEDURE_OWNER
 * <br>PROCEDURE_NAME
 * <br>COLUMN_NAME
 * <br>COLUMN_TYPE
 * <br>DATA_TYPE
 * <br>TYPE_NAME
 * <br>PRECISION
 * <br>LENGTH
 * <br>SCALE
 * <br>RADIX
 * <br>NULLABLE
 * <br>REMARKS
 * </p>
 * </p>
 */
function odbc_procedurecolumns ($connection_id) {}

/**
 * Alias: odbc_exec
 * @link http://www.php.net/manual/en/function.odbc-do.php
 * @param $connection_id
 * @param $query
 * @param $flags [optional]
 */
function odbc_do ($connection_id, $query, $flags = null) {}

/**
 * Alias: odbc_field_len
 * @link http://www.php.net/manual/en/function.odbc-field-precision.php
 * @param $result_id
 * @param $field_number
 */
function odbc_field_precision ($result_id, $field_number) {}


/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('ODBC_TYPE', "unixODBC");

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('ODBC_BINMODE_PASSTHRU', 0);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('ODBC_BINMODE_RETURN', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('ODBC_BINMODE_CONVERT', 2);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_ODBC_CURSORS', 110);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_CUR_USE_DRIVER', 2);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_CUR_USE_IF_NEEDED', 0);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_CUR_USE_ODBC', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_CONCURRENCY', 7);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_CONCUR_READ_ONLY', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_CONCUR_LOCK', 2);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_CONCUR_ROWVER', 3);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_CONCUR_VALUES', 4);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_CURSOR_TYPE', 6);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_CURSOR_FORWARD_ONLY', 0);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_CURSOR_KEYSET_DRIVEN', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_CURSOR_DYNAMIC', 2);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_CURSOR_STATIC', 3);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_KEYSET_SIZE', 8);
define ('SQL_FETCH_FIRST', 2);
define ('SQL_FETCH_NEXT', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_CHAR', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_VARCHAR', 12);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_LONGVARCHAR', -1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_DECIMAL', 3);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_NUMERIC', 2);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_BIT', -7);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_TINYINT', -6);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_SMALLINT', 5);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_INTEGER', 4);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_BIGINT', -5);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_REAL', 7);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_FLOAT', 6);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_DOUBLE', 8);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_BINARY', -2);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_VARBINARY', -3);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_LONGVARBINARY', -4);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_DATE', 9);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_TIME', 10);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_TIMESTAMP', 11);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_TYPE_DATE', 91);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_TYPE_TIME', 92);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_TYPE_TIMESTAMP', 93);
define ('SQL_WCHAR', -8);
define ('SQL_WVARCHAR', -9);
define ('SQL_WLONGVARCHAR', -10);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_BEST_ROWID', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_ROWVER', 2);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_SCOPE_CURROW', 0);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_SCOPE_TRANSACTION', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_SCOPE_SESSION', 2);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_NO_NULLS', 0);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_NULLABLE', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_INDEX_UNIQUE', 0);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_INDEX_ALL', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_ENSURE', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 */
define ('SQL_QUICK', 0);

// End of odbc v.7.1.1
