<?php

// Start of sqlsrv v.4.1.6.1

/**
 * Opens a connection to a Microsoft SQL Server database
 * @link http://www.php.net/manual/en/function.sqlsrv-connect.php
 * @param string $serverName The name of the server to which a connection is established. To connect 
 * to a specific instance, follow the server name with a backward slash 
 * and the instance name (e.g. serverName\sqlexpress).
 * @param array $connectionInfo [optional] An associative array that specifies options for connecting to the server. 
 * If values for the UID and PWD keys are not specified, the connection 
 * will be attempted using Windows Authentication. For a complete list of supported 
 * keys, see SQLSRV Connection Options.
 * @return resource A connection resource. If a connection cannot be successfully opened, false is returned.
 */
function sqlsrv_connect (string $serverName, array $connectionInfo = null) {}

/**
 * Closes an open connection and releases resourses associated with the connection
 * @link http://www.php.net/manual/en/function.sqlsrv-close.php
 * @param resource $conn The connection to be closed.
 * @return bool true on success or false on failure
 */
function sqlsrv_close ($conn) {}

/**
 * Commits a transaction that was begun with sqlsrv_begin_transaction
 * @link http://www.php.net/manual/en/function.sqlsrv-commit.php
 * @param resource $conn The connection on which the transaction is to be committed.
 * @return bool true on success or false on failure
 */
function sqlsrv_commit ($conn) {}

/**
 * Begins a database transaction
 * @link http://www.php.net/manual/en/function.sqlsrv-begin-transaction.php
 * @param resource $conn The connection resource returned by a call to sqlsrv_connect.
 * @return bool true on success or false on failure
 */
function sqlsrv_begin_transaction ($conn) {}

/**
 * Rolls back a transaction that was begun with 
 * sqlsrv_begin_transaction
 * @link http://www.php.net/manual/en/function.sqlsrv-rollback.php
 * @param resource $conn The connection resource returned by a call to sqlsrv_connect.
 * @return bool true on success or false on failure
 */
function sqlsrv_rollback ($conn) {}

/**
 * Returns error and warning information about the last SQLSRV operation performed
 * @link http://www.php.net/manual/en/function.sqlsrv-errors.php
 * @param int $errorsOrWarnings [optional] Determines whether error information, warning information, or both are 
 * returned. If this parameter is not supplied, both error information and 
 * warning information are returned. The following are the supported values 
 * for this parameter: SQLSRV_ERR_ALL, SQLSRV_ERR_ERRORS, SQLSRV_ERR_WARNINGS.
 * @return mixed If errors and/or warnings occurred on the last sqlsrv operation, an array of 
 * arrays containing error information is returned. If no errors and/or warnings 
 * occurred on the last sqlsrv operation, null is returned. The following table 
 * describes the structure of the returned arrays:
 * <table>
 * Array returned by sqlsrv_errors
 * <table>
 * <tr valign="top">
 * <td>Key</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>SQLSTATE</td>
 * <td>For errors that originate from the ODBC driver, the SQLSTATE returned 
 * by ODBC. For errors that originate from the Microsoft Drivers for PHP for 
 * SQL Server, a SQLSTATE of IMSSP. For warnings that originate from the 
 * Microsoft Drivers for PHP for SQL Server, a SQLSTATE of 01SSP.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>code</td>
 * <td>For errors that originate from SQL Server, the native SQL Server 
 * error code. For errors that originate from the ODBC driver, the error 
 * code returned by ODBC. For errors that originate from the Microsoft Drivers 
 * for PHP for SQL Server, the Microsoft Drivers for PHP for SQL Server error code.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>message</td>
 * <td>A description of the error.</td>
 * </tr>
 * </table>
 * </table>
 */
function &sqlsrv_errors (int $errorsOrWarnings = null) {}

