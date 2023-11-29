<?php

// Start of mysqli v.8.3.0

final class mysqli_sql_exception extends RuntimeException implements Stringable, Throwable {

	protected string $sqlstate;

	/**
	 * {@inheritdoc}
	 */
	public function getSqlState (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

final class mysqli_driver  {

	public string $client_info;

	public int $client_version;

	public int $driver_version;

	public int $report_mode;
}

class mysqli  {

	public string|int $affected_rows;

	public string $client_info;

	public int $client_version;

	public int $connect_errno;

	public ?string $connect_error;

	public int $errno;

	public string $error;

	public array $error_list;

	public int $field_count;

	public string $host_info;

	public ?string $info;

	public string|int $insert_id;

	public string $server_info;

	public int $server_version;

	public string $sqlstate;

	public int $protocol_version;

	public int $thread_id;

	public int $warning_count;

	/**
	 * {@inheritdoc}
	 * @param string|null $hostname [optional]
	 * @param string|null $username [optional]
	 * @param string|null $password [optional]
	 * @param string|null $database [optional]
	 * @param int|null $port [optional]
	 * @param string|null $socket [optional]
	 */
	public function __construct (?string $hostname = NULL, ?string $username = NULL, ?string $password = NULL, ?string $database = NULL, ?int $port = NULL, ?string $socket = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param bool $enable
	 */
	public function autocommit (bool $enable) {}

	/**
	 * {@inheritdoc}
	 * @param int $flags [optional]
	 * @param string|null $name [optional]
	 */
	public function begin_transaction (int $flags = 0, ?string $name = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $username
	 * @param string $password
	 * @param string|null $database
	 */
	public function change_user (string $username, string $password, ?string $database = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function character_set_name () {}

	/**
	 * {@inheritdoc}
	 */
	public function close () {}

	/**
	 * {@inheritdoc}
	 * @param int $flags [optional]
	 * @param string|null $name [optional]
	 */
	public function commit (int $flags = 0, ?string $name = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $hostname [optional]
	 * @param string|null $username [optional]
	 * @param string|null $password [optional]
	 * @param string|null $database [optional]
	 * @param int|null $port [optional]
	 * @param string|null $socket [optional]
	 */
	public function connect (?string $hostname = NULL, ?string $username = NULL, ?string $password = NULL, ?string $database = NULL, ?int $port = NULL, ?string $socket = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function dump_debug_info () {}

	/**
	 * {@inheritdoc}
	 * @param string $options
	 */
	public function debug (string $options) {}

	/**
	 * {@inheritdoc}
	 */
	public function get_charset () {}

	/**
	 * {@inheritdoc}
	 * @param string $query
	 * @param array|null $params [optional]
	 */
	public function execute_query (string $query, ?array $params = NULL): mysqli_result|bool {}

	/**
	 * {@inheritdoc}
	 * @deprecated 
	 */
	public function get_client_info () {}

	/**
	 * {@inheritdoc}
	 */
	public function get_connection_stats () {}

	/**
	 * {@inheritdoc}
	 */
	public function get_server_info () {}

	/**
	 * {@inheritdoc}
	 */
	public function get_warnings () {}

	/**
	 * {@inheritdoc}
	 * @deprecated 
	 */
	public function init () {}

	/**
	 * {@inheritdoc}
	 * @param int $process_id
	 */
	public function kill (int $process_id) {}

	/**
	 * {@inheritdoc}
	 * @param string $query
	 */
	public function multi_query (string $query) {}

	/**
	 * {@inheritdoc}
	 */
	public function more_results () {}

	/**
	 * {@inheritdoc}
	 */
	public function next_result () {}

	/**
	 * {@inheritdoc}
	 */
	public function ping () {}

	/**
	 * {@inheritdoc}
	 * @param array|null $read
	 * @param array|null $error
	 * @param array $reject
	 * @param int $seconds
	 * @param int $microseconds [optional]
	 */
	public static function poll (?array &$read = null, ?array &$error = null, array &$reject, int $seconds, int $microseconds = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $query
	 */
	public function prepare (string $query) {}

	/**
	 * {@inheritdoc}
	 * @param string $query
	 * @param int $result_mode [optional]
	 */
	public function query (string $query, int $result_mode = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $hostname [optional]
	 * @param string|null $username [optional]
	 * @param string|null $password [optional]
	 * @param string|null $database [optional]
	 * @param int|null $port [optional]
	 * @param string|null $socket [optional]
	 * @param int $flags [optional]
	 */
	public function real_connect (?string $hostname = NULL, ?string $username = NULL, ?string $password = NULL, ?string $database = NULL, ?int $port = NULL, ?string $socket = NULL, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 */
	public function real_escape_string (string $string) {}

	/**
	 * {@inheritdoc}
	 */
	public function reap_async_query () {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 */
	public function escape_string (string $string) {}

	/**
	 * {@inheritdoc}
	 * @param string $query
	 */
	public function real_query (string $query) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function release_savepoint (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param int $flags [optional]
	 * @param string|null $name [optional]
	 */
	public function rollback (int $flags = 0, ?string $name = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function savepoint (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param string $database
	 */
	public function select_db (string $database) {}

	/**
	 * {@inheritdoc}
	 * @param string $charset
	 */
	public function set_charset (string $charset) {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 * @param mixed $value
	 */
	public function options (int $option, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 * @param mixed $value
	 */
	public function set_opt (int $option, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $key
	 * @param string|null $certificate
	 * @param string|null $ca_certificate
	 * @param string|null $ca_path
	 * @param string|null $cipher_algos
	 */
	public function ssl_set (?string $key = null, ?string $certificate = null, ?string $ca_certificate = null, ?string $ca_path = null, ?string $cipher_algos = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function stat () {}

	/**
	 * {@inheritdoc}
	 */
	public function stmt_init () {}

	/**
	 * {@inheritdoc}
	 * @param int $mode [optional]
	 */
	public function store_result (int $mode = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function thread_safe () {}

	/**
	 * {@inheritdoc}
	 */
	public function use_result () {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function refresh (int $flags) {}

}

final class mysqli_warning  {

	public string $message;

	public string $sqlstate;

	public int $errno;

	/**
	 * {@inheritdoc}
	 */
	private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function next (): bool {}

}

class mysqli_result implements IteratorAggregate, Traversable {

	public int $current_field;

	public int $field_count;

	public ?array $lengths;

	public string|int $num_rows;

	public int $type;

	/**
	 * {@inheritdoc}
	 * @param mysqli $mysql
	 * @param int $result_mode [optional]
	 */
	public function __construct (mysqli $mysql, int $result_mode = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function close () {}

	/**
	 * {@inheritdoc}
	 */
	public function free () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function data_seek (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function fetch_field () {}

	/**
	 * {@inheritdoc}
	 */
	public function fetch_fields () {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function fetch_field_direct (int $index) {}

	/**
	 * {@inheritdoc}
	 * @param int $mode [optional]
	 */
	public function fetch_all (int $mode = 2) {}

	/**
	 * {@inheritdoc}
	 * @param int $mode [optional]
	 */
	public function fetch_array (int $mode = 3) {}

	/**
	 * {@inheritdoc}
	 */
	public function fetch_assoc () {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 * @param array $constructor_args [optional]
	 */
	public function fetch_object (string $class = 'stdClass', array $constructor_args = array (
)) {}

	/**
	 * {@inheritdoc}
	 */
	public function fetch_row () {}

	/**
	 * {@inheritdoc}
	 * @param int $column [optional]
	 */
	public function fetch_column (int $column = 0): string|int|float|false|null {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function field_seek (int $index) {}

	/**
	 * {@inheritdoc}
	 */
	public function free_result () {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): Iterator {}

}

class mysqli_stmt  {

	public string|int $affected_rows;

	public string|int $insert_id;

	public string|int $num_rows;

	public int $param_count;

	public int $field_count;

	public int $errno;

	public string $error;

	public array $error_list;

	public string $sqlstate;

	public int $id;

	/**
	 * {@inheritdoc}
	 * @param mysqli $mysql
	 * @param string|null $query [optional]
	 */
	public function __construct (mysqli $mysql, ?string $query = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $attribute
	 */
	public function attr_get (int $attribute) {}

	/**
	 * {@inheritdoc}
	 * @param int $attribute
	 * @param int $value
	 */
	public function attr_set (int $attribute, int $value) {}

	/**
	 * {@inheritdoc}
	 * @param string $types
	 * @param mixed $vars [optional]
	 */
	public function bind_param (string $types, mixed &...$vars) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $vars [optional]
	 */
	public function bind_result (mixed &...$vars) {}

	/**
	 * {@inheritdoc}
	 */
	public function close () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function data_seek (int $offset) {}

	/**
	 * {@inheritdoc}
	 * @param array|null $params [optional]
	 */
	public function execute (?array $params = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function fetch () {}

	/**
	 * {@inheritdoc}
	 */
	public function get_warnings () {}

	/**
	 * {@inheritdoc}
	 */
	public function result_metadata () {}

	/**
	 * {@inheritdoc}
	 */
	public function more_results () {}

	/**
	 * {@inheritdoc}
	 */
	public function next_result () {}

	/**
	 * {@inheritdoc}
	 */
	public function num_rows () {}

	/**
	 * {@inheritdoc}
	 * @param int $param_num
	 * @param string $data
	 */
	public function send_long_data (int $param_num, string $data) {}

	/**
	 * {@inheritdoc}
	 */
	public function free_result () {}

	/**
	 * {@inheritdoc}
	 */
	public function reset () {}

	/**
	 * {@inheritdoc}
	 * @param string $query
	 */
	public function prepare (string $query) {}

	/**
	 * {@inheritdoc}
	 */
	public function store_result () {}

	/**
	 * {@inheritdoc}
	 */
	public function get_result () {}

}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_affected_rows (mysqli $mysql): string|int {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param bool $enable
 */
function mysqli_autocommit (mysqli $mysql, bool $enable): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param int $flags [optional]
 * @param string|null $name [optional]
 */
function mysqli_begin_transaction (mysqli $mysql, int $flags = 0, ?string $name = NULL): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param string $username
 * @param string $password
 * @param string|null $database
 */
function mysqli_change_user (mysqli $mysql, string $username, string $password, ?string $database = null): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_character_set_name (mysqli $mysql): string {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_close (mysqli $mysql): true {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param int $flags [optional]
 * @param string|null $name [optional]
 */
function mysqli_commit (mysqli $mysql, int $flags = 0, ?string $name = NULL): bool {}

/**
 * {@inheritdoc}
 * @param string|null $hostname [optional]
 * @param string|null $username [optional]
 * @param string|null $password [optional]
 * @param string|null $database [optional]
 * @param int|null $port [optional]
 * @param string|null $socket [optional]
 */
function mysqli_connect (?string $hostname = NULL, ?string $username = NULL, ?string $password = NULL, ?string $database = NULL, ?int $port = NULL, ?string $socket = NULL): mysqli|false {}

/**
 * {@inheritdoc}
 */
function mysqli_connect_errno (): int {}

/**
 * {@inheritdoc}
 */
function mysqli_connect_error (): ?string {}

/**
 * {@inheritdoc}
 * @param mysqli_result $result
 * @param int $offset
 */
function mysqli_data_seek (mysqli_result $result, int $offset): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_dump_debug_info (mysqli $mysql): bool {}

/**
 * {@inheritdoc}
 * @param string $options
 */
function mysqli_debug (string $options): true {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_errno (mysqli $mysql): int {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_error (mysqli $mysql): string {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_error_list (mysqli $mysql): array {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 * @param array|null $params [optional]
 */
function mysqli_stmt_execute (mysqli_stmt $statement, ?array $params = NULL): bool {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 * @param array|null $params [optional]
 */
function mysqli_execute (mysqli_stmt $statement, ?array $params = NULL): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param string $query
 * @param array|null $params [optional]
 */
function mysqli_execute_query (mysqli $mysql, string $query, ?array $params = NULL): mysqli_result|bool {}

/**
 * {@inheritdoc}
 * @param mysqli_result $result
 */
function mysqli_fetch_field (mysqli_result $result): object|false {}

/**
 * {@inheritdoc}
 * @param mysqli_result $result
 */
function mysqli_fetch_fields (mysqli_result $result): array {}

/**
 * {@inheritdoc}
 * @param mysqli_result $result
 * @param int $index
 */
function mysqli_fetch_field_direct (mysqli_result $result, int $index): object|false {}

/**
 * {@inheritdoc}
 * @param mysqli_result $result
 */
function mysqli_fetch_lengths (mysqli_result $result): array|false {}

/**
 * {@inheritdoc}
 * @param mysqli_result $result
 * @param int $mode [optional]
 */
function mysqli_fetch_all (mysqli_result $result, int $mode = 2): array {}

/**
 * {@inheritdoc}
 * @param mysqli_result $result
 * @param int $mode [optional]
 */
function mysqli_fetch_array (mysqli_result $result, int $mode = 3): array|false|null {}

/**
 * {@inheritdoc}
 * @param mysqli_result $result
 */
function mysqli_fetch_assoc (mysqli_result $result): array|false|null {}

/**
 * {@inheritdoc}
 * @param mysqli_result $result
 * @param string $class [optional]
 * @param array $constructor_args [optional]
 */
function mysqli_fetch_object (mysqli_result $result, string $class = 'stdClass', array $constructor_args = array (
)): object|false|null {}

/**
 * {@inheritdoc}
 * @param mysqli_result $result
 */
function mysqli_fetch_row (mysqli_result $result): array|false|null {}

/**
 * {@inheritdoc}
 * @param mysqli_result $result
 * @param int $column [optional]
 */
function mysqli_fetch_column (mysqli_result $result, int $column = 0): string|int|float|false|null {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_field_count (mysqli $mysql): int {}

/**
 * {@inheritdoc}
 * @param mysqli_result $result
 * @param int $index
 */
function mysqli_field_seek (mysqli_result $result, int $index): true {}

/**
 * {@inheritdoc}
 * @param mysqli_result $result
 */
function mysqli_field_tell (mysqli_result $result): int {}

/**
 * {@inheritdoc}
 * @param mysqli_result $result
 */
function mysqli_free_result (mysqli_result $result): void {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_get_connection_stats (mysqli $mysql): array {}

/**
 * {@inheritdoc}
 */
function mysqli_get_client_stats (): array {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_get_charset (mysqli $mysql): ?object {}

/**
 * {@inheritdoc}
 * @param mysqli|null $mysql [optional]
 */
function mysqli_get_client_info (?mysqli $mysql = NULL): string {}

/**
 * {@inheritdoc}
 */
function mysqli_get_client_version (): int {}

/**
 * {@inheritdoc}
 */
function mysqli_get_links_stats (): array {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_get_host_info (mysqli $mysql): string {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_get_proto_info (mysqli $mysql): int {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_get_server_info (mysqli $mysql): string {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_get_server_version (mysqli $mysql): int {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_get_warnings (mysqli $mysql): mysqli_warning|false {}

/**
 * {@inheritdoc}
 */
function mysqli_init (): mysqli|false {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_info (mysqli $mysql): ?string {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_insert_id (mysqli $mysql): string|int {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param int $process_id
 */
function mysqli_kill (mysqli $mysql, int $process_id): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_more_results (mysqli $mysql): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param string $query
 */
function mysqli_multi_query (mysqli $mysql, string $query): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_next_result (mysqli $mysql): bool {}

/**
 * {@inheritdoc}
 * @param mysqli_result $result
 */
function mysqli_num_fields (mysqli_result $result): int {}

/**
 * {@inheritdoc}
 * @param mysqli_result $result
 */
function mysqli_num_rows (mysqli_result $result): string|int {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param int $option
 * @param mixed $value
 */
function mysqli_options (mysqli $mysql, int $option, $value = null): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param int $option
 * @param mixed $value
 */
function mysqli_set_opt (mysqli $mysql, int $option, $value = null): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_ping (mysqli $mysql): bool {}

/**
 * {@inheritdoc}
 * @param array|null $read
 * @param array|null $error
 * @param array $reject
 * @param int $seconds
 * @param int $microseconds [optional]
 */
function mysqli_poll (?array &$read = null, ?array &$error = null, array &$reject, int $seconds, int $microseconds = 0): int|false {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param string $query
 */
function mysqli_prepare (mysqli $mysql, string $query): mysqli_stmt|false {}

/**
 * {@inheritdoc}
 * @param int $flags
 */
function mysqli_report (int $flags): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param string $query
 * @param int $result_mode [optional]
 */
function mysqli_query (mysqli $mysql, string $query, int $result_mode = 0): mysqli_result|bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param string|null $hostname [optional]
 * @param string|null $username [optional]
 * @param string|null $password [optional]
 * @param string|null $database [optional]
 * @param int|null $port [optional]
 * @param string|null $socket [optional]
 * @param int $flags [optional]
 */
function mysqli_real_connect (mysqli $mysql, ?string $hostname = NULL, ?string $username = NULL, ?string $password = NULL, ?string $database = NULL, ?int $port = NULL, ?string $socket = NULL, int $flags = 0): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param string $string
 */
function mysqli_real_escape_string (mysqli $mysql, string $string): string {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param string $string
 */
function mysqli_escape_string (mysqli $mysql, string $string): string {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param string $query
 */
function mysqli_real_query (mysqli $mysql, string $query): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_reap_async_query (mysqli $mysql): mysqli_result|bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param string $name
 */
function mysqli_release_savepoint (mysqli $mysql, string $name): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param int $flags [optional]
 * @param string|null $name [optional]
 */
function mysqli_rollback (mysqli $mysql, int $flags = 0, ?string $name = NULL): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param string $name
 */
function mysqli_savepoint (mysqli $mysql, string $name): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param string $database
 */
function mysqli_select_db (mysqli $mysql, string $database): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param string $charset
 */
function mysqli_set_charset (mysqli $mysql, string $charset): bool {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_affected_rows (mysqli_stmt $statement): string|int {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 * @param int $attribute
 */
function mysqli_stmt_attr_get (mysqli_stmt $statement, int $attribute): int {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 * @param int $attribute
 * @param int $value
 */
function mysqli_stmt_attr_set (mysqli_stmt $statement, int $attribute, int $value): bool {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 * @param string $types
 * @param mixed $vars [optional]
 */
function mysqli_stmt_bind_param (mysqli_stmt $statement, string $types, mixed &...$vars): bool {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 * @param mixed $vars [optional]
 */
function mysqli_stmt_bind_result (mysqli_stmt $statement, mixed &...$vars): bool {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_close (mysqli_stmt $statement): true {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 * @param int $offset
 */
function mysqli_stmt_data_seek (mysqli_stmt $statement, int $offset): void {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_errno (mysqli_stmt $statement): int {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_error (mysqli_stmt $statement): string {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_error_list (mysqli_stmt $statement): array {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_fetch (mysqli_stmt $statement): ?bool {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_field_count (mysqli_stmt $statement): int {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_free_result (mysqli_stmt $statement): void {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_get_result (mysqli_stmt $statement): mysqli_result|false {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_get_warnings (mysqli_stmt $statement): mysqli_warning|false {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_stmt_init (mysqli $mysql): mysqli_stmt|false {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_insert_id (mysqli_stmt $statement): string|int {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_more_results (mysqli_stmt $statement): bool {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_next_result (mysqli_stmt $statement): bool {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_num_rows (mysqli_stmt $statement): string|int {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_param_count (mysqli_stmt $statement): int {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 * @param string $query
 */
function mysqli_stmt_prepare (mysqli_stmt $statement, string $query): bool {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_reset (mysqli_stmt $statement): bool {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_result_metadata (mysqli_stmt $statement): mysqli_result|false {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 * @param int $param_num
 * @param string $data
 */
function mysqli_stmt_send_long_data (mysqli_stmt $statement, int $param_num, string $data): bool {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_store_result (mysqli_stmt $statement): bool {}

/**
 * {@inheritdoc}
 * @param mysqli_stmt $statement
 */
function mysqli_stmt_sqlstate (mysqli_stmt $statement): string {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_sqlstate (mysqli $mysql): string {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param string|null $key
 * @param string|null $certificate
 * @param string|null $ca_certificate
 * @param string|null $ca_path
 * @param string|null $cipher_algos
 */
function mysqli_ssl_set (mysqli $mysql, ?string $key = null, ?string $certificate = null, ?string $ca_certificate = null, ?string $ca_path = null, ?string $cipher_algos = null): true {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_stat (mysqli $mysql): string|false {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param int $mode [optional]
 */
function mysqli_store_result (mysqli $mysql, int $mode = 0): mysqli_result|false {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_thread_id (mysqli $mysql): int {}

/**
 * {@inheritdoc}
 */
function mysqli_thread_safe (): bool {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_use_result (mysqli $mysql): mysqli_result|false {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 */
function mysqli_warning_count (mysqli $mysql): int {}

/**
 * {@inheritdoc}
 * @param mysqli $mysql
 * @param int $flags
 */
function mysqli_refresh (mysqli $mysql, int $flags): bool {}

define ('MYSQLI_READ_DEFAULT_GROUP', 5);
define ('MYSQLI_READ_DEFAULT_FILE', 4);
define ('MYSQLI_OPT_CONNECT_TIMEOUT', 0);
define ('MYSQLI_OPT_LOCAL_INFILE', 8);
define ('MYSQLI_OPT_LOAD_DATA_LOCAL_DIR', 43);
define ('MYSQLI_INIT_COMMAND', 3);
define ('MYSQLI_OPT_READ_TIMEOUT', 11);
define ('MYSQLI_OPT_NET_CMD_BUFFER_SIZE', 202);
define ('MYSQLI_OPT_NET_READ_BUFFER_SIZE', 203);
define ('MYSQLI_OPT_INT_AND_FLOAT_NATIVE', 201);
define ('MYSQLI_OPT_SSL_VERIFY_SERVER_CERT', 21);
define ('MYSQLI_SERVER_PUBLIC_KEY', 35);
define ('MYSQLI_CLIENT_SSL', 2048);
define ('MYSQLI_CLIENT_COMPRESS', 32);
define ('MYSQLI_CLIENT_INTERACTIVE', 1024);
define ('MYSQLI_CLIENT_IGNORE_SPACE', 256);
define ('MYSQLI_CLIENT_NO_SCHEMA', 16);
define ('MYSQLI_CLIENT_FOUND_ROWS', 2);
define ('MYSQLI_CLIENT_SSL_VERIFY_SERVER_CERT', 1073741824);
define ('MYSQLI_CLIENT_SSL_DONT_VERIFY_SERVER_CERT', 64);
define ('MYSQLI_CLIENT_CAN_HANDLE_EXPIRED_PASSWORDS', 4194304);
define ('MYSQLI_OPT_CAN_HANDLE_EXPIRED_PASSWORDS', 37);
define ('MYSQLI_STORE_RESULT', 0);
define ('MYSQLI_USE_RESULT', 1);
define ('MYSQLI_ASYNC', 8);
define ('MYSQLI_STORE_RESULT_COPY_DATA', 16);
define ('MYSQLI_ASSOC', 1);
define ('MYSQLI_NUM', 2);
define ('MYSQLI_BOTH', 3);
define ('MYSQLI_STMT_ATTR_UPDATE_MAX_LENGTH', 0);
define ('MYSQLI_STMT_ATTR_CURSOR_TYPE', 1);
define ('MYSQLI_CURSOR_TYPE_NO_CURSOR', 0);
define ('MYSQLI_CURSOR_TYPE_READ_ONLY', 1);
define ('MYSQLI_CURSOR_TYPE_FOR_UPDATE', 2);
define ('MYSQLI_CURSOR_TYPE_SCROLLABLE', 4);
define ('MYSQLI_STMT_ATTR_PREFETCH_ROWS', 2);
define ('MYSQLI_NOT_NULL_FLAG', 1);
define ('MYSQLI_PRI_KEY_FLAG', 2);
define ('MYSQLI_UNIQUE_KEY_FLAG', 4);
define ('MYSQLI_MULTIPLE_KEY_FLAG', 8);
define ('MYSQLI_BLOB_FLAG', 16);
define ('MYSQLI_UNSIGNED_FLAG', 32);
define ('MYSQLI_ZEROFILL_FLAG', 64);
define ('MYSQLI_AUTO_INCREMENT_FLAG', 512);
define ('MYSQLI_TIMESTAMP_FLAG', 1024);
define ('MYSQLI_SET_FLAG', 2048);
define ('MYSQLI_NUM_FLAG', 32768);
define ('MYSQLI_PART_KEY_FLAG', 16384);
define ('MYSQLI_GROUP_FLAG', 32768);
define ('MYSQLI_ENUM_FLAG', 256);
define ('MYSQLI_BINARY_FLAG', 128);
define ('MYSQLI_NO_DEFAULT_VALUE_FLAG', 4096);
define ('MYSQLI_ON_UPDATE_NOW_FLAG', 8192);
define ('MYSQLI_TYPE_DECIMAL', 0);
define ('MYSQLI_TYPE_TINY', 1);
define ('MYSQLI_TYPE_SHORT', 2);
define ('MYSQLI_TYPE_LONG', 3);
define ('MYSQLI_TYPE_FLOAT', 4);
define ('MYSQLI_TYPE_DOUBLE', 5);
define ('MYSQLI_TYPE_NULL', 6);
define ('MYSQLI_TYPE_TIMESTAMP', 7);
define ('MYSQLI_TYPE_LONGLONG', 8);
define ('MYSQLI_TYPE_INT24', 9);
define ('MYSQLI_TYPE_DATE', 10);
define ('MYSQLI_TYPE_TIME', 11);
define ('MYSQLI_TYPE_DATETIME', 12);
define ('MYSQLI_TYPE_YEAR', 13);
define ('MYSQLI_TYPE_NEWDATE', 14);
define ('MYSQLI_TYPE_ENUM', 247);
define ('MYSQLI_TYPE_SET', 248);
define ('MYSQLI_TYPE_TINY_BLOB', 249);
define ('MYSQLI_TYPE_MEDIUM_BLOB', 250);
define ('MYSQLI_TYPE_LONG_BLOB', 251);
define ('MYSQLI_TYPE_BLOB', 252);
define ('MYSQLI_TYPE_VAR_STRING', 253);
define ('MYSQLI_TYPE_STRING', 254);
define ('MYSQLI_TYPE_CHAR', 1);
define ('MYSQLI_TYPE_INTERVAL', 247);
define ('MYSQLI_TYPE_GEOMETRY', 255);
define ('MYSQLI_TYPE_JSON', 245);
define ('MYSQLI_TYPE_NEWDECIMAL', 246);
define ('MYSQLI_TYPE_BIT', 16);
define ('MYSQLI_SET_CHARSET_NAME', 7);
define ('MYSQLI_SET_CHARSET_DIR', 6);
define ('MYSQLI_NO_DATA', 100);
define ('MYSQLI_DATA_TRUNCATED', 101);
define ('MYSQLI_REPORT_INDEX', 4);
define ('MYSQLI_REPORT_ERROR', 1);
define ('MYSQLI_REPORT_STRICT', 2);
define ('MYSQLI_REPORT_ALL', 255);
define ('MYSQLI_REPORT_OFF', 0);
define ('MYSQLI_DEBUG_TRACE_ENABLED', 0);
define ('MYSQLI_SERVER_QUERY_NO_GOOD_INDEX_USED', 16);
define ('MYSQLI_SERVER_QUERY_NO_INDEX_USED', 32);
define ('MYSQLI_SERVER_QUERY_WAS_SLOW', 2048);
define ('MYSQLI_SERVER_PS_OUT_PARAMS', 4096);
define ('MYSQLI_REFRESH_GRANT', 1);
define ('MYSQLI_REFRESH_LOG', 2);
define ('MYSQLI_REFRESH_TABLES', 4);
define ('MYSQLI_REFRESH_HOSTS', 8);
define ('MYSQLI_REFRESH_STATUS', 16);
define ('MYSQLI_REFRESH_THREADS', 32);
define ('MYSQLI_REFRESH_REPLICA', 64);
define ('MYSQLI_REFRESH_SLAVE', 64);
define ('MYSQLI_REFRESH_MASTER', 128);
define ('MYSQLI_REFRESH_BACKUP_LOG', 2097152);
define ('MYSQLI_TRANS_START_WITH_CONSISTENT_SNAPSHOT', 1);
define ('MYSQLI_TRANS_START_READ_WRITE', 2);
define ('MYSQLI_TRANS_START_READ_ONLY', 4);
define ('MYSQLI_TRANS_COR_AND_CHAIN', 1);
define ('MYSQLI_TRANS_COR_AND_NO_CHAIN', 2);
define ('MYSQLI_TRANS_COR_RELEASE', 4);
define ('MYSQLI_TRANS_COR_NO_RELEASE', 8);
define ('MYSQLI_IS_MARIADB', false);

// End of mysqli v.8.3.0
