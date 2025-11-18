<?php

// Start of sqlsrv v.5.11.1

/**
 * {@inheritdoc}
 * @param mixed $server_name
 * @param array $connection_info [optional]
 */
function sqlsrv_connect ($server_name = null, array $connection_info = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $conn
 */
function sqlsrv_close ($conn = null) {}

/**
 * {@inheritdoc}
 * @param mixed $conn
 */
function sqlsrv_commit ($conn = null) {}

/**
 * {@inheritdoc}
 * @param mixed $conn
 */
function sqlsrv_begin_transaction ($conn = null) {}

/**
 * {@inheritdoc}
 * @param mixed $conn
 */
function sqlsrv_rollback ($conn = null) {}

/**
 * {@inheritdoc}
 * @param mixed $errors_and_or_warnings [optional]
 */
function sqlsrv_errors ($errors_and_or_warnings = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $setting
 * @param mixed $value
 */
function sqlsrv_configure ($setting = null, $value = null) {}

/**
 * {@inheritdoc}
 * @param mixed $setting
 */
function sqlsrv_get_config ($setting = null) {}

/**
 * {@inheritdoc}
 * @param mixed $conn
 * @param mixed $tsql
 * @param mixed $params [optional]
 * @param mixed $options [optional]
 */
function sqlsrv_prepare ($conn = null, $tsql = null, $params = NULL, $options = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $stmt
 */
function sqlsrv_execute ($stmt = null) {}

/**
 * {@inheritdoc}
 * @param mixed $conn
 * @param mixed $tsql
 * @param mixed $params [optional]
 * @param mixed $options [optional]
 */
function sqlsrv_query ($conn = null, $tsql = null, $params = NULL, $options = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $stmt
 * @param mixed $row [optional]
 * @param mixed $offset [optional]
 */
function sqlsrv_fetch ($stmt = null, $row = NULL, $offset = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $stmt
 * @param mixed $field_index
 * @param mixed $get_as_type [optional]
 */
function sqlsrv_get_field ($stmt = null, $field_index = null, $get_as_type = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $stmt
 * @param mixed $fetch_type [optional]
 * @param mixed $row [optional]
 * @param mixed $offset [optional]
 */
function sqlsrv_fetch_array ($stmt = null, $fetch_type = NULL, $row = NULL, $offset = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $stmt
 * @param mixed $class_name [optional]
 * @param mixed $ctor_params [optional]
 * @param mixed $row [optional]
 * @param mixed $offset [optional]
 */
function sqlsrv_fetch_object ($stmt = null, $class_name = NULL, $ctor_params = NULL, $row = NULL, $offset = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $stmt
 */
function sqlsrv_has_rows ($stmt = null) {}

/**
 * {@inheritdoc}
 * @param mixed $stmt
 */
function sqlsrv_num_fields ($stmt = null) {}

/**
 * {@inheritdoc}
 * @param mixed $stmt
 */
function sqlsrv_next_result ($stmt = null) {}

/**
 * {@inheritdoc}
 * @param mixed $stmt
 */
function sqlsrv_num_rows ($stmt = null) {}

/**
 * {@inheritdoc}
 * @param mixed $stmt
 */
function sqlsrv_rows_affected ($stmt = null) {}

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
 * {@inheritdoc}
 * @param mixed $conn
 */
function sqlsrv_client_info ($conn = null) {}

/**
 * {@inheritdoc}
 * @param mixed $stmt
 */
function sqlsrv_server_info ($stmt = null) {}

/**
 * {@inheritdoc}
 * @param mixed $stmt
 */
function sqlsrv_cancel ($stmt = null) {}

/**
 * {@inheritdoc}
 * @param mixed $stmt
 */
function sqlsrv_free_stmt ($stmt = null) {}

/**
 * {@inheritdoc}
 * @param mixed $stmt
 */
function sqlsrv_field_metadata ($stmt = null) {}

/**
 * {@inheritdoc}
 * @param mixed $stmt
 */
function sqlsrv_send_stream_data ($stmt = null) {}

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

define ('SQLSRV_ERR_ERRORS', 0);
define ('SQLSRV_ERR_WARNINGS', 1);
define ('SQLSRV_ERR_ALL', 2);
define ('SQLSRV_LOG_SYSTEM_OFF', 0);
define ('SQLSRV_LOG_SYSTEM_INIT', 1);
define ('SQLSRV_LOG_SYSTEM_CONN', 2);
define ('SQLSRV_LOG_SYSTEM_STMT', 4);
define ('SQLSRV_LOG_SYSTEM_UTIL', 8);
define ('SQLSRV_LOG_SYSTEM_ALL', -1);
define ('SQLSRV_LOG_SEVERITY_ERROR', 1);
define ('SQLSRV_LOG_SEVERITY_WARNING', 2);
define ('SQLSRV_LOG_SEVERITY_NOTICE', 4);
define ('SQLSRV_LOG_SEVERITY_ALL', -1);
define ('SQLSRV_FETCH_NUMERIC', 1);
define ('SQLSRV_FETCH_ASSOC', 2);
define ('SQLSRV_FETCH_BOTH', 3);
define ('SQLSRV_PHPTYPE_NULL', 1);
define ('SQLSRV_PHPTYPE_INT', 2);
define ('SQLSRV_PHPTYPE_FLOAT', 3);
define ('SQLSRV_PHPTYPE_DATETIME', 5);
define ('SQLSRV_PHPTYPE_TABLE', 7);
define ('SQLSRV_ENC_BINARY', "binary");
define ('SQLSRV_ENC_CHAR', "char");
define ('SQLSRV_NULLABLE_NO', 0);
define ('SQLSRV_NULLABLE_YES', 1);
define ('SQLSRV_NULLABLE_UNKNOWN', 2);
define ('SQLSRV_SQLTYPE_BIGINT', -5);
define ('SQLSRV_SQLTYPE_BIT', -7);
define ('SQLSRV_SQLTYPE_DATETIME', 25177693);
define ('SQLSRV_SQLTYPE_FLOAT', 6);
define ('SQLSRV_SQLTYPE_IMAGE', -4);
define ('SQLSRV_SQLTYPE_INT', 4);
define ('SQLSRV_SQLTYPE_MONEY', 33564163);
define ('SQLSRV_SQLTYPE_NTEXT', -10);
define ('SQLSRV_SQLTYPE_TEXT', -1);
define ('SQLSRV_SQLTYPE_REAL', 7);
define ('SQLSRV_SQLTYPE_SMALLDATETIME', 8285);
define ('SQLSRV_SQLTYPE_SMALLINT', 5);
define ('SQLSRV_SQLTYPE_SMALLMONEY', 33559555);
define ('SQLSRV_SQLTYPE_TIMESTAMP', 4606);
define ('SQLSRV_SQLTYPE_TINYINT', -6);
define ('SQLSRV_SQLTYPE_UDT', -151);
define ('SQLSRV_SQLTYPE_TABLE', -153);
define ('SQLSRV_SQLTYPE_UNIQUEIDENTIFIER', -11);
define ('SQLSRV_SQLTYPE_XML', -152);
define ('SQLSRV_SQLTYPE_DATE', 5211);
define ('SQLSRV_SQLTYPE_TIME', 58728806);
define ('SQLSRV_SQLTYPE_DATETIMEOFFSET', 58738021);
define ('SQLSRV_SQLTYPE_DATETIME2', 58734173);
define ('SQLSRV_SQLTYPE_DECIMAL', 3);
define ('SQLSRV_SQLTYPE_NUMERIC', 2);
define ('SQLSRV_SQLTYPE_CHAR', 1);
define ('SQLSRV_SQLTYPE_NCHAR', -8);
define ('SQLSRV_SQLTYPE_VARCHAR', 12);
define ('SQLSRV_SQLTYPE_NVARCHAR', -9);
define ('SQLSRV_SQLTYPE_BINARY', -2);
define ('SQLSRV_SQLTYPE_VARBINARY', -3);
define ('SQLSRV_PARAM_IN', 1);
define ('SQLSRV_PARAM_OUT', 4);
define ('SQLSRV_PARAM_INOUT', 2);
define ('SQLSRV_TXN_READ_UNCOMMITTED', 1);
define ('SQLSRV_TXN_READ_COMMITTED', 2);
define ('SQLSRV_TXN_REPEATABLE_READ', 4);
define ('SQLSRV_TXN_SERIALIZABLE', 8);
define ('SQLSRV_TXN_SNAPSHOT', 32);
define ('SQLSRV_SCROLL_NEXT', 1);
define ('SQLSRV_SCROLL_PRIOR', 4);
define ('SQLSRV_SCROLL_FIRST', 2);
define ('SQLSRV_SCROLL_LAST', 3);
define ('SQLSRV_SCROLL_ABSOLUTE', 5);
define ('SQLSRV_SCROLL_RELATIVE', 6);
define ('SQLSRV_CURSOR_FORWARD', "forward");
define ('SQLSRV_CURSOR_STATIC', "static");
define ('SQLSRV_CURSOR_DYNAMIC', "dynamic");
define ('SQLSRV_CURSOR_KEYSET', "keyset");
define ('SQLSRV_CURSOR_CLIENT_BUFFERED', "buffered");

// End of sqlsrv v.5.11.1