/**
 * Changes the driver error handling and logging configurations
 * @link http://www.php.net/manual/en/function.sqlsrv-configure.php
 * @param string $setting The name of the setting to set. The possible values are 
 * "WarningsReturnAsErrors", "LogSubsystems", and "LogSeverity".
 * @param mixed $value The value of the specified setting. The following table shows possible values:
 * <table>
 * Error and Logging Setting Options
 * <table>
 * <tr valign="top">
 * <td>Setting</td>
 * <td>Options</td>
 * </tr>
 * <tr valign="top">
 * <td>WarningsReturnAsErrors</td>
 * <td>1 (true) or 0 (false)</td>
 * </tr>
 * <tr valign="top">
 * <td>LogSubsystems</td>
 * <td>SQLSRV_LOG_SYSTEM_ALL (-1) 
 * SQLSRV_LOG_SYSTEM_CONN (2) 
 * SQLSRV_LOG_SYSTEM_INIT (1) 
 * SQLSRV_LOG_SYSTEM_OFF (0) 
 * SQLSRV_LOG_SYSTEM_STMT (4) 
 * SQLSRV_LOG_SYSTEM_UTIL (8)</td>
 * </tr>
 * <tr valign="top">
 * <td>LogSeverity</td>
 * <td>SQLSRV_LOG_SEVERITY_ALL (-1) 
 * SQLSRV_LOG_SEVERITY_ERROR (1) 
 * SQLSRV_LOG_SEVERITY_NOTICE (4) 
 * SQLSRV_LOG_SEVERITY_WARNING (2)</td>
 * </tr>
 * </table>
 * </table>
 * @return bool true on success or false on failure
 */
function sqlsrv_configure (string $setting, $value) {}

/**
 * Returns the value of the specified configuration setting
 * @link http://www.php.net/manual/en/function.sqlsrv-get-config.php
 * @param string $setting The name of the setting for which the value is returned. For a list of 
 * configurable settings, see sqlsrv_configure.
 * @return mixed the value of the specified setting. If an invalid setting is specified, 
 * false is returned.
 */
function sqlsrv_get_config (string $setting) {}

/**
 * Prepares a query for execution
 * @link http://www.php.net/manual/en/function.sqlsrv-prepare.php
 * @param resource $conn A connection resource returned by sqlsrv_connect.
 * @param string $sql The string that defines the query to be prepared and executed.
 * @param array $params [optional] <p>
 * An array specifying parameter information when executing a parameterized 
 * query. Array elements can be any of the following:
 * <p>
 * A literal value
 * A PHP variable
 * An array with this structure: 
 * array($value [, $direction [, $phpType [, $sqlType]]])
 * </p>
 * The following table describes the elements in the array structure above:
 * </p>
 * <table>
 * Array structure
 * <table>
 * <tr valign="top">
 * <td>Element</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>$value</td>
 * <td>A literal value, a PHP variable, or a PHP by-reference variable.</td>
 * </tr>
 * <tr valign="top">
 * <td>$direction (optional)</td>
 * <td>One of the following SQLSRV constants used to indicate the 
 * parameter direction: SQLSRV_PARAM_IN, SQLSRV_PARAM_OUT, SQLSRV_PARAM_INOUT. 
 * The default value is SQLSRV_PARAM_IN. </td>
 * </tr>
 * <tr valign="top">
 * <td>$phpType (optional)</td>
 * <td>A SQLSRV_PHPTYPE_&#42; constant that specifies PHP data type of the 
 * returned value.</td>
 * </tr>
 * <tr valign="top">
 * <td>$sqlType (optional)</td>
 * <td>A SQLSRV_SQLTYPE_&#42; constant that specifies the SQL Server data 
 * type of the input value.</td>
 * </tr>
 * </table>
 * </table>
 * @param array $options [optional] <p>
 * An array specifing query property options. The supported keys are described 
 * in the following table:
 * </p>
 * <table>
 * Query Options
 * <table>
 * <tr valign="top">
 * <td>Key</td>
 * <td>Values</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>QueryTimeout</td>
 * <td>A positive integer value.</td>
 * <td>Sets the query timeout in seconds. By default, the driver will 
 * wait indefinitely for results.</td>
 * </tr>
 * <tr valign="top">
 * <td>SendStreamParamsAtExec</td>
 * <td>true or false (the default is true)</td>
 * <td>Configures the driver to send all stream data at execution (true), 
 * or to send stream data in chunks (false). By default, the value is set to true. 
 * For more information, see sqlsrv_send_stream_data.</td>
 * </tr>
 * <tr valign="top">
 * <td>Scrollable</td>
 * <td>SQLSRV_CURSOR_FORWARD, SQLSRV_CURSOR_STATIC, SQLSRV_CURSOR_DYNAMIC, 
 * or SQLSRV_CURSOR_KEYSET</td>
 * <td>See Specifying a Cursor Type and Selecting Rows 
 * in the Microsoft SQLSRV documentation.</td>
 * </tr>
 * </table>
 * </table>
 * @return mixed a statement resource on success and false if an error occurred.
 */
