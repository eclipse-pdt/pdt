<?php

// Start of mysql v.1.0

/**
 * Open a connection to a MySQL Server
 * @link http://php.net/manual/en/function.mysql-connect.php
 * @param server string[optional]
 * @param username string[optional]
 * @param password string[optional]
 * @param new_link bool[optional]
 * @param client_flags int[optional]
 * @return resource a MySQL link identifier on success, or false on failure.
 */
function mysql_connect ($server = null, $username = null, $password = null, $new_link = null, $client_flags = null) {}

/**
 * Open a persistent connection to a MySQL server
 * @link http://php.net/manual/en/function.mysql-pconnect.php
 * @param server string[optional]
 * @param username string[optional]
 * @param password string[optional]
 * @param client_flags int[optional]
 * @return resource a MySQL persistent link identifier on success, or false on
 */
function mysql_pconnect ($server = null, $username = null, $password = null, $client_flags = null) {}

/**
 * Close MySQL connection
 * @link http://php.net/manual/en/function.mysql-close.php
 * @param link_identifier resource[optional]
 * @return bool 
 */
function mysql_close ($link_identifier = null) {}

/**
 * Select a MySQL database
 * @link http://php.net/manual/en/function.mysql-select-db.php
 * @param database_name string
 * @param link_identifier resource[optional]
 * @return bool 
 */
function mysql_select_db ($database_name, $link_identifier = null) {}

/**
 * Send a MySQL query
 * @link http://php.net/manual/en/function.mysql-query.php
 * @param query string
 * @param link_identifier resource[optional]
 * @return resource 
 */
function mysql_query ($query, $link_identifier = null) {}

/**
 * Send an SQL query to MySQL, without fetching and buffering the result rows
 * @link http://php.net/manual/en/function.mysql-unbuffered-query.php
 * @param query string
 * @param link_identifier resource[optional]
 * @return resource 
 */
function mysql_unbuffered_query ($query, $link_identifier = null) {}

/**
 * Send a MySQL query
 * @link http://php.net/manual/en/function.mysql-db-query.php
 * @param database string
 * @param query string
 * @param link_identifier resource[optional]
 * @return resource a positive MySQL result resource to the query result,
 */
function mysql_db_query ($database, $query, $link_identifier = null) {}

/**
 * List databases available on a MySQL server
 * @link http://php.net/manual/en/function.mysql-list-dbs.php
 * @param link_identifier resource[optional]
 * @return resource a result pointer resource on success, or false on
 */
function mysql_list_dbs ($link_identifier = null) {}

/**
 * List tables in a MySQL database
 * @link http://php.net/manual/en/function.mysql-list-tables.php
 * @param database string
 * @param link_identifier resource[optional]
 * @return resource 
 */
function mysql_list_tables ($database, $link_identifier = null) {}

/**
 * List MySQL table fields
 * @link http://php.net/manual/en/function.mysql-list-fields.php
 * @param database_name string
 * @param table_name string
 * @param link_identifier resource[optional]
 * @return resource 
 */
function mysql_list_fields ($database_name, $table_name, $link_identifier = null) {}

/**
 * List MySQL processes
 * @link http://php.net/manual/en/function.mysql-list-processes.php
 * @param link_identifier resource[optional]
 * @return resource 
 */
function mysql_list_processes ($link_identifier = null) {}

/**
 * Returns the text of the error message from previous MySQL operation
 * @link http://php.net/manual/en/function.mysql-error.php
 * @param link_identifier resource[optional]
 * @return string the error text from the last MySQL function, or
 */
function mysql_error ($link_identifier = null) {}

/**
 * Returns the numerical value of the error message from previous MySQL operation
 * @link http://php.net/manual/en/function.mysql-errno.php
 * @param link_identifier resource[optional]
 * @return int the error number from the last MySQL function, or
 */
function mysql_errno ($link_identifier = null) {}

/**
 * Get number of affected rows in previous MySQL operation
 * @link http://php.net/manual/en/function.mysql-affected-rows.php
 * @param link_identifier resource[optional]
 * @return int the number of affected rows on success, and -1 if the last query
 */
function mysql_affected_rows ($link_identifier = null) {}

/**
 * Get the ID generated from the previous INSERT operation
 * @link http://php.net/manual/en/function.mysql-insert-id.php
 * @param link_identifier resource[optional]
 * @return int 
 */
