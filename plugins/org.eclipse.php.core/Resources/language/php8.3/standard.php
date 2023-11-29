<?php

// Start of standard v.8.3.0

#[AllowDynamicProperties]
final class __PHP_Incomplete_Class  {
}

class AssertionError extends Error implements Throwable, Stringable {

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

class php_user_filter  {

	public string $filtername;

	public mixed $params;

	public $stream;

	/**
	 * {@inheritdoc}
	 * @param mixed $in
	 * @param mixed $out
	 * @param mixed $consumed
	 * @param bool $closing
	 */
	public function filter ($in = null, $out = null, &$consumed = null, bool $closing) {}

	/**
	 * {@inheritdoc}
	 */
	public function onCreate () {}

	/**
	 * {@inheritdoc}
	 */
	public function onClose () {}

}

class Directory  {

	public readonly string $path;

	public readonly mixed $handle;

	/**
	 * {@inheritdoc}
	 */
	public function close () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function read () {}

}

/**
 * {@inheritdoc}
 * @param int $seconds
 */
function set_time_limit (int $seconds): bool {}

/**
 * {@inheritdoc}
 * @param callable $callback
 */
function header_register_callback (callable $callback): bool {}

/**
 * {@inheritdoc}
 * @param mixed $callback [optional]
 * @param int $chunk_size [optional]
 * @param int $flags [optional]
 */
function ob_start ($callback = NULL, int $chunk_size = 0, int $flags = 112): bool {}

/**
 * {@inheritdoc}
 */
function ob_flush (): bool {}

/**
 * {@inheritdoc}
 */
function ob_clean (): bool {}

/**
 * {@inheritdoc}
 */
function ob_end_flush (): bool {}

/**
 * {@inheritdoc}
 */
function ob_end_clean (): bool {}

/**
 * {@inheritdoc}
 */
function ob_get_flush (): string|false {}

/**
 * {@inheritdoc}
 */
function ob_get_clean (): string|false {}

/**
 * {@inheritdoc}
 */
function ob_get_contents (): string|false {}

/**
 * {@inheritdoc}
 */
function ob_get_level (): int {}

/**
 * {@inheritdoc}
 */
function ob_get_length (): int|false {}

/**
 * {@inheritdoc}
 */
function ob_list_handlers (): array {}

/**
 * {@inheritdoc}
 * @param bool $full_status [optional]
 */
function ob_get_status (bool $full_status = false): array {}

/**
 * {@inheritdoc}
 * @param bool $enable [optional]
 */
function ob_implicit_flush (bool $enable = true): void {}

/**
 * {@inheritdoc}
 */
function output_reset_rewrite_vars (): bool {}

/**
 * {@inheritdoc}
 * @param string $name
 * @param string $value
 */
function output_add_rewrite_var (string $name, string $value): bool {}

/**
 * {@inheritdoc}
 * @param string $protocol
 * @param string $class
 * @param int $flags [optional]
 */
function stream_wrapper_register (string $protocol, string $class, int $flags = 0): bool {}

/**
 * {@inheritdoc}
 * @param string $protocol
 * @param string $class
 * @param int $flags [optional]
 */
function stream_register_wrapper (string $protocol, string $class, int $flags = 0): bool {}

/**
 * {@inheritdoc}
 * @param string $protocol
 */
function stream_wrapper_unregister (string $protocol): bool {}

/**
 * {@inheritdoc}
 * @param string $protocol
 */
function stream_wrapper_restore (string $protocol): bool {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param mixed $values [optional]
 */
function array_push (array &$array, mixed ...$values): int {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param int $flags [optional]
 */
function krsort (array &$array, int $flags = 0): true {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param int $flags [optional]
 */
function ksort (array &$array, int $flags = 0): true {}

/**
 * {@inheritdoc}
 * @param Countable|array $value
 * @param int $mode [optional]
 */
function count (Countable|array $value, int $mode = 0): int {}

/**
 * {@inheritdoc}
 * @param Countable|array $value
 * @param int $mode [optional]
 */
function sizeof (Countable|array $value, int $mode = 0): int {}

/**
 * {@inheritdoc}
 * @param array $array
 */
function natsort (array &$array): true {}

/**
 * {@inheritdoc}
 * @param array $array
 */
function natcasesort (array &$array): true {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param int $flags [optional]
 */
function asort (array &$array, int $flags = 0): true {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param int $flags [optional]
 */
function arsort (array &$array, int $flags = 0): true {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param int $flags [optional]
 */
function sort (array &$array, int $flags = 0): true {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param int $flags [optional]
 */
function rsort (array &$array, int $flags = 0): true {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param callable $callback
 */
function usort (array &$array, callable $callback): true {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param callable $callback
 */
function uasort (array &$array, callable $callback): true {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param callable $callback
 */
function uksort (array &$array, callable $callback): true {}

/**
 * {@inheritdoc}
 * @param object|array $array
 */
function end (object|array &$array): mixed {}

/**
 * {@inheritdoc}
 * @param object|array $array
 */
function prev (object|array &$array): mixed {}

/**
 * {@inheritdoc}
 * @param object|array $array
 */
function next (object|array &$array): mixed {}

/**
 * {@inheritdoc}
 * @param object|array $array
 */
function reset (object|array &$array): mixed {}

/**
 * {@inheritdoc}
 * @param object|array $array
 */
function current (object|array $array): mixed {}

/**
 * {@inheritdoc}
 * @param object|array $array
 */
function pos (object|array $array): mixed {}

/**
 * {@inheritdoc}
 * @param object|array $array
 */
function key (object|array $array): string|int|null {}

/**
 * {@inheritdoc}
 * @param mixed $value
 * @param mixed $values [optional]
 */
function min (mixed $value = null, mixed ...$values): mixed {}

/**
 * {@inheritdoc}
 * @param mixed $value
 * @param mixed $values [optional]
 */
function max (mixed $value = null, mixed ...$values): mixed {}

/**
 * {@inheritdoc}
 * @param object|array $array
 * @param callable $callback
 * @param mixed $arg [optional]
 */
function array_walk (object|array &$array, callable $callback, mixed $arg = NULL): true {}

/**
 * {@inheritdoc}
 * @param object|array $array
 * @param callable $callback
 * @param mixed $arg [optional]
 */
function array_walk_recursive (object|array &$array, callable $callback, mixed $arg = NULL): true {}

/**
 * {@inheritdoc}
 * @param mixed $needle
 * @param array $haystack
 * @param bool $strict [optional]
 */
function in_array (mixed $needle = null, array $haystack, bool $strict = false): bool {}

/**
 * {@inheritdoc}
 * @param mixed $needle
 * @param array $haystack
 * @param bool $strict [optional]
 */
function array_search (mixed $needle = null, array $haystack, bool $strict = false): string|int|false {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param int $flags [optional]
 * @param string $prefix [optional]
 */
function extract (array &$array, int $flags = 0, string $prefix = ''): int {}

/**
 * {@inheritdoc}
 * @param mixed $var_name
 * @param mixed $var_names [optional]
 */
function compact ($var_name = null, ...$var_names): array {}

/**
 * {@inheritdoc}
 * @param int $start_index
 * @param int $count
 * @param mixed $value
 */
function array_fill (int $start_index, int $count, mixed $value = null): array {}

/**
 * {@inheritdoc}
 * @param array $keys
 * @param mixed $value
 */
function array_fill_keys (array $keys, mixed $value = null): array {}

/**
 * {@inheritdoc}
 * @param string|int|float $start
 * @param string|int|float $end
 * @param int|float $step [optional]
 */
function range (string|int|float $start, string|int|float $end, int|float $step = 1): array {}

/**
 * {@inheritdoc}
 * @param array $array
 */
function shuffle (array &$array): true {}

/**
 * {@inheritdoc}
 * @param array $array
 */
function array_pop (array &$array): mixed {}

/**
 * {@inheritdoc}
 * @param array $array
 */
function array_shift (array &$array): mixed {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param mixed $values [optional]
 */
function array_unshift (array &$array, mixed ...$values): int {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param int $offset
 * @param int|null $length [optional]
 * @param mixed $replacement [optional]
 */
function array_splice (array &$array, int $offset, ?int $length = NULL, mixed $replacement = array (
)): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param int $offset
 * @param int|null $length [optional]
 * @param bool $preserve_keys [optional]
 */
function array_slice (array $array, int $offset, ?int $length = NULL, bool $preserve_keys = false): array {}

/**
 * {@inheritdoc}
 * @param array $arrays [optional]
 */
function array_merge (array ...$arrays): array {}

/**
 * {@inheritdoc}
 * @param array $arrays [optional]
 */
function array_merge_recursive (array ...$arrays): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param array $replacements [optional]
 */
function array_replace (array $array, array ...$replacements): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param array $replacements [optional]
 */
function array_replace_recursive (array $array, array ...$replacements): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param mixed $filter_value [optional]
 * @param bool $strict [optional]
 */
function array_keys (array $array, mixed $filter_value = NULL, bool $strict = false): array {}

/**
 * {@inheritdoc}
 * @param array $array
 */
function array_key_first (array $array): string|int|null {}

/**
 * {@inheritdoc}
 * @param array $array
 */
function array_key_last (array $array): string|int|null {}

/**
 * {@inheritdoc}
 * @param array $array
 */
function array_values (array $array): array {}

/**
 * {@inheritdoc}
 * @param array $array
 */
function array_count_values (array $array): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param string|int|null $column_key
 * @param string|int|null $index_key [optional]
 */
function array_column (array $array, string|int|null $column_key = null, string|int|null $index_key = NULL): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param bool $preserve_keys [optional]
 */
function array_reverse (array $array, bool $preserve_keys = false): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param int $length
 * @param mixed $value
 */
function array_pad (array $array, int $length, mixed $value = null): array {}

/**
 * {@inheritdoc}
 * @param array $array
 */
function array_flip (array $array): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param int $case [optional]
 */
function array_change_key_case (array $array, int $case = 0): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param int $flags [optional]
 */
function array_unique (array $array, int $flags = 2): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param array $arrays [optional]
 */
function array_intersect_key (array $array, array ...$arrays): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param mixed $rest [optional]
 */
function array_intersect_ukey (array $array, ...$rest): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param array $arrays [optional]
 */
function array_intersect (array $array, array ...$arrays): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param mixed $rest [optional]
 */
function array_uintersect (array $array, ...$rest): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param array $arrays [optional]
 */
function array_intersect_assoc (array $array, array ...$arrays): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param mixed $rest [optional]
 */
function array_uintersect_assoc (array $array, ...$rest): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param mixed $rest [optional]
 */
function array_intersect_uassoc (array $array, ...$rest): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param mixed $rest [optional]
 */
function array_uintersect_uassoc (array $array, ...$rest): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param array $arrays [optional]
 */
function array_diff_key (array $array, array ...$arrays): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param mixed $rest [optional]
 */
function array_diff_ukey (array $array, ...$rest): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param array $arrays [optional]
 */
function array_diff (array $array, array ...$arrays): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param mixed $rest [optional]
 */
function array_udiff (array $array, ...$rest): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param array $arrays [optional]
 */
function array_diff_assoc (array $array, array ...$arrays): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param mixed $rest [optional]
 */
function array_diff_uassoc (array $array, ...$rest): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param mixed $rest [optional]
 */
function array_udiff_assoc (array $array, ...$rest): array {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param mixed $rest [optional]
 */
function array_udiff_uassoc (array $array, ...$rest): array {}

/**
 * {@inheritdoc}
 * @param mixed $array
 * @param mixed $rest [optional]
 */
function array_multisort (&$array = null, &...$rest): bool {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param int $num [optional]
 */
function array_rand (array $array, int $num = 1): array|string|int {}

/**
 * {@inheritdoc}
 * @param array $array
 */
function array_sum (array $array): int|float {}

/**
 * {@inheritdoc}
 * @param array $array
 */
function array_product (array $array): int|float {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param callable $callback
 * @param mixed $initial [optional]
 */
function array_reduce (array $array, callable $callback, mixed $initial = NULL): mixed {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param callable|null $callback [optional]
 * @param int $mode [optional]
 */
function array_filter (array $array, ?callable $callback = NULL, int $mode = 0): array {}

/**
 * {@inheritdoc}
 * @param callable|null $callback
 * @param array $array
 * @param array $arrays [optional]
 */
function array_map (?callable $callback = null, array $array, array ...$arrays): array {}

/**
 * {@inheritdoc}
 * @param mixed $key
 * @param array $array
 */
function array_key_exists ($key = null, array $array): bool {}

/**
 * {@inheritdoc}
 * @param mixed $key
 * @param array $array
 */
function key_exists ($key = null, array $array): bool {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param int $length
 * @param bool $preserve_keys [optional]
 */
function array_chunk (array $array, int $length, bool $preserve_keys = false): array {}

/**
 * {@inheritdoc}
 * @param array $keys
 * @param array $values
 */
function array_combine (array $keys, array $values): array {}

/**
 * {@inheritdoc}
 * @param array $array
 */
function array_is_list (array $array): bool {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function base64_encode (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param bool $strict [optional]
 */
function base64_decode (string $string, bool $strict = false): string|false {}

/**
 * {@inheritdoc}
 * @param string $name
 */
function constant (string $name): mixed {}

/**
 * {@inheritdoc}
 * @param string $ip
 */
function ip2long (string $ip): int|false {}

/**
 * {@inheritdoc}
 * @param int $ip
 */
function long2ip (int $ip): string|false {}

/**
 * {@inheritdoc}
 * @param string|null $name [optional]
 * @param bool $local_only [optional]
 */
function getenv (?string $name = NULL, bool $local_only = false): array|string|false {}

/**
 * {@inheritdoc}
 * @param string $assignment
 */
function putenv (string $assignment): bool {}

/**
 * {@inheritdoc}
 * @param string $short_options
 * @param array $long_options [optional]
 * @param mixed $rest_index [optional]
 */
function getopt (string $short_options, array $long_options = array (
), &$rest_index = NULL): array|false {}

/**
 * {@inheritdoc}
 */
function flush (): void {}

/**
 * {@inheritdoc}
 * @param int $seconds
 */
function sleep (int $seconds): int {}

/**
 * {@inheritdoc}
 * @param int $microseconds
 */
function usleep (int $microseconds): void {}

/**
 * {@inheritdoc}
 * @param int $seconds
 * @param int $nanoseconds
 */
function time_nanosleep (int $seconds, int $nanoseconds): array|bool {}

/**
 * {@inheritdoc}
 * @param float $timestamp
 */
function time_sleep_until (float $timestamp): bool {}

/**
 * {@inheritdoc}
 */
function get_current_user (): string {}

/**
 * {@inheritdoc}
 * @param string $option
 */
function get_cfg_var (string $option): array|string|false {}

/**
 * {@inheritdoc}
 * @param string $message
 * @param int $message_type [optional]
 * @param string|null $destination [optional]
 * @param string|null $additional_headers [optional]
 */
function error_log (string $message, int $message_type = 0, ?string $destination = NULL, ?string $additional_headers = NULL): bool {}

/**
 * {@inheritdoc}
 */
function error_get_last (): ?array {}

/**
 * {@inheritdoc}
 */
function error_clear_last (): void {}

/**
 * {@inheritdoc}
 * @param callable $callback
 * @param mixed $args [optional]
 */
function call_user_func (callable $callback, mixed ...$args): mixed {}

/**
 * {@inheritdoc}
 * @param callable $callback
 * @param array $args
 */
function call_user_func_array (callable $callback, array $args): mixed {}

/**
 * {@inheritdoc}
 * @param callable $callback
 * @param mixed $args [optional]
 */
function forward_static_call (callable $callback, mixed ...$args): mixed {}

/**
 * {@inheritdoc}
 * @param callable $callback
 * @param array $args
 */
function forward_static_call_array (callable $callback, array $args): mixed {}

/**
 * {@inheritdoc}
 * @param callable $callback
 * @param mixed $args [optional]
 */
function register_shutdown_function (callable $callback, mixed ...$args): void {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param bool $return [optional]
 */
function highlight_file (string $filename, bool $return = false): string|bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param bool $return [optional]
 */
function show_source (string $filename, bool $return = false): string|bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function php_strip_whitespace (string $filename): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param bool $return [optional]
 */
function highlight_string (string $string, bool $return = false): string|bool {}

/**
 * {@inheritdoc}
 * @param string $option
 */
function ini_get (string $option): string|false {}

/**
 * {@inheritdoc}
 * @param string|null $extension [optional]
 * @param bool $details [optional]
 */
function ini_get_all (?string $extension = NULL, bool $details = true): array|false {}

/**
 * {@inheritdoc}
 * @param string $option
 * @param string|int|float|bool|null $value
 */
function ini_set (string $option, string|int|float|bool|null $value = null): string|false {}

/**
 * {@inheritdoc}
 * @param string $option
 * @param string|int|float|bool|null $value
 */
function ini_alter (string $option, string|int|float|bool|null $value = null): string|false {}

/**
 * {@inheritdoc}
 * @param string $option
 */
function ini_restore (string $option): void {}

/**
 * {@inheritdoc}
 * @param string $shorthand
 */
function ini_parse_quantity (string $shorthand): int {}

/**
 * {@inheritdoc}
 * @param string $include_path
 */
function set_include_path (string $include_path): string|false {}

/**
 * {@inheritdoc}
 */
function get_include_path (): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $value
 * @param bool $return [optional]
 */
function print_r (mixed $value = null, bool $return = false): string|bool {}

/**
 * {@inheritdoc}
 */
function connection_aborted (): int {}

/**
 * {@inheritdoc}
 */
function connection_status (): int {}

/**
 * {@inheritdoc}
 * @param bool|null $enable [optional]
 */
function ignore_user_abort (?bool $enable = NULL): int {}

/**
 * {@inheritdoc}
 * @param string $service
 * @param string $protocol
 */
function getservbyname (string $service, string $protocol): int|false {}

/**
 * {@inheritdoc}
 * @param int $port
 * @param string $protocol
 */
function getservbyport (int $port, string $protocol): string|false {}

/**
 * {@inheritdoc}
 * @param string $protocol
 */
function getprotobyname (string $protocol): int|false {}

/**
 * {@inheritdoc}
 * @param int $protocol
 */
function getprotobynumber (int $protocol): string|false {}

/**
 * {@inheritdoc}
 * @param callable $callback
 * @param mixed $args [optional]
 */
function register_tick_function (callable $callback, mixed ...$args): bool {}

/**
 * {@inheritdoc}
 * @param callable $callback
 */
function unregister_tick_function (callable $callback): void {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function is_uploaded_file (string $filename): bool {}

/**
 * {@inheritdoc}
 * @param string $from
 * @param string $to
 */
function move_uploaded_file (string $from, string $to): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param bool $process_sections [optional]
 * @param int $scanner_mode [optional]
 */
function parse_ini_file (string $filename, bool $process_sections = false, int $scanner_mode = 0): array|false {}

/**
 * {@inheritdoc}
 * @param string $ini_string
 * @param bool $process_sections [optional]
 * @param int $scanner_mode [optional]
 */
function parse_ini_string (string $ini_string, bool $process_sections = false, int $scanner_mode = 0): array|false {}

/**
 * {@inheritdoc}
 */
function sys_getloadavg (): array|false {}

/**
 * {@inheritdoc}
 * @param string|null $user_agent [optional]
 * @param bool $return_array [optional]
 */
function get_browser (?string $user_agent = NULL, bool $return_array = false): object|array|false {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function crc32 (string $string): int {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string $salt
 */
function crypt (string $string, string $salt): string {}

/**
 * {@inheritdoc}
 * @param string $timestamp
 * @param string $format
 * @deprecated 
 */
function strptime (string $timestamp, string $format): array|false {}

/**
 * {@inheritdoc}
 */
function gethostname (): string|false {}

/**
 * {@inheritdoc}
 * @param string $ip
 */
function gethostbyaddr (string $ip): string|false {}

/**
 * {@inheritdoc}
 * @param string $hostname
 */
function gethostbyname (string $hostname): string {}

/**
 * {@inheritdoc}
 * @param string $hostname
 */
function gethostbynamel (string $hostname): array|false {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param string $type [optional]
 */
function dns_check_record (string $hostname, string $type = 'MX'): bool {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param string $type [optional]
 */
function checkdnsrr (string $hostname, string $type = 'MX'): bool {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param int $type [optional]
 * @param mixed $authoritative_name_servers [optional]
 * @param mixed $additional_records [optional]
 * @param bool $raw [optional]
 */
function dns_get_record (string $hostname, int $type = 268435456, &$authoritative_name_servers = NULL, &$additional_records = NULL, bool $raw = false): array|false {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param mixed $hosts
 * @param mixed $weights [optional]
 */
function dns_get_mx (string $hostname, &$hosts = null, &$weights = NULL): bool {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param mixed $hosts
 * @param mixed $weights [optional]
 */
function getmxrr (string $hostname, &$hosts = null, &$weights = NULL): bool {}

/**
 * {@inheritdoc}
 */
function net_get_interfaces (): array|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param string $project_id
 */
function ftok (string $filename, string $project_id): int {}

/**
 * {@inheritdoc}
 * @param bool $as_number [optional]
 */
function hrtime (bool $as_number = false): array|int|float|false {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param bool $binary [optional]
 */
function md5 (string $string, bool $binary = false): string {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param bool $binary [optional]
 */
function md5_file (string $filename, bool $binary = false): string|false {}

/**
 * {@inheritdoc}
 */
function getmyuid (): int|false {}

/**
 * {@inheritdoc}
 */
function getmygid (): int|false {}

/**
 * {@inheritdoc}
 */
function getmypid (): int|false {}

/**
 * {@inheritdoc}
 */
function getmyinode (): int|false {}

/**
 * {@inheritdoc}
 */
function getlastmod (): int|false {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param bool $binary [optional]
 */
function sha1 (string $string, bool $binary = false): string {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param bool $binary [optional]
 */
function sha1_file (string $filename, bool $binary = false): string|false {}

/**
 * {@inheritdoc}
 * @param string $prefix
 * @param int $flags
 * @param int $facility
 */
function openlog (string $prefix, int $flags, int $facility): true {}

/**
 * {@inheritdoc}
 */
function closelog (): true {}

/**
 * {@inheritdoc}
 * @param int $priority
 * @param string $message
 */
function syslog (int $priority, string $message): true {}

/**
 * {@inheritdoc}
 * @param string $ip
 */
function inet_ntop (string $ip): string|false {}

/**
 * {@inheritdoc}
 * @param string $ip
 */
function inet_pton (string $ip): string|false {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $max_phonemes [optional]
 */
function metaphone (string $string, int $max_phonemes = 0): string {}

/**
 * {@inheritdoc}
 * @param string $header
 * @param bool $replace [optional]
 * @param int $response_code [optional]
 */
function header (string $header, bool $replace = true, int $response_code = 0): void {}

/**
 * {@inheritdoc}
 * @param string|null $name [optional]
 */
function header_remove (?string $name = NULL): void {}

/**
 * {@inheritdoc}
 * @param string $name
 * @param string $value [optional]
 * @param array|int $expires_or_options [optional]
 * @param string $path [optional]
 * @param string $domain [optional]
 * @param bool $secure [optional]
 * @param bool $httponly [optional]
 */
function setrawcookie (string $name, string $value = '', array|int $expires_or_options = 0, string $path = '', string $domain = '', bool $secure = false, bool $httponly = false): bool {}

/**
 * {@inheritdoc}
 * @param string $name
 * @param string $value [optional]
 * @param array|int $expires_or_options [optional]
 * @param string $path [optional]
 * @param string $domain [optional]
 * @param bool $secure [optional]
 * @param bool $httponly [optional]
 */
function setcookie (string $name, string $value = '', array|int $expires_or_options = 0, string $path = '', string $domain = '', bool $secure = false, bool $httponly = false): bool {}

/**
 * {@inheritdoc}
 * @param int $response_code [optional]
 */
function http_response_code (int $response_code = 0): int|bool {}

/**
 * {@inheritdoc}
 * @param mixed $filename [optional]
 * @param mixed $line [optional]
 */
function headers_sent (&$filename = NULL, &$line = NULL): bool {}

/**
 * {@inheritdoc}
 */
function headers_list (): array {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $flags [optional]
 * @param string|null $encoding [optional]
 * @param bool $double_encode [optional]
 */
function htmlspecialchars (string $string, int $flags = 11, ?string $encoding = NULL, bool $double_encode = true): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $flags [optional]
 */
function htmlspecialchars_decode (string $string, int $flags = 11): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $flags [optional]
 * @param string|null $encoding [optional]
 */
function html_entity_decode (string $string, int $flags = 11, ?string $encoding = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $flags [optional]
 * @param string|null $encoding [optional]
 * @param bool $double_encode [optional]
 */
function htmlentities (string $string, int $flags = 11, ?string $encoding = NULL, bool $double_encode = true): string {}

/**
 * {@inheritdoc}
 * @param int $table [optional]
 * @param int $flags [optional]
 * @param string $encoding [optional]
 */
function get_html_translation_table (int $table = 0, int $flags = 11, string $encoding = 'UTF-8'): array {}

/**
 * {@inheritdoc}
 * @param mixed $assertion
 * @param Throwable|string|null $description [optional]
 */
function assert (mixed $assertion = null, Throwable|string|null $description = NULL): bool {}

/**
 * {@inheritdoc}
 * @param int $option
 * @param mixed $value [optional]
 * @deprecated 
 */
function assert_options (int $option, mixed $value = NULL): mixed {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function bin2hex (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function hex2bin (string $string): string|false {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string $characters
 * @param int $offset [optional]
 * @param int|null $length [optional]
 */
function strspn (string $string, string $characters, int $offset = 0, ?int $length = NULL): int {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string $characters
 * @param int $offset [optional]
 * @param int|null $length [optional]
 */
function strcspn (string $string, string $characters, int $offset = 0, ?int $length = NULL): int {}

/**
 * {@inheritdoc}
 * @param int $item
 */
function nl_langinfo (int $item): string|false {}

/**
 * {@inheritdoc}
 * @param string $string1
 * @param string $string2
 */
function strcoll (string $string1, string $string2): int {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string $characters [optional]
 */
function trim (string $string, string $characters = ' 
	' . "\0" . ''): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string $characters [optional]
 */
function rtrim (string $string, string $characters = ' 
	' . "\0" . ''): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string $characters [optional]
 */
function chop (string $string, string $characters = ' 
	' . "\0" . ''): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string $characters [optional]
 */
function ltrim (string $string, string $characters = ' 
	' . "\0" . ''): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $width [optional]
 * @param string $break [optional]
 * @param bool $cut_long_words [optional]
 */
function wordwrap (string $string, int $width = 75, string $break = '
', bool $cut_long_words = false): string {}

/**
 * {@inheritdoc}
 * @param string $separator
 * @param string $string
 * @param int $limit [optional]
 */
function explode (string $separator, string $string, int $limit = 9223372036854775807): array {}

/**
 * {@inheritdoc}
 * @param array|string $separator
 * @param array|null $array [optional]
 */
function implode (array|string $separator, ?array $array = NULL): string {}

/**
 * {@inheritdoc}
 * @param array|string $separator
 * @param array|null $array [optional]
 */
function join (array|string $separator, ?array $array = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string|null $token [optional]
 */
function strtok (string $string, ?string $token = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function strtoupper (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function strtolower (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function str_increment (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function str_decrement (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $path
 * @param string $suffix [optional]
 */
function basename (string $path, string $suffix = ''): string {}

/**
 * {@inheritdoc}
 * @param string $path
 * @param int $levels [optional]
 */
function dirname (string $path, int $levels = 1): string {}

/**
 * {@inheritdoc}
 * @param string $path
 * @param int $flags [optional]
 */
function pathinfo (string $path, int $flags = 15): array|string {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param bool $before_needle [optional]
 */
function stristr (string $haystack, string $needle, bool $before_needle = false): string|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param bool $before_needle [optional]
 */
function strstr (string $haystack, string $needle, bool $before_needle = false): string|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param bool $before_needle [optional]
 */
function strchr (string $haystack, string $needle, bool $before_needle = false): string|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param int $offset [optional]
 */
function strpos (string $haystack, string $needle, int $offset = 0): int|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param int $offset [optional]
 */
function stripos (string $haystack, string $needle, int $offset = 0): int|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param int $offset [optional]
 */
function strrpos (string $haystack, string $needle, int $offset = 0): int|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param int $offset [optional]
 */
function strripos (string $haystack, string $needle, int $offset = 0): int|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param bool $before_needle [optional]
 */
function strrchr (string $haystack, string $needle, bool $before_needle = false): string|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 */
function str_contains (string $haystack, string $needle): bool {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 */
function str_starts_with (string $haystack, string $needle): bool {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 */
function str_ends_with (string $haystack, string $needle): bool {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $length [optional]
 * @param string $separator [optional]
 */
function chunk_split (string $string, int $length = 76, string $separator = '
'): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $offset
 * @param int|null $length [optional]
 */
function substr (string $string, int $offset, ?int $length = NULL): string {}

/**
 * {@inheritdoc}
 * @param array|string $string
 * @param array|string $replace
 * @param array|int $offset
 * @param array|int|null $length [optional]
 */
function substr_replace (array|string $string, array|string $replace, array|int $offset, array|int|null $length = NULL): array|string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function quotemeta (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $character
 */
function ord (string $character): int {}

/**
 * {@inheritdoc}
 * @param int $codepoint
 */
function chr (int $codepoint): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function ucfirst (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function lcfirst (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string $separators [optional]
 */
function ucwords (string $string, string $separators = ' 	
'): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param array|string $from
 * @param string|null $to [optional]
 */
function strtr (string $string, array|string $from, ?string $to = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function strrev (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string1
 * @param string $string2
 * @param mixed $percent [optional]
 */
function similar_text (string $string1, string $string2, &$percent = NULL): int {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string $characters
 */
function addcslashes (string $string, string $characters): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function addslashes (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function stripcslashes (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function stripslashes (string $string): string {}

/**
 * {@inheritdoc}
 * @param array|string $search
 * @param array|string $replace
 * @param array|string $subject
 * @param mixed $count [optional]
 */
function str_replace (array|string $search, array|string $replace, array|string $subject, &$count = NULL): array|string {}

/**
 * {@inheritdoc}
 * @param array|string $search
 * @param array|string $replace
 * @param array|string $subject
 * @param mixed $count [optional]
 */
function str_ireplace (array|string $search, array|string $replace, array|string $subject, &$count = NULL): array|string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $max_chars_per_line [optional]
 */
function hebrev (string $string, int $max_chars_per_line = 0): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param bool $use_xhtml [optional]
 */
function nl2br (string $string, bool $use_xhtml = true): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param array|string|null $allowed_tags [optional]
 */
function strip_tags (string $string, array|string|null $allowed_tags = NULL): string {}

/**
 * {@inheritdoc}
 * @param int $category
 * @param mixed $locales
 * @param mixed $rest [optional]
 */
function setlocale (int $category, $locales = null, ...$rest): string|false {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param mixed $result
 */
function parse_str (string $string, &$result = null): void {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string $separator [optional]
 * @param string $enclosure [optional]
 * @param string $escape [optional]
 */
function str_getcsv (string $string, string $separator = ',', string $enclosure = '"', string $escape = '\\'): array {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $times
 */
function str_repeat (string $string, int $times): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $mode [optional]
 */
function count_chars (string $string, int $mode = 0): array|string {}

/**
 * {@inheritdoc}
 * @param string $string1
 * @param string $string2
 */
function strnatcmp (string $string1, string $string2): int {}

/**
 * {@inheritdoc}
 */
function localeconv (): array {}

/**
 * {@inheritdoc}
 * @param string $string1
 * @param string $string2
 */
function strnatcasecmp (string $string1, string $string2): int {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param int $offset [optional]
 * @param int|null $length [optional]
 */
function substr_count (string $haystack, string $needle, int $offset = 0, ?int $length = NULL): int {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $length
 * @param string $pad_string [optional]
 * @param int $pad_type [optional]
 */
function str_pad (string $string, int $length, string $pad_string = ' ', int $pad_type = 1): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string $format
 * @param mixed $vars [optional]
 */
function sscanf (string $string, string $format, mixed &...$vars): array|int|null {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function str_rot13 (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function str_shuffle (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $format [optional]
 * @param string|null $characters [optional]
 */
function str_word_count (string $string, int $format = 0, ?string $characters = NULL): array|int {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $length [optional]
 */
function str_split (string $string, int $length = 1): array {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string $characters
 */
function strpbrk (string $string, string $characters): string|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param int $offset
 * @param int|null $length [optional]
 * @param bool $case_insensitive [optional]
 */
function substr_compare (string $haystack, string $needle, int $offset, ?int $length = NULL, bool $case_insensitive = false): int {}

/**
 * {@inheritdoc}
 * @param string $string
 * @deprecated 
 */
function utf8_encode (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @deprecated 
 */
function utf8_decode (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $directory
 * @param mixed $context [optional]
 */
function opendir (string $directory, $context = NULL) {}

/**
 * {@inheritdoc}
 * @param string $directory
 * @param mixed $context [optional]
 */
function dir (string $directory, $context = NULL): Directory|false {}

/**
 * {@inheritdoc}
 * @param mixed $dir_handle [optional]
 */
function closedir ($dir_handle = NULL): void {}

/**
 * {@inheritdoc}
 * @param string $directory
 */
function chdir (string $directory): bool {}

/**
 * {@inheritdoc}
 */
function getcwd (): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $dir_handle [optional]
 */
function rewinddir ($dir_handle = NULL): void {}

/**
 * {@inheritdoc}
 * @param mixed $dir_handle [optional]
 */
function readdir ($dir_handle = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $directory
 * @param int $sorting_order [optional]
 * @param mixed $context [optional]
 */
function scandir (string $directory, int $sorting_order = 0, $context = NULL): array|false {}

/**
 * {@inheritdoc}
 * @param string $pattern
 * @param int $flags [optional]
 */
function glob (string $pattern, int $flags = 0): array|false {}

/**
 * {@inheritdoc}
 * @param string $command
 * @param mixed $output [optional]
 * @param mixed $result_code [optional]
 */
function exec (string $command, &$output = NULL, &$result_code = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $command
 * @param mixed $result_code [optional]
 */
function system (string $command, &$result_code = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $command
 * @param mixed $result_code [optional]
 */
function passthru (string $command, &$result_code = NULL): ?false {}

/**
 * {@inheritdoc}
 * @param string $command
 */
function escapeshellcmd (string $command): string {}

/**
 * {@inheritdoc}
 * @param string $arg
 */
function escapeshellarg (string $arg): string {}

/**
 * {@inheritdoc}
 * @param string $command
 */
function shell_exec (string $command): string|false|null {}

/**
 * {@inheritdoc}
 * @param int $priority
 */
function proc_nice (int $priority): bool {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param int $operation
 * @param mixed $would_block [optional]
 */
function flock ($stream = null, int $operation, &$would_block = NULL): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param bool $use_include_path [optional]
 */
function get_meta_tags (string $filename, bool $use_include_path = false): array|false {}

/**
 * {@inheritdoc}
 * @param mixed $handle
 */
function pclose ($handle = null): int {}

/**
 * {@inheritdoc}
 * @param string $command
 * @param string $mode
 */
function popen (string $command, string $mode) {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param bool $use_include_path [optional]
 * @param mixed $context [optional]
 */
function readfile (string $filename, bool $use_include_path = false, $context = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function rewind ($stream = null): bool {}

/**
 * {@inheritdoc}
 * @param string $directory
 * @param mixed $context [optional]
 */
function rmdir (string $directory, $context = NULL): bool {}

/**
 * {@inheritdoc}
 * @param int|null $mask [optional]
 */
function umask (?int $mask = NULL): int {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function fclose ($stream = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function feof ($stream = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function fgetc ($stream = null): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param int|null $length [optional]
 */
function fgets ($stream = null, ?int $length = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param int $length
 */
function fread ($stream = null, int $length): string|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param string $mode
 * @param bool $use_include_path [optional]
 * @param mixed $context [optional]
 */
function fopen (string $filename, string $mode, bool $use_include_path = false, $context = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param string $format
 * @param mixed $vars [optional]
 */
function fscanf ($stream = null, string $format, mixed &...$vars): array|int|false|null {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function fpassthru ($stream = null): int {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param int $size
 */
function ftruncate ($stream = null, int $size): bool {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function fstat ($stream = null): array|false {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param int $offset
 * @param int $whence [optional]
 */
function fseek ($stream = null, int $offset, int $whence = 0): int {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function ftell ($stream = null): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function fflush ($stream = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function fsync ($stream = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function fdatasync ($stream = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param string $data
 * @param int|null $length [optional]
 */
function fwrite ($stream = null, string $data, ?int $length = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param string $data
 * @param int|null $length [optional]
 */
function fputs ($stream = null, string $data, ?int $length = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param string $directory
 * @param int $permissions [optional]
 * @param bool $recursive [optional]
 * @param mixed $context [optional]
 */
function mkdir (string $directory, int $permissions = 511, bool $recursive = false, $context = NULL): bool {}

/**
 * {@inheritdoc}
 * @param string $from
 * @param string $to
 * @param mixed $context [optional]
 */
function rename (string $from, string $to, $context = NULL): bool {}

/**
 * {@inheritdoc}
 * @param string $from
 * @param string $to
 * @param mixed $context [optional]
 */
function copy (string $from, string $to, $context = NULL): bool {}

/**
 * {@inheritdoc}
 * @param string $directory
 * @param string $prefix
 */
function tempnam (string $directory, string $prefix): string|false {}

/**
 * {@inheritdoc}
 */
function tmpfile () {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param int $flags [optional]
 * @param mixed $context [optional]
 */
function file (string $filename, int $flags = 0, $context = NULL): array|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param bool $use_include_path [optional]
 * @param mixed $context [optional]
 * @param int $offset [optional]
 * @param int|null $length [optional]
 */
function file_get_contents (string $filename, bool $use_include_path = false, $context = NULL, int $offset = 0, ?int $length = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param mixed $context [optional]
 */
function unlink (string $filename, $context = NULL): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param mixed $data
 * @param int $flags [optional]
 * @param mixed $context [optional]
 */
function file_put_contents (string $filename, mixed $data = null, int $flags = 0, $context = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param array $fields
 * @param string $separator [optional]
 * @param string $enclosure [optional]
 * @param string $escape [optional]
 * @param string $eol [optional]
 */
function fputcsv ($stream = null, array $fields, string $separator = ',', string $enclosure = '"', string $escape = '\\', string $eol = '
'): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param int|null $length [optional]
 * @param string $separator [optional]
 * @param string $enclosure [optional]
 * @param string $escape [optional]
 */
function fgetcsv ($stream = null, ?int $length = NULL, string $separator = ',', string $enclosure = '"', string $escape = '\\'): array|false {}

/**
 * {@inheritdoc}
 * @param string $path
 */
function realpath (string $path): string|false {}

/**
 * {@inheritdoc}
 * @param string $pattern
 * @param string $filename
 * @param int $flags [optional]
 */
function fnmatch (string $pattern, string $filename, int $flags = 0): bool {}

/**
 * {@inheritdoc}
 */
function sys_get_temp_dir (): string {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function fileatime (string $filename): int|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function filectime (string $filename): int|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function filegroup (string $filename): int|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function fileinode (string $filename): int|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function filemtime (string $filename): int|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function fileowner (string $filename): int|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function fileperms (string $filename): int|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function filesize (string $filename): int|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function filetype (string $filename): string|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function file_exists (string $filename): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function is_writable (string $filename): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function is_writeable (string $filename): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function is_readable (string $filename): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function is_executable (string $filename): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function is_file (string $filename): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function is_dir (string $filename): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function is_link (string $filename): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function stat (string $filename): array|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function lstat (string $filename): array|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param string|int $user
 */
function chown (string $filename, string|int $user): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param string|int $group
 */
function chgrp (string $filename, string|int $group): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param string|int $user
 */
function lchown (string $filename, string|int $user): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param string|int $group
 */
function lchgrp (string $filename, string|int $group): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param int $permissions
 */
function chmod (string $filename, int $permissions): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param int|null $mtime [optional]
 * @param int|null $atime [optional]
 */
function touch (string $filename, ?int $mtime = NULL, ?int $atime = NULL): bool {}

/**
 * {@inheritdoc}
 * @param bool $clear_realpath_cache [optional]
 * @param string $filename [optional]
 */
function clearstatcache (bool $clear_realpath_cache = false, string $filename = ''): void {}

/**
 * {@inheritdoc}
 * @param string $directory
 */
function disk_total_space (string $directory): float|false {}

/**
 * {@inheritdoc}
 * @param string $directory
 */
function disk_free_space (string $directory): float|false {}

/**
 * {@inheritdoc}
 * @param string $directory
 */
function diskfreespace (string $directory): float|false {}

/**
 * {@inheritdoc}
 */
function realpath_cache_get (): array {}

/**
 * {@inheritdoc}
 */
function realpath_cache_size (): int {}

/**
 * {@inheritdoc}
 * @param string $format
 * @param mixed $values [optional]
 */
function sprintf (string $format, mixed ...$values): string {}

/**
 * {@inheritdoc}
 * @param string $format
 * @param mixed $values [optional]
 */
function printf (string $format, mixed ...$values): int {}

/**
 * {@inheritdoc}
 * @param string $format
 * @param array $values
 */
function vprintf (string $format, array $values): int {}

/**
 * {@inheritdoc}
 * @param string $format
 * @param array $values
 */
function vsprintf (string $format, array $values): string {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param string $format
 * @param mixed $values [optional]
 */
function fprintf ($stream = null, string $format, mixed ...$values): int {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param string $format
 * @param array $values
 */
function vfprintf ($stream = null, string $format, array $values): int {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param int $port [optional]
 * @param mixed $error_code [optional]
 * @param mixed $error_message [optional]
 * @param float|null $timeout [optional]
 */
function fsockopen (string $hostname, int $port = -1, &$error_code = NULL, &$error_message = NULL, ?float $timeout = NULL) {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param int $port [optional]
 * @param mixed $error_code [optional]
 * @param mixed $error_message [optional]
 * @param float|null $timeout [optional]
 */
function pfsockopen (string $hostname, int $port = -1, &$error_code = NULL, &$error_message = NULL, ?float $timeout = NULL) {}

/**
 * {@inheritdoc}
 * @param object|array $data
 * @param string $numeric_prefix [optional]
 * @param string|null $arg_separator [optional]
 * @param int $encoding_type [optional]
 */
function http_build_query (object|array $data, string $numeric_prefix = '', ?string $arg_separator = NULL, int $encoding_type = 1): string {}

/**
 * {@inheritdoc}
 * @param int $image_type
 */
function image_type_to_mime_type (int $image_type): string {}

/**
 * {@inheritdoc}
 * @param int $image_type
 * @param bool $include_dot [optional]
 */
function image_type_to_extension (int $image_type, bool $include_dot = true): string|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param mixed $image_info [optional]
 */
function getimagesize (string $filename, &$image_info = NULL): array|false {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param mixed $image_info [optional]
 */
function getimagesizefromstring (string $string, &$image_info = NULL): array|false {}

/**
 * {@inheritdoc}
 * @param int $flags [optional]
 */
function phpinfo (int $flags = 4294967295): true {}

/**
 * {@inheritdoc}
 * @param string|null $extension [optional]
 */
function phpversion (?string $extension = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param int $flags [optional]
 */
function phpcredits (int $flags = 4294967295): true {}

/**
 * {@inheritdoc}
 */
function php_sapi_name (): string|false {}

/**
 * {@inheritdoc}
 * @param string $mode [optional]
 */
function php_uname (string $mode = 'a'): string {}

/**
 * {@inheritdoc}
 */
function php_ini_scanned_files (): string|false {}

/**
 * {@inheritdoc}
 */
function php_ini_loaded_file (): string|false {}

/**
 * {@inheritdoc}
 * @param string $iptc_data
 * @param string $filename
 * @param int $spool [optional]
 */
function iptcembed (string $iptc_data, string $filename, int $spool = 0): string|bool {}

/**
 * {@inheritdoc}
 * @param string $iptc_block
 */
function iptcparse (string $iptc_block): array|false {}

/**
 * {@inheritdoc}
 * @param string $string1
 * @param string $string2
 * @param int $insertion_cost [optional]
 * @param int $replacement_cost [optional]
 * @param int $deletion_cost [optional]
 */
function levenshtein (string $string1, string $string2, int $insertion_cost = 1, int $replacement_cost = 1, int $deletion_cost = 1): int {}

/**
 * {@inheritdoc}
 * @param string $path
 */
function readlink (string $path): string|false {}

/**
 * {@inheritdoc}
 * @param string $path
 */
function linkinfo (string $path): int|false {}

/**
 * {@inheritdoc}
 * @param string $target
 * @param string $link
 */
function symlink (string $target, string $link): bool {}

/**
 * {@inheritdoc}
 * @param string $target
 * @param string $link
 */
function link (string $target, string $link): bool {}

/**
 * {@inheritdoc}
 * @param string $to
 * @param string $subject
 * @param string $message
 * @param array|string $additional_headers [optional]
 * @param string $additional_params [optional]
 */
function mail (string $to, string $subject, string $message, array|string $additional_headers = array (
), string $additional_params = ''): bool {}

/**
 * {@inheritdoc}
 * @param int|float $num
 */
function abs (int|float $num): int|float {}

/**
 * {@inheritdoc}
 * @param int|float $num
 */
function ceil (int|float $num): float {}

/**
 * {@inheritdoc}
 * @param int|float $num
 */
function floor (int|float $num): float {}

/**
 * {@inheritdoc}
 * @param int|float $num
 * @param int $precision [optional]
 * @param int $mode [optional]
 */
function round (int|float $num, int $precision = 0, int $mode = 1): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function sin (float $num): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function cos (float $num): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function tan (float $num): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function asin (float $num): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function acos (float $num): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function atan (float $num): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function atanh (float $num): float {}

/**
 * {@inheritdoc}
 * @param float $y
 * @param float $x
 */
function atan2 (float $y, float $x): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function sinh (float $num): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function cosh (float $num): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function tanh (float $num): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function asinh (float $num): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function acosh (float $num): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function expm1 (float $num): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function log1p (float $num): float {}

/**
 * {@inheritdoc}
 */
function pi (): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function is_finite (float $num): bool {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function is_nan (float $num): bool {}

/**
 * {@inheritdoc}
 * @param int $num1
 * @param int $num2
 */
function intdiv (int $num1, int $num2): int {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function is_infinite (float $num): bool {}

/**
 * {@inheritdoc}
 * @param mixed $num
 * @param mixed $exponent
 */
function pow (mixed $num = null, mixed $exponent = null): object|int|float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function exp (float $num): float {}

/**
 * {@inheritdoc}
 * @param float $num
 * @param float $base [optional]
 */
function log (float $num, float $base = 2.718281828459045): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function log10 (float $num): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function sqrt (float $num): float {}

/**
 * {@inheritdoc}
 * @param float $x
 * @param float $y
 */
function hypot (float $x, float $y): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function deg2rad (float $num): float {}

/**
 * {@inheritdoc}
 * @param float $num
 */
function rad2deg (float $num): float {}

/**
 * {@inheritdoc}
 * @param string $binary_string
 */
function bindec (string $binary_string): int|float {}

/**
 * {@inheritdoc}
 * @param string $hex_string
 */
function hexdec (string $hex_string): int|float {}

/**
 * {@inheritdoc}
 * @param string $octal_string
 */
function octdec (string $octal_string): int|float {}

/**
 * {@inheritdoc}
 * @param int $num
 */
function decbin (int $num): string {}

/**
 * {@inheritdoc}
 * @param int $num
 */
function decoct (int $num): string {}

/**
 * {@inheritdoc}
 * @param int $num
 */
function dechex (int $num): string {}

/**
 * {@inheritdoc}
 * @param string $num
 * @param int $from_base
 * @param int $to_base
 */
function base_convert (string $num, int $from_base, int $to_base): string {}

/**
 * {@inheritdoc}
 * @param float $num
 * @param int $decimals [optional]
 * @param string|null $decimal_separator [optional]
 * @param string|null $thousands_separator [optional]
 */
function number_format (float $num, int $decimals = 0, ?string $decimal_separator = '.', ?string $thousands_separator = ','): string {}

/**
 * {@inheritdoc}
 * @param float $num1
 * @param float $num2
 */
function fmod (float $num1, float $num2): float {}

/**
 * {@inheritdoc}
 * @param float $num1
 * @param float $num2
 */
function fdiv (float $num1, float $num2): float {}

/**
 * {@inheritdoc}
 * @param bool $as_float [optional]
 */
function microtime (bool $as_float = false): string|float {}

/**
 * {@inheritdoc}
 * @param bool $as_float [optional]
 */
function gettimeofday (bool $as_float = false): array|float {}

/**
 * {@inheritdoc}
 * @param int $mode [optional]
 */
function getrusage (int $mode = 0): array|false {}

/**
 * {@inheritdoc}
 * @param string $format
 * @param mixed $values [optional]
 */
function pack (string $format, mixed ...$values): string {}

/**
 * {@inheritdoc}
 * @param string $format
 * @param string $string
 * @param int $offset [optional]
 */
function unpack (string $format, string $string, int $offset = 0): array|false {}

/**
 * {@inheritdoc}
 * @param string $hash
 */
function password_get_info (string $hash): array {}

/**
 * {@inheritdoc}
 * @param string $password
 * @param string|int|null $algo
 * @param array $options [optional]
 */
function password_hash (string $password, string|int|null $algo = null, array $options = array (
)): string {}

/**
 * {@inheritdoc}
 * @param string $hash
 * @param string|int|null $algo
 * @param array $options [optional]
 */
function password_needs_rehash (string $hash, string|int|null $algo = null, array $options = array (
)): bool {}

/**
 * {@inheritdoc}
 * @param string $password
 * @param string $hash
 */
function password_verify (string $password, string $hash): bool {}

/**
 * {@inheritdoc}
 */
function password_algos (): array {}

/**
 * {@inheritdoc}
 * @param array|string $command
 * @param array $descriptor_spec
 * @param mixed $pipes
 * @param string|null $cwd [optional]
 * @param array|null $env_vars [optional]
 * @param array|null $options [optional]
 */
function proc_open (array|string $command, array $descriptor_spec, &$pipes = null, ?string $cwd = NULL, ?array $env_vars = NULL, ?array $options = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $process
 */
function proc_close ($process = null): int {}

/**
 * {@inheritdoc}
 * @param mixed $process
 * @param int $signal [optional]
 */
function proc_terminate ($process = null, int $signal = 15): bool {}

/**
 * {@inheritdoc}
 * @param mixed $process
 */
function proc_get_status ($process = null): array {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function quoted_printable_decode (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function quoted_printable_encode (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function soundex (string $string): string {}

/**
 * {@inheritdoc}
 * @param array|null $read
 * @param array|null $write
 * @param array|null $except
 * @param int|null $seconds
 * @param int|null $microseconds [optional]
 */
function stream_select (?array &$read = null, ?array &$write = null, ?array &$except = null, ?int $seconds = null, ?int $microseconds = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param array|null $options [optional]
 * @param array|null $params [optional]
 */
function stream_context_create (?array $options = NULL, ?array $params = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $context
 * @param array $params
 */
function stream_context_set_params ($context = null, array $params): bool {}

/**
 * {@inheritdoc}
 * @param mixed $context
 */
function stream_context_get_params ($context = null): array {}

/**
 * {@inheritdoc}
 * @param mixed $context
 * @param array|string $wrapper_or_options
 * @param string|null $option_name [optional]
 * @param mixed $value [optional]
 */
function stream_context_set_option ($context = null, array|string $wrapper_or_options, ?string $option_name = NULL, mixed $value = NULL): bool {}

/**
 * {@inheritdoc}
 * @param mixed $context
 * @param array $options
 */
function stream_context_set_options ($context = null, array $options): bool {}

/**
 * {@inheritdoc}
 * @param mixed $stream_or_context
 */
function stream_context_get_options ($stream_or_context = null): array {}

/**
 * {@inheritdoc}
 * @param array|null $options [optional]
 */
function stream_context_get_default (?array $options = NULL) {}

/**
 * {@inheritdoc}
 * @param array $options
 */
function stream_context_set_default (array $options) {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param string $filter_name
 * @param int $mode [optional]
 * @param mixed $params [optional]
 */
function stream_filter_prepend ($stream = null, string $filter_name, int $mode = 0, mixed $params = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param string $filter_name
 * @param int $mode [optional]
 * @param mixed $params [optional]
 */
function stream_filter_append ($stream = null, string $filter_name, int $mode = 0, mixed $params = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $stream_filter
 */
function stream_filter_remove ($stream_filter = null): bool {}

/**
 * {@inheritdoc}
 * @param string $address
 * @param mixed $error_code [optional]
 * @param mixed $error_message [optional]
 * @param float|null $timeout [optional]
 * @param int $flags [optional]
 * @param mixed $context [optional]
 */
function stream_socket_client (string $address, &$error_code = NULL, &$error_message = NULL, ?float $timeout = NULL, int $flags = 4, $context = NULL) {}

/**
 * {@inheritdoc}
 * @param string $address
 * @param mixed $error_code [optional]
 * @param mixed $error_message [optional]
 * @param int $flags [optional]
 * @param mixed $context [optional]
 */
function stream_socket_server (string $address, &$error_code = NULL, &$error_message = NULL, int $flags = 12, $context = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $socket
 * @param float|null $timeout [optional]
 * @param mixed $peer_name [optional]
 */
function stream_socket_accept ($socket = null, ?float $timeout = NULL, &$peer_name = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $socket
 * @param bool $remote
 */
function stream_socket_get_name ($socket = null, bool $remote): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $socket
 * @param int $length
 * @param int $flags [optional]
 * @param mixed $address [optional]
 */
function stream_socket_recvfrom ($socket = null, int $length, int $flags = 0, &$address = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $socket
 * @param string $data
 * @param int $flags [optional]
 * @param string $address [optional]
 */
function stream_socket_sendto ($socket = null, string $data, int $flags = 0, string $address = ''): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param bool $enable
 * @param int|null $crypto_method [optional]
 * @param mixed $session_stream [optional]
 */
function stream_socket_enable_crypto ($stream = null, bool $enable, ?int $crypto_method = NULL, $session_stream = NULL): int|bool {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param int $mode
 */
function stream_socket_shutdown ($stream = null, int $mode): bool {}

/**
 * {@inheritdoc}
 * @param int $domain
 * @param int $type
 * @param int $protocol
 */
function stream_socket_pair (int $domain, int $type, int $protocol): array|false {}

/**
 * {@inheritdoc}
 * @param mixed $from
 * @param mixed $to
 * @param int|null $length [optional]
 * @param int $offset [optional]
 */
function stream_copy_to_stream ($from = null, $to = null, ?int $length = NULL, int $offset = 0): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param int|null $length [optional]
 * @param int $offset [optional]
 */
function stream_get_contents ($stream = null, ?int $length = NULL, int $offset = -1): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function stream_supports_lock ($stream = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param int $size
 */
function stream_set_write_buffer ($stream = null, int $size): int {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param int $size
 */
function set_file_buffer ($stream = null, int $size): int {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param int $size
 */
function stream_set_read_buffer ($stream = null, int $size): int {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param bool $enable
 */
function stream_set_blocking ($stream = null, bool $enable): bool {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param bool $enable
 */
function socket_set_blocking ($stream = null, bool $enable): bool {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function stream_get_meta_data ($stream = null): array {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function socket_get_status ($stream = null): array {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param int $length
 * @param string $ending [optional]
 */
function stream_get_line ($stream = null, int $length, string $ending = ''): string|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function stream_resolve_include_path (string $filename): string|false {}

/**
 * {@inheritdoc}
 */
function stream_get_wrappers (): array {}

/**
 * {@inheritdoc}
 */
function stream_get_transports (): array {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function stream_is_local ($stream = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function stream_isatty ($stream = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param int $size
 */
function stream_set_chunk_size ($stream = null, int $size): int {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param int $seconds
 * @param int $microseconds [optional]
 */
function stream_set_timeout ($stream = null, int $seconds, int $microseconds = 0): bool {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param int $seconds
 * @param int $microseconds [optional]
 */
function socket_set_timeout ($stream = null, int $seconds, int $microseconds = 0): bool {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function gettype (mixed $value = null): string {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function get_debug_type (mixed $value = null): string {}

/**
 * {@inheritdoc}
 * @param mixed $var
 * @param string $type
 */
function settype (mixed &$var = null, string $type): bool {}

/**
 * {@inheritdoc}
 * @param mixed $value
 * @param int $base [optional]
 */
function intval (mixed $value = null, int $base = 10): int {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function floatval (mixed $value = null): float {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function doubleval (mixed $value = null): float {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function boolval (mixed $value = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function strval (mixed $value = null): string {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function is_null (mixed $value = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function is_resource (mixed $value = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function is_bool (mixed $value = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function is_int (mixed $value = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function is_integer (mixed $value = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function is_long (mixed $value = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function is_float (mixed $value = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function is_double (mixed $value = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function is_numeric (mixed $value = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function is_string (mixed $value = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function is_array (mixed $value = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function is_object (mixed $value = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function is_scalar (mixed $value = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $value
 * @param bool $syntax_only [optional]
 * @param mixed $callable_name [optional]
 */
function is_callable (mixed $value = null, bool $syntax_only = false, &$callable_name = NULL): bool {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function is_iterable (mixed $value = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function is_countable (mixed $value = null): bool {}

/**
 * {@inheritdoc}
 * @param string $prefix [optional]
 * @param bool $more_entropy [optional]
 */
function uniqid (string $prefix = '', bool $more_entropy = false): string {}

/**
 * {@inheritdoc}
 * @param string $url
 * @param int $component [optional]
 */
function parse_url (string $url, int $component = -1): array|string|int|false|null {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function urlencode (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function urldecode (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function rawurlencode (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function rawurldecode (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $url
 * @param bool $associative [optional]
 * @param mixed $context [optional]
 */
function get_headers (string $url, bool $associative = false, $context = NULL): array|false {}

/**
 * {@inheritdoc}
 * @param mixed $brigade
 */
function stream_bucket_make_writeable ($brigade = null): ?object {}

/**
 * {@inheritdoc}
 * @param mixed $brigade
 * @param object $bucket
 */
function stream_bucket_prepend ($brigade = null, object $bucket): void {}

/**
 * {@inheritdoc}
 * @param mixed $brigade
 * @param object $bucket
 */
function stream_bucket_append ($brigade = null, object $bucket): void {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param string $buffer
 */
function stream_bucket_new ($stream = null, string $buffer): object {}

/**
 * {@inheritdoc}
 */
function stream_get_filters (): array {}

/**
 * {@inheritdoc}
 * @param string $filter_name
 * @param string $class
 */
function stream_filter_register (string $filter_name, string $class): bool {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function convert_uuencode (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function convert_uudecode (string $string): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $value
 * @param mixed $values [optional]
 */
function var_dump (mixed $value = null, mixed ...$values): void {}

/**
 * {@inheritdoc}
 * @param mixed $value
 * @param bool $return [optional]
 */
function var_export (mixed $value = null, bool $return = false): ?string {}

/**
 * {@inheritdoc}
 * @param mixed $value
 * @param mixed $values [optional]
 */
function debug_zval_dump (mixed $value = null, mixed ...$values): void {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function serialize (mixed $value = null): string {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param array $options [optional]
 */
function unserialize (string $data, array $options = array (
)): mixed {}

/**
 * {@inheritdoc}
 * @param bool $real_usage [optional]
 */
function memory_get_usage (bool $real_usage = false): int {}

/**
 * {@inheritdoc}
 * @param bool $real_usage [optional]
 */
function memory_get_peak_usage (bool $real_usage = false): int {}

/**
 * {@inheritdoc}
 */
function memory_reset_peak_usage (): void {}

/**
 * {@inheritdoc}
 * @param string $version1
 * @param string $version2
 * @param string|null $operator [optional]
 */
function version_compare (string $version1, string $version2, ?string $operator = NULL): int|bool {}

/**
 * {@inheritdoc}
 * @param string $extension_filename
 */
function dl (string $extension_filename): bool {}

/**
 * {@inheritdoc}
 * @param string $title
 */
function cli_set_process_title (string $title): bool {}

/**
 * {@inheritdoc}
 */
function cli_get_process_title (): ?string {}

define ('EXTR_OVERWRITE', 0);
define ('EXTR_SKIP', 1);
define ('EXTR_PREFIX_SAME', 2);
define ('EXTR_PREFIX_ALL', 3);
define ('EXTR_PREFIX_INVALID', 4);
define ('EXTR_PREFIX_IF_EXISTS', 5);
define ('EXTR_IF_EXISTS', 6);
define ('EXTR_REFS', 256);
define ('SORT_ASC', 4);
define ('SORT_DESC', 3);
define ('SORT_REGULAR', 0);
define ('SORT_NUMERIC', 1);
define ('SORT_STRING', 2);
define ('SORT_LOCALE_STRING', 5);
define ('SORT_NATURAL', 6);
define ('SORT_FLAG_CASE', 8);
define ('CASE_LOWER', 0);
define ('CASE_UPPER', 1);
define ('COUNT_NORMAL', 0);
define ('COUNT_RECURSIVE', 1);
define ('ARRAY_FILTER_USE_BOTH', 1);
define ('ARRAY_FILTER_USE_KEY', 2);
define ('ASSERT_ACTIVE', 1);
define ('ASSERT_CALLBACK', 2);
define ('ASSERT_BAIL', 3);
define ('ASSERT_WARNING', 4);
define ('ASSERT_EXCEPTION', 5);
define ('CONNECTION_ABORTED', 1);
define ('CONNECTION_NORMAL', 0);
define ('CONNECTION_TIMEOUT', 2);
define ('INI_USER', 1);
define ('INI_PERDIR', 2);
define ('INI_SYSTEM', 4);
define ('INI_ALL', 7);
define ('INI_SCANNER_NORMAL', 0);
define ('INI_SCANNER_RAW', 1);
define ('INI_SCANNER_TYPED', 2);
define ('PHP_URL_SCHEME', 0);
define ('PHP_URL_HOST', 1);
define ('PHP_URL_PORT', 2);
define ('PHP_URL_USER', 3);
define ('PHP_URL_PASS', 4);
define ('PHP_URL_PATH', 5);
define ('PHP_URL_QUERY', 6);
define ('PHP_URL_FRAGMENT', 7);
define ('PHP_QUERY_RFC1738', 1);
define ('PHP_QUERY_RFC3986', 2);
define ('M_E', 2.718281828459);
define ('M_LOG2E', 1.442695040889);
define ('M_LOG10E', 0.43429448190325);
define ('M_LN2', 0.69314718055995);
define ('M_LN10', 2.302585092994);
define ('M_PI', 3.1415926535898);
define ('M_PI_2', 1.5707963267949);
define ('M_PI_4', 0.78539816339745);
define ('M_1_PI', 0.31830988618379);
define ('M_2_PI', 0.63661977236758);
define ('M_SQRTPI', 1.7724538509055);
define ('M_2_SQRTPI', 1.1283791670955);
define ('M_LNPI', 1.1447298858494);
define ('M_EULER', 0.57721566490153);
define ('M_SQRT2', 1.4142135623731);
define ('M_SQRT1_2', 0.70710678118655);
define ('M_SQRT3', 1.7320508075689);
define ('INF', INF);
define ('NAN', NAN);
define ('PHP_ROUND_HALF_UP', 1);
define ('PHP_ROUND_HALF_DOWN', 2);
define ('PHP_ROUND_HALF_EVEN', 3);
define ('PHP_ROUND_HALF_ODD', 4);
define ('CRYPT_SALT_LENGTH', 123);
define ('CRYPT_STD_DES', 1);
define ('CRYPT_EXT_DES', 1);
define ('CRYPT_MD5', 1);
define ('CRYPT_BLOWFISH', 1);
define ('CRYPT_SHA256', 1);
define ('CRYPT_SHA512', 1);
define ('DNS_A', 1);
define ('DNS_NS', 2);
define ('DNS_CNAME', 16);
define ('DNS_SOA', 32);
define ('DNS_PTR', 2048);
define ('DNS_HINFO', 4096);
define ('DNS_CAA', 8192);
define ('DNS_MX', 16384);
define ('DNS_TXT', 32768);
define ('DNS_SRV', 33554432);
define ('DNS_NAPTR', 67108864);
define ('DNS_AAAA', 134217728);
define ('DNS_A6', 16777216);
define ('DNS_ANY', 268435456);
define ('DNS_ALL', 251721779);
define ('HTML_SPECIALCHARS', 0);
define ('HTML_ENTITIES', 1);
define ('ENT_COMPAT', 2);
define ('ENT_QUOTES', 3);
define ('ENT_NOQUOTES', 0);
define ('ENT_IGNORE', 4);
define ('ENT_SUBSTITUTE', 8);
define ('ENT_DISALLOWED', 128);
define ('ENT_HTML401', 0);
define ('ENT_XML1', 16);
define ('ENT_XHTML', 32);
define ('ENT_HTML5', 48);
define ('IMAGETYPE_GIF', 1);
define ('IMAGETYPE_JPEG', 2);
define ('IMAGETYPE_PNG', 3);
define ('IMAGETYPE_SWF', 4);
define ('IMAGETYPE_PSD', 5);
define ('IMAGETYPE_BMP', 6);
define ('IMAGETYPE_TIFF_II', 7);
define ('IMAGETYPE_TIFF_MM', 8);
define ('IMAGETYPE_JPC', 9);
define ('IMAGETYPE_JP2', 10);
define ('IMAGETYPE_JPX', 11);
define ('IMAGETYPE_JB2', 12);
define ('IMAGETYPE_SWC', 13);
define ('IMAGETYPE_IFF', 14);
define ('IMAGETYPE_WBMP', 15);
define ('IMAGETYPE_JPEG2000', 9);
define ('IMAGETYPE_XBM', 16);
define ('IMAGETYPE_ICO', 17);
define ('IMAGETYPE_WEBP', 18);
define ('IMAGETYPE_AVIF', 19);
define ('IMAGETYPE_UNKNOWN', 0);
define ('IMAGETYPE_COUNT', 20);
define ('INFO_GENERAL', 1);
define ('INFO_CREDITS', 2);
define ('INFO_CONFIGURATION', 4);
define ('INFO_MODULES', 8);
define ('INFO_ENVIRONMENT', 16);
define ('INFO_VARIABLES', 32);
define ('INFO_LICENSE', 64);
define ('INFO_ALL', 4294967295);
define ('CREDITS_GROUP', 1);
define ('CREDITS_GENERAL', 2);
define ('CREDITS_SAPI', 4);
define ('CREDITS_MODULES', 8);
define ('CREDITS_DOCS', 16);
define ('CREDITS_FULLPAGE', 32);
define ('CREDITS_QA', 64);
define ('CREDITS_ALL', 4294967295);
define ('LOG_EMERG', 0);
define ('LOG_ALERT', 1);
define ('LOG_CRIT', 2);
define ('LOG_ERR', 3);
define ('LOG_WARNING', 4);
define ('LOG_NOTICE', 5);
define ('LOG_INFO', 6);
define ('LOG_DEBUG', 7);
define ('LOG_KERN', 0);
define ('LOG_USER', 8);
define ('LOG_MAIL', 16);
define ('LOG_DAEMON', 24);
define ('LOG_AUTH', 32);
define ('LOG_SYSLOG', 40);
define ('LOG_LPR', 48);
define ('LOG_NEWS', 56);
define ('LOG_UUCP', 64);
define ('LOG_CRON', 72);
define ('LOG_AUTHPRIV', 80);
define ('LOG_LOCAL0', 128);
define ('LOG_LOCAL1', 136);
define ('LOG_LOCAL2', 144);
define ('LOG_LOCAL3', 152);
define ('LOG_LOCAL4', 160);
define ('LOG_LOCAL5', 168);
define ('LOG_LOCAL6', 176);
define ('LOG_LOCAL7', 184);
define ('LOG_PID', 1);
define ('LOG_CONS', 2);
define ('LOG_ODELAY', 4);
define ('LOG_NDELAY', 8);
define ('LOG_NOWAIT', 16);
define ('LOG_PERROR', 32);
define ('STR_PAD_LEFT', 0);
define ('STR_PAD_RIGHT', 1);
define ('STR_PAD_BOTH', 2);
define ('PATHINFO_DIRNAME', 1);
define ('PATHINFO_BASENAME', 2);
define ('PATHINFO_EXTENSION', 4);
define ('PATHINFO_FILENAME', 8);
define ('PATHINFO_ALL', 15);
define ('CHAR_MAX', 127);
define ('LC_CTYPE', 2);
define ('LC_NUMERIC', 4);
define ('LC_TIME', 5);
define ('LC_COLLATE', 1);
define ('LC_MONETARY', 3);
define ('LC_ALL', 0);
define ('LC_MESSAGES', 6);
define ('ABDAY_1', 14);
define ('ABDAY_2', 15);
define ('ABDAY_3', 16);
define ('ABDAY_4', 17);
define ('ABDAY_5', 18);
define ('ABDAY_6', 19);
define ('ABDAY_7', 20);
define ('DAY_1', 7);
define ('DAY_2', 8);
define ('DAY_3', 9);
define ('DAY_4', 10);
define ('DAY_5', 11);
define ('DAY_6', 12);
define ('DAY_7', 13);
define ('ABMON_1', 33);
define ('ABMON_2', 34);
define ('ABMON_3', 35);
define ('ABMON_4', 36);
define ('ABMON_5', 37);
define ('ABMON_6', 38);
define ('ABMON_7', 39);
define ('ABMON_8', 40);
define ('ABMON_9', 41);
define ('ABMON_10', 42);
define ('ABMON_11', 43);
define ('ABMON_12', 44);
define ('MON_1', 21);
define ('MON_2', 22);
define ('MON_3', 23);
define ('MON_4', 24);
define ('MON_5', 25);
define ('MON_6', 26);
define ('MON_7', 27);
define ('MON_8', 28);
define ('MON_9', 29);
define ('MON_10', 30);
define ('MON_11', 31);
define ('MON_12', 32);
define ('AM_STR', 5);
define ('PM_STR', 6);
define ('D_T_FMT', 1);
define ('D_FMT', 2);
define ('T_FMT', 3);
define ('T_FMT_AMPM', 4);
define ('ERA', 45);
define ('ERA_D_T_FMT', 47);
define ('ERA_D_FMT', 46);
define ('ERA_T_FMT', 48);
define ('ALT_DIGITS', 49);
define ('CRNCYSTR', 56);
define ('RADIXCHAR', 50);
define ('THOUSEP', 51);
define ('YESEXPR', 52);
define ('NOEXPR', 53);
define ('YESSTR', 54);
define ('NOSTR', 55);
define ('CODESET', 0);
define ('SEEK_SET', 0);
define ('SEEK_CUR', 1);
define ('SEEK_END', 2);
define ('LOCK_SH', 1);
define ('LOCK_EX', 2);
define ('LOCK_UN', 3);
define ('LOCK_NB', 4);
define ('STREAM_NOTIFY_CONNECT', 2);
define ('STREAM_NOTIFY_AUTH_REQUIRED', 3);
define ('STREAM_NOTIFY_AUTH_RESULT', 10);
define ('STREAM_NOTIFY_MIME_TYPE_IS', 4);
define ('STREAM_NOTIFY_FILE_SIZE_IS', 5);
define ('STREAM_NOTIFY_REDIRECTED', 6);
define ('STREAM_NOTIFY_PROGRESS', 7);
define ('STREAM_NOTIFY_FAILURE', 9);
define ('STREAM_NOTIFY_COMPLETED', 8);
define ('STREAM_NOTIFY_RESOLVE', 1);
define ('STREAM_NOTIFY_SEVERITY_INFO', 0);
define ('STREAM_NOTIFY_SEVERITY_WARN', 1);
define ('STREAM_NOTIFY_SEVERITY_ERR', 2);
define ('STREAM_FILTER_READ', 1);
define ('STREAM_FILTER_WRITE', 2);
define ('STREAM_FILTER_ALL', 3);
define ('STREAM_CLIENT_PERSISTENT', 1);
define ('STREAM_CLIENT_ASYNC_CONNECT', 2);
define ('STREAM_CLIENT_CONNECT', 4);
define ('STREAM_CRYPTO_METHOD_ANY_CLIENT', 127);
define ('STREAM_CRYPTO_METHOD_SSLv2_CLIENT', 3);
define ('STREAM_CRYPTO_METHOD_SSLv3_CLIENT', 5);
define ('STREAM_CRYPTO_METHOD_SSLv23_CLIENT', 57);
define ('STREAM_CRYPTO_METHOD_TLS_CLIENT', 121);
define ('STREAM_CRYPTO_METHOD_TLSv1_0_CLIENT', 9);
define ('STREAM_CRYPTO_METHOD_TLSv1_1_CLIENT', 17);
define ('STREAM_CRYPTO_METHOD_TLSv1_2_CLIENT', 33);
define ('STREAM_CRYPTO_METHOD_TLSv1_3_CLIENT', 65);
define ('STREAM_CRYPTO_METHOD_ANY_SERVER', 126);
define ('STREAM_CRYPTO_METHOD_SSLv2_SERVER', 2);
define ('STREAM_CRYPTO_METHOD_SSLv3_SERVER', 4);
define ('STREAM_CRYPTO_METHOD_SSLv23_SERVER', 120);
define ('STREAM_CRYPTO_METHOD_TLS_SERVER', 120);
define ('STREAM_CRYPTO_METHOD_TLSv1_0_SERVER', 8);
define ('STREAM_CRYPTO_METHOD_TLSv1_1_SERVER', 16);
define ('STREAM_CRYPTO_METHOD_TLSv1_2_SERVER', 32);
define ('STREAM_CRYPTO_METHOD_TLSv1_3_SERVER', 64);
define ('STREAM_CRYPTO_PROTO_SSLv3', 4);
define ('STREAM_CRYPTO_PROTO_TLSv1_0', 8);
define ('STREAM_CRYPTO_PROTO_TLSv1_1', 16);
define ('STREAM_CRYPTO_PROTO_TLSv1_2', 32);
define ('STREAM_CRYPTO_PROTO_TLSv1_3', 64);
define ('STREAM_SHUT_RD', 0);
define ('STREAM_SHUT_WR', 1);
define ('STREAM_SHUT_RDWR', 2);
define ('STREAM_PF_INET', 2);
define ('STREAM_PF_INET6', 30);
define ('STREAM_PF_UNIX', 1);
define ('STREAM_IPPROTO_IP', 0);
define ('STREAM_IPPROTO_TCP', 6);
define ('STREAM_IPPROTO_UDP', 17);
define ('STREAM_IPPROTO_ICMP', 1);
define ('STREAM_IPPROTO_RAW', 255);
define ('STREAM_SOCK_STREAM', 1);
define ('STREAM_SOCK_DGRAM', 2);
define ('STREAM_SOCK_RAW', 3);
define ('STREAM_SOCK_SEQPACKET', 5);
define ('STREAM_SOCK_RDM', 4);
define ('STREAM_PEEK', 2);
define ('STREAM_OOB', 1);
define ('STREAM_SERVER_BIND', 4);
define ('STREAM_SERVER_LISTEN', 8);
define ('FILE_USE_INCLUDE_PATH', 1);
define ('FILE_IGNORE_NEW_LINES', 2);
define ('FILE_SKIP_EMPTY_LINES', 4);
define ('FILE_APPEND', 8);
define ('FILE_NO_DEFAULT_CONTEXT', 16);
define ('FILE_TEXT', 0);
define ('FILE_BINARY', 0);
define ('FNM_NOESCAPE', 1);
define ('FNM_PATHNAME', 2);
define ('FNM_PERIOD', 4);
define ('FNM_CASEFOLD', 16);
define ('PSFS_PASS_ON', 2);
define ('PSFS_FEED_ME', 1);
define ('PSFS_ERR_FATAL', 0);
define ('PSFS_FLAG_NORMAL', 0);
define ('PSFS_FLAG_FLUSH_INC', 1);
define ('PSFS_FLAG_FLUSH_CLOSE', 2);
define ('PASSWORD_DEFAULT', "2y");
define ('PASSWORD_BCRYPT', "2y");
define ('PASSWORD_ARGON2I', "argon2i");
define ('PASSWORD_ARGON2ID', "argon2id");
define ('PASSWORD_BCRYPT_DEFAULT_COST', 10);
define ('PASSWORD_ARGON2_DEFAULT_MEMORY_COST', 65536);
define ('PASSWORD_ARGON2_DEFAULT_TIME_COST', 4);
define ('PASSWORD_ARGON2_DEFAULT_THREADS', 1);
define ('PASSWORD_ARGON2_PROVIDER', "standard");
define ('DIRECTORY_SEPARATOR', "/");
define ('PATH_SEPARATOR', ":");
define ('SCANDIR_SORT_ASCENDING', 0);
define ('SCANDIR_SORT_DESCENDING', 1);
define ('SCANDIR_SORT_NONE', 2);
define ('GLOB_BRACE', 128);
define ('GLOB_MARK', 8);
define ('GLOB_NOSORT', 32);
define ('GLOB_NOCHECK', 16);
define ('GLOB_NOESCAPE', 8192);
define ('GLOB_ERR', 4);
define ('GLOB_ONLYDIR', 1073741824);
define ('GLOB_AVAILABLE_FLAGS', 1073750204);
define ('STREAM_USE_PATH', 1);
define ('STREAM_IGNORE_URL', 2);
define ('STREAM_REPORT_ERRORS', 8);
define ('STREAM_MUST_SEEK', 16);
define ('STREAM_URL_STAT_LINK', 1);
define ('STREAM_URL_STAT_QUIET', 2);
define ('STREAM_MKDIR_RECURSIVE', 1);
define ('STREAM_IS_URL', 1);
define ('STREAM_OPTION_BLOCKING', 1);
define ('STREAM_OPTION_READ_TIMEOUT', 4);
define ('STREAM_OPTION_READ_BUFFER', 2);
define ('STREAM_OPTION_WRITE_BUFFER', 3);
define ('STREAM_BUFFER_NONE', 0);
define ('STREAM_BUFFER_LINE', 1);
define ('STREAM_BUFFER_FULL', 2);
define ('STREAM_CAST_AS_STREAM', 0);
define ('STREAM_CAST_FOR_SELECT', 3);
define ('STREAM_META_TOUCH', 1);
define ('STREAM_META_OWNER', 3);
define ('STREAM_META_OWNER_NAME', 2);
define ('STREAM_META_GROUP', 5);
define ('STREAM_META_GROUP_NAME', 4);
define ('STREAM_META_ACCESS', 6);

// End of standard v.8.3.0