function &sqlsrv_prepare ($conn, string $sql, array $params = null, array $options = null) {}

/**
 * Executes a statement prepared with sqlsrv_prepare
 * @link http://www.php.net/manual/en/function.sqlsrv-execute.php
 * @param resource $stmt A statement resource returned by sqlsrv_prepare.
 * @return bool true on success or false on failure
 */
function sqlsrv_execute ($stmt) {}

/**
 * Prepares and executes a query.
 * @link http://www.php.net/manual/en/function.sqlsrv-query.php
 * @param resource $conn A connection resource returned by sqlsrv_connect.
 * @param string $sql The string that defines the query to be prepared and executed.
 * @param array $params [optional] <p>
 * An array specifying parameter information when executing a parameterized query. 
 * Array elements can be any of the following:
 * <p>
 * A literal value
 * A PHP variable
 * An array with this structure: 
 * array($value [, $direction [, $phpType [, $sqlType]]])
 * </p>
 * The following table describes the elements in the array structure above:
 * </p>
 * <table>
 * Array structure
 * <table>
 * <tr valign="top">
 * <td>Element</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>$value</td>
 * <td>A literal value, a PHP variable, or a PHP by-reference variable.</td>
 * </tr>
 * <tr valign="top">
 * <td>$direction (optional)</td>
 * <td>One of the following SQLSRV constants used to indicate the 
 * parameter direction: SQLSRV_PARAM_IN, SQLSRV_PARAM_OUT, SQLSRV_PARAM_INOUT. 
 * The default value is SQLSRV_PARAM_IN.</td>
 * </tr>
 * <tr valign="top">
 * <td>$phpType (optional)</td>
 * <td>A SQLSRV_PHPTYPE_&#42; constant that specifies PHP data type of the 
 * returned value.</td>
 * </tr>
 * <tr valign="top">
 * <td>$sqlType (optional)</td>
 * <td>A SQLSRV_SQLTYPE_&#42; constant that specifies the SQL Server data 
 * type of the input value.</td>
 * </tr>
 * </table>
 * </table>
 * @param array $options [optional] <p>
 * An array specifing query property options. The supported keys are described 
 * in the following table:
 * </p>
 * <table>
 * Query Options
 * <table>
 * <tr valign="top">
 * <td>Key</td>
 * <td>Values</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>QueryTimeout</td>
 * <td>A positive integer value.</td>
 * <td>Sets the query timeout in seconds. By default, the driver will 
 * wait indefinitely for results.</td>
 * </tr>
 * <tr valign="top">
 * <td>SendStreamParamsAtExec</td>
 * <td>true or false (the default is true)</td>
 * <td>Configures the driver to send all stream data at execution (true), 
 * or to send stream data in chunks (false). By default, the value is set 
 * to true. For more information, see sqlsrv_send_stream_data.</td>
 * </tr>
 * <tr valign="top">
 * <td>Scrollable</td>
 * <td>SQLSRV_CURSOR_FORWARD, SQLSRV_CURSOR_STATIC, SQLSRV_CURSOR_DYNAMIC, 
 * or SQLSRV_CURSOR_KEYSET</td>
 * <td>See Specifying 
 * a Cursor Type and Selecting Rows in the Microsoft SQLSRV documentation.</td>
 * </tr>
 * </table>
 * </table>
 * @return mixed a statement resource on success and false if an error occurred.
 */
function &sqlsrv_query ($conn, string $sql, array $params = null, array $options = null) {}

