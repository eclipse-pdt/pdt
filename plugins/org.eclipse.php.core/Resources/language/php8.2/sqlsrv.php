<?php

// Start of sqlsrv v.5.11.0

/**
 * Opens a connection to a Microsoft SQL Server database
 * @link http://www.php.net/manual/en/function.sqlsrv-connect.php
 * @param string $serverName 
 * @param array $connectionInfo [optional] 
 * @return resource A connection resource. If a connection cannot be successfully opened, false is returned.
 */
function sqlsrv_connect (string $serverName, array $connectionInfo = null) {}

/**
 * Closes an open connection and releases resourses associated with the connection
 * @link http://www.php.net/manual/en/function.sqlsrv-close.php
 * @param resource $conn 
 * @return bool Returns true on success or false on failure.
 */
function sqlsrv_close ($conn): bool {}

/**
 * Commits a transaction that was begun with sqlsrv_begin_transaction
 * @link http://www.php.net/manual/en/function.sqlsrv-commit.php
 * @param resource $conn 
 * @return bool Returns true on success or false on failure.
 */
function sqlsrv_commit ($conn): bool {}

/**
 * Begins a database transaction
 * @link http://www.php.net/manual/en/function.sqlsrv-begin-transaction.php
 * @param resource $conn 
 * @return bool Returns true on success or false on failure.
 */
function sqlsrv_begin_transaction ($conn): bool {}

/**
 * Rolls back a transaction that was begun with 
 * sqlsrv_begin_transaction
 * @link http://www.php.net/manual/en/function.sqlsrv-rollback.php
 * @param resource $conn 
 * @return bool Returns true on success or false on failure.
 */
function sqlsrv_rollback ($conn): bool {}

/**
 * Returns error and warning information about the last SQLSRV operation performed
 * @link http://www.php.net/manual/en/function.sqlsrv-errors.php
 * @param int $errorsOrWarnings [optional] 
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
function sqlsrv_errors (int $errorsOrWarnings = null): mixed {}

/**
 * Changes the driver error handling and logging configurations
 * @link http://www.php.net/manual/en/function.sqlsrv-configure.php
 * @param string $setting 
 * @param mixed $value 
 * @return bool Returns true on success or false on failure.
 */
function sqlsrv_configure (string $setting, mixed $value): bool {}

/**
 * Returns the value of the specified configuration setting
 * @link http://www.php.net/manual/en/function.sqlsrv-get-config.php
 * @param string $setting 
 * @return mixed Returns the value of the specified setting. If an invalid setting is specified, 
 * false is returned.
 */
function sqlsrv_get_config (string $setting): mixed {}

/**
 * Prepares a query for execution
 * @link http://www.php.net/manual/en/function.sqlsrv-prepare.php
 * @param resource $conn 
 * @param string $sql 
 * @param array $params [optional] 
 * @param array $options [optional] 
 * @return mixed Returns a statement resource on success and false if an error occurred.
 */
function sqlsrv_prepare ($conn, string $sql, array $params = null, array $options = null): mixed {}

/**
 * Executes a statement prepared with sqlsrv_prepare
 * @link http://www.php.net/manual/en/function.sqlsrv-execute.php
 * @param resource $stmt 
 * @return bool Returns true on success or false on failure.
 */
function sqlsrv_execute ($stmt): bool {}

/**
 * Prepares and executes a query
 * @link http://www.php.net/manual/en/function.sqlsrv-query.php
 * @param resource $conn 
 * @param string $sql 
 * @param array $params [optional] 
 * @param array $options [optional] 
 * @return mixed Returns a statement resource on success and false if an error occurred.
 */
function sqlsrv_query ($conn, string $sql, array $params = null, array $options = null): mixed {}

/**
 * Makes the next row in a result set available for reading
 * @link http://www.php.net/manual/en/function.sqlsrv-fetch.php
 * @param resource $stmt 
 * @param int $row [optional] 
 * @param int $offset [optional] 
 * @return mixed Returns true if the next row of a result set was successfully retrieved, 
 * false if an error occurs, and null if there are no more rows in the result set.
 */
