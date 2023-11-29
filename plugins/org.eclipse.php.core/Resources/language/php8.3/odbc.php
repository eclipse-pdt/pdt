<?php

// Start of odbc v.8.3.0

/**
 * {@inheritdoc}
 */
function odbc_close_all (): void {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param int $mode
 */
function odbc_binmode ($statement = null, int $mode): bool {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param int $length
 */
function odbc_longreadlen ($statement = null, int $length): bool {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 * @param string $query
 */
function odbc_prepare ($odbc = null, string $query) {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param array $params [optional]
 */
function odbc_execute ($statement = null, array $params = array (
)): bool {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 */
function odbc_cursor ($statement = null): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 * @param int $fetch_type
 */
function odbc_data_source ($odbc = null, int $fetch_type): array|false|null {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 * @param string $query
 */
function odbc_exec ($odbc = null, string $query) {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 * @param string $query
 */
function odbc_do ($odbc = null, string $query) {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param int $row [optional]
 */
function odbc_fetch_object ($statement = null, int $row = -1): stdClass|false {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param int $row [optional]
 */
function odbc_fetch_array ($statement = null, int $row = -1): array|false {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param mixed $array
 * @param int $row [optional]
 */
function odbc_fetch_into ($statement = null, &$array = null, int $row = 0): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param int|null $row [optional]
 */
function odbc_fetch_row ($statement = null, ?int $row = NULL): bool {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param string|int $field
 */
function odbc_result ($statement = null, string|int $field): string|bool|null {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param string $format [optional]
 * @deprecated 
 */
function odbc_result_all ($statement = null, string $format = ''): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 */
function odbc_free_result ($statement = null): bool {}

/**
 * {@inheritdoc}
 * @param string $dsn
 * @param string $user
 * @param string $password
 * @param int $cursor_option [optional]
 */
function odbc_connect (string $dsn, string $user, string $password, int $cursor_option = 2) {}

/**
 * {@inheritdoc}
 * @param string $dsn
 * @param string $user
 * @param string $password
 * @param int $cursor_option [optional]
 */
function odbc_pconnect (string $dsn, string $user, string $password, int $cursor_option = 2) {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 */
function odbc_close ($odbc = null): void {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 */
function odbc_num_rows ($statement = null): int {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 */
function odbc_next_result ($statement = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 */
function odbc_num_fields ($statement = null): int {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param int $field
 */
function odbc_field_name ($statement = null, int $field): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param int $field
 */
function odbc_field_type ($statement = null, int $field): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param int $field
 */
function odbc_field_len ($statement = null, int $field): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param int $field
 */
function odbc_field_precision ($statement = null, int $field): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param int $field
 */
function odbc_field_scale ($statement = null, int $field): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param string $field
 */
function odbc_field_num ($statement = null, string $field): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 * @param bool|null $enable [optional]
 */
function odbc_autocommit ($odbc = null, ?bool $enable = NULL): int|bool {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 */
function odbc_commit ($odbc = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 */
function odbc_rollback ($odbc = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $odbc [optional]
 */
function odbc_error ($odbc = NULL): string {}

/**
 * {@inheritdoc}
 * @param mixed $odbc [optional]
 */
function odbc_errormsg ($odbc = NULL): string {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 * @param int $which
 * @param int $option
 * @param int $value
 */
function odbc_setoption ($odbc = null, int $which, int $option, int $value): bool {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 * @param string|null $catalog [optional]
 * @param string|null $schema [optional]
 * @param string|null $table [optional]
 * @param string|null $types [optional]
 */
function odbc_tables ($odbc = null, ?string $catalog = NULL, ?string $schema = NULL, ?string $table = NULL, ?string $types = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 * @param string|null $catalog [optional]
 * @param string|null $schema [optional]
 * @param string|null $table [optional]
 * @param string|null $column [optional]
 */
function odbc_columns ($odbc = null, ?string $catalog = NULL, ?string $schema = NULL, ?string $table = NULL, ?string $column = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 * @param int $data_type [optional]
 */
function odbc_gettypeinfo ($odbc = null, int $data_type = 0) {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 * @param string|null $catalog
 * @param string $schema
 * @param string $table
 */
function odbc_primarykeys ($odbc = null, ?string $catalog = null, string $schema, string $table) {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 * @param string|null $catalog [optional]
 * @param string|null $schema [optional]
 * @param string|null $procedure [optional]
 * @param string|null $column [optional]
 */
function odbc_procedurecolumns ($odbc = null, ?string $catalog = NULL, ?string $schema = NULL, ?string $procedure = NULL, ?string $column = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 * @param string|null $catalog [optional]
 * @param string|null $schema [optional]
 * @param string|null $procedure [optional]
 */
function odbc_procedures ($odbc = null, ?string $catalog = NULL, ?string $schema = NULL, ?string $procedure = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 * @param string|null $pk_catalog
 * @param string $pk_schema
 * @param string $pk_table
 * @param string $fk_catalog
 * @param string $fk_schema
 * @param string $fk_table
 */
function odbc_foreignkeys ($odbc = null, ?string $pk_catalog = null, string $pk_schema, string $pk_table, string $fk_catalog, string $fk_schema, string $fk_table) {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 * @param int $type
 * @param string|null $catalog
 * @param string $schema
 * @param string $table
 * @param int $scope
 * @param int $nullable
 */
function odbc_specialcolumns ($odbc = null, int $type, ?string $catalog = null, string $schema, string $table, int $scope, int $nullable) {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 * @param string|null $catalog
 * @param string $schema
 * @param string $table
 * @param int $unique
 * @param int $accuracy
 */
function odbc_statistics ($odbc = null, ?string $catalog = null, string $schema, string $table, int $unique, int $accuracy) {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 * @param string|null $catalog
 * @param string $schema
 * @param string $table
 */
function odbc_tableprivileges ($odbc = null, ?string $catalog = null, string $schema, string $table) {}

/**
 * {@inheritdoc}
 * @param mixed $odbc
 * @param string|null $catalog
 * @param string $schema
 * @param string $table
 * @param string $column
 */
function odbc_columnprivileges ($odbc = null, ?string $catalog = null, string $schema, string $table, string $column) {}

/**
 * {@inheritdoc}
 * @param string $str
 */
function odbc_connection_string_is_quoted (string $str): bool {}

/**
 * {@inheritdoc}
 * @param string $str
 */
function odbc_connection_string_should_quote (string $str): bool {}

/**
 * {@inheritdoc}
 * @param string $str
 */
function odbc_connection_string_quote (string $str): string {}

define ('ODBC_TYPE', "unixODBC");
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
define ('SQL_TYPE_DATE', 91);
define ('SQL_TYPE_TIME', 92);
define ('SQL_TYPE_TIMESTAMP', 93);
define ('SQL_WCHAR', -8);
define ('SQL_WVARCHAR', -9);
define ('SQL_WLONGVARCHAR', -10);
define ('SQL_BEST_ROWID', 1);
define ('SQL_ROWVER', 2);
define ('SQL_SCOPE_CURROW', 0);
define ('SQL_SCOPE_TRANSACTION', 1);
define ('SQL_SCOPE_SESSION', 2);
define ('SQL_NO_NULLS', 0);
define ('SQL_NULLABLE', 1);
define ('SQL_INDEX_UNIQUE', 0);
define ('SQL_INDEX_ALL', 1);
define ('SQL_ENSURE', 1);
define ('SQL_QUICK', 0);

// End of odbc v.8.3.0
