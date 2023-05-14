<?php

// Start of xdebug v.2.9.1

function xdebug_get_stack_depth () {}

function xdebug_get_function_stack () {}

function xdebug_get_formatted_function_stack () {}

/**
 * @param mixed $message [optional]
 * @param mixed $options [optional]
 */
function xdebug_print_function_stack ($message = null, $options = null) {}

function xdebug_get_declared_vars () {}

/**
 * @param mixed $depth [optional]
 */
function xdebug_call_class ($depth = null) {}

/**
 * @param mixed $depth [optional]
 */
function xdebug_call_function ($depth = null) {}

/**
 * @param mixed $depth [optional]
 */
function xdebug_call_file ($depth = null) {}

/**
 * @param mixed $depth [optional]
 */
function xdebug_call_line ($depth = null) {}

/**
 * @param mixed $var
 */
function xdebug_var_dump ($var) {}

/**
 * @param mixed $var
 */
function xdebug_debug_zval ($var) {}

/**
 * @param mixed $var
 */
function xdebug_debug_zval_stdout ($var) {}

function xdebug_enable () {}

function xdebug_disable () {}

function xdebug_is_enabled () {}

function xdebug_is_debugger_active () {}

function xdebug_break () {}

/**
 * @param mixed $fname [optional]
 * @param mixed $options [optional]
 */
function xdebug_start_trace ($fname = null, $options = null) {}

function xdebug_stop_trace () {}

function xdebug_get_tracefile_name () {}

function xdebug_get_profiler_filename () {}

/**
 * @param mixed $fname [optional]
 */
function xdebug_start_gcstats ($fname = null) {}

function xdebug_stop_gcstats () {}

function xdebug_get_gcstats_filename () {}

function xdebug_get_gc_run_count () {}

function xdebug_get_gc_total_collected_roots () {}

function xdebug_memory_usage () {}

function xdebug_peak_memory_usage () {}

function xdebug_time_index () {}

function xdebug_start_error_collection () {}

function xdebug_stop_error_collection () {}

/**
 * @param mixed $clear [optional]
 */
function xdebug_get_collected_errors ($clear = null) {}

/**
 * @param mixed $functions_to_monitor
 */
function xdebug_start_function_monitor ($functions_to_monitor) {}

function xdebug_stop_function_monitor () {}

/**
 * @param mixed $clear [optional]
 */
function xdebug_get_monitored_functions ($clear = null) {}

/**
 * @param mixed $options [optional]
 */
function xdebug_start_code_coverage ($options = null) {}

/**
 * @param mixed $cleanup [optional]
 */
function xdebug_stop_code_coverage ($cleanup = null) {}

function xdebug_get_code_coverage () {}

function xdebug_code_coverage_started () {}

function xdebug_get_function_count () {}

function xdebug_dump_superglobals () {}

function xdebug_get_headers () {}

/**
 * @param mixed $filter_group
 * @param mixed $filter_type
 * @param mixed $array_of_filters
 */
function xdebug_set_filter ($filter_group, $filter_type, $array_of_filters) {}

define ('XDEBUG_STACK_NO_DESC', 1);
define ('XDEBUG_CC_UNUSED', 1);
define ('XDEBUG_CC_DEAD_CODE', 2);
define ('XDEBUG_CC_BRANCH_CHECK', 4);
define ('XDEBUG_TRACE_APPEND', 1);
define ('XDEBUG_TRACE_COMPUTERIZED', 2);
define ('XDEBUG_TRACE_HTML', 4);
define ('XDEBUG_TRACE_NAKED_FILENAME', 8);
define ('XDEBUG_FILTER_TRACING', 256);
define ('XDEBUG_FILTER_CODE_COVERAGE', 512);
define ('XDEBUG_FILTER_NONE', 0);
define ('XDEBUG_PATH_WHITELIST', 1);
define ('XDEBUG_PATH_BLACKLIST', 2);
define ('XDEBUG_NAMESPACE_WHITELIST', 17);
define ('XDEBUG_NAMESPACE_BLACKLIST', 18);

// End of xdebug v.2.9.1
