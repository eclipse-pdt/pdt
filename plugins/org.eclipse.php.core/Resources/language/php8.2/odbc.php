<?php

// Start of odbc v.8.2.6

/**
 * Close all ODBC connections
 * @link http://www.php.net/manual/en/function.odbc-close-all.php
 * @return void 
 */
function odbc_close_all (): void {}

/**
 * Handling of binary column data
 * @link http://www.php.net/manual/en/function.odbc-binmode.php
 * @param resource $statement <p>
 * The result identifier.
 * </p>
 * <p>
 * If statement is 0, the
 * settings apply as default for new results.
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
 * Handling of binary long
 * columns is also affected by odbc_longreadlen.
 * </p>
 * @return bool true on success or false on failure
 */
function odbc_binmode ($statement, int $mode): bool {}

/**
 * Handling of LONG columns
 * @link http://www.php.net/manual/en/function.odbc-longreadlen.php
 * @param resource $statement The result identifier.
 * @param int $length The number of bytes returned to PHP is controlled by the parameter
 * length. If it is set to 0, long column data is passed through to the
 * client (i.e. printed) when retrieved with odbc_result.
 * @return bool true on success or false on failure
 */
function odbc_longreadlen ($statement, int $length): bool {}

/**
 * Prepares a statement for execution
 * @link http://www.php.net/manual/en/function.odbc-prepare.php
 * @param resource $odbc odbc.connection.id
 * @param string $query The query string statement being prepared.
 * @return mixed an ODBC result identifier if the SQL command was prepared
 * successfully. Returns false on error.
 */
function odbc_prepare ($odbc, string $query) {}

/**
 * Execute a prepared statement
 * @link http://www.php.net/manual/en/function.odbc-execute.php
 * @param resource $statement The result id resource, from odbc_prepare.
 * @param array $params [optional] <p>
 * Parameters in params will be
 * substituted for placeholders in the prepared statement in order.
 * Elements of this array will be converted to strings by calling this
 * function.
 * </p>
 * <p>
 * Any parameters in params which
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
function odbc_execute ($statement, array $params = null): bool {}

/**
 * Get cursorname
 * @link http://www.php.net/manual/en/function.odbc-cursor.php
 * @param resource $statement The result identifier.
 * @return mixed the cursor name, as a string, or false on failure.
 */
function odbc_cursor ($statement): string|false {}

/**
 * Returns information about available DSNs
 * @link http://www.php.net/manual/en/function.odbc-data-source.php
 * @param resource $odbc odbc.connection.id
 * @param int $fetch_type The fetch_type can be one of two constant types:
 * SQL_FETCH_FIRST, SQL_FETCH_NEXT.
 * Use SQL_FETCH_FIRST the first time this function is
 * called, thereafter use the SQL_FETCH_NEXT.
 * @return mixed false on error, an array upon success, and null after fetching
 * the last available DSN.
 */
function odbc_data_source ($odbc, int $fetch_type): array|false {}

/**
 * Directly execute an SQL statement
 * @link http://www.php.net/manual/en/function.odbc-exec.php
 * @param resource $odbc odbc.connection.id
 * @param string $query The SQL statement.
 * @return mixed an ODBC result identifier if the SQL command was executed
 * successfully, or false on error.
 */
function odbc_exec ($odbc, string $query) {}

/**
 * Alias: odbc_exec
 * @link http://www.php.net/manual/en/function.odbc-do.php
 * @param mixed $odbc
 * @param string $query
 */
function odbc_do ($odbc = nullstring , $query) {}

/**
 * Fetch a result row as an object
 * @link http://www.php.net/manual/en/function.odbc-fetch-object.php
 * @param resource $statement The result resource from odbc_exec.
 * @param int $row [optional] Optionally choose which row number to retrieve.
 * @return mixed an object that corresponds to the fetched row, or false if there 
 * are no more rows.
 */