function mysql_insert_id ($link_identifier = null) {}

/**
 * Get result data
 * @link http://php.net/manual/en/function.mysql-result.php
 * @param result resource
 * @param row int
 * @param field mixed[optional]
 * @return string 
 */
function mysql_result ($result, $row, $field = null) {}

/**
 * Get number of rows in result
 * @link http://php.net/manual/en/function.mysql-num-rows.php
 * @param result resource
 * @return int 
 */
function mysql_num_rows ($result) {}

/**
 * Get number of fields in result
 * @link http://php.net/manual/en/function.mysql-num-fields.php
 * @param result resource
 * @return int the number of fields in the result set resource on
 */
function mysql_num_fields ($result) {}

/**
 * Get a result row as an enumerated array
 * @link http://php.net/manual/en/function.mysql-fetch-row.php
 * @param result resource
 * @return array an numerical array of strings that corresponds to the fetched row, or
 */
function mysql_fetch_row ($result) {}

/**
 * Fetch a result row as an associative array, a numeric array, or both
 * @link http://php.net/manual/en/function.mysql-fetch-array.php
 * @param result resource
 * @param result_type int[optional]
 * @return array an array of strings that corresponds to the fetched row, or false
 */
function mysql_fetch_array ($result, $result_type = null) {}

/**
 * Fetch a result row as an associative array
 * @link http://php.net/manual/en/function.mysql-fetch-assoc.php
 * @param result resource
 * @return array an associative array of strings that corresponds to the fetched row, or
 */
function mysql_fetch_assoc ($result) {}

/**
 * Fetch a result row as an object
 * @link http://php.net/manual/en/function.mysql-fetch-object.php
 * @param result resource
 * @param class_name string[optional]
 * @param params array[optional]
 * @return object an object with string properties that correspond to the
 */
function mysql_fetch_object ($result, $class_name = null, array $params = null) {}

/**
 * Move internal result pointer
 * @link http://php.net/manual/en/function.mysql-data-seek.php
 * @param result resource
 * @param row_number int
 * @return bool 
 */
function mysql_data_seek ($result, $row_number) {}

/**
 * Get the length of each output in a result
 * @link http://php.net/manual/en/function.mysql-fetch-lengths.php
 * @param result resource
 * @return array 
 */
function mysql_fetch_lengths ($result) {}

/**
 * Get column information from a result and return as an object
 * @link http://php.net/manual/en/function.mysql-fetch-field.php
 * @param result resource
 * @param field_offset int[optional]
 * @return object an object containing field information. The properties
 */
function mysql_fetch_field ($result, $field_offset = null) {}

/**
 * Set result pointer to a specified field offset
 * @link http://php.net/manual/en/function.mysql-field-seek.php
 * @param result resource
 * @param field_offset int
 * @return bool 
 */
function mysql_field_seek ($result, $field_offset) {}

/**
 * Free result memory
 * @link http://php.net/manual/en/function.mysql-free-result.php
 * @param result resource
 * @return bool 
 */
function mysql_free_result ($result) {}

/**
 * Get the name of the specified field in a result
 * @link http://php.net/manual/en/function.mysql-field-name.php
 * @param result resource
 * @param field_offset int
 * @return string 
 */
function mysql_field_name ($result, $field_offset) {}

/**
 * Get name of the table the specified field is in
 * @link http://php.net/manual/en/function.mysql-field-table.php
 * @param result resource
 * @param field_offset int
 * @return string 
 */
function mysql_field_table ($result, $field_offset) {}

/**
 * Returns the length of the specified field
 * @link http://php.net/manual/en/function.mysql-field-len.php
 * @param result resource
 * @param field_offset int
 * @return int 
 */
function mysql_field_len ($result, $field_offset) {}

/**
 * Get the type of the specified field in a result
 * @link http://php.net/manual/en/function.mysql-field-type.php
 * @param result resource
 * @param field_offset int
 * @return string 
 */
function mysql_field_type ($result, $field_offset) {}

/**
 * Get the flags associated with the specified field in a result
 * @link http://php.net/manual/en/function.mysql-field-flags.php
 * @param result resource
 * @param field_offset int
 * @return string a string of flags associated with the result, or false on failure.
 */
function mysql_field_flags ($result, $field_offset) {}

/**
 * Escapes a string for use in a mysql_query
 * @link http://php.net/manual/en/function.mysql-escape-string.php
 * @param unescaped_string string
 * @return string the escaped string.
 */
