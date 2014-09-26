<?php

// Start of wddx v.

/**
 * Serialize a single value into a WDDX packet
 * @link http://www.php.net/manual/en/function.wddx-serialize-value.php
 * @param var mixed <p>
 * The value to be serialized
 * </p>
 * @param comment string[optional] <p>
 * An optional comment string that appears in the packet header.
 * </p>
 * @return string the WDDX packet, or false on error.
 */
function wddx_serialize_value ($var, $comment = null) {}

/**
 * Serialize variables into a WDDX packet
 * @link http://www.php.net/manual/en/function.wddx-serialize-vars.php
 * @param var_name mixed <p>
 * Can be either a string naming a variable or an array containing
 * strings naming the variables or another array, etc.
 * </p>
 * @param _ mixed[optional] 
 * @return string the WDDX packet, or false on error.
 */
function wddx_serialize_vars ($var_name, $_ = null) {}

/**
 * Starts a new WDDX packet with structure inside it
 * @link http://www.php.net/manual/en/function.wddx-packet-start.php
 * @param comment string[optional] <p>
 * An optional comment string.
 * </p>
 * @return resource a packet ID for use in later functions, or false on error.
 */
function wddx_packet_start ($comment = null) {}

/**
 * Ends a WDDX packet with the specified ID
 * @link http://www.php.net/manual/en/function.wddx-packet-end.php
 * @param packet_id resource <p>
 * A WDDX packet, returned by wddx_packet_start.
 * </p>
 * @return string the string containing the WDDX packet.
 */
function wddx_packet_end ($packet_id) {}

/**
 * Add variables to a WDDX packet with the specified ID
 * @link http://www.php.net/manual/en/function.wddx-add-vars.php
 * @param packet_id resource <p>
 * A WDDX packet, returned by wddx_packet_start.
 * </p>
 * @param var_name mixed <p>
 * Can be either a string naming a variable or an array containing
 * strings naming the variables or another array, etc.
 * </p>
 * @param _ mixed[optional] 
 * @return bool true on success or false on failure
 */
function wddx_add_vars ($packet_id, $var_name, $_ = null) {}

/**
 * Unserializes a WDDX packet
 * @link http://www.php.net/manual/en/function.wddx-deserialize.php
 * @param packet string <p>
 * A WDDX packet, as a string or stream.
 * </p>
 * @return mixed the deserialized value which can be a string, a number or an
 * array. Note that structures are deserialized into associative arrays.
 */
function wddx_deserialize ($packet) {}

// End of wddx v.
