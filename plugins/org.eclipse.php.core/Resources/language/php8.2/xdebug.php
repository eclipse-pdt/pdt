<?php

// Start of xdebug v.3.2.1

/**
 * {@inheritdoc}
 */
function xdebug_break (): bool {}

/**
 * {@inheritdoc}
 * @param int $depth [optional]
 */
function xdebug_call_class (int $depth = 2) {}

/**
 * {@inheritdoc}
 * @param int $depth [optional]
 */
function xdebug_call_file (int $depth = 2) {}

/**
 * {@inheritdoc}
 * @param int $depth [optional]
 */
function xdebug_call_function (int $depth = 2) {}

/**
 * {@inheritdoc}
 * @param int $depth [optional]
 */
function xdebug_call_line (int $depth = 2) {}

/**
 * {@inheritdoc}
 */
function xdebug_code_coverage_started (): bool {}

/**
 * {@inheritdoc}
 */
function xdebug_connect_to_client (): bool {}

/**
 * {@inheritdoc}
 * @param string $varname [optional]
 */
function xdebug_debug_zval (string ...$varname) {}

/**
 * {@inheritdoc}
 * @param string $varname [optional]
 */
function xdebug_debug_zval_stdout (string ...$varname) {}

/**
 * {@inheritdoc}
 */
function xdebug_dump_superglobals () {}

/**
 * {@inheritdoc}
 */
function xdebug_get_code_coverage (): array {}

/**
 * {@inheritdoc}
 * @param bool $emptyList [optional]
 */
function xdebug_get_collected_errors (bool $emptyList = false) {}

/**
 * {@inheritdoc}
 */
function xdebug_get_function_count (): int {}

/**
 * {@inheritdoc}
 */
function xdebug_get_function_stack (): array {}

/**
 * {@inheritdoc}
 */
function xdebug_get_gc_run_count (): int {}

/**
 * {@inheritdoc}
 */
function xdebug_get_gc_total_collected_roots (): int {}

/**
 * {@inheritdoc}
 */
function xdebug_get_gcstats_filename () {}

/**
 * {@inheritdoc}
 */
function xdebug_get_headers (): array {}

/**
 * {@inheritdoc}
 */
function xdebug_get_monitored_functions (): array {}

/**
 * {@inheritdoc}
 */
function xdebug_get_profiler_filename () {}

/**
 * {@inheritdoc}
 */
function xdebug_get_stack_depth (): int {}

/**
 * {@inheritdoc}
 */
function xdebug_get_tracefile_name () {}

/**
 * {@inheritdoc}
 * @param string $category [optional]
 */
function xdebug_info (string $category = NULL) {}

/**
 * {@inheritdoc}
 */
function xdebug_is_debugger_active (): bool {}

/**
 * {@inheritdoc}
 */
function xdebug_memory_usage (): int {}

/**
 * {@inheritdoc}
 * @param mixed $data
 */
function xdebug_notify (mixed $data = null): bool {}

/**
 * {@inheritdoc}
 */
function xdebug_peak_memory_usage (): int {}

/**
 * {@inheritdoc}
 * @param string $message [optional]
 * @param int $options [optional]
 */
function xdebug_print_function_stack (string $message = 'user triggered', int $options = 0) {}

/**
 * {@inheritdoc}
 * @param int $group
 * @param int $listType
 * @param array $configuration
 */
function xdebug_set_filter (int $group, int $listType, array $configuration) {}

/**
 * {@inheritdoc}
 * @param int $options [optional]
 */
function xdebug_start_code_coverage (int $options = 0) {}

/**
 * {@inheritdoc}
 */
function xdebug_start_error_collection () {}

/**
 * {@inheritdoc}
 * @param array $listOfFunctionsToMonitor
 */
function xdebug_start_function_monitor (array $listOfFunctionsToMonitor) {}

/**
 * {@inheritdoc}
 * @param string|null $gcstatsFile [optional]
 */
function xdebug_start_gcstats (?string $gcstatsFile = NULL) {}

/**
 * {@inheritdoc}
 * @param string|null $traceFile [optional]
 * @param int $options [optional]
 */
function xdebug_start_trace (?string $traceFile = NULL, int $options = 0): ?string {}

/**
 * {@inheritdoc}
 * @param bool $cleanUp [optional]
 */
function xdebug_stop_code_coverage (bool $cleanUp = true) {}

/**
 * {@inheritdoc}
 */
function xdebug_stop_error_collection () {}

/**
 * {@inheritdoc}
 */
function xdebug_stop_function_monitor () {}

/**
 * {@inheritdoc}
 */
function xdebug_stop_gcstats () {}

/**
 * {@inheritdoc}
 */
function xdebug_stop_trace () {}

/**
 * {@inheritdoc}
 */
function xdebug_time_index (): float {}

/**
 * {@inheritdoc}
 * @param mixed $variable [optional]
 */
function xdebug_var_dump (mixed ...$variable) {}

define ('XDEBUG_STACK_NO_DESC', 1);
define ('XDEBUG_CC_UNUSED', 1);
define ('XDEBUG_CC_DEAD_CODE', 2);
define ('XDEBUG_CC_BRANCH_CHECK', 4);
define ('XDEBUG_FILTER_CODE_COVERAGE', 256);
define ('XDEBUG_FILTER_STACK', 512);
define ('XDEBUG_FILTER_TRACING', 768);
define ('XDEBUG_FILTER_NONE', 0);
define ('XDEBUG_PATH_INCLUDE', 1);
define ('XDEBUG_PATH_EXCLUDE', 2);
define ('XDEBUG_NAMESPACE_INCLUDE', 17);
define ('XDEBUG_NAMESPACE_EXCLUDE', 18);
define ('XDEBUG_TRACE_APPEND', 1);
define ('XDEBUG_TRACE_COMPUTERIZED', 2);
define ('XDEBUG_TRACE_HTML', 4);
define ('XDEBUG_TRACE_NAKED_FILENAME', 8);

// End of xdebug v.3.2.1