function odbc_fetch_object ($statement, int $row = null): stdClass|false {}

/**
 * Fetch a result row as an associative array
 * @link http://www.php.net/manual/en/function.odbc-fetch-array.php
 * @param resource $statement The result resource from odbc_exec.
 * @param int $row [optional] Optionally choose which row number to retrieve.
 * @return mixed an array that corresponds to the fetched row, or false if there 
 * are no more rows.
 */
function odbc_fetch_array ($statement, int $row = null): array|false {}

/**
 * Fetch one result row into array
 * @link http://www.php.net/manual/en/function.odbc-fetch-into.php
 * @param resource $statement The result resource.
 * @param array $array The result array
 * that can be of any type since it will be converted to type
 * array. The array will contain the column values starting at array
 * index 0.
 * @param int $row [optional] The row number.
 * @return mixed the number of columns in the result;
 * false on error.
 */
function odbc_fetch_into ($statement, array &$array, int $row = null): int|false {}

/**
 * Fetch a row
 * @link http://www.php.net/manual/en/function.odbc-fetch-row.php
 * @param resource $statement The result identifier.
 * @param mixed $row [optional] <p>
 * If row is not specified,
 * odbc_fetch_row will try to fetch the next row in
 * the result set. Calls to odbc_fetch_row with and
 * without row can be mixed.
 * </p>
 * <p>
 * To step through the result more than once, you can call
 * odbc_fetch_row with
 * row 1, and then continue doing
 * odbc_fetch_row without
 * row to review the result. If a driver
 * doesn't support fetching rows by number, the
 * row parameter is ignored.
 * </p>
 * @return bool true if there was a row, false otherwise.
 */
function odbc_fetch_row ($statement, $row = null): bool {}

/**
 * Get result data
 * @link http://www.php.net/manual/en/function.odbc-result.php
 * @param resource $statement The ODBC resource.
 * @param mixed $field The field name being retrieved. It can either be an integer containing
 * the column number of the field you want; or it can be a string
 * containing the name of the field.
 * @return mixed the string contents of the field, false on error, null for
 * NULL data, or true for binary data.
 */
function odbc_result ($statement, $field): ?string|bool|null {}

/**
 * Print result as HTML table
 * @link http://www.php.net/manual/en/function.odbc-result-all.php
 * @param resource $statement The result identifier.
 * @param string $format [optional] Additional overall table formatting.
 * @return mixed the number of rows in the result or false on error.
 * @deprecated 
 */
function odbc_result_all ($statement, string $format = null): int|false {}

/**
 * Free resources associated with a result
 * @link http://www.php.net/manual/en/function.odbc-free-result.php
 * @param resource $statement The result identifier.
 * @return bool Always returns true.
 */
function odbc_free_result ($statement): bool {}

/**
 * Connect to a datasource
 * @link http://www.php.net/manual/en/function.odbc-connect.php
 * @param string $dsn The database source name for the connection. Alternatively, a
 * DSN-less connection string can be used.
 * @param string $user The username.
 * @param string $password The password.
 * @param int $cursor_option [optional] <p>
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
 * @return mixed an ODBC connection, or false on failure.
 */
function odbc_connect (string $dsn, string $user, string $password, int $cursor_option = null) {}

/**
 * Open a persistent database connection
 * @link http://www.php.net/manual/en/function.odbc-pconnect.php
 * @param string $dsn 
 * @param string $user 
 * @param string $password 
 * @param int $cursor_option [optional] 
 * @return mixed an ODBC connection, or false on failure.
 * error.
 */
function odbc_pconnect (string $dsn, string $user, string $password, int $cursor_option = null) {}

/**
 * Close an ODBC connection
 * @link http://www.php.net/manual/en/function.odbc-close.php
 * @param resource $odbc odbc.connection.id
 * @return void 
 */
function odbc_close ($odbc): void {}

