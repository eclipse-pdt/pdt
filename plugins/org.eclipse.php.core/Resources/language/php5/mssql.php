<?php

// Start of mssql v.

/**
 * Open MS SQL server connection
 * @link http://php.net/manual/en/function.mssql-connect.php
 * @param servername string[optional]
 * @param username string[optional]
 * @param password string[optional]
 * @param new_link bool[optional]
 * @return resource a MS SQL link identifier on success, or false on error.
 */
function mssql_connect ($servername = null, $username = null, $password = null, $new_link = null) {}

/**
 * Open persistent MS SQL connection
 * @link http://php.net/manual/en/function.mssql-pconnect.php
 * @param servername string[optional]
 * @param username string[optional]
 * @param password string[optional]
 * @param new_link bool[optional]
 * @return resource a positive MS SQL persistent link identifier on success, or
 */
function mssql_pconnect ($servername = null, $username = null, $password = null, $new_link = null) {}

/**
 * Close MS SQL Server connection
 * @link http://php.net/manual/en/function.mssql-close.php
 * @param link_identifier resource[optional]
 * @return bool 
 */
function mssql_close ($link_identifier = null) {}

/**
 * Select MS SQL database
 * @link http://php.net/manual/en/function.mssql-select-db.php
 * @param database_name string
 * @param link_identifier resource[optional]
 * @return bool 
 */
function mssql_select_db ($database_name, $link_identifier = null) {}

/**
 * Send MS SQL query
 * @link http://php.net/manual/en/function.mssql-query.php
 * @param query string
 * @param link_identifier resource[optional]
 * @param batch_size int[optional]
 * @return mixed a MS SQL result resource on success, true if no rows were
 */
function mssql_query ($query, $link_identifier = null, $batch_size = null) {}

/**
 * Returns the next batch of records
 * @link http://php.net/manual/en/function.mssql-fetch-batch.php
 * @param result resource
 * @return int the batch number as an integer.
 */
function mssql_fetch_batch ($result) {}

/**
 * Returns the number of records affected by the query
 * @link http://php.net/manual/en/function.mssql-rows-affected.php
 * @param link_identifier resource
 * @return int 
 */
function mssql_rows_affected ($link_identifier) {}

/**
 * Free result memory
 * @link http://php.net/manual/en/function.mssql-free-result.php
 * @param result resource
 * @return bool 
 */
function mssql_free_result ($result) {}

/**
 * Returns the last message from the server
 * @link http://php.net/manual/en/function.mssql-get-last-message.php
 * @return string 
 */
function mssql_get_last_message () {}

/**
 * Gets the number of rows in result
 * @link http://php.net/manual/en/function.mssql-num-rows.php
 * @param result resource
 * @return int the number of rows, as an integer.
 */
function mssql_num_rows ($result) {}

/**
 * Gets the number of fields in result
 * @link http://php.net/manual/en/function.mssql-num-fields.php
 * @param result resource
 * @return int the number of fields, as an integer.
 */
function mssql_num_fields ($result) {}

/**
 * Get field information
 * @link http://php.net/manual/en/function.mssql-fetch-field.php
 * @param result resource
 * @param field_offset int[optional]
 * @return object an object containing field information.
 */
function mssql_fetch_field ($result, $field_offset = null) {}

/**
 * Get row as enumerated array
 * @link http://php.net/manual/en/function.mssql-fetch-row.php
 * @param result resource
 * @return array an array that corresponds to the fetched row, or false if there
 */
function mssql_fetch_row ($result) {}

/**
 * Fetch a result row as an associative array, a numeric array, or both
 * @link http://php.net/manual/en/function.mssql-fetch-array.php
 * @param result resource
 * @param result_type int[optional]
 * @return array an array that corresponds to the fetched row, or false if there
 */
function mssql_fetch_array ($result, $result_type = null) {}

/**
 * Returns an associative array of the current row in the result
 * @link http://php.net/manual/en/function.mssql-fetch-assoc.php
 * @param result_id resource
 * @return array an associative array that corresponds to the fetched row, or
 */
function mssql_fetch_assoc ($result_id) {}

/**
 * Fetch row as object
 * @link http://php.net/manual/en/function.mssql-fetch-object.php
 * @param result resource
 * @return object an object with properties that correspond to the fetched row, or
 */