/**
 * Makes the next row in a result set available for reading
 * @link http://www.php.net/manual/en/function.sqlsrv-fetch.php
 * @param resource $stmt A statement resource created by executing sqlsrv_query 
 * or sqlsrv_execute.
 * @param int $row [optional] <p>
 * The row to be accessed. This parameter can only be used if the specified 
 * statement was prepared with a scrollable cursor. In that case, this parameter 
 * can take on one of the following values:
 * <p>
 * SQLSRV_SCROLL_NEXT
 * SQLSRV_SCROLL_PRIOR
 * SQLSRV_SCROLL_FIRST
 * SQLSRV_SCROLL_LAST
 * SQLSRV_SCROLL_ABSOLUTE
 * SQLSRV_SCROLL_RELATIVE
 * </p>
 * </p>
 * @param int $offset [optional] Specifies the row to be accessed if the row parameter is set to
 * SQLSRV_SCROLL_ABSOLUTE or
 * SQLSRV_SCROLL_RELATIVE. Note that the first row in
 * a result set has index 0.
 * @return mixed true if the next row of a result set was successfully retrieved, 
 * false if an error occurs, and null if there are no more rows in the result set.
 */
function sqlsrv_fetch ($stmt, int $row = null, int $offset = null) {}

/**
 * Gets field data from the currently selected row
 * @link http://www.php.net/manual/en/function.sqlsrv-get-field.php
 * @param resource $stmt A statement resource returned by sqlsrv_query or 
 * sqlsrv_execute.
 * @param int $fieldIndex The index of the field to be retrieved. Field indices start at 0. Fields 
 * must be accessed in order. i.e. If you access field index 1, then field 
 * index 0 will not be available.
 * @param int $getAsType [optional] The PHP data type for the returned field data. If this parameter is not 
 * set, the field data will be returned as its default PHP data type.
 * For information about default PHP data types, see 
 * Default PHP Data Types 
 * in the Microsoft SQLSRV documentation.
 * @return mixed data from the specified field on success. Returns false otherwise.
 */
function &sqlsrv_get_field ($stmt, int $fieldIndex, int $getAsType = null) {}

/**
 * Returns a row as an array
 * @link http://www.php.net/manual/en/function.sqlsrv-fetch-array.php
 * @param resource $stmt A statement resource returned by sqlsrv_query or sqlsrv_prepare.
 * @param int $fetchType [optional] <p>
 * A predefined constant specifying the type of array to return. Possible 
 * values are SQLSRV_FETCH_ASSOC, 
 * SQLSRV_FETCH_NUMERIC, and 
 * SQLSRV_FETCH_BOTH (the default).
 * </p>
 * <p>
 * A fetch type of SQLSRV_FETCH_ASSOC should not be used when consuming a 
 * result set with multiple columns of the same name.
 * </p>
 * @param int $row [optional] Specifies the row to access in a result set that uses a scrollable cursor. 
 * Possible values are SQLSRV_SCROLL_NEXT, 
 * SQLSRV_SCROLL_PRIOR, SQLSRV_SCROLL_FIRST, 
 * SQLSRV_SCROLL_LAST, SQLSRV_SCROLL_ABSOLUTE and, 
 * SQLSRV_SCROLL_RELATIVE (the default). When this parameter 
 * is specified, the fetchType must be explicitly defined.
 * @param int $offset [optional] Specifies the row to be accessed if the row parameter is set to
 * SQLSRV_SCROLL_ABSOLUTE or
 * SQLSRV_SCROLL_RELATIVE. Note that the first row in
 * a result set has index 0.
 * @return array an array on success, null if there are no more rows to return, and 
 * false if an error occurs.
 */
function &sqlsrv_fetch_array ($stmt, int $fetchType = null, int $row = null, int $offset = null) {}

/**
 * Retrieves the next row of data in a result set as an object
 * @link http://www.php.net/manual/en/function.sqlsrv-fetch-object.php
 * @param resource $stmt A statement resource created by sqlsrv_query or 
 * sqlsrv_execute.
 * @param string $className [optional] The name of the class to instantiate. If no class name is specified, 
 * stdClass is instantiated.
 * @param array $ctorParams [optional] Values passed to the constructor of the specified class. If the constructor 
 * of the specified class takes parameters, the ctorParams array must be 
 * supplied.
 * @param int $row [optional] <p>
 * The row to be accessed. This parameter can only be used if the specified 
 * statement was prepared with a scrollable cursor. In that case, this parameter 
 * can take on one of the following values:
 * <p>
 * SQLSRV_SCROLL_NEXT
 * SQLSRV_SCROLL_PRIOR
 * SQLSRV_SCROLL_FIRST
 * SQLSRV_SCROLL_LAST
 * SQLSRV_SCROLL_ABSOLUTE
 * SQLSRV_SCROLL_RELATIVE
 * </p>
 * </p>
 * @param int $offset [optional] Specifies the row to be accessed if the row parameter is set to
 * SQLSRV_SCROLL_ABSOLUTE or
 * SQLSRV_SCROLL_RELATIVE. Note that the first row in
 * a result set has index 0.
 * @return mixed an object on success, null if there are no more rows to return, 
 * and false if an error occurs or if the specified class does not exist.
 */