/**
 * Number of rows in a result
 * @link http://www.php.net/manual/en/function.odbc-num-rows.php
 * @param resource $statement The result identifier returned by odbc_exec.
 * @return int the number of rows in an ODBC result.
 * This function will return -1 on error.
 */
function odbc_num_rows ($statement): int {}

/**
 * Checks if multiple results are available
 * @link http://www.php.net/manual/en/function.odbc-next-result.php
 * @param resource $statement The result identifier.
 * @return bool true if there are more result sets, false otherwise.
 */
function odbc_next_result ($statement): bool {}

/**
 * Number of columns in a result
 * @link http://www.php.net/manual/en/function.odbc-num-fields.php
 * @param resource $statement The result identifier returned by odbc_exec.
 * @return int the number of fields, or -1 on error.
 */
function odbc_num_fields ($statement): int {}

/**
 * Get the columnname
 * @link http://www.php.net/manual/en/function.odbc-field-name.php
 * @param resource $statement The result identifier.
 * @param int $field The field number. Field numbering starts at 1.
 * @return mixed the field name as a string, or false on error.
 */
function odbc_field_name ($statement, int $field): string|false {}

/**
 * Datatype of a field
 * @link http://www.php.net/manual/en/function.odbc-field-type.php
 * @param resource $statement The result identifier.
 * @param int $field The field number. Field numbering starts at 1.
 * @return mixed the field type as a string, or false on error.
 */
function odbc_field_type ($statement, int $field): string|false {}

/**
 * Get the length (precision) of a field
 * @link http://www.php.net/manual/en/function.odbc-field-len.php
 * @param resource $statement The result identifier.
 * @param int $field The field number. Field numbering starts at 1.
 * @return mixed the field length, or false on error.
 */
function odbc_field_len ($statement, int $field): int|false {}

/**
 * Alias: odbc_field_len
 * @link http://www.php.net/manual/en/function.odbc-field-precision.php
 * @param mixed $statement
 * @param int $field
 */
function odbc_field_precision ($statement = nullint , $field): int|false {}

/**
 * Get the scale of a field
 * @link http://www.php.net/manual/en/function.odbc-field-scale.php
 * @param resource $statement The result identifier.
 * @param int $field The field number. Field numbering starts at 1.
 * @return mixed the field scale as a integer, or false on error.
 */
function odbc_field_scale ($statement, int $field): int|false {}

/**
 * Return column number
 * @link http://www.php.net/manual/en/function.odbc-field-num.php
 * @param resource $statement The result identifier.
 * @param string $field The field name.
 * @return mixed the field number as a integer, or false on error.
 * Field numbering starts at 1.
 */
function odbc_field_num ($statement, string $field): int|false {}

/**
 * Toggle autocommit behaviour
 * @link http://www.php.net/manual/en/function.odbc-autocommit.php
 * @param resource $odbc odbc.connection.id
 * @param bool $enable [optional] If enable is true, auto-commit is enabled, if
 * it is false auto-commit is disabled.
 * @return mixed Without the enable parameter, this function returns
 * auto-commit status for odbc. Non-zero is
 * returned if auto-commit is on, 0 if it is off, or false if an error
 * occurs.
 * <p>
 * If enable is set, this function returns true on
 * success and false on failure.
 * </p>
 */
function odbc_autocommit ($odbc, bool $enable = null): int|bool {}

/**
 * Commit an ODBC transaction
 * @link http://www.php.net/manual/en/function.odbc-commit.php
 * @param resource $odbc odbc.connection.id
 * @return bool true on success or false on failure
 */
function odbc_commit ($odbc): bool {}

/**
 * Rollback a transaction
 * @link http://www.php.net/manual/en/function.odbc-rollback.php
 * @param resource $odbc odbc.connection.id
 * @return bool true on success or false on failure
 */
function odbc_rollback ($odbc): bool {}