function sqlsrv_fetch ($stmt, int $row = null, int $offset = null): mixed {}

/**
 * Gets field data from the currently selected row
 * @link http://www.php.net/manual/en/function.sqlsrv-get-field.php
 * @param resource $stmt 
 * @param int $fieldIndex 
 * @param int $getAsType [optional] 
 * @return mixed Returns data from the specified field on success. Returns false otherwise.
 */
function sqlsrv_get_field ($stmt, int $fieldIndex, int $getAsType = null): mixed {}

/**
 * Returns a row as an array
 * @link http://www.php.net/manual/en/function.sqlsrv-fetch-array.php
 * @param resource $stmt 
 * @param int $fetchType [optional] 
 * @param int $row [optional] 
 * @param int $offset [optional] 
 * @return array Returns an array on success, null if there are no more rows to return, and 
 * false if an error occurs.
 */
function sqlsrv_fetch_array ($stmt, int $fetchType = null, int $row = null, int $offset = null): array {}

/**
 * Retrieves the next row of data in a result set as an object
 * @link http://www.php.net/manual/en/function.sqlsrv-fetch-object.php
 * @param resource $stmt 
 * @param string $className [optional] 
 * @param array $ctorParams [optional] 
 * @param int $row [optional] 
 * @param int $offset [optional] 
 * @return mixed Returns an object on success, null if there are no more rows to return, 
 * and false if an error occurs or if the specified class does not exist.
 */
function sqlsrv_fetch_object ($stmt, string $className = null, array $ctorParams = null, int $row = null, int $offset = null): mixed {}

/**
 * Indicates whether the specified statement has rows
 * @link http://www.php.net/manual/en/function.sqlsrv-has-rows.php
 * @param resource $stmt 
 * @return bool Returns true if the specified statement has rows and false if the statement 
 * does not have rows or if an error occurred.
 */
function sqlsrv_has_rows ($stmt): bool {}

/**
 * Retrieves the number of fields (columns) on a statement
 * @link http://www.php.net/manual/en/function.sqlsrv-num-fields.php
 * @param resource $stmt 
 * @return mixed Returns the number of fields on success. Returns false otherwise.
 */
function sqlsrv_num_fields ($stmt): mixed {}

/**
 * Makes the next result of the specified statement active
 * @link http://www.php.net/manual/en/function.sqlsrv-next-result.php
 * @param resource $stmt 
 * @return mixed Returns true if the next result was successfully retrieved, false if an error 
 * occurred, and null if there are no more results to retrieve.
 */
function sqlsrv_next_result ($stmt): mixed {}

/**
 * Retrieves the number of rows in a result set
 * @link http://www.php.net/manual/en/function.sqlsrv-num-rows.php
 * @param resource $stmt 
 * @return mixed Returns the number of rows retrieved on success and false if an error occurred. 
 * If a forward cursor (the default) or dynamic cursor is used, false is returned.
 */
function sqlsrv_num_rows ($stmt): mixed {}

/**
 * Returns the number of rows modified by the last INSERT, UPDATE, or 
 * DELETE query executed
 * @link http://www.php.net/manual/en/function.sqlsrv-rows-affected.php
 * @param resource $stmt 
 * @return int|false Returns the number of rows affected by the last INSERT, UPDATE, or DELETE query. 
 * If no rows were affected, 0 is returned. If the number of affected rows cannot 
 * be determined, -1 is returned. If an error occurred, false is returned.
 */
function sqlsrv_rows_affected ($stmt): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $encoding
 */
function SQLSRV_PHPTYPE_STREAM ($encoding = null) {}

/**
 * {@inheritdoc}
 * @param mixed $encoding
 */
function SQLSRV_PHPTYPE_STRING ($encoding = null) {}