function &sqlsrv_fetch_object ($stmt, string $className = null, array $ctorParams = null, int $row = null, int $offset = null) {}

/**
 * Indicates whether the specified statement has rows
 * @link http://www.php.net/manual/en/function.sqlsrv-has-rows.php
 * @param resource $stmt A statement resource returned by sqlsrv_query or 
 * sqlsrv_execute.
 * @return bool true if the specified statement has rows and false if the statement 
 * does not have rows or if an error occurred.
 */
function sqlsrv_has_rows ($stmt) {}

/**
 * Retrieves the number of fields (columns) on a statement
 * @link http://www.php.net/manual/en/function.sqlsrv-num-fields.php
 * @param resource $stmt The statment for which the number of fields is returned. 
 * sqlsrv_num_fields can be called on a statement before 
 * or after statement execution.
 * @return mixed the number of fields on success. Returns false otherwise.
 */
function sqlsrv_num_fields ($stmt) {}

/**
 * Makes the next result of the specified statement active
 * @link http://www.php.net/manual/en/function.sqlsrv-next-result.php
 * @param resource $stmt The statment on which the next result is being called.
 * @return mixed true if the next result was successfully retrieved, false if an error 
 * occurred, and null if there are no more results to retrieve.
 */
function sqlsrv_next_result ($stmt) {}

/**
 * Retrieves the number of rows in a result set
 * @link http://www.php.net/manual/en/function.sqlsrv-num-rows.php
 * @param resource $stmt The statement for which the row count is returned. The statment resource 
 * must be created with a static or keyset cursor. For more information, see 
 * sqlsrv_query, sqlsrv_prepare, or 
 * Specifying a Cursor Type and Selecting Rows 
 * in the Microsoft SQLSRV documentation.
 * @return mixed the number of rows retrieved on success and false if an error occurred. 
 * If a forward cursor (the default) or dynamic cursor is used, false is returned.
 */
function sqlsrv_num_rows ($stmt) {}

/**
 * Returns the number of rows modified by the last INSERT, UPDATE, or 
 * DELETE query executed
 * @link http://www.php.net/manual/en/function.sqlsrv-rows-affected.php
 * @param resource $stmt The executed statement resource for which the number of affected rows is returned.
 * @return int the number of rows affected by the last INSERT, UPDATE, or DELETE query. 
 * If no rows were affected, 0 is returned. If the number of affected rows cannot 
 * be determined, -1 is returned. If an error occurred, false is returned.
 */
function sqlsrv_rows_affected ($stmt) {}

/**
 * @param $encoding
 */
function SQLSRV_PHPTYPE_STREAM ($encoding) {}

/**
 * @param $encoding
 */
function SQLSRV_PHPTYPE_STRING ($encoding) {}

/**
 * Returns information about the client and specified connection
 * @link http://www.php.net/manual/en/function.sqlsrv-client-info.php
 * @param resource $conn The connection about which information is returned.
 * @return array an associative array with keys described in the table below. 
 * Returns false otherwise. 
 * <table>
 * Array returned by sqlsrv_client_info
 * <table>
 * <tr valign="top">
 * <td>Key</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>DriverDllName</td>
 * <td>SQLNCLI10.DLL</td>
 * </tr>
 * <tr valign="top">
 * <td>DriverODBCVer</td>
 * <td>ODBC version (xx.yy)</td>
 * </tr>
 * <tr valign="top">
 * <td>DriverVer</td>
 * <td>SQL Server Native Client DLL version (10.5.xxx)</td>
 * </tr>
 * <tr valign="top">
 * <td>ExtensionVer</td>
 * <td>php_sqlsrv.dll version (2.0.xxx.x)</td>
 * </tr>
 * </table>
 * </table>
 */