function mysql_escape_string ($unescaped_string) {}

/**
 * Escapes special characters in a string for use in a SQL statement
 * @link http://php.net/manual/en/function.mysql-real-escape-string.php
 * @param unescaped_string string
 * @param link_identifier resource[optional]
 * @return string the escaped string, or false on error.
 */
function mysql_real_escape_string ($unescaped_string, $link_identifier = null) {}

/**
 * Get current system status
 * @link http://php.net/manual/en/function.mysql-stat.php
 * @param link_identifier resource[optional]
 * @return string a string with the status for uptime, threads, queries, open tables,
 */
function mysql_stat ($link_identifier = null) {}

/**
 * Return the current thread ID
 * @link http://php.net/manual/en/function.mysql-thread-id.php
 * @param link_identifier resource[optional]
 * @return int 
 */
function mysql_thread_id ($link_identifier = null) {}

/**
 * Returns the name of the character set
 * @link http://php.net/manual/en/function.mysql-client-encoding.php
 * @param link_identifier resource[optional]
 * @return string the default character set name for the current connection.
 */
function mysql_client_encoding ($link_identifier = null) {}

/**
 * Ping a server connection or reconnect if there is no connection
 * @link http://php.net/manual/en/function.mysql-ping.php
 * @param link_identifier resource[optional]
 * @return bool true if the connection to the server MySQL server is working,
 */
function mysql_ping ($link_identifier = null) {}

/**
 * Get MySQL client info
 * @link http://php.net/manual/en/function.mysql-get-client-info.php
 * @return string 
 */
function mysql_get_client_info () {}

/**
 * Get MySQL host info
 * @link http://php.net/manual/en/function.mysql-get-host-info.php
 * @param link_identifier resource[optional]
 * @return string a string describing the type of MySQL connection in use for the
 */
function mysql_get_host_info ($link_identifier = null) {}

/**
 * Get MySQL protocol info
 * @link http://php.net/manual/en/function.mysql-get-proto-info.php
 * @param link_identifier resource[optional]
 * @return int the MySQL protocol on success, or false on failure.
 */
function mysql_get_proto_info ($link_identifier = null) {}

/**
 * Get MySQL server info
 * @link http://php.net/manual/en/function.mysql-get-server-info.php
 * @param link_identifier resource[optional]
 * @return string the MySQL server version on success, or false on failure.
 */
function mysql_get_server_info ($link_identifier = null) {}

/**
 * Get information about the most recent query
 * @link http://php.net/manual/en/function.mysql-info.php
 * @param link_identifier resource[optional]
 * @return string information about the statement on success, or false on
 */
function mysql_info ($link_identifier = null) {}

/**
 * Sets the client character set
 * @link http://php.net/manual/en/function.mysql-set-charset.php
 * @param charset string
 * @param link_identifier resource[optional]
 * @return bool 
 */
function mysql_set_charset ($charset, $link_identifier = null) {}

function mysql () {}

function mysql_fieldname () {}

function mysql_fieldtable () {}

function mysql_fieldlen () {}

function mysql_fieldtype () {}

function mysql_fieldflags () {}

function mysql_selectdb () {}

function mysql_freeresult () {}

function mysql_numfields () {}

function mysql_numrows () {}

function mysql_listdbs () {}

function mysql_listtables () {}

function mysql_listfields () {}

/**
 * Get result data
 * @link http://php.net/manual/en/function.mysql-db-name.php
 * @param result resource
 * @param row int
 * @param field mixed[optional]
 * @return string the database name on success, and false on failure. If false
 */
function mysql_db_name ($result, $row, $field = null) {}

function mysql_dbname () {}

/**
 * Get table name of field
 * @link http://php.net/manual/en/function.mysql-tablename.php
 * @param result resource
 * @param i int
 * @return string 
 */
function mysql_tablename ($result, $i) {}

function mysql_table_name () {}

define ('MYSQL_ASSOC', 1);
define ('MYSQL_NUM', 2);
define ('MYSQL_BOTH', 3);
define ('MYSQL_CLIENT_COMPRESS', 32);
define ('MYSQL_CLIENT_SSL', 2048);
define ('MYSQL_CLIENT_INTERACTIVE', 1024);
define ('MYSQL_CLIENT_IGNORE_SPACE', 256);

// End of mysql v.1.0
?>
