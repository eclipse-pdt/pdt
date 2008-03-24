<?php

// Start of wddx v.

/**
 * Serialize a single value into a WDDX packet
 * @link http://php.net/manual/en/function.wddx-serialize-value.php
 * @param var mixed
 * @param comment string[optional]
 * @return string the WDDX packet, or false on error.
 */
function wddx_serialize_value ($var, $comment = null) {}

/**
 * Serialize variables into a WDDX packet
 * @link http://php.net/manual/en/function.wddx-serialize-vars.php
 * @param var_name mixed
 * @param _ mixed[optional]
 * @return string the WDDX packet, or false on error.
 */
function wddx_serialize_vars ($var_name, $_ = null) {}

/**
 * Starts a new WDDX packet with structure inside it
 * @link http://php.net/manual/en/function.wddx-packet-start.php
 * @param comment string[optional]
 * @return resource a packet ID for use in later functions, or false on error.
 */
function wddx_packet_start ($comment = null) {}

/**
 * Ends a WDDX packet with the specified ID
 * @link http://php.net/manual/en/function.wddx-packet-end.php
 * @param packet_id resource
 * @return string the string containing the WDDX packet.
 */
function wddx_packet_end ($packet_id) {}

/**
 * Add variables to a WDDX packet with the specified ID
 * @link http://php.net/manual/en/function.wddx-add-vars.php
 * @param packet_id resource
 * @param var_name mixed
 * @param _ mixed[optional]
 * @return bool 
 */
function wddx_add_vars ($packet_id, $var_name, $_ = null) {}

/**
 * &Alias; <function>wddx_unserialize</function>
 * @link http://php.net/manual/en/function.wddx-deserialize.php
 */
function wddx_deserialize () {}

// End of wddx v.
?>
