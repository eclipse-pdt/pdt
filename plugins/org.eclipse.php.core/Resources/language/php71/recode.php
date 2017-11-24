<?php

// Start of recode v.7.1.1

/**
 * Recode a string according to a recode request
 * @link http://www.php.net/manual/en/function.recode-string.php
 * @param string $request The desired recode request type
 * @param string $string The string to be recoded
 * @return string the recoded string or false, if unable to 
 * perform the recode request.
 */
function recode_string (string $request, string $string) {}

/**
 * Recode from file to file according to recode request
 * @link http://www.php.net/manual/en/function.recode-file.php
 * @param string $request The desired recode request type
 * @param resource $input A local file handle resource for 
 * the input
 * @param resource $output A local file handle resource for 
 * the output
 * @return bool false, if unable to comply, true otherwise.
 */
function recode_file (string $request, $input, $output) {}

/**
 * Alias: recode_string
 * @link http://www.php.net/manual/en/function.recode.php
 * @param $request
 * @param $str
 */
function recode ($request, $str) {}

// End of recode v.7.1.1
