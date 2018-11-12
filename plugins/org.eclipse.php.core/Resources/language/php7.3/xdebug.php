<?php

// Start of xdebug v.2.5.5

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
 * @param $var
 */
function xdebug_var_dump ($var) {}

/**
 * @param $var
 */
function xdebug_debug_zval ($var) {}

/**
 * @param $var
 */
function xdebug_debug_zval_stdout ($var) {}

function xdebug_enable () {}

function xdebug_disable () {}

function xdebug_is_enabled () {}

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
 * @param mixed $prefix [optional]
 */
function xdebug_dump_aggr_profiling_data ($prefix = null) {}

function xdebug_clear_aggr_profiling_data () {}

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
 * @param $functions_to_monitor
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

define ('XDEBUG_TRACE_APPEND', 1);
define ('XDEBUG_TRACE_COMPUTERIZED', 2);
define ('XDEBUG_TRACE_HTML', 4);
define ('XDEBUG_TRACE_NAKED_FILENAME', 8);
define ('XDEBUG_CC_UNUSED', 1);
define ('XDEBUG_CC_DEAD_CODE', 2);
define ('XDEBUG_CC_BRANCH_CHECK', 4);
define ('XDEBUG_STACK_NO_DESC', 1);

// End of xdebug v.2.5.5
