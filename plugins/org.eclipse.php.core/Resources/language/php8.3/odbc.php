<?php

// Start of odbc v.8.2.6

/**
 * Close all ODBC connections
 * @link http://www.php.net/manual/en/function.odbc-close-all.php
 * @return void No value is returned.
 */
function odbc_close_all (): void {}

/**
 * Handling of binary column data
 * @link http://www.php.net/manual/en/function.odbc-binmode.php
 * @param resource $statement 
 * @param int $mode 
 * @return bool Returns true on success or false on failure.
 */
function odbc_binmode ($statement, int $mode): bool {}

/**
 * Handling of LONG columns
 * @link http://www.php.net/manual/en/function.odbc-longreadlen.php
 * @param resource $statement 
 * @param int $length 
 * @return bool Returns true on success or false on failure.
 */
function odbc_longreadlen ($statement, int $length): bool {}

/**
 * Prepares a statement for execution
 * @link http://www.php.net/manual/en/function.odbc-prepare.php
 * @param resource $odbc 
 * @param string $query 
 * @return resource|false Returns an ODBC result identifier if the SQL command was prepared
 * successfully. Returns false on error.
 */
function odbc_prepare ($odbc, string $query) {}

/**
 * Execute a prepared statement
 * @link http://www.php.net/manual/en/function.odbc-execute.php
 * @param resource $statement 
 * @param array $params [optional] 
 * @return bool Returns true on success or false on failure.
 */
function odbc_execute ($statement, array $params = '[]'): bool {}

/**
 * Get cursorname
 * @link http://www.php.net/manual/en/function.odbc-cursor.php
 * @param resource $statement 
 * @return string|false Returns the cursor name, as a string, or false on failure.
 */
function odbc_cursor ($statement): string|false {}

/**
 * Returns information about available DSNs
 * @link http://www.php.net/manual/en/function.odbc-data-source.php
 * @param resource $odbc 
 * @param int $fetch_type 
 * @return array|false Returns false on error, an array upon success, and null after fetching
 * the last available DSN.
 */
function odbc_data_source ($odbc, int $fetch_type): array|false {}

/**
 * Directly execute an SQL statement
 * @link http://www.php.net/manual/en/function.odbc-exec.php
 * @param resource $odbc 
 * @param string $query 
 * @return resource|false Returns an ODBC result identifier if the SQL command was executed
 * successfully, or false on error.
 */
