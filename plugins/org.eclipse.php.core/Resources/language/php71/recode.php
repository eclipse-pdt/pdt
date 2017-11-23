<?php

// Start of recode v.7.1.1

/**
 * Recode a string according to a recode request
 * @link http://www.php.net/manual/en/function.recode-string.php
 * @param string $request <p>
 * The desired recode request type
 * </p>
 * @param string $string <p>
 * The string to be recoded
 * </p>
 * @return string the recoded string or false, if unable to 
 * perform the recode request.
 */
function recode_string (string $request, string $string) {}

/**
 * Recode from file to file according to recode request
 * @link http://www.php.net/manual/en/function.recode-file.php
 * @param string $request <p>
 * The desired recode request type
 * </p>
 * @param resource $input <p>
 * A local file handle resource for 
 * the input
 * </p>
 * @param resource $output <p>
 * A local file handle resource for 
 * the output
 * </p>
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