function sqlsrv_client_info ($conn) {}

/**
 * Returns information about the server
 * @link http://www.php.net/manual/en/function.sqlsrv-server-info.php
 * @param resource $conn The connection resource that connects the client and the server.
 * @return array an array as described in the following table:
 * <table>
 * Returned Array
 * <table>
 * <tr valign="top">
 * <td>CurrentDatabase</td>
 * <td>The connected-to database.</td>
 * </tr>
 * <tr valign="top">
 * <td>SQLServerVersion</td>
 * <td>The SQL Server version.</td>
 * </tr>
 * <tr valign="top">
 * <td>SQLServerName</td>
 * <td>The name of the server.</td>
 * </tr>
 * </table>
 * </table>
 */
function sqlsrv_server_info ($conn) {}

/**
 * Cancels a statement
 * @link http://www.php.net/manual/en/function.sqlsrv-cancel.php
 * @param resource $stmt The statement resource to be cancelled.
 * @return bool true on success or false on failure
 */
function sqlsrv_cancel ($stmt) {}

/**
 * Frees all resources for the specified statement
 * @link http://www.php.net/manual/en/function.sqlsrv-free-stmt.php
 * @param resource $stmt The statment for which resources are freed. 
 * Note that null is a valid parameter value. This allows the function to be 
 * called multiple times in a script.
 * @return bool true on success or false on failure
 */
function sqlsrv_free_stmt ($stmt) {}

/**
 * Retrieves metadata for the fields of a statement prepared by 
 * sqlsrv_prepare or sqlsrv_query
 * @link http://www.php.net/manual/en/function.sqlsrv-field-metadata.php
 * @param resource $stmt The statment resource for which metadata is returned.
 * @return mixed an array of arrays is returned on success. Otherwise, false is returned. 
 * Each returned array is described by the following table:
 * <table>
 * Array returned by sqlsrv_field_metadata
 * <table>
 * <tr valign="top">
 * <td>Key</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>Name</td>
 * <td>The name of the field.</td>
 * </tr>
 * <tr valign="top">
 * <td>Type</td>
 * <td>The numeric value for the SQL type.</td>
 * </tr>
 * <tr valign="top">
 * <td>Size</td>
 * <td>The number of characters for fields of character type, the number of 
 * bytes for fields of binary type, or null for other types.</td>
 * </tr>
 * <tr valign="top">
 * <td>Precision</td>
 * <td>The precision for types of variable precision, null for other types.</td>
 * </tr>
 * <tr valign="top">
 * <td>Scale</td>
 * <td>The scale for types of variable scale, null for other types.</td>
 * </tr>
 * <tr valign="top">
 * <td>Nullable</td>
 * <td>An enumeration indicating whether the column is nullable, not nullable, 
 * or if it is not known.</td>
 * </tr>
 * </table>
 * </table>
 * For more information, see sqlsrv_field_metadata 
 * in the Microsoft SQLSRV documentation.
 */
function &sqlsrv_field_metadata ($stmt) {}

/**
 * Sends data from parameter streams to the server
 * @link http://www.php.net/manual/en/function.sqlsrv-send-stream-data.php
 * @param resource $stmt A statement resource returned by sqlsrv_query or 
 * sqlsrv_execute.
 * @return bool true if there is more data to send and false if there is not.
 */
function sqlsrv_send_stream_data ($stmt) {}

/**
 * @param $size
 */
function SQLSRV_SQLTYPE_BINARY ($size) {}

/**
 * @param $size
 */
function SQLSRV_SQLTYPE_CHAR ($size) {}

/**
 * @param $precision
 * @param $scale
 */
function SQLSRV_SQLTYPE_DECIMAL ($precision, $scale) {}

/**
 * @param $size
 */
function SQLSRV_SQLTYPE_NCHAR ($size) {}

/**
 * @param $precision
 * @param $scale
 */
function SQLSRV_SQLTYPE_NUMERIC ($precision, $scale) {}

/**
 * @param $size
 */
function SQLSRV_SQLTYPE_NVARCHAR ($size) {}

/**
 * @param $size
 */
function SQLSRV_SQLTYPE_VARBINARY ($size) {}

/**
 * @param $size
 */
