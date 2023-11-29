<?php

// Start of pgsql v.8.3.0

namespace PgSql {

final class Connection  {
}

final class Result  {
}

final class Lob  {
}


}


namespace {

/**
 * {@inheritdoc}
 * @param string $connection_string
 * @param int $flags [optional]
 */
function pg_connect (string $connection_string, int $flags = 0): PgSql\Connection|false {}

/**
 * {@inheritdoc}
 * @param string $connection_string
 * @param int $flags [optional]
 */
function pg_pconnect (string $connection_string, int $flags = 0): PgSql\Connection|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 */
function pg_connect_poll (PgSql\Connection $connection): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection|null $connection [optional]
 */
function pg_close (?PgSql\Connection $connection = NULL): true {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection|null $connection [optional]
 */
function pg_dbname (?PgSql\Connection $connection = NULL): string {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection|null $connection [optional]
 */
function pg_last_error (?PgSql\Connection $connection = NULL): string {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection|null $connection [optional]
 * @deprecated 
 */
function pg_errormessage (?PgSql\Connection $connection = NULL): string {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection|null $connection [optional]
 */
function pg_options (?PgSql\Connection $connection = NULL): string {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection|null $connection [optional]
 */
function pg_port (?PgSql\Connection $connection = NULL): string {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection|null $connection [optional]
 */
function pg_tty (?PgSql\Connection $connection = NULL): string {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection|null $connection [optional]
 */
function pg_host (?PgSql\Connection $connection = NULL): string {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection|null $connection [optional]
 */
function pg_version (?PgSql\Connection $connection = NULL): array {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param string $name [optional]
 */
function pg_parameter_status ($connection = null, string $name = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection|null $connection [optional]
 */
function pg_ping (?PgSql\Connection $connection = NULL): bool {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param string $query [optional]
 */
function pg_query ($connection = null, string $query = NULL): PgSql\Result|false {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param string $query [optional]
 */
function pg_exec ($connection = null, string $query = NULL): PgSql\Result|false {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param mixed $query
 * @param array $params [optional]
 */
function pg_query_params ($connection = null, $query = null, array $params = NULL): PgSql\Result|false {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param string $statement_name
 * @param string $query [optional]
 */
function pg_prepare ($connection = null, string $statement_name, string $query = NULL): PgSql\Result|false {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param mixed $statement_name
 * @param array $params [optional]
 */
function pg_execute ($connection = null, $statement_name = null, array $params = NULL): PgSql\Result|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 */
function pg_num_rows (PgSql\Result $result): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @deprecated 
 */
function pg_numrows (PgSql\Result $result): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 */
function pg_num_fields (PgSql\Result $result): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @deprecated 
 */
function pg_numfields (PgSql\Result $result): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 */
function pg_affected_rows (PgSql\Result $result): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @deprecated 
 */
function pg_cmdtuples (PgSql\Result $result): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 * @param int $mode [optional]
 */
function pg_last_notice (PgSql\Connection $connection, int $mode = 1): array|string|bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int $field
 * @param bool $oid_only [optional]
 */
function pg_field_table (PgSql\Result $result, int $field, bool $oid_only = false): string|int|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int $field
 */
function pg_field_name (PgSql\Result $result, int $field): string {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int $field
 * @deprecated 
 */
function pg_fieldname (PgSql\Result $result, int $field): string {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int $field
 */
function pg_field_size (PgSql\Result $result, int $field): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int $field
 * @deprecated 
 */
function pg_fieldsize (PgSql\Result $result, int $field): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int $field
 */
function pg_field_type (PgSql\Result $result, int $field): string {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int $field
 * @deprecated 
 */
function pg_fieldtype (PgSql\Result $result, int $field): string {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int $field
 */
function pg_field_type_oid (PgSql\Result $result, int $field): string|int {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param string $field
 */
function pg_field_num (PgSql\Result $result, string $field): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param string $field
 * @deprecated 
 */
function pg_fieldnum (PgSql\Result $result, string $field): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param mixed $row
 * @param string|int $field [optional]
 */
function pg_fetch_result (PgSql\Result $result, $row = null, string|int $field = NULL): string|false|null {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param mixed $row
 * @param string|int $field [optional]
 * @deprecated 
 */
function pg_result (PgSql\Result $result, $row = null, string|int $field = NULL): string|false|null {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int|null $row [optional]
 * @param int $mode [optional]
 */
function pg_fetch_row (PgSql\Result $result, ?int $row = NULL, int $mode = 2): array|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int|null $row [optional]
 */
function pg_fetch_assoc (PgSql\Result $result, ?int $row = NULL): array|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int|null $row [optional]
 * @param int $mode [optional]
 */
function pg_fetch_array (PgSql\Result $result, ?int $row = NULL, int $mode = 3): array|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int|null $row [optional]
 * @param string $class [optional]
 * @param array $constructor_args [optional]
 */
function pg_fetch_object (PgSql\Result $result, ?int $row = NULL, string $class = 'stdClass', array $constructor_args = array (
)): object|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int $mode [optional]
 */
function pg_fetch_all (PgSql\Result $result, int $mode = 1): array {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int $field [optional]
 */
function pg_fetch_all_columns (PgSql\Result $result, int $field = 0): array {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int $row
 */
function pg_result_seek (PgSql\Result $result, int $row): bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param mixed $row
 * @param string|int $field [optional]
 */
function pg_field_prtlen (PgSql\Result $result, $row = null, string|int $field = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param mixed $row
 * @param string|int $field [optional]
 * @deprecated 
 */
function pg_fieldprtlen (PgSql\Result $result, $row = null, string|int $field = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param mixed $row
 * @param string|int $field [optional]
 */
function pg_field_is_null (PgSql\Result $result, $row = null, string|int $field = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param mixed $row
 * @param string|int $field [optional]
 * @deprecated 
 */
function pg_fieldisnull (PgSql\Result $result, $row = null, string|int $field = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 */
function pg_free_result (PgSql\Result $result): bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @deprecated 
 */
function pg_freeresult (PgSql\Result $result): bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 */
function pg_last_oid (PgSql\Result $result): string|int|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @deprecated 
 */
function pg_getlastoid (PgSql\Result $result): string|int|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param string $mode [optional]
 * @param PgSql\Connection|null $connection [optional]
 * @param int $trace_mode [optional]
 */
function pg_trace (string $filename, string $mode = 'w', ?PgSql\Connection $connection = NULL, int $trace_mode = 0): bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection|null $connection [optional]
 */
function pg_untrace (?PgSql\Connection $connection = NULL): true {}

/**
 * {@inheritdoc}
 * @param mixed $connection [optional]
 * @param mixed $oid [optional]
 */
function pg_lo_create ($connection = NULL, $oid = NULL): string|int|false {}

/**
 * {@inheritdoc}
 * @param mixed $connection [optional]
 * @param mixed $oid [optional]
 * @deprecated 
 */
function pg_locreate ($connection = NULL, $oid = NULL): string|int|false {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param mixed $oid [optional]
 */
function pg_lo_unlink ($connection = null, $oid = NULL): bool {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param mixed $oid [optional]
 * @deprecated 
 */
function pg_lounlink ($connection = null, $oid = NULL): bool {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param mixed $oid [optional]
 * @param string $mode [optional]
 */
function pg_lo_open ($connection = null, $oid = NULL, string $mode = NULL): PgSql\Lob|false {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param mixed $oid [optional]
 * @param string $mode [optional]
 * @deprecated 
 */
function pg_loopen ($connection = null, $oid = NULL, string $mode = NULL): PgSql\Lob|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Lob $lob
 */
function pg_lo_close (PgSql\Lob $lob): bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Lob $lob
 * @deprecated 
 */
function pg_loclose (PgSql\Lob $lob): bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Lob $lob
 * @param int $length [optional]
 */
function pg_lo_read (PgSql\Lob $lob, int $length = 8192): string|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Lob $lob
 * @param int $length [optional]
 * @deprecated 
 */
function pg_loread (PgSql\Lob $lob, int $length = 8192): string|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Lob $lob
 * @param string $data
 * @param int|null $length [optional]
 */
function pg_lo_write (PgSql\Lob $lob, string $data, ?int $length = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Lob $lob
 * @param string $data
 * @param int|null $length [optional]
 * @deprecated 
 */
function pg_lowrite (PgSql\Lob $lob, string $data, ?int $length = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Lob $lob
 */
function pg_lo_read_all (PgSql\Lob $lob): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Lob $lob
 * @deprecated 
 */
function pg_loreadall (PgSql\Lob $lob): int {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param mixed $filename [optional]
 * @param mixed $oid [optional]
 */
function pg_lo_import ($connection = null, $filename = NULL, $oid = NULL): string|int|false {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param mixed $filename [optional]
 * @param mixed $oid [optional]
 * @deprecated 
 */
function pg_loimport ($connection = null, $filename = NULL, $oid = NULL): string|int|false {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param mixed $oid [optional]
 * @param mixed $filename [optional]
 */
function pg_lo_export ($connection = null, $oid = NULL, $filename = NULL): bool {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param mixed $oid [optional]
 * @param mixed $filename [optional]
 * @deprecated 
 */
function pg_loexport ($connection = null, $oid = NULL, $filename = NULL): bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Lob $lob
 * @param int $offset
 * @param int $whence [optional]
 */
function pg_lo_seek (PgSql\Lob $lob, int $offset, int $whence = 1): bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Lob $lob
 */
function pg_lo_tell (PgSql\Lob $lob): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Lob $lob
 * @param int $size
 */
function pg_lo_truncate (PgSql\Lob $lob, int $size): bool {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param int $verbosity [optional]
 */
function pg_set_error_verbosity ($connection = null, int $verbosity = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param string $encoding [optional]
 */
function pg_set_client_encoding ($connection = null, string $encoding = NULL): int {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param string $encoding [optional]
 * @deprecated 
 */
function pg_setclientencoding ($connection = null, string $encoding = NULL): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection|null $connection [optional]
 */
function pg_client_encoding (?PgSql\Connection $connection = NULL): string {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection|null $connection [optional]
 * @deprecated 
 */
function pg_clientencoding (?PgSql\Connection $connection = NULL): string {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection|null $connection [optional]
 */
function pg_end_copy (?PgSql\Connection $connection = NULL): bool {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param string $query [optional]
 */
function pg_put_line ($connection = null, string $query = NULL): bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 * @param string $table_name
 * @param string $separator [optional]
 * @param string $null_as [optional]
 */
function pg_copy_to (PgSql\Connection $connection, string $table_name, string $separator = '	', string $null_as = '\\\\N'): array|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 * @param string $table_name
 * @param array $rows
 * @param string $separator [optional]
 * @param string $null_as [optional]
 */
function pg_copy_from (PgSql\Connection $connection, string $table_name, array $rows, string $separator = '	', string $null_as = '\\\\N'): bool {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param string $string [optional]
 */
function pg_escape_string ($connection = null, string $string = NULL): string {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param string $string [optional]
 */
function pg_escape_bytea ($connection = null, string $string = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function pg_unescape_bytea (string $string): string {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param string $string [optional]
 */
function pg_escape_literal ($connection = null, string $string = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param string $string [optional]
 */
function pg_escape_identifier ($connection = null, string $string = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 */
function pg_result_error (PgSql\Result $result): string|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int $field_code
 */
function pg_result_error_field (PgSql\Result $result, int $field_code): string|false|null {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 */
function pg_connection_status (PgSql\Connection $connection): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 */
function pg_transaction_status (PgSql\Connection $connection): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 */
function pg_connection_reset (PgSql\Connection $connection): bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 */
function pg_cancel_query (PgSql\Connection $connection): bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 */
function pg_connection_busy (PgSql\Connection $connection): bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 * @param string $query
 */
function pg_send_query (PgSql\Connection $connection, string $query): int|bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 * @param string $query
 * @param array $params
 */
function pg_send_query_params (PgSql\Connection $connection, string $query, array $params): int|bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 * @param string $statement_name
 * @param string $query
 */
function pg_send_prepare (PgSql\Connection $connection, string $statement_name, string $query): int|bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 * @param string $statement_name
 * @param array $params
 */
function pg_send_execute (PgSql\Connection $connection, string $statement_name, array $params): int|bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 */
function pg_get_result (PgSql\Connection $connection): PgSql\Result|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Result $result
 * @param int $mode [optional]
 */
function pg_result_status (PgSql\Result $result, int $mode = 1): string|int {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 * @param int $mode [optional]
 */
function pg_get_notify (PgSql\Connection $connection, int $mode = 1): array|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 */
function pg_get_pid (PgSql\Connection $connection): int {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 */
function pg_socket (PgSql\Connection $connection) {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 */
function pg_consume_input (PgSql\Connection $connection): bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 */
function pg_flush (PgSql\Connection $connection): int|bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 * @param string $table_name
 * @param bool $extended [optional]
 */
function pg_meta_data (PgSql\Connection $connection, string $table_name, bool $extended = false): array|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 * @param string $table_name
 * @param array $values
 * @param int $flags [optional]
 */
function pg_convert (PgSql\Connection $connection, string $table_name, array $values, int $flags = 0): array|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 * @param string $table_name
 * @param array $values
 * @param int $flags [optional]
 */
function pg_insert (PgSql\Connection $connection, string $table_name, array $values, int $flags = 512): PgSql\Result|string|bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 * @param string $table_name
 * @param array $values
 * @param array $conditions
 * @param int $flags [optional]
 */
function pg_update (PgSql\Connection $connection, string $table_name, array $values, array $conditions, int $flags = 512): string|bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 * @param string $table_name
 * @param array $conditions
 * @param int $flags [optional]
 */
function pg_delete (PgSql\Connection $connection, string $table_name, array $conditions, int $flags = 512): string|bool {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 * @param string $table_name
 * @param array $conditions
 * @param int $flags [optional]
 * @param int $mode [optional]
 */
function pg_select (PgSql\Connection $connection, string $table_name, array $conditions, int $flags = 512, int $mode = 1): array|string|false {}

/**
 * {@inheritdoc}
 * @param PgSql\Connection $connection
 * @param int $visibility
 */
function pg_set_error_context_visibility (PgSql\Connection $connection, int $visibility): int {}

define ('PGSQL_LIBPQ_VERSION', 16.1);
define ('PGSQL_LIBPQ_VERSION_STR', 16.1);
define ('PGSQL_CONNECT_FORCE_NEW', 2);
define ('PGSQL_CONNECT_ASYNC', 4);
define ('PGSQL_ASSOC', 1);
define ('PGSQL_NUM', 2);
define ('PGSQL_BOTH', 3);
define ('PGSQL_NOTICE_LAST', 1);
define ('PGSQL_NOTICE_ALL', 2);
define ('PGSQL_NOTICE_CLEAR', 3);
define ('PGSQL_CONNECTION_BAD', 1);
define ('PGSQL_CONNECTION_OK', 0);
define ('PGSQL_CONNECTION_STARTED', 2);
define ('PGSQL_CONNECTION_MADE', 3);
define ('PGSQL_CONNECTION_AWAITING_RESPONSE', 4);
define ('PGSQL_CONNECTION_AUTH_OK', 5);
define ('PGSQL_CONNECTION_SETENV', 6);
define ('PGSQL_POLLING_FAILED', 0);
define ('PGSQL_POLLING_READING', 1);
define ('PGSQL_POLLING_WRITING', 2);
define ('PGSQL_POLLING_OK', 3);
define ('PGSQL_POLLING_ACTIVE', 4);
define ('PGSQL_TRANSACTION_IDLE', 0);
define ('PGSQL_TRANSACTION_ACTIVE', 1);
define ('PGSQL_TRANSACTION_INTRANS', 2);
define ('PGSQL_TRANSACTION_INERROR', 3);
define ('PGSQL_TRANSACTION_UNKNOWN', 4);
define ('PGSQL_ERRORS_TERSE', 0);
define ('PGSQL_ERRORS_DEFAULT', 1);
define ('PGSQL_ERRORS_VERBOSE', 2);
define ('PGSQL_ERRORS_SQLSTATE', 0);
define ('PGSQL_SEEK_SET', 0);
define ('PGSQL_SEEK_CUR', 1);
define ('PGSQL_SEEK_END', 2);
define ('PGSQL_STATUS_LONG', 1);
define ('PGSQL_STATUS_STRING', 2);
define ('PGSQL_EMPTY_QUERY', 0);
define ('PGSQL_COMMAND_OK', 1);
define ('PGSQL_TUPLES_OK', 2);
define ('PGSQL_COPY_OUT', 3);
define ('PGSQL_COPY_IN', 4);
define ('PGSQL_BAD_RESPONSE', 5);
define ('PGSQL_NONFATAL_ERROR', 6);
define ('PGSQL_FATAL_ERROR', 7);
define ('PGSQL_DIAG_SEVERITY', 83);
define ('PGSQL_DIAG_SQLSTATE', 67);
define ('PGSQL_DIAG_MESSAGE_PRIMARY', 77);
define ('PGSQL_DIAG_MESSAGE_DETAIL', 68);
define ('PGSQL_DIAG_MESSAGE_HINT', 72);
define ('PGSQL_DIAG_STATEMENT_POSITION', 80);
define ('PGSQL_DIAG_INTERNAL_POSITION', 112);
define ('PGSQL_DIAG_INTERNAL_QUERY', 113);
define ('PGSQL_DIAG_CONTEXT', 87);
define ('PGSQL_DIAG_SOURCE_FILE', 70);
define ('PGSQL_DIAG_SOURCE_LINE', 76);
define ('PGSQL_DIAG_SOURCE_FUNCTION', 82);
define ('PGSQL_DIAG_SCHEMA_NAME', 115);
define ('PGSQL_DIAG_TABLE_NAME', 116);
define ('PGSQL_DIAG_COLUMN_NAME', 99);
define ('PGSQL_DIAG_DATATYPE_NAME', 100);
define ('PGSQL_DIAG_CONSTRAINT_NAME', 110);
define ('PGSQL_DIAG_SEVERITY_NONLOCALIZED', 86);
define ('PGSQL_CONV_IGNORE_DEFAULT', 2);
define ('PGSQL_CONV_FORCE_NULL', 4);
define ('PGSQL_CONV_IGNORE_NOT_NULL', 8);
define ('PGSQL_DML_ESCAPE', 4096);
define ('PGSQL_DML_NO_CONV', 256);
define ('PGSQL_DML_EXEC', 512);
define ('PGSQL_DML_ASYNC', 1024);
define ('PGSQL_DML_STRING', 2048);
define ('PGSQL_TRACE_REGRESS_MODE', 2);
define ('PGSQL_SHOW_CONTEXT_NEVER', 0);
define ('PGSQL_SHOW_CONTEXT_ERRORS', 1);
define ('PGSQL_SHOW_CONTEXT_ALWAYS', 2);


}

// End of pgsql v.8.3.0