/**
 * Get the last error code
 * @link http://www.php.net/manual/en/function.odbc-error.php
 * @param mixed $odbc [optional] odbc.connection.id
 * @return string If odbc is specified, the last state
 * of that connection is returned, else the last state of any connection
 * is returned.
 * <p>
 * This function returns meaningful value only if last odbc query failed
 * (i.e. odbc_exec returned false).
 * </p>
 */
function odbc_error ($odbc = null): string {}

/**
 * Get the last error message
 * @link http://www.php.net/manual/en/function.odbc-errormsg.php
 * @param mixed $odbc [optional] odbc.connection.id
 * @return string If odbc is specified, the last state
 * of that connection is returned, else the last state of any connection
 * is returned.
 * <p>
 * This function returns meaningful value only if last odbc query failed
 * (i.e. odbc_exec returned false).
 * </p>
 */
function odbc_errormsg ($odbc = null): string {}

/**
 * Adjust ODBC settings
 * @link http://www.php.net/manual/en/function.odbc-setoption.php
 * @param resource $odbc Is a connection id or result id on which to change the settings.
 * For SQLSetConnectOption(), this is a connection id.
 * For SQLSetStmtOption(), this is a result id.
 * @param int $which Is the ODBC function to use. The value should be
 * 1 for SQLSetConnectOption() and
 * 2 for SQLSetStmtOption().
 * @param int $option The option to set.
 * @param int $value The value for the given option.
 * @return bool true on success or false on failure
 */
function odbc_setoption ($odbc, int $which, int $option, int $value): bool {}

/**
 * Get the list of table names stored in a specific data source
 * @link http://www.php.net/manual/en/function.odbc-tables.php
 * @param resource $odbc odbc.connection.id
 * @param mixed $catalog [optional] The catalog (aposqualifierapos in ODBC 2 parlance).
 * @param mixed $schema [optional] The schema (aposownerapos in ODBC 2 parlance).
 * odbc.parameter.search
 * @param mixed $table [optional] The name.
 * odbc.parameter.search
 * @param mixed $types [optional] If table_type is not an empty string, it
 * must contain a list of comma-separated values for the types of
 * interest; each value may be enclosed in single quotes (') or
 * unquoted. For example, 'TABLE','VIEW' or TABLE, VIEW. If the
 * data source does not support a specified table type,
 * odbc_tables does not return any results for
 * that type.
 * @return mixed an ODBC result identifier containing the information 
 * or false on failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>TABLE_CAT
 * <br>TABLE_SCHEM
 * <br>TABLE_NAME
 * <br>TABLE_TYPE
 * <br>REMARKS
 * </p>
 * odbc.result.driver-specific
 * </p>
 */
function odbc_tables ($odbc, $catalog = null, $schema = null, $table = null, $types = null) {}

/**
 * Lists the column names in specified tables
 * @link http://www.php.net/manual/en/function.odbc-columns.php
 * @param resource $odbc odbc.connection.id
 * @param mixed $catalog [optional] The catalog (aposqualifierapos in ODBC 2 parlance).
 * @param mixed $schema [optional] The schema (aposownerapos in ODBC 2 parlance).
 * odbc.parameter.search
 * @param mixed $table [optional] The table name.
 * odbc.parameter.search
 * @param mixed $column [optional] The column name.
 * odbc.parameter.search
 * @return mixed an ODBC result identifier or false on failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>TABLE_CAT
 * <br>TABLE_SCHEM
 * <br>TABLE_NAME
 * <br>COLUMN_NAME
 * <br>DATA_TYPE
 * <br>TYPE_NAME
 * <br>COLUMN_SIZE
 * <br>BUFFER_LENGTH
 * <br>DECIMAL_DIGITS
 * <br>NUM_PREC_RADIX
 * <br>NULLABLE
 * <br>REMARKS
 * <br>COLUMN_DEF
 * <br>SQL_DATA_TYPE
 * <br>SQL_DATETIME_SUB
 * <br>CHAR_OCTET_LENGTH
 * <br>ORDINAL_POSITION
 * <br>IS_NULLABLE
 * </p>
 * odbc.result.driver-specific
 * </p>
 */