/**
 * Returns information about the client and specified connection
 * @link http://www.php.net/manual/en/function.sqlsrv-client-info.php
 * @param resource $conn 
 * @return array Returns an associative array with keys described in the table below. 
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
function sqlsrv_client_info ($conn): array {}

/**
 * Returns information about the server
 * @link http://www.php.net/manual/en/function.sqlsrv-server-info.php
 * @param resource $conn 
 * @return array Returns an array as described in the following table:
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
function sqlsrv_server_info ($conn): array {}

/**
 * Cancels a statement
 * @link http://www.php.net/manual/en/function.sqlsrv-cancel.php
 * @param resource $stmt 
 * @return bool Returns true on success or false on failure.
 */
function sqlsrv_cancel ($stmt): bool {}

/**
 * Frees all resources for the specified statement
 * @link http://www.php.net/manual/en/function.sqlsrv-free-stmt.php
 * @param resource $stmt 
 * @return bool Returns true on success or false on failure.
 */
function sqlsrv_free_stmt ($stmt): bool {}

/**
 * Retrieves metadata for the fields of a statement prepared by 
 * sqlsrv_prepare or sqlsrv_query
 * @link http://www.php.net/manual/en/function.sqlsrv-field-metadata.php
 * @param resource $stmt 
 * @return mixed Returns an array of arrays on success. Otherwise, false is returned. 
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
function sqlsrv_field_metadata ($stmt): mixed {}

/**
 * Sends data from parameter streams to the server
 * @link http://www.php.net/manual/en/function.sqlsrv-send-stream-data.php
 * @param resource $stmt 
 * @return bool Returns true if there is more data to send and false if there is not.
 */
function sqlsrv_send_stream_data ($stmt): bool {}

/**
 * {@inheritdoc}
 * @param mixed $size
 */
function SQLSRV_SQLTYPE_BINARY ($size = null) {}

/**
 * {@inheritdoc}
 * @param mixed $size
 */
function SQLSRV_SQLTYPE_CHAR ($size = null) {}

/**
 * {@inheritdoc}
 * @param mixed $precision
 * @param mixed $scale
 */
function SQLSRV_SQLTYPE_DECIMAL ($precision = null, $scale = null) {}

/**
 * {@inheritdoc}
 * @param mixed $size
 */
function SQLSRV_SQLTYPE_NCHAR ($size = null) {}

/**
 * {@inheritdoc}
 * @param mixed $precision
 * @param mixed $scale
 */
function SQLSRV_SQLTYPE_NUMERIC ($precision = null, $scale = null) {}

/**
 * {@inheritdoc}
 * @param mixed $size
 */
function SQLSRV_SQLTYPE_NVARCHAR ($size = null) {}

/**
 * {@inheritdoc}
 * @param mixed $size
 */
function SQLSRV_SQLTYPE_VARBINARY ($size = null) {}

/**
 * {@inheritdoc}
 * @param mixed $size
 */
function SQLSRV_SQLTYPE_VARCHAR ($size = null) {}


