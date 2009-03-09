<?php

// Start of Zend Monitor v.4.0

function monitor_pass_error () {}

function zend_monitor_pass_error () {}

function monitor_custom_event () {}

function zend_monitor_custom_event () {}

function monitor_set_aggregation_hint () {}

function zend_monitor_set_aggregation_hint () {}

function monitor_event_reporting () {}

function zend_monitor_event_reporting () {}

define ('ZEND_MONITOR_EVENT_CUSTOM', 1);
define ('ZEND_MONITOR_EVENT_SLOWFUNC', 2);
define ('ZEND_MONITOR_EVENT_FUNCERROR', 4);
define ('ZEND_MONITOR_EVENT_SLOWSCRIPT', 8);
define ('ZEND_MONITOR_EVENT_SLOWSCRIPT_REL', 16);
define ('ZEND_MONITOR_EVENT_MEMUSAGE', 32);
define ('ZEND_MONITOR_EVENT_MEMUSAGE_REL', 64);
define ('ZEND_MONITOR_EVENT_OUTPUTSIZE', 128);
define ('ZEND_MONITOR_EVENT_PHPERROR', 256);
define ('ZEND_MONITOR_EVENT_JAVAEX', 512);
define ('ZEND_MONITOR_EVENT_ALL', 1023);
define ('ZEND_MONITOR_EVENT_NONE', 1);

// End of Zend Monitor v.4.0
?>
