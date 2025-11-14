<?php

// Start of wddx v.7.3.7

/**
 * Serialize a single value into a WDDX packet
 * @link http://www.php.net/manual/en/function.wddx-serialize-value.php
 * @param mixed $var The value to be serialized
 * @param string $comment [optional] An optional comment string that appears in the packet header.
 * @return string the WDDX packet, or false on error.
 */
function wddx_serialize_value ($var, string $comment = null) {}

/**
 * Serialize variables into a WDDX packet
 * @link http://www.php.net/manual/en/function.wddx-serialize-vars.php
 * @param mixed $var_name Can be either a string naming a variable or an array containing
 * strings naming the variables or another array, etc.
 * @param mixed $_ [optional] 
 * @return string the WDDX packet, or false on error.
 */
function wddx_serialize_vars ($var_name, $_ = null) {}

/**
 * Starts a new WDDX packet with structure inside it
 * @link http://www.php.net/manual/en/function.wddx-packet-start.php
 * @param string $comment [optional] An optional comment string.
 * @return resource a packet ID for use in later functions, or false on error.
 */
function wddx_packet_start (string $comment = null) {}

/**
 * Ends a WDDX packet with the specified ID
 * @link http://www.php.net/manual/en/function.wddx-packet-end.php
 * @param resource $packet_id A WDDX packet, returned by wddx_packet_start.
 * @return string the string containing the WDDX packet.
 */
function wddx_packet_end ($packet_id) {}

/**
 * Add variables to a WDDX packet with the specified ID
 * @link http://www.php.net/manual/en/function.wddx-add-vars.php
 * @param resource $packet_id A WDDX packet, returned by wddx_packet_start.
 * @param mixed $var_name Can be either a string naming a variable or an array containing
 * strings naming the variables or another array, etc.
 * @param mixed $_ [optional] 
 * @return bool true on success or false on failure
 */
function wddx_add_vars ($packet_id, $var_name, $_ = null) {}

/**
 * Unserializes a WDDX packet
 * @link http://www.php.net/manual/en/function.wddx-deserialize.php
 * @param string $packet A WDDX packet, as a string or stream.
 * @return mixed the deserialized value which can be a string, a number or an
 * array. Note that structures are deserialized into associative arrays.
 */
function wddx_deserialize (string $packet) {}

// End of wddx v.7.3.7
