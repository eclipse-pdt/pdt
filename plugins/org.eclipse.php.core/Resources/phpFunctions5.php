<?php

/**
 * Return the absolute value of the number 
 *
 * @return number
 * @param  number int
 */
function abs($number) {}

/**
 * Obtain the contents of the black-list 
 *
 * @return array
 */
function accelerator_get_blacklist() {}

/**
 * Obtain the list of black-listed paths 
 *
 * @return array
 */
function accelerator_get_blacklisted_paths() {}

/**
 * Obtain configuration information for the Zend Performance Suite 
 *
 * @return array
 */
function accelerator_get_configuration() {}

/**
 * Get the scripts which are accelerated by the Zend Performance Suite 
 *
 * @return array
 */
function accelerator_get_scripts() {}

/**
 * Obtain the server's start time as recorded by the Zend Performance Suite 
 *
 * @return long
 */
function accelerator_get_server_start_time() {}

/**
 * Obtain statistics information regarding code acceleration in the Zend Performance Suite 
 *
 * @return array
 */
function accelerator_get_statistics() {}

/**
 * Obtain the list of URIs that were detected by the Zend Performance Suite 
 *
 * @return array
 */
function accelerator_get_uris() {}

/**
 * Obtain version information for the Zend Performance Suite 
 *
 * @return array
 */
function accelerator_get_version_info() {}

/**
 * Check accelerator license information 
 *
 * @return array
 */
function accelerator_license_info() {}

/**
 * Request that the contents of the Accelerator module in the ZPS be reset 
 *
 * @return void
 */
function accelerator_reset() {}

/**
 * Enable/disable code acceleration in the Zend Performance Suite 
 *
 * @return boolean
 * @param  status boolean
 */
function accelerator_set_status($status) {}

/**
 * Unlock the Zend Performance Suite API functions for usage 
 *
 * @return boolean
 * @param  password string
 */
function accelerator_unlock_functions($password) {}

/**
 * Return the arc cosine of the number in radians 
 *
 * @return float
 * @param  number float
 */
function acos($number) {}

/**
 * Returns the inverse hyperbolic cosine of the number, i.e. the value whose hyperbolic cosine is number 
 *
 * @return float
 * @param  number float
 */
function acosh($number) {}

/**
 * Escapes all chars mentioned in charlist with backslash. It creates octal representations if asked to backslash characters with 8th bit set or with ASCII<32 (except '\n', '\r', '\t' etc...) 
 *
 * @return string
 * @param  str string
 * @param  charlist string
 */
function addcslashes($str, $charlist) {}

/**
 * Escapes single quote, double quotes and backslash characters in a string with backslashes 
 *
 * @return string
 * @param  str string
 */
function addslashes($str) {}

/**
 * Terminate apache process after this request 
 *
 * @return bool
 */
function apache_child_terminate() {}

/**
 * Get a list of loaded Apache modules 
 *
 * @return array
 */
function apache_get_modules() {}

/**
 * Fetch Apache version 
 *
 * @return string
 */
function apache_get_version() {}

/**
 * Get an Apache subprocess_env variable 
 *
 * @return string
 * @param  variable string
 * @param  walk_to_top bool[optional]
 */
function apache_getenv($variable, $walk_to_top = null) {}

/**
 * Perform a partial request of the given URI to obtain information about it 
 *
 * @return object
 * @param  URI string
 */
function apache_lookup_uri($URI) {}

/**
 * Get and set Apache request notes 
 *
 * @return string
 * @param  note_name string
 * @param  note_value string[optional]
 */
function apache_note($note_name, $note_value = null) {}






/**
 * Get all headers from the request 
 *
 * @return array
 */
function apache_request_headers() {}

















/**
 * Reset the Apache write timer 
 *
 * @return bool
 */
function apache_reset_timeout() {}

/**
 * Get all headers from the response 
 *
 * @return array
 */
function apache_response_headers() {}

/**
 * Set an Apache subprocess_env variable 
 *
 * @return bool
 * @param  variable string
 * @param  value string
 * @param  walk_to_top bool[optional]
 */
function apache_setenv($variable, $value, $walk_to_top = null) {}

/**
 * Retuns an array with all string keys lowercased [or uppercased] 
 *
 * @return array
 * @param  input array
 * @param  case int[optional]
 */
function array_change_key_case($input, $case = CASE_LOWER) {}

/**
 * Split array into chunks 
 *
 * @return array
 * @param  input array
 * @param  size int
 * @param  preserve_keys bool[optional]
 */
function array_chunk($input, $size, $preserve_keys = null) {}

/**
 * Creates an array by using the elements of the first parameter as keys and the elements of the second as correspoding keys 
 *
 * @return array
 * @param  keys array
 * @param  values array
 */
function array_combine($keys, $values) {}

/**
 * Return the value as key and the frequency of that value in input as value 
 *
 * @return array
 * @param  input array
 */
function array_count_values($input) {}

/**
 * Returns the entries of arr1 that have values which are not present in any of the others arguments. 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 */
function array_diff($arr1, $arr2) {}

/**
 * Returns the entries of arr1 that have values which are not present in any of the others arguments but do additional checks whether the keys are equal 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 */
function array_diff_assoc($arr1, $arr2) {}

/**
 * Returns the entries of arr1 that have keys which are not present in any of the others arguments. This function is like array_diff() but works on the keys instead of the values. The associativity is preserved. 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 */
function array_diff_key($arr1, $arr2) {}

/**
 * Returns the entries of arr1 that have values which are not present in any of the others arguments but do additional checks whether the keys are equal. Elements are compared by user supplied function. 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 * @param  data_comp_func callback
 */
function array_diff_uassoc($arr1, $arr2, $data_comp_func) {}

/**
 * Returns the entries of arr1 that have keys which are not present in any of the others arguments. User supplied function is used for comparing the keys. This function is like array_udiff() but works on the keys instead of the values. The associativity is preserved. 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 * @param  key_comp_func callback
 */
function array_diff_ukey($arr1, $arr2, $key_comp_func) {}

/**
 * Create an array containing num elements starting with index start_key each initialized to val 
 *
 * @return array
 * @param  start_key int
 * @param  num int
 * @param  val mixed
 */
function array_fill($start_key, $num, $val) {}

/**
 * Filters elements from the array via the callback. 
 *
 * @return array
 * @param  input array
 * @param  callback mixed[optional]
 */
function array_filter($input, $callback = null) {}

/**
 * Return array with key <-> value flipped 
 *
 * @return array
 * @param  input array
 */
function array_flip($input) {}

/**
 * Returns the entries of arr1 that have values which are present in all the other arguments 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 */
function array_intersect($arr1, $arr2) {}

/**
 * Returns the entries of arr1 that have values which are present in all the other arguments. Keys are used to do more restrictive check 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 */
function array_intersect_assoc($arr1, $arr2) {}

/**
 * Returns the entries of arr1 that have keys which are present in all the other arguments. Kind of equivalent to array_diff(array_keys($arr1), array_keys($arr2)[,array_keys(...)]). Equivalent of array_intersect_assoc() but does not do compare of the data. 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 */
function array_intersect_key($arr1, $arr2) {}

/**
 * Returns the entries of arr1 that have values which are present in all the other arguments. Keys are used to do more restrictive check and they are compared by using an user-supplied callback. 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 * @param  key_compare_func callback
 */
function array_intersect_uassoc($arr1, $arr2, $key_compare_func) {}

/**
 * Returns the entries of arr1 that have keys which are present in all the other arguments. Kind of equivalent to array_diff(array_keys($arr1), array_keys($arr2)[,array_keys(...)]). The comparison of the keys is performed by a user supplied function. Equivalent of array_intersect_uassoc() but does not do compare of the data. 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 * @param  key_compare_func callback
 */
function array_intersect_ukey($arr1, $arr2, $key_compare_func) {}

/**
 * Checks if the given key or index exists in the array 
 *
 * @return bool
 * @param  key mixed
 * @param  search array
 */
function array_key_exists($key, $search) {}

/**
 * Return just the keys from the input array, optionally only for the specified search_value 
 *
 * @return array
 * @param  input array
 * @param  search_value mixed[optional]
 * @param  strict bool
 */
function array_keys($input, $search_value = null, $strict) {}

/**
 * Applies the callback to the elements in given arrays. 
 *
 * @return array
 * @param  callback mixed
 * @param  input1 array
 * @param  input2 array[optional]
 * @vararg ...
 */
function array_map($callback, $input1, $input2 = null) {}

/**
 * Merges elements from passed arrays into one array 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 */
function array_merge($arr1, $arr2) {}

/**
 * Recursively merges elements from passed arrays into one array 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 */
function array_merge_recursive($arr1, $arr2) {}

/**
 * Sort multiple arrays at once similar to how ORDER BY clause works in SQL 
 *
 * @return bool
 * @param  ar1 array
 * @param  SORT_ASC|SORT_DESC unknown[optional]
 * @param  SORT_REGULAR|SORT_NUMERIC|SORT_STRING unknown[optional]
 * @param  ar2 array[optional]
 * @param  SORT_ASC|SORT_DESC unknown[optional]
 * @param  SORT_REGULAR|SORT_NUMERIC|SORT_STRING unknown[optional]
 * @vararg ...
 */
function array_multisort($ar1, $SORT_ASC = null, $SORT_REGULAR = null, $ar2 = null, $SORT_ASC = null, $SORT_REGULAR = null) {}

/**
 * Returns a copy of input array padded with pad_value to size pad_size 
 *
 * @return array
 * @param  input array
 * @param  pad_size int
 * @param  pad_value mixed
 */
function array_pad($input, $pad_size, $pad_value) {}

/**
 * Pops an element off the end of the array 
 *
 * @return mixed
 * @param  stack array
 */
function array_pop($stack) {}

/**
 * Returns the product of the array entries 
 *
 * @return number
 * @param  input array
 */
function array_product($input) {}

/**
 * Pushes elements onto the end of the array 
 *
 * @return int
 * @param  stack array
 * @param  var mixed
 * @vararg ... mixed
 */
function array_push($stack, $var) {}

/**
 * Return key/keys for random entry/entries in the array 
 *
 * @return mixed
 * @param  input array
 * @param  num_req int[optional]
 */
function array_rand($input, $num_req = null) {}

/**
 * Iteratively reduce the array to a single value via the callback. 
 *
 * @return mixed
 * @param  input array
 * @param  callback mixed
 * @param  initial int[optional]
 */
function array_reduce($input, $callback, $initial = null) {}

/**
 * Return input as a new array with the order of the entries reversed 
 *
 * @return array
 * @param  input array
 * @param  preserve_keys bool[optional]
 */
function array_reverse($input, $preserve_keys = null) {}

/**
 * Searches the array for a given value and returns the corresponding key if successful 
 *
 * @return mixed
 * @param  needle mixed
 * @param  haystack array
 * @param  strict bool[optional]
 */
function array_search($needle, $haystack, $strict = null) {}

/**
 * Pops an element off the beginning of the array 
 *
 * @return mixed
 * @param  stack array
 */
function array_shift($stack) {}

/**
 * Returns elements specified by offset and length 
 *
 * @return array
 * @param  input array
 * @param  offset int
 * @param  length int[optional]
 */
function array_slice($input, $offset, $length = null) {}

/**
 * Removes the elements designated by offset and length and replace them with supplied array 
 *
 * @return array
 * @param  input array
 * @param  offset int
 * @param  length int[optional]
 * @param  replacement array[optional]
 */
function array_splice($input, $offset, $length = null, $replacement = null) {}

/**
 * Returns the sum of the array entries 
 *
 * @return number
 * @param  input array
 */
function array_sum($input) {}

/**
 * Returns the entries of arr1 that have values which are not present in any of the others arguments. Elements are compared by user supplied function. 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 * @param  data_comp_func callback
 */
function array_udiff($arr1, $arr2, $data_comp_func) {}

/**
 * Returns the entries of arr1 that have values which are not present in any of the others arguments but do additional checks whether the keys are equal. Keys are compared by user supplied function. 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 * @param  key_comp_func callback
 */
function array_udiff_assoc($arr1, $arr2, $key_comp_func) {}

/**
 * Returns the entries of arr1 that have values which are not present in any of the others arguments but do additional checks whether the keys are equal. Keys and elements are compared by user supplied functions. 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 * @param  data_comp_func callback
 * @param  key_comp_func callback
 */
function array_udiff_uassoc($arr1, $arr2, $data_comp_func, $key_comp_func) {}

/**
 * Returns the entries of arr1 that have values which are present in all the other arguments. Data is compared by using an user-supplied callback. 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 * @param  data_compare_func callback
 */
function array_uintersect($arr1, $arr2, $data_compare_func) {}

/**
 * Returns the entries of arr1 that have values which are present in all the other arguments. Keys are used to do more restrictive check. Data is compared by using an user-supplied callback. 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 * @param  data_compare_func callback
 */
function array_uintersect_assoc($arr1, $arr2, $data_compare_func) {}

/**
 * Returns the entries of arr1 that have values which are present in all the other arguments. Keys are used to do more restrictive check. Both data and keys are compared by using user-supplied callbacks. 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 * @param  data_compare_func callback
 * @param  key_compare_func callback
 */
function array_uintersect_uassoc($arr1, $arr2, $data_compare_func, $key_compare_func) {}

/**
 * Removes duplicate values from array 
 *
 * @return array
 * @param  input array
 */
function array_unique($input) {}

/**
 * Pushes elements onto the beginning of the array 
 *
 * @return int
 * @param  stack array
 * @param  var mixed
 * @vararg ... mixed
 */
function array_unshift($stack, $var) {}

/**
 * Return just the values from the input array 
 *
 * @return array
 * @param  input array
 */
function array_values($input) {}

/**
 * Apply a user function to every member of an array 
 *
 * @return bool
 * @param  input array
 * @param  funcname string
 * @param  userdata mixed[optional]
 */
function array_walk($input, $funcname, $userdata = null) {}

/**
 * Apply a user function recursively to every member of an array 
 *
 * @return bool
 * @param  input array
 * @param  funcname string
 * @param  userdata mixed[optional]
 */
function array_walk_recursive($input, $funcname, $userdata = null) {}

/**
 * Sort an array in reverse order and maintain index association 
 *
 * @return bool
 * @param  array_arg array
 * @param  sort_flags int[optional]
 */
function arsort($array_arg, $sort_flags = null) {}

/**
 * Returns the arc sine of the number in radians 
 *
 * @return float
 * @param  number float
 */
function asin($number) {}

/**
 * Returns the inverse hyperbolic sine of the number, i.e. the value whose hyperbolic sine is number 
 *
 * @return float
 * @param  number float
 */
function asinh($number) {}

/**
 * Sort an array and maintain index association 
 *
 * @return bool
 * @param  array_arg array
 * @param  sort_flags int[optional]
 */
function asort($array_arg, $sort_flags = null) {}

/**
 * Checks if assertion is false 
 *
 * @return bool
 * @param  assertion string|bool
 */
function assert($assertion) {}

/**
 * Set/get the various assert flags 
 *
 * @return mixed
 * @param  what int
 * @param  value mixed[optional]
 */
function assert_options($what, $value = null) {}

/**
 * Returns the arc tangent of the number in radians 
 *
 * @return float
 * @param  number float
 */
function atan($number) {}

/**
 * Returns the arc tangent of y/x, with the resulting quadrant determined by the signs of y and x 
 *
 * @return float
 * @param  y float
 * @param  x float
 */
function atan2($y, $x) {}

/**
 * Returns the inverse hyperbolic tangent of the number, i.e. the value whose hyperbolic tangent is number 
 *
 * @return float
 * @param  number float
 */
function atanh($number) {}

/**
 * Decodes string using MIME base64 algorithm 
 *
 * @return string
 * @param  str string
 */
function base64_decode($str) {}

/**
 * Encodes string using MIME base64 algorithm 
 *
 * @return string
 * @param  str string
 */
function base64_encode($str) {}

/**
 * Converts a number in a string from any base <= 36 to any base <= 36 
 *
 * @return string
 * @param  number string
 * @param  frombase int
 * @param  tobase int
 */
function base_convert($number, $frombase, $tobase) {}

/**
 * Returns the filename component of the path 
 *
 * @return string
 * @param  path string
 * @param  suffix string[optional]
 */
function basename($path, $suffix = null) {}

/**
 * Returns the sum of two arbitrary precision numbers 
 *
 * @return string
 * @param  left_operand string
 * @param  right_operand string
 * @param  scale int[optional]
 */
function bcadd($left_operand, $right_operand, $scale = null) {}

/**
 * Compares two arbitrary precision numbers 
 *
 * @return int
 * @param  left_operand string
 * @param  right_operand string
 * @param  scale int[optional]
 */
function bccomp($left_operand, $right_operand, $scale = null) {}

/**
 * Returns the quotient of two arbitrary precision numbers (division) 
 *
 * @return string
 * @param  left_operand string
 * @param  right_operand string
 * @param  scale int[optional]
 */
function bcdiv($left_operand, $right_operand, $scale = null) {}

/**
 * Returns the modulus of the two arbitrary precision operands 
 *
 * @return string
 * @param  left_operand string
 * @param  right_operand string
 */
function bcmod($left_operand, $right_operand) {}

/**
 * Returns the multiplication of two arbitrary precision numbers 
 *
 * @return string
 * @param  left_operand string
 * @param  right_operand string
 * @param  scale int[optional]
 */
function bcmul($left_operand, $right_operand, $scale = null) {}

/**
 * Returns the value of an arbitrary precision number raised to the power of another 
 *
 * @return string
 * @param  x string
 * @param  y string
 * @param  scale int[optional]
 */
function bcpow($x, $y, $scale = null) {}

/**
 * Returns the value of an arbitrary precision number raised to the power of another reduced by a modulous 
 *
 * @return string
 * @param  x string
 * @param  y string
 * @param  mod string
 * @param  scale int[optional]
 */
function bcpowmod($x, $y, $mod, $scale = null) {}

/**
 * Sets default scale parameter for all bc math functions 
 *
 * @return bool
 * @param  scale int
 */
function bcscale($scale) {}

/**
 * Returns the square root of an arbitray precision number 
 *
 * @return string
 * @param  operand string
 * @param  scale int[optional]
 */
function bcsqrt($operand, $scale = null) {}

/**
 * Returns the difference between two arbitrary precision numbers 
 *
 * @return string
 * @param  left_operand string
 * @param  right_operand string
 * @param  scale int[optional]
 */
function bcsub($left_operand, $right_operand, $scale = null) {}

/**
 * Converts the binary representation of data to hex 
 *
 * @return string
 * @param  data string
 */
function bin2hex($data) {}

/**
 * Specify the character encoding in which the messages from the DOMAIN message catalog will be returned. 
 *
 * @return string
 * @param  domain string
 * @param  codeset string
 */
function bind_textdomain_codeset($domain, $codeset) {}

/**
 * Returns the decimal equivalent of the binary number 
 *
 * @return number
 * @param  binary_number string
 */
function bindec($binary_number) {}

/**
 * Bind to the text domain domain_name, looking for translations in dir. Returns the current domain 
 *
 * @return string
 * @param  domain_name string
 * @param  dir string
 */
function bindtextdomain($domain_name, $dir) {}

/**
 * 
 *
 * @return bool
 * @param  index int
 */
function birdstep_autocommit($index) {}

/**
 * 
 *
 * @return bool
 * @param  id int
 */
function birdstep_close($id) {}

/**
 * 
 *
 * @return bool
 * @param  index int
 */
function birdstep_commit($index) {}

/**
 * 
 *
 * @return int
 * @param  server string
 * @param  user string
 * @param  pass string
 */
function birdstep_connect($server, $user, $pass) {}

/**
 * 
 *
 * @return int
 * @param  index int
 * @param  exec_str string
 */
function birdstep_exec($index, $exec_str) {}

/**
 * 
 *
 * @return bool
 * @param  index int
 */
function birdstep_fetch($index) {}

/**
 * 
 *
 * @return string
 * @param  index int
 * @param  col int
 */
function birdstep_fieldname($index, $col) {}

/**
 * 
 *
 * @return int
 * @param  index int
 */
function birdstep_fieldnum($index) {}

/**
 * 
 *
 * @return bool
 * @param  index int
 */
function birdstep_freeresult($index) {}

/**
 * 
 *
 * @return bool
 * @param  index int
 */
function birdstep_off_autocommit($index) {}

/**
 * 
 *
 * @return mixed
 * @param  index int
 * @param  col int
 */
function birdstep_result($index, $col) {}

/**
 * 
 *
 * @return bool
 * @param  index int
 */
function birdstep_rollback($index) {}

/**
 * Close an open file pointer 
 *
 * @return bool
 * @param  fp resource
 */
function bzclose($fp) {}

/**
 * Compresses a string into BZip2 encoded data 
 *
 * @return mixed
 * @param  source string
 * @param  blocksize100k int[optional]
 * @param  workfactor int[optional]
 */
function bzcompress($source, $blocksize100k = null, $workfactor = null) {}

/**
 * Decompresses BZip2 compressed data 
 *
 * @return mixed
 * @param  source string
 * @param  small int[optional]
 */
function bzdecompress($source, $small = null) {}

/**
 * Returns the error number 
 *
 * @return int
 * @param  bz resource
 */
function bzerrno($bz) {}

/**
 * Returns the error number and error string in an associative array 
 *
 * @return array
 * @param  bz resource
 */
function bzerror($bz) {}

/**
 * Returns the error string 
 *
 * @return string
 * @param  bz resource
 */
function bzerrstr($bz) {}

/**
 * Flushes output 
 *
 * @return bool
 * @param  fp resource
 */
function bzflush($fp) {}

/**
 * Opens a new BZip2 stream 
 *
 * @return resource
 * @param  file|fp string|int
 * @param  mode string
 */
function bzopen($file, $mode) {}

/**
 * Reads up to length bytes from a BZip2 stream, or 1024 bytes if length is not specified 
 *
 * @return string
 * @param  bz int
 * @param  length int
 */
function bzread($bz, $length) {}

/**
 * Binary-safe file write 
 *
 * @return int
 * @param  fp resource
 * @param  str string
 * @param  length int[optional]
 */
function bzwrite($fp, $str, $length = null) {}

/**
 * Returns the number of days in a month for a given year and calendar 
 *
 * @return int
 * @param  calendar int
 * @param  month int
 * @param  year int
 */
function cal_days_in_month($calendar, $month, $year) {}

/**
 * Converts from Julian Day Count to a supported calendar and return extended information 
 *
 * @return array
 * @param  jd int
 * @param  calendar int
 */
function cal_from_jd($jd, $calendar) {}

/**
 * Returns information about a particular calendar 
 *
 * @return array
 * @param  calendar int
 */
function cal_info($calendar) {}

/**
 * Converts from a supported calendar to Julian Day Count 
 *
 * @return int
 * @param  calendar int
 * @param  month int
 * @param  day int
 * @param  year int
 */
function cal_to_jd($calendar, $month, $day, $year) {}

/**
 * Call a user function which is the first parameter 
 *
 * @return mixed
 * @param  function_name string
 * @param  parmeter mixed[optional]
 * @vararg ... mixed
 */
function call_user_func($function_name, $parmeter = null) {}

/**
 * Call a user function which is the first parameter with the arguments contained in array 
 *
 * @return mixed
 * @param  function_name string
 * @param  parameters array
 */
function call_user_func_array($function_name, $parameters) {}

/**
 * Call a user method on a specific object or class 
 *
 * @return mixed
 * @param  method_name string
 * @param  object mixed
 * @param  parameter mixed[optional]
 * @vararg ... mixed
 */
function call_user_method($method_name, $object, $parameter = null) {}

/**
 * Call a user method on a specific object or class using a parameter array 
 *
 * @return mixed
 * @param  method_name string
 * @param  object mixed
 * @param  params array
 */
function call_user_method_array($method_name, $object, $params) {}

/**
 * Returns the next highest integer value of the number 
 *
 * @return float
 * @param  number float
 */
function ceil($number) {}

/**
 * Change the current directory 
 *
 * @return bool
 * @param  directory string
 */
function chdir($directory) {}

/**
 * Returns true(1) if it is a valid date in gregorian calendar 
 *
 * @return bool
 * @param  month int
 * @param  day int
 * @param  year int
 */
function checkdate($month, $day, $year) {}

/**
 * Check DNS records corresponding to a given Internet host name or IP address 
 *
 * @return bool
 * @param  host string
 * @param  type string[optional]
 */
function checkdnsrr($host, $type = null) {}

/**
 * Change file group 
 *
 * @return bool
 * @param  filename string
 * @param  group mixed
 */
function chgrp($filename, $group) {}

/**
 * Change file mode 
 *
 * @return bool
 * @param  filename string
 * @param  mode int
 */
function chmod($filename, $mode) {}

/**
 * Removes trailing whitespace 
 *
 * @return string
 * @param  str string
 * @param  character_mask string[optional]
 */
function chop($str, $character_mask = null) {}

/**
 * Change file owner 
 *
 * @return bool
 * @param  filename string
 * @param  user mixed
 */
function chown($filename, $user) {}

/**
 * Converts ASCII code to a character 
 *
 * @return string
 * @param  ascii int
 */
function chr($ascii) {}

/**
 * Change root directory 
 *
 * @return bool
 * @param  directory string
 */
function chroot($directory) {}

/**
 * Returns split line 
 *
 * @return string
 * @param  str string
 * @param  chunklen int[optional]
 * @param  ending string[optional]
 */
function chunk_split($str, $chunklen = null, $ending = null) {}

/**
 * Checks if the class exists 
 *
 * @return bool
 * @param  classname string
 * @param  autoload bool[optional]
 */
function class_exists($classname, $autoload = null) {}

/**
 * Return all classes and interfaces implemented by SPL 
 *
 * @return array
 * @param  what mixed
 * @param  autoload bool[optional]
 */
function class_implements($what, $autoload = null) {}

/**
 * Return an array containing the names of all parent classes 
 *
 * @return array
 * @param  instance object
 */
function class_parents($instance) {}

/**
 * Clear file stat cache 
 *
 * @return void
 */
function clearstatcache() {}

/**
 * Close directory connection identified by the dir_handle 
 *
 * @return void
 * @param  dir_handle resource[optional]
 */
function closedir($dir_handle = null) {}

/**
 * Close connection to system logger 
 *
 * @return bool
 */
function closelog() {}

/**
 * Generate a globally unique identifier (GUID) 
 *
 * @return string
 */
function com_create_guid() {}

/**
 * Connect events from a COM object to a PHP object 
 *
 * @return bool
 * @param  comobject object
 * @param  sinkobject object
 * @param  sinkinterface mixed[optional]
 */
function com_event_sink($comobject, $sinkobject, $sinkinterface = null) {}

/**
 * Returns a handle to an already running instance of a COM object 
 *
 * @return variant
 * @param  progid string
 * @param  code_page int[optional]
 */
function com_get_active_object($progid, $code_page = null) {}

/**
 * Loads a Typelibrary and registers its constants 
 *
 * @return bool
 * @param  typelib_name string
 * @param  case_insensitive int[optional]
 */
function com_load_typelib($typelib_name, $case_insensitive = null) {}

/**
 * Process COM messages, sleeping for up to timeoutms milliseconds 
 *
 * @return bool
 * @param  timeoutms int[optional]
 */
function com_message_pump($timeoutms = null) {}

/**
 * Print out a PHP class definition for a dispatchable interface 
 *
 * @return bool
 * @param  comobject_|_string_typelib object
 * @param  dispinterface string
 * @param  wantsink bool
 */
function com_print_typeinfo($comobject_, $dispinterface, $wantsink) {}

/**
 * Creates a hash containing variables and their values 
 *
 * @return array
 * @param  var_names mixed
 * @vararg ... mixed
 */
function compact($var_names) {}

/**
 * Return a string to confirm that the module is compiled in 
 *
 * @return string
 * @param  arg string
 */
function confirm_extname_compiled($arg) {}

/**
 * Returns true if client disconnected 
 *
 * @return int
 */
function connection_aborted() {}

/**
 * Returns the connection status bitfield 
 *
 * @return int
 */
function connection_status() {}

/**
 * Given the name of a constant this function will return the constants associated value 
 *
 * @return mixed
 * @param  const_name string
 */
function constant($const_name) {}

/**
 * Convert from one Cyrillic character set to another 
 *
 * @return string
 * @param  str string
 * @param  from string
 * @param  to string
 */
function convert_cyr_string($str, $from, $to) {}

/**
 * decode a uuencoded string 
 *
 * @return string
 * @param  data string
 */
function convert_uudecode($data) {}

/**
 * uuencode a string 
 *
 * @return string
 * @param  data string
 */
function convert_uuencode($data) {}

/**
 * Copy a file 
 *
 * @return bool
 * @param  source_file string
 * @param  destination_file string
 */
function copy($source_file, $destination_file) {}

/**
 * Returns the cosine of the number in radians 
 *
 * @return float
 * @param  number float
 */
function cos($number) {}

/**
 * Returns the hyperbolic cosine of the number, defined as (exp(number) + exp(-number))/2 
 *
 * @return float
 * @param  number float
 */
function cosh($number) {}

/**
 * Count the number of elements in a variable (usually an array) 
 *
 * @return int
 * @param  var mixed
 * @param  mode int[optional]
 */
function count($var, $mode = null) {}

/**
 * Returns info about what characters are used in input 
 *
 * @return mixed
 * @param  input string
 * @param  mode int[optional]
 */
function count_chars($input, $mode = null) {}

/**
 * Calculate the crc32 polynomial of a string 
 *
 * @return int
 * @param  str string
 */
function crc32($str) {}

/**
 * Creates an anonymous function, and returns its name (funny, eh?) 
 *
 * @return string
 * @param  args string
 * @param  code string
 */
function create_function($args, $code) {}

/**
 * Encrypt a string 
 *
 * @return string
 * @param  str string
 * @param  salt string[optional]
 */
function crypt($str, $salt = null) {}

/**
 * Checks for alphanumeric character(s) 
 *
 * @return bool
 * @param  c mixed
 */
function ctype_alnum($c) {}

/**
 * Checks for alphabetic character(s) 
 *
 * @return bool
 * @param  c mixed
 */
function ctype_alpha($c) {}

/**
 * Checks for control character(s) 
 *
 * @return bool
 * @param  c mixed
 */
function ctype_cntrl($c) {}

/**
 * Checks for numeric character(s) 
 *
 * @return bool
 * @param  c mixed
 */
function ctype_digit($c) {}

/**
 * Checks for any printable character(s) except space 
 *
 * @return bool
 * @param  c mixed
 */
function ctype_graph($c) {}

/**
 * Checks for lowercase character(s)  
 *
 * @return bool
 * @param  c mixed
 */
function ctype_lower($c) {}

/**
 * Checks for printable character(s) 
 *
 * @return bool
 * @param  c mixed
 */
function ctype_print($c) {}

/**
 * Checks for any printable character which is not whitespace or an alphanumeric character 
 *
 * @return bool
 * @param  c mixed
 */
function ctype_punct($c) {}

/**
 * Checks for whitespace character(s)
 *
 * @return bool
 * @param  c mixed
 */
function ctype_space($c) {}

/**
 * Checks for uppercase character(s) 
 *
 * @return bool
 * @param  c mixed
 */
function ctype_upper($c) {}

/**
 * Checks for character(s) representing a hexadecimal digit 
 *
 * @return bool
 * @param  c mixed
 */
function ctype_xdigit($c) {}

/**
 * Close a CURL session 
 *
 * @return void
 * @param  ch resource
 */
function curl_close($ch) {}

/**
 * Copy a cURL handle along with all of it's preferences 
 *
 * @return resource
 * @param  ch resource
 */
function curl_copy_handle($ch) {}

/**
 * Return an integer containing the last error number 
 *
 * @return int
 * @param  ch resource
 */
function curl_errno($ch) {}

/**
 * Return a string contain the last error for the current session 
 *
 * @return string
 * @param  ch resource
 */
function curl_error($ch) {}

/**
 * Perform a CURL session 
 *
 * @return mixed
 * @param  ch resource
 */
function curl_exec($ch) {}

/**
 * Get information regarding a specific transfer 
 *
 * @return mixed
 * @param  ch resource
 * @param  opt int
 */
function curl_getinfo($ch, $opt) {}

/**
 * Initialize a CURL session 
 *
 * @return resource
 * @param  url string[optional]
 */
function curl_init($url = null) {}

/**
 * Add a normal cURL handle to a cURL multi handle 
 *
 * @return int
 * @param  multi resource
 * @param  ch resource
 */
function curl_multi_add_handle($multi, $ch) {}

/**
 * Close a set of cURL handles 
 *
 * @return void
 * @param  mh resource
 */
function curl_multi_close($mh) {}

/**
 * Run the sub-connections of the current cURL handle 
 *
 * @return int
 * @param  mh resource
 * @param  still_running int
 */
function curl_multi_exec($mh, &$still_running) {}

/**
 * Return the content of a cURL handle if CURLOPT_RETURNTRANSFER is set 
 *
 * @return string
 * @param  ch resource
 */
function curl_multi_getcontent($ch) {}

/**
 * Get information about the current transfers 
 *
 * @return array
 * @param  mh resource
 */
function curl_multi_info_read($mh) {}

/**
 * Returns a new cURL multi handle 
 *
 * @return resource
 */
function curl_multi_init() {}

/**
 * Remove a multi handle from a set of cURL handles 
 *
 * @return int
 * @param  mh resource
 * @param  ch resource
 */
function curl_multi_remove_handle($mh, $ch) {}

/**
 * Get all the sockets associated with the cURL extension, which can then be "selected" 
 *
 * @return int
 * @param  mh resource
 * @param  timeout double
 */
function curl_multi_select($mh, $timeout) {}

/**
 * Set an option for a CURL transfer 
 *
 * @return bool
 * @param  ch resource
 * @param  option int
 * @param  value mixed
 */
function curl_setopt($ch, $option, $value) {}

/**
 * Set an array of option for a CURL transfer 
 *
 * @return bool
 * @param  ch resource
 * @param  options array
 */
function curl_setopt_array($ch, $options) {}

/**
 * Return cURL version information. 
 *
 * @return array
 * @param  version int[optional]
 */
function curl_version($version = null) {}

/**
 * Return the element currently pointed to by the internal array pointer 
 *
 * @return mixed
 * @param  array_arg array
 */
function current($array_arg) {}

/**
 * Format a local date/time 
 *
 * @return string
 * @param  format string
 * @param  timestamp long[optional]
 */
function date($format, $timestamp = null) {}

/**
 * Gets the default timezone used by all date/time functions in a script 
 *
 * @return string
 */
function date_default_timezone_get() {}

/**
 * Sets the default timezone used by all date/time functions in a script 
 *
 * @return bool
 * @param  timezone_identifier string
 */
function date_default_timezone_set($timezone_identifier) {}

/**
 * Returns an array with information about sun set/rise and twilight begin/end 
 *
 * @return array
 * @param  time long
 * @param  latitude float
 * @param  longitude float
 */
function date_sun_info($time, $latitude, $longitude) {}

/**
 * Returns time of sunrise for a given day and location 
 *
 * @return mixed
 * @param  time mixed
 * @param  format int[optional]
 * @param  latitude float[optional]
 * @param  longitude float[optional]
 * @param  zenith float[optional]
 * @param  gmt_offset float[optional]
 */
function date_sunrise($time, $format = null, $latitude = null, $longitude = null, $zenith = null, $gmt_offset = null) {}

/**
 * Returns time of sunset for a given day and location 
 *
 * @return mixed
 * @param  time mixed
 * @param  format int[optional]
 * @param  latitude float[optional]
 * @param  longitude float[optional]
 * @param  zenith float[optional]
 * @param  gmt_offset float[optional]
 */
function date_sunset($time, $format = null, $latitude = null, $longitude = null, $zenith = null, $gmt_offset = null) {}

/**
 * Returns or sets the AUTOCOMMIT state for a database connection 
 *
 * @return mixed
 * @param  connection resource
 * @param  value bool
 */
function db2_autocommit($connection, $value) {}

/**
 * Binds a PHP variable to an SQL statement parameter 
 *
 * @return bool
 * @param  stmt resource
 * @param  param_no long
 * @param  varname string
 * @param  param_type long[optional]
 * @param  data_type long[optional]
 * @param  precision long[optional]
 * @param  scale long[optional]
 */
function db2_bind_param($stmt, $param_no, $varname, $param_type = null, $data_type = null, $precision = null, $scale = null) {}

/**
 * Returns an object with properties that describe the DB2 database client 
 *
 * @return object
 * @param  connection resource
 */
function db2_client_info($connection) {}

/**
 * Closes a database connection 
 *
 * @return bool
 * @param  connection resource
 */
function db2_close($connection) {}

/**
 * Returns a result set listing the columns and associated privileges for a table 
 *
 * @return resource
 * @param  connection resource
 * @param  qualifier string
 * @param  owner string
 * @param  table_name string
 * @param  column_name string
 */
function db2_column_privileges($connection, $qualifier, $owner, $table_name, $column_name) {}

/**
 * Returns a result set listing the columns and associated privileges for a table 
 *
 * @return resource
 * @param  connection resource
 * @param  qualifier string
 * @param  owner string
 * @param  table_name string
 * @param  column_name string
 */
function db2_columnprivileges($connection, $qualifier, $owner, $table_name, $column_name) {}

/**
 * Returns a result set listing the columns and associated metadata for a table 
 *
 * @return resource
 * @param  connection resource
 * @param  qualifier string
 * @param  owner string
 * @param  table_name string
 * @param  column_name string
 */
function db2_columns($connection, $qualifier, $owner, $table_name, $column_name) {}

/**
 * Commits a transaction 
 *
 * @return bool
 * @param  connection resource
 */
function db2_commit($connection) {}

/**
 * Returns a string containing the SQLSTATE returned by the last connection attempt 
 *
 * @return string
 * @param  connection resource[optional]
 */
function db2_conn_error($connection = null) {}

/**
 * Returns a string containing the last connection error message 
 *
 * @return string
 * @param  connection resource[optional]
 */
function db2_conn_errormsg($connection = null) {}

/**
 * Returns a connection to a database 
 *
 * @return resource
 * @param  database string
 * @param  uid string
 * @param  password string
 * @param  options array[optional]
 */
function db2_connect($database, $uid, $password, $options = null) {}

/**
 * Returns the cursor type used by the indicated statement resource 
 *
 * @return int
 * @param  stmt resource
 */
function db2_cursor_type($stmt) {}

/**
 * Executes an SQL statement directly 
 *
 * @return resource
 * @param  connection resource
 * @param  stmt_string string
 * @param  options array[optional]
 */
function db2_exec($connection, $stmt_string, $options = null) {}

/**
 * Executes a prepared SQL statement 
 *
 * @return bool
 * @param  stmt resource
 * @param  parameters_array array[optional]
 */
function db2_execute($stmt, $parameters_array = null) {}

/**
 * Returns an array, indexed by column position, representing a row in a result set 
 *
 * @return array
 * @param  stmt resource
 * @param  row_number int[optional]
 */
function db2_fetch_array($stmt, $row_number = null) {}

/**
 * Returns an array, indexed by column name, representing a row in a result set 
 *
 * @return array
 * @param  stmt resource
 * @param  row_number int[optional]
 */
function db2_fetch_assoc($stmt, $row_number = null) {}

/**
 * Returns an array, indexed by both column name and position, representing a row in a result set 
 *
 * @return array
 * @param  stmt resource
 * @param  row_number int[optional]
 */
function db2_fetch_both($stmt, $row_number = null) {}

/**
 * Returns an object with properties that correspond to the fetched row 
 *
 * @return object
 * @param  stmt resource
 * @param  row_number int[optional]
 */
function db2_fetch_object($stmt, $row_number = null) {}

/**
 * Sets the fetch pointer to the next or requested row in a result set 
 *
 * @return bool
 * @param  stmt resource
 * @param  row_number int[optional]
 */
function db2_fetch_row($stmt, $row_number = null) {}

/**
 * Returns the maximum number of bytes required to display a column 
 *
 * @return int
 * @param  stmt resource
 * @param  column mixed
 */
function db2_field_display_size($stmt, $column) {}

/**
 * Returns the name of the column in the result set 
 *
 * @return string
 * @param  stmt resource
 * @param  column mixed
 */
function db2_field_name($stmt, $column) {}

/**
 * Returns the position of the named column in a result set 
 *
 * @return int
 * @param  stmt resource
 * @param  column mixed
 */
function db2_field_num($stmt, $column) {}

/**
 * Returns the precision for the indicated column in a result set 
 *
 * @return int
 * @param  stmt resource
 * @param  column mixed
 */
function db2_field_precision($stmt, $column) {}

/**
 * Returns the scale of the indicated column in a result set 
 *
 * @return int
 * @param  stmt resource
 * @param  column mixed
 */
function db2_field_scale($stmt, $column) {}

/**
 * Returns the data type of the indicated column in a result set 
 *
 * @return string
 * @param  stmt resource
 * @param  column mixed
 */
function db2_field_type($stmt, $column) {}

/**
 * Returns the width of the current value of the indicated column in a result set 
 *
 * @return int
 * @param  stmt resource
 * @param  column mixed
 */
function db2_field_width($stmt, $column) {}

/**
 * Returns a result set listing the foreign keys for a table 
 *
 * @return resource
 * @param  connection resource
 * @param  qualifier string
 * @param  owner string
 * @param  table_name string
 * @param  column_name string
 */
function db2_foreign_keys($connection, $qualifier, $owner, $table_name, $column_name) {}

/**
 * Returns a result set listing the foreign keys for a table 
 *
 * @return resource
 * @param  connection resource
 * @param  qualifier string
 * @param  owner string
 * @param  table_name string
 * @param  column_name string
 */
function db2_foreignkeys($connection, $qualifier, $owner, $table_name, $column_name) {}

/**
 * Frees resources associated with a result set 
 *
 * @return bool
 * @param  resource unknown
 */
function db2_free_result($resource) {}

/**
 * Frees resources associated with the indicated statement resource 
 *
 * @return bool
 * @param  stmt resource
 */
function db2_free_stmt($stmt) {}

/**
 * Requests the next result set from a stored procedure 
 *
 * @return resource
 * @param  stmt resource
 */
function db2_next_result($stmt) {}

/**
 * Returns the number of fields contained in a result set 
 *
 * @return int
 * @param  stmt resource
 */
function db2_num_fields($stmt) {}

/**
 * Returns the number of rows affected by an SQL statement 
 *
 * @return int
 * @param  stmt resource
 */
function db2_num_rows($stmt) {}

/**
 * Returns a persistent connection to a database 
 *
 * @return resource
 * @param  database_name string
 * @param  username string
 * @param  password string
 * @param  options array[optional]
 */
function db2_pconnect($database_name, $username, $password, $options = null) {}

/**
 * Prepares an SQL statement 
 *
 * @return resource
 * @param  connection resource
 * @param  stmt_string string
 * @param  options array[optional]
 */
function db2_prepare($connection, $stmt_string, $options = null) {}

/**
 * Returns a result set listing primary keys for a table 
 *
 * @return resource
 * @param  connection resource
 * @param  qualifier string
 * @param  owner string
 * @param  table_name string
 * @param  column_name string
 */
function db2_primary_keys($connection, $qualifier, $owner, $table_name, $column_name) {}

/**
 * Returns a result set listing primary keys for a table 
 *
 * @return resource
 * @param  connection resource
 * @param  qualifier string
 * @param  owner string
 * @param  table_name string
 * @param  column_name string
 */
function db2_primarykeys($connection, $qualifier, $owner, $table_name, $column_name) {}

/**
 * Returns a result set listing the input and output parameters for a stored procedure 
 *
 * @return resource
 * @param  connection resource
 * @param  qualifier string
 * @param  owner string
 * @param  table_name string
 * @param  column_name string
 */
function db2_procedure_columns($connection, $qualifier, $owner, $table_name, $column_name) {}

/**
 * Returns a result set listing the input and output parameters for a stored procedure 
 *
 * @return resource
 * @param  connection resource
 * @param  qualifier string
 * @param  owner string
 * @param  table_name string
 * @param  column_name string
 */
function db2_procedurecolumns($connection, $qualifier, $owner, $table_name, $column_name) {}

/**
 * Returns a result set listing the stored procedures registered in a database 
 *
 * @return resource
 * @param  connection resource
 * @param  qualifier string
 * @param  owner string
 * @param  proc_name string
 */
function db2_procedures($connection, $qualifier, $owner, $proc_name) {}

/**
 * Returns a single column from a row in the result set 
 *
 * @return mixed
 * @param  stmt resource
 * @param  column mixed
 */
function db2_result($stmt, $column) {}

/**
 * Rolls back a transaction 
 *
 * @return bool
 * @param  connection resource
 */
function db2_rollback($connection) {}

/**
 * Returns an object with properties that describe the DB2 database server 
 *
 * @return object
 * @param  connection resource
 */
function db2_server_info($connection) {}

/**
 * Sets the specified option in the resource. TYPE field specifies the resource type (1 = Connection) 
 *
 * @return bool
 * @param  stmt resource
 * @param  options array
 * @param  type int
 */
function db2_set_option($stmt, $options, $type) {}

/**
 * Sets the specified option in the resource. TYPE field specifies the resource type (1 = Connection) 
 *
 * @return bool
 * @param  stmt resource
 * @param  options array
 * @param  type int
 */
function db2_setoption($stmt, $options, $type) {}

/**
 * Returns a result set listing the unique row identifier columns for a table 
 *
 * @return resource
 * @param  connection resource
 * @param  qualifier string
 * @param  owner string
 * @param  table_name string
 * @param  column_name string
 */
function db2_special_columns($connection, $qualifier, $owner, $table_name, $column_name) {}

/**
 * Returns a result set listing the unique row identifier columns for a table 
 *
 * @return resource
 * @param  connection resource
 * @param  qualifier string
 * @param  owner string
 * @param  table_name string
 * @param  column_name string
 */
function db2_specialcolumns($connection, $qualifier, $owner, $table_name, $column_name) {}

/**
 * Returns a result set listing the index and statistics for a table 
 *
 * @return resource
 * @param  connection resource
 * @param  qualifier string
 * @param  owner string
 * @param  table_name string
 * @param  column_name string
 */
function db2_statistics($connection, $qualifier, $owner, $table_name, $column_name) {}

/**
 * Returns a string containing the SQLSTATE returned by an SQL statement 
 *
 * @return string
 * @param  stmt resource[optional]
 */
function db2_stmt_error($stmt = null) {}

/**
 * Returns a string containing the last SQL statement error message 
 *
 * @return string
 * @param  stmt resource[optional]
 */
function db2_stmt_errormsg($stmt = null) {}

/**
 * Returns a result set listing the tables and associated privileges in a database 
 *
 * @return resource
 * @param  connection resource
 * @param  qualifier string
 * @param  owner string
 * @param  table_name string
 * @param  column_name string
 */
function db2_table_privileges($connection, $qualifier, $owner, $table_name, $column_name) {}

/**
 * Returns a result set listing the tables and associated privileges in a database 
 *
 * @return resource
 * @param  connection resource
 * @param  qualifier string
 * @param  owner string
 * @param  table_name string
 * @param  column_name string
 */
function db2_tableprivileges($connection, $qualifier, $owner, $table_name, $column_name) {}

/**
 * Returns a result set listing the tables and associated metadata in a database 
 *
 * @return resource
 * @param  connection resource
 * @param  qualifier string
 * @param  owner string
 * @param  table_name string
 * @param  table_type string
 */
function db2_tables($connection, $qualifier, $owner, $table_name, $table_type) {}

/**
 * Closes database 
 *
 * @return void
 * @param  handle resource
 */
function dba_close($handle) {}

/**
 * 
 *
 * @return bool
 * @param  key string
 * @param  handle resource
 */
function dba_delete($key, $handle) {}

/**
 * Checks, if the specified key exists 
 *
 * @return bool
 * @param  key string
 * @param  handle resource
 */
function dba_exists($key, $handle) {}

/**
 * Fetches the data associated with key 
 *
 * @return string
 * @param  key string
 * @param  skip int[optional]
 * @param  handle resource
 */
function dba_fetch($key, $skip = null, $handle) {}

/**
 * Resets the internal key pointer and returns the first key 
 *
 * @return string
 * @param  handle resource
 */
function dba_firstkey($handle) {}

/**
 * List configured database handlers 
 *
 * @return array
 * @param  full_info bool[optional]
 */
function dba_handlers($full_info = null) {}

/**
 * 
 *
 * @return bool
 * @param  key string
 * @param  value string
 * @param  handle resource
 */
function dba_insert($key, $value, $handle) {}

/**
 * Splits an inifile key into an array of the form array(0=>group,1=>value_name) but returns false if input is false or null 
 *
 * @return mixed
 * @param  key string
 */
function dba_key_split($key) {}

/**
 * List opened databases 
 *
 * @return array
 */
function dba_list() {}

/**
 * Returns the next key 
 *
 * @return string
 * @param  handle resource
 */
function dba_nextkey($handle) {}

/**
 * Opens path using the specified handler in mode
 *
 * @return resource
 * @param  path string
 * @param  mode string
 * @param  handlername string[optional]
 * @vararg ... string
 */
function dba_open($path, $mode, $handlername = null) {}

/**
 * Optimizes (e.g. clean up, vacuum) database 
 *
 * @return bool
 * @param  handle resource
 */
function dba_optimize($handle) {}

/**
 * Opens path using the specified handler in mode persistently 
 *
 * @return resource
 * @param  path string
 * @param  mode string
 * @param  handlername string[optional]
 * @vararg ... string
 */
function dba_popen($path, $mode, $handlername = null) {}

/**
 * 
 *
 * @return bool
 * @param  key string
 * @param  value string
 * @param  handle resource
 */
function dba_replace($key, $value, $handle) {}

/**
 * Synchronizes database 
 *
 * @return bool
 * @param  handle resource
 */
function dba_sync($handle) {}

/**
 * Adds a record to the database 
 *
 * @return bool
 * @param  identifier int
 * @param  data array
 */
function dbase_add_record($identifier, $data) {}

/**
 * Closes an open dBase-format database file 
 *
 * @return bool
 * @param  identifier int
 */
function dbase_close($identifier) {}

/**
 * Creates a new dBase-format database file 
 *
 * @return int
 * @param  filename string
 * @param  fields array
 */
function dbase_create($filename, $fields) {}

/**
 * Marks a record to be deleted 
 *
 * @return bool
 * @param  identifier int
 * @param  record int
 */
function dbase_delete_record($identifier, $record) {}

/**
 * 
 *
 * @return array
 * @param  database_handle int
 */
function dbase_get_header_info($database_handle) {}

/**
 * Returns an array representing a record from the database 
 *
 * @return array
 * @param  identifier int
 * @param  record int
 */
function dbase_get_record($identifier, $record) {}

/**
 * Returns an associative array representing a record from the database 
 *
 * @return array
 * @param  identifier int
 * @param  record int
 */
function dbase_get_record_with_names($identifier, $record) {}

/**
 * Returns the number of fields (columns) in the database 
 *
 * @return int
 * @param  identifier int
 */
function dbase_numfields($identifier) {}

/**
 * Returns the number of records in the database 
 *
 * @return int
 * @param  identifier int
 */
function dbase_numrecords($identifier) {}

/**
 * Opens a dBase-format database file 
 *
 * @return int
 * @param  name string
 * @param  mode int
 */
function dbase_open($name, $mode) {}

/**
 * Packs the database (deletes records marked for deletion) 
 *
 * @return bool
 * @param  identifier int
 */
function dbase_pack($identifier) {}

/**
 * Replaces a record to the database 
 *
 * @return bool
 * @param  identifier int
 * @param  data array
 * @param  recnum int
 */
function dbase_replace_record($identifier, $data, $recnum) {}

/**
 * Return the translation of msgid for domain_name and category, or msgid unaltered if a translation does not exist 
 *
 * @return string
 * @param  domain_name string
 * @param  msgid string
 * @param  category long
 */
function dcgettext($domain_name, $msgid, $category) {}

/**
 * Plural version of dcgettext() 
 *
 * @return string
 * @param  domain string
 * @param  msgid1 string
 * @param  msgid2 string
 * @param  n int
 * @param  category int
 */
function dcngettext($domain, $msgid1, $msgid2, $n, $category) {}

/**
 * Return backtrace as array 
 *
 * @return array
 */
function debug_backtrace() {}

/**
 * 
 *
 * @return void
 */
function debug_print_backtrace() {}

/**
 * Dumps a string representation of an internal zend value to output. 
 *
 * @return void
 * @param  var mixed
 */
function debug_zval_dump($var) {}

/**
 * Obtain the server's start time as recorded by the Zend Debugger 
 *
 * @return long
 */
function debugger_get_server_start_time() {}

/**
 * Returns a string containing a binary representation of the number 
 *
 * @return string
 * @param  decimal_number int
 */
function decbin($decimal_number) {}

/**
 * Returns a string containing a hexadecimal representation of the given number 
 *
 * @return string
 * @param  decimal_number int
 */
function dechex($decimal_number) {}

/**
 * Returns a string containing an octal representation of the given number 
 *
 * @return string
 * @param  decimal_number int
 */
function decoct($decimal_number) {}

/**
 * Define a new constant 
 *
 * @return bool
 * @param  constant_name string
 * @param  value mixed
 * @param  case_sensitive boolean[optional]
 */
function define($constant_name, $value, $case_sensitive = true) {}

/**
 * Initializes all syslog-related variables 
 *
 * @return void
 */
function define_syslog_variables() {}

/**
 * Check whether a constant exists 
 *
 * @return bool
 * @param  constant_name string
 */
function defined($constant_name) {}

/**
 * Converts the number in degrees to the radian equivalent 
 *
 * @return float
 * @param  number float
 */
function deg2rad($number) {}

/**
 * Return the translation of msgid for domain_name, or msgid unaltered if a translation does not exist 
 *
 * @return string
 * @param  domain_name string
 * @param  msgid string
 */
function dgettext($domain_name, $msgid) {}

/**
 * Directory class with properties, handle and class and methods read, rewind and close 
 *
 * @return object
 * @param  directory string
 * @param  context resource
 */
function dir($directory, $context) {}

/**
 * 
 *
 * @return boolean
 * @param  directive string
 */
function directive_requires_restart($directive) {}

/**
 * Returns the directory name component of the path 
 *
 * @return string
 * @param  path string
 */
function dirname($path) {}

/**
 * Get free disk space for filesystem that path is on 
 *
 * @return float
 * @param  path string
 */
function disk_free_space($path) {}

/**
 * Get total disk space for filesystem that path is on 
 *
 * @return float
 * @param  path string
 */
function disk_total_space($path) {}

/**
 * Get free disk space for filesystem that path is on 
 *
 * @return float
 * @param  path string
 */
function diskfreespace($path) {}

/**
 * Load a PHP extension at runtime 
 *
 * @return int
 * @param  extension_filename string
 */
function dl($extension_filename) {}

/**
 * Plural version of dgettext() 
 *
 * @return string
 * @param  domain string
 * @param  msgid1 string
 * @param  msgid2 string
 * @param  count int
 */
function dngettext($domain, $msgid1, $msgid2, $count) {}

/**
 * Check DNS records corresponding to a given Internet host name or IP address 
 *
 * @return bool
 * @param  host string
 * @param  type string[optional]
 */
function dns_check_record($host, $type = null) {}

/**
 * Get MX records corresponding to a given Internet host name 
 *
 * @return bool
 * @param  hostname string
 * @param  mxhosts array
 * @param  weight array[optional]
 */
function dns_get_mx($hostname, $mxhosts, $weight = null) {}

/**
 * Get any Resource Record corresponding to a given Internet host name 
 *
 * @return array
 * @param  hostname string
 * @param  type int[optional]
 * @param  authns array
 * @param  addtl array
 */
function dns_get_record($hostname, $type = null, $authns, $addtl) {}

/**
 * 
 *
 * @return boolean
 */
function dom_attr_is_id() {}

/**
 * 
 *
 * @return void
 * @param  arg string
 */
function dom_characterdata_append_data($arg) {}

/**
 * 
 *
 * @return void
 * @param  offset int
 * @param  count int
 */
function dom_characterdata_delete_data($offset, $count) {}

/**
 * 
 *
 * @return void
 * @param  offset int
 * @param  arg string
 */
function dom_characterdata_insert_data($offset, $arg) {}

/**
 * 
 *
 * @return void
 * @param  offset int
 * @param  count int
 * @param  arg string
 */
function dom_characterdata_replace_data($offset, $count, $arg) {}

/**
 * 
 *
 * @return string
 * @param  offset int
 * @param  count int
 */
function dom_characterdata_substring_data($offset, $count) {}

/**
 * 
 *
 * @return DOMNode
 * @param  source DOMNode
 */
function dom_document_adopt_node($source) {}

/**
 * 
 *
 * @return DOMAttr
 * @param  name string
 */
function dom_document_create_attribute($name) {}

/**
 * 
 *
 * @return DOMAttr
 * @param  namespaceURI string
 * @param  qualifiedName string
 */
function dom_document_create_attribute_ns($namespaceURI, $qualifiedName) {}

/**
 * 
 *
 * @return DOMCdataSection
 * @param  data string
 */
function dom_document_create_cdatasection($data) {}

/**
 * 
 *
 * @return DOMComment
 * @param  data string
 */
function dom_document_create_comment($data) {}

/**
 * 
 *
 * @return DOMDocumentFragment
 */
function dom_document_create_document_fragment() {}

/**
 * 
 *
 * @return DOMElement
 * @param  tagName string
 * @param  value string[optional]
 */
function dom_document_create_element($tagName, $value = null) {}

/**
 * 
 *
 * @return DOMElement
 * @param  namespaceURI string
 * @param  qualifiedName string
 * @param  value string[optional]
 */
function dom_document_create_element_ns($namespaceURI, $qualifiedName, $value = null) {}

/**
 * 
 *
 * @return DOMEntityReference
 * @param  name string
 */
function dom_document_create_entity_reference($name) {}

/**
 * 
 *
 * @return DOMProcessingInstruction
 * @param  target string
 * @param  data string
 */
function dom_document_create_processing_instruction($target, $data) {}

/**
 * 
 *
 * @return DOMText
 * @param  data string
 */
function dom_document_create_text_node($data) {}

/**
 * 
 *
 * @return DOMElement
 * @param  elementId string
 */
function dom_document_get_element_by_id($elementId) {}

/**
 * 
 *
 * @return DOMNodeList
 * @param  tagname string
 */
function dom_document_get_elements_by_tag_name($tagname) {}

/**
 * 
 *
 * @return DOMNodeList
 * @param  namespaceURI string
 * @param  localName string
 */
function dom_document_get_elements_by_tag_name_ns($namespaceURI, $localName) {}

/**
 * 
 *
 * @return DOMNode
 * @param  importedNode DOMNode
 * @param  deep boolean
 */
function dom_document_import_node($importedNode, $deep) {}

/**
 * 
 *
 * @return DOMNode
 * @param  source string
 * @param  options int[optional]
 */
function dom_document_load($source, $options = null) {}

/**
 * 
 *
 * @return DOMNode
 * @param  source string
 */
function dom_document_load_html($source) {}

/**
 * 
 *
 * @return DOMNode
 * @param  source string
 */
function dom_document_load_html_file($source) {}

/**
 * 
 *
 * @return DOMNode
 * @param  source string
 * @param  options int[optional]
 */
function dom_document_loadxml($source, $options = null) {}

/**
 * 
 *
 * @return void
 */
function dom_document_normalize_document() {}

/**
 * 
 *
 * @return boolean
 * @param  filename string
 */
function dom_document_relaxNG_validate_file($filename) {}

/**
 * 
 *
 * @return boolean
 * @param  source string
 */
function dom_document_relaxNG_validate_xml($source) {}

/**
 * 
 *
 * @return DOMNode
 * @param  n node
 * @param  namespaceURI string
 * @param  qualifiedName string
 */
function dom_document_rename_node($n, $namespaceURI, $qualifiedName) {}

/**
 * 
 *
 * @return int
 * @param  file string
 */
function dom_document_save($file) {}

/**
 * 
 *
 * @return string
 */
function dom_document_save_html() {}

/**
 * 
 *
 * @return int
 * @param  file string
 */
function dom_document_save_html_file($file) {}

/**
 * 
 *
 * @return string
 * @param  n node[optional]
 */
function dom_document_savexml($n = null) {}

/**
 * 
 *
 * @return boolean
 * @param  source string
 */
function dom_document_schema_validate($source) {}

/**
 * 
 *
 * @return boolean
 * @param  filename string
 */
function dom_document_schema_validate_file($filename) {}

/**
 * 
 *
 * @return boolean
 */
function dom_document_validate() {}

/**
 * Substitutues xincludes in a DomDocument 
 *
 * @return int
 * @param  options int[optional]
 */
function dom_document_xinclude($options = null) {}

/**
 * 
 *
 * @return boolean
 * @param  name string
 * @param  value domuserdata
 */
function dom_domconfiguration_can_set_parameter($name, $value) {}

/**
 * 
 *
 * @return domdomuserdata
 * @param  name string
 */
function dom_domconfiguration_get_parameter($name) {}

/**
 * 
 *
 * @return dom_void
 * @param  name string
 * @param  value domuserdata
 */
function dom_domconfiguration_set_parameter($name, $value) {}

/**
 * 
 *
 * @return dom_boolean
 * @param  error domerror
 */
function dom_domerrorhandler_handle_error($error) {}

/**
 * 
 *
 * @return DOMDocument
 * @param  namespaceURI string
 * @param  qualifiedName string
 * @param  doctype DOMDocumentType
 */
function dom_domimplementation_create_document($namespaceURI, $qualifiedName, $doctype) {}

/**
 * 
 *
 * @return DOMDocumentType
 * @param  qualifiedName string
 * @param  publicId string
 * @param  systemId string
 */
function dom_domimplementation_create_document_type($qualifiedName, $publicId, $systemId) {}

/**
 * 
 *
 * @return DOMNode
 * @param  feature string
 * @param  version string
 */
function dom_domimplementation_get_feature($feature, $version) {}

/**
 * 
 *
 * @return boolean
 * @param  feature string
 * @param  version string
 */
function dom_domimplementation_has_feature($feature, $version) {}

/**
 * 
 *
 * @return domdomimplementation
 * @param  index int
 */
function dom_domimplementationlist_item($index) {}

/**
 * 
 *
 * @return domdomimplementation
 * @param  features string
 */
function dom_domimplementationsource_get_domimplementation($features) {}

/**
 * 
 *
 * @return domimplementationlist
 * @param  features string
 */
function dom_domimplementationsource_get_domimplementations($features) {}

/**
 * 
 *
 * @return domstring
 * @param  index int
 */
function dom_domstringlist_item($index) {}

/**
 * 
 *
 * @return string
 * @param  name string
 */
function dom_element_get_attribute($name) {}

/**
 * 
 *
 * @return DOMAttr
 * @param  name string
 */
function dom_element_get_attribute_node($name) {}

/**
 * 
 *
 * @return DOMAttr
 * @param  namespaceURI string
 * @param  localName string
 */
function dom_element_get_attribute_node_ns($namespaceURI, $localName) {}

/**
 * 
 *
 * @return string
 * @param  namespaceURI string
 * @param  localName string
 */
function dom_element_get_attribute_ns($namespaceURI, $localName) {}

/**
 * 
 *
 * @return DOMNodeList
 * @param  name string
 */
function dom_element_get_elements_by_tag_name($name) {}

/**
 * 
 *
 * @return DOMNodeList
 * @param  namespaceURI string
 * @param  localName string
 */
function dom_element_get_elements_by_tag_name_ns($namespaceURI, $localName) {}

/**
 * 
 *
 * @return boolean
 * @param  name string
 */
function dom_element_has_attribute($name) {}

/**
 * 
 *
 * @return boolean
 * @param  namespaceURI string
 * @param  localName string
 */
function dom_element_has_attribute_ns($namespaceURI, $localName) {}

/**
 * 
 *
 * @return void
 * @param  name string
 */
function dom_element_remove_attribute($name) {}

/**
 * 
 *
 * @return DOMAttr
 * @param  oldAttr DOMAttr
 */
function dom_element_remove_attribute_node($oldAttr) {}

/**
 * 
 *
 * @return void
 * @param  namespaceURI string
 * @param  localName string
 */
function dom_element_remove_attribute_ns($namespaceURI, $localName) {}

/**
 * 
 *
 * @return void
 * @param  name string
 * @param  value string
 */
function dom_element_set_attribute($name, $value) {}

/**
 * 
 *
 * @return DOMAttr
 * @param  newAttr DOMAttr
 */
function dom_element_set_attribute_node($newAttr) {}

/**
 * 
 *
 * @return DOMAttr
 * @param  newAttr DOMAttr
 */
function dom_element_set_attribute_node_ns($newAttr) {}

/**
 * 
 *
 * @return void
 * @param  namespaceURI string
 * @param  qualifiedName string
 * @param  value string
 */
function dom_element_set_attribute_ns($namespaceURI, $qualifiedName, $value) {}

/**
 * 
 *
 * @return void
 * @param  name string
 * @param  isId boolean
 */
function dom_element_set_id_attribute($name, $isId) {}

/**
 * 
 *
 * @return void
 * @param  idAttr attr
 * @param  isId boolean
 */
function dom_element_set_id_attribute_node($idAttr, $isId) {}

/**
 * 
 *
 * @return void
 * @param  namespaceURI string
 * @param  localName string
 * @param  isId boolean
 */
function dom_element_set_id_attribute_ns($namespaceURI, $localName, $isId) {}

/**
 * Get a simplexml_element object from dom to allow for processing 
 *
 * @return DOMElement
 * @param  node sxeobject
 */
function dom_import_simplexml($node) {}

/**
 * 
 *
 * @return DOMNode
 * @param  name string
 */
function dom_namednodemap_get_named_item($name) {}

/**
 * 
 *
 * @return DOMNode
 * @param  namespaceURI string
 * @param  localName string
 */
function dom_namednodemap_get_named_item_ns($namespaceURI, $localName) {}

/**
 * 
 *
 * @return DOMNode
 * @param  index int
 */
function dom_namednodemap_item($index) {}

/**
 * 
 *
 * @return DOMNode
 * @param  name string
 */
function dom_namednodemap_remove_named_item($name) {}

/**
 * 
 *
 * @return DOMNode
 * @param  namespaceURI string
 * @param  localName string
 */
function dom_namednodemap_remove_named_item_ns($namespaceURI, $localName) {}

/**
 * 
 *
 * @return DOMNode
 * @param  arg DOMNode
 */
function dom_namednodemap_set_named_item($arg) {}

/**
 * 
 *
 * @return DOMNode
 * @param  arg DOMNode
 */
function dom_namednodemap_set_named_item_ns($arg) {}

/**
 * 
 *
 * @return string
 * @param  index int
 */
function dom_namelist_get_name($index) {}

/**
 * 
 *
 * @return string
 * @param  index int
 */
function dom_namelist_get_namespace_uri($index) {}

/**
 * 
 *
 * @return DomNode
 * @param  newChild DomNode
 */
function dom_node_append_child($newChild) {}

/**
 * 
 *
 * @return DomNode
 * @param  deep boolean
 */
function dom_node_clone_node($deep) {}

/**
 * 
 *
 * @return short
 * @param  other DomNode
 */
function dom_node_compare_document_position($other) {}

/**
 * 
 *
 * @return DomNode
 * @param  feature string
 * @param  version string
 */
function dom_node_get_feature($feature, $version) {}

/**
 * 
 *
 * @return DomUserData
 * @param  key string
 */
function dom_node_get_user_data($key) {}

/**
 * 
 *
 * @return boolean
 */
function dom_node_has_attributes() {}

/**
 * 
 *
 * @return boolean
 */
function dom_node_has_child_nodes() {}

/**
 * 
 *
 * @return domnode
 * @param  newChild DomNode
 * @param  refChild DomNode
 */
function dom_node_insert_before($newChild, $refChild) {}

/**
 * 
 *
 * @return boolean
 * @param  namespaceURI string
 */
function dom_node_is_default_namespace($namespaceURI) {}

/**
 * 
 *
 * @return boolean
 * @param  arg DomNode
 */
function dom_node_is_equal_node($arg) {}

/**
 * 
 *
 * @return boolean
 * @param  other DomNode
 */
function dom_node_is_same_node($other) {}

/**
 * 
 *
 * @return boolean
 * @param  feature string
 * @param  version string
 */
function dom_node_is_supported($feature, $version) {}

/**
 * 
 *
 * @return string
 * @param  prefix string
 */
function dom_node_lookup_namespace_uri($prefix) {}

/**
 * 
 *
 * @return string
 * @param  namespaceURI string
 */
function dom_node_lookup_prefix($namespaceURI) {}

/**
 * 
 *
 * @return void
 */
function dom_node_normalize() {}

/**
 * 
 *
 * @return DomNode
 * @param  oldChild DomNode
 */
function dom_node_remove_child($oldChild) {}

/**
 * 
 *
 * @return DomNode
 * @param  newChild DomNode
 * @param  oldChild DomNode
 */
function dom_node_replace_child($newChild, $oldChild) {}

/**
 * 
 *
 * @return DomUserData
 * @param  key string
 * @param  data DomUserData
 * @param  handler userdatahandler
 */
function dom_node_set_user_data($key, $data, $handler) {}

/**
 * 
 *
 * @return DOMNode
 * @param  index int
 */
function dom_nodelist_item($index) {}

/**
 * 
 *
 * @return int
 * @param  offset32 int
 */
function dom_string_extend_find_offset16($offset32) {}

/**
 * 
 *
 * @return int
 * @param  offset16 int
 */
function dom_string_extend_find_offset32($offset16) {}

/**
 * 
 *
 * @return boolean
 */
function dom_text_is_whitespace_in_element_content() {}

/**
 * 
 *
 * @return DOMText
 * @param  content string
 */
function dom_text_replace_whole_text($content) {}

/**
 * 
 *
 * @return DOMText
 * @param  offset int
 */
function dom_text_split_text($offset) {}

/**
 * 
 *
 * @return dom_void
 * @param  operation short
 * @param  key string
 * @param  data domobject
 * @param  src node
 * @param  dst node
 */
function dom_userdatahandler_handle($operation, $key, $data, $src, $dst) {}

/**
 * 
 *
 * @return mixed
 * @param  expr string
 * @param  context DOMNode[optional]
 */
function dom_xpath_evaluate($expr, $context = null) {}

/**
 * 
 *
 * @return DOMNodeList
 * @param  expr string
 * @param  context DOMNode[optional]
 */
function dom_xpath_query($expr, $context = null) {}

/**
 * 
 *
 * @return boolean
 * @param  prefix string
 * @param  uri string
 */
function dom_xpath_register_ns($prefix, $uri) {}

/**
 * Get the float value of a variable 
 *
 * @return float
 * @param  var mixed
 */
function doubleval($var) {}

/**
 * Return the currently pointed key..value pair in the passed array, and advance the pointer to the next element 
 *
 * @return array
 * @param  arr array
 */
function each($arr) {}

/**
 * Return the timestamp of midnight on Easter of a given year (defaults to current year) 
 *
 * @return int
 * @param  year int[optional]
 */
function easter_date($year = null) {}

/**
 * Return the number of days after March 21 that Easter falls on for a given year (defaults to current year) 
 *
 * @return int
 * @param  year int[optional]
 * @param  method int[optional]
 */
function easter_days($year = null, $method = null) {}

/**
 * 
 *
 * @return void
 */
function embedded_server_end() {}

/**
 * initialize and start embedded server 
 *
 * @return bool
 * @param  start bool
 * @param  arguments array
 * @param  groups array
 */
function embedded_server_start($start, $arguments, $groups) {}

/**
 * Advances array argument's internal pointer to the last element and return it 
 *
 * @return mixed
 * @param  array_arg array
 */
function end($array_arg) {}

/**
 * Regular expression match 
 *
 * @return int
 * @param  pattern string
 * @param  string string
 * @param  registers array[optional]
 */
function ereg($pattern, $string, $registers = null) {}

/**
 * Replace regular expression 
 *
 * @return string
 * @param  pattern string
 * @param  replacement string
 * @param  string string
 */
function ereg_replace($pattern, $replacement, $string) {}

/**
 * Case-insensitive regular expression match 
 *
 * @return int
 * @param  pattern string
 * @param  string string
 * @param  registers array[optional]
 */
function eregi($pattern, $string, $registers = null) {}

/**
 * Case insensitive replace regular expression 
 *
 * @return string
 * @param  pattern string
 * @param  replacement string
 * @param  string string
 */
function eregi_replace($pattern, $replacement, $string) {}

/**
 * Send an error message somewhere 
 *
 * @return bool
 * @param  message string
 * @param  message_type int[optional]
 * @param  destination string[optional]
 * @param  extra_headers string[optional]
 */
function error_log($message, $message_type = null, $destination = null, $extra_headers = null) {}

/**
 * Return the current error_reporting level, and if an argument was passed - change to the new level 
 *
 * @return int
 * @param  new_error_level int[optional]
 */
function error_reporting($new_error_level = null) {}

/**
 * Quote and escape an argument for use in a shell command 
 *
 * @return string
 * @param  arg string
 */
function escapeshellarg($arg) {}

/**
 * Escape shell metacharacters 
 *
 * @return string
 * @param  command string
 */
function escapeshellcmd($command) {}

/**
 * 
 *
 * @return mixed
 * @param  expr string
 * @param  context DOMNode[optional]
 */
function evaluate($expr, $context = null) {}

/**
 * Execute an external program 
 *
 * @return string
 * @param  command string
 * @param  output array[optional]
 * @param  return_value int[optional]
 */
function exec($command, &$output, &$return_value) {}

/**
 * Get the type of an image 
 *
 * @return int
 * @param  imagefile string
 */
function exif_imagetype($imagefile) {}

/**
 * Reads header data from the JPEG/TIFF image filename and optionally reads the internal thumbnails 
 *
 * @return array
 * @param  filename string
 * @param  sections_needed unknown[optional]
 * @param  sub_arrays unknown[optional]
 * @param  read_thumbnail unknown
 */
function exif_read_data($filename, $sections_needed = null, $sub_arrays = null, $read_thumbnail) {}

/**
 * Get headername for index or false if not defined 
 *
 * @return string
 * @param  index unknown
 */
function exif_tagname($index) {}

/**
 * Reads the embedded thumbnail 
 *
 * @return string
 * @param  filename string
 * @param  width unknown[optional]
 * @param  height unknown
 * @param  imagetype unknown[optional]
 */
function exif_thumbnail($filename, &$width, &$height, &$imagetype) {}

/**
 * Returns e raised to the power of the number 
 *
 * @return float
 * @param  number float
 */
function exp($number) {}

/**
 * Splits a string on string separator and return array of components. If limit is positive only limit number of components is returned. If limit is negative all components except the last abs(limit) are returned. 
 *
 * @return array
 * @param  separator string
 * @param  str string
 * @param  limit int[optional]
 */
function explode($separator, $str, $limit = null) {}

/**
 * Returns exp(number) - 1, computed in a way that accurate even when the value of number is close to zero 
 *
 * @return float
 * @param  number float
 */
function expm1($number) {}

/**
 * Returns true if the named extension is loaded 
 *
 * @return bool
 * @param  extension_name string
 */
function extension_loaded($extension_name) {}

/**
 * Imports variables into symbol table from an array 
 *
 * @return int
 * @param  var_array array
 * @param  extract_type int[optional]
 * @param  prefix string[optional]
 */
function extract($var_array, $extract_type = null, $prefix = null) {}

/**
 * Calculate EZMLM list hash value. 
 *
 * @return int
 * @param  addr string
 */
function ezmlm_hash($addr) {}

/**
 * Add a user to security database 
 *
 * @return bool
 * @param  service_handle resource
 * @param  user_name string
 * @param  password string
 * @param  first_name string[optional]
 * @param  middle_name string[optional]
 * @param  last_name string[optional]
 */
function fbird_add_user($service_handle, $user_name, $password, $first_name = null, $middle_name = null, $last_name = null) {}

/**
 * Returns the number of rows affected by the previous INSERT, UPDATE or DELETE statement 
 *
 * @return int
 * @param  link_identifier resource[optional]
 */
function fbird_affected_rows($link_identifier = null) {}

/**
 * Initiates a backup task in the service manager and returns immediately 
 *
 * @return mixed
 * @param  service_handle resource
 * @param  source_db string
 * @param  dest_file string
 * @param  options int[optional]
 * @param  verbose bool[optional]
 */
function fbird_backup($service_handle, $source_db, $dest_file, $options = null, $verbose = null) {}

/**
 * Add data into created blob 
 *
 * @return void
 * @param  blob_handle resource
 * @param  data string
 */
function fbird_blob_add($blob_handle, $data) {}

/**
 * Cancel creating blob 
 *
 * @return bool
 * @param  blob_handle resource
 */
function fbird_blob_cancel($blob_handle) {}

/**
 * Close blob 
 *
 * @return mixed
 * @param  blob_handle resource
 */
function fbird_blob_close($blob_handle) {}

/**
 * Create blob for adding data 
 *
 * @return resource
 * @param  link_identifier resource[optional]
 */
function fbird_blob_create($link_identifier = null) {}

/**
 * Output blob contents to browser 
 *
 * @return bool
 * @param  link_identifier resource[optional]
 * @param  blob_id string
 */
function fbird_blob_echo($link_identifier = null, $blob_id) {}

/**
 * Get len bytes data from open blob 
 *
 * @return string
 * @param  blob_handle resource
 * @param  len int
 */
function fbird_blob_get($blob_handle, $len) {}

/**
 * Create blob, copy file in it, and close it 
 *
 * @return string
 * @param  link_identifier resource[optional]
 * @param  file resource
 */
function fbird_blob_import($link_identifier = null, $file) {}

/**
 * Return blob length and other useful info 
 *
 * @return array
 * @param  link_identifier resource[optional]
 * @param  blob_id string
 */
function fbird_blob_info($link_identifier = null, $blob_id) {}

/**
 * Open blob for retrieving data parts 
 *
 * @return resource
 * @param  link_identifier resource[optional]
 * @param  blob_id string
 */
function fbird_blob_open($link_identifier = null, $blob_id) {}

/**
 * Close an InterBase connection 
 *
 * @return bool
 * @param  link_identifier resource[optional]
 */
function fbird_close($link_identifier = null) {}

/**
 * Commit transaction 
 *
 * @return bool
 * @param  link_identifier resource
 */
function fbird_commit($link_identifier) {}

/**
 * Commit transaction and retain the transaction context 
 *
 * @return bool
 * @param  link_identifier resource
 */
function fbird_commit_ret($link_identifier) {}

/**
 * Open a connection to an InterBase database 
 *
 * @return resource
 * @param  database string
 * @param  username string[optional]
 * @param  password string[optional]
 * @param  charset string[optional]
 * @param  buffers int[optional]
 * @param  dialect int[optional]
 * @param  role string[optional]
 */
function fbird_connect($database, $username = null, $password = null, $charset = null, $buffers = null, $dialect = null, $role = null) {}

/**
 * Request statistics about a database 
 *
 * @return string
 * @param  service_handle resource
 * @param  db string
 * @param  action int
 * @param  argument int[optional]
 */
function fbird_db_info($service_handle, $db, $action, $argument = null) {}

/**
 * Delete a user from security database 
 *
 * @return bool
 * @param  service_handle resource
 * @param  user_name string
 * @param  password string
 * @param  first_name string[optional]
 * @param  middle_name string[optional]
 * @param  last_name string[optional]
 */
function fbird_delete_user($service_handle, $user_name, $password, $first_name = null, $middle_name = null, $last_name = null) {}

/**
 * Drop an InterBase database 
 *
 * @return bool
 * @param  link_identifier resource[optional]
 */
function fbird_drop_db($link_identifier = null) {}

/**
 * Return error code 
 *
 * @return int
 */
function fbird_errcode() {}

/**
 * Return error message 
 *
 * @return string
 */
function fbird_errmsg() {}

/**
 * Execute a previously prepared query 
 *
 * @return resource
 * @param  query resource
 * @param  bind_arg mixed[optional]
 * @param  bind_arg mixed[optional]
 * @vararg ...
 */
function fbird_execute($query, $bind_arg = null, $bind_arg = null) {}

/**
 * Fetch a row  from the results of a query 
 *
 * @return array
 * @param  result resource
 * @param  fetch_flags int[optional]
 */
function fbird_fetch_assoc($result, $fetch_flags = null) {}

/**
 * Fetch a object from the results of a query 
 *
 * @return object
 * @param  result resource
 * @param  fetch_flags int[optional]
 */
function fbird_fetch_object($result, $fetch_flags = null) {}

/**
 * Fetch a row  from the results of a query 
 *
 * @return array
 * @param  result resource
 * @param  fetch_flags int[optional]
 */
function fbird_fetch_row($result, $fetch_flags = null) {}

/**
 * Get information about a field 
 *
 * @return array
 * @param  query_result resource
 * @param  field_number int
 */
function fbird_field_info($query_result, $field_number) {}

/**
 * Frees the event handler set by ibase_set_event_handler() 
 *
 * @return bool
 * @param  event resource
 */
function fbird_free_event_handler($event) {}

/**
 * Free memory used by a query 
 *
 * @return bool
 * @param  query resource
 */
function fbird_free_query($query) {}

/**
 * Free the memory used by a result 
 *
 * @return bool
 * @param  result resource
 */
function fbird_free_result($result) {}

/**
 * Increments the named generator and returns its new value 
 *
 * @return mixed
 * @param  generator string
 * @param  increment int[optional]
 * @param  link_identifier resource[optional]
 */
function fbird_gen_id($generator, $increment = null, $link_identifier = null) {}

/**
 * Execute a maintenance command on the database server 
 *
 * @return bool
 * @param  service_handle resource
 * @param  db string
 * @param  action int
 * @param  argument int[optional]
 */
function fbird_maintain_db($service_handle, $db, $action, $argument = null) {}

/**
 * Modify a user in security database 
 *
 * @return bool
 * @param  service_handle resource
 * @param  user_name string
 * @param  password string
 * @param  first_name string[optional]
 * @param  middle_name string[optional]
 * @param  last_name string[optional]
 */
function fbird_modify_user($service_handle, $user_name, $password, $first_name = null, $middle_name = null, $last_name = null) {}

/**
 * Assign a name to a result for use with ... WHERE CURRENT OF <name> statements 
 *
 * @return bool
 * @param  result resource
 * @param  name string
 */
function fbird_name_result($result, $name) {}

/**
 * Get the number of fields in result 
 *
 * @return int
 * @param  query_result resource
 */
function fbird_num_fields($query_result) {}

/**
 * Get the number of params in a prepared query 
 *
 * @return int
 * @param  query resource
 */
function fbird_num_params($query) {}

/**
 * Return the number of rows that are available in a result 
 *
 * @return int
 * @param  result_identifier resource
 */
function fbird_num_rows($result_identifier) {}

/**
 * Get information about a parameter 
 *
 * @return array
 * @param  query resource
 * @param  field_number int
 */
function fbird_param_info($query, $field_number) {}

/**
 * Open a persistent connection to an InterBase database 
 *
 * @return resource
 * @param  database string
 * @param  username string[optional]
 * @param  password string[optional]
 * @param  charset string[optional]
 * @param  buffers int[optional]
 * @param  dialect int[optional]
 * @param  role string[optional]
 */
function fbird_pconnect($database, $username = null, $password = null, $charset = null, $buffers = null, $dialect = null, $role = null) {}

/**
 * Prepare a query for later execution 
 *
 * @return resource
 * @param  link_identifier resource[optional]
 * @param  query string
 */
function fbird_prepare($link_identifier = null, $query) {}

/**
 * Execute a query 
 *
 * @return resource
 * @param  link_identifier resource[optional]
 * @param  link_identifier resource[optional]
 * @param  query string
 * @param  bind_arg mixed[optional]
 * @param  bind_arg mixed[optional]
 * @vararg ...
 */
function fbird_query($link_identifier = null, $link_identifier = null, $query, $bind_arg = null, $bind_arg = null) {}

/**
 * Initiates a restore task in the service manager and returns immediately 
 *
 * @return mixed
 * @param  service_handle resource
 * @param  source_file string
 * @param  dest_db string
 * @param  options int[optional]
 * @param  verbose bool[optional]
 */
function fbird_restore($service_handle, $source_file, $dest_db, $options = null, $verbose = null) {}

/**
 * Rollback transaction 
 *
 * @return bool
 * @param  link_identifier resource
 */
function fbird_rollback($link_identifier) {}

/**
 * Rollback transaction and retain the transaction context 
 *
 * @return bool
 * @param  link_identifier resource
 */
function fbird_rollback_ret($link_identifier) {}

/**
 * Request information about a database server 
 *
 * @return string
 * @param  service_handle resource
 * @param  action int
 */
function fbird_server_info($service_handle, $action) {}

/**
 * Connect to the service manager 
 *
 * @return resource
 * @param  host string
 * @param  dba_username string
 * @param  dba_password string
 */
function fbird_service_attach($host, $dba_username, $dba_password) {}

/**
 * Disconnect from the service manager 
 *
 * @return bool
 * @param  service_handle resource
 */
function fbird_service_detach($service_handle) {}

/**
 * Register the callback for handling each of the named events 
 *
 * @return resource
 * @param  link_identifier resource[optional]
 * @param  handler callback
 * @param  event string
 * @param  event string[optional]
 * @vararg ...
 */
function fbird_set_event_handler($link_identifier = null, $handler, $event, $event = null) {}

/**
 * Start a transaction over one or several databases 
 *
 * @return resource
 * @param  trans_args int[optional]
 * @param  link_identifier resource[optional]
 * @vararg ...
 * @param  trans_args int
 * @param  link_identifier resource[optional]
 * @vararg ...
 * @vararg ...
 */
function fbird_trans($trans_args = null, $link_identifier = null, $trans_args, $link_identifier = null) {}

/**
 * Waits for any one of the passed Interbase events to be posted by the database, and returns its name 
 *
 * @return string
 * @param  link_identifier resource[optional]
 * @param  event string
 * @param  event string[optional]
 * @vararg ...
 */
function fbird_wait_event($link_identifier = null, $event, $event = null) {}

/**
 * Send one or more SQL statements to a specified database on the server 
 *
 * @return resource
 * @param  database_name string
 * @param  query string
 * @param  link_identifier resource[optional]
 */
function fbsql($database_name, $query, $link_identifier = null) {}

/**
 * Get the number of rows affected by the last statement 
 *
 * @return int
 * @param  link_identifier resource[optional]
 */
function fbsql_affected_rows($link_identifier = null) {}

/**
 * Turns on auto-commit 
 *
 * @return bool
 * @param  link_identifier resource
 * @param  OnOff bool[optional]
 */
function fbsql_autocommit($link_identifier, $OnOff = null) {}

/**
 * Get the size of a BLOB identified by blob_handle 
 *
 * @return int
 * @param  blob_handle string
 * @param  link_identifier resource[optional]
 */
function fbsql_blob_size($blob_handle, $link_identifier = null) {}

/**
 * Change the user for a session 
 *
 * @return resource
 * @param  user string
 * @param  password string
 * @param  database string[optional]
 * @param  link_identifier resource[optional]
 */
function fbsql_change_user($user, $password, $database = null, $link_identifier = null) {}

/**
 * Get the size of a CLOB identified by clob_handle 
 *
 * @return int
 * @param  clob_handle string
 * @param  link_identifier resource[optional]
 */
function fbsql_clob_size($clob_handle, $link_identifier = null) {}

/**
 * Close a connection to a database server 
 *
 * @return bool
 * @param  link_identifier resource[optional]
 */
function fbsql_close($link_identifier = null) {}

/**
 * Commit the transaction 
 *
 * @return bool
 * @param  link_identifier resource[optional]
 */
function fbsql_commit($link_identifier = null) {}

/**
 * Create a connection to a database server 
 *
 * @return resource
 * @param  hostname string[optional]
 * @param  username string[optional]
 * @param  password string[optional]
 */
function fbsql_connect($hostname = null, $username = null, $password = null) {}

/**
 * Create a BLOB in the database for use with an insert or update statement 
 *
 * @return string
 * @param  blob_data string
 * @param  link_identifier resource[optional]
 */
function fbsql_create_blob($blob_data, $link_identifier = null) {}

/**
 * Create a CLOB in the database for use with an insert or update statement 
 *
 * @return string
 * @param  clob_data string
 * @param  link_identifier resource[optional]
 */
function fbsql_create_clob($clob_data, $link_identifier = null) {}

/**
 * Create a new database on the server 
 *
 * @return bool
 * @param  database_name string
 * @param  link_identifier resource[optional]
 */
function fbsql_create_db($database_name, $link_identifier = null) {}

/**
 * Move the internal row counter to the specified row_number 
 *
 * @return bool
 * @param  result int
 * @param  row_number int
 */
function fbsql_data_seek($result, $row_number) {}

/**
 * Get or set the database name used with a connection 
 *
 * @return string
 * @param  link_identifier resource
 * @param  database string[optional]
 */
function fbsql_database($link_identifier, $database = null) {}

/**
 * Get or set the databsae password used with a connection 
 *
 * @return string
 * @param  link_identifier resource
 * @param  database_password string[optional]
 */
function fbsql_database_password($link_identifier, $database_password = null) {}

/**
 * Send one or more SQL statements to a specified database on the server 
 *
 * @return resource
 * @param  database_name string
 * @param  query string
 * @param  link_identifier resource[optional]
 */
function fbsql_db_query($database_name, $query, $link_identifier = null) {}

/**
 * Gets the status (Stopped, Starting, Running, Stopping) for a given database 
 *
 * @return int
 * @param  database_name string
 * @param  link_identifier resource[optional]
 */
function fbsql_db_status($database_name, $link_identifier = null) {}

/**
 * Drop a database on the server 
 *
 * @return bool
 * @param  database_name string
 * @param  link_identifier resource[optional]
 */
function fbsql_drop_db($database_name, $link_identifier = null) {}

/**
 * Returns the last error code 
 *
 * @return int
 * @param  link_identifier resource[optional]
 */
function fbsql_errno($link_identifier = null) {}

/**
 * Returns the last error string 
 *
 * @return string
 * @param  link_identifier resource[optional]
 */
function fbsql_error($link_identifier = null) {}

/**
 * Fetches a result row as an array (associative, numeric or both)
 *
 * @return array
 * @param  result resource
 * @param  result_type int[optional]
 */
function fbsql_fetch_array($result, $result_type = null) {}

/**
 * Detch a row of data. Returns an assoc array 
 *
 * @return array
 * @param  result resource
 */
function fbsql_fetch_assoc($result) {}

/**
 * Get the field properties for a specified field_index 
 *
 * @return object
 * @param  result int
 * @param  field_index int[optional]
 */
function fbsql_fetch_field($result, $field_index = null) {}

/**
 * Returns an array of the lengths of each column in the result set 
 *
 * @return array
 * @param  result int
 */
function fbsql_fetch_lengths($result) {}

/**
 * Fetch a row of data. Returns an object 
 *
 * @return object
 * @param  result resource
 * @param  result_type int[optional]
 */
function fbsql_fetch_object($result, $result_type = null) {}

/**
 * Fetch a row of data. Returns an indexed array 
 *
 * @return array
 * @param  result resource
 */
function fbsql_fetch_row($result) {}

/**
 * ??? 
 *
 * @return string
 * @param  result int
 * @param  field_index int[optional]
 */
function fbsql_field_flags($result, $field_index = null) {}

/**
 * Get the column length for a specified field_index 
 *
 * @return int
 * @param  result int
 * @param  field_index int[optional]
 */
function fbsql_field_len($result, $field_index = null) {}

/**
 * Get the column name for a specified field_index 
 *
 * @return string
 * @param  result int
 * @param  field_index int[optional]
 */
function fbsql_field_name($result, $field_index = null) {}

/**
 * ??? 
 *
 * @return bool
 * @param  result int
 * @param  field_index int[optional]
 */
function fbsql_field_seek($result, $field_index = null) {}

/**
 * Get the table name for a specified field_index 
 *
 * @return string
 * @param  result int
 * @param  field_index int[optional]
 */
function fbsql_field_table($result, $field_index = null) {}

/**
 * Get the field type for a specified field_index 
 *
 * @return string
 * @param  result int
 * @param  field_index int[optional]
 */
function fbsql_field_type($result, $field_index = null) {}

/**
 * free the memory used to store a result 
 *
 * @return bool
 * @param  result resource
 */
function fbsql_free_result($result) {}

/**
 * ??? 
 *
 * @return array
 * @param  link_identifier resource[optional]
 */
function fbsql_get_autostart_info($link_identifier = null) {}

/**
 * Get or set the host name used with a connection 
 *
 * @return string
 * @param  link_identifier resource
 * @param  host_name string[optional]
 */
function fbsql_hostname($link_identifier, $host_name = null) {}

/**
 * Get the internal index for the last insert statement 
 *
 * @return int
 * @param  link_identifier resource[optional]
 */
function fbsql_insert_id($link_identifier = null) {}

/**
 * Retreive a list of all databases on the server 
 *
 * @return resource
 * @param  link_identifier resource[optional]
 */
function fbsql_list_dbs($link_identifier = null) {}

/**
 * Retrieve a list of all fields for the specified database.table 
 *
 * @return resource
 * @param  database_name string
 * @param  table_name string
 * @param  link_identifier resource[optional]
 */
function fbsql_list_fields($database_name, $table_name, $link_identifier = null) {}

/**
 * Retreive a list of all tables from the specifoied database 
 *
 * @return resource
 * @param  database string
 * @param  link_identifier int[optional]
 */
function fbsql_list_tables($database, $link_identifier = null) {}

/**
 * Switch to the next result if multiple results are available 
 *
 * @return bool
 * @param  result int
 */
function fbsql_next_result($result) {}

/**
 * Get number of fields in the result set 
 *
 * @return int
 * @param  result int
 */
function fbsql_num_fields($result) {}

/**
 * Get number of rows 
 *
 * @return int
 * @param  result int
 */
function fbsql_num_rows($result) {}

/**
 * Get or set the user password used with a connection 
 *
 * @return string
 * @param  link_identifier resource
 * @param  password string[optional]
 */
function fbsql_password($link_identifier, $password = null) {}

/**
 * Create a persistant connection to a database server 
 *
 * @return resource
 * @param  hostname string[optional]
 * @param  username string[optional]
 * @param  password string[optional]
 */
function fbsql_pconnect($hostname = null, $username = null, $password = null) {}

/**
 * Send one or more SQL statements to the server and execute them 
 *
 * @return resource
 * @param  query string
 * @param  link_identifier resource[optional]
 * @param  batch_size long[optional]
 */
function fbsql_query($query, $link_identifier = null, $batch_size = null) {}

/**
 * Read the BLOB data identified by blob_handle 
 *
 * @return string
 * @param  blob_handle string
 * @param  link_identifier resource[optional]
 */
function fbsql_read_blob($blob_handle, $link_identifier = null) {}

/**
 * Read the CLOB data identified by clob_handle 
 *
 * @return string
 * @param  clob_handle string
 * @param  link_identifier resource[optional]
 */
function fbsql_read_clob($clob_handle, $link_identifier = null) {}

/**
 * ??? 
 *
 * @return mixed
 * @param  result int
 * @param  row int[optional]
 * @param  field mixed[optional]
 */
function fbsql_result($result, $row = null, $field = null) {}

/**
 * Rollback all statments since last commit 
 *
 * @return bool
 * @param  link_identifier resource[optional]
 */
function fbsql_rollback($link_identifier = null) {}

/**
 * Select the database to open 
 *
 * @return bool
 * @param  database_name string[optional]
 * @param  link_identifier resource[optional]
 */
function fbsql_select_db($database_name = null, $link_identifier = null) {}

/**
 * Change input/output character set 
 *
 * @return void
 * @param  link_identifier resource
 * @param  charcterset long
 * @param  in_out_both long[optional]
 */
function fbsql_set_characterset($link_identifier, $charcterset, $in_out_both = null) {}

/**
 * Sets the mode for how LOB data re retreived (actual data or a handle) 
 *
 * @return bool
 * @param  result resource
 * @param  lob_mode int
 */
function fbsql_set_lob_mode($result, $lob_mode) {}

/**
 * Change the password for a given user 
 *
 * @return bool
 * @param  link_identifier resource
 * @param  user string
 * @param  password string
 * @param  old_password string
 */
function fbsql_set_password($link_identifier, $user, $password, $old_password) {}

/**
 * Sets the transaction locking and isolation 
 *
 * @return void
 * @param  link_identifier resource
 * @param  locking int
 * @param  isolation int
 */
function fbsql_set_transaction($link_identifier, $locking, $isolation) {}

/**
 * Start a database on the server 
 *
 * @return bool
 * @param  database_name string
 * @param  link_identifier resource[optional]
 * @param  database_options string[optional]
 */
function fbsql_start_db($database_name, $link_identifier = null, $database_options = null) {}

/**
 * Stop a database on the server 
 *
 * @return bool
 * @param  database_name string
 * @param  link_identifier resource[optional]
 */
function fbsql_stop_db($database_name, $link_identifier = null) {}

/**
 * Retreive the table name for index after a call to fbsql_list_tables() 
 *
 * @return string
 * @param  result resource
 * @param  index int
 */
function fbsql_table_name($result, $index) {}

/**
 * Retreive the table name for index after a call to fbsql_list_tables() 
 *
 * @return string
 * @param  result resource
 * @param  index int
 */
function fbsql_tablename($result, $index) {}

/**
 * Get or set the host user used with a connection 
 *
 * @return string
 * @param  link_identifier resource
 * @param  username string[optional]
 */
function fbsql_username($link_identifier, $username = null) {}

/**
 * Enable or disable FrontBase warnings 
 *
 * @return bool
 * @param  flag int[optional]
 */
function fbsql_warnings($flag = null) {}

/**
 * Close an open file pointer 
 *
 * @return bool
 * @param  fp resource
 */
function fclose($fp) {}

/**
 * Add javascript code to the fdf file 
 *
 * @return bool
 * @param  fdfdoc resource
 * @param  scriptname string
 * @param  script string
 */
function fdf_add_doc_javascript($fdfdoc, $scriptname, $script) {}

/**
 * Adds a template into the FDF document 
 *
 * @return bool
 * @param  fdfdoc resource
 * @param  newpage int
 * @param  filename string
 * @param  template string
 * @param  rename int
 */
function fdf_add_template($fdfdoc, $newpage, $filename, $template, $rename) {}

/**
 * Closes the FDF document 
 *
 * @return void
 * @param  fdfdoc resource
 */
function fdf_close($fdfdoc) {}

/**
 * Creates a new FDF document 
 *
 * @return resource
 */
function fdf_create() {}

/**
 * Call a user defined function for each document value 
 *
 * @return bool
 * @param  fdfdoc resource
 * @param  function callback
 * @param  userdata mixed[optional]
 */
function fdf_enum_values($fdfdoc, $function, $userdata = null) {}

/**
 * Gets error code for last operation 
 *
 * @return int
 */
function fdf_errno() {}

/**
 * Gets error description for error code 
 *
 * @return string
 * @param  errno int[optional]
 */
function fdf_error($errno = null) {}

/**
 * Gets the appearance of a field and creates a PDF document out of it. 
 *
 * @return bool
 * @param  fdfdoc resource
 * @param  fieldname string
 * @param  face int
 * @param  filename string
 */
function fdf_get_ap($fdfdoc, $fieldname, $face, $filename) {}

/**
 * Get attached uploaded file 
 *
 * @return array
 * @param  fdfdoc resource
 * @param  fieldname string
 * @param  savepath string
 */
function fdf_get_attachment($fdfdoc, $fieldname, $savepath) {}

/**
 * Gets FDF file encoding scheme 
 *
 * @return string
 * @param  fdf resource
 */
function fdf_get_encoding($fdf) {}

/**
 * Gets the value of /F key 
 *
 * @return string
 * @param  fdfdoc resource
 */
function fdf_get_file($fdfdoc) {}

/**
 * Gets the flags of a field 
 *
 * @return int
 * @param  fdfdoc resorce
 * @param  fieldname string
 * @param  whichflags int
 */
function fdf_get_flags($fdfdoc, $fieldname, $whichflags) {}

/**
 * Gets a value from the opt array of a field 
 *
 * @return mixed
 * @param  fdfdof resource
 * @param  fieldname string
 * @param  element int[optional]
 */
function fdf_get_opt($fdfdof, $fieldname, $element = null) {}

/**
 * Gets the value of /Status key 
 *
 * @return string
 * @param  fdfdoc resource
 */
function fdf_get_status($fdfdoc) {}

/**
 * Gets the value of a field as string 
 *
 * @return mixed
 * @param  fdfdoc resource
 * @param  fieldname string
 * @param  which int[optional]
 */
function fdf_get_value($fdfdoc, $fieldname, $which = null) {}

/**
 * Gets version number for FDF api or file 
 *
 * @return string
 * @param  fdfdoc resource[optional]
 */
function fdf_get_version($fdfdoc = null) {}

/**
 * Set FDF specific HTTP headers 
 *
 * @return void
 */
function fdf_header() {}

/**
 * Gets the name of the next field name or the first field name 
 *
 * @return string
 * @param  fdfdoc resource
 * @param  fieldname string[optional]
 */
function fdf_next_field_name($fdfdoc, $fieldname = null) {}

/**
 * Opens a new FDF document 
 *
 * @return resource
 * @param  filename string
 */
function fdf_open($filename) {}

/**
 * Opens a new FDF document from string 
 *
 * @return resource
 * @param  fdf_data string
 */
function fdf_open_string($fdf_data) {}

/**
 * Sets target frame for form 
 *
 * @return bool
 * @param  fdfdoc resource
 * @param  fieldname string
 * @param  item int
 */
function fdf_remove_item($fdfdoc, $fieldname, $item) {}

/**
 * Writes out the FDF file 
 *
 * @return bool
 * @param  fdfdoc resource
 * @param  filename string[optional]
 */
function fdf_save($fdfdoc, $filename = null) {}

/**
 * Returns the FDF file as a string 
 *
 * @return string
 * @param  fdfdoc resource
 */
function fdf_save_string($fdfdoc) {}

/**
 * Sets the appearence of a field 
 *
 * @return bool
 * @param  fdfdoc resource
 * @param  fieldname string
 * @param  face int
 * @param  filename string
 * @param  pagenr int
 */
function fdf_set_ap($fdfdoc, $fieldname, $face, $filename, $pagenr) {}

/**
 * Sets FDF encoding (either "Shift-JIS" or "Unicode") 
 *
 * @return bool
 * @param  fdf_document resource
 * @param  encoding string
 */
function fdf_set_encoding($fdf_document, $encoding) {}

/**
 * Sets the value of /F key 
 *
 * @return bool
 * @param  fdfdoc resource
 * @param  filename string
 * @param  target_frame string[optional]
 */
function fdf_set_file($fdfdoc, $filename, $target_frame = null) {}

/**
 * Sets flags for a field in the FDF document 
 *
 * @return bool
 * @param  fdfdoc resource
 * @param  fieldname string
 * @param  whichflags int
 * @param  newflags int
 */
function fdf_set_flags($fdfdoc, $fieldname, $whichflags, $newflags) {}

/**
 * Sets the javascript action for a field 
 *
 * @return bool
 * @param  fdfdoc resource
 * @param  fieldname string
 * @param  whichtrigger int
 * @param  script string
 */
function fdf_set_javascript_action($fdfdoc, $fieldname, $whichtrigger, $script) {}

/**
 * Adds javascript code to be executed when Acrobat opens the FDF 
 *
 * @return bool
 * @param  fdfdoc resource
 * @param  script string
 * @param  before_data_import bool[optional]
 */
function fdf_set_on_import_javascript($fdfdoc, $script, $before_data_import = null) {}

/**
 * Sets a value in the opt array for a field 
 *
 * @return bool
 * @param  fdfdoc resource
 * @param  fieldname string
 * @param  element int
 * @param  value string
 * @param  name string
 */
function fdf_set_opt($fdfdoc, $fieldname, $element, $value, $name) {}

/**
 * Sets the value of /Status key 
 *
 * @return bool
 * @param  fdfdoc resource
 * @param  status string
 */
function fdf_set_status($fdfdoc, $status) {}

/**
 * Sets the submit form action for a field 
 *
 * @return bool
 * @param  fdfdoc resource
 * @param  fieldname string
 * @param  whichtrigger int
 * @param  url string
 * @param  flags int
 */
function fdf_set_submit_form_action($fdfdoc, $fieldname, $whichtrigger, $url, $flags) {}

/**
 * Sets target frame for form 
 *
 * @return bool
 * @param  fdfdoc resource
 * @param  target string
 */
function fdf_set_target_frame($fdfdoc, $target) {}

/**
 * Sets the value of a field 
 *
 * @return bool
 * @param  fdfdoc resource
 * @param  fieldname string
 * @param  value mixed
 * @param  isname int[optional]
 */
function fdf_set_value($fdfdoc, $fieldname, $value, $isname = null) {}

/**
 * Sets FDF version for a file
 *
 * @return bool
 * @param  fdfdoc resourece
 * @param  version string
 */
function fdf_set_version($fdfdoc, $version) {}

/**
 * Test for end-of-file on a file pointer 
 *
 * @return bool
 * @param  fp resource
 */
function feof($fp) {}

/**
 * Flushes output 
 *
 * @return bool
 * @param  fp resource
 */
function fflush($fp) {}

/**
 * Get a character from file pointer 
 *
 * @return string
 * @param  fp resource
 */
function fgetc($fp) {}

/**
 * Get line from file pointer and parse for CSV fields 
 *
 * @return array
 * @param  fp resource
 * @param  length int[optional]
 * @param  delimiter string[optional]
 * @param  enclosure string[optional]
 */
function fgetcsv($fp, $length = null, $delimiter = null, $enclosure = null) {}

/**
 * Get a line from file pointer 
 *
 * @return string
 * @param  fp resource
 * @param  length int
 */
function fgets($fp, $length) {}

/**
 * Get a line from file pointer and strip HTML tags 
 *
 * @return string
 * @param  fp resource
 * @param  length int[optional]
 * @param  allowable_tags string
 */
function fgetss($fp, $length = null, $allowable_tags) {}

/**
 * Read entire file into an array 
 *
 * @return array
 * @param  filename string
 * @param  flags int[optional]
 * @param  context resource
 */
function file($filename, $flags = null, $context) {}

/**
 * Returns true if filename exists 
 *
 * @return bool
 * @param  filename string
 */
function file_exists($filename) {}

/**
 * Read the entire file into a string 
 *
 * @return string
 * @param  filename string
 * @param  use_include_path bool[optional]
 * @param  context resource[optional]
 * @param  offset long[optional]
 * @param  maxlen long[optional]
 */
function file_get_contents($filename, $use_include_path = null, $context = null, $offset = null, $maxlen = null) {}

/**
 * Write/Create a file with contents data and return the number of bytes written 
 *
 * @return int
 * @param  file string
 * @param  data mixed
 * @param  flags int[optional]
 * @param  context resource[optional]
 */
function file_put_contents($file, $data, $flags = null, $context = null) {}

/**
 * Get last access time of file 
 *
 * @return int
 * @param  filename string
 */
function fileatime($filename) {}

/**
 * Get inode modification time of file 
 *
 * @return int
 * @param  filename string
 */
function filectime($filename) {}

/**
 * Get file group 
 *
 * @return int
 * @param  filename string
 */
function filegroup($filename) {}

/**
 * Get file inode 
 *
 * @return int
 * @param  filename string
 */
function fileinode($filename) {}

/**
 * Get last modification time of file 
 *
 * @return int
 * @param  filename string
 */
function filemtime($filename) {}

/**
 * Get file owner 
 *
 * @return int
 * @param  filename string
 */
function fileowner($filename) {}

/**
 * Get file permissions 
 *
 * @return int
 * @param  filename string
 */
function fileperms($filename) {}

/**
 * Read and verify the map file 
 *
 * @return bool
 * @param  directory string
 */
function filepro($directory) {}

/**
 * Find out how many fields are in a filePro database 
 *
 * @return int
 */
function filepro_fieldcount() {}

/**
 * Gets the name of a field 
 *
 * @return string
 * @param  fieldnumber int
 */
function filepro_fieldname($fieldnumber) {}

/**
 * Gets the type of a field 
 *
 * @return string
 * @param  field_number int
 */
function filepro_fieldtype($field_number) {}

/**
 * Gets the width of a field 
 *
 * @return int
 * @param  field_number int
 */
function filepro_fieldwidth($field_number) {}

/**
 * Retrieves data from a filePro database 
 *
 * @return string
 * @param  row_number int
 * @param  field_number int
 */
function filepro_retrieve($row_number, $field_number) {}

/**
 * Find out how many rows are in a filePro database 
 *
 * @return int
 */
function filepro_rowcount() {}

/**
 * Get file size 
 *
 * @return int
 * @param  filename string
 */
function filesize($filename) {}

/**
 * Get file type 
 *
 * @return string
 * @param  filename string
 */
function filetype($filename) {}

/**
 * Get the float value of a variable 
 *
 * @return float
 * @param  var mixed
 */
function floatval($var) {}

/**
 * Portable file locking 
 *
 * @return bool
 * @param  fp resource
 * @param  operation int
 * @param  wouldblock int[optional]
 */
function flock($fp, $operation, &$wouldblock) {}

/**
 * Returns the next lowest integer value from the number 
 *
 * @return float
 * @param  number float
 */
function floor($number) {}

/**
 * Flush the output buffer 
 *
 * @return void
 */
function flush() {}

/**
 * Returns the remainder of dividing x by y as a float 
 *
 * @return float
 * @param  x float
 * @param  y float
 */
function fmod($x, $y) {}

/**
 * Match filename against pattern 
 *
 * @return bool
 * @param  pattern string
 * @param  filename string
 * @param  flags int[optional]
 */
function fnmatch($pattern, $filename, $flags = null) {}

/**
 * Open a file or a URL and return a file pointer 
 *
 * @return resource
 * @param  filename string
 * @param  mode string
 * @param  use_include_path bool[optional]
 * @param  context resource[optional]
 */
function fopen($filename, $mode, $use_include_path = null, $context = null) {}

/**
 * Output all remaining data from a file pointer 
 *
 * @return int
 * @param  fp resource
 */
function fpassthru($fp) {}

/**
 * Output a formatted string into a stream 
 *
 * @return int
 * @param  stream resource
 * @param  format string
 * @param  arg1 mixed[optional]
 * @vararg ... mixed
 */
function fprintf($stream, $format, $arg1 = null) {}

/**
 * Format line as CSV and write to file pointer 
 *
 * @return int
 * @param  fp resource
 * @param  fields array
 * @param  delimiter string[optional]
 * @param  enclosure string[optional]
 */
function fputcsv($fp, $fields, $delimiter = null, $enclosure = null) {}

/**
 * Binary-safe file write 
 *
 * @return int
 * @param  fp resource
 * @param  str string
 * @param  length int[optional]
 */
function fputs($fp, $str, $length = null) {}

/**
 * Binary-safe file read 
 *
 * @return string
 * @param  fp resource
 * @param  length int
 */
function fread($fp, $length) {}

/**
 * Converts a french republic calendar date to julian day count 
 *
 * @return int
 * @param  month int
 * @param  day int
 * @param  year int
 */
function frenchtojd($month, $day, $year) {}

/**
 * Implements a mostly ANSI compatible fscanf() 
 *
 * @return mixed
 * @param  stream resource
 * @param  format string
 * @vararg ... string
 */
function fscanf($stream, $format) {}

/**
 * Seek on a file pointer 
 *
 * @return int
 * @param  fp resource
 * @param  offset int
 * @param  whence int[optional]
 */
function fseek($fp, $offset, $whence = null) {}

/**
 * Open Internet or Unix domain socket connection 
 *
 * @return resource
 * @param  hostname string
 * @param  port int
 * @param  errno int[optional]
 * @param  errstr string[optional]
 * @param  timeout float[optional]
 */
function fsockopen($hostname, $port, $errno = null, $errstr = null, $timeout = null) {}

/**
 * Stat() on a filehandle 
 *
 * @return array
 * @param  fp resource
 */
function fstat($fp) {}

/**
 * Get file pointer's read/write position 
 *
 * @return int
 * @param  fp resource
 */
function ftell($fp) {}

/**
 * Convert a pathname and a project identifier to a System V IPC key 
 *
 * @return int
 * @param  pathname string
 * @param  proj string
 */
function ftok($pathname, $proj) {}

/**
 * Attempt to allocate space on the remote FTP server 
 *
 * @return bool
 * @param  stream resource
 * @param  size int
 * @param  response unknown
 */
function ftp_alloc($stream, $size, &$response) {}

/**
 * Changes to the parent directory 
 *
 * @return bool
 * @param  stream resource
 */
function ftp_cdup($stream) {}

/**
 * Changes directories 
 *
 * @return bool
 * @param  stream resource
 * @param  directory string
 */
function ftp_chdir($stream, $directory) {}

/**
 * Sets permissions on a file 
 *
 * @return int
 * @param  stream resource
 * @param  mode int
 * @param  filename string
 */
function ftp_chmod($stream, $mode, $filename) {}

/**
 * Closes the FTP stream 
 *
 * @return bool
 * @param  stream resource
 */
function ftp_close($stream) {}

/**
 * Opens a FTP stream 
 *
 * @return resource
 * @param  host string
 * @param  port int[optional]
 * @param  timeout int[optional]
 */
function ftp_connect($host, $port = null, $timeout = null) {}

/**
 * Deletes a file 
 *
 * @return bool
 * @param  stream resource
 * @param  file string
 */
function ftp_delete($stream, $file) {}

/**
 * Requests execution of a program on the FTP server 
 *
 * @return bool
 * @param  stream resource
 * @param  command string
 */
function ftp_exec($stream, $command) {}

/**
 * Retrieves a file from the FTP server and writes it to an open file 
 *
 * @return bool
 * @param  stream resource
 * @param  fp resource
 * @param  remote_file string
 * @param  mode int
 * @param  resumepos int
 */
function ftp_fget($stream, $fp, $remote_file, $mode, $resumepos) {}

/**
 * Stores a file from an open file to the FTP server 
 *
 * @return bool
 * @param  stream resource
 * @param  remote_file string
 * @param  fp resource
 * @param  mode int
 * @param  startpos int
 */
function ftp_fput($stream, $remote_file, $fp, $mode, $startpos) {}

/**
 * Retrieves a file from the FTP server and writes it to a local file 
 *
 * @return bool
 * @param  stream resource
 * @param  local_file string
 * @param  remote_file string
 * @param  mode int
 * @param  resume_pos int
 */
function ftp_get($stream, $local_file, $remote_file, $mode, $resume_pos) {}

/**
 * Gets an FTP option 
 *
 * @return mixed
 * @param  stream resource
 * @param  option int
 */
function ftp_get_option($stream, $option) {}

/**
 * Logs into the FTP server 
 *
 * @return bool
 * @param  stream resource
 * @param  username string
 * @param  password string
 */
function ftp_login($stream, $username, $password) {}

/**
 * Returns the last modification time of the file, or -1 on error 
 *
 * @return int
 * @param  stream resource
 * @param  filename string
 */
function ftp_mdtm($stream, $filename) {}

/**
 * Creates a directory and returns the absolute path for the new directory or false on error 
 *
 * @return string
 * @param  stream resource
 * @param  directory string
 */
function ftp_mkdir($stream, $directory) {}

/**
 * Continues retrieving/sending a file nbronously 
 *
 * @return int
 * @param  stream resource
 */
function ftp_nb_continue($stream) {}

/**
 * Retrieves a file from the FTP server asynchronly and writes it to an open file 
 *
 * @return int
 * @param  stream resource
 * @param  fp resource
 * @param  remote_file string
 * @param  mode int
 * @param  resumepos int
 */
function ftp_nb_fget($stream, $fp, $remote_file, $mode, $resumepos) {}

/**
 * Stores a file from an open file to the FTP server nbronly 
 *
 * @return int
 * @param  stream resource
 * @param  remote_file string
 * @param  fp resource
 * @param  mode int
 * @param  startpos int
 */
function ftp_nb_fput($stream, $remote_file, $fp, $mode, $startpos) {}

/**
 * Retrieves a file from the FTP server nbhronly and writes it to a local file 
 *
 * @return int
 * @param  stream resource
 * @param  local_file string
 * @param  remote_file string
 * @param  mode int
 * @param  resume_pos int
 */
function ftp_nb_get($stream, $local_file, $remote_file, $mode, $resume_pos) {}

/**
 * Stores a file on the FTP server 
 *
 * @return int
 * @param  stream resource
 * @param  remote_file string
 * @param  local_file string
 * @param  mode int
 * @param  startpos int
 */
function ftp_nb_put($stream, $remote_file, $local_file, $mode, $startpos) {}

/**
 * Returns an array of filenames in the given directory 
 *
 * @return array
 * @param  stream resource
 * @param  directory string
 */
function ftp_nlist($stream, $directory) {}

/**
 * Turns passive mode on or off 
 *
 * @return bool
 * @param  stream resource
 * @param  pasv bool
 */
function ftp_pasv($stream, $pasv) {}

/**
 * Stores a file on the FTP server 
 *
 * @return bool
 * @param  stream resource
 * @param  remote_file string
 * @param  local_file string
 * @param  mode int
 * @param  startpos int
 */
function ftp_put($stream, $remote_file, $local_file, $mode, $startpos) {}

/**
 * Returns the present working directory 
 *
 * @return string
 * @param  stream resource
 */
function ftp_pwd($stream) {}

/**
 * Closes the FTP stream 
 *
 * @return bool
 * @param  stream resource
 */
function ftp_quit($stream) {}

/**
 * Sends a literal command to the FTP server 
 *
 * @return array
 * @param  stream resource
 * @param  command string
 */
function ftp_raw($stream, $command) {}

/**
 * Returns a detailed listing of a directory as an array of output lines 
 *
 * @return array
 * @param  stream resource
 * @param  directory string
 * @param  recursive bool[optional]
 */
function ftp_rawlist($stream, $directory, $recursive = null) {}

/**
 * Renames the given file to a new path 
 *
 * @return bool
 * @param  stream resource
 * @param  src string
 * @param  dest string
 */
function ftp_rename($stream, $src, $dest) {}

/**
 * Removes a directory 
 *
 * @return bool
 * @param  stream resource
 * @param  directory string
 */
function ftp_rmdir($stream, $directory) {}

/**
 * Sets an FTP option 
 *
 * @return bool
 * @param  stream resource
 * @param  option int
 * @param  value mixed
 */
function ftp_set_option($stream, $option, $value) {}

/**
 * Sends a SITE command to the server 
 *
 * @return bool
 * @param  stream resource
 * @param  cmd string
 */
function ftp_site($stream, $cmd) {}

/**
 * Returns the size of the file, or -1 on error 
 *
 * @return int
 * @param  stream resource
 * @param  filename string
 */
function ftp_size($stream, $filename) {}

/**
 * Opens a FTP-SSL stream 
 *
 * @return resource
 * @param  host string
 * @param  port int[optional]
 * @param  timeout int[optional]
 */
function ftp_ssl_connect($host, $port = null, $timeout = null) {}

/**
 * Returns the system type identifier 
 *
 * @return string
 * @param  stream resource
 */
function ftp_systype($stream) {}

/**
 * Truncate file to 'size' length 
 *
 * @return bool
 * @param  fp resource
 * @param  size int
 */
function ftruncate($fp, $size) {}

/**
 * Get the $arg_num'th argument that was passed to the function 
 *
 * @return mixed
 * @param  arg_num int
 */
function func_get_arg($arg_num) {}

/**
 * Get an array of the arguments that were passed to the function 
 *
 * @return array
 */
function func_get_args() {}

/**
 * Get the number of arguments that were passed to the function 
 *
 * @return int
 */
function func_num_args() {}

/**
 * Checks if the function exists 
 *
 * @return bool
 * @param  function_name string
 */
function function_exists($function_name) {}

/**
 * Binary-safe file write 
 *
 * @return int
 * @param  fp resource
 * @param  str string
 * @param  length int[optional]
 */
function fwrite($fp, $str, $length = null) {}

/**
 * 
 *
 * @return array
 */
function gd_info() {}

/**
 * 
 *
 * @return DOMNode
 * @param  name string
 */
function getNamedItem($name) {}

/**
 * 
 *
 * @return DOMNode
 * @param  namespaceURI string
 * @param  localName string
 */
function getNamedItemNS($namespaceURI, $localName) {}

/**
 * 
 *
 * @return mixed
 * @param  browser_name string[optional]
 * @param  return_array bool[optional]
 */
function get_browser($browser_name = null, $return_array = null) {}

/**
 * Obtain the function call stack trace 
 *
 * @return array
 */
function get_call_stack() {}

/**
 * Get the value of a PHP configuration option 
 *
 * @return string
 * @param  option_name string
 */
function get_cfg_var($option_name) {}

/**
 * Retrieves the class name 
 *
 * @return string
 * @param  object object[optional]
 */
function get_class($object = null) {}

/**
 * Returns an array of method names for class or class instance. 
 *
 * @return array
 * @param  class mixed
 */
function get_class_methods($class) {}

/**
 * Returns an array of default properties of the class. 
 *
 * @return array
 * @param  class_name string
 */
function get_class_vars($class_name) {}

/**
 * Get the name of the owner of the current PHP script 
 *
 * @return string
 */
function get_current_user() {}

/**
 * Returns an array of all declared classes. 
 *
 * @return array
 */
function get_declared_classes() {}

/**
 * Returns an array of all declared interfaces. 
 *
 * @return array
 */
function get_declared_interfaces() {}

/**
 * Return an array containing the names and values of all defined constants 
 *
 * @return array
 */
function get_defined_constants() {}

/**
 * Returns an array of all defined functions 
 *
 * @return array
 */
function get_defined_functions() {}

/**
 * Returns an associative array of names and values of all currently defined variable names (variables in the current scope) 
 *
 * @return array
 */
function get_defined_vars() {}

/**
 * Returns an array with the names of functions belonging to the named extension 
 *
 * @return array
 * @param  extension_name string
 */
function get_extension_funcs($extension_name) {}

/**
 * fetches all the headers sent by the server in response to a HTTP request 
 *
 * @return array
 * @param  url string
 */
function get_headers($url) {}

/**
 * Returns the internal translation table used by htmlspecialchars and htmlentities 
 *
 * @return array
 * @param  table int[optional]
 * @param  quote_style int[optional]
 */
function get_html_translation_table($table = null, $quote_style = null) {}

/**
 * Get the current include_path configuration option 
 *
 * @return string
 */
function get_include_path() {}

/**
 * Returns an array with the file names that were include_once()'d 
 *
 * @return array
 */
function get_included_files() {}

/**
 * Return an array containing names of loaded extensions 
 *
 * @return array
 */
function get_loaded_extensions() {}

/**
 * Get the current active configuration setting of magic_quotes_gpc 
 *
 * @return int
 */
function get_magic_quotes_gpc() {}

/**
 * Get the current active configuration setting of magic_quotes_runtime 
 *
 * @return int
 */
function get_magic_quotes_runtime() {}

/**
 * Extracts all meta tag content attributes from a file and returns an array 
 *
 * @return array
 * @param  filename string
 * @param  use_include_path bool[optional]
 */
function get_meta_tags($filename, $use_include_path = null) {}

/**
 * Returns an array of object properties 
 *
 * @return array
 * @param  obj object
 */
function get_object_vars($obj) {}

/**
 * Retrieves the parent class name for object or class or current scope. 
 *
 * @return string
 * @param  object mixed[optional]
 */
function get_parent_class($object = null) {}

/**
 * Returns an array with the file names that were include_once()'d 
 *
 * @return array
 */
function get_required_files() {}

/**
 * Get the resource type name for a given resource 
 *
 * @return string
 * @param  res resource
 */
function get_resource_type($res) {}

/**
 * Get all headers from the request 
 *
 * @return array
 */
function getallheaders() {}

/**
 * Gets the current directory 
 *
 * @return string
 */
function getcwd() {}

/**
 * Get date/time information 
 *
 * @return array
 * @param  timestamp int[optional]
 */
function getdate($timestamp = null) {}

/**
 * Get the value of an environment variable 
 *
 * @return string
 * @param  varname string
 */
function getenv($varname) {}

/**
 * Get the Internet host name corresponding to a given IP address 
 *
 * @return string
 * @param  ip_address string
 */
function gethostbyaddr($ip_address) {}

/**
 * Get the IP address corresponding to a given Internet host name 
 *
 * @return string
 * @param  hostname string
 */
function gethostbyname($hostname) {}

/**
 * Return a list of IP addresses that a given hostname resolves to. 
 *
 * @return array
 * @param  hostname string
 */
function gethostbynamel($hostname) {}

/**
 * Get the size of an image as 4-element array 
 *
 * @return array
 * @param  imagefile string
 * @param  info array[optional]
 */
function getimagesize($imagefile, $info = null) {}

/**
 * Get time of last page modification 
 *
 * @return int
 */
function getlastmod() {}

/**
 * Get MX records corresponding to a given Internet host name 
 *
 * @return bool
 * @param  hostname string
 * @param  mxhosts array
 * @param  weight array[optional]
 */
function getmxrr($hostname, $mxhosts, $weight = null) {}

/**
 * Get PHP script owner's GID 
 *
 * @return int
 */
function getmygid() {}

/**
 * Get the inode of the current script being parsed 
 *
 * @return int
 */
function getmyinode() {}

/**
 * Get current process ID 
 *
 * @return int
 */
function getmypid() {}

/**
 * Get PHP script owner's UID 
 *
 * @return int
 */
function getmyuid() {}

/**
 * Get options from the command line argument list 
 *
 * @return array
 * @param  options string
 * @param  longopts array[optional]
 */
function getopt($options, $longopts = null) {}

/**
 * Returns protocol number associated with name as per /etc/protocols 
 *
 * @return int
 * @param  name string
 */
function getprotobyname($name) {}

/**
 * Returns protocol name associated with protocol number proto 
 *
 * @return string
 * @param  proto int
 */
function getprotobynumber($proto) {}

/**
 * Returns the maximum value a random number can have 
 *
 * @return int
 */
function getrandmax() {}

/**
 * Returns an array of usage statistics 
 *
 * @return array
 * @param  who int[optional]
 */
function getrusage($who = null) {}

/**
 * Returns port associated with service. Protocol must be "tcp" or "udp" 
 *
 * @return int
 * @param  service string
 * @param  protocol string
 */
function getservbyname($service, $protocol) {}

/**
 * Returns service name associated with port. Protocol must be "tcp" or "udp" 
 *
 * @return string
 * @param  port int
 * @param  protocol string
 */
function getservbyport($port, $protocol) {}

/**
 * Return the translation of msgid for the current domain, or msgid unaltered if a translation does not exist 
 *
 * @return string
 * @param  msgid string
 */
function gettext($msgid) {}

/**
 * Returns the current time as array 
 *
 * @return mixed
 * @param  get_as_float bool[optional]
 */
function gettimeofday($get_as_float = null) {}

/**
 * Returns the type of the variable 
 *
 * @return string
 * @param  var mixed
 */
function gettype($var) {}

/**
 * Find pathnames matching a pattern 
 *
 * @return array
 * @param  pattern string
 * @param  flags int[optional]
 */
function glob($pattern, $flags = null) {}

/**
 * Format a GMT date/time 
 *
 * @return string
 * @param  format string
 * @param  timestamp long[optional]
 */
function gmdate($format, $timestamp = null) {}

/**
 * Get UNIX timestamp for a GMT date 
 *
 * @return int
 * @param  hour int
 * @param  min int
 * @param  sec int
 * @param  mon int
 * @param  day int
 * @param  year int
 */
function gmmktime($hour, $min, $sec, $mon, $day, $year) {}

/**
 * Calculates absolute value 
 *
 * @return resource
 * @param  a resource
 */
function gmp_abs($a) {}

/**
 * Add a and b 
 *
 * @return resource
 * @param  a resource
 * @param  b resource
 */
function gmp_add($a, $b) {}

/**
 * Calculates logical AND of a and b 
 *
 * @return resource
 * @param  a resource
 * @param  b resource
 */
function gmp_and($a, $b) {}

/**
 * Clears bit in a 
 *
 * @return void
 * @param  a resource
 * @param  index int
 */
function gmp_clrbit(&$a, $index) {}

/**
 * Compares two numbers 
 *
 * @return int
 * @param  a resource
 * @param  b resource
 */
function gmp_cmp($a, $b) {}

/**
 * Calculates one's complement of a 
 *
 * @return resource
 * @param  a resource
 */
function gmp_com($a) {}

/**
 * Divide a by b, returns quotient only 
 *
 * @return resource
 * @param  a resource
 * @param  b resource
 * @param  round int[optional]
 */
function gmp_div($a, $b, $round = null) {}

/**
 * Divide a by b, returns quotient only 
 *
 * @return resource
 * @param  a resource
 * @param  b resource
 * @param  round int[optional]
 */
function gmp_div_q($a, $b, $round = null) {}

/**
 * Divide a by b, returns quotient and reminder 
 *
 * @return array
 * @param  a resource
 * @param  b resource
 * @param  round int[optional]
 */
function gmp_div_qr($a, $b, $round = null) {}

/**
 * Divide a by b, returns reminder only 
 *
 * @return resource
 * @param  a resource
 * @param  b resource
 * @param  round int[optional]
 */
function gmp_div_r($a, $b, $round = null) {}

/**
 * Divide a by b using exact division algorithm 
 *
 * @return resource
 * @param  a resource
 * @param  b resource
 */
function gmp_divexact($a, $b) {}

/**
 * Calculates factorial function 
 *
 * @return resource
 * @param  a int
 */
function gmp_fact($a) {}

/**
 * Computes greatest common denominator (gcd) of a and b 
 *
 * @return resource
 * @param  a resource
 * @param  b resource
 */
function gmp_gcd($a, $b) {}

/**
 * Computes G, S, and T, such that AS + BT = G = `gcd' (A, B) 
 *
 * @return array
 * @param  a resource
 * @param  b resource
 */
function gmp_gcdext($a, $b) {}

/**
 * Calculates hamming distance between a and b 
 *
 * @return int
 * @param  a resource
 * @param  b resource
 */
function gmp_hamdist($a, $b) {}

/**
 * Initializes GMP number 
 *
 * @return resource
 * @param  number mixed
 * @param  base int[optional]
 */
function gmp_init($number, $base = null) {}

/**
 * Gets signed long value of GMP number 
 *
 * @return int
 * @param  gmpnumber resource
 */
function gmp_intval($gmpnumber) {}

/**
 * Computes the inverse of a modulo b 
 *
 * @return resource
 * @param  a resource
 * @param  b resource
 */
function gmp_invert($a, $b) {}

/**
 * Computes Jacobi symbol 
 *
 * @return int
 * @param  a resource
 * @param  b resource
 */
function gmp_jacobi($a, $b) {}

/**
 * Computes Legendre symbol 
 *
 * @return int
 * @param  a resource
 * @param  b resource
 */
function gmp_legendre($a, $b) {}

/**
 * Computes a modulo b 
 *
 * @return resource
 * @param  a resource
 * @param  b resource
 */
function gmp_mod($a, $b) {}

/**
 * Multiply a and b 
 *
 * @return resource
 * @param  a resource
 * @param  b resource
 */
function gmp_mul($a, $b) {}

/**
 * Negates a number 
 *
 * @return resource
 * @param  a resource
 */
function gmp_neg($a) {}

/**
 * Calculates logical OR of a and b 
 *
 * @return resource
 * @param  a resource
 * @param  b resource
 */
function gmp_or($a, $b) {}

/**
 * Checks if a is an exact square 
 *
 * @return bool
 * @param  a resource
 */
function gmp_perfect_square($a) {}

/**
 * Calculates the population count of a 
 *
 * @return int
 * @param  a resource
 */
function gmp_popcount($a) {}

/**
 * Raise base to power exp 
 *
 * @return resource
 * @param  base resource
 * @param  exp int
 */
function gmp_pow($base, $exp) {}

/**
 * Raise base to power exp and take result modulo mod 
 *
 * @return resource
 * @param  base resource
 * @param  exp resource
 * @param  mod resource
 */
function gmp_powm($base, $exp, $mod) {}

/**
 * Checks if a is "probably prime" 
 *
 * @return int
 * @param  a resource
 * @param  reps int
 */
function gmp_prob_prime($a, $reps) {}

/**
 * Gets random number 
 *
 * @return resource
 * @param  limiter int[optional]
 */
function gmp_random($limiter = null) {}

/**
 * Finds first zero bit 
 *
 * @return int
 * @param  a resource
 * @param  start int
 */
function gmp_scan0($a, $start) {}

/**
 * Finds first non-zero bit 
 *
 * @return int
 * @param  a resource
 * @param  start int
 */
function gmp_scan1($a, $start) {}

/**
 * Sets or clear bit in a 
 *
 * @return void
 * @param  a resource
 * @param  index int
 * @param  set_clear bool
 */
function gmp_setbit(&$a, $index, $set_clear) {}

/**
 * Gets the sign of the number 
 *
 * @return int
 * @param  a resource
 */
function gmp_sign($a) {}

/**
 * Takes integer part of square root of a 
 *
 * @return resource
 * @param  a resource
 */
function gmp_sqrt($a) {}

/**
 * Square root with remainder 
 *
 * @return array
 * @param  a resource
 */
function gmp_sqrtrem($a) {}

/**
 * Gets string representation of GMP number  
 *
 * @return string
 * @param  gmpnumber resource
 * @param  base int[optional]
 */
function gmp_strval($gmpnumber, $base = null) {}

/**
 * Subtract b from a 
 *
 * @return resource
 * @param  a resource
 * @param  b resource
 */
function gmp_sub($a, $b) {}

/**
 * Calculates logical exclusive OR of a and b 
 *
 * @return resource
 * @param  a resource
 * @param  b resource
 */
function gmp_xor($a, $b) {}

/**
 * Format a GMT/UCT time/date according to locale settings 
 *
 * @return string
 * @param  format string
 * @param  timestamp int[optional]
 */
function gmstrftime($format, $timestamp = null) {}

/**
 * Converts a gregorian calendar date to julian day count 
 *
 * @return int
 * @param  month int
 * @param  day int
 * @param  year int
 */
function gregoriantojd($month, $day, $year) {}

/**
 * Close an open file pointer 
 *
 * @return bool
 * @param  fp resource
 */
function gzclose($fp) {}

/**
 * Gzip-compress a string 
 *
 * @return string
 * @param  data string
 * @param  level int[optional]
 */
function gzcompress($data, $level = null) {}

/**
 * Gzip-compress a string 
 *
 * @return string
 * @param  data string
 * @param  level int[optional]
 */
function gzdeflate($data, $level = null) {}

/**
 * GZ encode a string 
 *
 * @return string
 * @param  data string
 * @param  level int[optional]
 * @param  encoding_mode int[optional]
 */
function gzencode($data, $level = null, $encoding_mode = null) {}

/**
 * Test for end-of-file on a file pointer 
 *
 * @return bool
 * @param  fp resource
 */
function gzeof($fp) {}

/**
 * Read und uncompress entire .gz-file into an array 
 *
 * @return array
 * @param  filename string
 * @param  use_include_path int[optional]
 */
function gzfile($filename, $use_include_path = null) {}

/**
 * Get a character from file pointer 
 *
 * @return string
 * @param  fp resource
 */
function gzgetc($fp) {}

/**
 * Get a line from file pointer 
 *
 * @return string
 * @param  fp resource
 * @param  length int
 */
function gzgets($fp, $length) {}

/**
 * Get a line from file pointer and strip HTML tags 
 *
 * @return string
 * @param  fp resource
 * @param  length int[optional]
 * @param  allowable_tags string
 */
function gzgetss($fp, $length = null, $allowable_tags) {}

/**
 * Unzip a gzip-compressed string 
 *
 * @return string
 * @param  data string
 * @param  length int[optional]
 */
function gzinflate($data, $length = null) {}

/**
 * Open a .gz-file and return a .gz-file pointer 
 *
 * @return resource
 * @param  filename string
 * @param  mode string
 * @param  use_include_path int[optional]
 */
function gzopen($filename, $mode, $use_include_path = null) {}

/**
 * Output all remaining data from a file pointer 
 *
 * @return int
 * @param  fp resource
 */
function gzpassthru($fp) {}

/**
 * Binary-safe file write 
 *
 * @return int
 * @param  fp resource
 * @param  str string
 * @param  length int[optional]
 */
function gzputs($fp, $str, $length = null) {}

/**
 * Binary-safe file read 
 *
 * @return string
 * @param  fp resource
 * @param  length int
 */
function gzread($fp, $length) {}

/**
 * Rewind the position of a file pointer 
 *
 * @return bool
 * @param  fp resource
 */
function gzrewind($fp) {}

/**
 * Seek on a file pointer 
 *
 * @return int
 * @param  fp resource
 * @param  offset int
 * @param  whence int[optional]
 */
function gzseek($fp, $offset, $whence = null) {}

/**
 * Get file pointer's read/write position 
 *
 * @return int
 * @param  fp resource
 */
function gztell($fp) {}

/**
 * Unzip a gzip-compressed string 
 *
 * @return string
 * @param  data string
 * @param  length int[optional]
 */
function gzuncompress($data, $length = null) {}

/**
 * Binary-safe file write 
 *
 * @return int
 * @param  fp resource
 * @param  str string
 * @param  length int[optional]
 */
function gzwrite($fp, $str, $length = null) {}

/**
 * 
 *
 * @return string
 * @param  algo string
 * @param  data string
 * @param  raw_output bool[optional]
 */
function hash($algo, $data, $raw_output = false) {}

/**
 * Return a list of registered hashing algorithms 
 *
 * @return array
 */
function hash_algos() {}

/**
 * 
 *
 * @return string
 * @param  algo string
 * @param  filename string
 * @param  raw_output bool[optional]
 */
function hash_file($algo, $filename, $raw_output = false) {}

/**
 * Output resulting digest 
 *
 * @return string
 * @param  context resource
 * @param  raw_output bool[optional]
 */
function hash_final($context, $raw_output = false) {}

/**
 * 
 *
 * @return string
 * @param  algo string
 * @param  data string
 * @param  key string
 * @param  raw_output bool[optional]
 */
function hash_hmac($algo, $data, $key, $raw_output = false) {}

/**
 * 
 *
 * @return string
 * @param  algo string
 * @param  filename string
 * @param  key string
 * @param  raw_output bool[optional]
 */
function hash_hmac_file($algo, $filename, $key, $raw_output = false) {}

/**
 * Initialize a hashing context 
 *
 * @return resource
 * @param  algo string
 * @param  options int
 * @param  key string
 */
function hash_init($algo, $options, $key) {}

/**
 * Pump data into the hashing algorithm 
 *
 * @return bool
 * @param  context resource
 * @param  data string
 */
function hash_update($context, $data) {}

/**
 * Pump data into the hashing algorithm from a file 
 *
 * @return bool
 * @param  context resource
 * @param  filename string
 * @param  context resource
 */
function hash_update_file($context, $filename, $context) {}

/**
 * Pump data into the hashing algorithm from an open stream 
 *
 * @return int
 * @param  context resource
 * @param  handle resource
 * @param  length integer
 */
function hash_update_stream($context, $handle, $length) {}

/**
 * Sends a raw HTTP header 
 *
 * @return void
 * @param  header string
 * @param  replace bool[optional]
 * @param  http_response_code int[optional]
 */
function header($header, $replace = null, $http_response_code = null) {}

/**
 * Return list of headers to be sent / already sent 
 *
 * @return array
 */
function headers_list() {}

/**
 * Returns true if headers have already been sent, false otherwise 
 *
 * @return bool
 * @param  file string[optional]
 * @param  line int[optional]
 */
function headers_sent(&$file, &$line) {}

/**
 * Converts logical Hebrew text to visual text 
 *
 * @return string
 * @param  str string
 * @param  max_chars_per_line int[optional]
 */
function hebrev($str, $max_chars_per_line = null) {}

/**
 * Converts logical Hebrew text to visual text with newline conversion 
 *
 * @return string
 * @param  str string
 * @param  max_chars_per_line int[optional]
 */
function hebrevc($str, $max_chars_per_line = null) {}

/**
 * Returns the decimal equivalent of the hexadecimal number 
 *
 * @return number
 * @param  hexadecimal_number string
 */
function hexdec($hexadecimal_number) {}

/**
 * Syntax highlight a source file 
 *
 * @return int
 * @param  file_name string
 * @param  return bool[optional]
 */
function highlight_file($file_name, $return = null) {}

/**
 * Syntax highlight a string or optionally return it 
 *
 * @return int
 * @param  string string
 * @param  return bool[optional]
 */
function highlight_string($string, $return = null) {}

/**
 * Convert all HTML entities to their applicable characters 
 *
 * @return string
 * @param  string string
 * @param  quote_style int[optional]
 * @param  charset string[optional]
 */
function html_entity_decode($string, $quote_style = null, $charset = null) {}

/**
 * Convert all applicable characters to HTML entities 
 *
 * @return string
 * @param  string string
 * @param  quote_style int[optional]
 * @param  charset string[optional]
 */
function htmlentities($string, $quote_style = null, $charset = null) {}

/**
 * Convert special HTML entities back to characters 
 *
 * @return string
 * @param  string string
 * @param  quote_style int[optional]
 */
function htmlspecialchars($string, $quote_style = null) {}

/**
 * Generates a form-encoded query string from an associative array or object. 
 *
 * @return string
 * @param  formdata mixed
 * @param  prefix string[optional]
 * @param  arg_separator string[optional]
 */
function http_build_query($formdata, $prefix = null, $arg_separator = null) {}

/**
 * Returns sqrt(num1*num1 + num2*num2) 
 *
 * @return float
 * @param  num1 float
 * @param  num2 float
 */
function hypot($num1, $num2) {}

/**
 * Add a user to security database 
 *
 * @return bool
 * @param  service_handle resource
 * @param  user_name string
 * @param  password string
 * @param  first_name string[optional]
 * @param  middle_name string[optional]
 * @param  last_name string[optional]
 */
function ibase_add_user($service_handle, $user_name, $password, $first_name = null, $middle_name = null, $last_name = null) {}

/**
 * Returns the number of rows affected by the previous INSERT, UPDATE or DELETE statement 
 *
 * @return int
 * @param  link_identifier resource[optional]
 */
function ibase_affected_rows($link_identifier = null) {}

/**
 * Initiates a backup task in the service manager and returns immediately 
 *
 * @return mixed
 * @param  service_handle resource
 * @param  source_db string
 * @param  dest_file string
 * @param  options int[optional]
 * @param  verbose bool[optional]
 */
function ibase_backup($service_handle, $source_db, $dest_file, $options = null, $verbose = null) {}

/**
 * Add data into created blob 
 *
 * @return void
 * @param  blob_handle resource
 * @param  data string
 */
function ibase_blob_add($blob_handle, $data) {}

/**
 * Cancel creating blob 
 *
 * @return bool
 * @param  blob_handle resource
 */
function ibase_blob_cancel($blob_handle) {}

/**
 * Close blob 
 *
 * @return mixed
 * @param  blob_handle resource
 */
function ibase_blob_close($blob_handle) {}

/**
 * Create blob for adding data 
 *
 * @return resource
 * @param  link_identifier resource[optional]
 */
function ibase_blob_create($link_identifier = null) {}

/**
 * Output blob contents to browser 
 *
 * @return bool
 * @param  link_identifier resource[optional]
 * @param  blob_id string
 */
function ibase_blob_echo($link_identifier = null, $blob_id) {}

/**
 * Get len bytes data from open blob 
 *
 * @return string
 * @param  blob_handle resource
 * @param  len int
 */
function ibase_blob_get($blob_handle, $len) {}

/**
 * Create blob, copy file in it, and close it 
 *
 * @return string
 * @param  link_identifier resource[optional]
 * @param  file resource
 */
function ibase_blob_import($link_identifier = null, $file) {}

/**
 * Return blob length and other useful info 
 *
 * @return array
 * @param  link_identifier resource[optional]
 * @param  blob_id string
 */
function ibase_blob_info($link_identifier = null, $blob_id) {}

/**
 * Open blob for retrieving data parts 
 *
 * @return resource
 * @param  link_identifier resource[optional]
 * @param  blob_id string
 */
function ibase_blob_open($link_identifier = null, $blob_id) {}

/**
 * Close an InterBase connection 
 *
 * @return bool
 * @param  link_identifier resource[optional]
 */
function ibase_close($link_identifier = null) {}

/**
 * Commit transaction 
 *
 * @return bool
 * @param  link_identifier resource
 */
function ibase_commit($link_identifier) {}

/**
 * Commit transaction and retain the transaction context 
 *
 * @return bool
 * @param  link_identifier resource
 */
function ibase_commit_ret($link_identifier) {}

/**
 * Open a connection to an InterBase database 
 *
 * @return resource
 * @param  database string
 * @param  username string[optional]
 * @param  password string[optional]
 * @param  charset string[optional]
 * @param  buffers int[optional]
 * @param  dialect int[optional]
 * @param  role string[optional]
 */
function ibase_connect($database, $username = null, $password = null, $charset = null, $buffers = null, $dialect = null, $role = null) {}

/**
 * Request statistics about a database 
 *
 * @return string
 * @param  service_handle resource
 * @param  db string
 * @param  action int
 * @param  argument int[optional]
 */
function ibase_db_info($service_handle, $db, $action, $argument = null) {}

/**
 * Delete a user from security database 
 *
 * @return bool
 * @param  service_handle resource
 * @param  user_name string
 * @param  password string
 * @param  first_name string[optional]
 * @param  middle_name string[optional]
 * @param  last_name string[optional]
 */
function ibase_delete_user($service_handle, $user_name, $password, $first_name = null, $middle_name = null, $last_name = null) {}

/**
 * Drop an InterBase database 
 *
 * @return bool
 * @param  link_identifier resource[optional]
 */
function ibase_drop_db($link_identifier = null) {}

/**
 * Return error code 
 *
 * @return int
 */
function ibase_errcode() {}

/**
 * Return error message 
 *
 * @return string
 */
function ibase_errmsg() {}

/**
 * Execute a previously prepared query 
 *
 * @return resource
 * @param  query resource
 * @param  bind_arg mixed[optional]
 * @param  bind_arg mixed[optional]
 * @vararg ...
 */
function ibase_execute($query, $bind_arg = null, $bind_arg = null) {}

/**
 * Fetch a row  from the results of a query 
 *
 * @return array
 * @param  result resource
 * @param  fetch_flags int[optional]
 */
function ibase_fetch_assoc($result, $fetch_flags = null) {}

/**
 * Fetch a object from the results of a query 
 *
 * @return object
 * @param  result resource
 * @param  fetch_flags int[optional]
 */
function ibase_fetch_object($result, $fetch_flags = null) {}

/**
 * Fetch a row  from the results of a query 
 *
 * @return array
 * @param  result resource
 * @param  fetch_flags int[optional]
 */
function ibase_fetch_row($result, $fetch_flags = null) {}

/**
 * Get information about a field 
 *
 * @return array
 * @param  query_result resource
 * @param  field_number int
 */
function ibase_field_info($query_result, $field_number) {}

/**
 * Frees the event handler set by ibase_set_event_handler() 
 *
 * @return bool
 * @param  event resource
 */
function ibase_free_event_handler($event) {}

/**
 * Free memory used by a query 
 *
 * @return bool
 * @param  query resource
 */
function ibase_free_query($query) {}

/**
 * Free the memory used by a result 
 *
 * @return bool
 * @param  result resource
 */
function ibase_free_result($result) {}

/**
 * Increments the named generator and returns its new value 
 *
 * @return mixed
 * @param  generator string
 * @param  increment int[optional]
 * @param  link_identifier resource[optional]
 */
function ibase_gen_id($generator, $increment = null, $link_identifier = null) {}

/**
 * Execute a maintenance command on the database server 
 *
 * @return bool
 * @param  service_handle resource
 * @param  db string
 * @param  action int
 * @param  argument int[optional]
 */
function ibase_maintain_db($service_handle, $db, $action, $argument = null) {}

/**
 * Modify a user in security database 
 *
 * @return bool
 * @param  service_handle resource
 * @param  user_name string
 * @param  password string
 * @param  first_name string[optional]
 * @param  middle_name string[optional]
 * @param  last_name string[optional]
 */
function ibase_modify_user($service_handle, $user_name, $password, $first_name = null, $middle_name = null, $last_name = null) {}

/**
 * Assign a name to a result for use with ... WHERE CURRENT OF <name> statements 
 *
 * @return bool
 * @param  result resource
 * @param  name string
 */
function ibase_name_result($result, $name) {}

/**
 * Get the number of fields in result 
 *
 * @return int
 * @param  query_result resource
 */
function ibase_num_fields($query_result) {}

/**
 * Get the number of params in a prepared query 
 *
 * @return int
 * @param  query resource
 */
function ibase_num_params($query) {}

/**
 * Return the number of rows that are available in a result 
 *
 * @return int
 * @param  result_identifier resource
 */
function ibase_num_rows($result_identifier) {}

/**
 * Get information about a parameter 
 *
 * @return array
 * @param  query resource
 * @param  field_number int
 */
function ibase_param_info($query, $field_number) {}

/**
 * Open a persistent connection to an InterBase database 
 *
 * @return resource
 * @param  database string
 * @param  username string[optional]
 * @param  password string[optional]
 * @param  charset string[optional]
 * @param  buffers int[optional]
 * @param  dialect int[optional]
 * @param  role string[optional]
 */
function ibase_pconnect($database, $username = null, $password = null, $charset = null, $buffers = null, $dialect = null, $role = null) {}

/**
 * Prepare a query for later execution 
 *
 * @return resource
 * @param  link_identifier resource[optional]
 * @param  query string
 */
function ibase_prepare($link_identifier = null, $query) {}

/**
 * Execute a query 
 *
 * @return resource
 * @param  link_identifier resource[optional]
 * @param  link_identifier resource[optional]
 * @param  query string
 * @param  bind_arg mixed[optional]
 * @param  bind_arg mixed[optional]
 * @vararg ...
 */
function ibase_query($link_identifier = null, $link_identifier = null, $query, $bind_arg = null, $bind_arg = null) {}

/**
 * Initiates a restore task in the service manager and returns immediately 
 *
 * @return mixed
 * @param  service_handle resource
 * @param  source_file string
 * @param  dest_db string
 * @param  options int[optional]
 * @param  verbose bool[optional]
 */
function ibase_restore($service_handle, $source_file, $dest_db, $options = null, $verbose = null) {}

/**
 * Rollback transaction 
 *
 * @return bool
 * @param  link_identifier resource
 */
function ibase_rollback($link_identifier) {}

/**
 * Rollback transaction and retain the transaction context 
 *
 * @return bool
 * @param  link_identifier resource
 */
function ibase_rollback_ret($link_identifier) {}

/**
 * Request information about a database server 
 *
 * @return string
 * @param  service_handle resource
 * @param  action int
 */
function ibase_server_info($service_handle, $action) {}

/**
 * Connect to the service manager 
 *
 * @return resource
 * @param  host string
 * @param  dba_username string
 * @param  dba_password string
 */
function ibase_service_attach($host, $dba_username, $dba_password) {}

/**
 * Disconnect from the service manager 
 *
 * @return bool
 * @param  service_handle resource
 */
function ibase_service_detach($service_handle) {}

/**
 * Register the callback for handling each of the named events 
 *
 * @return resource
 * @param  link_identifier resource[optional]
 * @param  handler callback
 * @param  event string
 * @param  event string[optional]
 * @vararg ...
 */
function ibase_set_event_handler($link_identifier = null, $handler, $event, $event = null) {}

/**
 * Start a transaction over one or several databases 
 *
 * @return resource
 * @param  trans_args int[optional]
 * @param  link_identifier resource[optional]
 * @vararg ...
 * @param  trans_args int
 * @param  link_identifier resource[optional]
 * @vararg ...
 * @vararg ...
 */
function ibase_trans($trans_args = null, $link_identifier = null, $trans_args, $link_identifier = null) {}

/**
 * Waits for any one of the passed Interbase events to be posted by the database, and returns its name 
 *
 * @return string
 * @param  link_identifier resource[optional]
 * @param  event string
 * @param  event string[optional]
 * @vararg ...
 */
function ibase_wait_event($link_identifier = null, $event, $event = null) {}

/**
 * Returns str converted to the out_charset character set 
 *
 * @return string
 * @param  in_charset string
 * @param  out_charset string
 * @param  str string
 */
function iconv($in_charset, $out_charset, $str) {}

/**
 * Get internal encoding and output encoding for ob_iconv_handler() 
 *
 * @return mixed
 * @param  type string[optional]
 */
function iconv_get_encoding($type = null) {}

/**
 * Decodes a mime header field 
 *
 * @return string
 * @param  encoded_string string
 * @param  mode int[optional]
 * @param  charset string
 */
function iconv_mime_decode($encoded_string, $mode = null, $charset) {}

/**
 * Decodes multiple mime header fields 
 *
 * @return array
 * @param  headers string
 * @param  mode int[optional]
 * @param  charset string
 */
function iconv_mime_decode_headers($headers, $mode = null, $charset) {}

/**
 * Composes a mime header field with field_name and field_value in a specified scheme 
 *
 * @return string
 * @param  field_name string
 * @param  field_value string
 * @param  preference array[optional]
 */
function iconv_mime_encode($field_name, $field_value, $preference = null) {}

/**
 * Sets internal encoding and output encoding for ob_iconv_handler() 
 *
 * @return bool
 * @param  type string
 * @param  charset string
 */
function iconv_set_encoding($type, $charset) {}

/**
 * Returns the character count of str 
 *
 * @return int
 * @param  str string
 * @param  charset string[optional]
 */
function iconv_strlen($str, $charset = null) {}

/**
 * Finds position of first occurrence of needle within part of haystack beginning with offset 
 *
 * @return int
 * @param  haystack string
 * @param  needle string
 * @param  offset int
 * @param  charset string[optional]
 */
function iconv_strpos($haystack, $needle, $offset, $charset = null) {}

/**
 * Finds position of last occurrence of needle within part of haystack beginning with offset 
 *
 * @return int
 * @param  haystack string
 * @param  needle string
 * @param  charset string[optional]
 */
function iconv_strrpos($haystack, $needle, $charset = null) {}

/**
 * Returns specified part of a string 
 *
 * @return string
 * @param  str string
 * @param  offset int
 * @param  length int[optional]
 * @param  charset string
 */
function iconv_substr($str, $offset, $length = null, $charset) {}

/**
 * Format a local time/date as integer 
 *
 * @return int
 * @param  format string
 * @param  timestamp int[optional]
 */
function idate($format, $timestamp = null) {}

/**
 * Returns the number of rows affected by query identified by resultid 
 *
 * @return int
 * @param  resultid resource
 */
function ifx_affected_rows($resultid) {}

/**
 * Sets the default blob-mode for all select-queries  
 *
 * @return bool
 * @param  mode int
 */
function ifx_blobinfile_mode($mode) {}

/**
 * Sets the default byte-mode for all select-queries  
 *
 * @return bool
 * @param  mode int
 */
function ifx_byteasvarchar($mode) {}

/**
 * Close informix connection 
 *
 * @return bool
 * @param  connid resource[optional]
 */
function ifx_close($connid = null) {}

/**
 * Connects to database using userid/password, returns connection id 
 *
 * @return resource
 * @param  database string[optional]
 * @param  userid string[optional]
 * @param  password string[optional]
 */
function ifx_connect($database = null, $userid = null, $password = null) {}

/**
 * Duplicates the given blob-object 
 *
 * @return int
 * @param  bid int
 */
function ifx_copy_blob($bid) {}

/**
 * Creates a blob-object 
 *
 * @return int
 * @param  type int
 * @param  mode int
 * @param  param string
 */
function ifx_create_blob($type, $mode, $param) {}

/**
 * Creates a char-object 
 *
 * @return int
 * @param  param string
 */
function ifx_create_char($param) {}

/**
 * Executes a previously prepared query or opens a cursor for it 
 *
 * @return bool
 * @param  resultid resource
 */
function ifx_do($resultid) {}

/**
 * Returns the Informix error codes (SQLSTATE & SQLCODE) 
 *
 * @return string
 * @param  connection_id resource[optional]
 */
function ifx_error($connection_id = null) {}

/**
 * Returns the Informix errormessage associated with  
 *
 * @return string
 * @param  errorcode int[optional]
 */
function ifx_errormsg($errorcode = null) {}

/**
 * Fetches the next row or <position> row if using a scroll cursor 
 *
 * @return array
 * @param  resultid resource
 * @param  position mixed[optional]
 */
function ifx_fetch_row($resultid, $position = null) {}

/**
 * Returns an associative for query <resultid> array with fieldnames as key 
 *
 * @return array
 * @param  resultid resource
 */
function ifx_fieldproperties($resultid) {}

/**
 * Returns an associative array with fieldnames as key for query <resultid> 
 *
 * @return array
 * @param  resultid resource
 */
function ifx_fieldtypes($resultid) {}

/**
 * Deletes the blob-object 
 *
 * @return bool
 * @param  bid int
 */
function ifx_free_blob($bid) {}

/**
 * Deletes the char-object 
 *
 * @return bool
 * @param  bid int
 */
function ifx_free_char($bid) {}

/**
 * Releases resources for query associated with resultid 
 *
 * @return bool
 * @param  resultid resource
 */
function ifx_free_result($resultid) {}

/**
 * Returns the content of the blob-object 
 *
 * @return string
 * @param  bid int
 */
function ifx_get_blob($bid) {}

/**
 * Returns the content of the char-object 
 *
 * @return string
 * @param  bid int
 */
function ifx_get_char($bid) {}

/**
 * Returns the sqlerrd[] fields of the sqlca struct for query resultid 
 *
 * @return array
 * @param  resultid resource
 */
function ifx_getsqlca($resultid) {}

/**
 * Formats all rows of the resultid query into a html table 
 *
 * @return int
 * @param  resultid resource
 * @param  htmltableoptions string[optional]
 */
function ifx_htmltbl_result($resultid, $htmltableoptions = null) {}

/**
 * Sets the default return value of a NULL-value on a fetch-row  
 *
 * @return bool
 * @param  mode int
 */
function ifx_nullformat($mode) {}

/**
 * Returns the number of columns in query resultid 
 *
 * @return int
 * @param  resultid resource
 */
function ifx_num_fields($resultid) {}

/**
 * Returns the number of rows already fetched for query identified by resultid 
 *
 * @return int
 * @param  resultid resource
 */
function ifx_num_rows($resultid) {}

/**
 * Connects to database using userid/password, returns connection id 
 *
 * @return resource
 * @param  database string[optional]
 * @param  userid string[optional]
 * @param  password string[optional]
 */
function ifx_pconnect($database = null, $userid = null, $password = null) {}

/**
 * Prepare a query on a given connection 
 *
 * @return resource
 * @param  query string
 * @param  connid resource
 * @param  cursortype int[optional]
 * @param  idarray array[optional]
 */
function ifx_prepare($query, $connid, $cursortype = null, $idarray = null) {}

/**
 * Perform a query on a given connection 
 *
 * @return resource
 * @param  query string
 * @param  connid resource
 * @param  cursortype int[optional]
 * @param  idarray array[optional]
 */
function ifx_query($query, $connid, $cursortype = null, $idarray = null) {}

/**
 * Sets the default text-mode for all select-queries 
 *
 * @return bool
 * @param  mode int
 */
function ifx_textasvarchar($mode) {}

/**
 * Updates the content of the blob-object 
 *
 * @return bool
 * @param  bid int
 * @param  content string
 */
function ifx_update_blob($bid, $content) {}

/**
 * Updates the content of the char-object 
 *
 * @return bool
 * @param  bid int
 * @param  content string
 */
function ifx_update_char($bid, $content) {}

/**
 * Deletes the slob-object 
 *
 * @return bool
 * @param  bid int
 */
function ifxus_close_slob($bid) {}

/**
 * Creates a slob-object and opens it 
 *
 * @return int
 * @param  mode int
 */
function ifxus_create_slob($mode) {}

/**
 * Deletes the slob-object 
 *
 * @return bool
 * @param  bid int
 */
function ifxus_free_slob($bid) {}

/**
 * Opens an slob-object 
 *
 * @return int
 * @param  bid int
 * @param  mode int
 */
function ifxus_open_slob($bid, $mode) {}

/**
 * Reads nbytes of the slob-object 
 *
 * @return string
 * @param  bid int
 * @param  nbytes int
 */
function ifxus_read_slob($bid, $nbytes) {}

/**
 * Sets the current file or seek position of an open slob-object 
 *
 * @return int
 * @param  bid int
 * @param  mode int
 * @param  offset long
 */
function ifxus_seek_slob($bid, $mode, $offset) {}

/**
 * Returns the current file or seek position of an open slob-object 
 *
 * @return int
 * @param  bid int
 */
function ifxus_tell_slob($bid) {}

/**
 * Writes a string into the slob-object 
 *
 * @return int
 * @param  bid int
 * @param  content string
 */
function ifxus_write_slob($bid, $content) {}

/**
 * Set whether we want to ignore a user abort event or not 
 *
 * @return int
 * @param  value bool
 */
function ignore_user_abort($value) {}

/**
 * Output WBMP image to browser or file 
 *
 * @return int
 * @param  im resource
 * @param  filename string[optional]
 * @param  threshold int[optional]
 */
function image2wbmp($im, $filename = null, $threshold = null) {}

/**
 * Get file extension for image-type returned by getimagesize, exif_read_data, exif_thumbnail, exif_imagetype 
 *
 * @return string
 * @param  imagetype int
 * @param  include_dot bool[optional]
 */
function image_type_to_extension($imagetype, $include_dot = null) {}

/**
 * Get Mime-Type for image-type returned by getimagesize, exif_read_data, exif_thumbnail, exif_imagetype 
 *
 * @return string
 * @param  imagetype int
 */
function image_type_to_mime_type($imagetype) {}

/**
 * Turn alpha blending mode on or off for the given image 
 *
 * @return bool
 * @param  im resource
 * @param  on bool
 */
function imagealphablending($im, $on) {}

/**
 * Should antialiased functions used or not
 *
 * @return bool
 * @param  im resource
 * @param  on bool
 */
function imageantialias($im, $on) {}

/**
 * Draw a partial ellipse 
 *
 * @return bool
 * @param  im resource
 * @param  cx int
 * @param  cy int
 * @param  w int
 * @param  h int
 * @param  s int
 * @param  e int
 * @param  col int
 */
function imagearc($im, $cx, $cy, $w, $h, $s, $e, $col) {}

/**
 * Draw a character 
 *
 * @return bool
 * @param  im resource
 * @param  font int
 * @param  x int
 * @param  y int
 * @param  c string
 * @param  col int
 */
function imagechar($im, $font, $x, $y, $c, $col) {}

/**
 * Draw a character rotated 90 degrees counter-clockwise 
 *
 * @return bool
 * @param  im resource
 * @param  font int
 * @param  x int
 * @param  y int
 * @param  c string
 * @param  col int
 */
function imagecharup($im, $font, $x, $y, $c, $col) {}

/**
 * Allocate a color for an image 
 *
 * @return int
 * @param  im resource
 * @param  red int
 * @param  green int
 * @param  blue int
 */
function imagecolorallocate($im, $red, $green, $blue) {}

/**
 * Allocate a color with an alpha level.  Works for true color and palette based images 
 *
 * @return int
 * @param  im resource
 * @param  red int
 * @param  green int
 * @param  blue int
 * @param  alpha int
 */
function imagecolorallocatealpha($im, $red, $green, $blue, $alpha) {}

/**
 * Get the index of the color of a pixel 
 *
 * @return int
 * @param  im resource
 * @param  x int
 * @param  y int
 */
function imagecolorat($im, $x, $y) {}

/**
 * Get the index of the closest color to the specified color 
 *
 * @return int
 * @param  im resource
 * @param  red int
 * @param  green int
 * @param  blue int
 */
function imagecolorclosest($im, $red, $green, $blue) {}

/**
 * Find the closest matching colour with alpha transparency 
 *
 * @return int
 * @param  im resource
 * @param  red int
 * @param  green int
 * @param  blue int
 * @param  alpha int
 */
function imagecolorclosestalpha($im, $red, $green, $blue, $alpha) {}

/**
 * Get the index of the color which has the hue, white and blackness nearest to the given color 
 *
 * @return int
 * @param  im resource
 * @param  red int
 * @param  green int
 * @param  blue int
 */
function imagecolorclosesthwb($im, $red, $green, $blue) {}

/**
 * De-allocate a color for an image 
 *
 * @return bool
 * @param  im resource
 * @param  index int
 */
function imagecolordeallocate($im, $index) {}

/**
 * Get the index of the specified color 
 *
 * @return int
 * @param  im resource
 * @param  red int
 * @param  green int
 * @param  blue int
 */
function imagecolorexact($im, $red, $green, $blue) {}

/**
 * Find exact match for colour with transparency 
 *
 * @return int
 * @param  im resource
 * @param  red int
 * @param  green int
 * @param  blue int
 * @param  alpha int
 */
function imagecolorexactalpha($im, $red, $green, $blue, $alpha) {}

/**
 * Makes the colors of the palette version of an image more closely match the true color version 
 *
 * @return bool
 * @param  im1 resource
 * @param  im2 resource
 */
function imagecolormatch($im1, $im2) {}

/**
 * Get the index of the specified color or its closest possible alternative 
 *
 * @return int
 * @param  im resource
 * @param  red int
 * @param  green int
 * @param  blue int
 */
function imagecolorresolve($im, $red, $green, $blue) {}

/**
 * Resolve/Allocate a colour with an alpha level.  Works for true colour and palette based images 
 *
 * @return int
 * @param  im resource
 * @param  red int
 * @param  green int
 * @param  blue int
 * @param  alpha int
 */
function imagecolorresolvealpha($im, $red, $green, $blue, $alpha) {}

/**
 * Set the color for the specified palette index 
 *
 * @return void
 * @param  im resource
 * @param  col int
 * @param  red int
 * @param  green int
 * @param  blue int
 */
function imagecolorset($im, $col, $red, $green, $blue) {}

/**
 * Get the colors for an index 
 *
 * @return array
 * @param  im resource
 * @param  col int
 */
function imagecolorsforindex($im, $col) {}

/**
 * Find out the number of colors in an image's palette 
 *
 * @return int
 * @param  im resource
 */
function imagecolorstotal($im) {}

/**
 * Define a color as transparent 
 *
 * @return int
 * @param  im resource
 * @param  col int[optional]
 */
function imagecolortransparent($im, $col = null) {}

/**
 * Apply a 3x3 convolution matrix, using coefficient div and offset 
 *
 * @return bool
 * @param  src_im resource
 * @param  matrix3x3 array
 * @param  div double
 * @param  offset double
 */
function imageconvolution($src_im, $matrix3x3, $div, $offset) {}

/**
 * Copy part of an image 
 *
 * @return bool
 * @param  dst_im resource
 * @param  src_im resource
 * @param  dst_x int
 * @param  dst_y int
 * @param  src_x int
 * @param  src_y int
 * @param  src_w int
 * @param  src_h int
 */
function imagecopy($dst_im, $src_im, $dst_x, $dst_y, $src_x, $src_y, $src_w, $src_h) {}

/**
 * Merge one part of an image with another 
 *
 * @return bool
 * @param  src_im resource
 * @param  dst_im resource
 * @param  dst_x int
 * @param  dst_y int
 * @param  src_x int
 * @param  src_y int
 * @param  src_w int
 * @param  src_h int
 * @param  pct int
 */
function imagecopymerge($src_im, $dst_im, $dst_x, $dst_y, $src_x, $src_y, $src_w, $src_h, $pct) {}

/**
 * Merge one part of an image with another 
 *
 * @return bool
 * @param  src_im resource
 * @param  dst_im resource
 * @param  dst_x int
 * @param  dst_y int
 * @param  src_x int
 * @param  src_y int
 * @param  src_w int
 * @param  src_h int
 * @param  pct int
 */
function imagecopymergegray($src_im, $dst_im, $dst_x, $dst_y, $src_x, $src_y, $src_w, $src_h, $pct) {}

/**
 * Copy and resize part of an image using resampling to help ensure clarity 
 *
 * @return bool
 * @param  dst_im resource
 * @param  src_im resource
 * @param  dst_x int
 * @param  dst_y int
 * @param  src_x int
 * @param  src_y int
 * @param  dst_w int
 * @param  dst_h int
 * @param  src_w int
 * @param  src_h int
 */
function imagecopyresampled($dst_im, $src_im, $dst_x, $dst_y, $src_x, $src_y, $dst_w, $dst_h, $src_w, $src_h) {}

/**
 * Copy and resize part of an image 
 *
 * @return bool
 * @param  dst_im resource
 * @param  src_im resource
 * @param  dst_x int
 * @param  dst_y int
 * @param  src_x int
 * @param  src_y int
 * @param  dst_w int
 * @param  dst_h int
 * @param  src_w int
 * @param  src_h int
 */
function imagecopyresized($dst_im, $src_im, $dst_x, $dst_y, $src_x, $src_y, $dst_w, $dst_h, $src_w, $src_h) {}

/**
 * Create a new image 
 *
 * @return resource
 * @param  x_size int
 * @param  y_size int
 */
function imagecreate($x_size, $y_size) {}

/**
 * Create a new image from GD file or URL 
 *
 * @return resource
 * @param  filename string
 */
function imagecreatefromgd($filename) {}

/**
 * Create a new image from GD2 file or URL 
 *
 * @return resource
 * @param  filename string
 */
function imagecreatefromgd2($filename) {}

/**
 * Create a new image from a given part of GD2 file or URL 
 *
 * @return resource
 * @param  filename string
 * @param  srcX int
 * @param  srcY int
 * @param  width int
 * @param  height int
 */
function imagecreatefromgd2part($filename, $srcX, $srcY, $width, $height) {}

/**
 * Create a new image from GIF file or URL 
 *
 * @return resource
 * @param  filename string
 */
function imagecreatefromgif($filename) {}

/**
 * Create a new image from JPEG file or URL 
 *
 * @return resource
 * @param  filename string
 */
function imagecreatefromjpeg($filename) {}

/**
 * Create a new image from PNG file or URL 
 *
 * @return resource
 * @param  filename string
 */
function imagecreatefrompng($filename) {}

/**
 * Create a new image from the image stream in the string 
 *
 * @return resource
 * @param  image string
 */
function imagecreatefromstring($image) {}

/**
 * Create a new image from WBMP file or URL 
 *
 * @return resource
 * @param  filename string
 */
function imagecreatefromwbmp($filename) {}

/**
 * Create a new image from XBM file or URL 
 *
 * @return resource
 * @param  filename string
 */
function imagecreatefromxbm($filename) {}

/**
 * Create a new image from XPM file or URL 
 *
 * @return resource
 * @param  filename string
 */
function imagecreatefromxpm($filename) {}

/**
 * Create a new true color image 
 *
 * @return resource
 * @param  x_size int
 * @param  y_size int
 */
function imagecreatetruecolor($x_size, $y_size) {}

/**
 * Draw a dashed line 
 *
 * @return bool
 * @param  im resource
 * @param  x1 int
 * @param  y1 int
 * @param  x2 int
 * @param  y2 int
 * @param  col int
 */
function imagedashedline($im, $x1, $y1, $x2, $y2, $col) {}

/**
 * Destroy an image 
 *
 * @return bool
 * @param  im resource
 */
function imagedestroy($im) {}

/**
 * Draw an ellipse 
 *
 * @return bool
 * @param  im resource
 * @param  cx int
 * @param  cy int
 * @param  w int
 * @param  h int
 * @param  color int
 */
function imageellipse($im, $cx, $cy, $w, $h, $color) {}

/**
 * Flood fill 
 *
 * @return bool
 * @param  im resource
 * @param  x int
 * @param  y int
 * @param  col int
 */
function imagefill($im, $x, $y, $col) {}

/**
 * Draw a filled partial ellipse 
 *
 * @return bool
 * @param  im resource
 * @param  cx int
 * @param  cy int
 * @param  w int
 * @param  h int
 * @param  s int
 * @param  e int
 * @param  col int
 * @param  style int
 */
function imagefilledarc($im, $cx, $cy, $w, $h, $s, $e, $col, $style) {}

/**
 * Draw an ellipse 
 *
 * @return bool
 * @param  im resource
 * @param  cx int
 * @param  cy int
 * @param  w int
 * @param  h int
 * @param  color int
 */
function imagefilledellipse($im, $cx, $cy, $w, $h, $color) {}

/**
 * Draw a filled polygon 
 *
 * @return bool
 * @param  im resource
 * @param  point array
 * @param  num_points int
 * @param  col int
 */
function imagefilledpolygon($im, $point, $num_points, $col) {}

/**
 * Draw a filled rectangle 
 *
 * @return bool
 * @param  im resource
 * @param  x1 int
 * @param  y1 int
 * @param  x2 int
 * @param  y2 int
 * @param  col int
 */
function imagefilledrectangle($im, $x1, $y1, $x2, $y2, $col) {}

/**
 * Flood fill to specific color 
 *
 * @return bool
 * @param  im resource
 * @param  x int
 * @param  y int
 * @param  border int
 * @param  col int
 */
function imagefilltoborder($im, $x, $y, $border, $col) {}

/**
 * Applies Filter an image using a custom angle 
 *
 * @return bool
 * @param  src_im resource
 * @param  filtertype int
 * @param  args unknown[optional]
 */
function imagefilter($src_im, $filtertype, $args = null) {}

/**
 * Get font height 
 *
 * @return int
 * @param  font int
 */
function imagefontheight($font) {}

/**
 * Get font width 
 *
 * @return int
 * @param  font int
 */
function imagefontwidth($font) {}

/**
 * Give the bounding box of a text using fonts via freetype2 
 *
 * @return array
 * @param  size float
 * @param  angle float
 * @param  font_file string
 * @param  text string
 * @param  extrainfo array[optional]
 */
function imageftbbox($size, $angle, $font_file, $text, $extrainfo = null) {}

/**
 * Write text to the image using fonts via freetype2 
 *
 * @return array
 * @param  im resource
 * @param  size float
 * @param  angle float
 * @param  x int
 * @param  y int
 * @param  col int
 * @param  font_file string
 * @param  text string
 * @param  extrainfo array[optional]
 */
function imagefttext($im, $size, $angle, $x, $y, $col, $font_file, $text, $extrainfo = null) {}

/**
 * Apply a gamma correction to a GD image 
 *
 * @return bool
 * @param  im resource
 * @param  inputgamma float
 * @param  outputgamma float
 */
function imagegammacorrect($im, $inputgamma, $outputgamma) {}

/**
 * Output GD image to browser or file 
 *
 * @return bool
 * @param  im resource
 * @param  filename string[optional]
 */
function imagegd($im, $filename = null) {}

/**
 * Output GD2 image to browser or file 
 *
 * @return bool
 * @param  im resource
 * @param  filename string[optional]
 * @param  chunk_size int[optional]
 * @param  type int[optional]
 */
function imagegd2($im, $filename = null, $chunk_size = null, $type = null) {}

/**
 * Output GIF image to browser or file 
 *
 * @return bool
 * @param  im resource
 * @param  filename string[optional]
 */
function imagegif($im, $filename = null) {}

/**
 * Enable or disable interlace 
 *
 * @return int
 * @param  im resource
 * @param  interlace int[optional]
 */
function imageinterlace($im, $interlace = null) {}

/**
 * return true if the image uses truecolor 
 *
 * @return bool
 * @param  im resource
 */
function imageistruecolor($im) {}

/**
 * Output JPEG image to browser or file 
 *
 * @return bool
 * @param  im resource
 * @param  filename string[optional]
 * @param  quality int[optional]
 */
function imagejpeg($im, $filename = null, $quality = null) {}

/**
 * Set the alpha blending flag to use the bundled libgd layering effects 
 *
 * @return bool
 * @param  im resource
 * @param  effect int
 */
function imagelayereffect($im, $effect) {}

/**
 * Draw a line 
 *
 * @return bool
 * @param  im resource
 * @param  x1 int
 * @param  y1 int
 * @param  x2 int
 * @param  y2 int
 * @param  col int
 */
function imageline($im, $x1, $y1, $x2, $y2, $col) {}

/**
 * Load a new font 
 *
 * @return int
 * @param  filename string
 */
function imageloadfont($filename) {}

/**
 * Copy the palette from the src image onto the dst image 
 *
 * @return void
 * @param  dst resource
 * @param  src resource
 */
function imagepalettecopy($dst, $src) {}

/**
 * Output PNG image to browser or file 
 *
 * @return bool
 * @param  im resource
 * @param  filename string[optional]
 */
function imagepng($im, $filename = null) {}

/**
 * Draw a polygon 
 *
 * @return bool
 * @param  im resource
 * @param  point array
 * @param  num_points int
 * @param  col int
 */
function imagepolygon($im, $point, $num_points, $col) {}

/**
 * Return the bounding box needed by a string if rasterized 
 *
 * @return array
 * @param  text string
 * @param  font resource
 * @param  size int
 * @param  space int[optional]
 * @param  tightness int
 * @param  angle int
 */
function imagepsbbox($text, $font, $size, $space = null, $tightness, $angle) {}

/**
 * Make a copy of a font for purposes like extending or reenconding 
 *
 * @return int
 * @param  font_index int
 */
function imagepscopyfont($font_index) {}

/**
 * To change a fonts character encoding vector 
 *
 * @return bool
 * @param  font_index resource
 * @param  filename string
 */
function imagepsencodefont($font_index, $filename) {}

/**
 * Extend or or condense (if extend < 1) a font 
 *
 * @return bool
 * @param  font_index resource
 * @param  extend float
 */
function imagepsextendfont($font_index, $extend) {}

/**
 * Free memory used by a font 
 *
 * @return bool
 * @param  font_index resource
 */
function imagepsfreefont($font_index) {}

/**
 * Load a new font from specified file 
 *
 * @return resource
 * @param  pathname string
 */
function imagepsloadfont($pathname) {}

/**
 * Slant a font 
 *
 * @return bool
 * @param  font_index resource
 * @param  slant float
 */
function imagepsslantfont($font_index, $slant) {}

/**
 * Rasterize a string over an image 
 *
 * @return array
 * @param  image resource
 * @param  text string
 * @param  font resource
 * @param  size int
 * @param  xcoord int
 * @param  ycoord int
 * @param  space int[optional]
 * @param  tightness int
 * @param  angle float
 * @param  antialias int
 */
function imagepstext($image, $text, $font, $size, $xcoord, $ycoord, $space = null, $tightness, $angle, $antialias) {}

/**
 * Draw a rectangle 
 *
 * @return bool
 * @param  im resource
 * @param  x1 int
 * @param  y1 int
 * @param  x2 int
 * @param  y2 int
 * @param  col int
 */
function imagerectangle($im, $x1, $y1, $x2, $y2, $col) {}

/**
 * Rotate an image using a custom angle 
 *
 * @return resource
 * @param  src_im resource
 * @param  angle float
 * @param  bgdcolor int
 */
function imagerotate($src_im, $angle, $bgdcolor) {}

/**
 * Include alpha channel to a saved image 
 *
 * @return bool
 * @param  im resource
 * @param  on bool
 */
function imagesavealpha($im, $on) {}

/**
 * Set the brush image to $brush when filling $image with the "IMG_COLOR_BRUSHED" color 
 *
 * @return bool
 * @param  image resource
 * @param  brush resource
 */
function imagesetbrush($image, $brush) {}

/**
 * Set a single pixel 
 *
 * @return bool
 * @param  im resource
 * @param  x int
 * @param  y int
 * @param  col int
 */
function imagesetpixel($im, $x, $y, $col) {}

/**
 * Set the line drawing styles for use with imageline and IMG_COLOR_STYLED. 
 *
 * @return bool
 * @param  im resource
 * @param  styles array
 */
function imagesetstyle($im, $styles) {}

/**
 * Set line thickness for drawing lines, ellipses, rectangles, polygons etc. 
 *
 * @return bool
 * @param  im resource
 * @param  thickness int
 */
function imagesetthickness($im, $thickness) {}

/**
 * Set the tile image to $tile when filling $image with the "IMG_COLOR_TILED" color 
 *
 * @return bool
 * @param  image resource
 * @param  tile resource
 */
function imagesettile($image, $tile) {}

/**
 * Draw a string horizontally 
 *
 * @return bool
 * @param  im resource
 * @param  font int
 * @param  x int
 * @param  y int
 * @param  str string
 * @param  col int
 */
function imagestring($im, $font, $x, $y, $str, $col) {}

/**
 * Draw a string vertically - rotated 90 degrees counter-clockwise 
 *
 * @return bool
 * @param  im resource
 * @param  font int
 * @param  x int
 * @param  y int
 * @param  str string
 * @param  col int
 */
function imagestringup($im, $font, $x, $y, $str, $col) {}

/**
 * Get image width 
 *
 * @return int
 * @param  im resource
 */
function imagesx($im) {}

/**
 * Get image height 
 *
 * @return int
 * @param  im resource
 */
function imagesy($im) {}

/**
 * Convert a true colour image to a palette based image with a number of colours, optionally using dithering. 
 *
 * @return bool
 * @param  im resource
 * @param  ditherFlag bool
 * @param  colorsWanted int
 */
function imagetruecolortopalette($im, $ditherFlag, $colorsWanted) {}

/**
 * Give the bounding box of a text using TrueType fonts 
 *
 * @return array
 * @param  size float
 * @param  angle float
 * @param  font_file string
 * @param  text string
 */
function imagettfbbox($size, $angle, $font_file, $text) {}

/**
 * Write text to the image using a TrueType font 
 *
 * @return array
 * @param  im resource
 * @param  size float
 * @param  angle float
 * @param  x int
 * @param  y int
 * @param  col int
 * @param  font_file string
 * @param  text string
 */
function imagettftext($im, $size, $angle, $x, $y, $col, $font_file, $text) {}

/**
 * Return the types of images supported in a bitfield - 1=GIF, 2=JPEG, 4=PNG, 8=WBMP, 16=XPM 
 *
 * @return int
 */
function imagetypes() {}

/**
 * Output WBMP image to browser or file 
 *
 * @return bool
 * @param  im resource
 * @param  filename string[optional]
 * @param  foreground int[optional]
 */
function imagewbmp($im, $filename = null, $foreground = null) {}

/**
 * Output XBM image to browser or file 
 *
 * @return bool
 * @param  im int
 * @param  filename string
 * @param  foreground int[optional]
 */
function imagexbm($im, $filename, $foreground = null) {}

/**
 * Convert an 8-bit string to a quoted-printable string 
 *
 * @return string
 * @param  text string
 */
function imap_8bit($text) {}

/**
 * Returns an array of all IMAP alerts that have been generated since the last page load or since the last imap_alerts() call, whichever came last. The alert stack is cleared after imap_alerts() is called. 
 *
 * @return array
 */
function imap_alerts() {}

/**
 * Append a new message to a specified mailbox 
 *
 * @return bool
 * @param  stream_id resource
 * @param  folder string
 * @param  message string
 * @param  options string[optional]
 */
function imap_append($stream_id, $folder, $message, $options = null) {}

/**
 * Decode BASE64 encoded text 
 *
 * @return string
 * @param  text string
 */
function imap_base64($text) {}

/**
 * Convert an 8bit string to a base64 string 
 *
 * @return string
 * @param  text string
 */
function imap_binary($text) {}

/**
 * Read the message body 
 *
 * @return string
 * @param  stream_id resource
 * @param  msg_no int
 * @param  options int[optional]
 */
function imap_body($stream_id, $msg_no, $options = null) {}

/**
 * Read the structure of a specified body section of a specific message 
 *
 * @return object
 * @param  stream_id resource
 * @param  msg_no int
 * @param  section string
 */
function imap_bodystruct($stream_id, $msg_no, $section) {}

/**
 * Get mailbox properties 
 *
 * @return object
 * @param  stream_id resource
 */
function imap_check($stream_id) {}

/**
 * Clears flags on messages 
 *
 * @return bool
 * @param  stream_id resource
 * @param  sequence string
 * @param  flag string
 * @param  options int[optional]
 */
function imap_clearflag_full($stream_id, $sequence, $flag, $options = null) {}

/**
 * Close an IMAP stream 
 *
 * @return bool
 * @param  stream_id resource
 * @param  options int[optional]
 */
function imap_close($stream_id, $options = null) {}

/**
 * Create a new mailbox 
 *
 * @return bool
 * @param  stream_id resource
 * @param  mailbox string
 */
function imap_create($stream_id, $mailbox) {}

/**
 * Create a new mailbox 
 *
 * @return bool
 * @param  stream_id resource
 * @param  mailbox string
 */
function imap_createmailbox($stream_id, $mailbox) {}

/**
 * Mark a message for deletion 
 *
 * @return bool
 * @param  stream_id resource
 * @param  msg_no int
 * @param  options int[optional]
 */
function imap_delete($stream_id, $msg_no, $options = null) {}

/**
 * Delete a mailbox 
 *
 * @return bool
 * @param  stream_id resource
 * @param  mailbox string
 */
function imap_deletemailbox($stream_id, $mailbox) {}

/**
 * Returns an array of all IMAP errors generated since the last page load, or since the last imap_errors() call, whichever came last. The error stack is cleared after imap_errors() is called. 
 *
 * @return array
 */
function imap_errors() {}

/**
 * Permanently delete all messages marked for deletion 
 *
 * @return bool
 * @param  stream_id resource
 */
function imap_expunge($stream_id) {}

/**
 * Read an overview of the information in the headers of the given message sequence 
 *
 * @return array
 * @param  stream_id resource
 * @param  msg_no int
 * @param  options int[optional]
 */
function imap_fetch_overview($stream_id, $msg_no, $options = null) {}

/**
 * Get a specific body section 
 *
 * @return string
 * @param  stream_id resource
 * @param  msg_no int
 * @param  section string
 * @param  options int[optional]
 */
function imap_fetchbody($stream_id, $msg_no, $section, $options = null) {}

/**
 * Get the full unfiltered header for a message 
 *
 * @return string
 * @param  stream_id resource
 * @param  msg_no int
 * @param  options int[optional]
 */
function imap_fetchheader($stream_id, $msg_no, $options = null) {}

/**
 * Read the full structure of a message 
 *
 * @return object
 * @param  stream_id resource
 * @param  msg_no int
 * @param  options int[optional]
 */
function imap_fetchstructure($stream_id, $msg_no, $options = null) {}

/**
 * Read the message body 
 *
 * @return string
 * @param  stream_id resource
 * @param  msg_no int
 * @param  options int[optional]
 */
function imap_fetchtext($stream_id, $msg_no, $options = null) {}

/**
 * Returns the quota set to the mailbox account qroot 
 *
 * @return array
 * @param  stream_id resource
 * @param  qroot string
 */
function imap_get_quota($stream_id, $qroot) {}

/**
 * Returns the quota set to the mailbox account mbox 
 *
 * @return array
 * @param  stream_id resource
 * @param  mbox string
 */
function imap_get_quotaroot($stream_id, $mbox) {}

/**
 * Gets the ACL for a given mailbox 
 *
 * @return array
 * @param  stream_id resource
 * @param  mailbox string
 */
function imap_getacl($stream_id, $mailbox) {}

/**
 * Reads the list of mailboxes and returns a full array of objects containing name, attributes, and delimiter 
 *
 * @return array
 * @param  stream_id resource
 * @param  ref string
 * @param  pattern string
 */
function imap_getmailboxes($stream_id, $ref, $pattern) {}

/**
 * Return a list of subscribed mailboxes, in the same format as imap_getmailboxes() 
 *
 * @return array
 * @param  stream_id resource
 * @param  ref string
 * @param  pattern string
 */
function imap_getsubscribed($stream_id, $ref, $pattern) {}

/**
 * Read the headers of the message 
 *
 * @return object
 * @param  stream_id resource
 * @param  msg_no int
 * @param  from_length int[optional]
 * @param  subject_length int[optional]
 * @param  default_host string[optional]
 */
function imap_header($stream_id, $msg_no, $from_length = null, $subject_length = null, $default_host = null) {}

/**
 * Read the headers of the message 
 *
 * @return object
 * @param  stream_id resource
 * @param  msg_no int
 * @param  from_length int[optional]
 * @param  subject_length int[optional]
 * @param  default_host string[optional]
 */
function imap_headerinfo($stream_id, $msg_no, $from_length = null, $subject_length = null, $default_host = null) {}

/**
 * Returns headers for all messages in a mailbox 
 *
 * @return array
 * @param  stream_id resource
 */
function imap_headers($stream_id) {}

/**
 * Returns the last error that was generated by an IMAP function. The error stack is NOT cleared after this call. 
 *
 * @return string
 */
function imap_last_error() {}

/**
 * Read the list of mailboxes 
 *
 * @return array
 * @param  stream_id resource
 * @param  ref string
 * @param  pattern string
 */
function imap_list($stream_id, $ref, $pattern) {}

/**
 * Read the list of mailboxes 
 *
 * @return array
 * @param  stream_id resource
 * @param  ref string
 * @param  pattern string
 */
function imap_listmailbox($stream_id, $ref, $pattern) {}

/**
 * Return a list of subscribed mailboxes 
 *
 * @return array
 * @param  stream_id resource
 * @param  ref string
 * @param  pattern string
 */
function imap_listsubscribed($stream_id, $ref, $pattern) {}

/**
 * Return a list of subscribed mailboxes 
 *
 * @return array
 * @param  stream_id resource
 * @param  ref string
 * @param  pattern string
 */
function imap_lsub($stream_id, $ref, $pattern) {}

/**
 * Send an email message 
 *
 * @return bool
 * @param  to string
 * @param  subject string
 * @param  message string
 * @param  additional_headers string[optional]
 * @param  cc string[optional]
 * @param  bcc string[optional]
 * @param  rpath string[optional]
 */
function imap_mail($to, $subject, $message, $additional_headers = null, $cc = null, $bcc = null, $rpath = null) {}

/**
 * Create a MIME message based on given envelope and body sections 
 *
 * @return string
 * @param  envelope array
 * @param  body array
 */
function imap_mail_compose($envelope, $body) {}

/**
 * Copy specified message to a mailbox 
 *
 * @return bool
 * @param  stream_id resource
 * @param  msg_no int
 * @param  mailbox string
 * @param  options int[optional]
 */
function imap_mail_copy($stream_id, $msg_no, $mailbox, $options = null) {}

/**
 * Move specified message to a mailbox 
 *
 * @return bool
 * @param  stream_id resource
 * @param  msg_no int
 * @param  mailbox string
 * @param  options int[optional]
 */
function imap_mail_move($stream_id, $msg_no, $mailbox, $options = null) {}

/**
 * Returns info about the current mailbox 
 *
 * @return object
 * @param  stream_id resource
 */
function imap_mailboxmsginfo($stream_id) {}

/**
 * Decode mime header element in accordance with RFC 2047 and return array of objects containing 'charset' encoding and decoded 'text' 
 *
 * @return array
 * @param  str string
 */
function imap_mime_header_decode($str) {}

/**
 * Get the sequence number associated with a UID 
 *
 * @return int
 * @param  stream_id resource
 * @param  unique_msg_id int
 */
function imap_msgno($stream_id, $unique_msg_id) {}

/**
 * Gives the number of messages in the current mailbox 
 *
 * @return int
 * @param  stream_id resource
 */
function imap_num_msg($stream_id) {}

/**
 * Gives the number of recent messages in current mailbox 
 *
 * @return int
 * @param  stream_id resource
 */
function imap_num_recent($stream_id) {}

/**
 * Open an IMAP stream to a mailbox 
 *
 * @return resource
 * @param  mailbox string
 * @param  user string
 * @param  password string
 * @param  options int[optional]
 */
function imap_open($mailbox, $user, $password, $options = null) {}

/**
 * Check if the IMAP stream is still active 
 *
 * @return bool
 * @param  stream_id resource
 */
function imap_ping($stream_id) {}

/**
 * Convert a quoted-printable string to an 8-bit string 
 *
 * @return string
 * @param  text string
 */
function imap_qprint($text) {}

/**
 * Rename a mailbox 
 *
 * @return bool
 * @param  stream_id resource
 * @param  old_name string
 * @param  new_name string
 */
function imap_rename($stream_id, $old_name, $new_name) {}

/**
 * Rename a mailbox 
 *
 * @return bool
 * @param  stream_id resource
 * @param  old_name string
 * @param  new_name string
 */
function imap_renamemailbox($stream_id, $old_name, $new_name) {}

/**
 * Reopen an IMAP stream to a new mailbox 
 *
 * @return bool
 * @param  stream_id resource
 * @param  mailbox string
 * @param  options int[optional]
 */
function imap_reopen($stream_id, $mailbox, $options = null) {}

/**
 * Parses an address string 
 *
 * @return array
 * @param  address_string string
 * @param  default_host string
 */
function imap_rfc822_parse_adrlist($address_string, $default_host) {}

/**
 * Parse a set of mail headers contained in a string, and return an object similar to imap_headerinfo() 
 *
 * @return object
 * @param  headers string
 * @param  default_host string[optional]
 */
function imap_rfc822_parse_headers($headers, $default_host = null) {}

/**
 * Returns a properly formatted email address given the mailbox, host, and personal info 
 *
 * @return string
 * @param  mailbox string
 * @param  host string
 * @param  personal string
 */
function imap_rfc822_write_address($mailbox, $host, $personal) {}

/**
 * Save a specific body section to a file 
 *
 * @return bool
 * @param  stream_id resource
 * @param  file string|resource
 * @param  msg_no int
 * @param  section string[optional]
 * @param  options int[optional]
 */
function imap_savebody($stream_id, $file, $msg_no, $section = "", $options = null) {}

/**
 * Read list of mailboxes containing a certain string 
 *
 * @return array
 * @param  stream_id resource
 * @param  ref string
 * @param  pattern string
 * @param  content string
 */
function imap_scan($stream_id, $ref, $pattern, $content) {}

/**
 * Return a list of messages matching the given criteria 
 *
 * @return array
 * @param  stream_id resource
 * @param  criteria string
 * @param  options int[optional]
 * @param  charset string[optional]
 */
function imap_search($stream_id, $criteria, $options = null, $charset = null) {}

/**
 * Will set the quota for qroot mailbox 
 *
 * @return bool
 * @param  stream_id resource
 * @param  qroot string
 * @param  mailbox_size int
 */
function imap_set_quota($stream_id, $qroot, $mailbox_size) {}

/**
 * Sets the ACL for a given mailbox 
 *
 * @return bool
 * @param  stream_id resource
 * @param  mailbox string
 * @param  id string
 * @param  rights string
 */
function imap_setacl($stream_id, $mailbox, $id, $rights) {}

/**
 * Sets flags on messages 
 *
 * @return bool
 * @param  stream_id resource
 * @param  sequence string
 * @param  flag string
 * @param  options int[optional]
 */
function imap_setflag_full($stream_id, $sequence, $flag, $options = null) {}

/**
 * Sort an array of message headers, optionally including only messages that meet specified criteria. 
 *
 * @return array
 * @param  stream_id resource
 * @param  criteria int
 * @param  reverse int
 * @param  options int[optional]
 * @param  search_criteria string[optional]
 * @param  charset string[optional]
 */
function imap_sort($stream_id, $criteria, $reverse, $options = null, $search_criteria = null, $charset = null) {}

/**
 * Get status info from a mailbox 
 *
 * @return object
 * @param  stream_id resource
 * @param  mailbox string
 * @param  options int
 */
function imap_status($stream_id, $mailbox, $options) {}

/**
 * Subscribe to a mailbox 
 *
 * @return bool
 * @param  stream_id resource
 * @param  mailbox string
 */
function imap_subscribe($stream_id, $mailbox) {}

/**
 * Return threaded by REFERENCES tree 
 *
 * @return array
 * @param  stream_id resource
 * @param  options int[optional]
 */
function imap_thread($stream_id, $options = null) {}

/**
 * Set or fetch imap timeout 
 *
 * @return mixed
 * @param  timeout_type int
 * @param  timeout int[optional]
 */
function imap_timeout($timeout_type, $timeout = null) {}

/**
 * Get the unique message id associated with a standard sequential message number 
 *
 * @return int
 * @param  stream_id resource
 * @param  msg_no int
 */
function imap_uid($stream_id, $msg_no) {}

/**
 * Remove the delete flag from a message 
 *
 * @return bool
 * @param  stream_id resource
 * @param  msg_no int
 */
function imap_undelete($stream_id, $msg_no) {}

/**
 * Unsubscribe from a mailbox 
 *
 * @return bool
 * @param  stream_id resource
 * @param  mailbox string
 */
function imap_unsubscribe($stream_id, $mailbox) {}

/**
 * Decode a modified UTF-7 string 
 *
 * @return string
 * @param  buf string
 */
function imap_utf7_decode($buf) {}

/**
 * Encode a string in modified UTF-7 
 *
 * @return string
 * @param  buf string
 */
function imap_utf7_encode($buf) {}

/**
 * Convert a mime-encoded text to UTF-8 
 *
 * @return string
 * @param  mime_encoded_text string
 */
function imap_utf8($mime_encoded_text) {}

/**
 * Joins array elements placing glue string between items and return one string 
 *
 * @return string
 * @param  glue string[optional]
 * @param  pieces array
 */
function implode($glue = null, $pieces) {}

/**
 * Import GET/POST/Cookie variables into the global scope 
 *
 * @return bool
 * @param  types string
 * @param  prefix string[optional]
 */
function import_request_variables($types, $prefix = null) {}

/**
 * Checks if the given value exists in the array 
 *
 * @return bool
 * @param  needle mixed
 * @param  haystack array
 * @param  strict bool[optional]
 */
function in_array($needle, $haystack, $strict = null) {}

/**
 * Converts a packed inet address to a human readable IP address string 
 *
 * @return string
 * @param  in_addr string
 */
function inet_ntop($in_addr) {}

/**
 * Converts a human readable IP address to a packed binary string 
 *
 * @return string
 * @param  ip_address string
 */
function inet_pton($ip_address) {}

/**
 * Set a configuration option, returns false on error and the old value of the configuration option on success 
 *
 * @return string
 * @param  varname string
 * @param  newvalue string
 */
function ini_alter($varname, $newvalue) {}

/**
 * Get a configuration option 
 *
 * @return string
 * @param  varname string
 */
function ini_get($varname) {}

/**
 * Get all configuration options 
 *
 * @return array
 * @param  extension string[optional]
 */
function ini_get_all($extension = null) {}

/**
 * Restore the value of a configuration option specified by varname 
 *
 * @return void
 * @param  varname string
 */
function ini_restore($varname) {}

/**
 * Set a configuration option, returns false on error and the old value of the configuration option on success 
 *
 * @return string
 * @param  varname string
 * @param  newvalue string
 */
function ini_set($varname, $newvalue) {}

/**
 * Checks if the class exists 
 *
 * @return bool
 * @param  classname string
 * @param  autoload bool[optional]
 */
function interface_exists($classname, $autoload = null) {}

/**
 * Get the integer value of a variable using the optional base for the conversion 
 *
 * @return int
 * @param  var mixed
 * @param  base int[optional]
 */
function intval($var, $base = null) {}

/**
 * Converts a string containing an (IPv4) Internet Protocol dotted address into a proper address 
 *
 * @return int
 * @param  ip_address string
 */
function ip2long($ip_address) {}

/**
 * Embed binary IPTC data into a JPEG image. 
 *
 * @return mixed
 * @param  iptcdata string
 * @param  jpeg_file_name string
 * @param  spool int[optional]
 */
function iptcembed($iptcdata, $jpeg_file_name, $spool = null) {}

/**
 * Parse binary IPTC-data into associative array 
 *
 * @return array
 * @param  iptcdata string
 */
function iptcparse($iptcdata) {}

/**
 * Returns true if the object is of this class or has this class as one of its parents 
 *
 * @return bool
 * @param  object object
 * @param  class_name string
 */
function is_a($object, $class_name) {}

/**
 * Returns true if variable is an array 
 *
 * @return bool
 * @param  var mixed
 */
function is_array($var) {}

/**
 * Returns true if variable is a boolean 
 *
 * @return bool
 * @param  var mixed
 */
function is_bool($var) {}

/**
 * Returns true if var is callable. 
 *
 * @return bool
 * @param  var mixed
 * @param  syntax_only bool[optional]
 * @param  callable_name string[optional]
 */
function is_callable($var, $syntax_only = null, $callable_name = null) {}

/**
 * Returns true if file is directory 
 *
 * @return bool
 * @param  filename string
 */
function is_dir($filename) {}

/**
 * Returns true if variable is float point
 *
 * @return bool
 * @param  var mixed
 */
function is_double($var) {}

/**
 * Returns true if file is executable 
 *
 * @return bool
 * @param  filename string
 */
function is_executable($filename) {}

/**
 * Returns true if file is a regular file 
 *
 * @return bool
 * @param  filename string
 */
function is_file($filename) {}

/**
 * Returns whether argument is finite 
 *
 * @return bool
 * @param  val float
 */
function is_finite($val) {}

/**
 * Returns true if variable is float point
 *
 * @return bool
 * @param  var mixed
 */
function is_float($var) {}

/**
 * Returns whether argument is infinite 
 *
 * @return bool
 * @param  val float
 */
function is_infinite($val) {}

/**
 * Returns true if variable is a long (integer) 
 *
 * @return bool
 * @param  var mixed
 */
function is_int($var) {}

/**
 * Returns true if variable is a long (integer) 
 *
 * @return bool
 * @param  var mixed
 */
function is_integer($var) {}

/**
 * Returns true if file is symbolic link 
 *
 * @return bool
 * @param  filename string
 */
function is_link($filename) {}

/**
 * Returns true if variable is a long (integer) 
 *
 * @return bool
 * @param  var mixed
 */
function is_long($var) {}

/**
 * Returns whether argument is not a number 
 *
 * @return bool
 * @param  val float
 */
function is_nan($val) {}

/**
 * Returns true if variable is null 
 *
 * @return bool
 * @param  var mixed
 */
function is_null($var) {}

/**
 * Returns true if value is a number or a numeric string 
 *
 * @return bool
 * @param  value mixed
 */
function is_numeric($value) {}

/**
 * Returns true if variable is an object 
 *
 * @return bool
 * @param  var mixed
 */
function is_object($var) {}

/**
 * Returns true if file can be read 
 *
 * @return bool
 * @param  filename string
 */
function is_readable($filename) {}

/**
 * Returns true if variable is float point
 *
 * @return bool
 * @param  var mixed
 */
function is_real($var) {}

/**
 * Returns true if variable is a resource 
 *
 * @return bool
 * @param  var mixed
 */
function is_resource($var) {}

/**
 * Returns true if value is a scalar 
 *
 * @return bool
 * @param  value mixed
 */
function is_scalar($value) {}

/**
 * Returns true if variable is a string 
 *
 * @return bool
 * @param  var mixed
 */
function is_string($var) {}

/**
 * Returns true if the object has this class as one of its parents 
 *
 * @return bool
 * @param  object object
 * @param  class_name string
 */
function is_subclass_of($object, $class_name) {}

/**
 * Check if file was created by rfc1867 upload  
 *
 * @return bool
 * @param  path string
 */
function is_uploaded_file($path) {}

/**
 * Returns true if file can be written 
 *
 * @return bool
 * @param  filename string
 */
function is_writable($filename) {}

/**
 * Returns true if file can be written 
 *
 * @return bool
 * @param  filename string
 */
function is_writeable($filename) {}

/**
 * Count the elements in an iterator 
 *
 * @return int
 * @param  it Traversable
 */
function iterator_count($it) {}

/**
 * Copy the iterator into an array 
 *
 * @return array
 * @param  it Traversable
 */
function iterator_to_array($it) {}

/**
 * Get java server statistics 
 *
 * @return array
 */
function java_get_statistics() {}

/**
 * Clear last java exception 
 *
 * @return void
 */
function java_last_exception_clear() {}

/**
 * Get last Java exception 
 *
 * @return object
 */
function java_last_exception_get() {}

/**
 * Returns name or number of day of week from julian day count 
 *
 * @return mixed
 * @param  juliandaycount int
 * @param  mode int[optional]
 */
function jddayofweek($juliandaycount, $mode = null) {}

/**
 * Returns name of month for julian day count 
 *
 * @return string
 * @param  juliandaycount int
 * @param  mode int
 */
function jdmonthname($juliandaycount, $mode) {}

/**
 * Converts a julian day count to a french republic calendar date 
 *
 * @return string
 * @param  juliandaycount int
 */
function jdtofrench($juliandaycount) {}

/**
 * Converts a julian day count to a gregorian calendar date 
 *
 * @return string
 * @param  juliandaycount int
 */
function jdtogregorian($juliandaycount) {}

/**
 * Converts a julian day count to a jewish calendar date 
 *
 * @return string
 * @param  juliandaycount int
 * @param  hebrew bool[optional]
 * @param  fl int[optional]
 */
function jdtojewish($juliandaycount, $hebrew = null, $fl = null) {}

/**
 * Convert a julian day count to a julian calendar date 
 *
 * @return string
 * @param  juliandaycount int
 */
function jdtojulian($juliandaycount) {}

/**
 * Convert Julian Day to UNIX timestamp 
 *
 * @return int
 * @param  jday int
 */
function jdtounix($jday) {}

/**
 * Converts a jewish calendar date to a julian day count 
 *
 * @return int
 * @param  month int
 * @param  day int
 * @param  year int
 */
function jewishtojd($month, $day, $year) {}

/**
 * Joins array elements placing glue string between items and return one string 
 *
 * @return string
 * @param  glue string[optional]
 * @param  pieces array
 */
function join($glue = null, $pieces) {}

/**
 * Convert JPEG image to WBMP image 
 *
 * @return int
 * @param  f_org string
 * @param  f_dest string
 * @param  d_height int
 * @param  d_width int
 * @param  threshold int
 */
function jpeg2wbmp($f_org, $f_dest, $d_height, $d_width, $threshold) {}

/**
 * Converts a julian calendar date to julian day count 
 *
 * @return int
 * @param  month int
 * @param  day int
 * @param  year int
 */
function juliantojd($month, $day, $year) {}

/**
 * Return the key of the element currently pointed to by the internal array pointer 
 *
 * @return mixed
 * @param  array_arg array
 */
function key($array_arg) {}

/**
 * Checks if the given key or index exists in the array 
 *
 * @return bool
 * @param  key mixed
 * @param  search array
 */
function key_exists($key, $search) {}

/**
 * Sort an array by key value in reverse order 
 *
 * @return bool
 * @param  array_arg array
 * @param  sort_flags int[optional]
 */
function krsort($array_arg, $sort_flags = null) {}

/**
 * Sort an array by key 
 *
 * @return bool
 * @param  array_arg array
 * @param  sort_flags int[optional]
 */
function ksort($array_arg, $sort_flags = null) {}

/**
 * Returns a value from the combined linear congruential generator 
 *
 * @return float
 */
function lcg_value() {}

/**
 * Change symlink group 
 *
 * @return bool
 * @param  filename string
 * @param  group mixed
 */
function lchgrp($filename, $group) {}

/**
 * Translate 8859 characters to t61 characters 
 *
 * @return string
 * @param  value string
 */
function ldap_8859_to_t61($value) {}

/**
 * Add entries to LDAP directory 
 *
 * @return bool
 * @param  link resource
 * @param  dn string
 * @param  entry array
 */
function ldap_add($link, $dn, $entry) {}

/**
 * Bind to LDAP directory 
 *
 * @return bool
 * @param  link resource
 * @param  dn string[optional]
 * @param  password string
 */
function ldap_bind($link, $dn = null, $password) {}

/**
 * Unbind from LDAP directory 
 *
 * @return bool
 * @param  link resource
 */
function ldap_close($link) {}

/**
 * Determine if an entry has a specific value for one of its attributes 
 *
 * @return mixed
 * @param  link resource
 * @param  dn string
 * @param  attr string
 * @param  value string
 */
function ldap_compare($link, $dn, $attr, $value) {}

/**
 * Connect to an LDAP server 
 *
 * @return resource
 * @param  host string[optional]
 * @param  port int[optional]
 */
function ldap_connect($host = null, $port = null) {}

/**
 * Count the number of entries in a search result 
 *
 * @return int
 * @param  link resource
 * @param  result resource
 */
function ldap_count_entries($link, $result) {}

/**
 * Delete an entry from a directory 
 *
 * @return bool
 * @param  link resource
 * @param  dn string
 */
function ldap_delete($link, $dn) {}

/**
 * Convert DN to User Friendly Naming format 
 *
 * @return string
 * @param  dn string
 */
function ldap_dn2ufn($dn) {}

/**
 * Convert error number to error string 
 *
 * @return string
 * @param  errno int
 */
function ldap_err2str($errno) {}

/**
 * Get the current ldap error number 
 *
 * @return int
 * @param  link resource
 */
function ldap_errno($link) {}

/**
 * Get the current ldap error string 
 *
 * @return string
 * @param  link resource
 */
function ldap_error($link) {}

/**
 * Splits DN into its component parts 
 *
 * @return array
 * @param  dn string
 * @param  with_attrib int
 */
function ldap_explode_dn($dn, $with_attrib) {}

/**
 * Return first attribute 
 *
 * @return string
 * @param  link resource
 * @param  result_entry resource
 * @param  ber int
 */
function ldap_first_attribute($link, $result_entry, $ber) {}

/**
 * Return first result id 
 *
 * @return resource
 * @param  link resource
 * @param  result resource
 */
function ldap_first_entry($link, $result) {}

/**
 * Return first reference 
 *
 * @return resource
 * @param  link resource
 * @param  result resource
 */
function ldap_first_reference($link, $result) {}

/**
 * Free result memory 
 *
 * @return bool
 * @param  result resource
 */
function ldap_free_result($result) {}

/**
 * Get attributes from a search result entry 
 *
 * @return array
 * @param  link resource
 * @param  result_entry resource
 */
function ldap_get_attributes($link, $result_entry) {}

/**
 * Get the DN of a result entry 
 *
 * @return string
 * @param  link resource
 * @param  result_entry resource
 */
function ldap_get_dn($link, $result_entry) {}

/**
 * Get all result entries 
 *
 * @return array
 * @param  link resource
 * @param  result resource
 */
function ldap_get_entries($link, $result) {}

/**
 * Get the current value of various session-wide parameters 
 *
 * @return bool
 * @param  link resource
 * @param  option int
 * @param  retval mixed
 */
function ldap_get_option($link, $option, $retval) {}

/**
 * Get all values from a result entry 
 *
 * @return array
 * @param  link resource
 * @param  result_entry resource
 * @param  attribute string
 */
function ldap_get_values($link, $result_entry, $attribute) {}

/**
 * Get all values with lengths from a result entry 
 *
 * @return array
 * @param  link resource
 * @param  result_entry resource
 * @param  attribute string
 */
function ldap_get_values_len($link, $result_entry, $attribute) {}

/**
 * Single-level search 
 *
 * @return resource
 * @param  link resource
 * @param  base_dn string
 * @param  filter string
 * @param  attrs array[optional]
 * @param  attrsonly int[optional]
 * @param  sizelimit int[optional]
 * @param  timelimit int[optional]
 * @param  deref int[optional]
 */
function ldap_list($link, $base_dn, $filter, $attrs = null, $attrsonly = null, $sizelimit = null, $timelimit = null, $deref = null) {}

/**
 * Add attribute values to current 
 *
 * @return bool
 * @param  link resource
 * @param  dn string
 * @param  entry array
 */
function ldap_mod_add($link, $dn, $entry) {}

/**
 * Delete attribute values 
 *
 * @return bool
 * @param  link resource
 * @param  dn string
 * @param  entry array
 */
function ldap_mod_del($link, $dn, $entry) {}

/**
 * Replace attribute values with new ones 
 *
 * @return bool
 * @param  link resource
 * @param  dn string
 * @param  entry array
 */
function ldap_mod_replace($link, $dn, $entry) {}

/**
 * Replace attribute values with new ones 
 *
 * @return bool
 * @param  link resource
 * @param  dn string
 * @param  entry array
 */
function ldap_modify($link, $dn, $entry) {}

/**
 * Get the next attribute in result 
 *
 * @return string
 * @param  link resource
 * @param  result_entry resource
 * @param  ber resource
 */
function ldap_next_attribute($link, $result_entry, $ber) {}

/**
 * Get next result entry 
 *
 * @return resource
 * @param  link resource
 * @param  result_entry resource
 */
function ldap_next_entry($link, $result_entry) {}

/**
 * Get next reference 
 *
 * @return resource
 * @param  link resource
 * @param  reference_entry resource
 */
function ldap_next_reference($link, $reference_entry) {}

/**
 * Extract information from reference entry 
 *
 * @return bool
 * @param  link resource
 * @param  reference_entry resource
 * @param  referrals array
 */
function ldap_parse_reference($link, $reference_entry, $referrals) {}

/**
 * Extract information from result 
 *
 * @return bool
 * @param  link resource
 * @param  result resource
 * @param  errcode int
 * @param  matcheddn string
 * @param  errmsg string
 * @param  referrals array
 */
function ldap_parse_result($link, $result, $errcode, $matcheddn, $errmsg, $referrals) {}

/**
 * Read an entry 
 *
 * @return resource
 * @param  link resource
 * @param  base_dn string
 * @param  filter string
 * @param  attrs array[optional]
 * @param  attrsonly int[optional]
 * @param  sizelimit int[optional]
 * @param  timelimit int[optional]
 * @param  deref int[optional]
 */
function ldap_read($link, $base_dn, $filter, $attrs = null, $attrsonly = null, $sizelimit = null, $timelimit = null, $deref = null) {}

/**
 * Modify the name of an entry 
 *
 * @return bool
 * @param  link resource
 * @param  dn string
 * @param  newrdn string
 * @param  newparent string
 * @param  deleteoldrdn bool
 */
function ldap_rename($link, $dn, $newrdn, $newparent, $deleteoldrdn) {}

/**
 * Bind to LDAP directory using SASL 
 *
 * @return bool
 * @param  link resource
 * @param  binddn string[optional]
 * @param  password string
 * @param  sasl_mech string
 * @param  sasl_realm string
 * @param  sasl_authz_id string
 * @param  props string
 */
function ldap_sasl_bind($link, $binddn = null, $password, $sasl_mech, $sasl_realm, $sasl_authz_id, $props) {}

/**
 * Search LDAP tree under base_dn 
 *
 * @return resource
 * @param  link resource
 * @param  base_dn string
 * @param  filter string
 * @param  attrs array[optional]
 * @param  attrsonly int[optional]
 * @param  sizelimit int[optional]
 * @param  timelimit int[optional]
 * @param  deref int[optional]
 */
function ldap_search($link, $base_dn, $filter, $attrs = null, $attrsonly = null, $sizelimit = null, $timelimit = null, $deref = null) {}

/**
 * Set the value of various session-wide parameters 
 *
 * @return bool
 * @param  link resource
 * @param  option int
 * @param  newval mixed
 */
function ldap_set_option($link, $option, $newval) {}

/**
 * Set a callback function to do re-binds on referral chasing. 
 *
 * @return bool
 * @param  link resource
 * @param  callback string
 */
function ldap_set_rebind_proc($link, $callback) {}

/**
 * Sort LDAP result entries 
 *
 * @return bool
 * @param  link resource
 * @param  result resource
 * @param  sortfilter string
 */
function ldap_sort($link, $result, $sortfilter) {}

/**
 * Start TLS 
 *
 * @return bool
 * @param  link resource
 */
function ldap_start_tls($link) {}

/**
 * Translate t61 characters to 8859 characters 
 *
 * @return string
 * @param  value string
 */
function ldap_t61_to_8859($value) {}

/**
 * Unbind from LDAP directory 
 *
 * @return bool
 * @param  link resource
 */
function ldap_unbind($link) {}

/**
 * Cause an intentional memory leak, for testing/debugging purposes 
 *
 * @return void
 * @param  num_bytes int[optional]
 */
function leak($num_bytes = 3) {}

/**
 * Calculate Levenshtein distance between two strings 
 *
 * @return int
 * @param  str1 string
 * @param  str2 string
 */
function levenshtein($str1, $str2) {}

/**
 * Clear last error from libxml 
 *
 * @return void
 */
function libxml_clear_errors() {}

/**
 * Retrieve array of errors 
 *
 * @return array
 */
function libxml_get_errors() {}

/**
 * Retrieve last error from libxml 
 *
 * @return LibXMLError
 */
function libxml_get_last_error() {}

/**
 * Set the streams context for the next libxml document load or write 
 *
 * @return void
 * @param  streams_context resource
 */
function libxml_set_streams_context($streams_context) {}

/**
 * Disable libxml errors and allow user to fetch error information as needed 
 *
 * @return bool
 * @param  use_errors boolean
 */
function libxml_use_internal_errors($use_errors) {}

/**
 * Create a hard link 
 *
 * @return bool
 * @param  target string
 * @param  link string
 */
function link($target, $link) {}

/**
 * Returns the st_dev field of the UNIX C stat structure describing the link 
 *
 * @return int
 * @param  filename string
 */
function linkinfo($filename) {}

/**
 * Returns numeric formatting information based on the current locale 
 *
 * @return array
 */
function localeconv() {}

/**
 * Returns the results of the C system call localtime as an associative array if the associative_array argument is set to 1 other wise it is a regular array 
 *
 * @return array
 * @param  timestamp int[optional]
 * @param  associative_array bool[optional]
 */
function localtime($timestamp = null, $associative_array = null) {}

/**
 * Returns the natural logarithm of the number, or the base log if base is specified 
 *
 * @return float
 * @param  number float
 * @param  base float[optional]
 */
function log($number, $base = null) {}

/**
 * Returns the base-10 logarithm of the number 
 *
 * @return float
 * @param  number float
 */
function log10($number) {}

/**
 * Returns log(1 + number), computed in a way that accurate even when the value of number is close to zero 
 *
 * @return float
 * @param  number float
 */
function log1p($number) {}

/**
 * Converts an (IPv4) Internet network address into a string in Internet standard dotted format 
 *
 * @return string
 * @param  proper_address int
 */
function long2ip($proper_address) {}

/**
 * Give information about a file or symbolic link 
 *
 * @return array
 * @param  filename string
 */
function lstat($filename) {}

/**
 * Strips whitespace from the beginning of a string 
 *
 * @return string
 * @param  str string
 * @param  character_mask string[optional]
 */
function ltrim($str, $character_mask = null) {}

/**
 * Set the current active configuration setting of magic_quotes_runtime and return previous 
 *
 * @return bool
 * @param  new_setting int
 */
function magic_quotes_runtime($new_setting) {}

/**
 * Send an email message 
 *
 * @return bool
 * @param  to string
 * @param  subject string
 * @param  message string
 * @param  additional_headers string[optional]
 * @param  additional_parameters string[optional]
 */
function mail($to, $subject, $message, $additional_headers = null, $additional_parameters = null) {}

/**
 * Return the highest value in an array or a series of arguments 
 *
 * @return mixed
 * @param  arg1 mixed
 * @param  arg2 mixed[optional]
 * @vararg ... mixed
 */
function max($arg1, $arg2 = null) {}

/**
 * Check if the string is valid for the specified encoding 
 *
 * @return bool
 * @param  var string[optional]
 * @param  encoding string
 */
function mb_check_encoding($var = null, $encoding) {}

/**
 * Returns a case-folded version of sourcestring 
 *
 * @return string
 * @param  sourcestring string
 * @param  mode int
 * @param  encoding string[optional]
 */
function mb_convert_case($sourcestring, $mode, $encoding = null) {}

/**
 * Returns converted string in desired encoding 
 *
 * @return string
 * @param  str string
 * @param  to_encoding string
 * @param  from_encoding mixed[optional]
 */
function mb_convert_encoding($str, $to_encoding, $from_encoding = null) {}

/**
 * Conversion between full-width character and half-width character (Japanese) 
 *
 * @return string
 * @param  str string
 * @param  option string[optional]
 * @param  encoding string[optional]
 */
function mb_convert_kana($str, $option = null, $encoding = null) {}

/**
 * Converts the string resource in variables to desired encoding 
 *
 * @return string
 * @param  to_encoding string
 * @param  from_encoding mixed
 * @vararg ... mixed
 */
function mb_convert_variables($to_encoding, $from_encoding) {}

/**
 * Decodes the MIME "encoded-word" in the string 
 *
 * @return string
 * @param  string string
 */
function mb_decode_mimeheader($string) {}

/**
 * Converts HTML numeric entities to character code 
 *
 * @return string
 * @param  string string
 * @param  convmap array
 * @param  encoding string[optional]
 */
function mb_decode_numericentity($string, $convmap, $encoding = null) {}

/**
 * Encodings of the given string is returned (as a string) 
 *
 * @return string
 * @param  str string
 * @param  encoding_list mixed[optional]
 * @param  strict bool[optional]
 */
function mb_detect_encoding($str, $encoding_list = null, $strict = null) {}

/**
 * Sets the current detect_order or Return the current detect_order as a array 
 *
 * @return mixed
 * @param  encoding_list mixed[optional]
 */
function mb_detect_order($encoding_list = null) {}

/**
 * Converts the string to MIME "encoded-word" in the format of =?charset?(B|Q)?encoded_string?= 
 *
 * @return string
 * @param  str string
 * @param  charset string[optional]
 * @param  transfer_encoding string[optional]
 * @param  linefeed string[optional]
 * @param  indent int[optional]
 */
function mb_encode_mimeheader($str, $charset = null, $transfer_encoding = null, $linefeed = null, $indent = null) {}

/**
 * Converts specified characters to HTML numeric entities 
 *
 * @return string
 * @param  string string
 * @param  convmap array
 * @param  encoding string[optional]
 */
function mb_encode_numericentity($string, $convmap, $encoding = null) {}

/**
 * Regular expression match for multibyte string 
 *
 * @return int
 * @param  pattern string
 * @param  string string
 * @param  registers array[optional]
 */
function mb_ereg($pattern, $string, $registers = null) {}

/**
 * Regular expression match for multibyte string 
 *
 * @return bool
 * @param  pattern string
 * @param  string string
 * @param  option string[optional]
 */
function mb_ereg_match($pattern, $string, $option = null) {}

/**
 * Replace regular expression for multibyte string 
 *
 * @return string
 * @param  pattern string
 * @param  replacement string
 * @param  string string
 * @param  option string[optional]
 */
function mb_ereg_replace($pattern, $replacement, $string, $option = null) {}

/**
 * Regular expression search for multibyte string 
 *
 * @return bool
 * @param  pattern string[optional]
 * @param  option string
 */
function mb_ereg_search($pattern = null, $option) {}

/**
 * Get search start position 
 *
 * @return int
 */
function mb_ereg_search_getpos() {}

/**
 * Get matched substring of the last time 
 *
 * @return array
 */
function mb_ereg_search_getregs() {}

/**
 * Initialize string and regular expression for search. 
 *
 * @return bool
 * @param  string string
 * @param  pattern string[optional]
 * @param  option string
 */
function mb_ereg_search_init($string, $pattern = null, $option) {}

/**
 * Regular expression search for multibyte string 
 *
 * @return array
 * @param  pattern string[optional]
 * @param  option string
 */
function mb_ereg_search_pos($pattern = null, $option) {}

/**
 * Regular expression search for multibyte string 
 *
 * @return array
 * @param  pattern string[optional]
 * @param  option string
 */
function mb_ereg_search_regs($pattern = null, $option) {}

/**
 * Set search start position 
 *
 * @return bool
 * @param  position int
 */
function mb_ereg_search_setpos($position) {}

/**
 * Case-insensitive regular expression match for multibyte string 
 *
 * @return int
 * @param  pattern string
 * @param  string string
 * @param  registers array[optional]
 */
function mb_eregi($pattern, $string, $registers = null) {}

/**
 * Case insensitive replace regular expression for multibyte string 
 *
 * @return string
 * @param  pattern string
 * @param  replacement string
 * @param  string string
 */
function mb_eregi_replace($pattern, $replacement, $string) {}

/**
 * Returns the current settings of mbstring 
 *
 * @return mixed
 * @param  type string[optional]
 */
function mb_get_info($type = null) {}

/**
 * Returns the input encoding 
 *
 * @return mixed
 * @param  type string[optional]
 */
function mb_http_input($type = null) {}

/**
 * Sets the current output_encoding or returns the current output_encoding as a string 
 *
 * @return mixed
 * @param  encoding string[optional]
 */
function mb_http_output($encoding = null) {}

/**
 * Sets the current internal encoding or Returns the current internal encoding as a string 
 *
 * @return mixed
 * @param  encoding string[optional]
 */
function mb_internal_encoding($encoding = null) {}

/**
 * Sets the current language or Returns the current language as a string 
 *
 * @return mixed
 * @param  language string[optional]
 */
function mb_language($language = null) {}

/**
 * Returns an array of all supported encodings 
 *
 * @return array
 */
function mb_list_encodings() {}

/**
 * Returns string in output buffer converted to the http_output encoding 
 *
 * @return string
 * @param  contents string
 * @param  status int
 */
function mb_output_handler($contents, $status) {}

/**
 * Parses GET/POST/COOKIE data and sets global variables 
 *
 * @return bool
 * @param  encoded_string string
 * @param  result array[optional]
 */
function mb_parse_str($encoded_string, $result = null) {}

/**
 * Return the preferred MIME name (charset) as a string 
 *
 * @return string
 * @param  encoding string
 */
function mb_preferred_mime_name($encoding) {}

/**
 * Returns the current encoding for regex as a string. 
 *
 * @return mixed
 * @param  encoding string[optional]
 */
function mb_regex_encoding($encoding = null) {}

/**
 * Set or get the default options for mbregex functions 
 *
 * @return string
 * @param  options string[optional]
 */
function mb_regex_set_options($options = null) {}

/**
 * 
 *
 * @return bool
 * @param  to string
 * @param  subject string
 * @param  message string
 * @param  additional_headers string[optional]
 * @param  additional_parameters string[optional]
 */
function mb_send_mail($to, $subject, $message, $additional_headers = null, $additional_parameters = null) {}

/**
 * split multibyte string into array by regular expression 
 *
 * @return array
 * @param  pattern string
 * @param  string string
 * @param  limit int[optional]
 */
function mb_split($pattern, $string, $limit = null) {}

/**
 * Returns part of a string 
 *
 * @return string
 * @param  str string
 * @param  start int
 * @param  length int[optional]
 * @param  encoding string[optional]
 */
function mb_strcut($str, $start, $length = null, $encoding = null) {}

/**
 * Trim the string in terminal width 
 *
 * @return string
 * @param  str string
 * @param  start int
 * @param  width int
 * @param  trimmarker string[optional]
 * @param  encoding string[optional]
 */
function mb_strimwidth($str, $start, $width, $trimmarker = null, $encoding = null) {}

/**
 * Get character numbers of a string 
 *
 * @return int
 * @param  str string
 * @param  encoding string[optional]
 */
function mb_strlen($str, $encoding = null) {}

/**
 * Find position of first occurrence of a string within another 
 *
 * @return int
 * @param  haystack string
 * @param  needle string
 * @param  offset int[optional]
 * @param  encoding string[optional]
 */
function mb_strpos($haystack, $needle, $offset = null, $encoding = null) {}

/**
 * Find the last occurrence of a character in a string within another 
 *
 * @return int
 * @param  haystack string
 * @param  needle string
 * @param  encoding string[optional]
 */
function mb_strrpos($haystack, $needle, $encoding = null) {}

/**
 * 
 *
 * @return string
 * @param  sourcestring string
 * @param  encoding string[optional]
 */
function mb_strtolower($sourcestring, $encoding = null) {}

/**
 * 
 *
 * @return string
 * @param  sourcestring string
 * @param  encoding string[optional]
 */
function mb_strtoupper($sourcestring, $encoding = null) {}

/**
 * Gets terminal width of a string 
 *
 * @return int
 * @param  str string
 * @param  encoding string[optional]
 */
function mb_strwidth($str, $encoding = null) {}

/**
 * Sets the current substitute_character or returns the current substitute_character 
 *
 * @return mixed
 * @param  substchar mixed[optional]
 */
function mb_substitute_character($substchar = null) {}

/**
 * Returns part of a string 
 *
 * @return string
 * @param  str string
 * @param  start int
 * @param  length int[optional]
 * @param  encoding string[optional]
 */
function mb_substr($str, $start, $length = null, $encoding = null) {}

/**
 * Count the number of substring occurrences 
 *
 * @return int
 * @param  haystack string
 * @param  needle string
 * @param  encoding string[optional]
 */
function mb_substr_count($haystack, $needle, $encoding = null) {}

/**
 * Regular expression match for multibyte string 
 *
 * @return int
 * @param  pattern string
 * @param  string string
 * @param  registers array[optional]
 */
function mbereg($pattern, $string, $registers = null) {}

/**
 * Regular expression match for multibyte string 
 *
 * @return bool
 * @param  pattern string
 * @param  string string
 * @param  option string[optional]
 */
function mbereg_match($pattern, $string, $option = null) {}

/**
 * Replace regular expression for multibyte string 
 *
 * @return string
 * @param  pattern string
 * @param  replacement string
 * @param  string string
 * @param  option string[optional]
 */
function mbereg_replace($pattern, $replacement, $string, $option = null) {}

/**
 * Regular expression search for multibyte string 
 *
 * @return bool
 * @param  pattern string[optional]
 * @param  option string
 */
function mbereg_search($pattern = null, $option) {}

/**
 * Get search start position 
 *
 * @return int
 */
function mbereg_search_getpos() {}

/**
 * Get matched substring of the last time 
 *
 * @return array
 */
function mbereg_search_getregs() {}

/**
 * Initialize string and regular expression for search. 
 *
 * @return bool
 * @param  string string
 * @param  pattern string[optional]
 * @param  option string
 */
function mbereg_search_init($string, $pattern = null, $option) {}

/**
 * Regular expression search for multibyte string 
 *
 * @return array
 * @param  pattern string[optional]
 * @param  option string
 */
function mbereg_search_pos($pattern = null, $option) {}

/**
 * Regular expression search for multibyte string 
 *
 * @return array
 * @param  pattern string[optional]
 * @param  option string
 */
function mbereg_search_regs($pattern = null, $option) {}

/**
 * Set search start position 
 *
 * @return bool
 * @param  position int
 */
function mbereg_search_setpos($position) {}

/**
 * Case-insensitive regular expression match for multibyte string 
 *
 * @return int
 * @param  pattern string
 * @param  string string
 * @param  registers array[optional]
 */
function mberegi($pattern, $string, $registers = null) {}

/**
 * Case insensitive replace regular expression for multibyte string 
 *
 * @return string
 * @param  pattern string
 * @param  replacement string
 * @param  string string
 */
function mberegi_replace($pattern, $replacement, $string) {}

/**
 * Returns the current encoding for regex as a string. 
 *
 * @return mixed
 * @param  encoding string[optional]
 */
function mbregex_encoding($encoding = null) {}

/**
 * split multibyte string into array by regular expression 
 *
 * @return array
 * @param  pattern string
 * @param  string string
 * @param  limit int[optional]
 */
function mbsplit($pattern, $string, $limit = null) {}

/**
 * CBC crypt/decrypt data using key key with cipher cipher starting with iv 
 *
 * @return string
 * @param  cipher int
 * @param  key string
 * @param  data string
 * @param  mode int
 * @param  iv string
 */
function mcrypt_cbc($cipher, $key, $data, $mode, $iv) {}

/**
 * CFB crypt/decrypt data using key key with cipher cipher starting with iv 
 *
 * @return string
 * @param  cipher int
 * @param  key string
 * @param  data string
 * @param  mode int
 * @param  iv string
 */
function mcrypt_cfb($cipher, $key, $data, $mode, $iv) {}

/**
 * Create an initialization vector (IV) 
 *
 * @return string
 * @param  size int
 * @param  source int
 */
function mcrypt_create_iv($size, $source) {}

/**
 * OFB crypt/decrypt data using key key with cipher cipher starting with iv 
 *
 * @return string
 * @param  cipher string
 * @param  key string
 * @param  data string
 * @param  mode string
 * @param  iv string
 */
function mcrypt_decrypt($cipher, $key, $data, $mode, $iv) {}

/**
 * ECB crypt/decrypt data using key key with cipher cipher starting with iv 
 *
 * @return string
 * @param  cipher int
 * @param  key string
 * @param  data string
 * @param  mode int
 * @param  iv string
 */
function mcrypt_ecb($cipher, $key, $data, $mode, $iv) {}

/**
 * Returns the name of the algorithm specified by the descriptor td 
 *
 * @return string
 * @param  td resource
 */
function mcrypt_enc_get_algorithms_name($td) {}

/**
 * Returns the block size of the cipher specified by the descriptor td 
 *
 * @return int
 * @param  td resource
 */
function mcrypt_enc_get_block_size($td) {}

/**
 * Returns the size of the IV in bytes of the algorithm specified by the descriptor td 
 *
 * @return int
 * @param  td resource
 */
function mcrypt_enc_get_iv_size($td) {}

/**
 * Returns the maximum supported key size in bytes of the algorithm specified by the descriptor td 
 *
 * @return int
 * @param  td resource
 */
function mcrypt_enc_get_key_size($td) {}

/**
 * Returns the name of the mode specified by the descriptor td 
 *
 * @return string
 * @param  td resource
 */
function mcrypt_enc_get_modes_name($td) {}

/**
 * This function decrypts the crypttext 
 *
 * @return array
 * @param  td resource
 */
function mcrypt_enc_get_supported_key_sizes($td) {}

/**
 * Returns TRUE if the alrogithm is a block algorithms 
 *
 * @return bool
 * @param  td resource
 */
function mcrypt_enc_is_block_algorithm($td) {}

/**
 * Returns TRUE if the mode is for use with block algorithms 
 *
 * @return bool
 * @param  td resource
 */
function mcrypt_enc_is_block_algorithm_mode($td) {}

/**
 * Returns TRUE if the mode outputs blocks 
 *
 * @return bool
 * @param  td resource
 */
function mcrypt_enc_is_block_mode($td) {}

/**
 * This function runs the self test on the algorithm specified by the descriptor td 
 *
 * @return int
 * @param  td resource
 */
function mcrypt_enc_self_test($td) {}

/**
 * OFB crypt/decrypt data using key key with cipher cipher starting with iv 
 *
 * @return string
 * @param  cipher string
 * @param  key string
 * @param  data string
 * @param  mode string
 * @param  iv string
 */
function mcrypt_encrypt($cipher, $key, $data, $mode, $iv) {}

/**
 * This function encrypts the plaintext 
 *
 * @return string
 * @param  td resource
 * @param  data string
 */
function mcrypt_generic($td, $data) {}

/**
 * This function terminates encrypt specified by the descriptor td 
 *
 * @return bool
 * @param  td resource
 */
function mcrypt_generic_deinit($td) {}

/**
 * This function terminates encrypt specified by the descriptor td 
 *
 * @return bool
 * @param  td resource
 */
function mcrypt_generic_end($td) {}

/**
 * This function initializes all buffers for the specific module 
 *
 * @return int
 * @param  td resource
 * @param  key string
 * @param  iv string
 */
function mcrypt_generic_init($td, $key, $iv) {}

/**
 * Get the key size of cipher 
 *
 * @return int
 * @param  cipher string
 * @param  module string
 */
function mcrypt_get_block_size($cipher, $module) {}

/**
 * Get the key size of cipher 
 *
 * @return string
 * @param  cipher string
 */
function mcrypt_get_cipher_name($cipher) {}

/**
 * Get the IV size of cipher (Usually the same as the blocksize) 
 *
 * @return int
 * @param  cipher string
 * @param  module string
 */
function mcrypt_get_iv_size($cipher, $module) {}

/**
 * Get the key size of cipher 
 *
 * @return int
 * @param  cipher string
 * @param  module string
 */
function mcrypt_get_key_size($cipher, $module) {}

/**
 * List all algorithms in "module_dir" 
 *
 * @return array
 * @param  lib_dir string[optional]
 */
function mcrypt_list_algorithms($lib_dir = null) {}

/**
 * List all modes "module_dir" 
 *
 * @return array
 * @param  lib_dir string[optional]
 */
function mcrypt_list_modes($lib_dir = null) {}

/**
 * Free the descriptor td 
 *
 * @return bool
 * @param  td resource
 */
function mcrypt_module_close($td) {}

/**
 * Returns the block size of the algorithm 
 *
 * @return int
 * @param  algorithm string
 * @param  lib_dir string[optional]
 */
function mcrypt_module_get_algo_block_size($algorithm, $lib_dir = null) {}

/**
 * Returns the maximum supported key size of the algorithm 
 *
 * @return int
 * @param  algorithm string
 * @param  lib_dir string[optional]
 */
function mcrypt_module_get_algo_key_size($algorithm, $lib_dir = null) {}

/**
 * This function decrypts the crypttext 
 *
 * @return array
 * @param  algorithm string
 * @param  lib_dir string[optional]
 */
function mcrypt_module_get_supported_key_sizes($algorithm, $lib_dir = null) {}

/**
 * Returns TRUE if the algorithm is a block algorithm 
 *
 * @return bool
 * @param  algorithm string
 * @param  lib_dir string[optional]
 */
function mcrypt_module_is_block_algorithm($algorithm, $lib_dir = null) {}

/**
 * Returns TRUE if the mode is for use with block algorithms 
 *
 * @return bool
 * @param  mode string
 * @param  lib_dir string[optional]
 */
function mcrypt_module_is_block_algorithm_mode($mode, $lib_dir = null) {}

/**
 * Returns TRUE if the mode outputs blocks of bytes 
 *
 * @return bool
 * @param  mode string
 * @param  lib_dir string[optional]
 */
function mcrypt_module_is_block_mode($mode, $lib_dir = null) {}

/**
 * Opens the module of the algorithm and the mode to be used 
 *
 * @return resource
 * @param  cipher string
 * @param  cipher_directory string
 * @param  mode string
 * @param  mode_directory string
 */
function mcrypt_module_open($cipher, $cipher_directory, $mode, $mode_directory) {}

/**
 * Does a self test of the module "module" 
 *
 * @return bool
 * @param  algorithm string
 * @param  lib_dir string[optional]
 */
function mcrypt_module_self_test($algorithm, $lib_dir = null) {}

/**
 * OFB crypt/decrypt data using key key with cipher cipher starting with iv 
 *
 * @return string
 * @param  cipher int
 * @param  key string
 * @param  data string
 * @param  mode int
 * @param  iv string
 */
function mcrypt_ofb($cipher, $key, $data, $mode, $iv) {}

/**
 * Calculate the md5 hash of a string 
 *
 * @return string
 * @param  str string
 * @param  raw_output bool[optional]
 */
function md5($str, $raw_output = null) {}

/**
 * Calculate the md5 hash of given filename 
 *
 * @return string
 * @param  filename string
 * @param  raw_output bool[optional]
 */
function md5_file($filename, $raw_output = null) {}

/**
 * This function decrypts the plaintext 
 *
 * @return string
 * @param  td resource
 * @param  data string
 */
function mdecrypt_generic($td, $data) {}

/**
 * Returns the allocated by PHP memory 
 *
 * @return int
 */
function memory_get_usage() {}

/**
 * Break english phrases down into their phonemes 
 *
 * @return string
 * @param  text string
 * @param  phones int
 */
function metaphone($text, $phones) {}

/**
 * Checks if the class method exists 
 *
 * @return bool
 * @param  object object
 * @param  method string
 */
function method_exists($object, $method) {}

/**
 * Hash data with hash 
 *
 * @return string
 * @param  hash int
 * @param  data string
 * @param  key string[optional]
 */
function mhash($hash, $data, $key = null) {}

/**
 * Gets the number of available hashes 
 *
 * @return int
 */
function mhash_count() {}

/**
 * Gets the block size of hash 
 *
 * @return int
 * @param  hash int
 */
function mhash_get_block_size($hash) {}

/**
 * Gets the name of hash 
 *
 * @return string
 * @param  hash int
 */
function mhash_get_hash_name($hash) {}

/**
 * Generates a key using hash functions 
 *
 * @return string
 * @param  hash int
 * @param  input_password string
 * @param  salt string
 * @param  bytes int
 */
function mhash_keygen_s2k($hash, $input_password, $salt, $bytes) {}

/**
 * Returns either a string or a float containing the current time in seconds and microseconds 
 *
 * @return mixed
 * @param  get_as_float bool[optional]
 */
function microtime($get_as_float = null) {}

/**
 * Return content-type for file 
 *
 * @return string
 * @param  filename|resource_stream string
 */
function mime_content_type($filename) {}

/**
 * Return the lowest value in an array or a series of arguments 
 *
 * @return mixed
 * @param  arg1 mixed
 * @param  arg2 mixed[optional]
 * @vararg ... mixed
 */
function min($arg1, $arg2 = null) {}

/**
 * Returns the action flag for keyPress(char) 
 *
 * @return int
 * @param  str string
 */
function ming_keypress($str) {}

/**
 * Set cubic threshold (?) 
 *
 * @return void
 * @param  threshold int
 */
function ming_setcubicthreshold($threshold) {}

/**
 * Set scale (?) 
 *
 * @return void
 * @param  scale int
 */
function ming_setscale($scale) {}

/**
 * Use constant pool (?) 
 *
 * @return void
 * @param  use int
 */
function ming_useconstants($use) {}

/**
 * Use SWF version (?) 
 *
 * @return void
 * @param  version int
 */
function ming_useswfversion($version) {}

/**
 * Create a directory 
 *
 * @return bool
 * @param  pathname string
 * @param  mode int[optional]
 * @param  recursive bool[optional]
 * @param  context resource[optional]
 */
function mkdir($pathname, $mode = null, $recursive = null, $context = null) {}

/**
 * Get UNIX timestamp for a date 
 *
 * @return int
 * @param  hour int
 * @param  min int
 * @param  sec int
 * @param  mon int
 * @param  day int
 * @param  year int
 */
function mktime($hour, $min, $sec, $mon, $day, $year) {}

/**
 * Convert monetary value(s) to string 
 *
 * @return string
 * @param  format string
 * @param  value float
 */
function money_format($format, $value) {}

/**
 * 
 *
 * @return void
 * @param  class string
 * @param  text string
 * @param  severe bool
 * @param  user_data mixed
 */
function monitor_custom_event($class, $text, $severe, $user_data) {}

/**
 * 
 *
 * @return void
 * @param  errno integer
 * @param  errstr string
 * @param  errfile string
 * @param  errline integer
 */
function monitor_pass_error($errno, $errstr, $errfile, $errline) {}

/**
 * 
 *
 * @return void
 * @param  hint string
 */
function monitor_set_aggregation_hint($hint) {}

/**
 * Move a file if and only if it was created by an upload 
 *
 * @return bool
 * @param  path string
 * @param  new_path string
 */
function move_uploaded_file($path, $new_path) {}

/**
 * Attach to a message queue 
 *
 * @return resource
 * @param  key int
 * @param  perms int[optional]
 */
function msg_get_queue($key, $perms = null) {}

/**
 * Send a message of type msgtype (must be > 0) to a message queue 
 *
 * @return bool
 * @param  queue resource
 * @param  desiredmsgtype int
 * @param  msgtype int
 * @param  maxsize int
 * @param  message mixed
 * @param  unserialize bool[optional]
 * @param  flags int[optional]
 * @param  errorcode int[optional]
 */
function msg_receive($queue, $desiredmsgtype, &$msgtype, $maxsize, $message, $unserialize = true, $flags = null, $errorcode = null) {}

/**
 * Destroy the queue 
 *
 * @return bool
 * @param  queue resource
 */
function msg_remove_queue($queue) {}

/**
 * Send a message of type msgtype (must be > 0) to a message queue 
 *
 * @return bool
 * @param  queue resource
 * @param  msgtype int
 * @param  message mixed
 * @param  serialize bool[optional]
 * @param  blocking bool[optional]
 * @param  errorcode int[optional]
 */
function msg_send($queue, $msgtype, $message, $serialize = true, $blocking = true, $errorcode = null) {}

/**
 * Set information for a message queue 
 *
 * @return bool
 * @param  queue resource
 * @param  data array
 */
function msg_set_queue($queue, $data) {}

/**
 * Returns information about a message queue 
 *
 * @return array
 * @param  queue resource
 */
function msg_stat_queue($queue) {}

/**
 * Send an SQL query to mSQL 
 *
 * @return resource
 * @param  database_name string
 * @param  query string
 * @param  link_identifier resource[optional]
 */
function msql($database_name, $query, $link_identifier = null) {}

/**
 * Return number of affected rows 
 *
 * @return int
 * @param  query resource
 */
function msql_affected_rows($query) {}

/**
 * Close an mSQL connection 
 *
 * @return bool
 * @param  link_identifier resource[optional]
 */
function msql_close($link_identifier = null) {}

/**
 * Open a connection to an mSQL Server 
 *
 * @return resource
 * @param  hostname string[optional]
 * @param  username string[optional]
 * @param  password string[optional]
 */
function msql_connect($hostname = null, $username = null, $password = null) {}

/**
 * Create an mSQL database 
 *
 * @return bool
 * @param  database_name string
 * @param  link_identifier resource[optional]
 */
function msql_create_db($database_name, $link_identifier = null) {}

/**
 * Create an mSQL database 
 *
 * @return bool
 * @param  database_name string
 * @param  link_identifier resource[optional]
 */
function msql_createdb($database_name, $link_identifier = null) {}

/**
 * Move internal result pointer 
 *
 * @return bool
 * @param  query resource
 * @param  row_number int
 */
function msql_data_seek($query, $row_number) {}

/**
 * Send an SQL query to mSQL 
 *
 * @return resource
 * @param  database_name string
 * @param  query string
 * @param  link_identifier resource[optional]
 */
function msql_db_query($database_name, $query, $link_identifier = null) {}

/**
 * Get result data 
 *
 * @return string
 * @param  query int
 * @param  row int
 * @param  field mixed[optional]
 */
function msql_dbname($query, $row, $field = null) {}

/**
 * Drop (delete) an mSQL database 
 *
 * @return bool
 * @param  database_name string
 * @param  link_identifier resource[optional]
 */
function msql_drop_db($database_name, $link_identifier = null) {}

/**
 * Drop (delete) an mSQL database 
 *
 * @return bool
 * @param  database_name string
 * @param  link_identifier resource[optional]
 */
function msql_dropdb($database_name, $link_identifier = null) {}

/**
 * Returns the text of the error message from previous mSQL operation 
 *
 * @return string
 */
function msql_error() {}

/**
 * Fetch a result row as an associative array 
 *
 * @return array
 * @param  query resource
 * @param  result_type int[optional]
 */
function msql_fetch_array($query, $result_type = null) {}

/**
 * Get column information from a result and return as an object 
 *
 * @return object
 * @param  query resource
 * @param  field_offset int[optional]
 */
function msql_fetch_field($query, $field_offset = null) {}

/**
 * Fetch a result row as an object 
 *
 * @return object
 * @param  query resource
 * @param  result_type resource[optional]
 */
function msql_fetch_object($query, $result_type = null) {}

/**
 * Get a result row as an enumerated array 
 *
 * @return array
 * @param  query resource
 */
function msql_fetch_row($query) {}

/**
 * Get the flags associated with the specified field in a result 
 *
 * @return string
 * @param  query resource
 * @param  field_offset int
 */
function msql_field_flags($query, $field_offset) {}

/**
 * Returns the length of the specified field 
 *
 * @return int
 * @param  query int
 * @param  field_offet int
 */
function msql_field_len($query, $field_offet) {}

/**
 * Get the name of the specified field in a result 
 *
 * @return string
 * @param  query resource
 * @param  field_index int
 */
function msql_field_name($query, $field_index) {}

/**
 * Set result pointer to a specific field offset 
 *
 * @return bool
 * @param  query resource
 * @param  field_offset int
 */
function msql_field_seek($query, $field_offset) {}

/**
 * Get name of the table the specified field is in 
 *
 * @return int
 * @param  query resource
 * @param  field_offset int
 */
function msql_field_table($query, $field_offset) {}

/**
 * Get the type of the specified field in a result 
 *
 * @return string
 * @param  query resource
 * @param  field_offset int
 */
function msql_field_type($query, $field_offset) {}

/**
 * Get the flags associated with the specified field in a result 
 *
 * @return string
 * @param  query resource
 * @param  field_offset int
 */
function msql_fieldflags($query, $field_offset) {}

/**
 * Returns the length of the specified field 
 *
 * @return int
 * @param  query int
 * @param  field_offet int
 */
function msql_fieldlen($query, $field_offet) {}

/**
 * Get the name of the specified field in a result 
 *
 * @return string
 * @param  query resource
 * @param  field_index int
 */
function msql_fieldname($query, $field_index) {}

/**
 * Get name of the table the specified field is in 
 *
 * @return int
 * @param  query resource
 * @param  field_offset int
 */
function msql_fieldtable($query, $field_offset) {}

/**
 * Get the type of the specified field in a result 
 *
 * @return string
 * @param  query resource
 * @param  field_offset int
 */
function msql_fieldtype($query, $field_offset) {}

/**
 * Free result memory 
 *
 * @return bool
 * @param  query resource
 */
function msql_free_result($query) {}

/**
 * Free result memory 
 *
 * @return bool
 * @param  query resource
 */
function msql_freeresult($query) {}

/**
 * List databases available on an mSQL server 
 *
 * @return resource
 * @param  link_identifier resource[optional]
 */
function msql_list_dbs($link_identifier = null) {}

/**
 * List mSQL result fields 
 *
 * @return resource
 * @param  database_name string
 * @param  table_name string
 * @param  link_identifier resource[optional]
 */
function msql_list_fields($database_name, $table_name, $link_identifier = null) {}

/**
 * List tables in an mSQL database 
 *
 * @return resource
 * @param  database_name string
 * @param  link_identifier resource[optional]
 */
function msql_list_tables($database_name, $link_identifier = null) {}

/**
 * List databases available on an mSQL server 
 *
 * @return resource
 * @param  link_identifier resource[optional]
 */
function msql_listdbs($link_identifier = null) {}

/**
 * List mSQL result fields 
 *
 * @return resource
 * @param  database_name string
 * @param  table_name string
 * @param  link_identifier resource[optional]
 */
function msql_listfields($database_name, $table_name, $link_identifier = null) {}

/**
 * List tables in an mSQL database 
 *
 * @return resource
 * @param  database_name string
 * @param  link_identifier resource[optional]
 */
function msql_listtables($database_name, $link_identifier = null) {}

/**
 * Get number of fields in a result 
 *
 * @return int
 * @param  query resource
 */
function msql_num_fields($query) {}

/**
 * Get number of rows in a result 
 *
 * @return int
 * @param  query resource
 */
function msql_num_rows($query) {}

/**
 * Get number of fields in a result 
 *
 * @return int
 * @param  query resource
 */
function msql_numfields($query) {}

/**
 * Get number of rows in a result 
 *
 * @return int
 * @param  query resource
 */
function msql_numrows($query) {}

/**
 * Open a persistent connection to an mSQL Server 
 *
 * @return resource
 * @param  hostname string[optional]
 * @param  username string[optional]
 * @param  password string[optional]
 */
function msql_pconnect($hostname = null, $username = null, $password = null) {}

/**
 * Send an SQL query to mSQL 
 *
 * @return resource
 * @param  query string
 * @param  link_identifier resource[optional]
 */
function msql_query($query, $link_identifier = null) {}

/**
 * Make regular expression for case insensitive match 
 *
 * @return string
 * @param  string string
 */
function msql_regcase($string) {}

/**
 * Get result data 
 *
 * @return string
 * @param  query int
 * @param  row int
 * @param  field mixed[optional]
 */
function msql_result($query, $row, $field = null) {}

/**
 * Select an mSQL database 
 *
 * @return bool
 * @param  database_name string
 * @param  link_identifier resource[optional]
 */
function msql_select_db($database_name, $link_identifier = null) {}

/**
 * Select an mSQL database 
 *
 * @return bool
 * @param  database_name string
 * @param  link_identifier resource[optional]
 */
function msql_selectdb($database_name, $link_identifier = null) {}

/**
 * Get result data 
 *
 * @return string
 * @param  query int
 * @param  row int
 * @param  field mixed[optional]
 */
function msql_tablename($query, $row, $field = null) {}

/**
 * Get number of affected rows in last query 
 *
 * @return int
 * @param  link_id int[optional]
 */
function mssql_affected_rows($link_id = null) {}

/**
 * Adds a parameter to a stored procedure or a remote stored procedure  
 *
 * @return bool
 * @param  stmt resource
 * @param  param_name string
 * @param  var mixed
 * @param  type int
 * @param  is_output int[optional]
 * @param  is_null int[optional]
 * @param  maxlen int[optional]
 */
function mssql_bind($stmt, $param_name, $var, $type, $is_output = null, $is_null = null, $maxlen = null) {}

/**
 * Closes a connection to a MS-SQL server 
 *
 * @return bool
 * @param  conn_id resource[optional]
 */
function mssql_close($conn_id = null) {}

/**
 * Establishes a connection to a MS-SQL server 
 *
 * @return resource
 * @param  servername string[optional]
 * @param  username string[optional]
 * @param  password string[optional]
 * @param  new_link bool[optional]
 */
function mssql_connect($servername = null, $username = null, $password = null, $new_link = null) {}

/**
 * Moves the internal row pointer of the MS-SQL result associated with the specified result identifier to pointer to the specified row number 
 *
 * @return bool
 * @param  result_id resource
 * @param  offset int
 */
function mssql_data_seek($result_id, $offset) {}

/**
 * Sets deadlock retry count 
 *
 * @return void
 * @param  retry_count int
 */
function mssql_deadlock_retry_count($retry_count) {}

/**
 * Executes a stored procedure on a MS-SQL server database 
 *
 * @return mixed
 * @param  stmt resource
 * @param  skip_results bool[optional]
 */
function mssql_execute($stmt, $skip_results = false) {}

/**
 * Returns an associative array of the current row in the result set specified by result_id 
 *
 * @return array
 * @param  result_id resource
 * @param  result_type int[optional]
 */
function mssql_fetch_array($result_id, $result_type = null) {}

/**
 * Returns an associative array of the current row in the result set specified by result_id 
 *
 * @return array
 * @param  result_id resource
 */
function mssql_fetch_assoc($result_id) {}

/**
 * Returns the next batch of records 
 *
 * @return int
 * @param  result_index resource
 */
function mssql_fetch_batch($result_index) {}

/**
 * Gets information about certain fields in a query result 
 *
 * @return object
 * @param  result_id resource
 * @param  offset int[optional]
 */
function mssql_fetch_field($result_id, $offset = null) {}

/**
 * Returns a psuedo-object of the current row in the result set specified by result_id 
 *
 * @return object
 * @param  result_id resource
 * @param  result_type int[optional]
 */
function mssql_fetch_object($result_id, $result_type = null) {}

/**
 * Returns an array of the current row in the result set specified by result_id 
 *
 * @return array
 * @param  result_id resource
 */
function mssql_fetch_row($result_id) {}

/**
 * Get the length of a MS-SQL field 
 *
 * @return int
 * @param  result_id resource
 * @param  offset int[optional]
 */
function mssql_field_length($result_id, $offset = null) {}

/**
 * Returns the name of the field given by offset in the result set given by result_id 
 *
 * @return string
 * @param  result_id resource
 * @param  offset int[optional]
 */
function mssql_field_name($result_id, $offset = null) {}

/**
 * Seeks to the specified field offset 
 *
 * @return bool
 * @param  result_id int
 * @param  offset int
 */
function mssql_field_seek($result_id, $offset) {}

/**
 * Returns the type of a field 
 *
 * @return string
 * @param  result_id resource
 * @param  offset int[optional]
 */
function mssql_field_type($result_id, $offset = null) {}

/**
 * Free a MS-SQL result index 
 *
 * @return bool
 * @param  result_index resource
 */
function mssql_free_result($result_index) {}

/**
 * Free a MS-SQL statement index 
 *
 * @return bool
 * @param  result_index resource
 */
function mssql_free_statement($result_index) {}

/**
 * Gets the last message from the MS-SQL server 
 *
 * @return string
 */
function mssql_get_last_message() {}

/**
 * Converts a 16 byte binary GUID to a string  
 *
 * @return string
 * @param  binary string
 * @param  short_format int[optional]
 */
function mssql_guid_string($binary, $short_format = null) {}

/**
 * Initializes a stored procedure or a remote stored procedure  
 *
 * @return resource
 * @param  sp_name string
 * @param  conn_id resource[optional]
 */
function mssql_init($sp_name, $conn_id = null) {}

/**
 * Sets minimum client severity 
 *
 * @return void
 * @param  severity int
 */
function mssql_min_client_severity($severity) {}

/**
 * Sets the lower error severity 
 *
 * @return void
 * @param  severity int
 */
function mssql_min_error_severity($severity) {}

/**
 * Sets the lower message severity 
 *
 * @return void
 * @param  severity int
 */
function mssql_min_message_severity($severity) {}

/**
 * Sets minimum server severity 
 *
 * @return void
 * @param  severity int
 */
function mssql_min_server_severity($severity) {}

/**
 * Move the internal result pointer to the next result 
 *
 * @return bool
 * @param  result_id resource
 */
function mssql_next_result($result_id) {}

/**
 * Returns the number of fields fetched in from the result id specified 
 *
 * @return int
 * @param  mssql_result_index resource
 */
function mssql_num_fields($mssql_result_index) {}

/**
 * Returns the number of rows fetched in from the result id specified 
 *
 * @return int
 * @param  mssql_result_index resource
 */
function mssql_num_rows($mssql_result_index) {}

/**
 * Establishes a persistent connection to a MS-SQL server 
 *
 * @return resource
 * @param  servername string[optional]
 * @param  username string[optional]
 * @param  password string[optional]
 * @param  new_link bool[optional]
 */
function mssql_pconnect($servername = null, $username = null, $password = null, $new_link = null) {}

/**
 * Perform an SQL query on a MS-SQL server database 
 *
 * @return mixed
 * @param  query string
 * @param  conn_id resource[optional]
 * @param  batch_size int[optional]
 */
function mssql_query($query, $conn_id = null, $batch_size = null) {}

/**
 * Returns the contents of one cell from a MS-SQL result set 
 *
 * @return string
 * @param  result_id resource
 * @param  row int
 * @param  field mixed
 */
function mssql_result($result_id, $row, $field) {}

/**
 * Returns the number of records affected by the query 
 *
 * @return int
 * @param  conn_id resource
 */
function mssql_rows_affected($conn_id) {}

/**
 * Select a MS-SQL database 
 *
 * @return bool
 * @param  database_name string
 * @param  conn_id resource[optional]
 */
function mssql_select_db($database_name, $conn_id = null) {}

/**
 * 
 *
 * @return bool
 * @param  error_func mixed
 * @param  connection resource[optional]
 */
function mssql_set_message_handler($error_func, $connection = null) {}

/**
 * Send Sybase query 
 *
 * @return resource
 * @param  query string
 * @param  link_id int[optional]
 */
function mssql_unbuffered_query($query, $link_id = null) {}

/**
 * Returns the maximum value a random number from Mersenne Twister can have 
 *
 * @return int
 */
function mt_getrandmax() {}

/**
 * Returns a random number from Mersenne Twister 
 *
 * @return int
 * @param  min int[optional]
 * @param  max int
 */
function mt_rand($min = null, $max) {}

/**
 * Seeds Mersenne Twister random number generator 
 *
 * @return void
 * @param  seed int[optional]
 */
function mt_srand($seed = null) {}

/**
 * Gets number of affected rows in previous MySQL operation 
 *
 * @return int
 * @param  link_identifier int[optional]
 */
function mysql_affected_rows($link_identifier = null) {}

/**
 * Returns the default character set for the current connection 
 *
 * @return string
 * @param  link_identifier int[optional]
 */
function mysql_client_encoding($link_identifier = null) {}

/**
 * Close a MySQL connection 
 *
 * @return bool
 * @param  link_identifier int[optional]
 */
function mysql_close($link_identifier = null) {}

/**
 * Opens a connection to a MySQL Server 
 *
 * @return resource
 * @param  hostname string[optional]
 * @param  username string[optional]
 * @param  password string[optional]
 * @param  new bool[optional]
 * @param  flags int[optional]
 */
function mysql_connect($hostname = null, $username = null, $password = null, $new = null, $flags = null) {}

/**
 * Create a MySQL database 
 *
 * @return bool
 * @param  database_name string
 * @param  link_identifier int[optional]
 */
function mysql_create_db($database_name, $link_identifier = null) {}

/**
 * Create a MySQL database 
 *
 * @return bool
 * @param  database_name string
 * @param  link_identifier int[optional]
 */
function mysql_createdb($database_name, $link_identifier = null) {}

/**
 * Move internal result pointer 
 *
 * @return bool
 * @param  result resource
 * @param  row_number int
 */
function mysql_data_seek($result, $row_number) {}

/**
 * Gets result data 
 *
 * @return string
 * @param  result resource
 * @param  row int
 * @param  field mixed[optional]
 */
function mysql_db_name($result, $row, $field = null) {}

/**
 * Sends an SQL query to MySQL 
 *
 * @return resource
 * @param  database_name string
 * @param  query string
 * @param  link_identifier int[optional]
 */
function mysql_db_query($database_name, $query, $link_identifier = null) {}

/**
 * Gets result data 
 *
 * @return string
 * @param  result resource
 * @param  row int
 * @param  field mixed[optional]
 */
function mysql_dbname($result, $row, $field = null) {}

/**
 * Drops (delete) a MySQL database 
 *
 * @return bool
 * @param  database_name string
 * @param  link_identifier int[optional]
 */
function mysql_drop_db($database_name, $link_identifier = null) {}

/**
 * Drops (delete) a MySQL database 
 *
 * @return bool
 * @param  database_name string
 * @param  link_identifier int[optional]
 */
function mysql_dropdb($database_name, $link_identifier = null) {}

/**
 * Returns the number of the error message from previous MySQL operation 
 *
 * @return int
 * @param  link_identifier int[optional]
 */
function mysql_errno($link_identifier = null) {}

/**
 * Returns the text of the error message from previous MySQL operation 
 *
 * @return string
 * @param  link_identifier int[optional]
 */
function mysql_error($link_identifier = null) {}

/**
 * Escape string for mysql query 
 *
 * @return string
 * @param  to_be_escaped string
 */
function mysql_escape_string($to_be_escaped) {}

/**
 * Fetch a result row as an array (associative, numeric or both) 
 *
 * @return array
 * @param  result resource
 * @param  result_type int[optional]
 */
function mysql_fetch_array($result, $result_type = null) {}

/**
 * Fetch a result row as an associative array 
 *
 * @return array
 * @param  result resource
 */
function mysql_fetch_assoc($result) {}

/**
 * Gets column information from a result and return as an object 
 *
 * @return object
 * @param  result resource
 * @param  field_offset int[optional]
 */
function mysql_fetch_field($result, $field_offset = null) {}

/**
 * Gets max data size of each column in a result 
 *
 * @return array
 * @param  result resource
 */
function mysql_fetch_lengths($result) {}

/**
 * Fetch a result row as an object 
 *
 * @return object
 * @param  result resource
 * @param  class_name string[optional]
 * @param  ctor_params NULL|array[optional]
 */
function mysql_fetch_object($result, $class_name = null, $ctor_params = null) {}

/**
 * Gets a result row as an enumerated array 
 *
 * @return array
 * @param  result resource
 */
function mysql_fetch_row($result) {}

/**
 * Gets the flags associated with the specified field in a result 
 *
 * @return string
 * @param  result resource
 * @param  field_offset int
 */
function mysql_field_flags($result, $field_offset) {}

/**
 * Returns the length of the specified field 
 *
 * @return int
 * @param  result resource
 * @param  field_offset int
 */
function mysql_field_len($result, $field_offset) {}

/**
 * Gets the name of the specified field in a result 
 *
 * @return string
 * @param  result resource
 * @param  field_index int
 */
function mysql_field_name($result, $field_index) {}

/**
 * Sets result pointer to a specific field offset 
 *
 * @return bool
 * @param  result resource
 * @param  field_offset int
 */
function mysql_field_seek($result, $field_offset) {}

/**
 * Gets name of the table the specified field is in 
 *
 * @return string
 * @param  result resource
 * @param  field_offset int
 */
function mysql_field_table($result, $field_offset) {}

/**
 * Gets the type of the specified field in a result 
 *
 * @return string
 * @param  result resource
 * @param  field_offset int
 */
function mysql_field_type($result, $field_offset) {}

/**
 * Gets the flags associated with the specified field in a result 
 *
 * @return string
 * @param  result resource
 * @param  field_offset int
 */
function mysql_fieldflags($result, $field_offset) {}

/**
 * Returns the length of the specified field 
 *
 * @return int
 * @param  result resource
 * @param  field_offset int
 */
function mysql_fieldlen($result, $field_offset) {}

/**
 * Gets the name of the specified field in a result 
 *
 * @return string
 * @param  result resource
 * @param  field_index int
 */
function mysql_fieldname($result, $field_index) {}

/**
 * Gets name of the table the specified field is in 
 *
 * @return string
 * @param  result resource
 * @param  field_offset int
 */
function mysql_fieldtable($result, $field_offset) {}

/**
 * Gets the type of the specified field in a result 
 *
 * @return string
 * @param  result resource
 * @param  field_offset int
 */
function mysql_fieldtype($result, $field_offset) {}

/**
 * Free result memory 
 *
 * @return bool
 * @param  result resource
 */
function mysql_free_result($result) {}

/**
 * Free result memory 
 *
 * @return bool
 * @param  result resource
 */
function mysql_freeresult($result) {}

/**
 * Returns a string that represents the client library version 
 *
 * @return string
 */
function mysql_get_client_info() {}

/**
 * Returns a string describing the type of connection in use, including the server host name 
 *
 * @return string
 * @param  link_identifier int[optional]
 */
function mysql_get_host_info($link_identifier = null) {}

/**
 * Returns the protocol version used by current connection 
 *
 * @return int
 * @param  link_identifier int[optional]
 */
function mysql_get_proto_info($link_identifier = null) {}

/**
 * Returns a string that represents the server version number 
 *
 * @return string
 * @param  link_identifier int[optional]
 */
function mysql_get_server_info($link_identifier = null) {}

/**
 * Returns a string containing information about the most recent query 
 *
 * @return string
 * @param  link_identifier int[optional]
 */
function mysql_info($link_identifier = null) {}

/**
 * Gets the ID generated from the previous INSERT operation 
 *
 * @return int
 * @param  link_identifier int[optional]
 */
function mysql_insert_id($link_identifier = null) {}

/**
 * List databases available on a MySQL server 
 *
 * @return resource
 * @param  link_identifier int[optional]
 */
function mysql_list_dbs($link_identifier = null) {}

/**
 * List MySQL result fields 
 *
 * @return resource
 * @param  database_name string
 * @param  table_name string
 * @param  link_identifier int[optional]
 */
function mysql_list_fields($database_name, $table_name, $link_identifier = null) {}

/**
 * Returns a result set describing the current server threads 
 *
 * @return resource
 * @param  link_identifier int[optional]
 */
function mysql_list_processes($link_identifier = null) {}

/**
 * List tables in a MySQL database 
 *
 * @return resource
 * @param  database_name string
 * @param  link_identifier int[optional]
 */
function mysql_list_tables($database_name, $link_identifier = null) {}

/**
 * List databases available on a MySQL server 
 *
 * @return resource
 * @param  link_identifier int[optional]
 */
function mysql_listdbs($link_identifier = null) {}

/**
 * List MySQL result fields 
 *
 * @return resource
 * @param  database_name string
 * @param  table_name string
 * @param  link_identifier int[optional]
 */
function mysql_listfields($database_name, $table_name, $link_identifier = null) {}

/**
 * List tables in a MySQL database 
 *
 * @return resource
 * @param  database_name string
 * @param  link_identifier int[optional]
 */
function mysql_listtables($database_name, $link_identifier = null) {}

/**
 * Gets number of fields in a result 
 *
 * @return int
 * @param  result resource
 */
function mysql_num_fields($result) {}

/**
 * Gets number of rows in a result 
 *
 * @return int
 * @param  result resource
 */
function mysql_num_rows($result) {}

/**
 * Gets number of fields in a result 
 *
 * @return int
 * @param  result resource
 */
function mysql_numfields($result) {}

/**
 * Gets number of rows in a result 
 *
 * @return int
 * @param  result resource
 */
function mysql_numrows($result) {}

/**
 * Opens a persistent connection to a MySQL Server 
 *
 * @return resource
 * @param  hostname string[optional]
 * @param  username string[optional]
 * @param  password string[optional]
 * @param  flags int[optional]
 */
function mysql_pconnect($hostname = null, $username = null, $password = null, $flags = null) {}

/**
 * Ping a server connection. If no connection then reconnect. 
 *
 * @return bool
 * @param  link_identifier int[optional]
 */
function mysql_ping($link_identifier = null) {}

/**
 * Sends an SQL query to MySQL 
 *
 * @return resource
 * @param  query string
 * @param  link_identifier int[optional]
 */
function mysql_query($query, $link_identifier = null) {}

/**
 * Escape special characters in a string for use in a SQL statement, taking into account the current charset of the connection 
 *
 * @return string
 * @param  to_be_escaped string
 * @param  link_identifier int[optional]
 */
function mysql_real_escape_string($to_be_escaped, $link_identifier = null) {}

/**
 * Gets result data 
 *
 * @return string
 * @param  result resource
 * @param  row int
 * @param  field mixed[optional]
 */
function mysql_result($result, $row, $field = null) {}

/**
 * Selects a MySQL database 
 *
 * @return bool
 * @param  database_name string
 * @param  link_identifier int[optional]
 */
function mysql_select_db($database_name, $link_identifier = null) {}

/**
 * Selects a MySQL database 
 *
 * @return bool
 * @param  database_name string
 * @param  link_identifier int[optional]
 */
function mysql_selectdb($database_name, $link_identifier = null) {}

/**
 * Returns a string containing status information 
 *
 * @return string
 * @param  link_identifier int[optional]
 */
function mysql_stat($link_identifier = null) {}

/**
 * Gets result data 
 *
 * @return string
 * @param  result resource
 * @param  row int
 * @param  field mixed[optional]
 */
function mysql_table_name($result, $row, $field = null) {}

/**
 * Gets result data 
 *
 * @return string
 * @param  result resource
 * @param  row int
 * @param  field mixed[optional]
 */
function mysql_tablename($result, $row, $field = null) {}

/**
 * Returns the thread id of current connection 
 *
 * @return int
 * @param  link_identifier int[optional]
 */
function mysql_thread_id($link_identifier = null) {}

/**
 * Sends an SQL query to MySQL, without fetching and buffering the result rows 
 *
 * @return resource
 * @param  query string
 * @param  link_identifier int[optional]
 */
function mysql_unbuffered_query($query, $link_identifier = null) {}

/**
 * Get number of affected rows in previous MySQL operation 
 *
 * @return int
 * @param  link object
 */
function mysqli_affected_rows($link) {}

/**
 * Turn auto commit on or of 
 *
 * @return bool
 * @param  link object
 * @param  mode bool
 */
function mysqli_autocommit($link, $mode) {}

/**
 * Bind variables to a prepared statement as parameters 
 *
 * @return bool
 * @param  stmt object
 * @param  types string
 * @param  variable mixed
 * @param  mixed unknown[optional]
 * @vararg ...
 */
function mysqli_bind_param($stmt, $types, $variable, $mixed = null) {}

/**
 * Bind variables to a prepared statement for result storage 
 *
 * @return bool
 * @param  stmt object
 * @param  var mixed
 * @param  mixed unknown[optional]
 * @vararg ...
 */
function mysqli_bind_result($stmt, $var, $mixed = null) {}

/**
 * Change logged-in user of the active connection 
 *
 * @return bool
 * @param  link object
 * @param  user string
 * @param  password string
 * @param  database string
 */
function mysqli_change_user($link, $user, $password, $database) {}

/**
 * Returns the name of the character set used for this connection 
 *
 * @return string
 * @param  link object
 */
function mysqli_character_set_name($link) {}

/**
 * Returns the name of the character set used for this connection 
 *
 * @return string
 * @param  link object
 */
function mysqli_client_encoding($link) {}

/**
 * Close connection 
 *
 * @return bool
 * @param  link object
 */
function mysqli_close($link) {}

/**
 * Commit outstanding actions and close transaction 
 *
 * @return bool
 * @param  link object
 */
function mysqli_commit($link) {}

/**
 * Open a connection to a mysql server 
 *
 * @return mysqli
 * @param  hostname string[optional]
 * @param  username string[optional]
 * @param  passwd string[optional]
 * @param  dbname string[optional]
 * @param  port int[optional]
 * @param  socket string[optional]
 */
function mysqli_connect($hostname = null, $username = null, $passwd = null, $dbname = null, $port = null, $socket = null) {}

/**
 * Returns the numerical value of the error message from last connect command 
 *
 * @return int
 */
function mysqli_connect_errno() {}

/**
 * Returns the text of the error message from previous MySQL operation 
 *
 * @return string
 */
function mysqli_connect_error() {}

/**
 * Move internal result pointer 
 *
 * @return bool
 * @param  result object
 * @param  offset int
 */
function mysqli_data_seek($result, $offset) {}

/**
 * 
 *
 * @return bool
 * @param  debug string
 */
function mysqli_debug($debug) {}

/**
 * 
 *
 * @return bool
 * @param  link object
 */
function mysqli_disable_reads_from_master($link) {}

/**
 * 
 *
 * @return bool
 * @param  link object
 */
function mysqli_disable_rpl_parse($link) {}

/**
 * 
 *
 * @return bool
 * @param  link object
 */
function mysqli_dump_debug_info($link) {}

/**
 * 
 *
 * @return void
 */
function mysqli_embedded_server_end() {}

/**
 * initialize and start embedded server 
 *
 * @return bool
 * @param  start bool
 * @param  arguments array
 * @param  groups array
 */
function mysqli_embedded_server_start($start, $arguments, $groups) {}

/**
 * 
 *
 * @return bool
 * @param  link object
 */
function mysqli_enable_reads_from_master($link) {}

/**
 * 
 *
 * @return bool
 * @param  link object
 */
function mysqli_enable_rpl_parse($link) {}

/**
 * Returns the numerical value of the error message from previous MySQL operation 
 *
 * @return int
 * @param  link object
 */
function mysqli_errno($link) {}

/**
 * Returns the text of the error message from previous MySQL operation 
 *
 * @return string
 * @param  link object
 */
function mysqli_error($link) {}

/**
 * Escapes special characters in a string for use in a SQL statement, taking into account the current charset of the connection 
 *
 * @return string
 * @param  link object
 * @param  escapestr string
 */
function mysqli_escape_string($link, $escapestr) {}

/**
 * Execute a prepared statement 
 *
 * @return bool
 * @param  stmt object
 */
function mysqli_execute($stmt) {}

/**
 * Fetch results from a prepared statement into the bound variables 
 *
 * @return bool
 * @param  stmt object
 */
function mysqli_fetch($stmt) {}

/**
 * Fetch a result row as an associative array, a numeric array, or both 
 *
 * @return mixed
 * @param  result object
 * @param  resulttype int[optional]
 */
function mysqli_fetch_array($result, $resulttype = null) {}

/**
 * Fetch a result row as an associative array 
 *
 * @return array
 * @param  result object
 */
function mysqli_fetch_assoc($result) {}

/**
 * Get column information from a result and return as an object 
 *
 * @return object
 * @param  result object
 */
function mysqli_fetch_field($result) {}

/**
 * Fetch meta-data for a single field 
 *
 * @return object
 * @param  result object
 * @param  offset int
 */
function mysqli_fetch_field_direct($result, $offset) {}

/**
 * Return array of objects containing field meta-data 
 *
 * @return array
 * @param  result object
 */
function mysqli_fetch_fields($result) {}

/**
 * Get the length of each output in a result 
 *
 * @return array
 * @param  result object
 */
function mysqli_fetch_lengths($result) {}

/**
 * Fetch a result row as an object 
 *
 * @return mixed
 * @param  result object
 * @param  class_name string[optional]
 * @param  ctor_params NULL|array[optional]
 */
function mysqli_fetch_object($result, $class_name = null, $ctor_params = null) {}

/**
 * Get a result row as an enumerated array 
 *
 * @return mixed
 * @param  result object
 */
function mysqli_fetch_row($result) {}

/**
 * 
 *
 * @return int
 * @param  link object
 */
function mysqli_field_count($link) {}

/**
 * 
 *
 * @return bool
 * @param  result object
 * @param  fieldnr int
 */
function mysqli_field_seek($result, $fieldnr) {}

/**
 * Get current field offset of result pointer 
 *
 * @return int
 * @param  result object
 */
function mysqli_field_tell($result) {}

/**
 * Free query result memory for the given result handle 
 *
 * @return void
 * @param  result object
 */
function mysqli_free_result($result) {}

/**
 * returns a character set object 
 *
 * @return object
 * @param  link object
 */
function mysqli_get_charset($link) {}

/**
 * Get MySQL client info 
 *
 * @return string
 */
function mysqli_get_client_info() {}

/**
 * Get MySQL client info 
 *
 * @return int
 */
function mysqli_get_client_version() {}

/**
 * Get MySQL host info 
 *
 * @return string
 * @param  link object
 */
function mysqli_get_host_info($link) {}

/**
 * return result set from statement 
 *
 * @return mysqli_result
 * @param  stmt object
 */
function mysqli_get_metadata($stmt) {}

/**
 * Get MySQL protocol information 
 *
 * @return int
 * @param  link object
 */
function mysqli_get_proto_info($link) {}

/**
 * Get MySQL server info 
 *
 * @return string
 * @param  link object
 */
function mysqli_get_server_info($link) {}

/**
 * Return the MySQL version for the server referenced by the given link 
 *
 * @return int
 * @param  link object
 */
function mysqli_get_server_version($link) {}

/**
 * 
 *
 * @return object
 * @param  link object
 */
function mysqli_get_warnings($link) {}

/**
 * Get information about the most recent query 
 *
 * @return string
 * @param  link object
 */
function mysqli_info($link) {}

/**
 * Initialize mysqli and return a resource for use with mysql_real_connect 
 *
 * @return mysqli
 */
function mysqli_init() {}

/**
 * Get the ID generated from the previous INSERT operation 
 *
 * @return int
 * @param  link object
 */
function mysqli_insert_id($link) {}

/**
 * Kill a mysql process on the server 
 *
 * @return bool
 * @param  link object
 * @param  processid int
 */
function mysqli_kill($link, $processid) {}

/**
 * Enforce execution of a query on the master in a master/slave setup 
 *
 * @return bool
 * @param  link object
 * @param  query string
 */
function mysqli_master_query($link, $query) {}

/**
 * check if there any more query results from a multi query 
 *
 * @return bool
 * @param  link object
 */
function mysqli_more_results($link) {}

/**
 * Binary-safe version of mysql_query() 
 *
 * @return bool
 * @param  link object
 * @param  query string
 */
function mysqli_multi_query($link, $query) {}

/**
 * read next result from multi_query 
 *
 * @return bool
 * @param  link object
 */
function mysqli_next_result($link) {}

/**
 * Get number of fields in result 
 *
 * @return int
 * @param  result object
 */
function mysqli_num_fields($result) {}

/**
 * Get number of rows in result 
 *
 * @return int
 * @param  result object
 */
function mysqli_num_rows($result) {}

/**
 * Set options 
 *
 * @return bool
 * @param  link object
 * @param  flags int
 * @param  values mixed
 */
function mysqli_options($link, $flags, $values) {}

/**
 * Return the number of parameter for the given statement 
 *
 * @return int
 * @param  stmt object
 */
function mysqli_param_count($stmt) {}

/**
 * Ping a server connection or reconnect if there is no connection 
 *
 * @return bool
 * @param  link object
 */
function mysqli_ping($link) {}

/**
 * Prepare a SQL statement for execution 
 *
 * @return mysqli_stmt
 * @param  link object
 * @param  query string
 */
function mysqli_prepare($link, $query) {}

/**
 * 
 *
 * @return mixed
 * @param  link object
 * @param  query string
 * @param  resultmode int[optional]
 */
function mysqli_query($link, $query, $resultmode = null) {}

/**
 * Open a connection to a mysql server 
 *
 * @return bool
 * @param  link object
 * @param  hostname string[optional]
 * @param  username string[optional]
 * @param  passwd string[optional]
 * @param  dbname string[optional]
 * @param  port int[optional]
 * @param  socket string[optional]
 * @param  flags int[optional]
 */
function mysqli_real_connect($link, $hostname = null, $username = null, $passwd = null, $dbname = null, $port = null, $socket = null, $flags = null) {}

/**
 * Escapes special characters in a string for use in a SQL statement, taking into account the current charset of the connection 
 *
 * @return string
 * @param  link object
 * @param  escapestr string
 */
function mysqli_real_escape_string($link, $escapestr) {}

/**
 * Binary-safe version of mysql_query() 
 *
 * @return bool
 * @param  link object
 * @param  query string
 */
function mysqli_real_query($link, $query) {}

/**
 * Undo actions from current transaction 
 *
 * @return bool
 * @param  link object
 */
function mysqli_rollback($link) {}

/**
 * 
 *
 * @return int
 * @param  link object
 */
function mysqli_rpl_parse_enabled($link) {}

/**
 * 
 *
 * @return bool
 * @param  link object
 */
function mysqli_rpl_probe($link) {}

/**
 * 
 *
 * @return int
 * @param  query string
 */
function mysqli_rpl_query_type($query) {}

/**
 * Select a MySQL database 
 *
 * @return bool
 * @param  link object
 * @param  dbname string
 */
function mysqli_select_db($link, $dbname) {}

/**
 * 
 *
 * @return bool
 * @param  stmt object
 * @param  param_nr int
 * @param  data string
 */
function mysqli_send_long_data($stmt, $param_nr, $data) {}

/**
 * 
 *
 * @return bool
 * @param  link object
 * @param  query string
 */
function mysqli_send_query($link, $query) {}

/**
 * sets client character set 
 *
 * @return bool
 * @param  link object
 * @param  csname string
 */
function mysqli_set_charset($link, $csname) {}

/**
 * unsets user defined handler for load local infile command 
 *
 * @return unknown
 * @param  link object
 */
function mysqli_set_local_infile_default($link) {}

/**
 * Set callback functions for LOAD DATA LOCAL INFILE 
 *
 * @return bool
 * @param  link object
 * @param  read_func callback
 */
function mysqli_set_local_infile_handler($link, $read_func) {}

/**
 * Set options 
 *
 * @return bool
 * @param  link object
 * @param  flags int
 * @param  values mixed
 */
function mysqli_set_opt($link, $flags, $values) {}

/**
 * Enforce execution of a query on a slave in a master/slave setup 
 *
 * @return bool
 * @param  link object
 * @param  query string
 */
function mysqli_slave_query($link, $query) {}

/**
 * Returns the SQLSTATE error from previous MySQL operation 
 *
 * @return string
 * @param  link object
 */
function mysqli_sqlstate($link) {}

/**
 * 
 *
 * @return bool
 * @param  link object
 * @param  key string
 * @param  cert string
 * @param  ca string
 * @param  capath string
 * @param  cipher string
 */
function mysqli_ssl_set($link, $key, $cert, $ca, $capath, $cipher) {}

/**
 * Get current system status 
 *
 * @return string
 * @param  link object
 */
function mysqli_stat($link) {}

/**
 * Return the number of rows affected in the last query for the given link 
 *
 * @return int
 * @param  stmt object
 */
function mysqli_stmt_affected_rows($stmt) {}

/**
 * 
 *
 * @return int
 * @param  stmt object
 * @param  attr long
 */
function mysqli_stmt_attr_get($stmt, $attr) {}

/**
 * 
 *
 * @return int
 * @param  stmt object
 * @param  attr long
 * @param  mode bool
 */
function mysqli_stmt_attr_set($stmt, $attr, $mode) {}

/**
 * Bind variables to a prepared statement as parameters 
 *
 * @return bool
 * @param  stmt object
 * @param  types string
 * @param  variable mixed
 * @param  mixed unknown[optional]
 * @vararg ...
 */
function mysqli_stmt_bind_param($stmt, $types, $variable, $mixed = null) {}

/**
 * Bind variables to a prepared statement for result storage 
 *
 * @return bool
 * @param  stmt object
 * @param  var mixed
 * @param  mixed unknown[optional]
 * @vararg ...
 */
function mysqli_stmt_bind_result($stmt, $var, $mixed = null) {}

/**
 * Close statement 
 *
 * @return bool
 * @param  stmt object
 */
function mysqli_stmt_close($stmt) {}

/**
 * Move internal result pointer 
 *
 * @return void
 * @param  stmt object
 * @param  offset int
 */
function mysqli_stmt_data_seek($stmt, $offset) {}

/**
 * 
 *
 * @return int
 * @param  stmt object
 */
function mysqli_stmt_errno($stmt) {}

/**
 * 
 *
 * @return string
 * @param  stmt object
 */
function mysqli_stmt_error($stmt) {}

/**
 * Execute a prepared statement 
 *
 * @return bool
 * @param  stmt object
 */
function mysqli_stmt_execute($stmt) {}

/**
 * Fetch results from a prepared statement into the bound variables 
 *
 * @return bool
 * @param  stmt object
 */
function mysqli_stmt_fetch($stmt) {}

/**
 * Return the number of result columns for the given statement 
 *
 * @return int
 * @param  stmt object
 */
function mysqli_stmt_field_count($stmt) {}

/**
 * Free stored result memory for the given statement handle 
 *
 * @return void
 * @param  stmt object
 */
function mysqli_stmt_free_result($stmt) {}

/**
 * 
 *
 * @return mysqli_stmt
 * @param  link object
 */
function mysqli_stmt_init($link) {}

/**
 * Get the ID generated from the previous INSERT operation 
 *
 * @return mixed
 * @param  stmt object
 */
function mysqli_stmt_insert_id($stmt) {}

/**
 * Return the number of rows in statements result set 
 *
 * @return int
 * @param  stmt object
 */
function mysqli_stmt_num_rows($stmt) {}

/**
 * Return the number of parameter for the given statement 
 *
 * @return int
 * @param  stmt object
 */
function mysqli_stmt_param_count($stmt) {}

/**
 * 
 *
 * @return bool
 * @param  stmt object
 * @param  query string
 */
function mysqli_stmt_prepare($stmt, $query) {}

/**
 * reset a prepared statement 
 *
 * @return bool
 * @param  stmt object
 */
function mysqli_stmt_reset($stmt) {}

/**
 * return result set from statement 
 *
 * @return mysqli_result
 * @param  stmt object
 */
function mysqli_stmt_result_metadata($stmt) {}

/**
 * 
 *
 * @return string
 * @param  stmt object
 */
function mysqli_stmt_sqlstate($stmt) {}

/**
 * 
 *
 * @return bool
 * @param  stmt unknown
 */
function mysqli_stmt_store_result($stmt) {}

/**
 * Buffer result set on client 
 *
 * @return mysqli_result
 * @param  link object
 */
function mysqli_store_result($link) {}

/**
 * Return the current thread ID 
 *
 * @return int
 * @param  link object
 */
function mysqli_thread_id($link) {}

/**
 * Return whether thread safety is given or not 
 *
 * @return bool
 */
function mysqli_thread_safe() {}

/**
 * Directly retrieve query results - do not buffer results on client side 
 *
 * @return mysqli_result
 * @param  link object
 */
function mysqli_use_result($link) {}

/**
 * Return number of warnings from the last query for the given link 
 *
 * @return int
 * @param  link object
 */
function mysqli_warning_count($link) {}

/**
 * Sort an array using case-insensitive natural sort 
 *
 * @return bool
 * @param  array_arg array
 */
function natcasesort($array_arg) {}

/**
 * Sort an array using natural sort 
 *
 * @return bool
 * @param  array_arg array
 */
function natsort($array_arg) {}

/**
 * Adds character at current position and advance cursor 
 *
 * @return int
 * @param  ch int
 */
function ncurses_addch($ch) {}

/**
 * Adds attributed string with specified length at current position 
 *
 * @return int
 * @param  s string
 * @param  n int
 */
function ncurses_addchnstr($s, $n) {}

/**
 * Adds attributed string at current position 
 *
 * @return int
 * @param  s string
 */
function ncurses_addchstr($s) {}

/**
 * Adds string with specified length at current position 
 *
 * @return int
 * @param  s string
 * @param  n int
 */
function ncurses_addnstr($s, $n) {}

/**
 * Outputs text at current position 
 *
 * @return int
 * @param  text string
 */
function ncurses_addstr($text) {}

/**
 * Defines default colors for color 0 
 *
 * @return int
 * @param  fg int
 * @param  bg int
 */
function ncurses_assume_default_colors($fg, $bg) {}

/**
 * Turns off the given attributes 
 *
 * @return int
 * @param  attributes int
 */
function ncurses_attroff($attributes) {}

/**
 * Turns on the given attributes 
 *
 * @return int
 * @param  attributes int
 */
function ncurses_attron($attributes) {}

/**
 * Sets given attributes 
 *
 * @return int
 * @param  attributes int
 */
function ncurses_attrset($attributes) {}

/**
 * Returns baudrate of terminal 
 *
 * @return int
 */
function ncurses_baudrate() {}

/**
 * Let the terminal beep 
 *
 * @return int
 */
function ncurses_beep() {}

/**
 * Sets background property for terminal screen 
 *
 * @return int
 * @param  attrchar int
 */
function ncurses_bkgd($attrchar) {}

/**
 * Controls screen background 
 *
 * @return void
 * @param  attrchar int
 */
function ncurses_bkgdset($attrchar) {}

/**
 * Draws a border around the screen using attributed characters 
 *
 * @return int
 * @param  left int
 * @param  right int
 * @param  top int
 * @param  bottom int
 * @param  tl_corner int
 * @param  tr_corner int
 * @param  bl_corner int
 * @param  br_corner int
 */
function ncurses_border($left, $right, $top, $bottom, $tl_corner, $tr_corner, $bl_corner, $br_corner) {}

/**
 * Moves a visible panel to the bottom of the stack 
 *
 * @return int
 * @param  panel resource
 */
function ncurses_bottom_panel($panel) {}

/**
 * Checks if we can change terminals colors 
 *
 * @return bool
 */
function ncurses_can_change_color() {}

/**
 * Switches of input buffering 
 *
 * @return bool
 */
function ncurses_cbreak() {}

/**
 * Clears screen 
 *
 * @return bool
 */
function ncurses_clear() {}

/**
 * Clears screen from current position to bottom 
 *
 * @return bool
 */
function ncurses_clrtobot() {}

/**
 * Clears screen from current position to end of line 
 *
 * @return bool
 */
function ncurses_clrtoeol() {}

/**
 * Gets the RGB value for color 
 *
 * @return int
 * @param  color int
 * @param  r int
 * @param  g int
 * @param  b int
 */
function ncurses_color_content($color, &$r, &$g, &$b) {}

/**
 * Sets fore- and background color 
 *
 * @return int
 * @param  pair int
 */
function ncurses_color_set($pair) {}

/**
 * Sets cursor state 
 *
 * @return int
 * @param  visibility int
 */
function ncurses_curs_set($visibility) {}

/**
 * Saves terminals (program) mode 
 *
 * @return bool
 */
function ncurses_def_prog_mode() {}

/**
 * Saves terminal (shell) mode
 *
 * @return bool
 */
function ncurses_def_shell_mode() {}

/**
 * Defines a keycode 
 *
 * @return int
 * @param  definition string
 * @param  keycode int
 */
function ncurses_define_key($definition, $keycode) {}

/**
 * Remove panel from the stack and delete it (but not the associated window) 
 *
 * @return bool
 * @param  panel resource
 */
function ncurses_del_panel($panel) {}

/**
 * Delays output on terminal using padding characters 
 *
 * @return int
 * @param  milliseconds int
 */
function ncurses_delay_output($milliseconds) {}

/**
 * Deletes character at current position, move rest of line left 
 *
 * @return bool
 */
function ncurses_delch() {}

/**
 * Deletes line at current position, move rest of screen up 
 *
 * @return bool
 */
function ncurses_deleteln() {}

/**
 * Deletes a ncurses window 
 *
 * @return bool
 * @param  window resource
 */
function ncurses_delwin($window) {}

/**
 * Writes all prepared refreshes to terminal 
 *
 * @return bool
 */
function ncurses_doupdate() {}

/**
 * Activates keyboard input echo 
 *
 * @return bool
 */
function ncurses_echo() {}

/**
 * Single character output including refresh 
 *
 * @return int
 * @param  character int
 */
function ncurses_echochar($character) {}

/**
 * Stops using ncurses, clean up the screen 
 *
 * @return int
 */
function ncurses_end() {}

/**
 * Erases terminal screen 
 *
 * @return bool
 */
function ncurses_erase() {}

/**
 * Returns current erase character 
 *
 * @return string
 */
function ncurses_erasechar() {}

/**
 * 
 *
 * @return void
 */
function ncurses_filter() {}

/**
 * Flashes terminal screen (visual bell) 
 *
 * @return bool
 */
function ncurses_flash() {}

/**
 * Flushes keyboard input buffer 
 *
 * @return bool
 */
function ncurses_flushinp() {}

/**
 * Reads a character from keyboard 
 *
 * @return int
 */
function ncurses_getch() {}

/**
 * Returns the size of a window 
 *
 * @return void
 * @param  window resource
 * @param  y int
 * @param  x int
 */
function ncurses_getmaxyx($window, &$y, &$x) {}

/**
 * Reads mouse event from queue. The content of mevent is cleared before new data is added. 
 *
 * @return bool
 * @param  mevent array
 */
function ncurses_getmouse(&$mevent) {}

/**
 * Returns the current cursor position for a window 
 *
 * @return void
 * @param  window resource
 * @param  y int
 * @param  x int
 */
function ncurses_getyx($window, &$y, &$x) {}

/**
 * Puts terminal into halfdelay mode 
 *
 * @return int
 * @param  tenth int
 */
function ncurses_halfdelay($tenth) {}

/**
 * Checks if terminal has colors 
 *
 * @return bool
 */
function ncurses_has_colors() {}

/**
 * Checks for insert- and delete-capabilities 
 *
 * @return bool
 */
function ncurses_has_ic() {}

/**
 * Checks for line insert- and delete-capabilities 
 *
 * @return bool
 */
function ncurses_has_il() {}

/**
 * Checks for presence of a function key on terminal keyboard 
 *
 * @return int
 * @param  keycode int
 */
function ncurses_has_key($keycode) {}

/**
 * Remove panel from the stack, making it invisible 
 *
 * @return int
 * @param  panel resource
 */
function ncurses_hide_panel($panel) {}

/**
 * Draws a horizontal line at current position using an attributed character and max. n characters long 
 *
 * @return int
 * @param  charattr int
 * @param  n int
 */
function ncurses_hline($charattr, $n) {}

/**
 * Gets character and attribute at current position 
 *
 * @return string
 */
function ncurses_inch() {}

/**
 * Initializes ncurses 
 *
 * @return void
 */
function ncurses_init() {}

/**
 * Sets new RGB value for color 
 *
 * @return int
 * @param  color int
 * @param  r int
 * @param  g int
 * @param  b int
 */
function ncurses_init_color($color, $r, $g, $b) {}

/**
 * Allocates a color pair 
 *
 * @return int
 * @param  pair int
 * @param  fg int
 * @param  bg int
 */
function ncurses_init_pair($pair, $fg, $bg) {}

/**
 * Inserts character moving rest of line including character at current position 
 *
 * @return int
 * @param  character int
 */
function ncurses_insch($character) {}

/**
 * Inserts lines before current line scrolling down (negative numbers delete and scroll up) 
 *
 * @return int
 * @param  count int
 */
function ncurses_insdelln($count) {}

/**
 * Inserts a line, move rest of screen down 
 *
 * @return bool
 */
function ncurses_insertln() {}

/**
 * Inserts string at current position, moving rest of line right 
 *
 * @return int
 * @param  text string
 */
function ncurses_insstr($text) {}

/**
 * Reads string from terminal screen 
 *
 * @return int
 * @param  buffer string
 */
function ncurses_instr(&$buffer) {}

/**
 * Ncurses is in endwin mode, normal screen output may be performed 
 *
 * @return bool
 */
function ncurses_isendwin() {}

/**
 * Enables or disable a keycode 
 *
 * @return int
 * @param  keycode int
 * @param  enable int
 */
function ncurses_keyok($keycode, $enable) {}

/**
 * Turns keypad on or off 
 *
 * @return int
 * @param  window resource
 * @param  bf bool
 */
function ncurses_keypad($window, $bf) {}

/**
 * Returns current line kill character 
 *
 * @return string
 */
function ncurses_killchar() {}

/**
 * Returns terminal description 
 *
 * @return string
 */
function ncurses_longname() {}

/**
 * Enables/Disable 8-bit meta key information 
 *
 * @return int
 * @param  window resource
 * @param  _8bit bool
 */
function ncurses_meta($window, $_8bit) {}

/**
 * Transforms coordinates 
 *
 * @return bool
 * @param  y int
 * @param  x int
 * @param  toscreen bool
 */
function ncurses_mouse_trafo(&$y, &$x, $toscreen) {}

/**
 * Sets timeout for mouse button clicks 
 *
 * @return int
 * @param  milliseconds int
 */
function ncurses_mouseinterval($milliseconds) {}

/**
 * Returns and sets mouse options 
 *
 * @return int
 * @param  newmask int
 * @param  oldmask int
 */
function ncurses_mousemask($newmask, &$oldmask) {}

/**
 * Moves output position 
 *
 * @return int
 * @param  y int
 * @param  x int
 */
function ncurses_move($y, $x) {}

/**
 * Moves a panel so that it's upper-left corner is at [startx, starty] 
 *
 * @return int
 * @param  panel resource
 * @param  startx int
 * @param  starty int
 */
function ncurses_move_panel($panel, $startx, $starty) {}

/**
 * Moves current position and add character 
 *
 * @return int
 * @param  y int
 * @param  x int
 * @param  c int
 */
function ncurses_mvaddch($y, $x, $c) {}

/**
 * Moves position and add attrributed string with specified length 
 *
 * @return int
 * @param  y int
 * @param  x int
 * @param  s string
 * @param  n int
 */
function ncurses_mvaddchnstr($y, $x, $s, $n) {}

/**
 * Moves position and add attributed string 
 *
 * @return int
 * @param  y int
 * @param  x int
 * @param  s string
 */
function ncurses_mvaddchstr($y, $x, $s) {}

/**
 * Moves position and add string with specified length 
 *
 * @return int
 * @param  y int
 * @param  x int
 * @param  s string
 * @param  n int
 */
function ncurses_mvaddnstr($y, $x, $s, $n) {}

/**
 * Moves position and add string 
 *
 * @return int
 * @param  y int
 * @param  x int
 * @param  s string
 */
function ncurses_mvaddstr($y, $x, $s) {}

/**
 * Moves cursor immediately 
 *
 * @return int
 * @param  old_y int
 * @param  old_x int
 * @param  new_y int
 * @param  new_x int
 */
function ncurses_mvcur($old_y, $old_x, $new_y, $new_x) {}

/**
 * Moves position and delete character, shift rest of line left 
 *
 * @return int
 * @param  y int
 * @param  x int
 */
function ncurses_mvdelch($y, $x) {}

/**
 * Moves position and get character at new position 
 *
 * @return int
 * @param  y int
 * @param  x int
 */
function ncurses_mvgetch($y, $x) {}

/**
 * Sets new position and draw a horizontal line using an attributed character and max. n characters long 
 *
 * @return int
 * @param  y int
 * @param  x int
 * @param  attrchar int
 * @param  n int
 */
function ncurses_mvhline($y, $x, $attrchar, $n) {}

/**
 * Moves position and get attributed character at new position 
 *
 * @return int
 * @param  y int
 * @param  x int
 */
function ncurses_mvinch($y, $x) {}

/**
 * Sets new position and draw a vertical line using an attributed character and max. n characters long 
 *
 * @return int
 * @param  y int
 * @param  x int
 * @param  attrchar int
 * @param  n int
 */
function ncurses_mvvline($y, $x, $attrchar, $n) {}

/**
 * Adds string at new position in window 
 *
 * @return int
 * @param  window resource
 * @param  y int
 * @param  x int
 * @param  text string
 */
function ncurses_mvwaddstr($window, $y, $x, $text) {}

/**
 * Sleep 
 *
 * @return int
 * @param  milliseconds int
 */
function ncurses_napms($milliseconds) {}

/**
 * Create a new panel and associate it with window 
 *
 * @return resource
 * @param  window resource
 */
function ncurses_new_panel($window) {}

/**
 * Creates a new pad (window) 
 *
 * @return resource
 * @param  rows int
 * @param  cols int
 */
function ncurses_newpad($rows, $cols) {}

/**
 * Creates a new window 
 *
 * @return resource
 * @param  rows int
 * @param  cols int
 * @param  y int
 * @param  x int
 */
function ncurses_newwin($rows, $cols, $y, $x) {}

/**
 * Translates newline and carriage return / line feed 
 *
 * @return bool
 */
function ncurses_nl() {}

/**
 * Switches terminal to cooked mode 
 *
 * @return bool
 */
function ncurses_nocbreak() {}

/**
 * Switches off keyboard input echo 
 *
 * @return bool
 */
function ncurses_noecho() {}

/**
 * Do not ranslate newline and carriage return / line feed 
 *
 * @return bool
 */
function ncurses_nonl() {}

/**
 * Do not flush on signal characters
 *
 * @return void
 */
function ncurses_noqiflush() {}

/**
 * Switches terminal out of raw mode 
 *
 * @return bool
 */
function ncurses_noraw() {}

/**
 * Gets the RGB value for color 
 *
 * @return int
 * @param  pair int
 * @param  f int
 * @param  b int
 */
function ncurses_pair_content($pair, &$f, &$b) {}

/**
 * Returns the panel above panel. If panel is null, returns the bottom panel in the stack 
 *
 * @return resource
 * @param  panel resource
 */
function ncurses_panel_above($panel) {}

/**
 * Returns the panel below panel. If panel is null, returns the top panel in the stack 
 *
 * @return resource
 * @param  panel resource
 */
function ncurses_panel_below($panel) {}

/**
 * Returns the window associated with panel 
 *
 * @return resource
 * @param  panel resource
 */
function ncurses_panel_window($panel) {}

/**
 * Copys a region from a pad into the virtual screen 
 *
 * @return int
 * @param  pad resource
 * @param  pminrow int
 * @param  pmincol int
 * @param  sminrow int
 * @param  smincol int
 * @param  smaxrow int
 * @param  smaxcol int
 */
function ncurses_pnoutrefresh($pad, $pminrow, $pmincol, $sminrow, $smincol, $smaxrow, $smaxcol) {}

/**
 * Copys a region from a pad into the virtual screen 
 *
 * @return int
 * @param  pad resource
 * @param  pminrow int
 * @param  pmincol int
 * @param  sminrow int
 * @param  smincol int
 * @param  smaxrow int
 * @param  smaxcol int
 */
function ncurses_prefresh($pad, $pminrow, $pmincol, $sminrow, $smincol, $smaxrow, $smaxcol) {}

/**
 * ??? 
 *
 * @return int
 * @param  text string
 */
function ncurses_putp($text) {}

/**
 * Flushes on signal characters 
 *
 * @return void
 */
function ncurses_qiflush() {}

/**
 * Switches terminal into raw mode 
 *
 * @return bool
 */
function ncurses_raw() {}

/**
 * Refresh screen 
 *
 * @return int
 * @param  ch int
 */
function ncurses_refresh($ch) {}

/**
 * Replaces the window associated with panel 
 *
 * @return int
 * @param  panel resource
 * @param  window resource
 */
function ncurses_replace_panel($panel, $window) {}

/**
 * Resets the prog mode saved by def_prog_mode 
 *
 * @return int
 */
function ncurses_reset_prog_mode() {}

/**
 * Resets the shell mode saved by def_shell_mode 
 *
 * @return int
 */
function ncurses_reset_shell_mode() {}

/**
 * Restores saved terminal state 
 *
 * @return bool
 */
function ncurses_resetty() {}

/**
 * Saves terminal state 
 *
 * @return bool
 */
function ncurses_savetty() {}

/**
 * Dumps screen content to file 
 *
 * @return int
 * @param  filename string
 */
function ncurses_scr_dump($filename) {}

/**
 * Initializes screen from file dump 
 *
 * @return int
 * @param  filename string
 */
function ncurses_scr_init($filename) {}

/**
 * Restores screen from file dump 
 *
 * @return int
 * @param  filename string
 */
function ncurses_scr_restore($filename) {}

/**
 * Inherits screen from file dump 
 *
 * @return int
 * @param  filename string
 */
function ncurses_scr_set($filename) {}

/**
 * Scrolls window content up or down without changing current position 
 *
 * @return int
 * @param  count int
 */
function ncurses_scrl($count) {}

/**
 * Places an invisible panel on top of the stack, making it visible 
 *
 * @return int
 * @param  panel resource
 */
function ncurses_show_panel($panel) {}

/**
 * Returns current soft label keys attribute 
 *
 * @return bool
 */
function ncurses_slk_attr() {}

/**
 * ??? 
 *
 * @return int
 * @param  intarg int
 */
function ncurses_slk_attroff($intarg) {}

/**
 * ??? 
 *
 * @return int
 * @param  intarg int
 */
function ncurses_slk_attron($intarg) {}

/**
 * ??? 
 *
 * @return int
 * @param  intarg int
 */
function ncurses_slk_attrset($intarg) {}

/**
 * Clears soft label keys from screen 
 *
 * @return bool
 */
function ncurses_slk_clear() {}

/**
 * Sets color for soft label keys
 *
 * @return int
 * @param  intarg int
 */
function ncurses_slk_color($intarg) {}

/**
 * Inits soft label keys 
 *
 * @return bool
 * @param  intarg int
 */
function ncurses_slk_init($intarg) {}

/**
 * Copies soft label keys to virtual screen 
 *
 * @return bool
 */
function ncurses_slk_noutrefresh() {}

/**
 * Copies soft label keys to screen 
 *
 * @return bool
 */
function ncurses_slk_refresh() {}

/**
 * Restores soft label keys 
 *
 * @return bool
 */
function ncurses_slk_restore() {}

/**
 * Sets function key labels 
 *
 * @return bool
 * @param  labelnr int
 * @param  label string
 * @param  format int
 */
function ncurses_slk_set($labelnr, $label, $format) {}

/**
 * Forces output when ncurses_slk_noutrefresh is performed 
 *
 * @return bool
 */
function ncurses_slk_touch() {}

/**
 * Stops using 'standout' attribute 
 *
 * @return int
 */
function ncurses_standend() {}

/**
 * Starts using 'standout' attribute 
 *
 * @return int
 */
function ncurses_standout() {}

/**
 * Starts using colors 
 *
 * @return int
 */
function ncurses_start_color() {}

/**
 * Returns a logical OR of all attribute flags supported by terminal 
 *
 * @return bool
 */
function ncurses_termattrs() {}

/**
 * Returns terminal name 
 *
 * @return string
 */
function ncurses_termname() {}

/**
 * Sets timeout for special key sequences 
 *
 * @return void
 * @param  millisec int
 */
function ncurses_timeout($millisec) {}

/**
 * Moves a visible panel to the top of the stack 
 *
 * @return int
 * @param  panel resource
 */
function ncurses_top_panel($panel) {}

/**
 * Specifys different filedescriptor for typeahead checking 
 *
 * @return int
 * @param  fd int
 */
function ncurses_typeahead($fd) {}

/**
 * Puts a character back into the input stream 
 *
 * @return int
 * @param  keycode int
 */
function ncurses_ungetch($keycode) {}

/**
 * Pushes mouse event to queue 
 *
 * @return bool
 * @param  mevent array
 */
function ncurses_ungetmouse($mevent) {}

/**
 * Refreshes the virtual screen to reflect the relations between panels in the stack. 
 *
 * @return void
 */
function ncurses_update_panels() {}

/**
 * Assigns terminal default colors to color id -1 
 *
 * @return bool
 */
function ncurses_use_default_colors() {}

/**
 * Controls use of environment information about terminal size 
 *
 * @return void
 * @param  flag int
 */
function ncurses_use_env($flag) {}

/**
 * Controls use of extended names in terminfo descriptions 
 *
 * @return int
 * @param  flag bool
 */
function ncurses_use_extended_names($flag) {}

/**
 * ??? 
 *
 * @return int
 * @param  intarg int
 */
function ncurses_vidattr($intarg) {}

/**
 * Draws a vertical line at current position using an attributed character and max. n characters long 
 *
 * @return int
 * @param  charattr int
 * @param  n int
 */
function ncurses_vline($charattr, $n) {}

/**
 * Adds character at current position in a window and advance cursor 
 *
 * @return int
 * @param  window resource
 * @param  ch int
 */
function ncurses_waddch($window, $ch) {}

/**
 * Outputs text at current postion in window 
 *
 * @return int
 * @param  window resource
 * @param  str string
 * @param  n int[optional]
 */
function ncurses_waddstr($window, $str, $n = null) {}

/**
 * Turns off attributes for a window 
 *
 * @return int
 * @param  window resource
 * @param  attrs int
 */
function ncurses_wattroff($window, $attrs) {}

/**
 * Turns on attributes for a window 
 *
 * @return int
 * @param  window resource
 * @param  attrs int
 */
function ncurses_wattron($window, $attrs) {}

/**
 * Set the attributes for a window 
 *
 * @return int
 * @param  window resource
 * @param  attrs int
 */
function ncurses_wattrset($window, $attrs) {}

/**
 * Draws a border around the window using attributed characters 
 *
 * @return int
 * @param  window resource
 * @param  left int
 * @param  right int
 * @param  top int
 * @param  bottom int
 * @param  tl_corner int
 * @param  tr_corner int
 * @param  bl_corner int
 * @param  br_corner int
 */
function ncurses_wborder($window, $left, $right, $top, $bottom, $tl_corner, $tr_corner, $bl_corner, $br_corner) {}

/**
 * Clears window 
 *
 * @return int
 * @param  window resource
 */
function ncurses_wclear($window) {}

/**
 * Sets windows color pairings 
 *
 * @return int
 * @param  window resource
 * @param  color_pair int
 */
function ncurses_wcolor_set($window, $color_pair) {}

/**
 * Erase window contents 
 *
 * @return int
 * @param  window resource
 */
function ncurses_werase($window) {}

/**
 * Reads a character from keyboard (window) 
 *
 * @return int
 * @param  window resource
 */
function ncurses_wgetch($window) {}

/**
 * Draws a horizontal line in a window at current position using an attributed character and max. n characters long 
 *
 * @return int
 * @param  window resource
 * @param  charattr int
 * @param  n int
 */
function ncurses_whline($window, $charattr, $n) {}

/**
 * Transforms window/stdscr coordinates 
 *
 * @return bool
 * @param  window resource
 * @param  y int
 * @param  x int
 * @param  toscreen bool
 */
function ncurses_wmouse_trafo($window, &$y, &$x, $toscreen) {}

/**
 * Moves windows output position 
 *
 * @return int
 * @param  window resource
 * @param  y int
 * @param  x int
 */
function ncurses_wmove($window, $y, $x) {}

/**
 * Copies window to virtual screen 
 *
 * @return int
 * @param  window resource
 */
function ncurses_wnoutrefresh($window) {}

/**
 * Refreshes window on terminal screen 
 *
 * @return int
 * @param  window resource
 */
function ncurses_wrefresh($window) {}

/**
 * End standout mode for a window 
 *
 * @return int
 * @param  window resource
 */
function ncurses_wstandend($window) {}

/**
 * Enter standout mode for a window 
 *
 * @return int
 * @param  window resource
 */
function ncurses_wstandout($window) {}

/**
 * Draws a vertical line in a window at current position using an attributed character and max. n characters long 
 *
 * @return int
 * @param  window resource
 * @param  charattr int
 * @param  n int
 */
function ncurses_wvline($window, $charattr, $n) {}

/**
 * Move array argument's internal pointer to the next element and return it 
 *
 * @return mixed
 * @param  array_arg array
 */
function next($array_arg) {}

/**
 * Plural version of gettext() 
 *
 * @return string
 * @param  MSGID1 string
 * @param  MSGID2 string
 * @param  N int
 */
function ngettext($MSGID1, $MSGID2, $N) {}

/**
 * Converts newlines to HTML line breaks 
 *
 * @return string
 * @param  str string
 */
function nl2br($str) {}

/**
 * Query language and locale information 
 *
 * @return string
 * @param  item int
 */
function nl_langinfo($item) {}

/**
 * Get all headers from the request 
 *
 * @return array
 */
function nsapi_request_headers() {}

/**
 * Get all headers from the response 
 *
 * @return array
 */
function nsapi_response_headers() {}

/**
 * Perform an NSAPI sub-request 
 *
 * @return bool
 * @param  uri string
 */
function nsapi_virtual($uri) {}

/**
 * Formats a number with grouped thousands 
 *
 * @return string
 * @param  number float
 * @param  num_decimal_places int[optional]
 * @param  dec_seperator string[optional]
 * @param  thousands_seperator string
 */
function number_format($number, $num_decimal_places = null, $dec_seperator = null, $thousands_seperator) {}

/**
 * Clean (delete) the current output buffer 
 *
 * @return void
 */
function ob_clean() {}

/**
 * Clean the output buffer, and delete current output buffer 
 *
 * @return bool
 */
function ob_end_clean() {}

/**
 * Flush (send) the output buffer, and delete current output buffer 
 *
 * @return bool
 */
function ob_end_flush() {}

/**
 * Flush (send) contents of the output buffer. The last buffer content is sent to next buffer 
 *
 * @return void
 */
function ob_flush() {}

/**
 * Get current buffer contents and delete current output buffer 
 *
 * @return string
 */
function ob_get_clean() {}

/**
 * Return the contents of the output buffer 
 *
 * @return string
 */
function ob_get_contents() {}

/**
 * Get current buffer contents, flush (send) the output buffer, and delete current output buffer 
 *
 * @return string
 */
function ob_get_flush() {}

/**
 * Return the length of the output buffer 
 *
 * @return int
 */
function ob_get_length() {}

/**
 * Return the nesting level of the output buffer 
 *
 * @return int
 */
function ob_get_level() {}

/**
 * Return the status of the active or all output buffers 
 *
 * @return array
 * @param  full_status bool[optional]
 */
function ob_get_status($full_status = null) {}

/**
 * Encode str based on accept-encoding setting - designed to be called from ob_start() 
 *
 * @return string
 * @param  str string
 * @param  mode int
 */
function ob_gzhandler($str, $mode) {}

/**
 * Returns str in output buffer converted to the iconv.output_encoding character set 
 *
 * @return string
 * @param  contents string
 * @param  status int
 */
function ob_iconv_handler($contents, $status) {}

/**
 * Turn implicit flush on/off and is equivalent to calling flush() after every output call 
 *
 * @return void
 * @param  flag int[optional]
 */
function ob_implicit_flush($flag = null) {}

/**
 * 
 *
 * @return array
 */
function ob_list_handlers() {}

/**
 * Turn on Output Buffering (specifying an optional output handler). 
 *
 * @return bool
 * @param  user_function string|array[optional]
 * @param  chunk_size int[optional]
 * @param  erase bool[optional]
 */
function ob_start($user_function = null, $chunk_size = null, $erase = null) {}

/**
 * Bind a PHP array to an Oracle PL/SQL type by name 
 *
 * @return bool
 * @param  stmt resource
 * @param  name string
 * @param  var array
 * @param  max_table_length int
 * @param  max_item_length int[optional]
 * @param  type int[optional]
 */
function oci_bind_array_by_name($stmt, $name, &$var, $max_table_length, $max_item_length = null, $type = null) {}

/**
 * Bind a PHP variable to an Oracle placeholder by name 
 *
 * @return bool
 * @param  stmt resource
 * @param  name string
 * @param  var mixed
 * @param  maxlength int[optional]
 * @param  type int[optional]
 */
function oci_bind_by_name($stmt, $name, &$var, $maxlength = null, $type = null) {}

/**
 * Cancel reading from a cursor 
 *
 * @return bool
 * @param  stmt resource
 */
function oci_cancel($stmt) {}

/**
 * Disconnect from database 
 *
 * @return bool
 * @param  connection resource
 */
function oci_close($connection) {}








/**
 * Commit the current context 
 *
 * @return bool
 * @param  connection resource
 */
function oci_commit($connection) {}

/**
 * Connect to an Oracle database and log on. Returns a new session. 
 *
 * @return resource
 * @param  user string
 * @param  pass string
 * @param  db string[optional]
 * @param  charset string[optional]
 * @param  session_mode int[optional]
 */
function oci_connect($user, $pass, $db = null, $charset = null, $session_mode = null) {}

/**
 * Define a PHP variable to an Oracle column by name 
 *
 * @return bool
 * @param  stmt resource
 * @param  name string
 * @param  var mixed
 * @param  type int[optional]
 */
function oci_define_by_name($stmt, $name, &$var, $type = null) {}

/**
 * Return the last error of stmt|connection|global. If no error happened returns false. 
 *
 * @return array
 * @param  stmt|connection|global resource[optional]
 */
function oci_error($stmt = null) {}

/**
 * Execute a parsed statement 
 *
 * @return bool
 * @param  stmt resource
 * @param  mode int[optional]
 */
function oci_execute($stmt, $mode = null) {}

/**
 * Prepare a new row of data for reading 
 *
 * @return bool
 * @param  stmt resource
 */
function oci_fetch($stmt) {}

/**
 * Fetch all rows of result data into an array 
 *
 * @return int
 * @param  stmt resource
 * @param  output array
 * @param  skip int
 * @param  maxrows int
 * @param  flags int
 */
function oci_fetch_all($stmt, &$output, $skip, $maxrows, $flags) {}

/**
 * Fetch a result row as an array 
 *
 * @return array
 * @param  stmt resource
 * @param  mode int[optional]
 */
function oci_fetch_array($stmt, $mode = null) {}

/**
 * Fetch a result row as an associative array 
 *
 * @return array
 * @param  stmt resource
 */
function oci_fetch_assoc($stmt) {}

/**
 * Fetch a result row as an object 
 *
 * @return object
 * @param  stmt resource
 */
function oci_fetch_object($stmt) {}

/**
 * Fetch a result row as an enumerated array 
 *
 * @return array
 * @param  stmt resource
 */
function oci_fetch_row($stmt) {}

/**
 * Tell whether a column is NULL 
 *
 * @return bool
 * @param  stmt resource
 * @param  col int
 */
function oci_field_is_null($stmt, $col) {}

/**
 * Tell the name of a column 
 *
 * @return string
 * @param  stmt resource
 * @param  col int
 */
function oci_field_name($stmt, $col) {}

/**
 * Tell the precision of a column 
 *
 * @return int
 * @param  stmt resource
 * @param  col int
 */
function oci_field_precision($stmt, $col) {}

/**
 * Tell the scale of a column 
 *
 * @return int
 * @param  stmt resource
 * @param  col int
 */
function oci_field_scale($stmt, $col) {}

/**
 * Tell the maximum data size of a column 
 *
 * @return int
 * @param  stmt resource
 * @param  col int
 */
function oci_field_size($stmt, $col) {}

/**
 * Tell the data type of a column 
 *
 * @return mixed
 * @param  stmt resource
 * @param  col int
 */
function oci_field_type($stmt, $col) {}

/**
 * Tell the raw oracle data type of a column 
 *
 * @return int
 * @param  stmt resource
 * @param  col int
 */
function oci_field_type_raw($stmt, $col) {}


/**
 * Free all resources associated with a statement 
 *
 * @return bool
 * @param  stmt resource
 */
function oci_free_cursor($stmt) {}


/**
 * Free all resources associated with a statement 
 *
 * @return bool
 * @param  stmt resource
 */
function oci_free_statement($stmt) {}

/**
 * Toggle internal debugging output for the OCI extension 
 *
 * @return void
 * @param  onoff int
 */
function oci_internal_debug($onoff) {}



/**
 * Copies data from a LOB to another LOB 
 *
 * @return bool
 * @param  lob_to object
 * @param  lob_from object
 * @param  length int[optional]
 */
function oci_lob_copy($lob_to, $lob_from, $length = null) {}






/**
 * Tests to see if two LOB/FILE locators are equal 
 *
 * @return bool
 * @param  lob1 object
 * @param  lob2 object
 */
function oci_lob_is_equal($lob1, $lob2) {}











/**
 * Initialize a new collection 
 *
 * @return OCI-Collection
 * @param  connection resource
 * @param  tdo string
 * @param  schema string[optional]
 */
function oci_new_collection($connection, $tdo, $schema = null) {}

/**
 * Connect to an Oracle database and log on. Returns a new session. 
 *
 * @return resource
 * @param  user string
 * @param  pass string
 * @param  db string[optional]
 */
function oci_new_connect($user, $pass, $db = null) {}

/**
 * Return a new cursor (Statement-Handle) - use this to bind ref-cursors! 
 *
 * @return resource
 * @param  connection resource
 */
function oci_new_cursor($connection) {}

/**
 * Initialize a new empty descriptor LOB/FILE (LOB is default) 
 *
 * @return OCI-Lob
 * @param  connection resource
 * @param  type int[optional]
 */
function oci_new_descriptor($connection, $type = null) {}

/**
 * Return the number of result columns in a statement 
 *
 * @return int
 * @param  stmt resource
 */
function oci_num_fields($stmt) {}

/**
 * Return the row count of an OCI statement 
 *
 * @return int
 * @param  stmt resource
 */
function oci_num_rows($stmt) {}

/**
 * Parse a query and return a statement 
 *
 * @return resource
 * @param  connection resource
 * @param  query string
 */
function oci_parse($connection, $query) {}

/**
 * Changes the password of an account 
 *
 * @return resource
 * @param  connection resource
 * @param  username string
 * @param  old_password string
 * @param  new_password string
 */
function oci_password_change($connection, $username, $old_password, $new_password) {}

/**
 * Connect to an Oracle database using a persistent connection and log on. Returns a new session. 
 *
 * @return resource
 * @param  user string
 * @param  pass string
 * @param  db string[optional]
 * @param  charset string[optional]
 */
function oci_pconnect($user, $pass, $db = null, $charset = null) {}

/**
 * Return a single column of result data 
 *
 * @return mixed
 * @param  stmt resource
 * @param  column mixed
 */
function oci_result($stmt, $column) {}

/**
 * Rollback the current context 
 *
 * @return bool
 * @param  connection resource
 */
function oci_rollback($connection) {}

/**
 * Return a string containing server version information 
 *
 * @return string
 * @param  connection resource
 */
function oci_server_version($connection) {}

/**
 * Sets the number of rows to be prefetched on execute to prefetch_rows for stmt 
 *
 * @return bool
 * @param  stmt resource
 * @param  prefetch_rows int
 */
function oci_set_prefetch($stmt, $prefetch_rows) {}

/**
 * Return the query type of an OCI statement 
 *
 * @return string
 * @param  stmt resource
 */
function oci_statement_type($stmt) {}

/**
 * Bind a PHP variable to an Oracle placeholder by name 
 *
 * @return bool
 * @param  stmt resource
 * @param  name string
 * @param  var mixed
 * @param  maxlength int[optional]
 * @param  type int[optional]
 */
function ocibindbyname($stmt, $name, &$var, $maxlength = null, $type = null) {}

/**
 * Cancel reading from a cursor 
 *
 * @return bool
 * @param  stmt resource
 */
function ocicancel($stmt) {}

/**
 * Append an object to the collection 
 *
 * @return bool
 * @param  value string
 */
function ocicollappend($value) {}

/**
 * Assign element val to collection at index ndx 
 *
 * @return bool
 * @param  index int
 * @param  val string
 */
function ocicollassignelem($index, $val) {}

/**
 * Retrieve the value at collection index ndx 
 *
 * @return string
 * @param  ndx int
 */
function ocicollgetelem($ndx) {}

/**
 * Return the max value of a collection. For a varray this is the maximum length of the array 
 *
 * @return int
 */
function ocicollmax() {}

/**
 * Return the size of a collection 
 *
 * @return int
 */
function ocicollsize() {}

/**
 * Trim num elements from the end of a collection 
 *
 * @return bool
 * @param  num int
 */
function ocicolltrim($num) {}

/**
 * Tell whether a column is NULL 
 *
 * @return bool
 * @param  stmt resource
 * @param  col int
 */
function ocicolumnisnull($stmt, $col) {}

/**
 * Tell the name of a column 
 *
 * @return string
 * @param  stmt resource
 * @param  col int
 */
function ocicolumnname($stmt, $col) {}

/**
 * Tell the precision of a column 
 *
 * @return int
 * @param  stmt resource
 * @param  col int
 */
function ocicolumnprecision($stmt, $col) {}

/**
 * Tell the scale of a column 
 *
 * @return int
 * @param  stmt resource
 * @param  col int
 */
function ocicolumnscale($stmt, $col) {}

/**
 * Tell the maximum data size of a column 
 *
 * @return int
 * @param  stmt resource
 * @param  col int
 */
function ocicolumnsize($stmt, $col) {}

/**
 * Tell the data type of a column 
 *
 * @return mixed
 * @param  stmt resource
 * @param  col int
 */
function ocicolumntype($stmt, $col) {}

/**
 * Tell the raw oracle data type of a column 
 *
 * @return int
 * @param  stmt resource
 * @param  col int
 */
function ocicolumntyperaw($stmt, $col) {}

/**
 * Commit the current context 
 *
 * @return bool
 * @param  connection resource
 */
function ocicommit($connection) {}

/**
 * Define a PHP variable to an Oracle column by name 
 *
 * @return bool
 * @param  stmt resource
 * @param  name string
 * @param  var mixed
 * @param  type int[optional]
 */
function ocidefinebyname($stmt, $name, &$var, $type = null) {}

/**
 * Return the last error of stmt|connection|global. If no error happened returns false. 
 *
 * @return array
 * @param  stmt|connection|global resource[optional]
 */
function ocierror($stmt = null) {}

/**
 * Execute a parsed statement 
 *
 * @return bool
 * @param  stmt resource
 * @param  mode int[optional]
 */
function ociexecute($stmt, $mode = null) {}

/**
 * Prepare a new row of data for reading 
 *
 * @return bool
 * @param  stmt resource
 */
function ocifetch($stmt) {}

/**
 * Fetch a row of result data into an array 
 *
 * @return int
 * @param  stmt resource
 * @param  output array
 * @param  mode int[optional]
 */
function ocifetchinto($stmt, &$output, $mode = null) {}

/**
 * Fetch all rows of result data into an array 
 *
 * @return int
 * @param  stmt resource
 * @param  output array
 * @param  skip int
 * @param  maxrows int
 * @param  flags int
 */
function ocifetchstatement($stmt, &$output, $skip, $maxrows, $flags) {}

/**
 * Deletes collection object
 *
 * @return bool
 */
function ocifreecollection() {}

/**
 * Free all resources associated with a statement 
 *
 * @return bool
 * @param  stmt resource
 */
function ocifreecursor($stmt) {}

/**
 * Deletes large object description 
 *
 * @return bool
 */
function ocifreedesc() {}

/**
 * Free all resources associated with a statement 
 *
 * @return bool
 * @param  stmt resource
 */
function ocifreestatement($stmt) {}


/**
 * Toggle internal debugging output for the OCI extension 
 *
 * @return void
 * @param  onoff int
 */
function ociinternaldebug($onoff) {}

/**
 * Loads a large object 
 *
 * @return string
 */
function ociloadlob() {}

/**
 * Disconnect from database 
 *
 * @return bool
 * @param  connection resource
 */
function ocilogoff($connection) {}

/**
 * Connect to an Oracle database and log on. Returns a new session. 
 *
 * @return resource
 * @param  user string
 * @param  pass string
 * @param  db string[optional]
 * @param  charset string[optional]
 * @param  session_mode int[optional]
 */
function ocilogon($user, $pass, $db = null, $charset = null, $session_mode = null) {}

/**
 * Initialize a new collection 
 *
 * @return OCI-Collection
 * @param  connection resource
 * @param  tdo string
 * @param  schema string[optional]
 */
function ocinewcollection($connection, $tdo, $schema = null) {}

/**
 * Return a new cursor (Statement-Handle) - use this to bind ref-cursors! 
 *
 * @return resource
 * @param  connection resource
 */
function ocinewcursor($connection) {}

/**
 * Initialize a new empty descriptor LOB/FILE (LOB is default) 
 *
 * @return OCI-Lob
 * @param  connection resource
 * @param  type int[optional]
 */
function ocinewdescriptor($connection, $type = null) {}

/**
 * Connect to an Oracle database and log on. Returns a new session. 
 *
 * @return resource
 * @param  user string
 * @param  pass string
 * @param  db string[optional]
 */
function ocinlogon($user, $pass, $db = null) {}

/**
 * Return the number of result columns in a statement 
 *
 * @return int
 * @param  stmt resource
 */
function ocinumcols($stmt) {}

/**
 * Parse a query and return a statement 
 *
 * @return resource
 * @param  connection resource
 * @param  query string
 */
function ociparse($connection, $query) {}

/**
 * Changes the password of an account 
 *
 * @return resource
 * @param  connection resource
 * @param  username string
 * @param  old_password string
 * @param  new_password string
 */
function ocipasswordchange($connection, $username, $old_password, $new_password) {}

/**
 * Connect to an Oracle database using a persistent connection and log on. Returns a new session. 
 *
 * @return resource
 * @param  user string
 * @param  pass string
 * @param  db string[optional]
 * @param  charset string[optional]
 */
function ociplogon($user, $pass, $db = null, $charset = null) {}

/**
 * Return a single column of result data 
 *
 * @return mixed
 * @param  stmt resource
 * @param  column mixed
 */
function ociresult($stmt, $column) {}

/**
 * Rollback the current context 
 *
 * @return bool
 * @param  connection resource
 */
function ocirollback($connection) {}

/**
 * Return the row count of an OCI statement 
 *
 * @return int
 * @param  stmt resource
 */
function ocirowcount($stmt) {}

/**
 * Saves a large object 
 *
 * @return bool
 * @param  data string
 * @param  offset int[optional]
 */
function ocisavelob($data, $offset = null) {}

/**
 * Loads file into a LOB 
 *
 * @return bool
 * @param  filename string
 */
function ocisavelobfile($filename) {}

/**
 * Return a string containing server version information 
 *
 * @return string
 * @param  connection resource
 */
function ociserverversion($connection) {}


/**
 * Sets the number of rows to be prefetched on execute to prefetch_rows for stmt 
 *
 * @return bool
 * @param  stmt resource
 * @param  prefetch_rows int
 */
function ocisetprefetch($stmt, $prefetch_rows) {}

/**
 * Return the query type of an OCI statement 
 *
 * @return string
 * @param  stmt resource
 */
function ocistatementtype($stmt) {}

/**
 * Writes a large object into a file 
 *
 * @return bool
 * @param  filename string[optional]
 * @param  start int[optional]
 * @param  length int[optional]
 */
function ociwritelobtofile($filename = null, $start = null, $length = null) {}

/**
 * Returns the decimal equivalent of an octal string 
 *
 * @return number
 * @param  octal_number string
 */
function octdec($octal_number) {}

/**
 * Toggle autocommit mode or get status 
 *
 * @return mixed
 * @param  connection_id resource
 * @param  OnOff int[optional]
 */
function odbc_autocommit($connection_id, $OnOff = null) {}

/**
 * Handle binary column data 
 *
 * @return bool
 * @param  result_id int
 * @param  mode int
 */
function odbc_binmode($result_id, $mode) {}

/**
 * Close an ODBC connection 
 *
 * @return void
 * @param  connection_id resource
 */
function odbc_close($connection_id) {}

/**
 * Close all ODBC connections 
 *
 * @return void
 */
function odbc_close_all() {}

/**
 * Returns a result identifier that can be used to fetch a list of columns and associated privileges for the specified table 
 *
 * @return resource
 * @param  connection_id resource
 * @param  catalog string
 * @param  schema string
 * @param  table string
 * @param  column string
 */
function odbc_columnprivileges($connection_id, $catalog, $schema, $table, $column) {}

/**
 * Returns a result identifier that can be used to fetch a list of column names in specified tables 
 *
 * @return resource
 * @param  connection_id resource
 * @param  qualifier string[optional]
 * @param  owner string[optional]
 * @param  table_name string[optional]
 * @param  column_name string[optional]
 */
function odbc_columns($connection_id, $qualifier = null, $owner = null, $table_name = null, $column_name = null) {}

/**
 * Commit an ODBC transaction 
 *
 * @return bool
 * @param  connection_id resource
 */
function odbc_commit($connection_id) {}

/**
 * Connect to a datasource 
 *
 * @return resource
 * @param  DSN string
 * @param  user string
 * @param  password string
 * @param  cursor_option int[optional]
 */
function odbc_connect($DSN, $user, $password, $cursor_option = null) {}

/**
 * Get cursor name 
 *
 * @return string
 * @param  result_id resource
 */
function odbc_cursor($result_id) {}

/**
 * Return information about the currently connected data source 
 *
 * @return array
 * @param  connection_id resource
 * @param  fetch_type int
 */
function odbc_data_source($connection_id, $fetch_type) {}

/**
 * Prepare and execute an SQL statement 
 *
 * @return resource
 * @param  connection_id resource
 * @param  query string
 * @param  flags int[optional]
 */
function odbc_do($connection_id, $query, $flags = null) {}

/**
 * Get the last error code 
 *
 * @return string
 * @param  connection_id resource[optional]
 */
function odbc_error($connection_id = null) {}

/**
 * Get the last error message 
 *
 * @return string
 * @param  connection_id resource[optional]
 */
function odbc_errormsg($connection_id = null) {}

/**
 * Prepare and execute an SQL statement 
 *
 * @return resource
 * @param  connection_id resource
 * @param  query string
 * @param  flags int[optional]
 */
function odbc_exec($connection_id, $query, $flags = null) {}

/**
 * Execute a prepared statement 
 *
 * @return bool
 * @param  result_id resource
 * @param  parameters_array array[optional]
 */
function odbc_execute($result_id, $parameters_array = null) {}

/**
 * Fetch a result row as an associative array 
 *
 * @return array
 * @param  result int
 * @param  rownumber int[optional]
 */
function odbc_fetch_array($result, $rownumber = null) {}

/**
 * Fetch one result row into an array 
 *
 * @return bool
 * @param  result_id resource
 * @param  result_array array
 * @param  rownumber int[optional]
 */
function odbc_fetch_into($result_id, $result_array, $rownumber = null) {}

/**
 * Fetch a result row as an object 
 *
 * @return object
 * @param  result int
 * @param  rownumber int[optional]
 */
function odbc_fetch_object($result, $rownumber = null) {}

/**
 * Fetch a row 
 *
 * @return bool
 * @param  result_id resource
 * @param  row_number int[optional]
 */
function odbc_fetch_row($result_id, $row_number = null) {}

/**
 * Get the length (precision) of a column 
 *
 * @return int
 * @param  result_id resource
 * @param  field_number int
 */
function odbc_field_len($result_id, $field_number) {}

/**
 * Get a column name 
 *
 * @return string
 * @param  result_id resource
 * @param  field_number int
 */
function odbc_field_name($result_id, $field_number) {}

/**
 * Return column number 
 *
 * @return int
 * @param  result_id resource
 * @param  field_name string
 */
function odbc_field_num($result_id, $field_name) {}

/**
 * Get the length (precision) of a column 
 *
 * @return int
 * @param  result_id resource
 * @param  field_number int
 */
function odbc_field_precision($result_id, $field_number) {}

/**
 * Get the scale of a column 
 *
 * @return int
 * @param  result_id resource
 * @param  field_number int
 */
function odbc_field_scale($result_id, $field_number) {}

/**
 * Get the datatype of a column 
 *
 * @return string
 * @param  result_id resource
 * @param  field_number int
 */
function odbc_field_type($result_id, $field_number) {}

/**
 * Returns a result identifier to either a list of foreign keys in the specified table or a list of foreign keys in other tables that refer to the primary key in the specified table 
 *
 * @return resource
 * @param  connection_id resource
 * @param  pk_qualifier string
 * @param  pk_owner string
 * @param  pk_table string
 * @param  fk_qualifier string
 * @param  fk_owner string
 * @param  fk_table string
 */
function odbc_foreignkeys($connection_id, $pk_qualifier, $pk_owner, $pk_table, $fk_qualifier, $fk_owner, $fk_table) {}

/**
 * Free resources associated with a result 
 *
 * @return bool
 * @param  result_id resource
 */
function odbc_free_result($result_id) {}

/**
 * Returns a result identifier containing information about data types supported by the data source 
 *
 * @return resource
 * @param  connection_id resource
 * @param  data_type int[optional]
 */
function odbc_gettypeinfo($connection_id, $data_type = null) {}

/**
 * Handle LONG columns 
 *
 * @return bool
 * @param  result_id int
 * @param  length int
 */
function odbc_longreadlen($result_id, $length) {}

/**
 * Checks if multiple results are avaiable 
 *
 * @return bool
 * @param  result_id resource
 */
function odbc_next_result($result_id) {}

/**
 * Get number of columns in a result 
 *
 * @return int
 * @param  result_id resource
 */
function odbc_num_fields($result_id) {}

/**
 * Get number of rows in a result 
 *
 * @return int
 * @param  result_id resource
 */
function odbc_num_rows($result_id) {}

/**
 * Establish a persistent connection to a datasource 
 *
 * @return resource
 * @param  DSN string
 * @param  user string
 * @param  password string
 * @param  cursor_option int[optional]
 */
function odbc_pconnect($DSN, $user, $password, $cursor_option = null) {}

/**
 * Prepares a statement for execution 
 *
 * @return resource
 * @param  connection_id resource
 * @param  query string
 */
function odbc_prepare($connection_id, $query) {}

/**
 * Returns a result identifier listing the column names that comprise the primary key for a table 
 *
 * @return resource
 * @param  connection_id resource
 * @param  qualifier string
 * @param  owner string
 * @param  table string
 */
function odbc_primarykeys($connection_id, $qualifier, $owner, $table) {}

/**
 * Returns a result identifier containing the list of input and output parameters, as well as the columns that make up the result set for the specified procedures 
 *
 * @return resource
 * @param  connection_id resource
 * @param  qualifier string[optional]
 * @param  owner string
 * @param  proc string
 * @param  column string
 */
function odbc_procedurecolumns($connection_id, $qualifier = null, $owner, $proc, $column) {}

/**
 * Returns a result identifier containg the list of procedure names in a datasource 
 *
 * @return resource
 * @param  connection_id resource
 * @param  qualifier string[optional]
 * @param  owner string
 * @param  name string
 */
function odbc_procedures($connection_id, $qualifier = null, $owner, $name) {}

/**
 * Get result data 
 *
 * @return mixed
 * @param  result_id resource
 * @param  field mixed
 */
function odbc_result($result_id, $field) {}

/**
 * Print result as HTML table 
 *
 * @return int
 * @param  result_id resource
 * @param  format string[optional]
 */
function odbc_result_all($result_id, $format = null) {}

/**
 * Rollback a transaction 
 *
 * @return bool
 * @param  connection_id resource
 */
function odbc_rollback($connection_id) {}

/**
 * Sets connection or statement options 
 *
 * @return bool
 * @param  conn_id|result_id resource
 * @param  which int
 * @param  option int
 * @param  value int
 */
function odbc_setoption($conn_id, $which, $option, $value) {}

/**
 * Returns a result identifier containing either the optimal set of columns that uniquely identifies a row in the table or columns that are automatically updated when any value in the row is updated by a transaction 
 *
 * @return resource
 * @param  connection_id resource
 * @param  type int
 * @param  qualifier string
 * @param  owner string
 * @param  table string
 * @param  scope int
 * @param  nullable int
 */
function odbc_specialcolumns($connection_id, $type, $qualifier, $owner, $table, $scope, $nullable) {}

/**
 * Returns a result identifier that contains statistics about a single table and the indexes associated with the table 
 *
 * @return resource
 * @param  connection_id resource
 * @param  qualifier string
 * @param  owner string
 * @param  name string
 * @param  unique int
 * @param  accuracy int
 */
function odbc_statistics($connection_id, $qualifier, $owner, $name, $unique, $accuracy) {}

/**
 * Returns a result identifier containing a list of tables and the privileges associated with each table 
 *
 * @return resource
 * @param  connection_id resource
 * @param  qualifier string
 * @param  owner string
 * @param  name string
 */
function odbc_tableprivileges($connection_id, $qualifier, $owner, $name) {}

/**
 * Call the SQLTables function 
 *
 * @return resource
 * @param  connection_id resource
 * @param  qualifier string[optional]
 * @param  owner string[optional]
 * @param  name string[optional]
 * @param  table_types string[optional]
 */
function odbc_tables($connection_id, $qualifier = null, $owner = null, $name = null, $table_types = null) {}

/**
 * Open a directory and return a dir_handle 
 *
 * @return resource
 * @param  path string
 * @param  context resource
 */
function opendir($path, $context) {}

/**
 * Open connection to system logger 
 *
 * @return bool
 * @param  ident string
 * @param  option int
 * @param  facility int
 */
function openlog($ident, $option, $facility) {}

/**
 * Exports a CSR to file or a var 
 *
 * @return bool
 * @param  csr resource
 * @param  out string
 * @param  notext bool[optional]
 */
function openssl_csr_export($csr, &$out, $notext = true) {}

/**
 * Exports a CSR to file 
 *
 * @return bool
 * @param  csr resource
 * @param  outfilename string
 * @param  notext bool[optional]
 */
function openssl_csr_export_to_file($csr, $outfilename, $notext = true) {}

/**
 * Generates a privkey and CSR 
 *
 * @return mixed
 * @param  dn array
 * @param  privkey resource
 * @param  configargs array[optional]
 * @param  extraattribs array
 */
function openssl_csr_new($dn, &$privkey, $configargs = null, $extraattribs) {}

/**
 * Signs a cert with another CERT 
 *
 * @return resource
 * @param  csr mixed
 * @param  x509 mixed
 * @param  priv_key mixed
 * @param  days long
 * @param  config_args array[optional]
 * @param  serial long[optional]
 */
function openssl_csr_sign($csr, $x509, $priv_key, $days, $config_args = null, $serial = null) {}

/**
 * Returns a description of the last error, and alters the index of the error messages. Returns false when the are no more messages 
 *
 * @return string
 */
function openssl_error_string() {}

/**
 * Frees a key 
 *
 * @return void
 * @param  key int
 */
function openssl_free_key($key) {}

/**
 * Gets private keys 
 *
 * @return resource
 * @param  key string
 * @param  passphrase string[optional]
 */
function openssl_get_privatekey($key, $passphrase = null) {}

/**
 * Gets public key from X.509 certificate 
 *
 * @return resource
 * @param  cert mixed
 */
function openssl_get_publickey($cert) {}

/**
 * Opens data 
 *
 * @return bool
 * @param  data string
 * @param  opendata &string
 * @param  ekey string
 * @param  privkey mixed
 */
function openssl_open($data, $opendata, $ekey, $privkey) {}

/**
 * Decrypts the S/MIME message in the file name infilename and output the results to the file name outfilename.  recipcert is a CERT for one of the recipients. recipkey specifies the private key matching recipcert, if recipcert does not include the key 
 *
 * @return bool
 * @param  infilename string
 * @param  outfilename string
 * @param  recipcert mixed
 * @param  recipkey mixed[optional]
 */
function openssl_pkcs7_decrypt($infilename, $outfilename, $recipcert, $recipkey = null) {}

/**
 * Encrypts the message in the file named infile with the certificates in recipcerts and output the result to the file named outfile 
 *
 * @return bool
 * @param  infile string
 * @param  outfile string
 * @param  recipcerts mixed
 * @param  headers array
 * @param  flags long[optional]
 * @param  cipher long[optional]
 */
function openssl_pkcs7_encrypt($infile, $outfile, $recipcerts, $headers, $flags = null, $cipher = null) {}

/**
 * Signs the MIME message in the file named infile with signcert/signkey and output the result to file name outfile. headers lists plain text headers to exclude from the signed portion of the message, and should include to, from and subject as a minimum 
 *
 * @return bool
 * @param  infile string
 * @param  outfile string
 * @param  signcert mixed
 * @param  signkey mixed
 * @param  headers array
 * @param  flags long[optional]
 * @param  extracertsfilename string[optional]
 */
function openssl_pkcs7_sign($infile, $outfile, $signcert, $signkey, $headers, $flags = null, $extracertsfilename = null) {}

/**
 * Verifys that the data block is intact, the signer is who they say they are, and returns the CERTs of the signers 
 *
 * @return mixed
 * @param  filename string
 * @param  flags long
 * @param  signerscerts string[optional]
 * @param  cainfo array[optional]
 * @param  extracerts string[optional]
 * @param  content string[optional]
 */
function openssl_pkcs7_verify($filename, $flags, $signerscerts = null, $cainfo = null, $extracerts = null, $content = null) {}

/**
 * Gets an exportable representation of a key into a string or file 
 *
 * @return bool
 * @param  key mixed
 * @param  out &mixed
 * @param  passphrase string[optional]
 * @param  config_args array[optional]
 */
function openssl_pkey_export($key, $out, $passphrase = null, $config_args = null) {}

/**
 * Gets an exportable representation of a key into a file 
 *
 * @return bool
 * @param  key mixed
 * @param  outfilename string
 * @param  passphrase string[optional]
 * @param  config_args array
 */
function openssl_pkey_export_to_file($key, $outfilename, $passphrase = null, $config_args) {}

/**
 * Frees a key 
 *
 * @return void
 * @param  key int
 */
function openssl_pkey_free($key) {}

/**
 * Gets private keys 
 *
 * @return resource
 * @param  key string
 * @param  passphrase string[optional]
 */
function openssl_pkey_get_private($key, $passphrase = null) {}

/**
 * Gets public key from X.509 certificate 
 *
 * @return resource
 * @param  cert mixed
 */
function openssl_pkey_get_public($cert) {}

/**
 * Generates a new private key 
 *
 * @return resource
 * @param  configargs array[optional]
 */
function openssl_pkey_new($configargs = null) {}

/**
 * Decrypts data with private key 
 *
 * @return bool
 * @param  data string
 * @param  decrypted string
 * @param  key mixed
 * @param  padding int[optional]
 */
function openssl_private_decrypt($data, $decrypted, $key, $padding = null) {}

/**
 * Encrypts data with private key 
 *
 * @return bool
 * @param  data string
 * @param  crypted string
 * @param  key mixed
 * @param  padding int[optional]
 */
function openssl_private_encrypt($data, $crypted, $key, $padding = null) {}

/**
 * Decrypts data with public key 
 *
 * @return bool
 * @param  data string
 * @param  crypted string
 * @param  key resource
 * @param  padding int[optional]
 */
function openssl_public_decrypt($data, $crypted, $key, $padding = null) {}

/**
 * Encrypts data with public key 
 *
 * @return bool
 * @param  data string
 * @param  crypted string
 * @param  key mixed
 * @param  padding int[optional]
 */
function openssl_public_encrypt($data, $crypted, $key, $padding = null) {}

/**
 * Seals data 
 *
 * @return int
 * @param  data string
 * @param  sealdata &string
 * @param  ekeys &array
 * @param  pubkeys array
 */
function openssl_seal($data, $sealdata, $ekeys, $pubkeys) {}

/**
 * Signs data 
 *
 * @return bool
 * @param  data string
 * @param  signature &string
 * @param  key mixed
 */
function openssl_sign($data, $signature, $key) {}

/**
 * Verifys data 
 *
 * @return int
 * @param  data string
 * @param  signature string
 * @param  key mixed
 */
function openssl_verify($data, $signature, $key) {}

/**
 * Checks if a private key corresponds to a CERT 
 *
 * @return bool
 * @param  cert mixed
 * @param  key mixed
 */
function openssl_x509_check_private_key($cert, $key) {}

/**
 * Checks the CERT to see if it can be used for the purpose in purpose. cainfo holds information about trusted CAs 
 *
 * @return int
 * @param  x509cert mixed
 * @param  purpose int
 * @param  cainfo array
 * @param  untrustedfile string[optional]
 */
function openssl_x509_checkpurpose($x509cert, $purpose, $cainfo, $untrustedfile = null) {}

/**
 * Exports a CERT to file or a var 
 *
 * @return bool
 * @param  x509 mixed
 * @param  out string
 * @param  notext bool[optional]
 */
function openssl_x509_export($x509, &$out, $notext = true) {}

/**
 * Exports a CERT to file or a var 
 *
 * @return bool
 * @param  x509 mixed
 * @param  outfilename string
 * @param  notext bool[optional]
 */
function openssl_x509_export_to_file($x509, $outfilename, $notext = true) {}

/**
 * Frees X.509 certificates 
 *
 * @return void
 * @param  x509 resource
 */
function openssl_x509_free($x509) {}

/**
 * Returns an array of the fields/values of the CERT 
 *
 * @return array
 * @param  x509 mixed
 * @param  shortnames bool[optional]
 */
function openssl_x509_parse($x509, $shortnames = true) {}

/**
 * Reads X.509 certificates 
 *
 * @return resource
 * @param  cert mixed
 */
function openssl_x509_read($cert) {}

/**
 * Returns ASCII value of character 
 *
 * @return int
 * @param  character string
 */
function ord($character) {}

/**
 * Add URL rewriter values 
 *
 * @return bool
 * @param  name string
 * @param  value string
 */
function output_add_rewrite_var($name, $value) {}

/**
 * Check filename against the Zend Performance Suite's content cache rules 
 *
 * @return array
 * @param  filename string
 */
function output_cache_check_file($filename) {}

/**
 * Disable content-caching of current page results (Zend Performance Suite) 
 *
 * @return void
 */
function output_cache_disable() {}

/**
 * Disable compression of current page results (Zend Performance Suite) 
 *
 * @return void
 */
function output_cache_disable_compression() {}

/**
 * Start output caching (Zend Performance Suite) 
 *
 * @return bool
 * @param  key string
 * @param  lifetime int
 */
function output_cache_exists($key, $lifetime) {}

/**
 * Cache the result of a function call, or obtain previously-generated cache (Zend Performance Suite) 
 *
 * @return string
 * @param  key string
 * @param  function string
 * @param  lifetime int
 */
function output_cache_fetch($key, $function, $lifetime) {}

/**
 * Fetch value from Zend Performance Suite's cache by key 
 *
 * @return mixed
 * @param  key string
 * @param  lifetime int
 */
function output_cache_get($key, $lifetime) {}

/**
 * Get content cache statistics from the Zend Performance Suite
 *
 * @return array
 */
function output_cache_get_statistics() {}

/**
 * Cache the output of a function call, or obtain previously-generated cachbe (Zend Performance Suite) 
 *
 * @return string
 * @param  key string
 * @param  function string
 * @param  lifetime int
 */
function output_cache_output($key, $function, $lifetime) {}

/**
 * Store a value in the Zend Performance Suite's cache using a specified key 
 *
 * @return bool
 * @param  key string
 * @param  data mixed
 */
function output_cache_put($key, $data) {}

/**
 * Remove all copies of the specified file from the Zend Performance Suite's cache 
 *
 * @return bool
 * @param  filename string
 */
function output_cache_remove($filename) {}

/**
 * Remove all cached elements using the specified key from the Zend Performance Suite's cache 
 *
 * @return bool
 * @param  key string
 */
function output_cache_remove_key($key) {}

/**
 * Remove all copies of the specified URL from the Zend Performance Suite's cache 
 *
 * @return bool
 * @param  url string
 */
function output_cache_remove_url($url) {}

/**
 * Stop output caching (Zend Performance Suite) 
 *
 * @return void
 */
function output_cache_stop() {}

/**
 * Reset(clear) URL rewriter values 
 *
 * @return bool
 */
function output_reset_rewrite_vars() {}

/**
 * Takes one or more arguments and packs them into a binary string according to the format argument 
 *
 * @return string
 * @param  format string
 * @param  arg1 mixed
 * @param  arg2 mixed[optional]
 * @vararg ... mixed
 */
function pack($format, $arg1, $arg2 = null) {}

/**
 * Parse configuration file 
 *
 * @return array
 * @param  filename string
 * @param  process_sections bool[optional]
 */
function parse_ini_file($filename, $process_sections = null) {}

/**
 * Parses GET/POST/COOKIE data and sets global variables 
 *
 * @return void
 * @param  encoded_string string
 * @param  result array[optional]
 */
function parse_str($encoded_string, $result = null) {}

/**
 * Parse a URL and return its components 
 *
 * @return array
 * @param  url string
 * @param  url_component int[optional]
 */
function parse_url($url, $url_component = null) {}

/**
 * Execute an external program and display raw output 
 *
 * @return void
 * @param  command string
 * @param  return_value int[optional]
 */
function passthru($command, &$return_value) {}

/**
 * Returns information about a certain string 
 *
 * @return mixed
 * @param  path string
 */
function pathinfo($path) {}

/**
 * Close a file pointer opened by popen() 
 *
 * @return int
 * @param  fp resource
 */
function pclose($fp) {}

/**
 * Set an alarm clock for delivery of a signal
 *
 * @return int
 * @param  seconds int
 */
function pcntl_alarm($seconds) {}

/**
 * Executes specified program in current process space as defined by exec(2) 
 *
 * @return void
 * @param  path string
 * @param  args array[optional]
 * @param  envs array[optional]
 */
function pcntl_exec($path, $args = null, $envs = null) {}

/**
 * Forks the currently running process following the same behavior as the UNIX fork() system call
 *
 * @return int
 */
function pcntl_fork() {}

/**
 * Get the priority of any process 
 *
 * @return int
 * @param  pid int[optional]
 * @param  process_identifier int[optional]
 */
function pcntl_getpriority($pid = null, $process_identifier = null) {}

/**
 * Change the priority of any process 
 *
 * @return bool
 * @param  priority int
 * @param  pid int[optional]
 * @param  process_identifier int[optional]
 */
function pcntl_setpriority($priority, $pid = null, $process_identifier = null) {}

/**
 * Assigns a system signal handler to a PHP function 
 *
 * @return bool
 * @param  signo int
 * @param  handle callback
 * @param  restart_syscalls bool[optional]
 */
function pcntl_signal($signo, $handle, $restart_syscalls = null) {}

/**
 * Waits on or returns the status of a forked child as defined by the waitpid() system call 
 *
 * @return int
 * @param  status int
 */
function pcntl_wait(&$status) {}

/**
 * Waits on or returns the status of a forked child as defined by the waitpid() system call 
 *
 * @return int
 * @param  pid int
 * @param  status int
 * @param  options int
 */
function pcntl_waitpid($pid, &$status, $options) {}

/**
 * Returns the status code of a child's exit 
 *
 * @return int
 * @param  status int
 */
function pcntl_wexitstatus($status) {}

/**
 * Returns true if the child status code represents a successful exit 
 *
 * @return bool
 * @param  status int
 */
function pcntl_wifexited($status) {}

/**
 * Returns true if the child status code represents a process that was terminated due to a signal 
 *
 * @return bool
 * @param  status int
 */
function pcntl_wifsignaled($status) {}

/**
 * Returns true if the child status code represents a stopped process (WUNTRACED must have been used with waitpid) 
 *
 * @return bool
 * @param  status int
 */
function pcntl_wifstopped($status) {}

/**
 * Returns the number of the signal that caused the process to stop who's status code is passed 
 *
 * @return int
 * @param  status int
 */
function pcntl_wstopsig($status) {}

/**
 * Returns the number of the signal that terminated the process who's status code is passed  
 *
 * @return int
 * @param  status int
 */
function pcntl_wtermsig($status) {}

/**
 * Return array of available PDO drivers 
 *
 * @return array
 */
function pdo_drivers() {}

/**
 * Open persistent Internet or Unix domain socket connection 
 *
 * @return resource
 * @param  hostname string
 * @param  port int
 * @param  errno int[optional]
 * @param  errstr string[optional]
 * @param  timeout float[optional]
 */
function pfsockopen($hostname, $port, $errno = null, $errstr = null, $timeout = null) {}

/**
 * Returns the number of affected tuples 
 *
 * @return int
 * @param  result resource
 */
function pg_affected_rows($result) {}

/**
 * Cancel request 
 *
 * @return bool
 * @param  connection resource
 */
function pg_cancel_query($connection) {}

/**
 * Get the current client encoding 
 *
 * @return string
 * @param  connection resource[optional]
 */
function pg_client_encoding($connection = null) {}

/**
 * Get the current client encoding 
 *
 * @return string
 * @param  connection resource[optional]
 */
function pg_clientencoding($connection = null) {}

/**
 * Close a PostgreSQL connection 
 *
 * @return bool
 * @param  connection resource[optional]
 */
function pg_close($connection = null) {}

/**
 * Returns the number of affected tuples 
 *
 * @return int
 * @param  result resource
 */
function pg_cmdtuples($result) {}

/**
 * Get connection is busy or not 
 *
 * @return bool
 * @param  connection resource
 */
function pg_connection_busy($connection) {}

/**
 * Reset connection (reconnect) 
 *
 * @return bool
 * @param  connection resource
 */
function pg_connection_reset($connection) {}

/**
 * Get connection status 
 *
 * @return int
 * @param  connnection resource
 */
function pg_connection_status($connnection) {}

/**
 * Check and convert values for PostgreSQL SQL statement 
 *
 * @return array
 * @param  db resource
 * @param  table string
 * @param  values array
 * @param  options int
 */
function pg_convert($db, $table, $values, $options) {}

/**
 * Copy table from array 
 *
 * @return bool
 * @param  connection resource
 * @param  table_name string
 * @param  rows array
 * @param  delimiter string[optional]
 * @param  null_as string[optional]
 */
function pg_copy_from($connection, $table_name, $rows, $delimiter = null, $null_as = null) {}

/**
 * Copy table to array 
 *
 * @return array
 * @param  connection resource
 * @param  table_name string
 * @param  delimiter string[optional]
 * @param  null_as string[optional]
 */
function pg_copy_to($connection, $table_name, $delimiter = null, $null_as = null) {}

/**
 * Get the database name 
 *
 * @return string
 * @param  connection resource[optional]
 */
function pg_dbname($connection = null) {}

/**
 * Delete records has ids (id=>value) 
 *
 * @return mixed
 * @param  db resource
 * @param  table string
 * @param  ids array
 * @param  options int
 */
function pg_delete($db, $table, $ids, $options) {}

/**
 * Sync with backend. Completes the Copy command 
 *
 * @return bool
 * @param  connection resource[optional]
 */
function pg_end_copy($connection = null) {}

/**
 * Get the error message string 
 *
 * @return string
 * @param  connection resource[optional]
 */
function pg_errormessage($connection = null) {}

/**
 * Escape binary for bytea type  
 *
 * @return string
 * @param  data string
 */
function pg_escape_bytea($data) {}

/**
 * Escape string for text/char type 
 *
 * @return string
 * @param  data string
 */
function pg_escape_string($data) {}

/**
 * Execute a query 
 *
 * @return resource
 * @param  connection resource[optional]
 * @param  query string
 */
function pg_exec($connection = null, $query) {}

/**
 * Execute a prepared query  
 *
 * @return resource
 * @param  connection resource[optional]
 * @param  stmtname string
 * @param  params array
 */
function pg_execute($connection = null, $stmtname, $params) {}

/**
 * Fetch all rows into array 
 *
 * @return array
 * @param  result resource
 */
function pg_fetch_all($result) {}

/**
 * Fetch all rows into array 
 *
 * @return array
 * @param  result resource
 * @param  column_number int[optional]
 */
function pg_fetch_all_columns($result, $column_number = null) {}

/**
 * Fetch a row as an array 
 *
 * @return array
 * @param  result resource
 * @param  row int[optional]
 * @param  result_type int[optional]
 */
function pg_fetch_array($result, $row = null, $result_type = null) {}

/**
 * Fetch a row as an assoc array 
 *
 * @return array
 * @param  result resource
 * @param  row int[optional]
 */
function pg_fetch_assoc($result, $row = null) {}

/**
 * Fetch a row as an object 
 *
 * @return object
 * @param  result resource
 * @param  row int[optional]
 * @param  class_name string[optional]
 * @param  ctor_params NULL|array[optional]
 */
function pg_fetch_object($result, $row = null, $class_name = null, $ctor_params = null) {}

/**
 * Returns values from a result identifier 
 *
 * @return string
 * @param  result resource
 * @param  row_number int[optional]
 * @param  field_name mixed
 */
function pg_fetch_result($result, $row_number = null, $field_name) {}

/**
 * Get a row as an enumerated array 
 *
 * @return array
 * @param  result resource
 * @param  row int[optional]
 * @param  result_type int[optional]
 */
function pg_fetch_row($result, $row = null, $result_type = null) {}

/**
 * Test if a field is NULL 
 *
 * @return int
 * @param  result resource
 * @param  row int[optional]
 * @param  field_name_or_number mixed
 */
function pg_field_is_null($result, $row = null, $field_name_or_number) {}

/**
 * Returns the name of the field 
 *
 * @return string
 * @param  result resource
 * @param  field_number int
 */
function pg_field_name($result, $field_number) {}

/**
 * Returns the field number of the named field 
 *
 * @return int
 * @param  result resource
 * @param  field_name string
 */
function pg_field_num($result, $field_name) {}

/**
 * Returns the printed length 
 *
 * @return int
 * @param  result resource
 * @param  row int[optional]
 * @param  field_name_or_number mixed
 */
function pg_field_prtlen($result, $row = null, $field_name_or_number) {}

/**
 * Returns the internal size of the field 
 *
 * @return int
 * @param  result resource
 * @param  field_number int
 */
function pg_field_size($result, $field_number) {}

/**
 * Returns the type name for the given field 
 *
 * @return string
 * @param  result resource
 * @param  field_number int
 */
function pg_field_type($result, $field_number) {}

/**
 * Returns the type oid for the given field 
 *
 * @return int
 * @param  result resource
 * @param  field_number int
 */
function pg_field_type_oid($result, $field_number) {}

/**
 * Test if a field is NULL 
 *
 * @return int
 * @param  result resource
 * @param  row int[optional]
 * @param  field_name_or_number mixed
 */
function pg_fieldisnull($result, $row = null, $field_name_or_number) {}

/**
 * Returns the name of the field 
 *
 * @return string
 * @param  result resource
 * @param  field_number int
 */
function pg_fieldname($result, $field_number) {}

/**
 * Returns the field number of the named field 
 *
 * @return int
 * @param  result resource
 * @param  field_name string
 */
function pg_fieldnum($result, $field_name) {}

/**
 * Returns the printed length 
 *
 * @return int
 * @param  result resource
 * @param  row int[optional]
 * @param  field_name_or_number mixed
 */
function pg_fieldprtlen($result, $row = null, $field_name_or_number) {}

/**
 * Returns the internal size of the field 
 *
 * @return int
 * @param  result resource
 * @param  field_number int
 */
function pg_fieldsize($result, $field_number) {}

/**
 * Returns the type name for the given field 
 *
 * @return string
 * @param  result resource
 * @param  field_number int
 */
function pg_fieldtype($result, $field_number) {}

/**
 * Free result memory 
 *
 * @return bool
 * @param  result resource
 */
function pg_free_result($result) {}

/**
 * Free result memory 
 *
 * @return bool
 * @param  result resource
 */
function pg_freeresult($result) {}

/**
 * Get asynchronous notification 
 *
 * @return array
 * @param  connection resource[optional]
 * @param  result_type unknown
 */
function pg_get_notify($connection = null, $result_type) {}

/**
 * Get backend(server) pid 
 *
 * @return int
 * @param  connection resource[optional]
 */
function pg_get_pid($connection = null) {}

/**
 * Get asynchronous query result 
 *
 * @return resource
 * @param  connection resource
 */
function pg_get_result($connection) {}

/**
 * Returns the last object identifier 
 *
 * @return string
 * @param  result resource
 */
function pg_getlastoid($result) {}

/**
 * Returns the host name associated with the connection 
 *
 * @return string
 * @param  connection resource[optional]
 */
function pg_host($connection = null) {}

/**
 * Insert values (filed=>value) to table 
 *
 * @return mixed
 * @param  db resource
 * @param  table string
 * @param  values array
 * @param  options int
 */
function pg_insert($db, $table, $values, $options) {}

/**
 * Get the error message string 
 *
 * @return string
 * @param  connection resource[optional]
 */
function pg_last_error($connection = null) {}

/**
 * Returns the last notice set by the backend 
 *
 * @return string
 * @param  connection resource
 */
function pg_last_notice($connection) {}

/**
 * Returns the last object identifier 
 *
 * @return string
 * @param  result resource
 */
function pg_last_oid($result) {}

/**
 * Close a large object 
 *
 * @return bool
 * @param  large_object resource
 */
function pg_lo_close($large_object) {}

/**
 * Create a large object 
 *
 * @return int
 * @param  connection resource[optional]
 */
function pg_lo_create($connection = null) {}

/**
 * Export large object direct to filesystem 
 *
 * @return bool
 * @param  connection resource[optional]
 * @param  objoid int
 * @param  filename string
 */
function pg_lo_export($connection = null, $objoid, $filename) {}

/**
 * Import large object direct from filesystem 
 *
 * @return int
 * @param  connection resource[optional]
 * @param  filename string
 */
function pg_lo_import($connection = null, $filename) {}

/**
 * Open a large object and return fd 
 *
 * @return resource
 * @param  connection resource[optional]
 * @param  large_object_oid int
 * @param  mode string
 */
function pg_lo_open($connection = null, $large_object_oid, $mode) {}

/**
 * Read a large object 
 *
 * @return string
 * @param  large_object resource
 * @param  len int[optional]
 */
function pg_lo_read($large_object, $len = null) {}

/**
 * Read a large object and send straight to browser 
 *
 * @return int
 * @param  large_object resource
 */
function pg_lo_read_all($large_object) {}

/**
 * Seeks position of large object 
 *
 * @return bool
 * @param  large_object resource
 * @param  offset int
 * @param  whence int[optional]
 */
function pg_lo_seek($large_object, $offset, $whence = null) {}

/**
 * Returns current position of large object 
 *
 * @return int
 * @param  large_object resource
 */
function pg_lo_tell($large_object) {}

/**
 * Delete a large object 
 *
 * @return bool
 * @param  connection resource[optional]
 * @param  large_object_oid string
 */
function pg_lo_unlink($connection = null, $large_object_oid) {}

/**
 * Write a large object 
 *
 * @return int
 * @param  large_object resource
 * @param  buf string
 * @param  len int[optional]
 */
function pg_lo_write($large_object, $buf, $len = null) {}

/**
 * Close a large object 
 *
 * @return bool
 * @param  large_object resource
 */
function pg_loclose($large_object) {}

/**
 * Create a large object 
 *
 * @return int
 * @param  connection resource[optional]
 */
function pg_locreate($connection = null) {}

/**
 * Export large object direct to filesystem 
 *
 * @return bool
 * @param  connection resource[optional]
 * @param  objoid int
 * @param  filename string
 */
function pg_loexport($connection = null, $objoid, $filename) {}

/**
 * Import large object direct from filesystem 
 *
 * @return int
 * @param  connection resource[optional]
 * @param  filename string
 */
function pg_loimport($connection = null, $filename) {}

/**
 * Open a large object and return fd 
 *
 * @return resource
 * @param  connection resource[optional]
 * @param  large_object_oid int
 * @param  mode string
 */
function pg_loopen($connection = null, $large_object_oid, $mode) {}

/**
 * Read a large object 
 *
 * @return string
 * @param  large_object resource
 * @param  len int[optional]
 */
function pg_loread($large_object, $len = null) {}

/**
 * Read a large object and send straight to browser 
 *
 * @return int
 * @param  large_object resource
 */
function pg_loreadall($large_object) {}

/**
 * Delete a large object 
 *
 * @return bool
 * @param  connection resource[optional]
 * @param  large_object_oid string
 */
function pg_lounlink($connection = null, $large_object_oid) {}

/**
 * Write a large object 
 *
 * @return int
 * @param  large_object resource
 * @param  buf string
 * @param  len int[optional]
 */
function pg_lowrite($large_object, $buf, $len = null) {}

/**
 * Get meta_data 
 *
 * @return array
 * @param  db resource
 * @param  table string
 */
function pg_meta_data($db, $table) {}

/**
 * Return the number of fields in the result 
 *
 * @return int
 * @param  result resource
 */
function pg_num_fields($result) {}

/**
 * Return the number of rows in the result 
 *
 * @return int
 * @param  result resource
 */
function pg_num_rows($result) {}

/**
 * Return the number of fields in the result 
 *
 * @return int
 * @param  result resource
 */
function pg_numfields($result) {}

/**
 * Return the number of rows in the result 
 *
 * @return int
 * @param  result resource
 */
function pg_numrows($result) {}

/**
 * Get the options associated with the connection 
 *
 * @return string
 * @param  connection resource[optional]
 */
function pg_options($connection = null) {}

/**
 * Returns the value of a server parameter 
 *
 * @return string
 * @param  connection resource[optional]
 * @param  param_name string
 */
function pg_parameter_status($connection = null, $param_name) {}

/**
 * Open a persistent PostgreSQL connection 
 *
 * @return resource
 * @param  connection_string_|_ string
 * @param  port string
 * @param  options string[optional]
 * @param  tty string[optional]
 * @param  database string
 */
function pg_pconnect($connection_string_, $port, $options = null, $tty = null, $database) {}

/**
 * Ping database. If connection is bad, try to reconnect. 
 *
 * @return bool
 * @param  connection resource[optional]
 */
function pg_ping($connection = null) {}

/**
 * Return the port number associated with the connection 
 *
 * @return int
 * @param  connection resource[optional]
 */
function pg_port($connection = null) {}

/**
 * Prepare a query for future execution 
 *
 * @return resource
 * @param  connection resource[optional]
 * @param  stmtname string
 * @param  query string
 */
function pg_prepare($connection = null, $stmtname, $query) {}

/**
 * Send null-terminated string to backend server
 *
 * @return bool
 * @param  connection resource[optional]
 * @param  query string
 */
function pg_put_line($connection = null, $query) {}

/**
 * Execute a query 
 *
 * @return resource
 * @param  connection resource[optional]
 * @param  query string
 */
function pg_query($connection = null, $query) {}

/**
 * Execute a query 
 *
 * @return resource
 * @param  connection resource[optional]
 * @param  query string
 * @param  params array
 */
function pg_query_params($connection = null, $query, $params) {}

/**
 * Returns values from a result identifier 
 *
 * @return string
 * @param  result resource
 * @param  row_number int[optional]
 * @param  field_name mixed
 */
function pg_result($result, $row_number = null, $field_name) {}

/**
 * Get error message associated with result 
 *
 * @return string
 * @param  result resource
 */
function pg_result_error($result) {}

/**
 * Get error message field associated with result 
 *
 * @return string
 * @param  result resource
 * @param  fieldcode int
 */
function pg_result_error_field($result, $fieldcode) {}

/**
 * Set internal row offset 
 *
 * @return bool
 * @param  result resource
 * @param  offset int
 */
function pg_result_seek($result, $offset) {}

/**
 * Get status of query result 
 *
 * @return mixed
 * @param  result resource
 * @param  result_type long
 */
function pg_result_status($result, $result_type) {}

/**
 * Select records that has ids (id=>value) 
 *
 * @return mixed
 * @param  db resource
 * @param  table string
 * @param  ids array
 * @param  options int
 */
function pg_select($db, $table, $ids, $options) {}

/**
 * Executes prevriously prepared stmtname asynchronously 
 *
 * @return bool
 * @param  connection resource
 * @param  stmtname string
 * @param  params array
 */
function pg_send_execute($connection, $stmtname, $params) {}

/**
 * Asynchronously prepare a query for future execution 
 *
 * @return bool
 * @param  connection resource
 * @param  stmtname string
 * @param  query string
 */
function pg_send_prepare($connection, $stmtname, $query) {}

/**
 * Send asynchronous query 
 *
 * @return bool
 * @param  connection resource
 * @param  query string
 */
function pg_send_query($connection, $query) {}

/**
 * Send asynchronous parameterized query 
 *
 * @return bool
 * @param  connection resource
 * @param  query string
 */
function pg_send_query_params($connection, $query) {}

/**
 * Set client encoding 
 *
 * @return int
 * @param  connection resource[optional]
 * @param  encoding string
 */
function pg_set_client_encoding($connection = null, $encoding) {}

/**
 * Set error verbosity 
 *
 * @return int
 * @param  connection resource[optional]
 * @param  verbosity int
 */
function pg_set_error_verbosity($connection = null, $verbosity) {}

/**
 * Set client encoding 
 *
 * @return int
 * @param  connection resource[optional]
 * @param  encoding string
 */
function pg_setclientencoding($connection = null, $encoding) {}

/**
 * Enable tracing a PostgreSQL connection 
 *
 * @return bool
 * @param  filename string
 * @param  mode string[optional]
 * @param  connection resource[optional]
 */
function pg_trace($filename, $mode = null, $connection = null) {}

/**
 * Get transaction status 
 *
 * @return int
 * @param  connnection resource
 */
function pg_transaction_status($connnection) {}

/**
 * Return the tty name associated with the connection 
 *
 * @return string
 * @param  connection resource[optional]
 */
function pg_tty($connection = null) {}

/**
 * Unescape binary for bytea type  
 *
 * @return string
 * @param  data string
 */
function pg_unescape_bytea($data) {}

/**
 * Disable tracing of a PostgreSQL connection 
 *
 * @return bool
 * @param  connection resource[optional]
 */
function pg_untrace($connection = null) {}

/**
 * Update table using values (field=>value) and ids (id=>value) 
 *
 * @return mixed
 * @param  db resource
 * @param  table string
 * @param  fields array
 * @param  ids array
 * @param  options int
 */
function pg_update($db, $table, $fields, $ids, $options) {}

/**
 * Returns an array with client, protocol and server version (when available) 
 *
 * @return array
 * @param  connection resource[optional]
 */
function pg_version($connection = null) {}

/**
 * Return the special ID used to request the PHP logo in phpinfo screens
 *
 * @return string
 */
function php_egg_logo_guid() {}

/**
 * Return comma-separated string of .ini files parsed from the additional ini dir 
 *
 * @return string
 */
function php_ini_scanned_files() {}

/**
 * Return the special ID used to request the PHP logo in phpinfo screens
 *
 * @return string
 */
function php_logo_guid() {}

/**
 * Return the special ID used to request the PHP logo in phpinfo screens
 *
 * @return string
 */
function php_real_logo_guid() {}

/**
 * Return the current SAPI module name 
 *
 * @return string
 */
function php_sapi_name() {}

/**
 * 
 *
 * @return void
 * @param  INTERNAL_FUNCTION_PARAMETERS unknown
 * @param  st int
 */
function php_snmpv3($INTERNAL_FUNCTION_PARAMETERS, $st) {}

/**
 * Return source with stripped comments and whitespace 
 *
 * @return string
 * @param  file_name string
 */
function php_strip_whitespace($file_name) {}

/**
 * Return information about the system PHP was built on 
 *
 * @return string
 */
function php_uname() {}

/**
 * Prints the list of people who've contributed to the PHP project 
 *
 * @return bool
 * @param  flag int[optional]
 */
function phpcredits($flag = null) {}

/**
 * Output a page of useful information about PHP and the current request 
 *
 * @return bool
 * @param  what int[optional]
 */
function phpinfo($what = null) {}

/**
 * Return the current PHP version 
 *
 * @return string
 * @param  extension string[optional]
 */
function phpversion($extension = null) {}

/**
 * Returns an approximation of pi 
 *
 * @return float
 */
function pi() {}

/**
 * Get the value of a PHP configuration option - but search in zend.ini first... 
 *
 * @return string
 * @param  option_name string
 */
function platform_get_cfg_var($option_name) {}

/**
 * Convert PNG image to WBMP image 
 *
 * @return int
 * @param  f_org string
 * @param  f_dest string
 * @param  d_height int
 * @param  d_width int
 * @param  threshold int
 */
function png2wbmp($f_org, $f_dest, $d_height, $d_width, $threshold) {}

/**
 * Execute a command and open either a read or a write pipe to it 
 *
 * @return resource
 * @param  command string
 * @param  mode string
 */
function popen($command, $mode) {}

/**
 * Return the element currently pointed to by the internal array pointer 
 *
 * @return mixed
 * @param  array_arg array
 */
function pos($array_arg) {}

/**
 * Determine accessibility of a file (POSIX.1 5.6.3) 
 *
 * @return bool
 * @param  file string
 * @param  mode int[optional]
 */
function posix_access($file, $mode = null) {}

/**
 * Generate terminal path name (POSIX.1, 4.7.1) 
 *
 * @return string
 */
function posix_ctermid() {}

/**
 * Retrieve the error number set by the last posix function which failed. 
 *
 * @return int
 */
function posix_errno() {}

/**
 * Retrieve the error number set by the last posix function which failed. 
 *
 * @return int
 */
function posix_get_last_error() {}

/**
 * Get working directory pathname (POSIX.1, 5.2.2) 
 *
 * @return string
 */
function posix_getcwd() {}

/**
 * Get the current effective group id (POSIX.1, 4.2.1) 
 *
 * @return int
 */
function posix_getegid() {}

/**
 * Get the current effective user id (POSIX.1, 4.2.1) 
 *
 * @return int
 */
function posix_geteuid() {}

/**
 * Get the current group id (POSIX.1, 4.2.1) 
 *
 * @return int
 */
function posix_getgid() {}

/**
 * Group database access (POSIX.1, 9.2.1) 
 *
 * @return array
 * @param  gid long
 */
function posix_getgrgid($gid) {}

/**
 * Group database access (POSIX.1, 9.2.1) 
 *
 * @return array
 * @param  groupname string
 */
function posix_getgrnam($groupname) {}

/**
 * Get supplementary group id's (POSIX.1, 4.2.3) 
 *
 * @return array
 */
function posix_getgroups() {}

/**
 * Get user name (POSIX.1, 4.2.4) 
 *
 * @return string
 */
function posix_getlogin() {}

/**
 * Get the process group id of the specified process (This is not a POSIX function, but a SVR4ism, so we compile conditionally) 
 *
 * @return int
 */
function posix_getpgid() {}

/**
 * Get current process group id (POSIX.1, 4.3.1) 
 *
 * @return int
 */
function posix_getpgrp() {}

/**
 * Get the current process id (POSIX.1, 4.1.1) 
 *
 * @return int
 */
function posix_getpid() {}

/**
 * Get the parent process id (POSIX.1, 4.1.1) 
 *
 * @return int
 */
function posix_getppid() {}

/**
 * User database access (POSIX.1, 9.2.2) 
 *
 * @return array
 * @param  groupname string
 */
function posix_getpwnam($groupname) {}

/**
 * User database access (POSIX.1, 9.2.2) 
 *
 * @return array
 * @param  uid long
 */
function posix_getpwuid($uid) {}

/**
 * Get system resource consumption limits (This is not a POSIX function, but a BSDism and a SVR4ism. We compile conditionally) 
 *
 * @return array
 */
function posix_getrlimit() {}

/**
 * Get process group id of session leader (This is not a POSIX function, but a SVR4ism, so be compile conditionally) 
 *
 * @return int
 */
function posix_getsid() {}

/**
 * Get the current user id (POSIX.1, 4.2.1) 
 *
 * @return int
 */
function posix_getuid() {}

/**
 * Determine if filedesc is a tty (POSIX.1, 4.7.1) 
 *
 * @return bool
 * @param  fd int
 */
function posix_isatty($fd) {}

/**
 * Send a signal to a process (POSIX.1, 3.3.2) 
 *
 * @return bool
 * @param  pid int
 * @param  sig int
 */
function posix_kill($pid, $sig) {}

/**
 * Make a FIFO special file (POSIX.1, 5.4.2) 
 *
 * @return bool
 * @param  pathname string
 * @param  mode int
 */
function posix_mkfifo($pathname, $mode) {}

/**
 * Make a special or ordinary file (POSIX.1) 
 *
 * @return bool
 * @param  pathname string
 * @param  mode int
 * @param  major int[optional]
 * @param  minor int[optional]
 */
function posix_mknod($pathname, $mode, $major = null, $minor = null) {}

/**
 * Set effective group id 
 *
 * @return bool
 * @param  uid long
 */
function posix_setegid($uid) {}

/**
 * Set effective user id 
 *
 * @return bool
 * @param  uid long
 */
function posix_seteuid($uid) {}

/**
 * Set group id (POSIX.1, 4.2.2) 
 *
 * @return bool
 * @param  uid int
 */
function posix_setgid($uid) {}

/**
 * Set process group id for job control (POSIX.1, 4.3.3) 
 *
 * @return bool
 * @param  pid int
 * @param  pgid int
 */
function posix_setpgid($pid, $pgid) {}

/**
 * Create session and set process group id (POSIX.1, 4.3.2) 
 *
 * @return int
 */
function posix_setsid() {}

/**
 * Set user id (POSIX.1, 4.2.2) 
 *
 * @return bool
 * @param  uid long
 */
function posix_setuid($uid) {}

/**
 * Retrieve the system error message associated with the given errno. 
 *
 * @return string
 * @param  errno int
 */
function posix_strerror($errno) {}

/**
 * Get process times (POSIX.1, 4.5.2) 
 *
 * @return array
 */
function posix_times() {}

/**
 * Determine terminal device name (POSIX.1, 4.7.2) 
 *
 * @return string
 * @param  fd int
 */
function posix_ttyname($fd) {}

/**
 * Get system name (POSIX.1, 4.4.1) 
 *
 * @return array
 */
function posix_uname() {}

/**
 * Returns base raised to the power of exponent. Returns integer result when possible 
 *
 * @return number
 * @param  base number
 * @param  exponent number
 */
function pow($base, $exponent) {}

/**
 * Searches array and returns entries which match regex 
 *
 * @return array
 * @param  regex string
 * @param  input array
 */
function preg_grep($regex, $input) {}

/**
 * Perform a Perl-style regular expression match 
 *
 * @return int
 * @param  pattern string
 * @param  subject string
 * @param  subpatterns array[optional]
 * @param  flags int[optional]
 * @param  offset int[optional]
 */
function preg_match($pattern, $subject, $subpatterns = null, $flags = null, $offset = null) {}

/**
 * Perform a Perl-style global regular expression match 
 *
 * @return int
 * @param  pattern string
 * @param  subject string
 * @param  subpatterns array
 * @param  flags int[optional]
 * @param  offset int[optional]
 */
function preg_match_all($pattern, $subject, $subpatterns, $flags = null, $offset = null) {}

/**
 * Quote regular expression characters plus an optional character 
 *
 * @return string
 * @param  str string
 * @param  delim_char string
 */
function preg_quote($str, $delim_char) {}

/**
 * Perform Perl-style regular expression replacement. 
 *
 * @return mixed
 * @param  regex mixed
 * @param  replace mixed
 * @param  subject mixed
 * @param  limit int[optional]
 * @param  count unknown[optional]
 */
function preg_replace($regex, $replace, $subject, $limit = null, $count = null) {}

/**
 * Perform Perl-style regular expression replacement using replacement callback. 
 *
 * @return mixed
 * @param  regex mixed
 * @param  callback mixed
 * @param  subject mixed
 * @param  limit int[optional]
 * @param  count unknown[optional]
 */
function preg_replace_callback($regex, $callback, $subject, $limit = null, $count = null) {}

/**
 * Split string into an array using a perl-style regular expression as a delimiter 
 *
 * @return array
 * @param  pattern string
 * @param  subject string
 * @param  limit int[optional]
 * @param  flags int[optional]
 */
function preg_split($pattern, $subject, $limit = null, $flags = null) {}

/**
 * Move array argument's internal pointer to the previous element and return it 
 *
 * @return mixed
 * @param  array_arg array
 */
function prev($array_arg) {}

/**
 * Prints out or returns information about the specified variable 
 *
 * @return bool
 * @param  var mixed
 * @param  return bool[optional]
 */
function print_r($var, $return = null) {}

/**
 * Output a formatted string 
 *
 * @return int
 * @param  format string
 * @param  arg1 mixed[optional]
 * @vararg ... mixed
 */
function printf($format, $arg1 = null) {}

/**
 * close a process opened by proc_open 
 *
 * @return int
 * @param  process resource
 */
function proc_close($process) {}

/**
 * get information about a process opened by proc_open 
 *
 * @return array
 * @param  process resource
 */
function proc_get_status($process) {}

/**
 * Change the priority of the current process 
 *
 * @return bool
 * @param  priority int
 */
function proc_nice($priority) {}

/**
 * Run a process with more control over it's file descriptors 
 *
 * @return resource
 * @param  command string
 * @param  descriptorspec array
 * @param  pipes array
 * @param  cwd string[optional]
 * @param  env array[optional]
 * @param  other_options array[optional]
 */
function proc_open($command, $descriptorspec, &$pipes, $cwd = null, $env = null, $other_options = null) {}

/**
 * kill a process opened by proc_open 
 *
 * @return int
 * @param  process resource
 * @param  signal long[optional]
 */
function proc_terminate($process, $signal = null) {}

/**
 * Checks if the object or class has a property 
 *
 * @return bool
 * @param  object_or_class mixed
 * @param  property_name string
 */
function property_exists($object_or_class, $property_name) {}

/**
 * Adds a word to a personal list 
 *
 * @return bool
 * @param  pspell int
 * @param  word string
 */
function pspell_add_to_personal($pspell, $word) {}

/**
 * Adds a word to the current session 
 *
 * @return bool
 * @param  pspell int
 * @param  word string
 */
function pspell_add_to_session($pspell, $word) {}

/**
 * Returns true if word is valid 
 *
 * @return bool
 * @param  pspell int
 * @param  word string
 */
function pspell_check($pspell, $word) {}

/**
 * Clears the current session 
 *
 * @return bool
 * @param  pspell int
 */
function pspell_clear_session($pspell) {}

/**
 * Create a new config to be used later to create a manager 
 *
 * @return int
 * @param  language string
 * @param  spelling string[optional]
 * @param  jargon string[optional]
 * @param  encoding string[optional]
 */
function pspell_config_create($language, $spelling = null, $jargon = null, $encoding = null) {}

/**
 * location of language data files 
 *
 * @return bool
 * @param  conf int
 * @param  directory string
 */
function pspell_config_data_dir($conf, $directory) {}

/**
 * location of the main word list 
 *
 * @return bool
 * @param  conf int
 * @param  directory string
 */
function pspell_config_dict_dir($conf, $directory) {}

/**
 * Ignore words <= n chars 
 *
 * @return bool
 * @param  conf int
 * @param  ignore int
 */
function pspell_config_ignore($conf, $ignore) {}

/**
 * Select mode for config (PSPELL_FAST, PSPELL_NORMAL or PSPELL_BAD_SPELLERS) 
 *
 * @return bool
 * @param  conf int
 * @param  mode long
 */
function pspell_config_mode($conf, $mode) {}

/**
 * Use a personal dictionary for this config 
 *
 * @return bool
 * @param  conf int
 * @param  personal string
 */
function pspell_config_personal($conf, $personal) {}

/**
 * Use a personal dictionary with replacement pairs for this config 
 *
 * @return bool
 * @param  conf int
 * @param  repl string
 */
function pspell_config_repl($conf, $repl) {}

/**
 * Consider run-together words as valid components 
 *
 * @return bool
 * @param  conf int
 * @param  runtogether bool
 */
function pspell_config_runtogether($conf, $runtogether) {}

/**
 * Save replacement pairs when personal list is saved for this config 
 *
 * @return bool
 * @param  conf int
 * @param  save bool
 */
function pspell_config_save_repl($conf, $save) {}

/**
 * Load a dictionary 
 *
 * @return int
 * @param  language string
 * @param  spelling string[optional]
 * @param  jargon string[optional]
 * @param  encoding string[optional]
 * @param  mode int[optional]
 */
function pspell_new($language, $spelling = null, $jargon = null, $encoding = null, $mode = null) {}

/**
 * Load a dictionary based on the given config 
 *
 * @return int
 * @param  config int
 */
function pspell_new_config($config) {}

/**
 * Load a dictionary with a personal wordlist
 *
 * @return int
 * @param  personal string
 * @param  language string
 * @param  spelling string[optional]
 * @param  jargon string[optional]
 * @param  encoding string[optional]
 * @param  mode int[optional]
 */
function pspell_new_personal($personal, $language, $spelling = null, $jargon = null, $encoding = null, $mode = null) {}

/**
 * Saves the current (personal) wordlist 
 *
 * @return bool
 * @param  pspell int
 */
function pspell_save_wordlist($pspell) {}

/**
 * Notify the dictionary of a user-selected replacement 
 *
 * @return bool
 * @param  pspell int
 * @param  misspell string
 * @param  correct string
 */
function pspell_store_replacement($pspell, $misspell, $correct) {}

/**
 * Returns array of suggestions 
 *
 * @return array
 * @param  pspell int
 * @param  word string
 */
function pspell_suggest($pspell, $word) {}

/**
 * Set the value of an environment variable 
 *
 * @return bool
 * @param  setting string
 */
function putenv($setting) {}

/**
 * Convert a quoted-printable string to an 8 bit string 
 *
 * @return string
 * @param  str string
 */
function quoted_printable_decode($str) {}

/**
 * Quotes meta characters 
 *
 * @return string
 * @param  str string
 */
function quotemeta($str) {}

/**
 * Converts the radian number to the equivalent number in degrees 
 *
 * @return float
 * @param  number float
 */
function rad2deg($number) {}

/**
 * Returns a random number 
 *
 * @return int
 * @param  min int[optional]
 * @param  max int
 */
function rand($min = null, $max) {}

/**
 * Create an array containing the range of integers or characters from low to high (inclusive) 
 *
 * @return array
 * @param  low mixed
 * @param  high mixed
 * @param  step int
 */
function range($low, $high, $step) {}

/**
 * Decodes URL-encodes string 
 *
 * @return string
 * @param  str string
 */
function rawurldecode($str) {}

/**
 * URL-encodes string 
 *
 * @return string
 * @param  str string
 */
function rawurlencode($str) {}

/**
 * Reads header data from the JPEG/TIFF image filename and optionally reads the internal thumbnails 
 *
 * @return array
 * @param  filename string
 * @param  sections_needed unknown[optional]
 * @param  sub_arrays unknown[optional]
 * @param  read_thumbnail unknown
 */
function read_exif_data($filename, $sections_needed = null, $sub_arrays = null, $read_thumbnail) {}

/**
 * Read directory entry from dir_handle 
 *
 * @return string
 * @param  dir_handle resource[optional]
 */
function readdir($dir_handle = null) {}

/**
 * Output a file or a URL 
 *
 * @return int
 * @param  filename string
 * @param  use_include_path bool[optional]
 * @param  context resource
 */
function readfile($filename, $use_include_path = null, $context) {}

/**
 * Output a .gz-file 
 *
 * @return int
 * @param  filename string
 * @param  use_include_path int[optional]
 */
function readgzfile($filename, $use_include_path = null) {}

/**
 * Reads a line 
 *
 * @return string
 * @param  prompt string[optional]
 */
function readline($prompt = null) {}

/**
 * Adds a line to the history 
 *
 * @return bool
 * @param  prompt string[optional]
 */
function readline_add_history($prompt = null) {}

/**
 * Initializes the readline callback interface and terminal, prints the prompt and returns immediately 
 *
 * @return bool
 * @param  prompt string
 * @param  callback mixed
 */
function readline_callback_handler_install($prompt, $callback) {}

/**
 * Removes a previously installed callback handler and restores terminal settings 
 *
 * @return bool
 */
function readline_callback_handler_remove() {}

/**
 * Informs the readline callback interface that a character is ready for input 
 *
 * @return void
 */
function readline_callback_read_char() {}

/**
 * Clears the history 
 *
 * @return bool
 */
function readline_clear_history() {}

/**
 * Readline completion function? 
 *
 * @return bool
 * @param  funcname string
 */
function readline_completion_function($funcname) {}

/**
 * Gets/sets various internal readline variables. 
 *
 * @return mixed
 * @param  varname string[optional]
 * @param  newvalue string[optional]
 */
function readline_info($varname = null, $newvalue = null) {}

/**
 * Lists the history 
 *
 * @return array
 */
function readline_list_history() {}

/**
 * Inform readline that the cursor has moved to a new line 
 *
 * @return void
 */
function readline_on_new_line() {}

/**
 * Reads the history 
 *
 * @return bool
 * @param  filename string[optional]
 * @param  from int[optional]
 * @param  to int[optional]
 */
function readline_read_history($filename = null, $from = null, $to = null) {}

/**
 * Ask readline to redraw the display 
 *
 * @return void
 */
function readline_redisplay() {}

/**
 * Writes the history 
 *
 * @return bool
 * @param  filename string[optional]
 */
function readline_write_history($filename = null) {}

/**
 * Return the target of a symbolic link 
 *
 * @return string
 * @param  filename string
 */
function readlink($filename) {}

/**
 * Return the resolved path 
 *
 * @return string
 * @param  path string
 */
function realpath($path) {}

/**
 * Recode string str according to request string 
 *
 * @return string
 * @param  request string
 * @param  str string
 */
function recode($request, $str) {}

/**
 * Recode file input into file output according to request 
 *
 * @return bool
 * @param  request string
 * @param  input resource
 * @param  output resource
 */
function recode_file($request, $input, $output) {}

/**
 * Recode string str according to request string 
 *
 * @return string
 * @param  request string
 * @param  str string
 */
function recode_string($request, $str) {}

/**
 * 
 *
 * @return boolean
 * @param  prefix string
 * @param  uri string
 */
function registerNamespace($prefix, $uri) {}

/**
 * Register a user-defined event handler function.  Returns false on error 
 *
 * @return bool
 * @param  handler_function callback
 * @param  handler_name string
 * @param  handler_type integer
 */
function register_event_handler($handler_function, $handler_name, $handler_type) {}

/**
 * Register a user-level function to be called on request termination 
 *
 * @return void
 * @param  function_name string
 */
function register_shutdown_function($function_name) {}

/**
 * Registers a tick callback function 
 *
 * @return bool
 * @param  function_name string
 * @param  arg mixed[optional]
 * @vararg ... mixed
 */
function register_tick_function($function_name, $arg = null) {}

/**
 * 
 *
 * @return boolean
 */
function reload_zend_ini_file() {}

/**
 * 
 *
 * @return DOMNode
 * @param  name string
 */
function removeNamedItem($name) {}

/**
 * 
 *
 * @return DOMNode
 * @param  namespaceURI string
 * @param  localName string
 */
function removeNamedItemNS($namespaceURI, $localName) {}

/**
 * Rename a file 
 *
 * @return bool
 * @param  old_name string
 * @param  new_name string
 * @param  context resource
 */
function rename($old_name, $new_name, $context) {}

/**
 * Set array argument's internal pointer to the first element and return it 
 *
 * @return mixed
 * @param  array_arg array
 */
function reset($array_arg) {}

/**
 * Restores the previously defined error handler function 
 *
 * @return bool
 */
function restore_error_handler() {}

/**
 * Restores the previously defined exception handler function 
 *
 * @return bool
 */
function restore_exception_handler() {}

/**
 * Restore the value of the include_path configuration option 
 *
 * @return void
 */
function restore_include_path() {}

/**
 * Rewind the position of a file pointer 
 *
 * @return bool
 * @param  fp resource
 */
function rewind($fp) {}

/**
 * Rewind dir_handle back to the start 
 *
 * @return void
 * @param  dir_handle resource[optional]
 */
function rewinddir($dir_handle = null) {}

/**
 * Remove a directory 
 *
 * @return bool
 * @param  dirname string
 * @param  context resource
 */
function rmdir($dirname, $context) {}

/**
 * Returns the number rounded to specified precision 
 *
 * @return float
 * @param  number float
 * @param  precision int[optional]
 */
function round($number, $precision = null) {}

/**
 * Sort an array in reverse order 
 *
 * @return bool
 * @param  array_arg array
 * @param  sort_flags int[optional]
 */
function rsort($array_arg, $sort_flags = null) {}

/**
 * Removes trailing whitespace 
 *
 * @return string
 * @param  str string
 * @param  character_mask string[optional]
 */
function rtrim($str, $character_mask = null) {}

/**
 * List files & directories inside the specified path 
 *
 * @return array
 * @param  dir string
 * @param  sorting_order int[optional]
 * @param  context resource[optional]
 */
function scandir($dir, $sorting_order = null, $context = null) {}

/**
 * Acquires the semaphore with the given id, blocking if necessary 
 *
 * @return bool
 * @param  id resource
 */
function sem_acquire($id) {}

/**
 * Return an id for the semaphore with the given key, and allow max_acquire (default 1) processes to acquire it simultaneously 
 *
 * @return resource
 * @param  key int
 * @param  max_acquire int[optional]
 * @param  perm int[optional]
 * @param  auto_release int[optional]
 */
function sem_get($key, $max_acquire = null, $perm = null, $auto_release = null) {}

/**
 * Releases the semaphore with the given id 
 *
 * @return bool
 * @param  id resource
 */
function sem_release($id) {}

/**
 * Removes semaphore from Unix systems 
 *
 * @return bool
 * @param  id resource
 */
function sem_remove($id) {}

/**
 * Returns a string representation of variable (which can later be unserialized) 
 *
 * @return string
 * @param  variable mixed
 */
function serialize($variable) {}

/**
 * Return the current cache expire. If new_cache_expire is given, the current cache_expire is replaced with new_cache_expire 
 *
 * @return int
 * @param  new_cache_expire int[optional]
 */
function session_cache_expire($new_cache_expire = null) {}

/**
 * Return the current cache limiter. If new_cache_limited is given, the current cache_limiter is replaced with new_cache_limiter 
 *
 * @return string
 * @param  new_cache_limiter string[optional]
 */
function session_cache_limiter($new_cache_limiter = null) {}

/**
 * Write session data and end session 
 *
 * @return void
 */
function session_commit() {}

/**
 * Deserializes data and reinitializes the variables 
 *
 * @return bool
 * @param  data string
 */
function session_decode($data) {}

/**
 * Destroy the current session and all data associated with it 
 *
 * @return bool
 */
function session_destroy() {}

/**
 * Serializes the current setup and returns the serialized representation 
 *
 * @return string
 */
function session_encode() {}

/**
 * Return the session cookie parameters 
 *
 * @return array
 */
function session_get_cookie_params() {}

/**
 * Return the current session id. If newid is given, the session id is replaced with newid 
 *
 * @return string
 * @param  newid string[optional]
 */
function session_id($newid = null) {}

/**
 * Checks if a variable is registered in session 
 *
 * @return bool
 * @param  varname string
 */
function session_is_registered($varname) {}

/**
 * Return the current module name used for accessing session data. If newname is given, the module name is replaced with newname 
 *
 * @return string
 * @param  newname string[optional]
 */
function session_module_name($newname = null) {}

/**
 * Return the current session name. If newname is given, the session name is replaced with newname 
 *
 * @return string
 * @param  newname string[optional]
 */
function session_name($newname = null) {}

/**
 * Update the current session id with a newly generated one. If delete_old_session is set to true, remove the old session. 
 *
 * @return bool
 * @param  delete_old_session bool[optional]
 */
function session_regenerate_id($delete_old_session = null) {}

/**
 * Adds varname(s) to the list of variables which are freezed at the session end 
 *
 * @return bool
 * @param  var_names mixed
 * @vararg ... mixed
 */
function session_register($var_names) {}

/**
 * Return the current save path passed to module_name. If newname is given, the save path is replaced with newname 
 *
 * @return string
 * @param  newname string[optional]
 */
function session_save_path($newname = null) {}

/**
 * Set session cookie parameters 
 *
 * @return void
 * @param  lifetime int
 * @param  path string[optional]
 * @param  domain string[optional]
 * @param  secure bool[optional]
 */
function session_set_cookie_params($lifetime, $path = null, $domain = null, $secure = null) {}

/**
 * Sets user-level functions 
 *
 * @return bool
 * @param  open string
 * @param  close string
 * @param  read string
 * @param  write string
 * @param  destroy string
 * @param  gc string
 */
function session_set_save_handler($open, $close, $read, $write, $destroy, $gc) {}

/**
 * Begin session - reinitializes freezed variables, registers browsers etc 
 *
 * @return bool
 */
function session_start() {}

/**
 * Removes varname from the list of variables which are freezed at the session end 
 *
 * @return bool
 * @param  varname string
 */
function session_unregister($varname) {}

/**
 * Unset all registered variables 
 *
 * @return void
 */
function session_unset() {}

/**
 * Write session data and end session 
 *
 * @return void
 */
function session_write_close() {}

/**
 * 
 *
 * @return DOMNode
 * @param  arg DOMNode
 */
function setNamedItem($arg) {}

/**
 * 
 *
 * @return DOMNode
 * @param  arg DOMNode
 */
function setNamedItemNS($arg) {}

/**
 * Sets a user-defined error handler function.  Returns the previously defined error handler, or false on error 
 *
 * @return mixed
 * @param  error_handler string
 * @param  error_types int[optional]
 */
function set_error_handler($error_handler, $error_types = null) {}

/**
 * Sets a user-defined exception handler function.  Returns the previously defined exception handler, or false on error 
 *
 * @return string
 * @param  exception_handler callable
 */
function set_exception_handler($exception_handler) {}

/**
 * Set file write buffer 
 *
 * @return int
 * @param  fp resource
 * @param  buffer int
 */
function set_file_buffer($fp, $buffer) {}

/**
 * Sets the include_path configuration option 
 *
 * @return string
 * @param  new_include_path string
 */
function set_include_path($new_include_path) {}

/**
 * Set the current active configuration setting of magic_quotes_runtime and return previous 
 *
 * @return bool
 * @param  new_setting int
 */
function set_magic_quotes_runtime($new_setting) {}

/**
 * Set blocking/non-blocking mode on a socket 
 *
 * @return bool
 * @param  socket resource
 * @param  mode int
 */
function set_socket_blocking($socket, $mode) {}

/**
 * Sets the maximum time a script can run 
 *
 * @return void
 * @param  seconds int
 */
function set_time_limit($seconds) {}

/**
 * Send a cookie 
 *
 * @return bool
 * @param  name string
 * @param  value string[optional]
 * @param  expires int[optional]
 * @param  path string[optional]
 * @param  domain string[optional]
 * @param  secure bool[optional]
 */
function setcookie($name, $value = null, $expires = null, $path = null, $domain = null, $secure = null) {}

/**
 * Set locale information 
 *
 * @return string
 * @param  category mixed
 * @param  locale string
 * @vararg ... string
 */
function setlocale($category, $locale) {}

/**
 * Send a cookie with no url encoding of the value 
 *
 * @return bool
 * @param  name string
 * @param  value string[optional]
 * @param  expires int[optional]
 * @param  path string[optional]
 * @param  domain string[optional]
 * @param  secure bool[optional]
 */
function setrawcookie($name, $value = null, $expires = null, $path = null, $domain = null, $secure = null) {}

/**
 * Set the type of the variable 
 *
 * @return bool
 * @param  var mixed
 * @param  type string
 */
function settype($var, $type) {}

/**
 * Calculate the sha1 hash of a string 
 *
 * @return string
 * @param  str string
 * @param  raw_output bool[optional]
 */
function sha1($str, $raw_output = null) {}

/**
 * Calculate the sha1 hash of given filename 
 *
 * @return string
 * @param  filename string
 * @param  raw_output bool[optional]
 */
function sha1_file($filename, $raw_output = null) {}

/**
 * Execute command via shell and return complete output as string 
 *
 * @return string
 * @param  cmd string
 */
function shell_exec($cmd) {}

/**
 * Creates or open a shared memory segment 
 *
 * @return int
 * @param  key int
 * @param  memsize int[optional]
 * @param  perm int[optional]
 */
function shm_attach($key, $memsize = null, $perm = null) {}

/**
 * Disconnects from shared memory segment 
 *
 * @return bool
 * @param  shm_identifier int
 */
function shm_detach($shm_identifier) {}

/**
 * Returns a variable from shared memory 
 *
 * @return mixed
 * @param  id int
 * @param  variable_key int
 */
function shm_get_var($id, $variable_key) {}

/**
 * Inserts or updates a variable in shared memory 
 *
 * @return bool
 * @param  shm_identifier int
 * @param  variable_key int
 * @param  variable mixed
 */
function shm_put_var($shm_identifier, $variable_key, $variable) {}

/**
 * Removes shared memory from Unix systems 
 *
 * @return bool
 * @param  shm_identifier int
 */
function shm_remove($shm_identifier) {}

/**
 * Removes variable from shared memory 
 *
 * @return bool
 * @param  id int
 * @param  variable_key int
 */
function shm_remove_var($id, $variable_key) {}

/**
 * closes a shared memory segment 
 *
 * @return void
 * @param  shmid int
 */
function shmop_close($shmid) {}

/**
 * mark segment for deletion 
 *
 * @return bool
 * @param  shmid int
 */
function shmop_delete($shmid) {}

/**
 * gets and attaches a shared memory segment 
 *
 * @return int
 * @param  key int
 * @param  flags string
 * @param  mode int
 * @param  size int
 */
function shmop_open($key, $flags, $mode, $size) {}

/**
 * reads from a shm segment 
 *
 * @return string
 * @param  shmid int
 * @param  start int
 * @param  count int
 */
function shmop_read($shmid, $start, $count) {}

/**
 * returns the shm size 
 *
 * @return int
 * @param  shmid int
 */
function shmop_size($shmid) {}

/**
 * writes to a shared memory segment 
 *
 * @return int
 * @param  shmid int
 * @param  data string
 * @param  offset int
 */
function shmop_write($shmid, $data, $offset) {}

/**
 * Syntax highlight a source file 
 *
 * @return int
 * @param  file_name string
 * @param  return bool[optional]
 */
function show_source($file_name, $return = null) {}

/**
 * Randomly shuffle the contents of an array 
 *
 * @return bool
 * @param  array_arg array
 */
function shuffle($array_arg) {}

/**
 * Calculates the similarity between two strings 
 *
 * @return int
 * @param  str1 string
 * @param  str2 string
 * @param  percent float[optional]
 */
function similar_text($str1, $str2, $percent = null) {}

/**
 * Get a simplexml_element object from dom to allow for processing 
 *
 * @return SimpleXMLElement
 * @param  node domNode
 * @param  class_name string[optional]
 */
function simplexml_import_dom($node, $class_name = null) {}

/**
 * Load a filename and return a simplexml_element object to allow for processing 
 *
 * @return object
 * @param  filename string
 * @param  class_name string[optional]
 * @param  options int[optional]
 */
function simplexml_load_file($filename, $class_name = null, $options = null) {}

/**
 * Load a string and return a simplexml_element object to allow for processing 
 *
 * @return object
 * @param  data string
 * @param  class_name string[optional]
 * @param  options int[optional]
 */
function simplexml_load_string($data, $class_name = null, $options = null) {}

/**
 * Returns the sine of the number in radians 
 *
 * @return float
 * @param  number float
 */
function sin($number) {}

/**
 * Returns the hyperbolic sine of the number, defined as (exp(number) - exp(-number))/2 
 *
 * @return float
 * @param  number float
 */
function sinh($number) {}

/**
 * Count the number of elements in a variable (usually an array) 
 *
 * @return int
 * @param  var mixed
 * @param  mode int[optional]
 */
function sizeof($var, $mode = null) {}

/**
 * Delay for a given number of seconds 
 *
 * @return int
 * @param  seconds int
 */
function sleep($seconds) {}

/**
 * Adds a header to the current message. 
 *
 * @return string
 * @param  headerf string
 * @param  headerv string
 */
function smfi_addheader($headerf, $headerv) {}

/**
 * Add a recipient to the message envelope. 
 *
 * @return string
 * @param  rcpt string
 */
function smfi_addrcpt($rcpt) {}

/**
 * Changes a header's value for the current message. 
 *
 * @return string
 * @param  headerf string
 * @param  headerv string
 */
function smfi_chgheader($headerf, $headerv) {}

/**
 * Removes the named recipient from the current message's envelope. 
 *
 * @return string
 * @param  rcpt string
 */
function smfi_delrcpt($rcpt) {}

/**
 * Returns the value of the given macro or NULL if the macro is not defined. 
 *
 * @return string
 * @param  macro string
 */
function smfi_getsymval($macro) {}

/**
 * 
 *
 * @return string
 * @param  body string
 */
function smfi_replacebody($body) {}

/**
 * Sets the flags describing the actions the filter may take. 
 *
 * @return string
 * @param  flags long
 */
function smfi_setflags($flags) {}

/**
 * 
 *
 * @return string
 * @param  rcode string
 * @param  xcode string
 * @param  message string
 */
function smfi_setreply($rcode, $xcode, $message) {}

/**
 * Sets the number of seconds libmilter will wait for an MTA connection before timing out a socket. 
 *
 * @return string
 * @param  timeout long
 */
function smfi_settimeout($timeout) {}

/**
 * Fetch a SNMP object 
 *
 * @return string
 * @param  host string
 * @param  community string
 * @param  object_id string
 * @param  timeout int[optional]
 * @param  retries int[optional]
 */
function snmp2_get($host, $community, $object_id, $timeout = null, $retries = null) {}

/**
 * Fetch a SNMP object 
 *
 * @return string
 * @param  host string
 * @param  community string
 * @param  object_id string
 * @param  timeout int[optional]
 * @param  retries int[optional]
 */
function snmp2_getnext($host, $community, $object_id, $timeout = null, $retries = null) {}

/**
 * Return all objects including their respective object id withing the specified one 
 *
 * @return array
 * @param  host string
 * @param  community string
 * @param  object_id string
 * @param  timeout int[optional]
 * @param  retries int[optional]
 */
function snmp2_real_walk($host, $community, $object_id, $timeout = null, $retries = null) {}

/**
 * Set the value of a SNMP object 
 *
 * @return int
 * @param  host string
 * @param  community string
 * @param  object_id string
 * @param  type string
 * @param  value mixed
 * @param  timeout int[optional]
 * @param  retries int[optional]
 */
function snmp2_set($host, $community, $object_id, $type, $value, $timeout = null, $retries = null) {}

/**
 * Return all objects under the specified object id 
 *
 * @return array
 * @param  host string
 * @param  community string
 * @param  object_id string
 * @param  timeout int[optional]
 * @param  retries int[optional]
 */
function snmp2_walk($host, $community, $object_id, $timeout = null, $retries = null) {}

/**
 * Fetch the value of a SNMP object 
 *
 * @return int
 * @param  host string
 * @param  sec_name string
 * @param  sec_level string
 * @param  auth_protocol string
 * @param  auth_passphrase string
 * @param  priv_protocol string
 * @param  priv_passphrase string
 * @param  object_id string
 * @param  timeout int[optional]
 * @param  retries int[optional]
 */
function snmp3_get($host, $sec_name, $sec_level, $auth_protocol, $auth_passphrase, $priv_protocol, $priv_passphrase, $object_id, $timeout = null, $retries = null) {}

/**
 * Fetch the value of a SNMP object 
 *
 * @return int
 * @param  host string
 * @param  sec_name string
 * @param  sec_level string
 * @param  auth_protocol string
 * @param  auth_passphrase string
 * @param  priv_protocol string
 * @param  priv_passphrase string
 * @param  object_id string
 * @param  timeout int[optional]
 * @param  retries int[optional]
 */
function snmp3_getnext($host, $sec_name, $sec_level, $auth_protocol, $auth_passphrase, $priv_protocol, $priv_passphrase, $object_id, $timeout = null, $retries = null) {}

/**
 * Fetch the value of a SNMP object 
 *
 * @return int
 * @param  host string
 * @param  sec_name string
 * @param  sec_level string
 * @param  auth_protocol string
 * @param  auth_passphrase string
 * @param  priv_protocol string
 * @param  priv_passphrase string
 * @param  object_id string
 * @param  timeout int[optional]
 * @param  retries int[optional]
 */
function snmp3_real_walk($host, $sec_name, $sec_level, $auth_protocol, $auth_passphrase, $priv_protocol, $priv_passphrase, $object_id, $timeout = null, $retries = null) {}

/**
 * Fetch the value of a SNMP object 
 *
 * @return int
 * @param  host string
 * @param  sec_name string
 * @param  sec_level string
 * @param  auth_protocol string
 * @param  auth_passphrase string
 * @param  priv_protocol string
 * @param  priv_passphrase string
 * @param  object_id string
 * @param  type string
 * @param  value mixed
 * @param  timeout int[optional]
 * @param  retries int[optional]
 */
function snmp3_set($host, $sec_name, $sec_level, $auth_protocol, $auth_passphrase, $priv_protocol, $priv_passphrase, $object_id, $type, $value, $timeout = null, $retries = null) {}

/**
 * Fetch the value of a SNMP object 
 *
 * @return int
 * @param  host string
 * @param  sec_name string
 * @param  sec_level string
 * @param  auth_protocol string
 * @param  auth_passphrase string
 * @param  priv_protocol string
 * @param  priv_passphrase string
 * @param  object_id string
 * @param  timeout int[optional]
 * @param  retries int[optional]
 */
function snmp3_walk($host, $sec_name, $sec_level, $auth_protocol, $auth_passphrase, $priv_protocol, $priv_passphrase, $object_id, $timeout = null, $retries = null) {}

/**
 * Return the current status of quick_print 
 *
 * @return bool
 */
function snmp_get_quick_print() {}

/**
 * Return the method how the SNMP values will be returned 
 *
 * @return int
 */
function snmp_get_valueretrieval() {}

/**
 * Reads and parses a MIB file into the active MIB tree. 
 *
 * @return bool
 * @param  filename string
 */
function snmp_read_mib($filename) {}

/**
 * Return all values that are enums with their enum value instead of the raw integer 
 *
 * @return void
 * @param  enum_print int
 */
function snmp_set_enum_print($enum_print) {}

/**
 * Return all objects including their respective object id withing the specified one 
 *
 * @return void
 * @param  oid_numeric_print int
 */
function snmp_set_oid_numeric_print($oid_numeric_print) {}

/**
 * Return all objects including their respective object id withing the specified one 
 *
 * @return void
 * @param  quick_print int
 */
function snmp_set_quick_print($quick_print) {}

/**
 * Specify the method how the SNMP values will be returned 
 *
 * @return void
 * @param  method int
 */
function snmp_set_valueretrieval($method) {}

/**
 * Fetch a SNMP object 
 *
 * @return string
 * @param  host string
 * @param  community string
 * @param  object_id string
 * @param  timeout int[optional]
 * @param  retries int[optional]
 */
function snmpget($host, $community, $object_id, $timeout = null, $retries = null) {}

/**
 * Fetch a SNMP object 
 *
 * @return string
 * @param  host string
 * @param  community string
 * @param  object_id string
 * @param  timeout int[optional]
 * @param  retries int[optional]
 */
function snmpgetnext($host, $community, $object_id, $timeout = null, $retries = null) {}

/**
 * Return all objects including their respective object id withing the specified one 
 *
 * @return array
 * @param  host string
 * @param  community string
 * @param  object_id string
 * @param  timeout int[optional]
 * @param  retries int[optional]
 */
function snmprealwalk($host, $community, $object_id, $timeout = null, $retries = null) {}

/**
 * Set the value of a SNMP object 
 *
 * @return bool
 * @param  host string
 * @param  community string
 * @param  object_id string
 * @param  type string
 * @param  value mixed
 * @param  timeout int[optional]
 * @param  retries int[optional]
 */
function snmpset($host, $community, $object_id, $type, $value, $timeout = null, $retries = null) {}

/**
 * Return all objects under the specified object id 
 *
 * @return array
 * @param  host string
 * @param  community string
 * @param  object_id string
 * @param  timeout int[optional]
 * @param  retries int[optional]
 */
function snmpwalk($host, $community, $object_id, $timeout = null, $retries = null) {}

/**
 * Return all objects including their respective object id withing the specified one 
 *
 * @return array
 * @param  host string
 * @param  community string
 * @param  object_id string
 * @param  timeout int[optional]
 * @param  retries int[optional]
 */
function snmpwalkoid($host, $community, $object_id, $timeout = null, $retries = null) {}

/**
 * Accepts a connection on the listening socket fd 
 *
 * @return resource
 * @param  socket resource
 */
function socket_accept($socket) {}

/**
 * Binds an open socket to a listening port, port is only specified in AF_INET family. 
 *
 * @return bool
 * @param  socket resource
 * @param  addr string
 * @param  port int[optional]
 */
function socket_bind($socket, $addr, $port = null) {}

/**
 * Clears the error on the socket or the last error code. 
 *
 * @return void
 * @param  socket resource[optional]
 */
function socket_clear_error($socket = null) {}

/**
 * Closes a file descriptor 
 *
 * @return void
 * @param  socket resource
 */
function socket_close($socket) {}

/**
 * Opens a connection to addr:port on the socket specified by socket 
 *
 * @return bool
 * @param  socket resource
 * @param  addr string
 * @param  port int[optional]
 */
function socket_connect($socket, $addr, $port = null) {}

/**
 * Creates an endpoint for communication in the domain specified by domain, of type specified by type 
 *
 * @return resource
 * @param  domain int
 * @param  type int
 * @param  protocol int
 */
function socket_create($domain, $type, $protocol) {}

/**
 * Opens a socket on port to accept connections 
 *
 * @return resource
 * @param  port int
 * @param  backlog int
 */
function socket_create_listen($port, $backlog) {}

/**
 * Creates a pair of indistinguishable sockets and stores them in fds. 
 *
 * @return bool
 * @param  domain int
 * @param  type int
 * @param  protocol int
 * @param  fd array
 */
function socket_create_pair($domain, $type, $protocol, &$fd) {}

/**
 * Gets socket options for the socket 
 *
 * @return mixed
 * @param  socket resource
 * @param  level int
 * @param  optname int
 */
function socket_get_option($socket, $level, $optname) {}

/**
 * Retrieves header/meta data from streams/file pointers 
 *
 * @return array
 * @param  fp resource
 */
function socket_get_status($fp) {}

/**
 * Gets socket options for the socket 
 *
 * @return mixed
 * @param  socket resource
 * @param  level int
 * @param  optname int
 */
function socket_getopt($socket, $level, $optname) {}

/**
 * Queries the remote side of the given socket which may either result in host/port or in a UNIX filesystem path, dependent on its type. 
 *
 * @return bool
 * @param  socket resource
 * @param  addr string
 * @param  port int
 */
function socket_getpeername($socket, &$addr, &$port) {}

/**
 * Queries the remote side of the given socket which may either result in host/port or in a UNIX filesystem path, dependent on its type. 
 *
 * @return bool
 * @param  socket resource
 * @param  addr string
 * @param  port int
 */
function socket_getsockname($socket, &$addr, &$port) {}

/**
 * Returns the last socket error (either the last used or the provided socket resource) 
 *
 * @return int
 * @param  socket resource[optional]
 */
function socket_last_error($socket = null) {}

/**
 * Sets the maximum number of connections allowed to be waited for on the socket specified by fd 
 *
 * @return bool
 * @param  socket resource
 * @param  backlog int
 */
function socket_listen($socket, $backlog) {}

/**
 * Reads a maximum of length bytes from socket 
 *
 * @return string
 * @param  socket resource
 * @param  length int
 * @param  type int[optional]
 */
function socket_read($socket, $length, $type = null) {}

/**
 * Receives data from a connected socket 
 *
 * @return int
 * @param  socket resource
 * @param  buf string
 * @param  len int
 * @param  flags int
 */
function socket_recv($socket, &$buf, $len, $flags) {}

/**
 * Receives data from a socket, connected or not 
 *
 * @return int
 * @param  socket resource
 * @param  buf string
 * @param  len int
 * @param  flags int
 * @param  name string
 * @param  port int[optional]
 */
function socket_recvfrom($socket, &$buf, $len, $flags, &$name, &$port) {}

/**
 * Runs the select() system call on the sets mentioned with a timeout specified by tv_sec and tv_usec 
 *
 * @return int
 * @param  read_fds array
 * @param  write_fds array
 * @param  except_fds &array
 * @param  tv_sec int
 * @param  tv_usec int
 */
function socket_select(&$read_fds, &$write_fds, $except_fds, $tv_sec, $tv_usec) {}

/**
 * Sends data to a connected socket 
 *
 * @return int
 * @param  socket resource
 * @param  buf string
 * @param  len int
 * @param  flags int
 */
function socket_send($socket, $buf, $len, $flags) {}

/**
 * Sends a message to a socket, whether it is connected or not 
 *
 * @return int
 * @param  socket resource
 * @param  buf string
 * @param  len int
 * @param  flags int
 * @param  addr string
 * @param  port int[optional]
 */
function socket_sendto($socket, $buf, $len, $flags, $addr, $port = null) {}

/**
 * Sets blocking mode on a socket resource 
 *
 * @return bool
 * @param  socket resource
 */
function socket_set_block($socket) {}

/**
 * Set blocking/non-blocking mode on a socket or stream 
 *
 * @return bool
 * @param  socket resource
 * @param  mode int
 */
function socket_set_blocking($socket, $mode) {}

/**
 * Sets nonblocking mode on a socket resource 
 *
 * @return bool
 * @param  socket resource
 */
function socket_set_nonblock($socket) {}

/**
 * Sets socket options for the socket 
 *
 * @return bool
 * @param  socket resource
 * @param  level int
 * @param  optname int
 * @param  optval int|array
 */
function socket_set_option($socket, $level, $optname, $optval) {}

/**
 * Set timeout on stream read to seconds + microseonds 
 *
 * @return bool
 * @param  stream resource
 * @param  seconds int
 * @param  microseconds int
 */
function socket_set_timeout($stream, $seconds, $microseconds) {}

/**
 * Sets socket options for the socket 
 *
 * @return bool
 * @param  socket resource
 * @param  level int
 * @param  optname int
 * @param  optval int|array
 */
function socket_setopt($socket, $level, $optname, $optval) {}

/**
 * Shuts down a socket for receiving, sending, or both. 
 *
 * @return bool
 * @param  socket resource
 * @param  how int
 */
function socket_shutdown($socket, $how) {}

/**
 * Returns a string describing an error 
 *
 * @return string
 * @param  errno int
 */
function socket_strerror($errno) {}

/**
 * Writes the buffer to the socket resource, length is optional 
 *
 * @return int
 * @param  socket resource
 * @param  buf string
 * @param  length int
 */
function socket_write($socket, $buf, $length) {}

/**
 * 
 *
 * @return bool
 * @param  result_id resource
 */
function solid_fetch_prev($result_id) {}

/**
 * Sort an array 
 *
 * @return bool
 * @param  array_arg array
 * @param  sort_flags int[optional]
 */
function sort($array_arg, $sort_flags = null) {}

/**
 * Calculate the soundex key of a string 
 *
 * @return string
 * @param  str string
 */
function soundex($str) {}

/**
 * Default implementation for __autoload() 
 *
 * @return void
 * @param  class_name string
 * @param  file_extensions string[optional]
 */
function spl_autoload($class_name, $file_extensions = null) {}

/**
 * Try all registerd autoload function to load the requested class 
 *
 * @return void
 * @param  class_name string
 */
function spl_autoload_call($class_name) {}

/**
 * Register and return default file extensions for spl_autoload 
 *
 * @return string
 * @param  file_extensions string[optional]
 */
function spl_autoload_extensions($file_extensions = null) {}

/**
 * Return all registered __autoload() functionns 
 *
 * @return array
 */
function spl_autoload_functions() {}

/**
 * Register given function as __autoload() implementation 
 *
 * @return bool
 * @param  autoload_function mixed[optional]
 * @param  throw unknown[optional]
 */
function spl_autoload_register($autoload_function = "spl_autoload", $throw = true) {}

/**
 * Unregister given function as __autoload() implementation 
 *
 * @return bool
 * @param  autoload_function mixed
 */
function spl_autoload_unregister($autoload_function) {}

/**
 * Return an array containing the names of all clsses and interfaces defined in SPL 
 *
 * @return array
 */
function spl_classes() {}

/**
 * Split string into array by regular expression 
 *
 * @return array
 * @param  pattern string
 * @param  string string
 * @param  limit int[optional]
 */
function split($pattern, $string, $limit = null) {}

/**
 * Split string into array by regular expression case-insensitive 
 *
 * @return array
 * @param  pattern string
 * @param  string string
 * @param  limit int[optional]
 */
function spliti($pattern, $string, $limit = null) {}

/**
 * Return a formatted string 
 *
 * @return string
 * @param  format string
 * @param  arg1 mixed[optional]
 * @vararg ... mixed
 */
function sprintf($format, $arg1 = null) {}

/**
 * Make regular expression for case insensitive match 
 *
 * @return string
 * @param  string string
 */
function sql_regcase($string) {}

/**
 * Executes a query against a given database and returns an array of arrays. 
 *
 * @return array
 * @param  db resource
 * @param  query string
 * @param  result_type int[optional]
 * @param  decode_binary bool[optional]
 */
function sqlite_array_query($db, $query, $result_type = null, $decode_binary = null) {}

/**
 * Set busy timeout duration. If ms <= 0, all busy handlers are disabled. 
 *
 * @return void
 * @param  db resource
 * @param  ms int
 */
function sqlite_busy_timeout($db, $ms) {}

/**
 * Returns the number of rows that were changed by the most recent SQL statement. 
 *
 * @return int
 * @param  db resource
 */
function sqlite_changes($db) {}

/**
 * Closes an open sqlite database. 
 *
 * @return void
 * @param  db resource
 */
function sqlite_close($db) {}

/**
 * Fetches a column from the current row of a result set. 
 *
 * @return mixed
 * @param  result resource
 * @param  index_or_name mixed
 * @param  decode_binary bool[optional]
 */
function sqlite_column($result, $index_or_name, $decode_binary = null) {}

/**
 * Registers an aggregate function for queries. 
 *
 * @return void
 * @param  db resource
 * @param  funcname string
 * @param  step_func mixed
 * @param  finalize_func mixed
 * @param  num_args long
 */
function sqlite_create_aggregate($db, $funcname, $step_func, $finalize_func, $num_args) {}

/**
 * Registers a "regular" function for queries. 
 *
 * @return void
 * @param  db resource
 * @param  funcname string
 * @param  callback mixed
 * @param  num_args long
 */
function sqlite_create_function($db, $funcname, $callback, $num_args) {}

/**
 * Fetches the current row from a result set as an array. 
 *
 * @return array
 * @param  result resource
 * @param  result_type int[optional]
 * @param  decode_binary bool[optional]
 */
function sqlite_current($result, $result_type = null, $decode_binary = null) {}

/**
 * Returns the textual description of an error code. 
 *
 * @return string
 * @param  error_code int
 */
function sqlite_error_string($error_code) {}

/**
 * Escapes a string for use as a query parameter. 
 *
 * @return string
 * @param  item string
 */
function sqlite_escape_string($item) {}

/**
 * Executes a result-less query against a given database 
 *
 * @return bool
 * @param  query string
 * @param  db resource
 * @param  error_message string
 */
function sqlite_exec($query, $db, &$error_message) {}

/**
 * Opens a SQLite database and creates an object for it. Will create the database if it does not exist. 
 *
 * @return SQLiteDatabase
 * @param  filename string
 * @param  mode int[optional]
 * @param  error_message string[optional]
 */
function sqlite_factory($filename, $mode = null, &$error_message) {}

/**
 * Fetches all rows from a result set as an array of arrays. 
 *
 * @return array
 * @param  result resource
 * @param  result_type int[optional]
 * @param  decode_binary bool[optional]
 */
function sqlite_fetch_all($result, $result_type = null, $decode_binary = null) {}

/**
 * Fetches the next row from a result set as an array. 
 *
 * @return array
 * @param  result resource
 * @param  result_type int[optional]
 * @param  decode_binary bool[optional]
 */
function sqlite_fetch_array($result, $result_type = null, $decode_binary = null) {}

/**
 * Return an array of column types from a particular table. 
 *
 * @return array
 * @param  table_name string
 * @param  db resource
 * @param  result_type int[optional]
 */
function sqlite_fetch_column_types($table_name, $db, $result_type = null) {}

/**
 * Fetches the next row from a result set as an object. 
 *
 * @return object
 * @param  result resource
 * @param  class_name string[optional]
 * @param  ctor_params NULL|array[optional]
 * @param  decode_binary bool[optional]
 */
function sqlite_fetch_object($result, $class_name = null, $ctor_params = null, $decode_binary = null) {}

/**
 * Fetches the first column of a result set as a string. 
 *
 * @return string
 * @param  result resource
 * @param  decode_binary bool[optional]
 */
function sqlite_fetch_single($result, $decode_binary = null) {}

/**
 * Fetches the first column of a result set as a string. 
 *
 * @return string
 * @param  result resource
 * @param  decode_binary bool[optional]
 */
function sqlite_fetch_string($result, $decode_binary = null) {}

/**
 * Returns the name of a particular field of a result set. 
 *
 * @return string
 * @param  result resource
 * @param  field_index int
 */
function sqlite_field_name($result, $field_index) {}

/**
 * Returns whether more rows are available. 
 *
 * @return bool
 * @param  result resource
 */
function sqlite_has_more($result) {}

/**
 * * Returns whether a previous row is available. 
 *
 * @return bool
 * @param  result resource
 */
function sqlite_has_prev($result) {}

/**
 * Return the current row index of a buffered result. 
 *
 * @return int
 * @param  result resource
 */
function sqlite_key($result) {}

/**
 * Returns the error code of the last error for a database. 
 *
 * @return int
 * @param  db resource
 */
function sqlite_last_error($db) {}

/**
 * Returns the rowid of the most recently inserted row. 
 *
 * @return int
 * @param  db resource
 */
function sqlite_last_insert_rowid($db) {}

/**
 * Returns the encoding (iso8859 or UTF-8) of the linked SQLite library. 
 *
 * @return string
 */
function sqlite_libencoding() {}

/**
 * Returns the version of the linked SQLite library. 
 *
 * @return string
 */
function sqlite_libversion() {}

/**
 * Seek to the next row number of a result set. 
 *
 * @return bool
 * @param  result resource
 */
function sqlite_next($result) {}

/**
 * Returns the number of fields in a result set. 
 *
 * @return int
 * @param  result resource
 */
function sqlite_num_fields($result) {}

/**
 * Returns the number of rows in a buffered result set. 
 *
 * @return int
 * @param  result resource
 */
function sqlite_num_rows($result) {}

/**
 * Opens a SQLite database. Will create the database if it does not exist. 
 *
 * @return resource
 * @param  filename string
 * @param  mode int[optional]
 * @param  error_message string[optional]
 */
function sqlite_open($filename, $mode = null, &$error_message) {}

/**
 * Opens a persistent handle to a SQLite database. Will create the database if it does not exist. 
 *
 * @return resource
 * @param  filename string
 * @param  mode int[optional]
 * @param  error_message string[optional]
 */
function sqlite_popen($filename, $mode = null, &$error_message) {}

/**
 * * Seek to the previous row number of a result set. 
 *
 * @return bool
 * @param  result resource
 */
function sqlite_prev($result) {}

/**
 * Executes a query against a given database and returns a result handle. 
 *
 * @return resource
 * @param  query string
 * @param  db resource
 * @param  result_type int[optional]
 * @param  error_message string[optional]
 */
function sqlite_query($query, $db, $result_type = null, &$error_message) {}

/**
 * Seek to the first row number of a buffered result set. 
 *
 * @return bool
 * @param  result resource
 */
function sqlite_rewind($result) {}

/**
 * Seek to a particular row number of a buffered result set. 
 *
 * @return bool
 * @param  result resource
 * @param  row int
 */
function sqlite_seek($result, $row) {}

/**
 * Executes a query and returns either an array for one single column or the value of the first row. 
 *
 * @return array
 * @param  db resource
 * @param  query string
 * @param  first_row_only bool[optional]
 * @param  decode_binary bool[optional]
 */
function sqlite_single_query($db, $query, $first_row_only = null, $decode_binary = null) {}

/**
 * Decode binary encoding on a string parameter passed to an UDF. 
 *
 * @return string
 * @param  data string
 */
function sqlite_udf_decode_binary($data) {}

/**
 * Apply binary encoding (if required) to a string to return from an UDF. 
 *
 * @return string
 * @param  data string
 */
function sqlite_udf_encode_binary($data) {}

/**
 * Executes a query that does not prefetch and buffer all data. 
 *
 * @return resource
 * @param  query string
 * @param  db resource
 * @param  result_type int[optional]
 * @param  error_message string[optional]
 */
function sqlite_unbuffered_query($query, $db, $result_type = null, &$error_message) {}

/**
 * Returns whether more rows are available. 
 *
 * @return bool
 * @param  result resource
 */
function sqlite_valid($result) {}

/**
 * Returns the square root of the number 
 *
 * @return float
 * @param  number float
 */
function sqrt($number) {}

/**
 * Seeds random number generator 
 *
 * @return void
 * @param  seed int[optional]
 */
function srand($seed = null) {}

/**
 * Implements an ANSI C compatible sscanf 
 *
 * @return mixed
 * @param  str string
 * @param  format string
 * @vararg ... string
 */
function sscanf($str, $format) {}

/**
 * Give information about a file 
 *
 * @return array
 * @param  filename string
 */
function stat($filename) {}

/**
 * Replaces all occurrences of search in haystack with replace / case-insensitive 
 *
 * @return mixed
 * @param  search mixed
 * @param  replace mixed
 * @param  subject mixed
 * @param  replace_count int[optional]
 */
function str_ireplace($search, $replace, $subject, &$replace_count) {}

/**
 * Returns input string padded on the left or right to specified length with pad_string 
 *
 * @return string
 * @param  input string
 * @param  pad_length int
 * @param  pad_string string[optional]
 * @param  pad_type int[optional]
 */
function str_pad($input, $pad_length, $pad_string = null, $pad_type = null) {}

/**
 * Returns the input string repeat mult times 
 *
 * @return string
 * @param  input string
 * @param  mult int
 */
function str_repeat($input, $mult) {}

/**
 * Replaces all occurrences of search in haystack with replace 
 *
 * @return mixed
 * @param  search mixed
 * @param  replace mixed
 * @param  subject mixed
 * @param  replace_count int[optional]
 */
function str_replace($search, $replace, $subject, &$replace_count) {}

/**
 * Perform the rot13 transform on a string 
 *
 * @return string
 * @param  str string
 */
function str_rot13($str) {}

/**
 * Shuffles string. One permutation of all possible is created 
 *
 * @return string
 * @param  str string
 */
function str_shuffle($str) {}

/**
 * Convert a string to an array. If split_length is specified, break the string down into chunks each split_length characters long. 
 *
 * @return array
 * @param  str string
 * @param  split_length int[optional]
 */
function str_split($str, $split_length = null) {}

/**
 * 
 *
 * @return mixed
 * @param  str string
 * @param  format int[optional]
 * @param  charlist string[optional]
 */
function str_word_count($str, $format = null, $charlist = null) {}

/**
 * Binary safe case-insensitive string comparison 
 *
 * @return int
 * @param  str1 string
 * @param  str2 string
 */
function strcasecmp($str1, $str2) {}

/**
 * Finds first occurrence of a string within another 
 *
 * @return string
 * @param  haystack string
 * @param  needle string
 */
function strchr($haystack, $needle) {}

/**
 * Binary safe string comparison 
 *
 * @return int
 * @param  str1 string
 * @param  str2 string
 */
function strcmp($str1, $str2) {}

/**
 * Compares two strings using the current locale 
 *
 * @return int
 * @param  str1 string
 * @param  str2 string
 */
function strcoll($str1, $str2) {}

/**
 * Finds length of initial segment consisting entirely of characters not found in mask. If start or/and length is provide works like strcspn(substr($s,$start,$len),$bad_chars) 
 *
 * @return int
 * @param  str string
 * @param  mask string
 * @param  start unknown[optional]
 * @param  len unknown[optional]
 */
function strcspn($str, $mask, $start = null, $len = null) {}

/**
 * Append bucket to brigade 
 *
 * @return void
 * @param  brigade resource
 * @param  bucket resource
 */
function stream_bucket_append($brigade, $bucket) {}

/**
 * Return a bucket object from the brigade for operating on 
 *
 * @return object
 * @param  brigade resource
 */
function stream_bucket_make_writeable($brigade) {}

/**
 * Create a new bucket for use on the current stream 
 *
 * @return object
 * @param  stream resource
 * @param  buffer string
 */
function stream_bucket_new($stream, $buffer) {}

/**
 * Prepend bucket to brigade 
 *
 * @return void
 * @param  brigade resource
 * @param  bucket resource
 */
function stream_bucket_prepend($brigade, $bucket) {}

/**
 * Create a file context and optionally set parameters 
 *
 * @return resource
 * @param  options array[optional]
 */
function stream_context_create($options = null) {}

/**
 * Get a handle on the default file/stream context and optionally set parameters 
 *
 * @return resource
 * @param  options array[optional]
 */
function stream_context_get_default($options = null) {}

/**
 * Retrieve options for a stream/wrapper/context 
 *
 * @return array
 * @param  context|resource_stream resource
 */
function stream_context_get_options($context) {}

/**
 * Set an option for a wrapper 
 *
 * @return bool
 * @param  context|resource_stream resource
 * @param  wrappername string
 * @param  optionname string
 * @param  value mixed
 */
function stream_context_set_option($context, $wrappername, $optionname, $value) {}

/**
 * Set parameters for a file context 
 *
 * @return bool
 * @param  context|resource_stream resource
 * @param  options array
 */
function stream_context_set_params($context, $options) {}

/**
 * Reads up to maxlen bytes from source stream and writes them to dest stream. 
 *
 * @return int
 * @param  source resource
 * @param  dest resource
 * @param  maxlen long[optional]
 * @param  pos long[optional]
 */
function stream_copy_to_stream($source, $dest, $maxlen = null, $pos = null) {}

/**
 * Append a filter to a stream 
 *
 * @return resource
 * @param  stream resource
 * @param  filtername string
 * @param  read_write int
 * @param  filterparams string
 */
function stream_filter_append($stream, $filtername, $read_write, $filterparams) {}

/**
 * Prepend a filter to a stream 
 *
 * @return resource
 * @param  stream resource
 * @param  filtername string
 * @param  read_write int
 * @param  filterparams string
 */
function stream_filter_prepend($stream, $filtername, $read_write, $filterparams) {}

/**
 * Registers a custom filter handler class 
 *
 * @return bool
 * @param  filtername string
 * @param  classname string
 */
function stream_filter_register($filtername, $classname) {}

/**
 * Flushes any data in the filter's internal buffer, removes it from the chain, and frees the resource 
 *
 * @return bool
 * @param  stream_filter resource
 */
function stream_filter_remove($stream_filter) {}

/**
 * Reads all remaining bytes (or up to maxlen bytes) from a stream and returns them as a string. 
 *
 * @return string
 * @param  source resource
 * @param  maxlen long[optional]
 * @param  offset long[optional]
 */
function stream_get_contents($source, $maxlen = null, $offset = null) {}

/**
 * Returns a list of registered filters 
 *
 * @return array
 */
function stream_get_filters() {}

/**
 * Read up to maxlen bytes from a stream or until the ending string is found 
 *
 * @return string
 * @param  stream resource
 * @param  maxlen int
 * @param  ending string[optional]
 */
function stream_get_line($stream, $maxlen, $ending = null) {}

/**
 * Retrieves header/meta data from streams/file pointers 
 *
 * @return array
 * @param  fp resource
 */
function stream_get_meta_data($fp) {}

/**
 * Retrieves list of registered socket transports 
 *
 * @return array
 */
function stream_get_transports() {}

/**
 * Retrieves list of registered stream wrappers 
 *
 * @return array
 */
function stream_get_wrappers() {}

/**
 * Registers a custom URL protocol handler class 
 *
 * @return bool
 * @param  protocol string
 * @param  classname string
 */
function stream_register_wrapper($protocol, $classname) {}

/**
 * Runs the select() system call on the sets of streams with a timeout specified by tv_sec and tv_usec 
 *
 * @return int
 * @param  read_streams array
 * @param  write_streams array
 * @param  except_streams array
 * @param  tv_sec int
 * @param  tv_usec int
 */
function stream_select(&$read_streams, &$write_streams, &$except_streams, $tv_sec, $tv_usec) {}

/**
 * Set blocking/non-blocking mode on a socket or stream 
 *
 * @return bool
 * @param  socket resource
 * @param  mode int
 */
function stream_set_blocking($socket, $mode) {}

/**
 * Set timeout on stream read to seconds + microseonds 
 *
 * @return bool
 * @param  stream resource
 * @param  seconds int
 * @param  microseconds int
 */
function stream_set_timeout($stream, $seconds, $microseconds) {}

/**
 * Set file write buffer 
 *
 * @return int
 * @param  fp resource
 * @param  buffer int
 */
function stream_set_write_buffer($fp, $buffer) {}

/**
 * Accept a client connection from a server socket 
 *
 * @return resource
 * @param  serverstream resource
 * @param  timeout double[optional]
 * @param  peername string
 */
function stream_socket_accept($serverstream, $timeout = null, &$peername) {}

/**
 * Open a client connection to a remote address 
 *
 * @return resource
 * @param  remoteaddress string
 * @param  errcode long[optional]
 * @param  errstring string
 * @param  timeout double
 * @param  flags long
 * @param  context resource
 */
function stream_socket_client($remoteaddress, &$errcode, &$errstring, $timeout, $flags, $context) {}

/**
 * Enable or disable a specific kind of crypto on the stream 
 *
 * @return mixed
 * @param  stream resource
 * @param  enable bool
 * @param  cryptokind int[optional]
 * @param  sessionstream resource
 */
function stream_socket_enable_crypto($stream, $enable, $cryptokind = null, $sessionstream) {}

/**
 * Returns either the locally bound or remote name for a socket stream 
 *
 * @return string
 * @param  stream resource
 * @param  want_peer bool
 */
function stream_socket_get_name($stream, $want_peer) {}

/**
 * Creates a pair of connected, indistinguishable socket streams 
 *
 * @return array
 * @param  domain int
 * @param  type int
 * @param  protocol int
 */
function stream_socket_pair($domain, $type, $protocol) {}

/**
 * Receives data from a socket stream 
 *
 * @return string
 * @param  stream resource
 * @param  amount long
 * @param  flags long[optional]
 * @param  remote_addr string[optional]
 */
function stream_socket_recvfrom($stream, $amount, $flags = null, &$remote_addr) {}

/**
 * Send data to a socket stream.  If target_addr is specified it must be in dotted quad (or [ipv6]) format 
 *
 * @return int
 * @param  stream resouce
 * @param  data string
 * @param  flags long[optional]
 * @param  target_addr string[optional]
 */
function stream_socket_sendto($stream, $data, $flags = null, $target_addr = null) {}

/**
 * Create a server socket bound to localaddress 
 *
 * @return resource
 * @param  localaddress string
 * @param  errcode long[optional]
 * @param  errstring string
 * @param  flags long
 * @param  context resource
 */
function stream_socket_server($localaddress, &$errcode, &$errstring, $flags, $context) {}

/**
 * Registers a custom URL protocol handler class 
 *
 * @return bool
 * @param  protocol string
 * @param  classname string
 */
function stream_wrapper_register($protocol, $classname) {}

/**
 * Restore the original protocol handler, overriding if necessary 
 *
 * @return bool
 * @param  protocol string
 */
function stream_wrapper_restore($protocol) {}

/**
 * Unregister a wrapper for the life of the current request. 
 *
 * @return bool
 * @param  protocol string
 */
function stream_wrapper_unregister($protocol) {}

/**
 * Format a local time/date according to locale settings 
 *
 * @return string
 * @param  format string
 * @param  timestamp int[optional]
 */
function strftime($format, $timestamp = null) {}

/**
 * Strips HTML and PHP tags from a string 
 *
 * @return string
 * @param  str string
 * @param  allowable_tags string[optional]
 */
function strip_tags($str, $allowable_tags = null) {}

/**
 * Strips backslashes from a string. Uses C-style conventions 
 *
 * @return string
 * @param  str string
 */
function stripcslashes($str) {}

/**
 * Finds position of first occurrence of a string within another, case insensitive 
 *
 * @return int
 * @param  haystack string
 * @param  needle string
 * @param  offset int[optional]
 */
function stripos($haystack, $needle, $offset = null) {}

/**
 * Strips backslashes from a string 
 *
 * @return string
 * @param  str string
 */
function stripslashes($str) {}

/**
 * Finds first occurrence of a string within another, case insensitive 
 *
 * @return string
 * @param  haystack string
 * @param  needle string
 */
function stristr($haystack, $needle) {}

/**
 * Get string length 
 *
 * @return int
 * @param  str string
 */
function strlen($str) {}

/**
 * Returns the result of case-insensitive string comparison using 'natural' algorithm 
 *
 * @return int
 * @param  s1 string
 * @param  s2 string
 */
function strnatcasecmp($s1, $s2) {}

/**
 * Returns the result of string comparison using 'natural' algorithm 
 *
 * @return int
 * @param  s1 string
 * @param  s2 string
 */
function strnatcmp($s1, $s2) {}

/**
 * Binary safe string comparison 
 *
 * @return int
 * @param  str1 string
 * @param  str2 string
 * @param  len int
 */
function strncasecmp($str1, $str2, $len) {}

/**
 * Binary safe string comparison 
 *
 * @return int
 * @param  str1 string
 * @param  str2 string
 * @param  len int
 */
function strncmp($str1, $str2, $len) {}

/**
 * Search a string for any of a set of characters 
 *
 * @return string
 * @param  haystack string
 * @param  char_list string
 */
function strpbrk($haystack, $char_list) {}

/**
 * Finds position of first occurrence of a string within another 
 *
 * @return int
 * @param  haystack string
 * @param  needle string
 * @param  offset int[optional]
 */
function strpos($haystack, $needle, $offset = null) {}

/**
 * Parse a time/date generated with strftime() 
 *
 * @return array
 * @param  timestamp string
 * @param  format string
 */
function strptime($timestamp, $format) {}

/**
 * Finds the last occurrence of a character in a string within another 
 *
 * @return string
 * @param  haystack string
 * @param  needle string
 */
function strrchr($haystack, $needle) {}

/**
 * Reverse a string 
 *
 * @return string
 * @param  str string
 */
function strrev($str) {}

/**
 * Finds position of last occurrence of a string within another string 
 *
 * @return int
 * @param  haystack string
 * @param  needle string
 * @param  offset int[optional]
 */
function strripos($haystack, $needle, $offset = null) {}

/**
 * Finds position of last occurrence of a string within another string 
 *
 * @return int
 * @param  haystack string
 * @param  needle string
 * @param  offset int[optional]
 */
function strrpos($haystack, $needle, $offset = null) {}

/**
 * Finds length of initial segment consisting entirely of characters found in mask. If start or/and length is provided works like strspn(substr($s,$start,$len),$good_chars) 
 *
 * @return int
 * @param  str string
 * @param  mask string
 * @param  start unknown[optional]
 * @param  len unknown[optional]
 */
function strspn($str, $mask, $start = null, $len = null) {}

/**
 * Finds first occurrence of a string within another 
 *
 * @return string
 * @param  haystack string
 * @param  needle string
 */
function strstr($haystack, $needle) {}

/**
 * Tokenize a string 
 *
 * @return string
 * @param  str string[optional]
 * @param  token string
 */
function strtok($str = null, $token) {}

/**
 * Makes a string lowercase 
 *
 * @return string
 * @param  str string
 */
function strtolower($str) {}

/**
 * Convert string representation of date and time to a timestamp 
 *
 * @return int
 * @param  time string
 * @param  now int[optional]
 */
function strtotime($time, $now = null) {}

/**
 * Makes a string uppercase 
 *
 * @return string
 * @param  str string
 */
function strtoupper($str) {}

/**
 * Translates characters in str using given translation tables 
 *
 * @return string
 * @param  str string
 * @param  from string
 * @param  to string
 */
function strtr($str, $from, $to) {}

/**
 * Get the string value of a variable 
 *
 * @return string
 * @param  var mixed
 */
function strval($var) {}

/**
 * Returns part of a string 
 *
 * @return string
 * @param  str string
 * @param  start int
 * @param  length int[optional]
 */
function substr($str, $start, $length = null) {}

/**
 * Binary safe optionally case insensitive comparison of 2 strings from an offset, up to length characters 
 *
 * @return int
 * @param  main_str string
 * @param  str string
 * @param  offset int
 * @param  length int[optional]
 * @param  case_sensitivity bool[optional]
 */
function substr_compare($main_str, $str, $offset, $length = null, $case_sensitivity = null) {}

/**
 * Returns the number of times a substring occurs in the string 
 *
 * @return int
 * @param  haystack string
 * @param  needle string
 * @param  offset int[optional]
 * @param  length int[optional]
 */
function substr_count($haystack, $needle, $offset = null, $length = null) {}

/**
 * Replaces part of a string with another string 
 *
 * @return mixed
 * @param  str mixed
 * @param  repl mixed
 * @param  start mixed
 * @param  length mixed[optional]
 */
function substr_replace($str, $repl, $start, $length = null) {}

/**
 * Returns a SWFPrebuiltClip object 
 *
 * @return class
 * @param  file unknown[optional]
 */
function swfprebuiltclip_init($file = null) {}

/**
 * Returns a SWVideoStream object 
 *
 * @return class
 * @param  file unknown[optional]
 */
function swfvideostream_init($file = null) {}

/**
 * Get number of affected rows in last query 
 *
 * @return int
 * @param  link_id int[optional]
 */
function sybase_affected_rows($link_id = null) {}

/**
 * Close Sybase connection 
 *
 * @return bool
 * @param  link_id int[optional]
 */
function sybase_close($link_id = null) {}

/**
 * Open Sybase server connection 
 *
 * @return resource
 * @param  host string[optional]
 * @param  user string[optional]
 * @param  password string[optional]
 * @param  charset string[optional]
 * @param  appname string[optional]
 */
function sybase_connect($host = null, $user = null, $password = null, $charset = null, $appname = null) {}

/**
 * Move internal row pointer 
 *
 * @return bool
 * @param  result int
 * @param  offset int
 */
function sybase_data_seek($result, $offset) {}

/**
 * Sets deadlock retry count 
 *
 * @return void
 * @param  retry_count int
 */
function sybase_deadlock_retry_count($retry_count) {}

/**
 * Fetch row as array 
 *
 * @return array
 * @param  result int
 */
function sybase_fetch_array($result) {}

/**
 * Fetch row as array without numberic indices 
 *
 * @return array
 * @param  result int
 */
function sybase_fetch_assoc($result) {}

/**
 * Get field information 
 *
 * @return object
 * @param  result int
 * @param  offset int[optional]
 */
function sybase_fetch_field($result, $offset = null) {}

/**
 * Fetch row as object 
 *
 * @return object
 * @param  result int
 * @param  object mixed[optional]
 */
function sybase_fetch_object($result, $object = null) {}

/**
 * Get row as enumerated array 
 *
 * @return array
 * @param  result int
 */
function sybase_fetch_row($result) {}

/**
 * Set field offset 
 *
 * @return bool
 * @param  result int
 * @param  offset int
 */
function sybase_field_seek($result, $offset) {}

/**
 * Free result memory 
 *
 * @return bool
 * @param  result int
 */
function sybase_free_result($result) {}

/**
 * Returns the last message from server (over min_message_severity) 
 *
 * @return string
 */
function sybase_get_last_message() {}

/**
 * Sets minimum client severity 
 *
 * @return void
 * @param  severity int
 */
function sybase_min_client_severity($severity) {}

/**
 * Sets the minimum error severity 
 *
 * @return void
 * @param  severity int
 */
function sybase_min_error_severity($severity) {}

/**
 * Sets the minimum message severity 
 *
 * @return void
 * @param  severity int
 */
function sybase_min_message_severity($severity) {}

/**
 * Sets minimum server severity 
 *
 * @return void
 * @param  severity int
 */
function sybase_min_server_severity($severity) {}

/**
 * Get number of fields in result 
 *
 * @return int
 * @param  result int
 */
function sybase_num_fields($result) {}

/**
 * Get number of rows in result 
 *
 * @return int
 * @param  result int
 */
function sybase_num_rows($result) {}

/**
 * Open persistent Sybase connection 
 *
 * @return resource
 * @param  host string[optional]
 * @param  user string[optional]
 * @param  password string[optional]
 * @param  charset string[optional]
 * @param  appname string[optional]
 */
function sybase_pconnect($host = null, $user = null, $password = null, $charset = null, $appname = null) {}

/**
 * Send Sybase query 
 *
 * @return mixed
 * @param  query string
 * @param  link_id int[optional]
 */
function sybase_query($query, $link_id = null) {}

/**
 * Get result data 
 *
 * @return string
 * @param  result int
 * @param  row int
 * @param  field mixed
 */
function sybase_result($result, $row, $field) {}

/**
 * Select Sybase database 
 *
 * @return bool
 * @param  database string
 * @param  link_id int[optional]
 */
function sybase_select_db($database, $link_id = null) {}

/**
 * 
 *
 * @return bool
 * @param  error_func mixed
 * @param  connection resource[optional]
 */
function sybase_set_message_handler($error_func, $connection = null) {}

/**
 * Send Sybase query 
 *
 * @return resource
 * @param  query string
 * @param  link_id int[optional]
 */
function sybase_unbuffered_query($query, $link_id = null) {}

/**
 * Create a symbolic link 
 *
 * @return bool
 * @param  target string
 * @param  link string
 */
function symlink($target, $link) {}

/**
 * Generate a system log message 
 *
 * @return bool
 * @param  priority int
 * @param  message string
 */
function syslog($priority, $message) {}

/**
 * Execute an external program and display output 
 *
 * @return string
 * @param  command string
 * @param  return_value int[optional]
 */
function system($command, &$return_value) {}

/**
 * Returns the tangent of the number in radians 
 *
 * @return float
 * @param  number float
 */
function tan($number) {}

/**
 * Returns the hyperbolic tangent of the number, defined as sinh(number)/cosh(number) 
 *
 * @return float
 * @param  number float
 */
function tanh($number) {}

/**
 * Create a unique filename in a directory 
 *
 * @return string
 * @param  dir string
 * @param  prefix string
 */
function tempnam($dir, $prefix) {}

/**
 * Set the textdomain to "domain". Returns the current domain 
 *
 * @return string
 * @param  domain string
 */
function textdomain($domain) {}

/**
 * Returns the Number of Tidy accessibility warnings encountered for specified document. 
 *
 * @return int
 */
function tidy_access_count() {}

/**
 * Execute configured cleanup and repair operations on parsed markup 
 *
 * @return bool
 */
function tidy_clean_repair() {}

/**
 * Returns the Number of Tidy configuration errors encountered for specified document. 
 *
 * @return int
 */
function tidy_config_count() {}

/**
 * Run configured diagnostics on parsed and repaired markup. 
 *
 * @return bool
 */
function tidy_diagnose() {}

/**
 * Returns the Number of Tidy errors encountered for specified document. 
 *
 * @return int
 */
function tidy_error_count() {}

/**
 * Returns a TidyNode Object starting from the <BODY> tag of the tidy parse tree 
 *
 * @return tidyNode
 * @param  tidy resource
 */
function tidy_get_body($tidy) {}

/**
 * Get current Tidy configuarion 
 *
 * @return array
 */
function tidy_get_config() {}

/**
 * Return warnings and errors which occured parsing the specified document
 *
 * @return string
 * @param  detailed boolean[optional]
 */
function tidy_get_error_buffer($detailed = null) {}

/**
 * Returns a TidyNode Object starting from the <HEAD> tag of the tidy parse tree 
 *
 * @return tidyNode
 */
function tidy_get_head() {}

/**
 * Returns a TidyNode Object starting from the <HTML> tag of the tidy parse tree 
 *
 * @return tidyNode
 */
function tidy_get_html() {}

/**
 * Get the Detected HTML version for the specified document. 
 *
 * @return int
 */
function tidy_get_html_ver() {}

/**
 * Returns the documentation for the given option name 
 *
 * @return string
 * @param  resource tidy
 * @param  optname string
 */
function tidy_get_opt_doc($resource, $optname) {}

/**
 * Return a string representing the parsed tidy markup 
 *
 * @return string
 */
function tidy_get_output() {}

/**
 * Get release date (version) for Tidy library 
 *
 * @return string
 */
function tidy_get_release() {}

/**
 * Returns a TidyNode Object representing the root of the tidy parse tree 
 *
 * @return tidyNode
 */
function tidy_get_root() {}

/**
 * Get status of specfied document. 
 *
 * @return int
 */
function tidy_get_status() {}

/**
 * Returns the value of the specified configuration option for the tidy document. 
 *
 * @return mixed
 * @param  option string
 */
function tidy_getopt($option) {}

/**
 * Indicates if the document is a generic (non HTML/XHTML) XML document. 
 *
 * @return bool
 */
function tidy_is_xhtml() {}

/**
 * Parse markup in file or URI 
 *
 * @return tidy
 * @param  file string
 * @param  config_options mixed[optional]
 * @param  encoding string[optional]
 * @param  use_include_path bool[optional]
 */
function tidy_parse_file($file, $config_options = null, $encoding = null, $use_include_path = null) {}

/**
 * Parse a document stored in a string 
 *
 * @return tidy
 * @param  input string
 * @param  config_options mixed[optional]
 * @param  encoding string[optional]
 */
function tidy_parse_string($input, $config_options = null, $encoding = null) {}

/**
 * Repair a file using an optionally provided configuration file 
 *
 * @return string
 * @param  filename string
 * @param  config_file mixed[optional]
 * @param  encoding string[optional]
 * @param  use_include_path bool[optional]
 */
function tidy_repair_file($filename, $config_file = null, $encoding = null, $use_include_path = null) {}

/**
 * Repair a string using an optionally provided configuration file 
 *
 * @return string
 * @param  data string
 * @param  config_file mixed[optional]
 * @param  encoding string[optional]
 */
function tidy_repair_string($data, $config_file = null, $encoding = null) {}

/**
 * Returns the Number of Tidy warnings encountered for specified document. 
 *
 * @return int
 */
function tidy_warning_count() {}

/**
 * Return current UNIX timestamp 
 *
 * @return int
 */
function time() {}

/**
 * Delay for a number of seconds and nano seconds 
 *
 * @return mixed
 * @param  seconds long
 * @param  nanoseconds long
 */
function time_nanosleep($seconds, $nanoseconds) {}

/**
 * Make the script sleep until the specified time 
 *
 * @return bool
 * @param  timestamp float
 */
function time_sleep_until($timestamp) {}

/**
 * Create a temporary file that will be deleted automatically after use 
 *
 * @return resource
 */
function tmpfile() {}

/**
 * 
 *
 * @return array
 * @param  source string
 */
function token_get_all($source) {}

/**
 * 
 *
 * @return string
 * @param  type int
 */
function token_name($type) {}

/**
 * Set modification time of file 
 *
 * @return bool
 * @param  filename string
 * @param  time int[optional]
 * @param  atime int[optional]
 */
function touch($filename, $time = null, $atime = null) {}

/**
 * Generates a user-level error/warning/notice message 
 *
 * @return bool
 * @param  messsage string
 * @param  error_type int[optional]
 */
function trigger_error($messsage, $error_type = null) {}

/**
 * Strips whitespace from the beginning and end of a string 
 *
 * @return string
 * @param  str string
 * @param  character_mask string[optional]
 */
function trim($str, $character_mask = null) {}

/**
 * Sort an array with a user-defined comparison function and maintain index association 
 *
 * @return bool
 * @param  array_arg array
 * @param  cmp_function string
 */
function uasort($array_arg, $cmp_function) {}

/**
 * Makes a string's first character uppercase 
 *
 * @return string
 * @param  str string
 */
function ucfirst($str) {}

/**
 * Uppercase the first character of every word in a string 
 *
 * @return string
 * @param  str string
 */
function ucwords($str) {}

/**
 * Sort an array by keys using a user-defined comparison function 
 *
 * @return bool
 * @param  array_arg array
 * @param  cmp_function string
 */
function uksort($array_arg, $cmp_function) {}

/**
 * Return or change the umask 
 *
 * @return int
 * @param  mask int[optional]
 */
function umask($mask = null) {}

/**
 * Generates a unique ID 
 *
 * @return string
 * @param  prefix string[optional]
 * @param  more_entropy bool
 */
function uniqid($prefix = null, $more_entropy) {}

/**
 * Convert UNIX timestamp to Julian Day 
 *
 * @return int
 * @param  timestamp int[optional]
 */
function unixtojd($timestamp = null) {}

/**
 * Delete a file 
 *
 * @return bool
 * @param  filename string
 * @param  context context
 */
function unlink($filename, $context) {}

/**
 * Unpack binary string into named array elements according to format argument 
 *
 * @return array
 * @param  format string
 * @param  input string
 */
function unpack($format, $input) {}

/**
 * 
 *
 * @return boolean
 * @param  handle string
 */
function unregister_event_handler($handle) {}

/**
 * Unregisters a tick callback function 
 *
 * @return void
 * @param  function_name string
 */
function unregister_tick_function($function_name) {}

/**
 * Takes a string representation of variable and recreates it 
 *
 * @return mixed
 * @param  variable_representation string
 */
function unserialize($variable_representation) {}

/**
 * Decodes URL-encoded string 
 *
 * @return string
 * @param  str string
 */
function urldecode($str) {}

/**
 * URL-encodes string 
 *
 * @return string
 * @param  str string
 */
function urlencode($str) {}

/**
 * Generates a user-level error/warning/notice message 
 *
 * @return bool
 * @param  messsage string
 * @param  error_type int[optional]
 */
function user_error($messsage, $error_type = null) {}

/**
 * Delay for a given number of micro seconds 
 *
 * @return void
 * @param  micro_seconds int
 */
function usleep($micro_seconds) {}

/**
 * Sort an array by values using a user-defined comparison function 
 *
 * @return bool
 * @param  array_arg array
 * @param  cmp_function string
 */
function usort($array_arg, $cmp_function) {}

/**
 * Converts a UTF-8 encoded string to ISO-8859-1 
 *
 * @return string
 * @param  data string
 */
function utf8_decode($data) {}

/**
 * Encodes an ISO-8859-1 string to UTF-8 
 *
 * @return string
 * @param  data string
 */
function utf8_encode($data) {}

/**
 * Dumps a string representation of variable to output 
 *
 * @return void
 * @param  var mixed
 */
function var_dump($var) {}

/**
 * Outputs or returns a string representation of a variable 
 *
 * @return mixed
 * @param  var mixed
 * @param  return bool[optional]
 */
function var_export($var, $return = null) {}

/**
 * Returns the absolute value of a variant 
 *
 * @return mixed
 * @param  left mixed
 */
function variant_abs($left) {}

/**
 * "Adds" two variant values together and returns the result 
 *
 * @return mixed
 * @param  left mixed
 * @param  right mixed
 */
function variant_add($left, $right) {}

/**
 * performs a bitwise AND operation between two variants and returns the result 
 *
 * @return mixed
 * @param  left mixed
 * @param  right mixed
 */
function variant_and($left, $right) {}

/**
 * Convert a variant into a new variant object of another type 
 *
 * @return variant
 * @param  variant object
 * @param  type int
 */
function variant_cast($variant, $type) {}

/**
 * concatenates two variant values together and returns the result 
 *
 * @return mixed
 * @param  left mixed
 * @param  right mixed
 */
function variant_cat($left, $right) {}

/**
 * Compares two variants 
 *
 * @return int
 * @param  left mixed
 * @param  right mixed
 * @param  lcid int[optional]
 * @param  flags int[optional]
 */
function variant_cmp($left, $right, $lcid = null, $flags = null) {}

/**
 * Returns a variant date representation of a unix timestamp 
 *
 * @return variant
 * @param  timestamp int
 */
function variant_date_from_timestamp($timestamp) {}

/**
 * Converts a variant date/time value to unix timestamp 
 *
 * @return int
 * @param  variant object
 */
function variant_date_to_timestamp($variant) {}

/**
 * Returns the result from dividing two variants 
 *
 * @return mixed
 * @param  left mixed
 * @param  right mixed
 */
function variant_div($left, $right) {}

/**
 * Performs a bitwise equivalence on two variants 
 *
 * @return mixed
 * @param  left mixed
 * @param  right mixed
 */
function variant_eqv($left, $right) {}

/**
 * Returns the integer part ? of a variant 
 *
 * @return mixed
 * @param  left mixed
 */
function variant_fix($left) {}

/**
 * Returns the VT_XXX type code for a variant 
 *
 * @return int
 * @param  variant object
 */
function variant_get_type($variant) {}

/**
 * Converts variants to integers and then returns the result from dividing them 
 *
 * @return mixed
 * @param  left mixed
 * @param  right mixed
 */
function variant_idiv($left, $right) {}

/**
 * Performs a bitwise implication on two variants 
 *
 * @return mixed
 * @param  left mixed
 * @param  right mixed
 */
function variant_imp($left, $right) {}

/**
 * Returns the integer portion of a variant 
 *
 * @return mixed
 * @param  left mixed
 */
function variant_int($left) {}

/**
 * Divides two variants and returns only the remainder 
 *
 * @return mixed
 * @param  left mixed
 * @param  right mixed
 */
function variant_mod($left, $right) {}

/**
 * multiplies the values of the two variants and returns the result 
 *
 * @return mixed
 * @param  left mixed
 * @param  right mixed
 */
function variant_mul($left, $right) {}

/**
 * Performs logical negation on a variant 
 *
 * @return mixed
 * @param  left mixed
 */
function variant_neg($left) {}

/**
 * Performs bitwise not negation on a variant 
 *
 * @return mixed
 * @param  left mixed
 */
function variant_not($left) {}

/**
 * Performs a logical disjunction on two variants 
 *
 * @return mixed
 * @param  left mixed
 * @param  right mixed
 */
function variant_or($left, $right) {}

/**
 * Returns the result of performing the power function with two variants 
 *
 * @return mixed
 * @param  left mixed
 * @param  right mixed
 */
function variant_pow($left, $right) {}

/**
 * Rounds a variant to the specified number of decimal places 
 *
 * @return mixed
 * @param  left mixed
 * @param  decimals int
 */
function variant_round($left, $decimals) {}

/**
 * Assigns a new value for a variant object 
 *
 * @return void
 * @param  variant object
 * @param  value mixed
 */
function variant_set($variant, $value) {}

/**
 * Convert a variant into another type.  Variant is modified "in-place" 
 *
 * @return void
 * @param  variant object
 * @param  type int
 */
function variant_set_type($variant, $type) {}

/**
 * subtracts the value of the right variant from the left variant value and returns the result 
 *
 * @return mixed
 * @param  left mixed
 * @param  right mixed
 */
function variant_sub($left, $right) {}

/**
 * Performs a logical exclusion on two variants 
 *
 * @return mixed
 * @param  left mixed
 * @param  right mixed
 */
function variant_xor($left, $right) {}

/**
 * 
 *
 * @return bool
 * @param  index int
 */
function velocis_autocommit($index) {}

/**
 * 
 *
 * @return bool
 * @param  id int
 */
function velocis_close($id) {}

/**
 * 
 *
 * @return bool
 * @param  index int
 */
function velocis_commit($index) {}

/**
 * 
 *
 * @return int
 * @param  server string
 * @param  user string
 * @param  pass string
 */
function velocis_connect($server, $user, $pass) {}

/**
 * 
 *
 * @return int
 * @param  index int
 * @param  exec_str string
 */
function velocis_exec($index, $exec_str) {}

/**
 * 
 *
 * @return bool
 * @param  index int
 */
function velocis_fetch($index) {}

/**
 * 
 *
 * @return string
 * @param  index int
 * @param  col int
 */
function velocis_fieldname($index, $col) {}

/**
 * 
 *
 * @return int
 * @param  index int
 */
function velocis_fieldnum($index) {}

/**
 * 
 *
 * @return bool
 * @param  index int
 */
function velocis_freeresult($index) {}

/**
 * 
 *
 * @return bool
 * @param  index int
 */
function velocis_off_autocommit($index) {}

/**
 * 
 *
 * @return mixed
 * @param  index int
 * @param  col int
 */
function velocis_result($index, $col) {}

/**
 * 
 *
 * @return bool
 * @param  index int
 */
function velocis_rollback($index) {}

/**
 * Compares two "PHP-standardized" version number strings 
 *
 * @return mixed
 * @param  ver1 string
 * @param  ver2 string
 * @param  oper string[optional]
 */
function version_compare($ver1, $ver2, $oper = null) {}

/**
 * Output a formatted string into a stream 
 *
 * @return int
 * @param  stream resource
 * @param  format string
 * @param  args array
 */
function vfprintf($stream, $format, $args) {}

/**
 * Perform an NSAPI sub-request 
 *
 * @return bool
 * @param  uri string
 */
function virtual($uri) {}

/**
 * Output a formatted string 
 *
 * @return int
 * @param  format string
 * @param  args array
 */
function vprintf($format, $args) {}

/**
 * Return a formatted string 
 *
 * @return string
 * @param  format string
 * @param  args array
 */
function vsprintf($format, $args) {}

/**
 * Serializes given variables and adds them to packet given by packet_id 
 *
 * @return bool
 * @param  packet_id int
 * @param  var_names mixed
 * @vararg ... mixed
 */
function wddx_add_vars($packet_id, $var_names) {}

/**
 * Deserializes given packet and returns a PHP value 
 *
 * @return mixed
 * @param  packet mixed
 */
function wddx_deserialize($packet) {}

/**
 * Ends specified WDDX packet and returns the string containing the packet 
 *
 * @return string
 * @param  packet_id int
 */
function wddx_packet_end($packet_id) {}

/**
 * Starts a WDDX packet with optional comment and returns the packet id 
 *
 * @return resource
 * @param  comment string[optional]
 */
function wddx_packet_start($comment = null) {}

/**
 * Creates a new packet and serializes the given value 
 *
 * @return string
 * @param  var mixed
 * @param  comment string[optional]
 */
function wddx_serialize_value($var, $comment = null) {}

/**
 * Creates a new packet and serializes given variables into a struct 
 *
 * @return string
 * @param  var_name mixed
 * @vararg ... mixed
 */
function wddx_serialize_vars($var_name) {}

/**
 * Wraps buffer to selected number of characters using string break char 
 *
 * @return string
 * @param  str string
 * @param  width int[optional]
 * @param  break string[optional]
 * @param  cut boolean[optional]
 */
function wordwrap($str, $width = null, $break = null, $cut = null) {}

/**
 * Get XML parser error string 
 *
 * @return string
 * @param  code int
 */
function xml_error_string($code) {}

/**
 * Get current byte index for an XML parser 
 *
 * @return int
 * @param  parser resource
 */
function xml_get_current_byte_index($parser) {}

/**
 * Get current column number for an XML parser 
 *
 * @return int
 * @param  parser resource
 */
function xml_get_current_column_number($parser) {}

/**
 * Get current line number for an XML parser 
 *
 * @return int
 * @param  parser resource
 */
function xml_get_current_line_number($parser) {}

/**
 * Get XML parser error code 
 *
 * @return int
 * @param  parser resource
 */
function xml_get_error_code($parser) {}

/**
 * Start parsing an XML document 
 *
 * @return int
 * @param  parser resource
 * @param  data string
 * @param  isFinal int[optional]
 */
function xml_parse($parser, $data, $isFinal = null) {}

/**
 * Parsing a XML document 
 *
 * @return int
 * @param  parser resource
 * @param  data string
 * @param  struct array
 * @param  index array
 */
function xml_parse_into_struct($parser, $data, &$struct, &$index) {}

/**
 * Create an XML parser 
 *
 * @return resource
 * @param  encoding string[optional]
 */
function xml_parser_create($encoding = null) {}

/**
 * Create an XML parser 
 *
 * @return resource
 * @param  encoding string[optional]
 * @param  sep string[optional]
 */
function xml_parser_create_ns($encoding = null, $sep = null) {}

/**
 * Free an XML parser 
 *
 * @return bool
 * @param  parser resource
 */
function xml_parser_free($parser) {}

/**
 * Get options from an XML parser 
 *
 * @return mixed
 * @param  parser resource
 * @param  option int
 */
function xml_parser_get_option($parser, $option) {}

/**
 * Set options in an XML parser 
 *
 * @return bool
 * @param  parser resource
 * @param  option int
 * @param  value mixed
 */
function xml_parser_set_option($parser, $option, $value) {}

/**
 * Set up character data handler 
 *
 * @return bool
 * @param  parser resource
 * @param  hdl string
 */
function xml_set_character_data_handler($parser, $hdl) {}

/**
 * Set up default handler 
 *
 * @return bool
 * @param  parser resource
 * @param  hdl string
 */
function xml_set_default_handler($parser, $hdl) {}

/**
 * Set up start and end element handlers 
 *
 * @return bool
 * @param  parser resource
 * @param  shdl string
 * @param  ehdl string
 */
function xml_set_element_handler($parser, $shdl, $ehdl) {}

/**
 * Set up character data handler 
 *
 * @return bool
 * @param  parser resource
 * @param  hdl string
 */
function xml_set_end_namespace_decl_handler($parser, $hdl) {}

/**
 * Set up external entity reference handler 
 *
 * @return bool
 * @param  parser resource
 * @param  hdl string
 */
function xml_set_external_entity_ref_handler($parser, $hdl) {}

/**
 * Set up notation declaration handler 
 *
 * @return bool
 * @param  parser resource
 * @param  hdl string
 */
function xml_set_notation_decl_handler($parser, $hdl) {}

/**
 * Set up object which should be used for callbacks 
 *
 * @return bool
 * @param  parser resource
 * @param  obj object
 */
function xml_set_object($parser, &$obj) {}

/**
 * Set up processing instruction (PI) handler 
 *
 * @return bool
 * @param  parser resource
 * @param  hdl string
 */
function xml_set_processing_instruction_handler($parser, $hdl) {}

/**
 * Set up character data handler 
 *
 * @return bool
 * @param  parser resource
 * @param  hdl string
 */
function xml_set_start_namespace_decl_handler($parser, $hdl) {}

/**
 * Set up unparsed entity declaration handler 
 *
 * @return bool
 * @param  parser resource
 * @param  hdl string
 */
function xml_set_unparsed_entity_decl_handler($parser, $hdl) {}

/**
 * Decodes XML into native PHP types 
 *
 * @return array
 * @param  xml string
 * @param  encoding string[optional]
 */
function xmlrpc_decode($xml, $encoding = null) {}

/**
 * Decodes XML into native PHP types 
 *
 * @return array
 * @param  xml string
 * @param  method string&
 * @param  encoding string[optional]
 */
function xmlrpc_decode_request($xml, $method, $encoding = null) {}

/**
 * Generates XML for a PHP value 
 *
 * @return string
 * @param  value mixed
 */
function xmlrpc_encode($value) {}

/**
 * Generates XML for a method request 
 *
 * @return string
 * @param  method string
 * @param  params mixed
 */
function xmlrpc_encode_request($method, $params) {}

/**
 * Gets xmlrpc type for a PHP value. Especially useful for base64 and datetime strings 
 *
 * @return string
 * @param  value mixed
 */
function xmlrpc_get_type($value) {}

/**
 * Determines if an array value represents an XMLRPC fault. 
 *
 * @return bool
 * @param  array unknown
 */
function xmlrpc_is_fault($array) {}

/**
 * Decodes XML into a list of method descriptions 
 *
 * @return array
 * @param  xml string
 */
function xmlrpc_parse_method_descriptions($xml) {}

/**
 * Adds introspection documentation  
 *
 * @return int
 * @param  server resource
 * @param  desc array
 */
function xmlrpc_server_add_introspection_data($server, $desc) {}

/**
 * Parses XML requests and call methods 
 *
 * @return string
 * @param  server resource
 * @param  xml string
 * @param  user_data mixed
 * @param  output_options array[optional]
 */
function xmlrpc_server_call_method($server, $xml, $user_data, $output_options = null) {}

/**
 * Creates an xmlrpc server 
 *
 * @return resource
 */
function xmlrpc_server_create() {}

/**
 * Destroys server resources 
 *
 * @return int
 * @param  server resource
 */
function xmlrpc_server_destroy($server) {}

/**
 * Register a PHP function to generate documentation 
 *
 * @return bool
 * @param  server resource
 * @param  function string
 */
function xmlrpc_server_register_introspection_callback($server, $function) {}

/**
 * Register a PHP function to handle method matching method_name 
 *
 * @return bool
 * @param  server resource
 * @param  method_name string
 * @param  function string
 */
function xmlrpc_server_register_method($server, $method_name, $function) {}

/**
 * Sets xmlrpc type, base64 or datetime, for a PHP string value 
 *
 * @return bool
 * @param  value string
 * @param  type string
 */
function xmlrpc_set_type($value, $type) {}

/**
 * End attribute - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 */
function xmlwriter_end_attribute($xmlwriter) {}

/**
 * End current CDATA - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 */
function xmlwriter_end_cdata($xmlwriter) {}

/**
 * Create end comment - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 */
function xmlwriter_end_comment($xmlwriter) {}

/**
 * End current document - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 */
function xmlwriter_end_document($xmlwriter) {}

/**
 * End current DTD - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 */
function xmlwriter_end_dtd($xmlwriter) {}

/**
 * End current DTD AttList - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 */
function xmlwriter_end_dtd_attlist($xmlwriter) {}

/**
 * End current DTD element - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 */
function xmlwriter_end_dtd_element($xmlwriter) {}

/**
 * End current DTD Entity - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 */
function xmlwriter_end_dtd_entity($xmlwriter) {}

/**
 * End current element - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 */
function xmlwriter_end_element($xmlwriter) {}

/**
 * End current PI - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 */
function xmlwriter_end_pi($xmlwriter) {}

/**
 * Output current buffer 
 *
 * @return mixed
 * @param  xmlwriter resource
 * @param  empty bool[optional]
 */
function xmlwriter_flush($xmlwriter, $empty = null) {}

/**
 * Create new xmlwriter using memory for string output 
 *
 * @return resource
 */
function xmlwriter_open_memory() {}

/**
 * Create new xmlwriter using source uri for output 
 *
 * @return resource
 * @param  xmlwriter resource
 * @param  source string
 */
function xmlwriter_open_uri($xmlwriter, $source) {}

/**
 * Output current buffer as string 
 *
 * @return string
 * @param  xmlwriter resource
 * @param  flush bool[optional]
 */
function xmlwriter_output_memory($xmlwriter, $flush = null) {}

/**
 * Toggle indentation on/off - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  indent bool
 */
function xmlwriter_set_indent($xmlwriter, $indent) {}

/**
 * Set string used for indenting - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  indentString string
 */
function xmlwriter_set_indent_string($xmlwriter, $indentString) {}

/**
 * Create start attribute - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  name string
 */
function xmlwriter_start_attribute($xmlwriter, $name) {}

/**
 * Create start namespaced attribute - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  prefix string
 * @param  name string
 * @param  uri string
 */
function xmlwriter_start_attribute_ns($xmlwriter, $prefix, $name, $uri) {}

/**
 * Create start CDATA tag - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 */
function xmlwriter_start_cdata($xmlwriter) {}

/**
 * Create start comment - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 */
function xmlwriter_start_comment($xmlwriter) {}

/**
 * Create document tag - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  version string
 * @param  encoding string
 * @param  standalone string
 */
function xmlwriter_start_document($xmlwriter, $version, $encoding, $standalone) {}

/**
 * Create start DTD tag - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  name string
 * @param  pubid string
 * @param  sysid string
 */
function xmlwriter_start_dtd($xmlwriter, $name, $pubid, $sysid) {}

/**
 * Create start DTD AttList - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  name string
 */
function xmlwriter_start_dtd_attlist($xmlwriter, $name) {}

/**
 * Create start DTD element - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  name string
 */
function xmlwriter_start_dtd_element($xmlwriter, $name) {}

/**
 * Create start DTD Entity - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  name string
 * @param  isparam bool
 */
function xmlwriter_start_dtd_entity($xmlwriter, $name, $isparam) {}

/**
 * Create start element tag - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  name string
 */
function xmlwriter_start_element($xmlwriter, $name) {}

/**
 * Create start namespaced element tag - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  prefix string
 * @param  name string
 * @param  uri string
 */
function xmlwriter_start_element_ns($xmlwriter, $prefix, $name, $uri) {}

/**
 * Create start PI tag - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  target string
 */
function xmlwriter_start_pi($xmlwriter, $target) {}

/**
 * Write text - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  content string
 */
function xmlwriter_text($xmlwriter, $content) {}

/**
 * Write full attribute - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  name string
 * @param  content string
 */
function xmlwriter_write_attribute($xmlwriter, $name, $content) {}

/**
 * Write full namespaced attribute - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  prefix string
 * @param  name string
 * @param  uri string
 * @param  content string
 */
function xmlwriter_write_attribute_ns($xmlwriter, $prefix, $name, $uri, $content) {}

/**
 * Write full CDATA tag - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  content string
 */
function xmlwriter_write_cdata($xmlwriter, $content) {}

/**
 * Write full comment tag - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  content string
 */
function xmlwriter_write_comment($xmlwriter, $content) {}

/**
 * Write full DTD tag - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  name string
 * @param  pubid string
 * @param  sysid string
 * @param  subset string
 */
function xmlwriter_write_dtd($xmlwriter, $name, $pubid, $sysid, $subset) {}

/**
 * Write full DTD AttList tag - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  name string
 * @param  content string
 */
function xmlwriter_write_dtd_attlist($xmlwriter, $name, $content) {}

/**
 * Write full DTD element tag - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  name string
 * @param  content string
 */
function xmlwriter_write_dtd_element($xmlwriter, $name, $content) {}

/**
 * Write full DTD Entity tag - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  name string
 * @param  content string
 */
function xmlwriter_write_dtd_entity($xmlwriter, $name, $content) {}

/**
 * Write full element tag - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  name string
 * @param  content string
 */
function xmlwriter_write_element($xmlwriter, $name, $content) {}

/**
 * Write full namesapced element tag - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  prefix string
 * @param  name string
 * @param  uri string
 * @param  content string
 */
function xmlwriter_write_element_ns($xmlwriter, $prefix, $name, $uri, $content) {}

/**
 * Write full PI tag - returns FALSE on error 
 *
 * @return bool
 * @param  xmlwriter resource
 * @param  target string
 * @param  content string
 */
function xmlwriter_write_pi($xmlwriter, $target, $content) {}

/**
 * 
 *
 * @return string
 * @param  namespace string
 * @param  name string
 */
function xsl_xsltprocessor_get_parameter($namespace, $name) {}

/**
 * 
 *
 * @return bool
 */
function xsl_xsltprocessor_has_exslt_support() {}

/**
 * 
 *
 * @return void
 * @param  doc domdocument
 */
function xsl_xsltprocessor_import_stylesheet($doc) {}

/**
 * 
 *
 * @return void
 */
function xsl_xsltprocessor_register_php_functions() {}

/**
 * 
 *
 * @return bool
 * @param  namespace string
 * @param  name string
 */
function xsl_xsltprocessor_remove_parameter($namespace, $name) {}

/**
 * 
 *
 * @return bool
 * @param  namespace string
 * @param  name mixed
 * @param  value string[optional]
 */
function xsl_xsltprocessor_set_parameter($namespace, $name, $value = null) {}

/**
 * 
 *
 * @return domdocument
 * @param  doc domnode
 */
function xsl_xsltprocessor_transform_to_doc($doc) {}

/**
 * 
 *
 * @return int
 * @param  doc domdocument
 * @param  uri string
 */
function xsl_xsltprocessor_transform_to_uri($doc, $uri) {}

/**
 * 
 *
 * @return string
 * @param  doc domdocument
 */
function xsl_xsltprocessor_transform_to_xml($doc) {}

/**
 * 
 *
 * @return int
 */
function zend_current_obfuscation_level() {}

/**
 * Get the value of a PHP configuration option - but search in zend.ini first... 
 *
 * @return string
 * @param  option_name string
 */
function zend_get_cfg_var($option_name) {}

/**
 * Verifies license in filename, if it's valid - returns array of license fields, if not - returns error string 
 *
 * @return mixed
 * @param  filename string
 */
function zend_get_license_info($filename) {}

/**
 * Looks up the license in the path and returns it. If no license, returns error. 
 *
 * @return mixed
 * @param  path string
 */
function zend_get_platform_license($path) {}

/**
 * Returns the name of the current file being executed. 
 *
 * @return string
 */
function zend_loader_current_file() {}

/**
 * Returns true if the Zend Optimizer is configured to load Zend-encoded files. 
 *
 * @return boolean
 */
function zend_loader_enabled() {}

/**
 * Returns true if the current file is a Zend-encoded file. 
 *
 * @return boolean
 */
function zend_loader_file_encoded() {}

/**
 * Returns license if the current file has a valid license, false otherwise 
 *
 * @return array
 */
function zend_loader_file_licensed() {}

/**
 * Dynamically load a license for a Zend-encoded application 
 *
 * @return boolean
 * @param  license_file string
 * @param  override bool
 */
function zend_loader_install_license($license_file, $override) {}

/**
 * Return the special ID used to request the Zend logo in phpinfo screens
 *
 * @return string
 */
function zend_logo_guid() {}

/**
 * 
 *
 * @return bool
 * @param  host string
 * @param  masks string
 * @param  delimiter string
 */
function zend_match_hostmasks($host, $masks, $delimiter) {}

/**
 * 
 *
 * @return string
 * @param  class_name string
 */
function zend_obfuscate_class_name($class_name) {}

/**
 * 
 *
 * @return string
 * @param  function_name string
 */
function zend_obfuscate_function_name($function_name) {}

/**
 * 
 *
 * @return int
 */
function zend_runtime_obfuscate() {}

/**
 * Get the version of the Zend Engine 
 *
 * @return string
 */
function zend_version() {}

/**
 * Returns the coding type used for output compression 
 *
 * @return string
 */
function zlib_get_coding_type() {}


class stdClass {
};

/**
 * @return resource
 * @param  connection_string string
 * @param  connect_type int
 * @desc   Open a PostgreSQL connection 
 */
function pg_connect($connection_string, $connect_type) {}

/**
 * @return resource
 * @param  host string
 * @param  port string
 * @param  options string[optional]
 * @param  tty string[optional]
 * @param  database string
 * @desc   Open a PostgreSQL connection 
 */
function pg_connect($host, $port, $options = null, $tty = null, $database) {}

class Exception {
    /**
     * Clone the exception object 
     *
     * @return Exception
     */
    function __clone() {}

    /**
     * Exception constructor 
     *
     * @return Exception
     * @param  message string
     * @param  code int
     */
    function __construct($message, $code) {}

    /**
     * Get the file in which the exception occurred 
     *
     * @return string
     */
    function getFile() {}

    /**
     * Get the line in which the exception occurred 
     *
     * @return int
     */
    function getLine() {}

    /**
     * Get the exception message 
     *
     * @return string
     */
    function getMessage() {}

    /**
     * Get the exception code 
     *
     * @return int
     */
    function getCode() {}

    /**
     * Get the stack trace for the location in which the exception occurred 
     *
     * @return array
     */
    function getTrace() {}

    /**
     * Obtain the backtrace for the exception as a string (instead of an array) 
     *
     * @return string
     */
    function getTraceAsString() {}

    /**
     * Obtain the string representation of the Exception object 
     *
     * @return string
     */
    function __toString() {}

};

class ErrorException extends Exception {
    /**
     * ErrorException constructor 
     *
     * @return ErrorException
     * @param  message string
     * @param  code int
     * @param  severity int
     * @param  filename string[optional]
     * @param  lineno int[optional]
     */
    function __construct($message, $code, $severity, $filename = null, $lineno = null) {}

    /**
     * Get the exception severity 
     *
     * @return int
     */
    function getSeverity() {}

};

class ApacheRequest {
    /**
     * 
     *
     * @return string
     * @param  new_filename string[optional]
     */
    function filename($new_filename = null) {}

    /**
     * 
     *
     * @return string
     * @param  new_uri string[optional]
     */
    function uri($new_uri = null) {}

    /**
     * 
     *
     * @return string
     * @param  new_unparsed_uri string[optional]
     */
    function unparsed_uri($new_unparsed_uri = null) {}

    /**
     * 
     *
     * @return string
     * @param  new_path_info string[optional]
     */
    function path_info($new_path_info = null) {}

    /**
     * 
     *
     * @return string
     * @param  new_args string[optional]
     */
    function args($new_args = null) {}

    /**
     * 
     *
     * @return string
     */
    function boundary() {}

    /**
     * 
     *
     * @return string
     * @param  new_type string[optional]
     */
    function content_type($new_type = null) {}

    /**
     * 
     *
     * @return string
     * @param  new_encoding string[optional]
     */
    function content_encoding($new_encoding = null) {}

    /**
     * 
     *
     * @return string
     * @param  new_handler string[optional]
     */
    function handler($new_handler = null) {}

    /**
     * 
     *
     * @return string
     */
    function the_request() {}

    /**
     * 
     *
     * @return string
     */
    function protocol() {}

    /**
     * 
     *
     * @return string
     */
    function hostname() {}

    /**
     * 
     *
     * @return string
     * @param  new_status_line string[optional]
     */
    function status_line($new_status_line = null) {}

    /**
     * 
     *
     * @return string
     */
    function method() {}

    /**
     * 
     *
     * @return int
     */
    function proto_num() {}

    /**
     * 
     *
     * @return int
     */
    function assbackwards() {}

    /**
     * 
     *
     * @return int
     * @param  new_proxyreq int[optional]
     */
    function proxyreq($new_proxyreq = null) {}

    /**
     * 
     *
     * @return int
     */
    function chunked() {}

    /**
     * 
     *
     * @return int
     */
    function header_only() {}

    /**
     * 
     *
     * @return int
     */
    function request_time() {}

    /**
     * 
     *
     * @return int
     * @param  new_status int[optional]
     */
    function status($new_status = null) {}

    /**
     * 
     *
     * @return int
     * @param  method_number int[optional]
     */
    function method_number($method_number = null) {}

    /**
     * 
     *
     * @return int
     * @param  allowed int[optional]
     */
    function allowed($allowed = null) {}

    /**
     * 
     *
     * @return int
     */
    function bytes_sent() {}

    /**
     * 
     *
     * @return int
     */
    function mtime() {}

    /**
     * 
     *
     * @return int
     * @param  new_content_length int[optional]
     */
    function content_length($new_content_length = null) {}

    /**
     * 
     *
     * @return int
     */
    function remaining() {}

    /**
     * 
     *
     * @return int
     */
    function no_cache() {}

    /**
     * 
     *
     * @return int
     */
    function no_local_copy() {}

    /**
     * 
     *
     * @return int
     */
    function read_body() {}

    /**
     * 
     *
     * @return array
     */
    function headers_in() {}

    /**
     * 
     *
     * @return array
     * @param  name|array_list} {string[optional]
     * @param  value string[optional]
     * @param  replace bool[optional]
     */
    function headers_out($name = null, $value = null, $replace = false) {}

    /**
     * 
     *
     * @return array
     * @param  name|array_list} {string[optional]
     * @param  value string[optional]
     * @param  replace bool[optional]
     */
    function err_headers_out($name = null, $value = null, $replace = false) {}

    /**
     * 
     *
     * @return string
     */
    function auth_name() {}

    /**
     * 
     *
     * @return string
     */
    function auth_type() {}

    /**
     * 
     *
     * @return unknown
     */
    function basic_auth_pw() {}

    /**
     * 
     *
     * @return long
     */
    function discard_request_body() {}

    /**
     * 
     *
     * @return bool
     */
    function is_initial_req() {}

    /**
     * 
     *
     * @return long
     */
    function meets_conditions() {}

    /**
     * 
     *
     * @return int
     * @param  type int[optional]
     */
    function remote_host($type = null) {}

    /**
     * 
     *
     * @return long
     */
    function satisfies() {}

    /**
     * 
     *
     * @return int
     */
    function server_port() {}

    /**
     * 
     *
     * @return void
     */
    function set_etag() {}

    /**
     * 
     *
     * @return void
     */
    function set_last_modified() {}

    /**
     * 
     *
     * @return bool
     */
    function some_auth_required() {}

    /**
     * 
     *
     * @return long
     * @param  dependency_mtime int[optional]
     */
    function update_mtime($dependency_mtime = null) {}

    /**
     * 
     *
     * @return boolean
     * @param  message string
     * @param  facility long[optional]
     */
    function log_error($message, $facility = null) {}

    /**
     * 
     *
     * @return object
     * @param  uri string
     */
    function lookup_uri($uri) {}

    /**
     * 
     *
     * @return object
     * @param  file string
     */
    function lookup_file($file) {}

    /**
     * 
     *
     * @return object
     * @param  method string
     * @param  uri string
     */
    function method_uri($method, $uri) {}

    /**
     * 
     *
     * @return long
     */
    function run() {}

};

class PDO {
    /**
     * Creates a new large object, returning its identifier.  Must be called inside a transaction. 
     *
     * @return string
     */
    function pgsqlLOBCreate() {}

    /**
     * Opens an existing large object stream.  Must be called inside a transaction. 
     *
     * @return resource
     * @param  oid string
     * @param  mode string[optional]
     */
    function pgsqlLOBOpen($oid, $mode = 'rb') {}

    /**
     * Deletes the large object identified by oid.  Must be called inside a transaction. 
     *
     * @return bool
     * @param  oid string
     */
    function pgsqlLOBUnlink($oid) {}

    /**
     * 
     *
     * @return PDO
     * @param  dsn string
     * @param  username string
     * @param  passwd string
     * @param  options array[optional]
     */
    function __construct($dsn, $username, $passwd, $options = null) {}

    /**
     * Prepares a statement for execution and returns a statement object 
     *
     * @return PDOStatement
     * @param  statment string
     * @param  options array[optional]
     */
    function prepare($statment, $options = null) {}

    /**
     * Initiates a transaction 
     *
     * @return bool
     */
    function beginTransaction() {}

    /**
     * Commit a transaction 
     *
     * @return bool
     */
    function commit() {}

    /**
     * roll back a transaction 
     *
     * @return bool
     */
    function rollBack() {}

    /**
     * Set an attribute 
     *
     * @return bool
     * @param  attribute long
     * @param  value mixed
     */
    function setAttribute($attribute, $value) {}

    /**
     * Get an attribute 
     *
     * @return mixed
     * @param  attribute long
     */
    function getAttribute($attribute) {}

    /**
     * Execute a query that does not return a row set, returning the number of affected rows 
     *
     * @return int
     * @param  query string
     */
    function exec($query) {}

    /**
     * Returns the id of the last row that we affected on this connection.  Some databases require a sequence or table name to be passed in.  Not always meaningful. 
     *
     * @return string
     * @param  seqname string[optional]
     */
    function lastInsertId($seqname = null) {}

    /**
     * Fetch the error code associated with the last operation on the database handle 
     *
     * @return string
     */
    function errorCode() {}

    /**
     * Fetch extended error information associated with the last operation on the database handle 
     *
     * @return array
     */
    function errorInfo() {}

    /**
     * Prepare and execute $sql; returns the statement object for iteration 
     *
     * @return bool
     * @param  sql string
     * @param  args PDOStatement::setFetchMode()[optional]
     */
    function query($sql, $args = null) {}

    /**
     * quotes string for use in a query.  The optional paramtype acts as a hint for drivers that have alternate quoting styles.  The default value is PDO_PARAM_STR 
     *
     * @return string
     * @param  string string
     * @param  paramtype int[optional]
     */
    function quote($string, $paramtype = null) {}

    /**
     * Prevents use of a PDO instance that has been unserialized 
     *
     * @return int
     */
    function __wakeup() {}

    /**
     * Prevents serialization of a PDO instance 
     *
     * @return int
     */
    function __sleep() {}

    /**
     * Execute an external program 
     *
     * @return string
     * @param  command string
     * @param  output array[optional]
     * @param  return_value int[optional]
     */
    function exec($command, &$output, &$return_value) {}

};

class DOMAttr {
    /**
     * 
     *
     * @return DOMAttr
     * @param  name string
     * @param  value string[optional]
     */
    function __construct($name, $value = null) {}

    /**
     * 
     *
     * @return boolean
     */
    function isId() {}

};

class DOMProcessingInstruction {
    /**
     * 
     *
     * @return DOMProcessingInstruction
     * @param  name string
     * @param  value string[optional]
     */
    function __construct($name, $value = null) {}

};

class DOMEntityReference {
    /**
     * 
     *
     * @return DOMEntityReference
     * @param  name string
     */
    function __construct($name) {}

};

class DOMDocument {
    /**
     * 
     *
     * @return DOMDocument
     * @param  version string[optional]
     * @param  encoding string[optional]
     */
    function __construct($version = null, $encoding = null) {}

    /**
     * 
     *
     * @return DOMElement
     * @param  tagName string
     * @param  value string[optional]
     */
    function createElement($tagName, $value = null) {}

    /**
     * 
     *
     * @return DOMDocumentFragment
     */
    function createDocumentFragment() {}

    /**
     * 
     *
     * @return DOMText
     * @param  data string
     */
    function createTextNode($data) {}

    /**
     * 
     *
     * @return DOMComment
     * @param  data string
     */
    function createComment($data) {}

    /**
     * 
     *
     * @return DOMCdataSection
     * @param  data string
     */
    function createCDATASection($data) {}

    /**
     * 
     *
     * @return DOMProcessingInstruction
     * @param  target string
     * @param  data string
     */
    function createProcessingInstruction($target, $data) {}

    /**
     * 
     *
     * @return DOMAttr
     * @param  name string
     */
    function createAttribute($name) {}

    /**
     * 
     *
     * @return DOMEntityReference
     * @param  name string
     */
    function createEntityReference($name) {}

    /**
     * 
     *
     * @return DOMNodeList
     * @param  tagname string
     */
    function getElementsByTagName($tagname) {}

    /**
     * 
     *
     * @return DOMNode
     * @param  importedNode DOMNode
     * @param  deep boolean
     */
    function importNode($importedNode, $deep) {}

    /**
     * 
     *
     * @return DOMElement
     * @param  namespaceURI string
     * @param  qualifiedName string
     * @param  value string[optional]
     */
    function createElementNS($namespaceURI, $qualifiedName, $value = null) {}

    /**
     * 
     *
     * @return DOMAttr
     * @param  namespaceURI string
     * @param  qualifiedName string
     */
    function createAttributeNS($namespaceURI, $qualifiedName) {}

    /**
     * 
     *
     * @return DOMNodeList
     * @param  namespaceURI string
     * @param  localName string
     */
    function getElementsByTagNameNS($namespaceURI, $localName) {}

    /**
     * 
     *
     * @return DOMElement
     * @param  elementId string
     */
    function getElementById($elementId) {}

    /**
     * 
     *
     * @return DOMNode
     * @param  source DOMNode
     */
    function adoptNode($source) {}

    /**
     * 
     *
     * @return void
     */
    function normalizeDocument() {}

    /**
     * 
     *
     * @return DOMNode
     * @param  n node
     * @param  namespaceURI string
     * @param  qualifiedName string
     */
    function renameNode($n, $namespaceURI, $qualifiedName) {}

    /**
     * 
     *
     * @return int
     * @param  file string
     */
    function save($file) {}

    /**
     * 
     *
     * @return string
     * @param  n node[optional]
     */
    function saveXML($n = null) {}

    /**
     * 
     *
     * @return boolean
     */
    function validate() {}

    /**
     * Substitutues xincludes in a DomDocument 
     *
     * @return int
     * @param  options int[optional]
     */
    function xinclude($options = null) {}

    /**
     * 
     *
     * @return string
     */
    function saveHTML() {}

    /**
     * 
     *
     * @return int
     * @param  file string
     */
    function saveHTMLFile($file) {}

    /**
     * 
     *
     * @return boolean
     * @param  filename string
     */
    function schemaValidate($filename) {}

    /**
     * 
     *
     * @return boolean
     * @param  filename string
     */
    function relaxNGValidate($filename) {}

    /**
     * 
     *
     * @return boolean
     * @param  source string
     */
    function relaxNGValidateSource($source) {}

};

class DOMElement {
    /**
     * 
     *
     * @return DOMElement
     * @param  name string
     * @param  value string[optional]
     * @param  uri string[optional]
     */
    function __construct($name, $value = null, $uri = null) {}

    /**
     * 
     *
     * @return string
     * @param  name string
     */
    function getAttribute($name) {}

    /**
     * 
     *
     * @return void
     * @param  name string
     * @param  value string
     */
    function setAttribute($name, $value) {}

    /**
     * 
     *
     * @return void
     * @param  name string
     */
    function removeAttribute($name) {}

    /**
     * 
     *
     * @return DOMAttr
     * @param  name string
     */
    function getAttributeNode($name) {}

    /**
     * 
     *
     * @return DOMAttr
     * @param  newAttr DOMAttr
     */
    function setAttributeNode($newAttr) {}

    /**
     * 
     *
     * @return DOMAttr
     * @param  oldAttr DOMAttr
     */
    function removeAttributeNode($oldAttr) {}

    /**
     * 
     *
     * @return DOMNodeList
     * @param  name string
     */
    function getElementsByTagName($name) {}

    /**
     * 
     *
     * @return string
     * @param  namespaceURI string
     * @param  localName string
     */
    function getAttributeNS($namespaceURI, $localName) {}

    /**
     * 
     *
     * @return void
     * @param  namespaceURI string
     * @param  qualifiedName string
     * @param  value string
     */
    function setAttributeNS($namespaceURI, $qualifiedName, $value) {}

    /**
     * 
     *
     * @return void
     * @param  namespaceURI string
     * @param  localName string
     */
    function removeAttributeNS($namespaceURI, $localName) {}

    /**
     * 
     *
     * @return DOMAttr
     * @param  namespaceURI string
     * @param  localName string
     */
    function getAttributeNodeNS($namespaceURI, $localName) {}

    /**
     * 
     *
     * @return DOMAttr
     * @param  newAttr DOMAttr
     */
    function setAttributeNodeNS($newAttr) {}

    /**
     * 
     *
     * @return DOMNodeList
     * @param  namespaceURI string
     * @param  localName string
     */
    function getElementsByTagNameNS($namespaceURI, $localName) {}

    /**
     * 
     *
     * @return boolean
     * @param  name string
     */
    function hasAttribute($name) {}

    /**
     * 
     *
     * @return boolean
     * @param  namespaceURI string
     * @param  localName string
     */
    function hasAttributeNS($namespaceURI, $localName) {}

    /**
     * 
     *
     * @return void
     * @param  name string
     * @param  isId boolean
     */
    function setIdAttribute($name, $isId) {}

    /**
     * 
     *
     * @return void
     * @param  namespaceURI string
     * @param  localName string
     * @param  isId boolean
     */
    function setIdAttributeNS($namespaceURI, $localName, $isId) {}

    /**
     * 
     *
     * @return void
     * @param  idAttr attr
     * @param  isId boolean
     */
    function setIdAttributeNode($idAttr, $isId) {}

};

class DOMComment {
    /**
     * 
     *
     * @return DOMComment
     * @param  value string[optional]
     */
    function __construct($value = null) {}

};

class DOMXPath {
    /**
     * 
     *
     * @return DOMXPath
     * @param  doc DOMDocument
     */
    function __construct($doc) {}

};

class DOMCdataSection {
    /**
     * 
     *
     * @return DOMCdataSection
     * @param  value string
     */
    function __construct($value) {}

};

class DOMText {
    /**
     * 
     *
     * @return DOMText
     * @param  value string[optional]
     */
    function __construct($value = null) {}

    /**
     * 
     *
     * @return DOMText
     * @param  offset int
     */
    function splitText($offset) {}

    /**
     * 
     *
     * @return boolean
     */
    function isWhitespaceInElementContent() {}

    /**
     * 
     *
     * @return boolean
     */
    function isElementContentWhitespace() {}

    /**
     * 
     *
     * @return DOMText
     * @param  content string
     */
    function replaceWholeText($content) {}

};

class DOMDocumentFragment {
    /**
     * 
     *
     * @return DOMDocumentFragment
     */
    function __construct() {}

    /**
     * 
     *
     * @return bool
     * @param  data string
     */
    function appendXML($data) {}

};

class Reflection {
    /**
     * Exports a reflection object. Returns the output if TRUE is specified for return, printing it otherwise. 
     *
     * @return mixed
     * @param  r Reflector
     * @param  return bool[optional]
     */
    function export($r, $return = null) {}

    /**
     * Returns an array of modifier names 
     *
     * @return array
     * @param  modifiers int
     */
    function getModifierNames($modifiers) {}

};

class ReflectionFunction {
    /**
     * Exports a reflection object. Returns the output if TRUE is specified for return, printing it otherwise. 
     *
     * @return mixed
     * @param  name string
     * @param  return bool[optional]
     */
    function export($name, $return = null) {}

    /**
     * Constructor. Throws an Exception in case the given function does not exist 
     *
     * @return ReflectionFunction
     * @param  name string
     */
    function __construct($name) {}

    /**
     * Returns a string representation 
     *
     * @return string
     */
    function __toString() {}

    /**
     * Returns this function's name 
     *
     * @return string
     */
    function getName() {}

    /**
     * Returns whether this is an internal function 
     *
     * @return bool
     */
    function isInternal() {}

    /**
     * Returns whether this is an user-defined function 
     *
     * @return bool
     */
    function isUserDefined() {}

    /**
     * Returns the filename of the file this function was declared in 
     *
     * @return string
     */
    function getFileName() {}

    /**
     * Returns the line this function's declaration starts at 
     *
     * @return int
     */
    function getStartLine() {}

    /**
     * Returns the line this function's declaration ends at 
     *
     * @return int
     */
    function getEndLine() {}

    /**
     * Returns the doc comment for this function 
     *
     * @return string
     */
    function getDocComment() {}

    /**
     * Returns an associative array containing this function's static variables and their values 
     *
     * @return array
     */
    function getStaticVariables() {}

    /**
     * Invokes the function 
     *
     * @return mixed
     * @param  args mixed*
     */
    function invoke($args) {}

    /**
     * Invokes the function and pass its arguments as array. 
     *
     * @return mixed
     * @param  args array
     */
    function invokeArgs($args) {}

    /**
     * Gets whether this function returns a reference 
     *
     * @return bool
     */
    function returnsReference() {}

    /**
     * Gets the number of required parameters 
     *
     * @return bool
     */
    function getNumberOfParameters() {}

    /**
     * Gets the number of required parameters 
     *
     * @return bool
     */
    function getNumberOfRequiredParameters() {}

    /**
     * Returns an array of parameter objects for this function 
     *
     * @return ReflectionParameter[]
     */
    function getParameters() {}

    /**
     * Returns NULL or the extension the function belongs to 
     *
     * @return ReflectionExtension|NULL
     */
    function getExtension() {}

    /**
     * Returns false or the name of the extension the function belongs to 
     *
     * @return string|false
     */
    function getExtensionName() {}

    /**
     * Returns whether this function is deprecated 
     *
     * @return bool
     */
    function isDeprecated() {}

};

class ReflectionParameter {
    /**
     * Exports a reflection object. Returns the output if TRUE is specified for return, printing it otherwise. 
     *
     * @return mixed
     * @param  function mixed
     * @param  parameter mixed
     * @param  return bool[optional]
     * @throws ReflectionException
     */
    function export($function, $parameter, $return = null) {}

    /**
     * Constructor. Throws an Exception in case the given method does not exist 
     *
     * @return ReflectionParameter
     * @param  function mixed
     * @param  parameter mixed
     */
    function __construct($function, $parameter) {}

    /**
     * Returns a string representation 
     *
     * @return string
     */
    function __toString() {}

    /**
     * Returns this parameters's name 
     *
     * @return string
     */
    function getName() {}

    /**
     * Returns the ReflectionFunction for the function of this parameter 
     *
     * @return ReflectionFunction
     */
    function getDeclaringFunction() {}

    /**
     * Returns in which class this parameter is defined (not the typehint of the parameter) 
     *
     * @return ReflectionClass|NULL
     */
    function getDeclaringClass() {}

    /**
     * Returns this parameters's class hint or NULL if there is none 
     *
     * @return ReflectionClass|NULL
     */
    function getClass() {}

    /**
     * Returns whether parameter MUST be an array 
     *
     * @return bool
     */
    function isArray() {}

    /**
     * Returns whether NULL is allowed as this parameters's value 
     *
     * @return bool
     */
    function allowsNull() {}

    /**
     * Returns whether this parameters is passed to by reference 
     *
     * @return bool
     */
    function isPassedByReference() {}

    /**
     * Returns whether this parameter is an optional parameter 
     *
     * @return bool
     */
    function getPosition() {}

    /**
     * Returns whether this parameter is an optional parameter 
     *
     * @return bool
     */
    function isOptional() {}

    /**
     * Returns whether the default value of this parameter is available 
     *
     * @return bool
     */
    function isDefaultValueAvailable() {}

    /**
     * Returns the default value of this parameter or throws an exception 
     *
     * @return bool
     */
    function getDefaultValue() {}

};

class ReflectionMethod extends ReflectionFunction {
    /**
     * Exports a reflection object. Returns the output if TRUE is specified for return, printing it otherwise. 
     *
     * @return mixed
     * @param  class mixed
     * @param  name string
     * @param  return bool[optional]
     * @throws ReflectionException
     */
    function export($class, $name, $return = null) {}

    /**
     * Constructor. Throws an Exception in case the given method does not exist 
     *
     * @return ReflectionMethod
     * @param  class_or_method mixed
     * @param  name string[optional]
     */
    function __construct($class_or_method, $name = null) {}

    /**
     * Returns a string representation 
     *
     * @return string
     */
    function __toString() {}

    /**
     * Invokes the method. 
     *
     * @return mixed
     * @param  object mixed
     * @param  args mixed*
     */
    function invoke($object, $args) {}

    /**
     * Invokes the function and pass its arguments as array. 
     *
     * @return mixed
     * @param  object mixed
     * @param  args array
     */
    function invokeArgs($object, $args) {}

    /**
     * Returns whether this method is final 
     *
     * @return bool
     */
    function isFinal() {}

    /**
     * Returns whether this method is abstract 
     *
     * @return bool
     */
    function isAbstract() {}

    /**
     * Returns whether this method is public 
     *
     * @return bool
     */
    function isPublic() {}

    /**
     * Returns whether this method is private 
     *
     * @return bool
     */
    function isPrivate() {}

    /**
     * Returns whether this method is protected 
     *
     * @return bool
     */
    function isProtected() {}

    /**
     * Returns whether this method is static 
     *
     * @return bool
     */
    function isStatic() {}

    /**
     * Returns whether this method is the constructor 
     *
     * @return bool
     */
    function isConstructor() {}

    /**
     * Returns whether this method is static 
     *
     * @return bool
     */
    function isDestructor() {}

    /**
     * Returns a bitfield of the access modifiers for this method 
     *
     * @return int
     */
    function getModifiers() {}

    /**
     * Get the declaring class 
     *
     * @return ReflectionClass
     */
    function getDeclaringClass() {}

    /**
     * Get the prototype 
     *
     * @return ReflectionClass
     */
    function getPrototype() {}

};

class ReflectionClass {
    /**
     * Exports a reflection object. Returns the output if TRUE is specified for return, printing it otherwise. 
     *
     * @return mixed
     * @param  argument mixed
     * @param  return bool[optional]
     * @throws ReflectionException
     */
    function export($argument, $return = null) {}

    /**
     * Constructor. Takes a string or an instance as an argument 
     *
     * @return ReflectionClass
     * @param  argument mixed
     * @throws ReflectionException
     */
    function __construct($argument) {}

    /**
     * Returns an associative array containing all static property values of the class 
     *
     * @return array
     */
    function getStaticProperties() {}

    /**
     * Returns the value of a tsstic property 
     *
     * @return mixed
     * @param  name string
     * @param  default mixed[optional]
     */
    function getStaticPropertyValue($name, $default = null) {}

    /**
     * Sets the value of a static property 
     *
     * @return void
     * @param  name unknown
     * @param  value unknown
     */
    function setStaticPropertyValue($name, $value) {}

    /**
     * Returns an associative array containing copies of all default property values of the class 
     *
     * @return array
     */
    function getDefaultProperties() {}

    /**
     * Returns a string representation 
     *
     * @return string
     */
    function __toString() {}

    /**
     * Returns the class' name 
     *
     * @return string
     */
    function getName() {}

    /**
     * Returns whether this class is an internal class 
     *
     * @return bool
     */
    function isInternal() {}

    /**
     * Returns whether this class is user-defined 
     *
     * @return bool
     */
    function isUserDefined() {}

    /**
     * Returns the filename of the file this class was declared in 
     *
     * @return string
     */
    function getFileName() {}

    /**
     * Returns the line this class' declaration starts at 
     *
     * @return int
     */
    function getStartLine() {}

    /**
     * Returns the line this class' declaration ends at 
     *
     * @return int
     */
    function getEndLine() {}

    /**
     * Returns the doc comment for this class 
     *
     * @return string
     */
    function getDocComment() {}

    /**
     * Returns the class' constructor if there is one, NULL otherwise 
     *
     * @return ReflectionMethod
     */
    function getConstructor() {}

    /**
     * Returns whether a method exists or not 
     *
     * @return bool
     * @param  name string
     */
    function hasMethod($name) {}

    /**
     * Returns the class' method specified by its name 
     *
     * @return ReflectionMethod
     * @param  name string
     * @throws ReflectionException
     */
    function getMethod($name) {}

    /**
     * Returns an array of this class' methods 
     *
     * @return ReflectionMethod[]
     */
    function getMethods() {}

    /**
     * Returns whether a property exists or not 
     *
     * @return bool
     * @param  name string
     */
    function hasProperty($name) {}

    /**
     * Returns the class' property specified by its name 
     *
     * @return ReflectionProperty
     * @param  name string
     * @throws ReflectionException
     */
    function getProperty($name) {}

    /**
     * Returns an array of this class' properties 
     *
     * @return ReflectionProperty[]
     */
    function getProperties() {}

    /**
     * Returns whether a constant exists or not 
     *
     * @return bool
     * @param  name string
     */
    function hasConstant($name) {}

    /**
     * Returns an associative array containing this class' constants and their values 
     *
     * @return array
     */
    function getConstants() {}

    /**
     * Returns the class' constant specified by its name 
     *
     * @return mixed
     * @param  name string
     */
    function getConstant($name) {}

    /**
     * Returns whether this class is instantiable 
     *
     * @return bool
     */
    function isInstantiable() {}

    /**
     * Returns whether this is an interface or a class 
     *
     * @return bool
     */
    function isInterface() {}

    /**
     * Returns whether this class is final 
     *
     * @return bool
     */
    function isFinal() {}

    /**
     * Returns whether this class is abstract 
     *
     * @return bool
     */
    function isAbstract() {}

    /**
     * Returns a bitfield of the access modifiers for this class 
     *
     * @return int
     */
    function getModifiers() {}

    /**
     * Returns whether the given object is an instance of this class 
     *
     * @return bool
     * @param  object stdclass
     */
    function isInstance($object) {}

    /**
     * Returns an instance of this class 
     *
     * @return stdclass
     * @param  args mixed*
     * @vararg ...
     */
    function newInstance($args) {}

    /**
     * Returns an instance of this class 
     *
     * @return stdclass
     * @param  args array
     */
    function newInstanceArgs($args) {}

    /**
     * Returns an array of interfaces this class implements 
     *
     * @return ReflectionClass[]
     */
    function getInterfaces() {}

    /**
     * Returns the class' parent class, or, if none exists, FALSE 
     *
     * @return ReflectionClass
     */
    function getParentClass() {}

    /**
     * Returns whether this class is a subclass of another class 
     *
     * @return bool
     * @param  class string|ReflectionClass
     */
    function isSubclassOf($class) {}

    /**
     * Returns whether this class is a subclass of another class 
     *
     * @return bool
     * @param  interface_name string|ReflectionClass
     */
    function implementsInterface($interface_name) {}

    /**
     * Returns whether this class is iterateable (can be used inside foreach) 
     *
     * @return bool
     */
    function isIterateable() {}

    /**
     * Returns NULL or the extension the class belongs to 
     *
     * @return ReflectionExtension|NULL
     */
    function getExtension() {}

    /**
     * Returns false or the name of the extension the class belongs to 
     *
     * @return string|false
     */
    function getExtensionName() {}

};

class ReflectionObject extends ReflectionClass {
    /**
     * Exports a reflection object. Returns the output if TRUE is specified for return, printing it otherwise. 
     *
     * @return mixed
     * @param  argument mixed
     * @param  return bool[optional]
     * @throws ReflectionException
     */
    function export($argument, $return = null) {}

    /**
     * Constructor. Takes an instance as an argument 
     *
     * @return ReflectionObject
     * @param  argument mixed
     * @throws ReflectionException
     */
    function __construct($argument) {}

};

class ReflectionProperty {
    /**
     * Exports a reflection object. Returns the output if TRUE is specified for return, printing it otherwise. 
     *
     * @return mixed
     * @param  class mixed
     * @param  name string
     * @param  return bool[optional]
     * @throws ReflectionException
     */
    function export($class, $name, $return = null) {}

    /**
     * Constructor. Throws an Exception in case the given property does not exist 
     *
     * @return ReflectionProperty
     * @param  class mixed
     * @param  name string
     */
    function __construct($class, $name) {}

    /**
     * Returns a string representation 
     *
     * @return string
     */
    function __toString() {}

    /**
     * Returns the class' name 
     *
     * @return string
     */
    function getName() {}

    /**
     * Returns whether this property is public 
     *
     * @return bool
     */
    function isPublic() {}

    /**
     * Returns whether this property is private 
     *
     * @return bool
     */
    function isPrivate() {}

    /**
     * Returns whether this property is protected 
     *
     * @return bool
     */
    function isProtected() {}

    /**
     * Returns whether this property is static 
     *
     * @return bool
     */
    function isStatic() {}

    /**
     * Returns whether this property is default (declared at compilation time). 
     *
     * @return bool
     */
    function isDefault() {}

    /**
     * Returns a bitfield of the access modifiers for this property 
     *
     * @return int
     */
    function getModifiers() {}

    /**
     * Returns this property's value 
     *
     * @return mixed
     * @param  object stdclass[optional]
     */
    function getValue($object = null) {}

    /**
     * Sets this property's value 
     *
     * @return void
     * @param  object stdclass[optional]
     * @param  value mixed
     */
    function setValue($object = null, $value) {}

    /**
     * Get the declaring class 
     *
     * @return ReflectionClass
     */
    function getDeclaringClass() {}

    /**
     * Returns the doc comment for this property 
     *
     * @return string
     */
    function getDocComment() {}

};

class ReflectionExtension {
    /**
     * Exports a reflection object. Returns the output if TRUE is specified for return, printing it otherwise. 
     *
     * @return mixed
     * @param  name string
     * @param  return bool[optional]
     * @throws ReflectionException
     */
    function export($name, $return = null) {}

    /**
     * Constructor. Throws an Exception in case the given extension does not exist 
     *
     * @return ReflectionExtension
     * @param  name string
     */
    function __construct($name) {}

    /**
     * Returns a string representation 
     *
     * @return string
     */
    function __toString() {}

    /**
     * Returns this extension's name 
     *
     * @return string
     */
    function getName() {}

    /**
     * Returns this extension's version 
     *
     * @return string
     */
    function getVersion() {}

    /**
     * Returns an array of this extension's fuctions 
     *
     * @return ReflectionFunction[]
     */
    function getFunctions() {}

    /**
     * Returns an associative array containing this extension's constants and their values 
     *
     * @return array
     */
    function getConstants() {}

    /**
     * Returns an associative array containing this extension's INI entries and their values 
     *
     * @return array
     */
    function getINIEntries() {}

    /**
     * Returns an array containing ReflectionClass objects for all classes of this extension 
     *
     * @return ReflectionClass[]
     */
    function getClasses() {}

    /**
     * Returns an array containing all names of all classes of this extension 
     *
     * @return array
     */
    function getClassNames() {}

    /**
     * Returns an array containing all names of all extensions this extension depends on 
     *
     * @return array
     */
    function getDependencies() {}

};

class XMLReader {
    /**
     * Closes xmlreader - current frees resources until xmlTextReaderClose is fixed in libxml 
     *
     * @return bool
     */
    function close() {}

    /**
     * Get value of an attribute from current element 
     *
     * @return string
     * @param  name string
     */
    function getAttribute($name) {}

    /**
     * Get value of an attribute at index from current element 
     *
     * @return string
     * @param  index int
     */
    function getAttributeNo($index) {}

    /**
     * Get value of a attribute via name and namespace from current element 
     *
     * @return string
     * @param  name string
     * @param  namespaceURI string
     */
    function getAttributeNs($name, $namespaceURI) {}

    /**
     * Indicates whether given property (one of the parser option constants) is set or not on parser 
     *
     * @return bool
     * @param  property int
     */
    function getParserProperty($property) {}

    /**
     * 
     *
     * @return bool
     */
    function isValid() {}

    /**
     * Return namespaceURI for associated prefix on current node 
     *
     * @return bool
     * @param  prefix string
     */
    function lookupNamespace($prefix) {}

    /**
     * Positions reader at specified attribute - Returns TRUE on success and FALSE on failure 
     *
     * @return bool
     * @param  name string
     */
    function moveToAttribute($name) {}

    /**
     * 
     *
     * @return bool
     * @param  index int
     */
    function moveToAttributeNo($index) {}

    /**
     * 
     *
     * @return bool
     * @param  name string
     * @param  namespaceURI string
     */
    function moveToAttributeNs($name, $namespaceURI) {}

    /**
     * Moves the position of the current instance to the node that contains the current Attribute node. 
     *
     * @return bool
     */
    function moveToElement() {}

    /**
     * Moves the position of the current instance to the first attribute associated with the current node. 
     *
     * @return bool
     */
    function moveToFirstAttribute() {}

    /**
     * Moves the position of the current instance to the next attribute associated with the current node. 
     *
     * @return bool
     */
    function moveToNextAttribute() {}

    /**
     * Moves the position of the current instance to the next node in the stream. 
     *
     * @return bool
     */
    function read() {}

    /**
     * Moves the position of the current instance to the next node in the stream. 
     *
     * @return bool
     * @param  localname string[optional]
     */
    function next($localname = null) {}

    /**
     * Sets the URI that the the XMLReader will parse. 
     *
     * @return bool
     * @param  URI string
     */
    function open($URI) {}

    /**
     * 
     *
     * @return bool
     * @param  property int
     * @param  value boolean
     */
    function setParserProperty($property, $value) {}

    /**
     * Sets the string that the the XMLReader will parse. 
     *
     * @return bool
     * @param  source string
     */
    function setRelaxNGSchemaSource($source) {}

    /**
     * Sets the string that the the XMLReader will parse. 
     *
     * @return boolean
     * @param  source string
     */
    function XML($source) {}

    /**
     * Moves the position of the current instance to the next node in the stream. 
     *
     * @return DOMNode
     */
    function expand() {}

    /**
     * Move array argument's internal pointer to the next element and return it 
     *
     * @return mixed
     * @param  array_arg array
     */
    function next($array_arg) {}

};

class tidyNode {
    /**
     * Constructor. 
     *
     * @return tidyNode
     */
    function tidyNode() {}

    /**
     * Returns true if this node has children 
     *
     * @return bool
     */
    function hasChildren() {}

    /**
     * Returns true if this node has siblings 
     *
     * @return bool
     */
    function hasSiblings() {}

    /**
     * Returns true if this node represents a comment 
     *
     * @return bool
     */
    function isComment() {}

    /**
     * Returns true if this node is part of a HTML document 
     *
     * @return bool
     */
    function isHtml() {}

    /**
     * Returns true if this node is part of a XHTML document 
     *
     * @return boolean
     */
    function isXhtml() {}

    /**
     * Returns true if this node is part of a XML document 
     *
     * @return boolean
     */
    function isXml() {}

    /**
     * Returns true if this node represents text (no markup) 
     *
     * @return bool
     */
    function isText() {}

    /**
     * Returns true if this node is JSTE 
     *
     * @return bool
     */
    function isJste() {}

    /**
     * Returns true if this node is ASP 
     *
     * @return bool
     */
    function isAsp() {}

    /**
     * Returns true if this node is PHP 
     *
     * @return bool
     */
    function isPhp() {}

};

class SplObjectStorage {
    /**
     * Attaches an object to the storage if not yet contained 
     *
     * @return void
     * @param  obj unknown
     */
    function attach($obj) {}

    /**
     * Detaches an object from the storage 
     *
     * @return void
     * @param  obj unknown
     */
    function detach($obj) {}

    /**
     * Determine whethe an object is contained in the storage 
     *
     * @return bool
     * @param  obj unknown
     */
    function contains($obj) {}

    /**
     * Determine number of objects in storage 
     *
     * @return int
     */
    function count() {}

    /**
     * 
     *
     * @return void
     */
    function rewind() {}

    /**
     * 
     *
     * @return bool
     */
    function valid() {}

    /**
     * 
     *
     * @return mixed
     */
    function key() {}

    /**
     * 
     *
     * @return mixed
     */
    function current() {}

    /**
     * 
     *
     * @return void
     */
    function next() {}

};

class DirectoryIterator {
    /**
     * Cronstructs a new dir iterator from a path. 
     *
     * @return DirectoryIterator
     * @param  path string
     */
    function __construct($path) {}

    /**
     * Rewind dir back to the start 
     *
     * @return void
     */
    function rewind() {}

    /**
     * Return current dir entry 
     *
     * @return string
     */
    function key() {}

    /**
     * Return this (needed for Iterator interface) 
     *
     * @return DirectoryIterator
     */
    function current() {}

    /**
     * Move to next entry 
     *
     * @return void
     */
    function next() {}

    /**
     * Check whether dir contains more entries 
     *
     * @return string
     */
    function valid() {}

    /**
     * Return filename of current dir entry 
     *
     * @return string
     */
    function getFilename() {}

    /**
     * Returns true if current entry is '.' or  '..' 
     *
     * @return bool
     */
    function isDot() {}

    /**
     * Returns an iterator for the current entry if it is a directory 
     *
     * @return RecursiveDirectoryIterator
     */
    function getChildren() {}

};

class SplFileInfo {
    /**
     * Return the path 
     *
     * @return string
     */
    function getPath() {}

    /**
     * Return filename only 
     *
     * @return string
     */
    function getFilename() {}

    /**
     * Return path and filename 
     *
     * @return string
     */
    function getPathname() {}

    /**
     * Cronstructs a new SplFileInfo from a path. 
     *
     * @return SplFileInfo
     * @param  file_name string
     */
    function __construct($file_name) {}

    /**
     * Get file permissions 
     *
     * @return int
     */
    function getPerms() {}

    /**
     * Get file inode 
     *
     * @return int
     */
    function getInode() {}

    /**
     * Get file size 
     *
     * @return int
     */
    function getSize() {}

    /**
     * Get file owner 
     *
     * @return int
     */
    function getOwner() {}

    /**
     * Get file group 
     *
     * @return int
     */
    function getGroup() {}

    /**
     * Get last access time of file 
     *
     * @return int
     */
    function getATime() {}

    /**
     * Get last modification time of file 
     *
     * @return int
     */
    function getMTime() {}

    /**
     * Get inode modification time of file 
     *
     * @return int
     */
    function getCTime() {}

    /**
     * Get file type 
     *
     * @return string
     */
    function getType() {}

    /**
     * Returns true if file can be written 
     *
     * @return bool
     */
    function isWritable() {}

    /**
     * Returns true if file can be read 
     *
     * @return bool
     */
    function isReadable() {}

    /**
     * Returns true if file is executable 
     *
     * @return bool
     */
    function isExecutable() {}

    /**
     * Returns true if file is a regular file 
     *
     * @return bool
     */
    function isFile() {}

    /**
     * Returns true if file is directory 
     *
     * @return bool
     */
    function isDir() {}

    /**
     * Returns true if file is symbolic link 
     *
     * @return bool
     */
    function isLink() {}

    /**
     * Open the current file 
     *
     * @return SplFileObject
     * @param  mode string[optional]
     * @param  use_include_path bool[optional]
     * @param  context resource[optional]
     */
    function openFile($mode = 'r', $use_include_path = null, $context = null) {}

    /**
     * Class to use in openFile() 
     *
     * @return void
     * @param  class_name string[optional]
     */
    function setFileClass($class_name = null) {}

    /**
     * Class to use in getFileInfo(), getPathInfo() 
     *
     * @return void
     * @param  class_name string[optional]
     */
    function setInfoClass($class_name = null) {}

    /**
     * Get/copy file info 
     *
     * @return SplFileInfo
     * @param  class_name string[optional]
     */
    function getFileInfo($class_name = null) {}

    /**
     * Get/copy file info 
     *
     * @return SplFileInfo
     * @param  class_name string[optional]
     */
    function getPathInfo($class_name = null) {}

};

class RecursiveDirectoryIterator {
    /**
     * Return getPathname() or getFilename() depending on flags 
     *
     * @return string
     */
    function key() {}

    /**
     * Return getFilename(), getFileInfo() or $this depending on flags 
     *
     * @return string
     */
    function current() {}

    /**
     * Cronstructs a new dir iterator from a path. 
     *
     * @return RecursiveDirectoryIterator
     * @param  path string
     * @param  flags int[optional]
     */
    function __construct($path, $flags = null) {}

    /**
     * Rewind dir back to the start 
     *
     * @return void
     */
    function rewind() {}

    /**
     * Move to next entry 
     *
     * @return void
     */
    function next() {}

    /**
     * Returns whether current entry is a directory and not '.' or '..' 
     *
     * @return bool
     * @param  allow_links bool[optional]
     */
    function hasChildren($allow_links = false) {}

    /**
     * Get sub path 
     *
     * @return void
     */
    function getSubPath() {}

    /**
     * Get sub path and file name 
     *
     * @return void
     */
    function getSubPathname() {}

};

class SplFileObject {
    /**
     * Construct a new temp file object 
     *
     * @return SplFileObject
     * @param  max_memory int[optional]
     */
    function __construct($max_memory = null) {}

    /**
     * Rewind the file and read the first line 
     *
     * @return void
     */
    function rewind() {}

    /**
     * Return the filename 
     *
     * @return string
     */
    function getFilename() {}

    /**
     * Return whether end of file is reached 
     *
     * @return void
     */
    function eof() {}

    /**
     * Return !eof() 
     *
     * @return void
     */
    function valid() {}

    /**
     * Rturn next line from file 
     *
     * @return string
     */
    function fgets() {}

    /**
     * Return current line from file 
     *
     * @return string
     */
    function current() {}

    /**
     * Return line number 
     *
     * @return int
     */
    function key() {}

    /**
     * Read next line 
     *
     * @return void
     */
    function next() {}

    /**
     * Set file handling flags 
     *
     * @return void
     * @param  flags int
     */
    function setFlags($flags) {}

    /**
     * Get file handling flags 
     *
     * @return int
     */
    function getFlags() {}

    /**
     * Set maximum line length 
     *
     * @return void
     * @param  max_len int
     */
    function setMaxLineLen($max_len) {}

    /**
     * Get maximum line length 
     *
     * @return int
     */
    function getMaxLineLen() {}

    /**
     * Return false 
     *
     * @return bool
     */
    function hasChildren() {}

    /**
     * Read NULL 
     *
     * @return bool
     */
    function getChildren() {}

    /**
     * Return current line as csv 
     *
     * @return array
     * @param  delimiter string[optional]
     * @param  enclosure string[optional]
     */
    function fgetcsv($delimiter = null, $enclosure = null) {}

    /**
     * Portable file locking 
     *
     * @return bool
     * @param  operation int
     * @param  wouldblock int[optional]
     */
    function flock($operation, &$wouldblock) {}

    /**
     * Flush the file 
     *
     * @return bool
     */
    function fflush() {}

    /**
     * Return current file position 
     *
     * @return int
     */
    function ftell() {}

    /**
     * Return current file position 
     *
     * @return int
     * @param  pos int
     * @param  whence int[optional]
     */
    function fseek($pos, $whence = SEEK_SET) {}

    /**
     * Get a character form the file 
     *
     * @return int
     */
    function fgetc() {}

    /**
     * Get a line from file pointer and strip HTML tags 
     *
     * @return string
     * @param  allowable_tags string[optional]
     */
    function fgetss($allowable_tags = null) {}

    /**
     * Output all remaining data from a file pointer 
     *
     * @return int
     */
    function fpassthru() {}

    /**
     * Implements a mostly ANSI compatible fscanf() 
     *
     * @return bool
     * @param  format string
     * @vararg ... string
     */
    function fscanf($format) {}

    /**
     * Binary-safe file write 
     *
     * @return mixed
     * @param  str string
     * @param  length int[optional]
     */
    function fwrite($str, $length = null) {}

    /**
     * Stat() on a filehandle 
     *
     * @return bool
     */
    function fstat() {}

    /**
     * Truncate file to 'size' length 
     *
     * @return bool
     * @param  size int
     */
    function ftruncate($size) {}

    /**
     * Seek to specified line 
     *
     * @return void
     * @param  line_pos int
     */
    function seek($line_pos) {}

};

class ArrayObject {
    /**
     * 
     *
     * @return bool
     * @param  index mixed
     */
    function offsetExists($index) {}

    /**
     * 
     *
     * @return bool
     * @param  index mixed
     */
    function offsetGet($index) {}

    /**
     * 
     *
     * @return void
     * @param  index mixed
     * @param  newval mixed
     */
    function offsetSet($index, $newval) {}

    /**
     * 
     *
     * @return void
     * @param  newval mixed
     */
    function append($newval) {}

    /**
     * 
     *
     * @return void
     * @param  index mixed
     */
    function offsetUnset($index) {}

    /**
     * 
     *
     * @return ArrayObject
     * @param  ar array|object[optional]
     * @param  flags int[optional]
     * @param  iterator_class string[optional]
     */
    function __construct($ar = array(), $flags = null, $iterator_class = "ArrayIterator") {}

    /**
     * Set the class used in getIterator. 
     *
     * @return void
     * @param  iterator_class string
     */
    function setIteratorClass($iterator_class) {}

    /**
     * Get the class used in getIterator. 
     *
     * @return string
     */
    function getIteratorClass() {}

    /**
     * Get flags 
     *
     * @return int
     */
    function getFlags() {}

    /**
     * Set flags 
     *
     * @return void
     * @param  flags int
     */
    function setFlags($flags) {}

    /**
     * Replace the referenced array or object with a new one and return the old one (right now copy - to be changed) 
     *
     * @return Array|Object
     * @param  ar Array|Object[optional]
     */
    function exchangeArray($ar = array()) {}

    /**
     * Create a new iterator from a ArrayObject instance 
     *
     * @return ArrayIterator
     */
    function getIterator() {}

    /**
     * 
     *
     * @return int
     */
    function count() {}

};

class ArrayIterator {
    /**
     * Rewind array back to the start 
     *
     * @return void
     */
    function rewind() {}

    /**
     * Seek to position. 
     *
     * @return void
     * @param  position int
     */
    function seek($position) {}

    /**
     * Return current array entry 
     *
     * @return mixed
     */
    function current() {}

    /**
     * Return current array key 
     *
     * @return mixed
     */
    function key() {}

    /**
     * Move to next entry 
     *
     * @return void
     */
    function next() {}

    /**
     * Check whether array contains more entries 
     *
     * @return bool
     */
    function valid() {}

};

class RecursiveArrayIterator {
    /**
     * Check whether current element has children (e.g. is an array) 
     *
     * @return bool
     */
    function hasChildren() {}

    /**
     * Create a sub iterator for the current element (same class as $this) 
     *
     * @return object
     */
    function getChildren() {}

};

class RecursiveIteratorIterator {
    /**
     * Creates a RecursiveIteratorIterator from a RecursiveIterator. 
     *
     * @return RecursiveIteratorIterator
     * @param  it RecursiveIterator|IteratorAggregate
     * @param  mode int[optional]
     * @param  flags int[optional]
     * @throws InvalidArgumentException
     */
    function __construct($it, $mode = RIT_LEAVES_ONLY, $flags = null) {}

    /**
     * Rewind the iterator to the first element of the top level inner iterator. 
     *
     * @return void
     */
    function rewind() {}

    /**
     * Check whether the current position is valid 
     *
     * @return bolean
     */
    function valid() {}

    /**
     * Access the current key 
     *
     * @return mixed
     */
    function key() {}

    /**
     * Access the current element value 
     *
     * @return mixed
     */
    function current() {}

    /**
     * Move forward to the next element 
     *
     * @return void
     */
    function next() {}

    /**
     * Get the current depth of the recursive iteration 
     *
     * @return int
     */
    function getDepth() {}

    /**
     * The current active sub iterator or the iterator at specified level 
     *
     * @return RecursiveIterator
     * @param  level int[optional]
     */
    function getSubIterator($level = null) {}

    /**
     * The current active sub iterator 
     *
     * @return RecursiveIterator
     */
    function getInnerIterator() {}

    /**
     * Called when iteration begins (after first rewind() call) 
     *
     * @return RecursiveIterator
     */
    function beginIteration() {}

    /**
     * Called when iteration ends (when valid() first returns false 
     *
     * @return RecursiveIterator
     */
    function endIteration() {}

    /**
     * Called for each element to test whether it has children 
     *
     * @return bool
     */
    function callHasChildren() {}

    /**
     * Return children of current element 
     *
     * @return RecursiveIterator
     */
    function callGetChildren() {}

    /**
     * Called when recursing one level down 
     *
     * @return void
     */
    function beginChildren() {}

    /**
     * Called when end recursing one level 
     *
     * @return void
     */
    function endChildren() {}

    /**
     * Called when the next element is available 
     *
     * @return void
     */
    function nextElement() {}

    /**
     * Set the maximum allowed depth (or any depth if pmax_depth = -1] 
     *
     * @return void
     * @param  max_depth unknown[optional]
     */
    function setMaxDepth($max_depth = -1) {}

    /**
     * Return the maximum accepted depth or false if any depth is allowed 
     *
     * @return int|false
     */
    function getMaxDepth() {}

};

class FilterIterator {
    /**
     * Create an Iterator from another iterator 
     *
     * @return FilterIterator
     * @param  it Iterator
     */
    function __construct($it) {}

    /**
     * 
     *
     * @return Iterator
     */
    function getInnerIterator() {}

    /**
     * 
     *
     * @return bool
     */
    function valid() {}

    /**
     * 
     *
     * @return mixed
     */
    function key() {}

    /**
     * 
     *
     * @return mixed
     */
    function current() {}

    /**
     * Rewind the iterator 
     *
     * @return void
     */
    function rewind() {}

    /**
     * Move the iterator forward 
     *
     * @return void
     */
    function next() {}

};

class ParentIterator {
    /**
     * 
     *
     * @return void
     */
    function rewind() {}

    /**
     * 
     *
     * @return void
     */
    function next() {}

    /**
     * Create a ParentIterator from a RecursiveIterator 
     *
     * @return ParentIterator
     * @param  it RecursiveIterator
     */
    function __construct($it) {}

    /**
     * Check whether the inner iterator's current element has children 
     *
     * @return bool
     */
    function hasChildren() {}

    /**
     * Return the inner iterator's children contained in a ParentIterator 
     *
     * @return ParentIterator
     */
    function getChildren() {}

};

class RecursiveFilterIterator {
    /**
     * Create a RecursiveFilterIterator from a RecursiveIterator 
     *
     * @return RecursiveFilterIterator
     * @param  it RecursiveIterator
     */
    function __construct($it) {}

    /**
     * Check whether the inner iterator's current element has children 
     *
     * @return bool
     */
    function hasChildren() {}

    /**
     * Return the inner iterator's children contained in a RecursiveFilterIterator 
     *
     * @return RecursiveFilterIterator
     */
    function getChildren() {}

};

class LimitIterator {
    /**
     * Construct a LimitIterator from an Iterator with a given starting offset and optionally a maximum count 
     *
     * @return LimitIterator
     * @param  it Iterator
     * @param  offset int[optional]
     * @param  count int
     */
    function __construct($it, $offset = null, $count) {}

    /**
     * Rewind the iterator to the specified starting offset 
     *
     * @return void
     */
    function rewind() {}

    /**
     * Check whether the current element is valid 
     *
     * @return bool
     */
    function valid() {}

    /**
     * Move the iterator forward 
     *
     * @return void
     */
    function next() {}

    /**
     * Seek to the given position 
     *
     * @return void
     * @param  position int
     */
    function seek($position) {}

    /**
     * Return the current position 
     *
     * @return int
     */
    function getPosition() {}

};

class CachingIterator {
    /**
     * Construct a CachingIterator from an Iterator 
     *
     * @return CachingIterator
     * @param  it Iterator
     * @param  flags unknown[optional]
     */
    function __construct($it, $flags = CIT_CALL_TOSTRING) {}

    /**
     * Rewind the iterator 
     *
     * @return void
     */
    function rewind() {}

    /**
     * Check whether the current element is valid 
     *
     * @return bool
     */
    function valid() {}

    /**
     * Move the iterator forward 
     *
     * @return void
     */
    function next() {}

    /**
     * Check whether the inner iterator has a valid next element 
     *
     * @return bool
     */
    function hasNext() {}

    /**
     * Return the string representation of the current element 
     *
     * @return string
     */
    function __toString() {}

};

class RecursiveCachingIterator {
    /**
     * Create an iterator from a RecursiveIterator 
     *
     * @return RecursiveCachingIterator
     * @param  it RecursiveIterator
     * @param  flags unknown[optional]
     */
    function __construct($it, $flags = CIT_CALL_TOSTRING) {}

    /**
     * Check whether the current element of the inner iterator has children 
     *
     * @return bool
     */
    function hasChildren() {}

    /**
     * Return the inner iterator's children as a RecursiveCachingIterator 
     *
     * @return RecursiveCachingIterator
     */
    function getChildren() {}

};

class IteratorIterator {
    /**
     * Create an iterator from anything that is traversable 
     *
     * @return IteratorIterator
     * @param  it Traversable
     */
    function __construct($it) {}

};

class NoRewindIterator {
    /**
     * Create an iterator from another iterator 
     *
     * @return NoRewindIterator
     * @param  it Iterator
     */
    function __construct($it) {}

    /**
     * Prevent a call to inner iterators rewind() 
     *
     * @return void
     */
    function rewind() {}

    /**
     * Return inner iterators valid() 
     *
     * @return bool
     */
    function valid() {}

    /**
     * Return inner iterators key() 
     *
     * @return mixed
     */
    function key() {}

    /**
     * Return inner iterators current() 
     *
     * @return mixed
     */
    function current() {}

    /**
     * Return inner iterators next() 
     *
     * @return void
     */
    function next() {}

};

class InfiniteIterator {
    /**
     * Create an iterator from another iterator 
     *
     * @return InfiniteIterator
     * @param  it Iterator
     */
    function __construct($it) {}

    /**
     * Prevent a call to inner iterators rewind() (internally the current data will be fetched if valid()) 
     *
     * @return void
     */
    function next() {}

};

class EmptyIterator {
    /**
     * Does nothing  
     *
     * @return void
     */
    function rewind() {}

    /**
     * Return false 
     *
     * @return false
     */
    function valid() {}

    /**
     * Throws exception BadMethodCallException 
     *
     * @return void
     */
    function key() {}

    /**
     * Throws exception BadMethodCallException 
     *
     * @return void
     */
    function current() {}

    /**
     * Does nothing 
     *
     * @return void
     */
    function next() {}

};

class AppendIterator {
    /**
     * Create an AppendIterator 
     *
     * @return AppendIterator
     */
    function __construct() {}

    /**
     * Append an iterator 
     *
     * @return void
     * @param  it Iterator
     */
    function append($it) {}

    /**
     * Rewind to the first iterator and rewind the first iterator, too 
     *
     * @return void
     */
    function rewind() {}

    /**
     * Check if the current state is valid 
     *
     * @return bool
     */
    function valid() {}

    /**
     * Forward to next element 
     *
     * @return void
     */
    function next() {}

};

class SimpleXMLIterator {
    /**
     * Rewind to first element 
     *
     * @return void
     */
    function rewind() {}

    /**
     * Check whether iteration is valid 
     *
     * @return bool
     */
    function valid() {}

    /**
     * Get current element 
     *
     * @return mixed
     */
    function current() {}

    /**
     * Get name of current child element 
     *
     * @return mixed
     */
    function key() {}

    /**
     * Move to next element 
     *
     * @return void
     */
    function next() {}

    /**
     * Check whether element has children (elements) 
     *
     * @return bool
     */
    function hasChildren() {}

    /**
     * Get child element iterator 
     *
     * @return object
     */
    function getChildren() {}

    /**
     * Get number of child elements 
     *
     * @return int
     */
    function count() {}

};

class PDOStatement {
    /**
     * Execute a prepared statement, optionally binding parameters 
     *
     * @return bool
     * @param  bound_input_params array[optional]
     */
    function execute($bound_input_params = null) {}

    /**
     * Fetches the next row and returns it, or false if there are no more rows 
     *
     * @return mixed
     * @param  how int[optional]
     * @param  orientation int[optional]
     * @param  offset int[optional]
     */
    function fetch($how = PDO_FETCH_BOTH, $orientation = null, $offset = null) {}

    /**
     * Fetches the next row and returns it as an object. 
     *
     * @return mixed
     * @param  class_name string
     * @param  ctor_args NULL|array[optional]
     */
    function fetchObject($class_name, $ctor_args = null) {}

    /**
     * Returns a data of the specified column in the result set. 
     *
     * @return string
     * @param  column_number int[optional]
     */
    function fetchColumn($column_number = null) {}

    /**
     * Returns an array of all of the results. 
     *
     * @return array
     * @param  how int[optional]
     * @param  class_name string[optional]
     * @param  ctor_args NULL|array[optional]
     */
    function fetchAll($how = PDO_FETCH_BOTH, $class_name = null, $ctor_args = null) {}

    /**
     * bind an input parameter to the value of a PHP variable.  $paramno is the 1-based position of the placeholder in the SQL statement (but can be the parameter name for drivers that support named placeholders).  It should be called prior to execute(). 
     *
     * @return bool
     * @param  paramno mixed
     * @param  param mixed
     * @param  type int[optional]
     */
    function bindValue($paramno, $param, $type = null) {}

    /**
     * bind a parameter to a PHP variable.  $paramno is the 1-based position of the placeholder in the SQL statement (but can be the parameter name for drivers that support named placeholders).  This isn't supported by all drivers.  It should be called prior to execute(). 
     *
     * @return bool
     * @param  paramno mixed
     * @param  param mixed
     * @param  type int[optional]
     * @param  maxlen int[optional]
     * @param  driverdata mixed[optional]
     */
    function bindParam($paramno, &$param, $type = null, $maxlen = null, $driverdata = null) {}

    /**
     * bind a column to a PHP variable.  On each row fetch $param will contain the value of the corresponding column.  $column is the 1-based offset of the column, or the column name.  For portability, don't call this before execute(). 
     *
     * @return bool
     * @param  column mixed
     * @param  param mixed
     * @param  type int[optional]
     * @param  maxlen int[optional]
     * @param  driverdata mixed[optional]
     */
    function bindColumn($column, &$param, $type = null, $maxlen = null, $driverdata = null) {}

    /**
     * Returns the number of rows in a result set, or the number of rows affected by the last execute().  It is not always meaningful. 
     *
     * @return int
     */
    function rowCount() {}

    /**
     * Fetch the error code associated with the last operation on the statement handle 
     *
     * @return string
     */
    function errorCode() {}

    /**
     * Fetch extended error information associated with the last operation on the statement handle 
     *
     * @return array
     */
    function errorInfo() {}

    /**
     * Set an attribute 
     *
     * @return bool
     * @param  attribute long
     * @param  value mixed
     */
    function setAttribute($attribute, $value) {}

    /**
     * Get an attribute 
     *
     * @return mixed
     * @param  attribute long
     */
    function getAttribute($attribute) {}

    /**
     * Returns the number of columns in the result set 
     *
     * @return int
     */
    function columnCount() {}

    /**
     * Returns meta data for a numbered column 
     *
     * @return mixed
     * @param  column int
     */
    function getColumnMeta($column) {}

    /**
     * Changes the default fetch mode for subsequent fetches (params have different meaning for different fetch modes) 
     *
     * @return bool
     * @param  mode_ int
     */
    function setFetchMode($mode_) {}

    /**
     * Advances to the next rowset in a multi-rowset statement handle. Returns true if it succeded, false otherwise 
     *
     * @return bool
     */
    function nextRowset() {}

    /**
     * Closes the cursor, leaving the statement ready for re-execution. 
     *
     * @return bool
     */
    function closeCursor() {}

    /**
     * A utility for internals hackers to debug parameter internals 
     *
     * @return void
     */
    function debugDumpParams() {}

    /**
     * Prevents use of a PDOStatement instance that has been unserialized 
     *
     * @return int
     */
    function __wakeup() {}

    /**
     * Prevents serialization of a PDOStatement instance 
     *
     * @return int
     */
    function __sleep() {}

};

class SoapParam {
    /**
     * SoapParam constructor 
     *
     * @return SoapParam
     * @param  data mixed
     * @param  name string
     */
    function SoapParam($data, $name) {}

};

class SoapHeader {
    /**
     * SoapHeader constructor 
     *
     * @return SoapHeader
     * @param  namespace string
     * @param  name string
     * @param  data mixed[optional]
     * @param  mustUnderstand bool[optional]
     * @param  actor mixed[optional]
     */
    function SoapHeader($namespace, $name, $data = null, $mustUnderstand = null, $actor = null) {}

};

class SoapFault {
    /**
     * SoapFault constructor 
     *
     * @return SoapFault
     * @param  faultcode string
     * @param  faultstring string
     * @param  faultactor string[optional]
     * @param  detail mixed[optional]
     * @param  faultname string[optional]
     * @param  headerfault mixed[optional]
     */
    function SoapFault($faultcode, $faultstring, $faultactor = null, $detail = null, $faultname = null, $headerfault = null) {}

    /**
     * 
     *
     * @return object
     */
    function __toString() {}

};

class SoapVar {
    /**
     * SoapVar constructor 
     *
     * @return SoapVar
     * @param  data mixed
     * @param  encoding int
     * @param  type_name string[optional]
     * @param  type_namespace string[optional]
     * @param  node_name string[optional]
     * @param  node_namespace string[optional]
     */
    function SoapVar($data, $encoding, $type_name = null, $type_namespace = null, $node_name = null, $node_namespace = null) {}

};

class SoapServer {
    /**
     * SoapServer constructor 
     *
     * @return SoapServer
     * @param  wsdl mixed
     * @param  options array[optional]
     */
    function SoapServer($wsdl, $options = null) {}

    /**
     * Sets persistence mode of SoapServer 
     *
     * @return void
     * @param  mode int
     */
    function setPersistence($mode) {}

    /**
     * Sets class which will handle SOAP requests 
     *
     * @return void
     * @param  class_name string
     * @param  args mixed[optional]
     */
    function setClass($class_name, $args = null) {}

    /**
     * Returns list of defined functions 
     *
     * @return array
     */
    function getFunctions() {}

    /**
     * Adds one or several functions those will handle SOAP requests 
     *
     * @return void
     * @param  functions mixed
     */
    function addFunction($functions) {}

    /**
     * Handles a SOAP request 
     *
     * @return void
     * @param  soap_request string[optional]
     */
    function handle($soap_request = null) {}

    /**
     * Issue SoapFault indicating an error 
     *
     * @return void
     * @param  code staring
     * @param  string string
     * @param  actor string[optional]
     * @param  details mixed[optional]
     * @param  name string[optional]
     */
    function fault($code, $string, $actor = null, $details = null, $name = null) {}

};

class SoapClient {
    /**
     * SoapClient constructor 
     *
     * @return SoapClient
     * @param  wsdl mixed
     * @param  options array[optional]
     */
    function SoapClient($wsdl, $options = null) {}

    /**
     * Calls a SOAP function 
     *
     * @return mixed
     * @param  function_name string
     * @param  arguments array[optional]
     * @param  options array[optional]
     * @param  input_headers array[optional]
     * @param  output_headers array[optional]
     */
    function __call($function_name, $arguments = null, $options = null, $input_headers = null, $output_headers = null) {}

    /**
     * Returns list of SOAP functions 
     *
     * @return array
     */
    function __getFunctions() {}

    /**
     * Returns list of SOAP types 
     *
     * @return array
     */
    function __getTypes() {}

    /**
     * Returns last SOAP request 
     *
     * @return string
     */
    function __getLastRequest() {}

    /**
     * Returns last SOAP response 
     *
     * @return string
     */
    function __getLastResponse() {}

    /**
     * Returns last SOAP request headers 
     *
     * @return string
     */
    function __getLastRequestHeaders() {}

    /**
     * Returns last SOAP response headers 
     *
     * @return string
     */
    function __getLastResponseHeaders() {}

    /**
     * SoapClient::__doRequest() 
     *
     * @return string
     */
    function __doRequest() {}

    /**
     * 
     *
     * @return void
     * @param  name string
     * @param  value strung[optional]
     */
    function __setCookie($name, $value = null) {}

    /**
     * 
     *
     * @return void
     * @param  SoapHeaders array
     */
    function __setSoapHeaders($SoapHeaders) {}

    /**
     * 
     *
     * @return string
     * @param  new_location string[optional]
     */
    function __setLocation($new_location = null) {}

};

class COMPersistHelper {
    /**
     * Determines the filename into which an object will be saved, or false if none is set, via IPersistFile::GetCurFile 
     *
     * @return string
     */
    function GetCurFile() {}

    /**
     * Persist object data to file, via IPersistFile::Save 
     *
     * @return bool
     * @param  filename string
     * @param  remember bool[optional]
     */
    function SaveToFile($filename, $remember = null) {}

    /**
     * Load object data from file, via IPersistFile::Load 
     *
     * @return bool
     * @param  filename string
     * @param  flags int[optional]
     */
    function LoadFromFile($filename, $flags = null) {}

    /**
     * Gets maximum stream size required to store the object data, via IPersistStream::GetSizeMax (or IPersistStreamInit::GetSizeMax) 
     *
     * @return int
     */
    function GetMaxStreamSize() {}

    /**
     * Initializes the object to a default state, via IPersistStreamInit::InitNew 
     *
     * @return int
     */
    function InitNew() {}

    /**
     * Initializes an object from the stream where it was previously saved, via IPersistStream::Load or OleLoadFromStream 
     *
     * @return mixed
     * @param  stream resource
     */
    function LoadFromStream($stream) {}

    /**
     * Saves the object to a stream, via IPersistStream::Save 
     *
     * @return int
     * @param  stream resource
     */
    function SaveToStream($stream) {}

    /**
     * Creates a persistence helper object, usually associated with a com_object 
     *
     * @return COMPersistHelper
     * @param  com_object object[optional]
     */
    function __construct($com_object = null) {}

};

class swfaction {
    /**
     * Creates a new SWFAction object, compiling the given script 
     *
     * @return swfaction
     * @param  string unknown
     */
    function __construct($string) {}

};

class swfbitmap {
    /**
     * Creates a new SWFBitmap object from jpg (with optional mask) or dbl file 
     *
     * @return swfbitmap
     * @param  file mixed
     * @param  maskfile mixed[optional]
     */
    function __construct($file, $maskfile = null) {}

    /**
     * Returns the width of this bitmap 
     *
     * @return float
     */
    function getWidth() {}

    /**
     * Returns the height of this bitmap 
     *
     * @return float
     */
    function getHeight() {}

};

class swfbutton {
    /**
     * Creates a new SWFButton object 
     *
     * @return swfbutton
     */
    function __construct() {}

    /**
     * Sets the character for this button's hit test state 
     *
     * @return void
     * @param  SWFCharacter object
     */
    function setHit($SWFCharacter) {}

    /**
     * Sets the character for this button's over state 
     *
     * @return void
     * @param  SWFCharacter object
     */
    function setOver($SWFCharacter) {}

    /**
     * Sets the character for this button's up state 
     *
     * @return void
     * @param  SWFCharacter object
     */
    function setUp($SWFCharacter) {}

    /**
     * Sets the character for this button's down state 
     *
     * @return void
     * @param  SWFCharacter object
     */
    function setDown($SWFCharacter) {}

    /**
     * Sets the character to display for the condition described in flags 
     *
     * @return void
     * @param  SWFCharacter object
     * @param  flags int
     */
    function addShape($SWFCharacter, $flags) {}

    /**
     * enable track as menu button behaviour 
     *
     * @return void
     * @param  flag int
     */
    function setMenu($flag) {}

    /**
     * Sets the action to perform when button is pressed 
     *
     * @return void
     * @param  SWFAction object
     */
    function setAction($SWFAction) {}

    /**
     * 
     *
     * @return SWFSoundInstance
     * @param  sound SWFSound
     * @param  flags int
     */
    function addASound($sound, $flags) {}

    /**
     * Sets the action to perform when conditions described in flags is met 
     *
     * @return void
     * @param  SWFAction object
     * @param  flags int
     */
    function addAction($SWFAction, $flags) {}

};

class swfdisplayitem {
    /**
     * Moves this SWFDisplayItem to movie coordinates (x, y) 
     *
     * @return void
     * @param  x int
     * @param  y int
     */
    function moveTo($x, $y) {}

    /**
     * Displaces this SWFDisplayItem by (dx, dy) in movie coordinates 
     *
     * @return void
     * @param  dx float
     * @param  dy float
     */
    function move($dx, $dy) {}

    /**
     * Scales this SWFDisplayItem by xScale in the x direction, yScale in the y, or both to xScale if only one arg 
     *
     * @return void
     * @param  xScale float
     * @param  yScale float[optional]
     */
    function scaleTo($xScale, $yScale = null) {}

    /**
     * Multiplies this SWFDisplayItem's current x scale by xScale, its y scale by yScale 
     *
     * @return void
     * @param  xScale float
     * @param  yScale float
     */
    function scale($xScale, $yScale) {}

    /**
     * Rotates this SWFDisplayItem the given (clockwise) degrees from its original orientation 
     *
     * @return void
     * @param  degrees float
     */
    function rotateTo($degrees) {}

    /**
     * Rotates this SWFDisplayItem the given (clockwise) degrees from its current orientation 
     *
     * @return void
     * @param  degrees float
     */
    function rotate($degrees) {}

    /**
     * Sets this SWFDisplayItem's x skew value to xSkew 
     *
     * @return void
     * @param  xSkew float
     */
    function skewXTo($xSkew) {}

    /**
     * Adds xSkew to this SWFDisplayItem's x skew value 
     *
     * @return void
     * @param  xSkew float
     */
    function skewX($xSkew) {}

    /**
     * Sets this SWFDisplayItem's y skew value to ySkew 
     *
     * @return void
     * @param  ySkew float
     */
    function skewYTo($ySkew) {}

    /**
     * Adds ySkew to this SWFDisplayItem's y skew value 
     *
     * @return void
     * @param  ySkew float
     */
    function skewY($ySkew) {}

    /**
     * Sets the item's transform matrix 
     *
     * @return void
     * @param  a float
     * @param  b float
     * @param  c float
     * @param  d float
     * @param  x float
     * @param  y float
     */
    function setMatrix($a, $b, $c, $d, $x, $y) {}

    /**
     * Sets this SWFDisplayItem's z-depth to depth.  Items with higher depth values are drawn on top of those with lower values 
     *
     * @return void
     * @param  depth int
     */
    function setDepth($depth) {}

    /**
     * Sets this SWFDisplayItem's ratio to ratio.  Obviously only does anything if displayitem was created from an SWFMorph 
     *
     * @return void
     * @param  ratio float
     */
    function setRatio($ratio) {}

    /**
     * Sets the add color part of this SWFDisplayItem's CXform to (r, g, b [, a]), a defaults to 0 
     *
     * @return void
     * @param  r int
     * @param  g int
     * @param  b int
     * @param  a int[optional]
     */
    function addColor($r, $g, $b, $a = null) {}

    /**
     * Sets the multiply color part of this SWFDisplayItem's CXform to (r, g, b [, a]), a defaults to 1.0 
     *
     * @return void
     * @param  r float
     * @param  g float
     * @param  b float
     * @param  a float[optional]
     */
    function multColor($r, $g, $b, $a = null) {}

    /**
     * Sets this SWFDisplayItem's name to name 
     *
     * @return void
     * @param  name string
     */
    function setName($name) {}

    /**
     * Adds this SWFAction to the given SWFSprite instance 
     *
     * @return void
     * @param  SWFAction object
     * @param  flags int
     */
    function addAction($SWFAction, $flags) {}

    /**
     * defines a MASK layer at level 
     *
     * @return void
     * @param  level int
     */
    function setMaskLevel($level) {}

    /**
     * another way of defining a MASK layer 
     *
     * @return void
     */
    function endMask() {}

};

class swffill {
    /**
     * Creates a new SWFFill object 
     *
     * @return swffill
     */
    function __construct() {}

    /**
     * Moves this SWFFill to shape coordinates (x,y) 
     *
     * @return void
     * @param  x float
     * @param  y float
     */
    function moveTo($x, $y) {}

    /**
     * Scales this SWFFill by xScale in the x direction, yScale in the y, or both to xScale if only one arg 
     *
     * @return void
     * @param  xScale float
     * @param  yScale float[optional]
     */
    function scaleTo($xScale, $yScale = null) {}

    /**
     * Rotates this SWFFill the given (clockwise) degrees from its original orientation 
     *
     * @return void
     * @param  degrees float
     */
    function rotateTo($degrees) {}

    /**
     * Sets this SWFFill's x skew value to xSkew 
     *
     * @return void
     * @param  xSkew float
     */
    function skewXTo($xSkew) {}

    /**
     * Sets this SWFFill's y skew value to ySkew 
     *
     * @return void
     * @param  ySkew float
     */
    function skewYTo($ySkew) {}

};

class swffontcha {
    /**
     * adds characters to a font for exporting font 
     *
     * @return void
     * @param  string unknown
     */
    function raddChars($string) {}

};

class swffontchar {
    /**
     * adds characters to a font for exporting font 
     *
     * @return void
     * @param  string unknown
     */
    function addChars($string) {}

};

class swffont {
    /**
     * Creates a new SWFFont object from given file 
     *
     * @return swffont
     * @param  filename string
     */
    function __construct($filename) {}

    /**
     * Calculates the width of the given string in this font at full height 
     *
     * @return float
     * @param  str string
     */
    function getWidth($str) {}

    /**
     * Calculates the width of the given string in this font at full height 
     *
     * @return int
     * @param  string unknown
     */
    function getUTF8Width($string) {}

    /**
     * Calculates the width of the given string in this font at full height 
     *
     * @return int
     * @param  string unknown
     */
    function getWideWidth($string) {}

    /**
     * Returns the ascent of the font, or 0 if not available 
     *
     * @return float
     */
    function getAscent() {}

    /**
     * Returns the descent of the font, or 0 if not available 
     *
     * @return float
     */
    function getDescent() {}

    /**
     * Returns the leading of the font, or 0 if not available 
     *
     * @return float
     */
    function getLeading() {}

    /**
     * adds characters to a font required within textfields 
     *
     * @return void
     * @param  string unknown
     */
    function addChars($string) {}

    /**
     * Returns the glyph shape of a char as a text string 
     *
     * @return string
     * @param  code unknown
     */
    function getShape($code) {}

};

class swfgradient {
    /**
     * Creates a new SWFGradient object 
     *
     * @return swfgradient
     */
    function __construct() {}

    /**
     * Adds given entry to the gradient 
     *
     * @return void
     * @param  ratio float
     * @param  r int
     * @param  g int
     * @param  b int
     * @param  a int[optional]
     */
    function addEntry($ratio, $r, $g, $b, $a = null) {}

};

class swfmorph {
    /**
     * Creates a new SWFMorph object 
     *
     * @return swfmorph
     */
    function __construct() {}

    /**
     * Return's this SWFMorph's start shape object 
     *
     * @return object
     */
    function getShape1() {}

    /**
     * Return's this SWFMorph's start shape object 
     *
     * @return object
     */
    function getShape2() {}

};

class swfsound {
    /**
     * Creates a new SWFSound object from given file 
     *
     * @return swfsound
     * @param  filename string
     * @param  flags int
     */
    function __construct($filename, $flags) {}

};

class swfmovie {
    /**
     * Creates swfmovie object according to the passed version 
     *
     * @return swfmovie
     * @param  version int
     */
    function __construct($version) {}

    /**
     * 
     *
     * @return void
     */
    function nextframe() {}

    /**
     * 
     *
     * @return void
     * @param  SWFBlock object
     */
    function labelframe($SWFBlock) {}

    /**
     * 
     *
     * @return void
     * @param  SWFBlock object
     */
    function add($SWFBlock) {}

    /**
     * 
     *
     * @return int
     * @param  compression int[optional]
     */
    function output($compression = null) {}

    /**
     * 
     *
     * @return int
     * @param  x stream
     * @param  compression int[optional]
     */
    function saveToFile($x, $compression = null) {}

    /**
     * Saves the movie. 'where' can be stream and the movie will be saved there otherwise it is treated as string and written in file with that name 
     *
     * @return int
     * @param  where mixed
     * @param  compression int[optional]
     */
    function save($where, $compression = null) {}

    /**
     * Sets background color (r,g,b) 
     *
     * @return void
     * @param  r int
     * @param  g int
     * @param  b int
     */
    function setBackground($r, $g, $b) {}

    /**
     * Sets movie rate 
     *
     * @return void
     * @param  rate float
     */
    function setRate($rate) {}

    /**
     * Sets movie dimension 
     *
     * @return void
     * @param  x float
     * @param  y float
     */
    function setDimension($x, $y) {}

    /**
     * Sets number of frames 
     *
     * @return void
     * @param  frames int
     */
    function setFrames($frames) {}

    /**
     * Sets sound stream of the SWF movie. The parameter can be stream or string. 
     *
     * @return void
     * @param  file mixed
     */
    function streamMP3($file) {}

};

class swfshape {
    /**
     * Creates a new SWFShape object 
     *
     * @return swfshape
     */
    function __construct() {}

    /**
     * Sets the current line style for this SWFShape 
     *
     * @return void
     * @param  width int
     * @param  r int
     * @param  g int
     * @param  b int
     * @param  a int[optional]
     */
    function setline($width, $r, $g, $b, $a = null) {}

    /**
     * Returns a fill object, for use with swfshape_setleftfill and swfshape_setrightfill. If 1 or 2 parameter(s) is (are) passed first should be object (from gradient class) and the second int (flags). Gradient fill is performed. If 3 or 4 parameters are passed : r, g, b [, a]. Solid fill is performed. 
     *
     * @return object
     * @param  arg1 mixed
     * @param  arg2 int
     * @param  b int[optional]
     * @param  a int[optional]
     */
    function addfill($arg1, $arg2, $b = null, $a = null) {}

    /**
     * Sets the right side fill style to fill in case only one parameter is passed. When 3 or 4 parameters are passed they are treated as : int r, int g, int b, int a . Solid fill is performed in this case before setting right side fill type. 
     *
     * @return void
     * @param  arg1 int
     * @param  g int[optional]
     * @param  b int
     * @param  a int[optional]
     */
    function setleftfill($arg1, $g = null, $b, $a = null) {}

    /**
     * Moves the pen to shape coordinates (x, y) 
     *
     * @return void
     * @param  x float
     * @param  y float
     */
    function movepento($x, $y) {}

    /**
     * Moves the pen from its current location by vector (x, y) 
     *
     * @return void
     * @param  x float
     * @param  y float
     */
    function movepen($x, $y) {}

    /**
     * Draws a line from the current pen position to shape coordinates (x, y) in the current line style 
     *
     * @return void
     * @param  x float
     * @param  y float
     */
    function drawlineto($x, $y) {}

    /**
     * Draws a line from the current pen position (x, y) to the point (x+dx, y+dy) in the current line style 
     *
     * @return void
     * @param  dx float
     * @param  dy float
     */
    function drawline($dx, $dy) {}

    /**
     * Draws a curve from the current pen position (x,y) to the point (bx, by) in the current line style, using point (ax, ay) as a control point. Or draws a cubic bezier to point (dx, dy) with control points (ax, ay) and (bx, by) 
     *
     * @return int
     * @param  ax float
     * @param  ay float
     * @param  bx float
     * @param  by float
     * @param  dx float[optional]
     * @param  dy float
     */
    function drawcurveto($ax, $ay, $bx, $by, $dx = null, $dy) {}

    /**
     * Draws a curve from the current pen position (x, y) to the point (x+bdx, y+bdy) in the current line style, using point (x+adx, y+ady) as a control point or draws a cubic bezier to point (x+cdx, x+cdy) with control points (x+adx, y+ady) and (x+bdx, y+bdy) 
     *
     * @return int
     * @param  adx float
     * @param  ady float
     * @param  bdx float
     * @param  bdy float
     * @param  cdx float[optional]
     * @param  cdy float
     */
    function drawcurve($adx, $ady, $bdx, $bdy, $cdx = null, $cdy) {}

    /**
     * Draws the first character in the given string into the shape using the glyph definition from the given font 
     *
     * @return void
     * @param  font SWFFont
     * @param  character string
     * @param  size int[optional]
     */
    function drawglyph($font, $character, $size = null) {}

    /**
     * Draws a circle of radius r centered at the current location, in a counter-clockwise fashion 
     *
     * @return void
     * @param  r float
     */
    function drawcircle($r) {}

    /**
     * Draws an arc of radius r centered at the current location, from angle startAngle to angle endAngle measured clockwise from 12 o'clock 
     *
     * @return void
     * @param  r float
     * @param  startAngle float
     * @param  endAngle float
     */
    function drawarc($r, $startAngle, $endAngle) {}

    /**
     * Draws a cubic bezier curve using the current position and the three given points as control points 
     *
     * @return void
     * @param  bx float
     * @param  by float
     * @param  cx float
     * @param  cy float
     * @param  dx float
     * @param  dy float
     */
    function drawcubic($bx, $by, $cx, $cy, $dx, $dy) {}

};

class swfsprite {
    /**
     * Creates a new SWFSprite object 
     *
     * @return swfsprite
     */
    function __construct() {}

    /**
     * Adds the character to the sprite, returns a displayitem object 
     *
     * @return void
     * @param  SWFCharacter object
     */
    function add($SWFCharacter) {}

    /**
     * Remove the named character from the sprite's display list 
     *
     * @return void
     * @param  SWFDisplayItem object
     */
    function remove($SWFDisplayItem) {}

    /**
     * Moves the sprite to the next frame 
     *
     * @return void
     */
    function nextFrame() {}

    /**
     * Labels frame 
     *
     * @return void
     * @param  label string
     */
    function labelFrame($label) {}

    /**
     * Sets the number of frames in this SWFSprite 
     *
     * @return void
     * @param  frames int
     */
    function setFrames($frames) {}

};

class swftext {
    /**
     * Creates new SWFText object 
     *
     * @return swftext
     */
    function __construct() {}

    /**
     * Sets this SWFText object's current font to given font 
     *
     * @return void
     * @param  font object
     */
    function setFont($font) {}

    /**
     * Sets this SWFText object's current height to given height 
     *
     * @return void
     * @param  height float
     */
    function setHeight($height) {}

    /**
     * Sets this SWFText object's current letterspacing to given spacing 
     *
     * @return void
     * @param  spacing float
     */
    function setSpacing($spacing) {}

    /**
     * Sets this SWFText object's current color to the given color 
     *
     * @return void
     * @param  r int
     * @param  g int
     * @param  b int
     * @param  a int[optional]
     */
    function setColor($r, $g, $b, $a = null) {}

    /**
     * Moves this SWFText object's current pen position to (x, y) in text coordinates 
     *
     * @return void
     * @param  x float
     * @param  y float
     */
    function moveTo($x, $y) {}

    /**
     * Writes the given text into this SWFText object at the current pen position, using the current font, height, spacing, and color 
     *
     * @return void
     * @param  text string
     */
    function addString($text) {}

    /**
     * 
     *
     * @return void
     * @param  text string
     */
    function addUTF8String($text) {}

    /**
     * 
     *
     * @return void
     * @param  text string
     */
    function addWideString($text) {}

    /**
     * Calculates the width of the given string in this text objects current font and size 
     *
     * @return float
     * @param  str string
     */
    function getWidth($str) {}

    /**
     * calculates the width of the given string in this text objects current font and size 
     *
     * @return double
     * @param  string unknown
     */
    function getUTF8Width($string) {}

    /**
     * calculates the width of the given string in this text objects current font and size 
     *
     * @return double
     * @param  string unknown
     */
    function getWideWidth($string) {}

    /**
     * Returns the ascent of the current font at its current size, or 0 if not available 
     *
     * @return float
     */
    function getAscent() {}

    /**
     * Returns the descent of the current font at its current size, or 0 if not available 
     *
     * @return float
     */
    function getDescent() {}

    /**
     * Returns the leading of the current font at its current size, or 0 if not available 
     *
     * @return float
     */
    function getLeading() {}

};

class swftextfield {
    /**
     * Creates a new SWFTextField object 
     *
     * @return swftextfield
     * @param  flags int[optional]
     */
    function __construct($flags = null) {}

    /**
     * Sets the font for this textfield 
     *
     * @return void
     * @param  font object
     */
    function setFont($font) {}

    /**
     * Sets the width and height of this textfield 
     *
     * @return void
     * @param  width float
     * @param  height float
     */
    function setBounds($width, $height) {}

    /**
     * Sets the alignment of this textfield 
     *
     * @return void
     * @param  alignment int
     */
    function align($alignment) {}

    /**
     * Sets the font height of this textfield 
     *
     * @return void
     * @param  height float
     */
    function setHeight($height) {}

    /**
     * Sets the left margin of this textfield 
     *
     * @return void
     * @param  margin float
     */
    function setLeftMargin($margin) {}

    /**
     * Sets the right margin of this textfield 
     *
     * @return void
     * @param  margin float
     */
    function setRightMargin($margin) {}

    /**
     * Sets both margins of this textfield 
     *
     * @return void
     * @param  left float
     * @param  right float
     */
    function setMargins($left, $right) {}

    /**
     * Sets the indentation of the first line of this textfield 
     *
     * @return void
     * @param  indentation float
     */
    function setIndentation($indentation) {}

    /**
     * Sets the line spacing of this textfield 
     *
     * @return void
     * @param  space float
     */
    function setLineSpacing($space) {}

    /**
     * Sets the color of this textfield 
     *
     * @return void
     * @param  r int
     * @param  g int
     * @param  b int
     * @param  a int[optional]
     */
    function setColor($r, $g, $b, $a = null) {}

    /**
     * Sets the variable name of this textfield 
     *
     * @return void
     * @param  var_name string
     */
    function setName($var_name) {}

    /**
     * Adds the given string to this textfield 
     *
     * @return void
     * @param  str string
     */
    function addString($str) {}

    /**
     * Sets the padding of this textfield 
     *
     * @return void
     * @param  padding float
     */
    function setPadding($padding) {}

    /**
     * adds characters to a font that will be available within a textfield 
     *
     * @return void
     * @param  string unknown
     */
    function addChars($string) {}

};

class SimpleXMLElement {
    /**
     * Return a well-formed XML string based on SimpleXML element 
     *
     * @return mixed
     * @param  filename string[optional]
     */
    function asXML($filename = null) {}

    /**
     * Return all namespaces in use 
     *
     * @return array
     * @param  recursve bool[optional]
     */
    function getNamespaces($recursve = null) {}

    /**
     * Return all namespaces registered with document 
     *
     * @return array
     * @param  recursive bool[optional]
     */
    function getDocNamespaces($recursive = null) {}

    /**
     * Finds children of given node 
     *
     * @return SimpleXMLElement
     * @param  ns string[optional]
     */
    function children($ns = null) {}

    /**
     * Finds children of given node 
     *
     * @return string
     */
    function getName() {}

    /**
     * Identifies an element's attributes 
     *
     * @return SimpleXMLElement
     * @param  ns string[optional]
     */
    function attributes($ns = null) {}

    /**
     * Add Element with optional namespace information 
     *
     * @return SimpleXMLElement
     * @param  qName string
     * @param  value string[optional]
     * @param  ns string[optional]
     */
    function addChild($qName, $value = null, $ns = null) {}

    /**
     * Add Attribute with optional namespace information 
     *
     * @return void
     * @param  qName string
     * @param  value string
     * @param  ns string[optional]
     */
    function addAttribute($qName, $value, $ns = null) {}

    /**
     * SimpleXMLElement constructor 
     *
     * @return SimpleXMLElement
     * @param  data string
     * @param  options int[optional]
     * @param  data_is_url bool[optional]
     */
    function __construct($data, $options = null, $data_is_url = null) {}

};

class OCI_Lob {
    /**
     * Loads a large object 
     *
     * @return string
     */
    function load() {}

    /**
     * Tells LOB pointer position 
     *
     * @return int
     */
    function tell() {}

    /**
     * Truncates a LOB 
     *
     * @return bool
     * @param  length int[optional]
     */
    function truncate($length = null) {}

    /**
     * Erases a specified portion of the internal LOB, starting at a specified offset 
     *
     * @return int
     * @param  offset int[optional]
     * @param  length int[optional]
     */
    function erase($offset = null, $length = null) {}

    /**
     * Flushes the LOB buffer 
     *
     * @return bool
     * @param  flag int[optional]
     */
    function flush($flag = null) {}

    /**
     * Enables/disables buffering for a LOB 
     *
     * @return bool
     * @param  flag boolean
     */
    function setbuffering($flag) {}

    /**
     * Returns current state of buffering for a LOB 
     *
     * @return bool
     */
    function getbuffering() {}

    /**
     * Rewind pointer of a LOB 
     *
     * @return bool
     */
    function rewind() {}

    /**
     * Reads particular part of a large object 
     *
     * @return string
     * @param  length int
     */
    function read($length) {}

    /**
     * Checks if EOF is reached 
     *
     * @return bool
     */
    function eof() {}

    /**
     * Moves the pointer of a LOB 
     *
     * @return bool
     * @param  offset int
     * @param  whence int[optional]
     */
    function seek($offset, $whence = null) {}

    /**
     * Writes data to current position of a LOB 
     *
     * @return int
     * @param  string string
     * @param  length int[optional]
     */
    function write($string, $length = null) {}

    /**
     * Appends data from a LOB to another LOB 
     *
     * @return bool
     * @param  lob object
     */
    function append($lob) {}

    /**
     * Returns size of a large object 
     *
     * @return int
     */
    function size() {}

    /**
     * Writes a large object into a file 
     *
     * @return bool
     * @param  filename string[optional]
     * @param  start int[optional]
     * @param  length int[optional]
     */
    function writetofile($filename = null, $start = null, $length = null) {}

    /**
     * Writes a large object into a file 
     *
     * @return bool
     * @param  filename string[optional]
     * @param  start int[optional]
     * @param  length int[optional]
     */
    function export($filename = null, $start = null, $length = null) {}

    /**
     * Loads file into a LOB 
     *
     * @return bool
     * @param  filename string
     */
    function import($filename) {}

    /**
     * Writes temporary blob 
     *
     * @return bool
     * @param  var string
     * @param  lob_type int[optional]
     */
    function writetemporary($var, $lob_type = null) {}

    /**
     * Closes lob descriptor 
     *
     * @return bool
     */
    function close() {}

    /**
     * Saves a large object 
     *
     * @return bool
     * @param  data string
     * @param  offset int[optional]
     */
    function save($data, $offset = null) {}

    /**
     * Loads file into a LOB 
     *
     * @return bool
     * @param  filename string
     */
    function savefile($filename) {}

    /**
     * Deletes large object description 
     *
     * @return bool
     */
    function free() {}

};

class OCI_Collection {
    /**
     * Append an object to the collection 
     *
     * @return bool
     * @param  value string
     */
    function append($value) {}

    /**
     * Retrieve the value at collection index ndx 
     *
     * @return string
     * @param  ndx int
     */
    function getelem($ndx) {}

    /**
     * Assign element val to collection at index ndx 
     *
     * @return bool
     * @param  index int
     * @param  val string
     */
    function assignelem($index, $val) {}

    /**
     * Assign a collection from another existing collection 
     *
     * @return bool
     * @param  from object
     */
    function assign($from) {}

    /**
     * Return the size of a collection 
     *
     * @return int
     */
    function size() {}

    /**
     * Return the max value of a collection. For a varray this is the maximum length of the array 
     *
     * @return int
     */
    function max() {}

    /**
     * Trim num elements from the end of a collection 
     *
     * @return bool
     * @param  num int
     */
    function trim($num) {}

    /**
     * Deletes collection object
     *
     * @return bool
     */
    function free() {}

};

class XMLWriter {
    /**
     * Create new xmlwriter using source uri for output 
     *
     * @return resource
     * @param  xmlwriter resource
     * @param  source string
     */
    function openUri($xmlwriter, $source) {}

    /**
     * Create new xmlwriter using memory for string output 
     *
     * @return resource
     */
    function openMemory() {}

    /**
     * Toggle indentation on/off - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  indent bool
     */
    function setIndent($xmlwriter, $indent) {}

    /**
     * Set string used for indenting - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  indentString string
     */
    function setIndentString($xmlwriter, $indentString) {}

    /**
     * Create start comment - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     */
    function startComment($xmlwriter) {}

    /**
     * Create end comment - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     */
    function endComment($xmlwriter) {}

    /**
     * Create start attribute - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  name string
     */
    function startAttribute($xmlwriter, $name) {}

    /**
     * End attribute - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     */
    function endAttribute($xmlwriter) {}

    /**
     * Write full attribute - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  name string
     * @param  content string
     */
    function writeAttribute($xmlwriter, $name, $content) {}

    /**
     * Create start namespaced attribute - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  prefix string
     * @param  name string
     * @param  uri string
     */
    function startAttributeNs($xmlwriter, $prefix, $name, $uri) {}

    /**
     * Write full namespaced attribute - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  prefix string
     * @param  name string
     * @param  uri string
     * @param  content string
     */
    function writeAttributeNs($xmlwriter, $prefix, $name, $uri, $content) {}

    /**
     * Create start element tag - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  name string
     */
    function startElement($xmlwriter, $name) {}

    /**
     * End current element - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     */
    function endElement($xmlwriter) {}

    /**
     * Create start namespaced element tag - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  prefix string
     * @param  name string
     * @param  uri string
     */
    function startElementNs($xmlwriter, $prefix, $name, $uri) {}

    /**
     * Write full element tag - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  name string
     * @param  content string
     */
    function writeElement($xmlwriter, $name, $content) {}

    /**
     * Write full namesapced element tag - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  prefix string
     * @param  name string
     * @param  uri string
     * @param  content string
     */
    function writeElementNs($xmlwriter, $prefix, $name, $uri, $content) {}

    /**
     * Create start PI tag - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  target string
     */
    function startPi($xmlwriter, $target) {}

    /**
     * End current PI - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     */
    function endPi($xmlwriter) {}

    /**
     * Write full PI tag - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  target string
     * @param  content string
     */
    function writePi($xmlwriter, $target, $content) {}

    /**
     * Create start CDATA tag - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     */
    function startCdata($xmlwriter) {}

    /**
     * End current CDATA - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     */
    function endCdata($xmlwriter) {}

    /**
     * Write full CDATA tag - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  content string
     */
    function writeCdata($xmlwriter, $content) {}

    /**
     * Write text - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  content string
     */
    function text($xmlwriter, $content) {}

    /**
     * Create document tag - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  version string
     * @param  encoding string
     * @param  standalone string
     */
    function startDocument($xmlwriter, $version, $encoding, $standalone) {}

    /**
     * End current document - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     */
    function endDocument($xmlwriter) {}

    /**
     * Write full comment tag - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  content string
     */
    function writeComment($xmlwriter, $content) {}

    /**
     * Create start DTD tag - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  name string
     * @param  pubid string
     * @param  sysid string
     */
    function startDtd($xmlwriter, $name, $pubid, $sysid) {}

    /**
     * End current DTD - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     */
    function endDtd($xmlwriter) {}

    /**
     * Write full DTD tag - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  name string
     * @param  pubid string
     * @param  sysid string
     * @param  subset string
     */
    function writeDtd($xmlwriter, $name, $pubid, $sysid, $subset) {}

    /**
     * Create start DTD element - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  name string
     */
    function startDtdElement($xmlwriter, $name) {}

    /**
     * End current DTD element - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     */
    function endDtdElement($xmlwriter) {}

    /**
     * Write full DTD element tag - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  name string
     * @param  content string
     */
    function writeDtdElement($xmlwriter, $name, $content) {}

    /**
     * Create start DTD AttList - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  name string
     */
    function startDtdAttlist($xmlwriter, $name) {}

    /**
     * End current DTD AttList - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     */
    function endDtdAttlist($xmlwriter) {}

    /**
     * Write full DTD AttList tag - returns FALSE on error 
     *
     * @return bool
     * @param  xmlwriter resource
     * @param  name string
     * @param  content string
     */
    function writeDtdAttlist($xmlwriter, $name, $content) {}

    /**
     * Output current buffer as string 
     *
     * @return string
     * @param  xmlwriter resource
     * @param  flush bool[optional]
     */
    function outputMemory($xmlwriter, $flush = null) {}

    /**
     * Output current buffer 
     *
     * @return mixed
     * @param  xmlwriter resource
     * @param  empty bool[optional]
     */
    function flush($xmlwriter, $empty = null) {}

};

class Directory {
    /**
     * Close directory connection identified by the dir_handle 
     *
     * @return void
     * @param  dir_handle resource[optional]
     */
    function close($dir_handle = null) {}

    /**
     * Rewind dir_handle back to the start 
     *
     * @return void
     * @param  dir_handle resource[optional]
     */
    function rewind($dir_handle = null) {}

    /**
     * Read directory entry from dir_handle 
     *
     * @return string
     * @param  dir_handle resource[optional]
     */
    function read($dir_handle = null) {}

};

class SQLiteDatabase {
    /**
     * Opens a SQLite database. Will create the database if it does not exist. 
     *
     * @return resource
     * @param  filename string
     * @param  mode int[optional]
     * @param  error_message string[optional]
     */
    function __construct($filename, $mode = null, &$error_message) {}

    /**
     * Closes an open sqlite database. 
     *
     * @return void
     * @param  db resource
     */
    function close($db) {}

    /**
     * Executes a query against a given database and returns a result handle. 
     *
     * @return resource
     * @param  query string
     * @param  db resource
     * @param  result_type int[optional]
     * @param  error_message string[optional]
     */
    function query($query, $db, $result_type = null, &$error_message) {}

    /**
     * Executes a result-less query against a given database 
     *
     * @return bool
     * @param  query string
     * @param  db resource
     * @param  error_message string
     */
    function queryExec($query, $db, &$error_message) {}

    /**
     * Executes a query against a given database and returns an array of arrays. 
     *
     * @return array
     * @param  db resource
     * @param  query string
     * @param  result_type int[optional]
     * @param  decode_binary bool[optional]
     */
    function arrayQuery($db, $query, $result_type = null, $decode_binary = null) {}

    /**
     * Executes a query and returns either an array for one single column or the value of the first row. 
     *
     * @return array
     * @param  db resource
     * @param  query string
     * @param  first_row_only bool[optional]
     * @param  decode_binary bool[optional]
     */
    function singleQuery($db, $query, $first_row_only = null, $decode_binary = null) {}

    /**
     * Executes a query that does not prefetch and buffer all data. 
     *
     * @return resource
     * @param  query string
     * @param  db resource
     * @param  result_type int[optional]
     * @param  error_message string[optional]
     */
    function unbufferedQuery($query, $db, $result_type = null, &$error_message) {}

    /**
     * Returns the rowid of the most recently inserted row. 
     *
     * @return int
     * @param  db resource
     */
    function lastInsertRowid($db) {}

    /**
     * Returns the number of rows that were changed by the most recent SQL statement. 
     *
     * @return int
     * @param  db resource
     */
    function changes($db) {}

    /**
     * Registers an aggregate function for queries. 
     *
     * @return void
     * @param  db resource
     * @param  funcname string
     * @param  step_func mixed
     * @param  finalize_func mixed
     * @param  num_args long
     */
    function createAggregate($db, $funcname, $step_func, $finalize_func, $num_args) {}

    /**
     * Registers a "regular" function for queries. 
     *
     * @return void
     * @param  db resource
     * @param  funcname string
     * @param  callback mixed
     * @param  num_args long
     */
    function createFunction($db, $funcname, $callback, $num_args) {}

    /**
     * Set busy timeout duration. If ms <= 0, all busy handlers are disabled. 
     *
     * @return void
     * @param  db resource
     * @param  ms int
     */
    function busyTimeout($db, $ms) {}

    /**
     * Returns the error code of the last error for a database. 
     *
     * @return int
     * @param  db resource
     */
    function lastError($db) {}

    /**
     * Return an array of column types from a particular table. 
     *
     * @return array
     * @param  table_name string
     * @param  db resource
     * @param  result_type int[optional]
     */
    function fetchColumnTypes($table_name, $db, $result_type = null) {}

    /**
     * Returns the textual description of an error code. 
     *
     * @return string
     * @param  error_code int
     */
    function error_string($error_code) {}

    /**
     * Escapes a string for use as a query parameter. 
     *
     * @return string
     * @param  item string
     */
    function escape_string($item) {}

};

class SQLiteResult {
    /**
     * Fetches the next row from a result set as an array. 
     *
     * @return array
     * @param  result resource
     * @param  result_type int[optional]
     * @param  decode_binary bool[optional]
     */
    function fetch($result, $result_type = null, $decode_binary = null) {}

    /**
     * Fetches the next row from a result set as an object. 
     *
     * @return object
     * @param  result resource
     * @param  class_name string[optional]
     * @param  ctor_params NULL|array[optional]
     * @param  decode_binary bool[optional]
     */
    function fetchObject($result, $class_name = null, $ctor_params = null, $decode_binary = null) {}

    /**
     * Fetches the first column of a result set as a string. 
     *
     * @return string
     * @param  result resource
     * @param  decode_binary bool[optional]
     */
    function fetchSingle($result, $decode_binary = null) {}

    /**
     * Fetches all rows from a result set as an array of arrays. 
     *
     * @return array
     * @param  result resource
     * @param  result_type int[optional]
     * @param  decode_binary bool[optional]
     */
    function fetchAll($result, $result_type = null, $decode_binary = null) {}

    /**
     * Fetches a column from the current row of a result set. 
     *
     * @return mixed
     * @param  result resource
     * @param  index_or_name mixed
     * @param  decode_binary bool[optional]
     */
    function column($result, $index_or_name, $decode_binary = null) {}

    /**
     * Returns the number of fields in a result set. 
     *
     * @return int
     * @param  result resource
     */
    function numFields($result) {}

    /**
     * Returns the name of a particular field of a result set. 
     *
     * @return string
     * @param  result resource
     * @param  field_index int
     */
    function fieldName($result, $field_index) {}

    /**
     * Fetches the current row from a result set as an array. 
     *
     * @return array
     * @param  result resource
     * @param  result_type int[optional]
     * @param  decode_binary bool[optional]
     */
    function current($result, $result_type = null, $decode_binary = null) {}

    /**
     * Return the current row index of a buffered result. 
     *
     * @return int
     * @param  result resource
     */
    function key($result) {}

    /**
     * Seek to the next row number of a result set. 
     *
     * @return bool
     * @param  result resource
     */
    function next($result) {}

    /**
     * Returns whether more rows are available. 
     *
     * @return bool
     * @param  result resource
     */
    function valid($result) {}

    /**
     * Seek to the first row number of a buffered result set. 
     *
     * @return bool
     * @param  result resource
     */
    function rewind($result) {}

    /**
     * Returns the number of rows in a buffered result set. 
     *
     * @return int
     * @param  result resource
     */
    function count($result) {}

    /**
     * * Seek to the previous row number of a result set. 
     *
     * @return bool
     * @param  result resource
     */
    function prev($result) {}

    /**
     * * Returns whether a previous row is available. 
     *
     * @return bool
     * @param  result resource
     */
    function hasPrev($result) {}

    /**
     * Returns the number of rows in a buffered result set. 
     *
     * @return int
     * @param  result resource
     */
    function numRows($result) {}

    /**
     * Seek to a particular row number of a buffered result set. 
     *
     * @return bool
     * @param  result resource
     * @param  row int
     */
    function seek($result, $row) {}

};

class SQLiteUnbuffered {
    /**
     * Fetches the next row from a result set as an array. 
     *
     * @return array
     * @param  result resource
     * @param  result_type int[optional]
     * @param  decode_binary bool[optional]
     */
    function fetch($result, $result_type = null, $decode_binary = null) {}

    /**
     * Fetches the next row from a result set as an object. 
     *
     * @return object
     * @param  result resource
     * @param  class_name string[optional]
     * @param  ctor_params NULL|array[optional]
     * @param  decode_binary bool[optional]
     */
    function fetchObject($result, $class_name = null, $ctor_params = null, $decode_binary = null) {}

    /**
     * Fetches the first column of a result set as a string. 
     *
     * @return string
     * @param  result resource
     * @param  decode_binary bool[optional]
     */
    function fetchSingle($result, $decode_binary = null) {}

    /**
     * Fetches all rows from a result set as an array of arrays. 
     *
     * @return array
     * @param  result resource
     * @param  result_type int[optional]
     * @param  decode_binary bool[optional]
     */
    function fetchAll($result, $result_type = null, $decode_binary = null) {}

    /**
     * Fetches a column from the current row of a result set. 
     *
     * @return mixed
     * @param  result resource
     * @param  index_or_name mixed
     * @param  decode_binary bool[optional]
     */
    function column($result, $index_or_name, $decode_binary = null) {}

    /**
     * Returns the number of fields in a result set. 
     *
     * @return int
     * @param  result resource
     */
    function numFields($result) {}

    /**
     * Returns the name of a particular field of a result set. 
     *
     * @return string
     * @param  result resource
     * @param  field_index int
     */
    function fieldName($result, $field_index) {}

    /**
     * Fetches the current row from a result set as an array. 
     *
     * @return array
     * @param  result resource
     * @param  result_type int[optional]
     * @param  decode_binary bool[optional]
     */
    function current($result, $result_type = null, $decode_binary = null) {}

    /**
     * Seek to the next row number of a result set. 
     *
     * @return bool
     * @param  result resource
     */
    function next($result) {}

    /**
     * Returns whether more rows are available. 
     *
     * @return bool
     * @param  result resource
     */
    function valid($result) {}

};

class mysqli {
    /**
     * Turn auto commit on or of 
     *
     * @return bool
     * @param  link object
     * @param  mode bool
     */
    function autocommit($link, $mode) {}

    /**
     * Change logged-in user of the active connection 
     *
     * @return bool
     * @param  link object
     * @param  user string
     * @param  password string
     * @param  database string
     */
    function change_user($link, $user, $password, $database) {}

    /**
     * Returns the name of the character set used for this connection 
     *
     * @return string
     * @param  link object
     */
    function character_set_name($link) {}

    /**
     * Returns the name of the character set used for this connection 
     *
     * @return string
     * @param  link object
     */
    function client_encoding($link) {}

    /**
     * Close connection 
     *
     * @return bool
     * @param  link object
     */
    function close($link) {}

    /**
     * Commit outstanding actions and close transaction 
     *
     * @return bool
     * @param  link object
     */
    function commit($link) {}

    /**
     * Open a connection to a mysql server 
     *
     * @return mysqli
     * @param  hostname string[optional]
     * @param  username string[optional]
     * @param  passwd string[optional]
     * @param  dbname string[optional]
     * @param  port int[optional]
     * @param  socket string[optional]
     */
    function connect($hostname = null, $username = null, $passwd = null, $dbname = null, $port = null, $socket = null) {}

    /**
     * 
     *
     * @return bool
     * @param  debug string
     */
    function debug($debug) {}

    /**
     * 
     *
     * @return bool
     * @param  link object
     */
    function disable_reads_from_master($link) {}

    /**
     * 
     *
     * @return bool
     * @param  link object
     */
    function disable_rpl_parse($link) {}

    /**
     * 
     *
     * @return bool
     * @param  link object
     */
    function dump_debug_info($link) {}

    /**
     * 
     *
     * @return bool
     * @param  link object
     */
    function enable_reads_from_master($link) {}

    /**
     * 
     *
     * @return bool
     * @param  link object
     */
    function enable_rpl_parse($link) {}

    /**
     * returns a character set object 
     *
     * @return object
     * @param  link object
     */
    function get_charset($link) {}

    /**
     * Get MySQL client info 
     *
     * @return string
     */
    function get_client_info() {}

    /**
     * Get MySQL server info 
     *
     * @return string
     * @param  link object
     */
    function get_server_info($link) {}

    /**
     * 
     *
     * @return object
     * @param  link object
     */
    function get_warnings($link) {}

    /**
     * Initialize mysqli and return a resource for use with mysql_real_connect 
     *
     * @return mysqli
     */
    function init() {}

    /**
     * Kill a mysql process on the server 
     *
     * @return bool
     * @param  link object
     * @param  processid int
     */
    function kill($link, $processid) {}

    /**
     * unsets user defined handler for load local infile command 
     *
     * @return unknown
     * @param  link object
     */
    function set_local_infile_default($link) {}

    /**
     * Set callback functions for LOAD DATA LOCAL INFILE 
     *
     * @return bool
     * @param  link object
     * @param  read_func callback
     */
    function set_local_infile_handler($link, $read_func) {}

    /**
     * Enforce execution of a query on the master in a master/slave setup 
     *
     * @return bool
     * @param  link object
     * @param  query string
     */
    function master_query($link, $query) {}

    /**
     * Binary-safe version of mysql_query() 
     *
     * @return bool
     * @param  link object
     * @param  query string
     */
    function multi_query($link, $query) {}

    /**
     * Open a connection to a mysql server 
     *
     * @return mysqli
     * @param  hostname string[optional]
     * @param  username string[optional]
     * @param  passwd string[optional]
     * @param  dbname string[optional]
     * @param  port int[optional]
     * @param  socket string[optional]
     */
    function mysqli($hostname = null, $username = null, $passwd = null, $dbname = null, $port = null, $socket = null) {}

    /**
     * check if there any more query results from a multi query 
     *
     * @return bool
     * @param  link object
     */
    function more_results($link) {}

    /**
     * read next result from multi_query 
     *
     * @return bool
     * @param  link object
     */
    function next_result($link) {}

    /**
     * Set options 
     *
     * @return bool
     * @param  link object
     * @param  flags int
     * @param  values mixed
     */
    function options($link, $flags, $values) {}

    /**
     * Ping a server connection or reconnect if there is no connection 
     *
     * @return bool
     * @param  link object
     */
    function ping($link) {}

    /**
     * Prepare a SQL statement for execution 
     *
     * @return mysqli_stmt
     * @param  link object
     * @param  query string
     */
    function prepare($link, $query) {}

    /**
     * 
     *
     * @return mixed
     * @param  link object
     * @param  query string
     * @param  resultmode int[optional]
     */
    function query($link, $query, $resultmode = null) {}

    /**
     * Open a connection to a mysql server 
     *
     * @return bool
     * @param  link object
     * @param  hostname string[optional]
     * @param  username string[optional]
     * @param  passwd string[optional]
     * @param  dbname string[optional]
     * @param  port int[optional]
     * @param  socket string[optional]
     * @param  flags int[optional]
     */
    function real_connect($link, $hostname = null, $username = null, $passwd = null, $dbname = null, $port = null, $socket = null, $flags = null) {}

    /**
     * Escapes special characters in a string for use in a SQL statement, taking into account the current charset of the connection 
     *
     * @return string
     * @param  link object
     * @param  escapestr string
     */
    function real_escape_string($link, $escapestr) {}

    /**
     * Escapes special characters in a string for use in a SQL statement, taking into account the current charset of the connection 
     *
     * @return string
     * @param  link object
     * @param  escapestr string
     */
    function escape_string($link, $escapestr) {}

    /**
     * Binary-safe version of mysql_query() 
     *
     * @return bool
     * @param  link object
     * @param  query string
     */
    function real_query($link, $query) {}

    /**
     * Undo actions from current transaction 
     *
     * @return bool
     * @param  link object
     */
    function rollback($link) {}

    /**
     * 
     *
     * @return int
     * @param  link object
     */
    function rpl_parse_enabled($link) {}

    /**
     * 
     *
     * @return bool
     * @param  link object
     */
    function rpl_probe($link) {}

    /**
     * 
     *
     * @return int
     * @param  query string
     */
    function rpl_query_type($query) {}

    /**
     * Select a MySQL database 
     *
     * @return bool
     * @param  link object
     * @param  dbname string
     */
    function select_db($link, $dbname) {}

    /**
     * sets client character set 
     *
     * @return bool
     * @param  link object
     * @param  csname string
     */
    function set_charset($link, $csname) {}

    /**
     * Set options 
     *
     * @return bool
     * @param  link object
     * @param  flags int
     * @param  values mixed
     */
    function set_opt($link, $flags, $values) {}

    /**
     * Enforce execution of a query on a slave in a master/slave setup 
     *
     * @return bool
     * @param  link object
     * @param  query string
     */
    function slave_query($link, $query) {}

    /**
     * 
     *
     * @return bool
     * @param  link object
     * @param  key string
     * @param  cert string
     * @param  ca string
     * @param  capath string
     * @param  cipher string
     */
    function ssl_set($link, $key, $cert, $ca, $capath, $cipher) {}

    /**
     * Get current system status 
     *
     * @return string
     * @param  link object
     */
    function stat($link) {}

    /**
     * 
     *
     * @return mysqli_stmt
     * @param  link object
     */
    function stmt_init($link) {}

    /**
     * Buffer result set on client 
     *
     * @return mysqli_result
     * @param  link object
     */
    function store_result($link) {}

    /**
     * Return whether thread safety is given or not 
     *
     * @return bool
     */
    function thread_safe() {}

    /**
     * Directly retrieve query results - do not buffer results on client side 
     *
     * @return mysqli_result
     * @param  link object
     */
    function use_result($link) {}

};

class mysqli_result {
    /**
     * Free query result memory for the given result handle 
     *
     * @return void
     * @param  result object
     */
    function close($result) {}

    /**
     * Free query result memory for the given result handle 
     *
     * @return void
     * @param  result object
     */
    function free($result) {}

    /**
     * Move internal result pointer 
     *
     * @return bool
     * @param  result object
     * @param  offset int
     */
    function data_seek($result, $offset) {}

    /**
     * Get column information from a result and return as an object 
     *
     * @return object
     * @param  result object
     */
    function fetch_field($result) {}

    /**
     * Return array of objects containing field meta-data 
     *
     * @return array
     * @param  result object
     */
    function fetch_fields($result) {}

    /**
     * Fetch meta-data for a single field 
     *
     * @return object
     * @param  result object
     * @param  offset int
     */
    function fetch_field_direct($result, $offset) {}

    /**
     * Fetch a result row as an associative array, a numeric array, or both 
     *
     * @return mixed
     * @param  result object
     * @param  resulttype int[optional]
     */
    function fetch_array($result, $resulttype = null) {}

    /**
     * Fetch a result row as an associative array 
     *
     * @return array
     * @param  result object
     */
    function fetch_assoc($result) {}

    /**
     * Fetch a result row as an object 
     *
     * @return mixed
     * @param  result object
     * @param  class_name string[optional]
     * @param  ctor_params NULL|array[optional]
     */
    function fetch_object($result, $class_name = null, $ctor_params = null) {}

    /**
     * Get a result row as an enumerated array 
     *
     * @return mixed
     * @param  result object
     */
    function fetch_row($result) {}

    /**
     * 
     *
     * @return int
     * @param  link object
     */
    function field_count($link) {}

    /**
     * 
     *
     * @return bool
     * @param  result object
     * @param  fieldnr int
     */
    function field_seek($result, $fieldnr) {}

    /**
     * Free query result memory for the given result handle 
     *
     * @return void
     * @param  result object
     */
    function free_result($result) {}

};

class mysqli_stmt {
    /**
     * 
     *
     * @return int
     * @param  stmt object
     * @param  attr long
     */
    function attr_get($stmt, $attr) {}

    /**
     * 
     *
     * @return int
     * @param  stmt object
     * @param  attr long
     * @param  mode bool
     */
    function attr_set($stmt, $attr, $mode) {}

    /**
     * Bind variables to a prepared statement as parameters 
     *
     * @return bool
     * @param  stmt object
     * @param  types string
     * @param  variable mixed
     * @param  mixed unknown[optional]
     * @vararg ...
     */
    function bind_param($stmt, $types, $variable, $mixed = null) {}

    /**
     * Bind variables to a prepared statement for result storage 
     *
     * @return bool
     * @param  stmt object
     * @param  var mixed
     * @param  mixed unknown[optional]
     * @vararg ...
     */
    function bind_result($stmt, $var, $mixed = null) {}

    /**
     * Close statement 
     *
     * @return bool
     * @param  stmt object
     */
    function close($stmt) {}

    /**
     * Move internal result pointer 
     *
     * @return void
     * @param  stmt object
     * @param  offset int
     */
    function data_seek($stmt, $offset) {}

    /**
     * Execute a prepared statement 
     *
     * @return bool
     * @param  stmt object
     */
    function execute($stmt) {}

    /**
     * Fetch results from a prepared statement into the bound variables 
     *
     * @return bool
     * @param  stmt object
     */
    function fetch($stmt) {}

    /**
     * return result set from statement 
     *
     * @return mysqli_result
     * @param  stmt object
     */
    function result_metadata($stmt) {}

    /**
     * Return the number of rows in statements result set 
     *
     * @return int
     * @param  stmt object
     */
    function num_rows($stmt) {}

    /**
     * Prepare a SQL statement for execution 
     *
     * @return mysqli_stmt
     * @param  link object
     * @param  query string
     */
    function stmt($link, $query) {}

    /**
     * Free stored result memory for the given statement handle 
     *
     * @return void
     * @param  stmt object
     */
    function free_result($stmt) {}

    /**
     * reset a prepared statement 
     *
     * @return bool
     * @param  stmt object
     */
    function reset($stmt) {}

    /**
     * 
     *
     * @return bool
     * @param  stmt object
     * @param  query string
     */
    function prepare($stmt, $query) {}

    /**
     * 
     *
     * @return bool
     * @param  stmt unknown
     */
    function store_result($stmt) {}

};

class DOMStringList {
    /**
     * 
     *
     * @return domstring
     * @param  index int
     */
    function item($index) {}

};

class DOMNameList {
    /**
     * 
     *
     * @return string
     * @param  index int
     */
    function getName($index) {}

    /**
     * 
     *
     * @return string
     * @param  index int
     */
    function getNamespaceURI($index) {}

};

class DOMImplementationList {
    /**
     * 
     *
     * @return domdomimplementation
     * @param  index int
     */
    function item($index) {}

};

class DOMImplementationSource {
    /**
     * 
     *
     * @return domdomimplementation
     * @param  features string
     */
    function getDomimplementation($features) {}

    /**
     * 
     *
     * @return domimplementationlist
     * @param  features string
     */
    function getDomimplementations($features) {}

};

class DOMNode {
    /**
     * 
     *
     * @return domnode
     * @param  newChild DomNode
     * @param  refChild DomNode
     */
    function insertBefore($newChild, $refChild) {}

    /**
     * 
     *
     * @return DomNode
     * @param  newChild DomNode
     * @param  oldChild DomNode
     */
    function replaceChild($newChild, $oldChild) {}

    /**
     * 
     *
     * @return DomNode
     * @param  oldChild DomNode
     */
    function removeChild($oldChild) {}

    /**
     * 
     *
     * @return DomNode
     * @param  newChild DomNode
     */
    function appendChild($newChild) {}

    /**
     * 
     *
     * @return boolean
     */
    function hasChildNodes() {}

    /**
     * 
     *
     * @return DomNode
     * @param  deep boolean
     */
    function cloneNode($deep) {}

    /**
     * 
     *
     * @return void
     */
    function normalize() {}

    /**
     * 
     *
     * @return boolean
     * @param  feature string
     * @param  version string
     */
    function isSupported($feature, $version) {}

    /**
     * 
     *
     * @return boolean
     */
    function hasAttributes() {}

    /**
     * 
     *
     * @return short
     * @param  other DomNode
     */
    function compareDocumentPosition($other) {}

    /**
     * 
     *
     * @return boolean
     * @param  other DomNode
     */
    function isSameNode($other) {}

    /**
     * 
     *
     * @return string
     * @param  namespaceURI string
     */
    function lookupPrefix($namespaceURI) {}

    /**
     * 
     *
     * @return boolean
     * @param  namespaceURI string
     */
    function isDefaultNamespace($namespaceURI) {}

    /**
     * 
     *
     * @return string
     * @param  prefix string
     */
    function lookupNamespaceUri($prefix) {}

    /**
     * 
     *
     * @return boolean
     * @param  arg DomNode
     */
    function isEqualNode($arg) {}

    /**
     * 
     *
     * @return DomNode
     * @param  feature string
     * @param  version string
     */
    function getFeature($feature, $version) {}

    /**
     * 
     *
     * @return DomUserData
     * @param  key string
     * @param  data DomUserData
     * @param  handler userdatahandler
     */
    function setUserData($key, $data, $handler) {}

    /**
     * 
     *
     * @return DomUserData
     * @param  key string
     */
    function getUserData($key) {}

};

class DOMCharacterData extends DOMNode {
    /**
     * 
     *
     * @return string
     * @param  offset int
     * @param  count int
     */
    function substringData($offset, $count) {}

    /**
     * 
     *
     * @return void
     * @param  arg string
     */
    function appendData($arg) {}

    /**
     * 
     *
     * @return void
     * @param  offset int
     * @param  arg string
     */
    function insertData($offset, $arg) {}

    /**
     * 
     *
     * @return void
     * @param  offset int
     * @param  count int
     */
    function deleteData($offset, $count) {}

    /**
     * 
     *
     * @return void
     * @param  offset int
     * @param  count int
     * @param  arg string
     */
    function replaceData($offset, $count, $arg) {}

};

class DOMUserDataHandler {
    /**
     * 
     *
     * @return dom_void
     * @param  operation short
     * @param  key string
     * @param  data domobject
     * @param  src node
     * @param  dst node
     */
    function handle($operation, $key, $data, $src, $dst) {}

};

class DOMErrorHandler {
    /**
     * 
     *
     * @return dom_boolean
     * @param  error domerror
     */
    function handleError($error) {}

};

class DOMConfiguration {
    /**
     * 
     *
     * @return dom_void
     * @param  name string
     * @param  value domuserdata
     */
    function setParameter($name, $value) {}

    /**
     * 
     *
     * @return domdomuserdata
     * @param  name string
     */
    function getParameter($name) {}

    /**
     * 
     *
     * @return boolean
     * @param  name string
     * @param  value domuserdata
     */
    function canSetParameter($name, $value) {}

};

class DOMDocumentType extends DOMNode {
};

class DOMNotation extends DOMNode {
};

class DOMEntity extends DOMNode {
};

class DOMStringExtend {
    /**
     * 
     *
     * @return int
     * @param  offset32 int
     */
    function findOffset16($offset32) {}

    /**
     * 
     *
     * @return int
     * @param  offset16 int
     */
    function findOffset32($offset16) {}

};

class XSLTProcessor {
    /**
     * 
     *
     * @return void
     * @param  doc domdocument
     */
    function importStylesheet($doc) {}

    /**
     * 
     *
     * @return domdocument
     * @param  doc domnode
     */
    function transformToDoc($doc) {}

    /**
     * 
     *
     * @return int
     * @param  doc domdocument
     * @param  uri string
     */
    function transformToUri($doc, $uri) {}

    /**
     * 
     *
     * @return string
     * @param  doc domdocument
     */
    function transformToXml($doc) {}

    /**
     * 
     *
     * @return bool
     * @param  namespace string
     * @param  name mixed
     * @param  value string[optional]
     */
    function setParameter($namespace, $name, $value = null) {}

    /**
     * 
     *
     * @return string
     * @param  namespace string
     * @param  name string
     */
    function getParameter($namespace, $name) {}

    /**
     * 
     *
     * @return bool
     * @param  namespace string
     * @param  name string
     */
    function removeParameter($namespace, $name) {}

    /**
     * 
     *
     * @return bool
     */
    function hasExsltSupport() {}

    /**
     * 
     *
     * @return void
     */
    function registerPHPFunctions() {}

};

class tidy {
    /**
     * Returns the value of the specified configuration option for the tidy document. 
     *
     * @return mixed
     * @param  option string
     */
    function getOpt($option) {}

    /**
     * Execute configured cleanup and repair operations on parsed markup 
     *
     * @return bool
     */
    function cleanRepair() {}

    /**
     * Repair a string using an optionally provided configuration file 
     *
     * @return string
     * @param  data string
     * @param  config_file mixed[optional]
     * @param  encoding string[optional]
     */
    function repairString($data, $config_file = null, $encoding = null) {}

    /**
     * Repair a file using an optionally provided configuration file 
     *
     * @return string
     * @param  filename string
     * @param  config_file mixed[optional]
     * @param  encoding string[optional]
     * @param  use_include_path bool[optional]
     */
    function repairFile($filename, $config_file = null, $encoding = null, $use_include_path = null) {}

    /**
     * Run configured diagnostics on parsed and repaired markup. 
     *
     * @return bool
     */
    function diagnose() {}

    /**
     * Get release date (version) for Tidy library 
     *
     * @return string
     */
    function getRelease() {}

    /**
     * Get current Tidy configuarion 
     *
     * @return array
     */
    function getConfig() {}

    /**
     * Get status of specfied document. 
     *
     * @return int
     */
    function getStatus() {}

    /**
     * Get the Detected HTML version for the specified document. 
     *
     * @return int
     */
    function getHtmlVer() {}

    /**
     * Returns the documentation for the given option name 
     *
     * @return string
     * @param  resource tidy
     * @param  optname string
     */
    function getOptDoc($resource, $optname) {}

    /**
     * Indicates if the document is a generic (non HTML/XHTML) XML document. 
     *
     * @return bool
     */
    function isXhtml() {}

    /**
     * Returns a TidyNode Object representing the root of the tidy parse tree 
     *
     * @return tidyNode
     */
    function root() {}

    /**
     * Returns a TidyNode Object starting from the <HEAD> tag of the tidy parse tree 
     *
     * @return tidyNode
     */
    function head() {}

    /**
     * Returns a TidyNode Object starting from the <HTML> tag of the tidy parse tree 
     *
     * @return tidyNode
     */
    function html() {}

    /**
     * Returns a TidyNode Object starting from the <BODY> tag of the tidy parse tree 
     *
     * @return tidyNode
     * @param  tidy resource
     */
    function body($tidy) {}

};

class ReflectionException extends Exception {
};

interface Traversable {
};

interface IteratorAggregate {
};

interface Iterator {
};

interface ArrayAccess {
};

interface Serializable {
};

interface SplObserver {
};

interface SplSubject {
};

interface Countable {
};

interface RecursiveIterator {
};

interface OuterIterator {
};

interface SeekableIterator {
};

define("ABDAY_1", 0);
define("ABDAY_2", 0);
define("ABDAY_3", 0);
define("ABDAY_4", 0);
define("ABDAY_5", 0);
define("ABDAY_6", 0);
define("ABDAY_7", 0);
define("ABMON_1", 0);
define("ABMON_2", 0);
define("ABMON_3", 0);
define("ABMON_4", 0);
define("ABMON_5", 0);
define("ABMON_6", 0);
define("ABMON_7", 0);
define("ABMON_8", 0);
define("ABMON_9", 0);
define("ABMON_10", 0);
define("ABMON_11", 0);
define("ABMON_12", 0);
define("AF_INET", 0);
define("AF_INET6", 0);
define("AF_UNIX", 0);
define("ALT_DIGITS", 0);
define("AM_STR", 0);
define("APLOG_ALERT", 0);
define("APLOG_CRIT", 0);
define("APLOG_DEBUG", 0);
define("APLOG_EMERG", 0);
define("APLOG_ERR", 0);
define("APLOG_INFO", 0);
define("APLOG_NOTICE", 0);
define("APLOG_WARNING", 0);
define("ASSERT_ACTIVE", 0);
define("ASSERT_BAIL", 0);
define("ASSERT_CALLBACK", 0);
define("ASSERT_QUIET_EVAL", 0);
define("ASSERT_WARNING", 0);
define("AUTH_REQUIRED", 0);
define("BAD_REQUEST", 0);
define("CAL_DOW_DAYNO", 0);
define("CAL_DOW_LONG", 0);
define("CAL_DOW_SHORT", 0);
define("CAL_EASTER_ALWAYS_GREGORIAN", 0);
define("CAL_EASTER_ALWAYS_JULIAN", 0);
define("CAL_EASTER_DEFAULT", 0);
define("CAL_EASTER_ROMAN", 0);
define("CAL_FRENCH", 0);
define("CAL_GREGORIAN", 0);
define("CAL_JEWISH", 0);
define("CAL_JEWISH_ADD_ALAFIM", 0);
define("CAL_JEWISH_ADD_ALAFIM_GERESH", 0);
define("CAL_JEWISH_ADD_GERESHAYIM", 0);
define("CAL_JULIAN", 0);
define("CAL_MONTH_FRENCH", 0);
define("CAL_MONTH_GREGORIAN_LONG", 0);
define("CAL_MONTH_GREGORIAN_SHORT", 0);
define("CAL_MONTH_JEWISH", 0);
define("CAL_MONTH_JULIAN_LONG", 0);
define("CAL_MONTH_JULIAN_SHORT", 0);
define("CAL_NUM_CALS", 0);
define("CASE_LOWER", 0);
define("CASE_UPPER", 0);
define("CHAR_MAX", 0);
define("CL_EXPUNGE", 0);
define("CODESET", 0);
define("CONNECTION_ABORTED", 0);
define("CONNECTION_NORMAL", 0);
define("CONNECTION_TIMEOUT", 0);
define("COUNT_NORMAL", 0);
define("COUNT_RECURSIVE", 0);
define("CP_MOVE", 0);
define("CP_UID", 0);
define("CREDITS_ALL", 0);
define("CREDITS_DOCS", 0);
define("CREDITS_FULLPAGE", 0);
define("CREDITS_GENERAL", 0);
define("CREDITS_GROUP", 0);
define("CREDITS_MODULES", 0);
define("CREDITS_QA", 0);
define("CREDITS_SAPI", 0);
define("CRNCYSTR", 0);
define("CRYPT_BLOWFISH", 0);
define("CRYPT_EXT_DES", 0);
define("CRYPT_MD5", 0);
define("CRYPT_SALT_LENGTH", 0);
define("CRYPT_STD_DES", 0);
define("CURLAUTH_ANY", 0);
define("CURLAUTH_ANYSAFE", 0);
define("CURLAUTH_BASIC", 0);
define("CURLAUTH_DIGEST", 0);
define("CURLAUTH_GSSNEGOTIATE", 0);
define("CURLAUTH_NTLM", 0);
define("CURLCLOSEPOLICY_CALLBACK", 0);
define("CURLCLOSEPOLICY_LEAST_RECENTLY_USED", 0);
define("CURLCLOSEPOLICY_LEAST_TRAFFIC", 0);
define("CURLCLOSEPOLICY_OLDEST", 0);
define("CURLCLOSEPOLICY_SLOWEST", 0);
define("CURLE_ABORTED_BY_CALLBACK", 0);
define("CURLE_BAD_CALLING_ORDER", 0);
define("CURLE_BAD_CONTENT_ENCODING", 0);
define("CURLE_BAD_FUNCTION_ARGUMENT", 0);
define("CURLE_BAD_PASSWORD_ENTERED", 0);
define("CURLE_COULDNT_CONNECT", 0);
define("CURLE_COULDNT_RESOLVE_HOST", 0);
define("CURLE_COULDNT_RESOLVE_PROXY", 0);
define("CURLE_FAILED_INIT", 0);
define("CURLE_FILESIZE_EXCEEDED", 0);
define("CURLE_FILE_COULDNT_READ_FILE", 0);
define("CURLE_FTP_ACCESS_DENIED", 0);
define("CURLE_FTP_BAD_DOWNLOAD_RESUME", 0);
define("CURLE_FTP_CANT_GET_HOST", 0);
define("CURLE_FTP_CANT_RECONNECT", 0);
define("CURLE_FTP_COULDNT_GET_SIZE", 0);
define("CURLE_FTP_COULDNT_RETR_FILE", 0);
define("CURLE_FTP_COULDNT_SET_ASCII", 0);
define("CURLE_FTP_COULDNT_SET_BINARY", 0);
define("CURLE_FTP_COULDNT_STOR_FILE", 0);
define("CURLE_FTP_COULDNT_USE_REST", 0);
define("CURLE_FTP_PORT_FAILED", 0);
define("CURLE_FTP_QUOTE_ERROR", 0);
define("CURLE_FTP_SSL_FAILED", 0);
define("CURLE_FTP_USER_PASSWORD_INCORRECT", 0);
define("CURLE_FTP_WEIRD_227_FORMAT", 0);
define("CURLE_FTP_WEIRD_PASS_REPLY", 0);
define("CURLE_FTP_WEIRD_PASV_REPLY", 0);
define("CURLE_FTP_WEIRD_SERVER_REPLY", 0);
define("CURLE_FTP_WEIRD_USER_REPLY", 0);
define("CURLE_FTP_WRITE_ERROR", 0);
define("CURLE_FUNCTION_NOT_FOUND", 0);
define("CURLE_GOT_NOTHING", 0);
define("CURLE_HTTP_NOT_FOUND", 0);
define("CURLE_HTTP_PORT_FAILED", 0);
define("CURLE_HTTP_POST_ERROR", 0);
define("CURLE_HTTP_RANGE_ERROR", 0);
define("CURLE_LDAP_CANNOT_BIND", 0);
define("CURLE_LDAP_INVALID_URL", 0);
define("CURLE_LDAP_SEARCH_FAILED", 0);
define("CURLE_LIBRARY_NOT_FOUND", 0);
define("CURLE_MALFORMAT_USER", 0);
define("CURLE_OBSOLETE", 0);
define("CURLE_OK", 0);
define("CURLE_OPERATION_TIMEOUTED", 0);
define("CURLE_OUT_OF_MEMORY", 0);
define("CURLE_PARTIAL_FILE", 0);
define("CURLE_READ_ERROR", 0);
define("CURLE_RECV_ERROR", 0);
define("CURLE_SEND_ERROR", 0);
define("CURLE_SHARE_IN_USE", 0);
define("CURLE_SSL_CACERT", 0);
define("CURLE_SSL_CERTPROBLEM", 0);
define("CURLE_SSL_CIPHER", 0);
define("CURLE_SSL_CONNECT_ERROR", 0);
define("CURLE_SSL_ENGINE_NOTFOUND", 0);
define("CURLE_SSL_ENGINE_SETFAILED", 0);
define("CURLE_SSL_PEER_CERTIFICATE", 0);
define("CURLE_TELNET_OPTION_SYNTAX", 0);
define("CURLE_TOO_MANY_REDIRECTS", 0);
define("CURLE_UNKNOWN_TELNET_OPTION", 0);
define("CURLE_UNSUPPORTED_PROTOCOL", 0);
define("CURLE_URL_MALFORMAT", 0);
define("CURLE_URL_MALFORMAT_USER", 0);
define("CURLE_WRITE_ERROR", 0);
define("CURLFTPAUTH_DEFAULT", 0);
define("CURLFTPAUTH_SSL", 0);
define("CURLFTPAUTH_TLS", 0);
define("CURLINFO_CONNECT_TIME", 0);
define("CURLINFO_CONTENT_LENGTH_DOWNLOAD", 0);
define("CURLINFO_CONTENT_LENGTH_UPLOAD", 0);
define("CURLINFO_CONTENT_TYPE", 0);
define("CURLINFO_EFFECTIVE_URL", 0);
define("CURLINFO_FILETIME", 0);
define("CURLINFO_HEADER_OUT", 0);
define("CURLINFO_HEADER_SIZE", 0);
define("CURLINFO_HTTP_CODE", 0);
define("CURLINFO_NAMELOOKUP_TIME", 0);
define("CURLINFO_PRETRANSFER_TIME", 0);
define("CURLINFO_REDIRECT_COUNT", 0);
define("CURLINFO_REDIRECT_TIME", 0);
define("CURLINFO_REQUEST_SIZE", 0);
define("CURLINFO_SIZE_DOWNLOAD", 0);
define("CURLINFO_SIZE_UPLOAD", 0);
define("CURLINFO_SPEED_DOWNLOAD", 0);
define("CURLINFO_SPEED_UPLOAD", 0);
define("CURLINFO_SSL_VERIFYRESULT", 0);
define("CURLINFO_STARTTRANSFER_TIME", 0);
define("CURLINFO_TOTAL_TIME", 0);
define("CURLMSG_DONE", 0);
define("CURLM_BAD_EASY_HANDLE", 0);
define("CURLM_BAD_HANDLE", 0);
define("CURLM_CALL_MULTI_PERFORM", 0);
define("CURLM_INTERNAL_ERROR", 0);
define("CURLM_OK", 0);
define("CURLM_OUT_OF_MEMORY", 0);
define("CURLOPT_AUTOREFERER", 0);
define("CURLOPT_BINARYTRANSFER", 0);
define("CURLOPT_BUFFERSIZE", 0);
define("CURLOPT_CAINFO", 0);
define("CURLOPT_CAPATH", 0);
define("CURLOPT_CLOSEPOLICY", 0);
define("CURLOPT_CONNECTTIMEOUT", 0);
define("CURLOPT_COOKIE", 0);
define("CURLOPT_COOKIEFILE", 0);
define("CURLOPT_COOKIEJAR", 0);
define("CURLOPT_COOKIESESSION", 0);
define("CURLOPT_CRLF", 0);
define("CURLOPT_CUSTOMREQUEST", 0);
define("CURLOPT_DNS_CACHE_TIMEOUT", 0);
define("CURLOPT_DNS_USE_GLOBAL_CACHE", 0);
define("CURLOPT_EGDSOCKET", 0);
define("CURLOPT_ENCODING", 0);
define("CURLOPT_FAILONERROR", 0);
define("CURLOPT_FILE", 0);
define("CURLOPT_FILETIME", 0);
define("CURLOPT_FOLLOWLOCATION", 0);
define("CURLOPT_FORBID_REUSE", 0);
define("CURLOPT_FRESH_CONNECT", 0);
define("CURLOPT_FTPAPPEND", 0);
define("CURLOPT_FTPASCII", 0);
define("CURLOPT_FTPLISTONLY", 0);
define("CURLOPT_FTPPORT", 0);
define("CURLOPT_FTPSSLAUTH", 0);
define("CURLOPT_FTP_USE_EPRT", 0);
define("CURLOPT_FTP_USE_EPSV", 0);
define("CURLOPT_HEADER", 0);
define("CURLOPT_HEADERFUNCTION", 0);
define("CURLOPT_HTTP200ALIASES", 0);
define("CURLOPT_HTTPAUTH", 0);
define("CURLOPT_HTTPGET", 0);
define("CURLOPT_HTTPHEADER", 0);
define("CURLOPT_HTTPPROXYTUNNEL", 0);
define("CURLOPT_HTTP_VERSION", 0);
define("CURLOPT_INFILE", 0);
define("CURLOPT_INFILESIZE", 0);
define("CURLOPT_INTERFACE", 0);
define("CURLOPT_KRB4LEVEL", 0);
define("CURLOPT_LOW_SPEED_LIMIT", 0);
define("CURLOPT_LOW_SPEED_TIME", 0);
define("CURLOPT_MAXCONNECTS", 0);
define("CURLOPT_MAXREDIRS", 0);
define("CURLOPT_MUTE", 0);
define("CURLOPT_NETRC", 0);
define("CURLOPT_NOBODY", 0);
define("CURLOPT_NOPROGRESS", 0);
define("CURLOPT_NOSIGNAL", 0);
define("CURLOPT_PASSWDFUNCTION", 0);
define("CURLOPT_PORT", 0);
define("CURLOPT_POST", 0);
define("CURLOPT_POSTFIELDS", 0);
define("CURLOPT_POSTQUOTE", 0);
define("CURLOPT_PROXY", 0);
define("CURLOPT_PROXYAUTH", 0);
define("CURLOPT_PROXYPORT", 0);
define("CURLOPT_PROXYTYPE", 0);
define("CURLOPT_PROXYUSERPWD", 0);
define("CURLOPT_PUT", 0);
define("CURLOPT_QUOTE", 0);
define("CURLOPT_RANDOM_FILE", 0);
define("CURLOPT_RANGE", 0);
define("CURLOPT_READDATA", 0);
define("CURLOPT_READFUNCTION", 0);
define("CURLOPT_REFERER", 0);
define("CURLOPT_RESUME_FROM", 0);
define("CURLOPT_RETURNTRANSFER", 0);
define("CURLOPT_SSLCERT", 0);
define("CURLOPT_SSLCERTPASSWD", 0);
define("CURLOPT_SSLCERTTYPE", 0);
define("CURLOPT_SSLENGINE", 0);
define("CURLOPT_SSLENGINE_DEFAULT", 0);
define("CURLOPT_SSLKEY", 0);
define("CURLOPT_SSLKEYPASSWD", 0);
define("CURLOPT_SSLKEYTYPE", 0);
define("CURLOPT_SSLVERSION", 0);
define("CURLOPT_SSL_CIPHER_LIST", 0);
define("CURLOPT_SSL_VERIFYHOST", 0);
define("CURLOPT_SSL_VERIFYPEER", 0);
define("CURLOPT_STDERR", 0);
define("CURLOPT_TIMECONDITION", 0);
define("CURLOPT_TIMEOUT", 0);
define("CURLOPT_TIMEVALUE", 0);
define("CURLOPT_TRANSFERTEXT", 0);
define("CURLOPT_UNRESTRICTED_AUTH", 0);
define("CURLOPT_UPLOAD", 0);
define("CURLOPT_URL", 0);
define("CURLOPT_USERAGENT", 0);
define("CURLOPT_USERPWD", 0);
define("CURLOPT_VERBOSE", 0);
define("CURLOPT_WRITEFUNCTION", 0);
define("CURLOPT_WRITEHEADER", 0);
define("CURLPROXY_HTTP", 0);
define("CURLPROXY_SOCKS5", 0);
define("CURLVERSION_NOW", 0);
define("CURL_HTTP_VERSION_1_0", 0);
define("CURL_HTTP_VERSION_1_1", 0);
define("CURL_HTTP_VERSION_NONE", 0);
define("CURL_NETRC_IGNORED", 0);
define("CURL_NETRC_OPTIONAL", 0);
define("CURL_NETRC_REQUIRED", 0);
define("CURL_TIMECOND_IFMODSINCE", 0);
define("CURL_TIMECOND_IFUNMODSINCE", 0);
define("CURL_TIMECOND_LASTMOD", 0);
define("CURL_VERSION_IPV6", 0);
define("CURL_VERSION_KERBEROS4", 0);
define("CURL_VERSION_LIBZ", 0);
define("CURL_VERSION_SSL", 0);
define("CURRENCY_SYMBOL", 0);
define("DATE_ATOM", 0);
define("DATE_COOKIE", 0);
define("DATE_ISO8601", 0);
define("DATE_RFC822", 0);
define("DATE_RFC850", 0);
define("DATE_RFC1036", 0);
define("DATE_RFC1123", 0);
define("DATE_RFC2822", 0);
define("DATE_RFC3339", 0);
define("DATE_RSS", 0);
define("DATE_W3C", 0);
define("DAY_1", 0);
define("DAY_2", 0);
define("DAY_3", 0);
define("DAY_4", 0);
define("DAY_5", 0);
define("DAY_6", 0);
define("DAY_7", 0);
define("DB2_AUTOCOMMIT_OFF", 0);
define("DB2_AUTOCOMMIT_ON", 0);
define("DB2_BINARY", 0);
define("DB2_CASE_LOWER", 0);
define("DB2_CASE_NATURAL", 0);
define("DB2_CASE_UPPER", 0);
define("DB2_CHAR", 0);
define("DB2_CONVERT", 0);
define("DB2_DOUBLE", 0);
define("DB2_FORWARD_ONLY", 0);
define("DB2_LONG", 0);
define("DB2_PARAM_FILE", 0);
define("DB2_PARAM_IN", 0);
define("DB2_PARAM_INOUT", 0);
define("DB2_PARAM_OUT", 0);
define("DB2_PASSTHRU", 0);
define("DB2_SCROLLABLE", 0);
define("DB2_XML", 0);
define("DEBUGGER_VERSION", 0);
define("DECIMAL_POINT", 0);
define("DECLINED", 0);
define("DEFAULT_INCLUDE_PATH", 0);
define("DIRECTORY_SEPARATOR", 0);
define("DNS_A", 0);
define("DNS_A6", 0);
define("DNS_AAAA", 0);
define("DNS_ALL", 0);
define("DNS_ANY", 0);
define("DNS_CNAME", 0);
define("DNS_HINFO", 0);
define("DNS_MX", 0);
define("DNS_NAPTR", 0);
define("DNS_NS", 0);
define("DNS_PTR", 0);
define("DNS_SOA", 0);
define("DNS_SRV", 0);
define("DNS_TXT", 0);
define("DOMSTRING_SIZE_ERR", 0);
define("DOM_HIERARCHY_REQUEST_ERR", 0);
define("DOM_INDEX_SIZE_ERR", 0);
define("DOM_INUSE_ATTRIBUTE_ERR", 0);
define("DOM_INVALID_ACCESS_ERR", 0);
define("DOM_INVALID_CHARACTER_ERR", 0);
define("DOM_INVALID_MODIFICATION_ERR", 0);
define("DOM_INVALID_STATE_ERR", 0);
define("DOM_NAMESPACE_ERR", 0);
define("DOM_NOT_FOUND_ERR", 0);
define("DOM_NOT_SUPPORTED_ERR", 0);
define("DOM_NO_DATA_ALLOWED_ERR", 0);
define("DOM_NO_MODIFICATION_ALLOWED_ERR", 0);
define("DOM_PHP_ERR", 0);
define("DOM_SYNTAX_ERR", 0);
define("DOM_VALIDATION_ERR", 0);
define("DOM_WRONG_DOCUMENT_ERR", 0);
define("DONE", 0);
define("D_FMT", 0);
define("D_T_FMT", 0);
define("ENC7BIT", 0);
define("ENC8BIT", 0);
define("ENCBASE64", 0);
define("ENCBINARY", 0);
define("ENCOTHER", 0);
define("ENCQUOTEDPRINTABLE", 0);
define("ENT_COMPAT", 0);
define("ENT_NOQUOTES", 0);
define("ENT_QUOTES", 0);
define("ERA", 0);
define("ERA_D_FMT", 0);
define("ERA_D_T_FMT", 0);
define("ERA_T_FMT", 0);
define("ERA_YEAR", 0);
define("EXIF_USE_MBSTRING", 0);
define("EXTR_IF_EXISTS", 0);
define("EXTR_OVERWRITE", 0);
define("EXTR_PREFIX_ALL", 0);
define("EXTR_PREFIX_IF_EXISTS", 0);
define("EXTR_PREFIX_INVALID", 0);
define("EXTR_PREFIX_SAME", 0);
define("EXTR_REFS", 0);
define("EXTR_SKIP", 0);
define("E_ALL", 0);
define("E_COMPILE_ERROR", 0);
define("E_COMPILE_WARNING", 0);
define("E_CORE_ERROR", 0);
define("E_CORE_WARNING", 0);
define("E_ERROR", 0);
define("E_NOTICE", 0);
define("E_PARSE", 0);
define("E_STRICT", 0);
define("E_USER_ERROR", 0);
define("E_USER_NOTICE", 0);
define("E_USER_WARNING", 0);
define("E_WARNING", 0);
define("FBSQL_ASSOC", 0);
define("FBSQL_BOTH", 0);
define("FBSQL_ISO8859_1", 0);
define("FBSQL_ISO_READ_COMMITTED", 0);
define("FBSQL_ISO_READ_UNCOMMITTED", 0);
define("FBSQL_ISO_REPEATABLE_READ", 0);
define("FBSQL_ISO_SERIALIZABLE", 0);
define("FBSQL_ISO_VERSIONED", 0);
define("FBSQL_LOB_DIRECT", 0);
define("FBSQL_LOB_HANDLE", 0);
define("FBSQL_LOCK_DEFERRED", 0);
define("FBSQL_LOCK_OPTIMISTIC", 0);
define("FBSQL_LOCK_PESSIMISTIC", 0);
define("FBSQL_NOEXEC", 0);
define("FBSQL_NUM", 0);
define("FBSQL_RUNNING", 0);
define("FBSQL_STARTING", 0);
define("FBSQL_STOPPED", 0);
define("FBSQL_STOPPING", 0);
define("FBSQL_UNKNOWN", 0);
define("FBSQL_UTF8", 0);
define("FDFAA", 0);
define("FDFAP", 0);
define("FDFAPRef", 0);
define("FDFAS", 0);
define("FDFAction", 0);
define("FDFCalculate", 0);
define("FDFClearFf", 0);
define("FDFClrF", 0);
define("FDFDown", 0);
define("FDFDownAP", 0);
define("FDFEnter", 0);
define("FDFExit", 0);
define("FDFFf", 0);
define("FDFFile", 0);
define("FDFFlags", 0);
define("FDFFormat", 0);
define("FDFID", 0);
define("FDFIF", 0);
define("FDFKeystroke", 0);
define("FDFNormalAP", 0);
define("FDFRolloverAP", 0);
define("FDFSetF", 0);
define("FDFSetFf", 0);
define("FDFStatus", 0);
define("FDFUp", 0);
define("FDFValidate", 0);
define("FDFValue", 0);
define("FILE_APPEND", 0);
define("FILE_IGNORE_NEW_LINES", 0);
define("FILE_NO_DEFAULT_CONTEXT", 0);
define("FILE_SKIP_EMPTY_LINES", 0);
define("FILE_USE_INCLUDE_PATH", 0);
define("FNM_CASEFOLD", 0);
define("FNM_NOESCAPE", 0);
define("FNM_PATHNAME", 0);
define("FNM_PERIOD", 0);
define("FORBIDDEN", 0);
define("FORCE_DEFLATE", 0);
define("FORCE_GZIP", 0);
define("FRAC_DIGITS", 0);
define("FTP_ASCII", 0);
define("FTP_AUTORESUME", 0);
define("FTP_AUTOSEEK", 0);
define("FTP_BINARY", 0);
define("FTP_FAILED", 0);
define("FTP_FINISHED", 0);
define("FTP_IMAGE", 0);
define("FTP_MOREDATA", 0);
define("FTP_TEXT", 0);
define("FTP_TIMEOUT_SEC", 0);
define("FT_INTERNAL", 0);
define("FT_NOT", 0);
define("FT_PEEK", 0);
define("FT_PREFETCHTEXT", 0);
define("FT_UID", 0);
define("GD_BUNDLED", 0);
define("GLOB_BRACE", 0);
define("GLOB_ERR", 0);
define("GLOB_MARK", 0);
define("GLOB_NOCHECK", 0);
define("GLOB_NOESCAPE", 0);
define("GLOB_NOSORT", 0);
define("GLOB_ONLYDIR", 0);
define("GMP_ROUND_MINUSINF", 0);
define("GMP_ROUND_PLUSINF", 0);
define("GMP_ROUND_ZERO", 0);
define("GROUPING", 0);
define("GSLC_SSL_NO_AUTH", 0);
define("GSLC_SSL_ONEWAY_AUTH", 0);
define("GSLC_SSL_TWOWAY_AUTH", 0);
define("HASH_HMAC", 0);
define("HTML_ENTITIES", 0);
define("HTML_SPECIALCHARS", 0);
define("HTTP_ACCEPTED", 0);
define("HTTP_BAD_GATEWAY", 0);
define("HTTP_BAD_REQUEST", 0);
define("HTTP_CONFLICT", 0);
define("HTTP_CONTINUE", 0);
define("HTTP_CREATED", 0);
define("HTTP_EXPECTATION_FAILED", 0);
define("HTTP_FAILED_DEPENDENCY", 0);
define("HTTP_FORBIDDEN", 0);
define("HTTP_GATEWAY_TIME_OUT", 0);
define("HTTP_GONE", 0);
define("HTTP_INSUFFICIENT_STORAGE", 0);
define("HTTP_INTERNAL_SERVER_ERROR", 0);
define("HTTP_LOCKED", 0);
define("HTTP_METHOD_NOT_ALLOWED", 0);
define("HTTP_MOVED_PERMANENTLY", 0);
define("HTTP_MOVED_TEMPORARILY", 0);
define("HTTP_MULTIPLE_CHOICES", 0);
define("HTTP_MULTI_STATUS", 0);
define("HTTP_NON_AUTHORITATIVE", 0);
define("HTTP_NOT_ACCEPTABLE", 0);
define("HTTP_NOT_EXTENDED", 0);
define("HTTP_NOT_FOUND", 0);
define("HTTP_NOT_IMPLEMENTED", 0);
define("HTTP_NOT_MODIFIED", 0);
define("HTTP_NO_CONTENT", 0);
define("HTTP_OK", 0);
define("HTTP_PARTIAL_CONTENT", 0);
define("HTTP_PAYMENT_REQUIRED", 0);
define("HTTP_PRECONDITION_FAILED", 0);
define("HTTP_PROCESSING", 0);
define("HTTP_PROXY_AUTHENTICATION_REQUIRED", 0);
define("HTTP_RANGE_NOT_SATISFIABLE", 0);
define("HTTP_REQUEST_ENTITY_TOO_LARGE", 0);
define("HTTP_REQUEST_TIME_OUT", 0);
define("HTTP_REQUEST_URI_TOO_LARGE", 0);
define("HTTP_RESET_CONTENT", 0);
define("HTTP_SEE_OTHER", 0);
define("HTTP_SERVICE_UNAVAILABLE", 0);
define("HTTP_SWITCHING_PROTOCOLS", 0);
define("HTTP_TEMPORARY_REDIRECT", 0);
define("HTTP_UNAUTHORIZED", 0);
define("HTTP_UNPROCESSABLE_ENTITY", 0);
define("HTTP_UNSUPPORTED_MEDIA_TYPE", 0);
define("HTTP_USE_PROXY", 0);
define("HTTP_VARIANT_ALSO_VARIES", 0);
define("HTTP_VERSION_NOT_SUPPORTED", 0);
define("IBASE_BKP_CONVERT", 0);
define("IBASE_BKP_IGNORE_CHECKSUMS", 0);
define("IBASE_BKP_IGNORE_LIMBO", 0);
define("IBASE_BKP_METADATA_ONLY", 0);
define("IBASE_BKP_NON_TRANSPORTABLE", 0);
define("IBASE_BKP_NO_GARBAGE_COLLECT", 0);
define("IBASE_BKP_OLD_DESCRIPTIONS", 0);
define("IBASE_COMMITTED", 0);
define("IBASE_CONCURRENCY", 0);
define("IBASE_CONSISTENCY", 0);
define("IBASE_CREATE", 0);
define("IBASE_DEFAULT", 0);
define("IBASE_FETCH_ARRAYS", 0);
define("IBASE_FETCH_BLOBS", 0);
define("IBASE_NOWAIT", 0);
define("IBASE_PRP_ACCESS_MODE", 0);
define("IBASE_PRP_ACTIVATE", 0);
define("IBASE_PRP_AM_READONLY", 0);
define("IBASE_PRP_AM_READWRITE", 0);
define("IBASE_PRP_DB_ONLINE", 0);
define("IBASE_PRP_DENY_NEW_ATTACHMENTS", 0);
define("IBASE_PRP_DENY_NEW_TRANSACTIONS", 0);
define("IBASE_PRP_PAGE_BUFFERS", 0);
define("IBASE_PRP_RES", 0);
define("IBASE_PRP_RESERVE_SPACE", 0);
define("IBASE_PRP_RES_USE_FULL", 0);
define("IBASE_PRP_SET_SQL_DIALECT", 0);
define("IBASE_PRP_SHUTDOWN_DB", 0);
define("IBASE_PRP_SWEEP_INTERVAL", 0);
define("IBASE_PRP_WM_ASYNC", 0);
define("IBASE_PRP_WM_SYNC", 0);
define("IBASE_PRP_WRITE_MODE", 0);
define("IBASE_READ", 0);
define("IBASE_REC_NO_VERSION", 0);
define("IBASE_REC_VERSION", 0);
define("IBASE_RES_CREATE", 0);
define("IBASE_RES_DEACTIVATE_IDX", 0);
define("IBASE_RES_NO_SHADOW", 0);
define("IBASE_RES_NO_VALIDITY", 0);
define("IBASE_RES_ONE_AT_A_TIME", 0);
define("IBASE_RES_REPLACE", 0);
define("IBASE_RES_USE_ALL_SPACE", 0);
define("IBASE_RPR_CHECK_DB", 0);
define("IBASE_RPR_FULL", 0);
define("IBASE_RPR_IGNORE_CHECKSUM", 0);
define("IBASE_RPR_KILL_SHADOWS", 0);
define("IBASE_RPR_MEND_DB", 0);
define("IBASE_RPR_SWEEP_DB", 0);
define("IBASE_RPR_VALIDATE_DB", 0);
define("IBASE_STS_DATA_PAGES", 0);
define("IBASE_STS_DB_LOG", 0);
define("IBASE_STS_HDR_PAGES", 0);
define("IBASE_STS_IDX_PAGES", 0);
define("IBASE_STS_SYS_RELATIONS", 0);
define("IBASE_SVC_GET_ENV", 0);
define("IBASE_SVC_GET_ENV_LOCK", 0);
define("IBASE_SVC_GET_ENV_MSG", 0);
define("IBASE_SVC_GET_USERS", 0);
define("IBASE_SVC_IMPLEMENTATION", 0);
define("IBASE_SVC_SERVER_VERSION", 0);
define("IBASE_SVC_SVR_DB_INFO", 0);
define("IBASE_SVC_USER_DBPATH", 0);
define("IBASE_TEXT", 0);
define("IBASE_UNIXTIME", 0);
define("IBASE_WAIT", 0);
define("IBASE_WRITE", 0);
define("ICONV_IMPL", 0);
define("ICONV_MIME_DECODE_CONTINUE_ON_ERROR", 0);
define("ICONV_MIME_DECODE_STRICT", 0);
define("ICONV_VERSION", 0);
define("IFX_HOLD", 0);
define("IFX_LO_APPEND", 0);
define("IFX_LO_BUFFER", 0);
define("IFX_LO_NOBUFFER", 0);
define("IFX_LO_RDONLY", 0);
define("IFX_LO_RDWR", 0);
define("IFX_LO_WRONLY", 0);
define("IFX_SCROLL", 0);
define("IMAGETYPE_BMP", 0);
define("IMAGETYPE_GIF", 0);
define("IMAGETYPE_IFF", 0);
define("IMAGETYPE_JB2", 0);
define("IMAGETYPE_JP2", 0);
define("IMAGETYPE_JPC", 0);
define("IMAGETYPE_JPEG", 0);
define("IMAGETYPE_JPEG2000", 0);
define("IMAGETYPE_JPX", 0);
define("IMAGETYPE_PNG", 0);
define("IMAGETYPE_PSD", 0);
define("IMAGETYPE_SWC", 0);
define("IMAGETYPE_SWF", 0);
define("IMAGETYPE_TIFF_II", 0);
define("IMAGETYPE_TIFF_MM", 0);
define("IMAGETYPE_WBMP", 0);
define("IMAGETYPE_XBM", 0);
define("IMAP_CLOSETIMEOUT", 0);
define("IMAP_OPENTIMEOUT", 0);
define("IMAP_READTIMEOUT", 0);
define("IMAP_WRITETIMEOUT", 0);
define("IMG_ARC_CHORD", 0);
define("IMG_ARC_EDGED", 0);
define("IMG_ARC_NOFILL", 0);
define("IMG_ARC_PIE", 0);
define("IMG_ARC_ROUNDED", 0);
define("IMG_COLOR_BRUSHED", 0);
define("IMG_COLOR_STYLED", 0);
define("IMG_COLOR_STYLEDBRUSHED", 0);
define("IMG_COLOR_TILED", 0);
define("IMG_COLOR_TRANSPARENT", 0);
define("IMG_EFFECT_ALPHABLEND", 0);
define("IMG_EFFECT_NORMAL", 0);
define("IMG_EFFECT_OVERLAY", 0);
define("IMG_EFFECT_REPLACE", 0);
define("IMG_FILTER_BRIGHTNESS", 0);
define("IMG_FILTER_COLORIZE", 0);
define("IMG_FILTER_CONTRAST", 0);
define("IMG_FILTER_EDGEDETECT", 0);
define("IMG_FILTER_EMBOSS", 0);
define("IMG_FILTER_GAUSSIAN_BLUR", 0);
define("IMG_FILTER_GRAYSCALE", 0);
define("IMG_FILTER_MEAN_REMOVAL", 0);
define("IMG_FILTER_NEGATE", 0);
define("IMG_FILTER_SELECTIVE_BLUR", 0);
define("IMG_FILTER_SMOOTH", 0);
define("IMG_GD2_COMPRESSED", 0);
define("IMG_GD2_RAW", 0);
define("IMG_GIF", 0);
define("IMG_JPEG", 0);
define("IMG_JPG", 0);
define("IMG_PNG", 0);
define("IMG_WBMP", 0);
define("IMG_XPM", 0);
define("INF", 0);
define("INFO_ALL", 0);
define("INFO_CONFIGURATION", 0);
define("INFO_CREDITS", 0);
define("INFO_ENVIRONMENT", 0);
define("INFO_GENERAL", 0);
define("INFO_LICENSE", 0);
define("INFO_MODULES", 0);
define("INFO_VARIABLES", 0);
define("INI_ALL", 0);
define("INI_PERDIR", 0);
define("INI_SYSTEM", 0);
define("INI_USER", 0);
define("INT_CURR_SYMBOL", 0);
define("INT_FRAC_DIGITS", 0);
define("LATT_HASCHILDREN", 0);
define("LATT_HASNOCHILDREN", 0);
define("LATT_MARKED", 0);
define("LATT_NOINFERIORS", 0);
define("LATT_NOSELECT", 0);
define("LATT_REFERRAL", 0);
define("LATT_UNMARKED", 0);
define("LC_ALL", 0);
define("LC_COLLATE", 0);
define("LC_CTYPE", 0);
define("LC_MESSAGES", 0);
define("LC_MONETARY", 0);
define("LC_NUMERIC", 0);
define("LC_TIME", 0);
define("LDAP_DEREF_ALWAYS", 0);
define("LDAP_DEREF_FINDING", 0);
define("LDAP_DEREF_NEVER", 0);
define("LDAP_DEREF_SEARCHING", 0);
define("LDAP_OPT_CLIENT_CONTROLS", 0);
define("LDAP_OPT_DEBUG_LEVEL", 0);
define("LDAP_OPT_DEREF", 0);
define("LDAP_OPT_ERROR_NUMBER", 0);
define("LDAP_OPT_ERROR_STRING", 0);
define("LDAP_OPT_HOST_NAME", 0);
define("LDAP_OPT_MATCHED_DN", 0);
define("LDAP_OPT_PROTOCOL_VERSION", 0);
define("LDAP_OPT_REFERRALS", 0);
define("LDAP_OPT_RESTART", 0);
define("LDAP_OPT_SERVER_CONTROLS", 0);
define("LDAP_OPT_SIZELIMIT", 0);
define("LDAP_OPT_TIMELIMIT", 0);
define("LDAP_OPT_X_SASL_AUTHCID", 0);
define("LDAP_OPT_X_SASL_AUTHZID", 0);
define("LDAP_OPT_X_SASL_MECH", 0);
define("LDAP_OPT_X_SASL_REALM", 0);
define("LIBEXSLT_DOTTED_VERSION", 0);
define("LIBEXSLT_VERSION", 0);
define("LIBXML_COMPACT", 0);
define("LIBXML_DOTTED_VERSION", 0);
define("LIBXML_DTDATTR", 0);
define("LIBXML_DTDLOAD", 0);
define("LIBXML_DTDVALID", 0);
define("LIBXML_ERR_ERROR", 0);
define("LIBXML_ERR_FATAL", 0);
define("LIBXML_ERR_NONE", 0);
define("LIBXML_ERR_WARNING", 0);
define("LIBXML_NOBLANKS", 0);
define("LIBXML_NOCDATA", 0);
define("LIBXML_NOEMPTYTAG", 0);
define("LIBXML_NOENT", 0);
define("LIBXML_NOERROR", 0);
define("LIBXML_NONET", 0);
define("LIBXML_NOWARNING", 0);
define("LIBXML_NOXMLDECL", 0);
define("LIBXML_NSCLEAN", 0);
define("LIBXML_VERSION", 0);
define("LIBXML_XINCLUDE", 0);
define("LIBXSLT_DOTTED_VERSION", 0);
define("LIBXSLT_VERSION", 0);
define("LOCK_EX", 0);
define("LOCK_NB", 0);
define("LOCK_SH", 0);
define("LOCK_UN", 0);
define("LOG_ALERT", 0);
define("LOG_AUTH", 0);
define("LOG_AUTHPRIV", 0);
define("LOG_CONS", 0);
define("LOG_CRIT", 0);
define("LOG_CRON", 0);
define("LOG_DAEMON", 0);
define("LOG_DEBUG", 0);
define("LOG_EMERG", 0);
define("LOG_ERR", 0);
define("LOG_INFO", 0);
define("LOG_KERN", 0);
define("LOG_LOCAL0", 0);
define("LOG_LOCAL1", 0);
define("LOG_LOCAL2", 0);
define("LOG_LOCAL3", 0);
define("LOG_LOCAL4", 0);
define("LOG_LOCAL5", 0);
define("LOG_LOCAL6", 0);
define("LOG_LOCAL7", 0);
define("LOG_LPR", 0);
define("LOG_MAIL", 0);
define("LOG_NDELAY", 0);
define("LOG_NEWS", 0);
define("LOG_NOTICE", 0);
define("LOG_NOWAIT", 0);
define("LOG_ODELAY", 0);
define("LOG_PERROR", 0);
define("LOG_PID", 0);
define("LOG_SYSLOG", 0);
define("LOG_USER", 0);
define("LOG_UUCP", 0);
define("LOG_WARNING", 0);
define("MB_CASE_LOWER", 0);
define("MB_CASE_TITLE", 0);
define("MB_CASE_UPPER", 0);
define("MB_OVERLOAD_MAIL", 0);
define("MB_OVERLOAD_REGEX", 0);
define("MB_OVERLOAD_STRING", 0);
define("MCRYPT_DECRYPT", 0);
define("MCRYPT_DEV_RANDOM", 0);
define("MCRYPT_DEV_URANDOM", 0);
define("MCRYPT_ENCRYPT", 0);
define("MCRYPT_RAND", 0);
define("MONITOR_EVENT_ALL", 0);
define("MONITOR_EVENT_CUSTOM", 0);
define("MONITOR_EVENT_DEVMEM", 0);
define("MONITOR_EVENT_DEVSCRIPT", 0);
define("MONITOR_EVENT_FUNCERROR", 0);
define("MONITOR_EVENT_LOAD", 0);
define("MONITOR_EVENT_LONGFUNCTION", 0);
define("MONITOR_EVENT_LONGSCRIPT", 0);
define("MONITOR_EVENT_MEMSIZE", 0);
define("MONITOR_EVENT_OUTPUT", 0);
define("MONITOR_EVENT_ZENDERROR", 0);
define("MON_1", 0);
define("MON_2", 0);
define("MON_3", 0);
define("MON_4", 0);
define("MON_5", 0);
define("MON_6", 0);
define("MON_7", 0);
define("MON_8", 0);
define("MON_9", 0);
define("MON_10", 0);
define("MON_11", 0);
define("MON_12", 0);
define("MON_DECIMAL_POINT", 0);
define("MON_GROUPING", 0);
define("MON_THOUSANDS_SEP", 0);
define("MSG_DONTROUTE", 0);
define("MSG_EXCEPT", 0);
define("MSG_IPC_NOWAIT", 0);
define("MSG_NOERROR", 0);
define("MSG_OOB", 0);
define("MSG_PEEK", 0);
define("MSG_WAITALL", 0);
define("MSQL_ASSOC", 0);
define("MSQL_BOTH", 0);
define("MSQL_NUM", 0);
define("MSSQL_ASSOC", 0);
define("MSSQL_BOTH", 0);
define("MSSQL_NUM", 0);
define("MYSQLI_ASSOC", 0);
define("MYSQLI_AUTO_INCREMENT_FLAG", 0);
define("MYSQLI_BLOB_FLAG", 0);
define("MYSQLI_BOTH", 0);
define("MYSQLI_CLIENT_COMPRESS", 0);
define("MYSQLI_CLIENT_FOUND_ROWS", 0);
define("MYSQLI_CLIENT_IGNORE_SPACE", 0);
define("MYSQLI_CLIENT_INTERACTIVE", 0);
define("MYSQLI_CLIENT_NO_SCHEMA", 0);
define("MYSQLI_CLIENT_SSL", 0);
define("MYSQLI_CURSOR_TYPE_FOR_UPDATE", 0);
define("MYSQLI_CURSOR_TYPE_NO_CURSOR", 0);
define("MYSQLI_CURSOR_TYPE_READ_ONLY", 0);
define("MYSQLI_CURSOR_TYPE_SCROLLABLE", 0);
define("MYSQLI_DATA_TRUNCATED", 0);
define("MYSQLI_GROUP_FLAG", 0);
define("MYSQLI_INIT_COMMAND", 0);
define("MYSQLI_MULTIPLE_KEY_FLAG", 0);
define("MYSQLI_NOT_NULL_FLAG", 0);
define("MYSQLI_NO_DATA", 0);
define("MYSQLI_NUM", 0);
define("MYSQLI_NUM_FLAG", 0);
define("MYSQLI_OPT_CONNECT_TIMEOUT", 0);
define("MYSQLI_OPT_LOCAL_INFILE", 0);
define("MYSQLI_PART_KEY_FLAG", 0);
define("MYSQLI_PRI_KEY_FLAG", 0);
define("MYSQLI_READ_DEFAULT_FILE", 0);
define("MYSQLI_READ_DEFAULT_GROUP", 0);
define("MYSQLI_REPORT_ALL", 0);
define("MYSQLI_REPORT_ERROR", 0);
define("MYSQLI_REPORT_INDEX", 0);
define("MYSQLI_REPORT_OFF", 0);
define("MYSQLI_REPORT_STRICT", 0);
define("MYSQLI_RPL_ADMIN", 0);
define("MYSQLI_RPL_MASTER", 0);
define("MYSQLI_RPL_SLAVE", 0);
define("MYSQLI_SET_FLAG", 0);
define("MYSQLI_STMT_ATTR_CURSOR_TYPE", 0);
define("MYSQLI_STMT_ATTR_PREFETCH_ROWS", 0);
define("MYSQLI_STMT_ATTR_UPDATE_MAX_LENGTH", 0);
define("MYSQLI_STORE_RESULT", 0);
define("MYSQLI_TIMESTAMP_FLAG", 0);
define("MYSQLI_TYPE_BIT", 0);
define("MYSQLI_TYPE_BLOB", 0);
define("MYSQLI_TYPE_CHAR", 0);
define("MYSQLI_TYPE_DATE", 0);
define("MYSQLI_TYPE_DATETIME", 0);
define("MYSQLI_TYPE_DECIMAL", 0);
define("MYSQLI_TYPE_DOUBLE", 0);
define("MYSQLI_TYPE_ENUM", 0);
define("MYSQLI_TYPE_FLOAT", 0);
define("MYSQLI_TYPE_GEOMETRY", 0);
define("MYSQLI_TYPE_INT24", 0);
define("MYSQLI_TYPE_INTERVAL", 0);
define("MYSQLI_TYPE_LONG", 0);
define("MYSQLI_TYPE_LONGLONG", 0);
define("MYSQLI_TYPE_LONG_BLOB", 0);
define("MYSQLI_TYPE_MEDIUM_BLOB", 0);
define("MYSQLI_TYPE_NEWDATE", 0);
define("MYSQLI_TYPE_NEWDECIMAL", 0);
define("MYSQLI_TYPE_NULL", 0);
define("MYSQLI_TYPE_SET", 0);
define("MYSQLI_TYPE_SHORT", 0);
define("MYSQLI_TYPE_STRING", 0);
define("MYSQLI_TYPE_TIME", 0);
define("MYSQLI_TYPE_TIMESTAMP", 0);
define("MYSQLI_TYPE_TINY", 0);
define("MYSQLI_TYPE_TINY_BLOB", 0);
define("MYSQLI_TYPE_VAR_STRING", 0);
define("MYSQLI_TYPE_YEAR", 0);
define("MYSQLI_UNIQUE_KEY_FLAG", 0);
define("MYSQLI_UNSIGNED_FLAG", 0);
define("MYSQLI_USE_RESULT", 0);
define("MYSQLI_ZEROFILL_FLAG", 0);
define("MYSQL_ASSOC", 0);
define("MYSQL_BOTH", 0);
define("MYSQL_CLIENT_COMPRESS", 0);
define("MYSQL_CLIENT_IGNORE_SPACE", 0);
define("MYSQL_CLIENT_INTERACTIVE", 0);
define("MYSQL_CLIENT_SSL", 0);
define("MYSQL_NUM", 0);
define("M_1_PI", 0);
define("M_2_PI", 0);
define("M_2_SQRTPI", 0);
define("M_CONNECT", 0);
define("M_COPY", 0);
define("M_DELETE", 0);
define("M_E", 0);
define("M_GET", 0);
define("M_INVALID", 0);
define("M_LN2", 0);
define("M_LN10", 0);
define("M_LOCK", 0);
define("M_LOG2E", 0);
define("M_LOG10E", 0);
define("M_MKCOL", 0);
define("M_MOVE", 0);
define("M_OPTIONS", 0);
define("M_PATCH", 0);
define("M_PI", 0);
define("M_PI_2", 0);
define("M_PI_4", 0);
define("M_POST", 0);
define("M_PROPFIND", 0);
define("M_PROPPATCH", 0);
define("M_PUT", 0);
define("M_SQRT1_2", 0);
define("M_SQRT2", 0);
define("M_TRACE", 0);
define("M_UNLOCK", 0);
define("NAN", 0);
define("NEGATIVE_SIGN", 0);
define("NIL", 0);
define("NOEXPR", 0);
define("NOSTR", 0);
define("NOT_FOUND", 0);
define("N_CS_PRECEDES", 0);
define("N_SEP_BY_SPACE", 0);
define("N_SIGN_POSN", 0);
define("OCI_ASSOC", 0);
define("OCI_BOTH", 0);
define("OCI_B_BFILE", 0);
define("OCI_B_BIN", 0);
define("OCI_B_BLOB", 0);
define("OCI_B_CFILEE", 0);
define("OCI_B_CLOB", 0);
define("OCI_B_CURSOR", 0);
define("OCI_B_INT", 0);
define("OCI_B_NTY", 0);
define("OCI_B_NUM", 0);
define("OCI_B_ROWID", 0);
define("OCI_COMMIT_ON_SUCCESS", 0);
define("OCI_DEFAULT", 0);
define("OCI_DESCRIBE_ONLY", 0);
define("OCI_DTYPE_FILE", 0);
define("OCI_DTYPE_LOB", 0);
define("OCI_DTYPE_ROWID", 0);
define("OCI_D_FILE", 0);
define("OCI_D_LOB", 0);
define("OCI_D_ROWID", 0);
define("OCI_EXACT_FETCH", 0);
define("OCI_FETCHSTATEMENT_BY_COLUMN", 0);
define("OCI_FETCHSTATEMENT_BY_ROW", 0);
define("OCI_LOB_BUFFER_FREE", 0);
define("OCI_NUM", 0);
define("OCI_RETURN_LOBS", 0);
define("OCI_RETURN_NULLS", 0);
define("OCI_SEEK_CUR", 0);
define("OCI_SEEK_END", 0);
define("OCI_SEEK_SET", 0);
define("OCI_SYSDATE", 0);
define("OCI_SYSDBA", 0);
define("OCI_SYSOPER", 0);
define("OCI_TEMP_BLOB", 0);
define("OCI_TEMP_CLOB", 0);
define("ODBC_BINMODE_CONVERT", 0);
define("ODBC_BINMODE_PASSTHRU", 0);
define("ODBC_BINMODE_RETURN", 0);
define("ODBC_TYPE", 0);
define("OK", 0);
define("OPENSSL_ALGO_MD2", 0);
define("OPENSSL_ALGO_MD4", 0);
define("OPENSSL_ALGO_MD5", 0);
define("OPENSSL_ALGO_SHA1", 0);
define("OPENSSL_CIPHER_3DES", 0);
define("OPENSSL_CIPHER_DES", 0);
define("OPENSSL_CIPHER_RC2_40", 0);
define("OPENSSL_CIPHER_RC2_64", 0);
define("OPENSSL_CIPHER_RC2_128", 0);
define("OPENSSL_KEYTYPE_DH", 0);
define("OPENSSL_KEYTYPE_DSA", 0);
define("OPENSSL_KEYTYPE_RSA", 0);
define("OPENSSL_NO_PADDING", 0);
define("OPENSSL_PKCS1_OAEP_PADDING", 0);
define("OPENSSL_PKCS1_PADDING", 0);
define("OPENSSL_SSLV23_PADDING", 0);
define("OPTIMIZER_VERSION", 0);
define("OPT_ALL", 0);
define("OPT_PASS_1", 0);
define("OPT_PASS_2", 0);
define("OPT_PASS_3", 0);
define("OPT_PASS_4", 0);
define("OPT_PASS_5", 0);
define("OPT_PASS_6", 0);
define("OPT_PASS_7", 0);
define("OPT_PASS_8", 0);
define("OPT_PASS_9", 0);
define("OPT_PASS_10", 0);
define("OP_ANONYMOUS", 0);
define("OP_DEBUG", 0);
define("OP_EXPUNGE", 0);
define("OP_HALFOPEN", 0);
define("OP_PROTOTYPE", 0);
define("OP_READONLY", 0);
define("OP_SECURE", 0);
define("OP_SHORTCACHE", 0);
define("OP_SILENT", 0);
define("PATHINFO_BASENAME", 0);
define("PATHINFO_DIRNAME", 0);
define("PATHINFO_EXTENSION", 0);
define("PATH_SEPARATOR", 0);
define("PEAR_EXTENSION_DIR", 0);
define("PEAR_INSTALL_DIR", 0);
define("PGSQL_ASSOC", 0);
define("PGSQL_BAD_RESPONSE", 0);
define("PGSQL_BOTH", 0);
define("PGSQL_COMMAND_OK", 0);
define("PGSQL_CONNECTION_BAD", 0);
define("PGSQL_CONNECTION_OK", 0);
define("PGSQL_CONNECT_FORCE_NEW", 0);
define("PGSQL_CONV_FORCE_NULL", 0);
define("PGSQL_CONV_IGNORE_DEFAULT", 0);
define("PGSQL_CONV_IGNORE_NOT_NULL", 0);
define("PGSQL_COPY_IN", 0);
define("PGSQL_COPY_OUT", 0);
define("PGSQL_DIAG_CONTEXT", 0);
define("PGSQL_DIAG_INTERNAL_POSITION", 0);
define("PGSQL_DIAG_INTERNAL_QUERY", 0);
define("PGSQL_DIAG_MESSAGE_DETAIL", 0);
define("PGSQL_DIAG_MESSAGE_HINT", 0);
define("PGSQL_DIAG_MESSAGE_PRIMARY", 0);
define("PGSQL_DIAG_SEVERITY", 0);
define("PGSQL_DIAG_SOURCE_FILE", 0);
define("PGSQL_DIAG_SOURCE_FUNCTION", 0);
define("PGSQL_DIAG_SOURCE_LINE", 0);
define("PGSQL_DIAG_SQLSTATE", 0);
define("PGSQL_DIAG_STATEMENT_POSITION", 0);
define("PGSQL_DML_ASYNC", 0);
define("PGSQL_DML_EXEC", 0);
define("PGSQL_DML_NO_CONV", 0);
define("PGSQL_DML_STRING", 0);
define("PGSQL_EMPTY_QUERY", 0);
define("PGSQL_ERRORS_DEFAULT", 0);
define("PGSQL_ERRORS_TERSE", 0);
define("PGSQL_ERRORS_VERBOSE", 0);
define("PGSQL_FATAL_ERROR", 0);
define("PGSQL_NONFATAL_ERROR", 0);
define("PGSQL_NUM", 0);
define("PGSQL_SEEK_CUR", 0);
define("PGSQL_SEEK_END", 0);
define("PGSQL_SEEK_SET", 0);
define("PGSQL_STATUS_LONG", 0);
define("PGSQL_STATUS_STRING", 0);
define("PGSQL_TRANSACTION_ACTIVE", 0);
define("PGSQL_TRANSACTION_IDLE", 0);
define("PGSQL_TRANSACTION_INERROR", 0);
define("PGSQL_TRANSACTION_INTRANS", 0);
define("PGSQL_TRANSACTION_UNKNOWN", 0);
define("PGSQL_TUPLES_OK", 0);
define("PHP_BINARY_READ", 0);
define("PHP_BINDIR", 0);
define("PHP_CONFIG_FILE_PATH", 0);
define("PHP_CONFIG_FILE_SCAN_DIR", 0);
define("PHP_DATADIR", 0);
define("PHP_EOL", 0);
define("PHP_EXTENSION_DIR", 0);
define("PHP_INT_MAX", 0);
define("PHP_INT_SIZE", 0);
define("PHP_LIBDIR", 0);
define("PHP_LOCALSTATEDIR", 0);
define("PHP_NORMAL_READ", 0);
define("PHP_OS", 0);
define("PHP_OUTPUT_HANDLER_CONT", 0);
define("PHP_OUTPUT_HANDLER_END", 0);
define("PHP_OUTPUT_HANDLER_START", 0);
define("PHP_PREFIX", 0);
define("PHP_SAPI", 0);
define("PHP_SHLIB_SUFFIX", 0);
define("PHP_SYSCONFDIR", 0);
define("PHP_URL_FRAGMENT", 0);
define("PHP_URL_HOST", 0);
define("PHP_URL_PASS", 0);
define("PHP_URL_PATH", 0);
define("PHP_URL_PORT", 0);
define("PHP_URL_QUERY", 0);
define("PHP_URL_SCHEME", 0);
define("PHP_URL_USER", 0);
define("PHP_VERSION", 0);
define("PKCS7_BINARY", 0);
define("PKCS7_DETACHED", 0);
define("PKCS7_NOATTR", 0);
define("PKCS7_NOCERTS", 0);
define("PKCS7_NOCHAIN", 0);
define("PKCS7_NOINTERN", 0);
define("PKCS7_NOSIGS", 0);
define("PKCS7_NOVERIFY", 0);
define("PKCS7_TEXT", 0);
define("PLATFORM_VERSION", 0);
define("PM_STR", 0);
define("PNG_ALL_FILTERS", 0);
define("PNG_FILTER_AVG", 0);
define("PNG_FILTER_NONE", 0);
define("PNG_FILTER_PAETH", 0);
define("PNG_FILTER_SUB", 0);
define("PNG_FILTER_UP", 0);
define("PNG_NO_FILTER", 0);
define("POSITIVE_SIGN", 0);
define("POSIX_F_OK", 0);
define("POSIX_R_OK", 0);
define("POSIX_S_IFBLK", 0);
define("POSIX_S_IFCHR", 0);
define("POSIX_S_IFIFO", 0);
define("POSIX_S_IFREG", 0);
define("POSIX_S_IFSOCK", 0);
define("POSIX_W_OK", 0);
define("POSIX_X_OK", 0);
define("PREG_GREP_INVERT", 0);
define("PREG_OFFSET_CAPTURE", 0);
define("PREG_PATTERN_ORDER", 0);
define("PREG_SET_ORDER", 0);
define("PREG_SPLIT_DELIM_CAPTURE", 0);
define("PREG_SPLIT_NO_EMPTY", 0);
define("PREG_SPLIT_OFFSET_CAPTURE", 0);
define("PRIO_PGRP", 0);
define("PRIO_PROCESS", 0);
define("PRIO_USER", 0);
define("PSFS_ERR_FATAL", 0);
define("PSFS_FEED_ME", 0);
define("PSFS_FLAG_FLUSH_CLOSE", 0);
define("PSFS_FLAG_FLUSH_INC", 0);
define("PSFS_FLAG_NORMAL", 0);
define("PSFS_PASS_ON", 0);
define("PSPELL_BAD_SPELLERS", 0);
define("PSPELL_FAST", 0);
define("PSPELL_NORMAL", 0);
define("PSPELL_RUN_TOGETHER", 0);
define("P_CS_PRECEDES", 0);
define("P_SEP_BY_SPACE", 0);
define("P_SIGN_POSN", 0);
define("RADIXCHAR", 0);
define("REDIRECT", 0);
define("REMOTE_DOUBLE_REV", 0);
define("REMOTE_HOST", 0);
define("REMOTE_NAME", 0);
define("REMOTE_NOLOOKUP", 0);
define("REQUEST_CHUNKED_DECHUNK", 0);
define("REQUEST_CHUNKED_ERROR", 0);
define("REQUEST_CHUNKED_PASS", 0);
define("REQUEST_NO_BODY", 0);
define("SA_ALL", 0);
define("SA_MESSAGES", 0);
define("SA_RECENT", 0);
define("SA_UIDNEXT", 0);
define("SA_UIDVALIDITY", 0);
define("SA_UNSEEN", 0);
define("SEEK_CUR", 0);
define("SEEK_END", 0);
define("SEEK_SET", 0);
define("SERVER_ERROR", 0);
define("SE_FREE", 0);
define("SE_NOPREFETCH", 0);
define("SE_UID", 0);
define("SID", 0);
define("SIGABRT", 0);
define("SIGALRM", 0);
define("SIGBABY", 0);
define("SIGBUS", 0);
define("SIGCHLD", 0);
define("SIGCLD", 0);
define("SIGCONT", 0);
define("SIGFPE", 0);
define("SIGHUP", 0);
define("SIGILL", 0);
define("SIGINT", 0);
define("SIGIO", 0);
define("SIGIOT", 0);
define("SIGKILL", 0);
define("SIGPIPE", 0);
define("SIGPOLL", 0);
define("SIGPROF", 0);
define("SIGPWR", 0);
define("SIGQUIT", 0);
define("SIGSEGV", 0);
define("SIGSTKFLT", 0);
define("SIGSTOP", 0);
define("SIGSYS", 0);
define("SIGTERM", 0);
define("SIGTRAP", 0);
define("SIGTSTP", 0);
define("SIGTTIN", 0);
define("SIGTTOU", 0);
define("SIGURG", 0);
define("SIGUSR1", 0);
define("SIGUSR2", 0);
define("SIGVTALRM", 0);
define("SIGWINCH", 0);
define("SIGXCPU", 0);
define("SIGXFSZ", 0);
define("SIG_DFL", 0);
define("SIG_ERR", 0);
define("SIG_IGN", 0);
define("SMFIF_ADDHDRS", 0);
define("SMFIF_ADDRCPT", 0);
define("SMFIF_CHGBODY", 0);
define("SMFIF_CHGHDRS", 0);
define("SMFIF_DELRCPT", 0);
define("SMFIS_ACCEPT", 0);
define("SMFIS_CONTINUE", 0);
define("SMFIS_DISCARD", 0);
define("SMFIS_REJECT", 0);
define("SMFIS_TEMPFAIL", 0);
define("SNMP_BIT_STR", 0);
define("SNMP_COUNTER", 0);
define("SNMP_COUNTER64", 0);
define("SNMP_INTEGER", 0);
define("SNMP_IPADDRESS", 0);
define("SNMP_NULL", 0);
define("SNMP_OBJECT_ID", 0);
define("SNMP_OCTET_STR", 0);
define("SNMP_OPAQUE", 0);
define("SNMP_TIMETICKS", 0);
define("SNMP_UINTEGER", 0);
define("SNMP_UNSIGNED", 0);
define("SNMP_VALUE_LIBRARY", 0);
define("SNMP_VALUE_OBJECT", 0);
define("SNMP_VALUE_PLAIN", 0);
define("SOAP_1_1", 0);
define("SOAP_1_2", 0);
define("SOAP_ACTOR_NEXT", 0);
define("SOAP_ACTOR_NONE", 0);
define("SOAP_ACTOR_UNLIMATERECEIVER", 0);
define("SOAP_AUTHENTICATION_BASIC", 0);
define("SOAP_AUTHENTICATION_DIGEST", 0);
define("SOAP_COMPRESSION_ACCEPT", 0);
define("SOAP_COMPRESSION_DEFLATE", 0);
define("SOAP_COMPRESSION_GZIP", 0);
define("SOAP_DOCUMENT", 0);
define("SOAP_ENCODED", 0);
define("SOAP_ENC_ARRAY", 0);
define("SOAP_ENC_OBJECT", 0);
define("SOAP_FUNCTIONS_ALL", 0);
define("SOAP_LITERAL", 0);
define("SOAP_PERSISTENCE_REQUEST", 0);
define("SOAP_PERSISTENCE_SESSION", 0);
define("SOAP_RPC", 0);
define("SOAP_SINGLE_ELEMENT_ARRAYS", 0);
define("SOAP_WAIT_ONE_WAY_CALLS", 0);
define("SOCKET_E2BIG", 0);
define("SOCKET_EACCES", 0);
define("SOCKET_EADDRINUSE", 0);
define("SOCKET_EADDRNOTAVAIL", 0);
define("SOCKET_EADV", 0);
define("SOCKET_EAFNOSUPPORT", 0);
define("SOCKET_EAGAIN", 0);
define("SOCKET_EALREADY", 0);
define("SOCKET_EBADE", 0);
define("SOCKET_EBADF", 0);
define("SOCKET_EBADFD", 0);
define("SOCKET_EBADMSG", 0);
define("SOCKET_EBADR", 0);
define("SOCKET_EBADRQC", 0);
define("SOCKET_EBADSLT", 0);
define("SOCKET_EBUSY", 0);
define("SOCKET_ECHRNG", 0);
define("SOCKET_ECOMM", 0);
define("SOCKET_ECONNABORTED", 0);
define("SOCKET_ECONNREFUSED", 0);
define("SOCKET_ECONNRESET", 0);
define("SOCKET_EDESTADDRREQ", 0);
define("SOCKET_EDISCON", 0);
define("SOCKET_EDQUOT", 0);
define("SOCKET_EEXIST", 0);
define("SOCKET_EFAULT", 0);
define("SOCKET_EHOSTDOWN", 0);
define("SOCKET_EHOSTUNREACH", 0);
define("SOCKET_EIDRM", 0);
define("SOCKET_EINPROGRESS", 0);
define("SOCKET_EINTR", 0);
define("SOCKET_EINVAL", 0);
define("SOCKET_EIO", 0);
define("SOCKET_EISCONN", 0);
define("SOCKET_EISDIR", 0);
define("SOCKET_EISNAM", 0);
define("SOCKET_EL2HLT", 0);
define("SOCKET_EL2NSYNC", 0);
define("SOCKET_EL3HLT", 0);
define("SOCKET_EL3RST", 0);
define("SOCKET_ELNRNG", 0);
define("SOCKET_ELOOP", 0);
define("SOCKET_EMEDIUMTYPE", 0);
define("SOCKET_EMFILE", 0);
define("SOCKET_EMLINK", 0);
define("SOCKET_EMSGSIZE", 0);
define("SOCKET_EMULTIHOP", 0);
define("SOCKET_ENAMETOOLONG", 0);
define("SOCKET_ENETDOWN", 0);
define("SOCKET_ENETRESET", 0);
define("SOCKET_ENETUNREACH", 0);
define("SOCKET_ENFILE", 0);
define("SOCKET_ENOANO", 0);
define("SOCKET_ENOBUFS", 0);
define("SOCKET_ENOCSI", 0);
define("SOCKET_ENODATA", 0);
define("SOCKET_ENODEV", 0);
define("SOCKET_ENOENT", 0);
define("SOCKET_ENOLCK", 0);
define("SOCKET_ENOLINK", 0);
define("SOCKET_ENOMEDIUM", 0);
define("SOCKET_ENOMEM", 0);
define("SOCKET_ENOMSG", 0);
define("SOCKET_ENONET", 0);
define("SOCKET_ENOPROTOOPT", 0);
define("SOCKET_ENOSPC", 0);
define("SOCKET_ENOSR", 0);
define("SOCKET_ENOSTR", 0);
define("SOCKET_ENOSYS", 0);
define("SOCKET_ENOTBLK", 0);
define("SOCKET_ENOTCONN", 0);
define("SOCKET_ENOTDIR", 0);
define("SOCKET_ENOTEMPTY", 0);
define("SOCKET_ENOTSOCK", 0);
define("SOCKET_ENOTTY", 0);
define("SOCKET_ENOTUNIQ", 0);
define("SOCKET_ENXIO", 0);
define("SOCKET_EOPNOTSUPP", 0);
define("SOCKET_EPERM", 0);
define("SOCKET_EPFNOSUPPORT", 0);
define("SOCKET_EPIPE", 0);
define("SOCKET_EPROCLIM", 0);
define("SOCKET_EPROTO", 0);
define("SOCKET_EPROTONOSUPPORT", 0);
define("SOCKET_EPROTOTYPE", 0);
define("SOCKET_EREMCHG", 0);
define("SOCKET_EREMOTE", 0);
define("SOCKET_EREMOTEIO", 0);
define("SOCKET_ERESTART", 0);
define("SOCKET_EROFS", 0);
define("SOCKET_ESHUTDOWN", 0);
define("SOCKET_ESOCKTNOSUPPORT", 0);
define("SOCKET_ESPIPE", 0);
define("SOCKET_ESRMNT", 0);
define("SOCKET_ESTALE", 0);
define("SOCKET_ESTRPIPE", 0);
define("SOCKET_ETIME", 0);
define("SOCKET_ETIMEDOUT", 0);
define("SOCKET_ETOOMANYREFS", 0);
define("SOCKET_EUNATCH", 0);
define("SOCKET_EUSERS", 0);
define("SOCKET_EWOULDBLOCK", 0);
define("SOCKET_EXDEV", 0);
define("SOCKET_EXFULL", 0);
define("SOCKET_HOST_NOT_FOUND", 0);
define("SOCKET_NOTINITIALISED", 0);
define("SOCKET_NO_ADDRESS", 0);
define("SOCKET_NO_DATA", 0);
define("SOCKET_NO_RECOVERY", 0);
define("SOCKET_SYSNOTREADY", 0);
define("SOCKET_TRY_AGAIN", 0);
define("SOCKET_VERNOTSUPPORTED", 0);
define("SOCK_DGRAM", 0);
define("SOCK_RAW", 0);
define("SOCK_RDM", 0);
define("SOCK_SEQPACKET", 0);
define("SOCK_STREAM", 0);
define("SOL_SOCKET", 0);
define("SOL_TCP", 0);
define("SOL_UDP", 0);
define("SOMAXCONN", 0);
define("SORTARRIVAL", 0);
define("SORTCC", 0);
define("SORTDATE", 0);
define("SORTFROM", 0);
define("SORTSIZE", 0);
define("SORTSUBJECT", 0);
define("SORTTO", 0);
define("SORT_ASC", 0);
define("SORT_DESC", 0);
define("SORT_LOCALE_STRING", 0);
define("SORT_NUMERIC", 0);
define("SORT_REGULAR", 0);
define("SORT_STRING", 0);
define("SO_BROADCAST", 0);
define("SO_DEBUG", 0);
define("SO_DONTROUTE", 0);
define("SO_ERROR", 0);
define("SO_FREE", 0);
define("SO_KEEPALIVE", 0);
define("SO_LINGER", 0);
define("SO_NOSERVER", 0);
define("SO_OOBINLINE", 0);
define("SO_RCVBUF", 0);
define("SO_RCVLOWAT", 0);
define("SO_RCVTIMEO", 0);
define("SO_REUSEADDR", 0);
define("SO_SNDBUF", 0);
define("SO_SNDLOWAT", 0);
define("SO_SNDTIMEO", 0);
define("SO_TYPE", 0);
define("SQLBIT", 0);
define("SQLCHAR", 0);
define("SQLFLT4", 0);
define("SQLFLT8", 0);
define("SQLFLTN", 0);
define("SQLINT1", 0);
define("SQLINT2", 0);
define("SQLINT4", 0);
define("SQLITE_ABORT", 0);
define("SQLITE_ASSOC", 0);
define("SQLITE_AUTH", 0);
define("SQLITE_BOTH", 0);
define("SQLITE_BUSY", 0);
define("SQLITE_CANTOPEN", 0);
define("SQLITE_CONSTRAINT", 0);
define("SQLITE_CORRUPT", 0);
define("SQLITE_DONE", 0);
define("SQLITE_EMPTY", 0);
define("SQLITE_ERROR", 0);
define("SQLITE_FORMAT", 0);
define("SQLITE_FULL", 0);
define("SQLITE_INTERNAL", 0);
define("SQLITE_INTERRUPT", 0);
define("SQLITE_IOERR", 0);
define("SQLITE_LOCKED", 0);
define("SQLITE_MISMATCH", 0);
define("SQLITE_MISUSE", 0);
define("SQLITE_NOLFS", 0);
define("SQLITE_NOMEM", 0);
define("SQLITE_NOTADB", 0);
define("SQLITE_NOTFOUND", 0);
define("SQLITE_NUM", 0);
define("SQLITE_OK", 0);
define("SQLITE_PERM", 0);
define("SQLITE_PROTOCOL", 0);
define("SQLITE_READONLY", 0);
define("SQLITE_ROW", 0);
define("SQLITE_SCHEMA", 0);
define("SQLITE_TOOBIG", 0);
define("SQLTEXT", 0);
define("SQLT_AFC", 0);
define("SQLT_AVC", 0);
define("SQLT_BDOUBLE", 0);
define("SQLT_BFILEE", 0);
define("SQLT_BFLOAT", 0);
define("SQLT_BIN", 0);
define("SQLT_BLOB", 0);
define("SQLT_CFILEE", 0);
define("SQLT_CHR", 0);
define("SQLT_CLOB", 0);
define("SQLT_FLT", 0);
define("SQLT_INT", 0);
define("SQLT_LBI", 0);
define("SQLT_LNG", 0);
define("SQLT_LVC", 0);
define("SQLT_NTY", 0);
define("SQLT_NUM", 0);
define("SQLT_ODT", 0);
define("SQLT_RDD", 0);
define("SQLT_RSET", 0);
define("SQLT_STR", 0);
define("SQLT_UIN", 0);
define("SQLT_VCS", 0);
define("SQLVARCHAR", 0);
define("SQL_BEST_ROWID", 0);
define("SQL_BIGINT", 0);
define("SQL_BINARY", 0);
define("SQL_BIT", 0);
define("SQL_CHAR", 0);
define("SQL_CONCURRENCY", 0);
define("SQL_CONCUR_LOCK", 0);
define("SQL_CONCUR_READ_ONLY", 0);
define("SQL_CONCUR_ROWVER", 0);
define("SQL_CONCUR_VALUES", 0);
define("SQL_CURSOR_DYNAMIC", 0);
define("SQL_CURSOR_FORWARD_ONLY", 0);
define("SQL_CURSOR_KEYSET_DRIVEN", 0);
define("SQL_CURSOR_STATIC", 0);
define("SQL_CURSOR_TYPE", 0);
define("SQL_CUR_USE_DRIVER", 0);
define("SQL_CUR_USE_IF_NEEDED", 0);
define("SQL_CUR_USE_ODBC", 0);
define("SQL_DATE", 0);
define("SQL_DECIMAL", 0);
define("SQL_DOUBLE", 0);
define("SQL_ENSURE", 0);
define("SQL_FETCH_FIRST", 0);
define("SQL_FETCH_NEXT", 0);
define("SQL_FLOAT", 0);
define("SQL_INDEX_ALL", 0);
define("SQL_INDEX_UNIQUE", 0);
define("SQL_INTEGER", 0);
define("SQL_KEYSET_SIZE", 0);
define("SQL_LONGVARBINARY", 0);
define("SQL_LONGVARCHAR", 0);
define("SQL_NO_NULLS", 0);
define("SQL_NULLABLE", 0);
define("SQL_NUMERIC", 0);
define("SQL_ODBC_CURSORS", 0);
define("SQL_QUICK", 0);
define("SQL_REAL", 0);
define("SQL_ROWVER", 0);
define("SQL_SCOPE_CURROW", 0);
define("SQL_SCOPE_SESSION", 0);
define("SQL_SCOPE_TRANSACTION", 0);
define("SQL_SMALLINT", 0);
define("SQL_TIME", 0);
define("SQL_TIMESTAMP", 0);
define("SQL_TINYINT", 0);
define("SQL_TYPE_DATE", 0);
define("SQL_TYPE_TIME", 0);
define("SQL_TYPE_TIMESTAMP", 0);
define("SQL_VARBINARY", 0);
define("SQL_VARCHAR", 0);
define("STREAM_CLIENT_ASYNC_CONNECT", 0);
define("STREAM_CLIENT_CONNECT", 0);
define("STREAM_CLIENT_PERSISTENT", 0);
define("STREAM_CRYPTO_METHOD_SSLv2_CLIENT", 0);
define("STREAM_CRYPTO_METHOD_SSLv2_SERVER", 0);
define("STREAM_CRYPTO_METHOD_SSLv3_CLIENT", 0);
define("STREAM_CRYPTO_METHOD_SSLv3_SERVER", 0);
define("STREAM_CRYPTO_METHOD_SSLv23_CLIENT", 0);
define("STREAM_CRYPTO_METHOD_SSLv23_SERVER", 0);
define("STREAM_CRYPTO_METHOD_TLS_CLIENT", 0);
define("STREAM_CRYPTO_METHOD_TLS_SERVER", 0);
define("STREAM_ENFORCE_SAFE_MODE", 0);
define("STREAM_FILTER_ALL", 0);
define("STREAM_FILTER_READ", 0);
define("STREAM_FILTER_WRITE", 0);
define("STREAM_IGNORE_URL", 0);
define("STREAM_IPPROTO_ICMP", 0);
define("STREAM_IPPROTO_IP", 0);
define("STREAM_IPPROTO_RAW", 0);
define("STREAM_IPPROTO_TCP", 0);
define("STREAM_IPPROTO_UDP", 0);
define("STREAM_MKDIR_RECURSIVE", 0);
define("STREAM_MUST_SEEK", 0);
define("STREAM_NOTIFY_AUTH_REQUIRED", 0);
define("STREAM_NOTIFY_AUTH_RESULT", 0);
define("STREAM_NOTIFY_COMPLETED", 0);
define("STREAM_NOTIFY_CONNECT", 0);
define("STREAM_NOTIFY_FAILURE", 0);
define("STREAM_NOTIFY_FILE_SIZE_IS", 0);
define("STREAM_NOTIFY_MIME_TYPE_IS", 0);
define("STREAM_NOTIFY_PROGRESS", 0);
define("STREAM_NOTIFY_REDIRECTED", 0);
define("STREAM_NOTIFY_RESOLVE", 0);
define("STREAM_NOTIFY_SEVERITY_ERR", 0);
define("STREAM_NOTIFY_SEVERITY_INFO", 0);
define("STREAM_NOTIFY_SEVERITY_WARN", 0);
define("STREAM_OOB", 0);
define("STREAM_PEEK", 0);
define("STREAM_PF_INET", 0);
define("STREAM_PF_INET6", 0);
define("STREAM_PF_UNIX", 0);
define("STREAM_REPORT_ERRORS", 0);
define("STREAM_SERVER_BIND", 0);
define("STREAM_SERVER_LISTEN", 0);
define("STREAM_SOCK_DGRAM", 0);
define("STREAM_SOCK_RAW", 0);
define("STREAM_SOCK_RDM", 0);
define("STREAM_SOCK_SEQPACKET", 0);
define("STREAM_SOCK_STREAM", 0);
define("STREAM_URL_STAT_LINK", 0);
define("STREAM_URL_STAT_QUIET", 0);
define("STREAM_USE_PATH", 0);
define("STR_PAD_BOTH", 0);
define("STR_PAD_LEFT", 0);
define("STR_PAD_RIGHT", 0);
define("ST_SET", 0);
define("ST_SILENT", 0);
define("ST_UID", 0);
define("SUNFUNCS_RET_DOUBLE", 0);
define("SUNFUNCS_RET_STRING", 0);
define("SUNFUNCS_RET_TIMESTAMP", 0);
define("THOUSANDS_SEP", 0);
define("THOUSEP", 0);
define("TYPEAPPLICATION", 0);
define("TYPEAUDIO", 0);
define("TYPEIMAGE", 0);
define("TYPEMESSAGE", 0);
define("TYPEMODEL", 0);
define("TYPEMULTIPART", 0);
define("TYPEOTHER", 0);
define("TYPETEXT", 0);
define("TYPEVIDEO", 0);
define("T_ABSTRACT", 0);
define("T_AND_EQUAL", 0);
define("T_ARRAY", 0);
define("T_ARRAY_CAST", 0);
define("T_AS", 0);
define("T_BAD_CHARACTER", 0);
define("T_BOOLEAN_AND", 0);
define("T_BOOLEAN_OR", 0);
define("T_BOOL_CAST", 0);
define("T_BREAK", 0);
define("T_CASE", 0);
define("T_CATCH", 0);
define("T_CHARACTER", 0);
define("T_CLASS", 0);
define("T_CLASS_C", 0);
define("T_CLONE", 0);
define("T_CLOSE_TAG", 0);
define("T_COMMENT", 0);
define("T_CONCAT_EQUAL", 0);
define("T_CONST", 0);
define("T_CONSTANT_ENCAPSED_STRING", 0);
define("T_CONTINUE", 0);
define("T_CURLY_OPEN", 0);
define("T_DEC", 0);
define("T_DECLARE", 0);
define("T_DEFAULT", 0);
define("T_DIV_EQUAL", 0);
define("T_DNUMBER", 0);
define("T_DO", 0);
define("T_DOC_COMMENT", 0);
define("T_DOLLAR_OPEN_CURLY_BRACES", 0);
define("T_DOUBLE_ARROW", 0);
define("T_DOUBLE_CAST", 0);
define("T_DOUBLE_COLON", 0);
define("T_ECHO", 0);
define("T_ELSE", 0);
define("T_ELSEIF", 0);
define("T_EMPTY", 0);
define("T_ENCAPSED_AND_WHITESPACE", 0);
define("T_ENDDECLARE", 0);
define("T_ENDFOR", 0);
define("T_ENDFOREACH", 0);
define("T_ENDIF", 0);
define("T_ENDSWITCH", 0);
define("T_ENDWHILE", 0);
define("T_END_HEREDOC", 0);
define("T_EVAL", 0);
define("T_EXIT", 0);
define("T_EXTENDS", 0);
define("T_FILE", 0);
define("T_FINAL", 0);
define("T_FMT", 0);
define("T_FMT_AMPM", 0);
define("T_FOR", 0);
define("T_FOREACH", 0);
define("T_FUNCTION", 0);
define("T_FUNC_C", 0);
define("T_GLOBAL", 0);
define("T_HALT_COMPILER", 0);
define("T_IF", 0);
define("T_IMPLEMENTS", 0);
define("T_INC", 0);
define("T_INCLUDE", 0);
define("T_INCLUDE_ONCE", 0);
define("T_INLINE_HTML", 0);
define("T_INSTANCEOF", 0);
define("T_INTERFACE", 0);
define("T_INT_CAST", 0);
define("T_ISSET", 0);
define("T_IS_EQUAL", 0);
define("T_IS_GREATER_OR_EQUAL", 0);
define("T_IS_IDENTICAL", 0);
define("T_IS_NOT_EQUAL", 0);
define("T_IS_NOT_IDENTICAL", 0);
define("T_IS_SMALLER_OR_EQUAL", 0);
define("T_LINE", 0);
define("T_LIST", 0);
define("T_LNUMBER", 0);
define("T_LOGICAL_AND", 0);
define("T_LOGICAL_OR", 0);
define("T_LOGICAL_XOR", 0);
define("T_METHOD_C", 0);
define("T_MINUS_EQUAL", 0);
define("T_MOD_EQUAL", 0);
define("T_MUL_EQUAL", 0);
define("T_NEW", 0);
define("T_NUM_STRING", 0);
define("T_OBJECT_CAST", 0);
define("T_OBJECT_OPERATOR", 0);
define("T_OPEN_TAG", 0);
define("T_OPEN_TAG_WITH_ECHO", 0);
define("T_OR_EQUAL", 0);
define("T_PAAMAYIM_NEKUDOTAYIM", 0);
define("T_PLUS_EQUAL", 0);
define("T_PRINT", 0);
define("T_PRIVATE", 0);
define("T_PROTECTED", 0);
define("T_PUBLIC", 0);
define("T_REQUIRE", 0);
define("T_REQUIRE_ONCE", 0);
define("T_RETURN", 0);
define("T_SL", 0);
define("T_SL_EQUAL", 0);
define("T_SR", 0);
define("T_SR_EQUAL", 0);
define("T_START_HEREDOC", 0);
define("T_STATIC", 0);
define("T_STRING", 0);
define("T_STRING_CAST", 0);
define("T_STRING_VARNAME", 0);
define("T_SWITCH", 0);
define("T_THROW", 0);
define("T_TRY", 0);
define("T_UNSET", 0);
define("T_UNSET_CAST", 0);
define("T_USE", 0);
define("T_VAR", 0);
define("T_VARIABLE", 0);
define("T_WHILE", 0);
define("T_WHITESPACE", 0);
define("T_XOR_EQUAL", 0);
define("UNKNOWN_TYPE", 0);
define("UPLOAD_ERR_CANT_WRITE", 0);
define("UPLOAD_ERR_FORM_SIZE", 0);
define("UPLOAD_ERR_INI_SIZE", 0);
define("UPLOAD_ERR_NO_FILE", 0);
define("UPLOAD_ERR_NO_TMP_DIR", 0);
define("UPLOAD_ERR_OK", 0);
define("UPLOAD_ERR_PARTIAL", 0);
define("WNOHANG", 0);
define("WSDL_CACHE_BOTH", 0);
define("WSDL_CACHE_DISK", 0);
define("WSDL_CACHE_MEMORY", 0);
define("WSDL_CACHE_NONE", 0);
define("WUNTRACED", 0);
define("X509_PURPOSE_ANY", 0);
define("X509_PURPOSE_CRL_SIGN", 0);
define("X509_PURPOSE_NS_SSL_SERVER", 0);
define("X509_PURPOSE_SMIME_ENCRYPT", 0);
define("X509_PURPOSE_SMIME_SIGN", 0);
define("X509_PURPOSE_SSL_CLIENT", 0);
define("X509_PURPOSE_SSL_SERVER", 0);
define("XML_ATTRIBUTE_CDATA", 0);
define("XML_ATTRIBUTE_DECL_NODE", 0);
define("XML_ATTRIBUTE_ENTITY", 0);
define("XML_ATTRIBUTE_ENUMERATION", 0);
define("XML_ATTRIBUTE_ID", 0);
define("XML_ATTRIBUTE_IDREF", 0);
define("XML_ATTRIBUTE_IDREFS", 0);
define("XML_ATTRIBUTE_NMTOKEN", 0);
define("XML_ATTRIBUTE_NMTOKENS", 0);
define("XML_ATTRIBUTE_NODE", 0);
define("XML_ATTRIBUTE_NOTATION", 0);
define("XML_CDATA_SECTION_NODE", 0);
define("XML_COMMENT_NODE", 0);
define("XML_DOCUMENT_FRAG_NODE", 0);
define("XML_DOCUMENT_NODE", 0);
define("XML_DOCUMENT_TYPE_NODE", 0);
define("XML_DTD_NODE", 0);
define("XML_ELEMENT_DECL_NODE", 0);
define("XML_ELEMENT_NODE", 0);
define("XML_ENTITY_DECL_NODE", 0);
define("XML_ENTITY_NODE", 0);
define("XML_ENTITY_REF_NODE", 0);
define("XML_ERROR_ASYNC_ENTITY", 0);
define("XML_ERROR_ATTRIBUTE_EXTERNAL_ENTITY_REF", 0);
define("XML_ERROR_BAD_CHAR_REF", 0);
define("XML_ERROR_BINARY_ENTITY_REF", 0);
define("XML_ERROR_DUPLICATE_ATTRIBUTE", 0);
define("XML_ERROR_EXTERNAL_ENTITY_HANDLING", 0);
define("XML_ERROR_INCORRECT_ENCODING", 0);
define("XML_ERROR_INVALID_TOKEN", 0);
define("XML_ERROR_JUNK_AFTER_DOC_ELEMENT", 0);
define("XML_ERROR_MISPLACED_XML_PI", 0);
define("XML_ERROR_NONE", 0);
define("XML_ERROR_NO_ELEMENTS", 0);
define("XML_ERROR_NO_MEMORY", 0);
define("XML_ERROR_PARAM_ENTITY_REF", 0);
define("XML_ERROR_PARTIAL_CHAR", 0);
define("XML_ERROR_RECURSIVE_ENTITY_REF", 0);
define("XML_ERROR_SYNTAX", 0);
define("XML_ERROR_TAG_MISMATCH", 0);
define("XML_ERROR_UNCLOSED_CDATA_SECTION", 0);
define("XML_ERROR_UNCLOSED_TOKEN", 0);
define("XML_ERROR_UNDEFINED_ENTITY", 0);
define("XML_ERROR_UNKNOWN_ENCODING", 0);
define("XML_GLOBAL_NAMESPACE", 0);
define("XML_HTML_DOCUMENT_NODE", 0);
define("XML_LOCAL_NAMESPACE", 0);
define("XML_NAMESPACE_DECL_NODE", 0);
define("XML_NOTATION_NODE", 0);
define("XML_OPTION_CASE_FOLDING", 0);
define("XML_OPTION_SKIP_TAGSTART", 0);
define("XML_OPTION_SKIP_WHITE", 0);
define("XML_OPTION_TARGET_ENCODING", 0);
define("XML_PI_NODE", 0);
define("XML_SAX_IMPL", 0);
define("XML_TEXT_NODE", 0);
define("XSD_1999_NAMESPACE", 0);
define("XSD_1999_TIMEINSTANT", 0);
define("XSD_ANYTYPE", 0);
define("XSD_ANYURI", 0);
define("XSD_ANYXML", 0);
define("XSD_BASE64BINARY", 0);
define("XSD_BOOLEAN", 0);
define("XSD_BYTE", 0);
define("XSD_DATE", 0);
define("XSD_DATETIME", 0);
define("XSD_DECIMAL", 0);
define("XSD_DOUBLE", 0);
define("XSD_DURATION", 0);
define("XSD_ENTITIES", 0);
define("XSD_ENTITY", 0);
define("XSD_FLOAT", 0);
define("XSD_GDAY", 0);
define("XSD_GMONTH", 0);
define("XSD_GMONTHDAY", 0);
define("XSD_GYEAR", 0);
define("XSD_GYEARMONTH", 0);
define("XSD_HEXBINARY", 0);
define("XSD_ID", 0);
define("XSD_IDREF", 0);
define("XSD_IDREFS", 0);
define("XSD_INT", 0);
define("XSD_INTEGER", 0);
define("XSD_LANGUAGE", 0);
define("XSD_LONG", 0);
define("XSD_NAME", 0);
define("XSD_NAMESPACE", 0);
define("XSD_NCNAME", 0);
define("XSD_NEGATIVEINTEGER", 0);
define("XSD_NMTOKEN", 0);
define("XSD_NMTOKENS", 0);
define("XSD_NONNEGATIVEINTEGER", 0);
define("XSD_NONPOSITIVEINTEGER", 0);
define("XSD_NORMALIZEDSTRING", 0);
define("XSD_NOTATION", 0);
define("XSD_POSITIVEINTEGER", 0);
define("XSD_QNAME", 0);
define("XSD_SHORT", 0);
define("XSD_STRING", 0);
define("XSD_TIME", 0);
define("XSD_TOKEN", 0);
define("XSD_UNSIGNEDBYTE", 0);
define("XSD_UNSIGNEDINT", 0);
define("XSD_UNSIGNEDLONG", 0);
define("XSD_UNSIGNEDSHORT", 0);
define("XSL_CLONE_ALWAYS", 0);
define("XSL_CLONE_AUTO", 0);
define("XSL_CLONE_NEVER", 0);
define("YESEXPR", 0);
define("YESSTR", 0);
define("ZPS_VERSION", 0);
define("__CLASS__", 0);
define("__COMPILER_HALT_OFFSET__", 0);
define("__FILE__", 0);
define("__FUNCTION__", 0);
define("__LINE__", 0);
define("__METHOD__", 0);

?>