function odbc_columns ($odbc, $catalog = null, $schema = null, $table = null, $column = null) {}

/**
 * Retrieves information about data types supported by the data source
 * @link http://www.php.net/manual/en/function.odbc-gettypeinfo.php
 * @param resource $odbc odbc.connection.id
 * @param int $data_type [optional] The data type, which can be used to restrict the information to a
 * single data type.
 * @return mixed an ODBC result identifier or
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
function odbc_gettypeinfo ($odbc, int $data_type = null) {}

/**
 * Gets the primary keys for a table
 * @link http://www.php.net/manual/en/function.odbc-primarykeys.php
 * @param resource $odbc odbc.connection.id
 * @param mixed $catalog The catalog (aposqualifierapos in ODBC 2 parlance).
 * @param string $schema The schema (aposownerapos in ODBC 2 parlance).
 * @param string $table 
 * @return mixed an ODBC result identifier or false on failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>TABLE_CAT
 * <br>TABLE_SCHEM
 * <br>TABLE_NAME
 * <br>COLUMN_NAME
 * <br>KEY_SEQ
 * <br>PK_NAME
 * </p>
 * odbc.result.driver-specific
 * </p>
 */
function odbc_primarykeys ($odbc, $catalog, string $schema, string $table) {}

/**
 * Retrieve information about parameters to procedures
 * @link http://www.php.net/manual/en/function.odbc-procedurecolumns.php
 * @param resource $odbc odbc.connection.id
 * @param mixed $catalog [optional] The catalog (aposqualifierapos in ODBC 2 parlance).
 * @param mixed $schema [optional] The schema (aposownerapos in ODBC 2 parlance).
 * odbc.parameter.search
 * @param mixed $procedure [optional] The proc.
 * odbc.parameter.search
 * @param mixed $column [optional] The column.
 * odbc.parameter.search
 * @return mixed the list of input and output parameters, as well as the
 * columns that make up the result set for the specified procedures. 
 * Returns an ODBC result identifier or false on failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>PROCEDURE_CAT
 * <br>PROCEDURE_SCHEM
 * <br>PROCEDURE_NAME
 * <br>COLUMN_NAME
 * <br>COLUMN_TYPE
 * <br>DATA_TYPE
 * <br>TYPE_NAME
 * <br>COLUMN_SIZE
 * <br>BUFFER_LENGTH
 * <br>DECIMAL_DIGITS
 * <br>NUM_PREC_RADIX
 * <br>NULLABLE
 * <br>REMARKS
 * <br>COLUMN_DEF
 * <br>SQL_DATA_TYPE
 * <br>SQL_DATETIME_SUB
 * <br>CHAR_OCTET_LENGTH
 * <br>ORDINAL_POSITION
 * <br>IS_NULLABLE
 * </p>
 * odbc.result.driver-specific
 * </p>
 */
function odbc_procedurecolumns ($odbc, $catalog = null, $schema = null, $procedure = null, $column = null) {}

/**
 * Get the list of procedures stored in a specific data source
 * @link http://www.php.net/manual/en/function.odbc-procedures.php
 * @param resource $odbc odbc.connection.id
 * @param mixed $catalog [optional] The catalog (aposqualifierapos in ODBC 2 parlance).
 * @param mixed $schema [optional] The schema (aposownerapos in ODBC 2 parlance).
 * odbc.parameter.search
 * @param mixed $procedure [optional] The name.
 * odbc.parameter.search
 * @return mixed an ODBC
 * result identifier containing the information or false on failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>PROCEDURE_CAT
 * <br>PROCEDURE_SCHEM
 * <br>PROCEDURE_NAME
 * <br>NUM_INPUT_PARAMS
 * <br>NUM_OUTPUT_PARAMS
 * <br>NUM_RESULT_SETS
 * <br>REMARKS
 * <br>PROCEDURE_TYPE
 * </p>
 * odbc.result.driver-specific
 * </p>
 */