function mssql_fetch_object ($result) {}

/**
 * Get the length of a field
 * @link http://php.net/manual/en/function.mssql-field-length.php
 * @param result resource
 * @param offset int[optional]
 * @return int 
 */
function mssql_field_length ($result, $offset = null) {}

/**
 * Get the name of a field
 * @link http://php.net/manual/en/function.mssql-field-name.php
 * @param result resource
 * @param offset int[optional]
 * @return string 
 */
function mssql_field_name ($result, $offset = null) {}

/**
 * Gets the type of a field
 * @link http://php.net/manual/en/function.mssql-field-type.php
 * @param result resource
 * @param offset int[optional]
 * @return string 
 */
function mssql_field_type ($result, $offset = null) {}

/**
 * Moves internal row pointer
 * @link http://php.net/manual/en/function.mssql-data-seek.php
 * @param result_identifier resource
 * @param row_number int
 * @return bool 
 */
function mssql_data_seek ($result_identifier, $row_number) {}

/**
 * Seeks to the specified field offset
 * @link http://php.net/manual/en/function.mssql-field-seek.php
 * @param result resource
 * @param field_offset int
 * @return bool 
 */
function mssql_field_seek ($result, $field_offset) {}

/**
 * Get result data
 * @link http://php.net/manual/en/function.mssql-result.php
 * @param result resource
 * @param row int
 * @param field mixed
 * @return string the contents of the specified cell.
 */
function mssql_result ($result, $row, $field) {}

/**
 * Move the internal result pointer to the next result
 * @link http://php.net/manual/en/function.mssql-next-result.php
 * @param result_id resource
 * @return bool true if an additional result set was available or false
 */
function mssql_next_result ($result_id) {}

/**
 * Sets the lower error severity
 * @link http://php.net/manual/en/function.mssql-min-error-severity.php
 * @param severity int
 * @return void 
 */
function mssql_min_error_severity ($severity) {}

/**
 * Sets the lower message severity
 * @link http://php.net/manual/en/function.mssql-min-message-severity.php
 * @param severity int
 * @return void 
 */
function mssql_min_message_severity ($severity) {}

/**
 * Initializes a stored procedure or a remote stored procedure
 * @link http://php.net/manual/en/function.mssql-init.php
 * @param sp_name string
 * @param link_identifier resource[optional]
 * @return resource a resource identifier "statement", used in subsequent calls to
 */
function mssql_init ($sp_name, $link_identifier = null) {}

/**
 * Adds a parameter to a stored procedure or a remote stored procedure
 * @link http://php.net/manual/en/function.mssql-bind.php
 * @param stmt resource
 * @param param_name string
 * @param var mixed
 * @param type int
 * @param is_output int[optional]
 * @param is_null int[optional]
 * @param maxlen int[optional]
 * @return bool 
 */
function mssql_bind ($stmt, $param_name, &$var, $type, $is_output = null, $is_null = null, $maxlen = null) {}

/**
 * Executes a stored procedure on a MS SQL server database
 * @link http://php.net/manual/en/function.mssql-execute.php
 * @param stmt resource
 * @param skip_results bool[optional]
 * @return mixed 
 */
function mssql_execute ($stmt, $skip_results = null) {}

/**
 * Free statement memory
 * @link http://php.net/manual/en/function.mssql-free-statement.php
 * @param stmt resource
 * @return bool 
 */
function mssql_free_statement ($stmt) {}

/**
 * Converts a 16 byte binary GUID to a string
 * @link http://php.net/manual/en/function.mssql-guid-string.php
 * @param binary string
 * @param short_format int[optional]
 * @return string 
 */
function mssql_guid_string ($binary, $short_format = null) {}

define ('MSSQL_ASSOC', 1);
define ('MSSQL_NUM', 2);
define ('MSSQL_BOTH', 3);
define ('SQLTEXT', 35);
define ('SQLVARCHAR', 39);
define ('SQLCHAR', 47);
define ('SQLINT1', 48);
define ('SQLINT2', 52);
define ('SQLINT4', 56);
define ('SQLBIT', 50);
define ('SQLFLT4', 59);
define ('SQLFLT8', 62);
define ('SQLFLTN', 109);

// End of mssql v.
?>