function SQLSRV_SQLTYPE_VARCHAR ($size) {}


/**
 * Forces sqlsrv_errors to return errors only (no warnings) 
 * when passed as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_ERR_ERRORS', 0);

/**
 * Forces sqlsrv_errors to return warnings only (no errors) 
 * when passed as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_ERR_WARNINGS', 1);

/**
 * Forces sqlsrv_errors to return both errors and warings 
 * when passed as a parameter (the default behavior).
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_ERR_ALL', 2);

/**
 * Turns off logging of all subsystems when passed to 
 * sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_LOG_SYSTEM_OFF', 0);

/**
 * Turns on logging of initialization activity when passed to 
 * sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_LOG_SYSTEM_INIT', 1);

/**
 * Turns on logging of connection activity when passed to 
 * sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_LOG_SYSTEM_CONN', 2);

/**
 * Turns on logging of statement activity when passed to 
 * sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_LOG_SYSTEM_STMT', 4);

/**
 * Turns on logging of error function activity when passed to 
 * sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_LOG_SYSTEM_UTIL', 8);

/**
 * Turns on logging of all subsystems when passed to 
 * sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_LOG_SYSTEM_ALL', -1);

/**
 * Specifies that errors will be logged when passed to 
 * sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_LOG_SEVERITY_ERROR', 1);

/**
 * Specifies that warnings will be logged when passed to 
 * sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_LOG_SEVERITY_WARNING', 2);

/**
 * Specifies that notices will be logged when passed to 
 * sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_LOG_SEVERITY_NOTICE', 4);

/**
 * Specifies that errors, warnings, and notices will be logged 
 * when passed to sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_LOG_SEVERITY_ALL', -1);

/**
 * Forces sqlsrv_fetch_array to return an array with 
 * numeric when passed as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_FETCH_NUMERIC', 1);

/**
 * Forces sqlsrv_fetch_array to return an associative 
 * array when passed as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_FETCH_ASSOC', 2);

/**
 * Forces sqlsrv_fetch_array to return an array with both 
 * associative and numeric keys when passed as a parameter (the default behavior).
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_FETCH_BOTH', 3);
define ('SQLSRV_PHPTYPE_NULL', 1);

/**
 * Specifies an integer PHP data type. For usage information, see 
 * How to: Specify PHP Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_PHPTYPE_INT', 2);

/**
 * Specifies a float PHP data type. For usage information, see 
 * How to: Specify PHP Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_PHPTYPE_FLOAT', 3);

/**
 * Specifies a datetime PHP data type. For usage information, see 
 * How to: Specify PHP Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_PHPTYPE_DATETIME', 5);

/**
 * Specifies that data is returned as a raw byte stream from the server without 
 * performing encoding or translation. For usage information, see 
 * How to: Specify PHP Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_ENC_BINARY', "binary");

/**
 * Data is returned in 8-bit characters as specified in the code page of the 
 * Windows locale that is set on the system. Any multi-byte characters or characters 
 * that do not map into this code page are substituted with a single byte question 
 * mark (?) character. This is the default encoding. For usage information, 
 * see How to: Specify PHP Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_ENC_CHAR', "char");

/**
 * Indicates that a column is not nullable.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_NULLABLE_NO', 0);

/**
 * Indicates that a column is nullable.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_NULLABLE_YES', 1);

/**
 * Indicates that it is not known if a column is nullable.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_NULLABLE_UNKNOWN', 2);

/**
 * Describes the bigint SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_BIGINT', -5);

/**
 * Describes the bit SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_BIT', -7);

/**
 * Describes the datetime SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_DATETIME', 25177693);

/**
 * Describes the float SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_FLOAT', 6);

/**
 * Describes the image SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_IMAGE', -4);

/**
 * Describes the int SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_INT', 4);

/**
 * Describes the money SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_MONEY', 33564163);

/**
 * Describes the ntext SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_NTEXT', -10);

/**
 * Describes the text SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_TEXT', -1);

/**
 * Describes the real SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_REAL', 7);

/**
 * Describes the smalldatetime SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_SMALLDATETIME', 8285);

/**
 * Describes the smallint SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_SMALLINT', 5);

/**
 * Describes the smallmoney SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_SMALLMONEY', 33559555);

/**
 * Describes the timestamp SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_TIMESTAMP', 4606);

/**
 * Describes the tinyint SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_TINYINT', -6);

/**
 * Describes the UDT SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_UDT', -151);

/**
 * Describes the uniqueidentifier SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_UNIQUEIDENTIFIER', -11);

/**
 * Describes the XML SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_XML', -152);

/**
 * Describes the date SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_DATE', 5211);

/**
 * Describes the time SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_TIME', 58728806);

/**
 * Describes the datetimeoffset SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_DATETIMEOFFSET', 58738021);

/**
 * Describes the datetime2 SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_DATETIME2', 58734173);

/**
 * Describes the decimal SQL Server data type. This constant works like a function 
 * and accepts two parameters indicating (in order) precision and scale. For usage information, 
 * see How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_DECIMAL', 3);

/**
 * Describes the numeric SQL Server data type. This constant works like a function 
 * and accepts two parameter indicating (in order) precision and scale. For usage 
 * information, see How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_NUMERIC', 2);

/**
 * Describes the char SQL Server data type. This constant works like a function 
 * and accepts a parameter indicating the number characters. For usage information, 
 * see How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_CHAR', 1);

/**
 * Describes the nchar SQL Server data type. This constant works like a function 
 * and accepts a single parameter indicating the character count. For usage information, 
 * see How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_NCHAR', -8);

/**
 * Describes the varchar(MAX) SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_VARCHAR', 12);

/**
 * Describes the nvarchar(MAX) SQL Server data type. For usage information, 
 * see How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_NVARCHAR', -9);

/**
 * Describes the binary SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_BINARY', -2);

/**
 * Describes the varbinary(MAX) SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SQLTYPE_VARBINARY', -3);

/**
 * Indicates an input parameter when passed to sqlsrv_query 
 * or sqlsrv_prepare.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_PARAM_IN', 1);

/**
 * Indicates an output parameter when passed to sqlsrv_query or 
 * sqlsrv_prepare.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_PARAM_OUT', 4);

/**
 * Indicates a bidirectional parameter when passed to sqlsrv_query 
 * or sqlsrv_prepare.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_PARAM_INOUT', 2);

/**
 * Inidicates a transaction isolation level of READ UNCOMMITTED. This value is 
 * used to set the TransactionIsolation level in the $connectionOptions arrary 
 * passed to sqlsrv_connect.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_TXN_READ_UNCOMMITTED', 1);

/**
 * Inidicates a transaction isolation level of READ COMMITTED. This value is 
 * used to set the TransactionIsolation level in the $connectionOptions arrary 
 * passed to sqlsrv_connect.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_TXN_READ_COMMITTED', 2);

/**
 * Inidicates a transaction isolation level of REPEATABLE READ. This value is 
 * used to set the TransactionIsolation level in the $connectionOptions arrary 
 * passed to sqlsrv_connect.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_TXN_REPEATABLE_READ', 4);
define ('SQLSRV_TXN_SERIALIZABLE', 8);

/**
 * Inidicates a transaction isolation level of SNAPSHOT. This value is used to 
 * set the TransactionIsolation level in the $connectionOptions arrary passed 
 * to sqlsrv_connect.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_TXN_SNAPSHOT', 32);

/**
 * Specifies which row to select in a result set. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SCROLL_NEXT', 1);

/**
 * Specifies which row to select in a result set. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SCROLL_PRIOR', 4);

/**
 * Specifies which row to select in a result set. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SCROLL_FIRST', 2);

/**
 * Specifies which row to select in a result set. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SCROLL_LAST', 3);

/**
 * Specifies which row to select in a result set. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SCROLL_ABSOLUTE', 5);

/**
 * Specifies which row to select in a result set. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_SCROLL_RELATIVE', 6);

/**
 * Inidicates a forward-only cursor. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_CURSOR_FORWARD', "forward");

/**
 * Inidicates a static cursor. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_CURSOR_STATIC', "static");

/**
 * Inidicates a dynamic cursor. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_CURSOR_DYNAMIC', "dynamic");

/**
 * Inidicates a keyset cursor. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 */
define ('SQLSRV_CURSOR_KEYSET', "keyset");
define ('SQLSRV_CURSOR_CLIENT_BUFFERED', "buffered");

// End of sqlsrv v.4.1.6.1