function odbc_exec ($odbc, string $query) {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 * @param string $query
 */
function odbc_do ($odbc = null, string $query) {}

/**
 * Fetch a result row as an object
 * @link http://www.php.net/manual/en/function.odbc-fetch-object.php
 * @param resource $statement 
 * @param int $row [optional] 
 * @return stdClass|false Returns an object that corresponds to the fetched row, or false if there 
 * are no more rows.
 */
function odbc_fetch_object ($statement, int $row = -1): stdClass|false {}

/**
 * Fetch a result row as an associative array
 * @link http://www.php.net/manual/en/function.odbc-fetch-array.php
 * @param resource $statement 
 * @param int $row [optional] 
 * @return array|false Returns an array that corresponds to the fetched row, or false if there 
 * are no more rows.
 */
function odbc_fetch_array ($statement, int $row = -1): array|false {}

/**
 * Fetch one result row into array
 * @link http://www.php.net/manual/en/function.odbc-fetch-into.php
 * @param resource $statement 
 * @param array $array 
 * @param int $row [optional] 
 * @return int|false Returns the number of columns in the result;
 * false on error.
 */
function odbc_fetch_into ($statement, array &$array, int $row = null): int|false {}

/**
 * Fetch a row
 * @link http://www.php.net/manual/en/function.odbc-fetch-row.php
 * @param resource $statement 
 * @param int|null $row [optional] 
 * @return bool Returns true if there was a row, false otherwise.
 */
function odbc_fetch_row ($statement, ?int $row = null): bool {}

/**
 * Get result data
 * @link http://www.php.net/manual/en/function.odbc-result.php
 * @param resource $statement 
 * @param string|int $field 
 * @return string|bool|null Returns the string contents of the field, false on error, null for
 * NULL data, or true for binary data.
 */
function odbc_result ($statement, string|int $field): string|bool|null {}

/**
 * Print result as HTML table
 * @link http://www.php.net/manual/en/function.odbc-result-all.php
 * @param resource $statement 
 * @param string $format [optional] 
 * @return int|false Returns the number of rows in the result or false on error.
 * @deprecated 1
 */
function odbc_result_all ($statement, string $format = '""'): int|false {}

/**
 * Free resources associated with a result
 * @link http://www.php.net/manual/en/function.odbc-free-result.php
 * @param resource $statement 
 * @return bool Always returns true.
 */
function odbc_free_result ($statement): bool {}

/**
 * Connect to a datasource
 * @link http://www.php.net/manual/en/function.odbc-connect.php
 * @param string $dsn 
 * @param string $user 
 * @param string $password 
 * @param int $cursor_option [optional] 
 * @return resource|false Returns an ODBC connection, or false on failure.
 */
function odbc_connect (string $dsn, string $user, string $password, int $cursor_option = SQL_CUR_USE_DRIVER) {}

/**
 * Open a persistent database connection
 * @link http://www.php.net/manual/en/function.odbc-pconnect.php
 * @param string $dsn 
 * @param string $user 
 * @param string $password 
 * @param int $cursor_option [optional] 
 * @return resource|false Returns an ODBC connection, or false on failure.
 * error.
 */
function odbc_pconnect (string $dsn, string $user, string $password, int $cursor_option = SQL_CUR_USE_DRIVER) {}

/**
 * Close an ODBC connection
 * @link http://www.php.net/manual/en/function.odbc-close.php
 * @param resource $odbc 
 * @return void No value is returned.
 */
function odbc_close ($odbc): void {}

/**
 * Number of rows in a result
 * @link http://www.php.net/manual/en/function.odbc-num-rows.php
 * @param resource $statement 
 * @return int Returns the number of rows in an ODBC result.
 * This function will return -1 on error.
 */
function odbc_num_rows ($statement): int {}

/**
 * Checks if multiple results are available
 * @link http://www.php.net/manual/en/function.odbc-next-result.php
 * @param resource $statement 
 * @return bool Returns true if there are more result sets, false otherwise.
 */
function odbc_next_result ($statement): bool {}

/**
 * Number of columns in a result
 * @link http://www.php.net/manual/en/function.odbc-num-fields.php
 * @param resource $statement 
 * @return int Returns the number of fields, or -1 on error.
 */
function odbc_num_fields ($statement): int {}

/**
 * Get the columnname
 * @link http://www.php.net/manual/en/function.odbc-field-name.php
 * @param resource $statement 
 * @param int $field 
 * @return string|false Returns the field name as a string, or false on error.
 */
function odbc_field_name ($statement, int $field): string|false {}

/**
 * Datatype of a field
 * @link http://www.php.net/manual/en/function.odbc-field-type.php
 * @param resource $statement 
 * @param int $field 
 * @return string|false Returns the field type as a string, or false on error.
 */
function odbc_field_type ($statement, int $field): string|false {}

/**
 * Get the length (precision) of a field
 * @link http://www.php.net/manual/en/function.odbc-field-len.php
 * @param resource $statement 
 * @param int $field 
 * @return int|false Returns the field length, or false on error.
 */
function odbc_field_len ($statement, int $field): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param int $field
 */
function odbc_field_precision ($statement = null, int $field): int|false {}

/**
 * Get the scale of a field
 * @link http://www.php.net/manual/en/function.odbc-field-scale.php
 * @param resource $statement 
 * @param int $field 
 * @return int|false Returns the field scale as a integer, or false on error.
 */
function odbc_field_scale ($statement, int $field): int|false {}

/**
 * Return column number
 * @link http://www.php.net/manual/en/function.odbc-field-num.php
 * @param resource $statement 
 * @param string $field 
 * @return int|false Returns the field number as a integer, or false on error.
 * Field numbering starts at 1.
 */
function odbc_field_num ($statement, string $field): int|false {}

/**
 * Toggle autocommit behaviour
 * @link http://www.php.net/manual/en/function.odbc-autocommit.php
 * @param resource $odbc 
 * @param bool $enable [optional] 
 * @return int|bool Without the enable parameter, this function returns
 * auto-commit status for odbc. Non-zero is
 * returned if auto-commit is on, 0 if it is off, or false if an error
 * occurs.
 * <p>If enable is set, this function returns true on
 * success and false on failure.</p>
 */
function odbc_autocommit ($odbc, bool $enable = false): int|bool {}

/**
 * Commit an ODBC transaction
 * @link http://www.php.net/manual/en/function.odbc-commit.php
 * @param resource $odbc 
 * @return bool Returns true on success or false on failure.
 */
function odbc_commit ($odbc): bool {}

/**
 * Rollback a transaction
 * @link http://www.php.net/manual/en/function.odbc-rollback.php
 * @param resource $odbc 
 * @return bool Returns true on success or false on failure.
 */
function odbc_rollback ($odbc): bool {}

/**
 * Get the last error code
 * @link http://www.php.net/manual/en/function.odbc-error.php
 * @param resource|null $odbc [optional] 
 * @return string If odbc is specified, the last state
 * of that connection is returned, else the last state of any connection
 * is returned.
 * <p>This function returns meaningful value only if last odbc query failed
 * (i.e. odbc_exec returned false).</p>
 */
function odbc_error ($odbc = null): string {}

/**
 * Get the last error message
 * @link http://www.php.net/manual/en/function.odbc-errormsg.php
 * @param resource|null $odbc [optional] 
 * @return string If odbc is specified, the last state
 * of that connection is returned, else the last state of any connection
 * is returned.
 * <p>This function returns meaningful value only if last odbc query failed
 * (i.e. odbc_exec returned false).</p>
 */
function odbc_errormsg ($odbc = null): string {}

/**
 * Adjust ODBC settings
 * @link http://www.php.net/manual/en/function.odbc-setoption.php
 * @param resource $odbc 
 * @param int $which 
 * @param int $option 
 * @param int $value 
 * @return bool Returns true on success or false on failure.
 */
function odbc_setoption ($odbc, int $which, int $option, int $value): bool {}

/**
 * Get the list of table names stored in a specific data source
 * @link http://www.php.net/manual/en/function.odbc-tables.php
 * @param resource $odbc 
 * @param string|null $catalog [optional] 
 * @param string|null $schema [optional] 
 * @param string|null $table [optional] 
 * @param string|null $types [optional] 
 * @return resource|false Returns an ODBC result identifier containing the information 
 * or false on failure.
 * <p>The result set has the following columns:
 * <p>
 * <br>TABLE_CAT
 * <br>TABLE_SCHEM
 * <br>TABLE_NAME
 * <br>TABLE_TYPE
 * <br>REMARKS
 * </p>
 * Drivers can report additional columns.</p>
 */
function odbc_tables ($odbc, ?string $catalog = null, ?string $schema = null, ?string $table = null, ?string $types = null) {}

/**
 * Lists the column names in specified tables
 * @link http://www.php.net/manual/en/function.odbc-columns.php
 * @param resource $odbc 
 * @param string|null $catalog [optional] 
 * @param string|null $schema [optional] 
 * @param string|null $table [optional] 
 * @param string|null $column [optional] 
 * @return resource|false Returns an ODBC result identifier or false on failure.
 * <p>The result set has the following columns:
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
 * Drivers can report additional columns.</p>
 */
function odbc_columns ($odbc, ?string $catalog = null, ?string $schema = null, ?string $table = null, ?string $column = null) {}

/**
 * Retrieves information about data types supported by the data source
 * @link http://www.php.net/manual/en/function.odbc-gettypeinfo.php
 * @param resource $odbc 
 * @param int $data_type [optional] 
 * @return resource|false Returns an ODBC result identifier or
 * false on failure.
 * <p>The result set has the following columns:
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
 * </p></p>
 * <p>The result set is ordered by DATA_TYPE and TYPE_NAME.</p>
 */
function odbc_gettypeinfo ($odbc, int $data_type = null) {}

/**
 * Gets the primary keys for a table
 * @link http://www.php.net/manual/en/function.odbc-primarykeys.php
 * @param resource $odbc 
 * @param string|null $catalog 
 * @param string $schema 
 * @param string $table 
 * @return resource|false Returns an ODBC result identifier or false on failure.
 * <p>The result set has the following columns:
 * <p>
 * <br>TABLE_CAT
 * <br>TABLE_SCHEM
 * <br>TABLE_NAME
 * <br>COLUMN_NAME
 * <br>KEY_SEQ
 * <br>PK_NAME
 * </p>
 * Drivers can report additional columns.</p>
 */
function odbc_primarykeys ($odbc, ?string $catalog, string $schema, string $table) {}

/**
 * Retrieve information about parameters to procedures
 * @link http://www.php.net/manual/en/function.odbc-procedurecolumns.php
 * @param resource $odbc 
 * @param string|null $catalog [optional] 
 * @param string|null $schema [optional] 
 * @param string|null $procedure [optional] 
 * @param string|null $column [optional] 
 * @return resource|false Returns the list of input and output parameters, as well as the
 * columns that make up the result set for the specified procedures. 
 * Returns an ODBC result identifier or false on failure.
 * <p>The result set has the following columns:
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
 * Drivers can report additional columns.</p>
 */
function odbc_procedurecolumns ($odbc, ?string $catalog = null, ?string $schema = null, ?string $procedure = null, ?string $column = null) {}

/**
 * Get the list of procedures stored in a specific data source
 * @link http://www.php.net/manual/en/function.odbc-procedures.php
 * @param resource $odbc 
 * @param string|null $catalog [optional] 
 * @param string|null $schema [optional] 
 * @param string|null $procedure [optional] 
 * @return resource|false Returns an ODBC
 * result identifier containing the information or false on failure.
 * <p>The result set has the following columns:
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
 * Drivers can report additional columns.</p>
 */
function odbc_procedures ($odbc, ?string $catalog = null, ?string $schema = null, ?string $procedure = null) {}

/**
 * Retrieves a list of foreign keys
 * @link http://www.php.net/manual/en/function.odbc-foreignkeys.php
 * @param resource $odbc 
 * @param string|null $pk_catalog 
 * @param string $pk_schema 
 * @param string $pk_table 
 * @param string $fk_catalog 
 * @param string $fk_schema 
 * @param string $fk_table 
 * @return resource|false Returns an ODBC result identifier or false on failure.
 * <p>The result set has the following columns:
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
 * Drivers can report additional columns.</p>
 */
function odbc_foreignkeys ($odbc, ?string $pk_catalog, string $pk_schema, string $pk_table, string $fk_catalog, string $fk_schema, string $fk_table) {}

/**
 * Retrieves special columns
 * @link http://www.php.net/manual/en/function.odbc-specialcolumns.php
 * @param resource $odbc 
 * @param int $type 
 * @param string|null $catalog 
 * @param string $schema 
 * @param string $table 
 * @param int $scope 
 * @param int $nullable 
 * @return resource|false Returns an ODBC result identifier or false on
 * failure.
 * <p>The result set has the following columns:
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
 * Drivers can report additional columns.</p>
 */
function odbc_specialcolumns ($odbc, int $type, ?string $catalog, string $schema, string $table, int $scope, int $nullable) {}

/**
 * Retrieve statistics about a table
 * @link http://www.php.net/manual/en/function.odbc-statistics.php
 * @param resource $odbc 
 * @param string|null $catalog 
 * @param string $schema 
 * @param string $table 
 * @param int $unique 
 * @param int $accuracy 
 * @return resource|false Returns an ODBC result identifier or false on failure.
 * <p>The result set has the following columns:
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
 * Drivers can report additional columns.</p>
 */
function odbc_statistics ($odbc, ?string $catalog, string $schema, string $table, int $unique, int $accuracy) {}

/**
 * Lists tables and the privileges associated with each table
 * @link http://www.php.net/manual/en/function.odbc-tableprivileges.php
 * @param resource $odbc 
 * @param string|null $catalog 
 * @param string $schema 
 * @param string $table 
 * @return resource|false An ODBC result identifier or false on failure.
 * <p>The result set has the following columns:
 * <p>
 * <br>TABLE_CAT
 * <br>TABLE_SCHEM
 * <br>TABLE_NAME
 * <br>GRANTOR
 * <br>GRANTEE
 * <br>PRIVILEGE
 * <br>IS_GRANTABLE
 * </p>
 * Drivers can report additional columns.</p>
 */
function odbc_tableprivileges ($odbc, ?string $catalog, string $schema, string $table) {}

/**
 * Lists columns and associated privileges for the given table
 * @link http://www.php.net/manual/en/function.odbc-columnprivileges.php
 * @param resource $odbc 
 * @param string|null $catalog 
 * @param string $schema 
 * @param string $table 
 * @param string $column 
 * @return resource|false Returns an ODBC result identifier or false on failure.
 * This result identifier can be used to fetch a list of columns and
 * associated privileges.
 * <p>The result set has the following columns:
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
 * Drivers can report additional columns.</p>
 */
function odbc_columnprivileges ($odbc, ?string $catalog, string $schema, string $table, string $column) {}

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
 * @var int
 */
define ('ODBC_TYPE', "unixODBC");

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('ODBC_BINMODE_PASSTHRU', 0);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('ODBC_BINMODE_RETURN', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('ODBC_BINMODE_CONVERT', 2);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_ODBC_CURSORS', 110);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_CUR_USE_DRIVER', 2);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_CUR_USE_IF_NEEDED', 0);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_CUR_USE_ODBC', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_CONCURRENCY', 7);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_CONCUR_READ_ONLY', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_CONCUR_LOCK', 2);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_CONCUR_ROWVER', 3);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_CONCUR_VALUES', 4);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_CURSOR_TYPE', 6);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_CURSOR_FORWARD_ONLY', 0);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_CURSOR_KEYSET_DRIVEN', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_CURSOR_DYNAMIC', 2);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_CURSOR_STATIC', 3);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_KEYSET_SIZE', 8);
define ('SQL_FETCH_FIRST', 2);
define ('SQL_FETCH_NEXT', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_CHAR', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_VARCHAR', 12);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_LONGVARCHAR', -1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_DECIMAL', 3);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_NUMERIC', 2);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_BIT', -7);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_TINYINT', -6);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_SMALLINT', 5);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_INTEGER', 4);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_BIGINT', -5);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_REAL', 7);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_FLOAT', 6);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_DOUBLE', 8);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_BINARY', -2);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_VARBINARY', -3);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_LONGVARBINARY', -4);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_DATE', 9);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_TIME', 10);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_TIMESTAMP', 11);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_TYPE_DATE', 91);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_TYPE_TIME', 92);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_TYPE_TIMESTAMP', 93);
define ('SQL_WCHAR', -8);
define ('SQL_WVARCHAR', -9);
define ('SQL_WLONGVARCHAR', -10);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_BEST_ROWID', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_ROWVER', 2);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_SCOPE_CURROW', 0);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_SCOPE_TRANSACTION', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_SCOPE_SESSION', 2);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_NO_NULLS', 0);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_NULLABLE', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_INDEX_UNIQUE', 0);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_INDEX_ALL', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_ENSURE', 1);

/**
 * 
 * @link http://www.php.net/manual/en/uodbc.constants.php
 * @var int
 */
define ('SQL_QUICK', 0);

// End of odbc v.8.2.6