/**
 * Forces sqlsrv_errors to return errors only (no warnings) 
 * when passed as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_ERR_ERRORS', 0);

/**
 * Forces sqlsrv_errors to return warnings only (no errors) 
 * when passed as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_ERR_WARNINGS', 1);

/**
 * Forces sqlsrv_errors to return both errors and warings 
 * when passed as a parameter (the default behavior).
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_ERR_ALL', 2);

/**
 * Turns off logging of all subsystems when passed to 
 * sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_LOG_SYSTEM_OFF', 0);

/**
 * Turns on logging of initialization activity when passed to 
 * sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_LOG_SYSTEM_INIT', 1);

/**
 * Turns on logging of connection activity when passed to 
 * sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_LOG_SYSTEM_CONN', 2);

/**
 * Turns on logging of statement activity when passed to 
 * sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_LOG_SYSTEM_STMT', 4);

/**
 * Turns on logging of error function activity when passed to 
 * sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_LOG_SYSTEM_UTIL', 8);

/**
 * Turns on logging of all subsystems when passed to 
 * sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_LOG_SYSTEM_ALL', -1);

/**
 * Specifies that errors will be logged when passed to 
 * sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_LOG_SEVERITY_ERROR', 1);

/**
 * Specifies that warnings will be logged when passed to 
 * sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_LOG_SEVERITY_WARNING', 2);

/**
 * Specifies that notices will be logged when passed to 
 * sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_LOG_SEVERITY_NOTICE', 4);

/**
 * Specifies that errors, warnings, and notices will be logged 
 * when passed to sqlsrv_configure as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_LOG_SEVERITY_ALL', -1);

/**
 * Forces sqlsrv_fetch_array to return an array with 
 * numeric when passed as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_FETCH_NUMERIC', 1);

/**
 * Forces sqlsrv_fetch_array to return an associative 
 * array when passed as a parameter.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_FETCH_ASSOC', 2);

/**
 * Forces sqlsrv_fetch_array to return an array with both 
 * associative and numeric keys when passed as a parameter (the default behavior).
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_FETCH_BOTH', 3);
define ('SQLSRV_PHPTYPE_NULL', 1);

/**
 * Specifies an integer PHP data type. For usage information, see 
 * How to: Specify PHP Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_PHPTYPE_INT', 2);

/**
 * Specifies a float PHP data type. For usage information, see 
 * How to: Specify PHP Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_PHPTYPE_FLOAT', 3);

/**
 * Specifies a datetime PHP data type. For usage information, see 
 * How to: Specify PHP Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_PHPTYPE_DATETIME', 5);
define ('SQLSRV_PHPTYPE_TABLE', 7);

/**
 * Specifies that data is returned as a raw byte stream from the server without 
 * performing encoding or translation. For usage information, see 
 * How to: Specify PHP Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_ENC_BINARY', "binary");

/**
 * Data is returned in 8-bit characters as specified in the code page of the 
 * Windows locale that is set on the system. Any multi-byte characters or characters 
 * that do not map into this code page are substituted with a single byte question 
 * mark (?) character. This is the default encoding. For usage information, 
 * see How to: Specify PHP Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_ENC_CHAR', "char");

/**
 * Indicates that a column is not nullable.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_NULLABLE_NO', 0);

/**
 * Indicates that a column is nullable.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_NULLABLE_YES', 1);

/**
 * Indicates that it is not known if a column is nullable.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_NULLABLE_UNKNOWN', 2);

/**
 * Describes the bigint SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_BIGINT', -5);

/**
 * Describes the bit SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_BIT', -7);

/**
 * Describes the datetime SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_DATETIME', 25177693);

/**
 * Describes the float SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_FLOAT', 6);

/**
 * Describes the image SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_IMAGE', -4);

/**
 * Describes the int SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_INT', 4);

/**
 * Describes the money SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_MONEY', 33564163);

/**
 * Describes the ntext SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_NTEXT', -10);

/**
 * Describes the text SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_TEXT', -1);

/**
 * Describes the real SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_REAL', 7);

/**
 * Describes the smalldatetime SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_SMALLDATETIME', 8285);

/**
 * Describes the smallint SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_SMALLINT', 5);

/**
 * Describes the smallmoney SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_SMALLMONEY', 33559555);

/**
 * Describes the timestamp SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_TIMESTAMP', 4606);

/**
 * Describes the tinyint SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_TINYINT', -6);

/**
 * Describes the UDT SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_UDT', -151);
define ('SQLSRV_SQLTYPE_TABLE', -153);

/**
 * Describes the uniqueidentifier SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_UNIQUEIDENTIFIER', -11);

/**
 * Describes the XML SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_XML', -152);

/**
 * Describes the date SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_DATE', 5211);

/**
 * Describes the time SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_TIME', 58728806);

/**
 * Describes the datetimeoffset SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_DATETIMEOFFSET', 58738021);

/**
 * Describes the datetime2 SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_DATETIME2', 58734173);

/**
 * Describes the decimal SQL Server data type. This constant works like a function 
 * and accepts two parameters indicating (in order) precision and scale. For usage information, 
 * see How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_DECIMAL', 3);

/**
 * Describes the numeric SQL Server data type. This constant works like a function 
 * and accepts two parameter indicating (in order) precision and scale. For usage 
 * information, see How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_NUMERIC', 2);

/**
 * Describes the char SQL Server data type. This constant works like a function 
 * and accepts a parameter indicating the number characters. For usage information, 
 * see How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_CHAR', 1);

/**
 * Describes the nchar SQL Server data type. This constant works like a function 
 * and accepts a single parameter indicating the character count. For usage information, 
 * see How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_NCHAR', -8);

/**
 * Describes the varchar SQL Server data type. This constant works like a function 
 * and accepts a single parameter indicating the character count. For usage information, 
 * see How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_VARCHAR', 12);

/**
 * Describes the nvarchar SQL Server data type. This constant works like a function 
 * and accepts a single parameter indicating the character count. For usage 
 * information, see How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_NVARCHAR', -9);

/**
 * Describes the binary SQL Server data type. For usage information, see 
 * How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_BINARY', -2);

/**
 * Describes the varbinary SQL Server data type. This constant works like a function 
 * and accepts a single parameter indicating the byte count. For usage information, 
 * see How to: Specify SQL Types.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SQLTYPE_VARBINARY', -3);

/**
 * Indicates an input parameter when passed to sqlsrv_query 
 * or sqlsrv_prepare.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_PARAM_IN', 1);

/**
 * Indicates an output parameter when passed to sqlsrv_query or 
 * sqlsrv_prepare.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_PARAM_OUT', 4);

/**
 * Indicates a bidirectional parameter when passed to sqlsrv_query 
 * or sqlsrv_prepare.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_PARAM_INOUT', 2);

/**
 * Indicates a transaction isolation level of READ UNCOMMITTED. This value is 
 * used to set the TransactionIsolation level in the $connectionOptions array 
 * passed to sqlsrv_connect.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_TXN_READ_UNCOMMITTED', 1);

/**
 * Indicates a transaction isolation level of READ COMMITTED. This value is 
 * used to set the TransactionIsolation level in the $connectionOptions array 
 * passed to sqlsrv_connect.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_TXN_READ_COMMITTED', 2);

/**
 * Indicates a transaction isolation level of REPEATABLE READ. This value is 
 * used to set the TransactionIsolation level in the $connectionOptions array 
 * passed to sqlsrv_connect.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_TXN_REPEATABLE_READ', 4);
define ('SQLSRV_TXN_SERIALIZABLE', 8);

/**
 * Indicates a transaction isolation level of SNAPSHOT. This value is used to 
 * set the TransactionIsolation level in the $connectionOptions array passed 
 * to sqlsrv_connect.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_TXN_SNAPSHOT', 32);

/**
 * Specifies which row to select in a result set. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SCROLL_NEXT', 1);

/**
 * Specifies which row to select in a result set. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SCROLL_PRIOR', 4);

/**
 * Specifies which row to select in a result set. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SCROLL_FIRST', 2);

/**
 * Specifies which row to select in a result set. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SCROLL_LAST', 3);

/**
 * Specifies which row to select in a result set. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SCROLL_ABSOLUTE', 5);

/**
 * Specifies which row to select in a result set. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_SCROLL_RELATIVE', 6);

/**
 * Indicates a forward-only cursor. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_CURSOR_FORWARD', "forward");

/**
 * Indicates a static cursor. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_CURSOR_STATIC', "static");

/**
 * Indicates a dynamic cursor. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_CURSOR_DYNAMIC', "dynamic");

/**
 * Indicates a keyset cursor. For usage information, see 
 * Specifying a Cursor Type and Selecting Rows.
 * @link http://www.php.net/manual/en/sqlsrv.constants.php
 * @var int
 */
define ('SQLSRV_CURSOR_KEYSET', "keyset");
define ('SQLSRV_CURSOR_CLIENT_BUFFERED', "buffered");

// End of sqlsrv v.5.11.0
