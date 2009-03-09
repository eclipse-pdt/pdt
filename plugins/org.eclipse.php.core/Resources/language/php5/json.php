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
 * @return string a JSON encoded string on success.
 */
function json_encode ($value) {}

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
 * @return mixed an object or if the optional
 * assoc parameter is true, an associative 
 * array is instead returned.
 */
function json_decode ($json, $assoc = null) {}

// End of json v.1.2.1
?>
