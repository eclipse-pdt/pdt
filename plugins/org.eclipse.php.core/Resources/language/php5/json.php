<?php

// Start of json v.1.2.1

/**
 * Returns the JSON representation of a value
 * @link http://php.net/manual/en/function.json-encode.php
 * @param value mixed <p>
 * The value being encoded. Can be any type except
 * a resource.
 * </p>
 * <p>
 * This function only works with UTF-8 encoded data.
 * </p>
 * @param options int[optional] <p>
 * Bitmask consisting of JSON_HEX_QUOT,
 * JSON_HEX_TAG,
 * JSON_HEX_AMP,
 * JSON_HEX_APOS,
 * JSON_FORCE_OBJECT. Defaults to 0.
 * </p>
 * @return string a JSON encoded string on success.
 */
function json_encode ($value, $options = null) {}

/**
 * Decodes a JSON string
 * @link http://php.net/manual/en/function.json-decode.php
 * @param json string <p>
 * The json string being decoded.
 * </p>
 * @param assoc bool[optional] <p>
 * When true, returned objects will be converted into
 * associative arrays.
 * </p>
 * @param depth int[optional] <p>
 * User specified recursion depth.
 * </p>
 * @return mixed an object or if the optional
 * assoc parameter is true, an associative 
 * array is instead returned. &null; is returned if the
 * json cannot be decoded or if the encoded
 * data is deeper than the recursion limit.
 */
function json_decode ($json, $assoc = null, $depth = null) {}

// End of json v.1.2.1
?>