function odbc_procedures ($odbc, $catalog = null, $schema = null, $procedure = null) {}

/**
 * Retrieves a list of foreign keys
 * @link http://www.php.net/manual/en/function.odbc-foreignkeys.php
 * @param resource $odbc odbc.connection.id
 * @param mixed $pk_catalog The catalog (aposqualifierapos in ODBC 2 parlance) of the primary key table.
 * @param string $pk_schema The schema (aposownerapos in ODBC 2 parlance) of the primary key table.
 * @param string $pk_table The primary key table.
 * @param string $fk_catalog The catalog (aposqualifierapos in ODBC 2 parlance) of the foreign key table.
 * @param string $fk_schema The schema (aposownerapos in ODBC 2 parlance) of the foreign key table.
 * @param string $fk_table The foreign key table.
 * @return mixed an ODBC result identifier or false on failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>PKTABLE_CAT
 * <br>PKTABLE_SCHEM
 * <br>PKTABLE_NAME
 * <br>PKCOLUMN_NAME
 * <br>FKTABLE_CAT
 * <br>FKTABLE_SCHEM
 * <br>FKTABLE_NAME
 * <br>FKCOLUMN_NAME
 * <br>KEY_SEQ
 * <br>UPDATE_RULE
 * <br>DELETE_RULE
 * <br>FK_NAME
 * <br>PK_NAME
 * <br>DEFERRABILITY
 * </p>
 * odbc.result.driver-specific
 * </p>
 */
function odbc_foreignkeys ($odbc, $pk_catalog, string $pk_schema, string $pk_table, string $fk_catalog, string $fk_schema, string $fk_table) {}

/**
 * Retrieves special columns
 * @link http://www.php.net/manual/en/function.odbc-specialcolumns.php
 * @param resource $odbc odbc.connection.id
 * @param int $type When the type argument is SQL_BEST_ROWID,
 * odbc_specialcolumns returns the
 * column or columns that uniquely identify each row in the table.
 * When the type argument is SQL_ROWVER,
 * odbc_specialcolumns returns the column or columns in the
 * specified table, if any, that are automatically updated by the data source
 * when any value in the row is updated by any transaction.
 * @param mixed $catalog The catalog (aposqualifierapos in ODBC 2 parlance).
 * @param string $schema The schema (aposownerapos in ODBC 2 parlance).
 * @param string $table The table.
 * @param int $scope The scope, which orders the result set.
 * One of SQL_SCOPE_CURROW, SQL_SCOPE_TRANSACTION
 * or SQL_SCOPE_SESSION.
 * @param int $nullable Determines whether to return special columns that can have a NULL value.
 * One of SQL_NO_NULLS or SQL_NULLABLE .
 * @return mixed an ODBC result identifier or false on
 * failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>SCOPE
 * <br>COLUMN_NAME
 * <br>DATA_TYPE
 * <br>TYPE_NAME
 * <br>COLUMN_SIZE
 * <br>BUFFER_LENGTH
 * <br>DECIMAL_DIGITS
 * <br>PSEUDO_COLUMN
 * </p>
 * odbc.result.driver-specific
 * </p>
 */
function odbc_specialcolumns ($odbc, int $type, $catalog, string $schema, string $table, int $scope, int $nullable) {}

/**
 * Retrieve statistics about a table
 * @link http://www.php.net/manual/en/function.odbc-statistics.php
 * @param resource $odbc odbc.connection.id
 * @param mixed $catalog The catalog (aposqualifierapos in ODBC 2 parlance).
 * @param string $schema The schema (aposownerapos in ODBC 2 parlance).
 * @param string $table The table name.
 * @param int $unique The type of the index.
 * One of SQL_INDEX_UNIQUE or SQL_INDEX_ALL.
 * @param int $accuracy One of SQL_ENSURE or SQL_QUICK.
 * The latter requests that the driver retrieve the CARDINALITY and
 * PAGES only if they are readily available from the server.
 * @return mixed an ODBC result identifier or false on failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>TABLE_CAT
 * <br>TABLE_SCHEM
 * <br>TABLE_NAME
 * <br>NON_UNIQUE
 * <br>INDEX_QUALIFIER
 * <br>INDEX_NAME
 * <br>TYPE
 * <br>ORDINAL_POSITION
 * <br>COLUMN_NAME
 * <br>ASC_OR_DESC
 * <br>CARDINALITY
 * <br>PAGES
 * <br>FILTER_CONDITION
 * </p>
 * odbc.result.driver-specific
 * </p>
 */
function odbc_statistics ($odbc, $catalog, string $schema, string $table, int $unique, int $accuracy) {}

/**
 * Lists tables and the privileges associated with each table
 * @link http://www.php.net/manual/en/function.odbc-tableprivileges.php
 * @param resource $odbc odbc.connection.id
 * @param mixed $catalog The catalog (aposqualifierapos in ODBC 2 parlance).
 * @param string $schema The schema (aposownerapos in ODBC 2 parlance).
 * odbc.parameter.search
 * @param string $table The name.
 * odbc.parameter.search
 * @return mixed An ODBC result identifier or false on failure.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>TABLE_CAT
 * <br>TABLE_SCHEM
 * <br>TABLE_NAME
 * <br>GRANTOR
 * <br>GRANTEE
 * <br>PRIVILEGE
 * <br>IS_GRANTABLE
 * </p>
 * odbc.result.driver-specific
 * </p>
 */
function odbc_tableprivileges ($odbc, $catalog, string $schema, string $table) {}

/**
 * Lists columns and associated privileges for the given table
 * @link http://www.php.net/manual/en/function.odbc-columnprivileges.php
 * @param resource $odbc odbc.connection.id
 * @param mixed $catalog The catalog (aposqualifierapos in ODBC 2 parlance).
 * @param string $schema The schema (aposownerapos in ODBC 2 parlance).
 * odbc.parameter.search
 * @param string $table The table name.
 * odbc.parameter.search
 * @param string $column The column name.
 * odbc.parameter.search
 * @return mixed an ODBC result identifier or false on failure.
 * This result identifier can be used to fetch a list of columns and
 * associated privileges.
 * <p>
 * The result set has the following columns:
 * <p>
 * <br>TABLE_CAT
 * <br>TABLE_SCHEM
 * <br>TABLE_NAME
 * <br>COLUMN_NAME
 * <br>GRANTOR
 * <br>GRANTEE
 * <br>PRIVILEGE
 * <br>IS_GRANTABLE
 * </p>
 * odbc.result.driver-specific
 * </p>
 */
function odbc_columnprivileges ($odbc, $catalog, string $schema, string $table, string $column) {}

/**
 * Determines if an ODBC connection string value is quoted
 * @link http://www.php.net/manual/en/function.odbc-connection-string-is-quoted.php
 * @param string $str The string to check for quoting.
 * @return bool true if quoted properly, false if not.
 */
function odbc_connection_string_is_quoted (string $str): bool {}

/**
 * Determines if an ODBC connection string value should be quoted
 * @link http://www.php.net/manual/en/function.odbc-connection-string-should-quote.php
 * @param string $str The string to check for.
 * @return bool true if the string should be quoted; false otherwise.
 */
function odbc_connection_string_should_quote (string $str): bool {}

/**
 * Quotes an ODBC connection string value
 * @link http://www.php.net/manual/en/function.odbc-connection-string-quote.php
 * @param string $str The unquoted string.
 * @return string A quoted string, surrounded by curly braces, and properly escaped.
 */
function odbc_connection_string_quote (string $str): string {}


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

// End of odbc v.8.2.6
